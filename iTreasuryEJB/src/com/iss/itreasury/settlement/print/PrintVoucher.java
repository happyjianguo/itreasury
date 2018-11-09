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
				//��Խ�������
				int intervalYears = 0;
				//��Խ���·���
				int intervalMonths = 0;
				//���������
				int intervalDays = 0;
				//��Ϣ�յ���
				int sDay = 0;
				//ֹϢ�յ���
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
					
					//���������
					intervalDays = eDay - sDay;
				}
				
				//��Խ�������
				intervalYears = eCalendar.get(Calendar.YEAR) - sCalendar.get(Calendar.YEAR);
				
				//��Խ���·���
				intervalMonths = eCalendar.get(Calendar.MONTH) - sCalendar.get(Calendar.MONTH);
				
				//����ÿ��30�����ļ��������
				resIntervalDays = intervalYears * 360 + intervalMonths * 30 + intervalDays;

				//Boxu Add ����ļ������һ������,���ʱ�䲻��һ�����ҿ��¸պ���31��ʱ����������һ��
				if(resIntervalDays < 30
				&& !(eCalendar.get(Calendar.MONTH) == Calendar.FEBRUARY && (eCalendar.get(Calendar.DAY_OF_MONTH) == 28 || eCalendar.get(Calendar.DAY_OF_MONTH) == 29)))
				{
					//��ʵ�������������
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
				info.setPayAccountName(strPayAccountName.trim());
				//strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
				//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
				else //����
					{
					strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
					info.setPayAccountName(strPayAccountName.trim());
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
				//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
			
			
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
            
                
            String strAbstract = "��Ϣ�գ�" + strInterestStart + "��" + pi.getAbstract();
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
     * ���˵���ӡ 
     * ֻ��ӡ��һ�� 
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
            //����˻�
            if (pi.getPayAccountID() > 0)
            {
                strPayAccountName = NameRef.getAccountNameByID(pi.getPayAccountID());
                info.setPayAccountName(strPayAccountName.trim());
                //strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
				//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
                else //����
                    {
                    strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
                    info.setPayAccountName(strPayAccountName.trim());
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
                //strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
				//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
            
            info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
            
            
            String strAbstract = "��Ϣ�գ�" + strInterestStart + "��" + pi.getAbstract();
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
			//��Ӫ�����ջأ�ί�д����ջش��֧ȡƾ֤�н��͸���˻���
			//���������Ϣ�������ѣ������ѣ��ӽ��ͻ��Ļ����˻��п۳��������ۼ�����֧ȡƾ֤���
			//�������������˻��Ŵ��ڣ��򸶿Ϊ����������˻��������������Ϣ�����˻��Ŵ��ڣ��򸶿Ϊ��Ϣ�����˻��������ѡ���������������
			if (pi.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE || pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
			{
				System.out.println("=====��Ӫ/ί��");
				//������˻���
				if (pi.getPayAccountID() > 0)
				{
					System.out.println("=====11111111111111111");
					strPayAccountName = NameRef.getAccountNameByID(pi.getPayAccountID());
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
					//strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
					//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
	                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
	                {
	                    strPayAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getPayAccountID());
	                }
	                else
	                {
	                    strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
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
					//���ڸ�Ϣ�����˻���
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
						//��Ӫ�����ջأ����ڸ������ѻ����˻���
						if (pi.getPaySuretyFeeAccountID() > 0)
						{
							dAmount = DataFormat.formatDouble(dAmount + DataFormat.formatDouble(pi.getRealSuretyFee()));
							System.out.println("=====dAmount2��" + dAmount);
						}
					}
					else
						if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
						{
							//ί�д����ջأ����ڸ������ѻ����˻���
							if (pi.getPayCommissionAccountID() > 0)
							{
								dAmount = DataFormat.formatDouble(dAmount + DataFormat.formatDouble(pi.getRealCommission()));
								System.out.println("=====dAmount3��" + dAmount);
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
						//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
		                {
		                    strPayAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getPayInterestAccountID());
		                }
		                else
		                {
		                    strPayAccountNo = NameRef.getAccountNoByID(pi.getPayInterestAccountID());
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
								DataFormat.formatDouble(pi.getRealInterest())
									+ DataFormat.formatDouble(pi.getRealCompoundInterest())
									+ DataFormat.formatDouble(pi.getRealOverDueInterest()));
						System.out.println("=====dAmount4��" + dAmount);
						if (pi.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE)
						{
							//��Ӫ�����ջأ����ڸ������ѻ����˻���
							if (pi.getPaySuretyFeeAccountID() > 0)
							{
								dAmount = DataFormat.formatDouble(dAmount + DataFormat.formatDouble(pi.getRealSuretyFee()));
								System.out.println("=====dAmount5��" + dAmount);
							}
						}
						else
							if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
							{
								//ί�д����ջأ����ڸ������ѻ����˻���
								if (pi.getPayCommissionAccountID() > 0)
								{
									dAmount = DataFormat.formatDouble(dAmount + DataFormat.formatDouble(pi.getRealCommission()));
									System.out.println("=====dAmount6��" + dAmount);
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
								//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
				                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
				                {
				                    strPayAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getPaySuretyFeeAccountID());
				                }
				                else
				                {
				                    strPayAccountNo = NameRef.getAccountNoByID(pi.getPaySuretyFeeAccountID());
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
							}
							else
								if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
								{
									strPayAccountName = NameRef.getAccountNameByID(pi.getPayCommissionAccountID());
									info.setPayAccountName(DataFormat.formatString(strPayAccountName));
									//strPayAccountNo = NameRef.getAccountNoByID(pi.getPayCommissionAccountID());
									//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
					                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
					                {
					                    strPayAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getPayCommissionAccountID());
					                }
					                else
					                {
					                    strPayAccountNo = NameRef.getAccountNoByID(pi.getPayCommissionAccountID());
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
					//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
					//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
	                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
	                {
	                	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID());
	                }
	                else
	                {
	                	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
	                }
					//���Ӵ浥��
					if (pi.getFixedDepositNo() != null && pi.getFixedDepositNo().length() > 0)
					{
						strReceiveAccountNo = strReceiveAccountNo + "��" + pi.getFixedDepositNo();
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
						//���Ӵ浥��
						if (pi.getFixedDepositNo() != null && pi.getFixedDepositNo().length() > 0)
						{
							strReceiveAccountNo = strReceiveAccountNo + "��" + pi.getFixedDepositNo();
						}
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
						//strReceiveAccountNo = "";
						//���Ӵ浥��
						if (pi.getFixedDepositNo() != null && pi.getFixedDepositNo().length() > 0)
						{
							strReceiveAccountNo = strReceiveAccountNo + "��" + pi.getFixedDepositNo();
						}
						info.setReceiveAccountNo(DataFormat.formatString(strReceiveAccountNo));
					}
			}
			else //����ҵ��
				{
				System.out.println("=====����Ӫ/ί��====");
				//����˻��ڲ��˻�
				if (pi.getPayAccountID() > 0)
				{
					strPayAccountName = NameRef.getAccountNameByID(pi.getPayAccountID());
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
					//strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
					//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
	                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
	                {
	                    strPayAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getPayAccountID());
	                }
	                else
	                {
	                    strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
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
				//�ⲿ�����˻�
				else if(pi.getPayBankID() > 0)
				{
					Sett_BranchDAO ldao = new Sett_BranchDAO();
					BranchInfo linfo = new BranchInfo();
					linfo = ldao.findByID(pi.getPayBankID());
					info.setPayAccountName(DataFormat.formatString(linfo.getEnterpriseName()));
					info.setPayAccountNo(DataFormat.formatString(linfo.getBankAccountCode()));
					info.setPayBankName(DataFormat.formatString(linfo.getBranchName()));
				}
				//�ⲿ�˻�
				else
					if (pi.getExtClientName() != null && pi.getExtClientName().trim().length() > 0)
					{
						strPayAccountName = pi.getExtClientName();
						info.setPayAccountName(DataFormat.formatString(strPayAccountName));
						strPayAccountNo = pi.getExtAccountNo();
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
						if (pi.getTransTypeID() == SETTConstant.TransactionType.SPECIALOPERATION)
						{
							info.setPayAccountNo("");
						}
						else
						{
							info.setPayAccountNo(DataFormat.formatString(strPayAccountNo));
						}
					}
				//�տ�˻�
				String strReceiveAccountName = "";
				String strReceiveAccountNo = "";
				if (pi.getReceiveAccountID() > 0)
				{
					strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveAccountID());
					info.setReceiveAccountName(DataFormat.formatString(strReceiveAccountName));
					//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
					//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
	                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
	                {
	                	strReceiveAccountNo = NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID());
	                }
	                else
	                {
	                	strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
	                }
					//���Ӵ浥��
					if (pi.getFixedDepositNo() != null && pi.getFixedDepositNo().length() > 0)
					{
						strReceiveAccountNo = strReceiveAccountNo + "��" + pi.getFixedDepositNo();
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
						//���Ӵ浥��
						if (pi.getFixedDepositNo() != null && pi.getFixedDepositNo().length() > 0)
						{
							strReceiveAccountNo = strReceiveAccountNo + "��" + pi.getFixedDepositNo();
						}
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
						System.out.println("----------------------came here ---------------------");
						System.out.println("receiveaccountname is " + strReceiveAccountName);
						System.out.println("strReceiveAccountNo is " + strReceiveAccountNo);
						strReceiveAccountName = NameRef.getGLTypeDescByID(pi.getReceiveGL());
						info.setReceiveAccountName(DataFormat.formatString(strReceiveAccountName));
						strReceiveAccountNo = DataFormat.formatString(NameRef.getGLTypeNoByID(pi.getReceiveGL()));
						//���Ӵ浥��
						if (pi.getFixedDepositNo() != null && pi.getFixedDepositNo().length() > 0)
						{
							strReceiveAccountNo = strReceiveAccountNo + "��" + pi.getFixedDepositNo();
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
				info.setPayAccountName(DataFormat.formatString(strPayAccountName));
				//strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
				//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
			//�ⲿ�����˻�
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
				else //����
					{
					strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
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
					//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
					//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
	                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
					  
					//���ڶ���֧ȡ��֪֧ͨȡ�������λ�޸�Ϊ��������Ϣ��20060214��gqfang
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
						//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
					//����ת��
					if (pi.getReceiveInterestAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveInterestAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
						//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
				else //����ҵ��
					{
					if (pi.getReceiveAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
						//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
					strCheckUser = "����";
				}
				else
				{
					strCheckUser = "&nbsp;&nbsp;&nbsp;&nbsp;";
				}
			}
			info.setCheckUserName(strCheckUser);
			
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
            
            String strAbstract = "��Ϣ�գ�" + DataFormat.formatDate(pi.getInterestStartDate()) + "��" + pi.getAbstract();
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
	 * ��ӡ�ڲ�ת�˴�Ʊ
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
			//����˻�
			if (pi.getPayAccountID() > 0)
			{
				strPayAccountName = NameRef.getAccountNameByID(pi.getPayAccountID());
				info.setPayAccountName(DataFormat.formatString(strPayAccountName));
				//strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
				//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
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
			//�ⲿ�����˻�
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
				else //����
					{
					strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
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
					//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
					//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
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
					  
					//���ڶ���֧ȡ��֪֧ͨȡ�������λ�޸�Ϊ��������Ϣ��20060214��gqfang
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
						//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
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
					//����ת��
					if (pi.getReceiveInterestAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveInterestAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
						//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
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
				else //����ҵ��
					{
					if (pi.getReceiveAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
						//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
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
					strCheckUser = "����";
				}
				else
				{
					strCheckUser = "&nbsp;&nbsp;&nbsp;&nbsp;";
				}
			}
			info.setCheckUserName(strCheckUser);
			
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
            
            String strAbstract = "��Ϣ�գ�" + DataFormat.formatDate(pi.getInterestStartDate()) + "��" + pi.getAbstract();
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
	 * ��ӡ����ת���ڴ�Ʊ
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
			//����˻�
			if (pi.getPayAccountID() > 0)
			{
				strPayAccountName = NameRef.getAccountNameByID(pi.getPayAccountID());
				info.setPayAccountName(DataFormat.formatString(strPayAccountName));
				//strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
				//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
			//�ⲿ�����˻�
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
				else //����
					{
					strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
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
					//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
					//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
	                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
					  
					//���ڶ���֧ȡ��֪֧ͨȡ�������λ�޸�Ϊ��������Ϣ��20060214��gqfang
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
						//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
					//����ת��
					if (pi.getReceiveInterestAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveInterestAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
						//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
				else //����ҵ��
					{
					if (pi.getReceiveAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
						//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
					strCheckUser = "����";
				}
				else
				{
					strCheckUser = "&nbsp;&nbsp;&nbsp;&nbsp;";
				}
			}
			info.setCheckUserName(strCheckUser);
			
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
            
            String strAbstract = "��Ϣ�գ�" + DataFormat.formatDate(pi.getInterestStartDate()) + "��" + pi.getAbstract();
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
	 * ��ӡ����ת���ڴ�Ʊ
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
			//����˻�
			if (pi.getPayAccountID() > 0)
			{
				strPayAccountName = NameRef.getAccountNameByID(pi.getPayAccountID());
				info.setPayAccountName(DataFormat.formatString(strPayAccountName));
				//strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
				//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
			//�ⲿ�����˻�
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
				else //����
					{
					strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
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
					//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
					//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
	                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
					  
					//���ڶ���֧ȡ��֪֧ͨȡ�������λ�޸�Ϊ��������Ϣ��20060214��gqfang
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
						//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
					//����ת��
					if (pi.getReceiveInterestAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveInterestAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
						//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
				else //����ҵ��
					{
					if (pi.getReceiveAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
						//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
					strCheckUser = "����";
				}
				else
				{
					strCheckUser = "&nbsp;&nbsp;&nbsp;&nbsp;";
				}
			}
			info.setCheckUserName(strCheckUser);
			
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
            
            String strAbstract = "��Ϣ�գ�" + DataFormat.formatDate(pi.getInterestStartDate()) + ("".equals(pi.getAbstract())?"":"��" + pi.getAbstract());
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
	 * ��ӡ����ת֪ͨ��Ʊ
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
			//����˻�
			if (pi.getPayAccountID() > 0)
			{
				strPayAccountName = NameRef.getAccountNameByID(pi.getPayAccountID());
				info.setPayAccountName(DataFormat.formatString(strPayAccountName));
				//strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
				//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
			//�ⲿ�����˻�
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
				else //����
					{
					strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
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
					//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
					//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
	                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
					  
					//���ڶ���֧ȡ��֪֧ͨȡ�������λ�޸�Ϊ��������Ϣ��20060214��gqfang
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
						//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
					//����ת��
					if (pi.getReceiveInterestAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveInterestAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
						//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
				else //����ҵ��
					{
					if (pi.getReceiveAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
						//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
					strCheckUser = "����";
				}
				else
				{
					strCheckUser = "&nbsp;&nbsp;&nbsp;&nbsp;";
				}
			}
			info.setCheckUserName(strCheckUser);
			
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
            
            String strAbstract = "��Ϣ�գ�" + DataFormat.formatDate(pi.getInterestStartDate()) + ("".equals(pi.getAbstract())?"":"��" + pi.getAbstract());
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
	 * ��ӡ֪ͨת���ڴ�Ʊ
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
			//����˻�
			if (pi.getPayAccountID() > 0)
			{
				strPayAccountName = NameRef.getAccountNameByID(pi.getPayAccountID());
				info.setPayAccountName(DataFormat.formatString(strPayAccountName));
				//strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
				//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
			//�ⲿ�����˻�
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
				else //����
					{
					strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
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
					//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
					//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
	                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
					  
					//���ڶ���֧ȡ��֪֧ͨȡ�������λ�޸�Ϊ��������Ϣ��20060214��gqfang
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
						//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
					//����ת��
					if (pi.getReceiveInterestAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveInterestAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
						//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
				else //����ҵ��
					{
					if (pi.getReceiveAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
						//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
					strCheckUser = "����";
				}
				else
				{
					strCheckUser = "&nbsp;&nbsp;&nbsp;&nbsp;";
				}
			}
			info.setCheckUserName(strCheckUser);
			
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
            
            String strAbstract = "��Ϣ�գ�" + DataFormat.formatDate(pi.getInterestStartDate()) + "��" + pi.getAbstract();
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
	 * ��ӡ����ת�˴�Ʊ
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
			//����˻�
			if (pi.getPayAccountID() > 0)
			{
				strPayAccountName = NameRef.getAccountNameByID(pi.getPayAccountID());
				info.setPayAccountName(DataFormat.formatString(strPayAccountName));
				//strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
				//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
			//�ⲿ�����˻����н��޸ģ�������������������
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
				else //����
					{
					strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
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
					//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
					//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
	                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
					  
					//���ڶ���֧ȡ��֪֧ͨȡ�������λ�޸�Ϊ��������Ϣ��20060214��gqfang
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
						//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
					//����ת��
					if (pi.getReceiveInterestAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveInterestAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
						//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
				else //����ҵ��
					{
					if (pi.getReceiveAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
						//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
					strCheckUser = "����";
				}
				else
				{
					strCheckUser = "&nbsp;&nbsp;&nbsp;&nbsp;";
				}
			}
			info.setCheckUserName(strCheckUser);
			
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
            
            String strAbstract = "��Ϣ�գ�" + DataFormat.formatDate(pi.getInterestStartDate()) + "��" + pi.getAbstract();
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
	 * ��ӡ����ת�˴�Ʊ
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
			//����˻�
			if (pi.getPayAccountID() > 0)
			{
				strPayAccountName = NameRef.getAccountNameByID(pi.getPayAccountID());
				info.setPayAccountName(DataFormat.formatString(strPayAccountName));
				//strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
				//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
			//�ⲿ�����˻�
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
				else //����
					{
					strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
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
					//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
					//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
	                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
					  
					//���ڶ���֧ȡ��֪֧ͨȡ�������λ�޸�Ϊ��������Ϣ��20060214��gqfang
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
						//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
					//����ת��
					if (pi.getReceiveInterestAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveInterestAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveInterestAccountID());
						//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
				else //����ҵ��
					{
					if (pi.getReceiveAccountID() > 0)
					{
						strReceiveAccountName = NameRef.getAccountNameByID(pi.getReceiveAccountID());
						info.setReceiveAccountName(strReceiveAccountName);
						//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
						//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
		                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
					strCheckUser = "����";
				}
				else
				{
					strCheckUser = "&nbsp;&nbsp;&nbsp;&nbsp;";
				}
			}
			info.setCheckUserName(strCheckUser);
			
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
            
            String strAbstract = "��Ϣ�գ�" + DataFormat.formatDate(pi.getInterestStartDate()) + "��" + pi.getAbstract();
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
			info.setPayBankName(DataFormat.formatString(strPayBankName));
			String strReceiveBankName = "";
			info.setReceiveBankName(DataFormat.formatString(strReceiveBankName));
			String strPayAccountName = "";
			String strPayAccountNo = "";
			//����˻�
			if (pi.getPayAccountID() > 0)
			{
				strPayAccountName = NameRef.getAccountNameByID(pi.getPayAccountID());
				info.setPayAccountName(DataFormat.formatString(strPayAccountName));
				//strPayAccountNo = NameRef.getAccountNoByID(pi.getPayAccountID());
				//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
			//�ⲿ�����˻�
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
				else //����
					{
					strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
					info.setPayAccountName(DataFormat.formatString(strPayAccountName));
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
				//strReceiveAccountNo = NameRef.getAccountNoByID(pi.getReceiveAccountID());
				//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
			
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
            String strAbstract = "��Ϣ�գ�" + DataFormat.formatDate(pi.getInterestStartDate()) + "��" + pi.getAbstract();
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
	
//	��PiInfo�и��ݱ��ȡ����Ӧ��ֵ
	//���ߣ�feiye   2005-11-23
	private static String getValueForScodeFromPIVo(Object pInfo,Collection collXml,Object objPiInfo){
		//���ڷ�װ���piInfo����������;�ΪString
		String backInfo="";		//���ڷ��ص�ֵ
		String propertyName="";	//��sCoce��Ӧ��PIVo��������
		try{
			//����sCode ���ҵ�piInfo���Ӧ���������֣���Ҫ��һ�����������ļ�)
			if(collXml!=null){
				for(int i=0;i<((ArrayList)collXml).size();i++){
					TemplateSettingXmlInfo info = (TemplateSettingXmlInfo)((ArrayList)collXml).get(i);
					//sCode �� ���;����
					if(info.getTemplateDetailCode().equals(((PrintOptionInfo)pInfo).m_strCode.trim()) && info.getTemplateType()==((PrintOptionInfo)pInfo).m_nPrintTemplateType){
						propertyName=info.getTemplateDetailVariable().trim();
						break;						
					}
				}
			}
			System.out.println("     =========�õ�����XML�е���Ӧ�ķ�����Ϊ:"+propertyName);
			
		/***********�������Ե������Զ���piInfo��ȡ�����Ե�ֵ*************/
		//������������ִ�з���
			if(!propertyName.equals("") && !propertyName.equals("noVariable")){
				System.out.println("     =========ִ�����д˷��� get"+propertyName+"()");
				Method meth=objPiInfo.getClass().getMethod("get"+propertyName,new Class[]{});
				Object retobj=meth.invoke(objPiInfo,new Object[]{});
				backInfo=(String)retobj;
			}
			
		}catch (NoSuchMethodException e) {
			   backInfo="";
	       	   System.out.println("4=========�������"+objPiInfo.getClass()+"��û�д˷��� get"+propertyName+"()");
	           System.err.println(e);
	    } catch(Exception e){
	    	backInfo="";
			e.printStackTrace();
		}
	    return backInfo;
	}
	
	
	/**
	 *	��ʾ�״� ���ó���
	 * @throws Exception
	 * (feiye�İ�)
	 */								
	public static void PrintTemplate(long lPrintTemplateID, PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			System.out.println("=============Ҷ�ɴ�ӡ==============");
			PrintInfoVo PIVo=new PrintInfoVo();
			
			//��ҵ��ϵͳ�еõ���ƾ֤INFO����Ӧ�Ĵ����Ժö�Ӧʵ�ʵ�ƾ֤��ϸԪ������
			//��Pi   ��    PIVo  ת��
			PIVo.setExecuteDate(DataFormat.formatDate(pi.getExecuteDate()));
			
			System.out.println("    ִ����:"+pi.getExecuteDate());
			
			if (PIVo.getExecuteDate().length() < 10)
				PIVo.setExecuteDate("0000000000");			
			PIVo.setYear(PIVo.getExecuteDate().substring(0, 4));
			PIVo.setMonth(PIVo.getExecuteDate().substring(5, 7));
			PIVo.setDay(PIVo.getExecuteDate().substring(8, 10));
			
			PIVo.setTransNo(pi.getTransNo());

			if (pi.getPayAccountID() > 0)
			{
				//ȡ�ɻ������к� add by feiye 2006.5.16
				//PIVo.setPayAccountNo(DataFormat.formatString(NameRef.getBankAccountCodeByAccountIDandBankID(pi.getPayAccountID(), pi.getPayBankID())));
				PIVo.setPayBankName(DataFormat.formatString(IPrintTemplate.getBankAccountPrintNameByID(pi.getPayBankID())));
				PIVo.setPayProvince(DataFormat.formatString(IPrintTemplate.getBankAccountProvinceByID(pi.getPayBankID())));
				PIVo.setPayCity(DataFormat.formatString(IPrintTemplate.getBankAccountCityByID(pi.getPayBankID())));
				
				PIVo.setPayAccountName(DataFormat.formatString(NameRef.getAccountNameByID(pi.getPayAccountID())));
				PIVo.setPayAccountNo(DataFormat.formatString(NameRef.getAccountNoByID(pi.getPayAccountID()))); //�õ����ڲ��˻�
				
				//����ǻ������ڴ浥ҵ��Ļ���ȡ����Ԥ��ҵ������к�
				if(pi.getDepositBillNO()!=null && !pi.getDepositBillNO().equals("")){
					System.out.println(" :    �ǻ������ڴ浥ҵ��!");
					PIVo.setPayAccountName(DataFormat.formatString(NameRef.getBankFactAccountName(pi.getPayAccountID())));
					PIVo.setPayAccountNo(DataFormat.formatString(NameRef.getBankFactAccountNO(pi.getPayAccountID())));
				}
				
				System.out.println("1:�õ��ĸ���˻���Ϊ:"+PIVo.getPayAccountNo()+"pi.getPayBankID()"+pi.getPayBankID()+"pi.getPayAccountID()"+pi.getPayAccountID());

			}
			else	//
			{
				PIVo.setPayAccountName(DataFormat.formatString(NameRef.getBankNameByID(pi.getPayBankID())));	//���ⲿ�е�����
				PIVo.setPayAccountNo(DataFormat.formatString(NameRef.getBankAccountCodeByID(-1,pi.getPayBankID())));	//���ⲿ�е��˺�
				
				PIVo.setPayBankName(DataFormat.formatString(pi.getExtRemitInBank()));
				PIVo.setPayProvince(DataFormat.formatString(pi.getExtRemitInProvince()));
				PIVo.setPayCity(DataFormat.formatString(pi.getPayExtRemitInCity()));
				
				System.out.println("2:�õ��ĸ���˻���Ϊ:"+PIVo.getPayAccountNo()+"���������IDΪ:"+pi.getPayBankID());
			}
			
			if (pi.getReceiveAccountID() > 0)
			{
				PIVo.setReceiveAccountName(DataFormat.formatString(NameRef.getAccountNameByID(pi.getReceiveAccountID())));
				PIVo.setReceiveAccountNo(DataFormat.formatString(NameRef.getBankAccountCodeByAccountIDandBankID(pi.getReceiveAccountID(), pi.getReceiveBankID())));
				PIVo.setReceiveBankName(DataFormat.formatString(IPrintTemplate.getBankAccountPrintNameByID(pi.getReceiveBankID())));
				PIVo.setReceiveProvince(DataFormat.formatString(IPrintTemplate.getBankAccountProvinceByID(pi.getReceiveBankID())));
				PIVo.setReceiveCity(DataFormat.formatString(IPrintTemplate.getBankAccountCityByID(pi.getReceiveBankID())));
				
				System.out.println("1:�õ����տ�˻���Ϊ:"+PIVo.getReceiveAccountNo()+"pi.getReceiveBankID()"+pi.getReceiveBankID()+"pi.getReceiveAccountID()"+pi.getReceiveAccountID());
			}
			else	//���û�����ã���ֻ�Ӵ�PI����ȡֵ
			{
				PIVo.setReceiveAccountName(DataFormat.formatString(pi.getExtClientName()));
				PIVo.setReceiveAccountNo(DataFormat.formatString(pi.getExtAccountNo()));
				PIVo.setReceiveBankName(DataFormat.formatString(pi.getExtRemitInBank()));
				PIVo.setReceiveProvince(DataFormat.formatString(pi.getExtRemitInProvince()));
				PIVo.setReceiveCity(DataFormat.formatString(pi.getExtRemitInCity()));
				System.out.println("2:�õ����տ�˻���Ϊ:"+pi.getExtAccountNo());
			}
			
			PIVo.setAmount(DataFormat.formatAmount(pi.getAmount()));
			PIVo.setChineseAmount(ChineseCurrency.showChinese(PIVo.getAmount(), pi.getCurrencyID()));
			PIVo.setAmount(DataFormat.formatDisabledAmount(pi.getAmount()));
			PIVo.setInputUser(NameRef.getUserNameByID(pi.getInputUserID()));
			PIVo.setCheckUser(NameRef.getUserNameByID(pi.getCheckUserID()));
			PIVo.setAbstract(pi.getAbstract());
			//for ת����̨�� start
			PIVo.setBillType(LOANConstant.DraftType.getName(pi.getBillTypeID()));
			PIVo.setDiscountBillNo(DataFormat.formatString(pi.getDiscountBillNo()));

			PIVo.setEndDate(DataFormat.formatDate(pi.getEndDate()));
			System.out.println("    ������:"+pi.getEndDate());
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
			
			System.out.println("    ����:"+pi.getRate());

			PIVo.setRate(DataFormat.formatAmountUseZero(pi.getRate(),6));
			PIVo.setDiscountInterestAmount(DataFormat.formatDisabledAmount(pi.getDiscountInterestAmount()));
			PIVo.setDiscountAmount(DataFormat.formatDisabledAmount(pi.getDiscountAmount()));
			PIVo.setApplicantName(DataFormat.formatString(pi.getApplicantName()));
			PIVo.setApplicantAccountNo(DataFormat.formatString(pi.getApplicantAccountNo()));
			PIVo.setApplicantAccountBankNo(DataFormat.formatString(pi.getApplicantAccountBankNo()));
			
			
			//�ڶ�����Ҫ��ӵĲ���
			PIVo.setInterestStartDate(DataFormat.formatDate(pi.getInterestStartDate()));
			System.out.println("    ��Ϣ��:"+pi.getInterestStartDate());
			if (PIVo.getInterestStartDate().length() < 10)
				PIVo.setInterestStartDate("0000000000");
			
			System.out.println("    ��������:"+pi.getFixedDepositTerm());
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
			
			//�õ�ҵ������ı�����Ϣ		2006.3.26  Ϊ�������ڴ浥 feiye
			System.out.println("�õ��鵽ҵ����ı���IDΪ:"+pi.getCurrencyID());
			PIVo.setCurrencyType( Constant.CurrencyType.getName(pi.getCurrencyID()) );
			System.out.println("����ת�������õ��鵽ҵ����ı�������Ϊ:"+PIVo.getCurrencyType());
			
			//�õ�ҵ����Ļ������ڴ浥��	2006.3.26  Ϊ�������ڴ浥 feiye
			System.out.println("=====pi.getDepositBillNO():"+pi.getDepositBillNO());
			if(pi.getDepositBillNO()!=null && !pi.getDepositBillNO().equals("")){
				PIVo.setDepositBillNO(pi.getDepositBillNO());
			}
			System.out.println("=====PIVo.getDepositBillNO():"+PIVo.getDepositBillNO());
			

			//�õ�ҵ����Ļ������ڴ浥��	2006.3.26  Ϊ�������ڴ浥 feiye
			System.out.println("=====pi.getTransTypeID():"+pi.getTransTypeID());
			if(pi.getTransTypeID()!=-1){
				PIVo.setTransactionType(SETTConstant.TransactionType.getName(pi.getTransTypeID()));
			}
			System.out.println("=====PIVo.getTransactionType():"+PIVo.getTransactionType());

			//�õ�ҵ����Ŀ���֤ʵ���	2006.3.27  Ϊ�������ڴ浥 feiye
			System.out.println("=====pi.getFixedDepositNo():"+pi.getFixedDepositNo());
			if(pi.getFixedDepositNo()!=null && !pi.getFixedDepositNo().equals("")){
				PIVo.setFixedDepositNo(pi.getFixedDepositNo());
			}
			System.out.println("=====PIVo.getFixedDepositNo():"+PIVo.getFixedDepositNo());
//			add by wjliu 2007-5-11
			//���ڴ浥��
			if(pi.getFixedDepositNo()!=null && !pi.getFixedDepositNo().equals("")){
				PIVo.setFixedDepositNo(pi.getFixedDepositNo());
			}
//			ʵ����Ϣ,��Ϣ֧��
			if(pi.getPayableInterest()!=0.0){
				PIVo.setPayableInterest(String.valueOf(new java.math.BigDecimal(pi.getPayableInterest())));
			}
//			��Ϣ�ϼ�TotalInterest 
			
				PIVo.setTotalInterest(String.valueOf(new java.math.BigDecimal (pi.getPayableInterest() +pi.getCurrentInterest() + pi.getOtherInterest() + pi.getAmount())));
				//�˻���
				if(pi.getAccountNo()!=null && !pi.getAccountNo().equals(""))
				{
					PIVo.setAccountNo(pi.getAccountNo());
				}
				//�˻���
				if(pi.getAccountName()!=null && !pi.getAccountName().equals(""))
				{
					PIVo.setAccountName(pi.getAccountName());
				}
			
			
			
			/*		����û������Ӧ��������Ŀ�ж�  Ϊ�ĸ����촦�Ķ������ӡ,��3.1��Ķ����ҲӦ�ô�ӡ,��ʱ�Ӹ������ 2006.3.26 feiye
			//Ϊһ�������������PIVO
			
			System.out.println("��ʼһ����������PIVO��PI��õ�");
			System.out.println("�õ�PI����� pi.getPayBankID()"+pi.getPayBankID());
			System.out.println("�õ�PI����� pi.getReceiveBankID()"+pi.getReceiveBankID());
			
			if(pi.getPayBankID()!=-1){
				//�����������
				PIVo.setPayBankName(NameRef.getBankNameByID(pi.getPayBankID()));
				PIVo.setPayAccountNo(NameRef.getBankAccountCodeByAccountIDandBankID(-1,pi.getPayBankID()));
			}
			if(pi.getReceiveBankID()!=-1){
				//�տ��������
				PIVo.setReceiveBankName(NameRef.getBankNameByID(pi.getReceiveBankID()));
				PIVo.setReceiveAccountNo(NameRef.getBankAccountCodeByAccountIDandBankID(-1,pi.getReceiveBankID()));
			}
			System.out.println("    ==�����������"+PIVo.getPayBankName());
			System.out.println("    ==��������˺�"+PIVo.getPayAccountNo());
			System.out.println("    ==�տ��������"+PIVo.getReceiveBankName());
			System.out.println("    ==�տ�����˺�"+PIVo.getReceiveAccountNo());
			*/
			
			
			
			System.out.println("ȡ��pi�������,����PIVo���!");
			//for ת����̨�� end
			
			/**********************************����ʵ�ʵĴ�ӡҳ��*************************/
			//��һ��:�õ����ڴ�ģ�����е���ϸ��ϢvPt:Vecor
			PrintTemplateBiz ptBiz=new PrintTemplateBiz();
			Vector vPt=new Vector();
			System.out.println("=========��Ҫ���д���Ĵ�ӡģ��ID:"+lPrintTemplateID);
			vPt=(Vector)ptBiz.findPrintOptionDetailsByTemplateID(lPrintTemplateID);

			//�ڶ���:����ÿ������ϸ��Ϣ�е�sCode�ֶε�ֵ������pi����Ϣ����ϸ��m_strDetailsData(��������)�����
			PrintOptionInfo pInfo=new PrintOptionInfo();
			if(vPt!=null){
					//��ȡXML�õ���ӡģ����ϸ��������Ϣ
					TemplateSettingXml xml = new TemplateSettingXml();
					Collection collXML = null;
					collXML=xml.getTemplateSetting();
					if(collXML!=null)
						System.out.println("�õ���XML������Ϣ���ϵĳ���Ϊ��"+collXML.size());
					
						//����pInfo�����sCode�����͵õ�collXML�е�������Ϣ�����еķ�������Ȼ��ִ��PIVo�еķ���
					for(int i=0;i<vPt.size();i++){
						//�õ�һ����ӡ��ϸinfo
						pInfo=(PrintOptionInfo)vPt.get(i);
						
						//�����������Ϣȡ������Ӧ��bean�е�����ֵ�Ļ������ӡ��ҳ���ϵ�����Ϊ�գ�������ʾ
						pInfo.m_strDetailsData="";
						pInfo.m_strDetailsData=getValueForScodeFromPIVo(pInfo,collXML,PIVo);
						System.out.println("LOG:��ϸԪ�����õ�sCode"+pInfo.m_strCode+"�õ��ķ���ֵΪ:"+pInfo.m_strDetailsData);
				}
			}
			//������:�����Ϸ�װ�õ���ȷ��Ϣ����jspҳ�棬��ӡ����
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
			String currencyName="";
			String rate="";
			String ManagerName = ""; //��λ����
			ManagerName=pi.getManagerName();
			rate=String.valueOf(pi.getRate());
			String InterestStartDate=DataFormat.formatDate(pi.getInterestStartDate());
			String endDate=DataFormat.formatDate(pi.getEndDate());
			String FixedDepositTerm = ""; //��������
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
				case 4 : //���ڴ���
					String[] strCode4 = { "01", "02", "03", "20", "04", "05", "14", "35", "21", "22", "23", "24", "25", "0026", "0027", "0028" };//��sett_printtemplatedetails�е�scode�ֶε�����һ��
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
				//info.setRepaymentAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
				//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
			info.setLoanRate(DataFormat.formatAmountUseZero(pi.getRate(),6));
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
				//info.setRepaymentAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
				//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
                if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
			//info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
			//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
			//��ͬ����
			LoanPayFormDetailInfo lpfdinfo = new LoanPayFormDetailInfo();
			lpfdinfo.setLoadNoteID(pi.getLoanNoteID());
			Sett_TransGrantLoanDAO stdao = new Sett_TransGrantLoanDAO();
			lpfdinfo = stdao.findPayFormDetailByCondition(lpfdinfo);
			//ί�е�λ
			info.setConsignUnit(lpfdinfo.getClientName());
			info.setContractRate(DataFormat.formatRate(DataFormat.formatDisabledAmount(lpfdinfo.getInterestRate(), 2, 4)));
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			info.setLoanInterval(DataFormat.getChineseDateString(lpfdinfo.getStart()) + " �� " + DataFormat.getChineseDateString(lpfdinfo.getEnd()));
			info.setLoanType(LOANConstant.LoanType.getName(lpfdinfo.getLoanType()));
			info.setLoanUnit(NameRef.getClientNameByID(pi.getReceiveClientID()));
			info.setNote(pi.getAbstract());
			//�����˻���
			//info.setLoanAccountNo(NameRef.getAccountNoByID(pi.getLoanAccountID()));
			//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
            {
            	info.setLoanAccountNo(NameRef.getOriginalAcctNoByAcctID(pi.getLoanAccountID()));
            }
            else
            {
            	info.setLoanAccountNo(NameRef.getAccountNoByID(pi.getLoanAccountID()));
            }
			//��λ������Ӫ�����˻�
			info.setLoanUnit(NameRef.getAccountNameByID(pi.getLoanAccountID()));
			if (pi.getReceiveAccountID() > 0)
			{
				//�տ����˺�
				//info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
				//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
	            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
	            {
	            	info.setAccountNo(NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID()));
	            }
	            else
	            {
	            	info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
	            }
				//�տ��˿�����������
				info.setOpenBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			else
				if (pi.getReceiveBankID() > 0)
				{
					//�տ����˺�
					info.setAccountNo(pi.getExtAccountNo());
					//�տ��˿�����������
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
			//info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
			//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
			//��ͬ����
			LoanPayFormDetailInfo lpfdinfo = new LoanPayFormDetailInfo();
			lpfdinfo.setLoadNoteID(pi.getLoanNoteID());
			Sett_TransGrantLoanDAO stdao = new Sett_TransGrantLoanDAO();
			lpfdinfo = stdao.findPayFormDetailByCondition(lpfdinfo);
			//ί�е�λ
			info.setConsignUnit(lpfdinfo.getClientName());
			info.setContractRate(DataFormat.formatRate(DataFormat.formatDisabledAmount(lpfdinfo.getInterestRate(), 2, 4)));
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			info.setLoanInterval(DataFormat.getChineseDateString(lpfdinfo.getStart()) + " �� " + DataFormat.getChineseDateString(lpfdinfo.getEnd()));
			info.setLoanType(LOANConstant.LoanType.getName(lpfdinfo.getLoanType()));
			info.setLoanUnit(NameRef.getClientNameByID(pi.getReceiveClientID()));
			info.setNote(pi.getAbstract());
			//�����˻���
			//info.setLoanAccountNo(NameRef.getAccountNoByID(pi.getLoanAccountID()));
			//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
            {
            	info.setLoanAccountNo(NameRef.getOriginalAcctNoByAcctID(pi.getLoanAccountID()));
            }
            else
            {
            	info.setLoanAccountNo(NameRef.getAccountNoByID(pi.getLoanAccountID()));
            }
			//��λ������Ӫ�����˻�			
			info.setLoanUnit(NameRef.getAccountNameByID(pi.getLoanAccountID()));
			if (pi.getReceiveAccountID() > 0)
			{
				//�տ����˺�
				//info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
				//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
	            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
	            {
	            	info.setAccountNo(NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID()));
	            }
	            else
	            {
	            	info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
	            }
				//�տ��˿�����������
				info.setOpenBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			else
				if (pi.getReceiveBankID() > 0)
				{
					//�տ����˺�
					info.setAccountNo(pi.getExtAccountNo());
					//�տ��˿�����������
					info.setOpenBankName(pi.getExtRemitInBank());
				}
			//��������
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
			TransDiscountDelegation transDiscountDelegation = new TransDiscountDelegation();
			dcinfo = transDiscountDelegation.findDiscountCredenceByID(pi.getDiscountNoteID());
			
			System.out.println("====befor===="+info.getAbstract());
			System.out.println("====���ֺ�ͬ��===="+dcinfo.getDiscountContractCode());
			System.out.println("������ƾ֤��ţ�" + dcinfo.getCode());
			info.setAbstract(pi.getAbstract() + "�����ֺ�ͬ�ţ�" + dcinfo.getDiscountContractCode() + "������ƾ֤��ţ�" + dcinfo.getCode());
			System.out.println("=====after==="+info.getAbstract());
			info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getDiscountAmount()));
			//��Ʊ����Ϣ
			if (pi.getReceiveAccountID() > 0)
			{
				//��Ʊ������
				info.setBillKeeperName(NameRef.getAccountNameByID(pi.getReceiveAccountID()));
				//��Ʊ���˺�
				//info.setBillKeeperAccount(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
				//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
	            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
	            {
	            	info.setBillKeeperAccount(NameRef.getOriginalAcctNoByAcctID(pi.getReceiveAccountID()));
	            }
	            else
	            {
	            	info.setBillKeeperAccount(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
	            }
				//��Ʊ�˿�����������
				info.setBillKeeperBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			else
				if (pi.getReceiveBankID() > 0)
				{
					//��Ʊ������
					//info.setBillKeeperName(NameRef.getBankNameByID(pi.getReceiveBankID()));
					info.setBillKeeperName(pi.getExtClientName());
					//��Ʊ���˺�
					info.setBillKeeperAccount(pi.getExtAccountNo());
					//��Ʊ�˿�����������
					info.setBillKeeperBankName(pi.getExtRemitInBank());
				}
			//�ر�˵��:��Ʊ��Ʊ����Ϣ��ʱ����Ҫ
			//��Ʊ��Ʊ���˺�
			info.setBillOutAccount("&nbsp;");
			info.setBillOutBankName("&nbsp;");
			//��Ʊ��Ʊ������
			info.setBillOutName("&nbsp;");
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
			//������Ϣ
			info.setDiscountInterest(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getDiscountInterestAmount()));
			//���ֺ���
			info.setDiscountNo(dcinfo.getCode());
			//������
			info.setDiscountRate(DataFormat.formatRate(DataFormat.formatDisabledAmount(dcinfo.getDiscountRate(), 2, 4)));
			//��������
			info.setDiscountType(LOANConstant.DraftType.getName(dcinfo.getDraftTypeID()));
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			//ʵ�����ֽ��
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
			//ƾ֤��findbyid����
			IPrintTemplate iPrintTemplate = new IPrintTemplate();
			DiscountCredenceInfo discountCredenceInfo = null;
			discountCredenceInfo = iPrintTemplate.findDiscountCredenceByID(pi.getDiscountNoteID());
			if (discountCredenceInfo != null)
			{
				info.setAbstract(pi.getAbstract() + "�����ֺ�ͬ�ţ�" + NameRef.getContractNoByID(pi.getContractID()) + "������ƾ֤��ţ�" + discountCredenceInfo.getCode());
				info.setBillType(LOANConstant.DraftType.getName(discountCredenceInfo.getDraftTypeID()));
				info.setDateDiscount(DataFormat.getChineseDateString(DataFormat.getDateTime(NameRef.getDiscountDateByDiscountBillID(pi.getDiscountBillID()))));
				info.setDateEnd(DataFormat.getChineseDateString(DataFormat.getDateTime(NameRef.getEndDateByDiscountBillID(pi.getDiscountBillID()))));
			}
			//info.setDiscountAccount(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
			//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
			
			/**
			 * ������Ϣ,�ǰ�ÿ��30�����,���ǰ�ʵ����������
			 * true :��ÿ��30�����;false:��ʵ����������
			 */
			long intervalDaysFlag = -1;
			boolean bIsFact_InterestIntervalDays = false;
			bIsFact_InterestIntervalDays = Config.getBoolean(Config.SETT_INTEREST_INTERVALDAYS_ISFACT, false);
			if(bIsFact_InterestIntervalDays)
			{
				//��ʵ����������
				intervalDaysFlag = 1;
			}
			else
			{
				//��ÿ��30�����
				intervalDaysFlag = 2;
			}
			
			
			System.out.println("�����Ϣ֪ͨ��");
			ShowPayInterestInfo info = new ShowPayInterestInfo();
			info.setCurrencyID(pi.getCurrencyID());
			info.setOfficeID(pi.getOfficeID());
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
			//info.setAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
			//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
            {
            	info.setAccountNo(NameRef.getOriginalAcctNoByAcctID(pi.getPayAccountID()));
            }
            else
            {
            	info.setAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
            }
			//������Ϣ����ʼ���ڣ���Ϣ���ڣ����������������ʣ���Ϣ
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
			//Э����Ϣ����ʼ���ڣ���Ϣ���ڣ����������������ʣ���Ϣ
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
			//��Ϣ�ܶ�
			info.setTotalInterest(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getCurrentInterest()) + DataFormat.formatDouble(pi.getAccordInterest())));
			//��Ϣ�˻���
			//info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getReceiveInterestAccountID()));
			//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
            {
            	info.setInterestAccountNo(NameRef.getOriginalAcctNoByAcctID(pi.getReceiveInterestAccountID()));
            }
            else
            {
            	info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getReceiveInterestAccountID()));
            }
			//��Ӧ�˻���
			//info.setCurrentAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
			//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
            {
            	info.setCurrentAccountNo(NameRef.getOriginalAcctNoByAcctID(pi.getPayAccountID()));
            }
            else
            {
            	info.setCurrentAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
            }
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
				if (pi.getStatusID() == SETTConstant.TransactionStatus.CHECK)
				{
					info.setCheckUserName("����");
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
	 * �����Ϣ֪ͨ��
	 * @param pi
	 * @param out
	 * @throws Exception
	 */
	public static void PrintDepositInterestNotice(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			/**
			 * ������Ϣ,�ǰ�ÿ��30�����,���ǰ�ʵ����������
			 * true :��ÿ��30�����;false:��ʵ����������
			 */
			long intervalDaysFlag = -1;
			boolean bIsFact_InterestIntervalDays = false;
			bIsFact_InterestIntervalDays = Config.getBoolean(Config.SETT_INTEREST_INTERVALDAYS_ISFACT, false);
			if(bIsFact_InterestIntervalDays)
			{
				//��ʵ����������
				intervalDaysFlag = 1;
			}
			else
			{
				//��ÿ��30�����
				intervalDaysFlag = 2;
			}
			
			
			//System.out.println("�����Ϣ֪ͨ��");
			ShowPayInterestInfo info = new ShowPayInterestInfo();
			info.setCurrencyID(pi.getCurrencyID());
			info.setOfficeID(pi.getOfficeID());
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
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
			info.setCurrentInterestDay((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), 1) + intervalDaysFlag) + "");
			info.setCurrentInterestAmount(
				DataFormat.formatDisabledAmount(
					IPrintTemplate.getCurrentAccountBanlance(pi.getPayAccountID(), pi.getStartDate(), pi.getInterestStartDate())
						/ (getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), intervalDaysFlag) + 1)));
			info.setCurrentInterestRate(DataFormat.formatAmountUseZero(pi.getRate(),6));
			info.setCurrentInterest(DataFormat.formatDisabledAmount(pi.getCurrentInterest()));
			//Э����Ϣ����ʼ���ڣ���Ϣ���ڣ��������������ʣ���Ϣ
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
			//��Ϣ�ܶ�
			info.setTotalInterest(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getCurrentInterest()) + DataFormat.formatDouble(pi.getAccordInterest())));
			//��Ϣ�˻���
			info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getReceiveInterestAccountID()));
			//��Ӧ�˻���
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
			if(pi.getCheckUserID() > 0)
			    info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			else
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
			System.out.print("����1��" + info.getCurrentInterestDay());
			System.out.print("����2��" + info.getAccordInterestDay());
            //�޸� by kenny[2006-05-06] ȷ�����ڴ��ҵ�����������

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
	*  ��Ϣ֪ͨ��(��Ϣ��ר��)��ӡ
	* @throws Exception
	*/
	public static void PrintInterestNotice1(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			/**
			 * ������Ϣ,�ǰ�ÿ��30�����,���ǰ�ʵ����������
			 * true :��ÿ��30�����;false:��ʵ����������
			 */
			long intervalDaysFlag = -1;
			boolean bIsFact_InterestIntervalDays = false;
			bIsFact_InterestIntervalDays = Config.getBoolean(Config.SETT_INTEREST_INTERVALDAYS_ISFACT, false);
			if(bIsFact_InterestIntervalDays)
			{
				//��ʵ����������
				intervalDaysFlag = 1;
			}
			else
			{
				//��ÿ��30�����
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
			//��λ����
			info.setClientName(pi.getBorrowClientName());
			//ί�е�λ����(������2004/04/12��Ϊ�˻�����)
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
					//info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
					//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
		            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
						//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
			            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
				//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
	            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
				normalInterestSum = PrintSubsectionInfo.getNormalInterestSum();
				//����ֶ���ϢEnd
			}
			//�⻹��Ϣ
			double remitInterest = DataFormat.formatDouble(normalInterestSum)-DataFormat.formatDouble(pi.getRealInterest());
			if (remitInterest > 0)
			{
				//�⻹��Ϣ��Ϣ����
				//�⻹��Ϣ��Ϣ����
				//�⻹��Ϣ����
				//�⻹������ʾΪ��
				//�⻹����
				//�⻹��Ϣ	
				info.setRemitInterest(DataFormat.formatDisabledAmount(remitInterest));
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
					info.setCompoundInterestDay((getIntervalDays(pi.getCompoundInterestStart(), pi.getCompoundInterestEnd(), intervalDaysFlag) + 1) + "");
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
					info.setOverInterestDay((getIntervalDays(pi.getOverDueStart(), pi.getOverDueEnd(), intervalDaysFlag) + 1) + "");
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
				info.setCommissionFeeDay((getIntervalDays(pi.getCommissionStart(), pi.getCommissionFeeEnd(), intervalDaysFlag) + 1) + "");
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
				info.setAssureFeeDay((getIntervalDays(pi.getSuretyFeeStart(), pi.getSuretyFeeEnd(), intervalDaysFlag) + 1) + "");
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
						DataFormat.formatDouble(pi.getRealInterest())
							+ DataFormat.formatDouble(pi.getRealCompoundInterest())
							+ DataFormat.formatDouble(pi.getRealOverDueInterest())),
					pi.getCurrencyID()));
			if (DataFormat.formatDouble(pi.getRealInterest()) + DataFormat.formatDouble(pi.getRealCompoundInterest()) + DataFormat.formatDouble(pi.getRealOverDueInterest())
				== 0.0)
			{
				info.setTotalInterest("0.00");
			}
			//��Ϣ�˻���
			//info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getPayInterestAccountID()));
			//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
            {
            	info.setInterestAccountNo(NameRef.getOriginalAcctNoByAcctID(pi.getPayInterestAccountID()));
            }
            else
            {
            	info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getPayInterestAccountID()));
            }
			//��Ӧ�Ļ����˻���
			//info.setCurrentAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
			//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
            {
            	info.setCurrentAccountNo(NameRef.getOriginalAcctNoByAcctID(pi.getPayAccountID()));
            }
            else
            {
            	info.setCurrentAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
            }
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
			if(pi.getCheckUserID() > 0)
			    info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			else
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
			System.out.println("��������:" + pi.getTransTypeID());
			if (pi.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE)
			{
				System.out.println("�������ͣ���Ӫ");
				 
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
					System.out.println("�������ͣ�ί��");
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
	*  ��Ϣ֪ͨ��(֧ȡ)��ӡ
	* @throws Exception
	*/
	public static void PrintInterestNotice(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			 System.out.println("1111111111111111111111111111111");
			/**
			 * ������Ϣ,�ǰ�ÿ��30�����,���ǰ�ʵ����������
			 * true :��ÿ��30�����;false:��ʵ����������
			 */
			long intervalDaysFlag = -1;
			boolean bIsFact_InterestIntervalDays = false;
			bIsFact_InterestIntervalDays = Config.getBoolean(Config.SETT_INTEREST_INTERVALDAYS_ISFACT, false);
			if(bIsFact_InterestIntervalDays)
			{
				//��ʵ����������
				intervalDaysFlag = 1;
			}
			else
			{
				//��ÿ��30�����
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
			//��λ����
			if (pi.getPayClientID() > 0)
			{
				info.setClientName(NameRef.getClientNameByID(pi.getPayClientID()));
			}
			else
			{
				info.setClientName(pi.getBorrowClientName());
			}
			System.out.println("33333333333333333333333333333333");
			//ί�е�λ����
			info.setConsignClientName(NameRef.getClientNameByID(pi.getConsignClientID()));
			//�˺�����
			info.setAccountType(SETTConstant.AccountType.getName(pi.getAccountTypeID()));
			//���ױ��
			info.setTransNo(pi.getTransNo());
			info.setReceiveAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
			//�˺�
			long lPayAccountID = -1;
			if (pi.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE || pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
			{
				//info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
				//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
	            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
				//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
	            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
	            {
	            	info.setAccountNo(NameRef.getOriginalAcctNoByAcctID(pi.getPayAccountID()));
	            }
	            else
	            {
	            	info.setAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
	            }
				lPayAccountID = pi.getPayAccountID();
			}
			//������Ϣ
			//����ֶ���Ϣ
			SubsectionInterest dao = new SubsectionInterest();
			SubsectionDateInfo PrintSubsectionInfo = new SubsectionDateInfo();
			PrintSubsectionInfo =
				dao.getSubsectionInterest(lPayAccountID, -1, pi.getLoanNoteID(), pi.getLatestInterestClearDate(), DataFormat.getPreviousDate(pi.getInterestClearDate()));
			info.setNormalInterestDateStart(PrintSubsectionInfo.getPrintStartDate()); //������Ϣ��ʼ����
			info.setNormalInterestDateEnd(PrintSubsectionInfo.getPrintEndDate());
			info.setNormalInterestAmount(PrintSubsectionInfo.getPrintBalance()); //������Ϣ����
			if (PrintSubsectionInfo.getPrintInterestRate() != null && PrintSubsectionInfo.getPrintInterestRate().length() > 0)
			{
				info.setNormalInterestRate(DataFormat.formatRate(Double.parseDouble(PrintSubsectionInfo.getPrintInterestRate()))); //������Ϣ��
			}
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
					info.setCompoundInterestDay((getIntervalDays(pi.getCompoundInterestStart(), pi.getInterestStartDate(), intervalDaysFlag)) + "");
				}
				info.setCompoundInterestAmount("&nbsp;");
				//��������
				info.setCompoundInterestRate(DataFormat.formatRate(pi.getCompoundRate()));
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
					info.setOverInterestDay((getIntervalDays(pi.getOverDueStart(), pi.getInterestClearDate(), intervalDaysFlag)) + "");
				}
				//���ڷ�Ϣ����
				info.setOverInterestAmount(DataFormat.formatDisabledAmount(pi.getOverDueAmount()));
				if (pi.getOverDueAmount() == 0.00)
				{
					info.setOverInterestAmount("0.00");
				}
				//���ڷ�Ϣ����
				info.setOverInterestRate(DataFormat.formatRate(pi.getOverDueRate()));
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
				info.setCommissionFeeDay((getIntervalDays(pi.getCommissionStart(), pi.getInterestClearDate(), intervalDaysFlag)) + "");
				//��������Ϣ����		
				info.setCommissionFeeAmount(DataFormat.formatDisabledAmount(pi.getAmount()));
				if (pi.getAmount() == 0.00)
				{
					info.setCommissionFeeAmount("0.00");
				}
				//����������				
				System.out.print("========���������ʣ�" + pi.getCommissionRate());
				info.setCommissionFeeRate(DataFormat.formatRate(pi.getCommissionRate()));
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
				info.setAssureFeeDay((getIntervalDays(pi.getSuretyFeeStart(), pi.getInterestClearDate(), intervalDaysFlag)) + "");
				//��������Ϣ����		
				info.setAssureFeeAmount(DataFormat.formatDisabledAmount(pi.getAmount()));
				if (pi.getAmount() == 0.0)
				{
					info.setAssureFee("0.00");
				}
				//����������
				info.setAssureFeeRate(DataFormat.formatRate(pi.getSuretyFeeRate() ));
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
						DataFormat.formatDouble(pi.getRealInterest())
							+ DataFormat.formatDouble(pi.getRealCompoundInterest())
							+ DataFormat.formatDouble(pi.getRealOverDueInterest())),
					pi.getCurrencyID()));
			if (DataFormat.formatDouble(pi.getRealInterest()) + DataFormat.formatDouble(pi.getRealCompoundInterest()) + DataFormat.formatDouble(pi.getRealOverDueInterest())
				== 0.0)
			{
				info.setTotalInterest("0.00");
			}
			//��Ϣ�˻���
			//info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getPayInterestAccountID()));
			//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
            {
            	info.setInterestAccountNo(NameRef.getOriginalAcctNoByAcctID(pi.getPayInterestAccountID()));
            }
            else
            {
            	info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getPayInterestAccountID()));
            }
			//��Ӧ���˻���
			//info.setCurrentAccountNo(NameRef.getAccountNoByID(lPayAccountID));
			//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
            {
            	info.setCurrentAccountNo(NameRef.getOriginalAcctNoByAcctID(lPayAccountID));
            }
            else
            {
            	info.setCurrentAccountNo(NameRef.getAccountNoByID(lPayAccountID));
            }
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
			System.out.println("��������:" + pi.getTransTypeID());
			if (pi.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE)
			{
				System.out.println("�������ͣ���Ӫ");
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
     *  ֪ͨ����֤ʵ���ӡ 
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
			//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
				sInsert = pi.getNoticeDay() - 10000 + "��";
			}
			else
			{
				sInsert = pi.getNoticeDay() + "����";
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
				sInterval=String.valueOf(piTemp.getDepositTerm())+"����";
			}else{
				sInterval="0����";
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
     *  ���ڴ���֤ʵ���ӡ 
     * @throws Exception
     */
	public static void PrintFixedDepositOpen(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			ShowFixedDepositOpenInfo info = new ShowFixedDepositOpenInfo();
			info.setAccountName(NameRef.getAccountNameByID(pi.getReceiveAccountID()));
			//info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
			//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
            if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )//������Ϻ���Ŀ
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
				sInterval = pi.getFixedDepositTerm() - 10000 + "��";
			}
			else
			{
				sInterval = pi.getFixedDepositTerm() + "����";
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
	 * @param ��֤���Ϣ��ӡ
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
			info.setDepositTypeName("��֤���Ϣ");
//			info.setDepositBillNo(pi.getBillNo());
			info.setAmount1(DataFormat.formatAmount(pi.getBaseBalance()));
			System.out.println("ȡֵinterest:%%%"+pi.getInterest());
			info.setInterest1("��" + DataFormat.formatAmount(pi.getInterest()));
			info.setRate1(DataFormat.formatAmountUseZero(pi.getRate(),6));
			System.out.println("����1" + info.getRate1());
			
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
     *  �����Ϣ�Ƹ�֪ͨ��(����֪ͨ��� ���ڴ����ջ�ƾ֤) 
     * @throws Exception
     */
	public static void PrintDepositInterest(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			/**
			 * ������Ϣ,�ǰ�ÿ��30�����,���ǰ�ʵ����������
			 * true :��ÿ��30�����;false:��ʵ����������
			 */
			long intervalDaysFlag = -1;
			boolean bIsFact_InterestIntervalDays = false;
			bIsFact_InterestIntervalDays = Config.getBoolean(Config.SETT_INTEREST_INTERVALDAYS_ISFACT, false);

			if(bIsFact_InterestIntervalDays)
			{
				//��ʵ����������
				intervalDaysFlag = 1;
			}
			else
			{
				//��ÿ��30�����
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
			//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
            if( Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )
            {
                info.setAccountNo(NameRef.getOriginalAcctNoByAcctID(pi.getPayAccountID()));
            }
            else
            {
            	info.setAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
            }
			
			info.setClientName(NameRef.getClientNameByAccountID(pi.getPayAccountID()));
			//ȡ�ϴμ������ڣ���Ϊ�������ڵĽ������ڡ���������������������������
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
			//����������������������������������
			info.setDepositBillNo(pi.getBillNo());
			/*
			//�������Ϣ֮���ת��[���ڡ�֪ͨ]
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
			if (pi.getCurrentInterest() != 0.0) //�л�����Ϣ
			{
				if ( pi.getInterestStartDate() != null && pi.getEndDate() != null && pi.getInterestStartDate().before(pi.getEndDate()) ) //����Ϣ������ֹ����֮ǰ������ǰ֧ȡ
				{
					//����1��������Ϣ��������Ϣ���ʣ���ʼ���ڣ��������ڣ�����
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
						//����֧ȡ&��������
						info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), intervalDaysFlag)) + "");
					}
					else
						if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
						{
							//֪֧ͨȡ
							info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), intervalDaysFlag)) + "");
						}
						else
						{
							info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), intervalDaysFlag) + 1) + "");
						}
					//����2��������Ϣ(������)��������Ϣ����(��)����ʼ���ڣ��������ڣ�����
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
							//����֧ȡ&��������
							info.setDayNumber2((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), intervalDaysFlag)) + "");
						}
						else
							if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
							{
								//֪֧ͨȡ
								info.setDayNumber2((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), intervalDaysFlag)) + "");
							}
							else
							{
								info.setDayNumber2((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), 1) + intervalDaysFlag) + "");
							}
					}
					//��Ϣ�ϼ�
					info.setTotalInterest(
						DataFormat.formatAmount(DataFormat.formatDouble(DataFormat.formatDouble(pi.getCurrentInterest()) + DataFormat.formatDouble(pi.getOtherInterest()))));
				}
				else //����Ϣ������ֹ����֮��
					{
					//����1����Ϣ֧������Ϣ֧�����ʣ���ʼ���ڣ��������ڣ�����
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
						//����֧ȡ&��������
						info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), intervalDaysFlag)) + "");
					}
					else
						if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
						{
							//֪֧ͨȡ
							info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), intervalDaysFlag)) + "");
						}
						else
						{
							info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), intervalDaysFlag) + 1) + "");
						}
					//����2��������Ϣ��������Ϣ���ʣ���ʼ���ڣ��������ڣ�����
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
						//����֧ȡ&��������
						info.setDayNumber2((getIntervalDays(pi.getEndDate(), pi.getInterestStartDate(), intervalDaysFlag)) + "");
					}
					else
						if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
						{
							//֪֧ͨȡ
							info.setDayNumber2((getIntervalDays(pi.getEndDate(), pi.getInterestStartDate(), intervalDaysFlag)) + "");
						}
						else
						{
							info.setDayNumber2((getIntervalDays(pi.getEndDate(), pi.getInterestStartDate(), intervalDaysFlag) + 1) + "");
						}
					if (pi.getOtherInterest() != 0.0) //������Ϣ
					{
						//����3��������Ϣ(������)��������Ϣ����(��)����ʼ���ڣ��������ڣ�����
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
							//����֧ȡ&��������
							info.setDayNumber3((getIntervalDays(pi.getEndDate(), pi.getInterestStartDate(), intervalDaysFlag)) + "");
						}
						else
							if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
							{
								//֪֧ͨȡ
								info.setDayNumber3((getIntervalDays(pi.getEndDate(), pi.getInterestStartDate(), intervalDaysFlag)) + "");
							}
							else
							{
								info.setDayNumber3((getIntervalDays(pi.getEndDate(), pi.getInterestStartDate(), intervalDaysFlag) + 1) + "");
							}
					}
					//��Ϣ�ϼ�
					info.setTotalInterest(DataFormat.formatAmount(DataFormat.formatDouble(pi.getPayableInterest())));
				}
			}
			else //û�л�����Ϣ
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
				//����1����Ϣ֧������Ϣ֧�����ʣ���ʼ���ڣ��������ڣ�����
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
					//����֧ȡ&��������
					info.setDayNumber1((getIntervalDays(pi.getStartDate(), tsEndDate, intervalDaysFlag)) + "");
				}
				else {
					if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
					{
						//֪֧ͨȡ
						info.setDayNumber1((getIntervalDays(pi.getStartDate(), tsEndDate, intervalDaysFlag)) + "");
					}
					else
					{
						info.setDayNumber1((getIntervalDays(pi.getStartDate(), tsEndDate, intervalDaysFlag) + 1) + "");
					}
				}
				//��Ϣ�ϼ�
				info.setTotalInterest(DataFormat.formatAmount(DataFormat.formatDouble(pi.getPayableInterest())));
				//����2��������Ϣ(������)��������Ϣ����(��)����ʼ���ڣ��������ڣ�����
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
						//����֧ȡ&��������
						info.setDayNumber2((getIntervalDays(pi.getStartDate(), pi.getInterestStartDate(), intervalDaysFlag)) + "");
					} else {
						if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
						{
							//֪֧ͨȡ
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
				//����Ƕ�������
				//������Ϣ
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
						//��������
						//ȡ�ϴμ������ڣ���Ϊ�������ڵĽ�������
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
						//����֧ȡ
						info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), intervalDaysFlag)) + "");
					}
					else
						if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
						{
							//��������
							info.setDayNumber1((getIntervalDays(pi.getStartDate(), tsLastPreDraw, intervalDaysFlag)) + "");
						}
						else
							if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
							{
								//֪֧ͨȡ
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
						//������ڼ�����Ϣ������Ϣ֧�����ϴν�Ϣ��Ϊ�ϴμ�����Ϣ������
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
							//����֧ȡ
							info.setDayNumber2((getIntervalDays(pi.getStartDate(), pi.getEndDate(), intervalDaysFlag)) + "");
						} else {
							if (pi.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
							{
								//��������
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
									//֪֧ͨȡ
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
							//����֧ȡ&��������
							info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), intervalDaysFlag)) + "");
						}
						else
							if (pi.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
							{
								//֪֧ͨȡ
								info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), intervalDaysFlag)) + "");
							}
							else
							{
								info.setDayNumber1((getIntervalDays(pi.getStartDate(), pi.getEndDate(), intervalDaysFlag) + 1) + "");
							}
					}
				//��Ϣ�ϼ�
				info.setTotalInterest(
					DataFormat.formatAmount(DataFormat.formatDouble(DataFormat.formatDouble(pi.getRealInterestReceivable()) + DataFormat.formatDouble(pi.getPayableInterest()))));
			}
			info.setTotalInterestChinese(ChineseCurrency.showChinese(info.getTotalInterest(), pi.getCurrencyID()));
			//��Ϣ�˺�
			//info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getReceiveInterestAccountID()));
			//Ϊ����ƹ��¼ӣ���ʾ���˺š�����Ӱ��������Ŀ�����û���¾��˻���Ӧ��ϵ���򷵻����˺ţ���ͨ�����˺ţ�
			if(Config.getBoolean( ConfigConstant.SETT_ACCOUNT_ISOLDACCOUNT,false ) )
            {
                info.setInterestAccountNo(NameRef.getOriginalAcctNoByAcctID(pi.getReceiveInterestAccountID()));
            }
            else
            {
			    info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getReceiveInterestAccountID()));
            }
			
			//�������
			info.setDepositTypeName(SETTConstant.TransactionType.getName(pi.getTransTypeID()));
			if (info.getDepositTypeName().indexOf("����") >= 0)
			{
				info.setDepositTypeName("���ڴ��");
			}
			else if (info.getDepositTypeName().indexOf("֪ͨ") >= 0)
			{
				info.setDepositTypeName("֪ͨ���");
			} else 
			{
				info.setDepositTypeName("��֤����");
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
			//ί�з��ͻ�
			info.setConsignClientName(NameRef.getClientNameByID(contractInfo.getClientID()));
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
				DataFormat.formatDisabledAmount(
					DataFormat.formatDouble(pi.getInterest()) + DataFormat.formatDouble(pi.getCompoundInterest()) + DataFormat.formatDouble(pi.getOverDueInterest())));
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
			
			//modify by xwhe 2008-12-03
			if(pi.getAmount()>0.0)
			{
			String chiLoanAmount = "";
			PrintVoucher  pVoucher= new PrintVoucher();
			chiLoanAmount = pVoucher.getChineseFormatAmount(pi.getLoanBalance(),pi.getCurrencyID());
			info.setChinLoanAmount(chiLoanAmount+"(��д)");
			}
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
				strMonth = DataFormat.formatInt(Long.valueOf(DataFormat.getRealMonthString(pi.getStartDate())).longValue(), 2);
				strDay = DataFormat.formatInt(Long.valueOf(DataFormat.getDayString(pi.getStartDate())).longValue(), 2);
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
				strMonth = DataFormat.formatInt(Long.valueOf(DataFormat.getRealMonthString(pi.getEndDate())).longValue(), 2);
				strDay = DataFormat.formatInt(Long.valueOf(DataFormat.getDayString(pi.getEndDate())).longValue(), 2);
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
            //��ţ��ţ�
			info.setNoticeNo(strMonth+pi.getFormNo().substring(2, pi.getFormNo().length()));
			
			
			//��Ϣ��
			if( pi != null && pi.getLatestInterestClearDate() != null )
			{
				strYear = DataFormat.getYearString(pi.getLatestInterestClearDate());
				strMonth = DataFormat.formatInt(Long.valueOf(DataFormat.getRealMonthString(pi.getLatestInterestClearDate())).longValue(), 2);
				strDay = DataFormat.formatInt(Long.valueOf(DataFormat.getDayString(pi.getLatestInterestClearDate())).longValue(), 2);
				strTemp = "<u>&nbsp;" + strYear + "&nbsp;</u>��<u>&nbsp;" + strMonth + "&nbsp;</u>��<u>&nbsp;" + strDay + "&nbsp;</u>��";
				info.setInterestStartDate(strTemp);
			}
			else
			{
				info.setInterestStartDate("<u>&nbsp;&nbsp;&nbsp;&nbsp;</u>��<u>&nbsp;&nbsp;&nbsp;&nbsp;</u>��<u>&nbsp;&nbsp;&nbsp;&nbsp;</u>��");
			}
			
			//��Ϣ����
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
					strMonth = DataFormat.formatInt(Long.valueOf(DataFormat.getRealMonthString(contractInfo.getLoanStart())).longValue(), 2);
					strDay = DataFormat.formatInt(Long.valueOf(DataFormat.getDayString(contractInfo.getLoanStart())).longValue(),2);
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
				strMonth = DataFormat.formatInt(Long.valueOf(DataFormat.getRealMonthString(pi.getClearInterestDate())).longValue(), 2);
				strDay = DataFormat.formatInt(Long.valueOf(DataFormat.getDayString(pi.getClearInterestDate())).longValue(), 2);
				strTemp = "<u>&nbsp;" + strYear + "&nbsp;</u>��<u>&nbsp;" + strMonth + "&nbsp;</u>��<u>&nbsp;" + strDay + "&nbsp;</u>��";
				info.setDateDuePay(strTemp);
				//modified by ylguo
				String strDayTemp = (Integer.parseInt(strDay)-1)+"";
				String strPreTemp = "<u>&nbsp;" + strYear + "&nbsp;</u>��<u>&nbsp;" + strMonth + "&nbsp;</u>��<u>&nbsp;" + strDayTemp + "&nbsp;</u>��";
				info.setPreInterestDate(strPreTemp);
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
					DataFormat.formatDouble(
						DataFormat.formatDouble(pi.getInterest()) + DataFormat.formatDouble(pi.getCompoundInterest()) + DataFormat.formatDouble(pi.getOverDueInterest()))));
			//�����
			info.setLoanAmount(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getAmount())));
			//�������
			String ChiLoanBalance ="";
            //moidfy by xwhe 2008-12-03
			PrintVoucher  pVoucher= new PrintVoucher();
			if (pi.getLoanBalance() > 0)
			{
				if (pi.getLoanTypeID() == LOANConstant.LoanType.YT || pi.getLoanTypeID() == LOANConstant.LoanType.YT) //���Ŵ������Ϊ�ſ�������ǲ���˾���ǲ������
				{
					System.out.println("���Ŵ������Ϊ�ſ�������ǲ���˾���ǲ������");
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
			//��������дmoidfy by xwhe 2008-12-03
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
			info.setChiLoanBalance(ChiLoanBalance+"(��д)");
			//��ͬ����
			info.setContractRate(String.valueOf(DataFormat.formatDouble(pi.getContractRate(), 6)));
			//ִ������
			info.setExcecuteRate(String.valueOf(DataFormat.formatDouble(pi.getExecuteRate(), 6)));
			//�������ʵ�������
			if (pi.getAdjustRateDate() != null)
			{
				strYear = DataFormat.getYearString(pi.getAdjustRateDate());
				strMonth = DataFormat.formatInt(Long.valueOf(DataFormat.getRealMonthString(pi.getAdjustRateDate())).longValue(), 2);
				strDay = DataFormat.formatInt(Long.valueOf(DataFormat.getDayString(pi.getAdjustRateDate())).longValue(), 2);
				strTemp = "<u>&nbsp;" + strYear + "&nbsp;</u>��<u>&nbsp;" + strMonth + "&nbsp;</u>��<u>&nbsp;" + strDay + "&nbsp;</u>��";
				info.setDateRateModify(strTemp);
			}
			else
			{
				info.setDateRateModify("<u>&nbsp;&nbsp;&nbsp;&nbsp;</u>��<u>&nbsp;&nbsp;&nbsp;&nbsp;</u>��<u>&nbsp;&nbsp;&nbsp;&nbsp;</u>��");
			}
			
			//��Ϣ��
			if( pi != null && pi.getLatestInterestClearDate() != null )
			{
				strYear = DataFormat.getYearString(pi.getLatestInterestClearDate());
				strMonth = DataFormat.formatInt(Long.valueOf(DataFormat.getRealMonthString(pi.getLatestInterestClearDate())).longValue(), 2);
				strDay = DataFormat.formatInt(Long.valueOf(DataFormat.getDayString(pi.getLatestInterestClearDate())).longValue(), 2);
				strTemp = "<u>&nbsp;" + strYear + "&nbsp;</u>��<u>&nbsp;" + strMonth + "&nbsp;</u>��<u>&nbsp;" + strDay + "&nbsp;</u>��";
				info.setInterestStartDate(strTemp);
			}
			else
			{
				info.setInterestStartDate("<u>&nbsp;&nbsp;&nbsp;&nbsp;</u>��<u>&nbsp;&nbsp;&nbsp;&nbsp;</u>��<u>&nbsp;&nbsp;&nbsp;&nbsp;</u>��");
			}
			
			//��Ϣ����
			if( pi != null && pi.getLatestInterestClearDate() != null )
			{
				info.setIntervalDays(""+ DataFormat.getIntervalDays( pi.getLatestInterestClearDate() ,DataFormat.getNextDate( pi.getClearInterestDate(),-1) ) );
			}
			else
			{
				info.setIntervalDays( "&nbsp;&nbsp;&nbsp;&nbsp;");
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
				strMonth = DataFormat.formatInt(Long.valueOf(DataFormat.getRealMonthString(pi.getStartDate())).longValue(), 2);
				strDay = DataFormat.formatInt(Long.valueOf(DataFormat.getDayString(pi.getStartDate())).longValue(), 2);
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
				strMonth = DataFormat.formatInt(Long.valueOf(DataFormat.getRealMonthString(pi.getEndDate())).longValue(), 2);
				strDay = DataFormat.formatInt(Long.valueOf(DataFormat.getDayString(pi.getEndDate())).longValue(), 2);
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
						DataFormat.formatDouble(
							DataFormat.formatDouble(pi.getInterest()) + DataFormat.formatDouble(pi.getOverDueInterest()) + DataFormat.formatDouble(pi.getCompoundInterest()))));
			//�������ڣ��꣩
			//	strTemp = DataFormat.formatDate(pi.getExecuteDate());
			//modify by xwhe 2008-12-03
			strTemp = Env.getSystemDateString();
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
			
            //��ţ��ţ�
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
	
//	��ʽ�����,�����ѧ����������������
	public String getChineseFormatAmount(double amount, long currencyID) throws Exception
	{
		String strChineseAmount = "��Ԫ";
		
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
			boolean bIsFirst = false;
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
					if (pi.getAdjustRateDate().after(pi.getInterestStartDate())
						&& (pi.getAdjustRateDate().before(pi.getClearInterestDate()) || pi.getAdjustRateDate() == pi.getClearInterestDate()))
					{
						//������������Ϣ�պͽ�Ϣ��֮��
						//ִ������
						tmpPrintInfo.setExecuteRate(pi.getExecuteRateNew());
						//�������ʵ�������
						tmpPrintInfo.setAdjustRateDate(pi.getAdjustRateDate());
						//��������Ϣ
						tmpPrintInfo.setExecuteRateNew(pi.getExecuteRateNew());
					}
					else
						if (!pi.getAdjustRateDate().after(pi.getInterestStartDate()))
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
							+ "<table border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"In1-table1\" width=\"640\">"
							+ "<tr>"
							+ "<td  align=\"center\" class=\"In1-td-rightbottom\">�ſ�֪ͨ�����</td>"
							+ "<td  align=\"center\" class=\"In1-td-rightbottom\">�ſ���</td>"
							+ "<td  align=\"center\" class=\"In1-td-rightbottom\">�ſ�����</td>"
							+ "<td  align=\"center\" class=\"In1-td-rightbottom\">��Ϣ��</td>"
							+ "<td  align=\"center\" class=\"In1-td-rightbottom\">ֹϢ��</td>"
							+ "<td  align=\"center\" class=\"In1-td-rightbottom\">���ʣ�</td>"
							+ "<td  align=\"center\" class=\"In1-td-rightbottom\">Ӧ����Ϣ</td>"
							+ "<td  align=\"center\" class=\"In1-td-rightbottom\">Ӧ������</td>"
							+ "<td  align=\"center\" class=\"In1-td-bottom\">Ӧ����Ϣ���úϼ�</td>"
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
						+ "��"
						+ DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getInterest()))
						+ "</font></td>"
						+ "<td  class=\"In1-td-rightbottom\" align=\"right\"><font style=\"font-size:12px\">"
						+ "��"
						+ DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getCompoundInterest()))
						+ "</font></td>"
						+ "<td  class=\"In1-td-bottom\" align=\"right\"><font style=\"font-size:12px\">"
						+ "��"
						+ DataFormat.formatDisabledAmount(DataFormat.formatDouble(DataFormat.formatDouble(pi.getInterest()) + DataFormat.formatDouble(pi.getCompoundInterest())))
						+ "</font></td>"
						+ "</tr>");
			} //end for
			out.println(
				"<tr>"
					+ "<td  class=\"In1-td-right\" align=\"center\"><font style=\"font-size:12px\"><B>�ϼ�</B></font></td>"
					+ "<td class=\"In1-td-right\" align=\"right\"><font style=\"font-size:12px\">"
					+ "��"
					+ DataFormat.formatDisabledAmount(dSumLoanAmount)
					+ "</font></td>"
					+ "<td class=\"In1-td-right\"><font style=\"font-size:12px\">&nbsp;</font></td>"
					+ "<td class=\"In1-td-right\"><font style=\"font-size:12px\">&nbsp;</font></td>"
					+ "<td class=\"In1-td-right\"><font style=\"font-size:12px\">&nbsp;</font></td>"
					+ "<td class=\"In1-td-right\"><font style=\"font-size:12px\">&nbsp;</font></td>"
					+ "<td class=\"In1-td-right\" align=\"right\"><font style=\"font-size:12px\">"
					+ "��"
					+ DataFormat.formatDisabledAmount(dSumInterest)
					+ "</font></td>"
					+ "<td class=\"In1-td-right\" align=\"right\"><font style=\"font-size:12px\">"
					+ "��"
					+ DataFormat.formatDisabledAmount(dSumCompoundInterest)
					+ "</font></td>"
					+ "<td align=\"right\"><font style=\"font-size:12px\">"
					+ "��"
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
					strMonth = DataFormat.formatInt(Long.valueOf(DataFormat.getRealMonthString(contractInfo.getLoanStart())).longValue(), 2);
					strDay = DataFormat.formatInt(Long.valueOf(DataFormat.getDayString(contractInfo.getLoanStart())).longValue(), 2);
					strTemp = "<u>&nbsp;" + strYear + "&nbsp;</u>��<u>&nbsp;" + strMonth + "&nbsp;</u>��<u>&nbsp;" + strDay + "&nbsp;</u>��";
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
					strMonth = DataFormat.formatInt(Long.valueOf(DataFormat.getRealMonthString(contractInfo.getLoanEnd())).longValue(), 2);
					strDay = DataFormat.formatInt(Long.valueOf(DataFormat.getDayString(contractInfo.getLoanEnd())).longValue(), 2);
					strTemp = "<u>&nbsp;" + strYear + "&nbsp;</u>��<u>&nbsp;" + strMonth + "&nbsp;</u>��<u>&nbsp;" + strDay + "&nbsp;</u>��";
					info.setDateEnd(strTemp);
					
					Timestamp dtTemp = DataFormat.getNextDate(contractInfo.getLoanEnd(), 14);
					strYear = DataFormat.getYearString(dtTemp);
					strMonth = DataFormat.formatInt(Long.valueOf(DataFormat.getRealMonthString(dtTemp)).longValue(), 2);
					strDay = DataFormat.formatInt(Long.valueOf(DataFormat.getDayString(dtTemp)).longValue(), 2);
					strTemp = "<u>&nbsp;" + strYear + "&nbsp;</u>��<u>&nbsp;" + strMonth + "&nbsp;</u>��<u>&nbsp;" + strDay + "&nbsp;</u>��";
					
					info.setNextTwoWeekDateEnd(strTemp);
				}
			}
			//��������--ȡ��Ϣ��
			if (pi.getClearInterestDate() != null)
			{
				strYear = DataFormat.getYearString(pi.getClearInterestDate());
				strMonth = DataFormat.formatInt(Long.valueOf(DataFormat.getRealMonthString(pi.getClearInterestDate())).longValue(), 2);
				strDay = DataFormat.formatInt(Long.valueOf(DataFormat.getDayString(pi.getClearInterestDate())).longValue(), 2);
				strTemp = "<u>&nbsp;" + strYear + "&nbsp;</u>��<u>&nbsp;" + strMonth + "&nbsp;</u>��<u>&nbsp;" + strDay + "&nbsp;</u>��";
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
					DataFormat.formatDouble(
						DataFormat.formatDouble(pi.getInterest()) + DataFormat.formatDouble(pi.getOverDueInterest()) + DataFormat.formatDouble(pi.getCompoundInterest()))));
			//δ��������
			info.setOwnComissionFee(DataFormat.formatDisabledAmount(pi.getCommission()));

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
			if (pi.getLoanTypeID() == LOANConstant.LoanType.YT || pi.getLoanTypeID() == LOANConstant.LoanType.YT)
			{
				BankLoanQuery bankLoanQuery = new BankLoanQuery();
				double dSynRate = bankLoanQuery.findRateByFormID(pi.getLoanNoteID());
				info.setLoanAmount(DataFormat.formatDisabledAmount(subAccountLoanInfo.getOpenAmount() * 100 / dSynRate));
				info.setOwnAmount(DataFormat.formatDisabledAmount(subAccountLoanInfo.getBalance() * 100 / dSynRate));
			}
			
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
			//20121116 wcl ���˵�ɾ�������˻�
			subAccountLoanInfo.setStatusID(1);
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
					DataFormat.formatDouble(
						(DataFormat.formatDouble(pi.getInterest()) + DataFormat.formatDouble(pi.getOverDueInterest()) + DataFormat.formatDouble(pi.getCompoundInterest())))));
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
			info.setCurrencyID(pi.getCurrencyID());
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
				DataFormat.formatDisabledAmount(
					DataFormat.formatDouble(pi.getInterest()) + DataFormat.formatDouble(pi.getOverDueInterest()) + DataFormat.formatDouble(pi.getCompoundInterest())));
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
			////////////////////////////////////////////////////////////////
			//���Ŵ��������ڱ�Ϣ�ϼ�֮ǰ
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
     *@description:��ӡ������ƾ֤
     *��һ�� �������и��º��˻ص�λ
     *�ڶ��� ����������������Ʊ
     *������ ����������Ӫҵ���봫Ʊ
     *void
     *@param TransCommissionResultInfo
     *@param JspWriter
     *@throws Exception
     *Notes: Ϊ�Ϻ�������Ŀ���
     */
    public static void PrintCommission( TransCommissionResultInfo info,JspWriter out ) throws Exception
    {
        try 
        {   
            //��ӡ����
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
            
            //���������
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
	/**
	 *	���е���ת��ƾ֤��ӡ
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
	 * �����˻�������ϸ��ӡ
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
	/*�������ϸҳ���ȫ���ӡ
	 * 
	 * 
	 * 
	 * @author feiye
	 *
	 * TODO To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Style - Code Templates
	 */
	public static void printOnePayMutiRecevice(Collection coll, JspWriter out) throws Exception {
		
		//һ���������INFO��Ϣ
		TransOnePayMultiReceiveInfo transOnePayMultiReceiveInfo=null;
		double sumAmount=0.0;
		
		if(coll!=null && coll.size()>0){
			for(int i=0;i<coll.size();i++){
				transOnePayMultiReceiveInfo=(TransOnePayMultiReceiveInfo)((ArrayList)coll).get(i);
				sumAmount=sumAmount+transOnePayMultiReceiveInfo.getAmount();
			}
		}else{
			System.out.println("�����û����ϸ�嵥���ݿɹ���ӡ!");
			return;
		}
		
		try
		{
			for(int i=0;i<10;i++)
				System.out.println("������Ƕ������ȫ����ϸҳ��!");
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
						+ "       ��&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;(    )</font></font></strong> ��</strong></TD>"
						+ "    <TD colspan=\"2\" width=\"180\" align=\"left\" nowrap>");
			out.print(
					"</TD>"
						+ "</TR> "
						+ "<TR> 	<TD width=\"90\">&nbsp;	</TD><TD class=\"In1-td-top\" colspan=\"3\" width=\"450\">&nbsp;	</TD><TD width=\"110\">&nbsp;	</TD></TR> 	"
						+ "	</TABLE> ");
			
			out.print("	<TABLE width=\"600\"> "
					+ " <TR> "
					+ "		<TD align=\"left\" width=\"50%\">"
                    + " �ܼƱ���: "
					+ coll.size()
					+ " </TD> "
					+ "	<TD width=\"50%\" align=\"left\">���ڣ�"
					+ Env.getSystemDateString()
					+ "	</TD> "
					+ "</TR> "
					+ " <TR> "
					+ "		<TD align=\"left\" width=\"50%\">"
                    + " �ܼƽ��: "
					+ sumAmount
					+ " </TD> "
					+ "	<TD width=\"50%\" align=\"left\">ʱ�䣺"
					+ Env.getSystemDateTime().toString().substring(11,19)
					+ "	</TD> "
					+ "</TR> "
					+ "	</TABLE>");
			//ѭ����
			out.print("	<TABLE width=\"600\"> "
					+ " <TR> "
					+ "		<TD align=\"left\" width=\"25%\">"
                    + " "
					+ " </TD> "
					+ "		<TD align=\"left\" width=\"25%\">"
                    + " ��  �� "
					+ " </TD> "
					+ "	<TD width=\"25%\" align=\"left\">��  ��"
					+ "	</TD> "
					+ "	<TD width=\"25%\" align=\"left\">��  ע"
					+ "	</TD> "
					+ "</TR> ");
			
			out.print(" <TR> "
					+"<TD colspan=\"2\" width=\"180\" align=\"left\" nowrap></TD>"
					+" </TR>"
				);
					
					
			for(int i=0;i<coll.size();i++){
				transOnePayMultiReceiveInfo=(TransOnePayMultiReceiveInfo)((ArrayList)coll).get(i);
				
				System.out.println("�˻�ID��"+transOnePayMultiReceiveInfo.getAccountID());
				System.out.println("�˻���"+transOnePayMultiReceiveInfo.getAmount());
				System.out.println("�˻�ժҪ��"+transOnePayMultiReceiveInfo.getAbstract());
				
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
			System.out.println("�������쳣!");
			e.printStackTrace();
		}
	}
	
	/**
     *  �ַ���ӡ����ƾ֤��   2006.4.10 add by feiye
     * @throws Exception
     */
	public static void PrintRepairVoucher(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			System.out.println("��ʼ�ַ�����ƾ֤����.........��pi��װ��rvInfo(��ʼ)");
			
			//��ʼ��һ��������Ϣ�����		(Ȼ��printInfo�������Ϣ�����װ����Ҫ��ӡ������ƾ֤��������Ｔ��)
			ShowRepairVoucher rvInfo = new ShowRepairVoucher();
			
			//Сд���
			rvInfo.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getAmount()));
			//��д���
			rvInfo.setChineseAmount(ChineseCurrency.showChinese(DataFormat.formatAmount(pi.getAmount()), pi.getCurrencyID()));
			//������
			rvInfo.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			//������
			rvInfo.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			//�ͻ�����
			rvInfo.setClientName(Env.getClientName());
			
			String strExecuteDate = DataFormat.formatDate(pi.getExecuteDate());
            if (strExecuteDate.length() < 10)
                strExecuteDate = "0000000000";
            String strYear = strExecuteDate.substring(0, 4);
            //��
            rvInfo.setYear(strYear);
            String strMonth = strExecuteDate.substring(5, 7);
            //��
            rvInfo.setMonth(strMonth);
            String strDay = strExecuteDate.substring(8, 10);
            //��
            rvInfo.setDay(strDay);
            //������
            rvInfo.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
            //����ID
            rvInfo.setTranscationTypeName(SETTConstant.TransactionType.getName(pi.getTransTypeID()));
            
            String strPayAccountName = "";
            String strPayAccountNo = "";
            
            //����˻�
            if (pi.getPayAccountID() > 0)
            {
            	 strPayAccountName = NameRef.getAccountNameByID(pi.getPayAccountID());
                System.out.println("�õ���������ƣ�"+strPayAccountName);
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
				//�����������
				PIVo.setPayBankName(NameRef.getBankNameByID(pi.getPayBankID()));
				PIVo.setPayAccountNo(NameRef.getBankAccountCodeByAccountIDandBankID(-1,pi.getPayBankID()));
			}
			if(pi.getReceiveBankID()!=-1){
				//�տ��������
				PIVo.setReceiveBankName(NameRef.getBankNameByID(pi.getReceiveBankID()));
				PIVo.setReceiveAccountNo(NameRef.getBankAccountCodeByAccountIDandBankID(-1,pi.getReceiveBankID()));
			}
             * 
             */
            else if (pi.getPayBankID()!=-1){			//������������
            	System.out.println("////////////////////////////////////////-begin   ����������:"+pi.getPayBankID());
                strPayAccountName = NameRef.getBankNameByID(pi.getPayBankID());
                rvInfo.setPayAccountName(DataFormat.formatString(strPayAccountName.trim()));		//�����˻���
                strPayAccountNo = NameRef.getBankAccountCodeByAccountIDandBankID(-1,pi.getPayBankID());
                rvInfo.setPayAccountNo(DataFormat.formatString(strPayAccountNo));				//�����˻���
                rvInfo.setPayBankName("");			//���������
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
            else //����
            {
            	strPayAccountName = NameRef.getGLTypeDescByID(pi.getPayGL());
                rvInfo.setPayAccountName(strPayAccountName.trim());
                strPayAccountNo = NameRef.getGLTypeNoByID(pi.getPayGL());
                rvInfo.setPayAccountNo(strPayAccountNo);
            }
            //�տ�˻�
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
				//�տ��������
				System.out.println("////////////////////////////////////////-begin   �տ�������:"+pi.getReceiveBankID());
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
            else //����
            {
	            strReceiveAccountName = NameRef.getGLTypeDescByID(pi.getReceiveGL());
	            rvInfo.setReceiveAccountName(strReceiveAccountName);
	            strReceiveAccountNo = NameRef.getGLTypeNoByID(pi.getReceiveGL());
	            rvInfo.setReceiveAccountNo(strReceiveAccountNo);
            }
            System.out.println("��ʼ�ַ�����ƾ֤����.........��pi��װ��rvInfo(����)");
            
			//����ʵ�ʵĴ�ӡ����д�ӡ
			IPrintTemplate.showRepairVoucher1(rvInfo,out);		//����ƾ֤��һ��
			out.println("<br clear=all style='page-break-before:always'>");
			IPrintTemplate.showRepairVoucher2(rvInfo,out);		//����ƾ֤�ڶ���
			
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
	 * ��ӡӦ��������Ϣ֪ͨ��
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
	 * add by keivn(������)2011-08-05
	 * ��ӡ���������ʵ�
	 * @param pi
	 * @param out
	 * @throws Exception
	 */
	public static void printAccountBalance (PrintInfo pi, JspWriter out) throws Exception
	{
	   try
	   {
		//���������
		String strYear = "";
		//���������
		String strMonth = "";
		//���������
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
						+"  <td colspan=\"7\"><div align=\"center\"><strong>�˻����˵���֪</strong></div></td>"
						+"  <td>&nbsp;</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"7\">1.������λ������˶Խ��˶�����ģ����ں˶Խ���� ����������ڴ򡰡̡����������ڡ��Ƚ������������к����</td>"
						+"  <td>&nbsp;</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"7\">&nbsp;&nbsp;�ڴ򡰡̡��������������������ʾ�ڶ��˵��ڶ�������λ�����ģ��ɵ�����������ǩ�º󷵻ء�</td>"
						+"  <td>&nbsp;</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"7\">2.�����Ƿ�����������յ����˵�ʮ���ڷ������˽����</td>"
						+"  <td>&nbsp;</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"7\">3.���ڲ��ˣ���Ϊ�˶���������е��ɴ˴�����������Ρ�</td>"
						+"  <td width=\"20\" rowspan=\"14\">��<br>"
						+"    һ<br>"
						+"    ��<br>"
						+"    :<br>"
						+"    ��<br>"
						+"    ��<br>"
						+"    ��<br>"
						+"    ��<br>"
						+"    ��<br>"
						+"    ��<br>"
						+"    ��<br>"
						+"    &nbsp;<br>"
						+"    </td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"7\">4.�����з��ִ��ˣ�Ӧ��ʱ֪ͨ�������ĺ�ʵ��������Ӧ������</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"7\">5.�յ��˶��˵�������ϸ�˶Ե�λ���ƺ������Ϣ���������ʣ����µ磺010-68373975,010-68373974</td>"
						+"</tr>"
						+"<tr> "
						+"  <td height=\"17\" colspan=\"7\">6.���˵��ڶ�����������º󽻽������ġ�</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"7\">&nbsp;</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"7\"><div align=\"center\">����ƹ������������ι�˾</div></td>"
						+"</tr>"
						+"<tr> "
						+"  <td height=\"20\" colspan=\"7\"> <div align=\"center\">���������˵�</div></td>"
						+"</tr>"
						+"<tr> "
						+"<tr> "
						+"  <td><font  align=\"left\">�˺ţ�</font></td>"
						+"  <td colspan=\"3\" align=\"left\">&nbsp;"
						+pi.getExtAccountNo()+"</td>"
						+"  <td><font  align=\"left\">No.</td>"
						+"  <td colspan=\"3\" align=\"left\">&nbsp;"+pi.getFormNo()+"</td>"
						+"</tr>"
						+"<tr> "
						+"  <td><font align=\"left\">���֣�</font></td>"
						+"  <td colspan=\"3\" align=\"left\">&nbsp;RMB</td>"
						+"  <td><font  align=\"left\">������λ���ƣ�</td>"
						+"  <td colspan=\"2\" align=\"left\">&nbsp;" 
						+pi.getExtClientName()+"</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"5\">��������"
						+strYear
						+"��" +strMonth
						+"��"+strDay+"�����:"+DataFormat.formatDisabledAmount(pi.getAmount())+"Ԫ���������� �� </td>"
						+"  <td colspan=\"2\">&nbsp;ϣ���ա�&nbsp;</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"4\" align=\"right\">�Ƚ������������к�" 
						+"    ��</td>"
						+"  <td colspan=\"3\">&nbsp;</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"3\" align=\"right\">&nbsp;</td>"
						+"  <td colspan=\"4\" align=\"right\">��λǩ��(��ԭ��ӡ��)</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"7\">&nbsp;</td>"
						+"</tr>"
						+"<tr> "
						+"  <td>�������</td>"
						+"  <td colspan=\"6\">&nbsp;</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"7\" rowspan=\"6\"><table table width=\"647\" cellspacing=\"0\" height=\"138\" border=\"1px:solid\" bordercolor=\"black\" align=\"left\">"
						+"      <tr class=\"table1\"> "
						+"        <td width=\"65\" rowspan=\"2\"><div align=\"center\">��������</div></td>"
						+"        <td width=\"75\" rowspan=\"2\"><div align=\"center\">ƾ֤����</div></td>"
						+"        <td width=\"79\" rowspan=\"2\"><div align=\"center\">ժҪ</div></td>"
						+"        <td colspan=\"2\"><div align=\"center\">���������ѵ��ˣ���λ��δ����</div></td>"
						+"        <td colspan=\"2\"><div align=\"center\">��λ�ѵ��ˣ���������δ����</div></td>"
						+"      </tr>"
						+"      <tr class=\"table1\">" 
						+"        <td width=\"102\"><div align=\"center\">�跽</div></td>"
						+"        <td width=\"98\"><div align=\"center\">����</div></td>"
						+"        <td width=\"95\"><div align=\"center\">�跽</div></td>"
						+"        <td width=\"103\"><div align=\"center\">����</div></td>"
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
						+"  <td height=\"37\" colspan=\"8\">�Ӵ����߲ü�------------------------------------------------------------------------------</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"7\"><div align=\"center\">����ƹ������������ι�˾</div></td>"
						+"  <td rowspan=\"11\">��<br>"
						+"    ��<br>"
						+"    ��<br>"
						+"    :<br>"
						+"    ��<br>"
						+"    ��<br>"
						+"    ��<br>"
						+"    ��<br>"
						+"    ��<br>"
						+"    ��<br>"
						+"    ��<br>"
						+"    ��<br>"
						+"    <font color=\"#000000\" >��<br>"
						+"    ��<br>"
						+"    ��<br>"
						+"    </td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"7\"><div align=\"center\">���������˵�</div></td>"
						+"</tr>"
						+"<tr> "
						+"  <td><font align=\"left\">�˺ţ�</font></td>"
						+"  <td colspan=\"3\" align=\"left\">&nbsp;"
						+pi.getExtAccountNo()+"</td>"
						+"  <td><font align=\"left\">No.</td>"
						+"  <td colspan=\"3\" align=\"left\">&nbsp;"+pi.getFormNo()+"</td>"
						+"</tr>"
						+"<tr> "
						+"  <td><font align=\"left\">���֣�</td>"
						+"  <td colspan=\"3\" align=\"left\">&nbsp;RMB</td>"
						+"  <td><font align=\"left\">������λ���ƣ�</td>"
						+"  <td colspan=\"2\" align=\"left\">&nbsp;"
						+pi.getExtClientName()+"</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"5\">��������"
						+strYear
						+"��" +strMonth
						+"��"+strDay+"�����:"+DataFormat.formatDisabledAmount(pi.getAmount())+"Ԫ���������� �� </td>"
						+"  <td colspan=\"2\">&nbsp;ϣ���ա�&nbsp;</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"4\" align=\"right\" >�Ƚ������������к�" 
						+"    ��</td>"
						+"  <td colspan=\"3\">&nbsp;</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"3\" align=\"right\">&nbsp;</td>"
						+"  <td colspan=\"4\" align=\"right\">��λǩ��(��ԭ��ӡ��)</td>"
						+"</tr>"
						+"<tr> "
						+"  <td>&nbsp;</td>"
						+"  <td width=\"83\">&nbsp;</td>"
						+"  <td width=\"70\">&nbsp;</td>"
						+"  <td width=\"79\">&nbsp;</td>"
						+"  <td colspan=\"3\">&nbsp;&nbsp;</td>"
						+"</tr>"
						+"<tr> "
						+"  <td>�������</td>"
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
						+"        <td width=\"69\" rowspan=\"2\"><div align=\"center\">��������</div></td>"
						+"        <td width=\"78\" rowspan=\"2\"><div align=\"center\">ƾ֤����</div></td>"
						+"        <td width=\"74\" rowspan=\"2\"><div align=\"center\">ժҪ</div></td>"
						+"        <td colspan=\"2\"><div align=\"center\">���������ѵ��ˣ���λ��δ����</div></td>"
						+"        <td colspan=\"2\"><div align=\"center\">��λ�ѵ��ˣ���������δ����</div></td>"
						+"      </tr>"
						+"      <tr class=\"table1\">" 
						+"        <td width=\"87\"><div align=\"center\">�跽</div></td>"
						+"        <td width=\"99\"><div align=\"center\">����</div></td>"
						+"        <td width=\"89\"><div align=\"center\">�跽</div></td>"
						+"        <td width=\"105\"><div align=\"center\">����</div></td>"
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
						+"  <td colspan=\"7\"><div align=\"center\">Ϊ�ṩ����ķ������������������䶯&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div></td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"7\"><div align=\"center\">����д������Ϣ&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div></td>"
						+"</tr>"
						+"<tr> "
						
						+"  <td height=\"20\" colspan=\"7\">��λ��________________________________________________________________________</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"4\">��ַ��</font>___________________________________</td>"
						+"  <td colspan=\"3\">�ʱ�</font>��______________________________</td>"
						+"  <td>&nbsp;</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"4\">��ϵ�ˣ�<font size=\"2\">___________________________</td>"
						+"  <td colspan=\"3\">��ϵ�绰��<font size=\"2\">___________________________</td>"
						+"  <td>&nbsp;</td>"
						+"</tr>"
						+"<tr> "
						+"  <td colspan=\"7\">�������䣺_____________________________________________________________________</td>"
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
	     	System.out.println("�������쳣!");
			e.printStackTrace(); 
	  	}
	}
}