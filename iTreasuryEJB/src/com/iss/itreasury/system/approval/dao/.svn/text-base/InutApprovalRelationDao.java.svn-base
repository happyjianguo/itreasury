/*
 * Created on 2007-4-12
 *
 * Title:				iTreasury
 * @author             	刘琰 
 * Company:             iSoftStone
 * @version				iTreasury3.2新增
 * Description:         产品化3.2在结算,网银新增审批流,且审批流引擎采用北京研发中心的产品iNut,
 * 						为将iNut审批流与业务关联,新增关联设置功能        
 */
package com.iss.itreasury.system.approval.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.approval.dataentity.InutApprovalRelationInfo;
import com.iss.itreasury.util.IException;

public class InutApprovalRelationDao extends ITreasuryDAO implements java.io.Serializable
{	
	public InutApprovalRelationDao()
	{
		super("SYS_APPROVALRELATION");
	}

	public InutApprovalRelationDao(Connection con)
	{
		super("SYS_APPROVALRELATION",con);
	}
	
	public long findApprovalID(InutApprovalRelationInfo info) throws IException
	{
		long lReturn = -1;
		ResultSet localRS = null;
		String strSQL = "";
		try
		{
			initDAO();
			strSQL = "select * from SYS_APPROVALRELATION where 1=1";
			if(info.getId()>0)
			{
				strSQL = strSQL + " and id = "+ info.getId() ;
			}
			if(info.getOfficeID()>0)
			{
				strSQL = strSQL + " and officeid = " + info.getOfficeID() ;
			}
			if(info.getCurrencyID()>0)
			{
				strSQL = strSQL + " and currencyid = " + info.getCurrencyID();
			}
			if(info.getModuleID()>0)
			{
				strSQL = strSQL + " and moduleid = " + info.getModuleID();
			}
			
			//modify by leiyang transtypeid 为必查项
			//date 20081111
			/*if(info.getTransTypeID()>0)
			{
				strSQL = strSQL + " and transtypeid = " + info.getTransTypeID();
			}
			*/
			strSQL = strSQL + " and transtypeid = " + info.getTransTypeID();
			
			if(info.getActionID()>0)
			{
				strSQL = strSQL + " and actionid = " + info.getActionID();
			}

			if(info.getApprovalID()>0)
			{
				strSQL = strSQL + " and approvalid = " + info.getApprovalID();
			}			
			strSQL = strSQL + " order by id,transtypeid,actionid ";
			
			System.out.print("--------------------strSQL = "+strSQL);
			
			prepareStatement(strSQL.toString());
			localRS = executeQuery();
			if (localRS.next())
			{
				lReturn = localRS.getLong("APPROVALID");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001",e);
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch(ITreasuryDAOException e1)
			{
				e1.printStackTrace();
			}
		}
		return lReturn;
	}
	
	public Collection queryByCondition(InutApprovalRelationInfo info) throws IException
	{
		Vector v_Return = new Vector();
		ResultSet localRS = null;
		String strSQL = "";
		try
		{
			initDAO();
			strSQL = "select * from SYS_APPROVALRELATION where 1=1";
			if(info.getId()>0)
			{
				strSQL = strSQL + " and id = "+ info.getId() ;
			}
			if(info.getOfficeID()>0)
			{
				strSQL = strSQL + " and officeid = " + info.getOfficeID() ;
			}
			if(info.getCurrencyID()>0)
			{
				strSQL = strSQL + " and currencyid = " + info.getCurrencyID();
			}
			if(info.getModuleID()>0)
			{
				strSQL = strSQL + " and moduleid = " + info.getModuleID();
			}
			if(info.getTransTypeID()>0)
			{
				strSQL = strSQL + " and transtypeid = " + info.getTransTypeID();
			}
			if(info.getActionID()>0)
			{
				strSQL = strSQL + " and actionid = " + info.getActionID();
			}
			if(info.getApprovalID()>0)
			{
				strSQL = strSQL + " and approvalid = " + info.getApprovalID();
			}			
			strSQL = strSQL + " order by id,transtypeid,actionid ";
			System.out.println("-----------------strSQL---------------------"+strSQL.toString());
			prepareStatement(strSQL.toString());
			localRS = executeQuery();
			while (localRS.next())
			{
				InutApprovalRelationInfo tempInfo = new InutApprovalRelationInfo();
				tempInfo.setId(localRS.getLong("id"));
				tempInfo.setOfficeID(localRS.getLong("officeid"));
				tempInfo.setCurrencyID(localRS.getLong("currencyid"));
				tempInfo.setModuleID(localRS.getLong("moduleid"));
				tempInfo.setTransTypeID(localRS.getLong("transTypeID"));
				tempInfo.setActionID(localRS.getLong("actionID"));
				tempInfo.setApprovalID(localRS.getLong("approvalID"));
				v_Return.add(tempInfo);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001",e);
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch(ITreasuryDAOException e1)
			{
				e1.printStackTrace();
			}
		}
		
		return v_Return;
	}
	
	
	
	public Collection queryMyworkByCondition(InutApprovalRelationInfo info) throws IException
	{
		Vector v_Return = new Vector();
		ResultSet localRS = null;
		String strSQL = "";
		try
		{
			initDAO();
			strSQL = "select * from SYS_APPROVALRELATION where 1=1 and transtypeid not in(135,136)";
			if(info.getId()>0)
			{
				strSQL = strSQL + " and id = "+ info.getId() ;
			}
			if(info.getOfficeID()>0)
			{
				strSQL = strSQL + " and officeid = " + info.getOfficeID() ;
			}
			if(info.getCurrencyID()>0)
			{
				strSQL = strSQL + " and currencyid = " + info.getCurrencyID();
			}
			if(info.getModuleID()>0)
			{
				strSQL = strSQL + " and moduleid = " + info.getModuleID();
			}
			if(info.getTransTypeID()>0)
			{
				strSQL = strSQL + " and transtypeid = " + info.getTransTypeID();
			}
			if(info.getActionID()>0)
			{
				strSQL = strSQL + " and actionid = " + info.getActionID();
			}
			if(info.getApprovalID()>0)
			{
				strSQL = strSQL + " and approvalid = " + info.getApprovalID();
			}			
			strSQL = strSQL + " order by id,transtypeid,actionid ";
			System.out.println("-----------------strSQL---------------------"+strSQL.toString());
			prepareStatement(strSQL.toString());
			localRS = executeQuery();
			while (localRS.next())
			{
				InutApprovalRelationInfo tempInfo = new InutApprovalRelationInfo();
				tempInfo.setId(localRS.getLong("id"));
				tempInfo.setOfficeID(localRS.getLong("officeid"));
				tempInfo.setCurrencyID(localRS.getLong("currencyid"));
				tempInfo.setModuleID(localRS.getLong("moduleid"));
				tempInfo.setTransTypeID(localRS.getLong("transTypeID"));
				tempInfo.setActionID(localRS.getLong("actionID"));
				tempInfo.setApprovalID(localRS.getLong("approvalID"));
				v_Return.add(tempInfo);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001",e);
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch(ITreasuryDAOException e1)
			{
				e1.printStackTrace();
			}
		}
		
		return v_Return;
	}
	public Collection queryByAccountCondition(InutApprovalRelationInfo info) throws IException
	{
		Vector v_Return = new Vector();
		ResultSet localRS = null;
		String strSQL = "";
		try
		{
			initDAO();
			strSQL = "select * from SYS_APPROVALRELATION where 1=1";
			if(info.getId()>0)
			{
				strSQL = strSQL + " and id = "+ info.getId() ;
			}
			if(info.getOfficeID()>0)
			{
				strSQL = strSQL + " and officeid = " + info.getOfficeID() ;
			}
			if(info.getCurrencyID()>0)
			{
				strSQL = strSQL + " and currencyid = " + info.getCurrencyID();
			}
			if(info.getModuleID()>0)
			{
				strSQL = strSQL + " and moduleid = " + info.getModuleID();
			}
			if(info.getTransTypeID()>0)
			{
				strSQL = strSQL + " and transtypeid = " + info.getTransTypeID();
			}
			else
			{
				strSQL = strSQL + " and transtypeid in("+SETTConstant.TransactionType.ACCOUNTMODIFY+","+SETTConstant.TransactionType.ACCOUNTOPEN+")";
			}
			if(info.getActionID()>0)
			{
				strSQL = strSQL + " and actionid = " + info.getActionID();
			}
			if(info.getApprovalID()>0)
			{
				strSQL = strSQL + " and approvalid = " + info.getApprovalID();
			}			
			strSQL = strSQL + " order by id,transtypeid,actionid ";
			System.out.println("-----------------strSQL---------------------"+strSQL.toString());
			prepareStatement(strSQL.toString());
			localRS = executeQuery();
			while (localRS.next())
			{
				InutApprovalRelationInfo tempInfo = new InutApprovalRelationInfo();
				tempInfo.setId(localRS.getLong("id"));
				tempInfo.setOfficeID(localRS.getLong("officeid"));
				tempInfo.setCurrencyID(localRS.getLong("currencyid"));
				tempInfo.setModuleID(localRS.getLong("moduleid"));
				tempInfo.setTransTypeID(localRS.getLong("transTypeID"));
				tempInfo.setActionID(localRS.getLong("actionID"));
				tempInfo.setApprovalID(localRS.getLong("approvalID"));
				v_Return.add(tempInfo);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001",e);
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch(ITreasuryDAOException e1)
			{
				e1.printStackTrace();
			}
		}
		
		return v_Return;
	}
}
