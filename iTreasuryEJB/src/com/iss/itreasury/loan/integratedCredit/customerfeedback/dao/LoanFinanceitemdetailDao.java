package com.iss.itreasury.loan.integratedCredit.customerfeedback.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.LoanCreditgradedetail;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.LoanExternalliabilities;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.LoanFinanceanalyse;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.LoanFinanceitemdetail;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.LoanGroupinsidecontact;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.LoanReceiveaccountage;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;

/**
 * @author wxsu
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LoanFinanceitemdetailDao extends SettlementDAO {

	private static Log4j log4j = null;

	public LoanFinanceitemdetailDao() {
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
	}

	/**
	 * 保存财务分析主表
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long LoanFinanceanalysesave(LoanFinanceanalyse info)
			throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;
		long ID = this.getMaxID(2);
		try {
			conn = Database.getConnection();
			strSQL = "insert into LOAN_FINANCEANALYSE ("
					+ "id,creditid,clientid,cycleyear,cyclemonth, "
					+ "isaudit,repoetnameid,repoetname,reporttype,status,inputuserid,inputdate,explain,officeid,currencyid "
					+ ") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?, ?, ?, ? )";
			ps = conn.prepareStatement(strSQL);
			int n = 1;
			ps.setLong(n++, ID);
			ps.setLong(n++, info.getCreditid());
			ps.setLong(n++, info.getClientid());
			ps.setString(n++, info.getCycleyear());
			ps.setString(n++, info.getCyclemonth());
			ps.setLong(n++, info.getIsaudit());
			ps.setLong(n++, info.getRepoetnameid());
			ps.setString(n++, info.getRepoetname());
			ps.setLong(n++, info.getReporttype());
			ps.setLong(n++, info.getStatus());
			ps.setLong(n++, info.getInputuserid());
			ps.setTimestamp(n++, info.getInputdate());
			ps.setString(n++, info.getExplain());
			ps.setLong(n++, info.getOfficeid());
			ps.setLong(n++, info.getCurrencyid());
			lResult = ps.executeUpdate();

			if (lResult < 0) {
				log.info("insert a loan plan detail fail");
				return -1;
			} else {
				return ID;
			}
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
			return -1;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}

	}
/**
 * 修改财务分析主表
 * @param pInfo
 * @return
 * @throws Exception
 */
	public long updateFinanceanalysesave(LoanFinanceanalyse pInfo) throws Exception
	{
		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;
		try
		{
			conn = Database.getConnection();
			strSQL = " update LOAN_FINANCEANALYSE set creditid=?, clientid=?, ";
			strSQL+= "cycleyear=?, cyclemonth=?, isaudit=? ,repoetnameid=?, repoetname=?, status=?,inputuserid=?,inputdate=?,explain=? ,officeid=?,currencyid=?  where ID=? ";

			ps = conn.prepareStatement(strSQL);

			int n = 1;
			ps.setLong(n++, pInfo.getCreditid());
			ps.setLong(n++, pInfo.getClientid());
			ps.setString(n++, pInfo.getCycleyear());
			ps.setString(n++, pInfo.getCyclemonth());
			ps.setLong(n++, pInfo.getIsaudit());
			ps.setLong(n++, pInfo.getRepoetnameid());
			ps.setString(n++, pInfo.getRepoetname());
			ps.setLong(n++, pInfo.getStatus());
			ps.setLong(n++, pInfo.getInputuserid());
			ps.setTimestamp(n++, pInfo.getInputdate());
			ps.setString(n++, pInfo.getExplain());
			ps.setLong(n++, pInfo.getOfficeid());
			ps.setLong(n++, pInfo.getCurrencyid());
			ps.setLong(n++, pInfo.getId());

			lResult = ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
			if (lResult < 0)
			{
				log.info("update loan property info error:" + lResult);
				return -1;
			}
			else
			{
				return pInfo.getId();
			}
		}
		catch (Exception e)
		{

			cleanup(ps);
			cleanup(conn);
			e.printStackTrace();
			throw e;
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}

	}
	/**
	 * 财务分析保存
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long LoanFinanceitemdetailsave(LoanFinanceitemdetail info)
			throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;
		long ID = this.getMaxID(1);
		try {
			conn = Database.getConnection();
			strSQL = "insert into LOAN_FINANCEITEMDETAIL ("
					+ "id,itemid,financeid,reportnametype,reporttype, "
					+ "amount,cycleyear,cyclemonth,scale,explain,balancestate "
					+ ") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
			ps = conn.prepareStatement(strSQL);
			int n = 1;
			ps.setLong(n++, ID);
			ps.setLong(n++, info.getItemid());
			ps.setLong(n++, info.getFinanceid());
			ps.setLong(n++, info.getReportnametype());
			ps.setLong(n++, info.getReporttype());
			ps.setDouble(n++, info.getAmount());
			ps.setString(n++, info.getCycleyear());
			ps.setString(n++, info.getCyclemonth());
			ps.setDouble(n++, info.getScale());
			ps.setString(n++, info.getExplain());
			ps.setLong(n++, info.getBalancestate());
			lResult = ps.executeUpdate();
			if (lResult < 0) {
				log.info("insert a loan plan detail fail");
				return -1;
			} else {
				return ID;
			}
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
			return -1;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}

	}

	/**
	 * 财务分析修改
	 * 
	 * @param pdInfo
	 * @return
	 * @throws SQLException
	 */
	public long updateFinanceitemdetail(LoanFinanceitemdetail pdInfo)
			throws SQLException {
		PreparedStatement ps = null;
		// ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;
		try {
			log4j.info("\n=======调用保存方法　ＤＡＯ1====");
			conn = Database.getConnection();

			System.out.println("***dwj***进入Dao***上传块");
			strSQL = "update LOAN_FINANCEITEMDETAIL "
					+ "set itemid=?, financeid=?, "
					+ "reportnametype=?, reporttype=?, amount=?, cycleyear=?, "
					+ "cyclemonth=?, scale=? ,explain=? ,balancestate=?  "
					+ "   where ID=?";

			ps = conn.prepareStatement(strSQL);
			int n = 1;
			ps.setLong(n++, pdInfo.getItemid());
			ps.setLong(n++, pdInfo.getFinanceid());
			ps.setLong(n++, pdInfo.getReportnametype());
			ps.setLong(n++, pdInfo.getReporttype());
			ps.setDouble(n++, pdInfo.getAmount());
			ps.setString(n++, pdInfo.getCycleyear());
			ps.setString(n++, pdInfo.getCyclemonth());
			ps.setDouble(n++, pdInfo.getScale());
			ps.setString(n++, pdInfo.getExplain());
			ps.setLong(n++, pdInfo.getBalancestate());
			ps.setLong(n++, pdInfo.getId());
			lResult = ps.executeUpdate();
			log4j.info("\n=======调用保存方法　ＤＡＯ2====");
			cleanup(ps);
			cleanup(conn);

			if (lResult < 0) {
				log.info("update loan plan detail fail:" + lResult);
				return -1;
			} else {
				return pdInfo.getId();
			}

		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
			return -1;
		} finally {
			try {
				cleanup(ps);
				cleanup(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * 财务分析 通过年月查询
	 * 
	 * @param ear
	 * @param yue
	 * @return
	 * @throws Exception
	 */
	public Collection quetyFinanceitemdetail(String ear, String yue)
			throws Exception {

		Collection res = null;
		LoanFinanceitemdetail info = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		res = new ArrayList();
		try {
			log4j.info("\n=======findByID start====");
			conn = Database.getConnection();
			// 查询表ob_extend
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from LOAN_FINANCEITEMDETAIL  where 1=1");
			sbSQL.append(" where cycleyear=" + ear + " and   cyclemonth=" + yue
					+ "");
			log4j.info("\n=======findByID start====" + sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				info = new LoanFinanceitemdetail();
				info.setId(rs.getLong("ID"));
				info.setItemid(rs.getLong("itemid"));
				info.setFinanceid(rs.getLong("financeid"));
				info.setReportnametype(rs.getLong("reportnametype"));
				info.setReporttype(rs.getLong("reporttype"));
				info.setAmount(rs.getDouble("amount"));
				info.setCyclemonth(rs.getString("cyclemonth"));
				info.setCycleyear(rs.getString("cycleyear"));
				info.setScale(rs.getLong("scale"));
				info.setExplain(rs.getString("explain"));
				info.setBalancestate(rs.getLong("balancestate"));
				res.add(info);

			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception ex) {
				throw new Exception(ex.getMessage());
			}
		}
		return res;
	}

	/**
	 * 通过项目ID 年 月 查询
	 * 
	 * @param itemID
	 * @param cycleyears
	 * @param cyclemonths
	 * @return
	 * @throws Exception
	 */
	public List findFinanceitemdetailt(long clientid, String cycleyears,
			String cyclemonths) throws Exception {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		LoanFinanceitemdetail info = null;
		List lista = new ArrayList();
		try {
			conn = Database.getConnection();
			strSQL = " select a.* from LOAN_FINANCEITEMDETAIL a ,LOAN_FINANCEANALYSE b where b.id=a.FINANCEID and  b.CLIENTID="
					+ clientid
					+ " and  a.cycleyear="
					+ cycleyears
					+ " and a.cyclemonth=" + cyclemonths + " order by a.ITEMID ";

			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				info = new LoanFinanceitemdetail();
				info.setId(rs.getLong("ID"));
				info.setItemid(rs.getLong("itemid"));
				info.setFinanceid(rs.getLong("financeid"));
				info.setAmount(rs.getDouble("amount"));
				info.setExplain(rs.getString("explain"));
				lista.add(info);
			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
		return lista;
	}

	
	/**
	 * 通过主表ID查询主表
	 * @param clientid
	 * @return
	 * @throws Exception
	 */
	public LoanFinanceanalyse  findFinanceanalyse(long clientid) throws Exception {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		LoanFinanceanalyse info = null;
		
		try {
			conn = Database.getConnection();
			strSQL = " select * from  LOAN_FINANCEANALYSE where id="+clientid;


			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				info = new LoanFinanceanalyse();
				info.setIsaudit(rs.getLong("isaudit"));
				info.setId(rs.getLong("id"));
				
			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
		return info;
	}

	/**
	 * 取财务分析最大ID
	 * 
	 * @return
	 * @throws Exception
	 */
	public long getMaxID(long type) throws Exception {
		long lMaxID = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = null;
		try {
			if (type == 1) {
				strSQL = "select nvl(max(id)+1,1) from LOAN_FINANCEITEMDETAIL ";
			} else if (type == 2) {
				strSQL = "select nvl(max(id)+1,1) from LOAN_FINANCEANALYSE ";
			}

			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				lMaxID = rs.getLong(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(con);

		}
		return lMaxID;
	}
	
	public long findFinanceByCreditID(long creditID)
	{
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		long ID = -1;
		
		try
		{
			String sql = "select id from LOAN_FINANCEANALYSE where CREDITID = " + creditID;
			conn = Database.getConnection();
			statement = conn.createStatement();
			rs = statement.executeQuery(sql);
			if (!rs.next())
			{
				ID = -1;
			}
			else
			{
				ID = rs.getLong(1);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(statement);
				cleanup(conn);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return ID;
	}
	
	/**
	 * 查询财务报表分析info的List
	 * @author lcliu
	 * @ 08-11-18
	 */
	public List findFinanceAnalyseList(
			long clientIdFrom,
			long clientIdTo,
			long cycleYearFrom,
			long cycleMonthFrom,
			long cycleYearTo,
			long cycleMonthTo,
			String reportName,
			long isAudit
			)
	{
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		List lst = new ArrayList();
		
		// 生成SQL语句
		String SQL = "select a.id id,"
				   + "       creditid,"
				   + "       scode clientcode,"
				   + "       clientid,"
				   + "       b.sname clientname,"
				   + "       cycleyear,"
				   + "       cyclemonth,"
				   + "       isaudit,"
				   + "       repoetnameid,"
				   + "       repoetname,"
				   + "       reporttype,"
				   + "       status,"
				   + "       inputuserid,"
				   + "       c.sname inputusername,"
				   + "       inputdate,"
				   + "       explain,"
				   + "       officeid,"
				   + "       currencyid"
				   + "  from LOAN_FINANCEANALYSE a, client b, userinfo c"
				   + " where a.clientid = b.id"
		   		   + "   and a.inputuserid = c.id"
		   		   + "   and a.status in (" + LOANConstant.reportState.SAVE + ", " + LOANConstant.reportState.SUBMIT + ")";
		if (clientIdFrom != -1)
		{
			SQL += " and a.clientid >= " + clientIdFrom;
		}
		if (clientIdTo != -1)
		{
			SQL += " and a.clientid <= " + clientIdTo;
		}
		if (cycleYearFrom!=-1 && cycleYearTo!=-1)
		{
			if (cycleMonthFrom == -1)
			{
				cycleMonthFrom = 1;
			}
			if (cycleMonthTo == -1)
			{
				cycleMonthTo = 1;
			}
			if (cycleYearFrom == cycleYearTo)
			{
				SQL += " and to_number(a.cycleyear) = " + cycleYearFrom
					 + " and to_number(a.cyclemonth) >= " + cycleMonthFrom
					 + " and to_number(a.cyclemonth) <= " + cycleMonthTo;
			}
			else
			{
				SQL += " and ((to_number(a.cycleyear) = " + cycleYearFrom + " and to_number(a.cyclemonth) >= " + cycleMonthFrom
					 + ") or (to_number(a.cycleyear) > " + cycleYearFrom + " and to_number(a.cycleyear) < " + cycleYearTo
					 + ") or (to_number(a.cycleyear) = " + cycleYearTo + " and to_number(a.cyclemonth) <= " + cycleMonthTo + "))";
			}
		}
		else if (cycleYearFrom != -1)
		{
			if (cycleMonthFrom == -1)
			{
				cycleMonthFrom = 1;
			}
			SQL += " and ((to_number(a.cycleyear) = " + cycleYearFrom + " and to_number(a.cyclemonth) >= " + cycleMonthFrom
				 + ") or (to_number(a.cycleyear) > " + cycleYearFrom + "))";
		}
		else if (cycleYearTo != -1)
		{
			if (cycleMonthTo == -1)
			{
				cycleMonthTo = 1;
			}
			SQL += " and ((to_number(a.cycleyear) < " + cycleYearTo
				 + ") or (to_number(a.cycleyear) = " + cycleYearTo + " and to_number(a.cyclemonth) <= " + cycleMonthTo + "))";
		}
		if (reportName!=null && !reportName.equals(""))
		{
			SQL += " and a.repoetname = '" + reportName + "'";
		}
		if (isAudit != -1)
		{
			SQL += " and a.isaudit = " + isAudit;
		}
		SQL += " order by id";		// 按主键排序
		// SQL += " order by to_number(cycleyear), to_number(cyclemonth)";		// 按时间排序
		log4j.print(SQL);
		
		try
		{
			conn = Database.getConnection();
			statement = conn.createStatement();
			rs = statement.executeQuery(SQL);
			
			LoanFinanceanalyse info = null;
			while (rs.next())
			{
				info = new LoanFinanceanalyse();
				info.setId(rs.getLong("id"));
				info.setCreditid(rs.getLong("creditid"));
				info.setClientcode(rs.getString("clientcode"));
				info.setClientid(rs.getLong("clientid"));
				info.setClientname(rs.getString("clientname"));
				info.setCycleyear(rs.getString("cycleyear"));
				info.setCyclemonth(rs.getString("cyclemonth"));
				info.setIsaudit(rs.getLong("isaudit"));
				info.setRepoetnameid(rs.getLong("repoetnameid"));
				info.setRepoetname(rs.getString("repoetname"));
				info.setReporttype(rs.getLong("reporttype"));
				info.setStatus(rs.getLong("status"));
				info.setInputuserid(rs.getLong("inputuserid"));
				info.setInputusername(rs.getString("inputusername"));
				info.setInputdate(rs.getTimestamp("inputdate"));
				info.setExplain(rs.getString("explain"));
				info.setOfficeid(rs.getLong("officeid"));
				info.setCurrencyid(rs.getLong("currencyid"));
				lst.add(info);
			}
		}
		catch (Exception e)
		{
			lst = new ArrayList();
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
				}
				if (statement != null)
				{
					statement.close();
				}
				if (conn != null)
				{
					conn.close();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return lst;
	}
	
	/**
	 * 判断记录是否已经存在，若存在则返回其ID
	 * @author lcliu
	 * @param info
	 * @return
	 */
	public long findFinanceAnalyse(LoanFinanceanalyse info)
	{
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		long id = -1;
		
		try
		{
			String sql = "select id" +
						 "  from loan_financeanalyse" +
						 " where clientid = " + info.getClientid() +
						 "   and cycleyear = " + info.getCycleyear() +
						 "   and cyclemonth = " + info.getCyclemonth() +
						 "   and status in (" + LOANConstant.reportState.SAVE + ", " + LOANConstant.reportState.SUBMIT + ")";
			conn = Database.getConnection();
			statement = conn.createStatement();
			rs = statement.executeQuery(sql);
			if (rs.next())
			{
				id = rs.getLong(1);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			id = -1;
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
				}
				if (statement != null)
				{
					statement.close();
				}
				if (conn != null)
				{
					conn.close();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return id;
	}
	
	/**
	 * 查询财务报表分析info
	 * @author lcliu
	 * @ 08-11-18
	 */
	public LoanFinanceanalyse findFinanceAnalyse(long id)
	{
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		LoanFinanceanalyse info = new LoanFinanceanalyse();
		
		// 生成SQL语句
		String SQL = "select a.id id,"
				   + "       creditid,"
				   + "       clientid,"
				   + "       b.sname clientname,"
				   + "       b.scode clientcode,"
				   + "       cycleyear,"
				   + "       cyclemonth,"
				   + "       isaudit,"
				   + "       repoetnameid,"
				   + "       repoetname,"
				   + "       reporttype,"
				   + "       status,"
				   + "       inputuserid,"
				   + "       c.sname inputusername,"
				   + "       inputdate,"
				   + "       explain,"
				   + "       officeid,"
				   + "       currencyid"
				   + "  from LOAN_FINANCEANALYSE a, client b, userinfo c"
				   + " where a.clientid = b.id"
		   		   + "   and a.inputuserid = c.id"
		   		   + "   and a.id = " + id;
		log4j.print(SQL);
		
		try
		{
			conn = Database.getConnection();
			statement = conn.createStatement();
			rs = statement.executeQuery(SQL);
			
			if (!rs.next())
			{
				info.setId(-1);
			}
			else
			{
				info.setId(rs.getLong("id"));
				info.setCreditid(rs.getLong("creditid"));
				info.setClientcode(rs.getString("clientcode"));
				info.setClientid(rs.getLong("clientid"));
				info.setClientname(rs.getString("clientname"));
				info.setCycleyear(rs.getString("cycleyear"));
				info.setCyclemonth(rs.getString("cyclemonth"));
				info.setIsaudit(rs.getLong("isaudit"));
				info.setRepoetnameid(rs.getLong("repoetnameid"));
				info.setRepoetname(rs.getString("repoetname"));
				info.setReporttype(rs.getLong("reporttype"));
				info.setStatus(rs.getLong("status"));
				info.setInputuserid(rs.getLong("inputuserid"));
				info.setInputusername(rs.getString("inputusername"));
				info.setInputdate(rs.getTimestamp("inputdate"));
				info.setExplain(rs.getString("explain"));
				info.setOfficeid(rs.getLong("officeid"));
				info.setCurrencyid(rs.getLong("currencyid"));
			}
		}
		catch (Exception e)
		{
			info.setId(-1);
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
				}
				if (statement != null)
				{
					statement.close();
				}
				if (conn != null)
				{
					conn.close();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return info;
	}

	/**
	 * 查询财务报表分析info
	 * @author lcliu
	 * @ 08-11-30
	 */
	public LoanFinanceanalyse findFinanceAnalyse(long clientID, String cycleYear, String cycleMonth)
	{
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		LoanFinanceanalyse info = new LoanFinanceanalyse();
		
		// 生成SQL语句
		String SQL = "select a.id id,"
				   + "       creditid,"
				   + "       clientid,"
				   + "       b.sname clientname,"
				   + "       b.scode clientcode,"
				   + "       cycleyear,"
				   + "       cyclemonth,"
				   + "       isaudit,"
				   + "       repoetnameid,"
				   + "       repoetname,"
				   + "       reporttype,"
				   + "       status,"
				   + "       inputuserid,"
				   + "       c.sname inputusername,"
				   + "       inputdate,"
				   + "       explain,"
				   + "       officeid,"
				   + "       currencyid"
				   + "  from LOAN_FINANCEANALYSE a, client b, userinfo c"
				   + " where a.clientid = b.id"
		   		   + "   and a.inputuserid = c.id"
		   		   + "   and b.id = " + clientID
		   		   + "   and a.cycleyear = '" + cycleYear + "'"
		   		   + "   and a.cyclemonth = '" + cycleMonth + "'";
		log4j.print(SQL);
		
		try
		{
			conn = Database.getConnection();
			statement = conn.createStatement();
			rs = statement.executeQuery(SQL);
			
			if (!rs.next())
			{
				info.setId(-1);
			}
			else
			{
				info.setId(rs.getLong("id"));
				info.setCreditid(rs.getLong("creditid"));
				info.setClientcode(rs.getString("clientcode"));
				info.setClientid(rs.getLong("clientid"));
				info.setClientname(rs.getString("clientname"));
				info.setCycleyear(rs.getString("cycleyear"));
				info.setCyclemonth(rs.getString("cyclemonth"));
				info.setIsaudit(rs.getLong("isaudit"));
				info.setRepoetnameid(rs.getLong("repoetnameid"));
				info.setRepoetname(rs.getString("repoetname"));
				info.setReporttype(rs.getLong("reporttype"));
				info.setStatus(rs.getLong("status"));
				info.setInputuserid(rs.getLong("inputuserid"));
				info.setInputusername(rs.getString("inputusername"));
				info.setInputdate(rs.getTimestamp("inputdate"));
				info.setExplain(rs.getString("explain"));
				info.setOfficeid(rs.getLong("officeid"));
				info.setCurrencyid(rs.getLong("currencyid"));
			}
		}
		catch (Exception e)
		{
			info.setId(-1);
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
				}
				if (statement != null)
				{
					statement.close();
				}
				if (conn != null)
				{
					conn.close();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return info;
	}
	
	/**
	 * 插入财务报表分析数据
	 * @author lcliu
	 * @ 08-11-18
	 */
	public long insertFinanceAnalyse(LoanFinanceanalyse info)
	{
		Connection conn = null;
		Statement statement = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long id = -1;
		
		try
		{
			String SQL = "select max(id)+1 id from LOAN_FINANCEANALYSE";
			conn = Database.getConnection();
			statement = conn.createStatement();
			rs = statement.executeQuery(SQL);
			if (!rs.next())
			{
				id = -1;
			}
			else
			{
				id = rs.getLong("id");
			
				SQL = "insert into LOAN_FINANCEANALYSE(id, Creditid, Clientid, Cycleyear, Cyclemonth, Isaudit, Repoetnameid, Repoetname, Reporttype, Status, Inputuserid, Inputdate, Explain, Officeid, Currencyid)"
						+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				ps = conn.prepareStatement(SQL);
				ps.setLong(1, id);
				ps.setLong(2, info.getCreditid());
				ps.setLong(3, info.getClientid());
				ps.setString(4, info.getCycleyear());
				ps.setString(5, info.getCyclemonth());
				ps.setLong(6, info.getIsaudit());
				ps.setLong(7, info.getRepoetnameid());
				ps.setString(8, info.getRepoetname());
				ps.setLong(9, info.getReporttype());
				ps.setLong(10, info.getStatus());
				ps.setLong(11, info.getInputuserid());
				ps.setTimestamp(12, info.getInputdate());
				ps.setString(13, info.getExplain());
				ps.setLong(14, info.getOfficeid());
				ps.setLong(15, info.getCurrencyid());
				if (ps.executeUpdate() <= 0)
				{
					id = -1;
				}
			}
		}
		catch (Exception e)
		{
			id = -1;
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
				}
				if (statement != null)
				{
					statement.close();
				}
				if (ps != null)
				{
					ps.close();
				}
				if (conn != null)
				{
					conn.close();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return id;
	}
	
	/**
	 * 更新财务报表分析数据
	 * @author lcliu
	 * @param info
	 * @return
	 */
	public long updateFinanceAnalyse(LoanFinanceanalyse info)
	{
		Connection conn = null;
		PreparedStatement ps = null;
		long ID = -1;
		
		try
		{
			String sql = "update loan_financeanalyse set creditid = ?, clientid = ?, cycleyear = ?, cyclemonth = ?,"
				+ " isaudit = ?, repoetnameid = ?, repoetname = ?, reporttype = ?, status = ?, inputuserid = ?,"
				+ " inputdate = ?, explain = ?, officeid = ?, currencyid = ? where id = ?";
			conn = Database.getConnection();
			ps = conn.prepareStatement(sql);
			
			ps.setLong(1, info.getCreditid());
			ps.setLong(2, info.getClientid());
			ps.setString(3, info.getCycleyear());
			ps.setString(4, info.getCyclemonth());
			ps.setLong(5, info.getIsaudit());
			ps.setLong(6, info.getRepoetnameid());
			ps.setString(7, info.getRepoetname());
			ps.setLong(8, info.getReporttype());
			ps.setLong(9, info.getStatus());
			ps.setLong(10, info.getInputuserid());
			ps.setTimestamp(11, info.getInputdate());
			ps.setString(12, info.getExplain());
			ps.setLong(13, info.getOfficeid());
			ps.setLong(14, info.getCurrencyid());
			ps.setLong(15, info.getId());
			
			int result = ps.executeUpdate();
			if (result <= 0)
			{
				ID = -1;
			}
			else
			{
				ID = info.getId();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			ID = -1;
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
				}
				if (conn != null)
				{
					conn.close();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return ID;
	}
	
	/**
	 * 更新财务报表分析数据
	 * @author lcliu
	 * @version 08-11-18
	 * @param isAudit
	 * @param explain
	 * @param id
	 * @return boolean
	 */
	public boolean updateFinanceAnalyse(long isAudit, long reportNameId, String explain, long state, long id)
	{
		Connection conn = null;
		Statement statement = null;
		
		try
		{
			conn = Database.getConnection();
			statement = conn.createStatement();
			String sql = "update loan_financeanalyse set isaudit = " + isAudit
							+ ", repoetnameid = " + reportNameId
							+ ", status = " + state
							+ ", explain = '" + explain
							+ "' where id = " + id;
			log4j.print(sql);
			if (statement.executeUpdate(sql) <= 0)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			try
			{
				if (statement != null)
				{
					statement.close();
				}
				if (conn != null)
				{
					conn.close();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 删除财务报表分析数据
	 * @author lcliu
	 * @version 08-11-18
	 */
	public boolean deleteFinanceAnalyse(long id)
	{
		Connection conn = null;
		Statement statement = null;
		
		try
		{
			conn = Database.getConnection();
			statement = conn.createStatement();
			String sql = "update loan_financeanalyse set status = " + LOANConstant.reportState.DELETE
							+ " where id = " + id;
			if (statement.executeUpdate(sql) <= 0)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			try
			{
				if (statement != null)
				{
					statement.close();
				}
				if (conn != null)
				{
					conn.close();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	
	/**
	 * 查询LoanFinanceitemdetail info
	 * @author lcliu
	 * @param info
	 * @return 查询的LoanFinanceitemdetail的ID
	 */
	public long findFinanceDetail(LoanFinanceitemdetail info)
	{
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		long ID = -1;
		
		try
		{
			String sql = "select * from loan_financeitemdetail where financeid = " + info.getFinanceid()
						+ " and itemid = " + info.getItemid();
			conn = Database.getConnection();
			statement = conn.createStatement();
			rs = statement.executeQuery(sql);
			if (!rs.next())
			{
				ID = -1;
			}
			else
			{
				ID = rs.getLong(1);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			ID = -1;
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
				}
				if (statement != null)
				{
					statement.close();
				}
				if (conn != null)
				{
					conn.close();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return ID;
	}
	
	/**
	 * 插入财务报表分析数据数据项
	 * @author lcliu
	 * @version 08-11-28
	 * @return 插入的数据项的ID
	 */
	public long insertFinanceDetail(LoanFinanceitemdetail info)
	{
		Connection conn = null;
		Statement statement = null;
		long ID = -1;
		
		try
		{
			ID = new LoanFinanceitemdetailDao().getMaxID(1);
			String sql = "insert into loan_financeitemdetail(id, itemid, financeid, reportnametype," +
						 " reporttype, amount, cycleyear, cyclemonth, scale, explain, balancestate)" +
						 " values ("
					   + ID + ","
					   + info.getItemid() + ","
					   + info.getFinanceid() + ","
					   + info.getReportnametype() + ","
					   + info.getReporttype() + ","
					   + info.getAmount() + ",'"
					   + info.getCycleyear() + "','"
					   + info.getCyclemonth() + "',"
					   + info.getScale() + ",'"
					   + info.getExplain() + "',"
					   + info.getBalancestate() + ")";
			log.print(sql);
			conn = Database.getConnection();
			statement = conn.createStatement();
			int result = statement.executeUpdate(sql);
			if (result <= 0)
			{
				ID = -1;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			ID = -1;
		}
		finally
		{
			try
			{
				if (statement != null)
				{
					statement.close();
				}
				if (conn != null)
				{
					conn.close();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return ID;
	}
	
	/**
	 * 更新财务报表分析数据数据项
	 * @author lcliu
	 * @param info
	 * @return 更新的数据项的ID
	 */
	public long updateFinanceDetail(LoanFinanceitemdetail info)
	{
		Connection conn = null;
		PreparedStatement ps = null;
		long ID = -1;
		
		try
		{
			String sql = "update loan_financeitemdetail"
					   + "   set itemid         = ?,"
					   + "       financeid      = ?,"
					   + "       reportnametype = ?,"
					   + "       reporttype     = ?,"
					   + "       AMOUNT         = ?,"
					   + "       CYCLEYEAR      = ?,"
					   + "       CYCLEMONTH     = ?,"
					   + "       SCALE          = ?,"
					   + "       EXPLAIN        = ?,"
					   + "       BALANCESTATE   = ?"
					   + " where id = ?";
			conn = Database.getConnection();
			ps = conn.prepareStatement(sql);
			
			ps.setLong(1, info.getItemid());
			ps.setLong(2, info.getFinanceid());
			ps.setLong(3, info.getReportnametype());
			ps.setLong(4, info.getReporttype());
			ps.setDouble(5, info.getAmount());
			ps.setString(6, info.getCycleyear());
			ps.setString(7, info.getCyclemonth());
			ps.setDouble(8, info.getScale());
			ps.setString(9, info.getExplain());
			ps.setLong(10, info.getBalancestate());
			ps.setLong(11, info.getId());
			
			int result = ps.executeUpdate();
			if (result <= 0)
			{
				ID = -1;
			}
			else
			{
				ID = info.getId();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			ID = -1;
		}
		finally
		{
			try
			{
				if (conn != null)
				{
					conn.close();
				}
				if (ps != null)
				{
					try
					{
						ps.close();	
					}
					catch (Exception e)
					{
						System.out.println("======ps已关======");
					}
					
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return ID;
	}
	
	/**
	 * 插入财务报表分析数据数据项列表
	 * @author lcliu
	 * @version 08-11-29
	 */
	public boolean insertFinanceDetail(List lst)
	{
		long ID = -1;
		boolean flag = true;
		
		Iterator it = lst.iterator();
		while (it.hasNext())
		{
			LoanFinanceitemdetail info = (LoanFinanceitemdetail)it.next();
			ID = findFinanceDetail(info);
			if (ID <= 0)
			{
				ID = insertFinanceDetail(info);
			}
			else
			{
				info.setId(ID);
				ID = updateFinanceDetail(info);
			}
			if (ID <= 0)
			{
				flag = false;
			}
		}
		
		return flag;
	}
	
	/**
	 * 查询财务报表分析数据数据项列表
	 * @author lcliu
	 * @param financeId
	 * @version 08-11-29
	 */
	public Map findFinanceDetailList(long financeId)
	{
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		Map map = new HashMap();
		LoanFinanceitemdetail info = null;
		
		try
		{
			conn = Database.getConnection();
			statement = conn.createStatement();
			
			String sql = "select * from Loan_Financeitemdetail a where financeid = " + financeId + " order by itemid";
			log4j.print(sql);
			rs = statement.executeQuery(sql);
			while (rs.next())
			{
				info = new LoanFinanceitemdetail();
				info.setId(rs.getLong(1));
				info.setItemid(rs.getLong(2));
				info.setFinanceid(rs.getLong(3));
				info.setReportnametype(rs.getLong(4));
				info.setReporttype(rs.getLong(5));
				info.setAmount(rs.getDouble(6));
				info.setCycleyear(rs.getString(7));
				info.setCyclemonth(rs.getString(8));
				info.setScale(rs.getDouble(9));
				info.setExplain(rs.getString(10));
				info.setBalancestate(rs.getLong(11));
				map.put(String.valueOf(info.getItemid()), info);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
				}
				if (statement != null)
				{
					statement.close();
				}
				if (conn != null)
				{
					conn.close();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return map;
	}
	
	/**
	 * 根据客户ID查找最近的财务分析项
	 * @author lcliu
	 * @param clientID
	 * @return double数组：
	 * 				0--为资产规模
	 * 				1--净资产
	 * 				2--销售收入
	 * 				3--净利润
	 */
	public double[] findLatestFinancialItem(long clientID)
	{
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		long financeID = -1;
		double[] financialItem = new double[4];
		
		try
		{
			conn = Database.getConnection();
			statement = conn.createStatement();
			
			Calendar cal = Calendar.getInstance();
			int thisYear = cal.get(Calendar.YEAR);
			int thisMonth = cal.get(Calendar.MONTH) + 1;
			
			// 取当前月份的资产规模和净资产
			String sql = "select t.id"
					   + "  from loan_financeanalyse t"
					   + " where clientid = " + clientID
					   + "   and status != " + LOANConstant.reportState.REFUSE
					   + "   and cycleyear = " + thisYear
					   + "   and cyclemonth = " + thisMonth;
			log.print(sql);
			rs = statement.executeQuery(sql);
			if (rs.next())
			{
				financeID = rs.getLong(1);
				
				Map map = findFinanceDetailList(financeID);
				financialItem[0] = 10000 * (map.get("39")==null ? 0 : ((LoanFinanceitemdetail)map.get("39")).getAmount());
				financialItem[1] = 10000 * ((map.get("39")==null ? 0 : ((LoanFinanceitemdetail)map.get("39")).getAmount()) - (map.get("61")==null ? 0 : ((LoanFinanceitemdetail)map.get("61")).getAmount()));
			}
			
			// 取去年年底的销售收入和净利润
			sql = "select t.id"
				   + "  from loan_financeanalyse t"
				   + " where clientid = " + clientID
				   + "   and status != " + LOANConstant.reportState.REFUSE
				   + "   and cycleyear = " + (thisYear - 1)
				   + "   and cyclemonth = 12";
			log.print(sql);
			rs = statement.executeQuery(sql);
			if (rs.next())
			{
				financeID = rs.getLong(1);
				
				Map map = findFinanceDetailList(financeID);
				financialItem[2] = 10000 * (map.get("71")==null ? 0 : ((LoanFinanceitemdetail)map.get("71")).getAmount());
				financialItem[3] = 10000 * (map.get("89")==null ? 0 : ((LoanFinanceitemdetail)map.get("89")).getAmount());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (conn != null)
				{
					conn.close();
				}
				if (statement != null)
				{
					statement.close();
				}
				if (rs != null)
				{
					rs.close();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return financialItem;
	}
	/**
	 * 根据客户ID查找最近的财务分析项
	 * @author lcliu
	 * @param clientID
	 * @return double数组：
	 * 				0--为资产规模
	 * 				1--净资产
	 * 				2--销售收入
	 * 				3--净利润
	 */
	public double[] findLatestFinancialItem(long clientID, long creditGradeId)
	{
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		long financeID = -1;
		double[] financialItem = new double[4];
		
		try
		{
			conn = Database.getConnection();
			statement = conn.createStatement();

			int year = Integer.parseInt(DataFormat.formatDate(Env.getSystemDate()).split("-")[0]);
			int month = Integer.parseInt(DataFormat.formatDate(Env.getSystemDate()).split("-")[1]);
			String[] cycle = new CustomerfeedbackDao().queryCreditCycle(creditGradeId);
			if (cycle!=null && cycle[0]!=null && cycle[1]!=null)
			{
				year = Integer.parseInt(cycle[0]);
				month = Integer.parseInt(cycle[1]);
			}
			
			String sql = "select t.id"
					   + "  from loan_financeanalyse t"
					   + " where t.clientid = " + clientID
					   + "   and t.status != " + LOANConstant.reportState.REFUSE
					   + "   and t.cycleyear = '" + year + "'"
					   + "   and t.cyclemonth = '" + month + "'";
			log.print(sql);
			rs = statement.executeQuery(sql);
			if (rs.next())
			{
				financeID = rs.getLong(1);
				
				Map map = findFinanceDetailList(financeID);
				financialItem[0] = 10000 * (map.get("39")==null ? 0 : ((LoanFinanceitemdetail)map.get("39")).getAmount());
				financialItem[1] = 10000 * ((map.get("39")==null ? 0 : ((LoanFinanceitemdetail)map.get("39")).getAmount()) - (map.get("61")==null ? 0 : ((LoanFinanceitemdetail)map.get("61")).getAmount()));
				financialItem[2] = 10000 * (map.get("71")==null ? 0 : ((LoanFinanceitemdetail)map.get("71")).getAmount());
				financialItem[3] = 10000 * (map.get("89")==null ? 0 : ((LoanFinanceitemdetail)map.get("89")).getAmount());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (conn != null)
				{
					conn.close();
				}
				if (statement != null)
				{
					statement.close();
				}
				if (rs != null)
				{
					rs.close();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return financialItem;
	}
	
	/**
	 * 查询指定客户之前在指定年月之前12个月的主营业务收入之和
	 * @author lcliu
	 * @param clientID
	 * @param year
	 * @param month
	 * @return double
	 */
	public double getAmountOfLastYear(long clientID, long year, long month)
	{
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		long financeID = -1;
		double amount = 0;

		try
		{
			conn = Database.getConnection();
			statement = conn.createStatement();

			/*
			 * 在查询中对年月进行编码，编码规则为：年*100+月
			 * 例：2008年12月编码后为200812
			 * 
			 * 对2008年6月，查询开始时间是2007年7月，结束时间是2008年6月
			 * 对2008年12月，查询开始时间是2008年1月，结束时间是2008年12月
			 */
			long startDate = month==12 ? year*100+1 : (year-1)*100+month+1;		// 查询开始时间
			long endDate = year*100+month;		// 查询结束时间
			
			String sql = "select financeid"
					   + "  from (select t.id financeid,"
					   + "               to_number(t.cycleyear) * 100 + to_number(t.cyclemonth) cycledate"
					   + "          from loan_financeanalyse t"
					   + "         where clientid = " + clientID
					   + "           and status != " + LOANConstant.reportState.REFUSE
					   + "         order by cycledate desc)"
					   + " where cycledate >= " + startDate
					   + "   and cycledate <= " + endDate;
			log.print(sql);
			rs = statement.executeQuery(sql);
			while (rs.next())
			{
				financeID = rs.getLong(1);
				Map map = findFinanceDetailList(financeID);
				LoanFinanceitemdetail info = (LoanFinanceitemdetail)map.get("71");
				if (info != null)
				{
					amount += info.getAmount();
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (conn != null)
				{
					conn.close();
				}
				if (statement != null)
				{
					try
					{
						statement.close();
					}
					catch(Exception e)
					{
						System.out.println("=======statement已关======");
					}

				}
				if (rs != null)
				{
					try
					{
						rs.close();
					}
					catch(Exception e)
					{
						System.out.println("=======rs已关======");
					}

				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return amount;
	}
}




















