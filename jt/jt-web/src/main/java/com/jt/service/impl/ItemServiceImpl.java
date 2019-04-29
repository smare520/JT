package com.jt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.util.HttpClientService;
import com.jt.util.ObjectMapperUtil;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private HttpClientService httpClient;
	/**
	 * 	前台service请求jt-manage中的数据，需要httpClient技术
	 */
	@Override
	public Item findItemById(Long itemId) {
		String url="http://manage.jt.com/web/item/findItemById/"+itemId;
		String result=httpClient.doGet(url);
		//System.out.println(result);
		//将result转化成对象
		return ObjectMapperUtil.toObject(result, Item.class);
		
	}
	@Override
	public ItemDesc findItemDescById(Long itemId) {
		String url="http://manage.jt.com/web/item/findItemDescById/"+itemId;
		String reslut=httpClient.doGet(url);
		return ObjectMapperUtil.toObject(reslut, ItemDesc.class);
	}

}
