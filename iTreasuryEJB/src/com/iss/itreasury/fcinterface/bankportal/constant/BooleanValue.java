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
 * Boolean��������
 * @author mxzhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BooleanValue extends BaseConstantObject
{
	public static final long TRUE 					= 1; //��
    public static final long FALSE 					= 0; //��
    
    private static final String NAME_TRUE    		= "��";
    private static final String NAME_FALSE      	= "��";
    
    public static final String getName(long lCode)
    {
        String strReturn = ""; //��ʼ������ֵ
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
     * ��ʾ�����б�
     * 
     * @param out
     * @param strControlName, *
     *            �ؼ�����
     * @param nType���ؼ����ͣ�0����ʾȫ������
     * @param lSelectValue
     * @param isNeedAll���Ƿ���Ҫ��ȫ���
     * @param isNeedBlank
     *            �Ƿ���Ҫ�հ���
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
