package com.ityj.boot;

import com.ityj.boot.entity.User;
import com.ityj.boot.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
@Slf4j
public class BootTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testJDBCTemplate() {
        Long count = jdbcTemplate.queryForObject("select count(1) from test_user", Long.class);
        log.info("数据条数为：{}", count);
    }

    @Test
    public void testMybatisPlus() {
        User user = userMapper.selectById(32423);
        log.info("User info: {}", user);
    }
}
