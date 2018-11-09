package com.iss.itreasury.fcinterface.bankportal.usermgt.dao;

import java.sql.Connection;

import com.iss.itreasury.fcinterface.bankportal.sysframe.dao.BaseDAO_oracle;
import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.system.SystemException;

public class UserDAO_oracle extends BaseDAO_oracle implements UserDAO
{
	/**
	 * @param tableName
	 * @param isNeedPrefix
	 * @param conn
	 * @throws SystemException
	 */
	public UserDAO_oracle(Connection conn) throws SystemException
	{
		super(tableName, isNeedPrefix, conn);
		this.setIDType(ID_TYPE_MAXID);
	}
}
