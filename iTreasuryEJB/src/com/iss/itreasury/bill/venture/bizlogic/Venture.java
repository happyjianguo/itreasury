/* Generated by Together */
package com.iss.itreasury.bill.venture.bizlogic;
import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import java.util.Vector;
import com.iss.itreasury.bill.util.BillException;
import com.iss.itreasury.bill.venture.dataentity.BlackBillInfo;
import com.iss.itreasury.bill.venture.dataentity.BlackConditionInfo;
import com.iss.itreasury.bill.venture.dataentity.BlackQueryCondition;

public interface Venture extends EJBObject
{
	/**
	 * Method add.
	 * 
	 * @return long
	 * @throws BillException
	 * @throws RemoteException
	 */
	public long add(BlackConditionInfo bci) throws BillException, RemoteException;
	/**
	 * Method findByCondition.
	 * @param bci
	 * @return Vector
	 * @throws BillException
	 * @throws RemoteException
	 */
	public Vector findByCondition(BlackQueryCondition bci) throws BillException, RemoteException;
	/**
	 * Method releaseFromBlackList.
	 * @param ID
	 * @return long
	 * @throws BillException
	 * @throws RemoteException
	 */
	public long releaseFromBlackList(long[] IDs) throws BillException, RemoteException;
	/**
	 * Method findByID.
	 * @param ID
	 * @return BlackBillInfo
	 * @throws BillException
	 * @throws RemoteException
	 */
	public BlackBillInfo findByID(long ID) throws BillException, RemoteException;
	/**
	 * Method delete.
	 * @param ID
	 * @return long
	 * @throws BillException
	 * @throws RemoteException
	 */
	public long delete(long[] IDs) throws BillException, RemoteException;
	/**
	 * Method update.
	 * @param bbi
	 * @return long
	 * @throws BillException
	 * @throws RemoteException
	 */
	public long update(BlackBillInfo bbi) throws BillException, RemoteException;
	/** @link dependency */
	/*# VentureEJB lnkSession1Bean; */
}
