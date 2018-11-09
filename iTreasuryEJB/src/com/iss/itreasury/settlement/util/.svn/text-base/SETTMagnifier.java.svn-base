/*
 * CLASS   : Magnifier
 * FUNCTION: 放大镜类
 * VERSION : 1.0.0
 * DATE    : 2003/08/08
 * AUTHOR  : Forest Ming
 * HISTORY :
 *
 */
package com.iss.itreasury.settlement.util;
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
public class SETTMagnifier implements java.io.Serializable
{
	private static String ZOOMERRORMSG = "放大镜参数设置错误!";
	/**
	 * 创建利率放大镜(返回利率值)
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lInterestRateID 利率ID(初识值)
	 * @param dInterestRate 利率值(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnInterestRateNameCtrl 返回值（利率名称）对应的控件名
	 */
	public static void createInterestRateCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lInterestRateID,
		double dInterestRate,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnInterestRateNameCtrl)
	{
		String strMagnifierName = URLEncoder.encode("利率");
		//String strMainProperty = " value='" + dInterestRate +"' onblur='adjustAmount(\"" + strFormName +"\",\"" + strCtrlName +"Ctrl\",1,\"" + strChineseCtrl +"\"," + lCurrencyID+")'  onfocus='adjustAmount(\"" + strFormName +"\",\"" + strCtrlName +"Ctrl\",2,\"" + strChineseCtrl +"\"," + lCurrencyID + ")'>";
		String strMainProperty = " maxlength='25' ";
		if (dInterestRate != 0.0)
		{
			strMainProperty = strMainProperty + " value='" + DataFormat.formatRate(dInterestRate) + "'";
		}
		if (strNextControls != null && strNextControls.length > 0)
		{
			strMainProperty =
				strMainProperty + " onblur=\"adjustInterestRate('" + strFormName + "','" + strCtrlName + "Ctrl',1,''," + lCurrencyID + ",'" + strNextControls[0] + "')\"";
			strMainProperty =
				strMainProperty + "  onfocus=\"adjustInterestRate('" + strFormName + "','" + strCtrlName + "Ctrl',2,''," + lCurrencyID + ",'" + strNextControls[0] + "')\"";
				
		}
		else
		{
			strMainProperty = strMainProperty + " onblur=\"adjustInterestRate('" + strFormName + "','" + strCtrlName + "Ctrl',1,''," + lCurrencyID + ")\"";
			strMainProperty = strMainProperty + "  onfocus=\"adjustInterestRate('" + strFormName + "','" + strCtrlName + "Ctrl',2,''," + lCurrencyID + ")\"";
		}
		String strPrefix = "";
		if (strRtnInterestRateNameCtrl == null || strRtnInterestRateNameCtrl.equals(""))
		{
			strRtnInterestRateNameCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnInterestRateNameCtrl };
		String[] strMainFields = { "InterestRate", "InterestRateName" };
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "InterestRateID" };
		String[] strReturnValues = { String.valueOf(lInterestRateID)};
		String[] strDisplayNames = { URLEncoder.encode("利率名称"), URLEncoder.encode("利率值")};
		String[] strDisplayFields = { "InterestRateName", "InterestRate" };
		int nIndex = 1;
		String strSQL = "getInterestRateSQL(" + lOfficeID + "," + lCurrencyID + ","+ strCtrlName + "Ctrl.value)";
		boolean blnIsOptional = false;
		boolean blnIsRateCtrl = true;
		try
		{
			SETTMagnifier.showZoomCtrl(
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
				"rate");
		}
		catch (Exception e)
		{
			Log.print("利率放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	
	/**
	 * 创建贷款利率放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lInterestRateID 利率ID(初识值)
	 * @param strInterestRateNo 利率编号(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnInterestRateNameCtrl 返回值（利率名称）对应的控件名
	 */
	public static void createLoanInterestRateCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lInterestRateID,
		String strInterestRateNo,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnInterestRateNameCtrl,
		String strRtnInterestRateValueCtrl)
	{
		String strMagnifierName = URLEncoder.encode("利率");
		String strMainProperty = " maxlength='30' value='" + strInterestRateNo + "'";
		String strPrefix = "";
		if (strRtnInterestRateNameCtrl == null || strRtnInterestRateNameCtrl.equals(""))
		{
			strRtnInterestRateNameCtrl = "ItIsNotControl";
		}
		if (strRtnInterestRateValueCtrl == null || strRtnInterestRateValueCtrl.equals(""))
		{
			strRtnInterestRateValueCtrl = "ItIsNotControl";
		}
		
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnInterestRateNameCtrl,strRtnInterestRateValueCtrl };
		String[] strMainFields = { "RateCode", "RateName","RateValue" };

		String[] strReturnNames = { strCtrlName};
		String[] strReturnFields = { "RateID" };
		String[] strReturnValues = { String.valueOf(lInterestRateID)};
		String[] strDisplayNames = { URLEncoder.encode("利率编号"), URLEncoder.encode("利率名称"),URLEncoder.encode("利率")};
		String[] strDisplayFields = { "RateCode", "RateName","RateValue" };
		int nIndex = 0;
		String strSQL = "getLoanInterestRateSQL(" + lOfficeID + "," + lCurrencyID + "," + strCtrlName + "Ctrl.value)";
		try
		{
			SETTMagnifier.showZoomCtrl(
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
			Log.print("利率放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	/**
	 * 创建贷款利率放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lInterestRateID 利率ID(初识值)
	 * @param strInterestRate 利率(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnInterestRateNameCtrl 返回值（利率名称）对应的控件名
	 */
	public static void createLoanInterestRateCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lInterestRateID,
		String strInterestRate,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls		
		)
	{
		String strMagnifierName = URLEncoder.encode("利率");
		String strMainProperty = " maxlength='30' value='" + strInterestRate + "'";
		String strPrefix = "";
		
		
		String[] strMainNames = { strCtrlName + "Ctrl"};
		String[] strMainFields = {"RateValue" };

		String[] strReturnNames = { strCtrlName};
		String[] strReturnFields = { "RateID" };
		String[] strReturnValues = { String.valueOf(lInterestRateID)};
		String[] strDisplayNames = { URLEncoder.encode("利率编号"), URLEncoder.encode("利率名称"),URLEncoder.encode("利率")};
		String[] strDisplayFields = { "RateCode", "RateName","RateValue" };
		int nIndex = 0;
		String strSQL = "getLoanInterestRateSQL(" + lOfficeID + "," + lCurrencyID + "," + strCtrlName + "Ctrl.value)";
		boolean blnIsOptional = true;
		try
		{
			SETTMagnifier.showZoomCtrl(
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
					"rate");
		}
		catch (Exception e)
		{
			Log.print("利率放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	/**
	 * 创建贷款利率放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lInterestRateID 利率ID(初识值)
	 * @param strInterestRate 利率(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnInterestRateNameCtrl 返回值（利率名称）对应的控件名
	 */
	public static void createLoanInterestRateCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lInterestRateID,
		String strInterestRate,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strMainProperty
		)
	{
		String strMagnifierName = URLEncoder.encode("利率");
		//String strMainProperty = " maxlength='30' value='" + strInterestRate + "'";
		 strMainProperty = " maxlength='30' value='" + strInterestRate + "' " +strMainProperty ;
		String strPrefix = "";
		
		
		String[] strMainNames = { strCtrlName + "Ctrl"};
		String[] strMainFields = {"RateValue" };

		String[] strReturnNames = { strCtrlName};
		String[] strReturnFields = { "RateID" };
		String[] strReturnValues = { String.valueOf(lInterestRateID)};
		String[] strDisplayNames = { URLEncoder.encode("利率编号"), URLEncoder.encode("利率名称"),URLEncoder.encode("利率")};
		String[] strDisplayFields = { "RateCode", "RateName","RateValue" };
		int nIndex = 0;
		String strSQL = "getLoanInterestRateSQL(" + lOfficeID + "," + lCurrencyID + "," + strCtrlName + "Ctrl.value)";
		boolean blnIsOptional = true;
		try
		{
			SETTMagnifier.showZoomCtrl(
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
					"rate");
		}
		catch (Exception e)
		{
			Log.print("利率放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	/**
	 * 创建银行利率名称放大镜(返回利率名称)
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lInterestRateID 利率ID(初识值)
	 * @param strInterestRateName 利率名称(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnInterestRateCtrl 返回值（利率值）对应的控件名
	 */
	public static void createInterestRateNameCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lInterestRateID,
		String strInterestRateName,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnInterestRateCtrl)
	{
		String strMagnifierName = URLEncoder.encode("利率");
		String strMainProperty = " value='" + strInterestRateName + "'";
		String strPrefix = "";
		if (strRtnInterestRateCtrl == null || strRtnInterestRateCtrl.equals(""))
		{
			strRtnInterestRateCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnInterestRateCtrl };
		String[] strMainFields = { "InterestRateName", "InterestRate" };
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "InterestRateID" };
		String[] strReturnValues = { String.valueOf(lInterestRateID)};
		String[] strDisplayNames = { URLEncoder.encode("利率名称"), URLEncoder.encode("利率值")};
		String[] strDisplayFields = { "InterestRateName", "InterestRate" };
		int nIndex = 0;
		String strSQL = "getInterestRateSQL(" + lOfficeID + "," + lCurrencyID + ","+ strCtrlName + "Ctrl.value )";
		boolean blnIsOptional = false;
		try
		{
			SETTMagnifier.showZoomCtrl(
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
		catch (Exception e)
		{
			Log.print("利率放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	/**
	 * 创建利率计划放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lInterestRatePlanID 利率计划ID(初识值)
	 * @param strInterestRatePlanDesc 利率计划描述(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性 
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 */
	public static void createInterestRatePlanCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lInterestRatePlanID,
		String strInterestRatePlanDesc,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls)
	{
		String strMagnifierName = URLEncoder.encode("利率计划");
		String strMainProperty = " maxlength='25' ";
		strMainProperty = strMainProperty + " value='" + strInterestRatePlanDesc + "'";
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl" };
		String[] strMainFields = { "InterestRatePlanDesc" };
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "InterestRatePlanID" };
		String[] strReturnValues = { String.valueOf(lInterestRatePlanID)};
		String[] strDisplayNames = { URLEncoder.encode("利率计划编号"), URLEncoder.encode("利率计划名称")};
		String[] strDisplayFields = { "InterestRatePlanNo", "InterestRatePlanDesc" };
		int nIndex = 0;
		String strSQL = "getInterestRatePlanSQL(" + lOfficeID + "," + lCurrencyID + "," + strCtrlName + "Ctrl.value)";
		try
		{
			SETTMagnifier.showZoomCtrl(
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
			Log.print("放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	/**
	 * 创建合同放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lClientID 客户ID(初识值)
	 * @param lContractID 合同ID(初识值)
	 * @param strContractNo 合同号(初识值)
	 * @param lTransactionType 交易类型
	 * (查询条件:由SETTConstant.TransactionType定义，-1 查询所有)
	 * @param lMagnifierType 合同放大境类型
	 * (查询条件: 1 发放;2 收回;)
	 * @param strClientCtrl 客户ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 */
	public static void createContractCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lClientID,
		long lContractID,
		String strContractNo,
		long lTransactionType,
		long lMagnifierType,
		String strClientCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls)
	{      
		createContractCtrl(
			out,
			lOfficeID,
			lCurrencyID,
			strFormName,
			strCtrlName,
			strTitle,
			lClientID,
			lContractID,
			strContractNo,
			lTransactionType,
			lMagnifierType,
			strClientCtrl,
			strFirstTD,
			strSecondTD,
			strNextControls,
			"");
	}
	/**
	 * 创建贷款结算合同放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lClientID 客户ID(初识值)
	 * @param lContractID 合同ID(初识值)
	 * @param strContractNo 合同号(初识值)
	 * @param lTransactionType 贷款类型
	 * (查询条件:由LOANConstant.LoanType定义，-1 查询所有)
	 * @param lMagnifierType 合同放大境类型
	 * (查询条件: 1 未发放;2 可发放;)
	 * @param strClientCtrl 客户ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnContractTypeCtrl 返回值（合同类型）对应的控件名
	 */
	public static void createSettLoanContractCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lClientID,
		long lContractID,
		String strContractNo,
		long lLoanType,
		long lMagnifierType,
		String strClientCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnContractTypeCtrl)
	{
		String strMagnifierName = URLEncoder.encode("合同");
		String strMainProperty = " maxlength='30' value='" + strContractNo + "'";
		String strPrefix = "";
		if (strRtnContractTypeCtrl == null || strRtnContractTypeCtrl.equals(""))
		{
			strRtnContractTypeCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnContractTypeCtrl };
		String[] strMainFields = { "ContractCode", "ContractType" };
		String[] strReturnNames = { strCtrlName, strCtrlName + "ClientID" };
		String[] strReturnFields = { "ContractID", "ClientID" };
		String[] strReturnValues = { String.valueOf(lContractID), String.valueOf(lClientID)};
		String[] strDisplayNames = { URLEncoder.encode("合同号"), URLEncoder.encode("客户名称")};
		String[] strDisplayFields = { "ContractCode", "ClientName" };
		int nIndex = 0;
		long[] lContractTypeIDs = null;
		if (lLoanType == -1)
		{
			lContractTypeIDs = LOANConstant.LoanType.getAllCode(lOfficeID,lCurrencyID);
		}
		else
		{
			lContractTypeIDs = new long[] { lLoanType };
		}
		long[] lStatusIDs = null;
		if (lMagnifierType == -1)
		{
			lStatusIDs = LOANConstant.ContractStatus.getAllCode();
		}
		//未发放
		else
			if (lMagnifierType == 1)
			{
				lStatusIDs = new long[] { LOANConstant.ContractStatus.SAVE, LOANConstant.ContractStatus.SUBMIT, LOANConstant.ContractStatus.CHECK,LOANConstant.ContractStatus.NOTACTIVE };
			}
		//已发放
		else
			if (lMagnifierType == 2)
			{
				lStatusIDs = new long[] {  LOANConstant.ContractStatus.ACTIVE, LOANConstant.ContractStatus.EXTEND };
			}
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getContractSQL(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		sbSQL.append("'");
		if (lContractTypeIDs != null && lContractTypeIDs.length > 0)
		{
			for (int i = 0; i < lContractTypeIDs.length; i++)
			{
				sbSQL.append(lContractTypeIDs[i]);
				if (i < lContractTypeIDs.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append("'");
		if (lStatusIDs != null && lStatusIDs.length > 0)
		{
			for (int i = 0; i < lStatusIDs.length; i++)
			{
				sbSQL.append(lStatusIDs[i]);
				if (i < lStatusIDs.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value");
		sbSQL.append(",");
		if (strClientCtrl != null && !strClientCtrl.trim().equals(""))
		{
			sbSQL.append(strClientCtrl);
			sbSQL.append(".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(")");
		//Log.print(sbSQL.toString());
		try
		{
			SETTMagnifier.showZoomCtrl(
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
			Log.print("合同放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	
	/**
	 * 创建贷款结算合同放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述	 * 
	 * @param lContractID 合同ID(初识值)
	 * @param strContractNo 合同号(初识值)	
	 * @param lMagnifierType 合同放大境类型
	 * (查询条件: 1 未发放;2 可发放;)
	 * @param strLoanTypeCtrl 贷款类型对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnContractTypeCtrl 返回值（合同类型）对应的控件名
	 */
	public static void createSettLoanContractCtrl1(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lClientID,
		long lContractID,
		String strContractNo,
		long lLoanType,
		long lMagnifierType,
		String strLoanTypeCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnContractTypeCtrl)
	{
		String strMagnifierName = URLEncoder.encode("合同");
		String strMainProperty = " maxlength='30' value='" + strContractNo + "'";
		String strPrefix = "";
		if (strRtnContractTypeCtrl == null || strRtnContractTypeCtrl.equals(""))
		{
			strRtnContractTypeCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnContractTypeCtrl };
		String[] strMainFields = { "ContractCode", "ContractType" };
		String[] strReturnNames = { strCtrlName, strCtrlName + "ClientID" };
		String[] strReturnFields = { "ContractID", "ClientID" };
		String[] strReturnValues = { String.valueOf(lContractID), String.valueOf(lClientID)};
		String[] strDisplayNames = { URLEncoder.encode("合同号"), URLEncoder.encode("客户名称")};
		String[] strDisplayFields = { "ContractCode", "ClientName" };
		int nIndex = 0;
		long[] lContractTypeIDs = null;
		if (lLoanType == -1)
		{
			lContractTypeIDs = LOANConstant.LoanType.getAllCode(lOfficeID,lCurrencyID);
		}
		else
		{
			lContractTypeIDs = new long[] { lLoanType };
		}
		long[] lStatusIDs = null;
		if (lMagnifierType == -1)
		{
			lStatusIDs = LOANConstant.ContractStatus.getAllCode();
		}
		//未发放
		else
			if (lMagnifierType == 1)
			{
				lStatusIDs = new long[] { LOANConstant.ContractStatus.SAVE, LOANConstant.ContractStatus.SUBMIT, LOANConstant.ContractStatus.CHECK,LOANConstant.ContractStatus.NOTACTIVE };
			}
		//已发放
		else
			if (lMagnifierType == 2)
			{
				lStatusIDs = new long[] {  LOANConstant.ContractStatus.ACTIVE, LOANConstant.ContractStatus.EXTEND };
			}
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getLoanSettContractSQL(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		sbSQL.append("'");
		if (lContractTypeIDs != null && lContractTypeIDs.length > 0)
		{
			for (int i = 0; i < lContractTypeIDs.length; i++)
			{
				sbSQL.append(lContractTypeIDs[i]);
				if (i < lContractTypeIDs.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append("'");
		if (lStatusIDs != null && lStatusIDs.length > 0)
		{
			for (int i = 0; i < lStatusIDs.length; i++)
			{
				sbSQL.append(lStatusIDs[i]);
				if (i < lStatusIDs.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value");
		sbSQL.append(",");
		if (strLoanTypeCtrl != null && !strLoanTypeCtrl.trim().equals(""))
		{
			sbSQL.append(strLoanTypeCtrl);
			sbSQL.append(".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(")");
		Log.print(sbSQL.toString());
		try
		{
			SETTMagnifier.showZoomCtrl(
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
			Log.print("合同放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	/**
	 * 创建合同放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lClientID 客户ID(初识值)
	 * @param lContractID 合同ID(初识值)
	 * @param strContractNo 合同号(初识值)
	 * @param lTransactionType 交易类型
	 * (查询条件:由SETTConstant.TransactionType定义，-1 查询所有)
	 * @param lMagnifierType 合同放大境类型
	 * (查询条件: 1 发放;2 收回;3,转贴现卖出（买断）)
	 * @param strClientCtrl 客户ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnContractTypeCtrl 返回值（合同类型）对应的控件名
	 */
	public static void createContractCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lClientID,
		long lContractID,
		String strContractNo,
		long lTransactionType,
		long lMagnifierType,
		String strClientCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnContractTypeCtrl)
	{
		createContractCtrl(out,
				lOfficeID,
				lCurrencyID,
				strFormName,
				strCtrlName,
				strTitle,
				lClientID,
				lContractID,
				strContractNo,
				lTransactionType,
				lMagnifierType,
				strClientCtrl,
				strFirstTD,
				strSecondTD,
				strNextControls,
				strRtnContractTypeCtrl,
				"",
				"",
				"");
	}
	
	
	/**
	 * 创建合同放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lClientID 客户ID(初识值)
	 * @param lContractID 合同ID(初识值)
	 * @param strContractNo 合同号(初识值)
	 * @param lTransactionType 交易类型
	 * (查询条件:由SETTConstant.TransactionType定义，-1 查询所有)
	 * @param lMagnifierType 合同放大境类型
	 * (查询条件: 1 发放;2 收回;3,转贴现卖出（买断）)
	 * @param strClientCtrl 客户ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnContractTypeCtrl 返回值（合同类型）对应的控件名
	 */
	public static void createContractNoCredenceCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lClientID,
		long lContractID,
		String strContractNo,
		long lTransactionType,
		long lMagnifierType,
		String strClientCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnContractTypeCtrl)
	{
		createContractNoCredenceCtrl(out,
				lOfficeID,
				lCurrencyID,
				strFormName,
				strCtrlName,
				strTitle,
				lClientID,
				lContractID,
				strContractNo,
				lTransactionType,
				lMagnifierType,
				strClientCtrl,
				strFirstTD,
				strSecondTD,
				strNextControls,
				strRtnContractTypeCtrl,
				"",
				"",
				"");
	}
	
	/**
	 * 创建合同放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lClientID 客户ID(初识值)
	 * @param lContractID 合同ID(初识值)
	 * @param strContractNo 合同号(初识值)
	 * @param lTransactionType 交易类型
	 * (查询条件:由SETTConstant.TransactionType定义，-1 查询所有)
	 * @param lMagnifierType 合同放大境类型
	 * (查询条件: 1 发放;2 收回;)
	 * @param strClientCtrl 客户ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnContractTypeCtrl 返回值（合同类型）对应的控件名
	 * @param strRtnCommissionDealWayCtrl 返回值（手续费处理类型）对应的控件名
	 */
	public static void createContractCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lClientID,
		long lContractID,
		String strContractNo,
		long lTransactionType,
		long lMagnifierType,
		String strClientCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnContractTypeCtrl,
		String strRtnCommissionDealWayCtrl)
	{
		createContractCtrl(
			out,
			lOfficeID,
			lCurrencyID,
			strFormName,
			strCtrlName,
			strTitle,
			lClientID,
			lContractID,
			strContractNo,
			lTransactionType,
			lMagnifierType,
			strClientCtrl,
			strFirstTD,
			strSecondTD,
			strNextControls,
			strRtnContractTypeCtrl,
			"");
	}
	/**
	 * 创建合同放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lClientID 客户ID(初识值)
	 * @param lContractID 合同ID(初识值)
	 * @param strContractNo 合同号(初识值)
	 * @param lTransactionType 交易类型
	 * (查询条件:由SETTConstant.TransactionType定义，-1 查询所有)
	 * @param lMagnifierType 合同放大境类型
	 * (查询条件: 1 发放;2 收回;3,转贴现卖出（买断）)
	 * @param strClientCtrl 客户ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnContractTypeCtrl 返回值（合同类型）对应的控件名
	 * @param strRtnClientIDCtrl 返回值（客户ID）对应的控件名(一般是隐含控件)
	 * @param strRtnClientCodeCtrl 返回值（客户编号）对应的控件名
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 */
	public static void createContractCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lClientID,
		long lContractID,
		String strContractNo,
		long lTransactionType,
		long lMagnifierType,
		String strClientCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnContractTypeCtrl,
		String strRtnClientIDCtrl,
		String strRtnClientCodeCtrl,
		String strRtnClientNameCtrl)
	{
		String strMagnifierName = URLEncoder.encode("合同");
		String strMainProperty = " maxlength='30' value='" + strContractNo + "'";
		String strPrefix = "";
		
		if (strRtnContractTypeCtrl == null || strRtnContractTypeCtrl.equals(""))
		{
			strRtnContractTypeCtrl = "ItIsNotControl";
		}
		
		if (strRtnClientIDCtrl == null || strRtnClientIDCtrl.equals(""))
		{
			strRtnClientIDCtrl = "ItIsNotControl";
		}
		if (strRtnClientCodeCtrl == null || strRtnClientCodeCtrl.equals(""))
		{
			strRtnClientCodeCtrl = "ItIsNotControl";
		}
		if (strRtnClientNameCtrl == null || strRtnClientNameCtrl.equals(""))
		{
			strRtnClientNameCtrl = "ItIsNotControl";
		}
		
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnContractTypeCtrl,strRtnClientIDCtrl,strRtnClientCodeCtrl,strRtnClientNameCtrl};
		String[] strMainFields = { "ContractCode", "ContractType","ClientID","ClientCode","ClientName"};
		String[] strReturnNames = { strCtrlName, strCtrlName + "ClientID"};
		String[] strReturnFields = { "ContractID", "ClientID"};
		String[] strReturnValues = { String.valueOf(lContractID), String.valueOf(lClientID)};
		String[] strDisplayNames = null;
		String[] strDisplayFields = null;
		if (lTransactionType == SETTConstant.TransactionType.ACCEPTANCENOTEACCEPTANCE || lTransactionType == SETTConstant.TransactionType.ADVANCEDRECEVICENOTEACCEPTANCE)
		{
		strDisplayNames = new String[]{ URLEncoder.encode("合同号"), URLEncoder.encode("客户名称")};
		strDisplayFields =new String[]{ "ContractCode", "ClientName"};
		}else{
		strDisplayNames = new String[]{ URLEncoder.encode("合同号"), URLEncoder.encode("客户名称"),URLEncoder.encode("贷款类型"),URLEncoder.encode("合同金额"),URLEncoder.encode("到期日")}; //jzw 2010-04-29 修改贷款合同的放大镜，增加显示信息。
		strDisplayFields =new String[]{ "ContractCode", "ClientName","nTypeID","mExamineAmount","dtEndDate" };
		}
		int nIndex = 0;
		long[] lContractTypeIDs = null;
		//信托 ：LOANConstant.LoanType.ZYDQ  ZYZCQ  ZGXEDQ  ZGXEZCQ 
		if (lTransactionType == SETTConstant.TransactionType.TRUSTLOANGRANT || lTransactionType == SETTConstant.TransactionType.TRUSTLOANRECEIVE)
		{
			lContractTypeIDs = new long[] { LOANConstant.LoanType.ZY, LOANConstant.LoanType.ZGXE,  LOANConstant.LoanType.MFXD };
		}
		//银团 ：LOANConstant.LoanType. YT
		else
			if (lTransactionType == SETTConstant.TransactionType.BANKGROUPLOANGRANT || lTransactionType == SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
			{
				lContractTypeIDs = new long[] { LOANConstant.LoanType.YT};
			}
		//委托 : WT  WTTJTH
		else
			if (lTransactionType == SETTConstant.TransactionType.CONSIGNLOANGRANT || lTransactionType == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
			{
				lContractTypeIDs = new long[] { LOANConstant.LoanType.WT};
			}
		//贴现 : TX
		else
			if (lTransactionType == SETTConstant.TransactionType.DISCOUNTGRANT || lTransactionType == SETTConstant.TransactionType.DISCOUNTRECEIVE)
			{
				lContractTypeIDs = new long[] { LOANConstant.LoanType.TX };
			}
		//转贴现
		else
			if (lTransactionType == SETTConstant.TransactionType.TRANSABATEMENT)
			{
				lContractTypeIDs = new long[] { LOANConstant.LoanType.ZTX };
			}
		//商业汇票贴现
	    else
	    	if (lTransactionType == SETTConstant.TransactionType.DISCOUNT_INCOME_OFFBALANCE || lTransactionType == SETTConstant.TransactionType.DISCOUNT_PAYOUT_OFFBALANCE)
	    	{
	    		lContractTypeIDs = new long[] { LOANConstant.LoanType.TX };
	    	}
		//显示委托和信托
		else
			if (lTransactionType == SETTConstant.TransactionType.INTERESTFEEPAYMENT)
			{
				lContractTypeIDs =
					new long[] {
						LOANConstant.LoanType.ZY,
						LOANConstant.LoanType.ZGXE,
						LOANConstant.LoanType.YT,
						LOANConstant.LoanType.WT,
						LOANConstant.LoanType.MFXD};
			}
	  //显示商业承兑汇票 20081022 add by feiye
		else
			if (lTransactionType == SETTConstant.TransactionType.ACCEPTANCENOTEACCEPTANCE || lTransactionType == SETTConstant.TransactionType.ADVANCEDRECEVICENOTEACCEPTANCE)
			{
				lContractTypeIDs = new long[] { LOANConstant.LoanType.SHYCHDHP };
			}
		else
			if( lTransactionType == SETTConstant.TransactionType.OPENMARGIN
					|| lTransactionType == SETTConstant.TransactionType.WITHDRAWMARGIN)
			{
				//保证金开立、支取
				lContractTypeIDs = new long[] { LOANConstant.LoanType.DB };
			}
		else	
			if( lTransactionType == SETTConstant.TransactionType.RECEIVEFINANCE		//add by feiye 2006.5.2
						|| lTransactionType == SETTConstant.TransactionType.RETURNFINANCE)
				{
					//融资租凭收款,还款
					lContractTypeIDs = new long[] { LOANConstant.LoanType.RZZL };	//融资租赁合同
				}
			else
				if (lTransactionType == -1)
				{
					lContractTypeIDs = LOANConstant.LoanType.getAllCode(lOfficeID,lCurrencyID);
				}
		long[] lStatusIDs = null;
		if (lMagnifierType == -1)
		{
			lStatusIDs = LOANConstant.ContractStatus.getAllCode();
		}
		//modify by xwhe 2008-09-18
		else if(lMagnifierType==4)
		{
			lStatusIDs = new long[] { LOANConstant.ContractStatus.NOTACTIVE, LOANConstant.ContractStatus.ACTIVE, LOANConstant.ContractStatus.EXTEND,LOANConstant.ContractStatus.OVERDUE };
		}
		//发放
		else
			if (lMagnifierType == 1)
			{
				lStatusIDs = new long[] { LOANConstant.ContractStatus.NOTACTIVE, LOANConstant.ContractStatus.ACTIVE, LOANConstant.ContractStatus.EXTEND };
			}
		//收回
		else
			if (lMagnifierType == 2 || lMagnifierType == 3)
			{
				lStatusIDs =
					new long[] {
				        LOANConstant.ContractStatus.SAVE,
				        LOANConstant.ContractStatus.SUBMIT,
				        LOANConstant.ContractStatus.CHECK,
						LOANConstant.ContractStatus.NOTACTIVE,
						LOANConstant.ContractStatus.ACTIVE,
						LOANConstant.ContractStatus.EXTEND,
						LOANConstant.ContractStatus.OVERDUE,
						LOANConstant.ContractStatus.DELAYDEBT,
						LOANConstant.ContractStatus.BADDEBT };
			}
		else 
			if( lMagnifierType == 5)
			{
				lStatusIDs = new long[]{LOANConstant.ContractStatus.ACTIVE};
			}
		else
			if (lMagnifierType == 8)
			{
				lStatusIDs = new long[] { 
						LOANConstant.ContractStatus.NOTACTIVE, 
						LOANConstant.ContractStatus.ACTIVE, 
						LOANConstant.ContractStatus.EXTEND, 
						LOANConstant.ContractStatus.OVERDUE, 
						LOANConstant.ContractStatus.DELAYDEBT, 
						LOANConstant.ContractStatus.BADDEBT, 
						LOANConstant.ContractStatus.FINISH, 
						};
			}		
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getContractSQL(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		sbSQL.append("'");
		if (lContractTypeIDs != null && lContractTypeIDs.length > 0)
		{
			for (int i = 0; i < lContractTypeIDs.length; i++)
			{
				sbSQL.append(lContractTypeIDs[i]);
				if (i < lContractTypeIDs.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append("'");
		if (lStatusIDs != null && lStatusIDs.length > 0)
		{
			for (int i = 0; i < lStatusIDs.length; i++)
			{
				sbSQL.append(lStatusIDs[i]);
				if (i < lStatusIDs.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value");
		sbSQL.append(",");
		if (strClientCtrl != null && !strClientCtrl.trim().equals(""))
		{
			sbSQL.append(strClientCtrl);
			sbSQL.append(".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		//转贴现卖出（买断）
		if (lMagnifierType == 3)
		{
			sbSQL.append("," + LOANConstant.TransDiscountInOrOut.OUT + "," + LOANConstant.TransDiscountType.BUYBREAK + ")");
		}
		else
		{
			sbSQL.append(")");
		}
		//Log.print(sbSQL.toString());
		try
		{
			SETTMagnifier.showZoomCtrl(
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
			Log.print("合同放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	
	
	/**
	 * 创建合同放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lClientID 客户ID(初识值)
	 * @param lContractID 合同ID(初识值)
	 * @param strContractNo 合同号(初识值)
	 * @param lTransactionType 交易类型
	 * (查询条件:由SETTConstant.TransactionType定义，-1 查询所有)
	 * @param lMagnifierType 合同放大境类型
	 * (查询条件: 1 发放;2 收回;3,转贴现卖出（买断）)
	 * @param strClientCtrl 客户ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnContractTypeCtrl 返回值（合同类型）对应的控件名
	 * @param strRtnClientIDCtrl 返回值（客户ID）对应的控件名(一般是隐含控件)
	 * @param strRtnClientCodeCtrl 返回值（客户编号）对应的控件名
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 */
	public static void createContractNoCredenceCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lClientID,
		long lContractID,
		String strContractNo,
		long lTransactionType,
		long lMagnifierType,
		String strClientCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnContractTypeCtrl,
		String strRtnClientIDCtrl,
		String strRtnClientCodeCtrl,
		String strRtnClientNameCtrl)
	{
		String strMagnifierName = URLEncoder.encode("合同");
		String strMainProperty = " maxlength='30' value='" + strContractNo + "'";
		String strPrefix = "";
		
		if (strRtnContractTypeCtrl == null || strRtnContractTypeCtrl.equals(""))
		{
			strRtnContractTypeCtrl = "ItIsNotControl";
		}
		
		if (strRtnClientIDCtrl == null || strRtnClientIDCtrl.equals(""))
		{
			strRtnClientIDCtrl = "ItIsNotControl";
		}
		if (strRtnClientCodeCtrl == null || strRtnClientCodeCtrl.equals(""))
		{
			strRtnClientCodeCtrl = "ItIsNotControl";
		}
		if (strRtnClientNameCtrl == null || strRtnClientNameCtrl.equals(""))
		{
			strRtnClientNameCtrl = "ItIsNotControl";
		}
		
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnContractTypeCtrl,strRtnClientIDCtrl,strRtnClientCodeCtrl,strRtnClientNameCtrl};
		String[] strMainFields = { "ContractCode", "ContractType","ClientID","ClientCode","ClientName"};
		String[] strReturnNames = { strCtrlName, strCtrlName + "ClientID"};
		String[] strReturnFields = { "ContractID", "ClientID"};
		String[] strReturnValues = { String.valueOf(lContractID), String.valueOf(lClientID)};
		String[] strDisplayNames = { URLEncoder.encode("合同号"), URLEncoder.encode("客户名称"),URLEncoder.encode("贷款类型"),URLEncoder.encode("合同金额"),URLEncoder.encode("到期日")}; //jzw 2010-04-29 修改贷款合同的放大镜，增加显示信息。
		String[] strDisplayFields = { "ContractCode", "ClientName","nTypeID","mExamineAmount","dtEndDate" };
		int nIndex = 0;
		long[] lContractTypeIDs = null;
		//信托 ：LOANConstant.LoanType.ZYDQ  ZYZCQ  ZGXEDQ  ZGXEZCQ 
		if (lTransactionType == SETTConstant.TransactionType.TRUSTLOANGRANT || lTransactionType == SETTConstant.TransactionType.TRUSTLOANRECEIVE)
		{
			lContractTypeIDs = new long[] { LOANConstant.LoanType.ZY, LOANConstant.LoanType.ZGXE,  LOANConstant.LoanType.MFXD };
		}
		//银团 ：LOANConstant.LoanType. YT
		else
			if (lTransactionType == SETTConstant.TransactionType.BANKGROUPLOANGRANT || lTransactionType == SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
			{
				lContractTypeIDs = new long[] { LOANConstant.LoanType.YT};
			}
		//委托 : WT  WTTJTH
		else
			if (lTransactionType == SETTConstant.TransactionType.CONSIGNLOANGRANT || lTransactionType == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
			{
				lContractTypeIDs = new long[] { LOANConstant.LoanType.WT};
			}
		//贴现 : TX
		else
			if (lTransactionType == SETTConstant.TransactionType.DISCOUNTGRANT || lTransactionType == SETTConstant.TransactionType.DISCOUNTRECEIVE)
			{
				lContractTypeIDs = new long[] { LOANConstant.LoanType.TX };
			}
		//转贴现
		else
			if (lTransactionType == SETTConstant.TransactionType.TRANSABATEMENT)
			{
				lContractTypeIDs = new long[] { LOANConstant.LoanType.ZTX };
			}
		//商业汇票贴现
	    else
	    	if (lTransactionType == SETTConstant.TransactionType.DISCOUNT_INCOME_OFFBALANCE || lTransactionType == SETTConstant.TransactionType.DISCOUNT_PAYOUT_OFFBALANCE)
	    	{
	    		lContractTypeIDs = new long[] { LOANConstant.LoanType.TX };
	    	}
		//显示委托和信托
		else
			if (lTransactionType == SETTConstant.TransactionType.INTERESTFEEPAYMENT)
			{
				lContractTypeIDs =
					new long[] {
						LOANConstant.LoanType.ZY,
						LOANConstant.LoanType.ZGXE,
						LOANConstant.LoanType.YT,
						LOANConstant.LoanType.WT,
						LOANConstant.LoanType.MFXD};
			}
		else
			if( lTransactionType == SETTConstant.TransactionType.OPENMARGIN
					|| lTransactionType == SETTConstant.TransactionType.WITHDRAWMARGIN)
			{
				//保证金开立、支取
				lContractTypeIDs = new long[] { LOANConstant.LoanType.DB };
			}
		else	
			if( lTransactionType == SETTConstant.TransactionType.RECEIVEFINANCE		//add by feiye 2006.5.2
						|| lTransactionType == SETTConstant.TransactionType.RETURNFINANCE)
				{
					//融资租凭收款,还款
					lContractTypeIDs = new long[] { LOANConstant.LoanType.RZZL };	//融资租赁合同
				}
			else
				if (lTransactionType == -1)
				{
					lContractTypeIDs = LOANConstant.LoanType.getAllCode(lOfficeID,lCurrencyID);
				}
		long[] lStatusIDs = null;
		if (lMagnifierType == -1)
		{
			lStatusIDs = LOANConstant.ContractStatus.getAllCode();
		}
		//modify by xwhe 2008-09-18
		else if(lMagnifierType==4)
		{
			lStatusIDs = new long[] { LOANConstant.ContractStatus.NOTACTIVE, LOANConstant.ContractStatus.ACTIVE, LOANConstant.ContractStatus.EXTEND,LOANConstant.ContractStatus.OVERDUE };
		}
		//发放
		else
			if (lMagnifierType == 1)
			{
				lStatusIDs = new long[] { LOANConstant.ContractStatus.NOTACTIVE, LOANConstant.ContractStatus.ACTIVE, LOANConstant.ContractStatus.EXTEND };
			}
		//收回
		else
			if (lMagnifierType == 2 || lMagnifierType == 3)
			{
				lStatusIDs =
					new long[] {
				        LOANConstant.ContractStatus.SAVE,
				        LOANConstant.ContractStatus.SUBMIT,
				        LOANConstant.ContractStatus.CHECK,
						LOANConstant.ContractStatus.NOTACTIVE,
						LOANConstant.ContractStatus.ACTIVE,
						LOANConstant.ContractStatus.EXTEND,
						LOANConstant.ContractStatus.OVERDUE,
						LOANConstant.ContractStatus.DELAYDEBT,
						LOANConstant.ContractStatus.BADDEBT };
			}
		else 
			if( lMagnifierType == 5)
			{
				lStatusIDs = new long[]{LOANConstant.ContractStatus.ACTIVE};
			}
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getContractNoCredenceSQL(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		sbSQL.append("'");
		if (lContractTypeIDs != null && lContractTypeIDs.length > 0)
		{
			for (int i = 0; i < lContractTypeIDs.length; i++)
			{
				sbSQL.append(lContractTypeIDs[i]);
				if (i < lContractTypeIDs.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append("'");
		if (lStatusIDs != null && lStatusIDs.length > 0)
		{
			for (int i = 0; i < lStatusIDs.length; i++)
			{
				sbSQL.append(lStatusIDs[i]);
				if (i < lStatusIDs.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value");
		sbSQL.append(",");
		if (strClientCtrl != null && !strClientCtrl.trim().equals(""))
		{
			sbSQL.append(strClientCtrl);
			sbSQL.append(".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		//转贴现卖出（买断）
		if (lMagnifierType == 3)
		{
			sbSQL.append("," + LOANConstant.TransDiscountInOrOut.OUT + "," + LOANConstant.TransDiscountType.BUYBREAK + ")");
		}
		else
		{
			sbSQL.append(")");
		}
		//Log.print(sbSQL.toString());
		try
		{
			SETTMagnifier.showZoomCtrl(
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
			Log.print("合同放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	
	/**
	 * 创建提前还款通知单放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lPayFormID 放款通知单ID(初识值)
	 * @param lAheadPayFormID 提前还款通知单ID(初识值)
	 * @param strAheadPayFormNo 提前还款通知单号(初识值)
	 * @param lAheadPayFormTypeID 提前还款通知单类型(查询条件:1,信托；2，委托)
	 * @param lStatusID 提前还款通知单状态(内部状态：0，显示全部；1，业务处理使用；2，业务复核使用）
	 * @param strPayFormIDCtrl 放款通知单ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnAmountCtrl 返回值（还款金额）对应的控件名
	 * @param strRtnClientNoCtrl 返回值（客户编号）对应的控件名
	 * @param strRtnContractNoCtrl 返回值（合同号）对应的控件名
	 * @param strRtnPayFormNoCtrl 返回值（放款通知单号）对应的控件名
	 */
	public static void createAheadPayFormCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lPayFormID,
		long lAheadPayFormID,
		String strAheadPayFormNo,
		long lAheadPayFormTypeID,
		long lStatusID,
		String strPayFormIDCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnAmountCtrl,
		String strRtnClientNoCtrl,
		String strRtnContractNoCtrl,
		String strRtnPayFormNoCtrl)
	{
		String strMagnifierName = URLEncoder.encode("还款通知单");
		String strMainProperty = " maxlength='30' value='" + strAheadPayFormNo + "'";
		String strPrefix = "";
		if (strRtnAmountCtrl == null || strRtnAmountCtrl.equals(""))
		{
			strRtnAmountCtrl = "ItIsNotControl";
		}
		if (strRtnContractNoCtrl == null || strRtnContractNoCtrl.equals(""))
		{
			strRtnContractNoCtrl = "ItIsNotControl";
		}
		if (strRtnClientNoCtrl == null || strRtnClientNoCtrl.equals(""))
		{
			strRtnClientNoCtrl = "ItIsNotControl";
		}
		if (strRtnPayFormNoCtrl == null || strRtnPayFormNoCtrl.equals(""))
		{
			strRtnPayFormNoCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnAmountCtrl, strRtnClientNoCtrl, strRtnContractNoCtrl, strRtnPayFormNoCtrl };
		String[] strMainFields = { "AheadRepayFormNo", "Amount", "ClientNo", "ContractNo", "PayFormNo" };
		String[] strReturnNames = { strCtrlName, strCtrlName + "PayFormID" };
		String[] strReturnFields = { "AheadRepayFormID", "PayFormID" };
		String[] strReturnValues = { String.valueOf(lAheadPayFormID), String.valueOf(lPayFormID)};
		String[] strDisplayNames = { URLEncoder.encode("还款通知单号"), URLEncoder.encode("合同号"), URLEncoder.encode("放款通知单号"), URLEncoder.encode("还款金额")};
		String[] strDisplayFields = { "AheadRepayFormNo", "ContractNo", "PayFormNo", "Amount" };
		int nIndex = 0;
		long[] lContractTypeIDs = null;
		//信托 ：LOANConstant.LoanType.ZYDQ  ZYZCQ  ZGXEDQ  ZGXEZCQ  YT
		if (lAheadPayFormTypeID == 1)
		{
			lContractTypeIDs =
				new long[] {
					LOANConstant.LoanType.ZY,
					LOANConstant.LoanType.ZGXE,
					LOANConstant.LoanType.YT,
					LOANConstant.LoanType.MFXD};
		}
		//委托 : WT  WTTJTH
		else
			if (lAheadPayFormTypeID == 2)
			{
				lContractTypeIDs = new long[] { LOANConstant.LoanType.WT };
			}
		long[] lStatusIDArray = null;
		//获得状态数组
		if (lStatusID == 0)
		{
			lStatusIDArray = new long[] { LOANConstant.AheadRepayStatus.CHECK, LOANConstant.AheadRepayStatus.USED };
		}
		else
			if (lStatusID == 1)
			{
				lStatusIDArray = new long[] { LOANConstant.AheadRepayStatus.CHECK };
			}
			else
				if (lStatusID == 2)
				{
					lStatusIDArray = new long[] { LOANConstant.AheadRepayStatus.USED };
				}
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getAheadPayFormSQL(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		if (strPayFormIDCtrl != null && !strPayFormIDCtrl.trim().equals(""))
		{
			sbSQL.append(strPayFormIDCtrl + ".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(",");
		sbSQL.append("'");
		if (lContractTypeIDs != null && lContractTypeIDs.length > 0)
		{
			for (int i = 0; i < lContractTypeIDs.length; i++)
			{
				sbSQL.append(lContractTypeIDs[i]);
				if (i < lContractTypeIDs.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append("'");
		if (lStatusIDArray != null && lStatusIDArray.length > 0)
		{
			for (int i = 0; i < lStatusIDArray.length; i++)
			{
				sbSQL.append(lStatusIDArray[i]);
				if (i < lStatusIDArray.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value)");
		//Log.print(sbSQL.toString());
		try
		{
			SETTMagnifier.showZoomCtrl(
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
	 * 创建提前还款通知单放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lPayFormID 放款通知单ID(初识值)
	 * @param lAheadPayFormID 提前还款通知单ID(初识值)
	 * @param strAheadPayFormNo 提前还款通知单号(初识值)
	 * @param lAheadPayFormTypeID 提前还款通知单类型(查询条件:1,信托；2，委托)
	 * @param lStatusID 提前还款通知单状态(内部状态：0，显示全部；1，业务处理使用；2，业务复核使用）
	 * @param strPayFormIDCtrl 放款通知单ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnAmountCtrl 返回值（还款金额）对应的控件名
	 * @param strRtnClientNoCtrl 返回值（客户编号）对应的控件名
	 * @param strRtnContractNoCtrl 返回值（合同号）对应的控件名
	 * @param strRtnPayFormNoCtrl 返回值（放款通知单号）对应的控件名
	 */
	public static void createAheadPayAllCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lPayFormID,
		long lAheadPayFormID,
		String strAheadPayFormNo,
		long lAheadPayFormTypeID,
		long lStatusID,
		String strPayFormIDCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnAmountCtrl,
		String strRtnClientNoCtrl,
		String strRtnContractNoCtrl,
		String strRtnPayFormNoCtrl,
		String strRtnContractID,
		String strRtnContractClientID,
		String strRtnClientID,
		String strRtnPayFormID,
		String strRtnPayFormContractID,
		String strRtnBalance,
		String strRtnDateStart)
	{
		String strMagnifierName = URLEncoder.encode("还款通知单");
		String strMainProperty = " maxlength='30' value='" + strAheadPayFormNo + "'";
		String strPrefix = "";
		if (strRtnAmountCtrl == null || strRtnAmountCtrl.equals(""))
		{
			strRtnAmountCtrl = "ItIsNotControl";
		}
		if (strRtnContractNoCtrl == null || strRtnContractNoCtrl.equals(""))
		{
			strRtnContractNoCtrl = "ItIsNotControl";
		}
		if (strRtnClientNoCtrl == null || strRtnClientNoCtrl.equals(""))
		{
			strRtnClientNoCtrl = "ItIsNotControl";
		}
		if (strRtnPayFormNoCtrl == null || strRtnPayFormNoCtrl.equals(""))
		{
			strRtnPayFormNoCtrl = "ItIsNotControl";
		}
		
		if (strRtnContractID == null || strRtnContractID.equals(""))
		{
			strRtnContractID = "ItIsNotControl";
		}
		if (strRtnContractClientID == null || strRtnContractClientID.equals(""))
		{
			strRtnContractClientID = "ItIsNotControl";
		}
		if (strRtnClientID == null || strRtnClientID.equals(""))
		{
			strRtnClientID = "ItIsNotControl";
		}
		if (strRtnPayFormID == null || strRtnPayFormID.equals(""))
		{
			strRtnPayFormID = "ItIsNotControl";
		}
		if (strRtnPayFormContractID == null || strRtnPayFormContractID.equals(""))
		{
			strRtnPayFormContractID = "ItIsNotControl";
		}
		if (strRtnBalance == null || strRtnBalance.equals(""))
		{
			strRtnBalance = "ItIsNotControl";
		}
		if (strRtnDateStart == null || strRtnDateStart.equals(""))
		{
			strRtnDateStart = "ItIsNotControl";
		}
		
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnAmountCtrl, strRtnClientNoCtrl, strRtnContractNoCtrl, strRtnPayFormNoCtrl , strRtnContractID , strRtnClientID , strRtnPayFormID , strRtnContractClientID , strRtnPayFormContractID , strRtnBalance , strRtnDateStart };
		String[] strMainFields = { "AheadRepayFormNo", "Amount", "ClientNo", "ContractNo", "PayFormNo" , "ContractID" , "ClientID" , "PayFormID" , "ClientID" , "ContractID" , "Balance" , "dtStart" };
		String[] strReturnNames = { strCtrlName, strCtrlName + "PayFormID" };
		String[] strReturnFields = { "AheadRepayFormID", "PayFormID" };
		String[] strReturnValues = { String.valueOf(lAheadPayFormID), String.valueOf(lPayFormID) };
		String[] strDisplayNames = { URLEncoder.encode("还款通知单号"), URLEncoder.encode("合同号"), URLEncoder.encode("放款通知单号"), URLEncoder.encode("还款金额")};
		String[] strDisplayFields = { "AheadRepayFormNo", "ContractNo", "PayFormNo", "Amount" };
		int nIndex = 0;
		long[] lContractTypeIDs = null;
		//信托 ：LOANConstant.LoanType.ZYDQ  ZYZCQ  ZGXEDQ  ZGXEZCQ  YT
		if (lAheadPayFormTypeID == 1)
		{
			lContractTypeIDs =
				new long[] {
					LOANConstant.LoanType.ZY,
					LOANConstant.LoanType.ZGXE,
					LOANConstant.LoanType.YT,
					LOANConstant.LoanType.MFXD};
		}
		//委托 : WT  WTTJTH
		else
			if (lAheadPayFormTypeID == 2)
			{
				lContractTypeIDs = new long[] { LOANConstant.LoanType.WT };
			}
		long[] lStatusIDArray = null;
		//获得状态数组
		if (lStatusID == 0)
		{
			lStatusIDArray = new long[] { LOANConstant.AheadRepayStatus.CHECK, LOANConstant.AheadRepayStatus.USED };
		}
		else
			if (lStatusID == 1)
			{
				lStatusIDArray = new long[] { LOANConstant.AheadRepayStatus.CHECK };
			}
			else
				if (lStatusID == 2)
				{
					lStatusIDArray = new long[] { LOANConstant.AheadRepayStatus.USED };
				}
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getAheadPayAllSQL(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		if (strPayFormIDCtrl != null && !strPayFormIDCtrl.trim().equals(""))
		{
			sbSQL.append(strPayFormIDCtrl + ".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(",");
		sbSQL.append("'");
		if (lContractTypeIDs != null && lContractTypeIDs.length > 0)
		{
			for (int i = 0; i < lContractTypeIDs.length; i++)
			{
				sbSQL.append(lContractTypeIDs[i]);
				if (i < lContractTypeIDs.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append("'");
		if (lStatusIDArray != null && lStatusIDArray.length > 0)
		{
			for (int i = 0; i < lStatusIDArray.length; i++)
			{
				sbSQL.append(lStatusIDArray[i]);
				if (i < lStatusIDArray.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value)");
		//Log.print(sbSQL.toString());
		try
		{
			SETTMagnifier.showZoomCtrl(
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
	 * 创建放款通知单放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lContractID 合同ID(初识值)
	 * @param lPayFormID 放款通知单ID(初识值)
	 * @param strPayFormNo 放款通知单号(初识值)
	 * @param lPayFormTypeID 放款通知单类型(查询条件:1,信托；2，委托)
	 * @param lStatusID 放款通知单状态(内部状态：
	 * 		0，显示全部；
	 * 		1，信托/委托发放——业务处理使用；
	 * 		2，信托/委托发放——业务复核使用；
	 * 		3，信托/委托收回——业务处理使用；
	 * 		4，信托/委托收回——业务复核使用。）
	 * @param strContractCtrl 合同ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnStartDateCtrl 返回值（起始日期）对应的控件名
	 */
	public static void createPayFormCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lContractID,
		long lPayFormID,
		String strPayFormNo,
		long lPayFormTypeID,
		long lStatusID,
		String strContractCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnStartDateCtrl)
	{
		createPayFormCtrl(
			out,
			lOfficeID,
			lCurrencyID,
			strFormName,
			strCtrlName,
			strTitle,
			lContractID,
			lPayFormID,
			strPayFormNo,
			lPayFormTypeID,
			lStatusID,
			strContractCtrl,
			strFirstTD,
			strSecondTD,
			strNextControls,
			strRtnStartDateCtrl,
			"",
			"",
			"");
	}
	/**
	 * 创建放款通知单放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lContractID 合同ID(初识值)
	 * @param lPayFormID 放款通知单ID(初识值)
	 * @param strPayFormNo 放款通知单号(初识值)
	 * @param lPayFormTypeID 放款通知单类型
	 * (查询条件:1,信托；2，委托；3，银团；4,全部(包括贴现);0,全部)
	 * @param lTypeID 放款通知单使用条件(内部状态：
	 * 		0，显示全部；
	 * 		1，信托/委托/银团发放——业务处理使用；
	 * 		2，信托/委托/银团发放——业务复核使用；
	 * 		3，信托/委托收回——业务处理使用；
	 * 		4，信托/委托收回——业务复核使用；
	 * 		5，交易费用/特殊业务——业务处理使用；
	 * 		6，交易费用/特殊业务——业务复核使用；
	 * 		7，银团贷款收回——业务处理使用；
	 * 		8，银团贷款收回——业务复核使用；）
	 * @param strContractCtrl 合同ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnStartDateCtrl 返回值（起始日期）对应的控件名
	 * @param strRtnEndDateCtrl 返回值（到期日期）对应的控件名
	 * @param strRtnSubAccountIDCtrl 返回值（子账户ID）对应的控件名
	 * @param strRtnBalanceCtrl 返回值（放款单余额）对应的控件名
	 * @param strRtnIsHasFreeCtrl 返回值（是否有免还通知单：1，是；2，否）对应的控件名
	 */
	public static void createPayFormCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lContractID,
		long lPayFormID,
		String strPayFormNo,
		long lPayFormTypeID,
		long lTypeID,
		String strContractCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnStartDateCtrl,
		String strRtnEndDateCtrl,
		String strRtnSubAccountIDCtrl,
		String strRtnBalanceCtrl,
		String strRtnIsHasFreeCtrl)
	{
		String strMagnifierName = URLEncoder.encode("放款通知单");
		String strMainProperty = " maxlength='30' value='" + strPayFormNo + "'";
		String strPrefix = "";
		if (strRtnStartDateCtrl == null || strRtnStartDateCtrl.equals(""))
		{
			strRtnStartDateCtrl = "NCTL";
		}
		if (strRtnEndDateCtrl == null || strRtnEndDateCtrl.equals(""))
		{
			strRtnEndDateCtrl = "NCTL";
		}
		if (strRtnSubAccountIDCtrl == null || strRtnSubAccountIDCtrl.equals(""))
		{
			strRtnSubAccountIDCtrl = "NCTL";
		}
		if (strRtnBalanceCtrl == null || strRtnBalanceCtrl.equals(""))
		{
			strRtnBalanceCtrl = "NCTL";
		}
		if (strRtnIsHasFreeCtrl == null || strRtnIsHasFreeCtrl.equals(""))
		{
			strRtnIsHasFreeCtrl = "NCTL";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnStartDateCtrl, strRtnEndDateCtrl, strRtnSubAccountIDCtrl, strRtnBalanceCtrl, strRtnIsHasFreeCtrl };
		String[] strMainFields = { "PayFormCode", "StartDate", "EndDate", "SubAccountID", "Balance", "IsHasFree" };
		String[] strReturnNames = { strCtrlName, strCtrlName + "ContractID" };
		String[] strReturnFields = { "PayFormID", "ContractID" };
		String[] strReturnValues = { String.valueOf(lPayFormID), String.valueOf(lContractID)};
		String[] strDisplayNames = { URLEncoder.encode("放款通知单号"), URLEncoder.encode("起始日期"), URLEncoder.encode("放款日期")};
		String[] strDisplayFields = { "PayFormCode", "StartDate", "PayDate" };
		int nIndex = 0;
		long[] lContractTypeIDs = null;
		//信托 ：LOANConstant.LoanType.ZYDQ  ZYZCQ  ZGXEDQ  ZGXEZCQ  MFXD
		if (lPayFormTypeID == 1)
		{
			lContractTypeIDs =
				new long[] {
					LOANConstant.LoanType.ZY,
					LOANConstant.LoanType.ZGXE,
					LOANConstant.LoanType.YT,
					LOANConstant.LoanType.MFXD };
		}
		//委托 : WT  WTTJTH
		else
			if (lPayFormTypeID == 2)
			{
				lContractTypeIDs = new long[] { LOANConstant.LoanType.WT};
			}
		//银团 ：LOANConstant.LoanType.YT
		else
			if (lPayFormTypeID == 3)
			{
				lContractTypeIDs = new long[] { LOANConstant.LoanType.YT };
			}
		else if (lPayFormTypeID == 0)
			{
				lContractTypeIDs =
					new long[] {
						LOANConstant.LoanType.WT,
						LOANConstant.LoanType.ZY,
						LOANConstant.LoanType.ZGXE,
						LOANConstant.LoanType.YT,
						LOANConstant.LoanType.MFXD };
			}
		//全部(包括贴现)
		else if (lPayFormTypeID == 4)
		{
			lContractTypeIDs =
				new long[] {
					LOANConstant.LoanType.WT,
					LOANConstant.LoanType.ZY,
					LOANConstant.LoanType.ZGXE,
					LOANConstant.LoanType.YT,
					LOANConstant.LoanType.TX ,
					LOANConstant.LoanType.MFXD };
		}
		//票据承兑业务		add by feiye 20081022
		else if (lPayFormTypeID == 51 || lPayFormTypeID == 52)
		{
			lContractTypeIDs =
				new long[] {
					LOANConstant.LoanType.SHYCHDHP};
		}
		else if(lPayFormTypeID == 5)
		{
			//担保贷款
			lContractTypeIDs = new long[] { LOANConstant.LoanType.DB};
		}
		
		long[] lStatusIDArray = null;
		//获得状态数组
		if (lTypeID == 0)
		{
			lStatusIDArray = new long[] { LOANConstant.LoanPayNoticeStatus.CHECK, LOANConstant.LoanPayNoticeStatus.USED };
		}
		else
			if (lTypeID == 1)
			{
				lStatusIDArray = new long[] { LOANConstant.LoanPayNoticeStatus.CHECK };
			}
			else
				if (lTypeID == 2)
				{
					lStatusIDArray = new long[] { LOANConstant.LoanPayNoticeStatus.USED };
				}
				else
					if (lTypeID == 3)
					{
						lStatusIDArray = new long[] { -100 };
					}
					else
						if (lTypeID == 4)
						{
							lStatusIDArray = new long[] { -200 };
						}
						else
							if (lTypeID == 5)
							{
								lStatusIDArray = new long[] { -500 };
							}
							else
								if (lTypeID == 6)
								{
									lStatusIDArray = new long[] { -600 };
								}
								else
									if (lTypeID == 7)
									{
										lStatusIDArray = new long[] { -700 };
									}
									else
										if (lTypeID == 8)
										{
											lStatusIDArray = new long[] { -800 };
										}
										else
											if (lTypeID == 9)
											{
												lStatusIDArray = new long[] { -900 };
											}
											else
												if (lTypeID == 10)
												{
													lStatusIDArray = new long[] { -1000 };
												}
		StringBuffer sbSQL = new StringBuffer(64);
		//change by feiye 20081022
		if (lPayFormTypeID == 51){
			for(int i=0;i<10;i++)
				System.out.println("取的是商票承兑SQL语句---到期承兑");
			sbSQL.append("getInFormSQL1(");
		}else if(lPayFormTypeID == 52){
			for(int i=0;i<10;i++)
				System.out.println("取的是商票承兑SQL语句---垫付收回");
			sbSQL.append("getInFormSQL2(");
		}else{
			for(int i=0;i<10;i++)
				System.out.println("取的是原来的业务SQL语句");
			sbSQL.append("getPayFormSQL(");
		}
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		if (strContractCtrl != null && !strContractCtrl.trim().equals(""))
		{
			sbSQL.append(strContractCtrl + ".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(",");
		sbSQL.append("'");
		if (lContractTypeIDs != null && lContractTypeIDs.length > 0)
		{
			for (int i = 0; i < lContractTypeIDs.length; i++)
			{
				sbSQL.append(lContractTypeIDs[i]);
				if (i < lContractTypeIDs.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append("'");
		if (lStatusIDArray != null && lStatusIDArray.length > 0)
		{
			for (int i = 0; i < lStatusIDArray.length; i++)
			{
				sbSQL.append(lStatusIDArray[i]);
				if (i < lStatusIDArray.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value)");
		//Log.print(sbSQL.toString());
		try
		{
			SETTMagnifier.showZoomCtrl(
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
	 * 创建担保放款通知单放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lContractID 合同ID(初识值)
	 * @param lPayFormID 放款通知单ID(初识值)
	 * @param strPayFormNo 放款通知单号(初识值)
	 * @param lPayFormTypeID 放款通知单类型
	 * (查询条件:1,信托；2，委托；3，银团；4,全部(包括贴现);0,全部)
	 * @param lTypeID 放款通知单使用条件(内部状态：
	 * 		0，显示全部；
	 * 		1，信托/委托/银团发放——业务处理使用；
	 * 		2，信托/委托/银团发放——业务复核使用；
	 * 		3，信托/委托收回——业务处理使用；
	 * 		4，信托/委托收回——业务复核使用；
	 * 		5，交易费用/特殊业务——业务处理使用；
	 * 		6，交易费用/特殊业务——业务复核使用；
	 * 		7，银团贷款收回——业务处理使用；
	 * 		8，银团贷款收回——业务复核使用；）
	 * @param strContractCtrl 合同ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnStartDateCtrl 返回值（起始日期）对应的控件名
	 * @param strRtnEndDateCtrl 返回值（到期日期）对应的控件名
	 * @param strRtnSubAccountIDCtrl 返回值（子账户ID）对应的控件名
	 * @param strRtnBalanceCtrl 返回值（放款单余额）对应的控件名
	 * @param strRtnRateCtrl 返回值（放款单利率）对应的控件名
	 * @param strRtnPayAccountCtrl 返回值（担保合同的收款通知单制定的付保证金活期账户）对应的控件名
	 * @param strRtnClientNameCtrl
	 * @param strRtnClientNameCtrl
	 */
	public static void createMarginPayFormCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lContractID,
		long lPayFormID,
		String strPayFormNo,
		long lPayFormTypeID,
		long lTypeID,
		String strContractCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnStartDateCtrl,
		String strRtnEndDateCtrl,
		String strRtnBalanceCtrl,
		String strRtnRateCtrl,
		long lCheckStatus,
		String strRtnPayAccountIDCtrl,
		String strRtnPayAccountNoCtrl,
		String strRtnPayAccountNo1Ctrl,
		String strRtnPayAccountNo2Ctrl,
		String strRtnPayAccountNo3Ctrl,
		String strRtnPayAccountNo4Ctrl,
		String strRtnPayAccountNo5Ctrl,
		/**
		String strRtnPayInterstAccountIDCtrl,
		String strRtnPayInterstAccountNoCtrl,
		String strRtnPayInterstAccountNo1Ctrl,
		String strRtnPayInterstAccountNo2Ctrl,
		String strRtnPayInterstAccountNo3Ctrl,
		String strRtnPayInterstAccountNo4Ctrl,
		**/
		
		String strRtnClientNameCtrl)
	{
		String strMagnifierName = URLEncoder.encode("收款通知单");
		String strMainProperty = " maxlength='30' value='" + strPayFormNo + "'";
		String strPrefix = "";
		if (strRtnStartDateCtrl == null || strRtnStartDateCtrl.equals(""))
		{
			strRtnStartDateCtrl = "NCTL";
		}
		if (strRtnEndDateCtrl == null || strRtnEndDateCtrl.equals(""))
		{
			strRtnEndDateCtrl = "NCTL";
		}
		
		if (strRtnBalanceCtrl == null || strRtnBalanceCtrl.equals(""))
		{
			strRtnBalanceCtrl = "NCTL";
		}
		if (strRtnRateCtrl == null || strRtnRateCtrl.equals(""))
		{
			strRtnRateCtrl = "NCTL";
		}
		
		if (strRtnPayAccountIDCtrl == null || strRtnPayAccountIDCtrl.equals(""))
		{
			strRtnPayAccountIDCtrl = "NCTL";
		}
		if (strRtnPayAccountNoCtrl == null || strRtnPayAccountNoCtrl.equals(""))
		{
			strRtnPayAccountNoCtrl = "NCTL";
		}
		
		if (strRtnPayAccountNo1Ctrl == null || strRtnPayAccountNo1Ctrl.equals(""))
		{
			strRtnPayAccountNo1Ctrl = "NCTL";
		}
		if (strRtnPayAccountNo2Ctrl == null || strRtnPayAccountNo2Ctrl.equals(""))
		{
			strRtnPayAccountNo2Ctrl = "NCTL";
		}
		if (strRtnPayAccountNo3Ctrl == null || strRtnPayAccountNo3Ctrl.equals(""))
		{
			strRtnPayAccountNo3Ctrl = "NCTL";
		}
		if (strRtnPayAccountNo4Ctrl == null || strRtnPayAccountNo4Ctrl.equals(""))
		{
			strRtnPayAccountNo4Ctrl = "NCTL";
		}
		if (strRtnPayAccountNo5Ctrl == null || strRtnPayAccountNo5Ctrl.equals(""))
		{
			strRtnPayAccountNo5Ctrl = "NCTL";
		}
		
		/**
		if (strRtnPayInterstAccountIDCtrl == null || strRtnPayInterstAccountIDCtrl.equals(""))
		{
			strRtnPayInterstAccountIDCtrl = "NCTL";
		}
		if (strRtnPayInterstAccountNoCtrl == null || strRtnPayInterstAccountNoCtrl.equals(""))
		{
			strRtnPayInterstAccountNoCtrl = "NCTL";
		}
		if (strRtnPayInterstAccountNo1Ctrl == null || strRtnPayInterstAccountNo1Ctrl.equals(""))
		{
			strRtnPayInterstAccountNo1Ctrl = "NCTL";
		}
		if (strRtnPayInterstAccountNo2Ctrl == null || strRtnPayInterstAccountNo2Ctrl.equals(""))
		{
			strRtnPayInterstAccountNo2Ctrl = "NCTL";
		}
		if (strRtnPayInterstAccountNo3Ctrl == null || strRtnPayInterstAccountNo3Ctrl.equals(""))
		{
			strRtnPayInterstAccountNo3Ctrl = "NCTL";
		}
		if (strRtnPayInterstAccountNo4Ctrl == null || strRtnPayInterstAccountNo4Ctrl.equals(""))
		{
			strRtnPayInterstAccountNo4Ctrl = "NCTL";
		}
		**/
		if (strRtnClientNameCtrl == null || strRtnClientNameCtrl.equals(""))
		{
			strRtnClientNameCtrl = "NCTL";
		}
		
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnStartDateCtrl, strRtnEndDateCtrl,  strRtnBalanceCtrl, strRtnRateCtrl ,strRtnPayAccountIDCtrl,strRtnPayAccountNoCtrl,strRtnPayAccountNo1Ctrl,strRtnPayAccountNo2Ctrl,strRtnPayAccountNo3Ctrl,strRtnPayAccountNo4Ctrl,strRtnClientNameCtrl,strRtnPayAccountNo5Ctrl};
		String[] strMainFields = { "PayFormCode", "StartDate", "EndDate",  "Balance", "Rate" ,"PayAccountID","PayAccountNo","PayAccountNo1","PayAccountNo2","PayAccountNo3","PayAccountNo4","ClientName","Amount" };
		
		
		String[] strReturnNames = { strCtrlName, strCtrlName + "ContractID" };
		String[] strReturnFields = { "PayFormID", "ContractID" };
		String[] strReturnValues = { String.valueOf(lPayFormID), String.valueOf(lContractID)};
		String[] strDisplayNames = { URLEncoder.encode("收款通知单号")};
		String[] strDisplayFields = { "PayFormCode"};
		int nIndex = 0;
		long[] lContractTypeIDs = null;
		//信托 ：LOANConstant.LoanType.ZYDQ  ZYZCQ  ZGXEDQ  ZGXEZCQ  MFXD
		if (lPayFormTypeID == 1)
		{
			lContractTypeIDs =
				new long[] {
					LOANConstant.LoanType.ZY,
					LOANConstant.LoanType.ZGXE,
					LOANConstant.LoanType.YT,
					LOANConstant.LoanType.MFXD
					};
		}
		//委托 : WT  WTTJTH
		else
			if (lPayFormTypeID == 2)
			{
				lContractTypeIDs = new long[] { LOANConstant.LoanType.WT};
			}
		//银团 ：LOANConstant.LoanType.YT
		else
			if (lPayFormTypeID == 3)
			{
				lContractTypeIDs = new long[] { LOANConstant.LoanType.YT };
			}
		else if (lPayFormTypeID == 0)
			{
				lContractTypeIDs =
					new long[] {
						LOANConstant.LoanType.WT,
						LOANConstant.LoanType.ZY,
						LOANConstant.LoanType.ZGXE,
						LOANConstant.LoanType.YT,
						LOANConstant.LoanType.MFXD };
			}
		//全部(包括贴现)
		else if (lPayFormTypeID == 4)
		{
			lContractTypeIDs =
				new long[] {
					LOANConstant.LoanType.WT,
					LOANConstant.LoanType.ZY,
					LOANConstant.LoanType.ZGXE,
					LOANConstant.LoanType.YT,
					LOANConstant.LoanType.TX ,
					LOANConstant.LoanType.MFXD };
		}
		else if(lPayFormTypeID == 5)
		{
			//担保贷款
			lContractTypeIDs = new long[] { LOANConstant.LoanType.DB};
		}
		else if(lPayFormTypeID == 6)
		{
			//融资租赁贷款
			lContractTypeIDs = new long[] { LOANConstant.LoanType.RZZL};	//add by feiye 2006.5.2
		}

		long[] lStatusIDArray = null;
		//获得状态数组
		
		if (lTypeID == 0)
		{
			lStatusIDArray = new long[] { LOANConstant.LoanPayNoticeStatus.CHECK, LOANConstant.LoanPayNoticeStatus.USED };
		}
		else
			if (lTypeID == 1)
			{
				lStatusIDArray = new long[] { LOANConstant.LoanPayNoticeStatus.CHECK };
			}
			else
				if (lTypeID == 2)
				{
					lStatusIDArray = new long[] { LOANConstant.LoanPayNoticeStatus.USED };
				}
				else
					if (lTypeID == 3)
					{
						lStatusIDArray = new long[] { -100 };
					}
					else
						if (lTypeID == 4)
						{
							lStatusIDArray = new long[] { -200 };
						}
						else
							if (lTypeID == 5)
							{
								lStatusIDArray = new long[] { -500 };
							}
							else
								if (lTypeID == 6)
								{
									lStatusIDArray = new long[] { -600 };
								}
								else
									if (lTypeID == 7)
									{
										lStatusIDArray = new long[] { -700 };
									}
									else
										if (lTypeID == 8)
										{
											lStatusIDArray = new long[] { -800 };
										}
										else
											if (lTypeID == 9)
											{
												lStatusIDArray = new long[] { -900 };
											}
											else
												if (lTypeID == 10)
												{
													lStatusIDArray = new long[] { -1000 };
												}
		
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getAssurePayFormSQL1(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		sbSQL.append(lCheckStatus);
		sbSQL.append(",");
		if (strContractCtrl != null && !strContractCtrl.trim().equals(""))
		{
			sbSQL.append(strContractCtrl + ".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(",");
		sbSQL.append("'");
		if (lContractTypeIDs != null && lContractTypeIDs.length > 0)
		{
			for (int i = 0; i < lContractTypeIDs.length; i++)
			{
				sbSQL.append(lContractTypeIDs[i]);
				if (i < lContractTypeIDs.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append("'");
		if (lStatusIDArray != null && lStatusIDArray.length > 0)
		{
			for (int i = 0; i < lStatusIDArray.length; i++)
			{
				sbSQL.append(lStatusIDArray[i]);
				if (i < lStatusIDArray.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value)");
		//Log.print(sbSQL.toString());
		try
		{
			SETTMagnifier.showZoomCtrl(
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
	 * 创建保后处理通知单放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 */
	public static void createAssureManagementFormCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lClientID,
		long lAssureFormID,
		String strAssureFormNo,
		String strClientCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		long lCheckStatus,
		String strRtnBalanceCtrl,
		String strRtnClientCodeCtrl,
		String strRtnClientNameCtrl)
	{
		String strMagnifierName = URLEncoder.encode("保后处理通知单");
		String strMainProperty = " maxlength='30' value='" + strAssureFormNo + "'";
		String strPrefix = "";
		if (strRtnBalanceCtrl == null || strRtnBalanceCtrl.equals(""))
		{
			strRtnBalanceCtrl = "NCTL";
		}
		if (strRtnClientNameCtrl == null || strRtnClientNameCtrl.equals(""))
		{
			strRtnClientNameCtrl = "NCTL";
		}
		if (strRtnClientCodeCtrl == null || strRtnClientCodeCtrl.equals(""))
		{
		    strRtnClientCodeCtrl = "NCTL";
		}
		
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnBalanceCtrl, strRtnClientCodeCtrl,strRtnClientNameCtrl};
		String[] strMainFields = { "AssureFormCode",  "recognizanceAmount","ClientCode","ClientName" };
		
		
		String[] strReturnNames = { strCtrlName, strCtrlName + "ClientID" };
		String[] strReturnFields = { "AssureFormID", "ClientID" };
		String[] strReturnValues = { String.valueOf(lAssureFormID), String.valueOf(lClientID)};
		String[] strDisplayNames = { URLEncoder.encode("通知单编号"), URLEncoder.encode("支取金额")};
		String[] strDisplayFields = { "AssureFormCode", "recognizanceAmount" };
		int nIndex = 0;
		
		
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getAssureManagementFormSQL(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		sbSQL.append(lCheckStatus);
		sbSQL.append(",");
		if (strClientCtrl != null && !strClientCtrl.trim().equals(""))
		{
			sbSQL.append(strClientCtrl + ".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(",");
		
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value)");
		//Log.print(sbSQL.toString());
		try
		{
			SETTMagnifier.showZoomCtrl(
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
	 * 创建贷款通知单放大镜
	 * 贷款通知单包含自营/委贷放款单、银团放款单、贴现凭证、转贴现凭证
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lContractID 合同ID(初识值)
	 * @param lLoanFormID 贷款通知单ID(初识值)
	 * @param strLoanFormNo 贷款通知单号(初识值)
	 * @param lLoanFormTypeID 贷款通知单类型
	 * (查询条件:1,自营；2，委托；3，银团；4，贴现；5转贴现；0,全部)
	 * @param lStatusIDArray 贷款通知单状态数组（请使用贷款的通知单状态常量LoanPayNoticeStatus）：
	 * @param strContractCtrl 合同ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnStartDateCtrl 返回值（起始日期）对应的控件名
	 * @param strRtnEndDateCtrl 返回值（到期日期）对应的控件名
	 * @param strRtnBalanceCtrl 返回值（放款单余额）对应的控件名
	 */
	public static void createLoanFormCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lContractID,
		long lLoanFormID,
		String strLoanFormNo,
		long lLoanFormTypeID,
		long[] lStatusIDArray,
		String strContractCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnStartDateCtrl,
		String strRtnEndDateCtrl,
		String strRtnBalanceCtrl)
	{
		String strMagnifierName = URLEncoder.encode("贷款通知单");
		String strMainProperty = " maxlength='30' value='" + strLoanFormNo + "'";
		String strPrefix = "";
		if (strRtnStartDateCtrl == null || strRtnStartDateCtrl.equals(""))
		{
			strRtnStartDateCtrl = "NC";
		}
		if (strRtnEndDateCtrl == null || strRtnEndDateCtrl.equals(""))
		{
			strRtnEndDateCtrl = "NC";
		}
		if (strRtnBalanceCtrl == null || strRtnBalanceCtrl.equals(""))
		{
			strRtnBalanceCtrl = "NC";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnStartDateCtrl, strRtnEndDateCtrl, strRtnBalanceCtrl };
		String[] strMainFields = { "LFCode", "SDate", "EDate", "Balance" };
		String[] strReturnNames = { strCtrlName, strCtrlName + "ContractID" };
		String[] strReturnFields = { "LFID", "ContractID" };
		String[] strReturnValues = { String.valueOf(lLoanFormID), String.valueOf(lContractID)};
		String[] strDisplayNames = { URLEncoder.encode("贷款通知单号"), URLEncoder.encode("起始日期"), URLEncoder.encode("放款日期")};
		String[] strDisplayFields = { "LFCode", "SDate", "PDate" };
		int nIndex = 0;
		long[] lContractTypeIDs = null;
		//信托 ：LOANConstant.LoanType.ZYDQ  ZYZCQ  ZGXEDQ  ZGXEZCQ
		if (lLoanFormTypeID == 1)
		{
			lContractTypeIDs =
				new long[] {
					LOANConstant.LoanType.ZY,
					LOANConstant.LoanType.ZGXE,
					LOANConstant.LoanType.YT};
		}
		//委托 : WT  WTTJTH
		else
			if (lLoanFormTypeID == 2)
			{
				lContractTypeIDs = new long[] { LOANConstant.LoanType.WT};
			}
		//银团 ：LOANConstant.LoanType.YT
		else
			if (lLoanFormTypeID == 3)
			{
				lContractTypeIDs = new long[] { LOANConstant.LoanType.YT };
			}
		//贴现 : TX
		else
			if (lLoanFormTypeID == 4)
			{
				lContractTypeIDs = new long[] { LOANConstant.LoanType.TX };
			}
		//转贴现 ：ZTX
		else
			if (lLoanFormTypeID == 5)
			{
				lContractTypeIDs = new long[] { LOANConstant.LoanType.ZTX };
			}
			else
				if (lLoanFormTypeID == 0)
				{
					lContractTypeIDs =
						new long[] {
							LOANConstant.LoanType.WT,
							LOANConstant.LoanType.ZY,
							LOANConstant.LoanType.ZGXE,
							LOANConstant.LoanType.YT,
							LOANConstant.LoanType.TX,
							LOANConstant.LoanType.ZTX };
				}
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getLoanFormSQL(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		if (strContractCtrl != null && !strContractCtrl.trim().equals(""))
		{
			sbSQL.append(strContractCtrl + ".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(",");
		sbSQL.append("'");
		if (lContractTypeIDs != null && lContractTypeIDs.length > 0)
		{
			for (int i = 0; i < lContractTypeIDs.length; i++)
			{
				sbSQL.append(lContractTypeIDs[i]);
				if (i < lContractTypeIDs.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append("'");
		if (lStatusIDArray != null && lStatusIDArray.length > 0)
		{
			for (int i = 0; i < lStatusIDArray.length; i++)
			{
				sbSQL.append(lStatusIDArray[i]);
				if (i < lStatusIDArray.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value)");
		//Log.print(sbSQL.toString());
		try
		{
			SETTMagnifier.showZoomCtrl(
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
	 * 创建放款通知单放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lContractID 合同ID(初识值)
	 * @param lPayFormID 放款通知单ID(初识值)
	 * @param strPayFormNo 放款通知单号(初识值)
	 * @param lPayFormTypeID 放款通知单类型
	 * (查询条件:1,信托；2，委托；0,全部)
	 * @param lTypeID 放款通知单使用条件(内部状态：
	 * 		0，显示全部；
	 * 		1，信托/委托发放——业务处理使用；
	 * 		2，信托/委托发放——业务复核使用；
	 * 		3，信托/委托收回——业务处理使用；
	 * 		4，信托/委托收回——业务复核使用；
	 * 		5，交易费用/特殊业务——业务处理使用；
	 * 		6，交易费用/特殊业务——业务复核使用；）
	 * @param strContractCtrl 合同ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnStartDateCtrl 返回值（起始日期）对应的控件名
	 * @param strRtnEndDateCtrl 返回值（到期日期）对应的控件名
	 * @param strRtnSubAccountIDCtrl 返回值（子账户ID）对应的控件名
	 * @param strRtnBalanceCtrl 返回值（放款单余额）对应的控件名
	 */
	public static void createPayFormCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lContractID,
		long lPayFormID,
		String strPayFormNo,
		long lPayFormTypeID,
		long lTypeID,
		String strContractCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnStartDateCtrl,
		String strRtnEndDateCtrl,
		String strRtnSubAccountIDCtrl,
		String strRtnBalanceCtrl)
	{
		createPayFormCtrl(
			out,
			lOfficeID,
			lCurrencyID,
			strFormName,
			strCtrlName,
			strTitle,
			lContractID,
			lPayFormID,
			strPayFormNo,
			lPayFormTypeID,
			lTypeID,
			strContractCtrl,
			strFirstTD,
			strSecondTD,
			strNextControls,
			strRtnStartDateCtrl,
			strRtnEndDateCtrl,
			strRtnSubAccountIDCtrl,
			strRtnBalanceCtrl,
			"");
	}
	/**
	 * 创建融资租赁收款放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lReturnID 还款通知单ID(初识值)
	 * @param strReturnNo 还款通知单编号(初识值)
	 * @param strContractCtrl 银行类型ID对应的控件名称
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 */
	public static void createPayFinance(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lReturnID,
		String strReturnNo, 
		long lPayFormTypeID,									
		long lPayFormStatusIDs,
		String strContractCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnClientNameCtrl,
		String strRtnContractID)
	{
		String strMagnifierName = URLEncoder.encode("收款通知单");
		String strMainProperty = " maxlength='30' value='" + strReturnNo + "'";
		String strPrefix = "";
		if (strRtnClientNameCtrl == null || strRtnClientNameCtrl.equals(""))
		{
			strRtnClientNameCtrl = "NCTL";
		}
		if (strRtnContractID == null || strRtnContractID.equals(""))
		{
			strRtnContractID = "N";
		}
		
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnClientNameCtrl,strRtnContractID};
		String[] strMainFields = { "payNo", "ClientName","contractID"};
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "payID" };
		String[] strReturnValues = { String.valueOf(lReturnID)};
		String[] strDisplayNames = { URLEncoder.encode("收款通知单号")};
		String[] strDisplayFields = { "payNo"};
		int nIndex = 0;
		String strSQL = "";
		
		strSQL = "getPayFinanceSQL(" + lOfficeID + "," + lCurrencyID + "," + lPayFormTypeID + "," + lPayFormStatusIDs + ",";
		if (strContractCtrl != null && !strContractCtrl.trim().equals(""))
		{
			strSQL+=strContractCtrl+".value";
		}
		else
		{
			strSQL+="-1";
		}
		strSQL+=",";
		strSQL+=strCtrlName + "Ctrl.value)";

		try
		{
			SETTMagnifier.showZoomCtrl(
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
			Log.print("还款通知单放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	/**
	 * 创建融资租赁还款放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lReturnID 还款通知单ID(初识值)
	 * @param strReturnNo 还款通知单编号(初识值)
	 * @param strContractCtrl 银行类型ID对应的控件名称
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 */
	public static void createReturnFinance(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lReturnID,
		String strReturnNo,
		long lstatus,
		String strContractCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnClientNameCtrl,
		String strRtnContractID)
	{
		String strMagnifierName = URLEncoder.encode("还款通知单");
		String strMainProperty = " maxlength='30' value='" + strReturnNo + "'";
		String strPrefix = "";
		if (strRtnClientNameCtrl == null || strRtnClientNameCtrl.equals(""))
		{
			strRtnClientNameCtrl = "NCTL";
		}
		if (strRtnContractID == null || strRtnContractID.equals(""))
		{
			strRtnContractID = "NCTL";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnClientNameCtrl,strRtnContractID};
		String[] strMainFields = { "returnNo", "ClientName","contractID"};
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "returnID" };
		String[] strReturnValues = { String.valueOf(lReturnID)};
		String[] strDisplayNames = { URLEncoder.encode("还款通知单号")};
		String[] strDisplayFields = { "returnNo"};
		int nIndex = 0;
		String strSQL = "";
		strSQL = "getReturnFinanceSQL(" + lOfficeID + "," + lCurrencyID + "," + lstatus + "," ;
		if (strContractCtrl != null && !strContractCtrl.trim().equals(""))
		{
			strSQL+=strContractCtrl+".value";
		}
		else
		{
			strSQL+="-1";
		}
		strSQL+=",";
		strSQL+=strCtrlName + "Ctrl.value)";				
		try
		{ 
			SETTMagnifier.showZoomCtrl(
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
			Log.print("还款通知单放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	/**
	 * 创建担保放款通知单放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lContractID 合同ID(初识值)
	 * @param lPayFormID 放款通知单ID(初识值)
	 * @param strPayFormNo 放款通知单号(初识值)
	 * @param lPayFormTypeID 放款通知单类型
	 * (查询条件:1,信托；2，委托；0,全部)
	 * @param lTypeID 放款通知单使用条件(内部状态：
	 * 		0，显示全部；
	 * 		1，信托/委托发放——业务处理使用；
	 * 		2，信托/委托发放——业务复核使用；
	 * 		3，信托/委托收回——业务处理使用；
	 * 		4，信托/委托收回——业务复核使用；
	 * 		5，交易费用/特殊业务——业务处理使用；
	 * 		6，交易费用/特殊业务——业务复核使用；）
	 * @param strContractCtrl 合同ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnStartDateCtrl 返回值（起始日期）对应的控件名
	 * @param strRtnEndDateCtrl 返回值（到期日期）对应的控件名
	 * @param strRtnBalanceCtrl 返回值（放款单余额）对应的控件名
	 * @param strRtnRate 返回值（放款单利率）对应的控件名
	 * @param strRtnPayAccountID 返回值（担保合同的收款通知单制定的付保证金活期账户）对应的控件名
	 * @param strRtnPayAccountNo
	 * @param strRtnPayAccountNo1
	 * @param strRtnPayAccountNo2
	 * @param strRtnPayAccountNo3
	 * @param strRtnPayAccountNo4
	 * @param strRtnClientName
	 */
	public static void createAssurePayFormCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lContractID,
		long lPayFormID,
		String strPayFormNo,
		long lPayFormTypeID,
		long lTypeID,
		String strContractCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnStartDateCtrl,
		String strRtnEndDateCtrl,
		String strRtnSubAccountIDCtrl,
		String strRtnRate,
		long lCheckStatus,
		String strRtnPayAccountID,
		String strRtnPayAccountNo,
		
		String strRtnPayAccountNo1,
		String strRtnPayAccountNo2,
		String strRtnPayAccountNo3,
		String strRtnPayAccountNo4,
		String strRtnPayAccountNo5,
		/**
		String strRtnPayInterestAccountID,
		String strRtnPayInterestAccountNo,
		String strRtnPayInterestAccountNo1,
		String strRtnPayInterestAccountNo2,
		String strRtnPayInterestAccountNo3,
		String strRtnPayInterestAccountNo4,
		**/
		String strRtnClientName)
	{
		createMarginPayFormCtrl(
			out,
			lOfficeID,
			lCurrencyID,
			strFormName,
			strCtrlName,
			strTitle,
			lContractID,
			lPayFormID,
			strPayFormNo,
			lPayFormTypeID,
			lTypeID,
			strContractCtrl,
			strFirstTD,
			strSecondTD,
			strNextControls,
			strRtnStartDateCtrl,
			strRtnEndDateCtrl,
			strRtnSubAccountIDCtrl,
			strRtnRate,
			lCheckStatus,
			strRtnPayAccountID,
			strRtnPayAccountNo,
			
			strRtnPayAccountNo1,
			strRtnPayAccountNo2,
			strRtnPayAccountNo3,
			strRtnPayAccountNo4,
			strRtnPayAccountNo5,
			/**
			strRtnPayInterestAccountID,
			strRtnPayInterestAccountNo,
			strRtnPayInterestAccountNo1,
			strRtnPayInterestAccountNo2,
			strRtnPayInterestAccountNo3,
			strRtnPayInterestAccountNo4,
			**/
			
			strRtnClientName);
	}
	/**
	 * 创建开户行和银行账户编号放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lBranchID 开户行ID(初识值)
	 * @param strBranchName 开户行名称(初识值)
	 * @param lIsSingleBank 是否单边账银行（1，是；其它，不是）
	 * @param lBankType 银行类型ID
	 * @param strBankTypeCtrl 银行类型ID对应的控件名称
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnBankAccountNoCtrl 返回值（银行账户编号）对应的控件名
	 * @param strRtnBankNoCtrl 返回值（开户行编号）对应的控件名
	 */
	public static void createBranchAndBankAccountNo(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lBranchID,
		String strBranchName,
		long lIsSingleBank,
		long lBankType,
		String strBankTypeCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnBankAccountNoCtrl,
		String strRtnBankNoCtrl)
	{
		String strMagnifierName = URLEncoder.encode("开户行");
		String strMainProperty = strBranchName;
		String strPrefix = "";
		if (strRtnBankAccountNoCtrl == null || strRtnBankAccountNoCtrl.equals(""))
		{
			strRtnBankAccountNoCtrl = "ItIsNotControl";
		}
		if (strRtnBankNoCtrl == null || strRtnBankNoCtrl.equals(""))
		{
			strRtnBankNoCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnBankAccountNoCtrl, strRtnBankNoCtrl };
		String[] strMainFields = { "BranchName", "BranchAccountNo", "BranchNo" };
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "BranchID" };
		String[] strReturnValues = { String.valueOf(lBranchID)};
		String[] strDisplayNames = { URLEncoder.encode("开户行编号"), URLEncoder.encode("开户行名称")};		
		String[] strDisplayFields = { "BranchNo", "BranchName" };		
		int nIndex = 0;
		String strSQL = "";
		strSQL = "getBranchAndBankAccountNoSQL(" + lOfficeID + "," + lCurrencyID + "," + lIsSingleBank + "," + strBankTypeCtrl + ".value" + "," + strCtrlName + "Ctrl.value)";
		try
		{
			SETTMagnifier.showZoomCtrl(
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
				false,
				"branch");
		}
		catch (Exception e)
		{
			Log.print("开户行放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	/**
	 * 创建银企接口--银行账户放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lBankAccountID 银行账户ID(初识值)
	 * @param strBankAccountNo 银行账户编号(初识值)
	 * @param lBankTypeID 开户行类型ID(初识值)
	 * @param strBankTypeCtrl 银行类型ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnBankTypeIDCtrl 返回值（开户行类型ID）对应的控件名
	 */
	public static void createBankAccountNoCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lBankAccountID,
		String strBankAccountNo,
		long lBankTypeID,
		String strBankTypeCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnBankTypeIDCtrl)
	{
		//放大镜控件非数组
		String strMagnifierName = URLEncoder.encode("银行账户编号");
		String strMainProperty = " maxlength='30' value='" + strBankAccountNo + "'";
		String strPrefix = "";
		if (strRtnBankTypeIDCtrl == null || strRtnBankTypeIDCtrl.equals(""))
		{
			strRtnBankTypeIDCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnBankTypeIDCtrl };
		String[] strMainFields = { "BankAccountNo", "BankTypeID" };
		String[] strReturnNames = { strCtrlName, strCtrlName + "BankTypeID" };
		String[] strReturnFields = { "BankAccountID", "BankTypeID" };
		String[] strReturnValues = { String.valueOf(lBankAccountID), String.valueOf(lBankTypeID)};
		String[] strDisplayNames = { URLEncoder.encode("银行账户编号")};
		String[] strDisplayFields = { "BankAccountNo" };
		int nIndex = 0;
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getBankAccountNoSQL(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		if (strBankTypeCtrl != null && !strBankTypeCtrl.trim().equals(""))
		{
			sbSQL.append(strBankTypeCtrl + ".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(",");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value)");
		try
		{
			SETTMagnifier.showZoomCtrl(
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
	 * add by liangge
	 * @param out
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @param strFormName
	 * @param strCtrlName
	 * @param strTitle
	 * @param lUserID
	 * @param lAccountID
	 * @param lSubAccountID
	 * @param strDepositNo
	 * @param lDepositTypeID
	 * @param lTypeID
	 * @param lClientID
	 * @param strAccountIDCtrl
	 * @param strFirstTD
	 * @param strSecondTD
	 * @param strNextControls
	 * @param strRtnEndDateCtrl
	 * @param strRtnOpenDateCtrl
	 * @param strRtnCapitalCtrl
	 * @param strRtnBalanceCtrl
	 * @param strRtnRateCtrl
	 * @param strRtnIntervalCtrl
	 * @param strRtnStartDateCtrl
	 * @param strReturnNames
	 * @param strReturnFields
	 * @param strReturnValues
	 */
	public static void createFixedDepositNoCtrlZJ(
			JspWriter out,
			long lOfficeID,
			long lCurrencyID,
			String strFormName,
			String strCtrlName,
			String strTitle,
			long lUserID,
			long lAccountID,
			long lSubAccountID,
			String strDepositNo,
			long lDepositTypeID,
			long lTypeID,
			long lClientID,
			String strAccountIDCtrl,
			String strFirstTD,
			String strSecondTD,
			String[] strNextControls,
			String strRtnEndDateCtrl,
			String strRtnOpenDateCtrl,
			String strRtnCapitalCtrl,
			String strRtnBalanceCtrl,
			String strRtnRateCtrl,
			String strRtnIntervalCtrl,
			String strRtnStartDateCtrl,
			String[] strReturnNames,
			String[] strReturnFields,
			String[] strReturnValues)
		{
			String strMagnifierName = URLEncoder.encode("存款单据号");
			if (lDepositTypeID == 1)
			{
				strMagnifierName = URLEncoder.encode("定期存款单据号");
			}
			else if (lDepositTypeID == 2)
			{
				strMagnifierName = URLEncoder.encode("通知存款单据号");
			}
			else if(lDepositTypeID == 6)
			{
				strMagnifierName = URLEncoder.encode("保证金存款单据号");
			}
			
			String strMainProperty = " maxlength='30' value='" + strDepositNo + "'";
			String strPrefix = "";
			if (strRtnEndDateCtrl == null || strRtnEndDateCtrl.equals(""))
			{
				strRtnEndDateCtrl = "ItIsNotControl";
			}
			if (strRtnOpenDateCtrl == null || strRtnOpenDateCtrl.equals(""))
			{
				strRtnOpenDateCtrl = "ItIsNotControl";
			}
			if (strRtnCapitalCtrl == null || strRtnCapitalCtrl.equals(""))
			{
				strRtnCapitalCtrl = "ItIsNotControl";
			}
			if (strRtnBalanceCtrl == null || strRtnBalanceCtrl.equals(""))
			{
				strRtnBalanceCtrl = "ItIsNotControl";
			}
			if (strRtnRateCtrl == null || strRtnRateCtrl.equals(""))
			{
				strRtnRateCtrl = "ItIsNotControl";
			}
			if (strRtnIntervalCtrl == null || strRtnIntervalCtrl.equals(""))
			{
				strRtnIntervalCtrl = "ItIsNotControl";
			}
			if (strRtnStartDateCtrl == null || strRtnStartDateCtrl.equals(""))
			{
				strRtnStartDateCtrl = "ItIsNotControl";
			}
			long lFromClient = 0;
			if (lClientID > 0)
			{
				lFromClient = 1;
			}
			/*String[] strMainNames =
			{ strCtrlName + "Ctrl", strRtnEndDateCtrl, strRtnOpenDateCtrl, strRtnCapitalCtrl, strRtnBalanceCtrl, strRtnRateCtrl, strRtnIntervalCtrl, strRtnStartDateCtrl };
		    String[] strMainFields = { "DepositNo", "EndDate", "OpenDate", "Capital", "Balance", "Rate", "Interval", "StartDate" };*/
			//String[] strMainNames =
			//{ strCtrlName + "Ctrl", strRtnEndDateCtrl, strRtnOpenDateCtrl, strRtnCapitalCtrl, strRtnBalanceCtrl, strRtnRateCtrl, strRtnIntervalCtrl, strRtnStartDateCtrl };
		    //String[] strMainFields = { "DepositNo", "EndDate", "OpenDate", "Capital", "Balance", "Rate", "Interval", "StartDate" };
			String[] strMainNames =
				{ strCtrlName + "Ctrl","lClientIDCtrl","strClientName","lClientID","lAccountID","lAccountIDClientID","dPayerStartDate","dPayerEndDate","nFixedDepositTime","dPayerCurrBalance","dPayerCurrInterest","dPayerCurrStartDate","dAmount"};
			String[] strMainFields = { "DepositNo","ClientNo","ClientName","ClientID","AccountID","ClientID","OpenDate","EndDate","Interval","Balance","Rate","EndDate","Balance"};
			//String[] strReturnNames = { strCtrlName, strCtrlName + "AccountID","lClientID","lClientIDFromClient","lAccountID","lAccountIDClientID","lAccountIDGroupID"};
			//String[] strReturnFields = { "SubAccountID", "AccountID","ClientID","FromClient","AccountID","ClientID","accountgroupid" };
//			String[] strReturnNames = { strCtrlName, strCtrlName + "AccountID" };
//			String[] strReturnFields = { "SubAccountID", "AccountID" };
//			String[] strReturnValues = { String.valueOf(lSubAccountID), String.valueOf(lAccountID)};
			//String[] strReturnValues = { String.valueOf(lSubAccountID), String.valueOf(lAccountID),String.valueOf(lClientID), String.valueOf(lFromClient),String.valueOf(lAccountID), String.valueOf(lClientID), "-1"};
			String[] strDisplayNames = { URLEncoder.encode("存款单据号")};
			String[] strDisplayFields = { "DepositNo" };
			//支取或续期转存时，显示不同
			if (lTypeID == 21 || lTypeID == 22 || lTypeID == 3 || lTypeID == 0)
			{
				if (lDepositTypeID == 1)
				{
					//定期显示单据号、余额、到期日
					strDisplayNames = new String[] { URLEncoder.encode("存款单据号"), URLEncoder.encode("余额"), URLEncoder.encode("到期日")};
					strDisplayFields = new String[] { "DepositNo", "Balance", "EndDate" };
				}else if(lDepositTypeID == 6){
//					保证金显示单据号、可用余额、开户日期
					strDisplayNames = new String[] { URLEncoder.encode("存款单据号"), URLEncoder.encode("可用余额"), URLEncoder.encode("开户日")};
					strDisplayFields = new String[] { "DepositNo", "Balance", "OpenDate" };
				}
				else
				{
					//定期显示单据号、余额、开户日期
					strDisplayNames = new String[] { URLEncoder.encode("存款单据号"), URLEncoder.encode("余额")};
					strDisplayFields = new String[] { "DepositNo", "Balance"};
				}
			}
			int nIndex = 0;
			String strSQL = "";
			if (strAccountIDCtrl != null && !strAccountIDCtrl.trim().equals(""))
			{
				strSQL =
					"getFixedDepositNoSQLZJ("
						+ lOfficeID
						+ ","
						+ lCurrencyID
						+ ","
						+ lDepositTypeID
						+ ","
						+ strAccountIDCtrl
						+ ".value,"
						+ lUserID
						+ ","
						+ strCtrlName
						+ "Ctrl.value,"
						+ lTypeID
						+ ",'"
						+ Env.getSystemDateString(lOfficeID, lCurrencyID)
						+ "')";
			}
			else
			{
				strSQL =
					"getFixedDepositNoSQLZJ("
						+ lOfficeID
						+ ","
						+ lCurrencyID
						+ ","
						+ lDepositTypeID
						+ ",-1,"
						+ lUserID
						+ ","
						+ strCtrlName
						+ "Ctrl.value,"
						+ lTypeID
						+ ",'"
						+ Env.getSystemDateString(lOfficeID, lCurrencyID)
						+ "')";
			}
			try
			{
				SETTMagnifier.showZoomCtrl(
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
				Log.print("定期存款单据号放大镜[" + strCtrlName + "]异常：" + e.toString());
			}
		}
	
	/**
	 * 中交项目修改放大镜！定期续期转账！
	 */
	public static void createFixedDepositNoCtrlZJ(
			JspWriter out,
			long lOfficeID,
			long lCurrencyID,
			String strFormName,
			String strCtrlName,
			String strTitle,
			long lUserID,
			long lAccountID,
			long lSubAccountID,
			String strDepositNo,
			long lDepositTypeID,
			long lTypeID,
			long lClientID,
			String strAccountIDCtrl,
			String strFirstTD,
			String strSecondTD,
			String[] strNextControls,
			String strRtnEndDateCtrl,
			String strRtnOpenDateCtrl,
			String strRtnCapitalCtrl,
			String strRtnBalanceCtrl,
			String strRtnRateCtrl,
			String strRtnIntervalCtrl,
			String strRtnStartDateCtrl)
		{
			String strMagnifierName = URLEncoder.encode("存款单据号");
			if (lDepositTypeID == 1)
			{
				strMagnifierName = URLEncoder.encode("定期存款单据号");
			}
			else if (lDepositTypeID == 2)
			{
				strMagnifierName = URLEncoder.encode("通知存款单据号");
			}
			else if(lDepositTypeID == 6)
			{
				strMagnifierName = URLEncoder.encode("保证金存款单据号");
			}
			
			String strMainProperty = " maxlength='30' value='" + strDepositNo + "'";
			String strPrefix = "";
			if (strRtnEndDateCtrl == null || strRtnEndDateCtrl.equals(""))
			{
				strRtnEndDateCtrl = "ItIsNotControl";
			}
			if (strRtnOpenDateCtrl == null || strRtnOpenDateCtrl.equals(""))
			{
				strRtnOpenDateCtrl = "ItIsNotControl";
			}
			if (strRtnCapitalCtrl == null || strRtnCapitalCtrl.equals(""))
			{
				strRtnCapitalCtrl = "ItIsNotControl";
			}
			if (strRtnBalanceCtrl == null || strRtnBalanceCtrl.equals(""))
			{
				strRtnBalanceCtrl = "ItIsNotControl";
			}
			if (strRtnRateCtrl == null || strRtnRateCtrl.equals(""))
			{
				strRtnRateCtrl = "ItIsNotControl";
			}
			if (strRtnIntervalCtrl == null || strRtnIntervalCtrl.equals(""))
			{
				strRtnIntervalCtrl = "ItIsNotControl";
			}
			if (strRtnStartDateCtrl == null || strRtnStartDateCtrl.equals(""))
			{
				strRtnStartDateCtrl = "ItIsNotControl";
			}
			long lFromClient = 0;
			if (lClientID > 0)
			{
				lFromClient = 1;
			}
			/*String[] strMainNames =
			{ strCtrlName + "Ctrl", strRtnEndDateCtrl, strRtnOpenDateCtrl, strRtnCapitalCtrl, strRtnBalanceCtrl, strRtnRateCtrl, strRtnIntervalCtrl, strRtnStartDateCtrl };
		    String[] strMainFields = { "DepositNo", "EndDate", "OpenDate", "Capital", "Balance", "Rate", "Interval", "StartDate" };*/
			//String[] strMainNames =
			//{ strCtrlName + "Ctrl", strRtnEndDateCtrl, strRtnOpenDateCtrl, strRtnCapitalCtrl, strRtnBalanceCtrl, strRtnRateCtrl, strRtnIntervalCtrl, strRtnStartDateCtrl };
		    //String[] strMainFields = { "DepositNo", "EndDate", "OpenDate", "Capital", "Balance", "Rate", "Interval", "StartDate" };
			String[] strMainNames =
				{ strCtrlName + "Ctrl", strRtnEndDateCtrl,"lClientIDCtrl","strClientName","lAccountIDCtrlCtrl1","lAccountIDCtrlCtrl2","lAccountIDCtrlCtrl3","lClientID","lClientIDFromClient","lAccountID","lAccountIDClientID","lAccountIDGroupID"};
			String[] strMainFields = { "DepositNo", "EndDate","ClientNo","ClientName" ,"AccountNo1","AccountNo2","AccountNo3","ClientID","FromClient","AccountID","ClientID","accountgroupid"};
			//String[] strReturnNames = { strCtrlName, strCtrlName + "AccountID","lClientID","lClientIDFromClient","lAccountID","lAccountIDClientID","lAccountIDGroupID"};
			//String[] strReturnFields = { "SubAccountID", "AccountID","ClientID","FromClient","AccountID","ClientID","accountgroupid" };
			String[] strReturnNames = { strCtrlName, strCtrlName + "AccountID" };
			String[] strReturnFields = { "SubAccountID", "AccountID" };
			String[] strReturnValues = { String.valueOf(lSubAccountID), String.valueOf(lAccountID)};
			//String[] strReturnValues = { String.valueOf(lSubAccountID), String.valueOf(lAccountID),String.valueOf(lClientID), String.valueOf(lFromClient),String.valueOf(lAccountID), String.valueOf(lClientID), "-1"};
			String[] strDisplayNames = { URLEncoder.encode("存款单据号")};
			String[] strDisplayFields = { "DepositNo" };
			//支取或续期转存时，显示不同
			if (lTypeID == 21 || lTypeID == 22 || lTypeID == 3 || lTypeID == 0)
			{
				if (lDepositTypeID == 1)
				{
					//定期显示单据号、余额、到期日
					strDisplayNames = new String[] { URLEncoder.encode("存款单据号"), URLEncoder.encode("余额"), URLEncoder.encode("到期日")};
					strDisplayFields = new String[] { "DepositNo", "Balance", "EndDate" };
				}else if(lDepositTypeID == 6){
//					保证金显示单据号、可用余额、开户日期
					strDisplayNames = new String[] { URLEncoder.encode("存款单据号"), URLEncoder.encode("可用余额"), URLEncoder.encode("开户日")};
					strDisplayFields = new String[] { "DepositNo", "Balance", "OpenDate" };
				}
				else
				{
					//定期显示单据号、余额、开户日期
					strDisplayNames = new String[] { URLEncoder.encode("存款单据号"), URLEncoder.encode("余额"), URLEncoder.encode("开户日")};
					strDisplayFields = new String[] { "DepositNo", "Balance", "OpenDate" };
				}
			}
			int nIndex = 0;
			String strSQL = "";
			if (strAccountIDCtrl != null && !strAccountIDCtrl.trim().equals(""))
			{
				strSQL =
					"getFixedDepositNoSQLZJ("
						+ lOfficeID
						+ ","
						+ lCurrencyID
						+ ","
						+ lDepositTypeID
						+ ","
						+ strAccountIDCtrl
						+ ".value,"
						+ lUserID
						+ ","
						+ strCtrlName
						+ "Ctrl.value,"
						+ lTypeID
						+ ",'"
						+ Env.getSystemDateString(lOfficeID, lCurrencyID)
						+ "')";
			}
			else
			{
				strSQL =
					"getFixedDepositNoSQLZJ("
						+ lOfficeID
						+ ","
						+ lCurrencyID
						+ ","
						+ lDepositTypeID
						+ ",-1,"
						+ lUserID
						+ ","
						+ strCtrlName
						+ "Ctrl.value,"
						+ lTypeID
						+ ",'"
						+ Env.getSystemDateString(lOfficeID, lCurrencyID)
						+ "')";
			}
			try
			{
				SETTMagnifier.showZoomCtrl(
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
				Log.print("定期存款单据号放大镜[" + strCtrlName + "]异常：" + e.toString());
			}
		}
	/**
	 * 创建银企接口--成员单位账户放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lFilialeAccountID 成员单位账户ID(初识值)
	 * @param strFilialeAccountNo 成员单位账户编号(初识值)
	 * @param lBankTypeID 开户行类型ID(初识值)
	 * @param strBankTypeCtrl 银行类型ID对应的控件名称（查询时关联）
	 * @param lClientID 所属单位ID(初识值)
	 * @param strClientCtrl 所属单位ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnAccountNoCtrl 返回值（账户名称）对应的控件名
	 * @param strRtnAccountTypeCtrl 返回值（账户类型）对应的控件名
	 * @param strRtnFilialeNameCtrl 返回之（成员单位名称）对应的控件名
	 * @param strProperty 外加的控件属性，一般传空串即可 
	 */
	public static void createFilialeAccountNoCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lFilialeAccountID,
		String strFilialeAccountNo,
		long lBankTypeID,
		String strBankTypeCtrl,
		long lClinetID,
		String strClinetCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnAccountNameCtrl,
		String strRtnAccountTypeCtrl,
		String strRtnFilialeNameCtrl,
		String strProperty)
	{
		//放大镜控件非数组
		String strMagnifierName = URLEncoder.encode("银行账户编号");
		String strMainProperty = " maxlength='30' value='" + strFilialeAccountNo + "' ";
		if (strProperty != null && !strProperty.equals(""))
		{
			strMainProperty += strProperty;
		}
		String strPrefix = "";
		if (strRtnAccountNameCtrl == null || strRtnAccountNameCtrl.equals(""))
		{
			strRtnAccountNameCtrl = "ItIsNotControl";
		}
		if (strRtnAccountTypeCtrl == null || strRtnAccountTypeCtrl.equals(""))
		{
			strRtnAccountTypeCtrl = "ItIsNotControl";
		}
		if (strRtnFilialeNameCtrl == null || strRtnFilialeNameCtrl.equals(""))
		{
			strRtnFilialeNameCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnAccountNameCtrl, strRtnAccountTypeCtrl, strRtnFilialeNameCtrl };
		String[] strMainFields = { "BANKACCOUNTNO", "BANKACCOUNTNAME", "ACCOUNTTYPE", "FILIALENAME" };
		String[] strReturnNames = { strCtrlName, strCtrlName + "BankTypeID" };
		String[] strReturnFields = { "ID", "BANKTYPE" };
		String[] strReturnValues = { String.valueOf(lFilialeAccountID), String.valueOf(lBankTypeID)};
		String[] strDisplayNames = { URLEncoder.encode("银行账户编号"), URLEncoder.encode("成员单位名称")};
		String[] strDisplayFields = { "BANKACCOUNTNO", "FILIALENAME" };
		int nIndex = 0;
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getFilialeAccountNoSQL(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		if (strBankTypeCtrl != null && !strBankTypeCtrl.trim().equals(""))
		{
			sbSQL.append(strBankTypeCtrl + ".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(",");
		if (strClinetCtrl != null && !strClinetCtrl.trim().equals(""))
		{
			sbSQL.append(strClinetCtrl + ".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(",");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value)");
		try
		{
			SETTMagnifier.showZoomCtrl(
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
	 * 创建证券投资结算--证券业务通知单放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lSecNoticeFormID 通知单ID(初识值)
	 * @param strSecNoticeFormNo 通知单编号(初识值)
	 * @param lSettTransTypeID 结算交易类型ID(初识值，请使用结算的交易类型常量；-1，全部)
	 * @param lSecNoticeStatusID 通知单状态ID(初识值，请使用结算的通知单状态常量;-1,全部)
	 * 			如果是-100，表示没有已复核状态；
	 * 			如果是-200，表示包含已复核状态；
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnSecTransTypeDescCtrl 返回值（证券业务类型描述）对应的控件名
	 * @param strRtnCompanyBankIDCtrl 返回值（财务公司开户行ID）对应的控件名
	 * @param strRtnCompanyBankNameCtrl 返回值（财务公司开户行名称）对应的控件名
	 * @param strRtnCompanyBankAccountNoCtrl 返回值（财务公司银行账号）对应的控件名
	 * @param strRtnCompanyBankAccountNameCtrl 返回值（财务公司银行账户名称）对应的控件名
	 * @param strRtnCounterBankNameCtrl 返回值（交易对手开户行名称）对应的控件名
	 * @param strRtnCounterBankNoCtrl 返回值（交易对手银行账号）对应的控件名
	 * @param strRtnCounterBankAccountNameCtrl 返回值（交易对手银行账户名称）对应的控件名
	 * @param strRtnPlanAmountCtrl 返回值（应收付款金额）对应的控件名
	 * @param strRtnRealAmountCtrl 返回值（实际收付款金额）对应的控件名
	 * @param strRtnPayOrReceiveDateCtrl 返回值（收付款日期）对应的控件名
	 * @param strRtnAbstractCtrl 返回值（摘要）对应的控件名
	 * @param strRtnSecAccountTypeIDCtrl 返回值（账户类型/收付款方式）对应的控件名
	 * @param strRtnCurrentAccountIDCtrl 返回值（活期账户ID）对应的控件名
	 * @param strRtnCurrentAccountNoCtrl 返回值（活期账户编号）对应的控件名
	 * @param strRtnCurrentClientNameCtrl 返回值（活期客户名称）对应的控件名
	 * @param strRtnCounterPartNameCtrl 返回值（交易对手名称）对应的控件名
	 */
	public static void createSecNoticeFormCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lSecNoticeFormID,
		String strSecNoticeFormNo,
		long lSettTransTypeID,
		long lSecNoticeStatusID,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnSecTransTypeDescCtrl,
		String strRtnCompanyBankIDCtrl,
		String strRtnCompanyBankNameCtrl,
		String strRtnCompanyBankAccountNoCtrl,
		String strRtnCompanyBankAccountNameCtrl,
		String strRtnCounterBankNameCtrl,
		String strRtnCounterBankNoCtrl,
		String strRtnCounterBankAccountNameCtrl,
		String strRtnPlanAmountCtrl,
		String strRtnRealAmountCtrl,
		String strRtnPayOrReceiveDateCtrl,
		String strRtnAbstractCtrl,
		String strRtnSecAccountTypeIDCtrl,
		String strRtnCurrentAccountIDCtrl,
		String strRtnCurrentAccountNoCtrl,
		String strRtnCurrentClientNameCtrl,
		String strRtnCounterPartNameCtrl)
	{
		//放大镜控件非数组
		String strMagnifierName = URLEncoder.encode("证券业务通知单");
		String strMainProperty = " maxlength='30' value='" + strSecNoticeFormNo + "'";
		String strPrefix = "";
		if (strRtnSecTransTypeDescCtrl == null || strRtnSecTransTypeDescCtrl.equals(""))
		{
			strRtnSecTransTypeDescCtrl = "N";
		}
		if (strRtnCompanyBankIDCtrl == null || strRtnCompanyBankIDCtrl.equals(""))
		{
			strRtnCompanyBankIDCtrl = "N";
		}
		if (strRtnCompanyBankNameCtrl == null || strRtnCompanyBankNameCtrl.equals(""))
		{
			strRtnCompanyBankNameCtrl = "N";
		}
		if (strRtnCompanyBankAccountNoCtrl == null || strRtnCompanyBankAccountNoCtrl.equals(""))
		{
			strRtnCompanyBankAccountNoCtrl = "N";
		}
		if (strRtnCompanyBankAccountNameCtrl == null || strRtnCompanyBankAccountNameCtrl.equals(""))
		{
			strRtnCompanyBankAccountNameCtrl = "N";
		}
		if (strRtnCounterBankNameCtrl == null || strRtnCounterBankNameCtrl.equals(""))
		{
			strRtnCounterBankNameCtrl = "N";
		}
		if (strRtnCounterBankNoCtrl == null || strRtnCounterBankNoCtrl.equals(""))
		{
			strRtnCounterBankNoCtrl = "N";
		}
		if (strRtnCounterBankAccountNameCtrl == null || strRtnCounterBankAccountNameCtrl.equals(""))
		{
			strRtnCounterBankAccountNameCtrl = "N";
		}
		if (strRtnPlanAmountCtrl == null || strRtnPlanAmountCtrl.equals(""))
		{
			strRtnPlanAmountCtrl = "N";
		}
		if (strRtnRealAmountCtrl == null || strRtnRealAmountCtrl.equals(""))
		{
			strRtnRealAmountCtrl = "N";
		}
		if (strRtnPayOrReceiveDateCtrl == null || strRtnPayOrReceiveDateCtrl.equals(""))
		{
			strRtnPayOrReceiveDateCtrl = "N";
		}
		if (strRtnAbstractCtrl == null || strRtnAbstractCtrl.equals(""))
		{
			strRtnAbstractCtrl = "N";
		}
		if (strRtnSecAccountTypeIDCtrl == null || strRtnSecAccountTypeIDCtrl.equals(""))
		{
			strRtnSecAccountTypeIDCtrl = "N";
		}
		if (strRtnCurrentAccountIDCtrl == null || strRtnCurrentAccountIDCtrl.equals(""))
		{
			strRtnCurrentAccountIDCtrl = "N";
		}
		if (strRtnCurrentAccountNoCtrl == null || strRtnCurrentAccountNoCtrl.equals(""))
		{
			strRtnCurrentAccountNoCtrl = "N";
		}
		if (strRtnCurrentClientNameCtrl == null || strRtnCurrentClientNameCtrl.equals(""))
		{
			strRtnCurrentClientNameCtrl = "N";
		}
		if (strRtnCounterPartNameCtrl == null || strRtnCounterPartNameCtrl.equals(""))
		{
			strRtnCounterPartNameCtrl = "N";
		}
		String[] strMainNames =
			{
				strCtrlName + "Ctrl",
				strRtnSecTransTypeDescCtrl,
				strRtnCompanyBankIDCtrl,
				strRtnCompanyBankNameCtrl,
				strRtnCompanyBankAccountNoCtrl,
				strRtnCompanyBankAccountNameCtrl,
				strRtnCounterBankNameCtrl,
				strRtnCounterBankNoCtrl,
				strRtnCounterBankAccountNameCtrl,
				strRtnPlanAmountCtrl,
				strRtnRealAmountCtrl,
				strRtnPayOrReceiveDateCtrl,
				strRtnAbstractCtrl,
				strRtnSecAccountTypeIDCtrl,
				strRtnCurrentAccountIDCtrl,
				strRtnCurrentAccountNoCtrl,
				strRtnCurrentClientNameCtrl,
				strRtnCounterPartNameCtrl };
		String[] strMainFields =
			{ "NtcNo", "STTDesc", "CBID", "CBNm", "CBANo", "CBANm", "OBNm", "OBNo", "OBANm", "PAmt", "RAmt", "PRDate", "Abstract", "RPType", "CAID", "CANo", "CCNm", "CPName" };
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "NtcID" };
		String[] strReturnValues = { String.valueOf(lSecNoticeFormID)};
		String[] strDisplayNames = { URLEncoder.encode("编号"), URLEncoder.encode("业务类型")};
		String[] strDisplayFields = { "NtcNo", "STTDesc" };
		int nIndex = 0;
		String strDirection = null; //资金方向
		String strSecTransType = null; //证券交易类型
		if (lSettTransTypeID == SETTConstant.TransactionType.SECURITIESPAY)
		{
			//财务公司付款
			strDirection = "'" + SECConstant.Direction.FINANCE_PAY + "," + SECConstant.Direction.RECEIVE_AND_FINANCE_PAY + "'";
			strSecTransType = "'-100'";
		}
		else
			if (lSettTransTypeID == SETTConstant.TransactionType.SECURITIESRECEIVE)
			{
				//财务公司收款
				strDirection = "'" + SECConstant.Direction.FINANCE_RECEIVE + "," + SECConstant.Direction.PAY_AND_FINANCE_RECEIVE + "'";
				strSecTransType = "'-100'";
			}
			else
				if (lSettTransTypeID == SETTConstant.TransactionType.SEC_WTLC_PAY)
				{
					//委托理财付款
					strDirection = "'-1'";
					strSecTransType = "'" + SECConstant.BusinessType.ENTRUST_FINANCING.CORPORACITY_PAYMENT_NOTIFY + "'";
				}
				else
					if (lSettTransTypeID == SETTConstant.TransactionType.SEC_WTLC_RECEIVE)
					{
						//委托理财收款
						strDirection = "'-1'";
						strSecTransType =
							"'"
								+ SECConstant.BusinessType.ENTRUST_FINANCING.CORPORACITY_DRAWBACK_NOTIFY
								+ ","
								+ SECConstant.BusinessType.ENTRUST_FINANCING.INCOME_DRAWBACK_NOTIFY
								+ "'";
					}
					else
						if (lSettTransTypeID == SETTConstant.TransactionType.SEC_ZQCX_PAY)
						{
							//债券承销付款
							strDirection = "'-1'";
							strSecTransType = "'" + SECConstant.BusinessType.BOND_UNDERWRITING.UNDERWRITING_PAYMENT_NOTIFY + "'";
						}
						else
							if (lSettTransTypeID == SETTConstant.TransactionType.SEC_ZQCX_RECEIVE)
							{
								//债券承销收款
								strDirection = "'-1'";
								strSecTransType =
									"'"
										+ SECConstant.BusinessType.BOND_UNDERWRITING.INCOME_DRAWBACK_NOTIFY
										+ ","
										+ SECConstant.BusinessType.BOND_UNDERWRITING.UNDERWRITING_DRAWBACK_NOTIFY
										+ "'";
							}
							else
							{
								strDirection =
									"'"
										+ SECConstant.Direction.FINANCE_PAY
										+ ","
										+ SECConstant.Direction.PAY_AND_FINANCE_RECEIVE
										+ ","
										+ SECConstant.Direction.FINANCE_RECEIVE
										+ ","
										+ SECConstant.Direction.RECEIVE_AND_FINANCE_PAY
										+ "'";
								strSecTransType = "'-1'";
							}
		String strStatus = null;
		if (lSecNoticeStatusID > 0)
		{
			strStatus = "'" + lSecNoticeStatusID + "'";
		}
		else
			if (lSecNoticeStatusID == -200)
			{
				strStatus =
					"'"
						+ SECConstant.NoticeFormStatus.CHECKED
						+ ","
						+ SECConstant.NoticeFormStatus.USED
						+ ","
						+ SECConstant.NoticeFormStatus.COMPLETED
						+ ","
						+ SECConstant.NoticeFormStatus.POSTED
						+ "'";
			}
			else
				if (lSecNoticeStatusID == -100)
				{
					strStatus = "'" + SECConstant.NoticeFormStatus.USED + "," + SECConstant.NoticeFormStatus.COMPLETED + "," + SECConstant.NoticeFormStatus.POSTED + "'";
				}
				else
				{
					strStatus = "'-1'";
				}
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getSecNoticeFormSQL(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		sbSQL.append(strDirection);
		sbSQL.append(",");
		sbSQL.append(strSecTransType);
		sbSQL.append(",");
		sbSQL.append(strStatus);
		sbSQL.append(",");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value)");
		try
		{
			SETTMagnifier.showZoomCtrl(
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
	 * 创建免还通知单放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lFreeFormID 免还通知单ID(初识值)
	 * @param strFreeFormNo 免还通知单号(初识值)
	 * @param lPayFormID 放款通知单ID(初识值)
	 * @param lTypeID 使用状态
	 * （0，全部；1，处理；2，复核。）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnFreeAmountCtrl 返回值（免还金额）对应的控件名
	 * @param strRtnFreeInterestCtrl 返回值（免还利息）对应的控件名
	 * @param strRtnClientNoCtrl 返回值（贷款客户编号）对应的控件名
	 * @param strRtnContractNoCtrl 返回值（合同编号）对应的控件名
	 * @param strRtnPayFormNoCtrl 返回值（放款通知单编号）对应的控件名
	 */
	public static void createFreeFormCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lFreeFormID,
		String strFreeFormNo,
		long lPayFormID,
		long lTypeID,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnFreeAmountCtrl,
		String strRtnFreeInterestCtrl,
		String strRtnClientNoCtrl,
		String strRtnContractNoCtrl,
		String strRtnPayFormNoCtrl)
	{
		String strMagnifierName = URLEncoder.encode("免还通知单");
		String strMainProperty = " maxlength='30' value='" + strFreeFormNo + "'";
		String strPrefix = "";
		if (strRtnFreeAmountCtrl == null || strRtnFreeAmountCtrl.equals(""))
		{
			strRtnFreeAmountCtrl = "ItIsNotControl";
		}
		if (strRtnFreeInterestCtrl == null || strRtnFreeInterestCtrl.equals(""))
		{
			strRtnFreeInterestCtrl = "ItIsNotControl";
		}
		if (strRtnClientNoCtrl == null || strRtnClientNoCtrl.equals(""))
		{
			strRtnClientNoCtrl = "ItIsNotControl";
		}
		if (strRtnContractNoCtrl == null || strRtnContractNoCtrl.equals(""))
		{
			strRtnContractNoCtrl = "ItIsNotControl";
		}
		if (strRtnContractNoCtrl == null || strRtnContractNoCtrl.equals(""))
		{
			strRtnContractNoCtrl = "ItIsNotControl";
		}
		if (strRtnPayFormNoCtrl == null || strRtnPayFormNoCtrl.equals(""))
		{
			strRtnPayFormNoCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnFreeAmountCtrl, strRtnFreeInterestCtrl, strRtnClientNoCtrl, strRtnContractNoCtrl, strRtnPayFormNoCtrl };
		String[] strMainFields = { "FreeFormNo", "FreeAmount", "FreeInterest", "ClientNo", "ContractNo", "PayFormNo" };
		String[] strReturnNames = { strCtrlName, strCtrlName + "PayFormID" };
		String[] strReturnFields = { "FreeFormID", "PayFormID" };
		String[] strReturnValues = { String.valueOf(lFreeFormID), String.valueOf(lPayFormID)};
		String[] strDisplayNames = { URLEncoder.encode("免还通知单号"), URLEncoder.encode("合同号"), URLEncoder.encode("放款通知单号"), URLEncoder.encode("免还金额"), URLEncoder.encode("免还利息")};
		String[] strDisplayFields = { "FreeFormNo", "ContractNo", "PayFormNo", "FreeAmount", "FreeInterest" };
		int nIndex = 0;
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getFreeFormSQL(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",'");
		if (lTypeID == 1)
		{
			sbSQL.append(LOANConstant.FreeApplyStatus.CHECK);
		}
		else
			if (lTypeID == 2)
			{
				sbSQL.append(LOANConstant.FreeApplyStatus.USED);
			}
			else
				if (lTypeID == 0)
				{
					sbSQL.append(LOANConstant.FreeApplyStatus.CHECK + "," + LOANConstant.FreeApplyStatus.USED);
				}
		sbSQL.append("',");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value)");
		//Log.print(sbSQL.toString());
		try
		{
			SETTMagnifier.showZoomCtrl(
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
	 * 创建免还通知单放大镜 可以返回合同id,放款通知单id,起始日期
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lFreeFormID 免还通知单ID(初识值)
	 * @param strFreeFormNo 免还通知单号(初识值)
	 * @param lPayFormID 放款通知单ID(初识值)
	 * @param lTypeID 使用状态
	 * （0，全部；1，处理；2，复核。）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnFreeAmountCtrl 返回值（免还金额）对应的控件名
	 * @param strRtnFreeInterestCtrl 返回值（免还利息）对应的控件名
	 * @param strRtnClientNoCtrl 返回值（贷款客户编号）对应的控件名
	 * @param strRtnClientNameCtrl 返回值（贷款客户姓名）对应的控件名
	 * @param strRtnContractNoCtrl 返回值（合同编号）对应的控件名
	 * @param lRtnContractID 返回值（合同id）对应的控件名
	 * @param strRtnPayFormNoCtrl 返回值（放款通知单编号）对应的控件名
	 * @param lRtnPayFormID 返回值（放款通知单id）对应的控件名
	 * @param strRtnRePayDateCtrl 返回值（起始日期）对应的控件名
	 */
	public static void createFreeFormCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lFreeFormID,
		String strFreeFormNo,
		long lPayFormID,
		long lTypeID,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnFreeAmountCtrl,
		String strRtnFreeInterestCtrl,
		String strRtnClientNoCtrl,
		String strRtnClientNameCtrl,
		String strRtnContractNoCtrl,		
		String strRtnPayFormNoCtrl,
		String lRtnContractID,
		String lRtnPayFormID,
		String strRtnRePayDateCtrl)
	{
		String strMagnifierName = URLEncoder.encode("免还通知单");
		String strMainProperty = " maxlength='30' value='" + strFreeFormNo + "'";
		String strPrefix = "";
		if (strRtnFreeAmountCtrl == null || strRtnFreeAmountCtrl.equals(""))
		{
			strRtnFreeAmountCtrl = "ItIsNotControl";
		}
		if (strRtnFreeInterestCtrl == null || strRtnFreeInterestCtrl.equals(""))
		{
			strRtnFreeInterestCtrl = "ItIsNotControl";
		}
		if (strRtnClientNoCtrl == null || strRtnClientNoCtrl.equals(""))
		{
			strRtnClientNoCtrl = "ItIsNotControl";
		}
		if (strRtnContractNoCtrl == null || strRtnContractNoCtrl.equals(""))
		{
			strRtnContractNoCtrl = "ItIsNotControl";
		}
		if (strRtnContractNoCtrl == null || strRtnContractNoCtrl.equals(""))
		{
			strRtnContractNoCtrl = "ItIsNotControl";
		}
		if (strRtnPayFormNoCtrl == null || strRtnPayFormNoCtrl.equals(""))
		{
			strRtnPayFormNoCtrl = "ItIsNotControl";
		}
		if (lRtnContractID == null || lRtnContractID.equals(""))
		{
			lRtnContractID = "ItIsNotControl";
		}
		if (lRtnPayFormID == null || lRtnPayFormID.equals(""))
		{
			lRtnPayFormID = "ItIsNotControl";
		}
		if (strRtnClientNameCtrl == null || strRtnClientNameCtrl.equals(""))
		{
			strRtnClientNameCtrl = "ItIsNotControl";
		}if (strRtnRePayDateCtrl == null || strRtnRePayDateCtrl.equals(""))
		{
			strRtnRePayDateCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnFreeAmountCtrl, strRtnFreeInterestCtrl, strRtnClientNoCtrl, strRtnClientNameCtrl,strRtnContractNoCtrl, strRtnPayFormNoCtrl,lRtnContractID,lRtnPayFormID,strRtnRePayDateCtrl};
		String[] strMainFields = { "FreeFormNo", "FreeAmount", "FreeInterest", "ClientNo","clientName", "ContractNo", "PayFormNo","ContractID","PayFormID","PFStartDate" };
		String[] strReturnNames = { strCtrlName, strCtrlName + "PayFormID" };
		String[] strReturnFields = { "FreeFormID", "PayFormID" };
		String[] strReturnValues = { String.valueOf(lFreeFormID), String.valueOf(lPayFormID)};
		String[] strDisplayNames = { URLEncoder.encode("免还通知单号"), URLEncoder.encode("合同号"), URLEncoder.encode("放款通知单号"), URLEncoder.encode("免还金额"), URLEncoder.encode("免还利息")};
		String[] strDisplayFields = { "FreeFormNo", "ContractNo", "PayFormNo", "FreeAmount", "FreeInterest" };
		int nIndex = 0;
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getFreeFormSQL(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",'");
		if (lTypeID == 1)
		{
			sbSQL.append(LOANConstant.FreeApplyStatus.CHECK);
		}
		else
			if (lTypeID == 2)
			{
				sbSQL.append(LOANConstant.FreeApplyStatus.USED);
			}
			else
				if (lTypeID == 0)
				{
					sbSQL.append(LOANConstant.FreeApplyStatus.CHECK + "," + LOANConstant.FreeApplyStatus.USED);
				}
		sbSQL.append("',");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value)");
		//Log.print(sbSQL.toString());
		try
		{
			SETTMagnifier.showZoomCtrl(
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
	 * 创建免还通知单放大镜 可以返回合同id,放款通知单id,起始日期
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lFreeFormID 免还通知单ID(初识值)
	 * @param strFreeFormNo 免还通知单号(初识值)
	 * @param lPayFormID 放款通知单ID(初识值)
	 * @param lTypeID 使用状态
	 * （0，全部；1，处理；2，复核。）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnFreeAmountCtrl 返回值（免还金额）对应的控件名
	 * @param strRtnFreeInterestCtrl 返回值（免还利息）对应的控件名
	 * @param strRtnClientNoCtrl 返回值（贷款客户编号）对应的控件名
	 * @param strRtnClientNameCtrl 返回值（贷款客户姓名）对应的控件名
	 * @param strRtnContractNoCtrl 返回值（合同编号）对应的控件名
	 * @param lRtnContractID 返回值（合同id）对应的控件名
	 * @param strRtnPayFormNoCtrl 返回值（放款通知单编号）对应的控件名
	 * @param lRtnPayFormID 返回值（放款通知单id）对应的控件名
	 * @param strRtnRePayDateCtrl 返回值（起始日期）对应的控件名
	 */
	public static void createFreeFormCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lFreeFormID,
		String strFreeFormNo,
		long lPayFormID,
		long lTypeID,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnFreeAmountCtrl,
		String strRtnFreeInterestCtrl,
		String strClientId,
		String strRtnClientNoCtrl,
		String strRtnClientNameCtrl,
		String strRtnContractNoCtrl,		
		String strRtnPayFormNoCtrl,
		String lRtnContractID,
		String lRtnPayFormID,
		String strRtnRePayDateCtrl)
	{
		String strMagnifierName = URLEncoder.encode("免还通知单");
		String strMainProperty = " maxlength='30' value='" + strFreeFormNo + "'";
		String strPrefix = "";
		if (strRtnFreeAmountCtrl == null || strRtnFreeAmountCtrl.equals(""))
		{
			strRtnFreeAmountCtrl = "ItIsNotControl";
		}
		if (strRtnFreeInterestCtrl == null || strRtnFreeInterestCtrl.equals(""))
		{
			strRtnFreeInterestCtrl = "ItIsNotControl";
		}
		if (strRtnClientNoCtrl == null || strRtnClientNoCtrl.equals(""))
		{
			strRtnClientNoCtrl = "ItIsNotControl";
		}
		if (strRtnContractNoCtrl == null || strRtnContractNoCtrl.equals(""))
		{
			strRtnContractNoCtrl = "ItIsNotControl";
		}
		if (strRtnContractNoCtrl == null || strRtnContractNoCtrl.equals(""))
		{
			strRtnContractNoCtrl = "ItIsNotControl";
		}
		if (strRtnPayFormNoCtrl == null || strRtnPayFormNoCtrl.equals(""))
		{
			strRtnPayFormNoCtrl = "ItIsNotControl";
		}
		if (lRtnContractID == null || lRtnContractID.equals(""))
		{
			lRtnContractID = "ItIsNotControl";
		}
		if (lRtnPayFormID == null || lRtnPayFormID.equals(""))
		{
			lRtnPayFormID = "ItIsNotControl";
		}
		if (strRtnClientNameCtrl == null || strRtnClientNameCtrl.equals(""))
		{
			strRtnClientNameCtrl = "ItIsNotControl";
		}if (strRtnRePayDateCtrl == null || strRtnRePayDateCtrl.equals(""))
		{
			strRtnRePayDateCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnFreeAmountCtrl, strRtnFreeInterestCtrl, strClientId, strRtnClientNoCtrl, strRtnClientNameCtrl,strRtnContractNoCtrl, strRtnPayFormNoCtrl,lRtnContractID,lRtnPayFormID,strRtnRePayDateCtrl};
		String[] strMainFields = { "FreeFormNo", "FreeAmount", "FreeInterest", "clientId", "ClientNo","clientName", "ContractNo", "PayFormNo","ContractID","PayFormID","PFStartDate" };
		String[] strReturnNames = { strCtrlName, strCtrlName + "PayFormID" };
		String[] strReturnFields = { "FreeFormID", "PayFormID" };
		String[] strReturnValues = { String.valueOf(lFreeFormID), String.valueOf(lPayFormID)};
		String[] strDisplayNames = { URLEncoder.encode("免还通知单号"), URLEncoder.encode("合同号"), URLEncoder.encode("放款通知单号"), URLEncoder.encode("免还金额"), URLEncoder.encode("免还利息")};
		String[] strDisplayFields = { "FreeFormNo", "ContractNo", "PayFormNo", "FreeAmount", "FreeInterest" };
		int nIndex = 0;
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getFreeFormSQL(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",'");
		if (lTypeID == 1)
		{
			sbSQL.append(LOANConstant.FreeApplyStatus.CHECK);
		}
		else
			if (lTypeID == 2)
			{
				sbSQL.append(LOANConstant.FreeApplyStatus.USED);
			}
			else
				if (lTypeID == 0)
				{
					sbSQL.append(LOANConstant.FreeApplyStatus.CHECK + "," + LOANConstant.FreeApplyStatus.USED);
				}
		sbSQL.append("',");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value)");
		//Log.print(sbSQL.toString());
		try
		{
			SETTMagnifier.showZoomCtrl(
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
	 * 创建放款通知单放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lContractID 合同ID(初识值)
	 * @param lPayFormID 放款通知单ID(初识值)
	 * @param strPayFormNo 放款通知单号(初识值)
	 * @param lPayFormTypeID 放款通知单类型(查询条件:1,信托；2，委托)
	 * @param lStatusID 放款通知单状态(内部状态：
	 * 		0，显示全部；
	 * 		1，信托/委托发放——业务处理使用；
	 * 		2，信托/委托发放——业务复核使用；
	 * 		3，信托/委托收回——业务处理使用；
	 * 		4，信托/委托收回——业务复核使用。）
	 * @param strContractCtrl 合同ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 */
	public static void createPayFormCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lContractID,
		long lPayFormID,
		String strPayFormNo,
		long lPayFormTypeID,
		long lStatusID,
		String strContractCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls)
	{
		createPayFormCtrl(
			out,
			lOfficeID,
			lCurrencyID,
			strFormName,
			strCtrlName,
			strTitle,
			lContractID,
			lPayFormID,
			strPayFormNo,
			lPayFormTypeID,
			lStatusID,
			strContractCtrl,
			strFirstTD,
			strSecondTD,
			strNextControls,
			"");
	}
	
	/**
	 * 创建贴现汇票放大镜(银行承兑汇票号)
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lDiscountCredenceID 贴现凭证ID(初识值)
	 * @param lDiscountBillID 贴现汇票ID(初识值)
	 * @param strDiscountBillNo 贴现汇票编号(初识值)
	 * @param strDiscountCredenceCtrl 贴现凭证ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnDiscountDateCtrl 返回值（贴现票据贴现日）对应的控件名
	 * @param strRtnEndDateCtrl 返回值（贴现票据到期日期）对应的控件名
	 * @param strRtnBillAmountCtrl1 返回值（票面金额）对应的控件名
	 * @param strRtnBillAmountCtrl2 返回值（票面金额）对应的控件名
	 * @param strRtnDiscountAmountCtrl 返回值（实际贴现金额）对应的控件名
	 * @param strRtnDelayInterestCtrl 返回值（罚息）对应的控件名
	 */
	public static void createDiscountBillCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lDiscountCredenceID,
		long lDiscountBillID,
		String strDiscountBillNo,
		String strDiscountCredenceCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnDiscountDateCtrl,
		String strRtnEndDateCtrl,
		String strRtnBillAmountCtrl1,
		String strRtnBillAmountCtrl2,
		String strRtnDiscountAmountCtrl,
		String strRtnDelayInterestCtrl)
	{
		createDiscountBillCtrl(
				out,
				lOfficeID,
				lCurrencyID,
				strFormName,
				strCtrlName,
				strTitle,
				lDiscountCredenceID,
				lDiscountBillID,
				strDiscountBillNo,
				"",
				strDiscountCredenceCtrl,
				strFirstTD,
				strSecondTD,
				strNextControls,
				strRtnDiscountDateCtrl,
				strRtnEndDateCtrl,
				strRtnBillAmountCtrl1,
				strRtnBillAmountCtrl2,
				strRtnDiscountAmountCtrl,
				strRtnDelayInterestCtrl,
				"",
				"",
				"",
				"");
	}
	/**
	 * 创建贴现汇票放大镜(银行承兑汇票号)
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lDiscountCredenceID 贴现凭证ID(初识值)
	 * @param lDiscountBillID 贴现汇票ID(初识值)
	 * @param strDiscountBillNo 贴现汇票编号(初识值)
	 * @param strDiscountCredenceCtrl 贴现凭证ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnDiscountDateCtrl 返回值（贴现票据贴现日）对应的控件名
	 * @param strRtnEndDateCtrl 返回值（贴现票据到期日期）对应的控件名
	 * @param strRtnBillAmountCtrl1 返回值（票面金额）对应的控件名
	 * @param strRtnBillAmountCtrl2 返回值（票面金额）对应的控件名
	 * @param strRtnDiscountAmountCtrl 返回值（实际贴现金额）对应的控件名
	 * @param strRtnDelayInterestCtrl 返回值（罚息）对应的控件名
	 */
	public static void createDiscountBillCtrlExtNew(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lDiscountCredenceID,
		long lDiscountBillID,
		String strDiscountBillNo,
		String strDiscountCredenceCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnDiscountDateCtrl,
		String strRtnEndDateCtrl,
		String strRtnBillAmountCtrl1,
		String strRtnBillAmountCtrl2,
		String strRtnDiscountAmountCtrl,
		String strRtnDelayInterestCtrl,String dtDiscountDate,String discountContractBillIDs)
	{
		createDiscountBillCtrlExtNew(
				out,
				lOfficeID,
				lCurrencyID,
				strFormName,
				strCtrlName,
				strTitle,
				lDiscountCredenceID,
				lDiscountBillID,
				strDiscountBillNo,
				"",
				strDiscountCredenceCtrl,
				strFirstTD,
				strSecondTD,
				strNextControls,
				strRtnDiscountDateCtrl,
				strRtnEndDateCtrl,
				strRtnBillAmountCtrl1,
				strRtnBillAmountCtrl2,
				strRtnDiscountAmountCtrl,
				strRtnDelayInterestCtrl,
				"",
				"",
				"",
				"",dtDiscountDate,discountContractBillIDs);
	}	
	
	/**
	 * 创建贴现汇票放大镜(银行承兑汇票号)
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lDiscountCredenceID 贴现凭证ID(初识值)
	 * @param lDiscountBillID 贴现汇票ID(初识值)
	 * @param strDiscountBillNo 贴现汇票编号(初识值)
	 * @param strDiscountCredenceCtrl 贴现凭证ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnDiscountDateCtrl 返回值（贴现票据贴现日）对应的控件名
	 * @param strRtnEndDateCtrl 返回值（贴现票据到期日期）对应的控件名
	 * @param strRtnBillAmountCtrl1 返回值（票面金额）对应的控件名
	 * @param strRtnBillAmountCtrl2 返回值（票面金额）对应的控件名
	 * @param strRtnDiscountAmountCtrl 返回值（实际贴现金额）对应的控件名
	 * @param strRtnDelayInterestCtrl 返回值（罚息）对应的控件名
	 */
	public static void createDiscountBillCtrlExt(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lDiscountCredenceID,
		long lDiscountBillID,
		String strDiscountBillNo,
		String strDiscountCredenceCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnDiscountDateCtrl,
		String strRtnEndDateCtrl,
		String strRtnBillAmountCtrl1,
		String strRtnBillAmountCtrl2,
		String strRtnDiscountAmountCtrl,
		String strRtnDelayInterestCtrl,String dtDiscountDate)
	{
		createDiscountBillCtrlExt(
				out,
				lOfficeID,
				lCurrencyID,
				strFormName,
				strCtrlName,
				strTitle,
				lDiscountCredenceID,
				lDiscountBillID,
				strDiscountBillNo,
				"",
				strDiscountCredenceCtrl,
				strFirstTD,
				strSecondTD,
				strNextControls,
				strRtnDiscountDateCtrl,
				strRtnEndDateCtrl,
				strRtnBillAmountCtrl1,
				strRtnBillAmountCtrl2,
				strRtnDiscountAmountCtrl,
				strRtnDelayInterestCtrl,
				"",
				"",
				"",
				"",dtDiscountDate);
	}

	/**
	 * 创建贴现收回汇票放大镜(银行承兑汇票号lidi20101201)因为武钢项目没有申请直接出合同所以放大镜中去掉申请表关联
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lDiscountCredenceID 贴现凭证ID(初识值)
	 * @param lDiscountBillID 贴现汇票ID(初识值)
	 * @param strDiscountBillNo 贴现汇票编号(初识值)
	 * @param strDiscountCredenceCtrl 贴现凭证ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnDiscountDateCtrl 返回值（贴现票据贴现日）对应的控件名
	 * @param strRtnEndDateCtrl 返回值（贴现票据到期日期）对应的控件名
	 * @param strRtnBillAmountCtrl1 返回值（票面金额）对应的控件名
	 * @param strRtnBillAmountCtrl2 返回值（票面金额）对应的控件名
	 * @param strRtnDiscountAmountCtrl 返回值（实际贴现金额）对应的控件名
	 * @param strRtnDelayInterestCtrl 返回值（罚息）对应的控件名
	 */
	public static void createDiscountBillCtrlforwg(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lDiscountCredenceID,
		long lDiscountBillID,
		String strDiscountBillNo,
		String strDiscountCredenceCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnDiscountDateCtrl,
		String strRtnEndDateCtrl,
		String strRtnBillAmountCtrl1,
		String strRtnBillAmountCtrl2,
		String strRtnDiscountAmountCtrl,
		String strRtnDelayInterestCtrl,String dtDiscountDate)
	{
		createDiscountBillCtrlExtforwg(
				out,
				lOfficeID,
				lCurrencyID,
				strFormName,
				strCtrlName,
				strTitle,
				lDiscountCredenceID,
				lDiscountBillID,
				strDiscountBillNo,
				"",
				strDiscountCredenceCtrl,
				strFirstTD,
				strSecondTD,
				strNextControls,
				strRtnDiscountDateCtrl,
				strRtnEndDateCtrl,
				strRtnBillAmountCtrl1,
				strRtnBillAmountCtrl2,
				strRtnDiscountAmountCtrl,
				strRtnDelayInterestCtrl,
				"",
				"",
				"",
				"",dtDiscountDate);
	}	
	
	/**
	 * 创建贴现收回汇票放大镜(银行承兑汇票号lidi20101201)因为武钢项目没有申请直接出合同所以放大镜中去掉申请表关联
	 * 过滤掉已经做过转贴现卖出买断发放的票据
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lDiscountCredenceID 贴现凭证ID(初识值)
	 * @param lDiscountBillID 贴现汇票ID(初识值)
	 * @param strDiscountBillNo 贴现汇票编号(初识值)
	 * @param strDiscountCredenceCtrl 贴现凭证ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnDiscountDateCtrl 返回值（贴现票据贴现日）对应的控件名
	 * @param strRtnEndDateCtrl 返回值（贴现票据到期日期）对应的控件名
	 * @param strRtnBillAmountCtrl1 返回值（票面金额）对应的控件名
	 * @param strRtnBillAmountCtrl2 返回值（票面金额）对应的控件名
	 * @param strRtnDiscountAmountCtrl 返回值（实际贴现金额）对应的控件名
	 * @param strRtnDelayInterestCtrl 返回值（罚息）对应的控件名
	 */
	public static void createDiscountBillCtrlforwgNew(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lDiscountCredenceID,
		long lDiscountBillID,
		String strDiscountBillNo,
		String strDiscountCredenceCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnDiscountDateCtrl,
		String strRtnEndDateCtrl,
		String strRtnBillAmountCtrl1,
		String strRtnBillAmountCtrl2,
		String strRtnDiscountAmountCtrl,
		String strRtnDelayInterestCtrl,String dtDiscountDate,String discountContractBillIDs)
	{
		createDiscountBillCtrlExtforwgNew(
				out,
				lOfficeID,
				lCurrencyID,
				strFormName,
				strCtrlName,
				strTitle,
				lDiscountCredenceID,
				lDiscountBillID,
				strDiscountBillNo,
				"",
				strDiscountCredenceCtrl,
				strFirstTD,
				strSecondTD,
				strNextControls,
				strRtnDiscountDateCtrl,
				strRtnEndDateCtrl,
				strRtnBillAmountCtrl1,
				strRtnBillAmountCtrl2,
				strRtnDiscountAmountCtrl,
				strRtnDelayInterestCtrl,
				"",
				"",
				"",
				"",dtDiscountDate,discountContractBillIDs);
	}	
	
	/**
	 * 创建贴现汇票放大镜(银行承兑汇票号)
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lDiscountCredenceID 贴现凭证ID(初识值)
	 * @param lDiscountBillID 贴现汇票ID(初识值)
	 * @param strDiscountBillNo 贴现汇票编号(初识值)
	 * @param strDiscountContractCtrl 贴现合同ID对应的控件名称（查询时关联）
	 * @param strDiscountCredenceCtrl 贴现凭证ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnDiscountDateCtrl 返回值（贴现票据贴现日）对应的控件名
	 * @param strRtnEndDateCtrl 返回值（贴现票据到期日期）对应的控件名
	 * @param strRtnBillAmountCtrl1 返回值（票面金额）对应的控件名
	 * @param strRtnBillAmountCtrl2 返回值（票面金额）对应的控件名
	 * @param strRtnDiscountAmountCtrl 返回值（实际贴现金额）对应的控件名
	 * @param strRtnDelayInterestCtrl 返回值（罚息）对应的控件名
	 * @param strRtnIsLocalIDCtrl 返回值（是否本地号）对应的控件名
	 * @param strRtnIsLocalIDDescCtrl 返回值（是否本地）对应的控件名
	 * @param strRtnAcceptPOTypeIDCtrl 返回值（汇票类型号）对应的控件名
	 * @param strRtnAcceptPOTypeIDDescCtrl 返回值（汇票类型）对应的控件名
	 */
	public static void createDiscountBillReturnCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lDiscountCredenceID,
		long lDiscountBillID,
		String strDiscountBillNo,
		String strDiscountContractCtrl,
		String strDiscountCredenceCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnDiscountDateCtrl,
		String strRtnEndDateCtrl,
		String strRtnBillAmountCtrl1,
		String strRtnBillAmountCtrl2,
		String strRtnDiscountAmountCtrl,
		String strRtnDelayInterestCtrl,
		String strRtnContractIdCtrl,
		String strRtnContractNoCtrl,
		String strRtnDiscountCredenceIdCtrl,
		String strRtnDiscountCredenceNoCtrl
		
		)
	{
		String strMagnifierName = URLEncoder.encode("贴现票据");
		String strMainProperty = " maxlength='30' value='" + strDiscountBillNo + "'";
		String strPrefix = "";
		if (strRtnDiscountDateCtrl == null || strRtnDiscountDateCtrl.equals(""))
		{
			strRtnDiscountDateCtrl = "ItIsNotControl";
		}
		if (strRtnEndDateCtrl == null || strRtnEndDateCtrl.equals(""))
		{
			strRtnEndDateCtrl = "ItIsNotControl";
		}
		if (strRtnBillAmountCtrl1 == null || strRtnBillAmountCtrl1.equals(""))
		{
			strRtnBillAmountCtrl1 = "ItIsNotControl";
		}
		if (strRtnBillAmountCtrl2 == null || strRtnBillAmountCtrl2.equals(""))
		{
			strRtnBillAmountCtrl2 = "ItIsNotControl";
		}
		if (strRtnDiscountAmountCtrl == null || strRtnDiscountAmountCtrl.equals(""))
		{
			strRtnDiscountAmountCtrl = "ItIsNotControl";
		}
		if (strRtnDelayInterestCtrl == null || strRtnDelayInterestCtrl.equals(""))
		{
			strRtnDelayInterestCtrl = "ItIsNotControl";
		}
		
		/*if (strRtnIsLocalIDCtrl == null || strRtnIsLocalIDCtrl.equals(""))
		{
			strRtnIsLocalIDCtrl = "ItIsNotControl";
		}
		if (strRtnIsLocalIDDescCtrl == null || strRtnIsLocalIDDescCtrl.equals(""))
		{
			strRtnIsLocalIDDescCtrl = "ItIsNotControl";
		}
		if (strRtnAcceptPOTypeIDCtrl == null || strRtnAcceptPOTypeIDCtrl.equals(""))
		{
			strRtnAcceptPOTypeIDCtrl = "ItIsNotControl";
		}
		if (strRtnAcceptPOTypeIDDescCtrl == null || strRtnAcceptPOTypeIDDescCtrl.equals(""))
		{
			strRtnAcceptPOTypeIDDescCtrl = "ItIsNotControl";
		}*/
		String[] strMainNames =
			{ strCtrlName + "Ctrl", strRtnDiscountDateCtrl, strRtnEndDateCtrl, strRtnBillAmountCtrl1, strRtnBillAmountCtrl2, strRtnDiscountAmountCtrl, strRtnDelayInterestCtrl,strRtnContractIdCtrl, strRtnContractNoCtrl, strRtnDiscountCredenceIdCtrl, strRtnDiscountCredenceNoCtrl };
		String[] strMainFields = { "DiscountBillNo", "DiscountDate", "EndDate", "Amount", "Amount", "CheckAmount", "DelayInterest","ContractId","ContractNo","DiscountCredenceID","DiscountCredenceNo" };
		String[] strReturnNames = { strCtrlName, strCtrlName + "DiscountCredenceID" };
		String[] strReturnFields = { "DiscountBillID", "DiscountCredenceID" };
		String[] strReturnValues = { String.valueOf(lDiscountBillID), String.valueOf(lDiscountCredenceID)};
		String[] strDisplayNames = { URLEncoder.encode("银行承兑汇票号"), URLEncoder.encode("票面金额"), URLEncoder.encode("贴现日"), URLEncoder.encode("票据到期日期")};
		String[] strDisplayFields = { "DiscountBillNo", "Amount", "DiscountDate", "EndDate" };
		int nIndex = 0;
		//获得状态数组
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getDiscountBillReturnSQL(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		if (strDiscountContractCtrl != null && !strDiscountContractCtrl.trim().equals(""))
		{
			sbSQL.append(strDiscountContractCtrl + ".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(",");
		if (strDiscountCredenceCtrl != null && !strDiscountCredenceCtrl.trim().equals(""))
		{
			sbSQL.append(strDiscountCredenceCtrl + ".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(",");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value)");
		//Log.print(sbSQL.toString());
		try
		{
			SETTMagnifier.showZoomCtrl(
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
	 * 表外业务
	 * 创建贴现汇票放大镜(银行承兑汇票号)
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lDiscountCredenceID 贴现凭证ID(初识值)
	 * @param lDiscountBillID 贴现汇票ID(初识值)
	 * @param strDiscountBillNo 贴现汇票编号(初识值)
	 * @param strDiscountContractCtrl 贴现合同ID对应的控件名称（查询时关联）
	 * @param strDiscountCredenceCtrl 贴现凭证ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnDiscountDateCtrl 返回值（贴现票据贴现日）对应的控件名
	 * @param strRtnEndDateCtrl 返回值（贴现票据到期日期）对应的控件名
	 * @param strRtnBillAmountCtrl1 返回值（票面金额）对应的控件名
	 * @param strRtnBillAmountCtrl2 返回值（票面金额）对应的控件名
	 * @param strRtnDiscountAmountCtrl 返回值（实际贴现金额）对应的控件名
	 * @param strRtnDelayInterestCtrl 返回值（罚息）对应的控件名
	 * @param strRtnIsLocalIDCtrl 返回值（是否本地号）对应的控件名
	 * @param strRtnIsLocalIDDescCtrl 返回值（是否本地）对应的控件名
	 * @param strRtnAcceptPOTypeIDCtrl 返回值（汇票类型号）对应的控件名
	 * @param strRtnAcceptPOTypeIDDescCtrl 返回值（汇票类型）对应的控件名
	 */
	public static void createDiscountBillCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lDiscountCredenceID,
		long lDiscountBillID,
		String strDiscountBillNo,
		String strDiscountContractCtrl,
		String strDiscountCredenceCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnDiscountDateCtrl,
		String strRtnEndDateCtrl,
		String strRtnBillAmountCtrl1,
		String strRtnBillAmountCtrl2,
		String strRtnDiscountAmountCtrl,
		String strRtnDelayInterestCtrl,
		String strRtnIsLocalIDCtrl,
		String strRtnIsLocalIDDescCtrl,
		String strRtnAcceptPOTypeIDCtrl,
		String strRtnAcceptPOTypeIDDescCtrl)
	{
		String strMagnifierName = URLEncoder.encode("贴现票据");
		String strMainProperty = " maxlength='30' value='" + strDiscountBillNo + "'";
		String strPrefix = "";
		if (strRtnDiscountDateCtrl == null || strRtnDiscountDateCtrl.equals(""))
		{
			strRtnDiscountDateCtrl = "ItIsNotControl";
		}
		if (strRtnEndDateCtrl == null || strRtnEndDateCtrl.equals(""))
		{
			strRtnEndDateCtrl = "ItIsNotControl";
		}
		if (strRtnBillAmountCtrl1 == null || strRtnBillAmountCtrl1.equals(""))
		{
			strRtnBillAmountCtrl1 = "ItIsNotControl";
		}
		if (strRtnBillAmountCtrl2 == null || strRtnBillAmountCtrl2.equals(""))
		{
			strRtnBillAmountCtrl2 = "ItIsNotControl";
		}
		if (strRtnDiscountAmountCtrl == null || strRtnDiscountAmountCtrl.equals(""))
		{
			strRtnDiscountAmountCtrl = "ItIsNotControl";
		}
		if (strRtnDelayInterestCtrl == null || strRtnDelayInterestCtrl.equals(""))
		{
			strRtnDelayInterestCtrl = "ItIsNotControl";
		}
		
		if (strRtnIsLocalIDCtrl == null || strRtnIsLocalIDCtrl.equals(""))
		{
			strRtnIsLocalIDCtrl = "ItIsNotControl";
		}
		if (strRtnIsLocalIDDescCtrl == null || strRtnIsLocalIDDescCtrl.equals(""))
		{
			strRtnIsLocalIDDescCtrl = "ItIsNotControl";
		}
		if (strRtnAcceptPOTypeIDCtrl == null || strRtnAcceptPOTypeIDCtrl.equals(""))
		{
			strRtnAcceptPOTypeIDCtrl = "ItIsNotControl";
		}
		if (strRtnAcceptPOTypeIDDescCtrl == null || strRtnAcceptPOTypeIDDescCtrl.equals(""))
		{
			strRtnAcceptPOTypeIDDescCtrl = "ItIsNotControl";
		}
		String strContractIDCtrlTemp = strDiscountContractCtrl;
		String strContractNOCtrl =strContractIDCtrlTemp+"Ctrl"; 
		String strCredenceIDCtrlTemp = strDiscountCredenceCtrl;
		String strCredenceNOCtrl = strCredenceIDCtrlTemp + "Ctrl"; 
		if (strContractIDCtrlTemp == null || strContractIDCtrlTemp.equals(""))
		{
			strContractIDCtrlTemp = "ItIsNotControl";
			strContractNOCtrl = "ItIsNotControl";
		}
		if (strCredenceIDCtrlTemp == null || strCredenceIDCtrlTemp.equals(""))
		{
			strCredenceIDCtrlTemp = "ItIsNotControl";
			strCredenceNOCtrl = "ItIsNotControl";
		}
		String[] strMainNames =
			{ strCtrlName + "Ctrl",strContractIDCtrlTemp,strContractNOCtrl,strCredenceIDCtrlTemp,strCredenceNOCtrl, strRtnDiscountDateCtrl, strRtnEndDateCtrl, strRtnBillAmountCtrl1, strRtnBillAmountCtrl2, strRtnDiscountAmountCtrl, strRtnDelayInterestCtrl,strRtnIsLocalIDCtrl,strRtnIsLocalIDDescCtrl,strRtnAcceptPOTypeIDCtrl,strRtnAcceptPOTypeIDDescCtrl };
		String[] strMainFields = { "BillNo","ContractID","ContractNO","CredenceID","CredenceNO", "dDate", "EndDate", "Amount", "Amount", "CheckAmount", "DelayInterest","IsLocalID","Desc1","AcceptPOTypeID","Desc2" };
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "BillID"};
		String[] strReturnValues = { String.valueOf(lDiscountBillID) };
		String[] strDisplayNames = { URLEncoder.encode("承兑汇票号"), URLEncoder.encode("票面金额"), URLEncoder.encode("贴现日"), URLEncoder.encode("票据到期日期")};
		String[] strDisplayFields = { "BillNo", "Amount", "dDate", "EndDate" };
		int nIndex = 0;
		//获得状态数组
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getDiscountBillSQL(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		if (strDiscountContractCtrl != null && !strDiscountContractCtrl.trim().equals(""))
		{
			sbSQL.append(strDiscountContractCtrl + ".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(",");
		if (strDiscountCredenceCtrl != null && !strDiscountCredenceCtrl.trim().equals(""))
		{
			sbSQL.append(strDiscountCredenceCtrl + ".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(",");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value)");
		//Log.print(sbSQL.toString());
		try
		{
			SETTMagnifier.showZoomCtrl(
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
	 * 表外业务
	 * 创建贴现汇票放大镜(银行承兑汇票号)
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lDiscountCredenceID 贴现凭证ID(初识值)
	 * @param lDiscountBillID 贴现汇票ID(初识值)
	 * @param strDiscountBillNo 贴现汇票编号(初识值)
	 * @param strDiscountContractCtrl 贴现合同ID对应的控件名称（查询时关联）
	 * @param strDiscountCredenceCtrl 贴现凭证ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnDiscountDateCtrl 返回值（贴现票据贴现日）对应的控件名
	 * @param strRtnEndDateCtrl 返回值（贴现票据到期日期）对应的控件名
	 * @param strRtnBillAmountCtrl1 返回值（票面金额）对应的控件名
	 * @param strRtnBillAmountCtrl2 返回值（票面金额）对应的控件名
	 * @param strRtnDiscountAmountCtrl 返回值（实际贴现金额）对应的控件名
	 * @param strRtnDelayInterestCtrl 返回值（罚息）对应的控件名
	 * @param strRtnIsLocalIDCtrl 返回值（是否本地号）对应的控件名
	 * @param strRtnIsLocalIDDescCtrl 返回值（是否本地）对应的控件名
	 * @param strRtnAcceptPOTypeIDCtrl 返回值（汇票类型号）对应的控件名
	 * @param strRtnAcceptPOTypeIDDescCtrl 返回值（汇票类型）对应的控件名
	 */
	public static void createDiscountBillCtrlExt(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lDiscountCredenceID,
		long lDiscountBillID,
		String strDiscountBillNo,
		String strDiscountContractCtrl,
		String strDiscountCredenceCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnDiscountDateCtrl,
		String strRtnEndDateCtrl,
		String strRtnBillAmountCtrl1,
		String strRtnBillAmountCtrl2,
		String strRtnDiscountAmountCtrl,
		String strRtnDelayInterestCtrl,
		String strRtnIsLocalIDCtrl,
		String strRtnIsLocalIDDescCtrl,
		String strRtnAcceptPOTypeIDCtrl,
		String strRtnAcceptPOTypeIDDescCtrl,String dtDiscountDate)
	{
		String strMagnifierName = URLEncoder.encode("贴现票据");
		String strMainProperty = " maxlength='30' value='" + strDiscountBillNo + "'";
		String strPrefix = "";
		if (strRtnDiscountDateCtrl == null || strRtnDiscountDateCtrl.equals(""))
		{
			strRtnDiscountDateCtrl = "ItIsNotControl";
		}
		if (strRtnEndDateCtrl == null || strRtnEndDateCtrl.equals(""))
		{
			strRtnEndDateCtrl = "ItIsNotControl";
		}
		if (strRtnBillAmountCtrl1 == null || strRtnBillAmountCtrl1.equals(""))
		{
			strRtnBillAmountCtrl1 = "ItIsNotControl";
		}
		if (strRtnBillAmountCtrl2 == null || strRtnBillAmountCtrl2.equals(""))
		{
			strRtnBillAmountCtrl2 = "ItIsNotControl";
		}
		if (strRtnDiscountAmountCtrl == null || strRtnDiscountAmountCtrl.equals(""))
		{
			strRtnDiscountAmountCtrl = "ItIsNotControl";
		}
		if (strRtnDelayInterestCtrl == null || strRtnDelayInterestCtrl.equals(""))
		{
			strRtnDelayInterestCtrl = "ItIsNotControl";
		}
		
		if (strRtnIsLocalIDCtrl == null || strRtnIsLocalIDCtrl.equals(""))
		{
			strRtnIsLocalIDCtrl = "ItIsNotControl";
		}
		if (strRtnIsLocalIDDescCtrl == null || strRtnIsLocalIDDescCtrl.equals(""))
		{
			strRtnIsLocalIDDescCtrl = "ItIsNotControl";
		}
		if (strRtnAcceptPOTypeIDCtrl == null || strRtnAcceptPOTypeIDCtrl.equals(""))
		{
			strRtnAcceptPOTypeIDCtrl = "ItIsNotControl";
		}
		if (strRtnAcceptPOTypeIDDescCtrl == null || strRtnAcceptPOTypeIDDescCtrl.equals(""))
		{
			strRtnAcceptPOTypeIDDescCtrl = "ItIsNotControl";
		}
		String strContractIDCtrlTemp = strDiscountContractCtrl;
		String strContractNOCtrl =strContractIDCtrlTemp+"Ctrl"; 
		String strCredenceIDCtrlTemp = strDiscountCredenceCtrl;
		String strCredenceNOCtrl = strCredenceIDCtrlTemp + "Ctrl"; 
		if (strContractIDCtrlTemp == null || strContractIDCtrlTemp.equals(""))
		{
			strContractIDCtrlTemp = "ItIsNotControl";
			strContractNOCtrl = "ItIsNotControl";
		}
		if (strCredenceIDCtrlTemp == null || strCredenceIDCtrlTemp.equals(""))
		{
			strCredenceIDCtrlTemp = "ItIsNotControl";
			strCredenceNOCtrl = "ItIsNotControl";
		}
		String[] strMainNames =
			{ strCtrlName + "Ctrl",strContractIDCtrlTemp,strContractNOCtrl,strCredenceIDCtrlTemp,strCredenceNOCtrl, strRtnDiscountDateCtrl, strRtnEndDateCtrl,dtDiscountDate, strRtnBillAmountCtrl1, strRtnBillAmountCtrl2, strRtnDiscountAmountCtrl };
		String[] strMainFields = { "BillNo","ContractID","ContractNO","CredenceID","CredenceNO", "dDate", "EndDate" ,"dtDiscountDate", "Amount", "Amount", "CheckAmount"};
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "BillID"};
		String[] strReturnValues = { String.valueOf(lDiscountBillID) };
		String[] strDisplayNames = { URLEncoder.encode("承兑汇票号"), URLEncoder.encode("票面金额"), URLEncoder.encode("贴现日"), URLEncoder.encode("票据到期日期")};
		String[] strDisplayFields = { "BillNo", "Amount", "dDate", "EndDate" };
		int nIndex = 0;
		//获得状态数组
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getDiscountBillExtSQL(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		if (strDiscountContractCtrl != null && !strDiscountContractCtrl.trim().equals(""))
		{
			sbSQL.append(strDiscountContractCtrl + ".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(",");
		if (strDiscountCredenceCtrl != null && !strDiscountCredenceCtrl.trim().equals(""))
		{
			sbSQL.append(strDiscountCredenceCtrl + ".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(",");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value)");
		//Log.print(sbSQL.toString());
		try
		{
			SETTMagnifier.showZoomCtrl(
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
	 * add by dwj 
	 * 创建贴现凭证放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lContractID 合同ID(初识值)
	 * @param lDiscountCredenceID 贴现凭证ID(初识值)
	 * @param strDiscountCredenceNo 贴现凭证号(初识值)
	 * @param lTypeID 贴现凭证类型
	 * 	(内部状态：
	 * 	0，显示全部；
	 * 	11，贴现发放——业务处理使用；
	 * 	12，贴现发放——业务复核使用；
	 * 	21，贴现收回——业务处理使用；
	 *  22，贴现收回——业务复核使用；）
	 * @param strContractCtrl 合同ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnSubAccountIDCtrl 返回值（子账户ID）对应的控件名
	 */
	public static void createDiscountCredenceCtrlAdd(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lContractID,
		long lDiscountCredenceID,
		String strDiscountCredenceNo,
		long lTypeID,
		String strContractCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnSubAccountIDCtrl)
	{
		createDiscountCredenceCtrlAdd(
			out,
			lOfficeID,
			lCurrencyID,
			strFormName,
			strCtrlName,
			strTitle,
			lContractID,
			lDiscountCredenceID,
			strDiscountCredenceNo,
			lTypeID,
			strContractCtrl,
			strFirstTD,
			strSecondTD,
			strNextControls,
			strRtnSubAccountIDCtrl,
			"",
			"",
			"");
	}
	
	/* add by dwj 
	 * 创建贴现凭证放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lContractID 合同ID(初识值)
	 * @param lDiscountCredenceID 贴现凭证ID(初识值)
	 * @param strDiscountCredenceNo 贴现凭证号(初识值)
	 * @param lTypeID 贴现凭证类型
	 * 	(内部状态：
	 * 	0，显示全部；
	 * 	11，贴现发放——业务处理使用；
	 * 	12，贴现发放——业务复核使用；
	 * 	21，贴现收回——业务处理使用；
	 *  22，贴现收回——业务复核使用；）
	 * @param strContractCtrl 合同ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnSubAccountIDCtrl 返回值（子账户ID）对应的控件名
	 * @param strRtnCredenceAmountCtrl 返回值（汇票金额）对应的控件名
	 * @param strRtnDiscountAmountCtrl 返回值（贴现金额）对应的控件名
	 * @param strRtnInterestAmountCtrl 返回值（利息金额）对应的控件名
	 */
	public static void createDiscountCredenceCtrlAdd(
			JspWriter out,
			long lOfficeID,
			long lCurrencyID,
			String strFormName,
			String strCtrlName,
			String strTitle,
			long lContractID,
			long lDiscountCredenceID,
			String strDiscountCredenceNo,
			long lTypeID,
			String strContractCtrl,
			String strFirstTD,
			String strSecondTD,
			String[] strNextControls,
			String strRtnSubAccountIDCtrl,
			String strRtnCredenceAmountCtrl,
			String strRtnDiscountAmountCtrl,
			String strRtnInterestAmountCtrl)
		{
			String strMagnifierName = URLEncoder.encode("贴现凭证");
			String strMainProperty = " maxlength='30' value='" + strDiscountCredenceNo + "'";
			String strPrefix = "";
			if (strRtnSubAccountIDCtrl == null || strRtnSubAccountIDCtrl.equals(""))
			{
				strRtnSubAccountIDCtrl = "ItIsNotControl";
			}
			if (strRtnCredenceAmountCtrl == null || strRtnCredenceAmountCtrl.equals(""))
			{
				strRtnCredenceAmountCtrl = "ItIsNotControl";
			}
			if (strRtnDiscountAmountCtrl == null || strRtnDiscountAmountCtrl.equals(""))
			{
				strRtnDiscountAmountCtrl = "ItIsNotControl";
			}
			if (strRtnInterestAmountCtrl == null || strRtnInterestAmountCtrl.equals(""))
			{
				strRtnInterestAmountCtrl = "ItIsNotControl";
			}
			String[] strMainNames = { strCtrlName + "Ctrl", strRtnSubAccountIDCtrl, strRtnCredenceAmountCtrl, strRtnDiscountAmountCtrl, strRtnInterestAmountCtrl };
			String[] strMainFields = { "DiscountCredenceNo", "SubAccountID", "Amount", "DiscountAmount", "Interest" };
			String[] strReturnNames = { strCtrlName, strCtrlName + "ContractID" };
			String[] strReturnFields = { "DiscountCredenceID", "ContractID" };
			String[] strReturnValues = { String.valueOf(lDiscountCredenceID), String.valueOf(lContractID)};
			String[] strDisplayNames = { URLEncoder.encode("贴现凭证编号"), URLEncoder.encode("汇票金额"), URLEncoder.encode("贴现利息")};
			String[] strDisplayFields = { "DiscountCredenceNo", "Amount", "Interest" };
			int nIndex = 0;
			//获得状态数组
			long[] lStatusIDArray = null;
			if (lTypeID == 0)
			{
				lStatusIDArray = new long[] { LOANConstant.DiscountCredenceStatus.CHECK, LOANConstant.DiscountCredenceStatus.USED };
			}
			else
				if (lTypeID == 11)
				{
					lStatusIDArray = new long[] { LOANConstant.DiscountCredenceStatus.CHECK };
				}
				else
					if (lTypeID == 12)
					{
						lStatusIDArray = new long[] { LOANConstant.DiscountCredenceStatus.USED };
					}
					else
						if (lTypeID == 21)
						{
							lStatusIDArray = new long[] { -100 };
						}
						else
							if (lTypeID == 22)
							{
								lStatusIDArray = new long[] { -200 };
							}
			StringBuffer sbSQL = new StringBuffer(64);
			sbSQL.append("getDiscountCredenceSQLAdd(");
			sbSQL.append(lOfficeID);
			sbSQL.append(",");
			sbSQL.append(lCurrencyID);
			sbSQL.append(",");
			if (strContractCtrl != null && !strContractCtrl.trim().equals(""))
			{
				sbSQL.append(strContractCtrl + ".value");
			}
			else
			{
				sbSQL.append("-1");
			}
			sbSQL.append(",");
			sbSQL.append("'");
			if (lStatusIDArray != null && lStatusIDArray.length > 0)
			{
				for (int i = 0; i < lStatusIDArray.length; i++)
				{
					sbSQL.append(lStatusIDArray[i]);
					if (i < lStatusIDArray.length - 1)
					{
						sbSQL.append(",");
					}
				}
			}
			else
			{
				sbSQL.append("-1");
			}
			sbSQL.append("'");
			sbSQL.append(",");
			sbSQL.append(strCtrlName);
			sbSQL.append("Ctrl.value)");
			//Log.print(sbSQL.toString());
			try
			{
				SETTMagnifier.showZoomCtrl(
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
	 * 创建贴现放款通知单放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lContractID 合同ID(初识值)
	 * @param lDiscountCredenceID 贴现凭证ID(初识值)
	 * @param strDiscountCredenceNo 贴现凭证号(初识值)
	 * @param lTypeID 贴现凭证类型
	 * 	(内部状态：
	 * 	0，显示全部；
	 * 	11，贴现发放——业务处理使用；
	 * 	12，贴现发放——业务复核使用；
	 * 	21，贴现收回——业务处理使用；
	 *  22，贴现收回——业务复核使用；）
	 * @param strContractCtrl 合同ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnSubAccountIDCtrl 返回值（子账户ID）对应的控件名
	 */
	public static void createDiscountPayFormCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lContractID,
		long lDiscountCredenceID,
		String strDiscountCredenceNo,
		long lTypeID,
		String strContractCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnSubAccountIDCtrl)
	{
		createDiscountPayFormCtrl(
			out,
			lOfficeID,
			lCurrencyID,
			strFormName,
			strCtrlName,
			strTitle,
			lContractID,
			lDiscountCredenceID,
			strDiscountCredenceNo,
			lTypeID,
			strContractCtrl,
			strFirstTD,
			strSecondTD,
			strNextControls,
			strRtnSubAccountIDCtrl,
			"",
			"",
			"");
	}
	/**
	 * 创建贴现放款通知单放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lContractID 合同ID(初识值)
	 * @param lDiscountCredenceID 贴现凭证ID(初识值)
	 * @param strDiscountCredenceNo 贴现凭证号(初识值)
	 * @param lTypeID 贴现凭证类型
	 * 	(内部状态：
	 * 	0，显示全部；
	 * 	11，贴现发放——业务处理使用；
	 * 	12，贴现发放——业务复核使用；
	 * 	21，贴现收回——业务处理使用；
	 *  22，贴现收回——业务复核使用；）
	 * @param strContractCtrl 合同ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnSubAccountIDCtrl 返回值（子账户ID）对应的控件名
	 * @param strRtnCredenceAmountCtrl 返回值（汇票金额）对应的控件名
	 * @param strRtnDiscountAmountCtrl 返回值（贴现金额）对应的控件名
	 * @param strRtnInterestAmountCtrl 返回值（利息金额）对应的控件名
	 */
	
	public static void createDiscountPayFormCtrl(
			JspWriter out,
			long lOfficeID,
			long lCurrencyID,
			String strFormName,
			String strCtrlName, 
			String strTitle,
			long lContractID,
			long lDiscountCredenceID,
			String strDiscountCredenceNo,
			long lTypeID,
			String strContractCtrl,
			String strFirstTD,
			String strSecondTD,
			String[] strNextControls,
			String strRtnSubAccountIDCtrl,
			String strRtnCredenceAmountCtrl,
			String strRtnDiscountAmountCtrl,
			String strRtnInterestAmountCtrl)
		{
			String strMagnifierName = URLEncoder.encode("贴现放款通知单");
			String strMainProperty = " maxlength='30' value='" + strDiscountCredenceNo + "'";
			String strPrefix = "";
			if (strRtnSubAccountIDCtrl == null || strRtnSubAccountIDCtrl.equals(""))
			{
				strRtnSubAccountIDCtrl = "ItIsNotControl";
			}
			if (strRtnCredenceAmountCtrl == null || strRtnCredenceAmountCtrl.equals(""))
			{
				strRtnCredenceAmountCtrl = "ItIsNotControl";
			}
			if (strRtnDiscountAmountCtrl == null || strRtnDiscountAmountCtrl.equals(""))
			{
				strRtnDiscountAmountCtrl = "ItIsNotControl";
			}
			if (strRtnInterestAmountCtrl == null || strRtnInterestAmountCtrl.equals(""))
			{
				strRtnInterestAmountCtrl = "ItIsNotControl";
			}
			String[] strMainNames = { strCtrlName + "Ctrl", strRtnSubAccountIDCtrl, strRtnCredenceAmountCtrl, strRtnDiscountAmountCtrl, strRtnInterestAmountCtrl };
			String[] strMainFields = { "DiscountCredenceNo", "SubAccountID", "Amount", "DiscountAmount", "Interest" };
			String[] strReturnNames = { strCtrlName, strCtrlName + "ContractID" };
			String[] strReturnFields = { "DiscountCredenceID", "ContractID" };
			String[] strReturnValues = { String.valueOf(lDiscountCredenceID), String.valueOf(lContractID)};
			String[] strDisplayNames = { URLEncoder.encode("贴现放款通知单编号"), URLEncoder.encode("汇票金额"), URLEncoder.encode("贴现利息")};
			String[] strDisplayFields = { "DiscountCredenceNo", "Amount", "Interest" };
			int nIndex = 0;
			//获得状态数组
			long[] lStatusIDArray = null;
			if (lTypeID == 0)
			{
				lStatusIDArray = new long[] { LOANConstant.DiscountCredenceStatus.CHECK, LOANConstant.DiscountCredenceStatus.USED };
			}
			else
				if (lTypeID == 11)
				{
					lStatusIDArray = new long[] { LOANConstant.DiscountCredenceStatus.CHECK };
				}
				else
					if (lTypeID == 12)
					{
						lStatusIDArray = new long[] { LOANConstant.DiscountCredenceStatus.USED };
					}
					else
						if (lTypeID == 21)
						{
							lStatusIDArray = new long[] { -100 };
						}
						else
							if (lTypeID == 22)
							{
								lStatusIDArray = new long[] { -200 };
							}
			StringBuffer sbSQL = new StringBuffer(64);
			sbSQL.append("getDiscountPayForm(");
			sbSQL.append(lOfficeID);
			sbSQL.append(",");
			sbSQL.append(lCurrencyID);
			sbSQL.append(",");
			if (strContractCtrl != null && !strContractCtrl.trim().equals(""))
			{
				sbSQL.append(strContractCtrl + ".value");
			}
			else
			{
				sbSQL.append("-1");
			}
			sbSQL.append(",");
			sbSQL.append("'");
			if (lStatusIDArray != null && lStatusIDArray.length > 0)
			{
				for (int i = 0; i < lStatusIDArray.length; i++)
				{
					sbSQL.append(lStatusIDArray[i]);
					if (i < lStatusIDArray.length - 1)
					{
						sbSQL.append(",");
					}
				}
			}
			else
			{
				sbSQL.append("-1");
			}
			sbSQL.append("'");
			sbSQL.append(",");
			sbSQL.append(strCtrlName);
			sbSQL.append("Ctrl.value)");
			//Log.print(sbSQL.toString());
			try
			{
				SETTMagnifier.showZoomCtrl(
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

	/**
	 * 表外业务
	 * 创建贴现汇票放大镜(银行承兑汇票号)
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lDiscountCredenceID 贴现凭证ID(初识值)
	 * @param lDiscountBillID 贴现汇票ID(初识值)
	 * @param strDiscountBillNo 贴现汇票编号(初识值)
	 * @param strDiscountContractCtrl 贴现合同ID对应的控件名称（查询时关联）
	 * @param strDiscountCredenceCtrl 贴现凭证ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnDiscountDateCtrl 返回值（贴现票据贴现日）对应的控件名
	 * @param strRtnEndDateCtrl 返回值（贴现票据到期日期）对应的控件名
	 * @param strRtnBillAmountCtrl1 返回值（票面金额）对应的控件名
	 * @param strRtnBillAmountCtrl2 返回值（票面金额）对应的控件名
	 * @param strRtnDiscountAmountCtrl 返回值（实际贴现金额）对应的控件名
	 * @param strRtnDelayInterestCtrl 返回值（罚息）对应的控件名
	 * @param strRtnIsLocalIDCtrl 返回值（是否本地号）对应的控件名
	 * @param strRtnIsLocalIDDescCtrl 返回值（是否本地）对应的控件名
	 * @param strRtnAcceptPOTypeIDCtrl 返回值（汇票类型号）对应的控件名
	 * @param strRtnAcceptPOTypeIDDescCtrl 返回值（汇票类型）对应的控件名
	 */
	public static void createDiscountBillCtrlExtNew(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lDiscountCredenceID,
		long lDiscountBillID,
		String strDiscountBillNo,
		String strDiscountContractCtrl,
		String strDiscountCredenceCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnDiscountDateCtrl,
		String strRtnEndDateCtrl,
		String strRtnBillAmountCtrl1,
		String strRtnBillAmountCtrl2,
		String strRtnDiscountAmountCtrl,
		String strRtnDelayInterestCtrl,
		String strRtnIsLocalIDCtrl,
		String strRtnIsLocalIDDescCtrl,
		String strRtnAcceptPOTypeIDCtrl,
		String strRtnAcceptPOTypeIDDescCtrl,String dtDiscountDate,String discountContractBillIDs)
	{
		String strMagnifierName = URLEncoder.encode("贴现票据");
		String strMainProperty = " maxlength='30' value='" + strDiscountBillNo + "'";
		String strPrefix = "";
		if (strRtnDiscountDateCtrl == null || strRtnDiscountDateCtrl.equals(""))
		{
			strRtnDiscountDateCtrl = "ItIsNotControl";
		}
		if (strRtnEndDateCtrl == null || strRtnEndDateCtrl.equals(""))
		{
			strRtnEndDateCtrl = "ItIsNotControl";
		}
		if (strRtnBillAmountCtrl1 == null || strRtnBillAmountCtrl1.equals(""))
		{
			strRtnBillAmountCtrl1 = "ItIsNotControl";
		}
		if (strRtnBillAmountCtrl2 == null || strRtnBillAmountCtrl2.equals(""))
		{
			strRtnBillAmountCtrl2 = "ItIsNotControl";
		}
		if (strRtnDiscountAmountCtrl == null || strRtnDiscountAmountCtrl.equals(""))
		{
			strRtnDiscountAmountCtrl = "ItIsNotControl";
		}
		if (strRtnDelayInterestCtrl == null || strRtnDelayInterestCtrl.equals(""))
		{
			strRtnDelayInterestCtrl = "ItIsNotControl";
		}
		
		if (strRtnIsLocalIDCtrl == null || strRtnIsLocalIDCtrl.equals(""))
		{
			strRtnIsLocalIDCtrl = "ItIsNotControl";
		}
		if (strRtnIsLocalIDDescCtrl == null || strRtnIsLocalIDDescCtrl.equals(""))
		{
			strRtnIsLocalIDDescCtrl = "ItIsNotControl";
		}
		if (strRtnAcceptPOTypeIDCtrl == null || strRtnAcceptPOTypeIDCtrl.equals(""))
		{
			strRtnAcceptPOTypeIDCtrl = "ItIsNotControl";
		}
		if (strRtnAcceptPOTypeIDDescCtrl == null || strRtnAcceptPOTypeIDDescCtrl.equals(""))
		{
			strRtnAcceptPOTypeIDDescCtrl = "ItIsNotControl";
		}
		String strContractIDCtrlTemp = strDiscountContractCtrl;
		String strContractNOCtrl =strContractIDCtrlTemp+"Ctrl"; 
		String strCredenceIDCtrlTemp = strDiscountCredenceCtrl;
		String strCredenceNOCtrl = strCredenceIDCtrlTemp + "Ctrl"; 
		if (strContractIDCtrlTemp == null || strContractIDCtrlTemp.equals(""))
		{
			strContractIDCtrlTemp = "ItIsNotControl";
			strContractNOCtrl = "ItIsNotControl";
		}
		if (strCredenceIDCtrlTemp == null || strCredenceIDCtrlTemp.equals(""))
		{
			strCredenceIDCtrlTemp = "ItIsNotControl";
			strCredenceNOCtrl = "ItIsNotControl";
		}
		String[] strMainNames =
			{ strCtrlName + "Ctrl",strContractIDCtrlTemp,strContractNOCtrl,strCredenceIDCtrlTemp,strCredenceNOCtrl, strRtnDiscountDateCtrl, strRtnEndDateCtrl,dtDiscountDate, strRtnBillAmountCtrl1, strRtnBillAmountCtrl2, strRtnDiscountAmountCtrl };
		String[] strMainFields = { "BillNo","ContractID","ContractNO","CredenceID","CredenceNO", "dDate", "EndDate" ,"dtDiscountDate", "Amount", "Amount", "CheckAmount"};
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "BillID"};
		String[] strReturnValues = { String.valueOf(lDiscountBillID) };
		String[] strDisplayNames = { URLEncoder.encode("承兑汇票号"), URLEncoder.encode("票面金额"), URLEncoder.encode("贴现日"), URLEncoder.encode("票据到期日期")};
		String[] strDisplayFields = { "BillNo", "Amount", "dDate", "EndDate" };
		int nIndex = 0;
		//获得状态数组
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getDiscountBillExtSQLNew(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		if (strDiscountContractCtrl != null && !strDiscountContractCtrl.trim().equals(""))
		{
			sbSQL.append(strDiscountContractCtrl + ".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(",");
		if (strDiscountCredenceCtrl != null && !strDiscountCredenceCtrl.trim().equals(""))
		{
			sbSQL.append(strDiscountCredenceCtrl + ".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(",'");
		
		if (discountContractBillIDs != null && discountContractBillIDs.trim().length()>0)
		{
			sbSQL.append(discountContractBillIDs);
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("',");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value)");
		//Log.print(sbSQL.toString());
		try
		{
			SETTMagnifier.showZoomCtrl(
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
	 * 表外业务
	 * 创建贴现汇票放大镜(银行承兑汇票号)武钢项目修改SQL去掉申请表
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lDiscountCredenceID 贴现凭证ID(初识值)
	 * @param lDiscountBillID 贴现汇票ID(初识值)
	 * @param strDiscountBillNo 贴现汇票编号(初识值)
	 * @param strDiscountContractCtrl 贴现合同ID对应的控件名称（查询时关联）
	 * @param strDiscountCredenceCtrl 贴现凭证ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnDiscountDateCtrl 返回值（贴现票据贴现日）对应的控件名
	 * @param strRtnEndDateCtrl 返回值（贴现票据到期日期）对应的控件名
	 * @param strRtnBillAmountCtrl1 返回值（票面金额）对应的控件名
	 * @param strRtnBillAmountCtrl2 返回值（票面金额）对应的控件名
	 * @param strRtnDiscountAmountCtrl 返回值（实际贴现金额）对应的控件名
	 * @param strRtnDelayInterestCtrl 返回值（罚息）对应的控件名
	 * @param strRtnIsLocalIDCtrl 返回值（是否本地号）对应的控件名
	 * @param strRtnIsLocalIDDescCtrl 返回值（是否本地）对应的控件名
	 * @param strRtnAcceptPOTypeIDCtrl 返回值（汇票类型号）对应的控件名
	 * @param strRtnAcceptPOTypeIDDescCtrl 返回值（汇票类型）对应的控件名
	 */
	public static void createDiscountBillCtrlExtforwg(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lDiscountCredenceID,
		long lDiscountBillID,
		String strDiscountBillNo,
		String strDiscountContractCtrl,
		String strDiscountCredenceCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnDiscountDateCtrl,
		String strRtnEndDateCtrl,
		String strRtnBillAmountCtrl1,
		String strRtnBillAmountCtrl2,
		String strRtnDiscountAmountCtrl,
		String strRtnDelayInterestCtrl,
		String strRtnIsLocalIDCtrl,
		String strRtnIsLocalIDDescCtrl,
		String strRtnAcceptPOTypeIDCtrl,
		String strRtnAcceptPOTypeIDDescCtrl,String dtDiscountDate)
	{
		String strMagnifierName = URLEncoder.encode("贴现票据");
		String strMainProperty = " maxlength='30' value='" + strDiscountBillNo + "'";
		String strPrefix = "";
		if (strRtnDiscountDateCtrl == null || strRtnDiscountDateCtrl.equals(""))
		{
			strRtnDiscountDateCtrl = "ItIsNotControl";
		}
		if (strRtnEndDateCtrl == null || strRtnEndDateCtrl.equals(""))
		{
			strRtnEndDateCtrl = "ItIsNotControl";
		}
		if (strRtnBillAmountCtrl1 == null || strRtnBillAmountCtrl1.equals(""))
		{
			strRtnBillAmountCtrl1 = "ItIsNotControl";
		}
		if (strRtnBillAmountCtrl2 == null || strRtnBillAmountCtrl2.equals(""))
		{
			strRtnBillAmountCtrl2 = "ItIsNotControl";
		}
		if (strRtnDiscountAmountCtrl == null || strRtnDiscountAmountCtrl.equals(""))
		{
			strRtnDiscountAmountCtrl = "ItIsNotControl";
		}
		if (strRtnDelayInterestCtrl == null || strRtnDelayInterestCtrl.equals(""))
		{
			strRtnDelayInterestCtrl = "ItIsNotControl";
		}
		
		if (strRtnIsLocalIDCtrl == null || strRtnIsLocalIDCtrl.equals(""))
		{
			strRtnIsLocalIDCtrl = "ItIsNotControl";
		}
		if (strRtnIsLocalIDDescCtrl == null || strRtnIsLocalIDDescCtrl.equals(""))
		{
			strRtnIsLocalIDDescCtrl = "ItIsNotControl";
		}
		if (strRtnAcceptPOTypeIDCtrl == null || strRtnAcceptPOTypeIDCtrl.equals(""))
		{
			strRtnAcceptPOTypeIDCtrl = "ItIsNotControl";
		}
		if (strRtnAcceptPOTypeIDDescCtrl == null || strRtnAcceptPOTypeIDDescCtrl.equals(""))
		{
			strRtnAcceptPOTypeIDDescCtrl = "ItIsNotControl";
		}
		String strContractIDCtrlTemp = strDiscountContractCtrl;
		String strContractNOCtrl =strContractIDCtrlTemp+"Ctrl"; 
		String strCredenceIDCtrlTemp = strDiscountCredenceCtrl;
		String strCredenceNOCtrl = strCredenceIDCtrlTemp + "Ctrl"; 
		if (strContractIDCtrlTemp == null || strContractIDCtrlTemp.equals(""))
		{
			strContractIDCtrlTemp = "ItIsNotControl";
			strContractNOCtrl = "ItIsNotControl";
		}
		if (strCredenceIDCtrlTemp == null || strCredenceIDCtrlTemp.equals(""))
		{
			strCredenceIDCtrlTemp = "ItIsNotControl";
			strCredenceNOCtrl = "ItIsNotControl";
		}
		String[] strMainNames =
			{ strCtrlName + "Ctrl",strContractIDCtrlTemp,strContractNOCtrl,strCredenceIDCtrlTemp,strCredenceNOCtrl, strRtnDiscountDateCtrl, strRtnEndDateCtrl,dtDiscountDate, strRtnBillAmountCtrl1, strRtnBillAmountCtrl2, strRtnDiscountAmountCtrl };
		String[] strMainFields = { "BillNo","ContractID","ContractNO","CredenceID","CredenceNO", "dDate", "EndDate" ,"dtDiscountDate", "Amount", "Amount", "CheckAmount"};
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "BillID"};
		String[] strReturnValues = { String.valueOf(lDiscountBillID) };
		String[] strDisplayNames = { URLEncoder.encode("承兑汇票号"), URLEncoder.encode("票面金额"), URLEncoder.encode("贴现日"), URLEncoder.encode("票据到期日期")};
		String[] strDisplayFields = { "BillNo", "Amount", "dDate", "EndDate" };
		int nIndex = 0;
		//获得状态数组
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getDiscountBillExtSQLforwg(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		if (strDiscountContractCtrl != null && !strDiscountContractCtrl.trim().equals(""))
		{
			sbSQL.append(strDiscountContractCtrl + ".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(",");
		if (strDiscountCredenceCtrl != null && !strDiscountCredenceCtrl.trim().equals(""))
		{
			sbSQL.append(strDiscountCredenceCtrl + ".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(",");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value)");
		//Log.print(sbSQL.toString());
		try
		{
			SETTMagnifier.showZoomCtrl(
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
	 * 表外业务
	 * 创建贴现汇票放大镜(银行承兑汇票号)武钢项目修改SQL去掉申请表
	 * 过滤掉已做过转贴现卖出买断的票据
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lDiscountCredenceID 贴现凭证ID(初识值)
	 * @param lDiscountBillID 贴现汇票ID(初识值)
	 * @param strDiscountBillNo 贴现汇票编号(初识值)
	 * @param strDiscountContractCtrl 贴现合同ID对应的控件名称（查询时关联）
	 * @param strDiscountCredenceCtrl 贴现凭证ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnDiscountDateCtrl 返回值（贴现票据贴现日）对应的控件名
	 * @param strRtnEndDateCtrl 返回值（贴现票据到期日期）对应的控件名
	 * @param strRtnBillAmountCtrl1 返回值（票面金额）对应的控件名
	 * @param strRtnBillAmountCtrl2 返回值（票面金额）对应的控件名
	 * @param strRtnDiscountAmountCtrl 返回值（实际贴现金额）对应的控件名
	 * @param strRtnDelayInterestCtrl 返回值（罚息）对应的控件名
	 * @param strRtnIsLocalIDCtrl 返回值（是否本地号）对应的控件名
	 * @param strRtnIsLocalIDDescCtrl 返回值（是否本地）对应的控件名
	 * @param strRtnAcceptPOTypeIDCtrl 返回值（汇票类型号）对应的控件名
	 * @param strRtnAcceptPOTypeIDDescCtrl 返回值（汇票类型）对应的控件名
	 */
	public static void createDiscountBillCtrlExtforwgNew(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lDiscountCredenceID,
		long lDiscountBillID,
		String strDiscountBillNo,
		String strDiscountContractCtrl,
		String strDiscountCredenceCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnDiscountDateCtrl,
		String strRtnEndDateCtrl,
		String strRtnBillAmountCtrl1,
		String strRtnBillAmountCtrl2,
		String strRtnDiscountAmountCtrl,
		String strRtnDelayInterestCtrl,
		String strRtnIsLocalIDCtrl,
		String strRtnIsLocalIDDescCtrl,
		String strRtnAcceptPOTypeIDCtrl,
		String strRtnAcceptPOTypeIDDescCtrl,String dtDiscountDate,String discountContractBillIDs)
	{
		String strMagnifierName = URLEncoder.encode("贴现票据");
		String strMainProperty = " maxlength='30' value='" + strDiscountBillNo + "'";
		String strPrefix = "";
		if (strRtnDiscountDateCtrl == null || strRtnDiscountDateCtrl.equals(""))
		{
			strRtnDiscountDateCtrl = "ItIsNotControl";
		}
		if (strRtnEndDateCtrl == null || strRtnEndDateCtrl.equals(""))
		{
			strRtnEndDateCtrl = "ItIsNotControl";
		}
		if (strRtnBillAmountCtrl1 == null || strRtnBillAmountCtrl1.equals(""))
		{
			strRtnBillAmountCtrl1 = "ItIsNotControl";
		}
		if (strRtnBillAmountCtrl2 == null || strRtnBillAmountCtrl2.equals(""))
		{
			strRtnBillAmountCtrl2 = "ItIsNotControl";
		}
		if (strRtnDiscountAmountCtrl == null || strRtnDiscountAmountCtrl.equals(""))
		{
			strRtnDiscountAmountCtrl = "ItIsNotControl";
		}
		if (strRtnDelayInterestCtrl == null || strRtnDelayInterestCtrl.equals(""))
		{
			strRtnDelayInterestCtrl = "ItIsNotControl";
		}
		
		if (strRtnIsLocalIDCtrl == null || strRtnIsLocalIDCtrl.equals(""))
		{
			strRtnIsLocalIDCtrl = "ItIsNotControl";
		}
		if (strRtnIsLocalIDDescCtrl == null || strRtnIsLocalIDDescCtrl.equals(""))
		{
			strRtnIsLocalIDDescCtrl = "ItIsNotControl";
		}
		if (strRtnAcceptPOTypeIDCtrl == null || strRtnAcceptPOTypeIDCtrl.equals(""))
		{
			strRtnAcceptPOTypeIDCtrl = "ItIsNotControl";
		}
		if (strRtnAcceptPOTypeIDDescCtrl == null || strRtnAcceptPOTypeIDDescCtrl.equals(""))
		{
			strRtnAcceptPOTypeIDDescCtrl = "ItIsNotControl";
		}
		String strContractIDCtrlTemp = strDiscountContractCtrl;
		String strContractNOCtrl =strContractIDCtrlTemp+"Ctrl"; 
		String strCredenceIDCtrlTemp = strDiscountCredenceCtrl;
		String strCredenceNOCtrl = strCredenceIDCtrlTemp + "Ctrl"; 
		if (strContractIDCtrlTemp == null || strContractIDCtrlTemp.equals(""))
		{
			strContractIDCtrlTemp = "ItIsNotControl";
			strContractNOCtrl = "ItIsNotControl";
		}
		if (strCredenceIDCtrlTemp == null || strCredenceIDCtrlTemp.equals(""))
		{
			strCredenceIDCtrlTemp = "ItIsNotControl";
			strCredenceNOCtrl = "ItIsNotControl";
		}
		String[] strMainNames =
			{ strCtrlName + "Ctrl",strContractIDCtrlTemp,strContractNOCtrl,strCredenceIDCtrlTemp,strCredenceNOCtrl, strRtnDiscountDateCtrl, strRtnEndDateCtrl,dtDiscountDate, strRtnBillAmountCtrl1, strRtnBillAmountCtrl2, strRtnDiscountAmountCtrl };
		String[] strMainFields = { "BillNo","ContractID","ContractNO","CredenceID","CredenceNO", "dtCreate", "EndDate" ,"dtDiscountDate", "Amount", "Amount", "CheckAmount"};
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "BillID"};
		String[] strReturnValues = { String.valueOf(lDiscountBillID) };
		String[] strDisplayNames = { URLEncoder.encode("承兑汇票号"), URLEncoder.encode("票面金额"), URLEncoder.encode("贴现日"), URLEncoder.encode("票据到期日期")};
		String[] strDisplayFields = { "BillNo", "Amount", "dDate", "EndDate" };
		int nIndex = 0;
		//获得状态数组
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getDiscountBillExtSQLforwgNew(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		if (strDiscountContractCtrl != null && !strDiscountContractCtrl.trim().equals(""))
		{
			sbSQL.append(strDiscountContractCtrl + ".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(",");
		if (strDiscountCredenceCtrl != null && !strDiscountCredenceCtrl.trim().equals(""))
		{
			sbSQL.append(strDiscountCredenceCtrl + ".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(",'");
		
		if (discountContractBillIDs != null && discountContractBillIDs.trim().length()>0)
		{
			sbSQL.append(discountContractBillIDs);
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("',");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value)");
		try
		{
			SETTMagnifier.showZoomCtrl(
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
	 * 创建贴现凭证放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lContractID 合同ID(初识值)
	 * @param lDiscountCredenceID 贴现凭证ID(初识值)
	 * @param strDiscountCredenceNo 贴现凭证号(初识值)
	 * @param lTypeID 贴现凭证类型
	 * 	(内部状态：
	 * 	0，显示全部；
	 * 	11，贴现发放——业务处理使用；
	 * 	12，贴现发放——业务复核使用；
	 * 	21，贴现收回——业务处理使用；
	 *  22，贴现收回——业务复核使用；）
	 * @param strContractCtrl 合同ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnSubAccountIDCtrl 返回值（子账户ID）对应的控件名
	 */
	public static void createDiscountCredenceCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lContractID,
		long lDiscountCredenceID,
		String strDiscountCredenceNo,
		long lTypeID,
		String strContractCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnSubAccountIDCtrl)
	{
		createDiscountCredenceCtrl(
			out,
			lOfficeID,
			lCurrencyID,
			strFormName,
			strCtrlName,
			strTitle,
			lContractID,
			lDiscountCredenceID,
			strDiscountCredenceNo,
			lTypeID,
			strContractCtrl,
			strFirstTD,
			strSecondTD,
			strNextControls,
			strRtnSubAccountIDCtrl,
			"",
			"",
			"");
	}
	/**
	 * 创建贴现凭证放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lContractID 合同ID(初识值)
	 * @param lDiscountCredenceID 贴现凭证ID(初识值)
	 * @param strDiscountCredenceNo 贴现凭证号(初识值)
	 * @param lTypeID 贴现凭证类型
	 * 	(内部状态：
	 * 	0，显示全部；
	 * 	11，贴现发放——业务处理使用；
	 * 	12，贴现发放——业务复核使用；
	 * 	21，贴现收回——业务处理使用；
	 *  22，贴现收回——业务复核使用；）
	 * @param strContractCtrl 合同ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnSubAccountIDCtrl 返回值（子账户ID）对应的控件名
	 * @param strRtnCredenceAmountCtrl 返回值（汇票金额）对应的控件名
	 * @param strRtnDiscountAmountCtrl 返回值（贴现金额）对应的控件名
	 * @param strRtnInterestAmountCtrl 返回值（利息金额）对应的控件名
	 */
	public static void createDiscountCredenceCtrl(
			JspWriter out,
			long lOfficeID,
			long lCurrencyID,
			String strFormName,
			String strCtrlName,
			String strTitle,
			long lContractID,
			long lDiscountCredenceID,
			String strDiscountCredenceNo,
			long lTypeID,
			String strContractCtrl,
			String strFirstTD,
			String strSecondTD,
			String[] strNextControls,
			String strRtnSubAccountIDCtrl,
			String strRtnCredenceAmountCtrl,
			String strRtnDiscountAmountCtrl,
			String strRtnInterestAmountCtrl)
		{
			String strMagnifierName = URLEncoder.encode("贴现凭证");
			String strMainProperty = " maxlength='30' value='" + strDiscountCredenceNo + "'";
			String strPrefix = "";
			if (strRtnSubAccountIDCtrl == null || strRtnSubAccountIDCtrl.equals(""))
			{
				strRtnSubAccountIDCtrl = "ItIsNotControl";
			}
			if (strRtnCredenceAmountCtrl == null || strRtnCredenceAmountCtrl.equals(""))
			{
				strRtnCredenceAmountCtrl = "ItIsNotControl";
			}
			if (strRtnDiscountAmountCtrl == null || strRtnDiscountAmountCtrl.equals(""))
			{
				strRtnDiscountAmountCtrl = "ItIsNotControl";
			}
			if (strRtnInterestAmountCtrl == null || strRtnInterestAmountCtrl.equals(""))
			{
				strRtnInterestAmountCtrl = "ItIsNotControl";
			}
			String[] strMainNames = { strCtrlName + "Ctrl", strRtnSubAccountIDCtrl, strRtnCredenceAmountCtrl, strRtnDiscountAmountCtrl, strRtnInterestAmountCtrl };
			String[] strMainFields = { "DiscountCredenceNo", "SubAccountID", "Amount", "DiscountAmount", "Interest" };
			String[] strReturnNames = { strCtrlName, strCtrlName + "ContractID" };
			String[] strReturnFields = { "DiscountCredenceID", "ContractID" };
			String[] strReturnValues = { String.valueOf(lDiscountCredenceID), String.valueOf(lContractID)};
			String[] strDisplayNames = { URLEncoder.encode("贴现凭证编号"), URLEncoder.encode("汇票金额"), URLEncoder.encode("贴现利息")};
			String[] strDisplayFields = { "DiscountCredenceNo", "Amount", "Interest" };
			int nIndex = 0;
			//获得状态数组
			long[] lStatusIDArray = null;
			if (lTypeID == 0)
			{
				lStatusIDArray = new long[] { LOANConstant.DiscountCredenceStatus.CHECK, LOANConstant.DiscountCredenceStatus.USED };
			}
			else
				if (lTypeID == 11)
				{
					lStatusIDArray = new long[] { LOANConstant.DiscountCredenceStatus.CHECK };
				}
				else
					if (lTypeID == 12)
					{
						lStatusIDArray = new long[] { LOANConstant.DiscountCredenceStatus.USED };
					}
					else
						if (lTypeID == 21)
						{
							lStatusIDArray = new long[] { -100 };
						}
						else
							if (lTypeID == 22)
							{
								lStatusIDArray = new long[] { -200 };
							}
			StringBuffer sbSQL = new StringBuffer(64);
			sbSQL.append("getDiscountCredenceSQL(");
			sbSQL.append(lOfficeID);
			sbSQL.append(",");
			sbSQL.append(lCurrencyID);
			sbSQL.append(",");
			if (strContractCtrl != null && !strContractCtrl.trim().equals(""))
			{
				sbSQL.append(strContractCtrl + ".value");
			}
			else
			{
				sbSQL.append("-1");
			}
			sbSQL.append(",");
			sbSQL.append("'");
			if (lStatusIDArray != null && lStatusIDArray.length > 0)
			{
				for (int i = 0; i < lStatusIDArray.length; i++)
				{
					sbSQL.append(lStatusIDArray[i]);
					if (i < lStatusIDArray.length - 1)
					{
						sbSQL.append(",");
					}
				}
			}
			else
			{
				sbSQL.append("-1");
			}
			sbSQL.append("'");
			sbSQL.append(",");
			sbSQL.append(strCtrlName);
			sbSQL.append("Ctrl.value)");
			//Log.print(sbSQL.toString());
			try
			{
				SETTMagnifier.showZoomCtrl(
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
	 * 创建来自票据模块录入的贴现汇票放大镜(银行承兑汇票号)
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lDiscountCredenceID 贴现凭证ID(初识值)
	 * @param lDiscountBillID 贴现汇票ID(初识值)
	 * @param strDiscountBillNo 贴现汇票编号(初识值)
	 * @param strDiscountContractCtrl 贴现合同ID对应的控件名称（查询时关联）
	 * @param strDiscountCredenceCtrl 贴现凭证ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnCreatDateCtrl 返回值（贴现票据出票日）对应的控件名
	 * @param strRtnEndDateCtrl 返回值（贴现票据到期日期）对应的控件名
	 * @param strRtnBillAmountCtrl1 返回值（票面金额）对应的控件名
	 * @param strRtnAcceptorNameCtrl 返回值（承兑方）对应的控件名
	 */
	public static void createBillModuleDiscountBillCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lDiscountCredenceID,
		long lDiscountBillID,
		String strDiscountBillNo,
		String strDiscountContractCtrl,
		String strDiscountCredenceCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnCreatDateCtrl,
		String strRtnEndDateCtrl,
		String strRtnBillAmountCtrl1,
		String strRtnAcceptorNameCtrl,
		String strRtnFormerOwnerCtrl)
	{
		String strMagnifierName = URLEncoder.encode("汇票号码");
		String strMainProperty = " maxlength='30' value='" + strDiscountBillNo + "'";
		String strPrefix = "";
		if (strRtnCreatDateCtrl == null || strRtnCreatDateCtrl.equals(""))
		{
			strRtnCreatDateCtrl = "ItIsNotControl";
		}
		if (strRtnEndDateCtrl == null || strRtnEndDateCtrl.equals(""))
		{
			strRtnEndDateCtrl = "ItIsNotControl";
		}
		if (strRtnBillAmountCtrl1 == null || strRtnBillAmountCtrl1.equals(""))
		{
			strRtnBillAmountCtrl1 = "ItIsNotControl";
		}
		if (strRtnAcceptorNameCtrl == null || strRtnAcceptorNameCtrl.equals(""))
		{
			strRtnAcceptorNameCtrl = "ItIsNotControl";
		}
		if (strRtnFormerOwnerCtrl == null || strRtnFormerOwnerCtrl.equals(""))
		{
			strRtnFormerOwnerCtrl = "ItIsNotControl";
		}

		String[] strMainNames =
			{ strCtrlName + "Ctrl", strRtnCreatDateCtrl, strRtnEndDateCtrl, strRtnBillAmountCtrl1, strRtnAcceptorNameCtrl,strRtnFormerOwnerCtrl};
		String[] strMainFields = { "SCODE", "DTCREATE", "DTEND", "MAMOUNT","STRACCEPTORNAME" ,"SFORMEROWNER"};
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "ID"};
		String[] strReturnValues = { String.valueOf(lDiscountBillID) };
		String[] strDisplayNames = { URLEncoder.encode("汇票号"), URLEncoder.encode("票面金额"), URLEncoder.encode("出票日"), URLEncoder.encode("到期日")};
		String[] strDisplayFields = { "SCODE", "mAmount", "DTCREATE", "DTEND" };
		int nIndex = 0;
		//获得状态数组
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getBillModuleDiscountBillSQL(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		if (strDiscountContractCtrl != null && !strDiscountContractCtrl.trim().equals(""))
		{
			sbSQL.append(strDiscountContractCtrl + ".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(",");
		if (strDiscountCredenceCtrl != null && !strDiscountCredenceCtrl.trim().equals(""))
		{
			sbSQL.append(strDiscountCredenceCtrl + ".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(",");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value)");
		//Log.print(sbSQL.toString());
		try
		{
			SETTMagnifier.showZoomCtrl(
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
	 * 创建贴现凭证放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lContractID 合同ID(初识值)
	 * @param lDiscountCredenceID 贴现凭证ID(初识值)
	 * @param strDiscountCredenceNo 贴现凭证号(初识值)
	 * @param lTypeID 贴现凭证类型
	 * 	(内部状态：
	 * 	0，显示全部；
	 * 	11，贴现发放——业务处理使用；
	 * 	12，贴现发放——业务复核使用；
	 * 	21，贴现收回——业务处理使用；
	 *  22，贴现收回——业务复核使用；）
	 * @param strContractCtrl 合同ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnSubAccountIDCtrl 返回值（子账户ID）对应的控件名
	 * @param strRtnCredenceAmountCtrl 返回值（汇票金额）对应的控件名
	 * @param strRtnDiscountAmountCtrl 返回值（贴现金额）对应的控件名
	 * @param strRtnInterestAmountCtrl 返回值（利息金额）对应的控件名
	 * @param strRtnAccountNOCtrl 返回值（账号）对应的控件名
	 * @param strRtnAccountIDCtrl 返回值（主账户ID）对应的控件名
	 */
	public static void createDiscountCredenceCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lContractID,
		long lDiscountCredenceID,
		String strDiscountCredenceNo,
		long lTypeID,
		String strContractCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnSubAccountIDCtrl,
		String strRtnCredenceAmountCtrl,
		String strRtnDiscountAmountCtrl,
		String strRtnInterestAmountCtrl,
		String strRtnAccountNOCtrl,
		String strRtnAccountIDCtrl)
	{
		String strMagnifierName = URLEncoder.encode("贴现凭证");
		String strMainProperty = " maxlength='30' value='" + strDiscountCredenceNo + "'";
		String strPrefix = "";
		if (strRtnSubAccountIDCtrl == null || strRtnSubAccountIDCtrl.equals(""))
		{
			strRtnSubAccountIDCtrl = "ItIsNotControl";
		}
		if (strRtnCredenceAmountCtrl == null || strRtnCredenceAmountCtrl.equals(""))
		{
			strRtnCredenceAmountCtrl = "ItIsNotControl";
		}
		if (strRtnDiscountAmountCtrl == null || strRtnDiscountAmountCtrl.equals(""))
		{
			strRtnDiscountAmountCtrl = "ItIsNotControl";
		}
		if (strRtnInterestAmountCtrl == null || strRtnInterestAmountCtrl.equals(""))
		{
			strRtnInterestAmountCtrl = "ItIsNotControl";
		}
		if (strRtnAccountNOCtrl == null || strRtnAccountNOCtrl.equals(""))
		{
			strRtnAccountNOCtrl = "ItIsNotControl";
		}
		if (strRtnAccountIDCtrl == null || strRtnAccountIDCtrl.equals(""))
		{
			strRtnAccountIDCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnSubAccountIDCtrl, strRtnCredenceAmountCtrl, strRtnDiscountAmountCtrl, strRtnInterestAmountCtrl, strRtnAccountNOCtrl,strRtnAccountIDCtrl };
		String[] strMainFields = { "DiscountCredenceNo", "SubAccountID", "Amount", "DiscountAmount", "Interest","AccountNO","AccountID" };
		String[] strReturnNames = { strCtrlName, strCtrlName + "ContractID" };
		String[] strReturnFields = { "DiscountCredenceID", "ContractID" };
		String[] strReturnValues = { String.valueOf(lDiscountCredenceID), String.valueOf(lContractID)};
		String[] strDisplayNames = { URLEncoder.encode("贴现凭证编号"), URLEncoder.encode("汇票金额"), URLEncoder.encode("贴现利息")};
		String[] strDisplayFields = { "DiscountCredenceNo", "Amount", "Interest" };
		int nIndex = 0;
		//获得状态数组
		long[] lStatusIDArray = null;
		if (lTypeID == 0)
		{
			lStatusIDArray = new long[] { LOANConstant.DiscountCredenceStatus.CHECK, LOANConstant.DiscountCredenceStatus.USED };
		}
		else
			if (lTypeID == 11)
			{
				lStatusIDArray = new long[] { LOANConstant.DiscountCredenceStatus.CHECK };
			}
			else
				if (lTypeID == 12)
				{
					lStatusIDArray = new long[] { LOANConstant.DiscountCredenceStatus.USED };
				}
				else
					if (lTypeID == 21)
					{
						lStatusIDArray = new long[] { -100 };
					}
					else
						if (lTypeID == 22)
						{
							lStatusIDArray = new long[] { -200 };
						}
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getDiscountCredenceSQL(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		if (strContractCtrl != null && !strContractCtrl.trim().equals(""))
		{
			sbSQL.append(strContractCtrl + ".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(",");
		sbSQL.append("'");
		if (lStatusIDArray != null && lStatusIDArray.length > 0)
		{
			for (int i = 0; i < lStatusIDArray.length; i++)
			{
				sbSQL.append(lStatusIDArray[i]);
				if (i < lStatusIDArray.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value)");
		//Log.print(sbSQL.toString());
		try
		{
			SETTMagnifier.showZoomCtrl(
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
	 * 创建商业汇票贴现类表外科目放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lSubjectID 科目ID(初识值)
	 * @param strSubjectNo 科目编号(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnSubjectTypeCtrl 返回值（科目类型）对应的控件名
	 * *@param strRtnSubjectName 返回值，带出科目名称对应的控件名
	 */
	public static void createDiscountSubjectCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lSubjectID,
		String strSubjectNo,		
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,		
		String strRtnSubjectName,
		String strRtnSubjectCode) 	
	{
		String strMagnifierName = URLEncoder.encode("商业汇票贴现类表外科目");
		String strMainProperty = " maxlength='30' value='" + strSubjectNo + "'";
		String strPrefix = "";		
		
		if (strRtnSubjectName == null || strRtnSubjectName.equals("")) {
			strRtnSubjectName="ItIsNotControl";
		}		
		
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnSubjectName };
		String[] strMainFields = { "SubjectCode", "SubjectName" };		
		
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "GLSubjectID" };
		String[] strReturnValues = { String.valueOf(lSubjectID)};
		
		String[] strDisplayNames = { URLEncoder.encode("科目代号"), URLEncoder.encode("科目名称")};
		String[] strDisplayFields = { "SubjectCode", "SubjectName" };
		
		long[] lTransactionTypeIDs = null;   
		lTransactionTypeIDs = new long[] { 
		        SETTConstant.TransactionType.DISCOUNT_INCOME_OFFBALANCE, //商业汇票贴现类表外业务收入
		        SETTConstant.TransactionType.DISCOUNT_PAYOUT_OFFBALANCE  //商业汇票贴现类表外业务付出 	   
 	   								};
 	
		int nIndex = 0;
				
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getDiscountSubjectSQL(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		if (strCtrlName != null && !strCtrlName.trim().equals(""))
		{
			sbSQL.append(strCtrlName + "Ctrl.value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(",");
		sbSQL.append("'");
		if (lTransactionTypeIDs != null && lTransactionTypeIDs.length > 0)
		{
			for (int i = 0; i < lTransactionTypeIDs.length; i++)
			{
				sbSQL.append(lTransactionTypeIDs[i]);
				if (i < lTransactionTypeIDs.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("')");			
		Log.print("####createDiscountSubjectCtrl sbSQL.toString()=" + sbSQL.toString());
		try {
			SETTMagnifier.showZoomCtrl(
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
		} catch(Exception e) {
			Log.print("商业汇票贴现类表外科目放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	
	/**
	 * 创建母公司放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lParentCorpID 母公司ID(初识值)
	 * @param strParentCorpNo 母公司编号(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnParentCorpNameCtrl 返回值（母公司名称）对应的控件名
	 */
	public static void createParentCorpCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lParentCorpID,
		String strParentCorpNo,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnParentCorpNameCtrl)
	{
		String strMagnifierName = URLEncoder.encode("母公司");
		String strMainProperty = " maxlength='30' value='" + strParentCorpNo + "'";
		String strPrefix = "";
		if (strRtnParentCorpNameCtrl == null || strRtnParentCorpNameCtrl.equals(""))
		{
			strRtnParentCorpNameCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnParentCorpNameCtrl };
		String[] strMainFields = { "ParentCorpNo", "ParentCorpName" };
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "ParentCorpID" };
		String[] strReturnValues = { String.valueOf(lParentCorpID)};
		String[] strDisplayNames = { URLEncoder.encode("母公司编号"), URLEncoder.encode("母公司名称")};
		String[] strDisplayFields = { "ParentCorpNo", "ParentCorpName" };
		int nIndex = 0;
		String strSQL = "getParentCorpSQL(" + lOfficeID + "," + strCtrlName + "Ctrl.value)";
		try
		{
			SETTMagnifier.showZoomCtrl(
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
	 * 创建上级单位1放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lSuperCorp1ID 上级单位1ID(初识值)
	 * @param strSuperCorp1No 上级单位1编号(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnSuperCorp1NameCtrl 返回值（上级单位1名称）对应的控件名
	 */
	public static void createSuperCorp1Ctrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lSuperCorp1ID,
		String strSuperCorp1No,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnSuperCorp1NameCtrl)
	{
		String strMagnifierName = URLEncoder.encode("上级单位1");
		String strMainProperty = " maxlength='40' value='" + strSuperCorp1No + "'";
		String strPrefix = "";
		if (strRtnSuperCorp1NameCtrl == null || strRtnSuperCorp1NameCtrl.equals(""))
		{
			strRtnSuperCorp1NameCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnSuperCorp1NameCtrl };
		String[] strMainFields = { "ParentCorpNo", "ParentCorpName" };
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "ParentCorpID" };
		String[] strReturnValues = { String.valueOf(lSuperCorp1ID)};
		String[] strDisplayNames = { URLEncoder.encode("上级单位1编号"), URLEncoder.encode("上级单位1名称")};
		String[] strDisplayFields = { "ParentCorpNo", "ParentCorpName" };
		int nIndex = 0;
		String strSQL = "getSuperCorp1SQL(" + lOfficeID + "," + strCtrlName + "Ctrl.value)";
		try
		{
			SETTMagnifier.showZoomCtrl(
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
			Log.print("放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	/**
	 * 创建上级单位2放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lSuperCorp2ID 上级单位2ID(初识值)
	 * @param strSuperCorp2No 上级单位2编号(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnSuperCorp2NameCtrl 返回值（上级单位2名称）对应的控件名
	 */
	public static void createSuperCorp2Ctrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lSuperCorp2ID,
		String strSuperCorp2No,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnSuperCorp2NameCtrl)
	{
		String strMagnifierName = URLEncoder.encode("上级单位2");
		String strMainProperty = " maxlength='40' value='" + strSuperCorp2No + "'";
		String strPrefix = "";
		if (strRtnSuperCorp2NameCtrl == null || strRtnSuperCorp2NameCtrl.equals(""))
		{
			strRtnSuperCorp2NameCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnSuperCorp2NameCtrl };
		String[] strMainFields = { "ParentCorpNo", "ParentCorpName" };
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "ParentCorpID" };
		String[] strReturnValues = { String.valueOf(lSuperCorp2ID)};
		String[] strDisplayNames = { URLEncoder.encode("上级单位2编号"), URLEncoder.encode("上级单位2名称")};
		String[] strDisplayFields = { "ParentCorpNo", "ParentCorpName" };
		int nIndex = 0;
		String strSQL = "getSuperCorp2SQL(" + lOfficeID + "," + strCtrlName + "Ctrl.value)";
		try
		{
			SETTMagnifier.showZoomCtrl(
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
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnClientNameCtrl, "levelcode"};
		String[] strMainFields = { "clientNo", "clientName", "levelcode" };
		//特殊处理
		long lFromClient = 0;
		if (lClientID > 0)
		{
			lFromClient = 1;
		}
		String[] strReturnNames = { strCtrlName, strCtrlName + "FromClient" };
		String[] strReturnFields = { "ClientID", "FromClient" };
		String[] strReturnValues = { String.valueOf(lClientID), String.valueOf(lFromClient)};
		String[] strDisplayNames = { URLEncoder.encode("客户编号"), URLEncoder.encode("客户名称"),URLEncoder.encode("所属分支机构")};
		String[] strDisplayFields = { "clientNo", "clientName" ,"OfficeName"};
		int nIndex = 0;
		String strSQL = "getClientSQL_OLD(" + lOfficeID + "," + strCtrlName + "Ctrl.value)";
		try
		{
			SETTMagnifier.showZoomCtrl(
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
	 * @param strClientNo 客户编号(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 * @param clientid 客户id
	 */
	public static void createStockClientCtrl(
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
		String strRtnClientNameCtrl1,
		String strRtnClientNameCtrl2,
		long clientid)
	{
		String strMagnifierName = URLEncoder.encode("客户");
		String strMainProperty = " maxlength='30' value='" + strClientNo + "'";
		String strPrefix = "";
		if (strRtnClientNameCtrl1 == null || strRtnClientNameCtrl1.equals(""))
		{
			strRtnClientNameCtrl1 = "ItIsNotControl";
		}
		if (strRtnClientNameCtrl2 == null || strRtnClientNameCtrl2.equals(""))
		{
			strRtnClientNameCtrl2 = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnClientNameCtrl1,strRtnClientNameCtrl2 };
		String[] strMainFields = { "clientNo", "clientName" ,"EngName"};
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
		String strSQL = "getStockClientSQL(" + lOfficeID + "," + strCtrlName + "Ctrl.value,"+clientid+")";
		try
		{
			SETTMagnifier.showZoomCtrl(
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
	 * 创建外部单位放大镜
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
	 * @param clientid 客户id
	 */
	public static void createExtStockClientCtrl(
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
		String strRtnClientNameCtrl1,
		String strRtnClientNameCtrl2,
		String strRtnLinkMan,
		String strRtnTelephone
		)
	{
		String strMagnifierName = URLEncoder.encode("外部单位");
		String strMainProperty = " maxlength='30' value='" + strClientNo + "'";
		String strPrefix = "";
		if (strRtnClientNameCtrl1 == null || strRtnClientNameCtrl1.equals(""))
		{
			strRtnClientNameCtrl1 = "ItIsNotControl";
		}
		if (strRtnClientNameCtrl2 == null || strRtnClientNameCtrl2.equals(""))
		{
			strRtnClientNameCtrl2 = "ItIsNotControl";
		}
		if (strRtnLinkMan == null || strRtnLinkMan.equals(""))
		{
			strRtnLinkMan = "ItIsNotControl";
		}
		if (strRtnTelephone == null || strRtnTelephone.equals(""))
		{
			strRtnTelephone = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnClientNameCtrl1,strRtnClientNameCtrl2,strRtnLinkMan,strRtnTelephone };
		String[] strMainFields = { "clientNo", "clientName" ,"EngName","linkman","telephone"};
		String[] strReturnNames = { strCtrlName};
		String[] strReturnFields = { "ClientID"};
		String[] strReturnValues = { String.valueOf(lClientID)};
		String[] strDisplayNames = { URLEncoder.encode("客户编号"), URLEncoder.encode("客户名称")};
		String[] strDisplayFields = { "clientNo", "clientName" };
		int nIndex = 0;
		String strSQL = "getExtStockClientSQL(" + strCtrlName + "Ctrl.value,"+lOfficeID+","+lCurrencyID+")";
		try
		{
			SETTMagnifier.showZoomCtrl(
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
	 * 创建二级户账户放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lAccountID 账户ID(初识值)
	 * @param lBankAccountID 银行账户ID(初识值)
	 * @param strAccountNo 账户编号(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 */
	public static void createTwoLevelAccountCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lAccountID,
		String strAccountNo,
		long lBankAccountID,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnAccountNameCtrl)
	{
		String strMagnifierName = URLEncoder.encode("账户");
		String strMainProperty = " maxlength='30' value='" + strAccountNo + "'";
		String strPrefix = "";
		if (strRtnAccountNameCtrl == null || strRtnAccountNameCtrl.equals(""))
		{
			strRtnAccountNameCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnAccountNameCtrl };
		String[] strMainFields = { "AccountNo", "AccountName" };
		String[] strReturnNames = { strCtrlName,"bankAccountID" };
		String[] strReturnFields = { "AccountID","bankid"};
		String[] strReturnValues = { String.valueOf(lAccountID),String.valueOf(lBankAccountID)};
		String[] strDisplayNames = { URLEncoder.encode("账户编号"), URLEncoder.encode("账户名称"),URLEncoder.encode("银行账户编号"),URLEncoder.encode("银行账户名称"),};
		String[] strDisplayFields = { "AccountNo", "AccountName","bankAccountNO","bankAccountName" };
		int nIndex = 0;
		String strSQL = "getTwoLevelAccountSQL(" + lOfficeID + ","  + lCurrencyID + ","+ strCtrlName+ ".value" + ","+ strCtrlName + "Ctrl.value)";
		try
		{
			SETTMagnifier.showZoomCtrl(
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
			Log.print("二级户账户放大镜[" + strCtrlName + "]异常：" + e.toString());
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
			SETTMagnifier.showZoomCtrl(
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
			SETTMagnifier.showZoomCtrl(
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
	 * 创建用户放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lUserID 用户ID(初识值)
	 * @param strUserName 用户名称(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 */
	public static void createUserCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lUserID,
		String strUserName,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls)
	{
		String strMagnifierName = URLEncoder.encode("用户");
		String strMainProperty = " maxlength='30' value='" + strUserName + "'";
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl" };
		String[] strMainFields = { "UserName" };
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "UserID" };
		String[] strReturnValues = { String.valueOf(lUserID)};
		String[] strDisplayNames = { URLEncoder.encode("用户名称"),URLEncoder.encode("所属机构")};
		String[] strDisplayFields = { "UserName","OfficeName" };
		int nIndex = 0;
		String strSQL = "getUserSQL(" + lOfficeID + "," + lCurrencyID + "," + strCtrlName + "Ctrl.value)";
		try
		{
			SETTMagnifier.showZoomCtrl(
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
			Log.print(strMagnifierName + "放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	/**
	 * 创建用户放大镜(该办事处下过滤管理员)
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lUserID 用户ID(初识值)
	 * @param strUserName 用户名称(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 */
	public static void createFFUserCtrl(
			JspWriter out,
			long lOfficeID,
			long lCurrencyID,
			String strFormName,
			String strCtrlName,
			String strTitle,
			long lUserID,
			String strUserName,
			String strFirstTD,
			String strSecondTD,
			String[] strNextControls)
	{
		String strMagnifierName = URLEncoder.encode("用户");
		String strMainProperty = " maxlength='30' value='" + strUserName + "'";
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl" };
		String[] strMainFields = { "UserName" };
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "UserID" };
		String[] strReturnValues = { String.valueOf(lUserID)};
		String[] strDisplayNames = { URLEncoder.encode("用户名称"),URLEncoder.encode("所属机构")};
		String[] strDisplayFields = { "UserName","OfficeName" };
		int nIndex = 0;
		String strSQL = "getFFUserSQL(" + lOfficeID + "," + lCurrencyID + "," + strCtrlName + "Ctrl.value)";
		try
		{
			SETTMagnifier.showZoomCtrl(
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
			Log.print(strMagnifierName + "放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}

	/**
	 * 创建报单号放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lBankChequeNOID 支票号ID(初识值)
	 * @param strBankChequeNO 支票号(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControls 下一个（或多个）获得焦点的控件
	 */
	public static void createBankChequeNOCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lBankChequeNOID,
		String strBankChequeNO,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls)
	{
		String strMagnifierName = URLEncoder.encode("支票号");
		String strMainProperty = " maxlength='30' value='" + strBankChequeNO + "'";
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl" };
		String[] strMainFields = { "sBankChequeNO" };
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = {"sBankChequeNO"};
		String[] strReturnValues = { String.valueOf(lBankChequeNOID)};
		String[] strDisplayNames = { URLEncoder.encode("支票号")};
		String[] strDisplayFields = { "sBankChequeNO" };
		int nIndex = 0;
		String strSQL = "getBankChequeNOSQL(" + lOfficeID + "," + lCurrencyID + "," + strCtrlName + "Ctrl.value)";
		try
		{
			SETTMagnifier.showZoomCtrl(
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
			Log.print(strMagnifierName + "放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	/**
	 * 创建报单号放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lDeclarationNOID 报单号ID(初识值)
	 * @param strDeclarationNO 报单号(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControls 下一个（或多个）获得焦点的控件
	 */
	public static void createDeclarationNOCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lDeclarationNOID,
		String strDeclarationNO,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls)
	{
		String strMagnifierName = URLEncoder.encode("报单号");
		String strMainProperty = " maxlength='30' value='" + strDeclarationNO + "'";
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl" };
		String[] strMainFields = { "sDeclarationNO" };
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = {"sDeclarationNO"};
		String[] strReturnValues = { String.valueOf(lDeclarationNOID)};
		String[] strDisplayNames = { URLEncoder.encode("报单号")};
		String[] strDisplayFields = { "sDeclarationNO" };
		int nIndex = 0;
		String strSQL = "getsDeclarationNOSQL(" + lOfficeID + "," + lCurrencyID + "," + strCtrlName + "Ctrl.value)";
		try
		{
			SETTMagnifier.showZoomCtrl(
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
			Log.print(strMagnifierName + "放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	
	/**
	 * 创建清算表单的凭证种类放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lReconingTypeID 凭证种类ID(初识值)
	 * @param strReconingTypeDesc 凭证种类描述(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 */
	public static void createReckoningBillTypeCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lReconingTypeID,
		String strReconingTypeDesc,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls)
	{
		String strMagnifierName = URLEncoder.encode("凭证种类");
		String strMainProperty = " maxlength='30' value='" + strReconingTypeDesc + "'";
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl" };
		String[] strMainFields = { "ReconingTypeDesc" };
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "ReconingTypeID" };
		String[] strReturnValues = { String.valueOf(lReconingTypeID)};
		String[] strDisplayNames = { URLEncoder.encode("编号"), URLEncoder.encode("凭证种类")};
		String[] strDisplayFields = { "ReconingTypeCode", "ReconingTypeDesc" };
		int nIndex = 1;
		String strSQL = "getReconingTypeSQL(" + lOfficeID + "," + lCurrencyID + "," + strCtrlName + "Ctrl.value)";
		try
		{
			SETTMagnifier.showZoomCtrl(
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
			Log.print(strMagnifierName + "放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	/**
	 * 创建总账科目放大镜(只用于表外业务)
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
	 * *@param strRtnGLSubjectName 返回值，带出总账科目名称对应的控件名
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
		String strRtnGLSubjectTypeCtrl,
		String strRtnGLSubjectName) {
		String strMagnifierName = URLEncoder.encode("总账科目");
		String strMainProperty = " size='50' maxlength='30' value='" + strGLSubjectNo + "'";
		String strPrefix = "";
		
		
		if (strRtnGLSubjectTypeCtrl == null || strRtnGLSubjectTypeCtrl.equals("")) {
			strRtnGLSubjectTypeCtrl="ItIsNotControl";
		}
		
		if (strRtnGLSubjectName == null || strRtnGLSubjectName.equals("")) {
			strRtnGLSubjectName="ItIsNotControl";
		}
		
		
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnGLSubjectName, strRtnGLSubjectTypeCtrl };
		String[] strMainFields = { "GLSubjectCode", "GLSubjectName", "GLSubjectType" }; 
		
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "GLSubjectID" };
		String[] strReturnValues = { String.valueOf(lGLSubjectID)};
		
		String[] strDisplayNames = { URLEncoder.encode("科目代号"), URLEncoder.encode("科目名称")};
		String[] strDisplayFields = { "GLSubjectCode", "GLSubjectName" };
		int nIndex = 0;
		long nSubjectType = 6;//表外科目
		String strSQL = "getGLSubjectSQL(" + lOfficeID + "," + lCurrencyID + "," + strCtrlName + "Ctrl.value," + lIsleaf + ","+nSubjectType+")";
		try {
			SETTMagnifier.showZoomCtrl(
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
		String strMainProperty = " size='50' maxlength='30' value='" + strGLSubjectNo + "'";
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
			SETTMagnifier.showZoomCtrl(
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
	 * 创建总账类类型放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lGLTypeID 总账类类型ID(初识值)
	 * @param strGLTypeDesc 总账类类型描述(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnGLTypeNoCtrl 返回值（总账类类型编号）对应的控件名
	 * @param strRtnSubjectNoCtrl 返回值（总账类对应科目）对应的控件名
	 */
	public static void createGeneralLedgerTypeCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lGLTypeID,
		String strGLTypeDesc,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnGLTypeNoCtrl,
		String strRtnSubjectNoCtrl)
	{
		String strMagnifierName = URLEncoder.encode("总账业务");
		String strMainProperty = " maxlength='30' value='" + strGLTypeDesc + "'";
		String strPrefix = "";
		if (strRtnGLTypeNoCtrl == null || strRtnGLTypeNoCtrl.equals(""))
		{
			strRtnGLTypeNoCtrl = "ItIsNotControl";
		}
		if (strRtnSubjectNoCtrl == null || strRtnSubjectNoCtrl.equals(""))
		{
			strRtnSubjectNoCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnGLTypeNoCtrl, strRtnSubjectNoCtrl };
		String[] strMainFields = { "GLName", "GLCode", "SubjectCode" };
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "GLID" };
		String[] strReturnValues = { String.valueOf(lGLTypeID)};
		String[] strDisplayNames = { URLEncoder.encode("分录编码"), URLEncoder.encode("分录名称")};
		String[] strDisplayFields = { "GLCode", "GLName" };
		int nIndex = 0;
		String strSQL = "getGLTypeSQL(" + lOfficeID + "," + lCurrencyID + "," + strCtrlName + "Ctrl.value)";
		try
		{
			SETTMagnifier.showZoomCtrl(
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
			Log.print("放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	/**
	 * 创建转贴现类型放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lGLTypeID 转贴现类型ID(初识值)
	 * @param strGLTypeDesc 总账类类型描述(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnGLTypeNoCtrl 返回值（转贴现类型编号）对应的控件名
	 * @param strRtnSubjectNoCtrl 返回值（转贴现对应科目）对应的控件名
	 */
	public static void createTransDiscountTypeCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lGLTypeID,
		String strGLTypeDesc,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnGLTypeNoCtrl
		)
	{
		String strMagnifierName = URLEncoder.encode("转贴现业务");
		String strMainProperty = " maxlength='30' value='" + strGLTypeDesc + "'";
		String strPrefix = "";
		if (strRtnGLTypeNoCtrl == null || strRtnGLTypeNoCtrl.equals(""))
		{
			strRtnGLTypeNoCtrl = "ItIsNotControl";
		}
		
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnGLTypeNoCtrl };
		String[] strMainFields = { "sName", "sCode" };
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "ID" };
		String[] strReturnValues = { String.valueOf(lGLTypeID)};
		String[] strDisplayNames = { URLEncoder.encode("分录编码"), URLEncoder.encode("分录名称")};
		String[] strDisplayFields = { "sCode", "sName" };
		int nIndex = 0;
		String strSQL = "getZTXTypeSQL(" + lOfficeID + "," + lCurrencyID + "," + strCtrlName + "Ctrl.value)";
		try
		{
			SETTMagnifier.showZoomCtrl(
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
			Log.print("放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	/**
	 * 创建挂失冻结业务存款单据号放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lUserID 当前用户ID(初识值)
	 * @param lAccountID 主账户ID(初识值) 
	 * @param lSubAccountID 子账户ID(初识值)
	 * @param strDepositNo 定期(通知)存单号(初识值)
	 * @param lDepositTypeID 存单类型：1，定期；2，通知。
	 * @param lTypeID 放大镜类型，取值如下：
	 *  0、显示所有的定期（通知）单据号
	 * 	1、定期（通知）开立--复核匹配时使用
	 * 	21、定期（通知）支取--业务处理时使用
	 *  22、定期（通知）支取--业务复核时使用
	 *  3、定期续期转存--业务处理时使用（仅显示已到期的存单）
	 * 
	 * @param strAccountIDCtrl（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnEndDateCtrl 返回值（到期日）对应的控件名
	 * @param strRtnOpenDateCtrl 返回值（开户日）对应的控件名
	 * @param strRtnCapitalCtrl 返回值（本金）对应的控件名
	 * @param strRtnBalanceCtrl 返回值（余额）对应的控件名
	 * @param strRtnRateCtrl 返回值（利率）对应的控件名
	 * @param strRtnIntervalCtrl 返回值（期限）对应的控件名
	 * @param strRtnStartDateCtrl 返回值（开始日期）对应的控件名
	 */
	public static void createLossAndFreezeDepositNoCtrl(
		JspWriter out,
		long lSubStatusId,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lUserID,
		long lAccountID,
		long lSubAccountID,
		String strDepositNo,
		long lDepositTypeID,
		long lTypeID,
		String strAccountIDCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnEndDateCtrl,
		String strRtnOpenDateCtrl,
		String strRtnCapitalCtrl,
		String strRtnBalanceCtrl,
		String strRtnRateCtrl,
		String strRtnIntervalCtrl,
		String strRtnStartDateCtrl)
	{
		String strMagnifierName = URLEncoder.encode("存款单据号");
		if (lDepositTypeID == 1)
		{
			strMagnifierName = URLEncoder.encode("定期存款单据号");
		}
		else
			if (lDepositTypeID == 2)
			{
				strMagnifierName = URLEncoder.encode("通知存款单据号");
			}
		String strMainProperty = " maxlength='30' value='" + strDepositNo + "'";
		String strPrefix = "";
		if (strRtnEndDateCtrl == null || strRtnEndDateCtrl.equals(""))
		{
			strRtnEndDateCtrl = "ItIsNotControl";
		}
		if (strRtnOpenDateCtrl == null || strRtnOpenDateCtrl.equals(""))
		{
			strRtnOpenDateCtrl = "ItIsNotControl";
		}
		if (strRtnCapitalCtrl == null || strRtnCapitalCtrl.equals(""))
		{
			strRtnCapitalCtrl = "ItIsNotControl";
		}
		if (strRtnBalanceCtrl == null || strRtnBalanceCtrl.equals(""))
		{
			strRtnBalanceCtrl = "ItIsNotControl";
		}
		if (strRtnRateCtrl == null || strRtnRateCtrl.equals(""))
		{
			strRtnRateCtrl = "ItIsNotControl";
		}
		if (strRtnIntervalCtrl == null || strRtnIntervalCtrl.equals(""))
		{
			strRtnIntervalCtrl = "ItIsNotControl";
		}
		if (strRtnStartDateCtrl == null || strRtnStartDateCtrl.equals(""))
		{
			strRtnStartDateCtrl = "ItIsNotControl";
		}
		String[] strMainNames =
			{ strCtrlName + "Ctrl", strRtnEndDateCtrl, strRtnOpenDateCtrl, strRtnCapitalCtrl, strRtnBalanceCtrl, strRtnRateCtrl, strRtnIntervalCtrl, strRtnStartDateCtrl };
		String[] strMainFields = { "DepositNo", "EndDate", "OpenDate", "Capital", "Balance", "Rate", "Interval", "StartDate" };
		String[] strReturnNames = { strCtrlName, strCtrlName + "AccountID" };
		String[] strReturnFields = { "SubAccountID", "AccountID" };
		String[] strReturnValues = { String.valueOf(lSubAccountID), String.valueOf(lAccountID)};
		String[] strDisplayNames = { URLEncoder.encode("存款单据号")};
		String[] strDisplayFields = { "DepositNo" };
		//支取或续期转存时，显示不同
		if (lTypeID == 21 || lTypeID == 22 || lTypeID == 3 || lTypeID == 0)
		{
			if (lDepositTypeID == 1)
			{
				//定期显示单据号、余额、到期日
				strDisplayNames = new String[] { URLEncoder.encode("存款单据号"), URLEncoder.encode("余额"), URLEncoder.encode("到期日")};
				strDisplayFields = new String[] { "DepositNo", "Balance", "EndDate" };
			}
			else
			{
				//定期显示单据号、余额、开户日期
				strDisplayNames = new String[] { URLEncoder.encode("存款单据号"), URLEncoder.encode("余额"), URLEncoder.encode("开户日")};
				strDisplayFields = new String[] { "DepositNo", "Balance", "OpenDate" };
			}
		}
		int nIndex = 0;
		String strSQL = "";
		if (strAccountIDCtrl != null && !strAccountIDCtrl.trim().equals(""))
		{
			strSQL =
				"getLossAndFreezeDepositNoSQL("
				    + lSubStatusId
					+ ","
					+ lOfficeID
					+ ","
					+ lCurrencyID
					+ ","
					+ lDepositTypeID
					+ ","
					+ strAccountIDCtrl
					+ ".value,"
					+ lUserID
					+ ","
					+ strCtrlName
					+ "Ctrl.value,"
					+ lTypeID
					+ ",'"
					
					+ Env.getSystemDateString(lOfficeID, lCurrencyID)
					+ "')";
		}
		else
		{
			strSQL =
				"getLossAndFreezeDepositNoSQL("
				    + lSubStatusId
					+ ","
					+ lOfficeID
					+ ","
					+ lCurrencyID
					+ ","
					+ lDepositTypeID
					+ ",-1,"
					+ lUserID
					+ ","
					+ strCtrlName
					+ "Ctrl.value,"
					+ lTypeID
					+ ",'"
					+ Env.getSystemDateString(lOfficeID, lCurrencyID)
					+ "')";
		}
		try
		{
			SETTMagnifier.showZoomCtrl(
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
			Log.print("定期存款单据号放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	/**
	 * 创建定期(通知)存款单据号放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lUserID 当前用户ID(初识值)
	 * @param lAccountID 主账户ID(初识值) 
	 * @param lSubAccountID 子账户ID(初识值)
	 * @param strDepositNo 定期(通知)存单号(初识值)
	 * @param lDepositTypeID 存单类型：1，定期；2，通知。
	 * @param lTypeID 放大镜类型，取值如下：
	 * 	1、定期（通知）开立--复核匹配时使用
	 * 	21、定期（通知）支取--业务处理时使用
	 *  22、定期（通知）支取--业务复核时使用
	 *  3、定期续期转存--业务处理时使用（仅显示已到期的存单）
	 * 
	 * @param strAccountIDCtrl（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnEndDateCtrl 返回值（到期日）对应的控件名
	 */
	public static void createFixedDepositNoCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lUserID,
		long lAccountID,
		long lSubAccountID,
		String strDepositNo,
		long lDepositTypeID,
		long lTypeID,
		String strAccountIDCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnEndDateCtrl)
	{
		createFixedDepositNoCtrl(
			out,
			lOfficeID,
			lCurrencyID,
			strFormName,
			strCtrlName,
			strTitle,
			lUserID,
			lAccountID,
			lSubAccountID,
			strDepositNo,
			lDepositTypeID,
			lTypeID,
			strAccountIDCtrl,
			strFirstTD,
			strSecondTD,
			strNextControls,
			strRtnEndDateCtrl,
			"",
			"",
			"",
			"",
			"",
			"");
	}
	
	/**
	 * 创建定期(通知)存款单据号放大镜 -- 查询用 4Query
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lUserID 当前用户ID(初识值)
	 * @param lAccountID 主账户ID(初识值) 
	 * @param lSubAccountID 子账户ID(初识值)
	 * @param strDepositNo 定期(通知)存单号(初识值)
	 * @param lDepositTypeID 存单类型：1，定期；2，通知。
	 * @param lTypeID 放大镜类型，取值如下：
	 * 	1、定期（通知）开立--复核匹配时使用
	 * 	21、定期（通知）支取--业务处理时使用
	 *  22、定期（通知）支取--业务复核时使用
	 *  3、定期续期转存--业务处理时使用（仅显示已到期的存单）
	 * 
	 * @param strAccountIDCtrl（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnEndDateCtrl 返回值（到期日）对应的控件名
	 */
	public static void createFixedDepositNoCtrl4Query(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lUserID,
		long lAccountID,
		long lSubAccountID,
		String strDepositNo,
		long lDepositTypeID,
		long lTypeID,
		String strAccountIDCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnEndDateCtrl)
	{
		createFixedDepositNoCtrl4Query(
			out,
			lOfficeID,
			lCurrencyID,
			strFormName,
			strCtrlName,
			strTitle,
			lUserID,
			lAccountID,
			lSubAccountID,
			strDepositNo,
			lDepositTypeID,
			lTypeID,
			strAccountIDCtrl,
			strFirstTD,
			strSecondTD,
			strNextControls,
			strRtnEndDateCtrl,
			"",
			"",
			"",
			"",
			"",
			"");
	}
	
	/**
	 * 创建定期(通知)存款单据号放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lUserID 当前用户ID(初识值)
	 * @param lAccountID 主账户ID(初识值) 
	 * @param lSubAccountID 子账户ID(初识值)
	 * @param strDepositNo 定期(通知)存单号(初识值)
	 * @param lDepositTypeID 存单类型：1，定期；2，通知。
	 * @param lTypeID 放大镜类型，取值如下：
	 *  0、显示所有的定期（通知）单据号
	 * 	1、定期（通知）开立--复核匹配时使用
	 * 	21、定期（通知）支取--业务处理时使用
	 *  22、定期（通知）支取--业务复核时使用
	 *  3、定期续期转存--业务处理时使用（仅显示已到期的存单）
	 * 
	 * @param strAccountIDCtrl（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnEndDateCtrl 返回值（到期日）对应的控件名
	 * @param strRtnOpenDateCtrl 返回值（开户日）对应的控件名
	 * @param strRtnCapitalCtrl 返回值（本金）对应的控件名
	 * @param strRtnBalanceCtrl 返回值（余额）对应的控件名
	 * @param strRtnRateCtrl 返回值（利率）对应的控件名
	 * @param strRtnIntervalCtrl 返回值（期限）对应的控件名
	 * @param strRtnStartDateCtrl 返回值（开始日期）对应的控件名
	 */
	public static void createFixedDepositNoCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lUserID,
		long lAccountID,
		long lSubAccountID,
		String strDepositNo,
		long lDepositTypeID,
		long lTypeID,
		String strAccountIDCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnEndDateCtrl,
		String strRtnOpenDateCtrl,
		String strRtnCapitalCtrl,
		String strRtnBalanceCtrl,
		String strRtnRateCtrl,
		String strRtnIntervalCtrl,
		String strRtnStartDateCtrl)
	{
		String strMagnifierName = URLEncoder.encode("存款单据号");
		if (lDepositTypeID == 1)
		{
			strMagnifierName = URLEncoder.encode("定期存款单据号");
		}
		else if (lDepositTypeID == 2)
		{
			strMagnifierName = URLEncoder.encode("通知存款单据号");
		}
		else if(lDepositTypeID == 6)
		{
			strMagnifierName = URLEncoder.encode("保证金存款单据号");
		}
		
		String strMainProperty = " maxlength='30' value='" + strDepositNo + "'";
		String strPrefix = "";
		if (strRtnEndDateCtrl == null || strRtnEndDateCtrl.equals(""))
		{
			strRtnEndDateCtrl = "ItIsNotControl";
		}
		if (strRtnOpenDateCtrl == null || strRtnOpenDateCtrl.equals(""))
		{
			strRtnOpenDateCtrl = "ItIsNotControl";
		}
		if (strRtnCapitalCtrl == null || strRtnCapitalCtrl.equals(""))
		{
			strRtnCapitalCtrl = "ItIsNotControl";
		}
		if (strRtnBalanceCtrl == null || strRtnBalanceCtrl.equals(""))
		{
			strRtnBalanceCtrl = "ItIsNotControl";
		}
		if (strRtnRateCtrl == null || strRtnRateCtrl.equals(""))
		{
			strRtnRateCtrl = "ItIsNotControl";
		}
		if (strRtnIntervalCtrl == null || strRtnIntervalCtrl.equals(""))
		{
			strRtnIntervalCtrl = "ItIsNotControl";
		}
		if (strRtnStartDateCtrl == null || strRtnStartDateCtrl.equals(""))
		{
			strRtnStartDateCtrl = "ItIsNotControl";
		}
		String[] strMainNames =
			{ strCtrlName + "Ctrl", strRtnEndDateCtrl, strRtnOpenDateCtrl, strRtnCapitalCtrl, strRtnBalanceCtrl, strRtnRateCtrl, strRtnIntervalCtrl, strRtnStartDateCtrl };
		String[] strMainFields = { "DepositNo", "EndDate", "OpenDate", "Capital", "Balance", "Rate", "Interval", "StartDate" };
		String[] strReturnNames = { strCtrlName, strCtrlName + "AccountID" };
		String[] strReturnFields = { "SubAccountID", "AccountID" };
		String[] strReturnValues = { String.valueOf(lSubAccountID), String.valueOf(lAccountID)};
		String[] strDisplayNames = { URLEncoder.encode("存款单据号")};
		String[] strDisplayFields = { "DepositNo" };
		//支取或续期转存时，显示不同
		if (lTypeID == 21 || lTypeID == 22 || lTypeID == 23 || lTypeID == 3 || lTypeID == 0 || lTypeID == SETTConstant.TransactionType.WITHDRAWMARGIN
				|| lTypeID == 303 || lTypeID == 101)
		{
			if (lDepositTypeID == 1)
			{
				//定期显示单据号、余额、到期日
				strDisplayNames = new String[] { URLEncoder.encode("存款单据号"), URLEncoder.encode("余额"), URLEncoder.encode("到期日")};
				strDisplayFields = new String[] { "DepositNo", "Balance", "EndDate" };
			}else if(lDepositTypeID == 6){
//				保证金显示单据号、可用余额、开户日期
				strDisplayNames = new String[] { URLEncoder.encode("存款单据号"), URLEncoder.encode("可用余额"), URLEncoder.encode("开户日")};
				strDisplayFields = new String[] { "DepositNo", "Balance", "OpenDate" };
			}
			else
			{
				//定期显示单据号、余额、开户日期
				strDisplayNames = new String[] { URLEncoder.encode("存款单据号"), URLEncoder.encode("余额"), URLEncoder.encode("开户日")};
				strDisplayFields = new String[] { "DepositNo", "Balance", "OpenDate" };
			}
		}
		int nIndex = 0;
		String strSQL = "";
		if (strAccountIDCtrl != null && !strAccountIDCtrl.trim().equals(""))
		{
			strSQL =
				"getFixedDepositNoSQL("
					+ lOfficeID
					+ ","
					+ lCurrencyID
					+ ","
					+ lDepositTypeID
					+ ","
					+ strAccountIDCtrl
					+ ".value,"
					+ lUserID
					+ ","
					+ strCtrlName
					+ "Ctrl.value,"
					+ lTypeID
					+ ",'"
					+ Env.getSystemDateString(lOfficeID, lCurrencyID)
					+ "')";
		}
		else
		{
			strSQL =
				"getFixedDepositNoSQL("
					+ lOfficeID
					+ ","
					+ lCurrencyID
					+ ","
					+ lDepositTypeID
					+ ",-1,"
					+ lUserID
					+ ","
					+ strCtrlName
					+ "Ctrl.value,"
					+ lTypeID
					+ ",'"
					+ Env.getSystemDateString(lOfficeID, lCurrencyID)
					+ "')";
		}
		try
		{
			SETTMagnifier.showZoomCtrl(
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
			Log.print("定期存款单据号放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	
	
	/**
	 * 创建定期(通知)存款单据号放大镜 --查询用 4Query
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lUserID 当前用户ID(初识值)
	 * @param lAccountID 主账户ID(初识值) 
	 * @param lSubAccountID 子账户ID(初识值)
	 * @param strDepositNo 定期(通知)存单号(初识值)
	 * @param lDepositTypeID 存单类型：1，定期；2，通知。
	 * @param lTypeID 放大镜类型，取值如下：
	 *  0、显示所有的定期（通知）单据号
	 * 	1、定期（通知）开立--复核匹配时使用
	 * 	21、定期（通知）支取--业务处理时使用
	 *  22、定期（通知）支取--业务复核时使用
	 *  3、定期续期转存--业务处理时使用（仅显示已到期的存单）
	 * 
	 * @param strAccountIDCtrl（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnEndDateCtrl 返回值（到期日）对应的控件名
	 * @param strRtnOpenDateCtrl 返回值（开户日）对应的控件名
	 * @param strRtnCapitalCtrl 返回值（本金）对应的控件名
	 * @param strRtnBalanceCtrl 返回值（余额）对应的控件名
	 * @param strRtnRateCtrl 返回值（利率）对应的控件名
	 * @param strRtnIntervalCtrl 返回值（期限）对应的控件名
	 * @param strRtnStartDateCtrl 返回值（开始日期）对应的控件名
	 */
	public static void createFixedDepositNoCtrl4Query(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lUserID,
		long lAccountID,
		long lSubAccountID,
		String strDepositNo,
		long lDepositTypeID,
		long lTypeID,
		String strAccountIDCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnEndDateCtrl,
		String strRtnOpenDateCtrl,
		String strRtnCapitalCtrl,
		String strRtnBalanceCtrl,
		String strRtnRateCtrl,
		String strRtnIntervalCtrl,
		String strRtnStartDateCtrl)
	{
		String strMagnifierName = URLEncoder.encode("存款单据号");
		if (lDepositTypeID == 1)
		{
			strMagnifierName = URLEncoder.encode("定期存款单据号");
		}
		else if (lDepositTypeID == 2)
		{
			strMagnifierName = URLEncoder.encode("通知存款单据号");
		}
		else if(lDepositTypeID == 6)
		{
			strMagnifierName = URLEncoder.encode("保证金存款单据号");
		}
		
		String strMainProperty = " maxlength='30' value='" + strDepositNo + "'";
		String strPrefix = "";
		if (strRtnEndDateCtrl == null || strRtnEndDateCtrl.equals(""))
		{
			strRtnEndDateCtrl = "ItIsNotControl";
		}
		if (strRtnOpenDateCtrl == null || strRtnOpenDateCtrl.equals(""))
		{
			strRtnOpenDateCtrl = "ItIsNotControl";
		}
		if (strRtnCapitalCtrl == null || strRtnCapitalCtrl.equals(""))
		{
			strRtnCapitalCtrl = "ItIsNotControl";
		}
		if (strRtnBalanceCtrl == null || strRtnBalanceCtrl.equals(""))
		{
			strRtnBalanceCtrl = "ItIsNotControl";
		}
		if (strRtnRateCtrl == null || strRtnRateCtrl.equals(""))
		{
			strRtnRateCtrl = "ItIsNotControl";
		}
		if (strRtnIntervalCtrl == null || strRtnIntervalCtrl.equals(""))
		{
			strRtnIntervalCtrl = "ItIsNotControl";
		}
		if (strRtnStartDateCtrl == null || strRtnStartDateCtrl.equals(""))
		{
			strRtnStartDateCtrl = "ItIsNotControl";
		}
		String[] strMainNames =
			{ strCtrlName + "Ctrl", strRtnEndDateCtrl, strRtnOpenDateCtrl, strRtnCapitalCtrl, strRtnBalanceCtrl, strRtnRateCtrl, strRtnIntervalCtrl, strRtnStartDateCtrl };
		String[] strMainFields = { "DepositNo", "EndDate", "OpenDate", "Capital", "Balance", "Rate", "Interval", "StartDate" };
		String[] strReturnNames = { strCtrlName, strCtrlName + "AccountID" };
		String[] strReturnFields = { "SubAccountID", "AccountID" };
		String[] strReturnValues = { String.valueOf(lSubAccountID), String.valueOf(lAccountID)};
		String[] strDisplayNames = { URLEncoder.encode("存款单据号")};
		String[] strDisplayFields = { "DepositNo" };
		//支取或续期转存时，显示不同
		if (lTypeID == 21 || lTypeID == 22 || lTypeID == 23 || lTypeID == 3 || lTypeID == 0 || lTypeID == SETTConstant.TransactionType.WITHDRAWMARGIN
				|| lTypeID == 303 || lTypeID == 101)
		{
			if (lDepositTypeID == 1)
			{
				//定期显示单据号、余额、到期日
				strDisplayNames = new String[] { URLEncoder.encode("存款单据号"), URLEncoder.encode("余额"), URLEncoder.encode("到期日")};
				strDisplayFields = new String[] { "DepositNo", "Balance", "EndDate" };
			}else if(lDepositTypeID == 6){
//				保证金显示单据号、可用余额、开户日期
				strDisplayNames = new String[] { URLEncoder.encode("存款单据号"), URLEncoder.encode("可用余额"), URLEncoder.encode("开户日")};
				strDisplayFields = new String[] { "DepositNo", "Balance", "OpenDate" };
			}
			else
			{
				//定期显示单据号、余额、开户日期
				strDisplayNames = new String[] { URLEncoder.encode("存款单据号"), URLEncoder.encode("余额"), URLEncoder.encode("开户日")};
				strDisplayFields = new String[] { "DepositNo", "Balance", "OpenDate" };
			}
		}
		int nIndex = 0;
		String strSQL = "";
		if (strAccountIDCtrl != null && !strAccountIDCtrl.trim().equals(""))
		{
			strSQL =
				"getFixedDepositNoSQL("
					+ lOfficeID
					+ ","
					+ lCurrencyID
					+ ","
					+ lDepositTypeID
					+ ","
					+ strAccountIDCtrl
					+ ".value,"
					+ lUserID
					+ ","
					+ strCtrlName
					+ "Ctrl.value,"
					+ lTypeID
					+ ",'')";
		}
		else
		{
			strSQL =
				"getFixedDepositNoSQL("
					+ lOfficeID
					+ ","
					+ lCurrencyID
					+ ","
					+ lDepositTypeID
					+ ",-1,"
					+ lUserID
					+ ","
					+ strCtrlName
					+ "Ctrl.value,"
					+ lTypeID
					+ ",'')";
		}
		try
		{
			SETTMagnifier.showZoomCtrl(
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
			Log.print("定期存款单据号放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	
	/**
	 * 创建定期(通知)存款单据号放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lUserID 当前用户ID(初识值)
	 * @param lAccountID 主账户ID(初识值) 
	 * @param lSubAccountID 子账户ID(初识值)
	 * @param strDepositNo 定期(通知)存单号(初识值)
	 * @param lDepositTypeID 存单类型：1.定期；2.通知
	 * @param lTypeID 放大镜类型，取值如下：
	 * 	1、定期（通知）					-- sett_TransOpenFixedDeposit 已保存（未复核）
	 *  3、定期续期转存 					-- 子账户状态:未结清,不收不付冻结
	 * 	21、定期（通知）支取--业务处理时使用  -- 子账户状态:未结清
	 *  22、定期（通知）支取--业务复核时使用  -- 子账户状态:未结清
	 *  302,303、保后处理
	 *  default、显示所有的定期（通知）		-- 单据号 子账户状态:大于0
	 * @param strAccountIDCtrl（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnEndDateCtrl 返回值（到期日）对应的控件名
	 * @param strRtnOpenDateCtrl 返回值（开户日）对应的控件名
	 * @param strRtnCapitalCtrl 返回值（本金）对应的控件名
	 * @param strRtnBalanceCtrl 返回值（余额）对应的控件名
	 * @param strRtnRateCtrl 返回值（利率）对应的控件名
	 * @param strRtnIntervalCtrl 返回值（期限）对应的控件名
	 * @param strRtnStartDateCtrl 返回值（开始日期）对应的控件名
	 * @param strSystemDate 查询日期
	 * @param lStatusID 状态
	 */
	public static void createFixedDepositNewCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lUserID,
		long lAccountID,
		long lSubAccountID,
		String strDepositNo,
		long lDepositTypeID,
		long lTypeID,
		String strAccountIDCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnEndDateCtrl,
		String strRtnOpenDateCtrl,
		String strRtnCapitalCtrl,
		String strRtnBalanceCtrl,
		String strRtnRateCtrl,
		String strRtnIntervalCtrl,
		String strRtnStartDateCtrl,
		String strSystemDate,
		long   lStatusID)
	{
		String strMagnifierName = URLEncoder.encode("存款单据号");
		//long lTransType = -1;
		if (lDepositTypeID == 1)
		{
			strMagnifierName = URLEncoder.encode("定期存款单据号");
			//lTransType = SETTConstant.TransactionType.OPENFIXEDDEPOSIT;
		}
		else if (lDepositTypeID == 2)
		{
			strMagnifierName = URLEncoder.encode("通知存款单据号");
			//lTransType = SETTConstant.TransactionType.OPENNOTIFYACCOUNT;
		}
		else if(lDepositTypeID == 6)
		{
			strMagnifierName = URLEncoder.encode("保证金存款单据号");
		}
		
		String strMainProperty = " maxlength='30' value='" + strDepositNo + "'";
		String strPrefix = "";
		if (strRtnEndDateCtrl == null || strRtnEndDateCtrl.equals(""))
		{
			strRtnEndDateCtrl = "ItIsNotControl";
		}
		if (strRtnOpenDateCtrl == null || strRtnOpenDateCtrl.equals(""))
		{
			strRtnOpenDateCtrl = "ItIsNotControl";
		}
		if (strRtnCapitalCtrl == null || strRtnCapitalCtrl.equals(""))
		{
			strRtnCapitalCtrl = "ItIsNotControl";
		}
		if (strRtnBalanceCtrl == null || strRtnBalanceCtrl.equals(""))
		{
			strRtnBalanceCtrl = "ItIsNotControl";
		}
		if (strRtnRateCtrl == null || strRtnRateCtrl.equals(""))
		{
			strRtnRateCtrl = "ItIsNotControl";
		}
		if (strRtnIntervalCtrl == null || strRtnIntervalCtrl.equals(""))
		{
			strRtnIntervalCtrl = "ItIsNotControl";
		}
		if (strRtnStartDateCtrl == null || strRtnStartDateCtrl.equals(""))
		{
			strRtnStartDateCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnEndDateCtrl, strRtnOpenDateCtrl, strRtnCapitalCtrl, strRtnBalanceCtrl, strRtnRateCtrl, strRtnIntervalCtrl, strRtnStartDateCtrl };
		String[] strMainFields = { "DepositNo", "EndDate", "OpenDate", "Capital", "Balance", "Rate", "Interval", "StartDate" };
		String[] strReturnNames = { strCtrlName, strCtrlName + "AccountID" };
		String[] strReturnFields = { "SubAccountID", "AccountID" };
		String[] strReturnValues = { String.valueOf(lSubAccountID), String.valueOf(lAccountID)};
		String[] strDisplayNames = { URLEncoder.encode("存款单据号")};
		String[] strDisplayFields = { "DepositNo" };
		//支取或续期转存时，显示不同
		if (lTypeID == 0 || lTypeID == 22 || lTypeID == 302 || lTypeID == 303)
		{
			if (lDepositTypeID == 1)
			{
				//定期显示单据号、余额、到期日
				strDisplayNames = new String[] { URLEncoder.encode("存款单据号"), URLEncoder.encode("余额"),URLEncoder.encode("到期日")};
				strDisplayFields = new String[] { "DepositNo", "Balance","EndDate"};
			}
			else if(lDepositTypeID == 6)
			{
				//保证金显示单据号、可用余额、开户日期
				strDisplayNames = new String[] { URLEncoder.encode("存款单据号"), URLEncoder.encode("可用余额"), URLEncoder.encode("开户日")};
				strDisplayFields = new String[] { "DepositNo", "Balance", "OpenDate" };
			}
			else
			{
				//定期显示单据号、余额、开户日期
				strDisplayNames = new String[] { URLEncoder.encode("存款单据号"), URLEncoder.encode("余额"),URLEncoder.encode("开户日")};
				strDisplayFields = new String[] { "DepositNo", "Balance","OpenDate" };
			}
		}else if(lTypeID == 3 || lTypeID == 21)
		{
			if (lDepositTypeID == 1)
			{
				//定期显示单据号、余额、到期日
				strDisplayNames = new String[] { URLEncoder.encode("存款单据号"), URLEncoder.encode("余额"),URLEncoder.encode("开户金额"),URLEncoder.encode("到期日"),URLEncoder.encode("起息日")};
				strDisplayFields = new String[] { "DepositNo", "Balance","Capital" ,"EndDate","InterestDate" };
			}
			else if(lDepositTypeID == 6)
			{
				//保证金显示单据号、可用余额、开户日期
				strDisplayNames = new String[] { URLEncoder.encode("存款单据号"), URLEncoder.encode("可用余额"),URLEncoder.encode("开户金额"), URLEncoder.encode("开户日"),URLEncoder.encode("起息日")};
				strDisplayFields = new String[] { "DepositNo", "Balance","Capital","OpenDate","InterestDate"  };
			}
			else
			{
				//定期显示单据号、余额、开户日期
				strDisplayNames = new String[] { URLEncoder.encode("存款单据号"), URLEncoder.encode("余额"),URLEncoder.encode("开户金额"), URLEncoder.encode("开户日"),URLEncoder.encode("起息日")};
				strDisplayFields = new String[] { "DepositNo", "Balance","Capital", "OpenDate","InterestDate" };
			}			
		}
		int nIndex = 0;
		String strSQL = "";
		if (strAccountIDCtrl != null && !strAccountIDCtrl.trim().equals(""))
		{
			strSQL =
				"getFixedDepositNewSQL("
					+ lOfficeID
					+ ","
					+ lCurrencyID
					+ ","
					+ lDepositTypeID
					+ ","
					+ strAccountIDCtrl
					+ ".value,"
					+ lUserID
					+ ","
					+ strCtrlName
					+ "Ctrl.value,"
					+ lTypeID
					+ ",'"
					+ strSystemDate
					+ "',"
					+ lStatusID
					+ ")";
		}
		else
		{
			strSQL =
				"getFixedDepositNewSQL("
					+ lOfficeID
					+ ","
					+ lCurrencyID
					+ ","
					+ lDepositTypeID
					+ ",-1,"
					+ lUserID
					+ ","
					+ strCtrlName
					+ "Ctrl.value,"
					+ lTypeID
					+ ",'"
					+ strSystemDate
					+ "',"
					+ lStatusID
					+ ")";
		}
		try
		{
			SETTMagnifier.showZoomCtrl(
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
			Log.print("定期存款单据号放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	
	/**
	 * 资金结算-账户类型编码设置专用放大镜
	 * 本放大镜需要按照客户编号查询存单号
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lClientID 客户ID(初始值)
	 * @param lAccountID 主账户ID(初始值)
	 * @param lSubAccountID 子账户(初始值)
	 * @param strDepositNo 定期(通知)存单号(初始值)
	 * @param lDepositTypeID 存单类型：1为定期；2为通知
	 * @param strClientIDCtrl 查询时关联客户ID控件名称
	 * @param strAccountIDCtrl 查询时关联账户ID控件名称
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @author kaishao
	 * 2008-11-12增加
	 */
	public static void createFixedDepositNoCtrl(
			JspWriter out,
			long lOfficeID,
			long lCurrencyID,
			String strFormName,
			String strCtrlName,
			String strTitle,
			long lClientID,
			long lAccountID,
			long lSubAccountID,
			String strDepositNo,
			long lDepositTypeID,
			String strClientIDCtrl,
			String strAccountIDCtrl,
			String[] strNextControls)
	{
		String strMagnifierName = URLEncoder.encode("存款单据号");
		if (lDepositTypeID == 1)
		{
			strMagnifierName = URLEncoder.encode("定期存款单据号");
		}
		else if (lDepositTypeID == 2)
		{
			strMagnifierName = URLEncoder.encode("通知存款单据号");
		}
		
		String strMainProperty = " maxlength='30' value='" + strDepositNo + "'";
		String strPrefix = "";
		String[] strMainNames ={strCtrlName + "Ctrl"};
		String[] strMainFields = {"DepositNo"};
		String[] strReturnNames = {strCtrlName, strCtrlName + "AccountID" };
		String[] strReturnFields = { "SubAccountID", "AccountID" };
		String[] strReturnValues = { String.valueOf(lSubAccountID), String.valueOf(lAccountID)};
		String[] strDisplayNames = { URLEncoder.encode("存款单据号")};
		String[] strDisplayFields = { "DepositNo" };
		String strFirstTD="";
		String strSecondTD="";
		
		strDisplayNames = new String[] { URLEncoder.encode("存款单据号"), URLEncoder.encode("开立日")};
		strDisplayFields = new String[] { "DepositNo", "OpenDate"};
		int nIndex = 0;
		String strSQL = "";
		if(strClientIDCtrl==null || strClientIDCtrl.trim().equals(""))
		{
			strClientIDCtrl="-1";
		}else{
			strClientIDCtrl=strClientIDCtrl+".value";
		}
		if(strAccountIDCtrl==null || strAccountIDCtrl.trim().equals(""))
		{
			strAccountIDCtrl="-1";
		}else{
			strAccountIDCtrl=strAccountIDCtrl+".value";
		}
		strSQL="getFixedDepositNoSQLSpecial("
				+ lOfficeID
				+ ","
				+ lCurrencyID
				+ ","
				+ lDepositTypeID
				+ ","
				+ strAccountIDCtrl
				+ ","
				+ strClientIDCtrl
				+ ","
				+ strCtrlName
				+ "Ctrl.value,'"
				+ Env.getSystemDateString(lOfficeID, lCurrencyID)
				+ "')";
		try
		{
			SETTMagnifier.showZoomCtrl(
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
			Log.print("定期存款单据号放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	
	/*
	 * 中交，结算，通知指令提交
	 * 通知存款单据号放大镜
	 */
	public static void createFixedDepositNoCtrlForZJ(
			JspWriter out,
			long lOfficeID,
			long lCurrencyID,
			String strFormName,
			String strCtrlName,
			String strTitle,
			long lUserID,
			long lAccountID,
			long lSubAccountID,
			String strDepositNo,
			long lDepositTypeID,
			long lTypeID,
			String strAccountIDCtrl,
			String strFirstTD,
			String strSecondTD,
			String[] strNextControls,
			String strRtnEndDateCtrl,
			String strRtnOpenDateCtrl,
			String strRtnCapitalCtrl,
			String strRtnBalanceCtrl,
			String strRtnRateCtrl,
			String strRtnIntervalCtrl,
			String strRtnStartDateCtrl)
		{
			String strMagnifierName = URLEncoder.encode("存款单据号");
			long lTransType = -1;
			if (lDepositTypeID == 1)
			{
				strMagnifierName = URLEncoder.encode("定期存款单据号");
				lTransType = SETTConstant.TransactionType.OPENFIXEDDEPOSIT;
			}
			else if (lDepositTypeID == 2)
			{
				strMagnifierName = URLEncoder.encode("通知存款单据号");
				lTransType = SETTConstant.TransactionType.OPENNOTIFYACCOUNT;
			}
			else if(lDepositTypeID == 6)
			{
				strMagnifierName = URLEncoder.encode("保证金存款单据号");
			}
			
			String strMainProperty = " maxlength='30' value='" + strDepositNo + "'";
			String strPrefix = "";
			if (strRtnEndDateCtrl == null || strRtnEndDateCtrl.equals(""))
			{
				strRtnEndDateCtrl = "ItIsNotControl";
			}
			if (strRtnOpenDateCtrl == null || strRtnOpenDateCtrl.equals(""))
			{
				strRtnOpenDateCtrl = "ItIsNotControl";
			}
			if (strRtnCapitalCtrl == null || strRtnCapitalCtrl.equals(""))
			{
				strRtnCapitalCtrl = "ItIsNotControl";
			}
			if (strRtnBalanceCtrl == null || strRtnBalanceCtrl.equals(""))
			{
				strRtnBalanceCtrl = "ItIsNotControl";
			}
			if (strRtnRateCtrl == null || strRtnRateCtrl.equals(""))
			{
				strRtnRateCtrl = "ItIsNotControl";
			}
			if (strRtnIntervalCtrl == null || strRtnIntervalCtrl.equals(""))
			{
				strRtnIntervalCtrl = "ItIsNotControl";
			}
			if (strRtnStartDateCtrl == null || strRtnStartDateCtrl.equals(""))
			{
				strRtnStartDateCtrl = "ItIsNotControl";
			}
			String[] strMainNames =
				{ strCtrlName + "Ctrl", strRtnEndDateCtrl, strRtnOpenDateCtrl, strRtnCapitalCtrl, strRtnBalanceCtrl, strRtnRateCtrl, strRtnIntervalCtrl, strRtnStartDateCtrl };
			String[] strMainFields = { "DepositNo", "EndDate", "OpenDate", "Capital", "Balance", "Rate", "Interval", "StartDate" };
			String[] strReturnNames = { strCtrlName, strCtrlName + "AccountID" };
			String[] strReturnFields = { "SubAccountID", "AccountID" };
			String[] strReturnValues = { String.valueOf(lSubAccountID), String.valueOf(lAccountID)};
			String[] strDisplayNames = { URLEncoder.encode("存款单据号")};
			String[] strDisplayFields = { "DepositNo" };
			//支取或续期转存时，显示不同
			if (lTypeID == 22 || lTypeID == 3 || lTypeID == 0)
			{
				if (lDepositTypeID == 1)
				{
					//定期显示单据号、余额、到期日
					strDisplayNames = new String[] { URLEncoder.encode("存款单据号"), URLEncoder.encode("余额"), URLEncoder.encode("到期日")};
					strDisplayFields = new String[] { "DepositNo", "Balance", "EndDate" };
				}else if(lDepositTypeID == 6){
//					保证金显示单据号、可用余额、开户日期
					strDisplayNames = new String[] { URLEncoder.encode("存款单据号"), URLEncoder.encode("可用余额"), URLEncoder.encode("开户日")};
					strDisplayFields = new String[] { "DepositNo", "Balance", "OpenDate" };
				}
				else
				{
					//定期显示单据号、余额、开户日期
					strDisplayNames = new String[] { URLEncoder.encode("存款单据号"), URLEncoder.encode("余额"), URLEncoder.encode("开户日")};
					strDisplayFields = new String[] { "DepositNo", "Balance", "OpenDate" };
				}
			}else if(lTypeID == 21)
			{
				if (lDepositTypeID == 1)
				{
					//定期显示单据号、余额、到期日
					strDisplayNames = new String[] { URLEncoder.encode("存款单据号"), URLEncoder.encode("余额"),URLEncoder.encode("开户金额"),URLEncoder.encode("到期日"),URLEncoder.encode("起息日")};
					strDisplayFields = new String[] { "DepositNo", "Balance","Capital" ,"EndDate","InterestDate" };
				}
				else if(lDepositTypeID == 6)
				{
					//保证金显示单据号、可用余额、开户日期
					strDisplayNames = new String[] { URLEncoder.encode("存款单据号"), URLEncoder.encode("可用余额"),URLEncoder.encode("开户金额"), URLEncoder.encode("开户日"),URLEncoder.encode("起息日")};
					strDisplayFields = new String[] { "DepositNo", "Balance","Capital","OpenDate","InterestDate"  };
				}
				else
				{
					//定期显示单据号、余额、开户日期
					strDisplayNames = new String[] { URLEncoder.encode("存款单据号"), URLEncoder.encode("余额"),URLEncoder.encode("开户金额"), URLEncoder.encode("开户日"),URLEncoder.encode("起息日")};
					strDisplayFields = new String[] { "DepositNo", "Balance","Capital", "OpenDate","InterestDate" };
				}				
			}
			int nIndex = 0;
			String strSQL = "";
			if (strAccountIDCtrl != null && !strAccountIDCtrl.trim().equals(""))
			{
				strSQL =
					"getFixedDepositNoSQLForZJ("
						+ lOfficeID
						+ ","
						+ lCurrencyID
						+ ","
						+ lDepositTypeID
						+ ","
						+ strAccountIDCtrl
						+ ".value,"
						+ lUserID
						+ ","
						+ strCtrlName
						+ "Ctrl.value,"
						+ lTypeID
						+ ",'"
						+ Env.getSystemDateString(lOfficeID, lCurrencyID)
						+ "',"
						+ lTransType
						+ ")";
			}
			else
			{
				strSQL =
					"getFixedDepositNoSQLForZJ("
						+ lOfficeID
						+ ","
						+ lCurrencyID
						+ ","
						+ lDepositTypeID
						+ ",-1,"
						+ lUserID
						+ ","
						+ strCtrlName
						+ "Ctrl.value,"
						+ lTypeID
						+ ",'"
						+ Env.getSystemDateString(lOfficeID, lCurrencyID)
						+ "',"
						+ lTransType
						+ ")";
			}
			try
			{
				SETTMagnifier.showZoomCtrl(
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
				Log.print("定期存款单据号放大镜[" + strCtrlName + "]异常：" + e.toString());
			}
		}
	/**
	 * 创建定期(通知)存款单据号放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lUserID 当前用户ID(初识值)
	 * @param lAccountID 主账户ID(初识值) 
	 * @param lSubAccountID 子账户ID(初识值)
	 * @param strDepositNo 定期(通知)存单号(初识值)
	 * @param lDepositTypeID 存单类型：1，定期；2，通知。
	 * @param lTypeID 放大镜类型，取值如下：
	 * 	1、定期（通知）开立--复核匹配时使用
	 * 	21、定期（通知）支取--业务处理时使用
	 *  22、定期（通知）支取--业务复核时使用
	 *  3、定期续期转存--业务处理时使用（仅显示已到期的存单）
	 * 
	 * @param strAccountIDCtrl（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnEndDateCtrl 返回值（到期日）对应的控件名
	 * @param strRtnOpenDateCtrl 返回值（开户日）对应的控件名
	 * @param strRtnCapitalCtrl 返回值（本金）对应的控件名
	 * @param strRtnBalanceCtrl 返回值（余额）对应的控件名
	 */
	public static void createFixedDepositNoCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lUserID,
		long lAccountID,
		long lSubAccountID,
		String strDepositNo,
		long lDepositTypeID,
		long lTypeID,
		String strAccountIDCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnEndDateCtrl,
		String strRtnOpenDateCtrl,
		String strRtnCapitalCtrl,
		String strRtnBalanceCtrl)
	{
		createFixedDepositNoCtrl(
			out,
			lOfficeID,
			lCurrencyID,
			strFormName,
			strCtrlName,
			strTitle,
			lUserID,
			lAccountID,
			lSubAccountID,
			strDepositNo,
			lDepositTypeID,
			lTypeID,
			strAccountIDCtrl,
			strFirstTD,
			strSecondTD,
			strNextControls,
			strRtnEndDateCtrl,
			strRtnOpenDateCtrl,
			strRtnCapitalCtrl,
			strRtnBalanceCtrl,
			"",
			"",
			"");
	}
	/**
	 * 创建开户行放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lBranchID 开户行ID(初识值)
	 * @param strBranchName 开户行名称(初识值)
	 * @param lIsSingleBank 是否单边账银行（1，是；其它，不是）
	 * @param strAccountCtrl 账户ID对应控件的名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnBankAccountNoCtrl 返回值（银行账户编号）对应的控件名
	 */
	public static void createBranchCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lBranchID,
		String strBranchName,
		long lIsSingleBank,
		String strAccountCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnBankAccountNoCtrl)
	{
		createBranchCtrl(
			out,
			lOfficeID,
			lCurrencyID,
			strFormName,
			strCtrlName,
			strTitle,
			lBranchID,
			strBranchName,
			lIsSingleBank,
			strAccountCtrl,
			strFirstTD,
			strSecondTD,
			strNextControls,
			strRtnBankAccountNoCtrl,
			"");
	}
	/**
	 * 创建开户行放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lBranchID 开户行ID(初识值)
	 * @param strBranchName 开户行名称(初识值)
	 * @param lIsSingleBank 是否单边账银行（1，是；其它，不是）
	 * @param strAccountCtrl 账户ID对应控件的名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnBankAccountNoCtrl 返回值（银行账户编号）对应的控件名
	 * @param strRtnBankNoCtrl 返回值（开户行编号）对应的控件名
	 */
	public static void createBranchCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lBranchID,
		String strBranchName,
		long lIsSingleBank,
		String strAccountCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnBankAccountNoCtrl,
		String strRtnBankNoCtrl)
	{
		String strMagnifierName = URLEncoder.encode("开户行");
		String strMainProperty = strBranchName;
		String strPrefix = "";
		if (strRtnBankAccountNoCtrl == null || strRtnBankAccountNoCtrl.equals(""))
		{
			strRtnBankAccountNoCtrl = "ItIsNotControl";
		}
		if (strRtnBankNoCtrl == null || strRtnBankNoCtrl.equals(""))
		{
			strRtnBankNoCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnBankAccountNoCtrl, strRtnBankNoCtrl };
		String[] strMainFields = { "BranchName", "BranchAccountNo", "BranchNo" };
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "BranchID" };
		String[] strReturnValues = { String.valueOf(lBranchID)};
		String[] strDisplayNames = { URLEncoder.encode("开户行编号"), URLEncoder.encode("开户行名称"), URLEncoder.encode("开户行账号")};			
		String[] strDisplayFields = { "BranchNo", "BranchName", "BranchAccountNo" };				
		int nIndex = 0;
		String strSQL = "";
		if (strAccountCtrl != null && !strAccountCtrl.trim().equals(""))
		{
			strSQL = "getBranchSQL_old(" + lOfficeID + "," + lCurrencyID + "," + lIsSingleBank + "," + strAccountCtrl + ".value," + strCtrlName + "Ctrl.value)";
		}
		else
		{
			strSQL = "getBranchSQL_old(" + lOfficeID + "," + lCurrencyID + "," + lIsSingleBank + ",-1," + strCtrlName + "Ctrl.value)";
		}
		try
		{
					
			SETTMagnifier.showZoomCtrl(
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
				false,
				"branch");
		}
		catch (Exception e)
		{
			Log.print("开户行放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	
	/**
	 * 创建开户行放大镜(华联专用，在显示时，显示银行编号、银行名称、对应的银行账户)
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lBranchID 开户行ID(初识值)
	 * @param strBranchName 开户行名称(初识值)
	 * @param lIsSingleBank 是否单边账银行（1，是；其它，不是）
	 * @param strAccountCtrl 账户ID对应控件的名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnBankAccountNoCtrl 返回值（银行账户编号）对应的控件名
	 * @param strRtnBankNoCtrl 返回值（开户行编号）对应的控件名
	 */
	public static void createBranchCtrlForHL(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lBranchID,
		String strBranchName,
		long lIsSingleBank,
		String strAccountCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnBankAccountNoCtrl,
		String strRtnBankNoCtrl)
	{
		String strMagnifierName = URLEncoder.encode("开户行");
		String strMainProperty = strBranchName;
		String strPrefix = "";
		if (strRtnBankAccountNoCtrl == null || strRtnBankAccountNoCtrl.equals(""))
		{
			strRtnBankAccountNoCtrl = "ItIsNotControl";
		}
		if (strRtnBankNoCtrl == null || strRtnBankNoCtrl.equals(""))
		{
			strRtnBankNoCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnBankAccountNoCtrl, strRtnBankNoCtrl };
		String[] strMainFields = { "BranchName", "BranchAccountNo", "BranchNo" };
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "BranchID" };
		String[] strReturnValues = { String.valueOf(lBranchID)};
		String[] strDisplayNames = { URLEncoder.encode("开户行编号"), URLEncoder.encode("开户行名称"),URLEncoder.encode("银行账户号")};			
		String[] strDisplayFields = { "BranchNo", "BranchName","BranchAccountNo"};				
		int nIndex = 0;
		String strSQL = "";
		if (strAccountCtrl != null && !strAccountCtrl.trim().equals(""))
		{
			strSQL = "getBranchSQL(" + lOfficeID + "," + lCurrencyID + "," + lIsSingleBank + "," + strAccountCtrl + ".value," + strCtrlName + "Ctrl.value)";
		}
		else
		{
			strSQL = "getBranchSQL(" + lOfficeID + "," + lCurrencyID + "," + lIsSingleBank + ",-1," + strCtrlName + "Ctrl.value)";
		}
		try
		{
					
			SETTMagnifier.showZoomCtrl(
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
				false,
				"branch");
		}
		catch (Exception e)
		{
			Log.print("开户行放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	
	/**
	 * 创建开户行放大镜（中交），付款方处理
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lBranchID 开户行ID(初识值)
	 * @param strBranchName 开户行名称(初识值)
	 * @param lIsSingleBank 是否单边账银行（1，是；其它，不是）
	 * @param strAccountCtrl 账户ID对应控件的名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnBankAccountNoCtrl 返回值（银行账户编号）对应的控件名
	 */
	public static void createBranchCtrlForZj(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lBranchID,
		String strBranchName,
		long lIsSingleBank,
		String strAccountCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnBankAccountNoCtrl)
	{
		createBranchCtrlForZj(
			out,
			lOfficeID,
			lCurrencyID,
			strFormName,
			strCtrlName,
			strTitle,
			lBranchID,
			strBranchName,
			lIsSingleBank,
			strAccountCtrl,
			strFirstTD,
			strSecondTD,
			strNextControls,
			strRtnBankAccountNoCtrl,
			"");
	}
	public static void createBranchCtrlForZj2(
			JspWriter out,
			long lOfficeID,
			long lCurrencyID,
			String strFormName,
			String strCtrlName,
			String strTitle,
			long lBranchID,
			String strBranchName,
			long lIsSingleBank,
			String strAccountCtrl,
			String strFirstTD,
			String strSecondTD,
			String[] strNextControls,
			String strRtnBankAccountNoCtrl)
		{
			createBranchCtrlForZj2(
				out,
				lOfficeID,
				lCurrencyID,
				strFormName,
				strCtrlName,
				strTitle,
				lBranchID,
				strBranchName,
				lIsSingleBank,
				strAccountCtrl,
				strFirstTD,
				strSecondTD,
				strNextControls,
				strRtnBankAccountNoCtrl,
				"");
		}
		
	/**
	 * @author 马现福
	 * @param out
	 * @param strFormName
	 * @param strCtrlName
	 * @param lAccountID
	 * @param strAccountNO
	 * @param strRtnAccountNameCtrlName
	 * @param strNextControls
	 * @param sConditions
	 * @param officeID
	 */
	public static void createAccountCtrlReturnCode(JspWriter out,
			String strFormName, String strCtrlName, long lAccountID,
			String strAccountNO, String strRtnAccountNameCtrlName,
			String[] strNextControls, String[] sConditions, long officeID) {
		String strMagnifierName = URLEncoder.encode("账户");
		String strMainProperty = " size='20' maxlength='30' value='"+ strAccountNO + "'";
		String strPrefix = "";
		String strTitle="账户编号";
		if (strRtnAccountNameCtrlName == null || strRtnAccountNameCtrlName.equals("")) 
		{
			strRtnAccountNameCtrlName = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl",strRtnAccountNameCtrlName };
		String[] strMainFields = { "AccountNO", "AccountName" };
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "AccountID" };
		String[] strReturnValues = { String.valueOf(lAccountID) };

		String[] strDisplayNames = { URLEncoder.encode("账号"),URLEncoder.encode("账户名称"),URLEncoder.encode("银行名称") };
		String[] strDisplayFields = { "AccountNO", "AccountName","BankName" };
		int nIndex = 0;
		String strSQL = null;
		//根据页面传入的查询条件，生成对应的sql,当前要求传入条件必需符合下面定义的顺序（条件录入在对应的查询jsp页面指定）
		//录入条件的顺序依次为：sAccountNO,officeID,clientId,countryId,bankId,currencyType,ownerType,inputOrOutput,
		//isDirectLink,accountPropertyOne,accountPropertyTwo,accountPropertyThree,isCheck,accountStatus,dirBankRefcode
		//若以后需添加查询过滤条件，只需在页面录入查询条件，在对应的JS函数中追究查询条件即可
		//注：入口参数sAccountNO,officeID是程序生成的，不需jsp页面
		StringBuffer bufferSQL=new StringBuffer();
		bufferSQL.append("getAccountSQL(" + strCtrlName + "Ctrl.value,");
		//追加办事处查询信息
//		if(Env.isHQ(officeID))//总部，可以查询各办事处信息
//		{
//			bufferSQL.append("-1");
//		}
//		else
//		{
			bufferSQL.append(officeID);
//		}
		if(sConditions==null || sConditions.length<=0)
		{
			strSQL=bufferSQL.toString()+")";
		}
		else
		{
			bufferSQL.append(",");
			int length=sConditions.length-1;
			for(int i=0;i<sConditions.length;i++)
			{
				if(sConditions[i]==null)
				{
					bufferSQL.append("-1");
				}
				else
				{
					bufferSQL.append(sConditions[i]+".value");
				}
				if(i<length)
				{					
					bufferSQL.append(",");
				}					
			}
			bufferSQL.append(")");					
			strSQL=bufferSQL.toString();
		}
		System.out.println("***strSQL="+strSQL);
		try {
			SETTMagnifier.showZoomCtrl(
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
					"",
					"");
			/*OBMagnifier.showZoomCtrl(out, strMagnifierName, strFormName,
					strPrefix, strMainNames, strMainFields, strReturnNames,
					strReturnFields, strReturnValues, strDisplayNames,
					strDisplayFields, nIndex, strMainProperty, strSQL,
					strNextControls,strTitle,null,null,false,false);*/
		} catch (Exception e) {
			Log.print("账户放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	
	/**
	 * @author 马现福
	 * @param out
	 * @param strFormName
	 * @param strCtrlName
	 * @param lAccountID
	 * @param strAccountNO
	 * @param strRtnAccountNameCtrlName
	 * @param strNextControls
	 * @param sConditions
	 * @param officeID
	 */
	public static void createAccountCtrlReturnCodeForBP(JspWriter out,
			String strFormName, String strCtrlName, long lAccountID,
			String strAccountNO, String strRtnAccountNameCtrlName,
			String[] strNextControls, String[] sConditions, long officeID) {
		String strMagnifierName = URLEncoder.encode("账户");
		String strMainProperty = " size='20' maxlength='30' value='"+ strAccountNO + "'";
		String strPrefix = "";
		String strTitle="账户编号";
		if (strRtnAccountNameCtrlName == null || strRtnAccountNameCtrlName.equals("")) 
		{
			strRtnAccountNameCtrlName = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl",strRtnAccountNameCtrlName };
		String[] strMainFields = { "AccountNO", "AccountName" };
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "AccountID" };
		String[] strReturnValues = { String.valueOf(lAccountID) };

		String[] strDisplayNames = { URLEncoder.encode("账号"),URLEncoder.encode("账户名称"),URLEncoder.encode("银行名称") };
		String[] strDisplayFields = { "AccountNO", "AccountName","BankName" };
		int nIndex = 0;
		String strSQL = null;
		//根据页面传入的查询条件，生成对应的sql,当前要求传入条件必需符合下面定义的顺序（条件录入在对应的查询jsp页面指定）
		//录入条件的顺序依次为：sAccountNO,officeID,clientId,countryId,bankId,currencyType,ownerType,inputOrOutput,
		//isDirectLink,accountPropertyOne,accountPropertyTwo,accountPropertyThree,isCheck,accountStatus,dirBankRefcode
		//若以后需添加查询过滤条件，只需在页面录入查询条件，在对应的JS函数中追究查询条件即可
		//注：入口参数sAccountNO,officeID是程序生成的，不需jsp页面
		StringBuffer bufferSQL=new StringBuffer();
		bufferSQL.append("getAccountSQLForBP(" + strCtrlName + "Ctrl.value,");
		//追加办事处查询信息
//		if(Env.isHQ(officeID))//总部，可以查询各办事处信息
//		{
//			bufferSQL.append("-1");
//		}
//		else
//		{
			bufferSQL.append(officeID);
//		}
		if(sConditions==null || sConditions.length<=0)
		{
			strSQL=bufferSQL.toString()+")";
		}
		else
		{
			bufferSQL.append(",");
			int length=sConditions.length-1;
			for(int i=0;i<sConditions.length;i++)
			{
				if(sConditions[i]==null)
				{
					bufferSQL.append("-1");
				}
				else
				{
					bufferSQL.append(sConditions[i]+".value");
				}
				if(i<length)
				{					
					bufferSQL.append(",");
				}					
			}
			bufferSQL.append(")");					
			strSQL=bufferSQL.toString();
		}
		System.out.println("***strSQL="+strSQL);
		try {
			SETTMagnifier.showZoomCtrl(
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
					"",
					"");
			
			/*OBMagnifier.showZoomCtrl(out, strMagnifierName, strFormName,
					strPrefix, strMainNames, strMainFields, strReturnNames,
					strReturnFields, strReturnValues, strDisplayNames,
					strDisplayFields, nIndex, strMainProperty, strSQL,
					strNextControls,strTitle,null,null,false,false);*/
		} catch (Exception e) {
			Log.print("账户放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	
	/**
	 * 创建开户行放大镜（中交）付款方处理
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lBranchID 开户行ID(初识值)
	 * @param strBranchName 开户行名称(初识值)
	 * @param lIsSingleBank 是否单边账银行（1，是；其它，不是）
	 * @param strAccountCtrl 账户ID对应控件的名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnBankAccountNoCtrl 返回值（银行账户编号）对应的控件名
	 * @param strRtnBankNoCtrl 返回值（开户行编号）对应的控件名
	 */
	public static void createBranchCtrlForZj(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lBranchID,
		String strBranchName,
		long lIsSingleBank,
		String strAccountCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnBankAccountNoCtrl,
		String strRtnBankNoCtrl)
	{
		String strMagnifierName = URLEncoder.encode("开户行");
		String strMainProperty = strBranchName;
		String strPrefix = "";
		if (strRtnBankAccountNoCtrl == null || strRtnBankAccountNoCtrl.equals(""))
		{
			strRtnBankAccountNoCtrl = "ItIsNotControl";
		}
		if (strRtnBankNoCtrl == null || strRtnBankNoCtrl.equals(""))
		{
			strRtnBankNoCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnBankAccountNoCtrl, strRtnBankNoCtrl };
		String[] strMainFields = { "BranchName", "BranchAccountNo", "BranchNo" };
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "BranchID" };
		String[] strReturnValues = { String.valueOf(lBranchID)};
	//	String[] strDisplayNames = { URLEncoder.encode("开户行编号"), URLEncoder.encode("开户行名称")};//原显示列名称
		/*现显示列名称*/
		String[] strDisplayNames = { URLEncoder.encode("开户行编号"), URLEncoder.encode("开户行名称"),
				URLEncoder.encode("银行账号"), URLEncoder.encode("银行账户名称"), URLEncoder.encode("银行账户余额"),};
		
	//	String[] strDisplayFields = { "BranchNo", "BranchName" };//原显示列字段
		String[] strDisplayFields = { "BranchNo", "BranchName", "acctcode", "acctname", "mBalance" };//现显示字段
		
		int nIndex = 0;
		String strSQL = "";
		if (strAccountCtrl != null && !strAccountCtrl.trim().equals(""))
		{
			strSQL = "getBranchSQLForZj_old(" + lOfficeID + "," + lCurrencyID + "," + lIsSingleBank + "," + strAccountCtrl + ".value," + strCtrlName + "Ctrl.value)";
		}
		else
		{
			strSQL = "getBranchSQLForZj_old(" + lOfficeID + "," + lCurrencyID + "," + lIsSingleBank + ",-1," + strCtrlName + "Ctrl.value)";
		}
		try
		{					
			
			SETTMagnifier.showZoomCtrl(
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
				false,
				"branch");
		}
		catch (Exception e)
		{
			Log.print("开户行放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	public static void createBranchCtrlForZj2(
			JspWriter out,
			long lOfficeID,
			long lCurrencyID,
			String strFormName,
			String strCtrlName,
			String strTitle,
			long lBranchID,
			String strBranchName,
			long lIsSingleBank,
			String strAccountCtrl,
			String strFirstTD,
			String strSecondTD,
			String[] strNextControls,
			String strRtnBankAccountNoCtrl,
			String strRtnBankNoCtrl)
		{
			String strMagnifierName = URLEncoder.encode("开户行");
			String strMainProperty = strBranchName;
			String strPrefix = "";
			if (strRtnBankAccountNoCtrl == null || strRtnBankAccountNoCtrl.equals(""))
			{
				strRtnBankAccountNoCtrl = "ItIsNotControl";
			}
			if (strRtnBankNoCtrl == null || strRtnBankNoCtrl.equals(""))
			{
				strRtnBankNoCtrl = "ItIsNotControl";
			}
			String[] strMainNames = { strCtrlName + "Ctrl", strRtnBankAccountNoCtrl, strRtnBankNoCtrl };
			String[] strMainFields = { "BranchName", "BranchAccountNo", "BranchNo" };
			String[] strReturnNames = { strCtrlName };
			String[] strReturnFields = { "BranchID" };
			String[] strReturnValues = { String.valueOf(lBranchID)};
		//	String[] strDisplayNames = { URLEncoder.encode("开户行编号"), URLEncoder.encode("开户行名称")};//原显示列名称
			/*现显示列名称*/
			String[] strDisplayNames = { URLEncoder.encode("开户行编号"), URLEncoder.encode("开户行名称"),
					URLEncoder.encode("银行账号"), URLEncoder.encode("银行账户名称"), URLEncoder.encode("银行账户余额"),};
			
		//	String[] strDisplayFields = { "BranchNo", "BranchName" };//原显示列字段
			String[] strDisplayFields = { "BranchNo", "BranchName", "acctcode", "acctname", "mBalance" };//现显示字段
			
			int nIndex = 0;
			String strSQL = "";
			if (strAccountCtrl != null && !strAccountCtrl.trim().equals(""))
			{
				strSQL = "getBranchSQLForZj2_old(" + lOfficeID + "," + lCurrencyID + "," + lIsSingleBank + "," + strAccountCtrl + ".value," + strCtrlName + "Ctrl.value)";
			}
			else
			{
				strSQL = "getBranchSQLForZj2_old(" + lOfficeID + "," + lCurrencyID + "," + lIsSingleBank + ",-1," + strCtrlName + "Ctrl.value)";
			}
			try
			{					
				
				SETTMagnifier.showZoomCtrl(
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
					false,
					"branch");
			}
			catch (Exception e)
			{
				Log.print("开户行放大镜[" + strCtrlName + "]异常：" + e.toString());
			}
		}
	/**
	 * 创建票据放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lBankID 发票银行ID(初识值)
	 * @param strBankBillNo 银行票据号(初识值)
	 * @param lStatusID 银行票据状态(
	 * 			查询条件：特殊的，如果是-1，显示四个状态（注册，申领，挂失，使用）)
	 * @param strBankCtrl 发票银行ID对应控件的名称（查询时关联）
	 * @param strBillTypeCtrl 票据类型对应控件的名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnBankBillIDCtrl 返回值（银行票据ID）对应的控件名
	 */
	public static void createBankBillCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lBankID,
		String strBankBillNo,
		long lStatusID,
		String strBankCtrl,
		String strBillTypeCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnBankBillIDCtrl)
	{
		createBankBillCtrl(
			out,
			lOfficeID,
			lCurrencyID,
			strFormName,
			strCtrlName,
			strTitle,
			lBankID,
			strBankBillNo,
			lStatusID,
			strBankCtrl,
			strBillTypeCtrl,
			"",
			strFirstTD,
			strSecondTD,
			strNextControls,
			strRtnBankBillIDCtrl);
	}
	/**
	 * 创建票据放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lBankID 发票银行ID(初识值)
	 * @param strBankBillNo 银行票据号(初识值)
	 * @param lStatusID 银行票据状态(
	 * 			查询条件：特殊的，如果是-1，显示四个状态（注册，申领，挂失，使用）)
	 * @param strBankCtrl 发票银行ID对应控件的名称（查询时关联）
	 * @param strBillTypeCtrl 票据类型对应控件的名称（查询时关联）
	 * @param strRequireClientIDCtrl 申领客户ID对应控件的名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnBankBillIDCtrl 返回值（银行票据ID）对应的控件名
	 */
	public static void createBankBillCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lBankID,
		String strBankBillNo,
		long lStatusID,
		String strBankCtrl,
		String strBillTypeCtrl,
		String strRequireClientIDCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnBankBillIDCtrl)
	{
		String strMagnifierName = URLEncoder.encode("银行票据");
		String strMainProperty = " maxlength='30' value='" + strBankBillNo + "'";
		String strPrefix = "";
		if (strRtnBankBillIDCtrl == null || strRtnBankBillIDCtrl.equals(""))
		{
			strRtnBankBillIDCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnBankBillIDCtrl };
		String[] strMainFields = { "BillNo", "BillID" };
		String[] strReturnNames = { strCtrlName, strCtrlName + "RequireClientID" };
		String[] strReturnFields = { "BankID", "RequireClientID" };
		String[] strReturnValues = { String.valueOf(lBankID), "-1" };
		String[] strDisplayNames = { URLEncoder.encode("发票银行"), URLEncoder.encode("票据类型"), URLEncoder.encode("票据编号")};
		String[] strDisplayFields = { "BankName", "BillTypeDesc", "BillNo" };
		int nIndex = 2;
		String strSQL = "";
		String strTempBankCtrlValue = "";
		if (strBankCtrl != null && !strBankCtrl.trim().equals(""))
		{
			strTempBankCtrlValue = strBankCtrl + ".value";
		}
		else
		{
			strTempBankCtrlValue = "-1";
		}
		String strTempRequireClientIDCtrlValue = "";
		if (strRequireClientIDCtrl != null && !strRequireClientIDCtrl.trim().equals(""))
		{
			strTempRequireClientIDCtrlValue = strRequireClientIDCtrl + ".value";
		}
		else
		{
			strTempRequireClientIDCtrlValue = "-1";
		}
		strSQL =
			"getBankBillSQL(" + strTempBankCtrlValue + "," + strBillTypeCtrl + ".value," + strCtrlName + "Ctrl.value," + lStatusID + "," + strTempRequireClientIDCtrlValue + ")";
		try
		{
			SETTMagnifier.showZoomCtrl(
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
			Log.print("银行票据放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	/**
	 * 创建摘要放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lAbstractID 摘要ID(初识值)
	 * @param strAbstractDesc 摘要描述(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 */
	public static void createAbstractCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lAbstractID,
		String strAbstractDesc,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls)
	{
		String strMagnifierName = URLEncoder.encode("摘要");
		String strMainProperty = " size='40' maxlength = '50' value='" + strAbstractDesc + "'";
		if (strNextControls != null && strNextControls.length > 0)
		{
			strMainProperty = strMainProperty + " onfocus=\"nextfield='" + strNextControls[0] + "'\"";
		}
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl" };
		String[] strMainFields = { "AbstractDesc" };
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "AbstractID" };
		String[] strReturnValues = { String.valueOf(lAbstractID)};
		String[] strDisplayNames = { URLEncoder.encode("摘要代号"), URLEncoder.encode("摘要描述")};
		String[] strDisplayFields = { "AbstractCode", "AbstractDesc" };
		int nIndex = 1;
		String strSQL = "getAbstractSQL(" + lOfficeID + "," + strCtrlName + "Ctrl.value)";
		boolean blnIsOptional = true;
		try
		{
			SETTMagnifier.showZoomCtrl(
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
		catch (Exception e)
		{
			Log.print("摘要放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	/**
	 * 创建现金流向放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lCashFlowID 现金流向ID(初识值)
	 * @param strCashFlowDesc 现金流向描述(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnMultiCodeCtrl 返回值（多维码）对应的控件名
	 */
	public static void createCashFlowCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lCashFlowID,
		String strCashFlowDesc,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnMultiCodeCtrl)
	{
		String strMagnifierName = URLEncoder.encode("现金流向");
		String strMainProperty = " maxlength='30' value='" + strCashFlowDesc + "'";
		if (strNextControls != null && strNextControls.length > 0)
		{
			strMainProperty = strMainProperty + " onfocus=\"nextfield='" + strNextControls[0] + "'\"";
		}
		String strPrefix = "";
		if (strRtnMultiCodeCtrl == null || strRtnMultiCodeCtrl.equals(""))
		{
			strRtnMultiCodeCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnMultiCodeCtrl };
		String[] strMainFields = { "CashFlowDesc", "Multicode" };
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "CashFlowID" };
		String[] strReturnValues = { String.valueOf(lCashFlowID)};
		String[] strDisplayNames = { URLEncoder.encode("现金流向描述"), URLEncoder.encode("多维码")};
		String[] strDisplayFields = { "CashFlowDesc", "Multicode" };
		int nIndex = 1;
		String strSQL = "getCashFlowSQL(" + strCtrlName + "Ctrl.value)";
		boolean blnIsOptional = true;
		try
		{
			SETTMagnifier.showZoomCtrl(
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
		catch (Exception e)
		{
			Log.print("现金流向放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	/**
	 * 创建外部银行放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lExtBankID 汇入行ID(初识值)
	 * @param strExtBankName 汇入行名称(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 */
	public static void createExtBankCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lExtBankID,
		String strExtBankName,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls)
	{
		String strMagnifierName = URLEncoder.encode("外部银行");
		String strMainProperty = " maxlength='50' value='" + strExtBankName + "'";
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl" };
		String[] strMainFields = { "RemitInBankName" };
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "RemitInBankID" };
		String[] strReturnValues = { String.valueOf(lExtBankID)};
		String[] strDisplayNames = { URLEncoder.encode("外部银行名称")};
		String[] strDisplayFields = { "RemitInBankName" };
		int nIndex = 0;
		String strSQL = "getRemitInBankSQL(" + lOfficeID + "," + lCurrencyID + "," + strCtrlName + "Ctrl.value)";
		try
		{
			SETTMagnifier.showZoomCtrl(
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
			Log.print("汇入行放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	/**
	 * 创建外部（非中油客户）账户放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lExtAccountID 外部账户ID(初识值)
	 * @param strExtAccountNo 外部账户编号(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnClientNameCtrl 返回值（账户客户名称）对应的控件名
	 * @param strRtnProvinceCtrl 返回值（汇入地(省)）对应的控件名
	 * @param strRtnCityCtrl 返回值（汇入地(市)）对应的控件名
	 * @param strRtnBankNameCtrl 返回值（汇入行名称）对应的控件名
	 */
	public static void createExtAccountCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lExtAccountID,
		String strExtAccountNo,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnClientNameCtrl,
		String strRtnProvinceCtrl,
		String strRtnCityCtrl,
		String strRtnBankNameCtrl)
	{
		String strMagnifierName = URLEncoder.encode("外部账户");
		String strMainProperty = " size='30' maxlength='30' value='" + strExtAccountNo + "'";
		String strPrefix = "";
		if (strRtnClientNameCtrl == null || strRtnClientNameCtrl.equals(""))
		{
			strRtnClientNameCtrl = "ItIsNotControl";
		}
		if (strRtnProvinceCtrl == null || strRtnProvinceCtrl.equals(""))
		{
			strRtnProvinceCtrl = "ItIsNotControl";
		}
		if (strRtnCityCtrl == null || strRtnCityCtrl.equals(""))
		{
			strRtnCityCtrl = "ItIsNotControl";
		}
		if (strRtnBankNameCtrl == null || strRtnBankNameCtrl.equals(""))
		{
			strRtnBankNameCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnClientNameCtrl, strRtnProvinceCtrl, strRtnCityCtrl, strRtnBankNameCtrl };
		String[] strMainFields = { "ExtAcctNo", "ExtAcctName", "ExtProvince", "ExtCity", "ExtBankName" };
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "ExtAcctID" };
		String[] strReturnValues = { String.valueOf(lExtAccountID)};
		String[] strDisplayNames = { URLEncoder.encode("账户编号"), URLEncoder.encode("账户名称"), URLEncoder.encode("省"), URLEncoder.encode("市"), URLEncoder.encode("汇入行名称")};
		String[] strDisplayFields = { "ExtAcctNo", "ExtAcctName", "ExtProvince", "ExtCity", "ExtBankName" };
		int nIndex = 0;
		String strSQL = "getExtAcctCurrencySQL(" + lOfficeID + "," +lCurrencyID + "," + strCtrlName + "Ctrl.value)";
		try
		{
			SETTMagnifier.showZoomCtrl(
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
			Log.print("外部（非中油客户）账户放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	/**
	 * 创建外部（非中油客户）客户放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lExtAccountID 外部账户ID(初识值)
	 * @param strExtAccountNo 外部账户编号(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnClientNameCtrl 返回值（账户客户名称）对应的控件名
	 * @param strRtnProvinceCtrl 返回值（汇入地(省)）对应的控件名
	 * @param strRtnCityCtrl 返回值（汇入地(市)）对应的控件名
	 * @param strRtnBankNameCtrl 返回值（汇入行名称）对应的控件名
	 */
	public static void createExtClientCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lExtAccountID,
		String strExtClientName,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnAccountNoCtrl,
		String strRtnProvinceCtrl,
		String strRtnCityCtrl,
		String strRtnBankNameCtrl,
		String strRtnPayeeBankCNAPSNOCtrl,
		String strRtnPayeeBankOrgNOCtrl,
		String strRtnPayeeBankExchangeNOCtrl)
	{
		String strMagnifierName = URLEncoder.encode("外部账户");
		String strMainProperty = strExtClientName;
		String strPrefix = "";
		if (strRtnAccountNoCtrl == null || strRtnAccountNoCtrl.equals(""))
		{
			strRtnAccountNoCtrl = "ItIsNotControl";
		}
		if (strRtnProvinceCtrl == null || strRtnProvinceCtrl.equals(""))
		{
			strRtnProvinceCtrl = "ItIsNotControl";
		}
		if (strRtnCityCtrl == null || strRtnCityCtrl.equals(""))
		{
			strRtnCityCtrl = "ItIsNotControl";
		}
		if (strRtnBankNameCtrl == null || strRtnBankNameCtrl.equals(""))
		{
			strRtnBankNameCtrl = "ItIsNotControl";
		}
		if (strRtnPayeeBankCNAPSNOCtrl == null || strRtnPayeeBankCNAPSNOCtrl.equals(""))
		{
			strRtnPayeeBankCNAPSNOCtrl = "ItIsNotControl";
		}
		if (strRtnPayeeBankOrgNOCtrl == null || strRtnPayeeBankOrgNOCtrl.equals(""))
		{
			strRtnPayeeBankOrgNOCtrl = "ItIsNotControl";
		}
		if (strRtnPayeeBankExchangeNOCtrl == null || strRtnPayeeBankExchangeNOCtrl.equals(""))
		{
			strRtnPayeeBankExchangeNOCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName , strRtnAccountNoCtrl + "Ctrl", strRtnProvinceCtrl, strRtnCityCtrl, strRtnBankNameCtrl, strRtnPayeeBankCNAPSNOCtrl, strRtnPayeeBankOrgNOCtrl,strRtnPayeeBankExchangeNOCtrl  };
		String[] strMainFields = { "ExtAcctName", "ExtAcctNo", "ExtProvince", "ExtCity", "ExtBankName","ExtPayeeBankCNAPSNO","ExtPayeeBankOrgNO","ExtPayeeBankExchangeNO"};
		String[] strReturnNames = { "hid"+strCtrlName };
		String[] strReturnFields = { "ExtAcctName" };
		String[] strReturnValues = { String.valueOf(lExtAccountID)};
		String[] strDisplayNames = { URLEncoder.encode("账户编号"), URLEncoder.encode("账户名称"), URLEncoder.encode("省"), URLEncoder.encode("市"), URLEncoder.encode("汇入行名称"),URLEncoder.encode("汇入行CNAPS号"),URLEncoder.encode("汇入行机构号"),URLEncoder.encode("汇入行联行号")};
		String[] strDisplayFields = { "ExtAcctNo", "ExtAcctName", "ExtProvince", "ExtCity", "ExtBankName" ,"ExtPayeeBankCNAPSNO","ExtPayeeBankOrgNO","ExtPayeeBankExchangeNO"};
		int nIndex = 0;
		String strSQL = "getExtClientSQL(" + lOfficeID + "," + strCtrlName + ".value" + "," + strRtnAccountNoCtrl + "Ctrl.value)";
		try
		{
			SETTMagnifier.showZoomCtrl(
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
				false,
				"branch");
		}
		catch (Exception e)
		{
			Log.print("外部（非中油客户）账户放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	/**
	 * 创建定期账户放大镜（返回定期（通知）存单号，定期存款开立和通知存款开立专用）
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
	 * @param lReceiveOrPay 收付类型
	 * @param strClientCtrl 客户ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnClientIDCtrl 返回值（客户ID）对应的控件名
	 * @param strRtnClientNoCtrl 返回值（客户编号）对应的控件名
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 * @param strRtnDepositNoCtrl 返回值（定期/通知存单号）对应的控件名
	 */
	public static void createAccountRtnDepositNoCtrl(
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
		String strRtnClientNameCtrl,
		String strRtnDepositNoCtrl)
	{
		try
		{
			String strMagnifierName = URLEncoder.encode("账户");
			String strPrefix = "";
			String strMainProperty = "";
			if (lAccountGroupTypeID != SETTConstant.AccountGroupType.FIXED
			        && lAccountGroupTypeID != SETTConstant.AccountGroupType.NOTIFY)
			{
				out.println("账户组必须为定期账户组或通知账户组。");
				return;
			}
			int nCaseNumber = 3;
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
			if (strRtnDepositNoCtrl == null || strRtnDepositNoCtrl.equals(""))
			{
				strRtnDepositNoCtrl = "ItIsNotControl";
			}
			String[] strMainNames = { strCtrlName + "Ctrl", strRtnClientIDCtrl, strRtnClientNoCtrl, strRtnClientNameCtrl, strRtnDepositNoCtrl};
			String[] strMainFields = { "AccountNo", "ClientID", "ClientNo", "ClientName", "DepositNo"};
			String[] strReturnNames = { strCtrlName, strCtrlName + "ClientID" };
			String[] strReturnFields = { "AccountID", "ClientID" };
			String[] strReturnValues = { String.valueOf(lAccountID), String.valueOf(lClientID)};
			String[] strDisplayNames = { URLEncoder.encode("账户编号"), URLEncoder.encode("账户名称")};
			String[] strDisplayFields = { "AccountNo", "AccountName" };
			int nIndexAccount = 0;
			String strSQL = "";
			if (strClientCtrl != null && !strClientCtrl.trim().equals(""))
			{
				strSQL =
					"getAccountRtnDepostNoSQL("
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
					"getAccountRtnDepostNoSQL("
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
			}
			SETTMagnifier.showSpecialZoomCtrl(
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
			Log.print("账户(返回存单号)放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	/**
	 * 创建定期账户放大镜（返回定期（通知）存单号，返回开立起存金额，定期存款开立和通知存款开立专用）
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
	 * @param lReceiveOrPay 收付类型
	 * @param strClientCtrl 客户ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnClientIDCtrl 返回值（客户ID）对应的控件名
	 * @param strRtnClientNoCtrl 返回值（客户编号）对应的控件名
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 * @param strRtnDepositNoCtrl 返回值（定期/通知存单号）对应的控件名
	 * @param strRtnMinSinglePayAmountCtrl 返回值（起存金额）
	 */
	public static void createAccountRtnMinSinglePayAmountNoCtrl(
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
		String strRtnClientNameCtrl,
		String strRtnDepositNoCtrl,
		String strRtnMinSinglePayAmountCtrl)
	{
		try
		{
			String strMagnifierName = URLEncoder.encode("账户");
			String strPrefix = "";
			String strMainProperty = "";
			if (lAccountGroupTypeID != SETTConstant.AccountGroupType.FIXED
			        && lAccountGroupTypeID != SETTConstant.AccountGroupType.NOTIFY
			        && lAccountGroupTypeID != SETTConstant.AccountGroupType.MARGIN)
			{
				out.println("账户组必须为定期账户组或通知账户组或保证金存款账户组。");
				return;
			}
			int nCaseNumber = 3;
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
			if (strRtnDepositNoCtrl == null || strRtnDepositNoCtrl.equals(""))
			{
				strRtnDepositNoCtrl = "ItIsNotControl";
			}
			String[] strMainNames = { strCtrlName + "Ctrl", strRtnClientIDCtrl, strRtnClientNoCtrl, strRtnClientNameCtrl, strRtnDepositNoCtrl, strRtnMinSinglePayAmountCtrl};
			String[] strMainFields = { "AccountNo", "ClientID", "ClientNo", "ClientName", "DepositNo", "MinSinglePayAmount"};
			String[] strReturnNames = { strCtrlName, strCtrlName + "ClientID" };
			String[] strReturnFields = { "AccountID", "ClientID" };
			String[] strReturnValues = { String.valueOf(lAccountID), String.valueOf(lClientID)};
			String[] strDisplayNames = { URLEncoder.encode("账户编号"), URLEncoder.encode("账户名称")};
			String[] strDisplayFields = { "AccountNo", "AccountName" };
			int nIndexAccount = 0;
			String strSQL = "";
			if (strClientCtrl != null && !strClientCtrl.trim().equals(""))
			{
				strSQL =
					"getAccountRtnDepostNoSQL("
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
					"getAccountRtnDepostNoSQL("
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
			}
			SETTMagnifier.showSpecialZoomCtrl(
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
			Log.print("账户(返回存单号)放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	
	/**
	 * 创建定期账户放大镜（返回定期（通知）存单号，返回开立起存金额，返回柔刚性选择，定期存款开立和通知存款开立专用）
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
	 * @param lReceiveOrPay 收付类型
	 * @param strClientCtrl 客户ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnClientIDCtrl 返回值（客户ID）对应的控件名
	 * @param strRtnClientNoCtrl 返回值（客户编号）对应的控件名
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 * @param strRtnDepositNoCtrl 返回值（定期/通知存单号）对应的控件名
	 * @param strRtnMinSinglePayAmountCtrl 返回值（起存金额）
	 * @param lIsSoft 返回值（柔刚性选择）
	 */
	public static void createAccountRtnMinSinglePayAmountNoCtrl(
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
		String strRtnClientNameCtrl,
		String strRtnDepositNoCtrl,
		String strRtnMinSinglePayAmountCtrl,
		String strRtnSoftCtrl)
	{
		try
		{
			String strMagnifierName = URLEncoder.encode("账户");
			String strPrefix = "";
			String strMainProperty = "";
			if (lAccountGroupTypeID != SETTConstant.AccountGroupType.FIXED
			        && lAccountGroupTypeID != SETTConstant.AccountGroupType.NOTIFY
			        && lAccountGroupTypeID != SETTConstant.AccountGroupType.MARGIN)
			{
				out.println("账户组必须为定期账户组或通知账户组或保证金存款账户组。");
				return;
			}
			int nCaseNumber = 3;
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
			if (strRtnDepositNoCtrl == null || strRtnDepositNoCtrl.equals(""))
			{
				strRtnDepositNoCtrl = "ItIsNotControl";
			}
			String[] strMainNames = { strCtrlName + "Ctrl", strRtnClientIDCtrl, strRtnClientNoCtrl, strRtnClientNameCtrl, strRtnDepositNoCtrl, strRtnMinSinglePayAmountCtrl, strRtnSoftCtrl};
			String[] strMainFields = { "AccountNo", "ClientID", "ClientNo", "ClientName", "DepositNo", "MinSinglePayAmount", "IsSoft"};
			String[] strReturnNames = { strCtrlName, strCtrlName + "ClientID" };
			String[] strReturnFields = { "AccountID", "ClientID" };
			String[] strReturnValues = { String.valueOf(lAccountID), String.valueOf(lClientID)};
			String[] strDisplayNames = { URLEncoder.encode("账户编号"), URLEncoder.encode("账户名称")};
			String[] strDisplayFields = { "AccountNo", "AccountName" };
			int nIndexAccount = 0;
			String strSQL = "";
			if (strClientCtrl != null && !strClientCtrl.trim().equals(""))
			{
				strSQL =
					"getAccountExtendRtnDepostNoSQL("
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
					"getAccountExtendRtnDepostNoSQL("
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
			}
			SETTMagnifier.showSpecialZoomCtrl(
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
			Log.print("账户(返回存单号)放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	
	/**
	 * 创建定期账户放大镜（返回定期（通知）存单号，定期存款开立和通知存款开立专用）
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
	 * @param lReceiveOrPay 收付类型
	 * @param strClientCtrl 客户ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnClientIDCtrl 返回值（客户ID）对应的控件名
	 * @param strRtnClientNoCtrl 返回值（客户编号）对应的控件名
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 * @param strRtnDepositNoCtrl 返回值（定期/通知存单号）对应的控件名
	 */
	public static void createLossAndFreezeAccountRtnDepositNoCtrl(
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
		long lAccountStatus,
		String strClientCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnClientIDCtrl,
		String strRtnClientNoCtrl,
		String strRtnClientNameCtrl,
		String strRtnDepositNoCtrl)
	{
		try
		{
			String strMagnifierName = URLEncoder.encode("账户");
			String strPrefix = "";
			String strMainProperty = "";
			if (lAccountGroupTypeID != SETTConstant.AccountGroupType.FIXED)
			{
				out.println("账户组必须为定期账户组。");
				return;
			}
			int nCaseNumber = 4;
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
			if (strRtnDepositNoCtrl == null || strRtnDepositNoCtrl.equals(""))
			{
				strRtnDepositNoCtrl = "ItIsNotControl";
			}
			String[] strMainNames = { strCtrlName + "Ctrl", strRtnClientIDCtrl, strRtnClientNoCtrl, strRtnClientNameCtrl, strRtnDepositNoCtrl };
			String[] strMainFields = { "AccountNo", "ClientID", "ClientNo", "ClientName", "DepositNo" };
			String[] strReturnNames = { strCtrlName, strCtrlName + "ClientID" };
			String[] strReturnFields = { "AccountID", "ClientID" };
			String[] strReturnValues = { String.valueOf(lAccountID), String.valueOf(lClientID)};
			String[] strDisplayNames = { URLEncoder.encode("账户编号"), URLEncoder.encode("账户名称")};
			String[] strDisplayFields = { "AccountNo", "AccountName" };
			int nIndexAccount = 0;
			String strSQL = "";
			if (strClientCtrl != null && !strClientCtrl.trim().equals(""))
			{
				strSQL =
					"getLossAndFreezeAccountRtnDepostNoSQL("
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
						+ lAccountStatus
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
					"getLossAndFreezeAccountRtnDepostNoSQL("
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
						+ lAccountStatus
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
			}
			SETTMagnifier.showSpecialZoomCtrl(
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
			Log.print("账户(返回存单号)放大镜[" + strCtrlName + "]异常：" + e.toString());
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
	 * 	特殊的，利息费用支付，请传入-100（只显示两种信托和委托两种）；）
	 * @param lReceiveOrPay 收付类型（-1000特殊的，现实所有状态的账户）
	 * @param strClientCtrl 客户ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnClientIDCtrl 返回值（客户ID）对应的控件名
	 * @param strRtnClientNoCtrl 返回值（客户编号）对应的控件名
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 */
	public static void createLossAndFreezeAccountCtrl(
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
		long lAccountStatus,
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
				"getLossAndFreezeAccountSQL("
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
					+ lAccountStatus
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
				"getLossAndFreezeAccountSQL("
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
					+ lAccountStatus
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
			//			if (strAccountNo != null && strAccountNo.length() < 5)
			//			{
			//				strAccountNo = "";
			//			}
			//			else
			//			{
			//				strAccountNo = strAccountNo + "--";
			//			}
		}
		try
		{
			SETTMagnifier.showSpecialZoomCtrl(
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
	 * 创建账户放大镜(账户冻结录入专用)
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
	 * 	特殊的，利息费用支付，请传入-100（只显示两种信托和委托两种）；）
	 * @param lReceiveOrPay 收付类型（-1000特殊的，现实所有状态的账户）
	 * @param strClientCtrl 客户ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnClientIDCtrl 返回值（客户ID）对应的控件名
	 * @param strRtnClientNoCtrl 返回值（客户编号）对应的控件名
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 * @param strRtnAccountGroupId 返回值（账户组）对应的控件名
	 */
	public static void createFreezeAccountCtrl(
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
		long lAccountStatus,
		String strClientCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnClientIDCtrl,
		String strRtnClientNoCtrl,
		String strRtnClientNameCtrl,
		String strRtnAccountGroupId)
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
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnClientIDCtrl, strRtnClientNoCtrl, strRtnClientNameCtrl,strRtnAccountGroupId};
		String[] strMainFields = { "AccountNo", "ClientID", "ClientNo", "ClientName","naccountgroupid" };
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
				"getLossAndFreezeAccountSQL("
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
					+ lAccountStatus
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
				"getLossAndFreezeAccountSQL("
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
					+ lAccountStatus
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
			//			if (strAccountNo != null && strAccountNo.length() < 5)
			//			{
			//				strAccountNo = "";
			//			}
			//			else
			//			{
			//				strAccountNo = strAccountNo + "--";
			//			}
		}
		try
		{
			SETTMagnifier.showSpecialZoomCtrl(
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
	 * 创建账户放大镜（解冻录入专用）
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
	 * 	特殊的，利息费用支付，请传入-100（只显示两种信托和委托两种）；）
	 * @param lReceiveOrPay 收付类型（-1000特殊的，现实所有状态的账户）
	 * @param strClientCtrl 客户ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnClientIDCtrl 返回值（客户ID）对应的控件名
	 * @param strRtnClientNoCtrl 返回值（客户编号）对应的控件名
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 * @param strRtnfreezeAmount 返回值（解冻金额）对应的控件名
	 * @param strRtnAccountGroupId 返回值（账户组）对应的控件名 
	 */
	public static void createDeFreezeAccountCtrl(
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
		long lAccountStatus,
		String strClientCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnClientIDCtrl,
		String strRtnClientNoCtrl,
		String strRtnClientNameCtrl,
		String strRtnfreezeAmount,
		String strRtnAccountGroupId)
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
		if (strRtnfreezeAmount == null || strRtnfreezeAmount.equals(""))
		{
			strRtnfreezeAmount = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnClientIDCtrl, strRtnClientNoCtrl, strRtnClientNameCtrl,strRtnfreezeAmount,strRtnAccountGroupId};
		String[] strMainFields = { "AccountNo", "ClientID", "ClientNo", "ClientName","FREEZEAMOUNT","naccountgroupid" };
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
				"getLossAndFreezeAccountSQL("
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
					+ lAccountStatus
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
				"getLossAndFreezeAccountSQL("
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
					+ lAccountStatus
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
			//			if (strAccountNo != null && strAccountNo.length() < 5)
			//			{
			//				strAccountNo = "";
			//			}
			//			else
			//			{
			//				strAccountNo = strAccountNo + "--";
			//			}
		}
		try
		{
			SETTMagnifier.showSpecialZoomCtrl(
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
	 * 	特殊的，利息费用支付，请传入-100（只显示两种信托和委托两种）；）
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
		/*修改 by kenny (胡志强) (2007-03-22) 修改账户号段数的问题*/
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
			nCaseNumber = 3;
		}
		else if (lAccountGroupTypeID == SETTConstant.AccountGroupType.TRUST
		        ||lAccountGroupTypeID == SETTConstant.AccountGroupType.CONSIGN
		        ||lAccountGroupTypeID == SETTConstant.AccountGroupType.DISCOUNT
		        ||lAccountGroupTypeID == SETTConstant.AccountGroupType.OTHERLOAN		       
		        ||lAccountGroupTypeID == SETTConstant.AccountGroupType.YT)
		{
			//贷款账户组，显示3个文本框
			nCaseNumber = 3;
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
		String[] strReturnNames = { strCtrlName, strCtrlName + "ClientID" , strCtrlName + "GroupID" };
		String[] strReturnFields = { "AccountID", "ClientID" ,"accountgroupid"};
		String[] strReturnValues = { String.valueOf(lAccountID), String.valueOf(lClientID), String.valueOf(lAccountGroupTypeID)};
		String[] strDisplayNames = { URLEncoder.encode("账户编号"), URLEncoder.encode("账户名称")};
		String[] strDisplayFields = { "AccountNo", "AccountName" };
		int nIndexAccount = 0;
		String strSQL = "";
		if (strClientCtrl != null && !strClientCtrl.trim().equals(""))
		{
			strSQL =
			"getAccountSQL_old("
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
			"getAccountSQL_old("
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
			/*
			if (lOfficeID > 0)
			{
				strAccountNo = NameRef.getOfficeNoByID(lOfficeID);
			}
			*/
			//修改 by kenny(胡志强)(2007-03-26) 处理账户号编码规则问题
			//账户号的段间符号
			String tag = Config.getProperty(ConfigConstant.GLOBAL_ACCOUNTNO_TAG,"-");
			//账户号的第一段的类型
			int firstFieldType = Config.getInteger(ConfigConstant.GLOBAL_ACCOUNTNO_FIRSTFIELDTYPE,1);
			//正常情况下lCurrencyID、lOfficeID均会大于0
			if (firstFieldType == 1) {
				if (lCurrencyID > 0) {
					strAccountNo = NameRef.getCurrencyNoByID(lCurrencyID);
				}
				if (lOfficeID > 0) {
					strAccountNo = strAccountNo + tag + NameRef.getOfficeNoByID(lOfficeID);
				}
			/*	if (lAccountTypeID > 0) {
					strAccountNo = strAccountNo + tag + DataFormat.formatInt(lAccountTypeID, 2);
				}*/
			} else if (firstFieldType == 2) {
				if (lOfficeID > 0) {
					strAccountNo = NameRef.getOfficeNoByID(lOfficeID);
				}
				/*if (lAccountTypeID > 0) {
					strAccountNo = strAccountNo + tag + DataFormat.formatInt(lAccountTypeID, 2);
				}*/
			}
		}
		try
		{
			SETTMagnifier.showSpecialZoomCtrl(
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
	 * 	特殊的，利息费用支付，请传入-100（只显示两种信托和委托两种）；）
	 * @param lReceiveOrPay 收付类型（-1000特殊的，现实所有状态的账户）
	 * @param strClientCtrl 客户ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnClientIDCtrl 返回值（客户ID）对应的控件名
	 * @param strRtnClientNoCtrl 返回值（客户编号）对应的控件名
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 */
	public static void createCommissionAccountCtrl(
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
		String strRtnClientNameCtrl,
		String strRtnPayeeClientIDCtrl,
		String strRtnPayeeClientNoCtrl,
		String strRtnPayeeClientNameCtrl)
	{
		String strMagnifierName = URLEncoder.encode("账户");
		String strPrefix = "";
		String strMainProperty = "";
		int nCaseNumber = 4;
		/*修改 by kenny (胡志强) (2007-03-22) 修改账户号段数的问题*/
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
			nCaseNumber = 3;
		}
		else if (lAccountGroupTypeID == SETTConstant.AccountGroupType.TRUST
		        ||lAccountGroupTypeID == SETTConstant.AccountGroupType.CONSIGN
		        ||lAccountGroupTypeID == SETTConstant.AccountGroupType.DISCOUNT
		        ||lAccountGroupTypeID == SETTConstant.AccountGroupType.OTHERLOAN		       
		        ||lAccountGroupTypeID == SETTConstant.AccountGroupType.YT)
		{
			//贷款账户组，显示3个文本框
			nCaseNumber = 3;
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
		if (strRtnPayeeClientIDCtrl == null || strRtnPayeeClientIDCtrl.equals(""))
		{
			strRtnPayeeClientIDCtrl = "ItIsNotControl";
		}
		if (strRtnPayeeClientNoCtrl == null || strRtnPayeeClientNoCtrl.equals(""))
		{
			strRtnPayeeClientNoCtrl = "ItIsNotControl";
		}
		if (strRtnPayeeClientNameCtrl == null || strRtnPayeeClientNameCtrl.equals(""))
		{
			strRtnPayeeClientNameCtrl = "ItIsNotControl";
		}		
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnClientIDCtrl, strRtnClientNoCtrl, strRtnClientNameCtrl,strRtnPayeeClientIDCtrl,strRtnPayeeClientNoCtrl,strRtnPayeeClientNameCtrl };
		String[] strMainFields = { "AccountNo", "ClientID", "ClientNo", "ClientName" ,"ClientID", "ClientNo", "ClientName"};
		String[] strReturnNames = { strCtrlName, strCtrlName + "ClientID" , strCtrlName + "GroupID" };
		String[] strReturnFields = { "AccountID", "ClientID" ,"accountgroupid"};
		String[] strReturnValues = { String.valueOf(lAccountID), String.valueOf(lClientID), String.valueOf(lAccountGroupTypeID)};
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
			/*
			if (lOfficeID > 0)
			{
				strAccountNo = NameRef.getOfficeNoByID(lOfficeID);
			}
			*/
			//修改 by kenny(胡志强)(2007-03-26) 处理账户号编码规则问题
			//账户号的段间符号
			String tag = Config.getProperty(ConfigConstant.GLOBAL_ACCOUNTNO_TAG,"-");
			//账户号的第一段的类型
			int firstFieldType = Config.getInteger(ConfigConstant.GLOBAL_ACCOUNTNO_FIRSTFIELDTYPE,1);
			//正常情况下lCurrencyID、lOfficeID均会大于0
			if (firstFieldType == 1) {
				if (lCurrencyID > 0) {
					strAccountNo = NameRef.getCurrencyNoByID(lCurrencyID);
				}
				if (lOfficeID > 0) {
					strAccountNo = strAccountNo + tag + NameRef.getOfficeNoByID(lOfficeID);
				}
				if (lAccountTypeID > 0) {
					strAccountNo = strAccountNo + tag + DataFormat.formatInt(lAccountTypeID, 2);
				}
			} else if (firstFieldType == 2) {
				if (lOfficeID > 0) {
					strAccountNo = NameRef.getOfficeNoByID(lOfficeID);
				}
				if (lAccountTypeID > 0) {
					strAccountNo = strAccountNo + tag + DataFormat.formatInt(lAccountTypeID, 2);
				}
			}
		}
		try
		{
			SETTMagnifier.showSpecialZoomCtrl(
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
     *         特殊的，利息费用支付，请传入-100（只显示两种信托和委托两种）；）
     * @param lReceiveOrPay 收付类型（-1000特殊的，现实所有状态的账户）
     * @param strClientCtrl 客户ID对应的控件名称（查询时关联）
     * @param strFirstTD 第一个TD的属性
     * @param strSecondTD 第二个TD的属性
     * @param strNextControls 下一个（或多个）获得焦点的控件
     * @param strRtnClientIDCtrl 返回值（客户ID）对应的控件名
     * @param strRtnClientNoCtrl 返回值（客户编号）对应的控件名
     * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
     */
    public static void createReserveAccountCtrl(
            JspWriter out,
            long lOfficeID,
            long lCurrencyID,
            String strFormName,
            String strCtrlName,
            String strTitle,
            long belongOfficeID,
            long ClientID,
            long lAccountID,
            String strAccountNo,
            long lAccountGroupTypeID,
            long lAccountTypeID,
            long lReceiveOrPay,
            String strClientCtrl,
            String strBelongOfficeCtrl,
            String strFirstTD,
            String strSecondTD,
            String[] strNextControls,
            String strRtnOfficeIDCtrl,
            String strRtnOfficeNoCtrl,
            String strRtnOfficeNameCtrl)
    {
            String strMagnifierName = URLEncoder.encode("账户");
            String strPrefix = "";
            String strMainProperty = "";
            int nCaseNumber = 4;
            /*修改 by kenny (胡志强) (2007-03-22) 修改账户号段数的问题*/
            if (lAccountGroupTypeID == SETTConstant.AccountGroupType.CURRENT
                    ||lAccountGroupTypeID == SETTConstant.AccountGroupType.OTHERDEPOSIT
                    || lAccountGroupTypeID == SETTConstant.AccountGroupType.BAK
                    || lAccountGroupTypeID == SETTConstant.AccountGroupType.RESERVE
                    || lAccountGroupTypeID == SETTConstant.AccountGroupType.LENDING)
            {
                    //活期账户组，显示4个文本框
                    nCaseNumber = 4;
            }
            else if (lAccountGroupTypeID == SETTConstant.AccountGroupType.FIXED
                    ||lAccountGroupTypeID == SETTConstant.AccountGroupType.NOTIFY)
            {
                    //定期账户组，显示3个文本框
                    nCaseNumber = 3;
            }
            else if (lAccountGroupTypeID == SETTConstant.AccountGroupType.TRUST
                    ||lAccountGroupTypeID == SETTConstant.AccountGroupType.CONSIGN
                    ||lAccountGroupTypeID == SETTConstant.AccountGroupType.DISCOUNT
                    ||lAccountGroupTypeID == SETTConstant.AccountGroupType.OTHERLOAN                       
                    ||lAccountGroupTypeID == SETTConstant.AccountGroupType.YT)
            {
                    //贷款账户组，显示3个文本框
                    nCaseNumber = 3;
            }
            if (strRtnOfficeIDCtrl == null || strRtnOfficeIDCtrl.equals(""))
            {
                    strRtnOfficeIDCtrl = "ItIsNotControl";
            }
            if (strRtnOfficeNoCtrl == null || strRtnOfficeNoCtrl.equals(""))
            {
                    strRtnOfficeNoCtrl = "ItIsNotControl";
            }
            if (strRtnOfficeNameCtrl == null || strRtnOfficeNameCtrl.equals(""))
            {
                    strRtnOfficeNameCtrl = "ItIsNotControl";
            }
            String[] strMainNames = { strCtrlName + "Ctrl",strCtrlName+"ClientName" ,strRtnOfficeIDCtrl,strRtnOfficeNoCtrl, strRtnOfficeNameCtrl };
            String[] strMainFields = { "AccountNo","ClientName","ClientBelongOfficeID", "ClientBelongOfficeCode", "ClientBelongOfficeName"};
            String[] strReturnNames = { strCtrlName, strCtrlName + "ClientID" , strCtrlName + "GroupID",strCtrlName+"OfficeID"};
            String[] strReturnFields = { "AccountID", "ClientID" ,"accountgroupid","ClientBelongOfficeID"};
            String[] strReturnValues = { String.valueOf(lAccountID), String.valueOf(ClientID), String.valueOf(lAccountGroupTypeID),String.valueOf(belongOfficeID)};
            String[] strDisplayNames = { URLEncoder.encode("账户编号"), URLEncoder.encode("账户名称")};
            String[] strDisplayFields = { "AccountNo", "AccountName" };
            int nIndexAccount = 0;
            String strSQL = "";
            if(strClientCtrl !=null && !strClientCtrl.trim().equals("") )
            {
            	
                strSQL =
                    "getReserveAccountSQL("
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
                            + ",-1,"
                            + strFormName
                            + "."
                            + strCtrlName
                            + "Ctrl.value)";
            	
            	
            	
            	
            }            
            else if (strBelongOfficeCtrl != null && !strBelongOfficeCtrl.trim().equals(""))
            {
                    strSQL =
                    "getReserveAccountSQL("
                            + lOfficeID
                            + ","
                            + lCurrencyID
                            + ","
                            + lAccountGroupTypeID
                            + ","
                            + lAccountTypeID
                            + ","
                            + lReceiveOrPay
                            + ",-1"
                            + ","
                            + strFormName
                            + "."
                            + strBelongOfficeCtrl
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
                    "getReserveAccountSQL("
                            + lOfficeID
                            + ","
                            + lCurrencyID
                            + ","
                            + lAccountGroupTypeID
                            + ","
                            + lAccountTypeID
                            + ","
                            + lReceiveOrPay
                            + ",-1,-1,"
                            + strFormName
                            + "."
                            + strCtrlName
                            + "Ctrl.value)";
            }
            //初始默认值
            if (strAccountNo == null || strAccountNo.equals(""))
            {
                    /*
                    if (lOfficeID > 0)
                    {
                            strAccountNo = NameRef.getOfficeNoByID(lOfficeID);
                    }
                    */
                    //修改 by kenny(胡志强)(2007-03-26) 处理账户号编码规则问题
                    //账户号的段间符号
                    String tag = Config.getProperty(ConfigConstant.GLOBAL_ACCOUNTNO_TAG,"-");
                    //账户号的第一段的类型
                    int firstFieldType = Config.getInteger(ConfigConstant.GLOBAL_ACCOUNTNO_FIRSTFIELDTYPE,1);
                    //正常情况下lCurrencyID、lOfficeID均会大于0
                    if (firstFieldType == 1) {
                            if (lCurrencyID > 0) {
                                    strAccountNo = NameRef.getCurrencyNoByID(lCurrencyID);
                            }
                            if (lOfficeID > 0) {
                                    strAccountNo = strAccountNo + tag + NameRef.getOfficeNoByID(lOfficeID);
                            }
                            if (lAccountTypeID > 0) {
                                    strAccountNo = strAccountNo + tag + DataFormat.formatInt(lAccountTypeID, 2);
                            }
                    } else if (firstFieldType == 2) {
                            if (lOfficeID > 0) {
                                    strAccountNo = NameRef.getOfficeNoByID(lOfficeID);
                            }
                            if (lAccountTypeID > 0) {
                                    strAccountNo = strAccountNo + tag + DataFormat.formatInt(lAccountTypeID, 2);
                            }
                    }
            }
            try
            {
                    SETTMagnifier.showSpecialZoomCtrl(
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
			String strRtnClientNameCtrl,
			String strRtnAccountNameCtrl)
		{
			String strMagnifierName = URLEncoder.encode("账户");
			String strPrefix = "";
			String strMainProperty = "";
			int nCaseNumber = 4;
			/*修改 by kenny (胡志强) (2007-03-22) 修改账户号段数的问题*/
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
				nCaseNumber = 3;
			}
			else if (lAccountGroupTypeID == SETTConstant.AccountGroupType.TRUST
			        ||lAccountGroupTypeID == SETTConstant.AccountGroupType.CONSIGN
			        ||lAccountGroupTypeID == SETTConstant.AccountGroupType.DISCOUNT
			        ||lAccountGroupTypeID == SETTConstant.AccountGroupType.OTHERLOAN
			        ||lAccountGroupTypeID == SETTConstant.AccountGroupType.YT)
			{
				//贷款账户组，显示3个文本框
				nCaseNumber = 3;
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
			String[] strMainNames = { strCtrlName + "Ctrl", strRtnClientIDCtrl, strRtnClientNoCtrl, strRtnClientNameCtrl,strRtnAccountNameCtrl };
			String[] strMainFields = { "AccountNo", "ClientID", "ClientNo", "ClientName" ,"AccountName"};
			String[] strReturnNames = { strCtrlName, strCtrlName + "ClientID" , strCtrlName + "GroupID" };
			String[] strReturnFields = { "AccountID", "ClientID" ,"accountgroupid"};
			String[] strReturnValues = { String.valueOf(lAccountID), String.valueOf(lClientID), String.valueOf(lAccountGroupTypeID)};
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
				/*
				if (lOfficeID > 0)
				{
					strAccountNo = NameRef.getOfficeNoByID(lOfficeID);
				}
				*/
				//修改 by kenny(胡志强)(2007-03-26) 处理账户号编码规则问题
				//账户号的段间符号
				String tag = Config.getProperty(ConfigConstant.GLOBAL_ACCOUNTNO_TAG,"-");
				//账户号的第一段的类型
				int firstFieldType = Config.getInteger(ConfigConstant.GLOBAL_ACCOUNTNO_FIRSTFIELDTYPE,1);
				//正常情况下lCurrencyID、lOfficeID均会大于0
				if (firstFieldType == 1) {
					if (lCurrencyID > 0) {
						strAccountNo = NameRef.getCurrencyNoByID(lCurrencyID);
					}
					if (lOfficeID > 0) {
						strAccountNo = strAccountNo + tag + NameRef.getOfficeNoByID(lOfficeID);
					}
					if (lAccountTypeID > 0) {
						strAccountNo = strAccountNo + tag + DataFormat.formatInt(lAccountTypeID, 2);
					}
				} else if (firstFieldType == 2) {
					if (lOfficeID > 0) {
						strAccountNo = NameRef.getOfficeNoByID(lOfficeID);
					}
					if (lAccountTypeID > 0) {
						strAccountNo = strAccountNo + tag + DataFormat.formatInt(lAccountTypeID, 2);
					}
				}
			}
			try
			{
				SETTMagnifier.showSpecialZoomCtrl(
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
	 * 	特殊的，利息费用支付，请传入-100（只显示两种信托和委托两种）；）
	 * @param lReceiveOrPay 收付类型（-1000特殊的，现实所有状态的账户）
	 * @param strClientCtrl 客户ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnClientIDCtrl 返回值（客户ID）对应的控件名
	 * @param strRtnClientNoCtrl 返回值（客户编号）对应的控件名
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 */
	public static void createAccountNewCtrl(
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
		String strClientCtrl1,
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
		/*修改 by kenny (胡志强) (2007-03-22) 修改账户号段数的问题*/
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
			nCaseNumber = 3;
		}
		else if (lAccountGroupTypeID == SETTConstant.AccountGroupType.TRUST
		        ||lAccountGroupTypeID == SETTConstant.AccountGroupType.CONSIGN
		        ||lAccountGroupTypeID == SETTConstant.AccountGroupType.DISCOUNT
		        ||lAccountGroupTypeID == SETTConstant.AccountGroupType.OTHERLOAN
		        ||lAccountGroupTypeID == SETTConstant.AccountGroupType.YT)
		{
			//贷款账户组，显示3个文本框
			nCaseNumber = 3;
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
		String[] strReturnNames = { strCtrlName, strCtrlName + "ClientID" , strCtrlName + "GroupID" };
		String[] strReturnFields = { "AccountID", "ClientID" ,"accountgroupid"};
		String[] strReturnValues = { String.valueOf(lAccountID), String.valueOf(lClientID), String.valueOf(lAccountGroupTypeID)};
		String[] strDisplayNames = { URLEncoder.encode("账户编号"), URLEncoder.encode("账户名称")};
		String[] strDisplayFields = { "AccountNo", "AccountName" };
		int nIndexAccount = 0;
		String strSQL = "";
		if (strClientCtrl != null && !strClientCtrl.trim().equals("")&&strClientCtrl1 != null && !strClientCtrl1.trim().equals(""))
		{ 
			strSQL =
				"getAccountSQLNew("
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
					+ strClientCtrl1
					+ ".value"
					+ ","
					+ strFormName
					+ "."
					+ strCtrlName
					+ "Ctrl.value)";
		}else if (strClientCtrl != null && !strClientCtrl.trim().equals(""))
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
			/*
			if (lOfficeID > 0)
			{
				strAccountNo = NameRef.getOfficeNoByID(lOfficeID);
			}
			*/
			//修改 by kenny(胡志强)(2007-03-26) 处理账户号编码规则问题
			//账户号的段间符号
			String tag = Config.getProperty(ConfigConstant.GLOBAL_ACCOUNTNO_TAG,"-");
			//账户号的第一段的类型
			int firstFieldType = Config.getInteger(ConfigConstant.GLOBAL_ACCOUNTNO_FIRSTFIELDTYPE,1);
			//正常情况下lCurrencyID、lOfficeID均会大于0
			if (firstFieldType == 1) {
				if (lCurrencyID > 0) {
					strAccountNo = NameRef.getCurrencyNoByID(lCurrencyID);
				}
				if (lOfficeID > 0) {
					strAccountNo = strAccountNo + tag + NameRef.getOfficeNoByID(lOfficeID);
				}
				if (lAccountTypeID > 0) {
					strAccountNo = strAccountNo + tag + DataFormat.formatInt(lAccountTypeID, 2);
				}
			} else if (firstFieldType == 2) {
				if (lOfficeID > 0) {
					strAccountNo = NameRef.getOfficeNoByID(lOfficeID);
				}
				if (lAccountTypeID > 0) {
					strAccountNo = strAccountNo + tag + DataFormat.formatInt(lAccountTypeID, 2);
				}
			}
		}
		try
		{
			SETTMagnifier.showSpecialZoomCtrl(
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
	 * 创建旧账户放大镜
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
	 * 	特殊的，利息费用支付，请传入-100（只显示两种信托和委托两种）；）
	 * @param lReceiveOrPay 收付类型（-1000特殊的，现实所有状态的账户）
	 * @param strClientCtrl 客户ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnClientIDCtrl 返回值（客户ID）对应的控件名
	 * @param strRtnClientNoCtrl 返回值（客户编号）对应的控件名
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 */
	public static void createOldAccountCtrl(
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
			nCaseNumber = 3;
		}
		else if (lAccountGroupTypeID == SETTConstant.AccountGroupType.TRUST
		        ||lAccountGroupTypeID == SETTConstant.AccountGroupType.CONSIGN
		        ||lAccountGroupTypeID == SETTConstant.AccountGroupType.DISCOUNT
		        ||lAccountGroupTypeID == SETTConstant.AccountGroupType.OTHERLOAN)
		{
			//贷款账户组，显示3个文本框
			nCaseNumber = 3;
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
		String[] strDisplayNames = { URLEncoder.encode("账户编号"), URLEncoder.encode("旧账户号"),URLEncoder.encode("账户名称")};
		String[] strDisplayFields = { "AccountNo","OldAccount","AccountName" };
		int nIndexAccount = 0;
		String strSQL = "";
		if (strClientCtrl != null && !strClientCtrl.trim().equals(""))
		{
			strSQL =
				"getOldAccountSQL("
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
				"getOldAccountSQL("
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
			/*
			if (lOfficeID > 0)
			{
				strAccountNo = NameRef.getOfficeNoByID(lOfficeID);
			}
			*/
			//修改 by kenny(胡志强)(2007-03-26) 处理账户号编码规则问题
			//账户号的段间符号
			String tag = Config.getProperty(ConfigConstant.GLOBAL_ACCOUNTNO_TAG,"-");
			//账户号的第一段的类型
			int firstFieldType = Config.getInteger(ConfigConstant.GLOBAL_ACCOUNTNO_FIRSTFIELDTYPE,1);
			//正常情况下lCurrencyID、lOfficeID均会大于0
			if (firstFieldType == 1) {
				if (lCurrencyID > 0) {
					strAccountNo = NameRef.getCurrencyNoByID(lCurrencyID);
				}
				if (lOfficeID > 0) {
					strAccountNo = strAccountNo + tag + NameRef.getOfficeNoByID(lOfficeID);
				}
				if (lAccountTypeID > 0) {
					strAccountNo = strAccountNo + tag + DataFormat.formatInt(lAccountTypeID, 2);
				}
			} else if (firstFieldType == 2) {
				if (lOfficeID > 0) {
					strAccountNo = NameRef.getOfficeNoByID(lOfficeID);
				}
				if (lAccountTypeID > 0) {
					strAccountNo = strAccountNo + tag + DataFormat.formatInt(lAccountTypeID, 2);
				}
			}
		}
		try
		{
			SETTMagnifier.showSpecialZoomCtrl(
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
	 * 创建账户放大镜(增加账户的当前可用余额)
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
	 * 	特殊的，利息费用支付，请传入-100（只显示两种信托和委托两种）；）
	 * @param lReceiveOrPay 收付类型（-1000特殊的，现实所有状态的账户）
	 * @param strClientCtrl 客户ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnClientIDCtrl 返回值（客户ID）对应的控件名
	 * @param strRtnClientNoCtrl 返回值（客户编号）对应的控件名
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 */
	
	public static void createPayBalanceAccountCtrl(
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
		String[] strDisplayNames = { URLEncoder.encode("账户编号"), URLEncoder.encode("账户名称"), URLEncoder.encode("账户可用余额")};
		String[] strDisplayFields = { "AccountNo", "AccountName", "mBalance" };
		int nIndexAccount = 0;
		String strSQL = "";
		if (strClientCtrl != null && !strClientCtrl.trim().equals(""))
		{
			//modified by fxzhang 2006-12-26
//			strSQL =
//				"getAccountSQL("
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
			//modified by fxzhang 2006-12-26
//			strSQL =
//				"getAccountSQL("
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
			//			if (strAccountNo != null && strAccountNo.length() < 5)
			//			{
			//				strAccountNo = "";
			//			}
			//			else
			//			{
			//				strAccountNo = strAccountNo + "--";
			//			}
		}
		try
		{
			SETTMagnifier.showSpecialZoomCtrl(
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
	 * 创建账户放大镜(增加账户的当前可用余额)
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
	 * 	特殊的，利息费用支付，请传入-100（只显示两种信托和委托两种）；）
	 * @param lReceiveOrPay 收付类型（-1000特殊的，现实所有状态的账户）
	 * @param strClientCtrl 客户ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnClientIDCtrl 返回值（客户ID）对应的控件名
	 * @param strRtnClientNoCtrl 返回值（客户编号）对应的控件名
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 */
	
	public static void createPayBalanceAccountCtrl1(
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
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnClientIDCtrl, strRtnClientNoCtrl, strRtnClientNameCtrl,"lExtClientIDCtrl","strExtClientName","strRemitInProvince","strRemitInCity","strRemitInBank"};
		
		String[] strMainFields = { "ANo", "ClID", "ClNo", "ClN","o1","o2","p","c","o3" };
		
		String[] strReturnNames = { strCtrlName, strCtrlName + "ClientID" };
		String[] strReturnFields = { "AID", "ClID" };
		String[] strReturnValues = { String.valueOf(lAccountID), String.valueOf(lClientID)};
		Log.print("SETTConstant.ReceiveOrPay.PAY ==========" + SETTConstant.ReceiveOrPay.PAY);
		String[] strDisplayNames = { URLEncoder.encode("账户编号"), URLEncoder.encode("账户名称"), URLEncoder.encode("账户可用余额")};
		String[] strDisplayFields = { "ANo", "AN", "mB" };
		int nIndexAccount = 0;
		String strSQL = "";
		if (strClientCtrl != null && !strClientCtrl.trim().equals(""))
		{
			strSQL =
				"getAccountSQL1("
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
				"getAccountSQL1("
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
			//			if (strAccountNo != null && strAccountNo.length() < 5)
			//			{
			//				strAccountNo = "";
			//			}
			//			else
			//			{
			//				strAccountNo = strAccountNo + "--";
			//			}
		}
		try
		{
			SETTMagnifier.showSpecialZoomCtrl(
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
	 * 创建旧账户放大镜(增加账户的当前可用余额)
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
	 * 	特殊的，利息费用支付，请传入-100（只显示两种信托和委托两种）；）
	 * @param lReceiveOrPay 收付类型（-1000特殊的，现实所有状态的账户）
	 * @param strClientCtrl 客户ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnClientIDCtrl 返回值（客户ID）对应的控件名
	 * @param strRtnClientNoCtrl 返回值（客户编号）对应的控件名
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 */
	
	public static void createPayBalanceOldAccountCtrl(
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
		String[] strDisplayNames = { URLEncoder.encode("账户编号"), URLEncoder.encode("旧账户号"),URLEncoder.encode("账户名称"), URLEncoder.encode("账户可用余额")};
		String[] strDisplayFields = { "AccountNo","OldAccount", "AccountName", "mBalance" };
		int nIndexAccount = 0;
		String strSQL = "";
		if (strClientCtrl != null && !strClientCtrl.trim().equals(""))
		{
			strSQL =
				"getOldAccountSQL("
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
				"getOldAccountSQL("
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
			//			if (strAccountNo != null && strAccountNo.length() < 5)
			//			{
			//				strAccountNo = "";
			//			}
			//			else
			//			{
			//				strAccountNo = strAccountNo + "--";
			//			}
		}
		try
		{
			SETTMagnifier.showSpecialZoomCtrl(
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
	 * 创建担保合同放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lClientID 客户ID(初识值)
	 * @param lConsignAccountID 客户ID(初识值)
	 * @param lContractID 合同ID(初识值)
	 * @param strContractNo 合同号(初识值)
	 * @param lTransactionType 贷款类型
	 * (查询条件:由LOANConstant.LoanType定义，-1 查询所有)
	 * @param lMagnifierType 合同放大境类型
	 * (查询条件: 1 未执行;2 可发放;)
	 * @param strClientCtrl 客户ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnContractTypeCtrl 返回值（合同类型）对应的控件名
	 */
	public static void createSettAssureContractCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lClientID,
		long lConsignAccountID,
		long lContractID,
		String strContractNo,
		long lLoanType,
		long lMagnifierType,
		String strClientCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnContractTypeCtrl1,
		String strRtnContractTypeCtrl2,
		String strRtnContractTypeCtrl3,
		String strRtnContractTypeCtrl4,
		String strRtnContractTypeCtrl5,
		String strRtnContractTypeCtrl6,
		String strRtnContractTypeCtrl7,
		String strRtnContractTypeCtrl8,
		String strRtnContractTypeCtrl9,
		String strRtnContractTypeCtrl10,
		String strRtnContractTypeCtrl11,
		String strRtnContractTypeCtrl12
	)
	{
		String strMagnifierName = URLEncoder.encode("担保合同");
		String strMainProperty = " maxlength='30' value='" + strContractNo + "'";
		String strPrefix = "";
		if (strRtnContractTypeCtrl1 == null || strRtnContractTypeCtrl1.equals(""))
		{
			strRtnContractTypeCtrl1 = "ItIsNotControl";
		}
		if (strRtnContractTypeCtrl2 == null || strRtnContractTypeCtrl2.equals(""))
		{
			strRtnContractTypeCtrl2 = "ItIsNotControl";
		}
		if (strRtnContractTypeCtrl3 == null || strRtnContractTypeCtrl3.equals(""))
		{
			strRtnContractTypeCtrl3 = "ItIsNotControl";
		}
		if (strRtnContractTypeCtrl4 == null || strRtnContractTypeCtrl4.equals(""))
		{
			strRtnContractTypeCtrl4 = "ItIsNotControl";
		}
		if (strRtnContractTypeCtrl5 == null || strRtnContractTypeCtrl5.equals(""))
		{
			strRtnContractTypeCtrl5 = "ItIsNotControl";
		}
		if (strRtnContractTypeCtrl6 == null || strRtnContractTypeCtrl6.equals(""))
		{
			strRtnContractTypeCtrl6 = "ItIsNotControl";
		}
		if (strRtnContractTypeCtrl7 == null || strRtnContractTypeCtrl7.equals(""))
		{
			strRtnContractTypeCtrl7 = "ItIsNotControl";
		}
		if (strRtnContractTypeCtrl8 == null || strRtnContractTypeCtrl8.equals(""))
		{
			strRtnContractTypeCtrl8 = "ItIsNotControl";
		}
		if (strRtnContractTypeCtrl9 == null || strRtnContractTypeCtrl9.equals(""))
		{
			strRtnContractTypeCtrl9 = "ItIsNotControl";
		}
		if (strRtnContractTypeCtrl10 == null || strRtnContractTypeCtrl10.equals(""))
		{
			strRtnContractTypeCtrl10 = "ItIsNotControl";
		}
		if (strRtnContractTypeCtrl11 == null || strRtnContractTypeCtrl11.equals(""))
		{
			strRtnContractTypeCtrl11 = "ItIsNotControl";
		}
		if (strRtnContractTypeCtrl12 == null || strRtnContractTypeCtrl12.equals(""))
		{
			strRtnContractTypeCtrl12 = "ItIsNotControl";
		}

		String[] strMainNames = { strCtrlName + "Ctrl",strRtnContractTypeCtrl1,strRtnContractTypeCtrl2,strRtnContractTypeCtrl3,strRtnContractTypeCtrl4,strRtnContractTypeCtrl5,strRtnContractTypeCtrl6,strRtnContractTypeCtrl7,strRtnContractTypeCtrl8,strRtnContractTypeCtrl9,strRtnContractTypeCtrl10};//需要返回的控件的name
		String[] strMainFields = { "b",              "d", "e",   "g",       "h",     "startDate",             "i",         "endDate",              "j",           "k",             "amount"        };//返回的字段
		String[] strReturnNames = { strCtrlName, strRtnContractTypeCtrl11 ,strRtnContractTypeCtrl12};//需要返回的隐含控件
		String[] strReturnFields = { "a", "c" ,"f"};//需要返回的隐含控件的字段
		String[] strReturnValues = { String.valueOf(lContractID), String.valueOf(lClientID),String.valueOf(lConsignAccountID)};
		String[] strDisplayNames = { URLEncoder.encode("合同号"), URLEncoder.encode("客户名称")};//放大镜上的显示文字
		String[] strDisplayFields = { "b", "e"};//放大镜上的显示字段
		
		int nIndex = 0;
		long[] lContractTypeIDs = null; 
		if (lLoanType == -1)
		{
			lContractTypeIDs = LOANConstant.LoanType.getAllCode(lOfficeID,lCurrencyID);
		}
		else
		{ 
			lContractTypeIDs = new long[] { lLoanType };
		}
		long[] lStatusIDs = null;
		if (lMagnifierType == -1)
		{
			lStatusIDs = LOANConstant.ContractStatus.getAllCode(lOfficeID,lCurrencyID);
		}
		//未发放
		else
			if (lMagnifierType == 1)
			{
				lStatusIDs = new long[] { LOANConstant.ContractStatus.NOTACTIVE };
			}
		//已发放
		else
			if (lMagnifierType == 2)
			{
				lStatusIDs = new long[] {  LOANConstant.ContractStatus.ACTIVE, LOANConstant.ContractStatus.EXTEND };
			}
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getContractAssureSQL(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		sbSQL.append("'");
		if (lContractTypeIDs != null && lContractTypeIDs.length > 0)
		{
			for (int i = 0; i < lContractTypeIDs.length; i++)
			{
				sbSQL.append(lContractTypeIDs[i]);
				if (i < lContractTypeIDs.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append("'");
		if (lStatusIDs != null && lStatusIDs.length > 0)
		{
			for (int i = 0; i < lStatusIDs.length; i++)
			{
				sbSQL.append(lStatusIDs[i]);
				if (i < lStatusIDs.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value");
		sbSQL.append(",");
		if (strClientCtrl != null && !strClientCtrl.trim().equals(""))
		{
			sbSQL.append(strClientCtrl);
			sbSQL.append(".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(")");
		Log.print(sbSQL.toString());
		try
		{
			SETTMagnifier.showZoomCtrl(
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
			Log.print("担保合同放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	/**
	 * 创建担保合同放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lClientID 客户ID(初识值)
	 * @param lConsignAccountID 客户ID(初识值)
	 * @param lContractID 合同ID(初识值)
	 * @param strContractNo 合同号(初识值)
	 * @param lTransactionType 贷款类型
	 * (查询条件:由LOANConstant.LoanType定义，-1 查询所有)
	 * @param lMagnifierType 合同放大境类型
	 * (查询条件: 1 未发放;2 可发放;)
	 * @param strClientCtrl 客户ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnContractTypeCtrl 返回值（合同类型）对应的控件名
	 */
	public static void createSettAssureContractCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lClientID,
		long lConsignAccountID,
		long lContractID,
		String strContractNo,
		long lLoanType,
		long lMagnifierType,
		String strClientCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnContractTypeCtrl1,
		String strRtnContractTypeCtrl2,
		String strRtnContractTypeCtrl3,
		String strRtnContractTypeCtrl4,
		String strRtnContractTypeCtrl5,
		String strRtnContractTypeCtrl6,
		String strRtnContractTypeCtrl7,
		String strRtnContractTypeCtrl8,
		String strRtnContractTypeCtrl9,
		String strRtnContractTypeCtrl10,
		String strRtnContractTypeCtrl11,
		String strRtnContractTypeCtrl12,
		String strRtnContractTypeCtrl13
	)
	{
		String strMagnifierName = URLEncoder.encode("担保合同");
		String strMainProperty = " maxlength='30' value='" + strContractNo + "'";
		String strPrefix = "";
		if (strRtnContractTypeCtrl1 == null || strRtnContractTypeCtrl1.equals(""))
		{
			strRtnContractTypeCtrl1 = "ItIsNotControl";
		}
		if (strRtnContractTypeCtrl2 == null || strRtnContractTypeCtrl2.equals(""))
		{
			strRtnContractTypeCtrl2 = "ItIsNotControl";
		}
		if (strRtnContractTypeCtrl3 == null || strRtnContractTypeCtrl3.equals(""))
		{
			strRtnContractTypeCtrl3 = "ItIsNotControl";
		}
		if (strRtnContractTypeCtrl4 == null || strRtnContractTypeCtrl4.equals(""))
		{
			strRtnContractTypeCtrl4 = "ItIsNotControl";
		}
		if (strRtnContractTypeCtrl5 == null || strRtnContractTypeCtrl5.equals(""))
		{
			strRtnContractTypeCtrl5 = "ItIsNotControl";
		}
		if (strRtnContractTypeCtrl6 == null || strRtnContractTypeCtrl6.equals(""))
		{
			strRtnContractTypeCtrl6 = "ItIsNotControl";
		}
		if (strRtnContractTypeCtrl7 == null || strRtnContractTypeCtrl7.equals(""))
		{
			strRtnContractTypeCtrl7 = "ItIsNotControl";
		}
		if (strRtnContractTypeCtrl8 == null || strRtnContractTypeCtrl8.equals(""))
		{
			strRtnContractTypeCtrl8 = "ItIsNotControl";
		}
		if (strRtnContractTypeCtrl9 == null || strRtnContractTypeCtrl9.equals(""))
		{
			strRtnContractTypeCtrl9 = "ItIsNotControl";
		}
		if (strRtnContractTypeCtrl10 == null || strRtnContractTypeCtrl10.equals(""))
		{
			strRtnContractTypeCtrl10 = "ItIsNotControl";
		}
		if (strRtnContractTypeCtrl11 == null || strRtnContractTypeCtrl11.equals(""))
		{
			strRtnContractTypeCtrl11 = "ItIsNotControl";
		}
		if (strRtnContractTypeCtrl12 == null || strRtnContractTypeCtrl12.equals(""))
		{
			strRtnContractTypeCtrl12 = "ItIsNotControl";
		}
		if (strRtnContractTypeCtrl13 == null || strRtnContractTypeCtrl13.equals(""))
		{
			strRtnContractTypeCtrl13 = "ItIsNotControl";
		}

		String[] strMainNames = { strCtrlName + "Ctrl",strRtnContractTypeCtrl1,strRtnContractTypeCtrl2,strRtnContractTypeCtrl3,strRtnContractTypeCtrl4,strRtnContractTypeCtrl5,strRtnContractTypeCtrl6,strRtnContractTypeCtrl7,strRtnContractTypeCtrl8,strRtnContractTypeCtrl9,strRtnContractTypeCtrl10,strRtnContractTypeCtrl13};//需要返回的控件的name
		String[] strMainFields = { "b",              "d", "e",   "g",       "h",     "startDate",             "i",         "endDate",              "j",           "k",             "amount"     ,        "hm"   };//返回的字段
		String[] strReturnNames = { strCtrlName, strRtnContractTypeCtrl11 ,strRtnContractTypeCtrl12};//需要返回的隐含控件
		String[] strReturnFields = { "a", "c" ,"f"};//需要返回的隐含控件的字段
		String[] strReturnValues = { String.valueOf(lContractID), String.valueOf(lClientID),String.valueOf(lConsignAccountID)};
		String[] strDisplayNames = { URLEncoder.encode("合同号"), URLEncoder.encode("客户名称")};//放大镜上的显示文字
		String[] strDisplayFields = { "b", "e"};//放大镜上的显示字段
		int nIndex = 0;
		long[] lContractTypeIDs = null; 
		if (lLoanType == -1)
		{
			lContractTypeIDs = LOANConstant.LoanType.getAllCode(lOfficeID,lCurrencyID);
		}
		else
		{ 
			lContractTypeIDs = new long[] { lLoanType };
		}
		long[] lStatusIDs = null;
		if (lMagnifierType == -1)
		{
			lStatusIDs = LOANConstant.ContractStatus.getAllCode();
		}
		//未发放
		else
			if (lMagnifierType == 1)
			{
				lStatusIDs = new long[] { LOANConstant.ContractStatus.NOTACTIVE };
			}
		//已发放
		else
			if (lMagnifierType == 2)
			{
				lStatusIDs = new long[] {  LOANConstant.ContractStatus.ACTIVE, LOANConstant.ContractStatus.EXTEND };
			}
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getContractAssureSQL(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		sbSQL.append("'");
		if (lContractTypeIDs != null && lContractTypeIDs.length > 0)
		{
			for (int i = 0; i < lContractTypeIDs.length; i++)
			{
				sbSQL.append(lContractTypeIDs[i]);
				if (i < lContractTypeIDs.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append("'");
		if (lStatusIDs != null && lStatusIDs.length > 0)
		{
			for (int i = 0; i < lStatusIDs.length; i++)
			{
				sbSQL.append(lStatusIDs[i]);
				if (i < lStatusIDs.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value");
		sbSQL.append(",");
		if (strClientCtrl != null && !strClientCtrl.trim().equals(""))
		{
			sbSQL.append(strClientCtrl);
			sbSQL.append(".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(")");
		Log.print(sbSQL.toString());
		try
		{
			SETTMagnifier.showZoomCtrl(
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
			Log.print("担保合同放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	/**
	 * 创建担保合同放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lClientID 客户ID(初识值)
	 * @param lConsignAccountID 客户ID(初识值)
	 * @param lContractID 合同ID(初识值)
	 * @param strContractNo 合同号(初识值)
	 * @param lTransactionType 贷款类型
	 * (查询条件:由LOANConstant.LoanType定义，-1 查询所有)
	 * @param lMagnifierType 合同放大境类型
	 * (查询条件: 1 未发放;2 可发放;)
	 * @param strClientCtrl 客户ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnContractTypeCtrl 返回值（合同类型）对应的控件名
	 */
	public static void createSettAssureContractCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lClientID,
		long lConsignAccountID,
		long lContractID,
		String strContractNo,
		long lLoanType,
		long lMagnifierType,
		String strClientCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnContractTypeCtrl1,
		String strRtnContractTypeCtrl2,
		String strRtnContractTypeCtrl3,
		String strRtnContractTypeCtrl4,
		String strRtnContractTypeCtrl5,
		String strRtnContractTypeCtrl6,
		String strRtnContractTypeCtrl7,
		String strRtnContractTypeCtrl8,
		String strRtnContractTypeCtrl9,
		String strRtnContractTypeCtrl10,
		String strRtnContractTypeCtrl11,
		String strRtnContractTypeCtrl12,
		String strRtnContractTypeCtrl13,
		String strRtnContractTypeCtrl14
	)
	{
		String strMagnifierName = URLEncoder.encode("担保合同");
		String strMainProperty = " maxlength='30' value='" + strContractNo + "'";
		String strPrefix = "";
		if (strRtnContractTypeCtrl1 == null || strRtnContractTypeCtrl1.equals(""))
		{
			strRtnContractTypeCtrl1 = "ItIsNotControl";
		}
		if (strRtnContractTypeCtrl2 == null || strRtnContractTypeCtrl2.equals(""))
		{
			strRtnContractTypeCtrl2 = "ItIsNotControl";
		}
		if (strRtnContractTypeCtrl3 == null || strRtnContractTypeCtrl3.equals(""))
		{
			strRtnContractTypeCtrl3 = "ItIsNotControl";
		}
		if (strRtnContractTypeCtrl4 == null || strRtnContractTypeCtrl4.equals(""))
		{
			strRtnContractTypeCtrl4 = "ItIsNotControl";
		}
		if (strRtnContractTypeCtrl5 == null || strRtnContractTypeCtrl5.equals(""))
		{
			strRtnContractTypeCtrl5 = "ItIsNotControl";
		}
		if (strRtnContractTypeCtrl6 == null || strRtnContractTypeCtrl6.equals(""))
		{
			strRtnContractTypeCtrl6 = "ItIsNotControl";
		}
		if (strRtnContractTypeCtrl7 == null || strRtnContractTypeCtrl7.equals(""))
		{
			strRtnContractTypeCtrl7 = "ItIsNotControl";
		}
		if (strRtnContractTypeCtrl8 == null || strRtnContractTypeCtrl8.equals(""))
		{
			strRtnContractTypeCtrl8 = "ItIsNotControl";
		}
		if (strRtnContractTypeCtrl9 == null || strRtnContractTypeCtrl9.equals(""))
		{
			strRtnContractTypeCtrl9 = "ItIsNotControl";
		}
		if (strRtnContractTypeCtrl10 == null || strRtnContractTypeCtrl10.equals(""))
		{
			strRtnContractTypeCtrl10 = "ItIsNotControl";
		}
		if (strRtnContractTypeCtrl11 == null || strRtnContractTypeCtrl11.equals(""))
		{
			strRtnContractTypeCtrl11 = "ItIsNotControl";
		}
		if (strRtnContractTypeCtrl12 == null || strRtnContractTypeCtrl12.equals(""))
		{
			strRtnContractTypeCtrl12 = "ItIsNotControl";
		}
		if (strRtnContractTypeCtrl13 == null || strRtnContractTypeCtrl13.equals(""))
		{
			strRtnContractTypeCtrl13 = "ItIsNotControl";
		}
		if (strRtnContractTypeCtrl14 == null || strRtnContractTypeCtrl14.equals(""))
		{
			strRtnContractTypeCtrl14 = "ItIsNotControl";
		}

		String[] strMainNames = { strCtrlName + "Ctrl", strRtnContractTypeCtrl11 ,strRtnContractTypeCtrl12,strRtnContractTypeCtrl1,strRtnContractTypeCtrl2,strRtnContractTypeCtrl3,strRtnContractTypeCtrl4,strRtnContractTypeCtrl5,strRtnContractTypeCtrl6,strRtnContractTypeCtrl7,strRtnContractTypeCtrl8,strRtnContractTypeCtrl9,strRtnContractTypeCtrl10,strRtnContractTypeCtrl13,strRtnContractTypeCtrl14};//需要返回的控件的name
		String[] strMainFields = { "b",  "c" ,"f",         "d", "e",   "g",       "h",     "startDate",             "i",         "endDate",              "j",           "k",             "amount"     ,        "hm"  ,             "hn" };//返回的字段
		String[] strReturnNames = { strCtrlName};//需要返回的隐含控件
		String[] strReturnFields = { "a"};//需要返回的隐含控件的字段
		String[] strReturnValues = { String.valueOf(lContractID)};
		String[] strDisplayNames = { URLEncoder.encode("合同号"), URLEncoder.encode("客户名称")};//放大镜上的显示文字
		String[] strDisplayFields = { "b", "e"};//放大镜上的显示字段
		int nIndex = 0;
		long[] lContractTypeIDs = null; 
		if (lLoanType == -1)
		{
			lContractTypeIDs = LOANConstant.LoanType.getAllCode(lOfficeID,lCurrencyID);
		}
		else
		{ 
			lContractTypeIDs = new long[] { lLoanType };
		}
		long[] lStatusIDs = null;
		if (lMagnifierType == -1)
		{
			lStatusIDs = LOANConstant.ContractStatus.getAllCode(lOfficeID,lCurrencyID);
		}
		//未发放
		else
			if (lMagnifierType == 1)
			{
				lStatusIDs = new long[] { LOANConstant.ContractStatus.NOTACTIVE };
			}
		//已发放
		else
			if (lMagnifierType == 2)
			{
				lStatusIDs = new long[] {  LOANConstant.ContractStatus.ACTIVE, LOANConstant.ContractStatus.EXTEND };
			}
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getContractAssureSQL(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		sbSQL.append("'");
		if (lContractTypeIDs != null && lContractTypeIDs.length > 0)
		{
			for (int i = 0; i < lContractTypeIDs.length; i++)
			{
				sbSQL.append(lContractTypeIDs[i]);
				if (i < lContractTypeIDs.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append("'");
		if (lStatusIDs != null && lStatusIDs.length > 0)
		{
			for (int i = 0; i < lStatusIDs.length; i++)
			{
				sbSQL.append(lStatusIDs[i]);
				if (i < lStatusIDs.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value");
		sbSQL.append(",");
		if (strClientCtrl != null && !strClientCtrl.trim().equals(""))
		{
			sbSQL.append(strClientCtrl);
			sbSQL.append(".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(")");
		Log.print(sbSQL.toString());
		try
		{
			SETTMagnifier.showZoomCtrl(
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
			Log.print("担保合同放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	/**
	 * 创建贷款未收利息合同放大镜(贷款未收利息类表外业务用)
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lClientID 客户ID(初识值)
	 * @param lContractID 合同ID(初识值)
	 * @param strContractNo 合同号(初识值)
	 * @param lTransactionType 交易类型(自营,委托贷款)	
	 * @param lMagnifierType 合同放大境类型	
	 * @param strClientCtrl 客户ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnContractTypeCtrl 返回值（合同类型）对应的控件名
	 * @param strRtnClientIDCtrl 返回值（借款客户ID）对应的控件名(一般是隐含控件)
	 * @param strRtnClientCodeCtrl 返回值（借款客户编号）对应的控件名
	 * @param strRtnClientNameCtrl 返回值（借款客户名称）对应的控件名
	 * @param strRtnConsignClientIDCtrl 返回值（委托贷款客户ID）对应的控件名(委托贷款时)
	 * @param strRtnConsignClientCodeCtrl 返回值（委托贷款客户编号）对应的控件名
	 * @param strRtnConsignClientNameCtrl 返回值（委托贷款客户名称）对应的控件名
	 */
	public static void createConsignContractCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lClientID,
		long lContractID,
		long lConsignClientID,
		String strContractNo,
		long lTransactionType,
		long lMagnifierType,
		String strClientCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnContractTypeCtrl,
		String strRtnClientIDCtrl,
		String strRtnClientCodeCtrl,
		String strRtnClientNameCtrl,
		String strRtnConsignClientIDCtrl,
		String strRtnConsignClientCodeCtrl,
		String strRtnConsignClientNameCtrl)
	{
		String strMagnifierName = URLEncoder.encode("合同");
		String strMainProperty = " maxlength='30' value='" + strContractNo + "'";
		String strPrefix = "";
		
		if (strRtnContractTypeCtrl == null || strRtnContractTypeCtrl.equals(""))
		{
			strRtnContractTypeCtrl = "ItIsNotControl";
		}		
		if (strRtnClientIDCtrl == null || strRtnClientIDCtrl.equals(""))
		{
			strRtnClientIDCtrl = "ItIsNotControl";
		}
		if (strRtnClientCodeCtrl == null || strRtnClientCodeCtrl.equals(""))
		{
			strRtnClientCodeCtrl = "ItIsNotControl";
		}
		if (strRtnClientNameCtrl == null || strRtnClientNameCtrl.equals(""))
		{
			strRtnClientNameCtrl = "ItIsNotControl";
		}		
		if (strRtnConsignClientIDCtrl == null || strRtnConsignClientIDCtrl.equals(""))
		{
		    strRtnConsignClientIDCtrl = "ItIsNotControl";
		}
		if (strRtnConsignClientCodeCtrl == null || strRtnConsignClientCodeCtrl.equals(""))
		{
		    strRtnConsignClientCodeCtrl = "ItIsNotControl";
		}
		if (strRtnConsignClientNameCtrl == null || strRtnConsignClientNameCtrl.equals(""))
		{
		    strRtnConsignClientNameCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnContractTypeCtrl,strRtnClientCodeCtrl,strRtnClientNameCtrl,strRtnConsignClientCodeCtrl,strRtnConsignClientNameCtrl};
		String[] strMainFields = { "ContractCode", "ContractType","ClientCode","ClientName","ConsignClientCode","ConsignClientName"};
		String[] strReturnNames = { strCtrlName, strRtnClientIDCtrl, strRtnConsignClientIDCtrl };
		String[] strReturnFields = { "ContractID", "ClientID","ConsignClientID",};
		String[] strReturnValues = { String.valueOf(lContractID), String.valueOf(lClientID),String.valueOf(lConsignClientID)};
		String[] strDisplayNames = { URLEncoder.encode("合同号"), URLEncoder.encode("客户名称")};
		String[] strDisplayFields = { "ContractCode", "ClientName" };
		int nIndex = 0;
		long[] lContractTypeIDs = null;
			
	    //(自营和委托)贷款未收利息类表外业务	   
	 	if (lTransactionType == SETTConstant.TransactionType.CONSIGN_INCOME_OFFBALANCE || lTransactionType == SETTConstant.TransactionType.CONSIGN_PAYOUT_OFFBALANCE)
	 	{
	 	   lContractTypeIDs = new long[] { 
	 	           LOANConstant.LoanType.ZY, //自营短期贷款
	 	           LOANConstant.LoanType.WT, //委托贷款
	 	           LOANConstant.LoanType.ZGXE, //最高限额短期
	 	           LOANConstant.LoanType.YT //银团短期贷款 
	 	   								};
	 	}
		else			
			lContractTypeIDs = LOANConstant.LoanType.getAllCode(lOfficeID,lCurrencyID);
			
		long[] lStatusIDs = null;
		if (lMagnifierType == 1)
		{
		    lStatusIDs = new long[] { 
			        LOANConstant.ContractStatus.ACTIVE, //执行中 
			        LOANConstant.ContractStatus.EXTEND, //已展期
			        LOANConstant.ContractStatus.OVERDUE //已逾期
			        };			
		}		
		else			
			lStatusIDs = LOANConstant.ContractStatus.getAllCode();
							
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getConsignContractSQL(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		sbSQL.append("'");
		if (lContractTypeIDs != null && lContractTypeIDs.length > 0)
		{
			for (int i = 0; i < lContractTypeIDs.length; i++)
			{
				sbSQL.append(lContractTypeIDs[i]);
				if (i < lContractTypeIDs.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append("'");
		if (lStatusIDs != null && lStatusIDs.length > 0)
		{
			for (int i = 0; i < lStatusIDs.length; i++)
			{
				sbSQL.append(lStatusIDs[i]);
				if (i < lStatusIDs.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value");
		sbSQL.append(",");
		if (strClientCtrl != null && !strClientCtrl.trim().equals(""))
		{
			sbSQL.append(strClientCtrl);
			sbSQL.append(".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		//转贴现卖出（买断）
		if (lMagnifierType == 3)
		{
			sbSQL.append("," + LOANConstant.TransDiscountInOrOut.OUT + "," + LOANConstant.TransDiscountType.BUYBREAK + ")");
		}
		else
		{
			sbSQL.append(")");
		}
		
		Log.print(sbSQL.toString());
		try
		{
			SETTMagnifier.showZoomCtrl(
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
			e.printStackTrace();
			Log.print("贷款未收利息合同放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}	
	/**
	 * 创建放款通知单放大镜(贷款未收利息类表外业务用)
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lContractID 合同ID(初识值)
	 * @param lPayFormID 放款通知单ID(初识值)
	 * @param strPayFormNo 放款通知单号(初识值)
	 * @param lPayFormTypeID 放款通知单类型
	 * (查询条件:1,信托；2，委托；3，银团；0,全部)
	 * @param lTypeID 放款通知单使用条件(内部状态：
	 * 		0，显示全部；
	 * 		1，信托/委托/银团发放——业务处理使用；
	 * 		2，信托/委托/银团发放——业务复核使用；
	 * 		3，信托/委托收回——业务处理使用；
	 * 		4，信托/委托收回——业务复核使用；
	 * 		5，交易费用/特殊业务——业务处理使用；
	 * 		6，交易费用/特殊业务——业务复核使用；
	 * 		7，银团贷款收回——业务处理使用；
	 * 		8，银团贷款收回——业务复核使用；）
	 * @param strContractCtrl 合同ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnStartDateCtrl 返回值（起始日期）对应的控件名
	 * @param strRtnEndDateCtrl 返回值（到期日期）对应的控件名
	 * @param strRtnSubAccountIDCtrl 返回值（子账户ID）对应的控件名
	 * @param strRtnBalanceCtrl 返回值（放款单余额）对应的控件名
	 * @param strRtnIsHasFreeCtrl 返回值（是否有免还通知单：1，是；2，否）对应的控件名
	 */
	public static void createConsignPayFormCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lContractID,
		long lPayFormID,
		String strPayFormNo,
		long lPayFormTypeID,
		long lTypeID,
		String strContractCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnLoanAccountIDCtrl,
		String strRtnLoanAccountCtrl,
		String strRtnConsignAccountIDCtrl,
		String strRtnConsignAccountCtrl)
	{
		String strMagnifierName = URLEncoder.encode("放款通知单");
		String strMainProperty = " maxlength='30' value='" + strPayFormNo + "'";
		String strPrefix = "";
		if (strRtnLoanAccountIDCtrl == null || strRtnLoanAccountIDCtrl.equals(""))
		{
		    strRtnLoanAccountIDCtrl = "NCTL";
		}
		if (strRtnLoanAccountCtrl == null || strRtnLoanAccountCtrl.equals(""))
		{
		    strRtnLoanAccountCtrl = "NCTL";
		}
		if (strRtnConsignAccountIDCtrl == null || strRtnConsignAccountIDCtrl.equals(""))
		{
		    strRtnConsignAccountIDCtrl = "NCTL";
		}
		if (strRtnConsignAccountCtrl == null || strRtnConsignAccountCtrl.equals(""))
		{
		    strRtnConsignAccountCtrl = "NCTL";
		}		
		String[] strMainNames = { strCtrlName + "Ctrl",  strRtnLoanAccountCtrl,  strRtnConsignAccountCtrl , strRtnConsignAccountIDCtrl};
		String[] strMainFields = { "PayFormCode", "loanAccount", "consignAccount" , "consignAccountID"};
		String[] strReturnNames = { strCtrlName, strCtrlName + "ContractID", strRtnLoanAccountIDCtrl };
		String[] strReturnFields = { "PayFormID", "ContractID", "loanAccountID" };
		String[] strReturnValues = { String.valueOf(lPayFormID), String.valueOf(lContractID), "-1" };
		String[] strDisplayNames = { URLEncoder.encode("放款通知单号"), URLEncoder.encode("起始日期"), URLEncoder.encode("放款日期")};
		String[] strDisplayFields = { "PayFormCode", "StartDate", "PayDate" };	
		
		int nIndex = 0;
		long[] lContractTypeIDs = null;
		
		if (lPayFormTypeID == SETTConstant.TransactionType.CONSIGN_INCOME_OFFBALANCE || lPayFormTypeID == SETTConstant.TransactionType.CONSIGN_PAYOUT_OFFBALANCE)
	 	{
			lContractTypeIDs =
				new long[] {
			         LOANConstant.LoanType.ZY, 
			         LOANConstant.LoanType.WT };				 	
		}		
		else			
			{				
			}
		long[] lStatusIDArray = null;
		//获得状态数组
		if (lTypeID == 0)
		{
			lStatusIDArray = new long[] { LOANConstant.LoanPayNoticeStatus.CHECK, LOANConstant.LoanPayNoticeStatus.USED };
		}
		else
			if (lTypeID == 1)
			{
				lStatusIDArray = new long[] { LOANConstant.LoanPayNoticeStatus.CHECK };
			}
			else
				if (lTypeID == 2)
				{
					lStatusIDArray = new long[] { LOANConstant.LoanPayNoticeStatus.USED };
				}
				else
					if (lTypeID == 3)
					{
						lStatusIDArray = new long[] { -100 };
					}
					else
						if (lTypeID == 4)
						{
							lStatusIDArray = new long[] { -200 };
						}
						else
							if (lTypeID == 5)
							{
								lStatusIDArray = new long[] { -500 };
							}
							else
								if (lTypeID == 6)
								{
									lStatusIDArray = new long[] { -600 };
								}
								else
									if (lTypeID == 7)
									{
										lStatusIDArray = new long[] { -700 };
									}
									else
										if (lTypeID == 8)
										{
											lStatusIDArray = new long[] { -800 };
										}
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getConsignPayFormSQL(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		if (strContractCtrl != null && !strContractCtrl.trim().equals(""))
		{
			sbSQL.append(strContractCtrl + ".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(",");
		sbSQL.append("'");
		if (lContractTypeIDs != null && lContractTypeIDs.length > 0)
		{
			for (int i = 0; i < lContractTypeIDs.length; i++)
			{
				sbSQL.append(lContractTypeIDs[i]);
				if (i < lContractTypeIDs.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append("'");
		if (lStatusIDArray != null && lStatusIDArray.length > 0)
		{
			for (int i = 0; i < lStatusIDArray.length; i++)
			{
				sbSQL.append(lStatusIDArray[i]);
				if (i < lStatusIDArray.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value)");
		
		Log.print(sbSQL.toString());
		try
		{
			SETTMagnifier.showZoomCtrl(
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
	 * 创建股份公司分析项目放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lProjectID 分析项目ID(初识值)
	 * @param strProjectName 分析项目(初识值)
	 * @param lProjectType 
	 * 			-1	所有的分析项目 
	 * 			1	资产负债及表外项目设置 
	 * 			2	损益表项目设置
	 * @param lHaveItems 是否要求该项目名称有对应的设置项 1 是，0 否 
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 */
	public static void createStockProjectNameCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lProjectID,
		String strProjectName,
		long lProjectType,
		long lHaveItems,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls)
	{
		String strMagnifierName = URLEncoder.encode("分析项目");
		String strMainProperty = " size='30' value='" + strProjectName + "'";
		
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl" };
		String[] strMainFields = { "projectName" };
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "ID" };
		String[] strReturnValues = { String.valueOf(lProjectID)};
		String[] strDisplayNames = { URLEncoder.encode("项目ID"), URLEncoder.encode("项目名称")};
		String[] strDisplayFields = { "ID", "projectName" };
		int nIndex = 1;
		String strSQL = "getStockProjectNameSQL(" + lOfficeID + "," + lCurrencyID + "," + lProjectType + "," + lHaveItems + "," + strFormName+"."+ strCtrlName + "Ctrl.value" + ")";
		boolean blnIsOptional = true;
		try
		{
			SETTMagnifier.showZoomCtrl(
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
			Log.print("股份公司分析项目放大镜[" + strCtrlName + "]异常：" + e.toString());
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
	 * 显示贷款子类型放大镜
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种
	 * @param out
	 * @param strLoanTypeID 贷款种类
	 * @param strFormName 主页面表单名称
	 * @param strPrefix 控件名称前缀
	 * @param strMainProperty 放大镜栏位属性
	 * @param strSQL 放大镜查询SQL语句
	 * @param strNextControls 设置下一个焦点
	 * @throws Exception
	 */
	public static void CreateSubLoanTypeCtrl(long lOfficeID, long lCurrencyID, JspWriter out, String strLoanTypeID, String strFormName, String strPrefix, String strMainProperty, String strNextControls) throws Exception
	{
		try
		{
			//输出SQL到页面
			out.println("<script language=\"javascript\">");
			out.println("/*====================" + URLEncoder.encode("贷款子类型放大镜") + "=================*/");
			out.println("function " + strPrefix + "getSubLoanTypeSQL(nOfficeID,lLoanTypeID)");
			out.println("{");
			out.println(
				"	var sql = \"select a.ID, a.LoanTypeID, a.Code, a.Name \";");
			out.println("	sql += \" from Loan_LoanTypeSetting a, Loan_LoanTypeRelation b \";");
			out.println("	sql += \" where a.ID = b.subLoanTypeID and a.statusID = 1 \";");
			out.println(" ");
			out.println("   if (nOfficeID > 0)");
			out.println("	{");
			out.println("		sql += \" and b.officeID = \" + nOfficeID; ");
			out.println("	}");
			out.println("	if (lLoanTypeID > 0) ");
			out.println("	{");
			out.println("		sql += \" and a.loanTypeID = \" + lLoanTypeID; ");
			out.println("	}");
			out.println("	return sql;");
			out.println("}");
			out.println("</SCRIPT> ");			
			String strMagnifierName = "贷款子类型";
			String[] strMainNames = { "txtSubLoanTypeName", "txtSubLoanTypeCode", };
			String[] strMainFields = { "Name", "Code" };
			String[] strReturnNames = { "hidSubLoanTypeID","hidLoanTypeID" };
			String[] strReturnFields = { "ID","LoanTypeID" };
			String[] strReturnValues = { "-1","-1" };
			String[] strDisplayNames = { "贷款子类型名称", "贷款子类型编码" };
			String[] strDisplayFields = { "Name", "Code" };
			int nIndexOffice = 0;
			String strSQL = strPrefix + "getSubLoanTypeSQL(" + lOfficeID + ","  + strFormName + "." + strPrefix + strLoanTypeID + ".value)";
						
			showZoomCtrl(
				out,
				strMagnifierName,
				strFormName,
				strPrefix,
				strMainNames,
				strMainFields,
				strReturnNames,
				strReturnFields,
				"",
				strReturnValues,
				strDisplayNames,
				strDisplayFields,
				nIndexOffice,
				strMainProperty,
				strSQL,
				"",
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
	
	/**
	 * 创建资金上收策略放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lPolicyID 上收策略ID(初始值)
	 * @param strPolicyCode 上收策略编号(初始值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnPolicyNameCtrl 返回值（归集策略名称）对应的控件名
	 */
	public static void createUpGatheringPolicyCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lPolicyID,
		String strPolicyCode,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnPolicyNameCtrl)
	{
		String strMagnifierName = URLEncoder.encode("上收策略");
		String strMainProperty = " maxlength='30' value='" + strPolicyCode + "'";
		String strPrefix = "";
		if (strRtnPolicyNameCtrl == null || strRtnPolicyNameCtrl.equals(""))
		{
			strRtnPolicyNameCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnPolicyNameCtrl };
		String[] strMainFields = { "Code", "Name" };
		String[] strReturnNames = { strCtrlName};
		String[] strReturnFields = { "Id"};
		String[] strReturnValues = { String.valueOf(lPolicyID)};
		String[] strDisplayNames = { URLEncoder.encode("归集策略编号"), URLEncoder.encode("归集策略名称")};
		String[] strDisplayFields = { "Code", "Name" };
		int nIndex = 0;
		String strSQL = "getUpGatheringPolicySQL(" + lOfficeID + "," + lCurrencyID + "," + strCtrlName + "Ctrl.value)";
		try
		{
			SETTMagnifier.showZoomCtrl(
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
					+ "/iTreasury-settlement/magnifier/ShowMagnifierZoom.jsp?"
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
					"<td width=150"
						+ strFirstTD
						+ ">"
						+ strTitle
						+ "：&nbsp;"
						+ "<a href=#><img name=\""
						+ strButtonName
						+ "\" src='/websett/image/icon.gif' border=0 onclick=\""
						+ sOnKeydown
						+ "\"></a></td>");
				//image
			}
			else
			{
				out.println("<td " + strFirstTD + ">" + strTitle + "&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/websett/image/icon.gif' border=0 ></a></td>");
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
					out.println("<td" + strSecondTD + "><input type=\"text\" name=\""
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
					+ "/iTreasury-settlement/magnifier/ShowMagnifierZoom.jsp?"
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
//			int accountField = Config.getInteger(ConfigConstant.GLOBAL_ACCOUNTNO_FIELD,4);
//			if (nCaseNumber == 3) {
//				accountField = accountField-1;
//			} else if (nCaseNumber == 4) {
//			}
			int accountField = 4; //modify by bingliu 20110720为了航天科工项目，将所有类型账户都显示4段。
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
						+ "\" src='/websett/image/icon.gif' border=0 onclick=\""
						+ sOnKeydown
						+ "\"></a></td>");
				//image
			}
			else
			{
				out.println("<td " + strFirstTD + ">" + strTitle + "：&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/websett/image/icon.gif' border=0 ></a></td>");
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
				case 1 : //合同类型放大镜--演示用
					Log.print("合同类型放大镜");
					//strReturn = Notes.getCodeName(Notes.CODETYPE_CONTRACT_TYPE,lID);
					break;
				case 2 : //审批设置--模块名称放大镜
					Log.print("模块名称放大镜");
					strReturn = Constant.ModuleType.getName(lID);
					break;
				case 3 : //审批设置--业务类型放大镜
					Log.print("业务类型放大镜放大镜");
					strReturn = Constant.ApprovalLoanType.getName(lID);
					break;
				case 4 : //审批设置--操作放大镜
					Log.print("操作放大镜放大镜");
					strReturn = Constant.ApprovalAction.getName(lID);
					break;
				case 5 : //审批设置--状态
					Log.print("状态放大镜");
					strReturn = Constant.ApprovalStatus.getName(lID);
					break;
				case 6 : //银行票据放大镜（类型描述从常量中得）
					Log.print("银行票据放大镜");
					strReturn = SETTConstant.BankBillType.getName(lID);
					break;
				case 7 : //定期或通知存单号（从定期开立表中取得）
					Log.print("定期或通知存单号");
					//新增编码规则后,定期存单号在点选放大镜的某条记录后再取
					//UtilOperation utiloperation = new UtilOperation();
					//strReturn = utiloperation.getOpenDepositNo(lID);
					break;
				case 8 : //贴现汇票放大镜，汇票类型
					Log.print("贴现汇票放大镜");
					strReturn = LOANConstant.DraftType.getName(lID);
					break;
				case 9 : //贴现汇票放大镜，是否本地
					Log.print("贴现汇票放大镜");
					strReturn = Constant.YesOrNo.getName(lID);
					break;
				case 10 : //贴现汇票放大镜，是否本地
					Log.print("担保合同放大镜");
					strReturn = LOANConstant.AssureType2.getName(lID);
					break;
				case 11 : //贴现汇票放大镜，合同号
					Log.print("贴现汇票放大镜");
					strReturn = NameRef.getContractNoByID(lID);
					break;
				case 12 : //贴现汇票放大镜，贴现凭证号
					Log.print("贴现汇票放大镜");
					strReturn = NameRef.getDiscountCredenceNoByID(lID);
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
	 * 创建银企接口--成员单位账户放大镜，此放大镜返回下属单位名称和对应的科目
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lFilialeAccountID 成员单位账户ID(初识值)
	 * @param strFilialeAccountNo 成员单位账户编号(初识值)
	 * @param lBankTypeID 开户行类型ID(初识值)
	 * @param strBankTypeCtrl 银行类型ID对应的控件名称（查询时关联）
	 * @param lClientID 所属单位ID(初识值)
	 * @param strClientCtrl 所属单位ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnAccountNoCtrl 返回值（账户名称）对应的控件名
	 * @param strRtnAccountSubjectCtrl 返回值（账户科目号）对应的控件名
	 * @param strRtnFilialeNameCtrl 返回之（成员单位名称）对应的控件名
	 * @param strProperty 外加的控件属性，一般传空串即可 
	 */
	public static void createFilialeAccountNoAndSubjectCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lFilialeAccountID,
		String strFilialeAccountNo,
		long lBankTypeID,
		String strBankTypeCtrl,
		long lClinetID,
		String strClinetCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnAccountNameCtrl,
		String strRtnAccountSubjectCtrl,
		String strRtnFilialeNameCtrl,
		String strProperty)
	{
		//放大镜控件非数组
		String strMagnifierName = URLEncoder.encode("银行账户编号");
		String strMainProperty = " maxlength='30' value='" + strFilialeAccountNo + "' ";
		if (strProperty != null && !strProperty.equals(""))
		{
			strMainProperty += strProperty;
		}
		String strPrefix = "";
		if (strRtnAccountNameCtrl == null || strRtnAccountNameCtrl.equals(""))
		{
			strRtnAccountNameCtrl = "ItIsNotControl";
		}
		if (strRtnAccountSubjectCtrl == null || strRtnAccountSubjectCtrl.equals(""))
		{
			strRtnAccountSubjectCtrl = "ItIsNotControl";
		}
		if (strRtnFilialeNameCtrl == null || strRtnFilialeNameCtrl.equals(""))
		{
			strRtnFilialeNameCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnAccountNameCtrl, strRtnAccountSubjectCtrl, strRtnFilialeNameCtrl };
		String[] strMainFields = { "BANKACCOUNTNO", "BANKACCOUNTNAME", "SUBJECT", "FILIALENAME" };
		String[] strReturnNames = { strCtrlName, strCtrlName + "BankTypeID" };
		String[] strReturnFields = { "ID", "BANKTYPE" };
		String[] strReturnValues = { String.valueOf(lFilialeAccountID), String.valueOf(lBankTypeID)};
		String[] strDisplayNames = { URLEncoder.encode("银行账户编号"), URLEncoder.encode("成员单位名称"), URLEncoder.encode("科目")};
		String[] strDisplayFields = { "BANKACCOUNTNO", "FILIALENAME", "SUBJECT"};
		int nIndex = 0;
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getFilialeAccountNoAndSubjectSQL(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		if (strBankTypeCtrl != null && !strBankTypeCtrl.trim().equals(""))
		{
			sbSQL.append(strBankTypeCtrl + ".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(",");
		if (strClinetCtrl != null && !strClinetCtrl.trim().equals(""))
		{
			sbSQL.append(strClinetCtrl + ".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(",");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value)");
		try
		{
			SETTMagnifier.showZoomCtrl(
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
	 * 创建银企接口--用于上收的成员单位账户放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lFilialeAccountID 成员单位账户ID(初识值)
	 * @param strFilialeAccountNo 成员单位账户编号(初识值)
	 * @param lBankTypeID 开户行类型ID(初识值)
	 * @param strBankTypeCtrl 银行类型ID对应的控件名称（查询时关联）
	 * @param lClientID 所属单位ID(初识值)
	 * @param strClientCtrl 所属单位ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnAccountNoCtrl 返回值（账户名称）对应的控件名
	 * @param strRtnAccountTypeCtrl 返回值（账户类型）对应的控件名
	 * @param strRtnFilialeNameCtrl 返回之（成员单位名称）对应的控件名
	 * @param strProperty 外加的控件属性，一般传空串即可 
	 */
	public static void createTurnInFilialeAccountNoCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lFilialeAccountID,
		String strFilialeAccountNo,
		long lBankTypeID,
		String strBankTypeCtrl,
		long lClinetID,
		String strClinetCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnAccountNameCtrl,
		String strRtnAccountTypeCtrl,
		String strRtnFilialeNameCtrl,
		String strProperty)
	{
		//放大镜控件非数组
		String strMagnifierName = URLEncoder.encode("银行账户编号");
		String strMainProperty = " maxlength='30' value='" + strFilialeAccountNo + "' ";
		if (strProperty != null && !strProperty.equals(""))
		{
			strMainProperty += strProperty;
		}
		String strPrefix = "";
		if (strRtnAccountNameCtrl == null || strRtnAccountNameCtrl.equals(""))
		{
			strRtnAccountNameCtrl = "ItIsNotControl";
		}
		if (strRtnAccountTypeCtrl == null || strRtnAccountTypeCtrl.equals(""))
		{
			strRtnAccountTypeCtrl = "ItIsNotControl";
		}
		if (strRtnFilialeNameCtrl == null || strRtnFilialeNameCtrl.equals(""))
		{
			strRtnFilialeNameCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnAccountNameCtrl, strRtnAccountTypeCtrl, strRtnFilialeNameCtrl };
		String[] strMainFields = { "BANKACCOUNTNO", "BANKACCOUNTNAME", "ACCOUNTTYPE", "FILIALENAME" };
		String[] strReturnNames = { strCtrlName, strCtrlName + "BankTypeID" };
		String[] strReturnFields = { "ID", "BANKTYPE" };
		String[] strReturnValues = { String.valueOf(lFilialeAccountID), String.valueOf(lBankTypeID),"",""};
		String[] strDisplayNames = { URLEncoder.encode("银行账户编号"), URLEncoder.encode("成员单位名称")};
		String[] strDisplayFields = { "BANKACCOUNTNO", "FILIALENAME" };
		int nIndex = 0;
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getTurnInFilialeAccountNoSQL(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		if (strBankTypeCtrl != null && !strBankTypeCtrl.trim().equals(""))
		{
			sbSQL.append(strBankTypeCtrl + ".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(",");
		if (strClinetCtrl != null && !strClinetCtrl.trim().equals(""))
		{
			sbSQL.append(strClinetCtrl + ".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(",");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value)");
		try
		{
			SETTMagnifier.showZoomCtrl(
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
	 * 创建利息税费率计划放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lTaxRatePlanID 利息税费率计划ID(初识值)
	 * @param strTaxRatePlanCode 利息税费率计划编号(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnTaxRatePlanNameCtrl 返回值（利息税费率计划名称）对应的控件名
	 */
	public static void createTaxRatePlanCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lTaxRatePlanID,
		String strTaxRatePlanCode,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnTaxRatePlanNameCtrl)
	{
		String strMagnifierName = URLEncoder.encode("利息税费率计划");
		String strMainProperty = " maxlength='30' value='" + strTaxRatePlanCode + "'";
		String strPrefix = "";
		if (strRtnTaxRatePlanNameCtrl == null || strRtnTaxRatePlanNameCtrl.equals(""))
		{
			strRtnTaxRatePlanNameCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnTaxRatePlanNameCtrl };
		String[] strMainFields = { "planCode", "planName" };
		//特殊处理
		if (lTaxRatePlanID > 0)
		{
			lTaxRatePlanID = 1;
		}
		String[] strReturnNames = { strCtrlName};
		String[] strReturnFields = { "planID"};
		String[] strReturnValues = { String.valueOf(lTaxRatePlanID)};
		String[] strDisplayNames = { URLEncoder.encode("利息税费率计划编号"), URLEncoder.encode("利息税费率计划名称")};
		String[] strDisplayFields = { "planCode", "planName" };
		int nIndex = 0;
		String strSQL = "getTaxRatePlanSQL(" + lOfficeID + ")";
		
		try
		{
			SETTMagnifier.showZoomCtrl(
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
			Log.print("利息税费率计划放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
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
						
			SETTMagnifier.showZoomCtrl(
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
	
	/**
	 * 创建账户放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lAccountID 账户ID(初识值)
	 * @param strAccountNo 账户编号(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnAccountNameCtrl 返回值（账户名称）对应的控件名
	 */
	public static void createCurrentAccountCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		long lAccountGroupID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lAccountID,
		String strAccountNo,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnAccountNameCtrl)
	{
		String strMagnifierName = URLEncoder.encode("账户");
		String strMainProperty = " maxlength='30' value='" + strAccountNo + "'";
		String strPrefix = "";
		if (strRtnAccountNameCtrl == null || strRtnAccountNameCtrl.equals(""))
		{
			strRtnAccountNameCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnAccountNameCtrl };
		String[] strMainFields = { "saccountno", "sname" };
		//特殊处理
		long lFromAccount = 0;
		if (lAccountID > 0)
		{
			lFromAccount = 1;
		}
		String[] strReturnNames = { strCtrlName, strCtrlName + "lFromAccount" };
		String[] strReturnFields = { "id", "sname" };
		String[] strReturnValues = { String.valueOf(lAccountID), String.valueOf(lFromAccount)};
		String[] strDisplayNames = { URLEncoder.encode("账户编号"), URLEncoder.encode("账户名称")};
		String[] strDisplayFields = { "saccountno", "sname" };
		int nIndex = 0;
		String strSQL = "getCurrentAccountSQL(" + lOfficeID + "," + lCurrencyID + ","+lAccountGroupID+"," + strCtrlName + "Ctrl.value)";
		try
		{
			SETTMagnifier.showZoomCtrl(
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
			Log.print("账户放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	/**
	 * 创建放款通知单放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lLoanNoteID 放款单ID(初识值)
	 * @param strLoanNotetNo 放款单编号(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 */
	public static void createLoanNoteCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lLoanNoteID,
		String strLoanNotetNo,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnClientNameCtrl)
	{
		String strMagnifierName = URLEncoder.encode("放款通知单");
		String strMainProperty = " maxlength='30' value='" + strLoanNotetNo + "'";
		String strPrefix = "";
		if (strRtnClientNameCtrl == null || strRtnClientNameCtrl.equals(""))
		{
			strRtnClientNameCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnClientNameCtrl };
		String[] strMainFields = { "PayFormCode", "scontractcode" };
		//特殊处理
		long lFromNote = 0;
		if (lLoanNoteID > 0)
		{
			lFromNote = 1;
		}
		String[] strReturnNames = { strCtrlName, strCtrlName + "FromClient" };
		String[] strReturnFields = { "PayFormID", "scontractcode" };
		String[] strReturnValues = { String.valueOf(lLoanNoteID), String.valueOf(lFromNote)};
		String[] strDisplayNames = { URLEncoder.encode("放款通知单编号"), URLEncoder.encode("合同号")};
		String[] strDisplayFields = { "PayFormCode", "scontractcode" };
		int nIndex = 0;
		String strSQL = "getLoanNoteSQL(" + lOfficeID +","+lCurrencyID+ "," + strCtrlName + "Ctrl.value)";
		try
		{
			SETTMagnifier.showZoomCtrl(
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
	 * 按照账户类型和期限创建利率放大镜(返回利率值)
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lInterestRateID 利率ID(初识值)
	 * @param dInterestRate 利率值(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnInterestRateNameCtrl 返回值（利率名称）对应的控件名
	 */
	public static void createInterestRateCtrlBHIHF(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		long nAccounttypeID,
		String strFormName,
		String strCtrlName,
		String strlDepositTerm,
		String strTitle,
		long lInterestRateID,
		double dInterestRate,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnInterestRateNameCtrl)
	{
		String strMagnifierName = URLEncoder.encode("利率");
		//String strMainProperty = " value='" + dInterestRate +"' onblur='adjustAmount(\"" + strFormName +"\",\"" + strCtrlName +"Ctrl\",1,\"" + strChineseCtrl +"\"," + lCurrencyID+")'  onfocus='adjustAmount(\"" + strFormName +"\",\"" + strCtrlName +"Ctrl\",2,\"" + strChineseCtrl +"\"," + lCurrencyID + ")'>";
		String strMainProperty = " maxlength='25' ";
		if (dInterestRate != 0.0)
		{
			strMainProperty = strMainProperty + " value='" + DataFormat.formatRate(dInterestRate) + "'";
		}
		if (strNextControls != null && strNextControls.length > 0)
		{
			strMainProperty =
				strMainProperty + " onblur=\"adjustInterestRate('" + strFormName + "','" + strCtrlName + "Ctrl',1,''," + lCurrencyID + ",'" + strNextControls[0] + "')\"";
			strMainProperty =
				strMainProperty + "  onfocus=\"adjustInterestRate('" + strFormName + "','" + strCtrlName + "Ctrl',2,''," + lCurrencyID + ",'" + strNextControls[0] + "')\"";
				
		}
		else
		{
			strMainProperty = strMainProperty + " onblur=\"adjustInterestRate('" + strFormName + "','" + strCtrlName + "Ctrl',1,''," + lCurrencyID + ")\"";
			strMainProperty = strMainProperty + "  onfocus=\"adjustInterestRate('" + strFormName + "','" + strCtrlName + "Ctrl',2,''," + lCurrencyID + ")\"";
		}
		String strPrefix = "";
		if (strRtnInterestRateNameCtrl == null || strRtnInterestRateNameCtrl.equals(""))
		{
			strRtnInterestRateNameCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnInterestRateNameCtrl };
		String[] strMainFields = { "InterestRate", "InterestRateName" };
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "InterestRateID" };
		String[] strReturnValues = { String.valueOf(lInterestRateID)};
		String[] strDisplayNames = { URLEncoder.encode("利率名称"), URLEncoder.encode("利率值"),URLEncoder.encode("生效日期")};
		String[] strDisplayFields = { "InterestRateName", "InterestRate","Dteffective" };
		int nIndex = 1;
		String strSQL = "getInterestRateSQLBEIHF(" + lOfficeID + "," + lCurrencyID + ","+ strCtrlName + "Ctrl.value,"+strlDepositTerm+".value,"+nAccounttypeID+")";
		boolean blnIsOptional = false;
		boolean blnIsRateCtrl = true;
		try
		{
			SETTMagnifier.showZoomCtrl(
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
				"rate");
		}
		catch (Exception e)
		{
			Log.print("利率放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	/**
	 * 查询是协定存款的活期账户，账户状态为非删除和销户状态，子账户状态为非删除状态。
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lClientID 客户ID
	 * @param lAccountID 账户ID
	 * @param strAccountNo 账户编号
	 * @param lAccountGroupTypeID 账户组ID
	 * @param lAccountTypeID 账户类型ID 
	 * @param lReceiveOrPay 
	 * @param strClientCtrl 客户ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD名称 
	 * @param strSecondTD 第二个TD名称
	 * @param strNextControls  下一个（或多个）获得焦点的控件
	 * @param strRtnClientIDCtrl 返回值（客户ID）对应的控件名
	 * @param strRtnClientNoCtrl 返回值（客户编号）对应的控件名
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 */
	public static void createAccountCurrentCtrl(
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
			try
			{
//			if (lAccountGroupTypeID != SETTConstant.AccountGroupType.CURRENT  )
//			{
//				out.println("账户组必须为协定存款的活期账户。");
//				return;
//			}
			int nCaseNumber = 4;
			/*修改 by kenny (胡志强) (2007-03-22) 修改账户号段数的问题*/
			if (lAccountGroupTypeID == SETTConstant.AccountGroupType.CURRENT
			        ||lAccountGroupTypeID == SETTConstant.AccountGroupType.OTHERDEPOSIT)
			{
				//活期账户组，显示4个文本框
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
			String[] strReturnNames = { strCtrlName, strCtrlName + "ClientID" , strCtrlName + "GroupID" };
			String[] strReturnFields = { "AccountID", "ClientID" ,"accountgroupid"};
			String[] strReturnValues = { String.valueOf(lAccountID), String.valueOf(lClientID), String.valueOf(lAccountGroupTypeID)};
			String[] strDisplayNames = { URLEncoder.encode("账户编号"), URLEncoder.encode("账户名称")};
			String[] strDisplayFields = { "AccountNo", "AccountName" };
			int nIndexAccount = 0;
			String strSQL = "";
			if (strClientCtrl != null && !strClientCtrl.trim().equals(""))
			{
				strSQL =
				"getNegotiateAccountSQL_OLD("
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
				"getNegotiateAccountSQL_OLD("
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
				/*
				if (lOfficeID > 0)
				{
					strAccountNo = NameRef.getOfficeNoByID(lOfficeID);
				}
				*/
				//修改 by kenny(胡志强)(2007-03-26) 处理账户号编码规则问题
				//账户号的段间符号
				String tag = Config.getProperty(ConfigConstant.GLOBAL_ACCOUNTNO_TAG,"-");
				//账户号的第一段的类型
				int firstFieldType = Config.getInteger(ConfigConstant.GLOBAL_ACCOUNTNO_FIRSTFIELDTYPE,1);
				//正常情况下lCurrencyID、lOfficeID均会大于0
				if (firstFieldType == 1) {
					if (lCurrencyID > 0) {
						strAccountNo = NameRef.getCurrencyNoByID(lCurrencyID);
					}
					if (lOfficeID > 0) {
						strAccountNo = strAccountNo + tag + NameRef.getOfficeNoByID(lOfficeID);
					}
					if (lAccountTypeID > 0) {
						strAccountNo = strAccountNo + tag + DataFormat.formatInt(lAccountTypeID, 2);
					}
				} else if (firstFieldType == 2) {
					if (lOfficeID > 0) {
						strAccountNo = NameRef.getOfficeNoByID(lOfficeID);
					}
					if (lAccountTypeID > 0) {
						strAccountNo = strAccountNo + tag + DataFormat.formatInt(lAccountTypeID, 2);
					}
				}
			}
			
				SETTMagnifier.showSpecialZoomCtrl(
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
	 * 资金计划放大镜，查询出只包含复核和审批的资金计划
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param cpID 资金计划ID
	 * @param cpCode 资金计划编号(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 */
	public static void createOBFundPlanCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long cpID,
		String cpCode,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls)
	{
		String strMagnifierName = URLEncoder.encode("资金计划");
		String strMainProperty = " maxlength='30' value='" + cpCode + "'";
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl"};
		String[] strMainFields = { "CPCODE"};
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "CPID" };
		String[] strReturnValues = { String.valueOf(cpID)};
		String[] strDisplayNames = { URLEncoder.encode("资金计划编号"),URLEncoder.encode("客户编号"), URLEncoder.encode("资金计划开始日期"),URLEncoder.encode("资金计划结束日期")};
		String[] strDisplayFields = { "CPCODE","CLIENTNO","STARTDATE","ENDDATE" };
		int nIndex = 0;
		String strSQL = "getFundPlanSQL(" + lOfficeID + ","+ lCurrencyID + "," + strCtrlName + "Ctrl.value)";
		try
		{
			SETTMagnifier.showZoomCtrl(
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
	 * added by ylguo
	 * 资金计划放大镜,查询出所有资金计划(保存，复核，审批三中状态)，除了的删除状态的
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param cpID 资金计划ID
	 * @param cpCode 资金计划编号(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 */
	public static void createOBFundPlanCtrl2(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long cpID,
		String cpCode,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls)
	{
		String strMagnifierName = URLEncoder.encode("资金计划");
		String strMainProperty = " maxlength='30' value='" + cpCode + "'";
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl"};
		String[] strMainFields = { "CPCODE"};
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "CPID" };
		String[] strReturnValues = { String.valueOf(cpID)};
		String[] strDisplayNames = { URLEncoder.encode("资金计划编号"),URLEncoder.encode("客户编号"), URLEncoder.encode("资金计划开始日期"),URLEncoder.encode("资金计划结束日期")};
		String[] strDisplayFields = { "CPCODE","CLIENTNO","STARTDATE","ENDDATE" };
		int nIndex = 0;
		String strSQL = "getFundPlanSQL2(" + lOfficeID + ","+ lCurrencyID + "," + strCtrlName + "Ctrl.value)";
		try
		{
			SETTMagnifier.showZoomCtrl(
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
	 * 创建提前还款通知单放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lPayFormID 放款通知单ID(初识值)
	 * @param lAheadPayFormID 提前还款通知单ID(初识值)
	 * @param strAheadPayFormNo 提前还款通知单号(初识值)
	 * @param lAheadPayFormTypeID 提前还款通知单类型(查询条件:1,信托；2，委托)
	 * @param lStatusID 提前还款通知单状态(内部状态：0，显示全部；1，业务处理使用；2，业务复核使用）
	 * @param strPayFormIDCtrl 放款通知单ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnAmountCtrl 返回值（还款金额）对应的控件名
	 * @param strRtnClientNoCtrl 返回值（客户编号）对应的控件名
	 * @param strRtnContractNoCtrl 返回值（合同号）对应的控件名
	 * @param strRtnPayFormNoCtrl 返回值（放款通知单号）对应的控件名
	 */
	public static void createAheadPayFormCtrl1(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lPayFormID,
		long lAheadPayFormID,
		String strAheadPayFormNo,
		long lAheadPayFormTypeID,
		long lStatusID,
		String strPayFormIDCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnAmountCtrl,
		String strRtnClientNoCtrl,
		String strRtnContractNoCtrl,
		String strRtnPayFormNoCtrl)
	{
		String strMagnifierName = URLEncoder.encode("还款通知单");
		String strMainProperty = " maxlength='30' value='" + strAheadPayFormNo + "'";
		String strPrefix = "";
		if (strRtnAmountCtrl == null || strRtnAmountCtrl.equals(""))
		{
			strRtnAmountCtrl = "ItIsNotControl";
		}
		if (strRtnContractNoCtrl == null || strRtnContractNoCtrl.equals(""))
		{
			strRtnContractNoCtrl = "ItIsNotControl";
		}
		if (strRtnClientNoCtrl == null || strRtnClientNoCtrl.equals(""))
		{
			strRtnClientNoCtrl = "ItIsNotControl";
		}
		if (strRtnPayFormNoCtrl == null || strRtnPayFormNoCtrl.equals(""))
		{
			strRtnPayFormNoCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnAmountCtrl, strRtnClientNoCtrl, strRtnContractNoCtrl, strRtnPayFormNoCtrl,"strDateStart","lLoanNoteID","lLoanNoteIDContractID","hidBalance" };
		String[] strMainFields = { "AheadRepayFormNo", "Amount", "ClientNo", "ContractNo", "PayFormNo","StartDate","PayFormID","ContractID","Balance" };
		String[] strReturnNames = { strCtrlName, strCtrlName + "PayFormID" };
		String[] strReturnFields = { "AheadRepayFormID", "PayFormID" };
		String[] strReturnValues = { String.valueOf(lAheadPayFormID), String.valueOf(lPayFormID)};
		String[] strDisplayNames = { URLEncoder.encode("还款通知单号"), URLEncoder.encode("合同号"), URLEncoder.encode("放款通知单号"), URLEncoder.encode("还款金额")};
		String[] strDisplayFields = { "AheadRepayFormNo", "ContractNo", "PayFormNo", "Amount" };
		int nIndex = 0;
		long[] lContractTypeIDs = null;
		//信托 ：LOANConstant.LoanType.ZYDQ  ZYZCQ  ZGXEDQ  ZGXEZCQ  YT
		if (lAheadPayFormTypeID == 1)
		{
			lContractTypeIDs =
				new long[] {
					LOANConstant.LoanType.ZY,
					LOANConstant.LoanType.ZGXE,
					LOANConstant.LoanType.YT,
					LOANConstant.LoanType.MFXD};
		}
		//委托 : WT  WTTJTH
		else
			if (lAheadPayFormTypeID == 2)
			{
				lContractTypeIDs = new long[] { LOANConstant.LoanType.WT };
			}
		long[] lStatusIDArray = null;
		//获得状态数组
		if (lStatusID == 0)
		{
			lStatusIDArray = new long[] { LOANConstant.AheadRepayStatus.CHECK, LOANConstant.AheadRepayStatus.USED };
		}
		else
			if (lStatusID == 1)
			{
				lStatusIDArray = new long[] { LOANConstant.AheadRepayStatus.CHECK };
			}
			else
				if (lStatusID == 2)
				{
					lStatusIDArray = new long[] { LOANConstant.AheadRepayStatus.USED };
				}
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getAheadPayFormSQL1(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		if (strPayFormIDCtrl != null && !strPayFormIDCtrl.trim().equals(""))
		{
			sbSQL.append(strPayFormIDCtrl + ".value");
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append(",");
		sbSQL.append("'");
		if (lContractTypeIDs != null && lContractTypeIDs.length > 0)
		{
			for (int i = 0; i < lContractTypeIDs.length; i++)
			{
				sbSQL.append(lContractTypeIDs[i]);
				if (i < lContractTypeIDs.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append("'");
		if (lStatusIDArray != null && lStatusIDArray.length > 0)
		{
			for (int i = 0; i < lStatusIDArray.length; i++)
			{
				sbSQL.append(lStatusIDArray[i]);
				if (i < lStatusIDArray.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value)");
		//Log.print(sbSQL.toString());
		try
		{
			SETTMagnifier.showZoomCtrl(
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
	 * 创建信贷资产转让通知单放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lContractID 转让合同ID(初识值)
	 * @param lNoticeFormID 转让通知单ID(初识值)
	 * @param strNoticeFormNo  转让通知单号(初识值)
	 * @param lNoticeFormTypeID  转让通知单类型
	 * @param lTypeID 转让通知单使用条件(内部状态：
	
	 * @param strContractCtrl 转让合同ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnStartDateCtrl 返回值（起始日期）对应的控件名
	 * @param strRtnEndDateCtrl 返回值（到期日期）对应的控件名
	 * @param strRtnSubAccountIDCtrl 返回值（子账户ID）对应的控件名
	 * @param strRtnBalanceCtrl 返回值（转让通知单余额）对应的控件名
	 * @param strRtnIsHasFreeCtrl 返回值（是否有免还通知单：1，是；2，否）对应的控件名
	 */
	public static void createTransferNoticeFormCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lContractID,
		long lNoticeFormID,
		String strNoticeFormNo,
		long lNoticeFormTypeID,
		long lTypeID,
		String strContractCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnStartDateCtrl,
		String strRtnEndDateCtrl,
		String strRtnSubAccountIDCtrl,
		String strRtnBalanceCtrl,
		String strRtnIsHasFreeCtrl)
	{
		String strMagnifierName = URLEncoder.encode("业务通知单编号");
		String strMainProperty = " maxlength='30' value='" + strNoticeFormNo + "'";
		String strPrefix = "";
		if (strRtnStartDateCtrl == null || strRtnStartDateCtrl.equals(""))
		{
			strRtnStartDateCtrl = "NCTL";
		}
		if (strRtnEndDateCtrl == null || strRtnEndDateCtrl.equals(""))
		{
			strRtnEndDateCtrl = "NCTL";
		}
		if (strRtnSubAccountIDCtrl == null || strRtnSubAccountIDCtrl.equals(""))
		{
			strRtnSubAccountIDCtrl = "NCTL";
		}
		if (strRtnBalanceCtrl == null || strRtnBalanceCtrl.equals(""))
		{
			strRtnBalanceCtrl = "NCTL";
		}
		if (strRtnIsHasFreeCtrl == null || strRtnIsHasFreeCtrl.equals(""))
		{
			strRtnIsHasFreeCtrl = "NCTL";
		}
		String[] strMainNames = { strCtrlName + "Ctrl" };
		String[] strMainFields = { "NoticeCode" };
		String[] strReturnNames = { strCtrlName, strCtrlName+"ContractID" };
		String[] strReturnFields = { "ID", "ContractID" };
		String[] strReturnValues = { String.valueOf(lNoticeFormID), String.valueOf(lContractID)};
		String[] strDisplayNames = { URLEncoder.encode("转让通知单编号"),URLEncoder.encode("转让合同编号"), URLEncoder.encode("本金"),URLEncoder.encode("利息")};
		String[] strDisplayFields = { "NoticeCode", "ContractCode", "Amount","Interest" };
		int nIndex = 0;
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getTransferNoticeFormSQL(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value)");
		try
		{
			SETTMagnifier.showZoomCtrl(
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
			Log.print("放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	public static void createTransferNoticeForm(
			JspWriter out,
			long lOfficeID,
			long lCurrencyID,
			String strFormName,
			String strCtrlName,
			String strTitle,
			String strNoticeFormNo,
			long lNoticeFormID,
			String strFirstTD,
			String strSecondTD,
			String[] strNextControls,
			long noticeType,
			long statusid,
			java.sql.Timestamp sysTime)
		{
		   String strMagnifierName = URLEncoder.encode("业务通知单编号");
		    String strMainProperty = " maxlength='30' value='" + strNoticeFormNo + "'";			
			String strPrefix = "";
			String[] strMainNames = { strCtrlName + "Ctrl" };
			String[] strMainFields = { "NoticeCode" };
			String[] strReturnNames = { strCtrlName };
			String[] strReturnFields = { "ID" };
			String[] strReturnValues = { String.valueOf(lNoticeFormID)};
			String[] strDisplayNames = { URLEncoder.encode("转让通知单编号"),URLEncoder.encode("转让合同编号"), URLEncoder.encode("本金"),URLEncoder.encode("利息"),URLEncoder.encode("业务类型"),URLEncoder.encode("是否代收")};
			String[] strDisplayFields = { "NoticeCode", "CONTRACTCODE", "Amount","Interest","TRANSTYPE","Urrogatepay" };
			int nIndex = 0;
			String strSQL = "getTransferNoticeFormSQL1(" + lOfficeID + "," + lCurrencyID + "," + strCtrlName + "Ctrl.value,"+noticeType+","+statusid+",'"+DataFormat.formatDate(sysTime)+"')";
			try
			{
				SETTMagnifier.showZoomCtrl(
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
				Log.print("放大镜[" + strCtrlName + "]异常：" + e.toString());
			}
		}
	/**
	 * 创建资金调度令所需单位名称放大镜
	 * @param out
	 * @param OfficeID 办事处ID
	 * @param CurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param OrganizationID 单位ID(初识值)
	 * @param OrganizationName 单位名称(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 */
	public static void createOrganizationCtrl(
		JspWriter out,
		long OfficeID,
		long CurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long OrganizationID,
		String OrganizationName,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String MainProperty,
		String strOnKeyUp)
	{
		String strMagnifierName = URLEncoder.encode("单位名称");
		String strMainProperty = ""+MainProperty+ " maxlength='50' value='" + OrganizationName + "'";
		String strValue=OrganizationName;
		String strPrefix = "";
		String[] strMainNames = {strCtrlName + "Ctrl"};
		String[] strMainFields = {"OrganizationName"};
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "ID" };
		String[] strReturnValues = { String.valueOf(OrganizationID)};
		String[] strDisplayNames = { URLEncoder.encode("单位名称"), };
		String[] strDisplayFields = { "OrganizationName"};
		int nIndex = 0;
		String strSQL = "getOrganizationSQL(" + OfficeID + ","+ CurrencyID + "," +strCtrlName + ".value" + "," + strCtrlName + "Ctrl.value)";
		try
		{
			SETTMagnifier.showCommonCtrladdOnKeyUp(
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
				strValue,
				strOnKeyUp,
				strSQL,
				strNextControls,
				strTitle,
				strFirstTD,
				strSecondTD,
				false,
				"branch");
		}
		catch (Exception e)
		{
			Log.print("资金调度令（单位名称）放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	
	/**
	 * 创建资金调度令所需-查询单位名称放大镜（将状态置为0的单位也查出来）
	 * @param out
	 * @param OfficeID 办事处ID
	 * @param CurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param OrganizationID 单位ID(初识值)
	 * @param OrganizationName 单位名称(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 */
	public static void createSearchOrganizationCtrl(
		JspWriter out,
		long OfficeID,
		long CurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long OrganizationID,
		String OrganizationName,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String MainProperty,
		String strOnKeyUp)
	{
		String strMagnifierName = URLEncoder.encode("单位名称");
		String strMainProperty = ""+MainProperty+ " maxlength='50' value='" + OrganizationName + "'";
		String strValue=OrganizationName;
		String strPrefix = "";
		String[] strMainNames = {strCtrlName + "Ctrl"};
		String[] strMainFields = {"OrganizationName"};
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "ID" };
		String[] strReturnValues = { String.valueOf(OrganizationID)};
		String[] strDisplayNames = { URLEncoder.encode("单位名称"), };
		String[] strDisplayFields = { "OrganizationName"};
		int nIndex = 0;
		String strSQL = "getSearchOrganizationSQL(" + OfficeID + ","+ CurrencyID + "," +strCtrlName + ".value" + "," + strCtrlName + "Ctrl.value)";
		try
		{
			SETTMagnifier.showCommonCtrladdOnKeyUp(
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
				strValue,
				strOnKeyUp,
				strSQL,
				strNextControls,
				strTitle,
				strFirstTD,
				strSecondTD,
				false,
				"");
		}
		catch (Exception e)
		{
			Log.print("资金调度令（单位名称）放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}

	/**
	 * 创建资金调度令——开户行账号及开户行名称放大镜
	 * @param out
	 * @param OfficeID 办事处ID
	 * @param CurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param BankAccountID 外部账户ID(初识值)
	 * @param BankAccountNo 外部账户编号(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnBankNameCtrl 返回值（开户行名称）对应的控件名
	 * @param OrganizationStrCtrlName 关联的单位控件名称
	 */
	public static void createFoundsDispatchBankAccountCtrl(
		JspWriter out,
		long OfficeID,
		long CurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long BankAccountID,
		String BankAccountNo,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnBankNameCtrl,
		String MainProperty,
		String strOnKeyUp)
	{
		String strMagnifierName = URLEncoder.encode("开户行账号");
		String strMainProperty = ""+MainProperty+" maxlength='50' value='" + BankAccountNo + "'";
		String strValue=BankAccountNo;
		
		String strPrefix = "";

		if (strRtnBankNameCtrl == null || strRtnBankNameCtrl.equals(""))
		{
			strRtnBankNameCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnBankNameCtrl };
		String[] strMainFields = { "BankAccountCode", "BankName" };
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "ID" };
		String[] strReturnValues = { String.valueOf(BankAccountID)};
		String[] strDisplayNames = { URLEncoder.encode("开户行账号"), URLEncoder.encode("开户行名称")};
		String[] strDisplayFields = { "BankAccountCode", "BankName" };
		int nIndex = 0;
		String strSQL = "getFoundsDispatchBankAccountSQL(" + OfficeID + "," +CurrencyID + "," +strCtrlName + ".value "  + "," +  strCtrlName + "Ctrl.value)";
		try
		{
			SETTMagnifier.showCommonCtrladdOnKeyUp(
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
				strValue,
				strOnKeyUp,
				strSQL,
				strNextControls,
				strTitle,
				strFirstTD,
				strSecondTD,
				false,
				"");
		}
		catch (Exception e)
		{
			Log.print("外部（非中油客户）账户放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	
	
	
	/**
	 * 创建资金调度令所需调度令编号放大镜
	 * @param out
	 * @param OfficeID 办事处ID
	 * @param CurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param FoundsdispatchID 调度令ID(初识值)
	 * @param FoundsdispatchCode 调度令编号(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 */
	public static void createFoundsdispatchCodeCtrl(
		JspWriter out,
		long OfficeID,
		long CurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long FoundsdispatchID,
		String FoundsdispatchCode,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String MainProperty,
		String strOnKeyUp)
	{
		String strMagnifierName = URLEncoder.encode("调度令编号");
		String strMainProperty = ""+MainProperty+" dec='调度令编号' maxlength='30' value='" + FoundsdispatchCode + "'";
		String strValue=FoundsdispatchCode;
		String strPrefix = "";
		String[] strMainNames = {strCtrlName + "Ctrl"};
		String[] strMainFields = {"FoundsdispatchCode"};
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "ID" };
		String[] strReturnValues = { String.valueOf(FoundsdispatchID)};
		String[] strDisplayNames = { URLEncoder.encode("调度令编号"), };
		String[] strDisplayFields = { "FoundsdispatchCode"};
		int nIndex = 0;
		String strSQL = "getFoundsdispatchCodeSQL(" + OfficeID + "," +CurrencyID + "," + strCtrlName + ".value" + "," + strCtrlName + "Ctrl.value)";
		try
		{
			SETTMagnifier.showCommonCtrladdOnKeyUp(
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
				strValue,
				strOnKeyUp,
				strSQL,
				strNextControls,
				strTitle,
				strFirstTD,
				strSecondTD,
				false,
				"");
		}
		catch (Exception e)
		{
			Log.print("资金调度令（调度令编号）放大镜[" + strCtrlName + "]异常：" + e.toString());
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
	 * @param strCtrlType 控件类型（特殊处理）
	 *  rate 利率控件(可以格式化利率)
	 *  branch 开户行控件(控件为textarea)
	 * @throws Exception
	 */
	public static void showCommonCtrladdOnKeyUp(
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
		String strValue,
		String strOnKeyUp,
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
					+ "/iTreasury-settlement/magnifier/ShowMagnifierZoom.jsp?"
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
			sOnKeyUp = sOnKeyUp + strOnKeyUp;
			//int iPos = strMainProperty.toLowerCase().indexOf("disabled");
			int iPos = -1;
			//显示控件
			if (iPos == -1)
			{
				out.println(
					"<td width=150"
						+ strFirstTD
						+ ">"
						+ strTitle
						+ "：&nbsp;"
						+ "<a href=#><img name=\""
						+ strButtonName
						+ "\" src='/websett/image/icon.gif' border=0 onclick=\""
						+ sOnKeydown
						+ "\"></a></td>");
				//image
			}
			else
			{
				out.println("<td " + strFirstTD + ">" + strTitle + "&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/websett/image/icon.gif' border=0 ></a></td>");
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
								+ "\"  class=\"box\" type=\"text\"  rows=2 cols=30 "
								+ strMainProperty
								+ ">" 
								+ strValue
								+" </textarea></td>");
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
								+ "\"  class=\"box\" type=\"text\"  rows=2 cols=30 onKeyDown=\"if(event.keyCode==13) "
								+ sOnKeydown
								+ "\" onKeyUp=\""
								+ sOnKeyUp
								+ "\" "
								+ strMainProperty
								+ "\">"
								+ strValue
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

	//特约委托收款账户放大镜
	public static void createTrustCollectionAccountCtrl(
			JspWriter out,
			long lOfficeID,
			long lCurrencyID,
			String strFormName,
			String strCtrlName,
			String strTitle,
			long lClientID,
			long lAccountID,
			String strAccountNo,
			String strFirstTD,
			String strSecondTD,
			String[] strNextControls,
			String contractCtrlName
			)
		{
			String strMagnifierName = URLEncoder.encode("账户");
			String strPrefix = "";
			String strMainProperty = "";
			int nCaseNumber = 4;
			
			String[] strMainNames = { strCtrlName + "Ctrl" };
			String[] strMainFields = { "saccountno" };
			String[] strReturnNames = { strCtrlName };
			String[] strReturnFields = { "npayaccountid"};
			String[] strReturnValues = { String.valueOf(lAccountID)};
			Log.print("SETTConstant.ReceiveOrPay.PAY ==========" + SETTConstant.ReceiveOrPay.PAY);
			String[] strDisplayNames = { URLEncoder.encode("账户编号"),URLEncoder.encode("账户名称")};
			String[] strDisplayFields = { "saccountno","saccountname" };
			int nIndexAccount = 0;
			String strSQL = "";
			strSQL =
					"getTrustCollectionAccountSQL("
						+ lOfficeID
						+ ","
						+ lCurrencyID
						+ ","
						+contractCtrlName
						+".value,"
						+ strFormName
						+ "."
						+ strCtrlName
						+ "Ctrl.value)";
			//初始默认值
			if (strAccountNo == null || strAccountNo.equals(""))
			{
				String tag = Config.getProperty(ConfigConstant.GLOBAL_ACCOUNTNO_TAG,"-");
				//账户号的第一段的类型
				int firstFieldType = Config.getInteger(ConfigConstant.GLOBAL_ACCOUNTNO_FIRSTFIELDTYPE,1);
				//正常情况下lCurrencyID、lOfficeID均会大于0
				if (firstFieldType == 1) {
					if (lCurrencyID > 0) {
						strAccountNo = NameRef.getCurrencyNoByID(lCurrencyID);
					}
					if (lOfficeID > 0) {
						strAccountNo = strAccountNo + tag + NameRef.getOfficeNoByID(lOfficeID);
					}
					
				} else if (firstFieldType == 2) {
					if (lOfficeID > 0) {
						strAccountNo = NameRef.getOfficeNoByID(lOfficeID);
					}
				}
			}
			try
			{
				SETTMagnifier.showSpecialZoomCtrl(
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
					strSecondTD
					);
			}
			catch (Exception e)
			{
				Log.print("账户放大镜[" + strCtrlName + "]异常：" + e.toString());
			}
		}
	
	//特约委托收款合同放大镜
	public static void createTrustCollectionContractCtrl(
			JspWriter out,
			long lOfficeID,
			long lCurrencyID,
			String strFormName,
			String strCtrlName,
			String strTitle,
			long lClientID,
			long lContractID,
			String strContractNo,
			String strClientCtrl,
			String strFirstTD,
			String strSecondTD,
			String strAccountCtrlName,
			String[] strNextControls
			)
		{
			String strMagnifierName = URLEncoder.encode("合同");
			String strMainProperty = " maxlength='30' value='" + strContractNo + "'";
			String strPrefix = "";
			
			
			String[] strMainNames = { strCtrlName + "Ctrl"};
			String[] strMainFields = { "scode"};
			String[] strReturnNames = { strCtrlName, strCtrlName + "ClientID"};
			String[] strReturnFields = { "id", "scode"};
			String[] strReturnValues = { String.valueOf(lContractID), String.valueOf(lClientID)};
			String[] strDisplayNames = { URLEncoder.encode("合同号"), URLEncoder.encode("客户名称"),URLEncoder.encode("账户号")}; //jzw 2010-04-29 修改贷款合同的放大镜，增加显示信息。
			String[] strDisplayFields = { "scode", "sName","saccountno"};
			int nIndex = 0;
			StringBuffer sbSQL = new StringBuffer(64);
			sbSQL.append("getTrustCollectionContractCtrl(");
			sbSQL.append(lOfficeID);
			sbSQL.append(",");
			sbSQL.append(lCurrencyID);
			sbSQL.append(",");
			sbSQL.append(strCtrlName);
			sbSQL.append("Ctrl.value");
			sbSQL.append(",");
			if (strClientCtrl != null && !strClientCtrl.trim().equals(""))
			{
				sbSQL.append(strClientCtrl);
				sbSQL.append(".value");
			}
			else
			{
				sbSQL.append("-1");
			}
			sbSQL.append(",");
			sbSQL.append(strAccountCtrlName);
			sbSQL.append(".value");
			sbSQL.append(")");
			System.out.print(sbSQL);
			try
			{
				SETTMagnifier.showZoomCtrl(
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
				Log.print("合同放大镜[" + strCtrlName + "]异常：" + e.toString());
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
	 * @param strCtrlType 控件类型（特殊处理）
	 *  rate 利率控件(可以格式化利率)
	 *  branch 开户行控件(控件为textarea)
	 * @throws Exception
	 */
	public static void showZoomCtrlByLi(
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
					+ "/iTreasury-settlement/magnifier/ShowMagnifierZoom.jsp?"
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
					sOnKeyUp += strReturnNames[i] + ".value = '-1'; ";
				}
			}
			//int iPos = strMainProperty.toLowerCase().indexOf("disabled");
			int iPos = -1;
			//显示控件
			if (iPos == -1)
			{
				out.println(
					"<td width=150"
						+ strFirstTD
						+ ">"
						+ strTitle
						+ "：&nbsp;"
						+ "<a href=#><img name=\""
						+ strButtonName
						+ "\" src='/websett/image/icon.gif' border=0 onclick=\""
						+ sOnKeydown
						+ "\"></a></td>");
				//image
			}
			else
			{
				out.println("<td " + strFirstTD + ">" + strTitle + "&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/websett/image/icon.gif' border=0 ></a></td>");
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
					out.println("<td" + strSecondTD + "><input type=\"text\" name=\""
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
								+ "}\" onKeyUp=\"if(event.keyCode==8|event.keyCode==46){"
								+ sOnKeyUp
								+ "}\"></td>");
								
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
								+ "\" onKeyUp=\"if(event.keyCode==8|event.keyCode==46){"
								+ sOnKeyUp
								+ "}\"></td>");
					}
			}
		}
		catch (Exception exp)
		{
			throw exp;
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
	public static void createClientCtrlByLi(
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
		String strMainProperty = "  maxlength='30' value='" + strClientNo + "'";
		String strPrefix = "";
		if (strRtnClientNameCtrl == null || strRtnClientNameCtrl.equals(""))
		{
			strRtnClientNameCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnClientNameCtrl, "levelcode"};
		String[] strMainFields = { "clientNo", "clientName", "levelcode" };
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
			SETTMagnifier.showZoomCtrlByLi(
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
	public static void showZoomCtrlByLi(
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
		showZoomCtrlByLi(
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
	public static void showZoomCtrlByLi(
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
		showZoomCtrlByLi(
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
	 * 支付手续费账户号
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
	 * 	特殊的，利息费用支付，请传入-100（只显示两种信托和委托两种）；）
	 * @param lReceiveOrPay 收付类型（-1000特殊的，现实所有状态的账户）
	 * @param strClientCtrl 客户ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnClientIDCtrl 返回值（客户ID）对应的控件名
	 * @param strRtnClientNoCtrl 返回值（客户编号）对应的控件名
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 */
	public static void createAccountHandingChargeCtrl(
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
		String strConsignClientCtrl,
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
		/*修改 by kenny (胡志强) (2007-03-22) 修改账户号段数的问题*/
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
			nCaseNumber = 3;
		}
		else if (lAccountGroupTypeID == SETTConstant.AccountGroupType.TRUST
		        ||lAccountGroupTypeID == SETTConstant.AccountGroupType.CONSIGN
		        ||lAccountGroupTypeID == SETTConstant.AccountGroupType.DISCOUNT
		        ||lAccountGroupTypeID == SETTConstant.AccountGroupType.OTHERLOAN
		        ||lAccountGroupTypeID == SETTConstant.AccountGroupType.YT)
		{
			//贷款账户组，显示3个文本框
			nCaseNumber = 3;
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
		String[] strReturnNames = { strCtrlName, strCtrlName + "ClientID" , strCtrlName + "GroupID" };
		String[] strReturnFields = { "AccountID", "ClientID" ,"accountgroupid"};
		String[] strReturnValues = { String.valueOf(lAccountID), String.valueOf(lClientID), String.valueOf(lAccountGroupTypeID)};
		String[] strDisplayNames = { URLEncoder.encode("账户编号"), URLEncoder.encode("账户名称")};
		String[] strDisplayFields = { "AccountNo", "AccountName" };
		int nIndexAccount = 0;
		String strSQL = "";
		if (strClientCtrl != null && !strClientCtrl.trim().equals("") &&strConsignClientCtrl != null && !strConsignClientCtrl.trim().equals(""))
		{
			strSQL =
			"getAccountHandingChargeSQL("
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
				+ strConsignClientCtrl
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
			"getAccountHandingChargeSQL("
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
				+ "-1,"
				+ strFormName
				+ "."
				+ strCtrlName
				+ "Ctrl.value)";
		}
		//初始默认值
		if (strAccountNo == null || strAccountNo.equals(""))
		{
			/*
			if (lOfficeID > 0)
			{
				strAccountNo = NameRef.getOfficeNoByID(lOfficeID);
			}
			*/
			//修改 by kenny(胡志强)(2007-03-26) 处理账户号编码规则问题
			//账户号的段间符号
			String tag = Config.getProperty(ConfigConstant.GLOBAL_ACCOUNTNO_TAG,"-");
			//账户号的第一段的类型
			int firstFieldType = Config.getInteger(ConfigConstant.GLOBAL_ACCOUNTNO_FIRSTFIELDTYPE,1);
			//正常情况下lCurrencyID、lOfficeID均会大于0
			if (firstFieldType == 1) {
				if (lCurrencyID > 0) {
					strAccountNo = NameRef.getCurrencyNoByID(lCurrencyID);
				}
				if (lOfficeID > 0) {
					strAccountNo = strAccountNo + tag + NameRef.getOfficeNoByID(lOfficeID);
				}
				if (lAccountTypeID > 0) {
					strAccountNo = strAccountNo + tag + DataFormat.formatInt(lAccountTypeID, 2);
				}
			} else if (firstFieldType == 2) {
				if (lOfficeID > 0) {
					strAccountNo = NameRef.getOfficeNoByID(lOfficeID);
				}
				if (lAccountTypeID > 0) {
					strAccountNo = strAccountNo + tag + DataFormat.formatInt(lAccountTypeID, 2);
				}
			}
		}
		try
		{
			SETTMagnifier.showSpecialZoomCtrl(
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
	 * 创建外部（非中油客户）账户放大镜  (财企接口新增)
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lExtAccountID 外部账户ID(初识值)
	 * @param strExtAccountNo 外部账户编号(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnClientNameCtrl 返回值（账户客户名称）对应的控件名
	 * @param strRtnProvinceCtrl 返回值（汇入地(省)）对应的控件名
	 * @param strRtnCityCtrl 返回值（汇入地(市)）对应的控件名
	 * @param strRtnBankNameCtrl 返回值（汇入行名称）对应的控件名
	 */
	public static void createExternalAccountCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lExtAccountID,
		String strExtAccountNo,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnClientNameCtrl,
		String strRtnProvinceCtrl,
		String strRtnCityCtrl,
		String strRtnBankNameCtrl,
		String strRtnPayeeBankCNAPSNOCtrl,
		String strRtnPayeeBankOrgNOCtrl,
		String strRtnPayeeBankExchangeNOCtrl)
	{
		String strMagnifierName = URLEncoder.encode("外部账户");
		String strMainProperty = " size='30' maxlength='30' value='" + strExtAccountNo + "'";
		String strPrefix = "";
		if (strRtnClientNameCtrl == null || strRtnClientNameCtrl.equals(""))
		{
			strRtnClientNameCtrl = "ItIsNotControl";
		}
		if (strRtnProvinceCtrl == null || strRtnProvinceCtrl.equals(""))
		{
			strRtnProvinceCtrl = "ItIsNotControl";
		}
		if (strRtnCityCtrl == null || strRtnCityCtrl.equals(""))
		{
			strRtnCityCtrl = "ItIsNotControl";
		}
		if (strRtnBankNameCtrl == null || strRtnBankNameCtrl.equals(""))
		{
			strRtnBankNameCtrl = "ItIsNotControl";
		}
		if (strRtnPayeeBankCNAPSNOCtrl == null || strRtnPayeeBankCNAPSNOCtrl.equals(""))
		{
			strRtnPayeeBankCNAPSNOCtrl = "ItIsNotControl";
		}
		if (strRtnPayeeBankOrgNOCtrl == null || strRtnPayeeBankOrgNOCtrl.equals(""))
		{
			strRtnPayeeBankOrgNOCtrl = "ItIsNotControl";
		}
		if (strRtnPayeeBankExchangeNOCtrl == null || strRtnPayeeBankExchangeNOCtrl.equals(""))
		{
			strRtnPayeeBankExchangeNOCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnClientNameCtrl, strRtnProvinceCtrl, strRtnCityCtrl, strRtnBankNameCtrl,strRtnPayeeBankCNAPSNOCtrl, strRtnPayeeBankOrgNOCtrl,strRtnPayeeBankExchangeNOCtrl };
		String[] strMainFields = { "ExtAcctNo", "ExtAcctName", "ExtProvince", "ExtCity", "ExtBankName","ExtPayeeBankCNAPSNO","ExtPayeeBankOrgNO","ExtPayeeBankExchangeNO" };
		String[] strReturnNames = { strCtrlName };
		String[] strReturnFields = { "ExtAcctID" };
		String[] strReturnValues = { String.valueOf(lExtAccountID)};
		String[] strDisplayNames = { URLEncoder.encode("账户编号"), URLEncoder.encode("账户名称"), URLEncoder.encode("省"), URLEncoder.encode("市"), URLEncoder.encode("汇入行名称"),URLEncoder.encode("汇入行CNAPS号"),URLEncoder.encode("汇入行机构号"),URLEncoder.encode("汇入行联行号")};
		String[] strDisplayFields = { "ExtAcctNo", "ExtAcctName", "ExtProvince", "ExtCity", "ExtBankName" ,"ExtPayeeBankCNAPSNO","ExtPayeeBankOrgNO","ExtPayeeBankExchangeNO"};
		int nIndex = 0;
		String strSQL = "getExtAcctCurrencySQL(" + lOfficeID + "," +lCurrencyID + "," + strCtrlName + "Ctrl.value)";
		try
		{
			SETTMagnifier.showZoomCtrl(
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
			Log.print("外部（非中油客户）账户放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	
	
	
	/**
	 * 创建合同放大镜，能够带出合同号
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lClientID 客户ID(初识值)
	 * @param lContractID 合同ID(初识值)
	 * @param strContractNo 合同号(初识值)
	 * @param lTransactionType 交易类型
	 * (查询条件:由SETTConstant.TransactionType定义，-1 查询所有)
	 * @param lMagnifierType 合同放大境类型
	 * (查询条件: 1 发放;2 收回;3,转贴现卖出（买断）)
	 * @param strClientCtrl 客户ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnContractTypeCtrl 返回值（合同类型）对应的控件名
	 * @param strRtnClientIDCtrl 返回值（客户ID）对应的控件名(一般是隐含控件)
	 * @param strRtnClientCodeCtrl 返回值（客户编号）对应的控件名
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 */
	public static void createContractCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lClientID,
		long lContractID,
		String strContractNo,
		long lTransactionType,
		long lMagnifierType,
		String strClientCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnContractTypeCtrl,
		String strRtnClientIDCtrl,
		String strRtnClientCodeCtrl,
		String strRtnClientNameCtrl,
		String strRtnAccountNoCtrl,
		String strRtnAccountIDCtrl,
		String strRtnAccountgroupID,
		String strRtnAccountClientID,
		long lReceiveOrPayAcct,
		long lAccountGroupTypeIDAcct)
	{
		String strMagnifierName = "合同";
		String strMainProperty = " maxlength='30' value='" + strContractNo + "'";
		String strPrefix = "";
		
		if (strRtnContractTypeCtrl == null || strRtnContractTypeCtrl.equals(""))
		{
			strRtnContractTypeCtrl = "ItIsNotControl";
		}
		
		if (strRtnClientIDCtrl == null || strRtnClientIDCtrl.equals(""))
		{
			strRtnClientIDCtrl = "ItIsNotControl";
		}
		if (strRtnClientCodeCtrl == null || strRtnClientCodeCtrl.equals(""))
		{
			strRtnClientCodeCtrl = "ItIsNotControl";
		}
		if (strRtnClientNameCtrl == null || strRtnClientNameCtrl.equals(""))
		{
			strRtnClientNameCtrl = "ItIsNotControl";
		}
		if (strRtnAccountNoCtrl == null || strRtnAccountNoCtrl.equals(""))
		{
			strRtnAccountNoCtrl = "ItIsNotControl";
		}
		if (strRtnAccountIDCtrl == null || strRtnAccountIDCtrl.equals(""))
		{
			strRtnAccountIDCtrl = "ItIsNotControl";
		}
		if (strRtnAccountgroupID == null || strRtnAccountgroupID.equals(""))
		{
			strRtnAccountgroupID = "ItIsNotControl";
		}
		if (strRtnAccountClientID == null || strRtnAccountClientID.equals(""))
		{
			strRtnAccountClientID = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnAccountNoCtrl,strRtnContractTypeCtrl,strRtnClientIDCtrl,strRtnClientCodeCtrl,strRtnClientNameCtrl,strRtnAccountIDCtrl,strRtnAccountgroupID,strRtnAccountClientID};
		String[] strMainFields = { "Code", "accountno","Type","ClientID","ClientCode","ClientName","accountid","groupid","ClientID"};
		String[] strReturnNames = { strCtrlName, strCtrlName + "ClientID"};
		String[] strReturnFields = { "ContractID", "ClientID"};
		String[] strReturnValues = { String.valueOf(lContractID), String.valueOf(lClientID)};
		String[] strDisplayNames = { "合同号", "客户名称","帐户号","贷款类型","合同金额","到期日"}; 
		String[] strDisplayFields = { "Code", "ClientName","accountno","nTypeID","m","dtEndDate" };
		int nIndex = 0;
		long[] lContractTypeIDs = null;
		//信托 ：LOANConstant.LoanType.ZYDQ  ZYZCQ  ZGXEDQ  ZGXEZCQ 
		if (lTransactionType == SETTConstant.TransactionType.TRUSTLOANGRANT || lTransactionType == SETTConstant.TransactionType.TRUSTLOANRECEIVE|| lTransactionType == SETTConstant.TransactionType.BREAK_INVESTADDITIONALRECORDINGGRANT)
		{
			lContractTypeIDs = new long[] { LOANConstant.LoanType.ZY, LOANConstant.LoanType.ZGXE,  LOANConstant.LoanType.MFXD };
		}
		//银团 ：LOANConstant.LoanType. YT
		else
			if (lTransactionType == SETTConstant.TransactionType.BANKGROUPLOANGRANT || lTransactionType == SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
			{
				lContractTypeIDs = new long[] { LOANConstant.LoanType.YT};
			}
		//委托 : WT  WTTJTH
		else
			if (lTransactionType == SETTConstant.TransactionType.CONSIGNLOANGRANT || lTransactionType == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
			{
				lContractTypeIDs = new long[] { LOANConstant.LoanType.WT};
			}
		//贴现 : TX
		else
			if (lTransactionType == SETTConstant.TransactionType.DISCOUNTGRANT || lTransactionType == SETTConstant.TransactionType.DISCOUNTRECEIVE)
			{
				lContractTypeIDs = new long[] { LOANConstant.LoanType.TX };
			}
		//转贴现
		else
			if (lTransactionType == SETTConstant.TransactionType.TRANSABATEMENT)
			{
				lContractTypeIDs = new long[] { LOANConstant.LoanType.ZTX };
			}
		//商业汇票贴现
	    else
	    	if (lTransactionType == SETTConstant.TransactionType.DISCOUNT_INCOME_OFFBALANCE || lTransactionType == SETTConstant.TransactionType.DISCOUNT_PAYOUT_OFFBALANCE)
	    	{
	    		lContractTypeIDs = new long[] { LOANConstant.LoanType.TX };
	    	}
		//显示委托和信托
		else
			if (lTransactionType == SETTConstant.TransactionType.INTERESTFEEPAYMENT)
			{
				lContractTypeIDs =
					new long[] {
						LOANConstant.LoanType.ZY,
						LOANConstant.LoanType.ZGXE,
						LOANConstant.LoanType.YT,
						LOANConstant.LoanType.WT,
						LOANConstant.LoanType.MFXD};
			}
		else
			if( lTransactionType == SETTConstant.TransactionType.OPENMARGIN
					|| lTransactionType == SETTConstant.TransactionType.WITHDRAWMARGIN)
			{
				//保证金开立、支取
				lContractTypeIDs = new long[] { LOANConstant.LoanType.DB };
			}
		else	
			if( lTransactionType == SETTConstant.TransactionType.RECEIVEFINANCE		//add by feiye 2006.5.2
						|| lTransactionType == SETTConstant.TransactionType.RETURNFINANCE)
				{
					//融资租凭收款,还款
					lContractTypeIDs = new long[] { LOANConstant.LoanType.RZZL };	//融资租赁合同
				}
			else
				if (lTransactionType == -1)
				{
					lContractTypeIDs = LOANConstant.LoanType.getAllCode(lOfficeID,lCurrencyID);
				}
		long[] lStatusIDs = null;
		if (lMagnifierType == -1)
		{
			lStatusIDs = LOANConstant.ContractStatus.getAllCode();
		}
		//modify by xwhe 2008-09-18
		else if(lMagnifierType==4)
		{
			lStatusIDs = new long[] { LOANConstant.ContractStatus.NOTACTIVE, LOANConstant.ContractStatus.ACTIVE, LOANConstant.ContractStatus.EXTEND,LOANConstant.ContractStatus.OVERDUE };
		}
		//发放
		else
			if (lMagnifierType == 1)
			{
				lStatusIDs = new long[] { LOANConstant.ContractStatus.NOTACTIVE, LOANConstant.ContractStatus.ACTIVE, LOANConstant.ContractStatus.EXTEND };
			}
		//收回
		else
			if (lMagnifierType == 2 || lMagnifierType == 3)
			{
				lStatusIDs =
					new long[] {
				        LOANConstant.ContractStatus.SAVE,
				        LOANConstant.ContractStatus.SUBMIT,
				        LOANConstant.ContractStatus.CHECK,
						LOANConstant.ContractStatus.NOTACTIVE,
						LOANConstant.ContractStatus.ACTIVE,
						LOANConstant.ContractStatus.EXTEND,
						LOANConstant.ContractStatus.OVERDUE,
						LOANConstant.ContractStatus.DELAYDEBT,
						LOANConstant.ContractStatus.BADDEBT };
			}
		else 
			if( lMagnifierType == 5)
			{
				lStatusIDs = new long[]{LOANConstant.ContractStatus.ACTIVE};
			}
		else
			if (lMagnifierType == 8)
			{
				lStatusIDs = new long[] { 
						LOANConstant.ContractStatus.NOTACTIVE, 
						LOANConstant.ContractStatus.ACTIVE, 
						LOANConstant.ContractStatus.EXTEND, 
						LOANConstant.ContractStatus.OVERDUE, 
						LOANConstant.ContractStatus.DELAYDEBT, 
						LOANConstant.ContractStatus.BADDEBT, 
						LOANConstant.ContractStatus.FINISH, 
						};
			}		
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getContractSQLWithAccount(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		sbSQL.append("'");
		if (lContractTypeIDs != null && lContractTypeIDs.length > 0)
		{
			for (int i = 0; i < lContractTypeIDs.length; i++)
			{
				sbSQL.append(lContractTypeIDs[i]);
				if (i < lContractTypeIDs.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append("'");
		if (lStatusIDs != null && lStatusIDs.length > 0)
		{
			for (int i = 0; i < lStatusIDs.length; i++)
			{
				sbSQL.append(lStatusIDs[i]);
				if (i < lStatusIDs.length - 1)
				{
					sbSQL.append(",");
				}
			}
		}
		else
		{
			sbSQL.append("-1");
		}
		sbSQL.append("'");
		sbSQL.append(",");
		sbSQL.append(strCtrlName);
		sbSQL.append("Ctrl.value");
		sbSQL.append(",");
		if (strClientCtrl != null && !strClientCtrl.trim().equals(""))
		{
			sbSQL.append(strClientCtrl);
			sbSQL.append(".value");
			sbSQL.append(",");
		}
		else
		{
			sbSQL.append("-1");
			sbSQL.append(",");
		}
		sbSQL.append(lReceiveOrPayAcct);
		sbSQL.append(",");
		sbSQL.append(lAccountGroupTypeIDAcct);
		sbSQL.append(",");
		sbSQL.append(lTransactionType);
		sbSQL.append(")");
		
		int nCaseNumber = 4;
		/*修改 by kenny (胡志强) (2007-03-22) 修改账户号段数的问题*/
		if (lAccountGroupTypeIDAcct == SETTConstant.AccountGroupType.CURRENT
		        ||lAccountGroupTypeIDAcct == SETTConstant.AccountGroupType.OTHERDEPOSIT)
		{
			//活期账户组，显示4个文本框
			nCaseNumber = 4;
		}
		else if (lAccountGroupTypeIDAcct == SETTConstant.AccountGroupType.FIXED
		        ||lAccountGroupTypeIDAcct == SETTConstant.AccountGroupType.NOTIFY)
		{
			//定期账户组，显示3个文本框
			nCaseNumber = 3;
		}
		else if (lAccountGroupTypeIDAcct == SETTConstant.AccountGroupType.TRUST
		        ||lAccountGroupTypeIDAcct == SETTConstant.AccountGroupType.CONSIGN
		        ||lAccountGroupTypeIDAcct == SETTConstant.AccountGroupType.DISCOUNT
		        ||lAccountGroupTypeIDAcct == SETTConstant.AccountGroupType.OTHERLOAN
		        ||lAccountGroupTypeIDAcct == SETTConstant.AccountGroupType.YT)
		{
			//贷款账户组，显示3个文本框
			nCaseNumber = 3;
		}
		//获取初始化帐户编号
		
		String strAccountNo = "";
		String tag = Config.getProperty(ConfigConstant.GLOBAL_ACCOUNTNO_TAG,"-");
		//账户号的第一段的类型
		int firstFieldType = Config.getInteger(ConfigConstant.GLOBAL_ACCOUNTNO_FIRSTFIELDTYPE,1);
		//正常情况下lCurrencyID、lOfficeID均会大于0
		if (firstFieldType == 1) {
			if (lCurrencyID > 0) {
				strAccountNo = NameRef.getCurrencyNoByID(lCurrencyID);
			}
			if (lOfficeID > 0) {
				strAccountNo = strAccountNo + tag + NameRef.getOfficeNoByID(lOfficeID);
			}

		} else if (firstFieldType == 2) {
			if (lOfficeID > 0) {
				strAccountNo = NameRef.getOfficeNoByID(lOfficeID);
			}

		}
		try
		{
			SETTMagnifier.showZoomCtrlWithPost(
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
				strSecondTD,
				false,
				"");
		}
		catch (Exception e)
		{
			//e.printStackTrace();
			Log.print("合同放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	
	/**
	 * 显示普通放大镜,能够带出分段帐户号
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
	public static void showZoomCtrlWithPost(
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
			String strNextControlsAll = "";
			String strMainNamesAll = "";
			String strMainFieldsAll = "";
			String strReturnNamesAll = "";
			String strReturnFieldsAll = "";
			String strDisplayNamesAll = "";
			String strDisplayFieldsAll = "";
			
			for (int i = 0; i < strNextControls.length; i++)
			{
				strNextControlsAll = strNextControlsAll + strNextControls[i] + "##";
			}
		
			for (int i = 0; i < strMainNames.length; i++)
			{
				strMainNamesAll = strMainNamesAll + strMainNames[i] + "##";
				strMainFieldsAll = strMainFieldsAll + strMainFields[i] + "##";
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
					strReturnNamesAll = strReturnNamesAll + strReturnNames[i] + "##";
					strReturnFieldsAll = strReturnFieldsAll + strReturnFields[i] + "##";
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
				strDisplayNamesAll = strDisplayNamesAll + strDisplayNames[i] + "##";
				strDisplayFieldsAll = strDisplayFieldsAll + strDisplayFields[i] + "##";
			}
			//生成查询按钮的事件字符串
			String sOnKeydown = "MagnifierOnclick('"+Env.URL_PREFIX+"','"+sFeatures+"',"+strFormName+"."+strMainNames[0]+",'"+strMainNames[0]+"','"+strFormName+"','"+strMagnifierName+"',"+strSQL+","+nIndex+",'"+strNextControlsAll+"','"+strMainNamesAll+"','"+strMainFieldsAll+"','"+strReturnNamesAll+"','"+strReturnFieldsAll+"','"+strDisplayNamesAll+"','"+strDisplayFieldsAll+"')";
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
					"<td width=150"
						+ strFirstTD
						+ ">"
						+ strTitle
						+ "：&nbsp;"
						+ "<a href=#><img name=\""
						+ strButtonName
						+ "\" src='/websett/image/icon.gif' border=0 onclick=\""
						+ sOnKeydown
						+ "\"></a></td>");
				//image
			}
			else
			{
				out.println("<td " + strFirstTD + ">" + strTitle + "&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/websett/image/icon.gif' border=0 ></a></td>");
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
					out.println("<td" + strSecondTD + "><input type=\"text\" name=\""
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
	 * 创建分支机构放大镜
	 * @param out	 
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lOfficeID 机构ID(初识值)
	 * @param strOfficeNo 机构编号(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnOfficeNameCtrl 返回值（机构名称）对应的控件名
	 */
	public static void createOfficeCtrl(
		JspWriter out,		
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lOfficeID,
		String strOfficeNo,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnOfficeNameCtrl)
	{
		String strMagnifierName = URLEncoder.encode("机构");
		String strMainProperty = " maxlength='30' value='" + strOfficeNo + "'";
		String strPrefix = "";
		if (strRtnOfficeNameCtrl == null || strRtnOfficeNameCtrl.equals(""))
		{
			strRtnOfficeNameCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnOfficeNameCtrl };
		String[] strMainFields = { "OfficeNo", "officeName"};
		
		String[] strReturnNames = { strCtrlName};
		String[] strReturnFields = { "officeid"};
		String[] strReturnValues = { String.valueOf(lOfficeID)};
		String[] strDisplayNames = { URLEncoder.encode("机构编号"), URLEncoder.encode("机构名称")};
		String[] strDisplayFields = { "OfficeNo", "officeName"};
		int nIndex = 0;
		String strSQL = "getOfficeSQL(" + strCtrlName + "Ctrl.value)";
		try
		{
			SETTMagnifier.showZoomCtrl(
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
			Log.print("分支机构放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	/**
	 * 创建分支机构放大镜
	 * @param out	 
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lOfficeID 机构ID(初识值)
	 * @param strOfficeNo 机构编号(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnOfficeNameCtrl 返回值（机构名称）对应的控件名
	 */
	public static void createOfficeCtrl(
		JspWriter out,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lOfficeID,
		String strOfficeNo,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnOfficeNameCtrl,
		String strRtnOfficeClientIDCtrl,
		boolean isValidOfLendAccBalance,
		boolean isValidOfReserveAccBalance
		)
	{
		String strMagnifierName = "机构";
		String strMainProperty = " maxlength='30' value='" + strOfficeNo + "'";
		String strPrefix = "";
		if (strRtnOfficeNameCtrl == null || strRtnOfficeNameCtrl.equals(""))
		{
			strRtnOfficeNameCtrl = "ItIsNotControl";
		}
		if (strRtnOfficeClientIDCtrl == null || strRtnOfficeClientIDCtrl.equals(""))
		{
			strRtnOfficeClientIDCtrl = "ItIsNotControl";
		}
		
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnOfficeNameCtrl,strRtnOfficeClientIDCtrl};
		String[] strMainFields = { "officeCode", "officeName","officeclientid"};
		
		String[] strReturnNames = { strCtrlName};
		String[] strReturnFields = { "officeId"};
		String[] strReturnValues = { String.valueOf(lOfficeID)};
		String[] strDisplayNames = { "机构编号", "机构名称", "拆借账户编号","拆借账余额", "备付金账户编号", "备付金账户余额"};
		String[] strDisplayFields = { "officeCode", "officeName", "lendAccNo", "lendAccBalance", "reserveAccNo", "reserveAccBalance"};
		int nIndex = 0;
		String strSQL = "getLendingOfficeSQL("+lCurrencyID+"," + strCtrlName + "Ctrl.value"+","+""+isValidOfLendAccBalance+""+","+""+isValidOfReserveAccBalance+")";
		try
		{
			SETTMagnifier.showZoomCtrlWithPost(
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
					strSQL.toString(),
					strNextControls,
					strTitle,
					strFirstTD,
					strSecondTD,
					false,
					"");
		}
		catch (Exception e)
		{
			Log.print("分支机构放大镜[" + strCtrlName + "]异常：" + e.toString());
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
	 * 	特殊的，利息费用支付，请传入-100（只显示两种信托和委托两种）；）
	 * @param lReceiveOrPay 收付类型（-1000特殊的，现实所有状态的账户）
	 * @param strClientCtrl 客户ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnClientIDCtrl 返回值（客户ID）对应的控件名
	 * @param strRtnClientNoCtrl 返回值（客户编号）对应的控件名
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 */
	public static void createInternalLendAccountCtrl(
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
		String strOfficeClientCtrl,		
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnClientIDCtrl,
		String strRtnBranchOfficeIDCtrl,
		String strRtnBranchOfficeNoCtrl,
		String strRtnBranchOfficeNameCtrlA,
		String strRtnAccountNameCtrl,
		String strRtnAccountBalanceCtrl,
		boolean isAvaBalance)
	{
		String strMagnifierName = URLEncoder.encode("账户");
		String strPrefix = "";
		String strMainProperty = "";
		int nCaseNumber = 4;	
		if (lAccountGroupTypeID == SETTConstant.AccountGroupType.LENDING
			||lAccountGroupTypeID == SETTConstant.AccountGroupType.RESERVE )
		{
			nCaseNumber = 4;
		}
		if (strRtnClientIDCtrl == null || strRtnClientIDCtrl.equals(""))
		{
			strRtnClientIDCtrl = "ItIsNotControl";
		}
		if (strRtnBranchOfficeIDCtrl == null || strRtnBranchOfficeIDCtrl.equals(""))
		{
			strRtnBranchOfficeIDCtrl = "ItIsNotControl";
		}		
		if (strRtnBranchOfficeNoCtrl == null || strRtnBranchOfficeNoCtrl.equals(""))
		{
			strRtnBranchOfficeNoCtrl = "ItIsNotControl";
		}
		if (strRtnBranchOfficeNameCtrlA == null || strRtnBranchOfficeNameCtrlA.equals(""))
		{
			strRtnBranchOfficeNameCtrlA = "ItIsNotControl";
		}
		if (strRtnAccountNameCtrl == null || strRtnAccountNameCtrl.equals(""))
		{
			strRtnAccountNameCtrl = "ItIsNotControl";
		}
		if (strRtnAccountBalanceCtrl == null || strRtnAccountBalanceCtrl.equals(""))
		{
			strRtnAccountBalanceCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnClientIDCtrl,strRtnBranchOfficeIDCtrl, strRtnBranchOfficeNoCtrl,strRtnBranchOfficeNameCtrlA,strRtnAccountNameCtrl,strRtnAccountBalanceCtrl };
		String[] strMainFields = { "AccountNo", "ClientID","OfficeID", "OfficeNo", "OfficeName","AccountName" ,"mBalance"};
		String[] strReturnNames = { strCtrlName, strCtrlName + "ClientID" , strCtrlName + "GroupID" };
		String[] strReturnFields = { "AccountID", "ClientID" ,"accountgroupid"};
		String[] strReturnValues = { String.valueOf(lAccountID), String.valueOf(lClientID), String.valueOf(lAccountGroupTypeID)};
		String[] strDisplayNames = { URLEncoder.encode("账户编号"), URLEncoder.encode("账户名称")};
		String[] strDisplayFields = { "AccountNo", "AccountName" };
		int nIndexAccount = 0;
		String strSQL = "";	
		if (strOfficeClientCtrl != null && !strOfficeClientCtrl.trim().equals(""))
		{
			strSQL ="getLendAccountSQL("
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
				+ strOfficeClientCtrl
				+ ".value"
				+ ","
				+ strFormName
				+ "."
				+ strCtrlName
				+ "Ctrl.value"
				+ ","
				+isAvaBalance+")";
		}else{
		strSQL ="getLendAccountSQL_a("
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
				+ strCtrlName
				+ "Ctrl.value"
				+ ","
				+isAvaBalance+")";
		}
				
		//初始默认值
		if (strAccountNo == null || strAccountNo.equals(""))
		{
			
			//修改 by kenny(胡志强)(2007-03-26) 处理账户号编码规则问题
			//账户号的段间符号
			String tag = Config.getProperty(ConfigConstant.GLOBAL_ACCOUNTNO_TAG,"-");
			//账户号的第一段的类型
			int firstFieldType = Config.getInteger(ConfigConstant.GLOBAL_ACCOUNTNO_FIRSTFIELDTYPE,1);
			//正常情况下lCurrencyID、lOfficeID均会大于0
			if (firstFieldType == 1) {
				if (lCurrencyID > 0) {
					strAccountNo = NameRef.getCurrencyNoByID(lCurrencyID);
				}
				if (lOfficeID > 0) {
					strAccountNo = strAccountNo + tag + NameRef.getOfficeNoByID(lOfficeID);
				}
				if (lAccountTypeID > 0) {
					strAccountNo = strAccountNo + tag + DataFormat.formatInt(lAccountTypeID, 2);
				}
			} else if (firstFieldType == 2) {
				if (lOfficeID > 0) {
					strAccountNo = NameRef.getOfficeNoByID(lOfficeID);
				}
				if (lAccountTypeID > 0) {
					strAccountNo = strAccountNo + tag + DataFormat.formatInt(lAccountTypeID, 2);
				}
			}
		}
		try
		{
			SETTMagnifier.showSpecialZoomCtrl(
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
				strOfficeClientCtrl);
		}
		catch (Exception e)
		{
			Log.print("账户放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	public static void createClientCtrlt(
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
			String strRtnClientNameCtrlAcctReceive,
			String strRtnClientNoCtrlAcctReceive,
			String strRtnClientIdCtrlAcctReceive)
		{
			String strMagnifierName = URLEncoder.encode("客户");
			String strMainProperty = " maxlength='30' value='" + strClientNo + "'";
			String strPrefix = "";
			if (strRtnClientNameCtrl == null || strRtnClientNameCtrl.equals(""))
			{
				strRtnClientNameCtrl = "ItIsNotControl";
			}
			if (strRtnClientNameCtrlAcctReceive == null || strRtnClientNameCtrlAcctReceive.equals(""))
			{
				strRtnClientNameCtrlAcctReceive = "ItIsNotControl";
			}
			if (strRtnClientNoCtrlAcctReceive == null || strRtnClientNoCtrlAcctReceive.equals(""))
			{
				strRtnClientNoCtrlAcctReceive = "ItIsNotControl";
			}
			if (strRtnClientIdCtrlAcctReceive == null || strRtnClientIdCtrlAcctReceive.equals(""))
			{
				strRtnClientIdCtrlAcctReceive = "ItIsNotControl";
			}
			String[] strMainNames = { strCtrlName + "Ctrl", strRtnClientNameCtrl,strRtnClientNameCtrlAcctReceive,strRtnClientNoCtrlAcctReceive,strRtnClientIdCtrlAcctReceive,"levelcode"};
			String[] strMainFields = { "clientNo", "clientName","clientName" ,"clientNo","clientid","levelcode" };
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
				SETTMagnifier.showZoomCtrl(
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
	 * 创建客户放大镜(账户开立专用)
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
	public static void createClientCtrlForOpeningAccount(
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
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnClientNameCtrl,strCtrlName + "OfficeID" };
		String[] strMainFields = { "clientNo", "clientName", "ClientOfficeID"};
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
		
		String strSQL = "getInstitutionalclientSQL(" + strCtrlName + "Ctrl.value)";
		
		int nIndex = 0;
		try
		{
			SETTMagnifier.showZoomCtrl(
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
	
	
	public static void createBankAccountCtrlForLendForm(
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
			String strRtnClientNameCtrl){
		String strMagnifierName = URLEncoder.encode("银行账户");
		String strMainProperty = " maxlength='30' value='" + strClientNo + "'";
		String strPrefix = "";
		if (strRtnClientNameCtrl == null || strRtnClientNameCtrl.equals(""))
		{
			strRtnClientNameCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnClientNameCtrl};
		String[] strMainFields = { "sAccountNo", "sAccounName"};
		//特殊处理
		long lFromClient = 0;
		if (lClientID > 0)
		{
			lFromClient = 1;
		}
		String[] strReturnNames = { strCtrlName, strCtrlName + "FromClient" };
		String[] strReturnFields = { "sAccountNo", "sAccounName" };
		String[] strReturnValues = { String.valueOf(lClientID), String.valueOf(lFromClient)};
		String[] strDisplayNames = { URLEncoder.encode("账户号"), URLEncoder.encode("账户名称")};
		String[] strDisplayFields = { "sAccountNo", "sAccounName" };
		int nIndex = 0;
		String strSQL = "getBankAccountSQL(" + strCtrlName + "Ctrl.value)";
		try
		{
			SETTMagnifier.showZoomCtrl(
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
			Log.print("银行账户放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	
}