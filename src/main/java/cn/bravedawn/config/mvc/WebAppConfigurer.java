package cn.bravedawn.config.mvc;

import cn.bravedawn.config.mvc.intercepter.AuthenticationHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/6/5 19:36
 *
 * 配置拦截器
 */
@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationHandlerInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public static HandlerInterceptor authenticationHandlerInterceptor(){
        return new AuthenticationHandlerInterceptor();
    }

}
