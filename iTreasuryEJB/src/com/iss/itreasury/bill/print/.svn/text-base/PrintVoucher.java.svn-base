/*
 * Created on 2005-3-18
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.bill.print;

import java.text.DecimalFormat;

import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.bill.print.templateinfo.ShowSpecialTransInfo;
import com.iss.itreasury.bill.print.templateinfo.ShowWithDrawInfo;
import com.iss.itreasury.bill.util.BillNameRef;
import com.iss.itreasury.util.ChineseCurrency;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;

/**
 * @author zntan
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PrintVoucher
{
	/**
	 * 
	 */
	public PrintVoucher()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 *	显示特种转账贷方传票
	 *	凭证注册 & 领用
	 */
	public static void PrintBlankCredit(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			ShowSpecialTransInfo info = toShowSpecialTransInfo(pi);
			IPrintTemplate.showCredit(info,out);
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{}
	}
	
	/**
	 *	显示特种转账借方传票
	 *	凭证注册 & 领用
	 */
	public static void PrintBlankDebit(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			ShowSpecialTransInfo info = toShowSpecialTransInfo(pi);
			IPrintTemplate.showDebtor(info,out);
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{}
	}
	
	private static ShowSpecialTransInfo toShowSpecialTransInfo(PrintInfo pi)
	{
		ShowSpecialTransInfo info = new ShowSpecialTransInfo();
		String strExecuteDate = DataFormat.formatDate(pi.getExecuteDate());
		if (strExecuteDate.length() < 10)
			strExecuteDate = "0000000000";
		String strYear = strExecuteDate.substring(0, 4);
		info.setYear(strYear);
		String strMonth = strExecuteDate.substring(5, 7);
		info.setMonth(strMonth);
		String strDay = strExecuteDate.substring(8, 10);
		info.setDay(strDay);
		info.setTransNo(pi.getTransNo());
		info.setAbstract(pi.getAbstract());
		info.setAmount(DataFormat.formatDisabledAmount(pi.getAmount()));
		if (pi.getCheckUserID()> 0)
			info.setCheckUserName(BillNameRef.getUserNameByID(pi.getCheckUserID()));
		else info.setCheckUserName("&nbsp;&nbsp;&nbsp;&nbsp;");
		if (pi.getInputUserID() > 0)
			info.setInputUserName(BillNameRef.getUserNameByID(pi.getInputUserID()));
		else info.setInputUserName("&nbsp;&nbsp;&nbsp;&nbsp;");
		DecimalFormat df = new DecimalFormat("#.00");
		String tmp = "0.00";
		if (pi.getAmount() > 0.00)
			tmp = df.format(pi.getAmount());
		if (tmp.indexOf(".") ==0 )
			tmp = "0"+tmp;
		info.setChineseAmount(ChineseCurrency.showChinese(tmp));
		info.setPayAccountName(pi.getPayClient());
		info.setPayAccountNo(pi.getPayAccount());
		info.setPayBankCode(pi.getPayBank());
		info.setReceiveAccountName(pi.getReceiveClient());
		info.setReceiveAccountNo(pi.getReceiveAccount());
		info.setReceiveBankName(pi.getReceiveBank());
		return info;
	}
	
	/**
	 * 打印存款支取凭证
	 * @param pi
	 * @param out
	 */
	public static void PrintWithDraw(PrintInfo pi,JspWriter out) throws Exception
	{
		ShowWithDrawInfo info = new ShowWithDrawInfo();
		info.setAbstract(pi.getAbstract());
		info.setAmount(DataFormat.formatDisabledAmount(pi.getAmount()));
		info.setCheckUserName(BillNameRef.getUserNameByID(pi.getCheckUserID()));
		DecimalFormat df = new DecimalFormat("#.00");
		String tmp = "0.00";
		if (pi.getAmount() > 0.00)
			tmp = df.format(pi.getAmount());
		if (tmp.indexOf(".") ==0 )
			tmp = "0"+tmp;
		info.setChineseAmount(ChineseCurrency.showChinese(tmp));
		info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
		String strExecuteDate = DataFormat.formatDate(pi.getExecuteDate());
		if (strExecuteDate.length() < 10)
			strExecuteDate = "0000000000";
		String strYear = strExecuteDate.substring(0, 4);
		info.setYear(strYear);
		String strMonth = strExecuteDate.substring(5, 7);
		info.setMonth(strMonth);
		String strDay = strExecuteDate.substring(8, 10);
		info.setDay(strDay);
		info.setEnvClientName(Env.getClientName());
		info.setInputUserName(BillNameRef.getUserNameByID(pi.getInputUserID()));
		info.setOBCheckUserName("&nbsp;&nbsp;&nbsp;&nbsp;");
		info.setOBInputUserName("&nbsp;&nbsp;&nbsp;&nbsp;");
		info.setOBSignUserName("&nbsp;&nbsp;&nbsp;&nbsp;");
		info.setPayAccountName(BillNameRef.getAccountNameByID(pi.getPayAccountID()));
		info.setPayAccountNo(BillNameRef.getAccountNoByID(pi.getPayAccountID()));
		info.setPayBankName(pi.getPayBank());
		info.setPayRemitInAddress("");
		//info.setReceiveAccountName(pi.getPayAccount());
		info.setReceiveBankName(pi.getReceiveBank());
		info.setReceiveAccountName(pi.getReceiveClient());
		info.setTransNo(pi.getTransNo());
		IPrintTemplate.showWithDraw1(info,out);
		out.println("<br clear=all style='page-break-before:always'>");
		IPrintTemplate.showWithDraw2(info,out);
	}
	
	/**
	 *	显示套打 共用程序
	 * @throws Exception
	 */
	public static void PrintTemplate(long lPrintTemplateID, int lPrintTemplateTypeID, PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			String strExecuteDate = DataFormat.formatDate(pi.getExecuteDate());
			if (strExecuteDate.length() < 10)
				strExecuteDate = "0000000000";
			String strYear = strExecuteDate.substring(0, 4);
			String strMonth = strExecuteDate.substring(5, 7);
			String strDay = strExecuteDate.substring(8, 10);
			String strTransNo = pi.getTransNo();
			String strPayProvince = "";
			String strPayCity = "";
			String strPayBankName = "";
			String strPayAccountName = "";
			String strPayAccountNo = "";
			String strReceiveAccountName = "";
			String strReceiveAccountNo = "";
			String strReceiveBankName = "";
			String strReceiveProvince = "";
			String strReceiveCity = "";
			if (pi.getPayAccountID() > 0)
			{
				//strPayAccountName = DataFormat.formatString(NameRef.getAccountNameByID(pi.getPayAccountID()));
				//strPayAccountNo = DataFormat.formatString(NameRef.getBankAccountCodeByID(pi.getPayAccountID(), pi.getPayBankID()));
				//strPayBankName = DataFormat.formatString(IPrintTemplate.getBankAccountPrintNameByID(pi.getPayBankID()));
				//strPayProvince = DataFormat.formatString(IPrintTemplate.getBankAccountProvinceByID(pi.getPayBankID()));
				//strPayCity = DataFormat.formatString(IPrintTemplate.getBankAccountCityByID(pi.getPayBankID()));
			}
			else
			{
				//strPayAccountName = DataFormat.formatString(pi.getExtClientName());
				//strPayAccountNo = DataFormat.formatString(pi.getExtAccountNo());
				//strPayBankName = DataFormat.formatString(pi.getExtRemitInBank());
				//strPayProvince = DataFormat.formatString(pi.getExtRemitInProvince());
				//strPayCity = DataFormat.formatString(pi.getExtRemitInCity());
			}
			if (pi.getReceiveAccountID() > 0)
			{
				//strReceiveAccountName = DataFormat.formatString(NameRef.getAccountNameByID(pi.getReceiveAccountID()));
				//strReceiveAccountNo = DataFormat.formatString(NameRef.getBankAccountCodeByID(pi.getReceiveAccountID(), pi.getReceiveBankID()));
				//strReceiveBankName = DataFormat.formatString(IPrintTemplate.getBankAccountPrintNameByID(pi.getReceiveBankID()));
				//strReceiveProvince = DataFormat.formatString(IPrintTemplate.getBankAccountProvinceByID(pi.getReceiveBankID()));
				//strReceiveCity = DataFormat.formatString(IPrintTemplate.getBankAccountCityByID(pi.getReceiveBankID()));
			}
			else
			{
				//strReceiveAccountName = DataFormat.formatString(pi.getExtClientName());
				//strReceiveAccountNo = DataFormat.formatString(pi.getExtAccountNo());
				//strReceiveBankName = DataFormat.formatString(pi.getExtRemitInBank());
				//strReceiveProvince = DataFormat.formatString(pi.getExtRemitInProvince());
				//strReceiveCity = DataFormat.formatString(pi.getExtRemitInCity());
			}
			String strAmount = DataFormat.formatAmount(pi.getAmount());
			String strChineseAmount = ChineseCurrency.showChinese(strAmount, pi.getCurrencyID());
			strAmount = DataFormat.formatDisabledAmount(pi.getAmount());
			String strInputUser = BillNameRef.getUserNameByID(pi.getInputUserID());
			String strCheckUser = BillNameRef.getUserNameByID(pi.getCheckUserID());
			String strAbstract = pi.getAbstract();
			switch (lPrintTemplateTypeID)
			{
				case 1 : //银行进账单
					String[] strCode1 = { "01", "02", "03", "04", "05", "08", "09", "10", "13", "14", "15", "16", "17", "19", "0026", "0029", "0027", "0031" };
					com.iss.itreasury.settlement.print.IPrintTemplate.showTemplate(
						out,
						strCode1,
						lPrintTemplateID,
						strYear,
						strMonth,
						strDay,
						strPayAccountName,
						strPayAccountNo,
						strPayBankName,
						strReceiveAccountName,
						strReceiveAccountNo,
						strReceiveBankName,
						strChineseAmount,
						strAmount,
						"",
						"",
						"",
						"",
						strCheckUser,
						strInputUser,
						"",
						"",
						"",
						"",
						"",
						"",
						"",
						"",
						"",
						"",
						"");
					break;
				case 2 : //电汇信汇
					String[] strCode2 = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "18", "0026", "0029", "0027", "0031" };
					com.iss.itreasury.settlement.print.IPrintTemplate.showTemplate(
						out,
						strCode2,
						lPrintTemplateID,
						strYear,
						strMonth,
						strDay,
						strPayAccountName,
						strPayAccountNo,
						strPayProvince,
						strPayCity,
						strPayBankName,
						strReceiveAccountName,
						strReceiveAccountNo,
						strReceiveProvince,
						strReceiveCity,
						strReceiveBankName,
						strChineseAmount,
						strAmount,
						"",
						"",
						strCheckUser,
						strInputUser,
						"",
						"",
						"",
						"",
						"",
						"",
						"",
						"",
						"");
					break;
				case 3 : //银行汇票委托书
					String[] strCode3 = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "14", "15", "18", "19", "34", "0026", "0027", "0028", "0033" };
					com.iss.itreasury.settlement.print.IPrintTemplate.showTemplate(
						out,
						strCode3,
						lPrintTemplateID,
						strYear,
						strMonth,
						strDay,
						strPayAccountName,
						strPayAccountNo,
						strPayProvince,
						strPayCity,
						strPayBankName,
						strReceiveAccountName,
						strReceiveAccountNo,
						strChineseAmount,
						strAmount,
						strAbstract,
						"",
						"",
						strCheckUser,
						strInputUser,
						"",
						"",
						"",
						"",
						"",
						"",
						"",
						"",
						"",
						"",
						"");
					break;
			}
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
		}
	}
	
	public static void main(String[] arg) 
	{
		DecimalFormat df = new DecimalFormat("#.00");
		String tmp = "0.00";
		double amount = 10.03;
		if (amount > 0)
			tmp = df.format(amount);
		if (tmp.indexOf(".") ==0 )
			tmp = "0"+tmp;
		System.out.println(tmp);
		System.out.print(ChineseCurrency.showChinese(tmp));
	}
}
