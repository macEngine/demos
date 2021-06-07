package com.niuge.demos.redis3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfiguration {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(@Autowired RedisConnectionFactory redisConnectionFactory) {
        System.out.printf(redisConnectionFactory.toString());
        // 这里spring默认的注入的是LettuceConnectionFactory，不是jedis了。
        // 这种自动注入的方式，更简单。

        RedisTemplate<String, Object> template = new RedisTemplate<>(); // <String, Object>, 表示k-v

        template.setConnectionFactory(redisConnectionFactory);
        // 这里必须要设置connectionFactory,否则报factory找不到的错误.
        return template;
    }
}
