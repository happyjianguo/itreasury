package com.iss.itreasury.system.setting.action;

import java.util.Map;

import com.iss.itreasury.codingrule.dataentity.CodingRuleInfo;
import com.iss.itreasury.system.setting.bizlogic.DepartmentBiz;
import com.iss.itreasury.system.setting.dataentity.DepartmentInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class DepartmentAction {

	DepartmentBiz departmentBiz = new DepartmentBiz();
	
	/**
	 * 编码规则设置查询类
	 * add by liaiyi 2012-12-10
	 */
	public PagerInfo queryCodingRuleDetailInfo(Map map)throws Exception {
		
		PagerInfo pagerInfo = null;
		long officeid = -1;
		long statusID = -1;
		try
		{
			CodingRuleInfo codingRuleInfo = new CodingRuleInfo();
			if(map.get("officeid") != null){
				officeid = Long.parseLong((String)map.get("officeid"));
				codingRuleInfo.setOfficeid(officeid);
			}
			if(map.get("statusid") != null){
				statusID = Long.parseLong((String)map.get("statusid"));
				codingRuleInfo.setStatusID(statusID);
			}
			pagerInfo = departmentBiz.queryCodingRuleDetailInfo(officeid,statusID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(),e);
		}
		return pagerInfo;
	}
	
	
	/**
	 * 部门设置查询类
	 * add by liaiyi 2012-12-12
	 *
	 */
	public PagerInfo querydepartmentInfo(Map map) throws Exception {
		
		PagerInfo pagerInfo = null;
		long OfficeID = -1;
		long statusID = -1;
		try
		{
			DepartmentInfo departmentInfo = new DepartmentInfo();
			if(map.get("officeid") != null){
				OfficeID = Long.parseLong((String)map.get("officeid"));
				departmentInfo.setOfficeID(OfficeID);
			}
			if(map.get("statusid") != null){
				statusID = Long.parseLong((String)map.get("statusid"));
				departmentInfo.setStatusID(statusID);
			}
			pagerInfo = departmentBiz.querydepartmentInfo(departmentInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(),e);
		}
		return pagerInfo;
	}
}
