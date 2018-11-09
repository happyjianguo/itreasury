package com.iss.itreasury.util;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
/**
 * @author gqzhang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class UtilOperation
{
	public static String outputDataEntityHiddenElement(Object dataEntity) throws ITreasuryException
	{
		String strOutput = "";
		final String[] dataType = { "double", "long", "java.lang.String", "java.sql.Timestamp" }; //支持的数据类型
		BeanInfo info = null;
		try
		{
			info = Introspector.getBeanInfo(dataEntity.getClass());
		}
		catch (IntrospectionException e)
		{
			throw new ITreasuryException("Java Bean.内省异常发生", e);
		}
		//Log.print("----------从dataentity转化到hidden----------");
		PropertyDescriptor[] p = info.getPropertyDescriptors();
		for (int n = 0; n < p.length; n++)
		{
			if (p[n].getName().compareToIgnoreCase("class") == 0)
				continue;
			try
			{
				//Log.print("key:" + p[n].getName() + "// value:" + p[n].getReadMethod().invoke(dataEntity,null));
				String strValue = (p[n].getReadMethod().invoke(dataEntity, new Object[]{}) == null) ? "" : String.valueOf(p[n].getReadMethod().invoke(dataEntity, new Object[]{}));
				String strReturnType = p[n].getReadMethod().getReturnType().getName();
				if (strReturnType.equals(dataType[0]) && Double.parseDouble(strValue) == 0.0)
				{ //parameter type is double
					strValue = null;
				}
				else
					if (p[n].getReadMethod().getReturnType().getName().equals(dataType[1]) && Long.parseLong(strValue) == -1)
					{ //parameter type is long
						strValue = null;
					}
					else
						if (p[n].getReadMethod().getReturnType().getName().equals(dataType[2]) && strValue.equals(""))
						{ //parameter type is String
							strValue = null;
						}
				if (strValue != null)
				{
					if (!p[n].getName().equals("id") && !p[n].getName().equals("currencyId") && !p[n].getName().equals("officeId"))
					{
						strOutput += "<input type='hidden' name= '" + p[n].getName() + "' value='" + strValue + "'> \n";
					}
				}
			}
			catch (IllegalAccessException e)
			{
				// TODO Auto-generated catch block
				throw new ITreasuryException("把dataentity置入转化为hidden域出现错误", e);
			}
			catch (InvocationTargetException e)
			{
				throw new ITreasuryException("把dataentity置入转化为hidden域出现错误", e);
			}
		}
		return strOutput;
	}
	/**
	 * 方法说明：生成并返回交易号(新)
	 * add by zwsun, 2007/9/10
	 * @param inut
	 * @param lID 交易表的主键
	 * @return @throws
	 *         SQLException
	 */
	public static String getNewTransactionNo(InutParameterInfo inut, long lID) throws IException
	{
		String strTransNo = "";
		if(lID==-1){
			throw new IException("未传递参数主键id");
		}
		strTransNo=""+lID;
		return strTransNo;
	}	
}