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
		buffer.append("SBANKEXCHANGECODE,\n");   //�������к�
		buffer.append("SBRANCHCODE, \n");  //����������
		buffer.append("NISKEEPACCOUNTS) \n"); //�Ƿ���Ҫ��˾����

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
			pstmt.setString(nIndex++, info.getBankExchangeCode());//�������к�
			pstmt.setString(nIndex++, info.getBranchCodeOfBank());//����������
			pstmt.setLong(nIndex++, info.getIsKeepAccounts());//�����Ƿ���Ҫ��˾����

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
		buffer.append("SBANKEXCHANGECODE = ?, \n");//�������к�
		buffer.append("SBRANCHCODE = ?, \n");//����������
		buffer.append("NISKEEPACCOUNTS = ? \n");//�����Ƿ���Ҫ��˾����

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
			pstmt.setString(nIndex++, info.getBankExchangeCode());//�������к�
			pstmt.setString(nIndex++, info.getBranchCodeOfBank());//����������
			pstmt.setLong(nIndex++, info.getIsKeepAccounts());//�����Ƿ���Ҫ��˾����

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
	 * ����˵���������˻�ID���õ��˻���Ϣ
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
	 * ����˵������������ȡ�õ糧�˻����õ���Ϣ
	 * 
	 * @param qfInfo
	 *            QueryFilialeAccountInfo
	 * @return Vector��FilialeAccountInfo��
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
				//��Ϊ��ͨ��,�������ﵥ����withinAccountNo��ֵ
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
			fi.setBankExchangeCode(rs.getString("SBANKEXCHANGECODE"));//���к�
			fi.setBranchCodeOfBank(rs.getString("SBRANCHCODE"));//������
			fi.setIsKeepAccounts(rs.getLong("NISKEEPACCOUNTS"));//�Ƿ���Ҫ��˾����
		}
		catch (Exception se)
		{
			throw se;
		}
	}

	/**
	 * ��ȡ���ID �������ڣ�(2002-2-5 18:35:09)
	 * 
	 * @return long
	 * @param lOfficeID
	 *            long
	 * @exception Exception
	 *                �쳣˵����
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
			// �����������scode��ͨ�����淽sql��ȡ��������scode�����û��������scode�����ݿ��ѯ�������0��
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
	 * ɾ���糧�˻�������Ϣ �������ݿ��Sett_bankAccountOfFiliale ����ɾ��
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
	 * ����˵���������˻�ID���õ��˻���Ϣ
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
			//������޸�
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
	 * �����ڲ��˻���id��ѯ��Ӧ�Ŀͻ������п���Ľ����˻� accountId: �ڲ��˻���id ���ؿͻ������п���Ľ����˻��Ķ�������
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
	 * ���ݿͻ������п���Ľ����˻���ѯ����˾�����п���Ľ����˻� bankAccountNo:�ͻ������п���Ľ����˻�
	 * ���ز���˾�����п���Ľ����˻���id
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
	 * 2006.03.07���޸ģ����Զ���������ʱ�������п��ܶ�ʧ
	 * ��findRefBranchInfoByBankAccountNo���������Ӳ��� Connection conn
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
			log.info("��������Ϊ�գ������µ�����");
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
	 * ���ݿͻ������п���Ľ����˻���ѯ�ͻ��ڲ���˾������ڲ������˻���id bankAccountNo���ͻ������п���Ľ����˻�
	 * ���ؿͻ��ڲ���˾������ڲ������˻���id
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
	 * ��ѯ����������������ͣ���ѯ���з��������������˻�
	 * 
	 * @param long
	 *            nBankType ��������
	 * @return FilialeAccountInfo[] �����˻�������
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
	 * ��ȡ��sett_bankaccountoffiliale��sett_branch�е����а��´��ͱ��ֱ�š�
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
	 * ����˵�������������˺ź����ͣ��õ��˻���Ϣ
	 * @param accountNo �����˺� 
	 * @param bankType ��������
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