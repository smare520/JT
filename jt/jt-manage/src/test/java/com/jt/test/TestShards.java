package com.jt.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

/**
 * 测试redis分片技术
 * @author Tedu
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestShards {

	@Test
	public void test01() {
		//准备list集合封装多台redis
		List<JedisShardInfo>shards=new ArrayList<>();
		shards.add(new JedisShardInfo("192.168.58.133",6379));
		shards.add(new JedisShardInfo("192.168.58.133",6380));
		shards.add(new JedisShardInfo("192.168.58.133",6381));
		ShardedJedis jedis=new ShardedJedis(shards);
		
		jedis.set("1812", "分片操作");
		System.out.println(jedis.get("1812"));
	}
	
	
	//spring管理redis分片
	@Autowired
	private ShardedJedis jedis;
	
	@Test
	public void test02() {
		jedis.set("1812","spring整合成功" );
		System.out.println(jedis.get("1812"));
	}
	
}
