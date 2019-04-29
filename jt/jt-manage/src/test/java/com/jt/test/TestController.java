package com.jt.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
	@RequestMapping("/meg")
	@ResponseBody
	public String meg() {
		return "8095哈士狗";
	}
}