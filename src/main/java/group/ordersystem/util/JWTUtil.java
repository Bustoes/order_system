package group.ordersystem.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import group.ordersystem.mapper.UserMapper;
import group.ordersystem.pojo.User;
import group.ordersystem.util.response.ResponseException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Component
public class JWTUtil {
    @Resource
    private UserMapper userMapper;

    private static UserMapper staticUserMapper;

    public static String secure = "OrderSystem密钥";

    @PostConstruct
    private void initStaticUserDAO() {
        staticUserMapper = userMapper;
    }

    public static String createToken(String account) {
        return JWT.create().withAudience(account)
                .sign(Algorithm.HMAC256(JWTUtil.secure));
    }

    public static User getCurrentUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");
        if (Strings.isNotBlank(token)) {
            return staticUserMapper.getUserByAccount(JWT.decode(token).getAudience().get(0));
        }
        throw new ResponseException(401, "未持有token");
    }
}
