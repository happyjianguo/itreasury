/*
 * Created on 2004-07-19
 *
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.treasuryplan.setting.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.treasuryplan.util.TreasuryPlanDAO;
import com.iss.itreasury.treasuryplan.setting.dataentity.Trea_TPForecastDataInfo;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author              hewen hu
 * @version
 *  Date of Creation    2004-07-19
 */
public class Trea_TPForecastDataDAO extends TreasuryPlanDAO {
	protected Log4j log4j = new Log4j(Constant.ModuleType.PLAN, this);

	public Trea_TPForecastDataDAO() {
		super("Trea_TPForecastData");
	}

	public Trea_TPForecastDataDAO (Connection conn) {
		super("Trea_TPForecastData", conn);
	}

	/**
	 * 取最大的日期
	 * @param  nothing
	 * @return Timestamp
	 * @exception throws ITreasuryDAOException, SQLException
	 */
	public Timestamp getMaxTransactionDate() throws ITreasuryDAOException, SQLException {
		StringBuffer strSQL = new StringBuffer();

		strSQL.append("SELECT NVL(MAX(transactionDate), SYSDATE) transactionDate FROM Trea_TPForecastData \n");

		log4j.info("SQL="+strSQL.toString());

		Timestamp transactionDate=null;
		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			ResultSet rs = this.executeQuery();
			while (rs != null && rs.next()) {
				transactionDate = rs.getTimestamp("transactionDate");
			}
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			this.finalizeDAO();
		}

		return transactionDate;
	}

	/**
	 * 取最大的日期
	 * @param  String lineNo
	 * @return long
	 * @exception throws ITreasuryDAOException, SQLException
	 */
	public long getParentLineIDByLineNo(String lineNo) throws ITreasuryDAOException, SQLException {
		StringBuffer strSQL = new StringBuffer();

		strSQL.append("SELECT ID FROM Trea_TPForecastData WHERE lineNo = '"+lineNo+"' AND transActionDate = SYSDATE \n");

		log4j.info("SQL="+strSQL.toString());

		long id;
		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			ResultSet rs = this.executeQuery();
			id = -1;
			while (rs != null && rs.next()) {
				id = rs.getLong("ID");
			}
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally{
			this.finalizeDAO();
		}

		return id;
	}

	/**
	 * 通过条件更新
	 * @param  Trea_TPForecastDataInfo info
	 * @return void
	 * @exception throws ITreasuryDAOException, SQLException
	 */
	public void updateTPForecastData(Trea_TPForecastDataInfo info) throws ITreasuryDAOException, SQLException {
		StringBuffer strSQL = new StringBuffer();

		strSQL.append("UPDATE Trea_TPForecastData SET \n");
		strSQL.append("isLeaf = '"+info.getIsLeaf()+"', IsReadOnly = '"+info.getIsReadOnly()+"' \n");
		strSQL.append("WHERE 1 = 1 \n");
		strSQL.append("	AND lineID = "+info.getLineID()+" \n");

		log4j.info("SQL="+strSQL.toString());

		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			this.executeQuery();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally{
			this.finalizeDAO();
		}
	}

	/**
	 * 通过条件更新
	 * @param  Trea_TPForecastDataInfo info
	 * @return void
	 * @exception throws ITreasuryDAOException, SQLException
	 */
	public void deleteTPForecastData(Trea_TPForecastDataInfo info) throws ITreasuryDAOException, SQLException {
		StringBuffer strSQL = new StringBuffer();

		strSQL.append("DELETE FROM Trea_TPForecastData \n");
		strSQL.append("WHERE 1 = 1 \n");
		strSQL.append("	AND lineID = "+info.getLineID()+" \n");

		log4j.info("SQL="+strSQL.toString());

		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			this.executeQuery();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally{
			this.finalizeDAO();
		}
	}
}