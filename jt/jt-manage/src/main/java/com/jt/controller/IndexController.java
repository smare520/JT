package com.jt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	/**
	 * 	
	 * 	思路：能接收用户请求参数，实现页面动态跳转
	 * 	RESTFUL结构：
	 * 		1.参数必须使用“/”分割，如果多个参数则多个/
	 * 		2.参数必须使用{}包裹,并且命名名称
	 * 		3.必须添加参数
	 * 	
	 * 	特点：可以将url中的请求路径信息动态获取数据
	 * 	要求：传参时名称需要统一
	 * 
	 * 	用途：可以利用restful风格代替get请求
	 */
	@RequestMapping("/page/{indexName}")
	public String index(@PathVariable(value="indexName") String abc) {
		return abc;
	}
	/*
	@RequestMapping("/page/{}")
	public String index1() {
		return "item-list";
	}
	*/
}
