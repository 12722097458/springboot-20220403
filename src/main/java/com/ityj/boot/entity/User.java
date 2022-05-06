package com.ityj.boot.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Integer id;
    private String userId;
    private String userName;
    private String phone;
    private Integer lanId;
    private Integer regionId;
    private Date createTime;
}
