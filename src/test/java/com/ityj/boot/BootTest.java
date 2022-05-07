package com.ityj.boot;

import com.ityj.boot.entity.User;
import com.ityj.boot.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.Instant;

@SpringBootTest
@Slf4j
public class BootTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisConnectionFactory connectionFactory;


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

    @Test
    public void testRedisTemplate() {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("test", Instant.now().toString());
        System.out.println("end.......");

        Object test = valueOperations.get("test");
        System.out.println("test = " + test);
    }

    @Test
    public void testStringRedisTemplate() {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set("key_date", Instant.now().toString());
        System.out.println("end.......");

        Object test = ops.get("key_date");
        System.out.println("key_date = " + test);
    }

    @Test
    public void testConnector() {
        System.out.println(connectionFactory.getClass());
    }
}
