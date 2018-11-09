package com.iss.itreasury.settlement.bizdelegation;

import java.rmi.RemoteException;
import java.util.*;

import javax.ejb.CreateException;

//import com.iss.itreasury.settlement.bankinstruction.dao.BankInstructionDAO;
import com.iss.itreasury.settlement.transdiscount.bizlogic.*;
import com.iss.itreasury.settlement.transdiscount.dataentity.*;
import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.discount.dataentity.DiscountCredenceInfo;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2003-9-8
 */

public class TransDiscountDelegation
{
	//	private com.iss.itreasury.settlement.transdiscount.bizlogic.TransDiscountEJB ejb;
	private TransDiscount transDiscountFacade;

	public TransDiscountDelegation() throws RemoteException, IRollbackException
	{
		try
		{
			TransDiscountHome home = (TransDiscountHome) EJBHomeFactory.getFactory().lookUpHome(TransDiscountHome.class);
			transDiscountFacade = home.create();
		}
		catch (Exception e)
		{
			throw new RemoteException("CreateException in TransDiscountDelegation", e);
		}
	}
	/**
	 * 审批流：Added by zwsun, 2007-6-20
	 * 方法说明：审批方法
	 *  Method  doApproval. 
	 * @param TransFixedOpenInfo info
	 * @return long transid
	 */
	public long doApproval ( TransGrantDiscountInfo info)
		throws RemoteException, IRollbackException
	{
		return this.transDiscountFacade.doApproval(info);
	}
	/**
	 * 审批流：Added by zwsun, 2007-6-20
	 * 方法说明：审批拒绝
	 *  Method  doApproval. 
	 * @param TransFixedOpenInfo info
	 * @return long transid
	 */
	public long cancelApproval ( TransGrantDiscountInfo info)
		throws RemoteException, IRollbackException
	{
		return this.transDiscountFacade.cancelApproval(info);
	}	
	/**
	* Method preSave.
	* @param info
	* @return int 0: 正常 1: 委付凭证不可用--提示输入 2: 票据不可用 3: 重复交易记录 4: 账户透支
	* @throws RemoteException
	*/
	public long grantPreSave(TransGrantDiscountInfo info) throws RemoteException, IRollbackException
	{
		return transDiscountFacade.grantPreSave(info);
	}
   
	/**  票据修改 and by qulaian
	* Method updateDiscountBillSave.
	* @param info  
	* @return int 
	* @throws RemoteException
	*/
	public long updateDiscountBillSave(long discount) throws RemoteException, IRollbackException
	{
		return transDiscountFacade.updateDiscountBillSave(discount);
	}
	
	/**
	 * Method tempSave.
	 * @param info
	 * @return long
	 * @throws RemoteException
	 */
	public long grantTempSave(TransGrantDiscountInfo info) throws RemoteException, IRollbackException
	{
		return transDiscountFacade.grantTempSave(info);
	}
	public long grantTempSave(TransDiscountDetailInfo info) throws RemoteException, IRollbackException
	{
		return transDiscountFacade.grantTempSave(info);
	}
	public long grantSave(TransDiscountDetailInfo info) throws RemoteException, IRollbackException
	{
		return transDiscountFacade.grantSave(info);
	}
	public long grantSubjectSave(TransDiscountSubjectInfo info) throws RemoteException, IRollbackException
	{
		return transDiscountFacade.grantSubjectSave(info);
	}
	public long grantModifyTempSave(TransGrantDiscountInfo info) throws RemoteException, IRollbackException
	{
		return transDiscountFacade.grantTempSave(info);
	}
	/**
	 * 转贴现链接查找
	 * @param info
	 * @param orderByType
	 * @param isDesc
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection findByConditions(QueryConditionInfo info) throws RemoteException, IRollbackException
	{
		return transDiscountFacade.findByConditions(info);
	}

	/**
	 * Method save.
	 * @param info
	 * @return long
	 * @throws RemoteException
	 */
	public long grantSave(TransGrantDiscountInfo info) throws RemoteException, IRollbackException
	{
		return transDiscountFacade.grantSave(info);
	}

	/**
	 * Method delete.
	 * @param info
	 * @return long 删除的记录ID
	 * @throws RemoteException
	 */
	public long grantDelete(TransGrantDiscountInfo info) throws RemoteException, IRollbackException
	{
		return transDiscountFacade.grantDelete(info);
	}

