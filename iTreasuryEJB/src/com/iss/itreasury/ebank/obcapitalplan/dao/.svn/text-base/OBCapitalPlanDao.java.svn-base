package com.iss.itreasury.ebank.obcapitalplan.dao;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.ebank.obcapitalplan.dataentity.OBCapitalPlanInfo;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class OBCapitalPlanDao extends ITreasuryDAO
{

	public OBCapitalPlanDao()
	{
		super("OB_PERIODPLAN");
		super.setUseMaxID();
	}

	public OBCapitalPlanDao(String tableName)
	{
		super(tableName);
		super.setUseMaxID();
	}

	public OBCapitalPlanDao(String tableName, Connection conn)
	{
		super(tableName, conn);
		super.setUseMaxID();
	}

	/**
	 * 根据ID查询记录详细信息
	 * 
	 * @param id
	 * @return
	 * @throws IException
	 */
	public OBCapitalPlanInfo findByID(long id, long ClientID) throws IException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		OBCapitalPlanInfo infos = null;
		try
		{
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb
					.append(" SELECT a.CHECKUSERID,a.inputuserid,a.id,b.period,a.startdate,a.payamount,a.receivamount,a.statusID,b.startdate pstartdate,a.enddate \n");
			sb.append(" FROM OB_PERIODPLAN a ,SETT_PERIODSETTING b \n");
			sb.append(" WHERE a.periodid=b.id \n");
			sb.append(" and a.ClientID=" + ClientID);
			sb.append(" and a.id = " + id);
			System.out.println(sb.toString());
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				infos = new OBCapitalPlanInfo();
				infos.setId(rs.getLong("id"));
				infos.setCheckUserID(rs.getLong("CHECKUSERID"));
				infos.setInputUserID(rs.getLong("inputuserid"));
				infos.setPayAmount(rs.getLong("PAYAMOUNT"));
				infos.setReceivAmount(rs.getLong("RECEIVAMOUNT"));
				infos.setStartDate(rs.getTimestamp("STARTDATE"));
				infos.setPeriod(rs.getLong("period"));
				infos.setStatusID(rs.getLong("statusID"));
				infos.setEndDate(rs.getTimestamp("enddate"));
				infos.setPeriodStartDate(rs.getTimestamp("pstartdate"));
			}
		} catch (Exception e)
		{
			// e.printStackTrace();
			throw new IException("Gen_E001");
		} finally
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
			} catch (Exception e)
			{
				throw new IException("Gen_E001");
			}
		}
		return infos;
	}

	/**
	 * 根据查询条件得到记录集合
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public Collection queryByCondition(OBCapitalPlanInfo info)
			throws IException
	{
		Collection coll = new ArrayList();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append(" select t.* from OB_PERIODPLAN t \n");
			sb.append(" where 1=1 and \n");
			sb.append(" (t.statusid=1 or t.statusid=2) \n");
			sb.append(" and t.clientid=" + info.getClientID());
			sb.append(" order by t.id");
			System.out.println(sb.toString());
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				OBCapitalPlanInfo infos = new OBCapitalPlanInfo();
				infos.setId(rs.getLong("ID"));
				infos.setPayAmount(rs.getLong("PAYAMOUNT"));
				infos.setReceivAmount(rs.getLong("RECEIVAMOUNT"));
				infos.setStartDate(rs.getTimestamp("STARTDATE"));
				infos.setEndDate(rs.getTimestamp("ENDDATE"));
				infos.setStatusID(rs.getLong("statusID"));
				coll.add(infos);
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
		} catch (Exception e)
		{
			// e.printStackTrace();
			throw new IException("Gen_E001");
		} finally
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
			} catch (Exception e)
			{
				throw new IException("Gen_E001");
			}
		}
		return coll;
	}

	public Collection querySettByCondition(OBCapitalPlanInfo info)
			throws IException
	{
		Collection coll = new ArrayList();
		return coll;
	}

	/**
	 * 根据记录ID删除记录
	 * 
	 * @param dataEntity
	 * @return int
	 * @throws ITreasuryDAOException
	 */
	public int deleteById(ITreasuryBaseDataEntity dataEntity)
			throws ITreasuryDAOException
	{
		initDAO();

		StringBuffer buffer = new StringBuffer();
		buffer.append("UPDATE " + strTableName + " SET \n");

		String[] buffers = getAllFieldNameBuffer(dataEntity,
				DAO_OPERATION_UPDATE);
		buffer.append(buffers[0]);
		buffer.append(" WHERE ID = " + dataEntity.getId());

		String strSQL = buffer.toString();
		log.debug(strSQL);
		prepareStatement(strSQL);
		setPrepareStatementByDataEntity(dataEntity, DAO_OPERATION_UPDATE,
				buffers[0].toString());

		int retlong = executeUpdate();

		finalizeDAO();

		return retlong;
	}

	/*
	 * 测试用主函数
	 */
	public static void main(String[] args) throws IException
	{
		OBCapitalPlanDao dao = new OBCapitalPlanDao();
		OBCapitalPlanInfo info = new OBCapitalPlanInfo();
		info.setClientID(1);
		// info.setStatusID(2);
		System.out.println(dao.isDate(-1, Timestamp
				.valueOf("2006-04-17 00:00:00.0"), Timestamp
				.valueOf("2006-04-24 00:00:00.0"), 52));
	}

	private ArrayList getTime(String Sql) throws IException
	{
		ArrayList al = new ArrayList();
		Timestamp time = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = Database.getConnection();
			System.out.println(Sql);
			ps = con.prepareStatement(Sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				time = rs.getTimestamp(1);
				al.add(time);
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
		} catch (Exception e)
		{
			// e.printStackTrace();
			throw new IException("Gen_E001");
		} finally
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
			} catch (Exception e)
			{
				throw new IException("Gen_E001");
			}
		}
		return al;
	}

	public boolean isDate(long lID, Timestamp thisDate, Timestamp nextDate,
			long ClientID) throws IException
	{
		boolean bool = false;
		ArrayList StartDateList = new ArrayList();
		ArrayList EndDateList = new ArrayList();
		Timestamp StartDate = null;
		Timestamp EndDate = null;
		StringBuffer sbSQL = new StringBuffer();
		
		sbSQL.append("select startdate from OB_PERIODPLAN\n where startdate>=to_date('"
				+ thisDate.toString().substring(0, 10)
				+ "','yyyy-mm-dd')\n and ClientID="
				+ ClientID
				+ "\n and (statusid=1 or statusid=2)");
		if(lID>-1)
			sbSQL.append("\n and id<>" + lID);		
		StartDateList = this
				.getTime(sbSQL.toString());
		
		sbSQL = new StringBuffer();
		sbSQL.append("select enddate from OB_PERIODPLAN\n where enddate<=to_date('"
				+ nextDate.toString().substring(0, 10)
				+ "','yyyy-mm-dd')\n and ClientID="
				+ ClientID
				+ "\n and (statusid=1 or statusid=2)");
		if(lID>-1)
			sbSQL.append("\n and id<>" + lID);	
		EndDateList = this
				.getTime(sbSQL.toString());

		for (int i = 0; i < StartDateList.size(); i++)
		{
			StartDate = (Timestamp) StartDateList.get(i);
			bool = StartDate.getTime() >= nextDate.getTime();
			if (!bool)
				break;
		}
		for (int i = 0; i < EndDateList.size(); i++)
		{
			EndDate = (Timestamp) EndDateList.get(i);
			bool = EndDate.getTime() <= thisDate.getTime();
			if (!bool)
				break;
		}
		if(StartDateList.isEmpty() && EndDateList.isEmpty())
			bool = true;
		return bool;
	}

	public long getPeriod() throws IException
	{
		long period = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append(" select period from SETT_PERIODSETTING \n");
			System.out.println(sb.toString());
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				period = rs.getLong("period");
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
		} catch (Exception e)
		{
			// e.printStackTrace();
			throw new IException("Gen_E001");
		} finally
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
			} catch (Exception e)
			{
				throw new IException("Gen_E001");
			}
		}
		return period;
	}

	public long getId() throws IException
	{
		long id = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append(" select id from SETT_PERIODSETTING \n");
			System.out.println(sb.toString());
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				id = rs.getLong("id");
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
		} catch (Exception e)
		{
			// e.printStackTrace();
			throw new IException("Gen_E001");
		} finally
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
			} catch (Exception e)
			{
				throw new IException("Gen_E001");
			}
		}
		return id;
	}
}