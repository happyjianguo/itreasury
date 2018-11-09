/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-9
 */
package com.iss.itreasury.util;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.HashMap;


import javax.servlet.ServletRequest;

import com.iss.itreasury.util.ITreasuryException;

/**
 * ITreasuryBaseDataEntity���࿪��������<p>
 * 1. ����ģ���Data Entity��Ӧ�ü̳д��ಢʵ����صĳ������<p> 
 * 2. �������ݿ����ж�Ӧ�ֶεı�����setXXX()ʱ�������SECBaseDataEntity�ж����putUsedFieldʹ��DAO�����ܹ�ʶ��ʹ�ù��ĳ�Ա��������ֵ<p>   
 * 3. �����Ҫ�ظ�ʹ�� ITreasuryBaseDataEntity�����࣬(������findByID���в�ѯ��Ȼ��Է��ص�DataEntity set��ֵ��ִ��update����)�������ȵ���clearUsedFields()�����ʹ������ <p>
*/ 
public abstract class ITreasuryBaseDataEntity implements Serializable {
	
	/**
	 * ö�����е�ǰDataEntity�����õ�����������
	 * ����������µ���������,������������ö������
	 * */
	
	public final static class DataTypeName
	{
		public final static String[] DataTypeNames = {"string","long","double","timestamp"};
		
		public static String TYPE_STRING    = "java.lang.String";
		public static String TYPE_LONG      = "long";
		public static String TYPE_DOUBLE    = "double";
		public static String TYPE_TIMESTAMP = "java.sql.timestamp";
		
		/**
		 * �����������ͻ�ȡ��Ӧ��ǰ׺(�����ǰ׺)
		 * */
		public static String getPrefixByDataType(String dataType){
			if(dataType.equalsIgnoreCase(TYPE_STRING))
				return "s";
			else if(dataType.equalsIgnoreCase(TYPE_LONG))
				return "n";
			else if(dataType.equalsIgnoreCase(TYPE_DOUBLE))
				return "m";
			else if(dataType.equalsIgnoreCase(TYPE_TIMESTAMP))
				return "dt";
			return "";
		}
	} 	
	
	
	
	
	/**��ȡID�ĳ��󷽷�����������ʵ�֣�DAO��ͨ�ò�����ʹ�ã�û��ID�����ݿ��ʹ��*/
	public abstract long getId();
	/**����ID�ĳ��󷽷�����������ʵ�֣�DAO��ͨ�ò�����ʹ�ã�û��ID�����ݿ��ʹ��*/	
	public abstract void setId(long id);
	
	/**��¼��ʹ�õ�DataEntity�е��ֶΣ������е�setXXX�����б���(!!)�������²�����
	 * 		usedFields.put("FieldName", fieldValue);
	 * ����DAO��updateʱֻ�����Ҫ���µ����ݽ��и���
	*/
	protected HashMap usedFields = new HashMap();
	
	private final String[] dataType = {"double","long","java.lang.String","java.sql.Timestamp"};			//֧�ֵ���������
	
	/**
	 * ��ȡ���б����ù��ĳ�Ա���Ƽ���ֵ
	* @param
	* @param
	* @return Hashtable 
	* @throws
	 */
	final public HashMap gainAllUsedFieldsAndValue(){
		return usedFields;
	}
	
	/**
	 * put��ʹ�õĳ�Ա��������ʹ�ñ�
  	 * @param��������
	 * @param��long�ͱ���ֵ��
	 * @return
	 * @throws
	 */
	protected void putUsedField(String key, long value){
		usedFields.put(key, new Long(value));		
	}
	
	/**
	 * put��ʹ�õĳ�Ա��������ʹ�ñ�
	 * @param��������
	 * @param��double�ͱ���ֵ��
	 * @return
	 * @throws
	 */	
	protected void putUsedField(String key, double value){
		usedFields.put(key, new Double(value));		
	}
	
	/**
	 * put��ʹ�õĳ�Ա��������ʹ�ñ�
	 * @param��������
	 * @param��Object�ͱ���ֵ,���Ϊ���򲻱����á�
	 * @return
	 * @throws
	 */	
	protected void putUsedField(String key, Object value){
		if(value != null)
			usedFields.put(key, value);		
	}
					
	//Add other putUsedField for different field type at here
	
	/**
	 * �����ʹ�ñ���
	 * @param��
	 * @param����
	 * @return
	 * @throws
	 */	
	public void clearUsedFields(){
		if(usedFields != null)
			usedFields.clear();
	}	
	
	/**
	 * ����ϵͳ����Ŀ�ʱ��
	 * ����ITreasuryDAO�޷�֧��ͨ����DataEntity��Setter������ͨ��set null�����ݿ��е�Date���͸���Ϊ��ֵ�����ͨ��
	 * ����һ��ϵͳ�ڵĿ�ʱ��(1970-01-01 08:00:00.0)��Ϊ��ʱ��ı�־ʱ�䣬��Ҫ�����ݿ���Date�������ݸ���Ϊnull��
	 * ��������ִ��setXXX����ʱ�����ô˲�����ȡϵͳ����Ŀ�ʱ���־��ITreasuryDAO����setTimeStampʱ�ж�ʱ���Ƿ�Ϊ
	 * ��ʱ�䣬����ǣ�����¸��ֶ�Ϊnull
	 * */
	static public Timestamp getNullTimeStamp(){
		return new Timestamp(0);
	}
		
