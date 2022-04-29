package com.ityj.boot.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 进行业务逻辑判断，权限控制等操作
        Object key = request.getAttribute("key");
        Object sessionKey = request.getSession().getAttribute("sessionKey");
        if (key != null || sessionKey != null) {
            // 用户校验，有权限或者已经登录，继续执行（放行）
            return true;
        }

        // 认证未通过，跳转到登录页面
        request.setAttribute("msg", "请先进行登录操作！");
        request.getRequestDispatcher("/login").forward(request, response);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle...{}", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("afterCompletion.....");
    }
}
