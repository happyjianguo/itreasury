package com.iss.itreasury.creditrating.query.bizlogic;

import java.util.Collection;

import com.iss.itreasury.creditrating.query.dao.CreditRatingRecordQueryDao;
import com.iss.itreasury.creditrating.query.dataentity.CreditRatingRecordQueryInfo;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;

public class CreditRatingRecordQueryBiz {

	public PageLoader queryCreditRating(CreditRatingRecordQueryInfo queryInfo)throws IException
	{
		try
		{
			CreditRatingRecordQueryDao dao = new CreditRatingRecordQueryDao();
		    PageLoader  pageloader = dao.queryCreditRating(queryInfo);
		    return pageloader;
		}
		catch (Exception exp)
		{
			throw new IException("查询信用评级记录列表错误",exp);
		}
	}
	/**
	 * 通过信用评级ID,得到其有效的信用重估记录
	 * @param ratingID
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection findCreditratingreValuedByRatingID(long ratingID)throws IException
	{
		try
		{
			CreditRatingRecordQueryDao dao = new CreditRatingRecordQueryDao();
		    Collection  coll = dao.findCreditratingreValuedByRatingID(ratingID);
		    return coll;
		}
		catch (Exception exp)
		{
			throw new IException("查询信用评级重估记录错误",exp);
		}
	}
	/**
	 * 通过信用评级ID,得到其有效的作废记录
	 * @param ratingID
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection findCreditratingCancelByRatingID(long ratingID)throws IException
	{
		try
		{
			CreditRatingRecordQueryDao dao = new CreditRatingRecordQueryDao();
		    Collection  coll = dao.findCreditratingCancelByRatingID(ratingID);
		    return coll;
		}
		catch (Exception exp)
		{
			throw new IException("查询信用作废记录错误",exp);
		}
	}
	
}
