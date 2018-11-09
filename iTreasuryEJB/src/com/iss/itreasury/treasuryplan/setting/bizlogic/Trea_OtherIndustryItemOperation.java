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
import com.iss.itreasury.treasuryplan.setting.dao.Trea_OtherIndustryItemDAO;
import com.iss.itreasury.treasuryplan.setting.dataentity.Trea_OtherIndustryInfo;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author              kewen hu
 * @version
 *  Date of Creation    2004-07-19
 */
public class Trea_OtherIndustryItemOperation {
	protected Log4j log = new Log4j(Constant.ModuleType.PLAN, this);
	protected Trea_OtherIndustryItemDAO dao = null;

	/**
	 * 构造函数
	 * @param  nothing
	 * @return nothing
	 * @exception nothing
	 */
	public Trea_OtherIndustryItemOperation() {
		dao = new Trea_OtherIndustryItemDAO();
	}

	/**
	 * 新增系统参数
	 * @param  Trea_OtherIndustryItemInfo info
	 * @return long
	 * @exception throws SQLException
	 */
	public long add(Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		return dao.add(info);
	}

	/**
	 * 修改系统参数
	 * @param  Trea_OtherIndustryInfo info
	 * @return void
	 * @exception throws SQLException
	 */
	public void update(Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		dao.update(info);
	}

	/**
	 * 保存系统参数
	 * @param  Trea_OtherIndustryInfo info
	 * @return void
	 * @exception throws SQLException
	 */
	public long save(Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		dao.setUseMaxID();
		return dao.add(info);
	}

	/**
	 * 删除最新系统参数
	 * @param  Trea_OtherIndustryInfo info
	 * @return void
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public void delete(Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		info.setStatusID(0);
		dao.update(info);
	}

	/**
	 * 根据条件查找
	 * @param  Trea_OtherIndustryInfo info
	 * @return Collection
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public Collection findByCondition(Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		return dao.findByCondition(info);
	}

	/**
	 * 通过条件查找记录(包括父级)
	 * @param  Trea_OtherIndustryInfo info
	 * @return Collection
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public Collection findRecordByDepartmentID(Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		return dao.findRecordByDepartmentID(info);
	}

	/**
	 * 通过条件查找记录(包括父级)
	 * @param  Trea_OtherIndustryInfo info
	 * @return Collection
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public Collection findAllRecordForDepartment(Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		return dao.findAllRecordForDepartment(info);
	}

	/**
	 * 通过条件查找记录(不包括父级)
	 * @param  Trea_OtherIndustryInfo info
	 * @return Collection
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public Collection findByDepartmentID(Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		return dao.findByDepartmentID(info);
	}

	/**
	 * 通过条件查找记录(包括父级)
	 * @param  Trea_OtherIndustryInfo info
	 * @return Collection
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public Collection findRecordByUserID(Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		return dao.findRecordByUserID(info);
	}

	/**
	 * 通过条件查找记录(包括父级)
	 * @param  Trea_OtherIndustryInfo info
	 * @return Collection
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public Collection findAllRecordForUser(Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		return dao.findAllRecordForUser(info);
	}

	/**
	 * 通过条件查找记录(不包括父级)
	 * @param  Trea_OtherIndustryInfo info
	 * @return Collection
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public Collection findByUserID(Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		return dao.findByUserID(info);
	}

	/**
	 * 查找子项目
	 * @param  Trea_OtherIndustryInfo info
	 * @return PageLoader
	 * @throws nothing
	 */
	public PageLoader findTPTemplateItemByLineID(Trea_OtherIndustryInfo info) {
        return dao.findTPTemplateItemByLineID(info);
	}

