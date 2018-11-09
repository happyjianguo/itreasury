/*
* OBMagnifier.java
* Created by yanliu 
* 2002年12月8日
 */
package com.iss.itreasury.ebank.util;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.net.URLEncoder;

import com.iss.itreasury.dataentity.*;
import com.iss.itreasury.ebank.obfinanceinstr.dao.*;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.*;
import com.iss.itreasury.settlement.bizdelegation.AccountSystemDelegation;
import com.iss.itreasury.settlement.dataentity.AccountSystemInfo;
import com.iss.itreasury.settlement.util.*;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.util.*;

import javax.servlet.jsp.JspWriter;
import com.iss.itreasury.util.Constant;
public class OBMagnifier
{
	private static String ZOOMERRORMSG = "放大镜参数设置错误!";
	/**
		 * 创建收款方银行账号放大镜（汇）
		 * @author yanliu
		 * @param out
		 * @param lClientID 客户ID
		 * @param lCurrencyID 币种ID
		 * @param strRelativeForm 关联项
		 * @param sBankAccountCode 账号（初始值）
		 * @param strFormName 主页表单名称
		 * @param strCtrlName 放大镜主控件名称
		 * @param strTitle 放大镜描述)
		 * @param strClientNo 客户编号(初识值)
		 * @param strFirstTD 第一个TD的属性
		 * @param strSecondTD 第二个TD的属性
		 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
		 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
		 * @param isBlurQuery 是否支持收款方名称的模糊查询
		 */
	public static void createPayeeAccountNOCtrl(
		JspWriter out,
		long lCurrencyID,
		long lClientID,
		String strRelativeForm1,
		String strRelativeForm2,
		String strRelativeForm3,
		String strRelativeForm4,
		String strRelativeForm5,
		String strFormName,
		String sBankAccountCode,
		String strCtrlName,
		String strTitle,
		String strFirstTD,
		String strSecondTD)
	{
		createPayeeAccountNOCtrl(
			out,
			lCurrencyID,
			lClientID,
			strRelativeForm1,
			strRelativeForm2,
			strRelativeForm3,
			strRelativeForm4,
			strRelativeForm5,
			strFormName,
			sBankAccountCode,
			strCtrlName,
			strTitle,
			strFirstTD,
			strSecondTD,
			false);
	}
	public static void createPayeeAccountNOCtrl(
		JspWriter out,
		long lCurrencyID,
		long lClientID,
		String strRelativeForm1,
		String strRelativeForm2,
		String strRelativeForm3,
		String strRelativeForm4,
		String strRelativeForm5,
		String strFormName,
		String sBankAccountCode,
		String strCtrlName,
		String strTitle,
		String strFirstTD,
		String strSecondTD,
		boolean isBlurQuery)
	{
		String strMagnifierName = URLEncoder.encode("收款方账号");
		String strMainProperty = " maxlength='50' value='" + sBankAccountCode + "'";
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2, strRelativeForm3, strRelativeForm4, strRelativeForm5};
		String[] strMainFields = { "spayeeacctno", "ID", "sPayeeName", "SPAYEEPROV", "SPAYEECITY", "sPayeeBankName" };
		
		String[]  strReturnNames = { strCtrlName+"hiddenValue" }; //收款方
		
		String[] strReturnFields = {"spayeeacctno"
		};
		String[] strReturnValues = {""
		};
		String[] strDisplayNames = { URLEncoder.encode("收款方账号"), URLEncoder.encode("收款方名称")};
		String[] strDisplayFields = { "spayeeacctno", "sPayeeName" };
		int nIndex = 0;
		String strSQL = "getPayeeAccountNOSQL(" + isBlurQuery + "," + lClientID + "," + lCurrencyID + "," + strCtrlName + "Ctrl.value," + strRelativeForm2 + ".value" + ")";
		String[] strNextControls = { "dAmount" };
		try
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
				
				false,
				false);
			
		}
		catch (Exception e)
		{
		}
	}
	/**
		 * 创建收款方银行账号放大镜（本转）
		 * @author yanliu
		 * @param out
		 * @param lClientID 客户ID
		 * @param lCurrencyID 币种ID
		 * @param strRelativeForm 关联项
		 * @param sBankAccountCode 账号（初始值）
		 * @param strFormName 主页表单名称
		 * @param strCtrlName 放大镜主控件名称
		 * @param strTitle 放大镜描述)
		 * @param strClientNo 客户编号(初识值)
		 * @param strFirstTD 第一个TD的属性
		 * @param strSecondTD 第二个TD的属性
		 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
		 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
		 * @param isBlurQuery 是否支持收款方名称的模糊查询
		 */
	public static void createPayeeBankNOCtrl(
		JspWriter out,
		long lCurrencyID,
		long lClientID,
		String strRelativeForm1,
		String strRelativeForm2,
		String strFormName,
		String sBankAccountCode,
		String strCtrlName,
		String strTitle,
		String strFirstTD,
		String strSecondTD)
	{
		createPayeeBankNOCtrl(out, lCurrencyID, lClientID, strRelativeForm1, strRelativeForm2, strFormName, sBankAccountCode, strCtrlName, strTitle, strFirstTD, strSecondTD, true);
	}
	/**
	 * 创建收款方银行账号放大镜（本转）(国电专用)
	 * @author 
	 * @param out
	 * @param lClientID 客户ID
	 * @param lCurrencyID 币种ID
	 * @param strRelativeForm 关联项
	 * @param sBankAccountCode 账号（初始值）
	 * @param strFormName 主页表单名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述)
	 * @param strClientNo 客户编号(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 * @param isBlurQuery 是否支持收款方名称的模糊查询
	 */
public static void createInterPayeeBankNOCtrl(
	JspWriter out,
	long lCurrencyID,
	long lClientID,
	String strRelativeForm1,
	String strRelativeForm2,
	String strFormName,
	String sBankAccountCode,
	String strCtrlName,
	String strTitle,
	String strFirstTD,
	String strSecondTD)
{
	createInterPayeeBankNOCtrl(out, lCurrencyID, lClientID, strRelativeForm1, strRelativeForm2, strFormName, sBankAccountCode, strCtrlName, strTitle, strFirstTD, strSecondTD, true);
}

	public static void createPayeeBankNOCtrl(
		JspWriter out,
		long lCurrencyID,
		long lClientID,
		String strRelativeForm1,
		String strRelativeForm2,
		String strFormName,
		String sBankAccountCode,
		String strCtrlName,
		String strTitle,
		String strFirstTD,
		String strSecondTD,
		boolean isBlurQuery)
	{
		String strMagnifierName = URLEncoder.encode("收款方账号");
		String strMainProperty = " maxlength='40' value='" + sBankAccountCode + "'";
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2 };
		String[] strMainFields = { "spayeeacctno", "ID", "sPayeeName" };
		String[] strReturnNames = { "sPayeeBankAccountNO","hid"+strCtrlName +"Ctrl","hid"+strRelativeForm2 };
		String[] strReturnFields = { "accountBankNo","spayeeacctno", "sPayeeName" };
		String[] strReturnValues = { "","","" };
		String[] strDisplayNames = { URLEncoder.encode("收款方账号"), URLEncoder.encode("账户名称")};
		String[] strDisplayFields = { "displayAccountNo", "sPayeeName"};
		int nIndex = 0;
		String strSQL = "getPayeeBankNOSQL(" + isBlurQuery + "," + lClientID + "," + lCurrencyID + "," + strCtrlName + "Ctrl.value," + strRelativeForm2 + ".value" + ")";
		String[] strNextControls = { "dAmount" };
		try
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
				false,
				false);
		}
		catch (Exception e)
		{
		}
	}
	
	public static void createInterPayeeBankNOCtrl(
			JspWriter out,
			long lCurrencyID,
			long lClientID,
			String strRelativeForm1,
			String strRelativeForm2,
			String strFormName,
			String sBankAccountCode,
			String strCtrlName,
			String strTitle,
			String strFirstTD,
			String strSecondTD,
			boolean isBlurQuery)
		{
			String strMagnifierName = URLEncoder.encode("收款方账号");
			String strMainProperty = " maxlength='40' value='" + sBankAccountCode + "'";
			String strPrefix = "";
			String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2 };
			String[] strMainFields = { "spayeeacctno", "ID", "sPayeeName" };
			String[] strReturnNames = { "sPayeeBankAccountNO","hid"+strCtrlName +"Ctrl","hid"+strRelativeForm2 };
			String[] strReturnFields = { "accountBankNo","spayeeacctno", "sPayeeName" };
			String[] strReturnValues = { "","","" };
			String[] strDisplayNames = { URLEncoder.encode("收款方账号"), URLEncoder.encode("账户名称")};
			String[] strDisplayFields = { "displayAccountNo", "sPayeeName"};
			int nIndex = 0;
			String strSQL = "getInterPayeeBankNOSQL(" + isBlurQuery + "," + lClientID + "," + lCurrencyID + "," + strCtrlName + "Ctrl.value," + strRelativeForm2 + ".value" + ")";
			String[] strNextControls = { "dAmount" };
			try
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
					false,
					false);
			}
			catch (Exception e)
			{
			}
		}
	
	
	/**
	 * 创建收款方银行账号放大镜（本转）
	 * @author xwhe
	 * @param out
	 * @param m_lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strRelativeForm 关联项
	 * @param sBankAccountCode 账号（初始值）
	 * @param strFormName 主页表单名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述)
	 * @param strClientNo 客户编号(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 * @param isBlurQuery 是否支持收款方名称的模糊查询
	 */
public static void createPayeeBankNOCtrl1(
	JspWriter out,
	long lCurrencyID,
	long m_lOfficeID,
	String strRelativeForm1,
	String strRelativeForm2,
	String strFormName,
	String sBankAccountCode,
	String strCtrlName,
	String strTitle,
	String strFirstTD,
	String strSecondTD)
{
	createPayeeBankNOCtrl1(out, lCurrencyID, m_lOfficeID, strRelativeForm1, strRelativeForm2, strFormName, sBankAccountCode, strCtrlName, strTitle, strFirstTD, strSecondTD, false);
}

