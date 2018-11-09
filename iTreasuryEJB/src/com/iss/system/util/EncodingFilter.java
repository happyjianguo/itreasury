package com.iss.system.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 审批流使用中文过滤器
 * @author xfma3
 * 2011-05-03
 */
public class EncodingFilter extends HttpServlet implements Filter {
	 
	/**
	 * version 1.0
	 */
	private static final long serialVersionUID = 1L;
	
	private String encoding;
	
	public void destroy() {
		this.encoding = null;
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) 
		throws IOException, ServletException {
		// TODO Auto-generated method stub
		MyRequest req = new MyRequest( (HttpServletRequest)request,encoding);//设置代理请求
		filterChain.doFilter(req, response);
	}
	
	public void init(FilterConfig arg0) throws ServletException {
		this.encoding = arg0.getInitParameter("encoding");
	}
	
}
class MyRequest extends HttpServletRequestWrapper{//自定义的代理请求类
	HttpServletRequest request;
	String encoding;
	
	public MyRequest(HttpServletRequest request,String encoding) {
		super(request);
		this.request = request;
		this.encoding = encoding;
	}
	
	public String[] getParameterValues(String name){
		String strs[] = super.getParameterValues(name);
		for(int i=0;i<strs.length;i++){
			strs[i]  = this.myEncoding(strs[i]);
		}
		return strs;
	}
	
	private String myEncoding(String input){
		String output ="";
		try {
			output = new String(input.getBytes("ISO-8859-1"),encoding); //注意同页面的编码保持一致
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}
}