/*
 * Created on 2003-10-30
 * 
 * To change the template for this generated file go to Window - Preferences - Java - Code Generation - Code and
 * Comments
 */
package com.iss.itreasury.settlement.print;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.discount.dataentity.DiscountCredenceInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.account.bizlogic.AccountOperation;
import com.iss.itreasury.settlement.account.dataentity.SubAccountAssemblerInfo;
import com.iss.itreasury.settlement.bizdelegation.TransDiscountDelegation;
import com.iss.itreasury.settlement.print.templateinfo.ShowDepositInterestInfo;
import com.iss.itreasury.settlement.print.templateinfo.ShowFixedDepositOpenInfo;
import com.iss.itreasury.settlement.print.templateinfo.ShowGrantDiscountInfo;
import com.iss.itreasury.settlement.print.templateinfo.ShowGrantLoanInfo;
import com.iss.itreasury.settlement.print.templateinfo.ShowInInfo;
import com.iss.itreasury.settlement.print.templateinfo.ShowNoticeOpenInfo;
import com.iss.itreasury.settlement.print.templateinfo.ShowPayInterestInfo;
import com.iss.itreasury.settlement.print.templateinfo.ShowRepaymentDiscountInfo;
import com.iss.itreasury.settlement.print.templateinfo.ShowRepaymentLoanInfo;
import com.iss.itreasury.settlement.print.templateinfo.ShowSpecialTransInfo;
import com.iss.itreasury.settlement.print.templateinfo.ShowWithDrawInfo;
import com.iss.itreasury.settlement.print.templateinfo.SubsectionDateInfo;
import com.iss.itreasury.settlement.setting.dao.Sett_BranchDAO;
import com.iss.itreasury.settlement.setting.dataentity.BranchInfo;
import com.iss.itreasury.settlement.transloan.dao.Sett_TransGrantLoanDAO;
import com.iss.itreasury.settlement.transloan.dataentity.LoanPayFormDetailInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.ChineseCurrency;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

/**
 * @author rxie
 * 
 * To change the template for this generated type comment go to Window - Preferences - Java - Code Generation - Code and
 * Comments
 */
public class IPrintVoucher
{

	Log4j	logger	= null;

	/**
	 * 
	 */
	public IPrintVoucher()
	{

		super();
		// TODO Auto-generated constructor stub
		logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	}

	/**
	 * 由币种ID取得币种标志
	 * 
	 * @param lCurrencyID
	 * @return
	 */
	public static String getCurrencySymbolByCurrencyID(long lCurrencyID)
	{

		String strReturn = "";
		if (lCurrencyID == 1)
		{
			strReturn = "￥";
		}
		if (lCurrencyID == 2)
		{
			strReturn = "＄";
		}
		return strReturn;
	}

	/**
	 * @param sDate Date of Start
	 * @param eDate Date of End
	 * @param intervalDaysFlag flag for caculating interval: 1: caculating as fact days. 2: caculating as 30 days per
	 *            month
	 * @exception IException throw it while business exception occur and transaction need rollback
	 * @return Interval Days
	 */
	public static long getIntervalDays(Timestamp sDate, Timestamp eDate, long intervalDaysFlag) throws Exception
	{

		System.out.println("=================sDate:" + sDate);
		System.out.println("=================eDate:" + eDate);
		long resIntervalDays = -1;
		if (sDate != null && eDate != null)
		{
			GregorianCalendar sCalendar = new GregorianCalendar();
			sCalendar.setTime(sDate);
			GregorianCalendar eCalendar = new GregorianCalendar();
			eCalendar.setTime(eDate);
			if (intervalDaysFlag == 1)
			{
				resIntervalDays = getFactIntervalDays(sCalendar, eCalendar);
			}
			else
			{
				if (sCalendar.get(Calendar.DAY_OF_MONTH) > 30)
					sCalendar.set(Calendar.DAY_OF_MONTH, 30);
				if (eCalendar.get(Calendar.DAY_OF_MONTH) == Calendar.FEBRUARY
						&& (eCalendar.get(Calendar.DAY_OF_MONTH) == 28 || eCalendar.get(Calendar.DAY_OF_MONTH) == 29))
				{
					eCalendar.set(Calendar.DAY_OF_MONTH, 30);
				}
				int intervalYears = eCalendar.get(Calendar.YEAR) - sCalendar.get(Calendar.YEAR);
				int intervalMonths = eCalendar.get(Calendar.MONTH) - sCalendar.get(Calendar.MONTH);
				int intervalDays = eCalendar.get(Calendar.DAY_OF_MONTH) - sCalendar.get(Calendar.DAY_OF_MONTH);
				resIntervalDays = intervalYears * 360 + intervalMonths * 30 + intervalDays;
			}
		}
		return resIntervalDays;
	}

	/**
	 * 开始计算实际间隔日期
	 * 
	 * @param sG 起息日 GregorianCalendar
	 * @param eG 结息日 GregorianCalendar
	 * @return
	 */
	private static synchronized long getFactIntervalDays(GregorianCalendar sG, GregorianCalendar eG)
	{

		long elapsed = 0;
		GregorianCalendar gc1, gc2;
		gc2 = (GregorianCalendar) eG.clone();
		gc1 = (GregorianCalendar) sG.clone();
		gc1.clear(Calendar.MILLISECOND);
		gc1.clear(Calendar.SECOND);
		gc1.clear(Calendar.MINUTE);
		gc1.clear(Calendar.HOUR_OF_DAY);
		gc2.clear(Calendar.MILLISECOND);
		gc2.clear(Calendar.SECOND);
		gc2.clear(Calendar.MINUTE);
		gc2.clear(Calendar.HOUR_OF_DAY);
		while (gc1.before(gc2))
		{
			// Adds the specified (signed) amount of time to the given time field, based on the calendar's rules.
			gc1.add(Calendar.DATE, 1);
			elapsed++;
		}
		return elapsed;
	}

