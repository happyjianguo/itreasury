package com.iss.itreasury.settlement.setting.action;

import java.util.Map;
import com.iss.itreasury.settlement.setting.bizlogic.Sett_BranchBiz;
import com.iss.itreasury.settlement.setting.dataentity.QueryBranchInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class Sett_BranchAction
{
	Sett_BranchBiz biz=new Sett_BranchBiz();
	
	public PagerInfo queryBranchInfo(Map map)throws Exception {
		
		PagerInfo pagerInfo=null;
		try {
			QueryBranchInfo qbInfo=new QueryBranchInfo();
			//»ñµÃµÇÂ¼officeidºÍcurrencyid
			long OfficeID=Long.parseLong(map.get("OfficeID".toLowerCase()).toString());
			qbInfo.setOfficeID(OfficeID);
			
			long CurrencyID=Long.parseLong(map.get("CurrencyID".toLowerCase()).toString());
			qbInfo.setCurrencyID(CurrencyID);
			
			String strTmp = "";
			
			strTmp=map.get("lIsSingleBank".toLowerCase()).toString();
			if (strTmp != null && strTmp.length() > 0) {
				long lIsSingleBank = Long.parseLong(strTmp);
				qbInfo.setIsSingleBank(lIsSingleBank);
			}
			
			strTmp = map.get("lBranchStartID".toLowerCase()).toString();
			if (strTmp != null && strTmp.length() > 0) {
				long lBranchStartID = Long.parseLong(strTmp);
				qbInfo.setBranchStartID(lBranchStartID);
			}
			
			strTmp = map.get("lBranchEndID".toLowerCase()).toString();
			if (strTmp != null && strTmp.length() > 0) {
				long lBranchEndID = Long.parseLong(strTmp);
				qbInfo.setBranchEndID(lBranchEndID);
			}
			
			pagerInfo = biz.queryBranchInfo(qbInfo);

			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		
		return pagerInfo;
		
	}
	

}
