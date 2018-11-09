/*
 * Created on 2003-8-7
 * 
 * To change the template for this generated file go to Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.account.bizlogic;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.account.dataentity.ClientInfo;
import com.iss.itreasury.settlement.account.dataentity.ExternalAccountInfo;
import com.iss.itreasury.settlement.account.dataentity.QueryAccountConditionInfo;
import com.iss.itreasury.settlement.account.dataentity.QueryClientConditionInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountAssemblerInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountCurrentInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountFixedInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountLoanInfo;
import com.iss.itreasury.settlement.account.dataentity.TransAccountDetailInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryAccountWhereInfo;
import com.iss.itreasury.settlement.setting.dataentity.BranchInfo;
import com.iss.itreasury.settlement.transfinancialmargin.dataentity.TransFinancialMarginInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedDrawInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedOpenInfo;
import com.iss.itreasury.settlement.transspecial.dataentity.TransSpecialOperationInfo;
import com.iss.itreasury.util.IRollbackException;
/**
 * @author yiwang
 * 
 * To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and
 * Comments
 */
public interface Account extends javax.ejb.EJBObject
{
	// public methods write following block
	/**
	 * 方法说明： 新增客户
	 * 
	 * @param ci:
	 *            ClientInfo
	 * @return : long - 返回新增的客户ID
	 * @throws Exception
	 *  
	 */
	public long addClient(ClientInfo ci) throws RemoteException, IRollbackException;
	/**
	 * 方法说明：取得客户编号
	 * 
	 * @param lOfficeID :
	 *            long @return: String - 新增的客户编号
	 * @throws Exception
	 */
	public String getNewClientCode(long lOfficeID) throws RemoteException, IRollbackException;
	/**
	 * 方法说明：根据客户ID，查询客户信息
	 * 
	 * @param lClientID :
	 *            long @return: ClientInfo
	 * @throws Exception
	 */
	public ClientInfo findClientByID(long lClientID) throws RemoteException, IRollbackException;
	/**
	 * 方法说明：根据查询条件组合，查询出符合条件的客户
	 * 
	 * @param qcci:
	 *            QueryClientConditionInfo @return: Collection
	 * @throws Exception
	 */
	public Collection findClientByCondition(QueryClientConditionInfo qcci) throws RemoteException, IRollbackException;
	/**
	 * 方法说明：修改客户信息
	 * 
	 * @param ci:
	 *            ClientInfo @return: long - 客户ID
	 * @throws Exception
	 */
	public long updateClient(ClientInfo ci) throws RemoteException, IRollbackException;
	/**
	 * 方法说明： 删除客户
	 * 
	 * @param ci:
	 *            ClientInfo
	 * @return : long - 返回删除的客户ID
	 * @throws Exception
	 *  
	 */
	public long deleteClient(long lClientID) throws RemoteException, IRollbackException;
	/**
	 * 方法说明：新增活期账户
	 * 
	 * @param ai
	 *            @return: long - 新增的账户ID
	 * @throws RemoteException,IRollbackException
	 */
	public long addCurrentAccount(AccountInfo ai, SubAccountCurrentInfo saci, Vector vAccountBank)
		throws RemoteException, IRollbackException;
	/**
	 * 方法说明：新增定期和贷款账户
	 * 
	 * @param ai
	 *            @return: long - 新增的账户ID
	 * @throws RemoteException,IRollbackException
	 */
	public long addAccount(AccountInfo ai) throws RemoteException, IRollbackException;
	/**
	 * 方法说明：根据账户ID，查询账户信息
	 * 
	 * @param lAccountID
	 *            @return:AccountInfo ai
	 * @throws RemoteException,IRollbackException
	 */
	public AccountInfo findAccountByID(long lAccountID) throws RemoteException, IRollbackException;
	/**
	 * 方法说明：根据子账户ID，查询子账户信息
	 * 
	 * @param lSubAccountID
	 *            子账户ID @return:SubAccountAssemblerInfo ai 子账户Assemble
	 * @throws RemoteException,IRollbackException
	 */
	public SubAccountAssemblerInfo findSubAccountByID(long lSubAccountID) throws RemoteException, IRollbackException;
	/**
	 * 方法说明：根据开户行ID，查询开户行信息
	 * 
	 * @param lAccountID
	 *            @return:AccountInfo ai
	 * @throws RemoteException,IRollbackException
	 */
	public BranchInfo findBranchByID(long lAccountID) throws RemoteException, IRollbackException;
	/**
	 * 方法说明：根据查询条件组合，查询出符合条件的客户
	 * 
	 * @param QueryAccountConditionInfo
	 *            qcai @return:Collection
	 * @throws RemoteException,IRollbackException
	 */
	public Collection findAccountByCondition(QueryAccountConditionInfo qaci)
		throws RemoteException, IRollbackException;
	/**
	 * 方法说明：根据查询条件组合，查询出符合条件的客户
	 * 
	 * @param QueryAccountConditionInfo
	 *            qcai @return:Collection
	 * @throws RemoteException,IRollbackException
	 */
	public Collection findReserveAccountByCondition(QueryAccountConditionInfo qaci)
		throws RemoteException, IRollbackException;
	
