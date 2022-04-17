package com.ityj.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class RequestController {

    @GetMapping("/goto")
    public String gotoPage(HttpServletRequest request) {
        request.setAttribute("msg", "信息");
        return "forward:/success";    // 请求转发到 /success请求， 服务期间， 地址不变，一次请求一次相应
    }

    @GetMapping("/params")
    public String params(Map<String, Object> map,
                         Model model,
                         RedirectAttributes attribute,
                         HttpServletRequest request,
                         HttpServletResponse response) {
        map.put("map", "HelloMap");
        model.addAttribute("model", "HelloModel");
        attribute.addAttribute("redirectAttributes", "HelloRedirectAttributes");
        request.setAttribute("request", "HelloRequest");
        response.addCookie(new Cookie("k", "v-"));
        return "forward:/success";    // 请求转发到 /success请求， 服务期间， 地址不变，一次请求一次相应
    }

    @GetMapping("/success")
    @ResponseBody
    public Map<String, Object> success(@RequestAttribute(value = "msg", required = false) String message,
                                       HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        result.put("anno_message", message);
        result.put("request_message", request.getAttribute("msg"));
        result.put("map", request.getAttribute("map"));
        result.put("model", request.getAttribute("model"));
        result.put("redirectAttributes", request.getAttribute("redirectAttributes"));
        result.put("request", request.getAttribute("request"));
        return result;
    }

}
