package com.iss.itreasury.codingrule.action;

import java.util.Map;

import com.iss.itreasury.codingrule.bizlogic.EncodingRulesBiz;
import com.iss.itreasury.codingrule.dataentity.CodingRuleInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class EncodingRulesAction {

	EncodingRulesBiz encodingRulesBiz = new EncodingRulesBiz();
	
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
			pagerInfo = encodingRulesBiz.queryCodingRuleDetailInfo(officeid,statusID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(),e);
		}
		return pagerInfo;
	}
}
