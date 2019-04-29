package com.jt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;

public interface ItemMapper extends BaseMapper<Item>{
	/**
	 * 	关于mybatis取值传参问题
	 *	规定 ：mybatis不允许多值传参，只能将多值转化为单值
	 *		1.封装成pojo对象
	 *		2.封装成Map集合
	 *		3.封装成array/list  
	 */
	List<Item> findItemByPage(@Param("start")Integer start,@Param("rows") Integer rows);

	
	/**
	 * 更新操作
	 * @param item
	 */
//	void updateByPrimaryKeySelective(Item item);



	
	
	
	
	
}
