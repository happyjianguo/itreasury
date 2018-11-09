package com.iss.itreasury.settlement.bizdelegation;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;

import com.iss.itreasury.settlement.transbakreserve.bizlogic.TransBakReserve;
import com.iss.itreasury.settlement.transbakreserve.bizlogic.TransBakReserveHome;
import com.iss.itreasury.settlement.transbakreserve.dataentity.TransBakReserveInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositAssembler;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;

public class TransBakReserveDelegation {
	
	private TransBakReserve transBakReserveFacade;

	public TransBakReserveDelegation() throws RemoteException, IRollbackException
	{
		try
		{

			TransBakReserveHome home =
				(TransBakReserveHome) EJBHomeFactory.getFactory().lookUpHome(TransBakReserveHome.class);
			transBakReserveFacade = (TransBakReserve) home.create();
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
	 * Method preSave.
	 * @param TransBakReserveInfo
	 * @return int 0: 正常 1: 委付凭证不可用--提示输入 2: 票据不可用 3: 重复交易记录 4: 账户透支
	 * @throws RemoteException, IRollbackException
	 */
	public long preSave(TransBakReserveInfo info) throws RemoteException, IRollbackException
	{
	    return transBakReserveFacade.preSave(info);

	}
	/**
	 * Method tempSave.
	 * @param TransBakReserveInfo
	 * @return long
	 * @throws  RemoteException, IRollbackException
	 */
	public long tempSave(TransBakReserveInfo info) throws RemoteException, IRollbackException
	{
		return transBakReserveFacade.tempSave(info);
	}
	
	/**
	 * Method findByConditions.
	 * @param1 TransBakReserveInfo   
	 * @param2 isMatch 是否是匹配查找
	 * @return Collection 根据条件查找
	 * @throws  RemoteException, IRollbackException
	 */
	public Collection findByConditions(TransBakReserveInfo info, int orderByType, boolean isDesc) throws RemoteException, IRollbackException
	{
		return transBakReserveFacade.findByConditions(info, orderByType, isDesc);

	}
	/**
	 * Method saveUpreceive.总部-备付金上收-保存
	 * @param1 TransBakReserveInfo  
	 * @param2 InutParameterInfo  
	 * @return long 保存的记录ID
	 * @throws RemoteException,IRollbackException
	 */
	public long saveUpreceive(TransBakReserveInfo info,InutParameterInfo pInfo) throws RemoteException, IRollbackException
	{
		return transBakReserveFacade.saveUpreceive(info,pInfo);

	}
	/**
	 * Method saveDownReturn.总部-备付金调回-保存
	 * @param1 TransBakReserveInfo  
	 * @param2 InutParameterInfo  
	 * @return long 保存的记录ID
	 * @throws RemoteException,IRollbackException
	 */
	public long saveDownReturn(TransBakReserveInfo info,InutParameterInfo pInfo) throws RemoteException, IRollbackException
	{
		return transBakReserveFacade.saveDownReturn(info);

	}
	/**
	 * Method deleteUpreceive.总部-备付金上收-删除
	 * @param TransBakReserveInfo
	 * @return long 删除的记录ID
	 * @throws RemoteException,IRollbackException
	 */
	public long deleteUpreceive(TransBakReserveInfo info) throws RemoteException, IRollbackException
	{
		return transBakReserveFacade.deleteUpreceive(info);

	}
	
	/**
	 * Method deleteDownReturn.总部-备付金调回-删除
	 * @param TransBakReserveInfo
	 * @return long 删除的记录ID
	 * @throws RemoteException,IRollbackException
	 */
	public long deleteDownReturn(TransBakReserveInfo info) throws RemoteException, IRollbackException
	{
		return transBakReserveFacade.deleteDownReturn(info);

	}
	
	/**
	* 方法说明：根据查询条件匹配
	*  Method  matchUpreceive.总部-备付金上收-复核匹配
	* @param TransBakReserveInfo info
	* @return TransBakReserveInfo
	* @throws RemoteException,IRollbackException
	*/
	public TransBakReserveInfo matchUpreceive(TransBakReserveInfo info) throws RemoteException, IRollbackException
	{

		return transBakReserveFacade.matchUpreceive(info);

	}	
	/**
	* 方法说明：根据查询条件匹配
	*  Method  matchUpreceive.总部-备付金调回-复核匹配
	* @param TransBakReserveInfo info
	* @return TransBakReserveInfo
	* @throws RemoteException,IRollbackException
	*/
	public TransBakReserveInfo matchDownReturn(TransBakReserveInfo info)
		throws RemoteException, IRollbackException
	{

		return transBakReserveFacade.matchDownReturn(info);

	}	
	/**
	  * 方法说明：根据记录ID进行复核（总部-备付金上收-复核）
	 * @param TransBakReserveInfo
	 * @return long 复核的记录ID
	 * @throws RemoteException, IRollbackException
	 */
	public long checkUpreceive(TransBakReserveInfo info) throws RemoteException, IRollbackException
	{
		long res = transBakReserveFacade.checkUpreceive(info);
		return res;
	}
	/**
	  * 方法说明：根据记录ID进行复核（总部-备付金调回-复核）
	 * @param TransBakReserveInfo
	 * @return long 复核的记录ID
	 * @throws RemoteException, IRollbackException
	 */
	public long checkDownReturn(TransBakReserveInfo info) throws RemoteException, IRollbackException
	{
		long res = transBakReserveFacade.checkDownReturn(info);
		return res;
	}
	/**
	 * 方法说明：根据记录ID进行取消复核（总部-备付金上收-取消复核）
	 * @param TransBakReserveInfo
	 * @return long 取消复核的记录ID
	 * @throws RemoteException, IRollbackException
	 */
	public long cancelCheckUpreceive(TransBakReserveInfo info) throws RemoteException, IRollbackException
	{
		long res = transBakReserveFacade.cancelCheckUpreceive(info);
		return res;
	}

	/**
	 * 方法说明：根据记录ID进行取消复核（总部-备付金调回-取消复核）
	 * @param TransBakReserveInfo
	 * @return long 取消复核的记录ID
	 * @throws RemoteException, IRollbackException
	 */
	public long cancelCheckDownReturn(TransBakReserveInfo info) throws RemoteException, IRollbackException
	{
		long res = transBakReserveFacade.cancelCheckDownReturn(info);
		return res;
	}

	/**
	 * 方法说明：根据账户ID，得到账户信息
	 * @param transID
	 * @return TransBakReserveInfo
	 * @throws RemoteException,IRollbackException
	 */
	public TransBakReserveInfo findByID(long transID) throws RemoteException, IRollbackException
	{
		return transBakReserveFacade.findByID(transID);

	}

	
	/**
	* 方法说明： 总部-备付金上收-审批（没有实现，结算暂不直接审批）
	*  Method  doApprovalUpreceive.
	* @param TransBakReserveInfo info InutParameterInfo pInfo
	* @return 审批记录id
	* @throws RemoteException, IRollbackException
	*/
	public long doApprovalUpreceive(TransBakReserveInfo info,InutParameterInfo pInfo) throws RemoteException, IRollbackException
	{

		return transBakReserveFacade.doApproval(info);

	}	
	
	/**
	* 方法说明： 总部-备付金调回-审批（没有实现，结算暂不直接审批）
	*  Method  doApprovalUpreceive.
	* @param TransBakReserveInfo info InutParameterInfo pInfo
	* @return 审批记录id
	* @throws RemoteException, IRollbackException
	*/
	public long doApprovalDownReturn(TransBakReserveInfo info,InutParameterInfo pInfo)
		throws RemoteException, IRollbackException
	{

		return transBakReserveFacade.doApproval(info);

	}
	/**
	* 方法说明： 总部-备付金上收-取消审批（没有实现，结算暂不直接审批）
	*  Method  doApprovalUpreceive.
	* @param TransBakReserveInfo info InutParameterInfo pInfo
	* @return 审批记录id
	* @throws RemoteException, IRollbackException
	*/
	public long cancelApprovalUpreceive(TransBakReserveInfo info,InutParameterInfo pInfo)
		throws RemoteException, IRollbackException
	{

		return transBakReserveFacade.cancelApproval(info);

	}

	/**
	* 方法说明： 总部-备付金调回-取消审批（没有实现，结算暂不直接审批）
	*  Method  doApprovalUpreceive.
	* @param TransBakReserveInfo info InutParameterInfo pInfo
	* @return 审批记录id
	* @throws RemoteException, IRollbackException
	*/
	public long cancelApprovalDownReturn(TransBakReserveInfo info,InutParameterInfo pInfo)
		throws RemoteException, IRollbackException
	{

		return transBakReserveFacade.cancelApproval(info);

	}
}
