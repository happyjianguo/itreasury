package com.iss.itreasury.system.history.action;

import java.util.Map;

import com.iss.itreasury.system.approval.dataentity.InutApprovalRecordInfo;
import com.iss.itreasury.system.history.bizlogic.ApproveHistoryBiz;
import com.iss.itreasury.system.history.dataentity.HistoryAdviseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class ApproveHistoryAction {
	
	ApproveHistoryBiz ApproveHistoryBiz = new ApproveHistoryBiz();

	/**
	 * …Û≈˙“‚º˚
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zk 2012-01-18
	 */
	public PagerInfo queryApproveHistoryInfo(Map map) throws Exception {
		
		PagerInfo pagerInfo = null;
		long ID = -1;
		long actionID = -1;
		long loanSubType = -1;
		long moduleID = -1;
		long officeID = -1;
		long currencyID = -1;
		long userID = -1;
		String transNo = null;
		try {
			HistoryAdviseInfo hInfo = new HistoryAdviseInfo();
			InutApprovalRecordInfo aInfo = new InutApprovalRecordInfo();
			if(map != null && map.get("id") != null){
				ID = Long.parseLong((String) map.get("id"));
				hInfo.setEntryID(ID);
			}
			if(map != null && map.get("actionid") != null){
				actionID = Long.parseLong((String) map.get("actionid"));
				aInfo.setActionID(actionID);
			}
			if(map != null && map.get("loansubtype") != null){
				loanSubType = Long.parseLong((String) map.get("loansubtype"));
				aInfo.setTransTypeID(loanSubType);
			}
			if(map != null && map.get("moduleid") != null){
				moduleID = Long.parseLong((String) map.get("moduleid"));
				aInfo.setModuleID(moduleID);
			}
			if(map != null && map.get("officeid") != null){
				officeID = Long.parseLong((String) map.get("officeid"));
				aInfo.setOfficeID(officeID);
			}
			if(map != null && map.get("currencyid") != null){
				currencyID = Long.parseLong((String) map.get("currencyid"));
				aInfo.setCurrencyID(currencyID);
			}
			if(map != null && map.get("transno") != null){
				transNo = (String) map.get("transno");
				aInfo.setTransID(transNo);
			}
			if(map != null && map.get("userid") != null){
				userID = Long.parseLong((String) map.get("userid"));
			}	
			pagerInfo = ApproveHistoryBiz.queryApproveHistoryInfo(hInfo,aInfo,userID);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
}
