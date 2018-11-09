/**
 * 转换汉字处理类
 * @author jinchen
 */ 
package com.iss.itreasury.ebank.privilege.util;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;


public class GetData
{
	/**
	 * 
	 */
	public GetData()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	public HttpServletRequest setValue ( HttpServletRequest request ) 
	{
		Enumeration en = request.getParameterNames () ;
		while (en.hasMoreElements())
		{
			String strName = (String) en.nextElement ( ) ;
			String strValue = request.getParameter ( strName ) ;
			if (strValue != null)
			{
				strValue = toChinese ( strValue ) ;
				if (request.getAttribute ( strName ) == null)
				{
					request.setAttribute ( strName , strValue ) ;
				}
			}
		}
		return request;
	}
	public String toChinese ( String str )
	{
			try
			{
				return new String (str.getBytes ("ISO8859_1") , "GBK" );
			} catch (UnsupportedEncodingException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
	}
}
