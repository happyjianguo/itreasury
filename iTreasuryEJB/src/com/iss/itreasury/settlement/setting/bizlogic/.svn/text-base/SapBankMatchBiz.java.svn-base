package com.iss.itreasury.settlement.setting.bizlogic;

import java.util.Vector;
import com.iss.itreasury.settlement.setting.dao.SapBankMatchDao;
import com.iss.itreasury.settlement.setting.dataentity.SapBankMatchInfo;
import com.iss.itreasury.util.IException;

public class SapBankMatchBiz {

    
	public Vector findAllSapBankMatch(SapBankMatchInfo qbi) throws IException
	{
		Vector v = new Vector();
		try
		{
			SapBankMatchDao sapbankmatchdao = new SapBankMatchDao();
			v = sapbankmatchdao.querySapBankMatch(qbi);
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
	
	
	public boolean isSameBanktype(SapBankMatchInfo sapinfo) throws IException
	{ 
		boolean isSame = false;
		try
		{
			SapBankMatchDao sapbankmatchdao = new SapBankMatchDao();
			long lReturn = sapbankmatchdao.isSameBanktype(sapinfo);
			if(lReturn > 0)
			{
				isSame = true;
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
		return isSame;
	}
	
	public long saveSapBankMatch(SapBankMatchInfo info) throws IException
	{
		long lID = -1;
		try
		{
			SapBankMatchDao sapbankmatchdao = new SapBankMatchDao();
			
				lID = sapbankmatchdao.add(info);
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
	
	public long updateSapBankMatch(SapBankMatchInfo info) throws IException
	{
		long lID = -1;
		try
		{
			   SapBankMatchDao sapbankmatchdao = new SapBankMatchDao();
				sapbankmatchdao.update(info);
				lID = info.getId();
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
	
	public long deleteSapBankMatch(long lID) throws IException
	{
		long lResult = -1;
		try
		{
			SapBankMatchDao sapbankmatchdao = new SapBankMatchDao();
			lResult = sapbankmatchdao.deleteSapBankMacth(lID);
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
