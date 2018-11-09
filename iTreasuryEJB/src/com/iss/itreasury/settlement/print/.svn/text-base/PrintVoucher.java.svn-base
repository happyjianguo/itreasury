/*
 * Created on 2003-10-30
 * 
 * To change the template for this generated file go to Window - Preferences - Java - Code Generation - Code and
 * Comments
 */
package com.iss.itreasury.settlement.print;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.discount.dataentity.DiscountCredenceInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.project.gzbfcl.settlement.dataentity.FoundsdispatchInfo;
import com.iss.itreasury.project.gzbfcl.settlement.dataentity.QueryFoundsdispatchDetailInfo;
import com.iss.itreasury.settlement.account.dao.Sett_SubAccountDAO;
import com.iss.itreasury.settlement.account.dataentity.SubAccountAssemblerInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountFixedInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountLoanInfo;
import com.iss.itreasury.settlement.bizdelegation.TransDiscountDelegation;
import com.iss.itreasury.settlement.print.dataentity.TemplateSettingXmlInfo;
import com.iss.itreasury.settlement.print.templateinfo.BankShowCcbTransVoucherInfo;
import com.iss.itreasury.settlement.print.templateinfo.PrintInfoVo;
import com.iss.itreasury.settlement.print.templateinfo.PrintOptionInfo;
import com.iss.itreasury.settlement.print.templateinfo.ShowConsignLoanInterestAdviceNote;
import com.iss.itreasury.settlement.print.templateinfo.ShowDepositInterestInfo;
import com.iss.itreasury.settlement.print.templateinfo.ShowFixedDepositOpenInfo;
import com.iss.itreasury.settlement.print.templateinfo.ShowGrantDiscountInfo;
import com.iss.itreasury.settlement.print.templateinfo.ShowGrantLoanInfo;
import com.iss.itreasury.settlement.print.templateinfo.ShowInInfo;
import com.iss.itreasury.settlement.print.templateinfo.ShowLoanAtTermAdviceNotice;
import com.iss.itreasury.settlement.print.templateinfo.ShowNoticeOpenInfo;
import com.iss.itreasury.settlement.print.templateinfo.ShowOverLoanNoticeInfo;
import com.iss.itreasury.settlement.print.templateinfo.ShowPayInterestInfo;
import com.iss.itreasury.settlement.print.templateinfo.ShowRepairVoucher;
import com.iss.itreasury.settlement.print.templateinfo.ShowRepaymentDiscountInfo;
import com.iss.itreasury.settlement.print.templateinfo.ShowRepaymentLoanInfo;
import com.iss.itreasury.settlement.print.templateinfo.ShowSpecialTransInfo;
import com.iss.itreasury.settlement.print.templateinfo.ShowTrustLoanInterestInfo;
import com.iss.itreasury.settlement.print.templateinfo.ShowWithDrawInfo;
import com.iss.itreasury.settlement.print.templateinfo.SubsectionDateInfo;
import com.iss.itreasury.settlement.setting.bizlogic.PrintTemplateBiz;
import com.iss.itreasury.settlement.setting.dao.Sett_BranchDAO;
import com.iss.itreasury.settlement.setting.dao.Sett_TransactionCostDAO;
import com.iss.itreasury.settlement.setting.dataentity.BranchInfo;
import com.iss.itreasury.settlement.setting.dataentity.TransactionFeeTypeInfo;
import com.iss.itreasury.settlement.transcommission.dataentity.TransCommissionResultInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransOnePayMultiReceiveInfo;
import com.iss.itreasury.settlement.transfee.dao.Sett_TransFeeDAO;
import com.iss.itreasury.settlement.transfee.dataentity.TransFeeInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dao.Sett_TransOpenFixedDepositDAO;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedOpenInfo;
import com.iss.itreasury.settlement.transloan.bizlogic.BankLoanQuery;
import com.iss.itreasury.settlement.transloan.dao.Sett_TransGrantLoanDAO;
import com.iss.itreasury.settlement.transloan.dataentity.LoanPayFormDetailInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.ChineseCurrency;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
/**
 * @author rxie
 * 
 * To change the template for this generated type comment go to Window - Preferences - Java - Code Generation - Code
 * and Comments
 */ 
