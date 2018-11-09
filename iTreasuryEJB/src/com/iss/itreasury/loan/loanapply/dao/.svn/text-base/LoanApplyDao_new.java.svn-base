package com.iss.itreasury.loan.loanapply.dao;

import com.iss.itreasury.loan.util.LOANConstant;

public class LoanApplyDao_new {
	
	/**
	 * 查询贷款申请的所有计划明细SQL
	 * @param lLoanID
	 */
	public String queryPlanByLoanID(long lLoanID){
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select lpd.*,l.minterestrate,l.mstaidadjustrate,l.madjustrate,l.ntypeid,l.id loanID ");
		sql.append(" from loan_LoanFormPlanDetail lpd, loan_LoanFormPlan lp, loan_LoanForm l ");
		sql.append(" where 1=1 and lpd.nplanid = lp.id and lp.nloanid = l.id and l.id = " + lLoanID);
		
		return sql.toString();
	}
	/**
	 * 查询贷款申请的所有计划明细SQL
	 * @param lLoanID
	 */
	public String queryLoanApplyGuaranteeByLoanID(long lLoanID){
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select a.id as id,a.nloanid as nLoanID,a.nassuretypeid as nAssureTypeID,a.Nfillquestionary as nFillQuestionary,a.nclientid as nClientID, ");
		sql.append(" a.mamount as mAmount,a.simpawname as sImpawName,a.simpawquality as sImpawQuality,a.simpawstatus as sImpawStatus,a.mpledgeamount as mPledGeAmount, ");
		sql.append(" a.mpledgerate as mPledGeRate,a.nstatusid as nStatusID,a.sassurecode as sAssureCode,a.nistopassure as nIsTopAssure,a.clienttype as clienttype, ");
		sql.append(" l.mloanamount,b.sCode,b.sName,b.sContacter,b.sPhone,b.SPROVINCE,b.SCITY,b.SADDRESS,b.SBANK1,b.SEXTENDACCOUNT1 ");
		sql.append(" from loan_loanformassure a,loan_LoanForm l,client b ");
		sql.append(" where a.nLoanID=l.id and b.id=a.nclientid and a.nLoanID="+lLoanID+" and a.clienttype="+LOANConstant.LoanClientType.INTERIOR);
		sql.append(" union");
		sql.append(" select a.id as id,a.nloanid as nLoanID,a.nassuretypeid as nAssureTypeID,a.Nfillquestionary as nFillQuestionary,a.nclientid as nClientID, ");
		sql.append(" a.mamount as mAmount,a.simpawname as sImpawName,a.simpawquality as sImpawQuality,a.simpawstatus as sImpawStatus,a.mpledgeamount as mPledGeAmount, ");
		sql.append(" a.mpledgerate as mPledGeRate,a.nstatusid as nStatusID,a.sassurecode as sAssureCode,a.nistopassure as nIsTopAssure,a.clienttype as clienttype, ");
		sql.append(" l.mloanamount,c.code as sCode,c.name as sName,c.linkman as sContacter, c.tel as sPhone, ' ' as SPROVINCE,' ' as SCITY,' ' as SADDRESS,' ' as SBANK1,' ' as SEXTENDACCOUNT1 ");
		sql.append(" from loan_loanformassure a,loan_LoanForm l,client_extclientinfo c ");
		sql.append(" where a.nLoanID=l.id and a.nclientid=c.id and a.nloanid="+lLoanID+" and a.clienttype="+LOANConstant.LoanClientType.EXTERIOR);
		
		return sql.toString();
	}

}
