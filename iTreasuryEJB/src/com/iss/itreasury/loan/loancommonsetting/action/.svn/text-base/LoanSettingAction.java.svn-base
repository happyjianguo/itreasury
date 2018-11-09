package com.iss.itreasury.loan.loancommonsetting.action;

import java.util.Map;

import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.loancommonsetting.bizlogic.LoanContractBiz;
import com.iss.itreasury.loan.loancommonsetting.bizlogic.LoanSettingBiz;

import com.iss.sysframe.pager.dataentity.PagerInfo;

public class LoanSettingAction {

   LoanSettingBiz loanSettingBiz = new LoanSettingBiz();
/**
 * 申请书管理人权限转移action层
 * add by liaiyi 2012-12-17
 * @return
 * @throws Exception
 */	
	public PagerInfo queryloanSetting(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		long inputUserID = -1;
		try
		{
			ContractInfo ci = new ContractInfo();
			ci.convertMapToDataEntity(map);//将Map转化为INFO
			
			if(map.get("lapplymanageridold") != null && !map.get("lapplymanageridold").equals("")){
				inputUserID = Long.parseLong((String)map.get("lapplymanageridold"));
				ci.setLInputUserID(inputUserID);
			}
			
			pagerInfo = loanSettingBiz.queryAccount(ci);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	/**合同管理人权限转移action层
	 * add by liaiyi 2012-12-17
	 * 
	 */
		LoanContractBiz loanContractBiz = new LoanContractBiz();

		public PagerInfo queryContract(Map map) throws Exception
		{
			PagerInfo pagerInfo = null;
			long inputUserID = -1;
			long StatusID = -1;
			
			try
			{
				ContractInfo ci = new ContractInfo();
				ci.convertMapToDataEntity(map);//将Map转化为INFO
				
				if(map.get("lcontractmanageridold") != null && !map.get("lcontractmanageridold").equals("")){
					inputUserID = Long.parseLong((String)map.get("lcontractmanageridold"));
					ci.setLInputUserID(inputUserID);
				}
//				if(map.get("lcontractstatus") != null && !map.get("lcontractstatus").equals("")){
//					StatusID = Long.parseLong((String)map.get("lcontractstatus"));
//					ci.setLInputUserID(StatusID);
//				}
				
				pagerInfo = loanContractBiz.queryContract(ci);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				throw new Exception(e.getMessage(), e);
			}
			return pagerInfo;
		}
		
}
