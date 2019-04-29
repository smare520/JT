package com.jt.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.service.ItemCatService;
import com.jt.util.CaCheAnnotation;
import com.jt.util.CaCheAnnotation.CACHE_TYPE;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUITree;

import redis.clients.jedis.Jedis;

@Service
public  class ItemCatServiceImpl implements ItemCatService{
	
	@Autowired
	private ItemCatMapper itemcatmapper;
	//@Autowired
	private Jedis jedis;
	/**
	 * 	2.根据商品分类ID查询分类名称
	 */
	@Override
	public ItemCat findItemCatNameById(Long itemCatId) {
		return  itemcatmapper.selectById(itemCatId);
	
		
 }
	
	
	/**
	 * 3.实现商品信息树形结构展现
	 */
	@Override
	public List<EasyUITree> findItemCatList(Long parentId) {
		QueryWrapper<ItemCat>qw =new QueryWrapper<>();
		qw.eq("parent_id", parentId);
		List<ItemCat> itemcatlist = itemcatmapper.selectList(qw);
		//需要返回volist集合信息，则遍历itemcatlist
		List<EasyUITree> treelist=new ArrayList<>();
		for (ItemCat itemcat : itemcatlist) {
			EasyUITree uitree = new EasyUITree();
			uitree.setId(itemcat.getId()).setText(itemcat.getName()).setState(itemcat.getIsParent() ? "closed" : "open");
			treelist.add(uitree);
//			String state=itemcat.getIsParent()?	state="closed":"open";
			
		}
		return treelist;
	}


	@SuppressWarnings("unchecked")
	@Override
	@CaCheAnnotation(index=0,cacheType=CACHE_TYPE.FIND)
	public List<EasyUITree> findItemCatCacheList(Long parentId) {
		List<EasyUITree>treelist=new ArrayList<EasyUITree>();
		//key的定义 类名_变量
		String key="ITEM_CAT_"+parentId;
		String result = jedis.get(key);
		if(StringUtils.isEmpty(result)) {
			//缓存中没有数据，查询真实的数据库信息
			treelist = findItemCatList(parentId);
			//将数据保存到缓存中
			String json = ObjectMapperUtil.toJSON(treelist);
			jedis.set(key, json);
			System.out.println("查询数据库");
		}else {
			treelist=	ObjectMapperUtil.toObject(result, treelist.getClass());
		}
		return treelist;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
