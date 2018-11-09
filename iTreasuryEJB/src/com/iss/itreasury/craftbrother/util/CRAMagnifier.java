package com.iss.itreasury.craftbrother.util;
import java.net.URLEncoder;
import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Magnifier;
import com.iss.itreasury.util.Log4j;


/**
 * 同业往来放大镜
 * 
 * @author xintan
 *
 */
public class CRAMagnifier 
{
	private static Log4j log = new Log4j(Constant.ModuleType.CRAFTBROTHER, CRAMagnifier.class);
	
	private static String ZOOMERRORMSG = "放大镜参数设置错误!";
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
				+ lClientID
				+ ","
				+ lAccountID
				+ ","
				+ strFormName
				+ "."
				+ strCtrlName
				+ "Ctrl.value)";
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
			CRAMagnifier.showSpecialZoomCtrl(
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
	 * 显示同业往来子类型放大镜
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种
	 * @param out
	 * @param strLoanTypeID 证券种类
	 * @param strFormName 主页面表单名称
	 * @param strPrefix 控件名称前缀
	 * @param strMainProperty 放大镜栏位属性
	 * @param strSQL 放大镜查询SQL语句
	 * @param strNextControls 设置下一个焦点
	 * @throws Exception
	 */
	public static void CreateSubLoanTypeCtrl(long lOfficeID, long lCurrencyID,
			JspWriter out, String strLoanTypeID, String strFormName,
			String strPrefix, String strMainProperty, String strNextControls)
			throws Exception { 
		try {
			//输出SQL到页面
			out.println("<script language=\"javascript\">");
			out.println("/*===================="
					+ URLEncoder.encode("同业往来子类型放大镜") + "=================*/");
			out.println("function " + strPrefix
					+ "getSubLoanTypeSQL(nOfficeID,lLoanTypeID)");
			out.println("{");
			out
					.println("	var sql = \"select a.ID, a.LoanTypeID, a.Code, a.Name \";");
			out
					.println("	sql += \" from cra_craTypeSetting a, cra_craTypeRelation b \";");
			out
					.println("	sql += \" where a.ID = b.subLoanTypeID and a.statusID = 1 \";");
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
			String strMagnifierName = "同业往来业务子类型";
			String[] strMainNames = { "txtSubLoanTypeName",
					"txtSubLoanTypeCode", };
			String[] strMainFields = { "Name", "Code" };
			String[] strReturnNames = { "hidSubLoanTypeID", "hidLoanTypeID" };
			String[] strReturnFields = { "ID", "LoanTypeID" };
			String[] strReturnValues = { "-1", "-1" };
			String[] strDisplayNames = { "业务子类型名称", "业务子类型编码" };
			String[] strDisplayFields = { "Name", "LoanTypeID" };
			int nIndexOffice = 0;
			String strSQL = strPrefix + "getSubLoanTypeSQL(" + lOfficeID + ","
					+ strFormName + "." + strPrefix + strLoanTypeID + ".value)";

			showZoomCtrl(out, strMagnifierName, strFormName, strPrefix,
					strMainNames, strMainFields, strReturnNames,
					strReturnFields, "", strReturnValues, strDisplayNames,
					strDisplayFields, nIndexOffice, strMainProperty, strSQL,
					"", strNextControls, strMagnifierName, "", "");
		} catch (Exception exp) {
			throw exp;
		}
	}
	/**
	 * 显示同业往来交易类型放大镜
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种
	 * @param out
	 * @param strLoanTypeID 证券种类
	 * @param strFormName 主页面表单名称
	 * @param strPrefix 控件名称前缀
	 * @param strMainProperty 放大镜栏位属性
	 * @param strSQL 放大镜查询SQL语句
	 * @param strNextControls 设置下一个焦点
	 * @throws Exception
	 */
	public static void CreateSubCraTransactionTypeCtrl(long lOfficeID, long lCurrencyID,
			JspWriter out,
			String strCraTypeId,
			String strCraTransactionTypeID, String strFormName,
			String strPrefix, String strMainProperty, String strNextControls)
			throws Exception { 
		try {
			//输出SQL到页面
			String strMagnifierName = "交易类型";
			String[] strMainNames = { "txtCraTransactionTypeName"};
			String[] strMainFields = { "craTransTypeName"};
			String[] strReturnNames = { "hidCraTransactionTypeID"};
			String[] strReturnFields = { "craTransTypeId"};
			String[] strReturnValues = { "-1"};
			String[] strDisplayNames = {"交易类型名称", "交易类型编码" };
			String[] strDisplayFields = { "craTransTypeName", "craTransTypeId" };
			int nIndexOffice = 0;
			String strSQL = strPrefix + "getSubCraTransactionTypeSQL("
				+ strFormName + "." + strPrefix + strCraTypeId + ".value,"	
				+ strFormName + "." + strPrefix + strCraTransactionTypeID + ".value)";

			showZoomCtrl(out, strMagnifierName, strFormName, strPrefix,
					strMainNames, strMainFields, strReturnNames,
					strReturnFields, "", strReturnValues, strDisplayNames,
					strDisplayFields, nIndexOffice, strMainProperty, strSQL,
					"", strNextControls, strMagnifierName, "", "");
		} catch (Exception exp) {
			throw exp;
		}
	}		
	
	/**
	 * 显示同业往来子类型放大镜
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种
	 * @param out
	 * @param strLoanTypeID 证券种类
	 * @param strFormName 主页面表单名称
	 * @param strPrefix 控件名称前缀
	 * @param strMainProperty 放大镜栏位属性
	 * @param strSQL 放大镜查询SQL语句
	 * @param strNextControls 设置下一个焦点
	 * @throws Exception
	 */
	public static void CreateSubCraTypeCtrl(long lOfficeID, long lCurrencyID,
			JspWriter out, String strLoanTypeID, String strFormName,
			String strPrefix, String strMainProperty, String strNextControls)
			throws Exception { 
		try {
			//输出SQL到页面
			out.println("<script language=\"javascript\">");
			out.println("/*===================="
					+ URLEncoder.encode("同业往来子类型放大镜") + "=================*/");
			out.println("function " + strPrefix
					+ "getSubCraTypeSQL(nOfficeID,lLoanTypeID,lCraTransactionTypeID)");
			out.println("{");
			out
					.println("	var sql = \"select distinct a.ID, a.LoanTypeID, a.Code, a.Name \";");
			out
					.println("	sql += \" from cra_craTypeSetting a, cra_craTypeRelation b, cra_cratypeactionsetting t \";");
			out
					.println("	sql += \" where a.ID = b.subLoanTypeID and a.statusID = 1 \";");
			out.println(" ");
			out.println("   if (nOfficeID > 0)");
			out.println("	{");
			out.println("		sql += \" and b.officeID = \" + nOfficeID; ");
			out.println("	}");
			out.println("	if (lLoanTypeID > 0) ");
			out.println("	{");
			out.println("		sql += \" and a.loanTypeID = \" + lLoanTypeID; ");
			out.println("	}");
			out.println("	if (lCraTransactionTypeID > 0) ");
			out.println("	{");
			out.println("		sql += \" and a.id = t.loantypeid and t.id = \" + lCraTransactionTypeID; ");
			out.println("	}");
			out.println("   sql += \"order by a.LoanTypeID\" ");
			out.println("	return sql;");
			out.println("}");
			out.println("</SCRIPT> ");
			String strMagnifierName = "业务类型";
			String[] strMainNames = { "txtSubCraTypeName",
					"txtSubCraTypeCode", };
			String[] strMainFields = { "Name", "Code" };
			String[] strReturnNames = { "hidSubCraTypeID", "hidCraTypeID" };
			String[] strReturnFields = { "ID", "LoanTypeID" };
			String[] strReturnValues = { "-1", "-1" };
			String[] strDisplayNames = { "业务子类型名称", "业务子类型编码" };
			String[] strDisplayFields = { "Name", "LoanTypeID" };
			int nIndexOffice = 0;
			String strSQL = strPrefix + "getSubCraTypeSQL(" + lOfficeID + ","
					+ strFormName + "." + strPrefix + strLoanTypeID + ".value,hidCraTransactionTypeID.value)";

			showZoomCtrl(out, strMagnifierName, strFormName, strPrefix,
					strMainNames, strMainFields, strReturnNames,
					strReturnFields, "", strReturnValues, strDisplayNames,
					strDisplayFields, nIndexOffice, strMainProperty, strSQL,
					"", strNextControls, strMagnifierName, "", "");
		} catch (Exception exp) {
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
	public static void showZoomCtrl(JspWriter out, String strMagnifierName,
			String strFormName, String strPrefix, String[] strMainNames,
			String[] strMainFields, String[] strReturnNames,
			String[] strReturnFields, String strReturnInitValues,
			String[] strReturnValues, String[] strDisplayNames,
			String[] strDisplayFields, int nIndex, String strMainProperty,
			String strSQL, String strMatchValue, String strNextControls,
			String strTitle, String strFirstTD, String strSecondTD)
			throws Exception {
		//2004-11-18 模糊匹配支持多字段匹配
		String[] strMatchValues = new String[1];
		strMatchValues[0] = strMatchValue;

		showZoomCtrl(out, strMagnifierName, strFormName, strPrefix,
				strMainNames, strMainFields, strReturnNames, strReturnFields,
				strReturnInitValues, strReturnValues, strDisplayNames,
				strDisplayFields, nIndex, strMainProperty, strSQL,
				strMatchValues, strNextControls, strTitle, strFirstTD,
				strSecondTD, false, false);
	}
	public static void showZoomCtrl(JspWriter out, String strMagnifierName,
			String strFormName, String strPrefix, String[] strMainNames,
			String[] strMainFields, String[] strReturnNames,
			String[] strReturnFields, String strReturnInitValues,
			String[] strReturnValues, String[] strDisplayNames,
			String[] strDisplayFields, int nIndex, String strMainProperty,
			String strSQL, String[] strMatchValue, String strNextControls,
			String strTitle, String strFirstTD, String strSecondTD,
			boolean blnIsOptional, boolean blnIsRateCtrl) throws Exception {
		String strButtonName = "button";
		try {
			//检查放大镜参数
			//checkValue(strMainNames, strMainFields, true);
			//checkValue(strReturnNames, strReturnFields, strReturnValues, false);
			//checkValue(strDisplayNames, strDisplayFields, true);
			if (strMagnifierName == null || strFormName == null
					|| strFormName.equals("") || strSQL == null
					|| strSQL.equals("")) {
				throw new Exception();
			}
			if (strNextControls == null) {
				throw new Exception();
			}
			if (strMatchValue == null)//|| strMatchValue.equals(""))
			{
				strMatchValue = new String[1];
				strMatchValue[0] = strMainFields[0];
			} else {
				if (strMatchValue[0] == null || strMatchValue[0].equals("")) {
					strMatchValue[0] = strMainFields[0];
				}
			}

			if (strFirstTD == null) {
				strFirstTD = "";
			}
			if (strSecondTD == null) {
				strSecondTD = "";
			}

			if (strReturnInitValues == null) {
				strReturnInitValues = "";
			}

			//检查完毕
			//设置前缀
			if (strPrefix != null && !strPrefix.trim().equals("")) {
				for (int i = 0; i < strMainNames.length; i++) {
					strMainNames[i] = strPrefix + strMainNames[i];
				}
				for (int i = 0; i < strReturnNames.length; i++) {
					strReturnNames[i] = strPrefix + strReturnNames[i];
				}
			}
			//弹出窗口的属性
			String sFeatures = null;
			if (strDisplayNames.length < 3) {
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=520,height=450,scrollbars=yes";
			} else {
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=624,height=540,scrollbars=yes";
			}
			//生成传递给弹出窗口的参数字符串
			String strParam = "";
			strParam = "strFormName=" + strFormName;
			strParam += "&strMagnifierName="
					+ URLEncoder.encode(strMagnifierName);
			strParam += "&nIndex=" + nIndex;

			if (!isSQL(strSQL)) {
				strParam += "&strSQL= select * from ( '+" + strSQL
						+ "+' ) where 1=1 '+get" + strMainNames[0] + "("
						+ strMainNames[0] + ".value)+'";
			} else {
				strParam += "&strSQL= select * from ( " + strSQL
						+ " ) where 1=1 '+get" + strMainNames[0] + "("
						+ strMainNames[0] + ".value)+'";
			}

			if (strNextControls != null && !strNextControls.equals("")) {
				strParam += "&strNextControls=" + strNextControls;
			}

			for (int i = 0; i < strMainNames.length; i++) {
				strParam += "&strMainNames=" + strMainNames[i];
				strParam += "&strMainFields=" + strMainFields[i];
			}

			if (strReturnNames != null) {
				boolean bValue = false;
				if (strReturnValues != null
						&& strReturnValues.length == strReturnNames.length) {
					bValue = true;
				}
				for (int i = 0; i < strReturnNames.length; i++) {
					//生成数组参数
					strParam += "&strReturnNames=" + strReturnNames[i];
					strParam += "&strReturnFields=" + strReturnFields[i];
					if (bValue) {
						out.println("<input type=\"hidden\" name=\""
								+ strReturnNames[i] + "\" value=\""
								+ strReturnValues[i] + "\">");
					} else {
						out.println("<input type=\"hidden\" name=\""
								+ strReturnNames[i] + "\">");
					}
				}
			}

			for (int i = 0; i < strDisplayNames.length; i++) {
				//生成数组参数
				strParam += "&strDisplayNames="
						+ URLEncoder.encode(strDisplayNames[i]);
				strParam += "&strDisplayFields=" + strDisplayFields[i];
			}
			//Log.print("strParam = " + strParam);
			//生成查询按钮的事件字符串
			String strTmp = "";

			/*  TOCONFIG—TODELETE  */
			/*
			 * 产品化不再区分项目 
			 * ninh 
			 * 2005-03-24
			 */

			//	            if(Env.getProjectName().equals("cpf"))//特殊处理中油
			//	            {
			//	                strTmp = "cpfLoan";
			//	            }
			//	            else
			//	            {
			//	                strTmp = "iTreasury-loan";
			//	            }
			strTmp = "iTreasury-loan";

			/*  TOCONFIG—END  */

			String sOnKeydown = "if(" + strFormName + "." + strMainNames[0]
					+ ".disabled == false) {gnIsSelectCtrl=1;window.open('"
					+ Env.URL_PREFIX + "/" + strTmp
					+ "/magnifier/ShowMagnifierZoom.jsp?" + strParam
					+ "', 'SelectAnything', '" + sFeatures + "', false);}";
			//
			String sOnKeyUp = "";
			if (strReturnNames != null) {
				for (int i = 0; i < strReturnNames.length; i++) {
					sOnKeyUp += strReturnNames[i] + ".value = -1; ";
				}
			}

			int iPos = -1;
			//显示控件
			if (iPos == -1) {
				out
						.println("<td "
								+ strFirstTD
								+ " >"
								+ strTitle
								+ "：&nbsp;"
								+ "<img name=\""
								+ strButtonName
								+ "\" style=\"cursor: hand;\" src='/webloan/image/icon.gif' border=0 onclick=\""
								+ sOnKeydown + "\"></td>");
			} else {
				out
						.println("<td "
								+ strFirstTD
								+ " >"
								+ strTitle
								+ ":&nbsp;"
								+ "<img name=\""
								+ strButtonName
								+ "\" style=\"cursor: hand;\" src='/webloan/image/icon.gif' border=0 ></td>");
			}

			//blnIsOptional,是否可选项（仅对摘要、现金流向放大镜有效）
			if (blnIsOptional == true) {
				if (blnIsRateCtrl == true) {
					out.println("<td " + strSecondTD
							+ " ><input type=\"text\" name=\""
							+ strMainNames[0] + "\" value=\""
							+ strReturnInitValues + "\" class=\"tar\" "
							+ strMainProperty + ">%</td>");
				} else {
					out.println("<td " + strSecondTD
							+ " ><input type=\"text\" name=\""
							+ strMainNames[0] + "\" value=\""
							+ strReturnInitValues + "\" class=\"box\" "
							+ strMainProperty + "></td>");
				}
			} else {
				if (blnIsRateCtrl == true) {
					out.println("<td " + strSecondTD
							+ " ><input type=\"text\" name=\""
							+ strMainNames[0] + "\" value=\""
							+ strReturnInitValues + "\" class=\"tar\" "
							+ strMainProperty
							+ " onKeyDown=\"if(event.keyCode==13) "
							+ sOnKeydown + "\" onKeyUp=\"" + sOnKeyUp
							+ "\">%</td>");
				} else {
					out.println("<td " + strSecondTD
							+ " ><input type=\"text\" name=\""
							+ strMainNames[0] + "\" value=\""
							+ strReturnInitValues + "\" class=\"box\" "
							+ strMainProperty
							+ " onKeyDown=\"if(event.keyCode==13) "
							+ sOnKeydown + "\" onKeyUp=\"" + sOnKeyUp
							+ "\"></td>");
				}
			}

			out.println("<script language=\"JavaScript\">");
			out.println("function get" + strMainNames[0] + "(str)");
			out.println("{");
			out.println("   var sql = \"\"; ");
			out.println("   if (str != null && str != \"\") ");
			out.println("   {");
			if (strMatchValue == null) {
				out.println(" sql += \"\"; ");
			} else {
				if (strMatchValue.length == 1) {
					out.println(" sql +=  \" and " + strMatchValue[0]
							+ " like '" + URLEncoder.encode("%") + "\"+str+\""
							+ URLEncoder.encode("%") + "'\"; ");
				} else {
					out.println(" sql +=  \" and  ( \";");
					for (int i = 0; i < strMatchValue.length; i++) {
						if (i == 0) {
							if (strMatchValue[i] != null
									|| !strMatchValue[i].equals("")) {
								out.println(" sql +=  \"  " + strMatchValue[i]
										+ " like '" + URLEncoder.encode("%")
										+ "\"+str+\"" + URLEncoder.encode("%")
										+ "'\"; ");
							} else {
								out.println(" sql +=  \" 1=1 \"; ");
							}
						} else {
							if (strMatchValue[i] != null
									|| !strMatchValue[i].equals("")) {
								out.println(" sql +=  \" or  "
										+ strMatchValue[i] + " like '"
										+ URLEncoder.encode("%") + "\"+str+\""
										+ URLEncoder.encode("%") + "'\"; ");
							} else {
								out.println(" sql +=  \" 1=1 \"; ");
							}
						}
					}
					out.println(" sql +=  \" ) \";");
				}

			}
			out.println("   }");
			out.println("    ");
			out.println("   return sql;    ");
			out.println("}");
			out.println("</SCRIPT> ");
		} catch (Exception exp) {
			throw exp;
		}
	}
	
	private static boolean isSQL(String strSQL) {
		String strTemp = strSQL.toLowerCase();
		int nIndex = strTemp.indexOf("select ");
		if (nIndex == -1) {
			return false;
		}
		nIndex = strTemp.indexOf(" from ");
		if (nIndex == -1) {
			return false;
		}
		return true;
	}
	
	public static void showZoomCtrlNO(JspWriter out, String strMagnifierName,
			String strFormName, String strPrefix, String[] strMainNames,
			String[] strMainFields, String[] strReturnNames,
			String[] strReturnFields, String strReturnInitValues,
			String[] strReturnValues, String[] strDisplayNames,
			String[] strDisplayFields, int nIndex, String strMainProperty,
			String strSQL, String[] strMatchValue, String strNextControls,
			String strTitle, String strFirstTD, String strSecondTD,
			boolean blnIsOptional, boolean blnIsRateCtrl) throws Exception {
		String strButtonName = "button";
		try {
			//检查放大镜参数
			//checkValue(strMainNames, strMainFields, true);
			//checkValue(strReturnNames, strReturnFields, strReturnValues, false);
			//checkValue(strDisplayNames, strDisplayFields, true);
			if (strMagnifierName == null || strFormName == null
					|| strFormName.equals("") || strSQL == null
					|| strSQL.equals("")) {
				throw new Exception();
			}
			if (strNextControls == null) {
				throw new Exception();
			}
			if (strMatchValue == null)//|| strMatchValue.equals(""))
			{
				strMatchValue = new String[1];
				strMatchValue[0] = strMainFields[0];
			} else {
				if (strMatchValue[0] == null || strMatchValue[0].equals("")) {
					strMatchValue[0] = strMainFields[0];
				}
			}

			if (strFirstTD == null) {
				strFirstTD = "";
			}
			if (strSecondTD == null) {
				strSecondTD = "";
			}

			if (strReturnInitValues == null) {
				strReturnInitValues = "";
			}

			//检查完毕
			//设置前缀
			if (strPrefix != null && !strPrefix.trim().equals("")) {
				for (int i = 0; i < strMainNames.length; i++) {
					strMainNames[i] = strPrefix + strMainNames[i];
				}
				for (int i = 0; i < strReturnNames.length; i++) {
					strReturnNames[i] = strPrefix + strReturnNames[i];
				}
			}
			//弹出窗口的属性
			String sFeatures = null;
			if (strDisplayNames.length < 3) {
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=520,height=450,scrollbars=yes";
			} else {
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=624,height=540,scrollbars=yes";
			}
			//生成传递给弹出窗口的参数字符串
			String strParam = "";
			strParam = "strFormName=" + strFormName;
			strParam += "&strMagnifierName="
					+ URLEncoder.encode(strMagnifierName);
			strParam += "&nIndex=" + nIndex;

			if (!isSQL(strSQL)) {
				strParam += "&strSQL= select * from ( '+" + strSQL
						+ "+' ) where 1=1 '+get" + strMainNames[0] + "("
						+ strMainNames[0] + ".value)+'";
			} else {
				strParam += "&strSQL= select * from ( " + strSQL
						+ " ) where 1=1 '+get" + strMainNames[0] + "("
						+ strMainNames[0] + ".value)+'";
			}

			if (strNextControls != null && !strNextControls.equals("")) {
				strParam += "&strNextControls=" + strNextControls;
			}

			for (int i = 0; i < strMainNames.length; i++) {
				strParam += "&strMainNames=" + strMainNames[i];
				strParam += "&strMainFields=" + strMainFields[i];
			}

			if (strReturnNames != null) {
				boolean bValue = false;
				if (strReturnValues != null
						&& strReturnValues.length == strReturnNames.length) {
					bValue = true;
				}
				for (int i = 0; i < strReturnNames.length; i++) {
					//生成数组参数
					strParam += "&strReturnNames=" + strReturnNames[i];
					strParam += "&strReturnFields=" + strReturnFields[i];
					if (bValue) {
						out.println("<input type=\"hidden\" name=\""
								+ strReturnNames[i] + "\" value=\""
								+ strReturnValues[i] + "\">");
					} else {
						out.println("<input type=\"hidden\" name=\""
								+ strReturnNames[i] + "\">");
					}
				}
			}

			for (int i = 0; i < strDisplayNames.length; i++) {
				//生成数组参数
				strParam += "&strDisplayNames="
						+ URLEncoder.encode(strDisplayNames[i]);
				strParam += "&strDisplayFields=" + strDisplayFields[i];
			}
			//Log.print("strParam = " + strParam);
			//生成查询按钮的事件字符串
			String strTmp = "";

			/*  TOCONFIG—TODELETE  */
			/*
			 * 产品化不再区分项目 
			 * ninh 
			 * 2005-03-24
			 */

			//	            if(Env.getProjectName().equals("cpf"))//特殊处理中油
			//	            {
			//	                strTmp = "cpfLoan";
			//	            }
			//	            else
			//	            {
			//	                strTmp = "iTreasury-loan";
			//	            }
			strTmp = "iTreasury-loan";

			/*  TOCONFIG—END  */

			String sOnKeydown = "if(" + strFormName + "." + strMainNames[0]
					+ ".disabled == false) {gnIsSelectCtrl=1;window.open('"
					+ Env.URL_PREFIX + "/" + strTmp
					+ "/magnifier/ShowMagnifierZoom.jsp?" + strParam
					+ "', 'SelectAnything', '" + sFeatures + "', false);}";
			//
			String sOnKeyUp = "";
			if (strReturnNames != null) {
				for (int i = 0; i < strReturnNames.length; i++) {
					sOnKeyUp += strReturnNames[i] + ".value = -1; ";
				}
			}

			int iPos = -1;
			//显示控件
			if (iPos == -1) {
				out
						.println("<td "
								+ strFirstTD
								+ " >"
								+ strTitle
								+ "&nbsp;"
								+ "<img name=\""
								+ strButtonName
								+ "\" style=\"cursor: hand;\" src='/webloan/image/icon.gif' border=0 onclick=\""
								+ sOnKeydown + "\"></td>");
			} else {
				out
						.println("<td "
								+ strFirstTD
								+ " >"
								+ strTitle
								+ "&nbsp;"
								+ "<img name=\""
								+ strButtonName
								+ "\" style=\"cursor: hand;\" src='/webloan/image/icon.gif' border=0 ></td>");
			}

			//blnIsOptional,是否可选项（仅对摘要、现金流向放大镜有效）
			if (blnIsOptional == true) {
				if (blnIsRateCtrl == true) {
					out.println("<td " + strSecondTD
							+ " ><input type=\"text\" name=\""
							+ strMainNames[0] + "\" value=\""
							+ strReturnInitValues + "\" class=\"tar\" "
							+ strMainProperty + ">%</td>");
				} else {
					out.println("<td " + strSecondTD
							+ " ><input type=\"text\" name=\""
							+ strMainNames[0] + "\" value=\""
							+ strReturnInitValues + "\" class=\"box\" "
							+ strMainProperty + "></td>");
				}
			} else {
				if (blnIsRateCtrl == true) {
					out.println("<td " + strSecondTD
							+ " ><input type=\"text\" name=\""
							+ strMainNames[0] + "\" value=\""
							+ strReturnInitValues + "\" class=\"tar\" "
							+ strMainProperty
							+ " onKeyDown=\"if(event.keyCode==13) "
							+ sOnKeydown + "\" onKeyUp=\"" + sOnKeyUp
							+ "\">%</td>");
				} else {
					out.println("<td " + strSecondTD
							+ " ><input type=\"text\" name=\""
							+ strMainNames[0] + "\" value=\""
							+ strReturnInitValues + "\" class=\"box\" "
							+ strMainProperty
							+ " onKeyDown=\"if(event.keyCode==13) "
							+ sOnKeydown + "\" onKeyUp=\"" + sOnKeyUp
							+ "\"></td>");
				}
			}

			out.println("<script language=\"JavaScript\">");
			out.println("function get" + strMainNames[0] + "(str)");
			out.println("{");
			out.println("   var sql = \"\"; ");
			out.println("   if (str != null && str != \"\") ");
			out.println("   {");
			if (strMatchValue == null) {
				out.println(" sql += \"\"; ");
			} else {
				if (strMatchValue.length == 1) {
					out.println(" sql +=  \" and " + strMatchValue[0]
							+ " like '" + URLEncoder.encode("%") + "\"+str+\""
							+ URLEncoder.encode("%") + "'\"; ");
				} else {
					out.println(" sql +=  \" and  ( \";");
					for (int i = 0; i < strMatchValue.length; i++) {
						if (i == 0) {
							if (strMatchValue[i] != null
									|| !strMatchValue[i].equals("")) {
								out.println(" sql +=  \"  " + strMatchValue[i]
										+ " like '" + URLEncoder.encode("%")
										+ "\"+str+\"" + URLEncoder.encode("%")
										+ "'\"; ");
							} else {
								out.println(" sql +=  \" 1=1 \"; ");
							}
						} else {
							if (strMatchValue[i] != null
									|| !strMatchValue[i].equals("")) {
								out.println(" sql +=  \" or  "
										+ strMatchValue[i] + " like '"
										+ URLEncoder.encode("%") + "\"+str+\""
										+ URLEncoder.encode("%") + "'\"; ");
							} else {
								out.println(" sql +=  \" 1=1 \"; ");
							}
						}
					}
					out.println(" sql +=  \" ) \";");
				}

			}
			out.println("   }");
			out.println("    ");
			out.println("   return sql;    ");
			out.println("}");
			out.println("</SCRIPT> ");
		} catch (Exception exp) {
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
					+ "/iTreasury-craftbrother/magnifier/ShowMagnifierZoom.jsp?"
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
						+ "&nbsp;"
						+ "<a href=#><img name=\""
						+ strButtonName
						+ "\" src='/webcra/image/icon.gif' border=0 onclick=\""
						+ sOnKeydown
						+ "\"></a></td>");
				//image
			}
			else
			{
				out.println("<td " + strFirstTD + ">" + strTitle + "&nbsp;" + "<a href=#><img name=\"" + strButtonName + "\" src='/webcra/image/icon.gif' border=0 ></a></td>");
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
	 * 信贷资产转让合同编号放大镜
	 * 
	 * 显示合同编号，自动回带合同的业务类型
	 * 
	 * @param out
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @param lUserID
	 * @param strFormName
	 * @param strCtrlName
	 * @param lContractID
	 * @param strContractCode
	 * @param strRtnTransTypeCtrlName
	 * @param strNextControls
	 * @param sConditions
	 * @param strProperty
	 */
	public static void createTransferContractCodeCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		long lUserID,
		String strFormName,
		String strCtrlName,
		long lContractID,
		String strContractCode,
		String strRtnTransTypeCtrlName,
		String strNextControls,
		String[] sConditions,
		String strProperty,
		String strTitle)
	{
		String strMagnifierName = "转让合同编号";							//放大镜的名称
		String strPrefix = "";
		String[] strMainNames = {strCtrlName + "Ctrl",strRtnTransTypeCtrlName};			//放大镜回显栏位值列表
		String[] strMainFields = { "contractcode", "transtypeid"};				    //放大镜回显栏位对应的表格字段
		String[] strReturnNames = {strCtrlName};			//放大镜返回值列表(隐含值)
		String[] strReturnFields = {"id"};					//放大镜返回值(隐含值)对应的表格字段列表
		String[] strReturnValues = {String.valueOf(lContractID)};	//放大镜返回值(隐含值)对应的初始值
		String[] strDisplayNames = {"转让合同编号","业务类型","交易对手名称","合同金额","合同开始日期","合同结束日期"};	//放大镜小窗口显示的栏位名称
		String[] strDisplayFields = {"contractcode","TransType","counterpartname","amount","startdate","enddate"};					//放大镜小窗口显示栏位对应的表格字段
		String[] strMatchValues = {"contractcode"};
		String strMainProperty = " maxlength=50 " + (strProperty==null?"":strProperty);	//放大镜的对应控件栏位属性
		String strReturnInitValues = null; 
		int nIndex = 0;
		
		//如果需要作权限控制(即只有转让合同的录入人才能对合同进行付款和收款)，过滤出该用户录入的转让合同
		if(Config.getBoolean(ConfigConstant.GLOBAL_ISREQUIREPRIVILEGE,false)==false)
		{
			lUserID = -1;
		}
		   
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("getTransferContractCodeSQL("+lOfficeID+","+lCurrencyID+","+CRAconstant.TransactionStatus.USED+","+lUserID); //放大镜SQL
		
		if(sConditions!=null && sConditions.length>0)
		{
			for(int i=0;i<sConditions.length;i++)
			{
				sbSQL.append(",");
				
				if(sConditions[i]==null)
				{
					sbSQL.append("-1");
				}
				else
				{
					sbSQL.append(sConditions[i]+".value");
				}					
			}
		}
		
		sbSQL.append(")");
		
		//调用产生放大镜的方法
		try{
			CRAMagnifier.showZoomCtrl(
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
					sbSQL.toString(),
					strMatchValues,
					strNextControls,
					strTitle,
					"",
					"",
					false,
					false);
		}catch(Exception exp){
			log.error("转让合同放大镜[" + strCtrlName + "]异常：" + exp.toString());
		}
	}
	
	/**
	 * 信贷资产转让申请编号放大镜
	 * 
	 * 显示信贷资产转让申请编号，可回带交易对手名称
	 * 
	 * @param out
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @param lUserID 用户ID，如果要查询所有用户录入的申请，为-1
	 * @param strFormName
	 * @param strCtrlName
	 * @param l
	 * @param strContractCode
	 * @param strRtnTransTypeCtrlName
	 * @param strNextControls
	 * @param sConditions
	 * @param strProperty
	 */
	public static void createTransferApplyCodeCtrl(
		JspWriter out,
		long lOfficeID,
		long lCurrencyID,
		long lUserID,
		long lStatusID,
		String strFormName,
		String strCtrlName,
		long lApplyID,
		String strApplyCode,
		String strRtnCounterPartNameCtrlName,
		String strNextControls,
		String[] sConditions,
		String strProperty,
		String strTitle)
	{
		String strMagnifierName = "信贷资产转让申请编号";							//放大镜的名称
		String strPrefix = "";
		
		if (strRtnCounterPartNameCtrlName == null
				|| strRtnCounterPartNameCtrlName.equals("")) {
			strRtnCounterPartNameCtrlName = "ItIsNotControl";
		}
		
		String[] strMainNames = {strCtrlName + "Ctrl", strRtnCounterPartNameCtrlName};			//放大镜回显栏位值列表
		String[] strMainFields = { "sapplycode", "counterpartname"};				    //放大镜回显栏位对应的表格字段
		String[] strReturnNames = {strCtrlName};			//放大镜返回值列表(隐含值)
		String[] strReturnFields = {"id"};					//放大镜返回值(隐含值)对应的表格字段列表
		String[] strReturnValues = {String.valueOf(lApplyID)};	//放大镜返回值(隐含值)对应的初始值
		String[] strDisplayNames = {"转让申请编号","业务类型","交易对手名称","申请金额","开始日期","结束日期"};	//放大镜小窗口显示的栏位名称
		String[] strDisplayFields = {"sapplycode","transtype","counterpartname","sapplyamount","zstartdate","zenddate"};					//放大镜小窗口显示栏位对应的表格字段
		String[] strMatchValues = {"sapplycode"};
		String strMainProperty = " maxlength=50 " + (strProperty==null?"":strProperty);	//放大镜的对应控件栏位属性
		String strReturnInitValues = strApplyCode; 
		int nIndex = 0;
				   
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("getApplyCodeSQL4("+lOfficeID+","+lCurrencyID+","+lStatusID+","+lUserID); //放大镜SQL
		
		//可在此扩展查询条件
		if(sConditions!=null && sConditions.length>0)
		{
			for(int i=0;i<sConditions.length;i++)
			{
				sbSQL.append(",");
				
				if(sConditions[i]==null)
				{
					sbSQL.append("-1");
				}
				else
				{
					sbSQL.append(sConditions[i]+".value");
				}					
			}
		}
		
		sbSQL.append(")");
		
		//调用产生放大镜的方法
		try{
			CRAMagnifier.showZoomCtrlNO(
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
					sbSQL.toString(),
					strMatchValues,
					strNextControls,
					strTitle,
					"",
					"",
					false,
					false);
		}catch(Exception exp){
			log.error("转让申请放大镜[" + strCtrlName + "]异常：" + exp.toString());
		}
	}	
	
	
	
	
	/** 显示合同放大镜
	 * @param out
	 * @param strMagnifierName
	 * @param strFormName
	 * @param strPrefix
	 * @param strMainNames
	 * @param strMainFields
	 * @param strReturnNames
	 * @param strReturnFields
	 * @param strReturnInitValues
	 * @param strReturnValues
	 * @param nIndex
	 * @param strMatchValue
	 * @param strNextControls
	 * @param strFirstTD
	 * @param strSecondTD
	 * @param iPos
	 * @throws Exception
	 */
	public static void createApplyContractCtrl(JspWriter out, 
			String strMagnifierName,
			String strFormName, 
			String strPrefix, 
			String[] strMainNames,
			String[] strMainFields, 
			String[] strReturnNames,
			String[] strReturnFields, 
			String strReturnInitValues,
			String[] strReturnValues, 
			String strSQL,
			String[] strMatchValue, 
			String strNextControls,
			String strTitle,
			String strFirstTD, 
			String strSecondTD, 
			int iPos) throws Exception{
		
		String[] strDisplayNames = {"合同编号","交易对手"};
		String[] strDisplayFields = {"CODE","COUNTERPARTNAME"};
		String strMainProperty = "";
		int nIndex = 0; 
		
		showZoomCtrl(out, 
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
				iPos,
				false, 
				false);
	}	
	
	
	/**
	* 显示普通放大镜
	* @param JspWriter out
	* String strMagnifierName 放大镜的名称
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
	* @param iPos  放大镜图标显示在控件的位置(1.显示在控件的左边，2.显示在控件的右边)
	* @param blnIsOptional 是否是可选项（目前仅对摘要、现金流向放大镜有用）
	* @param blnIsRateCtrl 是否是利率控件
	* @throws Exception
	*/
	public static void showZoomCtrl(JspWriter out, 
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
			int iPos,
			boolean blnIsOptional, 
			boolean blnIsRateCtrl) throws Exception {
		String strButtonName = "button";
		try {
			//检查放大镜参数
			//checkValue(strMainNames, strMainFields, true);
			//checkValue(strReturnNames, strReturnFields, strReturnValues, false);
			//checkValue(strDisplayNames, strDisplayFields, true);
			if (strMagnifierName == null || strFormName == null
					|| strFormName.equals("") || strSQL == null
					|| strSQL.equals("")) {
				throw new Exception();
			}
			if (strNextControls == null) {
				throw new Exception();
			}
			if (strMatchValue == null)//|| strMatchValue.equals(""))
			{
				strMatchValue = new String[1];
				strMatchValue[0] = strMainFields[0];
			} else {
				if (strMatchValue[0] == null || strMatchValue[0].equals("")) {
					strMatchValue[0] = strMainFields[0];
				}
			}

			if (strFirstTD == null) {
				strFirstTD = "";
			}
			if (strSecondTD == null) {
				strSecondTD = "";
			}

			if (strReturnInitValues == null) {
				strReturnInitValues = "";
			}

			//检查完毕
			//设置前缀
			if (strPrefix != null && !strPrefix.trim().equals("")) {
				for (int i = 0; i < strMainNames.length; i++) {
					strMainNames[i] = strPrefix + strMainNames[i];
				}
				for (int i = 0; i < strReturnNames.length; i++) {
					strReturnNames[i] = strPrefix + strReturnNames[i];
				}
			}
			//弹出窗口的属性
			String sFeatures = null;
			if (strDisplayNames.length < 3) {
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=520,height=450,scrollbars=yes";
			} else {
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=624,height=540,scrollbars=yes";
			}
			//生成传递给弹出窗口的参数字符串
			String strParam = "";
			strParam = "strFormName=" + strFormName;
			strParam += "&strMagnifierName="
					+ URLEncoder.encode(strMagnifierName);
			strParam += "&nIndex=" + nIndex;
            String matchValue= URLEncoder.encode(strMainNames[0] + ".value");
			if (!isSQL(strSQL)) {
				strParam += "&strSQL= select * from ( '+" + strSQL
						+ "+' ) where 1=1 '+get" + strMainNames[0] + "("
						+ matchValue+")+'";
			} else {
				strParam += "&strSQL= select * from ( " + strSQL
						+ " ) where 1=1 '+get" + strMainNames[0] + "("
						+ matchValue+")+'";
			}

			if (strNextControls != null && !strNextControls.equals("")) {
				strParam += "&strNextControls=" + strNextControls;
			}

			for (int i = 0; i < strMainNames.length; i++) {
				strParam += "&strMainNames=" + strMainNames[i];
				strParam += "&strMainFields=" + strMainFields[i];
			}

			if (strReturnNames != null) {
				boolean bValue = false;
				if (strReturnValues != null
						&& strReturnValues.length == strReturnNames.length) {
					bValue = true;
				}
				for (int i = 0; i < strReturnNames.length; i++) {
					//生成数组参数
					strParam += "&strReturnNames=" + strReturnNames[i];
					strParam += "&strReturnFields=" + strReturnFields[i];
					if (bValue) {
						out.println("<input type=\"hidden\" name=\""
								+ strReturnNames[i] + "\" value=\""
								+ strReturnValues[i] + "\">");
					} else {
						out.println("<input type=\"hidden\" name=\""
								+ strReturnNames[i] + "\">");
					}
				}
			}

			for (int i = 0; i < strDisplayNames.length; i++) {
				//生成数组参数
				strParam += "&strDisplayNames="
						+ URLEncoder.encode(strDisplayNames[i]);
				strParam += "&strDisplayFields=" + strDisplayFields[i];
			}
			//Log.print("strParam = " + strParam);
			//生成查询按钮的事件字符串
			String strTmp = "";

			/*  TOCONFIG—TODELETE  */
			/*
			 * 产品化不再区分项目 
			 * ninh 
			 * 2005-03-24
			 */

			//	            if(Env.getProjectName().equals("cpf"))//特殊处理中油
			//	            {
			//	                strTmp = "cpfLoan";
			//	            }
			//	            else
			//	            {
			//	                strTmp = "iTreasury-loan";
			//	            }
			strTmp = "iTreasury-loan";

			/*  TOCONFIG—END  */

			String sOnKeydown = "if(" + strFormName + "." + strMainNames[0]
					+ ".disabled == false) {gnIsSelectCtrl=1;window.open('"
					+ Env.URL_PREFIX + "/" + strTmp
					+ "/magnifier/ShowMagnifierZoom.jsp?" + strParam
					+ "', 'SelectAnything', '" + sFeatures + "', false);}";
			//
			String sOnKeyUp = "";
			if (strReturnNames != null) {
				for (int i = 0; i < strReturnNames.length; i++) {
					sOnKeyUp += strReturnNames[i] + ".value = -1; ";
				}
			}
			
			out.println("<td " + strFirstTD + " >" + strTitle + "：&nbsp;");
			//显示放大镜图片，默认在控件左边
			if (iPos != 2) {
				out.println("<img name=\""
					+ strButtonName
					+ "\" style=\"cursor: hand;\" src='/webloan/image/icon.gif' border=0 onclick=\""
					+ sOnKeydown + "\">");
			}
			out.println("</td>");
		
			out.println("<td " + strSecondTD+ " >");
			//blnIsOptional,是否可选项（仅对摘要、现金流向放大镜有效）
			if (blnIsOptional == true) {
				if (blnIsRateCtrl == true) {
					out.println("<input type=\"text\" name=\""
							+ strMainNames[0] + "\" value=\""
							+ strReturnInitValues + "\" class=\"tar\" "
							+ strMainProperty + ">%");
				} else {
					out.println("<input type=\"text\" name=\""
							+ strMainNames[0] + "\" value=\""
							+ strReturnInitValues + "\" class=\"box\" "
							+ strMainProperty + ">");
				}
			} else {
				if (blnIsRateCtrl == true) {
					out.println("<input type=\"text\" name=\""
							+ strMainNames[0] + "\" value=\""
							+ strReturnInitValues + "\" class=\"tar\" "
							+ strMainProperty
							+ " onKeyDown=\"if(event.keyCode==13) "
							+ sOnKeydown + "\" onKeyUp=\"" + sOnKeyUp
							+ "\">%");
				} else {
					out.println("<input type=\"text\" name=\""
							+ strMainNames[0] + "\" value=\""
							+ strReturnInitValues + "\" class=\"box\" "
							+ strMainProperty
							+ " onKeyDown=\"if(event.keyCode==13) "
							+ sOnKeydown + "\" onKeyUp=\"" + sOnKeyUp
							+ "\">");
				}
			}
			//显示放大镜图片
			if (iPos == 2) {
				out.println("<img name=\""
					+ strButtonName
					+ "\" style=\"cursor: hand;\" src='/webloan/image/icon.gif' border=0 onclick=\""
					+ sOnKeydown + "\">");
			}
			out.println("</td>");
			out.println("<script language=\"JavaScript\">");
			out.println("function get" + strMainNames[0] + "(str)");
			out.println("{");
			out.println("   var sql = \"\"; ");
			out.println("   if (str != null && str != \"\") ");
			out.println("   {");
			if (strMatchValue == null) {
				out.println(" sql += \"\"; ");
			} else {
				if (strMatchValue.length == 1) {
					out.println(" sql +=  \" and " + strMatchValue[0]
							+ " like '" + URLEncoder.encode("%") + "\"+str+\""
							+ URLEncoder.encode("%") + "'\"; ");
				} else {
					out.println(" sql +=  \" and  ( \";");
					for (int i = 0; i < strMatchValue.length; i++) {
						if (i == 0) {
							if (strMatchValue[i] != null
									|| !strMatchValue[i].equals("")) {
								out.println(" sql +=  \"  " + strMatchValue[i]
										+ " like '" + URLEncoder.encode("%")
										+ "\"+str+\"" + URLEncoder.encode("%")
										+ "'\"; ");
							} else {
								out.println(" sql +=  \" 1=1 \"; ");
							}
						} else {
							if (strMatchValue[i] != null
									|| !strMatchValue[i].equals("")) {
								out.println(" sql +=  \" or  "
										+ strMatchValue[i] + " like '"
										+ URLEncoder.encode("%") + "\"+str+\""
										+ URLEncoder.encode("%") + "'\"; ");
							} else {
								out.println(" sql +=  \" 1=1 \"; ");
							}
						}
					}
					out.println(" sql +=  \" ) \";");
				}
				//					out.println(" sql +=  \" and " + strMatchValue + " like '" + URLEncoder.encode("%") + "\"+str+\"" + URLEncoder.encode("%") + "'\";    ");
			}
			out.println("   }");
			out.println("    ");
			out.println("   return sql;    ");
			out.println("}");
			out.println("</SCRIPT> ");
		} catch (Exception exp) {
			throw exp;
		}
	}		

	
}