	/**
	 * 通过行项目名称查找上级行项目ID
	 * @param  Trea_OtherIndustryInfo info
	 * @return Collection
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public Collection findParentLineByLineName(Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		return dao.findParentLineByLineName(info);
	}

	/**
	 * 通过行项目名称查找行项目ID
	 * @param  String lineName, Trea_OtherIndustryInfo info
	 * @return Collection
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public Collection findIDByLineName(String lineName, Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		return dao.findIDByLineName(lineName, info);
	}

	/**
	 * 通过行项目下级的最大编号
	 * @param  Trea_OtherIndustryInfo info
	 * @return String
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public String getMaxLineNo(Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		return dao.getMaxLineNo(info);
	}

	/**
	 * 通过行项目ID查找数据
	 * @param  String lineName, Trea_OtherIndustryInfo info
	 * @return boolean
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public boolean checkForecastDataByLineID(Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		return dao.checkForecastDataByLineID(info);
	}

	/**
	 * 通过行项目查找资金计划版本
	 * @param  String lineName, Trea_OtherIndustryInfo info
	 * @return boolean
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public boolean checkTreasuryPlanByLineID(Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		return dao.checkTreasuryPlanByLineID(info);
	}

	/**
	 * 通过行项目名称判断是否有下级行项目
	 * @param  String lineName, Trea_OtherIndustryInfo info
	 * @return boolean
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public boolean checkIsLeafByLineName(Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		return dao.checkIsLeafByLineName(info);
	}

	/**
	 * 通过行项目下级的最大编号
	 * @param  String Id, Trea_OtherIndustryInfo info
	 * @return void
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public void updateDepartment(String Id, Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		dao.updateDepartment(Id, info);
	}

	/**
	 * 通过条件更新
	 * @param  Trea_OtherIndustryInfo info, String authorizedDepartment
	 * @return int
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public int updateDepartment(Trea_OtherIndustryInfo info, String authorizedDepartment) throws SQLException, ITreasuryDAOException {
		return dao.updateDepartment(info, authorizedDepartment);
	}

	/**
	 * 通过行项目下级的最大编号
	 * @param  String Id, Trea_OtherIndustryInfo info
	 * @return void
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public void updateUser(String Id, Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		dao.updateUser(Id, info);
	}

	/**
	 * 通过条件更新
	 * @param  Trea_OtherIndustryInfo info, String authorizedUser
	 * @return void
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public void updateUser(Trea_OtherIndustryInfo info, String authorizedUser) throws SQLException, ITreasuryDAOException {
		dao.updateUser(info, authorizedUser);
	}
	
	/**
	 * 通过条件更新
	 * @param  Trea_OtherIndustryInfo info, String authorizedUser
	 * @return void
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public String getChindrenIDsWithDepartment(Trea_OtherIndustryInfo info ) throws SQLException, ITreasuryDAOException {
		return dao.getChindrenIDsWithDepartment(info);
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
	 * 通过条件查找子节点记录
	 * @param  String childrenLineIDs, Trea_OtherIndustryInfo info
	 * @return String
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public String findChildrenRecordByID(String childrenLineIDs, Trea_OtherIndustryInfo info) throws SQLException, ITreasuryDAOException {
		return dao.findChinldrenRecordByID(childrenLineIDs, info);
	}
	
	/**
	 * 从字符串中去掉某子串
	 * @param  String source, String regex, String replacement
	 * @return String
	 * @exception nothing
	 */
	public String replaceFirst(String source, String regex, String replacement) {
		return dao.replaceFirst(source, regex, replacement);
	}
	/**
	 * 删除父节点及子节点的指定权限
	 * @param info
	 * @param authorizedDepartment
	 * @return
	 * @throws Exception
	 */
	public int removeAllAuthorizedDepartmentByFather(Trea_OtherIndustryInfo info,String authorizedDepartment) throws Exception{
		return dao.removeAllAuthorizedDepartmentByFather(info,authorizedDepartment);
	}
	/**
	 * 删除父节点及子节点的指定权限
	 * @param info
	 * @param departmentID
	 * @param authorizedUser
	 * @return
	 * @throws Exception
	 */
	public int removeAllAuthorizedUserByFather(Trea_OtherIndustryInfo info,String departmentID,String authorizedUser) throws Exception{
		return dao.removeAllAuthorizedUserByFather(info,departmentID,authorizedUser);
	}
	/**
	 * 检查行项目是否已经被编制计划
	 * @param info	 
	 * @return
	 * @throws Exception
	 */
	public boolean CheckItemUsed(Trea_OtherIndustryInfo info) throws Exception{
		return dao.CheckItemUsed(info);
	}
	/**
	 * 检索是否父节点的所有子节点均分配了部门权限
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public boolean isAllChildrenSelected(Trea_OtherIndustryInfo info) throws Exception{
		return dao.isAllChildrenSelected(info);
	}
}