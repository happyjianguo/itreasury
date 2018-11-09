package com.iss.itreasury.ebank.obdiscountapply.bizlogic;
import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import java.util.*;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.ebank.obdiscountapply.dataentity.*;
public interface OBDiscountApply extends EJBObject
{
	public long saveDiscount1(DiscountMainInfo info) throws RemoteException, IRollbackException;
	public long saveDiscount2(DiscountBillStatInfo info) throws RemoteException, IRollbackException;
	public long updateStatus(long lDiscountID, long lStatusID) throws RemoteException, IRollbackException;
	public long deleteDiscountBill(long[] lDiscountBillID) throws RemoteException, IRollbackException;
	public Vector findDiscountBillByDiscountID(DiscountBillQueryInfo info) throws RemoteException, IRollbackException;
	public long saveDiscountBill(DiscountBillInfo info) throws RemoteException, IRollbackException;
	public long updateDiscountBill(DiscountBillInfo info) throws RemoteException, IRollbackException;
	public Vector findByID(DiscountBillQueryInfo discountBillQueryInfo) throws RemoteException, IRollbackException;
	public DiscountBillInfo findDiscountBillByID(long lDiscountBillID) throws RemoteException, IRollbackException;
	public DiscountInfo findDiscountByID(long lDiscountID) throws RemoteException, IRollbackException;
	public Collection findDiscountBillByContractID(DiscountBillQueryInfo info) throws RemoteException, IRollbackException;
	public DiscountCredenceInfo findDiscountCredence(DiscountCredenceQueryInfo info) throws RemoteException, IRollbackException;
	public long saveDiscountCredence(DiscountCredenceInfo info) throws RemoteException, IRollbackException;
	public long updateDiscountCredence(DiscountCredenceInfo info) throws RemoteException, IRollbackException;
	public long updateCredenceStatus(long lDiscountCredenceID, long lStatusID) throws RemoteException, IRollbackException;
	public long updateDiscount(DiscountInfo info) throws RemoteException, IRollbackException;
    /**
     * ninh 2004-3-10
     * Method cancelDiscountCredenceByID.
     * 取消贴现票据明细信息
     * @param lDiscountCredenceID
     * @return long
     * @throws Exception
     */
    public long cancelDiscountCredenceByID(long lDiscountCredenceID) throws RemoteException, IRollbackException;
}