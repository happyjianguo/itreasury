package com.iss.itreasury.system.privilege.action;

import java.util.Map;

import com.iss.itreasury.settlement.query.paraminfo.QueryTransAccountDetailWhereInfo;
import com.iss.itreasury.system.privilege.bizlogic.Sys_UserBiz;
import com.iss.itreasury.system.privilege.dataentity.Sys_GroupInfo;
import com.iss.itreasury.system.privilege.dataentity.Sys_UserInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class Sys_UserAction {
	
	Sys_UserBiz sys_UserBiz = new Sys_UserBiz();
	
	public PagerInfo queryUser(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		
		try
		{
			QueryTransAccountDetailWhereInfo qInfo = new QueryTransAccountDetailWhereInfo();
			qInfo.convertMapToDataEntity(map);//将Map转化为INFO
			
			Sys_GroupInfo groupInfo = null;
			Sys_UserInfo userInfo = new Sys_UserInfo();

			String username = "";
			String login = "";
			String groupname = "";
			long officeid = -1;
			String currencyid = "";
			long lOrderCondition = -1;
			long lhCompositorCondition = 1;
			long statusID = -1;
			long m_lIsAdminUser = -1;
			long m_lUserID = -1;
			
			if(map.get("username")!=null && map.get("username").toString().trim().length()>0){
				username = map.get("username").toString().trim();
				userInfo.setName(username);
			}

			if(map.get("loginname")!=null && map.get("loginname").toString().trim().length()>0){
				login = map.get("loginname").toString().trim();
				userInfo.setLoginNo(login);
			}

			if(map.get("groupname")!=null && map.get("groupname").toString().trim().length()>0  && (!("-1").equals(map.get("groupname").toString())) ){
				groupname = map.get("groupname").toString().trim();
				groupInfo = new Sys_GroupInfo();
				groupInfo.setName(groupname);
			}
			
			if(map.get("seloffice")!=null && map.get("seloffice").toString().trim().length()>0){
				officeid = Long.parseLong(map.get("seloffice").toString().trim());
				userInfo.setOfficeID(officeid);
			}

			if(map.get("selcurrency")!=null && map.get("selcurrency").toString().trim().length()>0 && (!("-1").equals(map.get("selcurrency").toString())) && (!("0").equals(map.get("selcurrency").toString()))){
				currencyid = map.get("selcurrency").toString();
				userInfo.setCurrencyID(currencyid);
			}
			
			if(map.get("hordercondition")!=null && map.get("hordercondition").toString().trim().length()>0){
				lOrderCondition = Long.parseLong(map.get("hordercondition").toString().trim());
			}
			
			if(map.get("hcompositorcondition")!=null && map.get("hcompositorcondition").toString().trim().length()>0){
				lhCompositorCondition = Long.parseLong(map.get("hcompositorcondition").toString().trim());
			}
			
			if(map.get("m_lisadminuser")!=null && map.get("m_lisadminuser").toString().trim().length()>0){
				m_lIsAdminUser = Long.parseLong(map.get("m_lisadminuser").toString().trim());
			}
			
			if(map.get("m_luserid")!=null && map.get("m_luserid").toString().trim().length()>0){
				m_lUserID = Long.parseLong(map.get("m_luserid").toString().trim());
				userInfo.setInputUserID(m_lUserID);
			}
			
			if(map.get("statusid")!=null && map.get("statusid").toString().trim().length()>0){
				statusID = Long.parseLong(map.get("statusid").toString().trim());
				userInfo.setStatusID(statusID);
			}
			
			pagerInfo = sys_UserBiz.queryUser(userInfo, groupInfo, lOrderCondition, lhCompositorCondition , m_lIsAdminUser , m_lUserID);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
		
	}

	public PagerInfo queryUser4Check(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		
		try
		{
			QueryTransAccountDetailWhereInfo qInfo = new QueryTransAccountDetailWhereInfo();
			qInfo.convertMapToDataEntity(map);//将Map转化为INFO
			
			Sys_GroupInfo groupInfo = null;
			Sys_UserInfo userInfo = new Sys_UserInfo();

			String username = "";
			String login = "";
			String groupname = "";
			long officeid = -1;
			String currencyid = "";
			long lOrderCondition = -1;
			long lhCompositorCondition = 1;
			long statusID = -1;
			long m_lIsAdminUser = -1;
			long m_lUserID = -1;
			
			if(map.get("username")!=null && map.get("username").toString().trim().length()>0){
				username = map.get("username").toString().trim();
				userInfo.setName(username);
			}

			if(map.get("loginname")!=null && map.get("loginname").toString().trim().length()>0){
				login = map.get("loginname").toString().trim();
				userInfo.setLoginNo(login);
			}

			if(map.get("groupname")!=null && map.get("groupname").toString().trim().length()>0  && (!("-1").equals(map.get("groupname").toString())) ){
				groupname = map.get("groupname").toString().trim();
				groupInfo = new Sys_GroupInfo();
				groupInfo.setName(groupname);
			}
			
			if(map.get("seloffice")!=null && map.get("seloffice").toString().trim().length()>0){
				officeid = Long.parseLong(map.get("seloffice").toString().trim());
				userInfo.setOfficeID(officeid);
			}

			if(map.get("selcurrency")!=null && map.get("selcurrency").toString().trim().length()>0 && (!("-1").equals(map.get("selcurrency").toString())) && (!("0").equals(map.get("selcurrency").toString()))){
				currencyid = map.get("selcurrency").toString();
				userInfo.setCurrencyID(currencyid);
			}
			
			if(map.get("hordercondition")!=null && map.get("hordercondition").toString().trim().length()>0){
				lOrderCondition = Long.parseLong(map.get("hordercondition").toString().trim());
			}
			
			if(map.get("hcompositorcondition")!=null && map.get("hcompositorcondition").toString().trim().length()>0){
				lhCompositorCondition = Long.parseLong(map.get("hcompositorcondition").toString().trim());
			}
			
			if(map.get("m_lisadminuser")!=null && map.get("m_lisadminuser").toString().trim().length()>0){
				m_lIsAdminUser = Long.parseLong(map.get("m_lisadminuser").toString().trim());
			}
			
			if(map.get("m_luserid")!=null && map.get("m_luserid").toString().trim().length()>0){
				m_lUserID = Long.parseLong(map.get("m_luserid").toString().trim());
				userInfo.setCheckUserID(m_lUserID);
			}
			
			if(map.get("statusid")!=null && map.get("statusid").toString().trim().length()>0){
				statusID = Long.parseLong(map.get("statusid").toString().trim());
				userInfo.setStatusID(statusID);
			}
			
			pagerInfo = sys_UserBiz.queryUser4Check(userInfo, groupInfo, lOrderCondition, lhCompositorCondition , m_lIsAdminUser , m_lUserID);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
		
	}

}
