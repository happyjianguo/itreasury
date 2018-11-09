package com.iss.itreasury.fcinterface.bankportal.usermgt.dao;

import java.sql.Connection;

import com.iss.itreasury.fcinterface.bankportal.sysframe.dao.BaseDAO_db2;
import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.system.SystemException;

public class UserDAO_db2 extends BaseDAO_db2 implements UserDAO
{
	/**
	 * @param tableName
	 * @param isNeedPrefix
	 * @param conn
	 * @throws SystemException
	 */
	public UserDAO_db2(Connection conn) throws SystemException
	{
		super(tableName, isNeedPrefix, conn);
		this.setIDType(ID_TYPE_MAXID);
	}
}
