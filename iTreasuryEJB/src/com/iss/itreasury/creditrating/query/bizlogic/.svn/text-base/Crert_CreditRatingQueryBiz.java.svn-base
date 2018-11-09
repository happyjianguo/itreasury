package com.iss.itreasury.creditrating.query.bizlogic;

import com.iss.itreasury.creditrating.query.dao.Crert_CreditRatingQueryDAO;
import com.iss.itreasury.creditrating.query.dataentity.Crert_CreditRatingQueryInfo;
import com.iss.system.dao.PageLoader;

public class Crert_CreditRatingQueryBiz {

	private Crert_CreditRatingQueryDAO crert_CreditRatingQueryDAO = null;
	
	public Crert_CreditRatingQueryBiz()
	{
		crert_CreditRatingQueryDAO = new Crert_CreditRatingQueryDAO();
	}
	
	
	public PageLoader queryCreditRatingList(Crert_CreditRatingQueryInfo crert_CreditRatingQueryInfo)throws Exception
	{
		
		return crert_CreditRatingQueryDAO.queryCreditRatingList(crert_CreditRatingQueryInfo);
		
	}
	public String getQueryCreditRatingListOrderParam(long lDesc,long lOrderParam)
	{
		return crert_CreditRatingQueryDAO.getQueryCreditRatingListOrderParam(lDesc, lOrderParam);
	}
}
