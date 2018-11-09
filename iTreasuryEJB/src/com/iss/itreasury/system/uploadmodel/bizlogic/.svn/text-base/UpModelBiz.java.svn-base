package com.iss.itreasury.system.uploadmodel.bizlogic;

import java.util.Collection;
import java.util.Vector;
import com.iss.itreasury.system.uploadmodel.dao.UpModelDao;
import com.iss.itreasury.system.uploadmodel.dataentity.UpModelInfo;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;

import java.lang.String;

public class UpModelBiz {
	
	public long add(UpModelInfo info) throws Exception
	{
		long lret = -1;
		boolean isRepeat = false;
		try
		{
			UpModelDao dao = new UpModelDao();
			isRepeat = dao.isNameRepeat(info);
			if(isRepeat)
			{
				throw new IException("文件标题不能重复！");
			}else{
				lret = dao.add(info);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return lret;
	}
	
	public long modify(UpModelInfo info) throws Exception
	{
		long lret = -1;
		boolean isRepeat = false;
		try
		{
			UpModelDao dao = new UpModelDao();
			isRepeat = dao.isNameRepeat(info);
			if(isRepeat)
			{
				throw new IException("文件标题不能重复！");
			}else{
				lret = dao.modify(info);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return lret;
	}
	
	public Collection findByCondition(UpModelInfo info) throws Exception
	{
		Vector vret = null;
		try
		{
			UpModelDao dao = new UpModelDao();
			vret = dao.findByCondition(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return vret;
	}
	
	
	public Collection findByUpModel(UpModelInfo info) throws Exception
	{
		Vector vret = null;
		try
		{
			UpModelDao dao = new UpModelDao();
			vret = dao.findByUpModel(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return vret;
	}
	
	public String findByUpModelString(UpModelInfo info)  throws Exception{
		String strBulletin = "";
		try
		{
			UpModelDao dao = new UpModelDao();
			strBulletin = dao.findByBulletinString(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return strBulletin;
	}

	public PageLoader findWithPage(UpModelInfo info) throws Exception
	{
		PageLoader loader = null;
		
		try
		{
			UpModelDao dao = new UpModelDao();
			loader = dao.findWithPage(info);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		System.out.print("执行成功");
		System.out.print(loader);
		return loader;
	}
}
