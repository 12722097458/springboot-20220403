package com.ityj.boot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("test_user")   //默认规定tableName=类名
public class User {
    private Integer id;
    private String userId;
    private String userName;
    private String phone;
    private Integer lanId;
    private Integer regionId;
    private Date createTime;
}
