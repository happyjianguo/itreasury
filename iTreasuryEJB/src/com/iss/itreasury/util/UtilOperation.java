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
		final String[] dataType = { "double", "long", "java.lang.String", "java.sql.Timestamp" }; //֧�ֵ���������
		BeanInfo info = null;
		try
		{
			info = Introspector.getBeanInfo(dataEntity.getClass());
		}
		catch (IntrospectionException e)
		{
			throw new ITreasuryException("Java Bean.��ʡ�쳣����", e);
		}
		//Log.print("----------��dataentityת����hidden----------");
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
				throw new ITreasuryException("��dataentity����ת��Ϊhidden����ִ���", e);
			}
			catch (InvocationTargetException e)
			{
				throw new ITreasuryException("��dataentity����ת��Ϊhidden����ִ���", e);
			}
		}
		return strOutput;
	}
	/**
	 * ����˵�������ɲ����ؽ��׺�(��)
	 * add by zwsun, 2007/9/10
	 * @param inut
	 * @param lID ���ױ������
	 * @return @throws
	 *         SQLException
	 */
	public static String getNewTransactionNo(InutParameterInfo inut, long lID) throws IException
	{
		String strTransNo = "";
		if(lID==-1){
			throw new IException("δ���ݲ�������id");
		}
		strTransNo=""+lID;
		return strTransNo;
	}	
}