package com.iss.sysframe.pager.tools;

import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.iss.itreasury.ebank.util.MathUtils;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;
import com.iss.sysframe.pager.dataentity.ResultPagerInfo;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.util.DataFormat;

/**
 * @author leiyang3
 * @date 2009-8-27
 */
public class PagerTools
{
	
	/**
	 * 根据配置信息将ResultSet数据库结果集转换到JSONList结果集
	 * @param resultSet
	 * @param pagerInfo
	 * @param pfFlag:(true：打印或导出,false：直接输出)
	 * @return
	 * @throws Exception
	 */
	public static List convertResultSetToJSONListEx(ResultSet resultSet, PagerInfo pagerInfo,String pfFlag) throws Exception{
		ArrayList resultList = new ArrayList(); //最终返回结果
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
		int extCnt	= 0;		//2010年3月9日	rczhan	追加【打印或导出】		
		
		//合计
//		String strUsertext = null;//存放	pagerInfo中usertext的内容
//		double[] dataTotals = null;//用于存放汇总的结果
//		String[] singleTexts = null;//存放pagerInfo中usertext的内容按“;”分割后的结果集
//		String[] displayNames = null;//存放汇总的字段
//		int singleTextNum = 0;//存放pagerInfo中usertext的内容按“;”分割后的结果的数目
//		boolean isSum = false;//是否要进行汇总的操作，兼容静态文本显示的情况		
		
		try
		{
			if(resultSet != null && pagerInfo != null)
			{
				if(pagerInfo.getDepictList() != null)
				{
					resultSetMD = resultSet.getMetaData();
					sourceDataTypeMap = new HashMap();
					for(int i=1; i<resultSetMD.getColumnCount(); i++)
					{
						sourceDataTypeMap.put(resultSetMD.getColumnName(i).toUpperCase(), String.valueOf(resultSetMD.getColumnType(i)));
					}
//					if(!pagerInfo.getUsertext().equals("")){
//						strUsertext = pagerInfo.getUsertext();
//						if(strUsertext.indexOf("{")>0&&strUsertext.indexOf("}")>strUsertext.indexOf("{")){
//							isSum=true;
//						}					
//						if(isSum){
//							singleTexts=strUsertext.split(";");
//							singleTextNum=singleTexts.length;
//							displayNames=new String[singleTextNum];	
//							dataTotals=new double[singleTextNum];		
//							for(int i=0;i<singleTextNum;i++){
//								displayNames[i] = singleTexts[i].substring(singleTexts[i].indexOf("{") + 1, singleTexts[i].indexOf("}"));
//							}
//						}					   
//					}
						
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
									//2010年3月9日	rczhan	Add	Start
									extCnt = extensionName.length;
									if("true".equalsIgnoreCase(pfFlag)){
										extCnt = 1;
									}
									//2010年3月9日	rczhan	Add	End
									for(int i=0; i<extCnt; i++)
									{
										displayName = extensionName[i].toUpperCase();
										if(sourceDataTypeMap.get(displayName) != null)
										{
											dataType = Long.parseLong(sourceDataTypeMap.get(displayName).toString());
										}
										displayType = extensionType[i];
										
										//根据数据库列对应的类型取值
										if(dataType == java.sql.Types.DOUBLE)
										{
											rsDouble = resultSet.getDouble(displayName);
											cellString = String.valueOf(rsDouble);
										}
									    else if(dataType == java.sql.Types.NUMERIC)
									    {
									    	rsDouble = resultSet.getDouble(displayName);
									    	cellString = String.valueOf(rsDouble);
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
										
										//转换为需要显示的类型
										if(displayType == PagerTypeConstant.STRING)
										{
											if(cellString == null)
											{
												cellString = "";
											}else if(cellString.indexOf(".")>0)
											{
												try{
													Double.valueOf(cellString);
													String point_end = cellString.substring(cellString.indexOf(".")+1,cellString.length());
													if(Double.valueOf(point_end)==0)
														cellString = cellString.substring(0, cellString.indexOf("."));
												}catch (Exception e) {
												}
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
												cellString = DataFormat.formatDate(rsTimestamp, DataFormat.DT_YYYY_MM_DD);
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
												cellString = DataFormat.formatDate(rsTimestamp, DataFormat.DT_YYYYMMDD_HHMMSS);
											}
										}
										else if(displayType == PagerTypeConstant.AMOUNT_2)
										{
											if(cellString!=null&&!cellString.equals(""))
											{
												//cellString = sessionMng.m_strCurrencySymbol + DataFormat.formatAmount(Double.parseDouble(cellString), 2);
												cellString = DataFormat.formatAmount(Double.parseDouble(cellString), 2);
											}
										}
										else if(displayType == PagerTypeConstant.AMOUNT_6)
										{
											if(cellString!=null&&!cellString.equals(""))
											{
												//cellString = sessionMng.m_strCurrencySymbol + DataFormat.formatAmount(Double.parseDouble(cellString), 6);
												cellString = DataFormat.formatAmount(Double.parseDouble(cellString), 6);
											}
										}
										else if(displayType == PagerTypeConstant.LONG)
										{
											if(cellString!=null&&!cellString.equals(""))
												cellString = String.valueOf((long)Double.parseDouble(cellString));
											else
												cellString ="";
										}
										else if(displayType == PagerTypeConstant.DOUBLE)
										{
											cellString = String.valueOf(rsDouble);
										}else if(displayType==PagerTypeConstant.FUNCTION){

											argClass = baseInfo.getFnArgClass();
											
											if(cellString == null)
											{
												cellString = "";
											}
											else
											{
												if(argClass[0].getName().equals(long.class.getName())||argClass[0].getName().equals("java.lang.Long"))
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
												 if(argClass[0].getName().equals(String.class.getName()))
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
										
										extensionString = extensionString.replaceFirst(PagerTypeConstant.LOGOTYPE, cellString);
									}
									if(extCnt > 1)		//2010年3月9日	rczhan	Add
										cellString = extensionString;
								}
								else
								{
									cellString = extensionString;
								}
							}
							else
							{
								//根据数据库列对应的类型取值
								if(dataType == java.sql.Types.DOUBLE)
								{
									rsDouble = resultSet.getDouble(displayName);
									cellString = String.valueOf(rsDouble);
								}
							    else if(dataType == java.sql.Types.NUMERIC)
							    {
							    	rsDouble = resultSet.getDouble(displayName);
							    	cellString = String.valueOf(rsDouble);
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
							    	cellString = convertClobToString(resultSet.getClob(displayName));
								}
							    else if(dataType == java.sql.Types.NULL)
							    {
							    	cellString = "";
								}
							    else
							    {
							    	cellString = "";
							    }
								
								//转换为需要显示的类型
								if(displayType == PagerTypeConstant.STRING)
								{
									if(cellString == null)
									{
										cellString = "";
									}else if(cellString.indexOf(".")>0)
									{
										try{
											Double.valueOf(cellString);
											cellString = cellString.substring(0, cellString.indexOf("."));
										}catch (Exception e) {
										}
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
										cellString = DataFormat.formatDate(rsTimestamp, DataFormat.DT_YYYY_MM_DD);
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
										cellString = DataFormat.formatDate(rsTimestamp, DataFormat.DT_YYYYMMDD_HHMMSS);
									}
								}
								else if(displayType == PagerTypeConstant.AMOUNT_2)
								{
									if(cellString!=null&&!cellString.equals(""))
									{
										//cellString = sessionMng.m_strCurrencySymbol + DataFormat.formatAmount(Double.parseDouble(cellString), 2);
										cellString = DataFormat.formatAmount(Double.parseDouble(cellString), 2);
									}else{
										cellString = "0.00";
									}
								}
								else if(displayType == PagerTypeConstant.AMOUNT_6)
								{
									if(cellString!=null&&!cellString.equals(""))
									{
										//cellString = sessionMng.m_strCurrencySymbol + DataFormat.formatAmount(Double.parseDouble(cellString), 6);
										cellString = DataFormat.formatAmount(Double.parseDouble(cellString), 6);
									}
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
									
									if(cellString == null||cellString.equals(""))
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
						
//						//合计
//						if(!pagerInfo.getUsertext().equals("")){											
//							for(int j=0;j<singleTextNum;j++){
//								dataTotals[j] = MathUtils.add(dataTotals[j], resultSet.getDouble(displayNames[j]));							
//							}					
//						}			
						
						rowInfo = new ResultPagerRowInfo();
						rowInfo.setCell(cellList);
						rowInfo.setId(String.valueOf(rownum));
						resultList.add(rowInfo);
					}
//					//把合计结果进行拼接，放入pagerInfo.setUsertext（）中
//					if(!pagerInfo.getUsertext().equals("")){
//						if(isSum){
//							StringBuffer textBuffer= new StringBuffer(); 
//							for(int w=0;w<singleTextNum;w++){						
//								singleTexts[w] = "<b>"+singleTexts[w].substring(0, singleTexts[w].indexOf("{"))+"</b><em>"+DataFormat.formatAmount(dataTotals[w], 2)+"</em>";
//								if(w>1&&(w)%5==0){
//									textBuffer.append("<br>&nbsp;&nbsp;");
//								}else{
//									textBuffer.append("&nbsp;&nbsp;");
//								}
//								textBuffer.append(singleTexts[w]);
//							}	
//							pagerInfo.setUsertext(textBuffer.toString());
//						}
//					}			
				}
				else if (pagerInfo.getExtClazz()!=null && pagerInfo.getExtMothodName()!=null)//针对业务的个性化处理 add by zhouxiang 2012-7-26
				{
					Class clazz = pagerInfo.getExtClazz();
					String methodName = pagerInfo.getExtMothodName();
					
					Method method =null;
					Method _method[] = clazz.getMethods();
					
					for(int i=0;i<_method.length;i++){
						if(_method[i].getName().equals(methodName)){
							method = _method[i];
							break;
						}
					}
					if(Modifier.isStatic(method.getModifiers())){
						if(pagerInfo.getParams()!=null)
							resultList = (ArrayList)method.invoke(null, new Object[]{resultSet,pagerInfo.getParams()});
						else
							resultList = (ArrayList)method.invoke(null, new Object[]{resultSet});
					}else{
						if(pagerInfo.getParams()!=null)
							resultList = (ArrayList)method.invoke(clazz.newInstance(), new Object[]{resultSet,pagerInfo.getParams()});
						else
							resultList = (ArrayList)method.invoke(clazz.newInstance(), new Object[]{resultSet});
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("转换结果集到JSON集合时异常", e);
		}
		
		return resultList;
		
	}
	/**
	 * 根据配置信息将ResultSet数据库结果集转换到JSONList结果集
	 * @param resultSet
	 * @param pagerInfo
	 * @return
	 * @throws Exception
	 */
	public static List convertResultSetToJSONList(ResultSet resultSet, PagerInfo pagerInfo) throws Exception
	{
		return convertResultSetToJSONListEx(resultSet,pagerInfo,"false");
	}
	
	/**
	 * 根据配置信息将ResultInfo数据库结果集转换到JSONList结果集
	 * @param pagerInfo
	 * @return
	 * @throws Exception
	 */
	public static List convertResultInfoToJSONList(PagerInfo pagerInfo,long startNum,String pfFlag) throws Exception
	{
		ResultInfo sourceInfo = pagerInfo.getResultInfo();
		Iterator sourceIter = null;
		Object sourceObj = null;
		Field[] sourceFields = null;
		Field currField = null;
		Object currFieldValue = null;
		Map sourceMap = null;
		//Map sourceDataTypeMap = null;
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ResultPagerRowInfo rowInfo = null;
		ArrayList cellList = null;
		long rownum = startNum;
		
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
		
		
//		//合计 
//		String strUsertext = null;//存放	pagerInfo中usertext的内容
//		double[] dataTotals=null;//用于存放汇总的结果
//		String[] singleTexts=null;//存放pagerInfo中usertext的内容按“;”分割后的结果集
//		String[] displayNames=null;//存放汇总的字段
//		int singleTextNum=0;//存放pagerInfo中usertext的内容按“;”分割后的结果的数目
//		boolean isSum=false;//是否要进行汇总的操作，兼容静态文本显示的情况	
		
		int extCnt	= 0;		//2011年12月22日	追加【打印或导出】	
		
		try
		{
			
			if(sourceInfo != null && sourceInfo.getResultList() != null && sourceInfo.getResultList().size() > 0 && pagerInfo != null && pagerInfo.getDepictList() != null)
			{
//				if(!pagerInfo.getUsertext().equals("")){
//					strUsertext = pagerInfo.getUsertext();
//					if(strUsertext.indexOf("{")>0&&strUsertext.indexOf("}")>strUsertext.indexOf("{")){
//						isSum=true;
//					}					
//					if(isSum){
//						singleTexts=strUsertext.split(";");
//						singleTextNum=singleTexts.length;
//						displayNames=new String[singleTextNum];	
//						dataTotals=new double[singleTextNum];		
//						for(int i=0;i<singleTextNum;i++){
//							displayNames[i] = singleTexts[i].substring(singleTexts[i].indexOf("{") + 1, singleTexts[i].indexOf("}"));
//						}
//					}					   
//				}
				sourceIter = sourceInfo.getResultList().iterator();
				
				while(sourceIter.hasNext())
				{
					sourceObj = sourceIter.next();
					sourceFields = sourceObj.getClass().getDeclaredFields();
					sourceMap = new HashMap();
					//sourceDataTypeMap = new HashMap();
					rownum=rownum+1;
					for(int i=0; i<sourceFields.length; i++)
					{
						currField = sourceFields[i];
		            	currField.setAccessible(true);
		            	currFieldValue = currField.get(sourceObj);
		            	
		            	sourceMap.put(currField.getName().toUpperCase(), currFieldValue);
		            	//sourceDataTypeMap.put(currField.getName().toUpperCase(), currField.getType().getName());
		            	
					}

					pagerIter = pagerInfo.getDepictList().iterator();
					cellList = new ArrayList();
					
					while(pagerIter.hasNext())
					{
						baseInfo =  (PagerDepictBaseInfo)pagerIter.next();					
						
						displayName = baseInfo.getDisplayName().toUpperCase();
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
								if("true".equalsIgnoreCase(pfFlag)){
									extCnt = 1;
								}
								for(int i=0; i<extCnt; i++)
								{
									displayName = extensionName[i].toUpperCase();
									displayType = extensionType[i];
								
									currFieldValue = sourceMap.get(displayName);
									
									if(displayType == PagerTypeConstant.STRING)
									{
										if(currFieldValue != null){
											cellString = (String)currFieldValue;
										}
										else{
											cellString = "";
										}
										
									}
									else if(displayType == PagerTypeConstant.DATE)
									{
										if(currFieldValue != null){
											rsTimestamp = (Timestamp)currFieldValue;
											cellString = DataFormat.formatDate(rsTimestamp, DataFormat.DT_YYYY_MM_DD);
										}
										else{
											cellString = "";
										}
									}
									else if(displayType == PagerTypeConstant.DATETIME)
									{
										if(currFieldValue != null){
											rsTimestamp = (Timestamp)currFieldValue;
											cellString = DataFormat.formatDate(rsTimestamp, DataFormat.DT_YYYYMMDD_HHMMSS);
										}
										else{
											cellString = "";
										}
									}
									else if(displayType == PagerTypeConstant.AMOUNT_2)
									{
										if(currFieldValue != null){
											rsDouble = ((Double)currFieldValue).doubleValue();
											cellString = DataFormat.formatAmount(rsDouble, 2);
										}
										else{
											cellString = "";
										}
									}
									else if(displayType == PagerTypeConstant.AMOUNT_6)
									{
										if(currFieldValue != null){
											rsDouble = ((Double)currFieldValue).doubleValue();
											cellString = DataFormat.formatAmount(rsDouble, 6);
										}
										else{
											cellString = "";
										}
									}
									else if(displayType == PagerTypeConstant.LONG)
									{
										if(currFieldValue != null){
											rsLong = ((Long)currFieldValue).longValue();
											cellString = String.valueOf(rsLong);
										}
										else{
											cellString = "";
										}
									}
									else if(displayType == PagerTypeConstant.DOUBLE)
									{
										if(currFieldValue != null){
											rsDouble = ((Double)currFieldValue).doubleValue();
											cellString = String.valueOf(rsDouble);
										}
										else{
											cellString = "";
										}
									}
									else if(displayType == PagerTypeConstant.ARRAY)
									{
										if(currFieldValue != null){
											cellString = convertArrayToString(currFieldValue, ",");
										}
										else{
											cellString = "";
										}
									}
									if("true".equalsIgnoreCase(pfFlag)){
										extensionString = cellString;
									}else{
										extensionString = extensionString.replaceFirst(PagerTypeConstant.LOGOTYPE, cellString);
									}
									
								}
								
								cellString = extensionString;
							}
							else
							{
								cellString = extensionString;
							}
						}
						else
						{
							currFieldValue = sourceMap.get(displayName);
							
							if(displayType == PagerTypeConstant.STRING)
							{
								if(currFieldValue != null){
									cellString = (String)currFieldValue;
								}
								else{
									cellString = "";
								}
								
							}
							else if(displayType == PagerTypeConstant.DATE)
							{
								if(currFieldValue != null){
									rsTimestamp = (Timestamp)currFieldValue;
									cellString = DataFormat.formatDate(rsTimestamp, DataFormat.DT_YYYY_MM_DD);
								}
								else{
									cellString = "";
								}
							}
							else if(displayType == PagerTypeConstant.DATETIME)
							{
								if(currFieldValue != null){
									rsTimestamp = (Timestamp)currFieldValue;
									cellString = DataFormat.formatDate(rsTimestamp, DataFormat.DT_YYYYMMDD_HHMMSS);
								}
								else{
									cellString = "";
								}
							}
							else if(displayType == PagerTypeConstant.AMOUNT_2)
							{
								if(currFieldValue != null){
									rsDouble = ((Double)currFieldValue).doubleValue();
									cellString = DataFormat.formatAmount(rsDouble, 2);
								}
								else{
									cellString = "";
								}
							}
							else if(displayType == PagerTypeConstant.AMOUNT_6)
							{
								if(currFieldValue != null){
									rsDouble = ((Double)currFieldValue).doubleValue();
									cellString = DataFormat.formatAmount(rsDouble, 6);
								}
								else{
									cellString = "";
								}
							}
							else if(displayType == PagerTypeConstant.LONG)
							{
								if(currFieldValue != null){
									rsLong = ((Long)currFieldValue).longValue();
									cellString = String.valueOf(rsLong);
								}
								else{
									cellString = "";
								}
							}
							else if(displayType == PagerTypeConstant.DOUBLE)
							{
								if(currFieldValue != null){
									rsDouble = ((Double)currFieldValue).doubleValue();
									cellString = String.valueOf(rsDouble);
								}
								else{
									cellString = "";
								}
							}
							else if(displayType == PagerTypeConstant.ARRAY)
							{
								if(currFieldValue != null){
									cellString = convertArrayToString(currFieldValue, ",");
								}
								else{
									cellString = "";
								}
							}
							else if(displayType == PagerTypeConstant.FUNCTION)
							{
								argClass = baseInfo.getFnArgClass();
								
								if(currFieldValue != null)
								{
									if(argClass[0].getName().equals(long.class.getName()))
									{
										rsLong = ((Long)currFieldValue).longValue();
										
										operateMethod =  baseInfo.getFnClazz().getMethod(baseInfo.getFnMethodName(), argClass);
										if(Modifier.isStatic(operateMethod.getModifiers())){
											methodObj = operateMethod.invoke(null, new Object[]{new Long(rsLong)});
										}
										else{
											methodObj = operateMethod.invoke(baseInfo.getFnClazz().newInstance(), new Object[]{new Long(rsLong)});
										}
										cellString = String.valueOf(methodObj);
									}
									else if(argClass[0].getName().equals(String.class.getName()))
									{
										cellString = (String)currFieldValue;
										
										operateMethod =  baseInfo.getFnClazz().getMethod(baseInfo.getFnMethodName(), argClass);
										if(Modifier.isStatic(operateMethod.getModifiers())){
											methodObj = operateMethod.invoke(null, new Object[]{new String(cellString)});
										}
										else{
											methodObj = operateMethod.invoke(baseInfo.getFnClazz().newInstance(), new Object[]{new String(cellString)});
										}
										cellString = String.valueOf(methodObj);
									}
								}
								else{
									cellString = "";
								}
							}
						}
						
						cellList.add(cellString);
						cellString = "";
					}
					
//					//合计
//					if(!pagerInfo.getUsertext().equals("")){											
//						for(int j=0;j<singleTextNum;j++){
//							dataTotals[j] = MathUtils.add(dataTotals[j], ((Double)sourceMap.get(displayNames[j].toUpperCase())).doubleValue());							
//						}					
//					}				
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(rownum));
					resultList.add(rowInfo);
				}			
				
//				//把合计结果进行拼接，放入pagerInfo.setUsertext（）中
//				if(!pagerInfo.getUsertext().equals("")){
//					if(isSum){
//						StringBuffer textBuffer= new StringBuffer(); 
//						for(int w=0;w<singleTextNum;w++){						
//							singleTexts[w] = "<b>"+singleTexts[w].substring(0, singleTexts[w].indexOf("{"))+"</b><em>"+DataFormat.formatAmount(dataTotals[w], 2)+"</em>";
//							if(w>1&&(w)%5==0){
//								textBuffer.append("<br>&nbsp;&nbsp;");
//							}else{
//								textBuffer.append("&nbsp;&nbsp;");
//							}
//							textBuffer.append(singleTexts[w]);
//							
//							
//						}	
//						pagerInfo.setUsertext(textBuffer.toString());
//					}
//				}			
			
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("转换结果集到JSON集合时异常", e);
		}
		
		return resultList;
	}
	
	/*public static String convertMapToString(Object mapObj, String split)
	{
		String returnStr = "";
		String tempStr = "";
		Map newMap = (Map)objValue;
		Iterator newIt = newMap.keySet().iterator();
		while(newIt.hasNext())
		{
			tempStr= newIt.next().toString();
			returnStr += tempStr +":" + newMap.get(tempStr) +splitMark;
		}
		if(!returnStr.trim().equals(""))
		{
			returnStr = returnStr.substring(0,returnStr.length()-1);
		}
		return returnStr;
	}*/
	
	/*public static String convertListToString(Object listObj, String split)
	{
		StringBuffer bufferString = new StringBuffer();
		
		List list = (List)listObj;
		for(int i=0; 1<list.size(); i++)
		{
			bufferString.append(list.toString() + split);
		}
		
		if(!bufferString.toString().equals(""))
		{
			bufferString = new StringBuffer(bufferString.toString().substring(0, bufferString.toString().length() - split.length()));
		}
		
		return bufferString.toString();
	}*/
	
	public static String convertArrayToString(Object arrayObj, String split)
	{
		StringBuffer bufferString = new StringBuffer();
		
		if(arrayObj != null)
		{
			if(long[].class.equals(arrayObj.getClass()))
			{
				long[] arrayLong = (long[])arrayObj;
				
				for(int i=0; i<arrayLong.length; i++)
				{
					bufferString.append(arrayLong[i] + split);
				}
			}
			else if(double[].class.equals(arrayObj.getClass()))
			{
				double[] arrayDouble = (double[])arrayObj;
				
				for(int i=0; i<arrayDouble.length; i++)
				{
					bufferString.append(arrayDouble[i] + split);
				}
			}
			else if(String[].class.equals(arrayObj.getClass()))
			{
				String[] arrayString = (String[])arrayObj;
				
				for(int i=0; i<arrayString.length; i++)
				{
					bufferString.append(arrayString[i] + split);
				}
			}
		}
		
		if(!bufferString.toString().equals(""))
		{
			bufferString = new StringBuffer(bufferString.toString().substring(0, bufferString.toString().length() - split.length()));
		}
		
		return bufferString.toString();
	}
	/**
	 * CLOB转换成字符串基本实现
	 * @throws SystemException
	 */	
	public static String convertClobToString(Clob clob) throws Exception
	{
		Reader reader = null;
		char[] ch = null;
		
		try
		{
			reader = (Reader)clob.getCharacterStream();
			ch = new char[(int)clob.length()]; 
			reader.read(ch);
			
			reader.close();
			reader = null;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception("CLOB转换成字符串时异常");
		} 
		finally
		{
			if(reader != null)
			{
				try {
					reader.close();
					reader = null;
				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception("CLOB转换成字符串时异常");
				}
			}
		}
		return new String(ch);
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
			throw new Exception("从结果集转换到JSON字符串失败!");
		}
		return strJSON;
	}
	
	public static List returnCellList(ArrayList cellList,Object _object)
	{
		String cellString = _object == null?"":String.valueOf(_object);
		cellList.add(cellString);
		return cellList;
	}
	public static List convertListMapToJSONList(PagerInfo pagerInfo,long startNum,String pfFlag) throws Exception{
		List rl= pagerInfo.getResultList();
		Iterator it = rl.iterator();
		int n = 0;
		while(it.hasNext()){
			n++;
			Map content = (HashMap) it.next();
			ResultPagerRowInfo info = convertMapToResultPagerRowInfo(pagerInfo,content,n);
		}
		return null;
	}
	private static ResultPagerRowInfo convertMapToResultPagerRowInfo(PagerInfo pagerInfo,Map content,int id) throws Exception {
		ResultPagerRowInfo info = new ResultPagerRowInfo();
		info.setId(id+"");
		info.setCell(getDisplayDaTa(pagerInfo,content));
		return info;
	}
	private static List getDisplayDaTa(PagerInfo pagerInfo,Map content) throws Exception {
		Iterator pagerIter = pagerInfo.getDepictList().iterator();
		List cellList = new ArrayList();
		PagerDepictBaseInfo baseInfo;
		String columnName ="";
		int type= -1;
		String cellString ;
		
		while(pagerIter.hasNext()){
			
			baseInfo =  (PagerDepictBaseInfo)pagerIter.next();
			
			columnName = baseInfo.getDisplayName();
			type = Long.valueOf(baseInfo.getDisplayType()+"").intValue();
			
			cellString = content.get(columnName)==null?"":content.get(columnName)+"";
			switch (type) {
				case (int) PagerTypeConstant.STRING:
					if(cellString.indexOf(".")>0)
					{
						try{
							Double.valueOf(cellString);
							cellString = cellString.substring(0, cellString.indexOf("."));
						}catch (Exception e) {
						}
					}		
					break;
				case (int) PagerTypeConstant.AMOUNT_2:
					if(!cellString.equals(""))
					{
						cellString = DataFormat.formatAmount(Double.parseDouble(cellString), 2);
					}
					break;
				case (int) PagerTypeConstant.AMOUNT_6:
					if(!cellString.equals(""))
					{
						cellString = DataFormat.formatAmount(Double.parseDouble(cellString), 6);
					}
					break;
				case (int) PagerTypeConstant.LONG:
					break;
				case (int) PagerTypeConstant.DOUBLE:
						//保留1位小数
						cellString = DataFormat.formatAmount(Double.parseDouble(cellString), 1);
					break;
				case (int) PagerTypeConstant.FUNCTION:
						Class []argClass = baseInfo.getFnArgClass();
						long rsLong;
						Method operateMethod;
						Object methodObj ;
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
							 if(argClass[0].getName().equals(String.class.getName()))
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
						}
			
					break;
			default:
				if(cellString == null)
				{
					cellString = "";
				}else if(cellString.indexOf(".")>0)
				{
					try{
						Double.valueOf(cellString);
						cellString = cellString.substring(0, cellString.indexOf("."));
					}catch (Exception e) {
					}
				}		
				break;
			}
		}
		return cellList;
	}
}
