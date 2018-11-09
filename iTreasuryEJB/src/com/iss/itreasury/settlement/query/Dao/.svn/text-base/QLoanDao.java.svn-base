package com.iss.itreasury.settlement.query.Dao;

import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.query.paraminfo.QueryAccountWhereInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;


/**
 * 贷款通知书查询数据层
 * @author songwenxiao
 *
 */
public class QLoanDao {
	
	public final static int OrderBy_FormYear = 1;	
	public final static int OrderBy_FormNo = 2;	
	//
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
	Log4j logger = null;
	
	public QLoanDao()
	{
		super();
		logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	}
	
	public String getLoanNoticeBookSQL(QueryAccountWhereInfo qawi, long lUnit)
	{
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" distinct a.BalanceDate,a.OfficeID,a.CurrencyID,a.ClientID,a.ClientCode,a.ClientName,a.AccountID,a.AccountNo,a.AccountTypeID,a.AccountStatusID, \n");
		m_sbSelect.append("        a.SubAccountID,a.SubAccountStatusID,round(a.Balance/"+lUnit+",2) Balance,round(a.OpenAmount/"+lUnit+",2) OpenAmount,round(a.Interest/"+lUnit+",2) Interest, \n");
		m_sbSelect.append("        a.InterestDate,a.FinishDate,round(a.UnCheckPaymentAmount,2) UnCheckPaymentAmount, \n");
		m_sbSelect.append("        a.ClearInterestDate,a.InterestRate,a.AccountOpenDate, 1 isToday ,\n");
		m_sbSelect.append("        -- 贷款 \n");
		m_sbSelect.append("        a.LoanPayID, round(a.LoanPreDrawInterest/"+lUnit+",2) LoanPreDrawInterest,\n");
		m_sbSelect.append("        -- 贷款 \n");
		m_sbSelect.append("        b.ContractID,b.LoanTypeID,b.ContractStatusID,round(b.ContractAmount/"+lUnit+",2) ContractAmount,round(b.LoanPayAmount/"+lUnit+",2) LoanPayAmount, \n");
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
		String sql="select "+m_sbSelect+" from "+m_sbFrom+" where "+m_sbWhere;
		return sql;
	}

}
