/*
 * Created on 2007-4-12
 *
 * Title:				iTreasury
 * @author             	刘琰 
 * Company:             iSoftStone
 * @version				iTreasury3.2新增
 * Description:         产品化3.2在结算,网银新增审批流,且审批流引擎采用北京研发中心的产品iNut,
 * 						审批流每审批一笔业务，在该表内新增一条记录，记录业务id与审批流实例id对应关系       
 */
package com.iss.itreasury.system.approval.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.system.approval.dataentity.InutApprovalRecordInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;

public class InutApprovalRecordDao extends ITreasuryDAO implements java.io.Serializable
{	
	public InutApprovalRecordDao()
	{
		super("SYS_APPROVALRECORD");
	}

	public InutApprovalRecordDao(Connection con)
	{
		super("SYS_APPROVALRECORD",con);
	}
	
	public Collection queryByCondition(InutApprovalRecordInfo info) throws IException
	{
		Vector v_Return = new Vector();
		ResultSet localRS = null;
		String strSQL = "";
		try
		{
			initDAO();
			strSQL = "select * from SYS_APPROVALRECORD where 1=1";
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
			if(info.getTransID()!=null && info.getTransID().length()>0)
			{
				strSQL = strSQL + " and transid = '" + info.getTransID() + "'";
			}
			if(info.getApprovalEntryID()>0)
			{
				strSQL = strSQL + " and approvalentryid = " + info.getApprovalEntryID();
			}
			if(info.getLinkUrl()!=null && info.getLinkUrl().length()>0)
			{
				strSQL = strSQL + " and linkurl like '" + info.getLinkUrl()+"'";
			}
			if(info.getLastAppUserID()>0)
			{
				strSQL = strSQL + " and lastappuserid = " + info.getLastAppUserID();
			}
			if(info.getStatusID()>=0)
			{
				strSQL = strSQL + " and statusid = " + info.getStatusID();
			}
			strSQL = strSQL + " order by id,transtypeid,actionid ";
			
			prepareStatement(strSQL.toString());
			localRS = executeQuery();
			while (localRS.next())
			{
				InutApprovalRecordInfo tempInfo = new InutApprovalRecordInfo();
				tempInfo.setId(localRS.getLong("id"));
				tempInfo.setOfficeID(localRS.getLong("officeid"));
				tempInfo.setCurrencyID(localRS.getLong("currencyid"));
				tempInfo.setModuleID(localRS.getLong("moduleid"));
				tempInfo.setTransTypeID(localRS.getLong("transTypeID"));
				tempInfo.setActionID(localRS.getLong("actionID"));
				tempInfo.setTransID(localRS.getString("transid"));
				tempInfo.setApprovalEntryID(localRS.getLong("approvalentryid"));
				tempInfo.setLastAppUserID(localRS.getLong("lastappuserid"));
				tempInfo.setStatusID(localRS.getLong("statusid"));
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
	/**
	 * 
	 * 查询未审批的记录  SYS_APPROVALRECORD表里有个最后一级审批人
	 *	只有最后一级审批人为空或小于0且状态为有效，则是审批中的
	 */
	
	public Collection queryNotApprovedByCondition(InutApprovalRecordInfo info) throws IException
	{
		Vector v_Return = new Vector();
		ResultSet localRS = null;
		String strSQL = "";
		try
		{
			initDAO();
			strSQL = "select * from SYS_APPROVALRECORD where lastappuserid < 0 or lastappuserid is null ";
			strSQL = strSQL + " and statusid = " + Constant.RecordStatus.VALID;
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
			if(info.getTransID()!=null && info.getTransID().length()>0)
			{
				strSQL = strSQL + " and transid = '" + info.getTransID() + "'";
			}
			if(info.getApprovalEntryID()>0)
			{
				strSQL = strSQL + " and approvalentryid = " + info.getApprovalEntryID();
			}
			if(info.getLinkUrl()!=null && info.getLinkUrl().length()>0)
			{
				strSQL = strSQL + " and linkurl like '" + info.getLinkUrl()+"'";
			}
//			if(info.getLastAppUserID()>0)
//			{
//				strSQL = strSQL + " and lastappuserid = " + info.getLastAppUserID();
//			}
			if(info.getStatusID()>=0)
			{
				strSQL = strSQL + " and statusid = " + info.getStatusID();
			}
			strSQL = strSQL + " order by id,transtypeid,actionid ";
			
			prepareStatement(strSQL.toString());
			localRS = executeQuery();
			while (localRS.next())
			{
				InutApprovalRecordInfo tempInfo = new InutApprovalRecordInfo();
				tempInfo.setId(localRS.getLong("id"));
				tempInfo.setOfficeID(localRS.getLong("officeid"));
				tempInfo.setCurrencyID(localRS.getLong("currencyid"));
				tempInfo.setModuleID(localRS.getLong("moduleid"));
				tempInfo.setTransTypeID(localRS.getLong("transTypeID"));
				tempInfo.setActionID(localRS.getLong("actionID"));
				tempInfo.setTransID(localRS.getString("transid"));
				tempInfo.setApprovalEntryID(localRS.getLong("approvalentryid"));
				tempInfo.setLastAppUserID(localRS.getLong("lastappuserid"));
				tempInfo.setStatusID(localRS.getLong("statusid"));
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
	
	public long updateByApprovalEntryID(InutApprovalRecordInfo info) throws IException
	{
		long lReturn = -1;
		ResultSet localRS = null;
		String strSQL = "";
		try
		{
			if(info.getApprovalEntryID()>0 && (info.getStatusID()>=0 || info.getLastAppUserID()>0))
			{
				initDAO();				
				strSQL = "update SYS_APPROVALRECORD set ";
				if(info.getStatusID()>=0)
				{
					strSQL += " STATUSID = "+info.getStatusID()+",";
				}
				if(info.getLastAppUserID()>0)
				{
					strSQL += " LASTAPPUSERID = "+info.getLastAppUserID() + ",";
				}
				strSQL = strSQL.substring(0, strSQL.length()-1);//去掉最后一个,
				strSQL += " where APPROVALENTRYID = "+info.getApprovalEntryID();
				
				System.out.print("==========sql = "+ strSQL);
				prepareStatement(strSQL.toString());
				this.executeUpdate();
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
}
