package com.iss.itreasury.loan.contract.dao;

import com.iss.itreasury.loan.util.LOANConstant;

public class ContractActivationDao {
	
	public String queryContractActivationInfo(long lType,long lCurrencyID,long lOfficeID,long lUserID,
			long lContractIDFrom,long lContractIDTo,long lClientID,double dAmountFrom,double dAmountTo) {
		
		StringBuffer sql = new StringBuffer();
		long[] lLoanTypeTmp = null; //贷款类型值字符串
		String strLoanTypeTmp = "";
		
		//取所有贷款类型的值
		lLoanTypeTmp = LOANConstant.LoanType.getAllCode(lOfficeID,lCurrencyID);
		for (int i = 0; i < lLoanTypeTmp.length; i++){
			if (i == 0){
				strLoanTypeTmp += "" + lLoanTypeTmp[i];
			}else{
				strLoanTypeTmp += "," + lLoanTypeTmp[i];
			}
				
		}
		
		sql.append("select a.ID as ContractID,a.SCONTRACTCODE as ContractCode,a.NTYPEID,a.NBORROWCLIENTID,c.sName as BorrowClientName, \n");
		sql.append(" a.NCONSIGNCLIENTID,c2.sName as ConsignClientName,a.mLoanAmount,a.MEXAMINEAMOUNT,a.NBANKINTERESTID as RateID, \n");
		sql.append(" r.MRATE as fRate,a.mdiscountrate,a.NINTERVALNUM as RateName,a.NSTATUSID,a.assurechargerate,a.NINPUTUSERID as InputUserID, \n");
		sql.append(" u.sName as InputUserName,nvl(a.nNextCheckLevel, 1) nNextCheckLevel,a.NCURRENCYID,a.NOFFICEID \n");
		sql.append("  from LOAN_CONTRACTFORM a,CLIENT c,CLIENT c2,USERINFO u,LOAN_INTERESTRATE r \n");
		sql.append(" where c.ID=a.NBORROWCLIENTID and u.ID=a.NINPUTUSERID and a.NCONSIGNCLIENTID=c2.ID(+) and a.NINTERESTTYPEID=r.ID(+) \n");
		sql.append("   and a.nTypeID in (" + strLoanTypeTmp + ") and a.nStatusID = " + LOANConstant.ContractStatus.CHECK + " \n");
		
		if (lOfficeID > 0){
			sql.append(" and a.nOfficeID = " + lOfficeID + " \n");
		}
		if (lCurrencyID > 0){
			sql.append(" and a.nCurrencyID = " + lCurrencyID + " \n");
		}
		if (lUserID > 0){
			sql.append(" and a.nInputUserID= " + lUserID + " \n");
		}
		if (lType > 0){
			sql.append(" and a.nTypeID = " + lType + " \n");
		}
		if (lContractIDFrom > 0){
			sql.append(" and a.ID >= " + lContractIDFrom + " \n");
		}
		if (lContractIDTo > 0){
			sql.append(" and a.ID <= " + lContractIDTo + " \n");
		}
		if (lClientID > 0){
			sql.append(" and a.NBORROWCLIENTID = " + lClientID + " \n");
		}
		if(dAmountFrom > 0){
			sql.append(" and a.MEXAMINEAMOUNT >= "+ dAmountFrom + " \n");
		}
		if(dAmountTo > 0){
			sql.append(" and a.MEXAMINEAMOUNT<= "+ dAmountTo + " \n");
		}
		
		return sql.toString();
	}

}
