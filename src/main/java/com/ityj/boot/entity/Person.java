package com.ityj.boot.entity;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class Person extends BaseEntity {

    private String name;
    private int age;

}
