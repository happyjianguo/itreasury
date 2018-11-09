/*
 * CLASS   : Magnifier
 * FUNCTION: 放大镜类
 * VERSION : 1.0.0
 * DATE    : 2003/08/08
 * AUTHOR  : Forest Ming
 * HISTORY :
 *
 */
package com.iss.itreasury.creditrating.util;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.servlet.jsp.JspWriter;
import com.iss.itreasury.dataentity.CommonSelectInfo;
import com.iss.itreasury.ebank.util.OBMagnifier;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
public class CRERTMagnifier implements java.io.Serializable
{
	private static String ZOOMERRORMSG = "放大镜参数设置错误!";
	

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
			CRERTMagnifier.showZoomCtrl(
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
	 * 创建标准等级放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param defaultId 初始ID
	 * @param strDefaultName 初始名称
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 */
	public static void createCreRtStandardRatingCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long defaultId,
		String strDefaultName,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnNameCtrl)
	{
		String strMagnifierName = URLEncoder.encode("标准等级");
		String strMainProperty = " maxlength='30' value='" + strDefaultName + "'";
		String strPrefix = "";
		if (strRtnNameCtrl == null || strRtnNameCtrl.equals(""))
		{
			strRtnNameCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl" };
		String[] strMainFields = { "standardRatingName" };

		String[] strReturnNames = { strCtrlName};
		String[] strReturnFields = { "standardRatingID"};
		String[] strReturnValues = { String.valueOf(defaultId)};
		String[] strDisplayNames = { URLEncoder.encode("标准等级名称"), URLEncoder.encode("标准等级描述")};
		String[] strDisplayFields = { "standardRatingName", "standardRatingDescription"};
		int nIndex = 0;
		String strSQL = "getStandardRatingSQL(" + lOfficeID + ","+ lCurrencyID +"," + strCtrlName + "Ctrl.value)";
		try
		{
			CRERTMagnifier.showZoomCtrl(
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
	 * 创建指标体系放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param defaultId 初始ID
	 * @param strDefaultName 初始名称
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 */
	public static void createCreRtTargetSetupCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long defaultId,
		String strDefaultName,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnNameCtrl)
	{
		String strMagnifierName = URLEncoder.encode("指标体系");
		String strMainProperty = " maxlength='30' value='" + strDefaultName + "'";
		String strPrefix = "";
		if (strRtnNameCtrl == null || strRtnNameCtrl.equals(""))
		{
			strRtnNameCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl" };
		String[] strMainFields = { "targetSetupName" };

		String[] strReturnNames = { strCtrlName};
		String[] strReturnFields = { "targetSetupID"};
		String[] strReturnValues = { String.valueOf(defaultId)};
		String[] strDisplayNames = { URLEncoder.encode("指标体系名称"), URLEncoder.encode("指标体系描述")};
		String[] strDisplayFields = { "targetSetupName", "targetSetupDescription"};
		int nIndex = 0;
		String strSQL = "getTargetSetupSQL(" + lOfficeID + ","+ lCurrencyID +"," + strCtrlName + "Ctrl.value)";
		try
		{
			CRERTMagnifier.showZoomCtrl(
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
	 * 创建评级方案放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param planID 评级方案ID
	 * @param strPlanName 评级方案名称
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 */
	public static void createCreRtPlanCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long planID,
		String strPlanName,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnNameCtrl)
	{
		String strMagnifierName = URLEncoder.encode("评级方案");
		String strMainProperty = " maxlength='30' value='" + strPlanName + "'";
		String strPrefix = "";
		if (strRtnNameCtrl == null || strRtnNameCtrl.equals(""))
		{
			strRtnNameCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl" };
		String[] strMainFields = { "planName" };

		String[] strReturnNames = { strCtrlName};
		String[] strReturnFields = { "planID"};
		String[] strReturnValues = { String.valueOf(planID)};
		String[] strDisplayNames = { URLEncoder.encode("评级方案名称"), URLEncoder.encode("标准等级名称"),URLEncoder.encode("评级指标体系名称")};
		String[] strDisplayFields = { "planName", "standratingName" ,"targetsetupName"};
		int nIndex = 0;
		String strSQL = "getPlanSQL(" + lOfficeID + ","+ planID+"," + strCtrlName + "Ctrl.value)";
		try
		{
			CRERTMagnifier.showZoomCtrl(
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
	 * 创建客户放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lClientID 客户ID(初识值)
	 * @param strClientName 客户名称(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 */
	public static void createClientCtrl1(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lClientID,
		String strClientName,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls)
	{
		String strMagnifierName = URLEncoder.encode("客户");
		String strMainProperty = " maxlength='30' value='" + strClientName + "'";
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl" };
		String[] strMainFields = { "clientName" };
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
		String strSQL = "getClientSQL1(" + lOfficeID + "," + strCtrlName + "Ctrl.value)";
		try
		{
			CRERTMagnifier.showZoomCtrl(
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
	 * 创建客户放大镜(带所属企业类型)
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
	 * @param strRtnEnterpriseTypeCtrl 返回值（客户所属企业类型）对应的控件名
	 */
	public static void createClientCtrl2(
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
		String strRtnClientNameCtrl,
		String strRtnEnterpriseTypeCtrl)
	{
		String strMagnifierName = URLEncoder.encode("客户");
		String strMainProperty = " maxlength='30' value='" + strClientNo + "'";
		String strPrefix = "";
		if (strRtnClientNameCtrl == null || strRtnClientNameCtrl.equals(""))
		{
			strRtnClientNameCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnClientNameCtrl, strRtnEnterpriseTypeCtrl };
		String[] strMainFields = { "clientNo", "clientName", "EnterpriseTypeName" };
		//特殊处理
		long lFromClient = 0;
		if (lClientID > 0)
		{
			lFromClient = 1;
		}
		String[] strReturnNames = { strCtrlName, strCtrlName + "FromClient" };
		String[] strReturnFields = { "ClientID", "FromClient" };
		String[] strReturnValues = { String.valueOf(lClientID), String.valueOf(lFromClient)};
		String[] strDisplayNames = { URLEncoder.encode("客户编号"), URLEncoder.encode("客户名称"), URLEncoder.encode("企业类型")};
		String[] strDisplayFields = { "clientNo", "clientName", "EnterpriseTypeName" };
		int nIndex = 0;
		String strSQL = "getClientSQL2(" + lOfficeID + "," + strCtrlName + "Ctrl.value)";
		try
		{
			CRERTMagnifier.showZoomCtrl(
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
	* 显示普通放大镜
	* @param JspWriter out
	* @param String strMagnifierName 放大镜的名称
	* @param String strFormName 主页面表单名称
	* @param strPrefix strPrefix 控件名称前缀
	* @param String[] strMainNames 放大镜回显栏位值列表
	* @param String[] strMainFields 放大镜回显栏位对应的表格字段
	* @param String[] strReturnNames 放大镜返回值列表(隐含值)
	* @param String[] strReturnFields 放大镜返回值(隐含值)对应的表格字段列表
	* @param String   strReturnInitValues 放大镜回显栏位对应的初始值
	* @param String[] strReturnValues 放大镜返回值(隐含值)对应的初始值
	* @param String[] strDisplayNames 放大镜小窗口显示的栏位名称
	* @param String[] strDisplayFields 放大镜小窗口显示栏位对应的表格字段
	* @param nIndex 确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,如果大于或等于strDisplayNames.length,则没有选择项
	* @param strMainProperty 放大镜栏位属性
	* @param strSQL 放大镜查询SQL语句
	* @param strMatchValue 放大镜要模糊匹配的字段
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
		String strReturnInitValues,
		String[] strReturnValues,
		String[] strDisplayNames,
		String[] strDisplayFields,
		int nIndex,
		String strMainProperty,
		String strSQL,
		String strMatchValue,
		String strNextControls,
		String strTitle,
		String strFirstTD,
		String strSecondTD)
		throws Exception
	{
		//2004-11-18 模糊匹配支持多字段匹配
		String[] strMatchValues = new String[1];
		strMatchValues[0]=strMatchValue;
		
		showZoomCtrl(
			out,
			strMagnifierName,
			strFormName,
			strPrefix,
			strMainNames,
			strMainFields,
			strReturnNames,
			strReturnFields,
			strReturnInitValues,
			strReturnValues,
			strDisplayNames,
			strDisplayFields,
			nIndex,
			strMainProperty,
			strSQL,
			strMatchValues,
			strNextControls,
			strTitle,
			strFirstTD,
			strSecondTD,
			false,
			false);
	}
	/**
	* 显示普通放大镜
	* @param JspWriter out
	* @param String strMagnifierName 放大镜的名称
	* @param String strFormName 主页面表单名称
	* @param strPrefix strPrefix 控件名称前缀
	* @param String[] strMainNames 放大镜回显栏位值列表
	* @param String[] strMainFields 放大镜回显栏位对应的表格字段
	* @param String[] strReturnNames 放大镜返回值列表(隐含值)
	* @param String[] strReturnFields 放大镜返回值(隐含值)对应的表格字段列表
	* @param String   strReturnInitValues 放大镜回显栏位对应的初始值
	* @param String[] strReturnValues 放大镜返回值(隐含值)对应的初始值
	* @param String[] strDisplayNames 放大镜小窗口显示的栏位名称
	* @param String[] strDisplayFields 放大镜小窗口显示栏位对应的表格字段
	* @param nIndex 确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,如果大于或等于strDisplayNames.length,则没有选择项
	* @param strMainProperty 放大镜栏位属性
	* @param strSQL 放大镜查询SQL语句
	* @param strMatchValue 放大镜要模糊匹配的字段
	* @param strNextControls 设置下一个焦点
	* @param strTitle 栏位标题
	* @param strFirstTD 第一个TD的属性
	* @param strSecondTD 第二个TD的属性 
	* @param blnIsOptional 是否是可选项（目前仅对摘要、现金流向放大镜有用）
	* @param blnIsRateCtrl 是否是利率控件
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
	String strReturnInitValues,
	String[] strReturnValues,
	String[] strDisplayNames,
	String[] strDisplayFields,
	int nIndex,
	String strMainProperty,
	String strSQL,
	String[] strMatchValue,
	String strNextControls,
	String strTitle,
	String strFirstTD,
	String strSecondTD,
	boolean blnIsOptional,
	boolean blnIsRateCtrl)
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
		if (strMatchValue == null )//|| strMatchValue.equals(""))
		{
			strMatchValue = new String[1];
			strMatchValue[0] = strMainFields[0];
		}
		else
		{
			if(strMatchValue[0] == null || strMatchValue[0].equals(""))
			{
				strMatchValue[0] = strMainFields[0];
			}
		}

		if (strFirstTD == null)
		{
			strFirstTD = "";
		}
		if (strSecondTD == null)
		{
			strSecondTD = "";
		}

		if (strReturnInitValues == null)
		{
			strReturnInitValues = "";
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
		strParam += "&strMagnifierName=" + URLEncoder.encode(strMagnifierName);
		strParam += "&nIndex=" + nIndex;

		if (!isSQL(strSQL))
		{
			strParam += "&strSQL= select * from ( '+" + strSQL + "+' ) where 1=1 '+get" + strMainNames[0] + "(" + strMainNames[0] + ".value)+'";
		}
		else
		{
			strParam += "&strSQL= select * from ( " + strSQL + " ) where 1=1 '+get" + strMainNames[0] + "(" + strMainNames[0] + ".value)+'";
		}

		if (strNextControls != null && !strNextControls.equals(""))
		{
			strParam += "&strNextControls=" + strNextControls;
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
			strParam += "&strDisplayNames=" + URLEncoder.encode(strDisplayNames[i]);
			strParam += "&strDisplayFields=" + strDisplayFields[i];
		}
		//Log.print("strParam = " + strParam);
		//生成查询按钮的事件字符串
        String strTmp ="";
        
		/*  TOCONFIG—TODELETE  */
        /*
         * 产品化不再区分项目 
         * ninh 
         * 2005-03-24
         */
        
//        if(Env.getProjectName().equals("cpf"))//特殊处理中油
//        {
//            strTmp = "cpfLoan";
//        }
//        else
//        {
//            strTmp = "iTreasury-loan";
//        }

		strTmp = "iTreasury-loan";
		
		/*  TOCONFIG—END  */
        
		String sOnKeydown =
			"if("
				+ strFormName
				+ "."
				+ strMainNames[0]
				+ ".disabled == false) {gnIsSelectCtrl=1;window.open('"
				+ Env.URL_PREFIX
				+ "/"
                + strTmp
                +"/magnifier/ShowMagnifierZoom.jsp?"
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

		int iPos = -1;
		//显示控件
		if (iPos == -1)
		{
			out.println(
				"<td "
					+ strFirstTD
					+ " >"
					+ strTitle
					+ "：&nbsp;"
					+ "<img name=\""
					+ strButtonName
					+ "\" style=\"cursor: hand;\" src='/webloan/image/icon.gif' border=0 onclick=\""
					+ sOnKeydown
					+ "\"></td>");
		}
		else
		{
			out.println("<td " + strFirstTD + " >" + strTitle + ":&nbsp;" + "<img name=\"" + strButtonName + "\" style=\"cursor: hand;\" src='/webloan/image/icon.gif' border=0 ></td>");
		}

		//blnIsOptional,是否可选项（仅对摘要、现金流向放大镜有效）
		if (blnIsOptional == true)
		{
			if (blnIsRateCtrl == true)
			{
				out.println("<td " + strSecondTD + " ><input type=\"text\" name=\"" + strMainNames[0] + "\" value=\"" + strReturnInitValues + "\" class=\"tar\" " + strMainProperty + ">%</td>");
			}
			else
			{
				out.println("<td " + strSecondTD + " ><input type=\"text\" name=\"" + strMainNames[0] + "\" value=\"" + strReturnInitValues + "\" class=\"box\" " + strMainProperty + "></td>");
			}
		}
		else
		{
			if (blnIsRateCtrl == true)
			{
				out.println(
					"<td "
						+ strSecondTD
						+ " ><input type=\"text\" name=\""
						+ strMainNames[0]
						+ "\" value=\""
						+ strReturnInitValues
						+ "\" class=\"tar\" "
						+ strMainProperty
						+ " onKeyDown=\"if(event.keyCode==13) "
						+ sOnKeydown
						+ "\" onKeyUp=\""
						+ sOnKeyUp
						+ "\">%</td>");
			}
			else
			{
				out.println(
					"<td "
						+ strSecondTD
						+ " ><input type=\"text\" name=\""
						+ strMainNames[0]
						+ "\" value=\""
						+ strReturnInitValues
						+ "\" class=\"box\" "
						+ strMainProperty
						+ " onKeyDown=\"if(event.keyCode==13) "
						+ sOnKeydown
						+ "\" onKeyUp=\""
						+ sOnKeyUp
						+ "\"></td>");
			}
		}

		out.println("<script language=\"JavaScript\">");
		out.println("function get" + strMainNames[0] + "(str)");
		out.println("{");
		out.println("   var sql = \"\"; ");
		out.println("   if (str != null && str != \"\") ");
		out.println("   {");
		if (strMatchValue == null)
		{
			out.println(" sql += \"\"; ");	
		}
		else
		{
			if(strMatchValue.length == 1)
			{
				out.println(" sql +=  \" and " + strMatchValue[0] + " like '" + URLEncoder.encode("%") + "\"+str+\"" + URLEncoder.encode("%") + "'\"; ");
			}
			else
			{
				out.println(" sql +=  \" and  ( \";");
				for(int i=0;i<strMatchValue.length;i++)
				{
					if(i==0)
					{
						if(strMatchValue[i] != null || !strMatchValue[i].equals("") )
						{
							out.println(" sql +=  \"  " + strMatchValue[i] + " like '" + URLEncoder.encode("%") + "\"+str+\"" + URLEncoder.encode("%") + "'\"; ");
						}
						else
						{
							out.println(" sql +=  \" 1=1 \"; ");
						}
					}
					else
					{
						if(strMatchValue[i] != null || !strMatchValue[i].equals("") )
						{
							out.println(" sql +=  \" or  " + strMatchValue[i] + " like '" + URLEncoder.encode("%") + "\"+str+\"" + URLEncoder.encode("%") + "'\"; ");
						}
						else
						{
							out.println(" sql +=  \" 1=1 \"; ");
						}
					}
				}
				out.println(" sql +=  \" ) \";");
			}
//			out.println(" sql +=  \" and " + strMatchValue + " like '" + URLEncoder.encode("%") + "\"+str+\"" + URLEncoder.encode("%") + "'\";    ");
		}
		out.println("   }");
		out.println("    ");
		out.println("   return sql;    ");
		out.println("}");
		out.println("</SCRIPT> ");
	}
	catch (Exception exp)
	{
		throw exp;
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
			strTitle);
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
	 * 显示普通放大镜  add by xfma 2008-10-25
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
	 * @param blnIsRateCtrl 是否是利率控件
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
		boolean blnIsRateCtrl)
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
					+ ".disabled == false) {"
					+ strFormName
					+ "."
					+ strMainNames[0]
					+ ".focus();"
					+ " gnIsSelectCtrl=1;window.open('"
					+ Env.URL_PREFIX
					+"/iTreasury-settlement/magnifier/ShowMagnifierZoom.jsp?"
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
				out.println(
					"<td width=140"
						+ strFirstTD
						+ ">"
						+ strTitle
						+ "：&nbsp;"
						+ "<a href=#><img name=\""
						+ strButtonName
						+ "\" src='/webob/graphics/zoom.gif' border=0 onclick=\""
						+ sOnKeydown
						+ "\"></a></td>");
				//image
			}
			else
			{
				out.println("<td" + strFirstTD + ">" + strTitle + "：&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/webob/graphics/zoom.gif' border=0 ></a></td>");
				//image
			}
			//blnIsOptional,是否可选项（仅对摘要、现金流向放大镜有效）
			if (blnIsOptional == true)
			{
				if (strNextControls != null && strNextControls.length > 0)
				{
					strMainProperty = " onfocus=\"nextfield='" + strNextControls[0] + "'\" " + strMainProperty;
				}
				if (blnIsRateCtrl == true)
				{
					out.println("<td" + strSecondTD + "><input type=\"text\" name=\"" + strMainNames[0] + "\" class=\"box\" " + strMainProperty + ">%</td>");
				}
				else
				{
					out.println("<td" + strSecondTD + "><input type=\"text\" name=\"" + strMainNames[0] + "\" class=\"box\" " + strMainProperty + "></td>");
				}
			}
			else
			{
				if (blnIsRateCtrl == true)
				{
					out.println(
						"<td"
							+ strSecondTD
							+ "><input type=\"text\" name=\""
							+ strMainNames[0]
							+ "\" class=\"box\" "
							+ strMainProperty
							+ " onKeyDown=\"if(event.keyCode==13) "
							+ sOnKeydown
							+ "\" onKeyUp=\""
							+ sOnKeyUp
							+ "\">%</td>");
				}
				else
				{
					out.println(
						"<td"
							+ strSecondTD
							+ "><input type=\"text\" name=\""
							+ strMainNames[0]
							+ "\" class=\"box\" "
							+ strMainProperty
							+ " onKeyDown=\"if(event.keyCode==13) "
							+ sOnKeydown
							+ "\" onKeyUp=\""
							+ sOnKeyUp
							+ "\"></td>");
				}
			}
		}
		catch (Exception exp)
		{
			throw exp;
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
			
			//生成查询按钮的事件字符串
			String sOnKeydown =
				"if(checkMagnifierInput("+ strFormName +"."+ strMainNames[0] +",'')){"
					+ "if("
					+ strFormName
					+ "."
					+ strMainNames[0]
					+ ".disabled == false) {gnIsSelectCtrl=1;window.open('"
					+ Env.URL_PREFIX
					+ "/iTreasury-creditrating/magnifier/ShowMagnifierZoom.jsp?"
					+ strParam
					+ "', 'SelectAnything', '"
					+ sFeatures
					+ "', false);}}";
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
				out.println(
					"<td "
						+ strFirstTD
						+ ">"
						+ strTitle
						+ "：&nbsp;"
						+ "<a href=#><img name=\""
						+ strButtonName
						+ "\" src='/itreasury/graphics/icon.gif' border=0 onclick=\""
						+ sOnKeydown
						+ "\"></a></td>");
				//image
			}
			else
			{
				out.println("<td " + strFirstTD + ">" + strTitle + "&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/itreasury/graphics/icon.gif' border=0 ></a></td>");
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
					out.println("<td" + strSecondTD + "><input type=\"text\" name=\"" + strMainNames[0] + "\" class=\"tar\" " + strMainProperty + "> %</td>");
				}
				else
					if (strCtrlType.equals("branch"))
					{
						out.println(
							"<td"
								+ strSecondTD
								+ "><textarea name=\""
								+ strMainNames[0]
								+ "\"  class=\"box\" bgcolor=\"#FF00\"  rows=2 cols=30>"
								+ strMainProperty
								+ "</textarea></td>");
					}
					else
					{
						out.println(
							"<td" + strSecondTD 
								+ "><input type=\"text\" name=\"" 
								+ strMainNames[0] 
								+ "\" class=\"box\" " 
								+ strMainProperty 
								//+ "></td>");
								//modified by qhzhou 2008-02-27 修正摘要放大镜回车键焦点问题
								+ " onKeyDown=\"if(event.keyCode==13) {"
								+ sOnKeydown
								+ "}\" onKeyUp=\""
								+ sOnKeyUp
								+ "\"></td>");
								
					}
			}
			else
			{
				if (strCtrlType.equals("rate"))
				{
					out.println(
						"<td"
							+ strSecondTD
							+ "><input type=\"text\" name=\""
							+ strMainNames[0]
							+ "\" class=\"tar\" "
							+ strMainProperty
							+ " onKeyDown=\"if(event.keyCode==13) {"
							+ sOnKeydown
							+ "}\" onKeyUp=\""
							+ sOnKeyUp
							+ "\"> %</td>");
				}
				else
					if (strCtrlType.equals("branch"))
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
						out.println(
							"<td"
								+ strSecondTD
								+ "><input type=\"text\" name=\""
								+ strMainNames[0]
								+ "\" class=\"box\" "
								+ strMainProperty
								+ " onKeyDown=\"if(event.keyCode==13) "
								+ sOnKeydown
								+ "\" onKeyUp=\""
								+ sOnKeyUp
								+ "\"></td>");
					}
			}
		}
		catch (Exception exp)
		{
			throw exp;
		}
	}
	
	/**
	 * Added by leiyang 2008/03/03 
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
	public static void showNewZoomCtrl(
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
		String strMatchValue,
		String[] strNextControls,
		String strTitle,
		String strFirstTD,
		String strSecondTD,
		boolean blnIsOptional,
		String strCtrlType) throws Exception
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
			if (strMatchValue == null || strMatchValue.equals(""))
			{
				strMatchValue = strMainFields[0];
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
			/*if (!isSQL(strSQL))
			{
				strParam += "&strSQL='+" + strSQL + "+'";
			}
			else
			{
				strParam += "&strSQL=" + getSQL(strSQL);
			}*/
			if (!isSQL(strSQL))
			{
				strParam += "&strSQL= select * from ( '+" + strSQL + "+' ) where 1=1 '+get" + strMainNames[0] + "(" + strMainNames[0] + ".value)+'";
			}
			else
			{
				strParam += "&strSQL= select * from ( " + strSQL + " ) where 1=1 '+get" + strMainNames[0] + "(" + strMainNames[0] + ".value)+'";
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
			
			//生成查询按钮的事件字符串
			String sOnKeydown =
				"if(checkMagnifierInput("+ strFormName +"."+ strMainNames[0] +",'')){"
					+ "if("
					+ strFormName
					+ "."
					+ strMainNames[0]
					+ ".disabled == false) {gnIsSelectCtrl=1;window.open('"
					+ Env.URL_PREFIX
					+ "/iTreasury-creditrating/magnifier/ShowMagnifierZoom.jsp?"
					+ strParam
					+ "', 'SelectAnything', '"
					+ sFeatures
					+ "', false);}}";
			//
			String sOnKeyUp = "";
			if (strReturnNames != null)
			{
				for (int i = 0; i < strReturnNames.length; i++)
				{
					sOnKeyUp += strReturnNames[i] + ".value = -1; ";
				}
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
					out.println("<td" + strSecondTD + "><input type=\"text\" name=\"" + strMainNames[0] + "\" class=\"tar\" " + strMainProperty + "> %</td>");
				}
				else
					if (strCtrlType.equals("branch"))
					{
						out.println(
							"<td"
								+ strSecondTD
								+ "><textarea name=\""
								+ strMainNames[0]
								+ "\"  class=\"box\" bgcolor=\"#FF00\"  rows=2 cols=30>"
								+ strMainProperty
								+ "</textarea></td>");
					}
					else
					{
						out.println(
							"<td" + strSecondTD 
								+ "><input type=\"text\" name=\"" 
								+ strMainNames[0] 
								+ "\" class=\"box\" " 
								+ strMainProperty 
								//+ "></td>");
								//modified by qhzhou 2008-02-27 修正摘要放大镜回车键焦点问题
								+ " onKeyDown=\"if(event.keyCode==13) {"
								+ sOnKeydown
								+ "}\" onKeyUp=\""
								+ sOnKeyUp
								+ "\"></td>");
								
					}
			}
			else
			{
				if (strCtrlType.equals("rate"))
				{
					out.println(
						"<td"
							+ strSecondTD
							+ "><input type=\"text\" name=\""
							+ strMainNames[0]
							+ "\" class=\"tar\" "
							+ strMainProperty
							+ " onKeyDown=\"if(event.keyCode==13) {"
							+ sOnKeydown
							+ "}\" onKeyUp=\""
							+ sOnKeyUp
							+ "\"> %</td>");
				}
				else
					if (strCtrlType.equals("branch"))
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
						out.println(
							"<td"
								+ strSecondTD
								+ "><input type=\"text\" name=\""
								+ strMainNames[0]
								+ "\" class=\"box\" "
								+ strMainProperty
								+ " onKeyDown=\"if(event.keyCode==13) "
								+ sOnKeydown
								+ "\" onKeyUp=\""
								+ sOnKeyUp
								+ "\"></td>");
					}
			}
			
			//int iPos = strMainProperty.toLowerCase().indexOf("disabled");
			int iPos = -1;
			//显示控件
			if (iPos == -1)
			{
				out.println(
					"<td width='150'"
						+ strFirstTD
						+ ">"
						+ strTitle
						+ "<a href=#><img name=\""
						+ strButtonName
						+ "\" src='/websett/image/icon.gif' border=0 onclick=\""
						+ sOnKeydown
						+ "\"></a></td>");
				//image
			}
			else
			{
				out.println("<td " + strFirstTD + ">" + strTitle + "<a href=#><img name=\"" + strButtonName + "\" src='/websett/image/icon.gif' border=0 ></a></td>");
				//image
			}
			
			out.println("<script language=\"JavaScript\">");
			out.println("function get" + strMainNames[0] + "(str)");
			out.println("{");
			out.println("   var sql = \"\"; ");
			out.println("   if (str != null && str != \"\") ");
			out.println("   {");
			if (strMatchValue.equals("0"))
			{
				out.println(" sql = \"\" ");
			}
			else
			{
				out.println(" sql +=  \" and " + strMatchValue + " like '" + URLEncoder.encode("%") + "\"+str+\"" + URLEncoder.encode("%") + "'\";   ");
			}
			out.println("   }");
			out.println("    ");
			out.println("   return sql;    ");
			out.println("}");
			out.println("</script> ");
		}
		catch (Exception exp)
		{
			throw exp;
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
			//检查完毕

			/*修改 by kenny (胡志强) 解决账户号段数的问题*/
			/*
			String strTempCtl1 = strMainNames[0] + "Ctrl1";
			String strTempCtl2 = strMainNames[0] + "Ctrl2";
			String strTempCtl3 = strMainNames[0] + "Ctrl3";
			String strTempCtl4 = strMainNames[0] + "Ctrl4";
			*/
			//取得账户值及定义属性
			int accountField = Config.getInteger(ConfigConstant.GLOBAL_ACCOUNTNO_FIELD,4);
			if (nCaseNumber == 3) {
				accountField = accountField-1;
			} else if (nCaseNumber == 4) {
			}
			String tag = Config.getProperty(ConfigConstant.GLOBAL_ACCOUNTNO_TAG,"");
			String[] accountNo = strAccountNo.split(tag);
			String[] strTempCtl = new String[accountField];
			for (int i=0; i<accountField; i++) {
				strTempCtl[i]=strMainNames[0] + "Ctrl"+(i+1);
			}
			//生成查询按钮的事件字符串
			String sOnKeydown =
				"if("
					+ strFormName
					+ "."
					+ strTempCtl[0]
					+ ".disabled == false) {gnIsSelectCtrl=1;window.open('"
					+ Env.URL_PREFIX
					+ "/iTreasury-creditrating/magnifier/ShowMagnifierZoom.jsp?"
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
					//Modify by leiyang date 2008/06/12
					//sOnKeyUp += "if(" + strClientIDCtrl + "FromClient.value == 0) {" + strClientIDCtrl + ".value = -1;}";
					sOnKeyUp += "if(" + strClientIDCtrl + ".value == 0) {" + strClientIDCtrl + ".value = -1;}";
				}
			}
			//int iPos = strMainProperty.toLowerCase().indexOf("disabled");
			int iPos = -1;
			//显示控件
			if (iPos == -1)
			{
				out.println(
					"<td "
						+ strFirstTD
						+ ">"
						+ strTitle
						+ "：&nbsp;"
						+ "<a href=#><img name=\""
						+ strButtonName
						+ "\" src='/itreasury/graphics/icon.gif' border=0 onclick=\""
						+ sOnKeydown
						+ "\"></a></td>");
				//image
			}
			else
			{
				out.println("<td " + strFirstTD + ">" + strTitle + "：&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/itreasury/graphics/icon.gif' border=0 ></a></td>");
				//image
			}
			/*修改 by kenny (胡志强) (2007-03-21) 解决账户号段数的问题*/
			//组装账户号模糊批匹配的值
			String strTempFunction = "";
			String strTempFunctionForFixed = "";
			for (int i=0; i<accountField; i++) {
				if (i>0) {
					strTempFunctionForFixed = strTempFunctionForFixed + ",";
				}
				strTempFunctionForFixed = strTempFunctionForFixed + strFormName + "." + strTempCtl[i] + ".value";
			}
			strTempFunction = 
				strPrefix
				+ strMainNames[0]
				+ "setWholeAcccountNo("
				+ strTempFunctionForFixed
				+ ");"
				+ sOnKeyUp;
			//界面显示
			int length = Config.getInteger(ConfigConstant.GLOBAL_MAXACCOUNTNO_LENGTH,1);
			int number = Config.getInteger(ConfigConstant.GLOBAL_MAXACCOUNTNO_NUMBER,1);
			out.println("<td " + strSecondTD + ">");
			out.println("<input type=\"hidden\" name=\"" + strMainNames[0] + "\" value=\"" + strAccountNo + "\">");
			for (int i=0; i<accountField; i++) {
				if (i>0) {
					out.println(tag);
				}
				String strSize= "";
				String accountFieldValue = "";
				if (accountNo.length>i) {
					accountFieldValue = accountNo[i];
				}
				if (number == i+1) {
					strSize = "size=\""+length+"\" maxlength=\""+length+"\"";
				} else {
					strSize = "size=\"2\" maxlength=\"2\"";
				}
				
				out.println("<input type=\"text\" "+strSize+" name=\""
				+ strTempCtl[i]
				+ "\" value=\""
				+ accountFieldValue
				+ "\" class=\"box\"  onKeyDown=\"gnIsSelectCtrl=1;"
				+ strPrefix
				+ strMainNames[0]
				+ "fieldFocus"+(i+1)+"("
				+ strFormName
				+ "."
				+ strTempCtl[i]
				+ ".value)\" onKeyUp=\""
				+ strTempFunction
				+ "\">");
			}
			//script函数处理
			out.println("</td>");
			out.println("<script language=\"JavaScript\">");
			for (int i=0; i<accountField; i++) {
				int iLength = 0;
				if (number == i+1) {
					iLength = length;
				} else {
					iLength = 2;
				}
				out.println("function " + strPrefix + strMainNames[0] + "fieldFocus"+(i+1)+"(sValue)");
				out.println("{");
				out.println("	var k = window.event.keyCode;");
				out.println("	if (k != 46 && k != 8 && k != 37 && k != 39)");
				out.println("	{");
				out.println("		var i;");
				out.println("		if (k == 13 || sValue.length == "+iLength+")");
				out.println("		{");
				out.println("			" + strFormName + "." + strTempCtl[i] + ".value = sValue;");
				out.println("			if (sValue.length > 0)");
				out.println("			{");
				out.println("				for (i=0; i<"+iLength+"-sValue.length; i++)");
				out.println("				{");
				out.println("					" + strFormName + "." + strTempCtl[i] + ".value = " + "\"0\" + " + strFormName + "." + strTempCtl[i] + ".value;");
				out.println("				}");
				out.println("			}");
				if (i == accountField-1) {
					out.println("			" + sOnKeydown);
				} else {
					out.println("			" + strFormName + "." + strTempCtl[i+1] + ".focus();");
					out.println("			" + strFormName + "." + strTempCtl[i+1] + ".select();");
				}
				out.println("		}");
				out.println("	}");
				if (i > 0){
					out.println("	else if (k == 8 && sValue.length == 0)");
					out.println("	{");
					out.println("		" + strFormName + "." + strTempCtl[i-1] + ".value = \"\";");
					out.println("		" + strFormName + "." + strTempCtl[i-1] + ".focus();");
					out.println("	}");
				}
				out.println("}");
			}
			//回写账户各段的值
			String param = "";
			String[] where = new String[accountField];
			String[] value = new String[accountField];
			for (int i=0; i<accountField; i++) {
				if (i>0) {
					param = param+",";
				}
				//组装函数的参数
				param = param+"sValue"+(i+1);
				for (int j=0; j<accountField-i; j++) {
					if (j == 0) {
						where[i] = "sValue"+(j+1)+" != \"\"";
						value[i] = "sValue"+(j+1);
					} else {
						//组装判断条件
						where[i] = where[i]+" && sValue"+(j+1)+" != \"\"";
						//组装相关条件下的值
						value[i] = value[i]+" + \"-\" + sValue"+(j+1);
					}
				}
			}
			out.println("function " + strPrefix + strMainNames[0] + "setWholeAcccountNo("+param+")");
			out.println("{");
			String strIf = "";
			for (int i=0; i<accountField; i++) {
				if (i == 0) {
					strIf = "	if (";
				} else {
					strIf = "	} else if (";
				}
				out.println(strIf+where[i]+") {");
				out.println("		" + strFormName + "." + strMainNames[0] + ".value = "+value[i]+";");
			}
			out.println("	} else {");
			out.println("		" + strFormName + "." + strMainNames[0] + ".value = \"\";");
			out.println("	}");
			out.println("}");
			out.println("</script>");

			/*
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

			if (nCaseNumber == 3)
			{
				strTempFunctionForFixed =
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
						+ ".value,'');";
				strTempFunction = strTempFunctionForFixed + sOnKeyUp;
			}
			else
				if (nCaseNumber >= 4)
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
					"<input type=\"text\" size=\"8\" maxlength=\"8\" name=\""
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
			else
				if (nCaseNumber >= 4)
				{
					out.println("-");
					out.println(
						"<input type=\"text\" size=\"8\" maxlength=\"8\" name=\""
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
			else
				if (nCaseNumber >= 4)
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
			*/
		}
		catch (Exception exp)
		{
			throw exp;
		}
	}
	
	/**
	 * added by leiyang 2008/03/03
	 * 
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
	public static void showNewSpecialZoomCtrl(
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
			//检查完毕

			/*修改 by kenny (胡志强) 解决账户号段数的问题*/
			/*
			String strTempCtl1 = strMainNames[0] + "Ctrl1";
			String strTempCtl2 = strMainNames[0] + "Ctrl2";
			String strTempCtl3 = strMainNames[0] + "Ctrl3";
			String strTempCtl4 = strMainNames[0] + "Ctrl4";
			*/
			//取得账户值及定义属性
			int accountField = Config.getInteger(ConfigConstant.GLOBAL_ACCOUNTNO_FIELD,4);
			if (nCaseNumber == 3) {
				accountField = accountField-1;
			} else if (nCaseNumber == 4) {
			}
			String tag = Config.getProperty(ConfigConstant.GLOBAL_ACCOUNTNO_TAG,"");
			String[] accountNo = strAccountNo.split(tag);
			String[] strTempCtl = new String[accountField];
			for (int i=0; i<accountField; i++) {
				strTempCtl[i]=strMainNames[0] + "Ctrl"+(i+1);
			}
			//生成查询按钮的事件字符串
			String sOnKeydown =
				"if("
					+ strFormName
					+ "."
					+ strTempCtl[0]
					+ ".disabled == false) {gnIsSelectCtrl=1;window.open('"
					+ Env.URL_PREFIX
					+ "/iTreasury-settlement/magnifier/ShowMagnifierZoom.jsp?"
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

			/*修改 by kenny (胡志强) (2007-03-21) 解决账户号段数的问题*/
			//组装账户号模糊批匹配的值
			String strTempFunction = "";
			String strTempFunctionForFixed = "";
			for (int i=0; i<accountField; i++) {
				if (i>0) {
					strTempFunctionForFixed = strTempFunctionForFixed + ",";
				}
				strTempFunctionForFixed = strTempFunctionForFixed + strFormName + "." + strTempCtl[i] + ".value";
			}
			strTempFunction = 
				strPrefix
				+ strMainNames[0]
				+ "setWholeAcccountNo("
				+ strTempFunctionForFixed
				+ ");"
				+ sOnKeyUp;
			//界面显示
			int length = Config.getInteger(ConfigConstant.GLOBAL_MAXACCOUNTNO_LENGTH,1);
			int number = Config.getInteger(ConfigConstant.GLOBAL_MAXACCOUNTNO_NUMBER,1);
			out.println("<td " + strSecondTD + ">");
			out.println("<input type=\"hidden\" name=\"" + strMainNames[0] + "\" value=\"" + strAccountNo + "\">");
			for (int i=0; i<accountField; i++) {
				if (i>0) {
					out.println(tag);
				}
				String strSize= "";
				String accountFieldValue = "";
				if (accountNo.length>i) {
					accountFieldValue = accountNo[i];
				}
				if (number == i+1) {
					strSize = "size=\""+length+"\" maxlength=\""+length+"\"";
				} else {
					strSize = "size=\"2\" maxlength=\"2\"";
				}
				
				out.println("<input type=\"text\" "+strSize+" name=\""
				+ strTempCtl[i]
				+ "\" value=\""
				+ accountFieldValue
				+ "\" class=\"box\"  onKeyDown=\"gnIsSelectCtrl=1;"
				+ strPrefix
				+ strMainNames[0]
				+ "fieldFocus"+(i+1)+"("
				+ strFormName
				+ "."
				+ strTempCtl[i]
				+ ".value)\" onKeyUp=\""
				+ strTempFunction
				+ "\">");
			}
			out.println("</td>");
			
			//int iPos = strMainProperty.toLowerCase().indexOf("disabled");
			int iPos = -1;
			//显示控件
			if (iPos == -1)
			{
				out.println(
					"<td "
						+ strFirstTD
						+ ">"
						+ strTitle
						+ "<a href=#><img name=\""
						+ strButtonName
						+ "\" src='/websett/image/icon.gif' border=0 onclick=\""
						+ sOnKeydown
						+ "\"></a></td>");
				//image
			}
			else
			{
				out.println("<td " + strFirstTD + ">" + strTitle + "<a href=#><img name=\"" + strButtonName + "\" src='/websett/image/icon.gif' border=0 ></a></td>");
				//image
			}
			
			//script函数处理
			out.println("<script language=\"JavaScript\">");
			for (int i=0; i<accountField; i++) {
				int iLength = 0;
				if (number == i+1) {
					iLength = length;
				} else {
					iLength = 2;
				}
				out.println("function " + strPrefix + strMainNames[0] + "fieldFocus"+(i+1)+"(sValue)");
				out.println("{");
				out.println("	var k = window.event.keyCode;");
				out.println("	if (k != 46 && k != 8 && k != 37 && k != 39)");
				out.println("	{");
				out.println("		var i;");
				out.println("		if (k == 13 || sValue.length == "+iLength+")");
				out.println("		{");
				out.println("			" + strFormName + "." + strTempCtl[i] + ".value = sValue;");
				out.println("			if (sValue.length > 0)");
				out.println("			{");
				out.println("				for (i=0; i<"+iLength+"-sValue.length; i++)");
				out.println("				{");
				out.println("					" + strFormName + "." + strTempCtl[i] + ".value = " + "\"0\" + " + strFormName + "." + strTempCtl[i] + ".value;");
				out.println("				}");
				out.println("			}");
				if (i == accountField-1) {
					out.println("			" + sOnKeydown);
				} else {
					out.println("			" + strFormName + "." + strTempCtl[i+1] + ".focus();");
					out.println("			" + strFormName + "." + strTempCtl[i+1] + ".select();");
				}
				out.println("		}");
				out.println("	}");
				if (i > 0){
					out.println("	else if (k == 8 && sValue.length == 0)");
					out.println("	{");
					out.println("		" + strFormName + "." + strTempCtl[i-1] + ".value = \"\";");
					out.println("		" + strFormName + "." + strTempCtl[i-1] + ".focus();");
					out.println("	}");
				}
				out.println("}");
			}
			//回写账户各段的值
			String param = "";
			String[] where = new String[accountField];
			String[] value = new String[accountField];
			for (int i=0; i<accountField; i++) {
				if (i>0) {
					param = param+",";
				}
				//组装函数的参数
				param = param+"sValue"+(i+1);
				for (int j=0; j<accountField-i; j++) {
					if (j == 0) {
						where[i] = "sValue"+(j+1)+" != \"\"";
						value[i] = "sValue"+(j+1);
					} else {
						//组装判断条件
						where[i] = where[i]+" && sValue"+(j+1)+" != \"\"";
						//组装相关条件下的值
						value[i] = value[i]+" + \"-\" + sValue"+(j+1);
					}
				}
			}
			out.println("function " + strPrefix + strMainNames[0] + "setWholeAcccountNo("+param+")");
			out.println("{");
			String strIf = "";
			for (int i=0; i<accountField; i++) {
				if (i == 0) {
					strIf = "	if (";
				} else {
					strIf = "	} else if (";
				}
				out.println(strIf+where[i]+") {");
				out.println("		" + strFormName + "." + strMainNames[0] + ".value = "+value[i]+";");
			}
			out.println("	} else {");
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
		if(nIndex<0){
		    return strSQL;
		}
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
	 * 2 ：审批设置--模块名称放大镜
	 * 3 ：审批设置--业务类型放大镜
	 * 4 ：审批设置--操作放大镜
	 * 5 ：审批设置--状态
	 * 6 ：银行票据放大镜（类型描述从常量中得）
	 * 7 ：定期或通知存单号（从定期开立表中取得）
	 * 8 ：
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
				
				case 1 : //审批设置--模块名称放大镜
					Log.print("模块名称放大镜");
					strReturn = Constant.ModuleType.getName(lID);
					break;
				case 2 : //审批设置--业务类型放大镜
					Log.print("业务类型放大镜放大镜");
					strReturn = Constant.ApprovalLoanType.getName(lID);
					break;
				case 3 : //审批设置--操作放大镜
					Log.print("操作放大镜放大镜");
					strReturn = Constant.ApprovalAction.getName(lID);
					break;
				case 4 : //审批设置--状态
					Log.print("状态放大镜");
					strReturn = Constant.ApprovalStatus.getName(lID);
					break;
				default :
					strReturn = "此值没有从常量中取得，请修改！";
					break;
			}
		}
		catch (Exception ex)
		{
			throw new Exception("发生数据库错误！");
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
			
			if(strMainFields!=null && strMainFields.length>0){
				System.out.println("   kkf:"+strMainFields.length);
				
			}else{
				System.out.println("   kkf: 传进来的参数是NULL型的");
			}
			
			conn = Database.getConnection();
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			System.out.println("SQL="+strSQL);
			while (rs.next())
			{
				info = new CommonSelectInfo();
				//获取放大镜查询的主字段
				Object[] oMainCols = new Object[strMainFields.length];
				
				
				for (int i = 0; i < strMainFields.length; i++)
				{
					//判断是否需要从常量类Constant中取得数据。
					//if(strMainFields[i].equals("ClientCode")==true)
					//{
					//	strMainFields[i]="ClientName";
					//}
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
			System.out.println("qlantest====="+e.getMessage());
			e.printStackTrace();
			throw new Exception("发生数据库错误！");
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
	
	
	/**
	 * 显示审批流放大镜
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种
	 * @param out
	 * @param lApprovalID 审批流ID
	 * @param strFormName 主页面表单名称
	 * @param strControlName 主页面控件名称 
	 * @param strPrefix 控件名称前缀
	 * @param strMainProperty 放大镜栏位属性
	 * @param strReturnName 放大镜隐含返回值
	 * @param strSQL 放大镜查询SQL语句
	 * @param strNextControls 设置下一个焦点
	 * @throws Exception
	 */
	public static void CreateApprovalSettingCtrl(long lOfficeID, long lCurrencyID, JspWriter out, long lApprovalID, String strFormName, String strControlName, String strPrefix, String strMainProperty, String strReturnName, String[] strNextControls) throws Exception
	{
		try
		{
			//输出SQL到页面
			out.println("<script language=\"javascript\">");
			out.println("/*====================" + URLEncoder.encode("显示审批流放大镜") + "=================*/");
			out.println("function " + strPrefix + "getApprovalSettingSQL(nOfficeID,lApprovalID,sname)");
			out.println("{");
			out.println("	var sql = \"select ID, sName \";");
			out.println("	sql += \" from loan_approvalSetting \";");
			out.println("	sql += \" where nStatusID = 2 \";");
			out.println(" ");
			out.println("   if (nOfficeID > 0)");
			out.println("	{");
			out.println("		sql += \" and nOfficeID = \" + nOfficeID; ");
			out.println("	}");
			out.println("   if (lApprovalID > 0)");
			out.println("	{");
			out.println("		sql += \" and ID = \" + lApprovalID; ");
			out.println("	}");
			out.println("   if (sname != null && sname != \"\")");
			out.println("   {");
			out.println("       sql += \" and sname like '%\" + sname + \"%'\"");
			out.println("   }");
			out.println("	return sql;");
			out.println("}");
			out.println("</SCRIPT> ");
			String strMagnifierName = "审批流";
			String[] strMainNames = { strControlName };
			String[] strMainFields = { "sName" };
			if(strReturnName.equals(""))
			{
				strReturnName = "hidApprovalID";
			}
			String[] strReturnNames = { strReturnName };
			String[] strReturnFields = { "ID" };
			String[] strReturnValues = { "-1" };
			String[] strDisplayNames = { URLEncoder.encode("审批流编号"), URLEncoder.encode("审批流名称") };
			String[] strDisplayFields = { "ID", "sName" };
			int nIndexOffice = 0;
			String name = DataFormat.toChinese(strFormName+"."+strControlName+".value");
			String strSQL = strPrefix + "getApprovalSettingSQL(" + lOfficeID + ","  + lApprovalID + "," + name + ")";
						
			CRERTMagnifier.showZoomCtrl(
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
				nIndexOffice,
				strMainProperty,
				strSQL,
				strNextControls,
				strMagnifierName,
				"",
				"");
		}
		catch (Exception exp)
		{
			throw exp;
		}
	}
	
}