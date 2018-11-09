/*
 * Created on 2006-2-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.transupsave.dao;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;

/**
 * @author hyzeng
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Sys_CurrencySetDAO extends SettlementDAO
{
	public String findNoByID(long id) throws ITreasuryDAOException
	{
		String no  = "";
		
		try
		{
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append(" SELECT currcode FROM sys_currencyset \n");
			buffer.append(" WHERE id= ? \n");
			log.debug(buffer.toString());
			
			transPS = prepareStatement(buffer.toString());
			transPS.setLong(1,id);
			
			transRS = executeQuery();
			if (transRS != null)
			{
				if (transRS.next())
				{		
					no = transRS.getString("currcode");
				}
			}
			
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{			
			finalizeDAO();			
		}
		
		return no;
	}
}
