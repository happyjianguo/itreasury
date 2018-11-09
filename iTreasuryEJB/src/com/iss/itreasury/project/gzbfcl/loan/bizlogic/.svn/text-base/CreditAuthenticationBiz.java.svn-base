package com.iss.itreasury.project.gzbfcl.loan.bizlogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.project.gzbfcl.loan.dataentity.CreditAuthenticationInfo;
import com.iss.itreasury.project.gzbfcl.loan.dataentity.CreditAuthenticationQueryInfo;
import com.iss.itreasury.project.gzbfcl.loan.dao.CreditAuthenticationDao;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.util.DataFormat;

/**
 * 
 * @author sunjing
 *
 */
public class CreditAuthenticationBiz 
{
	private static CreditAuthenticationBiz save = null;
	public static CreditAuthenticationBiz getInstance()
	{
		if(save ==null)
		{
			return new CreditAuthenticationBiz();
		}
		return save;
	}
	public synchronized long saveCredibility(CreditAuthenticationInfo info) throws IException
	{
		long lID = -1;
		
		try
		{

			CreditAuthenticationDao dao = new CreditAuthenticationDao("LOAN_CREDITAUTHENTICATION");
			if( info.getId() < 0 )
			{
				String systemYear = DataFormat.getDateString(info.getInputTime()).substring(0, 4);
				String code = TransCodeMaker.getIntance().getTransCode(systemYear,info);
				info.setTransNo(code);
				lID = dao.add(info);
			}
			else
			{
				dao.update(info);
				lID = info.getId();
			}
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lID;
	}
	
	public CreditAuthenticationInfo findByID(long lID) throws IException
	{
		CreditAuthenticationInfo ci = null;
		try
		{
			CreditAuthenticationDao dao = new CreditAuthenticationDao("LOAN_CREDITAUTHENTICATION");
			ci = (CreditAuthenticationInfo)dao.findByID(lID, CreditAuthenticationInfo.class);
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return ci != null ? ci : null;
	}	
	
	public void deleteCredibility(long lID) throws IException
	{
		try
		{
			CreditAuthenticationDao dao = new CreditAuthenticationDao("LOAN_CREDITAUTHENTICATION");
			 dao.updateStatus(lID, 2);
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
	}
	
	public double queryAmountSum(CreditAuthenticationQueryInfo info) throws Exception
	{
		double sumAmount = 0;
		try
		{
			CreditAuthenticationDao dao = new CreditAuthenticationDao("LOAN_CREDITAUTHENTICATION");
			sumAmount = dao.queryAmountSum(info);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return sumAmount;
	}
	
	public PageLoader queryInfos(CreditAuthenticationQueryInfo qInfo) throws IException
	{
		PageLoader pageLoader= null;
		String sql = "";
		try 
		{
			CreditAuthenticationDao dao = new CreditAuthenticationDao("LOAN_CREDITAUTHENTICATION");
			sql = dao.getQueryInfo(qInfo);
			pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
			pageLoader.initPageLoader(new AppContext(), sql, "*", "", (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.project.gzbfcl.loan.dataentity.CreditAuthenticationInfo", null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return pageLoader;
	}
	
	
	
}