	/**
	 * 方法说明：修改活期账户的信息
	 * 
	 * @param ai
	 * @return :返回账户的ID
	 * @throws RemoteException,IRollbackException
	 */
	public long updateCurrentAccount(AccountInfo ai, SubAccountCurrentInfo saci, Vector vAccountBank)
		throws RemoteException, IRollbackException;
	/**
	 * 方法说明：修改定期和贷款账户的信息
	 * 
	 * @param ai
	 * @return :返回账户的ID
	 * @throws RemoteException,IRollbackException
	 */
	public long updateAccount(AccountInfo ai) throws RemoteException, IRollbackException;
	/**
	 * 方法说明：得到新的账户号
	 * 
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @return ：返回账户编号
	 * @throws RemoteException,IRollbackException
	 */
	public String getNewAccountNo(long lOfficeID, long lCurrencyID, long lAccountTypeID, long lClientID)
		throws RemoteException, IRollbackException;
	/**
	 * 方法说明：得到新的账户号（备付金账户，准备金账户，拆借账户 适用）
	 * 
	 * @param lOfficeID 当前操作机构
	 * @param lOfficeID 客户所属机构
	 * @param lAccountGroupTypeID 账户组类型
	 * @param lAccountTypeID 账户类型
	 * @param lCurrencyID
	 * @return ：返回账户编号
	 * @throws RemoteException,IRollbackException
	 */
	public String getNewAccountNo(long lOfficeID, long lCurrencyID, long lAccountTypeID, long lClientID,long lAccountGroupTypeID,long belongOfficeID)
		throws RemoteException, IRollbackException;

	/**
	 * 方法说明：判断是否透支
	 * 
	 * @param lAccountID
	 * @param dPayAmount
	 *            @return: 透支返回 true; 不透支返回false
	 * @throws RemoteException,IRollbackException
	 * @throws IRollbackException
	 */
	public boolean isOverDraft(long lAccountID, long subAccountID, double dPayAmount)
		throws RemoteException, IRollbackException;
	
	/**
	 * 方法说明：判断 账户余额支付累计未复核交易金额后是否还有余额支付此交易金额（实际余额-累计未复核金额-交易金额<0,不考虑账户是否允许透支）
	 * 
	 * @param lAccountID
	 * @param dPayAmount
	 *            @return: 不足 返回true; 足 返回false
	 * @throws RemoteException,IRollbackException
	 * @throws IRollbackException
	 */
	public boolean isLackBalanceToDraft(long lAccountID, double dPayAmount)throws RemoteException, IRollbackException;
	
	
	public long validateAccountStatus(long lAccountID) throws RemoteException, IRollbackException;
	/**
	 * 方法说明 ： 开立活期存款子账户
	 * 
	 * @param safi
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long openCurrentSubAccount(SubAccountCurrentInfo saci) throws RemoteException, IRollbackException;
	/**
	 * 方法说明 ： 开立定期子账户
	 * 
	 * @param safi
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long openFixSubAccount(SubAccountFixedInfo safi) throws RemoteException, IRollbackException;
	/**
	 * 方法说明 ： 开立贷款存款子账户
	 * 
	 * @param safi
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long openLoanSubAccount(SubAccountLoanInfo sali) throws RemoteException, IRollbackException;
	/**
	 * 方法说明：增加账户余额。
	 * 
	 * @param lAccountID
	 * @param dPayAmount
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long addAccountBalance(long lSubAccountID, double dPayAmount) throws RemoteException, IRollbackException;
	/**
	 * 方法说明：减少账户余额。
	 * 
	 * @param lAccountID
	 * @param dPayAmount
	 * @return 子账户ID
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long subtractAccountBalance(long lSubAccountID, double dPayAmount)
		throws RemoteException, IRollbackException;
	// 查询-账户余额查询
	public Double queryAccountBalance(QueryAccountWhereInfo qawi) throws RemoteException, IRollbackException;
	/**
	 * 方法说明：增加活期子账户累计未复核付款金额。 保存时：未复核累计付款金额增加，参数dPaymount为正数；复核时：未复核累计付款金额减少，参数
	 * dPaymount为负数
	 * 
	 * @param lAccountID
	 * @param dPayAmount
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */

