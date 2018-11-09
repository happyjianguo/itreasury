/*
 * Created on 2004-07-28
 *
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.budget.mywork.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.budget.bizdelegation.ConstituteDelegation;
import com.iss.itreasury.budget.dao.BudgetDAO;
import com.iss.itreasury.budget.exception.BudgetException;
import com.iss.itreasury.dao.ITreasuryDAOException;

import com.iss.itreasury.budget.mywork.dataentity.MyWorkInfo;
import com.iss.itreasury.budget.util.BUDGETConstant;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2004
 * Company:             iSoftStone
 * @author              hewen hu
 * @version
 *  Date of Creation    2004-07-28
 */
public class MyWorkDAO extends BudgetDAO {
	protected Log4j log4j = new Log4j(Constant.ModuleType.BUDGET, this);

	public MyWorkDAO() {
		super("Trea_TreasuryPlan");
	}

	/**
	 * 通过状态取得预算数量(待审核)
	 * @param  MyWorkInfo info
	 * @return long
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public long getNoCheckCountByStatusID(MyWorkInfo info) throws Exception {
		long budgetCount = 0;
//		StringBuffer strSQL = new StringBuffer();
//		strSQL.append("SELECT COUNT(*) budgetCount \n");
//		strSQL.append("FROM BUDGET_PLAN \n");
//		strSQL.append("WHERE 1 = 1 \n");
//		strSQL.append("	AND officeID = "+info.getOfficeID()+" \n");
//		strSQL.append("	AND clientID = "+info.getClientID()+" \n");
//		strSQL.append("	AND currencyID = "+info.getCurrencyID()+" \n");
//		strSQL.append("	AND nextCheckUserID = "+info.getNextCheckUserID()+" \n");		
//		strSQL.append("	AND (statusID = "+ BUDGETConstant.ConstituteStatus.COMMIT +" or statusID = "+ BUDGETConstant.ConstituteStatus.RECEIVE +") \n");
//
//		log4j.info("SQL="+strSQL.toString());
//
//		try {
//			this.initDAO();
//			this.prepareStatement(strSQL.toString());
//			ResultSet rs = this.executeQuery();
//			while (rs != null && rs.next()) {
//				budgetCount = rs.getLong("budgetCount");
//			}
//		} catch (ITreasuryDAOException e) {
//			e.printStackTrace();
//			throw e;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw e;
//		} finally {
//			finalizeDAO();
//		}
		



		return budgetCount;
	}

	/**
	 * 通过状态取得预算数量(撰写)
	 * @param  MyWorkInfo info
	 * @return long
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public long getSaveCountByStatusID(MyWorkInfo info) throws SQLException, ITreasuryDAOException {
		long budgetCount = 0;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT COUNT(*) budgetCount \n");
		strSQL.append("FROM BUDGET_PLAN \n");
		strSQL.append("WHERE 1 = 1 \n");
		//strSQL.append("	AND clientID = "+info.getClientID()+" \n");
		strSQL.append("	AND officeID = "+info.getOfficeID()+" \n");
		strSQL.append("	AND currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("	AND inputUserID = "+info.getInputUserID()+" \n");		
		strSQL.append("	AND (statusID = "+BUDGETConstant.ConstituteStatus.SAVE+" or statusID = "+BUDGETConstant.ConstituteStatus.RETURN+") \n");

		log4j.info("SQL="+strSQL.toString());

		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			ResultSet rs = this.executeQuery();
			while (rs != null && rs.next()) {
				budgetCount = rs.getLong("budgetCount");
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


		return budgetCount;
	}

	/**
	 * 通过状态取得预算数量(已提交)
	 * @param  MyWorkInfo info
	 * @return long
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public long getSubmitCountByStatusID(MyWorkInfo info) throws SQLException, ITreasuryDAOException {
		long budgetCount = 0;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT COUNT(*) budgetCount \n");
		strSQL.append("FROM BUDGET_PLAN \n");
		strSQL.append("WHERE 1 = 1 \n");
		//strSQL.append("	AND clientID = "+info.getClientID()+" \n");
		strSQL.append("	AND officeID = "+info.getOfficeID()+" \n");
		strSQL.append("	AND currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("	AND inputUserID = "+info.getInputUserID()+" \n");
		
		strSQL.append("	AND statusID = "+BUDGETConstant.ConstituteStatus.COMMIT+" \n");

		log4j.info("SQL="+strSQL.toString());

		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			ResultSet rs = this.executeQuery();
			while (rs != null && rs.next()) {
				budgetCount = rs.getLong("budgetCount");
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


		return budgetCount;
	}

	

	/**
	 * 通过状态取得资金计划数量(已拒绝)
	 * @param  MyWorkInfo info
	 * @return long
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public long getRefuseCountByStatusID(MyWorkInfo info) throws SQLException, ITreasuryDAOException {
		long budgetCount = 0;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT COUNT(*) budgetCount \n");
		strSQL.append("FROM BUDGET_PLAN \n");
		strSQL.append("WHERE 1 = 1 \n");		
		strSQL.append("	AND clientID = "+info.getClientID()+" \n");
		strSQL.append("	AND officeID = "+info.getOfficeID()+" \n");
		strSQL.append("	AND currencyID = "+info.getCurrencyID()+" \n");
	
		strSQL.append("	AND statusID = "+BUDGETConstant.ConstituteStatus.REFUSE+" \n");

		log4j.info("SQL="+strSQL.toString());

		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			ResultSet rs = this.executeQuery();
			while (rs != null && rs.next()) {
				budgetCount = rs.getLong("budgetCount");
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


		return budgetCount;
	}
}