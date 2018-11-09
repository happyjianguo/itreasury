package com.iss.itreasury.settlement.bizdelegation;

import java.rmi.RemoteException;
import java.util.Collection;

import com.iss.itreasury.settlement.transfinancialmargin.bizlogic.TransFinancialMargin;
import com.iss.itreasury.settlement.transfinancialmargin.bizlogic.TransFinancialMarginHome;
import com.iss.itreasury.settlement.transfinancialmargin.dataentity.TransFinancialMarginInfo;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.TransMarginWithdrawInfo;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IRollbackException;

public class TransFinancialMarginDelegation {
	private TransFinancialMargin transFinancialMargin;

	public TransFinancialMarginDelegation() throws RemoteException,IRollbackException
	{
		try
		{
			TransFinancialMarginHome home = (TransFinancialMarginHome)EJBHomeFactory.getFactory().lookUpHome(TransFinancialMarginHome.class);
			transFinancialMargin = home.create();
		}
		catch(Exception e){
			throw new RemoteException("CreateException in TransMarginDepositDelegation", e);			
		}
	}
	/**
	 * �������ޱ�֤�𱣺���֧ȡ���׵��ݴ淽��
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long drawTempSave(TransFinancialMarginInfo info) throws RemoteException,IRollbackException
	{
		return transFinancialMargin.drawTempSave(info);	
	}
	/**
	 * �������ޱ�֤�𱣺���֧ȡ���׵ñ��淽��
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long drawSave(TransFinancialMarginInfo info) throws RemoteException,IRollbackException
	{
		return transFinancialMargin.drawSave(info);
	}
	
	/**
	 *  �������ޱ�֤�𱣺��� ҵ�񸴺� ƥ�� ����
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection drawMatch(TransFinancialMarginInfo info,long typeFlag) throws RemoteException,IRollbackException
	{
		return transFinancialMargin.drawMatch(info,typeFlag);	
	}
	
	/**
	 *  �������ޱ�֤�𱣺��� ���ݽ��׺Ų�ѯtransNO
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransFinancialMarginInfo findByTransNO(String strTransNO) throws RemoteException,IRollbackException
	{
		return transFinancialMargin.findByTransNO(strTransNO);	
	}
	
	/**
	 * ͨ��ID����
	 * @param ID
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransFinancialMarginInfo drawFindByID(long ID) throws RemoteException,IRollbackException
	{
		return transFinancialMargin.drawFindByID(ID);
	}
	/**
	 * ����
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long drawCheck(TransFinancialMarginInfo info) throws RemoteException,IRollbackException
	{
		return transFinancialMargin.drawCheck(info);
	}
	/**
	 * ��״̬���ң���Ҫ�����Ӳ��ҵ�ʱ��ʹ�� ҵ�񸴺�
	 * @param info
	 * @param lStatusIDs
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection drawFindByStatus4Check(TransFinancialMarginInfo info,long[] lStatusIDs) throws RemoteException,IRollbackException
	{
		return transFinancialMargin.drawFindByStatus4Check(info,lStatusIDs);
	}
	/**
	 * ��״̬���ң���Ҫ�����Ӳ��ҵ�ʱ��ʹ�� ҵ����
	 * @param info
	 * @param lStatusIDs
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection drawFindByStatus4Deal(TransFinancialMarginInfo info,long[] lStatusIDs) throws RemoteException,IRollbackException
	{
		return transFinancialMargin.drawFindByStatus4Deal(info,lStatusIDs);
	}
	
	/**
	 * ȡ��
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long drawCancel(TransFinancialMarginInfo info) throws RemoteException,IRollbackException
	{
		return transFinancialMargin.drawCancel(info);
	}
	/**
	 * ȡ������
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long drawCancelCheck(TransFinancialMarginInfo info) throws RemoteException,IRollbackException
	{
		return transFinancialMargin.drawCancelCheck(info);
	}
	
}
