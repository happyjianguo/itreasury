package com.iss.itreasury.ebank.magnifier.bizlogic;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.iss.itreasury.ebank.magnifier.dataentity.PagerDepictBaseInfo;
import com.iss.itreasury.ebank.magnifier.dataentity.PagerInfo;
import com.iss.itreasury.ebank.magnifier.dataentity.ResultPagerInfo;
import com.iss.itreasury.ebank.magnifier.dataentity.ResultPagerRowInfo;
import com.iss.itreasury.ebank.util.MathUtils;
import com.iss.itreasury.ebank.util.PagerTypeConstant;
import com.iss.itreasury.util.DataFormat;



public class PagerTools
{
	
	/**
	 * ����������Ϣ��ResultSet���ݿ�����ת����JSONList�����
	 * @param resultSet
	 * @param pagerInfo
	 * @param pfFlag:(true����ӡ�򵼳�,false��ֱ�����)
	 * @return
	 * @throws BusinessException
	 */
	public static List convertResultSetToJSONListEx(ResultSet resultSet, PagerInfo pagerInfo) throws Exception{
		ArrayList resultList = new ArrayList(); //���շ��ؽ��
		ResultPagerRowInfo rowInfo = null;
		ArrayList cellList = null;
		long rownum = -1;
		ResultSetMetaData resultSetMD = null;
		Map sourceDataTypeMap = null;
		long dataType = -1;
		
		Iterator pagerIter = null;
		PagerDepictBaseInfo baseInfo = null;
		String displayName = null;
		long displayType = -1;
		String cellString = "";
		
		Timestamp rsTimestamp = null;
		double rsDouble = 0.0;
		long rsLong = 0;
		String[] extensionName = null;
		long[] extensionType = null;
		String extensionString = null;
		String[] ESsplit = null;
		
		Class[] argClass = null;
		Method operateMethod = null;
		Object methodObj = null;
		int extCnt	= 0;		
		
		//�ϼ�
		try
		{
			if(resultSet != null && pagerInfo != null && pagerInfo.getDepictList() != null)
			{
				resultSetMD = resultSet.getMetaData();
				sourceDataTypeMap = new HashMap();
				for(int i=1; i<resultSetMD.getColumnCount(); i++)
				{
					sourceDataTypeMap.put(resultSetMD.getColumnName(i).toUpperCase(), String.valueOf(resultSetMD.getColumnType(i)));
				}

				while(resultSet.next())
				{
					pagerIter = pagerInfo.getDepictList().iterator();
					cellList = new ArrayList();
					
					while(pagerIter.hasNext())
					{
						baseInfo =  (PagerDepictBaseInfo)pagerIter.next();
						rownum = resultSet.getLong("rownum1");
						
						displayName = baseInfo.getDisplayName().toUpperCase();
						if(sourceDataTypeMap.get(displayName) != null)
						{
							dataType = Long.parseLong(sourceDataTypeMap.get(displayName).toString());
						}
						displayType = baseInfo.getDisplayType();
						
						if(baseInfo.isExtension() == true)
						{
							extensionName = baseInfo.getExtensionName();
							extensionType = baseInfo.getExtensionType();
							extensionString = baseInfo.getExtensionString();
							ESsplit = extensionString.split(PagerTypeConstant.LOGOTYPE);
							
							if(extensionName.length == extensionType.length && ESsplit.length >= extensionName.length)
							{
								
								extCnt = extensionName.length;

								for(int i=0; i<extCnt; i++)
								{
									displayName = extensionName[i].toUpperCase();
									if(sourceDataTypeMap.get(displayName) != null)
									{
										dataType = Long.parseLong(sourceDataTypeMap.get(displayName).toString());
									}
									displayType = extensionType[i];
									
									//�������ݿ��ж�Ӧ������ȡֵ
									if(dataType == java.sql.Types.DOUBLE)
									{
										rsDouble = resultSet.getDouble(displayName);
										cellString = String.valueOf(rsDouble);
									}
								    else if(dataType == java.sql.Types.NUMERIC)
								    {
								    	rsLong = resultSet.getLong(displayName);
								    	cellString = String.valueOf(rsLong);
									}
								    else if(dataType == java.sql.Types.DATE)
								    {
								    	rsTimestamp = resultSet.getTimestamp(displayName);
									}
								    else if(dataType == java.sql.Types.TIMESTAMP)
								    {
								    	rsTimestamp = resultSet.getTimestamp(displayName);
									}
								    else if(dataType == java.sql.Types.VARCHAR)
								    {
								    	cellString = resultSet.getString(displayName);
									}
								    else if(dataType == java.sql.Types.CHAR)
								    {
								    	cellString = resultSet.getString(displayName);
									}
								    else if(dataType == java.sql.Types.NULL)
								    {
								    	cellString = "";
									}
								    else
								    {
								    	cellString = "";
								    }
									
									//ת��Ϊ��Ҫ��ʾ������
									if(displayType == PagerTypeConstant.STRING)
									{
										if(cellString == null)
										{
											cellString = "";
										}
									}
									else if(displayType == PagerTypeConstant.DATE)
									{
										if(rsTimestamp == null)
										{
											cellString = "";
										}
										else
										{
											cellString = DataFormat.formatDate(rsTimestamp, DataFormat.FMT_DATE_YYYYMMDD);
										}
									}
									else if(displayType == PagerTypeConstant.DATETIME)
									{
										if(rsTimestamp == null)
										{
											cellString = "";
										}
										else
										{
											cellString = DataFormat.formatDate(rsTimestamp, DataFormat.FMT_DATE_YYYYMMDD_HHMMSS);
										}
									}
									else if(displayType == PagerTypeConstant.AMOUNT_2)
									{
										cellString = DataFormat.formatAmount(Double.parseDouble(cellString), 2);
									}
									else if(displayType == PagerTypeConstant.AMOUNT_6)
									{
										cellString = DataFormat.formatAmount(Double.parseDouble(cellString), 6);
									}
									else if(displayType == PagerTypeConstant.LONG)
									{
										cellString = String.valueOf((long)Double.parseDouble(cellString));
									}
									else if(displayType == PagerTypeConstant.DOUBLE)
									{
										cellString = String.valueOf(rsDouble);
									}
									
									extensionString = extensionString.replaceFirst(PagerTypeConstant.LOGOTYPE, cellString);
								}
								if(extCnt > 1)		
									cellString = extensionString;
							}
							else
							{
								cellString = extensionString;
							}
						}
						else
						{
							//�������ݿ��ж�Ӧ������ȡֵ
							if(dataType == java.sql.Types.DOUBLE)
							{
								rsDouble = resultSet.getDouble(displayName);
								cellString = String.valueOf(rsDouble);
							}
						    else if(dataType == java.sql.Types.NUMERIC)
						    {
						    	rsLong = resultSet.getLong(displayName);
						    	cellString = String.valueOf(rsLong);
							}
						    else if(dataType == java.sql.Types.DATE)
						    {
						    	rsTimestamp = resultSet.getTimestamp(displayName);
							}
						    else if(dataType == java.sql.Types.TIMESTAMP)
						    {
						    	rsTimestamp = resultSet.getTimestamp(displayName);
							}
						    else if(dataType == java.sql.Types.VARCHAR)
						    {
						    	cellString = resultSet.getString(displayName);
							}
						    else if(dataType == java.sql.Types.CHAR)
						    {
						    	cellString = resultSet.getString(displayName);
							}
						    else if(dataType == java.sql.Types.CLOB)
						    {
						    	cellString = DataFormat.convertClobToString(resultSet.getClob(displayName));
							}
						    else if(dataType == java.sql.Types.NULL)
						    {
						    	cellString = "";
							}
						    else
						    {
						    	cellString = "";
						    }
							
							//ת��Ϊ��Ҫ��ʾ������
							if(displayType == PagerTypeConstant.STRING)
							{
								if(cellString == null)
								{
									cellString = "";
								}
							}
							else if(displayType == PagerTypeConstant.DATE)
							{
								if(rsTimestamp == null)
								{
									cellString = "";
								}
								else
								{
									cellString = DataFormat.formatDate(rsTimestamp, DataFormat.FMT_DATE_YYYYMMDD);
								}
							}
							else if(displayType == PagerTypeConstant.DATETIME)
							{
								if(rsTimestamp == null)
								{
									cellString = "";
								}
								else
								{
									cellString = DataFormat.formatDate(rsTimestamp, DataFormat.FMT_DATE_YYYYMMDD_HHMMSS);
								}
							}
							else if(displayType == PagerTypeConstant.AMOUNT_2)
							{
								cellString = DataFormat.formatAmount(Double.parseDouble(cellString), 2);
							}
							else if(displayType == PagerTypeConstant.AMOUNT_6)
							{
								cellString = DataFormat.formatAmount(Double.parseDouble(cellString), 6);
							}
							else if(displayType == PagerTypeConstant.LONG)
							{
								cellString = String.valueOf((long)Double.parseDouble(cellString));
							}
							else if(displayType == PagerTypeConstant.DOUBLE)
							{
								//cellString = String.valueOf(rsDouble);
							}
							else if(displayType == PagerTypeConstant.FUNCTION)
							{
								argClass = baseInfo.getFnArgClass();
								
								if(cellString == null)
								{
									cellString = "";
								}
								else
								{
									if(argClass[0].getName().equals(long.class.getName()))
									{
									    rsLong = (long)Double.parseDouble(cellString);
									    
										operateMethod =  baseInfo.getFnClazz().getMethod(baseInfo.getFnMethodName(), argClass);
										if(Modifier.isStatic(operateMethod.getModifiers())){
											methodObj = operateMethod.invoke(null,  new Object[]{new Long(rsLong)});
										}
										else{
											methodObj = operateMethod.invoke(baseInfo.getFnClazz().newInstance(),  new Object[]{new Long(rsLong)});
										}
										cellString = String.valueOf(methodObj);
									}
									else if(argClass[0].getName().equals(String.class.getName()))
									{
										operateMethod =  baseInfo.getFnClazz().getMethod(baseInfo.getFnMethodName(), argClass);
										if(Modifier.isStatic(operateMethod.getModifiers())){
											methodObj = operateMethod.invoke(null, new Object[]{new String(cellString)});
										}
										else{
											methodObj = operateMethod.invoke(baseInfo.getFnClazz().newInstance(), new Object[]{new String(cellString)});
										}
										cellString = String.valueOf(methodObj);
									}
									else if(argClass[0].getName().equals(Timestamp.class.getName()))
									{
										operateMethod =  baseInfo.getFnClazz().getMethod(baseInfo.getFnMethodName(), argClass);
										if(Modifier.isStatic(operateMethod.getModifiers())){
											methodObj = operateMethod.invoke(null, new Object[]{rsTimestamp});
										}
										else{
											methodObj = operateMethod.invoke(baseInfo.getFnClazz().newInstance(), new Object[]{rsTimestamp});
										}
										cellString = String.valueOf(methodObj);
									}
								}
							}
						}

						cellList.add(cellString);
						cellString = "";
						dataType = -1;
					}
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(rownum));
					resultList.add(rowInfo);
				}
		
			
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("ת���������JSON����ʱ�쳣", e);
		}
		
		return resultList;
		
	}
	
	public static String getJSONString(ResultPagerInfo resultPagerinfo)throws Exception
	{
		String strJSON = "";
		try
		{
			if(resultPagerinfo!=null)
			{
				JSONObject jsonObject = JSONObject.fromObject(resultPagerinfo);
				strJSON = jsonObject.toString();
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("�ӽ����ת����JSON�ַ���ʧ��!");
		}
		return strJSON;
	}

}
