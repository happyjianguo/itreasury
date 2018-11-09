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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.treasuryplan.setting.dataentity.Trea_UserInfoInfo;
import com.iss.itreasury.treasuryplan.util.TreasuryPlanDAO;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author              hewen hu
 * @version
 *  Date of Creation    2004-07-19
 */
public class Trea_UserInfoDAO extends TreasuryPlanDAO {
	protected Log4j log4j = new Log4j(Constant.ModuleType.PLAN, this);

	public Trea_UserInfoDAO(){
		super("userinfo");
	}

	public Trea_UserInfoDAO(Connection conn){
		super("userinfo", conn);
	}

	/**
	 * 通过用户名查找所属部门下的全部用户
	 * @param  Trea_DepartmentPopedomInfo info
	 * @return Collection
	 * @exception throws ITreasuryDAOException
	 */
	public Collection getUsersByLoginUserID(Trea_UserInfoInfo info) throws ITreasuryDAOException {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT ID, sName Name, sLoginNo LoginNo \n");
		strSQL.append("FROM userInfo \n");
		strSQL.append("WHERE 1 = 1 \n");
		strSQL.append("	AND nDepartMentID = ( \n");
		strSQL.append("		SELECT nDepartMentID \n");
		strSQL.append("		FROM userInfo \n");
		strSQL.append("		WHERE 1 = 1 \n");
		strSQL.append("			AND ID = "+info.getId()+" \n");
		strSQL.append("			AND nOfficeID = "+info.getOfficeID()+" \n");
		strSQL.append("			AND '<' || replace(sCurrencyID,',','><') || '>' LIKE '%<"+info.getCurrencyID()+">%' \n");
		strSQL.append("			AND nStatusID = "+info.getStatusID()+" \n");
		strSQL.append("	) \n");
		strSQL.append("	AND nOfficeID = "+info.getOfficeID()+" \n");
		strSQL.append("	AND '<' || replace(sCurrencyID,',','><') || '>' LIKE '%<"+info.getCurrencyID()+">%' \n");
		strSQL.append("	AND nStatusID = "+info.getStatusID()+" \n");

		log4j.info("SQL="+strSQL.toString());

		Collection collection;
		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			this.executeQuery();
			collection = this.getDataEntitiesFromResultSet(Trea_UserInfoInfo.class);
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			this.finalizeDAO();
		}
		return collection;
	}

	/**
	 * 通过用户名查找所属部门下的全部用户
	 * @param  long lId
	 * @return long
	 * @exception throws ITreasuryDAOException, SQLException
	 */
	public long getDepartmentIDByID(long lId) throws ITreasuryDAOException, SQLException {
		long departmentID = -1;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT nDepartmentID \n");
		strSQL.append("FROM userInfo \n");
		strSQL.append("WHERE 1 = 1 \n");
		strSQL.append("	AND ID = "+lId+" \n");

		log4j.info("SQL="+strSQL.toString());

		try {
			this.initDAO();
			this.prepareStatement(strSQL.toString());
			ResultSet rs = this.executeQuery();
			while (rs != null && rs.next()) {
				departmentID = rs.getLong("nDepartmentID");
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
		return departmentID;
	}
}