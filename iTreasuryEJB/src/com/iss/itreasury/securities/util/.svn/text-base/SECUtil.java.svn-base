/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-3
 */
package com.iss.itreasury.securities.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;

import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.util.Log;

import com.iss.itreasury.securities.setting.dao.SEC_TransactionTypeDAO;
import com.iss.itreasury.securities.setting.dataentity.TransactionTypeInfo;
import com.iss.itreasury.util.DataFormat;
import java.sql.Timestamp;
import java.util.Properties;
import java.io.FileInputStream;

/**
 * 证券模块的工具类，存放不属于任何类的公用操作
 */
public class SECUtil {
	/**
	 * 
	 * 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精
	 * 
	 * 确的浮点数运算，包括加减乘除和四舍五入。 Copy from settlement's UtilOperation
	 *  
	 */
	static public class Arith
	{

		//默认除法运算精度
		private static final int DEF_DIV_SCALE = 10;

		/**
		 * 
		 * 提供精确的加法运算。
		 * 
		 * @param v1
		 *            被加数
		 * 
		 * @param v2
		 *            加数
		 * 
		 * @return 两个参数的和
		 *  
		 */
		public static double add(double v1, double v2)
		{

			BigDecimal b1 = new BigDecimal(Double.toString(v1));

			BigDecimal b2 = new BigDecimal(Double.toString(v2));

			return b1.add(b2).doubleValue();

		}

		/**
		 * 
		 * 提供精确的减法运算。
		 * 
		 * @param v1
		 *            被减数
		 * 
		 * @param v2
		 *            减数
		 * 
		 * @return 两个参数的差
		 *  
		 */
		public static double sub(double v1, double v2)
		{

			BigDecimal b1 = new BigDecimal(Double.toString(v1));

			BigDecimal b2 = new BigDecimal(Double.toString(v2));

			return b1.subtract(b2).doubleValue();

		}

		/**
		 * 
		 * 提供精确的乘法运算。
		 * 
		 * @param v1
		 *            被乘数
		 * 
		 * @param v2
		 *            乘数
		 * 
		 * @return 两个参数的积
		 *  
		 */
		public static double mul(double v1, double v2)
		{

			BigDecimal b1 = new BigDecimal(Double.toString(v1));

			BigDecimal b2 = new BigDecimal(Double.toString(v2));

			return b1.multiply(b2).doubleValue();

		}

		/**
		 * 
		 * 提供精确的连乘运算。
		 * 
		 * @param inputs
		 *            连乘参数
		 * 
		 * @return 积
		 *  
		 */
		public static double mul(double[] inputs)
		{

			double res = 0.0;
			for (int i = 0; i < inputs.length - 1; i++)
			{
				double v1 = 0.0;
				double v2 = 0.0;
				if (i == 0)
					v1 = inputs[i];
				else
					v1 = res;
				v2 = inputs[i + 1];
				BigDecimal b1 = new BigDecimal(Double.toString(v1));

				BigDecimal b2 = new BigDecimal(Double.toString(v2));

				res = b1.multiply(b2).doubleValue();
			}
			return res;

		}

		/**
		 * 
		 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
		 * 
		 * 小数点以后10位，以后的数字四舍五入。
		 * 
		 * @param v1
		 *            被除数
		 * 
		 * @param v2
		 *            除数
		 * 
		 * @return 两个参数的商
		 *  
		 */
		public static double div(double v1, double v2)
		{

			return div(v1, v2, DEF_DIV_SCALE);

		}

		/**
		 * 
		 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
		 * 
		 * 定精度，以后的数字四舍五入。
		 * 
		 * @param v1
		 *            被除数
		 * 
		 * @param v2
		 *            除数
		 * 
		 * @param scale
		 *            表示表示需要精确到小数点以后几位。
		 * 
		 * @return 两个参数的商
		 *  
		 */
		public static double div(double v1, double v2, int scale)
		{

			if (scale < 0)
			{

				throw new IllegalArgumentException("The scale must be a positive integer or zero");

			}

			BigDecimal b1 = new BigDecimal(Double.toString(v1));

			BigDecimal b2 = new BigDecimal(Double.toString(v2));

			return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

		}

		/**
		 * 
		 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
		 * 
		 * 定精度，以后的数字四舍五入。
		 * 
		 * @param inputs
		 *            连除参数，必须保证顺序
		 * 
		 * @param scale
		 *            表示表示需要精确到小数点以后几位。
		 * 
		 * @return 参数的商
		 * 
		 * @author Add by Huang Ye
		 *  
		 */
		public static double div(double[] inputs, int scale)
		{
			if (scale < 0)
			{

				throw new IllegalArgumentException("The scale must be a positive integer or zero");

			}
			double res = 0.0;
			for (int i = 0; i < inputs.length - 1; i++)
			{
				double v1 = 0.0;
				double v2 = 0.0;
				if (i == 0)
					v1 = inputs[i];
				else
					v1 = res;
				v2 = inputs[i + 1];
				BigDecimal b1 = new BigDecimal(Double.toString(v1));

				BigDecimal b2 = new BigDecimal(Double.toString(v2));

				res = b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
			}
			return res;

		}
		/**
		 * 
		 * 提供精确的小数位四舍五入处理。
		 * 
		 * @param v
		 *            需要四舍五入的数字
		 * 
		 * @param scale
		 *            小数点后保留几位
		 * 
		 * @return 四舍五入后的结果
		 *  
		 */
		public static double round(double v, int scale)
		{

			if (scale < 0)
			{

				throw new IllegalArgumentException("The scale must be a positive integer or zero");

			}

			BigDecimal b = new BigDecimal(Double.toString(v));

			BigDecimal one = new BigDecimal("1");

			return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

		}

