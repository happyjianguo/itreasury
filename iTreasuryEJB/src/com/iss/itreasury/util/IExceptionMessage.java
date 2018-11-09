/*
 * Created on 2003-8-18
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.util;

/**
 * @author yychen
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;


//import com.sun.rsasign.s;


public class IExceptionMessage
{

	private static int intKeyCount = 0;
	private static String[] strExceptionMessageList = null;
	private static String[] strKeyList = null;
	private static boolean blnHasInit = false;

	public IExceptionMessage()
	{
	}
	static {
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

	public static String getExceptionMessage(String strExceptionCode)
	{
		String strExceptionMessage = null;
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

		if (strExceptionMessage == null)
		{
			return null;
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

	public static String getExceptionMessage(String strExceptionCode, String sParam)
	{
		String[] sParams = new String[1];
		sParams[0] = sParam;

		return getExceptionMessage(strExceptionCode, sParams);
	}

	public static String getExceptionMessage(String strExceptionCode, String sParam1, String sParam2)
	{
		String[] sParams = new String[2];
		sParams[0] = sParam1;
		sParams[1] = sParam2;

		return getExceptionMessage(strExceptionCode, sParams);
	}

	private static String UnicodeToGBK(String s)
	{
		try
		{
			if (s == null || s.equals(""))
				return "";
			String newstring = null;
			newstring = new String(s.getBytes("ISO8859_1"), "GBK");
			return newstring;
		}
		catch (UnsupportedEncodingException e)
		{
			return s;
		}
	}

	public static String getExceptionMSG(Exception e) {
		// String strErrorMessage = e.getMessage();
		String strErrorMessage = null;

		String strErrorCode = null;

		String[] strParams = null;

		Exception orgException = null;

		//
		if (e instanceof IRollbackException) {
			strErrorCode = ((IRollbackException) e).getErrorCode();
			orgException = ((IRollbackException) e).getOriginalException();
			strParams = ((IRollbackException) e).getMessageArgs();
			
		} else if (e instanceof IRuntimeException) {
			strErrorCode = ((IRuntimeException) e).getErrorCode();
			orgException = ((IRuntimeException) e).getOriginalException();
			strParams = ((IRuntimeException) e).getMessageArgs();
		} else if (e instanceof ITreasuryException) {
			strErrorCode = ((ITreasuryException) e).getErrorCode();
			orgException = ((ITreasuryException) e).getOriginalException();
			strParams = ((ITreasuryException) e).getMessageArgs();
		} else if (e instanceof IException) {
			strErrorCode = ((IException) e).getErrorCode();
			orgException = ((IException) e).getOriginalException();
			strParams = ((IException) e).getMessageArgs();
		} else if(!e.getMessage().equals("")){
			orgException = e;
			strParams = new String[] { e.getMessage() };
		}
		else {
			strErrorCode = "Gen_E001";
			orgException = e;
			strParams = new String[] { e.getMessage() };
		}
		
		if(strErrorCode != null&&(strErrorCode.trim().charAt(0)>'a'&&strErrorCode.trim().charAt(0)<'z'||strErrorCode.trim().charAt(0)>'A'&&strErrorCode.trim().charAt(0)<'Z')){		
		if (strErrorCode != null && strErrorCode.length() <= 11
				&& strErrorCode.length() >= 5) {
			// modified by mzh_fu 2007/05/25 不抛出异常码
			// strErrorMessage = "[" + strErrorCode + "] ";
			strErrorMessage = "";

			if (IExceptionMessage.getExceptionMessage(strErrorCode, strParams) != null)
				strErrorMessage += IExceptionMessage.getExceptionMessage(
						strErrorCode, strParams);

			String originalMsg = "originalException is null.";
			if (orgException != null) {
				originalMsg = orgException.getMessage();
				int index = originalMsg.indexOf("Start server side"); // Start
																		// server
				if (index != -1) {
					originalMsg = originalMsg.substring(0, index);
				}
				// added by mzh_fu 2007/08/17
				else if(strErrorMessage==null || "".equals(strErrorMessage)){
					strErrorMessage = originalMsg;
				}
			}
			System.out.println("originalException is :" + originalMsg);
		}	}
		else {
			strErrorMessage = e.getMessage();
		}

		return strErrorMessage;
	}
	public static void main(String[] args) throws IException
	{
		/*String strExceptionMessage = "[?]在证券模块与财务模块中的[?]或[?]不符，请进行调查！";
		String[] sParams = { "123", "345", "4565" };
		StringBuffer sbExceptionMsg = new StringBuffer();
		
		//将其中的?号替换对应的sParams
		StringTokenizer st = new StringTokenizer(strExceptionMessage, "?");
		
		System.out.println("count = " + st.countTokens());
		if (sParams == null || st.countTokens() - 1 != sParams.length)
		{
			throw new IException("参数错误！");
		}
		
		for (int i = 0; i < sParams.length; i++)
		{
			sbExceptionMsg.append(st.nextToken()).append(sParams[i]);
		}
		sbExceptionMsg.append(st.nextToken());
		
		System.out.println(sbExceptionMsg.toString());*/
		try
		{
			throw new IException("Sett_E001");
		}
		catch (Exception ie)
		{
			System.out.println(ie.getMessage());
			System.out.println(IExceptionMessage.getExceptionMSG(ie));
		}
	}
}
