package com.jt.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	/**
	 * 	param:用户输入内容
	 * 	type：1.username/ 2.phone / 3.email
	 * 	sql:select count(*) from tb_user where username="XXX";
	 */
	@Override
	public Boolean findCheckUser(String param, Integer type) {
		String column =null;
		switch (type) {
		case 1:
			column="username";
			break;
		case 2:
			column="phone";
			break;
		case 3:
			column="email";
			break;
		}
		//根据数据库结果分析，如果总数为0，返回false;否则true
		QueryWrapper<User> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq(column, param);
		int count =userMapper.selectCount(queryWrapper);
		return count==0?false:true;
	}

	/**
	 * 新增用户
	 */
	@Override
	@Transactional
	public void saveUser(User user) {
		String MD5password=DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(MD5password);
		user.setEmail(user.getPhone());
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		userMapper.insert(user);
		
	}



}
