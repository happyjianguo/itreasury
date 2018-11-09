package com.iss.itreasury.managerquery.report.bizlogic;

import java.util.Collection;

import com.iss.itreasury.managerquery.report.dao.ReportDao;
import com.iss.itreasury.managerquery.report.dataentity.ReportCondition;
import com.iss.itreasury.util.IException;

public class ReportBiz {

	public Collection queryRptShow(ReportCondition condition) throws IException
	{
		Collection result = null;
		ReportDao dao = new ReportDao();
		try {
			result = dao.queryRptShow(condition);
		} catch (IException e) {
			// TODO Auto-generated catch block
			throw new IException(e.getMessage(),e);
		}
		return result;
	}
	
	public void tabRpt(Collection coll) throws IException
	{
		ReportDao dao = new ReportDao();
		try {
			dao.tabRpt(coll);
		} catch (IException e) {
			// TODO Auto-generated catch block
			throw new IException(e.getMessage(),e);
		}
	}
	
	public void setDefault(Collection coll) throws IException
	{
		ReportDao dao = new ReportDao();
		try {
			dao.setDefault(coll);
		} catch (IException e) {
			// TODO Auto-generated catch block
			throw new IException(e.getMessage(),e);
		}
	}
	
	public void saveRpt(Collection coll) throws IException
	{
		ReportDao dao = new ReportDao();
		try {
			dao.saveRpt(coll);
		} catch (IException e) {
			// TODO Auto-generated catch block
			throw new IException(e.getMessage(),e);
		}
	}
	
	public Collection backRptShow(Collection coll) throws IException
	{
		Collection result = null;
		ReportDao dao = new ReportDao();
		try {
			result = dao.backRptShow(coll);
		} catch (IException e) {
			// TODO Auto-generated catch block
			throw new IException(e.getMessage(),e);
		}
		return result;
	}
	
}
