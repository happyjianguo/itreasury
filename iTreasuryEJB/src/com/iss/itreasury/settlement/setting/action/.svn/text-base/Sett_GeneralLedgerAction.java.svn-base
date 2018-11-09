package com.iss.itreasury.settlement.setting.action;

/**
 * 账户类型编码设置
 */

import java.util.Map;

import com.iss.itreasury.settlement.setting.bizlogic.Sett_GeneralLedgerQueryBiz;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class Sett_GeneralLedgerAction
{
	Sett_GeneralLedgerQueryBiz biz=new Sett_GeneralLedgerQueryBiz();
	
	public PagerInfo queryGeneralLedger(Map map)throws Exception {
		
		PagerInfo pagerInfo=null;
		try {
			//获得登录officeid和currencyid
			long lOfficeID=Long.parseLong(map.get("OfficeID".toLowerCase()).toString());
			
			long lCurrencyID=Long.parseLong(map.get("CurrencyID".toLowerCase()).toString());
			
			String strStartCode=map.get("strStartCode".toLowerCase()).toString();
			
			String strEndCode=map.get("strEndCode".toLowerCase()).toString();
			
			pagerInfo = biz.queryGeneralLedger(lOfficeID, lCurrencyID, strStartCode, strEndCode, "", "");
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		
		return pagerInfo;
		
	}
	

}
