package com.ityj.boot.controller;

import com.ityj.boot.entity.Car;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HelloController {

    @Autowired
    private Car car;

    @GetMapping(path = "/hello")
    public String sayHello() {
        log.info(car.toString());
        return "Hello Spring Boot!";
    }
}
