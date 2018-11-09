package com.iss.itreasury.audit.settlementaudit.bizlogic;

import java.util.Collection;

import com.iss.itreasury.audit.settlementaudit.dao.SettlementAuditDao;
import com.iss.itreasury.audit.settlementaudit.dataentity.QueryAuditAccountDetailConditionInfo;
import com.iss.itreasury.audit.settlementaudit.dataentity.QueryAuditSubjectDetailConditionInfo;
import com.iss.itreasury.audit.settlementaudit.dataentity.SettlementAuditCondition;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;

public class SettlementAuditBiz {

	public Collection querySettlementAudit(SettlementAuditCondition condition) throws IException
	{
		Collection result = null;
		SettlementAuditDao dao = new SettlementAuditDao();
		try {
			result = dao.querySettlementAudit(condition);
		} catch (IException e) {
			// TODO Auto-generated catch block
			throw new IException(e.getMessage(),e);
		}
		return result;
	}
	
	public PageLoader queryAuditAccountDetail(QueryAuditAccountDetailConditionInfo condition) throws Exception
	{
		PageLoader pageloader = null;
		SettlementAuditDao dao = new SettlementAuditDao();
		try {
			pageloader = dao.queryAuditAccountDetail(condition);
		} catch (IException e) {
			// TODO Auto-generated catch block
			throw new IException(e.getMessage(),e);
		}
		return pageloader;
	}
	public PageLoader queryAuditSubjectDetail(QueryAuditSubjectDetailConditionInfo condition) throws Exception
	{
		PageLoader pageloader = null;
		SettlementAuditDao dao = new SettlementAuditDao();
		try {
			pageloader = dao.queryAuditSubjectDetail(condition);
		} catch (IException e) {
			// TODO Auto-generated catch block
			throw new IException(e.getMessage(),e);
		}
		return pageloader;
	}
	
}
