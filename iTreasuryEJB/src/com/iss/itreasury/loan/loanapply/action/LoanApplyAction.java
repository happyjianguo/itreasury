package com.iss.itreasury.loan.loanapply.action;

import java.util.Map;

import com.iss.itreasury.loan.loanapply.bizlogic.LoanApplyBiz;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class LoanApplyAction {
	
	LoanApplyBiz loanApplyBiz = new LoanApplyBiz();
	
	/**
	 * 查询贷款申请的所有计划明细action
	 * @author zk 2012-12-18
	 *
	 */
	public PagerInfo queryPlanByLoanID(Map map) throws Exception {
		
		PagerInfo pagerInfo = null;
		long lLoanID = -1;
		String lCurrencyID = null;
		String status = null;
		try
		{
			if(map !=null && map.get("lloanid") != null){
				lLoanID = Long.parseLong((String)map.get("lloanid"));
			}
			if(map !=null && map.get("lcurrencyid") != null){
				lCurrencyID = (String)map.get("lcurrencyid");
			}
			if(map !=null && map.get("txtaction") != null){
				status = (String)map.get("txtaction");
			}
			pagerInfo = loanApplyBiz.queryPlanByLoanID(lLoanID,status,lCurrencyID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(),e);
		}
		return pagerInfo;
	}
	/**
	 * 申请贷款担保方式action
	 * @author zk 2012-12-19
	 *
	 */
	public PagerInfo queryLoanApplyGuaranteeByLoanID(Map map) throws Exception {
		
		PagerInfo pagerInfo = null;
		long lLoanID = -1;
		try
		{
			if(map !=null && map.get("lloanid") != null){
				lLoanID = Long.parseLong((String)map.get("lloanid"));
			}
			pagerInfo = loanApplyBiz.queryLoanApplyGuaranteeByLoanID(lLoanID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(),e);
		}
		return pagerInfo;
	}
	public PagerInfo findByID(Map map) throws Exception {
		PagerInfo pagerInfo = null;
		try {
			long loanID= Long.parseLong(map.get("loanid")+"");
			pagerInfo = loanApplyBiz.queryLoanApply(loanID);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}


}
