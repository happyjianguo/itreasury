/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.securities.endprocess.bizlogic;

import java.rmi.RemoteException;
import java.sql.Timestamp;

import com.iss.itreasury.securities.exception.SecuritiesException;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface EndProcess extends javax.ejb.EJBObject
{
	/**
	 * 资金库存日结
	 * */
	public void endProcess(long officeID,long currencyID)  throws RemoteException,SecuritiesException;
	
	
	public void postGLVoucher(long lOfficeID, long lCurrencyID, Timestamp tsStart, Timestamp tsEnd) throws RemoteException,SecuritiesException;;	
}
