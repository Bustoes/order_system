package group.ordersystem.service;

import group.ordersystem.pojo.Menu;
import group.ordersystem.pojo.form.CommentForm;
import group.ordersystem.pojo.form.PostOrderForm;
import group.ordersystem.pojo.res.GetOrdersRes;
import group.ordersystem.util.response.UniversalResponse;

import java.util.List;

public interface CustomerService {
    UniversalResponse<List<Menu>> getMenu();

    UniversalResponse<List<GetOrdersRes>> getOrder();

    UniversalResponse<?> insertOrder(PostOrderForm postOrderForm);

    UniversalResponse<?> updateComment(CommentForm commentForm);

    UniversalResponse<?> deleteOrderByCustomer(Integer order_id);
}
