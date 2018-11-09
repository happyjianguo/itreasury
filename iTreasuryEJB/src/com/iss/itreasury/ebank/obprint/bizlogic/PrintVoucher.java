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
	     * �ɱ���IDȡ�ñ��ֱ�־
	     * @param lCurrencyID
	     * @return
	     */
	public static String getCurrencySymbolByCurrencyID(long lCurrencyID)
	{
		String strReturn = "";
		if (lCurrencyID == 1)
		{
			strReturn = "��";
		}
		if (lCurrencyID == 2)
		{
			strReturn = "��";
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
	 * ��ʼ����ʵ�ʼ������
	 * @param sG ��Ϣ�� GregorianCalendar
	 * @param eG ��Ϣ�� GregorianCalendar
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
	 *	���˵���ӡ �������˵�1�ͽ��˵�2
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
			//����˻�
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
			else //����
				{
				strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
				info.setPayAccountName(strPayAccountName);
				strPayAccountNo = NameRef.getGLTypeNoByID(pi.getPayGL());
				info.setPayAccountNo(strPayAccountNo);
			}
			//�տ�˻�
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
			else //����
				{
				strReceiveAccountName = NameRef.getGLTypeDescByID(pi.getReceiveGL());
				info.setReceiveAccountName(strReceiveAccountName);
				strReceiveAccountNo = NameRef.getGLTypeNoByID(pi.getReceiveGL());
				info.setReceiveAccountNo(strReceiveAccountNo);
			}
			//ȡ�ý��
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
			//ȡ���û�����
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			String strAbstract = "��Ϣ�գ�" + strInterestStart + "��" + pi.getAbstract();
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
	 *	���֧ȡƾ֤��ӡ �������֧ȡƾ֤1�ʹ��֧ȡƾ֤2
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
			//��Ӫ�����ջأ�ί�д����ջش��֧ȡƾ֤�н��͸���˺�
			//���������Ϣ�������ѣ������ѣ��ӽ��ͻ��Ļ����˻��п۳��������ۼ�����֧ȡƾ֤���
			//�������������˺Ŵ��ڣ��򸶿Ϊ����������˻��������������Ϣ�����˺Ŵ��ڣ��򸶿Ϊ��Ϣ�����˻��������ѡ���������������
			if (pi.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE || pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
			{
				System.out.println("=====��Ӫ/ί��");
				//������˺�
				if (pi.getPayAccountID() > 0)
				{
					System.out.println("=====11111111111111111");
					strPayAccountName = NameRef.getAccountNameByID(pi.getPayAccountID());
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
					strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
					//���Ӵ浥��
					if (pi.getFixedDepositNo() != null && pi.getFixedDepositNo().length() > 0)
					{
						strPayAccountNo = strPayAccountNo + "��" + pi.getFixedDepositNo();
					}
					//���ӷſ�֪ͨ����
					if (pi.getLoanNoteID() > 0)
					{
						strPayAccountNo = strPayAccountNo + "��" + NameRef.getPayFormNoByID(pi.getLoanNoteID());
					}
					//���Ӻ�ͬ��
					if (pi.getContractID() != -1)
					{
						strPayAccountNo = strPayAccountNo + "��" + NameRef.getContractNoByID(pi.getContractID());
					}
					info.setPayAccountNo(DataFormat.formatString(strPayAccountNo));
					info.setPayBankName(DataFormat.formatString(NameRef.getOfficeNameByID(pi.getOfficeID())));
					//�����ɻ����˻�֧��
					dAmount = DataFormat.formatDouble(pi.getAmount());
					System.out.println("=====dAmount1��" + dAmount);
					//���ڸ�Ϣ�����˺�
					if (pi.getPayInterestAccountID() > 0)
					{
						dAmount =
							DataFormat.formatDouble(
								dAmount + DataFormat.formatDouble(pi.getRealInterest()) + DataFormat.formatDouble(pi.getRealCompoundInterest()) + DataFormat.formatDouble(pi.getRealOverDueInterest()));
					}
					if (pi.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE)
					{
						//��Ӫ�����ջأ����ڸ������ѻ����˺�
						if (pi.getPaySuretyFeeAccountID() > 0)
						{
							dAmount = DataFormat.formatDouble(dAmount + DataFormat.formatDouble(pi.getRealSuretyFee()));
							System.out.println("=====dAmount2��" + dAmount);
						}
					}
					else if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
					{
						//ί�д����ջأ����ڸ������ѻ����˺�
						if (pi.getPayCommissionAccountID() > 0)
						{
							dAmount = DataFormat.formatDouble(dAmount + DataFormat.formatDouble(pi.getRealCommission()));
							System.out.println("=====dAmount3��" + dAmount);
						}
					}
				}
				else if (pi.getPayInterestAccountID() > 0)
				{
					System.out.println("=====222222222222222");
					strPayAccountName = NameRef.getAccountNameByID(pi.getPayInterestAccountID());
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
					strPayAccountNo = NameRef.getAccountNoByID(pi.getPayInterestAccountID());
					//���Ӵ浥��
					if (pi.getFixedDepositNo() != null && pi.getFixedDepositNo().length() > 0)
					{
						strPayAccountNo = strPayAccountNo + "��" + pi.getFixedDepositNo();
					}
					//���ӷſ�֪ͨ����
					if (pi.getLoanNoteID() > 0)
					{
						strPayAccountNo = strPayAccountNo + "��" + NameRef.getPayFormNoByID(pi.getLoanNoteID());
					}
					//���Ӻ�ͬ��
					if (pi.getContractID() != -1)
					{
						strPayAccountNo = strPayAccountNo + "��" + NameRef.getContractNoByID(pi.getContractID());
					}
					info.setPayAccountNo(DataFormat.formatString(strPayAccountNo));
					info.setPayBankName(DataFormat.formatString(NameRef.getOfficeNameByID(pi.getOfficeID())));
					dAmount =
						DataFormat.formatDouble(
							DataFormat.formatDouble(pi.getRealInterest()) + DataFormat.formatDouble(pi.getRealCompoundInterest()) + DataFormat.formatDouble(pi.getRealOverDueInterest()));
					System.out.println("=====dAmount4��" + dAmount);
					if (pi.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE)
					{
						//��Ӫ�����ջأ����ڸ������ѻ����˺�
						if (pi.getPaySuretyFeeAccountID() > 0)
						{
							dAmount = DataFormat.formatDouble(dAmount + DataFormat.formatDouble(pi.getRealSuretyFee()));
							System.out.println("=====dAmount5��" + dAmount);
						}
					}
					else if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
					{
						//ί�д����ջأ����ڸ������ѻ����˺�
						if (pi.getPayCommissionAccountID() > 0)
						{
							dAmount = DataFormat.formatDouble(dAmount + DataFormat.formatDouble(pi.getRealCommission()));
							System.out.println("=====dAmount6��" + dAmount);
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
						//���Ӵ浥��
						if (pi.getFixedDepositNo() != null && pi.getFixedDepositNo().length() > 0)
						{
							strPayAccountNo = strPayAccountNo + "��" + pi.getFixedDepositNo();
						}
						//���ӷſ�֪ͨ����
						if (pi.getLoanNoteID() > 0)
						{
							strPayAccountNo = strPayAccountNo + "��" + NameRef.getPayFormNoByID(pi.getLoanNoteID());
						}
						//���Ӻ�ͬ��
						if (pi.getContractID() != -1)
						{
							strPayAccountNo = strPayAccountNo + "��" + NameRef.getContractNoByID(pi.getContractID());
						}
						info.setPayAccountNo(DataFormat.formatString(strPayAccountNo));
						info.setPayBankName(DataFormat.formatString(NameRef.getOfficeNameByID(pi.getOfficeID())));
						dAmount = DataFormat.formatDouble(pi.getRealSuretyFee());
						System.out.println("=====dAmount7��" + dAmount);
					}
					else if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
					{
						strPayAccountName = NameRef.getAccountNameByID(pi.getPayCommissionAccountID());
						info.setPayAccountName(DataFormat.formatString(strPayAccountName));
						strPayAccountNo = NameRef.getAccountNoByID(pi.getPayCommissionAccountID());
						//���Ӵ浥��
						if (pi.getFixedDepositNo() != null && pi.getFixedDepositNo().length() > 0)
						{
							strPayAccountNo = strPayAccountNo + "��" + pi.getFixedDepositNo();
						}
						//���ӷſ�֪ͨ����
						if (pi.getLoanNoteID() > 0)
						{
							strPayAccountNo = strPayAccountNo + "��" + NameRef.getPayFormNoByID(pi.getLoanNoteID());
						}
						//���Ӻ�ͬ��
						if (pi.getContractID() != -1)
						{
							strPayAccountNo = strPayAccountNo + "��" + NameRef.getContractNoByID(pi.getContractID());
						}
						info.setPayAccountNo(DataFormat.formatString(strPayAccountNo));
						info.setPayBankName(DataFormat.formatString(NameRef.getOfficeNameByID(pi.getOfficeID())));
						dAmount = DataFormat.formatDouble(pi.getRealCommission());
						System.out.println("=====dAmount8��" + dAmount);
					}
				}
				strAmount = DataFormat.formatAmount(dAmount);
				System.out.println("=====dAmount9��" + dAmount);
				info.setAmount(strAmount);
				String strChineseAmount = ChineseCurrency.showChinese(strAmount, pi.getCurrencyID());
				info.setChineseAmount(strChineseAmount);
				strAmount = getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(dAmount);
				info.setAmount(strAmount);
				//�տ�˻�
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
					//�տ��˻���ص�
					if (pi.getExtRemitInProvince().indexOf("ʡ") < 0)
					{
						strTemp = pi.getExtRemitInProvince() + "ʡ";
					}
					else
					{
						strTemp = pi.getExtRemitInProvince();
					}
					if (pi.getExtRemitInCity().indexOf("��") < 0)
					{
						strTemp += pi.getExtRemitInCity() + "��";
					}
					else
					{
						strTemp += pi.getExtRemitInCity();
					}
					info.setReceiveRemitInAddress(DataFormat.formatString(strTemp));
				}
				else //����
					{
					strReceiveAccountName = NameRef.getGLTypeDescByID(pi.getReceiveGL());
					info.setReceiveAccountName(DataFormat.formatString(strReceiveAccountName));
					strReceiveAccountNo = DataFormat.formatString(NameRef.getGLTypeNoByID(pi.getReceiveGL()));
					info.setReceiveAccountNo(DataFormat.formatString(strReceiveAccountNo));
				}
			}
			else //����ҵ��
				{
				System.out.println("=====����Ӫ/ί��");
				//����˻�
				if (pi.getPayAccountID() > 0)
				{
					strPayAccountName = NameRef.getAccountNameByID(pi.getPayAccountID());
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
					strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
					//���Ӵ浥��
					if (pi.getFixedDepositNo() != null && pi.getFixedDepositNo().length() > 0)
					{
						strPayAccountNo = strPayAccountNo + "��" + pi.getFixedDepositNo();
					}
					//���ӷſ�֪ͨ����
					if (pi.getLoanNoteID() > 0)
					{
						strPayAccountNo = strPayAccountNo + "��" + NameRef.getPayFormNoByID(pi.getLoanNoteID());
					}
					//���Ӻ�ͬ��
					if (pi.getContractID() != -1)
					{
						strPayAccountNo = strPayAccountNo + "��" + NameRef.getContractNoByID(pi.getContractID());
					}
					info.setPayAccountNo(DataFormat.formatString(strPayAccountNo));
					info.setPayBankName(DataFormat.formatString(NameRef.getOfficeNameByID(pi.getOfficeID())));
				}
				else if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
				{
					strPayAccountName = pi.getExtClientName();
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
					strPayAccountNo = pi.getExtAccountNo();
					//���Ӵ浥��
					if (pi.getFixedDepositNo() != null && pi.getFixedDepositNo().length() > 0)
					{
						strPayAccountNo = strPayAccountNo + "��" + pi.getFixedDepositNo();
					}
					//���ӷſ�֪ͨ����
					if (pi.getLoanNoteID() > 0)
					{
						strPayAccountNo = strPayAccountNo + "��" + NameRef.getPayFormNoByID(pi.getLoanNoteID());
					}
					//���Ӻ�ͬ��
					if (pi.getContractID() != -1)
					{
						strPayAccountNo = strPayAccountNo + "��" + NameRef.getContractNoByID(pi.getContractID());
					}
					info.setPayAccountNo(DataFormat.formatString(strPayAccountNo));
					info.setPayBankName(DataFormat.formatString(pi.getExtRemitInBank()));
				}
				else //����
					{
					strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
					strPayAccountNo = NameRef.getGLTypeNoByID(pi.getPayGL());
					//���Ӵ浥��
					if (pi.getFixedDepositNo() != null && pi.getFixedDepositNo().length() > 0)
					{
						strPayAccountNo = strPayAccountNo + "��" + pi.getFixedDepositNo();
					}
					//���ӷſ�֪ͨ����
					if (pi.getLoanNoteID() > 0)
					{
						strPayAccountNo = strPayAccountNo + "��" + NameRef.getPayFormNoByID(pi.getLoanNoteID());
					}
					//���Ӻ�ͬ��
					if (pi.getContractID() != -1)
					{
						strPayAccountNo = strPayAccountNo + "��" + NameRef.getContractNoByID(pi.getContractID());
					}
					info.setPayAccountNo(DataFormat.formatString(strPayAccountNo));
				}
				//�տ�˻�
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
					//�տ��˻���ص�
					if (pi.getExtRemitInProvince().indexOf("ʡ") < 0)
					{
						strTemp = pi.getExtRemitInProvince() + "ʡ";
					}
					else
					{
						strTemp = pi.getExtRemitInProvince();
					}
					if (pi.getExtRemitInCity().indexOf("��") < 0)
					{
						strTemp += pi.getExtRemitInCity() + "��";
					}
					else
					{
						strTemp += pi.getExtRemitInCity();
					}
					info.setReceiveRemitInAddress(DataFormat.formatString(strTemp));
				}
				else //����
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
	 *	��ʾ����ת�˴�����Ʊ
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
			//����˻�
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
			else //����
				{
				strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
				info.setPayAccountName(strPayAccountName);
				strPayAccountNo = NameRef.getGLTypeNoByID(pi.getPayGL());
				info.setPayAccountNo(strPayAccountNo);
			}
			if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER || pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
			{
				//����Ƕ���֧ȡ����֪ͨ���֧ȡ�������տ���ջ����˻��տ��������
				//�տ
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
			else //����ҵ��
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
				else //����
					{
					strReceiveAccountName = NameRef.getGLTypeDescByID(pi.getReceiveGL());
					info.setReceiveAccountName(strReceiveAccountName);
					strReceiveAccountNo = NameRef.getGLTypeNoByID(pi.getReceiveGL());
					info.setReceiveAccountNo(strReceiveAccountNo);
				}
				if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
				{
					//�����ί�д����ջ�,��ת�����Ϊ������Ϣ-��Ϣ˰��(2004/3/31)
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
					//����Ƕ�������,��ת�����Ϊ����+������Ϣ+��Ϣ֧��(2004/3/31)
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
				strCheckUser = "����";
			}
			info.setCheckUserName(strCheckUser);
			String strAbstract = "��Ϣ�գ�" + DataFormat.formatDate(pi.getInterestStartDate()) + "��" + pi.getAbstract();
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
	 *	��ʾ����ת�˽跽��Ʊ
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
			//����˻�
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
			else //����
				{
				strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
				info.setPayAccountName(strPayAccountName);
				strPayAccountNo = NameRef.getGLTypeNoByID(pi.getPayGL());
				info.setPayAccountNo(strPayAccountNo);
			}
			//�տ�˻�
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
			else //����
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
				System.out.print("���-1:" + dAmount);
				System.out.print("���1:" + strAmount);
			}
			else
			{
				dAmount = pi.getAmount();
				strAmount = getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getAmount());
				System.out.print("���-2:" + dAmount);
				System.out.print("���2:" + strAmount);
			}
			if (dAmount == 0.0)
			{
				info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + "0.0");
			}
			else
			{
				info.setAmount(strAmount);
				System.out.print("���-3:" + dAmount);
				System.out.print("���3:" + strAmount);
			}
			strChineseAmount = ChineseCurrency.showChinese(DataFormat.formatAmount(dAmount), pi.getCurrencyID());
			System.out.print("���-4:" + dAmount);
			System.out.print("���4:" + strAmount);
			info.setChineseAmount(strChineseAmount);
			String strInputUser = NameRef.getUserNameByID(pi.getInputUserID());
			info.setInputUserName(strInputUser);
			String strCheckUser = NameRef.getUserNameByID(pi.getCheckUserID());
			info.setCheckUserName(strCheckUser);
			String strAbstract = "��Ϣ�գ�" + DataFormat.formatDate(pi.getInterestStartDate()) + "��" + pi.getAbstract();
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
	 *	��ʾ�״� ���ó���
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
				case 1 : //���н��˵�
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
				case 2 : //����Ż�
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
				case 3 : //���л�Ʊί����
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
	     *  ��Ӫ�����ջ�ƾ֤��ӡ 
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
					+ "����������Ϣ��"
					+ getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ strRealInterest
					+ "�����ո�����"
					+ getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ strRealCompoundInterest
					+ "�����շ�Ϣ��"
					+ getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ strRealOverDueInterest);
			info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getAmount()));
			info.setBillCode(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			info.setChineseAmount(ChineseCurrency.showChinese(DataFormat.formatAmount(pi.getAmount()), pi.getCurrencyID()));
			info.setContractCode(NameRef.getContractNoByID(pi.getContractID()));
			//��ͬ����
			LoanPayFormDetailInfo lpfdinfo = new LoanPayFormDetailInfo();
			lpfdinfo.setLoadNoteID(pi.getLoanNoteID());
			Sett_TransGrantLoanDAO stdao = new Sett_TransGrantLoanDAO();
			lpfdinfo = stdao.findPayFormDetailByCondition(lpfdinfo);
			info.setContractRate(DataFormat.formatDisabledAmount(lpfdinfo.getInterestRate()));
			//ί�е�λ
			info.setConsignUnit(lpfdinfo.getClientName());
			//���
			if (pi.getCurrentBalance() > 0)
			{
				info.setBalance(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getCurrentBalance()));
			}
			else
			{
				info.setBalance(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + "0.00");
			}
			//�ۼƻ���
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
			info.setLoanInterval(DataFormat.getChineseDateString(lpfdinfo.getStart()) + " �� " + DataFormat.getChineseDateString(lpfdinfo.getEnd()));
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
	     *  ί�д����ջ�ƾ֤��ӡ
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
			//����Ϣ��������������ϢֵΪ�����ʾ��ʽ
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
					+ "����������Ϣ��"
					+ getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ strRealInterest
					+ "�����ո�����"
					+ getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ strRealCompoundInterest
					+ "�����շ�Ϣ��"
					+ getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ strRealOverDueInterest);
			info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getAmount()));
			info.setBillCode(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			info.setChargeRate(DataFormat.formatDisabledAmount(pi.getCommission()));
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			info.setChineseAmount(ChineseCurrency.showChinese(DataFormat.formatAmount(pi.getAmount()), pi.getCurrencyID()));
			info.setContractCode(NameRef.getContractNoByID(pi.getContractID()));
			//��ͬ����
			LoanPayFormDetailInfo lpfdinfo = new LoanPayFormDetailInfo();
			lpfdinfo.setLoadNoteID(pi.getLoanNoteID());
			Sett_TransGrantLoanDAO stdao = new Sett_TransGrantLoanDAO();
			lpfdinfo = stdao.findPayFormDetailByCondition(lpfdinfo);
			info.setContractRate(DataFormat.formatDisabledAmount(lpfdinfo.getInterestRate()));
			//ί�е�λ
			info.setConsignUnit(lpfdinfo.getClientName());
			//���info.setOverDueRate()
			if (pi.getCurrentBalance() > 0)
			{
				info.setBalance(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getCurrentBalance()));
			}
			else
			{
				info.setBalance(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + "0.00");
			}
			//�ۼƻ���
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
			info.setLoanInterval(DataFormat.getChineseDateString(lpfdinfo.getStart()) + " �� " + DataFormat.getChineseDateString(lpfdinfo.getEnd()));
			info.setLoanRate(DataFormat.formatDisabledAmount(pi.getRate()));
			info.setLoanType(LOANConstant.LoanType.getName(lpfdinfo.getLoanType()));
			info.setNote(pi.getAbstract());
			info.setOverDueInterest(DataFormat.formatDisabledAmount(pi.getOverDueInterest()));
			//info.setOverDueRate();
			//�����˺�
			//������������
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
	         *  ��Ӫ�����ƾ֤��ӡ
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
			//��ͬ����
			LoanPayFormDetailInfo lpfdinfo = new LoanPayFormDetailInfo();
			lpfdinfo.setLoadNoteID(pi.getLoanNoteID());
			Sett_TransGrantLoanDAO stdao = new Sett_TransGrantLoanDAO();
			lpfdinfo = stdao.findPayFormDetailByCondition(lpfdinfo);
			//ί�е�λ
			info.setConsignUnit(lpfdinfo.getClientName());
			info.setContractRate(DataFormat.formatDisabledAmount(lpfdinfo.getInterestRate()));
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			info.setLoanInterval(DataFormat.getChineseDateString(lpfdinfo.getStart()) + " �� " + DataFormat.getChineseDateString(lpfdinfo.getEnd()));
			info.setLoanType(LOANConstant.LoanType.getName(lpfdinfo.getLoanType()));
			info.setLoanUnit(NameRef.getClientNameByID(pi.getReceiveClientID()));
			info.setNote(pi.getAbstract());
			//�����˺�
			info.setLoanAccountNo(NameRef.getAccountNoByID(pi.getLoanAccountID()));
			//��λ������Ӫ�����˻�
			info.setLoanUnit(NameRef.getAccountNameByID(pi.getLoanAccountID()));
			if (pi.getReceiveAccountID() > 0)
			{
				//�տ����˺�
				info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
				//�տ��˿�����������
				info.setOpenBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			else if (pi.getReceiveBankID() > 0)
			{
				//�տ����˺�
				info.setAccountNo(pi.getExtAccountNo());
				//�տ��˿�����������
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
	         *  ί�д����ƾ֤��ӡ 
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
			//��ͬ����
			LoanPayFormDetailInfo lpfdinfo = new LoanPayFormDetailInfo();
			lpfdinfo.setLoadNoteID(pi.getLoanNoteID());
			Sett_TransGrantLoanDAO stdao = new Sett_TransGrantLoanDAO();
			lpfdinfo = stdao.findPayFormDetailByCondition(lpfdinfo);
			//ί�е�λ
			info.setConsignUnit(lpfdinfo.getClientName());
			info.setContractRate(DataFormat.formatDisabledAmount(lpfdinfo.getInterestRate()));
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			info.setLoanInterval(DataFormat.getChineseDateString(lpfdinfo.getStart()) + " �� " + DataFormat.getChineseDateString(lpfdinfo.getEnd()));
			info.setLoanType(LOANConstant.LoanType.getName(lpfdinfo.getLoanType()));
			info.setLoanUnit(NameRef.getClientNameByID(pi.getReceiveClientID()));
			info.setNote(pi.getAbstract());
			//�����˺�
			info.setLoanAccountNo(NameRef.getAccountNoByID(pi.getLoanAccountID()));
			//��λ������Ӫ�����˻�
			info.setLoanUnit(NameRef.getAccountNameByID(pi.getLoanAccountID()));
			if (pi.getReceiveAccountID() > 0)
			{
				//�տ����˺�
				info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
				//�տ��˿�����������
				info.setOpenBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			else if (pi.getReceiveBankID() > 0)
			{
				//�տ����˺�
				info.setAccountNo(pi.getExtAccountNo());
				//�տ��˿�����������
				info.setOpenBankName(pi.getExtRemitInBank());
			}
			//��������
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
	         *  ���ַ���ƾ֤��ӡ 
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
			//ƾ֤��findbyid����
			DiscountCredenceInfo dcinfo = new DiscountCredenceInfo();
			Sett_TransGrantDiscountDAO transDiscountDelegation = new Sett_TransGrantDiscountDAO();
			dcinfo = transDiscountDelegation.findDiscountCredenceByID(pi.getDiscountNoteID());
			///System.out.print("dcinfo:Id"+dcinfo.getID());
			info.setAbstract(pi.getAbstract() + "�����ֺ�ͬ�ţ�" + dcinfo.getDiscountContractCode() + "������ƾ֤��ţ�" + dcinfo.getCode());
			info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getDiscountAmount()));
			//��Ʊ����Ϣ
			if (pi.getReceiveAccountID() > 0)
			{
				//��Ʊ������
				info.setBillKeeperName(NameRef.getAccountNameByID(pi.getReceiveAccountID()));
				//��Ʊ���˺�
				info.setBillKeeperAccount(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
				//��Ʊ�˿�����������
				info.setBillKeeperBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			else if (pi.getReceiveBankID() > 0)
			{
				//��Ʊ������
				info.setBillKeeperName(NameRef.getBankNameByID(pi.getReceiveBankID()));
				//��Ʊ���˺�
				info.setBillKeeperAccount(pi.getExtAccountNo());
				//��Ʊ�˿�����������
				info.setBillKeeperBankName(pi.getExtRemitInBank());
			}
			//�ر�˵��:��Ʊ��Ʊ����Ϣ��ʱ����Ҫ
			//��Ʊ��Ʊ���˺�
			info.setBillOutAccount("&nbsp;");
			//(dcinfo.getAcceptAccount());//��Ʊ��Ʊ������
			info.setBillOutBankName("&nbsp;"); //(dcinfo.getAcceptBank());
			//��Ʊ��Ʊ������
			info.setBillOutName("&nbsp;"); //dcinfo.getAcceptClientName());
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			info.setChineseAmount(ChineseCurrency.showChinese(DataFormat.formatAmount(pi.getDiscountAmount()), pi.getCurrencyID()));
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
			//��Ʊ���
			info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getDiscountBillAmount()));
			//��Ʊ��д���
			info.setChineseAmount(ChineseCurrency.showChinese(DataFormat.formatAmount(pi.getDiscountBillAmount()), pi.getCurrencyID()));
			//������
			info.setDateBillEnd(DataFormat.getChineseDateString(dcinfo.getAtTerm()));
			//��Ʊ��
			info.setDateBillOut(DataFormat.getChineseDateString(dcinfo.getPublicDate()));
			System.out.println("��Ʊ��:" + dcinfo.getPublicDate());
			//������Ϣ
			info.setDiscountInterest(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getDiscountInterestAmount()));
			//���ֺ���
			info.setDiscountNo(dcinfo.getCode());
			//������
			//info.setDiscountRate(DataFormat.formatDisabledAmount(pi.getDiscountAmount() / pi.getDiscountBillAmount()));
			info.setDiscountRate(DataFormat.formatString(dcinfo.getDiscountRate() + ""));
			//��������
			info.setDiscountType(LOANConstant.DraftType.getName(dcinfo.getDraftTypeID()));
			System.out.println("��������ID:" + dcinfo.getDraftTypeID());
			System.out.println("������������:" + LOANConstant.DraftType.getName(dcinfo.getDraftTypeID()));
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			//ʵ�����ֽ��
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
	         *  �����ջ�ƾ֤��ӡ 
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
			//ƾ֤��findbyid����
			IPrintTemplate iPrintTemplate = new IPrintTemplate();
			DiscountCredenceInfo discountCredenceInfo = null;
			discountCredenceInfo = iPrintTemplate.findDiscountCredenceByID(pi.getDiscountNoteID());
			if (discountCredenceInfo != null)
			{
				info.setAbstract(pi.getAbstract() + "�����ֺ�ͬ�ţ�" + discountCredenceInfo.getDiscountContractCode() + "������ƾ֤��ţ�" + discountCredenceInfo.getDraftCode());
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
			System.out.println("�����ջؽ��:" + pi.getDiscountAmount());
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
		 * �����Ϣ֪ͨ��
		 * ����ר��
		 * @param pi
		 * @param out
		 * @throws Exception
		 */
	public static void PrintDQDepositInterestNotice(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			System.out.println("�����Ϣ֪ͨ��");
			ShowPayInterestInfo info = new ShowPayInterestInfo();
			//�꣬�£���
			String strExecuteDate = DataFormat.formatDate(pi.getExecuteDate());
			if (strExecuteDate.length() < 10)
			{
				strExecuteDate = "0000000000";
			}
			info.setYear(strExecuteDate.substring(0, 4));
			info.setMonth(strExecuteDate.substring(5, 7));
			info.setDay(strExecuteDate.substring(8, 10));
			//��λ����,�����
			info.setClientName(NameRef.getClientNameByAccountID(pi.getPayAccountID()));
			//�˺�����
			info.setAccountType(SETTConstant.AccountType.getName(pi.getAccountTypeID()));
			//���ױ��
			info.setTransNo(pi.getTransNo());
			//�˺�
			info.setAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
			//������Ϣ����ʼ���ڣ���Ϣ���ڣ����������������ʣ���Ϣ
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
			//Э����Ϣ����ʼ���ڣ���Ϣ���ڣ����������������ʣ���Ϣ
			if (pi.getAccordInterest() > 0.0)
			{
				info.setAccordInterestDateStart(DataFormat.formatDate(pi.getStartDate()));
				info.setAccordInterestDateEnd(DataFormat.formatDate(pi.getInterestStartDate()));
				info.setAccordInterestDay((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), 1) + 1) + "");
				info.setAccordIntegalAmount(DataFormat.formatDouble(2, IPrintTemplate.getTotalDailyAccordAmountBanlance(pi.getPayAccountID(), pi.getStartDate(), pi.getInterestStartDate())));
				info.setAccordInterestRate(DataFormat.formatRate(pi.getAccordInterestRate()));
				info.setAccordInterest(DataFormat.formatDisabledAmount(pi.getAccordInterest()));
			}
			//��Ϣ�ܶ�
			info.setTotalInterest(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getCurrentInterest()) + DataFormat.formatDouble(pi.getAccordInterest())));
			//��Ϣ�˺�
			info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getReceiveInterestAccountID()));
			//��Ӧ�˺�
			info.setCurrentAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
			//��Ӧ��ͬ��
			info.setContractNo(NameRef.getContractNoByID(pi.getContractID()));
			//��Ӧ��ݺ�
			info.setLoanBillNo(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			//��Ӧ�浥��
			info.setDepositBillNo(pi.getFixedDepositNo()); //��Ӧ�浥��
			//ת����
			info.setTransAccountDate(DataFormat.formatDate(pi.getExecuteDate()));
			//¼����
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			if (pi.getInputUserID() < 0)
			{
				info.setInputUserName("����");
			}
			//������
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			if (pi.getCheckUserID() < 0)
			{
				info.setCheckUserName("����");
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
	 * �����Ϣ֪ͨ��
	 * @param pi
	 * @param out
	 * @throws Exception
	 */
	public static void PrintDepositInterestNotice(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			//System.out.println("�����Ϣ֪ͨ��");
			ShowPayInterestInfo info = new ShowPayInterestInfo();
			//�꣬�£���
			String strExecuteDate = DataFormat.formatDate(pi.getExecuteDate());
			if (strExecuteDate.length() < 10)
			{
				strExecuteDate = "0000000000";
			}
			info.setYear(strExecuteDate.substring(0, 4));
			info.setMonth(strExecuteDate.substring(5, 7));
			info.setDay(strExecuteDate.substring(8, 10));
			//��λ����,�����
			info.setClientName(NameRef.getClientNameByAccountID(pi.getPayAccountID()));
			//�˺�����
			info.setAccountType(SETTConstant.AccountType.getName(pi.getAccountTypeID()));
			//���ױ��
			info.setTransNo(pi.getTransNo());
			//�˺�
			info.setAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
			//�˻�����
			info.setAccountName(NameRef.getAccountNameByID(pi.getPayAccountID()));
			//������Ϣ����ʼ���ڣ���Ϣ���ڣ��������������ʣ���Ϣ
			info.setCurrentInterestDateStart(DataFormat.formatDate(pi.getStartDate()));
			info.setCurrentInterestDateEnd(DataFormat.formatDate(pi.getInterestStartDate()));
			info.setCurrentInterestDay((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), 1) + 1) + "");
			info.setCurrentInterestAmount(
				DataFormat.formatDisabledAmount(
					IPrintTemplate.getCurrentAccountBanlance(pi.getPayAccountID(), pi.getStartDate(), pi.getInterestStartDate())
						/ (getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), 1) + 1)));
			info.setCurrentInterestRate(DataFormat.formatRate(pi.getRate() + ""));
			info.setCurrentInterest(DataFormat.formatDisabledAmount(pi.getCurrentInterest()));
			//Э����Ϣ����ʼ���ڣ���Ϣ���ڣ��������������ʣ���Ϣ
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
			//��Ϣ�ܶ�
			info.setTotalInterest(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getCurrentInterest()) + DataFormat.formatDouble(pi.getAccordInterest())));
			//��Ϣ�˺�
			info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getReceiveInterestAccountID()));
			//��Ӧ�˺�
			info.setCurrentAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
			//��Ӧ��ͬ��
			info.setContractNo(NameRef.getContractNoByID(pi.getContractID()));
			//��Ӧ��ݺ�
			info.setLoanBillNo(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			//��Ӧ�浥��
			info.setDepositBillNo(pi.getFixedDepositNo()); //��Ӧ�浥��
			//ת����
			info.setTransAccountDate(DataFormat.formatDate(pi.getExecuteDate()));
			//¼����
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			if (pi.getInputUserID() < 0)
			{
				info.setInputUserName("����");
			}
			//������
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			if (pi.getCheckUserID() < 0)
			{
				info.setCheckUserName("����");
			}
			System.out.print("����1��" + info.getCurrentInterestDay());
			System.out.print("����2��" + info.getAccordInterestDay());
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
	*  ��Ϣ֪ͨ��(��Ϣ��ר��)��ӡ
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
			//��λ����
			info.setClientName(pi.getBorrowClientName());
			//ί�е�λ����
			info.setConsignClientName(NameRef.getClientNameByID(pi.getConsignClientID()));
			//�˺�����
			info.setAccountType(SETTConstant.AccountType.getName(pi.getAccountTypeID()));
			//���ױ��
			info.setTransNo(pi.getTransNo());
			//�˺�&�˻�����
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
				//����ֶ���Ϣ
				SubsectionInterest dao = new SubsectionInterest();
				SubsectionDateInfo PrintSubsectionInfo = new SubsectionDateInfo();
				PrintSubsectionInfo = dao.getSubsectionInterest(lPayAccountID, -1, pi.getLoanNoteID(), pi.getNormalInterestStart(), pi.getNormalInterestEnd());
				info.setNormalInterestDateStart(PrintSubsectionInfo.getPrintStartDate()); //������Ϣ��ʼ����
				info.setNormalInterestDateEnd(PrintSubsectionInfo.getPrintEndDate());
				info.setNormalInterestAmount(PrintSubsectionInfo.getPrintBalance()); //������Ϣ����
				info.setNormalInterestRate(PrintSubsectionInfo.getPrintInterestRate()); //������Ϣ��
				info.setNormalInterest(PrintSubsectionInfo.getPrintInterest()); //������Ϣ
				info.setNormalInterestDay(PrintSubsectionInfo.getPrintDays());

			}
			//����
			//��ʼ����--�ſ����ں��һ�������������ڣ����ʵ���������Ϣ������
			if (pi.getRealCompoundInterest() > 0)
			{
				//������Ϣ��Ϣ����
				if (pi.getCompoundInterestStart() != null)
				{
					info.setCompoundInterestDateStart(DataFormat.formatDate(pi.getCompoundInterestStart()));
				}
				//������Ϣ��Ϣ����
				info.setCompoundInterestDateEnd(DataFormat.formatDate(pi.getCompoundInterestEnd()));
				//������Ϣ����
				if (pi.getCompoundInterestStart() != null)
				{
					info.setCompoundInterestDay((getIntervalDays(pi.getCompoundInterestStart(), pi.getCompoundInterestEnd(), 1) + 1) + "");
				}

				//����������ʾΪ��
				info.setCompoundInterestAmount("&nbsp;");
				//��������
				info.setCompoundInterestRate(DataFormat.formatRate(pi.getCompoundRate() + ""));
				//������Ϣ	
				info.setCompoundInterest(DataFormat.formatDisabledAmount(pi.getRealCompoundInterest()));
			}
			//���ڷ�Ϣ
			//��ʼ���ڷ�Ϣ֪ͨ���ķ�Ϣ����/��һ�ν�Ϣ�����ڣ����ݲ�ͬ�����������ȡ��Ϣ֪ͨ���ķ�Ϣ����
			if (pi.getRealOverDueInterest() > 0)
			{
				//���ڷ�Ϣ��Ϣ��Ϣ����
				if (pi.getOverDueStart() != null)
				{
					info.setOverInterestDateStart(DataFormat.formatDate(pi.getOverDueStart()));
				}
				//���ڷ�Ϣ��Ϣ��Ϣ����
				info.setOverInterestDateEnd(DataFormat.formatDate(pi.getOverDueEnd()));
				//���ڷ�Ϣ��Ϣ����
				if (pi.getOverDueStart() != null)
				{
					info.setOverInterestDay((getIntervalDays(pi.getOverDueStart(), pi.getOverDueEnd(), 1) + 1) + "");
				}
				//���ڷ�Ϣ����
				info.setOverInterestAmount(DataFormat.formatDisabledAmount(pi.getOverDueAmount()));
				if (pi.getOverDueAmount() == 0.00)
				{
					info.setOverInterestAmount("0.00");
				}
				//���ڷ�Ϣ����
				info.setOverInterestRate(DataFormat.formatRate(pi.getOverDueRate() + ""));
				//���ڷ�Ϣ��Ϣ
				info.setOverInterest(DataFormat.formatDisabledAmount(pi.getRealOverDueInterest()));
			}
			//������
			//��ʼ����ȡ�ſ�֪ͨ������ʼ����/��һ�ν��������ѵ�����
			if (pi.getRealCommission() > 0)
			{
				//��������Ϣ��Ϣ����
				info.setCommissionFeeDateStart(DataFormat.formatDate(pi.getCommissionStart()));
				//��������Ϣ��Ϣ����
				info.setCommissionFeeDateEnd(DataFormat.formatDate(pi.getCommissionFeeEnd()));
				//��������Ϣ����
				info.setCommissionFeeDay((getIntervalDays(pi.getCommissionStart(), pi.getCommissionFeeEnd(), 1) + 1) + "");
				//��������Ϣ����		
				info.setCommissionFeeAmount(DataFormat.formatDisabledAmount(pi.getCommissionAmount()));
				if (pi.getCommissionAmount() == 0.00)
				{
					info.setCommissionFeeAmount("0.00");
				}
				//����������				
				info.setCommissionFeeRate(DataFormat.formatRate(pi.getCommissionRate() + ""));
				//��������Ϣ
				info.setCommissionFee(DataFormat.formatDisabledAmount(pi.getRealCommission()));
			}
			if (pi.getRealSuretyFee() > 0)
			{
				//������
				//��ʼ����ȡ�ſ�֪ͨ������ʼ����/��һ�ν��㵫���ѵ����ڣ����ʴӷſ�֪ͨ��ȡ
				//��������Ϣ��Ϣ����
				info.setAssureFeeDateStart(DataFormat.formatDate(pi.getSuretyFeeStart()));
				//��������Ϣ��Ϣ����
				info.setAssureFeeDateEnd(DataFormat.formatDate(pi.getSuretyFeeEnd()));
				//��������Ϣ����				
				info.setAssureFeeDay((getIntervalDays(pi.getSuretyFeeStart(), pi.getSuretyFeeEnd(), 1) + 1) + "");
				//��������Ϣ����		
				info.setAssureFeeAmount(DataFormat.formatDisabledAmount(pi.getSuretyFeeAmount()));
				if (pi.getSuretyFeeAmount() == 0.0)
				{
					info.setAssureFee("0.00");
				}
				//����������
				info.setAssureFeeRate(DataFormat.formatRate(pi.getSuretyFeeRate() + ""));
				//��������Ϣ
				info.setAssureFee(DataFormat.formatDisabledAmount(pi.getRealSuretyFee()));
			}
			//��Ϣ�ܶ�
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
			//��Ϣ�˺�
			info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getPayInterestAccountID()));
			//��Ӧ�Ļ����˺�
			info.setCurrentAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
			//��Ӧ�ĺ�ͬ��
			info.setContractNo(NameRef.getContractNoByID(pi.getContractID()));
			//��Ӧ��ݺ�
			info.setLoanBillNo(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			//��Ӧ�浥��
			info.setDepositBillNo(pi.getFixedDepositNo());
			//ת����
			info.setTransAccountDate(DataFormat.formatDate(pi.getExecuteDate()));
			//¼����
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			if (pi.getInputUserID() < 0)
			{
				info.setInputUserName("����");
			}
			//������
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			if (pi.getCheckUserID() < 0)
			{
				info.setCheckUserName("����");
			}
			System.out.println("��������:" + pi.getTransTypeID());
			if (pi.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE)
			{
				System.out.println("�������ͣ���Ӫ");
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
	*  ��Ϣ֪ͨ��(֧ȡ)��ӡ
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
			//��λ����
			if (pi.getPayClientID() > 0)
			{
				info.setClientName(NameRef.getClientNameByID(pi.getPayClientID()));
			}
			else
			{
				info.setClientName(pi.getBorrowClientName());
			}
			//ί�е�λ����
			info.setConsignClientName(NameRef.getClientNameByID(pi.getConsignClientID()));
			//�˺�����
			info.setAccountType(SETTConstant.AccountType.getName(pi.getAccountTypeID()));
			//���ױ��
			info.setTransNo(pi.getTransNo());
			//�˺�
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
			//������Ϣ
			//����ֶ���Ϣ
			SubsectionInterest dao = new SubsectionInterest();
			SubsectionDateInfo PrintSubsectionInfo = new SubsectionDateInfo();
			PrintSubsectionInfo = dao.getSubsectionInterest(lPayAccountID, -1, pi.getLoanNoteID(), pi.getLatestInterestClearDate(), DataFormat.getPreviousDate(pi.getInterestClearDate()));
			info.setNormalInterestDateStart(PrintSubsectionInfo.getPrintStartDate()); //������Ϣ��ʼ����
			info.setNormalInterestDateEnd(PrintSubsectionInfo.getPrintEndDate());
			info.setNormalInterestAmount(PrintSubsectionInfo.getPrintBalance()); //������Ϣ����
			info.setNormalInterestRate(PrintSubsectionInfo.getPrintInterestRate()); //������Ϣ��
			info.setNormalInterest(PrintSubsectionInfo.getPrintInterest()); //������Ϣ
			info.setNormalInterestDay(PrintSubsectionInfo.getPrintDays());
			//����
			if (pi.getRealCompoundInterest() > 0)
			{
				//������Ϣ��Ϣ����
				if (pi.getCompoundInterestStart() != null)
				{
					info.setCompoundInterestDateStart(DataFormat.formatDate(pi.getCompoundInterestStart()));
				}
				//������Ϣ��Ϣ����
				if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
				{
					info.setCompoundInterestDateEnd(DataFormat.formatDate(DataFormat.getPreviousDate(pi.getInterestClearDate())));
				}
				else
				{
					info.setCompoundInterestDateEnd(DataFormat.formatDate(pi.getInterestClearDate()));
				}
				//������Ϣ����
				if (pi.getCompoundInterestStart() != null)
				{
					info.setCompoundInterestDay((getIntervalDays(pi.getCompoundInterestStart(), pi.getInterestStartDate(), 1)) + "");
				}
				info.setCompoundInterestAmount("&nbsp;");
				//��������
				info.setCompoundInterestRate(DataFormat.formatRate(pi.getCompoundRate() + ""));
				//������Ϣ	
				info.setCompoundInterest(DataFormat.formatDisabledAmount(pi.getRealCompoundInterest()));
				if (pi.getRealCompoundInterest() == 0.0)
				{
					info.setCompoundInterest("0.00");
				}
			}
			//���ڷ�Ϣ
			if (pi.getRealOverDueInterest() > 0)
			{
				//���ڷ�Ϣ��Ϣ��Ϣ����
				if (pi.getOverDueStart() != null)
				{
					info.setOverInterestDateStart(DataFormat.formatDate(pi.getOverDueStart()));
				}
				//���ڷ�Ϣ��Ϣ��Ϣ����
				if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
				{
					info.setOverInterestDateEnd(DataFormat.formatDate(DataFormat.getPreviousDate(pi.getInterestClearDate())));
				}
				else
				{
					info.setOverInterestDateEnd(DataFormat.formatDate(pi.getInterestClearDate()));
				}
				//���ڷ�Ϣ��Ϣ����
				if (pi.getOverDueStart() != null)
				{
					info.setOverInterestDay((getIntervalDays(pi.getOverDueStart(), pi.getInterestClearDate(), 1)) + "");
				}
				//���ڷ�Ϣ����
				info.setOverInterestAmount(DataFormat.formatDisabledAmount(pi.getOverDueAmount()));
				if (pi.getOverDueAmount() == 0.00)
				{
					info.setOverInterestAmount("0.00");
				}
				//���ڷ�Ϣ����
				info.setOverInterestRate(DataFormat.formatRate(pi.getOverDueRate() + ""));
				//���ڷ�Ϣ��Ϣ
				info.setOverInterest(DataFormat.formatDisabledAmount(pi.getRealOverDueInterest()));
				if (pi.getRealOverDueInterest() == 0.0)
				{
					info.setOverInterest("0.00");
				}
			}
			//������
			if (pi.getRealCommission() > 0)
			{
				//��������Ϣ��Ϣ����
				info.setCommissionFeeDateStart(DataFormat.formatDate(pi.getCommissionStart()));
				//��������Ϣ��Ϣ����
				if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
				{
					info.setCommissionFeeDateEnd(DataFormat.formatDate(DataFormat.getPreviousDate(pi.getInterestClearDate())));
				}
				else
				{
					info.setCommissionFeeDateEnd(DataFormat.formatDate(pi.getInterestClearDate()));
				}
				//��������Ϣ����
				info.setCommissionFeeDay((getIntervalDays(pi.getCommissionStart(), pi.getInterestClearDate(), 1)) + "");
				//��������Ϣ����		
				info.setCommissionFeeAmount(DataFormat.formatDisabledAmount(pi.getAmount()));
				if (pi.getAmount() == 0.00)
				{
					info.setCommissionFeeAmount("0.00");
				}
				//����������				
				System.out.print("========���������ʣ�" + pi.getCommissionRate());
				info.setCommissionFeeRate(DataFormat.formatRate(pi.getCommissionRate() + ""));
				//��������Ϣ
				info.setCommissionFee(DataFormat.formatDisabledAmount(pi.getRealCommission()));
				if (pi.getRealCommission() == 0.0)
				{
					info.setCommissionFee("0.00");
				}
			}
			if (pi.getRealSuretyFee() > 0)
			{
				//������
				//��������Ϣ��Ϣ����
				info.setAssureFeeDateStart(DataFormat.formatDate(pi.getSuretyFeeStart()));
				//��������Ϣ��Ϣ����
				if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
				{
					info.setAssureFeeDateEnd(DataFormat.formatDate(DataFormat.getPreviousDate(pi.getInterestClearDate())));
				}
				else
				{
					info.setAssureFeeDateEnd(DataFormat.formatDate(pi.getInterestClearDate()));
				}
				//��������Ϣ����				
				info.setAssureFeeDay((getIntervalDays(pi.getSuretyFeeStart(), pi.getInterestClearDate(), 1)) + "");
				//��������Ϣ����		
				info.setAssureFeeAmount(DataFormat.formatDisabledAmount(pi.getAmount()));
				if (pi.getAmount() == 0.0)
				{
					info.setAssureFee("0.00");
				}
				//����������
				info.setAssureFeeRate(DataFormat.formatRate(pi.getSuretyFeeRate() + ""));
				//��������Ϣ
				info.setAssureFee(DataFormat.formatDisabledAmount(pi.getRealSuretyFee()));
				if (pi.getRealSuretyFee() == 0.0)
				{
					info.setAssureFee("0.00");
				}
			}
			//��Ϣ�ܶ�
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
			//��Ϣ�˺�
			info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getPayInterestAccountID()));
			//��Ӧ���˺�
			info.setCurrentAccountNo(NameRef.getAccountNoByID(lPayAccountID));
			//��Ӧ�ĺ�ͬ��
			info.setContractNo(NameRef.getContractNoByID(pi.getContractID()));
			//��Ӧ��ݺ�
			info.setLoanBillNo(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			//��Ӧ�浥��
			info.setDepositBillNo(pi.getFixedDepositNo());
			//ת����
			info.setTransAccountDate(DataFormat.formatDate(pi.getInterestStartDate()));
			//¼����
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			if (pi.getInputUserID() < 0)
			{
				info.setInputUserName("����");
			}
			//������
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			if (pi.getCheckUserID() < 0)
			{
				info.setCheckUserName("����");
			}
			System.out.println("��������:" + pi.getTransTypeID());
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
	         *  ֪ͨ����֤ʵ���ӡ 
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
				sInsert = pi.getNoticeDay() - 10000 + "��";
			}
			else
			{
				sInsert = pi.getNoticeDay() + "����";
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
	         *  ���ڴ���֤ʵ���ӡ 
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
				//����Ƕ�������,�������Ϊ����+������Ϣ+��Ϣ֧��(2004/4/30)
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
				sInterval = pi.getFixedDepositTerm() - 10000 + "��";
			}
			else
			{
				sInterval = pi.getFixedDepositTerm() + "����";
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
	         *  �����Ϣ�Ƹ�֪ͨ��(����֪ͨ��� ���ڴ����ջ�ƾ֤) 
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
			if (pi.getCurrentInterest() != 0.0) //�л�����Ϣ
			{
				System.out.println("====1=====");
				if (pi.getInterestStartDate().before(pi.getEndDate())) //����Ϣ������ֹ����֮ǰ������ǰ֧ȡ
				{
					//����1��������Ϣ��������Ϣ���ʣ���ʼ���ڣ��������ڣ�����
					info.setAmount1(DataFormat.formatAmount(pi.getAmount()));
					info.setInterest1(DataFormat.formatAmount(pi.getCurrentInterest()));
					info.setRate1(DataFormat.formatAmount(0.72));
					System.out.println("����1" + info.getRate1());
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
						//����֧ȡ&��������
						info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), 2)) + "");
					}
					else if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
					{
						System.out.println("====3=====");
						//֪֧ͨȡ
						info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), 1)) + "");
					}
					else
					{
						System.out.println("====4=====");
						info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), 1) + 1) + "");
					}
					//����2��������Ϣ(������)��������Ϣ����(��)����ʼ���ڣ��������ڣ�����
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
							//����֧ȡ&��������
							info.setDayNumber2((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), 2)) + "");
						}
						else if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
						{
							System.out.println("====7=====");
							//֪֧ͨȡ
							info.setDayNumber2((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), 1)) + "");
						}
						else
						{
							System.out.println("====8=====");
							info.setDayNumber2((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), 1) + 1) + "");
						}
					}
					//��Ϣ�ϼ�
					info.setTotalInterest(DataFormat.formatAmount(DataFormat.formatDouble(DataFormat.formatDouble(pi.getCurrentInterest()) + DataFormat.formatDouble(pi.getOtherInterest()))));
				}
				else //����Ϣ������ֹ����֮��
					{
					System.out.println("====9=====");
					//����1����Ϣ֧������Ϣ֧�����ʣ���ʼ���ڣ��������ڣ�����
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
						//����֧ȡ&��������
						info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), 2)) + "");
					}
					else if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
					{
						System.out.println("====11=====");
						//֪֧ͨȡ
						info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), 1)) + "");
					}
					else
					{
						System.out.println("====12=====");
						info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), 1) + 1) + "");
					}
					//����2��������Ϣ��������Ϣ���ʣ���ʼ���ڣ��������ڣ�����
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
						//����֧ȡ&��������
						info.setDayNumber2((getIntervalDays(pi.getEndDate(), pi.getInterestStartDate(), 1)) + "");

					}
					else if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
					{
						System.out.println("====14=====");
						//֪֧ͨȡ
						info.setDayNumber2((getIntervalDays(pi.getEndDate(), pi.getInterestStartDate(), 1)) + "");
					}
					else
					{
						System.out.println("====15=====");
						info.setDayNumber2((getIntervalDays(pi.getEndDate(), pi.getInterestStartDate(), 1) + 1) + "");
					}
					if (pi.getOtherInterest() != 0.0) //������Ϣ
					{
						System.out.println("====16=====");
						//����3��������Ϣ(������)��������Ϣ����(��)����ʼ���ڣ��������ڣ�����
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
							//����֧ȡ&��������
							info.setDayNumber3((getIntervalDays(pi.getEndDate(), pi.getInterestStartDate(), 2)) + "");
						}
						else if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
						{
							System.out.println("====18=====");
							//֪֧ͨȡ
							info.setDayNumber3((getIntervalDays(pi.getEndDate(), pi.getInterestStartDate(), 1)) + "");
						}
						else
						{
							System.out.println("====19=====");
							info.setDayNumber3((getIntervalDays(pi.getEndDate(), pi.getInterestStartDate(), 1) + 1) + "");
						}
					}
					//��Ϣ�ϼ�
					info.setTotalInterest(DataFormat.formatAmount(DataFormat.formatDouble(pi.getPayableInterest())));
				}
			}
			else //û�л�����Ϣ
				{
				System.out.println("====20=====");
				//����1����Ϣ֧������Ϣ֧�����ʣ���ʼ���ڣ��������ڣ�����
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
					//����֧ȡ&��������
					info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), 2)) + "");
				}
				else if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
				{
					System.out.println("====22=====");
					//֪֧ͨȡ
					info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), 1)) + "");
				}
				else
				{
					System.out.println("====23=====");
					info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), 1) + 1) + "");
				}
				//��Ϣ�ϼ�
				info.setTotalInterest(DataFormat.formatAmount(DataFormat.formatDouble(pi.getPayableInterest())));
			}
			if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
			{
				//����Ƕ�������
				//������Ϣ
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
						//����֧ȡ&��������
						info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), 2)) + "");
					}
					else if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
					{
						//֪֧ͨȡ
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
							//����֧ȡ&��������
							info.setDayNumber2((getIntervalDays(pi.getStartDate(), pi.getEndDate(), 2)) + "");
						}
						else if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
						{
							//֪֧ͨȡ
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
						//����֧ȡ&��������
						info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), 2)) + "");
					}
					else if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
					{
						//֪֧ͨȡ
						info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), 1)) + "");
					}
					else
					{
						info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), 1) + 1) + "");
					}
				}
				//��Ϣ�ϼ�
				info.setTotalInterest(DataFormat.formatAmount(DataFormat.formatDouble(DataFormat.formatDouble(pi.getRealInterestReceivable()) + DataFormat.formatDouble(pi.getPayableInterest()))));
			}
			info.setTotalInterestChinese(ChineseCurrency.showChinese(info.getTotalInterest(), pi.getCurrencyID()));
			//��Ϣ�˺�
			info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getReceiveInterestAccountID()));
			//�������
			info.setDepositTypeName(SETTConstant.TransactionType.getName(pi.getTransTypeID()));
			if (info.getDepositTypeName().indexOf("����") >= 0)
			{
				info.setDepositTypeName("���ڴ��");
			}
			else
			{
				info.setDepositTypeName("֪ͨ���");
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
	 * ��ӡӦ��ί�д�����Ϣ�������ѣ�֪ͨ��
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
			//��ţ���
			info.setNoticeYear(pi.getFormYear());
			//��ţ���
			info.setNoticeNo(pi.getFormNo());
			//�����
			info.setBorrowClientName(pi.getBorrowClientName());
			//���ݺ�ͬid���Һ�ͬǩ������
			ContractInfo contractInfo = new ContractInfo();
			ContractDao contractDao = new ContractDao();
			contractInfo = contractDao.findByID(pi.getContractID());
			//ǩ����ͬ��
			if (contractInfo != null)
			{
				strTemp = DataFormat.formatDate(contractInfo.getLoanStart());
				if (strTemp.length() < 10)
					strTemp = "0000000000";
				strYear = strTemp.substring(0, 4);
				strMonth = strTemp.substring(5, 7);
				strDay = strTemp.substring(8, 10);
				info.setSignLoanNoteYear(strYear);
				//��
				info.setSignLoanNoteMonth(strMonth);
				//��
				info.setSignLoanNoteDay(strDay);
			}
			//ί�д����ͬ��
			info.setContractNo(NameRef.getContractNoByID(pi.getContractID()));
			//�ſ�֪ͨ����
			info.setLoanNoteNo(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			//��Ϣ������
			strTemp = DataFormat.formatDate(pi.getClearInterestDate());
			if (strTemp.length() < 10)
				strTemp = "0000000000";
			strYear = strTemp.substring(0, 4);
			strMonth = strTemp.substring(5, 7);
			strDay = strTemp.substring(8, 10);
			info.setInterestYear(strYear);
			//��
			info.setInterestMonth(strMonth);
			//��	
			info.setInterestDay(strDay);
			//��Ϣ����
			info.setInterestQuarter(IPrintTemplate.getQuarterByMonth(Long.parseLong(info.getInterestMonth())));
			//ί�д�����Ϣ
			info.setConsignLoanInterest(
				DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getInterest()) + DataFormat.formatDouble(pi.getCompoundInterest()) + DataFormat.formatDouble(pi.getOverDueInterest())));
			//������
			info.setCommission(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getCommission())));
			//ί�д�����Ϣ�����Ѻϼ�	
			info.setTotalInterestCommission(
				DataFormat.formatDisabledAmount(
					DataFormat.formatDouble(pi.getInterest())
						+ DataFormat.formatDouble(pi.getCompoundInterest())
						+ DataFormat.formatDouble(pi.getCommission())
						+ DataFormat.formatDouble(pi.getOverDueInterest())));
			//�����λԪ
			info.setLoanAmount(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getAmount())));
			//������λԪ
			if (pi.getLoanBalance() > 0)
			{
				info.setLoanBalance(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getLoanBalance())));
			}
			else
			{
				info.setLoanBalance("0.00");
			}
			//��ͬ������Ϣ
			info.setContractRate(String.valueOf(DataFormat.formatDouble(pi.getContractRate(), 6)));
			//ִ��������Ϣ������ǰ��
			info.setExecuteRate(String.valueOf(DataFormat.formatDouble(pi.getExecuteRate(), 6)));
			//ִ�����ʵ�����
			String ExecuteRateAdjustYear = "";
			strTemp = DataFormat.formatDate(pi.getAdjustRateDate());
			if (strTemp.length() < 10)
			{
				info.setExecuteRateAdjustYear("&nbsp;&nbsp;&nbsp;&nbsp;");
				//ִ�����ʵ�����
				info.setExecuteRateAdjustMonth("&nbsp;&nbsp;");
				//ִ�����ʵ�����
				info.setExecuteRateAdjustDay("&nbsp;&nbsp;");
			}
			else
			{
				strYear = strTemp.substring(0, 4);
				strMonth = strTemp.substring(5, 7);
				strDay = strTemp.substring(8, 10);
				info.setExecuteRateAdjustYear(strYear);
				//ִ�����ʵ�����
				info.setExecuteRateAdjustMonth(strMonth);
				//ִ�����ʵ�����
				info.setExecuteRateAdjustDay(strDay);
			}
			//ִ��������Ϣ��������
			info.setExecuteRateNew(String.valueOf(DataFormat.formatDouble(pi.getExecuteRateNew(), 6)));
			//�������û�з����仯����ִ�����ʵĵ������ں͵������ִ��������ʾΪ��
			if (pi.getExecuteRateNew() == 0)
			{
				//ִ�����ʵ�����
				info.setExecuteRateAdjustYear("&nbsp;&nbsp;&nbsp;&nbsp;");
				//ִ�����ʵ�����
				info.setExecuteRateAdjustMonth("&nbsp;&nbsp;");
				//ִ�����ʵ�����
				info.setExecuteRateAdjustDay("&nbsp;&nbsp;");
				//ִ��������Ϣ��������
				info.setExecuteRateNew("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			}
			//��������
			info.setCommissionRate(String.valueOf(pi.getCommissionRate()));
			//�������ޣ��£�
			info.setLoanTerm(String.valueOf(pi.getLoanTerm()));
			//��������(��ʼ��)
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
			strTemp = "<u>" + strYear + "&nbsp;</u>��<u>&nbsp;" + strMonth + "&nbsp;</u>��<u>&nbsp;" + strDay + "&nbsp;</u>��";
			info.setLoanStartDate(strTemp);
			//�������ޣ������գ�
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
			strTemp = "<u>" + strYear + "&nbsp;</u>��<u>&nbsp;" + strMonth + "&nbsp;</u>��<u>&nbsp;" + strDay + "&nbsp;</u>��";
			info.setLoanStartEnd(strTemp);
			//Ӧ��������Ϣ
			info.setLoanInterest("��" + DataFormat.formatAmount(DataFormat.formatDouble(pi.getInterest()) + DataFormat.formatDouble(pi.getOverDueInterest())));
			//Ӧ������
			info.setLoanCompoundInterest("��" + DataFormat.formatAmount(pi.getCompoundInterest()));
			//Ӧ��������
			info.setLoanCommission("��" + DataFormat.formatAmount(pi.getCommission()));
			//Ӧ����Ϣ���úϼ�
			String TotalInterestFee = "";
			info.setTotalInterestFee(
				"��"
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
			//ִ������
			info.setExecuteDateYear(strYear);
			//��
			info.setExecuteDateMonth(strMonth);
			//��
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
	 * ��ӡӦ��������Ϣ֪ͨ��
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
			//��ţ�����
			info.setSerialYear(pi.getFormYear());
			//��ţ��ţ�
			info.setSerialNo(pi.getFormNo());
			//�����
			info.setDebitName(pi.getBorrowClientName());
			//�����ͬǩ����
			//���ݺ�ͬid���Һ�ͬǩ������
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
					strTemp = "<u>&nbsp;" + strYear + "&nbsp;</u>��<u>&nbsp;" + strMonth + "&nbsp;</u>��<u>&nbsp;" + strDay + "&nbsp;</u>��";
					info.setDateSign(strTemp);
				}
			}
			//�����ͬ��
			info.setLoanContractCode(NameRef.getContractNoByID(pi.getContractID()));
			//�ſ�֪ͨ��
			info.setLetOutRequisition(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			//Ӧ֧������
			if (pi.getClearInterestDate() != null)
			{
				strYear = DataFormat.getYearString(pi.getClearInterestDate());
				strMonth = Long.valueOf(DataFormat.getMonthString(pi.getClearInterestDate())).longValue() + 1 + "";
				strDay = DataFormat.getDayString(pi.getClearInterestDate());
				strTemp = "<u>&nbsp;" + strYear + "&nbsp;</u>��<u>&nbsp;" + strMonth + "&nbsp;</u>��<u>&nbsp;" + strDay + "&nbsp;</u>��";
				info.setDateDuePay(strTemp);
			}
			//����
			strTemp = DataFormat.formatDate(pi.getClearInterestDate());
			if (strTemp.length() > 7)
			{
				info.setQuarter(IPrintTemplate.getQuarterByMonth(Long.parseLong(strTemp.substring(5, 7))));
			}
			//��Ϣ
			info.setInterestAmount(
				DataFormat.formatDisabledAmount(
					DataFormat.formatDouble(DataFormat.formatDouble(pi.getInterest()) + DataFormat.formatDouble(pi.getCompoundInterest()) + DataFormat.formatDouble(pi.getOverDueInterest()))));
			//�����
			info.setLoanAmount(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getAmount())));
			//�������
			if (pi.getLoanBalance() > 0)
			{
				info.setLoanBalance(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getLoanBalance())));
			}
			else
			{
				info.setLoanBalance("0.00");
			}
			//��ͬ����
			info.setContractRate(String.valueOf(DataFormat.formatDouble(pi.getContractRate(), 6)));
			//ִ������
			info.setExcecuteRate(String.valueOf(DataFormat.formatDouble(pi.getExecuteRate(), 6)));
			//�������ʵ�������
			if (pi.getAdjustRateDate() != null)
			{
				strYear = DataFormat.getYearString(pi.getAdjustRateDate());
				strMonth = Long.valueOf(DataFormat.getMonthString(pi.getAdjustRateDate())).longValue() + 1 + "";
				strDay = DataFormat.getDayString(pi.getAdjustRateDate());
				strTemp = "<u>&nbsp;" + strYear + "&nbsp;</u>��<u>&nbsp;" + strMonth + "&nbsp;</u>��<u>&nbsp;" + strDay + "&nbsp;</u>��";
				info.setDateRateModify(strTemp);
			}
			else
			{
				info.setDateRateModify("<u>&nbsp;&nbsp;&nbsp;&nbsp;</u>��<u>&nbsp;&nbsp;&nbsp;&nbsp;</u>��<u>&nbsp;&nbsp;&nbsp;&nbsp;</u>��");
			}
			//��������Ϣ
			info.setRateModify(String.valueOf(DataFormat.formatDouble(pi.getExecuteRateNew(), 6)));
			if (pi.getExecuteRateNew() == 0)
			{
				info.setRateModify("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			}
			//�������ޣ��£�
			info.setLoanIntervalMonth(String.valueOf(pi.getLoanTerm()));
			//��������(��ʼ��)
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
			strTemp = "<u>&nbsp;" + strYear + "&nbsp;</u>��<u>&nbsp;" + strMonth + "&nbsp;</u>��<u>&nbsp;" + strDay + "&nbsp;</u>��";
			info.setLoanIntervalStart(strTemp);
			//�������ޣ������գ�
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
			strTemp = "<u>&nbsp;" + strYear + "&nbsp;</u>��<u>&nbsp;" + strMonth + "&nbsp;</u>��<u>&nbsp;" + strDay + "&nbsp;</u>��";
			info.setLoanIntervalEnd(strTemp);
			//Ӧ��������Ϣ
			info.setDuePayLoanInterest("��" + DataFormat.formatAmount(pi.getInterest()) + DataFormat.formatAmount(pi.getOverDueInterest()));
			//Ӧ������
			info.setDuePayCompundInterest("��" + DataFormat.formatAmount(pi.getCompoundInterest()));
			//Ӧ����Ϣ���úϼ�
			info.setDuePayToalInterest(
				"��"
					+ DataFormat.formatAmount(
						DataFormat.formatDouble(DataFormat.formatDouble(pi.getInterest()) + DataFormat.formatDouble(pi.getOverDueInterest()) + DataFormat.formatDouble(pi.getCompoundInterest()))));
			//�������ڣ��꣩
			strTemp = DataFormat.formatDate(pi.getExecuteDate());
			if (strTemp.length() < 10)
				strTemp = "0000000000";
			strYear = strTemp.substring(0, 4);
			strMonth = strTemp.substring(5, 7);
			strDay = strTemp.substring(8, 10);
			info.setYearSeal(strYear);
			//�������ڣ��£�
			info.setMonthSeal(strMonth);
			//�������ڣ��գ�		    
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
	 * ��ӡӦ��������Ϣ֪ͨ�� �ſ��ϸ��Ŀ
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
				//ȡ��ÿ����ͬ�Ļ��� �� ��ϸ��Ŀ
				pi = (PrintInfo) vctLoanNoticeDetails.elementAt(i);
				LoanPayFormDetailInfo lpfdinfo = new LoanPayFormDetailInfo();
				lpfdinfo.setLoadNoteID(pi.getLoanNoteID());
				Sett_TransGrantLoanDAO stdao = new Sett_TransGrantLoanDAO();
				lpfdinfo = stdao.findPayFormDetailByCondition(lpfdinfo);
				System.out.println("===�ſ�֪ͨ����" + pi.getLoanNoteID());
				System.out.println("===��Ϣ�գ�" + lpfdinfo.getInterestStart());
				dSumLoanAmount += DataFormat.formatDouble(lpfdinfo.getAmount());
				dSumInterest += DataFormat.formatDouble(pi.getInterest());
				dSumCompoundInterest += DataFormat.formatDouble(pi.getCompoundInterest());
				//����
				PrintInfo tmpPrintInfo = new PrintInfo();
				//�ж����ʵ��������Ƿ�����Ϣ�պͽ�Ϣ��֮��
				if (pi.getAdjustRateDate() != null && pi.getInterestStartDate() != null && pi.getClearInterestDate() != null)
				{
					if (pi.getAdjustRateDate().after(pi.getInterestStartDate()) && (pi.getAdjustRateDate().before(pi.getClearInterestDate()) || pi.getAdjustRateDate() == pi.getClearInterestDate()))
					{
						//������������Ϣ�պͽ�Ϣ��֮��
						//ִ������
						tmpPrintInfo.setExecuteRate(pi.getExecuteRateNew());
						//�������ʵ�������
						tmpPrintInfo.setAdjustRateDate(pi.getAdjustRateDate());
						//��������Ϣ
						tmpPrintInfo.setExecuteRateNew(pi.getExecuteRateNew());
					}
					else if (!pi.getAdjustRateDate().after(pi.getInterestStartDate()))
					{
						//������������Ϣ��֮ǰ��������Ϣ�յ���
						if (pi.getExecuteRateNew() > 0 && (pi.getExecuteRate() != pi.getExecuteRateNew()))
						{
							tmpPrintInfo.setExecuteRate(pi.getExecuteRateNew());
							//�������ʵ�������
							tmpPrintInfo.setAdjustRateDate(null);
							//��������Ϣ
							tmpPrintInfo.setExecuteRateNew(0.0);
						}
						else
						{
							tmpPrintInfo.setExecuteRate(pi.getExecuteRate());
							//�������ʵ�������
							tmpPrintInfo.setAdjustRateDate(null);
							//��������Ϣ
							tmpPrintInfo.setExecuteRateNew(0.0);
						}
					}
				}
				else
				{
					if (pi.getExecuteRateNew() == 0)
					{
						//ִ������
						tmpPrintInfo.setExecuteRate(pi.getExecuteRate());
						//�������ʵ�������
						tmpPrintInfo.setAdjustRateDate(null);
						//��������Ϣ
						tmpPrintInfo.setExecuteRateNew(0.0);
					}
					else
					{
						//��������Ϣ
						tmpPrintInfo.setExecuteRate(pi.getExecuteRateNew());
						//�������ʵ�������
						tmpPrintInfo.setAdjustRateDate(null);
						//��������Ϣ
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
							+ "    <td align=\"right\" height=\"30\"><font style=\"font-size:12px\"><b>��ţ�<u>&nbsp;&nbsp;"
							+ pi.getFormYear()
							+ "&nbsp;&nbsp;</u>��<u>&nbsp;&nbsp;"
							+ pi.getFormNo()
							+ "&nbsp;&nbsp;</u>��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></font></td>"
							+ "  </tr>"
							+ "  <tr> "
							+ "    <td align=\"center\" height=\"40\"><font style=\"font-size:24px\"><b>"
							+ Env.getClientName()
							+ "</b></font></td>"
							+ "  </tr>"
							+ "  <tr> "
							+ "    <td align=\"center\" height=\"40\"><font style=\"font-size:20px\"><b>Ӧ��������Ϣ֪ͨ�飨��¼��</b></font></td>"
							+ "  </tr>"
							+ "  <tr> "
							+ "    <td height=\"30\">&nbsp;</td>"
							+ "  </tr>"
							+ "  <tr> "
							+ "    <td align=\"left\" height=\"40\"><font style=\"font-size:20px\"><b>����ˣ�<u>&nbsp;"
							+ pi.getBorrowClientName()
							+ "&nbsp;</u></b></font></td>"
							+ "  <tr> "
							+ "    <td align=\"left\" height=\"40\"><font style=\"font-size:20px\"><b>�����ͬ�ţ�<u>&nbsp;"
							+ NameRef.getContractNoByID(pi.getContractID())
							+ "&nbsp;</u></b></font></td>"
							+ "  </tr>"
							+ "  <tr> "
							+ "    <td>"
							+ "<table border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"table1\" width=\"640\">"
							+ "<tr>"
							+ "<td class=\"td-right\" align=\"center\">�ſ�֪ͨ�����</td>"
							+ "<td class=\"td-right\" align=\"center\">�ſ���</td>"
							+ "<td class=\"td-right\" align=\"center\">�ſ�����</td>"
							+ "<td class=\"td-right\" align=\"center\">��Ϣ��</td>"
							+ "<td class=\"td-right\" align=\"center\">ֹϢ��</td>"
							+ "<td class=\"td-right\" align=\"center\">���ʣ�</td>"
							+ "<td class=\"td-right\" align=\"center\">Ӧ����Ϣ</td>"
							+ "<td class=\"td-right\" align=\"center\">Ӧ������</td>"
							+ "<td class=\"td-right\" align=\"center\">Ӧ����Ϣ���úϼ�</td>"
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
						+ "��"
						+ DataFormat.formatDisabledAmount(pi.getInterest())
						+ "</font></td>"
						+ "<td class=\"td-topright\" align=\"right\"><font style=\"font-size:12px\">"
						+ "��"
						+ DataFormat.formatDisabledAmount(pi.getCompoundInterest())
						+ "</font></td>"
						+ "<td class=\"td-top\" align=\"right\"><font style=\"font-size:12px\">"
						+ "��"
						+ DataFormat.formatDisabledAmount(DataFormat.formatDouble(DataFormat.formatDouble(pi.getInterest()) + DataFormat.formatDouble(pi.getCompoundInterest())))
						+ "</font></td>"
						+ "</tr>");
			} //end for
			out.println(
				"<tr>"
					+ "<td class=\"td-topright\" align=\"center\"><font style=\"font-size:12px\"><B>�ϼ�</B></font></td>"
					+ "<td class=\"td-topright\" align=\"right\"><font style=\"font-size:12px\">"
					+ "��"
					+ DataFormat.formatDisabledAmount(dSumLoanAmount)
					+ "</font></td>"
					+ "<td class=\"td-topright\"><font style=\"font-size:12px\">&nbsp;</font></td>"
					+ "<td class=\"td-topright\"><font style=\"font-size:12px\">&nbsp;</font></td>"
					+ "<td class=\"td-topright\"><font style=\"font-size:12px\">&nbsp;</font></td>"
					+ "<td class=\"td-topright\"><font style=\"font-size:12px\">&nbsp;</font></td>"
					+ "<td class=\"td-topright\" align=\"right\"><font style=\"font-size:12px\">"
					+ "��"
					+ DataFormat.formatDisabledAmount(dSumInterest)
					+ "</font></td>"
					+ "<td class=\"td-topright\" align=\"right\"><font style=\"font-size:12px\">"
					+ "��"
					+ DataFormat.formatDisabledAmount(dSumCompoundInterest)
					+ "</font></td>"
					+ "<td class=\"td-top\" align=\"right\"><font style=\"font-size:12px\">"
					+ "��"
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
	 * ��ӡ���ڴ������֪ͨ��
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
			//��ţ��꣩
			info.setSerialYear(pi.getFormYear());
			//��ţ��ţ�
			info.setSerialNo(pi.getFormNo());
			//���մ���
			info.setFormNum(DataFormat.getChineseNumberString(pi.getFormNum()));
			//�����
			info.setDebitName(pi.getBorrowClientName());
			//������
			info.setAssureName(pi.getAssureName());
			//���ݺ�ͬid���Һ�ͬǩ������
			ContractInfo contractInfo = new ContractInfo();
			ContractDao contractDao = new ContractDao();
			contractInfo = contractDao.findByID(pi.getContractID());
			//ǩ����ͬʱ��
			if (contractInfo != null)
			{
				if (contractInfo.getLoanStart() != null)
				{
					strYear = DataFormat.getYearString(contractInfo.getLoanStart());
					strMonth = Long.valueOf(DataFormat.getMonthString(contractInfo.getLoanStart())).longValue() + 1 + "";
					strDay = DataFormat.getDayString(contractInfo.getLoanStart());
					strTemp = strYear + "&nbsp;��&nbsp;" + strMonth + "&nbsp;��&nbsp;" + strDay + "&nbsp;��";
					info.setDateStart(strTemp);
				}
			}
			//�����ͬ��
			info.setLoanContractCode(NameRef.getContractNoByID(pi.getContractID()));
			//������ͬ��
			info.setAssureContractCode(NameRef.getContractNoByID(pi.getAssureContractID()));
			if (pi.getAssureContractID() < 0)
			{
				info.setAssureContractCode("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			}
			//�ſ�֪ͨ����
			info.setLetOutRequisition(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			//�ſ�֪ͨ��������
			Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO();
			SubAccountLoanInfo subAccountLoanInfo = new SubAccountLoanInfo();
			subAccountLoanInfo.setLoanNoteID(pi.getLoanNoteID());
			subAccountLoanInfo = sett_SubAccountDAO.querySubInfo(subAccountLoanInfo);
			if (subAccountLoanInfo != null)
			{
				info.setLoanAmount(DataFormat.formatDisabledAmount(DataFormat.formatDouble(subAccountLoanInfo.getOpenAmount())));
				pi.setLoanBalance(subAccountLoanInfo.getBalance());
			}
			//�������--ȡ��ͬ�д���Ľ�������
			if (contractInfo != null)
			{
				if (contractInfo.getLoanEnd() != null)
				{
					strYear = DataFormat.getYearString(contractInfo.getLoanEnd());
					strMonth = Long.valueOf(DataFormat.getMonthString(contractInfo.getLoanEnd())).longValue() + 1 + "";
					strDay = DataFormat.getDayString(contractInfo.getLoanEnd());
					strTemp = strYear + "&nbsp;��&nbsp;" + strMonth + "&nbsp;��&nbsp;" + strDay + "&nbsp;��";
					info.setDateEnd(strTemp);
				}
			}
			//��������--ȡ��Ϣ��
			if (pi.getClearInterestDate() != null)
			{
				strYear = DataFormat.getYearString(pi.getClearInterestDate());
				strMonth = Long.valueOf(DataFormat.getMonthString(pi.getClearInterestDate())).longValue() + 1 + "";
				strDay = DataFormat.getDayString(pi.getClearInterestDate());
				strTemp = strYear + "&nbsp;��&nbsp;" + strMonth + "&nbsp;��&nbsp;" + strDay + "&nbsp;��";
				info.setDeadline(strTemp);
			}
			//δ��������
			if (pi.getLoanBalance() > 0) //��ֵ�ڱ������н��й�����
			{
				info.setOwnAmount(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getLoanBalance())));
			}
			else
			{
				info.setOwnAmount("0.00");
			}
			//δ��������Ϣ(������Ϣ)
			info.setOwnInterest(
				DataFormat.formatDisabledAmount(
					DataFormat.formatDouble(DataFormat.formatDouble(pi.getInterest()) + DataFormat.formatDouble(pi.getOverDueInterest()) + DataFormat.formatDouble(pi.getCompoundInterest()))));
			//δ��������
			info.setOwnComissionFee(DataFormat.formatDisabledAmount(pi.getCommission()));
			if (pi.getCommission() == 0.0)
			{
				info.setOwnComissionFee("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			}
			//�������ޣ��ֹ���д��
			//��������
			strTemp = DataFormat.formatDate(pi.getExecuteDate());
			if (strTemp.length() < 10)
				strTemp = "0000000000";
			strYear = strTemp.substring(0, 4);
			strMonth = strTemp.substring(5, 7);
			strDay = strTemp.substring(8, 10);
			info.setYearSeal(strYear);
			//�������ڣ��£�
			info.setMonthSeal(strMonth);
			//�������ڣ��գ�		    
			info.setDaySeal(strDay);
			IPrintTemplate.showOverLoanDunNotice(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			//��ִ
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
	 * ��ӡί�д����֪ͨ��
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
			//��ţ��꣩
			info.setFormYear(pi.getFormYear());
			//��ţ��ţ�
			info.setFormNo(pi.getFormNo());
			//����� 
			info.setBorrowAmountClientName(pi.getBorrowClientName());
			//���ݺ�ͬid���Һ�ͬǩ������
			ContractInfo contractInfo = new ContractInfo();
			ContractDao contractDao = new ContractDao();
			contractInfo = contractDao.findByID(pi.getContractID());
			//ǩ����ͬ��
			if (contractInfo != null)
			{
				strTemp = DataFormat.formatDate(contractInfo.getLoanStart());
				if (strTemp.length() < 10)
					strTemp = "0000000000";
				strYear = strTemp.substring(0, 4);
				strMonth = strTemp.substring(5, 7);
				strDay = strTemp.substring(8, 10);
				info.setContractSignYear(strYear);
				//��
				info.setContractSignMonth(strMonth);
				//��
				info.setContractSignDay(strDay);
			}
			//��ͬ��
			info.setContractNo(NameRef.getContractNoByID(pi.getContractID()));
			//�ſ�֪ͨ��
			info.setLoanNoteNo(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			//�����
			Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO();
			SubAccountLoanInfo subAccountLoanInfo = new SubAccountLoanInfo();
			subAccountLoanInfo.setLoanNoteID(pi.getLoanNoteID());
			subAccountLoanInfo = sett_SubAccountDAO.querySubInfo(subAccountLoanInfo);
			if (subAccountLoanInfo != null)
			{
				info.setBorrowAmount(DataFormat.formatDisabledAmount(DataFormat.formatDouble(subAccountLoanInfo.getOpenAmount())));
				System.out.println("����" + info.getBorrowAmount());
				pi.setLoanBalance(subAccountLoanInfo.getBalance());
				System.out.println("������" + pi.getLoanBalance());
			}
			//���������
			String LoanEndYear = "";
			//���������
			String LoanEndMonth = "";
			//���������
			String LoanEndDay = "";
			strTemp = DataFormat.formatDate(pi.getEndDate());
			if (strTemp.length() < 10)
				strTemp = "0000000000";
			strYear = strTemp.substring(0, 4);
			strMonth = strTemp.substring(5, 7);
			strDay = strTemp.substring(8, 10);
			info.setLoanEndYear(strYear);
			//��
			info.setLoanEndMonth(strMonth);
			//��
			info.setLoanEndDay(strDay);
			//�������
			if (pi.getLoanBalance() > 0) //��ֵ��ǰ�����ù�
			{
				info.setLoanAmount(DataFormat.formatDisabledAmount(pi.getLoanBalance()));
			}
			else
			{
				info.setLoanAmount("0.00");
			}
			//��Ϣ���
			info.setInterestAmount(
				DataFormat.formatDisabledAmount(
					DataFormat.formatDouble((DataFormat.formatDouble(pi.getInterest()) + DataFormat.formatDouble(pi.getOverDueInterest()) + DataFormat.formatDouble(pi.getCompoundInterest())))));
			//������
			info.setCommission(DataFormat.formatDisabledAmount(pi.getCommission()));
			//��Ϣ�����Ѻϼ�
			String TotalAmountInterest = "";
			info.setTotalAmountInterest(
				DataFormat.formatDisabledAmount(
					DataFormat.formatDouble(pi.getLoanBalance())
						+ DataFormat.formatDouble(pi.getCompoundInterest())
						+ DataFormat.formatDouble(pi.getCommission())
						+ DataFormat.formatDouble(pi.getOverDueInterest())
						+ DataFormat.formatDouble(pi.getInterest())));
			//���·��(��д?)
			//String RemitAddress = "";
			//�տλ(��д?)
			//String ReceiveUnit = "";
			//��������(��д?)
			//String ReceiveBank = "";
			//�˺�
			//String AccountNo = "";
			//ǩ����
			strTemp = DataFormat.formatDate(pi.getExecuteDate());
			if (strTemp.length() < 10)
				strTemp = "0000000000";
			strYear = strTemp.substring(0, 4);
			strMonth = strTemp.substring(5, 7);
			strDay = strTemp.substring(8, 10);
			info.setSealYear(strYear);
			//�������ڣ��£�
			info.setSealMonth(strMonth);
			//�������ڣ��գ�		    
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
		 * ��ӡ�����֪ͨ��
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
			//��ţ��꣩
			info.setFormYear(pi.getFormYear());
			//��ţ��ţ�
			info.setFormNo(pi.getFormNo());
			//����� 
			info.setBorrowAmountClientName(pi.getBorrowClientName());
			//���ݺ�ͬid���Һ�ͬǩ������
			ContractInfo contractInfo = new ContractInfo();
			ContractDao contractDao = new ContractDao();
			contractInfo = contractDao.findByID(pi.getContractID());
			//ǩ����ͬ��
			if (contractInfo != null)
			{
				strTemp = DataFormat.formatDate(contractInfo.getLoanStart());
				if (strTemp.length() < 10)
					strTemp = "0000000000";
				strYear = strTemp.substring(0, 4);
				strMonth = strTemp.substring(5, 7);
				strDay = strTemp.substring(8, 10);
				info.setContractSignYear(strYear);
				//��
				info.setContractSignMonth(strMonth);
				//��
				info.setContractSignDay(strDay);
			}
			//��ͬ��
			info.setContractNo(NameRef.getContractNoByID(pi.getContractID()));
			//�ſ�֪ͨ��
			info.setLoanNoteNo(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			//�����
			Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO();
			SubAccountLoanInfo subAccountLoanInfo = new SubAccountLoanInfo();
			subAccountLoanInfo.setLoanNoteID(pi.getLoanNoteID());
			subAccountLoanInfo = sett_SubAccountDAO.querySubInfo(subAccountLoanInfo);
			if (subAccountLoanInfo != null)
			{
				info.setBorrowAmount(DataFormat.formatDisabledAmount(DataFormat.formatDouble(subAccountLoanInfo.getOpenAmount())));
				pi.setLoanBalance(subAccountLoanInfo.getBalance());
			}
			//���������
			String LoanEndYear = "";
			//���������
			String LoanEndMonth = "";
			//���������
			String LoanEndDay = "";
			strTemp = DataFormat.formatDate(pi.getEndDate());
			if (strTemp.length() < 10)
				strTemp = "0000000000";
			strYear = strTemp.substring(0, 4);
			strMonth = strTemp.substring(5, 7);
			strDay = strTemp.substring(8, 10);
			info.setLoanEndYear(strYear);
			//��
			info.setLoanEndMonth(strMonth);
			//��
			info.setLoanEndDay(strDay);
			//�������
			if (pi.getLoanBalance() > 0)
			{
				info.setLoanAmount(DataFormat.formatDisabledAmount(pi.getLoanBalance()));
			}
			else
			{
				info.setLoanAmount("0.00");
			}
			//��Ϣ���
			info.setInterestAmount(
				DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getInterest()) + DataFormat.formatDouble(pi.getOverDueInterest()) + DataFormat.formatDouble(pi.getCompoundInterest())));
			//��Ϣ�ϼ�
			String TotalAmountInterest = "";
			info.setTotalAmountInterest(
				DataFormat.formatDisabledAmount(
					DataFormat.formatDouble(pi.getLoanBalance())
						+ DataFormat.formatDouble(pi.getInterest())
						+ DataFormat.formatDouble(pi.getOverDueInterest())
						+ DataFormat.formatDouble(pi.getCompoundInterest())));
			//���·��(��д?)
			//String RemitAddress = "";
			//�տλ(��д?)
			//String ReceiveUnit = "";
			//��������(��д?)
			//String ReceiveBank = "";
			//�˺�
			//String AccountNo = "";
			//ǩ����
			strTemp = DataFormat.formatDate(pi.getExecuteDate());
			if (strTemp.length() < 10)
				strTemp = "0000000000";
			strYear = strTemp.substring(0, 4);
			strMonth = strTemp.substring(5, 7);
			strDay = strTemp.substring(8, 10);
			info.setSealYear(strYear);
			//�������ڣ��£�
			info.setSealMonth(strMonth);
			//�������ڣ��գ�		    
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
		*  ��Ϣ֪ͨ��(֧ȡ)��ӡ
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
			//��λ����
			if (pi.getBorrowClientID() > 0)
			{
				info.setClientName(NameRef.getClientNameByID(pi.getBorrowClientID()));
			}
			//ί�е�λ����
			ContractDao contractDao = new ContractDao();
			ContractInfo contractInfo = null;
			contractInfo = contractDao.findByID(pi.getContractID());
			info.setConsignClientName(NameRef.getClientNameByID(contractInfo.getClientID()));
			//�˺�����
			info.setAccountType(SETTConstant.AccountType.getName(pi.getAccountTypeID()));
			//���ױ��
			info.setTransNo(pi.getTransNo());
			//�˺�
			info.setAccountNo(NameRef.getAccountNoByID(pi.getConsignAccountID()));
			//������Ϣ
			//����ֶ���Ϣ
			SubsectionInterest dao = new SubsectionInterest();
			SubsectionDateInfo PrintSubsectionInfo = new SubsectionDateInfo();
			PrintSubsectionInfo = dao.getSubsectionInterest(pi.getConsignAccountID(), -1, pi.getLoanNoteID(), pi.getLatestInterestClearDate(), DataFormat.getPreviousDate(pi.getInterestClearDate()));
			info.setNormalInterestDateStart(PrintSubsectionInfo.getPrintStartDate()); //������Ϣ��ʼ����
			info.setNormalInterestDateEnd(PrintSubsectionInfo.getPrintEndDate());
			info.setNormalInterestAmount(PrintSubsectionInfo.getPrintBalance()); //������Ϣ����
			info.setNormalInterestRate(PrintSubsectionInfo.getPrintInterestRate()); //������Ϣ��
			info.setNormalInterest(PrintSubsectionInfo.getPrintInterest()); //������Ϣ
			info.setNormalInterestDay(PrintSubsectionInfo.getPrintDays());
			//����ֶ���ϢEnd
			/*
			 LoanPayFormDetailInfo lpfdinfo = new LoanPayFormDetailInfo();
			lpfdinfo.setLoadNoteID(pi.getLoanNoteID());
			Sett_TransGrantLoanDAO stdao = new Sett_TransGrantLoanDAO();
			lpfdinfo = stdao.findPayFormDetailByCondition(lpfdinfo);
			 //������Ϣ��Ϣ����
			info.setNormalInterestDateStart(DataFormat.formatDate(pi.getLatestInterestClearDate()));
			//������Ϣ��Ϣ����			
			info.setNormalInterestDateEnd(DataFormat.formatDate(DataFormat.getPreviousDate(pi.getInterestClearDate())));
			//������Ϣ����
			if (pi.getLatestInterestClearDate() != null && pi.getInterestClearDate() != null)
			{
				info.setNormalInterestDay((getIntervalDays(pi.getLatestInterestClearDate(), pi.getInterestClearDate(), 1)) + "");
			}
			//������Ϣ����
			info.setNormalInterestAmount(DataFormat.formatDisabledAmount(pi.getLoanBalance()));
			if (pi.getAmount() == 0.0)
			{
				info.setNormalInterestAmount("0.00");
			}
			//������Ϣ����
			info.setNormalInterestRate(DataFormat.formatRate(lpfdinfo.getInterestRate() + ""));
			//������Ϣ
			info.setNormalInterest(DataFormat.formatDisabledAmount(pi.getRealInterest()));
			if (pi.getRealInterest() == 0.0)
			{
				info.setNormalInterest("0.0");
			}*/
			//����
			if (pi.getRealCompoundInterest() > 0)
			{
				//������Ϣ��Ϣ����
				if (pi.getCompoundInterestStart() != null)
				{
					info.setCompoundInterestDateStart(DataFormat.formatDate(pi.getCompoundInterestStart()));
				}
				//������Ϣ��Ϣ����
				info.setCompoundInterestDateEnd(DataFormat.formatDate(DataFormat.getPreviousDate(pi.getInterestClearDate())));
				//������Ϣ����
				if (pi.getCompoundInterestStart() != null)
				{
					info.setCompoundInterestDay((getIntervalDays(pi.getCompoundInterestStart(), pi.getInterestClearDate(), 1)) + "");
				}
				info.setCompoundInterestAmount("&nbsp;");
				//��������
				info.setCompoundInterestRate(DataFormat.formatRate(pi.getCompoundRate() + ""));
				//������Ϣ	
				info.setCompoundInterest(DataFormat.formatDisabledAmount(pi.getRealCompoundInterest()));
				if (pi.getRealCompoundInterest() == 0.0)
				{
					info.setCompoundInterest("0.0");
				}
			}
			//���ڷ�Ϣ
			if (pi.getRealOverDueInterest() > 0)
			{
				//���ڷ�Ϣ��Ϣ��Ϣ����
				if (pi.getOverDueStart() != null)
				{
					info.setOverInterestDateStart(DataFormat.formatDate(pi.getOverDueStart()));
				}
				//���ڷ�Ϣ��Ϣ��Ϣ����
				info.setOverInterestDateEnd(DataFormat.formatDate(DataFormat.getPreviousDate(pi.getInterestClearDate())));
				//���ڷ�Ϣ��Ϣ����
				if (pi.getOverDueStart() != null)
				{
					info.setOverInterestDay((getIntervalDays(pi.getOverDueStart(), pi.getInterestClearDate(), 1)) + "");
				}
				//���ڷ�Ϣ����
				info.setOverInterestAmount(DataFormat.formatDisabledAmount(pi.getOverDueAmount()));
				if (pi.getOverDueAmount() == 0.00)
				{
					info.setOverInterestAmount("0.00");
				}
				//���ڷ�Ϣ����
				info.setOverInterestRate(DataFormat.formatRate(pi.getOverDueRate() + ""));
				//���ڷ�Ϣ��Ϣ
				info.setOverInterest(DataFormat.formatDisabledAmount(pi.getRealOverDueInterest()));
				if (pi.getRealOverDueInterest() == 0.0)
				{
					info.setOverInterest("0.0");
				}
			}
			//������
			if (pi.getRealCommission() > 0)
			{
				//��������Ϣ��Ϣ����
				info.setCommissionFeeDateStart(DataFormat.formatDate(pi.getCommissionStart()));
				//��������Ϣ��Ϣ����
				info.setCommissionFeeDateEnd(DataFormat.formatDate(DataFormat.getPreviousDate(pi.getInterestClearDate())));
				//��������Ϣ����
				info.setCommissionFeeDay((getIntervalDays(pi.getCommissionStart(), pi.getInterestClearDate(), 1)) + "");
				//��������Ϣ����		
				info.setCommissionFeeAmount(DataFormat.formatDisabledAmount(pi.getLoanBalance()));
				if (pi.getAmount() == 0.00)
				{
					info.setCommissionFeeAmount("0.00");
				}
				//����������				
				info.setCommissionFeeRate(DataFormat.formatRate(pi.getCommissionRate() + ""));
				//��������Ϣ
				info.setCommissionFee(DataFormat.formatDisabledAmount(pi.getRealCommission()));
				if (pi.getRealCommission() == 0.0)
				{
					info.setCommissionFee("0.0");
				}
			}
			if (pi.getRealSuretyFee() > 0)
			{
				//������
				//��������Ϣ��Ϣ����
				info.setAssureFeeDateStart(DataFormat.formatDate(pi.getSuretyFeeStart()));
				//��������Ϣ��Ϣ����
				info.setAssureFeeDateEnd(DataFormat.formatDate(DataFormat.getPreviousDate(pi.getInterestClearDate())));
				//��������Ϣ����				
				info.setAssureFeeDay((getIntervalDays(pi.getSuretyFeeStart(), pi.getInterestClearDate(), 1)) + "");
				//��������Ϣ����		
				info.setAssureFeeAmount(DataFormat.formatDisabledAmount(pi.getAmount()));
				if (pi.getAmount() == 0.0)
				{
					info.setAssureFee("0.00");
				}
				//����������
				info.setAssureFeeRate(DataFormat.formatRate(pi.getSuretyFeeRate() + ""));
				//��������Ϣ
				info.setAssureFee(DataFormat.formatDisabledAmount(pi.getRealSuretyFee()));
				if (pi.getRealSuretyFee() == 0.0)
				{
					info.setAssureFee("0.0");
				}
			}
			//��Ϣ�ܶ�
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
			//��Ϣ�˺�
			info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getPayInterestAccountID()));
			//��Ӧ���˺�
			info.setCurrentAccountNo(NameRef.getAccountNoByID(pi.getConsignAccountID()));
			//��Ӧ�ĺ�ͬ��
			info.setContractNo(NameRef.getContractNoByID(pi.getContractID()));
			//��Ӧ��ݺ�
			info.setLoanBillNo(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			//��Ӧ�浥��
			info.setDepositBillNo(pi.getFixedDepositNo());
			//ת����
			info.setTransAccountDate(DataFormat.formatDate(pi.getExecuteDate()));
			//¼����
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			if (pi.getInputUserID() < 0)
			{
				info.setInputUserName("����");
			}
			//������
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			if (pi.getCheckUserID() < 0)
			{
				info.setCheckUserName("����");
			}
			System.out.println("��������:" + pi.getTransTypeID());
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
		 * ���Ŵ����ƾ֤
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
			//��ͬ����
			LoanPayFormDetailInfo lpfdinfo = new LoanPayFormDetailInfo();
			lpfdinfo.setLoadNoteID(pi.getLoanNoteID());
			Sett_TransGrantLoanDAO stdao = new Sett_TransGrantLoanDAO();
			lpfdinfo = stdao.findPayFormDetailByCondition(lpfdinfo);
			//���������
			info.setAgentFeeRate(DataFormat.formatRate(String.valueOf(lpfdinfo.getCommissionRate())));
			//ί�е�λ
			info.setConsignUnit(lpfdinfo.getClientName());
			info.setContractRate(DataFormat.formatDisabledAmount(lpfdinfo.getInterestRate()));
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			info.setLoanInterval(DataFormat.getChineseDateString(lpfdinfo.getStart()) + " �� " + DataFormat.getChineseDateString(lpfdinfo.getEnd()));
			info.setLoanType(LOANConstant.LoanType.getName(lpfdinfo.getLoanType()));
			info.setLoanUnit(NameRef.getClientNameByID(pi.getReceiveClientID()));
			info.setNote(pi.getAbstract());
			//�����˺�
			info.setLoanAccountNo(NameRef.getAccountNoByID(pi.getLoanAccountID()));
			//��λ������Ӫ�����˻�
			info.setLoanUnit(NameRef.getAccountNameByID(pi.getLoanAccountID()));
			if (pi.getReceiveAccountID() > 0)
			{
				//�տ����˺�
				info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
				//�տ��˿�����������
				info.setOpenBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			else if (pi.getReceiveBankID() > 0)
			{
				//�տ����˺�
				info.setAccountNo(pi.getExtAccountNo());
				//�տ��˿�����������
				info.setOpenBankName(pi.getExtRemitInBank());
			}
			info.setTransNo(pi.getTransNo());
			//���÷��Ŵ�����ϸ���������ƣ��д��������д����
			double dSumRate = 0.0;
			double dSumAmount = 0.0;
			Vector vctDetail = pi.getDetail();
			Vector vctTemp = new Vector();
			Vector vctWithOutHead = new Vector();
			BankLoanDrawInfo bankLoanDrawInfo = null;
			SynLoanGrantDetailInfo synLoanGrantDetailInfo = null;
			if (vctDetail != null)
			{
				System.out.println("===���Ŵ�����ϸ not null===");
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
			//�ϼƳд�����
			info.setSumAssumeLoanRate(DataFormat.formatRate(String.valueOf(dSumRate)));
			//�ϼƳд����
			info.setSunAssumeLoanAmount(DataFormat.formatDisabledAmount(DataFormat.formatDouble(dSumAmount)));
			//ISynLoanPrintTemplate.showSynLoanGrantLoan1(info, out);
			//out.println("<br clear=all style='page-break-before:always'>");
			//ISynLoanPrintTemplate.showSynLoanGrantLoan2(info, out);
			//out.println("<br clear=all style='page-break-before:always'>");
			ISynLoanPrintTemplate.showSynLoanGrantLoan3(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			//ISynLoanPrintTemplate.showSynLoanGrantLoan4(info, out);
			//out.println("<br clear=all style='page-break-before:always'>");
			//����������ƾ֤
			info.setSynLoanGrantDetail(vctWithOutHead); //���⴦��
			vctTemp = info.getSynLoanGrantDetail();
			synLoanGrantDetailInfo = null;
			Vector vctTemp2 = null;
			if (vctTemp != null)
			{
				for (int i = 0; i < vctTemp.size(); i++)
				{
					vctTemp2 = new Vector();
					synLoanGrantDetailInfo = (SynLoanGrantDetailInfo) vctTemp.elementAt(i);
					//�ϼƳд�����
					info.setSumAssumeLoanRate(synLoanGrantDetailInfo.getLoanRate());
					//�ϼƳд����
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
		 * ���Ŵ�����ջ�ƾ֤
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
					+ "����������Ϣ��"
					+ PrintVoucher.getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ strRealInterest
					+ "�����ո�����"
					+ PrintVoucher.getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ strRealCompoundInterest
					+ "�����շ�Ϣ��"
					+ PrintVoucher.getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ strRealOverDueInterest);
			info.setBillCode(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			info.setChineseAmount(ChineseCurrency.showChinese(DataFormat.formatAmount(pi.getAmount()), pi.getCurrencyID()));
			info.setContractCode(NameRef.getContractNoByID(pi.getContractID()));
			//��ͬ����
			LoanPayFormDetailInfo lpfdinfo = new LoanPayFormDetailInfo();
			lpfdinfo.setLoadNoteID(pi.getLoanNoteID());
			Sett_TransGrantLoanDAO stdao = new Sett_TransGrantLoanDAO();
			lpfdinfo = stdao.findPayFormDetailByCondition(lpfdinfo);
			info.setContractRate(DataFormat.formatDisabledAmount(lpfdinfo.getInterestRate()));
			//ί�е�λ
			info.setConsignUnit(lpfdinfo.getClientName());
			//���
			if (pi.getCurrentBalance() > 0)
			{
				info.setBalance(PrintVoucher.getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getCurrentBalance()));
			}
			else
			{
				info.setBalance(PrintVoucher.getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + "0.00");
			}
			//�ۼƻ���
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
			info.setLoanInterval(DataFormat.getChineseDateString(lpfdinfo.getStart()) + " �� " + DataFormat.getChineseDateString(lpfdinfo.getEnd()));
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
			System.out.println("�ſ�֪ͨ��ID:" + pi.getLoanNoteID());
			//���ñ���
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
						//�տλ����
						repayDetailInfo.setReceiveAmountUnitName(DataFormat.formatString(bankLoanDrawInfo.getBankName()));
						//�д�����
						repayDetailInfo.setLoanRate(DataFormat.formatString(DataFormat.formatAmount(bankLoanDrawInfo.getRate(), 0) + ""));
						dSumLoanRate = DataFormat.formatDouble(dSumLoanRate + DataFormat.formatDouble(bankLoanDrawInfo.getRate()));
						YTLoanAttendBankInfo ytLoanAttendBankInfo = null;
						YTLoanAttendBankDelegation delegation = new YTLoanAttendBankDelegation();
						ytLoanAttendBankInfo = delegation.findById(bankLoanDrawInfo.getBankID());
						if (ytLoanAttendBankInfo != null)
						{
							//��������
							repayDetailInfo.setOpenBank(DataFormat.formatString(ytLoanAttendBankInfo.getBank()));
							//�տ���
							repayDetailInfo.setBankAccountNo(DataFormat.formatString(ytLoanAttendBankInfo.getBankAccountNo()));
						}
						else
						{
							//��������
							repayDetailInfo.setOpenBank("&nbsp;");
							//�����˺�
							repayDetailInfo.setBankAccountNo("&nbsp;");
						}
						//�տ���
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
			//����������ƾ֤
			info.setSynLoanRepayDetail(vctWithOutHead); //���⴦��
			vctDetail = info.getSynLoanRepayDetail();
			repayDetailInfo = null;
			Vector vctTemp = null;
			if (vctDetail != null)
			{
				for (int i = 0; i < vctDetail.size(); i++)
				{
					vctTemp = new Vector();
					repayDetailInfo = (SynLoanRepayDetailInfo) vctDetail.elementAt(i);
					//�ϼƳд�����
					info.setSumLoanRate(DataFormat.formatRate(repayDetailInfo.getLoanRate()));
					//�ϼƳд����
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
		 * ���Ŵ�����Ϣ�ջ�ƾ֤
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
			//��λ����
			if (pi.getPayClientID() > 0)
			{
				info.setClientName(NameRef.getClientNameByID(pi.getPayClientID()));
			}
			else
			{
				info.setClientName(pi.getBorrowClientName());
			}
			//ί�е�λ����
			info.setConsignClientName(NameRef.getClientNameByID(pi.getConsignClientID()));
			//�˺�����
			info.setAccountType(SETTConstant.AccountType.getName(pi.getAccountTypeID()));
			//���ױ��
			info.setTransNo(pi.getTransNo());
			//�˺�&�˻�����
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
			System.out.println("ʵ��֧����Ϣ��" + pi.getRealInterest());
			System.out.println("�����˺ţ�" + lPayAccountID);
			System.out.println("�ſ�֪ͨ���ţ�" + pi.getLoanNoteID());
			System.out.println("��Ϣ��ֹ����:" + pi.getNormalInterestStart() + "---" + pi.getNormalInterestEnd());
			if (pi.getRealInterest() > 0)
			{
				/*//����ֶ���Ϣ
				//������Ϣ
				//����ֶ���Ϣ
				SubsectionInterest dao = new SubsectionInterest();
				SubsectionDateInfo PrintSubsectionInfo = new SubsectionDateInfo();
				PrintSubsectionInfo =
					dao.getSubsectionInterest(lPayAccountID, -1, pi.getLoanNoteID(), pi.getLatestInterestClearDate(), DataFormat.getPreviousDate(pi.getInterestClearDate()));
				info.setNormalInterestDateStart(PrintSubsectionInfo.getPrintStartDate()); //������Ϣ��ʼ����
				info.setNormalInterestDateEnd(PrintSubsectionInfo.getPrintEndDate());
				info.setNormalInterestAmount(PrintSubsectionInfo.getPrintBalance()); //������Ϣ����
				info.setNormalInterestRate(PrintSubsectionInfo.getPrintInterestRate()); //������Ϣ��
				info.setNormalInterest(PrintSubsectionInfo.getPrintInterest()); //������Ϣ
				info.setNormalInterestDay(PrintSubsectionInfo.getPrintDays());
				*/
				info.setNormalInterestDateStart(DataFormat.getDateString(pi.getLatestInterestClearDate())); //������Ϣ��ʼ����
				info.setNormalInterestDateEnd(DataFormat.getDateString(DataFormat.getPreviousDate(pi.getInterestClearDate())));
				info.setNormalInterestAmount(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getAmount()))); //������Ϣ����
				info.setNormalInterestRate(DataFormat.formatRate(String.valueOf(DataFormat.formatRate(pi.getCompoundRate())))); //������Ϣ��
				info.setNormalInterest(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getRealInterest()))); //������Ϣ
				info.setNormalInterestDay((PrintVoucher.getIntervalDays(pi.getLatestInterestClearDate(), pi.getInterestClearDate(), 1)) + "");
				//����ֶ���ϢEnd
			}
			//����
			if (pi.getRealCompoundInterest() > 0)
			{
				//������Ϣ��Ϣ����
				if (pi.getCompoundInterestStart() != null)
				{
					info.setCompoundInterestDateStart(DataFormat.formatDate(pi.getCompoundInterestStart()));
				}
				//������Ϣ��Ϣ����
				if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE || pi.getTransTypeID() == SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
				{
					info.setCompoundInterestDateEnd(DataFormat.formatDate(DataFormat.getPreviousDate(pi.getInterestClearDate())));
				}
				else
				{
					info.setCompoundInterestDateEnd(DataFormat.formatDate(pi.getInterestClearDate()));
				}
				//������Ϣ����
				if (pi.getCompoundInterestStart() != null)
				{
					info.setCompoundInterestDay((PrintVoucher.getIntervalDays(pi.getCompoundInterestStart(), pi.getInterestStartDate(), 1)) + "");
				}
				info.setCompoundInterestAmount("&nbsp;");
				//��������
				info.setCompoundInterestRate(DataFormat.formatRate(pi.getCompoundRate() + ""));
				//������Ϣ	
				info.setCompoundInterest(DataFormat.formatDisabledAmount(pi.getRealCompoundInterest()));
				if (pi.getRealCompoundInterest() == 0.0)
				{
					info.setCompoundInterest("0.00");
				}
			}
			//���ڷ�Ϣ
			if (pi.getRealOverDueInterest() > 0)
			{
				//���ڷ�Ϣ��Ϣ��Ϣ����
				if (pi.getOverDueStart() != null)
				{
					info.setOverInterestDateStart(DataFormat.formatDate(pi.getOverDueStart()));
				}
				//���ڷ�Ϣ��Ϣ��Ϣ����
				if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
				{
					info.setOverInterestDateEnd(DataFormat.formatDate(DataFormat.getPreviousDate(pi.getInterestClearDate())));
				}
				else
				{
					info.setOverInterestDateEnd(DataFormat.formatDate(pi.getInterestClearDate()));
				}
				//���ڷ�Ϣ��Ϣ����
				if (pi.getOverDueStart() != null)
				{
					info.setOverInterestDay((PrintVoucher.getIntervalDays(pi.getOverDueStart(), pi.getInterestClearDate(), 1)) + "");
				}
				//���ڷ�Ϣ����
				info.setOverInterestAmount(DataFormat.formatDisabledAmount(pi.getOverDueAmount()));
				if (pi.getOverDueAmount() == 0.00)
				{
					info.setOverInterestAmount("0.00");
				}
				//���ڷ�Ϣ����
				info.setOverInterestRate(DataFormat.formatRate(pi.getOverDueRate() + ""));
				//���ڷ�Ϣ��Ϣ
				info.setOverInterest(DataFormat.formatDisabledAmount(pi.getRealOverDueInterest()));
				if (pi.getRealOverDueInterest() == 0.0)
				{
					info.setOverInterest("0.00");
				}
			}
			//������
			if (pi.getRealCommission() > 0)
			{
				//��������Ϣ��Ϣ����
				info.setCommissionFeeDateStart(DataFormat.formatDate(pi.getCommissionStart()));
				//��������Ϣ��Ϣ����
				if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
				{
					info.setCommissionFeeDateEnd(DataFormat.formatDate(DataFormat.getPreviousDate(pi.getInterestClearDate())));
				}
				else
				{
					info.setCommissionFeeDateEnd(DataFormat.formatDate(pi.getInterestClearDate()));
				}
				//��������Ϣ����
				info.setCommissionFeeDay((PrintVoucher.getIntervalDays(pi.getCommissionStart(), pi.getInterestClearDate(), 1)) + "");
				//��������Ϣ����		
				info.setCommissionFeeAmount(DataFormat.formatDisabledAmount(pi.getAmount()));
				if (pi.getAmount() == 0.00)
				{
					info.setCommissionFeeAmount("0.00");
				}
				//����������				
				System.out.print("========���������ʣ�" + pi.getCommissionRate());
				info.setCommissionFeeRate(DataFormat.formatRate(pi.getCommissionRate() + ""));
				//��������Ϣ
				info.setCommissionFee(DataFormat.formatDisabledAmount(pi.getRealCommission()));
				if (pi.getRealCommission() == 0.0)
				{
					info.setCommissionFee("0.00");
				}
			}
			if (pi.getRealSuretyFee() > 0)
			{
				//������
				//��������Ϣ��Ϣ����
				info.setAssureFeeDateStart(DataFormat.formatDate(pi.getSuretyFeeStart()));
				//��������Ϣ��Ϣ����
				if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
				{
					info.setAssureFeeDateEnd(DataFormat.formatDate(DataFormat.getPreviousDate(pi.getInterestClearDate())));
				}
				else
				{
					info.setAssureFeeDateEnd(DataFormat.formatDate(pi.getInterestClearDate()));
				}
				//��������Ϣ����				
				info.setAssureFeeDay((PrintVoucher.getIntervalDays(pi.getSuretyFeeStart(), pi.getInterestClearDate(), 1)) + "");
				//��������Ϣ����		
				info.setAssureFeeAmount(DataFormat.formatDisabledAmount(pi.getAmount()));
				if (pi.getAmount() == 0.0)
				{
					info.setAssureFee("0.00");
				}
				//����������
				info.setAssureFeeRate(DataFormat.formatRate(pi.getSuretyFeeRate() + ""));
				//��������Ϣ
				info.setAssureFee(DataFormat.formatDisabledAmount(pi.getRealSuretyFee()));
				if (pi.getRealSuretyFee() == 0.0)
				{
					info.setAssureFee("0.00");
				}
			}
			//��Ϣ�ܶ�
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
			//��Ϣ�˺�
			info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getPayInterestAccountID()));
			//��Ӧ���˺�
			info.setCurrentAccountNo(NameRef.getAccountNoByID(lPayAccountID));
			//��Ӧ�ĺ�ͬ��
			info.setContractNo(NameRef.getContractNoByID(pi.getContractID()));
			//��Ӧ��ݺ�
			info.setLoanBillNo(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			//��Ӧ�浥��
			info.setDepositBillNo(pi.getFixedDepositNo());
			//ת����
			info.setTransAccountDate(DataFormat.formatDate(pi.getInterestStartDate()));
			//¼����
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			if (pi.getInputUserID() < 0)
			{
				info.setInputUserName("����");
			}
			//������
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			if (pi.getCheckUserID() < 0)
			{
				if (pi.getStatusID() == SETTConstant.TransactionStatus.CHECK)
				{
					info.setCheckUserName("����");
				}
				else
				{
					info.setCheckUserName("&nbsp;&nbsp;&nbsp;&nbsp");
				}
			}
			//���ô�����Ϣ��ϸ����Ϣ��λ���ƣ��������У������˺ţ�Ӧ����Ϣ��Ԫ��
			Vector vctDetail = pi.getDetail();
			Vector vctTemp = new Vector();
			Vector vctWithOutHead = new Vector();
			SyndicationLoanInterestInfo interestInfo = null;
			SynLoanRepayDetailInfo synLoanRepayDetailInfo = null;
			double dSumInterest = 0.0;
			double dTotalSumInterest = 0.0;
			if (vctDetail != null)
			{
				System.out.println("===������Ϣ��ϸ not null===");
				for (int i = 0; i < vctDetail.size(); i++)
				{
					synLoanRepayDetailInfo = new SynLoanRepayDetailInfo();
					interestInfo = (SyndicationLoanInterestInfo) vctDetail.elementAt(i);
					//��Ϣ��λ����
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
						//��������
						synLoanRepayDetailInfo.setOpenBank("&nbsp;");
						//�����˺�
						synLoanRepayDetailInfo.setBankAccountNo("&nbsp;");
					}
					//Ӧ����Ϣ��Ԫ��
					dSumInterest =
						DataFormat.formatDouble(
							DataFormat.formatDouble(interestInfo.getInterest())
								+ DataFormat.formatDouble(interestInfo.getCompoundInterest())
								+ DataFormat.formatDouble(interestInfo.getForpeitInterest()));
					//�ϼ���Ϣ
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
			//����������ƾ֤
			info.setSynLoanRepayDetail(vctWithOutHead); //���⴦��
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
				strReturn = "һ";
				break;
			case 2 :
				strReturn = "��";
				break;
			case 3 :
				strReturn = "��";
				break;
			case 4 :
				strReturn = "��";
				break;
			case 5 :
				strReturn = "��";
				break;
			case 6 :
				strReturn = "��";
				break;
			case 7 :
				strReturn = "��";
				break;
			case 8 :
				strReturn = "��";
				break;
			case 9 :
				strReturn = "��";
				break;
			case 10 :
				strReturn = "ʮ";
				break;
			case 11 :
				strReturn = "ʮһ";
				break;
			case 12 :
				strReturn = "ʮ��";
				break;
			case 13 :
				strReturn = "ʮ��";
				break;
			case 14 :
				strReturn = "ʮ��";
				break;
			default :
				strReturn = "&nbsp;&nbsp;";
		}
		return strReturn;
	}

	//	���н��˵�
	//01	������	
	//02	������
	//03	������	
	//04	������ȫ��
	//05	�������˺�	
	//08	�����˿�������
	//09	�տ���ȫ��	
	//10	�տ����˺�
	//13	�տ��˿�������	
	//14	��д���
	//15	Сд���	
	//16	Ʊ������
	//17	Ʊ������	
	//19	��ע
	//0026	����	
	//0029	���
	//0027	����	
	//0031	����
	//����Ż�
	//  01	������
	//  02	������
	//  03	������
	//  04	�����ȫ��
	//  05	������˺�
	//  06	���ʡ��
	//  07	�������
	//  08	���������
	//  09	�տ���ȫ��
	//  10	�տ����˺�
	//  11	����ʡ��
	//  12	��������
	//  13	����������
	//  14	��д���
	//  15	Сд���
	//  18	�����;
	//  0026	����
	//  0029	���
	//  0027	����
	//  0031	����
	//
	//  01	������
	//  02	������
	//  03	������
	//  04	�����ȫ��
	//  05	������˺�
	//  06	�Ҹ�ʡ��
	//  07	�Ҹ�����
	//  08	�Ҹ�������
	//  09	�տ���ȫ��
	//  10	�տ����˺�
	//  14	��д���
	//  15	Сд���
	//  18	�����;
	//  19	��ע
	//	34	�Է���Ŀ
	//  0026	����
	//  0027	����
	//  0028	����
	//  0033	��Ŀ
}