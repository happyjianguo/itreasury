package com.iss.itreasury.loan.overdueapply.dao;

import com.iss.itreasury.loan.util.LOANConstant;

public class OverdueLoanDao {

	public String queryContractByMultiOption(long lLoanType,long lCurrencyID,long lOfficeID,long lUserID,
			long lContractIDFrom,long lContractIDTo,long lClientID,String tsLoanStart,String tsLoanEnd,long lStatusID){
		
		StringBuffer sql = new StringBuffer();
		sql.append("select a.id as ContractID,a.sContractCode as ContractCode,c.sName as BorrowClientName, ");
		sql.append(" 	a.mloanamount,a.mexamineamount,a.nintervalnum as LoanTime,a.nstatusid,a.DtStartDate,a.DtEndDate ");
		sql.append("  from loan_contractform a, client c ");
		sql.append(" where c.id(+) = a.nborrowclientid and a.nStatusID in ");
		sql.append("   ("+LOANConstant.ContractStatus.ACTIVE+","+LOANConstant.ContractStatus.EXTEND+","+LOANConstant.ContractStatus.OVERDUE+") ");
		sql.append("   and a.nTypeID in ("+LOANConstant.LoanType.RZZL+","+LOANConstant.LoanType.WT+","+LOANConstant.LoanType.ZY+","+LOANConstant.LoanType.YT+") ");
		
		if (lCurrencyID > -1) {
			sql.append(" and a.nCurrencyID = " + lCurrencyID);
		}
		if (lOfficeID > -1) {
			sql.append(" and a.nOfficeID = " + lOfficeID);
		}
		if (lUserID > -1) {
			sql.append(" and a.nInputUserID= " + lUserID);
		}
		//合同贷款类型
		if (lLoanType > -1) {
			sql.append(" and a.nTypeID = " + lLoanType);
		}
		//合同起始ID
		if (lContractIDFrom > -1) {
			sql.append(" and a.ID >= " + lContractIDFrom);
		}
		//合同结束ID
		if (lContractIDTo > -1) {
			sql.append(" and a.ID <= " + lContractIDTo);
		}
		//贷款单位
		if (lClientID > -1) {
			sql.append(" and a.nborrowclientid = " + lClientID);
		}
		if (tsLoanStart != null && !"".equals(tsLoanStart)) {
			sql.append(" and to_char(a.dtstartdate,'yyyy-mm-dd') >= '"+tsLoanStart+"'");
		}
		if (tsLoanEnd != null && !"".equals(tsLoanEnd)) {
			sql.append(" and to_char(a.dtenddate,'yyyy-mm-dd') <= '"+tsLoanEnd+"'");
		}
		// 合同状态
		if (lStatusID > -1) {
			sql.append(" and a.nStatusID = " + lStatusID);
		}
		
		return sql.toString();
	}
	public String queryOverDuePlanByContractPlanID(long lContractPlanID){
		
		StringBuffer sql = new StringBuffer();
		sql.append("select a.id, a.dtplandate,a.mamount,b.id OverDueID,b.mplanbalance,b.mfineamount,b.mfineinterestrate, ");
		sql.append(" 	b.dtfinedate,b.nstatusid,b.nnextcheckuserid,c.ncontractid,c.nisused,c.nusertype ");
		sql.append("  from loan_loancontractplandetail a,loan_overdueform b,loan_loancontractplan c ");
		sql.append(" where 1=1 and a.ncontractplanid = c.id(+) and a.nlastoverdueid = b.id(+) ");
		sql.append("   and a.ncontractplanid = "+lContractPlanID+" and a.npaytypeid = "+LOANConstant.PlanType.REPAY );
		
		return sql.toString();
	}
	
}
