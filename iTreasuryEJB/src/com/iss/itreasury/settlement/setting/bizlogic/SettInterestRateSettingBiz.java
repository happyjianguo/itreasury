package com.iss.itreasury.settlement.setting.bizlogic;

import java.util.Collection;
import java.sql.Connection;
import java.util.Vector;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.settlement.setting.dao.Sett_FilialeAccountSetDAO;
import com.iss.itreasury.settlement.setting.dataentity.FilialeAccountInfo;
import com.iss.itreasury.settlement.setting.dataentity.QueryFilialeAccountInfo;

/**
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class SettInterestRateSettingBiz
{
	private Connection conn = null;
	/**
	 * Constructor for FilialeBiz.
	 */
	public SettInterestRateSettingBiz()
	{
		try
		{
			conn = Database.getConnection();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public FilialeAccountInfo findbyID(long lID) throws IException
	{
		FilialeAccountInfo fi = null;
		try
		{
			Sett_FilialeAccountSetDAO sett_FilialeAccountSetDAO = new Sett_FilialeAccountSetDAO(conn);
			fi = sett_FilialeAccountSetDAO.findByID(lID);
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return fi != null ? fi : null;
	}
	
	public Collection findByCondition(QueryFilialeAccountInfo qfi) throws IException
	{
		Vector v = new Vector();
		try
		{
			Sett_FilialeAccountSetDAO sett_FilialeAccountSetDAO = new Sett_FilialeAccountSetDAO(conn);
			v = sett_FilialeAccountSetDAO.findByCondition(qfi);
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return v != null ? v : null;
	}
	
	public long save(FilialeAccountInfo info) throws IException
	{
		long lID = -1;
		try
		{
			Sett_FilialeAccountSetDAO sett_FilialeAccountSetDAO = new Sett_FilialeAccountSetDAO(conn);
			if( sett_FilialeAccountSetDAO.verifyByInfo(info) > 0)
			{
				lID = -2;
			}
			else
			{
				if( info.getID() < 0 )
				{
					lID = sett_FilialeAccountSetDAO.add(info);
				}
				else
				{
					sett_FilialeAccountSetDAO.update(info);
					lID = info.getID();
				}
			}
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lID;
	}
	
	public long delete(long lID) throws IException
	{ 
		long lResult = -1;
		try
		{
			Sett_FilialeAccountSetDAO sett_FilialeAccountSetDAO = new Sett_FilialeAccountSetDAO(conn);
			lResult = sett_FilialeAccountSetDAO.delete(lID);
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lResult;		
	}
}
