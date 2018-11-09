package com.iss.itreasury.ebank.privilege.action;

import java.util.Map;

import com.iss.itreasury.ebank.privilege.bizlogic.OB_UserBiz;
import com.iss.itreasury.system.dataentity.QueryClientSAInfo;
import com.iss.itreasury.system.privilege.dataentity.Sys_GroupInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class OB_UserAction {
	OB_UserBiz oB_UserBiz = new OB_UserBiz();
	
	public PagerInfo queryUser(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		
		try
		{
			QueryClientSAInfo qInfo = new QueryClientSAInfo();
			//qInfo.convertMapToDataEntity(map);//将Map转化为INFO

			long lOfficeID = -1;
			String strCodeStart = "";
			String strCodeEnd = "";
			long isAdmin = -1;
			long statusID = -1;
			String startDate = "";
			String endDate = "";
			long OfficeID = -1;
			
			if(map.get("selofficeview")!=null && map.get("selofficeview").toString().trim().length()>0){
				lOfficeID = Long.parseLong(map.get("selofficeview").toString().trim());
				qInfo.setOfficeID(lOfficeID);
			}

			if(map.get("clientcodestartctrl")!=null && map.get("clientcodestartctrl").toString().trim().length()>0){
				strCodeStart = map.get("clientcodestartctrl").toString().trim();
				qInfo.setStartClientCode(strCodeStart);
			}

			if(map.get("clientcodeendctrl")!=null && map.get("clientcodeendctrl").toString().trim().length()>0 ){
				strCodeEnd = map.get("clientcodeendctrl").toString().trim();
				qInfo.setEndClientCode(strCodeEnd);
			}
			
			if(map.get("startdate")!=null && map.get("startdate").toString().trim().length()>0){
				startDate = map.get("startdate").toString().trim();
				qInfo.setStartDate(startDate);
			}

			if(map.get("enddate")!=null && map.get("enddate").toString().trim().length()>0){
				endDate = map.get("enddate").toString().trim();
				qInfo.setEndDate(endDate);
			}
			
			if(map.get("isadmin")!=null && map.get("isadmin").toString().trim().length()>0){
				isAdmin = Long.parseLong(map.get("isadmin").toString().trim());
				qInfo.setIsAdmin(isAdmin);
			}
			
			if(map.get("statusid")!=null && map.get("statusid").toString().trim().length()>0){
				statusID = Long.parseLong(map.get("statusid").toString().trim());
				qInfo.setStatusID(statusID);
			}
			if(map.get("officeid")!=null && map.get("officeid").toString().trim().length()>0){
				OfficeID = Long.parseLong(map.get("officeid").toString().trim());
			}
			
			pagerInfo = oB_UserBiz.queryUser(qInfo,OfficeID);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
		
	}
	
	public PagerInfo queryUser4Check(Map map) throws Exception{

		PagerInfo pagerInfo = null;
		
		try
		{
			QueryClientSAInfo qInfo = new QueryClientSAInfo();

			long lOfficeID = -1;
			String strCodeStart = "";
			String strCodeEnd = "";
			long statusID = -1;
			long inputUserId = -1;
			
			if(map.get("lofficeid")!=null && map.get("lofficeid").toString().trim().length()>0){
				lOfficeID = Long.parseLong(map.get("lofficeid").toString().trim());
				qInfo.setOfficeID(lOfficeID);
			}

			if(map.get("clientcodestartctrl")!=null && map.get("clientcodestartctrl").toString().trim().length()>0){
				strCodeStart = map.get("clientcodestartctrl").toString().trim();
				qInfo.setStartClientCode(strCodeStart);
			}

			if(map.get("clientcodeendctrl")!=null && map.get("clientcodeendctrl").toString().trim().length()>0 ){
				strCodeEnd = map.get("clientcodeendctrl").toString().trim();
				qInfo.setEndClientCode(strCodeEnd);
			}
			
			if(map.get("statusid")!=null && map.get("statusid").toString().trim().length()>0){
				statusID = Long.parseLong(map.get("statusid").toString().trim());
				qInfo.setStatus(statusID);
			}
			
			if(map.get("inputuserid")!=null && map.get("inputuserid").toString().trim().length()>0){
				inputUserId = Long.parseLong(map.get("inputuserid").toString().trim());
				qInfo.setInputUserID(inputUserId);
			}
			
			pagerInfo = oB_UserBiz.queryUser4Check(qInfo);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	
	}
	
	public PagerInfo queryUser4Mng(Map map) throws Exception{

		PagerInfo pagerInfo = null;
		
		try
		{
			QueryClientSAInfo qInfo = new QueryClientSAInfo();

			long lClientID = -1;
			
			if(map.get("lclientid")!=null && map.get("lclientid").toString().trim().length()>0){
				lClientID = Long.parseLong(map.get("lclientid").toString().trim());
			}
			
			pagerInfo = oB_UserBiz.queryUser4Mng(lClientID);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	
	}
	
	public PagerInfo queryUser4Authorized(Map map) throws Exception{

		PagerInfo pagerInfo = null;
		
		try
		{
			QueryClientSAInfo qInfo = new QueryClientSAInfo();

			long lClientID = -1;
			
			if(map.get("lclientid")!=null && map.get("lclientid").toString().trim().length()>0){
				lClientID = Long.parseLong(map.get("lclientid").toString().trim());
			}
			
			pagerInfo = oB_UserBiz.queryUser4Authorized(lClientID);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	
	}
}
