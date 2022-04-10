package com.ityj.boot.controller;

import com.ityj.boot.entity.Car;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
public class HelloController {

    @Autowired
    private Car car;

    @Value("${data.str}")
    private String str;

    @Value("${data.number}")
    private double num;

    @Value("${data.boolean}")
    private boolean flag;

    @Value("${data.list}")
    private List<String> list;  // Spring默认情况下会以','进行分割，转换成对应的数组或List。
    @Value("${data.list}")
    private String[] arr;

    @Value("#{'${data.list}'.split(',')}")   // 数组或list接收都可以
    private List<String> list2;
    @Value("#{'${data.list}'.split(',')}")
    private String[] arr2;

    @GetMapping(path = "/hello")
    public String sayHello() {
        log.info(car.toString());
        return "Hello Spring Boot!";
    }

    @GetMapping(path = "/data")
    public String getData() {
        log.info("str = {}", str);
        log.info("num = {}", num);
        log.info("flag = {}", flag);
        log.info("list = {}", list);
        log.info("arr = {}", Arrays.toString(arr));
        return "Success";
    }
}
