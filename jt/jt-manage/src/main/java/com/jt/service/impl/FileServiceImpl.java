package com.jt.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;
import java.util.UUID;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.service.FileService;
import com.jt.vo.FileVo;
@Service	//默认对象是单例，不要修改成员变量
@PropertySource("classpath:/properties/image.properties")
public class FileServiceImpl implements FileService{
	//定义本地磁盘路径
	@Value("${image.dirPath}")
	private String localpath;
	@Value("${image.urlPath}")
	private String urlpath;
//	private String localpath="D:/SpringTool/jt-upload/";
//	private String urlpath="http://image.jt.com/";
	/**
	 * 	7.文件上传
	 * 		1.确定url,确定提交的参数名称路径
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
	@Override
	public FileVo upload(MultipartFile uploadFile) {

		FileVo fileVo = new FileVo();
		
		//1.获取文件名
		String filename = uploadFile.getOriginalFilename();
		//2.将文件名称转换成小写，方便以后判断
		filename =filename.toLowerCase();
		//3.利用正则表达式判断
		//	^开始，$结束, .除了回车换行外的任意单个字符
		//	+至少一个， *0或多个
		if(!filename.matches("^.+\\.(png|jpg|gif)$")) {
			// 表示文件类型不匹配
//			return new FileVo(1,"","",null);
			fileVo.setError(1);
			return fileVo;
		}
		//4.判断是否为恶意程序
		try {
			BufferedImage image=ImageIO.read(uploadFile.getInputStream());
			//4.1获取宽度和高度
			int width=image.getWidth();
			int height=image.getHeight();
			//4.2判断属性是否为0
			if(width==0||height==0) {
//				return new FileVo(1,"","",null);
				fileVo.setError(1);
				return fileVo;
			}
			//5.根据时间生成文件夹
			String datedir = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
			
			String localdir=localpath+datedir;
			File dirfile = new File(localdir);
			if(!dirfile.exists()) {
				dirfile.mkdirs();
			}
			//6.防止文件名重复
			//6.1生成UUID	(将横杠替换成空)
			String UUIDName=UUID.randomUUID().toString().replace("-", "");
			//6.2获取文件类型，进行拼接
			String fileType =filename.substring(filename.lastIndexOf("."));
			String realname=UUIDName+fileType;
			//6.3实现文件上传
			File realfile = new File(localdir+"/"+realname);
			uploadFile.transferTo(realfile);
//			fileVo.setError(0);
			fileVo.setHeight(height);
			fileVo.setWidth(width);
			//	设置图片虚拟访问路径
			String realurlpath=urlpath+datedir+"/"+realname;
			fileVo.setUrl(realurlpath);
//			fileVo.setUrl("https://img10.360buyimg.com/imgzone/jfs/t1/15064/10/13992/303230/5ca55ee8E4ca04189/f2bdbc6f782d66b9.jpg");
			return fileVo;
		} catch (Exception e) {
			e.printStackTrace();
			//表示为恶意程序
//			return new FileVo(1,null,null,null);
			fileVo.setError(1);
			return fileVo;
		}
		
	}

}
