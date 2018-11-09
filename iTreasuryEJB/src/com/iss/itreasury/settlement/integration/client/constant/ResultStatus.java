package com.iss.itreasury.settlement.integration.client.constant;
public class ResultStatus
{
	public static final long SUCCESS 					= 1; //是
    public static final long FAIL 					= 0; //否
    
    private static final String NAME_SUCCESS    		= "成功";
    private static final String NAME_FAIL      	= "失败";
    
    public static final String getName(long lCode)
    {
        String strReturn = ""; //初始化返回值
        switch ((int) lCode)
        {
            case (int) SUCCESS:
                strReturn = NAME_SUCCESS;
                break;
            case (int) FAIL:
                strReturn = NAME_FAIL;
                break;
        }
        return strReturn;
    }

    public static final long[] getAllCode()
    {
        long[] lTemp =
        { SUCCESS, FAIL };
        return lTemp;
    }  
}
