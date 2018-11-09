package com.iss.itreasury.ebank.obsystem.action;

import java.util.Map;

import com.iss.itreasury.ebank.obsystem.bizlogic.OBAbstractSettingBiz;
import com.iss.itreasury.ebank.obsystem.dataentity.OBAbstractSettingInfo;
import com.iss.itreasury.system.bulletin.dataentity.BulletinInfo;
import com.iss.itreasury.util.Constant;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class OBAbstractSettingAction {
	OBAbstractSettingBiz oBAbstractSettingBiz = new OBAbstractSettingBiz();
	public PagerInfo query(Map map) throws Exception
	{
		
		PagerInfo pagerInfo = null;
		try
		{
			long lOfficeID = Long.valueOf((String)map.get("lOfficeID".toLowerCase()));
			long lUserID = Long.valueOf((String)map.get("lUserID".toLowerCase()));
			OBAbstractSettingInfo queryInfo = new OBAbstractSettingInfo();
			queryInfo.setNofficeid(lOfficeID);
			queryInfo.setNclientid(lUserID);
			queryInfo.setNstatusid(Constant.RecordStatus.VALID);
			pagerInfo = oBAbstractSettingBiz.query(queryInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	
		
	}
	
	public PagerInfo queryClientCapInfo(Map map) throws Exception
	{
		
		PagerInfo pagerInfo = null;
		try
		{
			long lClientID = Long.valueOf((String)map.get("lClientID".toLowerCase()));
			long lCurrencyID = Long.valueOf((String)map.get("lCurrencyID".toLowerCase()));
			long lISCPFAccount = 2;//Notes中定义的
			//从S825－Ctr.jsp过来时，判断是不是中油用户
			String strIsCPF = (String)map.get("IsCPF".toLowerCase());
			String txtIsCPF = (String)map.get("txtIsCPF".toLowerCase());
			if ((strIsCPF != null && strIsCPF.equalsIgnoreCase("true")) ||  (txtIsCPF != null && txtIsCPF.trim().equalsIgnoreCase("true")))
			{
				lISCPFAccount = 1;
			}
			
			OBAbstractSettingInfo queryInfo = new OBAbstractSettingInfo();
			pagerInfo = oBAbstractSettingBiz.queryClientCapInfo(lClientID,lCurrencyID,lISCPFAccount);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	
		
	}
}
