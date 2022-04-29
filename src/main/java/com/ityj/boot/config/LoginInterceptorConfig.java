package com.ityj.boot.config;

import com.ityj.boot.interceptor.LoginInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 登录拦截：
 *  1. 编写好拦截器及其业务逻辑，实现HandlerInterceptor接口
 *  2. 将自定义的拦截器放入容器中
 *  3. 配置好拦截及放行的请求
 */
//@Configuration
public class LoginInterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")   //拦截所有请求包括静态资源
                .excludePathPatterns("/", "/login", "/css/**", "/js/**", "/fonts/**", "/images/**"); // 放行静态资源
    }

}
