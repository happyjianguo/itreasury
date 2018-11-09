package com.iss.itreasury.audit.datamaintenance.bizlogic;

import java.util.Collection;

import com.iss.itreasury.audit.datamaintenance.dao.DatamaintenanceDao;
import com.iss.itreasury.audit.datamaintenance.dataentity.DatamaintenanceCondition;
import com.iss.itreasury.util.IException;

public class DatamaintenanceBiz {

	public Collection queryBasic(DatamaintenanceCondition condition) throws IException
	{
		Collection result = null;
		DatamaintenanceDao dao = new DatamaintenanceDao();
		try {
			result = dao.queryBasic(condition);
		} catch (IException e) {
			// TODO Auto-generated catch block
			throw new IException(e.getMessage(),e);
		}
		return result;
	}
	
	public void saveBasic(DatamaintenanceCondition condition) throws IException
	{
		DatamaintenanceDao dao = new DatamaintenanceDao();
		try {
			dao.saveBasic(condition);
		} catch (IException e) {
			// TODO Auto-generated catch block
			throw new IException(e.getMessage(),e);
		}
	}
	
	public Collection queryRptShow(DatamaintenanceCondition condition) throws IException
	{
		Collection result = null;
		DatamaintenanceDao dao = new DatamaintenanceDao();
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
		DatamaintenanceDao dao = new DatamaintenanceDao();
		try {
			dao.tabRpt(coll);
		} catch (IException e) {
			// TODO Auto-generated catch block
			throw new IException(e.getMessage(),e);
		}
	}
	
	public void setDefault(Collection coll) throws IException
	{
		DatamaintenanceDao dao = new DatamaintenanceDao();
		try {
			dao.setDefault(coll);
		} catch (IException e) {
			// TODO Auto-generated catch block
			throw new IException(e.getMessage(),e);
		}
	}
	
	public void saveRpt(Collection coll) throws IException
	{
		DatamaintenanceDao dao = new DatamaintenanceDao();
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
		DatamaintenanceDao dao = new DatamaintenanceDao();
		try {
			result = dao.backRptShow(coll);
		} catch (IException e) {
			// TODO Auto-generated catch block
			throw new IException(e.getMessage(),e);
		}
		return result;
	}
	
}
