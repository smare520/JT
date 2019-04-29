package com.jt.controller.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.Item;
import com.jt.pojo.ItemCat;
import com.jt.util.ObjectMapperUtil;

@RestController
public class WebJSONPcontroller {
	/**
	@RequestMapping("/web/testJSONP")
	public String getJSONP(String callback) {
		Item item=new Item();
		item.setId(100L).setTitle("跨域成功");
		String json=ObjectMapperUtil.toJSON(item);
		return callback+"("+json+")";
	}
	*/
	
	@RequestMapping("/web/testJSONP")
	public JSONPObject getJSONP(String callback) {
		ItemCat item=new ItemCat();
		item.setId(100L);
//		String json=ObjectMapperUtil.toJSON(item);
		//function:代表回调函数
		//value:代表返回数据对象
		return new JSONPObject(callback,item);
	}
}
