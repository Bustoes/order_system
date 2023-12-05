package group.ordersystem.service;

import group.ordersystem.pojo.form.LoginForm;
import group.ordersystem.pojo.form.RegisterForm;
import group.ordersystem.pojo.res.TokenRes;
import group.ordersystem.util.response.UniversalResponse;
public interface CommonService {
    UniversalResponse<TokenRes> login(LoginForm loginForm);

    UniversalResponse<?> register(RegisterForm registerForm);
}
