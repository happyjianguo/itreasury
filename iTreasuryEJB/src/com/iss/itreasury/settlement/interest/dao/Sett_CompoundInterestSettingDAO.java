/*
 * Created on 2003-9-9
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.interest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.interest.dataentity.CompoundInterestSettingInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;

//import oracle.net.ano.SupervisorService;

/**
 * @author wlming
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Sett_CompoundInterestSettingDAO extends SettlementDAO
{
	/**
	 * 日志添加
	 */
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	/**
	 * constructor
	 * @param conn
	 */
	public Sett_CompoundInterestSettingDAO(Connection conn)
	{
		super(conn);
	}
	/**
		* constructor
		* @param conn
		*/
	public Sett_CompoundInterestSettingDAO()
	{

	}
	/**
	 * 新增复利计算设置的方法：
	 * 逻辑说明：
	 * 
	 * @param info, FixedContinueInfo, 复利计算设置实体类
	 * @return long, 新生成记录的标识
	 * @throws IException
	 */
	public long add(CompoundInterestSettingInfo info) throws Exception
	{
		long lReturn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			//利用数据库的序列号取ID;
			long id = getMaxID();
			info.setID(id);
			StringBuffer buffer = new StringBuffer();
			buffer.append("INSERT INTO sett_CompoundInterestSetting \n");
			buffer.append("(ID,nOfficeID,nCurrencyID,sSettingName, \n");
			buffer.append("dtCompoundInterest,nStatusID) \n");
			buffer.append("VALUES  \n");
			buffer.append("(?,?,?,?,?,?) \n");

			ps = con.prepareStatement(buffer.toString());
			log.info(buffer.toString());

			int index = 1;
			ps.setLong(index++, info.getID());
			ps.setLong(index++, info.getOfficeID());
			ps.setLong(index++, info.getCurrencyID());
			ps.setString(index++, info.getSettingName());
			ps.setTimestamp(index++, info.getCompoundInterestDate());
			ps.setLong(index++, info.getStatusID());

			int i = ps.executeUpdate();
			if (i > 0)
			{
				lReturn = info.getID();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
		}
		return lReturn;
	}
	/**
	 * 修改定期续期转存交易的方法：
	 * 逻辑说明：
	 * 
	 * @param info, FixedContinueInfo, 定期续期转存交易实体类
	 * @return long, 被修改记录的标识
	 * @throws Exception
	 */
	public long update(CompoundInterestSettingInfo info) throws Exception
	{
		long lReturn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			StringBuffer buffer = new StringBuffer();
			buffer.append("update sett_CompoundInterestSetting set \n");
			buffer.append("nOfficeID=?,nCurrencyID=?,sSettingName=?,\n");
			buffer.append("dtCompoundInterest=?,nStatusID=? \n");
			buffer.append("where ID=? \n");

			ps = con.prepareStatement(buffer.toString());
			log.info(buffer.toString());

			int index = 1;

			ps.setLong(index++, info.getOfficeID());
			ps.setLong(index++, info.getCurrencyID());
			ps.setString(index++, info.getSettingName());
			ps.setTimestamp(index++, info.getCompoundInterestDate());
			ps.setLong(index++, info.getStatusID());
			ps.setLong(index++, info.getID());

			int i = ps.executeUpdate();
			if (i > 0)
			{
				lReturn = info.getID();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
		}
		return lReturn;
	}
	/**
	 * 根据标识查询定期续期转存交易明细的方法：
	 * 逻辑说明：
	 * 
	 * @param lID long , 交易的ID
	 * @return FixedContinueInfo, 定期交易实体类
	 * @throws Exception
	 */
	public CompoundInterestSettingInfo findByID(long lID) throws Exception
	{
		CompoundInterestSettingInfo info = new CompoundInterestSettingInfo();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String strSQL = "select * from sett_CompoundInterestSetting where id=? ";
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				info = getCompound(info, rs);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
		}
		return info;
	}
	/**
	 * 修改定期续期转存交易状态的方法：
	 * 逻辑说明：
	 * 
	 * @param lID, long, 交易标识
	 * @param lStatusID, long, 状态标识
	 * @return long, 被修改记录的标识
	 * @throws Exception
	 */
	public long updateStatus(long lID, long lStatusID) throws Exception
	{
		long lReturn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			StringBuffer buffer = new StringBuffer();
			buffer.append("update sett_CompoundInterestSetting set nStatusID=? where ID=?");
			ps = con.prepareStatement(buffer.toString());
			int index = 1;
			ps.setLong(index++, lStatusID);
			ps.setLong(index++, lID);
			int i = ps.executeUpdate();
			if (i > 0)
			{
				lReturn = lID;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
		}
		return lReturn;
	}
	/**
	 * 设置定期交易结果集： 
	 * 逻辑说明：
	 * @throws Exception
	 */
	private CompoundInterestSettingInfo getCompound(CompoundInterestSettingInfo info, ResultSet rs) throws Exception
	{
		info = new CompoundInterestSettingInfo();
		try
		{
			info.setID(rs.getLong("ID"));
			info.setOfficeID(rs.getLong("nOfficeID"));
			info.setCurrencyID(rs.getLong("nCurrencyID"));
			info.setSettingName(rs.getString("sSettingName"));
			info.setCompoundInterestDate(rs.getTimestamp("dtCompoundInterest"));
			info.setStatusID(rs.getLong("nStatusID"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return info;

	}
	/**
	 * 得到状态条件
	 * @param info
	 * @return
	 */
	private String getQueryString(QueryByStatusConditionInfo info)
	{
		String query;
		query = "nStatusID=";
		for (int i = 0; i < info.getStatus().length; i++)
		{
			if (i < info.getStatus().length - 1)
			{

				query = query + info.getStatus()[i] + " or nStatusID=";
			}
			else
			{
				query = query + info.getStatus()[i];
			}
		}
		return query;
	}

	/**
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	private long getMaxID() throws Exception
	{
		long lMaxID = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String strSQL = "select nvl(max(id)+1,1) from sett_CompoundInterestSetting ";
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lMaxID = rs.getLong(1);
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
		return lMaxID;
	}
	/**
	 * 根据状态查询的方法：
	 * 逻辑说明：
	 * 
	 * @param QueryByStatusConditionInfo , 按状态查询的查询条件实体类。
	 * @return Collection ,包含FixedContinueInfo查询结果实体类的记录集
	 * @throws Exception
	 */
	public Collection findByStatus(QueryByStatusConditionInfo info) throws Exception
	{
		ArrayList arrResult = new ArrayList();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			StringBuffer buffer = new StringBuffer();
			//状态查询条件
			String query = "";
			if (info.getStatus() != null)
			{
				query = getQueryString(info);
			}
			else
			{
				return arrResult;
			}

			buffer.append("select * \n");
			buffer.append("from sett_CompoundInterestSetting \n");
			buffer.append("where \n");
			buffer.append("nOfficeID=? \n");
			buffer.append("and nCurrencyID=? and \n");
			buffer.append("(" + query + ") \n");
			buffer.append("order by ID \n");

			ps = con.prepareStatement(buffer.toString());

			int index = 1;
			ps.setLong(index++, info.getOfficeID());
			ps.setLong(index++, info.getCurrencyID());

			rs = ps.executeQuery();
			while (rs.next())
			{
				CompoundInterestSettingInfo resultInfo = new CompoundInterestSettingInfo();
				resultInfo = this.getCompound(resultInfo, rs);
				arrResult.add(resultInfo);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}

		}
		return arrResult;

	}

	/**
	 * 
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @param closeDate
	 * @return -1:复利计算日期设置表中没有与关机日期相同的设置日期。1：有
	 * @throws Exception
	 */
	public long findByCompoundInterestDate(long lOfficeID, long lCurrencyID, Timestamp closeDate) throws Exception
	{
		long lReturn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			StringBuffer buffer = new StringBuffer();

			buffer.append(" select * \n");
			buffer.append(" from sett_CompoundInterestSetting \n");
			buffer.append(" where \n");
		    buffer.append(" NSTATUSID = " + Constant.RecordStatus.VALID);
			buffer.append(" and nOfficeID = " + lOfficeID + " \n");
			buffer.append(" and nCurrencyID = " + lCurrencyID + " \n");
			buffer.append(" and  DTCOMPOUNDINTEREST = \n");
			buffer.append(" to_date('" + DataFormat.getDateString(closeDate) + "','yyyy-mm-dd') \n");
			ps = con.prepareStatement(buffer.toString());

			rs = ps.executeQuery();
			if (rs.next())
			{
				lReturn = 1;
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}

		}
		return lReturn;

	}
	
	/**
	 * 
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @param closeDate
	 * @return -1:复利计算日期设置表中没有与关机日期相同的设置日期。1：有
	 * @throws Exception
	 */
	public boolean validateBySettingName(long lOfficeID, long lCurrencyID, String settingName) throws Exception
	{
		boolean lReturn = true;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			StringBuffer buffer = new StringBuffer();

			buffer.append(" select * \n");
			buffer.append(" from sett_CompoundInterestSetting \n");
			buffer.append(" where \n");
		    buffer.append(" NSTATUSID = " + Constant.RecordStatus.VALID);
			buffer.append(" and nOfficeID = " + lOfficeID + " \n");
			buffer.append(" and nCurrencyID = " + lCurrencyID + " \n");
			buffer.append(" and  ssettingname = '" + settingName + "' \n");
			ps = con.prepareStatement(buffer.toString());

			rs = ps.executeQuery();
			if (rs.next())
			{
				lReturn = false;
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}

		}
		return lReturn;

	}	
}
