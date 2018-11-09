/*
 * Created on 2005-11-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author rongyang To change this generated comment edit the template variable
 *         "typecomment": Window>Preferences>Java>Templates. To enable and
 *         disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class ObjectUtil
{

	/**
	 * Constructor for ObjectUtil.
	 */
	private ObjectUtil()
	{
		super();
	}

	/**
	 * 对提供的对象集合按照指定的属性进行排序。 <br>
	 * 逻辑：根据提供的getMethodName参数指定的方法名，获取待排序对象的相应属性，然后根据 <br>
	 * 此属性进行排序，当getMethodName参数指定的方法返回值是java原始类型时，直接用布 <br>
	 * 尔运算比较，如果是对象，则检查对象是否提供compareTo(Object o)方法，如果有，就 <br>
	 * 使用此方法进行比较。如果没有则抛异常。
	 * 
	 * @param objs
	 *            原对象集合，不能为null，size=0，直接返回该数组
	 * @param getMethodName
	 *            objs中对象的获取属性的方法名，例如："getName"。不能为null，如果对象中无此方法，将抛异常
	 * @param isDesc
	 *            是否降序，true 降序排列，false 升序排列
	 * @return Object[]
	 */
	public static Object[] sortObjectByField(Object[] objs,
			String getMethodName, boolean isDesc) throws Exception
	{
		if (objs == null || objs.length <= 0)
		{
			throw new Exception("Object[] is null.");
		}

		Class objClass = null;
		Method objMethod = null;

		Object valueObj = null;
		int type = -1;
		Object objectTemp = null;

		try
		{
			objClass = objs[0].getClass();
			objMethod = objClass.getMethod(getMethodName, new Class[]{});
			if (objMethod == null)
			{
				throw new Exception();
			}
		}
		catch (Throwable t)
		{
			throw new Exception("\nClass:" + objClass.toString()
					+ " has no such method:" + objMethod + "().");
		}

		Class classTemp = objMethod.getReturnType();

		if (classTemp.equals(long.class))
		{
			type = 1;
		}
		else if (classTemp.equals(double.class))
		{
			type = 2;
		}
		else if (!classTemp.isArray())
		{
			try
			{
				Method valueObjMethod = null;
				valueObjMethod = classTemp.getMethod("compareTo",
						new Class[]{Object.class});
				if (valueObjMethod == null)
				{
					throw new Exception();
				}
				type = 4;
			}
			catch (Throwable t)
			{
				throw new Exception("\nClass:" + classTemp.toString()
						+ " has no such method:compareTo(Object o).");
			}
		}
		else
		{
			throw new Exception("unexpected type.");
		}

		if (type == 1)
		{
			for (int i = 0; i < objs.length - 1; i++)
			{
				Object doubleObjecti = objMethod.invoke(objs[i], new Object[]{});
				Object doubleObjectj = null;
				Object doubleObjectTemp = doubleObjecti;
				int pos = -1;
				for (int j = i + 1; j < objs.length; j++)
				{
					doubleObjectj = objMethod.invoke(objs[j], new Object[]{});
					if (isDesc == true
							&& compareTo(doubleObjectTemp, doubleObjectj, type,
									null) < 0)
					{
						doubleObjectTemp = doubleObjectj;
						pos = j;
					}
					else if (isDesc == false
							&& compareTo(doubleObjectTemp, doubleObjectj, type,
									null) > 0)
					{
						doubleObjectTemp = doubleObjectj;
						pos = j;
					}
				}
				if (pos != -1)
				{
					objectTemp = objs[i];
					objs[i] = objs[pos];
					objs[pos] = objectTemp;
				}
			}
		}
		else if (type == 2)
		{
			for (int i = 0; i < objs.length - 1; i++)
			{
				Object longObjecti = objMethod.invoke(objs[i], new Object[]{});
				Object longObjectj = null;
				Object longObjectTemp = longObjecti;
				int pos = -1;
				for (int j = i + 1; j < objs.length; j++)
				{
					longObjectj = objMethod.invoke(objs[j], new Object[]{});
					if (isDesc == true
							&& compareTo(longObjectTemp, longObjectj, type,
									null) < 0)
					{
						longObjectTemp = longObjectj;
						pos = j;
					}
					else if (isDesc == false
							&& compareTo(longObjectTemp, longObjectj, type,
									null) > 0)
					{
						longObjectTemp = longObjectj;
						pos = j;
					}
				}
				if (pos != -1)
				{
					objectTemp = objs[i];
					objs[i] = objs[pos];
					objs[pos] = objectTemp;
				}
			}
		}
		else if (type == 4)
		{
			int isDescEndPos = 0;
			for (int i = 0; i < objs.length - 1 - isDescEndPos; i++)
			{
				Object objectValuei = objMethod.invoke(objs[i], new Object[]{});
				if (objectValuei == null && isDesc == false)
				{
					objectTemp = objs[i];
					for (int k = i; k > 0; k--)
					{
						objs[k] = objs[k - 1];
					}
					objs[0] = objectTemp;
					continue;
				}
				else if (objectValuei == null && isDesc == true)
				{
					objectTemp = objs[i];
					for (int k = i; k < objs.length - 1 - isDescEndPos; k++)
					{
						objs[k] = objs[k + 1];
					}
					objs[objs.length - 1 - isDescEndPos] = objectTemp;
					isDescEndPos++;
					i--;
					continue;
				}

				Object objectValuej = null;
				Object objectValueTemp = objectValuei;

				Class objClassTemp = objectValuei.getClass();
				Method objMethodTemp = objClassTemp.getMethod("compareTo",
						new Class[]{Object.class});

				int pos = -1;
				for (int j = i + 1; j < objs.length - isDescEndPos; j++)
				{
					objectValuej = objMethod.invoke(objs[j], new Object[]{});

					if (objectValuej == null && isDesc == false)
					{
						objectTemp = objs[j];
						for (int k = j; k > 0; k--)
						{
							objs[k] = objs[k - 1];
						}
						objs[0] = objectTemp;
						i++;
						continue;
					}
					else if (objectValuej == null && isDesc == true)
					{
						objectTemp = objs[j];
						for (int k = j; k < objs.length - 1 - isDescEndPos; k++)
						{
							objs[k] = objs[k + 1];
						}
						objs[objs.length - 1 - isDescEndPos] = objectTemp;
						isDescEndPos++;
						j--;
						continue;
					}

					int result = compareTo(objectValueTemp, objectValuej, type,
							objMethodTemp);
					if (isDesc == true && result < 0)
					{
						objectValueTemp = objectValuej;
						pos = j;
					}
					else if (isDesc == false && result > 0)
					{
						objectValueTemp = objectValuej;
						pos = j;
					}
				}
				if (pos != -1)
				{
					objectTemp = objs[i];
					objs[i] = objs[pos];
					objs[pos] = objectTemp;
				}
			}
		}

		return objs;
	}

	private static int compareTo(Object obj1, Object obj2, int type,
			Method compareToMethod) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException
	{
		int result = 0;

		if (type == 1)
		{
			long longValue1 = ((Long) obj1).longValue();
			long longValue2 = ((Long) obj2).longValue();

			if (longValue1 > longValue2)
			{
				result = 1;
			}
			else if (longValue1 == longValue2)
			{
				result = 0;
			}
			else
			{
				result = -1;
			}
		}
		else if (type == 2)
		{
			double longValue1 = ((Double) obj1).doubleValue();
			double longValue2 = ((Double) obj2).doubleValue();

			if (longValue1 > longValue2)
			{
				result = 1;
			}
			else if (longValue1 == longValue2)
			{
				result = 0;
			}
			else
			{
				result = -1;
			}
		}
		else if (type == 4)
		{
			result = ((Integer) compareToMethod
					.invoke(obj1, new Object[]{obj2})).intValue();
		}

		return result;
	}

	/**
	 * 对字符串数组排序
	 * @param strArray
	 * @param isDesc
	 * @return
	 */
	public static String[] sortStringArray(String[] strArray, boolean isDesc)
	{
		if (strArray == null || strArray.length <= 0)
		{
			return strArray;
		}
		
		String strTemp = null;
		for(int i = 0; i < strArray.length - 1; i++)
		{
			if(strArray[i] == null)
			{
				continue;
			}
			strTemp = strArray[i];
			int pos = -1;
			for(int j = i + 1; j < strArray.length; j++)
			{
				if(strArray[j] == null)
				{
					pos = j;
					break;
				}
				if(strTemp.compareTo(strArray[j]) > 0)
				{
					strTemp = strArray[j];
					pos = j;
				}
			}
			if(pos != -1)
			{
				strTemp = strArray[i];
				strArray[i] = strArray[pos];
				strArray[pos] = strTemp;
			}
		}
		
		if(isDesc)
		{
			String[] arrTemp = new String[strArray.length];
			for(int i = 0; i < strArray.length; i++)
			{
				arrTemp[i] = strArray[strArray.length-1 - i];
			}
			strArray = arrTemp;
		}
		
		return strArray;
	}
	
	/**
	 * 对long型数组排序
	 * @param longValues
	 * @param isDesc
	 * @return
	 */
	public static long[] sortLongArray(long[] longValues, boolean isDesc)
	{
		if (longValues == null || longValues.length <= 0)
		{
			return longValues;
		}
		
		long longTemp = -1;
		for(int i = 0; i < longValues.length - 1; i++)
		{
			longTemp = longValues[i];
			int pos = -1;
			for(int j = i + 1; j < longValues.length; j++)
			{
				if(longTemp > longValues[j])
				{
					longTemp = longValues[j];
					pos = j;
				}
			}
			if(pos != -1)
			{
				longTemp = longValues[i];
				longValues[i] = longValues[pos];
				longValues[pos] = longTemp;
			}
		}
		
		if(isDesc)
		{
			long[] arrTemp = new long[longValues.length];
			for(int i = 0; i < longValues.length; i++)
			{
				arrTemp[i] = longValues[longValues.length-1 - i];
			}
			longValues = arrTemp;
		}
		
		return longValues;
	}
	
	/**
	 * 对int型数组排序
	 * @param intValues
	 * @param isDesc
	 * @return
	 */
	public static int[] sortIntArray(int[] intValues, boolean isDesc)
	{
		if (intValues == null || intValues.length <= 0)
		{
			return intValues;
		}
		
		int intTemp = -1;
		for(int i = 0; i < intValues.length - 1; i++)
		{
			intTemp = intValues[i];
			int pos = -1;
			for(int j = i + 1; j < intValues.length; j++)
			{
				if(intTemp > intValues[j])
				{
					intTemp = intValues[j];
					pos = j;
				}
			}
			if(pos != -1)
			{
				intTemp = intValues[i];
				intValues[i] = intValues[pos];
				intValues[pos] = intTemp;
			}
		}
		
		if(isDesc)
		{
			int[] arrTemp = new int[intValues.length];
			for(int i = 0; i < intValues.length; i++)
			{
				arrTemp[i] = intValues[intValues.length-1 - i];
			}
			intValues = arrTemp;
		}
		
		return intValues;
	}
	
	/**
	 * 对double型数组排序
	 * @param doubleValues
	 * @param isDesc
	 * @return
	 */
	public static double[] sortDoubleArray(double[] doubleValues, boolean isDesc)
	{
		if (doubleValues == null || doubleValues.length <= 0)
		{
			return doubleValues;
		}
		
		double doubleTemp = -1;
		for(int i = 0; i < doubleValues.length - 1; i++)
		{
			doubleTemp = doubleValues[i];
			int pos = -1;
			for(int j = i + 1; j < doubleValues.length; j++)
			{
				if(doubleTemp > doubleValues[j])
				{
					doubleTemp = doubleValues[j];
					pos = j;
				}
			}
			if(pos != -1)
			{
				doubleTemp = doubleValues[i];
				doubleValues[i] = doubleValues[pos];
				doubleValues[pos] = doubleTemp;
			}
		}
		
		if(isDesc)
		{
			double[] arrTemp = new double[doubleValues.length];
			for(int i = 0; i < doubleValues.length; i++)
			{
				arrTemp[i] = doubleValues[doubleValues.length-1 - i];
			}
			doubleValues = arrTemp;
		}
		
		return doubleValues;
	}
	
	public static void main(String[] args)
	{
		String[] s = new String[]{"b","a","",null,"1"};
		long[] l = new long[]{0,5,2,-1};
		double[] d = new double[]{0,5,2,-1};
		int[] i = new int[]{0,5,2,-1};
		
		s = sortStringArray(s, true);
		System.out.print("String: ");
		for(int j = 0; j < s.length; j++)
		{
			System.out.print(" ["+s[j]+"]");
		}
		System.out.println();
		
		l = sortLongArray(l, true);
		System.out.print("Long: ");
		for(int j = 0; j < l.length; j++)
		{
			System.out.print(" ["+l[j]+"]");
		}
		System.out.println();
		
		d = sortDoubleArray(d, true);
		System.out.print("Double: ");
		for(int j = 0; j < d.length; j++)
		{
			System.out.print(" ["+d[j]+"]");
		}
		System.out.println();
		
		i = sortIntArray(i, true);
		System.out.print("Int: ");
		for(int j = 0; j < i.length; j++)
		{
			System.out.print(" ["+i[j]+"]");
		}
		System.out.println();
	}
}
