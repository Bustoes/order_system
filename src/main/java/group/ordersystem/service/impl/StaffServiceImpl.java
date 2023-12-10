package group.ordersystem.service.impl;

import group.ordersystem.mapper.MenuMapper;
import group.ordersystem.mapper.MenuOrderMapper;
import group.ordersystem.mapper.OrderMapper;
import group.ordersystem.mapper.UserMapper;
import group.ordersystem.pojo.Menu;
import group.ordersystem.pojo.Menu_Order;
import group.ordersystem.pojo.Orders;
import group.ordersystem.pojo.form.*;
import group.ordersystem.pojo.res.GetOrdersWithDRes;
import group.ordersystem.pojo.res.OrderSellRes;
import group.ordersystem.service.StaffService;
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
public class StaffServiceImpl implements StaffService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private MenuOrderMapper menuOrderMapper;

    @Override
    public UniversalResponse<List<Menu>> getMenu() {
        return new UniversalResponse<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg(), menuMapper.selectMenu());
    }

    @Override
    public UniversalResponse<?> updateMeal(UpdateMealForm updateMealForm) {
        menuMapper.updateMealPriceByMealId(updateMealForm.getMeal_id(), updateMealForm.getNew_price());
        return new UniversalResponse<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg(), null);
    }

    @Override
    public UniversalResponse<?> insertMeal(InsertMealForm insertMealForm) {
        menuMapper.insertMealInformation(insertMealForm.getMeal_name(), insertMealForm.getMeal_price(), insertMealForm.getType(), insertMealForm.getImage_path());
        return new UniversalResponse<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg(), null);
    }

    @Override
    public UniversalResponse<?> deleteMeal(DeleteMealForm deleteMealForm) {
        try {
            menuMapper.deleteMealByMealId(deleteMealForm.getMeal_id());
        } catch (Exception e) {
            System.out.println("因数据库外键，不可删除");
            throw new ResponseException(ResponseEnum.SERVER_BUSY_SQL.getCode(), ResponseEnum.SERVER_BUSY_SQL.getMsg());
        }
        return new UniversalResponse<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg(), null);
    }

    @Override
    public UniversalResponse<List<OrderSellRes>> getMenuSale() {
        List<OrderSellRes> orderSellList = new ArrayList<>();
        List<Menu_Order> menuOrderList = menuOrderMapper.selectMenuOrder();
        List<Menu> menuList = menuMapper.selectMenu();
        for (Menu meal : menuList) {      // 根据menu中meal的数量创建orderFormList
            OrderSellRes orderForm = new OrderSellRes();
            orderForm.setMeal_name(meal.getMeal_name());
            orderForm.setNum_order(0);
            orderSellList.add(orderForm);
        }

        for (Menu_Order o : menuOrderList) {
            Integer numOrder;

            String nameByMealId = menuMapper.getNameByMealId(o.getMeal_id());
            for (OrderSellRes obj : orderSellList) {
                if (Objects.equals(obj.getMeal_name(), nameByMealId)) {
                    numOrder = obj.getNum_order();
                    numOrder++;
                    obj.setNum_order(numOrder);
                }
            }
        }
        return new UniversalResponse<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg(), orderSellList);
    }

    @Override
    public UniversalResponse<List<GetOrdersWithDRes>> getMenuOrder() {
        List<Orders> ordersList = orderMapper.selectOrders();
        List<GetOrdersWithDRes> ordersWithDResList = new ArrayList<>();
        for (Orders order : ordersList) {
            GetOrdersWithDRes orderWithDRes = new GetOrdersWithDRes();
            orderWithDRes.setOrder_id(order.getOrder_id());
            orderWithDRes.setDestination(order.getDestination());
            orderWithDRes.setStatus(order.getStatus());
            orderWithDRes.setOrder_price(order.getOrder_price());
            orderWithDRes.setOrder_comment(order.getOrder_comment());

            if (order.getDeliver_id() != null) {
                String deliveryName = userMapper.getUserNameById(order.getDeliver_id());
                orderWithDRes.setDelivery_name(deliveryName);
            }

            ordersWithDResList.add(orderWithDRes);
        }

        return new UniversalResponse<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg(), ordersWithDResList);
    }

    @Override
    public UniversalResponse<?> acceptOrder(AcceptForm acceptForm) {
        //TODO: 添加状态校验
        Orders orders = orderMapper.getOrdersByOrderId(acceptForm.getOrder_id());
        if (Objects.equals(orders.getStatus(), OrderStatusEnum.CREATED.getCode())) {
            orderMapper.acceptOrder(acceptForm.getOrder_id());
        } else {
            throw new ResponseException(ResponseEnum.ORDER_STATE_ERROR.getCode(), ResponseEnum.ORDER_STATE_ERROR.getMsg());
        }
        return new UniversalResponse<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg(), null);
    }
}
