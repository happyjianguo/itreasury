/**
 *  created on 2005-11-25
 */
package com.iss.itreasury.ebank.system.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author xintan
 * 
 */
public class HtmlKit {
	
	/**日志对象*/
	private static Logger log = new Logger(HtmlKit.class);
	
	/**
	 * @param out	JspWriter对象
	 * @param paramInfo	查询条件
	 * 
	 * 用于将info转换成html页面的hidden标记以保存值的情况
	 */
	public static void convertDataEntityToHiddenTag(JspWriter out, ITreasuryBaseDataEntity paramInfo,String[] notSetToHidden)	
	{
		//取得查询条件
		if(paramInfo ==null)
		{
			log.debug("paramInfo 为空！");
			return;
		}
		//用于写入input hidden
		StringBuffer innerHtml = new StringBuffer();
		String strTemp=null;
		String name = null;
		BeanInfo info;
		//在此设置BaseDataEntity中不设到Hidden中的property
		String[] innerNotSet = {"usedField","usedFieldMap"};
		try{	
			info = Introspector.getBeanInfo(paramInfo.getClass());
			PropertyDescriptor[] properties = info.getPropertyDescriptors();
			Method m;
			Object oValue;
			//没有propety
			if(properties!=null )
			{
				double dTmp =0.0;
				long lTmp = -1;
				boolean isSet = true;
				for(int i=0; i<properties.length;i++)
				{
					isSet = true;
					name = properties[i].getName();
					if(name.compareToIgnoreCase("class")==0)
						continue;
					//首先屏蔽掉innerNotSet中设置的property
					for(int n=0;n<innerNotSet.length; n++)
					{
						if(name.compareToIgnoreCase(innerNotSet[n])==0)
						{
							isSet = false;
							break;
						}
					}
					if(!isSet)	continue;
					//如果在notSetToHidden中设定，屏蔽掉此property
					if(notSetToHidden!=null)
					{
						for(int n=0;n<notSetToHidden.length;n++)
						{
							if(name.compareToIgnoreCase(notSetToHidden[n])==0)
							{
								isSet = false;
								break;
							}
						}
						if(!isSet)
							continue;
					}
					 m = properties[i].getReadMethod();
					 oValue = m.invoke(paramInfo, new Object[]{});
					 if(oValue!=null)
					 {
						if(oValue instanceof Long)
						{
							lTmp = ((Long)oValue).longValue();
							strTemp ="" +lTmp;
							
						}
						else if(oValue instanceof Double)
						{
							dTmp = ((Double)oValue).doubleValue();
							strTemp = "" + dTmp;
						}
						else if(oValue instanceof String)
						{
							strTemp= (String)oValue;
							if("".equals(strTemp.trim()))
								continue;
						}
						else if(oValue instanceof Date)
						{
							strTemp = DataFormat.formatDate((Date)oValue,1);
							name = name.trim()+"String";
						}
						if(strTemp==null )
						{
							strTemp ="";
						}
					 }
					 else{
						 strTemp="";
					 }
					if(name != null && !name.equals("sessionMng"))
					{
					    innerHtml.append("<input type=\"hidden\" name=\""+ name + "\" value=\""+strTemp.trim()+"\"> \n");
					}
				}			
			}
			log.debug("input hidden:" + innerHtml.toString());
			out.print(innerHtml.toString());
		}catch(Exception ex)
		{
			log.debug("生成hidden tag失败", ex);
		}	
	}
	
