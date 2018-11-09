package com.iss.itreasury.fcinterface.bankportal.bankcode.action;

import java.util.Map;

import com.iss.itreasury.fcinterface.bankportal.bankcode.bizlogic.BankCodeBiz;
import com.iss.itreasury.fcinterface.bankportal.bankcode.dataentity.BankCodeParamInfo;
import com.iss.itreasury.system.bulletin.dataentity.BulletinInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class BankCodeAction {
	BankCodeBiz bankCodeBiz = new BankCodeBiz();
	public PagerInfo queryBankCode(Map map) throws Exception
	{
		
		PagerInfo pagerInfo = null;
		try
		{
			BankCodeParamInfo condition = new BankCodeParamInfo();
			if(map.get("bankName".toLowerCase())!=null && map.get("bankName".toLowerCase()).toString().trim().length()>0){
				condition.setLbankName(map.get("bankName".toLowerCase()).toString().trim());
			}
			if(map.get("bank".toLowerCase())!=null && map.get("bank".toLowerCase()).toString().trim().length()>0){
				condition.setBankTypeName(map.get("bank".toLowerCase()).toString().trim());
			}
			if(map.get("recBankCode".toLowerCase())!=null && map.get("recBankCode".toLowerCase()).toString().trim().length()>0){
				condition.setBankCode(map.get("recBankCode".toLowerCase()).toString().trim());
			}
			if(map.get("province".toLowerCase())!=null && map.get("province".toLowerCase()).toString().trim().length()>0){
				condition.setProvinceName(map.get("province".toLowerCase()).toString().trim());
			}
			if(map.get("city".toLowerCase())!=null && map.get("city".toLowerCase()).toString().trim().length()>0){
				String tempCity = map.get("city".toLowerCase()).toString().trim();
				tempCity = new String(tempCity.getBytes("GBK"), "utf-8");
				if(!"?".equals(tempCity.substring(0,1)))
					condition.setCityName(map.get("city".toLowerCase()).toString().trim());
			}
			if(map.get("keyWord".toLowerCase())!=null && map.get("keyWord".toLowerCase()).toString().trim().length()>0){
				condition.setBankName(map.get("keyWord".toLowerCase()).toString().trim());
			}
			if(map.get("oldReceiveBranchName".toLowerCase())!=null && map.get("oldReceiveBranchName".toLowerCase()).toString().trim().length()>0){
				condition.setOldReceiveBranchName(map.get("oldReceiveBranchName".toLowerCase()).toString().trim());
			}
			pagerInfo = bankCodeBiz.queryBankCode(condition);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	
		
	}
}
