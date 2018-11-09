/*
 * Created on 2004-3-1
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obprint.bizlogic;

import com.iss.itreasury.ebank.util.*;
import com.iss.itreasury.ebank.obdao.OBBaseDao;
import com.iss.itreasury.ebank.obprint.dataentity.*;
import com.iss.itreasury.ebank.obprint.dao.*;
/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OBPrint {

	public long getPrintTimes(OBPrintLogInfo pInfo) throws Exception
	{
		long ret=-1;
		OBPrintDao dao=new OBPrintDao();
		try {
			ret=dao.getPrintTimes( pInfo );
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return ret;
	}
	
	public void updatePrintTimes(OBPrintLogInfo pInfo) throws Exception
	{
		OBPrintDao dao=new OBPrintDao();
		try {
			dao.updatePrintTimes( pInfo );
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
