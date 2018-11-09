/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transloan.bizlogic;
import java.rmi.RemoteException;
import java.util.Collection;

import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedOpenInfo;
import com.iss.itreasury.settlement.transloan.dataentity.GrantConsignLoanConditionInfo;
import com.iss.itreasury.settlement.transloan.dataentity.GrantTrustLoanConditionInfo;
import com.iss.itreasury.settlement.transloan.dataentity.LoanPayFormDetailInfo;
import com.iss.itreasury.settlement.transloan.dataentity.RepaymentConsignLoanConditionInfo;
import com.iss.itreasury.settlement.transloan.dataentity.RepaymentInterestConditionInfo;
import com.iss.itreasury.settlement.transloan.dataentity.RepaymentTrustLoanConditionInfo;
import com.iss.itreasury.settlement.transloan.dataentity.SubLoanAccountDetailInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo;
import com.iss.itreasury.util.IRollbackException;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface TransLoan extends javax.ejb.EJBObject
{
	/******************************************************************
	 * ******************************贷款发放开始***************************
	 * ****************************************************************
	 * */
	/**
	 * 保存前的操作
	 * @param info
	 * @return 
	 * @throws RemoteException
	 */
	public long preSave(TransGrantLoanInfo info) throws RemoteException, IRollbackException;
	/**
	 * 信托贷款发放暂存操作
	 * @param info TransGrantLoanInfo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long tempSave(TransGrantLoanInfo info) throws RemoteException, IRollbackException;
	/**
	 * 保存
	 * @param info
	 * @param isTrustLoan
	 * @return TransGrantLoanInfo id
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long save(TransGrantLoanInfo info) throws RemoteException, IRollbackException;
	
	/**
	 * 审批流：审批通过， Added by zwsun ,2007-06-20
	 */
	public long doApproval(TransGrantLoanInfo info)throws RemoteException, IRollbackException;
	/**
	 * 审批拒绝
	 */
	public long cancelApproval(TransGrantLoanInfo info)throws RemoteException, IRollbackException;
	/**
	* 根据id查找信托贷款发放信息
	* @param id 信托贷款发放信息id
	* @return TrustLoanInfoAssembler
	* @throws RemoteException
	* @throws IRollbackException
	*/
	public TransGrantLoanInfo FindGrantDetailByID(long id) throws RemoteException, IRollbackException;
	
	/*
	 * 根据放款通知单ID查找信托贷款发放信息
	 * 
	 * @param id 信托贷款发放信息的放款通知单ID
	 * @return TransGrantLoanInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransGrantLoanInfo FindGrantDetailByLoanNoteID(long lLoanNoteID) throws RemoteException, IRollbackException;
	
	/**
		 * 链接查询信托贷款发放信息集合
		 * @param info TrustLoanInfo
		 * @param orderType
		 * @param isDesc
		 * @return 信托贷款发放信息集合
		 * @throws RemoteException
		 * @throws IRollbackException
		 */
	public Collection findByCondition(TransGrantLoanInfo info) throws RemoteException, IRollbackException;
	/**
	 * 匹配查询信托贷款发放信息
	 * @param info
	 * @return 信托贷款发放信息
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransGrantLoanInfo match(GrantTrustLoanConditionInfo info) throws RemoteException, IRollbackException;
	/**
		 * 匹配查询信托贷款发放信息
		 * @param info
		 * @return 信托贷款发放信息
		 * @throws RemoteException
		 * @throws IRollbackException
		 */
	public TransGrantLoanInfo match(GrantConsignLoanConditionInfo info) throws RemoteException, IRollbackException;
	/**
	 * 得到贷款条件信息
	 * @param info LoanConditionInfo
	 * @return LoanConditionInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public LoanPayFormDetailInfo findLoanPayFormDetailByCondition(LoanPayFormDetailInfo info) throws RemoteException, IRollbackException;
	/**
	 * 删除贷款记录 
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long delete(TransGrantLoanInfo info) throws RemoteException, IRollbackException;
	/**
	 * 复核或者取消复核
	 * @param info
	 * @param checkOrCancel
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long check(TransGrantLoanInfo info, boolean checkOrCancel) throws RemoteException, IRollbackException;
	/******************************************************************
		 * ******************************贷款发放结束***************************
		 * ****************************************************************
		 * */
	/******************************************************************
		 * ******************************贷款收回开始***************************
		 * ****************************************************************
		 * */
	/**
		 * 保存前的操作
		 * @param info
		 * @return 
		 * @throws RemoteException
		 */
	public long preSave(TransRepaymentLoanInfo info) throws RemoteException, IRollbackException;
	/**
	 * 信托贷款收回暂存操作
	 * @param info TransRepaymentLoanInfo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long tempSave(TransRepaymentLoanInfo info) throws RemoteException, IRollbackException;
	/**
	 * 保存
	 * @param info 
	 * @return TransRepaymentLoanInfo id
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long save(TransRepaymentLoanInfo info) throws RemoteException, IRollbackException;
	/**
	* 根据id查找信托贷款收回信息
	* @param id 信托贷款收回信息id
	* @return TransRepaymentLoanInfo
	* @throws RemoteException
	* @throws IRollbackException
	*/
	public TransRepaymentLoanInfo FindRepaymentDetailByID(long id) throws RemoteException, IRollbackException;
	/**
		 * 链接查询信托贷款收回信息集合
		 * @param info TrustLoanInfo
		 * @param orderType
		 * @param isDesc
		 * @return 信托贷款发放信息集合
		 * @throws RemoteException
		 * @throws IRollbackException
		 */
	public Collection findByCondition(TransRepaymentLoanInfo info) throws RemoteException, IRollbackException;
	/**
	 * 匹配查询信托贷款收回信息
	 * @param info
	 * @return 信托贷款收回信息
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransRepaymentLoanInfo match(RepaymentTrustLoanConditionInfo info) throws RemoteException, IRollbackException;
	/**
		 * 匹配查询信托贷款收回信息
		 * @param info
		 * @return 信托贷款收回信息
		 * @throws RemoteException
		 * @throws IRollbackException
		 */
	public TransRepaymentLoanInfo match(RepaymentConsignLoanConditionInfo info) throws RemoteException, IRollbackException;
	/**
	 * 匹配查询信托贷款收回信息
	 * @param info
	 * @return 信托贷款收回信息
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransRepaymentLoanInfo match(RepaymentInterestConditionInfo info) throws RemoteException, IRollbackException;
	/**
	 * 得到贷款条件信息
	 * @param info SubLoanAccountDetailInfo
	 * @return SubLoanAccountDetailInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public SubLoanAccountDetailInfo findSubLoanAccountDetailByCondition(SubLoanAccountDetailInfo info) throws RemoteException, IRollbackException;
	/**
	 * 删除贷款记录 
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long delete(TransRepaymentLoanInfo info) throws RemoteException, IRollbackException;
	/**
	 * 复核或者取消复核
	 * @param info
	 * @param checkOrCancel
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long check(TransRepaymentLoanInfo info, boolean checkOrCancel) throws RemoteException, IRollbackException;
	/**
	 * 多笔贷款收回勾账
	 * @param infos
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public boolean squareUp(TransRepaymentLoanInfo[] infos) throws RemoteException, IRollbackException;
	/**
	 * 多笔贷款收回取消勾账
	 * @param infos
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public boolean cancelSquareUp(TransRepaymentLoanInfo[] infos) throws RemoteException, IRollbackException;
	
	/**
	 * 多笔贷款收回勾账查询
	 * @param condiInfo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection findSquareUpRecordsByCondition(TransRepaymentLoanInfo condiInfo) throws RemoteException, IRollbackException;
	/******************************************************************
			 * ******************************贷款收回结束***************************
			 * ****************************************************************
			 * */
	public long grantGetIDByTransNo(String strTransNo) throws RemoteException, IRollbackException;
	
	public long repaymentGetIDByTransNo(String strTransNo) throws RemoteException, IRollbackException;
	
	public long repaymentGetIDByTransNoAndSerialNo(String strTransNo,long lSerialNo) throws RemoteException, IRollbackException;
	
	/**
	 * 根据交易号查询出多笔贷款收回还款结果集
	 * @param strTransNo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection getRepaymentCollectionByTransNo(String strTransNo)throws RemoteException,IRollbackException;

    /*
	 * 根据放款通知单ID查找信托贷款收回信息
	 * 
	 * @param id 信托贷款发放信息的放款通知单ID
	 * @return TransGrantLoanInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection grantFindInterestByLoanNoteID(long lLoanNoteID,long nOfficeID,long nCurrencyID,long lLoanAccountID,long lContractID,long lSubAccountID) throws RemoteException, IRollbackException;
	
	  /*
	 * 根据放款通知单ID和合同ID查找信托贷款收回信息
	 * 
	 * @param id 信托贷款发放信息的放款通知单ID
	 * @return TransGrantLoanInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransRepaymentLoanInfo grantFindInterestByCondition(SubLoanAccountDetailInfo info) throws RemoteException, IRollbackException;
}
