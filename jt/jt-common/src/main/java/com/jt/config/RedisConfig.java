package com.jt.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

import com.jt.properties.RedisPerproties;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

/**
 *	 配置类 
 *		代替配置文件
 */
//@Configuration	/** 表示配置类  */
//@ImportResource({"classpath:/spring/application-redis.xml",})	
/** 导入第三方配置文件  ,{}表示加载多个配置文件*/

@Configuration
//@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {

//	@Value("${redis.host}")
//	private String host;
//	@Value("${redis.port}")
//	private Integer port;
//	@Bean	//将方法的返回值对象交给spring容器管理
//	public Jedis jedis() {
//		return new Jedis(host,port);
//	}
	
//	@Value("${redis.shards}")
//	
//	private String redisShards;
//	@Bean
//	public ShardedJedis shardedJedis() {
//		String[] nodes = redisShards.split(",");
//		List<JedisShardInfo> shards =new ArrayList<>();
//		//node:IP端口
//		for (String node :nodes) {
//			String[] args = node.split(":");
//			shards.add(new JedisShardInfo(args[0],Integer.valueOf(args[1])));
//		}
//		
//		/*
//		shards.add(new JedisShardInfo("192.168.58.133",6379));
//		shards.add(new JedisShardInfo("192.168.58.133",6380));
//		shards.add(new JedisShardInfo("192.168.58.133",6381));
//		*/
//		return new ShardedJedis(shards);
//	} 
	
	
//	@Value("${redis.nodes}")
//	private String nodes;
	@Bean
	public JedisCluster jedisCluster() {
		Set<HostAndPort>nodeset = new HashSet<>();
		String [] node =RedisPerproties.nodes.split(",");
		for (String hostnode:node) {
			String [] str=hostnode.split(":");
			int port=Integer.parseInt(str[1]);
			HostAndPort hostAndPort = new HostAndPort(str[0], port);
			nodeset.add(hostAndPort);
		}
		return new JedisCluster(nodeset);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
