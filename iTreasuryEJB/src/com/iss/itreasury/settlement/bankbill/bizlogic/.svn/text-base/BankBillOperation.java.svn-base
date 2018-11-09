/*
 * Created on 2003-9-10
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.bankbill.bizlogic;
import com.iss.itreasury.util.*;

import java.rmi.RemoteException;
import java.sql.Timestamp;

import javax.ejb.CreateException;
/**
 * @author wlming
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class BankBillOperation
{
	private BankBill ejb;
	public BankBillOperation() throws RemoteException
	{
		try
		{
			BankBillHome home = (BankBillHome) EJBHomeFactory.getFactory().lookUpHome(BankBillHome.class);
			ejb = (BankBill) home.create();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			throw new RemoteException("");
		}
	}

	/**检查票据是否可用的方法：
	 * @param lBankID 银行ID
	 * @param lBankBillTypeID 票据类型ID
	 * @param strBankBillNo 票据编号
	 * @param lRequireClientID 申领客户ID
	 * @return long 1, 可用；0, 不可用
	 * @exception
	 */
	public long checkIsUseable(long lTypeID, long lBankID, String strBillNo, long lRequireClientID)
		throws RemoteException, IRollbackException
	{
		return ejb.checkIsUseable(lTypeID, lBankID, strBillNo, lRequireClientID);

	}
	/**
	 * 功能：票据使用时调用的方法：
	 * @param lBankID 银行ID
	 * @param lBankBillTypeID 票据类型ID
	 * @param strBankBillNo 票据编号
	 * @param lRequireClientID 申领客户ID(对于证实书类，必输)
	 * @param tsDate 使用日期
	 * @param lUserID 操作人
	 * @return void
	 * @exception 
	 */
	public void useBankBill(
		long lBankID,
		long lBankBillTypeID,
		String strBankBillNo,
		long lRequireClientID,
		Timestamp tsDate,
		long lUserID)
		throws RemoteException, IRollbackException
	{
		ejb.useBankBill(lBankID, lBankBillTypeID, strBankBillNo, lRequireClientID, tsDate, lUserID);

	}

	/**
	 * 功能：票据取消使用（删除交易）时调用的方法：
	 * @param lBankID 银行ID
	 * @param lBankBillTypeID 票据类型ID
	 * @param strBankBillNo 票据编号
	 * @param tsDate 操作日期
	 * @param lUserID 操作人
	 * @return void
	 * @exception 
	 */
	public void cancelUseBankBill(
		long lBankID,
		long lBankBillTypeID,
		String strBankBillNo,
		Timestamp tsDate,
		long lUserID)
		throws RemoteException, IRollbackException
	{
		ejb.cancelUseBankBill(lBankID, lBankBillTypeID, strBankBillNo, tsDate, lUserID);

	}
	/**
	 * 功能：票据终止时调用的方法：
	 * @param lBankID 银行ID
	 * @param lBankBillTypeID 票据类型ID
	 * @param strBankBillNo 票据编号
	 * @return void
	 * @exception 
	 */
	public void terminateBankBill(
		long lBankID,
		long lBankBillTypeID,
		String strBankBillNo)
		throws RemoteException, IRollbackException
	{
		ejb.terminateBankBill(lBankID, lBankBillTypeID, strBankBillNo);

	}
	/**
	 * 功能：票据取消终止时调用的方法：
	 * @param lBankID 银行ID
	 * @param lBankBillTypeID 票据类型ID
	 * @param strBankBillNo 票据编号
	 * @return void
	 * @exception 
	 */
	public void cancelTerminateBankBill(
		long lBankID,
		long lBankBillTypeID,
		String strBankBillNo)
		throws RemoteException, IRollbackException
	{
		ejb.cancelTerminateBankBill(lBankID, lBankBillTypeID, strBankBillNo);

	}
	
	/**增加票据发放的银行（开户行）：
	 * @param lBankID 银行ID
	 * @param aryBankBillTypeID 票据类型ID的数据
	 * @return void
	 * @exception
	 */
	public void addGrantBillBank(long lBankID,long[] aryBankBillTypeID) throws RemoteException, IRollbackException
	{
		ejb.addGrantBillBank(lBankID,aryBankBillTypeID);
	}
	
	/**删除票据发放的银行（开户行）：
	 * @param lBankID 银行ID
	 * @param aryBankBillTypeID 票据类型ID的数据
	 * @return void
	 * @exception
	 */
	public void dropGrantBillBank(long lBankID,long[] aryBankBillTypeID) throws RemoteException, IRollbackException
	{
		ejb.dropGrantBillBank(lBankID,aryBankBillTypeID);
	}
	
	/**判断银行是否是发票银行的方法：
	 * @param lBankID 银行ID
	 * @return void
	 * @exception
	 */
	public boolean isGrantBillBank(long lBankID) throws RemoteException, IRollbackException
	{
		return ejb.isGrantBillBank(lBankID);
	}
}
