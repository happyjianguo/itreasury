package com.iss.itreasury.loan.closesystem;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Timestamp;

import javax.ejb.EJBObject;

public interface CloseSystem extends EJBObject {
	
	/**
	 * �ػ�����
	 * @param conn
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @param tsDate
	 * @return
	 * @throws Exception
	 * @throws RemoteException
	 */
	public boolean dealContractForShutDown(Connection conn, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception,RemoteException;
	
	/**
	 * ��������
	 * @param conn
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @param tsDate
	 * @return
	 * @throws Exception
	 * @throws RemoteException
	 */
	public boolean dealContractForOpen(Connection conn, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception,RemoteException;

}
