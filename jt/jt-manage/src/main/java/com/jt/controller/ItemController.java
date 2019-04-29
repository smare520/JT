package com.jt.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUIList;
import com.jt.vo.SysResult;

@Controller
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
//	@Autowired
	
	
	/**
	 * 	1.商品分页查询
	 */
	@RequestMapping("/query")
	@ResponseBody
	public EasyUIList findItemByPage(Integer page,Integer rows) {
		return itemService.findItemByPage(page,rows);
		
	}
	
	/**
	 * 2.商品信息新增
	 */
	@RequestMapping("/save")
	@ResponseBody
	public SysResult saveItem(Item item,ItemDesc itemDesc) {
		try {
			itemService.saveItem(item,itemDesc);
			return  SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return  SysResult.fail();
		}
	}
	
	/**
	 * 3.实现商品下架
	 */
	@RequestMapping("/instock")
	@ResponseBody
	public SysResult instock(Long...ids) {
		try {
			int status=2;
			itemService.updateStatus(ids,status);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
		
	}
	
	/**
	 * 3.实现商品上架
	 */
	@RequestMapping("/reshelf")
	@ResponseBody
	public SysResult reshelf(Long...ids) {
		try {
			Integer status=1;
			itemService.updateStatus(ids,status);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
		
	}
	
	/**
	 * 	4.修改商品信息
	 */
	@RequestMapping("/update")
	@ResponseBody
	public SysResult updateItem(Item item, ItemDesc itemDesc) {
		try {
			itemService.updateItem(item,itemDesc);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	
	
	/**
	 * 	5.删除商品信息
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public SysResult delete(Long...ids) {
		try {
			itemService.deleteItems(ids);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	
	/**
	 * 6.根据itemId查询商品详情信息
	 */
	@RequestMapping("/query/item/desc/{itemId}")
	@ResponseBody
	public SysResult findItemDescById(@PathVariable("itemId") Long itemId) {
		try {
			System.out.println(itemId);
			ItemDesc itemDesc = itemService.findItemDescById(itemId);
			System.out.println(itemDesc);
			return SysResult.ok(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
		
	}
	
	
}
