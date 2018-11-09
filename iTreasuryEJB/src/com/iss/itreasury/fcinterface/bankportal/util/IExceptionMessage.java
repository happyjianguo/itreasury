/*
 * Created on 2003-8-18
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.fcinterface.bankportal.util;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.business.BusinessException;

/**
 * 页面异常信息对象<br>
 * 存储需要页面显示的异常信息，如果需要异常代码和描述信息的转换，也在此处处理
 * @author mxzhou
 */
public class IExceptionMessage
{

	private static int intKeyCount = 0;
	private static String[] strExceptionMessageList = null;
	private static String[] strKeyList = null;
	private static boolean blnHasInit = false;

	static
	{
		if (!blnHasInit)
		{
			blnHasInit = true;
			try
			{
				Properties p = new Properties();
				FileInputStream is = new FileInputStream("IExceptionMessage.properties");
				p.load(is);
				is.close();

				Enumeration enuKey = p.propertyNames();
				Vector vctKey = new Vector();
				while (enuKey.hasMoreElements())
				{
					vctKey.add(enuKey.nextElement());
				}

				intKeyCount = vctKey.size();
				strKeyList = new String[intKeyCount];
				strExceptionMessageList = new String[intKeyCount];

				for (int i = 0; i < intKeyCount; i++)
				{
					String strKey = (String) vctKey.elementAt(i);
					strKeyList[i] = strKey;
					strExceptionMessageList[i] = p.getProperty(strKey);

					int index = strExceptionMessageList[i].indexOf("#");
					if (index != -1)
					{
						strExceptionMessageList[i] = strExceptionMessageList[i].substring(0, index).trim();
					}
				}
			}
			catch (Exception e)
			{
				blnHasInit = false;
				e.printStackTrace();
			}
		}
	}
	
	public IExceptionMessage()
	{
	}

	public static String getExceptionMessage(String strExceptionCode)
	{
		String strExceptionMessage = "";
		if (intKeyCount > 0)
		{
			for (int i = 0; i < intKeyCount; i++)
			{
				if (strExceptionCode.equals(strKeyList[i]))
				{
					strExceptionMessage = UnicodeToGBK(strExceptionMessageList[i]);
					break;
				}
			}
		}
		return strExceptionMessage;
	}

	/**
	 * Add by eagle
	 * @param strExceptionCode
	 * @param sParams
	 * @return
	 */
	public static String getExceptionMessage(String strExceptionCode, String[] sParams)
	{
		String strExceptionMessage = getExceptionMessage(strExceptionCode);
		StringBuffer sbExceptionMsg = new StringBuffer();

		if (strExceptionMessage == null || strExceptionMessage.trim().length() <= 0)
		{
			return "";
		}

		StringTokenizer st = new StringTokenizer(strExceptionMessage, "?");
		if (sParams == null || st.countTokens() - 1 != sParams.length)
		{
			return strExceptionMessage;
		}

		//将其中的?号替换对应的sParams
		for (int i = 0; i < sParams.length; i++)
		{
			sbExceptionMsg.append(st.nextToken()).append(sParams[i]);
		}
		sbExceptionMsg.append(st.nextToken());

		return sbExceptionMsg.toString();
	}

	public static String getExceptionMSG(Exception e)
	{
		String strErrorMessage = null;
		String strErrorCode = null;

		if (e instanceof BusinessException)
		{
			strErrorCode = ((BusinessException) e).getErrorCode();
			strErrorMessage = e.getMessage();
		}
		else
		{
			strErrorCode = null;
			strErrorMessage = "系统忙，请稍后再试。如果仍然有问题，请通知系统管理员!";
		}

		return strErrorMessage;
	}
	
	private static String UnicodeToGBK(String s)
	{
		try
		{
			if (s == null || s.equals(""))
			{
				return "";
			}
			String newstring = null;
			newstring = new String(s.getBytes("ISO8859_1"), "GBK");
			return newstring;
		}
		catch (UnsupportedEncodingException e)
		{
			return s;
		}
	}
}
