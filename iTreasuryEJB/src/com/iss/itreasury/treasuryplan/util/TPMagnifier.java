/*
 * CLASS   : Magnifier
 * FUNCTION: 放大镜类
 * VERSION : 1.0.0
 * DATE    : 2003/08/08
 * AUTHOR  : Forest Ming
 * HISTORY :
 *
 */
package com.iss.itreasury.treasuryplan.util;

import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.dataentity.CommonSelectInfo;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.treasuryplan.query.queryobj.QData;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;

public class TPMagnifier
{
	private static String ZOOMERRORMSG = "放大镜参数设置错误!";

	/**
	 * 创建资金计划版本放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lDepartID 部门ID(初识值)
	 * @param lTreaPlanID 资金计划版本ID(初识值)
	 * @param strTreaPlanNo 资金计划版本编号(初识值)	 
	 * @param strTreaPlanCtrl资金计划版本ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnStartDateCtrl 返回值（开始日期）对应的控件名
	 * @param strRtnEndDateCtrl 返回值（结束日期）对应的控件名	 
	 */
	public static void createTreaPlanCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lDepartID,
		long lDepartIDQx,
		long lTreaPlanID,
		String strTreaPlanNo,		
		String strTreaPlanCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnStartDateCtrl,
		String strRtnEndDateCtrl)
	{

		String strMagnifierName = URLEncoder.encode("资金计划");
		String strMainProperty = " maxlength='30' value='" + strTreaPlanNo + "'";
		String strPrefix = "";

		if (strRtnStartDateCtrl == null || strRtnStartDateCtrl.equals(""))
		{
			strRtnStartDateCtrl = "ItIsNotControl";
		}
		if (strRtnEndDateCtrl == null || strRtnEndDateCtrl.equals(""))
		{
			strRtnEndDateCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnStartDateCtrl, strRtnEndDateCtrl};
		String[] strMainFields = { "treaPlanNo", "startDate","endDate" };


		String[] strReturnNames = { strCtrlName};
		String[] strReturnFields = { "treaPlanID"};
		String[] strReturnValues = { String.valueOf(lTreaPlanID)};

		String[] strDisplayNames = { URLEncoder.encode("资金计划版本编号"),URLEncoder.encode("资金计划版本状态")};
		String[] strDisplayFields = { "treaPlanNO","statusdesc" };
		int nIndex = 0;
		
		String sStatus  = "'" + TPConstant.PlanVersionStatus.REFUSE + "," + TPConstant.PlanVersionStatus.CANCEL + "," + TPConstant.PlanVersionStatus.CHECK + "," +  TPConstant.PlanVersionStatus.SUBMIT + "," +TPConstant.PlanVersionStatus.SAVE +"'";
		
		QData qdata = new QData();
		try {
			if(lDepartID==qdata.getDepartmentID())
			{
				lDepartID=-1;
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String strSQL = "getTreaPlanSQL(" + lOfficeID + "," + lCurrencyID + "," + lDepartID + ","  + lDepartIDQx + "," + sStatus + "," + strCtrlName + "Ctrl.value )";
		
		try
		{
			TPMagnifier.showZoomCtrl(
				out,
				strMagnifierName,
				strFormName,
				strPrefix,
				strMainNames,
				strMainFields,
				strReturnNames,
				strReturnFields,
				strReturnValues,
				strDisplayNames,
				strDisplayFields,
				nIndex,
				strMainProperty,
				strSQL,
				strNextControls,
				strTitle,
				strFirstTD,
				strSecondTD);
		}
		catch (Exception e)
		{
			Log.print("资金计划放大镜[" + strCtrlName + "]异常：" + e.toString());
		}		
	}
	
	/**
	 * 创建资金计划版本放大镜(差异比较使用)
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lDepartID 部门ID(初识值)
	 * @param lTreaPlanID 资金计划版本ID(初识值)
	 * @param strTreaPlanNo 资金计划版本编号(初识值)	 
	 * @param strTreaPlanCtrl资金计划版本ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnStartDateCtrl 返回值（开始日期）对应的控件名
	 * @param strRtnEndDateCtrl 返回值（结束日期）对应的控件名	 
	 */
	public static void createTreaPlanCtrl1(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lDepartID,
		long lDepartIDQx,
		long lTreaPlanID,
		String strTreaPlanNo,		
		String strTreaPlanCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnStartDateCtrl,
		String strRtnEndDateCtrl)
	{

		String strMagnifierName = URLEncoder.encode("资金计划");
		String strMainProperty = " maxlength='30' value='" + strTreaPlanNo + "'";
		String strPrefix = "";

		if (strRtnStartDateCtrl == null || strRtnStartDateCtrl.equals(""))
		{
			strRtnStartDateCtrl = "ItIsNotControl";
		}
		if (strRtnEndDateCtrl == null || strRtnEndDateCtrl.equals(""))
		{
			strRtnEndDateCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnStartDateCtrl, strRtnEndDateCtrl};
		String[] strMainFields = { "treaPlanNo", "startDate","endDate" };


		String[] strReturnNames = { strCtrlName};
		String[] strReturnFields = { "treaPlanID"};
		String[] strReturnValues = { String.valueOf(lTreaPlanID)};

		String[] strDisplayNames = { URLEncoder.encode("资金计划版本编号"),URLEncoder.encode("资金计划版本状态")};
		String[] strDisplayFields = { "treaPlanNO","statusdesc" };
		int nIndex = 0;
		
		String sStatus  = "'" + TPConstant.PlanVersionStatus.REFUSE + "," + TPConstant.PlanVersionStatus.CANCEL + "," + TPConstant.PlanVersionStatus.CHECK + "," +  TPConstant.PlanVersionStatus.SUBMIT + "," +TPConstant.PlanVersionStatus.SAVE +"'";
		
		String strSQL = "getTreaPlanSQL(" + lOfficeID + "," + lCurrencyID + "," + lDepartID + ","  + lDepartIDQx + "," + sStatus + "," + strCtrlName + "Ctrl.value )";
		
		try
		{
			TPMagnifier.showZoomCtrl(
				out,
				strMagnifierName,
				strFormName,
				strPrefix,
				strMainNames,
				strMainFields,
				strReturnNames,
				strReturnFields,
				strReturnValues,
				strDisplayNames,
				strDisplayFields,
				nIndex,
				strMainProperty,
				strSQL,
				strNextControls,
				strTitle,
				strFirstTD,
				strSecondTD);
		}
		catch (Exception e)
		{
			Log.print("资金计划放大镜[" + strCtrlName + "]异常：" + e.toString());
		}		
	}
    
    
    
    
    
    
    
    
    
    /**
     * 创建 上级行项目放大镜
     * @param out
     * @param lOfficeID       办事处ID
     * @param lCurrencyID     币种ID
     * @param strFormName     表单域名称
     * @param strCtrlName     放大镜主控件名称
     * @param strTitle        放大镜描述
     * @param lSonLineId      子行项目Id(初始值) ----- 查询的约束条件
     * @param lParentLineId   上级行项目Id(初始值)
     * @param strParentLineNo 上级行项目编号(初始值)
     * @param lLineTypeId     行项目类型  ----- 查询的约束条件(暂时不用)
     *                             0：一般项目；    1：其他行项目；
                                   2：期初可用余额； 3：期末可用余额
     * @param lStatusID       行项目单状态(内部状态  0，显示全部；）(暂时不用)
     * @param strSonLineCtrl  子行项目编号对应的控件名称----- 查询的约束条件
     * @param strFirstTD      第一个TD的属性
     * @param strSecondTD     第二个TD的属性
     * @param strNextControls 下一个（或多个）获得焦点的控件
     */
    public static void createParentLineCtrl(
        JspWriter out,
        long lOfficeID,
        long lCurrencyID,
        String strFormName,
        String strCtrlName,
        String strTitle,
        long lSonLineId,
        long lParentLineId,
        String strParentLineNo,
        String strSonLineCtrl,
        String strFirstTD,
        String strSecondTD,
        String[] strNextControls)
    {
       
        System.out.println("创建上级行项目放大镜.............................................");
        
        String strMagnifierName = URLEncoder.encode("上级行项目");
        String strMainProperty = " maxlength='30' value='" + strParentLineNo + "'";
        String strPrefix = "";
        String strRtnParentLineCtrl = "";
        if (strRtnParentLineCtrl == null || strRtnParentLineCtrl.equals(""))
        {
            strRtnParentLineCtrl = "NCTL";
        }
        
        String[] strMainNames = { strCtrlName + "Ctrl", strRtnParentLineCtrl};
        String[] strMainFields = { "ParentLineNo", "ParentLineName"};/////////交换了位置
        String[] strReturnNames = { strCtrlName };
        String[] strReturnFields = { "ParentLineId" };
        String[] strReturnValues = {  String.valueOf(lParentLineId)};
        String[] strDisplayNames = { URLEncoder.encode("上级行项目编号"), URLEncoder.encode("上级行项目名称")};
        String[] strDisplayFields = { "ParentLineNo", "ParentLineName" };
        int nIndex = 0;
        
        
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("getParentLineSQL(");
        sbSQL.append(lOfficeID);
        sbSQL.append(",");
        sbSQL.append(lCurrencyID);
        sbSQL.append(",");
        
        if (strSonLineCtrl != null && !strSonLineCtrl.trim().equals(""))
        {
            sbSQL.append(strSonLineCtrl + ".value");
        }
        else
        {
            ///?????????????????????
            sbSQL.append("-1");
        }
       
        sbSQL.append(",");
        sbSQL.append(strCtrlName);
        sbSQL.append("Ctrl.value)");
        //Log.print(sbSQL.toString());
        try
        {
            TPMagnifier.showZoomCtrl(
                out,
                strMagnifierName,
                strFormName,
                strPrefix,
                strMainNames,
                strMainFields,
                strReturnNames,
                strReturnFields,
                strReturnValues,
                strDisplayNames,
                strDisplayFields,
                nIndex,
                strMainProperty,
                sbSQL.toString(),
                strNextControls,
                strTitle,
                strFirstTD,
                strSecondTD);
        }
        catch (Exception e)
        {
            //e.printStackTrace();
            Log.print("放大镜[" + strCtrlName + "]异常：" + e.toString());
        }
    
    }
    
    /**
     * 创建客户放大镜
     * @param out
     * @param lOfficeID 办事处ID
     * @param lCurrencyID 币种ID
     * @param strFormName 表单域名称
     * @param strCtrlName 放大镜主控件名称
     * @param strTitle 放大镜描述
     * @param lClientID 客户ID(初识值)
     * @param strClientNo 客户编号(初识值)
     * @param strFirstTD 第一个TD的属性
     * @param strSecondTD 第二个TD的属性
     * @param sNextControlsClient 下一个（或多个）获得焦点的控件
     * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
     */
    public static void createClientCtrl(
        JspWriter out,
        long lOfficeID,
        long lCurrencyID,
        String strFormName,
        String strCtrlName,
        String strTitle,
        long lClientID,
        String strClientNo,
        String strFirstTD,
        String strSecondTD,
        String[] strNextControls,
        String strRtnClientNameCtrl)
    {
        String strMagnifierName = URLEncoder.encode("客户");
        String strMainProperty = " maxlength='30' value='" + strClientNo + "'";
        String strPrefix = "";
        if (strRtnClientNameCtrl == null || strRtnClientNameCtrl.equals(""))
        {
            strRtnClientNameCtrl = "ItIsNotControl";
        }
        String[] strMainNames = { strCtrlName + "Ctrl", strRtnClientNameCtrl };
        String[] strMainFields = { "clientNo", "clientName" };
        //特殊处理
        long lFromClient = 0;
        if (lClientID > 0)
        {
            lFromClient = 1;
        }
        String[] strReturnNames = { strCtrlName, strCtrlName + "FromClient" };
        String[] strReturnFields = { "ClientID", "FromClient" };
        String[] strReturnValues = { String.valueOf(lClientID), String.valueOf(lFromClient)};
        String[] strDisplayNames = { URLEncoder.encode("客户编号"), URLEncoder.encode("客户名称")};
        String[] strDisplayFields = { "clientNo", "clientName" };
        int nIndex = 0;
        String strSQL = "getClientSQL(" + lOfficeID + "," + strCtrlName + "Ctrl.value)";
        try
        {
            TPMagnifier.showZoomCtrl(
                out,
                strMagnifierName,
                strFormName,
                strPrefix,
                strMainNames,
                strMainFields,
                strReturnNames,
                strReturnFields,
                strReturnValues,
                strDisplayNames,
                strDisplayFields,
                nIndex,
                strMainProperty,
                strSQL,
                strNextControls,
                strTitle,
                strFirstTD,
                strSecondTD);
        }
        catch (Exception e)
        {
            Log.print("客户放大镜[" + strCtrlName + "]异常：" + e.toString());
        }
    }
    
    /**
     * 创建总账科目放大镜
     * @param out
     * @param lOfficeID 办事处ID
     * @param lCurrencyID 币种ID
     * @param strFormName 表单域名称
     * @param strCtrlName 放大镜主控件名称
     * @param strTitle 放大镜描述
     * @param lGLSubjectID 总账科目ID(初识值)
     * @param strGLSubjectNo 总账科目编号(初识值)
     * @param lIsleaf 是否末级
     * @param strFirstTD 第一个TD的属性
     * @param strSecondTD 第二个TD的属性
     * @param sNextControlsClient 下一个（或多个）获得焦点的控件
     * @param strRtnGLSubjectTypeCtrl 返回值（总账科目类型）对应的控件名
     */
    public static void createGLSubjectCtrl(
        JspWriter out,
        long lOfficeID,
        long lCurrencyID,
        String strFormName,
        String strCtrlName,
        String strTitle,
        long lGLSubjectID,
        String strGLSubjectNo,
        long lIsleaf,
        String strFirstTD,
        String strSecondTD,
        String[] strNextControls,
        String strRtnGLSubjectTypeCtrl)
    {
        String strMagnifierName = URLEncoder.encode("总账科目");
        String strMainProperty = " maxlength='30' value='" + strGLSubjectNo + "'";
        String strPrefix = "";
        
        
        if (strRtnGLSubjectTypeCtrl == null || strRtnGLSubjectTypeCtrl.equals("")) {
            strRtnGLSubjectTypeCtrl="ItIsNotControl";
        }
                
        String[] strMainNames = { strCtrlName + "Ctrl", strRtnGLSubjectTypeCtrl };
        String[] strMainFields = { "GLSubjectCode", "GLSubjectType" }; 
        
        String[] strReturnNames = { strCtrlName };
        String[] strReturnFields = { "GLSubjectID" };
        String[] strReturnValues = { String.valueOf(lGLSubjectID)};
        
        String[] strDisplayNames = { URLEncoder.encode("科目代号"), URLEncoder.encode("科目名称")};
        String[] strDisplayFields = { "GLSubjectCode", "GLSubjectName" };
        int nIndex = 0;
        long nSubjectType = -1;
        String strSQL = "getGLSubjectSQL(" + lOfficeID + "," + lCurrencyID + "," + strCtrlName + "Ctrl.value," + lIsleaf + ","+nSubjectType+")";
        try {
            TPMagnifier.showZoomCtrl(
                out,
                strMagnifierName,
                strFormName,
                strPrefix,
                strMainNames,
                strMainFields,
                strReturnNames,
                strReturnFields,
                strReturnValues,
                strDisplayNames,
                strDisplayFields,
                nIndex,
                strMainProperty,
                strSQL,
                strNextControls,
                strTitle,
                strFirstTD,
                strSecondTD);
        } catch(Exception e) {
            Log.print("总账科目放大镜[" + strCtrlName + "]异常：" + e.toString());
        }
    }
    
    
    
    /**
     * 创建账户放大镜
     * @param out
     * @param lOfficeID 办事处ID
     * @param lCurrencyID 币种ID
     * @param strFormName 表单域名称
     * @param strCtrlName 放大镜主控件名称
     * @param strTitle 放大镜描述
     * @param lClientID 客户ID(初识值)
     * @param lAccountID 账户ID(初识值)
     * @param strAccountNo 账户编号(初识值)
     * @param lAccountGroupTypeID 账户组ID
     * @param lAccountTypeID 账户类型ID
     * （正常情况下，请直接用账户类型常量。
     *  特殊的，利息费用支付，请传入-100（只显示两种信托和委托两种）；）
     * @param lReceiveOrPay 收付类型（-1000特殊的，现实所有状态的账户）
     * @param strClientCtrl 客户ID对应的控件名称（查询时关联）
     * @param strFirstTD 第一个TD的属性
     * @param strSecondTD 第二个TD的属性
     * @param strNextControls 下一个（或多个）获得焦点的控件
     * @param strRtnClientIDCtrl 返回值（客户ID）对应的控件名
     * @param strRtnClientNoCtrl 返回值（客户编号）对应的控件名
     * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
     */
    public static void createAccountCtrl(
        JspWriter out,
        long lOfficeID,
        long lCurrencyID,
        String strFormName,
        String strCtrlName,
        String strTitle,
        long lClientID,
        long lAccountID,
        String strAccountNo,
        long lAccountGroupTypeID,
        long lAccountTypeID,
        long lReceiveOrPay,
        String strClientCtrl,
        String strFirstTD,
        String strSecondTD,
        String[] strNextControls,
        String strRtnClientIDCtrl,
        String strRtnClientNoCtrl,
        String strRtnClientNameCtrl)
    {
        String strMagnifierName = URLEncoder.encode("账户");
        String strPrefix = "";
        String strMainProperty = "";
        int nCaseNumber = 4;
        if (lAccountGroupTypeID == SETTConstant.AccountGroupType.CURRENT
                ||lAccountGroupTypeID == SETTConstant.AccountGroupType.OTHERDEPOSIT)
        {
            //活期账户组，显示4个文本框
            nCaseNumber = 4;
        }
        else if (lAccountGroupTypeID == SETTConstant.AccountGroupType.FIXED
                ||lAccountGroupTypeID == SETTConstant.AccountGroupType.NOTIFY)
        {
            //定期账户组，显示3个文本框
            nCaseNumber = 4;
        }
        else if (lAccountGroupTypeID == SETTConstant.AccountGroupType.TRUST
                ||lAccountGroupTypeID == SETTConstant.AccountGroupType.CONSIGN
                ||lAccountGroupTypeID == SETTConstant.AccountGroupType.DISCOUNT
                ||lAccountGroupTypeID == SETTConstant.AccountGroupType.OTHERLOAN)
        {
            //贷款账户组，显示3个文本框
            nCaseNumber = 4;
        }
        if (strRtnClientIDCtrl == null || strRtnClientIDCtrl.equals(""))
        {
            strRtnClientIDCtrl = "ItIsNotControl";
        }
        if (strRtnClientNoCtrl == null || strRtnClientNoCtrl.equals(""))
        {
            strRtnClientNoCtrl = "ItIsNotControl";
        }
        if (strRtnClientNameCtrl == null || strRtnClientNameCtrl.equals(""))
        {
            strRtnClientNameCtrl = "ItIsNotControl";
        }
        String[] strMainNames = { strCtrlName + "Ctrl", strRtnClientIDCtrl, strRtnClientNoCtrl, strRtnClientNameCtrl };
        String[] strMainFields = { "AccountNo", "ClientID", "ClientNo", "ClientName" };
        String[] strReturnNames = { strCtrlName, strCtrlName + "ClientID" };
        String[] strReturnFields = { "AccountID", "ClientID" };
        String[] strReturnValues = { String.valueOf(lAccountID), String.valueOf(lClientID)};
        Log.print("SETTConstant.ReceiveOrPay.PAY ==========" + SETTConstant.ReceiveOrPay.PAY);
        String[] strDisplayNames = { URLEncoder.encode("账户编号"), URLEncoder.encode("账户名称")};
        String[] strDisplayFields = { "AccountNo", "AccountName" };
        int nIndexAccount = 0;
        String strSQL = "";
        if (strClientCtrl != null && !strClientCtrl.trim().equals(""))
        {
            strSQL =
                "getAccountSQL("
                    + lOfficeID
                    + ","
                    + lCurrencyID
                    + ","
                    + lAccountGroupTypeID
                    + ","
                    + lAccountTypeID
                    + ","
                    + lReceiveOrPay
                    + ","
                    + strFormName
                    + "."
                    + strClientCtrl
                    + ".value"
                    + ","
                    + strFormName
                    + "."
                    + strCtrlName
                    + "Ctrl.value)";
        }
        else
        {
            strSQL =
                "getAccountSQL("
                    + lOfficeID
                    + ","
                    + lCurrencyID
                    + ","
                    + lAccountGroupTypeID
                    + ","
                    + lAccountTypeID
                    + ","
                    + lReceiveOrPay
                    + ",-1,"
                    + strFormName
                    + "."
                    + strCtrlName
                    + "Ctrl.value)";
        }
        //初始默认值
        if (strAccountNo == null || strAccountNo.equals(""))
        {
            if (lOfficeID > 0)
            {
                strAccountNo = NameRef.getOfficeNoByID(lOfficeID);
            }
            if (lAccountTypeID > 0)
            {
                strAccountNo = strAccountNo + "-" + DataFormat.formatInt(lAccountTypeID, 2);
            }
            //如果strAccountNo无效（应为“01-01”格式），就置为空    
            //          if (strAccountNo != null && strAccountNo.length() < 5)
            //          {
            //              strAccountNo = "";
            //          }
            //          else
            //          {
            //              strAccountNo = strAccountNo + "--";
            //          }
        }
        try
        {
            TPMagnifier.showSpecialZoomCtrl(
                out,
                strMagnifierName,
                strFormName,
                strPrefix,
                nCaseNumber,
                strMainNames,
                strMainFields,
                strReturnNames,
                strReturnFields,
                strReturnValues,
                strDisplayNames,
                strDisplayFields,
                nIndexAccount,
                strMainProperty,
                strSQL,
                strNextControls,
                strAccountNo,
                strTitle,
                strFirstTD,
                strSecondTD,
                strClientCtrl);
        }
        catch (Exception e)
        {
            Log.print("账户放大镜[" + strCtrlName + "]异常：" + e.toString());
        }
    }
    
    
    
    
    /**
     * 创建证券交易对象放大镜
     * @param out
     * @param lOfficeID 办事处ID
     * @param lCurrencyID 币种ID
     * @param strFormName 表单域名称
     * @param strCtrlName 放大镜主控件名称
     * @param strTitle 放大镜描述
     * @param lCounterPartID 交易对象ID(初始值)
     * @param strCounterPartNo 交易对象编号(初始值)
     * @param strFirstTD 第一个TD的属性
     * @param strSecondTD 第二个TD的属性
     * @param strNextControls 下一个（或多个）获得焦点的控件  
     */
    public static void createCounterPartCtrl(
        JspWriter out,
        long lOfficeID,
        long lCurrencyID,
        String strFormName,
        String strCtrlName,
        String strTitle,
        long lCounterPartID,
        String strCounterPartNo,
        String strFirstTD,
        String strSecondTD,
        String[] strNextControls,
        String strRtnCounterPartNameCtrl)
    {

        
        if (strRtnCounterPartNameCtrl == null || strRtnCounterPartNameCtrl.equals(""))
        {
            strRtnCounterPartNameCtrl = "ItIsNotControl";
        }
        
        
        String strMagnifierName = URLEncoder.encode("交易对象");
        String strMainProperty = " maxlength='30' value='" + strCounterPartNo + "'";
        String strPrefix = "";

        
        String[] strMainNames = { strCtrlName + "Ctrl",strRtnCounterPartNameCtrl};
        String[] strMainFields = { "counterPartNo" ,"counterPartName"};


        String[] strReturnNames = { strCtrlName};
        String[] strReturnFields = { "counterPartID"};
        String[] strReturnValues = { String.valueOf(lCounterPartID)};

        String[] strDisplayNames = { URLEncoder.encode("交易对象编号"),URLEncoder.encode("交易对象名称")};
        String[] strDisplayFields = { "counterPartNo","counterPartName" };
        int nIndex = 0;
        
        String strSQL = "getCounterPartSQL(" + lOfficeID + "," + lCurrencyID + "," + lCounterPartID + "," +  strCtrlName + "Ctrl.value )";
        
        try
        {
            TPMagnifier.showZoomCtrl(
                out,
                strMagnifierName,
                strFormName,
                strPrefix,
                strMainNames,
                strMainFields,
                strReturnNames,
                strReturnFields,
                strReturnValues,
                strDisplayNames,
                strDisplayFields,
                nIndex,
                strMainProperty,
                strSQL,
                strNextControls,
                strTitle,
                strFirstTD,
                strSecondTD);
        }
        catch (Exception e)
        {
            Log.print("资金计划放大镜[" + strCtrlName + "]异常：" + e.toString());
        }       
    }
    
    /**
     * 创建证券交易类型放大镜
     * @param out
     * @param lOfficeID 办事处ID
     * @param lCurrencyID 币种ID
     * @param strFormName 表单域名称
     * @param strCtrlName 放大镜主控件名称
     * @param strTitle 放大镜描述
     * @param lTransactionTypeID 业务类型ID(初始值)
     * @param strTransactionTypeNo 业务类型编号(初始值)
     * @param strFirstTD 第一个TD的属性
     * @param strSecondTD 第二个TD的属性
     * @param strNextControls 下一个（或多个）获得焦点的控件  
     */
    public static void createSECTransactionTypeCtrl(
        JspWriter out,
        long lOfficeID,
        long lCurrencyID,
        String strFormName,
        String strCtrlName,
        String strTitle,
        long lTransactionTypeID,
        String strTransactionTypeNo,
        String strFirstTD,
        String strSecondTD,
        String[] strNextControls,
        String strRtnTransactionTypeCtrl)
    {

        
        if (strRtnTransactionTypeCtrl == null || strRtnTransactionTypeCtrl.equals(""))
        {
            strRtnTransactionTypeCtrl = "ItIsNotControl";
        }
        
        
        String strMagnifierName = URLEncoder.encode("证券交易类型");
        String strMainProperty = " maxlength='30' value='" + strTransactionTypeNo + "'";
        String strPrefix = "";

        
        String[] strMainNames = { strCtrlName + "Ctrl",strRtnTransactionTypeCtrl};
        String[] strMainFields = { "secBusinessTypeId" ,"secBusinessTypeName"};


        String[] strReturnNames = { strCtrlName};
        String[] strReturnFields = { "secBusinessTypeId"};
        String[] strReturnValues = { String.valueOf(lTransactionTypeID)};

        String[] strDisplayNames = { URLEncoder.encode("交易类型"),URLEncoder.encode("交易类型名称")};
        String[] strDisplayFields = { "secBusinessTypeId","secBusinessTypeName" };
        int nIndex = 0;
        
        String strSQL = "getSECTransactionTypeSQL(" + lOfficeID + "," + lCurrencyID + "," + lTransactionTypeID + "," +  strCtrlName + "Ctrl.value )";
        
        try
        {
            TPMagnifier.showZoomCtrl(
                out,
                strMagnifierName,
                strFormName,
                strPrefix,
                strMainNames,
                strMainFields,
                strReturnNames,
                strReturnFields,
                strReturnValues,
                strDisplayNames,
                strDisplayFields,
                nIndex,
                strMainProperty,
                strSQL,
                strNextControls,
                strTitle,
                strFirstTD,
                strSecondTD);
        }
        catch (Exception e)
        {
            Log.print("资金计划放大镜[" + strCtrlName + "]异常：" + e.toString());
        }       
    }
    
    /**
     * 创建结算账户类型放大镜
     * @param out
     * @param lOfficeID 办事处ID
     * @param lCurrencyID 币种ID
     * @param strFormName 表单域名称
     * @param strCtrlName 放大镜主控件名称
     * @param strTitle 放大镜描述
     * @param lAccountTypeID 账户类型ID(初始值)
     * @param strAccountTypeNo 账户类型编号(初始值)
     * @param strFirstTD 第一个TD的属性
     * @param strSecondTD 第二个TD的属性
     * @param strNextControls 下一个（或多个）获得焦点的控件  
     */
    public static void createSETTAccountTypeCtrl(
        JspWriter out,
        long lOfficeID,
        long lCurrencyID,
        String strFormName,
        String strCtrlName,
        String strTitle,
        long lAccountTypeID,
        String strAccountTypeNo,
        String strFirstTD,
        String strSecondTD,
        String[] strNextControls,
        String strRtnTransactionTypeCtrl)
    {

        
        if (strRtnTransactionTypeCtrl == null || strRtnTransactionTypeCtrl.equals(""))
        {
            strRtnTransactionTypeCtrl = "ItIsNotControl";
        }
        
        
        String strMagnifierName = URLEncoder.encode("账户类型");
        String strMainProperty = " maxlength='30' value='" + strAccountTypeNo + "'";
        String strPrefix = "";

        
        String[] strMainNames = { strCtrlName + "Ctrl",strRtnTransactionTypeCtrl};
        String[] strMainFields = { "accountTypeId" ,"accountTypeName"};


        String[] strReturnNames = { strCtrlName};
        String[] strReturnFields = { "accountTypeId"};
        String[] strReturnValues = { String.valueOf(lAccountTypeID)};

        String[] strDisplayNames = { URLEncoder.encode("账户类型"),URLEncoder.encode("账户类型名称")};
        String[] strDisplayFields = { "accountTypeId","accountTypeName" };
        int nIndex = 0;
        
        String strSQL = "getSETTAccountTypeSQL(" + lOfficeID + "," + lCurrencyID + "," + lAccountTypeID + "," +  strCtrlName + "Ctrl.value )";
        
        try
        {
            TPMagnifier.showZoomCtrl(
                out,
                strMagnifierName,
                strFormName,
                strPrefix,
                strMainNames,
                strMainFields,
                strReturnNames,
                strReturnFields,
                strReturnValues,
                strDisplayNames,
                strDisplayFields,
                nIndex,
                strMainProperty,
                strSQL,
                strNextControls,
                strTitle,
                strFirstTD,
                strSecondTD);
        }
        catch (Exception e)
        {
            Log.print("资金计划放大镜[" + strCtrlName + "]异常：" + e.toString());
        }       
    }
    
    
    
    
    
    
	
	
	/**
	 * 显示普通放大镜
	 * @param out
	 * @param strMagnifierName 放大镜的名称
	 * @param strFormName 主页面表单名称
	 * @param strPrefix 控件名称前缀
	 * @param strMainNames 放大镜回显栏位值列表
	 * @param strMainFields 放大镜回显栏位对应的表格字段
	 * @param strReturnNames 放大镜返回值列表(隐含值)
	 * @param strReturnFields 放大镜返回值(隐含值)对应的表格字段列表
	 * @param strReturnValues 放大镜返回值(隐含值)对应的初始值
	 * @param strDisplayNames 放大镜小窗口显示的栏位名称
	 * @param strDisplayFields 放大镜小窗口显示栏位对应的表格字段
	 * @param nIndex 确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,如果大于或等于strDisplayNames.length,则没有选择项
	 * @param strMainProperty 放大镜栏位属性
	 * @param strSQL 放大镜查询SQL语句
	 * @param strNextControls 设置下一个焦点
	 * @throws Exception
	 */
	public static void showZoomCtrl(
		JspWriter out,
		String strMagnifierName,
		String strFormName,
		String strPrefix,
		String[] strMainNames,
		String[] strMainFields,
		String[] strReturnNames,
		String[] strReturnFields,
		String[] strReturnValues,
		String[] strDisplayNames,
		String[] strDisplayFields,
		int nIndex,
		String strMainProperty,
		String strSQL,
		String[] strNextControls)
		throws Exception
	{
		String strTitle = strMagnifierName;
		showZoomCtrl(out, strMagnifierName, strFormName, strPrefix, strMainNames, strMainFields, strReturnNames, strReturnFields, strReturnValues, strDisplayNames, strDisplayFields, nIndex, strMainProperty, strSQL, strNextControls, strTitle);
	}
	/**
	 * 显示普通放大镜
	 * @param out
	 * @param strMagnifierName 放大镜的名称
	 * @param strFormName 主页面表单名称
	 * @param strPrefix 控件名称前缀
	 * @param strMainNames 放大镜回显栏位值列表
	 * @param strMainFields 放大镜回显栏位对应的表格字段
	 * @param strReturnNames 放大镜返回值列表(隐含值)
	 * @param strReturnFields 放大镜返回值(隐含值)对应的表格字段列表
	 * @param strReturnValues 放大镜返回值(隐含值)对应的初始值
	 * @param strDisplayNames 放大镜小窗口显示的栏位名称
	 * @param strDisplayFields 放大镜小窗口显示栏位对应的表格字段
	 * @param nIndex 确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,如果大于或等于strDisplayNames.length,则没有选择项
	 * @param strMainProperty 放大镜栏位属性
	 * @param strSQL 放大镜查询SQL语句
	 * @param strNextControls 设置下一个焦点
	 * @param strTitle 栏位标题
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性 
	 * @throws Exception
	 */
	public static void showZoomCtrl(
		JspWriter out,
		String strMagnifierName,
		String strFormName,
		String strPrefix,
		String[] strMainNames,
		String[] strMainFields,
		String[] strReturnNames,
		String[] strReturnFields,
		String[] strReturnValues,
		String[] strDisplayNames,
		String[] strDisplayFields,
		int nIndex,
		String strMainProperty,
		String strSQL,
		String[] strNextControls,
		String strTitle,
		String strFirstTD,
		String strSecondTD)
		throws Exception
	{
		boolean blnIsOptional = false;
		showZoomCtrl(
			out,
			strMagnifierName,
			strFormName,
			strPrefix,
			strMainNames,
			strMainFields,
			strReturnNames,
			strReturnFields,
			strReturnValues,
			strDisplayNames,
			strDisplayFields,
			nIndex,
			strMainProperty,
			strSQL,
			strNextControls,
			strTitle,
			strFirstTD,
			strSecondTD,
			blnIsOptional);
	}
	/**
	 * 显示普通放大镜
	 * @param out
	 * @param strMagnifierName 放大镜的名称
	 * @param strFormName 主页面表单名称
	 * @param strPrefix 控件名称前缀
	 * @param strMainNames 放大镜回显栏位值列表
	 * @param strMainFields 放大镜回显栏位对应的表格字段
	 * @param strReturnNames 放大镜返回值列表(隐含值)
	 * @param strReturnFields 放大镜返回值(隐含值)对应的表格字段列表
	 * @param strReturnValues 放大镜返回值(隐含值)对应的初始值
	 * @param strDisplayNames 放大镜小窗口显示的栏位名称
	 * @param strDisplayFields 放大镜小窗口显示栏位对应的表格字段
	 * @param nIndex 确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,如果大于或等于strDisplayNames.length,则没有选择项
	 * @param strMainProperty 放大镜栏位属性
	 * @param strSQL 放大镜查询SQL语句
	 * @param strNextControls 设置下一个焦点
	 * @param strTitle 栏位标题
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性 
	 * @param blnIsOptional 是否是可选项（目前仅对摘要、现金流向放大镜有用）
	 * @throws Exception
	 */
	public static void showZoomCtrl(
		JspWriter out,
		String strMagnifierName,
		String strFormName,
		String strPrefix,
		String[] strMainNames,
		String[] strMainFields,
		String[] strReturnNames,
		String[] strReturnFields,
		String[] strReturnValues,
		String[] strDisplayNames,
		String[] strDisplayFields,
		int nIndex,
		String strMainProperty,
		String strSQL,
		String[] strNextControls,
		String strTitle,
		String strFirstTD,
		String strSecondTD,
		boolean blnIsOptional)
		throws Exception
	{
		showZoomCtrl(
			out,
			strMagnifierName,
			strFormName,
			strPrefix,
			strMainNames,
			strMainFields,
			strReturnNames,
			strReturnFields,
			strReturnValues,
			strDisplayNames,
			strDisplayFields,
			nIndex,
			strMainProperty,
			strSQL,
			strNextControls,
			strTitle,
			strFirstTD,
			strSecondTD,
			blnIsOptional,
			"");
	}
	/**
	 * 显示普通放大镜
	 * @param out
	 * @param strMagnifierName 放大镜的名称
	 * @param strFormName 主页面表单名称
	 * @param strPrefix 控件名称前缀
	 * @param strMainNames 放大镜回显栏位值列表
	 * @param strMainFields 放大镜回显栏位对应的表格字段
	 * @param strReturnNames 放大镜返回值列表(隐含值)
	 * @param strReturnFields 放大镜返回值(隐含值)对应的表格字段列表
	 * @param strReturnValues 放大镜返回值(隐含值)对应的初始值
	 * @param strDisplayNames 放大镜小窗口显示的栏位名称
	 * @param strDisplayFields 放大镜小窗口显示栏位对应的表格字段
	 * @param nIndex 确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,如果大于或等于strDisplayNames.length,则没有选择项
	 * @param strMainProperty 放大镜栏位属性
	 * @param strSQL 放大镜查询SQL语句
	 * @param strNextControls 设置下一个焦点
	 * @param strTitle 栏位标题
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性 
	 * @param blnIsOptional 是否是可选项（目前仅对摘要、现金流向放大镜有用）
	 * @param strCtrlType 控件类型（特殊处理）
	 *  rate 利率控件(可以格式化利率)
	 *  branch 开户行控件(控件为textarea)
	 * @throws Exception
	 */
	public static void showZoomCtrl(
		JspWriter out,
		String strMagnifierName,
		String strFormName,
		String strPrefix,
		String[] strMainNames,
		String[] strMainFields,
		String[] strReturnNames,
		String[] strReturnFields,
		String[] strReturnValues,
		String[] strDisplayNames,
		String[] strDisplayFields,
		int nIndex,
		String strMainProperty,
		String strSQL,
		String[] strNextControls,
		String strTitle,
		String strFirstTD,
		String strSecondTD,
		boolean blnIsOptional,
		String strCtrlType)
		throws Exception
	{
		String strButtonName = "button";
		try
		{
			//检查放大镜参数
			checkValue(strMainNames, strMainFields, true);
			checkValue(strReturnNames, strReturnFields, strReturnValues, false);
			checkValue(strDisplayNames, strDisplayFields, true);
			if (strMagnifierName == null || strFormName == null || strFormName.equals("") || strSQL == null || strSQL.equals(""))
			{
				throw new IException(ZOOMERRORMSG);
			}
			if (strNextControls == null)
			{
				throw new IException(ZOOMERRORMSG);
			}
			if (strFirstTD == null)
			{
				strFirstTD = "";
			}
			if (strSecondTD == null)
			{
				strSecondTD = "";
			}
			//检查完毕
			//设置前缀
			if (strPrefix != null && !strPrefix.trim().equals(""))
			{
				for (int i = 0; i < strMainNames.length; i++)
				{
					strMainNames[i] = strPrefix + strMainNames[i];
				}
				for (int i = 0; i < strReturnNames.length; i++)
				{
					strReturnNames[i] = strPrefix + strReturnNames[i];
				}
			}
			//弹出窗口的属性
			String sFeatures = null;
			if (strDisplayNames.length < 3)
			{
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=520,height=450,scrollbars=yes";
			}
			else
			{
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=624,height=540,scrollbars=yes";
			}
			//生成传递给弹出窗口的参数字符串
			String strParam = "";
			strParam = "strFormName=" + strFormName;
			strParam += "&strMagnifierName=" + strMagnifierName;
			strParam += "&nIndex=" + nIndex;
			if (!isSQL(strSQL))
			{
				strParam += "&strSQL='+" + strSQL + "+'";
			}
			else
			{
				strParam += "&strSQL=" + getSQL(strSQL);
			}
			for (int i = 0; i < strNextControls.length; i++)
			{
				strParam += "&strNextControls=" + strNextControls[i];
			}
			for (int i = 0; i < strMainNames.length; i++)
			{
				strParam += "&strMainNames=" + strMainNames[i];
				strParam += "&strMainFields=" + strMainFields[i];
			}
			if (strReturnNames != null)
			{
				boolean bValue = false;
				if (strReturnValues != null && strReturnValues.length == strReturnNames.length)
				{
					bValue = true;
				}
				for (int i = 0; i < strReturnNames.length; i++)
				{
					//生成数组参数
					strParam += "&strReturnNames=" + strReturnNames[i];
					strParam += "&strReturnFields=" + strReturnFields[i];
					if (bValue)
					{
						out.println("<input type=\"hidden\" name=\"" + strReturnNames[i] + "\" value=\"" + strReturnValues[i] + "\">");
					}
					else
					{
						out.println("<input type=\"hidden\" name=\"" + strReturnNames[i] + "\">");
					}
				}
			}
			for (int i = 0; i < strDisplayNames.length; i++)
			{
				//生成数组参数
				strParam += "&strDisplayNames=" + strDisplayNames[i];
				strParam += "&strDisplayFields=" + strDisplayFields[i];
			}
			//Log.print("strParam = " + strParam);
			//生成查询按钮的事件字符串
			String sOnKeydown =
				"if("
					+ strFormName
					+ "."
					+ strMainNames[0]
					+ ".disabled == false) {gnIsSelectCtrl=1;window.open('"
					+ Env.URL_PREFIX
					+ "/iTreasury-treasuryplan/magnifier/ShowMagnifierZoom.jsp?"
					+ strParam
					+ "', 'SelectAnything', '"
					+ sFeatures
					+ "', false);}";
			//
			String sOnKeyUp = "";
			if (strReturnNames != null)
			{
				for (int i = 0; i < strReturnNames.length; i++)
				{
					sOnKeyUp += strReturnNames[i] + ".value = -1; ";
				}
			}
			//int iPos = strMainProperty.toLowerCase().indexOf("disabled");
			int iPos = -1;
			//显示控件
			if (iPos == -1)
			{
				out.println("<td " + strFirstTD + ">" + strTitle + "：&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/itreasury/graphics/icon.gif' border=0 onclick=\"" + sOnKeydown + "\"></a></td>");
				//image
			}
			else
			{
				out.println("<td " + strFirstTD + ">" + strTitle + "：&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/itreasury/graphics/icon.gif' border=0 ></a></td>");
				//image
			}
			//blnIsOptional,是否可选项（仅对摘要、现金流向放大镜有效）
			if (blnIsOptional == true)
			{
				/*
				if (strNextControls != null && strNextControls.length > 0)
				{
					strMainProperty = " onfocus=\"nextfield='" + strNextControls[0] + "'\" "+strMainProperty;
				}
				*/
				if (strCtrlType.equals("rate"))
				{
					out.println("<td" + strSecondTD + "><input type=\"text\" name=\"" + strMainNames[0] + "\" class=\"tar\" " + strMainProperty + ">%</td>");
				}
				else if (strCtrlType.equals("branch"))
				{
					out.println("<td" + strSecondTD + "><textarea name=\"" + strMainNames[0] + "\"  class=\"box\" bgcolor=\"#FF00\"  rows=2 cols=30>" + strMainProperty + "</textarea></td>");
				}
				else
				{
					out.println("<td" + strSecondTD + "><input type=\"text\" name=\"" + strMainNames[0] + "\" class=\"box\" " + strMainProperty + "></td>");
				}
			}
			else
			{
				if (strCtrlType.equals("rate"))
				{
					out.println("<td" + strSecondTD + "><input type=\"text\" name=\"" + strMainNames[0] + "\" class=\"tar\" " + strMainProperty + " onKeyDown=\"if(event.keyCode==13) " + sOnKeydown + "\" onKeyUp=\"" + sOnKeyUp + "\">%</td>");
				}
				else if (strCtrlType.equals("branch"))
				{
					out.println(
						"<td"
							+ strSecondTD
							+ "><textarea name=\""
							+ strMainNames[0]
							+ "\"  class=\"box\" bgcolor=\"#FF00\"  rows=2 cols=30 onKeyDown=\"if(event.keyCode==13) "
							+ sOnKeydown
							+ "\" onKeyUp=\""
							+ sOnKeyUp
							+ "\">"
							+ strMainProperty
							+ "</textarea></td>");
				}
				else
				{
					out.println("<td" + strSecondTD + "><input type=\"text\" name=\"" + strMainNames[0] + "\" class=\"box\" " + strMainProperty + " onKeyDown=\"if(event.keyCode==13) " + sOnKeydown + "\" onKeyUp=\"" + sOnKeyUp + "\"></td>");
				}
			}
		}
		catch (Exception exp)
		{
			throw exp;
		}
	}
	/**
	 * 转换null成""
	 * @param str
	 * @return
	 */
	private static String convertNullToSpace(String str){
		return str == null?"":str;
	}
	/**
	 * 转换字符串数组里面的null为""
	 * @param str
	 * @return
	 */
	private static String[] convertNullToSpace(String[] str){
		String[] strArray = null;
		if (str!=null){
			for (int n=0;n<str.length;n++){
				str[n] = (str[n]==null?"":str[n]);
			}
			strArray = str;
		}
		else {strArray = new String[]{};}
		return strArray;
	}
    
	/**
	 * 判定元素名称和数据库字段是否对称
	 * @param strElements
	 * @param strFields
	 * @return
	 */
	private static boolean isElementsMatchFields(String[] strElements,String[] strFields,boolean blnPermitNoMember){
		boolean blnMatch = false;
		if (!blnPermitNoMember){			//不可以为null
			if (strElements.length == strFields.length && strElements.length>0) blnMatch = true;
		}
		else{								//可以为null
			if (strElements.length == strFields.length) blnMatch = true;
		}
	    
		return blnMatch;
	}	
	/**
	 * 普通放大镜生成html主方法
	 * @param out
	 * @param strCtrlElementNames						//会影响到放大镜的页面元素名称
	 * @param strCtrlElementFields						//会影响到放大镜的页面元素对应的数据库字段
	 * @param strCtrlElementValues						//影响到放大镜页面元素的后台校验初始值
	 * 
	 * @param strCtrlValues								//会影响到放大镜的值
	 * @param strCtrlFields								//会影响到放大镜的值对应的数据库字段
	 * 
	 * String[] strOperators,							//每一个控制参数的操作符,如">","<","like","in"
	 * String[] strLogicOperators,						//两个where子句之间的逻辑运算符,只有两个"and"和"or"
	 * 
	 * @param strVisibleElementNames					//页面显示元素的名称 ,要产生页面元素
	 * @param strVisibleElementFields					//显示元素对应数据库的字段名.
	 * @param strVisibleElementValues					//显示元素的初始值
	 * 
	 * @param strVisibleElementType						//显示元素的类型,"text","textarea"
	 * @param strVisibleElementProperty 				//属性,写入到放大镜显示文本域中
	 *  
	 * @param strHiddenElementNames						//主页面隐含域里名称,要产生隐含域
	 * @param strHiddenElementFields 					//id对应的字段名
	 * @param strHiddenElementValues					//初始值
	 * 
	 * @param strOtherElementNames						//其他和本放大镜关联的元素,即当返回时也要返回
	 * @param strOtherElementFields						//其他需要返回值的元素对应的数据库字段
	 * 
	 * @param strListTitleDisplayNames 					//放大镜弹出页面的显示名称,可以有多列
	 * @param strListTitleDisplayFields 				//显示名称的对应字段
	 * 
	 * @param strNextFocusElementNames					//下一个焦点
	 * 
	 * @param strWindowTitle							//放大镜弹出窗口标题
	 * @param intListAnchorPosition						//放大镜页面弹出时默认停留的位置
	 * @param intSQL									//数据查询Sql代码,确定使用第几组sql
	 * @param strName									//表名 
	 * @param blnShowOnly								//标志位,确定当前放大镜是否只是用于显示
	 * 											(比如复核页面,全部置灰,参数不向后传递),如果是,则不会写入弹出窗口代码
	 * @throws Exception
	 */
	public static void showZoomCtrl(
			JspWriter out,
		
			String[] strCtrlElementNames,
			String[] strCtrlElementFields,
			String[] strCtrlElementValues,
			
			String[] strCtrlValues,
			String[] strCtrlFields,
			
			String[] strOperators,
			String[] strLogicOperators,
			
			String strVisibleElementNames,
			String strVisibleElementFields,
			String strVisibleElementValues,

			String strVisibleElementType,
			String strVisibleElementProperty,
			
			String[] strHiddenElementNames,
			String[] strHiddenElementFields,
			String[] strHiddenElementValues,

			String[] strOtherElementNames,
			String[] strOtherElementFields,
			String[] strOtherElementValues,
			
			String[] strListTitleDisplayNames,
			String[] strListTitleDisplayFields,

			String[] strNextFocusElementNames,
			
			String strWindowTitle,
			int intListAnchorPosition,
			
			int intSQL,
			String strName,
			boolean blnShowOnly
			)throws Exception{
        
			if (strVisibleElementNames==null || strVisibleElementNames.trim().length()==0){
		
			}
			
			/**进行转化*/
			strCtrlElementNames			= convertNullToSpace(strCtrlElementNames);
			strCtrlElementFields		= convertNullToSpace(strCtrlElementFields);
			strCtrlElementValues		= convertNullToSpace(strCtrlElementValues);
			strCtrlValues				= convertNullToSpace(strCtrlValues);
			strCtrlFields				= convertNullToSpace(strCtrlFields);
			
			strVisibleElementValues 	= convertNullToSpace(strVisibleElementValues);
			
			strVisibleElementType 		= convertNullToSpace(strVisibleElementType);
			
			strHiddenElementNames		= convertNullToSpace(strHiddenElementNames);
			strHiddenElementFields		= convertNullToSpace(strHiddenElementFields);
			strHiddenElementValues		= convertNullToSpace(strHiddenElementValues);
			
			strOtherElementNames		= convertNullToSpace(strOtherElementNames);
			strOtherElementFields		= convertNullToSpace(strOtherElementFields);
			strOtherElementValues		= convertNullToSpace(strOtherElementValues);
			
			strListTitleDisplayNames	= convertNullToSpace(strListTitleDisplayNames);
			strListTitleDisplayFields	= convertNullToSpace(strListTitleDisplayFields);
						
			strWindowTitle 				= convertNullToSpace(strWindowTitle);
			strVisibleElementProperty 	= convertNullToSpace(strVisibleElementProperty);
			
			strNextFocusElementNames	= convertNullToSpace(strNextFocusElementNames);
			/**转化结束*/
			
			/**校验有效值*/
			if (!isElementsMatchFields(strCtrlElementNames,strCtrlElementFields,true)){
				throw new Exception("控制元素'名称'和'字段'不匹配!");
			}
			if (!isElementsMatchFields(strCtrlElementNames,strCtrlElementValues,true)){
				throw new Exception("控制元素'名称'和'初始值'不匹配!");
			}
			if (!isElementsMatchFields(strCtrlValues,strCtrlFields,true)){
				throw new Exception("控制值'值'和'字段'不匹配!");
			}
			if (!isElementsMatchFields(strHiddenElementNames,strHiddenElementFields,false)){		//不允许为空
				throw new Exception("隐含元素'名称'和'字段'不匹配!");
			}
			if (!isElementsMatchFields(strHiddenElementNames,strHiddenElementValues,true)){
				throw new Exception("隐含元素'名称'和'初始值'不匹配!");
			}
			if (!isElementsMatchFields(strOtherElementNames,strOtherElementFields,true)){
				throw new Exception("接收元素'名称'和'字段'不匹配!");
			}
			if (!isElementsMatchFields(strOtherElementFields,strOtherElementValues,true)){
				throw new Exception("接收元素'字段'和'初始值'不匹配");
			}
			if (!isElementsMatchFields(strListTitleDisplayNames,strListTitleDisplayFields,false)){	//不允许为空
				throw new Exception("窗口'描述'和'字段'不匹配!");
			}
			
			if (strVisibleElementNames.equals("") || strVisibleElementFields.equals("")){			//不允许为空
				throw new Exception("主元素'名称'和'字段'不匹配!");
			}
			/**校验结束*/
			
			StringBuffer sbOutputContent = new StringBuffer(1024);				//输出内容
			StringBuffer sbJsOnkeyDown = new StringBuffer(256);					//?后面传递的参数
			StringBuffer sbJsOnFocus = new StringBuffer(128);					//当放大镜获得焦点时出发,填充校验域
        	
        	
        	
				  /**        添加可见元素名称     	 **/
			sbJsOnkeyDown.append("openWindow(new Array('" + strVisibleElementNames + "'");

			for (int n=0;n<strHiddenElementNames.length;n++){
				sbJsOnkeyDown.append(",'" + strHiddenElementNames[n] + "'");
			}
			for (int n=0;n<strOtherElementNames.length;n++){
				sbJsOnkeyDown.append(",'" + strOtherElementNames[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
			
				  /**        添加可见元素对应数据库字段名称     	 **/
			sbJsOnkeyDown.append("new Array('" + strVisibleElementFields + "'");
			for (int n=0;n<strHiddenElementFields.length;n++){
				sbJsOnkeyDown.append(",'" + strHiddenElementFields[n] + "'");
			}
			for (int n=0;n<strOtherElementFields.length;n++){
				sbJsOnkeyDown.append(",'" + strOtherElementFields[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");

			/**			添加控制元素的值		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strCtrlElementNames.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strCtrlElementNames[n] + "'");
				else sbJsOnkeyDown.append(",'" + strCtrlElementNames[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
			
			/**			添加控制元素对应得字段名称		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strCtrlElementFields.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strCtrlElementFields[n] + "'");
				else sbJsOnkeyDown.append(",'" + strCtrlElementFields[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
		
				/**			添加控制元素对应得字段名称		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strCtrlValues.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strCtrlValues[n] + "'");
				else sbJsOnkeyDown.append(",'" + strCtrlValues[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
        
				/**			添加控制元素对应得字段名称		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strCtrlFields.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strCtrlFields[n] + "'");
				else sbJsOnkeyDown.append(",'" + strCtrlFields[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
			
				/**			添加控制元素对应得运算符		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strOperators.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strOperators[n] + "'");
				else sbJsOnkeyDown.append(",'" + strOperators[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
			
				/**			添加控制元素之间的逻辑运算符		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strLogicOperators.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strLogicOperators[n] + "'");
				else sbJsOnkeyDown.append(",'" + strLogicOperators[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
			
				  /**			添加弹出窗口显示字段的描述		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strListTitleDisplayNames.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strListTitleDisplayNames[n] + "'");
				else sbJsOnkeyDown.append(",'" + strListTitleDisplayNames[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
        	
				  /**			添加弹出窗口显示字段名字		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strListTitleDisplayFields.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strListTitleDisplayFields[n] + "'");
				else sbJsOnkeyDown.append(",'" + strListTitleDisplayFields[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
        	
			sbJsOnkeyDown.append("'"+strWindowTitle+"', \n");
			sbJsOnkeyDown.append("'"+intListAnchorPosition+"', \n");
			sbJsOnkeyDown.append("'"+intSQL+"', \n");
			sbJsOnkeyDown.append("'"+strName+"', \n");
		
			/**			添加下一个焦点位置		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strNextFocusElementNames.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strNextFocusElementNames[n] + "'");
				else sbJsOnkeyDown.append(",'" + strNextFocusElementNames[n] + "'");
			}
			sbJsOnkeyDown.append(")) \n");

			
			if (blnShowOnly) strVisibleElementProperty +=" disabled";			//如果只做显示,那么置为disabled
			if (strVisibleElementType.toLowerCase().equals("text") || strVisibleElementType.equals("")){
				sbOutputContent.append("<input type='" + strVisibleElementType + 
				"' name='" + strVisibleElementNames + 
				"' value='" + strVisibleElementValues + 
				"' " + strVisibleElementProperty);
			
				if (!blnShowOnly){
					sbOutputContent.append(" onKeyDown=\"if(event.keyCode==13)" + sbJsOnkeyDown.toString() + " \"" );
				
					if (strHiddenElementNames!=null){
						sbOutputContent.append(" onKeyUp=\"");
						for (int n=0;n<strHiddenElementNames.length;n++){
							sbOutputContent.append("document.all.");
							sbOutputContent.append(strHiddenElementNames[n]+".value=''; \n");
							if (n<(strHiddenElementNames.length-1)){sbOutputContent.append(",");}
						}
						for (int n=0;n<strCtrlElementNames.length;n++){				//影响放大镜的元素的校验域和初始值
							if (strCtrlElementNames[n]!=null && strCtrlElementNames[n].trim().length()>0 && !strCtrlElementNames[n].equals(strVisibleElementNames)){
								sbOutputContent.append("document.all.");
								sbOutputContent.append(strCtrlElementNames[n] + strVisibleElementNames);
								sbOutputContent.append(".value=''; \n");
							}
						}
						sbOutputContent.append("\"");
					}
				}
			
				sbOutputContent.append("> \n");
			}
			else if(strVisibleElementType.toLowerCase().equals("textarea")){
				sbOutputContent.append("<textArea name='" + strVisibleElementNames +
				"' " + strVisibleElementProperty);
			
				if (!blnShowOnly){
					sbOutputContent.append(" onKeyDown=\"if(event.keyCode==13)" + sbJsOnkeyDown.toString()+ "\"" );
				}
			
				sbOutputContent.append(">");
				sbOutputContent.append(strVisibleElementValues + "</textArea> \n");
			}
			if (!blnShowOnly){
				sbOutputContent.append("<a href='#1'><img name='imgButton'" +
				" src='/itreasury/graphics/icon.gif' border=0 " +
				" onclick=\"" + sbJsOnkeyDown.toString() +
				"\"></a> \n");
			}
			
			
			for (int n=0;n<strHiddenElementNames.length;n++){
				sbOutputContent.append("<input type='hidden' name='" + strHiddenElementNames[n] + 
				"' value='" + strHiddenElementValues[n] + "'> \n");
			}
		
			if (!blnShowOnly){
				for (int n=0;n<strCtrlElementNames.length;n++){				//影响放大镜的元素的校验域和初始值
					if (strCtrlElementNames[n]!=null && strCtrlElementNames[n].trim().length()>0 && !strCtrlElementNames[n].equals(strVisibleElementNames)){
						sbOutputContent.append("<input type='hidden' name='" + 
								(strCtrlElementNames[n] + strVisibleElementNames) + 
								"' value='" + strCtrlElementValues[n] + "'> \n");
					}
				}
			}
			if (strOtherElementNames != null && strOtherElementNames.length > 0){//影响对象的初始值赋值
				sbOutputContent.append("<script language='javascript'> \n");
				sbOutputContent.append("	setTimeout(\"");
				for (int n=0;n<strOtherElementNames.length;n++){
					if (strOtherElementNames[n]!=null && strOtherElementNames[n].trim().length()>0 
							&& strOtherElementValues[n]!=null && strOtherElementValues[n].length()>0){
						sbOutputContent.append("document.all."+strOtherElementNames[n].trim()+".value='"+strOtherElementValues[n]+"';");
					}
				}
				sbOutputContent.append("	\",100); \n");
				sbOutputContent.append("</script> \n");
			}
				
			out.println(sbOutputContent.toString());
		}	
	/**
	 * 显示普通放大镜
	 * @param out
	 * @param strMagnifierName 放大镜的名称
	 * @param strFormName 主页面表单名称
	 * @param strPrefix 控件名称前缀
	 * @param strMainNames 放大镜回显栏位值列表
	 * @param strMainFields 放大镜回显栏位对应的表格字段
	 * @param strReturnNames 放大镜返回值列表(隐含值)
	 * @param strReturnFields 放大镜返回值(隐含值)对应的表格字段列表
	 * @param strReturnValues 放大镜返回值(隐含值)对应的初始值
	 * @param strDisplayNames 放大镜小窗口显示的栏位名称
	 * @param strDisplayFields 放大镜小窗口显示栏位对应的表格字段
	 * @param nIndex 确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,如果大于或等于strDisplayNames.length,则没有选择项
	 * @param strMainProperty 放大镜栏位属性
	 * @param strSQL 放大镜查询SQL语句
	 * @param strNextControls 设置下一个焦点
	 * @param strTitle 栏位标题
	 * @throws Exception
	 */
	public static void showZoomCtrl(
		JspWriter out,
		String strMagnifierName,
		String strFormName,
		String strPrefix,
		String[] strMainNames,
		String[] strMainFields,
		String[] strReturnNames,
		String[] strReturnFields,
		String[] strReturnValues,
		String[] strDisplayNames,
		String[] strDisplayFields,
		int nIndex,
		String strMainProperty,
		String strSQL,
		String[] strNextControls,
		String strTitle)
		throws Exception
	{
		String strFirstTD = "";
		String strSecondTD = "";
		showZoomCtrl(
			out,
			strMagnifierName,
			strFormName,
			strPrefix,
			strMainNames,
			strMainFields,
			strReturnNames,
			strReturnFields,
			strReturnValues,
			strDisplayNames,
			strDisplayFields,
			nIndex,
			strMainProperty,
			strSQL,
			strNextControls,
			strTitle,
			strFirstTD,
			strSecondTD);
	}
	/**
	 * 显示特殊的放大镜（带多个文本输入框的，如账户放大镜）
	 * @param out
	 * @param strMagnifierName 放大镜的名称
	 * @param strFormName 主页面表单名称
	 * @param strPrefix 控件名称前缀
	 * @param nCaseNumber 显示文本框的数目（目前只支持3，4）
	 * @param strMainNames 放大镜回显栏位值列表
	 * @param strMainFields 放大镜回显栏位对应的表格字段
	 * @param strReturnNames 放大镜返回值列表(隐含值)
	 * @param strReturnFields 放大镜返回值(隐含值)对应的表格字段列表
	 * @param strReturnValues 放大镜返回值(隐含值)对应的初始值
	 * @param strDisplayNames 放大镜小窗口显示的栏位名称
	 * @param strDisplayFields 放大镜小窗口显示栏位对应的表格字段
	 * @param nIndex 确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,如果大于或等于strDisplayNames.length,则没有选择项
	 * @param strMainProperty 放大镜栏位属性
	 * @param strSQL 放大镜查询SQL语句
	 * @param strNextControls 设置下一个焦点
	 * @param strAccountNo 账户编号
	 * @throws Exception
	 */
	public static void showSpecialZoomCtrl(
		JspWriter out,
		String strMagnifierName,
		String strFormName,
		String strPrefix,
		int nCaseNumber,
		String[] strMainNames,
		String[] strMainFields,
		String[] strReturnNames,
		String[] strReturnFields,
		String[] strReturnValues,
		String[] strDisplayNames,
		String[] strDisplayFields,
		int nIndex,
		String strMainProperty,
		String strSQL,
		String[] strNextControls,
		String strAccountNo)
		throws Exception
	{
		String strTitle = strMagnifierName;
		showSpecialZoomCtrl(
			out,
			strMagnifierName,
			strFormName,
			strPrefix,
			nCaseNumber,
			strMainNames,
			strMainFields,
			strReturnNames,
			strReturnFields,
			strReturnValues,
			strDisplayNames,
			strDisplayFields,
			nIndex,
			strMainProperty,
			strSQL,
			strNextControls,
			strAccountNo,
			strTitle);
	}
	/**
	 * 显示特殊的放大镜（带多个文本输入框的，如账户放大镜）
	 * @param out
	 * @param strMagnifierName 放大镜的名称
	 * @param strFormName 主页面表单名称
	 * @param strPrefix 控件名称前缀
	 * @param nCaseNumber 显示文本框的数目（目前只支持3，4）
	 * @param strMainNames 放大镜回显栏位值列表
	 * @param strMainFields 放大镜回显栏位对应的表格字段
	 * @param strReturnNames 放大镜返回值列表(隐含值)
	 * @param strReturnFields 放大镜返回值(隐含值)对应的表格字段列表
	 * @param strReturnValues 放大镜返回值(隐含值)对应的初始值
	 * @param strDisplayNames 放大镜小窗口显示的栏位名称
	 * @param strDisplayFields 放大镜小窗口显示栏位对应的表格字段
	 * @param nIndex 确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,如果大于或等于strDisplayNames.length,则没有选择项
	 * @param strMainProperty 放大镜栏位属性
	 * @param strSQL 放大镜查询SQL语句
	 * @param strNextControls 设置下一个焦点
	 * @param strAccountNo 账户编号
	 * @param strTitle 放大镜的描述
	 * @throws Exception
	 */
	public static void showSpecialZoomCtrl(
		JspWriter out,
		String strMagnifierName,
		String strFormName,
		String strPrefix,
		int nCaseNumber,
		String[] strMainNames,
		String[] strMainFields,
		String[] strReturnNames,
		String[] strReturnFields,
		String[] strReturnValues,
		String[] strDisplayNames,
		String[] strDisplayFields,
		int nIndex,
		String strMainProperty,
		String strSQL,
		String[] strNextControls,
		String strAccountNo,
		String strTitle)
		throws Exception
	{
		String strFirstTD = "";
		String strSecondTD = "";

		showSpecialZoomCtrl(
			out,
			strMagnifierName,
			strFormName,
			strPrefix,
			nCaseNumber,
			strMainNames,
			strMainFields,
			strReturnNames,
			strReturnFields,
			strReturnValues,
			strDisplayNames,
			strDisplayFields,
			nIndex,
			strMainProperty,
			strSQL,
			strNextControls,
			strAccountNo,
			strTitle,
			strFirstTD,
			strSecondTD);
	}

	/**
	 * 显示特殊的放大镜（带多个文本输入框的，如账户放大镜）
	 * @param out
	 * @param strMagnifierName 放大镜的名称
	 * @param strFormName 主页面表单名称
	 * @param strPrefix 控件名称前缀
	 * @param nCaseNumber 显示文本框的数目（目前只支持3，4）
	 * @param strMainNames 放大镜回显栏位值列表
	 * @param strMainFields 放大镜回显栏位对应的表格字段
	 * @param strReturnNames 放大镜返回值列表(隐含值)
	 * @param strReturnFields 放大镜返回值(隐含值)对应的表格字段列表
	 * @param strReturnValues 放大镜返回值(隐含值)对应的初始值
	 * @param strDisplayNames 放大镜小窗口显示的栏位名称
	 * @param strDisplayFields 放大镜小窗口显示栏位对应的表格字段
	 * @param nIndex 确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,如果大于或等于strDisplayNames.length,则没有选择项
	 * @param strMainProperty 放大镜栏位属性
	 * @param strSQL 放大镜查询SQL语句
	 * @param strNextControls 设置下一个焦点
	 * @param strAccountNo 账户编号
	 * @param strTitle 放大镜的描述
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性 
	 * @throws Exception
	 */
	public static void showSpecialZoomCtrl(
		JspWriter out,
		String strMagnifierName,
		String strFormName,
		String strPrefix,
		int nCaseNumber,
		String[] strMainNames,
		String[] strMainFields,
		String[] strReturnNames,
		String[] strReturnFields,
		String[] strReturnValues,
		String[] strDisplayNames,
		String[] strDisplayFields,
		int nIndex,
		String strMainProperty,
		String strSQL,
		String[] strNextControls,
		String strAccountNo,
		String strTitle,
		String strFirstTD,
		String strSecondTD)
		throws Exception
	{
		showSpecialZoomCtrl(
			out,
			strMagnifierName,
			strFormName,
			strPrefix,
			nCaseNumber,
			strMainNames,
			strMainFields,
			strReturnNames,
			strReturnFields,
			strReturnValues,
			strDisplayNames,
			strDisplayFields,
			nIndex,
			strMainProperty,
			strSQL,
			strNextControls,
			strAccountNo,
			strTitle,
			strFirstTD,
			strSecondTD,
			"");
	}

	/**
	 * 显示特殊的放大镜（带多个文本输入框的，如账户放大镜）
	 * @param out
	 * @param strMagnifierName 放大镜的名称
	 * @param strFormName 主页面表单名称
	 * @param strPrefix 控件名称前缀
	 * @param nCaseNumber 显示文本框的数目（目前只支持3，4）
	 * @param strMainNames 放大镜回显栏位值列表
	 * @param strMainFields 放大镜回显栏位对应的表格字段
	 * @param strReturnNames 放大镜返回值列表(隐含值)
	 * @param strReturnFields 放大镜返回值(隐含值)对应的表格字段列表
	 * @param strReturnValues 放大镜返回值(隐含值)对应的初始值
	 * @param strDisplayNames 放大镜小窗口显示的栏位名称
	 * @param strDisplayFields 放大镜小窗口显示栏位对应的表格字段
	 * @param nIndex 确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,如果大于或等于strDisplayNames.length,则没有选择项
	 * @param strMainProperty 放大镜栏位属性
	 * @param strSQL 放大镜查询SQL语句
	 * @param strNextControls 设置下一个焦点
	 * @param strAccountNo 账户编号
	 * @param strTitle 放大镜的描述
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性 
	 * @param String strClientIDCtrl 关联控件（客户ID对应的控件名称）
	 * @throws Exception
	 */
	public static void showSpecialZoomCtrl(
		JspWriter out,
		String strMagnifierName,
		String strFormName,
		String strPrefix,
		int nCaseNumber,
		String[] strMainNames,
		String[] strMainFields,
		String[] strReturnNames,
		String[] strReturnFields,
		String[] strReturnValues,
		String[] strDisplayNames,
		String[] strDisplayFields,
		int nIndex,
		String strMainProperty,
		String strSQL,
		String[] strNextControls,
		String strAccountNo,
		String strTitle,
		String strFirstTD,
		String strSecondTD,
		String strClientIDCtrl)
		throws Exception
	{
		String strButtonName = "button";
		try
		{
			//检查放大镜参数
			checkValue(strMainNames, strMainFields, true);
			checkValue(strReturnNames, strReturnFields, strReturnValues, false);
			checkValue(strDisplayNames, strDisplayFields, true);
			if (strMagnifierName == null || strFormName == null || strFormName.equals("") || strSQL == null || strSQL.equals(""))
			{
				throw new IException(ZOOMERRORMSG);
			}
			if (strNextControls == null)
			{
				throw new IException(ZOOMERRORMSG);
			}
			if (strAccountNo == null)
			{
				strAccountNo = "";
			}
			if (strFirstTD == null)
			{
				strFirstTD = "";
			}
			if (strSecondTD == null)
			{
				strSecondTD = "";
			}
			//检查完毕

			String strTempCtl1 = strMainNames[0] + "Ctrl1";
			String strTempCtl2 = strMainNames[0] + "Ctrl2";
			String strTempCtl3 = strMainNames[0] + "Ctrl3";
			String strTempCtl4 = strMainNames[0] + "Ctrl4";
			//设置前缀
			if (strPrefix != null && !strPrefix.trim().equals(""))
			{
				for (int i = 0; i < strMainNames.length; i++)
				{
					strMainNames[i] = strPrefix + strMainNames[i];
				}
				for (int i = 0; i < strReturnNames.length; i++)
				{
					strReturnNames[i] = strPrefix + strReturnNames[i];
				}
			}
			//弹出窗口的属性
			String sFeatures = null;
			if (strDisplayNames.length < 3)
			{
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=520,height=450,scrollbars=yes";
			}
			else
			{
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=624,height=540,scrollbars=yes";
			}
			//生成传递给弹出窗口的参数字符串
			String strParam = "";
			strParam = "strFormName=" + strFormName;
			strParam += "&strMagnifierName=" + strMagnifierName;
			strParam += "&nIndex=" + nIndex;
			if (!isSQL(strSQL))
			{
				strParam += "&strSQL='+" + strSQL + "+'";
			}
			else
			{
				strParam += "&strSQL=" + getSQL(strSQL);
			}
			for (int i = 0; i < strNextControls.length; i++)
			{
				strParam += "&strNextControls=" + strNextControls[i];
			}
			for (int i = 0; i < strMainNames.length; i++)
			{
				strParam += "&strMainNames=" + strMainNames[i];
				strParam += "&strMainFields=" + strMainFields[i];
			}
			if (strReturnNames != null)
			{
				boolean bValue = false;
				if (strReturnValues != null && strReturnValues.length == strReturnNames.length)
				{
					bValue = true;
				}
				for (int i = 0; i < strReturnNames.length; i++)
				{
					//生成数组参数
					strParam += "&strReturnNames=" + strReturnNames[i];
					strParam += "&strReturnFields=" + strReturnFields[i];
					if (bValue)
					{
						out.println("<input type=\"hidden\" name=\"" + strReturnNames[i] + "\" value=\"" + strReturnValues[i] + "\">");
					}
					else
					{
						out.println("<input type=\"hidden\" name=\"" + strReturnNames[i] + "\">");
					}
				}
			}
			for (int i = 0; i < strDisplayNames.length; i++)
			{
				//生成数组参数
				strParam += "&strDisplayNames=" + strDisplayNames[i];
				strParam += "&strDisplayFields=" + strDisplayFields[i];
			}
			//Log.print("strParam = " + strParam);
			//生成查询按钮的事件字符串
			String sOnKeydown =
				"if("
					+ strFormName
					+ "."
					+ strTempCtl1
					+ ".disabled == false) {gnIsSelectCtrl=1;window.open('"
					+ Env.URL_PREFIX
					+ "/iTreasury-treasuryplan/magnifier/ShowMagnifierZoom.jsp?"
					+ strParam
					+ "', 'SelectAnything', '"
					+ sFeatures
					+ "', false);}";
			//
			String sOnKeyUp = "";
			if (strReturnNames != null)
			{
				for (int i = 0; i < strReturnNames.length; i++)
				{
					sOnKeyUp += strReturnNames[i] + ".value = -1; ";
				}
				if (strClientIDCtrl != null && !strClientIDCtrl.equals(""))
				{
					sOnKeyUp += "if(" + strClientIDCtrl + "FromClient.value == 0) {" + strClientIDCtrl + ".value = -1;}";
				}
			}
			//int iPos = strMainProperty.toLowerCase().indexOf("disabled");
			int iPos = -1;
			//显示控件
			if (iPos == -1)
			{
				out.println("<td " + strFirstTD + ">" + strTitle + "：&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/itreasury/graphics/icon.gif' border=0 onclick=\"" + sOnKeydown + "\"></a></td>");
				//image
			}
			else
			{
				out.println("<td " + strFirstTD + ">" + strTitle + "：&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/itreasury/graphics/icon.gif' border=0 ></a></td>");
				//image
			}
			//将账户编号拆分
			String strAccountNo1 = "";
			String strAccountNo2 = "";
			String strAccountNo3 = "";
			String strAccountNo4 = "";
			if (strAccountNo != null && strAccountNo.length() > 0)
			{
				String strTemp = strAccountNo;
				if (strTemp.indexOf("-") >= 0)
				{
					strAccountNo1 = strTemp.substring(0, strTemp.indexOf("-"));
				}
				else
				{
					strAccountNo1 = strTemp;
					strTemp = "";
				}
				Log.print(strAccountNo1);

				strTemp = strTemp.substring(strTemp.indexOf("-") + 1);
				if (strTemp.indexOf("-") >= 0)
				{
					strAccountNo2 = strTemp.substring(0, strTemp.indexOf("-"));
				}
				else
				{
					strAccountNo2 = strTemp;
					strTemp = "";
				}
				Log.print(strAccountNo2);

				strTemp = strTemp.substring(strTemp.indexOf("-") + 1);
				if (strTemp.indexOf("-") >= 0)
				{
					strAccountNo3 = strTemp.substring(0, strTemp.indexOf("-"));
				}
				else
				{
					strAccountNo3 = strTemp;
					strTemp = "";
				}
				Log.print(strAccountNo3);

				strTemp = strTemp.substring(strTemp.indexOf("-") + 1);
				strAccountNo4 = strTemp;
				Log.print(strAccountNo4);
			}

			String strTempFunction = "";
			String strTempFunctionForFixed = "";
			if (nCaseNumber == 3)
			{
				strTempFunctionForFixed = strPrefix + strMainNames[0] + "setWholeAcccountNo(" + strFormName + "." + strTempCtl1 + ".value," + strFormName + "." + strTempCtl2 + ".value," + strFormName + "." + strTempCtl3 + ".value,'');";
				strTempFunction = strTempFunctionForFixed + sOnKeyUp;
			}
			else if (nCaseNumber >= 4)
			{
				strTempFunction =
					strPrefix
						+ strMainNames[0]
						+ "setWholeAcccountNo("
						+ strFormName
						+ "."
						+ strTempCtl1
						+ ".value,"
						+ strFormName
						+ "."
						+ strTempCtl2
						+ ".value,"
						+ strFormName
						+ "."
						+ strTempCtl3
						+ ".value,"
						+ strFormName
						+ "."
						+ strTempCtl4
						+ ".value);"
						+ sOnKeyUp;
			}
			out.println("<td " + strSecondTD + ">");
			out.println("<input type=\"hidden\" name=\"" + strMainNames[0] + "\" value=\"" + strAccountNo + "\">");
			out.println(
				"<input type=\"text\" size=\"2\" maxlength=\"2\" name=\""
					+ strTempCtl1
					+ "\" value=\""
					+ strAccountNo1
					+ "\" class=\"box\"  onKeyDown=\"gnIsSelectCtrl=1;"
					+ strPrefix
					+ strMainNames[0]
					+ "fieldFirstFocus("
					+ strFormName
					+ "."
					+ strTempCtl1
					+ ".value)\" onKeyUp=\""
					+ strTempFunction
					+ "\">");
			out.println("-");
			out.println(
				"<input type=\"text\" size=\"2\" maxlength=\"2\" name=\""
					+ strTempCtl2
					+ "\" value=\""
					+ strAccountNo2
					+ "\" class=\"box\"  onKeyDown=\"gnIsSelectCtrl=1;"
					+ strPrefix
					+ strMainNames[0]
					+ "fieldSecondFocus("
					+ strFormName
					+ "."
					+ strTempCtl2
					+ ".value)\" onKeyUp=\""
					+ strTempFunction
					+ "\">");
			if (nCaseNumber == 3)
			{
				out.println("-");
				out.println(
					"<input type=\"text\" size=\"4\" maxlength=\"4\" name=\""
						+ strTempCtl3
						+ "\" value=\""
						+ strAccountNo3
						+ "\" class=\"box\"  onKeyDown=\"gnIsSelectCtrl=1;"
						+ strPrefix
						+ strMainNames[0]
						+ "fieldThirdFocus("
						+ strFormName
						+ "."
						+ strTempCtl3
						+ ".value)\" onKeyUp=\""
						+ strTempFunction
						+ "\">");
			}
			else if (nCaseNumber >= 4)
			{
				out.println("-");
				out.println(
					"<input type=\"text\" size=\"4\" maxlength=\"4\" name=\""
						+ strTempCtl3
						+ "\" value=\""
						+ strAccountNo3
						+ "\" class=\"box\"  onKeyDown=\"gnIsSelectCtrl=1;"
						+ strPrefix
						+ strMainNames[0]
						+ "fieldThirdFocus("
						+ strFormName
						+ "."
						+ strTempCtl3
						+ ".value)\" onKeyUp=\""
						+ strTempFunction
						+ "\">");
				out.println("-");
				out.println(
					"<input type=\"text\" size=\"1\" maxlength=\"1\" name=\""
						+ strTempCtl4
						+ "\" value=\""
						+ strAccountNo4
						+ "\" class=\"box\"  onKeyDown=\"gnIsSelectCtrl=1;"
						+ strPrefix
						+ strMainNames[0]
						+ "fieldFouthFocus("
						+ strFormName
						+ "."
						+ strTempCtl4
						+ ".value)\" onKeyUp=\""
						+ strTempFunction
						+ "\">");
			}
			out.println("</td>");
			//打印脚本程序
			out.println("<script language=\"JavaScript\">");
			out.println("function " + strPrefix + strMainNames[0] + "fieldFirstFocus(sValue)");
			out.println("{");
			out.println("	var k = window.event.keyCode;");
			out.println("	if (k != 46 && k != 8 && k != 37 && k != 39)");
			out.println("	{");
			out.println("		if (k == 13 || sValue.length == 2)");
			out.println("		{");
			out.println("			if (sValue.length ==1)");
			out.println("			{");
			out.println("				" + strFormName + "." + strTempCtl1 + ".value = '0'+sValue;");
			out.println("			}");
			out.println("			" + strFormName + "." + strTempCtl2 + ".focus();");
			out.println("			" + strFormName + "." + strTempCtl2 + ".select();");
			out.println("		}");
			out.println("	}");
			out.println("}");
			out.println("function " + strPrefix + strMainNames[0] + "fieldSecondFocus(sValue)");
			out.println("{");
			out.println("	var k = window.event.keyCode;");
			out.println("	if (k != 46 && k != 8 && k != 37 && k != 39)");
			out.println("	{");
			out.println("		if (k == 13 || sValue.length == 2)");
			out.println("		{");
			out.println("			if (sValue.length ==1)");
			out.println("			{");
			out.println("				" + strFormName + "." + strTempCtl2 + ".value = '0'+sValue;");
			out.println("			}");
			out.println("			" + strFormName + "." + strTempCtl3 + ".focus();");
			out.println("			" + strFormName + "." + strTempCtl3 + ".select();");
			out.println("		}");
			out.println("	}");
			out.println("	else if (k == 8 && sValue.length == 0)");
			out.println("	{");
			out.println("		" + strFormName + "." + strTempCtl1 + ".value = \"\";");
			out.println("		" + strFormName + "." + strTempCtl1 + ".focus();");
			out.println("	}");
			out.println("}");
			if (nCaseNumber == 3)
			{
				out.println("function " + strPrefix + strMainNames[0] + "fieldThirdFocus(sValue)");
				out.println("{");
				out.println("	var k = window.event.keyCode;");
				out.println("	if (k != 46 && k != 8 && k != 37 && k != 39)");
				out.println("	{");
				out.println("		if (k == 13 )");
				out.println("		{");
				out.println("			if (sValue.length ==1)");
				out.println("			{");
				out.println("				" + strFormName + "." + strTempCtl3 + ".value = '000'+sValue;");
				out.println("			}");
				out.println("			else if (sValue.length ==2)");
				out.println("			{");
				out.println("				" + strFormName + "." + strTempCtl3 + ".value = '00'+sValue;");
				out.println("			}");
				out.println("			else if (sValue.length ==3)");
				out.println("			{");
				out.println("				" + strFormName + "." + strTempCtl3 + ".value = '0'+sValue;");
				out.println("			}");
				out.println("			" + strTempFunctionForFixed);
				out.println("			" + sOnKeydown);
				out.println("		}");
				out.println("	}");
				out.println("	else if (k == 8 && sValue.length == 0)");
				out.println("	{");
				out.println("		" + strFormName + "." + strTempCtl2 + ".value = \"\";");
				out.println("		" + strFormName + "." + strTempCtl2 + ".focus();");
				out.println("	}");
				out.println("}");
			}
			else if (nCaseNumber >= 4)
			{
				out.println("function " + strPrefix + strMainNames[0] + "fieldThirdFocus(sValue)");
				out.println("{");
				out.println("	var k = window.event.keyCode;");
				out.println("	if (k != 46 && k != 8 && k != 37 && k != 39)");
				out.println("	{");
				out.println("		if (k == 13 || sValue.length == 4)");
				out.println("		{");
				out.println("			if (sValue.length ==1)");
				out.println("			{");
				out.println("				" + strFormName + "." + strTempCtl3 + ".value = '000'+sValue;");
				out.println("			}");
				out.println("			else if (sValue.length ==2)");
				out.println("			{");
				out.println("				" + strFormName + "." + strTempCtl3 + ".value = '00'+sValue;");
				out.println("			}");
				out.println("			else if (sValue.length ==3)");
				out.println("			{");
				out.println("				" + strFormName + "." + strTempCtl3 + ".value = '0'+sValue;");
				out.println("			}");
				out.println("			" + strFormName + "." + strTempCtl4 + ".focus();");
				out.println("			" + strFormName + "." + strTempCtl4 + ".select();");
				out.println("		}");
				out.println("	}");
				out.println("	else if (k == 8 && sValue.length == 0)");
				out.println("	{");
				out.println("		" + strFormName + "." + strTempCtl2 + ".value = \"\";");
				out.println("		" + strFormName + "." + strTempCtl2 + ".focus();");
				out.println("	}");
				out.println("}");
				out.println("function " + strPrefix + strMainNames[0] + "fieldFouthFocus(sValue)");
				out.println("{");
				out.println("	var k = window.event.keyCode;");
				out.println("	if (k != 46 && k != 8 && k != 37 && k != 39)");
				out.println("	{");
				out.println("		if (k == 13 )");
				out.println("		{");
				out.println("			" + sOnKeydown);
				out.println("		}");
				out.println("	}");
				out.println("	else if (k == 8 && sValue.length == 0)");
				out.println("	{");
				out.println("		" + strFormName + "." + strTempCtl3 + ".value = \"\";");
				out.println("		" + strFormName + "." + strTempCtl3 + ".focus();");
				out.println("	}");
				out.println("}");
			}
			out.println("function " + strPrefix + strMainNames[0] + "setWholeAcccountNo(sValue1,sValue2,sValue3,sValue4)");
			out.println("{");
			out.println("	if (sValue1 != \"\" && sValue2 != \"\" && sValue3 != \"\" && sValue4  != \"\")");
			out.println("	{");
			out.println("		" + strFormName + "." + strMainNames[0] + ".value = sValue1 + \"-\" + sValue2+ \"-\" + sValue3 + \"-\" + sValue4;");
			out.println("	}");
			out.println("	else if (sValue1 != \"\" && sValue2 != \"\" && sValue3 != \"\")");
			out.println("	{");
			out.println("		" + strFormName + "." + strMainNames[0] + ".value = sValue1 + \"-\" + sValue2 + \"-\" + sValue3;");
			out.println("	}");
			out.println("	else if (sValue1 != \"\" && sValue2 != \"\")");
			out.println("	{");
			out.println("		" + strFormName + "." + strMainNames[0] + ".value = sValue1 + \"-\" + sValue2;");
			out.println("	}");
			out.println("	else if (sValue1 != \"\")");
			out.println("	{");
			out.println("		" + strFormName + "." + strMainNames[0] + ".value = sValue1;");
			out.println("	}");
			out.println("	else if (sValue1 == \"\")");
			out.println("	{");
			out.println("		" + strFormName + "." + strMainNames[0] + ".value = \"\";");
			out.println("	}");
			out.println("}");
			out.println("</script>");
		}
		catch (Exception exp)
		{
			throw exp;
		}
	}
	/**
	 * 检测数组中的控件名称 和 对应的数据库字段 的数目 是否匹配
	 * @param strNames
	 * @param strValues
	 * @param bIsAllowNull
	 * @throws SecException
	 */
	private static void checkValue(String[] strNames, String[] strValues, boolean bIsAllowNull) throws IException
	{
		if (!bIsAllowNull)
		{
			if (strNames != null && strValues != null)
			{
				if (strNames.length != strValues.length)
				{
					throw new IException(ZOOMERRORMSG);
				}
			}
			return;
		}
		if (strNames == null || strValues == null)
		{
			throw new IException(ZOOMERRORMSG);
		}
		if (strNames.length == 0 || strValues.length == 0)
		{
			throw new IException(ZOOMERRORMSG);
		}
		if (strNames.length != strValues.length)
		{
			throw new IException(ZOOMERRORMSG);
		}
	}
	/**
	 * 检测数组中的控件名称 和 对应的数据库字段 的数目 是否匹配
	 * @param strNames 控件名称
	 * @param strFields 对应数据库字段
	 * @param strValues 对应初始值
	 * @param bIsAllowNull 是否允许为空
	 * @throws SecException
	 */
	private static void checkValue(String[] strNames, String[] strFields, String[] strValues, boolean bIsAllowNull) throws IException
	{
		if (!bIsAllowNull)
		{
			if (strNames != null && strFields != null && strValues != null)
			{
				if (strNames.length != strFields.length || strNames.length != strValues.length || strFields.length != strValues.length)
				{
					throw new IException(ZOOMERRORMSG);
				}
			}
		}
		else
		{
			if (strNames == null || strFields == null || strValues == null)
			{
				throw new IException(ZOOMERRORMSG);
			}
			if (strNames.length == 0 || strFields.length == 0 || strValues.length == 0)
			{
				throw new IException(ZOOMERRORMSG);
			}
			if (strNames.length != strFields.length || strNames.length != strValues.length || strFields.length != strValues.length)
			{
				throw new IException(ZOOMERRORMSG);
			}
		}
	}
	/**
	 * 对SQL语句进行处理,如果是完整的查询SQL语句,对所有的"'"字符前加"\".
	 * @param strSQL
	 * @return
	 */
	private static String getSQL(String strSQL)
	{
		StringBuffer sb = new StringBuffer();
		int nIndex = strSQL.indexOf("'");
		while (nIndex != -1)
		{
			String sTemp = strSQL.substring(0, nIndex);
			sb.append(sTemp).append("\\'");
			strSQL = strSQL.substring(nIndex + 1, strSQL.length());
			nIndex = strSQL.indexOf("'");
		}
		return sb.toString();
	}
	/**
	 * 检查是否为查询SQL语句
	 * @param strSQL
	 * @return ture-是,false-否
	 */
	private static boolean isSQL(String strSQL)
	{
		String strTemp = strSQL.toLowerCase();
		int nIndex = strTemp.indexOf("select ");
		if (nIndex == -1)
		{
			return false;
		}
		nIndex = strTemp.indexOf(" from ");
		if (nIndex == -1)
		{
			return false;
		}
		return true;
	}
	/**
	 * 从常量中取值
	 * @param nType, 涉及从常量中取值的放大镜的编号
	 * 目前的放大镜编号：
	 * 1 ：合同类型放大镜--演示用
	 * @param lID, 对应的常量值
	 * @return
	 * @throws Exception
	 */
	private static String getValueFromConstant(int nType, long lID) throws Exception
	{
		String strReturn = "";
		try
		{
			switch (nType)
			{
				case 1 : //合同类型放大镜--演示用
					Log.print("合同类型放大镜");
					//strReturn = Notes.getCodeName(Notes.CODETYPE_CONTRACT_TYPE,lID);
					break;
				case 2 : //资金计划状态
					Log.print("资金计划状态");
					strReturn = TPConstant.PlanVersionStatus.getName(lID);
					break;
				default :
					strReturn = "此值没有从常量中取得，请修改！";
					break;
			}
		}
		catch (Exception ex)
		{
			throw new Exception("发生数据库错误 ，when getValueFromConstant!");
		}
		return strReturn;
	}
	/**
	 *
	 * @param strMainFields
	 * @param strReturnFields
	 * @param strDisplayFields
	 * @param strSQL
	 * @return
	 * @throws Exception
	 */
	public static Vector getCommonSelectList(String[] strMainFields, String[] strReturnFields, String[] strDisplayFields, String strSQL) throws Exception
	{
		CommonSelectInfo info = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		Vector vResult = new Vector();
		try
		{
			conn = Database.getConnection();
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs.next())
			{
				info = new CommonSelectInfo();
				//获取放大镜查询的主字段
				
				Object[] oMainCols = new Object[strMainFields.length];
				for (int i = 0; i < strMainFields.length; i++)
				{
					//判断是否需要从常量类Constant中取得数据。
					
					String strTempMain = rs.getString(strMainFields[i]);					
					if (strTempMain != null && strTempMain.indexOf("FromConstant") >= 0)
					{
						String strSubString1 = strTempMain.substring(strTempMain.indexOf("_") + 1);
						String strTempField = strSubString1.substring(strSubString1.indexOf("_") + 1);
						int nType = (int) Long.parseLong(strSubString1.substring(0, strSubString1.indexOf("_")));

						oMainCols[i] = getValueFromConstant(nType, rs.getLong(strTempField));
					}
					else
					{
						oMainCols[i] = rs.getObject(strMainFields[i]);
						if (oMainCols[i] == null)
						{
							oMainCols[i] = "";
						}
					}
				}
				
				//需要返回主页面的字段
				Object[] oReturnCols = null;
				if (strReturnFields != null)
				{
					oReturnCols = new Object[strReturnFields.length];
					for (int i = 0; i < strReturnFields.length; i++)
					{
						oReturnCols[i] = rs.getObject(strReturnFields[i]);
						//<PK>
					}
				}
				
				
				//需要在放大镜中显示的字段
				Object[] oDisplayCols = new Object[strDisplayFields.length];
				for (int i = 0; i < strDisplayFields.length; i++)
				{
					
					//判断是否需要从常量类Constant中取得数据。
					String strTempDisplay = rs.getString(strDisplayFields[i]);
				
					if (strTempDisplay != null && strTempDisplay.indexOf("FromConstant") >= 0)
					{
						String strSubString1 = strTempDisplay.substring(strTempDisplay.indexOf("_") + 1);
						String strTempField = strSubString1.substring(strSubString1.indexOf("_") + 1);
						int nType = (int) Long.parseLong(strSubString1.substring(0, strSubString1.indexOf("_")));
						oDisplayCols[i] = getValueFromConstant(nType, rs.getLong(strTempField));
					}
					else
					{
						oDisplayCols[i] = rs.getObject(strDisplayFields[i]);
						//<PK>
						if (oDisplayCols[i] == null)
						{
							oDisplayCols[i] = "";
						}
					}
				}				
				info.setMainCols(oMainCols);
				if (strReturnFields != null)
				{
					info.setReturnCols(oReturnCols);
				}
				info.setDisplayCols(oDisplayCols);
				vResult.add(info);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new Exception("发生数据库错误，when getCommonSelectList！");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception _ex)
			{
				System.out.println("关闭数据库连接时发生数据库错误！");
			}
		}
		return vResult.size() > 0 ? vResult : null;
	}
	
	/**Magnifier0080
	 * 摘要放大镜
	 * @param out								jspWriter
	 * @param displayElementName				显示元素名称,返回摘要描述
	 * @param displayElementIniValue			显示元素初始值
	 * 
	 * @param typeId
	 * 摘要类型,以交易类型作为区分,传入交易类型码,获得公共摘要和本类型摘要,"0"为获得全部
	 * 
	 * @param visibleElementProperty			主显示元素的自定义属性
	 * @param nextFocusElementNames				下一个焦点位置
	 * @param showOnly							是否仅作为显示内容(用于复核页面)
	 * @throws Exception
	 */
	public static void createRemarkCtrl(
			JspWriter out,
			String displayElementName,
			String displayElementIniValue,
			
			long typeId,
			
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
		String[] strCtrlElementNames={displayElementName};			//会影响到放大镜显示得值的参数
		String[] strCtrlElementFields={"CODE"};						//影响到放大镜显示值得参数对应得数据库字段
		String[] strCtrlElementValues={""};							//会影响到放大镜的初始值
		
		String[] strCtrlValues = null;
		String[] strCtrlFields = null;
	
		String[] strOperators = null;
		String[] strLogicOperators = null;
		
		if (typeId == 0){				//显示全部
			strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED)};
			strCtrlFields = new String[]{"STATUSID"};
	
			strOperators = new String[]{"like","like","="};
			strLogicOperators = new String[]{"or","and"};
		}
		else{							//只显示全局摘要和针对类型的摘要
			strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),String.valueOf(typeId),"0"};
			strCtrlFields = new String[]{"STATUSID","BUSINESSTYPEID","BUSINESSTYPEID"};
	
			strOperators = new String[]{"like","like","=","=","="};
			strLogicOperators = new String[]{"or","and","and","or"};
		}
		
		String strVisibleElementNames=displayElementName;				//页面显示元素的名称 =;要产生页面元素
		String strVisibleElementFields="DESCRIPTION";				//显示元素对应数据库的字段名.
		String strVisibleElementValues=displayElementIniValue;			//显示元素的初始值?????
		
		String strVisibleElementType="textarea";						//显示元素的类型=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
		
		String[] strHiddenElementNames={"remarkID"};							//主页面隐含域里的id=;要产生页面元素
		String[] strHiddenElementFields={"ID"}; 						//id对应的字段名
		String[] strHiddenElementValues={""};							//返回的默认值
		
		String[] strOtherElementNames={};							//其他和本放大镜关联的元素=;即当返回时也要返回
		String[] strOtherElementFields={};							//其他需要返回值的元素对应的数据库字段
		String[] strOtherElementValues={};	
		
		String[] strListTitleDisplayNames={"审批语编号","审批语描述","业务类型"};	 //放大镜弹出页面的显示名称=;可以有多列
		String[] strListTitleDisplayFields={"CODE","DESCRIPTION","BUSINESSTYPENAME"};//显示名称的对应字段

		String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
			
		String strWindowTitle="资金计划-审批语放大镜";					//放大镜弹出窗口标题
		int intListAnchorPosition=0;								//放大镜页面弹出时默认停留的位置
			
		int intSQL=4;												//数据查询Sql 
		String strTableName = "SEC_VRemarkMagnifier";
	
		boolean blnShowOnly = showOnly;								//只供显示标志位
				
		showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,
						
						strCtrlValues,
						strCtrlFields,
						
						strOperators,
						strLogicOperators,
						
						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,
							
						strVisibleElementType,
						strVisibleElementProperty,
						
						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,
							
						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,
				
						strListTitleDisplayNames,
						strListTitleDisplayFields,
						
						strnextFocusElementNames,
						
						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
	}
	
}