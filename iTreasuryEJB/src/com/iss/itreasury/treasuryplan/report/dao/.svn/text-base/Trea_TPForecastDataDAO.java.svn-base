/*
 * Created on 2004-7-13
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.treasuryplan.report.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.treasuryplan.report.dataentity.TPForecastDataConditionInfo;
import com.iss.itreasury.treasuryplan.report.dataentity.TPForecastDataInfo;
import com.iss.itreasury.treasuryplan.util.TPConstant;
import com.iss.itreasury.treasuryplan.util.TPUtil;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log4j;
/**
 * @author wlming
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Trea_TPForecastDataDAO extends AbstractDestinationDataDAO
{
	protected Log4j log4j = new Log4j(Constant.ModuleType.PLAN, this);
	public Trea_TPForecastDataDAO()
	{
		super("Trea_TPForecastData");
		super.setUseMaxID();
		amountFieldName = "FORECASTAMOUNT";
	}
	public Trea_TPForecastDataDAO(Connection conn)
	{
		super("Trea_TPForecastData", conn);
		super.setUseMaxID();
		amountFieldName = "FORECASTAMOUNT";
	}
	/**
	 * 查询模板树总共有几级 
	 */
	public long getLevelCount() throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		long lTemp = -1;
		try
		{
			sbSQL = new StringBuffer();
			sbSQL.append(" select max(linelevel) \n");
			sbSQL.append(" from Trea_TPForecastData \n");
			log4j.info(sbSQL.toString());
			conn = Database.getConnection();
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lTemp = rs.getLong(1);
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
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception _ex)
			{
				System.out.println("关闭数据库连接时发生数据库错误！");
			}
		}
		return lTemp;
	}
	/**
	 * 根据条件查找预测数据
	 * @param conditionInfo
	 * @return Collection
	 * @throws Exception
	 */
	public Collection find(TPForecastDataConditionInfo conditionInfo) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector v = new Vector();
		StringBuffer sbSQL = null;
		try
		{
			con = Database.getConnection();
			sbSQL = new StringBuffer();
				sbSQL.append(" select forcastdata.* from Trea_TPForecastData forcastdata,trea_tptemplate template \n");
				sbSQL.append(" where 1=1 and forcastdata.lineID = template.ID \n");
				if (conditionInfo.getOfficeID() > 0)
				{
					sbSQL.append(" and forcastdata.OfficeID=" + conditionInfo.getOfficeID() + "\n");
				}
				if (conditionInfo.getCurrencyID() > 0)
				{
					sbSQL.append(" and forcastdata.CurrencyID=" + conditionInfo.getCurrencyID() + "\n");
				}
				if (conditionInfo.getAuthorizedUserID() > 0)
				{
					//sbSQL.append(" and (nvl(AuthorizedUserID,-1) < 0 or AuthorizedUserID="+conditionInfo.getAuthorizedUserID()+")\n");
					sbSQL.append(" and template.AuthorizedUser like '%<" + conditionInfo.getAuthorizedUserID() + ">%'\n");
				}
				if (conditionInfo.getAuthorizedDepartmentID() > 0)
				{
					//sbSQL.append(" and (nvl(AuthorizedDepartmentID,-1) < 0 or AuthorizedDepartmentID="+conditionInfo.getAuthorizedDepartmentID()+")\n");
					sbSQL.append(" and template.AuthorizedDepartment like '%<" + conditionInfo.getAuthorizedDepartmentID() + ">%'\n");
				}
				if (conditionInfo.getTransactionDateStart() != null)
				{
					sbSQL.append(" and forcastdata.TransactionDate>=to_date('" + DataFormat.getDateString(conditionInfo.getTransactionDateStart()) + "','yyyy-mm-dd')" + "\n");
				}
				if (conditionInfo.getTransactionDateEnd() != null)
				{
					sbSQL.append(" and forcastdata.TransactionDate<=to_date('" + DataFormat.getDateString(conditionInfo.getTransactionDateEnd()) + "','yyyy-mm-dd')" + "\n");
				}
				sbSQL.append(" order by forcastdata.LineNo,forcastdata.TransactionDate \n");
				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				while (rs.next())
				{
					TPForecastDataInfo info = new TPForecastDataInfo();
					info.setId(rs.getLong("ID"));
					//info.setAuthorizedDepartmentID(rs.getLong("AuthorizedDepartmentID"));
					//info.setAuthorizedUserID(rs.getLong("AuthorizedUserID"));
					info.setAuthorizedDepartment(rs.getString("authorizeddepartment"));
					info.setAuthorizedUser(rs.getString("authorizeduser"));
					info.setCurrencyID(rs.getLong("CurrencyID"));
					info.setExecuteDate(rs.getTimestamp("ExecuteDate"));
					info.setForecastAmount(rs.getDouble("ForecastAmount"));
					info.setInputTime(rs.getTimestamp("InputTime"));
					info.setIsLeaf(rs.getLong("IsLeaf"));
					info.setLineID(rs.getLong("LineID"));
					info.setLineLevel(rs.getLong("LineLevel"));
					info.setLineName(rs.getString("LineName"));
					info.setLineNo(rs.getString("LineNo"));
					info.setOfficeID(rs.getLong("OfficeID"));
					info.setParentLineID(rs.getLong("ParentLineID"));
					info.setPlanAmount(rs.getDouble("PlanAmount"));
					info.setTransactionDate(rs.getTimestamp("TransactionDate"));
					info.setIsReadOnly(rs.getLong("IsReadOnly"));
					info.setIsNeedSum(rs.getLong("isNeedSum"));
					v.add(info);
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
				if (rs != null)
				{
					rs.close();
				}
				if (ps != null)
				{
					ps.close();
				}
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
		}
		return ((v != null && v.size() > 0) ? v : null);
	}
	/**
	 * 更新预测数据（注意：是部分更新）
	 * @param conditionInfo
	 * @return Collection
	 * @throws Exception
	 */
	public void update(TPForecastDataInfo info) throws Exception
	{
		//Connection con = null;
		//PreparedStatement ps = null;		
		StringBuffer sbSQL = null;
		try
		{
			initDAO();
			//con = Database.getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" update Trea_TPForecastData \n");
			sbSQL.append(" set PlanAmount=?,InputTime=? \n");
			sbSQL.append(" where LineID=? and TransactionDate=? \n");	
			log4j.info(sbSQL.toString());
			PreparedStatement ps = prepareStatement(sbSQL.toString());
			//ps = con.prepareStatement(sbSQL.toString());
			ps.setDouble(1, info.getPlanAmount());
			ps.setTimestamp(2, info.getInputTime());
			ps.setLong(3, info.getLineID());
			ps.setTimestamp(4, info.getTransactionDate());
			//ps.executeUpdate();
			ps.executeUpdate();
			finalizeDAO();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			finalizeDAO();
		}
	}
	/**
	 * 从ResultSet中获取查询结果　
	 * @param className 需要查找的数据库表对应的Data Entity的类名
	 * @param 　
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection getDataEntitiesFromResultSet(Class dataEntityClass) throws ITreasuryDAOException
	{
		ArrayList resList = new ArrayList();
		try
		{
			while (transRS.next())
			{
				TPForecastDataInfo info = new TPForecastDataInfo();				
				setRStoBaseObject(transRS, info);
				info.setPlanAmount(transRS.getDouble("PlanAmount"));
				info.setTransactionDate(transRS.getTimestamp("TransactionDate"));
				info.setForecastAmount(transRS.getDouble("ForecastAmount"));
				info.setInputTime(transRS.getTimestamp("InputTime"));
				info.setIsReadOnly(transRS.getLong("IsReadOnly"));
				resList.add(info);
			}
		}
		catch (SQLException e)
		{
			throw new ITreasuryDAOException("", e);
		}
		return resList;
	}
	public static void main(String[] args)
	{
		try
		{
			//Connection tpConn = Database.getConnection();
			Timestamp currentDate = Timestamp.valueOf("2004-07-14 00:00:00.000000000");
			Timestamp forecastDate = Timestamp.valueOf("2004-07-02 00:00:00.000000000");
			Trea_TPForecastDataDAO dao = new Trea_TPForecastDataDAO();
			TPForecastDataInfo info = new TPForecastDataInfo();
			info.setAuthorizedUser("<2>");
			info.setAuthorizedDepartment("<2>");
			info.setForecastAmount(1000);
			info.setLineName("linename");
			info.setTransactionDate(currentDate);
			info.setLineNo("lineno");
			dao.updateByPK(info);
			//dao.finalizeDAO();
			//tpConn.close();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 *根据前一天的期末余额更新今天的起初余额
	 *在预测完成后调用
	 * */
	public void updateInitBalanceByLastDateEndBalance(long officeID, long currencyID, Timestamp date) throws Exception
	{
		updateInitBalanceByLastDateEndBalance(officeID, currencyID, date,1);
		updateInitBalanceByLastDateEndBalance(officeID, currencyID, date,2);
	}
	
	
	private void updateInitBalanceByLastDateEndBalance(long officeID, long currencyID, Timestamp date,long opreationType) throws Exception{
		long initMaintainFlag = -1;
		long endMaintainFlag = -1;
		if(opreationType == 1){
			initMaintainFlag = 2;
			endMaintainFlag = 3;			
		}
		else{
			initMaintainFlag = 4;
			endMaintainFlag = 5;						
		}
			
		try {
			String strDate = transferTimestampToTo_DateString(date);
			Timestamp lastDate = DataFormat.getNextDate(date, -1);
			String strLastDate = transferTimestampToTo_DateString(lastDate);
			StringBuffer bufferString = new StringBuffer();
			bufferString.append("update Trea_TPForecastData set forecastamount = ");
			bufferString.append(" (select forecastamount From Trea_TPForecastData where officeid= " + officeID + " and CURRENCYID= " + currencyID);
			bufferString.append(" and transactiondate=" + strLastDate + " and lineid = ");
			bufferString.append(" (select id from trea_tptemplate where MaintenanceFlag ="+endMaintainFlag+" and officeid= " + officeID + " and CURRENCYID=" + currencyID + " ))  ");
			bufferString.append(" where officeid= " + officeID + " and currencyid= " + currencyID + " and transactiondate= " + strDate);
			bufferString.append(" and lineid = (select id from trea_tptemplate where MaintenanceFlag ="+initMaintainFlag+" and officeid= " + officeID + " and CURRENCYID= " + currencyID + ")");
			prepareStatement(bufferString.toString());
			executeUpdate();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}				
	}
	
	/**
	 * 为重新计算与“期初余额”和“期末余额”有关的行项目获取数据
	 * */
	public Collection getDataAboutIntAndEndBalance(long officeID, long currencyID, Timestamp date) throws Exception
	{
		Collection c;
		try {
			String strDate = transferTimestampToTo_DateString(date);
			StringBuffer bufferString = new StringBuffer();
			bufferString.append(" select  distinct a.* from Trea_TPForecastData a , trea_tptemplate b, ");
			bufferString.append(" Trea_TPTemplateitem c where a.officeid = " + officeID + " and a.CURRENCYID= " + currencyID);
			bufferString.append(" and a.TransactionDate =  " + strDate);
			bufferString.append(" and b.id=a.lineid and b.MaintenanceFlag in (2,3) and c.Parameter like '#'|| to_char(b.id)  ");
			prepareStatement(bufferString.toString());
			ResultSet localRS = executeQuery();
			c = this.getDataEntitiesFromResultSet(null);
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}

		return c;
	}
	/**
	 * 检查用户所属于的部门/公司资金计划内，是否有未完成的资金计划版本（状态不等于“已审核”或“已拒绝”）
	 * @param conditionInfo
	 * @return
	 * @throws Exception
	 */
	public boolean isContainUncompletePlan(TPForecastDataConditionInfo conditionInfo) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		boolean bRtn = false;
		Vector aList = null;
		long firmDepID = -1;
		try
		{
			conn = Database.getConnection();
			firmDepID = TPUtil.getCompanyID();
			sbSQL = null;
			sbSQL = new StringBuffer();
			sbSQL.append("select * from Trea_TreasuryPlan \n");
			sbSQL.append(" where 1=1 \n");
			sbSQL.append(" and ( (departmentID=" + conditionInfo.getAuthorizedDepartmentID() + " ) or (departmentID=" + firmDepID + ") )");		
			sbSQL.append(" and (");
			sbSQL.append(" (to_char(StartDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(conditionInfo.getTransactionDateStart())+"'");
			sbSQL.append(" and to_char(endDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(conditionInfo.getTransactionDateStart()) + "') ");
			sbSQL.append(" or( to_char(startDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(conditionInfo.getTransactionDateEnd()) + "'");
			sbSQL.append(" and to_char(endDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(conditionInfo.getTransactionDateEnd()) + "') ");
			sbSQL.append(" or( to_char(startDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(conditionInfo.getTransactionDateStart()) + "'");
			sbSQL.append(" and to_char(endDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(conditionInfo.getTransactionDateEnd()) + "')");
			sbSQL.append(" or( to_char(startDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(conditionInfo.getTransactionDateStart()) + "'");
			sbSQL.append(" and to_char(endDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(conditionInfo.getTransactionDateEnd()) + "') ");
			sbSQL.append(")");
			sbSQL.append(" and (statusID = " + TPConstant.PlanVersionStatus.SAVE);
			sbSQL.append(" or statusID = " + TPConstant.PlanVersionStatus.SUBMIT+")");
			
			log4j.print(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString()); 
			rs = ps.executeQuery();
			if (rs.next())
			{
				log4j.print("区间内有未完成的资金计划！");
				bRtn = true;
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
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			throw e;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw e;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if (conn != null)
			{
				conn.close();
				conn = null;
			}
		}
		return bRtn;
	}
	
	public void updatePlanAmountAfterTransform(long officeID,long currencyID,Timestamp executeDate) throws Exception{
		try {
			String strDate = this.transferTimestampToTo_DateString(executeDate); 
			String strSQL = "update trea_tpforecastdata set planamount = forecastamount where inputtime is null and forecastamount != 0 and executeDate = "+ strDate
			+" and OFFICEID = "+ officeID + " and CURRENCYID = "+currencyID;
			this.prepareStatement(strSQL);
			this.executeQuery();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}

		
	}
	
	/**
	 * 汇总企业存款期初余额
	 * */
	public double sumEnterpriseDepositInitBalance(long officeID,long currencyID) throws Exception{
		String strSQL = "select sum(MBALANCE) initBalance from sett_account acc,sett_subaccount sub where sub.naccountid = acc.id and acc.NACCOUNTTYPEID in(1,2,3) and sub.nstatusid = 1" 
				+" and NOFFICEID = "+ officeID + " and NCURRENCYID = "+currencyID;
		double initBalance;
		try {
			initBalance = 0.0;
			this.prepareStatement(strSQL);
			ResultSet localRS = this.executeQuery();
			if(localRS.next())
				initBalance = localRS.getDouble("initBalance");
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}

		return initBalance;
	}
	
/*	*//**
	 * 更新预测数据第一天的期初余额
	 * *//*
	public void updateFirstDayInitBalance(Timestamp firstForecastDate) throws Exception{
		double initBalance = sumEnterpriseDepositInitBalance();
		String strDate = this.transferTimestampToTo_DateString(firstForecastDate); 
		String strSQL = "update trea_tpforecastdata set forecastamount = "+initBalance+" where transactiondate =  "+ strDate;
		this.prepareStatement(strSQL);
		this.executeQuery();
		this.finalizeDAO();		
	}*/
	
	public double updateAmountByLineIDAndTransactionDate(long officeID,long currencyID,double amount,long lineID,Timestamp transDate) throws Exception{
		try {
			String strDate = this.transferTimestampToTo_DateString(transDate);
			String strSQL = " update  "+strTableName+"  set forecastamount = "+amount+ " where transactiondate = "+strDate+" and lineid =" + lineID
			+" and OFFICEID = "+ officeID + " and CURRENCYID = "+currencyID;
			this.prepareStatement(strSQL);
			this.executeUpdate();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}

		return amount;
	}
	/* (non-Javadoc)
	 * @see com.iss.itreasury.treasuryplan.report.dao.AbstractDestinationDataDAO#resetAmountFieldName()
	 */
	public void resetAmountFieldName() {
		amountFieldName = "FORECASTAMOUNT";	
	}	 			
	
/*	public double updatePlanAmountByLineIDAndTransactionDate(long officeID,long currencyID,double amount,long lineID,Timestamp transDate) throws Exception{
		String strDate = this.transferTimestampToTo_DateString(transDate);
		String strSQL = "update Trea_TreasuryPlanDetail set planamount = "+amount+" where TREASURYPLANID in (select id from Trea_TreasuryPlan where officeid = "+ officeID + " and currencyid = "+currencyID+") " 
		+" and TransactionDate = "+strDate
		+" and lineid =  "+lineID;
		this.prepareStatement(strSQL);
		this.executeUpdate();
		finalizeDAO();
		return amount;
	}	*/
}
