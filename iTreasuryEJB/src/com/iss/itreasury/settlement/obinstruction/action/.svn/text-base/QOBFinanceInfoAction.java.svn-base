package com.iss.itreasury.settlement.obinstruction.action;

import java.util.Map;

import com.iss.itreasury.settlement.obinstruction.bizlogic.QOBFinanceInfoBiz;
import com.iss.itreasury.settlement.obinstruction.dataentity.QueryOBFinanceInstrInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class QOBFinanceInfoAction {
	
	QOBFinanceInfoBiz qOBFinanceInfoBiz = new QOBFinanceInfoBiz();
	
	public PagerInfo queryOBFinanceInfo(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{
			QueryOBFinanceInstrInfo qInfo = new QueryOBFinanceInstrInfo();
			qInfo.convertMapToDataEntity(map);//将Map转化为INFO
			
			pagerInfo = qOBFinanceInfoBiz.queryOBFinanceInfo(qInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	
	}
	
	public PagerInfo queryBatchOBFinanceInfo(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{
			QueryOBFinanceInstrInfo qInfo = new QueryOBFinanceInstrInfo();
			qInfo.convertMapToDataEntity(map);//将Map转化为INFO
			
			pagerInfo = qOBFinanceInfoBiz.queryBatchOBFinanceInfo(qInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	
	}

	public PagerInfo queryBatchOBFinanceInfoCheck(Map map) throws Exception{
		PagerInfo pagerInfo = null;
		try
		{
			QueryOBFinanceInstrInfo qInfo = new QueryOBFinanceInstrInfo();
			qInfo.convertMapToDataEntity(map);//将Map转化为INFO
			
			pagerInfo = qOBFinanceInfoBiz.queryBatchOBFinanceInfoCheck(qInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	
	}
}
