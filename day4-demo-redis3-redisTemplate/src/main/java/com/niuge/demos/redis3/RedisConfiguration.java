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
    // 这里spring默认注入的是LettuceConnectionFactory，不是Jedis了。

    // 首先 Redis 的一些配置都是依赖于 RedisAutoConfiguration，这个类是在 spring-boot-autoconfigure 里面.
    // 在 RedisAutoConfiguration 中，首先 import 的是 LettuceConnectionConfiguration，
    // 所以最后才会导致在 SpringBoot2.x 的时候，默认加载的 redisConnectionFactory 是 LettuceConnectionFactory。
    // https://somersames.xyz/2020/01/05/SpringBoot2-x%E6%98%AF%E6%80%8E%E6%A0%B7%E5%8F%AA%E5%88%9D%E5%A7%8B%E5%8C%96LettuceConnectionFactory%E7%9A%84%E5%91%A2/

    RedisTemplate<String, Object> template = new RedisTemplate<>(); // <String, Object>, 表示k-v

    template.setConnectionFactory(redisConnectionFactory);
    // 这里必须要设置connectionFactory,否则报factory找不到的错误.
    return template;
  }
}
