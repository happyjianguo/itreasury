package com.iss.itreasury.settlement.setting.action;

import java.util.Map;
import com.iss.itreasury.settlement.setting.bizlogic.BankInstructionSettingBiz;
import com.iss.itreasury.settlement.setting.dataentity.QueryBranchInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class BankInstructionSettingAction {
	BankInstructionSettingBiz biz = new BankInstructionSettingBiz();
	
	public PagerInfo queryBranchInfo(Map map)throws Exception {
		
		PagerInfo pagerInfo=null;
		try {
			QueryBranchInfo qbInfo = new QueryBranchInfo();
			//»ñµÃµÇÂ¼officeidºÍcurrencyid
			long OfficeID=Long.parseLong(map.get("nOfficeId".toLowerCase()).toString());
			qbInfo.setOfficeID(OfficeID);
			
			long CurrencyID=CurrencyID=Long.parseLong(map.get("nCurrencyId".toLowerCase()).toString());
			qbInfo.setCurrencyID(CurrencyID);
			long TransType=-1;
			long AccountModule=-1;
				
			
			
			if(!map.get("TransType".toLowerCase()).toString().equals(""))
			{
				TransType=Long.parseLong(map.get("TransType".toLowerCase()).toString());
			}
			qbInfo.setBankType(TransType);
			if(!map.get("AccountModule".toLowerCase()).toString().equals(""))
			{
				AccountModule=Long.parseLong(map.get("AccountModule".toLowerCase()).toString());
			}
			qbInfo.setAccountModule(AccountModule);
			pagerInfo = biz.queryBranchInfo(qbInfo);

			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		
		return pagerInfo;
		
	}
	

}
