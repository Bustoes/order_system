package group.ordersystem.service;

import group.ordersystem.pojo.form.TokenRes;
import group.ordersystem.pojo.form.RegisterForm;
import group.ordersystem.util.response.UniversalResponse;

public interface UserService {
    UniversalResponse<TokenRes> login(String account, String password);

    UniversalResponse<?> register(RegisterForm registerForm);
}
