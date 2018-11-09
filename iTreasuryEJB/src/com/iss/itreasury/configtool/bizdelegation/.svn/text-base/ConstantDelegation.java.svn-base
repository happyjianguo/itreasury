/*
 * Created on 2005-3-4
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.configtool.bizdelegation;

import java.util.Collection;

import com.iss.itreasury.configtool.constantmanage.bizlogic.*;
import com.iss.itreasury.configtool.constantmanage.dataentity.ConstantInfo;
import com.iss.itreasury.configtool.constantmanage.dataentity.ConstantManageInfo;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;

/**
 * @author hyzeng
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ConstantDelegation {
	
	private ConstantManageBiz biz = null;
	
	public ConstantDelegation()
	{
		biz = new ConstantManageBiz();
	}
	
	public ConstantInfo findConstantByID(long id) throws Exception
	{
		ConstantInfo info = null;
		try
		{
			info = biz.findConstantByID(id);
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
		
		return info;
	}
	
	public Collection findByCondition(ConstantInfo condition) throws Exception
	{
		Collection c = null;
		try
		{
			c = biz.findByCondition(condition);
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
	
	public long addToManage(ConstantInfo info) throws Exception
	{
		long id = -1;
		try
		{
			id = biz.addToManage(info);
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
		return id;
	}
	
	public Collection getConstantManageInfoByCondition(ConstantManageInfo condition) throws Exception
	{

		Collection c = null;
		try
		{
			c = biz.getConstantManageInfoByCondition(condition);
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
	
	public void saveConstantManageInfo(ConstantManageInfo[] condition) throws Exception {

		try
		{
			biz.saveConstantManageInfo(condition);
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
