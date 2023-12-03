package group.ordersystem.service;

import group.ordersystem.pojo.Menu;
import group.ordersystem.pojo.Menu_Order;
import group.ordersystem.pojo.form.*;
import group.ordersystem.pojo.res.GetOrdersRes;
import group.ordersystem.util.response.UniversalResponse;

import java.util.List;

public interface StaffService {
    UniversalResponse<List<Menu>> getMenu();

    UniversalResponse<?> updateMeal(UpdateMealForm updateMealForm);

    UniversalResponse<?> insertMeal(InsertMealForm insertMealForm);

    UniversalResponse<?> deleteMeal(DeleteMealForm deleteMealForm);

    UniversalResponse<List<Menu_Order>> getMenuOrder();

//    UniversalResponse<?> acceptOrder();
//
//    UniversalResponse<?> requestDelivery();
}
