package com.iss.itreasury.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class FilterEncoding implements Filter {

	protected String encoding = null;
	
	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub
		if(encoding != null)
		{
			arg0.setCharacterEncoding(encoding);
			arg1.setCharacterEncoding(encoding);
		}
		else {
			arg0.setCharacterEncoding("GBK");
			arg1.setCharacterEncoding("GBK");
		}
		arg2.doFilter(arg0, arg1); 
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		// 从web.xml中读取encoding的值
		encoding = arg0.getInitParameter("encoding");
	}

}