		/**
		 * 
		 * 提供两个数的比较　by Huang Ye
		 * 
		 * @param d1            需要比较的数字1
		 * @param d2            需要比较的数字2
		 * @param scale
		 *            与之比较的数字的小数位数
		 * 
		 * @return 是否相等
		 *  
		 */
		public static boolean equal(double d1, double d2, int scale)
		{
			double difference = Math.abs(d1 - d2);
			double minNum = Math.pow(10, (-1) * scale);
			if (difference < minNum)
				return true;
			else
				return false;
		}

	}	
	/**
	 * 得到没有空串的字符型数组
	 * 如果字符数组中有空字符串或null字符串,则数组缩小一
	 * @param strParams
	 * @return
	 */
	public static String[] getNotNullStringArray(String[] strParams){
		String[] str = null;
		ArrayList al = new ArrayList();
		if (strParams != null){
			for (int n=0;n<strParams.length;n++){
				if (strParams[n] != null && strParams[n].trim().length()>0){
					al.add(strParams[n]);
				}
			}
			
			str = new String[al.size()];
			for (int n=0;n<al.size();n++){
				str[n] = (String)al.get(n);
			}
		}
		
		return str;
	}
	
	public static String outputDataEntityHiddenElement(Object dataEntity)throws SecuritiesException{
		String strOutput = "";
		final String[] dataType = {"double","long","java.lang.String","java.sql.Timestamp"};			//支持的数据类型
		BeanInfo info = null;
		try{
			info = Introspector.getBeanInfo(dataEntity.getClass());
		}catch (IntrospectionException e) {
			throw new SecuritiesException("Java Bean.内省异常发生",e);			
		}
		//Log.print("----------从dataentity转化到hidden----------");
		PropertyDescriptor[] p = info.getPropertyDescriptors();
		for (int n=0;n<p.length;n++){
			if (p[n].getName().compareToIgnoreCase("class")==0) continue;
			try{
				//Log.print("key:" + p[n].getName() + "// value:" + p[n].getReadMethod().invoke(dataEntity,null));
				String strValue = (p[n].getReadMethod().invoke(dataEntity,new Object[]{})==null)?"":String.valueOf(p[n].getReadMethod().invoke(dataEntity,new Object[]{}));
		
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
				if (strValue != null){
					if (!p[n].getName().equals("id") && !p[n].getName().equals("currencyId") && !p[n].getName().equals("officeId")){
						strOutput += "<input type='hidden' name= '" + p[n].getName() + "' value='" + strValue + "'> \n";
					}
				}
				
			}
				catch (IllegalAccessException e)
			{
				// TODO Auto-generated catch block
				throw new SecuritiesException("把dataentity置入转化为hidden域出现错误",e);
			}
			catch(InvocationTargetException e){
				throw new SecuritiesException("把dataentity置入转化为hidden域出现错误",e);
			}
		}
		
		return strOutput;
	}
	
	/**
	 * 通过交易类型ID查找业务类型的ID
	 * @param actionID 交易类型
	 * @return
	 * @throws Exception
	 */
	public static long getBusinessIDByTransactionID(long actionID) throws Exception{
		SEC_TransactionTypeDAO actionDAO=new SEC_TransactionTypeDAO();
		TransactionTypeInfo actionInfo=(TransactionTypeInfo)actionDAO.findByID(actionID,TransactionTypeInfo.class);
		return actionInfo.getBusinessTypeID();	
		
	}
	
	/**
	 * 从配置文件中读取证券系统第一次上线时间
	 * @author zpli
	 * @return
	 */
	public static Timestamp getSEC_FIRST_RUN_DATE(){
		Properties p = new Properties();
		String strFile = "sec.properties";
		
		try
		{
			//m_prop = new Properties();
			FileInputStream is = new FileInputStream(strFile);
			p.load(is);
			is.close();
		}
		catch (Exception e)
		{			
			System.out.println("不能打开 sec.properties 文件。" + strFile);
			e.printStackTrace();
		}
		try
		{			
			return DataFormat.getDateTime(p.getProperty("SEC_FIRST_RUN_DATE"));
		}catch(Exception e){
			System.out.println("不能读取证券上线时间");
			e.printStackTrace();
		}
		return DataFormat.getDateTime("");
		
	}
}
