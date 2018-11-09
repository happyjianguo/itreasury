/*
 * Created on 2004-07-19
 *
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.treasuryplan.setting.bizlogic;

import java.util.Collection;
import java.sql.SQLException;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.treasuryplan.setting.dao.Trea_UserInfoDAO;
import com.iss.itreasury.treasuryplan.setting.dataentity.Trea_UserInfoInfo;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author              kewen hu
 * @version
 *  Date of Creation    2004-07-19
 */
public class Trea_UserInfoOperation {
	protected Log4j log = new Log4j(Constant.ModuleType.PLAN, this);
	protected Trea_UserInfoDAO dao = null;

	/**
	 * 构造函数
	 * @param  nothing
	 * @return nothing
	 * @exception nothing
	 */
	public Trea_UserInfoOperation() {
		dao = new Trea_UserInfoDAO();
	}

	/**
	 * 根据条件查找
	 * @param  Trea_UserInfoInfo info
	 * @return Collection
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public Collection findByCondition(Trea_UserInfoInfo info) throws SQLException, ITreasuryDAOException {
		return dao.findByCondition(info);
	}

	/**
	 * 通过用户名查找所属部门下的全部用户
	 * @param  Trea_UserInfoInfo info
	 * @return Collection
	 * @exception throws ITreasuryDAOException
	 */
	public Collection getUsersByLoginUserID(Trea_UserInfoInfo info) throws ITreasuryDAOException {
		return dao.getUsersByLoginUserID(info);
	}

	/**
	 * 通过用户名查找所属部门下的全部用户
	 * @param  long lId
	 * @return long
	 * @exception throws ITreasuryDAOException, SQLException
	 */
	public long getDepartmentIDByID(long lId) throws ITreasuryDAOException, SQLException {
		return dao.getDepartmentIDByID(lId);
	}
}