package com.iss.itreasury.audit.riskaudit.bizlogic;

import java.util.Collection;

import com.iss.itreasury.audit.interzonemaintenance.dao.InterzonemaintenanceDao;
import com.iss.itreasury.audit.interzonemaintenance.dataentity.InterzoneAdjustInfo;
import com.iss.itreasury.audit.interzonemaintenance.dataentity.InterzonemaintenanceResultInfo;
import com.iss.itreasury.audit.riskaudit.dao.Riskauditdao;
import com.iss.itreasury.audit.riskaudit.dataentity.RiskauditInfo;
import com.iss.itreasury.util.IException;

public class Riskauditbiz {
	public Collection queryAllzoneByIndexDate(InterzoneAdjustInfo info) throws IException
	{
		Collection result = null;
		Riskauditdao dao = new Riskauditdao();
		try {
			result = dao.queryAllzoneByIndexDate(info);
		} catch (IException e) {
			throw new IException(e.getMessage(),e);
		}
		return result;
	}
	public RiskauditInfo selInfo(InterzoneAdjustInfo aInfo) throws Exception
	{
		Riskauditdao dao = new Riskauditdao();
		RiskauditInfo selInfo = new RiskauditInfo();
		try {
			selInfo = dao.selInfo(aInfo);
		}
	    catch (IException e) {
		throw new IException(e.getMessage(),e);
	}
	    return selInfo;
	}
	public Collection queryAllIndex(InterzoneAdjustInfo info) throws IException
	{
		Collection result = null;
		Riskauditdao dao = new Riskauditdao();
		try {
			result = dao.queryAllIndex(info);
		} catch (IException e) {
			throw new IException(e.getMessage(),e);
		}
		return result;
	}
}
