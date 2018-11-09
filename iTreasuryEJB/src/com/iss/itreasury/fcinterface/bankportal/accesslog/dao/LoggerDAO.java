/*
 * Created on 2007-2-6
 *
 * 业务日志。记录用户所点击过的菜单项。
 */
package com.iss.itreasury.fcinterface.bankportal.accesslog.dao;


import com.iss.itreasury.fcinterface.bankportal.sysframe.dao.BaseDAO;
/**
 * @author xinan
 *
 */
public interface LoggerDAO extends BaseDAO {

	public static final String tableName = "bs_accesslogger";
	public static boolean isNeedPrefix = true;
}
