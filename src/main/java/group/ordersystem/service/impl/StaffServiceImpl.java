package group.ordersystem.service.impl;

import group.ordersystem.mapper.MenuMapper;
import group.ordersystem.mapper.MenuOrderMapper;
import group.ordersystem.mapper.OrderMapper;
import group.ordersystem.mapper.UserMapper;
import group.ordersystem.pojo.Menu;
import group.ordersystem.pojo.Menu_Order;
import group.ordersystem.pojo.Orders;
import group.ordersystem.pojo.User;
import group.ordersystem.pojo.form.*;
import group.ordersystem.pojo.res.GetOrdersRes;
import group.ordersystem.service.StaffService;
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
    public UniversalResponse<List<Menu_Order>> getMenuOrder() {
        return new UniversalResponse<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg(), menuOrderMapper.selectMenuOrder());
    }

//    @Override
//    public UniversalResponse<?> acceptOrder() {
//        return null;
//    }
//
//    @Override
//    public UniversalResponse<?> requestDelivery() {
//        return null;
//    }
}
