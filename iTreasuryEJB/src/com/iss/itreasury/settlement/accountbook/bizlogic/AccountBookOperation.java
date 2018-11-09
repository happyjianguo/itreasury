/*
 * Created on 2003-9-10
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
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;

/**
 * @author wlming
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AccountBookOperation
{
	private AccountBook accountBook;
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);	
	public AccountBookOperation() throws RemoteException, IRollbackException
	{
		try
		{
			AccountBookHome home = (AccountBookHome) EJBHomeFactory.getFactory().lookUpHome(AccountBookHome.class);
			accountBook = (AccountBook) home.create();
		}
		catch (Exception e)
		{
			throw new RemoteException();
		}
	}
	
	/**
	 * 证券业务保存的财务处理
	 * @author Forest,20040531
	 */
	public void saveSecuritiesDetails(TransSecuritiesInfo transInfo) throws RemoteException, IRollbackException
	{
		accountBook.saveSecuritiesDetails(transInfo);
	}
	/**
	 * 证券业务删除的财务处理
	 * @author Forest,20040531
	 */
	public void deleteSecuritiesDetails(TransSecuritiesInfo transInfo) throws RemoteException, IRollbackException
	{
		accountBook.deleteSecuritiesDetails(transInfo);
	}
	/**
	 * 证券业务复核的财务处理
	 * @author Forest,20040531
	 */
	public void checkSecuritiesDetails(TransSecuritiesInfo transInfo) throws RemoteException, IRollbackException
	{
		accountBook.checkSecuritiesDetails(transInfo);
	}
	/**
	 * 证券业务取消复核的财务处理
	 * @author Forest,20040531
	 */
	public void cancelCheckSecuritiesDetails(TransSecuritiesInfo transInfo) throws RemoteException, IRollbackException
	{
		accountBook.cancelCheckSecuritiesDetails(transInfo);
	}
	
	
	/**
	 * 定期(通知)开立财务交易复核(定期开立交易复核时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransFixedOpenInfo 定期(通知)开立交易实体类
	 * @throws IRollbackException
	 */
	public void checkOpenFixedDeposit(TransFixedOpenInfo transInfo) throws RemoteException,IRollbackException
	{
		log.debug("AccoutBookOperation::checkOpenFixedDeposit start");
		accountBook.checkOpenFixedDeposit(transInfo);
		log.debug("AccoutBookOperation::checkOpenFixedDeposit end");			
	}	
	
	/**
	 * 融资租赁收款财务交易复核(融资租赁收款交易复核时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransReceiveFinanceInfo 融资租赁收款交易实体类
	 * @throws IRollbackException
	 */
	public void checkReceiveFinance(TransReceiveFinanceInfo transInfo) throws RemoteException,IRollbackException
	{
		log.debug("AccoutBookOperation::checkReceiveFinance start");
		accountBook.checkReceiveFinance(transInfo);
		log.debug("AccoutBookOperation::checkReceiveFinance end");			
	}
	
	/**
	 * 融资租赁还款交易复核(融资租赁还款交易复核时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransReturnFinanceInfo 融资租赁还款交易实体类
	 * @throws IRollbackException
	 */
	public void checkReturnFinance(TransReturnFinanceInfo transInfo) throws RemoteException,IRollbackException
	{
		log.debug("AccoutBookOperation::checkReturnFinance start");
		accountBook.checkReturnFinance(transInfo);
		log.debug("AccoutBookOperation::checkReturnsFinance end");			
	}
	
	/**
	 * 定期(通知)开立财务交易取消复核(定期开立交易取消复核时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransFixedOpenInfo 定期(通知)开立交易实体类
	 * @throws IRollbackException
	 */
	public void cancelCheckOpenFixedDeposit(TransFixedOpenInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.cancelCheckOpenFixedDeposit(transInfo);
	}
	
	/**
	 * 融资租赁收款财务交易取消复核(融资租赁收款交易取消复核时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransReceiveFinanceInfo 融资租赁收款交易实体类
	 * @throws IRollbackException
	 */
	public void cancelCheckReceiveFinance(TransReceiveFinanceInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.cancelCheckReceiveFinance(transInfo);
	}
	
	/**
	 * 融资租赁还款财务交易取消复核(融资租赁还款交易取消复核时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransReturnFinanceInfo 融资租赁还款交易实体类
	 * @throws IRollbackException
	 */
	public void cancelCheckReturnFinance(TransReturnFinanceInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.cancelCheckReturnFinance(transInfo);
	}
	
	/**
	 * 定期支取财务交易保存(定期支取交易保存时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransFixedDrawInfo 定期支取交易实体类
	 * @throws IRollbackException
	 */
	public void saveWithdrawFixedDeposit(TransFixedDrawInfo transInfo) throws RemoteException,IRollbackException
	{	
		accountBook.saveWithdrawFixedDeposit(transInfo);	
	}	
	/**
	 * 定期(通知)支取财务交易删除(定期支取交易删除时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransFixedDrawInfo 定期(通知)支取交易实体类
	 * @throws IRollbackException
	 */
	public void deleteWithdrawFixedDeposit(TransFixedDrawInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.deleteWithdrawFixedDeposit(transInfo);
	}	
	/**
	 * 定期(通知)支取支取财务交易复核(定期支取交易复核时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransFixedDrawInfo 定期(通知)支取交易实体类
	 * @throws IRollbackException
	 */
	public void checkWithdrawFixedDeposit(TransFixedDrawInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.checkWithdrawFixedDeposit(transInfo);		
	}	
	/**
	 * 定期(通知)支取财务交易取消复核(定期支取交易取消复核时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransFixedDrawInfo 定期(通知)支取交易实体类
	 * @throws IRollbackException
	 */
	public void cancelCheckWithdrawFixedDeposit(TransFixedDrawInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.cancelCheckWithdrawFixedDeposit(transInfo);
	}	
	/**
	 * 定期续期转存财务交易保存(定期开立交易保存时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransFixedContinueInfo 定期续期转存交易实体类
	 * @throws IRollbackException
	 */
	public void saveContinueFixedDeposit(TransFixedContinueInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.saveContinueFixedDeposit(transInfo);
	}	
	/**
	 * 定期续期转存财务交易删除(定期开立交易删除时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransFixedContinueInfo 定期续期转存交易实体类
	 * @throws IRollbackException
	 */
	public void deleteContinueFixedDeposit(TransFixedContinueInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.deleteContinueFixedDeposit(transInfo);
	}	
	/**
	 * 定期续期转存财务交易复核(定期开立交易复核时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransFixedContinueInfo 定期续期转存交易实体类
	 * @throws IRollbackException
	 */
	public void checkContinueFixedDeposit(TransFixedContinueInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.checkContinueFixedDeposit(transInfo);
	}	
	/**
	 * 定期续期转存财务交易取消复核(定期开立交易取消复核时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransFixedContinueInfo 定期续期转存交易实体类
	 * @throws IRollbackException
	 */
	public void cancelCheckContinueFixedDeposit(TransFixedContinueInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.cancelCheckContinueFixedDeposit(transInfo);
	}	
	
	
	
	/**
	 * 保证金开立财务交易复核(保证金开立交易复核时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransMarginOpenInfo 保证金开立交易实体类
	 * @throws IRollbackException
	 */
	public void checkOpenMarginDeposit(TransMarginOpenInfo transInfo) throws RemoteException,IRollbackException
	{
		log.debug("AccoutBookOperation::checkOpenMarginDeposit start");
		accountBook.checkOpenMarginDeposit(transInfo);
		log.debug("AccoutBookOperation::checkOpenMarginDeposit end");			
	}	
	/**
	 * 保证金开立财务交易取消复核(保证金开立交易取消复核时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransMarginOpenInfo 保证金开立交易实体类
	 * @throws IRollbackException
	 */
	public void cancelCheckOpenMarginDeposit(TransMarginOpenInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.cancelCheckOpenMarginDeposit(transInfo);
	}
	/**
	 * 保证金支取财务交易保存(保证金支取交易保存时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransMarginDrawInfo 保证金支取交易实体类
	 * @throws IRollbackException
	 */
	public void saveWithdrawMarginDeposit(TransMarginWithdrawInfo transInfo) throws RemoteException,IRollbackException
	{	
		accountBook.saveWithdrawMarginDeposit(transInfo);	
	}
	/**
	 * 融资租赁 保证金 保后处理 （交易保存时的财务处理）
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveWithFinancialMargin(TransFinancialMarginInfo transInfo) throws RemoteException,IRollbackException
	{	
		accountBook.saveWithFinancialMargin(transInfo);	
	}
	/**
	 * 融资租赁 保证金 保后处理 （交易修改保存时的财务处理 删除）
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	
	public void deleteWithFinancialMargin(TransFinancialMarginInfo transInfo) throws RemoteException,IRollbackException
	{	
		accountBook.deleteWithFinancialMargin(transInfo);	
	}
	
	public void checkWithFinancialMargin(TransFinancialMarginInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.checkWithFinancialMargin(transInfo);		
	}	
	/**
	 * 保证金支取财务交易删除(保证金支取交易删除时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransMarginDrawInfo 保证金支取交易实体类
	 * @throws IRollbackException
	 */
	public void deleteWithdrawMarginDeposit(TransMarginWithdrawInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.deleteWithdrawMarginDeposit(transInfo);
	}	
	/**
	 * 保证金支取支取财务交易复核(保证金支取交易复核时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransMarginDrawInfo保证金支取交易实体类
	 * @throws IRollbackException
	 */
	public void checkWithdrawMarginDeposit(TransMarginWithdrawInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.checkWithdrawMarginDeposit(transInfo);		
	}	
	/**
	 * 保证金支取财务交易取消复核(保证金支取交易取消复核时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransMarginDrawInfo 保证金支取交易实体类
	 * @throws IRollbackException
	 */
	public void cancelCheckWithdrawMarginDeposit(TransMarginWithdrawInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.cancelCheckWithdrawMarginDeposit(transInfo);
	}
	
	public void cancelCheckWithFinancialMargin(TransFinancialMarginInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.cancelCheckWithFinancialMargin(transInfo);
	}
	
	/**
	 * 活期财务交易保存(活期交易保存时的财务处理，包括)
	 * 逻辑操作：
	 * 1.若交易类型为银行付款、支票付款、现金付款、票汇付款、其它付款、内部转账、委托收款、委托存款、保证金存款；或交易类型为一付多收，且付款账户ID>0（为内部转账），则：累计未复核金额（付款账户，发生额）
	 * 2.若交易类型为银行付款、支票付款、现金付款、票汇付款、其它付款、内部转账、委托收款、委托存款、保证金存款，且票据号>0，则：票据使用（票据发放银行，票据类型，票据号，付款客户ID，执行日，录入人）
	 * @param transInfo TransCurrentDepositInfo 活期交易实体类
	 * @throws IRollbackException
	 */
	public void saveCurrentAccountDetails(TransCurrentDepositInfo transInfo) throws RemoteException,IRollbackException
	{
		
			accountBook.saveCurrentAccountDetails(transInfo);
	
	}
    /**
     * 手续费收取财务交易保存(手续费交易保存时的财务处理，包括)
     * 逻辑操作：
     * @param transInfo TransCurrentDepositInfo 活期交易实体类
     * @throws IRollbackException
     */
    public void saveCommissionAccountDetails(TransCommissionInfo transInfo) throws RemoteException,IRollbackException
    {
        
            accountBook.saveCommissionAccountDetails(transInfo);
    
    }
	/**
	 * 活期财务交易保存(活期交易保存时的财务处理，包括)
	 * 逻辑操作：
	 * 1.若交易类型为银行付款、支票付款、现金付款、票汇付款、其它付款、内部转账、委托收款、委托存款、保证金存款；或交易类型为一付多收，且付款账户ID>0（为内部转账），则：累计未复核金额（付款账户，发生额）
	 * 2.若交易类型为银行付款、支票付款、现金付款、票汇付款、其它付款、内部转账、委托收款、委托存款、保证金存款，且票据号>0，则：票据使用（票据发放银行，票据类型，票据号，付款客户ID，执行日，录入人）
	 * @param transInfo TransCurrentDepositInfo 活期交易实体类
	 * @throws Exception
	 */
	public void deleteCurrentAccountDetails(TransCurrentDepositInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.deleteCurrentAccountDetails(transInfo);
	}
	/**
	 * 活期财务交易复核(活期交易复核时的财务处理，包括)
	 * 逻辑操作：
	 * 2.若交易类型为银行付款、支票付款、现金付款、票汇付款、其它付款、内部转账、委托收款、委托存款、保证金存款；或交易类型为一付多收，且付款账户ID>0（内部转账），则：活期支取（付款方账户，发生额，对方账户=收款方）
	 * 3.若交易类型为银行收款、现金收款、内部转账、委托收款、委托存款、保证金存款；或交易类型为一付多收，且收款账户ID>0（为内部转账），则：活期存入（收款方账户，发生额,对方账户=付款方）
	 * 4.通存通兑处理AccountBookBizlogic.InterbranchSettlement()
	 * 5.产生会计分录GeneralLedgerBizlogic.GenerateGLEntry(…)。如果收/付款银行账户ID>0，则本金流向lPrincipalType =2银行，否则本金流向lPrincipalType =1内部转账，分录类型lEntryType =0无关，lAccountID1=收款方账户，lAccountID2=付款方账户，dAmount1=发生额。其它略。
	 * @param transInfo TransCurrentDepositInfo 活期交易实体类
	 * @throws Exception
	 */
	public void checkCurrentAccountDetails(TransCurrentDepositInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.checkCurrentAccountDetails(transInfo);
	}
    /**
     * 手续费收取财务交易复核(手续费交易复核时的财务处理，包括)
     * 产生会计分录GeneralLedgerBizlogic.GenerateGLEntry(…)。如果收/付款银行账户ID>0，则本金流向lPrincipalType =2银行，否则本金流向lPrincipalType =1内部转账，分录类型lEntryType =0无关，lAccountID1=收款方账户，lAccountID2=付款方账户，dAmount1=发生额。其它略。
     * @param transInfo TransCurrentDepositInfo 活期交易实体类
     * @throws Exception
     */
    public void checkCommissionAccountDetails(TransCommissionInfo transInfo) throws RemoteException,IRollbackException
    {
    	accountBook.checkCommissionAccountDetails(transInfo);
    }
	/**
	 * 活期财务交易取消复核(活期交易取消复核时的财务处理，包括)
	 * 逻辑操作：
	 * 2.若交易类型为银行付款、支票付款、现金付款、票汇付款、其它付款、内部转账、委托收款、委托存款、保证金存款；或交易类型为一付多收，且付款方账户ID>0（为内部转账），则：活期支取反交易（付款方账户，发生额，对方账户=收款方）
	 * 3.若交易类型为银行收款、现金收款、内部转账、委托收款、委托存款、保证金存款；或交易类型为一付多收，且收款方账户ID>0（为内部转账），则：活期存入反交易（收款方账户，发生额，对方账户=付款方）
	 * 4.删除明细账AccountBookBizlogic.DeleteAccountDetail
	 * 5.通存通兑反交易处理AccountBookBizlogic.InterbranchSettlementReverse()
	 * 6.删除会计分录GeneralLedgerBizlogic.DeleteGLEntry(…)
	 * @param transInfo TransCurrentDepositInfo 活期交易实体类
	 * @throws Exception
	 */
	public void unCheckCurrentAccountDetails(TransCurrentDepositInfo transInfo) throws RemoteException, IRollbackException
	{
		log.debug("AccountBookOperation::unCheckCurrentAccountDetails");
		accountBook.unCheckCurrentAccountDetails(transInfo);
	}
	
	/*
	 * 定期开立、通知存款开立交易
	 * @param TransFixedOpenInfo
	 * @Exception RemoteException, IRollbackException
	 */
	 
		public void saveOpenFixedDeposit(TransFixedOpenInfo info)
		throws RemoteException, IRollbackException{
			accountBook.saveOpenFixedDeposit(info);
		}
		
		/*
		 * 保证金存款开立交易
		 * @param TransMarginOpenInfo
		 * @Exception RemoteException, IRollbackException
		 */
		 
			public void saveOpenMarginDeposit(TransMarginOpenInfo info)
			throws RemoteException, IRollbackException{
				accountBook.saveOpenMarginDeposit(info);
			}
			
		/*
		 * 融资租赁收款交易
		 * @param TransReceiveFinanceInfo
		 * @Exception RemoteException, IRollbackException
		 */
			 
		public void saveReceiveFinance(TransReceiveFinanceInfo info)
			throws RemoteException, IRollbackException
		{
					accountBook.saveReceiveFinance(info);
		}		
	
		/*
		 * 融资租赁还款交易
		 * @param TransReturnFinanceInfo
		 * @Exception RemoteException, IRollbackException
		 */
			 
		public void saveReturnFinance(TransReturnFinanceInfo info)
			throws RemoteException, IRollbackException
		{
					accountBook.saveReturnFinance(info);
		}
		
	/**
	 * 定期(通知)开立财务交易删除(保证金开立交易删除时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransFixedOpenInfo 定期(通知)开立交易实体类
	 * @throws IRollbackException
	 */
	public void deleteOpenFixedDeposit(TransFixedOpenInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.deleteOpenFixedDeposit(transInfo);
	}
	
	/**
	 * 融资租赁收款财务交易删除(融资租赁收款交易删除时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransReceiveFinanceInfo 定期(通知)开立交易实体类
	 * @throws IRollbackException
	 */
	public void deleteReceiveFinance(TransReceiveFinanceInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.deleteReceiveFinance(transInfo);
	}
	
	/**
	 * 融资租赁还款财务交易删除(融资租赁收款交易删除时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransReturnFinanceInfo 定期(通知)开立交易实体类
	 * @throws IRollbackException
	 */
	public void deleteReturnFinance(TransReturnFinanceInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.deleteReturnFinance(transInfo);
	}
	
	/**
	 * 保证金开立财务交易删除(保证金开立交易删除时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransMarginOpenInfo 保证金开立交易实体类
	 * @throws IRollbackException
	 */
	public void deleteOpenMarginDeposit(TransMarginOpenInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.deleteOpenMarginDeposit(transInfo);
	}
	
	/**
	 * 特殊业务财务交易保存(特殊业务保存时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransSpecialOperationInfo 特殊业务实体类
	 * @throws IRollbackException
	 */
	public void saveSpecialOperation(TransSpecialOperationInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.saveSpecialOperation(transInfo);
	}	
	/**
	 * 特殊业务财务交易删除(特殊业务交易删除时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransSpecialOperationInfo 特殊业务交易实体类
	 * @throws IRollbackException
	 */
	public void deleteSpecialOperation(TransSpecialOperationInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.deleteSpecialOperation(transInfo);
	}	
	/**
	 * 特殊业务财务交易复核(特殊业务交易复核时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransSpecialOperationInfo 特殊业务交易实体类
	 * @throws IRollbackException
	 */
	public void checkSpecialOperation(TransSpecialOperationInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.checkSpecialOperation(transInfo);
	}	
	/**
	 * 特殊业务财务交易取消复核(特殊业务交易取消复核时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransSpecialOperationInfo 特殊业务交易实体类
	 * @throws IRollbackException
	 */
	public void cancelCheckSpecialOperation(TransSpecialOperationInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.cancelCheckSpecialOperation(transInfo);
	}
	
	
	/**
	 * 自营贷款发放、委托贷款发放等交易保存
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveGrantLoan(TransGrantLoanInfo info) throws RemoteException,IRollbackException
	{
	
		accountBook.saveGrantLoan(info);
	
	}
	
	/**
	 * 自营贷款发放、委托贷款发放等交易删除
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */	
	public void deleteGrantLoan(TransGrantLoanInfo info) throws RemoteException,IRollbackException
	{
	
		accountBook.deleteGrantLoan(info);
	
	}
	
	/**
	 * 自营贷款发放、委托贷款发放等交易复核
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void checkGrantLoan(TransGrantLoanInfo info) throws RemoteException,IRollbackException
	{
		accountBook.checkGrantLoan(info);
	}
	
	/**
	 * 自营贷款发放、委托贷款发放等取消复核
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */	
	public void cancelCheckGrantLoan(TransGrantLoanInfo info) throws RemoteException,IRollbackException
	{
		accountBook.cancelCheckGrantLoan(info);
	}		
	
	/**
	 *贴现发放交易保存
	*/
	public void saveGrantDiscount(TransGrantDiscountInfo transInfo) throws RemoteException,IRollbackException{
		accountBook.saveGrantDiscount(transInfo);
	}
	/**
	 *贴现发放交易删除
	*/	
	public void deleteGrantDiscount(TransGrantDiscountInfo transInfo) throws RemoteException,IRollbackException{
		accountBook.deleteGrantDiscount(transInfo);
	}
	
	/**
	 *贴现发放交易复核
	*/
	public void checkGrantDiscount(TransGrantDiscountInfo transInfo) throws RemoteException,IRollbackException{
		accountBook.checkGrantDiscount(transInfo);
	}
	/**
	 *贴现发放交易取消复核
	*/	
	public void cancelCheckGrantDiscount(TransGrantDiscountInfo transInfo) throws RemoteException,IRollbackException{
		accountBook.cancelCheckGrantDiscount(transInfo);
	}		
	
	/**
	 * 贴现收回财务交易保存(贴现收回保存时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransRepaymentDiscountInfo 贴现收回实体类
	 * @throws IRollbackException
	 */
	public void saveRepaymentDiscount(TransRepaymentDiscountInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.saveRepaymentDiscount(transInfo);
	
	}
	/**
	 * 贴现收回财务交易删除(贴现收回交易删除时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransRepaymentDiscountInfo 贴现收回交易实体类
	 * @throws IRollbackException
	 */
	public void deleteRepaymentDiscount(TransRepaymentDiscountInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.deleteRepaymentDiscount(transInfo);
	}
	/**
	 * 贴现收回财务交易复核(贴现收回交易复核时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransRepaymentDiscountInfo 贴现收回交易实体类
	 * @throws IRollbackException
	 */
	public void checkRepaymentDiscount(TransRepaymentDiscountInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.checkRepaymentDiscount(transInfo);
	}	

	/**
	 * 贴现收回财务交易取消复核(贴现收回交易取消复核时的财务处理)
	 * 逻辑操作：
	 * @param transInfo TransRepaymentDiscountInfo 贴现收回交易实体类
	 * @throws IRollbackException
	 */
	public void cancelCheckRepaymentDiscount(TransRepaymentDiscountInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.cancelCheckRepaymentDiscount(transInfo);
	}	
	
	public void saveRepaymentLoan(TransRepaymentLoanInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.saveRepaymentLoan(transInfo);
	}

	public void deleteRepaymentLoan(TransRepaymentLoanInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.deleteRepaymentLoan(transInfo);
	}
	
	public void checkRepaymentLoan(TransRepaymentLoanInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.checkRepaymentLoan(transInfo);
	}
	
	public void cancelCheckRepaymentLoan(TransRepaymentLoanInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.cancelCheckRepaymentLoan(transInfo);
	}			
	/**
	 * 一付多收交易保存
	 *  逻辑操作：
	 * @param transInfo 
	 * @throws IRollbackException
	 */	
	public void saveOnePayMultiReceive(TransOnePayMultiReceiveInfo transInfo) throws RemoteException,IRollbackException{
		accountBook.saveOnePayMultiReceive(transInfo);
	}
	
	/**
	 * 一付多收交易删除
	 *  逻辑操作：
	 * @param transInfo 
	 * @throws IRollbackException
	 */	
	public void deleteOnePayMultiReceive(TransOnePayMultiReceiveInfo transInfo) throws RemoteException,IRollbackException{
		accountBook.deleteOnePayMultiReceive(transInfo);
	}
	
	/**
	 * 一付多收交易复核
	 *   逻  辑操作：
	 * @param transInfo 
	 * @throws IRollbackException
	 */	
	public void checkOnePayMultiReceive(TransOnePayMultiReceiveInfo transInfo) throws RemoteException,IRollbackException{
		accountBook.checkOnePayMultiReceive(transInfo);
	}
	
	/**
	 * 一付多收交易取消
	 *   逻辑操作：
	 * @param transInfo 
	 * @throws IRollbackException
	 */	
	public void cancelCheckOnePayMultiReceive(TransOnePayMultiReceiveInfo transInfo) throws RemoteException,IRollbackException{
		accountBook.cancelCheckOnePayMultiReceive(transInfo);		
	}
	
	/**
	 * 一付多收交易勾账
	 *   逻  辑操作：
	 * @param transInfo 
	 * @throws IRollbackException
	 */	
	public void squareOnePayMultiReceive(TransOnePayMultiReceiveInfo transInfo) throws RemoteException,IRollbackException{
		accountBook.squareOnePayMultiReceive(transInfo);
	}
	
	/**
	 * 一付多收交易取消勾账
	 *   逻辑操作：
	 * @param transInfo 
	 * @throws IRollbackException
	 */	
	public void cancelsquareCheckOnePayMultiReceive(TransOnePayMultiReceiveInfo transInfo) throws RemoteException,IRollbackException{
		accountBook.cancelsquareCheckOnePayMultiReceive(transInfo);		
	}
	
	/**
	 * 总账类保存   逻辑操作：
	 * @param transInfo 
	 * @throws IRollbackException
	 */
	public void saveGeneralLedgerOperation(TransGeneralLedgerInfo transInfo)  throws RemoteException, IRollbackException
	{
		accountBook.saveGeneralLedgerOperation(transInfo);
	}
	
	/**
	 * 总账类删除   逻辑操作：
	 * @param transInfo 
	 * @throws IRollbackException
	 */	
	public void deleteGeneralLedgerOperation(TransGeneralLedgerInfo transInfo)  throws RemoteException, IRollbackException
	{
		accountBook.deleteGeneralLedgerOperation(transInfo);
	}
	
	
	/**
	 * 总账类复核   逻辑操作：
	 * @param transInfo 
	 * @throws IRollbackException
	 */		
	public void checkGeneralLedgerOperation(TransGeneralLedgerInfo transInfo)  throws RemoteException, IRollbackException
	{
		accountBook.checkGeneralLedgerOperation(transInfo);
	}
	
	/**
	 * 总账类取消复核   逻辑操作：
	 * @param transInfo 
	 * @throws IRollbackException
	 */			
	public void cancelCheckGeneralLedgerOperation(TransGeneralLedgerInfo transInfo)  throws RemoteException, IRollbackException
	{
		accountBook.cancelCheckGeneralLedgerOperation(transInfo);
	}
	
	/**
	 *多笔贷款收回保存
	*/
	public void saveMultiLoanReceive(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException{
		accountBook.saveMultiLoanReceive(transInfo);
	}
	/**
	 *多笔贷款收回删除
	*/
	public void deleteMultiLoanReceive(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException{
		accountBook.deleteMultiLoanReceive(transInfo);
	}
	/**
	 *多笔贷款收回复核
	*/
	public void checkMultiLoanReceive(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException{
		accountBook.checkMultiLoanReceive(transInfo);
	}
	/**
	 *多笔贷款收回取消复核
	*/
	public void cancelCheckMultiLoanReceive(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException{
		accountBook.cancelCheckMultiLoanReceive(transInfo);
	}		
	/**
	 *多笔贷款收回勾账
	*/	
	public void squareMultiLoanReceive(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException{
		accountBook.squareMultiLoanReceive(transInfo); 
	}
	/**
	 *多笔贷款收回取消勾账
	*/
	public void cancelSquareMultiLoanReceive(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException{
		accountBook.cancelSquareMultiLoanReceive(transInfo);
	}	

	/**交易费用处理交易保存*/
	public void saveTransFee(TransFeeInfo transInfo)throws RemoteException, IRollbackException{
		accountBook.saveTransFee(transInfo);
	}
	
	/**交易费用处理交易删除*/
	public void deleteTransFee(TransFeeInfo transInfo)throws RemoteException, IRollbackException{
		accountBook.deleteTransFee(transInfo);
	}
	
	/**交易费用处理交易复核*/
	public void checkTransFee(TransFeeInfo transInfo)throws RemoteException, IRollbackException{
		accountBook.checkTransFee(transInfo);
	}
	
	/**交易费用处理交易取消复核*/
	public void cancelCheckTransFee(TransFeeInfo transInfo)throws RemoteException, IRollbackException{
		accountBook.cancelCheckTransFee(transInfo);
	}	
	/**
	 *转贴现发放交易复核
	*/
	public void checkTransDiscount(TransDiscountDetailInfo transInfo) throws RemoteException,IRollbackException{
		accountBook.checkTransDiscount(transInfo);
	}
	public boolean isCreateInstruction(long bankID) throws Exception
	{
		return accountBook.isCreateInstruction(bankID);
	}
	/**
	 *信贷资产转让付款交易复核
	*/
	public void checkTransferContract(TransferLoanContractInfo transInfo) throws RemoteException,IRollbackException{
		accountBook.checkTransferContract(transInfo);
	}
	/**
	 *信贷资产转让付款交易取消复核
	*/
	public void cancelCheckTransferContract(TransferLoanContractInfo transInfo) throws RemoteException,IRollbackException{
		accountBook.cancelCheckTransferContract(transInfo);
	}
	/**
	 *信贷资产转让收款交易复核
	*/
	public void repaycheckTransferContract(TransferLoanContractInfo transInfo) throws RemoteException,IRollbackException{
		accountBook.repaycheckTransferContract(transInfo);
	}
	/**
	 *信贷资产转让收款交易取消复核
	*/
	public void repaycancelCheckTransferContract(TransferLoanContractInfo transInfo) throws RemoteException,IRollbackException{
		accountBook.repaycancelCheckTransferContract(transInfo);
	}
	
	/**
	 *信贷资产转让收款交易复核(代收)
	*/
	public void repayClientcheckTransferContract(TransferLoanContractInfo transInfo) throws RemoteException,IRollbackException{
		accountBook.repayClientcheckTransferContract(transInfo);
	}
	/**
	 *信贷资产转让收款交易取消复核(代收)
	*/
	public void repayClientcancelCheckTransferContract(TransferLoanContractInfo transInfo) throws RemoteException,IRollbackException{
		accountBook.repayClientcancelCheckTransferContract(transInfo);
	}
	/**
	 * add by kevin(刘连凯)2011-07-15
	 * 内部拆借-保存操作
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveInternalLend(TransInternalLendInfo info) throws RemoteException,IRollbackException{
		accountBook.saveInternalLend(info);
	}
	/**
	 * add by kevin(刘连凯)2011-07-15
	 * 内部拆借-复核操作
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void checkInternalLend(TransInternalLendInfo info) throws RemoteException,IRollbackException{
		accountBook.checkInternalLend(info);
	}
	/**
	 * add by kevin(刘连凯)
	 * 2011-07-15
	 * 内部拆借-取消复核
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelCheckInternalLend(TransInternalLendInfo info) throws RemoteException,IRollbackException{
		accountBook.cancelCheckInternalLend(info);
	}
	/**
	 * add by kevin(刘连凯)2011-07-19
	 * 内部拆借收回-保存操作
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	
	public void saveInternalLendRepayment(TransInternalLendInfo info) throws RemoteException,IRollbackException{
		accountBook.saveInternalLendRepayment(info);
	}
	/**
	 * add by kevin(刘连凯)2011-07-19
	 * 内部拆借收回-删除操作
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void deleteInternalLendRepayment(TransInternalLendInfo info) throws RemoteException,IRollbackException{
		accountBook.deleteInternalLendRepayment(info);
	}
	/**
	 * add by kevin(刘连凯)2011-07-19
	 * 内部拆借收回-复核操作
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void checkInternalLendRepayment(TransInternalLendInfo info) throws RemoteException,IRollbackException{
		accountBook.checkInternalLendRepayment(info);
	}
	/**
	 * add by kevin(刘连凯)
	 * 2011-07-19
	 * 内部拆借收回-取消复核
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelCheckInternalLendRepayment(TransInternalLendInfo info) throws RemoteException,IRollbackException{
		accountBook.cancelCheckInternalLendRepayment(info);
	}
	/**
	 * add by 江琪2011-07-19
	 * 备付金上收-保存操作
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveBakReserveAccountDetails(TransBakReserveInfo transInfo) throws RemoteException,IRollbackException
	{
        if(transInfo.getNtransactiontypeid()==SETTConstant.TransactionType.BAKUPRECEIVE)
        {
        	accountBook.saveBakReserveAccountDetailsUpReceive(transInfo);
        }
        else if(transInfo.getNtransactiontypeid()==SETTConstant.TransactionType.BAKRETURN)
        {
        	accountBook.saveBakReserveAccountDetailsDownReturn(transInfo);
        }
        else
        {
                
                throw new RemoteException("获取的交易类型不对，导致账簿处理失败,请联系管理员.");
                
        }
		
			
	
	}
	/**
	 * add by 江琪2011-07-19
	 * 备付金上收-删除操作
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void deleteBakReserveAccountDetails(TransBakReserveInfo transInfo) throws RemoteException,IRollbackException
	{
        if(transInfo.getNtransactiontypeid()==SETTConstant.TransactionType.BAKUPRECEIVE)
        {
        	accountBook.deleteBakReserveAccountDetailsUpReceive(transInfo);
        }
        else if(transInfo.getNtransactiontypeid()==SETTConstant.TransactionType.BAKRETURN)
        {
        	accountBook.deleteBakReserveAccountDetailsDownReturn(transInfo);
        }
        else
        {
                
                throw new RemoteException("获取的交易类型不对，导致账簿处理失败,请联系管理员.");
                
        }
		
	}
	
	public void checkBakReserveDetails(TransBakReserveInfo transInfo) throws RemoteException,IRollbackException
	{
        if(transInfo.getNtransactiontypeid()==SETTConstant.TransactionType.BAKUPRECEIVE)
        {
        	accountBook.checkBakReserveDetailsUpReceive(transInfo);
        }
        else if(transInfo.getNtransactiontypeid()==SETTConstant.TransactionType.BAKRETURN)
        {
        	accountBook.checkBakReserveDetailsDownReturn(transInfo);
        }
        else
        {
                
                throw new RemoteException("获取的交易类型不对，导致账簿处理失败,请联系管理员.");
                
        }
		
	}
	public void unCheckBakReserveDetails(TransBakReserveInfo transInfo) throws RemoteException, IRollbackException
	{
        if(transInfo.getNtransactiontypeid()==SETTConstant.TransactionType.BAKUPRECEIVE)
        {
        	accountBook.unCheckBakReserveDetailsUpReceive(transInfo);
        }
        else if(transInfo.getNtransactiontypeid()==SETTConstant.TransactionType.BAKRETURN)
        {
        	accountBook.unCheckBakReserveDetailsDownReturn(transInfo);
        }
        else
        {
                
                throw new RemoteException("获取的交易类型不对，导致账簿处理失败,请联系管理员.");
                
        }
		
	}
	

	/**
	 * add by 王振 2011-07-20
	 * 准备金上收-保存操作
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveReserveAccountDetails(TransReserveInfo transInfo) throws RemoteException,IRollbackException
	{
        if(transInfo.getNtransactiontypeid()==SETTConstant.TransactionType.RESERVEUPRECEIVE)
        {
        	accountBook.saveReserveAccountDetailsUpReceive(transInfo);
        }
        else if(transInfo.getNtransactiontypeid()==SETTConstant.TransactionType.RESERVERETURN)
        {
        	accountBook.saveReserveAccountDetailsDownReturn(transInfo);
        }
        else
        {
                
                throw new RemoteException("获取的交易类型不对，导致账簿处理失败,请联系管理员.");
                
        }
			
	}
	/**
	 * add by 王振 2011-07-20
	 * 准备金上收-删除操作
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void deleteReserveAccountDetails(TransReserveInfo transInfo) throws RemoteException,IRollbackException
	{
        if(transInfo.getNtransactiontypeid()==SETTConstant.TransactionType.RESERVEUPRECEIVE)
        {
        	accountBook.deleteReserveAccountDetailsUpReceive(transInfo);
        }
        else if(transInfo.getNtransactiontypeid()==SETTConstant.TransactionType.RESERVERETURN)
        {
        	accountBook.deleteReserveAccountDetailsDownReturn(transInfo);
        }
        else
        {
                
                throw new RemoteException("获取的交易类型不对，导致账簿处理失败,请联系管理员.");
                
        }
		
	}
	public void checkReserveDetails(TransReserveInfo transInfo) throws RemoteException,IRollbackException
	{
        if(transInfo.getNtransactiontypeid()==SETTConstant.TransactionType.RESERVEUPRECEIVE)
        {
        	accountBook.checkReserveDetailsUpReceive(transInfo);
        }
        else if(transInfo.getNtransactiontypeid()==SETTConstant.TransactionType.RESERVERETURN)
        {
        	accountBook.checkReserveDetailsDownReturn(transInfo);
        }
        else
        {
                
                throw new RemoteException("获取的交易类型不对，导致账簿处理失败,请联系管理员.");
                
        }
		
	}
	public void unCheckReserveDetails(TransReserveInfo transInfo) throws RemoteException, IRollbackException
	{
        if(transInfo.getNtransactiontypeid()==SETTConstant.TransactionType.RESERVEUPRECEIVE)
        {
        	accountBook.unCheckReserveDetailsUpReceive(transInfo);
        }
        else if(transInfo.getNtransactiontypeid()==SETTConstant.TransactionType.RESERVERETURN)
        {
        	accountBook.unCheckReserveDetailsDownReturn(transInfo);
        }
        else
        {
                
                throw new RemoteException("获取的交易类型不对，导致账簿处理失败,请联系管理员.");
                
        }
		
	}
	
	/**商业票据承兑-到期承兑交易保存*/
	public void saveAcceptanceNoteAcceptance(TransAcceptanceNoteAcceptanceInfo transInfo)throws RemoteException, IRollbackException{
		accountBook.saveAcceptanceNoteAcceptance(transInfo);
	}
	
	/**商业票据承兑-到期承兑交易删除*/
	public void deleteAcceptanceNoteAcceptance(TransAcceptanceNoteAcceptanceInfo transInfo)throws RemoteException, IRollbackException{
		accountBook.deleteAcceptanceNoteAcceptance(transInfo);
	}
	
	/**商业票据承兑-到期承兑交易复核*/
	public void checkAcceptanceNoteAcceptance(TransAcceptanceNoteAcceptanceInfo transInfo)throws RemoteException, IRollbackException{
		accountBook.checkAcceptanceNoteAcceptance(transInfo);
	}
	
	/**商业票据承兑-到期承兑交易取消复核*/
	public void cancelCheckAcceptanceNoteAcceptance(TransAcceptanceNoteAcceptanceInfo transInfo)throws RemoteException, IRollbackException{
		accountBook.cancelCheckAcceptanceNoteAcceptance(transInfo);
	}
	
	/**
	 * 方正项目 : added by qhzhou 2011-03-28
	 * 同业往来复核 ,账户账簿操作
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void checkTransCraftbrother(TransCraftbrotherInfo transInfo) throws RemoteException, IRollbackException{
		accountBook.checkTransCraftbrother(transInfo);
	}
	/**
	 * 同业业务结算处理取消复核
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelCheckTransCraftbrother(TransCraftbrotherInfo transInfo) throws RemoteException, IRollbackException{
		accountBook.cancelCheckTransCraftbrother(transInfo);
	}
}
