package com.iss.itreasury.report.delegation;

import java.util.Collection;

import com.iss.itreasury.report.bizlogic.TransLoanGatherBiz;

public class TransLoanGatherDelegation {
	
	TransLoanGatherBiz gatherBiz = null;
	
	public Collection findTransLoanDetail(String strStartDate,String strEndDate,long lLoanTypeId,String strContractStatusId) throws Exception { 
		gatherBiz = new TransLoanGatherBiz();
		return gatherBiz.findTransLoanDetail(strStartDate, strEndDate, lLoanTypeId, strContractStatusId);
	}
	
	public double getSumBalanceOfLoanGather(String strStartDate,String strEndDate,long lLoanTypeId,String strContractStatusId) throws Exception {
		gatherBiz = new TransLoanGatherBiz();
		return gatherBiz.getSumBalanceOfLoanGather(strStartDate, strEndDate, lLoanTypeId, strContractStatusId);
	}
	
}
