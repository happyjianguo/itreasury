/**
 * Created on 2009-5-15
 */

package com.iss.sysframe.base.dataentity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;

import org.apache.log4j.Logger;

import com.iss.sysframe.util.DataFormat;
/**
 * @author xintan
 *
 */
public abstract class BaseBean implements Serializable
{	
	Logger logger = Logger.getLogger(BaseBean.class);
	private final String[] dataType = {"double","long","java.lang.String","java.sql.Timestamp","java.util.Date"};//֧�ֵ���������

	/**
	 * ����Object��toString���������е����඼����ͨ���˷��������������
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
	 * ��request�еõ�������dataentity
	 * ֧�ֶ����Ƕ��
	 */
	public void convertRequestToDataEntity(ServletRequest request)
		throws Exception
	{

		Field[] fields = this.getClass().getDeclaredFields();
		String strValue = null;
		
		Field currField = null;
		String currFieldName = null;
	    String currFieldTypeName = null;
	    String methodName = null;
	    Method currMethod = null;
		
		try {
			for (int i=0; i<fields.length; i++)
			{
				currField = fields[i];
				currFieldName = currField.getName();
				
				strValue = request.getParameter(currFieldName);
				
				if (strValue != null && !strValue.equals("")){
					
					currFieldTypeName = currField.getType().getName();
	            	methodName = "set" + currFieldName.substring(0, 1).toUpperCase() + currFieldName.substring(1);
					
	            	//����Ϊ������������
	            	if(currFieldTypeName.equals(double.class.getName()))
	            	{
	            		currMethod = this.getClass().getMethod(methodName, new Class[]{double.class});
	            		currMethod.invoke(this, new Object[]{new Double(DataFormat.parseNumber(strValue.trim()))});
	            	}
	            	else if(currFieldTypeName.equals(long.class.getName()))
	            	{
	            		currMethod = this.getClass().getMethod(methodName, new Class[]{long.class});
	            		currMethod.invoke(this, new Object[]{new Long(strValue.trim())});
	            	}
	            	else if(currFieldTypeName.equals(Timestamp.class.getName()))
	            	{
	            		currMethod = this.getClass().getMethod(methodName, new Class[]{Timestamp.class});
	            		currMethod.invoke(this, new Object[]{DataFormat.getDateTime(strValue.trim())});
	            	}
	            	else if(currFieldTypeName.equals(Date.class.getName()))
	            	{
	            		currMethod = this.getClass().getMethod(methodName, new Class[]{Date.class});
	            		currMethod.invoke(this, new Object[]{DataFormat.parseDate(strValue.trim())});
	            	}
	            	else if(currFieldTypeName.equals(String.class.getName()))
	            	{
	            		currMethod = this.getClass().getMethod(methodName, new Class[]{String.class});
	            		currMethod.invoke(this, new Object[]{strValue.trim()});
	            	}
	            	else
	            	{
	            		logger.error("����[" + methodName + "]�Ĳ���Ϊδ֪���������ͣ��޷�����ת��");
	            	}
	
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
			throw new Exception("��ServletRequest�л��dataentity����", e);
		}
	}
	
	/**
	 * ��request�еõ�������dataentity
	 * ֧�ֶ����Ƕ��
	 */
	public void convertMapToDataEntity(Map map) throws Exception
	{

		Field[] fields = this.getClass().getDeclaredFields();
		String strValue = null;
		
		Field currField = null;
		String currFieldName = null;
	    String currFieldTypeName = null;
	    String methodName = null;
	    Method currMethod = null;
		
		try {
			//System.out.println("------------convertMapToDataEntity----------");
			for (int i=0; i<fields.length; i++)
			{
				
				currField = fields[i];
				//System.out.println("------------convertMapToDataEntity----------currField="+currField);
				currFieldName = currField.getName();
				if(map.containsKey(currFieldName.toLowerCase()))
				{
					if(map.get(currFieldName.toLowerCase()) instanceof String){
						strValue =map.get(currFieldName.toLowerCase())==null||map.get(currFieldName.toLowerCase()).equals("null") ?"" :(String)map.get(currFieldName.toLowerCase()) ;
					}else{
						if( ((String[])map.get(currFieldName.toLowerCase())).length>0)
						strValue = ((String[])map.get(currFieldName.toLowerCase()))[0]==null?"": ((String[])map.get(currFieldName.toLowerCase()))[0];
					}
					logger.debug("map������" + currFieldName + " =  " + strValue);
				}
				else
				{
					continue;
				}
				
				if (strValue != null && !strValue.equals("")){
					
					currFieldTypeName = currField.getType().getName();
	            	methodName = "set" + currFieldName.substring(0, 1).toUpperCase() + currFieldName.substring(1);
					
	            	//System.out.println("-------convertMapToDataEntity--------strValue="+strValue);
	            	//����Ϊ������������
	            	if(currFieldTypeName.equals(double.class.getName()))
	            	{
	            		currMethod = this.getClass().getMethod(methodName, new Class[]{double.class});
	            		currMethod.invoke(this, new Object[]{new Double(DataFormat.parseNumber(strValue.trim()))});
	            	}
	            	else if(currFieldTypeName.equals(long.class.getName()))
	            	{
	            		currMethod = this.getClass().getMethod(methodName, new Class[]{long.class});
	            		currMethod.invoke(this, new Object[]{new Long(strValue.trim())});
	            	}
	            	else if(currFieldTypeName.equals(Timestamp.class.getName()))
	            	{
	            		currMethod = this.getClass().getMethod(methodName, new Class[]{Timestamp.class});
	            		currMethod.invoke(this, new Object[]{DataFormat.getDateTime(strValue.trim())});
	            	}
	            	else if(currFieldTypeName.equals(Date.class.getName()))
	            	{
	            		currMethod = this.getClass().getMethod(methodName, new Class[]{Date.class});
	            		currMethod.invoke(this, new Object[]{DataFormat.parseDate(strValue.trim())});
	            	}
	            	else if(currFieldTypeName.equals(String.class.getName()))
	            	{
	            		currMethod = this.getClass().getMethod(methodName, new Class[]{String.class});
	            		currMethod.invoke(this, new Object[]{strValue.trim()});
	            	}
	            	else if(currFieldTypeName.equals(boolean.class.getName()))
	            	{
	            		currMethod = this.getClass().getMethod(methodName, new Class[]{boolean.class});
	            		currMethod.invoke(this, new Object[]{new Boolean(strValue.trim())});
	            	}
	            	else
	            	{
	            		logger.error("����[" + methodName + "]�Ĳ���Ϊδ֪���������ͣ��޷�����ת��");
	            	}
	
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
			throw new Exception("��ServletRequest�л��dataentity����:"+currFieldName, e);
		}
	}
	
	/**
	 * �Ѷ�������ת����Hidden��
	 * @param out
	 * @throws Exception
	 */
	public void convertDataEntityToHidden(JspWriter out)
		throws Exception
	{

		Field[] fields = this.getClass().getDeclaredFields();
		Field currField = null;
		Object currFieldValue = null;
		String currFieldName = null;
	    String currFieldTypeName = null;
		
		try {
			for (int i=0; i<fields.length; i++)
			{
				currField = fields[i];
				currFieldName = currField.getName();
				currFieldTypeName = currField.getType().getName();

	            currField.setAccessible(true);
	            currFieldValue = currField.get(this);

	            if(currFieldName.equals("id")){
	            	continue;
	            }
	            	
            	//����Ϊ������������
            	if(currFieldTypeName.equals(double.class.getName()))
            	{
            		double d = Double.parseDouble(currFieldValue.toString());
            		out.print("<input type=\"hidden\" name=\""+ currFieldName +"\" value=\""+ DataFormat.formatAmount(d, 2) +"\" />\n");
            	}
            	else if(currFieldTypeName.equals(long.class.getName()))
            	{
            		out.print("<input type=\"hidden\" name=\""+ currFieldName +"\" value=\""+ currFieldValue.toString() +"\" />\n");
            	}
            	else if(currFieldTypeName.equals(Timestamp.class.getName()))
            	{
            		Timestamp ts = (Timestamp)currFieldValue;
            		if(ts != null)
            		{
            			out.print("<input type=\"hidden\" name=\""+ currFieldName +"\" value=\""+ DataFormat.formatDate(ts, DataFormat.DT_YYYYMMDD_HHMMSS) +"\" />\n");
            		}
            	}
            	else if(currFieldTypeName.equals(Date.class.getName()))
            	{
            		Date dt = (Date)currFieldValue;
            		if(dt != null)
            		{
            			out.print("<input type=\"hidden\" name=\""+ currFieldName +"\" value=\""+ DataFormat.formatDate(dt, DataFormat.DT_YYYY_MM_DD) +"\" />\n");
            		}
            	}
            	else if(currFieldTypeName.equals(String.class.getName()))
            	{
            		String str = (String)currFieldValue;
            		if(str != null && !str.equals(""))
            		{
            			out.print("<input type=\"hidden\" name=\""+ currFieldName +"\" value=\""+ str +"\" />\n");
            		}
            	}
            	else
            	{
            		logger.error("����[" + currFieldName + "]�Ĳ���Ϊδ֪���������ͣ��޷�����ת��");
            	}
			}
		}
		catch(Exception e){
			e.printStackTrace();
			throw new Exception("��ServletRequest�л��dataentity����", e);
		}
	}
	
	/**
	 * ��ʼ�����е�StringΪ""
	 */
	public void initString() throws Exception
	{

		Field[] fields = this.getClass().getDeclaredFields();
		//String strValue = null;
		
		Field currField = null;
		String currFieldName = null;
		Class currFieldType = null;
		Object currFieldValue = null;
		
		
	    String methodName = null;
	    Method currMethod = null;
		
		try {
			for (int i=0; i<fields.length; i++)
			{
				
				currField = fields[i];
				currFieldName = currField.getName();
				currFieldType = currField.getType();
				
            	currField.setAccessible(true);
            	currFieldValue = currField.get(this);
            	methodName = "set" + currFieldName.substring(0, 1).toUpperCase() + currFieldName.substring(1);
				
				if (currFieldValue == null){
					
					if(currFieldType.getName().equals(String.class.getName()))
					{
	            		currMethod = this.getClass().getMethod(methodName, new Class[]{String.class});
	            		currMethod.invoke(this, new Object[]{""});
					}
					
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
			throw new Exception("��ServletRequest�л��dataentity����", e);
		}
	}
	
	/**
	 * ��ʵ�����Ի�ȡ����ǩ������ǰ����
	 * @return
	 */
	public String getSignatureTextFromDataEntity()
	{
		StringBuffer res = new StringBuffer();
		Method[] methods = getClass().getDeclaredMethods();
		for (int i = 0; i < methods.length; i++)
		{
			Method tmp = methods[i];
			String mName = tmp.getName();
			if (mName.startsWith("get"))
			{
				String fName = mName.substring(3)+"=";
				try
				{
					//���е��޸�ʱ�侫ȷ���룬���е�id���з��۸ļ�ǩ
					if(fName.toLowerCase().indexOf("modifytime")!=-1||fName.toLowerCase().indexOf("id")!=-1)
					{
						Object o = tmp.invoke(this, new Object[]{});
						if (o == null)
						{
							continue;
						}
						if (o instanceof Double)
						{
							fName+=((Double) o).doubleValue();
						}
						else if (o instanceof Float)
						{
							fName+= ((Float) o).floatValue();
						}
						else if (o instanceof Long)
						{
							//ֵΪ-1��Ĭ��Ϊ��ֵ�������м�ǩ
							long lValue=((Long) o).longValue();
							if(lValue==-1)
							{
								continue;
							}
							else{
								fName+=lValue;
							}
						}
						else if (o instanceof String)
						{
							fName+=(String) o;
						}
						else if (o instanceof Date)
						{
							fName+=((Date) o).toString();
						}
						else
						{
							fName+= o.toString();
						}
						
						res.append(fName+"\n");
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
	 * ���ݸ���ʵ����ת���ַ���
	 * ������ʷ����������ϸ��Ϣ�Ĵ洢
	 * @return
	 * edit by YgNiu
	 */
	public String convertDataEntityToString()
	{
		StringBuffer res = new StringBuffer();
		Method[] methods = getClass().getDeclaredMethods();
		for (int i = 0; i < methods.length; i++)
		{
			Method tmp = methods[i];
			String mName = tmp.getName();
			if (mName.startsWith("get"))
			{
				String fName = mName.substring(3)+"=";
				try
				{
					Object o = tmp.invoke(this, new Object[]{});
					if (o == null)
					{
						continue;
					}
					if (o instanceof Double)
					{
						fName+=((Double) o).doubleValue();
					}
					else if (o instanceof Float)
					{
						fName+= ((Float) o).floatValue();
					}
					else if (o instanceof Long)
					{
						//ֵΪ-1��Ĭ��Ϊ��ֵ�������м�ǩ
						long lValue=((Long) o).longValue();
						if(lValue==-1)
						{
							continue;
						}
						else{
							fName+=lValue;
						}
					}
					else if (o instanceof String)
					{
						fName+=(String) o;
					}
					else if (o instanceof Date)
					{
						fName+=((Date) o).toString();
					}
					else
					{
						//�������Ͳ�����ת��
						//fName+= o.toString();
					}
					
					res.append(fName+"\n");
					
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return res.toString();
	}
	
	/**
	 * ���ݸ����ַ���ת��ʵ����
	 * ������ʷ����������ϸ��Ϣ�Ļ���
	 * @return
	 * edit by YgNiu
	 */
	public void convertStringToDataEntity(String dataString)
	{
		try{
			if(dataString!=null&&dataString.trim().length()>0)
			{
				String key_values[]=dataString.split("\n");
				HashMap map=new HashMap();
				//ת����convertMapToDataEntity����ʹ�õ�map
				if(key_values!=null&&key_values.length>0)
				{
					for(int i=0;i<key_values.length;i++)
					{
						String key_value=key_values[i];
						if(key_value!=null&&key_value.indexOf("=")!=-1)
						{
							String key=key_value.substring(0,key_value.indexOf("="));
							String value=key_value.substring(key_value.indexOf("=")+1);
							if(value!=null&&value.trim().length()>0)
							{
								map.put(key.toLowerCase(), value);
								//System.out.println("--------convertStringToDataEntity--------key.toLowerCase()="+key.toLowerCase()+"    value="+value);
							}
						}
					}
				}
				
				convertMapToDataEntity(map);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}