package com.iss.itreasury.system.bulletin.Action;

import java.util.Map;

import com.iss.itreasury.system.bulletin.bizlogic.BulletinBiz;
import com.iss.itreasury.system.bulletin.dataentity.BulletinInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class BulletinAction {
	
	BulletinBiz bulletinBiz = new BulletinBiz();
	
	public PagerInfo query(Map map) throws Exception
	{
		
		String bulletinHeader = null;
		String bulletinClientId = null;
		String tsDateFrom = null;
		String tsDateTo = null;
		long bulletinUserID = -1;
		String bulletinState = null;
		long statusID = -1;
		long lCurrencyID = -1;
		long lOfficeID = -1;
		long modifyId=1;//查询标识
		
		PagerInfo pagerInfo = null;
		try
		{
			bulletinHeader = (String)map.get("bulletinHeader".toLowerCase());
			bulletinClientId = (String)map.get("bulletinClientId".toLowerCase());
			tsDateFrom = (String)map.get("tsDateFrom".toLowerCase());
			tsDateTo = (String)map.get("tsDateTo".toLowerCase());
			if(!((String)map.get("bulletinUserID".toLowerCase())).equals("")){
				bulletinUserID = Long.valueOf((String)map.get("bulletinUserID".toLowerCase()));
			}
			bulletinState = (String)map.get("bulletinState".toLowerCase());
			lCurrencyID = Long.valueOf((String)map.get("OfficeID".toLowerCase()));
			lOfficeID = Long.valueOf((String)map.get("CurrencyID".toLowerCase()));
			
	    	if( (bulletinState!=null)&&(bulletinState.length()>0) ){
	    		if(bulletinState.equals("发布中"))
	    			statusID=1;
	    		else if(bulletinState.equals("已结束"))
	    			statusID=2;
	    		else if(bulletinState.equals("已取消"))
	    			statusID=3;
	    	} 
	    		
	    	BulletinInfo info=new BulletinInfo();
	    	info.setHeader(bulletinHeader);
	    	info.setClients(bulletinClientId);
	    	info.setFromReleaseDate(tsDateFrom);
	    	info.setEndReleaseDate(tsDateTo);
	    	info.setUserID(bulletinUserID);
	    	info.setStatusID(statusID);
	    	info.setModifyId(modifyId);
	    	info.setOfficeId(lCurrencyID);
	    	info.setModuleID(lOfficeID);
			
			pagerInfo = bulletinBiz.query(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	
	
	public PagerInfo notice(Map map) throws Exception
	{
		
		PagerInfo pagerInfo = null;
		try
		{
			long lClientID = Long.valueOf((String)map.get("lClientID".toLowerCase()));
			long lOfficeID = Long.valueOf((String)map.get("lOfficeID".toLowerCase()));
			BulletinInfo bulletinInfo = new BulletinInfo();
			bulletinInfo.setClients(String.valueOf(lClientID));
			bulletinInfo.setOfficeId(lOfficeID);
			pagerInfo = bulletinBiz.notice(bulletinInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	
		
	}
	
	

}
