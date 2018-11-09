/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-4-6
 */
package com.iss.itreasury.securities.bizdelegation;

import java.rmi.RemoteException;
import java.sql.Timestamp;

import javax.ejb.CreateException;

import com.iss.itreasury.securities.deliveryorderservice.bizlogic.DeliveryOrderServiceOperation;
import com.iss.itreasury.securities.endprocess.bizlogic.EndProcess;
import com.iss.itreasury.securities.endprocess.bizlogic.EndProcessHome;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.securitiesgeneralledger.dao.SEC_GLEntryDAO;
import com.iss.itreasury.securities.util.DailyOperation;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;

/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class EndProcessDelegation {

	private EndProcess endProcessFacade = null;	
	
	public EndProcessDelegation() throws RemoteException{
		try
		{
			EndProcessHome home;
			try {
				home =
					(EndProcessHome) EJBHomeFactory.getFactory().lookUpHome(
							EndProcessHome.class);
			} catch (IException e) {
				throw new RemoteException("EJBHomeFactory连接错误",e);
			}
			endProcessFacade = (EndProcess) home.create();
		}

		catch (CreateException ce)
		{
			throw new RemoteException("发生CreateException",ce);
		}

	}
	
	/**
	 * 资金库存日结
	 * */
	public void endProcess(long officeID,long currencyID)  throws RemoteException,SecuritiesException{
		DailyOperation dailyOperation = new DailyOperation();
		
		try {
			dailyOperation.dealAll();
			DeliveryOrderServiceOperation doOperation = new DeliveryOrderServiceOperation();
			
			doOperation.checkViolativeDeliveryOrder(Env.getSecuritiesSystemDate(officeID, currencyID));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException();
		}
		
		endProcessFacade.endProcess(officeID, currencyID);
	}	
	
	/**
	 * 向Oracle-Finance过帐
	 * */
	public void postGLVoucher(long lOfficeID, long lCurrencyID, Timestamp tsStart, Timestamp tsEnd) throws Exception{
		endProcessFacade.postGLVoucher(lOfficeID, lCurrencyID, tsStart, tsEnd);
	}
	
	/**
	 * 获取本次过帐日期
	 * */
	public Timestamp getLastPostGLVoucherDate()throws SecuritiesException{
		SEC_GLEntryDAO dao = new SEC_GLEntryDAO();
		return dao.getLastPostGLVoucherDate();
	}
}
