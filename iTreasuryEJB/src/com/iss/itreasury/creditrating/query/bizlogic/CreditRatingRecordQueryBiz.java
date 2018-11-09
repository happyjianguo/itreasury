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
			throw new IException("��ѯ����������¼�б����",exp);
		}
	}
	/**
	 * ͨ����������ID,�õ�����Ч�������ع���¼
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
			throw new IException("��ѯ���������ع���¼����",exp);
		}
	}
	/**
	 * ͨ����������ID,�õ�����Ч�����ϼ�¼
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
			throw new IException("��ѯ�������ϼ�¼����",exp);
		}
	}
	
}
