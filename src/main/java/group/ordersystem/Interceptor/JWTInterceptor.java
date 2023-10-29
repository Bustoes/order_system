package group.ordersystem.Interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import group.ordersystem.mapper.UserMapper;
import group.ordersystem.pojo.User;
import group.ordersystem.util.JWTUtil;
import group.ordersystem.util.enums.ResponseEnum;
import group.ordersystem.util.response.ResponseException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JWTInterceptor implements HandlerInterceptor {
    @Resource
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            // 之前一堆bug就是因为预检机制
            System.out.println("OPTIONS放行");
            return true;
        }

        String token = request.getHeader("token");
        if (Strings.isBlank(token)) {
            throw new ResponseException(ResponseEnum.USER_TOKEN_ERROR.getCode(), ResponseEnum.USER_TOKEN_ERROR.getMsg());
        }

        String account;
        try {
            account = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException e) {
            throw new ResponseException(ResponseEnum.USER_TOKEN_ERROR.getCode(), ResponseEnum.USER_TOKEN_ERROR.getMsg());
        }

        User user = userMapper.getUserByAccount(account);
        if (user == null) {
            throw new ResponseException(ResponseEnum.USER_TOKEN_ERROR.getCode(), ResponseEnum.USER_TOKEN_ERROR.getMsg());
        }

        // 验证签名
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(JWTUtil.secure)).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTDecodeException e) {
            throw new ResponseException(ResponseEnum.USER_TOKEN_ERROR.getCode(), ResponseEnum.USER_TOKEN_ERROR.getMsg());
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
