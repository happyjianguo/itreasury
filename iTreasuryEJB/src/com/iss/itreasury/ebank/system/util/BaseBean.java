/**
 * Title:        		BankPortal
 * Description:         
 * Copyright:           Copyright (c) 2005
 * Company:             iSoftStone
 * @author              mxzhou 
 * @version
 * Date of Creation     2005-5-10
 */

package com.iss.itreasury.ebank.system.util;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.iss.itreasury.ebank.util.SessionOB;
import com.iss.itreasury.util.DataFormat;
/**
 * @author mxzhou TODO To change the template for this generated type comment go
 *         to Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class BaseBean implements Serializable
{	

	/**
     * 请求会话信息，为支持多级办事处增加
     * @return
     */
    private SessionOB sessionMng = null;
    
	public SessionOB getSessionMng()
	{
		return sessionMng;
	}

	public void setSessionMng(SessionOB sessionMng)
	{
		this.sessionMng = sessionMng;
	}
	/**
	 * 重载Object的toString方法，所有的子类都可以通过此方法输出所有内容
	 */
	public String toString()
	{
		StringBuffer res = new StringBuffer(getClass().getName() + " \n");
		Method[] methods = getClass().getDeclaredMethods();
		for (int i = 0; i < methods.length; i++)
		{
			Method tmp = methods[i];
			String mName = tmp.getName();
			if (mName.startsWith("get"))
			{
				String fName = mName.substring(3);
				res.append(fName + " = ");
				try
				{
					Object o = tmp.invoke(this, new Object[]{});
					if (o == null)
					{
						res.append(" null \n");
						continue;
					}
					if (o instanceof Double)
					{
						res.append("" + ((Double) o).doubleValue() + " \n");
					}
					else if (o instanceof Float)
					{
						res.append("" + ((Float) o).floatValue() + " \n");
					}
					else if (o instanceof Long)
					{
						res.append("" + ((Long) o).longValue() + " \n");
					}
					else if (o instanceof String)
					{
						res.append("" + (String) o + " \n");
					}
					else if (o instanceof Date)
					{
						res.append("" + ((Date) o).toString() + " \n");
					}
					else
					{
						res.append("" + o.toString() + " \n");
					}
				}
				catch (IllegalAccessException e)
				{
					continue;
				}
				catch (InvocationTargetException e)
				{
					continue;
				}
			}

		}
		return res.toString();
	}

	/**
	 * 从request中得到完整的dataentity
	 * 支持对象的嵌套
	 */
	public void convertRequestToDataEntity(ServletRequest request)
		throws Exception
	{
		//Build a list of relevant request parameters from this request
		HashMap properties = new HashMap();
		// Iterator of parameter names
		Enumeration names = null;
		Boolean isChinese = (Boolean)request.getAttribute("_isChinese");
		//System.out.println("isChinese value:"+(isChinese==null?"null":""+isChinese.booleanValue()));
		names = request.getParameterNames();
		//System.out.println("Reqest中的名值对：");
		while (names.hasMoreElements())
		{
			String name = (String) names.nextElement();
			//System.out.println("name:\""+name+"\"");
			String[] values = request.getParameterValues(name);
			for(int i = 0; i < values.length; i++)
			{
				if(values[i] == null || values[i].trim().length() <= 0)
				{
					values[i] = null;
				}
				else
				{
					String str = values[i].trim();
					if(isChinese == null || !isChinese.booleanValue())
					{
						values[i] = DataFormat.toChinese(str);
					}
				}
				//System.out.println("    value:\""+values[i]+"\"");
			}
			//System.out.println("Parameter : " + name + " = " + request.getParameter(name));
			properties.put(name, values);
		}
		request.setAttribute("_isChinese", new Boolean(true));
		// Set the corresponding properties of our helper bean
		try
		{
			BeanUtils.populate(this, properties);
		}
		catch (Exception e)
		{
			throw new Exception("从request映射到dataEntity时出错，出错原因："+e.getMessage(), e);
		}
		HttpSession session = ((HttpServletRequest)request).getSession(true);
		SessionOB sessionMng = (SessionOB) session.getAttribute("sessionMng");
		this.setSessionMng(sessionMng);
	}
}