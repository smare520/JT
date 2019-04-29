package com.jt;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class TestHttpClient {
	/**
	 * 		实现步骤：
	 *	1.创建httpClient对象
	 *	2.指定url请求地址
	 *	3.指定请求方式 get/post(查询操作get请求，更新操作/涉密 post提交，)
	 *	4.发起请求获取返回值数据（json串）
	 *	5.判断请求是否正确，检查状态码200
	 *	6.从返回值对象中获取数据（json）,之后转化对象
	 * @throws Exception 
	 * @throws ClientProtocolException 
	 */
	@Test
	public void testGet() throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String url ="https://www.jd.com/";
		HttpGet httpGet = new HttpGet(url);
		HttpPost httpPost=new HttpPost(url);
		CloseableHttpResponse httpresponse = httpClient.execute(httpGet);
//		CloseableHttpResponse httpsotpresponse = httpClient.execute(httpPost);
		
		
		if(httpresponse.getStatusLine().getStatusCode()==200) {
			System.out.println("ok");
			String result= EntityUtils.toString(httpresponse.getEntity());
			System.out.println(result);
		}
	}
}
