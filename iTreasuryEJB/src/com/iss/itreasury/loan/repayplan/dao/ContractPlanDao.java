package com.iss.itreasury.loan.repayplan.dao;

import com.iss.itreasury.loan.repayplan.dataentity.QueryContractInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.DataFormat;

public class ContractPlanDao {

	
 public String queryContractSQL(QueryContractInfo contractInfo){

	 StringBuffer sql = new StringBuffer();
	 
		sql.append(" SELECT distinct bb.ID ,");
		sql.append("bb.SCONTRACTCODE ContractCode,");
		sql.append("bb.NTYPEID LoanTypeID,");
		sql.append("bb.nSubTypeID,");
		sql.append("dd.SNAME Name,");
		sql.append("bb.MLOANAMOUNT,");
		sql.append("bb.NINTERVALNUM IntervalNum,");
		sql.append("bb.NSTATUSID \n");
		sql.append("FROM loan_contractform bb,");
		sql.append("(select nContractID,");
		sql.append(" nNextCheckUserID,");
		sql.append(" nInputUserID,");
		sql.append("nStatusID,");
		sql.append("nvl(nNextCheckLevel, 1) nNextCheckLevel \n");
		sql.append("from loan_PlanModifyForm \n");
		sql.append("where nStatusID = 20) cc,");
		sql.append("CLIENT dd \n");
		sql.append("WHERE bb.nTypeID != 3\n");
		sql.append("and bb.nTypeID != 6 \n");
		sql.append("and bb.nTypeID != 8 \n");
		sql.append(" and cc.nContractID(+) = bb.ID \n");
		sql.append(" and (cc.nNextCheckUserID is null or \n");
		sql.append(" cc.nNextCheckUserID = cc.nInputUserID) \n");
		sql.append(" and bb.NCURRENCYID = 1 \n");
		sql.append(" AND bb.NOFFICEID = 1 \n");
		sql.append(" and (bb.NSTATUSID = 4 or bb.NSTATUSID = 5 or \n");
		sql.append(" bb.NSTATUSID = 6) \n");
		sql.append(" and bb.nTypeID != 3 \n");
		sql.append(" and bb.nTypeID != 10 \n");
		sql.append(" AND bb.NBORROWCLIENTID = dd.ID(+) \n");
		
		
		if (contractInfo.getLoanTypeID() > -1)

			sql.append("  and bb.NTYPEID =" + contractInfo.getLoanTypeID());
		// 1.lContractIDFrom 合同编号起
		if (contractInfo.getContractIDFrom() > -1)

			sql.append("  and bb.ID >=" + contractInfo.getContractIDFrom());
		// 1.lContractIDTo 合同编号止
		if (contractInfo.getContractIDTo() > -1)
			sql.append( " and bb.ID <=" + contractInfo.getContractIDTo());

		// 2.lClientID借款单位编号
		if (contractInfo.getClientID() > 0)
			sql.append( " and bb.NBORROWCLIENTID =" + contractInfo.getClientID());

		// 3.lPeriod期限
		if (contractInfo.getPeriod() > 0)
			sql.append( " and bb.NINTERVALNUM =" + contractInfo.getPeriod());

		// 4.dAmountFrom金额起
		if (contractInfo.getAmountFrom() > 0)
			sql.append( " and bb.MLOANAMOUNT >=" + contractInfo.getAmountFrom());
		// 4.dAmountTo金额止
		if (contractInfo.getAmountTo() > 0)
			sql.append( " and bb.MLOANAMOUNT <=" + contractInfo.getAmountTo());

		// 5.tsUpdateFrom更改日期
		if (contractInfo.getUpdateFrom() != null && contractInfo.getIsHaveNew() == 0)
			sql.append( " and bb.DTCHECK>= To_Date('" + DataFormat.formatDate(contractInfo.getUpdateFrom())
					+ "','yyyy-mm-dd') ");
		// 5.tsUpdateTo更改日期止
		if (contractInfo.getUpdateTo() != null && contractInfo.getIsHaveNew() == 0)
			sql.append(  " and bb.DTCHECK<= To_Date('" + DataFormat.formatDate(contractInfo.getUpdateTo())
					+ "','yyyy-mm-dd') ");

		// 6.lStatusID 借款合同状态(已复核/执行中/已展期状态之一)
		if (contractInfo.getStatusID() > 0)
			sql.append( " and bb.NSTATUSID =" + contractInfo.getStatusID());
		else{
			sql.append( " and ( bb.NSTATUSID = " + LOANConstant.ContractStatus.NOTACTIVE + " or bb.NSTATUSID = "
					+ LOANConstant.ContractStatus.ACTIVE + " or bb.NSTATUSID = "
					+ LOANConstant.ContractStatus.EXTEND + ") ");
 }
		sql.append("  order by bb.SCONTRACTCODE \n");
		
		return sql.toString();
	}
/**
 * /**合同执行计划更改执行计划版本列表action层
 * add by liaiyi 2012-12-18
 * 
 */	

  public String queryContractModifySQL(long lContractID){
		
	  StringBuffer sql = new StringBuffer();
		
		sql.append("select cc.ID nModifyID,");
		sql.append("aa.ID ID,");
		sql.append("aa.nLoanID nLoanID,");
		sql.append("aa.nContractID nContractID,");
		sql.append("aa.nPlanVersion nPlanVersion,");
		sql.append("cc.nInputUserID nInputUserID,");
		sql.append("aa.dtInputDate dtInput,");
		sql.append("cc.nNextCheckUserID nNextCheckUserID,");
		sql.append("aa.nStatusID nStatusID,");
		sql.append("aa.nIsUsed nIsUsed,");
		sql.append("aa.nUserType nUserType,");
		sql.append("bb1.SNAME as SNAME1,");
		sql.append("bb2.SNAME as SNAME2,");
		sql.append("nvl(cc.nNextCheckLevel, 1) nNextCheckLevel \n");
		sql.append("from loan_loanContractPlan aa,");
		sql.append("loan_PlanModifyForm cc,");
		sql.append("USERINFO bb1,");
		sql.append("USERINFO bb2 \n");
		sql.append("where aa.nContractID = "+lContractID +"\n");
		sql.append("and NPLANVERSION > 0\n");
		sql.append("and aa.id = cc.nPlanID(+)\n");
		sql.append("and cc.NINPUTUSERID = bb1.ID(+)\n");
		sql.append("and cc.NNextCHECKUSERID = bb2.ID(+)\n");
		sql.append("and (cc.nStatusID > 0 or cc.nStatusID is null)\n");
		sql.append("order by aa.dtInputDate");
		
		return sql.toString();
	}
     
  public String queryContractModifySQL1(long contractPayPlanVersionID){
		
	  StringBuffer sql = new StringBuffer();
	  
		sql.append("select aa.*, lcp.ncontractid \n");
		sql.append(" from loan_loanContractPlanDetail aa,loan_loanContractPlan lcp \n");
		sql.append(" where aa.ncontractplanid = lcp.id and lcp.id = " + contractPayPlanVersionID);
		
		return sql.toString();
	}
}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
