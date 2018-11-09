/*
 * Created on 2005-5-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.fcinterface.bankportal.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.ResultSetDynaClass;

import com.iss.itreasury.fcinterface.bankportal.constant.DataType;
import com.iss.itreasury.fcinterface.bankportal.sysframe.dataentity.BaseDataEntity;
import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.system.SystemException;

/**
 * DAO������
 * @author mxzhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DAOUtil
{
	/**��־����*/
	private static Logger log = new Logger(DAOUtil.class);
	private static HashMap hmFields = new HashMap();
	private static HashMap hmFieldsNoPrefix = new HashMap();
	private static boolean isInit = false;
	
	//��ʼ�����棬Ϊ�Ż����ܶ���
	private static void init() throws Exception
	{
		long[] typeCodes = DataType.getAllCode();
		for(int i=0;i<typeCodes.length;i++)
		{
			HashMap hashMap = new HashMap();
			//System.out.println("*******init dataType********:"+DataType.getName(typeCodes[i]));
			hmFields.put(DataType.getName(typeCodes[i]),hashMap);
		}
		isInit = true;
	}
	
	
	/**
	 * ��ResultSet�л�ȡ��ѯ���
	 * 
	 * @param className ��Ҫ���ҵ����ݿ���Ӧ��Data Entity������
	 * @param isNeedPrefix ���ݿ��ֶ��Ƿ���Ҫǰ׺
	 * @return @throws  SystemException
	 */
	public static BaseDataEntity getDataEntityFromResultSet(ResultSet transRS, Class dataEntityClass, boolean isNeedPrefix)
			throws SystemException
	{

		
		BaseDataEntity res = null;
		try
		{
			if(!isInit)
		    {
		    	init();
		    }

			ResultSetDynaClass rsdc = new ResultSetDynaClass(transRS);
			//log.debug("--------ResultSetDynaClass" + rsdc);
			Iterator rows = rsdc.iterator();

			if(rows == null || !rows.hasNext())
			{
				return null;
			}
			
			try
			{
				res = (BaseDataEntity) dataEntityClass.newInstance();
			}
			catch (InstantiationException e)
			{
				throw new SystemException("Data Entityʵ�����쳣����", e);
			}
			catch (IllegalAccessException e)
			{
				throw new SystemException("Data Entityʵ�����Ƿ������쳣����", e);
			}
			
			while (rows.hasNext())
			{
				
				DynaBean row = (DynaBean) rows.next();
				if (row == null)
				{
					continue;
				}
			//System.out.println("DynaBean:" + row.get("s_idofbankseg2"));
			//System.out.println("DynaBean:" + row.get("dt_createtime"));
				BeanInfo beanInfo = Introspector.getBeanInfo(dataEntityClass);
				PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
				for (int i = 0; i < properties.length; i++)
				{
					Class p = properties[i].getPropertyType();
					//log.debug("Property type:\n "+ p.getName()+ "\n Property name:\n "+ properties[i].getName());
					String fieldName = properties[i].getName();
//					System.out.println(properties[i].getValue("dt_createtime"));
					fieldName = getFieldName(fieldName, p, isNeedPrefix);
					if(fieldName == null)
					{
						continue;
					}
					Method writeMethod = properties[i].getWriteMethod();
					if (writeMethod == null)
					{
						continue;
					}

					Object valueObj = null;
					try
					{
						valueObj = row.get(fieldName);
						if (valueObj == null)//������DataEntity�ж����ж�Ӧ�����ݿ����û�еı���
						{
							continue;
						}
					}
					catch (IllegalArgumentException e)
					{
						//������DataEntity�ж����ж�Ӧ�����ݿ����û�еı���
						continue;
					}
					if (valueObj instanceof BigDecimal)
					{
						if (p.getName().compareToIgnoreCase("long") == 0)
						{
							valueObj = new Long(valueObj.toString());
						}
						else if (p.getName().compareToIgnoreCase("double") == 0)
						{
							valueObj = new Double(valueObj.toString());
						}
					}

					else if(valueObj instanceof java.sql.Date)
					{
						valueObj = new Date(((Timestamp)transRS.getTimestamp(fieldName)).getTime());
					}
		
					else if (valueObj instanceof Timestamp)
					{
						
						valueObj = new Date(((Timestamp)valueObj).getTime());
						//valueObj = new Date(((Date)valueObj).getTime());
					}
					else if (valueObj instanceof Date)
					{
						valueObj = transRS.getTimestamp(fieldName);
						//valueObj = new Date(((Date)valueObj).getTime());
					}
					
					else if(valueObj instanceof String)
					{
						
					}
					else
					{
						continue;
					}

					Object[] args = {valueObj};
					try
					{
						writeMethod.invoke(res, args);
					}
					catch (IllegalAccessException e)
					{
						throw new SystemException("Data Entityʵ�����Ƿ��쳣����", e);
					}
					catch (InvocationTargetException e)
					{
						throw new SystemException("Data Entity����Ŀ���쳣����", e);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		catch (SQLException e)
		{
			throw new SystemException("���ݿ��쳣����", e);
		}
		catch (IntrospectionException e)
		{
			throw new SystemException("Java Bean.��ʡ�쳣����", e);
		}
		catch (Exception e)
		{
			throw new SystemException("��ʼ���쳣����", e);
		}
		return res;
	}

	/**
	 * ��ResultSet�л�ȡ��ѯ���
	 * 
	 * @param className
	 *            ��Ҫ���ҵ����ݿ���Ӧ��Data Entity������
	 * @param
	 * @return @throws
	 *         ITreasuryDAOException
	 */
	public static Collection getDataEntitiesFromResultSet(ResultSet transRS, Class dataEntityClass, boolean isNeedPrefix)
			throws SystemException
	{		
		BaseDataEntity res = null;
		ArrayList resList = new ArrayList();
		try
		{
		    if(!isInit)
		    {
		    	init();
		    }
		    
			ResultSetDynaClass rsdc = new ResultSetDynaClass(transRS);			
			Iterator rows = rsdc.iterator();

			while (rows.hasNext())
			{
				try
				{
					res = (BaseDataEntity) dataEntityClass.newInstance();
				}
				catch (InstantiationException e)
				{
					throw new SystemException("Data Entityʵ�����쳣����", e);
				}
				catch (IllegalAccessException e)
				{
					throw new SystemException("Data Entityʵ�����Ƿ������쳣����", e);
				}

				DynaBean row = (DynaBean) rows.next();
				if (row == null)
				{
					continue;
				}
				//log.debug("DynaBean" + row);
				BeanInfo beanInfo = Introspector.getBeanInfo(dataEntityClass);
				PropertyDescriptor[] properties = beanInfo
						.getPropertyDescriptors();
				for (int i = 0; i < properties.length; i++)
				{
					Class p = properties[i].getPropertyType();
					String fieldName = properties[i].getName();
					fieldName = getFieldName(fieldName, p, isNeedPrefix);
					if(fieldName == null)
					{
						continue;
					}
					Method writeMethod = properties[i].getWriteMethod();
					if (writeMethod == null)
					{
						continue;
					}
					
					Object valueObj = null;
					try
					{
						valueObj = row.get(fieldName);
						if (valueObj == null)//������DataEntity�ж����ж�Ӧ�����ݿ����û�еı���
						{
							continue;
						}
					}
					catch (IllegalArgumentException e)
					{
						//������DataEntity�ж����ж�Ӧ�����ݿ����û�еı���
						continue;
					}
					
					if (valueObj instanceof BigDecimal)
					{
						if (p.getName().compareToIgnoreCase("long") == 0)
						{
							valueObj = new Long(valueObj.toString());
						}
						else if (p.getName().compareToIgnoreCase("double") == 0)
						{
							valueObj = new Double(valueObj.toString());
						}
					}
					else if (valueObj instanceof Timestamp)
					{
						valueObj = new Date(((Timestamp)valueObj).getTime());
					}
					else if(valueObj instanceof java.sql.Date)//06-3-5 tanxin ������iAs ������
					{
						valueObj = new Date(((Timestamp)transRS.getTimestamp(fieldName)).getTime());
					}
					else if(valueObj instanceof Long)
					{
						valueObj = new Long(valueObj.toString());
					}
					else if(valueObj instanceof Integer)
					{
						valueObj = new Integer(valueObj.toString());
					}
					else if(valueObj instanceof String)
					{
						
					}
					else
					{
						continue;
					}

					Object[] args = {valueObj};
					try
					{
						writeMethod.invoke(res, args);
					}
					catch (IllegalAccessException e)
					{
						throw new SystemException("Data Entityʵ�����Ƿ��쳣����", e);
					}
					catch (InvocationTargetException e)
					{
						throw new SystemException("Data Entity����Ŀ���쳣����", e);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				resList.add(res);
			}
		}
		catch (SQLException e)
		{
			throw new SystemException("���ݿ��쳣����", e);
		}
		catch (IntrospectionException e)
		{
			throw new SystemException("Java Bean.��ʡ�쳣����", e);
		}
		catch (Exception e)
		{
			throw new SystemException("��ʼ���쳣����", e);
		}		
		return resList;
	}
	


	/**
	 * �����������ͼ��Ƿ���ǰ׺,ȷ�����ֶ���DataEntity�е����� ע:�ֶ�IDû��ǰ׺ 
	 */
	public static String getFieldName(String fieldName, Class propertyType, boolean isNeedPrefix)
	{
		if(fieldName==null) return null;
		HashMap map = (HashMap)hmFields.get(propertyType.getName());
		//System.out.println("***filedName:"+fieldName+"**** propertyType.getName():"+propertyType.getName());
		if(map==null)
		{
			return null;
		}
		if (isNeedPrefix)
		{			
			String temp = (String)map.get(fieldName);
			if(temp==null)
			{
				temp = fieldName;				
			    String prefixName = DataType.getPrefix(propertyType.getName());
			    fieldName = (prefixName + temp).toLowerCase();
			    map.put(temp,fieldName);
			}
			else
			{
				fieldName = temp;
			}
			//log.debug("prefixName: "+prefixName+" fieldName: "+fieldName);
		}
		else
		{
			String temp = (String)hmFieldsNoPrefix.get(fieldName);
			if(temp==null)
			{		
				temp = fieldName;
				fieldName = fieldName.toLowerCase();
				hmFieldsNoPrefix.put(temp,fieldName);
			}
			else
			{
				fieldName = temp;
			}
		}
		return fieldName;
	}
}
