/*
 * Created on 2004-07-19
 *
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.treasuryplan.setting.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.treasuryplan.util.TPConstant;
import com.iss.itreasury.treasuryplan.util.TreasuryPlanDAO;
import com.iss.itreasury.treasuryplan.setting.dataentity.Trea_OtherIndustryInfo;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2004
 * Company:             iSoftStone
 * @author              hewen hu
 * @version
 *  Date of Creation    2004-07-19
 */
public class Trea_OtherIndustryItemDAO extends TreasuryPlanDAO {
	protected Log4j log4j = new Log4j(Constant.ModuleType.PLAN, this);
	private String parentLineIDs = "";
	private String chindrenLineIDs="";
	public Trea_OtherIndustryItemDAO() {
		super("Trea_TPTemplate");
		super.setUseMaxID();
	}

	public Trea_OtherIndustryItemDAO(Connection conn) {
		super("Trea_TPTemplate", conn);
		super.setUseMaxID();
	}

	/**
	 * 查找子项目
	 * @param  Trea_OtherIndustryInfo info
	 * @return PageLoader
	 * @throws nothing
	 */
	public PageLoader findTPTemplateItemByLineID(Trea_OtherIndustryInfo info) {
		//SELECT
		StringBuffer m_sbSelect = new StringBuffer();
		m_sbSelect.append("DISTINCT lineName");
		//FROM
		StringBuffer m_sbFrom = new StringBuffer();
		m_sbFrom.append("Trea_TPTemplate");
		//WHERE
		StringBuffer m_sbWhere = new StringBuffer();
		m_sbWhere.append("1 = 1 \n");
//		if (info.getAuthorizedDepartment() != null && !info.getAuthorizedDepartment().equals("")) {
//			m_sbWhere.append("	AND authorizedDepartment LIKE '%<"+info.getAuthorizedDepartment()+">%' \n");
//		} else {
//			m_sbWhere.append("	AND authorizedDepartment IS NULL \n");
//		}
//		if (info.getAuthorizedUser() != null && !info.getAuthorizedUser().equals("")) {
//			m_sbWhere.append("	AND authorizedUser LIKE '%<"+info.getAuthorizedUser()+">%' \n");
//		} else {
//			m_sbWhere.append("	AND authorizedUser IS NULL \n");
//		}
		m_sbWhere.append("	AND currencyID = "+info.getCurrencyID()+" \n");
		m_sbWhere.append("	AND officeID = "+info.getOfficeID()+" \n");
		m_sbWhere.append("	AND parentLineID IN ( \n");
		m_sbWhere.append("		SELECT ID FROM Trea_TPTemplate \n");
		m_sbWhere.append("		WHERE 1 = 1 \n");
		m_sbWhere.append("			AND lineName = '"+info.getLineName()+"' \n");
		m_sbWhere.append("			AND currencyID = "+info.getCurrencyID()+" \n");
		m_sbWhere.append("			AND officeID = "+info.getOfficeID()+" \n");
		m_sbWhere.append("			AND parentLineID IN ( \n");
		m_sbWhere.append("				SELECT ID FROM Trea_TPTemplate \n");
		m_sbWhere.append("				WHERE 1 = 1 \n");
		m_sbWhere.append("					AND currencyID = "+info.getCurrencyID()+" \n");
		m_sbWhere.append("					AND officeID = "+info.getOfficeID()+" \n");
		m_sbWhere.append("					AND maintenanceFlag = "+info.getMaintenanceFlag()+" \n");
		m_sbWhere.append("					AND statusID = "+info.getStatusID()+" \n");
		m_sbWhere.append("			) \n");
		m_sbWhere.append("			AND statusID = "+info.getStatusID()+" \n");
		m_sbWhere.append("	) \n");
		m_sbWhere.append("	AND statusID = "+info.getStatusID()+" \n");

		// 获取PageLoader对象
        PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

        // 初始化PageLoader对象、实现查询和分页
        pageLoader.initPageLoader(
            new AppContext(),
            m_sbFrom.toString(),
            m_sbSelect.toString(),
            m_sbWhere.toString(),
            (int) Constant.PageControl.CODE_PAGELINECOUNT,
            "com.iss.itreasury.treasuryplan.setting.dataentity.Trea_OtherIndustryInfo",
            null);
        pageLoader.setOrderBy(" "+" ");

        return pageLoader;
	}

