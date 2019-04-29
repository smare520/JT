package com.jt.service;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.OrderItemMapper;
import com.jt.mapper.OrderMapper;
import com.jt.mapper.OrderShippingMapper;
import com.jt.pojo.Order;
import com.jt.pojo.OrderItem;
import com.jt.pojo.OrderShipping;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;
	
	
	/**	分析：
	 * 
	 * 	用户提交一次订单，入库三张表
	 */
	@Override
	@Transactional
	public String saveOrder(Order order) {
		Date date = new Date();
		//1.准备orderId
		String orderId=""+order.getUserId() +System.currentTimeMillis();
		order.setOrderId(orderId).setStatus(1).setCreated(date);
		orderMapper.insert(order);
		System.out.println("订单入库成功*********");
		
		//2.入库订单物流
		OrderShipping orderShipping = order.getOrderShipping();
		orderShipping.setOrderId(orderId).setCreated(date).setUpdated(date);
		orderShippingMapper.insert(orderShipping);
		
		List<OrderItem> items =order.getOrderItems();
		for (OrderItem orderItem : items) {
			order.setOrderId(orderId).setCreated(date).setUpdated(date);
			orderItemMapper.insert(orderItem);
		}
		return orderId;
	}


	@Override
	public Order findOrderById(String id) {
		Order order=orderMapper.selectById(id);
		OrderShipping orderShipping=orderShippingMapper.selectById(id);
		QueryWrapper <OrderItem>queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("order_id",id);
		List<OrderItem>orderItems=orderItemMapper.selectList(queryWrapper);
		order.setOrderShipping(orderShipping).setOrderItems(orderItems);
		return order;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
