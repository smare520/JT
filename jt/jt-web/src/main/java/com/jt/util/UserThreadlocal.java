package com.jt.util;

import com.jt.pojo.User;

/**
 * 	该对象保存的用户信息
 */
public class UserThreadlocal {

	private static ThreadLocal<User> thread=new ThreadLocal<>();
	
	public static void set(User user) {
		thread.set(user);
	}
	public static User get() {
		return thread.get();
	}
	//使用threadLocal必须注意内存泄漏
	public static void remove() {
		thread.remove();
	}
}
