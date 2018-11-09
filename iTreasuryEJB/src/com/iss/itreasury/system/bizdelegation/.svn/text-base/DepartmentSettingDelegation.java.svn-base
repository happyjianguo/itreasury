/*
 * Created on 2004-7-6
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.system.bizdelegation;

import java.util.Collection;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.system.setting.bizlogic.DepartmentBean;
import com.iss.itreasury.system.setting.dataentity.DepartmentInfo;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class DepartmentSettingDelegation
{
	/**
	 * 
	 */
	DepartmentBean  bean = null;
	public DepartmentSettingDelegation ( )
	{
		super ( ) ;
		// TODO Auto-generated constructor stub
		bean = new DepartmentBean();
	}
	public void addDepartment(DepartmentInfo di ) throws ITreasuryDAOException
	{
		bean.addDepartment(di);
	}
	public void updateDepartment(DepartmentInfo di) throws ITreasuryDAOException 
	{
		bean.updateDepartment(di);
	}
	public long deleteDepartment(long Id) throws ITreasuryDAOException,Exception
	{
		return bean.deleteDepartment(Id);
	}
	
	public DepartmentInfo findDepartmentByID(long Id) throws ITreasuryDAOException
	{
		return bean.findDepartmentByID(Id);
	}
	public Collection findAllDepartment() throws ITreasuryDAOException
	{
		return bean.findAllDepartment();
	}
	public Collection findAllDepartment(long lOfficeID) throws ITreasuryDAOException
	{
		return bean.findAllDepartment(lOfficeID);
	}

	public Collection findByDepartmentCode(String departmentCode,long officeID) throws ITreasuryDAOException
	{
		return bean.findByDepartmentCode(departmentCode,officeID);
	}
}
