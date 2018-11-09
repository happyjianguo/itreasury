/*
 * Created on 2006-10-9
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.matureremind.bizlogic;

import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.settlement.matureremind.dao.MatureRemindDao;
import com.iss.itreasury.settlement.matureremind.dataentity.MatureRemindInfo;
import com.iss.itreasury.util.IException;

/**
 * @author lenovo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MatureRemindBiz {
	
	public Collection findAll(MatureRemindInfo info) throws Exception
	{
		Vector vret = null;
		
		try
		{			
			MatureRemindDao dao = new MatureRemindDao();
			vret = dao.findAll(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}

		return vret  ;
	}
	
	public long add(MatureRemindInfo info) throws Exception
	{
		long lret = -1;
		try
		{
			MatureRemindDao dao = new MatureRemindDao();
			lret = dao.add(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
	}
	
	public long update(MatureRemindInfo info) throws Exception
	{
		long lret = -1;
		try
		{
			MatureRemindDao dao = new MatureRemindDao();
			lret = dao.update(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
	}
	
	public long del(MatureRemindInfo info) throws Exception
	{
		long lret = -1;
		try
		{
			MatureRemindDao dao = new MatureRemindDao();
			lret = dao.del(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
	}

	public Collection findDate (MatureRemindInfo info) throws Exception
	{
		Vector vret = null;
		
		try
		{			
			MatureRemindDao dao = new MatureRemindDao();
			vret = dao.findDate(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}

		return vret  ;		
	}
}
