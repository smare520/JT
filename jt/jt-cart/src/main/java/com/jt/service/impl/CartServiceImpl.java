package com.jt.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.CartMapper;
import com.jt.pojo.Cart;
import com.jt.service.CartService;
@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private CartMapper cartMapper;
	
	/**	
	 * 	 实现购物车列表数据展现
	 * @return
	 */
	@Override
	public List<Cart> findCartList(Long userId) {
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_id", userId);
		return cartMapper.selectList(queryWrapper);
	}

	/**
	 * 	修改商品数量
	 */
	@Override
	public void updateCartNum(Cart cart) {
		Cart cartDB = new Cart();
		cartDB.setNum(cart.getNum()).setUpdated(new Date());
		QueryWrapper<Cart> queryWrapper =new QueryWrapper<>();
		queryWrapper.eq("user_id",cart.getUserId()).eq("item_id", cart.getItemId());
		cartMapper.update(cartDB, queryWrapper);
		
	}

	/**
	 * 	新增购物车
	 * 	1.如果当前商品数据库中已经存在，则数量更新update
	 * 	2.当前商品数据库中不存在，则新增商品insert
	 */
	@Transactional
	@Override
	public void saveCart(Cart cart) {
		QueryWrapper<Cart> queryWrapper =new QueryWrapper<>();
		queryWrapper.eq("item_id",cart.getItemId()).eq("user_id", cart.getUserId());
		Cart cartDB=cartMapper.selectOne(queryWrapper);
		
		if(cartDB==null) {
			cart.setCreated(new Date()).setUpdated(cart.getCreated());
			cartMapper.insert(cart);
		}else {
			int num =cart.getNum()+cartDB.getNum();
			cartDB.setNum(num);
			cartDB.setUpdated(new Date());
			cartMapper.updateById(cartDB);
			
		}
		
	}

	/**
	 * 	实现商品删除功能  http://www.jt.com/cart/delete/562379.html
	 */
	@Override
	public void deleteCart(Cart cart) {
		QueryWrapper<Cart> queryWrapper =new QueryWrapper<>();
		queryWrapper.eq("user_id",cart.getUserId()).eq("item_id", cart.getItemId());
		cartMapper.delete(queryWrapper);
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
}
