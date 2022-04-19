package com.ityj.boot.controller;

import com.ityj.boot.entity.Car;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class ParametersTestController {


    @GetMapping("/person/{id}/{name}")
    public Map<String, Object> getRequest(@PathVariable("id") String id,
                                          @PathVariable("name") String personName,
                                          @PathVariable Map<String, Object> map,
                                          @RequestParam("age") Integer age,
                                          @RequestHeader("User-Agent") String userAgent,
                                          @CookieValue("Idea-7e7a18c1") String cookieIde,
                                          @CookieValue("Idea-7e7a18c1") Cookie cookie) {
        Map<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("personName", personName);
        result.put("age", age);
        result.put("userAgent", userAgent);
        result.put("cookieIde", cookieIde);

        log.info("map = {}", map.toString());
        log.info("cookie.key = {}; cookie.value = {}", cookie.getName(), cookie.getValue());

        return result;
    }

    @PostMapping(path = "/saveUserInfo")
    public Map<String, Object> saveUserInfo(@RequestBody String content) {
        Map<String, Object> result = new HashMap<>();
        result.put("content", URLDecoder.decode(content, StandardCharsets.UTF_8));

        return result;
    }

    @PostMapping(path = "/saveCarInfo")
    public Car saveCarInfo(Car car) {
        return car;
    }
}
