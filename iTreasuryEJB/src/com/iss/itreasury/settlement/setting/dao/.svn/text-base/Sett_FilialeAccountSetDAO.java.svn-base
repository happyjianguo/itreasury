package com.iss.itreasury.settlement.setting.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.setting.dataentity.FilialeAccountInfo;
import com.iss.itreasury.settlement.setting.dataentity.OfficeCurrencytInfo;
import com.iss.itreasury.settlement.setting.dataentity.QueryFilialeAccountInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;

/**
 * @author yiwang
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class Sett_FilialeAccountSetDAO extends SettlementDAO
{
	public final static int ORDERBY_FILIALENAME = 1;
	public final static int ORDERBY_ACCOUNTTYPE = 2;

	/**
	 * Constructor for Sett_BranchDAO.
	 * 
	 * @param conn
	 */
	public Sett_FilialeAccountSetDAO(Connection conn)
	{
		super(conn);
		this.strTableName = "Sett_bankAccountOfFiliale";
	}

	/**
	 *  
	 */
	public Sett_FilialeAccountSetDAO()
	{
		super();
		this.strTableName = "Sett_bankAccountOfFiliale";
	}

	public long add(FilialeAccountInfo info) throws Exception
	{
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		StringBuffer buffer = new StringBuffer();
		buffer.append(" insert into \n");
		buffer.append(strTableName);
		buffer.append(" \n (ID, \n");
		buffer.append("SFILIALENAME,\n");
		buffer.append("SBANKACCOUNTNO,\n");
		buffer.append("SBANKACCOUNTNAME,\n");
		buffer.append("NACCOUNTTYPE,\n");
		buffer.append("SBRANCHNAME,\n");
		buffer.append("NBNKTYPE,\n");
		buffer.append("NCURRENCYID,\n");
		buffer.append("NCLIENTID,\n");
		buffer.append("NWITHINACCOUNTID,\n");
		buffer.append("NUPBANKACCOUNTID,\n");
		buffer.append("SNAMEOFPROVINCE,\n");
		buffer.append("SNAMEOFCITY,\n");
		buffer.append("NUPCLIENTID,\n");
		buffer.append("NOFFICEID,\n");
		buffer.append("SBANKEXCHANGECODE,\n");   //新增联行号
		buffer.append("SBRANCHCODE, \n");  //新增机构号
		buffer.append("NISKEEPACCOUNTS) \n"); //是否需要财司记账

		try
		{
			conn = this.getConnection();

			buffer.append(" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)\n");

			log.info(buffer.toString());

			pstmt = conn.prepareStatement(buffer.toString());
			long id = this.getMaxID();
			info.setID(id);
			int nIndex = 1;
			pstmt.setLong(nIndex++, info.getID());
			pstmt.setString(nIndex++, info.getFilialeName());
			pstmt.setString(nIndex++, info.getBankAccountNo());
			pstmt.setString(nIndex++, info.getBankAccountName());
			pstmt.setLong(nIndex++, info.getAccountType());
			pstmt.setString(nIndex++, info.getBranchName());
			pstmt.setLong(nIndex++, info.getBankType());
			pstmt.setLong(nIndex++, info.getCurrencyID());
			pstmt.setLong(nIndex++, info.getClientID());
			pstmt.setLong(nIndex++, info.getWithinAccountID());
			pstmt.setLong(nIndex++, info.getUpBankAccountID());
			pstmt.setString(nIndex++, info.getNameOfProvince());
			pstmt.setString(nIndex++, info.getNameOfCity());
			pstmt.setLong(nIndex++, info.getUpClientID());
			pstmt.setLong(nIndex++, info.getOfficeID());
			pstmt.setString(nIndex++, info.getBankExchangeCode());//新增联行号
			pstmt.setString(nIndex++, info.getBranchCodeOfBank());//新增机构号
			pstmt.setLong(nIndex++, info.getIsKeepAccounts());//新增是否需要财司记账

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

	public void update(FilialeAccountInfo info) throws Exception
	{
		Connection conn = null;
		PreparedStatement pstmt = null;

		StringBuffer buffer = new StringBuffer();
		buffer.append("update " + strTableName + " set \n");

		buffer.append("SFILIALENAME = ?, \n");
		buffer.append("SBANKACCOUNTNO = ?, \n");
		buffer.append("SBANKACCOUNTNAME = ?, \n");
		buffer.append("NACCOUNTTYPE = ?, \n");
		buffer.append("SBRANCHNAME = ?, \n");
		buffer.append("NBNKTYPE = ?, \n");
		buffer.append("NCURRENCYID = ?, \n");
		buffer.append("NCLIENTID = ?, \n");
		buffer.append("NWITHINACCOUNTID = ?, \n");
		buffer.append("NUPBANKACCOUNTID = ?, \n");
		buffer.append("SNAMEOFPROVINCE = ?, \n");
		buffer.append("SNAMEOFCITY = ?, \n");
		buffer.append("NUPCLIENTID = ?, \n");
		buffer.append("NOFFICEID = ?, \n");
		buffer.append("SBANKEXCHANGECODE = ?, \n");//新增联行号
		buffer.append("SBRANCHCODE = ?, \n");//新增机构号
		buffer.append("NISKEEPACCOUNTS = ? \n");//新增是否需要财司记账

		buffer.append("where ID=?\n");

		log.debug(buffer.toString());
		try
		{
			conn = this.getConnection();
			pstmt = conn.prepareStatement(buffer.toString());

			int nIndex = 1;

			pstmt.setString(nIndex++, info.getFilialeName());
			pstmt.setString(nIndex++, info.getBankAccountNo());
			pstmt.setString(nIndex++, info.getBankAccountName());
			pstmt.setLong(nIndex++, info.getAccountType());
			pstmt.setString(nIndex++, info.getBranchName());
			pstmt.setLong(nIndex++, info.getBankType());
			pstmt.setLong(nIndex++, info.getCurrencyID());
			pstmt.setLong(nIndex++, info.getClientID());
			pstmt.setLong(nIndex++, info.getWithinAccountID());
			pstmt.setLong(nIndex++, info.getUpBankAccountID());
			pstmt.setString(nIndex++, info.getNameOfProvince());
			pstmt.setString(nIndex++, info.getNameOfCity());
			pstmt.setLong(nIndex++, info.getUpClientID());
			pstmt.setLong(nIndex++, info.getOfficeID());
			pstmt.setString(nIndex++, info.getBankExchangeCode());//新增联行号
			pstmt.setString(nIndex++, info.getBranchCodeOfBank());//新增机构号
			pstmt.setLong(nIndex++, info.getIsKeepAccounts());//新增是否需要财司记账

			pstmt.setLong(nIndex++, info.getID());

			pstmt.execute();

		}
		finally
		{
			this.cleanup(pstmt);
			this.cleanup(conn);

		}
	}

	/**
	 * 方法说明：根据账户ID，得到账户信息
	 * 
	 * @param lID
	 * @return FilialeAccountInfo
	 * @throws Exception
	 */
	public FilialeAccountInfo findByID(long lID) throws Exception
	{
		FilialeAccountInfo ai = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from Sett_bankAccountOfFiliale ");
			sbSQL.append(" where ID = ? ");
			log.info(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//get the BankBillInfo from current ResultSet object
				ai = new FilialeAccountInfo();
				getInfoFromResultSet(ai, rs);
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
		return ai != null ? ai : null;
	}

	/**
	 * 方法说明：根据条件取得电厂账户设置的信息
	 * 
	 * @param qfInfo
	 *            QueryFilialeAccountInfo
	 * @return Vector（FilialeAccountInfo）
	 * @throws Exception
	 */
	public Vector findByCondition(QueryFilialeAccountInfo qfInfo)
		throws Exception
	{
		Vector v = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(
				" select a.*,b.saccountno withinAccountNo from Sett_bankAccountOfFiliale a,sett_account b where 1 = 1 ");
			sbSQL.append(" and a.nWithinAccountID = b.id(+) ");
			if (qfInfo.getID() > 0)
			{
				sbSQL.append(" and a.ID = " + qfInfo.getID());
			}
			if (qfInfo.getBankAccountNo() != null
				&& qfInfo.getBankAccountNo().length() > 0)
			{
				sbSQL.append(
					" and a.SBANKACCOUNTNO = '"
						+ qfInfo.getBankAccountNo()
						+ "'");
			}
			if (qfInfo.getClientID() > 0)
			{
				sbSQL.append(" and a.NCLIENTID = " + qfInfo.getClientID());
			}
			if (qfInfo.getBankType() > 0)
			{
				sbSQL.append(" and a.NBNKTYPE = " + qfInfo.getBankType());
			}
			if (qfInfo.getAccountType() > 0)
			{
				sbSQL.append(" and a.NACCOUNTTYPE = " + qfInfo.getAccountType());
			}
			if (qfInfo.getWithinAccountID() > 0)
			{
				sbSQL.append(
					" and a.NWITHINACCOUNTID = " + qfInfo.getWithinAccountID());
			}
			if (qfInfo.getUpBankAccountID() > 0)
			{
				sbSQL.append(
					" and a.NUPBANKACCOUNTID = " + qfInfo.getUpBankAccountID());

			}
			if (qfInfo.getNameOfProvince() != null
				&& qfInfo.getNameOfProvince().length() > 0)
			{
				sbSQL.append(
					" and a.SNAMEOFPROVINCE = '"
						+ qfInfo.getNameOfProvince()
						+ "'");
			}
			if (qfInfo.getNameOfCity() != null
				&& qfInfo.getNameOfCity().length() > 0)
			{
				sbSQL.append(
					" and a.SNAMEOFCITY = '" + qfInfo.getNameOfCity() + "'");
			}
			if (qfInfo.getOfficeID() > 0)
			{
				sbSQL.append(" and a.NOFFICEID = " + qfInfo.getOfficeID());
			}

			switch ((int) qfInfo.getOrderValue())
			{
				case (int) ORDERBY_FILIALENAME :
					sbSQL.append(" order by a.SFILIALENAME ");
					break;
				case (int) ORDERBY_ACCOUNTTYPE :
					sbSQL.append(" order by a.NACCOUNTTYPE ");
					break;
				default :
					sbSQL.append(" order by a.ID ");
					break;
			}
			if (qfInfo.getDescValue()
				== Constant.PageControl.CODE_ASCORDESC_ASC)
				sbSQL.append(" ASC ");
			else if (
				qfInfo.getDescValue()
					== Constant.PageControl.CODE_ASCORDESC_DESC)
				sbSQL.append(" DESC ");

			log.info(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				FilialeAccountInfo fi = new FilialeAccountInfo();
				getInfoFromResultSet(fi, rs);
				//因为不通用,所以这里单独给withinAccountNo赋值
				fi.setWithinAccountNo(rs.getString("WITHINACCOUNTNO"));				
				v.add(fi);
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

	private void getInfoFromResultSet(FilialeAccountInfo fi, ResultSet rs)
		throws Exception
	{
		try
		{
			fi.setID(rs.getLong("ID")); // ID
			fi.setFilialeName(rs.getString("SFILIALENAME")); // 
			fi.setBankAccountNo(rs.getString("SBANKACCOUNTNO")); //
			fi.setBankAccountName(rs.getString("SBANKACCOUNTNAME")); // 
			fi.setAccountType(rs.getLong("NACCOUNTTYPE"));
			fi.setBranchName(rs.getString("SBRANCHNAME")); // 
			fi.setBankType(rs.getLong("NBNKTYPE")); // 
			fi.setClientID(rs.getLong("NCLIENTID"));
			fi.setCurrencyID(rs.getLong("NCURRENCYID"));
			fi.setWithinAccountID(rs.getLong("NWITHINACCOUNTID"));
			fi.setUpBankAccountID(rs.getLong("NUPBANKACCOUNTID"));
			fi.setNameOfProvince(rs.getString("SNAMEOFPROVINCE"));
			fi.setNameOfCity(rs.getString("SNAMEOFCITY"));
			//fi.setNameOfCity(rs.getString("NOFFICEID"));
			fi.setOfficeID(rs.getLong("NOFFICEID"));
			fi.setUpClientID(rs.getLong("NUPCLIENTID"));
			fi.setBankExchangeCode(rs.getString("SBANKEXCHANGECODE"));//联行号
			fi.setBranchCodeOfBank(rs.getString("SBRANCHCODE"));//机构号
			fi.setIsKeepAccounts(rs.getLong("NISKEEPACCOUNTS"));//是否需要财司记账
		}
		catch (Exception se)
		{
			throw se;
		}
	}

	/**
	 * 获取最大ID 创建日期：(2002-2-5 18:35:09)
	 * 
	 * @return long
	 * @param lOfficeID
	 *            long
	 * @exception Exception
	 *                异常说明。
	 */
	public long getMaxID() throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		long lResult = -1;

		StringBuffer sb = new StringBuffer();

		try
		{
			con = getConnection();
			// 如果有跳过的scode，通过下面方sql获取被跳过的scode；如果没有跳过的scode，数据库查询结果返回0。
			sb.append(" select nvl(max(id),0)+1 id \n");
			sb.append(" from Sett_bankAccountOfFiliale  ");

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

	/**
	 * 删除电厂账户设置信息 操作数据库表Sett_bankAccountOfFiliale 物理删除
	 * 
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @return long
	 * @exception Exception
	 */
	public long delete(long lID) throws Exception
	{
		long lResult = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String removeString = null;

		try
		{
			con = getConnection();

			removeString =
				" delete  from Sett_bankAccountOfFiliale  WHERE ID =?       ";

			ps = con.prepareStatement(removeString);

			ps.setLong(1, lID);

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

	/**
	 * 方法说明：根据账户ID，得到账户信息
	 * 
	 * @param lID
	 * @return FilialeAccountInfo
	 * @throws Exception
	 */
	public long verifyByInfo(FilialeAccountInfo info) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		int nindex = 1;
		long lReturn = -1;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select count(*)  from Sett_bankAccountOfFiliale ");

			sbSQL.append(" where sbankaccountno = ? ");
			//如果是修改
			if (info.getID() > 0)
			{
				sbSQL.append(" and id != ").append(info.getID());
			}
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setString(nindex++, info.getBankAccountNo());

			/*
			 * sbSQL.append(" where SFILIALENAME = ? and SBANKACCOUNTNO = ? and
			 * SBANKACCOUNTNAME = ? and SBRANCHNAME = ? and NBNKTYPE = ? ");
			 * if(info.getAccountType() > 0) sbSQL.append(" and NACCOUNTTYPE = ?
			 * "); if(info.getClientID() > 0) sbSQL.append(" and NCLIENTID = ?
			 * "); if(info.getCurrencyID() > 0) sbSQL.append(" and NCURRENCYID = ?
			 * "); if(info.getID() > 0) sbSQL.append(" and ID != ? "); if
			 * (info.getWithinAccountID() >0) { sbSQL.append(" and
			 * NWITHINACCOUNTID = ?" ); } if (info.getUpBankAccountID() > 0) {
			 * sbSQL.append(" and NUPBANKACCOUNTID =? "); } if
			 * (info.getNameOfProvince() != null &&
			 * info.getNameOfProvince().length() > 0 ) { sbSQL.append(" and
			 * SNAMEOFPROVINCE =?"); } if (info.getNameOfCity() != null &&
			 * info.getNameOfCity().length() > 0 ) { sbSQL.append(" and
			 * SNAMEOFCITY = ?"); } if (info.getOfficeID() > 0) { sbSQL.append("
			 * and NOFFICEID = ?"); } log.info(sbSQL.toString());
			 * 
			 * ps = conn.prepareStatement(sbSQL.toString());
			 * ps.setString(nindex++, info.getFilialeName());
			 * ps.setString(nindex++, info.getBankAccountNo());
			 * ps.setString(nindex++, info.getBankAccountName());
			 * ps.setString(nindex++, info.getBranchName());
			 * ps.setLong(nindex++, info.getBankType());
			 * if(info.getAccountType() > 0) ps.setLong(nindex++,
			 * info.getAccountType()); if(info.getClientID() > 0)
			 * ps.setLong(nindex++, info.getClientID()); if(info.getCurrencyID() >
			 * 0) ps.setLong(nindex++, info.getCurrencyID()); if(info.getID() >
			 * 0) ps.setLong(nindex++ , info.getID()); if
			 * (info.getWithinAccountID() >0) { ps.setLong(nindex++ ,
			 * info.getWithinAccountID()); } if (info.getUpBankAccountID() > 0) {
			 * ps.setLong(nindex++ , info.getUpBankAccountID()); } if
			 * (info.getNameOfProvince() != null &&
			 * info.getNameOfProvince().length() > 0 ) { ps.setString(nindex++ ,
			 * info.getNameOfProvince()); } if (info.getNameOfCity() != null &&
			 * info.getNameOfCity().length() > 0 ) { ps.setString(nindex++ ,
			 * info.getNameOfCity()); } if (info.getOfficeID() > 0) {
			 * ps.setLong(nindex++ , info.getOfficeID()); }
			 */

			rs = ps.executeQuery();
			if (rs.next())
			{
				lReturn = rs.getLong(1);
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

	/**
	 * 根据内部账户的id查询对应的客户在银行开设的结算账户 accountId: 内部账户的id 返回客户在银行开设的结算账户的对象数组
	 */
	public FilialeAccountInfo[] findRefFilialeAccountInfoBySettAccountId(long accountId)
		throws Exception
	{
		FilialeAccountInfo ai;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		Vector v = new Vector();
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from Sett_bankAccountOfFiliale ");
			sbSQL.append(" where NWITHINACCOUNTID = ? ");
			log.info(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, accountId);
			rs = ps.executeQuery();
			while (rs.next())
			{
				//get the BankBillInfo from current ResultSet object
				ai = new FilialeAccountInfo();
				getInfoFromResultSet(ai, rs);
				v.add(ai);
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
		if (v == null || v.size() == 0)
		{
			return null;
		}
		else
		{
			int size = v.size();
			FilialeAccountInfo[] infos = new FilialeAccountInfo[size];
			for (int i = 0; i < size; i++)
			{
				infos[i] = (FilialeAccountInfo) v.elementAt(i);
			}
			return infos;
		}
	}

	/**
	 * 根据客户在银行开设的结算账户查询财务公司在银行开设的结算账户 bankAccountNo:客户在银行开设的结算账户
	 * 返回财务公司在银行开设的结算账户的id
	 * 
	 * @throws SQLException
	 */
	public long findRefBranchInfoByBankAccountNo(String bankAccountNo)
		throws Exception
	{
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from Sett_bankAccountOfFiliale ");
			sbSQL.append(" where SBANKACCOUNTNO = ? ");
			log.info(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setString(1, bankAccountNo);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lReturn = rs.getLong("NUPBANKACCOUNTID");
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

	/**
	 * 2006.03.07日修改，在自动任务运行时，连接有可能丢失
	 * 在findRefBranchInfoByBankAccountNo方法中增加参数 Connection conn
	 * 
	 */
	public long findRefBranchInfoByBankAccountNo(String bankAccountNo , Connection conn )
		throws Exception
	{
		long lReturn = -1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		if(conn == null)
		{
			log.info("传入连接为空，创建新的连接");
			conn = Database.getConnection();
		}
		try
		{
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from Sett_bankAccountOfFiliale ");
			sbSQL.append(" where SBANKACCOUNTNO = ? ");
			log.info(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setString(1, bankAccountNo);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lReturn = rs.getLong("NUPBANKACCOUNTID");
			}
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			try
			{
				if(conn != null && conn.isClosed() == false)
				{
					conn.close();
					conn = null ;
				}
			}
			catch(SQLException sqle)
			{
				Log.print(sqle.toString());
			}
			
		}
		return lReturn;
	}
	
	/***************************************************************************
	 * 根据客户在银行开设的结算账户查询客户在财务公司开设的内部结算账户的id bankAccountNo：客户在银行开设的结算账户
	 * 返回客户在财务公司开设的内部结算账户的id
	 */
	public long findRefSettAccountIdByBankAccountNo(String bankAccountNo)
		throws Exception
	{
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from Sett_bankAccountOfFiliale ");
			sbSQL.append(" where SBANKACCOUNTNO = ? ");
			log.info(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setString(1, bankAccountNo);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lReturn = rs.getLong("NWITHINACCOUNTID");
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

	/***************************************************************************
	 * 查询根据输入的银行类型，查询所有符合条件的银行账户
	 * 
	 * @param long
	 *            nBankType 银行类型
	 * @return FilialeAccountInfo[] 银行账户的类型
	 */
	public FilialeAccountInfo[] findAll(long nBankType) throws Exception
	{

		FilialeAccountInfo ai;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		Vector v = new Vector();
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from Sett_bankAccountOfFiliale ");
			sbSQL.append(" where NBNKTYPE = ? ");
			log.info(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, nBankType);
			rs = ps.executeQuery();
			while (rs.next())
			{
				//get the BankBillInfo from current ResultSet object
				ai = new FilialeAccountInfo();
				getInfoFromResultSet(ai, rs);
				v.add(ai);
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
		if (v == null || v.size() == 0)
		{
			return null;
		}
		else
		{
			int size = v.size();
			FilialeAccountInfo[] infos = new FilialeAccountInfo[size];
			for (int i = 0; i < size; i++)
			{
				infos[i] = (FilialeAccountInfo) v.elementAt(i);
			}
			return infos;
		}
	}

	/**
	 * 获取表sett_bankaccountoffiliale和sett_branch中的所有办事处和币种编号。
	 * 
	 * @return OfficeCurrencytInfo[]
	 * @throws Exception
	 */
	public OfficeCurrencytInfo[] findOfficeAndCurrencyId(long bankType) throws Exception
	{
		OfficeCurrencytInfo ai;
		Connection conn = null;
		PreparedStatement ps = null;
		Statement vs = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		Vector v = new Vector();
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append("select distinct NOFFICEID,NCURRENCYID from Sett_bankAccountOfFiliale \n");
			sbSQL.append("where 1 = 1 \n");
			if (bankType > 0) {
				sbSQL.append("	and nBnkType = "+bankType+" \n");
			}
			sbSQL.append(" union \n");
			sbSQL.append("select distinct NOFFICEID,NCURRENCYID from Sett_branch \n");
			sbSQL.append("where 1 = 1 \n");
			if (bankType > 0) {
				sbSQL.append("	and nBankType = "+bankType+" \n");
			} 
			log.info(sbSQL.toString());
			vs = conn.createStatement();
			rs = vs.executeQuery(sbSQL.toString());
			//ps = conn.prepareStatement(sbSQL.toString());
			//rs = ps.executeQuery();
			while (rs.next())
			{
				//get the BankBillInfo from current ResultSet object
				ai = new OfficeCurrencytInfo();
				ai.setCurrencyId(rs.getLong("NCURRENCYID"));
				ai.setOfficeId(rs.getLong("NOFFICEID"));
				v.add(ai);
			}
			cleanup(rs);
			cleanup(vs);
			cleanup(conn);
		}
		finally
		{
			cleanup(rs);
			cleanup(vs);
			cleanup(conn);
		}
		if (v == null || v.size() == 0)
		{
			return null;
		}
		else
		{
			int size = v.size();
			OfficeCurrencytInfo[] infos = new OfficeCurrencytInfo[size];
			for (int i = 0; i < size; i++)
			{
				infos[i] = (OfficeCurrencytInfo) v.elementAt(i);
			}
			return infos;
		}
	}
	
	/**
	 * 方法说明：根据银行账号和类型，得到账户信息
	 * @param accountNo 银行账号 
	 * @param bankType 银行类型
	 * @return FilialeAccountInfo
	 * @throws Exception
	 */
	public FilialeAccountInfo findByAccountNo(String accountNo, long bankType) throws Exception{
		FilialeAccountInfo ai = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from Sett_bankAccountOfFiliale ");
			sbSQL.append(" where SBANKACCOUNTNO = ? and NBNKTYPE = ? ");
			log.info(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setString(1,accountNo);
			ps.setLong(2, bankType);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//get the BankBillInfo from current ResultSet object
				ai = new FilialeAccountInfo();
				getInfoFromResultSet(ai, rs);
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
		
		return ai != null ? ai : null;		
	}

}