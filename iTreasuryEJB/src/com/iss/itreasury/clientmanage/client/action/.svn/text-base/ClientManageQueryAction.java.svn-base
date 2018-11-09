package com.iss.itreasury.clientmanage.client.action;

import java.util.Map;

import com.iss.itreasury.clientmanage.client.bizlogic.ClientManageQueryBiz;
import com.iss.itreasury.clientmanage.dataentity.ClientInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class ClientManageQueryAction {
	
	private ClientManageQueryBiz biz = new ClientManageQueryBiz();
	
	public PagerInfo findStockPartnerInformation(Map map) throws Exception {

		PagerInfo pagerInfo = null;
		try {
			long lClientID = Long.valueOf((map.get("lborrowclientid")+""));
			long lOfficeID = Long.valueOf((map.get("officeid")+""));
			pagerInfo = biz.findStockPartnerInformation(lClientID,lOfficeID);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	
	
	
	public PagerInfo findByClientID(Map map) throws Exception {

		PagerInfo pagerInfo = null;
		try {
			long lClientID = Long.valueOf((map.get("lborrowclientid")+""));
			pagerInfo = biz.findByClientID(lClientID);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	
	public PagerInfo findClientReportByID(Map map) throws Exception {

		PagerInfo pagerInfo = null;
		try {
			long lClientID = Long.valueOf((map.get("lborrowclientid")+""));
			long lOfficeID = Long.valueOf((map.get("officeid")+""));
			pagerInfo = biz.findClientReportByID(lClientID,lOfficeID);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	
	public PagerInfo queryManagerInfo(Map map) throws Exception {

		PagerInfo pagerInfo = null;
		try {
			ClientInfo clientInfo9 = new ClientInfo();
			String code = map.get("code")+"";
			long lOfficeID = Long.valueOf((map.get("officeid")+""));
			clientInfo9.setCode(code);
		    clientInfo9.setOfficeID(lOfficeID);
			pagerInfo = biz.queryManagerInfo(clientInfo9);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	

	public PagerInfo queryEnterpriseMemoInfo(Map map) throws Exception {

		PagerInfo pagerInfo = null;
		try {
			ClientInfo clientInfo9 = new ClientInfo();
			String code = map.get("code")+"";
			long lOfficeID = Long.valueOf((map.get("officeid")+""));
			clientInfo9.setCode(code);
		    clientInfo9.setOfficeID(lOfficeID);
			pagerInfo = biz.queryEnterpriseMemoInfo(clientInfo9);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	
}
