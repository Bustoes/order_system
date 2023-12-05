package group.ordersystem.service.impl;

import group.ordersystem.mapper.MenuMapper;
import group.ordersystem.mapper.MenuOrderMapper;
import group.ordersystem.mapper.OrderMapper;
import group.ordersystem.mapper.UserMapper;
import group.ordersystem.pojo.User;
import group.ordersystem.pojo.form.LoginForm;
import group.ordersystem.pojo.form.RegisterForm;
import group.ordersystem.pojo.res.TokenRes;
import group.ordersystem.service.CommonService;
import group.ordersystem.util.JWTUtil;
import group.ordersystem.util.enums.ResponseEnum;
import group.ordersystem.util.response.ResponseException;
import group.ordersystem.util.response.UniversalResponse;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CommonServicelmpl implements CommonService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private MenuOrderMapper menuOrderMapper;
    /**
     * 用户登录
     *
     * @param loginForm 登录表单
     * @return
     */
    @Override
    public UniversalResponse<TokenRes> login(LoginForm loginForm) {
        User user = userMapper.getUserByAccount(loginForm.getAccount());
        if (user == null) {
            throw new ResponseException(ResponseEnum.USER_LOGIN_ERROR.getCode(), ResponseEnum.USER_LOGIN_ERROR.getMsg());
        }
        if (!user.getPassword().equals(loginForm.getPassword())) {
            throw new ResponseException(ResponseEnum.USER_LOGIN_ERROR.getCode(), ResponseEnum.USER_LOGIN_ERROR.getMsg());
        }
        // 用户名、密码正确
        // 生成token
        String token;
        token = JWTUtil.createToken(loginForm.getAccount());
        return new UniversalResponse<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg(), new TokenRes(token, user.getIdentity()));
    }

    /**
     * 用户注册
     *
     * @param registerForm
     * @return
     */
    @Override
    public UniversalResponse<?> register(RegisterForm registerForm) {
        User newUser = new User();
        newUser.setUser_id(null);
        newUser.setUser_name(registerForm.getUser_name());
        newUser.setIdentity(registerForm.getIdentity());
        newUser.setAccount(registerForm.getAccount());
        newUser.setPassword(registerForm.getPassword());

        if (Strings.isBlank(newUser.getUser_name()) || Strings.isBlank(newUser.getPassword()) || Strings.isBlank(newUser.getAccount())) {
            throw new ResponseException(ResponseEnum.PARAM_IS_INVALID.getCode(), ResponseEnum.PARAM_IS_INVALID.getMsg());
        }

        User user = userMapper.getUserByAccount(newUser.getAccount());
        if (user != null) {
            throw new ResponseException(ResponseEnum.USER_ACCOUNT_EXISTS.getCode(), ResponseEnum.USER_ACCOUNT_EXISTS.getMsg());
        }

        userMapper.insertUser(newUser);

        return new UniversalResponse<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg());
    }

}
