package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;
import com.jt.service.UserService;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;
@RequestMapping("/user")
@RestController
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private JedisCluster jedisCluster;
	//	@RequestMapping("/get")
	//	public String getMsg() {
	//		return "测试成功";
	//	}
	//	

	//前台通过jsonp形式实现跨域请求，
	@RequestMapping("/check/{param}/{type}")
	public JSONPObject findCheckUser(@PathVariable String param,@PathVariable Integer type,String callback) {
		//返回true表示用户存在，false表示可以使用
		Boolean flag=userService.findCheckUser(param,type);
		return new JSONPObject(callback, SysResult.ok(flag));
	}

	
	/**
	 * 	实现用户新增
	 */
		@RequestMapping("/doRegister")
		@ResponseBody
		public SysResult saveUser(User user) {
			try {
				userService.saveUser(user);
				
			} catch (Exception e) {
				e.printStackTrace();
				return SysResult.fail();
			}
			return SysResult.ok();
			
		}


	//根据token数据获取用户信息
	@RequestMapping("/query/{token}")
	public JSONPObject findUserByToken(@PathVariable String token,String callback) {
		System.out.println(callback);
		String userJSON = jedisCluster.get(token);
		System.out.println(userJSON);
		return new JSONPObject(callback, SysResult.ok(userJSON));
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
