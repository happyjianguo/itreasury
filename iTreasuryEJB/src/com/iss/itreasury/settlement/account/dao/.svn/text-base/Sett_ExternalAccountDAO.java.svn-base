package com.iss.itreasury.settlement.account.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.account.dataentity.ExternalAccountInfo;
import com.iss.itreasury.settlement.setting.dataentity.StandardAbstractInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2003-8-23
 */
public class Sett_ExternalAccountDAO extends SettlementDAO
{
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	public Sett_ExternalAccountDAO()
	{
		super.strTableName = "sett_ExternalAccount";
	}
	public long add(ExternalAccountInfo info) throws Exception
	{
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		StringBuffer buffer = new StringBuffer();
		buffer.append("insert into \n");
		buffer.append(strTableName);
		buffer.append("\n (ID, \n");
		buffer.append("nOfficeID,\n");
		buffer.append("sExtAcctNo,\n");
		buffer.append("sExtAcctName,\n");
		buffer.append("sBankName,\n");
		buffer.append("sProvince,\n");
		buffer.append("sCity,\n ");
		buffer.append("nClientID,ncurrencyid,spayeebankcnapsno,spayeebankexchangeno,spayeebankorgno)\n ");
		buffer.append("values(?,?,?,?,?,?,?,?,?,?,?,?)");
		log.info(buffer.toString());
		try
		{
			long id = this.getNextID();
			info.setId(id);
			conn = this.getConnection();
			pstmt = conn.prepareStatement(buffer.toString());
			int nIndex = 1;
			pstmt.setLong(nIndex++, info.getId());
			pstmt.setLong(nIndex++, info.getOfficeID());
			pstmt.setString(nIndex++, info.getExtAcctNo());
			pstmt.setString(nIndex++, info.getExtAcctName());
			pstmt.setString(nIndex++, info.getBankName());
			pstmt.setString(nIndex++, info.getProvince());
			pstmt.setString(nIndex++, info.getCity());
			pstmt.setLong(nIndex++, info.getClientID());
			pstmt.setLong(nIndex++, info.getNcurrencyID());
			pstmt.setString(nIndex++, info.getSpayeebankcnapsno());
			pstmt.setString(nIndex++, info.getSpayeebankexchangeno());
			pstmt.setString(nIndex++, info.getSpayeebankorgno());
			pstmt.execute();
			lReturn = info.getId();
		}
		catch (Exception sqle)
		{
			throw sqle;
		}
		finally
		{
			try
			{
				this.cleanup(pstmt);
				this.cleanup(conn);
			}
			catch (Exception e)
			{
				throw e;
			}
		}
		return lReturn;
	}
	public long update(ExternalAccountInfo info) throws Exception
	{
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		StringBuffer buffer = new StringBuffer(64);
		buffer.append("update \n");
		buffer.append(strTableName);
		buffer.append(" set \n");
		buffer.append("NOFFICEID =?,\n");
		buffer.append("sExtAcctNo =?,\n");
		buffer.append("sExtAcctName =?,\n ");
		buffer.append("sBankName =?,\n");
		buffer.append("sProvince =?,\n");
		buffer.append("sCity =?,\n");
		buffer.append("nClientID=?, \n ");
		buffer.append("ncurrencyid="+info.getNcurrencyID()+", \n ");
		buffer.append("spayeebankexchangeno= ?, \n ");
		buffer.append("spayeebankcnapsno= ?, \n ");
		buffer.append("spayeebankorgno=? \n ");
		buffer.append("where id=?\n");
		;
		try
		{
			conn = this.getConnection();
			pstmt = conn.prepareStatement(buffer.toString());
			int nIndex = 1;
			pstmt.setLong(nIndex++, info.getOfficeID());
			pstmt.setString(nIndex++, info.getExtAcctNo());
			pstmt.setString(nIndex++, info.getExtAcctName());
			pstmt.setString(nIndex++, info.getBankName());
			pstmt.setString(nIndex++, info.getProvince());
			pstmt.setString(nIndex++, info.getCity());
			pstmt.setLong(nIndex++, info.getClientID());
			pstmt.setString(nIndex++, info.getSpayeebankexchangeno());
			pstmt.setString(nIndex++, info.getSpayeebankcnapsno());
			pstmt.setString(nIndex++, info.getSpayeebankorgno());
			pstmt.setLong(nIndex++, info.getId());
			pstmt.execute();
			lReturn = info.getId();
		}
		catch (Exception sqle)
		{
			throw sqle;
		}
		finally
		{
			try
			{
				this.cleanup(pstmt);
				this.cleanup(conn);
			}
			catch (Exception e)
			{
				throw e;
			}
		}
		return lReturn;
	}
	private long getNextID() throws Exception
	{
		return getSett_ExternalAccountID();
	}
	/**
	 *判断是否是重复记录
	*/
	public boolean isRepetitiveRecord(ExternalAccountInfo queryInfo) throws Exception
	{
		boolean res = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT ID FROM \n");
		buffer.append(strTableName);
		buffer.append(" WHERE \n");
		//buffer.append(" ID = ").append(queryInfo.getId());
		buffer.append(" nOfficeID = ").append(queryInfo.getOfficeID());
		buffer.append(" and ncurrencyid = ").append(queryInfo.getNcurrencyID());
		if (queryInfo.getExtAcctNo() != null && !"".equals(queryInfo.getExtAcctNo()))
		{
			buffer.append(" AND sExtAcctNo = '").append(queryInfo.getExtAcctNo());
			buffer.append("'");
		}
		else
		{
			buffer.append(" AND sExtAcctNo IS NULL ");
		}
		if (queryInfo.getExtAcctName() != null && !"".equals(queryInfo.getExtAcctName()))
		{
			buffer.append(" AND sExtAcctName = '").append(queryInfo.getExtAcctName());
			buffer.append("'");
		}
		else
		{
			buffer.append(" AND sExtAcctName IS NULL ");
		}
		if (queryInfo.getBankName() != null && !"".equals(queryInfo.getBankName()))
		{
			buffer.append(" AND sBankName = '").append(queryInfo.getBankName());
			buffer.append("'");
		}
		else
		{
			buffer.append(" AND sBankName IS NULL ");
		}
		if (queryInfo.getProvince() != null && !"".equals(queryInfo.getProvince()))
		{
			buffer.append(" AND sProvince = '").append(queryInfo.getProvince());
			buffer.append("'");
		}
		else
		{
			buffer.append(" AND sProvince IS NULL ");
		}
		if (queryInfo.getCity() != null && !"".equals(queryInfo.getCity()))
		{
			buffer.append(" AND sCity = '").append(queryInfo.getCity());
			buffer.append("'");
		}
		else
		{
			buffer.append(" AND sCity IS NULL ");
		}
		try
		{
			log.info(buffer.toString());
			conn = this.getConnection();
			pstmt = conn.prepareStatement(buffer.toString());
			rset = pstmt.executeQuery();
			return rset.next();
		}
		catch (Exception sqle)
		{
			throw sqle;
		}
		finally
		{
			try
			{
                this.cleanup(rset);
                this.cleanup(pstmt);
				this.cleanup(conn);
			}
			catch (Exception e)
			{
				throw e;
			}
		}
	}
	
	
	/**
	 *  查找外部银行账户
	 * Create Date: 2006-09-26
	 * @param lUserID 用户ID
	 * @param lClientID 登录用户所属客户ID
	 * @param lCurrencyID  登录币种
	 * @param lISCPFAccount 是否是中油内部账户，参见常量
	 * @param strAccountNo 账户编号
	 * @param strAccountName 账户名称
	 * @return Collection
	 * @exception Exception
	 */
	public Collection queryPayee(ExternalAccountInfo info) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector vReturn = new Vector();
		
		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT * FROM sett_externalaccount");
			sbSQL.append(" WHERE  1=1");
			if(info.getOfficeID()!=-1)
			{
				sbSQL.append(" and nofficeid =" + info.getOfficeID());
			}
			if(info.getNcurrencyID()!=-1)
			{
				sbSQL.append(" and ncurrencyid =" + info.getNcurrencyID());
			}
			if (info.getExtAcctNo() != null&&info.getExtAcctNo().length()>0&&!info.getExtAcctNo().equals(""))
			{
			    sbSQL.append(" and SEXTACCTNO like '%"+info.getExtAcctNo()+"%'");
			}
			if (info.getExtAcctName() != null&&info.getExtAcctName().length()>0&&!info.getExtAcctName().equals(""))
			{
			    sbSQL.append(" and SEXTACCTNAME like '%"+info.getExtAcctName()+"%'");
			}
			sbSQL.append(" order by id");
	 
