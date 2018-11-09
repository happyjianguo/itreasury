package com.iss.itreasury.settlement.setting.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import com.iss.itreasury.settlement.setting.dataentity.SapBankMatchInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.dao.SettlementDAO;

public class SapBankMatchDao extends SettlementDAO{
	
	public Vector querySapBankMatch(SapBankMatchInfo qbInfo) throws Exception
	{
		Vector v = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(
				"select s.id,s.banktype,s.bankmatch,s.status,b.sname,b.sbankaccountcode,b.scode from ep_branchmatchsetting s,SETT_BRANCH b"
				+" where s.bankmatch=b.id and s.status=1 and  b.nofficeid="
					+ qbInfo.getOfficeid()
					+ " and b.nCurrencyID= "
					+ qbInfo.getCurrencyid()
					+ " and s.officeid ="
					+ qbInfo.getOfficeid());
			sbSQL.append(" order by b.scode");
			log.info(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				SapBankMatchInfo bi = new SapBankMatchInfo();	
				bi.setId(rs.getLong("id")); 
				bi.setBankmath(rs.getLong("bankmatch"));
				bi.setBanktype(rs.getLong("banktype"));
				bi.setStatus(rs.getLong("status"));
				bi.setSbankaccoutcode(rs.getString("sbankaccountcode"));
				bi.setScode(rs.getString("scode"));
				bi.setSname(rs.getString("sname"));
				v.add(bi);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return v != null ? v : null;
	}
    
	/**
	 * 获取最大ID
	 * @return long
	 * @exception Exception 异常说明。
	 */
	public long getMaxID() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lResult = -1;
		StringBuffer sb = new StringBuffer();
		try
		{
			con = getConnection();
			sb.append(" select nvl(max(id),0)+1 id \n");
			sb.append(" from ep_branchmatchsetting  ");
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				lResult = rs.getLong(1);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return lResult;
	}
	
	public long isSameBanktype(SapBankMatchInfo sapinfo) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		long lTemp = -1;
		long lReturn = -1;
		try
		{
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" select s.banktype from ep_branchmatchsetting s ");
			sbSQL.append(" where  s.status != " + Constant.RecordStatus.INVALID);
			if(sapinfo.getOfficeid()>0)
			{
				sbSQL.append(" and s.officeid ="+sapinfo.getOfficeid());
				
			}
			log.info(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				lTemp = rs.getLong("banktype");
				if(sapinfo.getBanktype() == lTemp)
				{
					lReturn = 1;
				}
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}
	
	public long add(SapBankMatchInfo info) throws Exception
	{
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		StringBuffer buffer = new StringBuffer();
		buffer.append(" insert into \n");
		buffer.append(" ep_branchmatchsetting ");
		buffer.append(" \n (ID, \n");
		buffer.append("BANKTYPE,\n");
		buffer.append("BANKMATCH,\n");
		buffer.append("STATUS,\n");
		buffer.append("OFFICEID)\n");
		try
		{
			conn = getConnection();
			buffer.append(" values(?,?,?,?,?)");
			log.info(buffer.toString());
			pstmt = conn.prepareStatement(buffer.toString());
			long id = this.getMaxID();
			info.setId(id);
			int nIndex = 1;
			pstmt.setLong(nIndex++, info.getId());
			pstmt.setLong(nIndex++, info.getBanktype());
			pstmt.setLong(nIndex++, info.getBankmath());
			pstmt.setLong(nIndex++, 1);
			pstmt.setLong(nIndex++, info.getOfficeid());
			pstmt.execute();
			lReturn = id;
		}
		finally
		{
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		return lReturn;
	}
	
	public void update(SapBankMatchInfo info) throws Exception
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		StringBuffer buffer = new StringBuffer();
		buffer.append("update " + "ep_branchmatchsetting" + " set \n");
		buffer.append("BANKTYPE = ?,\n");                           
		buffer.append("BANKMATCH = ?,\n");                               
		buffer.append("STATUS = ?,\n");                               
		buffer.append("officeid = ?\n");                               
		buffer.append("where ID=?\n");
		log.debug(buffer.toString());
		try
		{
			conn = this.getConnection();
			pstmt = conn.prepareStatement(buffer.toString());
			int nIndex = 1;
			pstmt.setLong(nIndex++, info.getBanktype());
			pstmt.setLong(nIndex++, info.getBankmath());
			pstmt.setLong(nIndex++, 1);
			pstmt.setLong(nIndex++,info.getOfficeid());
			pstmt.setLong(nIndex++, info.getId());
			pstmt.execute();
		}
		finally
		{
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
	}
	
	public long deleteSapBankMacth(long lID) throws Exception {
		long lResult = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String removeString = null;
		try
		{
			con = getConnection();
			removeString = " update  ep_branchmatchsetting  set STATUS= ?  WHERE ID =?       ";
			ps = con.prepareStatement(removeString);
			ps.setLong(1, SETTConstant.RecordStatus.INVALID);
			ps.setLong(2, lID);
			lResult = ps.executeUpdate();
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return lResult;
	}
}
