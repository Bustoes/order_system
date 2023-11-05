package group.ordersystem.controller;

import group.ordersystem.annotation.JWTPass;
import group.ordersystem.pojo.form.LoginForm;
import group.ordersystem.pojo.form.RegisterForm;
import group.ordersystem.pojo.res.TokenRes;
import group.ordersystem.service.CustomerService;
import group.ordersystem.util.response.UniversalResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/")
public class CommonController {
    @Resource
    private CustomerService customerService;

    @GetMapping
    @ResponseBody
    @JWTPass
    public UniversalResponse<Object> index() {
        return new UniversalResponse<>(1, "OK");
    }

    @PostMapping("/login")
    @ResponseBody
    @JWTPass
    public UniversalResponse<TokenRes> login(@RequestBody LoginForm loginForm) {
        return customerService.login(loginForm.getAccount(), loginForm.getPassword());
    }

    @PostMapping("/register")
    @ResponseBody
    @JWTPass
    public UniversalResponse<?> register(@RequestBody RegisterForm registerForm) {
        return customerService.register(registerForm);
    }
}
