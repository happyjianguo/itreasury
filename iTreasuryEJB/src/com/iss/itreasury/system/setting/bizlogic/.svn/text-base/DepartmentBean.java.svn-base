/*
 * Created on 2004-7-5
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.system.setting.bizlogic;

import java.util.Collection;
import java.util.Iterator;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.system.setting.dao.DepartmentInfoDAO;
import com.iss.itreasury.system.setting.dataentity.DepartmentInfo;
import com.iss.itreasury.util.Constant;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class DepartmentBean
{
	/**
	 * 
	 */
	public DepartmentBean ( )
	{
		super ( ) ;
		// TODO Auto-generated constructor stub
	}
	public void addDepartment(DepartmentInfo di ) throws ITreasuryDAOException
	{
		DepartmentInfoDAO dao = new DepartmentInfoDAO();
		//
		Collection c = findByDepartmentCode(di.getDepartmentCode(),di.getOfficeID());
		if( c != null && c.size() > 0 )
		{
			throw new ITreasuryDAOException("部门编码重复，请重新录入。", null);
		}
		else
		{
			di.setId(dao.add(di));			
		}
	}
	public void updateDepartment(DepartmentInfo di) throws ITreasuryDAOException 
	{
	    Collection c = findByDepartmentCode(di.getDepartmentCode(),di.getOfficeID());
		if( c != null && c.size() > 0 )
		{
			Iterator it = c.iterator();
			while ( it.hasNext() )
			{
				DepartmentInfo obj = (DepartmentInfo)it.next();
				if( obj.getId() != di.getId())
				{
					throw new ITreasuryDAOException("部门编码重复，请重新录入。", null);
				}
			}
		}
		(new DepartmentInfoDAO()).update(di);
	}
	public long deleteDepartment(long Id) throws ITreasuryDAOException,Exception
	{
		DepartmentInfoDAO dao = new DepartmentInfoDAO();
		return dao.deleteByID(Id);		
	}
	
	public DepartmentInfo findDepartmentByID(long Id) throws ITreasuryDAOException
	{
		DepartmentInfo di = new DepartmentInfo();
		return (DepartmentInfo) (new DepartmentInfoDAO()).findByID(Id,di.getClass());
	}
	public Collection findAllDepartment() throws ITreasuryDAOException
	{
		DepartmentInfoDAO dao = new DepartmentInfoDAO();
		DepartmentInfo condition = new DepartmentInfo();
		//
		condition.setStatusID(Constant.RecordStatus.VALID);
		return dao.findByCondition(condition);
	}

	public Collection findAllDepartment(long lOfficeID) throws ITreasuryDAOException
	{
		DepartmentInfoDAO dao = new DepartmentInfoDAO();
		DepartmentInfo condition = new DepartmentInfo();
		//
		condition.setStatusID(Constant.RecordStatus.VALID);
		condition.setOfficeID( lOfficeID );
		return dao.findByCondition(condition);
	}

	public Collection findByDepartmentCode(String departmentCode,long officeid) throws ITreasuryDAOException
	{
		DepartmentInfoDAO dao = new DepartmentInfoDAO();
		DepartmentInfo condition = new DepartmentInfo();
		//
		condition.setDepartmentCode(departmentCode);
		condition.setStatusID(Constant.RecordStatus.VALID);
		condition.setOfficeID(officeid);
		return dao.findByCondition(condition);
	}
}
