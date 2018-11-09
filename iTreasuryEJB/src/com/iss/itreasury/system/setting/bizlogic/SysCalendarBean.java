package com.iss.itreasury.system.setting.bizlogic;

import java.sql.SQLException;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.system.setting.dao.SysCalendarDAO;
import com.iss.itreasury.system.setting.dataentity.SysCalendarInfo;

public class SysCalendarBean {

	/** 功能：查找所有有效的设置记录 */
	public SysCalendarInfo findById(String strIdDate) {
		return null;
	};
	
	
	

	/** 功能：查找所有有效的设置记录 
	 * @throws SQLException 
	 * @throws ITreasuryDAOException */
	public Collection getAll() throws ITreasuryDAOException, SQLException {
		SysCalendarDAO sysCalendarDAO = new SysCalendarDAO();
		return sysCalendarDAO.getAll();
		
	}

	public String updateOrAdd(SysCalendarInfo info)throws SQLException, ITreasuryDAOException{
		SysCalendarDAO sysCalendarDAO = new SysCalendarDAO();
		return sysCalendarDAO.updateOrAdd(info);
	}
	
	public Collection findCurrentMonth(String strIdDate)throws SQLException, ITreasuryDAOException{
		SysCalendarDAO sysCalendarDAO = new SysCalendarDAO();
		return sysCalendarDAO.findCurrentMonth(strIdDate);
	}
	/** 功能：设置日期为工作日或非工作日 */
	public String setHDorWD(SysCalendarInfo info) {
		return null;
	}

	/** 功能：回复当前日期为默认值 */
	public String setDefault(String strIDdt, long lTypeId) {
		return null;
	}
}
