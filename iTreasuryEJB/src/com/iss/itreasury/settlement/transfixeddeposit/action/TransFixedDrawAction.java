package com.iss.itreasury.settlement.transfixeddeposit.action;

import java.util.Map;

import com.iss.itreasury.settlement.transfixeddeposit.bizlogic.TransFixedDrawBiz;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class TransFixedDrawAction {
	
	TransFixedDrawBiz transFixedDrawBiz = new TransFixedDrawBiz();

	public PagerInfo queryTransFixedDraw(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{
			QueryByStatusConditionInfo qInfo = new QueryByStatusConditionInfo();
			qInfo.convertMapToDataEntity(map);//��Mapת��ΪINFO
			long lTypeID = Long.parseLong(map.get("lTransactionTypeID".toLowerCase()).toString());
			qInfo.setTransactionTypeID(lTypeID);
			long lStatusID = Long.parseLong(map.get("lStatusID".toLowerCase()).toString());
			long[] StatusIDs;
			if (lStatusID == -1 || lStatusID == 0) //"ȫ��"ѡ��
			{
				qInfo.setTypeID(0);
				StatusIDs = new long[2];
				StatusIDs[0] = SETTConstant.TransactionStatus.TEMPSAVE;
				StatusIDs[1] = SETTConstant.TransactionStatus.SAVE;
			}
			else
			{
				qInfo.setTypeID(0);
				StatusIDs = new long[1];
				StatusIDs[0] = lStatusID;
			}
			qInfo.setStatus(StatusIDs);
			pagerInfo = transFixedDrawBiz.queryTransFixedDraw(qInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
		
	}
	
	public PagerInfo queryTransFixedDraw_check(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{
			QueryByStatusConditionInfo qInfo = new QueryByStatusConditionInfo();
			qInfo.convertMapToDataEntity(map);//��Mapת��ΪINFO
			long lStatusID = Long.parseLong(map.get("lStatusID".toLowerCase()).toString());
			long[] StatusIDs;
			if (lStatusID == -1 || lStatusID == 0) //"ȫ��"ѡ��
			{
				qInfo.setTypeID(1);
				StatusIDs = new long[2];
				StatusIDs[0] = SETTConstant.TransactionStatus.TEMPSAVE;
				StatusIDs[1] = SETTConstant.TransactionStatus.SAVE;
			}
			else
			{
				qInfo.setTypeID(1);
				StatusIDs = new long[1];
				StatusIDs[0] = lStatusID;
			}
			qInfo.setStatus(StatusIDs);
			pagerInfo = transFixedDrawBiz.queryTransFixedDraw(qInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	
	}
	

}
