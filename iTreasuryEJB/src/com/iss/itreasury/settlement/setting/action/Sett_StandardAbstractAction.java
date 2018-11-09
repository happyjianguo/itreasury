package com.iss.itreasury.settlement.setting.action;

import java.util.Map;

import com.iss.itreasury.settlement.setting.bizlogic.Sett_QueryStandardAbstractBiz;
import com.iss.itreasury.settlement.setting.dataentity.StandardAbstractInfo;
import com.iss.itreasury.util.Constant;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class Sett_StandardAbstractAction
{
	Sett_QueryStandardAbstractBiz biz=new Sett_QueryStandardAbstractBiz();
	
	public PagerInfo queryStandardAbstract(Map map)throws Exception {
		
		PagerInfo pagerInfo=null;
		try {
			StandardAbstractInfo qbInfo=new StandardAbstractInfo();
			
			long OfficeID=Long.parseLong(map.get("OfficeID".toLowerCase()).toString());
			qbInfo.setNOfficeID(OfficeID);
			qbInfo.setNStatusID(Constant.RecordStatus.VALID);
			
			pagerInfo = biz.queryStandardAbastract(qbInfo);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		
		return pagerInfo;
		
	}
	

}
