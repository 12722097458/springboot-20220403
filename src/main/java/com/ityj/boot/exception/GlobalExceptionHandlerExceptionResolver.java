package com.ityj.boot.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandlerExceptionResolver {

    /**
     * 项目启动过程中会加载当前方法，读取ExceptionHandler注解，并将handleMathException能处理能异常类型
     * 绑定到ExceptionHandlerExceptionResolver中
     * @param e
     * @return
     */
    @ExceptionHandler(value = {ArithmeticException.class, NumberFormatException.class})
    public ModelAndView handleMathException(Exception e) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("error/number_error");
        mv.addObject("msg", e.toString());
        return mv;
    }

}
