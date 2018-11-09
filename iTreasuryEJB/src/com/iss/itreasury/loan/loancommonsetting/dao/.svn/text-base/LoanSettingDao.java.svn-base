package com.iss.itreasury.loan.loancommonsetting.dao;

import com.iss.itreasury.loan.contract.dataentity.ContractInfo;

public class LoanSettingDao {

	/**
	 * 申请书管理人权限转移Dao层
	 * add by liaiyi 2012-11-14
	 * @return
	 */	
	public String queryLoanDao(ContractInfo ci){
		
		StringBuffer sql = new StringBuffer();
		
			sql.append(" select a.ID as LoanID,"); 
			sql.append(" a.SAPPLYCODE as ApplyCode,"); 
			sql.append(" a.NTYPEID as LoanTypeID,"); 
			sql.append(" a.NBORROWCLIENTID LoanAccount,"); 
			sql.append(" c.sName as BorrowClientName,"); 
			sql.append(" d.sName as ConsignClientName,"); 
			sql.append(" a.NCONSIGNCLIENTID,"); 
			sql.append(" a.MLOANAMOUNT as LoanAmount,"); 
			sql.append(" a.NBANKINTERESTID as RateID,"); 
			sql.append(" a.mdiscountrate,"); 
			sql.append(" a.NINTERVALNUM as RateName,"); 
			sql.append(" a.NSTATUSID,"); 
			sql.append(" a.NINPUTUSERID as InputUserID,"); 
			sql.append(" u.sName as InputUserName,"); 
			sql.append(" a.NCURRENCYID,"); 
			sql.append(" a.NOFFICEID \n"); 
			sql.append(" from LOAN_LOANFORM a, CLIENT c, Client d, USERINFO u \n"); 
			sql.append("where a.NBORROWCLIENTID = c.ID(+) \n"); 
			sql.append(" and a.NCONSIGNCLIENTID = d.ID(+) \n"); 
			sql.append(" and a.NINPUTUSERID = u.ID(+) \n"); 
			sql.append(" and a.NCONSIGNCLIENTID = d.ID(+) \n");
			sql.append("  and a.nstatusid = 1 \n");
			sql.append("  and a.nInputUserID = "+ci.getLInputUserID()+" \n");
			
			if (ci.getLoanTypeID()>0 )
				sql.append(" and a.nTypeID =" + ci.getLoanTypeID());
				
			if (ci.getApplyCodeFrom() != null && ci.getApplyCodeFrom().length() > 0)
				sql.append(" and a.sApplyCode>='" + ci.getApplyCodeFrom() + "'");
			
			if (ci.getApplyCodeTo() != null && ci.getApplyCodeTo().length() > 0)
				sql.append(" and a.sApplyCode<='" + ci.getApplyCodeTo() + "'");
				
			if (ci.getLoanAccount() != null && ci.getLoanAccount().length() > 0)
				sql.append(" and c.sname='" + ci.getLoanAccount() + "'");
			
			if (ci.getLoanAmountFrom() > 0) 
			{
				sql.append(" and a.mLoanAmount>= " + ci.getLoanAmountFrom());
			}
			if (ci.getLoanAmountTo() > 0) 
			{
				sql.append(" and a.mLoanAmount<= " + ci.getLoanAmountTo());
			}
			if (ci.getLInputUserID() > -1)
			{
				sql.append(" and a.nInputUserID= " + ci.getLInputUserID());
			}
	           sql.append("  order by ApplyCode");
	           
			return sql.toString();
			
}
      
	/**
	 * 合同管理人权限转移Dao层
	 * add by liaiyi 2012-11-14
	 * @return
	 */	
  public String queryContractSQL(ContractInfo ci){
		
		StringBuffer sql = new StringBuffer();
		
			sql.append(" select a.ID as ContractID,"); 
			sql.append(" a.SCONTRACTCODE as ContractCode,"); 
			sql.append(" a.NTYPEID as LoanTypeID,"); 
			sql.append(" c.sName as BorrowClientName,"); 
			sql.append(" c2.sName as ConsignClientName,"); 
			sql.append(" a.NCONSIGNCLIENTID,"); 
			sql.append(" a.MEXAMINEAMOUNT,"); 
			sql.append(" a.MLOANAMOUNT as LoanAmount,"); 
			sql.append(" a.NBANKINTERESTID as RateID,"); 
			sql.append(" r.MRATE as fRate,"); 
			sql.append("   a.mdiscountrate,"); 
			sql.append(" a.NINTERVALNUM as RateName,"); 
			sql.append(" a.NSTATUSID,"); 
			sql.append(" a.NINPUTUSERID as InputUserID,"); 
			sql.append(" u.sName as InputUserName,"); 
			sql.append(" a.NCURRENCYID,"); 
			sql.append(" a.NOFFICEID \n"); 
			sql.append(" from LOAN_CONTRACTFORM a,CLIENT c,USERINFO u,Client c2,LOAN_INTERESTRATE r \n"); 
			sql.append(" where c.ID(+) = a.NBORROWCLIENTID \n"); 
			sql.append(" and a.NCONSIGNCLIENTID = c2.ID(+) \n"); 
			sql.append(" and u.ID(+) = a.NINPUTUSERID \n"); 
			sql.append(" and a.NBANKINTERESTID = r.ID(+) \n");
			sql.append("   and a.nTypeID in (1, 2, 3, 11) \n");
			sql.append(" and a.nOfficeID = 1 \n");
			sql.append(" and a.nCurrencyID = 1 \n");
			sql.append("  and a.nStatusID in (1, 2, 3, 4, 5, 6, 7, 8, 9, 10) \n");
			sql.append("  and a.nInputUserID = "+ci.getLInputUserID()+" \n");
			
		
			if (ci.getLoanTypeID()>0 )
				sql.append(" and a.nTypeID =" + ci.getLoanTypeID());
				
			if (ci.getContractCodeFrom() != null && ci.getContractCodeFrom().length() > 0)
				sql.append(" and a.SCONTRACTCODE>='" + ci.getContractCodeFrom() + "'");
			
			if (ci.getContractCodeTo() != null && ci.getContractCodeTo().length() > 0)
				sql.append(" and a.SCONTRACTCODE<='" + ci.getContractCodeTo() + "'");
				
			if (ci.getLoanAccount() != null && ci.getLoanAccount().length() > 0)
				sql.append(" and c.sname='" + ci.getLoanAccount() + "'");
			
			if (ci.getLoanAmountFrom() > 0) 
			{
				sql.append(" and a.mLoanAmount>= " + ci.getLoanAmountFrom());
			}
			if (ci.getLoanAmountTo() > 0) 
			{
				sql.append(" and a.mLoanAmount<= " + ci.getLoanAmountTo());
			}
			
			if (ci.getStatusID() > -1) //合同状态
			{
				sql.append(" and a.nStatusID =" + ci.getStatusID());
			}
			
			if (ci.getLInputUserID() > -1)
			{
				sql.append(" and a.nInputUserID= " + ci.getLInputUserID());
			}
			
			sql.append(" order by a.SCONTRACTCODE	 \n");
			return sql.toString();
			
   }
      
 
     
     
     
      
     
     
 

  
  
    
}
