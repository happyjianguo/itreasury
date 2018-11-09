package com.iss.itreasury.loan.assureresmanage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.loan.assureresmanage.dataentity.LOANGageMaintenanceInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Database;

public class Maintenance{

	
	private Log4j log = new Log4j(Constant.ModuleType.LOAN, this);
	
	/**
	 * 担保品信息维护日志查询
	 * add haoliang
	 * 2008-06-27
	 */
	public static List getLogger(long gageID)throws Exception 
	{
		List result = new ArrayList();
		Connection transConn = null;
		ResultSet transRS = null;
		PreparedStatement transPS = null;
		StringBuffer sbSQL = new StringBuffer();
		LOANGageMaintenanceInfo maintenanceInfo = null;
		try{
			transConn = Database.getConnection();
		}catch(Exception eConn){
			 throw new IException("查找维护日志创建数据库链接时出错........");
		}
		try{
			sbSQL.append(" SELECT A.ID AS ID,A.GAGEID AS GAGEID,A.MUSERID AS MUSERID,A.MACTION AS MACTION,A.MDATE AS MDATE,");
			sbSQL.append(" A.MSTATUS AS MSTATUS,A.OFFICEID AS OFFICEID,A.CURRENCYID AS CURRENCYID,U.SNAME AS SNAME");
			sbSQL.append(" FROM LOAN_GAGEMAINTENANCEINFO A,USERINFO U WHERE A.MUSERID = U.ID");
			sbSQL.append(" AND A.GAGEID = ? AND A.MSTATUS != 0");
			sbSQL.append(" order by A.ID ");
			transPS = transConn.prepareStatement(sbSQL.toString());
			transPS.setLong(1,gageID);
			transRS = transPS.executeQuery();
			while(transRS != null && transRS.next())
			{
				maintenanceInfo = new LOANGageMaintenanceInfo();
				maintenanceInfo.setId(transRS.getLong("ID"));
				maintenanceInfo.setGageid(transRS.getLong("GAGEID"));
				maintenanceInfo.setMuserid(transRS.getLong("MUSERID"));
				maintenanceInfo.setMaction(transRS.getLong("MACTION"));
				maintenanceInfo.setMdate(transRS.getTimestamp("MDATE"));
				maintenanceInfo.setMstatus(transRS.getLong("MSTATUS"));
				maintenanceInfo.setOfficeid(transRS.getLong("OFFICEID"));
				maintenanceInfo.setCurrencyid(transRS.getLong("CURRENCYID"));
				maintenanceInfo.setMusername(transRS.getString("SNAME"));
				result.add(maintenanceInfo);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new IException("Gen_E001",e);
			
		}finally{
			try
			{
				if (transRS != null)
				{
					transRS.close();
					transRS = null;
				}
				if (transPS != null)
				{
					transPS.close();
					transPS = null;
				}
				if (transConn != null)
				{
					transConn.close();
					transConn = null;
				}
			}
			catch (SQLException sqle)
			{
				
			}
		}
		return result;
	}
	
	public static void addLogger(long gageID,long mUserID,long mAction,long officeID,long currencyID)throws Exception
	{
		Connection transConn = null;
		PreparedStatement transPS = null;
		StringBuffer sbSQL = new StringBuffer();
		try{
			transConn = Database.getConnection();
		}catch(Exception eConn){
			 throw new IException("添加维护日志创建数据库链接时出错........");
		}
		try{
			
			sbSQL.append("INSERT INTO LOAN_GAGEMAINTENANCEINFO(ID,GAGEID,MUSERID,MACTION,MDATE,MSTATUS,OFFICEID,CURRENCYID)");
			sbSQL.append("VALUES((SELECT NVL(MAX(ID)+1,1) FROM LOAN_GAGEMAINTENANCEINFO),?,?,?,SYSDATE,?,?,?)");
			transPS = transConn.prepareStatement(sbSQL.toString());
			transPS.setLong(1,gageID);
			transPS.setLong(2,mUserID);
			transPS.setLong(3,mAction);
			transPS.setLong(4,LOANConstant.GageStatus.SAVE);
			transPS.setLong(5,officeID);
			transPS.setLong(6,currencyID);
			transPS.executeUpdate();
			
		}catch(Exception e){
			
			e.printStackTrace();
			throw new IException("添加维护日志时出错："+e.getMessage());
			
		}finally{
			try
			{
				if (transPS != null)
				{
					transPS.close();
					transPS = null;
				}
				if (transConn != null)
				{
					transConn.close();
					transConn = null;
				}
			}
			catch (SQLException sqle)
			{
				
			}
		}
	}
	
}





















