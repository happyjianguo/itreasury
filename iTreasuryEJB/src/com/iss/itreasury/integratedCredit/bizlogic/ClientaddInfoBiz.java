package com.iss.itreasury.integratedCredit.bizlogic;

import java.util.Collection;

import com.iss.itreasury.integratedCredit.dao.ClientaddinfoDao;
import com.iss.itreasury.integratedCredit.dataentity.Clientaddinfo;
import com.iss.itreasury.util.IException;

public class ClientaddInfoBiz {
	ClientaddinfoDao dao = new ClientaddinfoDao();
	public Collection findbyClientId(long clientid)throws Exception
	{
		Collection coll = null;
		coll = dao.findbyClientId(clientid);
		return coll;
	}
	public long SaveClientaddinfo(Clientaddinfo clientadd)throws IException,Exception
	{
		long lResult=-1;
		lResult = dao.SaveClientaddinfo(clientadd);		
		return lResult;
	}
	public long UpdateClientaddinfo(Clientaddinfo clientadd)throws IException,Exception
	{
		long lResult=-1;
		lResult = dao.UpdateClientaddinfo(clientadd);	
		return lResult;
	}

}
