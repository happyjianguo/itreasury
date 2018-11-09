/*
 * Created on 2004-8-13
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.actiontrace.bizlogic;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.actiontrace.dao.*;
import com.iss.itreasury.loan.actiontrace.dataentity.*;
import com.iss.itreasury.util.*;
import java.util.*;
import java.sql.*;
/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ActionTraceBean
{
	protected Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);

	/**
	 * 写日志
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long writeActionTrace(ActionTraceInfo info) throws Exception
	{
		long ret = -1;
		ActionTraceDao dao = new ActionTraceDao();

		try
		{
			dao.setUseMaxID();
			ret = dao.add(info);
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			throw e;
		}

		return ret;
	}

	/**
			 * 写日志
			 * @param info
			 * @return
			 * @throws Exception
			 */
	public long writeActionTrace(ActionTraceInfo info,Connection conn) throws Exception
	{
		long ret = -1;
		ActionTraceDao dao = new ActionTraceDao(conn);

		try
		{
			dao.setUseMaxID();
			ret = dao.add(info);
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			throw e;
		}

		return ret;
	}

	/**
	 * 根据条件查询日志
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	public Collection findByCondition(ActionTraceQueryInfo qInfo) throws Exception
	{
		Collection c = null;
		ActionTraceDao dao = new ActionTraceDao();

		try
		{
			c = dao.findByCondition(qInfo);
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			throw e;
		}

		return c;
	}

	public Collection findTraceHistory(long moduleID, long traceTypeID, long itemID) throws Exception
	{
		Collection c = null;
		ActionTraceDao dao = new ActionTraceDao();

		try
		{
			c = dao.findTraceHistory(moduleID, traceTypeID, itemID);
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			throw e;
		}

		return c;

	}

}
