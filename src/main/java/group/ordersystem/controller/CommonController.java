package group.ordersystem.controller;

import group.ordersystem.pojo.form.LoginForm;
import group.ordersystem.pojo.form.RegisterForm;
import group.ordersystem.pojo.form.TokenRes;
import group.ordersystem.service.UserService;
import group.ordersystem.util.response.UniversalResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/")
public class CommonController {
    @Resource
    private UserService userService;

    @GetMapping
    @ResponseBody
    public UniversalResponse<Object> index() {
        return new UniversalResponse<>(1, "OK");
    }

    @PostMapping("/login")
    @ResponseBody
    public UniversalResponse<TokenRes> login(@RequestBody LoginForm loginForm) {
        return userService.login(loginForm.getAccount(), loginForm.getPassword());
    }

    @PostMapping("/register")
    @ResponseBody
    public UniversalResponse<?> register(@RequestBody RegisterForm registerForm) {
        return userService.register(registerForm);
    }
}
