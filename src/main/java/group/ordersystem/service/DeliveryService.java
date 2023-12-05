package group.ordersystem.service;

import group.ordersystem.pojo.res.GetOrdersRes;
import group.ordersystem.util.response.UniversalResponse;

import java.util.List;

public interface DeliveryService {
    UniversalResponse<List<GetOrdersRes>> getOrder();

    UniversalResponse<List<GetOrdersRes>> getmyOrder();

    UniversalResponse<?> takeOrderAndMeal(Integer order_id);
    UniversalResponse<?> delivery_meal(Integer order_id);
    UniversalResponse<?> delete_meal(Integer order_id);
}

