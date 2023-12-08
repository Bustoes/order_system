package group.ordersystem.service;

import group.ordersystem.pojo.Menu;
import group.ordersystem.pojo.Orders;
import group.ordersystem.pojo.form.*;
import group.ordersystem.pojo.res.*;
import group.ordersystem.pojo.res.OrderSellRes;
import group.ordersystem.util.response.UniversalResponse;

import java.util.List;

public interface StaffService {
    UniversalResponse<List<Menu>> getMenu();

    UniversalResponse<?> updateMeal(UpdateMealForm updateMealForm);

    UniversalResponse<?> insertMeal(InsertMealForm insertMealForm);

    UniversalResponse<?> deleteMeal(DeleteMealForm deleteMealForm);

    UniversalResponse<List<OrderSellRes>> getMenuOrder();

    UniversalResponse<List<Orders>> acceptOrder(UpdateStatusForm updateStatusForm);
}
