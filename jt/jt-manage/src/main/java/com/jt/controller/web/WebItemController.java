package com.jt.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;

@RestController
@RequestMapping("/web/item")
public class WebItemController {
	/**
	 * 	模拟用户请求
	 * 	http://manage.jt.com/web/item/findItemById/XXId
	 */
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/findItemById/{itemId}")
	public Item findItemById(@PathVariable Long itemId) {
		System.out.println(itemId);
		return itemService.SelectById(itemId);
		
		
	}
	
	@RequestMapping("/findItemDescById/{itemId}")
	public ItemDesc findItemDescById(@PathVariable Long itemId) {
		System.out.println(itemId);
		return itemService.findItemDescById(itemId);
	}
	
	
	
}















