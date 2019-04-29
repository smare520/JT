package com.jt.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class TestRedisCluster {
	@Test
	public void test() {
		Set<HostAndPort>nodes = new HashSet<>();
		nodes.add(new  HostAndPort("192.168.58.133",7000));
		nodes.add(new  HostAndPort("192.168.58.133",7001));
		nodes.add(new  HostAndPort("192.168.58.133",7002));
		nodes.add(new  HostAndPort("192.168.58.133",7003));
		nodes.add(new  HostAndPort("192.168.58.133",7004));
		nodes.add(new  HostAndPort("192.168.58.133",7005));
		nodes.add(new  HostAndPort("192.168.58.133",7006));
		nodes.add(new  HostAndPort("192.168.58.133",7007));
		nodes.add(new  HostAndPort("192.168.58.133",7008));
	JedisCluster jedis= new JedisCluster(nodes);
	jedis.set("1812","集群搭建成功");
	System.out.println(jedis.get("1812"));
	
	}
}
