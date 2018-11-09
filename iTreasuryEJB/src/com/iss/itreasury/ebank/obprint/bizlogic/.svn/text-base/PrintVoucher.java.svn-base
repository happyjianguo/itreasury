/*
 * Created on 2003-10-30
 * 
 * To change the template for this generated file go to Window - Preferences - Java - Code Generation - Code and
 * Comments
 */
package com.iss.itreasury.ebank.obprint.bizlogic;
import javax.servlet.jsp.JspWriter;
import com.iss.itreasury.settlement.print.templateinfo.*;
import com.iss.itreasury.settlement.print.*;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.transloan.dao.*;
import com.iss.itreasury.settlement.transloan.dataentity.*;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.loan.setting.dataentity.YTLoanAttendBankInfo;
import com.iss.itreasury.settlement.transloan.bizlogic.BankLoanQuery;
import com.iss.itreasury.settlement.account.dao.Sett_SubAccountDAO;
import com.iss.itreasury.settlement.account.dataentity.*;
import com.iss.itreasury.loan.discount.dataentity.*;
import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.util.ChineseCurrency;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Database;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;
import java.util.Iterator;
import java.util.Collection;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.loan.bizdelegation.YTLoanAttendBankDelegation;
import com.iss.itreasury.settlement.transdiscount.dao.Sett_TransGrantDiscountDAO;
/**
 * @author rxie
 * 
 * To change the template for this generated type comment go to Window - Preferences - Java - Code Generation - Code
 * and Comments
 */
