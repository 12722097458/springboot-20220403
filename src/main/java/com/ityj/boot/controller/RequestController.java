package com.ityj.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class RequestController {

    @GetMapping("/goto")
    public String gotoPage(HttpServletRequest request) {
        request.setAttribute("msg", "信息");
        return "forward:/success";    // 请求转发到 /success请求， 服务期间， 地址不变，一次请求一次相应
    }

    @GetMapping("/success")
    @ResponseBody
    public Map<String, Object> success(@RequestAttribute("msg") String message,
                                       HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        result.put("anno_message", message);
        result.put("request_message", request.getAttribute("msg"));
        return result;
    }

}