	/**
	 * 通过行项目名称查找上级行项目ID
	 * @param  Trea_OtherIndustryInfo info
	 * @return Collection
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public Collection findParentLineByLineName(Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT ID, lineNo, lineLevel FROM Trea_TPTemplate \n");
		strSQL.append("WHERE 1 = 1 \n");
		strSQL.append("	AND lineName = '"+info.getLineName()+"' \n");
//		if (info.getAuthorizedUser() != null && !info.getAuthorizedUser().equals("")) {
//			strSQL.append("	AND authorizedUser LIKE '%<"+info.getAuthorizedUser()+">%' \n");
//		} else {
//			strSQL.append("	AND authorizedUser IS NULL \n");
//		}
		strSQL.append("	AND currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("	AND officeID = "+info.getOfficeID()+" \n");
		strSQL.append("	AND parentLineID IN ( \n");
		strSQL.append("		SELECT ID FROM Trea_TPTemplate \n");
		strSQL.append("		WHERE 1 = 1 \n");
		strSQL.append("			AND currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("			AND officeID = "+info.getOfficeID()+" \n");
		strSQL.append("			AND maintenanceFlag = "+info.getMaintenanceFlag()+" \n");
		strSQL.append("			AND statusID = "+info.getStatusID()+" \n");
		strSQL.append("	) \n");
		strSQL.append("	AND statusID = "+info.getStatusID()+" \n");

		log4j.info("SQL="+strSQL.toString());

		Collection collection;
		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			this.executeQuery();
			collection = this.getDataEntitiesFromResultSet(Trea_OtherIndustryInfo.class);
			this.finalizeDAO();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally{
			this.finalizeDAO();
		}
		return collection;
	}

	/**
	 * 通过条件查找父级记录
	 * @param  Trea_OtherIndustryInfo info
	 * @return void
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public void findParentRecordByID(Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT ID, parentLineID FROM Trea_TPTemplate \n");
		strSQL.append("WHERE 1 = 1 \n");
		strSQL.append("	AND ID = "+info.getParentLineID()+" \n");
		strSQL.append("	AND currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("	AND officeID = "+info.getOfficeID()+" \n");
		strSQL.append("	AND statusID = "+info.getStatusID()+" \n");

		log4j.info("SQL="+strSQL.toString());

		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			ResultSet rs = this.executeQuery();
			//Trea_OtherIndustryInfo trea_OtherIndustryInfo = new Trea_OtherIndustryInfo();
			if (rs != null && rs.next()) {
/**
				//ID
				trea_OtherIndustryInfo.setId(rs.getLong("ID"));
				//机构officeID
				trea_OtherIndustryInfo.setOfficeID(rs.getLong("officeID"));
				//币种currencyID
				trea_OtherIndustryInfo.setCurrencyID(rs.getLong("currencyID"));
				//行次lineNo
				trea_OtherIndustryInfo.setLineNo(rs.getString("lineNo"));
				//行项目名称lineName
				trea_OtherIndustryInfo.setLineName(rs.getString("lineName"));
				//行项目级别lineLevel
				trea_OtherIndustryInfo.setLineLevel(rs.getLong("lineLevel"));
				//上级行项目ID parentLineID
				trea_OtherIndustryInfo.setParentLineID(rs.getLong("parentLineID"));
				//是否叶子isLeaf
				trea_OtherIndustryInfo.setIsLeaf(rs.getLong("isLeaf"));
				//所属部门authorizedDepartment
				trea_OtherIndustryInfo.setAuthorizedDepartment(rs.getString("authorizedDepartment"));
				//所属用户authorizedUser
				trea_OtherIndustryInfo.setAuthorizedUser(rs.getString("authorizedUser"));
				//MaintenanceFlag
				trea_OtherIndustryInfo.setMaintenanceFlag(rs.getLong("MaintenanceFlag"));
				//状态statusID
				trea_OtherIndustryInfo.setStatusID(rs.getLong("statusID"));
				//录入人inputUserID
				trea_OtherIndustryInfo.setInputUserID(rs.getLong("inputUserID"));
				//录入时间inputDate
				trea_OtherIndustryInfo.setInputDate(rs.getTimestamp("inputDate"));
				//修改人updateUserID
				trea_OtherIndustryInfo.setUpdateUserID(rs.getLong("updateUserID"));
				//修改时间updateDate
				trea_OtherIndustryInfo.setUpdateDate(rs.getTimestamp("updateDate"));
				//IsReadOnly
				trea_OtherIndustryInfo.setIsReadOnly(rs.getLong("IsReadOnly"));
*/
				this.parentLineIDs = this.parentLineIDs + "," + String.valueOf(rs.getLong("ID"));
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
			this.finalizeDAO() ;
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
	 * 通过条件查找记录(包括父级)
	 * @param  Trea_OtherIndustryInfo info
	 * @return Collection
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public Collection findRecordByDepartmentID(Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT * FROM Trea_TPTemplate \n");
		strSQL.append("WHERE 1 = 1 \n");
		if (info.getAuthorizedDepartment() != null && !info.getAuthorizedDepartment().equals("")) {
			strSQL.append("	AND ((authorizedDepartment LIKE '%<"+info.getAuthorizedDepartment()+">%' \n");
		} else {
			strSQL.append("	AND ((authorizedDepartment IS NULL \n");
		}
		strSQL.append("		AND isLeaf = 1) \n");
		strSQL.append("		OR isLeaf = 0) \n");
		strSQL.append("	AND currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("	AND officeID = "+info.getOfficeID()+" \n");
		strSQL.append("	AND statusID = "+info.getStatusID()+" \n");
		strSQL.append("ORDER BY lineNo \n");

		log4j.info("SQL="+strSQL.toString());

		Collection collection=null;
		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			ResultSet rs = this.executeQuery();
			collection = this.getDataEntitiesFromResultSet(Trea_OtherIndustryInfo.class);
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		}finally{
			this.finalizeDAO();			
		}
		return collection;
	}

