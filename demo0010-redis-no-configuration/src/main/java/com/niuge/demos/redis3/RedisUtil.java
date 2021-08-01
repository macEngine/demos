package com.niuge.demos.redis3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RedisUtil {
    private static RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    public void init(RedisTemplate<Object, Object> redisTemplate) {
        RedisUtil.redisTemplate = redisTemplate;
    }

    public static void zsReverseRange(String key, long start, long end) {
        try {

            Set<ZSetOperations.TypedTuple<Object>> typedTuples = redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
            // 虽然这里是一个set，但是我感觉是有序的。
            for (ZSetOperations.TypedTuple<Object> element : typedTuples) {
                System.out.println(element.getValue() + "            " + element.getScore());
            }

        } catch (Throwable var7) {
            System.out.println(var7);
            throw var7;
        }
    }


    public static <V> Boolean zsadd(String key, V value, double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }
}
