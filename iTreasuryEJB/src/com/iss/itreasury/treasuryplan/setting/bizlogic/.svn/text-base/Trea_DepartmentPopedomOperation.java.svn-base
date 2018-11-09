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
import com.iss.itreasury.util.ITreasuryBaseDataEntity;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.treasuryplan.setting.dao.Trea_DepartmentPopedomDAO;
import com.iss.itreasury.treasuryplan.setting.dataentity.Trea_DepartmentPopedomInfo;
import com.iss.itreasury.treasuryplan.setting.dataentity.Trea_TPForecastDataInfo;
import com.iss.itreasury.treasuryplan.setting.dataentity.Trea_OtherIndustryInfo;;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author              hewen hu
 * @version
 *  Date of Creation    2004-07-19
 */
public class Trea_DepartmentPopedomOperation {
	protected Log4j log = new Log4j(Constant.ModuleType.PLAN, this);
	private Trea_DepartmentPopedomDAO dao = new Trea_DepartmentPopedomDAO();

	/**
	 * 新增系统参数
	 * @param  Trea_OtherIndustryItemInfo info
	 * @return long
	 * @exception throws SQLException
	 */
	public long add(Trea_DepartmentPopedomInfo info) throws ITreasuryDAOException {
		return dao.add(info);
	}

	/**
	 * 修改系统参数
	 * @param  Trea_OtherIndustryInfo info
	 * @return void
	 * @exception throws SQLException
	 */
	public void update(Trea_DepartmentPopedomInfo info) throws ITreasuryDAOException {
		dao.update(info);
	}

	/**
	 * 保存系统参数
	 * @param  Trea_OtherIndustryInfo info
	 * @return void
	 * @exception throws SQLException
	 */
	public long save(Trea_DepartmentPopedomInfo info) throws ITreasuryDAOException {
		dao.setUseMaxID();
		return dao.add(info);
	}

	/**
	 * 删除最新系统参数
	 * @param  Trea_OtherIndustryInfo info
	 * @return void
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public void delete(Trea_DepartmentPopedomInfo info) throws ITreasuryDAOException {
		info.setStatusID(0);
		dao.update(info);
	}

	/**
	 * 通过条件查找
	 * @param  Trea_DepartmentPopedomInfo info
	 * @return Collection
	 * @exception throws ITreasuryDAOException
	 */
	public Collection findByCondition(Trea_DepartmentPopedomInfo info) throws SQLException, ITreasuryDAOException {
		return dao.findByCondition(info);
	}

	/**
	 * 通过条件查找
	 * @param  long lId
	 * @return Collection
	 * @exception throws ITreasuryDAOException
	 */
	public Trea_DepartmentPopedomInfo findByID(long lId) throws ITreasuryDAOException {
		return (Trea_DepartmentPopedomInfo) dao.findByID(lId, Trea_DepartmentPopedomInfo.class);
	}

	/**
	 * 通过行项目ID查找ID
	 * @param  Trea_TPForecastDataInfo info
	 * @return long
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public long findIdByLineID(Trea_TPForecastDataInfo info) throws SQLException, ITreasuryDAOException {
		return dao.findIdByLineID(info);
	}

	/**
	 * 通过条件查找父级记录
	 * @param  String parentLineIDs, Trea_OtherIndustryInfo info
	 * @return String
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public String findParentRecordByID(String parentLineIDs, Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		return dao.findParentRecordByID(parentLineIDs, info);
	}

	/**
	 * update表Trea_TPForecastData
	 * @param  String parentLineIDs, Trea_OtherIndustryInfo info
	 * @return nothing
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public void updateDepartmentID(String parentLineIDs, Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		dao.updateDepartmentID(parentLineIDs, info);
	}

	/**
	 * update表Trea_TPForecastData
	 * @param  Trea_OtherIndustryInfo info, String authorizedDepartment
	 * @return int
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public int updateDepartmentID(Trea_OtherIndustryInfo info, String authorizedDepartment) throws SQLException, ITreasuryDAOException {
		return dao.updateDepartmentID(info, authorizedDepartment);
	}

	/**
	 * update表Trea_TPForecastData
	 * @param  String parentLineIDs, Trea_OtherIndustryInfo info
	 * @return nothing
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public void updateUserID(String parentLineIDs, Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		dao.updateUserID(parentLineIDs, info);
	}

	/**
	 * update表Trea_TPForecastData
	 * @param  Trea_OtherIndustryInfo info, String authorizedUser
	 * @return nothing
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public void updateUserID(Trea_OtherIndustryInfo info, String authorizedUser) throws SQLException, ITreasuryDAOException {
		dao.updateUserID(info, authorizedUser);
	}
}