	/**
	 * 通过条件查找记录(包括父级)
	 * @param  Trea_OtherIndustryInfo info
	 * @return Collection
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public Collection findAllRecordForDepartment(Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT * FROM Trea_TPTemplate \n");
		strSQL.append("WHERE 1 = 1 \n");
		strSQL.append("	AND currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("	AND officeID = "+info.getOfficeID()+" \n");
		strSQL.append("	AND statusID = "+info.getStatusID()+" \n");
		strSQL.append("ORDER BY lineNo \n");

		log4j.info("SQL="+strSQL.toString());

		Collection collection=null;
		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			ResultSet rs = this.executeQuery();
			collection = this.getDataEntitiesFromResultSet(Trea_OtherIndustryInfo.class);
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally{
			this.finalizeDAO();
		}
		return collection;
	}

	/**
	 * 通过条件查找记录(不包括父级)
	 * @param  Trea_OtherIndustryInfo info
	 * @return Collection
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public Collection findByDepartmentID(Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT * FROM Trea_TPTemplate \n");
		strSQL.append("WHERE 1 = 1 \n");
		if (info.getAuthorizedDepartment() != null && !info.getAuthorizedDepartment().equals("")) {
			strSQL.append("	AND authorizedDepartment LIKE '%<"+info.getAuthorizedDepartment()+">%' \n");
		} else {
			strSQL.append("	AND authorizedDepartment IS NULL \n");
		}
		strSQL.append("	AND currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("	AND officeID = "+info.getOfficeID()+" \n");
		strSQL.append("	AND statusID = "+info.getStatusID()+" \n");
		strSQL.append("ORDER BY lineNo \n");

		log4j.info("SQL="+strSQL.toString());

		Collection collection;
		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			this.executeQuery();
			collection = this.getDataEntitiesFromResultSet(Trea_OtherIndustryInfo.class);
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally{
			this.finalizeDAO();
		}
		return collection;
	}

	/**
	 * 通过条件查找记录(包括父级)
	 * @param  Trea_OtherIndustryInfo info
	 * @return Collection
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public Collection findAllRecordForUser(Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT * FROM Trea_TPTemplate \n");
		strSQL.append("WHERE 1 = 1 \n");
		if (info.getAuthorizedDepartment() != null && !info.getAuthorizedDepartment().equals("")) {
			strSQL.append("	AND authorizedDepartment LIKE '%<"+info.getAuthorizedDepartment()+">%' \n");
		} else {
			strSQL.append("	AND authorizedDepartment IS NULL \n");
		}
		strSQL.append("	AND currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("	AND officeID = "+info.getOfficeID()+" \n");
		strSQL.append("	AND statusID = "+info.getStatusID()+" \n");
		strSQL.append("ORDER BY lineNo \n");

		log4j.info("SQL="+strSQL.toString());

		Collection collection;
		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			ResultSet rs = this.executeQuery();
			collection = this.getDataEntitiesFromResultSet(Trea_OtherIndustryInfo.class);
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally{
			this.finalizeDAO();
		}
		return collection;
	}

	/**
	 * 通过条件查找记录(包括父级)
	 * @param  Trea_OtherIndustryInfo info
	 * @return Collection
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public Collection findRecordByUserID(Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT * FROM Trea_TPTemplate \n");
		strSQL.append("WHERE 1 = 1 \n");
		if (info.getAuthorizedDepartment() != null && !info.getAuthorizedDepartment().equals("")) {
			strSQL.append("	AND ((authorizedDepartment LIKE '%<"+info.getAuthorizedDepartment()+">%' \n");
		} else {
			strSQL.append("	AND ((authorizedDepartment IS NULL \n");
		}
		if (info.getAuthorizedUser() != null && !info.getAuthorizedUser().equals("")) {
			strSQL.append("		AND authorizedUser LIKE '%<"+info.getAuthorizedUser()+">%' \n");
		} else {
			strSQL.append("		AND authorizedUser IS NULL \n");
		}
		strSQL.append("		AND isLeaf = 1) \n");
		strSQL.append("		OR isLeaf = 0) \n");
		strSQL.append("	AND currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("	AND officeID = "+info.getOfficeID()+" \n");
		strSQL.append("	AND statusID = "+info.getStatusID()+" \n");
		strSQL.append("ORDER BY lineNo \n");

		log4j.info("SQL="+strSQL.toString());

		Collection collection;
		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			this.executeQuery();
			collection = this.getDataEntitiesFromResultSet(Trea_OtherIndustryInfo.class);
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally{
			this.finalizeDAO();
		}
		return collection;
	}

	/**
	 * 通过条件查找记录(不包括父级)
	 * @param  Trea_OtherIndustryInfo info
	 * @return Collection
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public Collection findByUserID(Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT * FROM Trea_TPTemplate \n");
		strSQL.append("WHERE 1 = 1 \n");
		if (info.getAuthorizedDepartment() != null && !info.getAuthorizedDepartment().equals("")) {
			strSQL.append("	AND authorizedDepartment LIKE '%<"+info.getAuthorizedDepartment()+">%' \n");
		} else {
			strSQL.append("	AND authorizedDepartment IS NULL \n");
		}
		if (info.getAuthorizedUser() != null && !info.getAuthorizedUser().equals("")) {
			strSQL.append("	AND authorizedUser LIKE '%<"+info.getAuthorizedUser()+">%' \n");
		} else {
			strSQL.append("	AND authorizedUser IS NULL \n");
		}
		strSQL.append("	AND currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("	AND officeID = "+info.getOfficeID()+" \n");
		strSQL.append("	AND statusID = "+info.getStatusID()+" \n");
		strSQL.append("ORDER BY lineNo \n");

		log4j.info("SQL="+strSQL.toString());

		Collection collection;
		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			this.executeQuery();
			collection = this.getDataEntitiesFromResultSet(Trea_OtherIndustryInfo.class);
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally{
			this.finalizeDAO();
		}
		return collection;
	}

	/**
	 * 通过行项目名称查找行项目ID
	 * @param  String lineName, Trea_OtherIndustryInfo info
	 * @return Collection
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public Collection findIDByLineName(String lineName, Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT ID FROM Trea_TPTemplate \n");
		strSQL.append("WHERE 1 = 1 \n");
		strSQL.append("	AND lineName = '"+lineName+"' \n");
//		if (info.getAuthorizedUser() != null && !info.getAuthorizedUser().equals("")) {
//			strSQL.append("	AND authorizedUser LIKE '%<"+info.getAuthorizedUser()+">%' \n");
//		} else {
//			strSQL.append("	AND authorizedUser IS NULL \n");
//		}
		strSQL.append("	AND currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("	AND officeID = "+info.getOfficeID()+" \n");
		strSQL.append("	AND parentLineID IN ( \n");
		strSQL.append("		SELECT ID FROM Trea_TPTemplate \n");
		strSQL.append("		WHERE 1 = 1 \n");
		strSQL.append("			AND lineName = '"+info.getLineName()+"' \n");
		strSQL.append("			AND currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("			AND officeID = "+info.getOfficeID()+" \n");
		strSQL.append("			AND parentLineID IN ( \n");
		strSQL.append("				SELECT ID FROM Trea_TPTemplate \n");
		strSQL.append("				WHERE 1 = 1 \n");
		strSQL.append("					AND currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("					AND officeID = "+info.getOfficeID()+" \n");
		strSQL.append("					AND maintenanceFlag = "+info.getMaintenanceFlag()+" \n");
		strSQL.append("					AND statusID = "+info.getStatusID()+" \n");
		strSQL.append("			) \n");
		strSQL.append("			AND statusID = "+info.getStatusID()+" \n");
		strSQL.append("	) \n");
		strSQL.append("	AND statusID = "+info.getStatusID()+" \n");

		log4j.info("SQL="+strSQL.toString());

		Collection collection;
		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			this.executeQuery();
			collection = this.getDataEntitiesFromResultSet(Trea_OtherIndustryInfo.class);
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally{
			this.finalizeDAO();
		}
		return collection;
	}

	/**
	 * 通过行项目ID查找数据
	 * @param  String lineName, Trea_OtherIndustryInfo info
	 * @return boolean
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public boolean checkForecastDataByLineID(Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT COUNT(*) count \n");
		strSQL.append("FROM \n");
		strSQL.append("( \n");
		strSQL.append("	SELECT SUM(ForecastAmount) ForecastAmount, SUM(PlanAmount) PlanAmount \n");
		strSQL.append("	FROM Trea_TPForecastData \n");
		strSQL.append("	WHERE 1 = 1 \n");
		if (info.getAuthorizedUser() != null && !info.getAuthorizedUser().equals("")) {
			strSQL.append("		AND authorizedUser LIKE '%<"+info.getAuthorizedUser()+">%' \n");
		} else {
			strSQL.append("		AND authorizedUser IS NULL \n");
		}
		strSQL.append("		AND currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("		AND officeID = "+info.getOfficeID()+" \n");
		strSQL.append("		AND lineID = "+info.getId()+" \n");
		strSQL.append("		AND TransactionDate >= SYSDATE \n");
		strSQL.append("	GROUP BY lineID \n");
		strSQL.append(") a \n");
		strSQL.append("WHERE 1 = 1 \n");
		strSQL.append("	AND a.ForecastAmount = 0.0 \n");
		strSQL.append("	AND a.PlanAmount = 0.0 \n");

		log4j.info("SQL="+strSQL.toString());

		long lCount;
		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			ResultSet rs = this.executeQuery();
			lCount = 0;
			while (rs != null && rs.next()) {
				lCount = rs.getLong("count");
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

		return lCount==0?true:false;
	}

	/**
	 * 通过行项目查找资金计划版本
	 * @param  String lineName, Trea_OtherIndustryInfo info
	 * @return boolean
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public boolean checkTreasuryPlanByLineID(Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT COUNT(*) count \n");
		strSQL.append("FROM Trea_TPTemplate a, Trea_TreasuryPlan b \n");
		strSQL.append("WHERE 1 = 1 \n");
		strSQL.append("	AND a.AuthorizedDepartment LIKE b.DepartmentID \n");
		strSQL.append("	AND b.currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("	AND b.officeID = "+info.getOfficeID()+" \n");
		strSQL.append("	AND a.authorizedUser LIKE '%<"+info.getAuthorizedUser()+">%' \n");
		strSQL.append("	AND a.currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("	AND a.officeID = "+info.getOfficeID()+" \n");
		strSQL.append("	AND a.ID = "+info.getId()+" \n");

		log4j.info("SQL="+strSQL.toString());

		long lCount;
		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			ResultSet rs = this.executeQuery();
			lCount = 0;
			while (rs != null && rs.next()) {
				lCount = rs.getLong("count");
			}
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}finally{
			this.finalizeDAO();
		}

		return lCount==0?true:false;
	}

	/**
	 * 通过行项目名称判断是否有下级行项目
	 * @param  String lineName, Trea_OtherIndustryInfo info
	 * @return boolean
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public boolean checkIsLeafByLineName(Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT COUNT(*) count \n");
		strSQL.append("FROM Trea_TPTemplate \n");
		strSQL.append("WHERE 1 = 1 \n");
		strSQL.append("	AND parentLineID IN ( \n");
		strSQL.append("		SELECT ID FROM Trea_TPTemplate \n");
		strSQL.append("		WHERE 1 = 1 \n");
		strSQL.append("			AND lineName = '"+info.getLineName()+"' \n");
		strSQL.append("			AND currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("			AND officeID = "+info.getOfficeID()+" \n");
		strSQL.append("			AND statusID = "+info.getStatusID()+" \n");
		strSQL.append("	) \n");
		strSQL.append("	AND currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("	AND officeID = "+info.getOfficeID()+" \n");
		strSQL.append("	AND statusID = "+info.getStatusID()+" \n");

		log4j.info("SQL="+strSQL.toString());

		long lCount;
		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			ResultSet rs = this.executeQuery();
			lCount = 0;
			while (rs != null && rs.next()) {
				lCount = rs.getLong("count");
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

		return lCount!=0?true:false;
	}

	/**
	 * 通过行项目下级的最大编号
	 * @param  Trea_OtherIndustryInfo info
	 * @return String
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public String getMaxLineNo(Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		String maxLineNo = "";
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT SUBSTR(MAX(lineNo),LENGTH(MAX(lineNo))-1,LENGTH(MAX(lineNo)))+1 maxLineNo \n");
		strSQL.append("FROM Trea_TPTemplate \n");
		strSQL.append("WHERE 1 = 1 \n");
//		if (info.getAuthorizedUser() != null && !info.getAuthorizedUser().equals("")) {
//			strSQL.append("		AND authorizedUser LIKE '%<"+info.getAuthorizedUser()+">%' \n");
//		} else {
//			strSQL.append("		AND authorizedUser IS NULL \n");
//		}
		strSQL.append("	AND currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("	AND officeID = "+info.getOfficeID()+" \n");
		strSQL.append("	AND parentLineID = "+info.getParentLineID()+" \n");
		strSQL.append("	AND statusID = "+info.getStatusID()+" \n");

		log4j.info("SQL="+strSQL.toString());

		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			ResultSet rs = this.executeQuery();
			while (rs != null && rs.next()) {
				maxLineNo = rs.getString("maxLineNo");
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

		return maxLineNo;
	}

	/**
	 * 通过条件更新
	 * @param  String Id, Trea_OtherIndustryInfo info
	 * @return void
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public void updateDepartment(String Id, Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("UPDATE Trea_TPTemplate SET authorizedDepartment = authorizedDepartment || '<"+info.getAuthorizedDepartment()+">' \n");
		strSQL.append("WHERE 1 = 1 \n");
		strSQL.append("	AND ID IN ("+Id+") \n");
		strSQL.append("	AND NVL(authorizedDepartment, 'NULL') NOT LIKE '%<"+info.getAuthorizedDepartment()+">%' \n");
		strSQL.append("	AND currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("	AND officeID = "+info.getOfficeID()+" \n");
		strSQL.append("	AND statusID = "+info.getStatusID()+" \n");

		log4j.info("SQL="+strSQL.toString());

		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			this.executeUpdate();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally{
			this.finalizeDAO();
		}
	}

	/**
	 * 通过条件更新
	 * @param  Trea_OtherIndustryInfo info, String authorizedDepartment
	 * @return int
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public int updateDepartment(Trea_OtherIndustryInfo info, String authorizedDepartment) throws SQLException, ITreasuryDAOException {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("UPDATE Trea_TPTemplate SET authorizedDepartment = '"+authorizedDepartment+"' \n");
		strSQL.append("WHERE 1 = 1 \n");
		strSQL.append("	AND ID = "+info.getId()+" \n");
		strSQL.append("	AND authorizedUser IS NULL \n");
		strSQL.append("	AND currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("	AND officeID = "+info.getOfficeID()+" \n");
		strSQL.append("	AND statusID = "+info.getStatusID()+" \n");

		log4j.info("SQL="+strSQL.toString());

		int count=-1;
		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			count = this.executeUpdate();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
		} finally {
			this.finalizeDAO();
		}
		
		try{
			this.clearNullFatherDepartmentAuthorize(info.getAuthorizedDepartment());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 通过条件更新
	 * @param  String Id, Trea_OtherIndustryInfo info
	 * @return void
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public void updateUser(String Id, Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("UPDATE Trea_TPTemplate SET authorizedUser = authorizedUser || '<"+info.getAuthorizedUser()+">' \n");
		strSQL.append("WHERE 1 = 1 \n");
		strSQL.append("	AND ID IN ("+Id+") \n");
		strSQL.append("	AND NVL(authorizedUser, 'NULL') NOT LIKE '%<"+info.getAuthorizedUser()+">%' \n");
		strSQL.append("	AND currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("	AND officeID = "+info.getOfficeID()+" \n");
		strSQL.append("	AND statusID = "+info.getStatusID()+" \n");

		log4j.info("SQL="+strSQL.toString());

		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			this.executeUpdate();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally{
			this.finalizeDAO();
		}
	}

	/**
	 * 通过条件更新
	 * @param  Trea_OtherIndustryInfo info, String authorizedUser
	 * @return void
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public void updateUser(Trea_OtherIndustryInfo info, String authorizedUser) throws SQLException, ITreasuryDAOException {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("UPDATE Trea_TPTemplate SET authorizedUser = '"+authorizedUser+"' \n");
		strSQL.append("WHERE 1 = 1 \n");
		strSQL.append("	AND ID = "+info.getId()+" \n");
		strSQL.append("	AND currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("	AND officeID = "+info.getOfficeID()+" \n");
		strSQL.append("	AND statusID = "+info.getStatusID()+" \n");

		log4j.info("SQL="+strSQL.toString());

		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			this.executeUpdate();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally{
			this.finalizeDAO();
		}
		try{
			clearNullFatherUserAuthorize(info.getAuthorizedDepartment(),info.getAuthorizedUser());
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 通过条件更新
	 * @param  Trea_OtherIndustryInfo info, String authorizedUser
	 * @return void
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public String getChindrenIDsWithDepartment(Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("select distinct m.id ");
		strSQL.append(" from trea_tptemplate m ");
		strSQL.append(" where ");
		strSQL.append("	currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("	AND officeID = "+info.getOfficeID()+" \n");
		strSQL.append("	AND statusID = "+info.getStatusID()+" \n");
		strSQL.append("and m.lineno like (select t.lineno || '%' from trea_tptemplate t where id="+info.getId()+")");
		strSQL.append("and	m.authorizeddepartment like '%<"+info.getAuthorizedDepartment()+">%'");
		log4j.info("SQL="+strSQL.toString());
		StringBuffer IDs=new StringBuffer();
		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			ResultSet rs = this.executeQuery();
			//Trea_OtherIndustryInfo trea_OtherIndustryInfo = new Trea_OtherIndustryInfo();
			if (rs!=null){
				while (rs.next()) {
					
					IDs.append("," + String.valueOf(rs.getLong("ID")));		
						
				}
			}
			this.finalizeDAO();				
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally{
			this.finalizeDAO();
		}
		return IDs.toString();
	}
	
	
	
	/**
	 * 从字符串中去掉某子串
	 * @param  String source, String regex, String replacement
	 * @return String
	 * @exception nothing
	 */
	public String replaceFirst(String source, String regex, String replacement) {
		int index = source.indexOf(regex);
		if(index>=0)
			return source.substring(0, index)+replacement+source.substring(index+regex.length(),source.length());
		else
			return source;
	}
	