			Log.print(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();

			while (rs.next())
			{
				ExternalAccountInfo resultInfo = new ExternalAccountInfo();
				resultInfo.setId(rs.getLong("ID"));
				resultInfo.setOfficeID(rs.getLong("NOFFICEID"));
				resultInfo.setExtAcctNo(rs.getString("SEXTACCTNO"));
				resultInfo.setExtAcctName(rs.getString("SEXTACCTNAME"));
				resultInfo.setBankName(rs.getString("SBANKNAME"));
				resultInfo.setProvince(rs.getString("SPROVINCE"));
				resultInfo.setCity(rs.getString("SCITY"));
				resultInfo.setClientID(rs.getLong("NCLIENTID"));
				resultInfo.setSpayeebankcnapsno(rs.getString("spayeebankcnapsno"));
				resultInfo.setSpayeebankexchangeno(rs.getString("spayeebankexchangeno"));
				resultInfo.setSpayeebankorgno(rs.getString("spayeebankorgno"));

				
				vReturn.add(resultInfo);
				resultInfo = null;
			}

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
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
			throw new IException("Gen_E001");
		}
		finally
		{
			try
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
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				throw new IException("Gen_E001");
			}
		}
		return vReturn.size() > 0 ? vReturn : null;
	}
	
	/**
	 * 删除一条外部银行账户信息
	 * Create Date: 2006-09-26
	 * @param lId 外部银行账户ID
	 * @return long 大于0表示成功，小于,等于0表示保存失败
	 * @exception Exception
	 */
	public long deletePayee(long lId) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		int nIndex = 1;
		long lReturn = -1;
		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append("delete sett_externalaccount where ID = ?");
			Log.print(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			nIndex = 1;
			ps.setLong(nIndex++, lId);
			lReturn = ps.executeUpdate();

			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				throw new IException("Gen_E001");
			}
		}
		return lReturn;
	}
	
	/**
	 *  查找外部银行账户详细信息
	 * Create Date: 2006-09-26
	 * @param lID 外部银行账户ID
	 * @return Collection
	 * @exception Exception
	 */
	public ExternalAccountInfo findExtAcctByID(long lID) throws SQLException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ExternalAccountInfo resultInfo = new ExternalAccountInfo();
		
		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT * FROM sett_externalaccount");
			sbSQL.append(" WHERE  ID="+lID);
			
	 
			Log.print(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();

			while (rs.next())
			{
				
				resultInfo.setId(rs.getLong("ID"));
				resultInfo.setOfficeID(rs.getLong("NOFFICEID"));
				resultInfo.setExtAcctNo(rs.getString("SEXTACCTNO"));
				resultInfo.setExtAcctName(rs.getString("SEXTACCTNAME"));
				resultInfo.setBankName(rs.getString("SBANKNAME"));
				resultInfo.setProvince(rs.getString("SPROVINCE"));
				resultInfo.setCity(rs.getString("SCITY"));
				resultInfo.setClientID(rs.getLong("NCLIENTID"));
				resultInfo.setSpayeebankcnapsno(rs.getString("spayeebankcnapsno"));
				resultInfo.setSpayeebankexchangeno(rs.getString("spayeebankexchangeno"));
				resultInfo.setSpayeebankorgno(rs.getString("spayeebankorgno"));
			}

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
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
			throw (SQLException) e;
		}
		finally
		{
			try
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
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				throw (SQLException) e;
			}
		}
		return resultInfo != null ? resultInfo : null;
	}
	
	/**
	 *  查找外部银行账户
	 * Create Date: 2006-09-26
	 * @param lUserID 用户ID
	 * @param lClientID 登录用户所属客户ID
	 * @param lCurrencyID  登录币种
	 * @param strAccountNo 账户编号
	 * @param strAccountName 账户名称
	 * @return Collection
	 * @exception Exception
	 */
	public ExternalAccountInfo queryExternalBankAccount(ExternalAccountInfo info) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ExternalAccountInfo resultInfo = null;
		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT * FROM sett_externalaccount");
			sbSQL.append(" WHERE  1=1");
			if(info.getOfficeID()!=-1)
			{
				sbSQL.append(" and nofficeid =" + info.getOfficeID());
			}
			if(info.getNcurrencyID()!=-1)
			{
				sbSQL.append(" and ncurrencyid =" + info.getNcurrencyID());
			}
			if (info.getExtAcctNo() != null&&info.getExtAcctNo().length()>0&&!info.getExtAcctNo().equals(""))
			{
			    sbSQL.append(" and SEXTACCTNO like '%"+info.getExtAcctNo()+"%'");
			}
			if (info.getExtAcctName() != null&&info.getExtAcctName().length()>0&&!info.getExtAcctName().equals(""))
			{
			    sbSQL.append(" and SEXTACCTNAME like '%"+info.getExtAcctName()+"%'");
			}
			if (info.getBankName() != null&&info.getBankName().length()>0&&!info.getBankName().equals(""))
			{
				sbSQL.append(" and SBANKNAME like '%"+info.getBankName()+"%'");
			}
			sbSQL.append(" order by id desc ");
			Log.print(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();

			while (rs.next())
			{
				resultInfo = new ExternalAccountInfo();
				resultInfo.setId(rs.getLong("ID"));
				resultInfo.setOfficeID(rs.getLong("NOFFICEID"));
				resultInfo.setNcurrencyID(rs.getLong("NCURRENCYID"));
				resultInfo.setExtAcctNo(rs.getString("SEXTACCTNO"));
				resultInfo.setExtAcctName(rs.getString("SEXTACCTNAME"));
				resultInfo.setBankName(rs.getString("SBANKNAME"));
				resultInfo.setProvince(rs.getString("SPROVINCE"));
				resultInfo.setCity(rs.getString("SCITY"));
				resultInfo.setClientID(rs.getLong("NCLIENTID"));
				resultInfo.setSpayeebankcnapsno(rs.getString("spayeebankcnapsno"));
				resultInfo.setSpayeebankexchangeno(rs.getString("spayeebankexchangeno"));
				resultInfo.setSpayeebankorgno(rs.getString("spayeebankorgno"));
				break;
			}
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
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
			throw new IException("Gen_E001");
		}
		finally
		{
			try
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
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				throw new IException("Gen_E001");
			}
		}
		return resultInfo;
	}
	
	
    public String queryPayeeSql(ExternalAccountInfo info) {
		
    	StringBuffer sbSQL = new StringBuffer();
		sbSQL.append(" SELECT * FROM sett_externalaccount");
		sbSQL.append(" WHERE  1=1");
		if(info.getOfficeID()!=-1)
		{
			sbSQL.append(" and nofficeid =" + info.getOfficeID());
		}
		if(info.getNcurrencyID()!=-1)
		{
			sbSQL.append(" and ncurrencyid =" + info.getNcurrencyID());
		}
		if (info.getExtAcctNo() != null&&info.getExtAcctNo().length()>0&&!info.getExtAcctNo().equals(""))
		{
		    sbSQL.append(" and SEXTACCTNO like '%"+info.getExtAcctNo()+"%'");
		}
		if (info.getExtAcctName() != null&&info.getExtAcctName().length()>0&&!info.getExtAcctName().equals(""))
		{
		    sbSQL.append(" and SEXTACCTNAME like '%"+info.getExtAcctName()+"%'");
		}
		sbSQL.append(" order by id");
 
		Log.print(sbSQL.toString());
        
       String sql= sbSQL.toString();
       
		return sql;
		
	}
	
	
}
