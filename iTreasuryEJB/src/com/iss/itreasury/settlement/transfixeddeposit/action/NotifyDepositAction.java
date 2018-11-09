package com.iss.itreasury.settlement.transfixeddeposit.action;

import java.util.Map;

import com.iss.itreasury.settlement.transfixeddeposit.bizlogic.TransNotifyDepositBiz;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.NotifyDepositInformInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class NotifyDepositAction {
	
	TransNotifyDepositBiz transNotifyDepositBiz = new TransNotifyDepositBiz();
	
	public PagerInfo queryNotifyDeposit(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{
			NotifyDepositInformInfo qInfo = new NotifyDepositInformInfo();
			qInfo.convertMapToDataEntity(map);//��Mapת��ΪINFO
			pagerInfo = transNotifyDepositBiz.queryNotifyDeposit(qInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
		
	}
	
	public PagerInfo queryNotifyDeposit4ebank(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{
			NotifyDepositInformInfo qInfo = new NotifyDepositInformInfo();
			qInfo.convertMapToDataEntity(map);//��Mapת��ΪINFO
			pagerInfo = transNotifyDepositBiz.queryNotifyDeposit4ebank(qInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
		
	}
}
