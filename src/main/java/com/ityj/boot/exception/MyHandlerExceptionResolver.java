package com.ityj.boot.exception;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Order   // 默认最低优先级Ordered.LOWEST_PRECEDENCE，可以调整。
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 具体的异常处理逻辑可以在这里处理。比如支持什么异常，参数怎么处理
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg", ex.toString());
        mv.setViewName("error/handler_resolver_error_page");
        return mv;
    }
}
