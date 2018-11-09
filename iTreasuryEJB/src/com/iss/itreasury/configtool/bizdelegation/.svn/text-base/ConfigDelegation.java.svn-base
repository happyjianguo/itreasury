/*
 * Created on 2005-2-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.configtool.bizdelegation;

import java.util.Collection;

import com.iss.itreasury.configtool.configmanage.bizlogic.*;
import com.iss.itreasury.configtool.configmanage.dataentity.ConfigItemInfo;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;

/**
 * @author hyzeng
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ConfigDelegation {
	
	private ConfigManageBiz biz = null;
	
	public ConfigDelegation()
	{
		biz = new ConfigManageBiz();
	}
	
	public Collection findModuleList() throws Exception
	{
		Collection c = null;
		try
		{
			c = biz.findModuleList();
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
	
	public Collection findConfigListByFileName(String configFileName) throws Exception
	{
		Collection c = null;
		try
		{
			c = biz.findConfigListByFileName(configFileName);
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
	
	public void updateConfigItem(String configFileName,ConfigItemInfo cInfo) throws Exception
	{
		try
		{
			biz.updateConfigItem(configFileName,cInfo);
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
	
	public void addConfigItem(String configFileName,ConfigItemInfo cInfo) throws Exception
	{
		try
		{
			biz.addConfigItem(configFileName,cInfo);
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
	
	public void delConfigItem(String configFileName,ConfigItemInfo cInfo) throws Exception
	{
		try
		{
			biz.delConfigItem(configFileName,cInfo);
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
