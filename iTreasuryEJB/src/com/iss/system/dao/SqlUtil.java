package com.iss.system.dao;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Hashtable;
/**
 * <p>数据库相关的简单工具方法类。</p>
 * @author pliu
 */
public final class SqlUtil
{
    /**
     * 一个私有构造方法是为了防止用构造这个类的实例。
     * @see java.lang.Object#Object()
     */
    private SqlUtil()
    {
    }
    /**
     * <p>从Resultset中解析出所有的DATAENTITYBEAN。</p>
     * <p>对于进行能够用这个方法进行解析的Resultset要求：其中的列名必须与目标类中的属性名相同，并且大小 写也要一致。如果列名中有前缀，可以声
     * 名 一 个 前缀参数，该也可以自动去掉列名中的前缀。</p>
     * @param rsResult 一个准备好的结果集。
     * @param strPrefix 结果集中列名前缀，当结果集列名有前缀时，这个方法会自动将列名去掉缀后的部
     * 分做为DataEntityBean的属性名。
     * @param strResultType 返回结果的DataEntityBean类名，全名。
     * @return Object[] 由strResultType指定类对象的数组，可以在应用中直接CAST成相应数组使用。
     * @throws Exception 可能用SqlException、类不存在及数组越界等多种问题。
     */
    public static Object[] parseDataEntityBeans(ResultSet rsResult, String strPrefix, String strResultType)
        throws Exception
    {
        int nColumnCount;
        String strColumnNames[] = null;
        String strAttributeNames[] = null;
        int nTypes[] = null;
        java.util.List listResult = new java.util.ArrayList();
/*		Class classResult = Class.forName(strResultType);
		Object objResult = null;
		objResult = classResult.newInstance();
		java.util.Hashtable hs = new java.util.Hashtable();*/
        for(; rsResult.next(); listResult.add(ParseObjectFromResultSet(rsResult, strColumnNames, strAttributeNames, nTypes, strResultType)))
        {
            if(strColumnNames == null)
            {
                ResultSetMetaData rsMetaData = rsResult.getMetaData();
                nColumnCount = rsMetaData.getColumnCount();
                strColumnNames = new String[nColumnCount];
                strAttributeNames = new String[nColumnCount];
                nTypes = new int[nColumnCount];
                for(int i = 0; i < nColumnCount; i++)
                {
                    strAttributeNames[i] = strColumnNames[i] = rsMetaData.getColumnName(i + 1);
                    if(strPrefix.length() > 0)
                    {
                        strAttributeNames[i] = strColumnNames[i].substring(strPrefix.length());
                    }
                    nTypes[i] = rsMetaData.getColumnType(i + 1);
                }

            }
        }

        if (listResult.size() > 0)
        {
            Class clazz = Thread.currentThread().getContextClassLoader().loadClass(strResultType);
            Object objResutlArray = Array.newInstance(clazz, listResult.size());
            listResult.toArray((Object[])objResutlArray);
            return (Object[])objResutlArray;
        }
        return null;
    }
    /**
     * 从Resultset中解析出一个目标类对象。
     * @param rsResult
     * @param strColumnNames rsResult中的所有列名。
     * @param strAttributeNames 目标DataEntityBean中的与属性名，顺序要与strColumnNames中的列名一一对
     * 应。
     * @param nTypes rsResult中各列的数据类型，这个数据类常是由java.sql.Types标识的。
     * @param strResultType 目标DataEntityBean类名。
     * @return Object 返回一个目标类对象，可以直接CAST成目标类使用。
     * @throws Exception 主要可能是类型不存在，属性不存在极SQLException.
     */
    public static Object ParseObjectFromResultSet(ResultSet rsResult, String strColumnNames[], String strAttributeNames[], int nTypes[], String strResultType)
        throws Exception
    {
        Class classResult = Thread.currentThread().getContextClassLoader().loadClass(strResultType);
        Object objResult = classResult.newInstance();
        Hashtable hs = new Hashtable();
        for(int ii = 0; ii < objResult.getClass().getDeclaredMethods().length; ii++)
        {
            hs.put(objResult.getClass().getDeclaredMethods()[ii].getName().toUpperCase(), objResult.getClass().getDeclaredMethods()[ii].getName());
        }
        Object objVal = null;
        Method method = null;
        String strMethodName = null;
        for (int i = 0; i < strColumnNames.length; i++)
        {
            objVal = rsResult.getObject(strColumnNames[i]);
            strMethodName = "set" + strAttributeNames[i];
            if (hs.get("SET"+strAttributeNames[i].toUpperCase())!=null)
            {
				strMethodName = hs.get("SET"+strAttributeNames[i].toUpperCase()).toString();
            }
            if (objVal != null)
            {
                try
                {
                    if (nTypes[i] == java.sql.Types.DECIMAL)
                    {
                        try {
                            method = classResult.getMethod(strMethodName, new Class[] { Class.forName("java.math.BigDecimal")});
                        } catch (NoSuchMethodException e) {
                            try {
                                method = classResult.getMethod(strMethodName, new Class[] { Double.TYPE });
                               	objVal = new Double(((BigDecimal)objVal).doubleValue());
                            } catch (NoSuchMethodException exec) {
                                method = classResult.getMethod(strMethodName, new Class[] { Float.TYPE });
                               	objVal = new Float(((BigDecimal)objVal).floatValue());
                            }
                        }
                    }
                    else if (nTypes[i] == java.sql.Types.NUMERIC)
                    {
                        try {
                            method = classResult.getMethod(strMethodName, new Class[] { Long.TYPE });
                            objVal = new Long(objVal.toString());
                        } catch(NoSuchMethodException e) {
                            try {
                                method = classResult.getMethod(strMethodName, new Class[] { Double.TYPE });
                                objVal = new Double(((BigDecimal)objVal).doubleValue());
                            } catch(NoSuchMethodException exec) {
                                method = classResult.getMethod(strMethodName, new Class[] { Float.TYPE });
                                objVal = new Float(((BigDecimal)objVal).floatValue());
                            }
                        }
                    }
                    else if (nTypes[i] == java.sql.Types.BIT)
                    {
                        method = classResult.getMethod(strMethodName, new Class[] { Boolean.TYPE });
                    }
                    else if (nTypes[i] == java.sql.Types.INTEGER)
                    {
                        method = classResult.getMethod(strMethodName, new Class[] { Long.TYPE });
                    }
                    else if (nTypes[i] == java.sql.Types.DOUBLE)
                    {
                        method = classResult.getMethod(strMethodName, new Class[] { Double.TYPE });
                    }
                    else if (nTypes[i] == java.sql.Types.FLOAT)
                    {
                        method = classResult.getMethod(strMethodName, new Class[] { Float.TYPE });
                    }
                    else if (nTypes[i] == java.sql.Types.TIME || nTypes[i] == java.sql.Types.TIMESTAMP)
                    {
                    	objVal = rsResult.getTimestamp(strColumnNames[i]);
                        method = classResult.getMethod(strMethodName, new Class[] { Class.forName("java.sql.Timestamp")});
                    }
                    else if (nTypes[i] == java.sql.Types.DATE)
                    {
                    	try {
                    		objVal = rsResult.getTimestamp(strColumnNames[i]);
                            method = classResult.getMethod(strMethodName, new Class[] { Class.forName("java.sql.Timestamp")});               		
                    	} catch (NoSuchMethodException e) {
                    		objVal = rsResult.getTimestamp(strColumnNames[i]);
                            method = classResult.getMethod(strMethodName, new Class[] { Class.forName("java.util.Date")});               		
                    	}
                    }
                    else if (
                        nTypes[i] == java.sql.Types.VARCHAR
                            || nTypes[i] == java.sql.Types.CHAR
                            || nTypes[i] == java.sql.Types.LONGVARCHAR)
                    {
                        method = classResult.getMethod(strMethodName, new Class[] { Class.forName("java.lang.String")});
                    }
                    method.invoke(objResult, new Object[] { objVal });
                }
                catch (NoSuchMethodException e)
                {
                    /*System.out.println(
                        java.text.MessageFormat.format(
                            "Can not find method set{1}(...) in class {0},参数类型为{2}",
                            new Object[] { strResultType, strAttributeNames[i] ,Long.valueOf(""+nTypes[i])}));
                    */
                }
            }
        }
        return objResult;
    }
}
