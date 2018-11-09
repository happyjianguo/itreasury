package com.iss.itreasury.evoucher.printcontrol.bizlogic;

import java.sql.Connection;
import java.util.Collection;

import com.iss.itreasury.evoucher.base.VoucherBaseBean;
import com.iss.itreasury.evoucher.base.VoucherException;
import com.iss.itreasury.evoucher.print.dataentity.PrintXMLTimeInfo;
import com.iss.itreasury.evoucher.print.dataentity.QueryPrintConditionInfo;
import com.iss.itreasury.evoucher.printcontrol.dao.PrintApplyDao;
import com.iss.itreasury.evoucher.printcontrol.dataentity.PrintApplyInfo;
import com.iss.itreasury.evoucher.util.VOUConstant;
import com.iss.itreasury.util.Database;
import com.iss.system.dao.PageLoader;

public class PrintApplyBiz extends VoucherBaseBean implements java.io.Serializable
{

	public PageLoader findForApplyTransInfo (QueryPrintConditionInfo info) throws VoucherException
	{
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		PrintApplyDao dao = new PrintApplyDao();
		
		try 
		{
			//pageLoader = dao.findForApplyTransInfo(info);
			pageLoader = dao.findApplyTransInfo(info);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new VoucherException("Gen_E001", e);
		}
		return pageLoader;
	}
	
	//新增加特殊交易处理 boxu add 2007-9-6
	public Collection getPrintDetail(PrintXMLTimeInfo printInfo) throws VoucherException
	{
		Collection coll = null;
		PrintApplyDao dao = new PrintApplyDao();
		
		try 
		{
			coll = dao.getPrintDetail(printInfo);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new VoucherException("Gen_E001", e);
		}
		
		return coll;
	}
	
	public Collection getPrintDetailByTransID (long lTransID, long officeID, long currencyID) throws VoucherException
	{
		Collection coll = null;
		PrintApplyDao dao = new PrintApplyDao();
		
		try 
		{
			coll = dao.getPrintDetailByTransID(lTransID, officeID, currencyID);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new VoucherException("Gen_E001", e);
		}
		
		return coll;
	}
	public Collection getPrintDetailByTransID (long lTransID, long officeID, long currencyID,long ndeptid) throws VoucherException
	{
		Collection coll = null;
		PrintApplyDao dao = new PrintApplyDao();
		
		try  
		{
			coll = dao.getPrintDetailByTransID(lTransID, officeID, currencyID,ndeptid);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new VoucherException("Gen_E001", e);
		}
		
		return coll;
	}
	public long savePrintApplayInfo(PrintApplyInfo info) throws VoucherException
	{
		long lResult = -1;
		PrintApplyDao dao = new PrintApplyDao();
		
		try 
		{
			lResult = dao.savePrintApplayInfo(info);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new VoucherException("Gen_E001", e);
		}
		
		return lResult;
	}
	
	public long updateApplyStatus(String lID[]) throws Exception
	{
	    long lResult = -1L;
	    Connection conn = Database.getConnection();
		conn.setAutoCommit(false);
	    PrintApplyDao dao = new PrintApplyDao(conn);
	    try
	    {
	        for(int i = 0; i < lID.length; i++)
	        {
	            lResult = dao.updateApplyStatus(Long.parseLong(lID[i]), VOUConstant.VoucherStatus.APPROVALING);
	            lResult = dao.updateebankApplyStatus(Long.parseLong(lID[i]), VOUConstant.VoucherStatus.APPROVED);
	        }
	        conn.commit();
	    }
	    catch(Exception e)
	    {
	    	conn.rollback();
	        e.printStackTrace();
	        throw new VoucherException("Gen_E001", e);
	    }
	    finally
	    {
			conn.close();
		}
	    
	    return lResult;
	}
	
	public long updateApplyStatus(String lID[], long statusID) throws Exception
	{
	    long lResult = -1L;
	    Connection conn = Database.getConnection();
		conn.setAutoCommit(false);
	    PrintApplyDao dao = new PrintApplyDao(conn);
	    try
	    {
	        for(int i = 0; i < lID.length; i++)
	        {
	        	lResult = dao.updateApplyStatus(Long.parseLong(lID[i]), statusID);
	            lResult = dao.updateebankApplyStatus(Long.parseLong(lID[i]), statusID);
	        }
	        conn.commit();
	    }
	    catch(Exception e)
	    {
	    	conn.rollback();
	        e.printStackTrace();
	        throw new VoucherException("Gen_E001", e);
	    }
	    finally
	    {
			conn.close();
		}
	    
	    return lResult;
	}

	public Collection getPrintDetailByTransIDforsp(long lTransID, String ids) throws VoucherException
	 {
	     Collection coll = null;
	     PrintApplyDao dao = new PrintApplyDao();
	     try
	     {
	         coll = dao.getPrintDetailByTransIDforsp(lTransID, ids);
	     }
	     catch(Exception e)
	     {
	         e.printStackTrace();
	         throw new VoucherException("Gen_E001", e);
	     }
	     
	     return coll;
	 }

	 public PrintApplyInfo getPrintaplyByIDS(String ids) throws VoucherException
	 {
	     PrintApplyInfo printApplyInfo = null;
	     PrintApplyDao dao = new PrintApplyDao();
	     try
	     {
	         printApplyInfo = dao.getPrintaplyByIDS(ids);
	     }
	     catch(Exception e)
	     {
	         e.printStackTrace();
	         throw new VoucherException("Gen_E001", e);
	     }
	     return printApplyInfo;
	 }
}
