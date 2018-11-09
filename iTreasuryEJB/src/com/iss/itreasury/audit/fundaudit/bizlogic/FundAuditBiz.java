package com.iss.itreasury.audit.fundaudit.bizlogic;

import java.util.Collection;

import com.iss.itreasury.audit.fundaudit.dao.FundAuditDao;
import com.iss.itreasury.audit.fundaudit.dataentity.QueryAuditSubjectDetailConditionInfo;
import com.iss.itreasury.audit.fundaudit.dataentity.FundAuditCondition;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;

public class FundAuditBiz {

	/**
	 * 查询所有的资金业务审计信息
	 * @author 马现福
	 * @param condition
	 * @return
	 * @throws IException
	 */
	public Collection queryFundAudit(FundAuditCondition condition) throws IException
	{
		Collection result = null;
		FundAuditDao dao = new FundAuditDao();
		try {
			result = dao.queryFundAudit(condition);
		} catch (IException e) {
			// TODO Auto-generated catch block
			throw new IException(e.getMessage(),e);
		}
		return result;
	}
	
	/*public PageLoader queryAuditAccountDetail(QueryAuditAccountDetailConditionInfo condition) throws Exception
	{
		PageLoader pageloader = null;
		FundAuditDao dao = new FundAuditDao();
		try {
			pageloader = dao.queryAuditAccountDetail(condition);
		} catch (IException e) {
			// TODO Auto-generated catch block
			throw new IException(e.getMessage(),e);
		}
		return pageloader;
	}*/
	
	/**
	 * 查询科目信息
	 * @author 马现福
	 * @param QueryAuditSubjectDetailConditionInfo
	 */
	public PageLoader queryAuditSubjectDetail(QueryAuditSubjectDetailConditionInfo condition) throws Exception
	{
		PageLoader pageloader = null;
		FundAuditDao dao = new FundAuditDao();
		try {
			pageloader = dao.queryAuditSubjectDetail(condition);
		} catch (IException e) {
			// TODO Auto-generated catch block
			throw new IException(e.getMessage(),e);
		}
		return pageloader;
	}
	
}
