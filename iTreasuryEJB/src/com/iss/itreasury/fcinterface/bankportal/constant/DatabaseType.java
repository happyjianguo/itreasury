/*
 * Created on 2005-5-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.fcinterface.bankportal.constant;

import com.iss.itreasury.fcinterface.bankportal.sysframe.constant.BaseConstantObject;

/**
 * 数据库类型
 * @author mxzhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DatabaseType extends BaseConstantObject
{
    public static final long ORACLE 				= 1; //oracle
    public static final long MYSQL 					= 2; //mysql
    public static final long SYBASE 				= 3; //sybase
    public static final long DB2                    = 4; //DB2
    
    private static final String NAME_ORACLE    		= "oracle";
    private static final String NAME_MYSQL      	= "mysql";
    private static final String NAME_SYBASE      	= "sybase";
    private static final String NAME_DB2      	    = "db2";
    

    public static final String getName(long lCode)
    {
        String strReturn = ""; //初始化返回值
        switch ((int) lCode)
        {
            case (int) ORACLE:
                strReturn = NAME_ORACLE;
                break;
            case (int) MYSQL:
                strReturn = NAME_MYSQL;
                break;
            case (int) SYBASE:
                strReturn = NAME_SYBASE;
                break;
            case (int) DB2:
            	strReturn = NAME_DB2;
            	break;
         }
        return strReturn;
    }

    public static final long[] getAllCode()
    {
        long[] lTemp =
        { ORACLE, MYSQL, SYBASE ,DB2};
        return lTemp;
    }
}
