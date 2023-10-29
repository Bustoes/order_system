package group.ordersystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORSConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // 设置允许访问的源
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 设置允许的HTTP请求方法
                .allowedHeaders("*")
                .exposedHeaders("token")
                .allowCredentials(true); // 设置是否允许发送身份验证信息
    }
}
