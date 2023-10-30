package group.ordersystem.service.impl;

import group.ordersystem.mapper.UserMapper;
import group.ordersystem.pojo.User;
import group.ordersystem.pojo.res.TokenRes;
import group.ordersystem.pojo.form.RegisterForm;
import group.ordersystem.service.CustomerService;
import group.ordersystem.util.JWTUtil;
import group.ordersystem.util.enums.ResponseEnum;
import group.ordersystem.util.response.ResponseException;
import group.ordersystem.util.response.UniversalResponse;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Resource
    private UserMapper userMapper;

    /**
     * 用户登录
     * @param account 账户名称
     * @param password 账户密码
     * @return
     */
    @Override
    public UniversalResponse<TokenRes> login(String account, String password) {
        User user = userMapper.getUserByAccount(account);
        if (user == null) {
            throw new ResponseException(ResponseEnum.USER_LOGIN_ERROR.getCode(), ResponseEnum.USER_LOGIN_ERROR.getMsg());
        }
        if (!user.getPassword().equals(password)) {
            throw new ResponseException(ResponseEnum.USER_LOGIN_ERROR.getCode(), ResponseEnum.USER_LOGIN_ERROR.getMsg());
        }
        // 用户名、密码正确
        // 生成token
        String token;
        token = JWTUtil.createToken(account);
        return new UniversalResponse<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg(), new TokenRes(token));
    }

    /**
     * 用户注册
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
