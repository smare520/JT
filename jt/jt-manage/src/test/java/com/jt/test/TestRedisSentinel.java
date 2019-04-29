package com.jt.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

public class TestRedisSentinel {

	@Test
	public void test() {
		HostAndPort msg = new HostAndPort("192.168.58.133", 26379);
		System.out.println("工具api的结果:"+msg);
		Set<String>sentinel=new HashSet<>();
		sentinel.add("192.168.58.133:26379");
		JedisSentinelPool pool = new JedisSentinelPool("mymaster", sentinel);
		
		//只能操作主机
		Jedis jedis = pool.getResource();
		jedis.set("1812", "哨兵测试完成");
		System.out.println(jedis.get("1812"));
		jedis.close();
	}
}