	/**
	 * 进账单打印 包括进账单1和进账单2
	 * 
	 * @throws Exception edited by gqzhang
	 */
	public static void PrintShowIn(PrintInfo pi, JspWriter out) throws Exception
	{

		try
		{
			ShowInInfo info = new ShowInInfo();
			String strExecuteDate = DataFormat.formatDate(pi.getExecuteDate());
			if (strExecuteDate.length() < 10)
				strExecuteDate = "0000000000";
			String strYear = strExecuteDate.substring(0, 4);
			info.setYear(strYear);
			String strMonth = strExecuteDate.substring(5, 7);
			info.setMonth(strMonth);
			String strDay = strExecuteDate.substring(8, 10);
			info.setDay(strDay);
			String strInterestStart = DataFormat.formatDate(pi.getInterestStartDate());
			info.setTransNo(pi.getTransNo());
			String strPayAccountName = "";
			String strPayAccountNo = "";
			// 付款方账户
			if (pi.getPayAccountID() > 0)
			{
				strPayAccountName = NameRef.getAccountNameByID(pi.getPayAccountID());
				info.setPayAccountName(strPayAccountName);
				strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
				info.setPayAccountNo(strPayAccountNo);
				info.setPayBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			else if (pi.getPayBankID() > 0)
			{
				Sett_BranchDAO ldao = new Sett_BranchDAO();
				BranchInfo linfo = new BranchInfo();
				linfo = ldao.findByID(pi.getPayBankID());
				info.setPayAccountName(DataFormat.formatString(linfo.getEnterpriseName()));
				info.setPayAccountNo(DataFormat.formatString(linfo.getBankAccountCode()));
				info.setPayBankName(DataFormat.formatString(linfo.getBranchName()));
			}

			else if (pi.getPayExtClientName() != null && pi.getPayExtClientName().trim().length() > 0)
			{
				strPayAccountName = pi.getPayExtClientName();
				info.setPayAccountName(strPayAccountName);
				strPayAccountNo = pi.getPayExtAccountNo();
				info.setPayAccountNo(strPayAccountNo);
				info.setPayBankName(pi.getPayExtRemitInBank());
			}
			else
			// 总账
			{
				strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
				info.setPayAccountName(strPayAccountName);
				strPayAccountNo = NameRef.getGLTypeNoByID(pi.getPayGL());
				info.setPayAccountNo(strPayAccountNo);
			}
			// 收款方账户
			String strReceiveAccountName = "";
			String strReceiveAccountNo = "";
			System.out.println("!!!!!!!!!! 收款账户：    " + pi.getReceiveBankID());
			if (pi.getReceiveAccountID() > 0)
			{
				strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveAccountID());
				info.setReceiveAccountName(strReceiveAccountName);
				strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
				info.setReceiveAccountNo(strReceiveAccountNo);
				info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}

			else if (pi.getReceiveBankID() > 0)
			{
				Sett_BranchDAO ldao = new Sett_BranchDAO();
				BranchInfo linfo = new BranchInfo();
				linfo = ldao.findByID(pi.getReceiveBankID());
				info.setReceiveAccountName(DataFormat.formatString(linfo.getEnterpriseName()));
				info.setReceiveAccountNo(DataFormat.formatString(linfo.getBankAccountCode()));
				info.setReceiveBankName(DataFormat.formatString(linfo.getBranchName()));
			}

			else if (pi.getReceiveExtClientName() != null && pi.getReceiveExtClientName().trim().length() > 0)
			{
				strReceiveAccountName = pi.getReceiveExtClientName();
				info.setReceiveAccountName(strReceiveAccountName);
				strReceiveAccountNo = pi.getReceiveExtAccountNo();
				info.setReceiveAccountNo(strReceiveAccountNo);
				info.setReceiveBankName(pi.getReceiveExtRemitInBank());
			}
			else
			// 总账
			{

				strReceiveAccountName = NameRef.getGLTypeDescByID(pi.getReceiveGL());
				info.setReceiveAccountName(strReceiveAccountName);
				strReceiveAccountNo = NameRef.getGLTypeNoByID(pi.getReceiveGL());
				info.setReceiveAccountNo(strReceiveAccountNo);

			}
			// 取得金额
			String strAmount = DataFormat.formatAmount(pi.getAmount());
			info.setAmount(strAmount);
			String strChineseAmount = ChineseCurrency.showChinese(strAmount, pi.getCurrencyID());
			info.setChineseAmount(strChineseAmount);
			strAmount = getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ DataFormat.formatDisabledAmount(pi.getAmount());
			info.setAmount(strAmount);
			// 取得用户名称
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));

			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));

			String strAbstract = "起息日：" + strInterestStart + "；" + pi.getAbstract();
			info.setAbstract(strAbstract);

			IPrintTemplate.showIn1(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showIn2(info, out);

		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
		}
	}

	/**
	 * 存款支取凭证打印 包括存款支取凭证1和存款支取凭证2
	 * 
	 * @throws Exception edited by gqzhang
	 */
	public static void PrintShowWithDraw(PrintInfo pi, JspWriter out) throws Exception
	{

		try
		{
			ShowWithDrawInfo info = new ShowWithDrawInfo();
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
			String strPayAccountName = "";
			String strPayAccountNo = "";
			// 付款方账户
			if (pi.getPayAccountID() > 0)
			{
				strPayAccountName = NameRef.getAccountNameByID(pi.getPayAccountID());
				info.setPayAccountName(DataFormat.formatString(strPayAccountName));
				strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
				info.setPayAccountNo(DataFormat.formatString(strPayAccountNo));
				info.setPayBankName(DataFormat.formatString(NameRef.getOfficeNameByID(pi.getOfficeID())));
				System.out.println("付款方开户行1：" + info.getPayBankName());
			}

			else if (pi.getPayBankID() > 0)
			{
				Sett_BranchDAO ldao = new Sett_BranchDAO();
				BranchInfo linfo = new BranchInfo();
				linfo = ldao.findByID(pi.getPayBankID());
				info.setPayAccountName(DataFormat.formatString(linfo.getEnterpriseName()));
				info.setPayAccountNo(DataFormat.formatString(linfo.getBankAccountCode()));
				info.setPayBankName(DataFormat.formatString(linfo.getBranchName()));
			}
			else if (pi.getPayExtClientName() != null && pi.getPayExtClientName().trim().length() > 0)
			{
				strPayAccountName = pi.getPayExtClientName();
				info.setPayAccountName(DataFormat.formatString(strPayAccountName));
				strPayAccountNo = pi.getPayExtAccountNo();
				info.setPayAccountNo(DataFormat.formatString(strPayAccountNo));
				info.setPayBankName(DataFormat.formatString(pi.getPayExtRemitInBank()));
				System.out.println("付款外部汇入行2：" + info.getPayBankName());
			}
			else
			// 总账
			{
				strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
				info.setPayAccountName(DataFormat.formatString(strPayAccountName));
				strPayAccountNo = NameRef.getGLTypeNoByID(pi.getPayGL());
				info.setPayAccountNo(DataFormat.formatString(strPayAccountNo));
			}
			// 收款方账户
			String strReceiveAccountName = "";
			String strReceiveAccountNo = "";
			if (pi.getReceiveAccountID() > 0)
			{
				strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveAccountID());
				info.setReceiveAccountName(DataFormat.formatString(strReceiveAccountName));
				strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
				info.setReceiveAccountNo(DataFormat.formatString(strReceiveAccountNo));
				info.setReceiveBankName(DataFormat.formatString(NameRef.getOfficeNameByID(pi.getOfficeID())));
				// info.setReceiveRemitInAddress("&nbsp;");
				// info.setReceiveRemitInAddress(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}

			else if (pi.getReceiveBankID() > 0)
			{
				Sett_BranchDAO ldao = new Sett_BranchDAO();
				BranchInfo linfo = new BranchInfo();
				linfo = ldao.findByID(pi.getReceiveBankID());
				info.setReceiveAccountName(DataFormat.formatString(linfo.getEnterpriseName()));
				info.setReceiveAccountNo(DataFormat.formatString(linfo.getBankAccountCode()));
				info.setReceiveBankName(DataFormat.formatString(linfo.getBranchName()));
				info.setReceiveRemitInAddress(DataFormat.formatString(linfo.getBranchCity()));
			}
			else if (pi.getReceiveExtClientName() != null && pi.getReceiveExtClientName().trim().length() > 0)
			{
				strReceiveAccountName = pi.getReceiveExtClientName();
				info.setReceiveAccountName(DataFormat.formatString(strReceiveAccountName));
				strReceiveAccountNo = pi.getReceiveExtAccountNo();
				info.setReceiveAccountNo(DataFormat.formatString(strReceiveAccountNo));
				info.setReceiveBankName(DataFormat.formatString(pi.getReceiveExtRemitInBank()));
				// 收款人汇入地点
				info.setReceiveRemitInAddress(DataFormat.formatString(DataFormat.formatString(pi
						.getReceiveExtRemitInProvince())
						+ DataFormat.formatString(pi.getReceiveExtRemitInCity())));
			}
			else
			// 总账
			{
				strReceiveAccountName = NameRef.getGLTypeDescByID(pi.getReceiveGL());
				info.setReceiveAccountName(DataFormat.formatString(strReceiveAccountName));
				// strReceiveAccountNo = NameRef.getGLTypeNoByID(pi.getReceiveGL());
				info.setReceiveAccountNo(DataFormat.formatString(strReceiveAccountNo));
			}
			String strAmount = DataFormat.formatAmount(pi.getAmount());
			info.setAmount(strAmount);
			String strChineseAmount = ChineseCurrency.showChinese(strAmount, pi.getCurrencyID());
			info.setChineseAmount(strChineseAmount);
			strAmount = getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ DataFormat.formatDisabledAmount(pi.getAmount());
			info.setAmount(strAmount);
			String strInputUser = NameRef.getUserNameByID(pi.getInputUserID());
			info.setInputUserName(strInputUser);
			String strCheckUser = NameRef.getUserNameByID(pi.getCheckUserID());
			info.setCheckUserName(strCheckUser);
			String strAbstract = pi.getAbstract();
			info.setAbstract(strAbstract);
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
			info = PrintVoucher.getOBInfoByTransNo(info);

			IPrintTemplate.showWithDraw1(info, out);

			out.println("<br clear=all style='page-break-before:always'>");

			IPrintTemplate.showWithDraw2(info, out);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
		}
	}

	/**
	 * 显示特种转账贷方传票
	 * 
	 * @throws Exception edited by gqzhang
	 */
	public static void PrintShowCredit(PrintInfo pi, JspWriter out) throws Exception
	{

		try
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
			String strPayBankName = "";
			info.setPayBankName(strPayBankName);
			String strReceiveBankName = "";
			info.setReceiveBankName(strReceiveBankName);
			String strPayAccountName = "";
			String strPayAccountNo = "";
			// 付款方账户
			if (pi.getPayAccountID() > 0)
			{
				strPayAccountName = NameRef.getAccountNameByID(pi.getPayAccountID());
				info.setPayAccountName(strPayAccountName);
				strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
				info.setPayAccountNo(strPayAccountNo);
				info.setPayBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}

			else if (pi.getPayBankID() > 0)
			{
				Sett_BranchDAO ldao = new Sett_BranchDAO();
				BranchInfo linfo = new BranchInfo();
				linfo = ldao.findByID(pi.getPayBankID());
				info.setPayAccountName(DataFormat.formatString(linfo.getEnterpriseName()));
				info.setPayAccountNo(DataFormat.formatString(linfo.getBankAccountCode()));
				info.setPayBankName(DataFormat.formatString(linfo.getBranchName()));
			}
			else if (pi.getPayExtClientName() != null && pi.getPayExtClientName().trim().length() > 0)
			{
				strPayAccountName = pi.getPayExtClientName();
				info.setPayAccountName(strPayAccountName);
				strPayAccountNo = pi.getPayExtAccountNo();
				info.setPayAccountNo(strPayAccountNo);
				info.setPayBankName(pi.getPayExtRemitInBank());
			}
			else
			// 总账
			{
				strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
				info.setPayAccountName(strPayAccountName);
				strPayAccountNo = NameRef.getGLTypeNoByID(pi.getPayGL());
				info.setPayAccountNo(strPayAccountNo);
			}
			// 收款方账户
			String strReceiveAccountName = "";
			String strReceiveAccountNo = "";
			if (pi.getReceiveAccountID() > 0)
			{
				strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveAccountID());
				info.setReceiveAccountName(strReceiveAccountName);
				strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
				info.setReceiveAccountNo(strReceiveAccountNo);
				info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}

			else if (pi.getReceiveBankID() > 0)
			{
				Sett_BranchDAO ldao = new Sett_BranchDAO();
				BranchInfo linfo = new BranchInfo();
				linfo = ldao.findByID(pi.getReceiveBankID());
				info.setReceiveAccountName(DataFormat.formatString(linfo.getEnterpriseName()));
				info.setReceiveAccountNo(DataFormat.formatString(linfo.getBankAccountCode()));
				info.setReceiveBankName(DataFormat.formatString(linfo.getBranchName()));
			}
			else if (pi.getReceiveExtClientName() != null && pi.getReceiveExtClientName().trim().length() > 0)
			{
				strReceiveAccountName = pi.getReceiveExtClientName();
				info.setReceiveAccountName(strReceiveAccountName);
				strReceiveAccountNo = pi.getReceiveExtAccountNo();
				info.setReceiveAccountNo(strReceiveAccountNo);
				info.setReceiveBankName(pi.getReceiveExtRemitInBank());
			}
			else
			// 总账
			{
				// modify by wjliu --2007-5-30 当收款方为借方时
				if (NameRef.getBalanceDirection(pi.getReceiveGL(),pi.getOfficeID(),pi.getCurrencyID()) == (SETTConstant.DebitOrCredit.DEBIT))
				{
					strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
					info.setPayAccountName(strPayAccountName);
					strPayAccountNo = NameRef.getGLTypeNoByID(pi.getPayGL());
					info.setPayAccountNo(strPayAccountNo);
				}
				else
				{
					strPayAccountName = NameRef.getGLTypeDescByID(pi.getReceiveGL());
					info.setReceiveAccountName(strPayAccountName);
					strPayAccountNo = NameRef.getGLTypeNoByID(pi.getReceiveGL());
					info.setReceiveAccountNo(strPayAccountNo);
				}
			}
			String strAmount = DataFormat.formatAmount(pi.getAmount());
			info.setAmount(strAmount);
			String strChineseAmount = ChineseCurrency.showChinese(strAmount, pi.getCurrencyID());
			info.setChineseAmount(strChineseAmount);
			strAmount = getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ DataFormat.formatDisabledAmount(pi.getAmount());
			info.setAmount(strAmount);
			String strInputUser = NameRef.getUserNameByID(pi.getInputUserID());
			info.setInputUserName(strInputUser);
			String strCheckUser = NameRef.getUserNameByID(pi.getCheckUserID());
			info.setCheckUserName(strCheckUser);

			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));

			String strAbstract = "起息日：" + DataFormat.formatDate(pi.getInterestStartDate()) + "；" + pi.getAbstract();
			info.setAbstract(strAbstract);

			IPrintTemplate.showCredit(info, out);

		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
		}
	}

	/**
	 * 显示特种转账借方传票
	 * 
	 * @throws Exception edited by gqzhang
	 */
	public static void PrintShowDebtor(PrintInfo pi, JspWriter out) throws Exception
	{

		try
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
			String strTransNo = pi.getTransNo();
			info.setTransNo(strTransNo);
			String strPayBankName = "";
			info.setPayBankName(strPayBankName);
			String strReceiveBankName = "";
			info.setReceiveBankName(strReceiveBankName);
			String strPayAccountName = "";
			String strPayAccountNo = "";
			// 付款方账户
			if (pi.getPayAccountID() > 0)
			{
				strPayAccountName = NameRef.getAccountNameByID(pi.getPayAccountID());
				info.setPayAccountName(strPayAccountName);
				strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
				info.setPayAccountNo(strPayAccountNo);
				info.setPayBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}

			else if (pi.getPayBankID() > 0)
			{
				Sett_BranchDAO ldao = new Sett_BranchDAO();
				BranchInfo linfo = new BranchInfo();
				linfo = ldao.findByID(pi.getPayBankID());
				info.setPayAccountName(DataFormat.formatString(linfo.getEnterpriseName()));
				info.setPayAccountNo(DataFormat.formatString(linfo.getBankAccountCode()));
				info.setPayBankName(DataFormat.formatString(linfo.getBranchName()));
			}
			else if (pi.getPayExtClientName() != null && pi.getPayExtClientName().trim().length() > 0)
			{
				strPayAccountName = pi.getPayExtClientName();
				info.setPayAccountName(strPayAccountName);
				strPayAccountNo = pi.getPayExtAccountNo();
				info.setPayAccountNo(strPayAccountNo);
				info.setPayBankName(pi.getPayExtRemitInBank());
			}
			else
			// 总账
			{
				 strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
				 info.setPayAccountName(strPayAccountName);
				 strPayAccountNo = NameRef.getGLTypeNoByID(pi.getPayGL());
				 info.setPayAccountNo(strPayAccountNo);

				
				
			}
			// 收款方账户
			String strReceiveAccountName = "";
			String strReceiveAccountNo = "";
			if (pi.getReceiveAccountID() > 0)
			{
				strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveAccountID());
				info.setReceiveAccountName(strReceiveAccountName);
				strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
				info.setReceiveAccountNo(strReceiveAccountNo);
				info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}

			else if (pi.getReceiveBankID() > 0)
			{
				Sett_BranchDAO ldao = new Sett_BranchDAO();
				BranchInfo linfo = new BranchInfo();
				linfo = ldao.findByID(pi.getReceiveBankID());
				info.setReceiveAccountName(DataFormat.formatString(linfo.getEnterpriseName()));
				info.setReceiveAccountNo(DataFormat.formatString(linfo.getBankAccountCode()));
				info.setReceiveBankName(DataFormat.formatString(linfo.getBranchName()));
			}
			else if (pi.getReceiveExtClientName() != null && pi.getReceiveExtClientName().trim().length() > 0)
			{
				strReceiveAccountName = pi.getReceiveExtClientName();
				info.setReceiveAccountName(strReceiveAccountName);
				strReceiveAccountNo = pi.getReceiveExtAccountNo();
				info.setReceiveAccountNo(strReceiveAccountNo);
				info.setReceiveBankName(pi.getReceiveExtRemitInBank());
			}
			else
			// 总账
			{

//				 modify by wjliu --2007-5-30 当收款方为借方时
				if (NameRef.getBalanceDirection(pi.getReceiveGL(),pi.getOfficeID(),pi.getCurrencyID()) == SETTConstant.DebitOrCredit.DEBIT)
				{
					strReceiveAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
					info.setReceiveAccountName(strReceiveAccountName);
					strReceiveAccountNo = NameRef.getGLTypeNoByID(pi.getPayGL());
					info.setReceiveAccountNo(strReceiveAccountNo);
				}
				else
				{
					strReceiveAccountName = NameRef.getGLTypeDescByID(pi.getReceiveGL());
					info.setPayAccountName(strReceiveAccountName);
					strReceiveAccountNo = NameRef.getGLTypeNoByID(pi.getReceiveGL());
					info.setPayAccountNo(strReceiveAccountNo);
				}

			}
			String strAmount = DataFormat.formatAmount(pi.getAmount());
			info.setAmount(strAmount);
			String strChineseAmount = ChineseCurrency.showChinese(strAmount, pi.getCurrencyID());
			info.setChineseAmount(strChineseAmount);
			strAmount = getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ DataFormat.formatDisabledAmount(pi.getAmount());
			info.setAmount(strAmount);
			String strInputUser = NameRef.getUserNameByID(pi.getInputUserID());
			info.setInputUserName(strInputUser);
			String strCheckUser = NameRef.getUserNameByID(pi.getCheckUserID());
			info.setCheckUserName(strCheckUser);

			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));

			String strAbstract = "起息日：" + DataFormat.formatDate(pi.getInterestStartDate()) + "；" + pi.getAbstract();
			info.setAbstract(strAbstract);

			IPrintTemplate.showDebtor(info, out);

		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
		}
	}

	/**
	 * 显示套打 共用程序
	 * 
	 * @throws Exception
	 */
	public static void PrintTemplate(long lPrintTemplateID, int lPrintTemplateTypeID, PrintInfo pi, JspWriter out)
			throws Exception
	{

		System.out.println("qlan---test-----");
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
				strPayAccountName = DataFormat.formatString(NameRef.getAccountNameByID(pi.getPayAccountID()));
				strPayAccountNo = DataFormat.formatString(NameRef.getBankAccountCodeByID(pi.getPayAccountID(), pi
						.getPayBankID()));
				strPayBankName = DataFormat.formatString(IPrintTemplate.getBankAccountPrintNameByID(pi.getPayBankID()));
				strPayProvince = DataFormat.formatString(IPrintTemplate.getBankAccountProvinceByID(pi.getPayBankID()));
				strPayCity = DataFormat.formatString(IPrintTemplate.getBankAccountCityByID(pi.getPayBankID()));
			}
			else
			{
				strPayAccountName = DataFormat.formatString(pi.getPayExtClientName());
				strPayAccountNo = DataFormat.formatString(pi.getPayExtAccountNo());
				strPayBankName = DataFormat.formatString(pi.getPayExtRemitInBank());
				strPayProvince = DataFormat.formatString(pi.getPayExtRemitInProvince());
				strPayCity = DataFormat.formatString(pi.getPayExtRemitInCity());
			}
			if (pi.getReceiveAccountID() > 0)
			{
				strReceiveAccountName = DataFormat.formatString(NameRef.getAccountNameByID(pi.getReceiveAccountID()));
				strReceiveAccountNo = DataFormat.formatString(NameRef.getBankAccountCodeByID(pi.getReceiveAccountID(),
						pi.getReceiveBankID()));
				strReceiveBankName = DataFormat.formatString(IPrintTemplate.getBankAccountPrintNameByID(pi
						.getReceiveBankID()));
				strReceiveProvince = DataFormat.formatString(IPrintTemplate.getBankAccountProvinceByID(pi
						.getReceiveBankID()));
				strReceiveCity = DataFormat.formatString(IPrintTemplate.getBankAccountCityByID(pi.getReceiveBankID()));
			}
			else
			{
				strReceiveAccountName = DataFormat.formatString(pi.getReceiveExtClientName());
				strReceiveAccountNo = DataFormat.formatString(pi.getReceiveExtAccountNo());
				strReceiveBankName = DataFormat.formatString(pi.getReceiveExtRemitInBank());
				strReceiveProvince = DataFormat.formatString(pi.getReceiveExtRemitInProvince());
				strReceiveCity = DataFormat.formatString(pi.getReceiveExtRemitInCity());
			}
			String strAmount = DataFormat.formatAmount(pi.getAmount());
			String strChineseAmount = ChineseCurrency.showChinese(strAmount, pi.getCurrencyID());
			strAmount = DataFormat.formatDisabledAmount(pi.getAmount());
			String strInputUser = NameRef.getUserNameByID(pi.getInputUserID());
			String strCheckUser = NameRef.getUserNameByID(pi.getCheckUserID());
			String strAbstract = pi.getAbstract();
			// for 转贴现台账 start
			String strBillType = LOANConstant.DraftType.getName(pi.getBillTypeID());
			String strDiscountBillNo = DataFormat.formatString(pi.getDiscountBillNo());
			String strEndDate = DataFormat.formatDate(pi.getEndDate());
			if (strEndDate.length() < 10)
				strEndDate = "0000000000";
			String strEndYear = strEndDate.substring(0, 4);
			String strEndMonth = strEndDate.substring(5, 7);
			String strEndDay = strEndDate.substring(8, 10);
			String strBillAcceptanceUser = DataFormat.formatString(pi.getBillAcceptanceUser());
			String strAcceptanceUserAccount = DataFormat.formatString(pi.getAcceptanceUserAccount());
			String strAcceptanceUserBank = DataFormat.formatString(pi.getAcceptanceUserBank());
			;
			String strDiscountBillAmount = DataFormat.formatDisabledAmount(pi.getDiscountBillAmount());
			String strChineseDiscountBillAmount = ChineseCurrency.showChinese(String
					.valueOf(pi.getDiscountBillAmount()));
			String strRate = DataFormat.formatRate(pi.getRate());
			String strDiscountInterestAmount = DataFormat.formatDisabledAmount(pi.getDiscountInterestAmount());
			String strDiscountAmount = DataFormat.formatDisabledAmount(pi.getDiscountAmount());
			String strApplicantName = DataFormat.formatString(pi.getApplicantName());
			String strApplicantAccountNo = DataFormat.formatString(pi.getApplicantAccountNo());
			String strApplicantAccountBankNo = DataFormat.formatString(pi.getApplicantAccountBankNo());
			System.out.println("取得pi参数完毕");
			// for 转贴现台账 end
			switch (lPrintTemplateTypeID)
			{
				case 1 : // 银行进账单
					String[] strCode1 = {"01", "02", "03", "04", "05", "08", "09", "10", "13", "14", "15", "16", "17",
							"19", "0026", "0029", "0027", "0031"};
					IPrintTemplate.showTemplate(out, strCode1, lPrintTemplateID, strYear, strMonth, strDay,
							strPayAccountName, strPayAccountNo, strPayBankName, strReceiveAccountName,
							strReceiveAccountNo, strReceiveBankName, strChineseAmount, strAmount, "", "", "", "",
							strCheckUser, strInputUser, "", "", "", "", "", "", "", "", "", "", "");
					break;
				case 2 : // 电汇信汇
					String[] strCode2 = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13",
							"14", "15", "18", "0026", "0029", "0027", "0031"};
					IPrintTemplate.showTemplate(out, strCode2, lPrintTemplateID, strYear, strMonth, strDay,
							strPayAccountName, strPayAccountNo, strPayProvince, strPayCity, strPayBankName,
							strReceiveAccountName, strReceiveAccountNo, strReceiveProvince, strReceiveCity,
							strReceiveBankName, strChineseAmount, strAmount, "", "", strCheckUser, strInputUser, "",
							"", "", "", "", "", "", "", "");
					break;
				case 3 : // 银行汇票委托书
					String[] strCode3 = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "14", "15", "18",
							"19", "34", "0026", "0027", "0028", "0033"};
					IPrintTemplate.showTemplate(out, strCode3, lPrintTemplateID, strYear, strMonth, strDay,
							strPayAccountName, strPayAccountNo, strPayProvince, strPayCity, strPayBankName,
							strReceiveAccountName, strReceiveAccountNo, strChineseAmount, strAmount, strAbstract, "",
							"", strCheckUser, strInputUser, "", "", "", "", "", "", "", "", "", "", "");
					break;
				case 4 : // 转贴现台账
					String[] strCode4 = {"01", "02", "03", "16", "52", "09", "36", "37", "38", "10", "39", "40", "41",
							"13", "04", "05", "08", "14", "22", "43", "42"};
					System.out.println("进入方法4中");
					IPrintTemplate.showTemplate(out, strCode4, lPrintTemplateID, strYear, strMonth, strDay,
							strBillType, strDiscountBillNo, strApplicantName, strYear, strMonth, strDay,
							strApplicantAccountNo, strEndYear, strEndMonth, strEndDay, strApplicantAccountBankNo,
							strBillAcceptanceUser, strAcceptanceUserAccount, strAcceptanceUserBank,
							strChineseDiscountBillAmount, strRate, strDiscountInterestAmount, strDiscountAmount, "",
							"", "", "", "", "", "");
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

	/**
	 * 自营贷款收回凭证打印
	 * 
	 * @throws Exception
	 */
	public static void PrintTrustRepaymentLoan(PrintInfo pi, JspWriter out) throws Exception
	{

		try
		{
			ShowRepaymentLoanInfo info = new ShowRepaymentLoanInfo();
			String strExecuteDate = DataFormat.formatDate(pi.getExecuteDate());
			if (strExecuteDate.length() < 10)
			{
				strExecuteDate = "0000000000";
			}
			info.setYear(strExecuteDate.substring(0, 4));
			info.setMonth(strExecuteDate.substring(5, 7));
			info.setDay(strExecuteDate.substring(8, 10));
			info.setAbstract(pi.getAbstract() + "已收正常利息：" + getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ pi.getRealInterest() + "；已收复利：" + getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ pi.getRealCompoundInterest() + "；已收罚息：" + getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ pi.getRealOverDueInterest());
			info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ DataFormat.formatDisabledAmount(pi.getAmount()));
			info.setBillCode(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			// info.setChargeRate(DataFormat.formatDisabledAmount(pi.getCommission()));
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			info.setChineseAmount(ChineseCurrency.showChinese(DataFormat.formatAmount(pi.getAmount()), pi
					.getCurrencyID()));
			info.setContractCode(NameRef.getContractNoByID(pi.getContractID()));
			// 合同利率
			LoanPayFormDetailInfo lpfdinfo = new LoanPayFormDetailInfo();
			lpfdinfo.setLoadNoteID(pi.getLoanNoteID());
			Sett_TransGrantLoanDAO stdao = new Sett_TransGrantLoanDAO();
			lpfdinfo = stdao.findPayFormDetailByCondition(lpfdinfo);
			info.setContractRate(DataFormat.formatDisabledAmount(lpfdinfo.getInterestRate()));
			// 委托单位
			info.setConsignUnit(lpfdinfo.getClientName());
			// 余额
			if (pi.getCurrentBalance() > 0)
			{
				info.setBalance(getCurrencySymbolByCurrencyID(pi.getCurrencyID())
						+ DataFormat.formatDisabledAmount(pi.getCurrentBalance()));
			}
			else
			{
				info.setBalance(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + "0.00");
			}
			// 累计还款
			if ((lpfdinfo.getAmount() - pi.getCurrentBalance()) > 0)
			{
				info.setTotalRepayAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID())
						+ DataFormat.formatDisabledAmount(lpfdinfo.getAmount() - pi.getCurrentBalance()));
			}
			else
			{
				info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + "0.00");
			}
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
			info.setDateStart(DataFormat.getChineseDateString(pi.getStartDate()));
			info.setDateEnd(DataFormat.getChineseDateString(pi.getEndDate()));
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			info.setLoanInterest(DataFormat.formatDisabledAmount(pi.getInterest()));
			info.setLoanInterval(DataFormat.getChineseDateString(lpfdinfo.getStart()) + " 至 "
					+ DataFormat.getChineseDateString(lpfdinfo.getEnd()));
			info.setLoanRate(DataFormat.formatDisabledAmount(pi.getRate()));
			info.setLoanType(LOANConstant.LoanType.getName(lpfdinfo.getLoanType()));
			info.setNote(pi.getAbstract());
			info.setOverDueInterest(DataFormat.formatDisabledAmount(pi.getRealOverDueInterest()));
			// info.setOverDueRate();
			info.setRepaymentUnitName(NameRef.getClientNameByID(pi.getPayClientID()));
			info.setTotalInterest(DataFormat.formatDisabledAmount(pi.getRealInterest() + pi.getRealCompoundInterest()
					+ pi.getRealOverDueInterest() + pi.getRealSuretyFee()));
			if (pi.getPayBankID() > 0)
			{
				info.setRepaymentBankName(NameRef.getBankNameByID(pi.getPayBankID()));
				info.setRepaymentAccountNo(IPrintTemplate.getBankAccountCodeByID(pi.getPayBankID()));
			}
			else
			{
				info.setRepaymentBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
				info.setRepaymentAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
			}
			info.setTransNo(pi.getTransNo());
			IPrintTemplate.showTrustRepaymentLoan1(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showTrustRepaymentLoan2(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showTrustRepaymentLoan3(info, out);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
		}
	}

	/**
	 * 委托贷款收回凭证打印
	 * 
	 * @throws Exception
	 */
	public static void PrintConsignRepaymentLoan(PrintInfo pi, JspWriter out) throws Exception
	{

		try
		{
			ShowRepaymentLoanInfo info = new ShowRepaymentLoanInfo();
			String strExecuteDate = DataFormat.formatDate(pi.getExecuteDate());
			if (strExecuteDate.length() < 10)
			{
				strExecuteDate = "0000000000";
			}
			info.setYear(strExecuteDate.substring(0, 4));
			info.setMonth(strExecuteDate.substring(5, 7));
			info.setDay(strExecuteDate.substring(8, 10));
			info.setAbstract(pi.getAbstract() + "已收正常利息：" + getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ pi.getRealInterest() + "；已收复利：" + getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ pi.getRealCompoundInterest() + "；已收罚息：" + getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ pi.getRealOverDueInterest());
			info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ DataFormat.formatDisabledAmount(pi.getAmount()));
			info.setBillCode(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			info.setChargeRate(DataFormat.formatDisabledAmount(pi.getCommission()));
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			info.setChineseAmount(ChineseCurrency.showChinese(DataFormat.formatAmount(pi.getAmount()), pi
					.getCurrencyID()));
			info.setContractCode(NameRef.getContractNoByID(pi.getContractID()));
			// 合同利率
			LoanPayFormDetailInfo lpfdinfo = new LoanPayFormDetailInfo();
			lpfdinfo.setLoadNoteID(pi.getLoanNoteID());
			Sett_TransGrantLoanDAO stdao = new Sett_TransGrantLoanDAO();
			lpfdinfo = stdao.findPayFormDetailByCondition(lpfdinfo);
			info.setContractRate(DataFormat.formatDisabledAmount(lpfdinfo.getInterestRate()));
			// 委托单位
			info.setConsignUnit(lpfdinfo.getClientName());
			// 余额
			if (pi.getCurrentBalance() > 0)
			{
				info.setBalance(getCurrencySymbolByCurrencyID(pi.getCurrencyID())
						+ DataFormat.formatDisabledAmount(pi.getCurrentBalance()));
			}
			else
			{
				info.setBalance(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + "0.00");
			}
			// 累计还款
			if ((lpfdinfo.getAmount() - pi.getCurrentBalance()) > 0)
			{
				info.setTotalRepayAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID())
						+ DataFormat.formatDisabledAmount(lpfdinfo.getAmount() - pi.getCurrentBalance()));
			}
			else
			{
				info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + "0.00");
			}
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
			info.setDateStart(DataFormat.getChineseDateString(pi.getStartDate()));
			info.setDateEnd(DataFormat.getChineseDateString(pi.getEndDate()));
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			info.setLoanInterest(DataFormat.formatDisabledAmount(pi.getInterest()));
			info.setLoanInterval(DataFormat.getChineseDateString(lpfdinfo.getStart()) + " 至 "
					+ DataFormat.getChineseDateString(lpfdinfo.getEnd()));
			info.setLoanRate(DataFormat.formatDisabledAmount(pi.getRate()));
			info.setLoanType(LOANConstant.LoanType.getName(lpfdinfo.getLoanType()));
			info.setNote(pi.getAbstract());
			info.setOverDueInterest(DataFormat.formatDisabledAmount(pi.getOverDueInterest()));
			// info.setOverDueRate();
			// 付款账号
			// 付款银行名称
			if (pi.getPayBankID() > 0)
			{
				info.setRepaymentBankName(NameRef.getBankNameByID(pi.getPayBankID()));
				info.setRepaymentAccountNo(IPrintTemplate.getBankAccountCodeByID(pi.getPayBankID()));
			}
			else
			{
				info.setRepaymentBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
				info.setRepaymentAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
			}
			info.setRepaymentUnitName(NameRef.getClientNameByID(pi.getPayClientID()));
			info.setTotalInterest(DataFormat.formatDisabledAmount(pi.getRealInterest() + pi.getRealCompoundInterest()
					+ pi.getRealOverDueInterest() + pi.getRealCommission()));
			info.setTransNo(pi.getTransNo());
			IPrintTemplate.showConsignRepaymentLoan1(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showConsignRepaymentLoan2(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showConsignRepaymentLoan3(info, out);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
		}
	}

	/**
	 * 自营贷款发放凭证打印
	 * 
	 * @throws Exception
	 */
	public static void PrintTrustGrantLoan(PrintInfo pi, JspWriter out) throws Exception
	{

		try
		{
			ShowGrantLoanInfo info = new ShowGrantLoanInfo();
			String strExecuteDate = DataFormat.formatDate(pi.getExecuteDate());
			if (strExecuteDate.length() < 10)
			{
				strExecuteDate = "0000000000";
			}
			info.setYear(strExecuteDate.substring(0, 4));
			info.setMonth(strExecuteDate.substring(5, 7));
			info.setDay(strExecuteDate.substring(8, 10));
			info.setAbstract(pi.getAbstract());
			info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
			info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ DataFormat.formatDisabledAmount(pi.getAmount()));
			info.setBillCode(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			info.setChargeRate(DataFormat.formatDisabledAmount(pi.getRealCommission()));
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			info.setChineseAmount(ChineseCurrency.showChinese(DataFormat.formatAmount(pi.getAmount()), pi
					.getCurrencyID()));
			info.setContractCode(NameRef.getContractNoByID(pi.getContractID()));
			// 合同利率
			LoanPayFormDetailInfo lpfdinfo = new LoanPayFormDetailInfo();
			lpfdinfo.setLoadNoteID(pi.getLoanNoteID());
			Sett_TransGrantLoanDAO stdao = new Sett_TransGrantLoanDAO();
			lpfdinfo = stdao.findPayFormDetailByCondition(lpfdinfo);
			// 委托单位
			info.setConsignUnit(lpfdinfo.getClientName());
			info.setContractRate(DataFormat.formatDisabledAmount(lpfdinfo.getInterestRate()));
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			info.setLoanInterval(DataFormat.getChineseDateString(lpfdinfo.getStart()) + " 至 "
					+ DataFormat.getChineseDateString(lpfdinfo.getEnd()));
			info.setLoanType(LOANConstant.LoanType.getName(lpfdinfo.getLoanType()));
			info.setLoanUnit(NameRef.getClientNameByID(pi.getReceiveClientID()));
			info.setNote(pi.getAbstract());
			// 借款单位，即自营贷款账户
			info.setLoanUnit(NameRef.getAccountNameByID(pi.getLoanAccountID()));
			if (pi.getReceiveAccountID() > 0)
			{
				// 收款人账号
				info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
				// 收款人开户银行名称
				info.setOpenBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			else if (pi.getReceiveBankID() > 0)
			{
				// 收款人账号
				info.setAccountNo(pi.getExtAccountNo());
				// 收款人开户银行名称
				info.setOpenBankName(pi.getExtRemitInBank());
			}
			info.setTransNo(pi.getTransNo());
			IPrintTemplate.showTrustGrantLoan1(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showTrustGrantLoan2(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showTrustGrantLoan3(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showTrustGrantLoan4(info, out);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
		}
	}

	/**
	 * 委托贷款发放凭证打印
	 * 
	 * @throws Exception
	 */
	public static void PrintConsignGrantLoan(PrintInfo pi, JspWriter out) throws Exception
	{

		try
		{
			ShowGrantLoanInfo info = new ShowGrantLoanInfo();
			String strExecuteDate = DataFormat.formatDate(pi.getExecuteDate());
			if (strExecuteDate.length() < 10)
			{
				strExecuteDate = "0000000000";
			}
			info.setYear(strExecuteDate.substring(0, 4));
			info.setMonth(strExecuteDate.substring(5, 7));
			info.setDay(strExecuteDate.substring(8, 10));
			info.setAbstract(pi.getAbstract());
			info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
			info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ DataFormat.formatDisabledAmount(pi.getAmount()));
			info.setBillCode(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			info.setChineseAmount(ChineseCurrency.showChinese(DataFormat.formatAmount(pi.getAmount()), pi
					.getCurrencyID()));
			info.setContractCode(NameRef.getContractNoByID(pi.getContractID()));
			// 合同利率
			LoanPayFormDetailInfo lpfdinfo = new LoanPayFormDetailInfo();
			lpfdinfo.setLoadNoteID(pi.getLoanNoteID());
			Sett_TransGrantLoanDAO stdao = new Sett_TransGrantLoanDAO();
			lpfdinfo = stdao.findPayFormDetailByCondition(lpfdinfo);
			// 委托单位
			info.setConsignUnit(lpfdinfo.getClientName());
			info.setContractRate(DataFormat.formatDisabledAmount(lpfdinfo.getInterestRate()));
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			info.setLoanInterval(DataFormat.getChineseDateString(lpfdinfo.getStart()) + " 至 "
					+ DataFormat.getChineseDateString(lpfdinfo.getEnd()));
			info.setLoanType(LOANConstant.LoanType.getName(lpfdinfo.getLoanType()));
			info.setLoanUnit(NameRef.getClientNameByID(pi.getReceiveClientID()));
			info.setNote(pi.getAbstract());
			// 借款单位，即自营贷款账户
			info.setLoanUnit(NameRef.getAccountNameByID(pi.getLoanAccountID()));
			if (pi.getReceiveAccountID() > 0)
			{
				// 收款人账号
				info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
				// 收款人开户银行名称
				info.setOpenBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			else if (pi.getReceiveBankID() > 0)
			{
				// 收款人账号
				info.setAccountNo(pi.getExtAccountNo());
				// 收款人开户银行名称
				info.setOpenBankName(pi.getExtRemitInBank());
			}
			// 手续费率
			info.setChargeRate(DataFormat.formatDisabledAmount(lpfdinfo.getPoundage()));
			info.setTransNo(pi.getTransNo());
			IPrintTemplate.showConsignGrantLoan1(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showConsignGrantLoan2(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showConsignGrantLoan3(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showConsignGrantLoan4(info, out);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
		}
	}

	/**
	 * 贴现发放凭证打印
	 * 
	 * @throws Exception
	 */
	public static void PrintGrantDiscount(PrintInfo pi, JspWriter out) throws Exception
	{

		try
		{
			ShowGrantDiscountInfo info = new ShowGrantDiscountInfo();
			String strExecuteDate = DataFormat.formatDate(pi.getExecuteDate());
			if (strExecuteDate.length() < 10)
			{
				strExecuteDate = "0000000000";
			}
			info.setYear(strExecuteDate.substring(0, 4));
			info.setMonth(strExecuteDate.substring(5, 7));
			info.setDay(strExecuteDate.substring(8, 10));
			// 凭证的findbyid方法
			DiscountCredenceInfo dcinfo = new DiscountCredenceInfo();
			TransDiscountDelegation transDiscountDelegation = new TransDiscountDelegation();
			dcinfo = transDiscountDelegation.findDiscountCredenceByID(pi.getDiscountNoteID());
			// /System.out.print("dcinfo:Id"+dcinfo.getID());
			info.setAbstract(pi.getAbstract() + "；贴现合同号：" + dcinfo.getDiscountContractCode() + "；贴现凭证编号："
					+ dcinfo.getCode());
			info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ DataFormat.formatDisabledAmount(pi.getDiscountAmount()));
			// 持票人信息
			if (pi.getReceiveAccountID() > 0)
			{
				// 持票人名称
				info.setBillKeeperName(NameRef.getAccountNameByID(pi.getReceiveAccountID()));
				// 持票人账号
				info.setBillKeeperAccount(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
				// 持票人开户银行名称
				info.setBillKeeperBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			else if (pi.getReceiveBankID() > 0)
			{
				// 持票人名称
				info.setBillKeeperName(NameRef.getBankNameByID(pi.getReceiveBankID()));
				// 持票人账号
				info.setBillKeeperAccount(pi.getExtAccountNo());
				// 持票人开户银行名称
				info.setBillKeeperBankName(pi.getExtRemitInBank());
			}
			// 特别说明:汇票出票人信息暂时不需要
			// 汇票出票人账号
			info.setBillOutAccount("&nbsp;"); // (dcinfo.getAcceptAccount());//汇票出票开户行
			info.setBillOutBankName("&nbsp;"); // (dcinfo.getAcceptBank());
			// 汇票出票人名称
			info.setBillOutName("&nbsp;"); // dcinfo.getAcceptClientName());
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			info.setChineseAmount(ChineseCurrency.showChinese(DataFormat.formatAmount(pi.getDiscountAmount()), pi
					.getCurrencyID()));
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
			// 汇票金额
			info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ DataFormat.formatDisabledAmount(pi.getDiscountBillAmount()));
			// 汇票大写金额
			info.setChineseAmount(ChineseCurrency.showChinese(DataFormat.formatAmount(pi.getDiscountBillAmount()), pi
					.getCurrencyID()));
			// 到期日
			info.setDateBillEnd(DataFormat.getChineseDateString(dcinfo.getAtTerm()));
			// 出票日
			info.setDateBillOut(DataFormat.getChineseDateString(dcinfo.getPublicDate()));
			System.out.println("出票日:" + dcinfo.getPublicDate());
			// 贴现利息
			info.setDiscountInterest(getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ DataFormat.formatDisabledAmount(pi.getDiscountInterestAmount()));
			// 贴现号码
			info.setDiscountNo(dcinfo.getCode());
			// 贴现率
			info.setDiscountRate(DataFormat.formatDisabledAmount(pi.getDiscountAmount() / pi.getDiscountBillAmount()));
			// 贴现种类
			info.setDiscountType(LOANConstant.DraftType.getName(dcinfo.getDraftTypeID()));
			System.out.println("贴现种类ID:" + dcinfo.getDraftTypeID());
			System.out.println("贴现种类名称:" + LOANConstant.DraftType.getName(dcinfo.getDraftTypeID()));
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			// 实付贴现金额
			info.setRealPayDiscountAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ DataFormat.formatDisabledAmount(pi.getDiscountAmount()));
			IPrintTemplate.showDiscountGrantLoan1(info, out);
			// out.println("<br clear=all style='page-break-before:always'>");
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
		}
	}

	/**
	 * 贴现收回凭证打印
	 * 
	 * @throws Exception
	 */
	public static void PrintRepaymentDiscount(PrintInfo pi, JspWriter out) throws Exception
	{

		try
		{
			ShowRepaymentDiscountInfo info = new ShowRepaymentDiscountInfo();
			String strExecuteDate = DataFormat.formatDate(pi.getExecuteDate());
			if (strExecuteDate.length() < 10)
			{
				strExecuteDate = "0000000000";
			}
			info.setYear(strExecuteDate.substring(0, 4));
			info.setMonth(strExecuteDate.substring(5, 7));
			info.setDay(strExecuteDate.substring(8, 10));
			if (pi.getDiscountAmount() > 0)
			{
				info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID())
						+ DataFormat.formatDisabledAmount(pi.getDiscountAmount()));
			}
			else
			{
				info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + "0.00");
			}
			if (pi.getCurrentBalance() > 0)
			{
				info.setBalance(getCurrencySymbolByCurrencyID(pi.getCurrencyID())
						+ DataFormat.formatDisabledAmount(pi.getCurrentBalance()));
			}
			else
			{
				info.setBalance(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + "0.00");
			}
			info.setBillNo(NameRef.getDiscountBillNoByID(pi.getDiscountBillID()));
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			info.setChineseAmount(ChineseCurrency.showChinese(DataFormat.formatAmount(pi.getDiscountAmount()), pi
					.getCurrencyID()));
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
			info.setDateBankAccount(DataFormat.getChineseDateString(pi.getInterestStartDate()));
			info.setDateDiscount(DataFormat.getChineseDateString(pi.getExecuteDate()));
			// 凭证的findbyid方法
			DiscountCredenceInfo dcinfo = new DiscountCredenceInfo();
			TransDiscountDelegation transDiscountDelegation = new TransDiscountDelegation();
			dcinfo = transDiscountDelegation.findDiscountCredenceByID(pi.getDiscountNoteID());
			info.setAbstract(pi.getAbstract() + "；贴现合同号：" + dcinfo.getDiscountContractCode() + "；贴现凭证编号："
					+ dcinfo.getCode());
			info.setBillType(LOANConstant.DraftType.getName(dcinfo.getDraftTypeID()));
			info.setDateEnd(DataFormat.getChineseDateString(dcinfo.getAtTerm()));
			info.setDiscountAccount(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
			info.setDiscountName(NameRef.getAccountNameByID(pi.getReceiveAccountID()));
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			info.setTotalRepaymentAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ DataFormat.formatDisabledAmount(dcinfo.getExamineAmount() - pi.getCurrentBalance()));
			IPrintTemplate.showDiscountRepaymentLoan1(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showDiscountRepaymentLoan2(info, out);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
		}
	}

	/**
	 * 利息通知单(支取)打印
	 * 
	 * @throws Exception
	 */
	public static void PrintInterestNotice(PrintInfo pi, JspWriter out) throws Exception
	{

		try
		{
			ShowPayInterestInfo info = new ShowPayInterestInfo();
			String strExecuteDate = DataFormat.formatDate(pi.getExecuteDate());
			if (strExecuteDate.length() < 10)
			{
				strExecuteDate = "0000000000";
			}
			info.setYear(strExecuteDate.substring(0, 4));
			info.setMonth(strExecuteDate.substring(5, 7));
			info.setDay(strExecuteDate.substring(8, 10));
			// 借款单位名称
			if (pi.getBorrowClientID() > 0)
			{
				info.setClientName(NameRef.getClientNameByID(pi.getBorrowClientID()));
			}
			// 委托单位名称
			ContractDao contractDao = new ContractDao();
			ContractInfo contractInfo = null;
			contractInfo = contractDao.findByID(pi.getContractID());
			info.setConsignClientName(NameRef.getClientNameByID(contractInfo.getClientID()));
			// 账号类型
			info.setAccountType(SETTConstant.AccountType.getName(pi.getAccountTypeID()));
			// 交易编号
			info.setTransNo(pi.getTransNo());
			// 账号
			info.setAccountNo(NameRef.getAccountNoByID(pi.getConsignAccountID()));
			// 正常利息
			// 加入分段利息
			SubsectionInterest dao = new SubsectionInterest();
			SubsectionDateInfo PrintSubsectionInfo = new SubsectionDateInfo();
			PrintSubsectionInfo = dao.getSubsectionInterest(pi.getConsignAccountID(), -1, pi.getLoanNoteID(), pi
					.getLatestInterestClearDate(), DataFormat.getPreviousDate(pi.getInterestClearDate()));
			info.setNormalInterestDateStart(PrintSubsectionInfo.getPrintStartDate()); // 正常利息开始日期
			info.setNormalInterestDateEnd(PrintSubsectionInfo.getPrintEndDate());
			info.setNormalInterestAmount(PrintSubsectionInfo.getPrintBalance()); // 正常利息本金
			info.setNormalInterestRate(PrintSubsectionInfo.getPrintInterestRate()); // 正常利息率
			info.setNormalInterest(PrintSubsectionInfo.getPrintInterest()); // 正常利息
			info.setNormalInterestDay(PrintSubsectionInfo.getPrintDays());
			// 加入分段利息End
			/*
			 * LoanPayFormDetailInfo lpfdinfo = new LoanPayFormDetailInfo(); lpfdinfo.setLoadNoteID(pi.getLoanNoteID());
			 * Sett_TransGrantLoanDAO stdao = new Sett_TransGrantLoanDAO(); lpfdinfo =
			 * stdao.findPayFormDetailByCondition(lpfdinfo); //正常利息起息日期
			 * info.setNormalInterestDateStart(DataFormat.formatDate(pi.getLatestInterestClearDate())); //正常利息终息日期
			 * info.setNormalInterestDateEnd(DataFormat.formatDate(DataFormat.getPreviousDate(pi.getInterestClearDate())));
			 * //正常利息天数 if (pi.getLatestInterestClearDate() != null && pi.getInterestClearDate() != null) {
			 * info.setNormalInterestDay((getIntervalDays(pi.getLatestInterestClearDate(), pi.getInterestClearDate(),
			 * 1)) + ""); } //正常利息本金 info.setNormalInterestAmount(DataFormat.formatDisabledAmount(pi.getLoanBalance()));
			 * if (pi.getAmount() == 0.0) { info.setNormalInterestAmount("0.00"); } //正常利息利率
			 * info.setNormalInterestRate(DataFormat.formatRate(lpfdinfo.getInterestRate() + "")); //正常利息
			 * info.setNormalInterest(DataFormat.formatDisabledAmount(pi.getRealInterest())); if (pi.getRealInterest() ==
			 * 0.0) { info.setNormalInterest("0.0"); }
			 */
			// 复利
			if (pi.getRealCompoundInterest() > 0)
			{
				// 复利利息起息日期
				if (pi.getCompoundInterestStart() != null)
				{
					info.setCompoundInterestDateStart(DataFormat.formatDate(pi.getCompoundInterestStart()));
				}
				// 复利利息终息日期
				info.setCompoundInterestDateEnd(DataFormat.formatDate(DataFormat.getPreviousDate(pi
						.getInterestClearDate())));
				// 复利利息天数
				if (pi.getCompoundInterestStart() != null)
				{
					info.setCompoundInterestDay((getIntervalDays(pi.getCompoundInterestStart(), pi
							.getInterestClearDate(), 1))
							+ "");
				}
				info.setCompoundInterestAmount("&nbsp;");
				// 复利利率
				info.setCompoundInterestRate(DataFormat.formatRate(pi.getCompoundRate() + ""));
				// 复利利息
				info.setCompoundInterest(DataFormat.formatDisabledAmount(pi.getRealCompoundInterest()));
				if (pi.getRealCompoundInterest() == 0.0)
				{
					info.setCompoundInterest("0.0");
				}
			}
			// 逾期罚息
			if (pi.getRealOverDueInterest() > 0)
			{
				// 逾期罚息利息起息日期
				if (pi.getOverDueStart() != null)
				{
					info.setOverInterestDateStart(DataFormat.formatDate(pi.getOverDueStart()));
				}
				// 逾期罚息利息终息日期
				info.setOverInterestDateEnd(DataFormat
						.formatDate(DataFormat.getPreviousDate(pi.getInterestClearDate())));
				// 逾期罚息利息天数
				if (pi.getOverDueStart() != null)
				{
					info.setOverInterestDay((getIntervalDays(pi.getOverDueStart(), pi.getInterestClearDate(), 1)) + "");
				}
				// 逾期罚息本金
				info.setOverInterestAmount(DataFormat.formatDisabledAmount(pi.getOverDueAmount()));
				if (pi.getOverDueAmount() == 0.00)
				{
					info.setOverInterestAmount("0.00");
				}
				// 逾期罚息利率
				info.setOverInterestRate(DataFormat.formatRate(pi.getOverDueRate() + ""));
				// 逾期罚息利息
				info.setOverInterest(DataFormat.formatDisabledAmount(pi.getRealOverDueInterest()));
				if (pi.getRealOverDueInterest() == 0.0)
				{
					info.setOverInterest("0.0");
				}
			}
			// 手续费
			if (pi.getRealCommission() > 0)
			{
				// 手续费利息起息日期
				info.setCommissionFeeDateStart(DataFormat.formatDate(pi.getCommissionStart()));
				// 手续费利息终息日期
				info.setCommissionFeeDateEnd(DataFormat.formatDate(DataFormat
						.getPreviousDate(pi.getInterestClearDate())));
				// 手续费利息天数
				info.setCommissionFeeDay((getIntervalDays(pi.getCommissionStart(), pi.getInterestClearDate(), 1)) + "");
				// 手续费利息本金
				info.setCommissionFeeAmount(DataFormat.formatDisabledAmount(pi.getLoanBalance()));
				if (pi.getAmount() == 0.00)
				{
					info.setCommissionFeeAmount("0.00");
				}
				// 手续费利率
				info.setCommissionFeeRate(DataFormat.formatRate(pi.getCommissionRate() + ""));
				// 手续费利息
				info.setCommissionFee(DataFormat.formatDisabledAmount(pi.getRealCommission()));
				if (pi.getRealCommission() == 0.0)
				{
					info.setCommissionFee("0.0");
				}
			}
			if (pi.getRealSuretyFee() > 0)
			{
				// 担保费
				// 担保费利息起息日期
				info.setAssureFeeDateStart(DataFormat.formatDate(pi.getSuretyFeeStart()));
				// 担保费利息终息日期
				info.setAssureFeeDateEnd(DataFormat.formatDate(DataFormat.getPreviousDate(pi.getInterestClearDate())));
				// 担保费利息天数
				info.setAssureFeeDay((getIntervalDays(pi.getSuretyFeeStart(), pi.getInterestClearDate(), 1)) + "");
				// 担保费利息本金
				info.setAssureFeeAmount(DataFormat.formatDisabledAmount(pi.getAmount()));
				if (pi.getAmount() == 0.0)
				{
					info.setAssureFee("0.00");
				}
				// 担保费利率
				info.setAssureFeeRate(DataFormat.formatRate(pi.getSuretyFeeRate() + ""));
				// 担保费利息
				info.setAssureFee(DataFormat.formatDisabledAmount(pi.getRealSuretyFee()));
				if (pi.getRealSuretyFee() == 0.0)
				{
					info.setAssureFee("0.0");
				}
			}
			// 利息总额
			info.setTotalInterest(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getRealInterest())
					+ DataFormat.formatDouble(pi.getRealCompoundInterest())
					+ DataFormat.formatDouble(pi.getRealOverDueInterest())));
			info.setTotalInterestChinese(ChineseCurrency.showChinese(DataFormat.formatAmount(DataFormat.formatDouble(pi
					.getRealInterest())
					+ DataFormat.formatDouble(pi.getRealCompoundInterest())
					+ DataFormat.formatDouble(pi.getRealOverDueInterest())), pi.getCurrencyID()));
			if (DataFormat.formatDouble(pi.getRealInterest()) + DataFormat.formatDouble(pi.getRealCompoundInterest())
					+ DataFormat.formatDouble(pi.getRealOverDueInterest()) == 0.0)
			{
				info.setTotalInterest("0.00");
			}
			// 利息账户号
			info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getPayInterestAccountID()));
			// 对应的账户号
			info.setCurrentAccountNo(NameRef.getAccountNoByID(pi.getConsignAccountID()));
			// 对应的合同号
			info.setContractNo(NameRef.getContractNoByID(pi.getContractID()));
			// 对应借据号
			info.setLoanBillNo(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			// 对应存单号
			info.setDepositBillNo(pi.getFixedDepositNo());
			// 转账日
			info.setTransAccountDate(DataFormat.formatDate(pi.getExecuteDate()));
			// 录入人
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			if (pi.getInputUserID() < 0)
			{
				info.setInputUserName("机制");
			}
			// 复核人
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			if (pi.getCheckUserID() < 0)
			{
				if (pi.getStatusID() == SETTConstant.TransactionStatus.CHECK)
				{
					info.setCheckUserName("机制");
				}
				else
				{
					info.setCheckUserName("&nbsp;&nbsp;&nbsp;&nbsp");
				}
			}
			System.out.println("贷款类型:" + pi.getTransTypeID());
			IPrintTemplate.showTrustPayInterestNoticeZJ(info, out);
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
		}
	}

	/**
	 * 通知存款开户证实书打印
	 * 
	 * @throws Exception
	 */
	public static void PrintNoticeOpen(PrintInfo pi, JspWriter out) throws Exception
	{

		try
		{
			ShowNoticeOpenInfo info = new ShowNoticeOpenInfo();
			info.setAccountName(NameRef.getAccountNameByID(pi.getReceiveAccountID()));
			info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
			info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ DataFormat.formatDisabledAmount(pi.getAmount()));
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			info.setChineseAmount(ChineseCurrency.showChinese(DataFormat.formatAmount(pi.getAmount()), pi
					.getCurrencyID()));
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
			info.setDateOpenAccount(DataFormat.getChineseDateString(pi.getExecuteDate()));
			info.setDepositBillNo(pi.getFixedDepositNo());
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			info.setStartInterestDate(DataFormat.getChineseDateString(pi.getInterestStartDate()));
			String sInsert = "";
			if (pi.getNoticeDay() > 10000)
			{
				sInsert = pi.getNoticeDay() - 10000 + "天";
			}
			else
			{
				sInsert = pi.getNoticeDay() + "个月";
			}
			info.setType(sInsert);
			IPrintTemplate.showOpenAccountNotice1(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showOpenAccountNotice2(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showOpenAccountNotice3(info, out);

		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
		}
	}

	/**
	 * 定期存款开户证实书打印
	 * 
	 * @throws Exception
	 */
	public static void PrintFixedDepositOpen(PrintInfo pi, JspWriter out) throws Exception
	{

		try
		{
			ShowFixedDepositOpenInfo info = new ShowFixedDepositOpenInfo();
			info.setAccountName(NameRef.getAccountNameByID(pi.getReceiveAccountID()));
			info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
			info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ DataFormat.formatDisabledAmount(pi.getAmount()));
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			info.setChineseAmount(ChineseCurrency.showChinese(DataFormat.formatAmount(pi.getAmount()), pi
					.getCurrencyID()));
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
			info.setDateOpenAccount(DataFormat.getChineseDateString(pi.getInterestStartDate()));
			info.setDepositBillNo(pi.getFixedDepositNo());
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			info.setStartInterestDate(DataFormat.getChineseDateString(pi.getInterestStartDate()));
			String sInterval = "";
			if (pi.getFixedDepositTerm() > 10000)
			{
				sInterval = pi.getFixedDepositTerm() - 10000 + "天";
			}
			else
			{
				sInterval = pi.getFixedDepositTerm() + "个月";
			}
			info.setInterval(sInterval);
			info.setRate(DataFormat.formatDisabledAmount(pi.getRate()));

			IPrintTemplate.showFixOpenAccountNotice1(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showFixOpenAccountNotice2(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showFixOpenAccountNotice3(info, out);

		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
		}
	}

	/**
	 * 存款利息计付通知单(用于通知存款 定期存款的收回凭证)
	 * 
	 * @throws Exception
	 */
	public static void PrintDepositInterest(PrintInfo pi, JspWriter out) throws Exception
	{

		try
		{
			ShowDepositInterestInfo info = new ShowDepositInterestInfo();
			String strExecuteDate = DataFormat.formatDate(pi.getExecuteDate());
			if (strExecuteDate.length() < 10)
			{
				strExecuteDate = "0000000000";
			}
			info.setYear(strExecuteDate.substring(0, 4));
			info.setMonth(strExecuteDate.substring(5, 7));
			info.setDay(strExecuteDate.substring(8, 10));
			SubAccountAssemblerInfo sainfo = new SubAccountAssemblerInfo();
			AccountOperation account = new AccountOperation();
			sainfo = account.findSubAccountByID(pi.getSubAccountID());
			sainfo.getSubAccountFixedInfo().setEndDate(pi.getInterestStartDate()); // InterestStartDate就是通知存款的EndDate
			info.setAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
			info.setClientName(NameRef.getClientNameByID(pi.getReceiveAccountID()));
			info.setDepositBillNo(pi.getBillNo());
			String strTemp = "";
			if (pi.getCurrentInterest() != 0.0) // 有活期利息
			{
				if (pi.getInterestStartDate().before(pi.getEndDate())) // 如起息日在终止日期之前，即提前支取
				{
					// 本金1，活期利息，活期利息利率，开始日期，结束日期，天数
					info.setAmount1(DataFormat.formatAmount(pi.getAmount()));
					info.setInterest1(DataFormat.formatAmount(pi.getCurrentInterest()));
					info.setRate1(DataFormat.formatAmount(0.72));
					strTemp = DataFormat.formatDate(pi.getStartDate());
					info.setBeginYear1(strTemp.substring(0, 4));
					info.setBeginMonth1(strTemp.substring(5, 7));
					info.setBeginDay1(strTemp.substring(8, 10));
					strTemp = DataFormat.formatDate(pi.getEndDate());
					info.setOverYear1(strTemp.substring(0, 4));
					info.setOverMonth1(strTemp.substring(5, 7));
					info.setOverDay1(strTemp.substring(8, 10));
					info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), 1) + 1) + "");
					// 本金2，其它利息(可能有)，其它利息利率(无)，开始日期，结束日期，天数
					if (pi.getOtherInterest() != 0.0)
					{
						info.setAmount2(DataFormat.formatAmount(pi.getOtherInterest()));
						info.setInterest2(DataFormat.formatAmount(pi.getCurrentInterest()));
						strTemp = DataFormat.formatDate(pi.getStartDate());
						info.setBeginYear2(strTemp.substring(0, 4));
						info.setBeginMonth2(strTemp.substring(5, 7));
						info.setBeginDay2(strTemp.substring(8, 10));
						strTemp = DataFormat.formatDate(pi.getEndDate());
						info.setOverYear2(strTemp.substring(0, 4));
						info.setOverMonth2(strTemp.substring(5, 7));
						info.setOverDay2(strTemp.substring(8, 10));
						info.setDayNumber2((getIntervalDays(pi.getStartDate(), pi.getEndDate(), 1) + 1) + "");
					}
				}
				else
				// 如起息日在终止日期之后
				{
					// 本金1，利息支出，利息支出利率，开始日期，结束日期，天数
					info.setAmount1(DataFormat.formatAmount(pi.getAmount()));
					info.setInterest1(DataFormat.formatAmount(pi.getPayableInterest()));
					info.setRate1(DataFormat.formatAmount(pi.getRate()));
					strTemp = DataFormat.formatDate(pi.getStartDate());
					info.setBeginYear1(strTemp.substring(0, 4));
					info.setBeginMonth1(strTemp.substring(5, 7));
					info.setBeginDay1(strTemp.substring(8, 10));
					strTemp = DataFormat.formatDate(pi.getEndDate());
					info.setOverYear1(strTemp.substring(0, 4));
					info.setOverMonth1(strTemp.substring(5, 7));
					info.setOverDay1(strTemp.substring(8, 10));
					info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), 1) + 1) + "");
					// 本金2，活期利息，活期利息利率，开始日期，结束日期，天数
					info.setAmount2(DataFormat.formatAmount(pi.getAmount()));
					info.setInterest2(DataFormat.formatAmount(pi.getCurrentInterest()));
					info.setRate2(DataFormat.formatAmount(0.72));
					strTemp = DataFormat.formatDate(pi.getEndDate());
					info.setBeginYear2(strTemp.substring(0, 4));
					info.setBeginMonth2(strTemp.substring(5, 7));
					info.setBeginDay2(strTemp.substring(8, 10));
					strTemp = DataFormat.formatDate(pi.getInterestStartDate());
					info.setOverYear2(strTemp.substring(0, 4));
					info.setOverMonth2(strTemp.substring(5, 7));
					info.setOverDay2(strTemp.substring(8, 10));
					info.setDayNumber2((getIntervalDays(pi.getEndDate(), pi.getInterestStartDate(), 1) + 1) + "");
					if (pi.getOtherInterest() != 0.0) // 其它利息
					{
						// 本金3，其它利息(可能有)，其它利息利率(无)，开始日期，结束日期，天数
						info.setAmount3(DataFormat.formatAmount(pi.getAmount()));
						info.setInterest3(DataFormat.formatAmount(pi.getOtherInterest()));
						strTemp = DataFormat.formatDate(pi.getEndDate());
						info.setBeginYear3(strTemp.substring(0, 4));
						info.setBeginMonth3(strTemp.substring(5, 7));
						info.setBeginDay3(strTemp.substring(8, 10));
						strTemp = DataFormat.formatDate(pi.getInterestStartDate());
						info.setOverYear3(strTemp.substring(0, 4));
						info.setOverMonth3(strTemp.substring(5, 7));
						info.setOverDay3(strTemp.substring(8, 10));
						info.setDayNumber3((getIntervalDays(pi.getEndDate(), pi.getInterestStartDate(), 1) + 1) + "");
					}
				}
			}
			else
			// 没有活期利息
			{
				// 本金1，利息支出，利息支出利率，开始日期，结束日期，天数
				info.setAmount1(DataFormat.formatAmount(pi.getAmount()));
				info.setInterest1(DataFormat.formatAmount(pi.getPayableInterest()));
				info.setRate1(DataFormat.formatAmount(pi.getRate()));
				strTemp = DataFormat.formatDate(pi.getStartDate());
				// System.out.println("起始日期:"+pi.getStartDate());
				info.setBeginYear1(strTemp.substring(0, 4));
				info.setBeginMonth1(strTemp.substring(5, 7));
				info.setBeginDay1(strTemp.substring(8, 10));
				strTemp = DataFormat.formatDate(pi.getEndDate());
				info.setOverYear1(strTemp.substring(0, 4));
				info.setOverMonth1(strTemp.substring(5, 7));
				info.setOverDay1(strTemp.substring(8, 10));
				info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), 1) + 1) + "");
			}
			// 利息合计
			info.setTotalInterest(DataFormat.formatAmount(DataFormat
					.formatDouble(DataFormat.formatDouble(pi.getPayableInterest())
							+ DataFormat.formatDouble(pi.getCurrentInterest())
							+ DataFormat.formatDouble(pi.getOtherInterest()))));
			info.setTotalInterestChinese(ChineseCurrency.showChinese(info.getTotalInterest(), pi.getCurrencyID()));
			// 利息账号
			info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getReceiveInterestAccountID()));
			// 利率
			info.setRate1(DataFormat.formatDisabledAmount(pi.getRate()));
			// 存款种类
			info.setDepositTypeName(SETTConstant.TransactionType.getName(pi.getTransTypeID()));
			if (info.getDepositTypeName().indexOf("定期") >= 0)
			{
				info.setDepositTypeName("定期存款");
			}
			else
			{
				info.setDepositTypeName("通知存款");
			}
			info.setDepositBillNo(pi.getFixedDepositNo());
			info.setTransNo(pi.getTransNo());
			info.setNotes(pi.getAbstract());

			IPrintTemplate.showDepositInterest1(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showDepositInterest2(info, out);

		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
		}
	}

	// 银行进账单
	// 01 交易年
	// 02 交易月
	// 03 交易日
	// 04 付款人全称
	// 05 付款人账号
	// 08 付款人开户银行
	// 09 收款人全称
	// 10 收款人账号
	// 13 收款人开户银行
	// 14 大写金额
	// 15 小写金额
	// 16 票据种类
	// 17 票据张数
	// 19 备注
	// 0026 主管
	// 0029 会计
	// 0027 复核
	// 0031 记账
	// 电汇信汇
	// 01 交易年
	// 02 交易月
	// 03 交易日
	// 04 汇款人全称
	// 05 汇款人账号
	// 06 汇出省市
	// 07 汇出市县
	// 08 汇出行名称
	// 09 收款人全称
	// 10 收款人账号
	// 11 汇入省市
	// 12 汇入市县
	// 13 汇入行名称
	// 14 大写金额
	// 15 小写金额
	// 18 汇款用途
	// 0026 主管
	// 0029 会计
	// 0027 复核
	// 0031 记账
	//
	// 01 交易年
	// 02 交易月
	// 03 交易日
	// 04 汇款人全称
	// 05 汇款人账号
	// 06 兑付省市
	// 07 兑付市县
	// 08 兑付行名称
	// 09 收款人全称
	// 10 收款人账号
	// 14 大写金额
	// 15 小写金额
	// 18 汇款用途
	// 19 备注
	// 34 对方科目
	// 0026 主管
	// 0027 复核
	// 0028 经办
	// 0033 科目
}