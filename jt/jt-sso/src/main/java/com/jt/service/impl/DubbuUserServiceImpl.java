package com.jt.service.impl;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.JedisCluster;

@Service
public class DubbuUserServiceImpl implements DubboUserService{

		@Autowired
		private UserMapper userMapper;
		@Autowired
		private JedisCluster jedis;

		@Override
		@Transactional
		public void saveUser(User user) {
//			String salt="wen";
			String MD5password=DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
			user.setPassword(MD5password);
			user.setEmail(user.getPhone());
			user.setCreated(new Date());
			user.setUpdated(user.getCreated());
			userMapper.insert(user);
		}

		
		/**
		 * 	实现用户单点登录
		 * 	1.根据用户名密码查询数据库
		 * 	2.生成token对象
		 * 	3.把用户对象转化成json
		 * 	4.保存到redis中
		 */
		@Override
		public String findUserByUP(User user) {
			String md5Password=DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
			System.out.println(md5Password);
			QueryWrapper<User> queryWrapper=new QueryWrapper<>();
			queryWrapper.eq("username",user.getUsername())
						.eq("password", md5Password);
			User userDB=userMapper.selectOne(queryWrapper);
			
			if(userDB==null) {
				return null;
			}
			//程序执行到这里，表示用户名密码正确
			String token ="JT_TICKET"+System.currentTimeMillis()+user.getUsername();
			token=DigestUtils.md5DigestAsHex(token.getBytes());
			//必须对敏感数据进行脱敏处理
			userDB.setPassword("******你猜*******");
			String userJson=ObjectMapperUtil.toJSON(userDB);
			jedis.setex(token, 3600*24*7, userJson);
			return token;
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
}















