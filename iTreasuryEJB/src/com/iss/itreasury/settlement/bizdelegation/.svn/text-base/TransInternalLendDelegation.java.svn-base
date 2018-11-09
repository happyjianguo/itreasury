/**
 * Creat by kevin(刘连凯)
 * 2011-07-13
 * 内部拆借业务
 */
package com.iss.itreasury.settlement.bizdelegation;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;


import com.iss.itreasury.settlement.transinternallend.bizlogic.TransInternalLend;
import com.iss.itreasury.settlement.transinternallend.bizlogic.TransInternalLendHome;
import com.iss.itreasury.settlement.transinternallend.dataentity.QueryInternalLendConditionInfo;
import com.iss.itreasury.settlement.transinternallend.dataentity.TransInternalLendInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;

public class TransInternalLendDelegation {
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	private TransInternalLend transInternalLendFacade;
	public TransInternalLendDelegation() throws RemoteException
	{
		try
		{
			TransInternalLendHome home = (TransInternalLendHome) EJBHomeFactory.getFactory().lookUpHome(TransInternalLendHome.class);
			transInternalLendFacade = (TransInternalLend) home.create();
		}
		catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (CreateException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("InternalLendDelegation::Constructor 11!!!~");
	}
	/**
	 * 内部拆借业务-暂存
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long transTempSave(TransInternalLendInfo info) throws RemoteException,IRollbackException
	{
		return transInternalLendFacade.transTempSave(info);	
	}
	/**
	 * 内部拆借业务-保存
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long transSave(TransInternalLendInfo info) throws RemoteException,IRollbackException
	{
		return transInternalLendFacade.transSave(info);	
	}
	/**
	 * 业务处理/业务复核链接查找
	 * @param queryInfo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	
	public Collection transFindByStatus(QueryInternalLendConditionInfo queryInfo) throws RemoteException,IRollbackException
	{
		return transInternalLendFacade.transFindByStatus(queryInfo);	
	}
	/**
	 * 通过id查询详细信息
	 * @param lID
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransInternalLendInfo findByID(long lID) throws RemoteException,IRollbackException
	{
		return transInternalLendFacade.FindTransInternalLendDetailByID(lID);	
	}
	/**
	 * 删除操作
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long transDelete(TransInternalLendInfo info) throws RemoteException,IRollbackException
	{
		return transInternalLendFacade.transDelete(info);	
	}
	/**
	 * 内部拆借-匹配操作
	 * @param conditonInfo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransInternalLendInfo match(TransInternalLendInfo conditonInfo) throws RemoteException,IRollbackException
	{
		return transInternalLendFacade.match(conditonInfo);	
	}
	/**
	 * 内部拆借-复核操作
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long check(TransInternalLendInfo info) throws RemoteException,IRollbackException
	{
		return transInternalLendFacade.check(info);	
	}
	/**
	 * 内部拆借-取消复核操作
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long cancelCheck(TransInternalLendInfo info) throws RemoteException,IRollbackException
	{
		return transInternalLendFacade.cancelCheck(info);	
	}
	/**
	 * 内部拆借收回-暂存操作
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long transRepaymentTempSave(TransInternalLendInfo info) throws RemoteException,IRollbackException
	{
		return transInternalLendFacade.transRepaymentTempSave(info);	
	}
	/**
	 * 内部拆借收回-保存操作
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long transRepaymentSave(TransInternalLendInfo info) throws RemoteException,IRollbackException
	{
		return transInternalLendFacade.transRepaymentSave(info);	
	}
	/**
	 * 通过id查询详细信息
	 * @param lID
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransInternalLendInfo repaymentFindByID(long lID) throws RemoteException,IRollbackException
	{
		return transInternalLendFacade.FindTransInternalLendRepaymentDetailByID(lID);	
	}
	/**
	 * 内部拆借收回-链接查找
	 * @param queryInfo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection transRepaymentFindByStatus(QueryInternalLendConditionInfo queryInfo) throws RemoteException, IRollbackException
	{
		return transInternalLendFacade.transRepaymentFindByStatus(queryInfo);
	}
	/**
	 * 内部拆借收回-删除
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */	
	public long transRepaymentDelete(TransInternalLendInfo info) throws RemoteException,IRollbackException
	{
		return transInternalLendFacade.transRepaymentDelete(info);	
	}
	/**
	 * 内部拆借收回-匹配
	 * @param conditonInfo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransInternalLendInfo repaymentMatch(TransInternalLendInfo conditonInfo) throws RemoteException, IRollbackException
	{
		return transInternalLendFacade.repaymentMatch(conditonInfo);	
	}
	/**
	 * 内部拆借收回-复核
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long repaymentCheck(TransInternalLendInfo info) throws RemoteException,IRollbackException
	{
		return transInternalLendFacade.repaymentCheck(info);	
	}
	/**
	 * 内部拆借收回-取消复核
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long repaymentCancelCheck(TransInternalLendInfo info) throws RemoteException,IRollbackException
	{
		return transInternalLendFacade.repaymentCancelCheck(info);	
	}
	
	

}
