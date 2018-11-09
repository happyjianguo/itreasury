package com.iss.itreasury.loan.repayplan.action;

import java.util.Map;

import com.iss.itreasury.loan.repayplan.biz.RepayPlanBiz;
import com.iss.itreasury.loan.repayplan.bizlogic.RepayPlanBiz_new;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class RepayPlanAction {

	RepayPlanBiz biz = new RepayPlanBiz();

	public PagerInfo findPlan(Map map) throws Exception {
		PagerInfo pagerInfo = null;
		long lID=-1;
		long lUserID = -1;
		long lOfficeID = -1;
		long lYU = -1;
		lID = Long.valueOf(map.get("lid")+"");
		lUserID = Long.valueOf(map.get("luserid")+"");
		lOfficeID = Long.valueOf(map.get("lofficeid")+"");
		lYU = Long.valueOf(map.get("lyu")+"");	
		String Symbol = map.get("symbol")+"";
		try {
			pagerInfo = biz.findPlan(lID,lUserID,lOfficeID,lYU,Symbol);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		} 
		return pagerInfo;
	}
	
	
	RepayPlanBiz_new repayPlanBiz = new RepayPlanBiz_new();
	
	/**
	 * 查询免还申请处理执行计划信息action
	 * @author zk 2012-12-24
	 *
	 */
	public PagerInfo queryRepayPlanInfo(Map map) throws Exception {
		
		PagerInfo pagerInfo = null;
		long nTmpID = -1;
		long isYU = -1;
		try
		{
			if(map !=null && map.get("ntmpid") != null){
				nTmpID = Long.parseLong((String)map.get("ntmpid"));
			}
			if(map !=null && map.get("isyu") != null){
				isYU = Long.parseLong((String)map.get("isyu"));
			}
			pagerInfo = repayPlanBiz.queryRepayPlanInfo(isYU,nTmpID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(),e);
		}
		return pagerInfo;
	}
}
