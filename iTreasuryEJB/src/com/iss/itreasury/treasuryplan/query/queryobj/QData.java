/*
 * Created on 2003-10-30
 * 
 * To change the template for this generated file go to Window - Preferences - Java - Code Generation - Code and
 * Comments
 */

package com.iss.itreasury.treasuryplan.query.queryobj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.clientcenter.query.queryobj.BaseQueryObject;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.treasuryplan.query.paraminfo.QueryDataInfo;
import com.iss.itreasury.treasuryplan.query.paraminfo.QueryVersionInfo;
import com.iss.itreasury.treasuryplan.query.resultinfo.QueryResultInfo;
import com.iss.itreasury.treasuryplan.report.dataentity.TemplateDetailInfo;
import com.iss.itreasury.treasuryplan.report.dataentity.TemplateInfo;
import com.iss.itreasury.treasuryplan.reportapproval.dataentity.TreasuryPlanInfo;
import com.iss.itreasury.treasuryplan.util.TPConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;

/**
 * @author xrli
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class QData extends BaseQueryObject
{

	StringBuffer	m_sbSelect	= null;

	StringBuffer	m_sbFrom	= null;

	StringBuffer	m_sbWhere	= null;

	StringBuffer	m_sbOrderBy	= null;

	ArrayList		columnList	= null;

	Log4j			log			= null;


	/**
	 * 
	 */
	public QData()
	{

		super();
		// TODO Auto-generated constructor stub
		log = new Log4j(Constant.ModuleType.PLAN, this);

	}


	/**
	 * 生成SQL语句
	 * 
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	private void getSQL(QueryDataInfo qInfo) throws Exception
	{

		m_sbSelect = new StringBuffer();
		// select
		m_sbSelect.append("* \n");

		// from
		m_sbFrom = new StringBuffer();
		//
		m_sbFrom.append("TREA_TPACTUALDATA \n");
		// where

		m_sbWhere = new StringBuffer();
		m_sbWhere.append("officeid=" + qInfo.getOfficeID() + " \n");
		m_sbWhere.append("and CurrencyID=" + qInfo.getCurrencyID() + " \n");
		if (qInfo.getDepartmentID() != this.getDepartmentID()) 
		{
			m_sbWhere.append("and AuthorizedUser like '%<" + qInfo.getUserID() + ">%' \n");
			m_sbWhere.append("and AuthorizedDepartment like '%<" + qInfo.getDepartmentID() + ">%' \n");
		}
		if (qInfo.getStartDate() != null)
		{
			m_sbWhere.append("and TRANSACTIONDATE>=to_date('" + DataFormat.formatDate(qInfo.getStartDate()) + "','yyyy-mm-dd') ");
		}
		if (qInfo.getEndDate() != null)
		{
			m_sbWhere.append("and TRANSACTIONDATE<=to_date('" + DataFormat.formatDate(qInfo.getEndDate()) + "','yyyy-mm-dd') ");
		}

		log.debug(m_sbSelect.toString() + m_sbFrom.toString() + m_sbWhere.toString());
	}


	/**
	 * 生成SQL语句
	 * 
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	private void getActualSQL(QueryDataInfo qInfo) throws Exception
	{

		m_sbSelect = new StringBuffer();
		// select
		m_sbSelect.append("a.* \n");

		// from
		m_sbFrom = new StringBuffer();
		//		
		m_sbFrom.append("(select * from TREA_TPACTUALDATA    \n");

		m_sbFrom.append("where officeid=" + qInfo.getOfficeID() + " \n");
		m_sbFrom.append("and CurrencyID=" + qInfo.getCurrencyID() + " \n");
		if (qInfo.getStartDate() != null)
			m_sbFrom.append("and TRANSACTIONDATE>=to_date('" + DataFormat.formatDate(qInfo.getStartDate()) + "','yyyy-mm-dd') \n");
		if (qInfo.getEndDate() != null)
			m_sbFrom.append("and TRANSACTIONDATE<=to_date('" + DataFormat.formatDate(qInfo.getEndDate()) + "','yyyy-mm-dd') \n ");
		m_sbFrom.append(") a ,trea_tptemplate b \n ");

		// where

		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" a.LineID=b.ID  \n");
		if (qInfo.getDepartmentID() != this.getDepartmentID())
		{
			// m_sbWhere.append("and b.AuthorizedUser like '%<" +
			// qInfo.getUserID()+ ">%' \n");
			m_sbWhere.append("and b.AuthorizedDepartment like '%<" + qInfo.getDepartmentID() + ">%' \n");
		}

		log.debug(m_sbSelect.toString() + m_sbFrom.toString() + m_sbWhere.toString());
	}


	/**
	 * 生成SQL语句
	 * 
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	private void getForcastSQL(QueryDataInfo qInfo) throws Exception
	{

		m_sbSelect = new StringBuffer();
		// select
		m_sbSelect.append("a.* \n");

		// from
		m_sbFrom = new StringBuffer();
		//
		m_sbFrom.append("(select * from TREA_TPforecastDATA    \n");

		m_sbFrom.append("where officeid=" + qInfo.getOfficeID() + " \n");
		m_sbFrom.append("and CurrencyID=" + qInfo.getCurrencyID() + " \n");
		if (qInfo.getStartDate() != null)
		{
			m_sbFrom.append("and TRANSACTIONDATE>=to_date('" + DataFormat.formatDate(qInfo.getStartDate()) + "','yyyy-mm-dd') \n");
		}
		if (qInfo.getEndDate() != null)
		{
			m_sbFrom.append("and TRANSACTIONDATE<=to_date('" + DataFormat.formatDate(qInfo.getEndDate()) + "','yyyy-mm-dd') \n ");
		}
		m_sbFrom.append(") a ,trea_tptemplate b \n ");
		// where

		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" a.LineID=b.ID  \n");
		if (qInfo.getDepartmentID() != this.getDepartmentID()) 
		{
			// m_sbWhere.append("and b.AuthorizedUser like '%<" +
			// qInfo.getUserID()+ ">%' \n");
			m_sbWhere.append("and b.AuthorizedDepartment like '%<" + qInfo.getDepartmentID() + ">%' \n");
		}

		log.debug(m_sbSelect.toString() + m_sbFrom.toString() + m_sbWhere.toString());
	}


	/**
	 * 生成SQL语句
	 * 
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	private void getPlanVersionSQL(QueryVersionInfo qInfo) throws Exception
	{

		m_sbSelect = new StringBuffer();
		// select
		m_sbSelect.append("* \n");

		// from
		m_sbFrom = new StringBuffer();
		//
		m_sbFrom.append("TREA_TREASURYPLAN \n");
		// where

		m_sbWhere = new StringBuffer();
		m_sbWhere.append("officeid=" + qInfo.getOfficeID() + " \n");
		m_sbWhere.append("and CurrencyID=" + qInfo.getCurrencyID() + " \n");
		if (qInfo.getPlanStatusID() > 0) 
		{
			m_sbWhere.append("and statusID = " + qInfo.getPlanStatusID() + " \n");
		}
		m_sbWhere.append("and statusID in (" + TPConstant.PlanVersionStatus.SAVE + "," + TPConstant.PlanVersionStatus.SUBMIT + "," + TPConstant.PlanVersionStatus.CHECK + "," + TPConstant.PlanVersionStatus.CANCEL + "," + TPConstant.PlanVersionStatus.REFUSE + "  ) \n");

		if (qInfo.getDepartmentID() != this.getDepartmentID()) 
		{
			m_sbWhere.append("and departmentID = " + qInfo.getDepartmentID() + " \n");
		}
		if (qInfo.getVersionID() > 0)
		{
			m_sbWhere.append("and id = " + qInfo.getVersionID() + " \n");
		}
		if (qInfo.getPlanDateFrom() != null)
		{
			m_sbWhere.append("and planDate>=to_date('" + DataFormat.formatDate(qInfo.getPlanDateFrom()) + "','yyyy-mm-dd') ");
		}
		if (qInfo.getPlanDateTo() != null)
		{
			m_sbWhere.append("and planDate<=to_date('" + DataFormat.formatDate(qInfo.getPlanDateTo()) + "','yyyy-mm-dd') ");
		}
		if (qInfo.getStartDate() != null)
		{
			m_sbWhere.append("and startDate>=to_date('" + DataFormat.formatDate(qInfo.getStartDate()) + "','yyyy-mm-dd') ");
		}
		if (qInfo.getEndDate() != null)
		{
			m_sbWhere.append("and endDate<=to_date('" + DataFormat.formatDate(qInfo.getEndDate()) + "','yyyy-mm-dd') ");
		}

		log.debug(m_sbSelect.toString() + m_sbFrom.toString() + m_sbWhere.toString());
	}


	/**
	 * 查询实际数据最大级别
	 * 
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	public long getMaxLevel(QueryDataInfo qInfo) throws Exception
	{

		long lRtn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList list = new ArrayList();

		String sql = "";
		getActualSQL(qInfo);
		sql = "select max(a.linelevel) maxLevel from " + m_sbFrom.toString() + "where " + m_sbWhere.toString();
		log.info("------------------SQL---" + sql);
		try 
		{

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				lRtn = rs.getLong("maxLevel");
			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		return lRtn;

	}


	/**
	 * 查询实际数据最大级别
	 * 
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	public long getDepartmentID() throws Exception
	{

		long lRtn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList list = new ArrayList();

		String sql = "";

		sql = "select ID from department where ISCOMPANYLEVEL=" + SETTConstant.BooleanValue.ISTRUE;
		sql += " and statusid=" + Constant.RecordStatus.VALID;

		log.info("------------------SQL---" + sql);
		try 
		{

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) 
			{
				lRtn = rs.getLong("ID");
			}
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		return lRtn;

	}
	
	/**
	 * 根据用户Id得到部门Id
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public long getDepartmentIDByUserId( long userId ) throws Exception
	{

		long lRtn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList list = new ArrayList();

		String sql = "";

		sql = "select ndepartmentid from userinfo where Id = " +userId;

		log.info("------------------SQL---" + sql);
		try 
		{

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) 
			{
				lRtn = rs.getLong("ndepartmentid");
			}
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		return lRtn;

	}


	/**
	 * 查询资金计划最大级别
	 * 
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	public long getMaxLevelTreaPlan(QueryDataInfo qInfo) throws Exception
	{

		long lRtn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList list = new ArrayList();

		String sql = "";
		getTreaPlanSql(qInfo);
		sql = "select max(linelevel) maxLevel from " + m_sbFrom.toString() + "where " + m_sbWhere.toString();
		log.info("------------------SQL---" + sql);
		try 
		{

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) 
			{
				lRtn = rs.getLong("maxLevel");
			}
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		return lRtn;

	}


	/**
	 * 查询应计数据最大级别
	 * 
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	public long getMaxLevelForecast(QueryDataInfo qInfo) throws Exception
	{

		long lRtn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList list = new ArrayList();

		String sql = "";
		getForcastSQL(qInfo);
		sql = "select max(a.linelevel) maxLevel from " + m_sbFrom.toString() + "where " + m_sbWhere.toString();
		log.info("------------------SQL---" + sql);
		try
		{

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lRtn = rs.getLong("maxLevel");
			}
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		return lRtn;

	}


	/**
	 * 查询实际数据
	 * 
	 * @author xrli
	 * 
	 * To change the template for this generated type comment go to Window -
	 * Preferences - Java - Code Generation - Code and Comments
	 */
	private Vector queryPlanActualData(QueryDataInfo qInfo) throws Exception
	{

		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector list = new Vector();

		String sql = "";
		getActualSQL(qInfo);
		sql = "select " + m_sbSelect.toString() + " from " + m_sbFrom.toString() + "where " + m_sbWhere.toString() + " order by a.lineNO,a.TRANSACTIONDATE ";
		log.info("------------------SQL---" + sql);
		try 
		{

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) 
			{
				QueryResultInfo info = null;
				info = getRs(info, rs, 1);
				list.add(info);
			}
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		return list;
	}


	/**
	 * 返回模版数据
	 * 
	 * @param qInfo
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Collection getActualData(QueryDataInfo qInfo) throws Exception
	{

		try 
		{
			Vector rtnList = new Vector();
			Vector tempList = new Vector();
			Vector list1 = new Vector();
			list1 = queryPlanActualData(qInfo);
			log.info("--------查询出数据的条数-----" + list1.size());
			tempList = dealActaulData(qInfo, list1);
			log.info("--------处理后数据的条数-----" + tempList.size());
			rtnList.add(tempList);
			rtnList.add(columnList);

			return rtnList;
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}

	}


	/**
	 * 返回模版数据
	 * 
	 * @param qInfo
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Collection getTreaPlanData(QueryDataInfo qInfo) throws Exception
	{

		try
		{
			Vector rtnList = new Vector();
			Vector tempList = new Vector();
			Vector list1 = new Vector();
			list1 = queryTreaPlan(qInfo);
			log.info("--------查询出数据的条数-----" + list1.size());
			tempList = dealActaulData(qInfo, list1);
			log.info("--------处理后数据的条数-----" + tempList.size());
			rtnList.add(tempList);
			rtnList.add(columnList);

			return rtnList;
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;

		}

	}


	/**
	 * 返回模版数据
	 * 
	 * @param qInfo
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Collection getTreaPlanDiffirenceData(QueryDataInfo qInfo) throws Exception
	{

		try 
		{
			Vector rtnList = new Vector();
			Vector tempList = new Vector();
			Vector list1 = new Vector();
			list1 = this.queryTreaPlanDifference(qInfo);
			log.info("--------查询出数据的条数-----" + list1.size());
			tempList = dealActaulData(qInfo, list1);
			log.info("--------处理后数据的条数-----" + tempList.size());
			rtnList.add(tempList);
			rtnList.add(columnList);

			return rtnList;
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}

	}


	/**
	 * 查询应计数据
	 * 
	 * @author xrli
	 * 
	 * To change the template for this generated type comment go to Window -
	 * Preferences - Java - Code Generation - Code and Comments
	 */
	private Vector queryForecastData(QueryDataInfo qInfo) throws Exception
	{

		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector list = new Vector();

		String sql = "";
		getForcastSQL(qInfo);
		sql = "select " + m_sbSelect.toString() + " from " + m_sbFrom.toString() + "where " + m_sbWhere.toString() + " order by a.lineNO,a.TRANSACTIONDATE ";
		log.info("------------------SQL---" + sql);
		try 
		{

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				QueryResultInfo info = null;
				info = getRs(info, rs, 2);
				list.add(info);
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		return list;
	}


	/**
	 * 资金计划查询
	 * 
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	public Collection queryPlanVersion(QueryVersionInfo qInfo) throws Exception
	{

		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector list = new Vector();

		getPlanVersionSQL(qInfo);

		String sql = "";
		sql = "select " + m_sbSelect.toString() + " from " + m_sbFrom.toString() + " where  " + m_sbWhere.toString() + " order by id ";
		log.info("------------------SQL---" + sql);
		try 
		{

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				TreasuryPlanInfo info = null;
				info = getPlanRs(info, rs);
				list.add(info);
			}
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		return list;

	}


	/**
	 * 资金计划查询
	 * 
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	private Vector queryTreaPlan(QueryDataInfo qInfo) throws Exception
	{

		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector list = new Vector();

		getTreaPlanSql(qInfo);

		String sql = "";
		sql = "select " + m_sbSelect.toString() + " from " + m_sbFrom.toString() + " where  " + m_sbWhere.toString() + " order by lineNO,TRANSACTIONDATE ";
		log.info("------------------SQL---" + sql);
		try
		{

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) 
			{
				QueryResultInfo info = null;
				info = getRs(info, rs, 3);
				list.add(info);
			}
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		return list;

	}


	/**
	 * @param qInfo
	 */
	private void getTreaPlanSql(QueryDataInfo qInfo)
	{

		m_sbSelect = new StringBuffer();

		m_sbSelect.append(" a.OfficeID AS OfficeID, \n");
		m_sbSelect.append(" a.CurrencyID AS CurrencyID, \n");
		m_sbSelect.append(" b.TransactionDate AS ExecuteDate, \n");
		m_sbSelect.append(" b.TransactionDate AS TransactionDate, \n");
		m_sbSelect.append(" b.LineID AS LineID, \n");
		m_sbSelect.append(" b.LineNO AS LineNO , \n");
		m_sbSelect.append(" b.LineName AS LineName, \n");
		m_sbSelect.append(" b.LineLevel AS LineLevel, \n");
		m_sbSelect.append(" b.ParentLineID AS ParentLineID, \n");
		m_sbSelect.append(" b.IsLeaf AS IsLeaf, \n");
		// m_sbSelect.append(" payform.sCode AS AuthorizedUserID, \n");
		m_sbSelect.append(" b.ForecastAmount AS ForecastAmount, \n");
		m_sbSelect.append(" b.PlanAmount AS PlanAmount, \n");
		m_sbSelect.append(" a.DepartmentID AS AuthorizedDepartmentID \n");

		m_sbFrom = new StringBuffer();
		m_sbFrom.append("  Trea_TreasuryPlan a,Trea_TreasuryPlanDetail b \n");
		// where
		m_sbWhere = new StringBuffer();

		m_sbWhere.append(" a.id=b.TreasuryPlanID  \n");
		m_sbWhere.append(" and a.StatusID<>0  \n");
		m_sbWhere.append(" and a.id=" + qInfo.getVersionID() + " \n ");
	}


	/**
	 * 资金计划差异查询
	 * 
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	private Vector queryTreaPlanDifference(QueryDataInfo qInfo) throws Exception
	{

		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector list = new Vector();

		getTreaPlanDifferenceSql(qInfo);

		String sql = "";
		sql = "select " + m_sbSelect.toString() + " from " + m_sbFrom.toString() + " where  " + m_sbWhere.toString() + " order by lineNO,TRANSACTIONDATE ";
		log.info("------------------SQL---" + sql);
		try 
		{

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) 
			{
				QueryResultInfo info = null;
				info = getRs(info, rs, 4);
				list.add(info);
			}
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		return list;

	}


	/**
	 * @param qInfo
	 */
	private void getTreaPlanDifferenceSql(QueryDataInfo qInfo)
	{

		m_sbSelect = new StringBuffer();

		m_sbSelect.append(" a.OfficeID AS OfficeID, \n");
		m_sbSelect.append(" a.CurrencyID AS CurrencyID, \n");
		m_sbSelect.append(" b.TransactionDate AS ExecuteDate, \n");
		m_sbSelect.append(" b.TransactionDate AS TransactionDate, \n");
		m_sbSelect.append(" b.LineID AS LineID, \n");
		m_sbSelect.append(" b.LineNO AS LineNO , \n");
		m_sbSelect.append(" b.LineName AS LineName, \n");
		m_sbSelect.append(" b.LineLevel AS LineLevel, \n");
		m_sbSelect.append(" b.ParentLineID AS ParentLineID, \n");
		m_sbSelect.append(" b.IsLeaf AS IsLeaf, \n");
		// m_sbSelect.append(" payform.sCode AS AuthorizedUserID, \n");
		m_sbSelect.append(" b.ForecastAmount AS ForecastAmount, \n");
		m_sbSelect.append(" b.PlanAmount AS PlanAmount, \n");
		m_sbSelect.append(" c.ActualAmount AS ActualAmount, \n");
		m_sbSelect.append(" a.DepartmentID AS AuthorizedDepartmentID \n");

		m_sbFrom = new StringBuffer();
		m_sbFrom.append("  Trea_TreasuryPlan a,Trea_TreasuryPlanDetail b,TREA_TPACTUALDATA c \n");
		// where
		m_sbWhere = new StringBuffer();

		m_sbWhere.append(" a.id=b.TreasuryPlanID  \n");
		m_sbWhere.append(" and b.LineID=c.LineID(+)  \n");
		m_sbWhere.append(" and b.TransactionDate=c.TransactionDate(+)   \n");
		m_sbWhere.append(" and a.StatusID<>0  \n");
		m_sbWhere.append(" and a.id=" + qInfo.getVersionID() + " \n ");
	}


	/**
	 * 返回模版数据
	 * 
	 * @param qInfo
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Collection getForecastData(QueryDataInfo qInfo) throws Exception
	{

		try 
		{
			Vector rtnList = new Vector();
			Vector tempList = new Vector();
			Vector list1 = new Vector();
			list1 = queryForecastData(qInfo);
			log.info("--------查询出数据的条数-----" + list1.size());
			tempList = dealActaulData(qInfo, list1);
			log.info("--------处理后数据的条数-----" + tempList.size());
			rtnList.add(tempList);
			rtnList.add(columnList);

			return rtnList;
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}

	}


	/**
	 * 汇总实际数据
	 * 
	 * @param info
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private Vector dealActaulData(QueryDataInfo qInfo, Vector list) throws Exception
	{

		try 
		{
			Vector rtnList = new Vector();
			ArrayList detailList = new ArrayList();
			columnList = new ArrayList();

			DataFormat df = new DataFormat();

			if (list != null && list.size() != 0) 
			{
				// 临时行号，确定数据条数
				long tempLineID = -1;
				// 临时列号，确定列名
				long tempColumnID = -1;
				// 临时周，确定按周汇总列数
				int tempWeek = 0;
				int tempWeek1 = 0;

				// 临时月份，确定按月汇总列数
				int tempMonth = 0;
				int tempMonth1 = 0;
				// 临时年份，确定按月汇总列数
				int tempYear = 0;
				int tempYear1 = 0;

				// 临时对应汇总数据
				TemplateDetailInfo tempSumInfo = new TemplateDetailInfo();
				TemplateInfo templateInfo = new TemplateInfo();
				for (int i = 0; i < list.size(); i++) 
				{
					QueryResultInfo info = new QueryResultInfo();
					info = (QueryResultInfo) list.elementAt(i);
					// 临时对应数据
					TemplateDetailInfo templateDetailInfo = new TemplateDetailInfo();

					switch ((int) qInfo.getStatType())
					{
						case TPConstant.QueryStatType.DAY : // 按日
						{

							// 判断显示一条纪录
							if (tempLineID != info.getLineID()) 
							{
								if (tempLineID != -1) // 不是第一次
								{
									templateInfo.setDetailInfos(detailList);
									rtnList.add(templateInfo);

									// 清空明细纪录
									detailList = new ArrayList();
									templateInfo = new TemplateInfo();

									templateDetailInfo.setActualAmount(info.getActualAmount());
									templateDetailInfo.setDifferenceAmount(info.getForecastAmount() - info.getPlanAmount());
									templateDetailInfo.setForecastAmount(info.getForecastAmount());
									templateDetailInfo.setPlanAmount(info.getPlanAmount());
									detailList.add(templateDetailInfo);
									// 修改临时列号
									tempColumnID = info.getLineID();

									// 最后一行
									if (i == list.size() - 1) {
										templateInfo.setIsLeaf(info.getIsLeaf());
										templateInfo.setLineID(info.getLineID());
										templateInfo.setLineLevel(info.getLineLevel());
										templateInfo.setLineName(info.getLineName());
										templateInfo.setLineNo(info.getLineNo());
										templateInfo.setParentLineID(info.getParentLineID());
										templateInfo.setDetailInfos(detailList);
										rtnList.add(templateInfo);
									}

								}
								else 
								{
									templateDetailInfo.setActualAmount(info.getActualAmount());
									templateDetailInfo.setDifferenceAmount(info.getActualAmount() - info.getPlanAmount());
									templateDetailInfo.setForecastAmount(info.getForecastAmount());
									templateDetailInfo.setPlanAmount(info.getPlanAmount());
									// templateDetailInfo.setTransactionDate(info.getTransactionDate());
									detailList.add(templateDetailInfo);
									log.info("---------生成列名数据---------");
									columnList.add(DataFormat.getDateString(info.getTransactionDate()));

									// 最后一行
									if (i == list.size() - 1) 
									{
										templateInfo.setDetailInfos(detailList);
										rtnList.add(templateInfo);
									}

								}

								tempLineID = info.getLineID();
								templateInfo.setIsLeaf(info.getIsLeaf());
								templateInfo.setLineID(info.getLineID());
								templateInfo.setLineLevel(info.getLineLevel());
								templateInfo.setLineName(info.getLineName());
								templateInfo.setLineNo(info.getLineNo());
								templateInfo.setParentLineID(info.getParentLineID());
							}
							else 
							{
								templateDetailInfo.setActualAmount(info.getActualAmount());
								templateDetailInfo.setDifferenceAmount(info.getActualAmount() - info.getPlanAmount());
								templateDetailInfo.setForecastAmount(info.getForecastAmount());
								templateDetailInfo.setPlanAmount(info.getPlanAmount());
								// templateDetailInfo.setTransactionDate(info.getTransactionDate());
								detailList.add(templateDetailInfo);

								if (tempColumnID == -1) 
								{
									log.info("---------生成列名数据---------");
									columnList.add(DataFormat.getDateString(info.getTransactionDate()));
								}

								// 最后一行
								if (i == list.size() - 1) 
								{
									templateInfo.setDetailInfos(detailList);
									rtnList.add(templateInfo);
								}

							}
						}
							break;
						case TPConstant.QueryStatType.WEEK : // 按周
						{

							// 判断显示一条纪录
							if (tempLineID != info.getLineID()) {
								if (tempLineID != -1) // 不是第一行
								{
									// 处理最后一列

									if (tempWeek1 != 0) 
									{
										detailList.add(tempSumInfo);
										tempSumInfo = new TemplateDetailInfo();
										if (tempWeek1 != 1) 
										{
											// detailList.add(tempSumInfo);
											if (tempColumnID == -1) {
												log.info("---------生成列名数据1---------");
												columnList.add(DataFormat.getDateString(qInfo.getEndDate()));
											}
										}
									}

									templateInfo.setDetailInfos(detailList);

									rtnList.add(templateInfo);

									// 清空明细纪录
									detailList = new ArrayList();
									templateInfo = new TemplateInfo();

									tempWeek = df.getWeekDay(info.getTransactionDate());
									templateDetailInfo.setActualAmount(info.getActualAmount());
									templateDetailInfo.setDifferenceAmount(info.getActualAmount() - info.getPlanAmount());
									templateDetailInfo.setForecastAmount(info.getForecastAmount());
									templateDetailInfo.setPlanAmount(info.getPlanAmount());

									detailList.add(templateDetailInfo);

									// 修改临时列号
									tempColumnID = info.getLineID();

									// 处理最后一行
									if (i == list.size() - 1) 
									{

										templateInfo.setIsLeaf(info.getIsLeaf());
										templateInfo.setLineID(info.getLineID());
										templateInfo.setLineLevel(info.getLineLevel());
										templateInfo.setLineName(info.getLineName());
										templateInfo.setLineNo(info.getLineNo());
										templateInfo.setParentLineID(info.getParentLineID());
										templateInfo.setDetailInfos(detailList);

										rtnList.add(templateInfo);

									}

								}
								else 
								{

									tempWeek = df.getWeekDay(info.getTransactionDate());
									templateDetailInfo.setActualAmount(info.getActualAmount());
									templateDetailInfo.setDifferenceAmount(info.getActualAmount() - info.getPlanAmount());
									templateDetailInfo.setForecastAmount(info.getForecastAmount());
									templateDetailInfo.setPlanAmount(info.getPlanAmount());

									detailList.add(templateDetailInfo);

									log.info("---------生成列名数据2---------");
									columnList.add(DataFormat.getDateString(info.getTransactionDate()));

									if (i == list.size() - 1)
									{
										templateInfo.setDetailInfos(detailList);

										rtnList.add(templateInfo);
									}
								}

								tempLineID = info.getLineID();
								templateInfo.setIsLeaf(info.getIsLeaf());
								templateInfo.setLineID(info.getLineID());
								templateInfo.setLineLevel(info.getLineLevel());
								templateInfo.setLineName(info.getLineName());
								templateInfo.setLineNo(info.getLineNo());
								templateInfo.setParentLineID(info.getParentLineID());

								tempWeek1 = 0;// 将临时的清为0
							}
							else 
							{
								if (tempWeek == 1) 
								{
									if (tempWeek1 == 1) 
									{
										tempWeek1 = df.getWeekDay(info.getTransactionDate());

										detailList.add(tempSumInfo);

										tempSumInfo = new TemplateDetailInfo();
										tempSumInfo.setActualAmount(info.getActualAmount() + tempSumInfo.getActualAmount());
										tempSumInfo.setForecastAmount(info.getForecastAmount() + tempSumInfo.getForecastAmount());
										tempSumInfo.setDifferenceAmount(info.getDifferenceAmount() + tempSumInfo.getDifferenceAmount());
										tempSumInfo.setPlanAmount(info.getPlanAmount() + tempSumInfo.getPlanAmount());

										// 处理最后一行最后一列
										if (i == list.size() - 1) {
											detailList.add(tempSumInfo);
											templateInfo.setDetailInfos(detailList);

											rtnList.add(templateInfo);
										}
									}
									else 
									{
										tempWeek1 = df.getWeekDay(info.getTransactionDate());

										tempSumInfo.setActualAmount(info.getActualAmount() + tempSumInfo.getActualAmount());
										tempSumInfo.setForecastAmount(info.getForecastAmount() + tempSumInfo.getForecastAmount());
										tempSumInfo.setDifferenceAmount(info.getDifferenceAmount() + tempSumInfo.getDifferenceAmount());
										tempSumInfo.setPlanAmount(info.getPlanAmount() + tempSumInfo.getPlanAmount());
										if (tempColumnID == -1 && tempWeek1 == 1)
										{
											log.info("---------生成列名数据3---------");
											columnList.add(DataFormat.getDateString(info.getTransactionDate()));
										}
										// 处理最后一行最后一列

										if (i == list.size() - 1) 
										{
											log.info("------处理最后一行最后一列3----------");
											detailList.add(tempSumInfo);
											log.info("------处理最后一行最后一列3----------" + detailList.size());
											templateInfo.setDetailInfos(detailList);
											rtnList.add(templateInfo);

										}
									}

								}
								else
								{
									tempWeek = df.getWeekDay(info.getTransactionDate());
									templateDetailInfo.setActualAmount(info.getActualAmount());
									templateDetailInfo.setDifferenceAmount(info.getActualAmount() - info.getPlanAmount());
									templateDetailInfo.setForecastAmount(info.getForecastAmount());
									templateDetailInfo.setPlanAmount(info.getPlanAmount());

									detailList.add(templateDetailInfo);

									if (tempColumnID == -1)
									{
										log.info("---------生成列名数据4---------");
										columnList.add(DataFormat.getDateString(info.getTransactionDate()));
									}
									// 处理最后一行最后一列
									if (i == list.size() - 1) 
									{
										templateInfo.setDetailInfos(detailList);
										rtnList.add(templateInfo);
									}

								}
							}

						}
							break;
						case TPConstant.QueryStatType.MONTH : // 按月
						{
							// 判断显示一条纪录
							if (tempLineID != info.getLineID()) 
							{
								if (tempLineID != -1) // 不是第一次
								{
									// 处理最后一列
									if (tempMonth1 != 0)
									{
										detailList.add(tempSumInfo);
										tempSumInfo = new TemplateDetailInfo();
										if (tempColumnID == -1)
										{
											log.info("---------生成列名数据1---------");
											columnList.add(DataFormat.getDateString(qInfo.getEndDate()));
										}
									}

									templateInfo.setDetailInfos(detailList);

									rtnList.add(templateInfo);
									// 清空明细纪录
									detailList = new ArrayList();
									templateInfo = new TemplateInfo();

									tempMonth1 = 0;

									tempMonth = info.getTransactionDate().getMonth();

									templateDetailInfo.setActualAmount(info.getActualAmount());
									templateDetailInfo.setDifferenceAmount(info.getActualAmount() - info.getPlanAmount());
									templateDetailInfo.setForecastAmount(info.getForecastAmount());
									templateDetailInfo.setPlanAmount(info.getPlanAmount());
									detailList.add(templateDetailInfo);

									// 修改临时列号
									tempColumnID = info.getLineID();
									// 处理最后一行最后一列
									if (i == list.size() - 1)
									{
										templateInfo.setIsLeaf(info.getIsLeaf());
										templateInfo.setLineID(info.getLineID());
										templateInfo.setLineLevel(info.getLineLevel());
										templateInfo.setLineName(info.getLineName());
										templateInfo.setLineNo(info.getLineNo());
										templateInfo.setParentLineID(info.getParentLineID());
										templateInfo.setDetailInfos(detailList);
										log.info("---------最后一行列数----" + detailList.size());
										rtnList.add(templateInfo);
									}

								}
								else
								{
									tempMonth = info.getTransactionDate().getMonth();
									templateDetailInfo.setActualAmount(info.getActualAmount());
									templateDetailInfo.setDifferenceAmount(info.getActualAmount() - info.getPlanAmount());
									templateDetailInfo.setForecastAmount(info.getForecastAmount());
									templateDetailInfo.setPlanAmount(info.getPlanAmount());
									detailList.add(templateDetailInfo);
									if (tempColumnID == -1)
									{
										log.info("---------生成列名数据2---------");
										columnList.add(DataFormat.getDateString(info.getTransactionDate()));
									}

									if (i == list.size() - 1)
									{
										templateInfo.setIsLeaf(info.getIsLeaf());
										templateInfo.setLineID(info.getLineID());
										templateInfo.setLineLevel(info.getLineLevel());
										templateInfo.setLineName(info.getLineName());
										templateInfo.setLineNo(info.getLineNo());
										templateInfo.setParentLineID(info.getParentLineID());
										templateInfo.setDetailInfos(detailList);
										rtnList.add(templateInfo);
									}
								}

								tempLineID = info.getLineID();
								templateInfo.setIsLeaf(info.getIsLeaf());
								templateInfo.setLineID(info.getLineID());
								templateInfo.setLineLevel(info.getLineLevel());
								templateInfo.setLineName(info.getLineName());
								templateInfo.setLineNo(info.getLineNo());
								templateInfo.setParentLineID(info.getParentLineID());
							}
							else 
							{
								if (tempMonth == info.getTransactionDate().getMonth()) 
								{
									templateDetailInfo.setActualAmount(info.getActualAmount());
									templateDetailInfo.setDifferenceAmount(info.getActualAmount() - info.getPlanAmount());
									templateDetailInfo.setForecastAmount(info.getForecastAmount());
									templateDetailInfo.setPlanAmount(info.getPlanAmount());
									detailList.add(templateDetailInfo);
									if (tempColumnID == -1) 
									{
										log.info("---------生成列名数据3---------");
										columnList.add(DataFormat.getDateString(info.getTransactionDate()));
									}

									if (i == list.size() - 1) 
									{
										templateInfo.setDetailInfos(detailList);
										log.info("---------最后一行列数1----" + detailList.size());
										rtnList.add(templateInfo);
									}
								}
								else 
								{
									// 以后几个月的汇总数据
									if (tempMonth1 == 0) 
									{
										tempSumInfo.setActualAmount(info.getActualAmount() + tempSumInfo.getActualAmount());
										tempSumInfo.setForecastAmount(info.getForecastAmount() + tempSumInfo.getForecastAmount());
										tempSumInfo.setDifferenceAmount(info.getDifferenceAmount() + tempSumInfo.getDifferenceAmount());
										tempSumInfo.setPlanAmount(info.getPlanAmount() + tempSumInfo.getPlanAmount());
										tempMonth1 = info.getTransactionDate().getMonth();

										if (i == list.size() - 1)
										{
											detailList.add(tempSumInfo);
											log.info("---------最后一行列数2----" + detailList.size());
											templateInfo.setDetailInfos(detailList);
											rtnList.add(templateInfo);
										}
									}
									else
									{
										if (tempMonth1 == info.getTransactionDate().getMonth()) 
										{
											tempSumInfo.setActualAmount(info.getActualAmount() + tempSumInfo.getActualAmount());
											tempSumInfo.setForecastAmount(info.getForecastAmount() + tempSumInfo.getForecastAmount());
											tempSumInfo.setDifferenceAmount(info.getDifferenceAmount() + tempSumInfo.getDifferenceAmount());
											tempSumInfo.setPlanAmount(info.getPlanAmount() + tempSumInfo.getPlanAmount());

											if (i == list.size() - 1) 
											{
												detailList.add(tempSumInfo);
												log.info("---------最后一行列数3----" + detailList.size());
												templateInfo.setDetailInfos(detailList);
												rtnList.add(templateInfo);
											}
										}
										else 
										{
											detailList.add(tempSumInfo);
											if (tempColumnID == -1) 
											{
												log.info("---------生成列名数据3---------");
												columnList.add(DataFormat.getDateString(UtilOperation.getNextNDay(info.getTransactionDate(), -1)));
											}
											tempSumInfo = new TemplateDetailInfo();
											tempMonth1 = info.getTransactionDate().getMonth();

											tempSumInfo.setActualAmount(info.getActualAmount() + tempSumInfo.getActualAmount());
											tempSumInfo.setForecastAmount(info.getForecastAmount() + tempSumInfo.getForecastAmount());
											tempSumInfo.setDifferenceAmount(info.getDifferenceAmount() + tempSumInfo.getDifferenceAmount());
											tempSumInfo.setPlanAmount(info.getPlanAmount() + tempSumInfo.getPlanAmount());

											if (i == list.size() - 1) 
											{
												detailList.add(tempSumInfo);
												log.info("---------最后一行列数4----" + detailList.size());
												templateInfo.setDetailInfos(detailList);
												rtnList.add(templateInfo);
											}
										}
									}

								}

							}

						}
							break;
						case TPConstant.QueryStatType.YEAR : // 按年
						{

							// 判断显示一条纪录
							if (tempLineID != info.getLineID()) 
							{
								if (tempLineID != -1) // 不是第一次
								{
									// 处理最后一列
									if (tempYear1 != 0) 
									{
										detailList.add(tempSumInfo);
										tempSumInfo = new TemplateDetailInfo();
										if (tempColumnID == -1) 
										{
											log.info("---------生成列名数据---------");
											columnList.add(DataFormat.getDateString(qInfo.getEndDate()));
										}
									}

									templateInfo.setDetailInfos(detailList);
									rtnList.add(templateInfo);
									// 清空明细纪录
									detailList = new ArrayList();
									templateInfo = new TemplateInfo();
									tempYear1 = 0;

									tempYear = info.getTransactionDate().getYear();
									templateDetailInfo.setActualAmount(info.getActualAmount());
									templateDetailInfo.setDifferenceAmount(info.getActualAmount() - info.getPlanAmount());
									templateDetailInfo.setForecastAmount(info.getForecastAmount());
									templateDetailInfo.setPlanAmount(info.getPlanAmount());
									detailList.add(templateDetailInfo);

									tempColumnID = info.getLineID();

									if (i == list.size() - 1) 
									{
										templateInfo.setIsLeaf(info.getIsLeaf());
										templateInfo.setLineID(info.getLineID());
										templateInfo.setLineLevel(info.getLineLevel());
										templateInfo.setLineName(info.getLineName());
										templateInfo.setLineNo(info.getLineNo());
										templateInfo.setParentLineID(info.getParentLineID());
										templateInfo.setDetailInfos(detailList);
										log.info("---------最后一行列数----" + detailList.size());
										rtnList.add(templateInfo);
									}
								}
								else 
								{
									tempYear = info.getTransactionDate().getYear();
									templateDetailInfo.setActualAmount(info.getActualAmount());
									templateDetailInfo.setDifferenceAmount(info.getActualAmount() - info.getPlanAmount());
									templateDetailInfo.setForecastAmount(info.getForecastAmount());
									templateDetailInfo.setPlanAmount(info.getPlanAmount());
									detailList.add(templateDetailInfo);
									if (tempColumnID == -1) 
									{
										log.info("---------生成列名数据---------");
										columnList.add(DataFormat.getDateString(info.getTransactionDate()));
									}
									/**
									 * 处理最后一行最后一列
									 */
									if (i == list.size() - 1) 
									{
										templateInfo.setIsLeaf(info.getIsLeaf());
										templateInfo.setLineID(info.getLineID());
										templateInfo.setLineLevel(info.getLineLevel());
										templateInfo.setLineName(info.getLineName());
										templateInfo.setLineNo(info.getLineNo());
										templateInfo.setParentLineID(info.getParentLineID());
										templateInfo.setDetailInfos(detailList);
										log.info("---------最后一行列数----" + detailList.size());
										rtnList.add(templateInfo);
									}
								}

								tempLineID = info.getLineID();
								templateInfo.setIsLeaf(info.getIsLeaf());
								templateInfo.setLineID(info.getLineID());
								templateInfo.setLineLevel(info.getLineLevel());
								templateInfo.setLineName(info.getLineName());
								templateInfo.setLineNo(info.getLineNo());
								templateInfo.setParentLineID(info.getParentLineID());
							}
							else 
							{

								// 不是第一次,第一个年显示的数据
								if (tempYear == info.getTransactionDate().getYear()) {
									templateDetailInfo.setActualAmount(info.getActualAmount());
									templateDetailInfo.setDifferenceAmount(info.getActualAmount() - info.getPlanAmount());
									templateDetailInfo.setForecastAmount(info.getForecastAmount());
									templateDetailInfo.setPlanAmount(info.getPlanAmount());
									detailList.add(templateDetailInfo);
									if (tempColumnID == -1) 
									{
										log.info("---------生成列名数据---------");
										columnList.add(DataFormat.getDateString(info.getTransactionDate()));
									}

									/**
									 * 处理最后一行最后一列
									 */
									if (i == list.size() - 1)
									{
										templateInfo.setDetailInfos(detailList);
										rtnList.add(templateInfo);
									}
								}
								else 
								{
									// 以后几年的汇总数据
									if (tempYear1 == 0) 
									{
										tempSumInfo.setActualAmount(info.getActualAmount() + tempSumInfo.getActualAmount());
										tempSumInfo.setForecastAmount(info.getForecastAmount() + tempSumInfo.getForecastAmount());
										tempSumInfo.setDifferenceAmount(info.getDifferenceAmount() + tempSumInfo.getDifferenceAmount());
										tempSumInfo.setPlanAmount(info.getPlanAmount() + tempSumInfo.getPlanAmount());
										tempYear1 = info.getTransactionDate().getYear();

										/**
										 * 处理最后一行最后一列
										 */
										if (i == list.size() - 1) 
										{
											detailList.add(tempSumInfo);
											templateInfo.setDetailInfos(detailList);
											rtnList.add(templateInfo);
										}
									}
									else 
									{
										if (tempYear1 == info.getTransactionDate().getYear()) 
										{
											tempSumInfo.setActualAmount(info.getActualAmount() + tempSumInfo.getActualAmount());
											tempSumInfo.setForecastAmount(info.getForecastAmount() + tempSumInfo.getForecastAmount());
											tempSumInfo.setDifferenceAmount(info.getDifferenceAmount() + tempSumInfo.getDifferenceAmount());
											tempSumInfo.setPlanAmount(info.getPlanAmount() + tempSumInfo.getPlanAmount());

											/**
											 * 处理最后一行最后一列
											 */
											if (i == list.size() - 1)
											{
												detailList.add(tempSumInfo);
												templateInfo.setDetailInfos(detailList);
												rtnList.add(templateInfo);
											}
										}
										else 
										{
											detailList.add(tempSumInfo);
											if (tempColumnID == -1)
											{
												log.info("---------生成列名数据---------");
												columnList.add(DataFormat.getDateString(info.getTransactionDate()));
											}
											tempSumInfo = new TemplateDetailInfo();
											tempYear1 = info.getTransactionDate().getYear();

											tempSumInfo.setActualAmount(info.getActualAmount() + tempSumInfo.getActualAmount());
											tempSumInfo.setForecastAmount(info.getForecastAmount() + tempSumInfo.getForecastAmount());
											tempSumInfo.setDifferenceAmount(info.getDifferenceAmount() + tempSumInfo.getDifferenceAmount());
											tempSumInfo.setPlanAmount(info.getPlanAmount() + tempSumInfo.getPlanAmount());

											/**
											 * 处理最后一行最后一列
											 */
											if (i == list.size() - 1) 
											{
												detailList.add(tempSumInfo);
												templateInfo.setDetailInfos(detailList);
												rtnList.add(templateInfo);
											}
										}
									}

								}
							}

						}
							break;
						case TPConstant.QueryStatType.MONTHSUM : // 按月汇总
						{
							// 判断显示一条纪录
							if (tempLineID != info.getLineID()) 
							{
								if (tempLineID != -1) // 不是第一次
								{
									// 处理最后一列
									detailList.add(tempSumInfo);
									tempSumInfo = new TemplateDetailInfo();
									if (tempColumnID == -1)
									{
										log.info("---------生成列名数据---------");
										// columnList.add(DataFormat.getDateString(qInfo.getEndDate()));
										//columnList.add(String.valueOf(qInfo.getEndDate().getYear() + 1900) + "0" + String.valueOf(qInfo.getEndDate().getMonth() + 1));										
										columnList.add(DataFormat.getYearString(qInfo.getEndDate()) + qInfo.getEndDate()!=null?("0"+DataFormat.getMonthString(qInfo.getEndDate())+1):"");										
									}

									templateInfo.setDetailInfos(detailList);
									rtnList.add(templateInfo);

									// 清空明细纪录
									detailList = new ArrayList();
									templateInfo = new TemplateInfo();

									tempSumInfo.setActualAmount(info.getActualAmount() + tempSumInfo.getActualAmount());
									tempSumInfo.setForecastAmount(info.getForecastAmount() + tempSumInfo.getForecastAmount());
									tempSumInfo.setDifferenceAmount(info.getDifferenceAmount() + tempSumInfo.getDifferenceAmount());
									tempSumInfo.setPlanAmount(info.getPlanAmount() + tempSumInfo.getPlanAmount());
									tempMonth = info.getTransactionDate().getMonth();
									tempColumnID = info.getLineID();

									/**
									 * 处理最后一行最后一列
									 */
									if (i == list.size() - 1) 
									{
										templateInfo.setIsLeaf(info.getIsLeaf());
										templateInfo.setLineID(info.getLineID());
										templateInfo.setLineLevel(info.getLineLevel());
										templateInfo.setLineName(info.getLineName());
										templateInfo.setLineNo(info.getLineNo());
										templateInfo.setParentLineID(info.getParentLineID());
										detailList.add(tempSumInfo);
										templateInfo.setDetailInfos(detailList);
										rtnList.add(templateInfo);
									}
								}
								else 
								{
									tempSumInfo.setActualAmount(info.getActualAmount() + tempSumInfo.getActualAmount());
									tempSumInfo.setForecastAmount(info.getForecastAmount() + tempSumInfo.getForecastAmount());
									tempSumInfo.setDifferenceAmount(info.getDifferenceAmount() + tempSumInfo.getDifferenceAmount());
									tempSumInfo.setPlanAmount(info.getPlanAmount() + tempSumInfo.getPlanAmount());
									tempMonth = info.getTransactionDate().getMonth();

									/**
									 * 处理最后一行最后一列
									 */
									if (i == list.size() - 1) 
									{
										templateInfo.setIsLeaf(info.getIsLeaf());
										templateInfo.setLineID(info.getLineID());
										templateInfo.setLineLevel(info.getLineLevel());
										templateInfo.setLineName(info.getLineName());
										templateInfo.setLineNo(info.getLineNo());
										templateInfo.setParentLineID(info.getParentLineID());
										detailList.add(tempSumInfo);
										templateInfo.setDetailInfos(detailList);
										rtnList.add(templateInfo);
									}
								}
								// 清空明细纪录
								detailList = new ArrayList();
								templateInfo = new TemplateInfo();

								tempLineID = info.getLineID();
								templateInfo.setIsLeaf(info.getIsLeaf());
								templateInfo.setLineID(info.getLineID());
								templateInfo.setLineLevel(info.getLineLevel());
								templateInfo.setLineName(info.getLineName());
								templateInfo.setLineNo(info.getLineNo());
								templateInfo.setParentLineID(info.getParentLineID());
							}
							else
							{
								// 以后几个月的汇总数据
								if (tempMonth != info.getTransactionDate().getMonth()) 
								{

									detailList.add(tempSumInfo);
									tempSumInfo = new TemplateDetailInfo();
									if (tempColumnID == -1)
									{
										log.info("---------生成列名数据---------");
										// columnList.add(DataFormat.getDateString(info.getTransactionDate()));
										columnList.add(String.valueOf(info.getTransactionDate().getYear() + 1900) + "0" + String.valueOf(tempMonth + 1));
									}

									tempMonth = info.getTransactionDate().getMonth();

									/**
									 * 处理最后一行最后一列
									 */
									if (i == list.size() - 1)
									{

										templateInfo.setDetailInfos(detailList);
										rtnList.add(templateInfo);
									}
								}
								else 
								{
									tempSumInfo.setActualAmount(info.getActualAmount() + tempSumInfo.getActualAmount());
									tempSumInfo.setForecastAmount(info.getForecastAmount() + tempSumInfo.getForecastAmount());
									tempSumInfo.setDifferenceAmount(info.getDifferenceAmount() + tempSumInfo.getDifferenceAmount());
									tempSumInfo.setPlanAmount(info.getPlanAmount() + tempSumInfo.getPlanAmount());

									/**
									 * 处理最后一行最后一列
									 */
									if (i == list.size() - 1)
									{
										detailList.add(tempSumInfo);
										templateInfo.setDetailInfos(detailList);
										rtnList.add(templateInfo);
									}
								}

							}
						}

							break;
						case TPConstant.QueryStatType.YEARSUM : // 按年汇总
						{

							// 判断显示一条纪录
							if (tempLineID != info.getLineID()) 
							{
								if (tempLineID != -1) // 不是第一次
								{
									// 处理最后一列
									detailList.add(tempSumInfo);
									tempSumInfo = new TemplateDetailInfo();
									if (tempColumnID == -1) 
									{
										log.info("---------生成列名数据---------");
										// columnList.add(DataFormat.getDateString(qInfo.getEndDate()));
										//columnList.add(String.valueOf(qInfo.getEndDate().getYear() + 1900));
										columnList.add(DataFormat.getYearString(qInfo.getEndDate()));
									}
									templateInfo.setDetailInfos(detailList);
									rtnList.add(templateInfo);
									// 清空明细纪录
									detailList = new ArrayList();
									templateInfo = new TemplateInfo();

									tempSumInfo.setActualAmount(info.getActualAmount() + tempSumInfo.getActualAmount());
									tempSumInfo.setForecastAmount(info.getForecastAmount() + tempSumInfo.getForecastAmount());
									tempSumInfo.setDifferenceAmount(info.getDifferenceAmount() + tempSumInfo.getDifferenceAmount());
									tempSumInfo.setPlanAmount(info.getPlanAmount() + tempSumInfo.getPlanAmount());
									tempYear = info.getTransactionDate().getYear();

									tempColumnID = info.getLineID();

									/**
									 * 处理最后一行最后一列
									 */
									if (i == list.size() - 1) 
									{
										templateInfo.setIsLeaf(info.getIsLeaf());
										templateInfo.setLineID(info.getLineID());
										templateInfo.setLineLevel(info.getLineLevel());
										templateInfo.setLineName(info.getLineName());
										templateInfo.setLineNo(info.getLineNo());
										templateInfo.setParentLineID(info.getParentLineID());
										detailList.add(tempSumInfo);
										templateInfo.setDetailInfos(detailList);
										rtnList.add(templateInfo);
									}
								}
								else 
								{
									tempSumInfo.setActualAmount(info.getActualAmount() + tempSumInfo.getActualAmount());
									tempSumInfo.setForecastAmount(info.getForecastAmount() + tempSumInfo.getForecastAmount());
									tempSumInfo.setDifferenceAmount(info.getDifferenceAmount() + tempSumInfo.getDifferenceAmount());
									tempSumInfo.setPlanAmount(info.getPlanAmount() + tempSumInfo.getPlanAmount());
									tempYear = info.getTransactionDate().getYear();

									/**
									 * 处理最后一行最后一列
									 */
									if (i == list.size() - 1) 
									{
										templateInfo.setIsLeaf(info.getIsLeaf());
										templateInfo.setLineID(info.getLineID());
										templateInfo.setLineLevel(info.getLineLevel());
										templateInfo.setLineName(info.getLineName());
										templateInfo.setLineNo(info.getLineNo());
										templateInfo.setParentLineID(info.getParentLineID());
										detailList.add(tempSumInfo);
										templateInfo.setDetailInfos(detailList);
										rtnList.add(templateInfo);
									}
								}
								// 清空明细纪录
								detailList = new ArrayList();
								templateInfo = new TemplateInfo();

								tempLineID = info.getLineID();
								templateInfo.setIsLeaf(info.getIsLeaf());
								templateInfo.setLineID(info.getLineID());
								templateInfo.setLineLevel(info.getLineLevel());
								templateInfo.setLineName(info.getLineName());
								templateInfo.setLineNo(info.getLineNo());
								templateInfo.setParentLineID(info.getParentLineID());
							}
							else 
							{
								// 以后几年的汇总数据
								if (tempYear != info.getTransactionDate().getYear())
								{

									detailList.add(tempSumInfo);
									tempSumInfo = new TemplateDetailInfo();
									if (tempColumnID == -1) 
									{
										log.info("---------生成列名数据---------");
										// columnList.add(DataFormat.getDateString(info.getTransactionDate()));
										columnList.add(String.valueOf(tempYear + 1900));
									}

									tempYear = info.getTransactionDate().getYear();

									/**
									 * 处理最后一行最后一列
									 */
									if (i == list.size() - 1) 
									{
										templateInfo.setDetailInfos(detailList);
										rtnList.add(templateInfo);
									}
								}
								else 
								{
									tempSumInfo.setActualAmount(info.getActualAmount() + tempSumInfo.getActualAmount());
									tempSumInfo.setForecastAmount(info.getForecastAmount() + tempSumInfo.getForecastAmount());
									tempSumInfo.setDifferenceAmount(info.getDifferenceAmount() + tempSumInfo.getDifferenceAmount());
									tempSumInfo.setPlanAmount(info.getPlanAmount() + tempSumInfo.getPlanAmount());

									/**
									 * 处理最后一行最后一列
									 */
									if (i == list.size() - 1) {
										detailList.add(tempSumInfo);
										templateInfo.setDetailInfos(detailList);
										rtnList.add(templateInfo);
									}
								}

							}

						}
							break;

					}
				}
			}
			return rtnList;
		}
		catch (Exception e)
		{
			log.info("***********************");
			e.printStackTrace();
			throw e;

			// TODO Auto-generated catch block

		}
	}


	/**
	 * 设置结果集： 逻辑说明：
	 * 
	 * @throws Exception
	 */
	private QueryResultInfo getRs(QueryResultInfo info, ResultSet rs, int i) throws Exception
	{

		info = new QueryResultInfo();
		try
		{
			// info.setId(rs.getLong("ID"));
			info.setOfficeID(rs.getLong("OfficeID"));
			info.setCurrencyID(rs.getLong("CurrencyID"));
			info.setExecuteDate(rs.getTimestamp("ExecuteDate"));
			info.setTransactionDate(rs.getTimestamp("TransactionDate"));
			info.setLineID(rs.getLong("LineID"));
			info.setLineNo(rs.getString("LineNO"));
			info.setLineName(rs.getString("LineName"));
			info.setLineLevel(rs.getLong("LineLevel"));
			info.setParentLineID(rs.getLong("ParentLineID"));
			info.setIsLeaf(rs.getLong("IsLeaf"));
			// info.setAuthorizedDepartmentID(rs.getLong("AuthorizedDepartmentID"));
			// info.setAuthorizedUserID(rs.getLong("AuthorizedUserID"));
			if (i == 1) // 实际数据查询
			{
				info.setActualAmount(rs.getDouble("ActualAmount"));
			}
			else if (i == 2) // 应计数据查询
			{
				info.setForecastAmount(rs.getDouble("FORECASTAMOUNT"));
				info.setPlanAmount(rs.getDouble("PlanAmount"));
			}
			else if (i == 3) // 资金计划查询
			{
				info.setForecastAmount(rs.getDouble("FORECASTAMOUNT"));
				info.setPlanAmount(rs.getDouble("PlanAmount"));
			}
			else if (i == 4) // 实际计划差异比较
			{
				info.setActualAmount(rs.getDouble("ActualAmount"));
				info.setForecastAmount(rs.getDouble("FORECASTAMOUNT"));
				info.setPlanAmount(rs.getDouble("PlanAmount"));
				info.setDifferenceAmount(info.getActualAmount() - info.getPlanAmount());
			}

		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		}
		return info;

	}


	/**
	 * 设置结果集： 逻辑说明：
	 * 
	 * @throws Exception
	 */
	private TreasuryPlanInfo getPlanRs(TreasuryPlanInfo info, ResultSet rs) throws Exception
	{

		info = new TreasuryPlanInfo();
		try
		{
			info.setId(rs.getLong("ID"));
			info.setCode(rs.getString("code"));
			info.setOfficeId(rs.getLong("OfficeID"));
			info.setCurrencyId(rs.getLong("CurrencyID"));
			info.setStartDate(rs.getTimestamp("startDate"));
			info.setEndDate(rs.getTimestamp("endDate"));
			info.setStatusId(rs.getLong("statusID"));
			info.setNextCheckLevel(rs.getLong("NextCheckLevel"));
			info.setDepartmentId(rs.getLong("departmentID"));

		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return info;

	}

}