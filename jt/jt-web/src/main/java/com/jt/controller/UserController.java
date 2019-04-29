package com.jt.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;


@Controller
@RequestMapping("/user")
public class UserController {

	@Reference(timeout=3000,check=false )
	private DubboUserService dubboUserService;
	
	@Autowired
	private JedisCluster jedisCluster;
	
	//实现登录页面跳转
	@RequestMapping("/{moduleName}")
	public String moduleName(@PathVariable String moduleName) {
		return moduleName;
	}
	/**
	 * 	实现用户新增
	 */
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult saveUser(User user) {
		try {
			dubboUserService.saveUser(user);
			System.out.println(user);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
		return SysResult.ok();

	}

	/**
	 * 1.实现用户单点登录
	 * 	如果用户名或密码错误，则token为null;
	 * 	用户名和密码正确，token有数据
	 * 	业务流程
	 * 		1.判断数据是否有效
	 * 		2.有效数据保存到cookie中
	 * 	关于cookie补充只是：
	 * 		.setPath("/")  只要是项目中的页面都可以访问cookie 
	 * 		.setPath("/sso/") 只有sso的页面下才能访问cookie
	 */
	@ResponseBody
	@RequestMapping("/doLogin")
	public SysResult login(User user,HttpServletResponse response) {
		System.out.println("asdasdasdasdasdsd");
		try {
			String token=dubboUserService.findUserByUP(user);
			System.out.println(token+"+++++++++++++++++++++++");
			if(!StringUtils.isEmpty(token)) {

				Cookie cookie = new Cookie("JT_TICKET",token);
				cookie.setMaxAge(60*60*24*7);
				cookie.setPath("/");//cookie 的权限
				response.addCookie(cookie);
				return SysResult.ok();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.fail();
	}

	/**
	 * 退出登录操作
	 *  删除redis：token/cookie
	 * 	1.先获取用户浏览器的cookie数据
	 * 	2.根据token数据删除redis 
	 * 	3.删除cookie
	 */
	@RequestMapping("logout")
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		String token=null;
		//1.获取cookie数据
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if("JT_TICKET".equals(cookie.getName())) {
				token=cookie.getValue();
				break;//结束循环
			}
		}
		//2.删除redis
		jedisCluster.del(token);
		//3.删除cookie
		Cookie cookie=new Cookie("JT_TICKET","");
		cookie.setMaxAge(0);//cookie.setMaxAge(0/-1); 0:立即删除数据；-1：关闭会话时删除
		response.addCookie(cookie);
		return "redirect:/index.html";
	}
	
	
	

}





















