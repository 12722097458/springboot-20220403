package com.ityj.boot.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "mycar")
// @Component 第一种注入容器的方式（ConfigurationProperties）
public class Car {
    private String brand;
    private double price;
}
