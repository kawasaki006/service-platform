package com.kawasaki.service.order_service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class OrderServiceApplicationTests {
    @Autowired
    private StringRedisTemplate redisTemplate;

	@Test
	void contextLoads() {
	}

    @Test
    public void testRedisConnection() {
        String key = "test:key";
        String value = "hello-redis";

        // set
        redisTemplate.opsForValue().set(key, value);

        // get
        String result = redisTemplate.opsForValue().get(key);

        System.out.println("Redis returned: " + result);

        assertThat(result).isEqualTo(value);
    }

}
