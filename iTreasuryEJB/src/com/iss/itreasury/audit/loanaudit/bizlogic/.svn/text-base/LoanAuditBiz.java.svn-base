package com.iss.itreasury.audit.loanaudit.bizlogic;

import java.util.Collection;

import com.iss.itreasury.audit.loanaudit.dao.LoanAuditDao;
import com.iss.itreasury.audit.loanaudit.dataentity.LoanAuditCondition;
import com.iss.itreasury.audit.settlementaudit.dataentity.QueryAuditAccountDetailConditionInfo;
import com.iss.itreasury.audit.loanaudit.dataentity.QueryAuditSubjectDetailConditionInfo;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;

public class LoanAuditBiz {

	public Collection queryLoanAudit(LoanAuditCondition condition) throws IException
	{
		Collection result = null;
		LoanAuditDao dao = new LoanAuditDao();
		try {
			result = dao.queryLoanAudit(condition);
		} catch (IException e) {
			// TODO Auto-generated catch block
			throw new IException(e.getMessage(),e);
		}
		return result;
	}
	
	public PageLoader queryAuditSubjectDetail(QueryAuditSubjectDetailConditionInfo condition) throws Exception
	{
		PageLoader pageloader = null;
		LoanAuditDao dao = new LoanAuditDao();
		try {
			pageloader = dao.queryAuditSubjectDetail(condition);
		} catch (IException e) {
			// TODO Auto-generated catch block
			throw new IException(e.getMessage(),e);
		}
		return pageloader;
	}
	
}
