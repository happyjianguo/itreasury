/*
 * Created on 2004-6-9
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.system.bizdelegation;

import com.iss.itreasury.system.logger.bizlogic.LoggerBean;
import com.iss.itreasury.system.logger.dataentity.LoggerInfo;
import com.iss.itreasury.system.logger.dataentity.QueryLoggerInfo;
import com.iss.system.dao.PageLoader;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class LoggerDelegation {
	
	public void addLogger(LoggerInfo li) throws Exception
	{
		LoggerBean bean = new LoggerBean();
		bean.add(li);
	}
	public PageLoader findLoggerByCondition(QueryLoggerInfo qli) throws Exception
	{
		LoggerBean bean = new LoggerBean();
		return bean.findLoggerByCondition(qli);
	}
}
