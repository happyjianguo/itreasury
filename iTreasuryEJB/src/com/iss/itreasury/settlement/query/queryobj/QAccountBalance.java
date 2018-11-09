/*
 * Created on 2003-11-16
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.query.queryobj;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.iss.itreasury.settlement.query.paraminfo.QueryAccountWhereInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryAccountSumInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryAccountBalanceResultInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.loan.util.LOANConstant;
/**
 * @author yiwang
 * 
 * To change the template for this generated type comment go to Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QAccountBalance extends BaseQueryObject
{
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
	Log4j logger = null;
	public final static int OrderBy_AccountNo = 1;
	public final static int OrderBy_ClientCode = 2;
	public final static int OrderBy_ClientName = 3;
	public final static int OrderBy_AccountTypeID = 4;
	//苏元锋添加的排序条件
	public final static int OrderBy_Loan_AccountNo = 11; // 贷款账户号
	public final static int OrderBy_Loan_ContractNo = 12; // 合同号
	public final static int OrderBy_Loan_ContractStatus = 13; // 合同状态
	public final static int OrderBy_Loan_PayCode = 14; // 贷款放款通知单
	public final static int OrderBy_Loan_PayStatus = 15; // 放款通知单状态
	public final static int OrderBy_Loan_ClientName = 16; // 客户名称
	public final static int OrderBy_Loan_Amount = 17; // 贷款金额
	public final static int OrderBy_Loan_Balance = 18; // 当前余额
	public final static int OrderBy_Loan_InterestRate = 19; // 利率
	public final static int OrderBy_Loan_Interest = 20; // 应付利息
	public final static int OrderBy_Loan_DrawInterest = 21; // 计提利息
	
	public final static int OrderBy_Loan_PayStartDate = 22; // 放款开始日期
	public final static int OrderBy_Loan_PayEndDate = 23; // 放款结束日期
	//public final static int OrderBy_Loan_ // 累计还息
	/**
	 *  
	 */
	public QAccountBalance()
	{
		super();
		// TODO Auto-generated constructor stub
		logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	}
	public void getTodayAccountBalance(QueryAccountWhereInfo qawi)
	{
		String JoinOut = "(+)";
		;
		// select
		m_sbSelect = new StringBuffer();
		m_sbSelect.append("  round(a.Balance,2) Minterestbalance,a.BalanceDate,a.OfficeID,a.CurrencyID,a.ClientID,a.ClientCode,a.ClientName,a.AccountID,a.AccountNo,a.AccountTypeID,a.AccountStatusID, \n");
		m_sbSelect.append("        a.SubAccountID,a.SubAccountStatusID,round(a.Balance,2) Balance,round(a.OpenAmount,2) OpenAmount,round(a.Interest,2) Interest, \n");
		m_sbSelect.append("        a.InterestDate,a.FinishDate,round(a.UnCheckPaymentAmount,2) UnCheckPaymentAmount, \n");
		m_sbSelect.append("        a.ClearInterestDate,a.InterestRate,a.AccountOpenDate, 1 isToday ,\n");
		m_sbSelect.append("            -- 活期 \n");
		m_sbSelect.append("        a.IsOverdraft, a.IsNegotiate,round(a.NegotiateAmount,2) NegotiateAmount, round(a.NegotiateUnit,2) NegotiateUnit,a.NegotiateRate, \n");
		m_sbSelect.append("        decode(a.accountgroupid,"+ SETTConstant.AccountGroupType.CURRENT +",round(a.NegotiateInterest,2)) NegotiateInterest,round(a.NegotiateBalance,2) NegotiateBalance, a.InterestPlanID, \n");
		m_sbSelect.append("        -- 定期 \n");
		m_sbSelect.append("        a.DepositNo,a.FixPeriod,a.FixDepositStartDate,a.FixDepositEndDate,a.FixInterestRate,a.NoticeDay, \n");
		m_sbSelect.append("        round(a.FixPreDrawInterest,2) FixPreDrawInterest,a.FixPreDrawDate, \n");
		m_sbSelect.append("        -- 贷款 \n");
		m_sbSelect.append("        a.LoanPayID, \n");
		m_sbSelect.append("        -- 贷款 \n");
		m_sbSelect.append("        b.ContractID,b.LoanTypeID,b.ContractStatusID,round(b.ContractAmount,2) ContractAmount,round(b.LoanPayAmount,2) LoanPayAmount, \n");
		m_sbSelect.append("        b.ContractPeriod,b.ContractYear,b.ContractCode, b.LoanpayCode,b.ConsignClientID,b.ContractStartDate,b.ContractEndDate, \n");
			//下行于2008-11-4修改 内容:增加"b.IndustryType3," kaishao
		m_sbSelect.append("        b.ContractInterestRate,b.LoanPayStartDate,b.LoanpayEndDate,b.IndustryType1,b.IndustryType2,b.IndustryType3,b.RegionTypeID,b.ClientTypeID1, \n");
		m_sbSelect.append("        b.ClientTypeID2,b.ClientTypeID3,b.ClientTypeID4,b.ClientTypeID5,b.ClientTypeID6, \n");
		m_sbSelect.append("        b.RiskLevel,b.ParentCorpID,b.LoanPayRate,b.CommissionRate,b.SecutyFeeRate,b.CommissionTypeID \n");
		// from
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" (select client.id ClientID,Client.sCode ClientCode,Client.sname ClientName,acct.id AccountID,acct.saccountno AccountNo,acct.naccounttypeid AccountTypeID, acctype.naccountgroupid AccountGroupID, \n");
		m_sbFrom.append("        acct.nstatusID AccountStatusID,acct.nOfficeID OfficeID,acct.nCurrencyID CurrencyID, \n");
		m_sbFrom.append("        subAcct.id SubAccountID, subAcct.nstatusid SubAccountStatusID,subAcct.mbalance Balance,subAcct.mOpenAmount OpenAmount, \n");
		m_sbFrom.append("        subAcct.mInterest Interest, subAcct.dtOpen InterestDate, subAcct.dtFinish FinishDate, subAcct.mUnCheckPaymentAmount UncheckPaymentAmount, \n");
		m_sbFrom.append("        subAcct.dtClearInterest ClearInterestDate, OfficeTime.dtOpenDate BalanceDate,0.00 InterestRate,subAcct.dtOpen AccountOpenDate,  \n");
		m_sbFrom.append("        -- 活期 \n");
		m_sbFrom.append("        subAcct.ac_nIsOverdraft IsOverdraft, subAcct.ac_nIsNegotiate IsNegotiate,subAcct.ac_mNegotiateAmount NegotiateAmount, \n");
		m_sbFrom.append("        subAcct.ac_mNegotiateUnit NegotiateUnit,subAcct.ac_mNegotiateRate NegotiateRate,0  NegotiateBalance, subAcct.ac_mNegotiateInterest NegotiateInterest, \n");
		m_sbFrom.append("        subAcct.ac_nInterestRatePlanID InterestPlanID, \n");
		m_sbFrom.append("        -- 定期 \n");
		m_sbFrom.append("        subAcct.af_sDepositNo DepositNo,subAcct.af_nDepositTerm FixPeriod,subAcct.af_dtStart FixDepositStartDate, \n");
		m_sbFrom.append("        subAcct.af_dtEnd FixDepositEndDate,subAcct.af_mRate FixInterestRate,subAcct.af_nNoticeDay NoticeDay, \n");
		m_sbFrom.append("        subAcct.af_mPreDrawInterest FixPreDrawInterest,subAcct.af_dtPreDraw FixPreDrawDate, \n");
		m_sbFrom.append("        -- 贷款 \n");
		m_sbFrom.append("        subAcct.al_nLoanNoteID LoanPayID \n");
		m_sbFrom.append(" from sett_OfficeTime OfficeTime,client,sett_account acct, sett_subAccount subAcct, sett_accounttype acctype \n");
		m_sbFrom.append(" where client.id=acct.nclientid and acct.id=subAcct.nAccountID and acct.nofficeid=" + qawi.getOfficeID() + " and acct.ncurrencyid=" + qawi.getCurrencyID() + " \n");
		m_sbFrom.append("       and OfficeTime.nOfficeID=acct.nOfficeID and OfficeTime.nCurrencyID=acct.nCurrencyID and subAcct.NSTATUSID>0 \n");
		m_sbFrom.append(" and acct.nCheckStatusID = 4 \n");//add by rxie 没有复核的账户和已删除的账户不会显示出来没有复核的账户和已删除的账户信息都显示出来了
		//  客户号
		if (qawi.getStartClientCode() != null && qawi.getStartClientCode().length() > 0)
			m_sbFrom.append("    and   client.scode>='" + qawi.getStartClientCode() + "' ");
		if (qawi.getEndClientCode() != null && qawi.getEndClientCode().length() > 0)
			m_sbFrom.append("    and   client.scode<='" + qawi.getEndClientCode() + "' ");
		// 账户号
		if (qawi.getStartAccountNo() != null && qawi.getStartAccountNo().length() > 0)
			m_sbFrom.append("    and   acct.saccountno>='" + qawi.getStartAccountNo() + "' ");
		if (qawi.getEndAccountNo() != null && qawi.getEndAccountNo().length() > 0)
			m_sbFrom.append("    and   acct.saccountno<='" + qawi.getEndAccountNo() + "' ");
		//add by 2012-05-16 添加指定编号
		if(qawi.getAppointAccountNo() != null && qawi.getAppointAccountNo().length() > 0){
			m_sbFrom.append("    and   acct.saccountno in ('"+qawi.getAppointAccountNo()+"')");
		}
		if (qawi.getEnterpriseTypeID1() > 0)
			m_sbFrom.append("    and   client.nClienttypeID1 = " + qawi.getEnterpriseTypeID1());
		if (qawi.getEnterpriseTypeID2() > 0)
			m_sbFrom.append("    and   client.nClienttypeID2 = " + qawi.getEnterpriseTypeID2());
		if (qawi.getEnterpriseTypeID3() > 0)
			m_sbFrom.append("    and   client.nClienttypeID3 = " + qawi.getEnterpriseTypeID3());
		if (qawi.getEnterpriseTypeID4() > 0)
			m_sbFrom.append("    and   client.nClienttypeID4 = " + qawi.getEnterpriseTypeID4());
		if (qawi.getEnterpriseTypeID5() > 0)
			m_sbFrom.append("    and   client.nClienttypeID5 = " + qawi.getEnterpriseTypeID5());
		if (qawi.getEnterpriseTypeID6() > 0)
			m_sbFrom.append("    and   client.nClienttypeID6 = " + qawi.getEnterpriseTypeID6());
		if (qawi.getIsNegotiate() > 0)
			m_sbFrom.append("    and   subAcct.ac_nIsNegotiate>0 ");
		if (qawi.getIsFilterNull() > 0)
			m_sbFrom.append("    and   subAcct.mBalance >0.0 ");
		if (qawi.getIsValidAccount() > 0)
			m_sbFrom.append("    and   subAcct.nStatusID=" + SETTConstant.SubAccountStatus.NORMAL);
		// 定期
		if (qawi.getStartFixFormNo() != null && qawi.getStartFixFormNo().length() > 0)
			m_sbFrom.append("    and   subAcct.af_sDepositNo='" + qawi.getStartFixFormNo() + "' ");
		if (qawi.getStartFixPeriod() > 0)
			m_sbFrom.append("    and   subAcct.af_nDepositTerm=" + qawi.getStartFixPeriod());
		// 自营贷款
		if (qawi.getLoanType() == LOANConstant.LoanType.ZY) {
			m_sbFrom.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.TRUST +" and nstatusId=1 and officeId="+qawi.getOfficeID()+" and currencyId="+qawi.getCurrencyID()+")");
		//　委托贷款
		} else if (qawi.getLoanType() == LOANConstant.LoanType.WT) {
			m_sbFrom.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.CONSIGN +" and nstatusId=1 and officeId="+qawi.getOfficeID()+" and currencyId="+qawi.getCurrencyID()+")");
		//　银团贷款
		} else if (qawi.getLoanType() == LOANConstant.LoanType.YT) {
			m_sbFrom.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.YT +" and nstatusId=1 and officeId="+qawi.getOfficeID()+" and currencyId="+qawi.getCurrencyID()+")");
		//　贴现
		} else if (qawi.getLoanType() == LOANConstant.LoanType.TX) {
			m_sbFrom.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.DISCOUNT +" and nstatusId=1 and officeId="+qawi.getOfficeID()+" and currencyId="+qawi.getCurrencyID()+")");
		//　担保
		} else if (qawi.getLoanType() == LOANConstant.LoanType.DB || qawi.getLoanType() == LOANConstant.LoanType.RZZL) {
			m_sbFrom.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.MARGIN +" and nstatusId=1 and officeId="+qawi.getOfficeID()+" and currencyId="+qawi.getCurrencyID()+")");
		//　其他贷款
		} else if (qawi.getLoanType() == LOANConstant.LoanType.OTHER) {
			m_sbFrom.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.OTHERLOAN +" and nstatusId=1 and officeId="+qawi.getOfficeID()+" and currencyId="+qawi.getCurrencyID()+")");
		} else {
			m_sbFrom.append("     and acct.naccounttypeid in (select id from sett_accounttype where nstatusId=1 and officeId="+qawi.getOfficeID()+" and currencyId="+qawi.getCurrencyID()+")");
		}
		m_sbFrom.append(" and acct.naccounttypeid = acctype.id \n");
		
		//
		m_sbFrom.append(" ) a, \n");
		m_sbFrom.append(" (select * \n");
		m_sbFrom.append("  from sett_vContractInfo \n");
		m_sbFrom.append("  where officeid=" + qawi.getOfficeID() + " and currencyid=" + qawi.getCurrencyID() + " \n");
		// 贷款
		if (qawi.getStartContractCode() != null && qawi.getStartContractCode().length() > 0)
		{
			m_sbFrom.append("      and ContractCode ='" + qawi.getStartContractCode() + "' ");
			JoinOut = "";
		}
		if (qawi.getStartLoanPayCode() != null && qawi.getStartLoanPayCode().length() > 0)
		{
			m_sbFrom.append("      and LoanPayCode ='" + qawi.getStartLoanPayCode() + "' ");
			JoinOut = "";
		}
		if (qawi.getContractYear() > 0)
		{
			m_sbFrom.append("      and ContractYear =" + qawi.getContractYear());
			JoinOut = "";
		}
		if (qawi.getConsignClientID() > 0)
		{
			m_sbFrom.append("      and ConsignClientID =" + qawi.getConsignClientID());
			JoinOut = "";
		}
		if (qawi.getStartContractPeriod() > 0)
		{
			m_sbFrom.append("      and ContractPeriod>=" + qawi.getStartContractPeriod());
			JoinOut = "";
		}
		if (qawi.getEndContractPeriod() > 0)
		{
			m_sbFrom.append("      and ContractPeriod<=" + qawi.getEndContractPeriod());
			JoinOut = "";
		}
		if (qawi.getLoanType() > 0)
		{
			m_sbFrom.append("      and LoanTypeID=" + qawi.getLoanType());
			JoinOut = "";
		}
		m_sbFrom.append(" ) b        \n");
		// where
		m_sbWhere = new StringBuffer();
		m_sbWhere.append("  a.LoanPayID=b.LoanPayID" + JoinOut + " \n");
		//
		m_sbOrderBy = new StringBuffer();
		String strDesc = qawi.getDesc() == 1 ? " desc " : "";
		switch ((int) qawi.getOrderField())
		{
			case OrderBy_AccountNo :
				m_sbOrderBy.append(" \n order by AccountNo,ContractCode,LoanpayCode" + strDesc);
				break;
			case OrderBy_ClientCode :
				m_sbOrderBy.append(" \n order by ClientCode" + strDesc);
				break;
			case OrderBy_ClientName :
				m_sbOrderBy.append(" \n order by ClientName" + strDesc);
				break;
			case OrderBy_AccountTypeID :
				m_sbOrderBy.append(" \n order by AccountTypeID" + strDesc);
				break;
			default :
				m_sbOrderBy.append(" \n order by AccountNo,ContractCode,LoanpayCode" + strDesc);
				break;
		}
	}
	public void getHistoryAccountBalance(QueryAccountWhereInfo qawi)
	{
		String JoinOut = "(+)";
		// select
		m_sbSelect = new StringBuffer();
		////minzhao 2009.3.25 增加计息余额
		m_sbSelect.append("  round(a.Minterestbalance,2) Minterestbalance,a.BalanceDate,a.OfficeID,a.CurrencyID,a.ClientID,a.ClientCode,a.ClientName,a.AccountID,a.AccountNo,a.AccountTypeID,a.AccountStatusID, \n");
		m_sbSelect.append("        a.SubAccountID,a.SubAccountStatusID,round(a.Balance,2) Balance,round(a.OpenAmount,2) OpenAmount,round(a.Interest,2) Interest, \n");
		m_sbSelect.append("        a.InterestDate,a.FinishDate,round(a.UnCheckPaymentAmount,2) UnCheckPaymentAmount, \n");
		m_sbSelect.append("        a.ClearInterestDate,a.InterestRate,a.AccountOpenDate,0 isToday,  \n");
		m_sbSelect.append("            -- 活期 \n");
		m_sbSelect.append("        a.IsOverdraft, a.IsNegotiate,round(a.NegotiateAmount,2) NegotiateAmount, round(a.NegotiateUnit,2) NegotiateUnit,a.NegotiateRate, \n");
		m_sbSelect.append("        decode(a.accountgroupid,"+ SETTConstant.AccountGroupType.CURRENT +",round(a.NegotiateInterest,2)) NegotiateInterest,round(a.NegotiateBalance,2) NegotiateBalance,a.InterestPlanID, \n");
		m_sbSelect.append("        -- 定期 \n");
		m_sbSelect.append("        a.DepositNo,a.FixPeriod,a.FixDepositStartDate,a.FixDepositEndDate,a.FixInterestRate,a.NoticeDay, \n");
		m_sbSelect.append("        round(a.FixPreDrawInterest,2) FixPreDrawInterest,a.FixPreDrawDate, \n");
		m_sbSelect.append("        -- 贷款 \n");
		m_sbSelect.append("        a.LoanPayID, \n");
		m_sbSelect.append("        -- 贷款 \n");
		m_sbSelect.append("        b.ContractID,b.LoanTypeID,b.ContractStatusID,round(b.ContractAmount,2) ContractAmount,round(b.LoanPayAmount,2) LoanPayAmount, \n");
		m_sbSelect.append("        b.ContractPeriod,b.ContractYear,b.ContractCode, b.LoanpayCode,b.ConsignClientID,b.ContractStartDate,b.ContractEndDate, \n");
			//下行于2008-11-4修改 内容:增加"b.IndustryType3," kaishao
		m_sbSelect.append("        b.ContractInterestRate,b.LoanPayStartDate,b.LoanpayEndDate,b.IndustryType1,b.IndustryType2,b.IndustryType3,b.RegionTypeID,b.ClientTypeID1, \n");
		m_sbSelect.append("        b.ClientTypeID2,b.ClientTypeID3,b.ClientTypeID4,b.ClientTypeID5,b.ClientTypeID6, \n");
		m_sbSelect.append("        b.RiskLevel,b.ParentCorpID,b.LoanPayRate,b.CommissionRate,b.SecutyFeeRate,b.CommissionTypeID \n");
		// from
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" (select client.id ClientID,Client.sCode ClientCode,Client.sname ClientName,acct.id AccountID,acct.saccountno AccountNo,acct.naccounttypeid AccountTypeID, acctype.naccountgroupid AccountGroupID, \n");
		m_sbFrom.append("        acct.nstatusID AccountStatusID,acct.nOfficeID OfficeID,acct.nCurrencyID CurrencyID, \n");
		m_sbFrom.append("        subAcct.id SubAccountID, subAcct.nstatusid SubAccountStatusID,daily.mbalance Balance,subAcct.mOpenAmount OpenAmount, \n");
		
		//minzhao 2009.3.25 增加计息余额
		m_sbFrom.append("        daily.minterestbalance  Minterestbalance,\n");
		
		//Boxu Add 2008年3月3日 利息和协定利息取的不对
		//m_sbFrom.append("        (daily.mInterest-daily.mDailyInterest) Interest, subAcct.dtOpen InterestDate, subAcct.dtFinish FinishDate, 0.0 UncheckPaymentAmount, \n");
		m_sbFrom.append("        daily.mInterest Interest, \n");
		
		m_sbFrom.append("        subAcct.dtOpen InterestDate, subAcct.dtFinish FinishDate, 0.0 UncheckPaymentAmount, \n");
		m_sbFrom.append("        subAcct.dtClearInterest ClearInterestDate, daily.dtDate BalanceDate,daily.mInterestRate InterestRate, \n");
		m_sbFrom.append("        subAcct.dtOpen AccountOpenDate, \n");
		m_sbFrom.append("        -- 活期 \n");
		m_sbFrom.append("        subAcct.ac_nIsOverdraft IsOverdraft, subAcct.ac_nIsNegotiate IsNegotiate,subAcct.ac_mNegotiateAmount NegotiateAmount, \n");
		m_sbFrom.append("        subAcct.ac_mNegotiateUnit NegotiateUnit,daily.ac_mNegotiateRate NegotiateRate,daily.ac_mNegotiateBalance  NegotiateBalance, \n");
		
		//Boxu Add 2008年3月3日 利息和协定利息取的不对
		//m_sbFrom.append("        (daily.ac_mNegotiateInterest-daily.ac_mDailyNegotiateInterest) NegotiateInterest,subAcct.ac_nInterestRatePlanID InterestPlanID, \n");
		m_sbFrom.append("        daily.ac_mNegotiateInterest NegotiateInterest, \n");
		
		m_sbFrom.append("        subAcct.ac_nInterestRatePlanID InterestPlanID, \n");
		m_sbFrom.append("        -- 定期 \n");
		m_sbFrom.append("        subAcct.af_sDepositNo DepositNo,subAcct.af_nDepositTerm FixPeriod,subAcct.af_dtStart FixDepositStartDate, \n");
		m_sbFrom.append("        subAcct.af_dtEnd FixDepositEndDate,subAcct.af_mRate FixInterestRate,subAcct.af_nNoticeDay NoticeDay, \n");
		m_sbFrom.append("        subAcct.af_mPreDrawInterest FixPreDrawInterest,subAcct.af_dtPreDraw FixPreDrawDate, \n");
		m_sbFrom.append("        -- 贷款 \n");
		m_sbFrom.append("        subAcct.al_nLoanNoteID LoanPayID,subAcct.al_mPreDrawInterest LoanPreDrawInterest \n");
		m_sbFrom.append(" from client,sett_account acct, sett_subAccount subAcct,sett_DailyAccountBalance daily, sett_accounttype acctype \n");
		m_sbFrom.append("  where client.id=acct.nclientid and acct.id=subAcct.nAccountID and acct.nofficeid=" + qawi.getOfficeID() + " and acct.ncurrencyid=" + qawi.getCurrencyID() + " \n");
		m_sbFrom.append("         and subAcct.NSTATUSID>0 and subAcct.id=daily.nSubAccountID and daily.dtDate=to_date('" + DataFormat.getDateString(qawi.getQueryDate()) + "','yyyy-mm-dd') \n");
		m_sbFrom.append(" and acct.nCheckStatusID = 4 \n");//add by rxie 没有复核的账户和已删除的账户不会显示出来没有复核的账户和已删除的账户信息都显示出来了
		//  客户号
		if (qawi.getStartClientCode() != null && qawi.getStartClientCode().length() > 0)
			m_sbFrom.append("    and   client.scode>='" + qawi.getStartClientCode() + "' ");
		if (qawi.getEndClientCode() != null && qawi.getEndClientCode().length() > 0)
			m_sbFrom.append("    and   client.scode<='" + qawi.getEndClientCode() + "' ");
		// 账户号
		if (qawi.getStartAccountNo() != null && qawi.getStartAccountNo().length() > 0)
			m_sbFrom.append("    and   acct.saccountno>='" + qawi.getStartAccountNo() + "' ");
		if (qawi.getEndAccountNo() != null && qawi.getEndAccountNo().length() > 0)
			m_sbFrom.append("    and   acct.saccountno<='" + qawi.getEndAccountNo() + "' ");
		//add by 2012-05-16 添加指定编号
		if(qawi.getAppointAccountNo() != null && qawi.getAppointAccountNo().length() > 0){
			m_sbFrom.append("    and   acct.saccountno in ('"+qawi.getAppointAccountNo()+"')");
		}
		if (qawi.getEnterpriseTypeID1() > 0)
			m_sbFrom.append("    and   client.nClienttypeID1 = " + qawi.getEnterpriseTypeID1());
		if (qawi.getEnterpriseTypeID2() > 0)
			m_sbFrom.append("    and   client.nClienttypeID2 = " + qawi.getEnterpriseTypeID2());
		if (qawi.getEnterpriseTypeID3() > 0)
			m_sbFrom.append("    and   client.nClienttypeID3 = " + qawi.getEnterpriseTypeID3());
		if (qawi.getEnterpriseTypeID4() > 0)
			m_sbFrom.append("    and   client.nClienttypeID4 = " + qawi.getEnterpriseTypeID4());
		if (qawi.getEnterpriseTypeID5() > 0)
			m_sbFrom.append("    and   client.nClienttypeID5 = " + qawi.getEnterpriseTypeID5());
		if (qawi.getEnterpriseTypeID6() > 0)
			m_sbFrom.append("    and   client.nClienttypeID6 = " + qawi.getEnterpriseTypeID6());
		if (qawi.getIsNegotiate() > 0)
			m_sbFrom.append("    and   subAcct.ac_nIsNegotiate>0 ");
		if (qawi.getIsFilterNull() > 0)
			m_sbFrom.append("    and   daily.mBalance>0");
		if (qawi.getIsValidAccount() > 0)
			m_sbFrom.append("    and   subAcct.nStatusID=" + SETTConstant.SubAccountStatus.NORMAL);
		// 定期
		if (qawi.getStartFixFormNo() != null && qawi.getStartFixFormNo().length() > 0)
			m_sbFrom.append("    and   subAcct.af_sDepositNo='" + qawi.getStartFixFormNo() + "' ");
		if (qawi.getStartFixPeriod() > 0)
			m_sbFrom.append("    and   subAcct.af_nDepositTerm=" + qawi.getStartFixPeriod());
		// 自营贷款
		if (qawi.getLoanType() == LOANConstant.LoanType.ZY) {
			m_sbFrom.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.TRUST +" and nstatusId=1 and officeId="+qawi.getOfficeID()+" and currencyId="+qawi.getCurrencyID()+")");
		//　委托贷款
		} else if (qawi.getLoanType() == LOANConstant.LoanType.WT) {
			m_sbFrom.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.CONSIGN +" and nstatusId=1 and officeId="+qawi.getOfficeID()+" and currencyId="+qawi.getCurrencyID()+")");
		//　银团贷款
		} else if (qawi.getLoanType() == LOANConstant.LoanType.YT) {
			m_sbFrom.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.YT +" and nstatusId=1 and officeId="+qawi.getOfficeID()+" and currencyId="+qawi.getCurrencyID()+")");
		//　贴现
		} else if (qawi.getLoanType() == LOANConstant.LoanType.TX) {
			m_sbFrom.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.DISCOUNT +" and nstatusId=1 and officeId="+qawi.getOfficeID()+" and currencyId="+qawi.getCurrencyID()+")");
		//　担保
		} else if (qawi.getLoanType() == LOANConstant.LoanType.DB || qawi.getLoanType() == LOANConstant.LoanType.RZZL) {
			m_sbFrom.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.MARGIN +" and nstatusId=1 and officeId="+qawi.getOfficeID()+" and currencyId="+qawi.getCurrencyID()+")");
		//　其他贷款
		} else if (qawi.getLoanType() == LOANConstant.LoanType.OTHER) {
			m_sbFrom.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.OTHERLOAN +" and nstatusId=1 and officeId="+qawi.getOfficeID()+" and currencyId="+qawi.getCurrencyID()+")");
		} else {
			m_sbFrom.append("     and acct.naccounttypeid in (select id from sett_accounttype where nstatusId=1 and officeId="+qawi.getOfficeID()+" and currencyId="+qawi.getCurrencyID()+")");
		}
		m_sbFrom.append(" and acct.naccounttypeid = acctype.id \n");
		//
		m_sbFrom.append(" ) a, \n");
		// sett_VContractInfo
		m_sbFrom.append(" (select * \n");
		m_sbFrom.append("  from sett_vContractInfo \n");
		m_sbFrom.append("  where officeid=" + qawi.getOfficeID() + " and currencyid=" + qawi.getCurrencyID() + " \n");
		// 贷款
		if (qawi.getStartContractCode() != null && qawi.getStartContractCode().length() > 0)
		{
			m_sbFrom.append("      and ContractCode ='" + qawi.getStartContractCode() + "' ");
			JoinOut = "";
		}
		if (qawi.getStartLoanPayCode() != null && qawi.getStartLoanPayCode().length() > 0)
		{
			m_sbFrom.append("      and LoanPayCode ='" + qawi.getStartLoanPayCode() + "' ");
			JoinOut = "";
		}
		if (qawi.getContractYear() > 0)
		{
			m_sbFrom.append("      and ContractYear =" + qawi.getContractYear());
			JoinOut = "";
		}
		if (qawi.getConsignClientID() > 0)
		{
			m_sbFrom.append("      and ConsignClientID =" + qawi.getConsignClientID());
			JoinOut = "";
		}
		if (qawi.getStartContractPeriod() > 0)
		{
			m_sbFrom.append("      and ContractPeriod>=" + qawi.getStartContractPeriod());
			JoinOut = "";
		}
		if (qawi.getEndContractPeriod() > 0)
		{
			m_sbFrom.append("      and ContractPeriod<=" + qawi.getEndContractPeriod());
			JoinOut = "";
		}
		if (qawi.getLoanType() > 0)
		{
			m_sbFrom.append("      and LoanTypeID=" + qawi.getLoanType());
			JoinOut = "";
		}
		m_sbFrom.append(" ) b        \n");
		// where
		m_sbWhere = new StringBuffer();
		m_sbWhere.append("  a.LoanPayID=b.LoanPayID" + JoinOut + " \n");
		// order by
		m_sbOrderBy = new StringBuffer();
		String strDesc = qawi.getDesc() == 1 ? " desc " : "";
		switch ((int) qawi.getOrderField())
		{
			case OrderBy_AccountNo :
				m_sbOrderBy.append(" \n order by AccountNo,ContractCode,LoanpayCode" + strDesc);
				break;
			case OrderBy_ClientCode :
				m_sbOrderBy.append(" \n order by ClientCode" + strDesc);
				break;
			case OrderBy_ClientName :
				m_sbOrderBy.append(" \n order by ClientName" + strDesc);
				break;
			case OrderBy_AccountTypeID :
				m_sbOrderBy.append(" \n order by AccountTypeID" + strDesc);
				break;
			default :
				m_sbOrderBy.append(" \n order by AccountNo,ContractCode,LoanpayCode" + strDesc);
				break;
		}
	}
	public PageLoader queryAccountBalance(QueryAccountWhereInfo qawi) throws Exception
	{
		/*
		if (qawi.getStartAccountNo() != null && qawi.getStartAccountNo().length() > 0)
			qawi.setStartClientCode(null);
		if (qawi.getEndAccountNo() != null && qawi.getEndAccountNo().length() > 0)
			qawi.setEndClientCode(null);
		*/
		if (isToday(qawi.getOfficeID(), qawi.getCurrencyID(), qawi.getQueryDate()))
			getTodayAccountBalance(qawi);
		else
			getHistoryAccountBalance(qawi);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(new AppContext(), m_sbFrom.toString(), m_sbSelect.toString(), m_sbWhere.toString(), (int) Constant.PageControl.CODE_PAGELINECOUNT,
				"com.iss.itreasury.settlement.query.resultinfo.QueryAccountBalanceResultInfo", null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	public String queryAccountBalanceStr(QueryAccountWhereInfo qawi) throws Exception
	{
		if (qawi.getStartAccountNo() != null && qawi.getStartAccountNo().length() > 0)
			qawi.setStartClientCode(null);
		if (qawi.getEndAccountNo() != null && qawi.getEndAccountNo().length() > 0)
			qawi.setEndClientCode(null);
		//
		if (isToday(qawi.getOfficeID(), qawi.getCurrencyID(), qawi.getQueryDate()))
			getTodayAccountBalance(qawi);
		else
			getHistoryAccountBalance(qawi);
		return "select "+m_sbSelect+" from "+m_sbFrom+" where "+m_sbSelect;
	}
	public QueryAccountSumInfo queryAccountBalanceSum(QueryAccountWhereInfo qawi) throws Exception
	{
		QueryAccountSumInfo sumObj = new QueryAccountSumInfo();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String strSelect = "";
		String strDepositWhere = "";
		String strLoanWhere = "";
		String sql = "";
		//
		try
		{
			/*
			if (qawi.getStartAccountNo() != null && qawi.getStartAccountNo().length() > 0)
				qawi.setStartClientCode(null);
			if (qawi.getEndAccountNo() != null && qawi.getEndAccountNo().length() > 0)
				qawi.setEndClientCode(null);
			*/
			if (isToday(qawi.getOfficeID(), qawi.getCurrencyID(), qawi.getQueryDate()))
				{
				getTodayAccountBalance(qawi);
				
				m_sbSelect.setLength(0);
				m_sbSelect.append(" SELECT nvl(sum(round(a.Balance,2)),0) MinterestbalanceSum,nvl(sum(round(a.Balance,2)),0) BalanceSum,nvl(sum(round(a.interest,2)),0) InterestSum,nvl(sum(round(a.NegotiateInterest,2)),0) NegotiateInterestSum,nvl(sum(decode(a.IsNegotiate,-1,0,0,0,decode(a.NegotiateUnit,0,0,decode(abs(a.Balance - a.NegotiateAmount) -(a.Balance - a.NegotiateAmount),0,(floor((a.Balance-a.NegotiateAmount)/a.NegotiateUnit)*a.NegotiateUnit),0)))),0) NegotiateBalanceSum \n");
				
				}
			else
				{
				getHistoryAccountBalance(qawi);
				m_sbSelect.setLength(0);
				m_sbSelect.append(" SELECT nvl(sum(round(a.Minterestbalance,2)),0) MinterestbalanceSum,nvl(sum(round(a.Balance,2)),0) BalanceSum,nvl(sum(round(a.interest,2)),0) InterestSum,nvl(sum(round(a.NegotiateInterest,2)),0) NegotiateInterestSum,nvl(sum(decode(a.IsNegotiate,-1,0,0,0,decode(a.NegotiateUnit,0,0,decode(abs(a.Balance - a.NegotiateAmount) -(a.Balance - a.NegotiateAmount),0,(floor((a.Balance-a.NegotiateAmount)/a.NegotiateUnit)*a.NegotiateUnit),0)))),0) NegotiateBalanceSum \n");
				
				}
			
			
			
			con = this.getConnection();
			sql = m_sbSelect.toString() + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString();
			
			//Modify by leiyang 2008/05/01
			//账户余额表 余额合计 不统计委托存款账户余额(国电专用)
			sql += " and a.accounttypeid not in (select id from sett_accounttype where nAccountGroupID = "
				+ SETTConstant.AccountGroupType.CURRENT +" and nstatusId=1 and officeId="
				+ qawi.getOfficeID() +" and currencyId="+ qawi.getCurrencyID() 
				+" and saccounttypecode = "+Config.getProperty(ConfigConstant.SETTLEMENT_WTDEPOSIT_ACCOUNTTYPECODE,"2")+" )";
			
			logger.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//
				sumObj.setBalanceSum(rs.getDouble("BalanceSum"));
				sumObj.setNegotiateBalanceSum(rs.getDouble("NegotiateBalanceSum"));
				sumObj.setInterestSum(rs.getDouble("InterestSum"));
				sumObj.setNegotiateInterestSum(rs.getDouble("NegotiateInterestSum"));
				sumObj.setMinterestbalanceSum(rs.getDouble("MinterestbalanceSum"));
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		} catch (Exception exp)
		{
			throw exp;
		} finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return sumObj;
	}
	/**
	 * 查询系统开机日当天的贷款余额
	 * 以下方法由苏元锋添加 
	 * modify by Forest, 20040517,修改原因是查不到贴现贷款。
	 */
	public void getTodayLoanAcountBalace(QueryAccountWhereInfo qawi)
	{
		// select
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" distinct a.BalanceDate,a.OfficeID,a.CurrencyID,a.ClientID,a.ClientCode,a.ClientName,a.AccountID,a.AccountNo,a.AccountTypeID,a.AccountStatusID, \n");
		m_sbSelect.append("        a.SubAccountID,a.SubAccountStatusID,round(a.Balance,2) Balance,round(a.OpenAmount,2) OpenAmount,round(a.Interest,2) Interest, \n");
		m_sbSelect.append("        a.InterestDate,a.FinishDate,round(a.UnCheckPaymentAmount,2) UnCheckPaymentAmount, \n");
		m_sbSelect.append("        a.ClearInterestDate,a.InterestRate,a.AccountOpenDate, 1 isToday ,\n");
		m_sbSelect.append("        -- 贷款 \n");
		m_sbSelect.append("        a.LoanPayID, round(a.LoanPreDrawInterest,2) LoanPreDrawInterest,\n");
		m_sbSelect.append("        -- 贷款 \n");
		m_sbSelect.append("        b.ContractID,b.LoanTypeID,b.ContractStatusID,round(b.ContractAmount,2) ContractAmount,round(b.LoanPayAmount,2) LoanPayAmount, \n");
		m_sbSelect.append("        b.ContractPeriod,b.ContractYear,b.ContractCode, b.LoanpayCode,b.ConsignClientID,b.ContractStartDate,b.ContractEndDate, \n");
			//下行于2008-11-4修改 内容:增加"b.IndustryType3," kaishao
		m_sbSelect.append("        b.ContractInterestRate,b.LoanPayStartDate,b.LoanpayEndDate,b.IndustryType1,b.IndustryType2,b.IndustryType3,b.RegionTypeID,b.ClientTypeID1, \n");
		m_sbSelect.append("        b.ClientTypeID2,b.ClientTypeID3,b.ClientTypeID4,b.ClientTypeID5,b.ClientTypeID6, \n");
		m_sbSelect.append("        b.RiskLevel,b.ParentCorpID,b.LoanPayRate,b.CommissionRate,b.SecutyFeeRate,b.CommissionTypeID \n");
		// from
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" (select client.id ClientID,Client.sCode ClientCode,Client.sname ClientName,acct.id AccountID,acct.saccountno AccountNo,acct.naccounttypeid AccountTypeID, \n");
		m_sbFrom.append("        acct.nstatusID AccountStatusID,acct.nOfficeID OfficeID,acct.nCurrencyID CurrencyID, \n");
		m_sbFrom.append("        subAcct.id SubAccountID, subAcct.nstatusid SubAccountStatusID,subAcct.mbalance Balance,subAcct.mOpenAmount OpenAmount, \n");
		m_sbFrom.append("        subAcct.mInterest Interest, subAcct.dtOpen InterestDate, subAcct.dtFinish FinishDate, subAcct.mUnCheckPaymentAmount UncheckPaymentAmount, \n");
		m_sbFrom.append("        subAcct.dtClearInterest ClearInterestDate, OfficeTime.dtOpenDate BalanceDate,0.00 InterestRate,subAcct.dtOpen AccountOpenDate,  \n");
		m_sbFrom.append("        -- 贷款 \n");
		m_sbFrom.append("        subAcct.al_nLoanNoteID LoanPayID,subAcct.al_mPreDrawInterest LoanPreDrawInterest \n");
		//modify by lxr 2004-01-07 begin
		//modify by forest,20040517,加上跟loan_discountcredence的关联。 
//		modify by ZYYAO,20070807,加上跟LOAN_ASSURECHARGEFORM的关联。
		m_sbFrom.append(" from sett_OfficeTime OfficeTime,client,sett_account acct, sett_subAccount subAcct,(select ID,dtEnd from loan_PayForm union select ID,dtAtterm dtEnd from loan_DiscountCredence union select ID,enddate dtEnd from LOAN_ASSURECHARGEFORM ) payform \n");
		m_sbFrom.append(" where client.id=acct.nclientid and acct.id=subAcct.nAccountID and subAcct.AL_nLoanNoteID = payform.ID and acct.nofficeid=" + qawi.getOfficeID() + " and acct.ncurrencyid="
				+ qawi.getCurrencyID() + " \n");
		m_sbFrom.append("       and OfficeTime.nOfficeID=acct.nOfficeID and OfficeTime.nCurrencyID=acct.nCurrencyID and subAcct.NSTATUSID>0 \n");
		if (qawi.getDtFinish() != null)
		{
			//处理截止日期
			if (qawi.getDtFinish().after(Env.getSystemDate(qawi.getOfficeID(), qawi.getCurrencyID())))
			{
				m_sbFrom.append("  and payform.dtEnd>=to_date('" + DataFormat.formatDate(qawi.getDtFinish()) + "','yyyy-mm-dd') ");
			} else
			{
				m_sbFrom.append(" and (subAcct.dtFinish>=to_date('" + DataFormat.formatDate(qawi.getDtFinish()) + "','yyyy-mm-dd') ");
				m_sbFrom.append("   or subAcct.dtFinish is null ) ");
			}
		}
		//modify by lxr 2004-01-07 end
		//  客户号
		if (qawi.getStartClientCode() != null && qawi.getStartClientCode().length() > 0)
			m_sbFrom.append("    and   client.scode>='" + qawi.getStartClientCode() + "' ");
		if (qawi.getEndClientCode() != null && qawi.getEndClientCode().length() > 0)
			m_sbFrom.append("    and   client.scode<='" + qawi.getEndClientCode() + "' ");
		//上级单位1 nParentCorpID1
		if (qawi.getParentCorpID1() > 0)
		{
			m_sbFrom.append("    and   client.nParentCorpID1=" + qawi.getParentCorpID1() + " ");
		}
		//上级单位2 nParentCorpID2
		if (qawi.getParentCorpID2() > 0)
		{
			m_sbFrom.append("    and   client.nParentCorpID2=" + qawi.getParentCorpID2() + " ");
		}
//		上海电气新增扩展属性条件
		if (qawi.getExtendAttribute1()!=-1 && qawi.getExtendAttribute1()!=0)
		{
			m_sbFrom.append(" and client.NEXTENDATTRIBUTE1 = " + qawi.getExtendAttribute1() + " \n");
		}
		if (qawi.getExtendAttribute2()!=-1 && qawi.getExtendAttribute2()!=0)
		{
			m_sbFrom.append(" and client.NEXTENDATTRIBUTE2 = " + qawi.getExtendAttribute2() + " \n");
		}
		if (qawi.getExtendAttribute3()!=-1 && qawi.getExtendAttribute3()!=0)
		{
			m_sbFrom.append(" and client.NEXTENDATTRIBUTE3 = " + qawi.getExtendAttribute3() + " \n");
		}
		if (qawi.getExtendAttribute4()!=-1 && qawi.getExtendAttribute4()!=0)
		{
			m_sbFrom.append(" and client.NEXTENDATTRIBUTE4 = " + qawi.getExtendAttribute4() + " \n");
		}
		// 账户号
		if (qawi.getStartAccountNo() != null && qawi.getStartAccountNo().length() > 0)
			m_sbFrom.append("    and   acct.saccountno>='" + qawi.getStartAccountNo() + "' ");
		if (qawi.getEndAccountNo() != null && qawi.getEndAccountNo().length() > 0)
			m_sbFrom.append("    and   acct.saccountno<='" + qawi.getEndAccountNo() + "' ");
		//add by 2012-05-21 添加指定编号
		if(qawi.getAppointAccountNo() != null && qawi.getAppointAccountNo().length() > 0){
			m_sbFrom.append("    and   acct.saccountno in ('"+qawi.getAppointAccountNo()+"')");
		}
		//账户类型(suyf added)
		if (qawi.getAccountTypeID() > 0)
			m_sbFrom.append("    and   acct.naccounttypeid=" + qawi.getAccountTypeID() + " ");
		//账户状态
		if (qawi.getAccountStatusID() > 0)
			m_sbFrom.append("    and   acct.nStatusID='" + qawi.getAccountStatusID() + "' ");
		if (qawi.getEnterpriseTypeID1() > 0)
			m_sbFrom.append("    and   client.nClienttypeID1 = " + qawi.getEnterpriseTypeID1());
		if (qawi.getEnterpriseTypeID2() > 0)
			m_sbFrom.append("    and   client.nClienttypeID2 = " + qawi.getEnterpriseTypeID2());
		if (qawi.getEnterpriseTypeID3() > 0)
			m_sbFrom.append("    and   client.nClienttypeID3 = " + qawi.getEnterpriseTypeID3());
		if (qawi.getEnterpriseTypeID4() > 0)
			m_sbFrom.append("    and   client.nClienttypeID4 = " + qawi.getEnterpriseTypeID4());
		if (qawi.getEnterpriseTypeID5() > 0)
			m_sbFrom.append("    and   client.nClienttypeID5 = " + qawi.getEnterpriseTypeID5());
		if (qawi.getEnterpriseTypeID6() > 0)
			m_sbFrom.append("    and   client.nClienttypeID6 = " + qawi.getEnterpriseTypeID6());
		
		
		if (qawi.getIsNegotiate() > 0)
			m_sbFrom.append("    and   subAcct.ac_nIsNegotiate>0 ");
		if (qawi.getIsFilterNull() > 0)
			m_sbFrom.append("    and   subAcct.mBalance >0.0 ");
		if (qawi.getIsValidAccount() > 0)
			m_sbFrom.append("    and   subAcct.nStatusID=" + SETTConstant.SubAccountStatus.NORMAL);
		//放款单状态（根据需求，作子账户状态处理）
		if (qawi.getLoanPayStatusID() > 0)
		{
			m_sbFrom.append("    and   subAcct.nStatusID=" + qawi.getLoanPayStatusID());
		}
		// 自营贷款
		if (qawi.getLoanType() == LOANConstant.LoanType.ZY) {
			m_sbFrom.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.TRUST +" and nstatusId=1 and officeId="+qawi.getOfficeID()+" and currencyId="+qawi.getCurrencyID()+")");
		//　委托贷款
		} else if (qawi.getLoanType() == LOANConstant.LoanType.WT) {
			m_sbFrom.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.CONSIGN +" and nstatusId=1 and officeId="+qawi.getOfficeID()+" and currencyId="+qawi.getCurrencyID()+")");
		//　银团贷款
		} else if (qawi.getLoanType() == LOANConstant.LoanType.YT) {
			m_sbFrom.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.YT +" and nstatusId=1 and officeId="+qawi.getOfficeID()+" and currencyId="+qawi.getCurrencyID()+")");
		//　贴现
		} else if (qawi.getLoanType() == LOANConstant.LoanType.TX) {
			m_sbFrom.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.DISCOUNT +" and nstatusId=1 and officeId="+qawi.getOfficeID()+" and currencyId="+qawi.getCurrencyID()+")");
		//　担保
		} else if (qawi.getLoanType() == LOANConstant.LoanType.DB || qawi.getLoanType() == LOANConstant.LoanType.RZZL) {
			m_sbFrom.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.MARGIN +" and nstatusId=1 and officeId="+qawi.getOfficeID()+" and currencyId="+qawi.getCurrencyID()+")");
		//　其他贷款
		} else if (qawi.getLoanType() == LOANConstant.LoanType.OTHER) {
			m_sbFrom.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.OTHERLOAN +" and nstatusId=1 and officeId="+qawi.getOfficeID()+" and currencyId="+qawi.getCurrencyID()+")");
		} else {
			m_sbFrom.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID in ("
				+SETTConstant.AccountGroupType.TRUST+","
				+SETTConstant.AccountGroupType.CONSIGN+"," 
				+SETTConstant.AccountGroupType.YT+","
				+SETTConstant.AccountGroupType.DISCOUNT+","
				+SETTConstant.AccountGroupType.OTHERLOAN+","
				+SETTConstant.AccountGroupType.MARGIN
				+") and nstatusId=1 and officeId="+qawi.getOfficeID()+" and currencyId="+qawi.getCurrencyID()+")");
		}
		m_sbFrom.append(" ) a, \n");
		m_sbFrom.append(" (select * \n");
		m_sbFrom.append("  from sett_vContractInfo \n");
		m_sbFrom.append("  where officeid=" + qawi.getOfficeID() + " and currencyid=" + qawi.getCurrencyID() + " \n");
		// 贷款
		if (qawi.getContractYear() > 0)
		{
			m_sbFrom.append("      and ContractYear =" + qawi.getContractYear());
		}
		if (qawi.getConsignClientID() > 0)
		{
			m_sbFrom.append("      and ConsignClientID =" + qawi.getConsignClientID());
		}
		if (qawi.getLoanType() > 0)
		{
			m_sbFrom.append("      and LoanTypeID=" + qawi.getLoanType());
		}
		//合同状态 ContractStatusID
		if (qawi.getContractStatusID() > 0)
		{
			m_sbFrom.append("      and ContractStatusID=" + qawi.getContractStatusID());
		}
		//合同风险状态
		if (qawi.getRisklevel() > 0)
		{
			m_sbFrom.append("      and Risklevel=" + qawi.getRisklevel());
		}
		//合同行业分类1 Industrytype1
		if (qawi.getIndustrytype1() > 0)
		{
			m_sbFrom.append("      and Industrytype1=" + qawi.getIndustrytype1());
		}
		//合同行业分类2 Industrytype2
		if (qawi.getIndustrytype2() > 0)
		{
			m_sbFrom.append("      and Industrytype2=" + qawi.getIndustrytype2());
		}
		//2008-11-4增加 kaishao
        //是否进行账户监管 Industrytype3
		if (qawi.getIndustrytype3() > 0)
		{
			m_sbFrom.append("      and Industrytype3=" + qawi.getIndustrytype3());
		}
		//增加结束
		//合同地区分类 RegiontypeID
		if (qawi.getRegiontypeID() > 0)
		{
			m_sbFrom.append("      and RegiontypeID=" + qawi.getRegiontypeID());
		}
		//合同号 ContractCode
		if (qawi.getStartContractCode() != null && qawi.getStartContractCode().length() > 0)
		{
			m_sbFrom.append("      and ContractCode>='" + qawi.getStartContractCode() + "'");
		} //getEndContractCode
		if (qawi.getEndContractCode() != null && qawi.getEndContractCode().length() > 0)
		{
			m_sbFrom.append("      and ContractCode<='" + qawi.getEndContractCode() + "'");
		}
		//放款通知单 LoanpayCode
		if (qawi.getStartLoanPayCode() != null && qawi.getStartLoanPayCode().length() > 0)
		{
			m_sbFrom.append("      and LoanpayCode>='" + qawi.getStartLoanPayCode() + "'");
		} //getEndContractCode
		if (qawi.getEndLoanPayCode() != null && qawi.getEndLoanPayCode().length() > 0)
		{
			m_sbFrom.append("      and LoanpayCode<='" + qawi.getEndLoanPayCode() + "'");
		}
		//起始日期 ContractStartDate
		if (qawi.getStartContractStartDate() != null)
		{
			m_sbFrom.append("      and ContractStartDate>=to_date('" + qawi.getStartContractStartDate().toString().substring(0, 10) + "','yyyy-mm-dd') ");
		} //ContractStartDate
		if (qawi.getEndContractStartDate() != null)
		{
			m_sbFrom.append("      and ContractStartDate<=to_date('" + qawi.getEndContractStartDate().toString().substring(0, 10) + "','yyyy-mm-dd') ");
		}
		//到期日期 ContractEndDate
		if (qawi.getStartContractEndDate() != null)
		{
			m_sbFrom.append("      and ContractEndDate>=to_date('" + qawi.getStartContractEndDate().toString().substring(0, 10) + "','yyyy-mm-dd') ");
		} //ContractStartDate
		if (qawi.getEndContractEndDate() != null)
		{
			m_sbFrom.append("      and ContractEndDate<=to_date('" + qawi.getEndContractEndDate().toString().substring(0, 10) + "','yyyy-mm-dd') ");
		}
		
		/////////////////////////////////////////////////////////////////
		//	放款单起始日期 LoanPayStartDate
		if (qawi.getStartLoanPayStartDate() != null)
		{
			m_sbFrom.append("      and to_char(LoanPayStartDate,'yyyy-mm-dd') >='" + DataFormat.formatDate(qawi.getStartLoanPayStartDate()) + "' ");
		} //LoanPayStartDate
		if (qawi.getEndLoanPayStartDate() != null)
		{
			m_sbFrom.append("      and to_char(LoanPayStartDate,'yyyy-mm-dd') <='" + DataFormat.formatDate(qawi.getEndLoanPayStartDate()) + "' ");
		}
		//放款单到期日期 LoanpayEndDate
		if (qawi.getStartLoanPayEnddate() != null)
		{
			m_sbFrom.append("      and LoanpayEndDate>='" + DataFormat.formatDate(qawi.getStartLoanPayEnddate())+"'");
		} //LoanPayStartDate
		if (qawi.getEndLoanPayEnddate() != null)
		{
			m_sbFrom.append("      and LoanpayEndDate<='" + DataFormat.formatDate(qawi.getEndLoanPayEnddate())+"'");
		}
		   
		
		/////////////////////////////////////////////////////////
		
		
		//金额 合同金额 ContractAmount -改为匹配贷款金额 StartLoanpayAmount EndLoanpayAmount
		if (qawi.getStartLoanpayAmount() > 0.0)
		{
			m_sbFrom.append("      and LoanPayAmount>=" + qawi.getStartLoanpayAmount());
		}
		if (qawi.getEndLoanpayAmount() > 0.0)
		{
			m_sbFrom.append("      and LoanPayAmount<=" + qawi.getEndLoanpayAmount());
		}
		//利率 LoanPayRate
		if (qawi.getStartLoanPayRate() > 0.0)
		{
			m_sbFrom.append("      and LoanPayRate>=" + qawi.getStartLoanPayRate());
            //m_sbFrom.append("      and contractInterestRate>=" + qawi.getStartLoanPayRate());
		} //ContractStartDate
		if (qawi.getEndLoanPayRate() > 0.0)
		{
			m_sbFrom.append("      and LoanPayRate<=" + qawi.getEndLoanPayRate());
            //m_sbFrom.append("      and contractInterestRate<=" + qawi.getEndLoanPayRate());
		}
		//贷款期限 ContractPeriod
		if (qawi.getStartContractPeriod() > 0)
		{
			m_sbFrom.append("      and ContractPeriod=" + qawi.getStartContractPeriod());
		}
		
		m_sbFrom.append(" ) b        \n");
		// where
		m_sbWhere = new StringBuffer();
		m_sbWhere.append("  a.LoanPayID=b.LoanPayID \n");
		//
		m_sbOrderBy = new StringBuffer();
		String strDesc = qawi.getDesc() == 1 ? " desc " : "";
		switch ((int) qawi.getOrderField())
		{
			//账户号
			case OrderBy_Loan_AccountNo :
				m_sbOrderBy.append(" \n order by AccountNo " + strDesc);
				break;
			//合同号
			case OrderBy_Loan_ContractNo :
				m_sbOrderBy.append(" \n order by ContractCode" + strDesc);
				break;
			//合同状态
			case OrderBy_Loan_ContractStatus :
				m_sbOrderBy.append(" \n order by ContractStatusID" + strDesc);
				break;
			//贷款放款通知单
			case OrderBy_Loan_PayCode :
				m_sbOrderBy.append(" \n order by LoanpayCode" + strDesc);
				break;
			//放款通知单状态
			case OrderBy_Loan_PayStatus :
				m_sbOrderBy.append(" \n order by SubAccountStatusID" + strDesc);
				break;
			//客户名称
			case OrderBy_Loan_ClientName :
				m_sbOrderBy.append(" \n order by ClientName" + strDesc);
				break;
			//贷款金额
			case OrderBy_Loan_Amount :
				m_sbOrderBy.append(" \n order by LoanPayAmount" + strDesc);
				break;
			//当前余额
			case OrderBy_Loan_Balance :
				m_sbOrderBy.append(" \n order by Balance" + strDesc);
				break;
			//利率
			case OrderBy_Loan_InterestRate :
				m_sbOrderBy.append(" \n order by ContractInterestRate" + strDesc);
				break;
			//应付利息
			case OrderBy_Loan_Interest :
				m_sbOrderBy.append(" \n order by Interest" + strDesc);
				break;
			//计提利息
			case OrderBy_Loan_DrawInterest :
				m_sbOrderBy.append(" \n order by LoanPreDrawInterest" + strDesc);
				break;
				//放款开始日期
			case OrderBy_Loan_PayStartDate :
				m_sbOrderBy.append(" \n order by LoanPayStartDate" + strDesc);
				break;
				//放款结束日期
			case OrderBy_Loan_PayEndDate :
				m_sbOrderBy.append(" \n order by LoanpayEndDate" + strDesc);
				break;
			default :
				m_sbOrderBy.append(" \n order by AccountNo,ContractCode,LoanpayCode" + strDesc);
				break;
		}
	}
	/**
	 * 查询贷款余额信息
	 * @param qawi
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryLoanAccountBalance(QueryAccountWhereInfo qawi) throws Exception
	{
		if (qawi.getStartAccountNo() != null && qawi.getStartAccountNo().length() > 0)
			qawi.setStartClientCode(null);
		if (qawi.getEndAccountNo() != null && qawi.getEndAccountNo().length() > 0)
			qawi.setEndClientCode(null); 
		//
		if (isToday(qawi.getOfficeID(), qawi.getCurrencyID(), qawi.getQueryDate()))
			getTodayLoanAcountBalace(qawi);
		else
			getHistoryLoanAccountBalance(qawi);
		
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(new AppContext(), m_sbFrom.toString(), m_sbSelect.toString(), m_sbWhere.toString(), (int) Constant.PageControl.CODE_PAGELINECOUNT,
				"com.iss.itreasury.settlement.query.resultinfo.QueryAccountBalanceResultInfo", null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		
		System.out.println(m_sbFrom.toString()+m_sbSelect.toString()+m_sbWhere.toString());
		return pageLoader;
	}
	/**
	 * 查询开机日之前的贷款余额
	 * @param qawi
	 */
	public void getHistoryLoanAccountBalance(QueryAccountWhereInfo qawi)
	{
		//String JoinOut = "";
		// select
		m_sbSelect = new StringBuffer();
		m_sbSelect.append("        -- 贷款 \n");
		m_sbSelect.append("        a.LoanPayID, round(a.LoanPreDrawInterest,2) LoanPreDrawInterest,\n");
		m_sbSelect.append("        -- 贷款 \n");
		m_sbSelect.append("        b.ContractID,b.LoanTypeID,b.ContractStatusID,round(b.ContractAmount,2) ContractAmount,round(b.LoanPayAmount,2) LoanPayAmount, \n");
		m_sbSelect.append("        b.ContractPeriod,b.ContractYear,b.ContractCode, b.LoanpayCode,b.ConsignClientID,b.ContractStartDate,b.ContractEndDate, \n");
			//下行于2008-11-4修改 内容:增加"b.IndustryType3," kaishao
		m_sbSelect.append("        b.ContractInterestRate,b.LoanPayStartDate,b.LoanpayEndDate,b.IndustryType1,b.IndustryType2,b.IndustryType3,b.RegionTypeID,b.ClientTypeID1, \n");
		m_sbSelect.append("        b.ClientTypeID2,b.ClientTypeID3,b.ClientTypeID4,b.ClientTypeID5,b.ClientTypeID6, \n");
		m_sbSelect.append("        b.RiskLevel,b.ParentCorpID,b.LoanPayRate,b.CommissionRate,b.SecutyFeeRate,b.CommissionTypeID \n");
		// from
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" (select client.id ClientID,Client.sCode ClientCode,Client.sname ClientName,acct.id AccountID,acct.saccountno AccountNo,acct.naccounttypeid AccountTypeID, \n");
		m_sbFrom.append("        acct.nstatusID AccountStatusID,acct.nOfficeID OfficeID,acct.nCurrencyID CurrencyID, \n");
		m_sbFrom.append("        subAcct.id SubAccountID, subAcct.nstatusid SubAccountStatusID,daily.mbalance Balance,subAcct.mOpenAmount OpenAmount, \n");
		m_sbFrom.append("        (daily.mInterest-daily.mDailyInterest) Interest, subAcct.dtOpen InterestDate, subAcct.dtFinish FinishDate, 0.0 UncheckPaymentAmount, \n");
		m_sbFrom.append("        subAcct.dtClearInterest ClearInterestDate, daily.dtDate BalanceDate,daily.mInterestRate InterestRate, \n");
		m_sbFrom.append("        subAcct.dtOpen AccountOpenDate, \n");
		m_sbFrom.append("        -- 贷款 \n");
		m_sbFrom.append("        subAcct.al_nLoanNoteID LoanPayID,subAcct.al_mPreDrawInterest LoanPreDrawInterest \n");
		//modify by lxr 2004-01-07 begin
		//modify by forest,20040517,加上跟loan_discountcredence的关联。
