/*
 * Created on 2004-07-19
 *
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.treasuryplan.setting.bizlogic;

import java.util.Collection;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.treasuryplan.setting.dao.Trea_TPForecastDataDAO;
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
public class Trea_TPForecastDataOperation {
	protected Log4j log = new Log4j(Constant.ModuleType.PLAN, this);
	private Trea_TPForecastDataDAO dao = new Trea_TPForecastDataDAO();

	/**
	 * 新增系统参数
	 * @param  Trea_TPForecastDataInfo info
	 * @return long
	 * @exception throws SQLException
	 */
	public long add(Trea_TPForecastDataInfo info) throws ITreasuryDAOException {
		return dao.add(info);
	}

	/**
	 * 修改系统参数
	 * @param  Trea_TPForecastDataInfo info
	 * @return void
	 * @exception throws SQLException
	 */
	public void update(Trea_TPForecastDataInfo info) throws ITreasuryDAOException {
		dao.update(info);
	}

	/**
	 * 保存系统参数
	 * @param  Trea_TPForecastDataInfo info
	 * @return void
	 * @exception throws SQLException
	 */
	public long save(Trea_TPForecastDataInfo info) throws ITreasuryDAOException {
		dao.setUseMaxID();
		return dao.add(info);
	}

	/**
	 * 取最大的日期
	 * @param  nothing
	 * @return Timestamp
	 * @exception throws ITreasuryDAOException, SQLException
	 */
	public Timestamp getMaxTransactionDate() throws ITreasuryDAOException, SQLException {
		return dao.getMaxTransactionDate();
	}

	/**
	 * 取最大的日期
	 * @param  String lineNo
	 * @return long
	 * @exception throws ITreasuryDAOException, SQLException
	 */
	public long getParentLineIDByLineNo(String lineNo) throws ITreasuryDAOException, SQLException {
		return dao.getParentLineIDByLineNo(lineNo);
	}

	/**
	 * 通过条件更新
	 * @param  Trea_TPForecastDataInfo info
	 * @return void
	 * @exception throws ITreasuryDAOException, SQLException
	 */
	public void updateTPForecastData(Trea_TPForecastDataInfo info) throws ITreasuryDAOException, SQLException {
		dao.updateTPForecastData(info);
	}

	/**
	 * 通过条件更新
	 * @param  Trea_TPForecastDataInfo info
	 * @return void
	 * @exception throws ITreasuryDAOException, SQLException
	 */
	public void deleteTPForecastData(Trea_TPForecastDataInfo info) throws ITreasuryDAOException, SQLException {
		dao.deleteTPForecastData(info);
	}
}