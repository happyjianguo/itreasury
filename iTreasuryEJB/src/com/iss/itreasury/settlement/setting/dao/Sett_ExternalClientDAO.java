/*
 * Created on 2003-12-4
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.setting.dao;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.settlement.setting.dataentity.ExtClientSetInfo;
import com.iss.itreasury.settlement.account.bizlogic.AccountOperation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.iss.itreasury.util.Constant;
import java.util.Collection;
import com.iss.itreasury.util.Log;
import java.util.ArrayList;

/**
 * @author yfsu
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Sett_ExternalClientDAO extends SettlementDAO
{
	public Sett_ExternalClientDAO()
	{
		super.strTableName = "Sett_ExternalClient";
	}

	public static void main(String[] args)
	{

	}
	/**
	 * 方法说明：新增贴现发放交易记录
	 * @param info:Sett_TransCurrentDepositInfo
	 * @return : long - 返回新增贴现发放交易记录ID
	 * @throws IException
	 */
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	/**
	 * 方法说明：新增贴现发放交易记录
	 * @param info:Sett_TransCurrentDepositInfo
	 * @return : long - 返回新增贴现发放交易记录ID
	 * @throws IException
	 */
	//新增一条记录
	public long add(ExtClientSetInfo info) throws Exception
	{
		long lRtn = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		AccountOperation accountOperation = new AccountOperation();
		try
		{
			if (this.checkAccountNo(info.getSExtAccountNo()) == 0) {
				String sCode = this.createNewCode();
				conn = this.getConnection();
				StringBuffer buffer = new StringBuffer();
				buffer.append("insert into " + this.strTableName + " ");
				buffer.append("(ID,nOfficeID,sName,sCode,nParentClientID1,nParentClientID2,");
				buffer.append("nStatusID,sExtAccountNo)values");
				buffer.append("(");
				buffer.append("?,?,?,?,?,?,?,?"); //
				buffer.append(")");
				String sql = buffer.toString();

				ps = conn.prepareStatement(sql);
				int index = 1;
				ps.setLong(index++, info.getID());
				ps.setLong(index++, info.getNOfficeID());
				ps.setString(index++, info.getSName());
				ps.setString(index++, sCode);
				ps.setLong(index++, info.getNParentClientID1());
				ps.setLong(index++, info.getNParentClientID2());
				ps.setLong(index++, info.getNStatusID());
				ps.setString(index++,info.getSExtAccountNo());

				int nRs = ps.executeUpdate();
				if (nRs > 0)
				{
					lRtn = info.getID();
				}
				accountOperation.saveExternalAccount(info.getExternalAccountInfo());
			} else {
				lRtn = -3;
			}
		}
		finally
		{
			this.cleanup(ps);
			this.cleanup(conn);
		}

		return lRtn;
	}

	//修改一条记录
	public long update(ExtClientSetInfo info) throws Exception
	{
		long lRtn = -1;
		Connection conn = null;
		PreparedStatement ps = null;

		try
		{
			if (this.checkAccountNo(info.getSExtAccountNo()) == 0 || 
				(this.checkAccountNo(info.getSExtAccountNo()) != 0 && 
				this.getIdByAccountNo(info.getSExtAccountNo()) == info.getID())) {
				conn = this.getConnection();
				String sql =
					" update sett_ExternalClient set "
						+ " nOfficeID=?,sName=?,sCode=?,nParentClientID1=?,nParentClientID2=?, "
						+ "nStatusID=?, sExtAccountNo=? WHERE ID = ?"
						+ " \n";
				ps = conn.prepareStatement(sql);

				int index = 1;
				ps.setLong(index++, info.getNOfficeID());
				ps.setString(index++, info.getSName());
				ps.setString(index++, info.getSCode());
				ps.setLong(index++, info.getNParentClientID1());
				ps.setLong(index++, info.getNParentClientID2());
				ps.setLong(index++, info.getNStatusID());
				ps.setString(index++,info.getSExtAccountNo());
				ps.setLong(index++,info.getID());
				int n = ps.executeUpdate();
				if (n > 0)
					lRtn = info.getID();
			} else {
				lRtn = -3;
			}
		}
		finally
		{
			this.cleanup(ps);
			this.cleanup(conn);
		}

		return lRtn;
	}

	//删除一条记录
	public long delete(long nID) throws Exception
	{
		long lRtn = -1;
		Connection conn = null;
		PreparedStatement ps = null;

		try
		{
			lRtn = this.updateStatusID(nID, Constant.RecordStatus.INVALID);
		}
		finally
		{
			this.cleanup(ps);
			this.cleanup(conn);
		}

		return lRtn;
	}

	//通过主键查找记录
	public ExtClientSetInfo findByID(long nID) throws Exception
	{
		ExtClientSetInfo info = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = this.getConnection();
			String sql = " select * from Sett_ExternalClient where id=? ";

			ps = conn.prepareStatement(sql);
			ps.setLong(1, nID);
			rs = ps.executeQuery();

			while (rs.next())
			{
				info = new ExtClientSetInfo();
				info.setID(rs.getLong("ID"));
				info.setNOfficeID(rs.getLong("nOfficeID"));
				info.setSName(rs.getString("sName"));
				info.setSCode(rs.getString("sCode"));
				info.setNParentClientID1(rs.getLong("nParentClientID1"));
				info.setNParentClientID2(rs.getLong("nParentClientID2"));
				info.setSExtAccountNo(rs.getString("sExtAccountNo"));
			}

		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}

		return info;
	}

	//根据办事处查询所有记录
	public Collection findByOfficeID(long nOfficeID, boolean isDesc)
		throws Exception
	{

		Collection cln = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = this.getConnection();
			StringBuffer buffer = new StringBuffer("");
			//拼凑查询语句
			buffer.append(" select * ");

			buffer.append(" from " + this.strTableName + " \n");
			buffer.append(" where nOfficeID=" + nOfficeID + " \n");
			buffer.append(" and nStatusID != "+Constant.RecordStatus.INVALID+" \n");
			buffer.append(" order by sCode ");

			//处理排序
			if (isDesc)
				buffer.append(" asc ");
			else
				buffer.append(" desc ");

			String sql = buffer.toString();

			Log.print("\n\n" + sql + "\n\n");

			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			cln = this.transferResultsetIntoCollection(rs);

		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}

		return cln;
	}

	//拼凑字符串
	private String getItemNames()
	{
		String rs = "";
		StringBuffer strBf = new StringBuffer("");

		strBf.append(" ID=?,"); //客户编号
		strBf.append(" nOfficeID=?,"); //办事处ID
		strBf.append(" sName=?,"); //外部客户名称
		strBf.append(" sCode=?,"); //外部客户编号
		strBf.append(" nParentClientID1=?,"); //母公司ID1
		strBf.append(" nParentClientID2=?,"); //母公司ID2	
		strBf.append(" nStatusID=?,"); //母公司ID	
		strBf.append(" sExtAccountNo=?"); //外部客户账户号

		rs = strBf.toString();
		return rs;
	}

	//设置记录状态
	private long updateStatusID(long id, long nNewStatus) throws Exception
	{
		long lRtn = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			conn = this.getConnection();
			String sql =
				" update " + this.strTableName + " set nStatusID=? where id=? ";
			ps = conn.prepareStatement(sql);
			ps.setLong(1, nNewStatus);
			ps.setLong(2, id);

			int n = ps.executeUpdate();
			if (n > 0)
				lRtn = 1;
		}
		finally
		{
			this.cleanup(ps);
			this.cleanup(conn);
		}

		return lRtn;
	}

	//将查询结果转为Collection
	private Collection transferResultsetIntoCollection(ResultSet rs)
		throws Exception
	{
		ArrayList list = new ArrayList();
		ExtClientSetInfo info = null;
		while (rs.next())
		{
			info = new ExtClientSetInfo();
			info.setID(rs.getLong("ID"));
			info.setNOfficeID(rs.getLong("nOfficeID"));
			info.setSName(rs.getString("sName"));
			info.setSCode(rs.getString("sCode"));
			info.setNParentClientID1(rs.getLong("nParentClientID1"));
			info.setNParentClientID2(rs.getLong("nParentClientID2"));
			info.setNStatusID(rs.getLong("nStatusID"));
			info.setSExtAccountNo(rs.getString("sExtAccountNo")); 
			list.add(info);
			Log.print("\n\n id=" + info.getID() + "\n\n");
		}

		return list;
	}
	
	//产生新的ID(数据库最大值+1)
	public long createNewID()throws Exception
	{
		long lRtn=-1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = this.getConnection();
			String sql = "  select max(id) maxid from Sett_ExternalClient ";
			ps = conn.prepareStatement(sql);		
			rs = ps.executeQuery();
			while (rs.next())
			{	
System.out.println("\n\n 最大的id="+rs.getLong("maxid")+"\n\n");		
				lRtn=rs.getLong("maxid")+1;	
System.out.println("\n\n生成的ID="+lRtn+"\n\n");			
			}
		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}
		return lRtn;
	}
	
	//	产生新的外部客户编号(数据库最大值+1)
	public String createNewCode() throws Exception
	{
		String sResult = "0001";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = this.getConnection();
			String sql = "SELECT NVL(MAX(sCode+1),1) maxCode FROM Sett_ExternalClient";
			ps = conn.prepareStatement(sql);		
			rs = ps.executeQuery();
			while (rs.next())
			{
				sResult = rs.getString("maxCode").trim();
				switch (sResult.length()) {
					case (int)1:
					sResult = "000"+sResult;
					break;
					case (int)2:
					sResult = "00"+sResult;
					break;
					case (int)3:
					sResult = "0"+sResult;
					break;
					default:
					break;
			}
		}
		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}
		return sResult;
	}

	//	判断账户号是否在外部客户表中
	public long checkAccountNo(String sAccountNo) throws Exception
	{
		long lResult = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			StringBuffer bufSQL = new StringBuffer();
			bufSQL.append("SELECT COUNT(sExtAccountNo) AccountNo FROM Sett_ExternalClient \n");
			bufSQL.append("WHERE sExtAccountNo = '"+sAccountNo+"' \n");
			bufSQL.append("	AND sExtAccountNo IN \n");
			bufSQL.append("	(SELECT sExtAccountNo FROM Sett_ExternalClient) \n");
			ps = conn.prepareStatement(bufSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				lResult = rs.getLong("AccountNo");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}
		return lResult;
	}
	// 通过账户号取ID
	private long getIdByAccountNo(String sAccountNo) throws Exception
	{
		long lResult = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			StringBuffer bufSQL = new StringBuffer();
			bufSQL.append("SELECT ID FROM Sett_ExternalClient \n");
			bufSQL.append("WHERE sExtAccountNo = '"+sAccountNo+"' \n");
			ps = conn.prepareStatement(bufSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				lResult = rs.getLong("ID");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}
		return lResult;
	}
}