//		modify by ZYYAO,20070807,加上跟LOAN_ASSURECHARGEFORM的关联。
		m_sbFrom.append(" from client,sett_account acct, sett_subAccount subAcct,(select ID,dtEnd from loan_PayForm union select ID,dtAtterm dtEnd from loan_DiscountCredence union select ID,enddate dtEnd from LOAN_ASSURECHARGEFORM ) payform,sett_DailyAccountBalance daily \n");
		m_sbFrom.append("  where client.id=acct.nclientid and acct.id=subAcct.nAccountID and subAcct.AL_nLoanNoteID = payform.ID and acct.nofficeid=" + qawi.getOfficeID() + " and acct.ncurrencyid="
				+ qawi.getCurrencyID() + " \n");
		m_sbFrom.append("         and subAcct.NSTATUSID>0 and subAcct.id=daily.nSubAccountID and daily.dtDate=to_date('" + DataFormat.getDateString(qawi.getQueryDate()) + "','yyyy-mm-dd') \n");
		if (qawi.getDtFinish() != null)
		{
			//处理截止日期
			if (qawi.getDtFinish().after(Env.getSystemDate(qawi.getOfficeID(), qawi.getCurrencyID())))
			{
				m_sbFrom.append("  and payform.dtEnd>=to_date('" + DataFormat.formatDate(qawi.getDtFinish()) + "','yyyy-mm-dd') ");
			} else
			{
				m_sbFrom.append(" and (subAcct.dtFinish>=to_date('" + DataFormat.formatDate(qawi.getDtFinish()) + "','yyyy-mm-dd') ");
				m_sbFrom.append("   or subAcct.dtFinish is null ) ");
			}
		}
		//modify by lxr 2004-01-07 end
		//  客户号
		if (qawi.getStartClientCode() != null && qawi.getStartClientCode().length() > 0)
			m_sbFrom.append("    and   client.scode>='" + qawi.getStartClientCode() + "' ");
		if (qawi.getEndClientCode() != null && qawi.getEndClientCode().length() > 0)
			m_sbFrom.append("    and   client.scode<='" + qawi.getEndClientCode() + "' ");
		//上级单位1 nParentCorpID1
		if (qawi.getParentCorpID1() > 0)
		{
			m_sbFrom.append("    and   client.nParentCorpID1=" + qawi.getParentCorpID1() + " ");
		}
		//上级单位2 nParentCorpID2
		if (qawi.getParentCorpID2() > 0)
		{
			m_sbFrom.append("    and   client.nParentCorpID2=" + qawi.getParentCorpID2() + " ");
		}