	/**
	 * 删除父节点及子节点的指定权限
	 * @param info
	 * @param authorizedDepartment
	 * @return
	 * @throws Exception
	 */
	public int removeAllAuthorizedDepartmentByFather(Trea_OtherIndustryInfo info,String authorizedDepartment) throws Exception{
		StringBuffer strSQL = new StringBuffer();
				
		strSQL.append("UPDATE Trea_TPTemplate t ");
		strSQL
				.append(" SET t.authorizedDepartment = " +
						"replace(t.authorizedDepartment,'<"+authorizedDepartment+">','')");
		strSQL.append(" WHERE ");
		strSQL.append(" t.currencyID ="+info.getCurrencyID());
		strSQL.append(" AND t.officeID ="+info.getOfficeID());
		strSQL.append(" AND t.statusID ="+info.getStatusID());
		strSQL.append(" AND t.authorizedDepartment like '%<"+authorizedDepartment+">%'  ");
		strSQL.append(" AND t.lineno like '"+info.getLineNo()+"%' ");
		strSQL.append(" and not  exists   ");
		strSQL.append("( select 1 from userinfo u ");
		strSQL
				.append("where u.ndepartmentid="+authorizedDepartment+" and t.authorizeduser " +
						"like '%<' ||u.id || '>%')");

		log4j.info("SQL=" + strSQL.toString());
		
		
		
		
		
		int count=-1;
		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			count = this.executeUpdate();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
		} finally {
			this.finalizeDAO();
		}		
		//是否是因为具有用户权限
		if (count<=0){
			strSQL = new StringBuffer();
			
			strSQL.append("select count(*) count from Trea_TPTemplate t ");
			strSQL.append(" WHERE ");
			strSQL.append(" t.currencyID = 1  ");
			strSQL.append(" AND t.officeID = 1  ");
			strSQL.append(" AND t.statusID = 1  ");
			strSQL.append(" AND t.authorizedDepartment like '%<"+authorizedDepartment+">%'  ");
			strSQL.append(" AND t.lineno like '"+info.getLineNo()+"%' ");
			strSQL.append(" and  exists   ");
			strSQL.append("( select 1 from userinfo u ");
			strSQL
					.append("where u.ndepartmentid="+authorizedDepartment+" and t.authorizeduser " +
							"like '%<' ||u.id || '>%')");

			log4j.info("SQL=" + strSQL.toString());
			try {
				this.initDAO();
				this.prepareStatement(strSQL.toString());				
				ResultSet rs = this.executeQuery();
				long lCount = 0;
				if (rs != null && rs.next()) {
					lCount = rs.getLong("count");
					if (lCount<=0){
						//没有符合条件的记录，如果lCount大于0，说明是因为存在用户权限
						count=1;
					}
				}
			} catch (ITreasuryDAOException e) {
				e.printStackTrace();
			} finally {
				this.finalizeDAO();
			}		
		}
		
