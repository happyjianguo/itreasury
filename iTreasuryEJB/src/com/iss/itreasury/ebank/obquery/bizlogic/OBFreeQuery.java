/*
 * Created on 2004-2-18
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obquery.bizlogic;

import java.sql.*;
import com.iss.itreasury.util.*;
import java.util.*;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.ebank.util.*;
import com.iss.itreasury.ebank.obdao.OBBaseDao;
import com.iss.itreasury.ebank.obdataentity.*;
import com.iss.itreasury.ebank.obquery.dataentity.*;
import com.iss.itreasury.ebank.obquery.dao.*;
import com.iss.itreasury.loan.freeapply.dataentity.*;
/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OBFreeQuery {

	public Collection questFree(OBQueryTermInfo qInfo) throws Exception
	{
		Collection c=null;
		try
		{
			OBQueryDao dao=new OBQueryDao();
			c=dao.questFree( qInfo );
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return c;
	}
	public FreeApplyInfo findFreeApplyByID(long ID) throws Exception
	{
		FreeApplyInfo info = null;
		Collection c=null;
		try
		{
			OBQueryDao dao=new OBQueryDao();
			info=dao.findFreeApplyByID( ID );
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return info;					
	}
}
