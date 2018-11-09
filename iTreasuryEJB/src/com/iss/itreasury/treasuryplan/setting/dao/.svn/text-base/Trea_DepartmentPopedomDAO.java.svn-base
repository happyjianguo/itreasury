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

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.treasuryplan.util.TreasuryPlanDAO;
import com.iss.itreasury.treasuryplan.setting.dataentity.Trea_OtherIndustryInfo;
import com.iss.itreasury.treasuryplan.setting.dataentity.Trea_TPForecastDataInfo;
import com.iss.itreasury.treasuryplan.setting.dataentity.Trea_DepartmentPopedomInfo;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author              hewen hu
 * @version
 *  Date of Creation    2004-07-19
 */
public class Trea_DepartmentPopedomDAO extends TreasuryPlanDAO {
	protected Log4j log4j = new Log4j(Constant.ModuleType.PLAN, this);
	private String parentLineIDs = "";

	public Trea_DepartmentPopedomDAO() {
		super("Department");
	}

	public Trea_DepartmentPopedomDAO (Connection conn) {
		super("Department", conn);
	}

	/**
	 * 通过行项目
	 * @param  Trea_TPForecastDataInfo info
	 * @return long
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public long findIdByLineID(Trea_TPForecastDataInfo info) throws SQLException, ITreasuryDAOException {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT ID FROM Trea_TPForecastData \n");
		strSQL.append("WHERE 1 = 1 \n");
		strSQL.append("	AND lineID = "+info.getLineID()+" \n");
		strSQL.append("	AND currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("	AND officeID = "+info.getOfficeID()+" \n");

		log4j.info("SQL="+strSQL.toString());

		long result;
		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			ResultSet rs = this.executeQuery();
			result = -1;
			if (rs != null && rs.next()) {
				result = rs.getLong("ID");
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
		
		return result;
	}

	/**
	 * 通过条件查找父级记录
	 * @param  Trea_OtherIndustryInfo info
	 * @return void
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public void findParentRecordByID(Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT DISTINCT lineID, parentLineID FROM Trea_TPForecastData \n");
		strSQL.append("WHERE 1 = 1 \n");
		strSQL.append("	AND lineID = "+info.getParentLineID()+" \n");
		strSQL.append("	AND currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("	AND officeID = "+info.getOfficeID()+" \n");
		//strSQL.append("	AND transActionDate = SYSDATE \n");

		log4j.info("SQL="+strSQL.toString());

		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			ResultSet rs = this.executeQuery();
			if (rs != null && rs.next()) {
				this.parentLineIDs = this.parentLineIDs + "," + String.valueOf(rs.getLong("lineID"));
				long parentLineID = rs.getLong("parentLineID");
				this.finalizeDAO();
				if (parentLineID != -1) {
					info.setParentLineID(parentLineID);
					this.findParentRecordByID(parentLineIDs, info);
				}
			}
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			this.finalizeDAO();
		}
	}

	/**
	 * 通过条件查找父级记录
	 * @param  String parentLineIDs, Trea_OtherIndustryInfo info
	 * @return String
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public String findParentRecordByID(String parentLineIDs, Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		this.parentLineIDs = parentLineIDs;
		this.findParentRecordByID(info);
		return this.parentLineIDs;
	}

	/**
	 * update表Trea_TPForecastData
	 * @param  String parentLineIDs, Trea_OtherIndustryInfo info
	 * @return nothing
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public void updateDepartmentID(String parentLineIDs, Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		StringBuffer strSQL = new StringBuffer();

		strSQL.append("UPDATE Trea_TPForecastData \n");
		strSQL.append("SET authorizedDepartment = authorizedDepartment || '<"+info.getAuthorizedDepartment()+">' \n");
		strSQL.append("WHERE 1 = 1 \n");
		strSQL.append("	AND NVL(authorizedDepartment, 'NULL') NOT LIKE '%<"+info.getAuthorizedDepartment()+">%' \n");
		strSQL.append("	AND lineID IN ("+parentLineIDs+") \n");
		strSQL.append("	AND officeID = "+info.getOfficeID()+" \n");
		strSQL.append("	AND currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("	AND transactionDate >= SYSDATE \n");

		log4j.info("SQL="+strSQL.toString());

		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			this.executeUpdate();
			this.finalizeDAO();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			this.finalizeDAO();
		}
	}

	/**
	 * update表Trea_TPForecastData
	 * @param  Trea_OtherIndustryInfo info, String authorizedDepartment
	 * @return int
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public int updateDepartmentID(Trea_OtherIndustryInfo info, String authorizedDepartment) throws SQLException, ITreasuryDAOException {
		StringBuffer strSQL = new StringBuffer();

		strSQL.append("UPDATE Trea_TPForecastData \n");
		strSQL.append("SET authorizedDepartment = '"+authorizedDepartment+"' \n");
		strSQL.append("WHERE 1 = 1 \n");
		//strSQL.append("	AND NVL(authorizedDepartment, 'NULL') NOT LIKE '%<"+info.getAuthorizedDepartment()+">%' \n");
		strSQL.append("	AND authorizedUser IS NULL \n");
		strSQL.append("	AND lineID = "+info.getId()+" \n");
		strSQL.append("	AND officeID = "+info.getOfficeID()+" \n");
		strSQL.append("	AND currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("	AND transactionDate >= SYSDATE \n");

		log4j.info("SQL="+strSQL.toString());

		int count;
		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			count = this.executeUpdate();
			this.finalizeDAO();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			this.finalizeDAO();
		}

		return count;
	}

	/**
	 * update表Trea_TPForecastData
	 * @param  String parentLineIDs, Trea_OtherIndustryInfo info
	 * @return nothing
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public void updateUserID(String parentLineIDs, Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		StringBuffer strSQL = new StringBuffer();

		strSQL.append("UPDATE Trea_TPForecastData \n");
		strSQL.append("SET authorizedUser = authorizedUser || '<"+info.getAuthorizedUser()+">' \n");
		strSQL.append("WHERE 1 = 1 \n");
		strSQL.append("	AND NVL(authorizedUser, 'NULL') NOT LIKE '%<"+info.getAuthorizedUser()+">%' \n");
		strSQL.append("	AND lineID IN ("+parentLineIDs+") \n");
		strSQL.append("	AND officeID = "+info.getOfficeID()+" \n");
		strSQL.append("	AND currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("	AND transactionDate >= SYSDATE \n");

		log4j.info("SQL="+strSQL.toString());

		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			this.executeUpdate();
			this.finalizeDAO();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally{
			this.finalizeDAO();
		}
	}

	/**
	 * update表Trea_TPForecastData
	 * @param  Trea_OtherIndustryInfo info, String authorizedUser
	 * @return nothing
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public void updateUserID(Trea_OtherIndustryInfo info, String authorizedUser) throws SQLException, ITreasuryDAOException {
		StringBuffer strSQL = new StringBuffer();

		strSQL.append("UPDATE Trea_TPForecastData \n");
		strSQL.append("SET authorizedUser = '"+authorizedUser+"' \n");
		strSQL.append("WHERE 1 = 1 \n");
		//strSQL.append("	AND NVL(authorizedUser, 'NULL') NOT LIKE '%<"+info.getAuthorizedUser()+">%' \n");
		strSQL.append("	AND lineID = "+info.getId()+" \n");
		strSQL.append("	AND officeID = "+info.getOfficeID()+" \n");
		strSQL.append("	AND currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("	AND transactionDate >= SYSDATE \n");

		log4j.info("SQL="+strSQL.toString());

		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			this.executeUpdate();
			this.finalizeDAO();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally{
			this.finalizeDAO();
		}
	}

	/**
	 * 通过条件查找
	 * @param  Trea_DepartmentPopedomInfo info
	 * @return Collection
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public Collection findByCondition(Trea_DepartmentPopedomInfo info) throws SQLException, ITreasuryDAOException {
		StringBuffer strSQL = new StringBuffer();

		strSQL.append("SELECT * FROM Department \n");
		strSQL.append("WHERE 1 = 1 \n");
		strSQL.append("	AND NVL(isCompanyLevel, -1) != 1 \n");
		strSQL.append("	AND officeID = "+info.getOfficeID()+" \n");
		strSQL.append("	AND statusID = "+info.getStatusID()+" \n");

		log4j.info("SQL="+strSQL.toString());

		this.initDAO();
		Collection collection;
		try {
			this.prepareStatement(strSQL.toString());
			this.executeQuery();
			collection = this.getDataEntitiesFromResultSet(Trea_DepartmentPopedomInfo.class);
			this.finalizeDAO();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally{
			this.finalizeDAO();
		}

		return collection;
	}
	public Collection findAllDepartment(long officeId,long statusId) throws Exception{
		StringBuffer strSQL = new StringBuffer();

		strSQL.append("SELECT * FROM Department \n");
		strSQL.append("WHERE \n");		
		strSQL.append("	officeID = "+officeId+" \n");
		strSQL.append("	AND statusID = "+statusId+" \n");

		log4j.info("SQL="+strSQL.toString());

		this.initDAO();
		Collection collection;
		try {
			this.prepareStatement(strSQL.toString());
			this.executeQuery();
			collection = this.getDataEntitiesFromResultSet(Trea_DepartmentPopedomInfo.class);
			this.finalizeDAO();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally{
			this.finalizeDAO();
		}

		return collection;
	}
}