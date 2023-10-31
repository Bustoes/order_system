package group.ordersystem.service;

import group.ordersystem.pojo.Menu;
import group.ordersystem.pojo.form.PostOrderForm;
import group.ordersystem.pojo.res.GetOrdersRes;
import group.ordersystem.pojo.res.TokenRes;
import group.ordersystem.pojo.form.RegisterForm;
import group.ordersystem.util.response.UniversalResponse;

import java.util.List;

public interface CustomerService {
    UniversalResponse<TokenRes> login(String account, String password);

    UniversalResponse<?> register(RegisterForm registerForm);

    UniversalResponse<List<Menu>> getMenu();

    UniversalResponse<List<GetOrdersRes>> getOrder();

    UniversalResponse<?> insertOrder(PostOrderForm postOrderForm);
}
