package com.iss.itreasury.loan.freeapply.bizlogic;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.EJBObject;

import com.iss.itreasury.loan.freeapply.dataentity.FreeApplyInfo;
import com.iss.itreasury.loan.freeapply.dataentity.FreeApplyQueryInfo;
import com.iss.itreasury.loan.loanapply.dataentity.LoanExaminePassInfo;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;

/**
 * Created 2003-8-15 14:50:59
 * Code generated by the Forte for Java EJB Module
 * @author yfan
 */

public interface FreeApply extends EJBObject {

	/**
	 * 保存免还申请，操作Loan_FreeForm 表
	 * @param lFreeApplyID 免还申请标识 ，如果<=0新增，否则修改
	 * @param lPayFormID 放款单号
	 * @param dLoanAmount 贷款金额
	 * @param lIntervalNum 年期
	 * @param dLoanBalance 贷款余额
	 * @param tsEnd 到期日
	 * @param dFreeAmount 免还金额
     * @param strConsignAccoutn,委托账户
	 * @param tsFreeDate 免还日期
	 * @param strFreeReason 免还原因
	 * @param dInterest 免还利息
     * @param lInputUserID   录入人
     * @param tsInputDate    录入时间
	 * /
	public long saveFreeApply(
                        int nTypeID,
						long lFreeApplyID,
						long lPayFormID,
						double dLoanAmount,
						long lIntervalNum,
						double dLoanBalance,
						Timestamp tsEnd,
						double dFreeAmount,
						String sConsignAccount,
                        Timestamp tsFreeDate,
						String strFreeReason,
						double dInterest,
                        long lInputUserID,
                        Timestamp tsInputDate
						) throws IException,RemoteException;

  
	/**
	 * 查询免还，操作Loan_FreeForm 表
   	 * @param ID 免还申请标示
	 */
	public FreeApplyInfo findFreeApplyByID(long ID) throws IException,RemoteException;
					
  
	/**
	 * 查询免还，操作Loan_FreeForm 表
	 * @param lOfficeID 办事处标识
	 * @param lUserID 用户标识
	 * @param lActionID 动作类型，提交链结查找，或者复核链接查找
	 * @param lContractIDFrom 借款合同编号（由）
	 * @param lContractIDTo 借款合同编号（到）
	 * @param lClientID 借款单位标识
	 * @param dAmountFrom 金额（由）
	 * @param dAmountTo 金额（到）
	 * @param tsFrom 贷款期限（由）
	 * @param tsTo 贷款期限（到）
	 * @param lIntervalNum 期限（月）
	 * @param lStatusID 状态
     * 
	 * @param lPageLineCount 行数
	 * @param lPageNo 页码
	 * @param lOrderParam  排序列
     * @param lDesc 顺序
	 * /
	public Collection findFreeApplyByMultiOption(
                        int nType,
						long lOfficeID,
						long lUserID,
                        long lActionID,
						long lContractIDFrom,
						long lContractIDTo,
						long lClientID,
						double dAmountFrom,
						double dAmountTo,
						Timestamp tsFrom,
						Timestamp tsTo,
						long lIntervalNum,
						long lStatusID,
                        long lPageLineCount,
                        long lPageNo, 
                        long lOrderParam,
                        long lDesc) throws IException,RemoteException;
					
   /**
	  * 审核免还申请，操作Loan_FreeForm表
	  * @param     long        nReviewContentID      审批内容类型
	  * @param     String      sOpinion              审批意见
	  * @param     long        nUserID               审批人标示
	  * @param     long        nNextUserID           下一个审批人标示
	  * @param     long        lAction               审批，拒绝，修改，最后审批
	  * @return    long        成功，返回值 == 1，失败，返回值 == -1
	  */
  public long checkFreeApply(
	  	long lApprovalContentID,
		String sOpinion,
		long lUserID,
		long lNextUserID,
		long lAction,
		long lCurrencyID,
		long lOfficeID)
	  throws IException,RemoteException;
	//add by 何小文 2007-06-29 审批通过方法 
  public long examinePass(FreeApplyInfo freeinfo)
	throws RemoteException, IRollbackException;
  
	/**
	 * 取消一个免还，操作表Loan_FreeForm 
	 * <br>更改状态为无效
	 * @param lFreeApplyID 免还标识
	 */
	public long cancelFreeApply(long lFreeApplyID,long lCurrencyID,long lOfficeID) throws IException,RemoteException;



	/**
	 * 保存免还申请，操作Loan_FreeForm 表
	 * @param lFreeApplyID 免还申请标识 ，如果<=0新增，否则修改
	 * @param lPayFormID 合同标识//现在是放款单标识
	 * @param dLoanAmount 贷款金额
	 * @param lIntervalNum 年期
	 * @param dLoanBalance 贷款余额
	 * @param tsEnd 到期日
	 * @param dFreeAmount 免还金额
	 * @param strConsignAccoutn,委托账户
	 * @param tsFreeDate 免还日期
	 * @param strFreeReason 免还原因
	 * @param dInterest 免还利息
	 * @param lInputUserID   录入人
	 * @param tsInputDate    录入时间
	 */
	  public long saveFreeApply(FreeApplyInfo info) throws IException, RemoteException;

	  public long saveFreeApplyInit(FreeApplyInfo info) throws IException, RemoteException;
	/**
	 * 查询免还，操作Loan_FreeForm 表
	 * @param lOfficeID 办事处标识
	 * @param lUserID 用户标识
	 * @param lActionID 动作类型，提交链结查找，或者复核链接查找
	 * @param lContractIDFrom 借款合同编号（由）
	 * @param lContractIDTo 借款合同编号（到）
	 * @param lClientID 借款单位标识
	 * @param dAmountFrom 金额（由）
	 * @param dAmountTo 金额（到）
	 * @param tsFrom 贷款期限（由）
	 * @param tsTo 贷款期限（到）
	 * @param lIntervalNum 期限（月）
	 * @param lStatusID 状态
	 * 
	 * @param lPageLineCount 行数
	 * @param lPageNo 页码
	 * @param lOrderParam  排序列
	 * @param lDesc 顺序
	 */
	public Collection findFreeApplyByMultiOption(FreeApplyQueryInfo qInfo)	throws IException,RemoteException;
	
	public long cancelApproval(FreeApplyInfo freeinfo)throws RemoteException, IRollbackException;
	
	/**
	 * @param payID 放款通知单ID
	 * @param rePayID 还款通知单ID
	 * @return 查询未还款金额
	 * @throws RemoteException
	 * @throws IException
	 */
	public double findUnRePayAmountByID(long payID,long rePayID) throws RemoteException, IException;
}
