/*
 * Created on 2004-07-28
 *
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.treasuryplan.mywork.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.treasuryplan.util.TPConstant;
import com.iss.itreasury.treasuryplan.util.TreasuryPlanDAO;
import com.iss.itreasury.treasuryplan.mywork.dataentity.MyWorkInfo;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2004
 * Company:             iSoftStone
 * @author              hewen hu
 * @version
 *  Date of Creation    2004-07-28
 */
public class MyWorkDAO extends TreasuryPlanDAO {
	protected Log4j log4j = new Log4j(Constant.ModuleType.PLAN, this);

	public MyWorkDAO() {
		super("Trea_TreasuryPlan");
	}

	/**
	 * 通过状态取得资金计划数量(待审核)
	 * @param  MyWorkInfo info
	 * @return long
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public long getNoCheckCountByStatusID(MyWorkInfo info) throws SQLException, ITreasuryDAOException {
		long treasuryplanCount = 0;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT COUNT(*) treasuryplanCount \n");
		strSQL.append("FROM Trea_TreasuryPlan \n");
		strSQL.append("WHERE 1 = 1 \n");
		strSQL.append("	AND officeID = "+info.getOfficeID()+" \n");
		strSQL.append("	AND currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("	AND nextCheckUserID = "+info.getNextCheckUserID()+" \n");
		strSQL.append("	AND departmentID = "+info.getDepartmentID()+" \n");
		strSQL.append("	AND statusID = "+TPConstant.PlanVersionStatus.SUBMIT+" \n");

		log4j.info("SQL="+strSQL.toString());

		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			ResultSet rs = this.executeQuery();
			while (rs != null && rs.next()) {
				treasuryplanCount = rs.getLong("treasuryplanCount");
			}
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}


		return treasuryplanCount;
	}

	/**
	 * 通过状态取得资金计划数量(撰写)
	 * @param  MyWorkInfo info
	 * @return long
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public long getSaveCountByStatusID(MyWorkInfo info) throws SQLException, ITreasuryDAOException {
		long treasuryplanCount = 0;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT COUNT(*) treasuryplanCount \n");
		strSQL.append("FROM Trea_TreasuryPlan \n");
		strSQL.append("WHERE 1 = 1 \n");
		strSQL.append("	AND officeID = "+info.getOfficeID()+" \n");
		strSQL.append("	AND currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("	AND inputUserID = "+info.getInputUserID()+" \n");
		strSQL.append("	AND departmentID = "+info.getDepartmentID()+" \n");
		strSQL.append("	AND statusID = "+TPConstant.PlanVersionStatus.SAVE+" \n");

		log4j.info("SQL="+strSQL.toString());

		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			ResultSet rs = this.executeQuery();
			while (rs != null && rs.next()) {
				treasuryplanCount = rs.getLong("treasuryplanCount");
			}
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}


		return treasuryplanCount;
	}

	/**
	 * 通过状态取得资金计划数量(已提交)
	 * @param  MyWorkInfo info
	 * @return long
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public long getSubmitCountByStatusID(MyWorkInfo info) throws SQLException, ITreasuryDAOException {
		long treasuryplanCount = 0;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT COUNT(*) treasuryplanCount \n");
		strSQL.append("FROM Trea_TreasuryPlan \n");
		strSQL.append("WHERE 1 = 1 \n");
		strSQL.append("	AND officeID = "+info.getOfficeID()+" \n");
		strSQL.append("	AND currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("	AND inputUserID = "+info.getInputUserID()+" \n");
		strSQL.append("	AND departmentID = "+info.getDepartmentID()+" \n");
		strSQL.append("	AND statusID = "+TPConstant.PlanVersionStatus.SUBMIT+" \n");

		log4j.info("SQL="+strSQL.toString());

		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			ResultSet rs = this.executeQuery();
			while (rs != null && rs.next()) {
				treasuryplanCount = rs.getLong("treasuryplanCount");
			}
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}


		return treasuryplanCount;
	}

	/**
	 * 通过状态取得资金计划数量(已审核)
	 * @param  MyWorkInfo info
	 * @return long
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public long getCheckCountByStatusID(MyWorkInfo info) throws SQLException, ITreasuryDAOException {
		long treasuryplanCount = 0;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT COUNT(*) treasuryplanCount \n");
		strSQL.append("FROM Trea_TreasuryPlan \n");
		strSQL.append("WHERE 1 = 1 \n");
		strSQL.append("	AND officeID = "+info.getOfficeID()+" \n");
		strSQL.append("	AND currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("	AND departmentID = "+info.getDepartmentID()+" \n");
		strSQL.append("	AND statusID = "+TPConstant.PlanVersionStatus.CHECK+" \n");

		log4j.info("SQL="+strSQL.toString());

		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			ResultSet rs = this.executeQuery();
			while (rs != null && rs.next()) {
				treasuryplanCount = rs.getLong("treasuryplanCount");
			}
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}

		return treasuryplanCount;
	}

	/**
	 * 通过状态取得资金计划数量(已拒绝)
	 * @param  MyWorkInfo info
	 * @return long
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public long getRefuseCountByStatusID(MyWorkInfo info) throws SQLException, ITreasuryDAOException {
		long treasuryplanCount = 0;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT COUNT(*) treasuryplanCount \n");
		strSQL.append("FROM Trea_TreasuryPlan \n");
		strSQL.append("WHERE 1 = 1 \n");
		strSQL.append("	AND officeID = "+info.getOfficeID()+" \n");
		strSQL.append("	AND currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("	AND departmentID = "+info.getDepartmentID()+" \n");
		strSQL.append("	AND statusID = "+TPConstant.PlanVersionStatus.REFUSE+" \n");

		log4j.info("SQL="+strSQL.toString());

		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			ResultSet rs = this.executeQuery();
			while (rs != null && rs.next()) {
				treasuryplanCount = rs.getLong("treasuryplanCount");
			}
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}


		return treasuryplanCount;
	}
}