//		上海电气新增扩展属性条件
		if (qawi.getExtendAttribute1()!=-1 && qawi.getExtendAttribute1()!=0)
		{
			m_sbFrom.append(" and client.NEXTENDATTRIBUTE1 = " + qawi.getExtendAttribute1() + " \n");
		}
		if (qawi.getExtendAttribute2()!=-1 && qawi.getExtendAttribute2()!=0)
		{
			m_sbFrom.append(" and client.NEXTENDATTRIBUTE2 = " + qawi.getExtendAttribute2() + " \n");
		}
		if (qawi.getExtendAttribute3()!=-1 && qawi.getExtendAttribute3()!=0)
		{
			m_sbFrom.append(" and client.NEXTENDATTRIBUTE3 = " + qawi.getExtendAttribute3() + " \n");
		}
		if (qawi.getExtendAttribute4()!=-1 && qawi.getExtendAttribute4()!=0)
		{
			m_sbFrom.append(" and client.NEXTENDATTRIBUTE4 = " + qawi.getExtendAttribute4() + " \n");
		}
		// 账户号
		if (qawi.getStartAccountNo() != null && qawi.getStartAccountNo().length() > 0)
			m_sbFrom.append("    and   acct.saccountno>='" + qawi.getStartAccountNo() + "' ");
		if (qawi.getEndAccountNo() != null && qawi.getEndAccountNo().length() > 0)
			m_sbFrom.append("    and   acct.saccountno<='" + qawi.getEndAccountNo() + "' ");
		//add by 2012-05-21 添加指定编号
		if(qawi.getAppointAccountNo() != null && qawi.getAppointAccountNo().length() > 0){
			m_sbFrom.append("    and   acct.saccountno in ('"+qawi.getAppointAccountNo()+"')");
		}
		//账户类型(suyf added)
		if (qawi.getAccountTypeID() > 0)
			m_sbFrom.append("    and   acct.naccounttypeid=" + qawi.getAccountTypeID() + " ");
		//账户状态
		if (qawi.getAccountStatusID() > 0)
			m_sbFrom.append("    and   acct.nStatusID='" + qawi.getAccountStatusID() + "' ");
		if (qawi.getEnterpriseTypeID1() > 0)
			m_sbFrom.append("    and   client.nClienttypeID1 = " + qawi.getEnterpriseTypeID1());
		if (qawi.getEnterpriseTypeID2() > 0)
			m_sbFrom.append("    and   client.nClienttypeID2 = " + qawi.getEnterpriseTypeID2());
		if (qawi.getEnterpriseTypeID3() > 0)
			m_sbFrom.append("    and   client.nClienttypeID3 = " + qawi.getEnterpriseTypeID3());
		if (qawi.getEnterpriseTypeID4() > 0)
			m_sbFrom.append("    and   client.nClienttypeID4 = " + qawi.getEnterpriseTypeID4());
		if (qawi.getEnterpriseTypeID5() > 0)
			m_sbFrom.append("    and   client.nClienttypeID5 = " + qawi.getEnterpriseTypeID5());
		if (qawi.getEnterpriseTypeID6() > 0)
			m_sbFrom.append("    and   client.nClienttypeID6 = " + qawi.getEnterpriseTypeID6());
		
		if (qawi.getIsNegotiate() > 0)
			m_sbFrom.append("    and   subAcct.ac_nIsNegotiate>0 ");
		if (qawi.getIsFilterNull() > 0)
			m_sbFrom.append("    and   subAcct.mBalance>0");
		if (qawi.getIsValidAccount() > 0)
			m_sbFrom.append("    and   subAcct.nStatusID=" + SETTConstant.SubAccountStatus.NORMAL);
		//放款单状态（根据需求，作子账户状态处理）
		if (qawi.getLoanPayStatusID() > 0)
		{
			m_sbFrom.append("    and   subAcct.nStatusID=" + qawi.getLoanPayStatusID());
		}
		// 自营贷款
		if (qawi.getLoanType() == LOANConstant.LoanType.ZY) {
			m_sbFrom.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.TRUST +" and nstatusId=1 and officeId="+qawi.getOfficeID()+" and currencyId="+qawi.getCurrencyID()+")");
		//　委托贷款
		} else if (qawi.getLoanType() == LOANConstant.LoanType.WT) {
			m_sbFrom.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.CONSIGN +" and nstatusId=1 and officeId="+qawi.getOfficeID()+" and currencyId="+qawi.getCurrencyID()+")");
		//　银团贷款
		} else if (qawi.getLoanType() == LOANConstant.LoanType.YT) {
			m_sbFrom.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.YT +" and nstatusId=1 and officeId="+qawi.getOfficeID()+" and currencyId="+qawi.getCurrencyID()+")");
		//　贴现
		} else if (qawi.getLoanType() == LOANConstant.LoanType.TX) {
			m_sbFrom.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.DISCOUNT +" and nstatusId=1 and officeId="+qawi.getOfficeID()+" and currencyId="+qawi.getCurrencyID()+")");
		//　担保
		} else if (qawi.getLoanType() == LOANConstant.LoanType.DB || qawi.getLoanType() == LOANConstant.LoanType.RZZL) {
			m_sbFrom.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.MARGIN +" and nstatusId=1 and officeId="+qawi.getOfficeID()+" and currencyId="+qawi.getCurrencyID()+")");
		//　其他贷款
		} else if (qawi.getLoanType() == LOANConstant.LoanType.OTHER) {
			m_sbFrom.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.OTHERLOAN +" and nstatusId=1 and officeId="+qawi.getOfficeID()+" and currencyId="+qawi.getCurrencyID()+")");
		} else {
			m_sbFrom.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID in ("
				+SETTConstant.AccountGroupType.TRUST+","
				+SETTConstant.AccountGroupType.CONSIGN+"," 
				+SETTConstant.AccountGroupType.YT+","
				+SETTConstant.AccountGroupType.DISCOUNT+","
				+SETTConstant.AccountGroupType.OTHERLOAN+","
				+SETTConstant.AccountGroupType.MARGIN
				+") and nstatusId=1 and officeId="+qawi.getOfficeID()+" and currencyId="+qawi.getCurrencyID()+")");
		}
		m_sbFrom.append(" ) a, \n");
		// sett_VContractInfo
		m_sbFrom.append(" (select * \n");
		m_sbFrom.append("  from sett_vContractInfo \n");
		m_sbFrom.append("  where officeid=" + qawi.getOfficeID() + " and currencyid=" + qawi.getCurrencyID() + " \n");
		// 贷款
		if (qawi.getContractYear() > 0)
		{
			m_sbFrom.append("      and ContractYear =" + qawi.getContractYear());
			//JoinOut= "";
		}
		if (qawi.getConsignClientID() > 0)
		{
			m_sbFrom.append("      and ConsignClientID =" + qawi.getConsignClientID());
			//JoinOut= "";
		}
		if (qawi.getLoanType() > 0)
		{
			m_sbFrom.append("      and LoanTypeID=" + qawi.getLoanType());
			//JoinOut= "";
		}
		//			合同状态 ContractStatusID
		if (qawi.getContractStatusID() > 0)
		{
			m_sbFrom.append("      and ContractStatusID=" + qawi.getContractStatusID());
		}
		//合同风险状态
		if (qawi.getRisklevel() > 0)
		{
			m_sbFrom.append("      and Risklevel=" + qawi.getRisklevel());
		}
		//合同行业分类1 Industrytype1
		if (qawi.getIndustrytype1() > 0)
		{
			m_sbFrom.append("      and Industrytype1=" + qawi.getIndustrytype1());
		}
		//合同行业分类2 Industrytype2
		if (qawi.getIndustrytype2() > 0)
		{
			m_sbFrom.append("      and Industrytype2=" + qawi.getIndustrytype2());
		}
		//合同地区分类 RegiontypeID
		if (qawi.getRegiontypeID() > 0)
		{
			m_sbFrom.append("      and RegiontypeID=" + qawi.getRegiontypeID());
		}
		//2008-11-4增加 kaishao
		//是否进行账户监管 Industrytype3
		if (qawi.getIndustrytype3() > 0)
		{
			m_sbFrom.append("      and Industrytype3=" + qawi.getIndustrytype3());
		}
		//增加结束
		//合同号 ContractCode
		if (qawi.getStartContractCode() != null && (!qawi.getStartContractCode().equals("")))
		{
			m_sbFrom.append("      and ContractCode>='" + qawi.getStartContractCode() + "'");
		} //getEndContractCode
		if (qawi.getEndContractCode() != null && qawi.getEndContractCode().length() > 0)
		{
			m_sbFrom.append("      and ContractCode<='" + qawi.getEndContractCode() + "'");
		}
		//放款通知单 LoanpayCode
		if (qawi.getStartLoanPayCode() != null && qawi.getStartLoanPayCode().length() > 0)
		{
			m_sbFrom.append("      and LoanpayCode>='" + qawi.getStartLoanPayCode() + "'");
		} //getEndContractCode
		if (qawi.getEndLoanPayCode() != null && qawi.getEndLoanPayCode().length() > 0)
		{
			m_sbFrom.append("      and getEndContractCode<='" + qawi.getEndLoanPayCode() + "'");
		}
		//合同起始日期 ContractStartDate
		if (qawi.getStartContractStartDate() != null)
		{
			m_sbFrom.append("      and ContractStartDate>=to_date('" + qawi.getStartContractStartDate().toString().substring(0, 10) + "','yyyy-mm-dd') ");
		} //ContractStartDate
		if (qawi.getEndContractStartDate() != null)
		{
			m_sbFrom.append("      and ContractStartDate<=to_date('" + qawi.getEndContractStartDate().toString().substring(0, 10) + "','yyyy-mm-dd') ");
		}
		//合同到期日期 ContractEndDate
		if (qawi.getStartContractEndDate() != null)
		{
			m_sbFrom.append("      and ContractEndDate>=to_date('" + qawi.getStartContractEndDate().toString().substring(0, 10) + "','yyyy-mm-dd') ");
		} //合同ContractStartDate
		if (qawi.getEndContractEndDate() != null)
		{
			m_sbFrom.append("      and ContractEndDate<=to_date('" + qawi.getEndContractEndDate().toString().substring(0, 10) + "','yyyy-mm-dd') ");
		}
		/////////////////////////////////////////////////////////////////
		//	放款单起始日期 LoanPayStartDate
		if (qawi.getStartLoanPayStartDate() != null)
		{
			m_sbFrom.append("      and to_char(LoanPayStartDate,'yyyy-mm-dd') >='" + DataFormat.formatDate(qawi.getStartLoanPayStartDate()) + "' ");
		} //LoanPayStartDate
		if (qawi.getEndLoanPayStartDate() != null)
		{
			m_sbFrom.append("      and to_char(LoanPayStartDate,'yyyy-mm-dd') <='" + DataFormat.formatDate(qawi.getEndLoanPayStartDate()) + "' ");
		}
		//放款单到期日期 LoanPayEndDate
		if (qawi.getStartLoanPayEnddate() != null)
		{
			m_sbFrom.append("      and LoanpayEndDate>='" + DataFormat.formatDate(qawi.getStartLoanPayEnddate())+"'");
		} //LoanPayStartDate
		if (qawi.getEndLoanPayEnddate() != null)
		{
			m_sbFrom.append("      and LoanpayEndDate<='" + DataFormat.formatDate(qawi.getEndLoanPayEnddate())+"'");
		}
		   
		
		/////////////////////////////////////////////////////////
		//金额 合同金额 ContractAmount -改为匹配贷款金额 StartLoanpayAmount EndLoanpayAmount
		if (qawi.getStartLoanpayAmount() > 0.0)
		{
			m_sbFrom.append("      and LoanPayAmount>=" + qawi.getStartLoanpayAmount());
		}
		if (qawi.getEndLoanpayAmount() > 0.0)
		{
			m_sbFrom.append("      and LoanPayAmount<=" + qawi.getEndLoanpayAmount());
		}
		//利率 LoanPayRate
		if (qawi.getStartLoanPayRate() > 0.0)
		{
			m_sbFrom.append("      and LoanPayRate>=" + qawi.getStartLoanPayRate());
		} //ContractStartDate
		if (qawi.getEndLoanPayRate() > 0.0)
		{
			m_sbFrom.append("      and LoanPayRate<=" + qawi.getEndLoanPayRate());
		}
		//贷款期限 ContractPeriod
		if (qawi.getStartContractPeriod() > 0)
		{
			m_sbFrom.append("      and ContractPeriod=" + qawi.getStartContractPeriod());
		}
		//贷款发放至活期账户号
		//贷款发放/收回至银行开户行
		m_sbFrom.append(" ) b        \n");
		// where
		m_sbWhere = new StringBuffer();
		//m_sbWhere.append(" a.LoanPayID=b.LoanPayID"+JoinOut+" \n");
		m_sbWhere.append("  a.LoanPayID=b.LoanPayID \n");
		// order by
		m_sbOrderBy = new StringBuffer();
		String strDesc = qawi.getDesc() == 1 ? " desc " : "";
		switch ((int) qawi.getOrderField())
		{
			//账户号
			case OrderBy_Loan_AccountNo :
				m_sbOrderBy.append(" \n order by AccountNo,ContractCode,LoanpayCode" + strDesc);
				break;
			//合同号
			case OrderBy_Loan_ContractNo :
				m_sbOrderBy.append(" \n order by ContractCode" + strDesc);
				break;
			//合同状态
			case OrderBy_Loan_ContractStatus :
				m_sbOrderBy.append(" \n order by ContractStatusID" + strDesc);
				break;
			//贷款放款通知单
			case OrderBy_Loan_PayCode :
				m_sbOrderBy.append(" \n order by LoanpayCode" + strDesc);
				break;
			//放款通知单状态
			case OrderBy_Loan_PayStatus :
				m_sbOrderBy.append(" \n order by SubAccountStatusID" + strDesc);
				break;
			//客户名称
			case OrderBy_Loan_ClientName :
				m_sbOrderBy.append(" \n order by ClientName" + strDesc);
				break;
			//贷款金额
			case OrderBy_Loan_Amount :
				m_sbOrderBy.append(" \n order by LoanPayAmount" + strDesc);
				break;
			//当前余额
			case OrderBy_Loan_Balance :
				m_sbOrderBy.append(" \n order by Balance" + strDesc);
				break;
			//利率
			case OrderBy_Loan_InterestRate :
				m_sbOrderBy.append(" \n order by InterestRate" + strDesc);
				break;
			//应付利息
			case OrderBy_Loan_Interest :
				m_sbOrderBy.append(" \n order by Interest" + strDesc);
				break;
			//计提利息
			case OrderBy_Loan_DrawInterest :
				m_sbOrderBy.append(" \n order by FixPreDrawInterest" + strDesc);
				break;
				//放款开始日期
			case OrderBy_Loan_PayStartDate :
				m_sbOrderBy.append(" \n order by LoanPayStartDate" + strDesc);
				break;
				//放款结束日期
			case OrderBy_Loan_PayEndDate :
				m_sbOrderBy.append(" \n order by LoanpayEndDate" + strDesc);
				break;
			default :
				m_sbOrderBy.append(" \n order by AccountNo,ContractCode,LoanpayCode" + strDesc);
				break;
		}
	}
	/*
	 * 取贷款查询余额合计
	 *  
	 */
	public QueryAccountSumInfo queryLoanAccountBalanceSum(QueryAccountWhereInfo qawi) throws Exception
	{
		QueryAccountSumInfo sumObj = new QueryAccountSumInfo();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String strSelect = "";
		String strDepositWhere = "";
		String strLoanWhere = "";
		String sql = "";
		//
		try
		{
			if (qawi.getStartAccountNo() != null && qawi.getStartAccountNo().length() > 0)
				qawi.setStartClientCode(null);
			if (qawi.getEndAccountNo() != null && qawi.getEndAccountNo().length() > 0)
				qawi.setEndClientCode(null);
			//
			//modify by lxr begin
			qawi.setQueryDate(qawi.getDtFinish());
			//modify by lxr end
			if (isToday(qawi.getOfficeID(), qawi.getCurrencyID(), qawi.getQueryDate()))
				getTodayLoanAcountBalace(qawi);
			else
				getHistoryLoanAccountBalance(qawi);
			m_sbSelect.setLength(0);
			m_sbSelect.append(" SELECT  nvl(sum(round(a.Balance,2)),0) BalanceSum,nvl(sum(round(a.interest,2)),0) InterestSum \n");
			con = this.getConnection();
			sql = m_sbSelect.toString() + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString();
			logger.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//
				sumObj.setBalanceSum(rs.getDouble("BalanceSum"));
				sumObj.setInterestSum(rs.getDouble("InterestSum"));
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		} catch (Exception exp)
		{
			throw exp;
		} finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return sumObj;
	}
	/**
	 * @return
	 */
	public StringBuffer getOrderBy()
	{
		return m_sbOrderBy;
	}
	/**
	 * @param orderBy
	 */
	public void setOrderBy(StringBuffer orderBy)
	{
		m_sbOrderBy = orderBy;
	}
	public QueryAccountBalanceResultInfo[] queryAccountBalanceInOnePage(QueryAccountWhereInfo qawi) throws Exception
	{
		if (qawi.getStartAccountNo() != null && qawi.getStartAccountNo().length() > 0)
		{
			qawi.setStartClientCode(null);
		}
		if (qawi.getEndAccountNo() != null && qawi.getEndAccountNo().length() > 0)
		{
			qawi.setEndClientCode(null);
		}
		//
		if (isToday(qawi.getOfficeID(), qawi.getCurrencyID(), qawi.getQueryDate()))
		{
			getTodayLoanAcountBalace(qawi);
		} else
		{
			getHistoryLoanAccountBalance(qawi);
		}
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(new AppContext(), m_sbFrom.toString(), m_sbSelect.toString(), m_sbWhere.toString(), 9999,
				"com.iss.itreasury.settlement.query.resultinfo.QueryAccountBalanceResultInfo", null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return (QueryAccountBalanceResultInfo[]) pageLoader.firstPage();
	}
}
