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
import com.iss.itreasury.treasuryplan.setting.dao.Trea_OtherIndustryDAO;
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
public class Trea_OtherIndustryOperation {
	protected Log4j log = new Log4j(Constant.ModuleType.PLAN, this);
	protected Trea_OtherIndustryDAO dao = null;

	/**
	 * 构造函数
	 * @param  nothing
	 * @return nothing
	 * @exception nothing
	 */
	public Trea_OtherIndustryOperation() {
		dao = new Trea_OtherIndustryDAO();
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
	 * 根据条件查找
	 * @param  Trea_OtherIndustryInfo info
	 * @return Collection
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public Trea_OtherIndustryInfo findByID(long Id) throws SQLException, ITreasuryDAOException {
		return (Trea_OtherIndustryInfo) dao.findByID(Id, Trea_OtherIndustryInfo.class);
	}

	/**
	 * 查找子项目
	 * @param  Trea_OtherIndustryInfo info
	 * @return PageLoader
	 * @throws nothing
	 */
	public PageLoader findTPTemplateByCondition(Trea_OtherIndustryInfo info) {
        return dao.findTPTemplateByCondition(info);
	}
}