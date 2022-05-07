package com.ityj.boot.config;

import com.ityj.boot.interceptor.RequestUriCountInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RequestUriCountInterceptorConfig implements WebMvcConfigurer {

    /*
    *   Filter、Interceptor几乎同样的功能，区别是什么？
    *   1、Filter是Servlet的原生组件。好处：脱离Spring也能使用。
    *   2、Interceptor是Spring定义好的接口。好处：可以使用Spring特有的性能，比如Autowired
    *
    * */

    @Autowired
    private RequestUriCountInterceptor requestUriCountInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestUriCountInterceptor)
                .addPathPatterns("/**")   //拦截所有请求包括静态资源
                .excludePathPatterns("/", "/login", "/css/**", "/js/**", "/fonts/**", "/images/**"); // 放行静态资源
        }
}
