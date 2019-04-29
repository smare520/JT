package com.jt.service;

import com.jt.pojo.User;

public interface DubboUserService {

	void saveUser(User user);

	/**
	 * 实现用户单点登录
	 */
	String findUserByUP(User user);

}
