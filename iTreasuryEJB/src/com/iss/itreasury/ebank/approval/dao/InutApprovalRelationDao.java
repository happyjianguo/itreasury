/*
 * Created on 2007-4-16
 *
 * Title:				iTreasury
 * @author             	ypxu
 * Company:             iSoftStone
 * @version				iTreasury3.2新增
 * Description:         产品化3.2在结算,网银新增审批流,且审批流引擎采用北京研发中心的产品iNut,
 * 						为将iNut审批流与业务关联,新增关联设置功能        
 */
package com.iss.itreasury.ebank.approval.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.ebank.approval.dataentity.InutApprovalRecordInfo;
import com.iss.itreasury.ebank.approval.dataentity.InutApprovalRelationInfo;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.util.IException;

public class InutApprovalRelationDao extends ITreasuryDAO implements java.io.Serializable
{	
	public InutApprovalRelationDao()
	{
		super("OB_APPROVALRELATIONNEW");
	}

	public InutApprovalRelationDao(Connection con)
	{
		super("OB_APPROVALRELATIONNEW",con);
	}
	
	/*
	 * add by ypxu
	 * 2007-04-21
	 * 查询审批流ID
	 */
	public long findApprovalID(InutApprovalRelationInfo info) throws IException
	{
		long lReturn = -1;
		ResultSet localRS = null;
		String strSQL = "";
		try
		{
			initDAO();
			strSQL = "select * from OB_APPROVALRELATIONNEW where 1=1";
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
			if(info.getClientID() > 0)
			{
				strSQL = strSQL + " and clientid =" + info.getClientID();
			}
			if(info.getModuleID()>0)
			{
				strSQL = strSQL + " and moduleid = " + info.getModuleID();
			}
			if(info.getTransTypeID()>0)
			{
				strSQL = strSQL + " and transtypeid = " + info.getTransTypeID();
			}
//			if(info.getActionID()>0)
//			{
//				strSQL = strSQL + " and actionid = " + info.getActionID();
//			}
			if(info.getApprovalID()>0)
			{
				strSQL = strSQL + " and approvalid = " + info.getApprovalID();
			}
			if(info.getIslowerunit()==OBConstant.IsLowerun.ISNO)
			{
				strSQL = strSQL + " and islowerunit = " + info.getIslowerunit();
			}
			if(info.getIslowerunit()==OBConstant.IsLowerun.ISYES)
			{
				strSQL = strSQL + " and islowerunit = " + info.getIslowerunit();
			}
			strSQL = strSQL + " order by id,transtypeid ";
			
			System.out.print("-------------- sql = "+strSQL);
			
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
	
	
	/*
	 * add by zcwang
	 * 2007-04-21
	 * 查询审批流对象
	 */
	public InutApprovalRelationInfo findApprovalInfo(InutApprovalRelationInfo info) throws IException
	{
		long lReturn = -1;
		ResultSet localRS = null;
		String strSQL = "";
		InutApprovalRelationInfo tempInfo = null;
		try
		{
			initDAO();
			strSQL = "select * from OB_APPROVALRELATIONNEW where 1=1";
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
			if(info.getClientID() > 0)
			{
				strSQL = strSQL + " and clientid =" + info.getClientID();
			}
			if(info.getModuleID()>0)
			{
				strSQL = strSQL + " and moduleid = " + info.getModuleID();
			}
			if(info.getTransTypeID()>0)
			{
				strSQL = strSQL + " and transtypeid = " + info.getTransTypeID();
			}
//			if(info.getActionID()>0)
//			{
//				strSQL = strSQL + " and actionid = " + info.getActionID();
//			}
			if(info.getApprovalID()>0)
			{
				strSQL = strSQL + " and approvalid = " + info.getApprovalID();
			}
			if(info.getIslowerunit()>0)
			{
				strSQL = strSQL + " and islowerunit = " + info.getIslowerunit();
			}

			strSQL = strSQL + " order by id,transtypeid ";
			
			prepareStatement(strSQL.toString());
			localRS = executeQuery();
			
			if (localRS.next())
			{
				 tempInfo = new InutApprovalRelationInfo();
				//tempInfo.setId(localRS.getLong("ID"));
				tempInfo.setOfficeID(localRS.getLong("officeID"));
				tempInfo.setCurrencyID(localRS.getLong("currencyID"));
				tempInfo.setClientID(localRS.getLong("clientID"));
				tempInfo.setModuleID(localRS.getLong("moduleID"));
				tempInfo.setTransTypeID(localRS.getLong("transTypeID"));
				//tempInfo.setActionID(localRS.getLong("actionID"));
				tempInfo.setApprovalID(localRS.getLong("approvalID"));
				//tempInfo.setIslowerunit(localRS.getLong("islowerunit"));
				tempInfo.setIssendtoupclient(localRS.getLong("ISSENDTOUPCLIENT"));
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
		return tempInfo;
	}
	
	/*
	 * add by ypxu
	 * 2007-04-21
	 * 通过approvalID得到INFO
	 */
	public String findNameByApprovalID(long lApprovalID) throws IException
	{
		ResultSet localRS = null;
		String approvalName = "";
		String strSQL = "";
		try
		{
			initDAO();
			strSQL = "select a.name from os_ob_wfdefine a where ID =" + lApprovalID;		
			prepareStatement(strSQL.toString());
			localRS = executeQuery();
			while (localRS.next())
			{				
				approvalName = localRS.getString("name");
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
		return approvalName;
	}
	
	
	public Collection queryByCondition(InutApprovalRelationInfo info) throws IException
	{
		Vector v_Return = new Vector();
		ResultSet localRS = null;
		StringBuffer strSQL = new StringBuffer();
		try
		{
			initDAO();
			strSQL.append("select distinct officeID,currencyID,clientID,moduleID,transTypeID,approvalID  from OB_APPROVALRELATIONNEW");
			strSQL.append(" where currencyID = " + info.getCurrencyID());
			strSQL.append(" and clientID = " + info.getClientID());
			strSQL.append(" and moduleID = " + info.getModuleID());
			strSQL.append(" order by transtypeID");
			
			prepareStatement(strSQL.toString());
			localRS = executeQuery();
			while (localRS.next())
			{
				InutApprovalRelationInfo tempInfo = new InutApprovalRelationInfo();
				//tempInfo.setId(localRS.getLong("ID"));
				tempInfo.setOfficeID(localRS.getLong("officeID"));
				tempInfo.setCurrencyID(localRS.getLong("currencyID"));
				tempInfo.setClientID(localRS.getLong("clientID"));
				tempInfo.setModuleID(localRS.getLong("moduleID"));
				tempInfo.setTransTypeID(localRS.getLong("transTypeID"));
//				tempInfo.setActionID(localRS.getLong("actionID"));
				tempInfo.setApprovalID(localRS.getLong("approvalID"));
				//tempInfo.setIslowerunit(localRS.getLong("islowerunit"));
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

	public Collection queryByConditions(InutApprovalRelationInfo info) throws IException
	{
		Vector v_Return = new Vector();
		ResultSet localRS = null;
		StringBuffer strSQL = new StringBuffer();
		try
		{
			initDAO();
			strSQL.append("select id,officeID,currencyID,clientID,moduleID,transTypeID,approvalID,ISSENDTOUPCLIENT from OB_APPROVALRELATIONNEW");
			strSQL.append(" where currencyID = " + info.getCurrencyID());
			strSQL.append(" and clientID = " + info.getClientID());
			strSQL.append(" and moduleID = " + info.getModuleID());
			strSQL.append(" and ISLOWERUNIT = " + info.getIslowerunit());
			strSQL.append(" order by transtypeID");
			
			prepareStatement(strSQL.toString());
			localRS = executeQuery();
			while (localRS.next())
			{
				InutApprovalRelationInfo tempInfo = new InutApprovalRelationInfo();
				tempInfo.setId(localRS.getLong("ID"));
				tempInfo.setOfficeID(localRS.getLong("officeID"));
				tempInfo.setCurrencyID(localRS.getLong("currencyID"));
				tempInfo.setClientID(localRS.getLong("clientID"));
				tempInfo.setModuleID(localRS.getLong("moduleID"));
				tempInfo.setTransTypeID(localRS.getLong("transTypeID"));
//				tempInfo.setActionID(localRS.getLong("actionID"));
				tempInfo.setApprovalID(localRS.getLong("approvalID"));
				//tempInfo.setIslowerunit(localRS.getLong("islowerunit"));
				tempInfo.setIssendtoupclient(localRS.getLong("ISSENDTOUPCLIENT"));
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
	 * Create by leiyang date 2007/08/06
	 * 
	 * @param id
	 * @return
	 * @throws IException 
	 */
	public InutApprovalRelationInfo getApprovalRelationInfoById(long id) throws IException{
		InutApprovalRelationInfo info = null;
		ResultSet localRS = null;
		StringBuffer strSQL = new StringBuffer();
		try
		{
			initDAO();
			strSQL.append("select * from OB_APPROVALRELATIONNEW");
			strSQL.append(" where id = " + id);
			
			prepareStatement(strSQL.toString());
			localRS = executeQuery();
			while (localRS.next())
			{
				info = new InutApprovalRelationInfo();
				info.setId(localRS.getLong("ID"));
				info.setOfficeID(localRS.getLong("officeID"));
				info.setCurrencyID(localRS.getLong("currencyID"));
				info.setClientID(localRS.getLong("clientID"));
				info.setModuleID(localRS.getLong("moduleID"));
				info.setTransTypeID(localRS.getLong("transTypeID"));
				info.setActionID(localRS.getLong("actionID"));
				info.setApprovalID(localRS.getLong("approvalID"));
				info.setIslowerunit(localRS.getLong("islowerunit"));
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
		return info;
	}

	public long getParentId(long clientID) {
		
		//Connection conn = null;
		//PreparedStatement ps = null;
		ResultSet rs = null;
		//StringBuffer strSQL = null;
		String strSQL = "";
		long  parentId = -1;
		
		try 
		{
			initDAO();
				
			strSQL = " select nParentCorpID1  from client  where  id=" + clientID;
			//conn = Database.getConnection();
			prepareStatement(strSQL.toString());
			rs = executeQuery();
			
			if (rs.next())
			{
				parentId = rs.getLong("nParentCorpID1");
			}		
	
		}
        catch (Exception ex)
        {

            ex.getMessage();
        }
        finally
        {
            try
            {
//            	if(rs != null){
//            		
//            		rs.close();
//            		rs = null;
//            	}
//                
//                if (ps != null)
//                {
//                    ps.close();
//                    ps = null;
//                }
//                
//                if(conn != null){
//                	
//                	conn.close();
//                	conn = null;
//                }
            	
            	finalizeDAO();
                
            }
            catch (Exception ex)
            {

            	 ex.getMessage();
            }
        }
		return parentId;
	}

	public void updateApprovalRecord(InutApprovalRecordInfo info) throws IException {
		
		{
			long lReturn = -1;
			ResultSet localRS = null;
			String strSQL = "";
			try
			{
				if(info.getApprovalEntryID()>0 && (info.getStatusID()>=0 || info.getLastAppUserID()>0 || info.getNextLevel()>0))
				{
					initDAO();				
					strSQL = "update OB_APPROVALRECORD set ";
					if(info.getStatusID()>=0)
					{
						strSQL += " STATUSID = "+info.getStatusID()+",";
					}
					if(info.getLastAppUserID()>0)
					{
						strSQL += " LASTAPPUSERID = "+info.getLastAppUserID() + ",";
					}
					if(info.getNextLevel()>0)
					{
						strSQL += " NEXTLEVEL = NEXTLEVEL+1 ,";
					}	
					strSQL = strSQL.substring(0, strSQL.length()-1);//去掉最后一个,
					strSQL += " where APPROVALENTRYID = "+info.getApprovalEntryID();
					
					//System.out.print("==========sql = "+ strSQL);
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
		}	
	}
	
}
