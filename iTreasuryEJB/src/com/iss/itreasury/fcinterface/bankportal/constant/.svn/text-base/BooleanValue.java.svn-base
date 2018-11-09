/*
 * Created on 2005-5-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.fcinterface.bankportal.constant;

import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.fcinterface.bankportal.sysframe.constant.BaseConstantObject;
import com.iss.itreasury.fcinterface.bankportal.util.HTMLHelper;

/**
 * Boolean常量类型
 * @author mxzhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BooleanValue extends BaseConstantObject
{
	public static final long TRUE 					= 1; //是
    public static final long FALSE 					= 0; //否
    
    private static final String NAME_TRUE    		= "是";
    private static final String NAME_FALSE      	= "否";
    
    public static final String getName(long lCode)
    {
        String strReturn = ""; //初始化返回值
        switch ((int) lCode)
        {
            case (int) TRUE:
                strReturn = NAME_TRUE;
                break;
            case (int) FALSE:
                strReturn = NAME_FALSE;
                break;
        }
        return strReturn;
    }

    public static final long[] getAllCode()
    {
        long[] lTemp =
        { TRUE, FALSE };
        return lTemp;
    }
    
    /**
     * 显示下拉列表
     * 
     * @param out
     * @param strControlName, *
     *            控件名称
     * @param nType，控件类型（0，显示全部；）
     * @param lSelectValue
     * @param isNeedAll，是否需要”全部项“
     * @param isNeedBlank
     *            是否需要空白行
     * @param strProperty
     */
    public static final void showList(JspWriter out, String strControlName, long lSelectValue, boolean isNeedAll, boolean isNeedBlank,
            String strProperty)
    {
        long[] lArrayID = null;
        String[] strArrayName = null;
        try
        {
        	lArrayID = getAllCode();
            strArrayName = new String[lArrayID.length];
            for (int i = 0; i < lArrayID.length; i++)
            {
                strArrayName[i] = getName(lArrayID[i]);
            }
            HTMLHelper.showCommonSelectListControl(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
        }
        catch (Exception ex)
        {
            log.error(ex.toString());
        }
    }
}
