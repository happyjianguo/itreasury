/*
 * Created on 2005-3-3
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.configtool.menumanage.bizlogic;

import java.util.Collection;

import com.iss.itreasury.util.IException;
import com.iss.itreasury.configtool.menumanage.dao.*;
import com.iss.itreasury.configtool.menumanage.dataentity.PrivilegeInfo;
import com.iss.itreasury.dao.ITreasuryDAOException;

/**
 * @author hyzeng
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MenuManageBiz {
	
	public Collection findPrivilegesbyModuleId(long moduleId) throws IException
	{
		Collection c = null;
		
		try
		{
			MenuManageDao dao = new MenuManageDao();
			c = dao.findPrivilegesbyModuleId(moduleId);			
		}
		catch (Exception e)
        {
            e.printStackTrace();
        }
		
		return c;
	}
	
	public Collection findInvalidPrivilegesbyModuleId(long moduleId) throws IException
	{
		Collection c = null;
		
		try
		{
			MenuManageDao dao = new MenuManageDao();
			c = dao.findInvalidPrivilegesbyModuleId(moduleId);			
		}
		catch (Exception e)
        {
            e.printStackTrace();
        }
		
		return c;
	}
	/**
     * 根据模块ID办事处ID查询所有有效权限
     * 
     * @param moduleId,
     * @return @throws
     *         SQLException
     * @throws ITreasuryDAOException
     */
	public Collection findPrivilegesbyModuleIdOfficeId(long moduleId,long OfficeId) throws IException
	{
		Collection c = null;
		
		try
		{
			MenuManageDao dao = new MenuManageDao();
			c = dao.findPrivilegesbyModuleIdOfficeId(moduleId,OfficeId);			
		}
		catch (Exception e)
        {
            e.printStackTrace();
        }
		
		return c;
	}
	 /**
     * 根据模块ID办事处ID查询所有权限
     * 
     * @param moduleId,
     * @return @throws
     *         SQLException
     * @throws ITreasuryDAOException
     */
	public Collection findAllPrivilegesbyModuleIdOfficeId(long moduleId,long OfficeId) throws IException
	{
		Collection c = null;
		
		try
		{
			MenuManageDao dao = new MenuManageDao();
			c = dao.findAllPrivilegesbyModuleIdOfficeId(moduleId,OfficeId);			
		}
		catch (Exception e)
        {
            e.printStackTrace();
        }
		
		return c;
	}
	public void savePrivilegesbyModuleId(long moduleId,PrivilegeInfo[] pInfo) throws IException
	{
		if (pInfo == null)
		{
			return;
		}
		
		try
		{
			for (int i=0;i<pInfo.length;i++)
			{
				if (pInfo[i]!=null)
				{
					MenuManageDao dao = new MenuManageDao();
					dao.update(pInfo[i]);
				}
			}
		}
		catch (Exception e)
        {
            e.printStackTrace();
        }
		
	}

}
