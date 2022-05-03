package com.ityj.boot.servlet;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Collectors;
import java.util.stream.Stream;

// 可以通过这个配置类，来应用自己定义的WebServlet/Filter/Listener
// 同时不需要在启动类上标注@ServletComponentScan(basePackages = {"com.ityj.boot.servlet"})注解
@Configuration
public class MyServletRegistrationConfig {

    // 替代@WebServlet(urlPatterns = {"/myservlet/t1", "/myservlet/t2"})
    @Bean
    public ServletRegistrationBean myServlet() {
        MyServlet myServlet = new MyServlet();
        return new ServletRegistrationBean(myServlet, "/myservlet/t1", "/myservlet/t2", "/bb/*");
    }

    // 替代@WebFilter(urlPatterns = {"/myservlet/t1", "/myservlet/t2"})
    @Bean
    public FilterRegistrationBean filter() {
        MyServletFilter myServletFilter = new MyServletFilter();
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(myServletFilter);
        // 1. myServletFilter可以直接针对myServlet，放入bean
        filterRegistrationBean.addServletNames("myServlet");
        // 2. 同时也可以指定过滤的url  单个*指代所有是servlet的用法，双星**是spring的写法
        filterRegistrationBean.setUrlPatterns(Stream.of("/aa/*").collect(Collectors.toList()));

        return filterRegistrationBean;
    }

    // 替代 @WebListener
    @Bean
    public ServletListenerRegistrationBean listenerRegistration() {
        MyWebListener myWebListener = new MyWebListener();
        return new ServletListenerRegistrationBean(myWebListener);
    }

}
