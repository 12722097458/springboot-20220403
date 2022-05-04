package com.ityj.boot.controller.data;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/count")
    public Long getUserCount() {
        log.info(jdbcTemplate.getDataSource().toString());
        return jdbcTemplate.queryForObject("select count(1) from test_user", Long.class);
    }
}
