/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.accountbook.bizlogic;
import java.rmi.RemoteException;

import com.iss.itreasury.settlement.craftbrother.dataentity.TransCraftbrotherInfo;
import com.iss.itreasury.settlement.transbakreserve.dataentity.TransBakReserveInfo;
import com.iss.itreasury.settlement.transcommission.dataentity.TransCommissionInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransOnePayMultiReceiveInfo;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransDiscountDetailInfo;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransGrantDiscountInfo;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransRepaymentDiscountInfo;
import com.iss.itreasury.settlement.transfee.dataentity.TransFeeInfo;
import com.iss.itreasury.settlement.transferloancontract.dataentity.TransferLoanContractInfo;
import com.iss.itreasury.settlement.transfinance.dataentity.TransReceiveFinanceInfo;
import com.iss.itreasury.settlement.transfinance.dataentity.TransReturnFinanceInfo;
import com.iss.itreasury.settlement.transfinancialmargin.dataentity.TransFinancialMarginInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedContinueInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedDrawInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedOpenInfo;
import com.iss.itreasury.settlement.transgeneralledger.dataentity.TransGeneralLedgerInfo;
import com.iss.itreasury.settlement.transinternallend.dataentity.TransInternalLendInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.TransMarginOpenInfo;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.TransMarginWithdrawInfo;
import com.iss.itreasury.settlement.transnoteacceptance.dataentity.TransAcceptanceNoteAcceptanceInfo;
import com.iss.itreasury.settlement.transreserve.dataentity.TransReserveInfo;
import com.iss.itreasury.settlement.transsecurities.dataentity.TransSecuritiesInfo;
import com.iss.itreasury.settlement.transspecial.dataentity.TransSpecialOperationInfo;
import com.iss.itreasury.util.IRollbackException;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface AccountBook extends javax.ejb.EJBObject
{
	public void saveSecuritiesDetails(TransSecuritiesInfo transInfo) throws RemoteException, IRollbackException;
	public void deleteSecuritiesDetails(TransSecuritiesInfo transInfo) throws RemoteException, IRollbackException;
	public void checkSecuritiesDetails(TransSecuritiesInfo transInfo) throws RemoteException, IRollbackException;
	public void cancelCheckSecuritiesDetails(TransSecuritiesInfo transInfo) throws RemoteException, IRollbackException;
	
	/**
	 * 活期财务交易保存(活期交易保存时的财务处理，包括)
	 * 逻辑操作：
	 * 1.若交易类型为银行付款、支票付款、现金付款、票汇付款、其它付款、内部转账、委托收款、委托存款、保证金存款；或交易类型为一付多收，且付款账户ID>0（为内部转账），则：累计未复核金额（付款账户，发生额）
	 * 2.若交易类型为银行付款、支票付款、现金付款、票汇付款、其它付款、内部转账、委托收款、委托存款、保证金存款，且票据号>0，则：票据使用（票据发放银行，票据类型，票据号，付款客户ID，执行日，录入人）
	 * @param transInfo Sett_TransCurrentDepositInfo 活期交易实体类
	 * @throws java.rmi.RemoteException
	 * @throws Exception
	 * @throws IRollbackException
	 */
	public void saveCurrentAccountDetails(TransCurrentDepositInfo transInfo) throws RemoteException, IRollbackException;
    
    /**
     * 手续费收取财务交易保存(手续费交易保存时的财务处理，包括)
     * @param transInfo Sett_TransCurrentDepositInfo 活期交易实体类
     * @throws java.rmi.RemoteException
     * @throws Exception
     * @throws IRollbackException
     */
    public void saveCommissionAccountDetails(TransCommissionInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 * 活期财务交易删除(活期交易删除时的财务处理，包括)
	 * 逻辑操作：
	 * 1.若交易类型为银行付款、支票付款、现金付款、票汇付款、其它付款、内部转账、委托收款、委托存款、保证金存款；或交易类型为一付多收，且付款账户ID>0（内部转账），则：扣除未复核金额（付款方账户，交易金额）
	 * 2.若交易类型为银行付款、支票付款、现金付款、票汇付款、其它付款、内部转账、委托收款、委托存款、保证金存款，且票据号>0，则：票据取消使用（票据发放银行，票据类型，票据号，执行日，录入人）
	 * @param transInfo Sett_TransCurrentDepositInfo
	 * @throws java.rmi.RemoteException
	 * @throws Exception
	 * @throws IRollbackException
	 */
	public void deleteCurrentAccountDetails(TransCurrentDepositInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 * 活期财务交易复核(活期交易复核时的财务处理，包括)
	 * 逻辑操作：
	 * 2.若交易类型为银行付款、支票付款、现金付款、票汇付款、其它付款、内部转账、委托收款、委托存款、保证金存款；或交易类型为一付多收，且付款账户ID>0（内部转账），则：活期支取（付款方账户，发生额，对方账户=收款方）
	 * 3.若交易类型为银行收款、现金收款、内部转账、委托收款、委托存款、保证金存款；或交易类型为一付多收，且收款账户ID>0（为内部转账），则：活期存入（收款方账户，发生额,对方账户=付款方）
	 * 4.通存通兑处理AccountBookBizlogic.InterbranchSettlement()
	 * 5.产生会计分录GeneralLedgerBizlogic.GenerateGLEntry(…)。如果收/付款银行账户ID>0，则本金流向lPrincipalType =2银行，否则本金流向lPrincipalType =1内部转账，分录类型lEntryType =0无关，lAccountID1=收款方账户，lAccountID2=付款方账户，dAmount1=发生额。其它略。
	 * @param transInfo Sett_TransCurrentDepositInfo 活期交易实体类
	 * @throws java.rmi.RemoteException
	 * @throws Exception
	 * @throws IRollbackException
	 */
	public void checkCurrentAccountDetails(TransCurrentDepositInfo transInfo) throws RemoteException, IRollbackException;

	/**
     * 手续费收取财务交易复核(手续费交易复核时的财务处理，包括)
     * 产生会计分录GeneralLedgerBizlogic.GenerateGLEntry(…)。如果收/付款银行账户ID>0，则本金流向lPrincipalType =2银行，否则本金流向lPrincipalType =1内部转账，分录类型lEntryType =0无关，lAccountID1=收款方账户，lAccountID2=付款方账户，dAmount1=发生额。其它略。
     * @param transInfo Sett_TransCurrentDepositInfo 活期交易实体类
     * @throws java.rmi.RemoteException
     * @throws Exception
     * @throws IRollbackException
     */
    public void checkCommissionAccountDetails(TransCommissionInfo transInfo) throws RemoteException, IRollbackException;
    
	/**
	 * 活期财务交易取消复核(活期交易取消复核时的财务处理，包括)
	 * 逻辑操作：
	 * 2.若交易类型为银行付款、支票付款、现金付款、票汇付款、其它付款、内部转账、委托收款、委托存款、保证金存款；或交易类型为一付多收，且付款方账户ID>0（为内部转账），则：活期支取反交易（付款方账户，发生额，对方账户=收款方）
	 * 3.若交易类型为银行收款、现金收款、内部转账、委托收款、委托存款、保证金存款；或交易类型为一付多收，且收款方账户ID>0（为内部转账），则：活期存入反交易（收款方账户，发生额，对方账户=付款方）
	 * 4.删除明细账AccountBookBizlogic.DeleteAccountDetail
	 * 5.通存通兑反交易处理AccountBookBizlogic.InterbranchSettlementReverse()
	 * 6.删除会计分录GeneralLedgerBizlogic.DeleteGLEntry(…)
	 * @param transInfo Sett_TransCurrentDepositInfo 活期交易实体类
	 * @throws java.rmi.RemoteException
	 * @throws Exception
	 * @throws IRollbackException
	 */
	public void unCheckCurrentAccountDetails(TransCurrentDepositInfo transInfo) throws RemoteException, IRollbackException;

	/*
	 * 定期开立、通知存款开立交易
	 * @param TransFixedOpenInfo
	 * @Exception RemoteException, IRollbackException
	 */

	public void saveOpenFixedDeposit(TransFixedOpenInfo info) throws RemoteException, IRollbackException;

	/*
	 * 定期开立、通知存款开立删除
	 * @param TransFixedOpenInfo
	 * @Exception RemoteException, IRollbackException
	 */
	public void deleteOpenFixedDeposit(TransFixedOpenInfo info) throws RemoteException, IRollbackException;

	/**
	 * 定期开立、通知存款开立交易复核
	 */
	public void checkOpenFixedDeposit(TransFixedOpenInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 * 定期开立、通知存款开立交易取消
	*/
	public void cancelCheckOpenFixedDeposit(TransFixedOpenInfo info) throws RemoteException, IRollbackException;
	/**
	 * 定期支取、通知存款支取交易保存
	    */
	public void saveWithdrawFixedDeposit(TransFixedDrawInfo info) throws RemoteException, IRollbackException;

	/**
	 * 定期支取、通知存款支取交易删除
	*/
	public void deleteWithdrawFixedDeposit(TransFixedDrawInfo info) throws RemoteException, IRollbackException;
	/**
	 * 定期支取、通知存款支取交易复核*/
	public void checkWithdrawFixedDeposit(TransFixedDrawInfo info) throws RemoteException, IRollbackException;

	/**定期支取、通知存款支取交易，按取消复核*
	 */
	public void cancelCheckWithdrawFixedDeposit(TransFixedDrawInfo info) throws RemoteException, IRollbackException;

	/**
	 * 保证金存款开立交易
	 * @param TransFixedOpenInfo
	 * @Exception RemoteException, IRollbackException
	 */
	public void saveOpenMarginDeposit(TransMarginOpenInfo info) throws RemoteException, IRollbackException;

	/**
	 * 保证金开立删除
	 * @param TransFixedOpenInfo
	 * @Exception RemoteException, IRollbackException
	 */
	public void deleteOpenMarginDeposit(TransMarginOpenInfo info) throws RemoteException, IRollbackException;

	/**
	 * 保证金存款开立交易复核
	 */
	public void checkOpenMarginDeposit(TransMarginOpenInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 * 保证金存款开立交易取消
	*/
	public void cancelCheckOpenMarginDeposit(TransMarginOpenInfo info) throws RemoteException, IRollbackException;
	/**
	 * 保证金存款支取交易保存
	 */
	public void saveWithdrawMarginDeposit(TransMarginWithdrawInfo info) throws RemoteException, IRollbackException;
	
	/**
	 * 融资租赁保证金保后处理 存款支取交易保存
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveWithFinancialMargin(TransFinancialMarginInfo info) throws RemoteException, IRollbackException;
    /**
     * 融资租赁保证金保后处理 修改保存时的 删除
     * @param info
     * @throws RemoteException
     * @throws IRollbackException
     */
	public void deleteWithFinancialMargin(TransFinancialMarginInfo info) throws RemoteException, IRollbackException;
	/**
	 * 融资租赁保证金保后处理 复核
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void checkWithFinancialMargin(TransFinancialMarginInfo info) throws RemoteException, IRollbackException;
	/**
	 * 保证金存款支取交易删除
	*/
	public void deleteWithdrawMarginDeposit(TransMarginWithdrawInfo info) throws RemoteException, IRollbackException;
	/**
	 * 保证金存款支取交易复核*/
	public void checkWithdrawMarginDeposit(TransMarginWithdrawInfo info) throws RemoteException, IRollbackException;

	/**保证金存款支取交易，按取消复核*
	 */
	public void cancelCheckWithdrawMarginDeposit(TransMarginWithdrawInfo info) throws RemoteException, IRollbackException;
	/**
	 * 融资租赁保证金保后处理 取消复核
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelCheckWithFinancialMargin(TransFinancialMarginInfo info) throws RemoteException, IRollbackException;

	
	/**定期续期转存保存*/
	public void saveContinueFixedDeposit(TransFixedContinueInfo info) throws RemoteException, IRollbackException;

	public void deleteContinueFixedDeposit(TransFixedContinueInfo info) throws RemoteException, IRollbackException;

	/**定期续期转存复核*/
	public void checkContinueFixedDeposit(TransFixedContinueInfo info) throws RemoteException, IRollbackException;

	/**定期续期转存取消复核*/
	public void cancelCheckContinueFixedDeposit(TransFixedContinueInfo info) throws RemoteException, IRollbackException;

	/**
	 * 自营贷款发放、委托贷款发放等交易保存
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveGrantLoan(TransGrantLoanInfo info) throws RemoteException, IRollbackException;

	/**
	 * 自营贷款发放、委托贷款发放等交易删除
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void deleteGrantLoan(TransGrantLoanInfo info) throws RemoteException, IRollbackException;

	/**
	 * 自营贷款发放、委托贷款发放等交易复核
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void checkGrantLoan(TransGrantLoanInfo info) throws RemoteException, IRollbackException;

	/**
	 * 自营贷款发放、委托贷款发放等取消复核
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelCheckGrantLoan(TransGrantLoanInfo info) throws RemoteException, IRollbackException;

	/**
	 * 自营贷款收回、委托贷款收回等交易保存
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */

	public void saveRepaymentLoan(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 * 自营贷款收回、委托贷款收回等交易删除
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void deleteRepaymentLoan(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 * 自营贷款收回、委托贷款收回等交易复核
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */

	public void checkRepaymentLoan(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 * 自营贷款收回、委托贷款收回等交易取消复核
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelCheckRepaymentLoan(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 *贴现发放交易保存
	*/
	public void saveGrantDiscount(TransGrantDiscountInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 *贴现发放交易删除
	*/
	public void deleteGrantDiscount(TransGrantDiscountInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 *贴现发放交易复核
	*/
	public void checkGrantDiscount(TransGrantDiscountInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 *贴现发放交易取消复核
	*/
	public void cancelCheckGrantDiscount(TransGrantDiscountInfo transInfo) throws RemoteException, IRollbackException;

	/**贴现收回保存*/
	public void saveRepaymentDiscount(TransRepaymentDiscountInfo transInfo) throws RemoteException, IRollbackException;

	/**贴现收回删除*/
	public void deleteRepaymentDiscount(TransRepaymentDiscountInfo transInfo) throws RemoteException, IRollbackException;

	/**贴现收回复核*/
	public void checkRepaymentDiscount(TransRepaymentDiscountInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 * 贴现收回财务交易取消复核(贴现收回交易取消复核时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransRepaymentDiscountInfo 贴现收回交易实体类
	 * @throws IRollbackException
	 */
	public void cancelCheckRepaymentDiscount(TransRepaymentDiscountInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 * 特殊业务财务交易保存(特殊业务保存时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransSpecialOperationInfo 特殊业务实体类
	 * @throws IRollbackException
	 */
	public void saveSpecialOperation(TransSpecialOperationInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 * 特殊业务财务交易删除(特殊业务交易删除时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransSpecialOperationInfo 特殊业务交易实体类
	 * @throws IRollbackException
	 */
	public void deleteSpecialOperation(TransSpecialOperationInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 * 特殊业务财务交易复核(特殊业务交易复核时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransSpecialOperationInfo 特殊业务交易实体类
	 * @throws IRollbackException
	 */
	public void checkSpecialOperation(TransSpecialOperationInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 * 特殊业务财务交易取消复核(特殊业务交易取消复核时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransSpecialOperationInfo 特殊业务交易实体类
	 * @throws IRollbackException
	 */
	public void cancelCheckSpecialOperation(TransSpecialOperationInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 * 一付多收交易保存
	 *  逻辑操作：
	 * @param transInfo 
	 * @throws IRollbackException
	 */
	public void saveOnePayMultiReceive(TransOnePayMultiReceiveInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 * 一付多收交易删除
	 *  逻辑操作：
	 * @param transInfo 
	 * @throws IRollbackException
	 */
	public void deleteOnePayMultiReceive(TransOnePayMultiReceiveInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 * 一付多收交易复核
	 *   逻  辑操作：
	 * @param transInfo 
	 * @throws IRollbackException
	 */
	public void checkOnePayMultiReceive(TransOnePayMultiReceiveInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 * 一付多收交易取消
	 *   逻辑操作：
	 * @param transInfo 
	 * @throws IRollbackException
	 */
	public void cancelCheckOnePayMultiReceive(TransOnePayMultiReceiveInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 * 一付多收交易勾账
	 *   逻  辑操作：
	 * @param transInfo 
	 * @throws IRollbackException
	 */
	public void squareOnePayMultiReceive(TransOnePayMultiReceiveInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 * 一付多收交易取消勾账
	 *   逻辑操作：
	 * @param transInfo 
	 * @throws IRollbackException
	 */
	public void cancelsquareCheckOnePayMultiReceive(TransOnePayMultiReceiveInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 * 总账类保存   逻辑操作：
	 * @param transInfo 
	 * @throws IRollbackException
	 */
	public void saveGeneralLedgerOperation(TransGeneralLedgerInfo transInfo)  throws RemoteException, IRollbackException;
		
	/**
	 * 总账类删除   逻辑操作：
	 * @param transInfo 
	 * @throws IRollbackException
	 */	
	public void deleteGeneralLedgerOperation(TransGeneralLedgerInfo transInfo)  throws RemoteException, IRollbackException;
	
	
	/**
	 * 总账类复核   逻辑操作：
	 * @param transInfo 
	 * @throws IRollbackException
	 */		
	public void checkGeneralLedgerOperation(TransGeneralLedgerInfo transInfo)  throws RemoteException, IRollbackException;
	
	/**
	 * 总账类取消复核   逻辑操作：
	 * @param transInfo 
	 * @throws IRollbackException
	 */			
	public void cancelCheckGeneralLedgerOperation(TransGeneralLedgerInfo transInfo)  throws RemoteException, IRollbackException;
	
	/**
	 *多笔贷款收回保存
	*/
	public void saveMultiLoanReceive(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 *多笔贷款收回删除
	*/
	public void deleteMultiLoanReceive(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 *多笔贷款收回复核
	*/
	public void checkMultiLoanReceive(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 *多笔贷款收回取消复核
	*/
	public void cancelCheckMultiLoanReceive(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 *多笔贷款收回勾账
	*/	
	public void squareMultiLoanReceive(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 *多笔贷款收回取消勾账
	*/
	public void cancelSquareMultiLoanReceive(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException;
	
	/**交易费用处理交易保存*/
	public void saveTransFee(TransFeeInfo transInfo)throws RemoteException, IRollbackException;
	
	/**交易费用处理交易删除*/
	public void deleteTransFee(TransFeeInfo transInfo)throws RemoteException, IRollbackException;
	
	/**交易费用处理交易复核*/
	public void checkTransFee(TransFeeInfo transInfo)throws RemoteException, IRollbackException;
	
	/**交易费用处理交易取消复核*/
	public void cancelCheckTransFee(TransFeeInfo transInfo)throws RemoteException, IRollbackException;
	

	public void interbranchSettlementReverse()  throws RemoteException, IRollbackException;;
	
	
	/**
	 * 融资租赁收款交易保存
	 */
	public void saveReceiveFinance(TransReceiveFinanceInfo info) throws RemoteException, IRollbackException;

	/**
	 * 融资租赁收款交易删除
	 */
	public void deleteReceiveFinance(TransReceiveFinanceInfo info) throws RemoteException, IRollbackException;

	/**
	 * 融资租赁收款交易复核
	 */
	public void checkReceiveFinance(TransReceiveFinanceInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 * 融资租赁收款交易取消复核
	*/
	public void cancelCheckReceiveFinance(TransReceiveFinanceInfo info) throws RemoteException, IRollbackException;
	
	
	/**
	 * 融资租赁还款交易保存
	 */
	public void saveReturnFinance(TransReturnFinanceInfo info) throws RemoteException, IRollbackException;

	/**
	 * 融资租赁还款交易删除
	 */
	public void deleteReturnFinance(TransReturnFinanceInfo info) throws RemoteException, IRollbackException;

	/**
	 * 融资租赁还款交易复核
	 */
	public void checkReturnFinance(TransReturnFinanceInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 * 融资租赁还款交易取消复核
	*/
	public void cancelCheckReturnFinance(TransReturnFinanceInfo info) throws RemoteException, IRollbackException;
	/**
	 *转贴现发放交易复核
	*/
	public void checkTransDiscount(TransDiscountDetailInfo transInfo) throws RemoteException, IRollbackException;
	
	public boolean isCreateInstruction(long bankID) throws Exception,RemoteException;
	/**
	 * 资产转让付款复核
	*/
	public void checkTransferContract(TransferLoanContractInfo info) throws RemoteException, IRollbackException;
	/**
	 * 资产转让付款取消复核
	*/
	public void cancelCheckTransferContract(TransferLoanContractInfo info) throws RemoteException, IRollbackException;
	/**
	 * 资产转让收款复核
	*/
	public void repaycheckTransferContract(TransferLoanContractInfo info) throws RemoteException, IRollbackException;
	
	/**
	 * 资产转让收款取消复核
	*/
	public void repaycancelCheckTransferContract(TransferLoanContractInfo info) throws RemoteException, IRollbackException;
	
	/**
	 * 资产转让收款复核(代收)
	*/
	public void repayClientcheckTransferContract(TransferLoanContractInfo info) throws RemoteException, IRollbackException;
	
	/**
	 * 资产转让收款取消复核（代收）
	*/
	public void repayClientcancelCheckTransferContract(TransferLoanContractInfo info) throws RemoteException, IRollbackException;
	/**
	 * add by kevin(刘连凯)2011-07-15
	 * 内部拆借-保存操作
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveInternalLend(TransInternalLendInfo info) throws RemoteException, IRollbackException;
	/**
	 * add by kevin(刘连凯)2011-07-15
	 * 内部拆借-复核操作
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void checkInternalLend(TransInternalLendInfo info) throws RemoteException, IRollbackException;
	/**
	 * add by keivn(刘连凯)
	 * 2011-07-15
	 * 内部拆借-取消复核
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelCheckInternalLend(TransInternalLendInfo info) throws RemoteException, IRollbackException;
	/**
	 * 内部拆借收回-保存
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveInternalLendRepayment(TransInternalLendInfo info) throws RemoteException, IRollbackException;
	/**
	 * 内部拆借收回-删除
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void deleteInternalLendRepayment(TransInternalLendInfo info) throws RemoteException, IRollbackException;
	/**
	 * add by kevin(刘连凯)2011-07-19
	 * 内部拆借收回-复核操作
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void checkInternalLendRepayment(TransInternalLendInfo info) throws RemoteException, IRollbackException;
	/**
	 * add by keivn(刘连凯)
	 * 2011-07-19
	 * 内部拆借收回-取消复核
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelCheckInternalLendRepayment(TransInternalLendInfo info) throws RemoteException, IRollbackException;

	/**
	 * add by 江琪 2011-07-19
	 * 备付金上收-保存操作
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveBakReserveAccountDetailsUpReceive(TransBakReserveInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 * add by 江琪 2011-07-19
	 * 备付金上收-保存操作
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveBakReserveAccountDetailsDownReturn(TransBakReserveInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 * add by 江琪 2011-07-19
	 * 备付金上收-删除
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void deleteBakReserveAccountDetailsUpReceive(TransBakReserveInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 * add by 江琪 2011-07-19
	 * 备付金上收-删除
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void deleteBakReserveAccountDetailsDownReturn(TransBakReserveInfo transInfo) throws RemoteException, IRollbackException;
	
    public void checkBakReserveDetailsUpReceive(TransBakReserveInfo transInfo) throws RemoteException, IRollbackException;
	
    public void checkBakReserveDetailsDownReturn(TransBakReserveInfo transInfo) throws RemoteException, IRollbackException;
    
	public void unCheckBakReserveDetailsUpReceive(TransBakReserveInfo transInfo) throws RemoteException, IRollbackException;
    
    
	public void unCheckBakReserveDetailsDownReturn(TransBakReserveInfo transInfo) throws RemoteException, IRollbackException;
    

	/**
	 * add by 王振 2011-07-20
	 * 准备金上收-保存操作
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveReserveAccountDetailsUpReceive(TransReserveInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 * add by 王振 2011-07-20
	 * 准备金上收-保存操作
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveReserveAccountDetailsDownReturn(TransReserveInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 * add by 王振 2011-07-20
	 * 准备金上收-删除
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void deleteReserveAccountDetailsUpReceive(TransReserveInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 * add by 王振 2011-07-20
	 * 准备金上收-删除
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void deleteReserveAccountDetailsDownReturn(TransReserveInfo transInfo) throws RemoteException, IRollbackException;
    
	public void checkReserveDetailsUpReceive(TransReserveInfo transInfo) throws RemoteException, IRollbackException;
    
	public void checkReserveDetailsDownReturn(TransReserveInfo transInfo) throws RemoteException, IRollbackException;
	    
	public void unCheckReserveDetailsUpReceive(TransReserveInfo transInfo) throws RemoteException, IRollbackException;
	    
    
	public void unCheckReserveDetailsDownReturn(TransReserveInfo transInfo) throws RemoteException, IRollbackException;
	
	/**商业票据承兑-到期承兑交易保存*/
	public void saveAcceptanceNoteAcceptance(TransAcceptanceNoteAcceptanceInfo transInfo)throws RemoteException, IRollbackException;

	/**商业票据承兑-到期承兑交易删除*/
	public void deleteAcceptanceNoteAcceptance(TransAcceptanceNoteAcceptanceInfo transInfo)throws RemoteException, IRollbackException;
	
	/**商业票据承兑-到期承兑交易复核*/
	public void checkAcceptanceNoteAcceptance(TransAcceptanceNoteAcceptanceInfo transInfo)throws RemoteException, IRollbackException;
	
	/**商业票据承兑-到期承兑交易取消复核*/
	public void cancelCheckAcceptanceNoteAcceptance(TransAcceptanceNoteAcceptanceInfo transInfo)throws RemoteException, IRollbackException;
	
	/**
	 * 方正项目 : added by qhzhou 2011-03-28
	 * 同业往来复核 ,账户账簿操作
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void checkTransCraftbrother(TransCraftbrotherInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 * 同业业务结算处理取消复核
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelCheckTransCraftbrother(TransCraftbrotherInfo transInfo) throws RemoteException, IRollbackException;

	
}
