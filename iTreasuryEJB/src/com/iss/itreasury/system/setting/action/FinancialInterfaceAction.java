package com.iss.itreasury.system.setting.action;

import java.util.Map;

import com.iss.itreasury.system.setting.bizlogic.FinancialInterfaceBiz;
import com.iss.itreasury.system.setting.dataentity.GlSettingInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class FinancialInterfaceAction {

	FinancialInterfaceBiz financialBiz = new FinancialInterfaceBiz();
	
	/**
	 * 财务接口设置查询类
	 * add by liaiyi 2013-01-28
	 */
  public PagerInfo queryFinancialInterfaceInfo(Map map) throws Exception {
		
		PagerInfo pagerInfo = null;
		long nStatusID = -1; 
		long officeID = -1; 
		long currencyID = -1;
		try
		{
			GlSettingInfo glSettingInfo = new GlSettingInfo();
			
			if(map.get("nstatusid") != null){
				nStatusID = Long.parseLong((String)map.get("nstatusid"));
				glSettingInfo.setNStatusID(nStatusID);
			}
			if(map.get("officeid") != null){
				officeID = Long.parseLong((String)map.get("officeid"));
				glSettingInfo.setOfficeID(officeID);
			}
			if(map.get("currencyid") != null){
				currencyID = Long.parseLong((String)map.get("currencyid"));
				glSettingInfo.setCurrencyID(currencyID);
			}
			pagerInfo = financialBiz.queryFinancialInterfaceInfo(glSettingInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(),e);
		}
		return pagerInfo;
	}
}
