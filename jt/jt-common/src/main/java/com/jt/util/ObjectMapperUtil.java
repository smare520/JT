package com.jt.util;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *	该工具类解决对象转化中的try-catch问题
 *		1.对象转json tojson
 *		2.json转对象	 toObject 
 */
public class ObjectMapperUtil {
	
	//是否有线程安全性问题(对象没有线程安全问题) 
	private static ObjectMapper mapper=new ObjectMapper();
	public static String toJSON(Object obj) {
		String json=null;
		try {
			json=mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			//如果报错，需要转化为运行异常
			throw new RuntimeException();
		}
		return json;
	}
	
	/** 用户传入class返回该类型的对象  */
	public static <T>T toObject(String json,Class<T>target ) {
		T t=null;
		try {
			t=mapper.readValue(json,target);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return t;
	}
	
	
}
