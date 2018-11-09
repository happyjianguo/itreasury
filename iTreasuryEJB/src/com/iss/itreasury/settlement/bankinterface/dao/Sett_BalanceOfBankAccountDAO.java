package com.iss.itreasury.settlement.bankinterface.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.bankinterface.dataentity.BankAccountBalanceInfo;
import com.iss.itreasury.util.DataFormat;

/**
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class Sett_BalanceOfBankAccountDAO extends SettlementDAO
{

	/**
	 * Constructor for Sett_BalanceOfBankAccountDAO.
	 * @param conn
	 */
	public Sett_BalanceOfBankAccountDAO(Connection conn)
	{
		super(conn);
		this.strTableName = "SETT_BALANCEOFBANKACCOUNT";
	}

	/**
	 * Constructor for Sett_BalanceOfBankAccountDAO.
	 */
	public Sett_BalanceOfBankAccountDAO()
	{
		super();
		this.strTableName = "SETT_BALANCEOFBANKACCOUNT";
	}

	/**
	 * 添加一条记录
	 * @param info
	 * @return long
	 * @throws Exception
	 */
	public void add(BankAccountBalanceInfo info) throws Exception
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		StringBuffer buffer = new StringBuffer();
		buffer.append(" insert into \n");
		buffer.append(strTableName);
		buffer.append(" \n (SBANKACCOUNTNO, \n");
		buffer.append("MBALANCE,\n");
		buffer.append("DTCURRENT,\n");
		buffer.append("NBANKTYPE)\n");

		try
		{
			conn = this.getConnection();

			buffer.append(" values(?,?,?,?)\n");

			log.info(buffer.toString());

			pstmt = conn.prepareStatement(buffer.toString());

			int nIndex = 1;
			pstmt.setString(nIndex++, info.getBankAccountNo());
			pstmt.setDouble(nIndex++, info.getBalance());
			pstmt.setDate(nIndex++, info.getCurrentDate());
			pstmt.setLong(nIndex++, info.getBankType());

			pstmt.execute();
		}
		finally
		{
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
	}
	
	/**
	 * 查询指定账户，某一天的日终余额。
	 * @param accountNo
	 * @param date
	 * @return BankAccontBalanceInfo
	 * @throws Exception
	 */
	public BankAccountBalanceInfo findByAccountAndDate(String accountNo, Date date) throws Exception
	{
		BankAccountBalanceInfo ai = null;
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
			sbSQL.append(" select * from " + strTableName);
			sbSQL.append(" where SBANKACCOUNTNO = ?");
			sbSQL.append(" and DTCURRENT = ? ");
			
			log.info(sbSQL.toString());
			
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setString(1, accountNo);
			ps.setDate(2, date);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//get the BankAccontBalanceInfo from current ResultSet object
				ai = new BankAccountBalanceInfo();
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
	 * 查询指定账户，历史的日终余额。
	 * @param accountNo
	 * @param date
	 * @return BankAccontBalanceInfo
	 * @throws Exception
	 */
	public Collection findHistoryBalance(String accountNo, long lBankType, Date dateStart, Date dateEnd) throws Exception
	{
		BankAccountBalanceInfo ai = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		Vector v = new Vector();
		int nIndex = 0;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from " + strTableName);
			sbSQL.append(" where SBANKACCOUNTNO = ?");
			sbSQL.append(" and NBANKTYPE = ?");
			if(dateStart!=null)
				sbSQL.append(" and DTCURRENT >= ?");
			if(dateEnd!=null)
				sbSQL.append(" and DTCURRENT <= ?");
			
			log.info(sbSQL.toString());
			
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setString(++nIndex, accountNo);
			ps.setLong(++nIndex, lBankType);
			if(dateStart!=null)
				ps.setDate(++nIndex, dateStart);
			if(dateEnd!=null)
				ps.setDate(++nIndex, dateEnd);
			rs = ps.executeQuery();
			while (rs.next())
			{
				//get the BankAccontBalanceInfo from current ResultSet object
				ai = new BankAccountBalanceInfo();
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
		//System.out.println("------------v.size = "+v.size());
		return v != null ? v : null;
	}
	
	/**
	 * 查询指定账户最后一天的日终余额。
	 * @param accountNo
	 * @return BankAccontBalanceInfo
	 * @throws Exception
	 */
	public BankAccountBalanceInfo findLastByAccount(String accountNo) throws Exception
	{
		BankAccountBalanceInfo ai = null;
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
			sbSQL.append(" select * from " + strTableName);
			sbSQL.append(" where SBANKACCOUNTNO = ?");
			sbSQL.append(" and DTCURRENT = ( ");
			sbSQL.append(" select max(DTCURRENT) from "+ strTableName);
			sbSQL.append(" where SBANKACCOUNTNO = ?");
			sbSQL.append(" ) ");
			
			log.info(sbSQL.toString());
			
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setString(1, accountNo);
			ps.setString(2, accountNo);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//get the BankAccontBalanceInfo from current ResultSet object
				ai = new BankAccountBalanceInfo();
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
	 * 删除指定账户、指定时间之后(包括指定时间)的所有记录
	 * @param accountNo
	 * @param startDate
	 * @throws Exception
	 */
	public void delete(String accountNo, Date startDate)throws Exception
	{
		Connection conn = null;
		Statement ps = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" delete " + strTableName);
			sbSQL.append(" where SBANKACCOUNTNO = '");
			sbSQL.append(accountNo);
			sbSQL.append("' and DTCURRENT = to_date('"
					+DataFormat.formatDate(new Timestamp(startDate.getTime()))
					+"','yyyy-mm-dd') ");
			
			log.info(sbSQL.toString());
			
			ps = conn.createStatement();

			ps.executeUpdate(sbSQL.toString());
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
	}
	
	private void getInfoFromResultSet(BankAccountBalanceInfo bi, ResultSet rs)
	throws Exception
	{
		try
		{
			bi.setBankAccountNo(rs.getString("SBANKACCOUNTNO")); // 账户号
			bi.setBalance(rs.getDouble("MBALANCE")); // 成功导入的记录数
			bi.setCurrentDate(rs.getDate("DTCURRENT")); //银行类型
			bi.setBankType(rs.getLong("NBANKTYPE")); // 
		}
		catch (Exception se)
		{
			throw se;
		}
	}
	
	public static void main(String[] args)
	{
		try
		{
//			BankAccontBalanceInfo condition = new BankAccontBalanceInfo();
//
//			condition.setBankAccountNo("0200004519000100297");
//			condition.setBalance(100.00);
//			condition.setCurrentDate(new Date(104,3,21));
//			condition.setBankType(2);
//
			Sett_BalanceOfBankAccountDAO dao = new Sett_BalanceOfBankAccountDAO();
			Collection c = dao.findHistoryBalance("0200004519000100297",2,null,null);
			System.out.println("========= "+dao.findHistoryBalance("0200004519000100297",2,null,null).size());
//			dao
//			dao.add(condition);
//			dao.delete("0005", new Date(2001,4,28));
			
//			BankAccontBalanceInfo bai = dao.findByAccountAndDate("0005",new Date(2001,4,28));
//			
//			System.out.println("\nAccountNO:"+bai.getBankAccountNo()+" Date:"+bai.getCurrentDate());
			System.out.println("========= *success* =========");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
