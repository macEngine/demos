package com.niuge.demos.day7;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfiguration {

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        // host, port都使用了默认值.
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>(); // <String, Object>, 表示k-v
        template.setConnectionFactory(jedisConnectionFactory());
        // 这里必须要设置connectionFactory,否则报factory找不到的错误.
        return template;
    }
}
