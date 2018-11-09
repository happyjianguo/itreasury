package com.iss.itreasury.loan.repayplan.dao;

public class ExtensionContractPlanDao {

	/**
	 * 展期合同执行计划查看
	 * add by liaiyi 2013-02-25
	 */
	  public String queryExtensionContractPlanSQL(long contractPayPlanVersionID){
			
		  StringBuffer sql = new StringBuffer();
		  
			sql.append("select aa.*, lcp.ncontractid \n");
			sql.append(" from loan_loanContractPlanDetail aa,loan_loanContractPlan lcp \n");
			sql.append(" where aa.ncontractplanid = lcp.id and lcp.id = " + contractPayPlanVersionID);
			
			return sql.toString();
		}
}
