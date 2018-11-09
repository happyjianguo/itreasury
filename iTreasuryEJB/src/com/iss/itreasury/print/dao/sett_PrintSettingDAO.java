/*
 * Created on 2004-12-27
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.print.dao;

import java.sql.ResultSet;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.print.dataentity.PrintSettingInfo;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.SessionMng;

/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class sett_PrintSettingDAO extends ITreasuryDAO
{
	public sett_PrintSettingDAO()
	{
		super("sett_PrintSetting");
	}
	
	public long findSettingID (SessionMng sessionMng) throws Exception
	{
		try
		{
			long lID = -1;
			initDAO();
			String strSQL = " select ID from sett_PrintSetting where Office = "+sessionMng.m_lOfficeID+" and Currency = "+sessionMng.m_lCurrencyID+" and ModuleType = "+sessionMng.m_lModuleID;
			
			PrintSettingInfo printSettingInfo = new PrintSettingInfo();
			
			System.out.println("¥Ú”°…Ë÷√ find by ID £∫" + strSQL);
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();
			if(rs.next())
			{
				lID = rs.getLong("ID");
			}
			finalizeDAO();
			
			return lID;
		}
		catch (IException e)
		{
			e.printStackTrace();
		}
		finally
		{
			finalizeDAO();
		}
		return -1;
	}

	
	
}
