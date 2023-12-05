package group.ordersystem.service.impl;

import group.ordersystem.mapper.MenuMapper;
import group.ordersystem.mapper.MenuOrderMapper;
import group.ordersystem.mapper.OrderMapper;
import group.ordersystem.mapper.UserMapper;
import group.ordersystem.pojo.Menu;
import group.ordersystem.pojo.Orders;
import group.ordersystem.pojo.User;
import group.ordersystem.pojo.form.CommentForm;
import group.ordersystem.pojo.form.PostOrderForm;
import group.ordersystem.pojo.res.GetOrdersRes;
import group.ordersystem.service.CustomerService;
import group.ordersystem.util.JWTUtil;
import group.ordersystem.util.enums.OrderStatusEnum;
import group.ordersystem.util.enums.ResponseEnum;
import group.ordersystem.util.response.ResponseException;
import group.ordersystem.util.response.UniversalResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            List<Menu> menu = menuMapper.getMenusByOrderId(order_id);
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
        Integer order_price = 0;
        List<Integer> meals = postOrderForm.getMeals();
        //判断地址是否为空
        if (destination == null) {
            throw new ResponseException(ResponseEnum.PARAM_IS_BLANK.getCode(), ResponseEnum.PARAM_IS_BLANK.getMsg());
        }

        // 从数据库中取出菜品价格
        for (Integer meal_id : meals) {
            order_price += menuMapper.getMealPriceByMealId(meal_id);
        }

        Orders newOrders = new Orders(status, customer_id, destination, order_price);
        //添加订单信息（除订单中的菜品信息）
        orderMapper.insertOrderDB(newOrders);
        //找到刚添加订单信息的主键号
        Integer last_order = newOrders.getOrder_id();
        //添加该订单菜品信息
        for (Integer i : meals) {
            menuOrderMapper.insertMealsInOrder(last_order, i);
        }
        //成功
        return new UniversalResponse<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg());
    }
    /**
     * 用户修改/添加评论
     * @param commentForm
     * @return
     */
    @Override
    public UniversalResponse<?> updateComment(CommentForm commentForm){
        Integer customer_id = JWTUtil.getCurrentUser().getUser_id();
        Orders orders = orderMapper.getOrdersByOrderId(commentForm.getOrder_id());
        //前端传入的需要评论的order与登录的user不匹配
        if(!Objects.equals(customer_id, orders.getCustomer_id())){
            throw new ResponseException(ResponseEnum.USER_MATCH_ERROR.getCode(), ResponseEnum.USER_MATCH_ERROR.getMsg());
        }
        //如果订单还未送达
        if(orders.getStatus()<OrderStatusEnum.FINISHED.getCode()){
            throw new ResponseException(ResponseEnum.ORDER_STATE_ERROR.getCode(), ResponseEnum.ORDER_STATE_ERROR.getMsg());
        }
        //字符串长度超过300
        if(commentForm.getOrder_comment().length()>300){
            throw new ResponseException(ResponseEnum.PARAM_IS_OVERLONG.getCode(), ResponseEnum.PARAM_IS_OVERLONG.getMsg());
        }
        if(orders.getOrder_comment()==null || orders.getOrder_comment().isEmpty()){
            //如果尚未评论，添加评论，并修改订单状态为已经评论
            orderMapper.updateOrderComment(commentForm.getOrder_comment(),commentForm.getOrder_id());
            if(!commentForm.getOrder_comment().isEmpty()){
                orderMapper.updateOrderStatus(OrderStatusEnum.COMMENTED.getCode(), orders.getOrder_id());
            }

            return new UniversalResponse<>(ResponseEnum.ADD_COMMENT.getCode(), ResponseEnum.ADD_COMMENT.getMsg());
        }else {
            //修改评论
                //如修改为空，则修改订单状态为还没有评论
            if (commentForm.getOrder_comment().isEmpty()){
                orderMapper.updateOrderStatus(OrderStatusEnum.FINISHED.getCode(), orders.getOrder_id());
            }
            orderMapper.updateOrderComment(commentForm.getOrder_comment(),commentForm.getOrder_id());
            return new UniversalResponse<>(ResponseEnum.CHANGE_COMMENT.getCode(), ResponseEnum.CHANGE_COMMENT.getMsg());
        }

    }
    /**
     * 用户删除订单（商家接单前）
     * @param order_id
     * @return
     */
    @Override
    public UniversalResponse<?> deleteOrderByCustomer(Integer order_id){
        Integer customer_id = JWTUtil.getCurrentUser().getUser_id();
        Orders orders = orderMapper.getOrdersByOrderId(order_id);
        //前端传入的需要删除的order与登录的user不匹配
        if(!Objects.equals(customer_id, orders.getCustomer_id())){
            throw new ResponseException(ResponseEnum.USER_MATCH_ERROR.getCode(), ResponseEnum.USER_MATCH_ERROR.getMsg());
        }
        if (Objects.equals(orders.getStatus(), OrderStatusEnum.CREATED.getCode())){
            menuOrderMapper.deleteMealsInOrder(order_id);
            orderMapper.deleteOrder(order_id);
            return new UniversalResponse<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg());
        }else {
            throw new ResponseException(ResponseEnum.ORDER_CANT_DELETE.getCode(),ResponseEnum.ORDER_CANT_DELETE.getMsg());
        }
    }
}
