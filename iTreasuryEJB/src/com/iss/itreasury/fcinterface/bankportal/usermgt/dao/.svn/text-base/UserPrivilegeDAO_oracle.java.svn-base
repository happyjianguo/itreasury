package com.iss.itreasury.fcinterface.bankportal.usermgt.dao;

import java.sql.Connection;

import com.iss.itreasury.fcinterface.bankportal.sysframe.dao.BaseDAO_oracle;
import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.system.SystemException;

public class UserPrivilegeDAO_oracle extends BaseDAO_oracle implements UserPrivilegeDAO
{
	/**
	 * @param tableName
	 * @param isNeedPrefix
	 * @param conn
	 * @throws SystemException
	 */
	public UserPrivilegeDAO_oracle(Connection conn) throws SystemException
	{
		super(tableName, isNeedPrefix, conn);
		this.setIDType(ID_TYPE_MAXID);
	}
}
