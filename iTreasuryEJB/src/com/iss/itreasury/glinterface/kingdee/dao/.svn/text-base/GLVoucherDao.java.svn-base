package com.iss.itreasury.glinterface.kingdee.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import com.iss.itreasury.glinterface.dataentity.GLKingDeePzflInfo;
import com.iss.itreasury.glinterface.kingdee.GLKingDeeBean;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Database;

/**
 * ½ðµû²Ù×÷Àà
 * @author xiangzhou 2012-11-27
 *
 */
public class GLVoucherDao {

	public String queryGLVoucherSQL(GLKingDeePzflInfo pzflinfo) {
		// TODO Auto-generated method stub
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append(" select FNumber,FEntryDC,FAccountCode,FAmount,FCustomerCode from CT_CUS_Fmbusiness");
		sbSQL.append(" where FState = "+SETTConstant.VoucherStatus.RTNVOUCHER);
		if(pzflinfo.getExecute()!=null && pzflinfo.getExecute().trim().length()>0){
			sbSQL.append(" and FBookDate = to_date('"+pzflinfo.getExecute().trim()+"','yyyy-mm-dd')");
		}
		return sbSQL.toString();
	}

	public GLKingDeePzflInfo queryGLEntry(GLKingDeePzflInfo pzflinfo) throws Exception {
	  	
	   GLKingDeePzflInfo info = null;
       
	   Connection conn = null;
	   PreparedStatement ps = null;
	   ResultSet rs = null;
       
       StringBuffer sbSQL = new StringBuffer();
       sbSQL.append(" select FNumber,FEntryDC,FAccountCode,FAmount,FCustomerCode from CT_CUS_Fmbusiness");
	   sbSQL.append(" where FState = ? and fnumber = ? and faccountcode = ?");
	   
	   try
		{
		    int iIndex = 1;
//		    conn = ConnectionSQLServer.getU8Connection(pzflinfo.getOfficeID(),pzflinfo.getFCurrencyID(),0);
		    conn = GLKingDeeBean.get_jdbc_connection(pzflinfo.getOfficeID(),pzflinfo.getFCurrencyID(),0);
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(iIndex++, SETTConstant.VoucherStatus.RTNVOUCHER);
			ps.setString(iIndex++, pzflinfo.getFNumber());
			ps.setString(iIndex++, pzflinfo.getFAccountID());
			rs = ps.executeQuery();

			while(rs.next())
			{
				info = new GLKingDeePzflInfo();
				info.setFNumber(rs.getString("FNumber"));
				info.setFDC(rs.getString("FEntryDC"));
				info.setFAccountID(rs.getString("FAccountCode"));
				info.setFAmount(rs.getDouble("FAmount"));
				info.setFCustomerCode(rs.getString("FCustomerCode"));
			}

		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	  finally
      {
          if (rs != null)
          {
              rs.close();
              rs = null;
          }
          if (ps != null)
          {
        	  ps.close();
        	  ps = null;
          }
          if (conn != null)
          {
              conn.close();
              conn = null;
          }
      }
	  return info;
	}

	public long modifyGLEntry(GLKingDeePzflInfo info) throws Exception {
		// TODO Auto-generated method stub
		long sign = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		
	    StringBuffer buffer = new StringBuffer();
		buffer.append(" update CT_CUS_Fmbusiness set FCustomerCode = ?,FState = ? ");
		buffer.append(" where fnumber = ? and faccountcode = ?");

		try
		{
			int iIndex = 1;
//		    conn = ConnectionSQLServer.getU8Connection(info.getOfficeID(),info.getFCurrencyID(),0);
			conn = GLKingDeeBean.get_jdbc_connection(info.getOfficeID(),info.getFCurrencyID(),0);
			ps = conn.prepareStatement(buffer.toString());
			ps.setString(iIndex++, info.getFCustomerCode());
			ps.setLong(iIndex++, SETTConstant.VoucherStatus.VALID);
			ps.setString(iIndex++, info.getFNumber());
			ps.setString(iIndex++, info.getFAccountID());
			sign = ps.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		finally
		{
		      if (ps != null)
		      {
		    	  ps.close();
		    	  ps = null;
		      }
		      if (conn != null)
		      {
		          conn.close();
		          conn = null;
		      }
		}
		return sign;
	}	
	
	public String queryGLKingdeeclientSQL(GLKingDeePzflInfo pzflinfo) {
		
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append(" select cc.id clientID,cc.code clientCode,cc.name clientName,kdc.id kingDeeID,kdc.assitantValue,kdc.kingDeeClientName from client_clientinfo cc");
		sbSQL.append(" left join gl_kingdeeclient kdc on cc.id = kdc.clientid where 1=1");
		if (pzflinfo.getStartClientCode() != null && pzflinfo.getStartClientCode().trim().length() > 0){
			sbSQL.append(" and cc.code >= '" + pzflinfo.getStartClientCode() + "'");
		}
		if (pzflinfo.getEndClientCode() != null && pzflinfo.getEndClientCode().trim().length() > 0){
			sbSQL.append(" and cc.code <= '" + pzflinfo.getEndClientCode() + "'");
		}
			
		return sbSQL.toString();
	}
	
	public long insertGLKingdeeclient(GLKingDeePzflInfo info) throws Exception {
		
		long sign = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long maxid = -1;
	    StringBuffer buffer = null;

		try
		{
			int iIndex = 1;
		    conn = Database.getConnection();
		    buffer = new StringBuffer();
		    buffer.append(" select nvl(max(ID),0) maxid from GL_KINGDEECLIENT");
			ps = conn.prepareStatement(buffer.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				maxid = rs.getLong("maxid")+1;
			}
			buffer = new StringBuffer();
			buffer.append(" INSERT INTO GL_KINGDEECLIENT VALUES ( ?,?,?,? )");
			ps = conn.prepareStatement(buffer.toString());
			ps.setLong(iIndex++, maxid);
			ps.setLong(iIndex++, info.getClientID());
			ps.setString(iIndex++, info.getAssitantValue());
			ps.setString(iIndex++, info.getKingDeeClientName());
			sign = ps.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		finally
		{
		      if (ps != null)
		      {
		    	  ps.close();
		    	  ps = null;
		      }
		      if (rs != null)
		      {
		    	  rs.close();
		    	  rs = null;
		      }
		      if (conn != null)
		      {
		          conn.close();
		          conn = null;
		      }
		}
		return sign;
	}
	
	public long modifyGLKingdeeclient(GLKingDeePzflInfo info) throws Exception {
		
		long sign = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		
	    StringBuffer buffer = new StringBuffer();
		buffer.append(" UPDATE GL_KINGDEECLIENT SET KINGDEECLIENTNAME = ? ");
		buffer.append(" WHERE ID = ? ");

		try
		{
			int iIndex = 1;
		    conn = Database.getConnection();
			ps = conn.prepareStatement(buffer.toString());
			ps.setString(iIndex++, info.getKingDeeClientName());
			ps.setLong(iIndex++, info.getId());
			sign = ps.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		finally
		{
		      if (ps != null)
		      {
		    	  ps.close();
		    	  ps = null;
		      }
		      if (conn != null)
		      {
		          conn.close();
		          conn = null;
		      }
		}
		return sign;
	}
	
	public Map<String, String> getGLKingdeeclient() throws Exception {
	  	
		   Map<String, String> map = new HashMap<String, String>();
		   
		   Connection conn = null;
		   PreparedStatement ps = null;
		   ResultSet rs = null;
	       
	       StringBuffer sbSQL = new StringBuffer();
	       sbSQL.append(" select a.assitantvalue key ,a.kingdeeclientname value from GL_KINGDEECLIENT a");
		   
		   try
			{
			    conn = Database.getConnection();
				ps = conn.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();

				while(rs.next())
				{
					map.put(rs.getString("key"), rs.getString("value"));
				}

			}catch(Exception e){
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
		  finally
	      {
	          if (rs != null)
	          {
	              rs.close();
	              rs = null;
	          }
	          if (ps != null)
	          {
	        	  ps.close();
	        	  ps = null;
	          }
	          if (conn != null)
	          {
	              conn.close();
	              conn = null;
	          }
	      }
		  return map;
		}

}
