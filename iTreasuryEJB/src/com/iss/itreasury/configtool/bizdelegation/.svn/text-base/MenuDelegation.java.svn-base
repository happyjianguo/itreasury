/*
 * Created on 2005-3-3
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.configtool.bizdelegation;

import java.util.Collection;

import com.iss.itreasury.configtool.menumanage.bizlogic.*;
import com.iss.itreasury.configtool.menumanage.dataentity.PrivilegeInfo;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;

/**
 * @author hyzeng
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MenuDelegation {
	
	private MenuManageBiz biz = null;
	
	public MenuDelegation()
	{
		biz = new MenuManageBiz();
	}
	
	public Collection findPrivilegesbyModuleId(long moduleId) throws IException
	{
		Collection c = null;
		try
		{
			c = biz.findPrivilegesbyModuleId(moduleId);
		}
		catch(IException ie)
		{
			throw ie;
		}
		catch(Exception e)
		{
			Log.print(e.toString());
			throw new IException("Gen_E001");
		}
		
		return c;
	}
	
	public Collection findInvalidPrivilegesbyModuleId(long moduleId) throws IException
	{
		Collection c = null;
		try
		{
			c = biz.findInvalidPrivilegesbyModuleId(moduleId);
		}
		catch(IException ie)
		{
			throw ie;
		}
		catch(Exception e)
		{
			Log.print(e.toString());
			throw new IException("Gen_E001");
		}
		
		return c;
	}
	
	public void savePrivilegesbyModuleId(long moduleId,PrivilegeInfo[] pInfo) throws IException
	{
		try
		{
			biz.savePrivilegesbyModuleId(moduleId,pInfo);
		}
		catch(IException ie)
		{
			throw ie;
		}
		catch(Exception e)
		{
			Log.print(e.toString());
			throw new IException("Gen_E001");
		}
	}

}
