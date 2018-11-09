package com.iss.itreasury.settlement.setting.action;

/**
 * 特殊业务类型设置
 */

import java.util.Map;

import com.iss.itreasury.settlement.setting.bizlogic.Sett_SpecialOperationQueryBiz;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class Sett_SpecialOperationAction
{
	Sett_SpecialOperationQueryBiz biz=new Sett_SpecialOperationQueryBiz();
	
	public PagerInfo querySpecialOperation(Map map)throws Exception {
		
		PagerInfo pagerInfo=null;
		try {
			//获得登录officeid和currencyid
			long lOfficeID=Long.parseLong(map.get("OfficeID".toLowerCase()).toString());
			long lCurrencyID=Long.parseLong(map.get("CurrencyID".toLowerCase()).toString());
			long lStartID=-1;
			long lEndID=-1;
			String lStartIDTemp=map.get("lStartID".toLowerCase()).toString();
			String lEndIDTemp=map.get("lEndID".toLowerCase()).toString();
			
			if (lStartIDTemp!=null &&!lStartIDTemp.equals("")) {
				
				lStartID=Long.parseLong(lStartIDTemp);
			}
			
			if (lEndIDTemp!=null &&!lEndIDTemp.equals("")) {
				lEndID=Long.parseLong(lEndIDTemp);
			}
			pagerInfo = biz.querySpecialOperation(lCurrencyID, lOfficeID, lStartID, lEndID, "");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		
		return pagerInfo;
		
	}
	

}
