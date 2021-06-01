package com.niuge.demos.redis;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.niuge.demos.redis.RedisUtil.*;

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
    }
}
