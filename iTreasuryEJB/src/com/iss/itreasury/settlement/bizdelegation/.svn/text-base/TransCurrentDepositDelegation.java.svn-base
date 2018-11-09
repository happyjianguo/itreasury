package com.iss.itreasury.settlement.bizdelegation;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Vector;

import javax.ejb.CreateException;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
//import com.iss.itreasury.settlement.bankinstruction.dao.BankInstructionDAO;
import com.iss.itreasury.settlement.transcurrentdeposit.bizlogic.TransCurrentDeposit;
import com.iss.itreasury.settlement.transcurrentdeposit.bizlogic.TransCurrentDepositHome;
import com.iss.itreasury.settlement.transcurrentdeposit.dao.Sett_TransCurrentDepositDAO;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositAssembler;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransOnePayMultiReceiveInfo;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2003-9-8
 */

public class TransCurrentDepositDelegation
{
	private TransCurrentDeposit transCurrentDepositFacade;

	public TransCurrentDepositDelegation() throws RemoteException, IRollbackException
	{
		try
		{

			TransCurrentDepositHome home =
				(TransCurrentDepositHome) EJBHomeFactory.getFactory().lookUpHome(TransCurrentDepositHome.class);
			transCurrentDepositFacade = (TransCurrentDeposit) home.create();
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
	public long preSave(TransCurrentDepositAssembler info) throws RemoteException, IRollbackException
	{
	    return transCurrentDepositFacade.preSave(info);

	}

	public long preSave(TransOnePayMultiReceiveInfo info) throws RemoteException, IRollbackException
	{
		return transCurrentDepositFacade.preSave(info);

	}

	/**
	 * Method save.
	 * @param info
	 * @return long
	 * @throws IException
	 */
	public long save(TransCurrentDepositAssembler info) throws RemoteException, IRollbackException
	{
		return transCurrentDepositFacade.save(info);

	}

	public long save(TransOnePayMultiReceiveInfo info) throws RemoteException, IRollbackException
	{
		return transCurrentDepositFacade.save(info);

	}	
	
	public void saveDebitInterest(Vector currentVec) throws RemoteException, IRollbackException
	{
		transCurrentDepositFacade.saveDebitInterest(currentVec);
	}
	
	public long tempSave(TransCurrentDepositAssembler info) throws RemoteException, IRollbackException
	{
		return transCurrentDepositFacade.tempSave(info);

	}

	public long tempSave(TransOnePayMultiReceiveInfo info) throws RemoteException, IRollbackException
	{
		return transCurrentDepositFacade.tempSave(info);

	}
	/**
	 * Method delete.
	 * @param info
	 * @return long 删除的记录ID
	 * @throws IException
	 */
	public long delete(TransCurrentDepositAssembler info) throws RemoteException, IRollbackException
	{
		return transCurrentDepositFacade.delete(info);

	}

	public long delete(TransOnePayMultiReceiveInfo info) throws RemoteException, IRollbackException
	{
		return transCurrentDepositFacade.delete(info);

	}

	/**
	 * Method delete.
	 * @param info
	 * @return long 复核的记录ID
	 * @throws IException
	 */
	public long check(TransCurrentDepositAssembler info) throws RemoteException, IRollbackException
	{
		long res = transCurrentDepositFacade.check(info);
////		发送银行指令
//		BankInstructionDAO bankInstructDAO = new BankInstructionDAO();
//		try
//		{
//			bankInstructDAO.sendBankInstruction(info.getSett_TransCurrentDepositInfo().getTransNo());
//		}
//		catch (IException e)
//		{
//			Log.print("----------------------------发送银行指令出现异常----------------");
//			throw new IRollbackException(null,e.getMessage(),e);
//		}
		return res;
	}

	public long check(TransOnePayMultiReceiveInfo info) throws RemoteException, IRollbackException
	{
		long res = transCurrentDepositFacade.check(info);
////		发送银行指令
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
	public long cancelCheck(TransCurrentDepositAssembler info) throws RemoteException, IRollbackException
	{
		return transCurrentDepositFacade.cancelCheck(info);

	}

	public long cancelCheck(TransOnePayMultiReceiveInfo info) throws RemoteException, IRollbackException
	{
		return transCurrentDepositFacade.cancelCheck(info);

	}

	/**
	 * Method delete.
	 * @param1 info  
	 * @param2 isMatch 是否是匹配查找
	 * @return long 根据条件查找
	 * @throws IException
	 */
	public Collection findByConditions(TransCurrentDepositAssembler info, int orderByType, boolean isDesc)
		throws RemoteException, IRollbackException
	{
		return transCurrentDepositFacade.findByConditions(info, orderByType, isDesc);

	}

	public Collection findByConditions(TransOnePayMultiReceiveInfo info, int orderByType, boolean isDesc)
		throws RemoteException, IRollbackException
	{
		return transCurrentDepositFacade.findByConditions(info, orderByType, isDesc);

	}

	public Collection findByConditionsForSquareUp(TransOnePayMultiReceiveInfo info, int orderByType, boolean isDesc)
		throws RemoteException, IRollbackException
	{
		return transCurrentDepositFacade.findByConditionsForSquareUp(info, orderByType, isDesc);

	}

	/**
	 * 方法说明：根据查询条件匹配
	 *  Method  match.
	 * @param Sett_TransCurrentDepositInfo info
	 * @return Sett_TransCurrentDepositInfo
	 */
	public TransCurrentDepositInfo match(long transactionType, TransCurrentDepositInfo info)
		throws RemoteException, IRollbackException
	{

		return transCurrentDepositFacade.match(transactionType, info);

	}

	public TransOnePayMultiReceiveInfo match(TransOnePayMultiReceiveInfo info)
		throws RemoteException, IRollbackException
	{

		return transCurrentDepositFacade.match(info);

	}

	public TransCurrentDepositInfo findBySett_TransCurrentDepositID(long transCurrentDepositID)
		throws RemoteException, IRollbackException
	{
		return transCurrentDepositFacade.findBySett_TransCurrentDepositID(transCurrentDepositID);

	}

	public TransOnePayMultiReceiveInfo findBySett_TransOnePayMultiReceiveID(long lID)
		throws RemoteException, IRollbackException
	{
		return transCurrentDepositFacade.findBySett_TransOnePayMultiReceiveID(lID);

	}

	public boolean squareUp(TransOnePayMultiReceiveInfo[] infos) throws RemoteException, IRollbackException
	{
		boolean reb = transCurrentDepositFacade.squareUp(infos);
////		发送银行指令
//		BankInstructionDAO bankInstructDAO = new BankInstructionDAO();
//		
//		try
//		{	
//			Log.print("=============准备发送银行指令=======");
//			Sett_TransOnePayMultiReceiveDAO currentDao = new Sett_TransOnePayMultiReceiveDAO();
//			TransOnePayMultiReceiveInfo currentInfo = currentDao.findByID(infos[0].getId());
//			if (currentInfo!=null)
//			{
//				Log.print("================currentInfo.getTransNo()="+ currentInfo.getTransNo() + "================");
//				bankInstructDAO.sendBankInstruction(currentInfo.getTransNo());
//			}
//		}
//		catch (Exception e)
//		{
//			System.out.println("----------------------------发送银行指令出现异常----------------");
//			e.printStackTrace();
//			throw new IRollbackException(null,e.getMessage(),e);
//		}
		return reb;
	}

	public boolean cancelSquareUp(TransOnePayMultiReceiveInfo[] infos) throws RemoteException, IRollbackException
	{
		return transCurrentDepositFacade.cancelSquareUp(infos);

	}
	
	/**
	 * 自动保存和复核
	 * */
	public TransCurrentDepositAssembler saveAndCheckAutomatically(TransCurrentDepositAssembler info) throws RemoteException, IRollbackException{
		TransCurrentDepositAssembler assemble = transCurrentDepositFacade.saveAndCheckAutomatically(info);
		return assemble;
	}
	
	
	/**
	 * 自动保存和复核(网银)
	 * */
	public TransCurrentDepositAssembler saveAndCheckAutomaticallyforEbank(TransCurrentDepositAssembler info) throws RemoteException, IRollbackException{
		TransCurrentDepositAssembler assemble = transCurrentDepositFacade.saveAndCheckAutomaticallyforEbank(info);
		return assemble;
	}
	
	/**
	 * 自动保存和复核
	 * 是 saveAndCheckAutomatically 的改进，CheckUserID 就是 InputUserID
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransCurrentDepositAssembler saveAndCheckNew(TransCurrentDepositAssembler info) throws RemoteException, IRollbackException{
		TransCurrentDepositAssembler assemble = transCurrentDepositFacade.saveAndCheckNew(info);
		return assemble;
	}

	/**
	 *取消复核与删除交易
	 * */	
	public void cancelAndCheckAutomatically(TransCurrentDepositAssembler info) throws RemoteException, IRollbackException{
		transCurrentDepositFacade.cancelSaveAndCheckAutomatically(info);
	}

	/**
	 * 网银指令接受自动保存复核
	 */
	public void saveAndCheckAutomatically(FinanceInfo info,long userID) throws RemoteException, IRollbackException, IException
	{
		String sTransNo = transCurrentDepositFacade.saveAndCheckAutomatically(info,userID);

//		try
//		{
//			//发送银行指令
//			BankInstructionDAO bankInstructDAO = new BankInstructionDAO();
//			bankInstructDAO.sendBankInstruction(sTransNo);
//		}
//		catch (IException e)
//		{
//			Log.print("----------------------------发送银行指令出现异常----------------");
//			throw new IException("发送银行指令出现异常",e);
//		}
	}
	
	public TransCurrentDepositInfo findByTransNo(String strTransNo) throws IException
	{
		Sett_TransCurrentDepositDAO currentDao = new Sett_TransCurrentDepositDAO();
		try
		{
			return currentDao.findByTransNo(strTransNo);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("",e);
		}
	}
	
	/**
	 * 方法说明：提交审批方法
	 *  Method  initApproval.
	 * @param TransCurrentDepositAssembler info
	 * @return long transid
	 */
	public long initApproval(TransCurrentDepositAssembler info)
		throws RemoteException, IRollbackException
	{
		return transCurrentDepositFacade.initApproval( info);
	}
	
	/**
	 * 方法说明：审批方法
	 *  Method  doApproval.
	 * @param TransCurrentDepositAssembler info
	 * @return long transid
	 */
	public long doApproval ( TransCurrentDepositAssembler info)
		throws RemoteException, IRollbackException
	{
		return transCurrentDepositFacade.doApproval(info);
	}
	
	/**
	 * 方法说明：取消审批方法
	 *  Method  cancelApproval.
	 * @param TransCurrentDepositAssembler info
	 * @return long transid
	 */
	public long cancelApproval ( TransCurrentDepositAssembler info)
		throws RemoteException, IRollbackException
	{
		return transCurrentDepositFacade.cancelApproval(info);
	}
	/**
	 * @throws IRollbackException 
	 * @throws RemoteException 
	 * @throws ITreasuryDAOException 
	 * 
	 */
	public long saveAndCheck(TransOnePayMultiReceiveInfo[] TransOnePayMultiReceiveInfos,long batchEntity) throws RemoteException, IRollbackException{
		return transCurrentDepositFacade.saveAndCheck(TransOnePayMultiReceiveInfos,batchEntity);
	}
}



