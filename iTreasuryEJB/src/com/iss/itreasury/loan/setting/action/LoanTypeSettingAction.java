package com.iss.itreasury.loan.setting.action;

import java.util.Map;

import com.iss.itreasury.clientmanage.client.dataentity.ExtClientInfo;
import com.iss.itreasury.clientmanage.client.dataentity.QueryCorporationInfo;
import com.iss.itreasury.loan.setting.bizlogic.LoanTypeBiz;
import com.iss.itreasury.loan.setting.dataentity.LoanAssortSettingInfo;
import com.iss.itreasury.loan.setting.dataentity.LoanTypeSettingInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class LoanTypeSettingAction {

	LoanTypeBiz loanTypeBiz = new LoanTypeBiz();
/**
 * 贷款类型分类设置action层
 * add by liaiyi
 * @return
 * @throws Exception
 */	
	public PagerInfo queryLoanType(Map map) throws Exception
	{
    	long OfficeID = -1;
    	long StatusID = -1;
    	long LoanTypeID = -1;
    	String Code = null;
    	PagerInfo pagerInfo = null;

    	try
		{
			LoanTypeSettingInfo info = new LoanTypeSettingInfo();
			if(map.get("officeid") != null &&!map.get("officeid").equals("")){
				OfficeID = Long.parseLong((String)map.get("officeid"));
				info.setOfficeID(OfficeID);
			}
			if(map.get("statusid") != null &&!map.get("statusid").equals("")){
				StatusID = Long.parseLong((String)map.get("statusid"));
				info.setStatusID(StatusID);
			}
			
			if(map.get("lloantype") != null &&!map.get("lloantype").equals("")){
				LoanTypeID = Long.parseLong((String)map.get("lloantype"));
				info.setLoanTypeID(LoanTypeID);
			}
			if(map.get("txtloantypecode") != null  &&!map.get("txtloantypecode").equals("")){
				Code =(String)map.get("txtloantypecode");
				info.setCode(Code);
			}
		
			pagerInfo = loanTypeBiz.queryLoanTypeInfo(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(),e);
		}
		return pagerInfo;
}
	/**
	 * 贷款合同分类设置action层
	 * add by liaiyi
	 * @return
	 * @throws Exception
	 */	
	public PagerInfo queryLoanAssort(Map map) throws Exception
	{
		long officeId = -1;
    	long statusId = -1;
    	long assortTypeId = -1;
		PagerInfo pagerInfo = null;
		try
		{
			LoanAssortSettingInfo loanAssortSettingInfo = new LoanAssortSettingInfo();
			//loanAssortSettingInfo.convertMapToDataEntity(map);
			if(map.get("officeid") != null&&!map.get("officeid").equals("")){
				officeId = Long.parseLong((String)map.get("officeid"));
				loanAssortSettingInfo.setOfficeId(officeId);
			}
			if(map.get("statusid") != null&&!map.get("statusid").equals("")){
				statusId = Long.parseLong((String)map.get("statusid"));
				loanAssortSettingInfo.setStatusId(statusId);
			}
			
			if(map.get("assorttypeid") != null&&!map.get("assorttypeid").equals("")){
				assortTypeId = Long.parseLong((String)map.get("assorttypeid"));
				loanAssortSettingInfo.setAssortTypeId(assortTypeId);
			}
			pagerInfo = loanTypeBiz.queryLoanAssortInfo(loanAssortSettingInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
}