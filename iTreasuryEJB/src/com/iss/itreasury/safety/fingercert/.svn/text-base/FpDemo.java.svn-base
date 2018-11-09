package com.iss.itreasury.safety.fingercert;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FpDemo {

	public String tlr_no; // 用户ID
	public String sys_id; // 机构ID
	public String randomNum; // 加密随机数
	public String fingerValue; // 指纹特征

	public FpDemo() {
	}

	public FpDemo(String tlr_no, String sys_id, String randomNum) {
		this.tlr_no = tlr_no;
		this.sys_id = sys_id;
		this.randomNum = randomNum;
	}

	public FpDemo(String tlr_no, String sys_id, String randomNum,
			String fingerValue) {
		this.tlr_no = tlr_no;
		this.sys_id = sys_id;
		this.randomNum = randomNum;
		this.fingerValue = fingerValue;
	}

	/**
	 *功能: 模拟业务后台向员工认证模式接口发送HTTP请求
     *参数: 员工认证接口的URL
     *返回值: 响应字符串，认证模式结果：1--指纹 0--密码
     *请求表单域： randomNum     加密随机数
     *           tlr_no	用户名，用户的唯一标识
     *           sys_id	系统ID，一般传默认值 1
	 * 向查询员工认证模式接口发送http请求 并输出响应的字符串
	 */
	public String queryMode(String POST_URL) throws IOException {

		/*
		 * 调用DES3的加密函数：DES3Encrypt(data, randomNum) 为表单参数
		 * 
		 * tlr_no, sys_id 进行加密,
		 * 
		 * 并将其拼接成请求参数：Query_Param
		 */
		tlr_no = DES3.DES3Encrypt(this.tlr_no, this.randomNum);

		sys_id = DES3.DES3Encrypt(this.sys_id, this.randomNum);

		String Query_Param = "randomNum=" + randomNum + "&tlr_no=" + tlr_no
				+ "&sys_id=" + sys_id;

		// Post请求的url
		URL postUrl = new URL(POST_URL);
		// 打开连接
		HttpURLConnection connection = (HttpURLConnection) postUrl
				.openConnection();

		// 设置是否向connection输出，因为这个是post请求，参数要放在
		// http正文内，因此需要设为true
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		// Post 请求不能使用缓存
		connection.setUseCaches(false);
		// URLConnection.setFollowRedirects是static函数，作用于所有的URLConnection对象。
		// URLConnection.setInstanceFollowRedirects是成员函数，仅作用于当前函数
		connection.setInstanceFollowRedirects(true);
		// 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
		// 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
		// 进行编码
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		// 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
		// 要注意的是connection.getOutputStream会隐含的进行connect。
		connection.connect();
		DataOutputStream out = new DataOutputStream(connection
				.getOutputStream());
		// DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写道流里面
		out.writeBytes(Query_Param);
		out.flush();
		out.close();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream(), "UTF-8"));// 设置编码,否则中文乱码
		String line = "";
		String results = "";// 存储响应http请求后的返回值

		// 解密随机数为原机密随机数+1
		randomNum = String.valueOf(Integer.parseInt(randomNum) + 1);

		System.out.println("=========认证模式============");
		while ((line = reader.readLine()) != null) {
			// 调用DES3.DES3Decrypt(data, randomNum)函数将相应字符串进行解密
			line = DES3.DES3Decrypt(line, randomNum);
			results += line;
			System.out.println(line);
		}
		System.out.println("=============================");
		reader.close();
		connection.disconnect();

		return results;
	}
	
	
	
	

	/**
	 * 向查询员工指纹认证接口发送http请求 并输出响应的字符串
	 * 功能: 模拟业务后台向指纹认证接口发送HTTP请求
     * 参数: 指纹认证接口的URL
     * 返回值: 响应字符串，验证指纹结果：0--验证通过 非0--验证失败
     * 请求表单域： randomNum     加密随机数
     *              tlr_no	用户名，用户的唯一标识
     *              sys_id	系统ID，一般传默认值 1
     *              fingerValue   指纹特征
	 */
	public String checkFinger(String POST_URL) throws IOException {

		/*
		 * 调用DES3的加密函数：DES3Encrypt(data, randomNum) 为表单参数
		 * 
		 * tlr_no, sys_id, fingerValue 进行加密,
		 * 
		 * 并将其拼接成请求参数：Check_Param
		 */
		System.out.println("tlr_no:"+tlr_no);
		tlr_no = DES3.DES3Encrypt(this.tlr_no, this.randomNum);

		sys_id = DES3.DES3Encrypt(this.sys_id, this.randomNum);

		fingerValue = DES3.DES3Encrypt(this.fingerValue, this.randomNum);

		String Check_Param = "randomNum=" + randomNum + "&tlr_no=" + tlr_no
				+ "&sys_id=" + sys_id + "&fingerValue=" + fingerValue;

		// Post请求的url，与get不同的是不需要带参数
		
		System.out.println(POST_URL);
		URL postUrl = new URL(POST_URL);
		// 打开连接
		HttpURLConnection connection = (HttpURLConnection) postUrl
				.openConnection();

		// 设置是否向connection输出，因为这个是post请求，参数要放在
		// http正文内，因此需要设为true
		connection.setDoOutput(true);
		// Read from the connection. Default is true.
		connection.setDoInput(true);
		// Set the post method. Default is GET
		connection.setRequestMethod("POST");
		// Post cannot use caches
		// Post 请求不能使用缓存
		connection.setUseCaches(false);
		// URLConnection.setFollowRedirects是static函数，作用于所有的URLConnection对象。
		// URLConnection.setInstanceFollowRedirects是成员函数，仅作用于当前函数
		connection.setInstanceFollowRedirects(true);
		// 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
		// 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
		// 进行编码
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		// 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
		// 要注意的是connection.getOutputStream会隐含的进行connect。

		DataOutputStream out = new DataOutputStream(connection
				.getOutputStream());
		// DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写道流里面
		out.writeBytes(Check_Param);
		out.flush();
		out.close(); // flush and close
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream(), "US-ASCII"));// 设置编码,否则中文乱码ISO-8859-1
		String line = "";
		String results = "";// 存储响应http请求后的返回值

		// 解密随机数为原机密随机数+1
		randomNum = String.valueOf(Integer.parseInt(randomNum) + 1);

		System.out.println("===========验证指纹 ==========");
		while ((line = reader.readLine()) != null) {
			// 调用DES3.DES3Decrypt(data, randomNum)函数将相应字符串进行解密
			line = DES3.DES3Decrypt(line, randomNum);
			results += line;
			System.out.println(line);
		}
		System.out.println("=============================");

		reader.close();
		connection.disconnect();

		return results;
	}

}
