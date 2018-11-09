package com.iss.itreasury.settlement.bizdelegation;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;

import com.iss.itreasury.settlement.transreserve.bizlogic.TransReserve;
import com.iss.itreasury.settlement.transreserve.bizlogic.TransReserveHome;
import com.iss.itreasury.settlement.transreserve.dataentity.TransReserveInfo;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;

public class TransReserveDelegation {
	
	private TransReserve transReserveFacade;

	public TransReserveDelegation() throws RemoteException, IRollbackException
	{
		try
		{
			TransReserveHome home =
				(TransReserveHome) EJBHomeFactory.getFactory().lookUpHome(TransReserveHome.class);
			transReserveFacade = (TransReserve) home.create();
		}
		catch (RemoteException e)
		{
			throw e;
		}
		catch (IException e)
		{
			e.printStackTrace();
			throw new RemoteException();
		}
		catch (CreateException e)
		{
			e.printStackTrace();
			throw new RemoteException();
		}
	}
	/**
	 * Method tempSave.
	 * @param info
	 * @return long
	 * @throws RemoteException
	 */
	public long tempSave(TransReserveInfo info) throws RemoteException, IRollbackException
	{
		return transReserveFacade.tempSave(info);
	}
	
	/**
	 * Method delete.
	 * @param1 info  
	 * @param2 isMatch 是否是匹配查找
	 * @return long 根据条件查找
	 * @throws IException
	 */
	public Collection findByConditions(TransReserveInfo info, int orderByType, boolean isDesc)
		throws RemoteException, IRollbackException
	{
		return transReserveFacade.findByConditions(info, orderByType, isDesc);
	}
	
	public long saveReserveUpreceive(TransReserveInfo info,InutParameterInfo pInfo) throws RemoteException, IRollbackException
	{
		return transReserveFacade.saveReserveUpreceive(info,pInfo);
	}
	
	public long saveReserveDownReturn(TransReserveInfo info,InutParameterInfo pInfo) throws RemoteException, IRollbackException
	{
		return transReserveFacade.saveReserveDownReturn(info,pInfo);
	}
	/**
	 * Method delete.
	 * @param info
	 * @return long 删除的记录ID
	 * @throws IException
	 */
	public long deleteReserveUpreceive(TransReserveInfo info) throws RemoteException, IRollbackException
	{
		return transReserveFacade.deleteReserveUpreceive(info);
	}
	
	/**
	 * Method delete.
	 * @param info
	 * @return long 删除的记录ID
	 * @throws IException
	 */
	public long deleteReserveDownReturn(TransReserveInfo info) throws RemoteException, IRollbackException
	{
		return transReserveFacade.deleteReserveDownReturn(info);
	}
	/**
	* 方法说明：根据查询条件匹配
	*  Method  match.
	* @param Sett_TransCurrentDepositInfo info
	* @return Sett_TransCurrentDepositInfo
	*/
	public TransReserveInfo matchReserveUpreceive(TransReserveInfo info) throws RemoteException, IRollbackException
	{
		return transReserveFacade.matchReserveUpreceive(info);
	}	
	/**
	* 方法说明：根据查询条件匹配
	*  Method  match.
	* @param Sett_TransCurrentDepositInfo info
	* @return Sett_TransCurrentDepositInfo
	*/
	public TransReserveInfo matchReserveDownReturn(TransReserveInfo info) throws RemoteException, IRollbackException
	{
		return transReserveFacade.matchReserveDownReturn(info);
	}
	/**
	  * 方法说明：根据记录ID进行复核
	 * @param info
	 * @return long 复核的记录ID
	 * @throws IException
	 */
	public long checkReserveUpreceive(TransReserveInfo info) throws RemoteException, IRollbackException
	{
		long res = transReserveFacade.checkReserveUpreceive(info);
		return res;
	}
	
	/**
	  * 方法说明：根据记录ID进行复核
	 * @param info
	 * @return long 复核的记录ID
	 * @throws IException
	 */
	public long checkReserveDownReturn(TransReserveInfo info) throws RemoteException, IRollbackException
	{
		long res = transReserveFacade.checkReserveDownReturn(info);
		return res;
	}
	/**
	 * 方法说明：根据记录ID进行取消复核
	 * @param info
	 * @return long 取消复核 的记录ID
	 * @throws IException
	 */
	public long cancelCheckReserveUpreceive(TransReserveInfo info) throws RemoteException, IRollbackException
	{
		long res = transReserveFacade.cancelCheckReserveUpreceive(info);
		return res;
	}
	/**
	 * 方法说明：根据记录ID进行取消复核
	 * @param info
	 * @return long 取消复核 的记录ID
	 * @throws IException
	 */
	public long cancelCheckReserveDownReturn(TransReserveInfo info) throws RemoteException, IRollbackException
	{
		long res = transReserveFacade.cancelCheckReserveDownReturn(info);
		return res;
	}
	/**
	 * Method preSave.
	 * @param info
	 * @return int 0: 正常 1: 委付凭证不可用--提示输入 2: 票据不可用 3: 重复交易记录 4: 账户透支
	 * @throws IException
	 */
	public long preSave(TransReserveInfo info) throws RemoteException, IRollbackException
	{
	    return transReserveFacade.preSave(info);

	}
	

	/**
	 * 方法说明：根据账户ID，得到账户信息
	 * @param transCurrentDepositID
	 * @return Sett_TransCurrentDepositInfo
	 * @throws IRollbackException
	 */
	public TransReserveInfo findByID(long transID) throws RemoteException, IRollbackException
	{
		return transReserveFacade.findByID(transID);
	}

}
