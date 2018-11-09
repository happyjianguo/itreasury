package com.iss.itreasury.treasuryplan.setting.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.treasuryplan.setting.dataentity.SystemParameterInfo;
import com.iss.itreasury.treasuryplan.util.TreasuryPlanDAO;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log4j;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-2-27
 */
public class Trea_SystemParameterDAO extends TreasuryPlanDAO {
	protected Log4j log4j = new Log4j(Constant.ModuleType.PLAN, this);

	public Trea_SystemParameterDAO(){
		super("Trea_SystemParameter");
	}
	
	public Trea_SystemParameterDAO(Connection conn){
		super("Trea_SystemParameter",conn);
	}	
	/**
	 * 查找最新一条有效纪录
	 * @param lStatusID
	 * @return
	 * @throws Exception
	 */
	public SystemParameterInfo find(SystemParameterInfo info) throws Exception
	{
		SystemParameterInfo rtnInfo = new SystemParameterInfo();
		Connection con = Database.getConnection();
		PreparedStatement ps = null;		
		ResultSet rs = null;	
		try 
		{				
						
			String strSQL = "select * from Trea_SystemParameter where officeID="+info.getOfficeID()+" and statusID=" + info.getStatusID() +" and ParameterName='" + info.getParameterName() + "' order by id desc";
			log4j.info(strSQL);
			ps = con.prepareStatement(strSQL);
		
			rs=ps.executeQuery();
			if(rs.next())
			{
				rtnInfo=getRs(info,rs);				
			}			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{	
			try
			{	
				if(ps != null)
				{
					ps.close();
				}
				if(rs != null)
				{
					rs.close();
				}
				if(con!=null)
				{
					con.close();
				}	
			}
			catch(Exception e)
			{
				e.printStackTrace();
				throw e;
			}					
		}
		return rtnInfo;
	}
	
	
	/**
	 * 查找最新一条有效纪录(有效日期最大)
	 * @param lStatusID
	 * @return
	 * @throws Exception
	 */
	public SystemParameterInfo findLastEffectiveDate(SystemParameterInfo info) throws Exception
	{
		SystemParameterInfo rtnInfo = new SystemParameterInfo();
		Connection con = Database.getConnection();
		PreparedStatement ps = null;		
		ResultSet rs = null;	
		try 
		{				
						
			String strSQL = "select * from Trea_SystemParameter where officeID="+info.getOfficeID()+" and statusID=" + info.getStatusID() +" and EFFECTIVEDATE <= to_date('" + DataFormat.formatDate(info.getEffectiveDate())+ "','yyyy-mm-dd') and ParameterName='" + info.getParameterName() + "' order by EFFECTIVEDATE desc";
			log4j.info(strSQL);
			ps = con.prepareStatement(strSQL);
		
			rs=ps.executeQuery();
			if(rs.next())
			{
				rtnInfo=getRs(info,rs);				
			}			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{	
			try
			{	
				if(ps != null)
				{
					ps.close();
				}
				if(rs != null)
				{
					rs.close();
				}
				if(con!=null)
				{
					con.close();
				}	
			}
			catch(Exception e)
			{
				e.printStackTrace();
				throw e;
			}					
		}
		return rtnInfo;
	}
	/**
	 * 设置结果集： 
	 * 逻辑说明：
	 * @throws Exception
	 */
	private SystemParameterInfo getRs(SystemParameterInfo info,ResultSet rs) throws Exception
	{
		info = new SystemParameterInfo();
		try
		{			
			info.setId(rs.getLong("ID"));
			info.setEffectiveDate(rs.getTimestamp("EffectiveDate"));
			info.setInputDate(rs.getTimestamp("InputDate"));			
			info.setInputUserID(rs.getLong("InputUserID"));
			info.setParameterName(rs.getString("ParameterName"));
			info.setParameterValue(rs.getDouble("ParameterValue"));
			info.setStatusID(rs.getLong("StatusID"));			
			info.setUpdateDate(rs.getTimestamp("UpdateDate"));	
			info.setUpdateUserID(rs.getLong("UpdateUserID"));		
			info.setOfficeID(rs.getLong("OfficeID"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return info;
		
	}
	/**
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public  long getMaxID() throws Exception
	{
		long lMaxID = -1;
		Connection con = Database.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String strSQL = "select nvl(max(id)+1,1) from Trea_SystemParameter ";
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lMaxID = rs.getLong(1);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if(ps != null)
			{
				ps.close();
			}
			if(rs != null)
			{
				rs.close();
			}
			if(con!=null)
			{
				con.close();
			}			

		}
		return lMaxID;
	}
	
}
