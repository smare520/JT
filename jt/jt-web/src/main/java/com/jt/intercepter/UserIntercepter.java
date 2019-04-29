package com.jt.intercepter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.internal.compiler.problem.ProblemHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.UserThreadlocal;

import redis.clients.jedis.JedisCluster;

/**
 * 	核心：获取用户访问的数据/路径  request/response
 * 		完成用户校验，没有登录则跳转登录页面
 */
@Component
public class UserIntercepter implements HandlerInterceptor{
	/**
	 * 	拦截器实现步骤：
	 * 		1.首先获取cookie数据
	 * 		2.判断是否登录
	 *			没有登录则重定向到用户登录页面
	 * 			已经登录，则判断redis中是否有数据
	 * 				有则放行，无则重定向到登录页面
	 */
	@Autowired
	private JedisCluster jedis;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token=null;
		//1.获取用户cookie数据
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if("JT_TICKET".equals(cookie.getName())) {
				 token = cookie.getValue();
				 break;
			}
		}
		if(!StringUtils.isEmpty(token)) {
			// 判断redis中是否有数据
			String userJSON = jedis.get(token);
			if(!StringUtils.isEmpty(userJSON)) {
				//程序执行到这里，表示用户已经登录
				User user = ObjectMapperUtil.toObject(userJSON, User.class);
				UserThreadlocal.set(user);
				return true;
			}
		}
		//如果执行到这里，则表示用户没有登录
		response.sendRedirect("/user/login.html");
		return false;//表示拦截
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		UserThreadlocal.remove();
		System.out.println("调用完成，清楚数据。。。");
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
