package group.ordersystem.service;

import group.ordersystem.pojo.form.RegisterForm;
import group.ordersystem.pojo.res.TokenRes;
import group.ordersystem.util.response.UniversalResponse;
public interface CommonService {
    UniversalResponse<TokenRes> login(String account, String password,int identity);

    UniversalResponse<?> register(RegisterForm registerForm);
}
