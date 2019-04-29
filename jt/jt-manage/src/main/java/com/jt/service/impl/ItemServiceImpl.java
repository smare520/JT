package com.jt.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.BasePojo;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUIList;

@Service
public class ItemServiceImpl implements ItemService {
	
	@SuppressWarnings("unused")
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemdescmapper;

	/**
	 * 	1.商品分页查询
	 */
	@Override
	public EasyUIList findItemByPage(Integer page, Integer rows) {
		int total=itemMapper.selectCount(null);
		int start= (page-1)*rows;
		List<Item> list=itemMapper.findItemByPage(start,rows);
		return new EasyUIList(total,list);
	}

	/**
	 * 2.商品信息新增
	 * 
	 * 	知识回顾：propagation:	事务传播属性
	 * 			1.REQUIRED：		必须添加事务，默认值
	 * 			2.SUPPORTS：		事务支持的（一般只有查询操作才会添加）
	 * 			3.REQUIRES_NEW：开启一个全新的事务
	 * 			4.NEVER：		从不添加事务，爬虫时使用
	 */
	@Transactional
	@Override
	public void saveItem(Item item,ItemDesc itemDesc) {
		
		item.setStatus(1);	//1.表示正常  2表示下架
		item.setCreated(new Date());
		item.setUpdated(item.getCreated());
		itemMapper.insert(item);
		
		BasePojo xx = itemDesc.setItemId(item.getId()).setCreated(item.getCreated()).setUpdated(item.getCreated());
		System.out.println(xx);
		itemdescmapper.insert(itemDesc);
		
	}

	/**
	 * 3.实现商品上下架
	 * 	说明：批量更新操作
	 * 	sql:update tb_item_cat set status=#{status} updated=#{updated}
	 * 			where id in (id1,id2...)
	 * 		
	 * 	实现1：面向对象方式操作
	 * 	实现2：自己写sql
	 */
	@Transactional
	@Override
	public void updateStatus(Long[] ids, Integer status) {
		Item item = new Item();
		item.setStatus(status).setUpdated(new Date());
		UpdateWrapper<Item>updateWrapper=new UpdateWrapper<>();
		List<Long> idlist=Arrays.asList(ids);
		updateWrapper.in("id", idlist);
		itemMapper.update(item, updateWrapper);
	}

	/**
	 * 	4.修改商品信息
	 */
	@Transactional
	@Override
	public void updateItem(Item item, ItemDesc itemDesc) {
		item.setUpdated(new Date());
		itemMapper.updateById(item);
		itemDesc.setItemId(item.getId()).setUpdated(item.getUpdated());
		itemdescmapper.updateById(itemDesc);
	}

	/**
	 * 	5.删除商品信息
	 */
	@Transactional
	@Override
	public void deleteItems(Long[] ids) {
		List<Long>idList=Arrays.asList(ids);
		System.out.println(idList);
		itemMapper.deleteBatchIds(idList);
		itemdescmapper.deleteBatchIds(idList);
	}

	@Override
	public ItemDesc findItemDescById(Long itemId) {
		ItemDesc selectById = itemdescmapper.selectById(itemId);
		System.out.println(selectById);
		return selectById;
	}

	
	
	//web
	@Override
	public Item SelectById(Long itemId) {
		return itemMapper.selectById(itemId);
	}

	

	
	
	
	
	
	
	
}
