package group.ordersystem.service.impl;

import group.ordersystem.mapper.MenuMapper;
import group.ordersystem.mapper.MenuOrderMapper;
import group.ordersystem.mapper.OrderMapper;
import group.ordersystem.mapper.UserMapper;
import group.ordersystem.pojo.Menu;
import group.ordersystem.pojo.Orders;
import group.ordersystem.pojo.User;
import group.ordersystem.pojo.form.PostOrderForm;
import group.ordersystem.pojo.res.GetOrdersRes;
import group.ordersystem.pojo.res.TokenRes;
import group.ordersystem.pojo.form.RegisterForm;
import group.ordersystem.service.CustomerService;
import group.ordersystem.util.JWTUtil;
import group.ordersystem.util.enums.OrderStatusEnum;
import group.ordersystem.util.enums.ResponseEnum;
import group.ordersystem.util.response.ResponseException;
import group.ordersystem.util.response.UniversalResponse;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private MenuOrderMapper menuOrderMapper;

    /**
     * 用户登录
     *
     * @param account  账户名称
     * @param password 账户密码
     * @return
     */
    @Override
    public UniversalResponse<TokenRes> login(String account, String password) {
        User user = userMapper.getUserByAccount(account);
        if (user == null) {
            throw new ResponseException(ResponseEnum.USER_LOGIN_ERROR.getCode(), ResponseEnum.USER_LOGIN_ERROR.getMsg());
        }
        if (!user.getPassword().equals(password)) {
            throw new ResponseException(ResponseEnum.USER_LOGIN_ERROR.getCode(), ResponseEnum.USER_LOGIN_ERROR.getMsg());
        }
        // 用户名、密码正确
        // 生成token
        String token;
        token = JWTUtil.createToken(account);
        return new UniversalResponse<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg(), new TokenRes(token));
    }

    /**
     * 用户注册
     *
     * @param registerForm
     * @return
     */
    @Override
    public UniversalResponse<?> register(RegisterForm registerForm) {
        User newUser = new User();
        newUser.setUser_id(null);
        newUser.setUser_name(registerForm.getUser_name());
        newUser.setIdentity(registerForm.getIdentity());
        newUser.setAccount(registerForm.getAccount());
        newUser.setPassword(registerForm.getPassword());

        if (Strings.isBlank(newUser.getUser_name()) || Strings.isBlank(newUser.getPassword()) || Strings.isBlank(newUser.getAccount())) {
            throw new ResponseException(ResponseEnum.PARAM_IS_INVALID.getCode(), ResponseEnum.PARAM_IS_INVALID.getMsg());
        }

        User user = userMapper.getUserByAccount(newUser.getAccount());
        if (user != null) {
            throw new ResponseException(ResponseEnum.USER_ACCOUNT_EXISTS.getCode(), ResponseEnum.USER_ACCOUNT_EXISTS.getMsg());
        }

        userMapper.insertUser(newUser);

        return new UniversalResponse<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg());
    }

    /**
     * 获取菜单
     * @return
     */
    @Override
    public UniversalResponse<List<Menu>> getMenu() {
        return new UniversalResponse<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg(), menuMapper.selectMenu());
    }

    /**
     * 获取用户的全部订单
     * @return
     */
    @Override
    public UniversalResponse<List<GetOrdersRes>> getOrder() {
        User user = JWTUtil.getCurrentUser();
        //数据库查询到orders表中的所有订单数据
        List<Orders> orders = orderMapper.getOrdersByUserId(user.getUser_id());
        //返回值类型的变量
        List<GetOrdersRes> ordersResList = new ArrayList<>();
        //往返回值类型的变量里添加数据
        for (Orders o : orders) {
            GetOrdersRes ordersRes = new GetOrdersRes();
            Integer order_id = o.getOrder_id();
            //通过order_id查询该订单菜品
            List<Menu> menu = orderMapper.getMenusByOrderId(order_id);
            //输入数据
            ordersRes.setOrder_id(o.getOrder_id());
            ordersRes.setOrder_comment(o.getOrder_comment());
            ordersRes.setOrder_price(o.getOrder_price());
            ordersRes.setDestination(o.getDestination());
            ordersRes.setStatus(o.getStatus());
            ordersRes.setDeliver_id(o.getDeliver_id());
            ordersRes.setDeliver_time(o.getDeliver_time());

            ordersRes.setMenus(menu);

            ordersResList.add(ordersRes);
        }

        return new UniversalResponse<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg(), ordersResList);
    }

    /**
     * 用户选完菜品后提交订单
     * @param postOrderForm
     * @return
     */
    @Override
    public UniversalResponse<?> insertOrder(PostOrderForm postOrderForm) {

        //加载postOderForm对象数据
        Integer status = OrderStatusEnum.CREATED.getCode();
        Integer customer_id = JWTUtil.getCurrentUser().getUser_id();
        String destination = postOrderForm.getDestination();
        Integer order_price = postOrderForm.getOrder_price();   // TODO:需不需要从前端获取总价格
        List<Integer> meals = postOrderForm.getMeals();
        //判断地址是否为空
        if (destination == null) {
            throw new ResponseException(ResponseEnum.PARAM_IS_BLANK.getCode(), ResponseEnum.PARAM_IS_BLANK.getMsg());
        }
        Orders newOrders = new Orders(status, customer_id, destination, order_price);
        //添加订单信息（除订单中的菜品信息）
        orderMapper.insertOrderDB(newOrders);
        //找到刚添加订单信息的主键号
        Integer last_order = newOrders.getOrder_id();
        //添加该订单菜品信息
        for (Integer i : meals) {
            orderMapper.insertMealsInOrder(last_order, i);
        }
        //成功
        return new UniversalResponse<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg());
    }
}
