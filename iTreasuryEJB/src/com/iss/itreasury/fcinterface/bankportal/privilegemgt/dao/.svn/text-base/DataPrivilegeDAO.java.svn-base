package com.iss.itreasury.fcinterface.bankportal.privilegemgt.dao;

import com.iss.itreasury.fcinterface.bankportal.privilegemgt.dataentity.DataPrivilegeInfo;
import com.iss.itreasury.fcinterface.bankportal.sysframe.dao.BaseDAO;
import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.system.SystemException;

public interface DataPrivilegeDAO extends BaseDAO
{	
	public static final String tableName = "bs_privilegesetting";
	public static boolean isNeedPrefix = true;
	public void addPrivilege(DataPrivilegeInfo paramInfo) throws SystemException;
	public long autoAuthorize(long userID, long officeID) throws SystemException;
	public long getGroupIDByUserID(long userID) throws SystemException;
}
