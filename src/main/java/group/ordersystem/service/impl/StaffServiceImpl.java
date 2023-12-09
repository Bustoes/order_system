package group.ordersystem.service.impl;

import group.ordersystem.mapper.MenuMapper;
import group.ordersystem.mapper.MenuOrderMapper;
import group.ordersystem.mapper.OrderMapper;
import group.ordersystem.mapper.UserMapper;
import group.ordersystem.pojo.Menu;
import group.ordersystem.pojo.Menu_Order;
import group.ordersystem.pojo.Orders;
import group.ordersystem.pojo.form.*;
import group.ordersystem.pojo.res.*;
import group.ordersystem.pojo.res.OrderSellRes;
import group.ordersystem.service.StaffService;
import group.ordersystem.util.enums.ResponseEnum;
import group.ordersystem.util.response.UniversalResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
        return new UniversalResponse<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg(), menuMapper.updateMealPriceByMealId(updateMealForm.getMeal_id(), updateMealForm.getNew_price()));
    }

    @Override
    public UniversalResponse<?> insertMeal(InsertMealForm insertMealForm) {
        return new UniversalResponse<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg(), menuMapper.insertMealInformation(insertMealForm.getMeal_id(), insertMealForm.getMeal_name(), insertMealForm.getMeal_price(), insertMealForm.getType()));
    }

    @Override
    public UniversalResponse<?> deleteMeal(DeleteMealForm deleteMealForm) {
        return new UniversalResponse<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg(), menuMapper.deleteMealByMealId(deleteMealForm.getMeal_id()));
    }

    @Override
    public UniversalResponse<List<OrderSellRes>> getMenuSale() {
        List<OrderSellRes> orderFormsList = new ArrayList<>();
        List<Menu_Order> menuOrderList = menuOrderMapper.selectMenuOrder();
        for(int i=1; i<=15; i++){
            OrderSellRes orderForm = new OrderSellRes();
            orderForm.setMeal_id(i);
            orderForm.setNum_order(0);
            orderFormsList.add(orderForm);
        }
        for (Menu_Order o : menuOrderList){
            Integer Num_order = orderFormsList.get(o.getMeal_id()-1).getNum_order();
            orderFormsList.get(o.getMeal_id()-1).setNum_order(Num_order+1);
        }
        return new UniversalResponse<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg(), orderFormsList);
    }

    @Override
    public UniversalResponse<List<Menu_Order>> getMenuOrder(){
        return new UniversalResponse<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg(), menuOrderMapper.selectMenuOrder());
    }

    @Override
    public UniversalResponse<List<Orders>> acceptOrder(UpdateStatusForm updateStatusForm) {
        orderMapper.acceptOrder(updateStatusForm.getOrder_id());
        return new UniversalResponse<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg(), orderMapper.selectOrder());
    }
}
