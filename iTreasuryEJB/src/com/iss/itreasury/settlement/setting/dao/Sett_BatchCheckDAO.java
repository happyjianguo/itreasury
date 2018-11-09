/*
 * Created on 2004-03-16
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.setting.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.setting.dataentity.BatchCheckSetInfo;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
/**
 * @author gqfang
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Sett_BatchCheckDAO extends SettlementDAO
{
	protected Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	public Sett_BatchCheckDAO()
	{
		super("Sett_BatchCheck");
	}
	public static void main(String[] args)
	{
		Sett_BatchCheckDAO dao = new Sett_BatchCheckDAO();
		try
		{
			ArrayList list = (ArrayList) dao.findAll();
			for(int i = 0;i<list.size();i++)
			{
				System.out.println(UtilOperation.dataentityToString(list.get(i)));
			}
			 
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//查询所有有效纪录
	public Collection findAll() throws Exception
	{
		System.out.println("调用findAll()");
		Collection coll = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = this.getConnection();
			StringBuffer sb = new StringBuffer("");
			//拼凑查询语句
			sb.append(" Select * ");
			sb.append(" from " + this.strTableName + " \n");
			sb.append(" order by id ");
			String sql = sb.toString();
			System.out.println(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			coll = this.transferRStoColl(rs);
			log.print("select all end.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(con);
		}
		return coll;
	}
	//将查询结果转化为Collection
	private Collection transferRStoColl(ResultSet rs) throws Exception
	{
		ArrayList list = new ArrayList();
		BatchCheckSetInfo info = null;
		while (rs.next())
		{
			info = new BatchCheckSetInfo();
			info.setID(rs.getLong("ID"));
			info.setNTransactionTypeID(rs.getLong("nTransactionTypeID"));
			info.setNIsBatchCheck(rs.getLong("NIsBatchCheck"));
			list.add(info);
		}
		return list;
	}
	public long updateStatusByID(long lID, long lStatusID, long lInputUserID, Timestamp tsInput) throws Exception
	{
		System.out.print("调用updateStatusByID()方法 \n");
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		long lResult = -1;
		try
		{
			con = this.getConnection();
			sb.append(" Update " + this.strTableName + "\n");
			sb.append(" set nIsBatchCheck = ? ,nInputUserID = ? ,dtInput = ? \n");
			sb.append(" Where ID = "+lID+" \n");
			log.print(sb.toString());
			ps = con.prepareStatement(sb.toString());
			ps.setLong(1, lStatusID);
			ps.setLong(2, lInputUserID);
			ps.setTimestamp(3, tsInput);
			lResult = ps.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(con);
		}
		return lResult;
	}
	/**
	 * 方法说明：判断是否需要批量复核
	 *  Method  isBatchCheck.
	 * @param TransCurrentDepositInfo info
	 * @return lIsBatchCheck
	 */
	public boolean isBatchCheck(long lTransactionTypeID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean lIsBatchCheck = false;
		long lStatusID = -1;
		try
		{
			con = this.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT * FROM sett_BatchCheck WHERE nTransActionTypeID = ? \n");
			ps = con.prepareStatement(sb.toString());
			ps.setLong(1, lTransactionTypeID);
			log.print(sb.toString());
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lStatusID = rs.getLong("nIsBatchCheck");
				if (lStatusID == 1)
				{
					lIsBatchCheck = true;
				}
				else
				{
					lIsBatchCheck = false;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return lIsBatchCheck;
	}
}