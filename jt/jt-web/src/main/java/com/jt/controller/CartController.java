package com.jt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.User;
import com.jt.service.CartService;
import com.jt.util.UserThreadlocal;
import com.jt.vo.SysResult;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Reference(timeout=3000,check=false)
	private CartService cartService;
	/**	
	 * 	 实现购物车列表数据展现
	 * @return
	 */
	@RequestMapping("/show")
	public String show(Model model,HttpServletRequest request) {
		//获取当前用户数据
		Long userId = UserThreadlocal.get().getId();
		//1.获取购物车信息
//		List<Cart> cartList=findCartList;
		List<Cart> cartList = cartService.findCartList(userId);
		model.addAttribute("cartList",cartList);
		return "cart";
	}
	
	
	/**
	 * 	实现购物车商品数量修改
	 * 	如果使用restful风格有多个参数，和pojo同属性，用对象接收
	 */
	@ResponseBody
	@RequestMapping("/update/num/{itemId}/{num}")
	public SysResult updateNum(Cart cart) {
		try {
			Long userId=UserThreadlocal.get().getId();
			cart.setUserId(userId);
			cartService.updateCartNum(cart);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}

	/**
	 * 	实现购物车新增商品
	 */
	@RequestMapping("/add/{itemId}")
	public String addCart(Cart cart) {
		Long userId=UserThreadlocal.get().getId();
		cart.setUserId(userId);
		cartService.saveCart(cart);
		//重定向到用户展现页面
		return "redirect:/cart/show.html";
	}
	
	/**
	 * 	实现商品删除功能  http://www.jt.com/cart/delete/562379.html
	 */
	@RequestMapping("/delete/{itemId}")
	public String deleteCart(Cart cart) {
		Long userId=UserThreadlocal.get().getId();
		cart.setUserId(userId);
		cartService.deleteCart(cart);
		return "redirect:/cart/show.html";
	}


}











