package com.ityj.boot.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import java.io.IOException;

@Slf4j
//@WebFilter(urlPatterns = {"/myservlet/t1", "/myservlet/t2"})
//@WebFilter(servletNames = {"myServlet"})
public class MyServletFilter extends HttpFilter {

    @Override
    public void init() throws ServletException {
        log.info("MyServletFilter.init()...");
        super.init();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("MyServletFilter.doFilter()...");
        super.doFilter(request, response, chain);
    }

    @Override
    public void destroy() {
        log.info("MyServletFilter.destroy()...");
        super.destroy();
    }
}
