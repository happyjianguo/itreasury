/**
 * @author zwsun
 * created on 2007/7/30
 * 过滤url地址
 */
package com.iss.itreasury.util;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iss.itreasury.util.PageController;
import com.iss.itreasury.util.SessionMng;


public class UrlFilter implements Filter{

	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("destroy");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		
		HttpServletRequest hreq=(HttpServletRequest)request;
		HttpServletResponse hres=(HttpServletResponse)response;
		
		//只改写在session中含有模块id的url
		if(hreq.getSession()!=null ){
			SessionMng sessionMng=(SessionMng)hreq.getSession().getAttribute("sessionMng");
			if(sessionMng!=null && sessionMng.m_lModuleID!=-1){
				String strModuleFolder= PageController.getModuleFolder(sessionMng.m_lModuleID);
				StringBuffer dispatchURL=new StringBuffer();			
				String servletPath=hreq.getServletPath();//原来的转发路径				
				String[] replaceStrings={hreq.getContextPath(),"/"+strModuleFolder};//需要去掉的字符串
				
				for(int i=0;i<replaceStrings.length && replaceStrings[i]!="/";i++){
					servletPath=servletPath.replaceAll(replaceStrings[i], "");
				}
				
				//构建转发路径
				dispatchURL.append("/"+strModuleFolder);//模块名
				dispatchURL.append(servletPath);//去掉context和模块名后的路径
				
				/*
				if(hreq.getQueryString()!=null){
					dispatchURL.append("?"+hreq.getQueryString());//参数
				}
				*/

				//修改所有参数
				Enumeration params=hreq.getParameterNames();
				String paramName="";
				String[] paramValues=null;
				boolean firstFlag=true;
				while(params.hasMoreElements()){
					paramName=(String)params.nextElement();
					if(paramName.trim()!=""){
						paramValues=hreq.getParameterValues(paramName);
						for(int i=0;i<paramValues.length;i++){
							//考虑转发的情况，因此路径上要去掉contextpath
							if(paramValues[i].startsWith("/")){//该属性为一个url
								for(int j=0;j<replaceStrings.length && replaceStrings[i]!="/";j++){
									paramValues[i]=paramValues[i].replaceAll(replaceStrings[j], "");
								}
								paramValues[i]="/"+strModuleFolder+paramValues[i];
							}
							if(firstFlag){
								dispatchURL.append("?");
								firstFlag=false;
							}else{
								dispatchURL.append("&");
							}					
							dispatchURL.append(paramName+"="+paramValues[i]);
						}				
					}			
				}																
						
				hreq.getRequestDispatcher(dispatchURL.toString()).forward(hreq,hres);
				return;
			}else{
				filterChain.doFilter(request, response);
			}				
			
		}		

	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("init");
	}

}
