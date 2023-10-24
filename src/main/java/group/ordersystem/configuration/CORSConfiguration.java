package group.ordersystem.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORSConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8800") // 设置允许访问的源
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 设置允许的HTTP请求方法
                .allowCredentials(true); // 设置是否允许发送身份验证信息
    }
}
