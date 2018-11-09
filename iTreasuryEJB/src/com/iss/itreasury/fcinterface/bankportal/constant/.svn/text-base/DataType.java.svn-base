/*
 * Created on 2005-5-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.fcinterface.bankportal.constant;

import com.iss.itreasury.fcinterface.bankportal.sysframe.constant.BaseConstantObject;

/**
 * 数据类型
 * @author mxzhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DataType extends BaseConstantObject
{
    public static final long STRING 				= 1; //java.lang.String
    public static final long LONG 					= 2; //long
    public static final long DOUBLE 				= 3; //double
    public static final long DATE 				= 4; //java.util.Date
    
    private static final String NAME_STRING    		= "java.lang.String";
    private static final String NAME_LONG      		= "long";
    private static final String NAME_DOUBLE    		= "double";
    private static final String NAME_DATE 		= "java.util.Date";
    
    private static final String PREFIX_STRING 		= "s_";
    private static final String PREFIX_LONG 		= "n_";
    private static final String PREFIX_DOUBLE 		= "n_";
    private static final String PREFIX_DATE 	= "dt_";
    
    public static final String getName(long lCode)
    {
        String strReturn = ""; //初始化返回值
        switch ((int) lCode)
        {
            case (int) STRING:
                strReturn = NAME_STRING;
                break;
            case (int) LONG:
                strReturn = NAME_LONG;
                break;
            case (int) DOUBLE:
                strReturn = NAME_DOUBLE;
                break;
            case (int) DATE:
                strReturn = NAME_DATE;
                break;
        }
        return strReturn;
    }

    public static final long[] getAllCode()
    {
        long[] lTemp =
        { STRING, LONG, DOUBLE, DATE };
        return lTemp;
    }
    
	/**
	 * 根据数据类型获取对应的前缀(如果有前缀)
	 */
    public static final String getPrefix(long lCode)
    {
        String strReturn = ""; //初始化返回值
        switch ((int) lCode)
        {
            case (int) STRING:
                strReturn = PREFIX_STRING;
                break;
            case (int) LONG:
                strReturn = PREFIX_LONG;
                break;
            case (int) DOUBLE:
                strReturn = PREFIX_DOUBLE;
                break;
            case (int) DATE:
                strReturn = PREFIX_DATE;
                break;
        }
        return strReturn;
    }
	public static String getPrefix(String dataType)
	{
		String strReturn = ""; //初始化返回值
		if(dataType.equalsIgnoreCase(getName(STRING)))
			strReturn = PREFIX_STRING;
		else if(dataType.equalsIgnoreCase(getName(LONG)))
			strReturn = PREFIX_LONG;
		else if(dataType.equalsIgnoreCase(getName(DOUBLE)))
			strReturn = PREFIX_DOUBLE;
		else if(dataType.equalsIgnoreCase(getName(DATE)))
			strReturn = PREFIX_DATE;
		return strReturn;
	}
}