public static void createPayeeBankNOCtrl1(
	JspWriter out,
	long lCurrencyID,
	long m_lOfficeID,
	String strRelativeForm1,
	String strRelativeForm2,
	String strFormName,
	String sBankAccountCode,
	String strCtrlName,
	String strTitle,
	String strFirstTD,
	String strSecondTD,
	boolean isBlurQuery)
{
	String strMagnifierName = URLEncoder.encode("收款方账号");
	String strMainProperty = " maxlength='50' value='" + sBankAccountCode + "'";
	String strPrefix = "";
	String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2 };
	String[] strMainFields = { "spayeeacctno", "ID", "sPayeeName" };
	String[] strReturnNames = { "sPayeeBankAccountNO","hid"+strCtrlName +"Ctrl","hid"+strRelativeForm2 };
	String[] strReturnFields = { "accountBankNo","spayeeacctno", "sPayeeName" };
	String[] strReturnValues = { "","","" };
	String[] strDisplayNames = { URLEncoder.encode("收款方账号"), URLEncoder.encode("账户名称")};
	String[] strDisplayFields = { "displayAccountNo", "sPayeeName"};
	int nIndex = 0;
	String strSQL = "getPayeeBankNOSQL1(" + isBlurQuery + "," + m_lOfficeID + "," + lCurrencyID + "," + strCtrlName + "Ctrl.value," + strRelativeForm2 + ".value" + ")";
	String[] strNextControls = { "dAmount" };
	try
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
			false,
			false);
	}
	catch (Exception e)
	{
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
	 * @param lTransactionType 交易类型(查询条件:由SETTConstant.TransactionType定义，-1 查询所有)
	 * @param lMagnifierType 合同放大境类型(查询条件: 1 业务处理;2 业务复核;)
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
		String strMagnifierName = URLEncoder.encode("合同");
		String strMainProperty = " maxlength='30' value='" + strContractNo + "'";
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl" };
		String[] strMainFields = { "ContractCode", };
		String[] strReturnNames = { strCtrlName, strCtrlName + "ClientID" };
		String[] strReturnFields = { "ContractID", "ClientID" };
		String[] strReturnValues = { String.valueOf(lContractID), String.valueOf(lClientID)};
		String[] strDisplayNames = { URLEncoder.encode("合同号"), URLEncoder.encode("客户名称")};
		String[] strDisplayFields = { "ContractCode", "ClientName" };
		int nIndex = 0;
		long[] lContractTypeIDs = null;
		//信托 ：LOANConstant.LoanType.ZYDQ  ZYZCQ  ZGXEDQ  ZGXEZCQ  YT
		if (lTransactionType == SETTConstant.TransactionType.TRUSTLOANGRANT || lTransactionType == SETTConstant.TransactionType.TRUSTLOANRECEIVE)
		{
//			lContractTypeIDs = new long[] { LOANConstant.LoanType.ZYDQ, LOANConstant.LoanType.ZYZCQ, LOANConstant.LoanType.ZGXEDQ, LOANConstant.LoanType.ZGXEZCQ };
			lContractTypeIDs = new long[] { LOANConstant.LoanType.ZY, LOANConstant.LoanType.ZGXE };
		}
		else if (lTransactionType == SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
		{
//			lContractTypeIDs = new long[] { LOANConstant.LoanType.YTDQ, LOANConstant.LoanType.YTZCQ };
			lContractTypeIDs = new long[] { LOANConstant.LoanType.YT };
		}
		//委托 : WT  WTTJTH
		else if (lTransactionType == SETTConstant.TransactionType.CONSIGNLOANGRANT || lTransactionType == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
		{
//			lContractTypeIDs = new long[] { LOANConstant.LoanType.WT, LOANConstant.LoanType.WTTJTH };
			lContractTypeIDs = new long[] { LOANConstant.LoanType.WT };
		}
		//贴现 : TX
		else if (lTransactionType == SETTConstant.TransactionType.DISCOUNTGRANT || lTransactionType == SETTConstant.TransactionType.DISCOUNTRECEIVE)
		{
			lContractTypeIDs = new long[] { LOANConstant.LoanType.TX };
		}
		else if (lTransactionType == SETTConstant.TransactionType.INTERESTFEEPAYMENT)
		{
			lContractTypeIDs =
				new long[] {
					LOANConstant.LoanType.ZY,
//					LOANConstant.LoanType.ZYZCQ,
					LOANConstant.LoanType.ZGXE,
//					LOANConstant.LoanType.ZGXEZCQ,
					LOANConstant.LoanType.YT,
//					LOANConstant.LoanType.YTZCQ,
					LOANConstant.LoanType.WT
//					LOANConstant.LoanType.WTTJTH 
					};
		}
		else if (lTransactionType == -1)
		{
			lContractTypeIDs = LOANConstant.LoanType.getAllCode(lOfficeID,lCurrencyID);
		}
		long[] lStatusIDs = null;
		if (lMagnifierType == -1)
		{
			lStatusIDs = LOANConstant.ContractStatus.getAllCode();
		}
		//业务处理
		else if (lMagnifierType == 1)
		{
			lStatusIDs = new long[] { LOANConstant.ContractStatus.NOTACTIVE, LOANConstant.ContractStatus.ACTIVE, LOANConstant.ContractStatus.EXTEND };
		}
		else if (lMagnifierType == 2)
		{
			lStatusIDs =
				new long[] {
					LOANConstant.ContractStatus.NOTACTIVE,
					LOANConstant.ContractStatus.ACTIVE,
					LOANConstant.ContractStatus.EXTEND,
					LOANConstant.ContractStatus.OVERDUE,
					LOANConstant.ContractStatus.DELAYDEBT,
					LOANConstant.ContractStatus.BADDEBT };
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
			sbSQL.append(".value)");
		}
		else
		{
			sbSQL.append("-1)");
		}
		//Log.print(sbSQL.toString());
		try
		{
			OBMagnifier.showZoomCtrl(
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
				false);
		}
		catch (Exception e)
		{
			//e.printStackTrace();
			Log.print("合同放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}

	public static void createContractCtrl1(
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

		String strMagnifierName = URLEncoder.encode("合同");
		String strMainProperty = " maxlength='30' value='" + strContractNo + "'";
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl" };
		String[] strMainFields = { "ContractCode", };
		String[] strReturnNames = { strCtrlName, strCtrlName + "ClientID" };
		String[] strReturnFields = { "ContractID", "ClientID" };
		String[] strReturnValues = { String.valueOf(lContractID), String.valueOf(lClientID)};
		String[] strDisplayNames = { URLEncoder.encode("合同号"), URLEncoder.encode("客户名称")};
		String[] strDisplayFields = { "ContractCode", "ClientName" };
		int nIndex = 0;
		//long[] lContractTypeIDs = null;
		long[] lStatusIDs = null;
		if (lMagnifierType == -1)
		{
			lStatusIDs = LOANConstant.ContractStatus.getAllCode();
		}
		//业务处理
		else if (lMagnifierType == 1)
		{
			lStatusIDs = new long[] { LOANConstant.ContractStatus.NOTACTIVE, LOANConstant.ContractStatus.ACTIVE, LOANConstant.ContractStatus.EXTEND };
		}
		else if (lMagnifierType == 2)
		{
			lStatusIDs =
				new long[] {
					LOANConstant.ContractStatus.NOTACTIVE,
					LOANConstant.ContractStatus.ACTIVE,
					LOANConstant.ContractStatus.EXTEND,
					LOANConstant.ContractStatus.OVERDUE,
					LOANConstant.ContractStatus.DELAYDEBT,
					LOANConstant.ContractStatus.BADDEBT };
		}
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getContractSQL(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		sbSQL.append("");
		sbSQL.append(strFormName + ".lContractTypeIDs.value");
		sbSQL.append("");
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
			sbSQL.append(".value)");
		}
		else
		{
			sbSQL.append("-1)");
		}
		//Log.print(sbSQL.toString());
		try
		{
			OBMagnifier.showZoomCtrl(
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
				false);
		}
		catch (Exception e)
		{
			//e.printStackTrace();
			Log.print("合同放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}

	/**
	 * 创建放款日期放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lContractID 合同ID(初识值)
	 * @param lPayFormID 放款通知单ID(初识值)
	 * @param lAccountID 贷款账户ID(初始值)
	 * @param tsPayDate 放款日期(初识值)
	 * @param strPayFormNo 放款通知单号(初识值)
	 * @param lPayFormTypeID 放款通知单类型(查询条件:1,信托；2，委托 3，信托和委托)
	 * @param lTypeID 放款通知单使用条件(内部状态：
	 * 		0，显示全部；
	 * 		1，信托/委托发放——业务处理使用；
	 * 		2，信托/委托发放——业务复核使用；
	 * 		3，信托/委托收回——业务处理使用；
	 * 		4，信托/委托收回——业务复核使用。）
	 * @param strContractCtrl 合同ID对应的控件名称（查询时关联）
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnLoanNoteIDCtrl 返回值（放款通知单ID）对应的控件名
	 * @param strRtnLoanFormCodeCtrl 返回值（放款通知单编号）对应的控件名
	 * @param strRtnStartDateCtrl 返回值（起始日期）对应的控件名
	 * @param strRtnEndDateCtrl 返回值（到期日期）对应的控件名
	 * @param strRtnSubAccountIDCtrl 返回值（子账户ID）对应的控件名
	 * @param strRtnAccountBalanceCtrl 返回值（贷款余额）对应的控件名
	 */
	public static void createPayFormCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		long lClientID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lContractID,
		long lPayFormID,
		long lAccountID,
		Timestamp tsPayDate,
		String strPayFormNo,
		long lPayFormTypeID,
		long lTypeID,
		String strContractCtrl,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		String strRtnLoanNoteIDCtrl,
		String strRtnContractIDCtrl,
		String strRtnStartDateCtrl,
		String strRtnEndDateCtrl,
		String strRtnSubAccountIDCtrl,
		String strRtnAccountBalanceCtrl)
	{
		String strMagnifierName = URLEncoder.encode("放款日期");
		String strMainProperty = " maxlength='30' value='" + DataFormat.formatDate(tsPayDate) + "'";
		String strPrefix = "";
		if (strRtnContractIDCtrl == null || strRtnContractIDCtrl.equals(""))
		{
			strRtnContractIDCtrl = "ItIsNotControl";
		}
		if (strRtnStartDateCtrl == null || strRtnStartDateCtrl.equals(""))
		{
			strRtnStartDateCtrl = "ItIsNotControl";
		}
		if (strRtnEndDateCtrl == null || strRtnEndDateCtrl.equals(""))
		{
			strRtnEndDateCtrl = "ItIsNotControl";
		}
		if (strRtnSubAccountIDCtrl == null || strRtnSubAccountIDCtrl.equals(""))
		{
			strRtnSubAccountIDCtrl = "ItIsNotControl";
		}
		String[] strMainNames = { strCtrlName + "Ctrl", strRtnAccountBalanceCtrl, strCtrlName + "rate" ,strRtnContractIDCtrl};
		String[] strMainFields = { "PayDate", "LoanBalance", "rate" ,"ContractID"};
		String[] strReturnNames = { strCtrlName, strCtrlName+"ContractID", strCtrlName + "AccountID" };
		String[] strReturnFields = { "LoanNoteID", "ContractID", "LoanAccountID" };
		String[] strReturnValues = { String.valueOf(lPayFormID), String.valueOf(lContractID), String.valueOf(lAccountID)};
		String[] strDisplayNames = { URLEncoder.encode("放款通知单号"), URLEncoder.encode("放款日期"), URLEncoder.encode("贷款余额")};
		String[] strDisplayFields = { "PayFormCode", "PayDate", "LoanBalance" };
		int nIndex = 0;
		long[] lContractTypeIDs = null;
		//信托 ：LOANConstant.LoanType.ZYDQ  ZYZCQ  ZGXEDQ  ZGXEZCQ  YT
		if (lPayFormTypeID == 1)
		{
//			lContractTypeIDs =
//				new long[] {
//					LOANConstant.LoanType.ZYDQ,
//					LOANConstant.LoanType.ZYZCQ,
//					LOANConstant.LoanType.ZGXEDQ,
//					LOANConstant.LoanType.ZGXEZCQ,
//					LOANConstant.LoanType.YTDQ,
//					LOANConstant.LoanType.YTZCQ };
			lContractTypeIDs =
				new long[] {
					LOANConstant.LoanType.ZY,
					LOANConstant.LoanType.ZGXE,
					LOANConstant.LoanType.YT };
		}
		//委托 : WT  WTTJTH
		else if (lPayFormTypeID == 2)
		{
//			lContractTypeIDs = new long[] { LOANConstant.LoanType.WT, LOANConstant.LoanType.WTTJTH };
			lContractTypeIDs = new long[] { LOANConstant.LoanType.WT};
		}
		else if (lPayFormTypeID == 3)
		{
//			lContractTypeIDs =
//				new long[] {
//					LOANConstant.LoanType.ZYDQ,
//					LOANConstant.LoanType.ZYZCQ,
//					LOANConstant.LoanType.ZGXEDQ,
//					LOANConstant.LoanType.ZGXEZCQ,
//					LOANConstant.LoanType.YTDQ,
//					LOANConstant.LoanType.YTZCQ,
//					LOANConstant.LoanType.WT,
//					LOANConstant.LoanType.WTTJTH };
			lContractTypeIDs =
				new long[] {
					LOANConstant.LoanType.ZY,
					LOANConstant.LoanType.ZGXE,
					LOANConstant.LoanType.YT,
					LOANConstant.LoanType.WT };
		}
		else if (lPayFormTypeID == 4)
		{
			lContractTypeIDs = new long[] { LOANConstant.LoanType.YT };
//			lContractTypeIDs = new long[] { LOANConstant.LoanType.YTDQ, LOANConstant.LoanType.YTZCQ, };
		}
		long[] lStatusIDArray = null;
		//获得状态数组
		if (lTypeID == 0)
		{
			lStatusIDArray = new long[] { LOANConstant.LoanPayNoticeStatus.CHECK, LOANConstant.LoanPayNoticeStatus.USED };
		}
		else if (lTypeID == 1)
		{
			lStatusIDArray = new long[] { LOANConstant.LoanPayNoticeStatus.CHECK };
		}
		else if (lTypeID == 2)
		{
			lStatusIDArray = new long[] { LOANConstant.LoanPayNoticeStatus.USED };
		}
		else if (lTypeID == 3)
		{
			lStatusIDArray = new long[] { -100 };
		}
		else if (lTypeID == 4)
		{
			lStatusIDArray = new long[] { -200 };
		}
		else if (lTypeID == 5)
		{
			lStatusIDArray = new long[] { -300 };
		}
		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getPayFormSQL(");
		sbSQL.append(lOfficeID);
		sbSQL.append(",");
		sbSQL.append(lCurrencyID);
		sbSQL.append(",");
		sbSQL.append(lClientID);
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
		Log.print("**放大镜Sql**" + sbSQL.toString());
		try
		{
			OBMagnifier.showDifferZoomCtrl(
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
				false,
				"ShowDifferMagnifierZoom.jsp");
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
		 * 		5，交易费用——业务处理使用；
		 * 		6，交易费用——业务处理使用；）
		 * @param strContractCtrl 合同ID对应的控件名称（查询时关联）
		 * @param strFirstTD 第一个TD的属性
		 * @param strSecondTD 第二个TD的属性
		 * @param strNextControls 下一个（或多个）获得焦点的控件
		 * @param strRtnStartDateCtrl 返回值（起始日期）对应的控件名
		 * @param strRtnEndDateCtrl 返回值（到期日期）对应的控件名
		 * @param strRtnSubAccountIDCtrl 返回值（子账户ID）对应的控件名
		 * @param strRtnBalanceCtrl 返回值（放款单余额）对应的控件名
		 */
	public static void createPayFormNOCtrl(
		JspWriter out,
		long lClientID,
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
		String strMagnifierName = URLEncoder.encode("放款通知单");
		String strMainProperty = " maxlength='30' value='" + strPayFormNo + "'";
		String strPrefix = "";

		if (strRtnStartDateCtrl == null || strRtnStartDateCtrl.equals(""))
		{
			strRtnStartDateCtrl = "ItIsNotControl";
		}
		if (strRtnEndDateCtrl == null || strRtnEndDateCtrl.equals(""))
		{
			strRtnEndDateCtrl = "ItIsNotControl";
		}
		if (strRtnSubAccountIDCtrl == null || strRtnSubAccountIDCtrl.equals(""))
		{
			strRtnSubAccountIDCtrl = "ItIsNotControl";
		}
		if (strRtnBalanceCtrl == null || strRtnBalanceCtrl.equals(""))
		{
			strRtnBalanceCtrl = "ItIsNotControl";
		}

		String[] strMainNames = { strCtrlName + "Ctrl", strRtnStartDateCtrl, strRtnEndDateCtrl, strRtnSubAccountIDCtrl, strRtnBalanceCtrl };
		String[] strMainFields = { "PayFormCode", "StartDate", "EndDate", "SubAccountID", "Balance" };

		String[] strReturnNames = { strCtrlName, strCtrlName + "ContractID" };
		String[] strReturnFields = { "PayFormID", "ContractID" };
		String[] strReturnValues = { String.valueOf(lPayFormID), String.valueOf(lContractID)};

		String[] strDisplayNames = { URLEncoder.encode("放款通知单号"), URLEncoder.encode("起始日期"), URLEncoder.encode("放款日期")};
		String[] strDisplayFields = { "PayFormCode", "StartDate", "PayDate" };
		int nIndex = 0;

		long[] lContractTypeIDs = null;

		//信托 ：LOANConstant.LoanType.ZYDQ  ZYZCQ  ZGXEDQ  ZGXEZCQ  YT
		if (lPayFormTypeID == 1)
		{
//			lContractTypeIDs =
//				new long[] {
//					LOANConstant.LoanType.ZYDQ,
//					LOANConstant.LoanType.ZYZCQ,
//					LOANConstant.LoanType.ZGXEDQ,
//					LOANConstant.LoanType.ZGXEZCQ,
//					LOANConstant.LoanType.YTDQ,
//					LOANConstant.LoanType.YTZCQ };
			lContractTypeIDs =
				new long[] {
					LOANConstant.LoanType.ZY,
					LOANConstant.LoanType.ZGXE,
					LOANConstant.LoanType.YT };

		}
		//委托 : WT  WTTJTH
		else if (lPayFormTypeID == 2)
		{
//			lContractTypeIDs = new long[] { LOANConstant.LoanType.WT, LOANConstant.LoanType.WTTJTH };
			lContractTypeIDs = new long[] { LOANConstant.LoanType.WT };
		}
		else if (lPayFormTypeID == 0)
		{
//			lContractTypeIDs =
//				new long[] {
//					LOANConstant.LoanType.WT,
//					LOANConstant.LoanType.WTTJTH,
//					LOANConstant.LoanType.ZYDQ,
//					LOANConstant.LoanType.ZYZCQ,
//					LOANConstant.LoanType.ZGXEDQ,
//					LOANConstant.LoanType.ZGXEZCQ,
//					LOANConstant.LoanType.YTDQ,
//					LOANConstant.LoanType.YTZCQ };
			lContractTypeIDs =
				new long[] {
					LOANConstant.LoanType.WT,
					LOANConstant.LoanType.ZY,
					LOANConstant.LoanType.ZGXE,
					LOANConstant.LoanType.YT };
		}
		long[] lStatusIDArray = null;

		//获得状态数组
		if (lTypeID == 0)
		{
			lStatusIDArray = new long[] { LOANConstant.LoanPayNoticeStatus.CHECK, LOANConstant.LoanPayNoticeStatus.USED };
		}
		else if (lTypeID == 1)
		{
			lStatusIDArray = new long[] { LOANConstant.LoanPayNoticeStatus.CHECK };
		}
		else if (lTypeID == 2)
		{
			lStatusIDArray = new long[] { LOANConstant.LoanPayNoticeStatus.USED };
		}
		else if (lTypeID == 3)
		{
			lStatusIDArray = new long[] { -100 };
		}
		else if (lTypeID == 4)
		{
			lStatusIDArray = new long[] { -200 };
		}
		else if (lTypeID == 5)
		{
			lStatusIDArray = new long[] { -500 };
		}
		else if (lTypeID == 6)
		{
			lStatusIDArray = new long[] { -600 };
		}

		StringBuffer sbSQL = new StringBuffer(64);
		sbSQL.append("getPayFormNOSQL(");
		sbSQL.append(lClientID);
		sbSQL.append(",");
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
			OBMagnifier.showZoomCtrl(
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
				false);
		}
		catch (Exception e)
		{
			//e.printStackTrace();
			Log.print("放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}

	/**
	 * 创建付款方银行账号放大镜,收款方账号（内部转账）
	 * 关联操作权限
	 * @author yanliu
	 * @param out
	 * @param lUserID 用户ID
	 * @param lClientID 客户ID
	 * @param lInstructionID 当前交易ID
	 * @param lCurrencyID 币种ID
	 * @param sBankAccountCode 账号（初始值）
	 * @param strFormName 主页表单名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述)
	 * @param strClientNo 客户编号(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 */
	public static void createPayerAccountNoCtrl(
		JspWriter out,
		long lUserID,
		long lCurrencyID,
		long lInstructionID,
		long lClientID,
		String strRelativeForm1,
		String strRelativeForm2,
		String strRelativeForm3,
		String strFormName,
		String sBankAccountCode,
		String strCtrlName,
		String strTitle,
		String strFirstTD,
		String strSecondTD)
	{
		String strMagnifierName = (strRelativeForm2 != null && strRelativeForm2.length() > 0)?URLEncoder.encode("付款方账号"):URLEncoder.encode("收款方账号");
		String strMainProperty = " maxlength='40' value='" + NameRef.getNoLineAccountNo(sBankAccountCode) + "'";
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2, strRelativeForm3 };
		String[] strMainFields = { "saccountno", "nAccountID", "", "" };
		String[] strMainName = { strCtrlName + "Ctrl", strRelativeForm1,strRelativeForm3 }; //收款方
		String[] strMainField = { "saccountno", "nAccountID","sname" }; //收款方
		String[] strReturnNames = { "sName" };
		String[] strReturnFields = { "sname" };
		String[] strReturnValues = { "" };
		String[] strReturnName = { "sPayeeBankAccountNOInternal" }; //收款方
		String[] strReturnField = { "sname" }; //收款方
		String[] strReturnValue = { "" }; //收款方
		String[] strDisplayNames = { URLEncoder.encode("账号"), URLEncoder.encode("账户名称")};
		String[] strDisplayFields = { "displayAccountNo", "sname" };

	    
		if(strCtrlName.equals("sInterestPayeeAccountInternal")){
		 
		strReturnName[0]="PayeeBankAccountNOInternal";//利息收款方
		 }
		int nIndex = 0;
		String strSQL = "";
		if (strRelativeForm2 != null && strRelativeForm2.length() > 0)
		{
			strSQL = "getPayerAccountNoSQL(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
		}
		else
		{
			strSQL = "getInternalPayeeAccountNoSQL(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
		}
		String[] strNextControls = { "nRemitType" };
		try
		{
			if (!strRelativeForm2.equals("") && !strRelativeForm3.equals(""))
			{
				showPayerAcctZoomCtrl(
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
					false);
			}
			else
			{
				showZoomCtrl(
					out,
					strMagnifierName,
					strFormName,
					strPrefix,
					strMainName,
					strMainField,
					strReturnName,
					strReturnField,
					strReturnValue,
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
					false);
			}
		}
		catch (Exception e)
		{
		}
	}
	/**
	 * 创建付款方银行账号放大镜,收款方账号（内部转账）
	 * 关联操作权限
	 * @author yanliu
	 * @param out
	 * @param lUserID 用户ID
	 * @param lClientID 客户ID
	 * @param lInstructionID 当前交易ID
	 * @param lCurrencyID 币种ID
	 * @param sBankAccountCode 账号（初始值）
	 * @param strFormName 主页表单名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述)
	 * @param strClientNo 客户编号(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 */
	public static void createPayerAccountCtrlWithout(
		JspWriter out,
		long lUserID,
		long lCurrencyID,
		long lInstructionID,
		long lClientID,
		String strRelativeForm1,
		String strRelativeForm2,
		String strRelativeForm3,
		String strFormName,
		String sBankAccountCode,
		String strCtrlName,
		String strTitle,
		String strFirstTD,
		String strSecondTD)
	{
		String strMagnifierName = (strRelativeForm2 != null && strRelativeForm2.length() > 0)?URLEncoder.encode("付款方账号"):URLEncoder.encode("收款方账号");
		String strMainProperty = " maxlength='40' value='" + NameRef.getNoLineAccountNo(sBankAccountCode) + "'";
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2, strRelativeForm3 };
		String[] strMainFields = { "saccountno", "nAccountID", "", "" };
		String[] strMainName = { strCtrlName + "Ctrl", strRelativeForm1,strRelativeForm3 }; //收款方
		String[] strMainField = { "saccountno", "nAccountID","sname" }; //收款方
		String[] strReturnNames = { "sName" };
		String[] strReturnFields = { "sname" };
		String[] strReturnValues = { "" };
		String[] strReturnName = { "sPayeeBankAccountNOInternal" }; //收款方
		String[] strReturnField = { "sname" }; //收款方
		String[] strReturnValue = { "" }; //收款方
		String[] strDisplayNames = { URLEncoder.encode("账号"), URLEncoder.encode("账户名称")};
		String[] strDisplayFields = { "displayAccountNo", "sname" };

	    
		if(strCtrlName.equals("sInterestPayeeAccountInternal")){
		 
		strReturnName[0]="PayeeBankAccountNOInternal";//利息收款方
		 }
		int nIndex = 0;
		String strSQL = "";
		if (strRelativeForm2 != null && strRelativeForm2.length() > 0)
		{
			strSQL = "getPayerAccountSQLWithout(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
		}
		else
		{
			strSQL = "getInternalPayeeAccountNoSQL(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
		}
		String[] strNextControls = { "nRemitType" };
		try
		{
			if (!strRelativeForm2.equals("") && !strRelativeForm3.equals(""))
			{
				showPayerAcctZoomCtrl(
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
					false);
			}
			else
			{
				showZoomCtrl(
					out,
					strMagnifierName,
					strFormName,
					strPrefix,
					strMainName,
					strMainField,
					strReturnName,
					strReturnField,
					strReturnValue,
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
					false);
			}
		}
		catch (Exception e)
		{
		}
	}
	/**
	 * 创建付款方银行账号放大镜,收款方账号（内部转账）(国电专用)
	 * @author yanliu
	 * @param out
	 * @param lUserID 用户ID
	 * @param lClientID 客户ID
	 * @param lInstructionID 当前交易ID
	 * @param lCurrencyID 币种ID
	 * @param sBankAccountCode 账号（初始值）
	 * @param strFormName 主页表单名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述)
	 * @param strClientNo 客户编号(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 */
	public static void createPayerInterAccountNoCtrl(
		JspWriter out,
		long lUserID,
		long lCurrencyID,
		long lInstructionID,
		long lClientID,
		String strRelativeForm1,
		String strRelativeForm2,
		String strRelativeForm3,
		String strFormName,
		String sBankAccountCode,
		String strCtrlName,
		String strTitle,
		String strFirstTD,
		String strSecondTD)
	{
		String strMagnifierName = (strRelativeForm2 != null && strRelativeForm2.length() > 0)?URLEncoder.encode("付款方账号"):URLEncoder.encode("收款方账号");
		String strMainProperty = " maxlength='40' value='" + NameRef.getNoLineAccountNo(sBankAccountCode) + "'";
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2, strRelativeForm3 };
		String[] strMainFields = { "saccountno", "nAccountID", "", "" };
		String[] strMainName = { strCtrlName + "Ctrl", strRelativeForm1,strRelativeForm3 }; //收款方
		String[] strMainField = { "saccountno", "nAccountID","sname" }; //收款方
		String[] strReturnNames = { "sName" };
		String[] strReturnFields = { "sname" };
		String[] strReturnValues = { "" };
		String[] strReturnName = { "sPayeeBankAccountNOInternal" }; //收款方
		String[] strReturnField = { "sname" }; //收款方
		String[] strReturnValue = { "" }; //收款方
		String[] strDisplayNames = { URLEncoder.encode("账号"), URLEncoder.encode("账户名称")};
		String[] strDisplayFields = { "displayAccountNo", "sname" };

	    
		if(strCtrlName.equals("sInterestPayeeAccountInternal")){
		 
		strReturnName[0]="PayeeBankAccountNOInternal";//利息收款方
		 }
		int nIndex = 0;
		String strSQL = "";
		if (strRelativeForm2 != null && strRelativeForm2.length() > 0)
		{
			strSQL = "getPayerAccountNoSQL(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
		}
		else
		{
			//strSQL = "getInternalPayeeAccountNoSQL(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
			strSQL = "getInterPayeeAccountNoSQL(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";		
		}            
		String[] strNextControls = { "nRemitType" };
		try
		{
			if (!strRelativeForm2.equals("") && !strRelativeForm3.equals(""))
			{
				showPayerAcctZoomCtrl(
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
					false);
			}
			else
			{
				showZoomCtrl(
					out,
					strMagnifierName,
					strFormName,
					strPrefix,
					strMainName,
					strMainField,
					strReturnName,
					strReturnField,
					strReturnValue,
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
					false);
			}
		}
		catch (Exception e)
		{
		}
	}
	
	
	
	/**
	 * 创建付款方银行账号放大镜,中交，下划付款方放大镜，带出收款方相关资料
	 * @author yanliu
	 * @param out
	 * @param lUserID 用户ID
	 * @param lClientID 客户ID
	 * @param lInstructionID 当前交易ID
	 * @param lCurrencyID 币种ID
	 * @param sBankAccountCode 账号（初始值）
	 * @param strFormName 主页表单名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述)
	 * @param strClientNo 客户编号(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 */
	public static void createPayerAccountNoCtrl1(
		JspWriter out,
		long lUserID,
		long lCurrencyID,
		long lInstructionID,
		long lClientID,
		String strRelativeForm1,
		String strRelativeForm2,
		String strRelativeForm3,
		String strFormName,
		String sBankAccountCode,
		String strCtrlName,
		String strTitle,
		String strFirstTD,
		String strSecondTD)
	{
		String strMagnifierName = URLEncoder.encode("付款方账号");
		String strMainProperty = " maxlength='40' value='" + sBankAccountCode + "'";
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1,strRelativeForm2, strRelativeForm3 ,"sPayeeAcctNoDownZoomCtrl","sPayeeNameZoomAcctDownCtrl","sPayeeProvDown","sPayeeCityDown","sPayeeBankNameDown"};
		String[] strMainFields = { "sactno", "nActID","","","o1","o2","p","c","o3"};
		//String[] strMainName = { strCtrlName + "Ctrl", strRelativeForm1 }; //收款方
		//String[] strMainField = { "saccountno", "nAccountID" }; //收款方
		String[] strReturnNames = { "sName" };
		String[] strReturnFields = { "sname" };
		String[] strReturnValues = { "" };
		//String[] strReturnName = { "sPayeeBankAccountNOInternal" }; //收款方
		//String[] strReturnField = { "sname" }; //收款方
		//String[] strReturnValue = { "" }; //收款方
		String[] strDisplayNames = { URLEncoder.encode("账号"), URLEncoder.encode("账户名称")};
		String[] strDisplayFields = { "dANo", "sname" };
		int nIndex = 0;
		String strSQL = "";
	
		strSQL = "getPayerAccountNoSQL1(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
	
		
		String[] strNextControls = { "nRemitType" };
		try
		{
			showPayerAcctZoomCtrlZj(
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
					false);
			
		}
		catch (Exception e)
		{
		}
	}
/*
 * 中交，网银本转收款方放大镜
 */
	public static void createPayerAccountNoCtrlForZj(
			JspWriter out,
			long lUserID,
			long lCurrencyID,
			long lInstructionID,
			long lClientID,
			String strRelativeForm1,
			String strRelativeForm2,
			String strRelativeForm3,
			String strFormName,
			String sBankAccountCode,
			String strCtrlName,
			String strTitle,
			String strFirstTD,
			String strSecondTD)
		{
			String strMagnifierName = URLEncoder.encode("付款方账号");
			String strMainProperty = " maxlength='40' value='" + sBankAccountCode + "'";
			String strPrefix = "";
			String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2, strRelativeForm3 };
			String[] strMainFields = { "saccountno", "nAccountID", "", "" };
			String[] strMainName = { strCtrlName + "Ctrl", strRelativeForm1 }; //收款方
			String[] strMainField = { "saccountno", "nAccountID" }; //收款方
			String[] strReturnNames = { "sName" };
			String[] strReturnFields = { "sname" };
			String[] strReturnValues = { "" };
			String[] strReturnName = { "sPayeeBankAccountNOInternal" }; //收款方
			String[] strReturnField = { "sname" }; //收款方
			String[] strReturnValue = { "" }; //收款方
			String[] strDisplayNames = { URLEncoder.encode("内部账号"), URLEncoder.encode("内部账户名称")};
			String[] strDisplayFields = { "displayAccountNo", "sname" };
			int nIndex = 0;
			String strSQL = "";
			if (strRelativeForm2 != null && strRelativeForm2.length() > 0)
			{
				strSQL = "getPayerAccountNoSQL(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
			}
			else
			{
				strSQL = "getInternalPayeeAccountNoSQL(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
			}
			String[] strNextControls = { "dAmount" };
			try
			{
				if (!strRelativeForm2.equals("") && !strRelativeForm3.equals(""))
				{
					showPayerAcctZoomCtrl(
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
						false);
				}
				else
				{
					showZoomCtrl(
						out,
						strMagnifierName,
						strFormName,
						strPrefix,
						strMainName,
						strMainField,
						strReturnName,
						strReturnField,
						strReturnValue,
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
						false);
				}
			}
			catch (Exception e)
			{
			}
		}
	//网上银行付款方账户查询放大镜（关联操作权限）
	public static void createPayerAccountNoCtrl(
		JspWriter out,
		long lUserID,
		long lCurrencyID,
		long lInstructionID,
		long lClientID,
		String strRelativeForm1,
		String strRelativeForm2,
		String strRelativeForm3,
		String strFormName,
		String sBankAccountCode,
		String strCtrlName,
		String strTitle,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls)
	{
		String strMagnifierName = (strRelativeForm2 != null && strRelativeForm2.length() > 0)?URLEncoder.encode("付款方账号"):URLEncoder.encode("收款方账号");
		String strMainProperty = " maxlength='40' value='" + NameRef.getNoLineAccountNo(sBankAccountCode) + "'";
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2, strRelativeForm3 };
		String[] strMainFields = { "saccountno", "nAccountID", "", "" };
		String[] strMainName = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm3}; //收款方
		String[] strMainField = { "saccountno", "nAccountID","sname" }; //收款方
		String[] strReturnNames = { "sName" };
		String[] strReturnFields = { "sname" };
		String[] strReturnValues = { "" };
	   
	     String[]  strReturnName = { "sPayeeBankAccountNOInternal" }; //收款方
		if(strCtrlName.equals("sInterestPayeeAccountInternal")){
		 
		strReturnName[0]="PayeeBankAccountNOInternal";//利息收款方
		 }
	     
		String[] strReturnField = { "sname" }; //收款方
		String[] strReturnValue = { "" }; //收款方
		String[] strDisplayNames = { URLEncoder.encode("账号"), URLEncoder.encode("账户名称")};
		String[] strDisplayFields = { "displayAccountNo", "sname" };
		int nIndex = 0;
		String strSQL = "";
		
		if (strRelativeForm2 != null && strRelativeForm2.length() > 0)
		{
			strSQL = "getPayerAccountNoSQL(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
		}
		else
		{
			strSQL = "getInternalPayeeAccountNoSQL(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
		}
		//String[] strNextControls = { "nRemitType" };
		try
		{
			if (!strRelativeForm2.equals("") && !strRelativeForm3.equals(""))
			{
				showPayerAcctZoomCtrl(
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
					false);
			}
			else
			{
				showZoomCtrl(
					out,
					strMagnifierName,
					strFormName,
					strPrefix,
					strMainName,
					strMainField,
					strReturnName,
					strReturnField,
					strReturnValue,
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
					false);
			}
		}
		catch (Exception e)
		{
		}
	}
	/**
	 * 关联机构开机日
	 * @param out
	 * @param lUserID
	 * @param lCurrencyID
	 * @param lInstructionID
	 * @param lClientID
	 * @param strRelativeForm1
	 * @param strRelativeForm2
	 * @param strRelativeForm3
	 * @param strFormName
	 * @param sBankAccountCode
	 * @param strCtrlName
	 * @param strTitle
	 * @param strFirstTD
	 * @param strSecondTD
	 * @param strNextControls
	 */
	
	public static void createPayerAccountNoCtrlByDate(
			JspWriter out,
			long lUserID,
			long lCurrencyID,
			long lOfficeId,
			long lInstructionID,
			long lClientID,
			String strRelativeForm1,
			String strRelativeForm2,
			String strRelativeForm3,
			String strRelativeForm4,
			String strFormName,
			String sBankAccountCode,
			String strCtrlName,
			String strTitle,
			String strFirstTD,
			String strSecondTD,
			String[] strNextControls)
		{
			//获取当前机构系统开机日期
			Timestamp opendate = Env.getSystemDate(lOfficeId,lCurrencyID);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String strOpenDate = sdf.format(opendate);
			String strMagnifierName = (strRelativeForm2 != null && strRelativeForm2.length() > 0)?URLEncoder.encode("付款方账号"):URLEncoder.encode("收款方账号");
			String strMainProperty = " maxlength='40' value='" + NameRef.getNoLineAccountNo(sBankAccountCode) + "'";
			String strPrefix = "";
			String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2, strRelativeForm3,strRelativeForm4 };
			String[] strMainFields = { "saccountno", "nAccountID", "", "", "dtopendate" };
			String[] strReturnNames = { "sName","hiddenOpendate" };
			String[] strReturnFields = { "sname","dtopendate"};
			String[] strReturnValues = { "",strOpenDate};
			String[] strDisplayNames = { URLEncoder.encode("账号"), URLEncoder.encode("账户名称")};
			String[] strDisplayFields = { "displayAccountNo", "sname" };
			int nIndex = 0;
			String strSQL = "";
			strSQL = "getPayerAccountNoSQLByDate(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";

			try
			{
					showPayerAcctZoomCtrl(
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
						false);
			}
			catch (Exception e)
			{
			}
		}	
	//业务复核new 专用放大镜（逐笔付款，通知开立）
	
	public static void createPayerAccountCheckCtrl(
		JspWriter out,
		long lUserID,
		long lCurrencyID,
		long lInstructionID,
		long lClientID,
		String strRelativeForm1,
		String strRelativeForm2,
		String strRelativeForm3,
		String strFormName,
		String sBankAccountCode,
		String strCtrlName,
		String strTitle,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls)
	{
		String strMagnifierName = (strRelativeForm2 != null && strRelativeForm2.length() > 0)?URLEncoder.encode("付款方账号"):URLEncoder.encode("收款方账号");
		String strMainProperty = " maxlength='40' value='" + NameRef.getNoLineAccountNo(sBankAccountCode) + "'";
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2, strRelativeForm3 };
		String[] strMainFields = { "saccountno", "nAccountID", "", "" };
		String[] strMainName = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm3}; //收款方
		String[] strMainField = { "saccountno", "nAccountID","sname" }; //收款方
		String[] strReturnNames = { "sName" };
		String[] strReturnFields = { "sname" };
		String[] strReturnValues = { "" };
	   
	     String[]  strReturnName = { "sPayeeBankAccountNOInternal" }; //收款方
		if(strCtrlName.equals("sInterestPayeeAccountInternal")){
		 
		strReturnName[0]="PayeeBankAccountNOInternal";//利息收款方
		 }
	     
		String[] strReturnField = { "sname" }; //收款方
		String[] strReturnValue = { "" }; //收款方
		String[] strDisplayNames = { URLEncoder.encode("账号"), URLEncoder.encode("账户名称")};
		String[] strDisplayFields = { "displayAccountNo", "sname" };
		int nIndex = 0;
		String strSQL = "";
		
		if (strRelativeForm2 != null && strRelativeForm2.length() > 0)
		{
			strSQL = "getPayerAccountCheckSQLWithout(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
		}
		else
		{
			strSQL = "getInternalPayeeAccountNoSQL(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
		}
		//String[] strNextControls = { "nRemitType" };
		try
		{
			if (!strRelativeForm2.equals("") && !strRelativeForm3.equals(""))
			{
				showPayerAcctZoomCtrl(
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
					false);
			}
			else
			{
				showZoomCtrl(
					out,
					strMagnifierName,
					strFormName,
					strPrefix,
					strMainName,
					strMainField,
					strReturnName,
					strReturnField,
					strReturnValue,
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
					false);
			}
		}
		catch (Exception e)
		{
		}
	}


	
	
	
	//网上银行付款方账户查询放大镜（没有关联操作权限）
	public static void createPayerAccountCtrlWithout(
			JspWriter out,
			long lUserID,
			long lCurrencyID,
			long lInstructionID,
			long lClientID,
			String strRelativeForm1,
			String strRelativeForm2,
			String strRelativeForm3,
			String strFormName,
			String sBankAccountCode,
			String strCtrlName,
			String strTitle,
			String strFirstTD,
			String strSecondTD,
			String[] strNextControls)
		{
			String strMagnifierName = (strRelativeForm2 != null && strRelativeForm2.length() > 0)?URLEncoder.encode("付款方账号"):URLEncoder.encode("收款方账号");
			String strMainProperty = " maxlength='40' value='" + NameRef.getNoLineAccountNo(sBankAccountCode) + "'";
			String strPrefix = "";
			String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2, strRelativeForm3 };
			String[] strMainFields = { "saccountno", "nAccountID", "", "" };
			String[] strMainName = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm3}; //收款方
			String[] strMainField = { "saccountno", "nAccountID","sname" }; //收款方
			String[] strReturnNames = { "sName" };
			String[] strReturnFields = { "sname" };
			String[] strReturnValues = { "" };
		   
		     String[]  strReturnName = { "sPayeeBankAccountNOInternal" }; //收款方
			if(strCtrlName.equals("sInterestPayeeAccountInternal")){
			 
			strReturnName[0]="PayeeBankAccountNOInternal";//利息收款方
			 }
		     
			String[] strReturnField = { "sname" }; //收款方
			String[] strReturnValue = { "" }; //收款方
			String[] strDisplayNames = { URLEncoder.encode("账号"), URLEncoder.encode("账户名称")};
			String[] strDisplayFields = { "displayAccountNo", "sname" };
			int nIndex = 0;
			String strSQL = "";
			
			if (strRelativeForm2 != null && strRelativeForm2.length() > 0)
			{
				strSQL = "getPayerAccountSQLWithout(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
			}
			else
			{
				strSQL = "getInternalPayeeAccountNoSQL(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
			}
			
			//String[] strNextControls = { "nRemitType" };
			try
			{
				if (!strRelativeForm2.equals("") && !strRelativeForm3.equals(""))
				{
					showPayerAcctZoomCtrl(
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
						false);
				}
				else
				{
					showZoomCtrl(
						out,
						strMagnifierName,
						strFormName,
						strPrefix,
						strMainName,
						strMainField,
						strReturnName,
						strReturnField,
						strReturnValue,
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
						false);
				}
			}
			catch (Exception e)
			{
			}
		}
	
	
	//国电专用
	public static void createPayerInterAccountNoCtrl(
			JspWriter out,
			long lUserID,
			long lCurrencyID,
			long lInstructionID,
			long lClientID,
			String strRelativeForm1,
			String strRelativeForm2,
			String strRelativeForm3,
			String strFormName,
			String sBankAccountCode,
			String strCtrlName,
			String strTitle,
			String strFirstTD,
			String strSecondTD,
			String[] strNextControls)
		{
			String strMagnifierName = (strRelativeForm2 != null && strRelativeForm2.length() > 0)?URLEncoder.encode("付款方账号"):URLEncoder.encode("收款方账号");
			String strMainProperty = " maxlength='40' value='" + NameRef.getNoLineAccountNo(sBankAccountCode) + "'";
			String strPrefix = "";
			String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2, strRelativeForm3 };
			String[] strMainFields = { "saccountno", "nAccountID", "", "" };
			String[] strMainName = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm3}; //收款方
			String[] strMainField = { "saccountno", "nAccountID","sname" }; //收款方
			String[] strReturnNames = { "sName" };
			String[] strReturnFields = { "sname" };
			String[] strReturnValues = { "" };
		   
		     String[]  strReturnName = { "sPayeeBankAccountNOInternal" }; //收款方
			if(strCtrlName.equals("sInterestPayeeAccountInternal")){
			 
			strReturnName[0]="PayeeBankAccountNOInternal";//利息收款方
			 }
		     
			String[] strReturnField = { "sname" }; //收款方
			String[] strReturnValue = { "" }; //收款方
			String[] strDisplayNames = { URLEncoder.encode("账号"), URLEncoder.encode("账户名称")};
			String[] strDisplayFields = { "displayAccountNo", "sname" };
			int nIndex = 0;
			String strSQL = "";
			if (strRelativeForm2 != null && strRelativeForm2.length() > 0)
			{
				strSQL = "getPayerAccountNoSQL(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
			}
			else
			{
				strSQL = "getInterPayeeAccountNoSQL(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
			}
			//String[] strNextControls = { "nRemitType" };
			try
			{
				if (!strRelativeForm2.equals("") && !strRelativeForm3.equals(""))
				{
					showPayerAcctZoomCtrl(
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
						false);
				}
				else
				{
					showZoomCtrl(
						out,
						strMagnifierName,
						strFormName,
						strPrefix,
						strMainName,
						strMainField,
						strReturnName,
						strReturnField,
						strReturnValue,
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
						false);
				}
			}
			catch (Exception e)
			{
			}
		}
	
	
	public static void createPayerAccountNoCtrl1(
			JspWriter out,
			long lUserID,
			long lCurrencyID,
			long lInstructionID,
			long lClientID,
			String strRelativeForm1,
			String strRelativeForm2,
			String strRelativeForm3,
			String strRelativeForm4,
			String strFormName,
			String sBankAccountCode,
			String strCtrlName,
			String strTitle,
			String strFirstTD,
			String strSecondTD,
			String[] strNextControls)
		{
			String strMagnifierName = URLEncoder.encode("付款方账号");
			String strMainProperty = " maxlength='40' value='" + NameRef.getNoLineAccountNo(sBankAccountCode) + "'";
			String strPrefix = "";
			String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2, strRelativeForm3,strRelativeForm4 };
			String[] strMainFields = { "saccountno", "nAccountID", "", "" ,"issoft"};
			String[] strMainName = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm3}; //收款方
			String[] strMainField = { "saccountno", "nAccountID","sname"  }; //收款方
			String[] strReturnNames = { "sName" };
			String[] strReturnFields = { "sname" };
			String[] strReturnValues = { "" };
		   
		     String[]  strReturnName = { "sPayeeBankAccountNOInternal" }; //收款方
			if(strCtrlName.equals("sInterestPayeeAccountInternal")){
			 
			strReturnName[0]="PayeeBankAccountNOInternal";//利息收款方
			 }
		     
			String[] strReturnField = { "sname" }; //收款方
			String[] strReturnValue = { "" }; //收款方
			String[] strDisplayNames = { URLEncoder.encode("账号"), URLEncoder.encode("账户名称")};
			String[] strDisplayFields = { "displayAccountNo", "sname" };
			int nIndex = 0;
			String strSQL = "";
			if (strRelativeForm2 != null && strRelativeForm2.length() > 0)
			{
				strSQL = "getPayerAccountNoSQL2(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
				
			}
			else
			{
				strSQL = "getInternalPayeeAccountNoSQL(" + strCtrlName + "Ctrl.value," + lUserID + "," + lClientID + "," + lCurrencyID + "," + lInstructionID + ")";
			}
			//String[] strNextControls = { "nRemitType" };
			try
			{
				if (!strRelativeForm2.equals("") && !strRelativeForm3.equals(""))
				{
					showPayerAcctZoomCtrl(
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
						false);
				}
				else
				{
					showZoomCtrl(
						out,
						strMagnifierName,
						strFormName,
						strPrefix,
						strMainName,
						strMainField,
						strReturnName,
						strReturnField,
						strReturnValue,
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
						false);
				}
			}
			catch (Exception e)
			{
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
		String strRtnStartDateCtrl,
		String strAmount)
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
		if (strAmount != null && strAmount != "")
		{
			strMainProperty += " onblur=\"adjustAmount('" + strFormName + "','" + strAmount + "',1,'sChineseAmount','" + lCurrencyID + "');\" ";
			//strMainProperty += " onfocus=\"adjustAmount('" + strFormName +"','" + strRtnCapitalCtrl + "',2,'sChineseAmount','" + lCurrencyID+"');\" ";
		}
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
		if (strAmount == null || strAmount.equals(""))
		{
			strAmount = "ItIsNotControl";
		}
		String[] strMainNames =
			{ strCtrlName + "Ctrl", strRtnEndDateCtrl, strRtnOpenDateCtrl, strRtnCapitalCtrl, strRtnBalanceCtrl, strRtnRateCtrl, strRtnIntervalCtrl, strRtnStartDateCtrl, strAmount };
		String[] strMainFields = { "DepositNo", "EndDate", "OpenDate", "Capital", "Balance", "Rate", "Interval", "StartDate", "Balance" };
		String[] strReturnNames = { strCtrlName, strCtrlName + "AccountID" };
		String[] strReturnFields = { "SubAccountID", "AccountID" };
		String[] strReturnValues = { String.valueOf(lSubAccountID), String.valueOf(lAccountID)};
		String[] strDisplayNames = { URLEncoder.encode("存款单据号")};
		String[] strDisplayFields = { "DepositNo" };
		//支取或续期转存时，显示不同
		if (lTypeID == 21 || lTypeID == 22 || lTypeID == 23 || lTypeID == 2 || lTypeID == 3)
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
		boolean isCreateNewBook = Config.getBoolean(ConfigConstant.SETT_FIXEDDRAW_CREATENEWBOOK, true);
		if (strAccountIDCtrl != null && !strAccountIDCtrl.trim().equals(""))
		{
			if(isCreateNewBook && lDepositTypeID == 1){
				
				strSQL =
					"getFixedDepositNoSQL_CREATE_OLD("
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
				
			}else{
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
		Log.print("----------sql=" + strSQL);
		try
		{
			OBMagnifier.showZoomCtrl(
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
					false);
				
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
	 * 创建下属单位客户放大镜
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
	public static void createChildClientCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lClientID,
		String strClientNo,
		long clientId,
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
		String strSQL = "getChildClientSQL(" + lOfficeID + "," +clientId+","+ strCtrlName + "Ctrl.value)";
		try
		{
			OBMagnifier.showZoomCtrl(
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
				strSecondTD,false,false);
		}
		catch (Exception e)
		{
			Log.print("客户放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	
	/**
	 * 创建资金计划放大镜
	 * @author 郭英亮
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lClientID 客户ID(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 * @param strRtnClientNameCtrl 返回值（计划编号）对应的控件名
	 */
	public static void createFundPlanCtrl(
			JspWriter out,
			long lOfficeID,
			long lCurrencyID,
			String strFormName,
			String strCtrlName,
			String strTitle,
			long lClientID,
			String strFirstTD,
			String strSecondTD,
			String[] strNextControls)
		{
			String strMagnifierName = URLEncoder.encode("资金计划放大镜");
			String strMainProperty = "";
			String strPrefix = "";
			String[] strMainNames = { strCtrlName + "Ctrl"};
			String[] strMainFields = { "cpcode" };
			String[] strReturnNames = { strCtrlName};
			String[] strReturnFields = { "id" };
			String[] strReturnValues = {""};
			String[] strDisplayNames = { URLEncoder.encode("序号"), URLEncoder.encode("资金计划编号")};
			String[] strDisplayFields = { "id", "cpcode" };
			int nIndex = 0;
			String strSQL = "getFundPlanSQL("+strMainNames[0]+".value,"+lCurrencyID+","+lClientID+","+lOfficeID+")";
			try
			{
				OBMagnifier.showZoomCtrl(
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
					strSecondTD,false,false);
			}
			catch (Exception e)
			{
				Log.print("资金计划放大镜[" + strCtrlName + "]异常：" + e.toString());
			}
		}
	
	/**
	 * 下级账户放大镜过滤字段：
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
	 * @param clientID
	 */
	 public static void createSubAccountCtrlReturnCode(JspWriter out, String strFormName, String strCtrlName, long lAccountID, String strAccountNO, String strRtnAccountNameCtrlName, String strNextControls[], 
	            String sConditions[], long officeID, long clientID)
	    {
	        String strMagnifierName = URLEncoder.encode("账户");
	        String strTitle = "下属账号";
	        String strMainProperty = " size='20' maxlength='30' value='" + strAccountNO + "'";
	        String strPrefix = "";
	        if(strRtnAccountNameCtrlName == null || strRtnAccountNameCtrlName.equals(""))
	            strRtnAccountNameCtrlName = "ItIsNotControl";
	        String strMainNames[] = {
	            strCtrlName + "Ctrl", strRtnAccountNameCtrlName
	        };
	        String strMainFields[] = {
	            "AccountNO", "AccountName"
	        };
	        String strReturnNames[] = {
	            strCtrlName
	        };
	        String strReturnFields[] = {
	            "AccountID"
	        };
	        String strReturnValues[] = {
	            String.valueOf(lAccountID)
	        };
	        String strDisplayNames[] = {
	        		URLEncoder.encode("账号"), URLEncoder.encode("账户名称"), URLEncoder.encode("银行名称")
	        };
	        String strDisplayFields[] = {
	            "AccountNO", "AccountName", "BankName"
	        };
	        int nIndex = 0;
	        String strSQL = null;
	        StringBuffer bufferSQL = new StringBuffer();
	        bufferSQL.append("getSubAccountSQLForEbank(" + strCtrlName + "Ctrl.value," + clientID + ",");
//	        if(Env.isHQ(officeID))
//	            bufferSQL.append("-1");
//	        else
	            bufferSQL.append(officeID);
	        if(sConditions == null || sConditions.length <= 0)
	        {
	            strSQL = bufferSQL.toString() + ")";
	        } else
	        {
	            bufferSQL.append(",");
	            int length = sConditions.length - 1;
	            for(int i = 0; i < sConditions.length; i++)
	            {
	                if(sConditions[i] == null)
	                    bufferSQL.append("-1");
	                else
	                    bufferSQL.append(sConditions[i] + ".value");
	                if(i < length)
	                    bufferSQL.append(",");
	            }

	            bufferSQL.append(")");
	            strSQL = bufferSQL.toString();
	        }
	        System.out.println("***strSQL=" + strSQL);
	        try
	        {
	        	OBMagnifier.showZoomCtrl(
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
	    				"","",false,false);
	        }
	        catch(Exception e)
	        {
	            Log.print("账户放大镜[" + strCtrlName + "]异常：" + e.toString());
	        }
	    }

	
	/**
	 * 显示付款方账号（网银）放大镜
	 * @author yanliu
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
	public static void showPayerAcctZoomCtrl(
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
					+ ".disabled == false) {gnIsSelectCtrl=1;window.open('/NASApp/iTreasury-ebank/magnifier/ShowBankAccountCodeZoom.jsp?"
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
					"<td "
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
				out.println("<td " + strFirstTD + ">" + strTitle + "：&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/webob/graphics/zoom.gif' border=0 width='568'></a></td>");
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
	/*
	 * 中交,为网银 下划成员单位 付款方放大镜添加
	 * */
	public static void showPayerAcctZoomCtrlZj(
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
						+ ".disabled == false) {gnIsSelectCtrl=1;window.open('/NASApp/iTreasury-ebank/magnifier/ShowBankAccountXH.jsp?"
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
						"<td "
							+ strFirstTD
							+ ">"
							+ strTitle
							+ ":&nbsp;"
							+ "<a href=#><img name=\""
							+ strButtonName
							+ "\" src='/webob/graphics/zoom.gif' border=0 onclick=\""
							+ sOnKeydown
							+ "\"></a></td>");
					//image
				}
				else
				{
					out.println("<td " + strFirstTD + ">" + strTitle + ":&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/webob/graphics/zoom.gif' border=0 ></a></td>");
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
					+ " gnIsSelectCtrl=1;window.open('/NASApp/iTreasury-ebank/magnifier/ShowMagnifierZoom.jsp?"
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
					"<td width=130"
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
			strMatchValue,
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
		String strMatchValue,
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
			strParam += "&strMagnifierName=" + strMagnifierName;
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
					+ "/iTreasury-ebank/magnifier/ShowMagnifierZoom.jsp?"
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
						+ "<a href=#><img name=\""
						+ strButtonName
						+ "\" src='/webob/graphics/icon.gif' border=0 onclick=\""
						+ sOnKeydown
						+ "\"></a></td>");
			}
			else
			{
				out.println("<td " + strFirstTD + " >" + strTitle + ":&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/webob/graphics/icon.gif' border=0 ></a></td>");
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
							+ " ><input style='background-color:#ffffff;' type=\"text\" name=\""
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
			if (strMatchValue.equals("0"))
			{
				out.println(" sql = \"\" ");
			}
			else
			{
				out.println(" sql +=  \" and " + strMatchValue + " like '" + URLEncoder.encode("%") + "\"+str+\"" + URLEncoder.encode("%") + "'\";    ");
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
	* Added by leiyang 2008/02/29 
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
	public static void showNewZoomCtrl(
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
			strParam += "&strMagnifierName=" + strMagnifierName;
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
					+ "/iTreasury-ebank/magnifier/ShowMagnifierZoom.jsp?"
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
			
			int iPos = -1;
			//显示控件
			if (iPos == -1)
			{
				out.println(
					"<td "
						+ strFirstTD
						+ " >"
						+ strTitle
						//+ "：&nbsp;"
						+ "<a href=#><img name=\""
						+ strButtonName
						+ "\" src='/webob/graphics/icon.gif' border=0 onclick=\""
						+ sOnKeydown
						+ "\"></a></td>");
			}
			else
			{
				out.println("<td " + strFirstTD + " >" + strTitle + "<a href=#><img name=\"" + strButtonName + "\" src='/webob/graphics/icon.gif' border=0 ></a></td>");
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
				out.println(" sql +=  \" and " + strMatchValue + " like '" + URLEncoder.encode("%") + "\"+str+\"" + URLEncoder.encode("%") + "'\";    ");
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
					//UtilOperation utiloperation = new UtilOperation();
					//strReturn = utiloperation.getOpenDepositNo(lID);
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
		PreparedStatement ps = null, ps1 = null;
		ResultSet rs = null, rs1 = null;
		Connection conn = null;
		Vector vResult = new Vector();
		try
		{
			conn = Database.getConnection();
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			/*for( int i =0 ;i< rs.getMetaData().getColumnCount();i++){
			System.out.println( "((((((((((  " + rs.getMetaData().getColumnName(i));
			}*/
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
					oMainCols[0] = NameRef.getNoLineAccountNo(oMainCols[0].toString());
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
	 * 针对网银中付款方账号放大镜的特殊方法，放大镜回显栏位值无法在同一sql语句中查出
	 * @author yanliu
	 * @param strMainFields
	 * @param strReturnFields
	 * @param strDisplayFields
	 * @param strSQL
	 * @return
	 * @throws Exception
	 */
	public static Vector getOBAccountCommonSelectList(String[] strMainFields, String[] strReturnFields, String[] strDisplayFields, String strSQL) throws Exception
	{
		CommonSelectInfo info = null;
		PreparedStatement ps = null, ps1 = null;
		ResultSet rs = null, rs1 = null;
		Connection conn = null;
		Vector vResult = new Vector();
		OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
		AccountBalanceInfo accountinfo = new AccountBalanceInfo();
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
				long lTempMain = rs.getLong(strMainFields[1]);
				long lCurrencyID = rs.getLong("CurrencyID");
				long lInstructionID = rs.getLong("InstructionID");
				accountinfo = obFinanceInstrDao.getCurrBalanceByAccountID(lTempMain, lCurrencyID, lInstructionID);
				oMainCols[0] = NameRef.getNoLineAccountNo(rs.getObject(strMainFields[0]).toString());
				oMainCols[1] = rs.getObject(strMainFields[1]);
				oMainCols[2] = DataFormat.formatDisabledAmount(accountinfo.getCurrentBalance(),2);
				oMainCols[3] = DataFormat.formatDisabledAmount(accountinfo.getUsableBalance(),2);
				if(oMainCols.length>4)
				{
					for(int j=oMainCols.length-1;j>=4;j--)
					{
						oMainCols[j] = rs.getObject(strMainFields[j]);
					}
				}
				/* added by mzh_fu 2008/03/13 增加账户体系的控制 begin*/
				AccountSystemDelegation accountSystemDelegation = new AccountSystemDelegation();
				AccountSystemInfo accountSystemInfo = new AccountSystemInfo();
				accountSystemInfo.setNAccountId(lTempMain);
               //modify by xwhe
				double dCPF2Amount = obFinanceInstrDao.getUsableBalanceByAccountID(lTempMain, lCurrencyID, lInstructionID);
				if(accountSystemDelegation.isInAccountSystem(accountSystemInfo))
				{
					String accoutCanUseBalance = DataFormat.formatDisabledAmount(accountSystemDelegation.getAccoutCanUseBalance(accountSystemInfo)-dCPF2Amount);
					oMainCols[3] ="".equals(accoutCanUseBalance)?"0.00":accoutCanUseBalance;	
				}
				//	oMainCols[3] = DataFormat.formatDisabledAmount(accountSystemDelegation.getAccoutCanUseBalance(accountSystemInfo));
				/* added by mzh_fu 2008/03/13 增加账户体系的控制 end*/
				
				Log.print("-------1:" + oMainCols[0]);
				Log.print("-------2:" + oMainCols[1]);
				Log.print("-------3:" + oMainCols[2]);
				Log.print("-------4:" + oMainCols[3]);
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
	 * 针对网银中付款方账号放大镜的特殊方法，放大镜回显栏位值无法在同一sql语句中查出
	 * @author yanliu
	 * @param strMainFields
	 * @param strReturnFields
	 * @param strDisplayFields
	 * @param strSQL
	 * @return
	 * @throws Exception
	 */
	public static Vector getOBAccountCommonSelectListXH(String[] strMainFields, String[] strReturnFields, String[] strDisplayFields, String strSQL) throws Exception
	{
		CommonSelectInfo info = null;
		PreparedStatement ps = null, ps1 = null;
		ResultSet rs = null, rs1 = null;
		Connection conn = null;
		Vector vResult = new Vector();
		OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
		AccountBalanceInfo accountinfo = new AccountBalanceInfo();
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
				long lTempMain = rs.getLong(strMainFields[1]);
				long lCurrencyID = rs.getLong("CurrencyID");
				long lInstructionID = rs.getLong("InstructionID");
				accountinfo = obFinanceInstrDao.getCurrBalanceByAccountID(lTempMain, lCurrencyID, lInstructionID);
				oMainCols[0] = rs.getObject(strMainFields[0]);
				oMainCols[1] = rs.getObject(strMainFields[1]);
				oMainCols[2] = DataFormat.formatDisabledAmount(accountinfo.getCurrentBalance());
				oMainCols[3] = DataFormat.formatDisabledAmount(accountinfo.getUsableBalance());
				oMainCols[4] = rs.getObject(strMainFields[4]);
				oMainCols[5] = rs.getObject(strMainFields[5]);
				oMainCols[6] = rs.getObject(strMainFields[6]);
				oMainCols[7] = rs.getObject(strMainFields[7]);
				oMainCols[8] = rs.getObject(strMainFields[8]);
				if(oMainCols[4]==" " || oMainCols[4]==null || oMainCols[4]=="null")
				{ 
					oMainCols[4]="";
				}
				if(oMainCols[5]==" " || oMainCols[5]==null || oMainCols[5]=="null")
				{ 
					oMainCols[5]="";
				}
				if(oMainCols[6]==" " || oMainCols[6]==null || oMainCols[6]=="null")
				{ 
					oMainCols[6]="";
				}
				if(oMainCols[7]==" " || oMainCols[7]==null || oMainCols[7]=="null")
				{ 
					oMainCols[7]="";
				}
				if(oMainCols[8]==" " || oMainCols[8]==null || oMainCols[8]=="null")
				{ 
					oMainCols[8]="";
				}
				Log.print("-------1:" + oMainCols[0]);
				Log.print("-------2:" + oMainCols[1]);
				Log.print("-------3:" + oMainCols[2]);
				Log.print("-------4:" + oMainCols[3]);
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
	 * 创建账户放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lClientID 客户ID(初识值)
	 * @param lAccountID 账户ID(初识值)
	 * @param strAccountNo 账号(初识值)
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
		String[] strDisplayNames = { URLEncoder.encode("账号"), URLEncoder.encode("账户名称")};
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
			//修改 by kenny(胡志强)(2007-03-26) 处理账号编码规则问题
			//账号的段间符号
			String tag = Config.getProperty(ConfigConstant.GLOBAL_ACCOUNTNO_TAG,"-");
			//账号的第一段的类型
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
			OBMagnifier.showSpecialZoomCtrl(
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
		String strTitle="账号";
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
			OBMagnifier.showZoomCtrl(out, strMagnifierName, strFormName,
					strPrefix, strMainNames, strMainFields, strReturnNames,
					strReturnFields, strReturnValues, strDisplayNames,
					strDisplayFields, nIndex, strMainProperty, strSQL,
					strNextControls,strTitle,null,null,false,false);
		} catch (Exception e) {
			Log.print("账户放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	}
	//关联查看权限的放大镜查询
	public static void createAccountCtrlReturnCodeByAuthority(JspWriter out,
			String strFormName, String strCtrlName, long lAccountID,
			String strAccountNO, String strRtnAccountNameCtrlName,
			String[] strNextControls, String[] sConditions, long officeID) {
		String strMagnifierName = URLEncoder.encode("账户");
		String strMainProperty = " size='20' maxlength='30' value='"+ strAccountNO + "'";
		String strPrefix = "";
		String strTitle="账号";
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
		bufferSQL.append("getAccountSQLByAuthority(" + strCtrlName + "Ctrl.value,");
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
			OBMagnifier.showZoomCtrl(out, strMagnifierName, strFormName,
					strPrefix, strMainNames, strMainFields, strReturnNames,
					strReturnFields, strReturnValues, strDisplayNames,
					strDisplayFields, nIndex, strMainProperty, strSQL,
					strNextControls,strTitle,null,null,false,false);
		} catch (Exception e) {
			Log.print("账户放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
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
	 * @param strAccountNo 账号
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

			/*修改 by kenny (胡志强) 解决账号段数的问题*/
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
			/*修改 by kenny (胡志强) (2007-03-21) 解决账号段数的问题*/
			//组装账号模糊批匹配的值
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
			//将账号拆分
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
			else
				if (nCaseNumber >= 4)
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
	 * @param strAccountNo 账号
	 * @param strTitle 栏位标题
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性 
	 * @throws Exception
	 */
	public static void showSpecialZoomCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
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
		String strNextControls,
		String strAccountNo,
		String strTitle,
		String strFirstTD,
		String strSecondTD)
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
			//初始默认值
			if (strAccountNo == null || strAccountNo.equals(""))
			{
				/*
				if (lOfficeID > 0)
				{
					strAccountNo = NameRef.getOfficeNoByID(lOfficeID);
				}
				*/
				//账号的第一段的类型
				int firstFieldType = Config.getInteger(ConfigConstant.GLOBAL_ACCOUNTNO_FIRSTFIELDTYPE,1);
				if (firstFieldType == 1) {
					if (lCurrencyID > 0)
					{
						strAccountNo = NameRef.getCurrencyNoByID(lCurrencyID);
					}
				} else if (firstFieldType == 2) {
				} else if (firstFieldType == 3) {
					if (lOfficeID > 0)
					{
						strAccountNo = NameRef.getOfficeNoByID(lOfficeID);
					}
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
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no," + "width=520,height=450,scrollbars=yes";
			}
			else
			{
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no," + "width=624,height=540,scrollbars=yes";
			}
			//生成传递给弹出窗口的参数字符串
			String strParam = "";
			strParam = "strFormName=" + strFormName;
			strParam += "&strMagnifierName=" + strMagnifierName;
			strParam += "&nIndex=" + nIndex;
			//================================修改开始================================================
			/*修改 by kenny (胡志强) 解决账号段数的问题*/
			strSQL =
				"getAccountSQL("
					+ lOfficeID
					+ ","
					+ lCurrencyID
					+ ","
					+ -1
					+ ","
					+ 100
					+ ","
					+ -1
					+ ","
					+ strFormName
					+ "."
					+ strMainNames[0]
					+ ".value)";
			if (!isSQL(strSQL))
			{
				strParam += "&strSQL='+" + strSQL + "+'";
			}
			else
			{
				strParam += "&strSQL=" + getSQL(strSQL);
			}
			//================================修改结束================================================
			if (strNextControls.length() > 0)
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
				strParam += "&strDisplayNames=" + strDisplayNames[i];
				strParam += "&strDisplayFields=" + strDisplayFields[i];
			}
			//Log.print("strParam = " + strParam);

			/*修改 by kenny (胡志强) 解决账号段数的问题*/
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
			//生成查询按钮的事件字符串
			String sOnKeydown =
				"if("
					+ strFormName
					+ "."
					+ strTempCtl[0]
					+ ".disabled == false) {gnIsSelectCtrl=1;window.open('"
					+ Env.URL_PREFIX
					+ "/iTreasury-ebank/magnifier/ShowMagnifierZoom.jsp?"
					+ strParam
					+ "', 'SelectAnything', '"
					+ sFeatures
					+ "', false);}";
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
					"<td  "
						+ strFirstTD
						+ "  >"
						+ strTitle
						+ "：&nbsp;"
						+ "<a href=#><img name=\""
						+ strButtonName
						+ "\" src='/webob/graphics/icon.gif' border=0 onclick=\""
						+ sOnKeydown
						+ "\"></a></td>");
				//image
			}
			else
			{
				out.println("<td  " + strFirstTD + " >" + strTitle + ":&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/webloan/graphics/icon.gif' border=0 ></a></td>");
				//image
			}

			/*修改 by kenny (胡志强) 解决账号段数的问题*/
			//组装账号模糊批匹配的值
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
			//将账号拆分
			String strAccountNo1 = "";
			String strAccountNo2 = "";
			String strAccountNo3 = "";
			String strAccountNo4 = "";
			if (strAccountNo != null && strAccountNo.length() > 0)
			{
				String strTemp = strAccountNo;
				strAccountNo1 = strTemp.substring(0, strTemp.indexOf("-"));
				Log.print(strAccountNo1);
				strTemp = strTemp.substring(strTemp.indexOf("-") + 1);
				strAccountNo2 = strTemp.substring(0, strTemp.indexOf("-"));
				Log.print(strAccountNo2);
				strTemp = strTemp.substring(strTemp.indexOf("-") + 1);
				if (strTemp.indexOf("-") > 0)
				{
					strAccountNo3 = strTemp.substring(0, strTemp.indexOf("-"));
					Log.print(strAccountNo3);
				}
				else
				{
					strTemp = strTemp.substring(strTemp.indexOf("-") + 1);
					strAccountNo3 = strTemp;
					Log.print(strAccountNo3);
				}
				if (strTemp.indexOf("-") > 0)
				{
					strTemp = strTemp.substring(strTemp.indexOf("-") + 1);
					strAccountNo4 = strTemp;
					Log.print(strAccountNo4);
				}
			}
			String strTempCtl1 = strMainNames[0] + "Ctrl1";
			String strTempCtl2 = strMainNames[0] + "Ctrl2";
			String strTempCtl3 = strMainNames[0] + "Ctrl3";
			String strTempCtl4 = strMainNames[0] + "Ctrl4";
			String strTempFunction = "";
			if (nCaseNumber == 3)
			{
				strTempFunction =
					strPrefix
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
						+ ".value,'');"
						+ sOnKeyUp;
			}
			else if (nCaseNumber >= 4)
			{
				strTempFunction =
					strPrefix
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
			out.println("<td  " + strSecondTD + " nowrap>");
			out.println("<input type=\"hidden\" name=\"" + strMainNames[0] + "\" value=\"" + strAccountNo + "\">");
			out.println(
				"<input type=\"text\" size=\"2\" name=\""
					+ strTempCtl1
					+ "\" value=\""
					+ strAccountNo1
					+ "\" class=\"box\"  onKeyDown=\""
					+ strPrefix
					+ "fieldFirstFocus("
					+ strFormName
					+ "."
					+ strTempCtl1
					+ ".value)\" onKeyUp=\""
					+ strTempFunction
					+ "\">");
			out.println("-");
			out.println(
				"<input type=\"text\" size=\"2\" name=\""
					+ strTempCtl2
					+ "\" value=\""
					+ strAccountNo2
					+ "\" class=\"box\"  onKeyDown=\""
					+ strPrefix
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
					"<input type=\"text\" size=\"4\" name=\""
						+ strTempCtl3
						+ "\" value=\""
						+ strAccountNo3
						+ "\" class=\"box\"  onKeyDown=\""
						+ strPrefix
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
					"<input type=\"text\" size=\"4\" name=\""
						+ strTempCtl3
						+ "\" value=\""
						+ strAccountNo3
						+ "\" class=\"box\"  onKeyDown=\""
						+ strPrefix
						+ "fieldThirdFocus("
						+ strFormName
						+ "."
						+ strTempCtl3
						+ ".value)\" onKeyUp=\""
						+ strTempFunction
						+ "\">");
				out.println("-");
				out.println(
					"<input type=\"text\" maxlength=\"1\" size=\"1\" name=\""
						+ strTempCtl4
						+ "\" value=\""
						+ strAccountNo4
						+ "\" class=\"box\"  onKeyDown=\"if(event.keyCode==13) "
						+ sOnKeydown
						+ " else "
						+ strPrefix
						+ "fieldFouthFocus("
						+ strFormName
						+ "."
						+ strTempCtl4
						+ ".value);\" onKeyUp=\""
						+ strTempFunction
						+ "\">");
			}
			out.println("</td>");
			//打印脚本程序
			//------------------------回车事件------------------------
			out.println("<script language=\"JavaScript\">");
			out.println("function " + strPrefix + "fieldFirstFocus(sValue)");
			out.println("{");
			out.println("	var k = window.event.keyCode;");
			out.println("	if (k != 46 && k != 8 && k != 37 && k != 39)");
			out.println("	{");
			out.println("		if (k == 13 || sValue.length == 2)");
			out.println("		{");
			out.println("           gnIsSelectCtrl=1; ");
			out.println("		   " + strFormName + "." + strTempCtl2 + ".focus();");
			out.println("			" + strFormName + "." + strTempCtl2 + ".select();");
			out.println("		}");
			out.println("	}");
			out.println("}");
			out.println("function " + strPrefix + "fieldSecondFocus(sValue)");
			out.println("{");
			out.println("	var k = window.event.keyCode;");
			out.println("	if (k != 46 && k != 8 && k != 37 && k != 39)");
			out.println("	{");
			out.println("		if (k == 13 || sValue.length == 2)");
			out.println("		{");
			out.println("           gnIsSelectCtrl=1; ");
			out.println("		   " + strFormName + "." + strTempCtl3 + ".focus();");
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
				out.println("function " + strPrefix + "fieldThirdFocus(sValue)");
				out.println("{");
				out.println("	var k = window.event.keyCode;");
				out.println("	if (k != 46 && k != 8 && k != 37 && k != 39)");
				out.println("	{");
				out.println("		if (k == 13 || sValue.length == 4)");
				out.println("		{");
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
				out.println("function " + strPrefix + "fieldThirdFocus(sValue)");
				out.println("{");
				out.println("	var k = window.event.keyCode;");
				out.println("	if (k != 46 && k != 8 && k != 37 && k != 39)");
				out.println("	{");
				out.println("		if (k == 13 || sValue.length == 4)");
				out.println("		{");
				out.println("           gnIsSelectCtrl=1; ");
				out.println("		   " + strFormName + "." + strTempCtl4 + ".focus();");
				out.println("		   " + strFormName + "." + strTempCtl4 + ".select();");
				out.println("		}");
				out.println("	}");
				out.println("	else if (k == 8 && sValue.length == 0)");
				out.println("	{");
				out.println("		" + strFormName + "." + strTempCtl2 + ".value = \"\";");
				out.println("		" + strFormName + "." + strTempCtl2 + ".focus();");
				out.println("	}");
				out.println("}");
				out.println("function " + strPrefix + "fieldFouthFocus(sValue)");
				out.println("{");
				out.println("	var k = window.event.keyCode;");
				out.println("	if (k != 46 && k != 8 && k != 37 && k != 39)");
				out.println("	{");
				out.println("		if (k == 13 )");
				out.println("		{");
				out.println("           gnIsSelectCtrl=1; ");
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
			out.println("function " + strPrefix + "setWholeAcccountNo(sValue1,sValue2,sValue3,sValue4)");
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
			//--------------------模糊匹配部分------------------
			out.println("function get" + strMainNames[0] + "(Account)");
			out.println("{");
			out.println("   var sql = \"\"; ");
			out.println("   if (Account != null && Account != \"\") ");
			out.println("   {");
			out.println("       sql +=  \" and " + strMainFields[0] + " like '" + URLEncoder.encode("%") + "\"+Account+\"" + URLEncoder.encode("%") + "'\";    ");
			out.println("   }");
			out.println("    ");
			out.println("   return sql;    ");
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
			* 显示特殊放大镜
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
	public static void showDifferZoomCtrl(
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
		boolean blnIsRateCtrl,
		String sPageName)
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
					+ " gnIsSelectCtrl=1;window.open('/NASApp/iTreasury-ebank/magnifier/"
					+ sPageName
					+ "?"
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
					"<td "
						+ strFirstTD
						+ ">"
						+ strTitle
						+ ":&nbsp;"
						+ "<a href=#><img name=\""
						+ strButtonName
						+ "\" src='/webob/graphics/zoom.gif' border=0 onclick=\""
						+ sOnKeydown
						+ "\"></a></td>");
				//image
			}
			else
			{
				out.println("<td " + strFirstTD + ">" + strTitle + ":&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/webob/graphics/zoom.gif' border=0 ></a></td>");
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
		long lClientID,
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
		String[] strDisplayNames = { URLEncoder.encode("用户名称"),URLEncoder.encode("单位")};
		String[] strDisplayFields = { "UserName","ClinetName" };
		int nIndex = 0;
		String strSQL = "getUserSQL(" + lOfficeID + "," + lCurrencyID + "," + lClientID + "," + strCtrlName + "Ctrl.value)";
		try
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
				strSecondTD, false, false);
		}
		catch (Exception e)
		{
			Log.print(strMagnifierName + "放大镜[" + strCtrlName + "]异常：" + e.toString());
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
	public static void CreateApprovalSettingCtrl(long lOfficeID, long lCurrencyID,long lClientID, JspWriter out, long lApprovalID, String strFormName, String strControlName, String strPrefix, String strMainProperty, String strReturnName, String strNextControls) throws Exception
	{
		try
		{
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
			String[] strDisplayNames = { "审批流编号", "审批流名称" };
			String[] strDisplayFields = { "ID", "sName" };
			int nIndexOffice = 0;
			String strSQL = strPrefix + "getApprovalSettingSQL(" + lOfficeID + ","  + lApprovalID+ "," + lClientID + ")";
						
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
	 * 显示审批流关联设置放大镜
	 * added by ypxu
	 * 2007-04-28
	 */
	public static void CreateApprovalRelationSettingCtrl(JspWriter out, long lOfficeID, long lClientID, String strFormName, String strControlName, String strMainProperty, String strReturnName1, String strReturnName2, String strNextControls) throws Exception
	{
		try
		{
			String strMagnifierName = "流程关联";
			String strPrefix = "";
			String[] strMainNames = { strControlName };
			String[] strMainFields = { "Name" };
			String[] strReturnNames = { strReturnName1, strReturnName2 };
			String[] strReturnFields = { "ID", "Name" };
			String[] strReturnValues = { "", "" };
			String[] strDisplayNames = { "审批流名称" };
			String[] strDisplayFields = { "Name" };
			int nIndexOffice = 0;
			String strSQL = strPrefix + "getApprovalRelationSettingSQL(" + lOfficeID + "," + lClientID + ")";
						
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
 public static void showRemarkCtrl(
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
         String sOnKeydown =
             "if("
                 + strFormName
                 + "."
                 + strMainNames[0]
                 + ".disabled == false) {gnIsSelectCtrl=1;window.open('"
                 + Env.URL_PREFIX
                 + "/iTreasury-ebank/magnifier/ShowMagnifierZoom.jsp?"
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
                     + "<a href=#><img name=\""
                     + strButtonName
                     + "\" src='/webob/graphics/zoom.gif' border=0 onclick=\""
                     + sOnKeydown
                     + "\"></a></td>");
         }
         else
         {
             out.println("<td " + strFirstTD + " >" + strTitle + ":&nbsp;" + "<img name=\"" + strButtonName + "\" style=\"cursor: hand;\" src='/webloan/image/icon.gif' border=0 ></td>");
         }

             out.println(
                 "<td "
                     + strSecondTD
                     + " ><textArea cols='70' name=\""
                     + strMainNames[0]
                     + "\" onpropertychange=\"if(this.value.length>250) this.value=this.value.substr(0,250)\" "
                     + strMainProperty
                     + " onKeyDown=\"if(event.keyCode==13) "
                     + sOnKeydown
                     + "\" onKeyUp=\""
                     + sOnKeyUp
                     + "\">"
                     + strReturnInitValues
                     +"</textArea></td>");

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
             out.println(" sql +=  \" and " + strMatchValue + " like '" + URLEncoder.encode("%") + "\"+str+\"" + URLEncoder.encode("%") + "'\";    ");
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
	 * 创建付款方银行账号放大镜,收款方账号（内部转账）
	 * @author yanliu
	 * @param out
	 * @param lUserID 用户ID
	 * @param lClientID 客户ID
	 * @param lInstructionID 当前交易ID
	 * @param lCurrencyID 币种ID
	 * @param sBankAccountCode 账号（初始值）
	 * @param strFormName 主页表单名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述)
	 * @param strClientNo 客户编号(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
	 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
	 */
 public static void createPayerBankAccountNoCtrl(
			JspWriter out,
			long lCurrencyID,
			long lOfficeID,
			long lClientID,
			String strRelativeForm1,
			String strRelativeForm2,
			String strRelativeForm3,
			String strRelativeForm4,//付款方名称
			String strFormName,
			String sBankAccountCode,
			String strCtrlName,
			String strTitle,
			String strFirstTD,
			String strSecondTD)
		{
			String strMagnifierName = URLEncoder.encode("付款方银行账号");
			String strMainProperty = " maxlength='40' value='" + sBankAccountCode + "'";
			String strPrefix = "";
			String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2, strRelativeForm3,strRelativeForm4,"bankname"};
			String[] strMainFields = { "accountno", "id", "currentbalance", "","name","bankname"};
			String[] strMainName = { strCtrlName + "Ctrl", strRelativeForm1 }; //收款方
			String[] strMainField = { "accountno", "id" }; //收款方
			String[] strReturnNames = { "sName" };
			String[] strReturnFields = { "name" };
			String[] strReturnValues = { "" };
			String[] strReturnName = { "sPayeeBankAccountNOInternal" }; //收款方
			String[] strReturnField = { "sname" }; //收款方
			String[] strReturnValue = { "" }; //收款方
			String[] strDisplayNames = { URLEncoder.encode("银行账号"), URLEncoder.encode("银行账户名称"), URLEncoder.encode("银行名称")};
			String[] strDisplayFields = { "accountno", "name", "bankname" };
			int nIndex = 0;
			String strSQL = "";
				strSQL = "getBankAccountforbankpay(" + strCtrlName + "Ctrl.value,"  +lOfficeID+","+ lClientID + "," + lCurrencyID +")";
			 
			String[] strNextControls = { "sPayeeAcctNoZoomCtrl" };
			try
			{
				if (!strRelativeForm2.equals("") && !strRelativeForm3.equals(""))
				{
					showBankPayerAcctZoomCtrl(
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
						false);
				}

			}
			catch (Exception e)
			{
			}
		}
	public static void createPayerBankAccount(
		JspWriter out,
		long lUserID,
		long lCurrencyID,
		long lOfficeID,
		long lClientID,
		String strRelativeForm1,
		String strRelativeForm2,
		String strRelativeForm3,
		String strRelativeForm4,//付款方名称
		String strFormName,
		String sBankAccountCode,
		String strCtrlName,
		String strTitle,
		String strFirstTD,
		String strSecondTD)
	{
		String strMagnifierName = URLEncoder.encode("付款方银行账号");
		String strMainProperty = " maxlength='40' value='" + sBankAccountCode + "'";
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2, strRelativeForm3,strRelativeForm4,"bankname"};
		String[] strMainFields = { "accountno", "id", "currentbalance", "","name","bankname"};
		//String[] strMainName = { strCtrlName + "Ctrl", strRelativeForm1 }; //收款方
		//String[] strMainField = { "accountno", "id" }; //收款方
		String[] strReturnNames = { "sName" };
		String[] strReturnFields = { "name" };
		String[] strReturnValues = { "" };
		//String[] strReturnName = { "sPayeeBankAccountNOInternal" }; //收款方
		//String[] strReturnField = { "sname" }; //收款方
		//String[] strReturnValue = { "" }; //收款方
		String[] strDisplayNames = { URLEncoder.encode("银行账号"), URLEncoder.encode("银行账户名称"), URLEncoder.encode("银行名称")};
		String[] strDisplayFields = { "accountno", "name", "bankname" };
		int nIndex = 0;
		String strSQL = "";
			strSQL = "getBankAccount(" + strCtrlName + "Ctrl.value,"  +lOfficeID+","+ lClientID + "," + lCurrencyID +","+lUserID +")";
		 
		String[] strNextControls = { "sPayeeAcctNoZoomCtrl" };
		try
		{
			if (!strRelativeForm2.equals("") && !strRelativeForm3.equals(""))
			{
				showBankPayerAcctZoomCtrl(
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
					false);
			}

		}
		catch (Exception e)
		{
		}
	}
	
	
	
	/**
	 * 显示付款方账号（网银）放大镜
	 * @author yanliu
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
	public static void showBankPayerAcctZoomCtrl(
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
					+ ".disabled == false) {gnIsSelectCtrl=1;window.open('/NASApp/iTreasury-ebank/magnifier/ShowBankPayZoom.jsp?"
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
					"<td "
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
				out.println("<td " + strFirstTD + ">" + strTitle + ":&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/webob/graphics/zoom.gif' border=0 ></a></td>");
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
	 * 针对网银中付款方账号放大镜的特殊方法 For CSSC
	 * @author 
	 * @param strMainFields
	 * @param strReturnFields
	 * @param strDisplayFields
	 * @param strSQL
	 * @return
	 * @throws Exception
	 */
	public static Vector getOBBankPaySelectList(String[] strMainFields, String[] strReturnFields, String[] strDisplayFields, String strSQL) throws Exception
	{
		CommonSelectInfo info = null;
		PreparedStatement ps = null, ps1 = null;
		ResultSet rs = null, rs1 = null;
		Connection conn = null;
		Vector vResult = new Vector();
		
		System.out.println("strSQL------------------------>" +   strSQL);
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
				long lTempMain = rs.getLong(strMainFields[1]);
				oMainCols[0] = rs.getObject(strMainFields[0]);
				oMainCols[1] = rs.getObject(strMainFields[1]);
				oMainCols[2] = rs.getObject(strMainFields[2]);
				//oMainCols[3] = OBOperation.GetUseableBalance(Long.parseLong(oMainCols[1].toString()),1,DataFormat.getStringDateTime().substring(0,10),-1);//"999999";//从别处获)得
				//String useableAmount=oMainCols[3].toString();
				//oMainCols[3]=DataFormat.formatDisabledAmount(Double.parseDouble(useableAmount),2);
				oMainCols[4] = rs.getObject(strMainFields[4]);
				oMainCols[5] = rs.getObject(strMainFields[5]);
				Log.print("-------1:" + oMainCols[0]);
				Log.print("-------2:" + oMainCols[1]);
				Log.print("-------3:" + oMainCols[2]);
				Log.print("-------4:" + oMainCols[3]);
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
			String[] strReturnValues
			)
	{
		String strMagnifierName = "存款单据号";
		if (lDepositTypeID == 1)
		{
			strMagnifierName = "定期存款单据号";
		}
		else if (lDepositTypeID == 2)
		{
			strMagnifierName = "通知存款单据号";
		}
		else if(lDepositTypeID == 6)
		{
			strMagnifierName = "保证金存款单据号";
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
//		String[] strReturnNames = { strCtrlName, strCtrlName + "AccountID" };
//		String[] strReturnFields = { "SubAccountID", "AccountID" };
//		String[] strReturnValues = { String.valueOf(lSubAccountID), String.valueOf(lAccountID)};
		//String[] strReturnValues = { String.valueOf(lSubAccountID), String.valueOf(lAccountID),String.valueOf(lClientID), String.valueOf(lFromClient),String.valueOf(lAccountID), String.valueOf(lClientID), "-1"};
		String[] strDisplayNames = { "存款单据号"};
		String[] strDisplayFields = { "DepositNo" };
		//支取或续期转存时，显示不同
		if (lTypeID == 21 || lTypeID == 22 || lTypeID == 3 || lTypeID == 0)
		{
			if (lDepositTypeID == 1)
			{
				//定期显示单据号、余额、到期日
				strDisplayNames = new String[] { "存款单据号", "余额", "到期日"};
				strDisplayFields = new String[] { "DepositNo", "Balance", "EndDate" };
			}else if(lDepositTypeID == 6){
//				保证金显示单据号、可用余额、开户日期
				strDisplayNames = new String[] { "存款单据号", "可用余额", "开户日"};
				strDisplayFields = new String[] { "DepositNo", "Balance", "OpenDate" };
			}
			else
			{
				//定期显示单据号、余额、开户日期
				strDisplayNames = new String[] { "存款单据号", "余额"};
				strDisplayFields = new String[] { "DepositNo", "Balance"};
			}
		}
		int nIndex = 0;
		String strSQL = "";
		if (strAccountIDCtrl != null && !strAccountIDCtrl.trim().equals(""))
		{
			strSQL =
				"getFixedDepositNoSQLOB("
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
				"getFixedDepositNoSQLOB("
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
			boolean blnIsOptional = false;
			boolean blnIsRateCtrl = false;
			OBMagnifier.showFormZoomCtrl(
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
				blnIsRateCtrl);
		}
	
		catch (Exception e)
		{
			Log.print("定期存款单据号放大镜[" + strCtrlName + "]异常：" + e.toString());
		}
	
	}
	
	/**
	 * 显示普通放大镜，通过表单提交
	 * 
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
	public static void showFormZoomCtrl(
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
		boolean blnIsRateCtrl) throws Exception
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

			//生成传递给弹出窗口的参数字符串
			StringBuffer strHiddenName = new StringBuffer();
			StringBuffer strHiddenValue = new StringBuffer();

			strHiddenName.append("strFormName|:|:");
			strHiddenValue.append(strFormName + "|:|:");
			strHiddenName.append("strMagnifierName|:|:");
			strHiddenValue.append(strMagnifierName + "|:|:");
			strHiddenName.append("nIndex|:|:");
			strHiddenValue.append(nIndex + "|:|:");

			for (int i = 0; i < strNextControls.length; i++)
			{
				strHiddenName.append("strNextControls|:|:");
				strHiddenValue.append(strNextControls[i] + "|:|:");
			}
			for (int i = 0; i < strMainNames.length; i++)
			{
				strHiddenName.append("strMainNames|:|:");
				strHiddenValue.append(strMainNames[i] + "|:|:");
				strHiddenName.append("strMainFields|:|:");
				strHiddenValue.append(strMainFields[i] + "|:|:");
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
					strHiddenName.append("strReturnNames|:|:");
					strHiddenValue.append(strReturnNames[i] + "|:|:");
					strHiddenName.append("strReturnFields|:|:");
					strHiddenValue.append(strReturnFields[i] + "|:|:");
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
				strHiddenName.append("strDisplayNames|:|:");
				strHiddenValue.append(strDisplayNames[i] + "|:|:");
				strHiddenName.append("strDisplayFields|:|:");
				strHiddenValue.append(strDisplayFields[i] + "|:|:");
			}

			//生成查询按钮的事件字符串
			StringBuffer sOnKeydown = new StringBuffer();
			sOnKeydown.append("var strHiddenName = '" + strHiddenName.toString() + "strSQL'; ");
			sOnKeydown.append("var strHiddenValue = '" + strHiddenValue.toString() + "'; ");
			if (!isSQL(strSQL)){
				sOnKeydown.append("var strSQL = " + strSQL + "; ");
			}
			else{
				sOnKeydown.append("var strSQL = " + getSQL(strSQL) + "; ");
			}
			sOnKeydown.append("strHiddenValue = strHiddenValue + encodeURI(strSQL); ");
			
			sOnKeydown.append("if(" + strFormName + "." + strMainNames[0] + ".disabled == false) { ");
			sOnKeydown.append("  " + strFormName + "." + strMainNames[0] + ".focus(); ");
			sOnKeydown.append("  gnIsSelectCtrl = 1; ");
			sOnKeydown.append("  openMagnifier('/NASApp/iTreasury-ebank/magnifier/ShowFormMagnifierZoom.jsp', strHiddenName, strHiddenValue); ");
			sOnKeydown.append("} ");
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
				out.println("<td width=130"+ strFirstTD+ ">"+ strTitle +"：&nbsp;" + "<a href=#><img name=\""+ strButtonName +"\" src='/webob/graphics/zoom.gif' border=0 onclick=\"" + sOnKeydown +"\"></a></td>");
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
					out.println("<td"+ strSecondTD+ "><input type=\"text\" name=\""+ strMainNames[0]+ "\" class=\"box\" "+ strMainProperty+ " onKeyDown=\"if(event.keyCode==13) {"+ sOnKeydown.toString()+ "} \" onKeyUp=\""+ sOnKeyUp+ "\">%</td>");
				}
				else
				{
					out.println("<td"+ strSecondTD+ "><input type=\"text\" name=\""+ strMainNames[0]+ "\" class=\"box\" "+ strMainProperty+ " onKeyDown=\"if(event.keyCode==13) {"+ sOnKeydown.toString()+ "} \" onKeyUp=\""+ sOnKeyUp+ "\"></td>");
				}
			}
		}
		catch (Exception exp)
		{
			throw exp;
		}
	}
	/**
	 * 创建汇款用途摘要放大镜
	 * @param out
	 * @param lOfficeID 办事处ID
	 * @param lClientID 用户ID
	 * @param lCurrencyID 币种ID
	 * @param strFormName 表单域名称
	 * @param strCtrlName 放大镜主控件名称
	 * @param strTitle 放大镜描述
	 * @param lAbstractID 摘要ID(初识值)
	 * @param strAbstractDesc 摘要描述(初识值)
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性
	 * @param maxLength 文本域可输入最大字符数
	 * @param strNextControls 下一个（或多个）获得焦点的控件
	 */
	public static void createAbstractSettingCtrl(
		JspWriter out,
		long lOfficeID,
		long lClientID,
		long lCurrencyID,
		String strFormName,
		String strCtrlName,
		String strTitle,
		long lAbstractID,
		String strAbstractDesc,
		String strFirstTD,
		String strSecondTD,
		long maxLength,
		String[] strNextControls)
	{
		String strTITLE = "汇款用途";
		String strMainName = strCtrlName + "Ctrl";
		String strMagnifierName = URLEncoder.encode(strTITLE);
		String strMainProperty = " size='40' maxlength = '50' onpropertychange='checkStr("+maxLength+",\""+strMainName+"\")' onblur ='checkStrMessage("+maxLength+",\""+strMainName+"\")' ";

		if (strNextControls != null && strNextControls.length > 0)
		{
			strMainProperty = strMainProperty + " onfocus=\"nextfield='" + strNextControls[0] + "';\"";
		}
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl", String.valueOf(maxLength) };
		String[] strMainFields = { "AbstractDesc" };
		String[] strReturnNames = { strCtrlName,"sCode" };
		String[] strReturnFields = { "AbstractID","AbstractCode" };
		String[] strReturnValues = { String.valueOf(lAbstractID),String.valueOf(lAbstractID)};
		String[] strDisplayNames = { URLEncoder.encode("摘要代号"), URLEncoder.encode("摘要描述")};
		String[] strDisplayFields = { "AbstractCode", "AbstractDesc" };
		int nIndex = 1;
		String strSQL = "getAbstractSettingSQL(" + lOfficeID + "," + lClientID + "," + strCtrlName + "Ctrl.value)";
		boolean blnIsOptional = true;
		try
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
				strAbstractDesc,
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
 * 显示普通文本域放大镜
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
	String strAbstractDesc,
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
		strAbstractDesc,
		strSQL,
		strNextControls,
		strTitle,
		strFirstTD,
		strSecondTD,
		blnIsOptional,
		"branch");
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
	String strAbstractDesc,
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
		String maxLength = "";
		if(strCtrlType.equals("branch")){
			maxLength = strMainNames[1];
			String[] _strMainNames = {strMainNames[0]};
			strMainNames  = _strMainNames;
		}
		
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
				+ "/iTreasury-ebank/magnifier/ShowMagnifierZoom.jsp?"
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
					+ "\" src='/webob/graphics/zoom.gif' border=0 onclick=\""
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
					long initLen = 0;
		    		long charLen = 0;
					int initValueLen = strAbstractDesc.length();
					for(int i = 0; i < initValueLen; i++){
					     if(strAbstractDesc.charAt(i)>255){
					    	 charLen+=2;
					     }else{
					         charLen+=1;
					     }  
					}
					initLen = Long.parseLong(maxLength) - charLen;
					//sOnKeyUp +="halfTurnFull(this,'"+strMainNames[0]+"');";//自动将半角标点转换成全角标点
					out.println(
						"<td"
							+ strSecondTD
							+ "><textarea name=\""
							+ strMainNames[0]
							+ "\"  " 
							+" id=\""
							+ strMainNames[0]
							+ "\" "
							+"class=\"textarea\" bgcolor=\"#FF00\"  rows=2 cols=65 onKeyDown=\"if(event.keyCode==13) "
							+ sOnKeydown
							+ "\" onKeyUp=\""
							+ sOnKeyUp
							+ "\" "
							+ strMainProperty
							+ ">" 
							+ strAbstractDesc
							+ "</textarea></br>"
							+ "<span id='"+strMainNames[0]+"textAreaShow'>最多<b>"+maxLength+"</b>个字符（一个汉字2个字符），还可以输入 <b>"+initLen+"</b> 个字符</span></td>");
	    		
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
							+ "><textarea id=\"" +strMainNames[0]+"\" name=\""
							+ strMainNames[0] 
							+ "\"  class=\"box\" bgcolor=\"#FF00\"  rows=2 cols=30 onKeyDown=\"if(event.keyCode==13) "
							+ sOnKeydown
							+ "\" onKeyUp=\""
							+ sOnKeyUp
							+ "\" "
							+ strMainProperty
							+ "></textarea></td>");
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
 * 创建定期(通知)存款单据号放大镜--修改
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
 * @param strRtnRateCtrl 返回值（利率）对应的控件名
 * @param strRtnIntervalCtrl 返回值（期限）对应的控件名
 * @param strRtnStartDateCtrl 返回值（开始日期）对应的控件名
 */
public static void createFixedDepositNoCtrlByModify(
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
	String strAmount,
	FinanceInfo financeInfo)
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
	if (strAmount != null && strAmount != "")
	{
		strMainProperty += " onblur=\"adjustAmount('" + strFormName + "','" + strAmount + "',1,'sChineseAmount','" + lCurrencyID + "');\" ";
		//strMainProperty += " onfocus=\"adjustAmount('" + strFormName +"','" + strRtnCapitalCtrl + "',2,'sChineseAmount','" + lCurrencyID+"');\" ";
	}
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
	if (strAmount == null || strAmount.equals(""))
	{
		strAmount = "ItIsNotControl";
	}
	String[] strMainNames =
		{ strCtrlName + "Ctrl", strRtnEndDateCtrl, strRtnOpenDateCtrl, strRtnCapitalCtrl, strRtnBalanceCtrl, strRtnRateCtrl, strRtnIntervalCtrl, strRtnStartDateCtrl, strAmount };
	String[] strMainFields = { "DepositNo", "EndDate", "OpenDate", "Capital", "Balance", "Rate", "Interval", "StartDate", "Balance" };
	String[] strReturnNames = { strCtrlName, strCtrlName + "AccountID" };
	String[] strReturnFields = { "SubAccountID", "AccountID" };
	String[] strReturnValues = { String.valueOf(lSubAccountID), String.valueOf(lAccountID)};
	String[] strDisplayNames = { URLEncoder.encode("存款单据号")};
	String[] strDisplayFields = { "DepositNo" };
	//支取或续期转存时，显示不同
	if (lTypeID == 21 || lTypeID == 22 || lTypeID == 23 || lTypeID == 2 || lTypeID == 3)
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
	boolean isCreateNewBook = Config.getBoolean(ConfigConstant.SETT_FIXEDDRAW_CREATENEWBOOK, true);
	if (strAccountIDCtrl != null && !strAccountIDCtrl.trim().equals(""))
	{
		if(lDepositTypeID == 1){
			
			if(isCreateNewBook){
					strSQL =
						"getFixedDepositNoSQL_CREATE_MODIFY("
						+ financeInfo.getSubAccountID()
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
				}else{
					strSQL =
						"getFixedDepositNoSQL_MODIFY("
						+ financeInfo.getID()
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
	Log.print("----------sql=" + strSQL);
	try
	{
		OBMagnifier.showZoomCtrl(
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
				false);
			
	}
	catch (Exception e)
	{
		Log.print("定期存款单据号放大镜[" + strCtrlName + "]异常：" + e.toString());
	}
}


/**
 * add by xlchang 2010-11-30 在原showZoomCtrl方法上修改,屏蔽td的固定长度
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
 * @param blnIsRateCtrl 是否是利率控件
 * @throws Exception
 */
public static void showZoomCtrl1(
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
				+ " gnIsSelectCtrl=1;window.open('/NASApp/iTreasury-ebank/magnifier/ShowMagnifierZoom.jsp?"
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
				"<td "
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
 * 创建收款方银行账号放大镜（汇）
 * @author yanliu
 * @param out
 * @param lClientID 客户ID
 * @param lCurrencyID 币种ID
 * @param strRelativeForm 关联项
 * @param sBankAccountCode 账号（初始值）
 * @param strFormName 主页表单名称
 * @param strCtrlName 放大镜主控件名称
 * @param strTitle 放大镜描述)
 * @param strClientNo 客户编号(初识值)
 * @param strFirstTD 第一个TD的属性
 * @param strSecondTD 第二个TD的属性
 * @param sNextControlsClient 下一个（或多个）获得焦点的控件
 * @param strRtnClientNameCtrl 返回值（客户名称）对应的控件名
 * @param isBlurQuery 是否支持收款方名称的模糊查询
 */

	public static void createPayeeAccountNOCtrl2(
		JspWriter out,
		long lCurrencyID,
		long lClientID,
		String strRelativeForm1,
		String strRelativeForm2,
		String strRelativeForm3,
		String strRelativeForm4,
		String strRelativeForm5,
		String strRelativeForm6,
		String strRelativeForm7,
		String strRelativeForm8,
		String strFormName,
		String sBankAccountCode,
		String strCtrlName,
		String strTitle,
		String strFirstTD,
		String strSecondTD,
		String[] strNextControls,
		boolean isBlurQuery)
	{
		String strMagnifierName = URLEncoder.encode("收款方账号");
		String strMainProperty = " maxlength='50' value='" + sBankAccountCode + "'";
		String strPrefix = "";
		String[] strMainNames = { strCtrlName + "Ctrl", strRelativeForm1, strRelativeForm2, strRelativeForm3, strRelativeForm4, strRelativeForm5,strRelativeForm6,strRelativeForm7,strRelativeForm8};
		String[] strMainFields = { "spayeeacctno", "ID", "sPayeeName", "SPAYEEPROV", "SPAYEECITY", "sPayeeBankName","spayeebankcnapsno","spayeebankorgno","spayeebankexchangeno" };
		
		String[]  strReturnNames = { strCtrlName+"hiddenValue" }; //收款方
		
		String[] strReturnFields = {"spayeeacctno"
		};
		String[] strReturnValues = {""
		};
		String[] strDisplayNames = { URLEncoder.encode("收款方账号"), URLEncoder.encode("收款方名称"), URLEncoder.encode("汇入地（省）"), URLEncoder.encode("汇入地（市）"), URLEncoder.encode("汇入行名称"), URLEncoder.encode("联行号"), URLEncoder.encode("CNAPS号"), URLEncoder.encode("机构号")};
		String[] strDisplayFields = { "spayeeacctno", "sPayeeName","SPAYEEPROV", "spayeecity", "sPayeeBankName", "spayeebankexchangeno", "spayeebankcnapsno", "spayeebankorgno" };
		int nIndex = 0;
		String strSQL = "getPayeeAccountNOSQL(" + isBlurQuery + "," + lClientID + "," + lCurrencyID + "," + strCtrlName + "Ctrl.value," + strRelativeForm2 + ".value" + ")";
		
		try
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
				
				false,
				false);
			
		}
		catch (Exception e)
		{
		}
	}


}
