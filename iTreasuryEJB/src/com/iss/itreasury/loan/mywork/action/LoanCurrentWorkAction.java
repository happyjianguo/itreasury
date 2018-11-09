package com.iss.itreasury.loan.mywork.action;

import java.util.Map;

import com.iss.itreasury.loan.mywork.bizlogic.LoanCurrentWorksBiz;
import com.iss.itreasury.loan.mywork.dataentity.LoanMyWorkInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class LoanCurrentWorkAction {

	LoanCurrentWorksBiz biz = new LoanCurrentWorksBiz();

	public PagerInfo queryCurrentWork(Map map) throws Exception {
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
			qInfo.setQueryPurpose(LOANConstant.WorkType.CURRENTWORK);
			qInfo.setModuleID(lmoduleid);

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
			qInfo.setQueryPurpose(LOANConstant.WorkType.CURRENTWORK);
			qInfo.setModuleID(lmoduleid);

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
			qInfo.setQueryPurpose(LOANConstant.WorkType.CURRENTWORK);
			qInfo.setModuleID(lmoduleid);

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
			qInfo.setQueryPurpose(LOANConstant.WorkType.CURRENTWORK);
			qInfo.setModuleID(lmoduleid);

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
			qInfo.setQueryPurpose(LOANConstant.WorkType.CURRENTWORK);
			qInfo.setModuleID(lmoduleid);
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
			qInfo.setQueryPurpose(LOANConstant.WorkType.CURRENTWORK);
			qInfo.setModuleID(lmoduleid);
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
			qInfo.setQueryPurpose(LOANConstant.WorkType.CURRENTWORK);
			qInfo.setModuleID(lmoduleid);

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
			qInfo.setQueryPurpose(LOANConstant.WorkType.CURRENTWORK);
			qInfo.setModuleID(lmoduleid);

			pagerInfo = biz.queryAfterCreditWork(qInfo,currencysymbol);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	
}