	/**����Object��toString���������е����඼����ͨ���˷��������������*/
	public String toString(){
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
					if (o instanceof Float)
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
					else if (o instanceof Timestamp)
					{
						res.append("" + ((Timestamp) o).toString() + " \n");
					}
					else
						continue;
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
	
	/**author Barry
	 * 2003-12-18
	 * ��dataentityת����Attribute����request
	 */
	public void convertDataEntityToRequest(ServletRequest request) throws ITreasuryException
	{
		BeanInfo info = null;
		try{
			info = Introspector.getBeanInfo(this.getClass());
		}catch (IntrospectionException e) {
			throw new ITreasuryException("Java Bean.��ʡ�쳣����",e);			
		}
		//Log.print("----------��dataentityת����request----------");
		PropertyDescriptor[] p = info.getPropertyDescriptors();
		for (int n=0;n<p.length;n++){
			if (p[n].getName().compareToIgnoreCase("class")==0) continue;
			try{
				if (p[n].getReadMethod()!=null){
					//Log.print("key:" + p[n].getName() + "// value:" + p[n].getReadMethod().invoke(this,null));
					String strValue = (p[n].getReadMethod().invoke(this,new Object[]{})==null)?"":String.valueOf(p[n].getReadMethod().invoke(this,new Object[]{}));
					
					String strReturnType = p[n].getReadMethod().getReturnType().getName();
					
					if( strReturnType.equals(dataType[0]) &&  Double.parseDouble(strValue)==0.0){//parameter type is double
						strValue = null;
					}
					else if(p[n].getReadMethod().getReturnType().getName().equals(dataType[1]) && Long.parseLong(strValue)==-1){//parameter type is long
						strValue = null;
					}
					else if(p[n].getReadMethod().getReturnType().getName().equals(dataType[2]) && strValue.equals("")){			//parameter type is String
						strValue = null;
					}
					
					if (strValue != null) request.setAttribute(p[n].getName(),strValue);	//�������ֵ����null������request
				}
					
			}

			catch (IllegalAccessException e)
			{
				// TODO Auto-generated catch block
				throw new ITreasuryException("��dataentity����request�г��ִ���",e);
			}
			catch(InvocationTargetException e){
				throw new ITreasuryException("��dataentity����request�г��ִ���",e);
			}
		}
	}

	/**@author Barry
	 * 2004-1-9
	 * ��request�еõ�������dataentity
	 */
	public void convertRequestToDataEntity(ServletRequest request) throws ITreasuryException
	{
		/**
		 * �趨��ʼֵ
		 */
		Object[] objIniString 	= {""};
		Object[] objIniLong 	= {new Long(-1)};
		Object[] objIniDouble 	= {new Double(0.0)};
		Object[] objIniTimestamp= {null};
		
		BeanInfo info = null;
		try{
			info = Introspector.getBeanInfo(this.getClass());
		}catch (IntrospectionException e) {
			throw new ITreasuryException("Java Bean.��ʡ�쳣����",e);			
		}
		//Log.print("----------��requestת����dataentity----------");
		PropertyDescriptor[] p = info.getPropertyDescriptors();
		for (int n=0;n<p.length;n++){
			if (p[n].getName().compareToIgnoreCase("class")==0) continue;
			String strValue = (String) request.getAttribute(p[n].getName());
			
			//Log.print("key:" + p[n].getName() + "// Value:"+strValue);
			
			if (strValue != null){
				Object[] oParam = new Object[]{};
				Method m = p[n].getWriteMethod(); 
				if (m!=null){
					if(m.getParameterTypes()[0].getName().equals(dataType[0])){			//parameter type is double
						if (strValue.trim().equals("")){
							strValue = "0.0";
						}
						oParam = new Double[]{new Double(DataFormat.parseNumber(strValue.trim()))};
					}
					else if(m.getParameterTypes()[0].getName().equals(dataType[1])){	//parameter type is long
						/**
						 * ���ID�������⴦��,�����������ĳ���Ϊ0,˵��������,���������ʼֵ
						 */
						if (strValue.trim().equals("")) strValue = "-1";
						oParam = new Long[]{new Long(strValue.trim())};
					}
					else if(m.getParameterTypes()[0].getName().equals(dataType[2])){	//parameter type is String
						oParam = new String[]{strValue.trim()};
					}
					else if(m.getParameterTypes()[0].getName().equals(dataType[3])){	//parameter type is Timestamp
						oParam = new Timestamp[]{DataFormat.getDateTime(strValue.trim())};
					}
					//Modify by leiyang date 2007/09/10 �����޸ķ���
					else {
						oParam = null;
					}
					try
					{
						if(oParam != null){
							m.invoke(this,oParam);				//set parameters to dataentity
						}
					}
					catch (IllegalArgumentException e)
					{
						// TODO Auto-generated catch block
						throw new ITreasuryException("��request�л��dataentity����",e);
					}
					catch (IllegalAccessException e)
					{
						// TODO Auto-generated catch block
						throw new ITreasuryException("��request�л��dataentity����",e);
					}
					catch(InvocationTargetException e){
						throw new ITreasuryException("��request�л��dataentity����",e);
					}
				}
			}
		}
	}
	
	
}
