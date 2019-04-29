package com.jt.test;

import org.junit.Test;

import redis.clients.jedis.Jedis;

//import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootTest
//@RunWith(SpringRunner.class)
public class TestRedis {
	
	//实现字符串操作
	@Test
	public void testString() throws Exception {
		Jedis jedis =new Jedis("192.168.58.133", 6379);
		jedis.set("1812","阿拉斯加猪");
		System.out.println(jedis.get("1812"));
		jedis.del("a");
		
		//设置超时时间
		jedis.expire("1812",60);
		Thread.sleep(1000);
		System.out.println(jedis.ttl("1812"));
	}
	
	@Test
	public void String () {
		Jedis jedis = new Jedis("192.168.58.133",6379);
		jedis.lpush("aaaaa","1","2","3","4");
		String rpop = jedis.rpop("aaaaa");
		System.out.println(rpop);	
	}
}
