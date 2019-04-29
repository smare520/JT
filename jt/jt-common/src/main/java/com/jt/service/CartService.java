package com.jt.service;

import java.util.List;

import com.jt.pojo.Cart;

public interface CartService {
	/**	
	 * 	 实现购物车列表数据展现
	 * @return
	 */
	List<Cart> findCartList(Long userId);

	
	/**
	 * 	实现购物车商品数量修改
	 * 	如果使用restful风格有多个参数，和pojo同属性，用对象接收
	 */
	void updateCartNum(Cart cart);

	
	/**
	 * 	实现购物车新增商品
	 */
	void saveCart(Cart cart);

	
	/**
	 * 	实现商品删除功能  http://www.jt.com/cart/delete/562379.html
	 */
	void deleteCart(Cart cart);

}