public class PrintVoucher
{
	Log4j logger = null;
	
	
	public PrintVoucher()
	{
		super();
		logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);
		
	}
	
	
	
	/**
     * 由币种ID取得币种标志
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
	 * @param sDate     Date of Start 
	 * @param eDate     Date of End
	 * @param intervalDaysFlag  flag for caculating interval: 
	 *                          1: caculating as fact days. 
	 *                          2: caculating as 30 days per month
	 * @exception IException   throw it while business exception occur and transaction need rollback
	 * @return  Interval Days
	*/
	public static long getIntervalDays(Timestamp sDate, Timestamp eDate, long intervalDaysFlag) throws Exception
	{
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
				//跨越的年份数
				int intervalYears = 0;
				//跨越的月份数
				int intervalMonths = 0;
				//多余的天数
				int intervalDays = 0;
				//起息日的天
				int sDay = 0;
				//止息日的天
				int eDay = 0;

				if (sCalendar.get(Calendar.DAY_OF_MONTH) != eCalendar.get(Calendar.DAY_OF_MONTH))
				{
					sDay = sCalendar.get(Calendar.DAY_OF_MONTH);
					eDay = eCalendar.get(Calendar.DAY_OF_MONTH);
					
					if (sCalendar.get(Calendar.DAY_OF_MONTH) > 30)
					{
						sDay = 30; 
					}

					if (eCalendar.get(Calendar.MONTH) == Calendar.FEBRUARY && (eCalendar.get(Calendar.DAY_OF_MONTH) == 28 || eCalendar.get(Calendar.DAY_OF_MONTH) == 29))
					{
						if (sCalendar.get(Calendar.DAY_OF_MONTH) == 29 && eCalendar.get(Calendar.DAY_OF_MONTH) == 28)
						{
							eDay = 29;
						}
						else
						{
							eDay = 30;
						}
					}
					
					//多余的天数
					intervalDays = eDay - sDay;
				}
				
				//跨越的年份数
				intervalYears = eCalendar.get(Calendar.YEAR) - sCalendar.get(Calendar.YEAR);
				
				//跨越的月份数
				intervalMonths = eCalendar.get(Calendar.MONTH) - sCalendar.get(Calendar.MONTH);
				
				//按照每月30天计算的间隔总天数
				resIntervalDays = intervalYears * 360 + intervalMonths * 30 + intervalDays;

				//Boxu Add 这里的计算会有一个问题,如果时间不足一个月且跨月刚好是31的时候天数会少一天
				if(resIntervalDays < 30
				&& !(eCalendar.get(Calendar.MONTH) == Calendar.FEBRUARY && (eCalendar.get(Calendar.DAY_OF_MONTH) == 28 || eCalendar.get(Calendar.DAY_OF_MONTH) == 29)))
				{
					//按实际天数获得天数
					resIntervalDays = getFactIntervalDays(sCalendar, eCalendar);
				}
			}
		}
		return resIntervalDays;
	}
	/**
	 * @param info     ShowWithDrawInfo
	 * @exception IException   throw it while business exception occur and transaction need rollback
	 * @return  ShowWithDrawInfo(with the ob info)
	*/
	public static ShowWithDrawInfo getOBInfoByTransNo(ShowWithDrawInfo info) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		try
		{
			if (info.getTransNo() != null && info.getTransNo().length() > 0)
			{
				conn = Database.getConnection();
				strSQL =
					"select cfmUser.sname confirmUserName,checkUser.sname checkUserName,signUser.sname signUserName "
						+ " from ob_FinanceInStr fin,OB_USER cfmUser,OB_USER checkUser,OB_USER signUser "
						+ " where fin.nconfirmuserid=cfmUser.id(+) and fin.nCheckUserID=checkUser.id(+) and fin.nsignuserid=signuser.id(+) "
						+ " and fin.NSTATUS = "
						+ OBConstant.SettInstrStatus.FINISH
						+ " and fin.CPF_STRANSNO ='"
						+ info.getTransNo()+"'";
				ps = conn.prepareStatement(strSQL.toString());
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					if (rs.getString("confirmUserName") != null)
						info.setOBInputUserName(rs.getString("confirmUserName"));
					if (rs.getString("checkUserName") != null)
						info.setOBCheckUserName(rs.getString("checkUserName"));
					if (rs.getString("signUserName") != null)
						info.setOBSignUserName(rs.getString("signUserName"));
				}
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
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
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
		return info;
	}
	/**
	 * 开始计算实际间隔日期
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
			//Adds the specified (signed) amount of time to the given time field, based on the calendar's rules.
			gc1.add(Calendar.DATE, 1);
			elapsed++;
		}
		return elapsed;
	}
	/**
	 *	进账单打印 包括进账单1和进账单2
	 * @throws Exception
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
			//付款方账户
			if (pi.getPayAccountID() > 0)
			{
				strPayAccountName = NameRef.getAccountNameByID(pi.getPayAccountID());
				info.setPayAccountName(strPayAccountName.trim());
				//strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
				//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
                {
                    strPayAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getPayAccountID());
                }
                else
                {
                    strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
                }
				
				info.setPayAccountNo(strPayAccountNo);
				info.setPayBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			else
				if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
				{
					strPayAccountName = pi.getExtClientName();
					info.setPayAccountName(strPayAccountName.trim());
					strPayAccountNo = pi.getExtAccountNo();
					info.setPayAccountNo(strPayAccountNo);
					info.setPayBankName(pi.getExtRemitInBank());
				}
				else //总账
					{
					strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
					info.setPayAccountName(strPayAccountName.trim());
					strPayAccountNo = NameRef.getGLTypeNoByID(pi.getPayGL());
					info.setPayAccountNo(strPayAccountNo);
				}
			//收款方账户
			String strReceiveAccountName = "";
			String strReceiveAccountNo = "";
			if (pi.getReceiveAccountID() > 0)
			{
				strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveAccountID());
				info.setReceiveAccountName(strReceiveAccountName);
				//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
                {
                	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID());
                }
                else
                {
                	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
                }
				info.setReceiveAccountNo(strReceiveAccountNo);
				info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			else
				if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
				{
					strReceiveAccountName = pi.getExtClientName();
					info.setReceiveAccountName(strReceiveAccountName);
					strReceiveAccountNo = pi.getExtAccountNo();
					info.setReceiveAccountNo(strReceiveAccountNo);
					info.setReceiveBankName(pi.getExtRemitInBank());
				}
				else //总账
					{
					strReceiveAccountName = NameRef.getGLTypeDescByID(pi.getReceiveGL());
					info.setReceiveAccountName(strReceiveAccountName);
					strReceiveAccountNo = NameRef.getGLTypeNoByID(pi.getReceiveGL());
					info.setReceiveAccountNo(strReceiveAccountNo);
				}
			//取得金额
			String strAmount = DataFormat.formatAmount(pi.getAmount());
			info.setAmount(strAmount);
			if (pi.getAmount() == 0.0)
			{
				info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + "0.00");
			}
			String strChineseAmount = ChineseCurrency.showChinese(strAmount, pi.getCurrencyID());
			info.setChineseAmount(strChineseAmount);
			strAmount = getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getAmount());
			info.setAmount(strAmount);
			//取得用户名称
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
     * 进账单打印 
     * 只打印第一联 
     * @throws Exception
     */
    public static void PrintShowIn_1(PrintInfo pi, JspWriter out) throws Exception
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
            //付款方账户
            if (pi.getPayAccountID() > 0)
            {
                strPayAccountName = NameRef.getAccountNameByID(pi.getPayAccountID());
                info.setPayAccountName(strPayAccountName.trim());
                //strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
				//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
                {
                    strPayAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getPayAccountID());
                }
                else
                {
                    strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
                }
                info.setPayAccountNo(strPayAccountNo);
                info.setPayBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
            }
            else
                if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
                {
                    strPayAccountName = pi.getExtClientName();
                    info.setPayAccountName(strPayAccountName.trim());
                    strPayAccountNo = pi.getExtAccountNo();
                    info.setPayAccountNo(strPayAccountNo);
                    info.setPayBankName(pi.getExtRemitInBank());
                }
                else //总账
                    {
                    strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
                    info.setPayAccountName(strPayAccountName.trim());
                    strPayAccountNo = NameRef.getGLTypeNoByID(pi.getPayGL());
                    info.setPayAccountNo(strPayAccountNo);
                }
            //收款方账户
            String strReceiveAccountName = "";
            String strReceiveAccountNo = "";
            if (pi.getReceiveAccountID() > 0)
            {
                strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveAccountID());
                info.setReceiveAccountName(strReceiveAccountName);
                //strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
				//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
                {
                	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID());
                }
                else
                {
                	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
                }
                info.setReceiveAccountNo(strReceiveAccountNo);
                info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
            }
            else
                if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
                {
                    strReceiveAccountName = pi.getExtClientName();
                    info.setReceiveAccountName(strReceiveAccountName);
                    strReceiveAccountNo = pi.getExtAccountNo();
                    info.setReceiveAccountNo(strReceiveAccountNo);
                    info.setReceiveBankName(pi.getExtRemitInBank());
                }
                else //总账
                    {
                    strReceiveAccountName = NameRef.getGLTypeDescByID(pi.getReceiveGL());
                    info.setReceiveAccountName(strReceiveAccountName);
                    strReceiveAccountNo = NameRef.getGLTypeNoByID(pi.getReceiveGL());
                    info.setReceiveAccountNo(strReceiveAccountNo);
                }
            //取得金额
            String strAmount = DataFormat.formatAmount(pi.getAmount());
            info.setAmount(strAmount);
            if (pi.getAmount() == 0.0)
            {
                info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + "0.00");
            }
            String strChineseAmount = ChineseCurrency.showChinese(strAmount, pi.getCurrencyID());
            info.setChineseAmount(strChineseAmount);
            strAmount = getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getAmount());
            info.setAmount(strAmount);
            //取得用户名称
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
	 *	存款支取凭证打印 包括存款支取凭证1和存款支取凭证2
	 * @throws Exception
	 */
	public static void PrintShowWithDraw(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			String strAmount = "";
			double dAmount = 0.0;
			ShowWithDrawInfo info = new ShowWithDrawInfo();
			String strExecuteDate = DataFormat.formatDate(pi.getExecuteDate());
			if (strExecuteDate.length() < 10)
				strExecuteDate = "0000000000";
			String strTemp = "";
			String strYear = strExecuteDate.substring(0, 4);
			info.setYear(strYear);
			String strMonth = strExecuteDate.substring(5, 7);
			info.setMonth(strMonth);
			String strDay = strExecuteDate.substring(8, 10);
			info.setDay(strDay);
			info.setTransNo(pi.getTransNo());
			String strPayAccountName = "";
			String strPayAccountNo = "";
			//自营贷款收回，委托贷款收回存款支取凭证中金额和付款方账户号
			//如果本金、利息、手续费（担保费）从借款客户的活期账户中扣除，才能累计入存款支取凭证金额
			//如果付本金活期账户号存在，则付款方为付本金活期账户，否则如果付利息活期账户号存在，则付款方为付息活期账户，手续费、担保费依次类推
			if (pi.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE || pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
			{
				System.out.println("=====自营/委托");
				//付款方方账户号
				if (pi.getPayAccountID() > 0)
				{
					System.out.println("=====11111111111111111");
					strPayAccountName = NameRef.getAccountNameByID(pi.getPayAccountID());
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
					//strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
					//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
	                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
	                {
	                    strPayAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getPayAccountID());
	                }
	                else
	                {
	                    strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
	                }
					//增加放款通知单号
					if (pi.getLoanNoteID() > 0)
					{
						strPayAccountNo = strPayAccountNo + "；" + NameRef.getPayFormNoByID(pi.getLoanNoteID());
					}
					//增加合同号
					if (pi.getContractID() != -1)
					{
						strPayAccountNo = strPayAccountNo + "；" + NameRef.getContractNoByID(pi.getContractID());
					}
					info.setPayAccountNo(DataFormat.formatString(strPayAccountNo));
					info.setPayBankName(DataFormat.formatString(NameRef.getOfficeNameByID(pi.getOfficeID())));
					//本金由活期账户支付
					dAmount = DataFormat.formatDouble(pi.getAmount());
					System.out.println("=====dAmount1：" + dAmount);
					//存在付息活期账户号
					if (pi.getPayInterestAccountID() > 0)
					{
						dAmount =
							DataFormat.formatDouble(
								dAmount
									+ DataFormat.formatDouble(pi.getRealInterest())
									+ DataFormat.formatDouble(pi.getRealCompoundInterest())
									+ DataFormat.formatDouble(pi.getRealOverDueInterest()));
					}
					if (pi.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE)
					{
						//自营贷款收回，存在付担保费活期账户号
						if (pi.getPaySuretyFeeAccountID() > 0)
						{
							dAmount = DataFormat.formatDouble(dAmount + DataFormat.formatDouble(pi.getRealSuretyFee()));
							System.out.println("=====dAmount2：" + dAmount);
						}
					}
					else
						if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
						{
							//委托贷款收回，存在付手续费活期账户号
							if (pi.getPayCommissionAccountID() > 0)
							{
								dAmount = DataFormat.formatDouble(dAmount + DataFormat.formatDouble(pi.getRealCommission()));
								System.out.println("=====dAmount3：" + dAmount);
							}
						}
				}
				else
					if (pi.getPayInterestAccountID() > 0)
					{
						System.out.println("=====222222222222222");
						strPayAccountName = NameRef.getAccountNameByID(pi.getPayInterestAccountID());
						info.setPayAccountName(DataFormat.formatString(strPayAccountName));
						//strPayAccountNo = NameRef.getAccountNoByID(pi.getPayInterestAccountID());
						//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
		                {
		                    strPayAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getPayInterestAccountID());
		                }
		                else
		                {
		                    strPayAccountNo = NameRef.getAccountNoByID(pi.getPayInterestAccountID());
		                }
						//增加放款通知单号
						if (pi.getLoanNoteID() > 0)
						{
							strPayAccountNo = strPayAccountNo + "；" + NameRef.getPayFormNoByID(pi.getLoanNoteID());
						}
						//增加合同号
						if (pi.getContractID() != -1)
						{
							strPayAccountNo = strPayAccountNo + "；" + NameRef.getContractNoByID(pi.getContractID());
						}
						info.setPayAccountNo(DataFormat.formatString(strPayAccountNo));
						info.setPayBankName(DataFormat.formatString(NameRef.getOfficeNameByID(pi.getOfficeID())));
						dAmount =
							DataFormat.formatDouble(
								DataFormat.formatDouble(pi.getRealInterest())
									+ DataFormat.formatDouble(pi.getRealCompoundInterest())
									+ DataFormat.formatDouble(pi.getRealOverDueInterest()));
						System.out.println("=====dAmount4：" + dAmount);
						if (pi.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE)
						{
							//自营贷款收回，存在付担保费活期账户号
							if (pi.getPaySuretyFeeAccountID() > 0)
							{
								dAmount = DataFormat.formatDouble(dAmount + DataFormat.formatDouble(pi.getRealSuretyFee()));
								System.out.println("=====dAmount5：" + dAmount);
							}
						}
						else
							if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
							{
								//委托贷款收回，存在付手续费活期账户号
								if (pi.getPayCommissionAccountID() > 0)
								{
									dAmount = DataFormat.formatDouble(dAmount + DataFormat.formatDouble(pi.getRealCommission()));
									System.out.println("=====dAmount6：" + dAmount);
								}
							}
					}
					else
						if (pi.getPaySuretyFeeAccountID() > 0 || pi.getPayCommissionAccountID() > 0)
						{
							System.out.println("=====33333333333333333333333");
							if (pi.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE)
							{
								strPayAccountName = NameRef.getAccountNameByID(pi.getPaySuretyFeeAccountID());
								info.setPayAccountName(DataFormat.formatString(strPayAccountName));
								//strPayAccountNo = NameRef.getAccountNoByID(pi.getPaySuretyFeeAccountID());
								//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
				                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
				                {
				                    strPayAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getPaySuretyFeeAccountID());
				                }
				                else
				                {
				                    strPayAccountNo = NameRef.getAccountNoByID(pi.getPaySuretyFeeAccountID());
				                }
								//增加放款通知单号
								if (pi.getLoanNoteID() > 0)
								{
									strPayAccountNo = strPayAccountNo + "；" + NameRef.getPayFormNoByID(pi.getLoanNoteID());
								}
								//增加合同号
								if (pi.getContractID() != -1)
								{
									strPayAccountNo = strPayAccountNo + "；" + NameRef.getContractNoByID(pi.getContractID());
								}
								info.setPayAccountNo(DataFormat.formatString(strPayAccountNo));
								info.setPayBankName(DataFormat.formatString(NameRef.getOfficeNameByID(pi.getOfficeID())));
								dAmount = DataFormat.formatDouble(pi.getRealSuretyFee());
							}
							else
								if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
								{
									strPayAccountName = NameRef.getAccountNameByID(pi.getPayCommissionAccountID());
									info.setPayAccountName(DataFormat.formatString(strPayAccountName));
									//strPayAccountNo = NameRef.getAccountNoByID(pi.getPayCommissionAccountID());
									//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
					                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
					                {
					                    strPayAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getPayCommissionAccountID());
					                }
					                else
					                {
					                    strPayAccountNo = NameRef.getAccountNoByID(pi.getPayCommissionAccountID());
					                }
									//增加放款通知单号
									if (pi.getLoanNoteID() > 0)
									{
										strPayAccountNo = strPayAccountNo + "；" + NameRef.getPayFormNoByID(pi.getLoanNoteID());
									}
									//增加合同号
									if (pi.getContractID() != -1)
									{
										strPayAccountNo = strPayAccountNo + "；" + NameRef.getContractNoByID(pi.getContractID());
									}
									info.setPayAccountNo(DataFormat.formatString(strPayAccountNo));
									info.setPayBankName(DataFormat.formatString(NameRef.getOfficeNameByID(pi.getOfficeID())));
									dAmount = DataFormat.formatDouble(pi.getRealCommission());
									System.out.println("=====dAmount8：" + dAmount);
								}
						}
				strAmount = DataFormat.formatAmount(dAmount);
				System.out.println("=====dAmount9：" + dAmount);
				info.setAmount(strAmount);
				String strChineseAmount = ChineseCurrency.showChinese(strAmount, pi.getCurrencyID());
				info.setChineseAmount(strChineseAmount);
				strAmount = getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(dAmount);
				info.setAmount(strAmount);
				//收款方账户
				String strReceiveAccountName = "";
				String strReceiveAccountNo = "";
				if (pi.getReceiveAccountID() > 0)
				{
					strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveAccountID());
					info.setReceiveAccountName(DataFormat.formatString(strReceiveAccountName));
					//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
					//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
	                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
	                {
	                	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID());
	                }
	                else
	                {
	                	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
	                }
					//增加存单号
					if (pi.getFixedDepositNo() != null && pi.getFixedDepositNo().length() > 0)
					{
						strReceiveAccountNo = strReceiveAccountNo + "；" + pi.getFixedDepositNo();
					}
					info.setReceiveAccountNo(DataFormat.formatString(strReceiveAccountNo));
					info.setReceiveBankName(DataFormat.formatString(NameRef.getOfficeNameByID(pi.getOfficeID())));
					//info.setReceiveRemitInAddress(NameRef.getOfficeNameByID(pi.getOfficeID()));
				}
				else
					if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
					{
						strReceiveAccountName = pi.getExtClientName();
						info.setReceiveAccountName(DataFormat.formatString(strReceiveAccountName));
						strReceiveAccountNo = pi.getExtAccountNo();
						//增加存单号
						if (pi.getFixedDepositNo() != null && pi.getFixedDepositNo().length() > 0)
						{
							strReceiveAccountNo = strReceiveAccountNo + "；" + pi.getFixedDepositNo();
						}
						info.setReceiveAccountNo(DataFormat.formatString(strReceiveAccountNo));
						info.setReceiveBankName(DataFormat.formatString(pi.getExtRemitInBank()));
						//收款人汇入地点
						if (pi.getExtRemitInProvince().indexOf("省") < 0)
						{
							strTemp = pi.getExtRemitInProvince() + "省";
						}
						else
						{
							strTemp = pi.getExtRemitInProvince();
						}
						if (pi.getExtRemitInCity().indexOf("市") < 0)
						{
							strTemp += pi.getExtRemitInCity() + "市";
						}
						else
						{
							strTemp += pi.getExtRemitInCity();
						}
						info.setReceiveRemitInAddress(DataFormat.formatString(strTemp));
					}
					else //总账
						{
						strReceiveAccountName = NameRef.getGLTypeDescByID(pi.getReceiveGL());
						info.setReceiveAccountName(DataFormat.formatString(strReceiveAccountName));
						strReceiveAccountNo = DataFormat.formatString(NameRef.getGLTypeNoByID(pi.getReceiveGL()));
						//strReceiveAccountNo = "";
						//增加存单号
						if (pi.getFixedDepositNo() != null && pi.getFixedDepositNo().length() > 0)
						{
							strReceiveAccountNo = strReceiveAccountNo + "；" + pi.getFixedDepositNo();
						}
						info.setReceiveAccountNo(DataFormat.formatString(strReceiveAccountNo));
					}
			}
			else //其他业务
				{
				System.out.println("=====非自营/委托====");
				//付款方账户内部账户
				if (pi.getPayAccountID() > 0)
				{
					strPayAccountName = NameRef.getAccountNameByID(pi.getPayAccountID());
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
					//strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
					//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
	                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
	                {
	                    strPayAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getPayAccountID());
	                }
	                else
	                {
	                    strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
	                }
					//增加放款通知单号
					if (pi.getLoanNoteID() > 0)
					{
						strPayAccountNo = strPayAccountNo + "；" + NameRef.getPayFormNoByID(pi.getLoanNoteID());
					}
					//增加合同号
					if (pi.getContractID() != -1)
					{
						strPayAccountNo = strPayAccountNo + "；" + NameRef.getContractNoByID(pi.getContractID());
					}
					info.setPayAccountNo(DataFormat.formatString(strPayAccountNo));
					info.setPayBankName(DataFormat.formatString(NameRef.getOfficeNameByID(pi.getOfficeID())));
				}
				//外部银行账户
				else if(pi.getPayBankID() > 0)
				{
					Sett_BranchDAO ldao = new Sett_BranchDAO();
					BranchInfo linfo = new BranchInfo();
					linfo = ldao.findByID(pi.getPayBankID());
					info.setPayAccountName(DataFormat.formatString(linfo.getEnterpriseName()));
					info.setPayAccountNo(DataFormat.formatString(linfo.getBankAccountCode()));
					info.setPayBankName(DataFormat.formatString(linfo.getBranchName()));
				}
				//外部账户
				else
					if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
					{
						strPayAccountName = pi.getExtClientName();
						info.setPayAccountName(DataFormat.formatString(strPayAccountName));
						strPayAccountNo = pi.getExtAccountNo();
						//增加放款通知单号
						if (pi.getLoanNoteID() > 0)
						{
							strPayAccountNo = strPayAccountNo + "；" + NameRef.getPayFormNoByID(pi.getLoanNoteID());
						}
						//增加合同号
						if (pi.getContractID() != -1)
						{
							strPayAccountNo = strPayAccountNo + "；" + NameRef.getContractNoByID(pi.getContractID());
						}
						info.setPayAccountNo(DataFormat.formatString(strPayAccountNo));
						info.setPayBankName(DataFormat.formatString(pi.getExtRemitInBank()));
					}
					else //总账
						{
						strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
						info.setPayAccountName(DataFormat.formatString(strPayAccountName));
						strPayAccountNo = NameRef.getGLTypeNoByID(pi.getPayGL());
						//增加放款通知单号
						if (pi.getLoanNoteID() > 0)
						{
							strPayAccountNo = strPayAccountNo + "；" + NameRef.getPayFormNoByID(pi.getLoanNoteID());
						}
						//增加合同号
						if (pi.getContractID() != -1)
						{
							strPayAccountNo = strPayAccountNo + "；" + NameRef.getContractNoByID(pi.getContractID());
						}
						info.setPayAccountNo(DataFormat.formatString(strPayAccountNo));
						if (pi.getTransTypeID() == SETTConstant.TransactionType.SPECIALOPERATION)
						{
							info.setPayAccountNo("");
						}
						else
						{
							info.setPayAccountNo(DataFormat.formatString(strPayAccountNo));
						}
					}
				//收款方账户
				String strReceiveAccountName = "";
				String strReceiveAccountNo = "";
				if (pi.getReceiveAccountID() > 0)
				{
					strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveAccountID());
					info.setReceiveAccountName(DataFormat.formatString(strReceiveAccountName));
					//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
					//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
	                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
	                {
	                	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID());
	                }
	                else
	                {
	                	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
	                }
					//增加存单号
					if (pi.getFixedDepositNo() != null && pi.getFixedDepositNo().length() > 0)
					{
						strReceiveAccountNo = strReceiveAccountNo + "；" + pi.getFixedDepositNo();
					}
					info.setReceiveAccountNo(DataFormat.formatString(strReceiveAccountNo));
					info.setReceiveBankName(DataFormat.formatString(NameRef.getOfficeNameByID(pi.getOfficeID())));
					//info.setReceiveRemitInAddress(NameRef.getOfficeNameByID(pi.getOfficeID()));
				}
				else
					if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
					{
						strReceiveAccountName = pi.getExtClientName();
						info.setReceiveAccountName(DataFormat.formatString(strReceiveAccountName));
						strReceiveAccountNo = pi.getExtAccountNo();
						//增加存单号
						if (pi.getFixedDepositNo() != null && pi.getFixedDepositNo().length() > 0)
						{
							strReceiveAccountNo = strReceiveAccountNo + "；" + pi.getFixedDepositNo();
						}
						info.setReceiveAccountNo(DataFormat.formatString(strReceiveAccountNo));
						info.setReceiveBankName(DataFormat.formatString(pi.getExtRemitInBank()));
						//收款人汇入地点
						if (pi.getExtRemitInProvince().indexOf("省") < 0)
						{
							strTemp = pi.getExtRemitInProvince() + "省";
						}
						else
						{
							strTemp = pi.getExtRemitInProvince();
						}
						if (pi.getExtRemitInCity().indexOf("市") < 0)
						{
							strTemp += pi.getExtRemitInCity() + "市";
						}
						else
						{
							strTemp += pi.getExtRemitInCity();
						}
						info.setReceiveRemitInAddress(DataFormat.formatString(strTemp));
					}
					else //总账
						{
						System.out.println("----------------------came here ---------------------");
						System.out.println("receiveaccountname is " + strReceiveAccountName);
						System.out.println("strReceiveAccountNo is " + strReceiveAccountNo);
						strReceiveAccountName = NameRef.getGLTypeDescByID(pi.getReceiveGL());
						info.setReceiveAccountName(DataFormat.formatString(strReceiveAccountName));
						strReceiveAccountNo = DataFormat.formatString(NameRef.getGLTypeNoByID(pi.getReceiveGL()));
						//增加存单号
						if (pi.getFixedDepositNo() != null && pi.getFixedDepositNo().length() > 0)
						{
							strReceiveAccountNo = strReceiveAccountNo + "；" + pi.getFixedDepositNo();
						}
						if (pi.getTransTypeID() == SETTConstant.TransactionType.SPECIALOPERATION)
						{
							info.setReceiveAccountNo("");
						}
						else
						{
							info.setReceiveAccountNo(DataFormat.formatString(strReceiveAccountNo));
						}
					}
				strAmount = DataFormat.formatAmount(pi.getAmount());
				info.setAmount(strAmount);
				String strChineseAmount = ChineseCurrency.showChinese(strAmount, pi.getCurrencyID());
				info.setChineseAmount(strChineseAmount);
				strAmount = getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getAmount());
				info.setAmount(strAmount);
			}
			String strInputUser = NameRef.getUserNameByID(pi.getInputUserID());
			info.setInputUserName(strInputUser);
			String strCheckUser = NameRef.getUserNameByID(pi.getCheckUserID());
			info.setCheckUserName(strCheckUser);
			String strAbstract = pi.getAbstract();
			info.setAbstract(strAbstract);
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
			info = getOBInfoByTransNo(info);
			
            
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
	 *	显示特种转账贷方传票
	 * @throws Exception
	 */
	public static void PrintShowCredit(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			ShowSpecialTransInfo info = new ShowSpecialTransInfo();
			double dAmount = 0;
			String strAmount = "";
			String strReceiveAccountName = "";
			String strReceiveAccountNo = "";
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
			//付款方账户
			if (pi.getPayAccountID() > 0)
			{
				strPayAccountName = NameRef.getAccountNameByID(pi.getPayAccountID());
				info.setPayAccountName(DataFormat.formatString(strPayAccountName));
				//strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
				//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
                {
                    strPayAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getPayAccountID());
                }
                else
                {
                    strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
                }
				info.setPayAccountNo(strPayAccountNo);
				info.setPayBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			//外部银行账户
			else if(pi.getPayBankID() > 0)
			{
				Sett_BranchDAO ldao = new Sett_BranchDAO();
				BranchInfo linfo = new BranchInfo();
				linfo = ldao.findByID(pi.getPayBankID());
				info.setPayAccountName(DataFormat.formatString(linfo.getEnterpriseName()));
				info.setPayAccountNo(DataFormat.formatString(linfo.getBankAccountCode()));
				info.setPayBankName(DataFormat.formatString(linfo.getBranchName()));
			}
			else
				if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
				{
					strPayAccountName = pi.getExtClientName();
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
					strPayAccountNo = DataFormat.formatString(pi.getExtAccountNo());
					info.setPayAccountNo(strPayAccountNo);
					info.setPayBankName(pi.getExtRemitInBank());
				}
				else //总账
					{
					strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
					strPayAccountNo = NameRef.getGLTypeNoByID(pi.getPayGL());
					info.setPayAccountNo(strPayAccountNo);
				}
			if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER || pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
			{
				//如果是定期支取或者通知存款支取，金额和收款方按照活期账户收款方进行设置
				//收款方
				if (pi.getReceiveAccountID() > 0)
				{
					strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveAccountID());
					info.setReceiveAccountName(strReceiveAccountName);
					//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
					//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
	                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
	                {
	                	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID());
	                }
	                else
	                {
	                	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
	                }
					info.setReceiveAccountNo(strReceiveAccountNo);
					info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
					dAmount = DataFormat.formatDouble(pi.getAmount());
					  
					//对于定期支取和通知支取，金额栏位修改为不包含利息，20060214，gqfang
					/**
					if (pi.getReceiveInterestAccountID() > 0)
					{
						dAmount = DataFormat.formatDouble(dAmount + DataFormat.formatDouble(pi.getPayableInterest()));
						System.out.println("========dAmount2:" + dAmount);
					}
					**/ 
				}
				else
					if (pi.getReceiveInterestAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveInterestAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
						//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
		                {
		                	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveInterestAccountID());
		                }
		                else
		                {
		                	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
		                }
						info.setReceiveAccountNo(strReceiveAccountNo);
						info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
						dAmount = DataFormat.formatDouble(pi.getPayableInterest());
					}
				strAmount = DataFormat.formatAmount(dAmount);
				info.setAmount(strAmount);
				String strChineseAmount = ChineseCurrency.showChinese(strAmount, pi.getCurrencyID());
				info.setChineseAmount(strChineseAmount);
				strAmount = getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(dAmount);
				info.setAmount(strAmount);
			}
			else
				if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
				{
					//定期转存
					if (pi.getReceiveInterestAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveInterestAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
						//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
		                {
		                	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveInterestAccountID());
		                }
		                else
		                {
		                	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
		                }
						info.setReceiveAccountNo(strReceiveAccountNo);
						info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
					}
					
					if(pi.getIsCapitalAndInterestTransfer()==1){
						dAmount = DataFormat.formatDouble(pi.getRealInterest()+pi.getAmount());						
						strAmount = DataFormat.formatAmount(dAmount);
					}else{
						dAmount = DataFormat.formatDouble(pi.getRealInterest());						
						strAmount = DataFormat.formatAmount(dAmount);
					}
					
					info.setAmount(strAmount);
					String strChineseAmount = ChineseCurrency.showChinese(strAmount, pi.getCurrencyID());
					info.setChineseAmount(strChineseAmount);
					strAmount = getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(dAmount);
					info.setAmount(strAmount);
					
				}
				else //其他业务
					{
					if (pi.getReceiveAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
						//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
		                {
		                	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID());
		                }
		                else
		                {
		                	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
		                }
						info.setReceiveAccountNo(strReceiveAccountNo);
						info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
					}
					else
						if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
						{
							strReceiveAccountName = pi.getExtClientName();
							info.setReceiveAccountName(strReceiveAccountName);
							strReceiveAccountNo = DataFormat.formatString(pi.getExtAccountNo());
							info.setReceiveAccountNo(strReceiveAccountNo);
							info.setReceiveBankName(pi.getExtRemitInBank());
						}
						else //总账
							{
							strReceiveAccountName = NameRef.getGLTypeDescByID(pi.getReceiveGL());
							info.setReceiveAccountName(strReceiveAccountName);
							strReceiveAccountNo = NameRef.getGLTypeNoByID(pi.getReceiveGL());
							info.setReceiveAccountNo(strReceiveAccountNo);
						}
					if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
					{
						//如果是委托贷款收回,特转贷金额为贷款利息-利息税费(2004/3/31)
						pi.setAmount(
							DataFormat.formatDouble(
								DataFormat.formatDouble(pi.getAmount())
									+ DataFormat.formatDouble(pi.getRealInterest())
									+ DataFormat.formatDouble(pi.getRealOverDueInterest())
									+ DataFormat.formatDouble(pi.getRealCompoundInterest())
									- DataFormat.formatDouble(pi.getRealInterestTax())));
					}
					strAmount = DataFormat.formatAmount(pi.getAmount());
					info.setAmount(strAmount);
					String strChineseAmount = ChineseCurrency.showChinese(strAmount, pi.getCurrencyID());
					info.setChineseAmount(strChineseAmount);
					strAmount = getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getAmount());
					info.setAmount(strAmount);
				}
			String strInputUser = NameRef.getUserNameByID(pi.getInputUserID());
			info.setInputUserName(strInputUser);
			String strCheckUser = "";
			if (pi.getCheckUserID() > 0)
			{
				strCheckUser = NameRef.getUserNameByID(pi.getCheckUserID());
			}
			else
			{
				if (pi.getStatusID() == SETTConstant.TransactionStatus.CHECK)
				{
					strCheckUser = "机核";
				}
				else
				{
					strCheckUser = "&nbsp;&nbsp;&nbsp;&nbsp;";
				}
			}
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
	/*
	 * 打印内部转账传票
	 */
	public static void printShowCredit2(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			ShowSpecialTransInfo info = new ShowSpecialTransInfo();
			double dAmount = 0;
			String strAmount = "";
			String strReceiveAccountName = "";
			String strReceiveAccountNo = "";
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
			//付款方账户
			if (pi.getPayAccountID() > 0)
			{
				strPayAccountName = NameRef.getAccountNameByID(pi.getPayAccountID());
				info.setPayAccountName(DataFormat.formatString(strPayAccountName));
				//strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
				//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
				//modify by zcwang 2007-3-26
                if( Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )
                {
                    strPayAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getPayAccountID());
                }
                else
                {
                    strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
                }
				//
				info.setPayAccountNo(strPayAccountNo);
				info.setPayBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			//外部银行账户
			else if(pi.getPayBankID() > 0)
			{
				Sett_BranchDAO ldao = new Sett_BranchDAO();
				BranchInfo linfo = new BranchInfo();
				linfo = ldao.findByID(pi.getPayBankID());
				info.setPayAccountName(DataFormat.formatString(linfo.getEnterpriseName()));
				info.setPayAccountNo(DataFormat.formatString(linfo.getBankAccountCode()));
				info.setPayBankName(DataFormat.formatString(linfo.getBranchName()));
			}
			else
				if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
				{
					strPayAccountName = pi.getExtClientName();
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
					strPayAccountNo = DataFormat.formatString(pi.getExtAccountNo());
					info.setPayAccountNo(strPayAccountNo);
					info.setPayBankName(pi.getExtRemitInBank());
				}
				else //总账
					{
					strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
					strPayAccountNo = NameRef.getGLTypeNoByID(pi.getPayGL());
					info.setPayAccountNo(strPayAccountNo);
				}
			if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER || pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
			{
				//如果是定期支取或者通知存款支取，金额和收款方按照活期账户收款方进行设置
				//收款方
				if (pi.getReceiveAccountID() > 0)
				{
					strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveAccountID());
					info.setReceiveAccountName(strReceiveAccountName);
					//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
					//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
                    if( Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )
                    {
                        
                    	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID());
                    	
                    }
                    else
                    {
                    	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
                    }
					
					info.setReceiveAccountNo(strReceiveAccountNo);
					info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
					dAmount = DataFormat.formatDouble(pi.getAmount());
					  
					//对于定期支取和通知支取，金额栏位修改为不包含利息，20060214，gqfang
					/**
					if (pi.getReceiveInterestAccountID() > 0)
					{
						dAmount = DataFormat.formatDouble(dAmount + DataFormat.formatDouble(pi.getPayableInterest()));
						System.out.println("========dAmount2:" + dAmount);
					}
					**/ 
				}
				else
					if (pi.getReceiveInterestAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveInterestAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
						//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
                        if( Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )
                        {
                        	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveInterestAccountID());
                        }
                        else
                        {
                        	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
                            
                        }
						
						info.setReceiveAccountNo(strReceiveAccountNo);
						info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
						dAmount = DataFormat.formatDouble(pi.getPayableInterest());
					}
				strAmount = DataFormat.formatAmount(dAmount);
				info.setAmount(strAmount);
				String strChineseAmount = ChineseCurrency.showChinese(strAmount, pi.getCurrencyID());
				info.setChineseAmount(strChineseAmount);
				strAmount = getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(dAmount);
				info.setAmount(strAmount);
			}
			else
				if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
				{
					//定期转存
					if (pi.getReceiveInterestAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveInterestAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
						//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
						if( Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )
                        {
							strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveInterestAccountID());
                        }
                        else
                        {
                        	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
                            
                        }
						
						info.setReceiveAccountNo(strReceiveAccountNo);
						info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
					}
					
					if(pi.getIsCapitalAndInterestTransfer()==1){
						dAmount = DataFormat.formatDouble(pi.getRealInterest()+pi.getAmount());						
						strAmount = DataFormat.formatAmount(dAmount);
					}else{
						dAmount = DataFormat.formatDouble(pi.getRealInterest());						
						strAmount = DataFormat.formatAmount(dAmount);
					}
					
					info.setAmount(strAmount);
					String strChineseAmount = ChineseCurrency.showChinese(strAmount, pi.getCurrencyID());
					info.setChineseAmount(strChineseAmount);
					strAmount = getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(dAmount);
					info.setAmount(strAmount);
					
				}
				else //其他业务
					{
					if (pi.getReceiveAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
						//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
						if( Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )
                        {
							strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID());
                        }
                        else
                        {
                        	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
                            
                        }
						
						info.setReceiveAccountNo(strReceiveAccountNo);
						info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
					}
					else
						if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
						{
							strReceiveAccountName = pi.getExtClientName();
							info.setReceiveAccountName(strReceiveAccountName);
							strReceiveAccountNo = DataFormat.formatString(pi.getExtAccountNo());
							info.setReceiveAccountNo(strReceiveAccountNo);
							info.setReceiveBankName(pi.getExtRemitInBank());
						}
						else //总账
							{
							strReceiveAccountName = NameRef.getGLTypeDescByID(pi.getReceiveGL());
							info.setReceiveAccountName(strReceiveAccountName);
							strReceiveAccountNo = NameRef.getGLTypeNoByID(pi.getReceiveGL());
							info.setReceiveAccountNo(strReceiveAccountNo);
						}
					if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
					{
						//如果是委托贷款收回,特转贷金额为贷款利息-利息税费(2004/3/31)
						pi.setAmount(
							DataFormat.formatDouble(
								DataFormat.formatDouble(pi.getAmount())
									+ DataFormat.formatDouble(pi.getRealInterest())
									+ DataFormat.formatDouble(pi.getRealOverDueInterest())
									+ DataFormat.formatDouble(pi.getRealCompoundInterest())
									- DataFormat.formatDouble(pi.getRealInterestTax())));
					}
					strAmount = DataFormat.formatAmount(pi.getAmount());
					info.setAmount(strAmount);
					String strChineseAmount = ChineseCurrency.showChinese(strAmount, pi.getCurrencyID());
					info.setChineseAmount(strChineseAmount);
					strAmount = getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getAmount());
					info.setAmount(strAmount);
				}
			String strInputUser = NameRef.getUserNameByID(pi.getInputUserID());
			info.setInputUserName(strInputUser);
			String strCheckUser = "";
			if (pi.getCheckUserID() > 0)
			{
				strCheckUser = NameRef.getUserNameByID(pi.getCheckUserID());
			}
			else
			{
				if (pi.getStatusID() == SETTConstant.TransactionStatus.CHECK)
				{
					strCheckUser = "机核";
				}
				else
				{
					strCheckUser = "&nbsp;&nbsp;&nbsp;&nbsp;";
				}
			}
			info.setCheckUserName(strCheckUser);
			
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
            
            String strAbstract = "起息日：" + DataFormat.formatDate(pi.getInterestStartDate()) + "；" + pi.getAbstract();
            info.setAbstract(strAbstract);
            
            IPrintTemplate.showCredit21(info, out);
            IPrintTemplate.showCredit22(info, out);
            IPrintTemplate.showCredit23(info, out);
			
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
		}
	}
	/*
	 * 打印定期转活期传票
	 */
	public static void printShowFixedToCurrent(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			ShowSpecialTransInfo info = new ShowSpecialTransInfo();
			double dAmount = 0;
			String strAmount = "";
			String strReceiveAccountName = "";
			String strReceiveAccountNo = "";
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
			//付款方账户
			if (pi.getPayAccountID() > 0)
			{
				strPayAccountName = NameRef.getAccountNameByID(pi.getPayAccountID());
				info.setPayAccountName(DataFormat.formatString(strPayAccountName));
				//strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
				//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
                {
                    strPayAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getPayAccountID());
                }
                else
                {
                    strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
                }
				info.setPayAccountNo(strPayAccountNo);
				info.setPayBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			//外部银行账户
			else if(pi.getPayBankID() > 0)
			{
				Sett_BranchDAO ldao = new Sett_BranchDAO();
				BranchInfo linfo = new BranchInfo();
				linfo = ldao.findByID(pi.getPayBankID());
				info.setPayAccountName(DataFormat.formatString(linfo.getEnterpriseName()));
				info.setPayAccountNo(DataFormat.formatString(linfo.getBankAccountCode()));
				info.setPayBankName(DataFormat.formatString(linfo.getBranchName()));
			}
			else
				if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
				{
					strPayAccountName = pi.getExtClientName();
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
					strPayAccountNo = DataFormat.formatString(pi.getExtAccountNo());
					info.setPayAccountNo(strPayAccountNo);
					info.setPayBankName(pi.getExtRemitInBank());
				}
				else //总账
					{
					strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
					strPayAccountNo = NameRef.getGLTypeNoByID(pi.getPayGL());
					info.setPayAccountNo(strPayAccountNo);
				}
			if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER || pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
			{
				//如果是定期支取或者通知存款支取，金额和收款方按照活期账户收款方进行设置
				//收款方
				if (pi.getReceiveAccountID() > 0)
				{
					strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveAccountID());
					info.setReceiveAccountName(strReceiveAccountName);
					//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
					//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
	                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
	                {
	                	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID());
	                }
	                else
	                {
	                	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
	                }
					info.setReceiveAccountNo(strReceiveAccountNo);
					info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
					dAmount = DataFormat.formatDouble(pi.getAmount());
					  
					//对于定期支取和通知支取，金额栏位修改为不包含利息，20060214，gqfang
					/**
					if (pi.getReceiveInterestAccountID() > 0)
					{
						dAmount = DataFormat.formatDouble(dAmount + DataFormat.formatDouble(pi.getPayableInterest()));
						System.out.println("========dAmount2:" + dAmount);
					}
					**/ 
				}
				else
					if (pi.getReceiveInterestAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveInterestAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
						//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
		                {
		                	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveInterestAccountID());
		                }
		                else
		                {
		                	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
		                }
						info.setReceiveAccountNo(strReceiveAccountNo);
						info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
						dAmount = DataFormat.formatDouble(pi.getPayableInterest());
					}
				strAmount = DataFormat.formatAmount(dAmount);
				info.setAmount(strAmount);
				String strChineseAmount = ChineseCurrency.showChinese(strAmount, pi.getCurrencyID());
				info.setChineseAmount(strChineseAmount);
				strAmount = getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(dAmount);
				info.setAmount(strAmount);
			}
			else
				if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
				{
					//定期转存
					if (pi.getReceiveInterestAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveInterestAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
						//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
		                {
		                	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveInterestAccountID());
		                }
		                else
		                {
		                	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
		                }
						info.setReceiveAccountNo(strReceiveAccountNo);
						info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
					}
					
					if(pi.getIsCapitalAndInterestTransfer()==1){
						dAmount = DataFormat.formatDouble(pi.getRealInterest()+pi.getAmount());						
						strAmount = DataFormat.formatAmount(dAmount);
					}else{
						dAmount = DataFormat.formatDouble(pi.getRealInterest());						
						strAmount = DataFormat.formatAmount(dAmount);
					}
					
					info.setAmount(strAmount);
					String strChineseAmount = ChineseCurrency.showChinese(strAmount, pi.getCurrencyID());
					info.setChineseAmount(strChineseAmount);
					strAmount = getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(dAmount);
					info.setAmount(strAmount);
					
				}
				else //其他业务
					{
					if (pi.getReceiveAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
						//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
		                {
		                	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID());
		                }
		                else
		                {
		                	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
		                }
						info.setReceiveAccountNo(strReceiveAccountNo);
						info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
					}
					else
						if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
						{
							strReceiveAccountName = pi.getExtClientName();
							info.setReceiveAccountName(strReceiveAccountName);
							strReceiveAccountNo = DataFormat.formatString(pi.getExtAccountNo());
							info.setReceiveAccountNo(strReceiveAccountNo);
							info.setReceiveBankName(pi.getExtRemitInBank());
						}
						else //总账
							{
							strReceiveAccountName = NameRef.getGLTypeDescByID(pi.getReceiveGL());
							info.setReceiveAccountName(strReceiveAccountName);
							strReceiveAccountNo = NameRef.getGLTypeNoByID(pi.getReceiveGL());
							info.setReceiveAccountNo(strReceiveAccountNo);
						}
					if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
					{
						//如果是委托贷款收回,特转贷金额为贷款利息-利息税费(2004/3/31)
						pi.setAmount(
							DataFormat.formatDouble(
								DataFormat.formatDouble(pi.getAmount())
									+ DataFormat.formatDouble(pi.getRealInterest())
									+ DataFormat.formatDouble(pi.getRealOverDueInterest())
									+ DataFormat.formatDouble(pi.getRealCompoundInterest())
									- DataFormat.formatDouble(pi.getRealInterestTax())));
					}
					strAmount = DataFormat.formatAmount(pi.getAmount());
					info.setAmount(strAmount);
					String strChineseAmount = ChineseCurrency.showChinese(strAmount, pi.getCurrencyID());
					info.setChineseAmount(strChineseAmount);
					strAmount = getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getAmount());
					info.setAmount(strAmount);
				}
			String strInputUser = NameRef.getUserNameByID(pi.getInputUserID());
			info.setInputUserName(strInputUser);
			String strCheckUser = "";
			if (pi.getCheckUserID() > 0)
			{
				strCheckUser = NameRef.getUserNameByID(pi.getCheckUserID());
			}
			else
			{
				if (pi.getStatusID() == SETTConstant.TransactionStatus.CHECK)
				{
					strCheckUser = "机核";
				}
				else
				{
					strCheckUser = "&nbsp;&nbsp;&nbsp;&nbsp;";
				}
			}
			info.setCheckUserName(strCheckUser);
			
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
            
            String strAbstract = "起息日：" + DataFormat.formatDate(pi.getInterestStartDate()) + "；" + pi.getAbstract();
            info.setAbstract(strAbstract);
            
            IPrintTemplate.showFixedToCurrent1(info,out);
            IPrintTemplate.showFixedToCurrent2(info,out);
			
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
		}
	}
	/*
	 * 打印活期转定期传票
	 */
	public static void printShowCurrentToFixed(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			ShowSpecialTransInfo info = new ShowSpecialTransInfo();
			double dAmount = 0;
			String strAmount = "";
			String strReceiveAccountName = "";
			String strReceiveAccountNo = "";
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
			//付款方账户
			if (pi.getPayAccountID() > 0)
			{
				strPayAccountName = NameRef.getAccountNameByID(pi.getPayAccountID());
				info.setPayAccountName(DataFormat.formatString(strPayAccountName));
				//strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
				//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
                {
                    strPayAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getPayAccountID());
                }
                else
                {
                    strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
                }
				info.setPayAccountNo(strPayAccountNo);
				info.setPayBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			//外部银行账户
			else if(pi.getPayBankID() > 0)
			{
				Sett_BranchDAO ldao = new Sett_BranchDAO();
				BranchInfo linfo = new BranchInfo();
				linfo = ldao.findByID(pi.getPayBankID());
				info.setPayAccountName(DataFormat.formatString(linfo.getEnterpriseName()));
				info.setPayAccountNo(DataFormat.formatString(linfo.getBankAccountCode()));
				info.setPayBankName(DataFormat.formatString(linfo.getBranchName()));
			}
			else
				if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
				{
					strPayAccountName = pi.getExtClientName();
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
					strPayAccountNo = DataFormat.formatString(pi.getExtAccountNo());
					info.setPayAccountNo(strPayAccountNo);
					info.setPayBankName(pi.getExtRemitInBank());
				}
				else //总账
					{
					strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
					strPayAccountNo = NameRef.getGLTypeNoByID(pi.getPayGL());
					info.setPayAccountNo(strPayAccountNo);
				}
			if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER || pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
			{
				//如果是定期支取或者通知存款支取，金额和收款方按照活期账户收款方进行设置
				//收款方
				if (pi.getReceiveAccountID() > 0)
				{
					strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveAccountID());
					info.setReceiveAccountName(strReceiveAccountName);
					//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
					//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
	                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
	                {
	                	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID());
	                }
	                else
	                {
	                	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
	                }
					info.setReceiveAccountNo(strReceiveAccountNo);
					info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
					dAmount = DataFormat.formatDouble(pi.getAmount());
					  
					//对于定期支取和通知支取，金额栏位修改为不包含利息，20060214，gqfang
					/**
					if (pi.getReceiveInterestAccountID() > 0)
					{
						dAmount = DataFormat.formatDouble(dAmount + DataFormat.formatDouble(pi.getPayableInterest()));
						System.out.println("========dAmount2:" + dAmount);
					}
					**/ 
				}
				else
					if (pi.getReceiveInterestAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveInterestAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
						//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
		                {
		                	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveInterestAccountID());
		                }
		                else
		                {
		                	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
		                }
						info.setReceiveAccountNo(strReceiveAccountNo);
						info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
						dAmount = DataFormat.formatDouble(pi.getPayableInterest());
					}
				strAmount = DataFormat.formatAmount(dAmount);
				info.setAmount(strAmount);
				String strChineseAmount = ChineseCurrency.showChinese(strAmount, pi.getCurrencyID());
				info.setChineseAmount(strChineseAmount);
				strAmount = getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(dAmount);
				info.setAmount(strAmount);
			}
			else
				if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
				{
					//定期转存
					if (pi.getReceiveInterestAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveInterestAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
						//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
		                {
		                	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveInterestAccountID());
		                }
		                else
		                {
		                	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
		                }
						info.setReceiveAccountNo(strReceiveAccountNo);
						info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
					}
					
					if(pi.getIsCapitalAndInterestTransfer()==1){
						dAmount = DataFormat.formatDouble(pi.getRealInterest()+pi.getAmount());						
						strAmount = DataFormat.formatAmount(dAmount);
					}else{
						dAmount = DataFormat.formatDouble(pi.getRealInterest());						
						strAmount = DataFormat.formatAmount(dAmount);
					}
					
					info.setAmount(strAmount);
					String strChineseAmount = ChineseCurrency.showChinese(strAmount, pi.getCurrencyID());
					info.setChineseAmount(strChineseAmount);
					strAmount = getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(dAmount);
					info.setAmount(strAmount);
					
				}
				else //其他业务
					{
					if (pi.getReceiveAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
						//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
		                {
		                	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID());
		                }
		                else
		                {
		                	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
		                }
						info.setReceiveAccountNo(strReceiveAccountNo);
						info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
					}
					else
						if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
						{
							strReceiveAccountName = pi.getExtClientName();
							info.setReceiveAccountName(strReceiveAccountName);
							strReceiveAccountNo = DataFormat.formatString(pi.getExtAccountNo());
							info.setReceiveAccountNo(strReceiveAccountNo);
							info.setReceiveBankName(pi.getExtRemitInBank());
						}
						else //总账
							{
							strReceiveAccountName = NameRef.getGLTypeDescByID(pi.getReceiveGL());
							info.setReceiveAccountName(strReceiveAccountName);
							strReceiveAccountNo = NameRef.getGLTypeNoByID(pi.getReceiveGL());
							info.setReceiveAccountNo(strReceiveAccountNo);
						}
					if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
					{
						//如果是委托贷款收回,特转贷金额为贷款利息-利息税费(2004/3/31)
						pi.setAmount(
							DataFormat.formatDouble(
								DataFormat.formatDouble(pi.getAmount())
									+ DataFormat.formatDouble(pi.getRealInterest())
									+ DataFormat.formatDouble(pi.getRealOverDueInterest())
									+ DataFormat.formatDouble(pi.getRealCompoundInterest())
									- DataFormat.formatDouble(pi.getRealInterestTax())));
					}
					strAmount = DataFormat.formatAmount(pi.getAmount());
					info.setAmount(strAmount);
					String strChineseAmount = ChineseCurrency.showChinese(strAmount, pi.getCurrencyID());
					info.setChineseAmount(strChineseAmount);
					strAmount = getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getAmount());
					info.setAmount(strAmount);
				}
			String strInputUser = NameRef.getUserNameByID(pi.getInputUserID());
			info.setInputUserName(strInputUser);
			String strCheckUser = "";
			if (pi.getCheckUserID() > 0)
			{
				strCheckUser = NameRef.getUserNameByID(pi.getCheckUserID());
			}
			else
			{
				if (pi.getStatusID() == SETTConstant.TransactionStatus.CHECK)
				{
					strCheckUser = "机核";
				}
				else
				{
					strCheckUser = "&nbsp;&nbsp;&nbsp;&nbsp;";
				}
			}
			info.setCheckUserName(strCheckUser);
			
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
            
            String strAbstract = "起息日：" + DataFormat.formatDate(pi.getInterestStartDate()) + ("".equals(pi.getAbstract())?"":"；" + pi.getAbstract());
            info.setAbstract(strAbstract);
            
            IPrintTemplate.showCurrentToFixed1(info, out);
            IPrintTemplate.showCurrentToFixed2(info, out);
			
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
		}
	}
	/*
	 * 打印活期转通知传票
	 */
	public static void printShowCurrentToNotice(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			ShowSpecialTransInfo info = new ShowSpecialTransInfo();
			double dAmount = 0;
			String strAmount = "";
			String strReceiveAccountName = "";
			String strReceiveAccountNo = "";
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
			//付款方账户
			if (pi.getPayAccountID() > 0)
			{
				strPayAccountName = NameRef.getAccountNameByID(pi.getPayAccountID());
				info.setPayAccountName(DataFormat.formatString(strPayAccountName));
				//strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
				//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
                {
                    strPayAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getPayAccountID());
                }
                else
                {
                    strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
                }
				info.setPayAccountNo(strPayAccountNo);
				info.setPayBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			//外部银行账户
			else if(pi.getPayBankID() > 0)
			{
				Sett_BranchDAO ldao = new Sett_BranchDAO();
				BranchInfo linfo = new BranchInfo();
				linfo = ldao.findByID(pi.getPayBankID());
				info.setPayAccountName(DataFormat.formatString(linfo.getEnterpriseName()));
				info.setPayAccountNo(DataFormat.formatString(linfo.getBankAccountCode()));
				info.setPayBankName(DataFormat.formatString(linfo.getBranchName()));
			}
			else
				if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
				{
					strPayAccountName = pi.getExtClientName();
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
					strPayAccountNo = DataFormat.formatString(pi.getExtAccountNo());
					info.setPayAccountNo(strPayAccountNo);
					info.setPayBankName(pi.getExtRemitInBank());
				}
				else //总账
					{
					strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
					strPayAccountNo = NameRef.getGLTypeNoByID(pi.getPayGL());
					info.setPayAccountNo(strPayAccountNo);
				}
			if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER || pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
			{
				//如果是定期支取或者通知存款支取，金额和收款方按照活期账户收款方进行设置
				//收款方
				if (pi.getReceiveAccountID() > 0)
				{
					strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveAccountID());
					info.setReceiveAccountName(strReceiveAccountName);
					//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
					//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
	                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
	                {
	                	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID());
	                }
	                else
	                {
	                	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
	                }
					info.setReceiveAccountNo(strReceiveAccountNo);
					info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
					dAmount = DataFormat.formatDouble(pi.getAmount());
					  
					//对于定期支取和通知支取，金额栏位修改为不包含利息，20060214，gqfang
					/**
					if (pi.getReceiveInterestAccountID() > 0)
					{
						dAmount = DataFormat.formatDouble(dAmount + DataFormat.formatDouble(pi.getPayableInterest()));
						System.out.println("========dAmount2:" + dAmount);
					}
					**/ 
				}
				else
					if (pi.getReceiveInterestAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveInterestAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
						//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
		                {
		                	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveInterestAccountID());
		                }
		                else
		                {
		                	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
		                }
						info.setReceiveAccountNo(strReceiveAccountNo);
						info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
						dAmount = DataFormat.formatDouble(pi.getPayableInterest());
					}
				strAmount = DataFormat.formatAmount(dAmount);
				info.setAmount(strAmount);
				String strChineseAmount = ChineseCurrency.showChinese(strAmount, pi.getCurrencyID());
				info.setChineseAmount(strChineseAmount);
				strAmount = getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(dAmount);
				info.setAmount(strAmount);
			}
			else
				if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
				{
					//定期转存
					if (pi.getReceiveInterestAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveInterestAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
						//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
		                {
		                	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveInterestAccountID());
		                }
		                else
		                {
		                	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
		                }
						info.setReceiveAccountNo(strReceiveAccountNo);
						info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
					}
					
					if(pi.getIsCapitalAndInterestTransfer()==1){
						dAmount = DataFormat.formatDouble(pi.getRealInterest()+pi.getAmount());						
						strAmount = DataFormat.formatAmount(dAmount);
					}else{
						dAmount = DataFormat.formatDouble(pi.getRealInterest());						
						strAmount = DataFormat.formatAmount(dAmount);
					}
					
					info.setAmount(strAmount);
					String strChineseAmount = ChineseCurrency.showChinese(strAmount, pi.getCurrencyID());
					info.setChineseAmount(strChineseAmount);
					strAmount = getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(dAmount);
					info.setAmount(strAmount);
					
				}
				else //其他业务
					{
					if (pi.getReceiveAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
						//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
		                {
		                	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID());
		                }
		                else
		                {
		                	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
		                }
						info.setReceiveAccountNo(strReceiveAccountNo);
						info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
					}
					else
						if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
						{
							strReceiveAccountName = pi.getExtClientName();
							info.setReceiveAccountName(strReceiveAccountName);
							strReceiveAccountNo = DataFormat.formatString(pi.getExtAccountNo());
							info.setReceiveAccountNo(strReceiveAccountNo);
							info.setReceiveBankName(pi.getExtRemitInBank());
						}
						else //总账
							{
							strReceiveAccountName = NameRef.getGLTypeDescByID(pi.getReceiveGL());
							info.setReceiveAccountName(strReceiveAccountName);
							strReceiveAccountNo = NameRef.getGLTypeNoByID(pi.getReceiveGL());
							info.setReceiveAccountNo(strReceiveAccountNo);
						}
					if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
					{
						//如果是委托贷款收回,特转贷金额为贷款利息-利息税费(2004/3/31)
						pi.setAmount(
							DataFormat.formatDouble(
								DataFormat.formatDouble(pi.getAmount())
									+ DataFormat.formatDouble(pi.getRealInterest())
									+ DataFormat.formatDouble(pi.getRealOverDueInterest())
									+ DataFormat.formatDouble(pi.getRealCompoundInterest())
									- DataFormat.formatDouble(pi.getRealInterestTax())));
					}
					strAmount = DataFormat.formatAmount(pi.getAmount());
					info.setAmount(strAmount);
					String strChineseAmount = ChineseCurrency.showChinese(strAmount, pi.getCurrencyID());
					info.setChineseAmount(strChineseAmount);
					strAmount = getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getAmount());
					info.setAmount(strAmount);
				}
			String strInputUser = NameRef.getUserNameByID(pi.getInputUserID());
			info.setInputUserName(strInputUser);
			String strCheckUser = "";
			if (pi.getCheckUserID() > 0)
			{
				strCheckUser = NameRef.getUserNameByID(pi.getCheckUserID());
			}
			else
			{
				if (pi.getStatusID() == SETTConstant.TransactionStatus.CHECK)
				{
					strCheckUser = "机核";
				}
				else
				{
					strCheckUser = "&nbsp;&nbsp;&nbsp;&nbsp;";
				}
			}
			info.setCheckUserName(strCheckUser);
			
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
            
            String strAbstract = "起息日：" + DataFormat.formatDate(pi.getInterestStartDate()) + ("".equals(pi.getAbstract())?"":"；" + pi.getAbstract());
            info.setAbstract(strAbstract);
            
            IPrintTemplate.showCurrentToNotice(info, out);
			
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
		}
	}
	/*
	 * 打印通知转活期传票
	 */
	public static void printShowNoticeToCurrent(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			ShowSpecialTransInfo info = new ShowSpecialTransInfo();
			double dAmount = 0;
			String strAmount = "";
			String strReceiveAccountName = "";
			String strReceiveAccountNo = "";
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
			//付款方账户
			if (pi.getPayAccountID() > 0)
			{
				strPayAccountName = NameRef.getAccountNameByID(pi.getPayAccountID());
				info.setPayAccountName(DataFormat.formatString(strPayAccountName));
				//strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
				//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
                {
                    strPayAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getPayAccountID());
                }
                else
                {
                    strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
                }
				info.setPayAccountNo(strPayAccountNo);
				info.setPayBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			//外部银行账户
			else if(pi.getPayBankID() > 0)
			{
				Sett_BranchDAO ldao = new Sett_BranchDAO();
				BranchInfo linfo = new BranchInfo();
				linfo = ldao.findByID(pi.getPayBankID());
				info.setPayAccountName(DataFormat.formatString(linfo.getEnterpriseName()));
				info.setPayAccountNo(DataFormat.formatString(linfo.getBankAccountCode()));
				info.setPayBankName(DataFormat.formatString(linfo.getBranchName()));
			}
			else
				if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
				{
					strPayAccountName = pi.getExtClientName();
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
					strPayAccountNo = DataFormat.formatString(pi.getExtAccountNo());
					info.setPayAccountNo(strPayAccountNo);
					info.setPayBankName(pi.getExtRemitInBank());
				}
				else //总账
					{
					strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
					strPayAccountNo = NameRef.getGLTypeNoByID(pi.getPayGL());
					info.setPayAccountNo(strPayAccountNo);
				}
			if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER || pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
			{
				//如果是定期支取或者通知存款支取，金额和收款方按照活期账户收款方进行设置
				//收款方
				if (pi.getReceiveAccountID() > 0)
				{
					strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveAccountID());
					info.setReceiveAccountName(strReceiveAccountName);
					//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
					//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
	                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
	                {
	                	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID());
	                }
	                else
	                {
	                	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
	                }
					info.setReceiveAccountNo(strReceiveAccountNo);
					info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
					dAmount = DataFormat.formatDouble(pi.getAmount());
					  
					//对于定期支取和通知支取，金额栏位修改为不包含利息，20060214，gqfang
					/**
					if (pi.getReceiveInterestAccountID() > 0)
					{
						dAmount = DataFormat.formatDouble(dAmount + DataFormat.formatDouble(pi.getPayableInterest()));
						System.out.println("========dAmount2:" + dAmount);
					}
					**/ 
				}
				else
					if (pi.getReceiveInterestAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveInterestAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
						//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
		                {
		                	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveInterestAccountID());
		                }
		                else
		                {
		                	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
		                }
						info.setReceiveAccountNo(strReceiveAccountNo);
						info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
						dAmount = DataFormat.formatDouble(pi.getPayableInterest());
					}
				strAmount = DataFormat.formatAmount(dAmount);
				info.setAmount(strAmount);
				String strChineseAmount = ChineseCurrency.showChinese(strAmount, pi.getCurrencyID());
				info.setChineseAmount(strChineseAmount);
				strAmount = getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(dAmount);
				info.setAmount(strAmount);
			}
			else
				if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
				{
					//定期转存
					if (pi.getReceiveInterestAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveInterestAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
						//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
		                {
		                	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveInterestAccountID());
		                }
		                else
		                {
		                	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
		                }
						info.setReceiveAccountNo(strReceiveAccountNo);
						info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
					}
					
					if(pi.getIsCapitalAndInterestTransfer()==1){
						dAmount = DataFormat.formatDouble(pi.getRealInterest()+pi.getAmount());						
						strAmount = DataFormat.formatAmount(dAmount);
					}else{
						dAmount = DataFormat.formatDouble(pi.getRealInterest());						
						strAmount = DataFormat.formatAmount(dAmount);
					}
					
					info.setAmount(strAmount);
					String strChineseAmount = ChineseCurrency.showChinese(strAmount, pi.getCurrencyID());
					info.setChineseAmount(strChineseAmount);
					strAmount = getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(dAmount);
					info.setAmount(strAmount);
					
				}
				else //其他业务
					{
					if (pi.getReceiveAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
						//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
		                {
		                	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID());
		                }
		                else
		                {
		                	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
		                }
						info.setReceiveAccountNo(strReceiveAccountNo);
						info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
					}
					else
						if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
						{
							strReceiveAccountName = pi.getExtClientName();
							info.setReceiveAccountName(strReceiveAccountName);
							strReceiveAccountNo = DataFormat.formatString(pi.getExtAccountNo());
							info.setReceiveAccountNo(strReceiveAccountNo);
							info.setReceiveBankName(pi.getExtRemitInBank());
						}
						else //总账
							{
							strReceiveAccountName = NameRef.getGLTypeDescByID(pi.getReceiveGL());
							info.setReceiveAccountName(strReceiveAccountName);
							strReceiveAccountNo = NameRef.getGLTypeNoByID(pi.getReceiveGL());
							info.setReceiveAccountNo(strReceiveAccountNo);
						}
					if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
					{
						//如果是委托贷款收回,特转贷金额为贷款利息-利息税费(2004/3/31)
						pi.setAmount(
							DataFormat.formatDouble(
								DataFormat.formatDouble(pi.getAmount())
									+ DataFormat.formatDouble(pi.getRealInterest())
									+ DataFormat.formatDouble(pi.getRealOverDueInterest())
									+ DataFormat.formatDouble(pi.getRealCompoundInterest())
									- DataFormat.formatDouble(pi.getRealInterestTax())));
					}
					strAmount = DataFormat.formatAmount(pi.getAmount());
					info.setAmount(strAmount);
					String strChineseAmount = ChineseCurrency.showChinese(strAmount, pi.getCurrencyID());
					info.setChineseAmount(strChineseAmount);
					strAmount = getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getAmount());
					info.setAmount(strAmount);
				}
			String strInputUser = NameRef.getUserNameByID(pi.getInputUserID());
			info.setInputUserName(strInputUser);
			String strCheckUser = "";
			if (pi.getCheckUserID() > 0)
			{
				strCheckUser = NameRef.getUserNameByID(pi.getCheckUserID());
			}
			else
			{
				if (pi.getStatusID() == SETTConstant.TransactionStatus.CHECK)
				{
					strCheckUser = "机核";
				}
				else
				{
					strCheckUser = "&nbsp;&nbsp;&nbsp;&nbsp;";
				}
			}
			info.setCheckUserName(strCheckUser);
			
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
            
            String strAbstract = "起息日：" + DataFormat.formatDate(pi.getInterestStartDate()) + "；" + pi.getAbstract();
            info.setAbstract(strAbstract);
            
            IPrintTemplate.showNoticeToCurrent(info, out);
			
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
		}
	}
	/*
	 * 打印划收转账传票
	 */
	public static void printShowAcceptTransfer(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			ShowSpecialTransInfo info = new ShowSpecialTransInfo();
			double dAmount = 0;
			String strAmount = "";
			String strReceiveAccountName = "";
			String strReceiveAccountNo = "";
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
			//付款方账户
			if (pi.getPayAccountID() > 0)
			{
				strPayAccountName = NameRef.getAccountNameByID(pi.getPayAccountID());
				info.setPayAccountName(DataFormat.formatString(strPayAccountName));
				//strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
				//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
                {
                    strPayAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getPayAccountID());
                }
                else
                {
                    strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
                }
				info.setPayAccountNo(strPayAccountNo);
				info.setPayBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			//外部银行账户－中交修改－－－－－－－－－－
//			else if(pi.getPayBankID() > 0)
//			{
//				Sett_BranchDAO ldao = new Sett_BranchDAO();
//				BranchInfo linfo = new BranchInfo();
//				linfo = ldao.findByID(pi.getPayBankID());
//				info.setPayAccountName(DataFormat.formatString(linfo.getEnterpriseName()));
//				info.setPayAccountNo(DataFormat.formatString(linfo.getBankAccountCode()));
//				info.setPayBankName(DataFormat.formatString(linfo.getBranchName()));
//			}
			else
				if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
				{
					strPayAccountName = pi.getExtClientName();
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
					strPayAccountNo = DataFormat.formatString(pi.getExtAccountNo());
					info.setPayAccountNo(strPayAccountNo);
					info.setPayBankName(pi.getExtRemitInBank());
				}
				else //总账
					{
					strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
					strPayAccountNo = NameRef.getGLTypeNoByID(pi.getPayGL());
					info.setPayAccountNo(strPayAccountNo);
				}
			if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER || pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
			{
				//如果是定期支取或者通知存款支取，金额和收款方按照活期账户收款方进行设置
				//收款方
				if (pi.getReceiveAccountID() > 0)
				{
					strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveAccountID());
					info.setReceiveAccountName(strReceiveAccountName);
					//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
					//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
	                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
	                {
	                	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID());
	                }
	                else
	                {
	                	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
	                }
					info.setReceiveAccountNo(strReceiveAccountNo);
					info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
					dAmount = DataFormat.formatDouble(pi.getAmount());
					  
					//对于定期支取和通知支取，金额栏位修改为不包含利息，20060214，gqfang
					/**
					if (pi.getReceiveInterestAccountID() > 0)
					{
						dAmount = DataFormat.formatDouble(dAmount + DataFormat.formatDouble(pi.getPayableInterest()));
						System.out.println("========dAmount2:" + dAmount);
					}
					**/ 
				}
				else
					if (pi.getReceiveInterestAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveInterestAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
						//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
		                {
		                	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveInterestAccountID());
		                }
		                else
		                {
		                	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
		                }
						info.setReceiveAccountNo(strReceiveAccountNo);
						info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
						dAmount = DataFormat.formatDouble(pi.getPayableInterest());
					}
				strAmount = DataFormat.formatAmount(dAmount);
				info.setAmount(strAmount);
				String strChineseAmount = ChineseCurrency.showChinese(strAmount, pi.getCurrencyID());
				info.setChineseAmount(strChineseAmount);
				strAmount = getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(dAmount);
				info.setAmount(strAmount);
			}
			else
				if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
				{
					//定期转存
					if (pi.getReceiveInterestAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveInterestAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
						//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
		                {
		                	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveInterestAccountID());
		                }
		                else
		                {
		                	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
		                }
						info.setReceiveAccountNo(strReceiveAccountNo);
						info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
					}
					
					if(pi.getIsCapitalAndInterestTransfer()==1){
						dAmount = DataFormat.formatDouble(pi.getRealInterest()+pi.getAmount());						
						strAmount = DataFormat.formatAmount(dAmount);
					}else{
						dAmount = DataFormat.formatDouble(pi.getRealInterest());						
						strAmount = DataFormat.formatAmount(dAmount);
					}
					
					info.setAmount(strAmount);
					String strChineseAmount = ChineseCurrency.showChinese(strAmount, pi.getCurrencyID());
					info.setChineseAmount(strChineseAmount);
					strAmount = getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(dAmount);
					info.setAmount(strAmount);
					
				}
				else //其他业务
					{
					if (pi.getReceiveAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
						//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
		                {
		                	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID());
		                }
		                else
		                {
		                	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
		                }
						info.setReceiveAccountNo(strReceiveAccountNo);
						info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
					}
					else
						if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
						{
							strReceiveAccountName = pi.getExtClientName();
							info.setReceiveAccountName(strReceiveAccountName);
							strReceiveAccountNo = DataFormat.formatString(pi.getExtAccountNo());
							info.setReceiveAccountNo(strReceiveAccountNo);
							info.setReceiveBankName(pi.getExtRemitInBank());
						}
						else //总账
							{
							strReceiveAccountName = NameRef.getGLTypeDescByID(pi.getReceiveGL());
							info.setReceiveAccountName(strReceiveAccountName);
							strReceiveAccountNo = NameRef.getGLTypeNoByID(pi.getReceiveGL());
							info.setReceiveAccountNo(strReceiveAccountNo);
						}
					if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
					{
						//如果是委托贷款收回,特转贷金额为贷款利息-利息税费(2004/3/31)
						pi.setAmount(
							DataFormat.formatDouble(
								DataFormat.formatDouble(pi.getAmount())
									+ DataFormat.formatDouble(pi.getRealInterest())
									+ DataFormat.formatDouble(pi.getRealOverDueInterest())
									+ DataFormat.formatDouble(pi.getRealCompoundInterest())
									- DataFormat.formatDouble(pi.getRealInterestTax())));
					}
					strAmount = DataFormat.formatAmount(pi.getAmount());
					info.setAmount(strAmount);
					String strChineseAmount = ChineseCurrency.showChinese(strAmount, pi.getCurrencyID());
					info.setChineseAmount(strChineseAmount);
					strAmount = getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getAmount());
					info.setAmount(strAmount);
				}
			String strInputUser = NameRef.getUserNameByID(pi.getInputUserID());
			info.setInputUserName(strInputUser);
			String strCheckUser = "";
			if (pi.getCheckUserID() > 0)
			{
				strCheckUser = NameRef.getUserNameByID(pi.getCheckUserID());
			}
			else
			{
				if (pi.getStatusID() == SETTConstant.TransactionStatus.CHECK)
				{
					strCheckUser = "机核";
				}
				else
				{
					strCheckUser = "&nbsp;&nbsp;&nbsp;&nbsp;";
				}
			}
			info.setCheckUserName(strCheckUser);
			
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
            
            String strAbstract = "起息日：" + DataFormat.formatDate(pi.getInterestStartDate()) + "；" + pi.getAbstract();
            info.setAbstract(strAbstract);
            
            IPrintTemplate.showCredit3(info, out);
            IPrintTemplate.showCredit32(info, out);
			
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
		}
	}
	/*
	 * 打印划付转账传票
	 */
	public static void printShowPayTransfer(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			ShowSpecialTransInfo info = new ShowSpecialTransInfo();
			double dAmount = 0;
			String strAmount = "";
			String strReceiveAccountName = "";
			String strReceiveAccountNo = "";
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
			//付款方账户
			if (pi.getPayAccountID() > 0)
			{
				strPayAccountName = NameRef.getAccountNameByID(pi.getPayAccountID());
				info.setPayAccountName(DataFormat.formatString(strPayAccountName));
				//strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
				//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
                {
                	strPayAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getPayAccountID());
                }
                else
                {
                	strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
                }
				info.setPayAccountNo(strPayAccountNo);
				info.setPayBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			//外部银行账户
			else if(pi.getPayBankID() > 0)
			{
				Sett_BranchDAO ldao = new Sett_BranchDAO();
				BranchInfo linfo = new BranchInfo();
				linfo = ldao.findByID(pi.getPayBankID());
				info.setPayAccountName(DataFormat.formatString(linfo.getEnterpriseName()));
				info.setPayAccountNo(DataFormat.formatString(linfo.getBankAccountCode()));
				info.setPayBankName(DataFormat.formatString(linfo.getBranchName()));
			}
			else
				if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
				{
					strPayAccountName = pi.getExtClientName();
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
					strPayAccountNo = DataFormat.formatString(pi.getExtAccountNo());
					info.setPayAccountNo(strPayAccountNo);
					info.setPayBankName(pi.getExtRemitInBank());
				}
				else //总账
					{
					strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
					strPayAccountNo = NameRef.getGLTypeNoByID(pi.getPayGL());
					info.setPayAccountNo(strPayAccountNo);
				}
			if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER || pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
			{
				//如果是定期支取或者通知存款支取，金额和收款方按照活期账户收款方进行设置
				//收款方
				if (pi.getReceiveAccountID() > 0)
				{
					strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveAccountID());
					info.setReceiveAccountName(strReceiveAccountName);
					//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
					//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
	                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
	                {
	                	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID());
	                }
	                else
	                {
	                	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
	                }
					info.setReceiveAccountNo(strReceiveAccountNo);
					info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
					dAmount = DataFormat.formatDouble(pi.getAmount());
					  
					//对于定期支取和通知支取，金额栏位修改为不包含利息，20060214，gqfang
					/**
					if (pi.getReceiveInterestAccountID() > 0)
					{
						dAmount = DataFormat.formatDouble(dAmount + DataFormat.formatDouble(pi.getPayableInterest()));
						System.out.println("========dAmount2:" + dAmount);
					}
					**/ 
				}
				else
					if (pi.getReceiveInterestAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveInterestAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
						//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
		                {
		                	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveInterestAccountID());
		                }
		                else
		                {
		                	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
		                }
						info.setReceiveAccountNo(strReceiveAccountNo);
						info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
						dAmount = DataFormat.formatDouble(pi.getPayableInterest());
					}
				strAmount = DataFormat.formatAmount(dAmount);
				info.setAmount(strAmount);
				String strChineseAmount = ChineseCurrency.showChinese(strAmount, pi.getCurrencyID());
				info.setChineseAmount(strChineseAmount);
				strAmount = getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(dAmount);
				info.setAmount(strAmount);
			}
			else
				if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
				{
					//定期转存
					if (pi.getReceiveInterestAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveInterestAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
						//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
		                {
		                	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveInterestAccountID());
		                }
		                else
		                {
		                	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
		                }
						info.setReceiveAccountNo(strReceiveAccountNo);
						info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
					}
					
					if(pi.getIsCapitalAndInterestTransfer()==1){
						dAmount = DataFormat.formatDouble(pi.getRealInterest()+pi.getAmount());						
						strAmount = DataFormat.formatAmount(dAmount);
					}else{
						dAmount = DataFormat.formatDouble(pi.getRealInterest());						
						strAmount = DataFormat.formatAmount(dAmount);
					}
					
					info.setAmount(strAmount);
					String strChineseAmount = ChineseCurrency.showChinese(strAmount, pi.getCurrencyID());
					info.setChineseAmount(strChineseAmount);
					strAmount = getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(dAmount);
					info.setAmount(strAmount);
					
				}
				else //其他业务
					{
					if (pi.getReceiveAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
						//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
		                {
		                	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID());
		                }
		                else
		                {
		                	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
		                }
						info.setReceiveAccountNo(strReceiveAccountNo);
						info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
					}
					else
						if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
						{
							strReceiveAccountName = pi.getExtClientName();
							info.setReceiveAccountName(strReceiveAccountName);
							strReceiveAccountNo = DataFormat.formatString(pi.getExtAccountNo());
							info.setReceiveAccountNo(strReceiveAccountNo);
							info.setReceiveBankName(pi.getExtRemitInBank());
						}
						else //总账
							{
							strReceiveAccountName = NameRef.getGLTypeDescByID(pi.getReceiveGL());
							info.setReceiveAccountName(strReceiveAccountName);
							strReceiveAccountNo = NameRef.getGLTypeNoByID(pi.getReceiveGL());
							info.setReceiveAccountNo(strReceiveAccountNo);
						}
					if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
					{
						//如果是委托贷款收回,特转贷金额为贷款利息-利息税费(2004/3/31)
						pi.setAmount(
							DataFormat.formatDouble(
								DataFormat.formatDouble(pi.getAmount())
									+ DataFormat.formatDouble(pi.getRealInterest())
									+ DataFormat.formatDouble(pi.getRealOverDueInterest())
									+ DataFormat.formatDouble(pi.getRealCompoundInterest())
									- DataFormat.formatDouble(pi.getRealInterestTax())));
					}
					strAmount = DataFormat.formatAmount(pi.getAmount());
					info.setAmount(strAmount);
					String strChineseAmount = ChineseCurrency.showChinese(strAmount, pi.getCurrencyID());
					info.setChineseAmount(strChineseAmount);
					strAmount = getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getAmount());
					info.setAmount(strAmount);
				}
			String strInputUser = NameRef.getUserNameByID(pi.getInputUserID());
			info.setInputUserName(strInputUser);
			String strCheckUser = "";
			if (pi.getCheckUserID() > 0)
			{
				strCheckUser = NameRef.getUserNameByID(pi.getCheckUserID());
			}
			else
			{
				if (pi.getStatusID() == SETTConstant.TransactionStatus.CHECK)
				{
					strCheckUser = "机核";
				}
				else
				{
					strCheckUser = "&nbsp;&nbsp;&nbsp;&nbsp;";
				}
			}
			info.setCheckUserName(strCheckUser);
			
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
            
            String strAbstract = "起息日：" + DataFormat.formatDate(pi.getInterestStartDate()) + "；" + pi.getAbstract();
            info.setAbstract(strAbstract);
            
            IPrintTemplate.showCredit4(info, out);
            IPrintTemplate.showCredit42(info, out);
			
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
	 *	显示特种转账借方传票
	 * @throws Exception
	 */
	public static void PrintShowDebtor(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			ShowSpecialTransInfo info = new ShowSpecialTransInfo();
			String strAmount = "";
			String strChineseAmount = "";
			double dAmount = 0.0;
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
			info.setPayBankName(DataFormat.formatString(strPayBankName));
			String strReceiveBankName = "";
			info.setReceiveBankName(DataFormat.formatString(strReceiveBankName));
			String strPayAccountName = "";
			String strPayAccountNo = "";
			//付款方账户
			if (pi.getPayAccountID() > 0)
			{
				strPayAccountName = NameRef.getAccountNameByID(pi.getPayAccountID());
				info.setPayAccountName(DataFormat.formatString(strPayAccountName));
				//strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
				//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
                {
                	strPayAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getPayAccountID());
                }
                else
                {
                	strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
                }
				info.setPayAccountNo(strPayAccountNo);
				info.setPayBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			//外部银行账户
			else if(pi.getPayBankID() > 0)
			{
				Sett_BranchDAO ldao = new Sett_BranchDAO();
				BranchInfo linfo = new BranchInfo();
				linfo = ldao.findByID(pi.getPayBankID());
				info.setPayAccountName(DataFormat.formatString(linfo.getEnterpriseName()));
				info.setPayAccountNo(DataFormat.formatString(linfo.getBankAccountCode()));
				info.setPayBankName(DataFormat.formatString(linfo.getBranchName()));
			}
			else
				if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
				{
					strPayAccountName = pi.getExtClientName();
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
					strPayAccountNo = DataFormat.formatString(pi.getExtAccountNo());
					info.setPayAccountNo(strPayAccountNo);
					info.setPayBankName(pi.getExtRemitInBank());
				}
				else //总账
					{
					strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
					strPayAccountNo = NameRef.getGLTypeNoByID(pi.getPayGL());
					info.setPayAccountNo(strPayAccountNo);
				}
			//收款方账户
			String strReceiveAccountName = "";
			String strReceiveAccountNo = "";
			if (pi.getReceiveAccountID() > 0) 
			{
				strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveAccountID());
				info.setReceiveAccountName(strReceiveAccountName);
				//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
				//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
                {
                	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID());
                }
                else
                {
                	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
                }
				info.setReceiveAccountNo(strReceiveAccountNo);
				info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			else
				if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
				{
					strReceiveAccountName = pi.getExtClientName();
					info.setReceiveAccountName(strReceiveAccountName);
					strReceiveAccountNo = DataFormat.formatString(pi.getExtAccountNo());
					info.setReceiveAccountNo(strReceiveAccountNo);
					info.setReceiveBankName(pi.getExtRemitInBank());
				}
				else //总账
					{
					strReceiveAccountName = NameRef.getGLTypeDescByID(pi.getReceiveGL());
					info.setReceiveAccountName(strReceiveAccountName);
					strReceiveAccountNo = NameRef.getGLTypeNoByID(pi.getReceiveGL());
					info.setReceiveAccountNo(strReceiveAccountNo);
				}
			if (pi.getTransTypeID() == SETTConstant.TransactionType.DISCOUNTRECEIVE)
			{
				dAmount = DataFormat.formatDouble(DataFormat.formatDouble(pi.getAmount()) + DataFormat.formatDouble(pi.getOverDueAmount()));
				strAmount = getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(dAmount);
				System.out.print("金额-1:" + dAmount);
				System.out.print("金额1:" + strAmount);
			}
			else
			{
				dAmount = pi.getAmount();
				strAmount = getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getAmount());
				System.out.print("金额-2:" + dAmount);
				System.out.print("金额2:" + strAmount);
			}
			if (dAmount == 0.0)
			{
				info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + "0.0");
			}
			else
			{
				info.setAmount(strAmount);
				System.out.print("金额-3:" + dAmount);
				System.out.print("金额3:" + strAmount);
			}
			strChineseAmount = ChineseCurrency.showChinese(DataFormat.formatAmount(dAmount), pi.getCurrencyID());
			System.out.print("金额-4:" + dAmount);
			System.out.print("金额4:" + strAmount);
			info.setChineseAmount(strChineseAmount);
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
	
