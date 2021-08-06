package com.niuge.demos.redis3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {

  @Bean
  public RedisTemplate<Object, Object> redisTemplate(@Autowired RedisConnectionFactory redisConnectionFactory) {
    System.out.printf(redisConnectionFactory.toString());
    // 这里spring默认注入的是LettuceConnectionFactory，不是Jedis了。

    // 非hash object
    RedisTemplate<Object, Object> template = new RedisTemplate<>(); // <String, Object>, 表示k-v
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new StringRedisSerializer());

    // hash object
    template.setHashKeySerializer(new StringRedisSerializer());
    template.setHashValueSerializer(new StringRedisSerializer());


    template.setConnectionFactory(redisConnectionFactory);
    // 这里必须要设置connectionFactory,否则报factory找不到的错误.
    return template;
  }
}