	public long addCurrentUncheckAmount(long lAccountID, long lOppAccountID, double dPayAmount)
		throws RemoteException, IRollbackException;

	/**
	 * 方法说明：增加定期子账户累计未复核付款金额。 保存时：未复核累计付款金额增加，参数dPaymount为正数；复核时：未复核累计付款金额减少，参数
	 * dPaymount为负数
	 * 
	 * @param lAccountID
	 * @param dPayAmount
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */

	public long addFixedUncheckAmount(long lAccountID, String strFixedDepositNo, double dPayAmount)
		throws RemoteException, IRollbackException;
	
	/**
	 * 增加 融资租赁 保证金 保后处理  子账户中累计未复核付款金额。
	 * @param lAccountID
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void addFixedUncheckAmount4Recog(long lAccountID,long contractid)
	throws RemoteException, IRollbackException;
	
	/**
	 * 方法说明：增加贷款子账户累计未复核付款金额。 保存时：未复核累计付款金额增加，参数dPaymount为正数；复核时：未复核累计付款金额减少，参数
	 * dPaymount为负数
	 * 
	 * @param lAccountID
	 * @param dPayAmount
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */

	public long addLoanUncheckAmount(long lAccountID, long loanNoteID, double dPayAmount)
		throws RemoteException, IRollbackException;
	/**
	 * 方法说明：扣除活期累计未复核付款金额。 保存时：未复核累计付款金额增加，参数dPaymount为正数；复核时：未复核累计付款金额减少，参数
	 * dPaymount为负数
	 * 
	 * @param lSubAccountID
	 * @param dPayAmount
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long subtractCurrentUncheckAmount(long lAccountID, double dPayAmount)
		throws RemoteException, IRollbackException;

	/**
	 * 方法说明：扣除定期累计未复核付款金额。 保存时：未复核累计付款金额增加，参数dPaymount为正数；复核时：未复核累计付款金额减少，参数
	 * dPaymount为负数
	 * 
	 * @param lSubAccountID
	 * @param dPayAmount
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long subtractFixedUncheckAmount(long lAccountID, String strFixedDepositNo, double dPayAmount)
		throws RemoteException, IRollbackException;

	/**
     * 融资租赁 保证金 保后处理  更新 子账户 中 累计未复核金额，更新为0.0
     * @param lAccountID
     * @param contractid
     * @throws RemoteException
     * @throws IRollbackException
     */
	public void subtractUncheckAmount4Recog(long lAccountID,long contractid)
	throws RemoteException, IRollbackException;
	/**
	 * 方法说明：活期支取。
	 * 
	 * @param tadi
	 * @return 子账户ID
	 * @throws RemoteException
	 * @throws IRollbackException
	 */

	/**
	 * 方法说明：扣除贷款累计未复核付款金额。 保存时：未复核累计付款金额增加，参数dPaymount为正数；复核时：未复核累计付款金额减少，参数
	 * dPaymount为负数
	 * 
	 * @param lSubAccountID
	 * @param dPayAmount
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long subtractLoanUncheckAmount(long lAccountID, long loanNoteID, double dPayAmount)
		throws RemoteException, IRollbackException;

	public long withdrawCurrent(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException;

	/**
	 * 定期支取
	 * @param tadi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long withdrawFix(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException;
	
	public Collection withdrawFix4Recog(TransAccountDetailInfo tadi,long nContractID) throws RemoteException, IRollbackException;
	
	/**
	 * 融资租赁还款支取
	 * @param tadi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long withdrawFinance(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException;

	/**
	 * 方法说明：取消活期支取。
	 * 
	 * @param tadi
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public void cancelWithdrawCurrent(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException;

	/**
	 * 取消定期存入
	 * @param tadi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */

