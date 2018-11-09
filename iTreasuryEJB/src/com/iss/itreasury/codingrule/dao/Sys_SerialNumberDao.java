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
package com.iss.itreasury.codingrule.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.codingrule.dataentity.CodingRuleRelationInfo;
import com.iss.itreasury.codingrule.dataentity.SerialnumberDetailInfo;
import com.iss.itreasury.codingrule.dataentity.SerialnumberInfo;
import com.iss.itreasury.codingrule.util.CodingRuleFormat;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.approval.dataentity.InutApprovalRelationInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;

public class Sys_SerialNumberDao extends ITreasuryDAO implements java.io.Serializable
{	
	public Sys_SerialNumberDao()
	{
		super("SYS_SERIALNUMBER");
	}

	public Sys_SerialNumberDao(Connection con)
	{
		super("SYS_SERIALNUMBER",con);
	}
	
	
	
	public Collection queryByCondition(CodingRuleRelationInfo info) throws IException
	{
		Vector v_Return = new Vector();
		ResultSet localRS = null;
		String strSQL = "";
		try
		{
			initDAO();
			strSQL = "select * from SYS_CODINGRULERELATION where 1=1 ";
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
			if(info.getCodingruleID()>0)
			{
				strSQL = strSQL + " and codingruleID = " + info.getCodingruleID();
			}	
			if(info.getSerialnumberID()>0)
			{
				strSQL = strSQL + " and serialnumberID = " + info.getSerialnumberID();
			}

			strSQL = strSQL + " order by id,transtypeid,actionid ";
			System.out.println("-----------------strSQL---------------------"+strSQL.toString());
			prepareStatement(strSQL.toString());
			localRS = executeQuery();
			while (localRS.next())
			{
				CodingRuleRelationInfo tempInfo = new CodingRuleRelationInfo();
				tempInfo.setId(localRS.getLong("id"));
				tempInfo.setOfficeID(localRS.getLong("officeid"));
				tempInfo.setCurrencyID(localRS.getLong("currencyid"));
				tempInfo.setModuleID(localRS.getLong("moduleid"));
				tempInfo.setTransTypeID(localRS.getLong("transTypeID"));
				tempInfo.setActionID(localRS.getLong("actionID"));
				tempInfo.setCodingruleID(localRS.getLong("codingruleID"));
				tempInfo.setSerialnumberID(localRS.getLong("serialnumberID"));

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
	
	public SerialnumberInfo findSerialNumberInfoByRelationInfo(CodingRuleRelationInfo info) throws IException
	{
		SerialnumberInfo returnInfo = new SerialnumberInfo();
		ResultSet localRS = null;
		String strSQL = "";
		try
		{
			initDAO();
			strSQL = "select se.* from SYS_CODINGRULERELATION re,SYS_SERIALNUMBER se where re.serialnumberid = se.id ";
			if(info.getId()>0)
			{
				strSQL = strSQL + " and re.id = "+ info.getId() ;
			}
			if(info.getOfficeID()>0)
			{
				strSQL = strSQL + " and re.officeid = " + info.getOfficeID() ;
			}
			if(info.getCurrencyID()>0)
			{
				strSQL = strSQL + " and re.currencyid = " + info.getCurrencyID();
			}
			if(info.getModuleID()>0)
			{
				strSQL = strSQL + " and re.moduleid = " + info.getModuleID();
			}
			if(info.getTransTypeID()>0)
			{
				strSQL = strSQL + " and re.transtypeid = " + info.getTransTypeID();
			}
			if(info.getActionID()>0)
			{
				strSQL = strSQL + " and re.actionid = " + info.getActionID();
			}	
			if(info.getSerialnumberID()>0)
			{
				strSQL = strSQL + " and re.serialnumberID = " + info.getSerialnumberID();
			}

			strSQL = strSQL + " order by re.id,re.transtypeid,re.actionid ";
			//System.out.println("-----------------strSQL---------------------"+strSQL.toString());
			prepareStatement(strSQL.toString());
			localRS = executeQuery();
			while (localRS.next())
			{
				returnInfo.setId(localRS.getLong("ID"));
				returnInfo.setOfficeid(localRS.getLong("OFFICEID"));
				returnInfo.setIscurrency(localRS.getLong("ISCURRENCY"));
				returnInfo.setIscontract(localRS.getLong("ISCONTRACT"));
				returnInfo.setPeriodtype(localRS.getLong("PERIODTYPE"));
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
		
		return returnInfo;
	}
	
}
