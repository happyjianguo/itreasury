/*
 * Created on 2004-11-19
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.setting.dataentity.TurnInBankTransSettingInfo;

/**
 * @author ytcui
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Sett_TurnInBankTransSettingDAO extends SettlementDAO
{
	/**
	 * Constructor for Sett_BranchDAO.
	 * @param conn
	 */
	public Sett_TurnInBankTransSettingDAO (Connection conn)
	{
		super(conn);
		this.strTableName = "sett_turninbanktranssetting";
	}
	/***
	 * 
	 * */
	public Sett_TurnInBankTransSettingDAO ()
	{
		super();
		this.strTableName = "sett_turninbanktranssetting";
	}
	
	/**
	 *增加自动入账的截至时间
	 * @param TurnInBankTransSettingInfo
	 * @exception Exception 异常说明。 
	 * */
	
	public void add(TurnInBankTransSettingInfo info) throws Exception
	{
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		StringBuffer buffer = new StringBuffer();
		buffer.append(" insert into \n");
		buffer.append(strTableName);
		buffer.append(" \n (DTTURNINENDTIME) \n");

		try
		{
			conn = this.getConnection();

			buffer.append(" values(?)\n");

			log.info(buffer.toString());

			pstmt = conn.prepareStatement(buffer.toString());
			
			int nIndex = 1;
			
			pstmt.setTimestamp(nIndex++,info.getDTTURNINENDTIME());
			
			
			pstmt.execute();			
		}
		finally
		{
			this.cleanup(pstmt);
			this.cleanup(conn);
		}		
	}
	/**
	 *修改自动入账的截至时间
	 * @param TurnInBankTransSettingInfo
	 * @exception Exception 异常说明。 
	 * */
	
	public void update(TurnInBankTransSettingInfo info) throws Exception
	{
		Connection conn = null;
		PreparedStatement pstmt = null;

		StringBuffer buffer = new StringBuffer();
		buffer.append("update " + strTableName + " set \n");

		buffer.append("DTTURNINENDTIME = ? \n");                           
	
		log.debug(buffer.toString());
		try
		{
			conn = this.getConnection();
			pstmt = conn.prepareStatement(buffer.toString());

			int nIndex = 1;

			pstmt.setTimestamp(nIndex++, info.getDTTURNINENDTIME());
		

			pstmt.execute();

		}
		finally
		{
			this.cleanup(pstmt);
			this.cleanup(conn);

		}
	}
	/**
	 * 删除自动入账的截至时间
	 * 操作数据库表sett_turninbanktranssetting
	 * 物理删除
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 *  
	 * @exception Exception
	 */
	public void delete() throws Exception {
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String removeString = null;

		try
		{
			con = getConnection();

			removeString = " delete  from  sett_turninbanktranssetting  ";

			ps = con.prepareStatement(removeString);

			

			ps.execute();
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}	

	}
	/**
	 * 判断自动入账设置表中是否有记录
	 * @return long
	 * @exception Exception
	 * */
	
	public long getCount() throws  Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		String getCountString ;
		ResultSet rs = null;
		
		long  lResult = -1;
		
		try
		{
			con = getConnection();

			getCountString = " select count(*)  from  sett_turninbanktranssetting  ";

			ps = con.prepareStatement(getCountString);
			rs = ps.executeQuery();
			if ( rs.next())
			{
				lResult = rs.getLong(1);
			}
			
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}	
		return lResult;
	}
	/**
	 * 查询自动入账的截至时间
	 * @throws SQLException
	 * */
	public Timestamp select() throws SQLException
	{
		Connection con = null;
		PreparedStatement ps = null;
		String getCountString ;
		ResultSet rs = null;
		
		Timestamp  stopTime = null;
		
		try
		{
			con = getConnection();

			getCountString = " select DTTURNINENDTIME  from  sett_turninbanktranssetting  ";

			ps = con.prepareStatement(getCountString);
			rs = ps.executeQuery();
			if ( rs.next())
			{
				stopTime = rs.getTimestamp(1);
			}
			
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}	
		return stopTime;
	
	}
}