	/**
	 * 本方法和getLongFromInfo配合使用，用于在jsp页面里从一个info里取值，同时简化格式化的过程。
	 * 由于在jsp页面中只用到String型和long型的值，所以提供两个方法把info里的值格式化成这两个类型。
	 * 		注意：1.本方法没有把long型的time转化成日期字符串的功能。
	 * 			2.本方法永远不会返回null,所以就不要在程序里加null的判断了！
	 * 			3.如果info有方法getAbC(),写属性名时请写成"AbC"或"abC",不要写成"abc"之类。后者比较耗时。

	 * @param paramInfo BaseDataEntity 要从中取值的Info
	 * @param propertyName 要获得值的属性的属性名：
	 * 			1.此属性名不同于在Info中定义的字段，如private long id,而是只要有get方法，
	 * 				如getUsedField（）（注意：BaseDataEntity中并没有usedField字段，但是却有这个方法）,
	 * 				usedField就是它的属性名。
	 * 			2.属性名的大小写和get方法一致或是首字小写。如果大小写不一致也可以取到值，但是会增加时间开销。建议不要这样写。：）
	 * @param flag int 使用此值取得指定格式的字符串
	 * 			＃. 属性值是long型。注：属性值的类型就是getXXX()的返回值类型。
	 * 				0：正常的数字字符串。也就是12345->"12345"
	 * 				1:number.12345->"12,345"
	 * 				2:UTC时差。-1 ->"0:1"
	 * 				其它：还没定义。
	 * 			#.属性值是String型。
	 * 				0：正常的String
	 * 				其它：还没有定义。
	 * 			#.Date或Timestamp
	 * 				0:日期字符串。:如：2005-01-01"
	 * 				<0:返回当前日期 N天以前的日期字符串。N为-flag.
	 * 				>0:当前日期N天以后的日期字符串。N为flag.
	 * 			#.double
	 * 				0:正常的double.如"12345.00"
	 * 				1:money.如："12,345.00"
	 * 				其它：没有定义。默认为和flag=0时返回值相同。
	 * @return	格式化后的字符串。
	 */
	public static String getStringFromInfo(ITreasuryBaseDataEntity paramInfo, String propertyName, int flag)
	{
		String strResult = "";
		String methodName = null;
		if(paramInfo==null || propertyName==null )
			return "";
		propertyName = propertyName.trim();
		if("".equals(propertyName))
			return "";
		//生成要调用的方法名
		methodName = "get" + propertyName.substring(0,1).toUpperCase()+propertyName.substring(1);
		
		Class clazz = paramInfo.getClass();
		
		Method m = null;
		//取得method
		try {
			m = clazz.getMethod(methodName,new Class[]{});
		} 
		catch (NoSuchMethodException e) 
		{
			//用其它方法得到method
			e.printStackTrace();
			Method[] ms = clazz.getMethods();
			if(ms!=null)
			{
				for(int i=0;i<ms.length;i++)
				{
					if(ms[i].getName().compareToIgnoreCase(methodName)==0)
						m = ms[i];
					break;
				}
			}
		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
			log.debug("获得" + propertyName + "的getMethod失败", ex);
		}
		//调用method,并将参数格式化
		if(m!=null)
		{
			Object oValue=null;
			//获得返回类型
			String typeName = m.getReturnType().getName();
			
			try {
				oValue = m.invoke(paramInfo, new Object[]{});
			} catch (Exception e) {
				e.printStackTrace();
				log.debug("执行方法" + methodName + "失败", e);
			}
			
			try{	
				if(typeName.compareTo(long.class.getName())==0)
				{
					strResult = convertLongToString(oValue, flag);			
				}
				else if(typeName.compareTo(double.class.getName())==0)
				{	
					strResult = convertDoubleToString(oValue, flag);	
				}
				else if(typeName.compareTo(String.class.getName())==0)
				{
					strResult = convertStringToString(oValue, flag);
				}
				else if(typeName.compareTo(Date.class.getName())==0)
				{
					strResult = convertDateToString(oValue, flag);
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				log.debug("property value 转化成 String失败,value的type:"+ typeName, ex);
			}
		}
		if(strResult==null) strResult = "";
		return strResult;
	}
	private static String convertStringToString(Object oValue, int flag) {
		if(oValue==null)
			return "";
		String strResult = null;
		try{
			strResult = ((String)oValue).trim();
			
		}catch(ClassCastException ex)
		{
			ex.printStackTrace();
			strResult = "";
		}
		return strResult;
	}
	private static String convertDateToString(Object oValue, int flag) {
		String strResult = null;
		Date dateTmp = null;
		if(oValue==null)
		{
			log.info("date:null");
			if(flag<0)
			{
				try {
					dateTmp = Env.getSystemDateTime();
					dateTmp = DateUtil.before(dateTmp, -((long)flag));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if(flag==0){
			    try {
					dateTmp = Env.getSystemDateTime();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}   
		}
		else
		{		
			try{
				dateTmp = (Date)oValue;
			}
			catch(ClassCastException ex)
			{
				ex.printStackTrace();
			}
		}
		strResult = DataFormat.formatDate(dateTmp,1);
		log.info("date:" + strResult);
		if(strResult==null)
			strResult ="";
		return strResult;
	}
	private static String convertDoubleToString(Object oValue, int flag) {
		String strResult = null;
		double doubleTmp =0.0;
		if(oValue==null)
		{
			doubleTmp = 0.0;
		}
		else
		{
			try{
			doubleTmp = ((Double)oValue).doubleValue();
			}catch(ClassCastException ex)
			{
				ex.printStackTrace();
			}
			
		}
		if(Double.isNaN(doubleTmp))
		{
			doubleTmp = 0.0;
		}
		switch(flag)
		{
		//显示为123,45.00
		case 1:
			strResult = DataFormat.formatNumber(doubleTmp, 2);
		default:
			strResult = "" + doubleTmp;
		}
		return strResult;
	}
	/**
	 * @param oValue
	 * @param flag
	 * @return
	 */
	private static String convertLongToString(Object oValue, int flag) {
		String strResult = null;
		long lTemp = -1;
		if(oValue==null)
		{
			switch(flag)
			{
			//id
			case 0:
				lTemp = -1;
				break;
			//number
			case 1:
				lTemp = 0;
				break;
			case 2:
				lTemp = 0;
				break;
			default:
				lTemp = 0;
			}
		}
		else{
			try{
				lTemp = ((Long)oValue).longValue();	
			}catch(ClassCastException ex)
			{
				ex.printStackTrace();
			}
		}
		switch(flag)
		{
		case 0:
			strResult = "" + lTemp;
			break;
		case 1:
			strResult = DataFormat.formatNumber(lTemp);
			break;
		//utc	
		case 2:
			if(lTemp<0) 
			{
				strResult="-";
				lTemp=-lTemp;
			}
			else strResult="+";
			strResult+=(lTemp/60)+":"+(Math.abs(lTemp%60));
			break;
		default:
			strResult = "" + lTemp;
		}
		
		return strResult;
	}
	/*
	public static String getStringFromCodeNameRef(String propertyName, long lId)
	{
		String strResult = "";
		
		return strResult;
	}
	*/
	/**
	 * 注意：保留此方法是为了程序的原有代码不出错，请不要使用它！可用getStringFromInfo(BaseDataEntity paramInfo, String propertyName, int flag)代替
	 * 
	 * @param paramInfo 要从中取值的BaseDataEntity
	 * @param propertyName 属性名
	 * @param flag int 标记，指示属性向String的转化方式。
	 * @return
	 */
	/**
	public static String getStringFromInfo(BaseDataEntity paramInfo, String propertyName, String refName, int flag)
	{
		if(paramInfo==null)
		{
			log.debug("pramInfo 为空");
			return "";
		}
		if(propertyName==null || propertyName.trim().length()==0)
		{
			log.debug("propertyName 无效");
			return "";
		}
		BeanInfo info = null;
		Method m = null;
		String strName = "";
		String strValue = "";
		boolean hasFind = false;
		boolean hasRef = false;
		if(refName!=null && refName.trim().length()>0) hasRef=true; 
		if(hasRef)
		{
			strName = refName;
		}
		else{
			strName = propertyName;
		}
		try{
			info = Introspector.getBeanInfo(paramInfo.getClass());
			PropertyDescriptor[] props = info.getPropertyDescriptors();
			for(int i=0;i<props.length;i++)
			{
				//如果propertyName 为info的属性名
				if((props[i].getName()).equals(strName))
				{
					//调用read方法，读取property的值
					m = props[i].getReadMethod();
					Object oValue  = m.invoke(paramInfo, null);
					if(oValue!=null)
					{
						try{
							
							if(oValue instanceof Long)
							{
								long lTemp = ((Long)oValue).longValue();
								if(hasRef)
								{
									strValue = getStringFromCodeNameRef(propertyName, lTemp);
								}
								else{
									strValue = ""+ lTemp;
								}
							}
							else if(oValue instanceof Double)
							{
								strValue = DataFormat.formatNumber(((Double)oValue).doubleValue(),2);
								
							}
							else if(oValue instanceof String)
							{
								strValue = "" + (String)oValue;
							}
							else if (oValue instanceof Date) {
								Date dTemp = (Date) oValue;
								strValue = DataFormat.formatDate(dTemp);
							}
						}
						catch(Exception ex)
						{
							strValue = "";
							ex.printStackTrace();
							log.debug("property value 转化成 String失败", ex);
						}
					}
					break;
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			log.debug("beaninfo 内省失败", ex);
		}
		if(strValue==null) strValue = "";
		return strValue;
	}
	*/
	/**
	 * 从info中取值，把long型和Date型以long型输出，其它类型则返回-1
	 * @param paramInfo
	 * @param propertyName
	 * @return
	 */
	public static long 	getLongFromInfo(ITreasuryBaseDataEntity paramInfo, String propertyName)
	{
		if(paramInfo==null)
		{
			log.debug("pramInfo 为空");
			return -1;
		}
		if(propertyName==null || propertyName.trim().length()==0)
		{
			log.debug("propertyName 无效");
			return -1;
		}
		BeanInfo info = null;
		Method m = null;
		long lValue = -1;
		boolean hasFind = false;
		try{
			info = Introspector.getBeanInfo(paramInfo.getClass());
			PropertyDescriptor[] props = info.getPropertyDescriptors();
			Object oValue = null;
			for(int i=0;i<props.length;i++)
			{
				if(props[i].getName().equals(propertyName))
				{
					hasFind = true;
					m = props[i].getReadMethod();
					oValue = m.invoke(paramInfo, new Object[]{});
					if(oValue!=null)
					{
						try{
							
							if(oValue instanceof Long)
							{
								lValue = ((Long)oValue).longValue();
							}
							else if (oValue instanceof Date) {
								Date dTemp = (Date) oValue;
								lValue = dTemp.getTime();
							}
							else if(oValue instanceof Double)
							{
								throw new ClassCastException(propertyName + "是double型，不能转化成long");
								
							}
							else if(oValue instanceof String)
							{
								throw new ClassCastException(propertyName + "是String型，不能转化成long");
							}
							else
							{
								throw new ClassCastException("无效类型" + oValue.getClass());
							}
							
						}
						catch(Exception ex)
						{
							lValue = -1;
							ex.printStackTrace();
							log.debug("property value 转化成 long 失败", ex);
						}
					}
					break;
				}
			}
			if(!hasFind)
			{
				log.debug(propertyName + "没有找到");
				lValue = -1;
			}
		}
		catch(Exception ex)
		{
			log.debug("beaninfo内省失败", ex);
		}
		return lValue;
	}
	/**
	 *  这个………… 还是不要用比较好……
	 * @param propertyName
	 * @param refValue
	 * @return
	 */
	/*
	private static String getStringFromCodeNameRef(String propertyName, long refValue)
	{
		String strValue = "";
		String methodName = "";
		if(propertyName==null ||propertyName.length()==0)
		{
			return "";
		}
		try {
			Class nameRef  = Class.forName("com.iss.itreasury.bankportal.util.CodeNameRef");
			Method m = null;
			//首字大写
			propertyName = propertyName.substring(0,1).toUpperCase() + propertyName.substring(1);
			methodName = "get"+propertyName + "ByID";
			if(nameRef!=null)
			{
				m = nameRef.getMethod(methodName, new Class[]{long.class});
				strValue = (String)m.invoke(nameRef.newInstance(), new Long[]{new Long(refValue)});
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			log.debug("CodeNameRef类没有找到", e);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			log.debug(methodName + "不存在", e);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("根据long 取得"+propertyName+"失败", e);
		}
		if(strValue==null) strValue="";
		return strValue;
	}
	*/
}