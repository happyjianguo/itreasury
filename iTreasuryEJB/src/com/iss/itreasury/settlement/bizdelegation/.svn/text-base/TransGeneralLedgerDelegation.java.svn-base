package com.iss.itreasury.settlement.bizdelegation;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;

import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedDrawInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedOpenInfo;
import com.iss.itreasury.settlement.transgeneralledger.bizlogic.TransGeneralLedger;
import com.iss.itreasury.settlement.transgeneralledger.bizlogic.TransGeneralLedgerHome;
import com.iss.itreasury.settlement.transgeneralledger.dataentity.TransGeneralLedgerInfo;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;

/**
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class TransGeneralLedgerDelegation
{
	private TransGeneralLedger transGeneralLedgerFacade = null;

	/**
	 * Constructor for TransGeneralLedgerDelegation.
	 */
	public TransGeneralLedgerDelegation() throws RemoteException, IRollbackException
	{
		try
		{

			TransGeneralLedgerHome home =
				(TransGeneralLedgerHome) EJBHomeFactory.getFactory().lookUpHome(TransGeneralLedgerHome.class);
			transGeneralLedgerFacade = (TransGeneralLedger) home.create();
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
	 * @param info
	 * @return int 0: 正常 1: 委付凭证不可用--提示输入 2: 票据不可用 3: 重复交易记录 4: 账户透支
	 * @throws IException
	 */
	public long preSave(TransGeneralLedgerInfo info) throws RemoteException, IRollbackException
	{
		return transGeneralLedgerFacade.preSave(info);

	}

	/**
	 * Method save.
	 * @param info
	 * @return long
	 * @throws IException
	 */
	public long save(TransGeneralLedgerInfo info) throws RemoteException, IRollbackException
	{
		return transGeneralLedgerFacade.save(info);

	}

	public long tempSave(TransGeneralLedgerInfo info) throws RemoteException, IRollbackException
	{
		return transGeneralLedgerFacade.tempSave(info);

	}

	/**
	 * Method delete.
	 * @param info
	 * @return long 删除的记录ID
	 * @throws IException
	 */
	public long delete(TransGeneralLedgerInfo info) throws RemoteException, IRollbackException
	{
		return transGeneralLedgerFacade.delete(info);

	}

	/**
	 * Method delete.
	 * @param info
	 * @return long 复核的记录ID
	 * @throws IException
	 */
	public long check(TransGeneralLedgerInfo info) throws RemoteException, IRollbackException
	{
		long res = transGeneralLedgerFacade.check(info);
//		//发送银行指令
//		BankInstructionDAO bankInstructDAO = new BankInstructionDAO();
//		try
//		{
//			bankInstructDAO.sendBankInstruction(info.getTransNo());
//		}
//		catch (IException e)
//		{
//			Log.print("----------------------------发送银行指令出现异常----------------");
//			throw new IRollbackException(null,e.getMessage(),e);
//		}
		return res;

	}

	/**
	 * Method delete.
	 * @param info
	 * @return long 取消复核的记录ID
	 * @throws IException
	 */
	public long cancelCheck(TransGeneralLedgerInfo info) throws RemoteException, IRollbackException
	{
		return transGeneralLedgerFacade.cancelCheck(info);

	}

	/**
	 * Method delete.
	 * @param1 info  
	 * @param2 isMatch 是否是匹配查找
	 * @return long 根据条件查找
	 * @throws IException
	 */
	public Collection findByConditions(TransGeneralLedgerInfo info, int orderByType, boolean isDesc)
		throws RemoteException, IRollbackException
	{
		return transGeneralLedgerFacade.findByConditions(info, orderByType, isDesc);

	}

	/**
	 * 方法说明：根据查询条件匹配
	 *  Method  match.
	 * @param Sett_TransCurrentDepositInfo info
	 * @return Sett_TransCurrentDepositInfo
	 */
	public TransGeneralLedgerInfo match(TransGeneralLedgerInfo info)
		throws RemoteException, IRollbackException
	{

		return transGeneralLedgerFacade.match(info);

	}

	public TransGeneralLedgerInfo findByID(long lID)
		throws RemoteException, IRollbackException
	{
		return transGeneralLedgerFacade.findByID(lID);

	}
	/**
	 * 方法说明：审批方法(总账)
	 *  Method  doApproval.
	 * @param TransFixedOpenInfo info
	 * @return long transid
	 */
	public long doApproval ( TransGeneralLedgerInfo info)
		throws RemoteException, IRollbackException
	{
		return transGeneralLedgerFacade.doApproval(info);
	}
	/**
	 * 方法说明：取消审批方法(总账)
	 *  Method  doApproval.
	 * @param TransFixedOpenInfo info
	 * @return long transid
	 */
	public long cancelApproval ( TransGeneralLedgerInfo info)
		throws RemoteException, IRollbackException
	{
		return transGeneralLedgerFacade.cancelApproval(info);
	}

}
