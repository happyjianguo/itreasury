package com.iss.itreasury.settlement.setting.ejb;

import java.rmi.RemoteException;
import java.util.List;

import com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetInfo;
import com.iss.itreasury.util.IRollbackException;

public interface CurrencySetting extends javax.ejb.EJBObject{
	/**
	 * ±£¥Ê±“÷÷
	 */
	public long saveAllCurrency(long officeId,String strTransactionType) throws IRollbackException,RemoteException;
}