//	从PiInfo中根据编号取得相应的值
	//作者：feiye   2005-11-23
	private static String getValueForScodeFromPIVo(Object pInfo,Collection collXml,Object objPiInfo){
		//由于封装后的piInfo里的属性类型均为String
		String backInfo="";		//用于返回的值
		String propertyName="";	//该sCoce对应的PIVo属性名字
		try{
			//根据sCode 和找到piInfo里对应的属性名字（主要有一个配置属性文件)
			if(collXml!=null){
				for(int i=0;i<((ArrayList)collXml).size();i++){
					TemplateSettingXmlInfo info = (TemplateSettingXmlInfo)((ArrayList)collXml).get(i);
					//sCode 和 类型均相等
					if(info.getTemplateDetailCode().equals(((PrintOptionInfo)pInfo).m_strCode.trim()) && info.getTemplateType()==((PrintOptionInfo)pInfo).m_nPrintTemplateType){
						propertyName=info.getTemplateDetailVariable().trim();
						break;						
					}
				}
			}
			System.out.println("     =========得到配置XML中的相应的方法名为:"+propertyName);
			
		/***********根据属性的名字自动从piInfo里取得属性的值*************/
		//根据属性名字执行方法
			if(!propertyName.equals("") && !propertyName.equals("noVariable")){
				System.out.println("     =========执行类中此方法 get"+propertyName+"()");
				Method meth=objPiInfo.getClass().getMethod("get"+propertyName,new Class[]{});
				Object retobj=meth.invoke(objPiInfo,new Object[]{});
				backInfo=(String)retobj;
			}
			
		}catch (NoSuchMethodException e) {
			   backInfo="";
	       	   System.out.println("4=========在这个类"+objPiInfo.getClass()+"中没有此方法 get"+propertyName+"()");
	           System.err.println(e);
	    } catch(Exception e){
	    	backInfo="";
			e.printStackTrace();
		}
	    return backInfo;
	}
	
	
	/**
	 *	显示套打 共用程序
	 * @throws Exception
	 * (feiye改版)
	 */								
	public static void PrintTemplate(long lPrintTemplateID, PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			System.out.println("=============叶飞打印==============");
			PrintInfoVo PIVo=new PrintInfoVo();
			
			//从业务系统中得到的凭证INFO做相应的处理，以好对应实际的凭证明细元素内容
			//从Pi   到    PIVo  转换
			PIVo.setExecuteDate(DataFormat.formatDate(pi.getExecuteDate()));
			
			System.out.println("    执行日:"+pi.getExecuteDate());
			
			if (PIVo.getExecuteDate().length() < 10)
				PIVo.setExecuteDate("0000000000");			
			PIVo.setYear(PIVo.getExecuteDate().substring(0, 4));
			PIVo.setMonth(PIVo.getExecuteDate().substring(5, 7));
			PIVo.setDay(PIVo.getExecuteDate().substring(8, 10));
			
			PIVo.setTransNo(pi.getTransNo());

			if (pi.getPayAccountID() > 0)
			{
				//取由户的银行号 add by feiye 2006.5.16
				//PIVo.setPayAccountNo(DataFormat.formatString(NameRef.getBankAccountCodeByAccountIDandBankID(pi.getPayAccountID(), pi.getPayBankID())));
				PIVo.setPayBankName(DataFormat.formatString(IPrintTemplate.getBankAccountPrintNameByID(pi.getPayBankID())));
				PIVo.setPayProvince(DataFormat.formatString(IPrintTemplate.getBankAccountProvinceByID(pi.getPayBankID())));
				PIVo.setPayCity(DataFormat.formatString(IPrintTemplate.getBankAccountCityByID(pi.getPayBankID())));
				
				PIVo.setPayAccountName(DataFormat.formatString(NameRef.getAccountNameByID(pi.getPayAccountID())));
				PIVo.setPayAccountNo(DataFormat.formatString(NameRef.getAccountNoByID(pi.getPayAccountID()))); //用的是内部账户
				
				//如果是换开定期存单业务的话，取的是预算业务的银行号
				if(pi.getDepositBillNO()!=null && !pi.getDepositBillNO().equals("")){
					System.out.println(" :    是换开定期存单业务!");
					PIVo.setPayAccountName(DataFormat.formatString(NameRef.getBankFactAccountName(pi.getPayAccountID())));
					PIVo.setPayAccountNo(DataFormat.formatString(NameRef.getBankFactAccountNO(pi.getPayAccountID())));
				}
				
				System.out.println("1:得到的付款方账户号为:"+PIVo.getPayAccountNo()+"pi.getPayBankID()"+pi.getPayBankID()+"pi.getPayAccountID()"+pi.getPayAccountID());

			}
			else	//
			{
				PIVo.setPayAccountName(DataFormat.formatString(NameRef.getBankNameByID(pi.getPayBankID())));	//查外部行的名称
				PIVo.setPayAccountNo(DataFormat.formatString(NameRef.getBankAccountCodeByID(-1,pi.getPayBankID())));	//查外部行的账号
				
				PIVo.setPayBankName(DataFormat.formatString(pi.getExtRemitInBank()));
				PIVo.setPayProvince(DataFormat.formatString(pi.getExtRemitInProvince()));
				PIVo.setPayCity(DataFormat.formatString(pi.getPayExtRemitInCity()));
				
				System.out.println("2:得到的付款方账户号为:"+PIVo.getPayAccountNo()+"及付款方银行ID为:"+pi.getPayBankID());
			}
			
			if (pi.getReceiveAccountID() > 0)
			{
				PIVo.setReceiveAccountName(DataFormat.formatString(NameRef.getAccountNameByID(pi.getReceiveAccountID())));
				PIVo.setReceiveAccountNo(DataFormat.formatString(NameRef.getBankAccountCodeByAccountIDandBankID(pi.getReceiveAccountID(), pi.getReceiveBankID())));
				PIVo.setReceiveBankName(DataFormat.formatString(IPrintTemplate.getBankAccountPrintNameByID(pi.getReceiveBankID())));
				PIVo.setReceiveProvince(DataFormat.formatString(IPrintTemplate.getBankAccountProvinceByID(pi.getReceiveBankID())));
				PIVo.setReceiveCity(DataFormat.formatString(IPrintTemplate.getBankAccountCityByID(pi.getReceiveBankID())));
				
				System.out.println("1:得到的收款方账户号为:"+PIVo.getReceiveAccountNo()+"pi.getReceiveBankID()"+pi.getReceiveBankID()+"pi.getReceiveAccountID()"+pi.getReceiveAccountID());
			}
			else	//如果没有设置，就只接从PI里面取值
			{
				PIVo.setReceiveAccountName(DataFormat.formatString(pi.getExtClientName()));
				PIVo.setReceiveAccountNo(DataFormat.formatString(pi.getExtAccountNo()));
				PIVo.setReceiveBankName(DataFormat.formatString(pi.getExtRemitInBank()));
				PIVo.setReceiveProvince(DataFormat.formatString(pi.getExtRemitInProvince()));
				PIVo.setReceiveCity(DataFormat.formatString(pi.getExtRemitInCity()));
				System.out.println("2:得到的收款方账户号为:"+pi.getExtAccountNo());
			}
			
			PIVo.setAmount(DataFormat.formatAmount(pi.getAmount()));
			PIVo.setChineseAmount(ChineseCurrency.showChinese(PIVo.getAmount(), pi.getCurrencyID()));
			PIVo.setAmount(DataFormat.formatDisabledAmount(pi.getAmount()));
			PIVo.setInputUser(NameRef.getUserNameByID(pi.getInputUserID()));
			PIVo.setCheckUser(NameRef.getUserNameByID(pi.getCheckUserID()));
			PIVo.setAbstract(pi.getAbstract());
			//for 转贴现台账 start
			PIVo.setBillType(LOANConstant.DraftType.getName(pi.getBillTypeID()));
			PIVo.setDiscountBillNo(DataFormat.formatString(pi.getDiscountBillNo()));

			PIVo.setEndDate(DataFormat.formatDate(pi.getEndDate()));
			System.out.println("    结束日:"+pi.getEndDate());
			if (PIVo.getEndDate().length() < 10)
				PIVo.setEndDate("0000000000");	
			
			PIVo.setEndYear(PIVo.getEndDate().substring(0, 4));
			PIVo.setEndMonth(PIVo.getEndDate().substring(5, 7));
			PIVo.setEndDay(PIVo.getEndDate().substring(8, 10));	
			PIVo.setBillAcceptanceUser(DataFormat.formatString(pi.getBillAcceptanceUser()));
			PIVo.setAcceptanceUserAccount(DataFormat.formatString(pi.getAcceptanceUserAccount()));
			PIVo.setAcceptanceUserBank(DataFormat.formatString(pi.getAcceptanceUserBank()));
			PIVo.setDiscountBillAmount(DataFormat.formatDisabledAmount(pi.getDiscountBillAmount()));
			PIVo.setChineseDiscountBillAmount(ChineseCurrency.showChinese(String.valueOf(pi.getDiscountBillAmount())));			
			
			System.out.println("    利率:"+pi.getRate());

			PIVo.setRate(DataFormat.formatAmountUseZero(pi.getRate(),6));
			PIVo.setDiscountInterestAmount(DataFormat.formatDisabledAmount(pi.getDiscountInterestAmount()));
			PIVo.setDiscountAmount(DataFormat.formatDisabledAmount(pi.getDiscountAmount()));
			PIVo.setApplicantName(DataFormat.formatString(pi.getApplicantName()));
			PIVo.setApplicantAccountNo(DataFormat.formatString(pi.getApplicantAccountNo()));
			PIVo.setApplicantAccountBankNo(DataFormat.formatString(pi.getApplicantAccountBankNo()));
			
			
			//第二批需要添加的参数
			PIVo.setInterestStartDate(DataFormat.formatDate(pi.getInterestStartDate()));
			System.out.println("    起息日:"+pi.getInterestStartDate());
			if (PIVo.getInterestStartDate().length() < 10)
				PIVo.setInterestStartDate("0000000000");
			
			System.out.println("    定期期限:"+pi.getFixedDepositTerm());
			if(pi.getFixedDepositTerm()<0)
				PIVo.setFixedDepositTerm("");
			else
				PIVo.setFixedDepositTerm(String.valueOf(pi.getFixedDepositTerm()));
			
			
			/*
			System.out.println("-------pi.getReceiveAccountID():"+pi.getReceiveAccountID());
			System.out.println("-------pi.getReceiveAccountName():"+PIVo.getReceiveAccountName());
			System.out.println("-------pi.getReceiveAccountNo():"+PIVo.getReceiveAccountNo());
			System.out.println("-------pi.getReceiveBankName():"+PIVo.getReceiveBankName());
			System.out.println("-------pi.getPayAccountID():"+pi.getPayAccountID());
			System.out.println("-------pi.getPayAccountName():"+PIVo.getPayAccountName());
			System.out.println("-------pi.getPayAccountNo():"+PIVo.getPayAccountNo());
			System.out.println("-------pi.getPayBankName():"+PIVo.getPayBankName());
			*/
			
			//得到业务里面的币种信息		2006.3.26  为换开定期存单 feiye
			System.out.println("得到查到业务里的币种ID为:"+pi.getCurrencyID());
			PIVo.setCurrencyType( Constant.CurrencyType.getName(pi.getCurrencyID()) );
			System.out.println("根据转换方法得到查到业务里的币种名字为:"+PIVo.getCurrencyType());
			
			//得到业务里的换开定期存单号	2006.3.26  为换开定期存单 feiye
			System.out.println("=====pi.getDepositBillNO():"+pi.getDepositBillNO());
			if(pi.getDepositBillNO()!=null && !pi.getDepositBillNO().equals("")){
				PIVo.setDepositBillNO(pi.getDepositBillNO());
			}
			System.out.println("=====PIVo.getDepositBillNO():"+PIVo.getDepositBillNO());
			

			//得到业务里的换开定期存单号	2006.3.26  为换开定期存单 feiye
			System.out.println("=====pi.getTransTypeID():"+pi.getTransTypeID());
			if(pi.getTransTypeID()!=-1){
				PIVo.setTransactionType(SETTConstant.TransactionType.getName(pi.getTransTypeID()));
			}
			System.out.println("=====PIVo.getTransactionType():"+PIVo.getTransactionType());

			//得到业务里的开户证实书号	2006.3.27  为换开定期存单 feiye
			System.out.println("=====pi.getFixedDepositNo():"+pi.getFixedDepositNo());
			if(pi.getFixedDepositNo()!=null && !pi.getFixedDepositNo().equals("")){
				PIVo.setFixedDepositNo(pi.getFixedDepositNo());
			}
			System.out.println("=====PIVo.getFixedDepositNo():"+PIVo.getFixedDepositNo());
//			add by wjliu 2007-5-11
			//定期存单号
			if(pi.getFixedDepositNo()!=null && !pi.getFixedDepositNo().equals("")){
				PIVo.setFixedDepositNo(pi.getFixedDepositNo());
			}
//			实付利息,利息支付
			if(pi.getPayableInterest()!=0.0){
				PIVo.setPayableInterest(String.valueOf(new java.math.BigDecimal(pi.getPayableInterest())));
			}
//			本息合计TotalInterest 
			
				PIVo.setTotalInterest(String.valueOf(new java.math.BigDecimal (pi.getPayableInterest() +pi.getCurrentInterest() + pi.getOtherInterest() + pi.getAmount())));
				//账户号
				if(pi.getAccountNo()!=null && !pi.getAccountNo().equals(""))
				{
					PIVo.setAccountNo(pi.getAccountNo());
				}
				//账户名
				if(pi.getAccountName()!=null && !pi.getAccountName().equals(""))
				{
					PIVo.setAccountName(pi.getAccountName());
				}
			
			
			
			/*		这里没有做好应该做成项目判断  为四个代办处的多借多贷打印,但3.1里的多借多代也应该打印,暂时加个区别吧 2006.3.26 feiye
			//为一付多生成特殊的PIVO
			
			System.out.println("开始一付多收设置PIVO从PI里得到");
			System.out.println("得到PI里面的 pi.getPayBankID()"+pi.getPayBankID());
			System.out.println("得到PI里面的 pi.getReceiveBankID()"+pi.getReceiveBankID());
			
			if(pi.getPayBankID()!=-1){
				//付款方银行名称
				PIVo.setPayBankName(NameRef.getBankNameByID(pi.getPayBankID()));
				PIVo.setPayAccountNo(NameRef.getBankAccountCodeByAccountIDandBankID(-1,pi.getPayBankID()));
			}
			if(pi.getReceiveBankID()!=-1){
				//收款方银行名称
				PIVo.setReceiveBankName(NameRef.getBankNameByID(pi.getReceiveBankID()));
				PIVo.setReceiveAccountNo(NameRef.getBankAccountCodeByAccountIDandBankID(-1,pi.getReceiveBankID()));
			}
			System.out.println("    ==付款方银行名称"+PIVo.getPayBankName());
			System.out.println("    ==付款方银行账号"+PIVo.getPayAccountNo());
			System.out.println("    ==收款方银行名称"+PIVo.getReceiveBankName());
			System.out.println("    ==收款方银行账号"+PIVo.getReceiveAccountNo());
			*/
			
			
			
			System.out.println("取得pi参数完毕,生成PIVo完毕!");
			//for 转贴现台账 end
			
			/**********************************生成实际的打印页面*************************/
			//第一步:得到关于此模版所有的明细信息vPt:Vecor
			PrintTemplateBiz ptBiz=new PrintTemplateBiz();
			Vector vPt=new Vector();
			System.out.println("=========需要进行处理的打印模版ID:"+lPrintTemplateID);
			vPt=(Vector)ptBiz.findPrintOptionDetailsByTemplateID(lPrintTemplateID);

			//第二步:根据每条明明细信息中的sCode字段的值来做从pi中信息到明细中m_strDetailsData(测试数据)的填充
			PrintOptionInfo pInfo=new PrintOptionInfo();
			if(vPt!=null){
					//读取XML得到打印模版明细的配置信息
					TemplateSettingXml xml = new TemplateSettingXml();
					Collection collXML = null;
					collXML=xml.getTemplateSetting();
					if(collXML!=null)
						System.out.println("得到的XML配置信息集合的长度为："+collXML.size());
					
						//根据pInfo里面的sCode及类型得到collXML中的配置信息集合中的方法名，然后执行PIVo中的方法
					for(int i=0;i<vPt.size();i++){
						//得到一条打印明细info
						pInfo=(PrintOptionInfo)vPt.get(i);
						
						//如果从配置信息取不到相应的bean中的属性值的话，则打印在页面上的数据为空，即不显示
						pInfo.m_strDetailsData="";
						pInfo.m_strDetailsData=getValueForScodeFromPIVo(pInfo,collXML,PIVo);
						System.out.println("LOG:明细元素配置的sCode"+pInfo.m_strCode+"得到的返回值为:"+pInfo.m_strDetailsData);
				}
			}
			//第三步:将以上封装好的正确信息生成jsp页面，打印出来
			IPrintTemplate.showTemplate(out,vPt);
		}
		catch (Exception exp)
		{
			throw exp;
		}finally
		{
		}
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
			String currencyName="";
			String rate="";
			String ManagerName = ""; //单位主管
			ManagerName=pi.getManagerName();
			rate=String.valueOf(pi.getRate());
			String InterestStartDate=DataFormat.formatDate(pi.getInterestStartDate());
			String endDate=DataFormat.formatDate(pi.getEndDate());
			String FixedDepositTerm = ""; //定期期限
			FixedDepositTerm=String.valueOf(pi.getFixedDepositTerm());
			currencyName=Constant.CurrencyType.getName(pi.getCurrencyID());
			if (pi.getPayAccountID() > 0)
			{
				strPayAccountName = DataFormat.formatString(NameRef.getAccountNameByID(pi.getPayAccountID()));
				strPayAccountNo = DataFormat.formatString(NameRef.getBankAccountCodeByID(pi.getPayAccountID(), pi.getPayBankID()));
				strPayBankName = DataFormat.formatString(IPrintTemplate.getBankAccountPrintNameByID(pi.getPayBankID()));
				strPayProvince = DataFormat.formatString(IPrintTemplate.getBankAccountProvinceByID(pi.getPayBankID()));
				strPayCity = DataFormat.formatString(IPrintTemplate.getBankAccountCityByID(pi.getPayBankID()));
			}
			else
			{
				strPayAccountName = DataFormat.formatString(pi.getExtClientName());
				strPayAccountNo = DataFormat.formatString(pi.getExtAccountNo());
				strPayBankName = DataFormat.formatString(pi.getExtRemitInBank());
				strPayProvince = DataFormat.formatString(pi.getExtRemitInProvince());
				strPayCity = DataFormat.formatString(pi.getExtRemitInCity());
			}
			if (pi.getReceiveAccountID() > 0)
			{
				strReceiveAccountName = DataFormat.formatString(NameRef.getAccountNameByID(pi.getReceiveAccountID()));
				strReceiveAccountNo = DataFormat.formatString(NameRef.getBankAccountCodeByID(pi.getReceiveAccountID(), pi.getReceiveBankID()));
				strReceiveBankName = DataFormat.formatString(IPrintTemplate.getBankAccountPrintNameByID(pi.getReceiveBankID()));
				strReceiveProvince = DataFormat.formatString(IPrintTemplate.getBankAccountProvinceByID(pi.getReceiveBankID()));
				strReceiveCity = DataFormat.formatString(IPrintTemplate.getBankAccountCityByID(pi.getReceiveBankID()));
			}
			else
			{
				strReceiveAccountName = DataFormat.formatString(pi.getExtClientName());
				strReceiveAccountNo = DataFormat.formatString(pi.getExtAccountNo());
				strReceiveBankName = DataFormat.formatString(pi.getExtRemitInBank());
				strReceiveProvince = DataFormat.formatString(pi.getExtRemitInProvince());
				strReceiveCity = DataFormat.formatString(pi.getExtRemitInCity());
			}
			System.out.println("ReceiveAccountIDid==="+pi.getReceiveAccountID()+"pi.getReceiveBankID()="+pi.getReceiveBankID());
			
			String strAmount = DataFormat.formatAmount(pi.getAmount());
			String strChineseAmount = ChineseCurrency.showChinese(strAmount, pi.getCurrencyID());
			strAmount = DataFormat.formatDisabledAmount(pi.getAmount());
			String strInputUser = NameRef.getUserNameByID(pi.getInputUserID());
			String strCheckUser = NameRef.getUserNameByID(pi.getCheckUserID());
			String strAbstract = pi.getAbstract();
			switch (lPrintTemplateTypeID)
			{
				case 1 : //银行进账单
					String[] strCode1 = { "01", "02", "03", "04", "05", "08", "09", "10", "13", "14", "15", "16", "17", "19", "0026", "0029", "0027", "0031" };
					IPrintTemplate.showTemplate(
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
					IPrintTemplate.showTemplate(
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
					IPrintTemplate.showTemplate(
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
				case 4 : //定期存款单据
					String[] strCode4 = { "01", "02", "03", "20", "04", "05", "14", "35", "21", "22", "23", "24", "25", "0026", "0027", "0028" };//和sett_printtemplatedetails中的scode字段的数据一样
					IPrintTemplate.showTemplate(
						out,
						strCode4,
						lPrintTemplateID,
						strYear,
						strMonth,
						strDay,
						currencyName,
						strReceiveAccountName,
						NameRef.getAccountNoByID(pi.getReceiveAccountID()),
						strChineseAmount,
						strAmount,
						FixedDepositTerm,
						rate,
						InterestStartDate,
						endDate,
						"",
						ManagerName,
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
	/**
     *  自营贷款收回凭证打印 
     * @throws Exception
     */
	public static void PrintTrustRepaymentLoan(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			ShowRepaymentLoanInfo info = new ShowRepaymentLoanInfo();
			String strExecuteDate = DataFormat.formatDate(pi.getExecuteDate());
			String strRealInterest = "";
			String strRealCompoundInterest = "";
			String strRealOverDueInterest = "";
			if (pi.getRealInterest() == 0.0)
			{
				strRealInterest = "0.00";
			}
			else
			{
				strRealInterest = pi.getRealInterest() + "";
			}
			if (pi.getRealCompoundInterest() == 0.0)
			{
				strRealCompoundInterest = "0.00";
			}
			else
			{
				strRealCompoundInterest = pi.getRealCompoundInterest() + "";
			}
			if (pi.getRealOverDueInterest() == 0.0)
			{
				strRealOverDueInterest = "0.00";
			}
			else
			{
				strRealOverDueInterest = pi.getRealOverDueInterest() + "";
			}
			if (strExecuteDate.length() < 10)
			{
				strExecuteDate = "0000000000";
			}
			info.setYear(strExecuteDate.substring(0, 4));
			info.setMonth(strExecuteDate.substring(5, 7));
			info.setDay(strExecuteDate.substring(8, 10));
			info.setAbstract(
				pi.getAbstract()
					+ "已收正常利息："
					+ getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ strRealInterest
					+ "；已收复利："
					+ getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ strRealCompoundInterest
					+ "；已收罚息："
					+ getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ strRealOverDueInterest);
			info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getAmount()));
			info.setBillCode(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			info.setChineseAmount(ChineseCurrency.showChinese(DataFormat.formatAmount(pi.getAmount()), pi.getCurrencyID()));
			info.setContractCode(NameRef.getContractNoByID(pi.getContractID()));
			//合同利率
			LoanPayFormDetailInfo lpfdinfo = new LoanPayFormDetailInfo();
			lpfdinfo.setLoadNoteID(pi.getLoanNoteID());
			Sett_TransGrantLoanDAO stdao = new Sett_TransGrantLoanDAO();
			lpfdinfo = stdao.findPayFormDetailByCondition(lpfdinfo);
			info.setContractRate(DataFormat.formatDisabledAmount(lpfdinfo.getInterestRate()));
			//委托单位
			info.setConsignUnit(lpfdinfo.getClientName());
			//余额
			if (pi.getCurrentBalance() > 0)
			{
				info.setBalance(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getCurrentBalance()));
			}
			else
			{
				info.setBalance(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + "0.00");
			}
			//累计还款
			if ((lpfdinfo.getAmount() - pi.getCurrentBalance()) > 0)
			{
				info.setTotalRepayAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(lpfdinfo.getAmount() - pi.getCurrentBalance()));
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
			info.setLoanInterval(DataFormat.getChineseDateString(lpfdinfo.getStart()) + " 至 " + DataFormat.getChineseDateString(lpfdinfo.getEnd()));
			info.setLoanRate(DataFormat.formatDisabledAmount(pi.getRate()));
			info.setLoanType(LOANConstant.LoanType.getName(lpfdinfo.getLoanType()));
			info.setNote(pi.getAbstract());
			info.setOverDueInterest(DataFormat.formatDisabledAmount(pi.getRealOverDueInterest()));
			info.setRepaymentUnitName(NameRef.getClientNameByID(pi.getPayClientID()));
			info.setTotalInterest(DataFormat.formatDisabledAmount(pi.getRealInterest() + pi.getRealCompoundInterest() + pi.getRealOverDueInterest() + pi.getRealSuretyFee()));
			if (pi.getPayBankID() > 0)
			{
				info.setRepaymentBankName(NameRef.getBankNameByID(pi.getPayBankID()));
				info.setRepaymentAccountNo(IPrintTemplate.getBankAccountCodeByID(pi.getPayBankID()));
			}
			else
			{
				info.setRepaymentBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
				//info.setRepaymentAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
				//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
                {
                	info.setRepaymentAccountNo(NameRef.getOriginalAcctNoByAcctID(pi.getPayAccountID()));
                }
                else
                {
                	info.setRepaymentAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
                }
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
     *  委托贷款收回凭证打印
     * @throws Exception
    */
	public static void PrintConsignRepaymentLoan(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			ShowRepaymentLoanInfo info = new ShowRepaymentLoanInfo();
			String strExecuteDate = DataFormat.formatDate(pi.getExecuteDate());
			String strRealInterest = "";
			String strRealCompoundInterest = "";
			String strRealOverDueInterest = "";
			//将利息，复利，逾期利息值为零的显示格式
			if (pi.getRealInterest() == 0.0)
			{
				strRealInterest = "0.00";
			}
			else
			{
				strRealInterest = pi.getRealInterest() + "";
			}
			if (pi.getRealCompoundInterest() == 0.0)
			{
				strRealCompoundInterest = "0.00";
			}
			else
			{
				strRealCompoundInterest = pi.getRealCompoundInterest() + "";
			}
			if (pi.getRealOverDueInterest() == 0.0)
			{
				strRealOverDueInterest = "0.00";
			}
			else
			{
				strRealOverDueInterest = pi.getRealOverDueInterest() + "";
			}
			if (strExecuteDate.length() < 10)
			{
				strExecuteDate = "0000000000";
			}
			info.setYear(strExecuteDate.substring(0, 4));
			info.setMonth(strExecuteDate.substring(5, 7));
			info.setDay(strExecuteDate.substring(8, 10));
			info.setAbstract(
				pi.getAbstract()
					+ "已收正常利息："
					+ getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ strRealInterest
					+ "；已收复利："
					+ getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ strRealCompoundInterest
					+ "；已收罚息："
					+ getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ strRealOverDueInterest);
			info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getAmount()));
			info.setBillCode(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			info.setChargeRate(DataFormat.formatDisabledAmount(pi.getCommission()));
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			info.setChineseAmount(ChineseCurrency.showChinese(DataFormat.formatAmount(pi.getAmount()), pi.getCurrencyID()));
			info.setContractCode(NameRef.getContractNoByID(pi.getContractID()));
			//合同利率
			LoanPayFormDetailInfo lpfdinfo = new LoanPayFormDetailInfo();
			lpfdinfo.setLoadNoteID(pi.getLoanNoteID());
			Sett_TransGrantLoanDAO stdao = new Sett_TransGrantLoanDAO();
			lpfdinfo = stdao.findPayFormDetailByCondition(lpfdinfo);
			info.setContractRate(DataFormat.formatDisabledAmount(lpfdinfo.getInterestRate()));
			//委托单位
			info.setConsignUnit(lpfdinfo.getClientName());
			//余额info.setOverDueRate()
			if (pi.getCurrentBalance() > 0)
			{
				info.setBalance(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getCurrentBalance()));
			}
			else
			{
				info.setBalance(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + "0.00");
			}
			//累计还款
			if ((lpfdinfo.getAmount() - pi.getCurrentBalance()) > 0)
			{
				info.setTotalRepayAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(lpfdinfo.getAmount() - pi.getCurrentBalance()));
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
			info.setLoanInterval(DataFormat.getChineseDateString(lpfdinfo.getStart()) + " 至 " + DataFormat.getChineseDateString(lpfdinfo.getEnd()));
			info.setLoanRate(DataFormat.formatAmountUseZero(pi.getRate(),6));
			info.setLoanType(LOANConstant.LoanType.getName(lpfdinfo.getLoanType()));
			info.setNote(pi.getAbstract());
			info.setOverDueInterest(DataFormat.formatDisabledAmount(pi.getOverDueInterest()));
			//info.setOverDueRate();
			//付款账号
			//付款银行名称
			if (pi.getPayBankID() > 0)
			{
				info.setRepaymentBankName(NameRef.getBankNameByID(pi.getPayBankID()));
				info.setRepaymentAccountNo(IPrintTemplate.getBankAccountCodeByID(pi.getPayBankID()));
			}
			else
			{
				info.setRepaymentBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
				//info.setRepaymentAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
				//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
                {
                	info.setRepaymentAccountNo(NameRef.getOriginalAcctNoByAcctID(pi.getPayAccountID()));
                }
                else
                {
                	info.setRepaymentAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
                }
			}
			info.setRepaymentUnitName(NameRef.getClientNameByID(pi.getPayClientID()));
			info.setTotalInterest(DataFormat.formatDisabledAmount(pi.getRealInterest() + pi.getRealCompoundInterest() + pi.getRealOverDueInterest() + pi.getRealCommission()));
			info.setTransNo(pi.getTransNo());
			IPrintTemplate.showConsignRepaymentLoan1(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showConsignRepaymentLoan2(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showConsignRepaymentLoan3(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showConsignRepaymentLoan4(info, out);
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
     *  自营贷款发放凭证打印
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
			//info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
			//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
            {
            	info.setAccountNo(NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID()));
            }
            else
            {
            	info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
            }
			info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getAmount()));
			info.setBillCode(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			info.setChargeRate(DataFormat.formatDisabledAmount(pi.getRealCommission()));
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			info.setChineseAmount(ChineseCurrency.showChinese(DataFormat.formatAmount(pi.getAmount()), pi.getCurrencyID()));
			info.setContractCode(NameRef.getContractNoByID(pi.getContractID()));
			//合同利率
			LoanPayFormDetailInfo lpfdinfo = new LoanPayFormDetailInfo();
			lpfdinfo.setLoadNoteID(pi.getLoanNoteID());
			Sett_TransGrantLoanDAO stdao = new Sett_TransGrantLoanDAO();
			lpfdinfo = stdao.findPayFormDetailByCondition(lpfdinfo);
			//委托单位
			info.setConsignUnit(lpfdinfo.getClientName());
			info.setContractRate(DataFormat.formatRate(DataFormat.formatDisabledAmount(lpfdinfo.getInterestRate(), 2, 4)));
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			info.setLoanInterval(DataFormat.getChineseDateString(lpfdinfo.getStart()) + " 至 " + DataFormat.getChineseDateString(lpfdinfo.getEnd()));
			info.setLoanType(LOANConstant.LoanType.getName(lpfdinfo.getLoanType()));
			info.setLoanUnit(NameRef.getClientNameByID(pi.getReceiveClientID()));
			info.setNote(pi.getAbstract());
			//贷款账户号
			//info.setLoanAccountNo(NameRef.getAccountNoByID(pi.getLoanAccountID()));
			//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
            {
            	info.setLoanAccountNo(NameRef.getOriginalAcctNoByAcctID(pi.getLoanAccountID()));
            }
            else
            {
            	info.setLoanAccountNo(NameRef.getAccountNoByID(pi.getLoanAccountID()));
            }
			//借款单位，即自营贷款账户
			info.setLoanUnit(NameRef.getAccountNameByID(pi.getLoanAccountID()));
			if (pi.getReceiveAccountID() > 0)
			{
				//收款人账号
				//info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
				//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
	            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
	            {
	            	info.setAccountNo(NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID()));
	            }
	            else
	            {
	            	info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
	            }
				//收款人开户银行名称
				info.setOpenBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			else
				if (pi.getReceiveBankID() > 0)
				{
					//收款人账号
					info.setAccountNo(pi.getExtAccountNo());
					//收款人开户银行名称
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
     *  委托贷款发放凭证打印 
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
			//info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
			//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
            {
            	info.setAccountNo(NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID()));
            }
            else
            {
            	info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
            }
			info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getAmount()));
			info.setBillCode(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			info.setChineseAmount(ChineseCurrency.showChinese(DataFormat.formatAmount(pi.getAmount()), pi.getCurrencyID()));
			info.setContractCode(NameRef.getContractNoByID(pi.getContractID()));
			//合同利率
			LoanPayFormDetailInfo lpfdinfo = new LoanPayFormDetailInfo();
			lpfdinfo.setLoadNoteID(pi.getLoanNoteID());
			Sett_TransGrantLoanDAO stdao = new Sett_TransGrantLoanDAO();
			lpfdinfo = stdao.findPayFormDetailByCondition(lpfdinfo);
			//委托单位
			info.setConsignUnit(lpfdinfo.getClientName());
			info.setContractRate(DataFormat.formatRate(DataFormat.formatDisabledAmount(lpfdinfo.getInterestRate(), 2, 4)));
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			info.setLoanInterval(DataFormat.getChineseDateString(lpfdinfo.getStart()) + " 至 " + DataFormat.getChineseDateString(lpfdinfo.getEnd()));
			info.setLoanType(LOANConstant.LoanType.getName(lpfdinfo.getLoanType()));
			info.setLoanUnit(NameRef.getClientNameByID(pi.getReceiveClientID()));
			info.setNote(pi.getAbstract());
			//贷款账户号
			//info.setLoanAccountNo(NameRef.getAccountNoByID(pi.getLoanAccountID()));
			//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
            {
            	info.setLoanAccountNo(NameRef.getOriginalAcctNoByAcctID(pi.getLoanAccountID()));
            }
            else
            {
            	info.setLoanAccountNo(NameRef.getAccountNoByID(pi.getLoanAccountID()));
            }
			//借款单位，即自营贷款账户			
			info.setLoanUnit(NameRef.getAccountNameByID(pi.getLoanAccountID()));
			if (pi.getReceiveAccountID() > 0)
			{
				//收款人账号
				//info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
				//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
	            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
	            {
	            	info.setAccountNo(NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID()));
	            }
	            else
	            {
	            	info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
	            }
				//收款人开户银行名称
				info.setOpenBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			else
				if (pi.getReceiveBankID() > 0)
				{
					//收款人账号
					info.setAccountNo(pi.getExtAccountNo());
					//收款人开户银行名称
					info.setOpenBankName(pi.getExtRemitInBank());
				}
			//手续费率
			info.setChargeRate(DataFormat.formatDisabledAmount(lpfdinfo.getPoundage()));
			info.setTransNo(pi.getTransNo());
			IPrintTemplate.showConsignGrantLoan1(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showConsignGrantLoan2(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showConsignGrantLoan3(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showConsignGrantLoan4(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showConsignGrantLoan5(info, out);
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
     *  贴现发放凭证打印 
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
			info.setTransactionNo(pi.getTransNo());
			//凭证的findbyid方法
			DiscountCredenceInfo dcinfo = new DiscountCredenceInfo();
			TransDiscountDelegation transDiscountDelegation = new TransDiscountDelegation();
			dcinfo = transDiscountDelegation.findDiscountCredenceByID(pi.getDiscountNoteID());
			
			System.out.println("====befor===="+info.getAbstract());
			System.out.println("====贴现合同号===="+dcinfo.getDiscountContractCode());
			System.out.println("；贴现凭证编号：" + dcinfo.getCode());
			info.setAbstract(pi.getAbstract() + "；贴现合同号：" + dcinfo.getDiscountContractCode() + "；贴现凭证编号：" + dcinfo.getCode());
			System.out.println("=====after==="+info.getAbstract());
			info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getDiscountAmount()));
			//持票人信息
			if (pi.getReceiveAccountID() > 0)
			{
				//持票人名称
				info.setBillKeeperName(NameRef.getAccountNameByID(pi.getReceiveAccountID()));
				//持票人账号
				//info.setBillKeeperAccount(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
				//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
	            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
	            {
	            	info.setBillKeeperAccount(NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID()));
	            }
	            else
	            {
	            	info.setBillKeeperAccount(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
	            }
				//持票人开户银行名称
				info.setBillKeeperBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			else
				if (pi.getReceiveBankID() > 0)
				{
					//持票人名称
					//info.setBillKeeperName(NameRef.getBankNameByID(pi.getReceiveBankID()));
					info.setBillKeeperName(pi.getExtClientName());
					//持票人账号
					info.setBillKeeperAccount(pi.getExtAccountNo());
					//持票人开户银行名称
					info.setBillKeeperBankName(pi.getExtRemitInBank());
				}
			//特别说明:汇票出票人信息暂时不需要
			//汇票出票人账号
			info.setBillOutAccount("&nbsp;");
			info.setBillOutBankName("&nbsp;");
			//汇票出票人名称
			info.setBillOutName("&nbsp;");
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			info.setChineseAmount(ChineseCurrency.showChinese(DataFormat.formatAmount(pi.getDiscountAmount()), pi.getCurrencyID()));
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
			//汇票金额
			info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getDiscountBillAmount()));
			//汇票大写金额
			info.setChineseAmount(ChineseCurrency.showChinese(DataFormat.formatAmount(pi.getDiscountBillAmount()), pi.getCurrencyID()));
			//到期日
			info.setDateBillEnd(DataFormat.getChineseDateString(dcinfo.getAtTerm()));
			//出票日
			info.setDateBillOut(DataFormat.getChineseDateString(dcinfo.getPublicDate()));
			//贴现利息
			info.setDiscountInterest(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getDiscountInterestAmount()));
			//贴现号码
			info.setDiscountNo(dcinfo.getCode());
			//贴现率
			info.setDiscountRate(DataFormat.formatRate(DataFormat.formatDisabledAmount(dcinfo.getDiscountRate(), 2, 4)));
			//贴现种类
			info.setDiscountType(LOANConstant.DraftType.getName(dcinfo.getDraftTypeID()));
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			//实付贴现金额
			info.setRealPayDiscountAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getDiscountAmount()));
			IPrintTemplate.showDiscountGrantLoan1(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showDiscountGrantLoan2(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showDiscountGrantLoan3(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showDiscountGrantLoan4(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showDiscountGrantLoan5(info, out);
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
     *  贴现收回凭证打印 
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
			info.setTransactionNo(pi.getTransNo());
			if (pi.getDiscountAmount() > 0)
			{
				info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getDiscountAmount()));
			}
			else
			{
				info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + "0.00");
			}
			if (pi.getCurrentBalance() > 0)
			{
				info.setBalance(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getCurrentBalance()));
			}
			else
			{
				info.setBalance(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + "0.00");
			}
			info.setBillNo(NameRef.getDiscountBillNoByID(pi.getDiscountBillID()));
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			info.setChineseAmount(ChineseCurrency.showChinese(DataFormat.formatAmount(pi.getDiscountAmount()), pi.getCurrencyID()));
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
			info.setDateBankAccount(DataFormat.getChineseDateString(pi.getInterestStartDate()));
			//凭证的findbyid方法
			IPrintTemplate iPrintTemplate = new IPrintTemplate();
			DiscountCredenceInfo discountCredenceInfo = null;
			discountCredenceInfo = iPrintTemplate.findDiscountCredenceByID(pi.getDiscountNoteID());
			if (discountCredenceInfo != null)
			{
				info.setAbstract(pi.getAbstract() + "；贴现合同号：" + NameRef.getContractNoByID(pi.getContractID()) + "；贴现凭证编号：" + discountCredenceInfo.getCode());
				info.setBillType(LOANConstant.DraftType.getName(discountCredenceInfo.getDraftTypeID()));
				info.setDateDiscount(DataFormat.getChineseDateString(DataFormat.getDateTime(NameRef.getDiscountDateByDiscountBillID(pi.getDiscountBillID()))));
				info.setDateEnd(DataFormat.getChineseDateString(DataFormat.getDateTime(NameRef.getEndDateByDiscountBillID(pi.getDiscountBillID()))));
			}
			//info.setDiscountAccount(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
			//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
            {
            	info.setDiscountAccount(NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID()));
            }
            else
            {
            	info.setDiscountAccount(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
            }
			info.setDiscountName(NameRef.getAccountNameByID(pi.getReceiveAccountID()));
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			if ((IPrintTemplate.getDiscountTotalRepaymentAmount(pi.getDiscountNoteID()) - pi.getCurrentBalance()) > 0)
			{
				info.setTotalRepaymentAmount(
					getCurrencySymbolByCurrencyID(pi.getCurrencyID())
						+ DataFormat.formatDisabledAmount(IPrintTemplate.getDiscountTotalRepaymentAmount(pi.getDiscountNoteID()) - pi.getCurrentBalance()));
			}
			else
			{
				info.setTotalRepaymentAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + "0.00");
			}
			System.out.println("贴现收回金额:" + pi.getDiscountAmount());
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
	 * 存款利息通知单
	 * 大桥专用
	 * @param pi
	 * @param out
	 * @throws Exception
	 */
	public static void PrintDQDepositInterestNotice(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			
			/**
			 * 计算利息,是按每月30天计算,还是按实际天数计算
			 * true :按每月30天计算;false:按实际天数计算
			 */
			long intervalDaysFlag = -1;
			boolean bIsFact_InterestIntervalDays = false;
			bIsFact_InterestIntervalDays = Config.getBoolean(Config.SETT_INTEREST_INTERVALDAYS_ISFACT, false);
			if(bIsFact_InterestIntervalDays)
			{
				//按实际天数计算
				intervalDaysFlag = 1;
			}
			else
			{
				//按每月30天计算
				intervalDaysFlag = 2;
			}
			
			
			System.out.println("存款利息通知单");
			ShowPayInterestInfo info = new ShowPayInterestInfo();
			info.setCurrencyID(pi.getCurrencyID());
			info.setOfficeID(pi.getOfficeID());
			//年，月，日
			String strExecuteDate = DataFormat.formatDate(pi.getExecuteDate());
			if (strExecuteDate.length() < 10)
			{
				strExecuteDate = "0000000000";
			}
			info.setYear(strExecuteDate.substring(0, 4));
			info.setMonth(strExecuteDate.substring(5, 7));
			info.setDay(strExecuteDate.substring(8, 10));
			//借款单位名称,即付款方
			info.setClientName(NameRef.getClientNameByAccountID(pi.getPayAccountID()));
			//账号类型
			info.setAccountType(SETTConstant.AccountType.getName(pi.getAccountTypeID()));
			//交易编号
			info.setTransNo(pi.getTransNo());
			//账号
			//info.setAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
			//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
            {
            	info.setAccountNo(NameRef.getOriginalAcctNoByAcctID(pi.getPayAccountID()));
            }
            else
            {
            	info.setAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
            }
			//活期利息：起始日期，终息日期，天数，积数，利率，利息
			info.setCurrentInterestDateStart(DataFormat.formatDate(pi.getStartDate()));
			info.setCurrentInterestDateEnd(DataFormat.formatDate(pi.getInterestStartDate()));
			info.setCurrentInterestDay((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), 1) + intervalDaysFlag) + "");
			info.setCurrentIntegalAmount(
				DataFormat.formatDouble(
					2,
					DataFormat.formatDouble(IPrintTemplate.getTotalDailyAmountBanlance(pi.getPayAccountID(), pi.getStartDate(), pi.getInterestStartDate()))
						+ DataFormat.formatDouble(IPrintTemplate.getTotalDailyAccordAmountBanlance(pi.getPayAccountID(), pi.getStartDate(), pi.getInterestStartDate()))));
			info.setCurrentInterestRate(DataFormat.formatAmountUseZero(pi.getRate(),6));
			info.setCurrentInterest(DataFormat.formatDisabledAmount(pi.getCurrentInterest()));
			//协定利息：起始日期，终息日期，天数，积数，利率，利息
			if (pi.getAccordInterest() > 0.0)
			{
				info.setAccordInterestDateStart(DataFormat.formatDate(pi.getStartDate()));
				info.setAccordInterestDateEnd(DataFormat.formatDate(pi.getInterestStartDate()));
				info.setAccordInterestDay((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), 1) + intervalDaysFlag) + "");
				info.setAccordIntegalAmount(
					DataFormat.formatDouble(2, IPrintTemplate.getTotalDailyAccordAmountBanlance(pi.getPayAccountID(), pi.getStartDate(), pi.getInterestStartDate())));
				info.setAccordInterestRate(DataFormat.formatRate(pi.getAccordInterestRate()));
				info.setAccordInterest(DataFormat.formatDisabledAmount(pi.getAccordInterest()));
			}
			//利息总额
			info.setTotalInterest(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getCurrentInterest()) + DataFormat.formatDouble(pi.getAccordInterest())));
			//收息账户号
			//info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getReceiveInterestAccountID()));
			//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
            {
            	info.setInterestAccountNo(NameRef.getOriginalAcctNoByAcctID(pi.getReceiveInterestAccountID()));
            }
            else
            {
            	info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getReceiveInterestAccountID()));
            }
			//对应账户号
			//info.setCurrentAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
			//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
            {
            	info.setCurrentAccountNo(NameRef.getOriginalAcctNoByAcctID(pi.getPayAccountID()));
            }
            else
            {
            	info.setCurrentAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
            }
			//对应合同号
			info.setContractNo(NameRef.getContractNoByID(pi.getContractID()));
			//对应借据号
			info.setLoanBillNo(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			//对应存单号
			info.setDepositBillNo(pi.getFixedDepositNo()); //对应存单号
			//转账日
			info.setTransAccountDate(DataFormat.formatDate(pi.getExecuteDate()));
			//录入人
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			if (pi.getInputUserID() < 0)
			{
				info.setInputUserName("机制");
			}
			//复核人
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			if (pi.getCheckUserID() < 0)
			{
				if (pi.getStatusID() == SETTConstant.TransactionStatus.CHECK)
				{
					info.setCheckUserName("机核");
				}
				else
				{
					info.setCheckUserName("&nbsp;&nbsp;&nbsp;&nbsp");
				}
			}
			IPrintTemplate.showDQCurrentPayInterestNotice(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
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
	 * 存款利息通知单
	 * @param pi
	 * @param out
	 * @throws Exception
	 */
	public static void PrintDepositInterestNotice(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			/**
			 * 计算利息,是按每月30天计算,还是按实际天数计算
			 * true :按每月30天计算;false:按实际天数计算
			 */
			long intervalDaysFlag = -1;
			boolean bIsFact_InterestIntervalDays = false;
			bIsFact_InterestIntervalDays = Config.getBoolean(Config.SETT_INTEREST_INTERVALDAYS_ISFACT, false);
			if(bIsFact_InterestIntervalDays)
			{
				//按实际天数计算
				intervalDaysFlag = 1;
			}
			else
			{
				//按每月30天计算
				intervalDaysFlag = 2;
			}
			
			
			//System.out.println("存款利息通知单");
			ShowPayInterestInfo info = new ShowPayInterestInfo();
			info.setCurrencyID(pi.getCurrencyID());
			info.setOfficeID(pi.getOfficeID());
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
			//年，月，日
			String strExecuteDate = DataFormat.formatDate(pi.getExecuteDate());
			if (strExecuteDate.length() < 10)
			{
				strExecuteDate = "0000000000";
			}
			info.setYear(strExecuteDate.substring(0, 4));
			info.setMonth(strExecuteDate.substring(5, 7));
			info.setDay(strExecuteDate.substring(8, 10));
			//借款单位名称,即付款方
			info.setClientName(NameRef.getClientNameByAccountID(pi.getPayAccountID()));
			//账号类型
			info.setAccountType(SETTConstant.AccountType.getName(pi.getAccountTypeID()));
			//交易编号
			info.setTransNo(pi.getTransNo());
			//账号
			info.setAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));			
			//账户名称
			info.setAccountName(NameRef.getAccountNameByID(pi.getPayAccountID()));
			//活期利息：起始日期，终息日期，天数，本金，利率，利息
			info.setCurrentInterestDateStart(DataFormat.formatDate(pi.getStartDate()));
			info.setCurrentInterestDateEnd(DataFormat.formatDate(pi.getInterestStartDate()));
			info.setCurrentInterestDay((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), 1) + intervalDaysFlag) + "");
			info.setCurrentInterestAmount(
				DataFormat.formatDisabledAmount(
					IPrintTemplate.getCurrentAccountBanlance(pi.getPayAccountID(), pi.getStartDate(), pi.getInterestStartDate())
						/ (getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), intervalDaysFlag) + 1)));
			info.setCurrentInterestRate(DataFormat.formatAmountUseZero(pi.getRate(),6));
			info.setCurrentInterest(DataFormat.formatDisabledAmount(pi.getCurrentInterest()));
			//协定利息：起始日期，终息日期，天数，本金，利率，利息
			if (pi.getAccordInterest() > 0.0)
			{
				info.setAccordInterestDateStart(DataFormat.formatDate(pi.getStartDate()));
				info.setAccordInterestDateEnd(DataFormat.formatDate(pi.getInterestStartDate()));
				info.setAccordInterestDay((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), intervalDaysFlag) + 1) + "");
				info.setAccordInterestAmount(
					DataFormat.formatDisabledAmount(
						IPrintTemplate.getNegotiateBanlance(pi.getPayAccountID(), pi.getStartDate(), pi.getInterestStartDate())
							/ (getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), intervalDaysFlag) + 1)));
				info.setAccordInterestRate(DataFormat.formatRate(pi.getAccordInterestRate() + ""));
				info.setAccordInterest(DataFormat.formatDisabledAmount(pi.getAccordInterest()));
			}
			//利息总额
			info.setTotalInterest(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getCurrentInterest()) + DataFormat.formatDouble(pi.getAccordInterest())));
			//收息账户号
			info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getReceiveInterestAccountID()));
			//对应账户号
			info.setCurrentAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
			//对应合同号
			info.setContractNo(NameRef.getContractNoByID(pi.getContractID()));
			//对应借据号
			info.setLoanBillNo(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			//对应存单号
			info.setDepositBillNo(pi.getFixedDepositNo()); //对应存单号
			//转账日
			info.setTransAccountDate(DataFormat.formatDate(pi.getExecuteDate()));
			//录入人
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			if (pi.getInputUserID() < 0)
			{
				info.setInputUserName("机制");
			}
			//复核人
			if(pi.getCheckUserID() > 0)
			    info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			else
			{
				if (pi.getStatusID() == SETTConstant.TransactionStatus.CHECK)
				{
					info.setCheckUserName("机核");
				}
				else
				{
					info.setCheckUserName("&nbsp;&nbsp;&nbsp;&nbsp");
				}
			}
			System.out.print("天数1：" + info.getCurrentInterestDay());
			System.out.print("天数2：" + info.getAccordInterestDay());
            //修改 by kenny[2006-05-06] 确定活期存款业务按照三联打出

            NHCW_IPrintTemplate.showCurrentPayInterestNotice(info, out);
            out.println("<br clear=all style='page-break-before:always'>");
            NHCW_IPrintTemplate.showCurrentPayInterestNotice2(info, out);
            out.println("<br clear=all style='page-break-before:always'>");
            NHCW_IPrintTemplate.showCurrentPayInterestNotice3(info, out);

			out.println("<br clear=all style='page-break-before:always'>");
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
	*  利息通知单(结息单专用)打印
	* @throws Exception
	*/
	public static void PrintInterestNotice1(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			/**
			 * 计算利息,是按每月30天计算,还是按实际天数计算
			 * true :按每月30天计算;false:按实际天数计算
			 */
			long intervalDaysFlag = -1;
			boolean bIsFact_InterestIntervalDays = false;
			bIsFact_InterestIntervalDays = Config.getBoolean(Config.SETT_INTEREST_INTERVALDAYS_ISFACT, false);
			if(bIsFact_InterestIntervalDays)
			{
				//按实际天数计算
				intervalDaysFlag = 1;
			}
			else
			{
				//按每月30天计算
				intervalDaysFlag = 2;
			}

			ShowPayInterestInfo info = new ShowPayInterestInfo();
			info.setCurrencyID(pi.getCurrencyID());
			info.setOfficeID(pi.getOfficeID());
			String strExecuteDate = DataFormat.formatDate(pi.getExecuteDate());
			if (strExecuteDate.length() < 10)
			{
				strExecuteDate = "0000000000";
			}
			info.setYear(strExecuteDate.substring(0, 4));
			info.setMonth(strExecuteDate.substring(5, 7));
			info.setDay(strExecuteDate.substring(8, 10));
			//借款单位名称
			info.setClientName(pi.getBorrowClientName());
			//委托单位名称(需求变更2004/04/12改为账户名称)
			info.setConsignClientName(NameRef.getClientNameByID(pi.getConsignClientID()));
			//账号类型
			info.setAccountType(SETTConstant.AccountType.getName(pi.getAccountTypeID()));
			//交易编号
			info.setTransNo(pi.getTransNo());
			//账号&账户名称
			long lPayAccountID = -1;
			if (pi.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE || pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
			{
				if (pi.getReceiveAccountID() > 0)
				{
					lPayAccountID = pi.getReceiveAccountID();
					info.setAccountName(NameRef.getAccountNameByID(pi.getReceiveAccountID()));
					//info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
					//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
		            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
		            {
		            	info.setAccountNo(NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID()));
		            }
		            else
		            {
		            	info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
		            }
				}
				else
					if (pi.getPayAccountID() > 0)
					{
						lPayAccountID = pi.getPayAccountID();
						info.setAccountName(NameRef.getAccountNameByID(pi.getPayAccountID()));
						//info.setAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
						//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
			            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
			            {
			            	info.setAccountNo(NameRef.getOriginalAcctNoByAcctID(pi.getPayAccountID()));
			            }
			            else
			            {
			            	info.setAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
			            }
					}
			}
			else
			{
				lPayAccountID = pi.getPayAccountID();
				info.setAccountName(NameRef.getAccountNameByID(pi.getPayAccountID()));
				//info.setAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
				//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
	            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
	            {
	            	info.setAccountNo(NameRef.getOriginalAcctNoByAcctID(pi.getPayAccountID()));
	            }
	            else
	            {
	            	info.setAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
	            }
			}
			double normalInterestSum = 0.0;
			if (pi.getRealInterest() > 0)
			{
				//加入分段利息
				SubsectionInterest dao = new SubsectionInterest();
				SubsectionDateInfo PrintSubsectionInfo = new SubsectionDateInfo();
				PrintSubsectionInfo = dao.getSubsectionInterest(lPayAccountID, -1, pi.getLoanNoteID(), pi.getNormalInterestStart(), pi.getNormalInterestEnd());
				info.setNormalInterestDateStart(PrintSubsectionInfo.getPrintStartDate()); //正常利息开始日期
				info.setNormalInterestDateEnd(PrintSubsectionInfo.getPrintEndDate());
				info.setNormalInterestAmount(PrintSubsectionInfo.getPrintBalance()); //正常利息本金
				info.setNormalInterestRate(PrintSubsectionInfo.getPrintInterestRate()); //正常利息率
				info.setNormalInterest(PrintSubsectionInfo.getPrintInterest()); //正常利息
				info.setNormalInterestDay(PrintSubsectionInfo.getPrintDays());
				normalInterestSum = PrintSubsectionInfo.getNormalInterestSum();
				//加入分段利息End
			}
			//免还利息
			double remitInterest = DataFormat.formatDouble(normalInterestSum)-DataFormat.formatDouble(pi.getRealInterest());
			if (remitInterest > 0)
			{
				//免还利息起息日期
				//免还利息终息日期
				//免还利息天数
				//免还本金显示为空
				//免还利率
				//免还利息	
				info.setRemitInterest(DataFormat.formatDisabledAmount(remitInterest));
			}
			//复利
			//起始日期--放款日期后第一个复利设置日期，利率等于正常利息的利率
			if (pi.getRealCompoundInterest() > 0)
			{
				//复利利息起息日期
				if (pi.getCompoundInterestStart() != null)
				{
					info.setCompoundInterestDateStart(DataFormat.formatDate(pi.getCompoundInterestStart()));
				}
				//复利利息终息日期
				info.setCompoundInterestDateEnd(DataFormat.formatDate(pi.getCompoundInterestEnd()));
				//复利利息天数
				if (pi.getCompoundInterestStart() != null)
				{
					info.setCompoundInterestDay((getIntervalDays(pi.getCompoundInterestStart(), pi.getCompoundInterestEnd(), intervalDaysFlag) + 1) + "");
				}
				//复利本金显示为空
				info.setCompoundInterestAmount("&nbsp;");
				//复利利率
				info.setCompoundInterestRate(DataFormat.formatRate(pi.getCompoundRate() + ""));
				//复利利息	
				info.setCompoundInterest(DataFormat.formatDisabledAmount(pi.getRealCompoundInterest()));
			}
			//逾期罚息
			//起始日期罚息通知单的罚息日期/上一次结息的日期（根据不同情况），利率取罚息通知单的罚息日期
			if (pi.getRealOverDueInterest() > 0)
			{
				//逾期罚息利息起息日期
				if (pi.getOverDueStart() != null)
				{
					info.setOverInterestDateStart(DataFormat.formatDate(pi.getOverDueStart()));
				}
				//逾期罚息利息终息日期
				info.setOverInterestDateEnd(DataFormat.formatDate(pi.getOverDueEnd()));
				//逾期罚息利息天数
				if (pi.getOverDueStart() != null)
				{
					info.setOverInterestDay((getIntervalDays(pi.getOverDueStart(), pi.getOverDueEnd(), intervalDaysFlag) + 1) + "");
				}
				//逾期罚息本金
				info.setOverInterestAmount(DataFormat.formatDisabledAmount(pi.getOverDueAmount()));
				if (pi.getOverDueAmount() == 0.00)
				{
					info.setOverInterestAmount("0.00");
				}
				//逾期罚息利率
				info.setOverInterestRate(DataFormat.formatRate(pi.getOverDueRate() + ""));
				//逾期罚息利息
				info.setOverInterest(DataFormat.formatDisabledAmount(pi.getRealOverDueInterest()));
			}
			//手续费
			//起始日期取放款通知单的起始日期/上一次结算手续费的日期
			if (pi.getRealCommission() > 0)
			{
				//手续费利息起息日期
				info.setCommissionFeeDateStart(DataFormat.formatDate(pi.getCommissionStart()));
				//手续费利息终息日期
				info.setCommissionFeeDateEnd(DataFormat.formatDate(pi.getCommissionFeeEnd()));
				//手续费利息天数
				info.setCommissionFeeDay((getIntervalDays(pi.getCommissionStart(), pi.getCommissionFeeEnd(), intervalDaysFlag) + 1) + "");
				//手续费利息本金		
				info.setCommissionFeeAmount(DataFormat.formatDisabledAmount(pi.getCommissionAmount()));
				if (pi.getCommissionAmount() == 0.00)
				{
					info.setCommissionFeeAmount("0.00");
				}
				//手续费利率				
				info.setCommissionFeeRate(DataFormat.formatRate(pi.getCommissionRate() + ""));
				//手续费利息
				info.setCommissionFee(DataFormat.formatDisabledAmount(pi.getRealCommission()));
			}
			if (pi.getRealSuretyFee() > 0)
			{
				//担保费
				//起始日期取放款通知单的起始日期/上一次结算但报费的日期，利率从放款通知单取
				//担保费利息起息日期
				info.setAssureFeeDateStart(DataFormat.formatDate(pi.getSuretyFeeStart()));
				//担保费利息终息日期
				info.setAssureFeeDateEnd(DataFormat.formatDate(pi.getSuretyFeeEnd()));
				//担保费利息天数				
				info.setAssureFeeDay((getIntervalDays(pi.getSuretyFeeStart(), pi.getSuretyFeeEnd(), intervalDaysFlag) + 1) + "");
				//担保费利息本金		
				info.setAssureFeeAmount(DataFormat.formatDisabledAmount(pi.getSuretyFeeAmount()));
				if (pi.getSuretyFeeAmount() == 0.0)
				{
					info.setAssureFee("0.00");
				}
				//担保费利率
				info.setAssureFeeRate(DataFormat.formatRate(pi.getSuretyFeeRate() + ""));
				//担保费利息
				info.setAssureFee(DataFormat.formatDisabledAmount(pi.getRealSuretyFee()));
			}
			//利息总额
			info.setTotalInterest(
				DataFormat.formatDisabledAmount(
					DataFormat.formatDouble(pi.getRealInterest()) + DataFormat.formatDouble(pi.getRealCompoundInterest()) + DataFormat.formatDouble(pi.getRealOverDueInterest())));
			info.setTotalInterestChinese(
				ChineseCurrency.showChinese(
					DataFormat.formatAmount(
						DataFormat.formatDouble(pi.getRealInterest())
							+ DataFormat.formatDouble(pi.getRealCompoundInterest())
							+ DataFormat.formatDouble(pi.getRealOverDueInterest())),
					pi.getCurrencyID()));
			if (DataFormat.formatDouble(pi.getRealInterest()) + DataFormat.formatDouble(pi.getRealCompoundInterest()) + DataFormat.formatDouble(pi.getRealOverDueInterest())
				== 0.0)
			{
				info.setTotalInterest("0.00");
			}
			//利息账户号
			//info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getPayInterestAccountID()));
			//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
            {
            	info.setInterestAccountNo(NameRef.getOriginalAcctNoByAcctID(pi.getPayInterestAccountID()));
            }
            else
            {
            	info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getPayInterestAccountID()));
            }
			//对应的活期账户号
			//info.setCurrentAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
			//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
            {
            	info.setCurrentAccountNo(NameRef.getOriginalAcctNoByAcctID(pi.getPayAccountID()));
            }
            else
            {
            	info.setCurrentAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
            }
			//对应的合同号
			info.setContractNo(NameRef.getContractNoByID(pi.getContractID()));
			//对应借据号
			info.setLoanBillNo(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			//对应存单号
			info.setDepositBillNo(pi.getFixedDepositNo());
			//转账日
			info.setTransAccountDate(DataFormat.formatDate(pi.getExecuteDate()));
			//录入人
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			if (pi.getInputUserID() < 0)
			{
				info.setInputUserName("机制");
			}
			//复核人
			if(pi.getCheckUserID() > 0)
			    info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			else
			{
				if (pi.getStatusID() == SETTConstant.TransactionStatus.CHECK)
				{
					info.setCheckUserName("机核");
				}
				else
				{
					info.setCheckUserName("&nbsp;&nbsp;&nbsp;&nbsp");
				}
			}
			System.out.println("贷款类型:" + pi.getTransTypeID());
			if (pi.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE)
			{
				System.out.println("贷款类型－自营");
				 
            	IPrintTemplate.showTrustPayInterestNotice(info, out);
                out.println("<br clear=all style='page-break-before:always'>");
                IPrintTemplate.showTrustPayInterestNotice2(info, out);
                out.println("<br clear=all style='page-break-before:always'>");
                IPrintTemplate.showTrustPayInterestNotice3(info, out);
                out.println("<br clear=all style='page-break-before:always'>");
                IPrintTemplate.showTrustPayInterestNotice4(info, out);
                out.println("<br clear=all style='page-break-before:always'>");
                   
				 
			}
			else
				if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
				{
					System.out.println("贷款类型－委贷");
                    IPrintTemplate.showConsignPayInterestNotice(info, out);
                    out.println("<br clear=all style='page-break-before:always'>");
                    IPrintTemplate.showConsignPayInterestNotice2(info, out);
                    out.println("<br clear=all style='page-break-before:always'>");
                    IPrintTemplate.showConsignPayInterestNotice3(info, out);
                    out.println("<br clear=all style='page-break-before:always'>");
                    IPrintTemplate.showConsignPayInterestNotice4(info, out);
                    out.println("<br clear=all style='page-break-before:always'>");
                    IPrintTemplate.showConsignPayInterestNotice5(info, out);
					 
				}
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
	*  利息通知单(支取)打印
	* @throws Exception
	*/
	public static void PrintInterestNotice(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			 System.out.println("1111111111111111111111111111111");
			/**
			 * 计算利息,是按每月30天计算,还是按实际天数计算
			 * true :按每月30天计算;false:按实际天数计算
			 */
			long intervalDaysFlag = -1;
			boolean bIsFact_InterestIntervalDays = false;
			bIsFact_InterestIntervalDays = Config.getBoolean(Config.SETT_INTEREST_INTERVALDAYS_ISFACT, false);
			if(bIsFact_InterestIntervalDays)
			{
				//按实际天数计算
				intervalDaysFlag = 1;
			}
			else
			{
				//按每月30天计算
				intervalDaysFlag = 2;
			}
			
			System.out.println("2222222222222222222222222222222222222");
			ShowPayInterestInfo info = new ShowPayInterestInfo();
			info.setCurrencyID(pi.getCurrencyID());
			info.setOfficeID(pi.getOfficeID());
			String strExecuteDate = DataFormat.formatDate(pi.getExecuteDate());
			if (strExecuteDate.length() < 10)
			{
				strExecuteDate = "0000000000";
			}
			info.setYear(strExecuteDate.substring(0, 4));
			info.setMonth(strExecuteDate.substring(5, 7));
			info.setDay(strExecuteDate.substring(8, 10));
			//借款单位名称
			if (pi.getPayClientID() > 0)
			{
				info.setClientName(NameRef.getClientNameByID(pi.getPayClientID()));
			}
			else
			{
				info.setClientName(pi.getBorrowClientName());
			}
			System.out.println("33333333333333333333333333333333");
			//委托单位名称
			info.setConsignClientName(NameRef.getClientNameByID(pi.getConsignClientID()));
			//账号类型
			info.setAccountType(SETTConstant.AccountType.getName(pi.getAccountTypeID()));
			//交易编号
			info.setTransNo(pi.getTransNo());
			info.setReceiveAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
			//账号
			long lPayAccountID = -1;
			if (pi.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE || pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
			{
				//info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
				//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
	            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
	            {
	            	info.setAccountNo(NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID()));
	            }
	            else
	            {
	            	info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
	            }
				lPayAccountID = pi.getReceiveAccountID();
			}
			else
			{
				//info.setAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
				//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
	            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
	            {
	            	info.setAccountNo(NameRef.getOriginalAcctNoByAcctID(pi.getPayAccountID()));
	            }
	            else
	            {
	            	info.setAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
	            }
				lPayAccountID = pi.getPayAccountID();
			}
			//正常利息
			//加入分段利息
			SubsectionInterest dao = new SubsectionInterest();
			SubsectionDateInfo PrintSubsectionInfo = new SubsectionDateInfo();
			PrintSubsectionInfo =
				dao.getSubsectionInterest(lPayAccountID, -1, pi.getLoanNoteID(), pi.getLatestInterestClearDate(), DataFormat.getPreviousDate(pi.getInterestClearDate()));
			info.setNormalInterestDateStart(PrintSubsectionInfo.getPrintStartDate()); //正常利息开始日期
			info.setNormalInterestDateEnd(PrintSubsectionInfo.getPrintEndDate());
			info.setNormalInterestAmount(PrintSubsectionInfo.getPrintBalance()); //正常利息本金
			if (PrintSubsectionInfo.getPrintInterestRate() != null && PrintSubsectionInfo.getPrintInterestRate().length() > 0)
			{
				info.setNormalInterestRate(DataFormat.formatRate(Double.parseDouble(PrintSubsectionInfo.getPrintInterestRate()))); //正常利息率
			}
			info.setNormalInterest(PrintSubsectionInfo.getPrintInterest()); //正常利息
			info.setNormalInterestDay(PrintSubsectionInfo.getPrintDays());
			//复利
			if (pi.getRealCompoundInterest() > 0)
			{
				//复利利息起息日期
				if (pi.getCompoundInterestStart() != null)
				{
					info.setCompoundInterestDateStart(DataFormat.formatDate(pi.getCompoundInterestStart()));
				}
				//复利利息终息日期
				if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
				{
					info.setCompoundInterestDateEnd(DataFormat.formatDate(DataFormat.getPreviousDate(pi.getInterestClearDate())));
				}
				else
				{
					info.setCompoundInterestDateEnd(DataFormat.formatDate(pi.getInterestClearDate()));
				}
				//复利利息天数
				if (pi.getCompoundInterestStart() != null)
				{
					info.setCompoundInterestDay((getIntervalDays(pi.getCompoundInterestStart(), pi.getInterestStartDate(), intervalDaysFlag)) + "");
				}
				info.setCompoundInterestAmount("&nbsp;");
				//复利利率
				info.setCompoundInterestRate(DataFormat.formatRate(pi.getCompoundRate()));
				//复利利息	
				info.setCompoundInterest(DataFormat.formatDisabledAmount(pi.getRealCompoundInterest()));
				if (pi.getRealCompoundInterest() == 0.0)
				{
					info.setCompoundInterest("0.00");
				}
			}
			//逾期罚息
			if (pi.getRealOverDueInterest() > 0)
			{
				//逾期罚息利息起息日期
				if (pi.getOverDueStart() != null)
				{
					info.setOverInterestDateStart(DataFormat.formatDate(pi.getOverDueStart()));
				}
				//逾期罚息利息终息日期
				if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
				{
					info.setOverInterestDateEnd(DataFormat.formatDate(DataFormat.getPreviousDate(pi.getInterestClearDate())));
				}
				else
				{
					info.setOverInterestDateEnd(DataFormat.formatDate(pi.getInterestClearDate()));
				}
				//逾期罚息利息天数
				if (pi.getOverDueStart() != null)
				{
					info.setOverInterestDay((getIntervalDays(pi.getOverDueStart(), pi.getInterestClearDate(), intervalDaysFlag)) + "");
				}
				//逾期罚息本金
				info.setOverInterestAmount(DataFormat.formatDisabledAmount(pi.getOverDueAmount()));
				if (pi.getOverDueAmount() == 0.00)
				{
					info.setOverInterestAmount("0.00");
				}
				//逾期罚息利率
				info.setOverInterestRate(DataFormat.formatRate(pi.getOverDueRate()));
				//逾期罚息利息
				info.setOverInterest(DataFormat.formatDisabledAmount(pi.getRealOverDueInterest()));
				if (pi.getRealOverDueInterest() == 0.0)
				{
					info.setOverInterest("0.00");
				}
			}
			//手续费
			if (pi.getRealCommission() > 0)
			{
				//手续费利息起息日期
				info.setCommissionFeeDateStart(DataFormat.formatDate(pi.getCommissionStart()));
				//手续费利息终息日期
				if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
				{
					info.setCommissionFeeDateEnd(DataFormat.formatDate(DataFormat.getPreviousDate(pi.getInterestClearDate())));
				}
				else
				{
					info.setCommissionFeeDateEnd(DataFormat.formatDate(pi.getInterestClearDate()));
				}
				//手续费利息天数
				info.setCommissionFeeDay((getIntervalDays(pi.getCommissionStart(), pi.getInterestClearDate(), intervalDaysFlag)) + "");
				//手续费利息本金		
				info.setCommissionFeeAmount(DataFormat.formatDisabledAmount(pi.getAmount()));
				if (pi.getAmount() == 0.00)
				{
					info.setCommissionFeeAmount("0.00");
				}
				//手续费利率				
				System.out.print("========手续费利率：" + pi.getCommissionRate());
				info.setCommissionFeeRate(DataFormat.formatRate(pi.getCommissionRate()));
				//手续费利息
				info.setCommissionFee(DataFormat.formatDisabledAmount(pi.getRealCommission()));
				if (pi.getRealCommission() == 0.0)
				{
					info.setCommissionFee("0.00");
				}
			}
			if (pi.getRealSuretyFee() > 0)
			{
				//担保费
				//担保费利息起息日期
				info.setAssureFeeDateStart(DataFormat.formatDate(pi.getSuretyFeeStart()));
				//担保费利息终息日期
				if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
				{
					info.setAssureFeeDateEnd(DataFormat.formatDate(DataFormat.getPreviousDate(pi.getInterestClearDate())));
				}
				else
				{
					info.setAssureFeeDateEnd(DataFormat.formatDate(pi.getInterestClearDate()));
				}
				//担保费利息天数				
				info.setAssureFeeDay((getIntervalDays(pi.getSuretyFeeStart(), pi.getInterestClearDate(), intervalDaysFlag)) + "");
				//担保费利息本金		
				info.setAssureFeeAmount(DataFormat.formatDisabledAmount(pi.getAmount()));
				if (pi.getAmount() == 0.0)
				{
					info.setAssureFee("0.00");
				}
				//担保费利率
				info.setAssureFeeRate(DataFormat.formatRate(pi.getSuretyFeeRate() ));
				//担保费利息
				info.setAssureFee(DataFormat.formatDisabledAmount(pi.getRealSuretyFee()));
				if (pi.getRealSuretyFee() == 0.0)
				{
					info.setAssureFee("0.00");
				}
			}
			//利息总额
			info.setTotalInterest(
				DataFormat.formatDisabledAmount(
					DataFormat.formatDouble(pi.getRealInterest()) + DataFormat.formatDouble(pi.getRealCompoundInterest()) + DataFormat.formatDouble(pi.getRealOverDueInterest())));
			info.setTotalInterestChinese(
				ChineseCurrency.showChinese(
					DataFormat.formatAmount(
						DataFormat.formatDouble(pi.getRealInterest())
							+ DataFormat.formatDouble(pi.getRealCompoundInterest())
							+ DataFormat.formatDouble(pi.getRealOverDueInterest())),
					pi.getCurrencyID()));
			if (DataFormat.formatDouble(pi.getRealInterest()) + DataFormat.formatDouble(pi.getRealCompoundInterest()) + DataFormat.formatDouble(pi.getRealOverDueInterest())
				== 0.0)
			{
				info.setTotalInterest("0.00");
			}
			//利息账户号
			//info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getPayInterestAccountID()));
			//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
            {
            	info.setInterestAccountNo(NameRef.getOriginalAcctNoByAcctID(pi.getPayInterestAccountID()));
            }
            else
            {
            	info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getPayInterestAccountID()));
            }
			//对应的账户号
			//info.setCurrentAccountNo(NameRef.getAccountNoByID(lPayAccountID));
			//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
            {
            	info.setCurrentAccountNo(NameRef.getOriginalAcctNoByAcctID(lPayAccountID));
            }
            else
            {
            	info.setCurrentAccountNo(NameRef.getAccountNoByID(lPayAccountID));
            }
			//对应的合同号
			info.setContractNo(NameRef.getContractNoByID(pi.getContractID()));
			//对应借据号
			info.setLoanBillNo(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			//对应存单号
			info.setDepositBillNo(pi.getFixedDepositNo());
			//转账日
			info.setTransAccountDate(DataFormat.formatDate(pi.getInterestStartDate()));
			//录入人
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			if (pi.getInputUserID() < 0)
			{
				info.setInputUserName("机制");
			}
			//复核人
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			if (pi.getCheckUserID() < 0)
			{
				if (pi.getStatusID() == SETTConstant.TransactionStatus.CHECK)
				{
					info.setCheckUserName("机核");
				}
				else
				{
					info.setCheckUserName("&nbsp;&nbsp;&nbsp;&nbsp");
				}
			}
			System.out.println("贷款类型:" + pi.getTransTypeID());
			if (pi.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE)
			{
				System.out.println("贷款类型－自营");
                IPrintTemplate.showTrustPayInterestNoticeZJ(info, out);
				
			}
			else if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
			{
                IPrintTemplate.showConsignPayInterestNotice(info, out);
			}
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
     *  通知存款开户证实书打印 
     * @throws Exception
     */
	public static void PrintNoticeOpen(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			ShowNoticeOpenInfo info = new ShowNoticeOpenInfo();
			info.setCurrencyID(pi.getCurrencyID());
			info.setOfficeID(pi.getOfficeID());
			info.setAccountName(NameRef.getAccountNameByID(pi.getReceiveAccountID()));
			//info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
			//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
            {
            	info.setAccountNo(NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID()));
            }
            else
            {
            	info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
            }
			info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getAmount()));
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			info.setChineseAmount(ChineseCurrency.showChinese(DataFormat.formatAmount(pi.getAmount()), pi.getCurrencyID()));
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
			info.setDateOpenAccount(DataFormat.getChineseDateString(pi.getExecuteDate()));
			info.setDepositBillNo(pi.getFixedDepositNo());
			info.setTransNo(pi.getTransNo());
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
	 * 
	 * @param pi
	 * @param out
	 * @throws Exception
	 */
	public static void PrintChangeDepositOpen(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			TransFixedOpenInfo piTemp=new TransFixedOpenInfo();
			Sett_TransOpenFixedDepositDAO delegation1=new Sett_TransOpenFixedDepositDAO();
			ShowFixedDepositOpenInfo info = new ShowFixedDepositOpenInfo();
			piTemp = delegation1.findByDepositNo(pi.getOldDepositNo());
			info.setAccountName(NameRef.getAccountNameByID(pi.getAccountID()));
			info.setTransNo(pi.getTransNo());
			info.setDepositBillNo(pi.getNewDepositNo());
			info.setAccountNo(NameRef.getAccountNoByID(pi.getAccountID()));
			info.setDateOpenAccount(DataFormat.getChineseDateString(pi.getEffectiveDate()));
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
			info.setChineseAmount(ChineseCurrency.showChinese(DataFormat.formatAmount(piTemp.getAmount()), pi.getCurrencyID()));
			info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(piTemp.getAmount()));
			info.setRate(DataFormat.formatAmountUseZero(piTemp.getRate(),6));
			
			String sInterval = "";
			
			if(piTemp.getDepositTerm()>0){
				sInterval=String.valueOf(piTemp.getDepositTerm())+"个月";
			}else{
				sInterval="0个月";
			}
			
			info.setInterval(sInterval);
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			
			IPrintTemplate.showFixOpenAccountNoticeForChange1(info, out);
            out.println("<br clear=all style='page-break-before:always'>");
            IPrintTemplate.showFixOpenAccountNoticeForChange2(info, out);
            out.println("<br clear=all style='page-break-before:always'>");
            IPrintTemplate.showFixOpenAccountNoticeForChange3(info, out);
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
     *  定期存款开户证实书打印 
     * @throws Exception
     */
	public static void PrintFixedDepositOpen(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			ShowFixedDepositOpenInfo info = new ShowFixedDepositOpenInfo();
			info.setAccountName(NameRef.getAccountNameByID(pi.getReceiveAccountID()));
			//info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
			//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//如果是南航项目
            {
            	info.setAccountNo(NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID()));
            }
            else
            {
            	info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
            }
			if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
			{
				if (pi.getReceiveInterestAccountID() > 0 || (pi.getExtAccountNo() != null && pi.getExtAccountNo().length() > 0))
				{
					pi.setAmount(DataFormat.formatDouble(pi.getAmount()));
				}
				else
				{
					pi.setAmount(DataFormat.formatDouble(pi.getAmount()) + DataFormat.formatDouble(pi.getRealInterest()));
				}
			}
			info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getAmount()));
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			info.setChineseAmount(ChineseCurrency.showChinese(DataFormat.formatAmount(pi.getAmount()), pi.getCurrencyID()));
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
			info.setDateOpenAccount(DataFormat.getChineseDateString(pi.getInterestStartDate()));
			info.setDepositBillNo(pi.getFixedDepositNo());
			info.setTransNo(pi.getTransNo());
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			info.setStartInterestDate(DataFormat.getChineseDateString(pi.getInterestStartDate()));
			info.setEndDate(DataFormat.getChineseDateString(pi.getEndDate()));			
			info.setAbstract(pi.getAbstract());
			
			//added by mzh_fu 2007/04/11
			info.setStartDate(DataFormat.getChineseDateString(pi.getStartDate()));	
			
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
			if(pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
			{
			    info.setRate(DataFormat.formatDisabledAmount(pi.getNewRate(),1,4));
			}
			else
			{
				info.setRate(DataFormat.formatAmountUseZero(pi.getRate(),6));
			}
			
            IPrintTemplate.showFixOpenAccountNotice1(info, out);
            out.println("<br clear=all style='page-break-before:always'>");
            IPrintTemplate.showFixOpenAccountNotice2(info, out);
			
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
	 * liuchuan
	 * @param 保证金结息打印
	 * @param out
	 * @throws Exception
	 */
	public static void PrintMarGinDepositInterest(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			String strTemp = "";
			ShowDepositInterestInfo info = new ShowDepositInterestInfo();
			String strExecuteDate = DataFormat.formatDate(pi.getExecuteDate());
			if (strExecuteDate.length() < 10)
			{
				strExecuteDate = "0000000000";
			}
			info.setYear(strExecuteDate.substring(0, 4));
			info.setMonth(strExecuteDate.substring(5, 7));
			info.setDay(strExecuteDate.substring(8, 10));
			info.setClientName(NameRef.getClientNameByAccountID(pi.getAccountID()));
			info.setTransNo(pi.getTransNo());
			info.setAccountNo(NameRef.getAccountNoByID(pi.getAccountID()));
			info.setDepositTypeName("保证金结息");
//			info.setDepositBillNo(pi.getBillNo());
			info.setAmount1(DataFormat.formatAmount(pi.getBaseBalance()));
			System.out.println("取值interest:%%%"+pi.getInterest());
			info.setInterest1("￥" + DataFormat.formatAmount(pi.getInterest()));
			info.setRate1(DataFormat.formatAmountUseZero(pi.getRate(),6));
			System.out.println("利率1" + info.getRate1());
			
			strTemp = DataFormat.formatDate(pi.getInterestStart());
			info.setBeginYear1(strTemp.substring(0, 4));
			info.setBeginMonth1(strTemp.substring(5, 7));
			info.setBeginDay1(strTemp.substring(8, 10));
			strTemp = DataFormat.formatDate(pi.getInterestEnd());
			info.setOverYear1(strTemp.substring(0, 4));
			info.setOverMonth1(strTemp.substring(5, 7));
			info.setOverDay1(strTemp.substring(8, 10));
			info.setDayNumber1(String.valueOf(pi.getInterestDays()));
			
			IPrintTemplate.showDepositInterest1(info, out);
            out.println("<br clear=all style='page-break-before:always'>");
            IPrintTemplate.showDepositInterest2(info, out);
            out.println("<br clear=all style='page-break-before:always'>");
            IPrintTemplate.showDepositInterest3(info, out);
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
     *  存款利息计付通知单(用于通知存款 定期存款的收回凭证) 
     * @throws Exception
     */
	public static void PrintDepositInterest(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			/**
			 * 计算利息,是按每月30天计算,还是按实际天数计算
			 * true :按每月30天计算;false:按实际天数计算
			 */
			long intervalDaysFlag = -1;
			boolean bIsFact_InterestIntervalDays = false;
			bIsFact_InterestIntervalDays = Config.getBoolean(Config.SETT_INTEREST_INTERVALDAYS_ISFACT, false);

			if(bIsFact_InterestIntervalDays)
			{
				//按实际天数计算
				intervalDaysFlag = 1;
			}
			else
			{
				//按每月30天计算
				intervalDaysFlag = 2;
			}
			ShowDepositInterestInfo info = new ShowDepositInterestInfo();
			String strExecuteDate = DataFormat.formatDate(pi.getExecuteDate());
			if (strExecuteDate.length() < 10)
			{
				strExecuteDate = "0000000000";
			}
			info.setYear(strExecuteDate.substring(0, 4));
			info.setMonth(strExecuteDate.substring(5, 7));
			info.setDay(strExecuteDate.substring(8, 10));
			//info.setAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
			//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
            if( Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )
            {
                info.setAccountNo(NameRef.getOriginalAcctNoByAcctID(pi.getPayAccountID()));
            }
            else
            {
            	info.setAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
            }
			
			info.setClientName(NameRef.getClientNameByAccountID(pi.getPayAccountID()));
			//取上次计提日期，作为计提日期的结束日期××××××××××××××
			SubAccountFixedInfo subAccountFixedInfo = null;
			SubAccountAssemblerInfo subAccountAssemblerInfo = null;
			Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO();
			Timestamp tsLastPreDraw = null;
			subAccountAssemblerInfo = sett_SubAccountDAO.findByID(pi.getSubAccountID());
			if (subAccountAssemblerInfo != null)
			{
				subAccountFixedInfo = subAccountAssemblerInfo.getSubAccountFixedInfo();
			}
			if (subAccountFixedInfo != null)
			{
				tsLastPreDraw = subAccountFixedInfo.getPreDrawDate();
			}
			//×××××××××××××××××
			info.setDepositBillNo(pi.getBillNo());
			/*
			//计提与计息之间的转换[定期、通知]
			String normalInterestStart = DataFormat.formatDate(pi.getNormalInterestStart());
			String normalInterestEnd = DataFormat.formatDate(pi.getNormalInterestEnd());
			if (normalInterestStart != null && normalInterestStart.trim().length()>0) {
				pi.setStartDate(pi.getNormalInterestStart());
			}
			if (normalInterestEnd != null && normalInterestEnd.trim().length()>0) {
				pi.setEndDate(pi.getNormalInterestEnd());
			}
			*/

			String strTemp = "";
			if (pi.getCurrentInterest() != 0.0) //有活期利息
			{
				if ( pi.getInterestStartDate() != null && pi.getEndDate() != null && pi.getInterestStartDate().before(pi.getEndDate()) ) //如起息日在终止日期之前，即提前支取
				{
					//本金1，活期利息，活期利息利率，开始日期，结束日期，天数
					info.setAmount1(DataFormat.formatAmount(pi.getAmount()));
					info.setInterest1(DataFormat.formatAmount(pi.getCurrentInterest()));
					info.setRate1(DataFormat.formatAmount(Config.getDouble(ConfigConstant.CURRENCY_INTEREST_RATE,0.98)));
					strTemp = DataFormat.formatDate(pi.getStartDate());
					info.setBeginYear1(strTemp.substring(0, 4));
					info.setBeginMonth1(strTemp.substring(5, 7));
					info.setBeginDay1(strTemp.substring(8, 10));
					strTemp = DataFormat.formatDate(pi.getInterestStartDate());
					info.setOverYear1(strTemp.substring(0, 4));
					info.setOverMonth1(strTemp.substring(5, 7));
					info.setOverDay1(strTemp.substring(8, 10));
					if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER || pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
					{
						//定期支取&定期续存
						info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), intervalDaysFlag)) + "");
					}
					else
						if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
						{
							//通知支取
							info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), intervalDaysFlag)) + "");
						}
						else
						{
							info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), intervalDaysFlag) + 1) + "");
						}
					//本金2，其它利息(可能有)，其它利息利率(无)，开始日期，结束日期，天数
					if (pi.getOtherInterest() != 0.0)
					{
						info.setAmount2(DataFormat.formatAmount(pi.getAmount()));
						info.setInterest2(DataFormat.formatAmount(pi.getOtherInterest()));
						strTemp = DataFormat.formatDate(pi.getStartDate());
						info.setBeginYear2(strTemp.substring(0, 4));
						info.setBeginMonth2(strTemp.substring(5, 7));
						info.setBeginDay2(strTemp.substring(8, 10));
						strTemp = DataFormat.formatDate(pi.getInterestStartDate());
						info.setOverYear2(strTemp.substring(0, 4));
						info.setOverMonth2(strTemp.substring(5, 7));
						info.setOverDay2(strTemp.substring(8, 10));
						if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER
							|| pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
						{
							//定期支取&定期续存
							info.setDayNumber2((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), intervalDaysFlag)) + "");
						}
						else
							if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
							{
								//通知支取
								info.setDayNumber2((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), intervalDaysFlag)) + "");
							}
							else
							{
								info.setDayNumber2((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), 1) + intervalDaysFlag) + "");
							}
					}
					//利息合计
					info.setTotalInterest(
						DataFormat.formatAmount(DataFormat.formatDouble(DataFormat.formatDouble(pi.getCurrentInterest()) + DataFormat.formatDouble(pi.getOtherInterest()))));
				}
				else //如起息日在终止日期之后
					{
					//本金1，利息支出，利息支出利率，开始日期，结束日期，天数
					info.setAmount1(DataFormat.formatAmount(pi.getAmount()));
					info.setInterest1(
						DataFormat.formatAmount(
							DataFormat.formatDouble(
								DataFormat.formatDouble(pi.getPayableInterest())
									- DataFormat.formatDouble(pi.getCurrentInterest())
									- DataFormat.formatDouble(pi.getOtherInterest()))));
					info.setRate1(DataFormat.formatAmountUseZero(pi.getRate(),6));
					strTemp = DataFormat.formatDate(pi.getStartDate());
					if (strTemp != null && strTemp.trim().length()>0) {
						info.setBeginYear1(strTemp.substring(0, 4));
						info.setBeginMonth1(strTemp.substring(5, 7));
						info.setBeginDay1(strTemp.substring(8, 10));
					}
					strTemp = DataFormat.formatDate(pi.getEndDate());
					if (strTemp != null && strTemp.trim().length()>0) {
						info.setOverYear1(strTemp.substring(0, 4));
						info.setOverMonth1(strTemp.substring(5, 7));
						info.setOverDay1(strTemp.substring(8, 10));
					}
					if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER || pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
					{
						//定期支取&定期续存
						info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), intervalDaysFlag)) + "");
					}
					else
						if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
						{
							//通知支取
							info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), intervalDaysFlag)) + "");
						}
						else
						{
							info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), intervalDaysFlag) + 1) + "");
						}
					//本金2，活期利息，活期利息利率，开始日期，结束日期，天数
					info.setAmount2(DataFormat.formatAmount(pi.getAmount()));
					info.setInterest2(DataFormat.formatAmount(pi.getCurrentInterest()));
					info.setRate2(DataFormat.formatAmount(Config.getDouble(ConfigConstant.CURRENCY_INTEREST_RATE,0.98)));
					strTemp = DataFormat.formatDate(pi.getEndDate());
					if (strTemp != null && strTemp.trim().length()>0) {
						info.setBeginYear2(strTemp.substring(0, 4));
						info.setBeginMonth2(strTemp.substring(5, 7));
						info.setBeginDay2(strTemp.substring(8, 10));
					}
					strTemp = DataFormat.formatDate(pi.getInterestStartDate());
					if (strTemp != null && strTemp.trim().length()>0) {
						info.setOverYear2(strTemp.substring(0, 4));
						info.setOverMonth2(strTemp.substring(5, 7));
						info.setOverDay2(strTemp.substring(8, 10));
					}
					if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER || pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
					{
						//定期支取&定期续存
						info.setDayNumber2((getIntervalDays(pi.getEndDate(), pi.getInterestStartDate(), intervalDaysFlag)) + "");
					}
					else
						if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
						{
							//通知支取
							info.setDayNumber2((getIntervalDays(pi.getEndDate(), pi.getInterestStartDate(), intervalDaysFlag)) + "");
						}
						else
						{
							info.setDayNumber2((getIntervalDays(pi.getEndDate(), pi.getInterestStartDate(), intervalDaysFlag) + 1) + "");
						}
					if (pi.getOtherInterest() != 0.0) //其它利息
					{
						//本金3，其它利息(可能有)，其它利息利率(无)，开始日期，结束日期，天数
						info.setAmount3(DataFormat.formatAmount(pi.getAmount()));
						info.setInterest3(DataFormat.formatAmount(pi.getOtherInterest()));
						strTemp = DataFormat.formatDate(pi.getEndDate());
						if (strTemp != null && strTemp.trim().length()>0) {
							info.setBeginYear3(strTemp.substring(0, 4));
							info.setBeginMonth3(strTemp.substring(5, 7));
							info.setBeginDay3(strTemp.substring(8, 10));
						}
						strTemp = DataFormat.formatDate(pi.getInterestStartDate());
						if (strTemp != null && strTemp.trim().length()>0) {
							info.setOverYear3(strTemp.substring(0, 4));
							info.setOverMonth3(strTemp.substring(5, 7));
							info.setOverDay3(strTemp.substring(8, 10));
						}
						if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER
							|| pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
						{
							//定期支取&定期续存
							info.setDayNumber3((getIntervalDays(pi.getEndDate(), pi.getInterestStartDate(), intervalDaysFlag)) + "");
						}
						else
							if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
							{
								//通知支取
								info.setDayNumber3((getIntervalDays(pi.getEndDate(), pi.getInterestStartDate(), intervalDaysFlag)) + "");
							}
							else
							{
								info.setDayNumber3((getIntervalDays(pi.getEndDate(), pi.getInterestStartDate(), intervalDaysFlag) + 1) + "");
							}
					}
					//利息合计
					info.setTotalInterest(DataFormat.formatAmount(DataFormat.formatDouble(pi.getPayableInterest())));
				}
			}
			else //没有活期利息
				{
				Timestamp tsStartDate = pi.getStartDate();
				Timestamp tsEndDate = null;
				double rate = 0.0;
				double interest = 0.0;
				if (pi.getExecuteDate().compareTo(pi.getStartDate()) == 0) {
					tsEndDate = pi.getExecuteDate();
					rate = Config.getDouble(ConfigConstant.CURRENCY_INTEREST_RATE,0.98);
					interest = pi.getCurrentInterest();
				} else {
					tsEndDate = pi.getEndDate();
					rate = pi.getRate();
					interest = pi.getPayableInterest()-pi.getOtherInterest();
				}
				//本金1，利息支出，利息支出利率，开始日期，结束日期，天数
				info.setAmount1(DataFormat.formatAmount(pi.getAmount()));
				info.setInterest1(DataFormat.formatAmount(interest));
				info.setRate1(DataFormat.formatAmountUseZero((rate),6));
				if(pi.getStartDate()!=null)
				{
					strTemp = DataFormat.formatDate(tsStartDate);
					info.setBeginYear1(strTemp.substring(0, 4));
					info.setBeginMonth1(strTemp.substring(5, 7));
					info.setBeginDay1(strTemp.substring(8, 10));
				}
				if(pi.getExecuteDate()!=null)
				{
					strTemp = DataFormat.formatDate(tsEndDate);
					info.setOverYear1(strTemp.substring(0, 4));
					info.setOverMonth1(strTemp.substring(5, 7));
					info.setOverDay1(strTemp.substring(8, 10));
				}
				if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER || pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
				{
					//定期支取&定期续存
					info.setDayNumber1((getIntervalDays(pi.getStartDate(), tsEndDate, intervalDaysFlag)) + "");
				}
				else {
					if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
					{
						//通知支取
						info.setDayNumber1((getIntervalDays(pi.getStartDate(), tsEndDate, intervalDaysFlag)) + "");
					}
					else
					{
						info.setDayNumber1((getIntervalDays(pi.getStartDate(), tsEndDate, intervalDaysFlag) + 1) + "");
					}
				}
				//利息合计
				info.setTotalInterest(DataFormat.formatAmount(DataFormat.formatDouble(pi.getPayableInterest())));
				//本金2，其它利息(可能有)，其它利息利率(无)，开始日期，结束日期，天数
				if (pi.getOtherInterest() != 0.0)
				{
					info.setAmount2(DataFormat.formatAmount(pi.getAmount()));
					info.setInterest2(DataFormat.formatAmount(pi.getOtherInterest()));
					strTemp = DataFormat.formatDate(pi.getStartDate());
					info.setBeginYear2(strTemp.substring(0, 4));
					info.setBeginMonth2(strTemp.substring(5, 7));
					info.setBeginDay2(strTemp.substring(8, 10));
					strTemp = DataFormat.formatDate(pi.getInterestStartDate());
					info.setOverYear2(strTemp.substring(0, 4));
					info.setOverMonth2(strTemp.substring(5, 7));
					info.setOverDay2(strTemp.substring(8, 10));
					if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER
						|| pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
					{
						//定期支取&定期续存
						info.setDayNumber2((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), intervalDaysFlag)) + "");
					} else {
						if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
						{
							//通知支取
							info.setDayNumber2((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), intervalDaysFlag)) + "");
						}
						else
						{
							info.setDayNumber2((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), 1) + intervalDaysFlag) + "");
						}
					}
				}
			}
			if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
			{
				//如果是定期续存
				//计提利息
				if (pi.getRealInterestReceivable() > 0)
				{
					info.setAmount1(DataFormat.formatAmount(pi.getAmount()));
					info.setInterest1(DataFormat.formatAmount(pi.getRealInterestReceivable()));
					info.setRate1(DataFormat.formatAmountUseZero(pi.getRate(),6));
					strTemp = DataFormat.formatDate(pi.getStartDate());
					info.setBeginYear1(strTemp.substring(0, 4));
					info.setBeginMonth1(strTemp.substring(5, 7));
					info.setBeginDay1(strTemp.substring(8, 10));
					if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
					{
						//定期续存
						//取上次计提日期，作为计提日期的结束日期
						strTemp = DataFormat.formatDate(tsLastPreDraw);
						tsLastPreDraw = subAccountFixedInfo.getPreDrawDate();
						info.setOverYear1(strTemp.substring(0, 4));
						info.setOverMonth1(strTemp.substring(5, 7));
						info.setOverDay1(strTemp.substring(8, 10));
					}
					else
					{
						strTemp = DataFormat.formatDate(pi.getEndDate());
						info.setOverYear1(strTemp.substring(0, 4));
						info.setOverMonth1(strTemp.substring(5, 7));
						info.setOverDay1(strTemp.substring(8, 10));
					}
					if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)
					{
						//定期支取
						info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), intervalDaysFlag)) + "");
					}
					else
						if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
						{
							//定期续存
							info.setDayNumber1((getIntervalDays(pi.getStartDate(), tsLastPreDraw, intervalDaysFlag)) + "");
						}
						else
							if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
							{
								//通知支取
								info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), intervalDaysFlag)) + "");
							}
							else
							{
								info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), intervalDaysFlag) + 1) + "");
							}
					if (pi.getPayableInterest() > 0)
					{
						info.setAmount2(DataFormat.formatAmount(pi.getAmount()));
						info.setInterest2(DataFormat.formatAmount(pi.getPayableInterest()));
						info.setRate2(DataFormat.formatAmountUseZero(pi.getRate(),6));
						//如果存在计提利息，则利息支付的上次结息日为上次计提利息计提日
						if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
						{
							if (pi.getRealInterestReceivable() > 0)
							{
								strTemp = DataFormat.formatDate(tsLastPreDraw);
								info.setBeginYear2(strTemp.substring(0, 4));
								info.setBeginMonth2(strTemp.substring(5, 7));
								info.setBeginDay2(strTemp.substring(8, 10));
							}
							else
							{
								strTemp = DataFormat.formatDate(pi.getStartDate());
								info.setBeginYear2(strTemp.substring(0, 4));
								info.setBeginMonth2(strTemp.substring(5, 7));
								info.setBeginDay2(strTemp.substring(8, 10));
							}
						}
						else
						{
							strTemp = DataFormat.formatDate(pi.getStartDate());
							info.setBeginYear2(strTemp.substring(0, 4));
							info.setBeginMonth2(strTemp.substring(5, 7));
							info.setBeginDay2(strTemp.substring(8, 10));
						}
						strTemp = DataFormat.formatDate(pi.getEndDate());
						info.setOverYear2(strTemp.substring(0, 4));
						info.setOverMonth2(strTemp.substring(5, 7));
						info.setOverDay2(strTemp.substring(8, 10));
						if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)
						{
							//定期支取
							info.setDayNumber2((getIntervalDays(pi.getStartDate(), pi.getEndDate(), intervalDaysFlag)) + "");
						} else {
							if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
							{
								//定期续存
								if (pi.getRealInterestReceivable() > 0)
								{
									info.setDayNumber2((getIntervalDays(tsLastPreDraw, pi.getEndDate(), intervalDaysFlag)) + "");
								}
								else
								{
									info.setDayNumber2((getIntervalDays(pi.getStartDate(), pi.getEndDate(), intervalDaysFlag)) + "");
								}
							} else {
								if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
								{
									//通知支取
									info.setDayNumber2((getIntervalDays(pi.getStartDate(), pi.getEndDate(), intervalDaysFlag)) + "");
								}
								else
								{
									info.setDayNumber2((getIntervalDays(pi.getStartDate(), pi.getEndDate(), intervalDaysFlag) + 1) + "");
								}
							}
						}
					}
				}
				else
					if (pi.getPayableInterest() > 0)
					{
						info.setAmount1(DataFormat.formatAmount(pi.getAmount()));
						info.setInterest1(DataFormat.formatAmount(pi.getPayableInterest()));
						info.setRate1(DataFormat.formatAmountUseZero(pi.getRate(),6));
						strTemp = DataFormat.formatDate(pi.getStartDate());
						info.setBeginYear1(strTemp.substring(0, 4));
						info.setBeginMonth1(strTemp.substring(5, 7));
						info.setBeginDay1(strTemp.substring(8, 10));
						strTemp = DataFormat.formatDate(pi.getEndDate());
						info.setOverYear1(strTemp.substring(0, 4));
						info.setOverMonth1(strTemp.substring(5, 7));
						info.setOverDay1(strTemp.substring(8, 10));
						if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER
							|| pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
						{
							//定期支取&定期续存
							info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), intervalDaysFlag)) + "");
						}
						else
							if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
							{
								//通知支取
								info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), intervalDaysFlag)) + "");
							}
							else
							{
								info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), intervalDaysFlag) + 1) + "");
							}
					}
				//利息合计
				info.setTotalInterest(
					DataFormat.formatAmount(DataFormat.formatDouble(DataFormat.formatDouble(pi.getRealInterestReceivable()) + DataFormat.formatDouble(pi.getPayableInterest()))));
			}
			info.setTotalInterestChinese(ChineseCurrency.showChinese(info.getTotalInterest(), pi.getCurrencyID()));
			//利息账号
			//info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getReceiveInterestAccountID()));
			//为航天科工新加，显示旧账号。但不影响其他项目，如果没有新旧账户对应关系，则返回新账号（软通结算账号）
			if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )
            {
                info.setInterestAccountNo(NameRef.getOriginalAcctNoByAcctID(pi.getReceiveInterestAccountID()));
            }
            else
            {
			    info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getReceiveInterestAccountID()));
            }
			
			//存款种类
			info.setDepositTypeName(SETTConstant.TransactionType.getName(pi.getTransTypeID()));
			if (info.getDepositTypeName().indexOf("定期") >= 0)
			{
				info.setDepositTypeName("定期存款");
			}
			else if (info.getDepositTypeName().indexOf("通知") >= 0)
			{
				info.setDepositTypeName("通知存款");
			} else 
			{
				info.setDepositTypeName("保证金存款");
			}
			info.setDepositBillNo(pi.getFixedDepositNo());
			info.setTransNo(pi.getTransNo());
			info.setNotes(pi.getAbstract());
            
            IPrintTemplate.showDepositInterest1(info, out);
            out.println("<br clear=all style='page-break-before:always'>");
            IPrintTemplate.showDepositInterest2(info, out);
            out.println("<br clear=all style='page-break-before:always'>");
            IPrintTemplate.showDepositInterest3(info, out);
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
	 * Method PrintConsignLoanInterestAdviceNotice.
	 * 打印应付委托贷款利息（手续费）通知书
	 * @param pi
	 * @param out
	 * @throws Exception
	 */
	public static void PrintConsignLoanInterestAdviceNotice(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			String strTemp = "";
			String strYear = "";
			String strMonth = "";
			String strDay = "";
			ShowConsignLoanInterestAdviceNote info = new ShowConsignLoanInterestAdviceNote();
			//编号：年
			info.setNoticeYear(pi.getFormYear());
			//编号：号
			info.setNoticeNo(pi.getFormNo());
			//借款人
			info.setBorrowClientName(pi.getBorrowClientName());
			//根据合同id查找合同签订日期
			ContractInfo contractInfo = new ContractInfo();
			ContractDao contractDao = new ContractDao();
			contractInfo = contractDao.findByID(pi.getContractID());
			//签订合同年
			if (contractInfo != null)
			{
				strTemp = DataFormat.formatDate(contractInfo.getLoanStart());
				if (strTemp.length() < 10)
					strTemp = "0000000000";
				strYear = strTemp.substring(0, 4);
				strMonth = strTemp.substring(5, 7);
				strDay = strTemp.substring(8, 10);
				info.setSignLoanNoteYear(strYear);
				//月
				info.setSignLoanNoteMonth(strMonth);
				//日
				info.setSignLoanNoteDay(strDay);
			}
			//委托贷款合同号
			info.setContractNo(NameRef.getContractNoByID(pi.getContractID()));
			//放款通知单号
			info.setLoanNoteNo(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			//结息日期年
			strTemp = DataFormat.formatDate(pi.getClearInterestDate());
			//委托方客户
			info.setConsignClientName(NameRef.getClientNameByID(contractInfo.getClientID()));
			if (strTemp.length() < 10)
				strTemp = "0000000000";
			strYear = strTemp.substring(0, 4);
			strMonth = strTemp.substring(5, 7);
			strDay = strTemp.substring(8, 10);
			info.setInterestYear(strYear);
			//月
			info.setInterestMonth(strMonth);
			//日	
			info.setInterestDay(strDay);
			//结息季度
			info.setInterestQuarter(IPrintTemplate.getQuarterByMonth(Long.parseLong(info.getInterestMonth())));
			//委托贷款利息
			info.setConsignLoanInterest(
				DataFormat.formatDisabledAmount(
					DataFormat.formatDouble(pi.getInterest()) + DataFormat.formatDouble(pi.getCompoundInterest()) + DataFormat.formatDouble(pi.getOverDueInterest())));
			//手续费
			info.setCommission(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getCommission())));
			//委托贷款利息手续费合计	
			info.setTotalInterestCommission(
				DataFormat.formatDisabledAmount(
					DataFormat.formatDouble(pi.getInterest())
						+ DataFormat.formatDouble(pi.getCompoundInterest())
						+ DataFormat.formatDouble(pi.getCommission())
						+ DataFormat.formatDouble(pi.getOverDueInterest())));
			//贷款本金单位元
			info.setLoanAmount(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getAmount())));
			
			//modify by xwhe 2008-12-03
			if(pi.getAmount()>0.0)
			{
			String chiLoanAmount = "";
			PrintVoucher  pVoucher= new PrintVoucher();
			chiLoanAmount = pVoucher.getChineseFormatAmount(pi.getLoanBalance(),pi.getCurrencyID());
			info.setChinLoanAmount(chiLoanAmount+"(大写)");
			}
			//贷款余额单位元
			if (pi.getLoanBalance() > 0)
			{
				info.setLoanBalance(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getLoanBalance())));
			}
			else
			{
				info.setLoanBalance("0.00");
			}
			//合同利率年息
			info.setContractRate(String.valueOf(DataFormat.formatDouble(pi.getContractRate(), 6)));
			//执行利率年息（调整前）
			info.setExecuteRate(String.valueOf(DataFormat.formatDouble(pi.getExecuteRate(), 6)));
			//执行利率调整年
			String ExecuteRateAdjustYear = "";
			strTemp = DataFormat.formatDate(pi.getAdjustRateDate());
			if (strTemp.length() < 10)
			{
				info.setExecuteRateAdjustYear("&nbsp;&nbsp;&nbsp;&nbsp;");
				//执行利率调整月
				info.setExecuteRateAdjustMonth("&nbsp;&nbsp;");
				//执行利率调整日
				info.setExecuteRateAdjustDay("&nbsp;&nbsp;");
			}
			else
			{
				strYear = strTemp.substring(0, 4);
				strMonth = strTemp.substring(5, 7);
				strDay = strTemp.substring(8, 10);
				info.setExecuteRateAdjustYear(strYear);
				//执行利率调整月
				info.setExecuteRateAdjustMonth(strMonth);
				//执行利率调整日
				info.setExecuteRateAdjustDay(strDay);
			}
			//执行利率年息（调整后）
			info.setExecuteRateNew(String.valueOf(DataFormat.formatDouble(pi.getExecuteRateNew(), 6)));
			//如果利率没有发生变化，则执行利率的调整日期和调整后的执行利率显示为空
			if (pi.getExecuteRateNew() == 0)
			{
				//执行利率调整年
				info.setExecuteRateAdjustYear("&nbsp;&nbsp;&nbsp;&nbsp;");
				//执行利率调整月
				info.setExecuteRateAdjustMonth("&nbsp;&nbsp;");
				//执行利率调整日
				info.setExecuteRateAdjustDay("&nbsp;&nbsp;");
				//执行利率年息（调整后）
				info.setExecuteRateNew("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			}
			//手续费率
			info.setCommissionRate(String.valueOf(pi.getCommissionRate()));
			//贷款期限（月）
			info.setLoanTerm(String.valueOf(pi.getLoanTerm()));
			//贷款期限(起始日)
			if (contractInfo != null)
			{
				pi.setStartDate(contractInfo.getLoanStart());
			}
			if (pi.getStartDate() != null)
			{
				strYear = DataFormat.getYearString(pi.getStartDate());
				strMonth = DataFormat.formatInt(Long.valueOf(DataFormat.getRealMonthString(pi.getStartDate())).longValue(), 2);
				strDay = DataFormat.formatInt(Long.valueOf(DataFormat.getDayString(pi.getStartDate())).longValue(), 2);
			}
			strTemp = "<u>" + strYear + "&nbsp;</u>年<u>&nbsp;" + strMonth + "&nbsp;</u>月<u>&nbsp;" + strDay + "&nbsp;</u>日";
			info.setLoanStartDate(strTemp);
			//贷款期限（到期日）
			if (contractInfo != null)
			{
				pi.setEndDate(contractInfo.getLoanEnd());
			}
			if (pi.getEndDate() != null)
			{
				strYear = DataFormat.getYearString(pi.getEndDate());
				strMonth = DataFormat.formatInt(Long.valueOf(DataFormat.getRealMonthString(pi.getEndDate())).longValue(), 2);
				strDay = DataFormat.formatInt(Long.valueOf(DataFormat.getDayString(pi.getEndDate())).longValue(), 2);
			}
			strTemp = "<u>" + strYear + "&nbsp;</u>年<u>&nbsp;" + strMonth + "&nbsp;</u>月<u>&nbsp;" + strDay + "&nbsp;</u>日";
			info.setLoanStartEnd(strTemp);
			//应付贷款利息
			info.setLoanInterest("￥" + DataFormat.formatAmount(DataFormat.formatDouble(pi.getInterest()) + DataFormat.formatDouble(pi.getOverDueInterest())));
			//应付复利
			info.setLoanCompoundInterest("￥" + DataFormat.formatAmount(pi.getCompoundInterest()));
			//应付手续费
			info.setLoanCommission("￥" + DataFormat.formatAmount(pi.getCommission()));
			//应付利息费用合计
			String TotalInterestFee = "";
			info.setTotalInterestFee(
				"￥"
					+ DataFormat.formatAmount(
						DataFormat.formatDouble(pi.getInterest())
							+ DataFormat.formatDouble(pi.getCompoundInterest())
							+ DataFormat.formatDouble(pi.getCommission())
							+ DataFormat.formatDouble(pi.getOverDueInterest())));
			strTemp = DataFormat.formatDate(pi.getExecuteDate());
			if (strTemp.length() < 10)
				strTemp = "0000000000";
			strYear = strTemp.substring(0, 4);
			strMonth = strTemp.substring(5, 7);
			strDay = strTemp.substring(8, 10);
			//执行日年
			info.setExecuteDateYear(strYear);
			//月
			info.setExecuteDateMonth(strMonth);
			//日
			info.setExecuteDateDay(strDay);
            //编号（号）
			info.setNoticeNo(strMonth+pi.getFormNo().substring(2, pi.getFormNo().length()));
			
			
			//起息日
			if( pi != null && pi.getLatestInterestClearDate() != null )
			{
				strYear = DataFormat.getYearString(pi.getLatestInterestClearDate());
				strMonth = DataFormat.formatInt(Long.valueOf(DataFormat.getRealMonthString(pi.getLatestInterestClearDate())).longValue(), 2);
				strDay = DataFormat.formatInt(Long.valueOf(DataFormat.getDayString(pi.getLatestInterestClearDate())).longValue(), 2);
				strTemp = "<u>&nbsp;" + strYear + "&nbsp;</u>年<u>&nbsp;" + strMonth + "&nbsp;</u>月<u>&nbsp;" + strDay + "&nbsp;</u>日";
				info.setInterestStartDate(strTemp);
			}
			else
			{
				info.setInterestStartDate("<u>&nbsp;&nbsp;&nbsp;&nbsp;</u>年<u>&nbsp;&nbsp;&nbsp;&nbsp;</u>月<u>&nbsp;&nbsp;&nbsp;&nbsp;</u>日");
			}
			
			//计息天数
			if( pi != null && pi.getLatestInterestClearDate() != null )
			{
				info.setIntervalDays(""+ DataFormat.getIntervalDays( pi.getLatestInterestClearDate() ,DataFormat.getNextDate( pi.getClearInterestDate(),-1) ) );
			}
			else
			{
				info.setIntervalDays( "&nbsp;&nbsp;&nbsp;&nbsp;");
			}

			 IPrintTemplate.showConsignLoanInterestAdviceNotice(info, out);
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
	 * Method PrintLoanInterestNotice.
	 * 打印应付贷款利息通知单
	 * @param pi
	 * @param out
	 * @throws Exception
	 */
	public static void PrintLoanInterestNotice(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			String strYear = "";
			String strMonth = "";
			String strDay = "";
			String strTemp = "";
			ShowTrustLoanInterestInfo info = new ShowTrustLoanInterestInfo();
			//编号（）年
			info.setSerialYear(pi.getFormYear());
			//编号（号）
			info.setSerialNo(pi.getFormNo());
			//借款人
			info.setDebitName(pi.getBorrowClientName());
			//贷款合同签订日
			//根据合同id查找合同签订日期
			ContractInfo contractInfo = new ContractInfo();
			ContractDao contractDao = new ContractDao();
			contractInfo = contractDao.findByID(pi.getContractID());
			if (contractInfo != null)
			{
				if (contractInfo.getInputDate() != null)
				{
					strYear = DataFormat.getYearString(contractInfo.getLoanStart());
					strMonth = DataFormat.formatInt(Long.valueOf(DataFormat.getRealMonthString(contractInfo.getLoanStart())).longValue(), 2);
					strDay = DataFormat.formatInt(Long.valueOf(DataFormat.getDayString(contractInfo.getLoanStart())).longValue(),2);
					strTemp = "<u>&nbsp;" + strYear + "&nbsp;</u>年<u>&nbsp;" + strMonth + "&nbsp;</u>月<u>&nbsp;" + strDay + "&nbsp;</u>日";
					info.setDateSign(strTemp);
				}
			}
			//贷款合同号
			info.setLoanContractCode(NameRef.getContractNoByID(pi.getContractID()));
			//放款通知单
			info.setLetOutRequisition(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			//应支付日期
			if (pi.getClearInterestDate() != null)
			{
				strYear = DataFormat.getYearString(pi.getClearInterestDate());
				strMonth = DataFormat.formatInt(Long.valueOf(DataFormat.getRealMonthString(pi.getClearInterestDate())).longValue(), 2);
				strDay = DataFormat.formatInt(Long.valueOf(DataFormat.getDayString(pi.getClearInterestDate())).longValue(), 2);
				strTemp = "<u>&nbsp;" + strYear + "&nbsp;</u>年<u>&nbsp;" + strMonth + "&nbsp;</u>月<u>&nbsp;" + strDay + "&nbsp;</u>日";
				info.setDateDuePay(strTemp);
				//modified by ylguo
				String strDayTemp = (Integer.parseInt(strDay)-1)+"";
				String strPreTemp = "<u>&nbsp;" + strYear + "&nbsp;</u>年<u>&nbsp;" + strMonth + "&nbsp;</u>月<u>&nbsp;" + strDayTemp + "&nbsp;</u>日";
				info.setPreInterestDate(strPreTemp);
			}
			//季度
			strTemp = DataFormat.formatDate(pi.getClearInterestDate());
			if (strTemp.length() > 7)
			{
				info.setQuarter(IPrintTemplate.getQuarterByMonth(Long.parseLong(strTemp.substring(5, 7))));
			}
			//利息
			info.setInterestAmount(
				DataFormat.formatDisabledAmount(
					DataFormat.formatDouble(
						DataFormat.formatDouble(pi.getInterest()) + DataFormat.formatDouble(pi.getCompoundInterest()) + DataFormat.formatDouble(pi.getOverDueInterest()))));
			//贷款本金
			info.setLoanAmount(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getAmount())));
			//贷款余额
			String ChiLoanBalance ="";
            //moidfy by xwhe 2008-12-03
			PrintVoucher  pVoucher= new PrintVoucher();
			if (pi.getLoanBalance() > 0)
			{
				if (pi.getLoanTypeID() == LOANConstant.LoanType.YT || pi.getLoanTypeID() == LOANConstant.LoanType.YT) //银团贷款余额为放款单的余额，不是财务公司的那部分余额
				{
					System.out.println("银团贷款余额为放款单的余额，不是财务公司的那部分余额");
					BankLoanQuery bq = new BankLoanQuery();
					double dSynRate = bq.findRateByFormID(pi.getLoanNoteID());
					System.out.println(dSynRate);
					System.out.println(pi.getLoanBalance());
					if(dSynRate > 0)
					{
						info.setLoanBalance(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getLoanBalance() * 100 / dSynRate)));
						ChiLoanBalance = pVoucher.getChineseFormatAmount(DataFormat.formatDouble(pi.getLoanBalance() * 100 / dSynRate), pi.getCurrencyID());
					}
					System.out.println(info.getLoanBalance());
				}
				else
				{
					info.setLoanBalance(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getLoanBalance())));
				}
			}
			else
			{
				info.setLoanBalance("0.00");
			}
			//贷款余额大写moidfy by xwhe 2008-12-03
			//modify by xwhe 2009-06-08 
			if (pi.getLoanTypeID() == LOANConstant.LoanType.YT || pi.getLoanTypeID() == LOANConstant.LoanType.YT)
			{					
			}
			else
			{
			ChiLoanBalance = pVoucher.getChineseFormatAmount(pi.getLoanBalance(), pi.getCurrencyID());
			}			
			if(pi.getAmount()<=0.0)
			{
			ChiLoanBalance = ChineseCurrency.showChinese("", pi.getCurrencyID());
			}
			info.setChiLoanBalance(ChiLoanBalance+"(大写)");
			//合同利率
			info.setContractRate(String.valueOf(DataFormat.formatDouble(pi.getContractRate(), 6)));
			//执行利率
			info.setExcecuteRate(String.valueOf(DataFormat.formatDouble(pi.getExecuteRate(), 6)));
			//贷款利率调整日期
			if (pi.getAdjustRateDate() != null)
			{
				strYear = DataFormat.getYearString(pi.getAdjustRateDate());
				strMonth = DataFormat.formatInt(Long.valueOf(DataFormat.getRealMonthString(pi.getAdjustRateDate())).longValue(), 2);
				strDay = DataFormat.formatInt(Long.valueOf(DataFormat.getDayString(pi.getAdjustRateDate())).longValue(), 2);
				strTemp = "<u>&nbsp;" + strYear + "&nbsp;</u>年<u>&nbsp;" + strMonth + "&nbsp;</u>月<u>&nbsp;" + strDay + "&nbsp;</u>日";
				info.setDateRateModify(strTemp);
			}
			else
			{
				info.setDateRateModify("<u>&nbsp;&nbsp;&nbsp;&nbsp;</u>年<u>&nbsp;&nbsp;&nbsp;&nbsp;</u>月<u>&nbsp;&nbsp;&nbsp;&nbsp;</u>日");
			}
			
			//起息日
			if( pi != null && pi.getLatestInterestClearDate() != null )
			{
				strYear = DataFormat.getYearString(pi.getLatestInterestClearDate());
				strMonth = DataFormat.formatInt(Long.valueOf(DataFormat.getRealMonthString(pi.getLatestInterestClearDate())).longValue(), 2);
				strDay = DataFormat.formatInt(Long.valueOf(DataFormat.getDayString(pi.getLatestInterestClearDate())).longValue(), 2);
				strTemp = "<u>&nbsp;" + strYear + "&nbsp;</u>年<u>&nbsp;" + strMonth + "&nbsp;</u>月<u>&nbsp;" + strDay + "&nbsp;</u>日";
				info.setInterestStartDate(strTemp);
			}
			else
			{
				info.setInterestStartDate("<u>&nbsp;&nbsp;&nbsp;&nbsp;</u>年<u>&nbsp;&nbsp;&nbsp;&nbsp;</u>月<u>&nbsp;&nbsp;&nbsp;&nbsp;</u>日");
			}
			
			//计息天数
			if( pi != null && pi.getLatestInterestClearDate() != null )
			{
				info.setIntervalDays(""+ DataFormat.getIntervalDays( pi.getLatestInterestClearDate() ,DataFormat.getNextDate( pi.getClearInterestDate(),-1) ) );
			}
			else
			{
				info.setIntervalDays( "&nbsp;&nbsp;&nbsp;&nbsp;");
			}
			
			//调整后年息
			info.setRateModify(String.valueOf(DataFormat.formatDouble(pi.getExecuteRateNew(), 6)));
			if (pi.getExecuteRateNew() == 0)
			{
				info.setRateModify("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			}
			//贷款期限（月）
			info.setLoanIntervalMonth(String.valueOf(pi.getLoanTerm()));
			//贷款期限(起始日)
			if (contractInfo != null)
			{
				pi.setStartDate(contractInfo.getLoanStart());
			}
			if (pi.getStartDate() != null)
			{
				strYear = DataFormat.getYearString(pi.getStartDate());
				strMonth = DataFormat.formatInt(Long.valueOf(DataFormat.getRealMonthString(pi.getStartDate())).longValue(), 2);
				strDay = DataFormat.formatInt(Long.valueOf(DataFormat.getDayString(pi.getStartDate())).longValue(), 2);
			}
			strTemp = "<u>&nbsp;" + strYear + "&nbsp;</u>年<u>&nbsp;" + strMonth + "&nbsp;</u>月<u>&nbsp;" + strDay + "&nbsp;</u>日";
			info.setLoanIntervalStart(strTemp);
			//贷款期限（到期日）
			if (contractInfo != null)
			{
				pi.setEndDate(contractInfo.getLoanEnd());
			}
			if (pi.getEndDate() != null)
			{
				strYear = DataFormat.getYearString(pi.getEndDate());
				strMonth = DataFormat.formatInt(Long.valueOf(DataFormat.getRealMonthString(pi.getEndDate())).longValue(), 2);
				strDay = DataFormat.formatInt(Long.valueOf(DataFormat.getDayString(pi.getEndDate())).longValue(), 2);
			}
			strTemp = "<u>&nbsp;" + strYear + "&nbsp;</u>年<u>&nbsp;" + strMonth + "&nbsp;</u>月<u>&nbsp;" + strDay + "&nbsp;</u>日";
			info.setLoanIntervalEnd(strTemp);
			//应付贷款利息
			info.setDuePayLoanInterest("￥" + DataFormat.formatAmount(pi.getInterest()) + DataFormat.formatAmount(pi.getOverDueInterest()));
			//应付复利
			info.setDuePayCompundInterest("￥" + DataFormat.formatAmount(pi.getCompoundInterest()));
			//应付利息费用合计
			info.setDuePayToalInterest(
				"￥"
					+ DataFormat.formatAmount(
						DataFormat.formatDouble(
							DataFormat.formatDouble(pi.getInterest()) + DataFormat.formatDouble(pi.getOverDueInterest()) + DataFormat.formatDouble(pi.getCompoundInterest()))));
			//盖章日期（年）
			//	strTemp = DataFormat.formatDate(pi.getExecuteDate());
			//modify by xwhe 2008-12-03
			strTemp = Env.getSystemDateString();
			if (strTemp.length() < 10)
				strTemp = "0000000000";
			strYear = strTemp.substring(0, 4);
			strMonth = strTemp.substring(5, 7);
			strDay = strTemp.substring(8, 10);
			info.setYearSeal(strYear);
			//盖章日期（月）
			info.setMonthSeal(strMonth);
			//盖章日期（日）		    
			info.setDaySeal(strDay);
			
            //编号（号）
			info.setSerialNo(strMonth+pi.getFormNo().substring(2, pi.getFormNo().length()));

             IPrintTemplate.showPayLoanInterestNotice(info, out);
			// out.println("<br clear=all style='page-break-before:always'>");
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
	
//	格式化金额,处理科学计数法产生的问题
	public String getChineseFormatAmount(double amount, long currencyID) throws Exception
	{
		String strChineseAmount = "零元";
		
		if(amount != 0.0)
		{
			DecimalFormat decimalFormat = new DecimalFormat("0.00");
			strChineseAmount = decimalFormat.format(amount);
			strChineseAmount = ChineseCurrency.showChinese(strChineseAmount, currencyID);
		}
		
		return strChineseAmount;
	}
	
	/**
	 * Method PrintLoanInterestNotice.
	 * 打印应付贷款利息通知单 放款单明细条目
	 * @param pi
	 * @param out
	 * @throws Exception
	 */
	public static void PrintLoanInterestNoticeDetails(Vector vctLoanNoticeDetails, JspWriter out) throws Exception
	{
		try
		{
			PrintInfo pi = null;
			boolean bIsFirst = false;
			double dSumInterest = 0.0;
			double dSumCompoundInterest = 0.0;
			double dSumLoanAmount = 0.0;
			for (int i = 0; i < vctLoanNoticeDetails.size(); i++)
			{
				//取得每个合同的汇总 和 明细条目
				pi = (PrintInfo) vctLoanNoticeDetails.elementAt(i);
				LoanPayFormDetailInfo lpfdinfo = new LoanPayFormDetailInfo();
				lpfdinfo.setLoadNoteID(pi.getLoanNoteID());
				Sett_TransGrantLoanDAO stdao = new Sett_TransGrantLoanDAO();
				lpfdinfo = stdao.findPayFormDetailByCondition(lpfdinfo);
				System.out.println("===放款通知单：" + pi.getLoanNoteID());
				System.out.println("===起息日：" + lpfdinfo.getInterestStart());
				dSumLoanAmount += DataFormat.formatDouble(lpfdinfo.getAmount());
				dSumInterest += DataFormat.formatDouble(pi.getInterest());
				dSumCompoundInterest += DataFormat.formatDouble(pi.getCompoundInterest());
				//利率
				PrintInfo tmpPrintInfo = new PrintInfo();
				//判断利率调整日期是否在起息日和结息日之间
				if (pi.getAdjustRateDate() != null && pi.getInterestStartDate() != null && pi.getClearInterestDate() != null)
				{
					if (pi.getAdjustRateDate().after(pi.getInterestStartDate())
						&& (pi.getAdjustRateDate().before(pi.getClearInterestDate()) || pi.getAdjustRateDate() == pi.getClearInterestDate()))
					{
						//调整日期在起息日和结息日之间
						//执行利率
						tmpPrintInfo.setExecuteRate(pi.getExecuteRateNew());
						//贷款利率调整日期
						tmpPrintInfo.setAdjustRateDate(pi.getAdjustRateDate());
						//调整后年息
						tmpPrintInfo.setExecuteRateNew(pi.getExecuteRateNew());
					}
					else
						if (!pi.getAdjustRateDate().after(pi.getInterestStartDate()))
						{
							//调整日期在起息日之前，或在起息日当日
							if (pi.getExecuteRateNew() > 0 && (pi.getExecuteRate() != pi.getExecuteRateNew()))
							{
								tmpPrintInfo.setExecuteRate(pi.getExecuteRateNew());
								//贷款利率调整日期
								tmpPrintInfo.setAdjustRateDate(null);
								//调整后年息
								tmpPrintInfo.setExecuteRateNew(0.0);
							}
							else
							{
								tmpPrintInfo.setExecuteRate(pi.getExecuteRate());
								//贷款利率调整日期
								tmpPrintInfo.setAdjustRateDate(null);
								//调整后年息
								tmpPrintInfo.setExecuteRateNew(0.0);
							}
						}
				}
				else
				{
					if (pi.getExecuteRateNew() == 0)
					{
						//执行利率
						tmpPrintInfo.setExecuteRate(pi.getExecuteRate());
						//贷款利率调整日期
						tmpPrintInfo.setAdjustRateDate(null);
						//调整后年息
						tmpPrintInfo.setExecuteRateNew(0.0);
					}
					else
					{
						//调整后年息
						tmpPrintInfo.setExecuteRate(pi.getExecuteRateNew());
						//贷款利率调整日期
						tmpPrintInfo.setAdjustRateDate(null);
						//调整后年息
						tmpPrintInfo.setExecuteRateNew(0.0);
					}
				}
				/**
				if (bIsFirst)
				{
					bIsFirst = false;
					out.println(
						" <meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"> "
							+ " <link rel=\"stylesheet\" href=\"/websett/template.css\" type=\"text/css\"> "
							+ " <style type=\"text/css\"> "
							+ " <!-- "
							+ " .In1-table1 {  border: 2px #000000 solid} "
							+ " .In1-td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px} "
							+ " .In1-td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px} "
							+ " .In1-td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px} "
							+ " .In1-td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px} "
							+ " .In1-td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px} "
							+ " .In1-td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px} "
							+ " .In1-td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px} "
							+ " --> "
							+ " </style> "
							+ "<!-- saved from url=(0022)http://internet.e-mail --> "
							+ "<body>"
							+ "<table width=\"630\" border=\"0\" align=\"center\">"
							+ "  <tr> "
							+ "    <td align=\"right\" height=\"30\"><font style=\"font-size:12px\"><b>编号：<u>&nbsp;&nbsp;"
							+ pi.getFormYear()
							+ "&nbsp;&nbsp;</u>年<u>&nbsp;&nbsp;"
							+ pi.getFormNo()
							+ "&nbsp;&nbsp;</u>号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></font></td>"
							+ "  </tr>"
							+ "  <tr> "
							+ "    <td align=\"center\" height=\"40\"><font style=\"font-size:24px\"><b>"
							+ Env.getClientName()
							+ "</b></font></td>"
							+ "  </tr>"
							+ "  <tr> "
							+ "    <td align=\"center\" height=\"40\"><font style=\"font-size:20px\"><b>应付贷款利息通知书（附录）</b></font></td>"
							+ "  </tr>"
							+ "  <tr> "
							+ "    <td height=\"30\">&nbsp;</td>"
							+ "  </tr>"
							+ "  <tr> "
							+ "    <td align=\"left\" height=\"40\"><font style=\"font-size:20px\"><b>借款人：<u>&nbsp;"
							+ pi.getBorrowClientName()
							+ "&nbsp;</u></b></font></td>"
							+ "  <tr> "
							+ "    <td align=\"left\" height=\"40\"><font style=\"font-size:20px\"><b>贷款合同号：<u>&nbsp;"
							+ NameRef.getContractNoByID(pi.getContractID())
							+ "&nbsp;</u></b></font></td>"
							+ "  </tr>"
							+ "  <tr> "
							+ "    <td>"
							+ "<table border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"In1-table1\" width=\"640\">"
							+ "<tr>"
							+ "<td  align=\"center\" class=\"In1-td-rightbottom\">放款通知单编号</td>"
							+ "<td  align=\"center\" class=\"In1-td-rightbottom\">放款金额</td>"
							+ "<td  align=\"center\" class=\"In1-td-rightbottom\">放款日期</td>"
							+ "<td  align=\"center\" class=\"In1-td-rightbottom\">起息日</td>"
							+ "<td  align=\"center\" class=\"In1-td-rightbottom\">止息日</td>"
							+ "<td  align=\"center\" class=\"In1-td-rightbottom\">利率％</td>"
							+ "<td  align=\"center\" class=\"In1-td-rightbottom\">应付利息</td>"
							+ "<td  align=\"center\" class=\"In1-td-rightbottom\">应付复利</td>"
							+ "<td  align=\"center\" class=\"In1-td-bottom\">应付利息费用合计</td>"
							+ "</tr>");
				}
				out.println(
					"<tr>"
						+ "<td  class=\"In1-td-rightbottom\" align=\"center\"><font style=\"font-size:12px\">"
						+ NameRef.getPayFormNoByID(pi.getLoanNoteID())
						+ "</font></td>"
						+ "<td  class=\"In1-td-rightbottom\" align=\"right\"><font style=\"font-size:12px\">"
						+ DataFormat.formatDisabledAmount(DataFormat.formatDouble(lpfdinfo.getAmount()))
						+ "</font></td>"
						+ "<td  class=\"In1-td-rightbottom\"><font style=\"font-size:12px\">"
						+ DataFormat.formatDate(lpfdinfo.getInterestStart())
						+ "</font></td>"
						+ "<td  class=\"In1-td-rightbottom\"><font style=\"font-size:12px\">"
						+ DataFormat.formatDate(pi.getInterestStartDate())
						+ "</font></td>"
						+ "<td  class=\"In1-td-rightbottom\"><font style=\"font-size:12px\">"
						+ DataFormat.formatDate(DataFormat.getPreviousDate(pi.getClearInterestDate()))
						+ "</font></td>"
						+ "<td class=\"In1-td-rightbottom\"><font style=\"font-size:12px\">"
						+ String.valueOf(DataFormat.formatRate(tmpPrintInfo.getExecuteRate()))
						+ "</font></td>"
						+ "<td  class=\"In1-td-rightbottom\" align=\"right\"><font style=\"font-size:12px\">"
						+ "￥"
						+ DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getInterest()))
						+ "</font></td>"
						+ "<td  class=\"In1-td-rightbottom\" align=\"right\"><font style=\"font-size:12px\">"
						+ "￥"
						+ DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getCompoundInterest()))
						+ "</font></td>"
						+ "<td  class=\"In1-td-bottom\" align=\"right\"><font style=\"font-size:12px\">"
						+ "￥"
						+ DataFormat.formatDisabledAmount(DataFormat.formatDouble(DataFormat.formatDouble(pi.getInterest()) + DataFormat.formatDouble(pi.getCompoundInterest())))
						+ "</font></td>"
						+ "</tr>");
			} //end for
			out.println(
				"<tr>"
					+ "<td  class=\"In1-td-right\" align=\"center\"><font style=\"font-size:12px\"><B>合计</B></font></td>"
					+ "<td class=\"In1-td-right\" align=\"right\"><font style=\"font-size:12px\">"
					+ "￥"
					+ DataFormat.formatDisabledAmount(dSumLoanAmount)
					+ "</font></td>"
					+ "<td class=\"In1-td-right\"><font style=\"font-size:12px\">&nbsp;</font></td>"
					+ "<td class=\"In1-td-right\"><font style=\"font-size:12px\">&nbsp;</font></td>"
					+ "<td class=\"In1-td-right\"><font style=\"font-size:12px\">&nbsp;</font></td>"
					+ "<td class=\"In1-td-right\"><font style=\"font-size:12px\">&nbsp;</font></td>"
					+ "<td class=\"In1-td-right\" align=\"right\"><font style=\"font-size:12px\">"
					+ "￥"
					+ DataFormat.formatDisabledAmount(dSumInterest)
					+ "</font></td>"
					+ "<td class=\"In1-td-right\" align=\"right\"><font style=\"font-size:12px\">"
					+ "￥"
					+ DataFormat.formatDisabledAmount(dSumCompoundInterest)
					+ "</font></td>"
					+ "<td align=\"right\"><font style=\"font-size:12px\">"
					+ "￥"
					+ DataFormat.formatDisabledAmount(DataFormat.formatDouble(dSumInterest + dSumCompoundInterest))
					+ "</font></td>"
					+ "</tr>");
			out.println("</table></td></tr></table>" + "</body>");
			**/
			}
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
	 * Method PrintOverLoanNotice.
	 * 打印逾期贷款催收通知书
	 * @param pi
	 * @param out
	 * @throws Exception
	 */
	public static void PrintOverLoanNotice(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			String strTemp = "";
			String strYear = "";
			String strMonth = "";
			String strDay = "";
			ShowOverLoanNoticeInfo info = new ShowOverLoanNoticeInfo();
			//编号（年）
			info.setSerialYear(pi.getFormYear());
			//编号（号）
			info.setSerialNo(pi.getFormNo());
			//催收次数
			info.setFormNum(DataFormat.getChineseNumberString(pi.getFormNum()));
			//借款人
			info.setDebitName(pi.getBorrowClientName());
			//但保人
			info.setAssureName(pi.getAssureName());
			//根据合同id查找合同签订日期
			ContractInfo contractInfo = new ContractInfo();
			ContractDao contractDao = new ContractDao();
			contractInfo = contractDao.findByID(pi.getContractID());
			//签订合同时间
			if (contractInfo != null)
			{
				if (contractInfo.getLoanStart() != null)
				{
					strYear = DataFormat.getYearString(contractInfo.getLoanStart());
					strMonth = DataFormat.formatInt(Long.valueOf(DataFormat.getRealMonthString(contractInfo.getLoanStart())).longValue(), 2);
					strDay = DataFormat.formatInt(Long.valueOf(DataFormat.getDayString(contractInfo.getLoanStart())).longValue(), 2);
					strTemp = "<u>&nbsp;" + strYear + "&nbsp;</u>年<u>&nbsp;" + strMonth + "&nbsp;</u>月<u>&nbsp;" + strDay + "&nbsp;</u>日";
					info.setDateStart(strTemp);
				}
			}
			//贷款合同号
			info.setLoanContractCode(NameRef.getContractNoByID(pi.getContractID()));
			//担保合同号
			info.setAssureContractCode(NameRef.getContractNoByID(pi.getAssureContractID()));
			if (pi.getAssureContractID() < 0)
			{
				info.setAssureContractCode("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			}
			//放款通知单号
			info.setLetOutRequisition(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			//放款通知单贷款金额
			Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO();
			SubAccountLoanInfo subAccountLoanInfo = new SubAccountLoanInfo();
			subAccountLoanInfo.setLoanNoteID(pi.getLoanNoteID());
			subAccountLoanInfo = sett_SubAccountDAO.querySubInfo(subAccountLoanInfo);
			if (subAccountLoanInfo != null)
			{
				info.setLoanAmount(DataFormat.formatDisabledAmount(DataFormat.formatDouble(subAccountLoanInfo.getOpenAmount())));
				pi.setLoanBalance(subAccountLoanInfo.getBalance());
			}
			//贷款到期日--取合同中贷款的结束日期
			if (contractInfo != null)
			{
				if (contractInfo.getLoanEnd() != null)
				{
					strYear = DataFormat.getYearString(contractInfo.getLoanEnd());
					strMonth = DataFormat.formatInt(Long.valueOf(DataFormat.getRealMonthString(contractInfo.getLoanEnd())).longValue(), 2);
					strDay = DataFormat.formatInt(Long.valueOf(DataFormat.getDayString(contractInfo.getLoanEnd())).longValue(), 2);
					strTemp = "<u>&nbsp;" + strYear + "&nbsp;</u>年<u>&nbsp;" + strMonth + "&nbsp;</u>月<u>&nbsp;" + strDay + "&nbsp;</u>日";
					info.setDateEnd(strTemp);
					
					Timestamp dtTemp = DataFormat.getNextDate(contractInfo.getLoanEnd(), 14);
					strYear = DataFormat.getYearString(dtTemp);
					strMonth = DataFormat.formatInt(Long.valueOf(DataFormat.getRealMonthString(dtTemp)).longValue(), 2);
					strDay = DataFormat.formatInt(Long.valueOf(DataFormat.getDayString(dtTemp)).longValue(), 2);
					strTemp = "<u>&nbsp;" + strYear + "&nbsp;</u>年<u>&nbsp;" + strMonth + "&nbsp;</u>月<u>&nbsp;" + strDay + "&nbsp;</u>日";
					
					info.setNextTwoWeekDateEnd(strTemp);
				}
			}
			//截至日期--取结息日
			if (pi.getClearInterestDate() != null)
			{
				strYear = DataFormat.getYearString(pi.getClearInterestDate());
				strMonth = DataFormat.formatInt(Long.valueOf(DataFormat.getRealMonthString(pi.getClearInterestDate())).longValue(), 2);
				strDay = DataFormat.formatInt(Long.valueOf(DataFormat.getDayString(pi.getClearInterestDate())).longValue(), 2);
				strTemp = "<u>&nbsp;" + strYear + "&nbsp;</u>年<u>&nbsp;" + strMonth + "&nbsp;</u>月<u>&nbsp;" + strDay + "&nbsp;</u>日";
				info.setDeadline(strTemp);
			}
			//未还贷款金额
			if (pi.getLoanBalance() > 0) //此值在本程序中进行过设置
			{
				info.setOwnAmount(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getLoanBalance())));
			}
			else
			{
				info.setOwnAmount("0.00");
			}
			//未还贷款利息(包含罚息)
			info.setOwnInterest(
				DataFormat.formatDisabledAmount(
					DataFormat.formatDouble(
						DataFormat.formatDouble(pi.getInterest()) + DataFormat.formatDouble(pi.getOverDueInterest()) + DataFormat.formatDouble(pi.getCompoundInterest()))));
			//未还手续费
			info.setOwnComissionFee(DataFormat.formatDisabledAmount(pi.getCommission()));

			//偿还期限（手工填写）
			//盖章日期
			strTemp = DataFormat.formatDate(pi.getExecuteDate());
			if (strTemp.length() < 10)
				strTemp = "0000000000";
			strYear = strTemp.substring(0, 4);
			strMonth = strTemp.substring(5, 7);
			strDay = strTemp.substring(8, 10);
			info.setYearSeal(strYear);
			//盖章日期（月）
			info.setMonthSeal(strMonth);
			//盖章日期（日）		    
			info.setDaySeal(strDay);
			if (pi.getLoanTypeID() == LOANConstant.LoanType.YT || pi.getLoanTypeID() == LOANConstant.LoanType.YT)
			{
				BankLoanQuery bankLoanQuery = new BankLoanQuery();
				double dSynRate = bankLoanQuery.findRateByFormID(pi.getLoanNoteID());
				info.setLoanAmount(DataFormat.formatDisabledAmount(subAccountLoanInfo.getOpenAmount() * 100 / dSynRate));
				info.setOwnAmount(DataFormat.formatDisabledAmount(subAccountLoanInfo.getBalance() * 100 / dSynRate));
			}
			
				IPrintTemplate.showOverLoanDunNotice(info, out);
				out.println("<br clear=all style='page-break-before:always'>");

			
			//回执
				IPrintTemplate.showReceipt(out);
			
			//out.println("<br clear=all style='page-break-before:always'>");
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
	 * Method PrintLoanAtTermNotice.
	 * 打印委托贷款到期通知书
	 * @param pi
	 * @param out
	 * @throws Exception
	 */
	public static void PrintConsignLoanAtTermNotice(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			String strTemp = "";
			String strYear = "";
			String strMonth = "";
			String strDay = "";
			ShowLoanAtTermAdviceNotice info = new ShowLoanAtTermAdviceNotice();
			//编号（年）
			info.setFormYear(pi.getFormYear());
			//编号（号）
			info.setFormNo(pi.getFormNo());
			//借款人 
			info.setBorrowAmountClientName(pi.getBorrowClientName());
			//根据合同id查找合同签订日期
			ContractInfo contractInfo = new ContractInfo();
			ContractDao contractDao = new ContractDao();
			contractInfo = contractDao.findByID(pi.getContractID());
			//签订合同年
			if (contractInfo != null)
			{
				strTemp = DataFormat.formatDate(contractInfo.getLoanStart());
				if (strTemp.length() < 10)
					strTemp = "0000000000";
				strYear = strTemp.substring(0, 4);
				strMonth = strTemp.substring(5, 7);
				strDay = strTemp.substring(8, 10);
				info.setContractSignYear(strYear);
				//月
				info.setContractSignMonth(strMonth);
				//日
				info.setContractSignDay(strDay);
			}
			//合同号
			info.setContractNo(NameRef.getContractNoByID(pi.getContractID()));
			//放款通知单
			info.setLoanNoteNo(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			//借款金额
			Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO();
			SubAccountLoanInfo subAccountLoanInfo = new SubAccountLoanInfo();
			subAccountLoanInfo.setLoanNoteID(pi.getLoanNoteID());
			//20121116 wcl 过滤掉删除的子账户
			subAccountLoanInfo.setStatusID(1);
			subAccountLoanInfo = sett_SubAccountDAO.querySubInfo(subAccountLoanInfo);
			if (subAccountLoanInfo != null)
			{
				info.setBorrowAmount(DataFormat.formatDisabledAmount(DataFormat.formatDouble(subAccountLoanInfo.getOpenAmount())));
				System.out.println("借款金额：" + info.getBorrowAmount());
				pi.setLoanBalance(subAccountLoanInfo.getBalance());
				System.out.println("贷款余额：" + pi.getLoanBalance());
			}
			//贷款到期日年
			String LoanEndYear = "";
			//贷款到期日月
			String LoanEndMonth = "";
			//贷款到期日日
			String LoanEndDay = "";
			strTemp = DataFormat.formatDate(pi.getEndDate());
			if (strTemp.length() < 10)
				strTemp = "0000000000";
			strYear = strTemp.substring(0, 4);
			strMonth = strTemp.substring(5, 7);
			strDay = strTemp.substring(8, 10);
			info.setLoanEndYear(strYear);
			//月
			info.setLoanEndMonth(strMonth);
			//日
			info.setLoanEndDay(strDay);
			//贷款余额
			if (pi.getLoanBalance() > 0) //此值在前面设置过
			{
				info.setLoanAmount(DataFormat.formatDisabledAmount(pi.getLoanBalance()));
			}
			else
			{
				info.setLoanAmount("0.00");
			}
			//利息金额
			info.setInterestAmount(
				DataFormat.formatDisabledAmount(
					DataFormat.formatDouble(
						(DataFormat.formatDouble(pi.getInterest()) + DataFormat.formatDouble(pi.getOverDueInterest()) + DataFormat.formatDouble(pi.getCompoundInterest())))));
			//手续费
			info.setCommission(DataFormat.formatDisabledAmount(pi.getCommission()));
			//本息手续费合计
			String TotalAmountInterest = "";
			info.setTotalAmountInterest(
				DataFormat.formatDisabledAmount(
					DataFormat.formatDouble(pi.getLoanBalance())
						+ DataFormat.formatDouble(pi.getCompoundInterest())
						+ DataFormat.formatDouble(pi.getCommission())
						+ DataFormat.formatDouble(pi.getOverDueInterest())
						+ DataFormat.formatDouble(pi.getInterest())));
			//汇款路径(手写?)
			//String RemitAddress = "";
			//收款单位(手写?)
			//String ReceiveUnit = "";
			//开户银行(手写?)
			//String ReceiveBank = "";
			//账号
			//String AccountNo = "";
			//签署年
			strTemp = DataFormat.formatDate(pi.getExecuteDate());
			if (strTemp.length() < 10)
				strTemp = "0000000000";
			strYear = strTemp.substring(0, 4);
			strMonth = strTemp.substring(5, 7);
			strDay = strTemp.substring(8, 10);
			info.setSealYear(strYear);
			//盖章日期（月）
			info.setSealMonth(strMonth);
			//盖章日期（日）		    
			info.setSealDay(strDay);
			//IPrintTemplate.showConsignLoanAtTermAdviceNotice(info, out);
			//out.println("<br clear=all style='page-break-before:always'>");

	            IPrintTemplate.showConsignLoanAtTermAdviceNotice(info, out);
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
		 * Method PrintLoanAtTermNotice.
		 * 打印贷款到期通知书
		 * @param pi
		 * @param out
		 * @throws Exception
		 */
	public static void PrintLoanAtTermNotice(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			String strTemp = "";
			String strYear = "";
			String strMonth = "";
			String strDay = "";
			ShowLoanAtTermAdviceNotice info = new ShowLoanAtTermAdviceNotice();
			info.setCurrencyID(pi.getCurrencyID());
			//编号（年）
			info.setFormYear(pi.getFormYear());
			//编号（号）
			info.setFormNo(pi.getFormNo());
			//借款人 
			info.setBorrowAmountClientName(pi.getBorrowClientName());
			//根据合同id查找合同签订日期
			ContractInfo contractInfo = new ContractInfo();
			ContractDao contractDao = new ContractDao();
			contractInfo = contractDao.findByID(pi.getContractID());
			//签订合同年
			if (contractInfo != null)
			{
				strTemp = DataFormat.formatDate(contractInfo.getLoanStart());
				if (strTemp.length() < 10)
					strTemp = "0000000000";
				strYear = strTemp.substring(0, 4);
				strMonth = strTemp.substring(5, 7);
				strDay = strTemp.substring(8, 10);
				info.setContractSignYear(strYear);
				//月
				info.setContractSignMonth(strMonth);
				//日
				info.setContractSignDay(strDay);
			}
			//合同号
			info.setContractNo(NameRef.getContractNoByID(pi.getContractID()));
			//放款通知单
			info.setLoanNoteNo(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			//借款金额
			Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO();
			SubAccountLoanInfo subAccountLoanInfo = new SubAccountLoanInfo();
			subAccountLoanInfo.setLoanNoteID(pi.getLoanNoteID());
			subAccountLoanInfo = sett_SubAccountDAO.querySubInfo(subAccountLoanInfo);
			if (subAccountLoanInfo != null)
			{
				info.setBorrowAmount(DataFormat.formatDisabledAmount(DataFormat.formatDouble(subAccountLoanInfo.getOpenAmount())));
				pi.setLoanBalance(subAccountLoanInfo.getBalance());
			}
			//贷款到期日年
			String LoanEndYear = "";
			//贷款到期日月
			String LoanEndMonth = "";
			//贷款到期日日
			String LoanEndDay = "";
			strTemp = DataFormat.formatDate(pi.getEndDate());
			if (strTemp.length() < 10)
				strTemp = "0000000000";
			strYear = strTemp.substring(0, 4);
			strMonth = strTemp.substring(5, 7);
			strDay = strTemp.substring(8, 10);
			info.setLoanEndYear(strYear);
			//月
			info.setLoanEndMonth(strMonth);
			//日
			info.setLoanEndDay(strDay);
			//贷款余额
			if (pi.getLoanBalance() > 0)
			{
				info.setLoanAmount(DataFormat.formatDisabledAmount(pi.getLoanBalance()));
			}
			else
			{
				info.setLoanAmount("0.00");
			}
			//利息金额
			info.setInterestAmount(
				DataFormat.formatDisabledAmount(
					DataFormat.formatDouble(pi.getInterest()) + DataFormat.formatDouble(pi.getOverDueInterest()) + DataFormat.formatDouble(pi.getCompoundInterest())));
			//本息合计
			String TotalAmountInterest = "";
			info.setTotalAmountInterest(
				DataFormat.formatDisabledAmount(
					DataFormat.formatDouble(pi.getLoanBalance())
						+ DataFormat.formatDouble(pi.getInterest())
						+ DataFormat.formatDouble(pi.getOverDueInterest())
						+ DataFormat.formatDouble(pi.getCompoundInterest())));
			//汇款路径(手写?)
			//String RemitAddress = "";
			//收款单位(手写?)
			//String ReceiveUnit = "";
			//开户银行(手写?)
			//String ReceiveBank = "";
			//账号
			//String AccountNo = "";
			//签署年
			strTemp = DataFormat.formatDate(pi.getExecuteDate());
			if (strTemp.length() < 10)
				strTemp = "0000000000";
			strYear = strTemp.substring(0, 4);
			strMonth = strTemp.substring(5, 7);
			strDay = strTemp.substring(8, 10);
			info.setSealYear(strYear);
			//盖章日期（月）
			info.setSealMonth(strMonth);
			//盖章日期（日）		    
			info.setSealDay(strDay);
			////////////////////////////////////////////////////////////////
			//银团贷款需求－在本息合计之前
			if (pi.getLoanTypeID() == LOANConstant.LoanType.YT || pi.getLoanTypeID() == LOANConstant.LoanType.YT)
			{
				BankLoanQuery bankLoanQuery = new BankLoanQuery();
				double dSynRate = bankLoanQuery.findRateByFormID(pi.getLoanNoteID());
				info.setBorrowAmount(DataFormat.formatDisabledAmount(subAccountLoanInfo.getOpenAmount() * 100 / dSynRate));
				info.setLoanAmount(DataFormat.formatDisabledAmount(subAccountLoanInfo.getBalance() * 100 / dSynRate));
				info.setTotalAmountInterest(
					DataFormat.formatDisabledAmount(
						DataFormat.formatDouble(subAccountLoanInfo.getBalance() * 100 / dSynRate)
							+ DataFormat.formatDouble(pi.getInterest())
							+ DataFormat.formatDouble(pi.getOverDueInterest())
							+ DataFormat.formatDouble(pi.getCompoundInterest())));
			}

				IPrintTemplate.showLoanAtTermAdviceNotice(info, out);
				
			//out.println("<br clear=all style='page-break-before:always'>");
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
     *@description:打印手续费凭证
     *第一联 此联银行盖章后退回单位
     *第二联 此联银行作付出传票
     *第三联 此联银行作营业收入传票
     *void
     *@param TransCommissionResultInfo
     *@param JspWriter
     *@throws Exception
     *Notes: 为南航财务项目添加
     */
    public static void PrintCommission( TransCommissionResultInfo info,JspWriter out ) throws Exception
    {
        try 
        {   
            //打印日期
            Timestamp systemDate = Env.getSystemDate(info.getOfficeId(),info.getCurrencyId());
            String strSystemDate = DataFormat.formatDate(systemDate);
            if (strSystemDate.length() < 10)
                strSystemDate = "0000000000";
            String strYear = strSystemDate.substring(0, 4);
            info.setYear(strYear);
            String strMonth = strSystemDate.substring(5, 7);
            info.setMonth(strMonth);
            String strDay = strSystemDate.substring(8, 10);
            info.setDay(strDay);
            
            //笔数，金额
            double countNum = 0.00;
            double commissionAmount = 0.00;
            countNum = (double)info.getCountNum();
            //commissionAmount = info.getCommissionAmount();
            commissionAmount = info.getRebatecommissionAmount();
            
            info.setCountNum( (long)countNum );
            info.setCommissionAmount( commissionAmount );
            
            /*if( countNum > 1 )
            {
                info.setCountNum( (long)Math.sqrt(countNum) );
                info.setCommissionAmount( commissionAmount/Math.sqrt(countNum) );
            }*/
            
            NHCW_IPrintTemplate.showCommission1(info, out);
            out.println("<br clear=all style='page-break-before:always'>");
            NHCW_IPrintTemplate.showCommission2(info, out);
//            out.println("<br clear=all style='page-break-before:always'>");
//            NHCW_IPrintTemplate.showCommission3(info, out);
        }
        catch ( IOException e ) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public static void PrintCommissionBill( List list,JspWriter out ) throws Exception
    {
        try 
        {   
           
            
            NHCW_IPrintTemplate.showCommissionBill(list, out);

        }
        catch ( Exception e ) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        }
    }
	//	银行进账单
	//01	交易年	
	//02	交易月
	//03	交易日	
	//04	付款人全称
	//05	付款人账号	
	//08	付款人开户银行
	//09	收款人全称	
	//10	收款人账号
	//13	收款人开户银行	
	//14	大写金额
	//15	小写金额	
	//16	票据种类
	//17	票据张数	
	//19	备注
	//0026	主管	
	//0029	会计
	//0027	复核	
	//0031	记账
	//电汇信汇
	//  01	交易年
	//  02	交易月
	//  03	交易日
	//  04	汇款人全称
	//  05	汇款人账号
	//  06	汇出省市
	//  07	汇出市县
	//  08	汇出行名称
	//  09	收款人全称
	//  10	收款人账号
	//  11	汇入省市
	//  12	汇入市县
	//  13	汇入行名称
	//  14	大写金额
	//  15	小写金额
	//  18	汇款用途
	//  0026	主管
	//  0029	会计
	//  0027	复核
	//  0031	记账
	//
	//  01	交易年
	//  02	交易月
	//  03	交易日
	//  04	汇款人全称
	//  05	汇款人账号
	//  06	兑付省市
	//  07	兑付市县
	//  08	兑付行名称
	//  09	收款人全称
	//  10	收款人账号
	//  14	大写金额
	//  15	小写金额
	//  18	汇款用途
	//  19	备注
	//	34	对方科目
	//  0026	主管
	//  0027	复核
	//  0028	经办
	//  0033	科目
	/**
	 *	建行电子转账凭证打印
	 * @throws Exception
	 */
	public static void BankPrintCcbTransVoucher(BankShowCcbTransVoucherInfo info, JspWriter out) throws Exception
	{
		try
		{
			 
			IPrintTemplate.BankShowCcbTransVoucher1(info, out);
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
	 * 银行账户交易明细打印
	 * @param info
	 * @param bankTypeID
	 * @param out
	 * @throws Exception
	 */
	/*
	public static void PrintBankTransInfo(AccountTransactionInfo info,long bankTypeID,JspWriter out ) throws Exception {
		try {
			IPrintTemplate.showBankTransInfo(info,bankTypeID,out);
		}
		catch (Exception exp) {
			throw exp;
		}
		finally {
			
		}
	}
	*/
	/*多借多贷明细页面的全打打印
	 * 
	 * 
	 * 
	 * @author feiye
	 *
	 * TODO To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Style - Code Templates
	 */
	public static void printOnePayMutiRecevice(Collection coll, JspWriter out) throws Exception {
		
		//一条多借多贷的INFO信息
		TransOnePayMultiReceiveInfo transOnePayMultiReceiveInfo=null;
		double sumAmount=0.0;
		
		if(coll!=null && coll.size()>0){
			for(int i=0;i<coll.size();i++){
				transOnePayMultiReceiveInfo=(TransOnePayMultiReceiveInfo)((ArrayList)coll).get(i);
				sumAmount=sumAmount+transOnePayMultiReceiveInfo.getAmount();
			}
		}else{
			System.out.println("多借多贷没有明细清单内容可供打印!");
			return;
		}
		
		try
		{
			for(int i=0;i<10;i++)
				System.out.println("输出的是多借多贷的全打明细页面!");
			//out.println(" <html> " +
			//" <head> " );
			////noShowPrintHeadAndFooter(out);
			
			out.print(
					"<Script Language=\"JavaScript\"> document.write(' "
						+ " <meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"> "
						+ " <link rel=\"stylesheet\" href=\"/websett/css/template.css\" type=\"text/css\"> "
						+ " <style type=\"text/css\"> "
						+ " <!-- "
						+ " .In1-table1 {  border: 2px #000000 solid} "
						+ " .In1-td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px} "
						+ " .In1-td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px} "
						+ " .In1-td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px} "
						+ " .In1-td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px} "
						+ " .In1-td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px} "
						+ " .In1-td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px} "
						+ " .In1-td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px} "
						+ " .In1-td-top {  border-color: black black #000000; border-style: solid; border-top-width: 2px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px} "
						+ " --> "
						+ " </style> "
						+ "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\"> "
						+ "	<TABLE width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> "
						+ "<TR> 	<TD width=\"90\">&nbsp;	</TD><TD  width=\"50\">&nbsp;	</TD><TD width=\"310\">&nbsp;	</TD><TD width=\"70\">&nbsp;	</TD><TD width=\"110\">&nbsp;	</TD></TR> 	"
						+ "<TR> 	"			
						+ "<TD colspan=\"2\" width=\"140\" height=\"46\" nowrap>&nbsp;	</TD> "
						+ "  <TD  align=\"center\" width=\"310\" nowrap><strong><font style=\"font-size:16px\">"
						+ Env.getClientName()
						+ "</font><strong><font style=\"font-size:20px\"><font face=\"Arial Black\"><br>"
						+ "       清&nbsp;&nbsp;&nbsp;单&nbsp;&nbsp;&nbsp;(    )</font></font></strong> 　</strong></TD>"
						+ "    <TD colspan=\"2\" width=\"180\" align=\"left\" nowrap>");
			out.print(
					"</TD>"
						+ "</TR> "
						+ "<TR> 	<TD width=\"90\">&nbsp;	</TD><TD class=\"In1-td-top\" colspan=\"3\" width=\"450\">&nbsp;	</TD><TD width=\"110\">&nbsp;	</TD></TR> 	"
						+ "	</TABLE> ");
			
			out.print("	<TABLE width=\"600\"> "
					+ " <TR> "
					+ "		<TD align=\"left\" width=\"50%\">"
                    + " 总计笔数: "
					+ coll.size()
					+ " </TD> "
					+ "	<TD width=\"50%\" align=\"left\">日期："
					+ Env.getSystemDateString()
					+ "	</TD> "
					+ "</TR> "
					+ " <TR> "
					+ "		<TD align=\"left\" width=\"50%\">"
                    + " 总计金额: "
					+ sumAmount
					+ " </TD> "
					+ "	<TD width=\"50%\" align=\"left\">时间："
					+ Env.getSystemDateTime().toString().substring(11,19)
					+ "	</TD> "
					+ "</TR> "
					+ "	</TABLE>");
			//循环体
			out.print("	<TABLE width=\"600\"> "
					+ " <TR> "
					+ "		<TD align=\"left\" width=\"25%\">"
                    + " "
					+ " </TD> "
					+ "		<TD align=\"left\" width=\"25%\">"
                    + " 账  号 "
					+ " </TD> "
					+ "	<TD width=\"25%\" align=\"left\">金  额"
					+ "	</TD> "
					+ "	<TD width=\"25%\" align=\"left\">备  注"
					+ "	</TD> "
					+ "</TR> ");
			
			out.print(" <TR> "
					+"<TD colspan=\"2\" width=\"180\" align=\"left\" nowrap></TD>"
					+" </TR>"
				);
					
					
			for(int i=0;i<coll.size();i++){
				transOnePayMultiReceiveInfo=(TransOnePayMultiReceiveInfo)((ArrayList)coll).get(i);
				
				System.out.println("账户ID："+transOnePayMultiReceiveInfo.getAccountID());
				System.out.println("账户金额："+transOnePayMultiReceiveInfo.getAmount());
				System.out.println("账户摘要："+transOnePayMultiReceiveInfo.getAbstract());
				
				out.print(" <TR> "
						+ "		<TD align=\"left\" width=\"25%\">"
	                    + (i+1)
						+ " </TD> "
						+ "		<TD align=\"left\" width=\"25%\">"
	                    + NameRef.getAccountNoByID(transOnePayMultiReceiveInfo.getAccountID()) 
						+ " </TD> "
						+ "	<TD width=\"25%\" align=\"left\">"
						+ DataFormat.formatAmount(transOnePayMultiReceiveInfo.getAmount())
						+ "	</TD> "
						+ "	<TD width=\"25%\" align=\"left\">"
						+ DataFormat.formatString(transOnePayMultiReceiveInfo.getAbstract())
						+ "	</TD> "
						+ "</TR> ");
			}
			out.print("</TABLE>");
			
			
			
			
			out.print( "</BODY> "
			+ " '); </SCRIPT>  ");
			
		}
		catch (Exception e)
		{
			System.out.println("发生了异常!");
			e.printStackTrace();
		}
	}
	
	/**
     *  浦发打印补打凭证类   2006.4.10 add by feiye
     * @throws Exception
     */
	public static void PrintRepairVoucher(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			System.out.println("开始浦发补打凭证操作.........从pi封装到rvInfo(开始)");
			
			//初始化一个补打信息类最好		(然后将printInfo里面的信息逐个封装到需要打印到补打凭证里的内容里即可)
			ShowRepairVoucher rvInfo = new ShowRepairVoucher();
			
			//小写金额
			rvInfo.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getAmount()));
			//大写金额
			rvInfo.setChineseAmount(ChineseCurrency.showChinese(DataFormat.formatAmount(pi.getAmount()), pi.getCurrencyID()));
			//输入人
			rvInfo.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			//复核人
			rvInfo.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			//客户名字
			rvInfo.setClientName(Env.getClientName());
			
			String strExecuteDate = DataFormat.formatDate(pi.getExecuteDate());
            if (strExecuteDate.length() < 10)
                strExecuteDate = "0000000000";
            String strYear = strExecuteDate.substring(0, 4);
            //年
            rvInfo.setYear(strYear);
            String strMonth = strExecuteDate.substring(5, 7);
            //月
            rvInfo.setMonth(strMonth);
            String strDay = strExecuteDate.substring(8, 10);
            //日
            rvInfo.setDay(strDay);
            //币种名
            rvInfo.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
            //类型ID
            rvInfo.setTranscationTypeName(SETTConstant.TransactionType.getName(pi.getTransTypeID()));
            
            String strPayAccountName = "";
            String strPayAccountNo = "";
            
            //付款方账户
            if (pi.getPayAccountID() > 0)
            {
            	 strPayAccountName = NameRef.getAccountNameByID(pi.getPayAccountID());
                System.out.println("得到付款方的名称："+strPayAccountName);
                if(strPayAccountName == null)
                {
                	strPayAccountName = "";
               	}
                rvInfo.setPayAccountName(strPayAccountName);
                //rvInfo.setPayAccountName(strPayAccountName.trim());
                //strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
                
                strPayAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getPayAccountID());
                rvInfo.setPayAccountNo(strPayAccountNo);
                
                rvInfo.setPayBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
                
            }
            /*
             * if(pi.getPayBankID()!=-1){
				//付款方银行名称
				PIVo.setPayBankName(NameRef.getBankNameByID(pi.getPayBankID()));
				PIVo.setPayAccountNo(NameRef.getBankAccountCodeByAccountIDandBankID(-1,pi.getPayBankID()));
			}
			if(pi.getReceiveBankID()!=-1){
				//收款方银行名称
				PIVo.setReceiveBankName(NameRef.getBankNameByID(pi.getReceiveBankID()));
				PIVo.setReceiveAccountNo(NameRef.getBankAccountCodeByAccountIDandBankID(-1,pi.getReceiveBankID()));
			}
             * 
             */
            else if (pi.getPayBankID()!=-1){			//如果付款方是银行
            	System.out.println("////////////////////////////////////////-begin   付款方针对银行:"+pi.getPayBankID());
                strPayAccountName = NameRef.getBankNameByID(pi.getPayBankID());
                rvInfo.setPayAccountName(DataFormat.formatString(strPayAccountName.trim()));		//付款账户名
                strPayAccountNo = NameRef.getBankAccountCodeByAccountIDandBankID(-1,pi.getPayBankID());
                rvInfo.setPayAccountNo(DataFormat.formatString(strPayAccountNo));				//付款账户号
                rvInfo.setPayBankName("");			//付款开户银行
                System.out.println("////////////////////////////////////////-end");
            }
            else if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
            {
            	strPayAccountName = pi.getExtClientName();
                rvInfo.setPayAccountName(strPayAccountName.trim());
                strPayAccountNo = pi.getExtAccountNo();
                rvInfo.setPayAccountNo(strPayAccountNo);
                rvInfo.setPayBankName(pi.getExtRemitInBank());
              }
            else //总账
            {
            	strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
                rvInfo.setPayAccountName(strPayAccountName.trim());
                strPayAccountNo = NameRef.getGLTypeNoByID(pi.getPayGL());
                rvInfo.setPayAccountNo(strPayAccountNo);
            }
            //收款方账户
            String strReceiveAccountName = "";
            String strReceiveAccountNo = "";
            if (pi.getReceiveAccountID() > 0)
            {
            	strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveAccountID());
                if(strReceiveAccountName == null)
                {
                	strReceiveAccountName = "";
               	}
                rvInfo.setReceiveAccountName(strReceiveAccountName);
                //strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
                
                strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID());
                rvInfo.setReceiveAccountNo(strReceiveAccountNo);

                rvInfo.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
            }
            else if(pi.getReceiveBankID()!=-1){
				//收款方银行名称
				System.out.println("////////////////////////////////////////-begin   收款方针对银行:"+pi.getReceiveBankID());
                strReceiveAccountName = NameRef.getBankNameByID(pi.getReceiveBankID());
                rvInfo.setReceiveAccountName(DataFormat.formatString(strReceiveAccountName));
                strReceiveAccountNo = NameRef.getBankAccountCodeByAccountIDandBankID(-1,pi.getReceiveBankID());
                rvInfo.setReceiveAccountNo(DataFormat.formatString(strReceiveAccountNo));
                rvInfo.setReceiveBankName("");
                System.out.println("////////////////////////////////////////-end");
			}
            else if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
            {
            	strReceiveAccountName = pi.getExtClientName();
                rvInfo.setReceiveAccountName(strReceiveAccountName);
                strReceiveAccountNo = pi.getExtAccountNo();
                rvInfo.setReceiveAccountNo(strReceiveAccountNo);
                rvInfo.setReceiveBankName(pi.getExtRemitInBank());
            }
            else //总账
            {
	            strReceiveAccountName = NameRef.getGLTypeDescByID(pi.getReceiveGL());
	            rvInfo.setReceiveAccountName(strReceiveAccountName);
	            strReceiveAccountNo = NameRef.getGLTypeNoByID(pi.getReceiveGL());
	            rvInfo.setReceiveAccountNo(strReceiveAccountNo);
            }
            System.out.println("开始浦发补打凭证操作.........从pi封装到rvInfo(结束)");
            
			//调用实际的打印类进行打印
			IPrintTemplate.showRepairVoucher1(rvInfo,out);		//补打凭证第一联
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showRepairVoucher2(rvInfo,out);		//补打凭证第二联
			
					/*
					+ info.getPayAccountName()
					+ info.getReceiveAccountName()
					+ info.getPayAccountNo()
					+ info.getReceiveAccountNo()
					+ info.getPayBankName()
					+ info.getReceiveBankName()
					*/
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
	 * Method PrintLoanInterestNotice.
	 * 打印应付贷款利息通知单
	 * @param pi
	 * @param out
	 * @throws Exception
	 */
	public static void PrintFoundsdispatch(FoundsdispatchInfo info, JspWriter out) throws Exception
	{
		try{

             IPrintTemplate.showFoundsdispatch(info, out);
			
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
	 * add by keivn(刘连凯)2011-08-05
	 * 打印银企余额对帐单
	 * @param pi
	 * @param out
	 * @throws Exception
	 */
	public static void printAccountBalance (PrintInfo pi, JspWriter out) throws Exception
	{
	   try
	   {
		//贷款到期日年
		String strYear = "";
		//贷款到期日月
		String strMonth = "";
		//贷款到期日日
		String strDay = "";
		String strTemp="";
		strTemp = DataFormat.formatDate(pi.getEndDate());
		if (strTemp.length() < 10)
			strTemp = "0000000000";
		strYear = strTemp.substring(0, 4);
		strMonth = strTemp.substring(5, 7);
		strDay = strTemp.substring(8, 10);
		
		
	out.print("<Script Language=\"JavaScript\"> document.write(' "
						+ " <meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"> "
						+ " <link rel=\"stylesheet\" href=\"/websett/css/template.css\" type=\"text/css\"> "
						+ " <style type=\"text/css\"> "
						+"<!-- "
						+" .In1-table1 {  border: 2px #000000 solid}"
						+".In1-table11 {  border-color:#000000;border-style: solid;boder-top-width:2px;border-right-width:1px; border-bottom-width:1px;border-left-width: 0px}" 
						+".Inl-td-b234 {  border-color:#000000;border-style: solid;boder-top-width:2px;border-right-width:1px; border-bottom-width:1px;}"
						+".In1-td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px} "
						+".In1-td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px} "
						+".In1-td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px} "
						+".In1-td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px} "
						+".In1-td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}"
						+".In1-td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}" 
						+".In1-td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px} "
						+".In1-td-top {  border-color: black black #000000; border-style: solid; border-top-width: 2px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px} "
						+"--></style>"
						+"<center>"
						+"<table width=\"684\" border=\"0\">"
						+"<tr> "
						+"  <td colspan=\"7\">&nbsp;</td>"
						+"  <td width=\"20\">&nbsp;</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"7\"><div align=\"center\"><strong>账户对账单须知</strong></div></td>"
						+"  <td>&nbsp;</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"7\">1.开户单位请认真核对金额，核对相符的，请在核对结果栏 “相符□”内打“√”，不符的在“兹将不符合账项列后□”</td>"
						+"  <td>&nbsp;</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"7\">&nbsp;&nbsp;内打“√”，并将不符款项逐笔列示在对账单第二联，栏位不够的，可单独附单，并签章后返回。</td>"
						+"  <td>&nbsp;</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"7\">2.不论是否相符，请于收到对账单十日内反馈对账结果。</td>"
						+"  <td>&nbsp;</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"7\">3.逾期不退，视为核对相符，不承担由此带来的相关责任。</td>"
						+"  <td width=\"20\" rowspan=\"14\">第<br>"
						+"    一<br>"
						+"    联<br>"
						+"    :<br>"
						+"    存<br>"
						+"    根<br>"
						+"    联<br>"
						+"    客<br>"
						+"    户<br>"
						+"    留<br>"
						+"    存<br>"
						+"    &nbsp;<br>"
						+"    </td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"7\">4.对账中发现错账，应及时通知结算中心核实，并作相应调整。</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"7\">5.收到此对账单后，请仔细核对单位名称和相关信息，如有疑问，请致电：010-68373975,010-68373974</td>"
						+"</tr>"
						+"<tr> "
						+"  <td height=\"17\" colspan=\"7\">6.对账单第二联，必须盖章后交结算中心。</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"7\">&nbsp;</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"7\"><div align=\"center\">航天科工财务有限责任公司</div></td>"
						+"</tr>"
						+"<tr> "
						+"  <td height=\"20\" colspan=\"7\"> <div align=\"center\">银企余额对账单</div></td>"
						+"</tr>"
						+"<tr> "
						+"<tr> "
						+"  <td><font  align=\"left\">账号：</font></td>"
						+"  <td colspan=\"3\" align=\"left\">&nbsp;"
						+pi.getExtAccountNo()+"</td>"
						+"  <td><font  align=\"left\">No.</td>"
						+"  <td colspan=\"3\" align=\"left\">&nbsp;"+pi.getFormNo()+"</td>"
						+"</tr>"
						+"<tr> "
						+"  <td><font align=\"left\">币种：</font></td>"
						+"  <td colspan=\"3\" align=\"left\">&nbsp;RMB</td>"
						+"  <td><font  align=\"left\">开户单位名称：</td>"
						+"  <td colspan=\"2\" align=\"left\">&nbsp;" 
						+pi.getExtClientName()+"</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"5\">你行送来"
						+strYear
						+"年" +strMonth
						+"月"+strDay+"日余额:"+DataFormat.formatDisabledAmount(pi.getAmount())+"元，经查对相符 □ </td>"
						+"  <td colspan=\"2\">&nbsp;希查照。&nbsp;</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"4\" align=\"right\">兹将不符合账项列后" 
						+"    □</td>"
						+"  <td colspan=\"3\">&nbsp;</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"3\" align=\"right\">&nbsp;</td>"
						+"  <td colspan=\"4\" align=\"right\">单位签章(用原留印鉴)</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"7\">&nbsp;</td>"
						+"</tr>"
						+"<tr> "
						+"  <td>不符账项：</td>"
						+"  <td colspan=\"6\">&nbsp;</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"7\" rowspan=\"6\"><table table width=\"647\" cellspacing=\"0\" height=\"138\" border=\"1px:solid\" bordercolor=\"black\" align=\"left\">"
						+"      <tr class=\"table1\"> "
						+"        <td width=\"65\" rowspan=\"2\"><div align=\"center\">记账日期</div></td>"
						+"        <td width=\"75\" rowspan=\"2\"><div align=\"center\">凭证号码</div></td>"
						+"        <td width=\"79\" rowspan=\"2\"><div align=\"center\">摘要</div></td>"
						+"        <td colspan=\"2\"><div align=\"center\">结算中心已到账，单位尚未到账</div></td>"
						+"        <td colspan=\"2\"><div align=\"center\">单位已到账，结算中心未到账</div></td>"
						+"      </tr>"
						+"      <tr class=\"table1\">" 
						+"        <td width=\"102\"><div align=\"center\">借方</div></td>"
						+"        <td width=\"98\"><div align=\"center\">贷方</div></td>"
						+"        <td width=\"95\"><div align=\"center\">借方</div></td>"
						+"        <td width=\"103\"><div align=\"center\">贷方</div></td>"
						+"      </tr>"
						+"      <tr class=\"table1\">" 
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"      </tr>"
						+"      <tr class=\"table1\">" 
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"      </tr>"
						+"      <tr class=\"table1\">" 
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"      </tr>"
						+"      <tr class=\"table1\">" 
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"      </tr>"
						+"    </table></td>"
						+"</tr>"
						+"<tr> "
						+"  <td>&nbsp;</td>"
						+"</tr>"
						+"<tr> "
						+"  <td>&nbsp;</td>"
						+"</tr>"
						+"<tr>" 
						+"  <td>&nbsp;</td>"
						+"</tr>"
						+"<tr>"
						+"  <td>&nbsp;</td>"
						+"</tr>"
						+"<tr> "
						+"  <td height=\"20\">&nbsp;</td>"
						+"</tr>"
						+"<tr> "
						+"  <td height=\"37\" colspan=\"8\">延此虚线裁剪------------------------------------------------------------------------------</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"7\"><div align=\"center\">航天科工财务有限责任公司</div></td>"
						+"  <td rowspan=\"11\">第<br>"
						+"    二<br>"
						+"    联<br>"
						+"    :<br>"
						+"    客<br>"
						+"    户<br>"
						+"    核<br>"
						+"    对<br>"
						+"    盖<br>"
						+"    章<br>"
						+"    后<br>"
						+"    交<br>"
						+"    <font color=\"#000000\" >经<br>"
						+"    办<br>"
						+"    行<br>"
						+"    </td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"7\"><div align=\"center\">银企余额对账单</div></td>"
						+"</tr>"
						+"<tr> "
						+"  <td><font align=\"left\">账号：</font></td>"
						+"  <td colspan=\"3\" align=\"left\">&nbsp;"
						+pi.getExtAccountNo()+"</td>"
						+"  <td><font align=\"left\">No.</td>"
						+"  <td colspan=\"3\" align=\"left\">&nbsp;"+pi.getFormNo()+"</td>"
						+"</tr>"
						+"<tr> "
						+"  <td><font align=\"left\">币种：</td>"
						+"  <td colspan=\"3\" align=\"left\">&nbsp;RMB</td>"
						+"  <td><font align=\"left\">开户单位名称：</td>"
						+"  <td colspan=\"2\" align=\"left\">&nbsp;"
						+pi.getExtClientName()+"</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"5\">你行送来"
						+strYear
						+"年" +strMonth
						+"月"+strDay+"日余额:"+DataFormat.formatDisabledAmount(pi.getAmount())+"元，经查对相符 □ </td>"
						+"  <td colspan=\"2\">&nbsp;希查照。&nbsp;</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"4\" align=\"right\" >兹将不符合账项列后" 
						+"    □</td>"
						+"  <td colspan=\"3\">&nbsp;</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"3\" align=\"right\">&nbsp;</td>"
						+"  <td colspan=\"4\" align=\"right\">单位签章(用原留印鉴)</td>"
						+"</tr>"
						+"<tr> "
						+"  <td>&nbsp;</td>"
						+"  <td width=\"83\">&nbsp;</td>"
						+"  <td width=\"70\">&nbsp;</td>"
						+"  <td width=\"79\">&nbsp;</td>"
						+"  <td colspan=\"3\">&nbsp;&nbsp;</td>"
						+"</tr>"
						+"<tr> "
						+"  <td>不符账项：</td>"
						+"  <td>&nbsp;</td>"
						+"  <td>&nbsp;</td>"
						+"  <td>&nbsp;</td>"
						+"  <td>&nbsp;</td>"
					    +  "<td width=\"101\">&nbsp;</td>"
						+"  <td width=\"107\">&nbsp;</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"7\"> <table width=\"647\" cellspacing=\"0\" height=\"138\" border=\"1px:solid\" bordercolor=\"black\" align=\"left\">"
						+"      <tr class=\"table1\"> "
						+"        <td width=\"69\" rowspan=\"2\"><div align=\"center\">记账日期</div></td>"
						+"        <td width=\"78\" rowspan=\"2\"><div align=\"center\">凭证号码</div></td>"
						+"        <td width=\"74\" rowspan=\"2\"><div align=\"center\">摘要</div></td>"
						+"        <td colspan=\"2\"><div align=\"center\">结算中心已到账，单位尚未到账</div></td>"
						+"        <td colspan=\"2\"><div align=\"center\">单位已到账，结算中心未到账</div></td>"
						+"      </tr>"
						+"      <tr class=\"table1\">" 
						+"        <td width=\"87\"><div align=\"center\">借方</div></td>"
						+"        <td width=\"99\"><div align=\"center\">贷方</div></td>"
						+"        <td width=\"89\"><div align=\"center\">借方</div></td>"
						+"        <td width=\"105\"><div align=\"center\">贷方</div></td>"
						+"      </tr>"
						+"      <tr class=\"table1\">" 
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"      </tr>"
						+"      <tr class=\"table1\">" 
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"      </tr>"
						+"      <tr class=\"table1\">" 
						+"  	<td>&nbsp;</td>"
						+"      <td>&nbsp;</td>"
						+"       <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"      </tr>"
						+"      <tr class=\"table1\">" 
						+"        <td height=\"20\">&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"        <td>&nbsp;</td>"
						+"      </tr>"
						+"    </table>"
						+"    &nbsp;</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"7\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"7\"><div align=\"center\">为提供周详的服务，如您的资料有所变动&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div></td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"7\"><div align=\"center\">请填写以下信息&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div></td>"
						+"</tr>"
						+"<tr> "
						
						+"  <td height=\"20\" colspan=\"7\">单位：________________________________________________________________________</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"4\">地址：</font>___________________________________</td>"
						+"  <td colspan=\"3\">邮编</font>：______________________________</td>"
						+"  <td>&nbsp;</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"4\">联系人：<font size=\"2\">___________________________</td>"
						+"  <td colspan=\"3\">联系电话：<font size=\"2\">___________________________</td>"
						+"  <td>&nbsp;</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"7\">电子邮箱：_____________________________________________________________________</td>"
						+"  <td>&nbsp;</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"7\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>"
						+"  <td>&nbsp;</td>"
						+"</tr>"
						+"</table>"
						+"</center>"
						+"</body>"
						+"')"
						+ " </SCRIPT>  "); 
	
			
			out.println("<br clear=all style='page-break-before:always'>");
	   	}
	     catch (Exception e)
	 	{
	     	System.out.println("发生了异常!");
			e.printStackTrace(); 
	  	}
	}
}