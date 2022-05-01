package com.ityj.boot.controller;

import com.ityj.boot.entity.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/req")
public class RequestController {

    @GetMapping("/goto")
    public String gotoPage(HttpServletRequest request) {
        request.setAttribute("msg", "信息");
        // 如果类名上加了@RequestMapping("/req")，return "forward:R-C.jpg"; 这里forward的路径变成了  /req/R-C.jpg ***
        // "forward:/R-C.jpg" 这样可以正常转发到R-C.jpg
        return "forward:/R-C.jpg";    // 请求转发到 R-C.jpg资源， 是在服务器间进行的。url地址不变，服务器间进行了两次请求：1. /req/goto 2. /R-C.jpg
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

    /*
    *   测试返回值解析器如何将对象转换成Json返回到浏览器
    *   org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter.writeInternal
    *
    *
    *   2.现在需求是：
    *       （1）浏览器请求直接返回xml                     [application/xml]    xmlConverter
    *       （2）如果是ajax请求，返回json                  [application/json]   jsonConverter
    *       （3）如果是第三方请求，返回自定义类型的数据    [application/x-yj]   yjConverter
    *     实现原理是：根据Accept的参数不同，返回不同类型数据。自定义的参数需要通过自定义的converter来处理
    *
    *      步骤：
    *       （1）添加自定义的MessageConverter进入系统层
    *       （2）系统层会统计能够支持返回哪些类型的数据
    *       （3）根据内容协商，返回出不同类型数据
    *
    * */
    @GetMapping("/person")
    @ResponseBody
    public Person getPerson() {
        Person person = new Person();
        person.setAge(11);
        person.setName("杰克");
        return person;
    }

    /**
     *  returnValueHandlers： HttpEntityMethodProcessor
     *   @GetMapping没有指定path，会默认组装项目名称和类上标注的RequestMapping路径
     * @return
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> jsonReturnTest() {
        Map<String, Object> data = new HashMap<>();
        data.put("key", "G60188888888");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

}
