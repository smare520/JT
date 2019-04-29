package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;

@RequestMapping("/items")
@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/{itemId}")
	public String findItemById(@PathVariable Long itemId ,Model model) {
		//获取商品信息,存入域对象
		Item item=itemService.findItemById(itemId);
		System.out.println(item);
		
		model.addAttribute("item",item);
		
		//获取商品详情信息
		ItemDesc itemdesc=itemService.findItemDescById(itemId);
//		System.out.println(itemdesc);
		model.addAttribute("itemDesc",itemdesc);
		return "item";
	}
}
