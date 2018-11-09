package com.iss.itreasury.settlement.clearinterestplanSetting.action;

import java.util.Map;

import com.iss.itreasury.settlement.clearinterestplanSetting.bizlogic.ClearinterestplanBiz;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class ClearinterestplanAction {
	private ClearinterestplanBiz biz =new ClearinterestplanBiz();
	public PagerInfo findAllClearinterestplan(Map map) throws Exception{
		long lOfficeID=Long.parseLong(map.get("lofficeid")+"");
		long currencyID=Long.parseLong(map.get("lcurrencyid")+"");
		return biz.findClearinterestplan(lOfficeID,currencyID);
				
	}
	
	
	

}
