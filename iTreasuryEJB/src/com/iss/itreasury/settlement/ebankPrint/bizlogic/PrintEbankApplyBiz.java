/*
 * Created on 2006-12-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.ebankPrint.bizlogic;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

import com.iss.itreasury.evoucher.base.VoucherException;
import com.iss.itreasury.evoucher.print.dataentity.QueryPrintConditionInfo;
import com.iss.itreasury.evoucher.printcontrol.dao.PrintApplyDao;
import com.iss.itreasury.evoucher.printcontrol.dataentity.PrintApplyInfo;
import com.iss.itreasury.settlement.ebankPrint.dao.PrintEbankApplyDao;
import com.iss.itreasury.settlement.ebankPrint.dataentity.PrintEbankApply;
import com.iss.itreasury.tags.database.Database;
import com.iss.system.dao.PageLoader;

/**
 * @author liangwang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PrintEbankApplyBiz 
{
	//查询所有申请打印新增的记录
	public Collection findAllprint() throws RemoteException
	{
		Collection coll = null;
		
		try
		{
			PrintEbankApplyDao printEbankApplyDao = new PrintEbankApplyDao();
			
			coll = printEbankApplyDao.findAllprint();
		}
		catch(Exception re)
		{
			throw new RemoteException(re.getMessage());
		}
		
		return coll;
	}

	public Collection findeBankPrint(long transID) throws RemoteException
	{
		Collection coll = null;
		
		try
		{
			PrintEbankApplyDao printEbankApplyDao = new PrintEbankApplyDao();
			
			coll = printEbankApplyDao.findeBankPrint(transID);
		}
		catch(Exception re)
		{
			throw new RemoteException(re.getMessage());
		}
		
		return coll;
	}
	
	public Collection findeBankPrintchoose(long transID) throws RemoteException
	{
		Collection coll = null;
		
		try
		{
			PrintEbankApplyDao printEbankApplyDao = new PrintEbankApplyDao();
			
			coll = printEbankApplyDao.findeBankPrintchoose(transID);
		}
		catch(Exception re)
		{
			throw new RemoteException(re.getMessage());
		}
		
		return coll;
	}
	
	public PageLoader findForApplyTransInfo() throws RemoteException
	{
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		PrintEbankApplyDao printEbankApplyDao = new PrintEbankApplyDao();
		
		try {
			pageLoader = printEbankApplyDao.findForApplyTransInfo();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException(e.getMessage());
		}
		return pageLoader;
	}
	
	//需要区分办事处
	public PageLoader findForApplyTransInfo(long officeID, long currencyID) throws RemoteException
	{
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		PrintEbankApplyDao printEbankApplyDao = new PrintEbankApplyDao();
		
		try {
			pageLoader = printEbankApplyDao.findForApplyTransInfo(officeID, currencyID);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException(e.getMessage());
		}
		return pageLoader;
	}
	
	//保存网银单据打印申请表
	public void saveEbankPrint(PrintEbankApply printEbankApply)throws RemoteException 
	{
		PrintEbankApplyDao printEbankApplyDao = null;
		Connection conn = null;
		
		try{
			
			printEbankApplyDao  = new PrintEbankApplyDao();
						 
			conn = Database.getConnection();
			
			conn.setAutoCommit(false); 
			
			printEbankApplyDao.saveEbankPrint(printEbankApply,conn);
			
			conn.commit();
			
		}
		catch(Exception re)
		{
			try 
			{
				conn.rollback();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}   
			
			throw new RemoteException(re.getMessage());
			
		}
		finally
		{
			if(conn != null)
			{
				try 
				{
					conn.close();
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
				conn = null;
			}
		}	
	}
	
	//更新网银单据打印申请表状态 add by zyyao 2008-8-3
	public void updateEbankPrintstatus(PrintEbankApply printEbankApply) throws RemoteException 
	{
		PrintEbankApplyDao printEbankApplyDao = null;
		Connection conn = null;
		
		try
		{
			printEbankApplyDao  = new PrintEbankApplyDao();
						 
			conn = Database.getConnection();
			
			conn.setAutoCommit(false); 
			
			printEbankApplyDao.updateEbankPrintstatus(printEbankApply,conn);
			
			conn.commit();
			
		}
		catch(Exception re)
		{
			try 
			{
				conn.rollback();
			} 
			catch(SQLException e) 
			{

				e.printStackTrace();
			}
			throw new RemoteException(re.getMessage());
		}
		finally
		{
			if(conn != null)
			{
				try 
				{
					conn.close();
				} 
				catch(SQLException e) 
				{
					e.printStackTrace();
				}
				conn = null;
			}
		}
	}
	
	//拒绝网银单据打印申请表
	public void refuseEbankPrint(PrintEbankApply printEbankApply) throws RemoteException 
	{
		PrintEbankApplyDao printEbankApplyDao = null;
		Connection conn = null;
		
		try
		{
			printEbankApplyDao  = new PrintEbankApplyDao();
		 
			conn = Database.getConnection();

			conn.setAutoCommit(false); 

			printEbankApplyDao.updateEbankPrint(printEbankApply,conn);

			conn.commit();

		}
		catch(Exception re)
		{
			try 
			{
				conn.rollback();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}   
			throw new RemoteException(re.getMessage());
		}
		finally
		{
			if(conn != null)
			{
				try 
				{
					conn.close();
				} 
				catch(SQLException e) 
				{
					e.printStackTrace();
				}
				conn = null;
			}
		}
	}
	
	//审批通过网银单据打印申请表
	public long passEbankPrint(PrintEbankApply printEbankApply) throws RemoteException 
	{
		PrintEbankApplyDao printEbankApplyDao = null;
		
		Connection conn = null;
		long printId = -1;
		try
		{
			printEbankApplyDao  = new PrintEbankApplyDao();
						 
			conn = Database.getConnection();
			
			conn.setAutoCommit(false); 
			
			//printEbankApplyDao.updateEbankPrint(printEbankApply,conn);
			
			PrintApplyInfo info = this.getNewPrintApplyInfo(printEbankApply);
			printId = printEbankApplyDao.savePrintApplayInfo(info, conn);
			
			printEbankApply.setNprintid(printId);
			
			printEbankApplyDao.updateEbankPrint(printEbankApply, conn);
			
			conn.commit();
		}
		catch(Exception re)
		{
			try 
			{
				conn.rollback();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}   
			throw new RemoteException(re.getMessage());
		}
		finally
		{
			if(conn != null)
			{
				try 
				{
					conn.close();
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
				conn = null;
			}
		}
		
		return printId;	
	}
	
	private PrintApplyInfo getNewPrintApplyInfo(PrintEbankApply printEbankApply) throws RemoteException
	{
		PrintApplyInfo info = new PrintApplyInfo();
		
		info.setNOfficeID(printEbankApply.getNofficeid());
		info.setNCurrencyID(printEbankApply.getNcurrency());
		info.setNTransID(printEbankApply.getNprintcontentid());
		info.setNTransNo(printEbankApply.getNprintcontentno());
		info.setNDeptID(printEbankApply.getNdeptid());
		info.setNBillID(printEbankApply.getNbillid());
		info.setNtempid(printEbankApply.getNtempid());
		info.setNstatusid(printEbankApply.getNstatusid());
		info.setIschapter(printEbankApply.getIschapter());
		info.setNclientid(printEbankApply.getNclientid());
		info.setNInputUserId(printEbankApply.getNinputuserid());
		info.setNInputDate(printEbankApply.getNinputdate());
		info.setLTransTypeID(printEbankApply.getNtypeid());
		info.setStrBillName(printEbankApply.getStempname());
		info.setModuleId(printEbankApply.getNmoduleid());
		//add by dwj 20110930 添加接收人(网银提交的补打审批由谁接收的)
		info.setNreceiveuserid(printEbankApply.getNreceiveuserid());
		//update by dwj 20110930
		return info;
	}
}