/*
 * Created on 2005-5-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.fcinterface.bankportal.constant;

import com.iss.itreasury.fcinterface.bankportal.sysframe.constant.BaseConstantObject;

/**
 * 记录状态
 * @author mxzhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RecordStatus extends BaseConstantObject
{
    //表中记录的nStatus
    public static final long VALID 					= 1; //有效
    public static final long INVALID 				= 0; //无效(删除)
    
    private static final String NAME_VALID    		= "有效";
    private static final String NAME_INVALID      	= "无效";

    public static final String getName(long lCode)
    {
        String strReturn = ""; //初始化返回值
        switch ((int) lCode)
        {
            case (int) VALID:
                strReturn = NAME_VALID;
                break;
            case (int) INVALID:
                strReturn = NAME_INVALID;
                break;
        }
        return strReturn;
    }

    public static final long[] getAllCode()
    {
        long[] lTemp =
        { VALID, INVALID };
        return lTemp;
    }
}
