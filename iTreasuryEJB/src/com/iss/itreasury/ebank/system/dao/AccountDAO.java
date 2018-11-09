/*
 * Created on 2005-5-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.ebank.system.dao;

import com.iss.itreasury.dao.ITreasuryDAO;

/**
 * @author ÂíÏÖ¸£
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AccountDAO extends ITreasuryDAO
{
	public static final String strTableName = "BS_BANKACCOUNTINFO";
	public static boolean isNeedPrefix = true;
	public static long CHECK = 2;
	public static long APPROVING = 3;
	public static long APPROVE = 4;
	public static long REFUSE = 5;
}
