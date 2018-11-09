/*
 * Created on 2005-5-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.fcinterface.bankportal.setting.currency.dao;

import com.iss.itreasury.fcinterface.bankportal.setting.client.dataentity.ClientInfo;
import com.iss.itreasury.fcinterface.bankportal.setting.currency.dataentity.CurrencyInfo;
import com.iss.itreasury.fcinterface.bankportal.sysframe.dao.BaseDAO;
import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.system.SystemException;
import com.iss.system.dao.PageLoader;

/**
 * @author mxzhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface CurrencyDAO extends BaseDAO
{
	public static final String tableName = "BS_CURRENCYSETTING";
	public static boolean isNeedPrefix = true;
	
	public long getCurrencyIDByCode(String code) throws SystemException;
	public long getBaseCurrencyID() throws SystemException;
	 public PageLoader findByCondition(CurrencyInfo paramInfo) throws SystemException;
}
