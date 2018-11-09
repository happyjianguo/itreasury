package com.iss.itreasury.settlement.account.action;


import java.util.Map;

import com.iss.itreasury.settlement.account.bizlogic.Sett_QueryExternalAccountBiz;
import com.iss.itreasury.settlement.account.dataentity.ExternalAccountInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class Sett_ExternalAccountAction
{
	Sett_QueryExternalAccountBiz biz=new Sett_QueryExternalAccountBiz();
	
	public PagerInfo queryExternalAccount(Map map)throws Exception {
		
		PagerInfo pagerInfo=null;
		try {
			ExternalAccountInfo info = new ExternalAccountInfo(); 
			String strAction = null;
			String ExtAcctNo="";//外部银行账户号
			String ExtAcctName="";//外部银行账户名称
		    
				if(map.get("strAction".toLowerCase()).toString()!=null){
					strAction = map.get("strAction".toLowerCase()).toString();		
				}
			
				if(map.get("ExtAcctNo".toLowerCase()).toString()!=null){
					ExtAcctNo =map.get("ExtAcctNo".toLowerCase()).toString();
				}
				if(map.get("ExtAcctName".toLowerCase()).toString()!=null){
					ExtAcctName =map.get("ExtAcctName".toLowerCase()).toString();
				}
				
				long OfficeID=Long.parseLong(map.get("OfficeID".toLowerCase()).toString());
				long CurrencyID=Long.parseLong(map.get("CurrencyID".toLowerCase()).toString());
				
		              
		        info.setOfficeID(OfficeID);
				info.setExtAcctNo(ExtAcctNo);
				info.setExtAcctName(ExtAcctName);
		        info.setNcurrencyID(CurrencyID);
		        
		        pagerInfo = biz.queryExternalAccount(info);
				
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		
		return pagerInfo;
		
	}
	

	

}