	/**
	 * Method delete.
	 * @param info
	 * @return long 复核的记录ID
	 * @throws RemoteException
	 */
	public long grantCheck(TransGrantDiscountInfo info) throws RemoteException, IRollbackException
	{
		long res = transDiscountFacade.grantCheck(info);
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
	 * @throws RemoteException
	 */
	public long grantCancelCheck(TransGrantDiscountInfo info) throws RemoteException, IRollbackException
	{
		return transDiscountFacade.grantCancelCheck(info);
	}

	/**
	 * 链接查找
	 * @param info
	 * @param orderByType
	 * @param isDesc
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection grantFindByConditions(QueryConditionInfo info) throws RemoteException, IRollbackException
	{
		return transDiscountFacade.grantFindByConditions(info);
	}

	/**
	* 方法说明：根据查询条件匹配
	*  Method  match.
	* @param Sett_TransCurrentDepositInfo info
	* @return Sett_TransCurrentDepositInfo
	*/
	public TransGrantDiscountInfo grantMatch(TransGrantDiscountInfo info) throws RemoteException, IRollbackException
	{
		return transDiscountFacade.grantMatch(info);
	}
	/**
	* 方法说明：根据查询条件匹配
	*  Method  match.
	* @param Sett_TransCurrentDepositInfo info
	* @return Sett_TransCurrentDepositInfo
	*/
	public TransDiscountDetailInfo grantDiscountMatch(TransDiscountDetailInfo info) throws RemoteException, IRollbackException
	{
		return transDiscountFacade.grantDiscountMatch(info);
	}
	/**
	 * 方法说明 ：根据查询条件查找科目
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection findSubjectInfo(TransDiscountSubjectInfo info) throws RemoteException, IRollbackException
	{
		return transDiscountFacade.findSubjectInfo(info);
	}
	/**
	 * 方法说明：根据账户ID，得到账户信息
	 * @param transCurrentDepositID
	 * @return Sett_TransCurrentDepositInfo
	 * @throws IException
	 */
	public TransGrantDiscountInfo grantFindDetailByID(long lTransID) throws RemoteException, IRollbackException
	{
		return transDiscountFacade.grantFindDetailByID(lTransID);
	}

	public TransGrantDiscountInfo grantFindGrantDetailByNoteID(long lDiscountNoteID) throws RemoteException, IRollbackException
	{
		return transDiscountFacade.grantFindGrantDetailByNoteID(lDiscountNoteID);
	}
	public TransDiscountDetailInfo findTransDiscountByNoteID(long transDiscountNoteID) throws RemoteException, IRollbackException
	{
		return transDiscountFacade.findTransDiscountByNoteID(transDiscountNoteID);
	}

	public TransGrantDiscountInfo grantFindDiscountDetailByNoteID(long lDiscountNoteID) throws RemoteException, IRollbackException
	{
		return transDiscountFacade.grantFindGrantDetailByNoteID(lDiscountNoteID);
	}
	public TransDiscountDetailInfo findTransDetailByID(long lTransID) throws RemoteException, IRollbackException
	{
		return transDiscountFacade.findTransDetailByID(lTransID);
	}
    
	/**
	 * Method delete.
	 * @param info
	 * @return long 复核的记录ID
	 * @throws RemoteException
	 */
	public long grantCheck(TransDiscountDetailInfo info) throws RemoteException, IRollbackException
	{
		long res = transDiscountFacade.grantCheck(info);
		return res;
	}
	/**
	 * Method delete.
	 * @param info
	 * @return long 删除的记录ID
	 * @throws RemoteException
	 */
	public long grantDelete(TransDiscountDetailInfo info) throws RemoteException, IRollbackException
	{
		return transDiscountFacade.grantDelete(info);
		
	}
	/**
	 * Method delete.
	 * @param info
	 * @return long 取消复核的记录ID
	 * @throws RemoteException
	 */
	public long grantCancelCheck(TransDiscountDetailInfo info) throws RemoteException, IRollbackException
	{
		return transDiscountFacade.grantCancelCheck(info);
	}
	/*******************贴现收回*******************/
	/**
	* Method preSave.
	* @param info
	* @return int 0: 正常 1: 委付凭证不可用--提示输入 2: 票据不可用 3: 重复交易记录 4: 账户透支
	* @throws RemoteException
	*/
	public long repaymentPreSave(TransRepaymentDiscountInfo info) throws RemoteException, IRollbackException
	{
		return transDiscountFacade.repaymentPreSave(info);
	}

	/**
	 * Method tempSave.
	 * @param info
	 * @return long
	 * @throws RemoteException
	 */
	public long repaymentTempSave(TransRepaymentDiscountInfo info) throws RemoteException, IRollbackException
	{
		return transDiscountFacade.repaymentTempSave(info);
	}

	public long repaymentModifyTempSave(TransRepaymentDiscountInfo info) throws RemoteException, IRollbackException
	{
		System.out.println("\n\n 后台 delegation.repaymentModifyTempSave \n\n");
		return transDiscountFacade.repaymentModifyTempSave(info);

	}

	/**
	 * Method save.
	 * @param info
	 * @return long
	 * @throws RemoteException
	 */
	public long repaymentSave(TransRepaymentDiscountInfo info) throws RemoteException, IRollbackException
	{
		return transDiscountFacade.repaymentSave(info);
	}

	/**
	 * Method delete.
	 * @param info
	 * @return long 删除的记录ID
	 * @throws RemoteException
	 */
	public long repaymentDelete(TransRepaymentDiscountInfo info) throws RemoteException, IRollbackException
	{
		return transDiscountFacade.repaymentDelete(info);
	}

	/**
	 * Method delete.
	 * @param info
	 * @return long 复核的记录ID
	 * @throws RemoteException
	 */
	public long repaymentCheck(TransRepaymentDiscountInfo info) throws RemoteException, IRollbackException
	{
		long res = transDiscountFacade.repaymentCheck(info);
////		发送银行指令
//		BankInstructionDAO bankInstructDAO = new BankInstructionDAO();
//		try
//		{
//			bankInstructDAO.sendBankInstruction(info.getSTransNo());
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
	 * @throws RemoteException
	 */
	public long repaymentCancelCheck(TransRepaymentDiscountInfo info) throws RemoteException, IRollbackException
	{
		return transDiscountFacade.repaymentCancelCheck(info);
	}
	/**
	 * 根据交易号查找交易ID
	 * @param strTransNo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long repaymentGetIDByTransNo(String strTransNo) throws RemoteException, IRollbackException
	{
		return transDiscountFacade.repaymentGetIDByTransNo(strTransNo);
	}
	/**
	 * 根据交易号查找交易ID
	 * @param strTransNo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long grantGetIDByTransNo(String strTransNo) throws RemoteException, IRollbackException
	{
		return transDiscountFacade.grantGetIDByTransNo(strTransNo);
	}
	/**
	 * 链接查找
	 * @param info
	 * @param orderByType
	 * @param isDesc
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection repaymentFindByConditions(QueryConditionInfo info) throws RemoteException, IRollbackException
	{
		return transDiscountFacade.repaymentFindByConditions(info);
	}

	/**
	* 方法说明：根据查询条件匹配
	*  Method  match.
	* @param Sett_TransCurrentDepositInfo info
	* @return Sett_TransCurrentDepositInfo
	*/
	public TransRepaymentDiscountInfo repaymentMatch(TransRepaymentDiscountInfo info) throws RemoteException, IRollbackException
	{
		System.out.println("\n\n 进入delegation ,开始调用repaymentMatch \n\n");
		return transDiscountFacade.repaymentMatch(info);
	}

	/**
	 * 方法说明：根据账户ID，得到账户信息
	 * @param transCurrentDepositID
	 * @return Sett_TransCurrentDepositInfo
	 * @throws IException
	 */
	public TransRepaymentDiscountInfo repaymentFindDetailByID(long lTransID) throws RemoteException, IRollbackException
	{
		return transDiscountFacade.repaymentFindDetailByID(lTransID);
	}

	//	由于打印方面的需要,增加一个方法
	public DiscountCredenceInfo findDiscountCredenceByID(long lDiscountCredenceID) throws RemoteException, IRollbackException
	{
System.out.println("\n\n delegation lDiscountCredenceID="+lDiscountCredenceID+" \n\n");
		return transDiscountFacade.findDiscountCredenceByID(lDiscountCredenceID);
	}
	/**  票据修改 add by lkliu
	* Method updateDiscountBillOfCancelCheck.
	* @param info  
	* @return int 
	* @throws RemoteException
	*/
	public long updateDiscountBillOfCancelCheck(long lID) throws RemoteException, IRollbackException
	{
		return transDiscountFacade.updateDiscountBillOfCancelCheck(lID);
	}

}
