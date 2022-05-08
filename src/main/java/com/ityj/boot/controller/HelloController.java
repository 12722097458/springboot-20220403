package com.ityj.boot.controller;

import com.ityj.boot.entity.Car;
import com.ityj.boot.exception.IncorrectAgeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

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


    @Value("${path.primary:${path.secondary:/app/project/}}")
    private String pathValue;

    @Value("${JAVA_HOME}")
    private String javaHome;

    @GetMapping(path = "/hello")
    public String sayHello() {
        log.info(car.toString());
        return pathValue;
    }

    @GetMapping(path = "/data")
    public String getData() throws InterruptedException {
        TimeUnit.SECONDS.sleep(30);
        log.info("str = {}", str);
        log.info("num = {}", num);
        log.info("flag = {}", flag);
        log.info("list = {}", list);
        log.info("arr = {}", Arrays.toString(arr));
        return "Success";
    }

    @GetMapping(path = "/user")
    public String getMethod() {
        return "GET";
    }
    @PostMapping(path = "/user")
    public String postMethod() {
        return "POST";
    }
    @PutMapping(path = "/user")
    public String putMethod() {
        return "PUT";
    }
    @DeleteMapping(path = "/user")
    public String deleteMethod() {
        return "DELETE";
    }
    @PatchMapping(path = "/user")
    public String patchMethod() {
        return "PATCH";
    }

    @GetMapping(path = "/err")
    public Integer errorMethod(@RequestParam("age") Integer age) {
        if (age < 0) {
            throw new IncorrectAgeException();
        }
        Double.valueOf("sdf");
        return age;
    }

    @GetMapping(path = "/system")
    public String getSystemVariable() {
        return javaHome;
    }

}
