package com.iss.itreasury.settlement.transgeneralledger.action;

import java.util.Map;

import com.iss.itreasury.settlement.transgeneralledger.bizlogic.TransGeneralLedgerBiz;
import com.iss.itreasury.settlement.transgeneralledger.dataentity.TransGeneralLedgerInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class TransGeneralLedgerAction {
	
	TransGeneralLedgerBiz transGeneralLedgerBiz = new TransGeneralLedgerBiz();
	
	public PagerInfo queryTransGeneralLedger(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{
			TransGeneralLedgerInfo qInfo = new TransGeneralLedgerInfo();
			qInfo.convertMapToDataEntity(map);//将Map转化为INFO
			
			long lNstatusids = Long.parseLong(map.get("lStatusID".toLowerCase()).toString());
			
			long[] StatusIDs;
			if (lNstatusids == -1 || lNstatusids == 0) //"全部"选项
			{
				StatusIDs = new long[2];
				StatusIDs[0] = SETTConstant.TransactionStatus.TEMPSAVE;
				StatusIDs[1] = SETTConstant.TransactionStatus.SAVE;
			}
			else
			{
				StatusIDs = new long[1];
				StatusIDs[0] = lNstatusids;
			}
			
			qInfo.setStatusIDs(StatusIDs);
			
			pagerInfo = transGeneralLedgerBiz.queryTransGeneralLedger(qInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	
		
	}
}
