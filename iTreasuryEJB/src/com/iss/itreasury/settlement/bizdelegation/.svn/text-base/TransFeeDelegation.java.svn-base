package com.iss.itreasury.settlement.bizdelegation;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;

//import com.iss.itreasury.settlement.bankinstruction.dao.BankInstructionDAO;
import com.iss.itreasury.settlement.transfee.bizlogic.TransFee;
import com.iss.itreasury.settlement.transfee.bizlogic.TransFeeHome;
import com.iss.itreasury.settlement.transfee.dataentity.TransFeeInfo;
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
public class TransFeeDelegation
{
	private TransFee transFeeFacade = null;

	/**
	 * Constructor for TransGeneralLedgerDelegation.
	 */
	public TransFeeDelegation() throws RemoteException, IRollbackException
	{
		try
		{

			TransFeeHome home =
				(TransFeeHome) EJBHomeFactory.getFactory().lookUpHome(TransFeeHome.class);
			transFeeFacade = (TransFee) home.create();
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
	public long preSave(TransFeeInfo info) throws RemoteException, IRollbackException
	{
		return transFeeFacade.preSave(info);

	}

	/**
	 * Method save.
	 * @param info
	 * @return long
	 * @throws IException
	 */
	public long save(TransFeeInfo info) throws RemoteException, IRollbackException
	{
		return transFeeFacade.save(info);

	}

	public long tempSave(TransFeeInfo info) throws RemoteException, IRollbackException
	{
		return transFeeFacade.tempSave(info);

	}

	/**
	 * Method delete.
	 * @param info
	 * @return long 删除的记录ID
	 * @throws IException
	 */
	public long delete(TransFeeInfo info) throws RemoteException, IRollbackException
	{
		return transFeeFacade.delete(info);

	}

	/**
	 * Method delete.
	 * @param info
	 * @return long 复核的记录ID
	 * @throws IException
	 */
	public long check(TransFeeInfo info) throws RemoteException, IRollbackException
	{
		long res = transFeeFacade.check(info);
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
	public long cancelCheck(TransFeeInfo info) throws RemoteException, IRollbackException
	{
		return transFeeFacade.cancelCheck(info);

	}

	/**
	 * Method delete.
	 * @param1 info  
	 * @param2 isMatch 是否是匹配查找
	 * @return long 根据条件查找
	 * @throws IException
	 */
	public Collection findByConditions(TransFeeInfo info, int orderByType, boolean isDesc)
		throws RemoteException, IRollbackException
	{
		return transFeeFacade.findByConditions(info, orderByType, isDesc);

	}

	/**
	 * 方法说明：根据查询条件匹配
	 *  Method  match.
	 * @param Sett_TransCurrentDepositInfo info
	 * @return Sett_TransCurrentDepositInfo
	 */
	public TransFeeInfo match(TransFeeInfo info)
		throws RemoteException, IRollbackException
	{

		return transFeeFacade.match(info);

	}

	public TransFeeInfo findByID(long lID)
		throws RemoteException, IRollbackException
	{
		return transFeeFacade.findByID(lID);

	}

}
