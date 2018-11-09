package com.iss.itreasury.settlement.transloan.action;

import java.util.HashMap;
import java.util.Map;

import com.iss.itreasury.settlement.transloan.bizlogic.TransloanBiz;
import com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class TransloanAction {
	TransloanBiz transloanBiz = new TransloanBiz();
	
	public PagerInfo queryTransloan(Map map) throws Exception
	{

		PagerInfo pagerInfo = null;
		try
		{
			TransRepaymentLoanInfo qInfo = new TransRepaymentLoanInfo();
			qInfo.convertMapToDataEntity(map);//将Map转化为INFO
			long lStatusID = Long.parseLong(map.get("lStatusID".toLowerCase()).toString());
			
			long[] StatusIDs;
			if (lStatusID == -1 || lStatusID == 0) //"全部"选项
			{
				StatusIDs = new long[2];
				StatusIDs[0] = SETTConstant.TransactionStatus.TEMPSAVE;
				StatusIDs[1] = SETTConstant.TransactionStatus.SAVE;
			}
			else
			{
				StatusIDs = new long[1];
				StatusIDs[0] = lStatusID;
			}
			
			qInfo.setStatusID(StatusIDs);
			
			pagerInfo = transloanBiz.queryTransloan(qInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	
		
	
	}
	public PagerInfo getRepaymentCollectionByTransNo(Map map) throws Exception
	{
		
		return transloanBiz.grantFindInterestByLoanNoteID((HashMap)map);
	}
}
