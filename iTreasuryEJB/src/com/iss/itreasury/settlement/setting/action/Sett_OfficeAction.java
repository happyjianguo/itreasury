package com.iss.itreasury.settlement.setting.action;

import java.util.Map;

import com.iss.itreasury.settlement.setting.bizlogic.Sett_OfficeBiz;
import com.iss.itreasury.settlement.setting.dataentity.OfficeInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class Sett_OfficeAction {
	
	Sett_OfficeBiz biz =new Sett_OfficeBiz();
	public PagerInfo queryOfficeByConditions(Map map) throws Exception{
		PagerInfo pagerInfo = null;
		OfficeInfo officeInfo =new OfficeInfo();
		try 
		{ 
			pagerInfo = biz.queryOfficefindByConditions(officeInfo);
			
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		
		return pagerInfo;
	}

}