		try{
			this.clearNullFatherDepartmentAuthorize(authorizedDepartment);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return count;
	}
	/**
	 * 删除父节点及子节点的指定权限
	 * @param info
	 * @param authorizedDepartment
	 * @return
	 * @throws Exception
	 */
	public int removeAllAuthorizedUserByFather(Trea_OtherIndustryInfo info,String departmentID,String authorizedUser) throws Exception{
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("UPDATE Trea_TPTemplate SET authorizeduser = replace(authorizeduser,'<"+authorizedUser+">','') \n");
		strSQL.append("WHERE 1 = 1 \n");		
		//strSQL.append("	AND authorizedUser IS NULL \n");
		strSQL.append("	AND currencyID = "+info.getCurrencyID()+" \n");
		strSQL.append("	AND officeID = "+info.getOfficeID()+" \n");
		strSQL.append("	AND statusID = "+info.getStatusID()+" \n");
		strSQL.append("	AND lineno like '"+info.getLineNo()+"%' \n");
		log4j.info("SQL="+strSQL.toString());

		int count=-1;
		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			count = this.executeUpdate();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
		} finally {
			this.finalizeDAO();
		}		
		
		try{
			this.clearNullFatherUserAuthorize(departmentID,authorizedUser);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * 检查行项目是否已经被编制计划
	 * @param info	 
	 * @return
	 * @throws Exception
	 */
	public boolean CheckItemUsed(Trea_OtherIndustryInfo info) throws Exception{
		boolean bUsed=false;		
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("select t.* from trea_treasuryplan t,trea_treasuryplandetail m \n");
				
		strSQL.append("	where t.id=m.treasuryplanid and t.officeid= "
				+ info.getOfficeID() + " \n ");
		strSQL.append("	and t.currencyid= " + info.getCurrencyID() + " \n");
		strSQL.append(" and t.statusid in ("
				+ TPConstant.PlanVersionStatus.SAVE + ","
				+ TPConstant.PlanVersionStatus.SUBMIT + ","						
				+ TPConstant.PlanVersionStatus.CHECK + ") \n ");
		strSQL.append(" and t.departmentid=" + info.getAuthorizedDepartment()
				+ " \n ");
		strSQL.append(" and m.lineid="+info.getId());
		
		//log4j.info("SQL="+strSQL.toString());

		int count=-1;
		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			ResultSet rs = this.executeQuery();
			
			//Trea_OtherIndustryInfo trea_OtherIndustryInfo = new Trea_OtherIndustryInfo();
			if (rs != null && rs.next()){					
				bUsed = true;
			}
			else{								
				bUsed=false;
			}
			return bUsed;
		} catch (ITreasuryDAOException e) {
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
	public String findChinldrenRecordByID(String childrenLineIDs, Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		//this.chindrenLineIDs = childrenLineIDs;
		this.findChildrenRecordByID(info);
		return this.chindrenLineIDs;
	}
	
	/**
	 * 通过条件查找父级记录，只查找未被分配的行项目，增加部门权限时用
	 * @param  Trea_OtherIndustryInfo info
	 * @return void
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public void findChildrenRecordByID(Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		StringBuffer strSQL = new StringBuffer();
		
		strSQL.append("select m.id id from trea_tptemplate t,trea_tptemplate m where m.lineno like t.lineno || '%' ");	
		
		strSQL.append("	AND m.currencyID = "+info.getCurrencyID());
		strSQL.append("	AND m.officeID = "+info.getOfficeID());
		strSQL.append("	AND m.statusID = "+info.getStatusID());
		strSQL.append("	AND (m.authorizeddepartment is null and m.isleaf=1 or m.isleaf=0" +
				" and exists (select 1 from trea_tptemplate n where n.lineno like m.lineno || '%' and n.officeid=1 and n.statusid=1 and n.authorizeddepartment is null and n.isleaf=1)) ");
		strSQL.append(" and t.id="+info.getId()+" and m.id<>"+info.getId());		
		log4j.info("SQL="+strSQL.toString());

		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			ResultSet rs = this.executeQuery();
			//Trea_OtherIndustryInfo trea_OtherIndustryInfo = new Trea_OtherIndustryInfo();
			if (rs!=null){
				while (rs.next()) {
					
					this.chindrenLineIDs += "," + String.valueOf(rs.getLong("ID"));		
						
				}
			}
			this.finalizeDAO();			
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			this.finalizeDAO() ;
		}
	}
	/**
	 * 检索是否父节点的所有子节点均分配了部门权限
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public boolean isAllChildrenSelected(Trea_OtherIndustryInfo info) throws Exception{
		boolean bAllSelected=true;
		StringBuffer strSQL = new StringBuffer();		
		strSQL.append("select count(*) sum from trea_tptemplate t ");	
		strSQL.append(" where t.lineno like '"+info.getLineNo() +"%' ");
		strSQL.append("	AND t.currencyID = "+info.getCurrencyID()+" ");
		strSQL.append("	AND t.officeID = "+info.getOfficeID()+" ");
		strSQL.append("	AND t.statusID = "+info.getStatusID()+" ");		
		strSQL.append(" and t.id<>"+info.getId());			
		strSQL.append(" and t.authorizeddepartment is null ");
		log4j.info("SQL="+strSQL.toString());
		
		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			ResultSet rs = this.executeQuery();			
			if (rs!=null && rs.next()){					
				if (rs.getLong("sum")>0){
					bAllSelected=false;		
				}
			}			
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			this.finalizeDAO() ;
		}
		return bAllSelected;
	}
	/**
	 * 删除表中所有子节点权限被清除，但父节点权限没清除的父节点
	 * @return
	 * @throws Exception
	 */
	private boolean clearNullFatherDepartmentAuthorize(String departmentID) throws Exception{
		boolean bSuccess=false;
		
		StringBuffer strSQL = new StringBuffer();		
		strSQL.append("update trea_tptemplate t set t.authorizeddepartment=");
		strSQL.append("replace(t.authorizeddepartment,'<"+departmentID+">','') ");	
		strSQL.append(" where not exists ");
		strSQL.append(" (select 1 from trea_tptemplate m " );
		strSQL.append(" where m.authorizeddepartment like '%<"+departmentID+">%' " );
		strSQL.append(" and m.lineno like t.lineno || '%' and m.isleaf=1 ");
		strSQL.append("	AND t.currencyID = m.currencyID");
		strSQL.append("	AND t.officeID = m.officeID");
		strSQL.append("	AND m.statusID =1 )");
		strSQL.append(" AND t.authorizeddepartment like '%<"+departmentID+">%' and t.isleaf=0 ");		
		strSQL.append("	AND t.statusID =1 ");
		
		log4j.info("SQL="+strSQL.toString());
		
		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			this.executeUpdate();			
			bSuccess=true;		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;		
		} finally {
			this.finalizeDAO() ;
		}		
		return bSuccess;
		
	}
	/**
	 * 删除表中所有子节点权限被清除，但父节点权限没清除的父节点
	 * @return
	 * @throws Exception
	 */
	private boolean clearNullFatherUserAuthorize(String departmentID,String userID) throws Exception{
		boolean bSuccess=false;
		
		StringBuffer strSQL = new StringBuffer();		
		strSQL.append("update trea_tptemplate t set t.authorizeduser=");
		strSQL.append("replace(t.authorizeduser,'<"+userID+">','') ");	
		strSQL.append(" where not exists ");
		strSQL.append(" (select 1 from trea_tptemplate m " );
		strSQL.append(" where m.authorizeddepartment like '%<"+departmentID+">%' " );
		strSQL.append(" and m.authorizeduser like '%<"+userID+">%' ");
		strSQL.append(" and m.lineno like t.lineno || '%' and m.isleaf=1 ");
		strSQL.append("	AND t.currencyID = m.currencyID");
		strSQL.append("	AND t.officeID = m.officeID");
		strSQL.append("	AND m.statusID =1 )");		
		strSQL.append(" and t.authorizeddepartment like '%<"+departmentID+">%' and t.isleaf=0 ");		
		strSQL.append(" and t.authorizeduser like '%<"+userID+">%' ");
		strSQL.append("	AND t.statusID =1 ");
		log4j.info("SQL="+strSQL.toString());
		
		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			this.executeUpdate();			
			bSuccess=true;		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;		
		} finally {
			this.finalizeDAO() ;
		}		
		return bSuccess;
		
	}
}
