1. 主要是验证下:
    Springboot默认使用LettuceConnectionFactory作为redisConnectionFactory.
    结论是: 确实是用的lettuce，Jedis已经被抛弃.