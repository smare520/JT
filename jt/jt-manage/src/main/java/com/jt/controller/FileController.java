package com.jt.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jt.service.FileService;
import com.jt.vo.FileVo;

@Controller
public class FileController {
	
	@Autowired
	private FileService fileService;
	/**	
	 * 	1.要求：实现文件上传后，重定向到文件上传页面
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping("/file")
	/**文件上传接口类型：MultipartFile*/
	public String fileImage(MultipartFile fileImage) throws Exception {	
		/**
		 * 	1.步骤一：获取文件名称
		 * 	2.步骤二：指定文件目录,判断文件夹是否存在
		 * 	3.步骤三：实现文件上传
		 */
//		步骤一：获取文件名称
		String filename = fileImage.getOriginalFilename();
//		步骤二：指定文件目录,判断文件夹是否存在
		String filepath ="D:/SpringTool/jt-upload";
		File dirfile = new File(filepath);
		if(!dirfile.exists())dirfile.mkdirs();
		//步骤三：实现文件上传
		String realName="D:/SpringTool/jt-upload/"+filename;
		fileImage.transferTo(new File(realName));
		return "redirect:/file.jsp";
	}
	
	
	/**
	 * 	1.文件上传
	 * 		1.确定url,确定提交的参数名称
	 * 		2.可以通过文件的后缀判断是否为图片类型。 png/gif/jpg
	 * 		3.通过第三方工具判定是否为恶意程序
	 * 		4.为提高检索效率，分文件存储
	 * 			a.按照商品类型，需要解决文件存储不均衡的现象
	 * 			b.按照时间进行储存
	 * 		5.防止文件重名现象
	 * 			a.利用时间戳生成名称+随机数3位
	 * 			b.UUID:保证每次生成的串都不相同
	 * 			c.MD5(盐值+随机数)
	 * 		6.实现文件上传，将图片信息保存到磁盘信息
	 * 		7.生存网络请求的虚拟路径，方便他人访问
	 */
	@RequestMapping("/pic/upload")
	@ResponseBody
	public FileVo uploadFile(MultipartFile uploadFile) {
		return fileService.upload(uploadFile);
	}
	
	
	
	/**
	 *  2.反向代理
	 * 	 1.用户发起url请求，访问虚拟空间地址
	 * 	 2.nginx监听用户的请求域名和端口，
	 * 	 3.nginx代替用户发起请求，获取服务器资源
	 * 	 4.nginx获取数据返回给用户
	 */
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
