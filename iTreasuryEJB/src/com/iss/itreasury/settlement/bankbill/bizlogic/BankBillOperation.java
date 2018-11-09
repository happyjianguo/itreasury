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

	/**���Ʊ���Ƿ���õķ�����
	 * @param lBankID ����ID
	 * @param lBankBillTypeID Ʊ������ID
	 * @param strBankBillNo Ʊ�ݱ��
	 * @param lRequireClientID ����ͻ�ID
	 * @return long 1, ���ã�0, ������
	 * @exception
	 */
	public long checkIsUseable(long lTypeID, long lBankID, String strBillNo, long lRequireClientID)
		throws RemoteException, IRollbackException
	{
		return ejb.checkIsUseable(lTypeID, lBankID, strBillNo, lRequireClientID);

	}
	/**
	 * ���ܣ�Ʊ��ʹ��ʱ���õķ�����
	 * @param lBankID ����ID
	 * @param lBankBillTypeID Ʊ������ID
	 * @param strBankBillNo Ʊ�ݱ��
	 * @param lRequireClientID ����ͻ�ID(����֤ʵ���࣬����)
	 * @param tsDate ʹ������
	 * @param lUserID ������
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
	 * ���ܣ�Ʊ��ȡ��ʹ�ã�ɾ�����ף�ʱ���õķ�����
	 * @param lBankID ����ID
	 * @param lBankBillTypeID Ʊ������ID
	 * @param strBankBillNo Ʊ�ݱ��
	 * @param tsDate ��������
	 * @param lUserID ������
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
	 * ���ܣ�Ʊ����ֹʱ���õķ�����
	 * @param lBankID ����ID
	 * @param lBankBillTypeID Ʊ������ID
	 * @param strBankBillNo Ʊ�ݱ��
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
	 * ���ܣ�Ʊ��ȡ����ֹʱ���õķ�����
	 * @param lBankID ����ID
	 * @param lBankBillTypeID Ʊ������ID
	 * @param strBankBillNo Ʊ�ݱ��
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
	
	/**����Ʊ�ݷ��ŵ����У������У���
	 * @param lBankID ����ID
	 * @param aryBankBillTypeID Ʊ������ID������
	 * @return void
	 * @exception
	 */
	public void addGrantBillBank(long lBankID,long[] aryBankBillTypeID) throws RemoteException, IRollbackException
	{
		ejb.addGrantBillBank(lBankID,aryBankBillTypeID);
	}
	
	/**ɾ��Ʊ�ݷ��ŵ����У������У���
	 * @param lBankID ����ID
	 * @param aryBankBillTypeID Ʊ������ID������
	 * @return void
	 * @exception
	 */
	public void dropGrantBillBank(long lBankID,long[] aryBankBillTypeID) throws RemoteException, IRollbackException
	{
		ejb.dropGrantBillBank(lBankID,aryBankBillTypeID);
	}
	
	/**�ж������Ƿ��Ƿ�Ʊ���еķ�����
	 * @param lBankID ����ID
	 * @return void
	 * @exception
	 */
	public boolean isGrantBillBank(long lBankID) throws RemoteException, IRollbackException
	{
		return ejb.isGrantBillBank(lBankID);
	}
}
