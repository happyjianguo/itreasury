/**
 * 
 */
package com.iss.itreasury.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xintan
 * 
 * У�������Ƿ���ϵͳֱ���ύ�����Ǳ��۸ġ��۸ĵķ�ʽָ��
 * 1.ҳ��ͨ��window.open��ʽ�򿪣���ֱ���޸�url����Ĳ������Բ鿴���ݣ�
 * 2.�ͻ�ֱ����IE������˵����ӣ���û��Ȩ�޷��ʵ�ҳ�棻
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
			throw new  IException("�Բ�����û��ʹ�øù��ܵ�Ȩ��");
		}
		
		if(isCheckedByPassword==null && password!=null)
		{
			if(!Encryptiontools.validateEncryption(password))
			{
				throw new  IException("�Բ�����û��ʹ�øù��ܵ�Ȩ�ޣ�");
			}
			request.setAttribute("_isCheckedByPassworrd", "true");
			log4j.info("Password ��֤ͨ��");
		}
		**/
	}
}
