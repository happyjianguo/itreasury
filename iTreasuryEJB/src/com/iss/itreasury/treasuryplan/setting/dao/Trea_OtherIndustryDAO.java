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
import com.iss.itreasury.treasuryplan.util.TreasuryPlanDAO;
import com.iss.itreasury.treasuryplan.setting.dataentity.Trea_OtherIndustryInfo;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2004
 * Company:             iSoftStone
 * @author              kewen hu
 * @version
 *  Date of Creation    2004-07-19
 */
public class Trea_OtherIndustryDAO extends TreasuryPlanDAO {
	protected Log4j log4j = new Log4j(Constant.ModuleType.PLAN, this);

	public Trea_OtherIndustryDAO() {
		super("Trea_TPTemplate");
	}

	public Trea_OtherIndustryDAO(Connection conn) {
		super("Trea_TPTemplate", conn);
	}

	/**
	 * 查找子项目
	 * @param  Trea_OtherIndustryInfo
	 * @return PageLoader
	 * @throws nothing
	 */
	public PageLoader findTPTemplateByCondition(Trea_OtherIndustryInfo info) {
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
		m_sbWhere.append("			AND currencyID = "+info.getCurrencyID()+" \n");
		m_sbWhere.append("			AND officeID = "+info.getOfficeID()+" \n");
		m_sbWhere.append("			AND maintenanceFlag = "+info.getMaintenanceFlag()+" \n");
		m_sbWhere.append("			AND statusID = "+info.getStatusID()+" \n");
		m_sbWhere.append("	) \n");
		m_sbWhere.append("	AND statusID = "+info.getStatusID()+" \n");
        m_sbWhere.append("  AND maintenanceFlag = "+info.getMaintenanceFlag()+" \n");

		// 获取PageLoader对象
        PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

        // 初始化PageLoader对象、实现查询和分页
        pageLoader.initPageLoader(
            new AppContext(),
            m_sbFrom.toString(),
            m_sbSelect.toString(),
            m_sbWhere.toString(),
            //(int) Constant.PageControl.CODE_PAGELINECOUNT,
            10000,
            "com.iss.itreasury.treasuryplan.setting.dataentity.Trea_OtherIndustryInfo",
            null);
        pageLoader.setOrderBy(" "+" ");

        return pageLoader;
	}
}