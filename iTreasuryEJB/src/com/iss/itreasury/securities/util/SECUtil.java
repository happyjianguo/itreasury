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
 * ֤ȯģ��Ĺ����࣬��Ų������κ���Ĺ��ò���
 */
public class SECUtil {
	/**
	 * 
	 * ����Java�ļ����Ͳ��ܹ���ȷ�ĶԸ������������㣬����������ṩ��
	 * 
	 * ȷ�ĸ��������㣬�����Ӽ��˳����������롣 Copy from settlement's UtilOperation
	 *  
	 */
	static public class Arith
	{

		//Ĭ�ϳ������㾫��
		private static final int DEF_DIV_SCALE = 10;

		/**
		 * 
		 * �ṩ��ȷ�ļӷ����㡣
		 * 
		 * @param v1
		 *            ������
		 * 
		 * @param v2
		 *            ����
		 * 
		 * @return ���������ĺ�
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
		 * �ṩ��ȷ�ļ������㡣
		 * 
		 * @param v1
		 *            ������
		 * 
		 * @param v2
		 *            ����
		 * 
		 * @return ���������Ĳ�
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
		 * �ṩ��ȷ�ĳ˷����㡣
		 * 
		 * @param v1
		 *            ������
		 * 
		 * @param v2
		 *            ����
		 * 
		 * @return ���������Ļ�
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
		 * �ṩ��ȷ���������㡣
		 * 
		 * @param inputs
		 *            ���˲���
		 * 
		 * @return ��
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
		 * �ṩ����ԣ���ȷ�ĳ������㣬�����������������ʱ����ȷ��
		 * 
		 * С�����Ժ�10λ���Ժ�������������롣
		 * 
		 * @param v1
		 *            ������
		 * 
		 * @param v2
		 *            ����
		 * 
		 * @return ������������
		 *  
		 */
		public static double div(double v1, double v2)
		{

			return div(v1, v2, DEF_DIV_SCALE);

		}

		/**
		 * 
		 * �ṩ����ԣ���ȷ�ĳ������㡣�����������������ʱ����scale����ָ
		 * 
		 * �����ȣ��Ժ�������������롣
		 * 
		 * @param v1
		 *            ������
		 * 
		 * @param v2
		 *            ����
		 * 
		 * @param scale
		 *            ��ʾ��ʾ��Ҫ��ȷ��С�����Ժ�λ��
		 * 
		 * @return ������������
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
		 * �ṩ����ԣ���ȷ�ĳ������㡣�����������������ʱ����scale����ָ
		 * 
		 * �����ȣ��Ժ�������������롣
		 * 
		 * @param inputs
		 *            �������������뱣֤˳��
		 * 
		 * @param scale
		 *            ��ʾ��ʾ��Ҫ��ȷ��С�����Ժ�λ��
		 * 
		 * @return ��������
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
		 * �ṩ��ȷ��С��λ�������봦��
		 * 
		 * @param v
		 *            ��Ҫ�������������
		 * 
		 * @param scale
		 *            С���������λ
		 * 
		 * @return ���������Ľ��
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
		 * �ṩ�������ıȽϡ�by Huang Ye
		 * 
		 * @param d1            ��Ҫ�Ƚϵ�����1
		 * @param d2            ��Ҫ�Ƚϵ�����2
		 * @param scale
		 *            ��֮�Ƚϵ����ֵ�С��λ��
		 * 
		 * @return �Ƿ����
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
	 * �õ�û�пմ����ַ�������
	 * ����ַ��������п��ַ�����null�ַ���,��������Сһ
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
		final String[] dataType = {"double","long","java.lang.String","java.sql.Timestamp"};			//֧�ֵ���������
		BeanInfo info = null;
		try{
			info = Introspector.getBeanInfo(dataEntity.getClass());
		}catch (IntrospectionException e) {
			throw new SecuritiesException("Java Bean.��ʡ�쳣����",e);			
		}
		//Log.print("----------��dataentityת����hidden----------");
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
				throw new SecuritiesException("��dataentity����ת��Ϊhidden����ִ���",e);
			}
			catch(InvocationTargetException e){
				throw new SecuritiesException("��dataentity����ת��Ϊhidden����ִ���",e);
			}
		}
		
		return strOutput;
	}
	
	/**
	 * ͨ����������ID����ҵ�����͵�ID
	 * @param actionID ��������
	 * @return
	 * @throws Exception
	 */
	public static long getBusinessIDByTransactionID(long actionID) throws Exception{
		SEC_TransactionTypeDAO actionDAO=new SEC_TransactionTypeDAO();
		TransactionTypeInfo actionInfo=(TransactionTypeInfo)actionDAO.findByID(actionID,TransactionTypeInfo.class);
		return actionInfo.getBusinessTypeID();	
		
	}
	
	/**
	 * �������ļ��ж�ȡ֤ȯϵͳ��һ������ʱ��
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
			System.out.println("���ܴ� sec.properties �ļ���" + strFile);
			e.printStackTrace();
		}
		try
		{			
			return DataFormat.getDateTime(p.getProperty("SEC_FIRST_RUN_DATE"));
		}catch(Exception e){
			System.out.println("���ܶ�ȡ֤ȯ����ʱ��");
			e.printStackTrace();
		}
		return DataFormat.getDateTime("");
		
	}
}
