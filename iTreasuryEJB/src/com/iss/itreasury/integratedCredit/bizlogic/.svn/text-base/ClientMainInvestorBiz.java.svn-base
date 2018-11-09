package com.iss.itreasury.integratedCredit.bizlogic;

import java.util.Collection;

import com.iss.itreasury.integratedCredit.dao.ClientMainInvestorDao;
import com.iss.itreasury.integratedCredit.dataentity.ClientMainInvestorInfo;
import com.iss.itreasury.util.IException;

public class ClientMainInvestorBiz {
	
	ClientMainInvestorDao mainInvestDao = new ClientMainInvestorDao();
	
	public Collection findbyClientId(long clientid)throws Exception
	{
		Collection c = null;
		c = mainInvestDao.findbyClientId(clientid);
		return c;
	}
	public long SaveClientMainInvest(ClientMainInvestorInfo cinvest)throws IException,Exception
	{
		long lResult=-1;
		lResult = mainInvestDao.SaveClientMainInvest(cinvest);
		return lResult;
	}
	public long UpdateClientMainInvest(ClientMainInvestorInfo cinvest)throws IException,Exception
	{
		long lResult=-1;
		lResult = mainInvestDao.UpdateClientMainInvest(cinvest);
		return lResult;
	}
	public long DeleteClientMainInvest(long clientid)throws IException,Exception
	{
		long lResult=-1;
		lResult = mainInvestDao.DeleteClientMainInvest(clientid);
		return lResult;
	}

}
