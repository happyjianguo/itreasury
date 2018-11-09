package com.iss.itreasury.audit.interestaudit.bizlogic;

import java.util.Collection;

import com.iss.itreasury.audit.interestaudit.dao.InterestAuditDao;
import com.iss.itreasury.audit.interestaudit.dataentity.InterestAuditCondition;
import com.iss.itreasury.util.IException;

public class InterestAuditBiz {

	public Collection querySettlementInterestAudit(InterestAuditCondition condition) throws IException
	{
		Collection result = null;
		InterestAuditDao dao = new InterestAuditDao();
		try {
			result = dao.querySettlementInterestAudit(condition);
		} catch (IException e) {
			// TODO Auto-generated catch block
			throw new IException(e.getMessage(),e);
		}
		return result;
	}
	
	public Collection queryLoanInterestAudit(InterestAuditCondition condition) throws IException
	{
		Collection result = null;
		InterestAuditDao dao = new InterestAuditDao();
		try {
			result = dao.queryLoanInterestAudit(condition);
		} catch (IException e) {
			// TODO Auto-generated catch block
			throw new IException(e.getMessage(),e);
		}
		return result;
	}
}