public class PrintVoucher
{
	Log4j logger = null;
	/**
	 *  
	 */
	public PrintVoucher()
	{
		super();
		// TODO Auto-generated constructor stub
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
		if (sDate == null || eDate == null)
		{
			return 0;
		}
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
			if (eCalendar.get(Calendar.DAY_OF_MONTH) == Calendar.FEBRUARY && (eCalendar.get(Calendar.DAY_OF_MONTH) == 28 || eCalendar.get(Calendar.DAY_OF_MONTH) == 29))
			{
				eCalendar.set(Calendar.DAY_OF_MONTH, 30);
			}
			int intervalYears = eCalendar.get(Calendar.YEAR) - sCalendar.get(Calendar.YEAR);
			int intervalMonths = eCalendar.get(Calendar.MONTH) - sCalendar.get(Calendar.MONTH);
			int intervalDays = eCalendar.get(Calendar.DAY_OF_MONTH) - sCalendar.get(Calendar.DAY_OF_MONTH);
			resIntervalDays = intervalYears * 360 + intervalMonths * 30 + intervalDays;
		}
		return resIntervalDays;
	}

	/**
	 * @param info     ShowWithDrawInfo
	 * @exception IException   throw it while business exception occur and transaction need rollback
	 * @return  ShowWithDrawInfo(with the ob info)
	*/
	private static ShowWithDrawInfo getOBInfoByTransNo(ShowWithDrawInfo info) throws Exception
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
						+ " and fin.CPF_STRANSNO ="
						+ info.getTransNo();
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
				info.setPayAccountName(strPayAccountName);
				strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
				info.setPayAccountNo(strPayAccountNo);
				info.setPayBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			else if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
			{
				strPayAccountName = pi.getExtClientName();
				info.setPayAccountName(strPayAccountName);
				strPayAccountNo = DataFormat.formatString(pi.getExtAccountNo());
				info.setPayAccountNo(strPayAccountNo);
				info.setPayBankName(pi.getExtRemitInBank());
			}
			else //总账
				{
				strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
				info.setPayAccountName(strPayAccountName);
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
				strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
				info.setReceiveAccountNo(strReceiveAccountNo);
				info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			else if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
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
			String strAbstract = "起息日：" + strInterestStart + "；" + pi.getAbstract();
			info.setAbstract(strAbstract);
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
			IPrintTemplate.showIn1(info, out);
			//out.println("<br clear=all style='page-break-before:always'>");
			//IPrintTemplate.showIn2(info, out);
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
			//自营贷款收回，委托贷款收回存款支取凭证中金额和付款方账号
			//如果本金、利息、手续费（担保费）从借款客户的活期账户中扣除，才能累计入存款支取凭证金额
			//如果付本金活期账号存在，则付款方为付本金活期账户，否则如果付利息活期账号存在，则付款方为付息活期账户，手续费、担保费依次类推
			if (pi.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE || pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
			{
				System.out.println("=====自营/委托");
				//付款方方账号
				if (pi.getPayAccountID() > 0)
				{
					System.out.println("=====11111111111111111");
					strPayAccountName = NameRef.getAccountNameByID(pi.getPayAccountID());
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
					strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
					//增加存单号
					if (pi.getFixedDepositNo() != null && pi.getFixedDepositNo().length() > 0)
					{
						strPayAccountNo = strPayAccountNo + "；" + pi.getFixedDepositNo();
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
					//存在付息活期账号
					if (pi.getPayInterestAccountID() > 0)
					{
						dAmount =
							DataFormat.formatDouble(
								dAmount + DataFormat.formatDouble(pi.getRealInterest()) + DataFormat.formatDouble(pi.getRealCompoundInterest()) + DataFormat.formatDouble(pi.getRealOverDueInterest()));
					}
					if (pi.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE)
					{
						//自营贷款收回，存在付担保费活期账号
						if (pi.getPaySuretyFeeAccountID() > 0)
						{
							dAmount = DataFormat.formatDouble(dAmount + DataFormat.formatDouble(pi.getRealSuretyFee()));
							System.out.println("=====dAmount2：" + dAmount);
						}
					}
					else if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
					{
						//委托贷款收回，存在付手续费活期账号
						if (pi.getPayCommissionAccountID() > 0)
						{
							dAmount = DataFormat.formatDouble(dAmount + DataFormat.formatDouble(pi.getRealCommission()));
							System.out.println("=====dAmount3：" + dAmount);
						}
					}
				}
				else if (pi.getPayInterestAccountID() > 0)
				{
					System.out.println("=====222222222222222");
					strPayAccountName = NameRef.getAccountNameByID(pi.getPayInterestAccountID());
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
					strPayAccountNo = NameRef.getAccountNoByID(pi.getPayInterestAccountID());
					//增加存单号
					if (pi.getFixedDepositNo() != null && pi.getFixedDepositNo().length() > 0)
					{
						strPayAccountNo = strPayAccountNo + "；" + pi.getFixedDepositNo();
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
							DataFormat.formatDouble(pi.getRealInterest()) + DataFormat.formatDouble(pi.getRealCompoundInterest()) + DataFormat.formatDouble(pi.getRealOverDueInterest()));
					System.out.println("=====dAmount4：" + dAmount);
					if (pi.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE)
					{
						//自营贷款收回，存在付担保费活期账号
						if (pi.getPaySuretyFeeAccountID() > 0)
						{
							dAmount = DataFormat.formatDouble(dAmount + DataFormat.formatDouble(pi.getRealSuretyFee()));
							System.out.println("=====dAmount5：" + dAmount);
						}
					}
					else if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
					{
						//委托贷款收回，存在付手续费活期账号
						if (pi.getPayCommissionAccountID() > 0)
						{
							dAmount = DataFormat.formatDouble(dAmount + DataFormat.formatDouble(pi.getRealCommission()));
							System.out.println("=====dAmount6：" + dAmount);
						}
					}
				}
				else if (pi.getPaySuretyFeeAccountID() > 0 || pi.getPayCommissionAccountID() > 0)
				{
					System.out.println("=====33333333333333333333333");
					if (pi.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE)
					{
						strPayAccountName = NameRef.getAccountNameByID(pi.getPaySuretyFeeAccountID());
						info.setPayAccountName(DataFormat.formatString(strPayAccountName));
						strPayAccountNo = NameRef.getAccountNoByID(pi.getPaySuretyFeeAccountID());
						//增加存单号
						if (pi.getFixedDepositNo() != null && pi.getFixedDepositNo().length() > 0)
						{
							strPayAccountNo = strPayAccountNo + "；" + pi.getFixedDepositNo();
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
						System.out.println("=====dAmount7：" + dAmount);
					}
					else if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
					{
						strPayAccountName = NameRef.getAccountNameByID(pi.getPayCommissionAccountID());
						info.setPayAccountName(DataFormat.formatString(strPayAccountName));
						strPayAccountNo = NameRef.getAccountNoByID(pi.getPayCommissionAccountID());
						//增加存单号
						if (pi.getFixedDepositNo() != null && pi.getFixedDepositNo().length() > 0)
						{
							strPayAccountNo = strPayAccountNo + "；" + pi.getFixedDepositNo();
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
					strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
					info.setReceiveAccountNo(DataFormat.formatString(strReceiveAccountNo));
					info.setReceiveBankName(DataFormat.formatString(NameRef.getOfficeNameByID(pi.getOfficeID())));
					//info.setReceiveRemitInAddress(NameRef.getOfficeNameByID(pi.getOfficeID()));
				}
				else if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
				{
					strReceiveAccountName = pi.getExtClientName();
					info.setReceiveAccountName(DataFormat.formatString(strReceiveAccountName));
					strReceiveAccountNo = pi.getExtAccountNo();
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
					info.setReceiveAccountNo(DataFormat.formatString(strReceiveAccountNo));
				}
			}
			else //其他业务
				{
				System.out.println("=====非自营/委托");
				//付款方账户
				if (pi.getPayAccountID() > 0)
				{
					strPayAccountName = NameRef.getAccountNameByID(pi.getPayAccountID());
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
					strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
					//增加存单号
					if (pi.getFixedDepositNo() != null && pi.getFixedDepositNo().length() > 0)
					{
						strPayAccountNo = strPayAccountNo + "；" + pi.getFixedDepositNo();
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
				else if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
				{
					strPayAccountName = pi.getExtClientName();
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
					strPayAccountNo = pi.getExtAccountNo();
					//增加存单号
					if (pi.getFixedDepositNo() != null && pi.getFixedDepositNo().length() > 0)
					{
						strPayAccountNo = strPayAccountNo + "；" + pi.getFixedDepositNo();
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
					info.setPayBankName(DataFormat.formatString(pi.getExtRemitInBank()));
				}
				else //总账
					{
					strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
					strPayAccountNo = NameRef.getGLTypeNoByID(pi.getPayGL());
					//增加存单号
					if (pi.getFixedDepositNo() != null && pi.getFixedDepositNo().length() > 0)
					{
						strPayAccountNo = strPayAccountNo + "；" + pi.getFixedDepositNo();
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
				}
				//收款方账户
				String strReceiveAccountName = "";
				String strReceiveAccountNo = "";
				if (pi.getReceiveAccountID() > 0)
				{
					strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveAccountID());
					info.setReceiveAccountName(DataFormat.formatString(strReceiveAccountName));
					strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
					info.setReceiveAccountNo(DataFormat.formatString(strReceiveAccountNo));
					info.setReceiveBankName(DataFormat.formatString(NameRef.getOfficeNameByID(pi.getOfficeID())));
					//info.setReceiveRemitInAddress(NameRef.getOfficeNameByID(pi.getOfficeID()));
				}
				else if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
				{
					strReceiveAccountName = pi.getExtClientName();
					info.setReceiveAccountName(DataFormat.formatString(strReceiveAccountName));
					strReceiveAccountNo = pi.getExtAccountNo();
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
					//info.setReceiveAccountNo(DataFormat.formatString(strReceiveAccountNo));
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
				info.setPayAccountName(strPayAccountName);
				strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
				info.setPayAccountNo(strPayAccountNo);
				info.setPayBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			else if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
			{
				strPayAccountName = pi.getExtClientName();
				info.setPayAccountName(strPayAccountName);
				strPayAccountNo = DataFormat.formatString(pi.getExtAccountNo());
				info.setPayAccountNo(strPayAccountNo);
				info.setPayBankName(pi.getExtRemitInBank());
			}
			else //总账
				{
				strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
				info.setPayAccountName(strPayAccountName);
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
					strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
					info.setReceiveAccountNo(strReceiveAccountNo);
					info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
					dAmount = DataFormat.formatDouble(pi.getAmount());
					System.out.println("========dAmount1:" + dAmount);
					if (pi.getReceiveInterestAccountID() > 0)
					{
						dAmount = DataFormat.formatDouble(dAmount + DataFormat.formatDouble(pi.getPayableInterest()));
						System.out.println("========dAmount2:" + dAmount);
					}
				}
				else if (pi.getReceiveInterestAccountID() > 0)
				{
					strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveInterestAccountID());
					info.setReceiveAccountName(strReceiveAccountName);
					strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
					info.setReceiveAccountNo(strReceiveAccountNo);
					info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
					dAmount = DataFormat.formatDouble(pi.getPayableInterest());
					System.out.println("========dAmount3:" + dAmount);
				}
				strAmount = DataFormat.formatAmount(dAmount);
				System.out.println("========dAmount4:" + dAmount);
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
					strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
					info.setReceiveAccountNo(strReceiveAccountNo);
					info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
				}
				else if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
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
				if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
				{
					//如果是定期续存,特转贷金额为本金+计提利息+利息支付(2004/3/31)
					pi.setAmount(DataFormat.formatDouble(pi.getAmount()) + DataFormat.formatDouble(pi.getRealInterest()));
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
				strCheckUser = "机制";
			}
			info.setCheckUserName(strCheckUser);
			String strAbstract = "起息日：" + DataFormat.formatDate(pi.getInterestStartDate()) + "；" + pi.getAbstract();
			info.setAbstract(strAbstract);
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
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
			info.setPayBankName(strPayBankName);
			String strReceiveBankName = "";
			info.setReceiveBankName(strReceiveBankName);
			String strPayAccountName = "";
			String strPayAccountNo = "";
			//付款方账户
			if (pi.getPayAccountID() > 0)
			{
				strPayAccountName = NameRef.getAccountNameByID(pi.getPayAccountID());
				info.setPayAccountName(strPayAccountName);
				strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
				info.setPayAccountNo(strPayAccountNo);
				info.setPayBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			else if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
			{
				strPayAccountName = pi.getExtClientName();
				info.setPayAccountName(strPayAccountName);
				strPayAccountNo = DataFormat.formatString(pi.getExtAccountNo());
				info.setPayAccountNo(strPayAccountNo);
				info.setPayBankName(pi.getExtRemitInBank());
			}
			else //总账
				{
				strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
				info.setPayAccountName(strPayAccountName);
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
				strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
				info.setReceiveAccountNo(strReceiveAccountNo);
				info.setReceiveBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			else if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
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
			String strAbstract = "起息日：" + DataFormat.formatDate(pi.getInterestStartDate()) + "；" + pi.getAbstract();
			info.setAbstract(strAbstract);
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
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
				info.setRepaymentAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
			}
			info.setTransNo(pi.getTransNo());
			/*
			IPrintTemplate.showTrustRepaymentLoan1(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showTrustRepaymentLoan2(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			*/
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
			info.setLoanRate(DataFormat.formatDisabledAmount(pi.getRate()));
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
				info.setRepaymentAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
			}
			info.setRepaymentUnitName(NameRef.getClientNameByID(pi.getPayClientID()));
			info.setTotalInterest(DataFormat.formatDisabledAmount(pi.getRealInterest() + pi.getRealCompoundInterest() + pi.getRealOverDueInterest() + pi.getRealCommission()));
			info.setTransNo(pi.getTransNo());
			/*
			IPrintTemplate.showConsignRepaymentLoan1(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showConsignRepaymentLoan2(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			*/
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
			info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
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
			info.setContractRate(DataFormat.formatDisabledAmount(lpfdinfo.getInterestRate()));
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			info.setLoanInterval(DataFormat.getChineseDateString(lpfdinfo.getStart()) + " 至 " + DataFormat.getChineseDateString(lpfdinfo.getEnd()));
			info.setLoanType(LOANConstant.LoanType.getName(lpfdinfo.getLoanType()));
			info.setLoanUnit(NameRef.getClientNameByID(pi.getReceiveClientID()));
			info.setNote(pi.getAbstract());
			//贷款账号
			info.setLoanAccountNo(NameRef.getAccountNoByID(pi.getLoanAccountID()));
			//借款单位，即自营贷款账户
			info.setLoanUnit(NameRef.getAccountNameByID(pi.getLoanAccountID()));
			if (pi.getReceiveAccountID() > 0)
			{
				//收款人账号
				info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
				//收款人开户银行名称
				info.setOpenBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			else if (pi.getReceiveBankID() > 0)
			{
				//收款人账号
				info.setAccountNo(pi.getExtAccountNo());
				//收款人开户银行名称
				info.setOpenBankName(pi.getExtRemitInBank());
			}
			info.setTransNo(pi.getTransNo());
			/*
			IPrintTemplate.showTrustGrantLoan1(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showTrustGrantLoan2(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			*/
			IPrintTemplate.showTrustGrantLoan3(info, out);
			//out.println("<br clear=all style='page-break-before:always'>");
			//IPrintTemplate.showTrustGrantLoan4(info, out);
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
			info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
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
			info.setContractRate(DataFormat.formatDisabledAmount(lpfdinfo.getInterestRate()));
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			info.setLoanInterval(DataFormat.getChineseDateString(lpfdinfo.getStart()) + " 至 " + DataFormat.getChineseDateString(lpfdinfo.getEnd()));
			info.setLoanType(LOANConstant.LoanType.getName(lpfdinfo.getLoanType()));
			info.setLoanUnit(NameRef.getClientNameByID(pi.getReceiveClientID()));
			info.setNote(pi.getAbstract());
			//贷款账号
			info.setLoanAccountNo(NameRef.getAccountNoByID(pi.getLoanAccountID()));
			//借款单位，即自营贷款账户
			info.setLoanUnit(NameRef.getAccountNameByID(pi.getLoanAccountID()));
			if (pi.getReceiveAccountID() > 0)
			{
				//收款人账号
				info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
				//收款人开户银行名称
				info.setOpenBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			else if (pi.getReceiveBankID() > 0)
			{
				//收款人账号
				info.setAccountNo(pi.getExtAccountNo());
				//收款人开户银行名称
				info.setOpenBankName(pi.getExtRemitInBank());
			}
			//手续费率
			info.setChargeRate(DataFormat.formatDisabledAmount(lpfdinfo.getPoundage()));
			info.setTransNo(pi.getTransNo());
			/*
			IPrintTemplate.showConsignGrantLoan1(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showConsignGrantLoan2(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			*/
			IPrintTemplate.showConsignGrantLoan3(info, out);
			//out.println("<br clear=all style='page-break-before:always'>");
			//IPrintTemplate.showConsignGrantLoan4(info, out);
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
			Sett_TransGrantDiscountDAO transDiscountDelegation = new Sett_TransGrantDiscountDAO();
			dcinfo = transDiscountDelegation.findDiscountCredenceByID(pi.getDiscountNoteID());
			///System.out.print("dcinfo:Id"+dcinfo.getID());
			info.setAbstract(pi.getAbstract() + "；贴现合同号：" + dcinfo.getDiscountContractCode() + "；贴现凭证编号：" + dcinfo.getCode());
			info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getDiscountAmount()));
			//持票人信息
			if (pi.getReceiveAccountID() > 0)
			{
				//持票人名称
				info.setBillKeeperName(NameRef.getAccountNameByID(pi.getReceiveAccountID()));
				//持票人账号
				info.setBillKeeperAccount(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
				//持票人开户银行名称
				info.setBillKeeperBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			else if (pi.getReceiveBankID() > 0)
			{
				//持票人名称
				info.setBillKeeperName(NameRef.getBankNameByID(pi.getReceiveBankID()));
				//持票人账号
				info.setBillKeeperAccount(pi.getExtAccountNo());
				//持票人开户银行名称
				info.setBillKeeperBankName(pi.getExtRemitInBank());
			}
			//特别说明:汇票出票人信息暂时不需要
			//汇票出票人账号
			info.setBillOutAccount("&nbsp;");
			//(dcinfo.getAcceptAccount());//汇票出票开户行
			info.setBillOutBankName("&nbsp;"); //(dcinfo.getAcceptBank());
			//汇票出票人名称
			info.setBillOutName("&nbsp;"); //dcinfo.getAcceptClientName());
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
			System.out.println("出票日:" + dcinfo.getPublicDate());
			//贴现利息
			info.setDiscountInterest(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getDiscountInterestAmount()));
			//贴现号码
			info.setDiscountNo(dcinfo.getCode());
			//贴现率
			//info.setDiscountRate(DataFormat.formatDisabledAmount(pi.getDiscountAmount() / pi.getDiscountBillAmount()));
			info.setDiscountRate(DataFormat.formatString(dcinfo.getDiscountRate() + ""));
			//贴现种类
			info.setDiscountType(LOANConstant.DraftType.getName(dcinfo.getDraftTypeID()));
			System.out.println("贴现种类ID:" + dcinfo.getDraftTypeID());
			System.out.println("贴现种类名称:" + LOANConstant.DraftType.getName(dcinfo.getDraftTypeID()));
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			//实付贴现金额
			info.setRealPayDiscountAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getDiscountAmount()));
			/*
			IPrintTemplate.showDiscountGrantLoan1(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showDiscountGrantLoan2(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			*/
			IPrintTemplate.showDiscountGrantLoan3(info, out);
			//out.println("<br clear=all style='page-break-before:always'>");
			//IPrintTemplate.showDiscountGrantLoan4(info, out);
			//out.println("<br clear=all style='page-break-before:always'>");
			//IPrintTemplate.showDiscountGrantLoan5(info, out);
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
			info.setDateDiscount(DataFormat.getChineseDateString(pi.getExecuteDate()));
			//凭证的findbyid方法
			IPrintTemplate iPrintTemplate = new IPrintTemplate();
			DiscountCredenceInfo discountCredenceInfo = null;
			discountCredenceInfo = iPrintTemplate.findDiscountCredenceByID(pi.getDiscountNoteID());
			if (discountCredenceInfo != null)
			{
				info.setAbstract(pi.getAbstract() + "；贴现合同号：" + discountCredenceInfo.getDiscountContractCode() + "；贴现凭证编号：" + discountCredenceInfo.getDraftCode());
				info.setBillType(LOANConstant.DraftType.getName(discountCredenceInfo.getDraftTypeID()));
				info.setDateDiscount(DataFormat.getChineseDateString(DataFormat.getDateTime(NameRef.getDiscountDateByDiscountBillID(pi.getDiscountBillID()))));
				info.setDateEnd(DataFormat.getChineseDateString(DataFormat.getDateTime(NameRef.getEndDateByDiscountBillID(pi.getDiscountBillID()))));
			}
			info.setDiscountAccount(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
			info.setDiscountName(NameRef.getAccountNameByID(pi.getReceiveAccountID()));
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			if ((pi.getDiscountAmount() - pi.getCurrentBalance()) > 0)
			{
				info.setTotalRepaymentAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getDiscountAmount() - pi.getCurrentBalance()));
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
			System.out.println("存款利息通知单");
			ShowPayInterestInfo info = new ShowPayInterestInfo();
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
			//活期利息：起始日期，终息日期，天数，积数，利率，利息
			info.setCurrentInterestDateStart(DataFormat.formatDate(pi.getStartDate()));
			info.setCurrentInterestDateEnd(DataFormat.formatDate(pi.getInterestStartDate()));
			info.setCurrentInterestDay((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), 1) + 1) + "");
			info.setCurrentIntegalAmount(
				DataFormat.formatDouble(
					2,
					DataFormat.formatDouble(IPrintTemplate.getTotalDailyAmountBanlance(pi.getPayAccountID(), pi.getStartDate(), pi.getInterestStartDate()))
						+ DataFormat.formatDouble(IPrintTemplate.getTotalDailyAccordAmountBanlance(pi.getPayAccountID(), pi.getStartDate(), pi.getInterestStartDate()))));
			info.setCurrentInterestRate(DataFormat.formatRate(pi.getRate()));
			info.setCurrentInterest(DataFormat.formatDisabledAmount(pi.getCurrentInterest()));
			//协定利息：起始日期，终息日期，天数，积数，利率，利息
			if (pi.getAccordInterest() > 0.0)
			{
				info.setAccordInterestDateStart(DataFormat.formatDate(pi.getStartDate()));
				info.setAccordInterestDateEnd(DataFormat.formatDate(pi.getInterestStartDate()));
				info.setAccordInterestDay((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), 1) + 1) + "");
				info.setAccordIntegalAmount(DataFormat.formatDouble(2, IPrintTemplate.getTotalDailyAccordAmountBanlance(pi.getPayAccountID(), pi.getStartDate(), pi.getInterestStartDate())));
				info.setAccordInterestRate(DataFormat.formatRate(pi.getAccordInterestRate()));
				info.setAccordInterest(DataFormat.formatDisabledAmount(pi.getAccordInterest()));
			}
			//利息总额
			info.setTotalInterest(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getCurrentInterest()) + DataFormat.formatDouble(pi.getAccordInterest())));
			//收息账号
			info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getReceiveInterestAccountID()));
			//对应账号
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
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			if (pi.getCheckUserID() < 0)
			{
				info.setCheckUserName("机制");
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
			//System.out.println("存款利息通知单");
			ShowPayInterestInfo info = new ShowPayInterestInfo();
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
			info.setCurrentInterestDay((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), 1) + 1) + "");
			info.setCurrentInterestAmount(
				DataFormat.formatDisabledAmount(
					IPrintTemplate.getCurrentAccountBanlance(pi.getPayAccountID(), pi.getStartDate(), pi.getInterestStartDate())
						/ (getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), 1) + 1)));
			info.setCurrentInterestRate(DataFormat.formatRate(pi.getRate() + ""));
			info.setCurrentInterest(DataFormat.formatDisabledAmount(pi.getCurrentInterest()));
			//协定利息：起始日期，终息日期，天数，本金，利率，利息
			if (pi.getAccordInterest() > 0.0)
			{
				info.setAccordInterestDateStart(DataFormat.formatDate(pi.getStartDate()));
				info.setAccordInterestDateEnd(DataFormat.formatDate(pi.getInterestStartDate()));
				info.setAccordInterestDay((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), 1) + 1) + "");
				info.setAccordInterestAmount(
					DataFormat.formatDisabledAmount(
						IPrintTemplate.getNegotiateBanlance(pi.getPayAccountID(), pi.getStartDate(), pi.getInterestStartDate())
							/ (getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), 1) + 1)));
				info.setAccordInterestRate(DataFormat.formatRate(pi.getAccordInterestRate() + ""));
				info.setAccordInterest(DataFormat.formatDisabledAmount(pi.getAccordInterest()));
			}
			//利息总额
			info.setTotalInterest(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getCurrentInterest()) + DataFormat.formatDouble(pi.getAccordInterest())));
			//收息账号
			info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getReceiveInterestAccountID()));
			//对应账号
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
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			if (pi.getCheckUserID() < 0)
			{
				info.setCheckUserName("机制");
			}
			System.out.print("天数1：" + info.getCurrentInterestDay());
			System.out.print("天数2：" + info.getAccordInterestDay());
			IPrintTemplate.showCurrentPayInterestNotice(info, out);
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
			ShowPayInterestInfo info = new ShowPayInterestInfo();
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
			//委托单位名称
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
					info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
				}
				else if (pi.getPayAccountID() > 0)
				{
					lPayAccountID = pi.getPayAccountID();
					info.setAccountName(NameRef.getAccountNameByID(pi.getPayAccountID()));
					info.setAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
				}
			}
			else
			{
				lPayAccountID = pi.getPayAccountID();
				info.setAccountName(NameRef.getAccountNameByID(pi.getPayAccountID()));
				info.setAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
			}
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
					info.setCompoundInterestDay((getIntervalDays(pi.getCompoundInterestStart(), pi.getCompoundInterestEnd(), 1) + 1) + "");
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
					info.setOverInterestDay((getIntervalDays(pi.getOverDueStart(), pi.getOverDueEnd(), 1) + 1) + "");
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
				info.setCommissionFeeDay((getIntervalDays(pi.getCommissionStart(), pi.getCommissionFeeEnd(), 1) + 1) + "");
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
				info.setAssureFeeDay((getIntervalDays(pi.getSuretyFeeStart(), pi.getSuretyFeeEnd(), 1) + 1) + "");
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
						DataFormat.formatDouble(pi.getRealInterest()) + DataFormat.formatDouble(pi.getRealCompoundInterest()) + DataFormat.formatDouble(pi.getRealOverDueInterest())),
					pi.getCurrencyID()));
			if (DataFormat.formatDouble(pi.getRealInterest()) + DataFormat.formatDouble(pi.getRealCompoundInterest()) + DataFormat.formatDouble(pi.getRealOverDueInterest()) == 0.0)
			{
				info.setTotalInterest("0.00");
			}
			//利息账号
			info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getPayInterestAccountID()));
			//对应的活期账号
			info.setCurrentAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
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
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			if (pi.getCheckUserID() < 0)
			{
				info.setCheckUserName("机制");
			}
			System.out.println("贷款类型:" + pi.getTransTypeID());
			if (pi.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE)
			{
				System.out.println("贷款类型－自营");
				IPrintTemplate.showTrustPayInterestNotice(info, out);
				//out.println("<br clear=all style='page-break-before:always'>");
			}
			else if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
			{
				IPrintTemplate.showConsignPayInterestNotice(info, out);
				//out.println("<br clear=all style='page-break-before:always'>");
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
			ShowPayInterestInfo info = new ShowPayInterestInfo();
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
			//委托单位名称
			info.setConsignClientName(NameRef.getClientNameByID(pi.getConsignClientID()));
			//账号类型
			info.setAccountType(SETTConstant.AccountType.getName(pi.getAccountTypeID()));
			//交易编号
			info.setTransNo(pi.getTransNo());
			//账号
			long lPayAccountID = -1;
			if (pi.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE || pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
			{
				info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
				lPayAccountID = pi.getReceiveAccountID();
			}
			else
			{
				info.setAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
				lPayAccountID = pi.getPayAccountID();
			}
			//正常利息
			//加入分段利息
			SubsectionInterest dao = new SubsectionInterest();
			SubsectionDateInfo PrintSubsectionInfo = new SubsectionDateInfo();
			PrintSubsectionInfo = dao.getSubsectionInterest(lPayAccountID, -1, pi.getLoanNoteID(), pi.getLatestInterestClearDate(), DataFormat.getPreviousDate(pi.getInterestClearDate()));
			info.setNormalInterestDateStart(PrintSubsectionInfo.getPrintStartDate()); //正常利息开始日期
			info.setNormalInterestDateEnd(PrintSubsectionInfo.getPrintEndDate());
			info.setNormalInterestAmount(PrintSubsectionInfo.getPrintBalance()); //正常利息本金
			info.setNormalInterestRate(PrintSubsectionInfo.getPrintInterestRate()); //正常利息率
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
					info.setCompoundInterestDay((getIntervalDays(pi.getCompoundInterestStart(), pi.getInterestStartDate(), 1)) + "");
				}
				info.setCompoundInterestAmount("&nbsp;");
				//复利利率
				info.setCompoundInterestRate(DataFormat.formatRate(pi.getCompoundRate() + ""));
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
					info.setOverInterestDay((getIntervalDays(pi.getOverDueStart(), pi.getInterestClearDate(), 1)) + "");
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
				info.setCommissionFeeDay((getIntervalDays(pi.getCommissionStart(), pi.getInterestClearDate(), 1)) + "");
				//手续费利息本金		
				info.setCommissionFeeAmount(DataFormat.formatDisabledAmount(pi.getAmount()));
				if (pi.getAmount() == 0.00)
				{
					info.setCommissionFeeAmount("0.00");
				}
				//手续费利率				
				System.out.print("========手续费利率：" + pi.getCommissionRate());
				info.setCommissionFeeRate(DataFormat.formatRate(pi.getCommissionRate() + ""));
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
				info.setAssureFeeDay((getIntervalDays(pi.getSuretyFeeStart(), pi.getInterestClearDate(), 1)) + "");
				//担保费利息本金		
				info.setAssureFeeAmount(DataFormat.formatDisabledAmount(pi.getAmount()));
				if (pi.getAmount() == 0.0)
				{
					info.setAssureFee("0.00");
				}
				//担保费利率
				info.setAssureFeeRate(DataFormat.formatRate(pi.getSuretyFeeRate() + ""));
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
						DataFormat.formatDouble(pi.getRealInterest()) + DataFormat.formatDouble(pi.getRealCompoundInterest()) + DataFormat.formatDouble(pi.getRealOverDueInterest())),
					pi.getCurrencyID()));
			if (DataFormat.formatDouble(pi.getRealInterest()) + DataFormat.formatDouble(pi.getRealCompoundInterest()) + DataFormat.formatDouble(pi.getRealOverDueInterest()) == 0.0)
			{
				info.setTotalInterest("0.00");
			}
			//利息账号
			info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getPayInterestAccountID()));
			//对应的账号
			info.setCurrentAccountNo(NameRef.getAccountNoByID(lPayAccountID));
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
				info.setCheckUserName("机制");
			}
			System.out.println("贷款类型:" + pi.getTransTypeID());
			if (pi.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE)
			{
				IPrintTemplate.showTrustPayInterestNotice3(info, out);
			}
			else if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
			{
				IPrintTemplate.showConsignPayInterestNotice3(info, out);
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
			info.setAccountName(NameRef.getAccountNameByID(pi.getReceiveAccountID()));
			info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
			info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getAmount()));
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			info.setChineseAmount(ChineseCurrency.showChinese(DataFormat.formatAmount(pi.getAmount()), pi.getCurrencyID()));
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
			info.setDateOpenAccount(DataFormat.getChineseDateString(pi.getExecuteDate()));
			info.setDepositBillNo(pi.getFixedDepositNo());
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			info.setStartInterestDate(DataFormat.getChineseDateString(pi.getInterestStartDate()));
			info.setTransNo(pi.getTransNo());
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
			/*
			IPrintTemplate.showOpenAccountNotice1(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showOpenAccountNotice2(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			*/
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
	         *  定期存款开户证实书打印 
	         * @throws Exception
	         */
	public static void PrintFixedDepositOpen(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			ShowFixedDepositOpenInfo info = new ShowFixedDepositOpenInfo();
			info.setAccountName(NameRef.getAccountNameByID(pi.getReceiveAccountID()));
			info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
			if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
			{
				//如果是定期续存,开户金额为本金+计提利息+利息支付(2004/4/30)
				pi.setAmount(DataFormat.formatDouble(pi.getAmount()) + DataFormat.formatDouble(pi.getRealInterest()));
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
			/*IPrintTemplate.showFixOpenAccountNotice1(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showFixOpenAccountNotice2(info, out);
			out.println("<br clear=all style='page-break-before:always'>");*/

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
	         *  存款利息计付通知单(用于通知存款 定期存款的收回凭证) 
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
			info.setAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
			info.setClientName(NameRef.getClientNameByAccountID(pi.getPayAccountID()));
			info.setDepositBillNo(pi.getBillNo());
			String strTemp = "";
			if (pi.getCurrentInterest() != 0.0) //有活期利息
			{
				System.out.println("====1=====");
				if (pi.getInterestStartDate().before(pi.getEndDate())) //如起息日在终止日期之前，即提前支取
				{
					//本金1，活期利息，活期利息利率，开始日期，结束日期，天数
					info.setAmount1(DataFormat.formatAmount(pi.getAmount()));
					info.setInterest1(DataFormat.formatAmount(pi.getCurrentInterest()));
					info.setRate1(DataFormat.formatAmount(0.72));
					System.out.println("利率1" + info.getRate1());
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
						System.out.println("====2=====");
						//定期支取&定期续存
						info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), 2)) + "");
					}
					else if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
					{
						System.out.println("====3=====");
						//通知支取
						info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), 1)) + "");
					}
					else
					{
						System.out.println("====4=====");
						info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), 1) + 1) + "");
					}
					//本金2，其它利息(可能有)，其它利息利率(无)，开始日期，结束日期，天数
					if (pi.getOtherInterest() != 0.0)
					{
						System.out.println("====5=====");
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
						if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER || pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
						{
							System.out.println("====6=====");
							//定期支取&定期续存
							info.setDayNumber2((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), 2)) + "");
						}
						else if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
						{
							System.out.println("====7=====");
							//通知支取
							info.setDayNumber2((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), 1)) + "");
						}
						else
						{
							System.out.println("====8=====");
							info.setDayNumber2((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), 1) + 1) + "");
						}
					}
					//利息合计
					info.setTotalInterest(DataFormat.formatAmount(DataFormat.formatDouble(DataFormat.formatDouble(pi.getCurrentInterest()) + DataFormat.formatDouble(pi.getOtherInterest()))));
				}
				else //如起息日在终止日期之后
					{
					System.out.println("====9=====");
					//本金1，利息支出，利息支出利率，开始日期，结束日期，天数
					info.setAmount1(DataFormat.formatAmount(pi.getAmount()));
					info.setInterest1(
						DataFormat.formatAmount(
							DataFormat.formatDouble(
								DataFormat.formatDouble(pi.getPayableInterest()) - DataFormat.formatDouble(pi.getCurrentInterest()) - DataFormat.formatDouble(pi.getOtherInterest()))));
					info.setRate1(String.valueOf(pi.getRate()));
					strTemp = DataFormat.formatDate(pi.getStartDate());
					info.setBeginYear1(strTemp.substring(0, 4));
					info.setBeginMonth1(strTemp.substring(5, 7));
					info.setBeginDay1(strTemp.substring(8, 10));
					strTemp = DataFormat.formatDate(pi.getEndDate());
					info.setOverYear1(strTemp.substring(0, 4));
					info.setOverMonth1(strTemp.substring(5, 7));
					info.setOverDay1(strTemp.substring(8, 10));
					if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER || pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
					{
						System.out.println("====10=====");
						//定期支取&定期续存
						info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), 2)) + "");
					}
					else if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
					{
						System.out.println("====11=====");
						//通知支取
						info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), 1)) + "");
					}
					else
					{
						System.out.println("====12=====");
						info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), 1) + 1) + "");
					}
					//本金2，活期利息，活期利息利率，开始日期，结束日期，天数
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
					if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER || pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
					{
						System.out.println("====13=====");
						//定期支取&定期续存
						info.setDayNumber2((getIntervalDays(pi.getEndDate(), pi.getInterestStartDate(), 1)) + "");

					}
					else if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
					{
						System.out.println("====14=====");
						//通知支取
						info.setDayNumber2((getIntervalDays(pi.getEndDate(), pi.getInterestStartDate(), 1)) + "");
					}
					else
					{
						System.out.println("====15=====");
						info.setDayNumber2((getIntervalDays(pi.getEndDate(), pi.getInterestStartDate(), 1) + 1) + "");
					}
					if (pi.getOtherInterest() != 0.0) //其它利息
					{
						System.out.println("====16=====");
						//本金3，其它利息(可能有)，其它利息利率(无)，开始日期，结束日期，天数
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
						if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER || pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
						{
							System.out.println("====17=====");
							//定期支取&定期续存
							info.setDayNumber3((getIntervalDays(pi.getEndDate(), pi.getInterestStartDate(), 2)) + "");
						}
						else if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
						{
							System.out.println("====18=====");
							//通知支取
							info.setDayNumber3((getIntervalDays(pi.getEndDate(), pi.getInterestStartDate(), 1)) + "");
						}
						else
						{
							System.out.println("====19=====");
							info.setDayNumber3((getIntervalDays(pi.getEndDate(), pi.getInterestStartDate(), 1) + 1) + "");
						}
					}
					//利息合计
					info.setTotalInterest(DataFormat.formatAmount(DataFormat.formatDouble(pi.getPayableInterest())));
				}
			}
			else //没有活期利息
				{
				System.out.println("====20=====");
				//本金1，利息支出，利息支出利率，开始日期，结束日期，天数
				info.setAmount1(DataFormat.formatAmount(pi.getAmount()));
				info.setInterest1(DataFormat.formatAmount(pi.getPayableInterest()));
				info.setRate1(String.valueOf(pi.getRate()));
				strTemp = DataFormat.formatDate(pi.getStartDate());
				info.setBeginYear1(strTemp.substring(0, 4));
				info.setBeginMonth1(strTemp.substring(5, 7));
				info.setBeginDay1(strTemp.substring(8, 10));
				strTemp = DataFormat.formatDate(pi.getEndDate());
				info.setOverYear1(strTemp.substring(0, 4));
				info.setOverMonth1(strTemp.substring(5, 7));
				info.setOverDay1(strTemp.substring(8, 10));
				if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER || pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
				{
					System.out.println("====21=====");
					//定期支取&定期续存
					info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), 2)) + "");
				}
				else if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
				{
					System.out.println("====22=====");
					//通知支取
					info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), 1)) + "");
				}
				else
				{
					System.out.println("====23=====");
					info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), 1) + 1) + "");
				}
				//利息合计
				info.setTotalInterest(DataFormat.formatAmount(DataFormat.formatDouble(pi.getPayableInterest())));
			}
			if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
			{
				//如果是定期续存
				//计提利息
				if (pi.getRealInterestReceivable() > 0)
				{
					System.out.println("====24=====");
					info.setAmount1(DataFormat.formatAmount(pi.getAmount()));
					info.setInterest1(DataFormat.formatAmount(pi.getRealInterestReceivable()));
					info.setRate1(String.valueOf(pi.getRate()));
					strTemp = DataFormat.formatDate(pi.getStartDate());
					info.setBeginYear1(strTemp.substring(0, 4));
					info.setBeginMonth1(strTemp.substring(5, 7));
					info.setBeginDay1(strTemp.substring(8, 10));
					strTemp = DataFormat.formatDate(pi.getEndDate());
					info.setOverYear1(strTemp.substring(0, 4));
					info.setOverMonth1(strTemp.substring(5, 7));
					info.setOverDay1(strTemp.substring(8, 10));
					if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER || pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
					{
						//定期支取&定期续存
						info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), 2)) + "");
					}
					else if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
					{
						//通知支取
						info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), 1)) + "");
					}
					else
					{
						info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), 1) + 1) + "");
					}
					if (pi.getPayableInterest() > 0)
					{
						System.out.println("====25=====");
						info.setAmount2(DataFormat.formatAmount(pi.getAmount()));
						info.setInterest2(DataFormat.formatAmount(pi.getPayableInterest()));
						info.setRate2(String.valueOf(pi.getRate()));
						strTemp = DataFormat.formatDate(pi.getStartDate());
						info.setBeginYear2(strTemp.substring(0, 4));
						info.setBeginMonth2(strTemp.substring(5, 7));
						info.setBeginDay2(strTemp.substring(8, 10));
						strTemp = DataFormat.formatDate(pi.getEndDate());
						info.setOverYear2(strTemp.substring(0, 4));
						info.setOverMonth2(strTemp.substring(5, 7));
						info.setOverDay2(strTemp.substring(8, 10));
						if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER || pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
						{
							//定期支取&定期续存
							info.setDayNumber2((getIntervalDays(pi.getStartDate(), pi.getEndDate(), 2)) + "");
						}
						else if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
						{
							//通知支取
							info.setDayNumber2((getIntervalDays(pi.getStartDate(), pi.getEndDate(), 1)) + "");
						}
						else
						{
							info.setDayNumber2((getIntervalDays(pi.getStartDate(), pi.getEndDate(), 1) + 1) + "");
						}
					}
				}
				else if (pi.getPayableInterest() > 0)
				{
					System.out.println("====26=====");
					info.setAmount1(DataFormat.formatAmount(pi.getAmount()));
					info.setInterest1(DataFormat.formatAmount(pi.getPayableInterest()));
					info.setRate1(String.valueOf(pi.getRate()));
					strTemp = DataFormat.formatDate(pi.getStartDate());
					info.setBeginYear1(strTemp.substring(0, 4));
					info.setBeginMonth1(strTemp.substring(5, 7));
					info.setBeginDay1(strTemp.substring(8, 10));
					strTemp = DataFormat.formatDate(pi.getEndDate());
					info.setOverYear1(strTemp.substring(0, 4));
					info.setOverMonth1(strTemp.substring(5, 7));
					info.setOverDay1(strTemp.substring(8, 10));
					if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER || pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
					{
						//定期支取&定期续存
						info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), 2)) + "");
					}
					else if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
					{
						//通知支取
						info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), 1)) + "");
					}
					else
					{
						info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), 1) + 1) + "");
					}
				}
				//利息合计
				info.setTotalInterest(DataFormat.formatAmount(DataFormat.formatDouble(DataFormat.formatDouble(pi.getRealInterestReceivable()) + DataFormat.formatDouble(pi.getPayableInterest()))));
			}
			info.setTotalInterestChinese(ChineseCurrency.showChinese(info.getTotalInterest(), pi.getCurrencyID()));
			//利息账号
			info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getReceiveInterestAccountID()));
			//存款种类
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
			//out.println("<br clear=all style='page-break-before:always'>");
			//IPrintTemplate.showDepositInterest2(info, out);
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
				DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getInterest()) + DataFormat.formatDouble(pi.getCompoundInterest()) + DataFormat.formatDouble(pi.getOverDueInterest())));
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
				strMonth = Long.valueOf(DataFormat.getMonthString(pi.getStartDate())).longValue() + 1 + "";
				strDay = DataFormat.getDayString(pi.getStartDate());
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
				strMonth = Long.valueOf(DataFormat.getMonthString(pi.getEndDate())).longValue() + 1 + "";
				strDay = DataFormat.getDayString(pi.getEndDate());
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
			IPrintTemplate.showConsignLoanInterestAdviceNotice(info, out);
			//out.println("<br clear=all style='page-break-before:always'>");
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
					strMonth = Long.valueOf(DataFormat.getMonthString(contractInfo.getLoanStart())).longValue() + 1 + "";
					strDay = DataFormat.getDayString(contractInfo.getLoanStart());
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
				strMonth = Long.valueOf(DataFormat.getMonthString(pi.getClearInterestDate())).longValue() + 1 + "";
				strDay = DataFormat.getDayString(pi.getClearInterestDate());
				strTemp = "<u>&nbsp;" + strYear + "&nbsp;</u>年<u>&nbsp;" + strMonth + "&nbsp;</u>月<u>&nbsp;" + strDay + "&nbsp;</u>日";
				info.setDateDuePay(strTemp);
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
					DataFormat.formatDouble(DataFormat.formatDouble(pi.getInterest()) + DataFormat.formatDouble(pi.getCompoundInterest()) + DataFormat.formatDouble(pi.getOverDueInterest()))));
			//贷款本金
			info.setLoanAmount(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getAmount())));
			//贷款余额
			if (pi.getLoanBalance() > 0)
			{
				info.setLoanBalance(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getLoanBalance())));
			}
			else
			{
				info.setLoanBalance("0.00");
			}
			//合同利率
			info.setContractRate(String.valueOf(DataFormat.formatDouble(pi.getContractRate(), 6)));
			//执行利率
			info.setExcecuteRate(String.valueOf(DataFormat.formatDouble(pi.getExecuteRate(), 6)));
			//贷款利率调整日期
			if (pi.getAdjustRateDate() != null)
			{
				strYear = DataFormat.getYearString(pi.getAdjustRateDate());
				strMonth = Long.valueOf(DataFormat.getMonthString(pi.getAdjustRateDate())).longValue() + 1 + "";
				strDay = DataFormat.getDayString(pi.getAdjustRateDate());
				strTemp = "<u>&nbsp;" + strYear + "&nbsp;</u>年<u>&nbsp;" + strMonth + "&nbsp;</u>月<u>&nbsp;" + strDay + "&nbsp;</u>日";
				info.setDateRateModify(strTemp);
			}
			else
			{
				info.setDateRateModify("<u>&nbsp;&nbsp;&nbsp;&nbsp;</u>年<u>&nbsp;&nbsp;&nbsp;&nbsp;</u>月<u>&nbsp;&nbsp;&nbsp;&nbsp;</u>日");
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
				strMonth = Long.valueOf(DataFormat.getMonthString(pi.getStartDate())).longValue() + 1 + "";
				strDay = DataFormat.getDayString(pi.getStartDate());
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
				strMonth = Long.valueOf(DataFormat.getMonthString(pi.getEndDate())).longValue() + 1 + "";
				strDay = DataFormat.getDayString(pi.getEndDate());
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
						DataFormat.formatDouble(DataFormat.formatDouble(pi.getInterest()) + DataFormat.formatDouble(pi.getOverDueInterest()) + DataFormat.formatDouble(pi.getCompoundInterest()))));
			//盖章日期（年）
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
			IPrintTemplate.showPayLoanInterestNotice(info, out);

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
			boolean bIsFirst = true;
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
					if (pi.getAdjustRateDate().after(pi.getInterestStartDate()) && (pi.getAdjustRateDate().before(pi.getClearInterestDate()) || pi.getAdjustRateDate() == pi.getClearInterestDate()))
					{
						//调整日期在起息日和结息日之间
						//执行利率
						tmpPrintInfo.setExecuteRate(pi.getExecuteRateNew());
						//贷款利率调整日期
						tmpPrintInfo.setAdjustRateDate(pi.getAdjustRateDate());
						//调整后年息
						tmpPrintInfo.setExecuteRateNew(pi.getExecuteRateNew());
					}
					else if (!pi.getAdjustRateDate().after(pi.getInterestStartDate()))
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
							+ "<table width=\"630\" border=\"0\">"
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
							+ "<table border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"table1\" width=\"640\">"
							+ "<tr>"
							+ "<td class=\"td-right\" align=\"center\">放款通知单编号</td>"
							+ "<td class=\"td-right\" align=\"center\">放款金额</td>"
							+ "<td class=\"td-right\" align=\"center\">放款日期</td>"
							+ "<td class=\"td-right\" align=\"center\">起息日</td>"
							+ "<td class=\"td-right\" align=\"center\">止息日</td>"
							+ "<td class=\"td-right\" align=\"center\">利率％</td>"
							+ "<td class=\"td-right\" align=\"center\">应付利息</td>"
							+ "<td class=\"td-right\" align=\"center\">应付复利</td>"
							+ "<td class=\"td-right\" align=\"center\">应付利息费用合计</td>"
							+ "</tr>");
				}
				out.println(
					"<tr>"
						+ "<td class=\"td-topright\" align=\"center\"><font style=\"font-size:12px\">"
						+ NameRef.getPayFormNoByID(pi.getLoanNoteID())
						+ "</font></td>"
						+ "<td class=\"td-topright\" align=\"right\"><font style=\"font-size:12px\">"
						+ DataFormat.formatDisabledAmount(DataFormat.formatDouble(lpfdinfo.getAmount()))
						+ "</font></td>"
						+ "<td class=\"td-topright\"><font style=\"font-size:12px\">"
						+ DataFormat.formatDate(lpfdinfo.getInterestStart())
						+ "</font></td>"
						+ "<td class=\"td-topright\"><font style=\"font-size:12px\">"
						+ DataFormat.formatDate(pi.getInterestStartDate())
						+ "</font></td>"
						+ "<td class=\"td-topright\"><font style=\"font-size:12px\">"
						+ DataFormat.formatDate(DataFormat.getPreviousDate(pi.getClearInterestDate()))
						+ "</font></td>"
						+ "<td class=\"td-topright\"><font style=\"font-size:12px\">"
						+ String.valueOf(tmpPrintInfo.getExecuteRate())
						+ "</font></td>"
						+ "<td class=\"td-topright\" align=\"right\"><font style=\"font-size:12px\">"
						+ "￥"
						+ DataFormat.formatDisabledAmount(pi.getInterest())
						+ "</font></td>"
						+ "<td class=\"td-topright\" align=\"right\"><font style=\"font-size:12px\">"
						+ "￥"
						+ DataFormat.formatDisabledAmount(pi.getCompoundInterest())
						+ "</font></td>"
						+ "<td class=\"td-top\" align=\"right\"><font style=\"font-size:12px\">"
						+ "￥"
						+ DataFormat.formatDisabledAmount(DataFormat.formatDouble(DataFormat.formatDouble(pi.getInterest()) + DataFormat.formatDouble(pi.getCompoundInterest())))
						+ "</font></td>"
						+ "</tr>");
			} //end for
			out.println(
				"<tr>"
					+ "<td class=\"td-topright\" align=\"center\"><font style=\"font-size:12px\"><B>合计</B></font></td>"
					+ "<td class=\"td-topright\" align=\"right\"><font style=\"font-size:12px\">"
					+ "￥"
					+ DataFormat.formatDisabledAmount(dSumLoanAmount)
					+ "</font></td>"
					+ "<td class=\"td-topright\"><font style=\"font-size:12px\">&nbsp;</font></td>"
					+ "<td class=\"td-topright\"><font style=\"font-size:12px\">&nbsp;</font></td>"
					+ "<td class=\"td-topright\"><font style=\"font-size:12px\">&nbsp;</font></td>"
					+ "<td class=\"td-topright\"><font style=\"font-size:12px\">&nbsp;</font></td>"
					+ "<td class=\"td-topright\" align=\"right\"><font style=\"font-size:12px\">"
					+ "￥"
					+ DataFormat.formatDisabledAmount(dSumInterest)
					+ "</font></td>"
					+ "<td class=\"td-topright\" align=\"right\"><font style=\"font-size:12px\">"
					+ "￥"
					+ DataFormat.formatDisabledAmount(dSumCompoundInterest)
					+ "</font></td>"
					+ "<td class=\"td-top\" align=\"right\"><font style=\"font-size:12px\">"
					+ "￥"
					+ DataFormat.formatDisabledAmount(DataFormat.formatDouble(dSumInterest + dSumCompoundInterest))
					+ "</font></td>"
					+ "</tr>");
			out.println("</table></td></tr></table>" + "</body>");
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
					strMonth = Long.valueOf(DataFormat.getMonthString(contractInfo.getLoanStart())).longValue() + 1 + "";
					strDay = DataFormat.getDayString(contractInfo.getLoanStart());
					strTemp = strYear + "&nbsp;年&nbsp;" + strMonth + "&nbsp;月&nbsp;" + strDay + "&nbsp;日";
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
					strMonth = Long.valueOf(DataFormat.getMonthString(contractInfo.getLoanEnd())).longValue() + 1 + "";
					strDay = DataFormat.getDayString(contractInfo.getLoanEnd());
					strTemp = strYear + "&nbsp;年&nbsp;" + strMonth + "&nbsp;月&nbsp;" + strDay + "&nbsp;日";
					info.setDateEnd(strTemp);
				}
			}
			//截至日期--取结息日
			if (pi.getClearInterestDate() != null)
			{
				strYear = DataFormat.getYearString(pi.getClearInterestDate());
				strMonth = Long.valueOf(DataFormat.getMonthString(pi.getClearInterestDate())).longValue() + 1 + "";
				strDay = DataFormat.getDayString(pi.getClearInterestDate());
				strTemp = strYear + "&nbsp;年&nbsp;" + strMonth + "&nbsp;月&nbsp;" + strDay + "&nbsp;日";
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
					DataFormat.formatDouble(DataFormat.formatDouble(pi.getInterest()) + DataFormat.formatDouble(pi.getOverDueInterest()) + DataFormat.formatDouble(pi.getCompoundInterest()))));
			//未还手续费
			info.setOwnComissionFee(DataFormat.formatDisabledAmount(pi.getCommission()));
			if (pi.getCommission() == 0.0)
			{
				info.setOwnComissionFee("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			}
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
					DataFormat.formatDouble((DataFormat.formatDouble(pi.getInterest()) + DataFormat.formatDouble(pi.getOverDueInterest()) + DataFormat.formatDouble(pi.getCompoundInterest())))));
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
			IPrintTemplate.showConsignLoanAtTermAdviceNotice(info, out);
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
				DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getInterest()) + DataFormat.formatDouble(pi.getOverDueInterest()) + DataFormat.formatDouble(pi.getCompoundInterest())));
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
		*  利息通知单(支取)打印
		* @throws Exception
		*/
	public static void IPrintInterestNotice(PrintInfo pi, JspWriter out) throws Exception
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
			//借款单位名称
			if (pi.getBorrowClientID() > 0)
			{
				info.setClientName(NameRef.getClientNameByID(pi.getBorrowClientID()));
			}
			//委托单位名称
			ContractDao contractDao = new ContractDao();
			ContractInfo contractInfo = null;
			contractInfo = contractDao.findByID(pi.getContractID());
			info.setConsignClientName(NameRef.getClientNameByID(contractInfo.getClientID()));
			//账号类型
			info.setAccountType(SETTConstant.AccountType.getName(pi.getAccountTypeID()));
			//交易编号
			info.setTransNo(pi.getTransNo());
			//账号
			info.setAccountNo(NameRef.getAccountNoByID(pi.getConsignAccountID()));
			//正常利息
			//加入分段利息
			SubsectionInterest dao = new SubsectionInterest();
			SubsectionDateInfo PrintSubsectionInfo = new SubsectionDateInfo();
			PrintSubsectionInfo = dao.getSubsectionInterest(pi.getConsignAccountID(), -1, pi.getLoanNoteID(), pi.getLatestInterestClearDate(), DataFormat.getPreviousDate(pi.getInterestClearDate()));
			info.setNormalInterestDateStart(PrintSubsectionInfo.getPrintStartDate()); //正常利息开始日期
			info.setNormalInterestDateEnd(PrintSubsectionInfo.getPrintEndDate());
			info.setNormalInterestAmount(PrintSubsectionInfo.getPrintBalance()); //正常利息本金
			info.setNormalInterestRate(PrintSubsectionInfo.getPrintInterestRate()); //正常利息率
			info.setNormalInterest(PrintSubsectionInfo.getPrintInterest()); //正常利息
			info.setNormalInterestDay(PrintSubsectionInfo.getPrintDays());
			//加入分段利息End
			/*
			 LoanPayFormDetailInfo lpfdinfo = new LoanPayFormDetailInfo();
			lpfdinfo.setLoadNoteID(pi.getLoanNoteID());
			Sett_TransGrantLoanDAO stdao = new Sett_TransGrantLoanDAO();
			lpfdinfo = stdao.findPayFormDetailByCondition(lpfdinfo);
			 //正常利息起息日期
			info.setNormalInterestDateStart(DataFormat.formatDate(pi.getLatestInterestClearDate()));
			//正常利息终息日期			
			info.setNormalInterestDateEnd(DataFormat.formatDate(DataFormat.getPreviousDate(pi.getInterestClearDate())));
			//正常利息天数
			if (pi.getLatestInterestClearDate() != null && pi.getInterestClearDate() != null)
			{
				info.setNormalInterestDay((getIntervalDays(pi.getLatestInterestClearDate(), pi.getInterestClearDate(), 1)) + "");
			}
			//正常利息本金
			info.setNormalInterestAmount(DataFormat.formatDisabledAmount(pi.getLoanBalance()));
			if (pi.getAmount() == 0.0)
			{
				info.setNormalInterestAmount("0.00");
			}
			//正常利息利率
			info.setNormalInterestRate(DataFormat.formatRate(lpfdinfo.getInterestRate() + ""));
			//正常利息
			info.setNormalInterest(DataFormat.formatDisabledAmount(pi.getRealInterest()));
			if (pi.getRealInterest() == 0.0)
			{
				info.setNormalInterest("0.0");
			}*/
			//复利
			if (pi.getRealCompoundInterest() > 0)
			{
				//复利利息起息日期
				if (pi.getCompoundInterestStart() != null)
				{
					info.setCompoundInterestDateStart(DataFormat.formatDate(pi.getCompoundInterestStart()));
				}
				//复利利息终息日期
				info.setCompoundInterestDateEnd(DataFormat.formatDate(DataFormat.getPreviousDate(pi.getInterestClearDate())));
				//复利利息天数
				if (pi.getCompoundInterestStart() != null)
				{
					info.setCompoundInterestDay((getIntervalDays(pi.getCompoundInterestStart(), pi.getInterestClearDate(), 1)) + "");
				}
				info.setCompoundInterestAmount("&nbsp;");
				//复利利率
				info.setCompoundInterestRate(DataFormat.formatRate(pi.getCompoundRate() + ""));
				//复利利息	
				info.setCompoundInterest(DataFormat.formatDisabledAmount(pi.getRealCompoundInterest()));
				if (pi.getRealCompoundInterest() == 0.0)
				{
					info.setCompoundInterest("0.0");
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
				info.setOverInterestDateEnd(DataFormat.formatDate(DataFormat.getPreviousDate(pi.getInterestClearDate())));
				//逾期罚息利息天数
				if (pi.getOverDueStart() != null)
				{
					info.setOverInterestDay((getIntervalDays(pi.getOverDueStart(), pi.getInterestClearDate(), 1)) + "");
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
				if (pi.getRealOverDueInterest() == 0.0)
				{
					info.setOverInterest("0.0");
				}
			}
			//手续费
			if (pi.getRealCommission() > 0)
			{
				//手续费利息起息日期
				info.setCommissionFeeDateStart(DataFormat.formatDate(pi.getCommissionStart()));
				//手续费利息终息日期
				info.setCommissionFeeDateEnd(DataFormat.formatDate(DataFormat.getPreviousDate(pi.getInterestClearDate())));
				//手续费利息天数
				info.setCommissionFeeDay((getIntervalDays(pi.getCommissionStart(), pi.getInterestClearDate(), 1)) + "");
				//手续费利息本金		
				info.setCommissionFeeAmount(DataFormat.formatDisabledAmount(pi.getLoanBalance()));
				if (pi.getAmount() == 0.00)
				{
					info.setCommissionFeeAmount("0.00");
				}
				//手续费利率				
				info.setCommissionFeeRate(DataFormat.formatRate(pi.getCommissionRate() + ""));
				//手续费利息
				info.setCommissionFee(DataFormat.formatDisabledAmount(pi.getRealCommission()));
				if (pi.getRealCommission() == 0.0)
				{
					info.setCommissionFee("0.0");
				}
			}
			if (pi.getRealSuretyFee() > 0)
			{
				//担保费
				//担保费利息起息日期
				info.setAssureFeeDateStart(DataFormat.formatDate(pi.getSuretyFeeStart()));
				//担保费利息终息日期
				info.setAssureFeeDateEnd(DataFormat.formatDate(DataFormat.getPreviousDate(pi.getInterestClearDate())));
				//担保费利息天数				
				info.setAssureFeeDay((getIntervalDays(pi.getSuretyFeeStart(), pi.getInterestClearDate(), 1)) + "");
				//担保费利息本金		
				info.setAssureFeeAmount(DataFormat.formatDisabledAmount(pi.getAmount()));
				if (pi.getAmount() == 0.0)
				{
					info.setAssureFee("0.00");
				}
				//担保费利率
				info.setAssureFeeRate(DataFormat.formatRate(pi.getSuretyFeeRate() + ""));
				//担保费利息
				info.setAssureFee(DataFormat.formatDisabledAmount(pi.getRealSuretyFee()));
				if (pi.getRealSuretyFee() == 0.0)
				{
					info.setAssureFee("0.0");
				}
			}
			//利息总额
			info.setTotalInterest(
				DataFormat.formatDisabledAmount(
					DataFormat.formatDouble(pi.getRealInterest()) + DataFormat.formatDouble(pi.getRealCompoundInterest()) + DataFormat.formatDouble(pi.getRealOverDueInterest())));
			info.setTotalInterestChinese(
				ChineseCurrency.showChinese(
					DataFormat.formatAmount(
						DataFormat.formatDouble(pi.getRealInterest()) + DataFormat.formatDouble(pi.getRealCompoundInterest()) + DataFormat.formatDouble(pi.getRealOverDueInterest())),
					pi.getCurrencyID()));
			if (DataFormat.formatDouble(pi.getRealInterest()) + DataFormat.formatDouble(pi.getRealCompoundInterest()) + DataFormat.formatDouble(pi.getRealOverDueInterest()) == 0.0)
			{
				info.setTotalInterest("0.00");
			}
			//利息账号
			info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getPayInterestAccountID()));
			//对应的账号
			info.setCurrentAccountNo(NameRef.getAccountNoByID(pi.getConsignAccountID()));
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
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			if (pi.getCheckUserID() < 0)
			{
				info.setCheckUserName("机制");
			}
			System.out.println("贷款类型:" + pi.getTransTypeID());
			if (pi.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE)
			{
				IPrintTemplate.showTrustPayInterestNotice3(info, out);
			}
			else if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
			{
				IPrintTemplate.showConsignPayInterestNotice3(info, out);
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
		 * Method PrintSynLoanGrant.
		 * 银团贷款发放凭证
		 * @param pi
		 * @param out
		 * @throws Exception
		 */
	public static void PrintSynLoanGrant(PrintInfo pi, JspWriter out) throws Exception
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
			info.setAmount(PrintVoucher.getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getAmount()));
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
			//代理费利率
			info.setAgentFeeRate(DataFormat.formatRate(String.valueOf(lpfdinfo.getCommissionRate())));
			//委托单位
			info.setConsignUnit(lpfdinfo.getClientName());
			info.setContractRate(DataFormat.formatDisabledAmount(lpfdinfo.getInterestRate()));
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			info.setLoanInterval(DataFormat.getChineseDateString(lpfdinfo.getStart()) + " 至 " + DataFormat.getChineseDateString(lpfdinfo.getEnd()));
			info.setLoanType(LOANConstant.LoanType.getName(lpfdinfo.getLoanType()));
			info.setLoanUnit(NameRef.getClientNameByID(pi.getReceiveClientID()));
			info.setNote(pi.getAbstract());
			//贷款账号
			info.setLoanAccountNo(NameRef.getAccountNoByID(pi.getLoanAccountID()));
			//借款单位，即自营贷款账户
			info.setLoanUnit(NameRef.getAccountNameByID(pi.getLoanAccountID()));
			if (pi.getReceiveAccountID() > 0)
			{
				//收款人账号
				info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
				//收款人开户银行名称
				info.setOpenBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			else if (pi.getReceiveBankID() > 0)
			{
				//收款人账号
				info.setAccountNo(pi.getExtAccountNo());
				//收款人开户银行名称
				info.setOpenBankName(pi.getExtRemitInBank());
			}
			info.setTransNo(pi.getTransNo());
			//设置发放贷款明细，银行名称，承贷比例，承贷金额
			double dSumRate = 0.0;
			double dSumAmount = 0.0;
			Vector vctDetail = pi.getDetail();
			Vector vctTemp = new Vector();
			Vector vctWithOutHead = new Vector();
			BankLoanDrawInfo bankLoanDrawInfo = null;
			SynLoanGrantDetailInfo synLoanGrantDetailInfo = null;
			if (vctDetail != null)
			{
				System.out.println("===发放贷款明细 not null===");
				for (int i = 0; i < vctDetail.size(); i++)
				{
					synLoanGrantDetailInfo = new SynLoanGrantDetailInfo();
					bankLoanDrawInfo = (BankLoanDrawInfo) vctDetail.elementAt(i);
					synLoanGrantDetailInfo.setLoanUnitName(DataFormat.formatString(bankLoanDrawInfo.getBankName()));
					synLoanGrantDetailInfo.setLoanRate(DataFormat.formatRate(String.valueOf(DataFormat.formatDouble(bankLoanDrawInfo.getRate()))));
					synLoanGrantDetailInfo.setLoanAmount(DataFormat.formatDisabledAmount(DataFormat.formatDouble(bankLoanDrawInfo.getDrawAmount())));
					dSumRate = DataFormat.formatDouble(dSumRate + DataFormat.formatDouble(bankLoanDrawInfo.getRate()));
					dSumAmount = DataFormat.formatDouble(dSumAmount + DataFormat.formatDouble(bankLoanDrawInfo.getDrawAmount()));
					if (bankLoanDrawInfo.getIsHead() != 1)
					{
						vctWithOutHead.add(synLoanGrantDetailInfo);
						vctTemp.add(synLoanGrantDetailInfo);
					}
					else
					{
						vctTemp.add(synLoanGrantDetailInfo);
					}
				}
				info.setSynLoanGrantDetail(vctTemp);
			}
			//合计承贷比例
			info.setSumAssumeLoanRate(DataFormat.formatRate(String.valueOf(dSumRate)));
			//合计承贷金额
			info.setSunAssumeLoanAmount(DataFormat.formatDisabledAmount(DataFormat.formatDouble(dSumAmount)));
			//ISynLoanPrintTemplate.showSynLoanGrantLoan1(info, out);
			//out.println("<br clear=all style='page-break-before:always'>");
			//ISynLoanPrintTemplate.showSynLoanGrantLoan2(info, out);
			//out.println("<br clear=all style='page-break-before:always'>");
			ISynLoanPrintTemplate.showSynLoanGrantLoan3(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			//ISynLoanPrintTemplate.showSynLoanGrantLoan4(info, out);
			//out.println("<br clear=all style='page-break-before:always'>");
			//参与行留存凭证
			info.setSynLoanGrantDetail(vctWithOutHead); //特殊处理
			vctTemp = info.getSynLoanGrantDetail();
			synLoanGrantDetailInfo = null;
			Vector vctTemp2 = null;
			if (vctTemp != null)
			{
				for (int i = 0; i < vctTemp.size(); i++)
				{
					vctTemp2 = new Vector();
					synLoanGrantDetailInfo = (SynLoanGrantDetailInfo) vctTemp.elementAt(i);
					//合计承贷比例
					info.setSumAssumeLoanRate(synLoanGrantDetailInfo.getLoanRate());
					//合计承贷金额
					info.setSunAssumeLoanAmount(synLoanGrantDetailInfo.getLoanAmount());
					vctTemp2.add(synLoanGrantDetailInfo);
					info.setSynLoanGrantDetail(vctTemp2);
					info.setNum(getChineseNumByNum(i + 5) + "");
					//ISynLoanPrintTemplate.showSynLoanGrantLoan5(info, out);
					if (i != (vctTemp.size() - 1))
					{
						//out.println("<br clear=all style='page-break-before:always'>");
					}
				}
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
		 * Method PrintSynLoanRepayment.
		 * 银团贷款本金收回凭证
		 * @param pi
		 * @param out
		 * @throws Exception
		 */
	public static void PrintSynLoanRepayment(PrintInfo pi, JspWriter out) throws Exception
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
					+ PrintVoucher.getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ strRealInterest
					+ "；已收复利："
					+ PrintVoucher.getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ strRealCompoundInterest
					+ "；已收罚息："
					+ PrintVoucher.getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ strRealOverDueInterest);
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
				info.setBalance(PrintVoucher.getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getCurrentBalance()));
			}
			else
			{
				info.setBalance(PrintVoucher.getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + "0.00");
			}
			//累计还款
			if ((lpfdinfo.getAmount() - pi.getCurrentBalance()) > 0)
			{
				info.setTotalRepayAmount(PrintVoucher.getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(lpfdinfo.getAmount() - pi.getCurrentBalance()));
			}
			else
			{
				info.setTotalRepayAmount(PrintVoucher.getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + "0.00");
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
				info.setRepaymentAccountNo(DataFormat.formatString(IPrintTemplate.getBankAccountCodeByID(pi.getPayBankID())));
			}
			else
			{
				info.setRepaymentBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
				info.setRepaymentAccountNo(DataFormat.formatString(NameRef.getAccountNoByID(pi.getPayAccountID())));
			}
			info.setTransNo(pi.getTransNo());
			double dSumLoanRate = 0.0;
			double dSumReciveAmount = 0.0;
			BankLoanQuery bankLoanQuery = new BankLoanQuery();
			Vector vctDetail = new Vector();
			Vector vctWithOutHead = new Vector();
			Collection collDetail = null;
			BankLoanDrawInfo bankLoanDrawInfo = null;
			SynLoanRepayDetailInfo repayDetailInfo = null;
			collDetail = bankLoanQuery.findByFormID(pi.getLoanNoteID());
			System.out.println("放款通知单ID:" + pi.getLoanNoteID());
			//设置本金
			info.setAmount(PrintVoucher.getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getAmount()));
			if (collDetail != null)
			{
				Iterator it = null;
				it = collDetail.iterator();
				while (it.hasNext())
				{
					bankLoanDrawInfo = (BankLoanDrawInfo) it.next();
					repayDetailInfo = new SynLoanRepayDetailInfo();
					if (bankLoanDrawInfo != null)
					{
						//收款单位名称
						repayDetailInfo.setReceiveAmountUnitName(DataFormat.formatString(bankLoanDrawInfo.getBankName()));
						//承贷比例
						repayDetailInfo.setLoanRate(DataFormat.formatString(DataFormat.formatAmount(bankLoanDrawInfo.getRate(), 0) + ""));
						dSumLoanRate = DataFormat.formatDouble(dSumLoanRate + DataFormat.formatDouble(bankLoanDrawInfo.getRate()));
						YTLoanAttendBankInfo ytLoanAttendBankInfo = null;
						YTLoanAttendBankDelegation delegation = new YTLoanAttendBankDelegation();
						ytLoanAttendBankInfo = delegation.findById(bankLoanDrawInfo.getBankID());
						if (ytLoanAttendBankInfo != null)
						{
							//开户银行
							repayDetailInfo.setOpenBank(DataFormat.formatString(ytLoanAttendBankInfo.getBank()));
							//收款金额
							repayDetailInfo.setBankAccountNo(DataFormat.formatString(ytLoanAttendBankInfo.getBankAccountNo()));
						}
						else
						{
							//开户银行
							repayDetailInfo.setOpenBank("&nbsp;");
							//银行账号
							repayDetailInfo.setBankAccountNo("&nbsp;");
						}
						//收款金额
						repayDetailInfo.setReciveAmount(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getAmount() * bankLoanDrawInfo.getRate() / 100)));
					}
					if (bankLoanDrawInfo.getIsHead() != 1)
					{
						vctWithOutHead.add(repayDetailInfo);
						vctDetail.add(repayDetailInfo);
					}
					else
					{
						vctDetail.add(repayDetailInfo);
					}
				}
			}
			info.setSumLoanRate(String.valueOf(dSumLoanRate));
			info.setSumReciveAmount(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getAmount())) + "");
			info.setSynLoanRepayDetail(vctDetail);
			//ISynLoanPrintTemplate.showSynLoanRepaymentLoan1(info, out);
			//out.println("<br clear=all style='page-break-before:always'>");
			//ISynLoanPrintTemplate.showSynLoanRepaymentLoan2(info, out);
			//out.println("<br clear=all style='page-break-before:always'>");
			ISynLoanPrintTemplate.showSynLoanRepaymentLoan3(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			//参与行留存凭证
			info.setSynLoanRepayDetail(vctWithOutHead); //特殊处理
			vctDetail = info.getSynLoanRepayDetail();
			repayDetailInfo = null;
			Vector vctTemp = null;
			if (vctDetail != null)
			{
				for (int i = 0; i < vctDetail.size(); i++)
				{
					vctTemp = new Vector();
					repayDetailInfo = (SynLoanRepayDetailInfo) vctDetail.elementAt(i);
					//合计承贷比例
					info.setSumLoanRate(DataFormat.formatRate(repayDetailInfo.getLoanRate()));
					//合计承贷金额
					info.setSumReciveAmount(repayDetailInfo.getReciveAmount());
					vctTemp.add(repayDetailInfo);
					info.setSynLoanRepayDetail(vctTemp);
					info.setNum(getChineseNumByNum(i + 4) + "");
					//ISynLoanPrintTemplate.showSynLoanRepaymentLoan4(info, out);
					if (i != (vctDetail.size() - 1))
					{
						//out.println("<br clear=all style='page-break-before:always'>");
					}
				}
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
		 * Method PrintSynLoanInterest.
		 * 银团贷款利息收回凭证
		 * @param pi
		 * @param out
		 * @throws Exception
		 */
	public static void PrintSynLoanInterest(PrintInfo pi, JspWriter out) throws Exception
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
			//借款单位名称
			if (pi.getPayClientID() > 0)
			{
				info.setClientName(NameRef.getClientNameByID(pi.getPayClientID()));
			}
			else
			{
				info.setClientName(pi.getBorrowClientName());
			}
			//委托单位名称
			info.setConsignClientName(NameRef.getClientNameByID(pi.getConsignClientID()));
			//账号类型
			info.setAccountType(SETTConstant.AccountType.getName(pi.getAccountTypeID()));
			//交易编号
			info.setTransNo(pi.getTransNo());
			//账号&账户名称
			long lPayAccountID = -1;
			if (pi.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE
				|| pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE
				|| pi.getTransTypeID() == SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
			{
				if (pi.getReceiveAccountID() > 0)
				{
					lPayAccountID = pi.getReceiveAccountID();
					info.setAccountName(NameRef.getAccountNameByID(pi.getReceiveAccountID()));
					info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
				}
				else if (pi.getPayAccountID() > 0)
				{
					lPayAccountID = pi.getPayAccountID();
					info.setAccountName(NameRef.getAccountNameByID(pi.getPayAccountID()));
					info.setAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
				}
			}
			else
			{
				lPayAccountID = pi.getPayAccountID();
				info.setAccountName(NameRef.getAccountNameByID(pi.getPayAccountID()));
				info.setAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
			}
			System.out.println("实际支付利息：" + pi.getRealInterest());
			System.out.println("付款账号：" + lPayAccountID);
			System.out.println("放款通知单号：" + pi.getLoanNoteID());
			System.out.println("算息起止日期:" + pi.getNormalInterestStart() + "---" + pi.getNormalInterestEnd());
			if (pi.getRealInterest() > 0)
			{
				/*//加入分段利息
				//正常利息
				//加入分段利息
				SubsectionInterest dao = new SubsectionInterest();
				SubsectionDateInfo PrintSubsectionInfo = new SubsectionDateInfo();
				PrintSubsectionInfo =
					dao.getSubsectionInterest(lPayAccountID, -1, pi.getLoanNoteID(), pi.getLatestInterestClearDate(), DataFormat.getPreviousDate(pi.getInterestClearDate()));
				info.setNormalInterestDateStart(PrintSubsectionInfo.getPrintStartDate()); //正常利息开始日期
				info.setNormalInterestDateEnd(PrintSubsectionInfo.getPrintEndDate());
				info.setNormalInterestAmount(PrintSubsectionInfo.getPrintBalance()); //正常利息本金
				info.setNormalInterestRate(PrintSubsectionInfo.getPrintInterestRate()); //正常利息率
				info.setNormalInterest(PrintSubsectionInfo.getPrintInterest()); //正常利息
				info.setNormalInterestDay(PrintSubsectionInfo.getPrintDays());
				*/
				info.setNormalInterestDateStart(DataFormat.getDateString(pi.getLatestInterestClearDate())); //正常利息开始日期
				info.setNormalInterestDateEnd(DataFormat.getDateString(DataFormat.getPreviousDate(pi.getInterestClearDate())));
				info.setNormalInterestAmount(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getAmount()))); //正常利息本金
				info.setNormalInterestRate(DataFormat.formatRate(String.valueOf(DataFormat.formatRate(pi.getCompoundRate())))); //正常利息率
				info.setNormalInterest(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getRealInterest()))); //正常利息
				info.setNormalInterestDay((PrintVoucher.getIntervalDays(pi.getLatestInterestClearDate(), pi.getInterestClearDate(), 1)) + "");
				//加入分段利息End
			}
			//复利
			if (pi.getRealCompoundInterest() > 0)
			{
				//复利利息起息日期
				if (pi.getCompoundInterestStart() != null)
				{
					info.setCompoundInterestDateStart(DataFormat.formatDate(pi.getCompoundInterestStart()));
				}
				//复利利息终息日期
				if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE || pi.getTransTypeID() == SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
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
					info.setCompoundInterestDay((PrintVoucher.getIntervalDays(pi.getCompoundInterestStart(), pi.getInterestStartDate(), 1)) + "");
				}
				info.setCompoundInterestAmount("&nbsp;");
				//复利利率
				info.setCompoundInterestRate(DataFormat.formatRate(pi.getCompoundRate() + ""));
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
					info.setOverInterestDay((PrintVoucher.getIntervalDays(pi.getOverDueStart(), pi.getInterestClearDate(), 1)) + "");
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
				info.setCommissionFeeDay((PrintVoucher.getIntervalDays(pi.getCommissionStart(), pi.getInterestClearDate(), 1)) + "");
				//手续费利息本金		
				info.setCommissionFeeAmount(DataFormat.formatDisabledAmount(pi.getAmount()));
				if (pi.getAmount() == 0.00)
				{
					info.setCommissionFeeAmount("0.00");
				}
				//手续费利率				
				System.out.print("========手续费利率：" + pi.getCommissionRate());
				info.setCommissionFeeRate(DataFormat.formatRate(pi.getCommissionRate() + ""));
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
				info.setAssureFeeDay((PrintVoucher.getIntervalDays(pi.getSuretyFeeStart(), pi.getInterestClearDate(), 1)) + "");
				//担保费利息本金		
				info.setAssureFeeAmount(DataFormat.formatDisabledAmount(pi.getAmount()));
				if (pi.getAmount() == 0.0)
				{
					info.setAssureFee("0.00");
				}
				//担保费利率
				info.setAssureFeeRate(DataFormat.formatRate(pi.getSuretyFeeRate() + ""));
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
						DataFormat.formatDouble(pi.getRealInterest()) + DataFormat.formatDouble(pi.getRealCompoundInterest()) + DataFormat.formatDouble(pi.getRealOverDueInterest())),
					pi.getCurrencyID()));
			if (DataFormat.formatDouble(pi.getRealInterest()) + DataFormat.formatDouble(pi.getRealCompoundInterest()) + DataFormat.formatDouble(pi.getRealOverDueInterest()) == 0.0)
			{
				info.setTotalInterest("0.00");
			}
			//利息账号
			info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getPayInterestAccountID()));
			//对应的账号
			info.setCurrentAccountNo(NameRef.getAccountNoByID(lPayAccountID));
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
					info.setCheckUserName("机制");
				}
				else
				{
					info.setCheckUserName("&nbsp;&nbsp;&nbsp;&nbsp");
				}
			}
			//设置贷款利息明细，收息单位名称，开户银行，银行账号，应收利息（元）
			Vector vctDetail = pi.getDetail();
			Vector vctTemp = new Vector();
			Vector vctWithOutHead = new Vector();
			SyndicationLoanInterestInfo interestInfo = null;
			SynLoanRepayDetailInfo synLoanRepayDetailInfo = null;
			double dSumInterest = 0.0;
			double dTotalSumInterest = 0.0;
			if (vctDetail != null)
			{
				System.out.println("===贷款利息明细 not null===");
				for (int i = 0; i < vctDetail.size(); i++)
				{
					synLoanRepayDetailInfo = new SynLoanRepayDetailInfo();
					interestInfo = (SyndicationLoanInterestInfo) vctDetail.elementAt(i);
					//收息单位名称
					synLoanRepayDetailInfo.setReveiveInterestUnitName(interestInfo.getBankName());
					YTLoanAttendBankInfo ytLoanAttendBankInfo = null;
					YTLoanAttendBankDelegation delegation = new YTLoanAttendBankDelegation();
					ytLoanAttendBankInfo = delegation.findById(interestInfo.getBankID());
					if (ytLoanAttendBankInfo != null)
					{
						synLoanRepayDetailInfo.setOpenBank(DataFormat.formatString(ytLoanAttendBankInfo.getBank()));
						synLoanRepayDetailInfo.setBankAccountNo(DataFormat.formatString(ytLoanAttendBankInfo.getBankAccountNo()));
					}
					else
					{
						//开户银行
						synLoanRepayDetailInfo.setOpenBank("&nbsp;");
						//银行账号
						synLoanRepayDetailInfo.setBankAccountNo("&nbsp;");
					}
					//应收利息（元）
					dSumInterest =
						DataFormat.formatDouble(
							DataFormat.formatDouble(interestInfo.getInterest())
								+ DataFormat.formatDouble(interestInfo.getCompoundInterest())
								+ DataFormat.formatDouble(interestInfo.getForpeitInterest()));
					//合计利息
					synLoanRepayDetailInfo.setReceiveInterest(DataFormat.formatDisabledAmount(dSumInterest) + "");
					dTotalSumInterest = DataFormat.formatDouble(dTotalSumInterest + DataFormat.formatDouble(dSumInterest));
					if (interestInfo.getIsHead() != 1)
					{
						vctTemp.add(synLoanRepayDetailInfo);
						vctWithOutHead.add(synLoanRepayDetailInfo);
					}
					else
					{
						vctTemp.add(synLoanRepayDetailInfo);
					}
				}
				info.setSynLoanRepayDetail(vctTemp);
				info.setSumInterest(DataFormat.formatDisabledAmount(dTotalSumInterest) + "");
			}
			//ISynLoanPrintTemplate.showSynLoanPayInterestNotice1(info, out);
			//out.println("<br clear=all style='page-break-before:always'>");
			//ISynLoanPrintTemplate.showSynLoanPayInterestNotice2(info, out);
			//out.println("<br clear=all style='page-break-before:always'>");
			ISynLoanPrintTemplate.showSynLoanPayInterestNotice3(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			//参与行留存凭证
			info.setSynLoanRepayDetail(vctWithOutHead); //特殊处理
			vctDetail = info.getSynLoanRepayDetail();
			synLoanRepayDetailInfo = null;
			vctTemp = null;
			if (vctDetail != null)
			{
				for (int i = 0; i < vctDetail.size(); i++)
				{
					vctTemp = new Vector();
					synLoanRepayDetailInfo = (SynLoanRepayDetailInfo) vctDetail.elementAt(i);
					vctTemp.add(synLoanRepayDetailInfo);
					info.setSynLoanRepayDetail(vctTemp);
					info.setNum(getChineseNumByNum(i + 4) + "");
					info.setSumInterest(synLoanRepayDetailInfo.getReceiveInterest());
					//ISynLoanPrintTemplate.showSynLoanPayInterestNotice4(info, out);
					if (i != (vctDetail.size() - 1))
					{
						//out.println("<br clear=all style='page-break-before:always'>");
					}
				}
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

	public static String getChineseNumByNum(int nNum)
	{
		String strReturn = "";
		switch (nNum)
		{
			case 1 :
				strReturn = "一";
				break;
			case 2 :
				strReturn = "二";
				break;
			case 3 :
				strReturn = "三";
				break;
			case 4 :
				strReturn = "四";
				break;
			case 5 :
				strReturn = "五";
				break;
			case 6 :
				strReturn = "六";
				break;
			case 7 :
				strReturn = "七";
				break;
			case 8 :
				strReturn = "八";
				break;
			case 9 :
				strReturn = "九";
				break;
			case 10 :
				strReturn = "十";
				break;
			case 11 :
				strReturn = "十一";
				break;
			case 12 :
				strReturn = "十二";
				break;
			case 13 :
				strReturn = "十三";
				break;
			case 14 :
				strReturn = "十四";
				break;
			default :
				strReturn = "&nbsp;&nbsp;";
		}
		return strReturn;
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
}