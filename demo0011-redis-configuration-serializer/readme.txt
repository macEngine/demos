1. 主要是验证下:
    // zsadd和zsReverseRange对应setKeySerializer和setValueSerializer
    // RedisTemplate如果不设置，存字节数组；如果设置StringRedisSerializer，存肉眼可读的（显然我们都想用肉眼可读的）.