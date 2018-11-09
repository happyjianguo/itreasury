package com.iss.itreasury.loan.repayplan.action;

import java.util.Map;

import com.iss.itreasury.loan.repayplan.bizlogic.ExtensionContractPlanBiz;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class ExtensionContractPlanAction {

/**
 * 展期合同执行计划查看
 * add by liaiyi 2013-02-25
 * 
 * 
 */
	
	ExtensionContractPlanBiz extensionContractPlanBiz = new ExtensionContractPlanBiz();
  public PagerInfo queryExtensionContractPlanInfo(Map map) throws Exception {
		
		PagerInfo pagerInfo = null;
		long contractPayPlanVersionID = -1;
		try
		{
			if(map !=null && map.get("contractpayplanversionid") != null && !map.get("contractpayplanversionid").equals("") ){
				contractPayPlanVersionID = Long.parseLong((String)map.get("contractpayplanversionid"));
			}
			
			pagerInfo = extensionContractPlanBiz.queryExtensionContractPlanInfo(contractPayPlanVersionID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(),e);
		}
		return pagerInfo;
	}
}
