/**
 * 
 */
package com.iss.itreasury.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xintan
 * 
 * 校验请求是否由系统直接提交，而非被篡改。篡改的方式指：
 * 1.页面通过window.open方式打开，可直接修改url传入的参数，以查看数据；
 * 2.客户直接在IE中输入菜单链接，打开没有权限访问的页面；
 *
 */
public class RequestAlteredValidator {
	
	private static Log4j log4j = new Log4j(Constant.ModuleType.SYSTEM);
	
	public static void validate(HttpServletRequest request) throws IException{
		/**
		String referer = request.getHeader("referer");
		String password = request.getParameter("Password");
		String isCheckedByPassword = (String) request.getAttribute("_isCheckedByPassworrd");
		log4j.info("referr:" + referer);
		log4j.info("Password:" + password);
		log4j.info("isCheckedByPassword:" + isCheckedByPassword);
		log4j.info("pathInfo:" + request.getPathInfo());
		log4j.info("queryInfo:" + request.getQueryString());
		log4j.info("pathtraslated:" + request.getPathTranslated());
		log4j.info("requestURI:" + request.getRequestURI());
		log4j.info("locale:" + request.getLocale());		
		
		if(referer==null && isCheckedByPassword==null && password==null)
		{
			throw new  IException("对不起，你没有使用该功能的权限");
		}
		
		if(isCheckedByPassword==null && password!=null)
		{
			if(!Encryptiontools.validateEncryption(password))
			{
				throw new  IException("对不起，您没有使用该功能的权限！");
			}
			request.setAttribute("_isCheckedByPassworrd", "true");
			log4j.info("Password 验证通过");
		}
		**/
	}
}
