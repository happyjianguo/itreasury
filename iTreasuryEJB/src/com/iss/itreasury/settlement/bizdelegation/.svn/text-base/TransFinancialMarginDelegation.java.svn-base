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
	 * 融资租赁保证金保后处理支取交易的暂存方法
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
	 * 融资租赁保证金保后处理支取交易得保存方法
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
	 *  融资租赁保证金保后处理 业务复核 匹配 方法
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
	 *  融资租赁保证金保后处理 根据交易号查询transNO
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
	 * 通过ID查找
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
	 * 复核
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
	 * 按状态查找，主要是链接查找的时候使用 业务复核
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
	 * 按状态查找，主要是链接查找的时候使用 业务处理
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
	 * 取消
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
	 * 取消复核
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
