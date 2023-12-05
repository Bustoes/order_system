package group.ordersystem.service.impl;

import group.ordersystem.mapper.MenuMapper;
import group.ordersystem.mapper.MenuOrderMapper;
import group.ordersystem.mapper.OrderMapper;
import group.ordersystem.mapper.UserMapper;
import group.ordersystem.pojo.Menu;
import group.ordersystem.pojo.Orders;
import group.ordersystem.pojo.User;
import group.ordersystem.pojo.res.GetOrdersRes;
import group.ordersystem.service.DeliveryService;
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
public class DeliveryServicelmpl implements DeliveryService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private MenuOrderMapper menuOrderMapper;
    /**
     * 获取系统中已出餐的全部订单
     * @return
     *
     */
    @Override
    public UniversalResponse<List<GetOrdersRes>> getOrder() {
        //数据库查询到orders表中的所有订单数据
        List<Orders> orders = orderMapper.getOrdersCooked();
        //返回值类型的变量
        List<GetOrdersRes> ordersResList = new ArrayList<>();
        //往返回值类型的变量里添加数据
        for (Orders o : orders) {
            GetOrdersRes ordersRes = new GetOrdersRes();
            //输入数据
            ordersRes.setOrder_id(o.getOrder_id());
            ordersRes.setOrder_comment(o.getOrder_comment());
            ordersRes.setOrder_price(o.getOrder_price());
            ordersRes.setDestination(o.getDestination());
            ordersRes.setStatus(o.getStatus());
            ordersRes.setDeliver_id(o.getDeliver_id());
            ordersRes.setDeliver_time(o.getDeliver_time());

            ordersResList.add(ordersRes);
        }

        return new UniversalResponse<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg(), ordersResList);
    }


    /**
     * 查询送餐员的已有订单信息
     * @return
     */
    @Override
    public UniversalResponse<List<GetOrdersRes>> getmyOrder() {
        User user = JWTUtil.getCurrentUser();
        //数据库查询到orders表中的所有订单数据
        List<Orders> orders = orderMapper.getOrdersByDelivery(user.getUser_id());
        //返回值类型的变量
        List<GetOrdersRes> ordersResList = new ArrayList<>();
        //往返回值类型的变量里添加数据
        for (Orders o : orders) {
            GetOrdersRes ordersRes = new GetOrdersRes();
            //输入数据
            ordersRes.setOrder_id(o.getOrder_id());
            ordersRes.setOrder_comment(o.getOrder_comment());
            ordersRes.setOrder_price(o.getOrder_price());
            ordersRes.setDestination(o.getDestination());
            ordersRes.setStatus(o.getStatus());
            ordersRes.setDeliver_id(o.getDeliver_id());
            ordersRes.setDeliver_time(o.getDeliver_time());

            ordersResList.add(ordersRes);
        }

        return new UniversalResponse<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg(), ordersResList);
    }
    /**
     * 送餐员接单    必要条件：orders.getStatus()==2
     * @return
     */
    @Override
    public UniversalResponse<?> takeOrderAndMeal(Integer order_id){
        Integer deliver_id = JWTUtil.getCurrentUser().getUser_id();
        Orders orders = orderMapper.getOrdersByOrderId(order_id);
        // 自己是否重复接单
        if(Objects.equals(orders.getDeliver_id(), deliver_id)){
            throw new ResponseException(ResponseEnum.ORDER_ACCEPTED_MINE.getCode(), ResponseEnum.ORDER_ACCEPTED_MINE.getMsg());
        }
        // 订单是否已被别人接单
        if(orders.getDeliver_id() != null){
            throw new ResponseException(ResponseEnum.ORDER_ACCEPTED.getCode(), ResponseEnum.ORDER_ACCEPTED.getMsg());
        }

        if (Objects.equals(orders.getStatus(), OrderStatusEnum.COOKED.getCode())){
            orderMapper.updateOrderdelivery_id(deliver_id,order_id);
            orderMapper.updateOrderStatus(OrderStatusEnum.ACCEPTED.getCode(), order_id);
            return new UniversalResponse<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg());
        }else {
            throw new ResponseException(ResponseEnum.ORDER_STATE_ERROR.getCode(),ResponseEnum.ORDER_STATE_ERROR.getMsg());
        }
    }
    /**
     *送餐完毕
     *
     */
    @Override
    public UniversalResponse<?> delivery_meal(Integer order_id){
        Integer deliver_id = JWTUtil.getCurrentUser().getUser_id();
        Orders orders = orderMapper.getOrdersByOrderId(order_id);
        if(!Objects.equals(deliver_id, orders.getDeliver_id())){
            throw new ResponseException(ResponseEnum.USER_MATCH_ERROR.getCode(), ResponseEnum.USER_MATCH_ERROR.getMsg());
        }
        if (Objects.equals(orders.getStatus(), OrderStatusEnum.ACCEPTED.getCode())){
            orderMapper.updateOrderStatus(OrderStatusEnum.FINISHED.getCode(), orders.getOrder_id());
            return new UniversalResponse<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg());
        }else {
            throw new ResponseException(ResponseEnum.ORDER_STATE_ERROR.getCode(),ResponseEnum.ORDER_STATE_ERROR.getMsg());
        }
    }
}
