package com.jt.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import redis.clients.jedis.Jedis;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSpringRedis {
	@Autowired
	private Jedis jedis;
	
	@Test
	public void testjedis() {
		jedis.set("1812", "阿拉司机猪");
		System.out.println(jedis.get("1812"));
	}
	
}
