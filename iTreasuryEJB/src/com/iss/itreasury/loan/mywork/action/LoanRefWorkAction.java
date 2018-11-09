package com.iss.itreasury.loan.mywork.action;

import java.util.Map;

import com.iss.itreasury.loan.mywork.bizlogic.LoanRefWorksBiz;
import com.iss.itreasury.loan.mywork.dataentity.LoanMyWorkInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class LoanRefWorkAction {

	LoanRefWorksBiz biz = new LoanRefWorksBiz();

	public PagerInfo queryLoanTransActionWork(Map map) throws Exception {
		PagerInfo pagerInfo = null;
		try {
			long luserid = Long.valueOf(map.get("luserid")+"").longValue();
			long lofficeid = Long.valueOf(map.get("lofficeid")+"").longValue();
			long lcurrencyid = Long.valueOf(map.get("lcurrencyid")+"").longValue();
			String currencysymbol = map.get("currencysymbol")+"";
			long lmoduleid = Long.valueOf(map.get("lmoduleid")+"").longValue();
			
			LoanMyWorkInfo qInfo = new LoanMyWorkInfo();
			qInfo.setUserID(luserid);
			qInfo.setOfficeID(lofficeid);
			qInfo.setCurrencyID(lcurrencyid);
			qInfo.setModuleID(lmoduleid);
			qInfo.setStatusID(LOANConstant.LoanStatus.SAVE);
			qInfo.setQueryPurpose(LOANConstant.WorkType.REFUSEWORK);
			
			pagerInfo = biz.queryLoanTransActionWork(qInfo,currencysymbol);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	
	public PagerInfo queryLoanContractStatusChangeWork(Map map) throws Exception {
		PagerInfo pagerInfo = null;
		try {
			long luserid = Long.valueOf(map.get("luserid")+"").longValue();
			long lofficeid = Long.valueOf(map.get("lofficeid")+"").longValue();
			long lcurrencyid = Long.valueOf(map.get("lcurrencyid")+"").longValue();
			String currencysymbol = map.get("currencysymbol")+"";
			long lmoduleid = Long.valueOf(map.get("lmoduleid")+"").longValue();
			
			LoanMyWorkInfo qInfo = new LoanMyWorkInfo();
			qInfo.setUserID(luserid);
			qInfo.setOfficeID(lofficeid);
			qInfo.setCurrencyID(lcurrencyid);
			qInfo.setModuleID(lmoduleid);
			qInfo.setStatusID(LOANConstant.LoanStatus.SAVE);
			qInfo.setQueryPurpose(LOANConstant.WorkType.REFUSEWORK);
			
			pagerInfo = biz.queryLoanContractStatusChangeWork(qInfo,currencysymbol);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	
	public PagerInfo queryLoanContractRisklevelChangeWork(Map map) throws Exception {
		PagerInfo pagerInfo = null;
		try {
			long luserid = Long.valueOf(map.get("luserid")+"").longValue();
			long lofficeid = Long.valueOf(map.get("lofficeid")+"").longValue();
			long lcurrencyid = Long.valueOf(map.get("lcurrencyid")+"").longValue();
			String currencysymbol = map.get("currencysymbol")+"";
			long lmoduleid = Long.valueOf(map.get("lmoduleid")+"").longValue();
			
			LoanMyWorkInfo qInfo = new LoanMyWorkInfo();
			qInfo.setUserID(luserid);
			qInfo.setOfficeID(lofficeid);
			qInfo.setCurrencyID(lcurrencyid);
			qInfo.setModuleID(lmoduleid);
			qInfo.setStatusID(LOANConstant.LoanStatus.SAVE);
			qInfo.setQueryPurpose(LOANConstant.WorkType.REFUSEWORK);
			
			pagerInfo = biz.queryLoanContractRisklevelChangeWork(qInfo,currencysymbol);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	
	public PagerInfo queryLoanContractPlanChangeWork(Map map) throws Exception {
		PagerInfo pagerInfo = null;
		try {
			long luserid = Long.valueOf(map.get("luserid")+"").longValue();
			long lofficeid = Long.valueOf(map.get("lofficeid")+"").longValue();
			long lcurrencyid = Long.valueOf(map.get("lcurrencyid")+"").longValue();
			String currencysymbol = map.get("currencysymbol")+"";
			long lmoduleid = Long.valueOf(map.get("lmoduleid")+"").longValue();
			
			LoanMyWorkInfo qInfo = new LoanMyWorkInfo();
			qInfo.setUserID(luserid);
			qInfo.setOfficeID(lofficeid);
			qInfo.setCurrencyID(lcurrencyid);
			qInfo.setModuleID(lmoduleid);
			qInfo.setStatusID(LOANConstant.LoanStatus.SAVE);
			qInfo.setQueryPurpose(LOANConstant.WorkType.REFUSEWORK);
			
			pagerInfo = biz.queryLoanContractPlanChangeWork(qInfo,currencysymbol);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	
	public PagerInfo queryLoanContractRateChangeWork(Map map) throws Exception {
		PagerInfo pagerInfo = null;
		try {
			long luserid = Long.valueOf(map.get("luserid")+"").longValue();
			long lofficeid = Long.valueOf(map.get("lofficeid")+"").longValue();
			long lcurrencyid = Long.valueOf(map.get("lcurrencyid")+"").longValue();
			String currencysymbol = map.get("currencysymbol")+"";
			long lmoduleid = Long.valueOf(map.get("lmoduleid")+"").longValue();
			
			LoanMyWorkInfo qInfo = new LoanMyWorkInfo();
			qInfo.setUserID(luserid);
			qInfo.setOfficeID(lofficeid);
			qInfo.setCurrencyID(lcurrencyid);
			qInfo.setModuleID(lmoduleid);
			qInfo.setStatusID(LOANConstant.LoanStatus.SAVE);
			qInfo.setQueryPurpose(LOANConstant.WorkType.REFUSEWORK);
			qInfo.setQSingleOrBatch("single");

			
			pagerInfo = biz.queryLoanContractRateChangeWork(qInfo,currencysymbol);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	
	public PagerInfo queryLoanContractRateChangeWorkBatch(Map map) throws Exception {
		PagerInfo pagerInfo = null;
		try {
			long luserid = Long.valueOf(map.get("luserid")+"").longValue();
			long lofficeid = Long.valueOf(map.get("lofficeid")+"").longValue();
			long lcurrencyid = Long.valueOf(map.get("lcurrencyid")+"").longValue();
			String currencysymbol = map.get("currencysymbol")+"";
			long lmoduleid = Long.valueOf(map.get("lmoduleid")+"").longValue();
			
			LoanMyWorkInfo qInfo = new LoanMyWorkInfo();
			qInfo.setUserID(luserid);
			qInfo.setOfficeID(lofficeid);
			qInfo.setCurrencyID(lcurrencyid);
			qInfo.setModuleID(lmoduleid);
			qInfo.setStatusID(LOANConstant.LoanStatus.SAVE);
			qInfo.setQueryPurpose(LOANConstant.WorkType.REFUSEWORK);
			qInfo.setQSingleOrBatch("batch");

			
			pagerInfo = biz.queryLoanContractRateChangeWorkBatch(qInfo,currencysymbol);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	
	public PagerInfo queryLoanCredit(Map map) throws Exception {
		PagerInfo pagerInfo = null;
		try {
			long luserid = Long.valueOf(map.get("luserid")+"").longValue();
			long lofficeid = Long.valueOf(map.get("lofficeid")+"").longValue();
			long lcurrencyid = Long.valueOf(map.get("lcurrencyid")+"").longValue();
			String currencysymbol = map.get("currencysymbol")+"";
			long lmoduleid = Long.valueOf(map.get("lmoduleid")+"").longValue();
			
			LoanMyWorkInfo qInfo = new LoanMyWorkInfo();
			qInfo.setUserID(luserid);
			qInfo.setOfficeID(lofficeid);
			qInfo.setCurrencyID(lcurrencyid);
			qInfo.setModuleID(lmoduleid);
			qInfo.setStatusID(LOANConstant.LoanStatus.SAVE);
			qInfo.setQueryPurpose(LOANConstant.WorkType.REFUSEWORK);

			
			pagerInfo = biz.queryLoanCredit(qInfo,currencysymbol);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	
	public PagerInfo queryAfterCreditWork(Map map) throws Exception {
		PagerInfo pagerInfo = null;
		try {
			long luserid = Long.valueOf(map.get("luserid")+"").longValue();
			long lofficeid = Long.valueOf(map.get("lofficeid")+"").longValue();
			long lcurrencyid = Long.valueOf(map.get("lcurrencyid")+"").longValue();
			String currencysymbol = map.get("currencysymbol")+"";
			long lmoduleid = Long.valueOf(map.get("lmoduleid")+"").longValue();
			
			LoanMyWorkInfo qInfo = new LoanMyWorkInfo();
			qInfo.setUserID(luserid);
			qInfo.setOfficeID(lofficeid);
			qInfo.setCurrencyID(lcurrencyid);
			qInfo.setModuleID(lmoduleid);
			qInfo.setStatusID(LOANConstant.LoanStatus.SAVE);
			qInfo.setQueryPurpose(LOANConstant.WorkType.REFUSEWORK);

			
			pagerInfo = biz.queryAfterCreditWork(qInfo,currencysymbol);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	
	
}
