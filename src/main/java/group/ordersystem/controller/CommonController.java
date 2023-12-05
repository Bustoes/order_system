package group.ordersystem.controller;

import group.ordersystem.annotation.JWTPass;
import group.ordersystem.pojo.form.LoginForm;
import group.ordersystem.pojo.form.RegisterForm;
import group.ordersystem.pojo.res.TokenRes;
import group.ordersystem.service.CommonService;
import group.ordersystem.util.response.UniversalResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/")
public class CommonController {
    @Resource
    private CommonService commonService;

    @GetMapping
    @ResponseBody
    @JWTPass
    public UniversalResponse<Object> index() {
        return new UniversalResponse<>(1, "OK");
    }

    /**
     * 将登录，注册操作封装到CommonService（lmpl）接口之中
     * 在登录后，将token和identity传给前端，用以登录时区分跳转到不同用户界面
     * @param loginForm 登录表单
     * @return
     * Author ruo371
     */
    @PostMapping("/login")
    @ResponseBody
    @JWTPass
    public UniversalResponse<TokenRes> login(@RequestBody LoginForm loginForm) {
        return commonService.login(loginForm);
    }

    @PostMapping("/register")
    @ResponseBody
    @JWTPass
    public UniversalResponse<?> register(@RequestBody RegisterForm registerForm) {
        return commonService.register(registerForm);
    }
}
