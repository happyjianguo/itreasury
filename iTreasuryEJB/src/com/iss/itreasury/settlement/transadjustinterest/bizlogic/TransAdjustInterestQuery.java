package com.iss.itreasury.settlement.transadjustinterest.bizlogic;

import com.iss.itreasury.settlement.transadjustinterest.dao.QueryAdjustInterestDao;
import com.iss.itreasury.settlement.transadjustinterest.dataentity.AccumulateFeeInfoQuery;
import com.iss.itreasury.settlement.transadjustinterest.dataentity.AdjustInterestInfoQuery;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;


public class TransAdjustInterestQuery
{
	private static Log4j log4j = null;
	
	public PageLoader  queryTransAdjustInterestFromHistory(AccumulateFeeInfoQuery accInfo) throws Exception
	{
		PageLoader c = null;
		QueryAdjustInterestDao dao = new QueryAdjustInterestDao();
		try
		{
			c = dao.adjustFindFormHistory(accInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return c;
	}
	
	public PageLoader queryTransAdjustInterest(AccumulateFeeInfoQuery accInfo) throws Exception
	{
		PageLoader c = null;
		QueryAdjustInterestDao dao = new QueryAdjustInterestDao();
		try
		{
			c = dao.adjustFind(accInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return c;
	}
	
	public PageLoader findAdjustByCondition (AdjustInterestInfoQuery adjustInfo) throws Exception
	{
		PageLoader c = null;
		QueryAdjustInterestDao dao = new QueryAdjustInterestDao();
		try
		{
			c = dao.findAdjustByCondition(adjustInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return c;
	}
	
}