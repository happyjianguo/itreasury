package com.iss.itreasury.credit.setting.action;

import java.util.Map;

import com.iss.itreasury.credit.setting.bizlogic.AmountUsedBiz;
import com.iss.itreasury.credit.setting.dataentity.AmountFormViewInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class AmountUsedAction {
	
	AmountUsedBiz amountUsedBiz = new AmountUsedBiz();
	
	/**
	 * 贷款申请占用的额度action层
	 * add by liaiyi 2013-01-09
	 */

	public PagerInfo queryApplyUsedAmount(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		long id = -1;
		
		try
		{
			AmountFormViewInfo amountFormViewInfo = new AmountFormViewInfo();
			
			if(map.get("id") != null && !map.get("id").equals("")){
				id = Long.parseLong((String)map.get("id"));
				amountFormViewInfo.setId(id);
			}
			
			pagerInfo = amountUsedBiz.queryApplyUsedAmount(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	
	/**
	 * 贷款合同占用的额度action层
	 * add by liaiyi 2013-01-09
	 */

	public PagerInfo queryContractUsedAmount(Map map) throws Exception
	{
		long id = -1;
		
		PagerInfo pagerInfo = null;
		try
		{
			AmountFormViewInfo amountFormViewInfo = new AmountFormViewInfo();
			if(map.get("id") != null && !map.get("id").equals("")){
				id = Long.parseLong((String)map.get("id"));
				amountFormViewInfo.setId(id);
			}
			pagerInfo = amountUsedBiz.queryContractUsedAmount(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	
	/**
	 * 自营贷款放款单占用的额度 action层
	 */
	public PagerInfo queryLoanUsedAmount(Map map) throws Exception
	{
		long id = -1;
		
		PagerInfo pagerInfo = null;
		try
		{
			AmountFormViewInfo amountFormViewInfo = new AmountFormViewInfo();
			if(map.get("id") != null && !map.get("id").equals("")){
				id = Long.parseLong((String)map.get("id"));
				amountFormViewInfo.setId(id);
			}
			pagerInfo = amountUsedBiz.queryLoanUsedAmount(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	
	/**
	 * 还款单释放的额度  action层
	 */
	public PagerInfo queryRepayUsedAmount(Map map) throws Exception
	{
		long id = -1;
		
		PagerInfo pagerInfo = null;
		try
		{
			AmountFormViewInfo amountFormViewInfo = new AmountFormViewInfo();
			if(map.get("id") != null && !map.get("id").equals("")){
				id = Long.parseLong((String)map.get("id"));
				amountFormViewInfo.setId(id);
			}
			pagerInfo = amountUsedBiz.queryRepayUsedAmount(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
}
