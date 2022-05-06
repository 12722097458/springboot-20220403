package com.ityj.boot.controller.data;

import com.ityj.boot.entity.User;
import com.ityj.boot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserService userService;

    @GetMapping("/count")
    public Long getUserCount() {
        log.info(jdbcTemplate.getDataSource().toString());
        return jdbcTemplate.queryForObject("select count(1) from test_user", Long.class);
    }
    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable("id") Integer userId) {
        return userService.getUserById(userId);
    }

}
