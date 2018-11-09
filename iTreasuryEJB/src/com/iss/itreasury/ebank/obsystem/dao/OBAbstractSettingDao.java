package com.iss.itreasury.ebank.obsystem.dao;

import com.iss.itreasury.ebank.obsystem.dataentity.*;
import com.iss.itreasury.settlement.account.dataentity.ExternalAccountInfo;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

public class OBAbstractSettingDao {
	
	public long saveStandardAbstract(OBAbstractSettingInfo info) throws Exception{
		
		long lret = -1;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql=null;
		
		sql = "select id from ob_standardabstract where nStatusID = 1 and nofficeid=?  and nclientid = ? and SDESC = ?";
		
		try{
			
			con = Database.getConnection();
			ps = con.prepareStatement(sql);
			ps.setLong(1, info.getNofficeid());
			ps.setLong(2, info.getNclientid());
			ps.setString(3, info.getSdesc());
			rs = ps.executeQuery();
			
			while(rs.next()){
				
				long lVar = rs.getLong("id");
				if(lVar != info.getId()){
					
					return -1;
					
				}
				
			}
			
			if (info.getId() < 0) {
				
				sql = "insert into ob_standardabstract values(?,?,?,?,?,?,?,?)";
				ps = con.prepareStatement(sql);
				ps.setLong(1, this.getMaxId());
				ps.setLong(2, info.getNofficeid());
				ps.setLong(3, info.getNclientid());
				ps.setString(4, info.getScode());
				ps.setString(5, info.getSdesc().trim());
				ps.setLong(6, info.getNstatusid());
				ps.setTimestamp(7, info.getInputtime());
				ps.setTimestamp(8, info.getModifytime());
				lret = ps.executeUpdate();
				
			} else {
				
				sql = "update ob_standardabstract set  sDesc=?, modifytime=? where id= " + info.getId();
				ps = con.prepareStatement(sql);
				ps.setString(1, info.getSdesc().trim());
				ps.setTimestamp(2, info.getModifytime());
				ps.executeUpdate();
				System.out.print(sql);
				lret = info.getId();
				
			}
			
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			throw e;
	
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		
		
		
		
		return lret;
	}
	
	public long getMaxId() throws Exception{
		
		long lret = -1;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql=null;
		
		sql = "select nvl(to_number(max(id))+1,1) from ob_standardabstract";
		
		try{
			
			con = Database.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				lret = rs.getLong(1);
			}
			System.out.print(lret);
			
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			throw e;
	
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return lret;
	}
	
	public Collection findAllStandardAbstract(OBAbstractSettingInfo info) throws Exception{
		
    	Collection coll = new ArrayList();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql=null;
		sql = "select * from ob_standardabstract where nStatusID = ? and nofficeid=? and nclientid=? order by scode asc";
		System.out.println(sql);
		
		try{
			
			con = Database.getConnection();
			ps = con.prepareStatement(sql);
			ps.setLong(1, info.getNstatusid());
			ps.setLong(2, info.getNofficeid());
			ps.setLong(3, info.getNclientid());
			rs = ps.executeQuery();
            while(rs.next()){
            	OBAbstractSettingInfo sInfo = new OBAbstractSettingInfo();
            	sInfo.setId(rs.getLong(1));
            	sInfo.setNofficeid(rs.getLong(2));
            	sInfo.setNclientid(rs.getLong(3));
            	sInfo.setScode(rs.getString(4));
            	sInfo.setSdesc(rs.getString(5));
            	sInfo.setNstatusid(rs.getLong(6));
            	sInfo.setInputtime(rs.getTimestamp(7));
            	sInfo.setModifytime(rs.getTimestamp(8));
            	coll.add(sInfo);
            }
			
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			throw e;
	
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
    	
    	return coll;
		
	}
	
	public OBAbstractSettingInfo findAllStandardAbstractByID(long lID) throws Exception{
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		OBAbstractSettingInfo resultInfo = new OBAbstractSettingInfo();
		
		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT * FROM ob_standardabstract");
			sbSQL.append(" WHERE  ID="+lID);
			
	 
			Log.print(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();

			while (rs.next())
			{
				
				resultInfo.setId(rs.getLong("id"));
				resultInfo.setNofficeid(rs.getLong("nofficeid"));
				resultInfo.setNclientid(rs.getLong("nclientid"));
				resultInfo.setScode(rs.getString("scode"));
				resultInfo.setSdesc(rs.getString("sdesc"));
				resultInfo.setInputtime(rs.getTimestamp("inputtime"));
				resultInfo.setModifytime(rs.getTimestamp("modifytime"));
			}
		}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
				throw e;
		
			}
			finally
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			
			return resultInfo != null ? resultInfo : null;
		

	}		
	
	public long getMaxCode(long lOfficeID,long lClientID) throws Exception{
		
		long lResult = -1;
		long tmp1 = -1;
		long tmp2 = -1;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		StringBuffer sb = new StringBuffer();
		sb.append(" select decode(min(a.scode),null,1,min(a.scode)) scode from ob_standardabstract a where a.SCODE not in");
		sb.append(" (select a.SCODE from ob_standardabstract a, ob_standardabstract b");
		sb.append(" where a.id = b.id and a.scode = b.scode and a.nclientid =? and a.nofficeid=?");
		sb.append(" and a.nstatusid = ?)");
		sb.append(" and a.nclientid =? and nofficeid=?");
		
		StringBuffer sb1 = new StringBuffer();
		sb1.append(" select nvl(min(a.scode),0) scode from ob_standardabstract a where a.SCODE not in");
		sb1.append(" (select a.SCODE from ob_standardabstract a, ob_standardabstract b");
		sb1.append(" where a.id = b.id and a.scode = b.scode and a.nclientid =? and a.nofficeid=?");
		sb1.append(" and a.nstatusid = ?)");
		sb1.append(" and a.nclientid =? and nofficeid=?");
		
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" select count(*) num from ob_standardabstract a");
		sb2.append(" where a.nclientid = ? and a.nofficeid = ? and a.nstatusid = ? ");
		
		StringBuffer sb3 = new StringBuffer(); 
		sb3.append(" select max(a.SCODE)+1 num from ob_standardabstract a");
		sb3.append(" where a.nclientid = ? and a.nofficeid = ? and a.nstatusid = ? ");
		
		try{
			con = Database.getConnection();
			
			ps = con.prepareStatement(sb1.toString());
			ps.setLong(1, lClientID);
			ps.setLong(2, lOfficeID);
			ps.setLong(3, Constant.RecordStatus.VALID);
			ps.setLong(4, lClientID);
			ps.setLong(5, lOfficeID);
			rs = ps.executeQuery();
			if (rs.next()) {
				tmp1 = rs.getLong(1);
			}
			
			ps = con.prepareStatement(sb2.toString());
			ps.setLong(1, lClientID);
			ps.setLong(2, lOfficeID);
			ps.setLong(3, Constant.RecordStatus.VALID);
			rs = ps.executeQuery();
			if (rs.next()) {
				tmp2 = rs.getLong(1);
			}
			
			if(tmp1 == 0 && tmp2 > 0){
				
				ps = con.prepareStatement(sb3.toString());
				ps.setLong(1, lClientID);
				ps.setLong(2, lOfficeID);
				ps.setLong(3, Constant.RecordStatus.VALID);
				rs = ps.executeQuery();
				if (rs.next()) {
					lResult = rs.getLong(1);
				}
				
			}else{
				
				ps = con.prepareStatement(sb.toString());
				ps.setLong(1, lClientID);
				ps.setLong(2, lOfficeID);
				ps.setLong(3, Constant.RecordStatus.VALID);
				ps.setLong(4, lClientID);
				ps.setLong(5, lOfficeID);
				rs = ps.executeQuery();
				if (rs.next()) {
					lResult = rs.getLong(1);
				}
				
			}
			
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			throw e;
	
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		
		return lResult;
	}
	
	public long updateStatus(String id, long StatusID) throws Exception{
		
		long lReturn = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = Database.getConnection();
			/**Important: If any field in database changed, please correct them at here and fucntion:addDatatoPrepareStatement*/
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("UPDATE \n");
			strSQLBuffer.append("ob_standardabstract   \n");
			strSQLBuffer.append(" SET \n");
			//strSQLBuffer.append("dtModify = sysdate, \n");
			strSQLBuffer.append(" nStatusID = ? \n");
			strSQLBuffer.append(" WHERE ID in ("+id+") \n");
			String strSQL = strSQLBuffer.toString();
			ps = con.prepareStatement(strSQL);
			System.out.println(strSQL);
			ps.setLong(1, StatusID);
			//ps.setString(2, id);
			ps.executeUpdate();
			lReturn = 1;
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			throw e;
	
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return lReturn;
		
	}
	
	
	
	private void cleanup(ResultSet rs) throws SQLException
	{
		try
		{
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
		}
		catch (SQLException sqle)
		{
		}
	}
	private void cleanup(Statement st) throws SQLException
	{
		try
		{
			if (st != null)
			{
				st.close();
				st = null;
			}
		}
		catch (SQLException sqle)
		{
		}
	}
	private void cleanup(PreparedStatement ps) throws SQLException
	{
		try
		{
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
		}
		catch (SQLException sqle)
		{
		}
	}
	private void cleanup(Connection con) throws SQLException
	{
		try
		{
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (SQLException sqle)
		{
		}
	}
}