	public void cancelWithdrawFinance(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException;

	/**
	 * 取消定期存入
	 * @param tadi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */

	public void cancelWithdrawFix(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException;
	
	public void cancelWithdrawFix4Recog(TransAccountDetailInfo tadi,long nContractID) throws RemoteException, IRollbackException;

	/**
	 * 方法说明：删除定期子账户。注意，该方法目前只做为一个返回子账户的方法，实际的删除将在定期或贷款减少账户余额到0的时候自动删除该账户
	 * 
	 * @param tadi
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public void deleteFixSubAccount(long subAccountID) throws RemoteException, IRollbackException;

	/**
	 * 方法说明：删除贷款子账户。(本方法不删除账户，删除操作在扣除账户余额时进行)
	 * 
	 * @param tadi
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public void deleteLoanSubAccount(long subAccountID) throws RemoteException, IRollbackException;

	/**
	 * 方法说明：活期存入。
	 * 
	 * @param tadi
	 * @return 子账户ID
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long depositCurrent(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException;
	/**
	 * 方法说明：活期存入。
	 * 
	 * @param tadi
	 * @return 子账户ID
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long depositFix(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException;

	/**
	 * 删除账户交易明细表的交易明细
	 * 
	 * @param strTransNo
	 *            交易编号
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long deleteTransAccountDetail(String strTransNo) throws RemoteException, IRollbackException;
	/**
	 * 方法说明：取消活期存入。
	 * 
	 * @param tadi
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public void cancelDepositCurrent(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException;

	/**
	 * 方法说明：取消定期存入。
	 * 
	 * @param tadi
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long cancelDepositFix(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException;

	public double getPredrawInterestBySubAccountIDAndAccountType(long subAccountID, long accountType)
		throws RemoteException, IRollbackException;

	/**
	 * 方法说明：贷款发放
	 * 
	 * @param tadi
	 * @return 子账户ID
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long grantLoan(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException;

	/**
	 * 方法说明：取消贷款发放
	 * 
	 * @param tadi
	 * 
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long cancelGrantLoan(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException;

	/**
	 * 贷款归还
	 * 
	 * @param tadi
	 * 
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long repayLoan(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException;
	/**
	 * 取消贷款归还
	 * 
	 * @param tadi
	 * 
	 * @throws RemoteException
	 * @throws IRollbackException
	 * 
	 */
	public long cancelRepayLoan(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException;

	/**
	 * 方法说明：保存外部账户
	 * 
	 * @param ExternalAccountInfo
	 * @return 保存记录ID
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long saveExternalAccount(ExternalAccountInfo info) throws RemoteException, IRollbackException;

	/**
	 * @param lSubAccountID
	 * @return @throws
	 *         IRollbackException
	 */
	public String getSubjectBySubAccountID(long lSubAccountID, int subjectType)
		throws RemoteException, IRollbackException;
	
	public String getSubjectByOther(long lCracontractID, int subjectType)
	throws RemoteException, IRollbackException;
	/**
	 * 查找活期子账户
	 * @param lAccountID
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public SubAccountAssemblerInfo findSubCurrentAccountByAccountID(long lAccountID)
		throws RemoteException, IRollbackException;
	/**
	 * 方法说明：批量修改账户状态和利率计划
	 * 
	 * @param ai
	 * @return :返回成功标识
	 * @throws RemoteException,IRollbackException
	 */
	public long BatchUpdateAccount(QueryAccountConditionInfo qaci, AccountInfo ai, SubAccountCurrentInfo saci)
		throws RemoteException, IRollbackException;
	/**
	 * 方法说明：根据账户ID修改账户状态
	 * @param qaci
	 * @return long - 返回账户ID
	 * @throws Exception
	 */
	public long UpdateCheckStatus(long lAccountID,long lActionID,long lCheckStatusID, long lCheckUserID, Timestamp tsCheckDate)
		throws RemoteException, IRollbackException;

	public long getCurrentSubAccoutIDByAccoutID(long accoutID) throws RemoteException, IRollbackException;

	public long getLoanSubAccountIDByAccountIDAndLoanNoteID(long accoutID, long loanNoteID)
		throws RemoteException, IRollbackException;
	
	/**
	 * added by mzh_fu 2007/08/08
	 * @param accoutID
	 * @param loanNoteID
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long getLoanSubAccountIDByAccountIDAndLoanNoteIDAndStatus(
			long accoutID, long loanNoteID,long lStatus) throws RemoteException,
			IRollbackException ;

	public long getAccountTypeBySubAccountID(long subAccountID) throws RemoteException, IRollbackException;

	/**根据交易费用类型ID查询科目号*/
	public String getSubjectByTransFeeTypeID(long transFeeTypeID) throws RemoteException, IRollbackException;

    //账户审批方法/
	public long doApproval(AccountInfo info)throws RemoteException, IRollbackException;
//	账户取消审批方法/
	public long cancelApproval(AccountInfo info)throws RemoteException, IRollbackException;
	//新建账户是否做了业务
	public long haveTrans(AccountInfo info)throws RemoteException, IRollbackException;
	
	/**
	 * 到期续存处理计提利息和计提日期
	 * @param tadi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long ContinueFixedPreDrawInterest(TransAccountDetailInfo tadi, String strCheckType) throws RemoteException, IRollbackException;
	
	//获取客户
	public String findClientCodeBySubAccountID(long lSubAccountID) throws RemoteException, IRollbackException;
	
	//获取账户类型
	public long findAccountTypeBySubAccountID(long lSubAccountID) throws RemoteException, IRollbackException;
	public long updateCommission(TransAccountDetailInfo info) throws RemoteException, IRollbackException;
}