/*
 * Created on 2004-2-10
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obquery.bizlogic;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
import java.sql.*;
import com.iss.itreasury.util.*;
import java.util.*;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.loan.query.dataentity.*;
import com.iss.itreasury.ebank.util.*;
import com.iss.itreasury.ebank.obdao.OBBaseDao;
import com.iss.itreasury.ebank.obdataentity.*;
import com.iss.itreasury.ebank.obquery.dataentity.*;
import com.iss.itreasury.ebank.obquery.dao.*;

public class OBInstrQuery
{
	public Collection queryInstr(QuerySumInfo sumInfo ,OBQueryTermInfo termInfo) throws Exception
	{
		OBQueryDao dao=new OBQueryDao();
		Collection c=null;
		try
		{
			c=dao.queryInstr(sumInfo, termInfo );
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return c;
	}
	public static void main(String[] args)
	{
	}
}
