package com.jt.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.ItemCat;

public class TestObjectMapper {

	@Test
	public void testJson() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		ItemCat itemCat = new ItemCat();
		itemCat.setId(1000L).setName("测试机").setParentId(2000L);
		String json = mapper.writeValueAsString(itemCat);
		System.out.println(json);
		//将json转换成对象
		ItemCat jsonItemCat = mapper.readValue(json,ItemCat.class);
		System.out.println(jsonItemCat);
	}
	
	@Test
	public void testList() throws Exception {
		ArrayList<ItemCat> catList = new ArrayList<ItemCat>();
		for(int i=0;i<5;i++){
			ItemCat itemCat = new ItemCat();
			itemCat.setId(1000L+i).setName("测试"+i).setParentId(2000L+i);
			catList.add(itemCat);
		}
		ObjectMapper objectMapper = new ObjectMapper();
		String json=objectMapper.writeValueAsString(catList);
		System.out.println(json);
		//将json转化为对象List
		List<ItemCat> readValue = objectMapper.readValue(json,catList.getClass());
		System.out.println(readValue);
	}
}
