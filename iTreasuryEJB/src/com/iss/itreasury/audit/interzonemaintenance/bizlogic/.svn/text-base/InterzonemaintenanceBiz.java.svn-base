package com.iss.itreasury.audit.interzonemaintenance.bizlogic;

import java.util.Collection;

import com.iss.itreasury.audit.interestaudit.dao.InterestAuditDao;
import com.iss.itreasury.audit.interestaudit.dataentity.InterestAuditCondition;
import com.iss.itreasury.audit.interzonemaintenance.dao.InterzonemaintenanceDao;
import com.iss.itreasury.audit.interzonemaintenance.dataentity.InterzoneAdjustInfo;
import com.iss.itreasury.audit.interzonemaintenance.dataentity.InterzonemaintenanceResultInfo;
import com.iss.itreasury.settlement.setting.dataentity.NotifySettingInfo;
import com.iss.itreasury.util.IException;

public class InterzonemaintenanceBiz {

	public Collection queryInterzoneByIndexid(InterzonemaintenanceResultInfo info) throws IException
	{
		Collection result = null;
		InterzonemaintenanceDao dao = new InterzonemaintenanceDao();
		try {
			result = dao.queryInterzoneByIndexid(info);
		} catch (IException e) {
			throw new IException(e.getMessage(),e);
		}
		return result;
	}
	public InterzonemaintenanceResultInfo queryInterByIndexid(InterzonemaintenanceResultInfo info) throws IException
	{
		InterzonemaintenanceResultInfo result = new InterzonemaintenanceResultInfo();
		InterzonemaintenanceDao dao = new InterzonemaintenanceDao();
		try {
			result = dao.queryInterIndexid(info);
		} catch (IException e) {
			throw new IException(e.getMessage(),e);
		}
		return result;
	}
	public long saveInfo(InterzoneAdjustInfo aInfo) throws Exception
	{
		InterzonemaintenanceDao dao = new InterzonemaintenanceDao();	
		long ID = -1;
		try {
			if (dao.checkRiskDate(aInfo) > 0)
            {
			throw new IException("该指标生效日期重复！");
            }
			ID = dao.addInfo(aInfo);
		}
	    catch (IException e) {
		throw new IException(e.getMessage(),e);
	}
	    return ID;
	}
	public long delInfo(InterzoneAdjustInfo aInfo) throws Exception
	{
		InterzonemaintenanceDao dao = new InterzonemaintenanceDao();	
		long ID = -1;
		try {
			ID = dao.delInfo(aInfo);
		}
	    catch (IException e) {
		throw new IException(e.getMessage(),e);
	}
	    return ID;
	}
	public InterzoneAdjustInfo selInfo(InterzoneAdjustInfo aInfo) throws Exception
	{
		InterzonemaintenanceDao dao = new InterzonemaintenanceDao();
		InterzoneAdjustInfo selInfo = new InterzoneAdjustInfo();
		try {
			selInfo = dao.selInfo(aInfo);
		}
	    catch (IException e) {
		throw new IException(e.getMessage(),e);
	}
	    return selInfo;
	}
	
	public long updateInfo(InterzoneAdjustInfo aInfo) throws Exception
	{
		InterzonemaintenanceDao dao = new InterzonemaintenanceDao();
		InterzoneAdjustInfo selInfo = new InterzoneAdjustInfo();
		long ID = -1;
		try {
		if (dao.checkRiskDate(aInfo) > 0)
            {
			throw new IException("该指标生效日期重复！");
         }
		 ID = dao.updateInfo(aInfo);
		}
	    catch (IException e) {
		throw new IException(e.getMessage(),e);
	}
	    return ID;
	}
	public Collection queryInterzoneInterestAudit(InterzoneAdjustInfo condition) throws IException
	{
		Collection result = null;
		InterzonemaintenanceDao dao = new InterzonemaintenanceDao();
		try {
			result = dao.queryInterzoneInterestAudit(condition);
		} catch (IException e) {
			// TODO Auto-generated catch block
			throw new IException(e.getMessage(),e);
		}
		return result;
	}
}
