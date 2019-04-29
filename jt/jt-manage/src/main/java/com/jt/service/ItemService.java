package com.jt.service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUIList;

public interface ItemService {


	/**
	 * 	1.商品分页查询
	 */
	EasyUIList findItemByPage(Integer page, Integer rows);

	/**
	 * 2.商品信息新增
	 * @param item
	 */
	void saveItem(Item item,ItemDesc itemDesc);

	/**
	 * 3.上架
	 * @param ids
	 * @param status
	 */
	void updateStatus(Long[] ids, Integer status);

	/**
	 * 	4.修改商品信息
	 */
	void updateItem(Item item, ItemDesc itemDesc);

	/**
	 * 	5.删除商品信息
	 */
	void deleteItems(Long[] ids);

	ItemDesc findItemDescById(Long itemId);

	
	
	
	Item SelectById(Long itemId);




	
}
