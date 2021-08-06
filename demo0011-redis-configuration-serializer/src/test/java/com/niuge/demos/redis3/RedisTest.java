package com.niuge.demos.redis3;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.niuge.demos.redis3.RedisUtil.zsReverseRange;
import static com.niuge.demos.redis3.RedisUtil.zsadd;


@SuppressWarnings("all")
@SpringBootTest
public class RedisTest {
  // 210601运行成功一次.
  @Test
  public void test() {
    zsadd("key1", "value1", 1);
    zsadd("key1", "value2", 2);
    zsadd("key1", "value3", 3);
    zsadd("key1", "value4", 4);
    zsadd("key1", "value5", 5);

    zsReverseRange("key1", 0, 2); // 直接在代码里print结果了，不需要返回值.
    // 在不设置Serializer的情况下，
    // RedisTemplate默认使用JdkSerializationRedisSerializer序列类进行序列化，
    // 存入数据先序列化成字节数组然后再存入Redis数据库:
    //     zrange "\xac\xed\x00\x05t\x00\x04key1" 0 -1
    //     1) "\xac\xed\x00\x05t\x00\x06value1"
    //     2) "\xac\xed\x00\x05t\x00\x06value2"
    //     3) "\xac\xed\x00\x05t\x00\x06value3"
    //     4) "\xac\xed\x00\x05t\x00\x06value4"
    //     5) "\xac\xed\x00\x05t\x00\x06value5"

    // zsadd和zsReverseRange对应setKeySerializer和setValueSerializer
    // RedisTemplate如果不设置，存字节数组；如果设置StringRedisSerializer，存肉眼可读的（显然我们都想用肉眼可读的）.
  }
}
