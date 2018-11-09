/*
 * Created on 2006-10-16
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.loan.capitalprove.bizlogic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.loan.capitalprove.dao.CapitalProveDao;
import com.iss.itreasury.loan.capitalprove.dataentity.CapitalProveInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

/**
 * @author yyhe
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CapitalProveBiz {
	
	private Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
	
	//  新增资信证明，将info里的信息insert到表loan_CapitalProve里
	public long add(CapitalProveInfo info) throws IException
	{
		long lret = -1;
		try
		{
			CapitalProveDao dao = new CapitalProveDao();
			lret = dao.add(info);
		}
		catch(Exception e)
		{
			log4j.error(e.toString());
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
	}
	
	//	修改资信证明，将info里的信息update到表loan_CapitalProve里
	public long modify(CapitalProveInfo info) throws IException
	{
		long lret = -1;
		try
		{
			CapitalProveDao dao = new CapitalProveDao();
			lret = dao.modify(info);
		}
		catch(Exception e)
		{
			log4j.error(e.toString());
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
	}
	
	//	删除资信证明
	public long delete(long id) throws IException
	{
		long lret = -1;
		try
		{
			CapitalProveDao dao = new CapitalProveDao();
			lret = dao.delete(id);
		}
		catch(Exception e)
		{
			log4j.error(e.toString());
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
	}
	
	//根据ID查询资信证明
	public CapitalProveInfo findByID(long id) throws IException{
		CapitalProveInfo cInfo = null;
		try{
			CapitalProveDao dao = new CapitalProveDao();
			cInfo = dao.findByID(id);
		}catch(Exception e){
			log4j.error(e.toString());
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return cInfo;
	}
	
	//获得所有的资信证明
//	public Collection findAllCapitalProve() throws IException{
//		Collection cret = null;
//		try{
//			CapitalProveDao dao = new CapitalProveDao();
//			cret = dao.findAllCapitalProve();
//		}catch(Exception e){
//			log4j.error(e.toString());
//			e.printStackTrace();
//			throw new IException("Gen_E001");
//		}
//		return cret;
//	}
	
	//	查询资信证明，按info里的信息查询表loan_CapitalProve
	//	返回由CreditProveInfo组成的集合
//	public Collection findByCondition(CapitalProveInfo info) throws IException
//	{
//		Collection vret = null;
//		try
//		{
//			CapitalProveDao dao = new CapitalProveDao();
//			vret = dao.findByCapitalProve(info);
//		}
//		catch(Exception e)
//		{
//			log4j.error(e.toString());
//			e.printStackTrace();
//			throw new IException("Gen_E001");
//		}
//		return vret;
//	}
	
	public PageLoader findWithPage(CapitalProveInfo info) throws IException{
		
		PageLoader loader = null;
		
		try
		{
			CapitalProveDao dao = new CapitalProveDao();
			loader = dao.findWithPage(info);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return loader;
	}
	
	public PageLoader findAllCapitalProveInfo(long officeId) throws IException{
		
		PageLoader loader = null;
		
		try
		{
			CapitalProveDao dao = new CapitalProveDao();
			loader = dao.findAllCapitalProveInfo(officeId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return loader;
	}
	
	public String getNextCode() throws IException{
		String prefix = "BJCP";
		String newCode = null;
		try{
			CapitalProveDao dao = new CapitalProveDao();
			newCode = dao.getNextCode(prefix);
		}catch(Exception e){
			log4j.error(e.toString());
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return newCode;
	}
	

}
