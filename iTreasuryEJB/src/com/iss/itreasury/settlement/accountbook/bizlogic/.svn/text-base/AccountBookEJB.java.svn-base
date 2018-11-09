
/*
 * Created on 2003-8-7
 * 
 * To change the template for this generated file go to Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.accountbook.bizlogic;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;

import com.iss.itreasury.craftbrother.notice.dao.SEC_NoticeDAO;
import com.iss.itreasury.craftbrother.transferloancontract.transfercontract.bizlogic.TransferContractBiz;
import com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dataentity.TransferContractInfo;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Sett_SubAccountInfo;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.loan.attornmentapply.dao.AttornmentApplyDao;
import com.iss.itreasury.loan.attornmentapply.dataentity.AttornmentContractInfo;
import com.iss.itreasury.loan.transdiscountcontract.dataentity.TransDiscountContractBillInfo;
import com.iss.itreasury.loan.transdiscountcredence.dao.TransDiscountCredenceDAO;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.securities.notice.dataentity.NoticeInfo;
import com.iss.itreasury.securities.securitiescontract.dao.SecuritiesContractDao;
import com.iss.itreasury.securities.securitiescontract.dataentity.SecuritiesContractInfo;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.settlement.Liquidation.bizlogic.LiquitationBiz;
import com.iss.itreasury.settlement.Liquidation.dataentity.LiquidationInfo;
import com.iss.itreasury.settlement.account.bizlogic.AccountBean;
import com.iss.itreasury.settlement.account.bizlogic.AccountOperation;
import com.iss.itreasury.settlement.account.dao.Sett_AccountDAO;
import com.iss.itreasury.settlement.account.dao.Sett_AccountTypeDAO;
import com.iss.itreasury.settlement.account.dao.Sett_SubAccountDAO;
import com.iss.itreasury.settlement.account.dao.sett_TransAccountDetailDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.account.dataentity.AccountTypeInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountAssemblerInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountCurrentInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountFixedInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountLoanInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountMarginInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountReceiveFinanceInfo;
import com.iss.itreasury.settlement.account.dataentity.TransAccountDetailInfo;
import com.iss.itreasury.settlement.bankbill.bizlogic.BankBillOperation;
import com.iss.itreasury.settlement.bankinstruction.BankInstructionFactory;
import com.iss.itreasury.settlement.bankinstruction.IBankInstruction;
import com.iss.itreasury.settlement.bankinstruction.entity.CreateInstructionParam;
import com.iss.itreasury.settlement.bizdelegation.AccountTrustVoucherDelegation;
import com.iss.itreasury.settlement.bizdelegation.TransDiscountDelegation;
import com.iss.itreasury.settlement.bizdelegation.TransferLoanContractDelegation;
import com.iss.itreasury.settlement.craftbrother.dataentity.TransCraftbrotherInfo;
import com.iss.itreasury.settlement.generalledger.bizlogic.GeneralLedgerOperation;
import com.iss.itreasury.settlement.generalledger.dao.Sett_GeneralLedgerDAO;
import com.iss.itreasury.settlement.generalledger.dao.sett_GLEntryDAO;
import com.iss.itreasury.settlement.generalledger.dataentity.GLEntryInfo;
import com.iss.itreasury.settlement.generalledger.dataentity.GenerateGLEntryParam;
import com.iss.itreasury.settlement.interest.bizlogic.InterestBatch;
import com.iss.itreasury.settlement.interest.bizlogic.InterestOperation;
import com.iss.itreasury.settlement.interest.bizlogic.InterestSettlement;
import com.iss.itreasury.settlement.interest.dataentity.ClearLoanAccountInterestConditionInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestQueryResultInfo;
import com.iss.itreasury.settlement.setting.dao.Sett_BranchDAO;
import com.iss.itreasury.settlement.setting.dataentity.BranchInfo;
import com.iss.itreasury.settlement.transbakreserve.dataentity.TransBakReserveInfo;
import com.iss.itreasury.settlement.transcommission.dataentity.TransCommissionInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransOnePayMultiReceiveInfo;
import com.iss.itreasury.settlement.transdiscount.dao.Sett_TransGrantDiscountDAO;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransDiscountDetailInfo;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransDiscountSubjectInfo;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransGrantDiscountInfo;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransRepaymentDiscountInfo;
import com.iss.itreasury.settlement.transfee.dataentity.TransFeeInfo;
import com.iss.itreasury.settlement.transferloancontract.dataentity.NoticeAndAgentDetailConditionInfo;
import com.iss.itreasury.settlement.transferloancontract.dataentity.NoticeAndAgentDetialResultInfo;
import com.iss.itreasury.settlement.transferloancontract.dataentity.TransferLoanContractInfo;
import com.iss.itreasury.settlement.transfinance.dao.Sett_TransReturnFinanceDao;
import com.iss.itreasury.settlement.transfinance.dataentity.TransReceiveFinanceInfo;
import com.iss.itreasury.settlement.transfinance.dataentity.TransReturnFinanceInfo;
import com.iss.itreasury.settlement.transfinance.dataentity.TransReturnFinanceNewInfo;
import com.iss.itreasury.settlement.transfinancialmargin.dataentity.TransFinancialMarginInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedContinueInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedDrawInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedOpenInfo;
import com.iss.itreasury.settlement.transgeneralledger.dataentity.TransGeneralLedgerInfo;
import com.iss.itreasury.settlement.transinternallend.dataentity.TransInternalLendInfo;
import com.iss.itreasury.settlement.transloan.bizlogic.BankLoanQuery;
import com.iss.itreasury.settlement.transloan.dao.Sett_TransGrantLoanDAO;
import com.iss.itreasury.settlement.transloan.dataentity.BankLoanDrawInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.TransMarginOpenInfo;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.TransMarginWithdrawInfo;
import com.iss.itreasury.settlement.transnoteacceptance.dataentity.TransAcceptanceNoteAcceptanceInfo;
import com.iss.itreasury.settlement.transreserve.dataentity.TransReserveInfo;
import com.iss.itreasury.settlement.transsecurities.dataentity.TransSecuritiesInfo;
import com.iss.itreasury.settlement.transspecial.dataentity.TransSpecialOperationInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
/** 

 * @author yiwang
 * 
 * To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and
 * Comments
 */
public class AccountBookEJB implements SessionBean
{
	private javax.ejb.SessionContext ctx = null;
	final static long serialVersionUID = 3206093459760846163L;
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	private final static int TRANS_CURRENT = 0;
	private final static int TRANS_CURRENT_COMMISSION = 6;
	private final static int TRANS_FIXED = 1;
	private final static int TRANS_DEPOSIT_CURRENT_PRINCIPAL = 0;
	private final static int TRANS_DEPOIST_CURRENT_INTEREST = 1;
	private final static int TRANS_DEPOIST_CURRENT_PRINCIPALANDINTEREST = 2;
	private final static int TRANS_DEPOSIT_FIXED = 4;
	
	private final static int TRANS_DEPOSIT_MARGIN = 5;
	private final static int TRANS_DRAW_MARGIN = 6;
	
	private AccountOperation accountOperation = null;
	private BankBillOperation bbOperation = null;
	private GeneralLedgerOperation glopOperation = null;
	/**
	 * ejbActivate method comment
	 * 
	 * @exception java.rmi.RemoteException
	 *                异常说明。
	 */
	public void ejbActivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbCreate method comment
	 * 
	 * @exception javax.ejb.CreateException
	 *                异常说明。
	 * @exception java.rmi.RemoteException
	 *                异常说明。
	 */
	public void ejbCreate() throws javax.ejb.CreateException, RemoteException
	{
		try
		{
			accountOperation = new AccountOperation();
			glopOperation = new GeneralLedgerOperation();
		}
		catch (Exception e)
		{
			log.debug("---------无法初使化AccountBook及其相关子系统，交易失败-----------");
			throw new CreateException();
		}
	}
	/**
	 * ejbPassivate method comment
	 * 
	 * @exception java.rmi.RemoteException
	 *                异常说明。
	 */
	public void ejbPassivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbRemove method comment
	 * 
	 * @exception java.rmi.RemoteException
	 *                异常说明。
	 */
	public void ejbRemove() throws java.rmi.RemoteException
	{
	}
	/**
	 * getSessionContext method comment
	 * 
	 * @return javax.ejb.SessionContext
	 */
	public javax.ejb.SessionContext getSessionContext()
	{
		return ctx;
	}
	/**
	 * setSessionContext method comment
	 * 
	 * @param ctx
	 *            javax.ejb.SessionContext
	 * @exception java.rmi.RemoteException
	 *                异常说明。
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx) throws RemoteException
	{
		this.ctx = ctx;
	}
	/**
	 * 证券业务处理 交易保存(财务处理) 逻辑操作：
	 * 1.若交易类型为委托理财收款、债券承销收款，且付款方活期账户ID>0（为内部转账），则：累计未复核金额（付款账户，发生额）
	 * @author Forest, 20040531加入
	 * @param transInfo  TransSecuritiesInfo 证券业务实体类
	 * @throws java.rmi.RemoteException
	 * @throws Exception
	 * @throws IRollbackException
	 */
	public void saveSecuritiesDetails(TransSecuritiesInfo transInfo) throws RemoteException, IRollbackException
	{
		log.info(" ------开始保存证券业务的财务交易--------");
		log.info("账户:" + transInfo.getCurrentAccountID());

		//修改 by kenny(胡志强) (2007-03-19) 处理保存时校验账户状态的操作
		//如果该交易有收/付款方，且收/付款方为内部账户，则校验收/付款方账户状态
		if (transInfo.getCurrentAccountID() > 0)
		{
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(transInfo.getCurrentAccountID(),AccountBean.TRANSTYPE_WITHDRAW);
			} catch (IException e) {
				e.printStackTrace();
				// 提示“系统忙”
				//modified by mzh_fu 2007/06/01 提示准确错误信息
	           // throw new IRollbackException(null,"Gen_E001");
				throw new IRollbackException(ctx,e);
			}
		}

		//如果该交易有付款方，且付款方为内部账户，则调用“累计未复核金额”的方法
		if (transInfo.getTransactionTypeID() == SETTConstant.TransactionType.SEC_WTLC_RECEIVE || transInfo.getTransactionTypeID() == SETTConstant.TransactionType.SEC_ZQCX_RECEIVE)
		{
			if (transInfo.getCurrentAccountID() > 0)
			{
				accountOperation.addCurrentUncheckAmount(transInfo.getCurrentAccountID(), -1, transInfo.getAmount());
			}
		}
		log.info(" ------结束保存证券业务的财务交易--------");
	}
	/**
	 * 证券业务交易删除(证券业务删除时的财务处理) 逻辑操作：
	 * 1.若交易类型为委托理财收款、债券承销收款，且付款方活期账户ID>0（内部转账），则：扣除未复核金额（付款方账户，交易金额）
	 * @author Forest, 20040531加入
	 * @param transInfo  Sett_TransCurrentDepositInfo
	 * @throws java.rmi.RemoteException
	 * @throws Exception
	 * @throws IRollbackException
	 */
	public void deleteSecuritiesDetails(TransSecuritiesInfo transInfo) throws RemoteException, IRollbackException
	{
		log.info(" ------开始删除证券业务的财务交易--------");
		//如果该交易有付款方，且付款方为内部账户，则调用“扣除未复核金额”的方法
		if (transInfo.getTransactionTypeID() == SETTConstant.TransactionType.SEC_WTLC_RECEIVE || transInfo.getTransactionTypeID() == SETTConstant.TransactionType.SEC_ZQCX_RECEIVE)
		{
			if (transInfo.getCurrentAccountID() > 0)
			{
				accountOperation.subtractCurrentUncheckAmount(transInfo.getCurrentAccountID(), transInfo.getAmount());
			}
		}
		log.info(" ------结束删除证券业务的财务交易--------");
	}
	/**
	 * 证券业务交易复核(证券业务复核时的财务处理，包括) 逻辑操作：
	 * 1.若交易类型为委托理财收款、债券承销收款，且付款账户ID>0（内部转账），则：活期支取（付款方账户，发生额，对方账户=收款方）
	 * 2.若交易类型为委托理财付款、债券承销付款，且收款账户ID>0（为内部转账），则：活期存入（收款方账户，发生额,对方账户=付款方）
	 * 3.产生会计分录GeneralLedgerBizlogic.GenerateGLEntry(…)。
	 * @param transInfo TransSecuritiesInfo 证券业务实体类
	 * @throws java.rmi.RemoteException
	 * @throws Exception
	 * @throws IRollbackException
	 */
	public void checkSecuritiesDetails(TransSecuritiesInfo transInfo) throws RemoteException, IRollbackException
	{
		log.info(" ------开始复核证券业务的财务交易--------");
		long paySubAccountID = -1;
		long receiveSubAccountID = -1;
		GeneralLedgerOperation glopOperation = new GeneralLedgerOperation();//总账接口操作类
		TransAccountDetailInfo tadi = null;
		if (transInfo.getTransactionTypeID() == SETTConstant.TransactionType.SEC_WTLC_RECEIVE || transInfo.getTransactionTypeID() == SETTConstant.TransactionType.SEC_ZQCX_RECEIVE)
		{
			//调用“活期支取”的方法
			if (transInfo.getCurrentAccountID() > 0)
			{
				//得到参数处理类
				tadi = this.transferTransSecuritiesInfoToTransAccountDetailInfo(transInfo, 1);
				paySubAccountID = accountOperation.withdrawCurrent(tadi);
			}
		}
		else if (transInfo.getTransactionTypeID() == SETTConstant.TransactionType.SEC_WTLC_PAY || transInfo.getTransactionTypeID() == SETTConstant.TransactionType.SEC_ZQCX_PAY)
		{
			//如果该交易有收款方，且收款方为内部账户，则调用“活期存入”的方法
			if (transInfo.getCurrentAccountID() > 0)
			{
				//得到参数处理类
				tadi = this.transferTransSecuritiesInfoToTransAccountDetailInfo(transInfo, 2);
				receiveSubAccountID = accountOperation.depositCurrent(tadi);
			}
		}
		
		if (transInfo.getTransactionTypeID() == SETTConstant.TransactionType.SEC_WTLC_PAY
				|| transInfo.getTransactionTypeID() == SETTConstant.TransactionType.SEC_WTLC_RECEIVE
				|| transInfo.getTransactionTypeID() == SETTConstant.TransactionType.SEC_ZQCX_PAY
				|| transInfo.getTransactionTypeID() == SETTConstant.TransactionType.SEC_ZQCX_RECEIVE)
		{
			log.info(" ------开始产生会计分录--------");
			/**
			 * 产生会计分录:分录类型lEntryType =0 无关，lAccountID1=收款方账户，lAccountID2=付款方账户， dAmount1=发生额
			 */
			GenerateGLEntryParam param = new GenerateGLEntryParam();
			/**
			 * 如果收/付款银行账户ID>0，则本金 流向lPrincipalType =2银行，否则本金流向lPrincipalType =1 内部转账
			 */
			long lPrincipalType = -1;
			if (transInfo.getBankID() > 0)
			{
				//本金流向是 银行
				lPrincipalType = SETTConstant.CapitalType.BANK;
			}
			else
			{
				//本金流向是 内部转账
				lPrincipalType = SETTConstant.CapitalType.INTERNAL;
			}
			//分录类型 无关
			long lEntryType = SETTConstant.EntryType.IRRESPECTIVE;
			//收款方账户
			long receiveAccountID = receiveSubAccountID;
			//付款方账户
			long payAccountID = paySubAccountID;
			//本金开户行ID
			long principalBankID = transInfo.getBankID();
			//发生额
			double dAmount = transInfo.getAmount();
			param.setOfficeID(transInfo.getOfficeID());
			param.setCurrencyID(transInfo.getCurrencyID());
			param.setTransactionTypeID(transInfo.getTransactionTypeID());
			param.setExecuteDate(transInfo.getExecuteDate());
			param.setInterestStartDate(transInfo.getDate());
			param.setTransNo(transInfo.getTransNo());
			param.setAbstractStr(transInfo.getAbstract());
			param.setInputUserID(transInfo.getInputUserID());
			param.setCheckUserID(transInfo.getCheckUserID());
			param.setPrincipalType(lPrincipalType);
			param.setEntryType(lEntryType);
			param.setReceiveAccountID(receiveAccountID);
			param.setPayAccountID(payAccountID);
			param.setPrincipalOrTransAmount(dAmount);
			param.setPrincipalBankID(principalBankID);
			boolean res = glopOperation.generateGLEntry(param);
			if (!res)
			{
				throw new IRollbackException(ctx, "产生会计分录错误2");
			}
			log.info(" ------结束产生会计分录--------");
		}

		log.info(" ------结束复核证券业务的财务交易--------");
	}
	/**
	 * 证券业务交易取消复核(证券业务取消复核时的财务处理，包括) 逻辑操作：
	 * 1.若交易类型为委托理财收款、债券承销收款，且付款方账户ID>0（为内部转账），则：活期支取反交易（付款方账户，发生额，对方账户=收款方）
	 * 2.若交易类型为委托理财付款、债券承销付款，且收款方账户ID>0（为内部转账），则：活期存入反交易（收款方账户，发生额，对方账户=付款方）
	 * 3.删除明细账AccountBookBizlogic.DeleteAccountDetail 
	 * 4.删除会计分录GeneralLedgerBizlogic.DeleteGLEntry(…)
	 * 
	 * @param transInfo TransSecuritiesInfo 证券业务实体类
	 * @throws java.rmi.RemoteException
	 * @throws Exception
	 * @throws IRollbackException
	 */
	public void cancelCheckSecuritiesDetails(TransSecuritiesInfo transInfo) throws RemoteException, IRollbackException
	{
		log.info(" ------开始取消复核证券业务的财务交易--------");
		GeneralLedgerOperation glopOperation = new GeneralLedgerOperation();//总账接口操作类
		TransAccountDetailInfo tadi = null;
		//如果该交易有付款方，且付款方为内部账户，则调用“活期支取反交易”的方法
		if (transInfo.getTransactionTypeID() == SETTConstant.TransactionType.SEC_WTLC_RECEIVE || transInfo.getTransactionTypeID() == SETTConstant.TransactionType.SEC_ZQCX_RECEIVE)
		{
			if (transInfo.getCurrentAccountID() > 0)
			{
				//得到参数处理类
				tadi = this.transferTransSecuritiesInfoToTransAccountDetailInfo(transInfo, 1);
				accountOperation.cancelWithdrawCurrent(tadi);
			}
		}
		//如果该交易有收款方，且收款方为内部账户，则调用“活期存入反交易”的方法
		if (transInfo.getTransactionTypeID() == SETTConstant.TransactionType.SEC_WTLC_PAY || transInfo.getTransactionTypeID() == SETTConstant.TransactionType.SEC_ZQCX_PAY)
		{
			if (transInfo.getCurrentAccountID() > 0)
			{
				//得到参数处理类
				tadi = this.transferTransSecuritiesInfoToTransAccountDetailInfo(transInfo, 2);
				accountOperation.cancelDepositCurrent(tadi);
			}
		}
		//删除明细账
		accountOperation.deleteTransAccountDetail(transInfo.getTransNo());
		//删除会计分录
		glopOperation.deleteGLEntry(transInfo.getTransNo());
		log.info(" ------结束取消复核证券业务的财务交易--------");
	}
	/**
	 * 从证券交易实体类中得到活期交易财务处理实体类
	 * @author Forest, 20040531
	 * @param info  TransSecuritiesInfo 证券交易实体类
	 * @param lTypeID  类型：1,付款方账户操作时使用；2，收款方账户操作时使用
	 * @return TransAccountDetailInfo 活期财务处理参数类
	 * @throws Exception
	 */
	private TransAccountDetailInfo transferTransSecuritiesInfoToTransAccountDetailInfo(TransSecuritiesInfo info, long lTypeID)
	{
		TransAccountDetailInfo tadi = null;
		if (info != null)
		{
			tadi = new TransAccountDetailInfo();
			tadi.setId(info.getID());
			tadi.setAbstractStr(info.getAbstract());
			tadi.setAmount(info.getAmount());
			tadi.setBankChequeNo(info.getBankChequeNo());
			tadi.setBillTypeID(-1);
			tadi.setBillNo("");
			tadi.setCurrencyID(info.getCurrencyID());
			tadi.setDtExecute(info.getExecuteDate());
			tadi.setDtInterestStart(info.getDate());
			tadi.setOfficeID(info.getOfficeID());
			tadi.setTransNo(info.getTransNo());
			tadi.setTransactionTypeID(info.getTransactionTypeID());
			tadi.setStatusID(info.getStatusID());
			tadi.setAbstractID(info.getAbstractID());
			tadi.setGroup(-1);
			tadi.setTransAccountID(info.getCurrentAccountID());
			tadi.setOppAccountID(-1);
			if (lTypeID == 1)
			{
				//付款方账户操作时使用
				tadi.setTransDirection(SETTConstant.ReceiveOrPay.PAY);
			}
			else if (lTypeID == 2)
			{
				//收款方账户操作时使用
				tadi.setTransDirection(SETTConstant.ReceiveOrPay.RECEIVE);
			}
		}
		return tadi;
	}
	/**
	 * 活期财务交易保存(活期交易保存时的财务处理，包括) 逻辑操作：
	 * 1.若交易类型为银行付款、支票付款、现金付款、票汇付款、其它付款、内部转账、委托收款、委托存款、保证金存款；或交易类型为一付多收，且付款账户ID>0（为内部转账），则：累计未复核金额（付款账户，发生额）
	 * 2.若交易类型为银行付款、支票付款、现金付款、票汇付款、其它付款、内部转账、委托收款、委托存款、保证金存款，且票据号>0，则：票据使用（票据发放银行，票据类型，票据号，付款客户ID，执行日，录入人）
	 * 
	 * @param transInfo
	 *            Sett_TransCurrentDepositInfo 活期交易实体类
	 * @throws java.rmi.RemoteException
	 * @throws Exception
	 * @throws IRollbackException
	 */
	public void saveCurrentAccountDetails(TransCurrentDepositInfo transInfo) throws RemoteException, IRollbackException
	{
		log.info("AccountBookEJB:saveCurrentAccountDetails是否生成银行指令 "+ transInfo.isAutoCreateBankInstruction());
		log.info(" ------开始保存活期财务交易--------");
		//票据接口操作类
		//如果该交易使用了银行票据，则保存时，调用使用银行票据的方法
		if (transInfo.getBillBankID() != -1 && transInfo.getBillTypeID() != -1 && transInfo.getBillNo() != null && !transInfo.getBillNo().equals("") && bbOperation != null)
		{
			bbOperation.useBankBill(transInfo.getBillBankID(), transInfo.getBillTypeID(), transInfo.getBillNo(), transInfo.getPayAccountID(), transInfo.getExecuteDate(), transInfo.getInputUserID());
		}
		//如果填入了委托付款凭证号码 和密码 则调用使用的方法
		if (transInfo.getConsignVoucherNo() != null && transInfo.getConsignVoucherNo().length() > 0 && transInfo.getConsignPassword() != null && transInfo.getConsignPassword().length() > 0)
		{
			log.print("保存校验委托付款凭证号＝" + transInfo.getConsignVoucherNo() + "========================" + transInfo.getConsignPassword());
			AccountTrustVoucherDelegation atvDelegation = new AccountTrustVoucherDelegation();
			long lVoucherReturn = atvDelegation.updateStatusByUse(transInfo.getConsignVoucherNo(), transInfo.getConsignPassword(), transInfo.getPayAccountID(), transInfo.getTransNo());
			log.print("VoucherReturn=" + lVoucherReturn);
			if (lVoucherReturn <= 0)
			{
				log.print("----------委托付款凭证保存不成功-----------");
				throw new IRollbackException(ctx, "Sett_E054", transInfo.getConsignVoucherNo());
			}
		}

		log.info("付款方账户:" + transInfo.getPayAccountID());
		log.info("收款方账户:" + transInfo.getReceiveAccountID());

		/*修改 by kenny(胡志强) (2007-03-19) 处理保存时校验账户状态的操作*/
		//如果该交易有付款方，且付款方为内部账户，则校验付款方账户状态
		if (transInfo.getPayAccountID() > 0)
		{
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(transInfo.getPayAccountID(),AccountBean.TRANSTYPE_WITHDRAW);
			} catch (IException e) {
				e.printStackTrace();
				// 提示“系统忙”	
				//modified by mzh_fu 2007/06/01 提示准确错误信息
	           // throw new IRollbackException(null,"Gen_E001");
				throw new IRollbackException(ctx,e);
			}
		}
		//如果该交易有收款方，且收款方为内部账户，则校验收款方账户状态
		if (transInfo.getReceiveAccountID() > 0)
		{
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(transInfo.getReceiveAccountID(),AccountBean.TRANSTYPE_DEPOSIT);
			} catch (IException e) {
				e.printStackTrace();
				// 提示“系统忙”
				//modified by mzh_fu 2007/06/01 提示准确错误信息
	           // throw new IRollbackException(null,"Gen_E001");
				throw new IRollbackException(ctx,e);
			}
		}

		//如果该交易有付款方，且付款方为内部账户，则调用“累计未复核金额”的方法
		if (transInfo.getPayAccountID() > 0)
		{
			//如果是 银行付款－拨子账户 或 银行收款－转成员单位收款 ，则账户余额不发生变化；
            //这两种交易类型只有南航财务的项目有，其他项目不受影响。 ---2005.9.23 方桂全
            if( transInfo.getTransactionTypeID() != SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT  &&
                transInfo.getTransactionTypeID() != SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT    )
            {
                log.info(" ------这笔交易的交易类型为： " + SETTConstant.TransactionType.getName(transInfo.getTransactionTypeID()) + " 调用“累计未复核金额”的方法！ ");
                accountOperation.addCurrentUncheckAmount(transInfo.getPayAccountID(), transInfo.getReceiveAccountID(), transInfo.getAmount());
            }
            else
            {
                log.info(" ------这笔交易的交易类型为： " + SETTConstant.TransactionType.getName(transInfo.getTransactionTypeID()) + " 不调用“累计未复核金额”的方法！ ");
            }
		}
		
		//如果是通存通兑交易，增加额外的处理
		if(transInfo.getIsDifOffice()==Constant.TRUE)
		{
			//得到付方备付金账户
			long lPayBakAccountID = -1;
			lPayBakAccountID = NameRef.getBakAccountIDByOfficeID(transInfo.getPayOfficeID(),transInfo.getCurrencyID());
			if(lPayBakAccountID>0)
			{//如果有付方备付金账户
				
				//校验付方备付金账户状态
				AccountBean accountBean = new AccountBean();
				try {
					accountBean.validateAccountStatus(lPayBakAccountID,AccountBean.TRANSTYPE_WITHDRAW);
				} catch (IException e) {
					e.printStackTrace();
					// 提示“系统忙”	
					throw new IRollbackException(ctx,e);
				}	
			}
			else if(lPayBakAccountID <= 0)
			{
				//如果付款方不是总部，而付款方没有备付金账户，则不允许做此业务
				if(transInfo.getPayOfficeID()!=transInfo.getParentOfficeID())
				{
					throw new IRollbackException(ctx,"付款方不是总部，而付款方没有备付金账户，交易失败");
				}
			}
			
			//得到收方备付金账户
			long lReceiveBakAccountID = -1;
			lReceiveBakAccountID = NameRef.getBakAccountIDByOfficeID(transInfo.getReceiveOfficeID(),transInfo.getCurrencyID());
			if(lReceiveBakAccountID>0)
			{//如果有收方备付金账户
				
				//校验收方备付金账户状态
				AccountBean accountBean = new AccountBean();
				try {
					accountBean.validateAccountStatus(lReceiveBakAccountID,AccountBean.TRANSTYPE_DEPOSIT);
				} catch (IException e) {
					e.printStackTrace();
					// 提示“系统忙”	
					throw new IRollbackException(ctx,e);
				}	
			}
			else if(lReceiveBakAccountID <= 0)
			{
				//如果付款方不是总部，而付款方没有备付金账户，则不允许做此业务
				if(transInfo.getReceiveOfficeID()!=transInfo.getParentOfficeID())
				{
					throw new IRollbackException(ctx,"收款方不是总部，而收款方没有备付金账户，交易失败");
				}
			}
			
			if(lPayBakAccountID>0)
			{//如果有付方备付金账户,调用“累计未复核金额”的方法
				//如果是 银行付款－拨子账户 或 银行收款－转成员单位收款 ，则账户余额不发生变化；
	            //这两种交易类型只有南航财务的项目有，其他项目不受影响。 ---2005.9.23 方桂全
	            if( transInfo.getTransactionTypeID() != SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT  &&
	                transInfo.getTransactionTypeID() != SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT    )
	            {
	            	log.info(" ------这笔交易的交易类型为： " + SETTConstant.TransactionType.getName(transInfo.getTransactionTypeID()) + " 调用“累计未复核金额”的方法！ ");
	                accountOperation.addCurrentUncheckAmount(lPayBakAccountID, lReceiveBakAccountID, transInfo.getAmount());
	            }
	            else
	            {
	            	log.info(" ------这笔交易的交易类型为： " + SETTConstant.TransactionType.getName(transInfo.getTransactionTypeID()) + " 不调用“累计未复核金额”的方法！ ");
	            }
			}
			
			try
			{
				//开始保存清算交易
				LiquidationInfo lInfo = new LiquidationInfo();
				lInfo.setNOfficeid(transInfo.getOfficeID());
				lInfo.setNCurrencyid(transInfo.getCurrencyID());
				lInfo.setDtExecute(transInfo.getExecuteDate());
				lInfo.setDtIntereststart(transInfo.getInterestStartDate());
				lInfo.setMAmount(transInfo.getAmount());
				lInfo.setNPayaccountid(transInfo.getPayAccountID());
				lInfo.setNPayofficeid(transInfo.getPayOfficeID());
				lInfo.setNReceiveaccountid(transInfo.getReceiveAccountID());
				lInfo.setNReceiveofficeid(transInfo.getReceiveOfficeID());
				lInfo.setNTransactiontypeid(transInfo.getTransactionTypeID());
				lInfo.setSTransno(transInfo.getTransNo());
				lInfo.setSAbstract(transInfo.getAbstractStr());
				lInfo.setNParentOfficeID(transInfo.getParentOfficeID());
				LiquitationBiz lbiz = new LiquitationBiz();
				Connection conn = Database.getConnection();
				long lret = lbiz.Save(lInfo,conn);
				if(lret <= 0)
				{
					throw new IRollbackException(ctx, "产生清算交易错误");
				}
			}
			catch(Exception ee)
			{
				ee.printStackTrace();
				throw new IRollbackException(ctx, "产生清算交易错误");
			}
		}
		log.info(" ------结束保存活期财务交易--------");
		log.info("AccountBookEJB:saveCurrentAccountDetails是否生成银行指令 "+ transInfo.isAutoCreateBankInstruction());
	}
    
    /**
     *@description:手续费收取业务财务交易保存
     *void
     *@param TransCommissionInfo
     *@throws RemoteException
     *@throws IRollbackException
     */
    public void saveCommissionAccountDetails(TransCommissionInfo transInfo) throws RemoteException, IRollbackException
    {
        log.info("AccountBookEJB:saveCommissionAccountDetails ");
        log.info(" ------开始保存活期财务交易--------");
        log.info("账户:" + transInfo.getAccountId());

		/*修改 by kenny(胡志强) (2007-03-19) 处理保存时校验账户状态的操作*/
		//如果该交易有收/付款方，且收/付款方为内部账户，则校验收/付款方账户状态
		if (transInfo.getAccountId() > 0)
		{
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(transInfo.getAccountId(),AccountBean.TRANSTYPE_WITHDRAW);
			} catch (IException e) {
				e.printStackTrace();
				// 提示“系统忙”
				//modified by mzh_fu 2007/06/01 提示准确错误信息
	           // throw new IRollbackException(null,"Gen_E001");
				 throw new IRollbackException(ctx,e);
			}
		}

        //如果该交易有付款方，且付款方为内部账户，则调用“累计未复核金额”的方法
        if (transInfo.getAccountId() > 0)
        {
            accountOperation.addCurrentUncheckAmount(transInfo.getAccountId(), -1, transInfo.getCommissionAmount());
        }
        log.info(" ------结束保存活期财务交易--------");
    }

	/**
	 * 活期财务交易删除(活期交易删除时的财务处理，包括) 逻辑操作：
	 * 1.若交易类型为银行付款、支票付款、现金付款、票汇付款、其它付款、内部转账、委托收款、委托存款、保证金存款；或交易类型为一付多收，且付款账户ID>0（内部转账），则：扣除未复核金额（付款方账户，交易金额）
	 * 2.若交易类型为银行付款、支票付款、现金付款、票汇付款、其它付款、内部转账、委托收款、委托存款、保证金存款，且票据号>0，则：票据取消使用（票据发放银行，票据类型，票据号，执行日，录入人）
	 * 
	 * @param transInfo
	 *            Sett_TransCurrentDepositInfo
	 * @throws java.rmi.RemoteException
	 * @throws Exception
	 * @throws IRollbackException
	 */
	public void deleteCurrentAccountDetails(TransCurrentDepositInfo transInfo) throws RemoteException, IRollbackException
	{
		//如果该交易使用了银行票据，则删除时，调用取消使用银行票据的方法
		if (transInfo.getBillBankID() != -1 && transInfo.getBillTypeID() != -1 && transInfo.getBillNo() != null && !transInfo.getBillNo().equals("") && bbOperation != null)
		{
			try
			{
				bbOperation.cancelUseBankBill(transInfo.getBillBankID(), transInfo.getBillTypeID(), transInfo.getBillNo(), transInfo.getExecuteDate(), transInfo.getInputUserID());
			}
			catch (Exception e1)
			{
				throw new IRollbackException(ctx, e1.getMessage(), e1);
			}
		}
		//如果填入了委托付款凭证号码 和密码 则调用使用的方法
		if (transInfo.getConsignVoucherNo() != null && transInfo.getConsignVoucherNo().length() > 0 && transInfo.getConsignPassword() != null && transInfo.getConsignPassword().length() > 0)
		{
			log.print("删除校验委托付款凭证号＝" + transInfo.getTransNo() + "========================");
			AccountTrustVoucherDelegation atvDelegation = new AccountTrustVoucherDelegation();
			long lVoucherReturn = atvDelegation.updateStatusByTransNo(transInfo.getTransNo());
			log.print("VoucherReturn=" + lVoucherReturn);
			if (lVoucherReturn <= 0)
			{
				log.print("----------委托付款凭证删除不成功-----------");
				throw new IRollbackException(ctx, "Sett_E054", transInfo.getConsignVoucherNo());
			}
		}
		//如果该交易有付款方，且付款方为内部账户，则调用“扣除未复核金额”的方法
		if (transInfo.getPayAccountID() > 0)
		{
            // 如果是 银行付款－拨子账户 或 银行收款－转成员单位收款 ，则账户余额不发生变化；
            // 这两种交易类型只有南航财务的项目有，其他项目不受影响。 ---2005.9.23 方桂全
            if( transInfo.getTransactionTypeID() != SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT  &&
                transInfo.getTransactionTypeID() != SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT    )
            {
                log.info(" ------这笔交易的交易类型为： " + SETTConstant.TransactionType.getName(transInfo.getTransactionTypeID()) + " 调用“累计未复核金额”的方法！ ");
                
                accountOperation.subtractCurrentUncheckAmount(transInfo.getPayAccountID(), transInfo.getAmount());
            }
            else
            {
                log.info(" ------这笔交易的交易类型为： " + SETTConstant.TransactionType.getName(transInfo.getTransactionTypeID()) + " 不调用“累计未复核金额”的方法！ ");
            }
            
		}
		
		//如果是通存通兑交易，增加额外的处理
		if(transInfo.getIsDifOffice()==Constant.TRUE)
		{
			//得到付方备付金账户
			long lPayBakAccountID = -1;
			lPayBakAccountID = NameRef.getBakAccountIDByOfficeID(transInfo.getPayOfficeID(),transInfo.getCurrencyID());
			if(lPayBakAccountID>0)
			{
				 // 如果是 银行付款－拨子账户 或 银行收款－转成员单位收款 ，则账户余额不发生变化；
	            // 这两种交易类型只有南航财务的项目有，其他项目不受影响。 ---2005.9.23 方桂全
	            if( transInfo.getTransactionTypeID() != SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT  &&
	                transInfo.getTransactionTypeID() != SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT    )
	            {
	            	log.info(" ------这笔交易的交易类型为： " + SETTConstant.TransactionType.getName(transInfo.getTransactionTypeID()) + " 调用“累计未复核金额”的方法！ ");
	                
	                accountOperation.subtractCurrentUncheckAmount(lPayBakAccountID, transInfo.getAmount());
	            }
	            else
	            {
	            	log.info(" ------这笔交易的交易类型为： " + SETTConstant.TransactionType.getName(transInfo.getTransactionTypeID()) + " 不调用“累计未复核金额”的方法！ ");
	            }
			}
			
			try
			{
				//开始删除清算交易
				LiquidationInfo lInfo = new LiquidationInfo();
				lInfo.setSTransno(transInfo.getTransNo());
				LiquitationBiz lbiz = new LiquitationBiz();
				Connection conn = Database.getConnection();
				lbiz.Delete(lInfo,conn);
			}
			catch(Exception ee)
			{
				ee.printStackTrace();
				throw new IRollbackException(ctx, "删除清算交易错误");
			}
		}
	}
	/**
	 * 活期财务交易复核(活期交易复核时的财务处理，包括) 逻辑操作：
	 * 2.若交易类型为银行付款、支票付款、现金付款、票汇付款、其它付款、内部转账、委托收款、委托存款、保证金存款；或交易类型为一付多收，且付款账户ID>0（内部转账），则：活期支取（付款方账户，发生额，对方账户=收款方）
	 * 3.若交易类型为银行收款、现金收款、内部转账、委托收款、委托存款、保证金存款；或交易类型为一付多收，且收款账户ID>0（为内部转账），则：活期存入（收款方账户，发生额,对方账户=付款方）
	 * 4.通存通兑处理AccountBookBizlogic.InterbranchSettlement()
	 * 5.产生会计分录GeneralLedgerBizlogic.GenerateGLEntry(…)。如果收/付款银行账户ID>0，则本金流向lPrincipalType =2银行，否则本金流向lPrincipalType
	 * =1内部转账，分录类型lEntryType =0无关，lAccountID1=收款方账户，lAccountID2=付款方账户，dAmount1=发生额。其它略。
	 * 
	 * @param transInfo
	 *    Sett_TransCurrentDepositInfo 活期交易实体类
	 * @throws java.rmi.RemoteException
	 * @throws Exception
	 * @throws IRollbackException
	 */
	public void checkCurrentAccountDetails(TransCurrentDepositInfo transInfo) throws RemoteException, IRollbackException
	{
		long paySubAccountID = -1;
		long receiveSubAccountID = -1;
		long paybakSubAccountID = -1;
		long receivebakSubAccountID = -1;
		GeneralLedgerOperation glopOperation = new GeneralLedgerOperation();
		//总账接口操作类
		//如果是一付多收，单独处理
		if (transInfo.getTransactionTypeID() != SETTConstant.TransactionType.ONETOMULTI)
		{
			TransAccountDetailInfo tadi = null;
			//如果该交易有付款方，且付款方为内部账户，则调用“活期支取”的方法
			if (transInfo.getPayAccountID() > 0)
			{
				//得到参数处理类
				tadi = this.transferTransCurrentDepositInfoToTransAccountDetailInfo(transInfo, 1);
                
                // 如果是 银行付款－拨子账户 或 银行收款－转成员单位收款 ，则账户余额不发生变化；
                // 这两种交易类型只有南航财务的项目有，其他项目不受影响。 ---2005.9.23 方桂全
                if( transInfo.getTransactionTypeID() != SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT  &&
                    transInfo.getTransactionTypeID() != SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT    )
                {
                    paySubAccountID = accountOperation.withdrawCurrent(tadi);
                }
                else
                {
                    // 得到子账户ID
                    AccountBean accountBean = new AccountBean();
                    try 
                    {
                        paySubAccountID = accountBean.getCurrentSubAccoutIDByAccoutID( transInfo.getPayAccountID());
                    }
                    catch ( IException e ) 
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
			}
			//如果该交易有收款方，且收款方为内部账户，则调用“活期存入”的方法
			if (transInfo.getReceiveAccountID() > 0)
			{
				//得到参数处理类
				tadi = this.transferTransCurrentDepositInfoToTransAccountDetailInfo(transInfo, 2);
                
                // 如果是 银行付款－拨子账户 或 银行收款－转成员单位收款 ，则账户余额不发生变化；
                // 这两种交易类型只有南航财务的项目有，其他项目不受影响。 ---2005.9.23 方桂全
                if( transInfo.getTransactionTypeID() != SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT  &&
                    transInfo.getTransactionTypeID() != SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT    )
                {
                    receiveSubAccountID = accountOperation.depositCurrent(tadi);
                }
                else
                {
                    //得到子账户ID
                    AccountBean accountBean = new AccountBean();
                    try 
                    {
                        receiveSubAccountID = accountBean.getCurrentSubAccoutIDByAccoutID( transInfo.getReceiveAccountID());
                    }
                    catch ( IException e ) 
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
			}
			
			//如果是通存通兑交易，增加额外的处理
			if(transInfo.getIsDifOffice()==Constant.TRUE)
			{
				//得到付方备付金账户
				long lPayBakAccountID = -1;
				lPayBakAccountID = NameRef.getBakAccountIDByOfficeID(transInfo.getPayOfficeID(),transInfo.getCurrencyID());
				
				//得到收方备付金账户
				long lReceiveBakAccountID = -1;
				lReceiveBakAccountID = NameRef.getBakAccountIDByOfficeID(transInfo.getReceiveOfficeID(),transInfo.getCurrencyID());
				
				if(lPayBakAccountID>0)
				{
					//得到参数处理类
					tadi = this.transferTransCurrentDepositInfoToTransBakAccountDetailInfo(transInfo, 1,lPayBakAccountID,lReceiveBakAccountID);
	                
	                // 如果是 银行付款－拨子账户 或 银行收款－转成员单位收款 ，则账户余额不发生变化；
	                // 这两种交易类型只有南航财务的项目有，其他项目不受影响。 ---2005.9.23 方桂全
	                if( transInfo.getTransactionTypeID() != SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT  &&
	                    transInfo.getTransactionTypeID() != SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT    )
	                {
	                    paybakSubAccountID = accountOperation.withdrawCurrent(tadi);
	                }
	                else
	                {
	                    // 得到子账户ID
	                    AccountBean accountBean = new AccountBean();
	                    try 
	                    {
	                    	paybakSubAccountID = accountBean.getCurrentSubAccoutIDByAccoutID( lPayBakAccountID);
	                    }
	                    catch ( IException e ) 
	                    {
	                        // TODO Auto-generated catch block
	                        e.printStackTrace();
	                    }
	                }
				}
				else if(lPayBakAccountID <= 0)
				{
					//如果付款方不是总部，而付款方没有备付金账户，则不允许做此业务
					if(transInfo.getPayOfficeID()!=transInfo.getParentOfficeID())
					{
						throw new IRollbackException(ctx,"付款方不是总部，而付款方没有备付金账户，交易失败");
					}
				}
				
				if(lReceiveBakAccountID>0)
				{//如果有收方备付金账户
					//得到参数处理类
					tadi = this.transferTransCurrentDepositInfoToTransBakAccountDetailInfo(transInfo, 2,lPayBakAccountID,lReceiveBakAccountID);
	               
					// 如果是 银行付款－拨子账户 或 银行收款－转成员单位收款 ，则账户余额不发生变化；
	                // 这两种交易类型只有南航财务的项目有，其他项目不受影响。 ---2005.9.23 方桂全
	                if( transInfo.getTransactionTypeID() != SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT  &&
	                    transInfo.getTransactionTypeID() != SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT    )
	                {
	                    receivebakSubAccountID = accountOperation.depositCurrent(tadi);
	                }
	                else
	                {
	                    //得到子账户ID
	                    AccountBean accountBean = new AccountBean();
	                    try 
	                    {
	                        receivebakSubAccountID = accountBean.getCurrentSubAccoutIDByAccoutID( lReceiveBakAccountID);
	                    }
	                    catch ( IException e ) 
	                    {
	                        // TODO Auto-generated catch block
	                        e.printStackTrace();
	                    }
	                }
				}
				else if(lReceiveBakAccountID <= 0)
				{
					//如果付款方不是总部，而付款方没有备付金账户，则不允许做此业务
					if(transInfo.getReceiveOfficeID()!=transInfo.getParentOfficeID())
					{
						throw new IRollbackException(ctx,"收款方不是总部，而收款方没有备付金账户，交易失败");
					}
				}
				
				try
				{
					//开始复核清算交易
					LiquidationInfo lInfo = new LiquidationInfo();
					lInfo.setSTransno(transInfo.getTransNo());
					LiquitationBiz lbiz = new LiquitationBiz();
					Connection conn = Database.getConnection();
					lbiz.Check(lInfo,conn);
				}
				catch(Exception ee)
				{
					ee.printStackTrace();
					throw new IRollbackException(ctx, "复核清算交易错误");
				}
			}
			
			//如果是拨子账户则不生成会计分录
			if (transInfo.getTransactionTypeID() != SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT) {
				/**
				 * 产生会计分录:分录类型lEntryType =0 无关，lAccountID1=收款方账户，lAccountID2=付款方账户， dAmount1=发生额
				 */
				GenerateGLEntryParam param = new GenerateGLEntryParam();
				/**
				 * 如果收/付款银行账户ID>0，则本金 流向lPrincipalType =2银行，否则本金流向lPrincipalType =1 内部转账
				 */
				long lPrincipalType = -1;
				if (transInfo.getBankID() > 0)
				{
					//本金流向是 银行
					lPrincipalType = SETTConstant.CapitalType.BANK;
				}
				else
				{
					//本金流向是 内部转账
					lPrincipalType = SETTConstant.CapitalType.INTERNAL;
				}
				//分录类型 无关
				long lEntryType = SETTConstant.EntryType.IRRESPECTIVE;
				//收款方账户
				long receiveAccountID = receiveSubAccountID;
				//付款方账户
				long payAccountID = paySubAccountID;
				//本金开户行ID
				long principalBankID = transInfo.getBankID();
				//发生额
				double dAmount = transInfo.getAmount();
				param.setOfficeID(transInfo.getOfficeID());
				param.setCurrencyID(transInfo.getCurrencyID());
				param.setTransactionTypeID(transInfo.getTransactionTypeID());
				param.setExecuteDate(transInfo.getExecuteDate());
				param.setInterestStartDate(transInfo.getInterestStartDate());
				param.setTransNo(transInfo.getTransNo());
				param.setAbstractStr(transInfo.getAbstractStr());
				param.setInputUserID(transInfo.getInputUserID());
				param.setCheckUserID(transInfo.getCheckUserID());
				param.setPrincipalType(lPrincipalType);
				param.setEntryType(lEntryType);
				param.setReceiveAccountID(receiveAccountID);
				param.setPayAccountID(payAccountID);
				param.setPrincipalOrTransAmount(dAmount);
				param.setPrincipalBankID(principalBankID);
				/*为通存通兑增加*/
				if(transInfo.getIsDifOffice()==Constant.TRUE)
				{//如果是通存通兑业务
					param.setPayofficeID(transInfo.getPayOfficeID());
					param.setReceiveofficeID(transInfo.getReceiveOfficeID());
					param.setParentofficeID(transInfo.getParentOfficeID());
					param.setSubTransactionType(SETTConstant.SubTransactionType.DIFOFFICE);
					param.setPayBakAccountID(paybakSubAccountID);
					param.setReceiveBakAccountID(receivebakSubAccountID);
				}
				else
				{//
					if (transInfo.getTransactionTypeID() == SETTConstant.TransactionType.INTERNALVIREMENT)
					{//如果是非通存通兑的内部转账业务，则需要取正常的科目设置
						param.setSubTransactionType(SETTConstant.SubTransactionType.NORMAL);
					}
				}
				
				log.print("----------ACCOUNTBOOKEJB:checkCurrentAccountDetails是否生成银行指令:"+ transInfo.isAutoCreateBankInstruction() +"-----------");
				param.setAutoCreateBankInstruction(transInfo.isAutoCreateBankInstruction());
				boolean res = glopOperation.generateGLEntry(param);
				if (!res)
				{
					throw new IRollbackException(ctx, "产生会计分录错误2");
				}
			}
			else if (transInfo.getTransactionTypeID() == SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT)
			{//如果是下拨子账户交易，则判断是否生成会计分录
				if(Config.getBoolean(ConfigConstant.SETTLEMENT_CREATESUBJECT_PAYSUBACCOUNT,true))
				{
					/**
					 * 产生会计分录:分录类型lEntryType =0 无关，lAccountID1=收款方账户，lAccountID2=付款方账户， dAmount1=发生额
					 */
					GenerateGLEntryParam param = new GenerateGLEntryParam();
					/**
					 * 如果收/付款银行账户ID>0，则本金 流向lPrincipalType =2银行，否则本金流向lPrincipalType =1 内部转账
					 */
					long lPrincipalType = -1;
					if (transInfo.getBankID() > 0)
					{
						//本金流向是 银行
						lPrincipalType = SETTConstant.CapitalType.BANK;
					}
					else
					{
						//本金流向是 内部转账
						lPrincipalType = SETTConstant.CapitalType.INTERNAL;
					}
					//分录类型 无关
					long lEntryType = SETTConstant.EntryType.IRRESPECTIVE;
					//收款方账户
					long receiveAccountID = paySubAccountID;//由于是拿银行付款改造，因此，收款方账户用的是银行付款的付款账户。
					//付款方账户
					long payAccountID = paySubAccountID;
					//本金开户行ID
					long principalBankID = transInfo.getBankID();
					//发生额
					double dAmount = transInfo.getAmount();
					param.setOfficeID(transInfo.getOfficeID());
					param.setCurrencyID(transInfo.getCurrencyID());
					param.setTransactionTypeID(transInfo.getTransactionTypeID());
					param.setExecuteDate(transInfo.getExecuteDate());
					param.setInterestStartDate(transInfo.getInterestStartDate());
					param.setTransNo(transInfo.getTransNo());
					param.setAbstractStr(transInfo.getAbstractStr());
					param.setInputUserID(transInfo.getInputUserID());
					param.setCheckUserID(transInfo.getCheckUserID());
					param.setPrincipalType(lPrincipalType);
					param.setEntryType(lEntryType);
					param.setReceiveAccountID(receiveAccountID);
					param.setPayAccountID(payAccountID);
					param.setPrincipalOrTransAmount(dAmount);
					param.setPrincipalBankID(principalBankID);
					
					param.setSubTransactionType(SETTConstant.SubTransactionType.NORMAL);
					
					log.print("----------ACCOUNTBOOKEJB:checkCurrentAccountDetails是否生成银行指令:"+ transInfo.isAutoCreateBankInstruction() +"-----------");
					param.setAutoCreateBankInstruction(transInfo.isAutoCreateBankInstruction());
					boolean res = glopOperation.generateGLEntry(param);
					if (!res)
					{
						throw new IRollbackException(ctx, "产生会计分录错误2");
					}
				}
			}
			
		}
		else
		{
			//TODO
		}
	}
    

     /**
     *@description:
     * 活期财务交易复核(活期交易复核时的财务处理，包括) 逻辑操作：
     * 手续费收取业务  
     * 产生会计分录GeneralLedgerBizlogic.GenerateGLEntry(…)。如果收/付款银行账户ID>0，则本金流向lPrincipalType =2银行，否则本金流向lPrincipalType
     * =1内部转账，分录类型lEntryType =0无关，lAccountID1=收款方账户，lAccountID2=付款方账户，dAmount1=发生额。其它略。
     *void
     *@param TransCommissionInfo
     *@throws RemoteException
     *@throws IRollbackException
     */
    public void checkCommissionAccountDetails(TransCommissionInfo transInfo) throws RemoteException, IRollbackException
    {
        long paySubAccountID = -1;
        long receiveSubAccountID = -1;
        GeneralLedgerOperation glopOperation = new GeneralLedgerOperation();
        //总账接口操作类
        
        TransAccountDetailInfo tadi = null;
        //如果该交易有付款方，且付款方为内部账户，则调用“活期支取”的方法
        if (transInfo.getAccountId() > 0)
        {
            //得到参数处理类
            tadi = this.transferTransCommissionInfoToTransAccountDetailInfo(transInfo, 1);
            System.out.println("  in checkCommissionAccountDetails  tadi.getTransAccountID() = "+tadi.getTransAccountID());
            System.out.println("  in checkCommissionAccountDetails  tadi.getTransactionTypeID() = "+tadi.getTransactionTypeID());
            paySubAccountID = accountOperation.withdrawCurrent(tadi);
            log.debug("11111111111111111111111 paySubAccountID = "+paySubAccountID);
        }
       
        
        //T产生会计分录
        /**
         * 产生会计分录:分录类型lEntryType =0 无关，lAccountID1=收款方账户，lAccountID2=付款方账户， dAmount1=发生额
         */
        GenerateGLEntryParam param = new GenerateGLEntryParam();
        /**
         * 如果收/付款银行账户ID>0，则本金 流向lPrincipalType =2银行，否则本金流向lPrincipalType =1 内部转账
         */
        long lPrincipalType = -1;
        
        //分录类型 无关
        long lEntryType = SETTConstant.EntryType.IRRESPECTIVE;
        //收款方账户
        long receiveAccountID = -1;
        //付款方账户
        long payAccountID = paySubAccountID;
        //本金开户行ID
        long principalBankID = -1;
        //发生额
        double dAmount = transInfo.getCommissionAmount();
        param.setOfficeID(transInfo.getOfficeId());
        param.setCurrencyID(transInfo.getCurrencyId());
        param.setTransactionTypeID(SETTConstant.TransactionType.COMMISSION);
        param.setExecuteDate(transInfo.getExecuteDate());
        param.setInterestStartDate(transInfo.getInterestStartDate());
        param.setTransNo(transInfo.getTransNo());
        param.setAbstractStr("手续费收取");
        param.setInputUserID(transInfo.getInputUserId());
        param.setCheckUserID(transInfo.getInputUserId());
        param.setPrincipalType(lPrincipalType);
        param.setEntryType(lEntryType);
        param.setReceiveAccountID(receiveAccountID);
        param.setPayAccountID(payAccountID);
        param.setPrincipalOrTransAmount(dAmount);
        param.setPrincipalBankID(principalBankID);
        //log.print("----------ACCOUNTBOOKEJB:checkCurrentAccountDetails是否生成银行指令:"+ transInfo.isAutoCreateBankInstruction() +"-----------");
        //param.setAutoCreateBankInstruction(transInfo.isAutoCreateBankInstruction());
        boolean res = glopOperation.generateGLEntry(param);
        if (!res)
        {
            throw new IRollbackException(ctx, "产生会计分录错误2");
        }
        
    }

	/**
	 * 活期财务交易取消复核(活期交易取消复核时的财务处理，包括) 逻辑操作：
	 * 2.若交易类型为银行付款、支票付款、现金付款、票汇付款、其它付款、内部转账、委托收款、委托存款、保证金存款；或交易类型为一付多收，且付款方账户ID>0（为内部转账），则：活期支取反交易（付款方账户，发生额，对方账户=收款方）
	 * 3.若交易类型为银行收款、现金收款、内部转账、委托收款、委托存款、保证金存款；或交易类型为一付多收，且收款方账户ID>0（为内部转账），则：活期存入反交易（收款方账户，发生额，对方账户=付款方）
	 * 4.删除明细账AccountBookBizlogic.DeleteAccountDetail 5.通存通兑反交易处理AccountBookBizlogic.InterbranchSettlementReverse()
	 * 6.删除会计分录GeneralLedgerBizlogic.DeleteGLEntry(…)
	 * 
	 * @param transInfo
	 *            Sett_TransCurrentDepositInfo 活期交易实体类
	 * @throws java.rmi.RemoteException
	 * @throws Exception
	 * @throws IRollbackException
	 */
	public void unCheckCurrentAccountDetails(TransCurrentDepositInfo transInfo) throws RemoteException, IRollbackException
	{
		GeneralLedgerOperation glopOperation = new GeneralLedgerOperation();
		//总账接口操作类
		//如果是一付多收，单独处理
		if (transInfo.getTransactionTypeID() != SETTConstant.TransactionType.ONETOMULTI)
		{
			TransAccountDetailInfo tadi = null;
			//如果该交易有付款方，且付款方为内部账户，则调用“活期支取反交易”的方法
			if (transInfo.getPayAccountID() > 0)
			{
				//得到参数处理类
				tadi = transferTransCurrentDepositInfoToTransAccountDetailInfo(transInfo, 1);
                
                // 如果是 银行付款－拨子账户 或 银行收款－转成员单位收款 ，则账户余额不发生变化；
                // 这两种交易类型只有南航财务的项目有，其他项目不受影响。 ---2005.9.23 方桂全
                if( transInfo.getTransactionTypeID() != SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT  &&
                    transInfo.getTransactionTypeID() != SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT    )
                {
                    accountOperation.cancelWithdrawCurrent(tadi);
                }
                else
                {
                    System.out.println("-----------交易取消复核，不调用cancelWithdrawCurrent()方法-----------");
                }
			}
			//如果该交易有收款方，且收款方为内部账户，则调用“活期存入反交易”的方法
			if (transInfo.getReceiveAccountID() > 0)
			{
				//得到参数处理类
				tadi = this.transferTransCurrentDepositInfoToTransAccountDetailInfo(transInfo, 2);
                
                // 如果是 银行付款－拨子账户 或 银行收款－转成员单位收款 ，则账户余额不发生变化；
                // 这两种交易类型只有南航财务的项目有，其他项目不受影响。 ---2005.9.23 方桂全
                if( transInfo.getTransactionTypeID() != SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT  &&
                    transInfo.getTransactionTypeID() != SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT    )
                {
                    accountOperation.cancelDepositCurrent(tadi);
                }
                else
                {
                    System.out.println("-----------交易取消复核，不调用cancelDepositCurrent()方法-----------");
                }
			}
			//如果是通存通兑交易，增加额外的处理
			if(transInfo.getIsDifOffice()==Constant.TRUE)
			{
				//得到付方备付金账户
				long lPayBakAccountID = -1;
				lPayBakAccountID = NameRef.getBakAccountIDByOfficeID(transInfo.getPayOfficeID(),transInfo.getCurrencyID());
				
				//得到收方备付金账户
				long lReceiveBakAccountID = -1;
				lReceiveBakAccountID = NameRef.getBakAccountIDByOfficeID(transInfo.getReceiveOfficeID(),transInfo.getCurrencyID());
				
				if(lPayBakAccountID>0)
				{
					//得到参数处理类
					tadi = transferTransCurrentDepositInfoToTransBakAccountDetailInfo(transInfo, 1,lPayBakAccountID,lReceiveBakAccountID);
	                
	                // 如果是 银行付款－拨子账户 或 银行收款－转成员单位收款 ，则账户余额不发生变化；
	                // 这两种交易类型只有南航财务的项目有，其他项目不受影响。 ---2005.9.23 方桂全
	                if( transInfo.getTransactionTypeID() != SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT  &&
	                    transInfo.getTransactionTypeID() != SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT    )
	                {
	                    accountOperation.cancelWithdrawCurrent(tadi);
	                }
	                else
	                {
	                    System.out.println("-----------交易取消复核，不调用cancelWithdrawCurrent()方法-----------");
	                }
					
				}
				
				if(lReceiveBakAccountID>0)
				{
					//得到参数处理类
					tadi = transferTransCurrentDepositInfoToTransBakAccountDetailInfo(transInfo, 2,lPayBakAccountID,lReceiveBakAccountID);
	                
	                // 如果是 银行付款－拨子账户 或 银行收款－转成员单位收款 ，则账户余额不发生变化；
	                // 这两种交易类型只有南航财务的项目有，其他项目不受影响。 ---2005.9.23 方桂全
	                if( transInfo.getTransactionTypeID() != SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT  &&
	                    transInfo.getTransactionTypeID() != SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT    )
	                {
	                    accountOperation.cancelDepositCurrent(tadi);
	                }
	                else
	                {
	                    System.out.println("-----------交易取消复核，不调用cancelDepositCurrent()方法-----------");
	                }
					
				}
				
				try
				{
					//开始取消复核清算交易
					LiquidationInfo lInfo = new LiquidationInfo();
					lInfo.setSTransno(transInfo.getTransNo());
					LiquitationBiz lbiz = new LiquitationBiz();
					Connection conn = Database.getConnection();
					lbiz.CancelCheck(lInfo,conn);
				}
				catch(Exception ee)
				{
					ee.printStackTrace();
					throw new IRollbackException(ctx, "取消复核清算交易错误");
				}
			}
			
			//删除明细账
			accountOperation.deleteTransAccountDetail(transInfo.getTransNo());
			//TODO:待确定4.通存通兑反交易处理
			//删除会计分录
			glopOperation.deleteGLEntry(transInfo.getTransNo());
		}
		else
		{
			//TODO:待确定
		}
	}
	/*
	 * 定期开立、通知存款开立交易 @param TransFixedOpenInfo @Exception RemoteException, IRollbackException
	 */
	public void saveOpenFixedDeposit(TransFixedOpenInfo info) throws RemoteException, IRollbackException
	{
		long lReturn = 1;
		//账户接口操作类
		log.info("存款来源账户ID:" + info.getCurrentAccountID());

		//修改 by kenny(胡志强) (2007-03-19) 处理保存时校验账户状态的操作
		//如果该交易有收/付款方，且收/付款方为内部账户，则校验收/付款方账户状态
		if (info.getCurrentAccountID() > 0)
		{
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(info.getCurrentAccountID(),AccountBean.TRANSTYPE_WITHDRAW);
			} catch (IException e) {
				e.printStackTrace();
				// 提示“系统忙”
				//modified by mzh_fu 2007/06/01 提示准确错误信息
	           // throw new IRollbackException(null,"Gen_E001");
				 throw new IRollbackException(ctx,e);
			}
		}

		//若存款来源为活期存款即CurrentAccountID>0，则：累计未复核金额
		if (info.getCurrentAccountID() > 0)
		{
			log.debug("---------活期子账户大于0,存款来源为活期存款，开始累计未复核金额-----------");
			accountOperation.addCurrentUncheckAmount(info.getCurrentAccountID(), info.getAccountID(), info.getAmount());
			log.debug("---------累计未复核金额完成-----------");
			//活期账户票据使用
			if (info.getBillBankID() > 0 && bbOperation != null)
			{
				log.debug("---------开始活期账户票据使用-----------");
				bbOperation.useBankBill(info.getBillBankID(), info.getBillTypeID(), info.getBillNo(), info.getCurrentAccountID(), info.getExecuteDate(), info.getInputUserID());
				log.debug("---------结束活期账户票据使用-----------");
			}
		}
		//交易类型：定期存款开立
		if (info.getCertificationBankID() > 0 && bbOperation != null)
		{
			if (info.getTransactionTypeID() == SETTConstant.TransactionType.OPENFIXEDDEPOSIT)
			{
				log.debug("---------开始定期存款开立票据使用-----------");
				bbOperation.useBankBill(info.getCertificationBankID(), SETTConstant.BankBillType.FIXED_DEPOSIT_AUTHENTICATION, info.getDepositNo(), info.getClientID(), info.getExecuteDate(), info
						.getInputUserID());
				log.debug("---------结束定期存款开立票据使用-----------");
			}
			//交易类型：通知存款开立
			else if (info.getTransactionTypeID() == SETTConstant.TransactionType.OPENNOTIFYACCOUNT)
			{
				log.debug("---------开始通知存款开立票据使用-----------");
				bbOperation.useBankBill(info.getCertificationBankID(), SETTConstant.BankBillType.NOTIFY_DEPOSIT_AUTHENTICATION, info.getDepositNo(), info.getClientID(), info.getExecuteDate(), info
						.getInputUserID());
				log.debug("---------结束通知存款开立票据使用-----------");
			}
		}
		log.debug("---------完成saveOpenFixedDeposit-----------");
	}
	/*
	 * 定期开立、通知存款开立删除 @param TransFixedOpenInfo @Exception RemoteException, IRollbackException
	 */
	public void deleteOpenFixedDeposit(TransFixedOpenInfo info) throws RemoteException, IRollbackException
	{
		//若存款来源为活期存款即CurrentAccountID>0，则：取消累计未复核金额
		if (info.getCurrentAccountID() > 0)
		{
			log.debug("---------活期子账户大于0,存款来源为活期存款，开始扣除累计未复核金额-----------");
			accountOperation.subtractCurrentUncheckAmount(info.getCurrentAccountID(), info.getAmount());
			log.debug("---------结束扣除累计未复核金额-----------");
			//活期账户票据使用
			if (info.getBillBankID() > 0 && bbOperation != null)
			{
				log.debug("---------开始取消活期账户票据使用-----------");
				bbOperation.cancelUseBankBill(info.getBillBankID(), info.getBillTypeID(), info.getBillNo(), info.getExecuteDate(), info.getInputUserID());
				log.debug("---------结束取消活期账户票据使用-----------");
			}
		}
		//交易类型：定期存款开立
		if (info.getCertificationBankID() > 0 && bbOperation != null)
		{
			if (info.getTransactionTypeID() == SETTConstant.TransactionType.OPENFIXEDDEPOSIT)
			{
				log.debug("---------开始取消定期存款开立票据使用-----------");
				log.debug("----------存单号:" + info.getDepositNo());
				bbOperation.cancelUseBankBill(info.getCertificationBankID(), SETTConstant.BankBillType.FIXED_DEPOSIT_AUTHENTICATION, info.getDepositNo(), info.getExecuteDate(), info.getInputUserID());
				log.debug("---------结束取消定期存款开立票据使用-----------");
			}
			//交易类型：通知存款开立
			else if (info.getTransactionTypeID() == SETTConstant.TransactionType.OPENNOTIFYACCOUNT)
			{
				log.debug("---------开始取消通知存款开立票据使用-----------");
				bbOperation
						.cancelUseBankBill(info.getCertificationBankID(), SETTConstant.BankBillType.NOTIFY_DEPOSIT_AUTHENTICATION, info.getDepositNo(), info.getExecuteDate(), info.getInputUserID());
				log.debug("---------结束取消通知存款开立票据使用-----------");
			}
		}
		if (info.getSealBankID() > 0 && bbOperation != null)
		{ //大桥需求 票据使用
			log.debug("---------大桥：开始取消印鉴卡票据使用-----------");
			bbOperation.cancelUseBankBill(info.getSealBankID(), SETTConstant.BankBillType.SEAL_CARD, info.getSealNo(), info.getExecuteDate(), info.getInputUserID());
			log.debug("---------大桥：结束取消印鉴卡票据使用-----------");
		}
		log.debug("---------结束deleteOpenFixedDeposit-----------");
	}
	/**
	 * 定期开立、通知存款开立交易复核
	 */
	public void checkOpenFixedDeposit(TransFixedOpenInfo transInfo) throws RemoteException, IRollbackException
	{
		long currentSubAccountID = -1;
		long fixedSubAccountID = -1;
		TransAccountDetailInfo currentTadi = null;
		TransAccountDetailInfo fixedTadi = null;
		log.debug("---------开始checkOpenFixedDeposit-----------");
		//若存款来源为活期存款,返回值=活期子账户ID
		if (transInfo.getCurrentAccountID() > 0)
		{
			log.debug("---------活期子账户大于0,存款来源为活期存款，开始从活期账户余额中扣钱：withdrawCurrent-----------");
			currentTadi = transferTransFixedOpenInfoToTransAccountDetailInfo(transInfo, TRANS_CURRENT);
			currentSubAccountID = accountOperation.withdrawCurrent(currentTadi);
			log.debug("---------结束从活期账户余额中扣钱：withdrawCurrent-----------");
		}
		//定期开空户（定期信息），返回值=定期子账户ID
		log.debug("---------开始开立定期子账户-----------");
		log.debug(UtilOperation.dataentityToString(transInfo));
		SubAccountFixedInfo safi = this.transferTransFixedOpenInfoToSubAccountFixedInfo(transInfo);
		UtilOperation.dataentityToString(safi);
		fixedSubAccountID = accountOperation.openFixSubAccount(safi);
		log.debug("---------新开立的定期子账户ID: " + fixedSubAccountID + "---------");
		log.debug("---------结束开立定期子账户-----------");
		//定期存入（定期账户ID，定期子账户ID，对方账户=CurrentAccountID）
		log.debug("---------开始开立定期存入depositFix-----------");
		fixedTadi = transferTransFixedOpenInfoToTransAccountDetailInfo(transInfo, TRANS_FIXED);
		fixedTadi.setTransSubAccountID(fixedSubAccountID);

		accountOperation.depositFix(fixedTadi);
		log.debug("---------结束开立定期存入depositFix-----------");
		//通存通兑处理InterbranchSettlement()
		//@TBD
		/**
		 * 产生会计分录:分录类型lEntryType =0 无关，lAccountID1=收款方账户，lAccountID2=付款方账户， dAmount1=发生额
		 */
		log.debug("---------开始产生会计分录-----------");
		GenerateGLEntryParam param = new GenerateGLEntryParam();
		/**
		 * 如果收/付款银行账户ID>0，则本金 流向lPrincipalType =2银行，否则本金流向lPrincipalType =1 
		 */
		long lPrincipalType = -1;
		if (transInfo.getBankID() > 0)
		{
			//本金流向是 银行
			lPrincipalType = SETTConstant.CapitalType.BANK;
		}
		else
		//if(info.getCurrentAccount()>0)
		{
			//本金流向是 内部转账
			lPrincipalType = SETTConstant.CapitalType.INTERNAL;
		}
		//分录类型 无关
		long lEntryType = SETTConstant.EntryType.IRRESPECTIVE;
		//收款方账户是定期子账户
		long receiveAccountID = fixedSubAccountID;
		//付款方账户是活期子账户
		long payAccountID = currentSubAccountID;
		//本金开户行ID
		long principalBankID = transInfo.getBankID();
		//发生额
		double dAmount = transInfo.getAmount();
		param.setOfficeID(transInfo.getOfficeID());
		param.setCurrencyID(transInfo.getCurrencyID());
		param.setTransactionTypeID(transInfo.getTransactionTypeID());
		param.setExecuteDate(transInfo.getExecuteDate());
		param.setInterestStartDate(transInfo.getInterestStartDate());
		param.setTransNo(transInfo.getTransNo());
		param.setAbstractStr(transInfo.getAbstract());
		param.setInputUserID(transInfo.getInputUserID());
		param.setCheckUserID(transInfo.getCheckUserID());
		param.setPrincipalType(lPrincipalType);
		param.setEntryType(lEntryType);
		param.setReceiveAccountID(receiveAccountID);
		param.setPayAccountID(payAccountID);
		param.setPrincipalOrTransAmount(dAmount);
		param.setPrincipalBankID(principalBankID);
		log.debug("--------产生会计分录参数类:-------------");
		log.debug(UtilOperation.dataentityToString(param));
		boolean res = glopOperation.generateGLEntry(param);
		if (!res)
		{
			throw new IRollbackException(ctx, "产生会计分录错误2");
		}
		else
		{
			log.debug("-------会计分录产生成功，复核结束---------------");
		}
		
		
}		
	
	/**
	 * 定期开立、通知存款开立交易取消
	 */
	public void cancelCheckOpenFixedDeposit(TransFixedOpenInfo info) throws RemoteException, IRollbackException
	{
		long currentSubAccountID = -1;
		long fixedSubAccountID = -1;
		TransAccountDetailInfo currentTadi = null;
		TransAccountDetailInfo fixedTadi = null;
		log.debug("---------开始cancelCheckOpenFixedDeposit-----------");
		//若存款来源为活期存款,反交易
		if (info.getCurrentAccountID() > 0)
		{
			log.debug("---------活期子账户大于0,存款来源为活期存款，开始活期账户交易：cancelWithdrawCurrent-----------");
			currentTadi = transferTransFixedOpenInfoToTransAccountDetailInfo(info, TRANS_CURRENT);
			accountOperation.cancelWithdrawCurrent(currentTadi);
			log.debug("---------结束活期账户反交易：cancelWithdrawCurrent-----------");
		}
		//定期存入反交易
		log.debug("---------开始定期存入反交易：cancelDepositFix-----------");
		fixedTadi = transferTransFixedOpenInfoToTransAccountDetailInfo(info, TRANS_FIXED);
		//fixedTadi.setTransSubAccountID(fixedSubAccountID);
		long cancelDepositSubFixedAccount = accountOperation.cancelDepositFix(fixedTadi);
		log.debug("---------结束定期存入反交易：cancelDepositFix-----------");
		//定期开空户反交易
		log.debug("---------开始定期开立空户反交易：deleteFixSubAccount-----------");
		accountOperation.deleteFixSubAccount(cancelDepositSubFixedAccount);
		log.debug("---------结束定期开立空户反交易：deleteFixSubAccount-----------");
		//通存通兑处理InterbranchSettlement()
		//@TBD
		//删除明细账
		accountOperation.deleteTransAccountDetail(info.getTransNo());
		glopOperation.deleteGLEntry(info.getTransNo());
		log.debug("---------结束cancelCheckOpenFixedDeposit-----------");
	}
	/**
	 * 定期支取、通知存款支取交易保存
	 */
	public void saveWithdrawFixedDeposit(TransFixedDrawInfo info) throws RemoteException, IRollbackException
	{
		//交易金额
		double transAmount = 0.0;
		long billTypeID = -1;
		long bankID = -1;
		String billNo = "";
		if (info.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)
		{
			if(Config.getBoolean(ConfigConstant.SETT_FIXEDDRAW_CREATENEWBOOK,true))
			{
				//定期转活期（定期支取）
				transAmount = info.getAmount();
			}
			else
			{
				transAmount = info.getDrawAmount();
			}
			billTypeID = SETTConstant.BankBillType.FIXED_DEPOSIT_AUTHENTICATION;
		}
		else if (info.getTransactionTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
		{ //通知存款支取
			transAmount = info.getDrawAmount();
			billTypeID = SETTConstant.BankBillType.NOTIFY_DEPOSIT_AUTHENTICATION;
		}
		if (info.getCertificationBankID() > 0 && bbOperation != null) //票据终止
			bbOperation.terminateBankBill(info.getCertificationBankID(), billTypeID, info.getDepositNo());
		if (info.getSealBankID() > 0 && bbOperation != null)
		{
			if (((info.getTransactionTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW) && info.getDepositBalance() == 0)
					|| info.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)
			{
				//票据终止
				bbOperation.terminateBankBill(info.getSealBankID(), SETTConstant.BankBillType.SEAL_CARD, info.getSealNo());
			}
		}
		log.info("活期账户ID:" + info.getAccountID());

		//修改 by kenny(胡志强) (2007-03-19) 处理保存时校验账户状态的操作
		//如果该交易有收/付款方，且收/付款方为内部账户，则校验收/付款方账户状态
		if (info.getAccountID() > 0)
		{
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(info.getAccountID(),AccountBean.TRANSTYPE_WITHDRAW);
			} catch (IException e) {
				e.printStackTrace();
				// 提示“系统忙”
				//modified by mzh_fu 2007/06/01 提示准确错误信息
		        // throw new IRollbackException(null,"Gen_E001");
				throw new IRollbackException(ctx,e);
			}
		}

		//累计未复核金额（SubAccountID，交易金额）
		accountOperation.addFixedUncheckAmount(info.getAccountID(), info.getDepositNo(), transAmount,info.getTransactionTypeID());
	}
	/** 
	 * 定期支取、通知存款支取交易删除
	 */
	public void deleteWithdrawFixedDeposit(TransFixedDrawInfo info) throws RemoteException, IRollbackException
	{
		double transAmount = 0.0;
		long billTypeID = -1;
		long bankID = -1;
		String billNo = "";
		if (info.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)
		{
			if(Config.getBoolean(ConfigConstant.SETT_FIXEDDRAW_CREATENEWBOOK,true))
			{
				//定期转活期（定期支取）
				transAmount = info.getAmount();
			}
			else
			{
				transAmount = info.getDrawAmount();
			}
			billTypeID = SETTConstant.BankBillType.FIXED_DEPOSIT_AUTHENTICATION;
		}
		else if (info.getTransactionTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
		{ //通知存款支取
			transAmount = info.getDrawAmount();
			billTypeID = SETTConstant.BankBillType.NOTIFY_DEPOSIT_AUTHENTICATION;
		}
		if (info.getCertificationBankID() > 0 && bbOperation != null) //票据终止
			bbOperation.cancelTerminateBankBill(info.getCertificationBankID(), billTypeID, info.getDepositNo());
		if (info.getSealBankID() > 0 && bbOperation != null)
		{
			if (((info.getTransactionTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW) && info.getDepositBalance() == 0)
					|| info.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)
			{
				//票据终止
				bbOperation.cancelTerminateBankBill(info.getSealBankID(), SETTConstant.BankBillType.SEAL_CARD, info.getSealNo());
			}
		}
		//扣除未复核金额（SubAccountID，交易金额）
		accountOperation.subtractFixedUncheckAmount(info.getAccountID(), info.getDepositNo(), transAmount);
	}
	
	/**
	 * 定期支取、通知存款支取交易复核
	 */
	public void checkWithdrawFixedDeposit(TransFixedDrawInfo info) throws RemoteException, IRollbackException
	{
		//交易金额
		double transAmount = 0.0;
		log.debug(UtilOperation.dataentityToString(info));
		if (info.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)
		{
			//定期转活期（定期支取）
			if(Config.getBoolean(ConfigConstant.SETT_FIXEDDRAW_CREATENEWBOOK,true))
			{
				log.debug("------定期转活期--------");
				transAmount = info.getAmount();
			}
			else
			{
				log.debug("------定期转活期--------");
				transAmount = info.getDrawAmount();
			}
		}
		else if (info.getTransactionTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
		{ //通知存款支取
			log.debug("------通知存款支取--------");
			transAmount = info.getDrawAmount();
		}
		TransAccountDetailInfo tadi = transferTransFixedDrawInfoToTransAccountDetailInfo(info, TRANS_DEPOSIT_FIXED);
		//定期支取（SubAccountID，交易金额，对方账户=CurrentAccountID）
		log.debug("------开始accountOperation.withdrawFix--------");
		log.debug(UtilOperation.dataentityToString(tadi));
		long subFixAccountID = accountOperation.withdrawFix(tadi);
		//本金付款方式为活期存款且利息付款方式为银行，或本金及利息的付款方式均为活期存款且本金/利息费用处理方法为分笔处理
		//本金活期子账户ID
		long principalCurrentSubAccountID = -1;
		//利息活期子账户ID
		long interestCurrentSubAccountID = -1;
		
		//是否有银企接口
	//	boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);		
		
		//本金存入活期账户
		if (info.getCapitalAndInterestDealWay() == SETTConstant.CapitalAndInterestDealWay.DIVIDE_DEAL){
			if (info.getCurrentAccountID() > 0 )
			{
				log.debug("--------分笔处理,本金存入活期账户accountOperation.depositCurrent------------");
				tadi = transferTransFixedDrawInfoToTransAccountDetailInfo(info, TRANS_DEPOSIT_CURRENT_PRINCIPAL);
				log.debug(UtilOperation.dataentityToString(tadi));
				principalCurrentSubAccountID = accountOperation.depositCurrent(tadi);
			}
			//else if (info.getBankID() > 0)
			//{
			log.info("定期支取、通知存款支取交易中本金的开户行ID："+info.getBankID()); 
			
			
		}

		//利息存入活期
		if (info.getCapitalAndInterestDealWay() == SETTConstant.CapitalAndInterestDealWay.DIVIDE_DEAL){
			if (info.getInterestBankID() < 0 )
			{
				log.debug("--------分笔处理,利息存入活期accountOperation.depositCurrent------------");
				tadi = transferTransFixedDrawInfoToTransAccountDetailInfo(info, TRANS_DEPOIST_CURRENT_INTEREST);
				log.debug(UtilOperation.dataentityToString(tadi));
				interestCurrentSubAccountID = accountOperation.depositCurrent(tadi);
			}
		//else if (info.getInterestBankID() > 0) {
			
			log.info("定期支取、通知存款支取交易中本金的开户行ID："+info.getInterestBankID()); 
			
			
		}
		
		//汇总处理，存入活期账号
		if ((info.getCurrentAccountID() > 0 && info.getInterestBankID() < 0) && (info.getCurrentAccountID() == info.getReceiveInterestAccountID())
				&& info.getCapitalAndInterestDealWay() == SETTConstant.CapitalAndInterestDealWay.SUM_DEAL)
		{
			log.debug("--------汇总处理,本金利息活期存入accountOperation.depositCurrent------------");
			tadi = transferTransFixedDrawInfoToTransAccountDetailInfo(info, TRANS_DEPOIST_CURRENT_PRINCIPALANDINTEREST);
			log.debug(UtilOperation.dataentityToString(tadi));
			principalCurrentSubAccountID = accountOperation.depositCurrent(tadi);
			
			
		
		// 汇总处理，存入银行
		}else if ((info.getCurrentAccountID() < 0 && info.getInterestBankID() > 0) 
				&& info.getCapitalAndInterestDealWay() == SETTConstant.CapitalAndInterestDealWay.SUM_DEAL)
		{
				
		}
		
		/*****修改通知子账户中的计提利息值*****/
		if(info.getTransactionTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
		{
			if(info.getStrikePreDrawInterest() > 0.0)
			{
				try
				{
					String strAction = "check";
					Sett_SubAccountDAO saDao = new Sett_SubAccountDAO();
					saDao.UpdateDrawInterest(subFixAccountID, info.getStrikePreDrawInterest(), info.getIsPreDraw(), strAction);
				}
				catch (Exception e)
				{
					throw new IRollbackException(this.ctx, "保存子账户计提利息出错！" + e.getMessage());
				}
			}
		}
		
		/*****修改定期子账户中的计提利息值*****/
		if(info.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)
		{
			if(info.getPreDrawInterest() > 0.0 || info.getStrikePreDrawInterest() > 0.0)
			{
				try
				{
					String strAction = "check";
					Sett_SubAccountDAO saDao = new Sett_SubAccountDAO();
					if(info.getPreDrawInterest() > 0.0)
						saDao.UpdateFixedDrawInterest(subFixAccountID, info.getPreDrawInterest(), strAction);
					if(info.getStrikePreDrawInterest() > 0.0)
						saDao.UpdateFixedDrawInterest(subFixAccountID, info.getStrikePreDrawInterest(), strAction);
				}
				catch (Exception e)
				{
					throw new IRollbackException(this.ctx, "保存子账户计提利息出错！" + e.getMessage());
				}
			}
		}
		
		//@TBD 目前还没有对银企接口处理汇总处理的情况
		//@TBD:通存通兑处理InterbranchSettlement()
		//产生会计分录
		log.debug("---------开始产生会计分录-----------");
		GenerateGLEntryParam param = new GenerateGLEntryParam();
		/**
		 * 如果收/付款银行账户ID>0，则本金 流向lPrincipalType =2银行，否则本金流向lPrincipalType =1 
		 */
		long lPrincipalType = -1;
		if (info.getBankID() > 0)
		{
			//本金流向是 银行
			lPrincipalType = SETTConstant.CapitalType.BANK;
		}
		else
		{
			//本金流向是 内部转账
			lPrincipalType = SETTConstant.CapitalType.INTERNAL;
		}
		long interestType = -1;
		if (info.getInterestBankID() > 0)
		{
			interestType = SETTConstant.CapitalType.BANK;
		}
		else if (info.getReceiveInterestAccountID() > 0)
		{
			interestType = SETTConstant.CapitalType.INTERNAL;
		}
		long lEntryType = info.getCapitalAndInterestDealWay();
		//收款方账户是本金活期子账户ID
		long receiveAccountID = principalCurrentSubAccountID;
		//付款方账户是定期子账户
		long payAccountID = subFixAccountID;
		//利息活期子账户ID
		long receiveInterestAccountID = interestCurrentSubAccountID;
		//本金开户行ID
		long principalBankID = info.getBankID();
		//利息开户行ID
		long interestBankID = info.getInterestBankID();
		//本金/交易金额
		double principalOrTransAmount = 0.0;
		//利息合计
		double totalInterest = 0.0;
		
		//计提利息
		double preDrawInterest = 0.0;
		//未计提利息
		double unPreDrawInterest = 0.0;
		//豁免利息
		double remissionInterest = 0.0;
		
		//本息合计
		if (info.getTransactionTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
		{
			if (info.getIsPreDraw() == 1)
			{
				totalInterest = 0;
				remissionInterest = info.getStrikePreDrawInterest();
			}
			else
			{
				totalInterest = info.getNoticeTotalInterest();
				remissionInterest = 0.0;
				
				//added by mzh_fu 2008/02/01 非提前才有
				preDrawInterest = info.getStrikePreDrawInterest();
				unPreDrawInterest = UtilOperation.Arith.sub(totalInterest,preDrawInterest);
			}
			//通知支取取支取金额
			principalOrTransAmount = info.getDrawAmount();		

			//modified by mzh_fu 2008/02/01 非提前才有
			//preDrawInterest = info.getStrikePreDrawInterest();
			//unPreDrawInterest = UtilOperation.Arith.sub(totalInterest,preDrawInterest);

		}
		else
		{
			totalInterest = info.getTotalInterest();
			//定期支取取本金
			principalOrTransAmount = info.getAmount();
			
			preDrawInterest = info.getPreDrawInterest();
			unPreDrawInterest = info.getPayableInterest() + info.getOtherInterest();
			
			remissionInterest = info.getStrikePreDrawInterest();
		}
		
		double totalPrincipalAndInterest = 0.0;
		if (info.getTransactionTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
		{
			totalPrincipalAndInterest = principalOrTransAmount + info.getNoticeTotalInterest();
		}
		else
		{
			totalPrincipalAndInterest = principalOrTransAmount + info.getTotalInterest();
		}
		//逾期利息
		double overTimeInterest = 0.0;
		if (info.getTransactionTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW && info.getIsPreDraw() == 1)
		{
			overTimeInterest = info.getDrawInterest();
		}
		else
		{
			overTimeInterest = info.getCurrentInterest();
		}

		param.setOfficeID(info.getOfficeID());
		param.setCurrencyID(info.getCurrencyID());
		param.setTransactionTypeID(info.getTransactionTypeID());
		param.setExecuteDate(info.getExecuteDate());
		param.setInterestStartDate(info.getInterestStartDate());
		param.setTransNo(info.getTransNo());
		param.setAbstractStr(info.getAbstract());
		param.setInputUserID(info.getInputUserID());
		param.setCheckUserID(info.getCheckUserID());
		param.setPrincipalType(lPrincipalType);
		param.setEntryType(lEntryType);
		param.setReceiveAccountID(receiveAccountID);
		param.setReceiveInterestAccountID(receiveInterestAccountID);
		param.setPayAccountID(payAccountID);
		param.setPrincipalBankID(principalBankID);
		param.setInterestBankID(interestBankID);
		param.setPrincipalOrTransAmount(principalOrTransAmount);
		param.setTotalInterest(totalInterest);
		param.setPreDrawInterest(preDrawInterest);
		param.setUnPreDrawInterest(unPreDrawInterest);
		param.setOverTimeInterest(overTimeInterest);
		param.setTotalPrincipalAndInterest(totalPrincipalAndInterest);
		param.setRemissionInterest(remissionInterest);
		param.setInterestType(interestType);
		log.debug(UtilOperation.dataentityToString(param));
		boolean res = glopOperation.generateGLEntry(param);
		if (!res)
		{
			throw new IRollbackException(ctx, "产生会计分录错误2");
		}
		log.debug("---------结束产生会计分录-----------");
	}
	
	/*******************************************************************************************************************
	 * 定期支取、通知存款支取交易，按取消复核
	 */
	public void cancelCheckWithdrawFixedDeposit(TransFixedDrawInfo info) throws RemoteException, IRollbackException
	{
		//交易金额
		double transAmount = 0.0;
		if (info.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)
		{
			//定期转活期（定期支取）
			transAmount = info.getAmount();
		}
		else if (info.getTransactionTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
		{ //通知存款支取
			transAmount = info.getDrawAmount();
		}
		//定期支取反交易（SubAccountID，交易金额）
		TransAccountDetailInfo tadi = transferTransFixedDrawInfoToTransAccountDetailInfo(info, TRANS_DEPOSIT_FIXED);
		accountOperation.cancelWithdrawFix(tadi);
		//活期存入反交易（收本金的活期账户CurrentAccountID，交易金额）
		if (info.getCurrentAccountID() > 0)
		{
			tadi = transferTransFixedDrawInfoToTransAccountDetailInfo(info, TRANS_DEPOSIT_CURRENT_PRINCIPAL);
			accountOperation.cancelDepositCurrent(tadi);
		}
		//活期存入反交易（收利息的活期账户ReceiveInterestAccountID，DrawInterest）
		if (info.getReceiveInterestAccountID() > 0)
		{
			tadi = transferTransFixedDrawInfoToTransAccountDetailInfo(info, TRANS_DEPOIST_CURRENT_INTEREST);
			accountOperation.cancelDepositCurrent(tadi);
		}
		
		/*****修改子账户中的计提利息值*****/
		if(info.getTransactionTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
		{
			if(info.getStrikePreDrawInterest() > 0.0 && info.getIsPreDraw() == 1)
			{
				try
				{
					String strAction = "cancelCheck";
					Sett_SubAccountDAO saDao = new Sett_SubAccountDAO();
					saDao.UpdateDrawInterest(info.getSubAccountID(), info.getStrikePreDrawInterest(), info.getIsPreDraw(), strAction);
				}
				catch (Exception e)
				{
					throw new IRollbackException(this.ctx, "保存子账户计提利息出错！" + e.getMessage());
				}
			}
		}
		
		/*****修改定期子账户中的计提利息值*****/
		if(info.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)
		{
			if(info.getPreDrawInterest() > 0.0 )
			{
				try
				{
					String strAction = "cancelCheck";
					Sett_SubAccountDAO saDao = new Sett_SubAccountDAO();
					saDao.UpdateFixedDrawInterest(info.getSubAccountID(), info.getPreDrawInterest(), strAction);
				}
				catch (Exception e)
				{
					throw new IRollbackException(this.ctx, "保存子账户计提利息出错！" + e.getMessage());
				}
			}
			if(info.getStrikePreDrawInterest() > 0.0)
			{
				try
				{
					String strAction = "cancelCheck";
					Sett_SubAccountDAO saDao = new Sett_SubAccountDAO();
					saDao.UpdateFixedDrawInterest(info.getSubAccountID(), info.getStrikePreDrawInterest(), strAction);
				}
				catch (Exception e)
				{
					throw new IRollbackException(this.ctx, "保存子账户计提利息出错！" + e.getMessage());
				}
			}
		}
		
		//删除明细账
		accountOperation.deleteTransAccountDetail(info.getTransNo());
		//通存通兑反交易处理AccountBookBizlogic.InterbranchSettlementReverse()
		//删除会计分录GeneralLedgerBizlogic.DeleteGLEntry（交易号）
		glopOperation.deleteGLEntry(info.getTransNo());
	}
	/** 定期续期转存保存 */
	public void saveContinueFixedDeposit(TransFixedContinueInfo info) throws RemoteException, IRollbackException
	{
		//账户接口操作类
		log.debug("---------开始saveContinueFixedDeposit-----------");
		log.info("活期账户ID:" + info.getAccountID());

		//修改 by kenny(胡志强) (2007-03-19) 处理保存时校验账户状态的操作
		//如果该交易有收/付款方，且收/付款方为内部账户，则校验收/付款方账户状态
		if (info.getAccountID() > 0)
		{
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(info.getAccountID(),AccountBean.TRANSTYPE_WITHDRAW);
			} catch (IException e) {
				e.printStackTrace();
				// 提示“系统忙”
				//modified by mzh_fu 2007/06/01 提示准确错误信息
	           // throw new IRollbackException(null,"Gen_E001");
				throw new IRollbackException(ctx,e);
			}
		}

		//累计未复核金额
		log.debug("---------开始累计未复核金额-----------");
		accountOperation.addFixedUncheckAmount(info.getAccountID(), info.getDepositNo(), info.getAmount(),info.getTransactionTypeID());
		log.debug("---------累计未复核金额完成-----------");
		//票据终止
		if (info.getCertificationBankID() > 0)
		{
			log.debug("---------开始旧票据终止-----------");
			bbOperation.terminateBankBill(info.getCertificationBankID(), SETTConstant.BankBillType.FIXED_DEPOSIT_AUTHENTICATION, info.getDepositNo());
			log.debug("---------结束始旧票据终止-----------");
		}
		//印鉴卡终止
		if (info.getSealBankID() > 0)
		{
			log.debug("---------开始旧印鉴卡终止-----------");
			bbOperation.terminateBankBill(info.getSealBankID(), SETTConstant.BankBillType.SEAL_CARD, info.getSealNo());
			log.debug("---------结束始旧印鉴卡终止-----------");
		}
		//新证实书使用
		if (info.getNewCertificationBankID() > 0)
		{
			log.debug("---------开始新印鉴卡终止-----------");
			bbOperation.useBankBill(info.getNewCertificationBankID(), SETTConstant.BankBillType.FIXED_DEPOSIT_AUTHENTICATION, info.getNewDepositNo(), info.getClientID(), info.getExecuteDate(), info
					.getInputUserID());
			log.debug("---------结束新印鉴卡终止-----------");
		}
		//新印鉴卡使用
		if (info.getNewSealBankID() > 0)
		{
			log.debug("---------开始新印鉴卡终止-----------");
			bbOperation.useBankBill(info.getNewSealBankID(), SETTConstant.BankBillType.SEAL_CARD, info.getNewSealNo(), info.getClientID(), info.getExecuteDate(), info.getInputUserID());
			log.debug("---------结束新印鉴卡终止-----------");
		}
	}
	public void deleteContinueFixedDeposit(TransFixedContinueInfo info) throws RemoteException, IRollbackException
	{
		log.debug("---------开始deleteContinueFixedDeposit-----------");
		//取消票据终止
		if (info.getCertificationBankID() > 0)
		{
			log.debug("---------开始取消旧票据终止-----------");
			bbOperation.cancelTerminateBankBill(info.getCertificationBankID(), SETTConstant.BankBillType.FIXED_DEPOSIT_AUTHENTICATION, info.getDepositNo());
			log.debug("---------结束取消旧票据终止-----------");
		}
		//取消印鉴卡终止
		if (info.getSealBankID() > 0)
		{
			log.debug("---------开始取消旧印鉴卡终止-----------");
			bbOperation.cancelTerminateBankBill(info.getSealBankID(), SETTConstant.BankBillType.SEAL_CARD, info.getSealNo());
			log.debug("---------结束取消旧印鉴卡终止-----------");
		}
		//取消新票据使用
		if (info.getNewCertificationBankID() > 0)
		{
			log.debug("---------开始取消新印鉴卡终止-----------");
			bbOperation.cancelUseBankBill(info.getNewCertificationBankID(), SETTConstant.BankBillType.FIXED_DEPOSIT_AUTHENTICATION, info.getNewDepositNo(), info.getExecuteDate(), info
					.getInputUserID());
			log.debug("---------结束取消新印鉴卡终止-----------");
		}
		//取消新票据使用
		if (info.getNewSealBankID() > 0)
		{
			log.debug("---------开始取消新印鉴卡终止-----------");
			bbOperation.cancelUseBankBill(info.getNewSealBankID(), SETTConstant.BankBillType.SEAL_CARD, info.getNewSealNo(), info.getExecuteDate(), info.getInputUserID());
			log.debug("---------结束取消新印鉴卡终止-----------");
		}
		log.debug("---------开--始扣除未复核金额-----------");
		accountOperation.subtractFixedUncheckAmount(info.getAccountID(), info.getDepositNo(), info.getAmount());
		log.debug("---------扣除未复核金额完成-----------");
		log.debug("---------结束deleteContinueFixedDeposit-----------");
	}
	/** 定期续期转存复核 */
	public void checkContinueFixedDeposit(TransFixedContinueInfo info) throws RemoteException, IRollbackException
	{
		long newFixedSubAccountID = -1;
		long currentSubAccountID = -1;
		log.debug("---------开始checkContinueFixedDeposit-----------");
		TransAccountDetailInfo tadi = null;
		//定期支取
		log.debug("---------开始定期支取-----------");
		tadi = transferTransFixedContinueInfoToTransAccountDetailInfo(info, TRANS_FIXED, false, false);
		
		try
		{
			accountOperation.ContinueFixedPreDrawInterest(tadi, "check");
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, "计提利息处理失败", e);
		}
		
		long fixedWithdrawSubAccountID = accountOperation.withdrawFix(tadi);
		log.debug("---------结束定期支取-----------");
		//定期开空户（定期信息），返回值=新定期子账户ID
		log.debug("---------开始开立定期子账户-----------");
		SubAccountFixedInfo safi;
		try
		{
			safi = this.transferTransFixedContinueInfoToSubAccountFixedInfo(info);
			//log.debug("-------新的定期存单号是"+safi.getDepositNo());
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, "无法获得新的定期存单号，交易失败", e);
		}
		newFixedSubAccountID = accountOperation.openFixSubAccount(safi);
		log.debug("---------结束开立定期子账户,新开立定期子账户ID: " + newFixedSubAccountID + "-----------");
		//定期存入
		log.debug("---------开始定期存入-----------");
		tadi = transferTransFixedContinueInfoToTransAccountDetailInfo(info, TRANS_FIXED, true, true);
		tadi.setTransSubAccountID(newFixedSubAccountID);
		accountOperation.depositFix(tadi);
		log.debug("---------结束定期存入-----------");
		if (info.getReceiveInterestAccountID() > 0)
		{ //利息转活期
			tadi = transferTransFixedContinueInfoToTransAccountDetailInfo(info, TRANS_CURRENT, true, false);
			currentSubAccountID = accountOperation.depositCurrent(tadi);
		}
		//@TBD通存通兑处理InterbranchSettlement()
		
		//产生会计分录
		GenerateGLEntryParam param = new GenerateGLEntryParam();
		/**
		 * 如果收/付款银行账户ID>0，则本金 流向lPrincipalType =2银行，否则本金流向lPrincipalType =1 
		 */
		long lPrincipalType = SETTConstant.CapitalType.INTERNAL;
		long lInterestType = -1;
		if (info.getInterestBankID() > 0)
		{
			//本金流向是 银行
			lInterestType = SETTConstant.CapitalType.BANK;
		}
		else
		{
			//本金流向是 内部转账
			lInterestType = SETTConstant.CapitalType.INTERNAL;
		}
		//分录类型 无关
		long lEntryType = -1;
		if (info.getIsCapitalAndInterestTransfer() == 1)
		{
			lEntryType = SETTConstant.EntryType.MERGER;
		}
		else
		{
			lEntryType = SETTConstant.EntryType.DIVIDE;
		}
		//收款方账户
		long receiveAccountID = newFixedSubAccountID;
		//付款方账户
		long payAccountID = fixedWithdrawSubAccountID;
		//收息账户ID
		long receiveInterestAccountID = currentSubAccountID;
		//long principalBankID =
		//利息开户行ID
		long interestBankID = info.getInterestBankID();
		//发生额
		double dAmount = info.getAmount();
		//利息合计
		double totalInterest = info.getWithDrawInterest();
		//计提利息
		double preDrawInterest = info.getPreDrawInterest();
		//未计提利息
		double unPreDrawInterest = info.getPayableInterest();
		
		//Boxu Add 2008年2月12日 活期利息(逾期利息)
		double overTimeInterest = info.getCurrentInterest();
		
		//本息合计
		double totalPrincipalAndInterest = dAmount + totalInterest;
		//豁免利息
		double remissionInterest = info.getPreDrawInterest();
		
		param.setOfficeID(info.getOfficeID());
		param.setCurrencyID(info.getCurrencyID());
		param.setTransactionTypeID(info.getTransactionTypeID());
		param.setExecuteDate(info.getExecuteDate());
		param.setInterestStartDate(info.getInterestStartDate());
		param.setTransNo(info.getTransNo());
		param.setAbstractStr(info.getAbstract());
		param.setInputUserID(info.getInputUserID());
		param.setCheckUserID(info.getCheckUserID());
		param.setPrincipalType(lPrincipalType);
		param.setEntryType(lEntryType);
		param.setReceiveAccountID(receiveAccountID);
		param.setPayAccountID(payAccountID);
		param.setReceiveInterestAccountID(receiveInterestAccountID);
		param.setInterestBankID(interestBankID);
		param.setInterestType(lInterestType);
		param.setPrincipalOrTransAmount(dAmount);
		param.setTotalInterest(totalInterest);
		param.setPreDrawInterest(preDrawInterest);
		param.setUnPreDrawInterest(unPreDrawInterest);
		param.setTotalPrincipalAndInterest(totalPrincipalAndInterest);
		
		//Boxu Add 2008年2月12日 活期利息分录
		param.setOverTimeInterest(overTimeInterest);
		
		//Boxu Add 2008年3月24日
		param.setRemissionInterest(remissionInterest);  //红字 豁免/冲销计提利息/正常利息
		
		boolean res = glopOperation.generateGLEntry(param);
		if (!res)
		{
			throw new IRollbackException(ctx, "产生会计分录错误2");
		}		

		}
	
	/** 定期续期转存取消复核 */
	public void cancelCheckContinueFixedDeposit(TransFixedContinueInfo info) throws RemoteException, IRollbackException
	{
		long newFixedSubAccountID = -1;
		long currentSubAccountID = -1;
		log.debug("---------开始checkContinueFixedDeposit-----------");
		log.debug(UtilOperation.dataentityToString(info));
		//定期存入反交易
		log.debug("---------开始定期存入反交易-----------");
		TransAccountDetailInfo tadiCancelDepsoit = transferTransFixedContinueInfoToTransAccountDetailInfo(info, TRANS_FIXED, true, true);
		
		try
		{
			accountOperation.ContinueFixedPreDrawInterest(tadiCancelDepsoit, "cancelCheck");
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, "计提利息处理失败", e);
		}
		
		//TransFixedContinueInfo中记录的是旧存单对应的子账户ID
		tadiCancelDepsoit.setTransSubAccountID(-1);
		long cancelDepositSubAccountID = accountOperation.cancelDepositFix(tadiCancelDepsoit);
		log.debug("---------结束定期存入反交易-----------");
		//定期开立反交易,
		log.debug("---------开始定期开立反交易：deleteFixSubAccount-----------");
		accountOperation.deleteFixSubAccount(cancelDepositSubAccountID);
		log.debug("---------结束定期开立反交易：deleteFixSubAccount-----------");
		if (info.getReceiveInterestAccountID() > 0)
		{
			log.debug("---------开始活期存入反交易-----------");
			TransAccountDetailInfo cancelWithDrawCurrentTadi = transferTransFixedContinueInfoToTransAccountDetailInfo(info, TRANS_CURRENT, true, true);
			accountOperation.cancelDepositCurrent(cancelWithDrawCurrentTadi);
			log.debug("---------结束活期存入反交易-----------");
		}
		log.debug("---------开始定期支取反交易-----------");
		TransAccountDetailInfo cancelWithdrawTadi = transferTransFixedContinueInfoToTransAccountDetailInfo(info, TRANS_FIXED, false, false);
		accountOperation.cancelWithdrawFix(cancelWithdrawTadi);
		log.debug("---------结束定期支取反交易-----------");
		accountOperation.deleteTransAccountDetail(info.getTransNo());
		//@TBD: 通存通兑反交易处理
		glopOperation.deleteGLEntry(info.getTransNo());
	}
	
	
	
	
	
	
	
	/**
	 * 保证金开立交易 @param TransMarginOpenInfo @Exception RemoteException, IRollbackException
	 */
	public void saveOpenMarginDeposit(TransMarginOpenInfo info) throws RemoteException, IRollbackException
	{
		long lReturn = 1;
		log.info("活期账户ID:" + info.getCurrentAccountID());

		//修改 by kenny(胡志强) (2007-03-19) 处理保存时校验账户状态的操作
		//如果该交易有收/付款方，且收/付款方为内部账户，则校验收/付款方账户状态
		if (info.getCurrentAccountID() > 0)
		{
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(info.getCurrentAccountID(),AccountBean.TRANSTYPE_WITHDRAW);
			} catch (IException e) {
				e.printStackTrace();
				// 提示“系统忙”
				//modified by mzh_fu 2007/06/01 提示准确错误信息
	           // throw new IRollbackException(null,"Gen_E001");
			   throw new IRollbackException(ctx,e);
			}
		}

		//账户接口操作类
		//若存款来源为活期存款即CurrentAccountID>0，则：累计未复核金额
		if (info.getCurrentAccountID() > 0)
		{
			log.debug("----保证金来源-----活期子账户大于0,存款来源为活期存款，开始累计未复核金额-----------");
			accountOperation.addCurrentUncheckAmount(info.getCurrentAccountID(), info.getAccountID(), info.getAmount());
			log.debug("---------累计未复核金额完成-----------");
		}
		
		if(info.getCommissionCurrentAccountID() > 0)
		{
			log.debug("----手续费来源-----活期子账户大于0,存款来源为活期存款，开始累计未复核金额-----------");
			accountOperation.addCurrentUncheckAmount(info.getCommissionCurrentAccountID(), info.getAccountID(), info.getCommissionAmount());
			log.debug("---------累计未复核金额完成-----------");
		}
			
		//交易类型：保证金存款开立
		/**
		if (info.getCertificationBankID() > 0 && bbOperation != null)
		{
			if (info.getTransactionTypeID() == SETTConstant.TransactionType.OPENMARGIN)
			{
				log.debug("---------开始保证金存款开立票据使用-----------");
				bbOperation.useBankBill(info.getCertificationBankID(), SETTConstant.BankBillType.FIXED_DEPOSIT_AUTHENTICATION, info.getDepositNo(), info.getClientID(), info.getExecuteDate(), info
						.getInputUserID());
				log.debug("---------结束保证金存款开立票据使用-----------");
			}
		}
		**/
		log.debug("---------saveOpenMarginDeposit-----------");
	}
	/**
	 * 保证金存款开立删除 @param TransMarginOpenInfo @Exception RemoteException, IRollbackException
	 */
	public void deleteOpenMarginDeposit(TransMarginOpenInfo info) throws RemoteException, IRollbackException
	{
		//若存款来源为活期存款即CurrentAccountID>0，则：取消累计未复核金额
		if (info.getCurrentAccountID() > 0)
		{
			log.debug("----保证金-----活期子账户大于0,存款来源为活期存款，开始扣除累计未复核金额-----------");
			accountOperation.subtractCurrentUncheckAmount(info.getCurrentAccountID(), info.getAmount());
			log.debug("---------结束扣除累计未复核金额-----------");
		}
		
		if(info.getCommissionCurrentAccountID() > 0)
		{
			log.debug("---手续费------活期子账户大于0,存款来源为活期存款，开始扣除累计未复核金额-----------");
			accountOperation.subtractCurrentUncheckAmount(info.getCommissionCurrentAccountID(), info.getCommissionAmount());
			log.debug("---------结束扣除累计未复核金额-----------");
		}
		
		log.debug("---------deleteOpenMarginDeposit-----------");
	}
	/**
	 * 保证金存款开立交易复核
	 */
	public void checkOpenMarginDeposit(TransMarginOpenInfo transInfo) throws RemoteException, IRollbackException
	{
		long currentSubAccountID = -1;
		long commissionCurrentSubAccountID = -1;
		long marginSubAccountID = -1;
		TransAccountDetailInfo currentTadi = null;
		TransAccountDetailInfo fixedTadi = null;
		Sett_SubAccountDAO  sett_SubAccountDAO = new Sett_SubAccountDAO();
		log.debug("---------checkOpenMarginDeposit-----------");
		//若存款来源为活期存款,返回值=活期子账户ID
		if (transInfo.getCurrentAccountID() > 0)
		{
			log.debug("----保证金-----活期子账户大于0,存款来源为活期存款，开始从活期账户余额中扣钱：withdrawCurrent-----------");
			currentTadi = transferTransMarginOpenInfoToTransAccountDetailInfo(transInfo, TRANS_CURRENT);
			currentSubAccountID = accountOperation.withdrawCurrent(currentTadi);
			log.debug("---------结束从活期账户余额中扣钱：withdrawCurrent-----------");
		}
		if (transInfo.getCommissionCurrentAccountID() > 0)
		{
			log.debug("----手续费-----活期子账户大于0,存款来源为活期存款，开始从活期账户余额中扣钱：withdrawCurrent-----------");
			currentTadi = transferTransMarginOpenInfoToTransAccountDetailInfo(transInfo, TRANS_CURRENT_COMMISSION);
			commissionCurrentSubAccountID = accountOperation.withdrawCurrent(currentTadi);
			log.debug("---------结束从活期账户余额中扣钱：withdrawCurrent-----------");
		}
		
		//保证金开空户（保证金信息），返回值=保证金子账户ID
		log.debug("---------开始开立保证金子账户-----------");
		log.debug(UtilOperation.dataentityToString(transInfo));
		SubAccountMarginInfo safi = this.transferTransMarginOpenInfoToSubAccountMargindInfo(transInfo);
		UtilOperation.dataentityToString(safi);
		marginSubAccountID = accountOperation.openFixSubAccount(safi);
		log.debug("---------新开立的保证金子账户ID: " + marginSubAccountID + "---------");
		log.debug("---------结束开立保证金子账户-----------");
		//保证金存入（保证金账户ID，保证金子账户ID，对方账户=CurrentAccountID）
		log.debug("---------开始开立保证金存入depositFix-----------");
		fixedTadi = transferTransMarginOpenInfoToTransAccountDetailInfo(transInfo, TRANS_FIXED);
		fixedTadi.setTransSubAccountID(marginSubAccountID);
		long newOpenSubAccountId = -1;  //新开立的保证金子账户ID
		newOpenSubAccountId = accountOperation.depositFix(fixedTadi);
		log.debug("---------新开立的保证金子账户ID = "+newOpenSubAccountId);
		
		try {
			log.debug("---------更新收款通知单ID : "+transInfo.getLoanNoticeID()+"  到保证金子账户: "+newOpenSubAccountId);
			sett_SubAccountDAO.updateLoanNoteIdToSubMargin(newOpenSubAccountId,transInfo.getLoanNoticeID());
			
			log.debug("---------更新收息活期账户ID : "+transInfo.getInterestAccountID()+"  到保证金子账户: "+newOpenSubAccountId);
			sett_SubAccountDAO.updateInterestAccountIDoSubMargin(newOpenSubAccountId,transInfo.getInterestAccountID());
		}
		catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		log.debug("---------结束开立保证金存入depositFix-----------");
		//通存通兑处理InterbranchSettlement()
		//@TBD
		/**
		 * 产生会计分录:分录类型lEntryType =0 无关，lAccountID1=收款方账户，lAccountID2=付款方账户， dAmount1=发生额
		 */
		
	
		
		log.debug("-------开始产生会计分录----------");
		GenerateGLEntryParam param = new GenerateGLEntryParam();
		/**
		 * 如果收/付款银行账户ID>0，则本金 流向lPrincipalType =2银行，否则本金流向lPrincipalType =1 
		 */
		long lPrincipalType = -1;
		long lCommissionType = -1;
		if (transInfo.getBankID() > 0)
		{
			//本金流向是 银行
			lPrincipalType = SETTConstant.CapitalType.BANK;
		}
		else
		{
			//本金流向是 内部转账
			lPrincipalType = SETTConstant.CapitalType.INTERNAL;
		}
		
		
		
		if (transInfo.getCommissionCurrentAccountID() > 0)
		{
			lCommissionType = SETTConstant.CapitalType.INTERNAL;
		}
		else
		{
			lCommissionType = SETTConstant.CapitalType.BANK;
		}
		
		//分录类型
		long lEntryType = SETTConstant.EntryType.IRRESPECTIVE;
		
		//收款方账户(保证金)
		long receiveAccountID = marginSubAccountID;
		System.out.println(".................. 收款方账户(保证金) : "+receiveAccountID);
		
		//付款方账户（保证金）
		long payAccountID = currentSubAccountID;
		System.out.println(".................. 付款方账户(保证金) : "+payAccountID);
		
		//付手续费账户ID
		long payCommissionAccountID = commissionCurrentSubAccountID;
		System.out.println(".................. 付手续费账户 : "+payCommissionAccountID);
		
		//保证金金额
		double dAmount = transInfo.getAmount();
		System.out.println(".................. 保证金金额 : "+dAmount);
		
		//手续费
		double dCommissionAmount = transInfo.getCommissionAmount();
		System.out.println(".................. 手续费 : "+dCommissionAmount);
		
		param.setOfficeID(transInfo.getOfficeID());
		param.setCurrencyID(transInfo.getCurrencyID());
		param.setTransactionTypeID(transInfo.getTransactionTypeID());
		param.setExecuteDate(transInfo.getExecuteDate());
		param.setInterestStartDate(transInfo.getInterestStartDate());
		param.setTransNo(transInfo.getTransNo());
		param.setAbstractStr(transInfo.getAbstract());
		param.setInputUserID(transInfo.getInputUserID());
		param.setCheckUserID(transInfo.getCheckUserID());
		param.setPrincipalType(lPrincipalType);
		param.setCommisionType(lCommissionType);
		param.setEntryType(lEntryType);
		
		param.setReceiveAccountID(receiveAccountID);
		param.setPayAccountID(payAccountID);
		
		param.setPayCommissionAccountID(payCommissionAccountID);
		
		param.setFeeBankID(transInfo.getCommissionBankID());
		
		param.setPrincipalBankID(transInfo.getBankID());
		param.setPrincipalOrTransAmount(dAmount);
	
		param.setCommissionFee(dCommissionAmount);
		
		
		boolean res = glopOperation.generateGLEntry(param);
		if (!res)
		{
			throw new IRollbackException(ctx, "借贷不平衡，交易失败");
		}
		log.debug("-------结束产生会计分录----------");
	}
	/**
	 * 保证金存款开立交易取消
	 */
	public void cancelCheckOpenMarginDeposit(TransMarginOpenInfo info) throws RemoteException, IRollbackException
	{
		long currentSubAccountID = -1;
		long fixedSubAccountID = -1;
		TransAccountDetailInfo currentTadi = null;
		TransAccountDetailInfo fixedTadi = null;
		log.debug("---------开始cancelCheckOpenMarginDeposit-----------");
		//若存款来源为活期存款,反交易
		if (info.getCurrentAccountID() > 0)
		{
			log.debug("---------活期子账户大于0,存款来源为活期存款，开始活期账户交易：cancelWithdrawCurrent-----------");
			currentTadi = transferTransMarginOpenInfoToTransAccountDetailInfo(info, TRANS_CURRENT);
			accountOperation.cancelWithdrawCurrent(currentTadi);
			log.debug("---------结束活期账户反交易：cancelWithdrawCurrent-----------");
		}
		if (info.getCommissionCurrentAccountID() > 0)
		{
			log.debug("---------活期子账户大于0,存款来源为活期存款，开始活期账户交易：cancelWithdrawCurrent-----------");
			currentTadi = transferTransMarginOpenInfoToTransAccountDetailInfo(info, TRANS_CURRENT_COMMISSION);
			accountOperation.cancelWithdrawCurrent(currentTadi);
			log.debug("---------结束活期账户反交易：cancelWithdrawCurrent-----------");
		}
		
		
		//保证金存入反交易
		log.debug("---------开始保证金存入反交易：cancelDepositFix-----------");
		fixedTadi = transferTransMarginOpenInfoToTransAccountDetailInfo(info, TRANS_FIXED);
		//fixedTadi.setTransSubAccountID(fixedSubAccountID);
		long cancelDepositSubFixedAccount = accountOperation.cancelDepositFix(fixedTadi);
		log.debug("---------结束保证金存入反交易：cancelDepositFix-----------");
		//保证金开空户反交易
		log.debug("---------开始保证金开立空户反交易：deleteFixSubAccount-----------");
		accountOperation.deleteFixSubAccount(cancelDepositSubFixedAccount);
		log.debug("---------结束保证金开立空户反交易：deleteFixSubAccount-----------");
		//通存通兑处理InterbranchSettlement()
		//@TBD
		//删除明细账
		accountOperation.deleteTransAccountDetail(info.getTransNo());
		glopOperation.deleteGLEntry(info.getTransNo());
		log.debug("---------结束cancelCheckOpenMarginDeposit-----------");
	}
	/**
	 * 保证金存款支取交易保存
	 */
	public void saveWithdrawMarginDeposit(TransMarginWithdrawInfo info) throws RemoteException, IRollbackException
	{
		//交易金额
		double transAmount = 0.0;
		
		if ( info != null && info.getTransactionTypeID() == SETTConstant.TransactionType.WITHDRAWMARGIN)
		{
			//保证金支取
			transAmount = info.getDrawAmount();
			System.out.println(".............. info.getAccountID() = "+info.getAccountID());
			System.out.println(".............. info.getDepositNo() = "+info.getDepositNo());
			System.out.println(".............. transAmount = "+transAmount);
			System.out.println(".............. info.getTransactionTypeID() = "+info.getTransactionTypeID());
		}
		log.info("活期账户ID:" + info.getCurrentAccountID());

		//修改 by kenny(胡志强) (2007-03-19) 处理保存时校验账户状态的操作
		//如果该交易有收/付款方，且收/付款方为内部账户，则校验收/付款方账户状态
		if (info.getAccountID() > 0)
		{
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(info.getAccountID(),AccountBean.TRANSTYPE_WITHDRAW);
			} catch (IException e) {
				e.printStackTrace();
				// 提示“系统忙”
				//modified by mzh_fu 2007/06/01 提示准确错误信息
	           // throw new IRollbackException(null,"Gen_E001");
				throw new IRollbackException(ctx,e);
			}
		}

		//累计未复核金额（SubAccountID，交易金额）
		accountOperation.addFixedUncheckAmount(info.getAccountID(), info.getDepositNo(), transAmount,info.getTransactionTypeID());
	}
	/**
	 * 融资租赁 保证金保后处理 保存要做的账务处理
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveWithFinancialMargin(TransFinancialMarginInfo info) throws RemoteException, IRollbackException{
		//处理保存时校验账户状态的操作
		//如果该交易有收/付款方，且收/付款方为内部账户，则校验收/付款方账户状态
		if (info.getNAccountID() > 0)
		{
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(info.getNAccountID(),AccountBean.TRANSTYPE_WITHDRAW);
			} catch (IException e) {
				e.printStackTrace();
				throw new IRollbackException(ctx,e);
			}
		}
		//累计未复核金额（AccountID，合同ID）
		accountOperation.addFixedUncheckAmount4Recog(info.getNAccountID(), info.getNContractID());
	}
	
	
	public void checkWithFinancialMargin(TransFinancialMarginInfo info) throws RemoteException, IRollbackException
	{
		//交易金额
		log.debug(UtilOperation.dataentityToString(info));
		
		//本金活期子账户ID
		long principalCurrentSubAccountID = -1;
		
		TransAccountDetailInfo tadi = transferTransFinancialMarginInfoToTransAccountDetailInfo(info, TRANS_DEPOSIT_MARGIN);
		//保证金支取（SubAccountID，交易金额，对方账户=CurrentAccountID）
		log.debug("------开始accountOperation.withdrawMargin--------");
		log.debug(UtilOperation.dataentityToString(tadi));
		
		Collection subFixAccountID_Info = accountOperation.withdrawFix4Recog(tadi,info.getNContractID());
		
		if(info.getTypeID() == 1 && info.getNCurrentAccountID() > 0){
			tadi = transferTransFinancialMarginInfoToTransAccountDetailInfo(info, TRANS_DRAW_MARGIN);
			log.debug(UtilOperation.dataentityToString(tadi));
			principalCurrentSubAccountID = accountOperation.depositCurrent(tadi);
			
		}
		
		
		//@TBD 目前还没有对银企接口处理汇总处理的情况
		//@TBD:通存通兑处理InterbranchSettlement()
		//产生会计分录
		log.debug("---------开始产生会计分录-----------");
		GenerateGLEntryParam param = new GenerateGLEntryParam();
		
		long lEntryType = SETTConstant.EntryType.DIVIDE;
		long transSubType = -1;
		
		//收款方账户是本金活期账户ID
		long receiveAccountID = principalCurrentSubAccountID;
		//付款方账户是定期账户
		param.setOfficeID(info.getNOfficeID());
		param.setCurrencyID(info.getNCurrencyID());
		param.setTransactionTypeID(info.getTranstypeID());
		param.setExecuteDate(info.getDtExecute());
		param.setTransNo(info.getSTransNo());
		param.setAbstractStr(info.getSAbstract());
		param.setInputUserID(info.getNInputUserID());
		param.setCheckUserID(info.getNCheckUserID());
		param.setEntryType(lEntryType);
	
		/**
		 * 如果收/付款银行账户ID>0，则本金 流向lPrincipalType =2银行，否则本金流向lPrincipalType =1 
		 */
		long lPrincipalType = -1;
		if(info.getTypeID() == 1 && info.getNCurrentAccountID() > 0 ){//如果是收款方为内部账户
			//本金流向是 内部转账
			lPrincipalType = SETTConstant.CapitalType.INTERNAL;
			transSubType = SETTConstant.SubTransactionType.BACK_RECOG;//退保证金
			param.setReceiveAccountID(receiveAccountID);
		}
		if (info.getTypeID() == 1 && info.getNCurrentAccountID() < 0 )
		{
			//本金流向是 银行
			lPrincipalType = SETTConstant.CapitalType.BANK;
			transSubType = SETTConstant.SubTransactionType.BACK_RECOG;//退保证金
			param.setPrincipalBankID(info.getNExtBankID());
			
		}
		if(info.getTypeID() == 2)
		{
			//本金流向是 开户行 扣保证金
			lPrincipalType = SETTConstant.CapitalType.IRRESPECTIVE;
			transSubType = SETTConstant.SubTransactionType.PAY_RECOG;//扣保证金
			param.setPrincipalBankID(info.getNBankID());
		}
		param.setSubTransactionType(transSubType);
		param.setPrincipalType(lPrincipalType);
		for(Iterator it=subFixAccountID_Info.iterator();it.hasNext();)
		{
			SubAccountCurrentInfo ai = (SubAccountCurrentInfo)it.next();
			param.setPayAccountID(ai.getID());
			param.setPrincipalOrTransAmount(ai.getBalance());
	
			log.debug(UtilOperation.dataentityToString(param));
			boolean res = glopOperation.generateGLEntry(param);
			if (!res)
				{
					throw new IRollbackException(ctx, "产生会计分录错误2");
				}
		}
		log.debug("---------结束产生会计分录-----------");
	}
	/** 
	 * 保证金存款支取交易删除
	 */
	public void deleteWithdrawMarginDeposit(TransMarginWithdrawInfo info) throws RemoteException, IRollbackException
	{
		double transAmount = 0.0;
		long billTypeID = -1;
		long bankID = -1;
		String billNo = "";
		if (info.getTransactionTypeID() == SETTConstant.TransactionType.WITHDRAWMARGIN)
		{
			//保证金支取
			transAmount = info.getDrawAmount();
			//billTypeID = SETTConstant.BankBillType.FIXED_DEPOSIT_AUTHENTICATION;
		}
		
		/**
		if (info.getCertificationBankID() > 0 && bbOperation != null) //票据终止
			bbOperation.cancelTerminateBankBill(info.getCertificationBankID(), billTypeID, info.getDepositNo());
		if (info.getSealBankID() > 0 && bbOperation != null)
		{
			if (((info.getTransactionTypeID() == SETTConstant.TransactionType.WITHDRAWMARGIN) && info.getDepositBalance() == 0))
			{
				//票据终止
				bbOperation.cancelTerminateBankBill(info.getSealBankID(), SETTConstant.BankBillType.SEAL_CARD, info.getSealNo());
			}
		}
		**/
		
		//扣除未复核金额（SubAccountID，交易金额）
		accountOperation.subtractFixedUncheckAmount(info.getAccountID(), info.getDepositNo(), transAmount);
	}
	/** 
	 * 融资租赁 保证金保后处理 删除要做的账务处理
	 */
	public void deleteWithFinancialMargin(TransFinancialMarginInfo info) throws RemoteException, IRollbackException{
		//扣除未复核金额（SubAccountID，交易金额）
		accountOperation.subtractFixedUncheckAmount4Recog(info.getNAccountID(), info.getNContractID());
	}
	/**
	 * 保证金存款支取交易复核
	 */
	public void checkWithdrawMarginDeposit(TransMarginWithdrawInfo info) throws RemoteException, IRollbackException
	{
		//交易金额
		double transAmount = 0.0;
		log.debug(UtilOperation.dataentityToString(info));
		if (info.getTransactionTypeID() == SETTConstant.TransactionType.WITHDRAWMARGIN)
		{
			//保证金支取
			log.debug("------保证金支取--------  info.getDrawAmount() = "+info.getDrawAmount());
			transAmount = info.getDrawAmount();
		}
		TransAccountDetailInfo tadi = transferTransMarginDrawInfoToTransAccountDetailInfo(info, TRANS_DEPOSIT_MARGIN);
		//保证金支取（SubAccountID，交易金额，对方账户=CurrentAccountID）
		log.debug("------开始accountOperation.withdrawMargin--------");
		log.debug(UtilOperation.dataentityToString(tadi));
		
		long subFixAccountID = accountOperation.withdrawFix(tadi);
		
		//本金活期子账户ID
		long principalCurrentSubAccountID = -1;
		
			
		
		//本金存入活期账户
		System.out.println("本金存入活期账户                  info.getCurrentAccountID() =  "+info.getCurrentAccountID());
		if (info.getCurrentAccountID() > 0 )
		{
			log.debug("--------本金存入活期账户accountOperation.depositCurrent------------");
			tadi = transferTransMarginDrawInfoToTransAccountDetailInfo(info, TRANS_DRAW_MARGIN);
			log.debug(UtilOperation.dataentityToString(tadi));
			principalCurrentSubAccountID = accountOperation.depositCurrent(tadi);
		}
		//else if (info.getBankID() > 0)
		//{
			log.info("保证金存款支取交易中本金的开户行ID："+info.getBankID()); 
			

		//}
		
		
		//@TBD 目前还没有对银企接口处理汇总处理的情况
		//@TBD:通存通兑处理InterbranchSettlement()
		//产生会计分录
		log.debug("---------开始产生会计分录-----------");
		GenerateGLEntryParam param = new GenerateGLEntryParam();
		/**
		 * 如果收/付款银行账户ID>0，则本金 流向lPrincipalType =2银行，否则本金流向lPrincipalType =1 
		 */
		long lPrincipalType = -1;
		if (info.getBankID() > 0)
		{
			//本金流向是 银行
			lPrincipalType = SETTConstant.CapitalType.BANK;
		}
		else
		{
			//本金流向是 内部转账
			lPrincipalType = SETTConstant.CapitalType.INTERNAL;
		}
		
		long lEntryType = SETTConstant.EntryType.DIVIDE;
		
		//收款方账户是本金活期子账户ID
		long receiveAccountID = principalCurrentSubAccountID;
		
		//付款方账户是定期子账户
		long payAccountID = subFixAccountID;
		
		//本金开户行ID
		long principalBankID = info.getBankID();
		
		//本金/交易金额
		double principalOrTransAmount = 0.0;
		
		//保证金支取取支取金额
		principalOrTransAmount = info.getDrawAmount();
		
		param.setOfficeID(info.getOfficeID());
		param.setCurrencyID(info.getCurrencyID());
		param.setTransactionTypeID(info.getTransactionTypeID());
		param.setExecuteDate(info.getExecuteDate());
		
		param.setTransNo(info.getTransNo());
		param.setAbstractStr(info.getAbstract());
		param.setInputUserID(info.getInputUserID());
		param.setCheckUserID(info.getCheckUserID());
		param.setPrincipalType(lPrincipalType);
		param.setEntryType(lEntryType);
		param.setReceiveAccountID(receiveAccountID);
		
		param.setPayAccountID(payAccountID);
		param.setPrincipalBankID(principalBankID);
		
		param.setPrincipalOrTransAmount(principalOrTransAmount);
		log.debug(UtilOperation.dataentityToString(param));
		boolean res = glopOperation.generateGLEntry(param);
		if (!res)
		{
			throw new IRollbackException(ctx, "产生会计分录错误2");
		}
		log.debug("---------结束产生会计分录-----------");
	}
	/*******************************************************************************************************************
	 * 保证金存款支取交易，按取消复核
	 */
	public void cancelCheckWithdrawMarginDeposit(TransMarginWithdrawInfo info) throws RemoteException, IRollbackException
	{
		//交易金额
		double transAmount = 0.0;
		
		transAmount = info.getDrawAmount();
		
		//保证金支取反交易（SubAccountID，交易金额）
		TransAccountDetailInfo tadi = transferTransMarginDrawInfoToTransAccountDetailInfo(info, TRANS_DEPOSIT_MARGIN);
		accountOperation.cancelWithdrawFix(tadi);
		//活期存入反交易（收本金的活期账户CurrentAccountID，交易金额）
		if (info.getCurrentAccountID() > 0)
		{
			tadi = transferTransMarginDrawInfoToTransAccountDetailInfo(info, TRANS_DEPOSIT_CURRENT_PRINCIPAL);
			accountOperation.cancelDepositCurrent(tadi);
		}
		
		//删除明细账
		accountOperation.deleteTransAccountDetail(info.getTransNo());
		//	通存通兑反交易处理AccountBookBizlogic.InterbranchSettlementReverse()
		//删除会计分录GeneralLedgerBizlogic.DeleteGLEntry（交易号）
		glopOperation.deleteGLEntry(info.getTransNo());
	}
	
	
	/*******************************************************************************************************************
	 * 融资租赁保后处理，按取消复核
	 */
	public void cancelCheckWithFinancialMargin(TransFinancialMarginInfo info) throws RemoteException, IRollbackException
	{
		//保证金支取反交易（SubAccountID，交易金额）
		TransAccountDetailInfo tadi = transferTransFinancialMarginInfoToTransAccountDetailInfo(info, TRANS_DEPOSIT_MARGIN);
		accountOperation.cancelWithdrawFix4Recog(tadi,info.getNContractID());
		//活期存入反交易（收本金的活期账户CurrentAccountID，交易金额）
		if (info.getNCurrentAccountID() > 0)
		{
			tadi = transferTransFinancialMarginInfoToTransAccountDetailInfo(info, TRANS_DEPOSIT_CURRENT_PRINCIPAL);
			accountOperation.cancelDepositCurrent(tadi);
		}
		//删除明细账
		accountOperation.deleteTransAccountDetail(info.getSTransNo());
		//	通存通兑反交易处理AccountBookBizlogic.InterbranchSettlementReverse()
		//删除会计分录GeneralLedgerBizlogic.DeleteGLEntry（交易号）
		glopOperation.deleteGLEntry(info.getSTransNo());
	}
	
	
	
	/**
	 * 自营贷款发放、委托贷款发放等交易保存
	 * 
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveGrantLoan(TransGrantLoanInfo info) throws RemoteException, IRollbackException
	{
		/** 检查委托存款账户是否可以放款 */
		if (info.getConsignDepositAccountID() > 0 && !info.isKeepAccount())
		{
			long consignSubDepositAccountID = accountOperation.getCurrentSubAccoutIDByAccoutID(info.getConsignDepositAccountID());
			log.info("委托存款账户ID:" + info.getConsignDepositAccountID());

			//修改 by kenny(胡志强) (2007-03-19) 处理保存时校验账户状态的操作
			//如果该交易有收/付款方，且收/付款方为内部账户，则校验收/付款方账户状态
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(info.getConsignDepositAccountID(),AccountBean.TRANSTYPE_WITHDRAW);
				//委托存款帐户透支检查 added by qhzhou 2008-02-28
				//委托自账户ID
				long lConsignAccountID = info.getConsignDepositAccountID();
				long[] lSubConsignAccountID = null;
				try {
					lSubConsignAccountID = (new Sett_SubAccountDAO().findSubAccountIDByAccountID(lConsignAccountID));
				} catch (SQLException e) {
					e.printStackTrace();
					throw new IException(true, "无法找到账户"+lConsignAccountID+"所对应的子账户,交易失败", null);
				}
				if (lSubConsignAccountID== null ){
					throw new IException(true, "无法找到子账户所对应的信息，账户余额透支检查错误,交易失败", null);
				}else{
					accountBean.isOverDraft(lConsignAccountID,lSubConsignAccountID[0], info.getAmount(), true);
				}
			} catch (IException e) {
				//e.printStackTrace();
				// 提示“系统忙”
				//modified by mzh_fu 2007/06/01 提示准确错误信息
	           // throw new IRollbackException(null,"Gen_E001");
				throw new IRollbackException(ctx,e);
			}
		}

		//检查活期账户是否可以做收付款操作
		if( info.getDepositAccountID() > 0 )
		{
			long currentSubDepositAccountID = accountOperation.getCurrentSubAccoutIDByAccoutID(info.getDepositAccountID());
			log.info("活期账户ID:" + info.getDepositAccountID());

			//修改 by kenny(胡志强) (2007-03-19) 处理保存时校验账户状态的操作
			//如果该交易有收/付款方，且收/付款方为内部账户，则校验收/付款方账户状态
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(info.getDepositAccountID(),AccountBean.TRANSTYPE_WITHDRAW);
			} catch (IException e) {
				e.printStackTrace();
				// 提示“系统忙”
				//modified by mzh_fu 2007/06/01 提示准确错误信息
	           // throw new IRollbackException(null,"Gen_E001");
				throw new IRollbackException(ctx,e);
			}
		}
		/** 检查付手续费账户号是否可以收付款  add by zcwang 2008-10-06 ,银团专用*/
		if(info.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANGRANT)
		{
			if (info.getPayCommisionAccountID() > 0)
			{
				long depositAccountID = accountOperation.getCurrentSubAccoutIDByAccoutID(info.getPayCommisionAccountID());
				log.info("付手续费账户ID:" + info.getPayCommisionAccountID());
				AccountBean accountBean = new AccountBean();
				try {
					accountBean.validateAccountStatus(info.getPayCommisionAccountID(),AccountBean.TRANSTYPE_WITHDRAW);
				} catch (IException e) {
					e.printStackTrace();
					// 提示“系统忙”
					//modified by mzh_fu 2007/06/01 提示准确错误信息
			           // throw new IRollbackException(null,"Gen_E001");
						throw new IRollbackException(ctx,e);
				}
			}
			BankLoanQuery bankLoanQuery = new BankLoanQuery();
			Collection bankLoancol = null;
			try {
				bankLoancol = bankLoanQuery.findByFormID(info.getLoanNoteID());
				BankLoanDrawInfo bkInfo = (BankLoanDrawInfo)bankLoancol.toArray()[0];
				double commissionAmount = bkInfo.getCommission();
				if(bkInfo.getIsHead()!=1)
				{
					commissionAmount=0.00;
				}
				if (info.getPayCommisionAccountID() > 0 && commissionAmount > 0)
				{
					log.debug("--------支付担保费账户ID>0且担保费>0 开始累计未复核金额------------");
					accountOperation.addCurrentUncheckAmount(info.getPayCommisionAccountID(), -1, commissionAmount);
					log.debug("--------结束担保费账户累计未复核操作------------");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				throw new IRollbackException(ctx,(IException)e);
			}
		}
		//
		//Modify by leiyang 2008/06/25
		//系统以前是在委贷收回时增加委托存款户的累计未复核金额，现把增加委托存款户的累计未复核金额的逻辑放到委贷发放
		if (!info.isKeepAccount()&&info.getConsignDepositAccountID() > 0)
		{
			log.debug("--------开始累计委托存款未复核金额------------");
			accountOperation.addCurrentUncheckAmount(info.getConsignDepositAccountID(), info.getReceiveInterestAccountID(), info.getAmount());
			log.debug("--------结束累计委托存款复核操作------------");
		}
	}
	/**
	 * 自营贷款发放、委托贷款发放等交易删除
	 * 
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void deleteGrantLoan(TransGrantLoanInfo info) throws RemoteException, IRollbackException
	{
		/** 检查付手续费账户号是否可以收付款  add by zcwang 2008-10-06 ,银团专用*/
		if(info.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANGRANT)
		{
			BankLoanQuery bankLoanQuery = new BankLoanQuery();
			Collection bankLoancol = null;
			
				try {
					bankLoancol = bankLoanQuery.findByFormID(info.getLoanNoteID());
					BankLoanDrawInfo bkInfo = (BankLoanDrawInfo)bankLoancol.toArray()[0];
					double commissionAmount = bkInfo.getCommission();
					if (info.getPayCommisionAccountID() > 0 && commissionAmount > 0)
					{
						log.debug("--------支付代理费账户ID>0且代理费>0 开始累计未复核金额------------");
						accountOperation.subtractCurrentUncheckAmount(info.getPayCommisionAccountID(), commissionAmount);
						log.debug("--------结束代理费账户累计未复核操作------------");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				
		}
		//不需要任何操作
		//Modify by leiyang 2008/06/25
		//系统以前是在委贷收回时增加委托存款户的累计未复核金额，现把增加委托存款户的累计未复核金额的逻辑放到委贷发放
		if (!info.isKeepAccount()&&info.getConsignDepositAccountID() > 0)
		{
			log.debug("--------开始累计委托存款未复核金额------------");
			accountOperation.subtractCurrentUncheckAmount(info.getConsignDepositAccountID(), info.getAmount());
			log.debug("--------结束累计委托存款复核操作------------");
		}
	}
	/**
	 * 自营贷款发放、委托贷款发放等交易复核
	 * 
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void checkGrantLoan(TransGrantLoanInfo info) throws RemoteException, IRollbackException
	{
		log.debug("--------开始复核贷款发放------------");
		log.debug("--------开始贷款开空户------------");
		long currentPayCommissionSubAccountID = -1;
		double commissionAmount= 0.0;
		SubAccountLoanInfo sali = transferTransGrantLoanInfoToSubAccountLoanInfo(info);
		
		if(info.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANGRANT)
		{
			log.info("-------------银团贷款特殊处理金额--------------");
			/*double dRate = 0.0;
			BankLoanQuery bankLoanQuery =new BankLoanQuery();
			try 
			{
				dRate=bankLoanQuery.findRateByFormID(info.getLoanNoteID());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new IRollbackException(this.ctx, "取财务公司承贷比例出错！");
			}
			sali.setOpenAmount(info.getAmount()*dRate/100);*/
			
			double dAmount = 0.0;
			/* modify by zcwang 2007-6-20 银团贷款时将所有金额都放过去，金额=财务公司金额+银行金额
			BankLoanQuery bankLoanQuery =new BankLoanQuery();
			try 
			{
				dAmount=bankLoanQuery.findAmountByFormID(info.getLoanNoteID());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new IRollbackException(this.ctx, "取财务公司承贷金额出错！");
			}
			sali.setOpenAmount(dAmount);
			*/
			sali.setOpenAmount(info.getAmount());
			
		}
		log.debug(UtilOperation.dataentityToString(sali));		
		long loanSubAccountID = accountOperation.openLoanSubAccount(sali);
		log.debug("--------结束贷款开空户，贷款新的子账户ID是:" + loanSubAccountID + "------------");
		log.debug("--------开始贷款放款accountOperation.grantLoan------------");
		TransAccountDetailInfo tadi = transferTransGrantLoanInfoToTransAccountDetailInfo(info);
		//lxr 后增加
		if(info.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANGRANT)
		{
			log.info("-------------银团贷款特殊处理金额--------------");
			/*double dRate = 0.0;
			BankLoanQuery bankLoanQuery =new BankLoanQuery();
			try 
			{
				dRate=bankLoanQuery.findRateByFormID(info.getLoanNoteID());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new IRollbackException(this.ctx, "取财务公司承贷比例出错！");
			}
			tadi.setAmount(tadi.getAmount()*dRate/100);	*/
			
			double dAmount = 0.0;
			/* modify by zcwang 2007-6-20 银团贷款时将所有金额都放过去，金额=财务公司金额+银行金额
			BankLoanQuery bankLoanQuery =new BankLoanQuery();
			try 
			{
				dAmount=bankLoanQuery.findAmountByFormID(info.getLoanNoteID());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new IRollbackException(this.ctx, "取财务公司承贷金额出错！");
			}
			tadi.setAmount(dAmount);
			*/
			tadi.setAmount(info.getAmount());
		}
		
		tadi.setTransSubAccountID(loanSubAccountID);
		log.debug(UtilOperation.dataentityToString(tadi));
		accountOperation.grantLoan(tadi);
		log.debug("--------结束贷款放款accountOperation.grantLoan------------");
		long currentDepositSubAccountID = -1;
		long consignDepositSubAccountID = -1;
		if (!info.isKeepAccount() && info.getDepositAccountID() > 0)
		{
			//为账户对账单信息查询 所加
			if(info.getLoanAccountID()>0)
			{
			Sett_AccountDAO sett_accountDao = new Sett_AccountDAO();
				try {
					AccountInfo accountInfo = sett_accountDao.findByID(info.getLoanAccountID());
					tadi.setOppAccountNo(accountInfo.getAccountNo());
					tadi.setOppAccountName(accountInfo.getAccountName());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				//
			//放款途径是活期
			log.debug("--------放款途径是活期,开始活期存入------------");
			if(info.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANGRANT)
			{
				log.info("-------------银团贷款将活期金额调整回放款单金额--------------");				
				tadi.setAmount(info.getAmount());
			}
			tadi.setTransAccountID(info.getDepositAccountID());
			tadi.setOppAccountID(info.getLoanAccountID());
			tadi.setOppSubAccountID(loanSubAccountID);
			currentDepositSubAccountID = accountOperation.depositCurrent(tadi);
			log.debug("--------结束活期存入------------");
			
			// add by zcwang 2008-10-06 银团贷款发放 ，代理费 活期账户支取 专用
			if(info.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANGRANT)
			{
				log.debug("------代理费活期支取开始-------");
				BankLoanQuery bankLoanQuery = new BankLoanQuery();
				Collection bankLoancol = null;
				try {
					bankLoancol = bankLoanQuery.findByFormID(info.getLoanNoteID());
					BankLoanDrawInfo bkInfo = (BankLoanDrawInfo)bankLoancol.toArray()[0];
					 commissionAmount = bkInfo.getCommission();
					 if(bkInfo.getIsHead()!=1)
					 {
						 commissionAmount=0.00;
					 }
					if (info.getPayCommisionAccountID() > 0 && commissionAmount > 0)
					{
						TransAccountDetailInfo tadi1 = new TransAccountDetailInfo();
						tadi1.setOfficeID(info.getOfficeID());
						tadi1.setCurrencyID(info.getCurrencyID());
						tadi1.setTransactionTypeID(info.getTransactionTypeID());
						tadi1.setDtExecute(info.getExecute());
						tadi1.setTransNo(info.getTransNo());
						tadi1.setAbstractID(info.getAbstractID());
						tadi1.setAmount(commissionAmount);
						tadi1.setTransAccountID(info.getPayCommisionAccountID());
						//resInfo.setOppAccountID();
						//账户金额查询区分金额类型增加
						tadi1.setAmountType(SETTConstant.AmountType.AmountType_9);
						tadi1.setLoanNoteID(info.getLoanNoteID());
						//交易方向在账户中设置
						//resInfo.setTransDirection();
						tadi1.setDtInterestStart(info.getInterestStart());
						tadi1.setAbstractStr(info.getAbstract());
						//在账户中设置
						//resInfo.setStatusID();
						//tadi1.setBillNo(info.getBillNo());
						//tadi1.setBillTypeID(info.getBillTypeID());
						log.debug(UtilOperation.dataentityToString(tadi1));
						//
						currentPayCommissionSubAccountID = accountOperation.withdrawCurrent(tadi1);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				
				log.debug("------代理费活期支取结束-------");
			}
			
			//end 
			
			
		}
		else if (!info.isKeepAccount())
		{
			//为账户对账单信息查询 所加
			if(info.getLoanAccountID()>0)
			{
			Sett_AccountDAO sett_accountDao = new Sett_AccountDAO();
				try {
					AccountInfo accountInfo = sett_accountDao.findByID(info.getLoanAccountID());
					tadi.setOppAccountNo(accountInfo.getAccountNo());
					tadi.setOppAccountName(accountInfo.getAccountName());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//
			/*是否有银企接口
			boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
			//是否需要生成银行指令
			boolean bCreateInstruction = false;
			long bankID = info.getBankID();
			try {
				//调用此方法后，bankID的值变为银行类型ID
				bCreateInstruction = this.isCreateInstruction(bankID);
			} catch (Exception e1) {				
				log.error("判断传入的银行ID是否需要生成银行指令出错！");
				e1.printStackTrace();
			}
			
			try
			{
				if(bIsValid) {
					Log.print("*******************开始产生自营贷款发放或委托贷款发放等付款指令，并保存**************************");
					try {
						log.debug("------开始自营贷款发放或委托贷款发放等付款指令生成--------");
						//构造参数
						CreateInstructionParam instructionParam = new CreateInstructionParam();
						instructionParam.setTransactionTypeID(info.getTransactionTypeID());
						instructionParam.setObjInfo(info);
						instructionParam.setOfficeID(info.getOfficeID());
						instructionParam.setCurrencyID(info.getCurrencyID());
						instructionParam.setCheckUserID(info.getCheckUserID());
						instructionParam.setBankType(bankID);
						instructionParam.setInputUserID(info.getInputUserID());
						
						//生成银行指令并保存
						IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
						bankInstruction.createBankInstruction(instructionParam);
						
						log.debug("------生成自营贷款发放或委托贷款发放等付款指令成功--------");
						
					} catch (Throwable e) {
						log.error("生成自营贷款发放或委托贷款发放等付款指令失败");
						e.printStackTrace();
						throw new IRollbackException(ctx, "生成自营贷款发放或委托贷款发放等付款指令失败："+e.getMessage());
					}
				}
				else {
					Log.print("没有银行接口或不需要生成银行指令！");
				}
			}
			catch (Exception e)
			{
				throw new IRollbackException(this.ctx, "保存银行转账指令出错！" + e.getMessage());
			}
			*/
		}
		if (info.isKeepAccount())
		{
			
			log.debug("--------是后补记账,开始活期存入------------");
			tadi.setTransAccountID(info.getConsignDepositAccountID());
			tadi.setOppAccountID(info.getLoanAccountID());
			tadi.setOppSubAccountID(loanSubAccountID);
			consignDepositSubAccountID = accountOperation.depositCurrent(tadi);
			log.debug("--------结束是后补记账活期存入------------");

			log.debug("--------更新未复核金额------------");
			accountOperation.addCurrentUncheckAmount(info.getConsignDepositAccountID(), info.getReceiveInterestAccountID(), info.getAmount());
			
		}
		if(info.getTransactionTypeID() == SETTConstant.TransactionType.BANKGROUPLOANGRANT)
		{
			log.debug("--------银团开始产生会计分录------------");			
			/**
			 * 如果收/付款银行账户ID>0，则本金 流向lPrincipalType =2银行，否则本金流向lPrincipalType =1 内部转账
			 */
			
			ArrayList list = info.getSyndicationLoan();
			if(list ==null || list.size()==0)			
			{
				throw new IRollbackException(ctx, "产生会计分录数据错误");
			}
			
			sett_GLEntryDAO sett_GLEntryDAO = new sett_GLEntryDAO();
					
			/*
			if (info.getBankID() > 0)
			{ //本金流向是 银行	
			*/							
				try 
				{	
					/* modify by zcwang 2007-6-20 调用共用的生成会计分录方法
					
					GLEntryInfo  gLEntryInfo = new GLEntryInfo();
					gLEntryInfo.setAbstract(info.getAbstract());
					gLEntryInfo.setAmount(info.getAmount());
					gLEntryInfo.setCheckUserID(info.getCheckUserID());
					gLEntryInfo.setCurrencyID(info.getCurrencyID());
					gLEntryInfo.setExecute(info.getExecute());
					//gLEntryInfo.setGroup(info.getAbstract());
					gLEntryInfo.setInputUserID(info.getInputUserID());
					gLEntryInfo.setInterestStart(info.getInterestStart());
					//gLEntryInfo.setMultiCode(info.getAbstract());
					gLEntryInfo.setOfficeID(info.getOfficeID());
					//gLEntryInfo.setPostStatusID(info.getAbstract());
					//gLEntryInfo.setStatusID(SETTConstant.BooleanValue.ISTRUE);
					gLEntryInfo.setStatusID(3);
					
					gLEntryInfo.setTransactionTypeID(info.getTransactionTypeID());
					gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
					gLEntryInfo.setTransNo(info.getTransNo());
					//gLEntryInfo.setType(info.getAbstract());
					
					
					 
					//贷102  						
					gLEntryInfo.setSubjectCode(accountOperation.getSubjectByBankID(info.getBankID()));
					gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
					sett_GLEntryDAO.add(gLEntryInfo);
						
					for(int i=0;i<list.size();i++)
					{
						BankLoanDrawInfo bankLoanDrawInfo = new BankLoanDrawInfo();	
						bankLoanDrawInfo = (BankLoanDrawInfo)list.get(i);									
						
						if(bankLoanDrawInfo.getIsHead()==1)
						{
							//借149
							gLEntryInfo.setAmount(bankLoanDrawInfo.getDrawAmount());
							gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
							gLEntryInfo.setSubjectCode(accountOperation.getSubjectBySubAccountID(loanSubAccountID,accountOperation.SUBJECT_TYPE_ACCOUNT));
							sett_GLEntryDAO.add(gLEntryInfo);
							//贷202 财务公司
							gLEntryInfo.setSubjectCode(NameRef.getSubjectByBankID(bankLoanDrawInfo.getBankID()));
							gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
							sett_GLEntryDAO.add(gLEntryInfo);
						}		
						//借202 其它应付款 （包含财务公司承贷部分）
						gLEntryInfo.setAmount(bankLoanDrawInfo.getDrawAmount());
						gLEntryInfo.setSubjectCode(NameRef.getSubjectByBankID(bankLoanDrawInfo.getBankID()));
						gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
						sett_GLEntryDAO.add(gLEntryInfo);
						
						if(bankLoanDrawInfo.getIsHead()!=1)
						{	
							//202					
							gLEntryInfo.setAmount(bankLoanDrawInfo.getCommission());
							gLEntryInfo.setSubjectCode(NameRef.getSubjectByBankID(bankLoanDrawInfo.getBankID()));
							gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
							sett_GLEntryDAO.add(gLEntryInfo);
							
							dCommissionSum=dCommissionSum+bankLoanDrawInfo.getCommission();
							
						}						
					} 
					
					//502
					gLEntryInfo.setAmount(dCommissionSum);
					//gLEntryInfo.setSubjectCode("001.000.5020040000.000.000000.0000.0000");
					gLEntryInfo.setSubjectCode(accountOperation.getSubjectBySubAccountID(loanSubAccountID,accountOperation.SUBJECT_TYPE_COMMISSION));
					gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
					sett_GLEntryDAO.add(gLEntryInfo);
					*/ 
					
					//add by zcwang 2007-6-20 修改以前的银团贷款生成会计分录方法
					GenerateGLEntryParam param = new GenerateGLEntryParam();
					long lPrincipalType = -1;
					double dCommissionSum =0.0; //手续费合计	
					if (info.getBankID() > 0)
					{ //本金流向是 银行
						lPrincipalType = SETTConstant.CapitalType.BANK;
					}
					else
					{
						//本金流向是 内部转账
						lPrincipalType = SETTConstant.CapitalType.INTERNAL;
					}
					//			分录类型 无关
					long lEntryType = SETTConstant.EntryType.IRRESPECTIVE;
				//	long lEntryType = info.getCapitalAndInterstDealway();
//					收款方账户
					long receiveAccountID = -1;
					if (!info.isKeepAccount())
						receiveAccountID = currentDepositSubAccountID;
					else
						receiveAccountID = consignDepositSubAccountID;
					//收款方银行
					long receiveBankID = info.getBankID();
					//付款方账户
					long payAccountID = loanSubAccountID;
					double dAmount = info.getAmount();
					param.setReceiveAccountID(receiveAccountID);
					param.setPrincipalBankID(receiveBankID);
					param.setPayAccountID(payAccountID);
					param.setPrincipalType(lPrincipalType);
					param.setEntryType(lEntryType);
					param.setPrincipalOrTransAmount(dAmount);  //本金
					param.setTransactionTypeID(info.getTransactionTypeID());
					param.setTransNo(info.getTransNo());
					param.setOfficeID(info.getOfficeID());
					param.setCurrencyID(info.getCurrencyID());
					param.setExecuteDate(info.getExecute());
					param.setInterestStartDate(info.getInterestStart());
					param.setAbstractStr(info.getAbstract());
					param.setInputUserID(info.getInputUserID());
					param.setCheckUserID(info.getCheckUserID());
					//付手续费账户 add by zcwang 2008-10-07
					param.setPayCommissionAccountID(currentPayCommissionSubAccountID);
					//财务公司那部分手续费（代理费）
					dCommissionSum = commissionAmount;
					param.setCommissionFee(dCommissionSum);
					param.setList(list);
					//end
					//本金生成会计分录
						boolean res = glopOperation.generateGLEntry(param);
						if (!res)
						{
							throw new IRollbackException(ctx, "产生会计分录（银团本金）错误");
						}
					
					//手续费生成的会计分录, 财务公司不生成手续费 
					/* modify by zcwang 银团贷款手续费 原逻辑，注释，现在逻辑是：借款人付手续费，收款方：财务公司和参与行，
					 *  会计分录只记录财务公司和借款人那部分分录，参与行和借款人那部分金额线下处理
					for(int i=1;i<list.size();i++)
					{  
						BankLoanDrawInfo bankLoanDrawInfo = new BankLoanDrawInfo();	
						GenerateGLEntryParam param1 = new GenerateGLEntryParam();
						bankLoanDrawInfo = (BankLoanDrawInfo)list.get(i);
						if(bankLoanDrawInfo.getCommission()>0)
						{
							param1.setCommissionFee(bankLoanDrawInfo.getCommission());
							param1.setPrincipalBankID(bankLoanDrawInfo.getBankID());
							//param1.setReceiveAccountID(loanSubAccountID);
							param1.setPayAccountID(payAccountID);
							param1.setEntryType(lEntryType);
							param1.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_COMMISION_FEE);
							param1.setOfficeID(info.getOfficeID());
							param1.setCurrencyID(info.getCurrencyID());
							param1.setTransNo(info.getTransNo());
							param1.setExecuteDate(info.getExecute());
							param1.setInterestStartDate(info.getInterestStart());
							param1.setAbstractStr(info.getAbstract());
							param1.setInputUserID(info.getInputUserID());
							param1.setCheckUserID(info.getCheckUserID());
							boolean res1 = glopOperation.generateGLEntry(param1);
							if (!res1)
							{
								throw new IRollbackException(ctx, "产生会计分录（银团手续费）错误");
							}
						}
					}
					*/
					/*
					//手续费合计生成的会计分录
					GenerateGLEntryParam paramsum = new GenerateGLEntryParam();
					paramsum.setCommissionFee(dCommissionSum);   //手续费
					paramsum.setReceiveAccountID(loanSubAccountID);
					paramsum.setEntryType(lEntryType);
					paramsum.setTransactionTypeID(info.getTransactionTypeID());
					paramsum.setOfficeID(info.getOfficeID());
					paramsum.setCurrencyID(info.getCurrencyID());
					boolean res2 = glopOperation.generateGLEntry(paramsum);
					if (!res2)
					{
						throw new IRollbackException(ctx, "产生会计分录（银团手续费合计）错误2");
					}
					*/
					log.debug("--------结束产生会计分录------------");
				
					
					
					//
				}	
				catch (Exception e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new IRollbackException(ctx, e.getMessage(),e);
				}
				/*	
				GeneralLedgerBean gl = new GeneralLedgerBean();
				log.info("----------检测借贷平衡-------------");
				try 
				{
					if(!gl.checkTransDCBalance(info.getTransNo()))
					{
						throw new IRollbackException(ctx, "借贷不平衡！");											
					}
				} 
				catch (IException e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
			
			}
			else
			{
				//本金流向是 内部转账
				log.debug("--------本金流向是 内部转账------------");		
				try 
				{	
					GLEntryInfo  gLEntryInfo = new GLEntryInfo();
					gLEntryInfo.setAbstract(info.getAbstract());
					gLEntryInfo.setAmount(info.getAmount());
					gLEntryInfo.setCheckUserID(info.getCheckUserID());
					gLEntryInfo.setCurrencyID(info.getCurrencyID());
					gLEntryInfo.setExecute(info.getExecute());
					//gLEntryInfo.setGroup(info.getAbstract());
					gLEntryInfo.setInputUserID(info.getInputUserID());
					gLEntryInfo.setInterestStart(info.getInterestStart());
					//gLEntryInfo.setMultiCode(info.getAbstract());
					gLEntryInfo.setOfficeID(info.getOfficeID());
					//gLEntryInfo.setPostStatusID(info.getAbstract());
					//gLEntryInfo.setStatusID(SETTConstant.BooleanValue.ISTRUE);
					gLEntryInfo.setStatusID(3);
							
					gLEntryInfo.setTransactionTypeID(info.getTransactionTypeID());
					gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
					gLEntryInfo.setTransNo(info.getTransNo());
					//gLEntryInfo.setType(info.getAbstract());
					
					//贷212						
					gLEntryInfo.setSubjectCode(accountOperation.getSubjectBySubAccountID(currentDepositSubAccountID,AccountOperation.SUBJECT_TYPE_ACCOUNT));
					gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
					sett_GLEntryDAO.add(gLEntryInfo);					
					for(int i=0;i<list.size();i++)
					{
						BankLoanDrawInfo bankLoanDrawInfo = new BankLoanDrawInfo();	
						bankLoanDrawInfo = (BankLoanDrawInfo)list.get(i);					
									
						
						if(bankLoanDrawInfo.getIsHead()==1)
						{
							//借149
							log.info("----------借149中长期贷款----------");
							gLEntryInfo.setAmount(bankLoanDrawInfo.getDrawAmount());
							gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
							gLEntryInfo.setSubjectCode(accountOperation.getSubjectBySubAccountID(loanSubAccountID,accountOperation.SUBJECT_TYPE_ACCOUNT));
							sett_GLEntryDAO.add(gLEntryInfo);
							//贷202 财务公司
							log.info("----------贷202 财务公司----------");							
							gLEntryInfo.setSubjectCode(NameRef.getSubjectByBankID(bankLoanDrawInfo.getBankID()));
							gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
							sett_GLEntryDAO.add(gLEntryInfo);
						}		
						//借202 其它应付款 （包含财务公司承贷部分）
						log.info("----------借202 其它应付款 （包含财务公司承贷部分----------");
						gLEntryInfo.setAmount(bankLoanDrawInfo.getDrawAmount());
						gLEntryInfo.setSubjectCode(NameRef.getSubjectByBankID(bankLoanDrawInfo.getBankID()));
						gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
						sett_GLEntryDAO.add(gLEntryInfo);
						
						if(bankLoanDrawInfo.getIsHead()!=1)
						{	
							//202	
							log.info("----------借202 其它应付款 手续费----------");				
							gLEntryInfo.setAmount(bankLoanDrawInfo.getCommission());
							gLEntryInfo.setSubjectCode(NameRef.getSubjectByBankID(bankLoanDrawInfo.getBankID()));
							gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
							sett_GLEntryDAO.add(gLEntryInfo);
							
							dCommissionSum=dCommissionSum+bankLoanDrawInfo.getCommission();
						}						
					} 
					
					//贷502
					log.info("----------贷502 手续费收入----------");	
					gLEntryInfo.setAmount(dCommissionSum);
					//gLEntryInfo.setSubjectCode("001.000.5020040000.000.000000.0000.0000");
					gLEntryInfo.setSubjectCode(accountOperation.getSubjectBySubAccountID(loanSubAccountID,accountOperation.SUBJECT_TYPE_COMMISSION));
					gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
					sett_GLEntryDAO.add(gLEntryInfo);
					
					
				}	
				catch (Exception e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new IRollbackException(ctx, "产生会计分录错误2");
				}
					
				GeneralLedgerBean gl = new GeneralLedgerBean();
				log.info("----------检测借贷平衡-------------");
				try 
				{
					if(!gl.checkTransDCBalance(info.getTransNo()))
					{
						throw new IRollbackException(ctx, "借贷不平衡！");											
					}
				} catch (IException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			
				log.debug("--------银团结束产生会计分录------------");
			}
			*/
		}
		else
		{
			log.debug("--------开始产生会计分录------------");
			GenerateGLEntryParam param = new GenerateGLEntryParam();
			/**
			 * 如果收/付款银行账户ID>0，则本金 流向lPrincipalType =2银行，否则本金流向lPrincipalType =1 内部转账
			 */
			long lPrincipalType = -1;
			if (info.getBankID() > 0)
			{ //本金流向是 银行
				lPrincipalType = SETTConstant.CapitalType.BANK;
			}
			else
			{
				//本金流向是 内部转账
				lPrincipalType = SETTConstant.CapitalType.INTERNAL;
			}
			if(info.getTransactionTypeID()==SETTConstant.TransactionType.BREAK_INVESTADDITIONALRECORDINGGRANT)
			{
				//本金流向是 无关
				lPrincipalType = SETTConstant.CapitalType.IRRESPECTIVE;
			}	
			
			//分录类型 无关
			long lEntryType = SETTConstant.EntryType.IRRESPECTIVE;
			//收款方账户
			long receiveAccountID = -1;
			if (!info.isKeepAccount())
				receiveAccountID = currentDepositSubAccountID;
			else
				receiveAccountID = consignDepositSubAccountID;
			//收款方银行
			long receiveBankID = info.getBankID();
			//付款方账户
			long payAccountID = loanSubAccountID;
			double dAmount = info.getAmount();
			param.setReceiveAccountID(receiveAccountID);
			param.setPrincipalBankID(receiveBankID);
			param.setPayAccountID(payAccountID);
			param.setPrincipalType(lPrincipalType);
			param.setEntryType(lEntryType);
			param.setPrincipalOrTransAmount(dAmount);
			param.setTransactionTypeID(info.getTransactionTypeID());
			param.setTransNo(info.getTransNo());
			param.setOfficeID(info.getOfficeID());
			param.setCurrencyID(info.getCurrencyID());
			param.setExecuteDate(info.getExecute());
			param.setInterestStartDate(info.getInterestStart());
			param.setAbstractStr(info.getAbstract());
			param.setInputUserID(info.getInputUserID());
			param.setCheckUserID(info.getCheckUserID());
			boolean res = glopOperation.generateGLEntry(param);
			if (!res)
			{
				throw new IRollbackException(ctx, "产生会计分录错误2");
			}
			log.debug("--------结束产生会计分录------------");
		}
		log.debug("--------结束复核贷款发放------------");
	}
	/**
	 * 自营贷款发放、委托贷款发放等取消复核
	 * 
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelCheckGrantLoan(TransGrantLoanInfo info) throws RemoteException, IRollbackException
	{
		log.debug("--------开始取消复核贷款发放复核------------");
		if (!info.isKeepAccount() && info.getDepositAccountID() > 0)
		{ //放款途径是活期
			log.debug("--------放款途径是活期,开始活期存入反交易------------");
			TransAccountDetailInfo tadi = new TransAccountDetailInfo();
			tadi.setTransAccountID(info.getDepositAccountID());
			tadi.setAmount(info.getAmount());
			accountOperation.cancelDepositCurrent(tadi);
			log.debug("--------结束活期存入反交易------------");
		}
		if (info.isKeepAccount())
		{
			log.debug("--------更新未复核金额反交易------------");
			accountOperation.subtractCurrentUncheckAmount(info.getConsignDepositAccountID(), info.getAmount());
			
			log.debug("--------是后补记账,开始活期存入反交易------------");
			TransAccountDetailInfo tadi = new TransAccountDetailInfo();
			tadi.setTransAccountID(info.getConsignDepositAccountID());
			tadi.setOppAccountID(info.getLoanAccountID());
			tadi.setAmount(info.getAmount());
			accountOperation.cancelDepositCurrent(tadi);
			log.debug("--------结束是后补记账活期存入反交易------------");

			
		}
		//		 add by zcwang 2008-10-06 银团贷款发放 ，代理费 活期账户支取 专用 反交易
		if(info.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANGRANT)
		{
			log.debug("------代理费活期支取反交易 开始-------");
			BankLoanQuery bankLoanQuery = new BankLoanQuery();
			Collection bankLoancol = null;
			try {
				bankLoancol = bankLoanQuery.findByFormID(info.getLoanNoteID());
				BankLoanDrawInfo bkInfo = (BankLoanDrawInfo)bankLoancol.toArray()[0];
				double commissionAmount = bkInfo.getCommission();
					if (info.getPayCommisionAccountID()> 0 && commissionAmount > 0)
					{
						TransAccountDetailInfo tadi1 = new TransAccountDetailInfo();
						tadi1.setOfficeID(info.getOfficeID());
						tadi1.setCurrencyID(info.getCurrencyID());
						tadi1.setTransactionTypeID(info.getTransactionTypeID());
						tadi1.setDtExecute(info.getExecute());
						tadi1.setTransNo(info.getTransNo());
						tadi1.setAbstractID(info.getAbstractID());
						
						tadi1.setAmount(commissionAmount);
						tadi1.setTransAccountID(info.getPayCommisionAccountID());
						//resInfo.setOppAccountID();
						//账户金额查询区分金额类型增加
						tadi1.setAmountType(SETTConstant.AmountType.AmountType_9);
						//
						tadi1.setLoanNoteID(info.getLoanNoteID());
						//交易方向在账户中设置
						//resInfo.setTransDirection();
						tadi1.setDtInterestStart(info.getInterestStart());
						tadi1.setAbstractStr(info.getAbstract());
						//在账户中设置
						//resInfo.setStatusID();
						//tadi1.setBillNo(info.getBillNo());
						//tadi1.setBillTypeID(info.getBillTypeID());
						log.debug(UtilOperation.dataentityToString(tadi1));
						
						accountOperation.cancelWithdrawCurrent(tadi1);
							
					}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			
			log.debug("------代理费活期支取反交易 结束-------");
		}
		//end 
		log.debug("--------开始贷款发放反交易------------");
		TransAccountDetailInfo tadi = new TransAccountDetailInfo();
		tadi.setTransAccountID(info.getLoanAccountID());
		tadi.setLoanNoteID(info.getLoanNoteID());
		tadi.setAmount(info.getAmount());
		if(info.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANGRANT)
		{
			log.info("-------------银团贷款特殊处理金额--------------");
			/*double dRate = 0.0;
			BankLoanQuery bankLoanQuery =new BankLoanQuery();
			try 
			{
				dRate=bankLoanQuery.findRateByFormID(info.getLoanNoteID());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new IRollbackException(this.ctx, "取财务公司承贷比例出错！");
			}
			tadi.setAmount(tadi.getAmount()*dRate/100);	
			double dAmount = 0.0;
			BankLoanQuery bankLoanQuery =new BankLoanQuery();
			try 
			{
				dAmount=bankLoanQuery.findAmountByFormID(info.getLoanNoteID());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new IRollbackException(this.ctx, "取财务公司承贷金额出错！");
			}
			*/
			tadi.setAmount(info.getAmount());
			
		}
		long cancelGrantLoanSubAccountID = accountOperation.cancelGrantLoan(tadi);
		log.debug("--------结束贷款发放反交易------------");
		log.debug("--------开始贷款开空户反交易------------");
		accountOperation.deleteLoanSubAccount(cancelGrantLoanSubAccountID);
		log.debug("--------结束贷款开空户反交易------------");
		accountOperation.deleteTransAccountDetail(info.getTransNo());
		//	通存通兑反交易处理AccountBookBizlogic.InterbranchSettlementReverse()
		glopOperation.deleteGLEntry(info.getTransNo());
		log.debug("--------开始取消复核贷款发放复核------------");
	}
	/**
	 * 自营贷款收回、委托贷款收回等交易保存
	 * 
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveRepaymentLoan(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException
	{
		log.debug("--------开始贷款收回保存------------");
		
		/** 检查委托方活期存款账户号是否可以收付款 */
		if (transInfo.getConsignDepositAccountID() > 0)
		{
			long consignSubDepositAccountID = accountOperation.getCurrentSubAccoutIDByAccoutID(transInfo.getConsignDepositAccountID());
			log.info("委托方活期存款账户ID:" + transInfo.getConsignDepositAccountID());

			//修改 by kenny(胡志强) (2007-03-19) 处理保存时校验账户状态的操作
			//如果该交易有收/付款方，且收/付款方为内部账户，则校验收/付款方账户状态
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(transInfo.getConsignDepositAccountID(),AccountBean.TRANSTYPE_WITHDRAW);
			} catch (IException e) {
				e.printStackTrace();
				// 提示“系统忙”
				//modified by mzh_fu 2007/06/01 提示准确错误信息
		           // throw new IRollbackException(null,"Gen_E001");
					throw new IRollbackException(ctx,e);
			}
		}

		/** 检查付款方活期存款账户号是否可以收付款 */
		if (transInfo.getDepositAccountID() > 0)
		{
			long depositAccountID = accountOperation.getCurrentSubAccoutIDByAccoutID(transInfo.getDepositAccountID());
			log.info("付款方活期存款账户ID:" + transInfo.getDepositAccountID());

			//修改 by kenny(胡志强) (2007-03-19) 处理保存时校验账户状态的操作
			//如果该交易有收/付款方，且收/付款方为内部账户，则校验收/付款方账户状态
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(transInfo.getDepositAccountID(),AccountBean.TRANSTYPE_WITHDRAW);
			} catch (IException e) {
				e.printStackTrace();
				// 提示“系统忙”
				//modified by mzh_fu 2007/06/01 提示准确错误信息
		           // throw new IRollbackException(null,"Gen_E001");
					throw new IRollbackException(ctx,e);
			}
		}

		/** 检查付息账户号是否可以收付款 */
		if (transInfo.getPayInterestAccountID() > 0)
		{
			long depositAccountID = accountOperation.getCurrentSubAccoutIDByAccoutID(transInfo.getPayInterestAccountID());
			log.info("付息账户ID:" + transInfo.getPayInterestAccountID());

			//修改 by kenny(胡志强) (2007-03-19) 处理保存时校验账户状态的操作
			//如果该交易有收/付款方，且收/付款方为内部账户，则校验收/付款方账户状态
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(transInfo.getPayInterestAccountID(),AccountBean.TRANSTYPE_WITHDRAW);
			} catch (IException e) {
				e.printStackTrace();
				// 提示“系统忙”
				//modified by mzh_fu 2007/06/01 提示准确错误信息
		           // throw new IRollbackException(null,"Gen_E001");
					throw new IRollbackException(ctx,e);
			}
		}

		/** 检查收息账户号是否可以收付款 */
		if (transInfo.getReceiveInterestAccountID() > 0)
		{
			long depositAccountID = accountOperation.getCurrentSubAccoutIDByAccoutID(transInfo.getReceiveInterestAccountID());
			log.info("收息账户ID:" + transInfo.getReceiveInterestAccountID());

			//修改 by kenny(胡志强) (2007-03-19) 处理保存时校验账户状态的操作
			//如果该交易有收/付款方，且收/付款方为内部账户，则校验收/付款方账户状态
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(transInfo.getReceiveInterestAccountID(),AccountBean.TRANSTYPE_WITHDRAW);
			} catch (IException e) {
				e.printStackTrace();
				// 提示“系统忙”
				//modified by mzh_fu 2007/06/01 提示准确错误信息
		           // throw new IRollbackException(null,"Gen_E001");
					throw new IRollbackException(ctx,e);
			}
		}

		/** 检查收担保费账户号是否可以收付款 */
		if (transInfo.getReceiveSuretyAccountID() > 0)
		{
			long depositAccountID = accountOperation.getCurrentSubAccoutIDByAccoutID(transInfo.getReceiveSuretyAccountID());
			log.info("收担保费账户ID:" + transInfo.getReceiveSuretyAccountID());

			//修改 by kenny(胡志强) (2007-03-19) 处理保存时校验账户状态的操作
			//如果该交易有收/付款方，且收/付款方为内部账户，则校验收/付款方账户状态
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(transInfo.getReceiveSuretyAccountID(),AccountBean.TRANSTYPE_WITHDRAW);
			} catch (IException e) {
				e.printStackTrace();
				// 提示“系统忙”
				//modified by mzh_fu 2007/06/01 提示准确错误信息
		           // throw new IRollbackException(null,"Gen_E001");
					throw new IRollbackException(ctx,e);
			}
		}

		/** 检查收手续费账户号是否可以收付款 */
		if (transInfo.getCommissionAccountID() > 0)
		{
			long depositAccountID = accountOperation.getCurrentSubAccoutIDByAccoutID(transInfo.getCommissionAccountID());
			log.info("收手续费账户ID:" + transInfo.getCommissionAccountID());

			//修改 by kenny(胡志强) (2007-03-19) 处理保存时校验账户状态的操作
			//如果该交易有收/付款方，且收/付款方为内部账户，则校验收/付款方账户状态
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(transInfo.getCommissionAccountID(),AccountBean.TRANSTYPE_WITHDRAW);
			} catch (IException e) {
				e.printStackTrace();
				// 提示“系统忙”
				//modified by mzh_fu 2007/06/01 提示准确错误信息
		           // throw new IRollbackException(null,"Gen_E001");
					throw new IRollbackException(ctx,e);
			}
		}

		log.debug(UtilOperation.dataentityToString(transInfo));
		if (transInfo.getDepositAccountID() > 0)
		{
			log.debug("--------活期存款账户ID>0 开始累计未复核金额------------");
			accountOperation.addCurrentUncheckAmount(transInfo.getDepositAccountID(), -1, transInfo.getAmount());
			log.debug("--------结束活期存款账户累计未复核操作------------");
			if (transInfo.getBillBankID() > 0 && bbOperation != null)
			{
				log.debug("--------开始银行票据使用------------");
				bbOperation.useBankBill(transInfo.getBillBankID(), transInfo.getBillTypeID(), transInfo.getBillNo(), transInfo.getClientID(), transInfo.getExecute(), transInfo.getInputUserID());
				log.debug("--------结束银行票据使用------------");
			}
		}
		//实际支付贷款利息+实际支付复利+实际支付逾期罚息
		double interest = 0.0;
		if (transInfo.getTransactionTypeID() == SETTConstant.TransactionType.INTERESTFEEPAYMENT){			
			interest = transInfo.getRealInterest() + transInfo.getRealCompoundInterest() + transInfo.getRealOverDueInterest() + transInfo.getRealSuretyFee();
			if(transInfo.getCommissionAccountID()<0&&transInfo.getCommissionBankID()<0){
		    	interest+= transInfo.getRealCommission();
		    }
		}else{
			interest = transInfo.getRealInterest() + transInfo.getRealCompoundInterest() + transInfo.getRealOverDueInterest();
		}
			if (transInfo.getPayInterestAccountID() > 0 && interest > 0.0)
		{
			log.debug("--------支付利息账户ID>0且利息>0 开始累计未复核金额------------");
			accountOperation.addCurrentUncheckAmount(transInfo.getPayInterestAccountID(), transInfo.getReceiveInterestAccountID(), interest);
			log.debug("--------结束利息账户累计未复核操作------------");
		}
		if (transInfo.getPaySuretyAccountID() > 0 && transInfo.getRealSuretyFee() > 0)
		{
			log.debug("--------支付担保费账户ID>0且担保费>0 开始累计未复核金额------------");
			accountOperation.addCurrentUncheckAmount(transInfo.getPaySuretyAccountID(), transInfo.getReceiveSuretyAccountID(), transInfo.getRealSuretyFee());
			log.debug("--------结束担保费账户累计未复核操作------------");
		}
		if (transInfo.getCommissionAccountID() > 0 && transInfo.getRealCommission() > 0)
		{
			log.debug("--------支付担保费账户ID>0且担保费>0 开始累计未复核金额------------");
			accountOperation.addCurrentUncheckAmount(transInfo.getCommissionAccountID(), -1, transInfo.getRealCommission());
			log.debug("--------结束担保费账户累计未复核操作------------");
		}
		//Modify by leiyang 2008/06/25
		//系统以前是在委贷收回时增加委托存款户的累计未复核金额，现把增加委托存款户的累计未复核金额的逻辑放到委贷发放
		/*if (transInfo.getConsignAccountID() > 0)
		{
			log.debug("--------开始累计委托存款未复核金额------------");
			accountOperation.addCurrentUncheckAmount(transInfo.getConsignAccountID(), transInfo.getConsignDepositAccountID(), transInfo.getAmount());
			log.debug("--------结束累计委托存款复核操作------------");
		}*/
		
		//if(transInfo.getDepositAccountID() > 0){
		//TransAccountDetailInfo withdrawTadi =
		// transferTransRepaymentLoanInfoToTransAccountDetailInfo(transInfo,
		// RepaymentLoan_CONSIGN_WITHDRAW);
		//	accountOperation.addCurrentUncheckAmount(transInfo.getDepositAccountID(),
		// transInfo.getAmount());
		if (transInfo.getLoanAccountID() > 0)
		{
			log.debug("------委托贷款支取开始累计未复核金额-------");
			if( transInfo.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
			{
				log.info("-------------银团贷款特殊处理金额--------------");
				/*double dRate = 0.0;
				BankLoanQuery bankLoanQuery =new BankLoanQuery();
				try 
				{
					dRate=bankLoanQuery.findRateByFormID(transInfo.getLoanNoteID());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new IRollbackException(this.ctx, "取财务公司承贷比例出错！");
				}
				transInfo.setAmount(transInfo.getAmount()*dRate/100);*/
				/* midify by zcwang 2007-6-21 银团收回时，收回全部金额  包括银行的
				double dAmount = 0.0;
				ArrayList syList = new ArrayList();
				syList=transInfo.getSyndicationLoanInterest();
				if(syList!=null && syList.size()>0)
				{
					Iterator it =null;
					it=syList.iterator();
					
					while(it!=null && it.hasNext())
					{					
						SyndicationLoanInterestInfo info =(SyndicationLoanInterestInfo)it.next();
						if(info.getIsHead()==1)
						{
							dAmount = info.getAmount();
						}						
					}
				}
				transInfo.setAmount(dAmount);				
				*/			
			}
			accountOperation.addLoanUncheckAmount(transInfo.getLoanAccountID(), transInfo.getLoanNoteID(), transInfo.getAmount());
			log.debug("------委托贷款支取开始累计未复核金额结束-------");
		}
		//}
		log.debug("--------结束贷款收回保存------------");
	}
	/**
	 * 自营贷款收回、委托贷款收回等交易删除
	 * 
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void deleteRepaymentLoan(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException
	{
		log.debug("--------开始贷款收回删除------------");
		//		增加判断：如果是暂存交易则不用对任何相关账户做累计未复核金额变更的操作，因为在暂存时只保存了暂存交易：见TransLoanEJB信托贷款收回暂存操作
		if(transInfo.getStatusID() == SETTConstant.TransactionStatus.TEMPSAVE){
			log.debug("--------交易是暂存状态，不用对任何相关账户做累计未复核金额变更的操作-------");
		}
		else{
			if (transInfo.getDepositAccountID() > 0)
			{
				log.debug("--------活期存款账户ID>0 开始扣除累计未复核金额------------");
				accountOperation.subtractCurrentUncheckAmount(transInfo.getDepositAccountID(), transInfo.getAmount());
				log.debug("--------结束活期存款账户扣除累计未复核操作------------");
				if (transInfo.getBillBankID() > 0 && bbOperation != null)
				{
					log.debug("--------开始银行票据取消使用------------");
					bbOperation.cancelUseBankBill(transInfo.getBillBankID(), transInfo.getBillTypeID(), transInfo.getBillNo(), transInfo.getExecute(), transInfo.getInputUserID());
					log.debug("--------结束银行票据取消使用------------");
				}
			}
			//实际支付贷款利息+实际支付复利+实际支付逾期罚息
			double interest = 0.0;
			if (transInfo.getTransactionTypeID() == SETTConstant.TransactionType.INTERESTFEEPAYMENT){
				interest = transInfo.getRealInterest() + transInfo.getRealCompoundInterest() + transInfo.getRealOverDueInterest() + transInfo.getRealSuretyFee();
				if(transInfo.getCommissionAccountID()<0&&transInfo.getCommissionBankID()<0){
			    	interest+= transInfo.getRealCommission();
			    }
			}else{
				interest = transInfo.getRealInterest() + transInfo.getRealCompoundInterest() + transInfo.getRealOverDueInterest();
			}
			if (transInfo.getPayInterestAccountID() > 0 && interest > 0.0)
			{
				log.debug("--------支付利息账户ID>0且利息>0 开始扣除累计未复核金额------------");
				accountOperation.subtractCurrentUncheckAmount(transInfo.getPayInterestAccountID(), interest);
				log.debug("--------结束利息账户扣除累计未复核操作------------");
			}
			if (transInfo.getPaySuretyAccountID() > 0 && transInfo.getRealSuretyFee() > 0)
			{
				log.debug("--------支付担保费账户ID>0且担保费>0 开始扣除累计未复核金额------------");
				accountOperation.subtractCurrentUncheckAmount(transInfo.getPaySuretyAccountID(), transInfo.getRealSuretyFee());
				log.debug("--------结束扣除担保费账户累计未复核操作------------");
			}
			if (transInfo.getCommissionAccountID() > 0 && transInfo.getRealCommission() > 0)
			{
				log.debug("--------支付担保费账户ID>0且担保费>0 开始累计未复核金额------------");
				accountOperation.subtractCurrentUncheckAmount(transInfo.getCommissionAccountID(), transInfo.getRealCommission());
				log.debug("--------结束担保费账户累计未复核操作------------");
			}
			
			/* 银团贷款 删除累计未复核金额,也是取还款金额
			if( transInfo.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
			{
				log.info("-------------银团贷款特殊处理金额--------------");
				/*double dRate = 0.0;
				BankLoanQuery bankLoanQuery =new BankLoanQuery();
				try 
				{
					dRate=bankLoanQuery.findRateByFormID(transInfo.getLoanNoteID());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new IRollbackException(this.ctx, "取财务公司承贷比例出错！");
				}
				transInfo.setAmount(transInfo.getAmount()*dRate/100);*/
			/*
				Sett_SyndicationLoanInterestDAO syDao = new Sett_SyndicationLoanInterestDAO();
				SyndicationLoanInterestInfo syInfo = new SyndicationLoanInterestInfo();
				try 
				{
					syInfo=syDao.findAmount(transInfo.getID());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new IRollbackException(this.ctx, "取财务公司承贷金额出错！");
				}
				transInfo.setAmount(syInfo.getAmount());
				
							
			}
			*/
			
			//Modify by leiyang 2008/06/25
			//系统以前是在委贷收回时增加委托存款户的累计未复核金额，现把增加委托存款户的累计未复核金额的逻辑放到委贷发放
			/*if (transInfo.getConsignAccountID() > 0)
			{
				log.debug("--------开始累计委托存款未复核金额------------");
				accountOperation.subtractCurrentUncheckAmount(transInfo.getConsignAccountID(), transInfo.getAmount());
				log.debug("--------结束累计委托存款复核操作------------");
			}*/
			//if(transInfo.getDepositAccountID() > 0){
			
			if (transInfo.getLoanAccountID() > 0)
			{
				log.debug("------委托贷款支取开始减少累计未复核金额-------");
				//TransAccountDetailInfo withdrawTadi =
				// transferTransRepaymentLoanInfoToTransAccountDetailInfo(transInfo,
				// RepaymentLoan_CONSIGN_WITHDRAW);
				//accountOperation.subtractCurrentUncheckAmount(transInfo.getDepositAccountID(),
				// transInfo.getAmount());
				accountOperation.subtractLoanUncheckAmount(transInfo.getLoanAccountID(), transInfo.getLoanNoteID(), transInfo.getAmount());
				log.debug("------委托贷款支取开始减少累计未复核金额结束-------");
			}
		}
		//}
		log.debug("--------结束贷款收回删除------------");
	}
	private final static int RepaymentLoan_WITHDRAW_SUM_AMOUNT = 0;
	private final static int RepaymentLoan_WITHDRAW_DIV_AMOUNT = 1;
	private final static int RepaymentLoan_WITHDRAW_DIV_INTEREST = 2;
	private final static int RepaymentLoan_WITHDRAW_DIV_SURETY = 3;
	private final static int RepaymentLoan_WITHDRAW_DIV_COMMISSION = 4;
	private final static int RepaymentLoan_DEPOSIT_INTEREST = 5;
	private final static int RepaymentLoan_DEPOSIT_SURETY = 6;
	private final static int RepaymentLoan_DEPOSIT_COMMISSION = 7;
	private final static int RepaymentLoan_GRANT_AMOUNT = 8;
	private final static int RepaymentLoan_CONSIGN_WITHDRAW = 9;
	private final static int RepaymentLoan_CONSIGN_DEPOSIT = 10;
	/**
	 * 自营贷款收回、委托贷款收回等交易复核
	 * 
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void checkRepaymentLoan(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException
	{
		CheckRepaymentLoan(transInfo, false);
	}
	/**
	 * 贷款收回和多笔贷款收回共同使用 区别在于贷款收回将产生会计账而多笔贷款收回只做账户操作而不产生会计分录，分录将在勾账中产生
	 * 
	 * @param isMultiple
	 *            true 为多笔贷款收回 否则为贷款收回
	 */
	private void CheckRepaymentLoan(TransRepaymentLoanInfo transInfo, boolean isMultiple) throws RemoteException, IRollbackException
	{
		log.debug("--------开始贷款收回复核------------");
		
		log.debug(UtilOperation.dataentityToString(transInfo));
		//实际支付贷款利息+实际支付复利+实际支付逾期罚息
		double interest = transInfo.getRealInterest() + transInfo.getRealCompoundInterest() + transInfo.getRealOverDueInterest();
		log.debug("利息是:" + interest);
		long transType = transInfo.getTransactionTypeID();
		long currentPaySubAccountID = -1;
		long currentPayInterestSubAccountID = -1;
		long currentPaySuretySubAccountID = -1;
		long currentPayCommissionSubAccountID = -1;
		long currentReceiveInterestSubAccountID = -1;
		long currentReceiveSuretySubAccountID = -1;
		long receieveConsignSubAccountID = -1;
		long payConsignSubAccountID = -1;
		long accountType = -1;
		long loanSubAccountID = -1;
		Sett_AccountDAO saDAO = new Sett_AccountDAO();
		try
		{
			if (transInfo.getLoanAccountID() > 0)
			{
				AccountInfo accountInfo = saDAO.findByID(transInfo.getLoanAccountID());
				accountType = accountInfo.getAccountTypeID();
			}
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage(), e);
		}
		if ((transInfo.getBankID() < 0) && ((transType == SETTConstant.TransactionType.INTERESTFEEPAYMENT&&transInfo.getCapitalAndInterstDealway() == SETTConstant.CapitalAndInterestDealWay.SUM_DEAL) 
			 || ((transType == SETTConstant.TransactionType.TRUSTLOANRECEIVE && interest > 0 && transInfo.getSuretyFee() == 0 
			 && transInfo.getPayInterestAccountID() == transInfo.getDepositAccountID())
			 || (transType == SETTConstant.TransactionType.TRUSTLOANRECEIVE && interest > 0 && transInfo.getSuretyFee() > 0
			 && transInfo.getPayInterestAccountID() == transInfo.getDepositAccountID() && transInfo.getDepositAccountID() == transInfo.getPaySuretyAccountID())
			 || (transType == SETTConstant.TransactionType.CONSIGNLOANRECEIVE && interest > 0 && transInfo.getCommission() == 0 
			 && transInfo.getPayInterestAccountID() == transInfo.getDepositAccountID()) 
			 || (transType == SETTConstant.TransactionType.CONSIGNLOANRECEIVE && interest > 0 && transInfo.getCommission() > 0
			 && transInfo.getPayInterestAccountID() == transInfo.getDepositAccountID() && transInfo.getDepositAccountID() == transInfo.getCommissionAccountID())
			 ||(transType == SETTConstant.TransactionType.BANKGROUPLOANRECEIVE && interest > 0 && transInfo.getSuretyFee() == 0 
			 && transInfo.getPayInterestAccountID() == transInfo.getDepositAccountID())
			 || (transType == SETTConstant.TransactionType.BANKGROUPLOANRECEIVE && interest > 0 && transInfo.getSuretyFee() > 0
			 && transInfo.getPayInterestAccountID() == transInfo.getDepositAccountID() && transInfo.getDepositAccountID() == transInfo.getPaySuretyAccountID()))
			 && transInfo.getCapitalAndInterstDealway() == SETTConstant.CapitalAndInterestDealWay.SUM_DEAL))
		{
			//汇总处理,内部转账
			log.debug("------汇总处理，活期支取开始-------");
			TransAccountDetailInfo tadi = transferTransRepaymentLoanInfoToTransAccountDetailInfo(transInfo, RepaymentLoan_WITHDRAW_SUM_AMOUNT);
			//log.debug(UtilOperation.dataentityToString(tadi));			
			currentPaySubAccountID = accountOperation.withdrawCurrent(tadi);
			log.debug("------汇总处理，活期支取结束-------");
			
		//}
			
		}
		else
		{
			if (transInfo.getFreeFormID() < 0 && transInfo.getDepositAccountID() > 0)
			{
				log.debug("------分笔处理，本金活期支取开始-------");
				TransAccountDetailInfo tadi = transferTransRepaymentLoanInfoToTransAccountDetailInfo(transInfo, RepaymentLoan_WITHDRAW_DIV_AMOUNT);
				currentPaySubAccountID = accountOperation.withdrawCurrent(tadi);
				log.debug("------分笔处理，本金活期支取结束-------");
			}
			if (transInfo.getPayInterestAccountID() > 0 && interest > 0.0)
			{
				log.debug("------分笔处理，利息活期支取开始-------");			
				TransAccountDetailInfo tadi = transferTransRepaymentLoanInfoToTransAccountDetailInfo(transInfo, RepaymentLoan_WITHDRAW_DIV_INTEREST);				
				currentPayInterestSubAccountID = accountOperation.withdrawCurrent(tadi);
				log.debug("------分笔处理，利息活期支取结束-------");
			}
			if (transInfo.getPaySuretyAccountID() > 0 &&( transInfo.getSuretyFee() > 0||transInfo.getRealSuretyFee()>0))//modify songwenxiao
			{
				log.debug("------分笔处理，担保费活期支取开始-------");
				TransAccountDetailInfo tadi = transferTransRepaymentLoanInfoToTransAccountDetailInfo(transInfo, RepaymentLoan_WITHDRAW_DIV_SURETY);
				currentPaySuretySubAccountID = accountOperation.withdrawCurrent(tadi);
				log.debug("------分笔处理，担保费活期支取结束-------");
			}
			if (transInfo.getCommissionAccountID() > 0 && transInfo.getCommission() > 0)
			{
				log.debug("------分笔处理，手续费活期支取开始-------");
				TransAccountDetailInfo tadi = transferTransRepaymentLoanInfoToTransAccountDetailInfo(transInfo, RepaymentLoan_WITHDRAW_DIV_COMMISSION);
				currentPayCommissionSubAccountID = accountOperation.withdrawCurrent(tadi);
				log.debug("------分笔处理，手续费活期支取结束-------");
			}
		}
		
		if (transInfo.getReceiveInterestAccountID() > 0)
		{
			log.debug("------利息存入开始-------");
			TransAccountDetailInfo depositTadi = transferTransRepaymentLoanInfoToTransAccountDetailInfo(transInfo, RepaymentLoan_DEPOSIT_INTEREST);
			currentReceiveInterestSubAccountID = accountOperation.depositCurrent(depositTadi);
			log.debug("------利息存入结束-------");
		}
		
		if (transInfo.getRealCommission()>0 && transInfo.getTransactionTypeID()==SETTConstant.TransactionType.INTERESTFEEPAYMENT){
			log.info("进入委托贷款手续费指令发送处理");
			//委贷手续费
			
		}	
		
		if (transInfo.getReceiveSuretyAccountID() > 0)
		{
			log.debug("------担保费存入开始-------");
			TransAccountDetailInfo depositTadi = transferTransRepaymentLoanInfoToTransAccountDetailInfo(transInfo, RepaymentLoan_DEPOSIT_SURETY);
			currentReceiveSuretySubAccountID = accountOperation.depositCurrent(depositTadi);
			log.debug("------担保费存入结束-------");
			
		}
		if (transType != SETTConstant.TransactionType.INTERESTFEEPAYMENT)
		{
			//利息费用支付不处理本金
			if (transInfo.getConsignAccountID() > 0)
			{
				log.debug("------委托存款支取开始-------");
				TransAccountDetailInfo withdrawTadi = transferTransRepaymentLoanInfoToTransAccountDetailInfo(transInfo, RepaymentLoan_CONSIGN_WITHDRAW);
				payConsignSubAccountID = accountOperation.withdrawCurrent(withdrawTadi);
				log.debug("------委托存款支取结束-------");
			}
			if (transInfo.getFreeFormID() < 0 && transInfo.getConsignDepositAccountID() > 0)
			{
				log.debug("------委托贷款存入开始-------");
				TransAccountDetailInfo depositTadi = transferTransRepaymentLoanInfoToTransAccountDetailInfo(transInfo, RepaymentLoan_CONSIGN_DEPOSIT);
				receieveConsignSubAccountID = accountOperation.depositCurrent(depositTadi);
				log.debug("------委托贷款存入结束-------");
			}
		}
		//如果结息（利息信息>0），若起息日期<执行日期，则调用单户结息倒填处理
		if ( interest > 0 
		|| ( interest == 0 && transInfo.getIsRemitInterest() == SETTConstant.BooleanValue.ISTRUE )
		||   transInfo.getRealCommission() > 0  //实际支付手续费
		|| ( transInfo.getRealCommission() == 0 && transInfo.getIsRemitCommission() == SETTConstant.BooleanValue.ISTRUE )
		||   transInfo.getRealSuretyFee() > 0  //实际支付担保费
		|| ( transInfo.getRealSuretyFee() == 0 && transInfo.getIsRemitSuretyFee() == SETTConstant.BooleanValue.ISTRUE ) 
		)
		{
			InterestBatch ib = new InterestBatch();
			try
			{
				Timestamp backDate = null;
				if (transType == SETTConstant.TransactionType.INTERESTFEEPAYMENT)
					backDate = transInfo.getInterestClear();
				else
					backDate = transInfo.getInterestStart();
				loanSubAccountID = accountOperation.getLoanSubAccountIDByAccountIDAndLoanNoteID(transInfo.getLoanAccountID(), transInfo.getLoanNoteID());
				if (backDate.before(transInfo.getExecute()))
				{
					log.debug("------单户结息倒填处理开始-------");
					//ib.accountInterestSettlelmentBackward(transInfo.getLoanAccountID(), loanSubAccountID, backDate, transInfo.getRealInterest(), transInfo.getOfficeID(), transInfo.getCurrencyID(),transInfo.getExecute());
					ib.accountInterestSettlelmentBackward(transInfo);
					log.debug("------单户结息倒填处理结束-------");
				}
				//贷款账户结息/费用
				ClearLoanAccountInterestConditionInfo claicInfo = new ClearLoanAccountInterestConditionInfo();
				claicInfo.setAccountID(transInfo.getLoanAccountID());
				claicInfo.setCommision(transInfo.getCommission());
				claicInfo.setCompoundInterest(transInfo.getCompoundInterest());
				claicInfo.setInterest(transInfo.getInterest());
				claicInfo.setInterestDate(transInfo.getInterestClear());
				claicInfo.setInterestReceivable(transInfo.getInterestReceiveAble());
				claicInfo.setIsRemitCommision(transInfo.getIsRemitCommission());
				claicInfo.setIsRemitCompoundInterest(transInfo.getIsRemitCompoundInterest());
				claicInfo.setIsRemitInterest(transInfo.getIsRemitInterest());
				claicInfo.setIsRemitOverDueInterest(transInfo.getIsRemitOverDueInterest());
				claicInfo.setIsRemitSuretyFee(transInfo.getIsRemitSuretyFee());
				claicInfo.setOverDueInterest(transInfo.getOverDueInterest());
				claicInfo.setRealCommission(transInfo.getRealCommission());
				claicInfo.setRealCompoundInterest(transInfo.getRealCompoundInterest());
				claicInfo.setRealInterest(transInfo.getRealInterest());
				claicInfo.setRealInterestReceivable(transInfo.getRealInterestReceiveAble());
				claicInfo.setRealOverDueInterest(transInfo.getRealOverDueInterest());
				claicInfo.setRealSuretyFee(transInfo.getRealSuretyFee());
				claicInfo.setSubAccountID(loanSubAccountID);
				claicInfo.setSuretyFee(transInfo.getSuretyFee());
				log.debug("------贷款账户结息/费用开始-------");
				/*
				if(transType==SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
				{
					log.info("-------------银团贷款特殊处理结息利息--------------");
					double dRate = 0.0;
					BankLoanQuery bankLoanQuery =new BankLoanQuery();
					try 
					{
						dRate=bankLoanQuery.findRateByFormID(transInfo.getLoanNoteID());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw new IRollbackException(this.ctx, "取财务公司承贷比例出错！");
					}
					
					claicInfo.setRealCompoundInterest(UtilOperation.Arith.round(claicInfo.getRealCompoundInterest()*dRate/100,2));
					claicInfo.setRealInterest(UtilOperation.Arith.round(claicInfo.getRealInterest()*dRate/100,2));				
					claicInfo.setRealOverDueInterest(UtilOperation.Arith.round(claicInfo.getRealOverDueInterest()*dRate/100,2));
					claicInfo.setCompoundInterest(UtilOperation.Arith.round(claicInfo.getCompoundInterest()*dRate/100,2));
					claicInfo.setInterest(UtilOperation.Arith.round(claicInfo.getInterest()*dRate/100,2));				
					claicInfo.setOverDueInterest(UtilOperation.Arith.round(claicInfo.getOverDueInterest()*dRate/100,2));
					log.debug(UtilOperation.dataentityToString(claicInfo));
						
				}
				*/
				//add by xwhe 2008-07-04 修改原因： 当利息费用支付部分还款金额的利息的时候，不修改他的结息日
				/* 注释原因：影响到手续费，担保费等业务上此得结息日
				if(transType==SETTConstant.TransactionType.INTERESTFEEPAYMENT)
				{
					Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO();
					SubAccountAssemblerInfo subAccountAssemblerInfo = null;
	
					try
					{
						subAccountAssemblerInfo = sett_SubAccountDAO.findByID(claicInfo.getSubAccountID());
						SubAccountLoanInfo resultInfo = new SubAccountLoanInfo();
						if (subAccountAssemblerInfo == null)
						{
							throw new IException(true, "子账户表中没有对应记录，交易失败", null);
						}
						if (subAccountAssemblerInfo != null)
						{
							resultInfo.setID(claicInfo.getSubAccountID());
							resultInfo = sett_SubAccountDAO.querySubAccountInfo(resultInfo);
							if(resultInfo.getBalance() > 0.0)
							{
							claicInfo.setInterestDate(resultInfo.getClearInterestDate());
							}
						}
					}
					catch (IException ie)
					{
						ie.printStackTrace();
						throw ie;
					}
					catch (SQLException e1)
					{
						e1.printStackTrace();
						throw new IException(true, "无法在子账户表中找到对应的信息，交易失败", null);
					}
					catch (Exception e)
					{
						e.printStackTrace();
						throw new IException(true, "无法在子账户表中找到对应的信息，交易失败", null);
					}
				}
				*/
				
				ib.clearLoanAccountInterest(claicInfo);
				log.debug("------贷款账户结息/费用结束-------");
			}
			catch (IException e)
			{
				throw new IRollbackException(ctx, e.getMessage(), e);
			}
			/**addded by mzh_fu 2008/03/14 解决内存泄露问题 begin*/
			finally{
				try {
					ib.closeConnection();
				} catch (IException e) {
					throw new IRollbackException(ctx, e.getMessage(), e);
				}
			}
			/**addded by mzh_fu 2008/03/14 解决内存泄露问题 end*/
		}
		if (transType != SETTConstant.TransactionType.INTERESTFEEPAYMENT && transInfo.getLoanAccountID() > 0)
		{
			TransAccountDetailInfo loanTadi = transferTransRepaymentLoanInfoToTransAccountDetailInfo(transInfo, RepaymentLoan_GRANT_AMOUNT);
			if(transType==SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
			{
				log.info("-------------银团贷款特殊处理贷款账户金额--------------");
				/*double dRate = 0.0;
				BankLoanQuery bankLoanQuery =new BankLoanQuery();
				try 
				{
					dRate=bankLoanQuery.findRateByFormID(transInfo.getLoanNoteID());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new IRollbackException(this.ctx, "取财务公司承贷比例出错！");
				}
				loanTadi.setAmount(loanTadi.getAmount()*dRate/100);	*/
				/* modyfy by  zcwang 2007-6-21 
				Sett_SyndicationLoanInterestDAO syDao = new Sett_SyndicationLoanInterestDAO();
				SyndicationLoanInterestInfo syInfo = new SyndicationLoanInterestInfo();
				try 
				{
					syInfo=syDao.findAmount(transInfo.getID());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new IRollbackException(this.ctx, "取财务公司承贷金额出错！");
				}
				loanTadi.setAmount(syInfo.getAmount());
				*/
			}
			
			/** added by mzh_fu 2008/03/14  该笔记录做过倒起息应该更新相应的账户交易明细 begin */
//			if (interest > 0)
//			{
//				Timestamp backDate = null;
//				if (transType == SETTConstant.TransactionType.INTERESTFEEPAYMENT)
//					backDate = transInfo.getInterestClear();
//				else
//					backDate = transInfo.getInterestStart();
//				if (backDate.before(transInfo.getExecute())){
//					loanTadi.setInterestBackFlag(1);
//				}
//			}
			/** added by mzh_fu 2008/03/14  该笔记录做过倒起息应该更新相应的账户交易明细 end */			
			
			loanSubAccountID = accountOperation.repayLoan(loanTadi);
		}
		//通存通兑处理InterbranchSettlement()
		//产生会计分录
		if (!isMultiple) //贷款收回将产生会计分录
		{
			if(transInfo.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
			{
				log.debug("-------银团贷款开始产生会计分录----------");
				BankLoanQuery bankLoanQuery= new BankLoanQuery();
				
				ArrayList list = new ArrayList();
				try {
					list = (ArrayList) bankLoanQuery.findByFormID(transInfo.getLoanNoteID());
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				ArrayList listSyndicationLoanInterest = transInfo.getSyndicationLoanInterest();  //成员行利息数据				
				
				sett_GLEntryDAO sett_GLEntryDAO = new sett_GLEntryDAO();						
				
				/**
				 * 如果收/付款银行账户ID>0，则本金 流向lPrincipalType =2银行，否则本金流向lPrincipalType =1 内部转账
				 */
				
				/*
				if (transInfo.getBankID() > 0)
				{
					log.info("---------------本金流向是 银行----------------");
					if(transInfo.getCapitalAndInterstDealway()==SETTConstant.CapitalAndInterestDealWay.SUM_DEAL)
					{						
						log.info("-------汇总处理-----------");
					*/	
						try 
						{
							
							/*
							GLEntryInfo  gLEntryInfo = new GLEntryInfo();
							gLEntryInfo.setAbstract(transInfo.getAbstract());
							gLEntryInfo.setAmount(transInfo.getAmount());
							gLEntryInfo.setCheckUserID(transInfo.getCheckUserID());
							gLEntryInfo.setCurrencyID(transInfo.getCurrencyID());
							gLEntryInfo.setExecute(transInfo.getExecute());
							//gLEntryInfo.setGroup(info.getAbstract());
							gLEntryInfo.setInputUserID(transInfo.getInputUserID());
							gLEntryInfo.setInterestStart(transInfo.getInterestStart());
							//gLEntryInfo.setMultiCode(info.getAbstract());
							gLEntryInfo.setOfficeID(transInfo.getOfficeID());
							//gLEntryInfo.setPostStatusID(info.getAbstract());
							//gLEntryInfo.setStatusID(SETTConstant.BooleanValue.ISTRUE);
							gLEntryInfo.setStatusID(3);
						
							gLEntryInfo.setTransactionTypeID(transInfo.getTransactionTypeID());
							gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
							gLEntryInfo.setTransNo(transInfo.getTransNo());
							//gLEntryInfo.setType(info.getAbstract());
							
							/*  modify by zcwang 2007-6-21 银团生成的会计分录没有按照会计分录设置（修改）
							if(transInfo.getInterestBankID()>0)
							{
								log.info("---------------利息流向是 银行----------------");
//								借102  合并本金和利息  	
								gLEntryInfo.setAmount(transInfo.getAmount()+transInfo.getRealCompoundInterest()+transInfo.getRealOverDueInterest()+transInfo.getRealInterest());					
								log.info("-------借102-----------"+gLEntryInfo.getAmount());
								gLEntryInfo.setSubjectCode(accountOperation.getSubjectByBankID(transInfo.getBankID()));
								gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
								sett_GLEntryDAO.add(gLEntryInfo);
							}
							else
							{
								log.info("---------------利息流向是 活期账户----------------");
//								借102  不合并本金和利息  	
								gLEntryInfo.setAmount(transInfo.getAmount());					
								log.info("-------借102-----------"+gLEntryInfo.getAmount());
								gLEntryInfo.setSubjectCode(accountOperation.getSubjectByBankID(transInfo.getBankID()));
								gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
								sett_GLEntryDAO.add(gLEntryInfo);
																
								log.info("------借212 ---------"+transInfo.getRealCompoundInterest()+transInfo.getRealOverDueInterest()+transInfo.getRealInterest());		
								gLEntryInfo.setAmount(transInfo.getRealCompoundInterest()+transInfo.getRealOverDueInterest()+transInfo.getRealInterest());								
								gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
								if(gLEntryInfo.getAmount()!=0)
								{
									gLEntryInfo.setSubjectCode(accountOperation.getSubjectBySubAccountID(currentPayInterestSubAccountID,AccountOperation.SUBJECT_TYPE_ACCOUNT));
									sett_GLEntryDAO.add(gLEntryInfo);
								}
							}
							
							
							/*for(int i=0;i<list.size();i++)
							{
								BankLoanDrawInfo bankLoanDrawInfo = new BankLoanDrawInfo();	
								bankLoanDrawInfo = (BankLoanDrawInfo)list.get(i);									
							
								if(bankLoanDrawInfo.getIsHead()==1)
								{
									//贷149
									gLEntryInfo.setAmount(transInfo.getAmount()*bankLoanDrawInfo.getRate()/100);
									log.info("-------贷149-----------"+gLEntryInfo.getAmount());
									gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
									gLEntryInfo.setSubjectCode(accountOperation.getSubjectBySubAccountID(loanSubAccountID,accountOperation.SUBJECT_TYPE_ACCOUNT));
									sett_GLEntryDAO.add(gLEntryInfo);
									//借202 财务公司
									gLEntryInfo.setSubjectCode(NameRef.getSubjectByBankID(bankLoanDrawInfo.getBankID()));
									gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
									sett_GLEntryDAO.add(gLEntryInfo);
								}		
								//贷202 其它应付款 （包含财务公司承贷部分）
								gLEntryInfo.setAmount(transInfo.getAmount()*bankLoanDrawInfo.getRate()/100);
								log.info("-------贷202 其它应付款-----------"+gLEntryInfo.getAmount());
								gLEntryInfo.setSubjectCode(NameRef.getSubjectByBankID(bankLoanDrawInfo.getBankID()));
								gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
								sett_GLEntryDAO.add(gLEntryInfo);						
													
							}	*/
							
							/*
							for(int i=0;i<listSyndicationLoanInterest.size();i++)
							{
								SyndicationLoanInterestInfo syndicationLoanInterestInfo = new SyndicationLoanInterestInfo();	
								syndicationLoanInterestInfo = (SyndicationLoanInterestInfo)listSyndicationLoanInterest.get(i);									
							
								if(syndicationLoanInterestInfo.getIsHead()==1)
								{
									//贷149
									gLEntryInfo.setAmount(syndicationLoanInterestInfo.getAmount());
									log.info("-------贷149-----------"+gLEntryInfo.getAmount());
									gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
									gLEntryInfo.setSubjectCode(accountOperation.getSubjectBySubAccountID(loanSubAccountID,accountOperation.SUBJECT_TYPE_ACCOUNT));
									sett_GLEntryDAO.add(gLEntryInfo);
									//借202 财务公司
									gLEntryInfo.setSubjectCode(NameRef.getSubjectByBankID(syndicationLoanInterestInfo.getBankID()));
									gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
									sett_GLEntryDAO.add(gLEntryInfo);
								}		
								//贷202 其它应付款 （包含财务公司承贷部分）
								gLEntryInfo.setAmount(syndicationLoanInterestInfo.getAmount());
								log.info("-------贷202 其它应付款-----------"+gLEntryInfo.getAmount());
								gLEntryInfo.setSubjectCode(NameRef.getSubjectByBankID(syndicationLoanInterestInfo.getBankID()));
								gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
								sett_GLEntryDAO.add(gLEntryInfo);						
													
							}	
							
							//利息可能为0
													
							
							//贷103  	
							gLEntryInfo.setAmount(transInfo.getRealInterestReceiveAble());
							log.info("-------贷103-----------"+gLEntryInfo.getAmount());							
							gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
							if(gLEntryInfo.getAmount()!=0)
							{
								gLEntryInfo.setSubjectCode(accountOperation.getSubjectBySubAccountID(loanSubAccountID,accountOperation.SUBJECT_TYPE_PREDRAWINTEREST));
								sett_GLEntryDAO.add(gLEntryInfo);
							}
							
							for(int i=0;i<listSyndicationLoanInterest.size();i++)
							{
								SyndicationLoanInterestInfo syndicationLoanInterestInfo = new SyndicationLoanInterestInfo();	
								syndicationLoanInterestInfo = (SyndicationLoanInterestInfo)listSyndicationLoanInterest.get(i);									
							
								if(syndicationLoanInterestInfo.getIsHead()==1)
								{
									//贷501  	
									gLEntryInfo.setAmount(syndicationLoanInterestInfo.getCompoundInterest()+syndicationLoanInterestInfo.getForpeitInterest()+syndicationLoanInterestInfo.getInterest()-transInfo.getRealInterestReceiveAble());
									log.info("-------贷501 ----------"+gLEntryInfo.getAmount());									
									gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
									if(gLEntryInfo.getAmount()!=0)
									{
										gLEntryInfo.setSubjectCode(accountOperation.getSubjectBySubAccountID(loanSubAccountID,accountOperation.SUBJECT_TYPE_INTEREST));
										sett_GLEntryDAO.add(gLEntryInfo);
									}
								}
								else
								{							
									//贷202 其它应付款 （包含财务公司承贷部分）
									gLEntryInfo.setAmount(syndicationLoanInterestInfo.getCompoundInterest()+syndicationLoanInterestInfo.getForpeitInterest()+syndicationLoanInterestInfo.getInterest());
									log.info("-------贷202 ----------"+gLEntryInfo.getAmount());
									gLEntryInfo.setSubjectCode(NameRef.getSubjectByBankID(syndicationLoanInterestInfo.getBankID()));
									gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
									if(gLEntryInfo.getAmount()!=0)
									{
										sett_GLEntryDAO.add(gLEntryInfo);
									}
								}						
													
							}					
						
							*/
							
							// add by zcwang 2007-6-21 修改银团贷款生成会计分录方法
							GenerateGLEntryParam param = new GenerateGLEntryParam();
							/**
							 * 如果收/付款银行账户ID>0，则本金 流向lPrincipalType =2银行，否则本金流向lPrincipalType =1 内部转账
							 */
							long lPrincipalType = -1;
							long lInterestType = -1;
							long lCommissionType = -1;
							if (transInfo.getBankID() > 0)
							{
								//本金流向是 银行
								lPrincipalType = SETTConstant.CapitalType.BANK;
							}
							else
							{
								//本金流向是 内部转账
								lPrincipalType = SETTConstant.CapitalType.INTERNAL;
							}
							if (transInfo.getInterestBankID() > 0)
							{
								//利息流向是 银行
								lInterestType = SETTConstant.CapitalType.BANK;
							}
							else
							{
								//利息流向是 内部转账
								lInterestType = SETTConstant.CapitalType.INTERNAL;
							}
					        log.debug("---------判断账户类型------------");
							//long accountTypeID = resultInfo.getAccountTypeID();
					        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
					        AccountTypeInfo accountTypeInfo = null;
					        try {
								accountTypeInfo = sett_AccountTypeDAO.findByID(accountType);
							} catch (SQLException e) {
								e.printStackTrace();
							}
							if (accountTypeInfo != null) {
								//为利息费用支付使用(利息费用支付使用与贷款收回使用相同的会计分录设置)
								if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
								{
									transInfo.setTransactionTypeID(SETTConstant.TransactionType.BANKGROUPLOANRECEIVE);
								}
								/*	
								//银团贷款担保费
								if (transInfo.getTransactionTypeID() == SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
								{
									if (transInfo.getPaySuretyAccountID() > 0)
									{
										lCommissionType = SETTConstant.CapitalType.INTERNAL;
									}
									else
										lCommissionType = SETTConstant.CapitalType.BANK;
								}
								*/
								//分录类型
								long lEntryType = transInfo.getCapitalAndInterstDealway();
								if (loanSubAccountID == -1) //利息费用支付
									loanSubAccountID = accountOperation.getLoanSubAccountIDByAccountIDAndLoanNoteID(transInfo.getLoanAccountID(), transInfo.getLoanNoteID());
								//收款方账户
								long receiveAccountID = loanSubAccountID;
								//付款方账户
								long payAccountID = currentPaySubAccountID;
								//收息账户ID
								long receiveInterestAccountID = currentReceiveInterestSubAccountID;
								//付息账户ID
								long payInterestAccountID = currentPayInterestSubAccountID;
								//收担保费账户ID
								long receieveSuertyFeeAccountID = currentReceiveSuretySubAccountID;
								//付担保费账户ID
								long paySuertyFeeAccountID = currentPaySuretySubAccountID;
								//付手续费账户ID
								long payCommissionAccountID = currentPayCommissionSubAccountID;
								//委托收款方账户ID
								long vouchReceiveAccountID = receieveConsignSubAccountID;
								//委托付款方账户ID
								long vouchPayAccountID = payConsignSubAccountID;
								//本金开户行ID
								long principalBankID = transInfo.getBankID();
								//利息开户行ID
								long interestBankID = transInfo.getInterestBankID();
								//费用开户行
								long feeBankID = -1;
								if (transInfo.getCommissionBankID() > 0)
									feeBankID = transInfo.getCommissionBankID();
								else if (transInfo.getSuretyBankID() > 0)
									feeBankID = transInfo.getSuretyBankID();
								//还款金额
								double dAmount = 0.0;
								//免还金额
								double dRemitAmount = 0.0;
								//不是免还
								if (transInfo.getFreeFormID() < 0)
									dAmount = transInfo.getAmount();
								else
									dRemitAmount = transInfo.getAmount();
								//利息合计
								double totalInterest = interest;
								log.debug("利息合计是:" + totalInterest);
								//计提利息
								double preDrawInterest = transInfo.getRealInterestReceiveAble();
								//实际支付本次利息
								double unPreDrawInterest = transInfo.getRealInterestIncome();
								//逾期罚息
								double overFee = transInfo.getRealOverDueInterest();
								//复利
								double compoundInterest = transInfo.getRealCompoundInterest();
								//担保费
								double suretyFee = transInfo.getRealSuretyFee();
								//手续费
								double commissionFee = transInfo.getRealCommission();
								//利息税费
								double interestTaxFee = transInfo.getRealInterestTax();
								//本息合计
								double totalPrincipalAndInterest = dAmount + totalInterest + suretyFee + commissionFee;
								log.debug("本息合计是:" + totalPrincipalAndInterest);
								//实收利息
								double reallyReceiveInterest = interest - interestTaxFee;
								
								double remissionInterest = 0.0;
								if (transInfo.getIsRemitInterest() == 1)
									remissionInterest = transInfo.getInterestReceiveAble() - transInfo.getRealInterestReceiveAble();
								else
									remissionInterest = 0.0;
								
								param.setOfficeID(transInfo.getOfficeID());
								param.setCurrencyID(transInfo.getCurrencyID());
								param.setTransactionTypeID(transInfo.getTransactionTypeID());
								param.setExecuteDate(transInfo.getExecute());
								param.setInterestStartDate(transInfo.getInterestStart());
								param.setTransNo(transInfo.getTransNo());
								param.setAbstractStr(transInfo.getAbstract());
								param.setInputUserID(transInfo.getInputUserID());
								param.setCheckUserID(transInfo.getCheckUserID());
								param.setPrincipalType(lPrincipalType);
								param.setInterestType(lInterestType);
								param.setCommisionType(lCommissionType);
								param.setEntryType(lEntryType);
								param.setReceiveAccountID(receiveAccountID);
								param.setPayAccountID(payAccountID);
								param.setPayInterestAccountID(payInterestAccountID);
								param.setReceiveInterestAccountID(receiveInterestAccountID);
								param.setPaySuertyFeeAccountID(paySuertyFeeAccountID);
								param.setReceieveSuertyFeeAccountID(receieveSuertyFeeAccountID);
								param.setPayCommissionAccountID(payCommissionAccountID);
								//param.setVouchPayAccountID(vouchPayAccountID);
								//param.setVouchReceiveAccountID(vouchReceiveAccountID);
								param.setFeeBankID(feeBankID);
								param.setInterestBankID(interestBankID);
								param.setPrincipalBankID(principalBankID);
								param.setPrincipalOrTransAmount(dAmount);
							    param.setTotalInterest(totalInterest);
								param.setPreDrawInterest(preDrawInterest);
								param.setUnPreDrawInterest(unPreDrawInterest);
								param.setOverFee(overFee);
								param.setCommissionFee(commissionFee);
								param.setCompoundInterest(compoundInterest);
								param.setSuretyFee(suretyFee);
								param.setInterestTaxFee(interestTaxFee);
								param.setTotalPrincipalAndInterest(totalPrincipalAndInterest);
								param.setReallyReceiveInterest(reallyReceiveInterest);
								param.setRemitAmount(dRemitAmount);
								if (param.getInterestTaxFee() > 0)
								param.setSubTransactionType(SETTConstant.SubTransactionType.INTERESTTAX);
								param.setList(listSyndicationLoanInterest); //银团详细信息
									
								param.setRemissionInterest(remissionInterest);  //豁免利息
								
								boolean res = glopOperation.generateGLEntry(param);
								if (!res)
								{
									throw new IRollbackException(ctx, "借贷不平衡，交易失败");
								}
								log.debug("-------结束产生会计分录----------");
							}
						}
						catch (Exception e) 
						{
							e.printStackTrace();
							throw new IRollbackException(ctx, e.getMessage());
						}
				/*		
					}
					else
					{
						log.info("-------分笔处理-----------");					
						try 
						{					
							GLEntryInfo  gLEntryInfo = new GLEntryInfo();
							gLEntryInfo.setAbstract(transInfo.getAbstract());
							gLEntryInfo.setAmount(transInfo.getAmount());
							gLEntryInfo.setCheckUserID(transInfo.getCheckUserID());
							gLEntryInfo.setCurrencyID(transInfo.getCurrencyID());
							gLEntryInfo.setExecute(transInfo.getExecute());
							//gLEntryInfo.setGroup(info.getAbstract());
							gLEntryInfo.setInputUserID(transInfo.getInputUserID());
							gLEntryInfo.setInterestStart(transInfo.getInterestStart());
							//gLEntryInfo.setMultiCode(info.getAbstract());
							gLEntryInfo.setOfficeID(transInfo.getOfficeID());
							//gLEntryInfo.setPostStatusID(info.getAbstract());
							//gLEntryInfo.setStatusID(SETTConstant.BooleanValue.ISTRUE);
							gLEntryInfo.setStatusID(3);
						
							gLEntryInfo.setTransactionTypeID(transInfo.getTransactionTypeID());
							gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
							gLEntryInfo.setTransNo(transInfo.getTransNo());
							//gLEntryInfo.setType(info.getAbstract());
						
							//借102  	
							gLEntryInfo.setAmount(transInfo.getAmount());					
							log.info("-------借102-----------"+gLEntryInfo.getAmount());
							gLEntryInfo.setSubjectCode(accountOperation.getSubjectByBankID(transInfo.getBankID()));
							gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
							sett_GLEntryDAO.add(gLEntryInfo);
							
							/*for(int i=0;i<list.size();i++)
							{
								BankLoanDrawInfo bankLoanDrawInfo = new BankLoanDrawInfo();	
								bankLoanDrawInfo = (BankLoanDrawInfo)list.get(i);									
							
								if(bankLoanDrawInfo.getIsHead()==1)
								{
									//贷149
									gLEntryInfo.setAmount(transInfo.getAmount()*bankLoanDrawInfo.getRate()/100);
									log.info("-------贷149-----------"+gLEntryInfo.getAmount());
									gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
									gLEntryInfo.setSubjectCode(accountOperation.getSubjectBySubAccountID(loanSubAccountID,accountOperation.SUBJECT_TYPE_ACCOUNT));
									sett_GLEntryDAO.add(gLEntryInfo);
									//借202 财务公司
									gLEntryInfo.setSubjectCode(NameRef.getSubjectByBankID(bankLoanDrawInfo.getBankID()));
									gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
									sett_GLEntryDAO.add(gLEntryInfo);
								}		
								//贷202 其它应付款 （包含财务公司承贷部分）
								gLEntryInfo.setAmount(transInfo.getAmount()*bankLoanDrawInfo.getRate()/100);
								log.info("-------贷202 其它应付款-----------"+gLEntryInfo.getAmount());
								gLEntryInfo.setSubjectCode(NameRef.getSubjectByBankID(bankLoanDrawInfo.getBankID()));
								gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
								sett_GLEntryDAO.add(gLEntryInfo);						
													
							}*/
							/*
							for(int i=0;i<listSyndicationLoanInterest.size();i++)
							{
								SyndicationLoanInterestInfo syndicationLoanInterestInfo = new SyndicationLoanInterestInfo();	
								syndicationLoanInterestInfo = (SyndicationLoanInterestInfo)listSyndicationLoanInterest.get(i);									
							
								if(syndicationLoanInterestInfo.getIsHead()==1)
								{
									//贷149
									gLEntryInfo.setAmount(syndicationLoanInterestInfo.getAmount());
									log.info("-------贷149-----------"+gLEntryInfo.getAmount());
									gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
									gLEntryInfo.setSubjectCode(accountOperation.getSubjectBySubAccountID(loanSubAccountID,accountOperation.SUBJECT_TYPE_ACCOUNT));
									sett_GLEntryDAO.add(gLEntryInfo);
									//借202 财务公司
									gLEntryInfo.setSubjectCode(NameRef.getSubjectByBankID(syndicationLoanInterestInfo.getBankID()));
									gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
									sett_GLEntryDAO.add(gLEntryInfo);
								}		
								//贷202 其它应付款 （包含财务公司承贷部分）
								gLEntryInfo.setAmount(syndicationLoanInterestInfo.getAmount());
								log.info("-------贷202 其它应付款-----------"+gLEntryInfo.getAmount());
								gLEntryInfo.setSubjectCode(NameRef.getSubjectByBankID(syndicationLoanInterestInfo.getBankID()));
								gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
								sett_GLEntryDAO.add(gLEntryInfo);						
													
							}	
							
							//利息可能为0
							if(transInfo.getInterestBankID()>0)
							{
								//借102  	
								gLEntryInfo.setAmount(transInfo.getRealCompoundInterest()+transInfo.getRealOverDueInterest()+transInfo.getRealInterest());
								log.info("-------借102 -----------"+gLEntryInfo.getAmount());					
								gLEntryInfo.setSubjectCode(accountOperation.getSubjectByBankID(transInfo.getInterestBankID()));
								gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
								if(gLEntryInfo.getAmount()!=0)
								{
									sett_GLEntryDAO.add(gLEntryInfo);
								}
							}
							else
							{
								log.info("------借212 ---------"+transInfo.getRealCompoundInterest()+transInfo.getRealOverDueInterest()+transInfo.getRealInterest());		
								gLEntryInfo.setAmount(transInfo.getRealCompoundInterest()+transInfo.getRealOverDueInterest()+transInfo.getRealInterest());						
								gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
								if(gLEntryInfo.getAmount()!=0)
								{
									gLEntryInfo.setSubjectCode(accountOperation.getSubjectBySubAccountID(currentPayInterestSubAccountID,AccountOperation.SUBJECT_TYPE_ACCOUNT));
									sett_GLEntryDAO.add(gLEntryInfo);
								}
							}
							
							
							//贷103  	
							gLEntryInfo.setAmount(transInfo.getRealInterestReceiveAble());
							log.info("-------贷103-----------"+gLEntryInfo.getAmount());											
							gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
							if(gLEntryInfo.getAmount()!=0)
							{
								gLEntryInfo.setSubjectCode(accountOperation.getSubjectBySubAccountID(loanSubAccountID,accountOperation.SUBJECT_TYPE_PREDRAWINTEREST));
								sett_GLEntryDAO.add(gLEntryInfo);
							}
							
							for(int i=0;i<listSyndicationLoanInterest.size();i++)
							{
								SyndicationLoanInterestInfo syndicationLoanInterestInfo = new SyndicationLoanInterestInfo();	
								syndicationLoanInterestInfo = (SyndicationLoanInterestInfo)listSyndicationLoanInterest.get(i);									
							
								if(syndicationLoanInterestInfo.getIsHead()==1)
								{
									//贷501  	
									gLEntryInfo.setAmount(syndicationLoanInterestInfo.getCompoundInterest()+syndicationLoanInterestInfo.getForpeitInterest()+syndicationLoanInterestInfo.getInterest()-transInfo.getRealInterestReceiveAble());
									log.info("-------贷501 ----------"+gLEntryInfo.getAmount());									
									gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
									if(gLEntryInfo.getAmount()!=0)
									{
										gLEntryInfo.setSubjectCode(accountOperation.getSubjectBySubAccountID(loanSubAccountID,accountOperation.SUBJECT_TYPE_INTEREST));										
										sett_GLEntryDAO.add(gLEntryInfo);
									}
								}
								else
								{							
									//贷202 其它应付款 （包含财务公司承贷部分）
									gLEntryInfo.setAmount(syndicationLoanInterestInfo.getCompoundInterest()+syndicationLoanInterestInfo.getForpeitInterest()+syndicationLoanInterestInfo.getInterest());
									log.info("-------贷202 ----------"+gLEntryInfo.getAmount());
									gLEntryInfo.setSubjectCode(NameRef.getSubjectByBankID(syndicationLoanInterestInfo.getBankID()));
									gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
									if(gLEntryInfo.getAmount()!=0)
									{
										sett_GLEntryDAO.add(gLEntryInfo);
									}
								}						
													
							}					
							
						}	
						catch (Exception e) 
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
							throw new IRollbackException(ctx, "产生银团会计分录错误");
						}
					}
					
							
					GeneralLedgerBean gl = new GeneralLedgerBean();
					log.info("----------检测借贷平衡-------------");
					try 
					{
						if(!gl.checkTransDCBalance(transInfo.getTransNo()))
						{
							throw new IRollbackException(ctx, "借贷不平衡！");											
						}
					} 
					catch (IException e1) 
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	
					
				}
				else
				{
					log.info("---------------本金流向是 活期----------------");
					if(transInfo.getCapitalAndInterstDealway()==SETTConstant.CapitalAndInterestDealWay.SUM_DEAL)
					{
						log.info("------------汇总处理----------");							
						
						try 
						{					
							GLEntryInfo  gLEntryInfo = new GLEntryInfo();
							gLEntryInfo.setAbstract(transInfo.getAbstract());
							gLEntryInfo.setAmount(transInfo.getAmount());
							gLEntryInfo.setCheckUserID(transInfo.getCheckUserID());
							gLEntryInfo.setCurrencyID(transInfo.getCurrencyID());
							gLEntryInfo.setExecute(transInfo.getExecute());
							//gLEntryInfo.setGroup(info.getAbstract());
							gLEntryInfo.setInputUserID(transInfo.getInputUserID());
							gLEntryInfo.setInterestStart(transInfo.getInterestStart());
							//gLEntryInfo.setMultiCode(info.getAbstract());
							gLEntryInfo.setOfficeID(transInfo.getOfficeID());
							//gLEntryInfo.setPostStatusID(info.getAbstract());
							//gLEntryInfo.setStatusID(SETTConstant.BooleanValue.ISTRUE);
							gLEntryInfo.setStatusID(3);
						
							gLEntryInfo.setTransactionTypeID(transInfo.getTransactionTypeID());
							gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
							gLEntryInfo.setTransNo(transInfo.getTransNo());
							//gLEntryInfo.setType(info.getAbstract());
							
							if(transInfo.getInterestBankID()>0)
							{
								//借212  	
								log.info("-------借212-----------"+transInfo.getAmount());
								gLEntryInfo.setAmount(transInfo.getAmount());					
								gLEntryInfo.setSubjectCode(accountOperation.getSubjectBySubAccountID(currentPaySubAccountID,AccountOperation.SUBJECT_TYPE_ACCOUNT));
								gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
								sett_GLEntryDAO.add(gLEntryInfo);
								
//								借102  	
								gLEntryInfo.setAmount(transInfo.getRealCompoundInterest()+transInfo.getRealOverDueInterest()+transInfo.getRealInterest());
								log.info("-------借102 -----------"+gLEntryInfo.getAmount());								
								gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
								if(gLEntryInfo.getAmount()!=0)
								{
									gLEntryInfo.setSubjectCode(accountOperation.getSubjectByBankID(transInfo.getInterestBankID()));
									sett_GLEntryDAO.add(gLEntryInfo);
								}
							}
							else
							{
								//借212  	
								log.info("-------借212-----------"+transInfo.getAmount());
								gLEntryInfo.setAmount(transInfo.getAmount()+transInfo.getRealCompoundInterest()+transInfo.getRealOverDueInterest()+transInfo.getRealInterest());					
								gLEntryInfo.setSubjectCode(accountOperation.getSubjectBySubAccountID(currentPaySubAccountID,AccountOperation.SUBJECT_TYPE_ACCOUNT));
								gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
								sett_GLEntryDAO.add(gLEntryInfo);
							}
							for(int i=0;i<list.size();i++)
							{
								BankLoanDrawInfo bankLoanDrawInfo = new BankLoanDrawInfo();	
								bankLoanDrawInfo = (BankLoanDrawInfo)list.get(i);									
							
								if(bankLoanDrawInfo.getIsHead()==1)
								{
									//贷149								
									gLEntryInfo.setAmount(transInfo.getAmount()*bankLoanDrawInfo.getRate()/100);
									log.info("-------贷149----------"+gLEntryInfo.getAmount());
									gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
									gLEntryInfo.setSubjectCode(accountOperation.getSubjectBySubAccountID(loanSubAccountID,accountOperation.SUBJECT_TYPE_ACCOUNT));
									sett_GLEntryDAO.add(gLEntryInfo);
									//借202 财务公司
									log.info("-------借202 财务公司----------"+gLEntryInfo.getAmount());
									gLEntryInfo.setSubjectCode(NameRef.getSubjectByBankID(bankLoanDrawInfo.getBankID()));
									gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
									sett_GLEntryDAO.add(gLEntryInfo);
								}		
								//贷202 其它应付款 （包含财务公司承贷部分）							
								gLEntryInfo.setAmount(transInfo.getAmount()*bankLoanDrawInfo.getRate()/100);
								log.info("-------贷202 其它应付款-----------"+gLEntryInfo.getAmount());
								gLEntryInfo.setSubjectCode(NameRef.getSubjectByBankID(bankLoanDrawInfo.getBankID()));
								gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
								sett_GLEntryDAO.add(gLEntryInfo);						
													
							}							
								
															
							//贷103  
							log.info("-------贷103 ---------"+transInfo.getRealInterestReceiveAble());	
							gLEntryInfo.setAmount(transInfo.getRealInterestReceiveAble());							
							gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
							if(gLEntryInfo.getAmount()!=0)
							{
								gLEntryInfo.setSubjectCode(accountOperation.getSubjectBySubAccountID(loanSubAccountID,accountOperation.SUBJECT_TYPE_PREDRAWINTEREST));
								sett_GLEntryDAO.add(gLEntryInfo);
							}
								
								
								
							for(int i=0;i<listSyndicationLoanInterest.size();i++)
							{
								SyndicationLoanInterestInfo syndicationLoanInterestInfo = new SyndicationLoanInterestInfo();	
								syndicationLoanInterestInfo = (SyndicationLoanInterestInfo)listSyndicationLoanInterest.get(i);									
									if(syndicationLoanInterestInfo.getIsHead()==1)
									{
									//贷501																	
									gLEntryInfo.setAmount(syndicationLoanInterestInfo.getCompoundInterest()+syndicationLoanInterestInfo.getForpeitInterest()+syndicationLoanInterestInfo.getInterest()-transInfo.getRealInterestReceiveAble());
									log.info("-------贷501 ---------"+gLEntryInfo.getAmount());									
									gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
									if(gLEntryInfo.getAmount()!=0)
									{
										gLEntryInfo.setSubjectCode(accountOperation.getSubjectBySubAccountID(loanSubAccountID,accountOperation.SUBJECT_TYPE_INTEREST));
										sett_GLEntryDAO.add(gLEntryInfo);
									}
									
								}
								else
								{							
									//贷202 其它应付款 （包含财务公司承贷部分）
									gLEntryInfo.setAmount(syndicationLoanInterestInfo.getCompoundInterest()+syndicationLoanInterestInfo.getForpeitInterest()+syndicationLoanInterestInfo.getInterest());
									log.info("------贷202 其它应付款  ---------"+gLEntryInfo.getAmount());	
									gLEntryInfo.setSubjectCode(NameRef.getSubjectByBankID(syndicationLoanInterestInfo.getBankID()));
									gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
									if(gLEntryInfo.getAmount()!=0)
									{
										sett_GLEntryDAO.add(gLEntryInfo);
									}
								}						
													
							}
							
						}	
						catch (Exception e) 
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
							throw new IRollbackException(ctx, "产生银团会计分录错误");
						}
					
					}
					else
					{	
						log.info("------------分笔处理----------");
						try 
						{					
							GLEntryInfo  gLEntryInfo = new GLEntryInfo();
							gLEntryInfo.setAbstract(transInfo.getAbstract());
							gLEntryInfo.setAmount(transInfo.getAmount());
							gLEntryInfo.setCheckUserID(transInfo.getCheckUserID());
							gLEntryInfo.setCurrencyID(transInfo.getCurrencyID());
							gLEntryInfo.setExecute(transInfo.getExecute());
							//gLEntryInfo.setGroup(info.getAbstract());
							gLEntryInfo.setInputUserID(transInfo.getInputUserID());
							gLEntryInfo.setInterestStart(transInfo.getInterestStart());
							//gLEntryInfo.setMultiCode(info.getAbstract());
							gLEntryInfo.setOfficeID(transInfo.getOfficeID());
							//gLEntryInfo.setPostStatusID(info.getAbstract());
							//gLEntryInfo.setStatusID(SETTConstant.BooleanValue.ISTRUE);
							gLEntryInfo.setStatusID(3);
						
							gLEntryInfo.setTransactionTypeID(transInfo.getTransactionTypeID());
							gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
							gLEntryInfo.setTransNo(transInfo.getTransNo());
							//gLEntryInfo.setType(info.getAbstract());
						
							//借212  	
							log.info("-------借212-----------"+transInfo.getAmount());
							gLEntryInfo.setAmount(transInfo.getAmount());					
							gLEntryInfo.setSubjectCode(accountOperation.getSubjectBySubAccountID(currentPaySubAccountID,AccountOperation.SUBJECT_TYPE_ACCOUNT));
							gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
							sett_GLEntryDAO.add(gLEntryInfo);
							
							for(int i=0;i<list.size();i++)
							{
								BankLoanDrawInfo bankLoanDrawInfo = new BankLoanDrawInfo();	
								bankLoanDrawInfo = (BankLoanDrawInfo)list.get(i);									
							
								if(bankLoanDrawInfo.getIsHead()==1)
								{
									//贷149								
									gLEntryInfo.setAmount(transInfo.getAmount()*bankLoanDrawInfo.getRate()/100);
									log.info("-------贷149----------"+gLEntryInfo.getAmount());
									gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
									gLEntryInfo.setSubjectCode(accountOperation.getSubjectBySubAccountID(loanSubAccountID,accountOperation.SUBJECT_TYPE_ACCOUNT));
									sett_GLEntryDAO.add(gLEntryInfo);
									//借202 财务公司
									log.info("-------借202 财务公司----------"+gLEntryInfo.getAmount());
									gLEntryInfo.setSubjectCode(NameRef.getSubjectByBankID(bankLoanDrawInfo.getBankID()));
									gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
									sett_GLEntryDAO.add(gLEntryInfo);
								}		
								//贷202 其它应付款 （包含财务公司承贷部分）							
								gLEntryInfo.setAmount(transInfo.getAmount()*bankLoanDrawInfo.getRate()/100);
								log.info("-------贷202 其它应付款-----------"+gLEntryInfo.getAmount());
								gLEntryInfo.setSubjectCode(NameRef.getSubjectByBankID(bankLoanDrawInfo.getBankID()));
								gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
								sett_GLEntryDAO.add(gLEntryInfo);						
													
							}	
							
							//利息有可能没有数据
							if(transInfo.getInterestBankID()>0)
							{
								//借102  	
								gLEntryInfo.setAmount(transInfo.getRealCompoundInterest()+transInfo.getRealOverDueInterest()+transInfo.getRealInterest());
								log.info("-------借102 -----------"+gLEntryInfo.getAmount());					
								gLEntryInfo.setSubjectCode(accountOperation.getSubjectByBankID(transInfo.getInterestBankID()));
								gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
								if(gLEntryInfo.getAmount()!=0)
								{
									sett_GLEntryDAO.add(gLEntryInfo);
								}
							}
							else
							{
//								借212  
								log.info("------借212 ---------"+transInfo.getRealCompoundInterest()+transInfo.getRealOverDueInterest()+transInfo.getRealInterest());		
								gLEntryInfo.setAmount(transInfo.getRealCompoundInterest()+transInfo.getRealOverDueInterest()+transInfo.getRealInterest());								
								gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
								if(gLEntryInfo.getAmount()!=0)
								{
									gLEntryInfo.setSubjectCode(accountOperation.getSubjectBySubAccountID(currentPayInterestSubAccountID,AccountOperation.SUBJECT_TYPE_ACCOUNT));
									sett_GLEntryDAO.add(gLEntryInfo);
								}
							}								
															
							//贷103  
							log.info("-------贷103 ---------"+transInfo.getRealInterestReceiveAble());	
							gLEntryInfo.setAmount(transInfo.getRealInterestReceiveAble());							
							gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
							if(gLEntryInfo.getAmount()!=0)
							{
								gLEntryInfo.setSubjectCode(accountOperation.getSubjectBySubAccountID(loanSubAccountID,accountOperation.SUBJECT_TYPE_PREDRAWINTEREST));
								sett_GLEntryDAO.add(gLEntryInfo);
							}
								
								
								
							for(int i=0;i<listSyndicationLoanInterest.size();i++)
							{
								SyndicationLoanInterestInfo syndicationLoanInterestInfo = new SyndicationLoanInterestInfo();	
								syndicationLoanInterestInfo = (SyndicationLoanInterestInfo)listSyndicationLoanInterest.get(i);									
									if(syndicationLoanInterestInfo.getIsHead()==1)
									{
									//贷501																	
									gLEntryInfo.setAmount(syndicationLoanInterestInfo.getCompoundInterest()+syndicationLoanInterestInfo.getForpeitInterest()+syndicationLoanInterestInfo.getInterest()-transInfo.getRealInterestReceiveAble());
									log.info("-------贷501 ---------"+gLEntryInfo.getAmount());					
									gLEntryInfo.setSubjectCode(accountOperation.getSubjectBySubAccountID(loanSubAccountID,accountOperation.SUBJECT_TYPE_INTEREST));
									gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
									if(gLEntryInfo.getAmount()!=0)
									{
										sett_GLEntryDAO.add(gLEntryInfo);
									}
									
								}
								else
								{							
									//贷202 其它应付款 （包含财务公司承贷部分）
									gLEntryInfo.setAmount(syndicationLoanInterestInfo.getCompoundInterest()+syndicationLoanInterestInfo.getForpeitInterest()+syndicationLoanInterestInfo.getInterest());
									log.info("------贷202 其它应付款  ---------"+gLEntryInfo.getAmount());	
									gLEntryInfo.setSubjectCode(NameRef.getSubjectByBankID(syndicationLoanInterestInfo.getBankID()));
									gLEntryInfo.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
									if(gLEntryInfo.getAmount()!=0)
									{
										sett_GLEntryDAO.add(gLEntryInfo);
									}
								}						
													
							}
							
						}	
						catch (Exception e) 
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
							throw new IRollbackException(ctx, "产生银团会计分录错误");
						}
					}
					//本金流向是 内部转账
					
							
					GeneralLedgerBean gl = new GeneralLedgerBean();
					log.info("----------检测借贷平衡-------------");
					try 
					{
						if(!gl.checkTransDCBalance(transInfo.getTransNo()))
						{
							throw new IRollbackException(ctx, "借贷不平衡！");											
						}
					} 
					catch (IException e1) 
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}	
				
				
				log.debug("-------银团贷款结束产生会计分录----------");
				*/
			}
			else
			{
				log.debug("-------开始产生会计分录----------");
				GenerateGLEntryParam param = new GenerateGLEntryParam();
				/**
				 * 如果收/付款银行账户ID>0，则本金 流向lPrincipalType =2银行，否则本金流向lPrincipalType =1 内部转账
				 */
				long lPrincipalType = -1;
				long lInterestType = -1;
				long lCommissionType = -1;
				if (transInfo.getBankID() > 0)
				{
					//本金流向是 银行
					lPrincipalType = SETTConstant.CapitalType.BANK;
				}
				else
				{
					//本金流向是 内部转账
					lPrincipalType = SETTConstant.CapitalType.INTERNAL;
				}
				if (transInfo.getInterestBankID() > 0)
				{
					//利息流向是 银行
					lInterestType = SETTConstant.CapitalType.BANK;
				}
				else
				{
					//利息流向是 内部转账
					lInterestType = SETTConstant.CapitalType.INTERNAL;
				}
								
				if (transInfo.getTransactionTypeID() == SETTConstant.TransactionType.INTERESTFEEPAYMENT)
				{
					if (transInfo.getCommissionAccountID() > 0)
					{
						lCommissionType = SETTConstant.CapitalType.INTERNAL;
					}
					else
						lCommissionType = SETTConstant.CapitalType.BANK;
				}
				//为利息费用支付使用(利息费用支付使用与贷款收回使用相同的会计分录设置)
				if (transInfo.getTransactionTypeID() == SETTConstant.TransactionType.INTERESTFEEPAYMENT)
				{
			        log.debug("---------判断账户类型------------");
					//long accountTypeID = resultInfo.getAccountTypeID();
			        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
			        AccountTypeInfo accountTypeInfo = null;
			        try {
						accountTypeInfo = sett_AccountTypeDAO.findByID(accountType);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					if (accountTypeInfo != null) {
						if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
						{
							transInfo.setTransactionTypeID(SETTConstant.TransactionType.CONSIGNLOANRECEIVE);
						}
						else
						{
							transInfo.setTransactionTypeID(SETTConstant.TransactionType.TRUSTLOANRECEIVE);
						}
					}
				}
				//委托贷款手续费
				if (transInfo.getTransactionTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
				{
					if (transInfo.getCommissionAccountID() > 0)
					{
						lCommissionType = SETTConstant.CapitalType.INTERNAL;
					}
					else
						lCommissionType = SETTConstant.CapitalType.BANK;
				}
				//信托贷款担保费
				if (transInfo.getTransactionTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE)
				{
					if (transInfo.getPaySuretyAccountID() > 0)
					{
						lCommissionType = SETTConstant.CapitalType.INTERNAL;
					}
					else
						lCommissionType = SETTConstant.CapitalType.BANK;
				}
				//分录类型
				long lEntryType = transInfo.getCapitalAndInterstDealway();
				if (loanSubAccountID == -1) //利息费用支付
					loanSubAccountID = accountOperation.getLoanSubAccountIDByAccountIDAndLoanNoteID(transInfo.getLoanAccountID(), transInfo.getLoanNoteID());
				//收款方账户
				long receiveAccountID = loanSubAccountID;
				//付款方账户
				long payAccountID = currentPaySubAccountID;
				////收息账户ID
				long receiveInterestAccountID = currentReceiveInterestSubAccountID;
				//付息账户ID
				long payInterestAccountID = currentPayInterestSubAccountID;
				//收担保费账户ID
				long receieveSuertyFeeAccountID = currentReceiveSuretySubAccountID;
				//付担保费账户ID
				long paySuertyFeeAccountID = currentPaySuretySubAccountID;
				//付手续费账户ID
				long payCommissionAccountID = currentPayCommissionSubAccountID;
				//委托收款方账户ID
				long vouchReceiveAccountID = receieveConsignSubAccountID;
				//委托付款方账户ID
				long vouchPayAccountID = payConsignSubAccountID;
				//本金开户行ID
				long principalBankID = transInfo.getBankID();
				//利息开户行ID
				long interestBankID = transInfo.getInterestBankID();
				//费用开户行
				long feeBankID = -1;
				if (transInfo.getCommissionBankID() > 0)
					feeBankID = transInfo.getCommissionBankID();
				else if (transInfo.getSuretyBankID() > 0)
					feeBankID = transInfo.getSuretyBankID();
				//还款金额
				double dAmount = 0.0;
				//免还金额
				double dRemitAmount = 0.0;
				if (transInfo.getFreeFormID() < 0) //不是免还
					dAmount = transInfo.getAmount();
				else
					dRemitAmount = transInfo.getAmount();
				//利息合计
				double totalInterest = interest;
				log.debug("利息合计是:" + totalInterest);
				
				//计提利息
				double preDrawInterest = 0.0;
				if(transInfo.getRealInterestReceiveAble() > 0)
				{
					preDrawInterest = transInfo.getRealInterestReceiveAble();
				}
				//if(transInfo.getInterestReceiveAble() > 0)
				//{
				//	preDrawInterest = transInfo.getInterestReceiveAble();
				//}
				
				//实际支付本次利息
				double unPreDrawInterest = transInfo.getRealInterestIncome();
				//逾期罚息
				double overFee = transInfo.getRealOverDueInterest();
				//复利
				double compoundInterest = transInfo.getRealCompoundInterest();
				//担保费
				double suretyFee = transInfo.getRealSuretyFee();
				//手续费
				double commissionFee = transInfo.getRealCommission();
				//利息税费
				double interestTaxFee = transInfo.getRealInterestTax();
				//本息合计
				double totalPrincipalAndInterest = dAmount + totalInterest + suretyFee + commissionFee;
				log.debug("本息合计是:" + totalPrincipalAndInterest);
				//实收利息
				double reallyReceiveInterest = interest - interestTaxFee;
				double remissionInterest = 0.0;
				
				//是否免还剩余贷款利息
				if (transInfo.getIsRemitInterest() == 1)
				{
					remissionInterest = transInfo.getInterestReceiveAble() - transInfo.getRealInterestReceiveAble();
				}
				else
				{
					remissionInterest = 0.0;
				}
					
				param.setOfficeID(transInfo.getOfficeID());
				param.setCurrencyID(transInfo.getCurrencyID());
				param.setTransactionTypeID(transInfo.getTransactionTypeID());
				param.setExecuteDate(transInfo.getExecute());
				param.setInterestStartDate(transInfo.getInterestStart());
				param.setTransNo(transInfo.getTransNo());
				param.setAbstractStr(transInfo.getAbstract());
				param.setInputUserID(transInfo.getInputUserID());
				param.setCheckUserID(transInfo.getCheckUserID());
				param.setPrincipalType(lPrincipalType);
				param.setInterestType(lInterestType);
				param.setCommisionType(lCommissionType);
				param.setEntryType(lEntryType);
				param.setReceiveAccountID(receiveAccountID);
				param.setPayAccountID(payAccountID);
				param.setPayInterestAccountID(payInterestAccountID);
				param.setReceiveInterestAccountID(receiveInterestAccountID);
				param.setPaySuertyFeeAccountID(paySuertyFeeAccountID);
				param.setReceieveSuertyFeeAccountID(receieveSuertyFeeAccountID);
				param.setPayCommissionAccountID(payCommissionAccountID);
				param.setVouchPayAccountID(vouchPayAccountID);
				param.setVouchReceiveAccountID(vouchReceiveAccountID);
				param.setFeeBankID(feeBankID);
				param.setInterestBankID(interestBankID);
				param.setPrincipalBankID(principalBankID);
				param.setPrincipalOrTransAmount(dAmount);
				param.setTotalInterest(totalInterest);
				
				param.setPreDrawInterest(preDrawInterest);
				param.setUnPreDrawInterest(unPreDrawInterest);
				
				param.setOverFee(overFee);
				param.setCommissionFee(commissionFee);
				param.setCompoundInterest(compoundInterest);
				param.setSuretyFee(suretyFee);
				param.setInterestTaxFee(interestTaxFee);
				param.setTotalPrincipalAndInterest(totalPrincipalAndInterest);
				param.setReallyReceiveInterest(reallyReceiveInterest);
				param.setRemitAmount(dRemitAmount);
				
				//免还的利息 added by mzh_fu 2008/01/31
				param.setRemissionInterest(remissionInterest);
				
				//added for 利息税费
				if (param.getInterestTaxFee() > 0)
					param.setSubTransactionType(SETTConstant.SubTransactionType.INTERESTTAX);
				
				boolean res = glopOperation.generateGLEntry(param);
				if (!res)
				{
					throw new IRollbackException(ctx, "借贷不平衡，交易失败");
				}
				log.debug("-------结束产生会计分录----------");
			}
		}
		log.debug("--------结束贷款收回复核------------");
		/*
		log.debug("--------生成贷款收回指令------------");
		if ( transType!=SETTConstant.TransactionType.INTERESTFEEPAYMENT && (transInfo.getDepositAccountID() > 0 || transInfo.getPayInterestAccountID()>0 ))
		{
			//是否有银企接口
			boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
			//是否需要生成银行指令
			//boolean bCreateInstruction = false;
//			long bankID = transInfo.getNpaybankid();
//			try {
//				//调用此方法后，bankID的值变为银行类型ID
//				bCreateInstruction = this.isCreateInstruction(bankID);
//			} catch (Exception e1) {				
//				log.error("判断传入的银行ID是否需要生成银行指令出错！");
//				e1.printStackTrace();
//			}
			
			
			try
			{
				if(bIsValid) {
					Log.print("*******************开始产生贷款收回指令，并保存**************************");
					try {
						log.debug("------开始贷款收回--------");
						//构造参数
						CreateInstructionParam instructionParam = new CreateInstructionParam();
						instructionParam.setTransactionTypeID(transInfo.getTransactionTypeID());
						instructionParam.setObjInfo(transInfo);
						instructionParam.setOfficeID(transInfo.getOfficeID());
						instructionParam.setCurrencyID(transInfo.getCurrencyID());
						instructionParam.setCheckUserID(transInfo.getCheckUserID());
						//instructionParam.setBankType(transInfo.get);
						instructionParam.setInputUserID(transInfo.getInputUserID());
						
						//生成银行指令并保存
						IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
						bankInstruction.createBankInstruction(instructionParam);
						
						log.debug("------生成贷款收回指令成功--------");
						
					} catch (Throwable e) {
						log.error("生成贷款收回指令失败");
						e.printStackTrace();
						throw new IRollbackException(ctx, "生成贷款收回指令失败："+e.getMessage());
					}
				}	
				else {
					Log.print("没有银行接口或不需要生成银行指令！");
				}
			}
			catch (Exception e)
			{
				log.debug("-----保存贷款收回指令指令失败");
				throw new IRollbackException(this.ctx, "保存贷款收回指令出错！" + e.getMessage());
			}
		}
		*/
	}
	
	/**
	 * 自营贷款收回、委托贷款收回等交易取消复核
	 * 
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelCheckRepaymentLoan(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException
	{
		cancelCheckRepaymentLoan(transInfo, false);
	}
	private void cancelCheckRepaymentLoan(TransRepaymentLoanInfo transInfo, boolean isMultiple) throws RemoteException, IRollbackException
	{
		log.debug("--------开始贷款收回取消复核------------");
		
		log.debug(UtilOperation.dataentityToString(transInfo));
		//实际支付贷款利息+实际支付复利+实际支付逾期罚息
		if (transInfo.getTransactionTypeID() == SETTConstant.TransactionType.INTERESTFEEPAYMENT)
		{   
			if(transInfo.getCommissionAccountID()<0&&transInfo.getCommissionBankID()<0){
				transInfo.setCommissionAccountID(transInfo.getPayInterestAccountID());
				transInfo.setCommissionBankID(transInfo.getInterestBankID());
			}
			//transInfo.setCommissionAccountID(transInfo.getPayInterestAccountID());
			transInfo.setPaySuretyAccountID(transInfo.getPayInterestAccountID());		
			transInfo.setSuretyBankID(transInfo.getInterestBankID());
		}
		double interest = transInfo.getInterest() + transInfo.getCompoundInterest() + transInfo.getOverDueInterest();
		if (transInfo.getDepositAccountID() > 0)
		{
			TransAccountDetailInfo currentTadi = transferTransRepaymentLoanInfoToTransAccountDetailInfo(transInfo, RepaymentLoan_WITHDRAW_DIV_AMOUNT);
			//活期支取反交易
			log.debug("------开始活期支取本金反交易-----------");
			accountOperation.cancelWithdrawCurrent(currentTadi);
			log.debug("------结束活期支取本金反交易-----------");
		}
		if (transInfo.getPayInterestAccountID() > 0 && interest > 0)
		{
			//活期支取利息反交易
			log.debug("------开始活期支取利息反交易-----------");
			TransAccountDetailInfo interestTadi = transferTransRepaymentLoanInfoToTransAccountDetailInfo(transInfo, RepaymentLoan_WITHDRAW_DIV_INTEREST);
			accountOperation.cancelWithdrawCurrent(interestTadi);
			log.debug("------结束活期利息支取反交易-----------");
		}
		if (transInfo.getPaySuretyAccountID() > 0 && transInfo.getRealSuretyFee() > 0)
		{
			//活期支取担保费反交易
			log.debug("------开始活期支取担保费反交易-----------");
			TransAccountDetailInfo suretyTadi = transferTransRepaymentLoanInfoToTransAccountDetailInfo(transInfo, RepaymentLoan_WITHDRAW_DIV_SURETY);
			accountOperation.cancelWithdrawCurrent(suretyTadi);
			log.debug("------结束活期担保费支取反交易-----------");
		}
		System.out.println(transInfo.getCommissionAccountID()+"transInfo.getRealCommission()--------++++++"+transInfo.getRealCommission());
		if (transInfo.getCommissionAccountID() > 0  && transInfo.getRealCommission() > 0)
		{
			//活期支取手续费反交易
			log.debug("------开始活期支取手续费反交易-----------");
			TransAccountDetailInfo suretyTadi = transferTransRepaymentLoanInfoToTransAccountDetailInfo(transInfo, RepaymentLoan_WITHDRAW_DIV_COMMISSION);
			accountOperation.cancelWithdrawCurrent(suretyTadi);
			log.debug("------结束活期手续费支取反交易-----------");
		}
		if (transInfo.getReceiveInterestAccountID() > 0)
		{
			//活期存入利息反交易
			log.debug("------开始活期存入利息反交易-----------");
			TransAccountDetailInfo interestTadi = transferTransRepaymentLoanInfoToTransAccountDetailInfo(transInfo, RepaymentLoan_DEPOSIT_INTEREST);
			accountOperation.cancelDepositCurrent(interestTadi);
			log.debug("------结束活期利息存入反交易-----------");
		}
		if (transInfo.getReceiveSuretyAccountID() > 0)
		{
			//活期存入担保费反交易
			log.debug("------开始活期存入担保费反交易-----------");
			TransAccountDetailInfo suretyTadi = transferTransRepaymentLoanInfoToTransAccountDetailInfo(transInfo, RepaymentLoan_DEPOSIT_SURETY);
			accountOperation.cancelDepositCurrent(suretyTadi);
			log.debug("------结束活期担保费存入反交易-----------");
		}		
	
		long loanSubAccountID = -1;
		if (transInfo.getLoanAccountID() > 0)
		{
			log.debug("------开始贷款归还反交易-----------");
			TransAccountDetailInfo loanTadi = transferTransRepaymentLoanInfoToTransAccountDetailInfo(transInfo, RepaymentLoan_GRANT_AMOUNT);
			if( transInfo.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
			{
				/*log.info("-------------银团贷款特殊处理贷款账户金额--------------");
				double dRate = 0.0;
				BankLoanQuery bankLoanQuery =new BankLoanQuery();
				try 
				{
					dRate=bankLoanQuery.findRateByFormID(transInfo.getLoanNoteID());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new IRollbackException(this.ctx, "取财务公司承贷比例出错！");
				}
				loanTadi.setAmount(loanTadi.getAmount()*dRate/100);*/
				double dAmount = 0.0;
				/* modify by zcwang 2007-06-25
				ArrayList syList = new ArrayList();
				syList=transInfo.getSyndicationLoanInterest();
				if(syList!=null && syList.size()>0)
				{
					Iterator it =null;
					it=syList.iterator();
					
					while(it!=null && it.hasNext())
					{					
						SyndicationLoanInterestInfo info =(SyndicationLoanInterestInfo)it.next();
						if(info.getIsHead()==1)
						{
							dAmount = info.getAmount();
						}						
					}
				}
				loanTadi.setAmount(dAmount);	
				
				*/
								
			}
			loanSubAccountID = accountOperation.cancelRepayLoan(loanTadi);
			//loanTadi.setTransSubAccountID(loanSubAccountID);
			if(transInfo.getRealCommission()>0){
				 loanTadi = transferTransRepaymentLoanInfoToTransAccountDetailInfo(transInfo, RepaymentLoan_DEPOSIT_COMMISSION);
				 loanTadi.setTransSubAccountID(loanSubAccountID);
				 loanTadi.setAmount(transInfo.getRealCommission());
				 loanTadi.setAmountType(SETTConstant.AmountType.AmountType_9);
				 loanSubAccountID=accountOperation.updateCommission(loanTadi);
			}
			log.debug("------结束贷款归还反交易-----------");
		}
		if (transInfo.getConsignAccountID() > 0)
		{
			log.debug("------委托存款支取反交易开始-------");
			TransAccountDetailInfo withdrawTadi = transferTransRepaymentLoanInfoToTransAccountDetailInfo(transInfo, RepaymentLoan_CONSIGN_WITHDRAW);
			accountOperation.cancelWithdrawCurrent(withdrawTadi);
			log.debug("------委托存款支取反交易结束-------");
		}
		if (transInfo.getConsignDepositAccountID() > 0)
		{
			log.debug("------委托贷款存入反交易开始-------");
			TransAccountDetailInfo depositTadi = transferTransRepaymentLoanInfoToTransAccountDetailInfo(transInfo, RepaymentLoan_CONSIGN_DEPOSIT);
			accountOperation.cancelDepositCurrent(depositTadi);
			log.debug("------委托贷款存入反交易结束-------");
		}
		//modified by mzh_fu 2008/03/14解决内存泄露问题
		//InterestBatch ib = new InterestBatch();
		//如果结息（利息信息>0），若起息日期<执行日期，则调用单户结息倒填处理
		if (interest > 0 || (interest == 0 && transInfo.getIsRemitInterest() == SETTConstant.BooleanValue.ISTRUE))
		{
			InterestBatch ib = new InterestBatch();
			try
			{
				Timestamp backDate = null;
				if (transInfo.getTransactionTypeID() == SETTConstant.TransactionType.INTERESTFEEPAYMENT)
					backDate = transInfo.getInterestClear();
				else
					backDate = transInfo.getInterestStart();
				if(transInfo.getAmount()!=0)
				{
					transInfo.setAmount(-transInfo.getAmount());
				}
				else
				{
					transInfo.setAmount(-1);
				}
				if (backDate.before(transInfo.getExecute()))
				{
					ib.accountInterestSettlelmentBackward(transInfo);
				}
				//贷款账户结息/费用
				ClearLoanAccountInterestConditionInfo claicInfo = new ClearLoanAccountInterestConditionInfo();
				claicInfo.setAccountID(transInfo.getLoanAccountID());
				claicInfo.setCommision(transInfo.getCommission());
				claicInfo.setCompoundInterest(transInfo.getCompoundInterest());
				claicInfo.setInterest(transInfo.getInterest());
				claicInfo.setInterestDate(transInfo.getLatestInterestClear());
				claicInfo.setInterestReceivable(transInfo.getInterestReceiveAble());
				claicInfo.setIsRemitCommision(transInfo.getIsRemitCommission());
				claicInfo.setIsRemitCompoundInterest(transInfo.getIsRemitCompoundInterest());
				claicInfo.setIsRemitInterest(transInfo.getIsRemitInterest());
				claicInfo.setIsRemitOverDueInterest(transInfo.getIsRemitOverDueInterest());
				claicInfo.setIsRemitSuretyFee(transInfo.getIsRemitSuretyFee());
				claicInfo.setOverDueInterest(transInfo.getOverDueInterest());
				claicInfo.setRealCommission(transInfo.getRealCommission());
				claicInfo.setRealCompoundInterest(transInfo.getRealCompoundInterest());
				claicInfo.setRealInterest(transInfo.getRealInterest());
				claicInfo.setRealInterestReceivable(transInfo.getRealInterestReceiveAble());
				claicInfo.setRealOverDueInterest(transInfo.getRealOverDueInterest());
				claicInfo.setRealSuretyFee(transInfo.getRealSuretyFee());
				claicInfo.setSubAccountID(loanSubAccountID);
				claicInfo.setSuretyFee(transInfo.getSuretyFee());
				claicInfo.setClearInterestDate(transInfo.getInterestClear());
				/*
				if(transInfo.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
				{
					log.info("-------------银团贷款特殊处理结息利息--------------");
					double dRate = 0.0;
					BankLoanQuery bankLoanQuery =new BankLoanQuery();
					try 
					{
						dRate=bankLoanQuery.findRateByFormID(transInfo.getLoanNoteID());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw new IRollbackException(this.ctx, "取财务公司承贷比例出错！");
					}
					
					claicInfo.setRealCompoundInterest(claicInfo.getRealCompoundInterest()*dRate/100);
					claicInfo.setRealInterest(claicInfo.getRealInterest()*dRate/100);				
					claicInfo.setRealOverDueInterest(claicInfo.getRealOverDueInterest()*dRate/100);
					claicInfo.setCompoundInterest(claicInfo.getCompoundInterest()*dRate/100);
					claicInfo.setInterest(claicInfo.getInterest()*dRate/100);				
					claicInfo.setOverDueInterest(claicInfo.getOverDueInterest()*dRate/100);	
				}
				*/
				ib.clearLoanAccountInterestReverse(claicInfo);
			}
			catch (IException e)
			{
				throw new IRollbackException(ctx, e.toString(), e);
			}
			/**addded by mzh_fu 2008/03/14 解决内存泄露问题 begin*/
			finally{
				try {
					ib.closeConnection();
				} catch (IException e) {
					throw new IRollbackException(ctx, e.getMessage(), e);
				}
			}
			/**addded by mzh_fu 2008/03/14 解决内存泄露问题 end*/
		}
		if (!isMultiple)
		{
			//删除明细账
			accountOperation.deleteTransAccountDetail(transInfo.getTransNo());
			//通存通兑反交易处理
			interbranchSettlementReverse();
			if (transInfo.getTransactionTypeID() == SETTConstant.TransactionType.MULTILOANRECEIVE)
				glopOperation.deleteGLEntry(transInfo.getTempTransNO());
			else
				glopOperation.deleteGLEntry(transInfo.getTransNo());
		}
		else
		{
			//删除明细账
			accountOperation.deleteTransAccountDetail(transInfo.getTempTransNO());
		}
		log.debug("--------结束贷款收回取消复核------------");
	}
	/**
	 * 贴现发放交易保存
	 */
	public void saveGrantDiscount(TransGrantDiscountInfo transInfo) throws RemoteException, IRollbackException
	{
		
		//检查活期账户是否可以做收付款操作
		if( transInfo.getDepositAccountID() > 0 )
		{
			log.debug("------检查活期账户是否可以做收付款操作--------");
			long currentSubDepositAccountID = accountOperation.getCurrentSubAccoutIDByAccoutID(transInfo.getDepositAccountID());
			log.info("活期存款账户ID:" + transInfo.getDepositAccountID());

			//修改 by kenny(胡志强) (2007-03-19) 处理保存时校验账户状态的操作
			//如果该交易有收/付款方，且收/付款方为内部账户，则校验收/付款方账户状态
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(transInfo.getDepositAccountID(),AccountBean.TRANSTYPE_WITHDRAW);
			} catch (IException e) {
				e.printStackTrace();
				// 提示“系统忙”
				//modified by mzh_fu 2007/06/01 提示准确错误信息
		           // throw new IRollbackException(null,"Gen_E001");
					throw new IRollbackException(ctx,e);
			}
		}

		log.debug("------开始保存贴现发放交易--------");
		//如果该交易有出票人活期账户，并且出票人承担利息大于零，则调用“累计未复核金额”的方法
		log.info("出票人活期账户:" + transInfo.getSignBillAccountID());
		if (transInfo.getSignBillAccountID() > 0) 
		{
			//修改 by kenny(胡志强) (2007-03-19) 处理保存时校验账户状态的操作
			//如果该交易有收/付款方，且收/付款方为内部账户，则校验收/付款方账户状态
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(transInfo.getSignBillAccountID(),AccountBean.TRANSTYPE_WITHDRAW);
			} catch (IException e) {
				e.printStackTrace();
				// 提示“系统忙”
				//modified by mzh_fu 2007/06/01 提示准确错误信息
		           // throw new IRollbackException(null,"Gen_E001");
				throw new IRollbackException(ctx,e);
			}
		}

		log.info("出票人承担利息:" + transInfo.getInterestOfSign());
		if (transInfo.getSignBillAccountID() > 0 && transInfo.getInterestOfSign() > 0.0)
		{
			accountOperation.addCurrentUncheckAmount(transInfo.getSignBillAccountID(),-1, transInfo.getInterestOfSign());
		}
		log.debug("------结束保存贴现发放交易--------");
	}
	/**
	 * 贴现发放交易删除
	 */
	public void deleteGrantDiscount(TransGrantDiscountInfo transInfo) throws RemoteException, IRollbackException
	{
		log.debug("------开始删除贴现发放交易--------");
		//如果该交易有出票人活期账户，并且出票人承担利息大于零，则调用“扣除累计未复核金额”的方法
		log.info("出票人活期账户:" + transInfo.getSignBillAccountID());
		log.info("出票人承担利息:" + transInfo.getInterestOfSign());
		
		if (transInfo.getSignBillAccountID() > 0 && transInfo.getInterestOfSign() > 0.0)
		{
			accountOperation.subtractCurrentUncheckAmount(transInfo.getSignBillAccountID(), transInfo.getInterestOfSign());
		}
		log.debug("------开始删除贴现发放交易--------");
	}
	/**
	 * 贴现发放交易复核
	 */
	public void checkGrantDiscount(TransGrantDiscountInfo transInfo) throws RemoteException, IRollbackException
	{
		log.debug("------开始复核贴现发放交易--------");
		SubAccountLoanInfo sali = transferTransGrantDiscountInfoToSubAccountLoanInfo(transInfo);
		log.debug(UtilOperation.dataentityToString(sali));
		log.debug("------开始开立贷款子账户--------");
		long subLoanAccoutID = accountOperation.openLoanSubAccount(sali);
		log.debug("------结束开立贷款子账户,ID是: " + subLoanAccoutID + "--------");
		log.debug("------开始贷款放款--------");
		TransAccountDetailInfo tadi = transferTransGrantDiscountInfoToTransAccountDetail(transInfo);
		tadi.setTransSubAccountID(subLoanAccoutID);
		log.debug(UtilOperation.dataentityToString(tadi));
		accountOperation.grantLoan(tadi);
		log.debug("------结束贷款放款--------");
		
		//如果该交易有出票人活期账户，并且出票人承担利息大于零，则出票人活期账户扣除出票人承担利息
		log.info("出票人活期账户:" + transInfo.getSignBillAccountID());
		log.info("出票人承担利息:" + transInfo.getInterestOfSign());
		long SignBillCurrentSubAccountID = -1;
		//为账户对账单信息查询 所加
		if(transInfo.getDiscountAccountID()>0)
		{
			Sett_AccountDAO sett_accountDao = new Sett_AccountDAO();
			try {
				AccountInfo accountInfo = sett_accountDao.findByID(transInfo.getDiscountAccountID());
				tadi.setOppAccountNo(accountInfo.getAccountNo());
				tadi.setOppAccountName(accountInfo.getAccountName());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//
		if (transInfo.getSignBillAccountID() > 0 && transInfo.getInterestOfSign() > 0.0)
		{
			log.debug("--------开始出票人活期账户扣除出票人承担利息------------");
			tadi.setTransAccountID(transInfo.getSignBillAccountID());
			tadi.setOppAccountID(-1);
			tadi.setOppSubAccountID(subLoanAccoutID);
			tadi.setAmount(transInfo.getInterestOfSign());
			SignBillCurrentSubAccountID = accountOperation.withdrawCurrent(tadi);
			log.debug("--------结束出票人活期账户扣除出票人承担利息------------");
		}
		
		long depositCurrentSubAccountID = -1;
		////////////////
		if (transInfo.getDepositAccountID() > 0)
		{ //放款途径是活期
			log.debug("--------放款途径是活期,开始活期存入------------");
			tadi.setTransAccountID(transInfo.getDepositAccountID());
			tadi.setOppAccountID(transInfo.getDiscountAccountID());
			tadi.setOppSubAccountID(subLoanAccoutID);
			//向活期子账户中存入的是实际贴现金额(票面金额-利息)
			tadi.setAmount(transInfo.getDiscountAmount());
			depositCurrentSubAccountID = accountOperation.depositCurrent(tadi);
			log.debug("--------结束活期存入------------");			
//
			//是否有银企接口
//			boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
//			//是否需要生成银行指令
//			boolean bCreateInstruction = false;
//			long bankID = transInfo.getBankID();
//			try {
//				//调用此方法后，bankID的值变为银行类型ID
//				bCreateInstruction = this.isCreateInstruction(bankID);
//			} catch (Exception e1) {				
//				log.error("判断传入的银行ID是否需要生成银行指令出错！");
//				e1.printStackTrace();
//			}
//			
//			try
//			{
//				if(bIsValid) {
//					log.debug("------开始贴现发放交易指令生成--------");
//					try {
//						log.debug("------开始贴现发放交易指令生成--------");
//						//构造参数
//						CreateInstructionParam instructionParam = new CreateInstructionParam();
//						instructionParam.setTransactionTypeID(transInfo.getTransactionTypeID());
//						instructionParam.setObjInfo(transInfo);
//						instructionParam.setOfficeID(transInfo.getOfficeID());
//						instructionParam.setCurrencyID(transInfo.getCurrencyID());
//						instructionParam.setCheckUserID(transInfo.getCheckUserID());
//						instructionParam.setBankType(bankID);
//						instructionParam.setInputUserID(transInfo.getInputUserID());
//						
//						//生成银行指令并保存
//						IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
//						bankInstruction.createBankInstruction(instructionParam);
//						
//						log.debug("------生成贴现发放交易指令成功--------");
//						
//					} catch (Throwable e) {
//						log.error("生成贴现发放交易指令失败");
//						e.printStackTrace();
//						throw new IRollbackException(ctx, "生成贴现发放交易指令失败："+e.getMessage());
//					}
//					log.debug("------结束贴现发放交易指令生成--------");
//				}
//				else {
//					Log.print("没有银行接口或不需要生成银行指令！");
//				}
//			}
//			catch (Exception e)
//			{
//				throw new IRollbackException(this.ctx, "保存银行转账指令出错！" + e.getMessage());
//			}
		
		}
		else if (transInfo.getBankID() > 0)
		{
			//是否有银企接口
			boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
			//是否需要生成银行指令
			boolean bCreateInstruction = false;
			long bankID = transInfo.getBankID();
			try {
				//调用此方法后，bankID的值变为银行类型ID
				bCreateInstruction = this.isCreateInstruction(bankID);
			} catch (Exception e1) {				
				log.error("判断传入的银行ID是否需要生成银行指令出错！");
				e1.printStackTrace();
			}
			
			try
			{
				if(bIsValid) {
					log.debug("------开始贴现发放交易指令生成--------");
					try {
						log.debug("------开始贴现发放交易指令生成--------");
						//构造参数
						CreateInstructionParam instructionParam = new CreateInstructionParam();
						instructionParam.setTransactionTypeID(transInfo.getTransactionTypeID());
						instructionParam.setObjInfo(transInfo);
						instructionParam.setOfficeID(transInfo.getOfficeID());
						instructionParam.setCurrencyID(transInfo.getCurrencyID());
						instructionParam.setCheckUserID(transInfo.getCheckUserID());
						instructionParam.setBankType(bankID);
						instructionParam.setInputUserID(transInfo.getInputUserID());
						
						//生成银行指令并保存
						IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
						bankInstruction.createBankInstruction(instructionParam);
						
						log.debug("------生成贴现发放交易指令成功--------");
						
					} catch (Throwable e) {
						log.error("生成贴现发放交易指令失败");
						e.printStackTrace();
						throw new IRollbackException(ctx, "生成贴现发放交易指令失败："+e.getMessage());
					}
					log.debug("------结束贴现发放交易指令生成--------");
				}
				else {
					Log.print("没有银行接口或不需要生成银行指令！");
				}
			}
			catch (Exception e)
			{
				throw new IRollbackException(this.ctx, "保存银行转账指令出错！" + e.getMessage());
			}
		}
		log.debug("--------开始产生会计分录------------");
		GenerateGLEntryParam param = new GenerateGLEntryParam();
		/**
		 * 如果收/付款银行账户ID>0，则本金 流向lPrincipalType =2银行，否则本金流向lPrincipalType =1 内部转账
		 */
		long lPrincipalType = -1;
		if (transInfo.getBankID() > 0)
		{ //本金流向是 银行
			lPrincipalType = SETTConstant.CapitalType.BANK;
		}
		else
		{
			//本金流向是 内部转账
			lPrincipalType = SETTConstant.CapitalType.INTERNAL;
		}
		//分录类型 无关
		long lEntryType = SETTConstant.EntryType.IRRESPECTIVE;
		//收款方账户
		long receiveAccountID = depositCurrentSubAccountID;
		//付款方账户
		long payAccountID = subLoanAccoutID;
		//出票人付息账户
		long signBillPayInterestAccountID = SignBillCurrentSubAccountID;
		
		long principleBankID = transInfo.getBankID();
		double dAmount = transInfo.getDiscountAmount();
		//本息合计
		double totalPrincipalAndInterest = transInfo.getDiscountBillAmount();
		double totalInterest = transInfo.getInterest();//利息合计
		double interestOfSign = transInfo.getInterestOfSign();//出票人承担利息
		double interestOfDiscount = transInfo.getInterestOfDiscount();//贴现人承担利息
		param.setReceiveAccountID(receiveAccountID);
		param.setPayAccountID(payAccountID);
		param.setPayCommissionAccountID(signBillPayInterestAccountID);
		param.setPrincipalBankID(principleBankID);
		param.setPrincipalType(lPrincipalType);
		param.setEntryType(lEntryType);
		
		param.setPrincipalOrTransAmount(dAmount);
		param.setTotalPrincipalAndInterest(totalPrincipalAndInterest);
		param.setTotalInterest(totalInterest);
		param.setSuretyFee(interestOfDiscount);//担保费和贴现人承担利息共用
		param.setCommissionFee(interestOfSign);//手续费和出票人承担利息共用
		param.setTransactionTypeID(transInfo.getTransactionTypeID());
		param.setTransNo(transInfo.getTransNo());
		param.setOfficeID(transInfo.getOfficeID());
		param.setCurrencyID(transInfo.getCurrencyID());
		param.setExecuteDate(transInfo.getExecuteDate());
		param.setInterestStartDate(transInfo.getInterestStartDate());
		param.setAbstractStr(transInfo.getAbstract());
		param.setInputUserID(transInfo.getInputUserID());
		param.setCheckUserID(transInfo.getCheckUserID());
		boolean res = glopOperation.generateGLEntry(param);
		if (!res)
		{
			throw new IRollbackException(ctx, "产生会计分录错误2");
		}
		
		
		log.debug("--------结束产生会计分录------------");
		log.debug("------结束复核贴现发放交易--------");
	}
	/**
	 * 贴现发放交易取消复核
	 */
	public void cancelCheckGrantDiscount(TransGrantDiscountInfo transInfo) throws RemoteException, IRollbackException
	{
		log.debug(UtilOperation.dataentityToString(transInfo));
		log.debug("--------开始取消复核贴现发放复核------------");
		if (transInfo.getDepositAccountID() > 0)
		{
			log.debug("--------放款途径是活期,开始活期存入反交易------------");
			TransAccountDetailInfo tadi = new TransAccountDetailInfo();
			tadi.setTransAccountID(transInfo.getDepositAccountID());
			tadi.setAmount(transInfo.getDiscountAmount());
			accountOperation.cancelDepositCurrent(tadi);
			log.debug("--------结束活期存入反交易------------");
		}
		if (transInfo.getSignBillAccountID() > 0 && transInfo.getInterestOfSign() > 0)
		{
			log.debug("--------出票人活期账户ID>0,并且出票人利息>0,执行出票人活期支出反交易------------");
			TransAccountDetailInfo tadi = new TransAccountDetailInfo();
			tadi.setTransAccountID(transInfo.getSignBillAccountID());
			tadi.setAmount(transInfo.getInterestOfSign());
			accountOperation.cancelWithdrawCurrent(tadi);
			log.debug("--------结束出票人活期支出反交易-----------");
		}
		
		//log.debug("--------开始贷款开空户反交易------------");
		//accountOperation.deleteLoanSubAccount(info.getLoanAccountID(),
		// info.getLoanNoteID());
		
		//log.debug("--------结束贷款开空户反交易------------");
		log.debug("--------开始贷款发放户反交易------------");
		TransAccountDetailInfo tadi = new TransAccountDetailInfo();
		tadi.setTransAccountID(transInfo.getDiscountAccountID());
		tadi.setLoanNoteID(transInfo.getDiscountNoteID());
		tadi.setAmount(transInfo.getDiscountBillAmount());
		accountOperation.cancelGrantLoan(tadi);
		
		//added by mzh_fu 2007/08/08 取消复核时需将子账户删除
		long lLoanSubAccountId = accountOperation
				.getLoanSubAccountIDByAccountIDAndLoanNoteIDAndStatus(transInfo
						.getDiscountAccountID(), transInfo.getDiscountNoteID(),
						SETTConstant.SubAccountStatus.FINISH);
		accountOperation.deleteLoanSubAccount(lLoanSubAccountId);		
		
		log.debug("--------结束贷款发放户反交易------------");
		accountOperation.deleteTransAccountDetail(transInfo.getTransNo());
		//	通存通兑反交易处理AccountBookBizlogic.InterbranchSettlementReverse()
		glopOperation.deleteGLEntry(transInfo.getTransNo());		

		log.debug("--------结束取消复核贷款发放复核------------");
	}
	/** 贴现收回保存 */
	public void saveRepaymentDiscount(TransRepaymentDiscountInfo transInfo) throws RemoteException, IRollbackException
	{
		/** 检查付款方活期存款账户号是否可以收付款 */
		if (transInfo.getNDepositAccountID() > 0)
		{
			long depositAccountID = accountOperation.getCurrentSubAccoutIDByAccoutID(transInfo.getNDepositAccountID());
			log.info("付款方活期存款账户ID:" + transInfo.getNDepositAccountID());

			//修改 by kenny(胡志强) (2007-03-19) 处理保存时校验账户状态的操作
			//如果该交易有收/付款方，且收/付款方为内部账户，则校验收/付款方账户状态
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(transInfo.getNDepositAccountID(),AccountBean.TRANSTYPE_WITHDRAW);
			} catch (IException e) {
				e.printStackTrace();
				// 提示“系统忙”
				//modified by mzh_fu 2007/06/01 提示准确错误信息
		           // throw new IRollbackException(null,"Gen_E001");
					throw new IRollbackException(ctx,e);
			}
		}

		log.debug(UtilOperation.dataentityToString(transInfo));
		if (transInfo.getNIsReturned() == 1)
		{
			accountOperation.addLoanUncheckAmount(transInfo.getNDiscountAccountID(), transInfo.getNDiscountNoteID(), transInfo.getMDiscountAmount());
			if (transInfo.getNDepositAccountID() > 0)
			{
				accountOperation.addCurrentUncheckAmount(transInfo.getNDepositAccountID(), -1, transInfo.getMReturnedAmount() + transInfo.getMOverDueInterest());
			}
		}
		else
		{
			accountOperation.addLoanUncheckAmount(transInfo.getNDiscountAccountID(), transInfo.getNDiscountNoteID(), transInfo.getMAmount());
			//对于国机系统，收回时可能从活期账户付款
			if (transInfo.getNCurrentAccountID() > 0)
			{
				log.info("活期存款账户ID:" + transInfo.getNCurrentAccountID());

				//修改 by kenny(胡志强) (2007-03-19) 处理保存时校验账户状态的操作
				//如果该交易有收/付款方，且收/付款方为内部账户，则校验收/付款方账户状态
				AccountBean accountBean = new AccountBean();
				try {
					accountBean.validateAccountStatus(transInfo.getNCurrentAccountID(),AccountBean.TRANSTYPE_WITHDRAW);
				} catch (IException e) {
					e.printStackTrace();
					// 提示“系统忙”
					//modified by mzh_fu 2007/06/01 提示准确错误信息
			           // throw new IRollbackException(null,"Gen_E001");
						throw new IRollbackException(ctx,e);
				}

				accountOperation.addCurrentUncheckAmount(transInfo.getNCurrentAccountID(), -1, transInfo.getMAmount());
			}
		}
	}
	/** 贴现收回删除 */
	public void deleteRepaymentDiscount(TransRepaymentDiscountInfo transInfo) throws RemoteException, IRollbackException
	{
		log.debug("贴现收回删除");
		log.debug(UtilOperation.dataentityToString(transInfo));
		if (transInfo.getNIsReturned() == 1)
		{
			if (transInfo.getNDepositAccountID() > 0)
			{
				accountOperation.subtractCurrentUncheckAmount(transInfo.getNDepositAccountID(), transInfo.getMReturnedAmount() + transInfo.getMOverDueInterest());
			}
			accountOperation.subtractLoanUncheckAmount(transInfo.getNDiscountAccountID(), transInfo.getNDiscountNoteID(), transInfo.getMDiscountAmount());
		}
		else
		{
			if (transInfo.getNCurrentAccountID() > 0)
			{
				accountOperation.subtractCurrentUncheckAmount(transInfo.getNCurrentAccountID(), transInfo.getMAmount());
			}
			accountOperation.subtractLoanUncheckAmount(transInfo.getNDiscountAccountID(), transInfo.getNDiscountNoteID(), transInfo.getMAmount());
		}
	}
	private final static int RepaymentDiscount_RETURN_CURRENT_WITHDRAW = 0;
	private final static int RepaymentDiscount_REPAYLOAN = 1;
	private final static int RepaymentDiscount_DEPOSIT_CURRENT_WITHDRAW = 2;
	/** 贴现收回复核 */
	public void checkRepaymentDiscount(TransRepaymentDiscountInfo transInfo) throws RemoteException, IRollbackException
	{
		log.debug("------开始贴现收回复核--------");
		log.debug(UtilOperation.dataentityToString(transInfo));
		long paySubAccoutID = -1;
		long loanSubAccountID = -1;
		SubAccountAssemblerInfo subAccountAssemblerInfo = null;
		SubAccountLoanInfo subAccountLoanInfo = null;
		Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO();
		TransDiscountDelegation delegation = new TransDiscountDelegation();	
		double dLoanPreDrawInterest=0.0;//计提利息
		double dLoanInterest=0.0;//总利息
		double unPreDrawInterest=0.0;
		//add by kevin(刘连凯)2011-04-19 添加对未计提利息的处理--生成会计分录
		try{
			AccountBean accBean = new AccountBean();
			long subAccountID = accBean.getLoanSubAccountIDByAccountIDAndLoanNoteID(transInfo.getNDiscountAccountID(), transInfo.getNDiscountNoteID());
			if(subAccountID>0){	
				log.debug("----获取贴现子账户的计提利息------");				
				subAccountAssemblerInfo = sett_SubAccountDAO.findByID(subAccountID);
				subAccountLoanInfo = subAccountAssemblerInfo.getSubAccountLoanInfo();				
				if(subAccountLoanInfo.getBalance()-transInfo.getMAmount()<=0){
					dLoanPreDrawInterest=subAccountLoanInfo.getPreDrawInterest();	
					log.debug("----结束获取------");	
					if(transInfo.getNDiscountNoteID()>0){
						log.debug("----获取总利息------");	
						TransGrantDiscountInfo info = delegation.grantFindGrantDetailByNoteID(transInfo.getNDiscountNoteID());				
						dLoanInterest = info.getInterest();					
						log.debug("----结束获取------");
					}
					unPreDrawInterest=dLoanInterest-dLoanPreDrawInterest;					
				}
			}			
		}catch(Exception ex){
			ex.printStackTrace();
			throw  new IRollbackException(ctx, "未能获取贴现账户的计提利息");				
		}		
		if (transInfo.getNIsReturned() == 1 && transInfo.getNDepositAccountID() > 0)
		{
			log.debug("----退票处理------");
			log.debug("----是退票开始活期支取------");
			TransAccountDetailInfo currentTadi = transferTransRepaymentDiscountInfoToTransAccountDetailInfo(transInfo, RepaymentDiscount_RETURN_CURRENT_WITHDRAW);
			paySubAccoutID = accountOperation.withdrawCurrent(currentTadi);
			log.debug("----结束活期支取------");
			log.debug("------开始贴现收回--------");
			TransAccountDetailInfo loanTadi = transferTransRepaymentDiscountInfoToTransAccountDetailInfo(transInfo, RepaymentDiscount_REPAYLOAN);
			loanSubAccountID = accountOperation.repayLoan(loanTadi);
			log.debug("------结束贴现收回--------");
		}
		else
		{
			log.debug("----正常贴现收回------");
			log.debug("------开始贴现收回--------");
			if (transInfo.getNCurrentAccountID() > 0)
			{
				log.debug("----开始活期支取------");
				TransAccountDetailInfo currentTadi = transferTransRepaymentDiscountInfoToTransAccountDetailInfo(transInfo, RepaymentDiscount_DEPOSIT_CURRENT_WITHDRAW);
				paySubAccoutID = accountOperation.withdrawCurrent(currentTadi);
				log.debug("----结束活期支取------");
			}
			TransAccountDetailInfo tadi = transferTransRepaymentDiscountInfoToTransAccountDetailInfo(transInfo, RepaymentDiscount_REPAYLOAN);
			loanSubAccountID = accountOperation.repayLoan(tadi);
			
			
			log.debug("------结束贴现收回--------");
		}
		
		//通存通兑处理InterbranchSettlement()
		log.debug("--------开始产生会计分录------------");
		GenerateGLEntryParam param = new GenerateGLEntryParam();
		long receiveAccountID = -1;
		long payAccountID = -1;
		long principalBankID = -1;
		double dAmount = 0.0;
		double totalInterest = 0.0;
		double overFee = 0.0;
		double totalPrincipalAndInterest = 0.0;
		long lEntryType = -1;		
		if (transInfo.getNIsReturned() != 1)
		{
			//收款方账户
			receiveAccountID = loanSubAccountID;
			//付款方账户
			payAccountID = paySubAccoutID;
			//开户行
			principalBankID = transInfo.getNBankID();
			dAmount = transInfo.getMAmount();
			//分录类型 无关
			lEntryType = SETTConstant.EntryType.IRRESPECTIVE;
		}
		else
		{
			//收款方账户
			receiveAccountID = loanSubAccountID;
			//付款方账户
			payAccountID = paySubAccoutID;
			//实际贴现金额
			dAmount = transInfo.getMReturnedAmount();
			//利息合计
			totalInterest = transInfo.getMDiscountAmount() - transInfo.getMReturnedAmount();
			//罚息
			overFee = transInfo.getMOverDueInterest();
			//本息合计
			totalPrincipalAndInterest = dAmount + totalInterest;
			//分录类型 反冲
			lEntryType = SETTConstant.EntryType.RECOIL;
		}
		
		/**
		 * 如果收/付款银行账户ID>0，则本金 流向lPrincipalType =2银行，否则本金流向lPrincipalType =1 内部转账
		 */
		long lPrincipalType = -1;
		//如果是国机项目，要设置本金流向， forest add
		if(transInfo.getNBankID() > 0)
		{ //本金流向是 银行
			lPrincipalType = SETTConstant.CapitalType.BANK;
		}
		else if(transInfo.getNCurrentAccountID() > 0)
		{
			//本金流向是 内部转账
			lPrincipalType = SETTConstant.CapitalType.INTERNAL;
		}
		//		if(transInfo.getBankID() > 0){ //本金流向是 银行
		//			lPrincipalType = SETTConstant.CapitalType.BANK;
		//		}
		//		else
		//		{
		//			//本金流向是 内部转账
		//			lPrincipalType = SETTConstant.CapitalType.INTERNAL;
		//		}
		
		param.setReceiveAccountID(receiveAccountID);
		param.setPayAccountID(payAccountID);
		param.setPrincipalBankID(principalBankID);
		param.setPrincipalType(lPrincipalType);
		param.setEntryType(lEntryType);
		param.setPrincipalOrTransAmount(dAmount);
		param.setTotalInterest(totalInterest);
		param.setOverFee(overFee);
		param.setTotalPrincipalAndInterest(totalPrincipalAndInterest);
		param.setTransactionTypeID(transInfo.getNTransactionTypeID());
		param.setTransNo(transInfo.getSTransNo());
		param.setOfficeID(transInfo.getNOfficeID());
		param.setCurrencyID(transInfo.getNCurrencyID());
		param.setExecuteDate(transInfo.getDtExecute());
		//		param.setInterestStartDate(transInfo.getInterestStartDate());
		param.setAbstractStr(transInfo.getSAbstract());
		param.setInputUserID(transInfo.getNInputUserID());
		param.setCheckUserID(transInfo.getNCheckUserID());
		param.setUnPreDrawInterest(unPreDrawInterest);
		boolean res = glopOperation.generateGLEntry(param);
		if (!res)
		{
			throw new IRollbackException(ctx, "产生会计分录错误2");
		}
		log.debug("--------结束产生会计分录------------");
		log.debug("------结束贴现收回复核--------");
		log.debug("------开始贴现回收交易指令生成--------");

	}
	/**
	 * 贴现收回财务交易取消复核(贴现收回交易取消复核时的财务处理) 逻辑操作：
	 * 
	 * @param transInfo
	 *            TransRepaymentDiscountInfo 贴现收回交易实体类
	 * @throws IRollbackException
	 */
	public void cancelCheckRepaymentDiscount(TransRepaymentDiscountInfo transInfo) throws RemoteException, IRollbackException
	{
		log.debug("------开始贴现收回取消复核--------");
		log.debug(UtilOperation.dataentityToString(transInfo));
		if (transInfo.getNIsReturned() == 1 && transInfo.getNDepositAccountID() > 0)
		{
			log.debug("----是退票开始活期支取反交易------");
			TransAccountDetailInfo currentTadi = transferTransRepaymentDiscountInfoToTransAccountDetailInfo(transInfo, RepaymentDiscount_RETURN_CURRENT_WITHDRAW);
			accountOperation.cancelWithdrawCurrent(currentTadi);
			log.debug("----结束活期支取反交易------");
			log.debug("------开始贴现收回反交易--------");
			TransAccountDetailInfo loanTadi = transferTransRepaymentDiscountInfoToTransAccountDetailInfo(transInfo, RepaymentDiscount_REPAYLOAN);
			accountOperation.cancelRepayLoan(loanTadi);
			log.debug("------结束贴现收回反交易--------");
		}
		else
		{
			if (transInfo.getNCurrentAccountID() > 0)
			{
				log.debug("----如果存款来源来自活期账户，开始活期支取反交易------");
				TransAccountDetailInfo currentTadi = transferTransRepaymentDiscountInfoToTransAccountDetailInfo(transInfo, RepaymentDiscount_DEPOSIT_CURRENT_WITHDRAW);
				accountOperation.cancelWithdrawCurrent(currentTadi);
				log.debug("----结束活期支取反交易------");
			}
			
			log.debug("------开始贴现收回反交易--------");
			TransAccountDetailInfo tadi = transferTransRepaymentDiscountInfoToTransAccountDetailInfo(transInfo, RepaymentDiscount_REPAYLOAN);
			accountOperation.cancelRepayLoan(tadi);
			log.debug("------结束贴现收回反交易--------");
		}
		//通存通兑反交易处理AccountBookBizlogic.InterbranchSettlementReverse()
		accountOperation.deleteTransAccountDetail(transInfo.getSTransNo());
		glopOperation.deleteGLEntry(transInfo.getSTransNo());
		log.debug("------结束贴现收回取消复核--------");
	}
	/**
	 * 特殊业务财务交易保存(特殊业务保存时的财务处理) 逻辑操作：
	 * 
	 * @param transInfo
	 *            TransSpecialOperationInfo 特殊业务实体类
	 * @throws IRollbackException
	 */
	public void saveSpecialOperation(TransSpecialOperationInfo transInfo) throws RemoteException, IRollbackException
	{
		log.debug("------开始特殊业务财务交易保存--------");
		log.debug(UtilOperation.dataentityToString(transInfo));
		if (transInfo.getNpayaccountid() > 0) 
		{
			log.info("付款方活期账户ID:" + transInfo.getNpayaccountid());

			//修改 by kenny(胡志强) (2007-03-19) 处理保存时校验账户状态的操作
			//如果该交易有收/付款方，且收/付款方为内部账户，则校验收/付款方账户状态
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(transInfo.getNpayaccountid(),AccountBean.TRANSTYPE_WITHDRAW);
			} catch (IException e) {
				e.printStackTrace();
				// 提示“系统忙”
				//modified by mzh_fu 2007/06/01 提示准确错误信息
		           // throw new IRollbackException(null,"Gen_E001");
					throw new IRollbackException(ctx,e);
			}
		}
		if (transInfo.getNreceiveaccountid() > 0) 
		{
			log.info("收款方活期账户ID:" + transInfo.getNreceiveaccountid());

			//修改 by kenny(胡志强) (2007-03-19) 处理保存时校验账户状态的操作
			//如果该交易有收/付款方，且收/付款方为内部账户，则校验收/付款方账户状态
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(transInfo.getNreceiveaccountid(),AccountBean.TRANSTYPE_WITHDRAW);
			} catch (IException e) {
				e.printStackTrace();
				// 提示“系统忙”
				//modified by mzh_fu 2007/06/01 提示准确错误信息
		           // throw new IRollbackException(null,"Gen_E001");
					throw new IRollbackException(ctx,e);
			}
		}
		if(transInfo.getMpayamount()>=0)
		{
			if (transInfo.getNpayaccountid() > 0 && (transInfo.getSpayfixeddepositno() == null || transInfo.getSpayfixeddepositno().equals("")) && transInfo.getNpaycontractid() < 0
					&& transInfo.getNpaydirection() == SETTConstant.DebitOrCredit.DEBIT)
			{
				log.debug("-----开始增加活期付款账户累计未复核金额--------");
				accountOperation.addCurrentUncheckAmount(transInfo.getNpayaccountid(), transInfo.getNreceiveaccountid(), transInfo.getMpayamount());
				log.debug("-----结束增加活期付款账户累计未复核金额--------");
			}

		}
		else //金额小于零时 为红字冲销业务 保存时需更新贷方的未复核金额
		{
			if (transInfo.getNpayaccountid() > 0 && (transInfo.getSpayfixeddepositno() == null || transInfo.getSpayfixeddepositno().equals("")) && transInfo.getNpaycontractid() < 0
					&& transInfo.getNpaydirection() == SETTConstant.DebitOrCredit.CREDIT)
			{
				log.debug("-----开始增加活期付款账户累计未复核金额--------");
				accountOperation.addCurrentUncheckAmount(transInfo.getNpayaccountid(), transInfo.getNreceiveaccountid(), Math.abs(transInfo.getMpayamount()));
				log.debug("-----结束增加活期付款账户累计未复核金额--------");
			}
		
		}
		if(transInfo.getMreceiveamount()>=0)
		{
			if (transInfo.getNreceiveaccountid() > 0 && (transInfo.getSreceivefixeddepositno() == null || transInfo.getSreceivefixeddepositno().equals("")) && transInfo.getNreceivecontractid() < 0
					&& transInfo.getNreceivedirection() == SETTConstant.DebitOrCredit.DEBIT)
			{
				log.debug("-----开始增加活期收款账户累计未复核金额--------");
				accountOperation.addCurrentUncheckAmount(transInfo.getNreceiveaccountid(), -1, transInfo.getMreceiveamount());
				log.debug("-----结束增加活期收款账户累计未复核金额--------");
			}			
		}
		else  //金额小于零时 为红字冲销业务 保存时需更新贷方的未复核金额
		{
			if (transInfo.getNreceiveaccountid() > 0 && (transInfo.getSreceivefixeddepositno() == null || transInfo.getSreceivefixeddepositno().equals("")) && transInfo.getNreceivecontractid() < 0
					&& transInfo.getNreceivedirection() == SETTConstant.DebitOrCredit.CREDIT)
			{
				log.debug("-----开始增加活期收款账户累计未复核金额--------");
				accountOperation.addCurrentUncheckAmount(transInfo.getNreceiveaccountid(), -1, Math.abs(transInfo.getMreceiveamount()));
				log.debug("-----结束增加活期收款账户累计未复核金额--------");
			}			
		}
		if (transInfo.getSpayfixeddepositno() != null && !transInfo.getSpayfixeddepositno().equals("") && transInfo.getNpaydirection() == SETTConstant.DebitOrCredit.DEBIT)
		{
			log.debug("-----开始增加定期付款账户累计未复核金额--------");
			accountOperation.addFixedUncheckAmount(transInfo.getNpayaccountid(), transInfo.getSpayfixeddepositno(), transInfo.getMpayamount(),-1);
			log.debug("-----结束增加定期付款账户累计未复核金额--------");
		}
		if (transInfo.getSreceivefixeddepositno() != null && !transInfo.getSreceivefixeddepositno().equals("") && transInfo.getNreceivedirection() == SETTConstant.DebitOrCredit.DEBIT)
		{
			log.debug("-----开始增加定期收款账户累计未复核金额--------");
			accountOperation.addFixedUncheckAmount(transInfo.getNreceiveaccountid(), transInfo.getSreceivefixeddepositno(), transInfo.getMreceiveamount(),-1);
			log.debug("-----结束增加定期收款账户累计未复核金额--------");
			//如果该交易使用了银行票据，则保存时，调用使用银行票据的方法
			if (transInfo.getNbillbankid() != -1 && transInfo.getNbillbankid() != -1 && transInfo.getSbillno() != null && !transInfo.getSbillno().equals("") && bbOperation != null)
			{
				log.debug("-----开始票据使用--------");
				bbOperation.useBankBill(transInfo.getNbillbankid(), transInfo.getNbilltypeid(), transInfo.getSbillno(), transInfo.getNpayaccountid(), transInfo.getDtexecute(), transInfo
						.getNinputuserid());
				log.debug("-----结束票据使用--------");
			}
		}
		log.debug("------结束特殊业务财务交易保存--------");
	}
	/**
	 * 特殊业务财务交易删除(特殊业务交易删除时的财务处理) 逻辑操作：
	 * 
	 * @param transInfo
	 *            TransSpecialOperationInfo 特殊业务交易实体类
	 * @throws IRollbackException
	 */
	public void deleteSpecialOperation(TransSpecialOperationInfo transInfo) throws RemoteException, IRollbackException
	{
		log.debug("------开始特殊业务财务交易删除--------");
		log.debug(UtilOperation.dataentityToString(transInfo));
		if(transInfo.getMpayamount()>=0)
		{
			if (transInfo.getNpayaccountid() > 0 && (transInfo.getSpayfixeddepositno() == null || transInfo.getSpayfixeddepositno().equals("")) && transInfo.getNpaycontractid() < 0
					&& transInfo.getNpaydirection() == SETTConstant.DebitOrCredit.DEBIT)
			{
				log.debug("-----开始增加活期付款账户累计未复核金额反交易--------");
				accountOperation.subtractCurrentUncheckAmount(transInfo.getNpayaccountid(), transInfo.getMpayamount());
				log.debug("-----结束增加活期付款账户累计未复核金额反交易--------");
			}			
		}
		else
		{
			if (transInfo.getNpayaccountid() > 0 && (transInfo.getSpayfixeddepositno() == null || transInfo.getSpayfixeddepositno().equals("")) && transInfo.getNpaycontractid() < 0
					&& transInfo.getNpaydirection() == SETTConstant.DebitOrCredit.CREDIT)
			{
				log.debug("-----开始增加活期付款账户累计未复核金额反交易--------");
				accountOperation.subtractCurrentUncheckAmount(transInfo.getNpayaccountid(), Math.abs(transInfo.getMpayamount()));
				log.debug("-----结束增加活期付款账户累计未复核金额反交易--------");
			}			
		}
		if(transInfo.getMreceiveamount()>=0)
		{
			if (transInfo.getNreceiveaccountid() > 0 && (transInfo.getSreceivefixeddepositno() == null || transInfo.getSreceivefixeddepositno().equals("")) && transInfo.getNreceivecontractid() < 0
					&& transInfo.getNreceivedirection() == SETTConstant.DebitOrCredit.DEBIT)
			{
				log.debug("-----开始增加活期收款账户累计未复核金额反交易--------");
				accountOperation.subtractCurrentUncheckAmount(transInfo.getNreceiveaccountid(), transInfo.getMreceiveamount());
				log.debug("-----结束增加活期收款账户累计未复核金额反交易--------");
			}			
		}
		else
		{
			if (transInfo.getNreceiveaccountid() > 0 && (transInfo.getSreceivefixeddepositno() == null || transInfo.getSreceivefixeddepositno().equals("")) && transInfo.getNreceivecontractid() < 0
					&& transInfo.getNreceivedirection() == SETTConstant.DebitOrCredit.CREDIT)
			{
				log.debug("-----开始增加活期收款账户累计未复核金额反交易--------");
				accountOperation.subtractCurrentUncheckAmount(transInfo.getNreceiveaccountid(), Math.abs(transInfo.getMreceiveamount()));
				log.debug("-----结束增加活期收款账户累计未复核金额反交易--------");
			}			
		}
		if (transInfo.getSpayfixeddepositno() != null && !transInfo.getSpayfixeddepositno().equals("") && transInfo.getNpaydirection() == SETTConstant.DebitOrCredit.DEBIT)
		{
			log.debug("-----开始增加定期付款账户累计未复核金额反交易--------");
			accountOperation.subtractFixedUncheckAmount(transInfo.getNpayaccountid(), transInfo.getSpayfixeddepositno(), transInfo.getMpayamount());
			log.debug("-----结束增加定期付款账户累计未复核金额反交易--------");
		}
		if (transInfo.getSreceivefixeddepositno() != null && !transInfo.getSreceivefixeddepositno().equals("") && transInfo.getNreceivedirection() == SETTConstant.DebitOrCredit.DEBIT)
		{
			log.debug("-----开始增加定期收款账户累计未复核金额反交易--------");
			accountOperation.subtractFixedUncheckAmount(transInfo.getNreceiveaccountid(), transInfo.getSreceivefixeddepositno(), transInfo.getMreceiveamount());
			log.debug("-----结束增加定期收款账户累计未复核金额反交易--------");
			//如果该交易使用了银行票据，则保存时，调用使用银行票据的方法
			if (transInfo.getNbillbankid() != -1 && transInfo.getNbillbankid() != -1 && transInfo.getSbillno() != null && !transInfo.getSbillno().equals("") && bbOperation != null)
			{
				log.debug("-----开始票据使用--------");
				bbOperation.cancelUseBankBill(transInfo.getNbillbankid(), transInfo.getNbilltypeid(), transInfo.getSbillno(), transInfo.getDtexecute(), transInfo.getNinputuserid());
				log.debug("-----结束票据使用--------");
			}
		}
		log.debug("------结束特殊业务财务交易删除--------");
	}
	/**
	 * 特殊业务财务交易复核(特殊业务交易复核时的财务处理) 逻辑操作：
	 * 
	 * @param transInfo
	 *            TransSpecialOperationInfo 特殊业务交易实体类
	 * @throws IRollbackException
	 */
	public void checkSpecialOperation(TransSpecialOperationInfo transInfo) throws RemoteException, IRollbackException
	{
		log.debug("------开始特殊业务财务交易复核--------");
		long paySubAccountID = -1;
		long receiveSubAccountID = -1;
		log.debug(UtilOperation.dataentityToString(transInfo));
		
		//2008年5月4日 Boxu Add 总账业务存在内部账户交易需要生成辅助核算信息 客户编号
		String clientno = "";
//		if(transInfo.getMpayamount()>0)
//		{
			if (transInfo.getNpayaccountid() > 0 && (transInfo.getSpayfixeddepositno() == null || transInfo.getSpayfixeddepositno().equals("")) && transInfo.getNpaycontractid() < 0
					&& transInfo.getNpaydirection() == SETTConstant.DebitOrCredit.DEBIT)
			{
				log.debug("-----开始付款账户活期付款账户支取-方向:借--------");
				TransAccountDetailInfo tadi = transferTransSpecialOperationInfoToTransAccountDetailInfo(transInfo, accoutType_PAY);
				paySubAccountID = accountOperation.withdrawCurrent(tadi);
				log.debug("-----结束付款账户活期付款账户支取--------");
			}
			if (transInfo.getNpayaccountid() > 0 && (transInfo.getSpayfixeddepositno() == null || transInfo.getSpayfixeddepositno().equals("")) && transInfo.getNpaycontractid() < 0
					&& transInfo.getNpaydirection() == SETTConstant.DebitOrCredit.CREDIT)
			{
				log.debug("-----开始付款账户活期付款账户存入-方向:贷-------");
				TransAccountDetailInfo tadi = transferTransSpecialOperationInfoToTransAccountDetailInfo(transInfo, accoutType_PAY);
				paySubAccountID = accountOperation.depositCurrent(tadi);
				log.debug("-----结束付款账户活期付款账户存入--------");
			}
//		}else //当付款金额小于零时账户金额流向相反
//		{
//			transInfo.setMpayamount(Math.abs(transInfo.getMpayamount()));
//			if (transInfo.getNpayaccountid() > 0 && (transInfo.getSpayfixeddepositno() == null || transInfo.getSpayfixeddepositno().equals("")) && transInfo.getNpaycontractid() < 0
//					&& transInfo.getNpaydirection() == SETTConstant.DebitOrCredit.DEBIT)
//			{
//				log.debug("-----开始付款账户活期付款账户存入-方向:借--------");
//				TransAccountDetailInfo tadi = transferTransSpecialOperationInfoToTransAccountDetailInfo(transInfo, accoutType_PAY);
//				paySubAccountID = accountOperation.depositCurrent(tadi);
//				log.debug("-----结束付款账户活期付款账户存入--------");
//			}
//			if (transInfo.getNpayaccountid() > 0 && (transInfo.getSpayfixeddepositno() == null || transInfo.getSpayfixeddepositno().equals("")) && transInfo.getNpaycontractid() < 0
//					&& transInfo.getNpaydirection() == SETTConstant.DebitOrCredit.CREDIT)
//			{
//				log.debug("-----开始付款账户活期付款账户支取-方向:贷-------");
//				TransAccountDetailInfo tadi = transferTransSpecialOperationInfoToTransAccountDetailInfo(transInfo, accoutType_PAY);
//				paySubAccountID = accountOperation.withdrawCurrent(tadi);
//				log.debug("-----结束付款账户活期付款账户支取--------");
//			}			
//		}
//		if(transInfo.getMreceiveamount()>0)
//		{
			if (transInfo.getNreceiveaccountid() > 0 && (transInfo.getSreceivefixeddepositno() == null || transInfo.getSreceivefixeddepositno().equals("")) && transInfo.getNreceivecontractid() < 0
					&& transInfo.getNreceivedirection() == SETTConstant.DebitOrCredit.DEBIT)
			{
				log.debug("-----开始收款账户活期付款账户支取-方向:借--------");
				TransAccountDetailInfo tadi = transferTransSpecialOperationInfoToTransAccountDetailInfo(transInfo, accoutType_RECEIVE);
				receiveSubAccountID = accountOperation.withdrawCurrent(tadi);
				log.debug("-----结束收款账户活期付款账户支取--------");
			}
			if (transInfo.getNreceiveaccountid() > 0 && (transInfo.getSreceivefixeddepositno() == null || transInfo.getSreceivefixeddepositno().equals("")) && transInfo.getNreceivecontractid() < 0
					&& transInfo.getNreceivedirection() == SETTConstant.DebitOrCredit.CREDIT)
			{
				log.debug("-----开始收款账户活期付款账户存入-方向:贷-------");
				TransAccountDetailInfo tadi = transferTransSpecialOperationInfoToTransAccountDetailInfo(transInfo, accoutType_RECEIVE);
				receiveSubAccountID = accountOperation.depositCurrent(tadi);
				log.debug("-----结束收款账户活期付款账户存入--------");
			}
//		}
//		else
//		{
//			transInfo.setMreceiveamount(Math.abs(transInfo.getMreceiveamount()));
//			if (transInfo.getNreceiveaccountid() > 0 && (transInfo.getSreceivefixeddepositno() == null || transInfo.getSreceivefixeddepositno().equals("")) && transInfo.getNreceivecontractid() < 0
//					&& transInfo.getNreceivedirection() == SETTConstant.DebitOrCredit.DEBIT)
//			{
//				log.debug("-----开始收款账户活期付款账户存入-方向:借--------");
//				TransAccountDetailInfo tadi = transferTransSpecialOperationInfoToTransAccountDetailInfo(transInfo, accoutType_RECEIVE);
//				receiveSubAccountID = accountOperation.depositCurrent(tadi);
//				log.debug("-----结束收款账户活期付款账户存入--------");
//			}
//			if (transInfo.getNreceiveaccountid() > 0 && (transInfo.getSreceivefixeddepositno() == null || transInfo.getSreceivefixeddepositno().equals("")) && transInfo.getNreceivecontractid() < 0
//					&& transInfo.getNreceivedirection() == SETTConstant.DebitOrCredit.CREDIT)
//			{
//				log.debug("-----开始收款账户活期付款账户支取-方向:贷-------");
//				TransAccountDetailInfo tadi = transferTransSpecialOperationInfoToTransAccountDetailInfo(transInfo, accoutType_RECEIVE);
//				receiveSubAccountID = accountOperation.withdrawCurrent(tadi);
//				log.debug("-----结束收款账户活期付款账户支取--------");
//			}			
//		}
		
		
		if (transInfo.getSpayfixeddepositno() != null && !transInfo.getSpayfixeddepositno().equals("") && transInfo.getNpaydirection() == SETTConstant.DebitOrCredit.DEBIT)
		{
			log.debug("-----开始付款账户定期付款账户支取-方向:借--------");
			TransAccountDetailInfo tadi = transferTransSpecialOperationInfoToTransAccountDetailInfo(transInfo, accoutType_PAY);
			paySubAccountID = accountOperation.withdrawFix(tadi);
			log.debug("-----结束付款账户定期付款账户支取--------");
		}
		if (transInfo.getSpayfixeddepositno() != null && !transInfo.getSpayfixeddepositno().equals("") && transInfo.getNpaydirection() == SETTConstant.DebitOrCredit.CREDIT)
		{
			log.debug("-----开始付款账户定期付款账户存入-方向:贷-------");
			TransAccountDetailInfo tadi = transferTransSpecialOperationInfoToTransAccountDetailInfo(transInfo, accoutType_PAY);
			paySubAccountID = accountOperation.depositFix(tadi);
			log.debug("-----结束付款账户定期付款账户存入--------");
		}
		if (transInfo.getSreceivefixeddepositno() != null && !transInfo.getSreceivefixeddepositno().equals("") && transInfo.getNreceivedirection() == SETTConstant.DebitOrCredit.DEBIT)
		{
			log.debug("-----开始收款账户定期付款账户支取-方向:借--------");
			TransAccountDetailInfo tadi = transferTransSpecialOperationInfoToTransAccountDetailInfo(transInfo, accoutType_RECEIVE);
			receiveSubAccountID = accountOperation.withdrawFix(tadi);
			log.debug("-----结束收款账户定期付款账户支取-方向:借--------");
		}
		if (transInfo.getSreceivefixeddepositno() != null && !transInfo.getSreceivefixeddepositno().equals("") && transInfo.getNreceivedirection() == SETTConstant.DebitOrCredit.CREDIT)
		{
			log.debug("-----开始收款账户定期付款账户支取-方向:贷--------");
			TransAccountDetailInfo tadi = transferTransSpecialOperationInfoToTransAccountDetailInfo(transInfo, accoutType_RECEIVE);
			receiveSubAccountID = accountOperation.depositFix(tadi);
			log.debug("-----结束收款账户定期付款账户支取-方向:贷--------");
		}
		if (transInfo.getNpayloannoteid() > 0 && transInfo.getNpaydirection() == SETTConstant.DebitOrCredit.DEBIT)
		{
			log.debug("-----开始付款账户贷款放款-方向:借--------");
			TransAccountDetailInfo tadi = transferTransSpecialOperationInfoToTransAccountDetailInfo(transInfo, accoutType_PAY);
			long subLoanAccountID = accountOperation.getLoanSubAccountIDByAccountIDAndLoanNoteID(transInfo.getNpayaccountid(), transInfo.getNpayloannoteid());
			tadi.setTransSubAccountID(subLoanAccountID);
			paySubAccountID = accountOperation.grantLoan(tadi);
			log.debug("-----结束付款账户贷款放款--------");
		}
		if (transInfo.getNpayloannoteid() > 0 && transInfo.getNpaydirection() == SETTConstant.DebitOrCredit.CREDIT)
		{
			log.debug("-----开始付款账户贷款放款收回-方向:贷-------");
			TransAccountDetailInfo tadi = transferTransSpecialOperationInfoToTransAccountDetailInfo(transInfo, accoutType_PAY);
			tadi.setCommonOperation(false);
			paySubAccountID = accountOperation.repayLoan(tadi);
			log.debug("-----结束付款账户贷款放款收回--------");
		}
		if (transInfo.getNreceiveloannoteid() > 0 && transInfo.getNreceivedirection() == SETTConstant.DebitOrCredit.DEBIT)
		{
			log.debug("-----开始收款账户贷款放款-方向:借--------");
			TransAccountDetailInfo tadi = transferTransSpecialOperationInfoToTransAccountDetailInfo(transInfo, accoutType_RECEIVE);
			long subLoanAccountID = accountOperation.getLoanSubAccountIDByAccountIDAndLoanNoteID(transInfo.getNreceiveaccountid(), transInfo.getNreceiveloannoteid());
			tadi.setTransSubAccountID(subLoanAccountID);
			receiveSubAccountID = accountOperation.grantLoan(tadi);
			log.debug("-----结束收款账户贷款放款-方向:借--------");
		}
		if (transInfo.getNreceiveloannoteid() > 0 && transInfo.getNreceivedirection() == SETTConstant.DebitOrCredit.CREDIT)
		{
			log.debug("-----开始收款账户贷款收回-方向:贷--------");
			TransAccountDetailInfo tadi = transferTransSpecialOperationInfoToTransAccountDetailInfo(transInfo, accoutType_RECEIVE);
			tadi.setCommonOperation(false);
			receiveSubAccountID = accountOperation.repayLoan(tadi);
			log.debug("-----结束收款账户贷款放款收回-方向:贷--------");
		}
		log.debug("------结束特殊业务财务交易复核--------");
		//通存通兑处理AccountBookBizlogic.InterbranchSettlement()
		GLEntryInfo payGlEntryIfo = new GLEntryInfo();
		GLEntryInfo receiveGlEntryIfo = new GLEntryInfo();
		String subjectCode = "";
		log.debug("------开始产生付款方会计科目信息-------");
		Sett_GeneralLedgerDAO glDAO = new Sett_GeneralLedgerDAO();
		
		if (transInfo.getNpayaccountid() > 0)
		{
			try
			{
				subjectCode = accountOperation.getSubjectBySubAccountID(paySubAccountID, AccountOperation.SUBJECT_TYPE_ACCOUNT);
				
				//2008年5月4日 Boxu Add 总账业务存在内部账户交易需要生成辅助核算信息 客户编号
				clientno = accountOperation.findClientCodeBySubAccountID(paySubAccountID);
				payGlEntryIfo.setAssitantValue(clientno);
			}
			catch (IException e)
			{
				throw new IRollbackException(ctx, e.getMessage(), e);
			}
			payGlEntryIfo.setSubjectCode(subjectCode);
		}
		else if (transInfo.getNpaybankid() > 0)
		{
			subjectCode = accountOperation.getSubjectByBankID(transInfo.getNpaybankid());
			payGlEntryIfo.setSubjectCode(subjectCode);
		}
		else if (transInfo.getNpaygeneralledgertypeid() > 0)
		{
			subjectCode = findGeneralLedgerSubjectCode(transInfo.getNpaygeneralledgertypeid());
			payGlEntryIfo.setSubjectCode(subjectCode);
		}
		
		payGlEntryIfo.setTransDirection(transInfo.getNpaydirection());
		payGlEntryIfo.setAmount(transInfo.getMpayamount());
		payGlEntryIfo.setAbstract(transInfo.getSabstract());
		payGlEntryIfo.setCheckUserID(transInfo.getNcheckuserid());
		payGlEntryIfo.setCurrencyID(transInfo.getNcurrencyid());
		payGlEntryIfo.setExecute(transInfo.getDtexecute());
		payGlEntryIfo.setInputUserID(transInfo.getNinputuserid());
		payGlEntryIfo.setInterestStart(transInfo.getDtintereststart());
		payGlEntryIfo.setOfficeID(transInfo.getNofficeid());
		payGlEntryIfo.setStatusID(transInfo.getNstatusid());
		payGlEntryIfo.setTransactionTypeID(transInfo.getNtransactiontypeid());
		payGlEntryIfo.setTransNo(transInfo.getStransno());
		
		log.debug(UtilOperation.dataentityToString(payGlEntryIfo));
		log.debug("------结束产生付款方会计科目信息-------");
		log.debug("------开始产生收款方会计科目信息-------");
		
		if (transInfo.getNreceiveaccountid() > 0)
		{
			try
			{
				subjectCode = accountOperation.getSubjectBySubAccountID(receiveSubAccountID, AccountOperation.SUBJECT_TYPE_ACCOUNT);
				
				//2008年5月4日 Boxu Add 总账业务存在内部账户交易需要生成辅助核算信息 客户编号
				clientno = accountOperation.findClientCodeBySubAccountID(receiveSubAccountID);
				receiveGlEntryIfo.setAssitantValue(clientno);
			}
			catch (IException e)
			{
				throw new IRollbackException(ctx, e.getMessage(), e);
			}
			receiveGlEntryIfo.setSubjectCode(subjectCode);
		}
		else if (transInfo.getNreceivebankid() > 0)
		{
			subjectCode = accountOperation.getSubjectByBankID(transInfo.getNreceivebankid());
			receiveGlEntryIfo.setSubjectCode(subjectCode);
		}
		else if (transInfo.getNreceivegeneralledgertypeid() > 0)
		{
			subjectCode = findGeneralLedgerSubjectCode(transInfo.getNreceivegeneralledgertypeid());
			receiveGlEntryIfo.setSubjectCode(subjectCode);
		}
		
		receiveGlEntryIfo.setTransDirection(transInfo.getNreceivedirection());
		receiveGlEntryIfo.setAmount(transInfo.getMreceiveamount());
		receiveGlEntryIfo.setAbstract(transInfo.getSabstract());
		receiveGlEntryIfo.setCheckUserID(transInfo.getNcheckuserid());
		receiveGlEntryIfo.setCurrencyID(transInfo.getNcurrencyid());
		receiveGlEntryIfo.setExecute(transInfo.getDtexecute());
		receiveGlEntryIfo.setInputUserID(transInfo.getNinputuserid());
		receiveGlEntryIfo.setInterestStart(transInfo.getDtintereststart());
		receiveGlEntryIfo.setOfficeID(transInfo.getNofficeid());
		receiveGlEntryIfo.setStatusID(transInfo.getNstatusid());
		receiveGlEntryIfo.setTransactionTypeID(transInfo.getNtransactiontypeid());
		receiveGlEntryIfo.setTransNo(transInfo.getStransno());
		
		log.debug(UtilOperation.dataentityToString(receiveGlEntryIfo));
		log.debug("------结束产生收款方会计科目信息-------");
		GLEntryInfo[] infos = {payGlEntryIfo, receiveGlEntryIfo};
		glopOperation.addGLEntries(infos);
		log.debug("-----------结束特殊业务复核---------------");

	}
	/**
	 * 特殊业务财务交易取消复核(特殊业务交易取消复核时的财务处理) 逻辑操作：
	 * 
	 * @param transInfo
	 *            TransSpecialOperationInfo 特殊业务交易实体类
	 * @throws IRollbackException
	 */
	public void cancelCheckSpecialOperation(TransSpecialOperationInfo transInfo) throws RemoteException, IRollbackException
	{
		log.debug(UtilOperation.dataentityToString(transInfo));
//		if(transInfo.getMpayamount()>0)
//		{
			if (transInfo.getNpayaccountid() > 0 && (transInfo.getSpayfixeddepositno() == null || transInfo.getSpayfixeddepositno().equals("")) && transInfo.getNpaycontractid() < 0
					&& transInfo.getNpaydirection() == SETTConstant.DebitOrCredit.DEBIT)
			{
				log.debug("-----开始付款账户活期付款账户支取反交易-方向:借--------");
				TransAccountDetailInfo tadi = transferTransSpecialOperationInfoToTransAccountDetailInfo(transInfo, accoutType_PAY);
				accountOperation.cancelWithdrawCurrent(tadi);
				log.debug("-----结束付款账户活期付款账户支取反交易--------");
			}
			if (transInfo.getNpayaccountid() > 0 && (transInfo.getSpayfixeddepositno() == null || transInfo.getSpayfixeddepositno().equals("")) && transInfo.getNpaycontractid() < 0
					&& transInfo.getNpaydirection() == SETTConstant.DebitOrCredit.CREDIT)
			{
				log.debug("-----开始付款账户活期付款账户存入反交易-方向:贷-------");
				TransAccountDetailInfo tadi = transferTransSpecialOperationInfoToTransAccountDetailInfo(transInfo, accoutType_PAY);
				accountOperation.cancelDepositCurrent(tadi);
				log.debug("-----结束付款账户活期付款账户存入反交易--------");
			}
//		}else
//		{
//			transInfo.setMpayamount(Math.abs(transInfo.getMpayamount()));
//			if (transInfo.getNpayaccountid() > 0 && (transInfo.getSpayfixeddepositno() == null || transInfo.getSpayfixeddepositno().equals("")) && transInfo.getNpaycontractid() < 0
//					&& transInfo.getNpaydirection() == SETTConstant.DebitOrCredit.DEBIT)
//			{
//				log.debug("-----开始付款账户活期付款账户存入反交易-方向:借--------");
//				TransAccountDetailInfo tadi = transferTransSpecialOperationInfoToTransAccountDetailInfo(transInfo, accoutType_PAY);
//				accountOperation.cancelDepositCurrent(tadi);
//				log.debug("-----结束付款账户活期付款账户存入反交易--------");
//			}
//			if (transInfo.getNpayaccountid() > 0 && (transInfo.getSpayfixeddepositno() == null || transInfo.getSpayfixeddepositno().equals("")) && transInfo.getNpaycontractid() < 0
//					&& transInfo.getNpaydirection() == SETTConstant.DebitOrCredit.CREDIT)
//			{
//				log.debug("-----开始付款账户活期付款账户支取反交易-方向:贷-------");
//				TransAccountDetailInfo tadi = transferTransSpecialOperationInfoToTransAccountDetailInfo(transInfo, accoutType_PAY);
//				accountOperation.cancelWithdrawCurrent(tadi);
//				log.debug("-----结束付款账户活期付款账户支取反交易--------");
//			}			
//		}
		
//		if(transInfo.getMreceiveamount()>0)
//		{
			if (transInfo.getNreceiveaccountid() > 0 && (transInfo.getSreceivefixeddepositno() == null || transInfo.getSreceivefixeddepositno().equals("")) && transInfo.getNreceivecontractid() < 0
					&& transInfo.getNreceivedirection() == SETTConstant.DebitOrCredit.DEBIT)
			{
				log.debug("-----开始收款账户活期付款账户支取反交易-方向:借--------");
				TransAccountDetailInfo tadi = transferTransSpecialOperationInfoToTransAccountDetailInfo(transInfo, accoutType_RECEIVE);
				accountOperation.cancelWithdrawCurrent(tadi);
				log.debug("-----结束收款账户活期付款账户支取反交易--------");
			}
			if (transInfo.getNreceiveaccountid() > 0 && (transInfo.getSreceivefixeddepositno() == null || transInfo.getSreceivefixeddepositno().equals("")) && transInfo.getNreceivecontractid() < 0
					&& transInfo.getNreceivedirection() == SETTConstant.DebitOrCredit.CREDIT)
			{
				log.debug("-----开始收款账户活期付款账户存入反交易-方向:贷-------");
				TransAccountDetailInfo tadi = transferTransSpecialOperationInfoToTransAccountDetailInfo(transInfo, accoutType_RECEIVE);
				accountOperation.cancelDepositCurrent(tadi);
				log.debug("-----结束收款账户活期付款账户存入反交易--------");
			}
//		}else
//		{
//			transInfo.setMreceiveamount(Math.abs(transInfo.getMreceiveamount()));
//			if (transInfo.getNreceiveaccountid() > 0 && (transInfo.getSreceivefixeddepositno() == null || transInfo.getSreceivefixeddepositno().equals("")) && transInfo.getNreceivecontractid() < 0
//					&& transInfo.getNreceivedirection() == SETTConstant.DebitOrCredit.DEBIT)
//			{
//				log.debug("-----开始收款账户活期付款账户存入反交易-方向:借--------");
//				TransAccountDetailInfo tadi = transferTransSpecialOperationInfoToTransAccountDetailInfo(transInfo, accoutType_RECEIVE);
//				accountOperation.cancelDepositCurrent(tadi);
//				log.debug("-----结束收款账户活期付款账户存入反交易--------");
//			}
//			if (transInfo.getNreceiveaccountid() > 0 && (transInfo.getSreceivefixeddepositno() == null || transInfo.getSreceivefixeddepositno().equals("")) && transInfo.getNreceivecontractid() < 0
//					&& transInfo.getNreceivedirection() == SETTConstant.DebitOrCredit.CREDIT)
//			{
//				log.debug("-----开始收款账户活期付款账户支取反交易-方向:贷-------");
//				TransAccountDetailInfo tadi = transferTransSpecialOperationInfoToTransAccountDetailInfo(transInfo, accoutType_RECEIVE);
//				accountOperation.cancelWithdrawCurrent(tadi);
//				log.debug("-----结束收款账户活期付款账户支取反交易--------");
//			}			
//		}
		
		
		
		if (transInfo.getSpayfixeddepositno() != null && !transInfo.getSpayfixeddepositno().equals("") && transInfo.getNpaydirection() == SETTConstant.DebitOrCredit.DEBIT)
		{
			log.debug("-----开始付款账户定期付款账户支取反交易-方向:借--------");
			TransAccountDetailInfo tadi = transferTransSpecialOperationInfoToTransAccountDetailInfo(transInfo, accoutType_PAY);
			accountOperation.cancelWithdrawFix(tadi);
			log.debug("-----结束付款账户定期付款账户支取反交易--------");
		}
		if (transInfo.getSpayfixeddepositno() != null && !transInfo.getSpayfixeddepositno().equals("") && transInfo.getNpaydirection() == SETTConstant.DebitOrCredit.CREDIT)
		{
			log.debug("-----开始付款账户定期付款账户存入反交易-方向:贷-------");
			TransAccountDetailInfo tadi = transferTransSpecialOperationInfoToTransAccountDetailInfo(transInfo, accoutType_PAY);
			accountOperation.cancelDepositFix(tadi);
			log.debug("-----结束付款账户定期付款账户存入反交易--------");
		}
		if (transInfo.getSreceivefixeddepositno() != null && !transInfo.getSreceivefixeddepositno().equals("") && transInfo.getNreceivedirection() == SETTConstant.DebitOrCredit.DEBIT)
		{
			log.debug("-----开始收款账户定期付款账户支取反交易-方向:借--------");
			TransAccountDetailInfo tadi = transferTransSpecialOperationInfoToTransAccountDetailInfo(transInfo, accoutType_RECEIVE);
			accountOperation.cancelWithdrawFix(tadi);
			log.debug("-----结束收款账户定期付款账户支取反交易-方向:借--------");
		}
		if (transInfo.getSreceivefixeddepositno() != null && !transInfo.getSreceivefixeddepositno().equals("") && transInfo.getNreceivedirection() == SETTConstant.DebitOrCredit.CREDIT)
		{
			log.debug("-----开始收款账户定期付款账户支取反交易-方向:贷--------");
			TransAccountDetailInfo tadi = transferTransSpecialOperationInfoToTransAccountDetailInfo(transInfo, accoutType_RECEIVE);
			accountOperation.cancelDepositFix(tadi);
			log.debug("-----结束收款账户定期付款账户支取反交易-方向:贷--------");
		}
		if (transInfo.getNpayloannoteid() > 0 && transInfo.getNpaydirection() == SETTConstant.DebitOrCredit.DEBIT)
		{
			log.debug("-----开始付款账户贷款放款反交易-方向:借--------");
			TransAccountDetailInfo tadi = transferTransSpecialOperationInfoToTransAccountDetailInfo(transInfo, accoutType_PAY);
			accountOperation.cancelGrantLoan(tadi);
			log.debug("-----结束付款账户贷款放款反交易--------");
		}
		if (transInfo.getNpayloannoteid() > 0 && transInfo.getNpaydirection() == SETTConstant.DebitOrCredit.CREDIT)
		{
			log.debug("-----开始付款账户贷款放款收回反交易-方向:贷-------");
			TransAccountDetailInfo tadi = transferTransSpecialOperationInfoToTransAccountDetailInfo(transInfo, accoutType_PAY);
			accountOperation.cancelRepayLoan(tadi);
			log.debug("-----结束付款账户贷款放款收回反交易--------");
		}
		if (transInfo.getNreceiveloannoteid() > 0 && transInfo.getNreceivedirection() == SETTConstant.DebitOrCredit.DEBIT)
		{
			log.debug("-----开始收款账户贷款放款反交易-方向:借--------");
			TransAccountDetailInfo tadi = transferTransSpecialOperationInfoToTransAccountDetailInfo(transInfo, accoutType_RECEIVE);
			accountOperation.cancelGrantLoan(tadi);
			log.debug("-----结束收款账户贷款放款反交易-方向:借--------");
		}
		if (transInfo.getNreceiveloannoteid() > 0 && transInfo.getNreceivedirection() == SETTConstant.DebitOrCredit.CREDIT)
		{
			log.debug("-----开始收款账户贷款收回反交易-方向:贷--------");
			TransAccountDetailInfo tadi = transferTransSpecialOperationInfoToTransAccountDetailInfo(transInfo, accoutType_RECEIVE);
			accountOperation.cancelRepayLoan(tadi);
			log.debug("-----结束收款账户贷款放款收回反交易-方向:贷--------");
		}
		accountOperation.deleteTransAccountDetail(transInfo.getStransno());
		glopOperation.deleteGLEntry(transInfo.getStransno());
	}
	/**
	 * 一付多收交易保存 逻辑操作：
	 * 
	 * @param transInfo
	 * @throws IRollbackException
	 */
	public void saveOnePayMultiReceive(TransOnePayMultiReceiveInfo transInfo) throws RemoteException, IRollbackException
	{
		log.debug("----开始一付多收交易保存----------");
		if (transInfo.getAccountID() > 0) 
		{
			log.info("活期账户ID:" + transInfo.getAccountID());

			//修改 by kenny(胡志强) (2007-03-19) 处理保存时校验账户状态的操作
			//如果该交易有收/付款方，且收/付款方为内部账户，则校验收/付款方账户状态
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(transInfo.getAccountID(),AccountBean.TRANSTYPE_WITHDRAW);
			} catch (IException e) {
				e.printStackTrace();
				// 提示“系统忙”
				//modified by mzh_fu 2007/06/01 提示准确错误信息
		           // throw new IRollbackException(null,"Gen_E001");
					throw new IRollbackException(ctx,e);
			}
		}
		if (transInfo.getTypeID() == SETTConstant.ReceiveOrPay.PAY && transInfo.getAccountID() > 0)
		{
			accountOperation.addCurrentUncheckAmount(transInfo.getAccountID(), -1, transInfo.getAmount());
		}
		//如果该交易使用了银行票据，则保存时，调用使用银行票据的方法
		//		if (transInfo.getBillBankID() != -1
		//			&& transInfo.getBillTypeID() != -1
		//			&& transInfo.getBillNo() != null
		//			&& !transInfo.getBillNo().equals("") && bbOperation != null)
		//		{
		//			bbOperation.useBankBill(
		//				transInfo.getBillBankID(),
		//				transInfo.getBillTypeID(),
		//				transInfo.getBillNo(),
		//				transInfo.getPayAccountID(),
		//				transInfo.getExecuteDate(),
		//				transInfo.getInputUserID());
		//
		//		}
		log.debug("----结束一付多收交易保存----------");
	}
	/**
	 * 一付多收交易删除 逻辑操作：
	 * 
	 * @param transInfo
	 * @throws IRollbackException
	 */
	public void deleteOnePayMultiReceive(TransOnePayMultiReceiveInfo transInfo) throws RemoteException, IRollbackException
	{
		log.debug("----开始一付多收交易删除----------");
		if (transInfo.getTypeID() == SETTConstant.ReceiveOrPay.PAY && transInfo.getAccountID() > 0)
		{
			accountOperation.subtractCurrentUncheckAmount(transInfo.getAccountID(), transInfo.getAmount());
		}
		log.debug("----结束一付多收交易删除----------");
	}
	/**
	 * 一付多收交易复核 逻 辑操作：
	 * 
	 * @param transInfo
	 * @throws IRollbackException
	 */
	public void checkOnePayMultiReceive(TransOnePayMultiReceiveInfo transInfo) throws RemoteException, IRollbackException
	{
		log.debug("----开始一付多收交易复核----------");
		if (transInfo.getTypeID() == SETTConstant.ReceiveOrPay.PAY && transInfo.getAccountID() > 0)
		{
			TransAccountDetailInfo tadi = transferTransTransOnePayMultiReceiveInfoToTransAccountDetailInfo(transInfo);
			accountOperation.withdrawCurrent(tadi);
		}
		//一付多收不作银行转账处理
		//		else if (transInfo.getTypeID() == SETTConstant.ReceiveOrPay.PAY &&
		// transInfo.getBankID() > 0)
		//		{
		//			log.debug("------开始银行付款指令生成--------");
		//			TransBankDetailInfo tbdi = new TransBankDetailInfo();
		//			tbdi.setBankID(transInfo.getBankID());
		//     		tbdi.setCreateUserID(transInfo.getCheckUserID());
		//			tbdi.setAmount(transInfo.getAmount());
		//			tbdi.setExternalAccountBankName(transInfo.getRemitInBank());
		//			tbdi.setExternalAccountCompanyName(transInfo.getExtClientName());
		//			tbdi.setExternalAccountNo(transInfo.getExtAccountNo());
		//			tbdi.setExternalAccountLocationName(transInfo.getRemitInCity());
		//
		//			log.debug(UtilOperation.dataentityToString(tbdi));
		//			accountOperation.withdrawFromBank(tbdi);
		//		}
		if (transInfo.getTypeID() == SETTConstant.ReceiveOrPay.RECEIVE && transInfo.getAccountID() > 0)
		{
			TransAccountDetailInfo tadi = transferTransTransOnePayMultiReceiveInfoToTransAccountDetailInfo(transInfo);
			accountOperation.depositCurrent(tadi);
		}
		log.debug("----结束一付多收交易复核----------");
	}
	/**
	 * 一付多收交易取消 逻辑操作：
	 * 
	 * @param transInfo
	 * @throws IRollbackException
	 */
	public void cancelCheckOnePayMultiReceive(TransOnePayMultiReceiveInfo transInfo) throws RemoteException, IRollbackException
	{
		log.debug("----开始一付多收交易取消复核----------");
		if (transInfo.getTypeID() == SETTConstant.ReceiveOrPay.PAY && transInfo.getAccountID() > 0)
		{
			TransAccountDetailInfo tadi = transferTransTransOnePayMultiReceiveInfoToTransAccountDetailInfo(transInfo);
			accountOperation.cancelWithdrawCurrent(tadi);
		}
		if (transInfo.getTypeID() == SETTConstant.ReceiveOrPay.RECEIVE && transInfo.getAccountID() > 0)
		{
			TransAccountDetailInfo tadi = transferTransTransOnePayMultiReceiveInfoToTransAccountDetailInfo(transInfo);
			accountOperation.cancelDepositCurrent(tadi);
		}
		accountOperation.deleteTransAccountDetail(transInfo.getEmptyTransNo());
		log.debug("----结束一付多收交易取消复核----------");
	}
	/**
	 * 一付多收交易勾账 逻 辑操作：
	 * 
	 * @param transInfo
	 * @throws IRollbackException
	 */
	public void squareOnePayMultiReceive(TransOnePayMultiReceiveInfo transInfo) throws RemoteException, IRollbackException
	{
		//UtilOperation uOperation = new UtilOperation();
		log.debug("----开始一付多收交易勾账----------");
		log.debug(UtilOperation.dataentityToString(transInfo));
		try
		{
			sett_TransAccountDetailDAO tadDAO = new sett_TransAccountDetailDAO();
			tadDAO.updateTransNo(transInfo.getEmptyTransNo(), transInfo.getTransNo(), transInfo.getSerialNo());
		}
		catch (SQLException e)
		{
			throw new IRollbackException(ctx, e.getMessage(), e);
		}
		////////////////
		if (transInfo.getTypeID() == SETTConstant.ReceiveOrPay.PAY)
		{
			log.debug("------开始产生付款方会计科目信息-------");
			GLEntryInfo payGlEntryIfo = new GLEntryInfo();
			String subjectCode = null;
			Sett_GeneralLedgerDAO glDAO = new Sett_GeneralLedgerDAO();
			if (transInfo.getAccountID() > 0)
			{
				long subAccontID = accountOperation.getCurrentSubAccoutIDByAccoutID(transInfo.getAccountID());
				try
				{
					subjectCode = accountOperation.getSubjectBySubAccountID(subAccontID, AccountOperation.SUBJECT_TYPE_ACCOUNT);
				}
				catch (IException e)
				{
					throw new IRollbackException(ctx, e.getMessage(), e);
				}
				payGlEntryIfo.setSubjectCode(subjectCode);
			}
			else if (transInfo.getBankID() > 0)
			{
				subjectCode = accountOperation.getSubjectByBankID(transInfo.getBankID());
				payGlEntryIfo.setSubjectCode(subjectCode);
			}
			else if (transInfo.getPayGL() > 0)
			{
				subjectCode = findGeneralLedgerSubjectCode(transInfo.getPayGL());
				payGlEntryIfo.setSubjectCode(subjectCode);
			}
			payGlEntryIfo.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
			payGlEntryIfo.setAmount(transInfo.getAmount());
			payGlEntryIfo.setAbstract(transInfo.getAbstract());
			payGlEntryIfo.setCheckUserID(transInfo.getCheckUserID());
			payGlEntryIfo.setCurrencyID(transInfo.getCurrencyID());
			payGlEntryIfo.setExecute(transInfo.getExecuteDate());
			payGlEntryIfo.setInputUserID(transInfo.getInputUserID());
			payGlEntryIfo.setInterestStart(transInfo.getInterestStartDate());
			payGlEntryIfo.setOfficeID(transInfo.getOfficeID());
			payGlEntryIfo.setStatusID(transInfo.getStatusID());
			payGlEntryIfo.setTransactionTypeID(transInfo.getTransactionTypeID());
			payGlEntryIfo.setTransNo(transInfo.getTransNo());
			payGlEntryIfo.setGroup(transInfo.getSerialNo());
			log.debug(UtilOperation.dataentityToString(payGlEntryIfo));
			GLEntryInfo[] infos = {payGlEntryIfo};
			glopOperation.addGLEntries(infos);
			log.debug("------结束产生付款方会计科目信息-------");
		}
		else if (transInfo.getTypeID() == SETTConstant.ReceiveOrPay.RECEIVE)
		{
			log.debug("------开始产生收款方会计科目信息-------");
			GLEntryInfo receiveGlEntryIfo = new GLEntryInfo();
			String subjectCode = null;
			Sett_GeneralLedgerDAO glDAO = new Sett_GeneralLedgerDAO();
			if (transInfo.getAccountID() > 0)
			{
				long subAccontID = accountOperation.getCurrentSubAccoutIDByAccoutID(transInfo.getAccountID());
				try
				{
					subjectCode = accountOperation.getSubjectBySubAccountID(subAccontID, AccountOperation.SUBJECT_TYPE_ACCOUNT);
				}
				catch (IException e)
				{
					throw new IRollbackException(ctx, e.getMessage(), e);
				}
				receiveGlEntryIfo.setSubjectCode(subjectCode);
			}
			else if (transInfo.getBankID() > 0)
			{
				subjectCode = accountOperation.getSubjectByBankID(transInfo.getBankID());
				receiveGlEntryIfo.setSubjectCode(subjectCode);
			}
			else if (transInfo.getReceiveGL() > 0)
			{
				subjectCode = findGeneralLedgerSubjectCode(transInfo.getReceiveGL());
				receiveGlEntryIfo.setSubjectCode(subjectCode);
			}
			receiveGlEntryIfo.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
			receiveGlEntryIfo.setAmount(transInfo.getAmount());
			receiveGlEntryIfo.setAbstract(transInfo.getAbstract());
			receiveGlEntryIfo.setCheckUserID(transInfo.getCheckUserID());
			receiveGlEntryIfo.setCurrencyID(transInfo.getCurrencyID());
			receiveGlEntryIfo.setExecute(transInfo.getExecuteDate());
			receiveGlEntryIfo.setInputUserID(transInfo.getInputUserID());
			receiveGlEntryIfo.setInterestStart(transInfo.getInterestStartDate());
			receiveGlEntryIfo.setOfficeID(transInfo.getOfficeID());
			receiveGlEntryIfo.setStatusID(transInfo.getStatusID());
			receiveGlEntryIfo.setTransactionTypeID(transInfo.getTransactionTypeID());
			receiveGlEntryIfo.setTransNo(transInfo.getTransNo());
			receiveGlEntryIfo.setGroup(transInfo.getSerialNo());
			log.debug(UtilOperation.dataentityToString(receiveGlEntryIfo));
			GLEntryInfo[] infos = {receiveGlEntryIfo};
			glopOperation.addGLEntries(infos);
			log.debug("------结束产生收款方会计科目信息-------");
		}
		log.debug("----结束一付多收交易勾账----------");
		
	}
	/**
	 * 一付多收交易取消勾账 逻辑操作：
	 * 
	 * @param transInfo
	 * @throws IRollbackException
	 */
	public void cancelsquareCheckOnePayMultiReceive(TransOnePayMultiReceiveInfo transInfo) throws RemoteException, IRollbackException
	{
		//Add by Forest, 将正式交易号恢复临时交易号
		try
		{
			sett_TransAccountDetailDAO tadDAO = new sett_TransAccountDetailDAO();
			tadDAO.updateTempTransNoByTransNoAndSerialNo(transInfo.getTransNo(), transInfo.getSerialNo(), transInfo.getEmptyTransNo());
		}
		catch (SQLException e)
		{
			throw new IRollbackException(ctx, e.getMessage(), e);
		}
		glopOperation.deleteGLEntry(transInfo.getTransNo());
	}
	/**
	 * 总账类保存 逻辑操作：
	 * 
	 * @param transInfo
	 * @throws IRollbackException
	 */
	public void saveGeneralLedgerOperation(TransGeneralLedgerInfo transInfo) throws RemoteException, IRollbackException
	{
		log.debug("-----开始总账类保存------");
		log.debug(UtilOperation.dataentityToString(transInfo));
		if (transInfo.getAccountID() > 0) 
		{
			log.info("活期账户ID:" + transInfo.getAccountID());

			//修改 by kenny(胡志强) (2007-03-19) 处理保存时校验账户状态的操作
			//如果该交易有收/付款方，且收/付款方为内部账户，则校验收/付款方账户状态
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(transInfo.getAccountID(),AccountBean.TRANSTYPE_WITHDRAW);
			} catch (IException e) {
				e.printStackTrace();
				// 提示“系统忙”
				//modified by mzh_fu 2007/06/01 提示准确错误信息
		           // throw new IRollbackException(null,"Gen_E001");
					throw new IRollbackException(ctx,e);
			}
		}
		if (transInfo.getAccountID() > 0 && transInfo.getAmount() != 0 && transInfo.getDirection() == SETTConstant.DebitOrCredit.DEBIT)
		{
			//long subAccountID =
			// accountOperation.getCurrentSubAccoutIDByAccoutID(transInfo.getAccountID());
			accountOperation.addCurrentUncheckAmount(transInfo.getAccountID(), -1, transInfo.getAmount());
		}
		//如果该交易使用了银行票据，则保存时，调用使用银行票据的方法
		if (transInfo.getBillBankID() != -1 && transInfo.getBillTypeID() != -1 && transInfo.getBillNo() != null && !transInfo.getBillNo().equals("") && bbOperation != null)
		{
			bbOperation.useBankBill(transInfo.getBillBankID(), transInfo.getBillTypeID(), transInfo.getBillNo(), transInfo.getAccountID(), transInfo.getExecuteDate(), transInfo.getInputUserID());
		}
		log.debug("-----结束总账类保存------");
	}
	/**
	 * 总账类删除 逻辑操作：
	 * 
	 * @param transInfo
	 * @throws IRollbackException
	 */
	public void deleteGeneralLedgerOperation(TransGeneralLedgerInfo transInfo) throws RemoteException, IRollbackException
	{
		log.debug("-----开始总账类删除------");
		log.debug(UtilOperation.dataentityToString(transInfo));
		if (transInfo.getAccountID() > 0 && transInfo.getAmount() != 0 && transInfo.getDirection() == SETTConstant.DebitOrCredit.DEBIT)
		{
			//long subAccountID =
			// accountOperation.getCurrentSubAccoutIDByAccoutID(transInfo.getAccountID());
			accountOperation.subtractCurrentUncheckAmount(transInfo.getAccountID(), transInfo.getAmount());
		}
		if (transInfo.getBillBankID() != -1 && transInfo.getBillTypeID() != -1 && transInfo.getBillNo() != null && !transInfo.getBillNo().equals("") && bbOperation != null)
		{
			bbOperation.cancelUseBankBill(transInfo.getBillBankID(), transInfo.getBillTypeID(), transInfo.getBillNo(), transInfo.getExecuteDate(), transInfo.getInputUserID());
		}
		log.debug("-----结束总账类删除------");
	}
	/**
	 * 总账类复核 逻辑操作：
	 * 
	 * @param transInfo
	 * @throws IRollbackException
	 */
	public void checkGeneralLedgerOperation(TransGeneralLedgerInfo transInfo) throws RemoteException, IRollbackException
	{
		log.debug("------开始总账类复核--------");
		log.debug(UtilOperation.dataentityToString(transInfo));
		long subAccountID = -1;
		if (transInfo.getDirection() == SETTConstant.DebitOrCredit.DEBIT && transInfo.getAmount() != 0 && transInfo.getAccountID() > 0)
		{
			TransAccountDetailInfo tadi = transferTransGeneralLedgerInfoToTransAccountDetailInfo(transInfo);
			subAccountID = accountOperation.withdrawCurrent(tadi);
		}
		else if (transInfo.getDirection() == SETTConstant.DebitOrCredit.CREDIT && transInfo.getAmount() != 0 && transInfo.getAccountID() > 0)
		{
			TransAccountDetailInfo tadi = transferTransGeneralLedgerInfoToTransAccountDetailInfo(transInfo);
			subAccountID = accountOperation.depositCurrent(tadi);
		}
		//InterbranchSettlement();
		int entryNum = 0;
		ArrayList list = new ArrayList();
		if (subAccountID > 0)
		{
			log.debug("------开始第一条会计分录-------");
			String subjectCode;
			
			//2008年5月4日 Boxu Add 总账业务存在内部账户交易需要生成辅助核算信息 客户编号
			String clientno = "";
			
			try
			{
				subjectCode = accountOperation.getSubjectBySubAccountID(subAccountID, AccountOperation.SUBJECT_TYPE_ACCOUNT);
				
				//2008年5月4日 Boxu Add 总账业务存在内部账户交易需要生成辅助核算信息 客户编号
				clientno = accountOperation.findClientCodeBySubAccountID(subAccountID);
			}
			catch (IException e)
			{
				throw new IRollbackException(ctx, e.getMessage(), e);
			}
			GLEntryInfo glEntryIfo1 = new GLEntryInfo();
			Sett_GeneralLedgerDAO glDAO = new Sett_GeneralLedgerDAO();
			glEntryIfo1.setSubjectCode(subjectCode);
			glEntryIfo1.setTransDirection(transInfo.getDirection());
			glEntryIfo1.setAmount(transInfo.getAmount());
			glEntryIfo1.setAbstract(transInfo.getAbstract());
			glEntryIfo1.setCheckUserID(transInfo.getCheckUserID());
			glEntryIfo1.setCurrencyID(transInfo.getCurrencyID());
			glEntryIfo1.setExecute(transInfo.getExecuteDate());
			glEntryIfo1.setInputUserID(transInfo.getInputUserID());
			glEntryIfo1.setInterestStart(transInfo.getInterestStartDate());
			glEntryIfo1.setOfficeID(transInfo.getOfficeID());
			glEntryIfo1.setStatusID(SETTConstant.TransactionStatus.CHECK);
			glEntryIfo1.setTransactionTypeID(transInfo.getTransActionTypeID());
			glEntryIfo1.setTransNo(transInfo.getTransNo());
			
			//2008年5月4日 Boxu Add 总账业务存在内部账户交易需要生成辅助核算信息 辅助核算值
			glEntryIfo1.setAssitantValue(clientno);
			
			log.debug(UtilOperation.dataentityToString(glEntryIfo1));
			list.add(glEntryIfo1);
			log.debug("------结束第一条会计分录-------");
		}
		if (transInfo.getGeneralLedgerOne() > 0)
		{
			log.debug("------开始第2条会计分录-------");
			Sett_GeneralLedgerDAO glDAO = new Sett_GeneralLedgerDAO();
			String subjectCode = findGeneralLedgerSubjectCode(transInfo.getGeneralLedgerOne());
			GLEntryInfo glEntryIfo2 = new GLEntryInfo();
			glEntryIfo2.setSubjectCode(subjectCode);
			glEntryIfo2.setTransDirection(transInfo.getDirOne());
			glEntryIfo2.setAmount(transInfo.getAmountOne());
			glEntryIfo2.setAbstract(transInfo.getAbstract());
			glEntryIfo2.setCheckUserID(transInfo.getCheckUserID());
			glEntryIfo2.setCurrencyID(transInfo.getCurrencyID());
			glEntryIfo2.setExecute(transInfo.getExecuteDate());
			glEntryIfo2.setInputUserID(transInfo.getInputUserID());
			glEntryIfo2.setInterestStart(transInfo.getInterestStartDate());
			glEntryIfo2.setOfficeID(transInfo.getOfficeID());
			glEntryIfo2.setStatusID(SETTConstant.TransactionStatus.CHECK);
			glEntryIfo2.setTransactionTypeID(transInfo.getTransActionTypeID());
			glEntryIfo2.setTransNo(transInfo.getTransNo());
			log.debug(UtilOperation.dataentityToString(glEntryIfo2));
			list.add(glEntryIfo2);
			log.debug("------结束第2条会计分录-------");
		}
		if (transInfo.getGeneralLedgerTwo() > 0)
		{
			log.debug("------开始第3条会计分录-------");
			Sett_GeneralLedgerDAO glDAO = new Sett_GeneralLedgerDAO();
			String subjectCode = findGeneralLedgerSubjectCode(transInfo.getGeneralLedgerTwo());
			GLEntryInfo glEntryIfo3 = new GLEntryInfo();
			glEntryIfo3.setSubjectCode(subjectCode);
			glEntryIfo3.setTransDirection(transInfo.getDirTwo());
			glEntryIfo3.setAmount(transInfo.getAmountTwo());
			glEntryIfo3.setAbstract(transInfo.getAbstract());
			glEntryIfo3.setCheckUserID(transInfo.getCheckUserID());
			glEntryIfo3.setCurrencyID(transInfo.getCurrencyID());
			glEntryIfo3.setExecute(transInfo.getExecuteDate());
			glEntryIfo3.setInputUserID(transInfo.getInputUserID());
			glEntryIfo3.setInterestStart(transInfo.getInterestStartDate());
			glEntryIfo3.setOfficeID(transInfo.getOfficeID());
			glEntryIfo3.setStatusID(SETTConstant.TransactionStatus.CHECK);
			glEntryIfo3.setTransactionTypeID(transInfo.getTransActionTypeID());
			glEntryIfo3.setTransNo(transInfo.getTransNo());
			log.debug(UtilOperation.dataentityToString(glEntryIfo3));
			list.add(glEntryIfo3);
			log.debug("------结束第3条会计分录-------");
		}
		if (transInfo.getGeneralLedgerThree() > 0)
		{
			log.debug("------开始第4条会计分录-------");
			Sett_GeneralLedgerDAO glDAO = new Sett_GeneralLedgerDAO();
			String subjectCode = findGeneralLedgerSubjectCode(transInfo.getGeneralLedgerThree());
			GLEntryInfo glEntryIfo4 = new GLEntryInfo();
			glEntryIfo4.setSubjectCode(subjectCode);
			glEntryIfo4.setTransDirection(transInfo.getDirThree());
			glEntryIfo4.setAmount(transInfo.getAmountThree());
			glEntryIfo4.setAbstract(transInfo.getAbstract());
			glEntryIfo4.setCheckUserID(transInfo.getCheckUserID());
			glEntryIfo4.setCurrencyID(transInfo.getCurrencyID());
			glEntryIfo4.setExecute(transInfo.getExecuteDate());
			glEntryIfo4.setInputUserID(transInfo.getInputUserID());
			glEntryIfo4.setInterestStart(transInfo.getInterestStartDate());
			glEntryIfo4.setOfficeID(transInfo.getOfficeID());
			glEntryIfo4.setStatusID(SETTConstant.TransactionStatus.CHECK);
			glEntryIfo4.setTransactionTypeID(transInfo.getTransActionTypeID());
			glEntryIfo4.setTransNo(transInfo.getTransNo());
			log.debug(UtilOperation.dataentityToString(glEntryIfo4));
			list.add(glEntryIfo4);
			log.debug("------结束第4条会计分录-------");
		}
		//-----------------------------中交加入--------------------------------------
		if (transInfo.getGeneralLedgerFour() > 0)
		{
			log.debug("------开始第5条会计分录-------");
			Sett_GeneralLedgerDAO glDAO = new Sett_GeneralLedgerDAO();
			String subjectCode = findGeneralLedgerSubjectCode(transInfo.getGeneralLedgerFour());
			GLEntryInfo glEntryIfo5 = new GLEntryInfo();
			glEntryIfo5.setSubjectCode(subjectCode);
			glEntryIfo5.setTransDirection(transInfo.getDirFour());
			glEntryIfo5.setAmount(transInfo.getAmountFour());
			glEntryIfo5.setAbstract(transInfo.getAbstract());
			glEntryIfo5.setCheckUserID(transInfo.getCheckUserID());
			glEntryIfo5.setCurrencyID(transInfo.getCurrencyID());
			glEntryIfo5.setExecute(transInfo.getExecuteDate());
			glEntryIfo5.setInputUserID(transInfo.getInputUserID());
			glEntryIfo5.setInterestStart(transInfo.getInterestStartDate());
			glEntryIfo5.setOfficeID(transInfo.getOfficeID());
			glEntryIfo5.setStatusID(SETTConstant.TransactionStatus.CHECK);
			glEntryIfo5.setTransactionTypeID(transInfo.getTransActionTypeID());
			glEntryIfo5.setTransNo(transInfo.getTransNo());
			log.debug(UtilOperation.dataentityToString(glEntryIfo5));
			list.add(glEntryIfo5);
			log.debug("------结束第5条会计分录-------");
		}
		
		
		
		//--------------------------------------------------------------------------
		if (list.size() > 0)
		{
			GLEntryInfo[] infos = new GLEntryInfo[list.size()];
			for (int i = 0; i < list.size(); i++)
			{
				infos[i] = (GLEntryInfo) list.get(i);
			}
			glopOperation.addGLEntries(infos);
		}
		log.debug("------结束总账类复核--------");
		
		

	}
	//else
	//{
		//TODO
	//}	
		
		
	//}
	/**
	 * 总账类取消复核 逻辑操作：
	 * 
	 * @param transInfo
	 * @throws IRollbackException
	 */
	public void cancelCheckGeneralLedgerOperation(TransGeneralLedgerInfo transInfo) throws RemoteException, IRollbackException
	{
		if (transInfo.getDirection() == SETTConstant.DebitOrCredit.DEBIT && transInfo.getAmount() != 0 && transInfo.getAccountID() > 0)
		{
			TransAccountDetailInfo tadi = transferTransGeneralLedgerInfoToTransAccountDetailInfo(transInfo);
			accountOperation.cancelWithdrawCurrent(tadi);
		}
		else if (transInfo.getDirection() == SETTConstant.DebitOrCredit.CREDIT && transInfo.getAmount() != 0 && transInfo.getAccountID() > 0)
		{
			TransAccountDetailInfo tadi = transferTransGeneralLedgerInfoToTransAccountDetailInfo(transInfo);
			accountOperation.cancelDepositCurrent(tadi);
		}
		accountOperation.deleteTransAccountDetail(transInfo.getTransNo());
		glopOperation.deleteGLEntry(transInfo.getTransNo());
	}
	/**
	 * 多笔贷款收回保存
	 */
	public void saveMultiLoanReceive(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException
	{
		saveRepaymentLoan(transInfo);
	}
	/**
	 * 多笔贷款收回删除
	 */
	public void deleteMultiLoanReceive(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException
	{
		deleteRepaymentLoan(transInfo);
	}
	/**
	 * 多笔贷款收回复核
	 */
	public void checkMultiLoanReceive(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException
	{
		CheckRepaymentLoan(transInfo, true);
	}
	/**
	 * 多笔贷款收回取消复核
	 */
	public void cancelCheckMultiLoanReceive(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException
	{
		cancelCheckRepaymentLoan(transInfo, true);
	}
	/**
	 * 多笔贷款收回勾账
	 */
	public void squareMultiLoanReceive(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException
	{
		//UtilOperation uOperation = new UtilOperation();
		log.debug("----开始多笔贷款收回勾账----------");
		log.debug(UtilOperation.dataentityToString(transInfo));
		try
		{
			sett_TransAccountDetailDAO tadDAO = new sett_TransAccountDetailDAO();
			tadDAO.updateTransNo(transInfo.getTempTransNO(), transInfo.getTransNo(), transInfo.getSerialNo());
		}
		catch (SQLException e)
		{
			throw new IRollbackException(ctx, e.getMessage(), e);
		}
		////////////////
		//		if (transInfo.getTransDirectionID() ==
		// SETTConstant.MultiLoanType.PAYMENT)
		//		{
		//			log.debug("------开始产生付款方会计科目信息-------");
		//			GLEntryInfo payGlEntryIfo = new GLEntryInfo();
		//			String subjectCode = null;
		//			Sett_GeneralLedgerDAO glDAO = new Sett_GeneralLedgerDAO();
		//			if (transInfo.getDepositAccountID() > 0)//还本金活期账户
		//			{
		//				long subAccontID =
		// accountOperation.getCurrentSubAccoutIDByAccoutID(transInfo.getDepositAccountID());
		//				try {
		//					subjectCode = ac总untOperation.getSubjectBySubAccountID(subAccontID,
		// AccountOperation.SUBJECT_TYPE_ACCOUNT);
		//				} catch (IException e) {
		//					throw new IRollbackException(ctx,e.getMessage(),e);
		//				}
		//				payGlEntryIfo.setSubjectCode(subjectCode);
		//			}
		//			else if (transInfo.getBankID() > 0)//还本金银行
		//			{
		//				subjectCode =
		// accountOperation.getSubjectByBankID(transInfo.getBankID());
		//				payGlEntryIfo.setSubjectCode(subjectCode);
		//			}
		//
		//			payGlEntryIfo.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
		//			payGlEntryIfo.setAmount(transInfo.getAmount());
		//			payGlEntryIfo.setAbstract(transInfo.getAbstract());
		//			payGlEntryIfo.setCheckUserID(transInfo.getCheckUserID());
		//			payGlEntryIfo.setCurrencyID(transInfo.getCurrencyID());
		//			payGlEntryIfo.setExecute(transInfo.getExecute());
		//			payGlEntryIfo.setInputUserID(transInfo.getInputUserID());
		//			payGlEntryIfo.setInterestStart(transInfo.getInterestStart());
		//			payGlEntryIfo.setOfficeID(transInfo.getOfficeID());
		//			payGlEntryIfo.setStatusID(transInfo.getStatusID());
		//			payGlEntryIfo.setTransactionTypeID(transInfo.getTransactionTypeID());
		//			payGlEntryIfo.setTransNo(transInfo.getTransNo());
		//			//payGlEntryIfo.setGroup(transInfo.getSerialNo());
		//			log.debug(UtilOperation.dataentityToString(payGlEntryIfo));
		//			GLEntryInfo[] infos = { payGlEntryIfo };
		//			glopOperation.addGLEntries(infos);
		//			log.debug("------结束产生付款方会计科目信息-------");
		//		}
		//		else
		//		{
		GenerateGLEntryParam param = new GenerateGLEntryParam();
		/**
		 * 如果收/付款银行账户ID>0，则本金 流向lPrincipalType =2银行，否则本金流向lPrincipalType =1 内部转账
		 */
		long lPrincipalType = -1;
		long lInterestType = -1;
		long lCommissionType = -1;
		long subPayAccountID = -1;
		log.print("=========交易类型："+transInfo.getTransactionTypeID());
		//			if(transInfo.getTransDirectionID() ==
		// SETTConstant.MultiLoanType.TRUSTLOAN){
		//				transInfo.setTransactionTypeID(SETTConstant.TransactionType.TRUSTLOANRECEIVE);
		//			}else
		//			    transInfo.setTransactionTypeID(SETTConstant.TransactionType.CONSIGNLOANRECEIVE);
		if (transInfo.getBankID() > 0)
		{
			//本金流向是 银行
			lPrincipalType = SETTConstant.CapitalType.BANK;
		}
		else
		{
			//本金流向是 内部转账
			lPrincipalType = SETTConstant.CapitalType.INTERNAL;
		}
		if (transInfo.getInterestBankID() > 0)
		{
			//利息流向是 银行
			lInterestType = SETTConstant.CapitalType.BANK;
		}
		else
		{
			//利息流向是 内部转账
			lInterestType = SETTConstant.CapitalType.INTERNAL;
		}
		//委托贷款手续费
		if (transInfo.getTransDirectionID() == SETTConstant.MultiLoanType.CONSIGNLOAN)
		{
			if (transInfo.getCommissionAccountID() > 0)
			{
				lCommissionType = SETTConstant.CapitalType.INTERNAL;
			}
			else
				lCommissionType = SETTConstant.CapitalType.BANK;
		}
		else if (transInfo.getTransDirectionID() == SETTConstant.MultiLoanType.TRUSTLOAN)
		//信托贷款担保费
		{
			if (transInfo.getPaySuretyAccountID() > 0)
			{
				lCommissionType = SETTConstant.CapitalType.INTERNAL;
			}
			else
				lCommissionType = SETTConstant.CapitalType.BANK;
		}
		else if (transInfo.getTransDirectionID() == SETTConstant.MultiLoanType.PAYMENT && transInfo.getDepositAccountID() > 0)
		{
			subPayAccountID = accountOperation.getCurrentSubAccoutIDByAccoutID(transInfo.getDepositAccountID());
		}
		//分录类型
		long lEntryType = transInfo.getCapitalAndInterstDealway();
		//收款方账户
		long receiveAccountID = -1;
		if (transInfo.getLoanAccountID() > 0)
			receiveAccountID = transInfo.getSubAccountID();
		//receiveAccountID =
		// accountOperation.getLoanSubAccountIDByAccountIDAndLoanNoteID(transInfo.getLoanAccountID(),
		// transInfo.getLoanNoteID());
		//			//付款方账户
		//			long payAccountID = -1;
		//			if(transInfo.getDepositAccountID() > 0)
		//				payAccountID =
		// accountOperation.getCurrentSubAccoutIDByAccoutID(transInfo.getDepositAccountID());
		////收息账户ID
		long receiveInterestAccountID = -1;
		if (transInfo.getReceiveInterestAccountID() > 0)
			receiveInterestAccountID = accountOperation.getCurrentSubAccoutIDByAccoutID(transInfo.getReceiveInterestAccountID());
		//付息账户ID
		long payInterestAccountID = -1;
		if (transInfo.getPayInterestAccountID() > 0)
			payInterestAccountID = accountOperation.getCurrentSubAccoutIDByAccoutID(transInfo.getPayInterestAccountID());
		//收担保费账户ID
		long receieveSuertyFeeAccountID = -1;
		if (transInfo.getReceiveSuretyAccountID() > 0)
			receieveSuertyFeeAccountID = accountOperation.getCurrentSubAccoutIDByAccoutID(transInfo.getReceiveSuretyAccountID());
		//付担保费账户ID
		long paySuertyFeeAccountID = -1;
		if (transInfo.getPaySuretyAccountID() > 0)
			paySuertyFeeAccountID = accountOperation.getCurrentSubAccoutIDByAccoutID(transInfo.getPaySuretyAccountID());
		//付手续费账户ID
		long payCommissionAccountID = -1;
		if (transInfo.getCommissionAccountID() > 0)
			payCommissionAccountID = accountOperation.getCurrentSubAccoutIDByAccoutID(transInfo.getCommissionAccountID());
		//委托收款方账户ID
		long vouchReceiveAccountID = -1;
		if (transInfo.getConsignDepositAccountID() > 0)
			vouchReceiveAccountID = accountOperation.getCurrentSubAccoutIDByAccoutID(transInfo.getConsignDepositAccountID());
		//委托付款方账户ID
		long vouchPayAccountID = -1;
		if (transInfo.getConsignAccountID() > 0)
			vouchPayAccountID = accountOperation.getCurrentSubAccoutIDByAccoutID(transInfo.getConsignAccountID());
		//本金开户行ID
		long principalBankID = transInfo.getBankID();
		//利息开户行ID
		long interestBankID = transInfo.getInterestBankID();
		//费用开户行
		long feeBankID = -1;
		if (transInfo.getCommissionBankID() > 0)
			feeBankID = transInfo.getCommissionBankID();
		else if (transInfo.getSuretyBankID() > 0)
			feeBankID = transInfo.getSuretyBankID();
		//还款金额
		double dAmount = transInfo.getAmount();
		//		利息合计
		double totalInterest = transInfo.getRealInterest() + transInfo.getRealCompoundInterest() + transInfo.getRealOverDueInterest();
		//		计提利息
		double preDrawInterest = transInfo.getRealInterestReceiveAble();
		//		实际支付本次利息
		double unPreDrawInterest = transInfo.getRealInterestIncome();
		//逾期罚息
		double overFee = transInfo.getRealOverDueInterest();
		//复利
		double compoundInterest = transInfo.getRealCompoundInterest();
		//担保费
		double suretyFee = transInfo.getRealSuretyFee();
		//手续费
		double commissionFee = transInfo.getRealCommission();
		//利息税费
		double interestTaxFee = transInfo.getRealInterestTax();
		//本息合计
		double totalPrincipalAndInterest = dAmount + totalInterest + suretyFee + commissionFee;
		//实收利息
		double reallyReceiveInterest = totalInterest - interestTaxFee;
		double remissionInterest = 0.0;
		if (transInfo.getIsRemitInterest() == 1)
			remissionInterest = transInfo.getInterestReceiveAble() - transInfo.getRealInterestReceiveAble();
		else
			remissionInterest = 0.0;
		param.setOfficeID(transInfo.getOfficeID());
		param.setCurrencyID(transInfo.getCurrencyID());
		param.setTransactionTypeID(transInfo.getTransactionTypeID());
		param.setExecuteDate(transInfo.getExecute());
		param.setInterestStartDate(transInfo.getInterestStart());
		param.setTransNo(transInfo.getTransNo());
		param.setAbstractStr(transInfo.getAbstract());
		param.setInputUserID(transInfo.getInputUserID());
		param.setCheckUserID(transInfo.getCheckUserID());
		param.setPrincipalType(lPrincipalType);
		param.setInterestType(lInterestType);
		param.setCommisionType(lCommissionType);
		param.setEntryType(lEntryType);
		if (transInfo.getTransDirectionID() == SETTConstant.MultiLoanType.TRUSTLOAN)
		{
			param.setSubTransactionType(SETTConstant.SubTransactionType.TRUSTRECEIEVE);
		}
		else if (transInfo.getTransDirectionID() == SETTConstant.MultiLoanType.CONSIGNLOAN)
		{
			
			param.setSubTransactionType(SETTConstant.SubTransactionType.CONSIGNRECEIEVE);
			log.print("=======交易子类型:"+param.getSubTransactionType());
		}
		else
		{
			param.setSubTransactionType(SETTConstant.SubTransactionType.PAYMENT);
		}
		param.setReceiveAccountID(receiveAccountID);
		param.setPayAccountID(subPayAccountID);
		param.setPayInterestAccountID(payInterestAccountID);
		param.setReceiveInterestAccountID(receiveInterestAccountID);
		param.setPaySuertyFeeAccountID(paySuertyFeeAccountID);
		param.setReceieveSuertyFeeAccountID(receieveSuertyFeeAccountID);
		param.setPayCommissionAccountID(payCommissionAccountID);
		param.setVouchPayAccountID(vouchPayAccountID);
		param.setVouchReceiveAccountID(vouchReceiveAccountID);
		param.setFeeBankID(feeBankID);
		param.setInterestBankID(interestBankID);
		param.setPrincipalBankID(principalBankID);
		param.setPrincipalOrTransAmount(dAmount);
		param.setTotalInterest(totalInterest);
		param.setPreDrawInterest(preDrawInterest);
		param.setUnPreDrawInterest(unPreDrawInterest);
		param.setOverFee(overFee);
		param.setCommissionFee(commissionFee);
		param.setCompoundInterest(compoundInterest);
		param.setSuretyFee(suretyFee);
		param.setInterestTaxFee(interestTaxFee);
		param.setTotalPrincipalAndInterest(totalPrincipalAndInterest);
		param.setReallyReceiveInterest(reallyReceiveInterest);
		//added for 利息税费
		/*if (param.getInterestTaxFee() > 0)
			param.setSubTransactionType(SETTConstant.SubTransactionType.INTERESTTAX);
		*/
		//delete by gqzhang 2004-12-28
		//不进行试算平衡
		param.setTrialBalance(false);
		log.print("=======交易子类型:"+param.getSubTransactionType());
		glopOperation.generateGLEntry(param);
		//}
		log.debug("----结束多笔贷款收回勾账----------");
	}
	/**
	 * 多笔贷款收回取消勾账
	 */
	public void cancelSquareMultiLoanReceive(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException
	{
		//Add by Forest, 将正式交易号恢复临时交易号
		try
		{
			sett_TransAccountDetailDAO tadDAO = new sett_TransAccountDetailDAO();
			tadDAO.updateTempTransNoByTransNoAndSerialNo(transInfo.getTransNo(), transInfo.getSerialNo(), transInfo.getTempTransNO());
		}
		catch (SQLException e)
		{
			throw new IRollbackException(ctx, e.getMessage(), e);
		}
		glopOperation.deleteGLEntry(transInfo.getTransNo());
	}
	/** 交易费用处理交易保存 */
	public void saveTransFee(TransFeeInfo transInfo) throws RemoteException, IRollbackException
	{
		if (transInfo.getAccountID() > 0) 
		{
			log.info("活期账户ID:" + transInfo.getAccountID());

			//修改 by kenny(胡志强) (2007-03-19) 处理保存时校验账户状态的操作
			//如果该交易有收/付款方，且收/付款方为内部账户，则校验收/付款方账户状态
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(transInfo.getAccountID(),AccountBean.TRANSTYPE_WITHDRAW);
			} catch (IException e) {
				e.printStackTrace();
				// 提示“系统忙”
				//modified by mzh_fu 2007/06/01 提示准确错误信息
		           // throw new IRollbackException(null,"Gen_E001");
					throw new IRollbackException(ctx,e);
			}
		}
		if (transInfo.getAccountID() > 0)
		{
			accountOperation.addCurrentUncheckAmount(transInfo.getAccountID(), -1, transInfo.getAmount());
		}
		if (transInfo.getBillBankID() != -1 && transInfo.getBillTypeID() != -1 && transInfo.getBillNo() != null && !transInfo.getBillNo().equals("") && bbOperation != null)
		{
			bbOperation.useBankBill(transInfo.getBillBankID(), transInfo.getBillTypeID(), transInfo.getBillNo(), transInfo.getAccountID(), transInfo.getExecuteDate(), transInfo.getInputUserID());
		}
	}
	/** 交易费用处理交易删除 */
	public void deleteTransFee(TransFeeInfo transInfo) throws RemoteException, IRollbackException
	{
		if (transInfo.getAccountID() > 0)
		{
			accountOperation.subtractCurrentUncheckAmount(transInfo.getAccountID(), transInfo.getAmount());
		}
		if (transInfo.getBillBankID() != -1 && transInfo.getBillTypeID() != -1 && transInfo.getBillNo() != null && !transInfo.getBillNo().equals("") && bbOperation != null)
		{
			bbOperation.cancelUseBankBill(transInfo.getBillBankID(), transInfo.getBillTypeID(), transInfo.getBillNo(), transInfo.getExecuteDate(), transInfo.getInputUserID());
		}
	}
	/** 交易费用处理交易复核 */
	public void checkTransFee(TransFeeInfo transInfo) throws RemoteException, IRollbackException
	{
		long subAccountID = -1;
		if (transInfo.getAccountID() > 0)
		{
			TransAccountDetailInfo tadi = transferTransFeeInfoToTransAccountDetailInfo(transInfo);
			subAccountID = accountOperation.withdrawCurrent(tadi);
		}
		//2. 通存通兑处理AccountBookBizlogic.InterbranchSettlement()
		String sujectCode1 = "";
		String sujectCode2 = "";
		if (transInfo.getFeeTypeID() > 0)
		{
			if (transInfo.getFeeBankID() > 0)
			{
				sujectCode1 = accountOperation.getSubjectByBankID(transInfo.getFeeBankID());
				if (sujectCode1 != null && sujectCode1.equalsIgnoreCase(sujectCode1))
				{
				}
				else
				{
					throw new IRollbackException(ctx, "无法取得费用开户行ID: " + transInfo.getFeeBankID() + " 所对应的科目号，请检查设置");
				}
			}
			else
			{
				sujectCode1 = accountOperation.getSubjectByTransFeeTypeID(transInfo.getFeeTypeID());
				if (sujectCode1 != null && sujectCode1.compareTo("") != 0)
				{
				}
				else
				{
					throw new IRollbackException(ctx, "无法取得交易费用类型ID: " + transInfo.getFeeTypeID() + " 所对应的科目号，请检查交易费用类型设置");
				}
			}
		}
		else
		{
			throw new IRollbackException(ctx, "交易费用处理复核输入错误，未指定交易费用类型");
		}
		ArrayList list = new ArrayList();
		Sett_GeneralLedgerDAO glDAO = new Sett_GeneralLedgerDAO();
		GLEntryInfo glEntryIfo1 = new GLEntryInfo();
		glEntryIfo1.setSubjectCode(sujectCode1);
		glEntryIfo1.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
		glEntryIfo1.setAmount(transInfo.getAmount());
		glEntryIfo1.setAbstract(transInfo.getAbstract());
		glEntryIfo1.setCheckUserID(transInfo.getCheckUserID());
		glEntryIfo1.setCurrencyID(transInfo.getCurrencyID());
		glEntryIfo1.setExecute(transInfo.getExecuteDate());
		glEntryIfo1.setInputUserID(transInfo.getInputUserID());
		glEntryIfo1.setInterestStart(transInfo.getInterestStartDate());
		glEntryIfo1.setOfficeID(transInfo.getOfficeID());
		glEntryIfo1.setStatusID(transInfo.getStatusID());
		glEntryIfo1.setTransactionTypeID(transInfo.getTransactionTypeID());
		glEntryIfo1.setTransNo(transInfo.getTransNo());
		log.debug(UtilOperation.dataentityToString(glEntryIfo1));
		list.add(glEntryIfo1);
		if (transInfo.getAccountID() > 0)
		{
			try
			{
				sujectCode2 = accountOperation.getSubjectBySubAccountID(subAccountID, AccountOperation.SUBJECT_TYPE_ACCOUNT);
			}
			catch (IException e)
			{
				throw new IRollbackException(ctx, e.getMessage(), e);
			}
		}
		else if (transInfo.getRemitInBankID() > 0)
		{
			sujectCode2 = accountOperation.getSubjectByBankID(transInfo.getRemitInBankID());
			if (sujectCode2 != null && sujectCode2.compareTo("") != 0)
			{
			}
			else
			{
				throw new IRollbackException(ctx, "无法取得开户行ID: " + transInfo.getRemitInBankID() + " 所对应的科目号，请检查设置");
			}
		}
		else
		{
			throw new IRollbackException(ctx, "交易费用处理复核输入错误，支付费用账户号及汇入户行都为空");
		}
		GLEntryInfo glEntryIfo2 = new GLEntryInfo();
		glEntryIfo2.setSubjectCode(sujectCode2);
		glEntryIfo2.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
		glEntryIfo2.setAmount(transInfo.getAmount());
		glEntryIfo2.setAbstract(transInfo.getAbstract());
		glEntryIfo2.setCheckUserID(transInfo.getCheckUserID());
		glEntryIfo2.setCurrencyID(transInfo.getCurrencyID());
		glEntryIfo2.setExecute(transInfo.getExecuteDate());
		glEntryIfo2.setInputUserID(transInfo.getInputUserID());
		glEntryIfo2.setInterestStart(transInfo.getInterestStartDate());
		glEntryIfo2.setOfficeID(transInfo.getOfficeID());
		glEntryIfo2.setStatusID(transInfo.getStatusID());
		glEntryIfo2.setTransactionTypeID(transInfo.getTransactionTypeID());
		glEntryIfo2.setTransNo(transInfo.getTransNo());
		log.debug(UtilOperation.dataentityToString(glEntryIfo2));
		list.add(glEntryIfo2);
		GLEntryInfo[] infos = {glEntryIfo1, glEntryIfo2};
		glopOperation.addGLEntries(infos);
	}
	/** 交易费用处理交易取消复核 */
	public void cancelCheckTransFee(TransFeeInfo transInfo) throws RemoteException, IRollbackException
	{
		if (transInfo.getAccountID() > 0)
		{
			TransAccountDetailInfo tadi = transferTransFeeInfoToTransAccountDetailInfo(transInfo);
			accountOperation.cancelWithdrawCurrent(tadi);
		}
		//通存通兑反交易处理AccountBookBizlogic.InterbranchSettlementReverse()
		accountOperation.deleteTransAccountDetail(transInfo.getTransNo());
		glopOperation.deleteGLEntry(transInfo.getTransNo());
	}
	/**
	 * 从活期交易实体类中得到活期交易财务处理实体类
	 * 
	 * @param info
	 *            Sett_TransCurrentDepositInfo 活期交易实体类
	 * @param lTypeID
	 *            类型：1,付款方账户操作时使用；2，收款方账户操作时使用
	 * @return TransAccountDetailInfo 活期财务处理参数类
	 * @throws Exception
	 */
	private TransAccountDetailInfo transferTransCurrentDepositInfoToTransAccountDetailInfo(TransCurrentDepositInfo info, long lTypeID)
	{
		TransAccountDetailInfo tadi = null;
		if (info != null)
		{
			tadi = new TransAccountDetailInfo();
			tadi.setId(info.getId()); //OTO:待确定
			tadi.setAbstractStr(info.getAbstractStr());
			tadi.setAmount(info.getAmount());
			tadi.setBankChequeNo(info.getBankChequeNo());
			tadi.setBillTypeID(info.getBillTypeID());
			tadi.setBillNo(info.getBillNo());
			tadi.setCurrencyID(info.getCurrencyID());
			tadi.setDtExecute(info.getExecuteDate());
			tadi.setDtInterestStart(info.getInterestStartDate());
			tadi.setOfficeID(info.getOfficeID());
			tadi.setTransNo(info.getTransNo());
			tadi.setTransactionTypeID(info.getTransactionTypeID());
			tadi.setStatusID(info.getStatusID());
			tadi.setAbstractID(info.getAbstractID());
			tadi.setGroup(-1); //TOTO:待确定
			//账户金额查询区分金额类型增加
				tadi.setAmountType(SETTConstant.AmountType.AmountType_1);
			// 

			if (lTypeID == 1)
			{
				//付款方账户操作时使用
				tadi.setTransDirection(SETTConstant.ReceiveOrPay.PAY);
				tadi.setTransAccountID(info.getPayAccountID());
				tadi.setTransSubAccountID(-1); //活期子账户不用设
				tadi.setOppAccountID(info.getReceiveAccountID());
				tadi.setOppSubAccountID(-1); //活期子账户不用设
			}
			else if (lTypeID == 2)
			{
				//收款方账户操作时使用
				tadi.setTransDirection(SETTConstant.ReceiveOrPay.RECEIVE);
				tadi.setTransAccountID(info.getReceiveAccountID());
				tadi.setOppAccountID(info.getPayAccountID());
			}
			//为账户对账单信息查询 所加
			if(info.getTransactionTypeID()==SETTConstant.TransactionType.BANKRECEIVE
			|| info.getTransactionTypeID()==SETTConstant.TransactionType.BANKPAY
			|| info.getTransactionTypeID()==SETTConstant.TransactionType.BANKPAY_NOTONLINE)
			{
				if (lTypeID == 1)
				{
					tadi.setOppAccountNo(info.getExtAccountNo());
					tadi.setOppAccountName(info.getExtClientName());
				}
				else if (lTypeID == 2)
				{
					tadi.setOppAccountNo(info.getExtAccountNo());
					tadi.setOppAccountName(info.getExtClientName());
				}
			}
			else if(info.getTransactionTypeID()==SETTConstant.TransactionType.INTERNALVIREMENT
					|| info.getTransactionTypeID()==SETTConstant.TransactionType.CONSIGNSAVE)
			{
				Sett_AccountDAO sett_accountDao = new Sett_AccountDAO();
				if (lTypeID == 1)
				{
					if(info.getReceiveAccountID()>0)
					{
						try {
							AccountInfo accountInfo = sett_accountDao.findByID(info.getReceiveAccountID());
							tadi.setOppAccountNo(accountInfo.getAccountNo());
							tadi.setOppAccountName(accountInfo.getAccountName());
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				else if (lTypeID == 2)
				{
					if(info.getPayAccountID()>0)
					{
						try {
							AccountInfo accountInfo = sett_accountDao.findByID(info.getPayAccountID());
							tadi.setOppAccountNo(accountInfo.getAccountNo());
							tadi.setOppAccountName(accountInfo.getAccountName());
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else if(info.getBankID()>0)
					{
						Sett_BranchDAO sett_branchDao = new Sett_BranchDAO();
						try {
							BranchInfo branchInfo = sett_branchDao.findByID(info.getBankID());
							tadi.setOppAccountNo(branchInfo.getBankAccountCode());
							tadi.setOppAccountName(branchInfo.getBranchName());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}
			}
			//
			//预算新增
			tadi.setBudgetItemID(info.getBudgetItemID());
		}
		return tadi;
	}
    
	/**
	 * 从活期交易实体类中得到活期交易财务处理实体类(备付金账户扣款、收款专用方法)
	 * 
	 * @param info
	 *            Sett_TransCurrentDepositInfo 活期交易实体类
	 * @param lTypeID
	 *            类型：1,付款方备付金账户操作时使用；2，收款方备付金账户操作时使用
	 * @return TransAccountDetailInfo 活期财务处理参数类
	 * @throws Exception
	 */
	private TransAccountDetailInfo transferTransCurrentDepositInfoToTransBakAccountDetailInfo(TransCurrentDepositInfo info, long lTypeID,long lPayBakAccountID,long lReceiveBakAccountID)
	{
		TransAccountDetailInfo tadi = null;
		if (info != null)
		{
			tadi = new TransAccountDetailInfo();
			tadi.setId(info.getId()); //OTO:待确定
			tadi.setAbstractStr(info.getAbstractStr());
			tadi.setAmount(info.getAmount());
			tadi.setBankChequeNo(info.getBankChequeNo());
			tadi.setBillTypeID(info.getBillTypeID());
			tadi.setBillNo(info.getBillNo());
			tadi.setCurrencyID(info.getCurrencyID());
			tadi.setDtExecute(info.getExecuteDate());
			tadi.setDtInterestStart(info.getInterestStartDate());
			tadi.setOfficeID(info.getOfficeID());
			tadi.setTransNo(info.getTransNo());
			tadi.setTransactionTypeID(info.getTransactionTypeID());
			tadi.setStatusID(info.getStatusID());
			tadi.setAbstractID(info.getAbstractID());
			tadi.setGroup(-1); //TOTO:待确定
			//账户金额查询区分金额类型增加
				tadi.setAmountType(SETTConstant.AmountType.AmountType_1);
			// 

			if (lTypeID == 1)
			{
				//付款方账户操作时使用
				tadi.setTransDirection(SETTConstant.ReceiveOrPay.PAY);
				tadi.setTransAccountID(lPayBakAccountID);
				tadi.setOppAccountID(lReceiveBakAccountID);
			}
			else if (lTypeID == 2)
			{
				//收款方账户操作时使用
				tadi.setTransDirection(SETTConstant.ReceiveOrPay.RECEIVE);
				tadi.setTransAccountID(lReceiveBakAccountID);
				tadi.setOppAccountID(lPayBakAccountID);
			}
			//为账户对账单信息查询 所加
			if(info.getTransactionTypeID()==SETTConstant.TransactionType.BANKRECEIVE
			|| info.getTransactionTypeID()==SETTConstant.TransactionType.BANKPAY
			|| info.getTransactionTypeID()==SETTConstant.TransactionType.BANKPAY_NOTONLINE)
			{
				if (lTypeID == 1)
				{
					tadi.setOppAccountNo(info.getExtAccountNo());
					tadi.setOppAccountName(info.getExtClientName());
				}
				else if (lTypeID == 2)
				{
					tadi.setOppAccountNo(info.getExtAccountNo());
					tadi.setOppAccountName(info.getExtClientName());
				}
			}
			else if(info.getTransactionTypeID()==SETTConstant.TransactionType.INTERNALVIREMENT
					|| info.getTransactionTypeID()==SETTConstant.TransactionType.CONSIGNSAVE)
			{
				Sett_AccountDAO sett_accountDao = new Sett_AccountDAO();
				if (lTypeID == 1)
				{
					if(lReceiveBakAccountID>0)
					{
						try {
							AccountInfo accountInfo = sett_accountDao.findByID(lReceiveBakAccountID);
							tadi.setOppAccountNo(accountInfo.getAccountNo());
							tadi.setOppAccountName(accountInfo.getAccountName());
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				else if (lTypeID == 2)
				{
					if(lPayBakAccountID>0)
					{
						try {
							AccountInfo accountInfo = sett_accountDao.findByID(lPayBakAccountID);
							tadi.setOppAccountNo(accountInfo.getAccountNo());
							tadi.setOppAccountName(accountInfo.getAccountName());
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else if(info.getBankID()>0)
					{
						Sett_BranchDAO sett_branchDao = new Sett_BranchDAO();
						try {
							BranchInfo branchInfo = sett_branchDao.findByID(info.getBankID());
							tadi.setOppAccountNo(branchInfo.getBankAccountCode());
							tadi.setOppAccountName(branchInfo.getBranchName());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}
			}
			//
			//预算新增
			tadi.setBudgetItemID(info.getBudgetItemID());
		}
		return tadi;
	}


    /**
     * 从手续费交易实体类中得到活期交易财务处理实体类
     * 
     * @param info
     *            TransCommissionInfo 活期交易实体类
     * @param lTypeID
     *            类型：1,付款方账户操作时使用；2，收款方账户操作时使用
     * @return TransAccountDetailInfo 活期财务处理参数类
     * @throws Exception
     */
    private TransAccountDetailInfo transferTransCommissionInfoToTransAccountDetailInfo(TransCommissionInfo info, long lTypeID)
    {
        TransAccountDetailInfo tadi = null;
        
        if (info != null)
        {
            tadi = new TransAccountDetailInfo();
            tadi.setId(info.getId()); //OTO:待确定
            tadi.setAbstractStr("手续费收取");
            tadi.setAmount(info.getCommissionAmount());
            tadi.setCurrencyID(info.getCurrencyId());
            tadi.setDtExecute(info.getExecuteDate());
            tadi.setOfficeID(info.getOfficeId());
            tadi.setTransNo(info.getTransNo());
            tadi.setTransactionTypeID(SETTConstant.TransactionType.COMMISSION);
            tadi.setDtInterestStart(info.getInterestStartDate());
            tadi.setStatusID(info.getStatusId());
            tadi.setGroup(-1); //TOTO:待确定
            //账户金额查询区分金额类型增加
            	tadi.setAmountType(SETTConstant.AmountType.AmountType_9);
        	//

            if (lTypeID == 1)
            {
                //付款方账户操作时使用
                tadi.setTransDirection(SETTConstant.ReceiveOrPay.PAY);
                tadi.setTransAccountID(info.getAccountId());
               
                tadi.setTransSubAccountID(-1); //活期子账户不用设
                tadi.setOppAccountID(-1);
                tadi.setOppSubAccountID(-1); //活期子账户不用设
            }
            else if (lTypeID == 2)
            {
                //收款方账户操作时使用
                //tadi.setTransDirection(SETTConstant.ReceiveOrPay.RECEIVE);
                //tadi.setTransAccountID(info.getReceiveAccountID());
                //tadi.setOppAccountID(info.getPayAccountID());
            }
            //预算新增
            //tadi.setBudgetItemID(info.getBudgetItemID());
        }
        return tadi;
    }
	private SubAccountFixedInfo transferTransFixedContinueInfoToSubAccountFixedInfo(TransFixedContinueInfo info) throws Exception
	{
		SubAccountFixedInfo resInfo = new SubAccountFixedInfo();
		resInfo.setAccountID(info.getAccountID());
		
		//lxr为结息插入两个字段的值
		resInfo.setOpenDate(info.getNewStartDate());
		resInfo.setClearInterestDate(info.getNewStartDate());
		//resInfo.setBalance(info.getAmount());
		//resInfo.setDailyUncheckAmount(dailyUncheckAmount);
		if (info.getIsCapitalAndInterestTransfer() == 1)
			resInfo.setOpenAmount(info.getAmount() + info.getWithDrawInterest());
		else
			resInfo.setOpenAmount(info.getAmount());
		//UtilOperation uo = new UtilOperation();
		//resInfo.setDepositNo(uo.getOpenDepositNo(info.getAccountID()));
		resInfo.setDepositNo(info.getNewDepositNo());
		resInfo.setDepositTerm(info.getNewDepositTerm());
		resInfo.setEndDate(info.getNewEndDate());
		//resInfo.setInterest();
		//resInfo.setInterestAccountID();
		//resInfo.setNoticeDay()
		resInfo.setPreDrawDate(info.getExecuteDate());
		
		//重新赋值，因为定期结息取开始日期为该字段(计提日期)(common from calculate interest定期子账户的上次计提日期)，不能为执行日，只能为定期开始日期 changed by rxie
		resInfo.setPreDrawDate(info.getNewStartDate());
		//resInfo.setPreDrawInterest();
		resInfo.setRate(info.getNewRate());
		resInfo.setSealBankID(info.getNewSealBankID());
		resInfo.setSealNo(info.getNewSealNo());
		resInfo.setStartDate(info.getNewStartDate());
		resInfo.setIsInterest(1);
		//resInfo.setStatusID()
		resInfo.setIsAutoContinue(info.getIsAutoContinue());
		resInfo.setAutoContinueType(info.getAutocontinuetype());
		resInfo.setInterestAccountID(info.getAutocontinueaccountid());
		return resInfo;
	}
	private TransAccountDetailInfo transferTransFixedContinueInfoToTransAccountDetailInfo(TransFixedContinueInfo info, int transType, boolean isDeposit, boolean isNewDepositNo)
	{
		TransAccountDetailInfo resInfo = new TransAccountDetailInfo();
		resInfo.setOfficeID(info.getOfficeID());
		resInfo.setCurrencyID(info.getCurrencyID());
		resInfo.setTransactionTypeID(info.getTransactionTypeID());
		resInfo.setDtExecute(info.getExecuteDate());
		resInfo.setTransNo(info.getTransNo());
		resInfo.setAmount(info.getAmount());
		//交易方向在账户中设置
		//resInfo.setTransDirection();
		resInfo.setDtInterestStart(info.getInterestStartDate());
		resInfo.setAbstractStr(info.getAbstract());
		resInfo.setTransSubAccountID(info.getSubAccountID());
		
		//resInfo.setStatusID();
		resInfo.setAbstractID(info.getAbstractID());
		//开立时在账户子系统中设置对方子账户（即新开里账户ID）
		//resInfo.setOppSubAccountID()
		if (transType == TRANS_FIXED)
		{
			resInfo.setTransAccountID(info.getAccountID());
			if (isNewDepositNo)
			{
				resInfo.setFixedDepositNo(info.getNewDepositNo());
			}
			else
			{
				resInfo.setFixedDepositNo(info.getDepositNo());
			}
			if (isDeposit && info.getIsCapitalAndInterestTransfer() == 1)
			{
				resInfo.setAmount(info.getAmount() + info.getWithDrawInterest());
			//账户金额查询区分金额类型增加
				resInfo.setAmountType(SETTConstant.AmountType.AmountType_11);
			//
			}
			else
			{
				resInfo.setAmount(info.getAmount());
			//账户金额查询区分金额类型增加
				resInfo.setAmountType(SETTConstant.AmountType.AmountType_1);
			//
			}
		
			//为账户对账单信息查询 所加
			if(info.getAccountID()>0)
			{
				Sett_AccountDAO sett_accountDao = new Sett_AccountDAO();
				try {
					AccountInfo accountInfo = sett_accountDao.findByID(info.getAccountID());
					resInfo.setOppAccountNo(accountInfo.getAccountNo());
					resInfo.setOppAccountName(accountInfo.getAccountName());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//
			

		}
		else
		{
			resInfo.setTransAccountID(info.getReceiveInterestAccountID());
			resInfo.setAmount(info.getWithDrawInterest());
			//为账户对账单信息查询 所加
			if(info.getAccountID()>0)
			{
				Sett_AccountDAO sett_accountDao = new Sett_AccountDAO();
				try {
					AccountInfo accountInfo = sett_accountDao.findByID(info.getAccountID());
					resInfo.setOppAccountNo(accountInfo.getAccountNo());
					resInfo.setOppAccountName(accountInfo.getAccountName());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		//
		//账户金额查询区分金额类型增加
			resInfo.setAmountType(SETTConstant.AmountType.AmountType_2);
		//
		}
		return resInfo;
	}
	private TransAccountDetailInfo transferTransFixedDrawInfoToTransAccountDetailInfo(TransFixedDrawInfo info, int transType) throws IRollbackException
	{
		TransAccountDetailInfo resInfo = new TransAccountDetailInfo();
		log.debug("-----transferTransFixedDrawInfoToTransAccountDetailInfo's Input-----");
		log.debug(UtilOperation.dataentityToString(info));
		//resInfo.setId(id);
		resInfo.setOfficeID(info.getOfficeID());
		resInfo.setCurrencyID(info.getCurrencyID());
		resInfo.setTransactionTypeID(info.getTransactionTypeID());
		resInfo.setDtExecute(info.getExecuteDate());
		resInfo.setTransNo(info.getTransNo());
		resInfo.setAmount(info.getAmount());
		
		//交易方向在账户中设置
		//resInfo.setTransDirection();
		resInfo.setDtInterestStart(info.getInterestStartDate());
		resInfo.setAbstractStr(info.getAbstract());
		resInfo.setStatusID(SETTConstant.TransactionStatus.CHECK);
		resInfo.setAbstractID(info.getAbstractID());
		//只为定期转活期金额使用!!!!!
		//double amount = 0.0;
		//即使是提前支取也支取全部金额只是利息按照提前支取的金额计算
		//if(info.getDrawAmount() == 0)//不是提前支取
		//	amount = info.getAmount();
		//else
		//	amount = info.getDrawAmount();
		resInfo.setFixedDepositNo(info.getDepositNo());
		switch (transType)
		{
			case TRANS_DEPOSIT_CURRENT_PRINCIPAL :
				{
					resInfo.setTransAccountID(info.getCurrentAccountID());
					if (info.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER&&Config.getBoolean(ConfigConstant.SETT_FIXEDDRAW_CREATENEWBOOK,true))
					{
						resInfo.setAmount(info.getAmount());
					}
					else
						resInfo.setAmount(info.getDrawAmount());
					resInfo.setOppAccountID(info.getAccountID());
					
					//为账户对账单信息查询 所加
					resInfo.setOppAccountNo(info.getAccountNo());
					resInfo.setOppAccountName(info.getAccountName());
					//
					//账户金额查询区分金额类型增加
						resInfo.setAmountType(SETTConstant.AmountType.AmountType_1);
					//
				}
				break;
			case TRANS_DEPOIST_CURRENT_INTEREST :
				{
					resInfo.setTransAccountID(info.getReceiveInterestAccountID());
					if (info.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)
						resInfo.setAmount(info.getTotalInterest());
					else
						resInfo.setAmount(info.getNoticeTotalInterest());
					//无对方账户
					resInfo.setOppAccountID(-1);
					//为账户对账单信息查询 所加
					resInfo.setOppAccountNo(info.getAccountNo());
					resInfo.setOppAccountName(info.getAccountName());
					//
					//
					//账户金额查询区分金额类型增加
						resInfo.setAmountType(SETTConstant.AmountType.AmountType_2);
					//
				}
				break;
			case TRANS_DEPOIST_CURRENT_PRINCIPALANDINTEREST :
				{
					resInfo.setTransAccountID(info.getCurrentAccountID());
					log.debug("------本金利息汇总处理-------");
					if (info.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER&&Config.getBoolean(ConfigConstant.SETT_FIXEDDRAW_CREATENEWBOOK,true))
						resInfo.setAmount(info.getAmount() + info.getTotalInterest());
					else
						resInfo.setAmount(info.getDrawAmount() + info.getDrawInterest());
					resInfo.setOppAccountID(info.getAccountID());
					log.debug("--------活期账户ID1:" + resInfo.getTransAccountID() + "---------");
					//为账户对账单信息查询 所加
					resInfo.setOppAccountNo(info.getAccountNo());
					resInfo.setOppAccountName(info.getAccountName());
					//
					//账户金额查询区分金额类型增加
						resInfo.setAmountType(SETTConstant.AmountType.AmountType_11);
					//
				}
				break;
			case TRANS_DEPOSIT_FIXED :
				{
					resInfo.setTransAccountID(info.getAccountID());
					resInfo.setTransSubAccountID(info.getSubAccountID());
					if (info.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER&&Config.getBoolean(ConfigConstant.SETT_FIXEDDRAW_CREATENEWBOOK,true))
						resInfo.setAmount(info.getAmount());
					else
						resInfo.setAmount(info.getDrawAmount());
					resInfo.setOppAccountID(info.getCurrentAccountID());
					//为账户对账单信息查询 所加
					
					if(info.getCurrentAccountID()>0)
					{
						Sett_AccountDAO sett_accountDao = new Sett_AccountDAO();
						try {
							AccountInfo accountInfo = sett_accountDao.findByID(info.getCurrentAccountID());
							resInfo.setOppAccountNo(accountInfo.getAccountNo());
							resInfo.setOppAccountName(accountInfo.getAccountName());
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else if(info.getBankID()>0)
					{
						Sett_BranchDAO sett_branchDao = new Sett_BranchDAO();
						try {
							BranchInfo branchInfo = sett_branchDao.findByID(info.getBankID());
							resInfo.setOppAccountNo(branchInfo.getBankAccountCode());
							resInfo.setOppAccountName(branchInfo.getBranchName());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					//
					//账户金额查询区分金额类型增加
						resInfo.setAmountType(SETTConstant.AmountType.AmountType_1);
					//
				}
				break;
			default :
				break;
		}
		log.debug("-----transferTransFixedDrawInfoToTransAccountDetailInfo's Output-----");
		log.debug(UtilOperation.dataentityToString(resInfo));
		return resInfo;
	}
	
	private TransAccountDetailInfo transferTransFinancialMarginInfoToTransAccountDetailInfo(TransFinancialMarginInfo info, int transType) throws IRollbackException
	{
		TransAccountDetailInfo resInfo = new TransAccountDetailInfo();
		
		log.debug("-----transferTransFixedDrawInfoToTransAccountDetailInfo's Input-----");
		log.debug(UtilOperation.dataentityToString(info));
		
		resInfo.setOfficeID(info.getNOfficeID());
		resInfo.setCurrencyID(info.getNCurrencyID());
		resInfo.setTransactionTypeID(info.getTranstypeID());
		
		resInfo.setDtExecute(info.getDtExecute());
		resInfo.setTransNo(info.getSTransNo());
		
		//交易方向在账户中设置
		resInfo.setDtInterestStart(info.getDtInterestStartDate());
		resInfo.setAbstractStr(info.getSAbstract());
		resInfo.setStatusID(SETTConstant.TransactionStatus.CHECK);
		
		switch (transType)
		{
			case TRANS_DEPOSIT_CURRENT_PRINCIPAL :
			{
				resInfo.setTransAccountID(info.getNCurrentAccountID());
				resInfo.setAmount(info.getAmount());
				resInfo.setOppAccountID(info.getNAccountID());
				
				Sett_SubAccountDAO sett_subaccountDao = new Sett_SubAccountDAO();
				SubAccountAssemblerInfo subAccountAssemblerInfo = new SubAccountAssemblerInfo();
				try {
					subAccountAssemblerInfo = sett_subaccountDao.findByAccountID(info.getNCurrentAccountID());
					resInfo.setTransSubAccountID(subAccountAssemblerInfo.getSubAccountCurrenctInfo().getID());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				//账户金额查询区分金额类型增加
					resInfo.setAmountType(SETTConstant.AmountType.AmountType_1);
			    //
	
			}
			break;
		
		    case TRANS_DEPOSIT_MARGIN :
			{
				resInfo.setTransAccountID(info.getNAccountID());
				resInfo.setOppAccountID(info.getNCurrentAccountID());
				
				if(info.getTypeID() == 1 && info.getNCurrentAccountID() > 0 ){//如果是收款方为内部账户
					Sett_AccountDAO sett_accountDao = new Sett_AccountDAO();
					try {
						AccountInfo accountInfo = sett_accountDao.findByID(info.getNCurrentAccountID());
						resInfo.setOppAccountNo(accountInfo.getAccountNo());
						resInfo.setOppAccountName(accountInfo.getAccountName());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (info.getTypeID() == 1 && info.getNCurrentAccountID() < 0 )
				{
					resInfo.setOppAccountNo(info.getSExtAcctNo());
					resInfo.setOppAccountName(info.getSExtClientName());
					
				}
				if(info.getTypeID() == 2)
				{
				}
				
				//账户金额查询区分金额类型增加
					resInfo.setAmountType(SETTConstant.AmountType.AmountType_1);
                //
			}
			   break;
			case TRANS_DRAW_MARGIN :
			{
				//保证金支取的时候,活期账户收款
				resInfo.setTransAccountID(info.getNCurrentAccountID());
         		resInfo.setOppAccountID(info.getNAccountID());
         		resInfo.setAmount(info.getAmount());
				Sett_SubAccountDAO sett_subaccountDao = new Sett_SubAccountDAO();
				SubAccountAssemblerInfo subAccountAssemblerInfo = new SubAccountAssemblerInfo();
				try {
					subAccountAssemblerInfo = sett_subaccountDao.findByAccountID(info.getNCurrentAccountID());
					resInfo.setTransSubAccountID(subAccountAssemblerInfo.getSubAccountCurrenctInfo().getID());
					//为账户对账单信息查询 所加
				    if(info.getNAccountID ()>0)
				    {
					    Sett_AccountDAO sett_accountDao = new Sett_AccountDAO();
						AccountInfo accountInfo = sett_accountDao.findByID(info.getNAccountID ());
						resInfo.setOppAccountNo(accountInfo.getAccountNo());
						resInfo.setOppAccountName(accountInfo.getAccountName());
				    }
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				//账户金额查询区分金额类型增加
				 	resInfo.setAmountType(SETTConstant.AmountType.AmountType_1);
                //
			}
			break;
			default :
				break;
		}
		log.debug("-----transferTransFixedDrawInfoToTransAccountDetailInfo's Output-----");
		log.debug(UtilOperation.dataentityToString(resInfo));
		return resInfo;
	}
	
	private TransAccountDetailInfo transferTransMarginDrawInfoToTransAccountDetailInfo(TransMarginWithdrawInfo info, int transType) throws IRollbackException
	{
		TransAccountDetailInfo resInfo = new TransAccountDetailInfo();
		log.debug("-----transferTransFixedDrawInfoToTransAccountDetailInfo's Input-----");
		log.debug(UtilOperation.dataentityToString(info));
		//resInfo.setId(id);
		resInfo.setOfficeID(info.getOfficeID());
		resInfo.setCurrencyID(info.getCurrencyID());
		resInfo.setTransactionTypeID(info.getTransactionTypeID());
		resInfo.setDtExecute(info.getExecuteDate());
		resInfo.setTransNo(info.getTransNo());
		resInfo.setAmount(info.getAmount());
		
		//交易方向在账户中设置
		//resInfo.setTransDirection();
		resInfo.setDtInterestStart(info.getInterestStartDate());
		resInfo.setAbstractStr(info.getAbstract());
		resInfo.setStatusID(SETTConstant.TransactionStatus.CHECK);
		resInfo.setAbstractID(info.getAbstractID());
		//只为定期转活期金额使用!!!!!
		//double amount = 0.0;
		//即使是提前支取也支取全部金额只是利息按照提前支取的金额计算
		//if(info.getDrawAmount() == 0)//不是提前支取
		//	amount = info.getAmount();
		//else
		//	amount = info.getDrawAmount();
		resInfo.setFixedDepositNo(info.getDepositNo());
		switch (transType)
		{
			case TRANS_DEPOSIT_CURRENT_PRINCIPAL :
				{
					resInfo.setTransAccountID(info.getCurrentAccountID());
					if (info.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)
					{
						resInfo.setAmount(info.getAmount());
					}
					else
						resInfo.setAmount(info.getDrawAmount());
					resInfo.setOppAccountID(info.getAccountID());
					//账户金额查询区分金额类型增加
						resInfo.setAmountType(SETTConstant.AmountType.AmountType_1);
				    //

				}
				break;
			case TRANS_DEPOIST_CURRENT_INTEREST :
				{
				    resInfo.setTransAccountID(info.getReceiveInterestAccountID());
					if (info.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)
						resInfo.setAmount(info.getTotalInterest());
					else
						resInfo.setAmount(info.getNoticeTotalInterest());
					//无对方账户
					resInfo.setOppAccountID(-1);
					//账户金额查询区分金额类型增加
						resInfo.setAmountType(SETTConstant.AmountType.AmountType_2);
			        //
				}
				break;
			case TRANS_DEPOIST_CURRENT_PRINCIPALANDINTEREST :
				{
				    resInfo.setTransAccountID(info.getCurrentAccountID());
					log.debug("------本金利息汇总处理-------");
					if (info.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)
						resInfo.setAmount(info.getAmount() + info.getTotalInterest());
					else
						resInfo.setAmount(info.getDrawAmount() + info.getDrawInterest());
					resInfo.setOppAccountID(info.getAccountID());
					log.debug("--------活期账户ID1:" + resInfo.getTransAccountID() + "---------");
					//账户金额查询区分金额类型增加
						resInfo.setAmountType(SETTConstant.AmountType.AmountType_11);
		            //
				}
				break;
			case TRANS_DEPOSIT_FIXED :
				{
				    resInfo.setTransAccountID(info.getAccountID());
					resInfo.setTransSubAccountID(info.getSubAccountID());
					if (info.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)
						resInfo.setAmount(info.getAmount());
					else
						resInfo.setAmount(info.getDrawAmount());
					resInfo.setOppAccountID(info.getCurrentAccountID());
					//账户金额查询区分金额类型增加
						resInfo.setAmountType(SETTConstant.AmountType.AmountType_1);
	                //
				}
				break;
			case TRANS_DEPOSIT_MARGIN :
			{
				resInfo.setTransAccountID(info.getAccountID());
				
				resInfo.setTransSubAccountID(info.getSubAccountID());
				
				resInfo.setAmount(info.getDrawAmount());
				
				resInfo.setOppAccountID(info.getCurrentAccountID());
				//为账户对账单信息查询 所加
				if(info.getCurrentAccountID()>0)
				{
					Sett_AccountDAO sett_accountDao = new Sett_AccountDAO();
					try {
						AccountInfo accountInfo = sett_accountDao.findByID(info.getCurrentAccountID());
						resInfo.setOppAccountNo(accountInfo.getAccountNo());
						resInfo.setOppAccountName(accountInfo.getAccountName());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
				{
					resInfo.setOppAccountNo(info.getExtAcctNo());
					resInfo.setOppAccountName(info.getExtClientName());
				}
				//
				//账户金额查询区分金额类型增加
					resInfo.setAmountType(SETTConstant.AmountType.AmountType_1);
                //
			}
			   break;
			case TRANS_DRAW_MARGIN :
			{
				//保证金支取的时候,活期账户收款
				resInfo.setTransAccountID(info.getCurrentAccountID());

				resInfo.setAmount(info.getDrawAmount());
				
				resInfo.setOppAccountID(info.getAccountID());
				//为账户对账单信息查询 所加
				if(info.getAccountID ()>0)
				{
					Sett_AccountDAO sett_accountDao = new Sett_AccountDAO();
					try {
						AccountInfo accountInfo = sett_accountDao.findByID(info.getAccountID ());
						resInfo.setOppAccountNo(accountInfo.getAccountNo());
						resInfo.setOppAccountName(accountInfo.getAccountName());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//
				//账户金额查询区分金额类型增加
				 	resInfo.setAmountType(SETTConstant.AmountType.AmountType_1);
                //
			}
			break;
			default :
				break;
		}
		log.debug("-----transferTransFixedDrawInfoToTransAccountDetailInfo's Output-----");
		log.debug(UtilOperation.dataentityToString(resInfo));
		return resInfo;
	}
	
	/**
	 * 定期开立，将定期交易信息转换成定期子账户信息，被注释的Setter方法为在开立时不需要使用的参数
	 */
	private SubAccountFixedInfo transferTransFixedOpenInfoToSubAccountFixedInfo(TransFixedOpenInfo info)
	{
		//
		SubAccountFixedInfo safi = null;
		if (info != null)
		{
			safi = new SubAccountFixedInfo();
			safi.setAccountID(info.getAccountID());
			//safi.setInterest();
			safi.setBalance(0.0);
			//safi.setStatusID();
			safi.setDepositNo(info.getDepositNo());
			safi.setOpenAmount(info.getAmount());
			safi.setRate(info.getRate());
			safi.setStartDate(info.getStartDate());
			safi.setEndDate(info.getEndDate());
			safi.setOpenDate(info.getExecuteDate());
			safi.setIsInterest(1);
			safi.setClearInterestDate(info.getInterestStartDate());
			//safi.setPreDrawInterest();
			safi.setPreDrawDate(info.getStartDate());
			safi.setDepositTerm(info.getDepositTerm());
			safi.setInterestPlanID(info.getInterestPlanID());
			safi.setNoticeDay(info.getNoticeDay());
			//safi.setInterestAccountID();
			safi.setSealNo(info.getSealNo());
			safi.setSealBankID(info.getSealBankID());
			safi.setStatusID(SETTConstant.SubAccountStatus.NORMAL);
			safi.setIsInterest(1);
			//safi.setDailyUncheckAmount();
			safi.setIsAutoContinue(info.getIsAutoContinue());
			safi.setAutoContinueType(info.getAutocontinuetype());
			safi.setInterestAccountID(info.getAutocontinueaccountid());
		}
		return safi;
	}
	
	/**
	 * 保证金开立，将保证金交易信息转换成保证金子账户信息，被注释的Setter方法为在开立时不需要使用的参数
	 */
	private SubAccountMarginInfo transferTransMarginOpenInfoToSubAccountMargindInfo(TransMarginOpenInfo info)
	{
		//
		SubAccountMarginInfo safi = null;
		if (info != null)
		{
			safi = new SubAccountMarginInfo();
			safi.setAccountID(info.getAccountID());
			//safi.setInterest();
			safi.setBalance(0.0);
			//safi.setStatusID();
			safi.setDepositNo(info.getDepositNo());
			safi.setOpenAmount(info.getAmount());
			safi.setRate(info.getRate());
			safi.setStartDate(info.getStartDate());
			safi.setEndDate(info.getEndDate());
			safi.setOpenDate(info.getExecuteDate());
			safi.setIsInterest(1);
			safi.setClearInterestDate(info.getInterestStartDate());
			//safi.setPreDrawInterest();
			safi.setPreDrawDate(info.getStartDate());
			safi.setDepositTerm(info.getDepositTerm());
			safi.setInterestPlanID(info.getInterestPlanID());
			safi.setNoticeDay(info.getNoticeDay());
			//safi.setInterestAccountID();
			safi.setSealNo(info.getSealNo());
			safi.setSealBankID(info.getSealBankID());
			safi.setStatusID(SETTConstant.SubAccountStatus.NORMAL);
			safi.setIsInterest(1);
			//safi.setDailyUncheckAmount();
		}
		return safi;
	}
	/***/
	private TransAccountDetailInfo transferTransFixedOpenInfoToTransAccountDetailInfo(TransFixedOpenInfo info, int transType)
	{
		TransAccountDetailInfo resInfo = new TransAccountDetailInfo();
		//resInfo.setId(id);
		resInfo.setOfficeID(info.getOfficeID());
		resInfo.setCurrencyID(info.getCurrencyID());
		resInfo.setTransactionTypeID(info.getTransactionTypeID());
		resInfo.setDtExecute(info.getExecuteDate());
		resInfo.setTransNo(info.getTransNo());
		resInfo.setAmount(info.getAmount());
		//交易方向在账户中设置
		//resInfo.setTransDirection();
		resInfo.setDtInterestStart(info.getInterestStartDate());
		resInfo.setAbstractStr(info.getAbstract());
		//resInfo.setStatusID();
		resInfo.setBillNo(info.getBillNo());
		resInfo.setBillTypeID(info.getBillTypeID());
		resInfo.setFixedDepositNo(info.getDepositNo());
		resInfo.setAbstractID(info.getAbstractID());
		//账户金额查询区分金额类型增加
			resInfo.setAmountType(SETTConstant.AmountType.AmountType_1);
		//

		//resInfo.setBankChequeNo();
		//resInfo.setDtExecute();
		//resInfo.setGroup();
		Sett_AccountDAO sett_accountDao = new Sett_AccountDAO();
		if (transType == TRANS_CURRENT)
		{
			//活期交易，交易账户为活期账户
			resInfo.setTransAccountID(info.getCurrentAccountID());
			//是否不用设子账户？？
			//resInfo.setTransSubAccountID();
			//活期交易，对方交易账户为定期账户
			resInfo.setOppAccountID(info.getAccountID());
			//是否不用设子账户？？
			//为账户对账单信息查询 所加
			if(info.getAccountID()>0)
			{
				try {
					AccountInfo accountInfo = sett_accountDao.findByID(info.getAccountID());
					resInfo.setOppAccountNo(accountInfo.getAccountNo());
					resInfo.setOppAccountName(accountInfo.getAccountName());
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//
		}
		else if (transType == TRANS_FIXED)
		{
			//定期交易，交易账户为定期账户
			resInfo.setTransAccountID(info.getAccountID());
			//是否不用设子账户？？
			//resInfo.setTransSubAccountID();
			//定期交易，对方交易账户为活期账户??
			//resInfo.setOppAccountID(info.getCurrentAccountID());
			//是否不用设子账户？？
			//resInfo.setOppSubAccountID();
			//为账户对账单信息查询 所加
			if(info.getCurrentAccountID()>0)
			{
				try {
					AccountInfo accountInfo = sett_accountDao.findByID(info.getCurrentAccountID());
					resInfo.setOppAccountNo(accountInfo.getAccountNo());
					resInfo.setOppAccountName(accountInfo.getAccountName());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(info.getBankID()>0)
			{
				Sett_BranchDAO sett_branchDao = new Sett_BranchDAO();
				try {
					BranchInfo branchInfo = sett_branchDao.findByID(info.getBankID());
					resInfo.setOppAccountNo(branchInfo.getBankAccountCode());
					resInfo.setOppAccountName(branchInfo.getBranchName());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//
			
		}
		log.debug(UtilOperation.dataentityToString(resInfo));
		return resInfo;
	}
	
	private TransAccountDetailInfo transferTransMarginOpenInfoToTransAccountDetailInfo(TransMarginOpenInfo info, int transType)
	{
		TransAccountDetailInfo resInfo = new TransAccountDetailInfo();
		//resInfo.setId(id);
		resInfo.setOfficeID(info.getOfficeID());
		resInfo.setCurrencyID(info.getCurrencyID());
		resInfo.setTransactionTypeID(info.getTransactionTypeID());
		resInfo.setDtExecute(info.getExecuteDate());
		resInfo.setTransNo(info.getTransNo());
		resInfo.setAmount(info.getAmount());
		//交易方向在账户中设置
		//resInfo.setTransDirection();
		resInfo.setDtInterestStart(info.getInterestStartDate());
		resInfo.setAbstractStr(info.getAbstract());
		//resInfo.setStatusID();
		resInfo.setBillNo(info.getBillNo());
		resInfo.setBillTypeID(info.getBillTypeID());
		resInfo.setFixedDepositNo(info.getDepositNo());
		resInfo.setAbstractID(info.getAbstractID());
		//resInfo.setBankChequeNo();
		//resInfo.setDtExecute();
		//resInfo.setGroup();
		if (transType == TRANS_CURRENT)
		{
			//活期交易，交易账户为活期账户
			resInfo.setTransAccountID(info.getCurrentAccountID());
			//是否不用设子账户？？
			//resInfo.setTransSubAccountID();
			//活期交易，对方交易账户为定期账户
			resInfo.setOppAccountID(info.getAccountID());
			//是否不用设子账户？？
			//为账户对账单信息查询 所加
			if(info.getAccountID()>0)
			{
				Sett_AccountDAO sett_accountDao = new Sett_AccountDAO();
				try {
					AccountInfo accountInfo = sett_accountDao.findByID(info.getAccountID());
					resInfo.setOppAccountNo(accountInfo.getAccountNo());
					resInfo.setOppAccountName(accountInfo.getAccountName());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//
			//账户金额查询区分金额类型增加
				resInfo.setAmountType(SETTConstant.AmountType.AmountType_1);
			//

		
		}
		else if(transType == TRANS_CURRENT_COMMISSION)
		{
			//活期交易，交易账户为手续费活期账户
			resInfo.setTransAccountID(info.getCommissionCurrentAccountID());
			//是否不用设子账户？？
			//resInfo.setTransSubAccountID();
			//活期交易，对方交易账户为保证金存款账户
			//resInfo.setOppAccountID(info.getAccountID());
			//是否不用设子账户？？
			
			//设置手续费金额
			resInfo.setAmount(info.getCommissionAmount());
			//为账户对账单信息查询 所加
			if(info.getAccountID()>0)
			{
				Sett_AccountDAO sett_accountDao = new Sett_AccountDAO();
				try {
					AccountInfo accountInfo = sett_accountDao.findByID(info.getAccountID());
					resInfo.setOppAccountNo(accountInfo.getAccountNo());
					resInfo.setOppAccountName(accountInfo.getAccountName());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//
			//账户金额查询区分金额类型增加
				resInfo.setAmountType(SETTConstant.AmountType.AmountType_9);
			//
		
		}
		else if (transType == TRANS_FIXED)
		{
			//定期交易，交易账户为定期账户
			resInfo.setTransAccountID(info.getAccountID());
			//是否不用设子账户？？
			//resInfo.setTransSubAccountID();
			//定期交易，对方交易账户为活期账户??
			//resInfo.setOppAccountID(info.getCurrentAccountID());
			//是否不用设子账户？？
			//resInfo.setOppSubAccountID();
			//为账户对账单信息查询 所加
			if(info.getCurrentAccountID()>0)
			{
				Sett_AccountDAO sett_accountDao = new Sett_AccountDAO();
				try {
					AccountInfo accountInfo = sett_accountDao.findByID(info.getCurrentAccountID());
					resInfo.setOppAccountNo(accountInfo.getAccountNo());
					resInfo.setOppAccountName(accountInfo.getAccountName());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(info.getBankID()>0)
			{
				Sett_BranchDAO sett_branchDao = new Sett_BranchDAO();
				try {
					BranchInfo branchInfo = sett_branchDao.findByID(info.getBankID());
					resInfo.setOppAccountNo(branchInfo.getBankAccountCode());
					resInfo.setOppAccountName(branchInfo.getBranchName());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//
			//账户金额查询区分金额类型增加
				resInfo.setAmountType(SETTConstant.AmountType.AmountType_1);
			//
		}
		log.debug(UtilOperation.dataentityToString(resInfo));
		return resInfo;
	}
	
	private TransAccountDetailInfo transferTransMarginOpenInfoToSubAccountMarginInfo(TransMarginOpenInfo info, int transType)
	{
		TransAccountDetailInfo resInfo = new TransAccountDetailInfo();
		//resInfo.setId(id);
		resInfo.setOfficeID(info.getOfficeID());
		resInfo.setCurrencyID(info.getCurrencyID());
		resInfo.setTransactionTypeID(info.getTransactionTypeID());
		resInfo.setDtExecute(info.getExecuteDate());
		resInfo.setTransNo(info.getTransNo());
		resInfo.setAmount(info.getAmount());
		//交易方向在账户中设置
		//resInfo.setTransDirection();
		resInfo.setDtInterestStart(info.getInterestStartDate());
		resInfo.setAbstractStr(info.getAbstract());
		//resInfo.setStatusID();
		resInfo.setBillNo(info.getBillNo());
		resInfo.setBillTypeID(info.getBillTypeID());
		resInfo.setFixedDepositNo(info.getDepositNo());
		resInfo.setAbstractID(info.getAbstractID());
		//resInfo.setBankChequeNo();
		//resInfo.setDtExecute();
		//resInfo.setGroup();
		if (transType == TRANS_CURRENT)
		{
			//活期交易，交易账户为活期账户
			resInfo.setTransAccountID(info.getCurrentAccountID());
			//是否不用设子账户？？
			//resInfo.setTransSubAccountID();
			//活期交易，对方交易账户为定期账户
			resInfo.setOppAccountID(info.getAccountID());
			//是否不用设子账户？？
		}
		else if (transType == TRANS_FIXED)
		{
			//定期交易，交易账户为定期账户
			resInfo.setTransAccountID(info.getAccountID());
			//是否不用设子账户？？
			//resInfo.setTransSubAccountID();
			//定期交易，对方交易账户为活期账户??
			//resInfo.setOppAccountID(info.getCurrentAccountID());
			//是否不用设子账户？？
			//resInfo.setOppSubAccountID();
		}
		log.debug(UtilOperation.dataentityToString(resInfo));
		return resInfo;
	}
	
	private TransAccountDetailInfo transferTransGrantLoanInfoToTransAccountDetailInfo(TransGrantLoanInfo info)
	{
		TransAccountDetailInfo resInfo = new TransAccountDetailInfo();
		//resInfo.setId(id);
		resInfo.setOfficeID(info.getOfficeID());
		resInfo.setCurrencyID(info.getCurrencyID());
		resInfo.setTransactionTypeID(info.getTransactionTypeID());
		resInfo.setDtExecute(info.getExecute());
		resInfo.setTransNo(info.getTransNo());
		resInfo.setAmount(info.getAmount());
		resInfo.setTransAccountID(info.getLoanAccountID());
		resInfo.setOppAccountID(info.getDepositAccountID());
		resInfo.setLoanNoteID(info.getLoanNoteID());
		//交易方向在账户中设置
		//resInfo.setTransDirection();
		resInfo.setDtInterestStart(info.getInterestStart());
		resInfo.setAbstractStr(info.getAbstract());
		resInfo.setAbstractID(info.getAbstractID());
		//resInfo.setStatusID();
		//resInfo.setBillNo(info.getBillNo());
		//resInfo.setBillTypeID(info.getBillTypeID());
		//		为账户对账单信息查询 所加
		if(info.getDepositAccountID()>0)
		{
			Sett_AccountDAO sett_accountDao = new Sett_AccountDAO();
			try {
				AccountInfo accountInfo = sett_accountDao.findByID(info.getDepositAccountID());
				resInfo.setOppAccountNo(accountInfo.getAccountNo());
				resInfo.setOppAccountName(accountInfo.getAccountName());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			resInfo.setOppAccountNo(info.getExtAcctNo());
			resInfo.setOppAccountName(info.getExtAcctName());
		}
		//
		//账户金额查询区分金额类型增加
			resInfo.setAmountType(SETTConstant.AmountType.AmountType_1);
		//

		return resInfo;
	}
	private SubAccountLoanInfo transferTransGrantLoanInfoToSubAccountLoanInfo(TransGrantLoanInfo info)
	{
		SubAccountLoanInfo resInfo = new SubAccountLoanInfo();
		resInfo.setAccountID(info.getLoanAccountID());
		//resInfo.setArrearageInterest(info.get)
		resInfo.setBalance(0.0);
		resInfo.setOpenAmount(info.getAmount());
		resInfo.setCalculateInterestDate(info.getInterestStart());
		resInfo.setClearCommissionDate(info.getInterestStart());
		resInfo.setClearCompoundDate(info.getInterestStart());
		resInfo.setClearInterestDate(info.getInterestStart());
		resInfo.setClearInterestTaxDate(info.getInterestStart());
		resInfo.setClearOverDueDate(info.getInterestStart());
		resInfo.setClearSureFeeDate(info.getInterestStart());
		//resInfo.setCommission();
		//resInfo.setCompoundAccountID()
		//resInfo.setCompoundInterest(compoundInterest)
		//resInfo.setDailyUncheckAmount(dailyUncheckAmount)
		resInfo.setEffectiveTaxDate(info.getInterestTaxRateVauleDate());
		//resInfo.setFinishDate(info.get);
		//resInfo.setInterest(interest)
		//resInfo.setInterestTax(info.getInterestTaxRate());
		//resInfo.setInterestTaxAccountID(info.geti)
		//resInfo.setInterestTaxRate(info.getInterestTaxRate());
		//@TBD is cyc loan
		//resInfo.setIsCycLoan(info.getis)
		//在账户开立中设置
		//resInfo.setIsInterest(false);
		resInfo.setLoanNoteID(info.getLoanNoteID());
		//resInfo.setOpenAmount();
		resInfo.setOpenDate(info.getExecute());
		//resInfo.setOverDueAccountID(info.get)
		//resInfo.setOverDueInterest()
		resInfo.setCommissionAccountID(info.getPayCommisionAccountID());
		resInfo.setPaySuretyAccountID(info.getPaySuretyFeeAccountID());
		resInfo.setPreDrawDate(info.getInterestStart());
		//resInfo.setPreDrawInterest(info.getpr)
		resInfo.setPayInterestAccountID(info.getPayInterestAccountID());
		resInfo.setReceiveInterestAccountID(info.getReceiveInterestAccountID());
		resInfo.setReceiveSuretyAccountID(info.getReceiveSuretyFeeAccountID());
		resInfo.setPaySuretyAccountID(info.getPaySuretyFeeAccountID());
		resInfo.setStatusID(SETTConstant.SubAccountStatus.NORMAL);
		resInfo.setConsignAccountID(info.getConsignDepositAccountID());
		resInfo.setIsInterest(1);
		//resInfo.setSuretyFee()
		resInfo.setInterestTaxRate(info.getInterestTaxRate());
		resInfo.setInterestTaxRatePlanID(info.getInterestTaxPlanId());
		return resInfo;
	}
	private TransAccountDetailInfo transferTransRepaymentLoanInfoToTransAccountDetailInfo(TransRepaymentLoanInfo info, int amountType)
	{
		TransAccountDetailInfo resInfo = new TransAccountDetailInfo();
		//resInfo.setId(id);
		resInfo.setOfficeID(info.getOfficeID());
		resInfo.setCurrencyID(info.getCurrencyID());
		resInfo.setTransactionTypeID(info.getTransactionTypeID());
		resInfo.setDtExecute(info.getExecute());
		if (info.getTransactionTypeID() == SETTConstant.TransactionType.MULTILOANRECEIVE)
			resInfo.setTransNo(info.getTempTransNO());
		else
			resInfo.setTransNo(info.getTransNo());
		resInfo.setAbstractID(info.getAbstractID());
		//resInfo.setSerialNo(info.getSerialNo());
		double interest = info.getRealInterest() + info.getRealCompoundInterest() + info.getRealOverDueInterest();
		
		System.out.println(info.getRealCompoundInterest() +"计算利息数据====="+info.getRealOverDueInterest()+"==================="+info.getRealInterest());
		switch (amountType)
		{
			case RepaymentLoan_WITHDRAW_SUM_AMOUNT :
				{
					log.debug("info.getAmount():" + info.getAmount());
					log.debug("interest:" + interest);
					log.debug("info.getRealSuretyFee():" + info.getRealSuretyFee());
					log.debug("info.getRealCommission():" + info.getRealCommission());
					resInfo.setAmount(info.getAmount() + interest + info.getRealSuretyFee() + info.getRealCommission());
					log.debug("resInfo.getAmount():" + resInfo.getAmount());
					if (info.getDepositAccountID() > 0) //是贷款收回
						resInfo.setTransAccountID(info.getDepositAccountID());
					else
						resInfo.setTransAccountID(info.getPayInterestAccountID());
					resInfo.setOppAccountID(info.getLoanAccountID());
					//为账户对账单信息查询 所加
					if(info.getLoanAccountID()>0)
					{
						Sett_AccountDAO sett_accountDao = new Sett_AccountDAO();
						try {
							AccountInfo accountInfo = sett_accountDao.findByID(info.getLoanAccountID());
							resInfo.setOppAccountNo(accountInfo.getAccountNo());
							resInfo.setOppAccountName(accountInfo.getAccountName());
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					//
					//账户金额查询区分金额类型增加
						resInfo.setAmountType(SETTConstant.AmountType.AmountType_11);
					//

				}
				break;
			case RepaymentLoan_WITHDRAW_DIV_AMOUNT :
				{
					resInfo.setAmount(info.getAmount());
					resInfo.setTransAccountID(info.getDepositAccountID());
					resInfo.setOppAccountID(info.getLoanAccountID());
					//为账户对账单信息查询 所加
					if(info.getLoanAccountID()>0)
					{
						Sett_AccountDAO sett_accountDao = new Sett_AccountDAO();
						try {
							AccountInfo accountInfo = sett_accountDao.findByID(info.getLoanAccountID());
							resInfo.setOppAccountNo(accountInfo.getAccountNo());
							resInfo.setOppAccountName(accountInfo.getAccountName());
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					//
					//账户金额查询区分金额类型增加
						resInfo.setAmountType(SETTConstant.AmountType.AmountType_1);
					//
				}
				break;
			case RepaymentLoan_WITHDRAW_DIV_INTEREST :
				{					
					//手续费单独收取的时候利息支付需要去掉手续费那部分值
					/*if(info.getTransactionTypeID()==SETTConstant.TransactionType.INTERESTFEEPAYMENT&&(info.getCommissionAccountID()>0||info.getCommissionBankID()>0)){
						interest=interest-info.getCommission();
					}*/
					resInfo.setAmount(interest);
					resInfo.setTransAccountID(info.getPayInterestAccountID());
					resInfo.setOppAccountID(info.getReceiveInterestAccountID());
					//为账户对账单信息查询 所加
					if(info.getReceiveInterestAccountID()>0)
					{
						Sett_AccountDAO sett_accountDao = new Sett_AccountDAO();
						try {
							AccountInfo accountInfo = sett_accountDao.findByID(info.getReceiveInterestAccountID());
							resInfo.setOppAccountNo(accountInfo.getAccountNo());
							resInfo.setOppAccountName(accountInfo.getAccountName());
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					//
					//账户金额查询区分金额类型增加
						resInfo.setAmountType(SETTConstant.AmountType.AmountType_2);
					//
				}
				break;
			case RepaymentLoan_WITHDRAW_DIV_SURETY :
				{
					resInfo.setAmount(info.getRealSuretyFee());
					resInfo.setTransAccountID(info.getPaySuretyAccountID());
					resInfo.setOppAccountID(info.getReceiveSuretyAccountID());
					//账户金额查询区分金额类型增加
						resInfo.setAmountType(SETTConstant.AmountType.AmountType_8);
					//
				}
				break;
			case RepaymentLoan_WITHDRAW_DIV_COMMISSION :
				{
					resInfo.setAmount(info.getRealCommission());
					resInfo.setTransAccountID(info.getCommissionAccountID());
					//resInfo.setOppAccountID();
					//账户金额查询区分金额类型增加
						resInfo.setAmountType(SETTConstant.AmountType.AmountType_9);
					//
				}
				break;
			case RepaymentLoan_DEPOSIT_INTEREST :
				{
					resInfo.setTransAccountID(info.getReceiveInterestAccountID());
					resInfo.setAmount(interest - info.getRealInterestTax());
					//resInfo.setAmount(interest - info.getRealInterestTax()-info.getCommission());
					resInfo.setOppAccountID(info.getPayInterestAccountID());
					//为账户对账单信息查询 所加
					if(info. getPayInterestAccountID () > 0)
					{
						Sett_AccountDAO sett_accountDao = new Sett_AccountDAO();
						try {
							AccountInfo accountInfo = sett_accountDao.findByID(info.getPayInterestAccountID());
							resInfo.setOppAccountNo(accountInfo.getAccountNo());
							resInfo.setOppAccountName(accountInfo.getAccountName());
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else if(info.getInterestBankID()>0)
					{
						Sett_BranchDAO sett_branchDao = new Sett_BranchDAO();
						try {
							BranchInfo branchInfo = sett_branchDao.findByID(info.getInterestBankID());
							resInfo.setOppAccountNo(branchInfo.getBankAccountCode());
							resInfo.setOppAccountName(branchInfo.getBranchName());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					//
					//账户金额查询区分金额类型增加
						resInfo.setAmountType(SETTConstant.AmountType.AmountType_2);
					//
				}
				break;
			case RepaymentLoan_DEPOSIT_SURETY :
				{
					resInfo.setTransAccountID(info.getReceiveSuretyAccountID());
					resInfo.setAmount(info.getRealSuretyFee());
					resInfo.setOppAccountID(info.getPaySuretyAccountID());
					//为账户对账单信息查询 所加
					if(info.getPaySuretyAccountID() > 0)
					{
						Sett_AccountDAO sett_accountDao = new Sett_AccountDAO();
						try {
							AccountInfo accountInfo = sett_accountDao.findByID(info.getPaySuretyAccountID());
							resInfo.setOppAccountNo(accountInfo.getAccountNo());
							resInfo.setOppAccountName(accountInfo.getAccountName());
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else if(info.getSuretyBankID()>0)
					{
						Sett_BranchDAO sett_branchDao = new Sett_BranchDAO();
						try {
							BranchInfo branchInfo = sett_branchDao.findByID(info.getSuretyBankID());
							resInfo.setOppAccountNo(branchInfo.getBankAccountCode());
							resInfo.setOppAccountName(branchInfo.getBranchName());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					//
					//账户金额查询区分金额类型增加
						resInfo.setAmountType(SETTConstant.AmountType.AmountType_8);
					//
				}
				break;
			case RepaymentLoan_GRANT_AMOUNT :
				{
					resInfo.setAmount(info.getAmount());
					resInfo.setTransAccountID(info.getLoanAccountID());
					resInfo.setTransSubAccountID(info.getSubAccountID());
					resInfo.setOppAccountID(info.getDepositAccountID());
					//为账户对账单信息查询 所加
					if(info.getDepositAccountID() > 0)
					{
						Sett_AccountDAO sett_accountDao = new Sett_AccountDAO();
						try {
							AccountInfo accountInfo = sett_accountDao.findByID(info.getDepositAccountID());
							resInfo.setOppAccountNo(accountInfo.getAccountNo());
							resInfo.setOppAccountName(accountInfo.getAccountName());
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else if(info.getBankID()>0)
					{
						Sett_BranchDAO sett_branchDao = new Sett_BranchDAO();
						try {
							BranchInfo branchInfo = sett_branchDao.findByID(info.getBankID());
							resInfo.setOppAccountNo(branchInfo.getBankAccountCode());
							resInfo.setOppAccountName(branchInfo.getBranchName());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					//
					//账户金额查询区分金额类型增加
						resInfo.setAmountType(SETTConstant.AmountType.AmountType_1);
					//
				}
				break;
			case RepaymentLoan_CONSIGN_WITHDRAW :
				{
					resInfo.setAmount(info.getAmount());
					resInfo.setTransAccountID(info.getConsignAccountID());
					resInfo.setOppAccountID(info.getConsignDepositAccountID());
					Sett_AccountDAO sett_accountDao = new Sett_AccountDAO();
					if(info.getConsignDepositAccountID()>0)
					{
						try {
							AccountInfo accountInfo = sett_accountDao.findByID(info.getConsignDepositAccountID());
							resInfo.setOppAccountNo(accountInfo.getAccountNo());
							resInfo.setOppAccountName(accountInfo.getAccountName());
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					//账户金额查询区分金额类型增加
						resInfo.setAmountType(SETTConstant.AmountType.AmountType_1);
					//
				}
				break;
			case RepaymentLoan_CONSIGN_DEPOSIT :
				{
					resInfo.setAmount(info.getAmount());
					resInfo.setTransAccountID(info.getConsignDepositAccountID());
					resInfo.setOppAccountID(info.getDepositAccountID());
					//为账户对账单信息查询 所加
					if(info.getDepositAccountID() > 0)
					{
						Sett_AccountDAO sett_accountDao = new Sett_AccountDAO();
						try {
							AccountInfo accountInfo = sett_accountDao.findByID(info.getDepositAccountID());
							resInfo.setOppAccountNo(accountInfo.getAccountNo());
							resInfo.setOppAccountName(accountInfo.getAccountName());
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else if(info.getBankID()>0)
					{
						Sett_BranchDAO sett_branchDao = new Sett_BranchDAO();
						try {
							BranchInfo branchInfo = sett_branchDao.findByID(info.getBankID());
							resInfo.setOppAccountNo(branchInfo.getBankAccountCode());
							resInfo.setOppAccountName(branchInfo.getBranchName());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					//
					//账户金额查询区分金额类型增加
						resInfo.setAmountType(SETTConstant.AmountType.AmountType_1);
					//
				}
				break;
		}
		resInfo.setLoanNoteID(info.getLoanNoteID());
		//交易方向在账户中设置
		//resInfo.setTransDirection();
		resInfo.setDtInterestStart(info.getInterestStart());
		resInfo.setAbstractStr(info.getAbstract());
		//在账户中设置
		//resInfo.setStatusID();
		resInfo.setBillNo(info.getBillNo());
		resInfo.setBillTypeID(info.getBillTypeID());
		log.debug(UtilOperation.dataentityToString(resInfo));
		return resInfo;
	}
	private TransAccountDetailInfo transferTransGrantDiscountInfoToTransAccountDetail(TransGrantDiscountInfo info)
	{
		TransAccountDetailInfo resInfo = new TransAccountDetailInfo();
		resInfo.setAbstractStr(info.getAbstract());
		resInfo.setAmount(info.getDiscountBillAmount());
		//resInfo.setBankChequeNo()
		//resInfo.setBillNo(billNo)
		//resInfo.setBillTypeID(serialVersionUID)
		resInfo.setCurrencyID(info.getCurrencyID());
		resInfo.setDtExecute(info.getExecuteDate());
		resInfo.setDtInterestStart(info.getInterestStartDate());
		//resInfo.setFixedDepositNo(fixedDepositNo)
		//resInfo.setGroup(info.get)
		//resInfo.setId()
		//resInfo.setInterestBackFlag(serialVersionUID)
		//resInfo.setLoanNoteID(info.get)
		resInfo.setOfficeID(info.getOfficeID());
		//resInfo.setOppAccountID()
		resInfo.setTransAccountID(info.getDiscountAccountID());
		//resInfo.setTransDirection()
		resInfo.setTransNo(info.getTransNo());
		resInfo.setTransactionTypeID(info.getTransactionTypeID());
		resInfo.setAbstractID(info.getNAbstract());
		//为账户对账单信息查询 所加
		if(info.getDepositAccountID()>0){
		Sett_AccountDAO sett_accountDao = new Sett_AccountDAO();
			try {
				AccountInfo accountInfo = sett_accountDao.findByID(info.getDepositAccountID());
				resInfo.setOppAccountNo(accountInfo.getAccountNo());
				resInfo.setOppAccountName(accountInfo.getAccountName());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			resInfo.setOppAccountNo(info.getExtAcctNo());
			resInfo.setOppAccountName(info.getExtAcctName());
		}
		//
		//账户金额查询区分金额类型增加
			resInfo.setAmountType(SETTConstant.AmountType.AmountType_1);
		//

		return resInfo;
	}
	private SubAccountLoanInfo transferTransGrantDiscountInfoToSubAccountLoanInfo(TransGrantDiscountInfo info)
	{
		SubAccountLoanInfo resInfo = new SubAccountLoanInfo();
		resInfo.setAccountID(info.getDiscountAccountID());
		//resInfo.setArrearageInterest(info.get)
		resInfo.setBalance(0.0);
		resInfo.setOpenAmount(info.getDiscountBillAmount());
		resInfo.setCalculateInterestDate(info.getInterestStartDate());
		resInfo.setClearCommissionDate(info.getInterestStartDate());
		resInfo.setClearCompoundDate(info.getInterestStartDate());
		resInfo.setClearInterestDate(info.getInterestStartDate());
		resInfo.setClearInterestTaxDate(info.getInterestStartDate());
		resInfo.setClearOverDueDate(info.getInterestStartDate());
		resInfo.setClearSureFeeDate(info.getInterestStartDate());
		//		在账户开立中设置
		//resInfo.setCommission();
		//resInfo.setCommissionAccountID(info.getPayCommisionAccountID());
		//resInfo.setCompoundAccountID()
		//resInfo.setCompoundInterest(compoundInterest)
		//resInfo.setDailyUncheckAmount(dailyUncheckAmount)
		//resInfo.setEffectiveTaxDate(info.getInterestTaxRateVauleDate());
		//resInfo.setFinishDate(info.get);
		//resInfo.setInterest(interest)
		//resInfo.setInterestTax(info.getInterestTaxRate());
		//resInfo.setInterestTaxAccountID(info.geti)
		//resInfo.setInterestTaxRate(info.getInterestTaxRate());
		//@TBD is cyc loan
		//resInfo.setIsCycLoan(info.getis)
		//在账户开立中设置
		//resInfo.setIsInterest(false);
		resInfo.setLoanNoteID(info.getDiscountNoteID());
		//resInfo.setOpenAmount();
		resInfo.setOpenDate(info.getExecuteDate());
		//resInfo.setOverDueAccountID(info.get)
		//resInfo.setOverDueInterest()
		//resInfo.setPayInterestAccountID(info.getPayInterestAccountID());
		resInfo.setPreDrawDate(info.getInterestStartDate());
		//resInfo.setPreDrawInterest(info.getpr)
		//resInfo.setReceiveInterestAccountID(info.getReceiveInterestAccountID());
		resInfo.setStatusID(SETTConstant.AccountStatus.NORMAL);
		//resInfo.setSuretyAccountID(info.getPaySuretyFeeAccountID());
		//resInfo.setSuretyFee(info.get)
		return resInfo;
	}
	private TransAccountDetailInfo transferTransRepaymentDiscountInfoToTransAccountDetailInfo(TransRepaymentDiscountInfo info, int type)
	{
		TransAccountDetailInfo resInfo = new TransAccountDetailInfo();
		resInfo.setAbstractStr(info.getSAbstract());
		//resInfo.setBankChequeNo()
		//resInfo.setBillNo()
		//resInfo.setBillTypeID()
		resInfo.setCurrencyID(info.getNCurrencyID());
		resInfo.setDtExecute(info.getDtExecute());
		resInfo.setDtInterestStart(info.getDtInterestStart());
		//resInfo.setFixedDepositNo()
		//resInfo.setGroup()
		//resInfo.setInterestBackFlag()
		resInfo.setLoanNoteID(info.getNDiscountNoteID());
		resInfo.setOfficeID(info.getNOfficeID());
		resInfo.setTransactionTypeID(info.getNTransactionTypeID());
		//resInfo.setOppAccountID()
		//resInfo.setOppSubAccountID()
		resInfo.setAbstractID(info.getNAbstractID());
		if (type == RepaymentDiscount_RETURN_CURRENT_WITHDRAW)
		{
			//退票处理，需要活期支取，支取金额=退票金额+罚息
			resInfo.setTransAccountID(info.getNDepositAccountID());
			resInfo.setAmount(info.getMReturnedAmount() + info.getMOverDueInterest());
			//账户金额查询区分金额类型增加
				resInfo.setAmountType(SETTConstant.AmountType.AmountType_11);
			//

		}
		else if (type == RepaymentDiscount_DEPOSIT_CURRENT_WITHDRAW)
		{
			//存款来源来自活期账户时，需要活期支取，支取金额=交易金额
			resInfo.setTransAccountID(info.getNCurrentAccountID());
			resInfo.setAmount(info.getMAmount());
			//账户金额查询区分金额类型增加
				resInfo.setAmountType(SETTConstant.AmountType.AmountType_1);
			//
		}
		else
		{ 
			//贴现回收
			resInfo.setTransAccountID(info.getNDiscountAccountID());
			resInfo.setTransSubAccountID(info.getNSubAccountID());
			//resInfo.setOppSubAccountID(info.getNDepositAccountID());
			if (info.getNIsReturned() == 1)
			{
				//回收退票金额
				resInfo.setAmount(info.getMDiscountAmount());
				resInfo.setOppSubAccountID(info.getNDepositAccountID());
			}
			else
			{
				//回收票面金额
				resInfo.setAmount(info.getMAmount());
				resInfo.setOppSubAccountID(info.getNCurrentAccountID());
			}
			//账户金额查询区分金额类型增加
				resInfo.setAmountType(SETTConstant.AmountType.AmountType_1);
			//
		}
		//resInfo.setTransDirection()
		resInfo.setTransNo(info.getSTransNo());
		//resInfo.setTransSubAccountID()
		//resInfo.set
		log.debug(UtilOperation.dataentityToString(resInfo));
		return resInfo;
	}
	static final private int accoutType_PAY = 1;
	static final private int accoutType_RECEIVE = 2;
	private TransAccountDetailInfo transferTransSpecialOperationInfoToTransAccountDetailInfo(TransSpecialOperationInfo info, int accoutType)
	{
		TransAccountDetailInfo resInfo = new TransAccountDetailInfo();
		resInfo.setAbstractStr(info.getSabstract());
		if (accoutType == accoutType_PAY)
		{
			resInfo.setAmount(info.getMpayamount());
			resInfo.setFixedDepositNo(info.getSpayfixeddepositno());
			resInfo.setTransAccountID(info.getNpayaccountid());
			resInfo.setTransSubAccountID(info.getNpaysubaccountid());
			resInfo.setOppAccountID(info.getNreceiveaccountid());
			resInfo.setLoanNoteID(info.getNpayloannoteid());
		}
		else
		{
			resInfo.setAmount(info.getMreceiveamount());
			resInfo.setFixedDepositNo(info.getSreceivefixeddepositno());
			resInfo.setTransAccountID(info.getNreceiveaccountid());
			resInfo.setTransSubAccountID(info.getNreceivesubaccountid());
			resInfo.setOppAccountID(info.getNpayaccountid());
			resInfo.setLoanNoteID(info.getNreceiveloannoteid());
		}
		//账户金额查询区分金额类型增加
			resInfo.setAmountType(SETTConstant.AmountType.AmountType_1);
		//

		resInfo.setBankChequeNo(info.getSbankchequeno());
		resInfo.setBillNo(info.getSbillno());
		resInfo.setBillTypeID(info.getNbilltypeid());
		resInfo.setCurrencyID(info.getNcurrencyid());
		resInfo.setDtExecute(info.getDtexecute());
		resInfo.setDtInterestStart(info.getDtintereststart());
		resInfo.setTransactionTypeID(info.getNtransactiontypeid());
		resInfo.setTransNo(info.getStransno());
		//resInfo.setGroup(info.getng)
		resInfo.setOfficeID(info.getNofficeid());
		resInfo.setStatusID(info.getNstatusid());
		resInfo.setAbstractID(info.getNabstractid());
		log.debug(UtilOperation.dataentityToString(resInfo));
		return resInfo;
	}
	private static final int OnePayMultiReceive_WITHDRAW = 0;
	private static final int OnePayMultiReceive_DEPOSIT = 1;
	private TransAccountDetailInfo transferTransTransOnePayMultiReceiveInfoToTransAccountDetailInfo(TransOnePayMultiReceiveInfo info)
	{
		TransAccountDetailInfo tadi = null;
		tadi = new TransAccountDetailInfo();
		tadi.setAbstractStr(info.getAbstract());
		tadi.setAmount(info.getAmount());
		//tadi.setBankChequeNo(info.get);
		//tadi.setBillTypeID(info.getBillTypeID());
		//tadi.setBillNo(info.getBillNo());
		tadi.setCurrencyID(info.getCurrencyID());
		tadi.setDtExecute(info.getExecuteDate());
		tadi.setDtInterestStart(info.getInterestStartDate());
		tadi.setOfficeID(info.getOfficeID());
		tadi.setTransNo(info.getEmptyTransNo());
		tadi.setTransactionTypeID(info.getTransactionTypeID());
		tadi.setStatusID(info.getStatusID());
		tadi.setGroup(-1);
		tadi.setTransAccountID(info.getAccountID());
		tadi.setAbstractID(info.getAbstractID());
		tadi.setSerialNo(info.getSerialNo());
		//账户金额查询区分金额类型增加
			tadi.setAmountType(SETTConstant.AmountType.AmountType_1);
		//

		return tadi;
	}
	private static final int TransGeneralLedger_WITHDRAW = 0;
	private static final int TransGeneralLedger_DEPOSIT = 1;
	private TransAccountDetailInfo transferTransGeneralLedgerInfoToTransAccountDetailInfo(TransGeneralLedgerInfo info)
	{
		TransAccountDetailInfo tadi = null;
		tadi = new TransAccountDetailInfo();
		tadi.setAbstractStr(info.getAbstract());
		tadi.setAmount(info.getAmount());
		//tadi.setBankChequeNo(info.get);
		//tadi.setBillTypeID(info.getBillTypeID());
		//tadi.setBillNo(info.getBillNo());
		tadi.setCurrencyID(info.getCurrencyID());
		tadi.setDtExecute(info.getExecuteDate());
		tadi.setDtInterestStart(info.getInterestStartDate());
		tadi.setOfficeID(info.getOfficeID());
		tadi.setTransNo(info.getTransNo());
		tadi.setTransactionTypeID(info.getTransActionTypeID());
		tadi.setStatusID(info.getStatusID());
		tadi.setGroup(-1);
		tadi.setTransAccountID(info.getAccountID());
		tadi.setAbstractID(info.getAbstractID());
		//账户金额查询区分金额类型增加
			tadi.setAmountType(SETTConstant.AmountType.AmountType_1);
		//

		log.debug(UtilOperation.dataentityToString(tadi));
		return tadi;
	}
	private TransAccountDetailInfo transferTransFeeInfoToTransAccountDetailInfo(TransFeeInfo info)
	{
		TransAccountDetailInfo tadi = null;
		tadi = new TransAccountDetailInfo();
		tadi.setAbstractStr(info.getAbstract());
		tadi.setAmount(info.getAmount());
		//tadi.setBankChequeNo(info.get);
		//tadi.setBillTypeID(info.getBillTypeID());
		//tadi.setBillNo(info.getBillNo());
		tadi.setCurrencyID(info.getCurrencyID());
		tadi.setDtExecute(info.getExecuteDate());
		tadi.setDtInterestStart(info.getInterestStartDate());
		tadi.setOfficeID(info.getOfficeID());
		tadi.setTransNo(info.getTransNo());
		tadi.setTransactionTypeID(info.getTransactionTypeID());
		tadi.setStatusID(info.getStatusID());
		tadi.setGroup(-1);
		tadi.setTransAccountID(info.getAccountID());
		tadi.setAbstractID(info.getAbstractID());
		log.debug(UtilOperation.dataentityToString(tadi));
		
		//为账户对账单信息查询 所加 
		if(info.getRelatedAccountID()>0)
		{
			Sett_AccountDAO sett_accountDao = new Sett_AccountDAO();
			try {
				AccountInfo accountInfo = sett_accountDao.findByID(info.getRelatedAccountID());
				tadi.setOppAccountNo(accountInfo.getAccountNo());
				tadi.setOppAccountName(accountInfo.getAccountName());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//
		//账户金额查询区分金额类型增加
				tadi.setAmountType(SETTConstant.AmountType.AmountType_1);
		//

		return tadi;
	}
	private String findGeneralLedgerSubjectCode(long generalLedgerID) throws IRollbackException
	{
		Sett_GeneralLedgerDAO glDAO = new Sett_GeneralLedgerDAO();
		String subjectCode;
		try
		{
			subjectCode = glDAO.findSubjectCodeByID(generalLedgerID);
		}
		catch (SQLException e)
		{
			throw new IRollbackException(ctx, e.getMessage(), e);
		}
		if (subjectCode == null || subjectCode.compareToIgnoreCase("") == 0)
			throw new IRollbackException(ctx, "无法查询到总账类ID" + generalLedgerID + "对应的总账类科目,交易失败");
		return subjectCode;
	}
	public void interbranchSettlementReverse() throws RemoteException, IRollbackException
	{
	}
	
	/**
	 * 判断传入的银行账户ID对应的银行是否需要生成银行指令，
	 * 调用此方法后，传入的bankID的值变为银行类型ID
	 * @return 需要返回true, 否则返回false
	 * @throws Exception
	 */
	public boolean isCreateInstruction(long bankID) throws Exception
	{/*
		
		boolean bCreateInstruction = false;		
		
		if(bankID == -1) {
			log.error("传入的银行ID参数错误！");
			throw new Exception("传入的银行ID参数错误！");
		}
		
		try {
			ArrayList list = new ArrayList(8);
			list = Config.getArray(Config.INTEGRATION_SERVICE_TOSENDBANKINSTRUCTION_BANKTYPE, new ArrayList(8));
			long[] bankType = new long[list.size()];
			
			//根据付款方BankID，获得付款方对应的开户行信息
			BranchInfo branchInfo = new BranchInfo();
			Sett_BranchDAO branchDAO = new Sett_BranchDAO();			
			branchInfo = branchDAO.findByID(bankID);			
			
			log.debug("交易传入的银行类型：" + branchInfo.getBankTypeID());			
			
			for(int i=0;i<list.size();i++) {
				bankType[i] = Long.parseLong((String)list.get(i));
				if(branchInfo.getBankTypeID() == bankType[i]) {
					bCreateInstruction = true;
					log.debug("配置文件中需要生成指令的银行："+bankType[i]);
					break;
				}			
			}
			
			bankID = branchInfo.getBankTypeID();
			
		} catch (NumberFormatException e) {
			log.error("判断银行ID是否生成银行指令时出错！");
			e.printStackTrace();
		}
		
		return bCreateInstruction;
	*/
	    return true;
	}
	
	/*
	 * 融资租赁收款交易--保存 @param TransReceiveFinanceInfo @Exception RemoteException, IRollbackException
	 */
	public void saveReceiveFinance(TransReceiveFinanceInfo info) throws RemoteException, IRollbackException
	{
		long lReturn = 1;
		//账户接口操作类
		if (info.getPayBailAccountID() > 0) 
		{
			log.info("付款方活期账户ID:" + info.getPayBailAccountID());

			//修改 by kenny(胡志强) (2007-03-19) 处理保存时校验账户状态的操作
			//如果该交易有收/付款方，且收/付款方为内部账户，则校验收/付款方账户状态
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(info.getPayBailAccountID(),AccountBean.TRANSTYPE_WITHDRAW);
			} catch (IException e) {
				e.printStackTrace();
				// 提示“系统忙”
				//modified by mzh_fu 2007/06/01 提示准确错误信息
		           // throw new IRollbackException(null,"Gen_E001");
					throw new IRollbackException(ctx,e);
			}
		}
		if (info.getPayPoundageAccountID() > 0) 
		{
			log.info("付手续费账户ID:" + info.getPayPoundageAccountID());

			//修改 by kenny(胡志强) (2007-03-19) 处理保存时校验账户状态的操作
			//如果该交易有收/付款方，且收/付款方为内部账户，则校验收/付款方账户状态
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(info.getPayPoundageAccountID(),AccountBean.TRANSTYPE_WITHDRAW);
			} catch (IException e) {
				e.printStackTrace();
				// 提示“系统忙”
				//modified by mzh_fu 2007/06/01 提示准确错误信息
		           // throw new IRollbackException(null,"Gen_E001");
					throw new IRollbackException(ctx,e);
			}
		}

		//若收款业务保证金的存款来源为活期账户
		if (info.getPayBailAccountID() > 0)     
		{
			log.debug("---------        (融资租赁收款：收保证金   保存操作）-----------");
			if(info.getBailAmount()>0){
				accountOperation.addCurrentUncheckAmount(info.getPayBailAccountID(),info.getReceviceBailAccountID(), info.getBailAmount());
			}
			log.debug("---------累计未复核金额完成-----------");
		}
		
		//若收款业务手续费存款来源为活期账户			
		if (info.getPayPoundageAccountID() > 0)
		{
			log.debug("---------        (融资租赁收款：收手续费    保存操作）-----------");
			accountOperation.addCurrentUncheckAmount(info.getPayPoundageAccountID(), -1,info.getPoundageAmount());
			log.debug("---------累计未复核金额完成-----------");
		}
		
		log.debug("---------完成saveReceiveFinance-----------");
	}
	
	/*
	 * 融资租赁收款交易--删除 @param TransReceiveFinanceInfo @Exception RemoteException, IRollbackException
	 */
	public void deleteReceiveFinance(TransReceiveFinanceInfo info) throws RemoteException, IRollbackException
	{
		//若收款业务保证金的存款来源为活期账户			即nPayBailAccountID>0，则：取消累计未复核金额
		if (info.getPayBailAccountID() > 0)
		{
			log.debug("---------        (融资租赁收款：收保证金   保存操作）-----------");
			log.debug("---------活期子账户大于0,存款来源为活期存款，开始扣除累计未复核金额-----------");
			accountOperation.subtractCurrentUncheckAmount(info.getPayBailAccountID(),info.getBailAmount());
			log.debug("---------结束扣除累计未复核金额-----------");
			
		}
		//若收款业务手续费存款来源为活期账户			即nPayPoundageAccountID>0，则：取消累计未复核金额
		if (info.getPayPoundageAccountID() > 0)
		{
			log.debug("---------        (融资租赁收款：收手续费   保存操作）-----------");
			log.debug("---------活期子账户大于0,存款来源为活期存款，开始扣除累计未复核金额-----------");
			accountOperation.subtractCurrentUncheckAmount(info.getPayPoundageAccountID(),info.getPoundageAmount());
			log.debug("---------结束扣除累计未复核金额-----------");
			
		}
		log.debug("---------deleteReceiveFinance-----------");
	}
	
	
//	融资租赁收款账户明细生成
	private TransAccountDetailInfo transferTransReceiveFinanceInfoToTransAccountDetailInfo(TransReceiveFinanceInfo info, int transType)
	{
		TransAccountDetailInfo resInfo = new TransAccountDetailInfo();
		//resInfo.setId(id);
		resInfo.setOfficeID(info.getOfficeID());		//办事处
		resInfo.setCurrencyID(info.getCurrencyID());	//币种
		resInfo.setTransactionTypeID(info.getTransactionTypeID());	//交易类型
		resInfo.setDtExecute(info.getExecuteDate());	//执行日
		resInfo.setTransNo(info.getTransNo());			//交易号
		resInfo.setAmount(info.getBailAmount());			//收保证金金额
		//交易方向在账户中设置
		//resInfo.setTransDirection();
		resInfo.setDtInterestStart(info.getInterestStartDate());	//保证金起息日
		resInfo.setAbstractStr(info.getAbstract());		//摘要
		//resInfo.setStatusID();
		//resInfo.setBillNo(info.getBillNo());  
		//resInfo.setBillTypeID(info.getBillTypeID());
		//resInfo.setFixedDepositNo(info.getDepositNo());	保证金存单号没有
		resInfo.setAbstractID(info.getAbstractID());
		//resInfo.setBankChequeNo();
		//resInfo.setDtExecute();
		//resInfo.setGroup();
		if (transType == TRANS_CURRENT)
		{
			//活期交易，交易账户为活期账户
			resInfo.setTransAccountID(info.getPayBailAccountID());			//从此账户扣钱
			//是否不用设子账户？？
			//resInfo.setTransSubAccountID();
			//活期交易，对方交易账户为定期账户
			resInfo.setOppAccountID(info.getReceviceBailAccountID());
			//是否不用设子账户？？
			//为账户对账单信息查询 所加
			if(info.getReceviceBailAccountID()>0)
			{
				Sett_AccountDAO sett_accountDao = new Sett_AccountDAO();
				try {
					AccountInfo accountInfo = sett_accountDao.findByID(info.getReceviceBailAccountID());
					resInfo.setOppAccountNo(accountInfo.getAccountNo());
					resInfo.setOppAccountName(accountInfo.getAccountName());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//
			//账户金额查询区分金额类型增加
				resInfo.setAmountType(SETTConstant.AmountType.AmountType_1);
			//

		}
		else if(transType == TRANS_CURRENT_COMMISSION)
		{
			//活期交易，交易账户为手续费活期账户
			resInfo.setTransAccountID(info.getPayPoundageAccountID());		//从此账户扣钱
			//是否不用设子账户？？
			//resInfo.setTransSubAccountID();
			//活期交易，对方交易账户为保证金存款账户
			//resInfo.setOppAccountID(info.getAccountID());
			//是否不用设子账户？？
			
			//设置手续费金额
			resInfo.setAmount(info.getPoundageAmount());
			//为账户对账单信息查询 所加
			if(info.getReceviceBailAccountID()>0)
			{
				Sett_AccountDAO sett_accountDao = new Sett_AccountDAO();
				try {
					AccountInfo accountInfo = sett_accountDao.findByID(info.getReceviceBailAccountID());
					resInfo.setOppAccountNo(accountInfo.getAccountNo());
					resInfo.setOppAccountName(accountInfo.getAccountName());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//
			//账户金额查询区分金额类型增加
				resInfo.setAmountType(SETTConstant.AmountType.AmountType_9);
			//
		}
		else if (transType == TRANS_FIXED)			//在往融资租赁收款的账户ID存入钱的时候，给个交易的ID
		{
			//定期交易，交易账户为定期账户
			resInfo.setTransAccountID(info.getReceviceBailAccountID());		//指往这个账户里存钱	
			//是否不用设子账户？？
			//resInfo.setTransSubAccountID();
			//定期交易，对方交易账户为活期账户??
			//resInfo.setOppAccountID(info.getCurrentAccountID());
			//是否不用设子账户？？
			//resInfo.setOppSubAccountID();
			//为账户对账单信息查询 所加
			if(info.getPayBailAccountID()>0)
			{
				Sett_AccountDAO sett_accountDao = new Sett_AccountDAO();
				try {
					AccountInfo accountInfo = sett_accountDao.findByID(info.getPayBailAccountID());
					resInfo.setOppAccountNo(accountInfo.getAccountNo());
					resInfo.setOppAccountName(accountInfo.getAccountName());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(info.getPayBailBankID()>0)
			{
				Sett_BranchDAO sett_branchDao = new Sett_BranchDAO();
				try {
					BranchInfo branchInfo = sett_branchDao.findByID(info.getPayBailBankID());
					resInfo.setOppAccountNo(branchInfo.getBankAccountCode());
					resInfo.setOppAccountName(branchInfo.getBranchName());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//
			//账户金额查询区分金额类型增加
				resInfo.setAmountType(SETTConstant.AmountType.AmountType_1);
			//
		}
		log.debug(UtilOperation.dataentityToString(resInfo));
		return resInfo;
	}
	//融资租赁收款子账户生成
	private SubAccountReceiveFinanceInfo transferTransReceiveFinanceInfoToSubAccountLoanInfo(TransReceiveFinanceInfo info)
	{
		SubAccountReceiveFinanceInfo resInfo = new SubAccountReceiveFinanceInfo();
		resInfo.setAccountID(info.getReceviceBailAccountID());		//保证金账户ID
		//resInfo.setArrearageInterest(info.get)
		resInfo.setBalance(0.0);			//余额刚开始为0.0
		resInfo.setOpenAmount(info.getBailAmount());	//保证金金额
		resInfo.setCalculateInterestDate(info.getInterestStartDate());	//保证金起息日
		resInfo.setClearCommissionDate(info.getInterestStartDate());	//保证金起息日
		resInfo.setClearCompoundDate(info.getInterestStartDate());		//保证金起息日
		resInfo.setClearInterestDate(info.getInterestStartDate());		//保证金起息日
		resInfo.setClearInterestTaxDate(info.getInterestStartDate());	//保证金起息日
		resInfo.setClearOverDueDate(info.getInterestStartDate());		//保证金起息日
		resInfo.setClearSureFeeDate(info.getInterestStartDate());		//保证金起息日
		//resInfo.setCommission();
		//resInfo.setCompoundAccountID()
		//resInfo.setCompoundInterest(compoundInterest)
		//resInfo.setDailyUncheckAmount(dailyUncheckAmount)
		//resInfo.setEffectiveTaxDate(info.getInterestTaxRateVauleDate());   feiye  利息税
		//resInfo.setFinishDate(info.get);
		//resInfo.setInterest(interest)
		//resInfo.setInterestTax(info.getInterestTaxRate());
		//resInfo.setInterestTaxAccountID(info.geti)
		//resInfo.setInterestTaxRate(info.getInterestTaxRate());
		//@TBD is cyc loan
		//resInfo.setIsCycLoan(info.getis)
		//在账户开立中设置
		//resInfo.setIsInterest(false);
		resInfo.setLoanNoteID(info.getReceiveFormID());		//feiye  收款单号
		//resInfo.setOpenAmount();
		resInfo.setOpenDate(info.getExecuteDate());		//执行日
		//resInfo.setOverDueAccountID(info.get)
		//resInfo.setOverDueInterest()
		resInfo.setCommissionAccountID(info.getPayPoundageAccountID());		//付手续费账户ID
		//resInfo.setPaySuretyAccountID(info.getPaySuretyFeeAccountID());   //担保费账户没有  自营才有担保费，委贷有手续费
		resInfo.setPreDrawDate(info.getInterestStartDate());			//保证金起息日
		//resInfo.setPreDrawInterest(info.getpr)
		
		//resInfo.setPayInterestAccountID(info.getPayInterestAccountID());		//在新增账户时有一个收息账户  暂时关掉
		//resInfo.setReceiveInterestAccountID(info.getReceiveInterestAccountID());	//在新增账户时有一个收息账户  暂时关掉
		//resInfo.setReceiveSuretyAccountID(info.getReceiveSuretyFeeAccountID());	//在新增账户时有一个收息账户  暂时关掉
		//resInfo.setPaySuretyAccountID(info.getPaySuretyFeeAccountID());			//在新增账户时有一个收息账户  暂时关掉
		resInfo.setStatusID(SETTConstant.SubAccountStatus.NORMAL);		//默认子账户的状态为未结清
		//resInfo.setConsignAccountID(info.getConsignDepositAccountID());			//关掉它
		//resInfo.setIsInterest(1);		//关掉它
		resInfo.setIsInterest(info.getIsBailInterest());		//保证金是否计息
				
		//resInfo.setSuretyFee()
		//resInfo.setInterestTaxRate(info.getInterestTaxRate());		//利息税费率  关掉它
		//resInfo.setInterestTaxRatePlanID(info.getInterestTaxPlanId());
		return resInfo;
	}
	
	/**
	 * 融资租赁收款交易--复核 @param TransReceiveFinanceInfo @Exception RemoteException, IRollbackException
	 */
	public void checkReceiveFinance(TransReceiveFinanceInfo transInfo) throws RemoteException, IRollbackException
	{
		long currentSubAccountID = -1;		//付保证金的活期子账户ID
		long commissionCurrentSubAccountID = -1;	//付手续费的活期子账户ID
		long marginSubAccountID = -1;				//保证金子账户ID(融资租赁保证金账户)
		TransAccountDetailInfo currentTadi = null;		//活期账户明细
		TransAccountDetailInfo fixedTadi = null;		//定期账户明细
		Sett_SubAccountDAO  sett_SubAccountDAO = new Sett_SubAccountDAO();
		log.debug("---------checkReceiveFinance-----------");
		//若存款来源为活期存款,返回值=活期子账户ID
		System.out.println("--------transInfo.getPayBailAccountID():"+transInfo.getPayBailAccountID());
		if (transInfo.getPayBailAccountID() > 0)
		{
			log.debug("----保证金-----活期子账户大于0,存款来源为活期存款，开始从活期账户余额中扣钱：withdrawCurrent-----------");
			if(transInfo.getBailAmount()>0){
				currentTadi = transferTransReceiveFinanceInfoToTransAccountDetailInfo(transInfo, TRANS_CURRENT);
				currentSubAccountID = accountOperation.withdrawCurrent(currentTadi);
			}
			log.debug("---------结束从活期账户余额中扣钱：withdrawCurrent-----------");
		}
		System.out.println("--------付保证金的活期子账户是currentSubAccountID："+currentSubAccountID);
		System.out.println("--------transInfo.getPayPoundageAccountID():"+transInfo.getPayPoundageAccountID());
		if (transInfo.getPayPoundageAccountID() > 0)
		{
			log.debug("----手续费-----活期子账户大于0,存款来源为活期存款，开始从活期账户余额中扣钱：withdrawCurrent-----------");
			//currentTadi = transferTransMarginOpenInfoToTransAccountDetailInfo(transInfo, TRANS_CURRENT_COMMISSION);
			currentTadi = transferTransReceiveFinanceInfoToTransAccountDetailInfo(transInfo, TRANS_CURRENT_COMMISSION);
			commissionCurrentSubAccountID = accountOperation.withdrawCurrent(currentTadi);
			log.debug("---------结束从活期账户余额中扣钱：withdrawCurrent-----------");
		}
		System.out.println("--------付手续费的活期子账户是commissionCurrentSubAccountID："+commissionCurrentSubAccountID);
		
		//保证金开空户（保证金信息），返回值=保证金子账户ID
		log.debug("---------开始开立保证金子账户-----------");
		log.debug(UtilOperation.dataentityToString(transInfo));
		//SubAccountMarginInfo safi = this.transferTransMarginOpenInfoToSubAccountMargindInfo(transInfo);
		SubAccountReceiveFinanceInfo safi = this.transferTransReceiveFinanceInfoToSubAccountLoanInfo(transInfo);
		UtilOperation.dataentityToString(safi);
		//marginSubAccountID = accountOperation.openFixSubAccount(safi);
		marginSubAccountID = accountOperation.openLoanSubAccount(safi);
		log.debug("---------新开立的保证金子账户ID: " + marginSubAccountID + "---------");
		log.debug("---------结束开立保证金子账户-----------");
		
		//保证金存入（保证金账户ID，保证金子账户ID，对方账户=CurrentAccountID）
		log.debug("---------开始开立保证金存入depositFix-----------");
		//fixedTadi = transferTransMarginOpenInfoToTransAccountDetailInfo(transInfo, TRANS_FIXED);
		fixedTadi = transferTransReceiveFinanceInfoToTransAccountDetailInfo(transInfo, TRANS_FIXED);
		fixedTadi.setTransSubAccountID(marginSubAccountID);
		long newOpenSubAccountId = -1;  //新开立的保证金子账户ID
		newOpenSubAccountId = accountOperation.depositFix(fixedTadi);
		
		log.debug("---------新开立的保证金子账户ID = "+newOpenSubAccountId);
		
		try {
			log.debug("---------更新收款利率 : "+transInfo.getRate()+"  到保证金子账户: "+newOpenSubAccountId);
			sett_SubAccountDAO.updateRate(newOpenSubAccountId,transInfo.getRate());
			
			log.debug("---------更新收息活期账户ID : "+transInfo.getInterestAccountID()+"  到保证金子账户: "+newOpenSubAccountId);
			sett_SubAccountDAO.updateInterestAccountIDoSubMargin(newOpenSubAccountId,transInfo.getInterestAccountID());
		}
		catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
		
		
		
		
		//由于融资租赁保证金在开立子账户后要计息，而数据记录在AL中，此时要把利息一值代入到AF中
		log.debug("---------结束开立保证金存入depositFix-----------");
		//通存通兑处理InterbranchSettlement()
		//@TBD
		/**
		 * 产生会计分录:分录类型lEntryType =0 无关，lAccountID1=收款方账户，lAccountID2=付款方账户， dAmount1=发生额
		 */

		log.debug("-------开始产生会计分录----------");
		GenerateGLEntryParam param = new GenerateGLEntryParam();
		/**
		 * 如果收/付款银行账户ID>0，则本金 流向lPrincipalType =2银行，否则本金流向lPrincipalType =1 内部转账
		 */
		long lPrincipalType = -1;	//本金流向			1:内部转账   2:银行
		long lCommissionType = -1;	//手续费流向			1:内部转账   2:银行
		if (transInfo.getPayBailBankID() > 0)
		{
			//本金流向是 银行
			lPrincipalType = SETTConstant.CapitalType.BANK;
		}
		else
		{
			//本金流向是 内部转账
			lPrincipalType = SETTConstant.CapitalType.INTERNAL;
		}
		
		if (transInfo.getPayPoundageAccountID() > 0)
		{
			lCommissionType = SETTConstant.CapitalType.INTERNAL;
		}
		else
		{
			lCommissionType = SETTConstant.CapitalType.BANK;
		}
		System.out.println("......dddddddddddddddddddddddd............  lCommissionType = "+lCommissionType);
		
		//分录类型
		//long lEntryType = SETTConstant.EntryType.IRRESPECTIVE;  //设置成无关 在页面上可以设置成无关和 
		long lEntryType = SETTConstant.EntryType.DIVIDE;	//分拆  2006.5.12 add by feiye
		
		//收款方账户(保证金)
		long receiveAccountID = marginSubAccountID;
		System.out.println(".................. 收款方账户(保证金) : "+receiveAccountID);
		
		//付款方账户（保证金）
		long payAccountID = currentSubAccountID;
		System.out.println(".................. 付款方账户(保证金) : "+payAccountID);
		
		//付手续费账户ID
		long payCommissionAccountID = commissionCurrentSubAccountID;
		System.out.println(".................. 付手续费账户 : "+payCommissionAccountID);
		
		//保证金金额
		double dAmount = transInfo.getBailAmount();
		System.out.println(".................. 保证金金额 : "+dAmount);
		
		//手续费
		double dCommissionAmount = transInfo.getPoundageAmount();
		System.out.println(".................. 手续费 : "+dCommissionAmount);
		
		param.setOfficeID(transInfo.getOfficeID());			//办事处
		param.setCurrencyID(transInfo.getCurrencyID());		//币种
		param.setTransactionTypeID(transInfo.getTransactionTypeID());		//交易类型
		param.setExecuteDate(transInfo.getExecuteDate());	//执行日
		param.setInterestStartDate(transInfo.getInterestStartDate());		//起息日
		param.setTransNo(transInfo.getTransNo());		//交易号
		param.setAbstractStr(transInfo.getAbstract());	//摘要
		param.setInputUserID(transInfo.getInputUserID());	//输入人
		param.setCheckUserID(transInfo.getCheckUserID());	//复核人
		
		param.setPrincipalType(lPrincipalType);	//本金流向
		param.setCommisionType(lCommissionType);//手续费流向
		
		param.setEntryType(lEntryType);					//分录类型
		
		param.setReceiveAccountID(receiveAccountID);
		param.setPayAccountID(payAccountID);
		
		param.setPayCommissionAccountID(payCommissionAccountID);		//传手续费账户ID
		param.setReceiveFinanceAccountID(receiveAccountID);//收保证金账户
		param.setPayFinanceAccountID(payAccountID); //付保证金账户
		//param.setFeeBankID(transInfo.getCommissionBankID());		  
		param.setFeeBankID(transInfo.getPayPoundageBankID());		//费用开户行
		param.setPrincipalBankID(transInfo.getPayBailBankID());		//本金开户行

		param.setPrincipalOrTransAmount(dAmount);		//本金/交易金额
	
		param.setCommissionFee(dCommissionAmount);		//手续费金额
		
		boolean res = glopOperation.generateGLEntry(param);
		if (!res)
		{
			throw new IRollbackException(ctx, "借贷不平衡，交易失败");
		}
		log.debug("-------结束产生会计分录----------");
		
		/***********构造银行付款指令**********/
		

	}	
	
	/**
	 * 融资租赁收款交易--取消复核 @param TransReceiveFinanceInfo @Exception RemoteException, IRollbackException
	 */
	public void cancelCheckReceiveFinance(TransReceiveFinanceInfo info) throws RemoteException, IRollbackException
	{
		long currentSubAccountID = -1;		//
		long fixedSubAccountID = -1;
		TransAccountDetailInfo currentTadi = null;
		TransAccountDetailInfo fixedTadi = null;
		log.debug("---------开始cancelCheckOpenMarginDeposit-----------");
		//若存款来源为活期存款,反交易
		
		if (info.getPayBailAccountID() > 0)
		{
			log.debug("---------活期子账户大于0,存款来源为活期存款，开始活期账户交易：cancelWithdrawCurrent-----------");
			//currentTadi = transferTransMarginOpenInfoToTransAccountDetailInfo(info, TRANS_CURRENT);
			currentTadi = transferTransReceiveFinanceInfoToTransAccountDetailInfo(info, TRANS_CURRENT);
			accountOperation.cancelWithdrawCurrent(currentTadi);
			log.debug("---------结束活期账户反交易：cancelWithdrawCurrent-----------");
		}
		
		if (info.getPayPoundageAccountID() > 0)
		{
			log.debug("---------活期子账户大于0,存款来源为活期存款，开始活期账户交易：cancelWithdrawCurrent-----------");
			//currentTadi = transferTransMarginOpenInfoToTransAccountDetailInfo(info, TRANS_CURRENT);
			//currentTadi = transferTransReceiveFinanceInfoToTransAccountDetailInfo(info, TRANS_CURRENT);
			currentTadi = transferTransReceiveFinanceInfoToTransAccountDetailInfo(info, TRANS_CURRENT_COMMISSION);
			accountOperation.cancelWithdrawCurrent(currentTadi);
			log.debug("---------结束活期账户反交易：cancelWithdrawCurrent-----------");
		}
	
		//保证金存入反交易
		log.debug("---------开始保证金存入反交易：cancelGrantLoan-----------");
		//fixedTadi = transferTransMarginOpenInfoToTransAccountDetailInfo(info, TRANS_FIXED);		方栓全的
		//fixedTadi = transferTransReceiveFinanceInfoToTransAccountDetailInfo(info, TRANS_FIXED);		叶飞定期一套 不成功
		//long cancelDepositSubFixedAccount = accountOperation.cancelDepositFix(fixedTadi);				叶飞定期一套 不成功
		
		TransAccountDetailInfo tadi = new TransAccountDetailInfo();
		tadi.setTransAccountID(info.getReceviceBailAccountID());	//保证金账户ID 复核时开立的即此
		tadi.setLoanNoteID(info.getReceiveFormID());		//放款通知单号
		tadi.setAmount(info.getBailAmount());				//保证金的金额
	
		long cancelDepositSubFixedAccount = accountOperation.cancelGrantLoan(tadi);
		log.debug("---------结束保证金存入反交易：cancelGrantLoan-----------");
		
		//保证金开空户反交易
		log.debug("---------开始保证金开立空户反交易：deleteFixSubAccount-----------");
		//accountOperation.deleteFixSubAccount(cancelDepositSubFixedAccount);
		accountOperation.deleteLoanSubAccount(cancelDepositSubFixedAccount);	
		log.debug("---------结束保证金开立空户反交易：deleteFixSubAccount-----------");
		//通存通兑处理InterbranchSettlement()
		//@TBD
		//删除明细账
		accountOperation.deleteTransAccountDetail(info.getTransNo());	//删除账户交易明细表

		glopOperation.deleteGLEntry(info.getTransNo());
	
		//删除会计分录表
		log.debug("---------结束cancelCheckOpenMarginDeposit-----------");
	}
	
	/*
	 * 融资租赁还款交易--保存 @param TransReturnFinanceInfo @Exception RemoteException, IRollbackException
	 */
	public void saveReturnFinance(TransReturnFinanceInfo info) throws RemoteException, IRollbackException
	{
		long lReturn = 1;
		//账户接口操作类
		
		double returnAmount=0.0;
		returnAmount=info.getCorpusAmount()+info.getInterestAmount()-info.getBailAmount();
		log.debug("---得到了本次还租金金额为："+info.getCorpusAmount());
		log.debug("---得到了本次还利息金额为："+info.getInterestAmount());
		log.debug("---得到了本次扣除保证金金额为："+info.getBailAmount());
		log.debug("---得到了本次还款金额为："+returnAmount);
		
		log.debug("---得到了本次还本金账户ID为："+info.getReturnBailAccountID());
		log.debug("---得到了本次还款通知单ID为："+info.getReturnFormID());
		log.debug("---得到了本次收款通知单ID为："+info.getReceiveFormID());
		log.debug("---得到了本次还本金账户ID："+info.getReturnCorpusAccountID());
		
		if (info.getReturnBailAccountID() > 0) 
		{
			log.info("付保证金账户ID:" + info.getReturnBailAccountID());

			//修改 by kenny(胡志强) (2007-03-19) 处理保存时校验账户状态的操作
			//如果该交易有收/付款方，且收/付款方为内部账户，则校验收/付款方账户状态
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(info.getReturnBailAccountID(),AccountBean.TRANSTYPE_WITHDRAW);
			} catch (IException e) {
				e.printStackTrace();
				// 提示“系统忙”
				//modified by mzh_fu 2007/06/01 提示准确错误信息
		           // throw new IRollbackException(null,"Gen_E001");
					throw new IRollbackException(ctx,e);
			}
		}
		
		if (info.getReturnCorpusAccountID() > 0) 
		{
			log.info("付款方活期账户ID:" + info.getReturnCorpusAccountID());

			//修改 by kenny(胡志强) (2007-03-19) 处理保存时校验账户状态的操作
			//如果该交易有收/付款方，且收/付款方为内部账户，则校验收/付款方账户状态
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(info.getReturnCorpusAccountID(),AccountBean.TRANSTYPE_WITHDRAW);
			} catch (IException e) {
				e.printStackTrace();
				// 提示“系统忙”
				//modified by mzh_fu 2007/06/01 提示准确错误信息
		           // throw new IRollbackException(null,"Gen_E001");
					throw new IRollbackException(ctx,e);
			}
		}

		//若扣除保证金的由账户有效，则：累计未复核金额
		if (info.getReturnBailAccountID() > 0)     //还款时是保证金变少
		{
			log.debug("---------活期子账户大于0,存款来源为保证金存款，开始累计未复核金额(融)"+info.getBailAmount());
			//accountOperation.addCurrentUncheckAmount(info.getReturnBailAccountID(),-1, info.getBailAmount());
//			if(info.getBailAmount()>0){
//				accountOperation.addLoanUncheckAmount(info.getReturnBailAccountID(),info.getReceiveFormID(),info.getBailAmount());
//			}
			//modify by zwxiao 2010-08-05
			Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO();
			double mBalance = 0.00;
			double marginBalance = 0.00;
			double tempBalance = 0.00;
			String subaccount_operation = "";
			try {
				int j = -1;
				List subAccountList = sett_SubAccountDAO.getSubAccountByCOntractIDAndMargin(info.getContractID(),info.getReturnBailAccountID());
				for (int i = 0; i < subAccountList.size(); i++) {
					Sett_SubAccountInfo sett_SubAccountInfo = (Sett_SubAccountInfo)subAccountList.get(i);
					mBalance += sett_SubAccountInfo.getMBalance();
					if(mBalance >= info.getBailAmount()){
						j = i;
						break;
					}
				}
				if(j != subAccountList.size()-1){
					for (int k = j; k < subAccountList.size(); k++) {
						if(k>0){
							subAccountList.remove(k);
						}
					}
				}
				tempBalance = info.getBailAmount();
				long iIndex = 1;
				if(subAccountList.size() > 1){
					Iterator iterator = subAccountList.iterator();
					marginBalance = info.getBailAmount();
					double marginBalanceTemp = 0.00;
					while(iterator.hasNext()){
						Sett_SubAccountInfo accountInfo = (Sett_SubAccountInfo)iterator.next();
						marginBalanceTemp = accountInfo.getMBalance() - marginBalance;
						if(marginBalanceTemp>=0){
							info.setReceiveFormID(accountInfo.getAl_NLoanNoteId());
							info.setBailAmount(marginBalance);
						}else{
							info.setReceiveFormID(accountInfo.getAl_NLoanNoteId());
							info.setBailAmount(accountInfo.getMBalance());
						}
						accountOperation.addLoanUncheckAmount(info.getReturnBailAccountID(),info.getReceiveFormID(),info.getBailAmount());
						marginBalance = Math.abs(marginBalanceTemp);
						if(iIndex == 1){
							subaccount_operation += info.getReceiveFormID()+"-"+String.valueOf(info.getBailAmount())+"-2";
						}else{
							subaccount_operation += "&"+info.getReceiveFormID()+"-"+String.valueOf(info.getBailAmount())+"-2";
						}
						iIndex++;
					}
				}else{
					Sett_SubAccountInfo sett_SubAccountInfo = (Sett_SubAccountInfo)subAccountList.get(0);
					accountOperation.addLoanUncheckAmount(info.getReturnBailAccountID(),sett_SubAccountInfo.getAl_NLoanNoteId(),info.getBailAmount());
					subaccount_operation += sett_SubAccountInfo.getAl_NLoanNoteId()+"-"+String.valueOf(info.getBailAmount())+"-2";
				}
				info.setBailAmount(tempBalance);
				
				TransReturnFinanceNewInfo returnFinanceNewInfo = new TransReturnFinanceNewInfo();
				returnFinanceNewInfo.setTransno(info.getTransNo());
				returnFinanceNewInfo.setStartDate(new Date());
				returnFinanceNewInfo.setEndDate(new Date());
				Sett_TransReturnFinanceDao dao=new Sett_TransReturnFinanceDao();
				dao.deleteQuantityRepaymentBalance(returnFinanceNewInfo.getTransno());
				dao.createHrefFind(returnFinanceNewInfo, subaccount_operation);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new IRollbackException(ctx,"Gen_E001");
			}
			log.debug("---------累计未复核金额完成-----------");
		}
		
		//若还款业务的存款来源扣除，则：累计未复核金额
		if (info.getReturnCorpusAccountID() > 0  && info.getReturnInterestAccountID() > 0)
		{
			log.debug("---------活期子账户大于0,存款来源为活期存款，开始累计未复核金额(融)"+returnAmount);
			accountOperation.addCurrentUncheckAmount(info.getReturnCorpusAccountID(),-1, returnAmount);
			log.debug("---------累计未复核金额完成-----------");
		}
		
		log.debug("---------完成saveReturnFinance-----------");
	}
	
	/*
	 * 融资租赁还款交易--删除 @param TransReturnFinanceInfo @Exception RemoteException, IRollbackException
	 */
	public void deleteReturnFinance(TransReturnFinanceInfo info) throws RemoteException, IRollbackException
	{
		double returnAmount=0.0;
		returnAmount=info.getCorpusAmount()+info.getInterestAmount()-info.getBailAmount();
		log.debug("---得到了本次还租金金额为："+info.getCorpusAmount());
		log.debug("---得到了本次还利息金额为："+info.getInterestAmount());
		log.debug("---得到了本次扣除保证金金额为："+info.getBailAmount());
		log.debug("---得到了本次还款金额为："+returnAmount);
		
		//若收款业务保证金的存款来源为活期账户			即nPayBailAccountID>0，则：取消累计未复核金额
		if (info.getReturnBailAccountID() > 0)
		{
			log.debug("---------        (融资租赁还款：冲减抵扣的融资租赁保证金   保存操作）-----------");
			log.debug("---------活期子账户大于0,存款来源为活期存款，开始扣除累计未复核金额"+info.getBailAmount());
			//accountOperation.subtractCurrentUncheckAmount(info.getReturnBailAccountID(),info.getBailAmount());
			
			//modify by zwxiao 2010-08-05
			Sett_TransReturnFinanceDao financeDao = new Sett_TransReturnFinanceDao();
			TransReturnFinanceNewInfo newInfo = null;
			try {
				newInfo = financeDao.getHrefFindListByTransNo(info.getTransNo());
				if(newInfo != null){
					String accountOperationDetail = newInfo.getAccountOperation();
					String[] tempOperationDetail = accountOperationDetail.split("&");
					long loanNoteID = -1;
					double amount = 0.00;
					if(info.getReturnBailAccountID() > 0 )
					{
						for(int i = 0;i<tempOperationDetail.length;i++){
							loanNoteID = Long.valueOf(tempOperationDetail[i].split("-")[0]).longValue();
							amount = Double.valueOf(tempOperationDetail[i].split("-")[1]).doubleValue();
							accountOperation.subtractLoanUncheckAmount(info.getReturnBailAccountID(),loanNoteID,amount);
						}
					}
					Sett_TransReturnFinanceDao dao=new Sett_TransReturnFinanceDao();
					dao.deleteQuantityRepaymentBalance(info.getTransNo());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new IRollbackException(ctx,"Gen_E001");
			}
			
			
			//取消息该子账户的   还需要知道收款通知单的ID  才可以进行删除操作		add by feiye 2006.5.24
			//accountOperation.subtractLoanUncheckAmount(info.getReturnBailAccountID(),info.getReceiveFormID(),info.getBailAmount());
			
			log.debug("---------结束扣除累计未复核金额-----------");
		}

		//若收款业务手续费存款来源为活期账户			即nPayPoundageAccountID>0，则：取消累计未复核金额
		if (info.getReturnCorpusAccountID() > 0 && info.getReturnInterestAccountID() > 0)
		{
			log.debug("---------        (融资租赁还款：(((累计从活期扣租金金额=租金+利息-上面扣的保证金)))   保存操作）-----------");
			log.debug("---------活期子账户大于0,存款来源为活期存款，开始扣除累计未复核金额:"+returnAmount);
			accountOperation.subtractCurrentUncheckAmount(info.getReturnCorpusAccountID(),returnAmount);
			log.debug("---------结束扣除累计未复核金额-----------");
		}
		
		log.debug("---------deleteReceiveFinance-----------");
	}
	
	//资租赁还款账户明细生成    针对生成的是本金及利息对应的活期的数据   另外还得有一个扣除保证金金额的数据
	private TransAccountDetailInfo transferTransReturnFinanceInfoToTransAccountDetailInfo(TransReturnFinanceInfo info, int transType)
	{
		double returnAmount=0.0;
		returnAmount=info.getCorpusAmount()+info.getInterestAmount()-info.getBailAmount();
		log.debug("---得到了本次还租金金额为："+info.getCorpusAmount());
		log.debug("---得到了本次还利息金额为："+info.getInterestAmount());
		log.debug("---得到了本次扣除保证金金额为："+info.getBailAmount());
		log.debug("---得到了本次还款金额为："+returnAmount);
		
		TransAccountDetailInfo resInfo = new TransAccountDetailInfo();
		//resInfo.setId(id);
		resInfo.setOfficeID(info.getOfficeID());		//办事处
		resInfo.setCurrencyID(info.getCurrencyID());	//币种
		resInfo.setTransactionTypeID(info.getTransactionTypeID());	//交易类型
		resInfo.setDtExecute(info.getExecuteDate());	//执行日
		resInfo.setTransNo(info.getTransNo());			//交易号
		//resInfo.setAmount(info.getBailAmount());			//收活期的金金额
		resInfo.setAmount(returnAmount);			//收活期的金金额		add bye feiye 2006.5.25
		//交易方向在账户中设置
		//resInfo.setTransDirection();
		resInfo.setDtInterestStart(info.getInterestStartDate());	//保证金起息日
		resInfo.setAbstractStr(info.getAbstract());		//摘要
		//resInfo.setStatusID();
		//resInfo.setBillNo(info.getBillNo());  
		//resInfo.setBillTypeID(info.getBillTypeID());
		//resInfo.setFixedDepositNo(info.getDepositNo());	保证金存单号没有
		resInfo.setAbstractID(info.getAbstractID());
		//resInfo.setBankChequeNo();
		//resInfo.setDtExecute();
		//resInfo.setGroup();
		if (transType == TRANS_CURRENT)
		{
			//活期交易，交易账户为活期账户
			resInfo.setTransAccountID(info.getReturnCorpusAccountID());			
			//是否不用设子账户？？
			//resInfo.setTransSubAccountID();
			//活期交易，对方交易账户为定期账户
			//resInfo.setOppAccountID(info.getReceviceBailAccountID());			
			//是否不用设子账户？？
			//账户金额查询区分金额类型增加
				resInfo.setAmountType(SETTConstant.AmountType.AmountType_11);
			//

		}
		else if (transType == TRANS_FIXED)			
		{
			//定期交易，交易账户为定期账户
			resInfo.setTransAccountID(info.getReturnBailAccountID());		
			resInfo.setLoanNoteID(info.getReceiveFormID());					
			resInfo.setAmount(info.getBailAmount());						
			//是否不用设子账户？？
			//resInfo.setTransSubAccountID();
			//定期交易，对方交易账户为活期账户??
			//resInfo.setOppAccountID(info.getCurrentAccountID());
			//是否不用设子账户？？
			//resInfo.setOppSubAccountID();
			//账户金额查询区分金额类型增加
				resInfo.setAmountType(SETTConstant.AmountType.AmountType_8);
			//
		}else if (transType == TRANS_DEPOSIT_MARGIN)	//add by zwxiao 新增对融资租赁还款的保证金的处理		
		{
			
			//交易账户为保证金账户
			resInfo.setTransAccountID(info.getReturnBailAccountID());
			resInfo.setLoanNoteID(info.getReceiveFormID());					
			resInfo.setAmount(info.getBailAmount());						
			//是否不用设子账户？？
			//resInfo.setTransSubAccountID();
			//定期交易，对方交易账户为活期账户??
			//resInfo.setOppAccountID(info.getCurrentAccountID());
			//是否不用设子账户？？
			//resInfo.setOppSubAccountID();
			//账户金额查询区分金额类型增加
				resInfo.setAmountType(SETTConstant.AmountType.AmountType_8);
			//
		}
		log.debug(UtilOperation.dataentityToString(resInfo));
		return resInfo;
	}
	
	/**
	 * 融资租赁还款交易--复核 @param TransReturnFinanceInfo @Exception RemoteException, IRollbackException
	 */
	public void checkReturnFinance(TransReturnFinanceInfo transInfo) throws RemoteException, IRollbackException
	{
		//融资租赁还款交易复核
		long currentSubAccountID = -1;		
		long financeSubAccountID = -1;				
		TransAccountDetailInfo currentTadi = null;		
		TransAccountDetailInfo financeTadi = null;		
		long lsubAccountID=-1;
		
		String subaccount_operation = "";
		log.debug("---------checkReturnFinance-----------");   
		
		for(int i=0;i<10;i++)
			log.debug("      开始复核还保证金的后台处理操作.     ");
		
		//在此做实际的金额的扣除处理   一个是贷款，一个是活期的扣钱
		if (transInfo.getReturnCorpusAccountID() > 0  && transInfo.getReturnInterestAccountID() > 0)	
		{
			log.debug("----本金及利息-----活期子账户大于0,存款来源为活期存款，开始从活期账户余额中扣钱：withdrawCurrent-----------");
			//currentTadi = transferTransMarginOpenInfoToTransAccountDetailInfo(transInfo, TRANS_CURRENT_COMMISSION);
			//currentTadi = transferTransReceiveFinanceInfoToTransAccountDetailInfo(transInfo, TRANS_CURRENT_COMMISSION);
			
			currentTadi = transferTransReturnFinanceInfoToTransAccountDetailInfo(transInfo, TRANS_CURRENT);
			
			currentSubAccountID = accountOperation.withdrawCurrent(currentTadi);		
			log.debug("---------结束从活期账户余额中扣钱：withdrawCurrent-----------");
		}
		System.out.println("--------付还款及利息扣除保证金后的金额的活期子账户是currentSubAccountID："+currentSubAccountID);
		
//		//若存款来源为活期存款,返回值=活期子账户ID
//		System.out.println("--------transInfo.getPayBailAccountID():"+transInfo.getReturnBailAccountID());
//		if (transInfo.getReturnBailAccountID() > 0  )     
//		{
//			//currentTadi = transferTransMarginOpenInfoToTransAccountDetailInfo(transInfo, TRANS_CURRENT);
//			//currentTadi = transferTransReceiveFinanceInfoToTransAccountDetailInfo(transInfo, TRANS_CURRENT);
//			//currentSubAccountID = accountOperation.withdrawCurrent(currentTadi);
//			
//			//for(int i=0;i<10;i++)
//				System.out.println("得到还保证金的金额为:"+transInfo.getBailAmount());
//			//取得合同对应的收款通知单ID，也就是子账户ID，以及金额之间的比较
//			//此处的扣款的逻辑是按照收款通知单的先后顺序进行扣款
//				
//				
//				
//			if(transInfo.getBailAmount()>0){
//				financeTadi = transferTransReturnFinanceInfoToTransAccountDetailInfo(transInfo, TRANS_FIXED);
//				financeSubAccountID=accountOperation.withdrawFinance(financeTadi);	
//			}
//			
//			log.debug("---------结束从-融资租赁保证金账户余额中扣钱：withdrawFinance-----------");
//		}
		
		//modify by zwxiao 2010-08-04 由于目前收款通知单能有多个，所以需要重写关于保证金处理的方法
		if (transInfo.getReturnBailAccountID() > 0  )     
		{
			System.out.println("得到还保证金的金额为:"+transInfo.getBailAmount());
			Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO();
			double mBalance = 0.00;
			double marginBalance = 0.00;
			double tempBalance = 0.00;
			try {
				int j = -1;
				List subAccountList = sett_SubAccountDAO.getSubAccountByCOntractIDAndMargin(transInfo.getContractID(),transInfo.getReturnBailAccountID());
				for (int i = 0; i < subAccountList.size(); i++) {
					Sett_SubAccountInfo info = (Sett_SubAccountInfo)subAccountList.get(i);
					mBalance += info.getMBalance();
					if(mBalance >= transInfo.getBailAmount()){
						j = i;
						break;
					}
				}
				if(j != subAccountList.size()-1){
					for (int k = j; k < subAccountList.size(); k++) {
						if(k>0){
							subAccountList.remove(k);
						}
					}
				}
				tempBalance = transInfo.getBailAmount();
				long iIndex = 1;
				if(subAccountList.size() > 1){
					Iterator iterator = subAccountList.iterator();
					marginBalance = transInfo.getBailAmount();
					double marginBalanceTemp = 0.00;
					while(iterator.hasNext()){
						Sett_SubAccountInfo accountInfo = (Sett_SubAccountInfo)iterator.next();
						marginBalanceTemp = accountInfo.getMBalance() - marginBalance;
						if(marginBalanceTemp>=0){
							transInfo.setReceiveFormID(accountInfo.getAl_NLoanNoteId());
							transInfo.setBailAmount(marginBalance);
						}else{
							transInfo.setReceiveFormID(accountInfo.getAl_NLoanNoteId());
							transInfo.setBailAmount(accountInfo.getMBalance());
						}
						financeTadi = transferTransReturnFinanceInfoToTransAccountDetailInfo(transInfo, TRANS_DEPOSIT_MARGIN);
						financeSubAccountID=accountOperation.withdrawFinance(financeTadi);	
						marginBalance = Math.abs(marginBalanceTemp);
						
						//added haoliang 2010-08-17 将子账户已结清状态修改为未结清
						long subAccountStatus = sett_SubAccountDAO.findStatusByID(financeSubAccountID);
						if(subAccountStatus > -1 && subAccountStatus == SETTConstant.SubAccountStatus.FINISH)
						{
							sett_SubAccountDAO.updateNoFinishDateAndStatus(financeSubAccountID,SETTConstant.SubAccountStatus.NORMAL);
						}
						//end
						
						if(iIndex == 1){
							subaccount_operation += transInfo.getReceiveFormID()+"-"+String.valueOf(transInfo.getBailAmount())+"-2";
						}else{
							subaccount_operation += "&"+transInfo.getReceiveFormID()+"-"+String.valueOf(transInfo.getBailAmount())+"-2";
						}
						iIndex++;
					}
				}else{
					Sett_SubAccountInfo accountInfo = (Sett_SubAccountInfo)subAccountList.get(0);
					transInfo.setReceiveFormID(accountInfo.getAl_NLoanNoteId());
					financeTadi = transferTransReturnFinanceInfoToTransAccountDetailInfo(transInfo, TRANS_DEPOSIT_MARGIN);
					financeSubAccountID=accountOperation.withdrawFinance(financeTadi);	
					subaccount_operation += accountInfo.getAl_NLoanNoteId()+"-"+String.valueOf(transInfo.getBailAmount())+"-2";
					
					//added haoliang 2010-08-17 将子账户已结清状态修改为未结清
					long subAccountStatus = sett_SubAccountDAO.findStatusByID(financeSubAccountID);
					if(subAccountStatus > -1 && subAccountStatus == SETTConstant.SubAccountStatus.FINISH)
					{
						sett_SubAccountDAO.updateNoFinishDateAndStatus(financeSubAccountID,SETTConstant.SubAccountStatus.NORMAL);
					}
					//end
				}
				transInfo.setBailAmount(tempBalance);
				TransReturnFinanceNewInfo returnFinanceNewInfo = new TransReturnFinanceNewInfo();
				returnFinanceNewInfo.setTransno(transInfo.getTransNo());
				returnFinanceNewInfo.setStartDate(new Date());
				returnFinanceNewInfo.setEndDate(new Date());
				Sett_TransReturnFinanceDao dao=new Sett_TransReturnFinanceDao();
				dao.deleteQuantityRepaymentBalance(returnFinanceNewInfo.getTransno());
				dao.createHrefFind(returnFinanceNewInfo, subaccount_operation);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new IRollbackException(ctx,"Gen_E001");
			}
		}
		
		/*				change by feiye 2006.5.24     开立子账户的功能在此处不用做了，因为在还款复核时不需要开立子账户
		//保证金开空户（保证金信息），返回值=保证金子账户ID
		log.debug("---------开始开立保证金子账户-----------");
		log.debug(UtilOperation.dataentityToString(transInfo));
		//SubAccountMarginInfo safi = this.transferTransMarginOpenInfoToSubAccountMargindInfo(transInfo);
		SubAccountReceiveFinanceInfo safi = this.transferTransReceiveFinanceInfoToSubAccountLoanInfo(transInfo);
		UtilOperation.dataentityToString(safi);
		//marginSubAccountID = accountOperation.openFixSubAccount(safi);
		marginSubAccountID = accountOperation.openLoanSubAccount(safi);
		log.debug("---------新开立的保证金子账户ID: " + marginSubAccountID + "---------");
		log.debug("---------结束开立保证金子账户-----------");
		//保证金存入（保证金账户ID，保证金子账户ID，对方账户=CurrentAccountID）
		log.debug("---------开始开立保证金存入depositFix-----------");
		//fixedTadi = transferTransMarginOpenInfoToTransAccountDetailInfo(transInfo, TRANS_FIXED);
		fixedTadi = transferTransReceiveFinanceInfoToTransAccountDetailInfo(transInfo, TRANS_FIXED);
		fixedTadi.setTransSubAccountID(marginSubAccountID);
		accountOperation.depositFix(fixedTadi);
		log.debug("---------结束开立保证金存入depositFix-----------");
		*/
		
		
		//通存通兑处理InterbranchSettlement()
		//@TBD
		/**
		 * 产生会计分录:分录类型lEntryType =0 无关，lAccountID1=收款方账户，lAccountID2=付款方账户， dAmount1=发生额
		 */
		log.debug("-------开始产生会计分录  融资租赁还款----------");
		GenerateGLEntryParam param = new GenerateGLEntryParam();
		
		/**
		 * 如果收/付款银行账户ID>0，则本金 流向lPrincipalType =2银行，否则本金流向lPrincipalType =1 内部转账
		 */
		long lPrincipalType = -1;	//本金流向			1:内部转账   2:银行
	
		if (transInfo.getReturnCorpusBankID() > 0)
		{
			//本金流向是 银行
			lPrincipalType = SETTConstant.CapitalType.BANK;
		}
		else
		{
			//本金流向是 内部转账
			lPrincipalType = SETTConstant.CapitalType.INTERNAL;
		}
		System.out.println("......dddddddddddddddddddddddd............  lPrincipalType = "+lPrincipalType);
		
		//分录类型
		long lEntryType = SETTConstant.EntryType.DIVIDE;	//分拆  2006.5.12 add by feiye
		
		/***********************************账户处理**************************/
		//付款方账户
		long payAccountID = currentSubAccountID;
		System.out.println(".................. 付款方账户(融资租赁保证金本金及利息) : "+payAccountID);
		
		//付融资租赁保证金账户
		long payFinanceAccountID=financeSubAccountID;
		System.out.println(".................. 付融资租赁保证金账户 : "+financeSubAccountID);
		
		/***********************************金额处理**************************/
		//融资租赁保证金本息合计
		//本息合计
		//利息合计
		//本金/交易金额
		
		double returnAmount=0.0;
		returnAmount=transInfo.getCorpusAmount()+transInfo.getInterestAmount()-transInfo.getBailAmount();
		log.debug("---得到了本次还租金金额为："+transInfo.getCorpusAmount());
		log.debug("---得到了本次还利息金额为："+transInfo.getInterestAmount());
		log.debug("---得到了本次扣除保证金金额为："+transInfo.getBailAmount());
		log.debug("---得到了本次还款金额为："+returnAmount);
		
		//融资租赁保证金本息合计
		double financeAllAmount=transInfo.getBailAmount();
		System.out.println(".................. 融资租赁保证金本息合计 : "+financeAllAmount);
		//本息合计
		double totalPrincipalAndInterest=transInfo.getCorpusAmount()+transInfo.getInterestAmount();
		System.out.println(".................. 本息合计 : "+totalPrincipalAndInterest);
		//利息合计
		double dTotalInterest=transInfo.getInterestAmount();
		System.out.println(".................. 利息合计 : "+dTotalInterest);
		//本金/交易金额
		double dAmount = transInfo.getCorpusAmount();
		System.out.println(".................. 本金/交易金额 : "+dAmount);
		
		//定义参数及给参数赋值
		param.setOfficeID(transInfo.getOfficeID());			//办事处
		param.setCurrencyID(transInfo.getCurrencyID());		//币种
		param.setTransactionTypeID(transInfo.getTransactionTypeID());		//交易类型
		param.setExecuteDate(transInfo.getExecuteDate());	//执行日
		param.setInterestStartDate(transInfo.getInterestStartDate());		//起息日
		param.setTransNo(transInfo.getTransNo());		//交易号
		
		param.setInputUserID(transInfo.getInputUserID());	//输入人
		param.setCheckUserID(transInfo.getCheckUserID());	//复核人
		
		param.setPrincipalType(lPrincipalType);	//本金流向
		
		param.setEntryType(lEntryType);					//分录类型
		
		/**********************账户信息**************************/
		param.setPayFinanceAccountID(payFinanceAccountID);		//付融资租赁保证金账户ID
		if(transInfo.getReturnCorpusBankID() > 0){
			param.setPrincipalBankID(transInfo.getReturnCorpusBankID());		//本金开户行
			param.setBankPrincipalAndInterest(returnAmount);//融资租赁开户行本息合计
		}else{
			param.setPayAccountID(payAccountID);				//付款方账户
			param.setCurrentPrincipalAndInterest(returnAmount);//融资租赁活期本息合计
		}
		/**********************金额信息**************************/
		param.setFinanceAllAmount(financeAllAmount);	//融资租赁保证金本息合计
		param.setTotalPrincipalAndInterest(totalPrincipalAndInterest);	//本息合计
		param.setTotalInterest(dTotalInterest);			//利息合计
		param.setPrincipalOrTransAmount(dAmount);		//本金/交易金额
		
		boolean res = glopOperation.generateGLEntry(param);
		if (!res)
		{
			throw new IRollbackException(ctx, "借贷不平衡，交易失败");
		}
		log.debug("-------结束产生会计分录----------");

	
		/***********构造银行付款指令**********/
		

	}	
	
	/**
	 * 融资租赁还款交易--取消复核 @param TransReturnFinanceInfo @Exception RemoteException, IRollbackException
	 */
	public void cancelCheckReturnFinance(TransReturnFinanceInfo transInfo) throws RemoteException, IRollbackException
	{
		long currentSubAccountID = -1;
		long fixedSubAccountID = -1;
		TransAccountDetailInfo currentTadi = null;
		TransAccountDetailInfo financeTadi = null;
		log.debug("---------开始cancelCheckReturnFinance-----------");
		//本金及利息的存款来源为活期,反交易
		if (transInfo.getReturnCorpusAccountID() > 0  && transInfo.getReturnInterestAccountID() > 0)
		{
			log.debug("---------活期子账户大于0,存款来源为活期存款，开始活期账户交易：cancelWithdrawCurrent-----------");
			//currentTadi = transferTransFixedOpenInfoToTransAccountDetailInfo(transInfo, TRANS_CURRENT);
			currentTadi = transferTransReturnFinanceInfoToTransAccountDetailInfo(transInfo, TRANS_CURRENT);
			
			accountOperation.cancelWithdrawCurrent(currentTadi);
			log.debug("---------结束活期账户反交易：cancelWithdrawCurrent-----------");
		}
		
		//定期存入反交易
//		log.debug("---------开始融资租赁还款保证金存入反交易：cancelDepositFix-----------");
//		//fixedTadi = transferTransFixedOpenInfoToTransAccountDetailInfo(transInfo, TRANS_FIXED);
//	
//		if(transInfo.getReturnBailAccountID() > 0 )
//		{
//			if(transInfo.getBailAmount()>0)
//			{
//				financeTadi = transferTransReturnFinanceInfoToTransAccountDetailInfo(transInfo, TRANS_FIXED);
//				accountOperation.cancelWithdrawFinance(financeTadi);
//			}
//		}
		
		//modify by zwxiao 2010-08-05
		Sett_TransReturnFinanceDao financeDao = new Sett_TransReturnFinanceDao();
		TransReturnFinanceNewInfo newInfo = null;
		try {
			newInfo = financeDao.getHrefFindListByTransNo(transInfo.getTransNo());
			if(newInfo != null){
				String accountOperationDetail = newInfo.getAccountOperation();
				String[] tempOperationDetail = accountOperationDetail.split("&");
				long loanNoteID = -1;
				double amount = 0.00;
				if(transInfo.getReturnBailAccountID() > 0 )
				{
					if(transInfo.getBailAmount()>0)
					{
						for(int i = 0;i<tempOperationDetail.length;i++){
							loanNoteID = Long.valueOf(tempOperationDetail[i].split("-")[0]).longValue();
							amount = Double.valueOf(tempOperationDetail[i].split("-")[1]).doubleValue();
							financeTadi = transferTransReturnFinanceInfoToTransAccountDetailInfo(transInfo, TRANS_DEPOSIT_MARGIN);
							financeTadi.setLoanNoteID(loanNoteID);					
							financeTadi.setAmount(amount);
							accountOperation.cancelWithdrawFinance(financeTadi);
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IRollbackException(ctx,"Gen_E001");
		}
			
		log.debug("---------结束融资租赁还款保证金存入反交易：cancelDepositFix-----------");
		
		/*				在还款时没有进行子账户的开户处理，所以不需要删除子账户
		//定期开户反交易
		log.debug("---------开始定期开立空户反交易：deleteFixSubAccount-----------");
		accountOperation.deleteFixSubAccount(cancelDepositSubFixedAccount);
		log.debug("---------结束定期开立空户反交易：deleteFixSubAccount-----------");
		*/
		
		//通存通兑处理InterbranchSettlement()
		//@TBD
		//删除明细账
		log.debug("---------开始删除账户交易明细__--交易号为:"+transInfo.getTransNo());
		accountOperation.deleteTransAccountDetail(transInfo.getTransNo());		
		
		log.debug("---------开始删除生成的会计分录--交易号为:"+transInfo.getTransNo());
		glopOperation.deleteGLEntry(transInfo.getTransNo());						
		
		log.debug("---------结束cancelCheckReturnFinance-----------");
		
		for(int i=0;i<10;i++)
			log.debug("      结束取消复核还保证金的后台处理操作.     ");
	}
	/**
	 * 转贴现类复核 逻辑操作：
	 * 
	 * @param transInfo
	 * @throws IRollbackException
	 */
	public void checkTransDiscount(TransDiscountDetailInfo transInfo) throws RemoteException, IRollbackException
	{   
		TransDiscountDelegation delegation = new TransDiscountDelegation();
		log.debug("------开始转贴现类复核--------");
		log.debug(UtilOperation.dataentityToString(transInfo));
		Collection resultColl = null;
		TransDiscountSubjectInfo subjectInfo = new TransDiscountSubjectInfo();
		subjectInfo.setTransDiscountID(transInfo.getID());
		resultColl = delegation.findSubjectInfo(subjectInfo);
		ArrayList list = new ArrayList();
		 if( (resultColl != null) && (resultColl.size() > 0) )
			{
		       Iterator it = resultColl.iterator();
		         while (it.hasNext() )
		       {    
			TransDiscountSubjectInfo info = ( TransDiscountSubjectInfo ) it.next();                     						  
		    GLEntryInfo glEntryIfo = new GLEntryInfo();
			Sett_GeneralLedgerDAO glDAO = new Sett_GeneralLedgerDAO();
			try{
			String subjectCode = glDAO.findGLSubjectCode(info.getGeneralLedger());
			glEntryIfo.setSubjectCode(subjectCode);
			}
			catch(SQLException e){
				e.printStackTrace();
				throw new IRollbackException(ctx, "生成会计分录失败："+e.getMessage());
			}		
			glEntryIfo.setTransDirection(info.getMdirection());
			glEntryIfo.setAmount(info.getMamount());
			glEntryIfo.setAbstract(transInfo.getSAbstract());
			glEntryIfo.setCheckUserID(transInfo.getNCheckUserID());
			glEntryIfo.setCurrencyID(transInfo.getNCurrencyID());
			glEntryIfo.setExecute(transInfo.getDtExecute());
			glEntryIfo.setInputUserID(transInfo.getNInputUserID());
			glEntryIfo.setInterestStart(transInfo.getDtInterestStart());
			glEntryIfo.setOfficeID(transInfo.getNOfficeID());
			glEntryIfo.setStatusID(SETTConstant.TransactionStatus.CHECK);
			glEntryIfo.setTransactionTypeID(SETTConstant.TransactionType.TRANSDISCOUNTGRANT);
			glEntryIfo.setTransNo(transInfo.getSTransNo());
			log.debug(UtilOperation.dataentityToString(glEntryIfo));
			list.add(glEntryIfo);
			log.debug("------结束会计分录-------");
		   }
	  }       
	//--------------------------------------------------------------------------
		if (list.size() > 0)
		{
			GLEntryInfo[] infos = new GLEntryInfo[list.size()];
			for (int i = 0; i < list.size(); i++)
			{
				infos[i] = (GLEntryInfo) list.get(i);
			}
			glopOperation.addDiscountGLEntries(infos);
		}
		log.debug("------结束转贴现类复核--------");		
		log.debug("------构造转贴现银行付款指令开始--------");
		//判断交易是否需要生成指令，如果存在活期账借贷则指令生成
		//查找转贴现类型
		TransDiscountDetailInfo transDiscountInfo = delegation.findTransDiscountByNoteID(transInfo.getNDiscountCredence());
		if( transDiscountInfo.getNInOrOut()== LOANConstant.TransDiscountInOrOut.IN ) {
			/***********构造银行付款指令**********/
			//是否有银企接口
			boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
			//是否需要生成银行指令
			long bankID = transInfo.getNBankID();//银行类型id
			
			if(bIsValid) {//有银企接口并且是需要生成银行指令
				try {
					log.debug("------开始转贴现指令生成--------");
					//构造参数
					CreateInstructionParam instructionParam = new CreateInstructionParam();
					instructionParam.setTransactionTypeID(SETTConstant.TransactionType.TRANSDISCOUNTGRANT);
					instructionParam.setObjInfo(transInfo);
					instructionParam.setOfficeID(transInfo.getNOfficeID());
					instructionParam.setCurrencyID(transInfo.getNCurrencyID());
					instructionParam.setCheckUserID(transInfo.getNCheckUserID());
					instructionParam.setBankType(bankID);
					instructionParam.setInputUserID(transInfo.getNInputUserID());					
					//生成银行指令并保存
					IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
					bankInstruction.createBankInstruction(instructionParam);					
					log.debug("------生成银行转贴现指令结束--------");					
				} catch (Throwable e) {
					log.error("生成银行转贴现指令失败");
					e.printStackTrace();
					throw new IRollbackException(ctx, "生成银行转贴现指令失败："+e.getMessage());
				}
			}
			else {
				Log.print("没有银行接口或不需要生成银行指令！");
			}
		}
		else {
			log.info("借贷业务不需要指令形成！");
		}
	}
	/**
	 * 信贷资产转让交易复核
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void checkTransferContract(TransferLoanContractInfo info) throws RemoteException, IRollbackException
	{
		    log.debug("--------开始复核转让复核------------");
		
			log.debug("--------开始产生会计分录------------");
			GenerateGLEntryParam param = new GenerateGLEntryParam();
			/**
			 * 如果收/付款银行账户ID>0，则本金 流向lPrincipalType =2银行，否则本金流向lPrincipalType =1 内部转账
			 */
			long lPrincipalType = -1;
			//流向 无关
		    lPrincipalType = SETTConstant.CapitalType.IRRESPECTIVE;
			//分录类型 无关
			long lEntryType = SETTConstant.EntryType.IRRESPECTIVE;
            //交易子类型
			if (info.getTRANSFERTYPE()== CRAconstant.CraTransactionType.REPURCHASE_NOTIFY)//区分卖出回购，卖出卖断
			{
			param.setSubTransactionType(SETTConstant.SubTransactionType.REPURCHASE_NOTIFY);
			}
			else
			{
			param.setSubTransactionType(SETTConstant.SubTransactionType.BREAK_NOTIFY);	
			}
			double dAmount = info.getAMOUNT();
			double interest = info.getINTEREST();
			double commission = info.getCOMMISSION();
			param.setPrincipalBankID(info.getPAYBANKID());
			param.setInterestBankID(info.getPAYBANKID());
			param.setPrincipalType(lPrincipalType);
			param.setEntryType(lEntryType);
			param.setPrincipalOrTransAmount(dAmount);
			param.setPreDrawInterest(info.getPreDrawInterest());
			param.setUnPreDrawInterest(info.getUnPreDrawInterest());
			param.setTotalInterest(interest);
			param.setCommissionFee(commission);
			param.setTransactionTypeID(info.getTRANSACTIONTYPEID());
			param.setTransNo(info.getSTRANSNO());
			param.setOfficeID(info.getOFFICEID());
			param.setCurrencyID(info.getCURRENCYID());
			param.setExecuteDate(info.getEXECUTE());
			param.setInterestStartDate(info.getINTERESTSTART());
			param.setAbstractStr(info.getSABSTRACT());
			param.setInputUserID(info.getINPUTUSERID());
			param.setCheckUserID(info.getCHECKUSERID());
			param.setCraContractID(info.getTRANSFERCONTRACTID());
			param.setGenaralLedgerTypeID(info.getPAYGENRALLEDGERTYPEID());
			boolean res = glopOperation.generateGLEntry(param);
			if (!res)
			{
				throw new IRollbackException(ctx, "产生会计分录错误2");
			}
			log.debug("--------结束产生会计分录------------");
		}
	/**
	 * 信贷资产转让交易取消复核
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelCheckTransferContract(TransferLoanContractInfo info) throws RemoteException, IRollbackException
	{
		    log.debug("--------开始复核转让复核------------");
		
			log.debug("--------开始产生会计分录------------");
			GenerateGLEntryParam param = new GenerateGLEntryParam();
			/**
			 * 如果收/付款银行账户ID>0，则本金 流向lPrincipalType =2银行，否则本金流向lPrincipalType =1 内部转账
			 */
			long lPrincipalType = -1;
		    lPrincipalType = SETTConstant.CapitalType.BANK;
			//分录类型 无关
			long lEntryType = SETTConstant.EntryType.IRRESPECTIVE;
			//收款方银行
			long receiveBankID = info.getRECEIVEBANKID();
			//付款方账户
			long payBankID = info.getPAYBANKID();
			double dAmount = info.getAMOUNT();
			param.setPrincipalBankID(receiveBankID);
			param.setPrincipalType(lPrincipalType);
			param.setEntryType(lEntryType);
			param.setPrincipalOrTransAmount(dAmount);
			param.setTransactionTypeID(info.getTRANSACTIONTYPEID());
			param.setTransNo(info.getSTRANSNO());
			param.setOfficeID(info.getOFFICEID());
			param.setCurrencyID(info.getCURRENCYID());
			param.setExecuteDate(info.getEXECUTE());
			param.setInterestStartDate(info.getINTERESTSTART());
			param.setAbstractStr(info.getSABSTRACT());
			param.setInputUserID(info.getINPUTUSERID());
			param.setCheckUserID(info.getCHECKUSERID());
			glopOperation.deleteGLEntry(info.getSTRANSNO());
			log.debug("--------结束删除会计分录------------");
		}
	
	/**
	 * 信贷资产转让收款交易复核
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void repaycheckTransferContract(TransferLoanContractInfo info) throws RemoteException, IRollbackException
	{
		    log.debug("--------开始复核转让复核------------");
		
			log.debug("--------开始产生会计分录------------");
			GenerateGLEntryParam param = new GenerateGLEntryParam();
			/**
			 * 如果收/付款银行账户ID>0，则本金 流向lPrincipalType =2银行，否则本金流向lPrincipalType =1 内部转账
			 */
			long lPrincipalType = -1;
			//流向 无关
			if(info.getRECEIVEBANKID()>0)
			{
				lPrincipalType = SETTConstant.CapitalType.BANK;
			}
			else if(info.getRECGENERALLEDGERTYPEID()>0)
			{
				lPrincipalType = SETTConstant.CapitalType.GENERALLEDGER;
			}
			else
			{
				lPrincipalType = SETTConstant.CapitalType.IRRESPECTIVE;
			}
		    
			//分录类型 无关
			long lEntryType = SETTConstant.EntryType.IRRESPECTIVE;
            //交易子类型
			if (info.getTRANSFERTYPE()== CRAconstant.CraTransactionType.REPURCHASE_NOTIFY)//区分卖出回购，卖出卖断
			{
			param.setSubTransactionType(SETTConstant.SubTransactionType.REPURCHASE_NOTIFY);
			}
			else
			{
			param.setSubTransactionType(SETTConstant.SubTransactionType.BREAK_NOTIFY);	
			}
			double dAmount = info.getAMOUNT();
			double interest = info.getINTEREST();
			double commission = info.getCOMMISSION();
			param.setPrincipalBankID(info.getRECEIVEBANKID());
			param.setPrincipalType(lPrincipalType);
			param.setEntryType(lEntryType);
			param.setPrincipalOrTransAmount(dAmount);
			param.setTotalInterest(interest);
			param.setCommissionFee(commission);
			param.setTransactionTypeID(info.getTRANSACTIONTYPEID());
			param.setTransNo(info.getSTRANSNO());
			param.setOfficeID(info.getOFFICEID());
			param.setCurrencyID(info.getCURRENCYID());
			param.setExecuteDate(info.getEXECUTE());
			param.setInterestStartDate(info.getINTERESTSTART());
			param.setAbstractStr(info.getSABSTRACT());
			param.setInputUserID(info.getINPUTUSERID());
			param.setCheckUserID(info.getCHECKUSERID());
			param.setCraContractID(info.getTRANSFERCONTRACTID());
			param.setGenaralLedgerTypeID(info.getRECGENERALLEDGERTYPEID());
			param.setList((ArrayList)info.getRepaycoll());
			boolean res = glopOperation.generateGLEntry(param);
			if (!res)
			{
				throw new IRollbackException(ctx, "产生会计分录错误2");
			}
			log.debug("--------结束产生会计分录------------");
		}
	/**
	 * 信贷资产转让收款交易取消复核
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void repaycancelCheckTransferContract(TransferLoanContractInfo info) throws RemoteException, IRollbackException
	{
		    log.debug("--------开始删除转让复核------------");
			glopOperation.deleteGLEntry(info.getSTRANSNO());
			log.debug("--------结束删除会计分录------------");
	}

	/**
	 * 信贷资产转让收款交易复核(代收)
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void repayClientcheckTransferContract(TransferLoanContractInfo info) throws RemoteException, IRollbackException
	{
		    log.debug("--------开始复核转让复核------------");
		    TransferContractBiz contractBiz = new TransferContractBiz();
		    TransferContractInfo  contractInfo = null;
		    try
		    {
		    	contractInfo = contractBiz.findInfoById(info.getTRANSFERCONTRACTID());
		    }
		    catch(Exception e)
		    {
		    	e.printStackTrace();
		    }
		    TransferLoanContractDelegation delegation = new TransferLoanContractDelegation();
		    NoticeAndAgentDetailConditionInfo candnConditionInfo = new NoticeAndAgentDetailConditionInfo();
		    candnConditionInfo.setTransferLoanAmountID(info.getID());
		    candnConditionInfo.setTransferNoticeFormId(info.getNOTICEID());
			candnConditionInfo.setOfficeID(info.getOFFICEID());
			candnConditionInfo.setCurrencyID(info.getCURRENCYID());
		    Collection coll = delegation.findNoticeAndAgentDetial(candnConditionInfo);
		    if(coll!=null)
		    {
		    	log.debug("--------开始产生会计分录------------");
		    	Iterator it = coll.iterator();
		    	while(it.hasNext())
		    	{
		    		NoticeAndAgentDetialResultInfo resultInfo = (NoticeAndAgentDetialResultInfo)it.next();

					GenerateGLEntryParam param = new GenerateGLEntryParam();
					/**
					 * 如果收/付款银行账户ID>0，则本金 流向lPrincipalType =2银行，否则本金流向lPrincipalType =1 内部转账
					 */
					long lPrincipalType = -1;
					//流向 无关
				    lPrincipalType = SETTConstant.CapitalType.IRRESPECTIVE;
					//分录类型 无关
					long lEntryType = SETTConstant.EntryType.IRRESPECTIVE;
		            //交易子类型
					param.setSubTransactionType(SETTConstant.SubTransactionType.BREAK_NOTIFY);	
					double dAmount = resultInfo.getBalance();
					double interest = resultInfo.getInterest();
					double commission = info.getCOMMISSION();
					param.setPayAccountID(resultInfo.getPayAccountID());
					param.setPrincipalType(lPrincipalType);
					param.setEntryType(lEntryType);
					if(contractInfo.getCommissionPaymentType()==CRAconstant.CommisonPayType.FANHUAN)
					{
						param.setPrincipalOrTransAmount(dAmount);
						param.setTotalInterest(interest);
						param.setReallyReceiveInterest(interest);
					}
					else
					{
						param.setPrincipalOrTransAmount(dAmount);
						param.setTotalInterest(interest);
						
						if(contractInfo.getChargeTypeId()==CRAconstant.ChargeCommisonPayType.CHARGEAMOUNT)
						{
							if(info.getAMOUNT()>0)
							{
								param.setReallyReceiveInterest(interest-UtilOperation.Arith.round((commission*dAmount/info.getAMOUNT()),2));
								param.setCommissionFee(UtilOperation.Arith.round((commission*dAmount/info.getAMOUNT()),2));
							}
							else
							{
								param.setReallyReceiveInterest(interest);
							}
						}
						else if(contractInfo.getChargeTypeId()==CRAconstant.ChargeCommisonPayType.CHARGEINTEREST || contractInfo.getChargeTypeId()==CRAconstant.ChargeCommisonPayType.CHARGEOTHER)
						{
							if(info.getINTEREST()>0)
							{
								param.setReallyReceiveInterest(interest-UtilOperation.Arith.round((commission*interest/info.getINTEREST()),2));
								param.setCommissionFee(UtilOperation.Arith.round((commission*interest/info.getINTEREST()),2));
							}
							else
							{
								param.setReallyReceiveInterest(interest);
							}
						}
						else
						{
							param.setReallyReceiveInterest(interest);
						}
						
					}
					param.setTransactionTypeID(info.getTRANSACTIONTYPEID());
					param.setTransNo(info.getSTRANSNO());
					param.setOfficeID(info.getOFFICEID());
					param.setCurrencyID(info.getCURRENCYID());
					param.setExecuteDate(info.getEXECUTE());
					param.setInterestStartDate(info.getINTERESTSTART());
					param.setAbstractStr(info.getSABSTRACT());
					param.setInputUserID(info.getINPUTUSERID());
					param.setCheckUserID(info.getCHECKUSERID());
					param.setCraContractID(info.getTRANSFERCONTRACTID());
					boolean res = glopOperation.generateGLEntry(param);
					if (!res)
					{
						throw new IRollbackException(ctx, "产生会计分录错误2");
					}
		    		
		    	}
		    	log.debug("--------结束产生会计分录------------");
		    }
			
			
		}
	/**
	 * 信贷资产转让收款交易取消复核(代收)
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void repayClientcancelCheckTransferContract(TransferLoanContractInfo info) throws RemoteException, IRollbackException
	{
		    log.debug("--------开始删除转让复核------------");
		    //删除明细账
			accountOperation.deleteTransAccountDetail(info.getSTRANSNO());
			glopOperation.deleteGLEntry(info.getSTRANSNO());
			log.debug("--------结束删除会计分录------------");
	}
	/**
	 * add by kevin(刘连凯)2011-07-15
	 * 内部拆借保存
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveInternalLend(TransInternalLendInfo info) throws RemoteException, IRollbackException
	{
		log.debug("------开始内部拆借保存--------");
		//检查拆借账户是否可以做收付款操作
		if( info.getLendingAccountID() > 0 )
		{
			log.info("拆借账户ID:" + info.getLendingAccountID());		
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(info.getLendingAccountID(),AccountBean.TRANSTYPE_WITHDRAW);
			} catch (IException e) {
				e.printStackTrace();				
				throw new IRollbackException(ctx,e);
			}
		}
		//检查备付金账户是否可以做收付款操作
		if( info.getReserveAccountID() > 0 )
		{
			log.info("备付金账户ID:" + info.getReserveAccountID());		
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(info.getReserveAccountID(),AccountBean.TRANSTYPE_WITHDRAW);
			} catch (IException e) {
				e.printStackTrace();				
				throw new IRollbackException(ctx,e);
			}
		}
		log.debug("------结束内部拆借保存--------");
	}
	/**
	 * add by kevin(刘连凯)
	 * 2011-7-15
	 * 内部拆借-复核操作
	 */
	private final static int INTERNALLEND_LENDACC_DEPOSIT = 1;//拆借账户存入
	private final static int INTERNALLEND_BAK_DEPOST = 2;//备付金账户存入
	public void checkInternalLend(TransInternalLendInfo info) throws RemoteException, IRollbackException
	{
		log.debug("------开始内部拆借复核--------");
		long depositLendSubAccountID=-1;
		long depositBakSubAccountID=-1;		
		if(info.getLendingAccountID()>0){
			log.print("------开始拆借账户存入--------");
			TransAccountDetailInfo LendTadi = transferTransInternalLendInfoToTransAccountDetailInfo(info,INTERNALLEND_LENDACC_DEPOSIT);
			depositLendSubAccountID = accountOperation.depositCurrent(LendTadi);
			log.print("------结束拆借账户存入--------");
		}
		if(info.getReserveAccountID()>0){
			log.print("------开始备付金账户存入--------");
			TransAccountDetailInfo bakTadi = transferTransInternalLendInfoToTransAccountDetailInfo(info,INTERNALLEND_BAK_DEPOST);
			depositBakSubAccountID = accountOperation.depositCurrent(bakTadi);
			log.print("------结束拆备付金账户存入--------");
		}
		log.print("--------开始产生会计分录------------");
		GenerateGLEntryParam param = new GenerateGLEntryParam();
		param.setReceiveAccountID(depositBakSubAccountID);		
		param.setPayAccountID(depositLendSubAccountID);		
		param.setPrincipalOrTransAmount(info.getAmount());  
		param.setTransactionTypeID(info.getTransActionTypeID());
		param.setTransNo(info.getTransNO());
		param.setOfficeID(info.getOfficeID());
		param.setCurrencyID(info.getCurrencyID());
		param.setExecuteDate(info.getExecute());
		param.setInterestStartDate(info.getInterestStart());
		param.setAbstractStr(info.getAbstract());
		param.setInputUserID(info.getInputUserID());
		param.setCheckUserID(info.getCheckUserID());
		boolean res = glopOperation.generateGLEntry(param);
		if (!res)
		{
			throw new IRollbackException(ctx, "产生会计分录错误");
		}		
		log.print("--------结束产生会计分录------------");
		log.print("------结束内部拆借复核--------");		
	}
	/**
	 * add by kevin(刘连凯)
	 * 2011-7-15
	 * 内部拆借-取消复核操作
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelCheckInternalLend(TransInternalLendInfo info) throws RemoteException, IRollbackException
	{
		log.print("------开始内部拆借取消复核--------");		
		if(info.getReserveAccountID()>0){
			log.print("------开始备付金账户存入反交易--------");
			TransAccountDetailInfo bakTadi = transferTransInternalLendInfoToTransAccountDetailInfo(info,INTERNALLEND_BAK_DEPOST);
			accountOperation.cancelDepositCurrent(bakTadi);
			log.print("------结束备付金账户存入反交易--------");
		}
		if(info.getLendingAccountID()>0){
			log.print("------开始拆借账户存入反交易--------");
			TransAccountDetailInfo LendTadi = transferTransInternalLendInfoToTransAccountDetailInfo(info,INTERNALLEND_LENDACC_DEPOSIT);
			accountOperation.cancelDepositCurrent(LendTadi);
			log.print("------结束拆借账户存入反交易--------");
		}
		log.print("------删除交易明细--------");
		accountOperation.deleteTransAccountDetail(info.getTransNO());
		log.print("------删除会计分录--------");
		glopOperation.deleteGLEntry(info.getTransNO());
		log.print("------结束内部拆借取消复核--------");		
	}
		/**
	 * add by kevin(刘连凯)
	 * 2011-7-19
	 * 内部拆借收回-保存
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveInternalLendRepayment(TransInternalLendInfo info) throws RemoteException, IRollbackException
	{
		log.debug("------开始内部拆借收回保存--------");
		//检查拆借账户是否可以做收付款操作
		if( info.getLendingAccountID() > 0 )
		{
			log.info("拆借账户ID:" + info.getLendingAccountID());		
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(info.getLendingAccountID(),AccountBean.TRANSTYPE_WITHDRAW);
			} catch (IException e) {
				e.printStackTrace();				
				throw new IRollbackException(ctx,e);
			}
		}
		//检查备付金账户是否可以做收付款操作
		if( info.getReserveAccountID() > 0 )
		{
			log.info("备付金账户ID:" + info.getReserveAccountID());		
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(info.getReserveAccountID(),AccountBean.TRANSTYPE_WITHDRAW);
			} catch (IException e) {
				e.printStackTrace();				
				throw new IRollbackException(ctx,e);
			}
		}
		if (info.getReserveAccountID() > 0)
		{
			log.debug("--------开始累计备付金账户未复核金额------------");
			accountOperation.addCurrentUncheckAmount(info.getReserveAccountID(), info.getLendingAccountID(), info.getAmount());
			log.debug("--------结束累计备付金账户未复核金额------------");
		}
		if (info.getLendingAccountID() > 0)
		{
			log.debug("--------开始累计拆借账户未复核金额------------");
			accountOperation.addCurrentUncheckAmount(info.getLendingAccountID(), info.getReserveAccountID(), info.getAmount());
			log.debug("--------结束累计拆借账户未复核金额------------");
		}
		log.debug("------结束内部拆借收回保存--------");
	}
	/**
	 * add by kevin(刘连凯)
	 * 2011-7-19
	 * 内部拆借收回-删除
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void deleteInternalLendRepayment(TransInternalLendInfo info) throws RemoteException, IRollbackException
	{
		log.debug("------开始内部拆借收回删除--------");
		if (info.getLendingAccountID() > 0)
		{
			log.debug("--------开始减少拆借账户未复核金额------------");
			accountOperation.subtractCurrentUncheckAmount(info.getLendingAccountID(), info.getAmount());
			log.debug("--------结束减少拆借账户未复核金额------------");
		}
		if (info.getReserveAccountID() > 0)
		{
			log.debug("--------开始减少备付金账户未复核金额------------");
			accountOperation.subtractCurrentUncheckAmount(info.getReserveAccountID(), info.getAmount());
			log.debug("--------结束减少备付金账户未复核金额------------");
		}
		log.debug("------结束内部拆借收回删除--------");
	}
	/**
	 * add by kevin(刘连凯)
	 * 2011-7-19
	 * 内部拆借收回-复核
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	private final static int INTERNALLENDREPAYMENT_LENDACC_WITHDRAW = 3;//拆借账户支取
	private final static int INTERNALLENDREPAYMENT_BAK_WITHDRAW = 4;//备付金账户支取

	public void checkInternalLendRepayment(TransInternalLendInfo info) throws RemoteException, IRollbackException
	{
		log.debug("------开始内部拆借收回复核--------");
		long receiveLendSubAccountID=-1;
		long payBakSubAccountID=-1;	
		if(info.getReserveAccountID()>0){
			log.print("------开始备付金账户支取--------");
			TransAccountDetailInfo bakTadi = transferTransInternalLendInfoToTransAccountDetailInfo(info,INTERNALLENDREPAYMENT_BAK_WITHDRAW);
			payBakSubAccountID = accountOperation.withdrawCurrent(bakTadi);
			log.print("------结束拆备付金账户支取--------");
		}
		if(info.getLendingAccountID()>0){
			log.print("------开始拆借账户支取--------");
			TransAccountDetailInfo LendTadi = transferTransInternalLendInfoToTransAccountDetailInfo(info,INTERNALLENDREPAYMENT_LENDACC_WITHDRAW);
			receiveLendSubAccountID = accountOperation.withdrawCurrent(LendTadi);
			log.print("------结束拆借账户支取--------");
		}		
		log.print("--------开始产生会计分录------------");
		GenerateGLEntryParam param = new GenerateGLEntryParam();
		param.setReceiveAccountID(receiveLendSubAccountID);		
		param.setPayAccountID(payBakSubAccountID);		
		param.setPrincipalOrTransAmount(info.getAmount());  
		param.setTransactionTypeID(info.getTransActionTypeID());
		param.setTransNo(info.getTransNO());
		param.setOfficeID(info.getOfficeID());
		param.setCurrencyID(info.getCurrencyID());
		param.setExecuteDate(info.getExecute());
		param.setInterestStartDate(info.getInterestStart());
		param.setAbstractStr(info.getAbstract());
		param.setInputUserID(info.getInputUserID());
		param.setCheckUserID(info.getCheckUserID());
		boolean res = glopOperation.generateGLEntry(param);
		if (!res)
		{
			throw new IRollbackException(ctx, "产生会计分录错误");
		}		
		log.print("--------结束产生会计分录------------");
		log.print("------结束内部拆借收回复核--------");		
		
	}
	/**
	 * add by kevin(刘连凯)
	 * 2011-7-19
	 * 内部拆借收回-取消复核
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelCheckInternalLendRepayment(TransInternalLendInfo info) throws RemoteException, IRollbackException
	{
		log.print("------开始内部拆借收回取消复核--------");	
		if(info.getLendingAccountID()>0){
			log.print("------开始拆借账户支取反交易--------");
			TransAccountDetailInfo LendTadi = transferTransInternalLendInfoToTransAccountDetailInfo(info,INTERNALLENDREPAYMENT_LENDACC_WITHDRAW);
			accountOperation.cancelWithdrawCurrent(LendTadi);
			log.print("------结束拆借账户支取反交易--------");
		}
		if(info.getReserveAccountID()>0){
			log.print("------开始备付金账户支取反交易--------");
			TransAccountDetailInfo bakTadi = transferTransInternalLendInfoToTransAccountDetailInfo(info,INTERNALLENDREPAYMENT_BAK_WITHDRAW);
			accountOperation.cancelWithdrawCurrent(bakTadi);
			log.print("------结束备付金账户支取反交易--------");
		}		
		log.print("------删除交易明细--------");
		accountOperation.deleteTransAccountDetail(info.getTransNO());
		log.print("------删除会计分录--------");
		glopOperation.deleteGLEntry(info.getTransNO());
		log.print("------结束内部拆借收回取消复核--------");				
	}
	private TransAccountDetailInfo transferTransInternalLendInfoToTransAccountDetailInfo(TransInternalLendInfo info,int type)
	{
		TransAccountDetailInfo resInfo = new TransAccountDetailInfo();
		resInfo.setCurrencyID(info.getCurrencyID());
		resInfo.setDtExecute(info.getExecute());
		resInfo.setDtInterestStart(info.getInterestStart());
		resInfo.setOfficeID(info.getOfficeID());
		resInfo.setTransactionTypeID(info.getTransActionTypeID());
		resInfo.setTransNo(info.getTransNO());
		resInfo.setAbstractStr(info.getAbstract());
		switch(type)
		{
			case INTERNALLEND_LENDACC_DEPOSIT:
			{
				resInfo.setTransAccountID(info.getLendingAccountID());
				resInfo.setOppAccountID(info.getReserveAccountID());
				resInfo.setAmount(info.getAmount());				
				resInfo.setAmountType(SETTConstant.AmountType.AmountType_1);	
			}	
			break;
			case INTERNALLEND_BAK_DEPOST:
			{
				resInfo.setTransAccountID(info.getReserveAccountID());
				resInfo.setOppAccountID(info.getLendingAccountID());
				resInfo.setAmount(info.getAmount());
				resInfo.setAmountType(SETTConstant.AmountType.AmountType_1);	
			}
			break;
			case INTERNALLENDREPAYMENT_BAK_WITHDRAW:
			{
				resInfo.setTransAccountID(info.getReserveAccountID());
				resInfo.setOppAccountID(info.getLendingAccountID());
				resInfo.setAmount(info.getAmount());
				resInfo.setAmountType(SETTConstant.AmountType.AmountType_1);	
			}
			break;
			case INTERNALLENDREPAYMENT_LENDACC_WITHDRAW:
			{
				resInfo.setTransAccountID(info.getLendingAccountID());
				resInfo.setOppAccountID(info.getReserveAccountID());
				resInfo.setAmount(info.getAmount());				
				resInfo.setAmountType(SETTConstant.AmountType.AmountType_1);	
			}
			break;
			
		}		
		return resInfo;
	}
	
	/**
	 * add by 江琪 2011-07-19
	 * 备付金上收-保存,
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveBakReserveAccountDetailsUpReceive(TransBakReserveInfo transInfo) throws RemoteException, IRollbackException
	{
		
			log.info(" ------开始保存备付金，准备金账户财务交易--------");
	
	
			log.info("付款方账户（准备金账户）:" + transInfo.getReserveaccountid());
			log.info("收款方账户（备付金账户）:" + transInfo.getBakaccountid());
	
			/*修改 by kenny(胡志强) (2007-03-19) 处理保存时校验账户状态的操作*/
			//如果该交易有付款方，且付款方为内部账户，则校验付款方账户状态
			if (transInfo.getReserveaccountid() > 0)
			{
				AccountBean accountBean = new AccountBean();
				try {
					accountBean.validateAccountStatus(transInfo.getReserveaccountid(),AccountBean.TRANSTYPE_WITHDRAW);
				} catch (IException e) {
					e.printStackTrace();
					// 提示“系统忙”	
					//modified by mzh_fu 2007/06/01 提示准确错误信息
		           // throw new IRollbackException(null,"Gen_E001");
					throw new IRollbackException(ctx,e);
				}
			}
			//如果该交易有收款方，且收款方为内部账户，则校验收款方账户状态
			if (transInfo.getBakaccountid() > 0)
			{
				AccountBean accountBean = new AccountBean();
				try {
					accountBean.validateAccountStatus(transInfo.getBakaccountid(),AccountBean.TRANSTYPE_DEPOSIT);
				} catch (IException e) {
					e.printStackTrace();
					// 提示“系统忙”
					//modified by mzh_fu 2007/06/01 提示准确错误信息
		           // throw new IRollbackException(null,"Gen_E001");
					throw new IRollbackException(ctx,e);
				}
			}
	
			//如果该交易有付款方，且付款方为内部账户，则调用“累计未复核金额”的方法
			if (transInfo.getReserveaccountid() > 0)
			{
	
					boolean isLackBalance= accountOperation.isLackBalanceToDraft(transInfo.getReserveaccountid(), transInfo.getAmount());
	               
					if(!isLackBalance)
					{
						log.info(" ------这笔交易的交易类型为： " + SETTConstant.TransactionType.getName(transInfo.getNtransactiontypeid()) + " 调用“累计未复核金额”的方法！ ");
						accountOperation.addCurrentUncheckAmount(transInfo.getReserveaccountid(), transInfo.getBakaccountid(), transInfo.getAmount());
					}
					else
					{
						throw new IRollbackException(ctx,"准备金余额不足,不能上收到备付金账户.");
						
					}
	                //log.info(" ------这笔交易的交易类型为： " + SETTConstant.TransactionType.getName(transInfo.getNtransactiontypeid()) + " 不调用“累计未复核金额”的方法！ ");
	
	                
			
			}
			
			log.info(" ------结束保存备付金，准备金账户 财务交易--------");
			//log.info("AccountBookEJB:saveCurrentAccountDetails是否生成银行指令 "+ transInfo.isAutoCreateBankInstruction());

	}

	
	/**
	 * add by 江琪 2011-07-19
	 * 备付金调回-保存
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveBakReserveAccountDetailsDownReturn(TransBakReserveInfo transInfo) throws RemoteException, IRollbackException
	{
			
			log.info(" ------开始保存备付金，准备金账户财务交易--------");
	
	
			log.info("付款方账户（备付金账户）:" + transInfo.getBakaccountid());
			log.info("收款方账户（准备金账户）:" + transInfo.getReserveaccountid());
	
			/*修改 by kenny(胡志强) (2007-03-19) 处理保存时校验账户状态的操作*/
			//如果该交易有付款方，且付款方为内部账户，则校验付款方账户状态
			if (transInfo.getBakaccountid() > 0)
			{
				AccountBean accountBean = new AccountBean();
				try {
					accountBean.validateAccountStatus(transInfo.getBakaccountid(),AccountBean.TRANSTYPE_WITHDRAW);
				} catch (IException e) {
					e.printStackTrace();
					// 提示“系统忙”	
					//modified by mzh_fu 2007/06/01 提示准确错误信息
		           // throw new IRollbackException(null,"Gen_E001");
					throw new IRollbackException(ctx,e);
				}
			}
			//如果该交易有收款方，且收款方为内部账户，则校验收款方账户状态
			if (transInfo.getReserveaccountid() > 0)
			{
				AccountBean accountBean = new AccountBean();
				try {
					accountBean.validateAccountStatus(transInfo.getReserveaccountid(),AccountBean.TRANSTYPE_DEPOSIT);
				} catch (IException e) {
					e.printStackTrace();
					// 提示“系统忙”
					//modified by mzh_fu 2007/06/01 提示准确错误信息
		           // throw new IRollbackException(null,"Gen_E001");
					throw new IRollbackException(ctx,e);
				}
			}
	
			//如果该交易有付款方，且付款方为内部账户，则调用“累计未复核金额”的方法
			if (transInfo.getBakaccountid() > 0)
			{

					boolean isLackBalance= accountOperation.isLackBalanceToDraft(transInfo.getBakaccountid(), transInfo.getAmount());
				
					if(!isLackBalance)
					{
		                log.info(" ------这笔交易的交易类型为： " + SETTConstant.TransactionType.getName(transInfo.getNtransactiontypeid()) + " 调用“累计未复核金额”的方法！ ");
		                accountOperation.addCurrentUncheckAmount(transInfo.getBakaccountid(), transInfo.getReserveaccountid(), transInfo.getAmount());
					}
					else
					{
						
						throw new IRollbackException(ctx,"备付金金余额不足,不能调回到.");
					}
	                //log.info(" ------这笔交易的交易类型为： " + SETTConstant.TransactionType.getName(transInfo.getNtransactiontypeid()) + " 不调用“累计未复核金额”的方法！ ");
	
			}
			
			log.info(" ------结束保存备付金，准备金账户 财务交易--------");
			//log.info("AccountBookEJB:saveCurrentAccountDetails是否生成银行指令 "+ transInfo.isAutoCreateBankInstruction());

	}

	/**
	 * add by 江琪 2011-07-19
	 * 备付金上收-删除
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void deleteBakReserveAccountDetailsUpReceive(TransBakReserveInfo transInfo) throws RemoteException, IRollbackException
	{

			log.info("付款方账户（准备金账户）:" + transInfo.getReserveaccountid());
			log.info("收款方账户（备付金账户）:" + transInfo.getBakaccountid());
	
	
			//如果该交易有付款方，且付款方为内部账户，则调用“累计未复核金额”的方法
			if (transInfo.getReserveaccountid() > 0)
			{
	                log.info(" ------这笔交易的交易类型为： " + SETTConstant.TransactionType.getName(transInfo.getNtransactiontypeid()) + " 调用“累计未复核金额”的方法！ ");
	
	                accountOperation.subtractCurrentUncheckAmount(transInfo.getReserveaccountid(), transInfo.getAmount());
	
			}
			
			log.info(" ------结束保存备付金，准备金账户 财务交易--------");


	}
	
	/**
	 * add by 江琪 2011-07-19
	 * 备付金调回-删除
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void deleteBakReserveAccountDetailsDownReturn(TransBakReserveInfo transInfo) throws RemoteException, IRollbackException
	{

			log.info("付款方账户（准备金账户）:" + transInfo.getBakaccountid());
			log.info("收款方账户（备付金账户）:" + transInfo.getReserveaccountid());
	
	
			//如果该交易有付款方，且付款方为内部账户，则调用“累计未复核金额”的方法
			if (transInfo.getBakaccountid() > 0)
			{
	                log.info(" ------这笔交易的交易类型为： " + SETTConstant.TransactionType.getName(transInfo.getNtransactiontypeid()) + " 调用“累计未复核金额”的方法！ ");
	
	                accountOperation.subtractCurrentUncheckAmount(transInfo.getBakaccountid(), transInfo.getAmount());
	
			}
			
			log.info(" ------结束保存备付金，准备金账户 财务交易--------");

	}

	public void checkBakReserveDetailsUpReceive(TransBakReserveInfo transInfo) throws RemoteException, IRollbackException
	{
	
		long paySubAccountID = -1;
		long receiveSubAccountID = -1;
		
		GeneralLedgerOperation glopOperation = new GeneralLedgerOperation();
		//总账接口操作类
		TransAccountDetailInfo tadi = null;

			log.info("付款方账户（准备金账户）:" + transInfo.getReserveaccountid());
			log.info("收款方账户（备付金账户）:" + transInfo.getBakaccountid());
			
			/*修改 by kenny(胡志强) (2007-03-19) 处理保存时校验账户状态的操作*/
			//如果该交易有付款方，且付款方为内部账户，则校验付款方账户状态
			if (transInfo.getReserveaccountid() > 0)
			{
				AccountBean accountBean = new AccountBean();
				try {
					accountBean.validateAccountStatus(transInfo.getReserveaccountid(),AccountBean.TRANSTYPE_WITHDRAW);
				} catch (IException e) {
					e.printStackTrace();
					// 提示“系统忙”	
					//modified by mzh_fu 2007/06/01 提示准确错误信息
		           // throw new IRollbackException(null,"Gen_E001");
					throw new IRollbackException(ctx,e);
				}
			}
			//如果该交易有收款方，且收款方为内部账户，则校验收款方账户状态
			if (transInfo.getBakaccountid() > 0)
			{
				AccountBean accountBean = new AccountBean();
				try {
					accountBean.validateAccountStatus(transInfo.getBakaccountid(),AccountBean.TRANSTYPE_DEPOSIT);
				} catch (IException e) {
					e.printStackTrace();
					// 提示“系统忙”
					//modified by mzh_fu 2007/06/01 提示准确错误信息
		           // throw new IRollbackException(null,"Gen_E001");
					throw new IRollbackException(ctx,e);
				}
			}
	
			//如果该交易有付款方，且付款方为内部账户，则调用“活期支取”的方法
			if (transInfo.getReserveaccountid() > 0)
			{
				//得到参数处理类
				tadi = this.transferTransBakReserveInfoToTransAccountDetailInfo(transInfo, 1);
	            
	           paySubAccountID = accountOperation.withdrawCurrent(tadi);
	     
			}
			//如果该交易有收款方，且收款方为内部账户，则调用“活期存入”的方法
			if (transInfo.getBakaccountid() > 0)
			{
				//得到参数处理类
				tadi = this.transferTransBakReserveInfoToTransAccountDetailInfo(transInfo, 2);
	                receiveSubAccountID = accountOperation.depositCurrent(tadi);
			}
		
		/**
		 * 产生会计分录:分录类型lEntryType =0 无关，lAccountID1=收款方账户，lAccountID2=付款方账户， dAmount1=发生额
		 */
		GenerateGLEntryParam param = new GenerateGLEntryParam();
		/**
		 * 如果收/付款银行账户ID>0，则本金 流向lPrincipalType =2银行，否则本金流向lPrincipalType =1 内部转账
		 */
		long lPrincipalType = -1;
		if (transInfo.getPayorreceivetype() == 2)
		{
			//本金流向是 银行
			lPrincipalType = SETTConstant.CapitalType.BANK;
		}
		else if(transInfo.getPayorreceivetype() == 1)
		{
			//本金流向是 内部转账
			lPrincipalType = SETTConstant.CapitalType.INTERNAL;
		}
		//分录类型 无关
		long lEntryType = SETTConstant.EntryType.IRRESPECTIVE;
		//收款方账户
		long receiveAccountID = receiveSubAccountID;
		//付款方账户
		long payAccountID = paySubAccountID;
		//本金开户行ID
		long principalBankID = transInfo.getBankid();
		//发生额
		double dAmount = transInfo.getAmount();
		param.setOfficeID(transInfo.getNofficeid());
		param.setCurrencyID(transInfo.getNcurrencyid());
		param.setTransactionTypeID(transInfo.getNtransactiontypeid());
		param.setExecuteDate(transInfo.getDtexecute());
		param.setInterestStartDate(transInfo.getDtintereststart());
		param.setTransNo(transInfo.getStransno());
		param.setAbstractStr(transInfo.getSabstract());
		param.setInputUserID(transInfo.getNinputuserid());
		param.setCheckUserID(transInfo.getNcheckuserid());
		
		//资金流向
		param.setPrincipalType(lPrincipalType);
		
		
		param.setEntryType(lEntryType);
		param.setReceiveAccountID(receiveAccountID);
		param.setPayAccountID(payAccountID);
		param.setPrincipalOrTransAmount(dAmount);
		param.setPrincipalBankID(principalBankID);
		
		param.setSubTransactionType(SETTConstant.SubTransactionType.NORMAL);
	
		boolean res = glopOperation.generateGLEntry(param);
		if (!res)
		{
			throw new IRollbackException(ctx, "产生会计分录错误2");
		}
	}
	
	
	public void checkBakReserveDetailsDownReturn(TransBakReserveInfo transInfo) throws RemoteException, IRollbackException
	{
	
		long paySubAccountID = -1;
		long receiveSubAccountID = -1;
		
		GeneralLedgerOperation glopOperation = new GeneralLedgerOperation();
		//总账接口操作类
		TransAccountDetailInfo tadi = null;
		
			log.info("付款方账户（备付金账户）:" + transInfo.getBakaccountid());
			log.info("收款方账户（准备金账户）:" + transInfo.getReserveaccountid());
			
			/*修改 by kenny(胡志强) (2007-03-19) 处理保存时校验账户状态的操作*/
			//如果该交易有付款方，且付款方为内部账户，则校验付款方账户状态
			if (transInfo.getBakaccountid() > 0)
			{
				AccountBean accountBean = new AccountBean();
				try {
					accountBean.validateAccountStatus(transInfo.getBakaccountid(),AccountBean.TRANSTYPE_WITHDRAW);
				} catch (IException e) {
					e.printStackTrace();
					// 提示“系统忙”	
					//modified by mzh_fu 2007/06/01 提示准确错误信息
		           // throw new IRollbackException(null,"Gen_E001");
					throw new IRollbackException(ctx,e);
				}
			}
			//如果该交易有收款方，且收款方为内部账户，则校验收款方账户状态
			if (transInfo.getReserveaccountid() > 0)
			{
				AccountBean accountBean = new AccountBean();
				try {
					accountBean.validateAccountStatus(transInfo.getReserveaccountid(),AccountBean.TRANSTYPE_DEPOSIT);
				} catch (IException e) {
					e.printStackTrace();
					// 提示“系统忙”
					//modified by mzh_fu 2007/06/01 提示准确错误信息
		           // throw new IRollbackException(null,"Gen_E001");
					throw new IRollbackException(ctx,e);
				}
			}
	
			//如果该交易有付款方，且付款方为内部账户，则调用“活期支取”的方法
			if (transInfo.getBakaccountid() > 0)
			{
				//得到参数处理类
				tadi = this.transferTransBakReserveInfoToTransAccountDetailInfo(transInfo, 1);
	            
	           paySubAccountID = accountOperation.withdrawCurrent(tadi);
	     
			}
			//如果该交易有收款方，且收款方为内部账户，则调用“活期存入”的方法
			if (transInfo.getReserveaccountid() > 0)
			{
				//得到参数处理类
				tadi = this.transferTransBakReserveInfoToTransAccountDetailInfo(transInfo, 2);
	                receiveSubAccountID = accountOperation.depositCurrent(tadi);
			}
				
		/**
		 * 产生会计分录:分录类型lEntryType =0 无关，lAccountID1=收款方账户，lAccountID2=付款方账户， dAmount1=发生额
		 */
		GenerateGLEntryParam param = new GenerateGLEntryParam();
		/**
		 * 如果收/付款银行账户ID>0，则本金 流向lPrincipalType =2银行，否则本金流向lPrincipalType =1 内部转账
		 */
		long lPrincipalType = -1;
		if (transInfo.getPayorreceivetype() == 2)
		{
			//本金流向是 银行
			lPrincipalType = SETTConstant.CapitalType.BANK;
		}
		else if(transInfo.getPayorreceivetype() == 1)
		{
			//本金流向是 内部转账
			lPrincipalType = SETTConstant.CapitalType.INTERNAL;
		}
		//分录类型 无关
		long lEntryType = SETTConstant.EntryType.IRRESPECTIVE;
		//收款方账户
		long receiveAccountID = receiveSubAccountID;
		//付款方账户
		long payAccountID = paySubAccountID;
		//本金开户行ID
		long principalBankID = transInfo.getBankid();
		//发生额
		double dAmount = transInfo.getAmount();
		param.setOfficeID(transInfo.getNofficeid());
		param.setCurrencyID(transInfo.getNcurrencyid());
		param.setTransactionTypeID(transInfo.getNtransactiontypeid());
		param.setExecuteDate(transInfo.getDtexecute());
		param.setInterestStartDate(transInfo.getDtintereststart());
		param.setTransNo(transInfo.getStransno());
		param.setAbstractStr(transInfo.getSabstract());
		param.setInputUserID(transInfo.getNinputuserid());
		param.setCheckUserID(transInfo.getNcheckuserid());
		
		//资金流向
		param.setPrincipalType(lPrincipalType);
		
		
		param.setEntryType(lEntryType);
		param.setReceiveAccountID(receiveAccountID);
		param.setPayAccountID(payAccountID);
		param.setPrincipalOrTransAmount(dAmount);
		param.setPrincipalBankID(principalBankID);
		
		param.setSubTransactionType(SETTConstant.SubTransactionType.NORMAL);
	
		boolean res = glopOperation.generateGLEntry(param);
		if (!res)
		{
			throw new IRollbackException(ctx, "产生会计分录错误2");
		}
	}
	
	private TransAccountDetailInfo transferTransBakReserveInfoToTransAccountDetailInfo(TransBakReserveInfo info, long lTypeID)
	{
		TransAccountDetailInfo tadi = null;
		if (info != null)
		{
			tadi = new TransAccountDetailInfo();

			tadi.setAbstractStr(info.getSabstract());
			tadi.setAmount(info.getAmount());
			tadi.setCurrencyID(info.getNcurrencyid());
			tadi.setDtExecute(info.getDtexecute());
			tadi.setDtInterestStart(info.getDtintereststart());
			tadi.setOfficeID(info.getNofficeid());
			tadi.setTransNo(info.getStransno());
			tadi.setTransactionTypeID(info.getNtransactiontypeid());
			tadi.setStatusID(info.getNstatusid());
			tadi.setAbstractID(info.getNabstractid());

			//账户金额查询区分金额类型增加
			tadi.setAmountType(SETTConstant.AmountType.AmountType_1);
			// 
			if(info.getNtransactiontypeid()==SETTConstant.TransactionType.BAKUPRECEIVE)
			{
				if (lTypeID == 1)
				{
					//付款方账户操作时使用
					tadi.setTransDirection(SETTConstant.ReceiveOrPay.PAY);
					tadi.setTransAccountID(info.getReserveaccountid());
					tadi.setTransSubAccountID(-1); //活期子账户不用设
					tadi.setOppAccountID(info.getBakaccountid());
					tadi.setOppSubAccountID(-1); //活期子账户不用设
				}
				else if (lTypeID == 2)
				{
					//收款方账户操作时使用
					tadi.setTransDirection(SETTConstant.ReceiveOrPay.RECEIVE);
					tadi.setTransAccountID(info.getBakaccountid());
					tadi.setOppAccountID(info.getReserveaccountid());
				}
			}
			else if(info.getNtransactiontypeid()==SETTConstant.TransactionType.BAKRETURN)
			{
				if (lTypeID == 1)
				{
					//付款方账户操作时使用
					tadi.setTransDirection(SETTConstant.ReceiveOrPay.PAY);
					tadi.setTransAccountID(info.getBakaccountid());
					tadi.setTransSubAccountID(-1); //活期子账户不用设
					tadi.setOppAccountID(info.getReserveaccountid());
					tadi.setOppSubAccountID(-1); //活期子账户不用设
				}
				else if (lTypeID == 2)
				{
					//收款方账户操作时使用
					tadi.setTransDirection(SETTConstant.ReceiveOrPay.RECEIVE);
					tadi.setTransAccountID(info.getReserveaccountid());
					tadi.setOppAccountID(info.getBakaccountid());
				}
			}
			
		}
		return tadi;
	}
	
	public void unCheckBakReserveDetailsUpReceive(TransBakReserveInfo transInfo) throws RemoteException, IRollbackException
	{
		
		GeneralLedgerOperation glopOperation = new GeneralLedgerOperation();
		//总账接口操作类
			TransAccountDetailInfo tadi = null;

			log.info("付款方账户（准备金账户）:" + transInfo.getReserveaccountid());
			log.info("收款方账户（备付金账户）:" + transInfo.getBakaccountid());
			
				//如果该交易有付款方，且付款方为内部账户，则调用“活期支取反交易”的方法
				if (transInfo.getReserveaccountid() > 0)
				{
					//得到参数处理类
					tadi = transferTransBakReserveInfoToTransAccountDetailInfo(transInfo, 1);
	                
	                accountOperation.cancelWithdrawCurrent(tadi);
	          
				}
				//如果该交易有收款方，且收款方为内部账户，则调用“活期存入反交易”的方法
				if (transInfo.getBakaccountid() > 0)
				{
					//得到参数处理类
					tadi = this.transferTransBakReserveInfoToTransAccountDetailInfo(transInfo, 2);
	                
	                accountOperation.cancelDepositCurrent(tadi);
				}
				
				//删除明细账
				accountOperation.deleteTransAccountDetail(transInfo.getStransno());
				//删除会计分录
				glopOperation.deleteGLEntry(transInfo.getStransno());

	}
	
	public void unCheckBakReserveDetailsDownReturn(TransBakReserveInfo transInfo) throws RemoteException, IRollbackException
	{
		
		GeneralLedgerOperation glopOperation = new GeneralLedgerOperation();
		//总账接口操作类
			TransAccountDetailInfo tadi = null;

			log.info("付款方账户（备付金账户）:" + transInfo.getBakaccountid());
			log.info("收款方账户（准备金账户）:" + transInfo.getReserveaccountid());
			
				//如果该交易有付款方，且付款方为内部账户，则调用“活期支取反交易”的方法
				if (transInfo.getBakaccountid() > 0)
				{
					//得到参数处理类
					tadi = transferTransBakReserveInfoToTransAccountDetailInfo(transInfo, 1);
	                
	                accountOperation.cancelWithdrawCurrent(tadi);
	          
				}
				//如果该交易有收款方，且收款方为内部账户，则调用“活期存入反交易”的方法
				if (transInfo.getReserveaccountid() > 0)
				{
					//得到参数处理类
					tadi = this.transferTransBakReserveInfoToTransAccountDetailInfo(transInfo, 2);
	                
	                accountOperation.cancelDepositCurrent(tadi);
				}
				
				//删除明细账
				accountOperation.deleteTransAccountDetail(transInfo.getStransno());
				//删除会计分录
				glopOperation.deleteGLEntry(transInfo.getStransno());
	}

	
	/**
	 * add by 王振 2011-07-20
	 * 准备金上收-保存
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveReserveAccountDetailsUpReceive(TransReserveInfo transInfo) throws RemoteException, IRollbackException
	{
		
		log.info(" ------开始保存备付金，准备金账户财务交易--------");


		log.info("付款方账户（备付金账户）:" + transInfo.getBakaccountid());
		log.info("收款方账户（准备金账户）:" + transInfo.getReserveaccountid());

		//如果该交易有付款方，且付款方为内部账户，则校验付款方账户状态
		if (transInfo.getBakaccountid() > 0)
		{
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(transInfo.getBakaccountid(),AccountBean.TRANSTYPE_WITHDRAW);
			} catch (IException e) {
				e.printStackTrace();
				throw new IRollbackException(ctx,e);
			}
		}
		//如果该交易有收款方，且收款方为内部账户，则校验收款方账户状态
		if (transInfo.getReserveaccountid() > 0)
		{
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(transInfo.getReserveaccountid(),AccountBean.TRANSTYPE_DEPOSIT);
			} catch (IException e) {
				e.printStackTrace();
				throw new IRollbackException(ctx,e);
			}
		}
		//如果该交易有付款方，且付款方为内部账户，则调用“累计未复核金额”的方法
		if (transInfo.getBakaccountid() > 0)
		{
			boolean isLackBalance= accountOperation.isLackBalanceToDraft(transInfo.getBakaccountid(), transInfo.getAmount());
			
			if(!isLackBalance)
			{
	            log.info(" ------这笔交易的交易类型为： " + SETTConstant.TransactionType.getName(transInfo.getNtransactiontypeid()) + " 调用“累计未复核金额”的方法！ ");
	            accountOperation.addCurrentUncheckAmount(transInfo.getBakaccountid(), transInfo.getReserveaccountid(), transInfo.getAmount());
				
			}
			else
			{
				throw new IRollbackException(ctx,"备付金金余额不足,不能上收到准备金账户.");
				
			}
				
				

		}
		log.info(" ------结束保存备付金，准备金账户 财务交易--------");
	}
	
	/**
	 * add by 王振 2011-07-20
	 * 准备金调回-保存
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveReserveAccountDetailsDownReturn(TransReserveInfo transInfo) throws RemoteException, IRollbackException
	{
		
		log.info(" ------开始保存备付金，准备金账户财务交易--------");


		log.info("付款方账户（准备金账户）:" + transInfo.getReserveaccountid());
		log.info("收款方账户（备付金账户）:" + transInfo.getBakaccountid());

		//如果该交易有付款方，且付款方为内部账户，则校验付款方账户状态
		if (transInfo.getReserveaccountid() > 0)
		{
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(transInfo.getReserveaccountid(),AccountBean.TRANSTYPE_WITHDRAW);
			} catch (IException e) {
				e.printStackTrace();
				throw new IRollbackException(ctx,e);
			}
		}
		//如果该交易有收款方，且收款方为内部账户，则校验收款方账户状态
		if (transInfo.getBakaccountid() > 0)
		{
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(transInfo.getBakaccountid(),AccountBean.TRANSTYPE_DEPOSIT);
			} catch (IException e) {
				e.printStackTrace();
				throw new IRollbackException(ctx,e);
			}
		}
		//如果该交易有付款方，且付款方为内部账户，则调用“累计未复核金额”的方法
		if (transInfo.getReserveaccountid() > 0)
		{
			boolean isLackBalance= accountOperation.isLackBalanceToDraft(transInfo.getReserveaccountid(), transInfo.getAmount());
			
			if(!isLackBalance)
			{
				log.info(" ------这笔交易的交易类型为： " + SETTConstant.TransactionType.getName(transInfo.getNtransactiontypeid()) + " 调用“累计未复核金额”的方法！ ");
	            accountOperation.addCurrentUncheckAmount(transInfo.getReserveaccountid(), transInfo.getBakaccountid(), transInfo.getAmount());
				
			}
			else
			{
				throw new IRollbackException(ctx,"准备金余额不足,不能调回.");
				
			}
}
		log.info(" ------结束保存备付金，准备金账户 财务交易--------");
	}
	
	/**
	 * add by 王振 2011-07-20
	 * 准备金上收-删除
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void deleteReserveAccountDetailsUpReceive(TransReserveInfo transInfo) throws RemoteException, IRollbackException
	{

		log.info("付款方账户（备付金账户）:" + transInfo.getBakaccountid());
		log.info("收款方账户（准备金账户）:" + transInfo.getReserveaccountid());


		//如果该交易有付款方，且付款方为内部账户，则调用“累计未复核金额”的方法
		if (transInfo.getBakaccountid() > 0)
		{
                log.info(" ------这笔交易的交易类型为： " + SETTConstant.TransactionType.getName(transInfo.getNtransactiontypeid()) + " 调用“累计未复核金额”的方法！ ");

                accountOperation.subtractCurrentUncheckAmount(transInfo.getBakaccountid(), transInfo.getAmount());
		}
		log.info(" ------结束保存备付金，准备金账户 财务交易--------");
	}
	
	/**
	 * add by 王振 2011-07-20
	 * 准备金调回-删除
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void deleteReserveAccountDetailsDownReturn(TransReserveInfo transInfo) throws RemoteException, IRollbackException
	{

		log.info("付款方账户（准备金金账户）:" + transInfo.getReserveaccountid());
		log.info("收款方账户（备付金账户）:" + transInfo.getBakaccountid());


		//如果该交易有付款方，且付款方为内部账户，则调用“累计未复核金额”的方法
		if (transInfo.getReserveaccountid() > 0)
		{
                log.info(" ------这笔交易的交易类型为： " + SETTConstant.TransactionType.getName(transInfo.getNtransactiontypeid()) + " 调用“累计未复核金额”的方法！ ");

                accountOperation.subtractCurrentUncheckAmount(transInfo.getReserveaccountid(), transInfo.getAmount());
		}
		log.info(" ------结束保存备付金，准备金账户 财务交易--------");
	}
	/**
	 * add by 王振
	 * 2011-7-20
	 * 准备金上收-复核
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void checkReserveDetailsUpReceive(TransReserveInfo transInfo) throws RemoteException, IRollbackException
	{
		long paySubAccountID = -1;
		long receiveSubAccountID = -1;

		GeneralLedgerOperation glopOperation = new GeneralLedgerOperation();
		//总账接口操作类
		TransAccountDetailInfo tadi = null;
		
		log.info("付款方账户（备付金账户）:" + transInfo.getBakaccountid());
		log.info("收款方账户（准备金账户）:" + transInfo.getReserveaccountid());
		
		//如果该交易有付款方，且付款方为内部账户，则校验付款方账户状态
		if (transInfo.getBakaccountid() > 0)
		{
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(transInfo.getBakaccountid(),AccountBean.TRANSTYPE_WITHDRAW);
			} catch (IException e) {
				e.printStackTrace();
				throw new IRollbackException(ctx,e);
			}
		}
		//如果该交易有收款方，且收款方为内部账户，则校验收款方账户状态
		if (transInfo.getReserveaccountid() > 0)
		{
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(transInfo.getReserveaccountid(),AccountBean.TRANSTYPE_DEPOSIT);
			} catch (IException e) {
				e.printStackTrace();
				throw new IRollbackException(ctx,e);
			}
		}

		//如果该交易有付款方，且付款方为内部账户，则调用“活期支取”的方法
		if (transInfo.getBakaccountid() > 0)
		{
			//得到参数处理类
			tadi = this.transferTransReserveInfoToTransAccountDetailInfo(transInfo, 1);
            
           paySubAccountID = accountOperation.withdrawCurrent(tadi);
     
		}
		//如果该交易有收款方，且收款方为内部账户，则调用“活期存入”的方法
		if (transInfo.getReserveaccountid() > 0)
		{
			//得到参数处理类
			tadi = this.transferTransReserveInfoToTransAccountDetailInfo(transInfo, 2);
                receiveSubAccountID = accountOperation.depositCurrent(tadi);
		}
			
		/**
		 * 产生会计分录:分录类型lEntryType =0 无关，lAccountID1=收款方账户，lAccountID2=付款方账户， dAmount1=发生额
		 */
		GenerateGLEntryParam param = new GenerateGLEntryParam();
		/**
		 * 如果收/付款银行账户ID>0，则本金 流向lPrincipalType =2银行，否则本金流向lPrincipalType =1 内部转账
		 */
		long lPrincipalType = -1;
		if (transInfo.getPayorreceivetype() == 2)
		{
			//本金流向是 银行
			lPrincipalType = SETTConstant.CapitalType.BANK;
		}
		else if(transInfo.getPayorreceivetype() == 1)
		{
			//本金流向是 内部转账
			lPrincipalType = SETTConstant.CapitalType.INTERNAL;
		}
		//分录类型 无关
		long lEntryType = SETTConstant.EntryType.IRRESPECTIVE;
		//收款方账户
		long receiveAccountID = receiveSubAccountID;
		//付款方账户
		long payAccountID = paySubAccountID;
		//本金开户行ID
		long principalBankID = transInfo.getBankid();
		//发生额
		double dAmount = transInfo.getAmount();
		param.setOfficeID(transInfo.getNofficeid());
		param.setCurrencyID(transInfo.getNcurrencyid());
		param.setTransactionTypeID(transInfo.getNtransactiontypeid());
		param.setExecuteDate(transInfo.getDtexecute());
		param.setInterestStartDate(transInfo.getDtintereststart());
		param.setTransNo(transInfo.getStransno());
		param.setAbstractStr(transInfo.getSabstract());
		param.setInputUserID(transInfo.getNinputuserid());
		param.setCheckUserID(transInfo.getNcheckuserid());
		//资金流向
		param.setPrincipalType(lPrincipalType);
		param.setEntryType(lEntryType);
		param.setReceiveAccountID(receiveAccountID);
		param.setPayAccountID(payAccountID);
		param.setPrincipalOrTransAmount(dAmount);
		param.setPrincipalBankID(principalBankID);
		param.setSubTransactionType(SETTConstant.SubTransactionType.NORMAL);
	
		boolean res = glopOperation.generateGLEntry(param);
		if (!res)
		{
			throw new IRollbackException(ctx, "产生会计分录错误2");
		}
	}
	/**
	 * add by 王振
	 * 2011-7-20
	 * 准备金调回-复核
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void checkReserveDetailsDownReturn(TransReserveInfo transInfo) throws RemoteException, IRollbackException
	{
		long paySubAccountID = -1;
		long receiveSubAccountID = -1;

		GeneralLedgerOperation glopOperation = new GeneralLedgerOperation();
		//总账接口操作类
		TransAccountDetailInfo tadi = null;
		
		log.info("付款方账户（准备金账户）:" + transInfo.getReserveaccountid());
		log.info("收款方账户（备付金账户）:" + transInfo.getBakaccountid());
		
		//如果该交易有付款方，且付款方为内部账户，则校验付款方账户状态
		if (transInfo.getReserveaccountid() > 0)
		{
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(transInfo.getReserveaccountid(),AccountBean.TRANSTYPE_WITHDRAW);
			} catch (IException e) {
				e.printStackTrace();
				throw new IRollbackException(ctx,e);
			}
		}
		//如果该交易有收款方，且收款方为内部账户，则校验收款方账户状态
		if (transInfo.getBakaccountid() > 0)
		{
			AccountBean accountBean = new AccountBean();
			try {
				accountBean.validateAccountStatus(transInfo.getBakaccountid(),AccountBean.TRANSTYPE_DEPOSIT);
			} catch (IException e) {
				e.printStackTrace();
				throw new IRollbackException(ctx,e);
			}
		}

		//如果该交易有付款方，且付款方为内部账户，则调用“活期支取”的方法
		if (transInfo.getReserveaccountid() > 0)
		{
			//得到参数处理类
			tadi = this.transferTransReserveInfoToTransAccountDetailInfo(transInfo, 1);
            
           paySubAccountID = accountOperation.withdrawCurrent(tadi);
     
		}
		//如果该交易有收款方，且收款方为内部账户，则调用“活期存入”的方法
		if (transInfo.getBakaccountid() > 0)
		{
			//得到参数处理类
			tadi = this.transferTransReserveInfoToTransAccountDetailInfo(transInfo, 2);
                receiveSubAccountID = accountOperation.depositCurrent(tadi);
		}
			
		/**
		 * 产生会计分录:分录类型lEntryType =0 无关，lAccountID1=收款方账户，lAccountID2=付款方账户， dAmount1=发生额
		 */
		GenerateGLEntryParam param = new GenerateGLEntryParam();
		/**
		 * 如果收/付款银行账户ID>0，则本金 流向lPrincipalType =2银行，否则本金流向lPrincipalType =1 内部转账
		 */
		long lPrincipalType = -1;
		if (transInfo.getPayorreceivetype() == 2)
		{
			//本金流向是 银行
			lPrincipalType = SETTConstant.CapitalType.BANK;
		}
		else if(transInfo.getPayorreceivetype() == 1)
		{
			//本金流向是 内部转账
			lPrincipalType = SETTConstant.CapitalType.INTERNAL;
		}
		//分录类型 无关
		long lEntryType = SETTConstant.EntryType.IRRESPECTIVE;
		//收款方账户
		long receiveAccountID = receiveSubAccountID;
		//付款方账户
		long payAccountID = paySubAccountID;
		//本金开户行ID
		long principalBankID = transInfo.getBankid();
		//发生额
		double dAmount = transInfo.getAmount();
		param.setOfficeID(transInfo.getNofficeid());
		param.setCurrencyID(transInfo.getNcurrencyid());
		param.setTransactionTypeID(transInfo.getNtransactiontypeid());
		param.setExecuteDate(transInfo.getDtexecute());
		param.setInterestStartDate(transInfo.getDtintereststart());
		param.setTransNo(transInfo.getStransno());
		param.setAbstractStr(transInfo.getSabstract());
		param.setInputUserID(transInfo.getNinputuserid());
		param.setCheckUserID(transInfo.getNcheckuserid());
		//资金流向
		param.setPrincipalType(lPrincipalType);
		param.setEntryType(lEntryType);
		param.setReceiveAccountID(receiveAccountID);
		param.setPayAccountID(payAccountID);
		param.setPrincipalOrTransAmount(dAmount);
		param.setPrincipalBankID(principalBankID);
		param.setSubTransactionType(SETTConstant.SubTransactionType.NORMAL);
	
		boolean res = glopOperation.generateGLEntry(param);
		if (!res)
		{
			throw new IRollbackException(ctx, "产生会计分录错误2");
		}
	}
	private TransAccountDetailInfo transferTransReserveInfoToTransAccountDetailInfo(TransReserveInfo info, long lTypeID)
	{
		TransAccountDetailInfo tadi = null;
		if (info != null)
		{
			tadi = new TransAccountDetailInfo();
			tadi.setId(info.getId()); //OTO:待确定
			tadi.setAbstractStr(info.getSabstract());
			tadi.setAmount(info.getAmount());
			//tadi.setBankChequeNo(info.getBankChequeNo());   银行支票号
		//	tadi.setBillTypeID(info.getBillTypeID());         票据类型
		    //tadi.setBillNo(info.getBillNo());               票据号
			tadi.setCurrencyID(info.getNcurrencyid());
			tadi.setDtExecute(info.getDtexecute());
			tadi.setDtInterestStart(info.getDtintereststart());
			tadi.setOfficeID(info.getNofficeid());
			tadi.setTransNo(info.getStransno());
			tadi.setTransactionTypeID(info.getNtransactiontypeid());
			tadi.setStatusID(info.getNstatusid());
			tadi.setAbstractID(info.getNabstractid());
			tadi.setGroup(-1); //TOTO:待确定
			//账户金额查询区分金额类型增加
			tadi.setAmountType(SETTConstant.AmountType.AmountType_1);

			if(info.getNtransactiontypeid()==SETTConstant.TransactionType.RESERVEUPRECEIVE)
			{
				if (lTypeID == 1)
				{
					//付款方账户操作时使用
					tadi.setTransDirection(SETTConstant.ReceiveOrPay.PAY);
					tadi.setTransAccountID(info.getBakaccountid());
					tadi.setTransSubAccountID(-1); //活期子账户不用设
					tadi.setOppAccountID(info.getReserveaccountid());
					tadi.setOppSubAccountID(-1); //活期子账户不用设
				}
				else if (lTypeID == 2)
				{
					//收款方账户操作时使用
					tadi.setTransDirection(SETTConstant.ReceiveOrPay.RECEIVE);
					tadi.setTransAccountID(info.getReserveaccountid());
					tadi.setOppAccountID(info.getBakaccountid());
				}
			}
			else if(info.getNtransactiontypeid()==SETTConstant.TransactionType.RESERVERETURN)
			{
				
				if (lTypeID == 1)
				{
					//付款方账户操作时使用
					tadi.setTransDirection(SETTConstant.ReceiveOrPay.PAY);
					tadi.setTransAccountID(info.getReserveaccountid());
					tadi.setTransSubAccountID(-1); //活期子账户不用设
					tadi.setOppAccountID(info.getBakaccountid());
					tadi.setOppSubAccountID(-1); //活期子账户不用设
				}
				else if (lTypeID == 2)
				{
					//收款方账户操作时使用
					tadi.setTransDirection(SETTConstant.ReceiveOrPay.RECEIVE);
					tadi.setTransAccountID(info.getBakaccountid());
					tadi.setOppAccountID(info.getReserveaccountid());
				}
			}
		}
		return tadi;
	}
	
	public void unCheckReserveDetailsUpReceive(TransReserveInfo transInfo) throws RemoteException, IRollbackException
	{
		GeneralLedgerOperation glopOperation = new GeneralLedgerOperation();
		//总账接口操作类
			TransAccountDetailInfo tadi = null;
			
			log.info("付款方账户（备付金账户）:" + transInfo.getBakaccountid());
			log.info("收款方账户（准备金账户）:" + transInfo.getReserveaccountid());
			
			
			//如果该交易有付款方，且付款方为内部账户，则调用“活期支取反交易”的方法
			if (transInfo.getBakaccountid() > 0)
			{
				//得到参数处理类
				tadi = transferTransReserveInfoToTransAccountDetailInfo(transInfo, 1);
                accountOperation.cancelWithdrawCurrent(tadi);
			}
			//如果该交易有收款方，且收款方为内部账户，则调用“活期存入反交易”的方法
			if (transInfo.getReserveaccountid() > 0)
			{
				//得到参数处理类
				tadi = this.transferTransReserveInfoToTransAccountDetailInfo(transInfo, 2);
                accountOperation.cancelDepositCurrent(tadi);
			}
			//删除明细账
			accountOperation.deleteTransAccountDetail(transInfo.getStransno());
			//删除会计分录
			glopOperation.deleteGLEntry(transInfo.getStransno());
	}
	
	public void unCheckReserveDetailsDownReturn(TransReserveInfo transInfo) throws RemoteException, IRollbackException
	{
		GeneralLedgerOperation glopOperation = new GeneralLedgerOperation();
		//总账接口操作类
			TransAccountDetailInfo tadi = null;
			
			log.info("付款方账户（准备金账户）:" + transInfo.getReserveaccountid());
			log.info("收款方账户（备付金账户）:" + transInfo.getBakaccountid());
			
			//如果该交易有付款方，且付款方为内部账户，则调用“活期支取反交易”的方法
			if (transInfo.getReserveaccountid() > 0)
			{
				//得到参数处理类
				tadi = transferTransReserveInfoToTransAccountDetailInfo(transInfo, 1);
                accountOperation.cancelWithdrawCurrent(tadi);
			}
			//如果该交易有收款方，且收款方为内部账户，则调用“活期存入反交易”的方法
			if (transInfo.getBakaccountid() > 0)
			{
				//得到参数处理类
				tadi = this.transferTransReserveInfoToTransAccountDetailInfo(transInfo, 2);
                accountOperation.cancelDepositCurrent(tadi);
			}
			//删除明细账
			accountOperation.deleteTransAccountDetail(transInfo.getStransno());
			//删除会计分录
			glopOperation.deleteGLEntry(transInfo.getStransno());
	}
    
	/**商业票据承兑-到期承兑交易保存*/
	public void saveAcceptanceNoteAcceptance(TransAcceptanceNoteAcceptanceInfo info)throws RemoteException, IRollbackException{
		long lReturn = 1;
		//帐户接口操作类
		
		//若到期承兑交易的付款方为活期帐户			
		if (info.getAcceptancePayAccountID() > 0)     
		{
			log.debug("---------        (商业票据承兑-到期承兑：   保存操作）-----------");
			if(info.getAcceptanceAmount()>0){
				accountOperation.addCurrentUncheckAmount(info.getAcceptancePayAccountID(),info.getAcceptanceReceiveAccountID(), info.getAcceptanceAmount());
			}
			log.debug("---------累计未复核金额完成-----------");
		}
		log.debug("---------完成saveAcceptanceNoteAcceptance-----------");
	}
	
	/**商业票据承兑-到期承兑交易删除*/
	public void deleteAcceptanceNoteAcceptance(TransAcceptanceNoteAcceptanceInfo info)throws RemoteException, IRollbackException{
		//若到期承兑交易的付款方为活期帐户			
		if (info.getAcceptancePayAccountID() > 0)
		{
			log.debug("---------        (商业票据承兑-到期承兑：   保存操作）-----------");
			log.debug("---------活期子账户大于0,存款来源为活期存款，开始扣除累计未复核金额-----------");
			accountOperation.subtractCurrentUncheckAmount(info.getAcceptancePayAccountID(),info.getAcceptanceAmount());
			log.debug("---------结束扣除累计未复核金额-----------");
		}
		log.debug("---------deleteAcceptanceNoteAcceptance-----------");
	}
	
	/**商业票据承兑-到期承兑交易复核*/
	public void checkAcceptanceNoteAcceptance(TransAcceptanceNoteAcceptanceInfo transInfo)throws RemoteException, IRollbackException{
		long currentSubAccountID = -1;				//付保证金的活期子帐户ID
		long receiveSubAccountID = -1;				//收款方帐户号
		TransAccountDetailInfo currentTadi = null;		//活期帐户明细
		Sett_SubAccountDAO  sett_SubAccountDAO = new Sett_SubAccountDAO();
		log.debug("---------checkAcceptanceNoteAcceptance-----------");
		if (transInfo.getAcceptancePayAccountID() > 0)
		{
			log.debug("----到期承兑-----活期子账户大于0,存款来源为活期存款，开始从活期账户余额中扣钱：withdrawCurrent-----------");
			if(transInfo.getAcceptanceAmount()>0){
				currentTadi = transferTransAcceptanceNoteAcceptanceInfoToTransAccountDetailInfo(transInfo, TRANS_CURRENT);
				currentSubAccountID = accountOperation.withdrawCurrent(currentTadi);
			}
			log.debug("---------结束从活期账户余额中扣钱：withdrawCurrent-----------");
		}
		System.out.println("--------到期承兑的活期子帐户是currentSubAccountID："+currentSubAccountID);
		
		//add by dwj 20091026
		//如果该交易有收款方，且收款方为内部帐户，则调用“活期存入”的方法
		if (transInfo.getAcceptanceReceiveAccountID() > 0)
		{
			//得到参数处理类
			currentTadi = transferTransAcceptanceNoteAcceptanceInfoToTransAccountDetailInfo(transInfo, 2);
            
            // 如果是 银行付款－拨子账户 或 银行收款－转成员单位收款 ，则账户余额不发生变化；
            // 这两种交易类型只有南航财务的项目有，其他项目不受影响。 ---2005.9.23 方桂全
            if( transInfo.getTransactionTypeID() != SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT  &&
                transInfo.getTransactionTypeID() != SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT    )
            {
                receiveSubAccountID = accountOperation.depositCurrent(currentTadi);
            }
            else
            {
                //得到子账户ID
                AccountBean accountBean = new AccountBean();
                try 
                {
                    receiveSubAccountID = accountBean.getCurrentSubAccoutIDByAccoutID( transInfo.getAcceptanceReceiveAccountID());
                }
                catch ( IException e ) 
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
		}
		//end add by dwj 20091026
		
		/**
		 * 产生会计分录:分录类型lEntryType =0 无关，lAccountID1=收款方帐户，lAccountID2=付款方帐户， dAmount1=发生额
		 */

		log.debug("-------开始产生会计分录----------");
		GenerateGLEntryParam param = new GenerateGLEntryParam();
		/**
		 * 如果收/付款银行帐户ID>0，则本金 流向lPrincipalType =2银行，否则本金流向lPrincipalType =1 内部转帐
		 */
		long lPrincipalType = -1;	//本金流向			1:内部转帐   2:银行
		if (transInfo.getAcceptancePayBankID() > 0)
		{
			//本金流向是 银行
			lPrincipalType = SETTConstant.CapitalType.BANK;
		}
		else
		{
			//本金流向是 内部转账
			lPrincipalType = SETTConstant.CapitalType.INTERNAL;
		}
		
		System.out.println("......本金流向............  lPrincipalType = "+lPrincipalType);
		
		//分录类型
		//long lEntryType = SETTConstant.EntryType.IRRESPECTIVE;  //设置成无关 在页面上可以设置成无关和 
		long lEntryType = SETTConstant.EntryType.DIVIDE;	//分拆  2006.5.12 add by feiye
		
		//收款方账户(到期承兑)
		//update by dwj 20091026
		//long receiveAccountID = transInfo.getAcceptanceReceiveAccountID();
		long receiveAccountID = receiveSubAccountID;
		//end update by dwj 20091026
		System.out.println(".................. 收款方账户(到期承兑) : "+receiveAccountID);
		
		//付款方账户（到期承兑）
		long payAccountID = currentSubAccountID;
		System.out.println(".................. 付款方账户(到期承兑) : "+payAccountID);
		
		//到期承兑金额
		double dAmount = transInfo.getAcceptanceAmount();
		System.out.println(".................. 到期承兑金额 : "+dAmount);
		
		param.setOfficeID(transInfo.getOfficeID());			//办事处
		param.setCurrencyID(transInfo.getCurrencyID());		//币种
		param.setTransactionTypeID(transInfo.getTransactionTypeID());		//交易类型
		param.setExecuteDate(transInfo.getExecuteDate());	//执行日
		param.setInterestStartDate(transInfo.getInterestStartDate());		//起息日
		param.setTransNo(transInfo.getTransNo());		//交易号
		param.setAbstractStr(transInfo.getAbstract());	//摘要
		param.setInputUserID(transInfo.getInputUserID());	//输入人
		param.setCheckUserID(transInfo.getCheckUserID());	//复核人
		
		param.setPrincipalType(lPrincipalType);	//本金流向
		param.setEntryType(lEntryType);					//分录类型
		param.setReceiveAccountID(receiveAccountID);		//收款方帐户
		param.setPayAccountID(payAccountID);				//付款方帐户
		
		param.setPrincipalBankID(transInfo.getAcceptancePayBankID());	//本金开户行
		param.setPrincipalOrTransAmount(dAmount);						//本金/交易金额
		
		boolean res = glopOperation.generateGLEntry(param);
		if (!res)
		{
			throw new IRollbackException(ctx, "借贷不平衡，交易失败");
		}
		log.debug("-------结束产生会计分录----------");
		
		/***********构造银行付款指令**********/
		
		//是否有银企接口
		boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
		//是否需要生成银行指令
		boolean bCreateInstruction = false;
		long bankID = transInfo.getAcceptancePayBankID();//付款开户行ID
		try {
			//调用此方法后，bankID的值变为银行类型ID
			if(bankID>0)
				bCreateInstruction = this.isCreateInstruction(bankID);
		} catch (Exception e1) {				
			log.error("判断传入的银行ID是否需要生成银行指令出错！");
			e1.printStackTrace();
		}
		//end
		if(bIsValid && bCreateInstruction) {//有银企接口并且是需要生成银行指令
			Log.print("*******************开始产生成商业票据承兑-到期承兑指令，并保存**************************");
			try {
				
				//构造参数
				CreateInstructionParam instructionParam = new CreateInstructionParam();
				instructionParam.setTransactionTypeID(transInfo.getTransactionTypeID());
				instructionParam.setObjInfo(transInfo);
				instructionParam.setOfficeID(transInfo.getOfficeID());
				instructionParam.setCurrencyID(transInfo.getCurrencyID());
				instructionParam.setCheckUserID(transInfo.getCheckUserID());
				instructionParam.setBankType(transInfo.getAcceptancePayBankID());
				instructionParam.setInputUserID(transInfo.getInputUserID());
				
				//生成银行指令并保存
				IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
				bankInstruction.createBankInstruction(instructionParam);
				
				log.debug("------生成商业票据承兑-到期承兑指令结束--------");
				
			} catch (Throwable e) {
				log.error("生成商业票据承兑-到期承兑指令失败");
				e.printStackTrace();
				throw new IRollbackException(ctx, "生成商业票据承兑-到期承兑指令失败："+e.getMessage());
			}
		}
		else 
		{
			log.debug("没有银行接口或不需要生成银行指令！");
		}
		
		
		
	}
	
	/**商业票据承兑-到期承兑交易取消复核*/
	public void cancelCheckAcceptanceNoteAcceptance(TransAcceptanceNoteAcceptanceInfo transInfo)throws RemoteException, IRollbackException{
		
		GeneralLedgerOperation glopOperation = new GeneralLedgerOperation();
		//总帐接口操作类
		//如果是一付多收，单独处理
		if (transInfo.getTransactionTypeID() != SETTConstant.TransactionType.ONETOMULTI)
		{
			TransAccountDetailInfo tadi = null;
			//如果该交易有付款方，且付款方为内部帐户，则调用“活期支取反交易”的方法
			if (transInfo.getAcceptancePayAccountID() > 0)
			{
				//得到参数处理类
				tadi = transferTransAcceptanceNoteAcceptanceInfoToTransAccountDetailInfo(transInfo, TRANS_CURRENT);
                
                // 如果是 银行付款－拨子账户 或 银行收款－转成员单位收款 ，则账户余额不发生变化；
                // 这两种交易类型只有南航财务的项目有，其他项目不受影响。 ---2005.9.23 方桂全
                if( transInfo.getTransactionTypeID() != SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT  &&
                    transInfo.getTransactionTypeID() != SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT    )
                {
                    accountOperation.cancelWithdrawCurrent(tadi);
                }
                else
                {
                    System.out.println("-----------交易取消复核，不调用cancelWithdrawCurrent()方法-----------");
                }
			}
			//如果该交易有收款方，且收款方为内部帐户，则调用“活期存入反交易”的方法
			if (transInfo.getAcceptanceReceiveAccountID() > 0)
			{
				//得到参数处理类
				tadi = this.transferTransAcceptanceNoteAcceptanceInfoToTransAccountDetailInfo(transInfo, 2);
                
                // 如果是 银行付款－拨子账户 或 银行收款－转成员单位收款 ，则账户余额不发生变化；
                // 这两种交易类型只有南航财务的项目有，其他项目不受影响。 ---2005.9.23 方桂全
                if( transInfo.getTransactionTypeID() != SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT  &&
                    transInfo.getTransactionTypeID() != SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT    )
                {
                    accountOperation.cancelDepositCurrent(tadi);
                }
                else
                {
                    System.out.println("-----------交易取消复核，不调用cancelDepositCurrent()方法-----------");
                }
			}
			//删除明细帐
			accountOperation.deleteTransAccountDetail(transInfo.getTransNo());
			//TODO:待确定4.通存通兑反交易处理
			//删除会计分录
			glopOperation.deleteGLEntry(transInfo.getTransNo());
		}
		else
		{
			//TODO:待确定
		}
		
		
	}
	
	//商业票据承兑-到期承兑收款帐户明细生成
	private TransAccountDetailInfo transferTransAcceptanceNoteAcceptanceInfoToTransAccountDetailInfo(TransAcceptanceNoteAcceptanceInfo info, int transType)
	{
		TransAccountDetailInfo resInfo = new TransAccountDetailInfo();
		//resInfo.setId(id);
		resInfo.setOfficeID(info.getOfficeID());		//办事处
		resInfo.setCurrencyID(info.getCurrencyID());	//币种
		resInfo.setTransactionTypeID(info.getTransactionTypeID());	//交易类型
		resInfo.setDtExecute(info.getExecuteDate());	//执行日
		resInfo.setTransNo(info.getTransNo());			//交易号
		resInfo.setAmount(info.getAcceptanceAmount());			//收保证金金额
		//交易方向在账户中设置
		//resInfo.setTransDirection();
		resInfo.setDtInterestStart(info.getInterestStartDate());	//保证金起息日
		resInfo.setAbstractStr(info.getAbstract());		//摘要
		resInfo.setAbstractID(info.getAbstractID());
		if (transType == TRANS_CURRENT)
		{
			//活期交易，交易账户为活期账户
			resInfo.setTransAccountID(info.getAcceptancePayAccountID());			//从此帐户扣钱
			//活期交易，对方交易账户为定期账户
			resInfo.setOppAccountID(info.getAcceptanceReceiveAccountID());
			//是否不用设子账户？？
		}
		//add by dwj 20091026
		else if (transType == 2)
		{
//			活期交易，交易账户为活期账户
			resInfo.setTransAccountID(info.getAcceptanceReceiveAccountID());			//从此帐户加钱
			//活期交易，对方交易账户为定期账户
			resInfo.setOppAccountID(info.getAcceptancePayAccountID());
			//是否不用设子账户？？
		}
		//end add by dwj 20091026
		log.debug(UtilOperation.dataentityToString(resInfo));
		return resInfo;
	}
	
	/**
	 * 方正项目 :同业结算处理接口 added by qhzhou 2011-03-28
	 * 同业业务结算处理复核
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void checkTransCraftbrother(TransCraftbrotherInfo transInfo) throws RemoteException, IRollbackException
	{
		log.debug("------开始同业往来结算处理账户、账簿复核--------");
		long lNoticeId = transInfo.getNnoticeId();
		NoticeInfo secNoticeInfo = null;
		long lSecContractId = -1;
		SecuritiesContractInfo secContractInfo = null;
		long lSecApplyId = -1;
		ArrayList attornmentContractList = null;
		ArrayList transDiscountContractBillList = null;
		SEC_NoticeDAO secNoticeDao = null;
		SecuritiesContractDao secContractDao = null;
		AttornmentApplyDao attornmentApplyDao = null;
		
		double dInTotalInterest = 0.00;//转贴现卖出买断专用，统计所有卖出票的买入利息
		//资产转让
		double marginAmount = 0.00;//资产转让买入买断【差额】 = 【转让价格】 - 【转让金额】
		double attornAmount = 0.00;//资产转让【转让金额】
		double attornPrice  = 0.00;//转让价格【转让价格】
		double adjustFee    = 0.00;//资产转让【费用调整】
		double interest     = 0.00;//资产转让【利息合计】
		double sumPrehDrawInterest = 0.00;//放款单已计提利息
		double sumInterest		 = 0.00;//放款单未计提利息
		
		
		/**资产 转让明细----------------------开始------------------------------------------------------ */
		
		if(transInfo.getNcraBusinessTypeId() == CRAconstant.SameBusinessAttribute.SAME_BUSINESS)//资产转让
		{
			try{
				secNoticeDao = new SEC_NoticeDAO();
				secContractDao = new SecuritiesContractDao();
				attornmentApplyDao = new AttornmentApplyDao();
				
				secNoticeInfo = (NoticeInfo)secNoticeDao.findByID(lNoticeId, NoticeInfo.class);
				if(secNoticeInfo != null){
					adjustFee = secNoticeInfo.getAdjustment();//资产转让【费用调整】
					interest  = secNoticeInfo.getNoticeInterest();//资产转让【利息合计】
					lSecContractId = secNoticeInfo.getContractId();
					secContractInfo = (SecuritiesContractInfo)secContractDao.findByID(lSecContractId, SecuritiesContractInfo.class);
				}
				if(secContractInfo != null){
					lSecApplyId = secContractInfo.getApplyId();
					marginAmount = secContractInfo.getMargin();//资产转让买入买断【差额】 = 【转让价格】 - 【转让金额】
					attornAmount = secContractInfo.getAmount();//资产转让【转让金额】
					attornPrice  = secContractInfo.getPrice(); //资产转让【转让价格】
				}else{
					throw new IRollbackException(ctx, "操作失败,找不到对应的资产转让合同 ");
				}
				if(transInfo.getNtransactionTypeId() == SETTConstant.TransactionType.SAME_BUSINESS_GRANT ||      
						transInfo.getNtransactionTypeId() == SETTConstant.TransactionType.SAME_BUSINESS_RECEIVE){
					//取得自营贷款转让明细
					attornmentContractList = (ArrayList)attornmentApplyDao.findContractByRepurchaseID(lSecApplyId); 
				}
			}catch (Exception e)
			{
				throw new IRollbackException(ctx, e.getMessage(), e);
			}
		}
		/**资产 转让明细----------------------结束------------------------------------------------------ */
		
		log.debug("------开始同业往来结算处理账户复核、记录账户交易明细--------");
		log.debug(UtilOperation.dataentityToString(transInfo));
		
		if (transInfo.getNtransactionTypeId() == SETTConstant.TransactionType.TRANS_DISCOUNT_GRANT  &&
			transInfo.getNsubTransactionTypeId() == SETTConstant.SubTransactionType.BREAK_NOTIFY) {//转贴现卖出买断发放

			log.debug("------开始记录转贴现卖出买断账户交易明细--------");
			long lTransDiscountCredenceID = transInfo.getNnoticeId();//转贴现凭证
			try{
				TransDiscountCredenceDAO tdDao = new TransDiscountCredenceDAO("LOAN_DISCOUNTCREDENCE");
				Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO();
				
				Collection cBills = tdDao.findBillByTransDiscountCredenceID(lTransDiscountCredenceID);
				
				TransDiscountContractBillInfo cbInfo = null;
				TransRepaymentDiscountInfo transRepaymentDiscountInfo = null;	
				TransAccountDetailInfo tadi = null;
				
				if(cBills != null && !cBills.isEmpty()){
					transDiscountContractBillList = new ArrayList();
					
					long lBillTmpId = -1;
					long lLoanPayTmpId = -1;
					long lLoanAccountTmpId = -1; 
					
					//票据贴现发放信息
					TransGrantDiscountInfo transGrantDiscountInfo = null;
					Sett_TransGrantDiscountDAO gdDao = new Sett_TransGrantDiscountDAO();
					
					Iterator bit = cBills.iterator();
					while(bit.hasNext()){
						lLoanPayTmpId = -1;
						lLoanAccountTmpId = -1; 
						
						cbInfo = (TransDiscountContractBillInfo)bit.next();
						transDiscountContractBillList.add(cbInfo);
						if(cbInfo != null && cbInfo.getBillSourceTypeID()==LOANConstant.BillSourceType.DISCOUN){
							lBillTmpId = cbInfo.getId();
							lLoanPayTmpId = cbInfo.getDiscountCredenceID();
							
							transGrantDiscountInfo = gdDao.findDiscountInfoByDiscountBillID(lBillTmpId);
							lLoanAccountTmpId = (transGrantDiscountInfo == null ? -1 : transGrantDiscountInfo.getDiscountAccountID());
														
							transRepaymentDiscountInfo = new TransRepaymentDiscountInfo();
							transRepaymentDiscountInfo.setSAbstract(transInfo.getSabstract());
							transRepaymentDiscountInfo.setNOfficeID(transInfo.getNofficeId());
							transRepaymentDiscountInfo.setNCurrencyID(transInfo.getNcurrencyId());
							transRepaymentDiscountInfo.setDtExecute(transInfo.getDtExecute());
							transRepaymentDiscountInfo.setDtInterestStart(transInfo.getDtInterestStart());
							transRepaymentDiscountInfo.setNTransactionTypeID(transInfo.getNtransactionTypeId());
							transRepaymentDiscountInfo.setNAbstractID(-1);
							transRepaymentDiscountInfo.setNDiscountAccountID(lLoanAccountTmpId);
							transRepaymentDiscountInfo.setNDiscountNoteID(lLoanPayTmpId);
							//回收票面金额
							transRepaymentDiscountInfo.setMAmount(cbInfo.getAmount());
							transRepaymentDiscountInfo.setNCurrentAccountID(-1);
							transRepaymentDiscountInfo.setNBankID(transInfo.getNbankId());
							transRepaymentDiscountInfo.setSTransNo(transInfo.getStransNo());
							
							tadi = transferTransRepaymentDiscountInfoToTransAccountDetailInfo(transRepaymentDiscountInfo, RepaymentDiscount_REPAYLOAN);
							accountOperation.repayLoan(tadi);
						}
						dInTotalInterest += cbInfo.getAccrual();
					}
				}else{
					throw new IRollbackException(ctx, "转贴现卖出买断发放交易失败，找不到卖出买断的票据信息！");
				}
			}catch (Exception e)
			{
				throw new IRollbackException(ctx, e.getMessage(), e);
			}
			log.debug("------结束记录转贴现卖出买断账户交易明细--------");
				
		}
		else if(transInfo.getNtransactionTypeId() == SETTConstant.TransactionType.SAME_BUSINESS_GRANT &&
			     transInfo.getNsubTransactionTypeId() == SETTConstant.SubTransactionType.BREAK_NOTIFY){//资产转让卖出买断发放
			marginAmount = 0.00;
			attornAmount = 0.00;
			log.debug("------开始记录资产转让卖出买断账户交易明细--------");
			try{
				if(attornmentContractList != null && attornmentContractList.size() > 0){
					//根据信贷合同转让明细，生成相应的贷款账户交易明细
					Sett_TransGrantLoanDAO sett_TransGrantLoanDAO = new Sett_TransGrantLoanDAO();
					Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO();
					TransGrantLoanInfo tmpGrantLoanInfo = null;
					long lLoanPayTmpId = -1;
					long lLoanAccountTmpId = -1; 
					long lLoanSubAccountTmpId = -1; 
					
					TransRepaymentLoanInfo transRepaymentLoanTmpInfo = null;
					TransAccountDetailInfo tadi = null;
					SubAccountAssemblerInfo tmpSubAccountAssemblerInfo = null;
					SubAccountLoanInfo tmpSubAccountLoanInfo = null;//子账户信息
					
					for(int n=0;n<attornmentContractList.size();n++)
					{   
						lLoanPayTmpId = -1;
						lLoanAccountTmpId = -1; 
						AttornmentContractInfo attornmentContractInfo = (AttornmentContractInfo)attornmentContractList.get(n);
						lLoanPayTmpId = attornmentContractInfo.getPayId();
						tmpGrantLoanInfo = sett_TransGrantLoanDAO.findByLoanNoteID(lLoanPayTmpId);
						lLoanAccountTmpId = (tmpGrantLoanInfo == null ? -1 : tmpGrantLoanInfo.getLoanAccountID());
						
						transRepaymentLoanTmpInfo = new TransRepaymentLoanInfo();
						transRepaymentLoanTmpInfo.setOfficeID(transInfo.getNofficeId());
						transRepaymentLoanTmpInfo.setCurrencyID(transInfo.getNcurrencyId());
						transRepaymentLoanTmpInfo.setTransactionTypeID(transInfo.getNtransactionTypeId());
						transRepaymentLoanTmpInfo.setExecute(transInfo.getDtExecute());
						transRepaymentLoanTmpInfo.setTransNo(transInfo.getStransNo());
						transRepaymentLoanTmpInfo.setAbstractID(-1);
						transRepaymentLoanTmpInfo.setAbstract(transInfo.getSabstract());
						transRepaymentLoanTmpInfo.setAmount(attornmentContractInfo.getAttornmentAmount());
						transRepaymentLoanTmpInfo.setDepositAccountID(-1);//资产转让发放只有开户行
						transRepaymentLoanTmpInfo.setBankID(transInfo.getNbankId());
						transRepaymentLoanTmpInfo.setLoanAccountID(lLoanAccountTmpId);
						transRepaymentLoanTmpInfo.setLoanNoteID(lLoanPayTmpId);
						transRepaymentLoanTmpInfo.setInterestStart(transInfo.getDtInterestStart());
						transRepaymentLoanTmpInfo.setBillNo("");
						transRepaymentLoanTmpInfo.setBillTypeID(-1);
						
						tadi = transferTransRepaymentLoanInfoToTransAccountDetailInfo(transRepaymentLoanTmpInfo, RepaymentLoan_GRANT_AMOUNT);
						accountOperation.repayLoan(tadi);
						
						//查找放款单子账户
						tmpSubAccountAssemblerInfo = subAccountDAO.findByLoanNoteID(lLoanAccountTmpId, lLoanPayTmpId);
						if(tmpSubAccountAssemblerInfo != null){
							tmpSubAccountLoanInfo = tmpSubAccountAssemblerInfo.getSubAccountLoanInfo();
						}
						if(tmpSubAccountLoanInfo != null){
							lLoanSubAccountTmpId = tmpSubAccountLoanInfo.getID();
							sumPrehDrawInterest += DataFormat.formatDouble(tmpSubAccountLoanInfo.getPreDrawInterest());
							sumInterest += DataFormat.formatDouble(tmpSubAccountLoanInfo.getInterest())-DataFormat.formatDouble(tmpSubAccountLoanInfo.getPreDrawInterest());
						}
						attornAmount += attornmentContractInfo.getAttornmentAmount();//转让金额
						
					}
					
					//差额 = 本金/交易金额 - 自营本金 - 自营未计提利息 - 自营已计提利息
					marginAmount = transInfo.getMamount() - attornAmount - sumPrehDrawInterest - sumInterest;
				}
			}
			catch (Exception e)
			{
				throw new IRollbackException(ctx, e.getMessage(), e);
			}
			
			log.debug("------结束记录资产转让卖出买断账户交易明细--------");
			
		}
		
		
		if(transInfo.getNcraBusinessTypeId() == CRAconstant.SameBusinessAttribute.SAME_BUSINESS)//资产转让
		{
			log.debug("资产转让:处理合同余额、状态变更----------------------开始------------------------------------------------------ */");
			//根据通知单类型设置合同余额状态信息
			try{
				//【支付转让款项通知单】
				if(secNoticeInfo.getTransactionTypeId() == CRAconstant.TransactionType.CAPITAL_PAYMENT){
					//处理合同金额:转让款项可用余额不变、合同转让款项余额减少
					secContractDao.updateTransferBalance(lSecContractId, transInfo.getMamount()*(-1));//合同转让款项余额减少
                    
					//统一将合同状态的变更放到关机中处理，业务复核时不再处理  modify by wangzhen 2012-02-23
					//处理合同状态：买入买断：合同"结束"、买入回购：合同"执行中"
					//if(transInfo.getNsubTransactionTypeId() == SETTConstant.SubTransactionType.BREAK_INVEST){//买入买断
					//	secContractDao.updateStatus(lSecContractId, SECConstant.ContractStatus.FINISH);//更新合同状态"已结束"
					//}else if(transInfo.getNsubTransactionTypeId() == SETTConstant.SubTransactionType.REPURCHASE_INVEST){//买入回购
					//	secContractDao.updateStatus(lSecContractId, SECConstant.ContractStatus.ACTIVE);//更新合同状态"执行中"
					//}
				}
				//【利息收回通知单】
				else if(secNoticeInfo.getTransactionTypeId() == CRAconstant.TransactionType.INTEREST_PAYBACK){
					//处理合同金额:已收利息（待收款）减少，已收利息（已收款）增加
					secContractDao.updateWaitReceivedInterest(lSecContractId, transInfo.getMamount()*(-1));//已收利息（待收款）减少
					secContractDao.updateReceivedInterest(lSecContractId, transInfo.getMamount());//已收利息（已收款）增加
				}
				//【买入（回购）购回通知单】
				else if(secNoticeInfo.getTransactionTypeId() == CRAconstant.TransactionType.REPURCHASE_CAPITAL){
					//处理合同金额:待购回可用余额不变，待购回余额减少
					secContractDao.updateRepurchaseBalance(lSecContractId, transInfo.getMamount()*(-1));//待购回余额减少
					
					//统一将合同状态的变更放到关机中处理，业务复核时不再处理  modify by wangzhen 2012-02-23
					//处理合同状态："已结束"
					//secContractDao.updateStatus(lSecContractId, SECConstant.ContractStatus.FINISH);//更新合同状态"已结束"
				}
				//【收到转让款项通知单】
				else if(secNoticeInfo.getTransactionTypeId() == CRAconstant.TransactionType.CAPITAL_PAYBACK){
					//处理合同金额:转让款项可用余额不变、转让款项余额减少
					secContractDao.updateTransferBalance(lSecContractId, transInfo.getMamount()*(-1));//合同转让款项余额减少
					
					//统一将合同状态的变更放到关机中处理，业务复核时不再处理  modify by wangzhen 2012-02-23
					//处理合同状态：卖出买断：合同"结束"、卖出回购：合同"执行中"
					//if(transInfo.getNsubTransactionTypeId() == SETTConstant.SubTransactionType.BREAK_NOTIFY){//卖出买断
					//	secContractDao.updateStatus(lSecContractId, SECConstant.ContractStatus.FINISH);//更新合同状态"已结束"
					//}else if(transInfo.getNsubTransactionTypeId() == SETTConstant.SubTransactionType.REPURCHASE_NOTIFY){//卖出回购
					//	secContractDao.updateStatus(lSecContractId, SECConstant.ContractStatus.ACTIVE);//更新合同状态"执行中"
					//}
				}
				//【利息支付通知单】
				else if(secNoticeInfo.getTransactionTypeId() == CRAconstant.TransactionType.INTEREST_PAYMENT){
					//处理合同金额:已付利息（待支付）减少，支付利息（已支付）增加
					secContractDao.updateWaitPaidInterest(lSecContractId, transInfo.getMamount()*(-1));//已付利息（待支付）减少
					secContractDao.updatePaidInterest(lSecContractId, transInfo.getMamount());//支付利息（已支付）增加
				}
				//【卖出（回购）购回通知单】
				else if(secNoticeInfo.getTransactionTypeId() == CRAconstant.TransactionType.ACCEPT_CAPITAL){
					//处理合同金额:待购回余额减少，待购回可用余额不变
					secContractDao.updateRepurchaseBalance(lSecContractId, transInfo.getMamount()*(-1));//待购回余额减少
					//统一将合同状态的变更放到关机中处理，业务复核时不再处理  modify by wangzhen 2012-02-23
					//处理合同状态："已结束"
					//secContractDao.updateStatus(lSecContractId, SECConstant.ContractStatus.FINISH);//更新合同状态"已结束"
				}else{
					//无效通知单类型
				}
			}catch(Exception e){
				e.printStackTrace();
				throw new IRollbackException(ctx, e.getMessage(), e);
			}
			log.debug("资产转让:处理合同余额、状态变更----------------------结束------------------------------------------------------ */");
		}
		
		
		log.debug("------结束同业往来结算处理账户复核--------");
		
		log.debug("--------开始同业往来结算处理产生会计分录------------");

		//复核：生成会计分录
		/**
		 * 产生会计分录:分录类型lEntryType =0 无关，lAccountID1=收款方账户，lAccountID2=付款方账户， dAmount1=发生额
		 */
		GenerateGLEntryParam param = new GenerateGLEntryParam();
		/**
		 * 如果收/付款银行账户ID>0，则本金 流向lPrincipalType =2银行，否则本金流向lPrincipalType =1 内部转账
		 */
		long lPrincipalType = -1;
		if (transInfo.getNbankId() > 0)
		{
			//本金流向是 银行
			lPrincipalType = SETTConstant.CapitalType.BANK;
		}
		else
		{
			//本金流向是 内部转账
			lPrincipalType = SETTConstant.CapitalType.INTERNAL;
		}
		//分录类型 无关
		long lEntryType = SETTConstant.EntryType.IRRESPECTIVE;
		//收款方账户
		long receiveAccountID = -1;
		//付款方账户
		long payAccountID = -1;
		//本金开户行ID
		long principalBankID = transInfo.getNbankId();
		//利息开户行
		long interestBankID = transInfo.getNbankId();
		//发生额
		double dAmount = transInfo.getMamount();
		param.setOfficeID(transInfo.getNofficeId());
		param.setCurrencyID(transInfo.getNcurrencyId());
		param.setTransactionTypeID(transInfo.getNtransactionTypeId());
		param.setSubTransactionType(transInfo.getNsubTransactionTypeId());
		param.setExecuteDate(transInfo.getDtExecute());
		param.setInterestStartDate(transInfo.getDtInterestStart());
		param.setTransNo(transInfo.getStransNo());
		param.setAbstractStr(transInfo.getSabstract());
		param.setInputUserID(transInfo.getNinputUserId());
		param.setCheckUserID(transInfo.getNcheckUserId());
		param.setPrincipalType(lPrincipalType);
		param.setEntryType(lEntryType);
		param.setReceiveAccountID(receiveAccountID);
		param.setPayAccountID(payAccountID);
		
		if(transInfo.getNtransactionTypeId() == SETTConstant.TransactionType.TRANS_DISCOUNT_GRANT  &&
			transInfo.getNsubTransactionTypeId() == SETTConstant.SubTransactionType.BREAK_NOTIFY) {//转贴现卖出买断发放
			//将利息合计拆成利息调整，利息收入
			param.setPrincipalOrTransAmount(transInfo.getMrealamount());//本金/交易金额
			//param.setTotalInterest(transInfo.getMinterest());//利息合计
			param.setTotalInterest(dInTotalInterest);//【贴现/转贴现】买入时票据利息合计
			param.setReallyReceiveInterest(dInTotalInterest-transInfo.getMinterest());//【转贴现】实收利息=【贴现/转贴现】买入时票据利息合计-【转贴现】卖出时票据利息合计
			param.setTotalPrincipalAndInterest(dAmount);//本息合计
			
			param.setTransDiscountContractBillList(transDiscountContractBillList);//转贴现票据信息
		}
		else
		{
			if(transInfo.getNtransactionTypeId() == SETTConstant.TransactionType.FUND_ATTORN_REPAY){//拆借返款
				param.setPrincipalOrTransAmount(dAmount);//本金/交易金额
				param.setTotalInterest(transInfo.getMinterest());//利息合计
				param.setTotalPrincipalAndInterest(transInfo.getMrealamount());//本息合计
			}else{
				param.setPrincipalOrTransAmount(transInfo.getMrealamount());//本金/交易金额
				param.setTotalInterest(transInfo.getMinterest());//利息合计
				param.setTotalPrincipalAndInterest(dAmount);//本息合计
			}
		}
		if(transInfo.getNtransactionTypeId() == SETTConstant.TransactionType.SAME_BUSINESS_GRANT ||      //资产转让发放
				transInfo.getNtransactionTypeId() == SETTConstant.TransactionType.SAME_BUSINESS_RECEIVE){//资产转让收回
			param.setAttornmentContractList(attornmentContractList);
		}
		
		if(transInfo.getNtransactionTypeId() == SETTConstant.TransactionType.SAME_BUSINESS_RECEIVE 
				&& transInfo.getNsubTransactionTypeId() == SETTConstant.SubTransactionType.REPURCHASE_INVEST){//买入购回：买入（购回）回购
			param.setTotalInterest(interest);
			
		}
		if(transInfo.getNtransactionTypeId() == SETTConstant.TransactionType.SAME_BUSINESS_RECEIVE 
				&& transInfo.getNsubTransactionTypeId() == SETTConstant.SubTransactionType.REPURCHASE_NOTIFY){//买出购回：买出（购回）回购
			param.setTotalInterest(interest);
		}
		if(transInfo.getNtransactionTypeId() == SETTConstant.TransactionType.SAME_BUSINESS_GRANT 
				&& transInfo.getNsubTransactionTypeId() == SETTConstant.SubTransactionType.BREAK_NOTIFY){//买出买断发放
			param.setPreDrawInterest(sumPrehDrawInterest);
			param.setUnPreDrawInterest(sumInterest-sumPrehDrawInterest);
		}
		param.setAdjustFee(adjustFee);
		param.setAttornAmount(attornAmount);
		param.setAttornPrice(attornPrice);
		param.setMarginAmount(marginAmount);
		
		param.setPrincipalBankID(principalBankID);
		param.setInterestBankID(interestBankID);
		
		param.setCraBusinessType(transInfo.getNcraBusinessTypeId());
		param.setCounterpartId(transInfo.getNcounterpartId());
		log.print("----------ACCOUNTBOOKEJB:checkCurrentAccountDetails是否生成银行指令:false"+"-----------");
		param.setAutoCreateBankInstruction(false);
		boolean res = glopOperation.generateGLEntry(param);
		
		if (!res)
		{
			throw new IRollbackException(ctx, "同业往来产生会计分录错误2");
		}
		log.debug("--------结束同业往来结算处理产生会计分录------------");
		log.debug("------结束同业往来结算处理账簿复核--------");
		
		//由于放款单利息，计提利息等都打包一起卖，故在此清零子账户利息【注意：必须一次性全额卖出】
		if(transInfo.getNtransactionTypeId() == SETTConstant.TransactionType.SAME_BUSINESS_GRANT &&
			     transInfo.getNsubTransactionTypeId() == SETTConstant.SubTransactionType.BREAK_NOTIFY){//资产转让卖出买断发放
			try{
				if(attornmentContractList != null && attornmentContractList.size() > 0){
					//根据信贷合同转让明细，生成相应的贷款账户交易明细
					Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO();
					long lLoanPayTmpId = -1;
					long lLoanSubAccountTmpId = -1; 
					
					SubAccountAssemblerInfo tmpSubAccountAssemblerInfo = null;
					SubAccountLoanInfo tmpSubAccountLoanInfo = null;//子账户信息
					
					for(int n=0;n<attornmentContractList.size();n++)
					{   
						lLoanPayTmpId = -1;
						AttornmentContractInfo attornmentContractInfo = (AttornmentContractInfo)attornmentContractList.get(n);
						lLoanPayTmpId = attornmentContractInfo.getPayId();
						
						//查找放款单子账户
						tmpSubAccountAssemblerInfo = subAccountDAO.findByLoanNoteID(-1, lLoanPayTmpId);
						if(tmpSubAccountAssemblerInfo != null){
							tmpSubAccountLoanInfo = tmpSubAccountAssemblerInfo.getSubAccountLoanInfo();
						}else{
							continue;
						}
						if(tmpSubAccountLoanInfo != null){
							lLoanSubAccountTmpId = tmpSubAccountLoanInfo.getID();
							subAccountDAO.clearSubAccountInterestBreakNotify(transInfo.getStransNo(),lLoanSubAccountTmpId,tmpSubAccountLoanInfo.getInterest(),tmpSubAccountLoanInfo.getPreDrawInterest());
						}
					}
				}
			}
			catch (Exception e)
			{
				throw new IRollbackException(ctx, e.getMessage(), e);
			}
		}
	}
	/**
	 * 同业业务结算处理取消复核
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelCheckTransCraftbrother(TransCraftbrotherInfo transInfo) throws RemoteException, IRollbackException
	{
		log.debug("--------开始同业结算处理取消复核------------");
		
		log.debug(UtilOperation.dataentityToString(transInfo));		
		log.debug("------开始同业往来结算处理账户、账簿取消复核--------");
		long lNoticeId = transInfo.getNnoticeId();
		NoticeInfo secNoticeInfo = null;
		long lSecContractId = -1;
		SecuritiesContractInfo secContractInfo = null;
		long lSecApplyId = -1;
		ArrayList attornmentContractList = null;
		SEC_NoticeDAO secNoticeDao = null;
		SecuritiesContractDao secContractDao = null;
		AttornmentApplyDao attornmentApplyDao = null;
		
		/**资产转让发放、资产转让收回 转让明细----------------------开始------------------------------------------------------ */
		if(transInfo.getNcraBusinessTypeId() == CRAconstant.SameBusinessAttribute.SAME_BUSINESS)//资产转让
		{
			try{
				secNoticeDao = new SEC_NoticeDAO();
				secContractDao = new SecuritiesContractDao();
				attornmentApplyDao = new AttornmentApplyDao();
				
				secNoticeInfo = (NoticeInfo)secNoticeDao.findByID(lNoticeId, NoticeInfo.class);
				if(secNoticeInfo != null){
					lSecContractId = secNoticeInfo.getContractId();
					secContractInfo = (SecuritiesContractInfo)secContractDao.findByID(lSecContractId, SecuritiesContractInfo.class);
				}
				if(secContractInfo != null){
					lSecApplyId = secContractInfo.getApplyId();
				}else{
					throw new IRollbackException(ctx, "操作失败,找不到对应的资产转让合同 ");
				}
				if(transInfo.getNtransactionTypeId() == SETTConstant.TransactionType.SAME_BUSINESS_GRANT ||      
						transInfo.getNtransactionTypeId() == SETTConstant.TransactionType.SAME_BUSINESS_RECEIVE){
					//取得自营贷款转让明细
					attornmentContractList = (ArrayList)attornmentApplyDao.findContractByRepurchaseID(lSecApplyId); 
				}
			}catch (Exception e)
			{
				throw new IRollbackException(ctx, e.getMessage(), e);
			}
		}
		/**资产转让发放、资产转让收回 转让明细----------------------结束------------------------------------------------------ */
		
		if (transInfo.getNtransactionTypeId() == SETTConstant.TransactionType.TRANS_DISCOUNT_GRANT  &&
				transInfo.getNsubTransactionTypeId() == SETTConstant.SubTransactionType.BREAK_NOTIFY) {//转贴现卖出买断发放

				log.debug("------开始记录转贴现卖出买断账户交易明细--------");

				long lTransDiscountCredenceID = transInfo.getNnoticeId();//转贴现凭证
				try{
					TransDiscountCredenceDAO tdDao = new TransDiscountCredenceDAO("LOAN_DISCOUNTCREDENCE");
					Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO();
					
					Collection cBills = tdDao.findBillByTransDiscountCredenceID(lTransDiscountCredenceID);
					
					TransDiscountContractBillInfo cbInfo = null;
					TransRepaymentDiscountInfo transRepaymentDiscountInfo = null;	
					TransAccountDetailInfo tadi = null;
					AccountBean accBean = null;
					if(cBills != null && !cBills.isEmpty()){
						
						long lBillTmpId = -1;
						long lLoanPayTmpId = -1;
						long lLoanAccountTmpId = -1; 
						//票据贴现发放信息
						TransGrantDiscountInfo transGrantDiscountInfo = null;
						Sett_TransGrantDiscountDAO gdDao = new Sett_TransGrantDiscountDAO();
						accBean = new AccountBean();
						Iterator bit = cBills.iterator();
						while(bit.hasNext()){
							lLoanPayTmpId = -1;
							lLoanAccountTmpId = -1; 
							
							cbInfo = (TransDiscountContractBillInfo)bit.next();
							if(cbInfo != null && cbInfo.getBillSourceTypeID()==LOANConstant.BillSourceType.DISCOUN){
								lBillTmpId = cbInfo.getId();
								lLoanPayTmpId = cbInfo.getDiscountCredenceID();
								
								transGrantDiscountInfo = gdDao.findDiscountInfoByDiscountBillID(lBillTmpId);
								lLoanAccountTmpId = (transGrantDiscountInfo == null ? -1 : transGrantDiscountInfo.getDiscountAccountID());

								transRepaymentDiscountInfo = new TransRepaymentDiscountInfo();
								transRepaymentDiscountInfo.setSAbstract(transInfo.getSabstract());
								transRepaymentDiscountInfo.setNOfficeID(transInfo.getNofficeId());
								transRepaymentDiscountInfo.setNCurrencyID(transInfo.getNcurrencyId());
								transRepaymentDiscountInfo.setDtExecute(transInfo.getDtExecute());
								transRepaymentDiscountInfo.setDtInterestStart(transInfo.getDtInterestStart());
								transRepaymentDiscountInfo.setNDiscountNoteID(lLoanPayTmpId);
								transRepaymentDiscountInfo.setNTransactionTypeID(transInfo.getNtransactionTypeId());
								transRepaymentDiscountInfo.setNAbstractID(-1);
								transRepaymentDiscountInfo.setNDiscountAccountID(lLoanAccountTmpId);
								transRepaymentDiscountInfo.setNSubAccountID(accBean.getLoanSubAccountIDByAccountIDAndLoanNoteIDAndStatus(lLoanAccountTmpId,lLoanPayTmpId,SETTConstant.SubAccountStatus.FINISH));
								//回收票面金额
								transRepaymentDiscountInfo.setMAmount(cbInfo.getAmount());
								transRepaymentDiscountInfo.setNCurrentAccountID(-1);
								transRepaymentDiscountInfo.setNBankID(transInfo.getNbankId());
								transRepaymentDiscountInfo.setSTransNo(transInfo.getStransNo());
								
								tadi = transferTransRepaymentDiscountInfoToTransAccountDetailInfo(transRepaymentDiscountInfo, RepaymentDiscount_REPAYLOAN);
								accountOperation.cancelRepayLoan(tadi);
								//删除同业结算账户明细账
								accountOperation.deleteTransAccountDetail(transInfo.getStransNo());
							}
						}
					}else{
						throw new IRollbackException(ctx, "转贴现卖出买断发放交易失败，找不到卖出买断的票据信息！");
					}
				}catch (Exception e)
				{
					throw new IRollbackException(ctx, e.getMessage(), e);
				}

				log.debug("------结束记录转贴现卖出买断账户交易明细--------");
					
		}
		else if(transInfo.getNtransactionTypeId() == SETTConstant.TransactionType.SAME_BUSINESS_GRANT &&
			transInfo.getNsubTransactionTypeId() == SETTConstant.SubTransactionType.BREAK_NOTIFY){//资产转让卖出买断发放
			
			log.debug("------开始资产转让卖出买断反交易--------");
			try{
				if(attornmentContractList != null && attornmentContractList.size() > 0){
					//根据信贷合同转让明细，生成相应的贷款账户交易明细
					Sett_TransGrantLoanDAO sett_TransGrantLoanDAO = new Sett_TransGrantLoanDAO();
					Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO();
					TransGrantLoanInfo tmpGrantLoanInfo = null;
					long lLoanPayTmpId = -1;
					long lLoanAccountTmpId = -1; 
					
					TransRepaymentLoanInfo transRepaymentLoanTmpInfo = null;
					
					for(int n=0;n<attornmentContractList.size();n++)
					{   
						lLoanPayTmpId = -1;
						lLoanAccountTmpId = -1; 
						AttornmentContractInfo attornmentContractInfo = (AttornmentContractInfo)attornmentContractList.get(n);
						lLoanPayTmpId = attornmentContractInfo.getPayId();
						tmpGrantLoanInfo = sett_TransGrantLoanDAO.findByLoanNoteID(lLoanPayTmpId);
						lLoanAccountTmpId = (tmpGrantLoanInfo == null ? -1 : tmpGrantLoanInfo.getLoanAccountID());
												
						//构造资产转让卖出买断还款信息
						transRepaymentLoanTmpInfo = new TransRepaymentLoanInfo();
						transRepaymentLoanTmpInfo.setOfficeID(transInfo.getNofficeId());
						transRepaymentLoanTmpInfo.setCurrencyID(transInfo.getNcurrencyId());
						transRepaymentLoanTmpInfo.setTransactionTypeID(transInfo.getNtransactionTypeId());
						transRepaymentLoanTmpInfo.setExecute(transInfo.getDtExecute());
						transRepaymentLoanTmpInfo.setTransNo(transInfo.getStransNo());
						transRepaymentLoanTmpInfo.setAbstractID(-1);
						transRepaymentLoanTmpInfo.setAbstract(transInfo.getSabstract());
						transRepaymentLoanTmpInfo.setAmount(attornmentContractInfo.getAttornmentAmount());
						transRepaymentLoanTmpInfo.setDepositAccountID(-1);//资产转让发放只有开户行
						transRepaymentLoanTmpInfo.setBankID(transInfo.getNbankId());
						transRepaymentLoanTmpInfo.setLoanAccountID(lLoanAccountTmpId);
						transRepaymentLoanTmpInfo.setLoanNoteID(lLoanPayTmpId);
						transRepaymentLoanTmpInfo.setInterestStart(transInfo.getDtInterestStart());
						transRepaymentLoanTmpInfo.setBillNo("");
						transRepaymentLoanTmpInfo.setBillTypeID(-1);
						
						TransAccountDetailInfo tadi = transferTransRepaymentLoanInfoToTransAccountDetailInfo(transRepaymentLoanTmpInfo, RepaymentLoan_GRANT_AMOUNT);
						//查找放款单子账户
						SubAccountAssemblerInfo tmpSubAccountAssemblerInfo = null;
						SubAccountLoanInfo tmpSubAccountLoanInfo = null;//子账户信息
						tmpSubAccountAssemblerInfo = subAccountDAO.findByLoanNoteID(-1, lLoanPayTmpId);
						if(tmpSubAccountAssemblerInfo != null){
							tmpSubAccountLoanInfo = tmpSubAccountAssemblerInfo.getSubAccountLoanInfo();
						}else{//查找结清状态的子账户
							tmpSubAccountAssemblerInfo = subAccountDAO.findByLoanNoteID1(-1, lLoanPayTmpId);
							if(tmpSubAccountAssemblerInfo != null){
								tmpSubAccountLoanInfo = tmpSubAccountAssemblerInfo.getSubAccountLoanInfo();
							}
						}
						if(tmpSubAccountLoanInfo != null){
							tadi.setTransSubAccountID(tmpSubAccountLoanInfo.getID());
						}
						accountOperation.cancelRepayLoan(tadi);
						//删除同业结算账户明细账
						accountOperation.deleteTransAccountDetail(transInfo.getStransNo());
						
						if(tmpSubAccountLoanInfo != null){
							subAccountDAO.comebackSubAccountInterestBreakNotify(transInfo.getStransNo(),tmpSubAccountLoanInfo.getID());
						}
					}
				}
			}
			catch (Exception e)
			{
				throw new IRollbackException(ctx, e.getMessage(), e);
			}
			
			log.debug("------结束资产转让卖出买断反交易---------");
		}
		log.debug("------结束同业往来结算处理账户取消复核--------");
		
		if(transInfo.getNcraBusinessTypeId() == CRAconstant.SameBusinessAttribute.SAME_BUSINESS)//资产转让
		{
			log.debug("资产转让:取消处理合同余额、状态变更----------------------开始------------------------------------------------------ */");
			//根据通知单类型设置合同余额状态信息
			try{
				
				//【支付转让款项通知单】
				if(secNoticeInfo.getTransactionTypeId() == CRAconstant.TransactionType.CAPITAL_PAYMENT){
					//处理合同金额：转让款项可用余额不变、合同转让款项余额增加
					secContractDao.updateTransferBalance(lSecContractId, transInfo.getMamount());//合同转让款项余额增加
					//统一将合同状态的变更放到关机中处理，取消复核时不再处理  modify by wangzhen 2012-02-23
					//处理合同状态：买入买断：合同"执行中"、买入回购：合同"未执行"
					//if(transInfo.getNsubTransactionTypeId() == SETTConstant.SubTransactionType.BREAK_INVEST){//买入买断
					//	secContractDao.updateStatus(lSecContractId, SECConstant.ContractStatus.ACTIVE);//更新合同状态"执行中"
					//}else if(transInfo.getNsubTransactionTypeId() == SETTConstant.SubTransactionType.REPURCHASE_INVEST){//买入回购
					//	secContractDao.updateStatus(lSecContractId, SECConstant.ContractStatus.NOTACTIVE);//更新合同状态"未执行"
					//}
				}
				//【利息收回通知单】
				else if(secNoticeInfo.getTransactionTypeId() == CRAconstant.TransactionType.INTEREST_PAYBACK){
					//处理合同金额:已收利息（待收款）增加，已收利息（已收款）减少
					secContractDao.updateWaitReceivedInterest(lSecContractId, transInfo.getMamount());//已收利息（待收款）增加
					secContractDao.updateReceivedInterest(lSecContractId, transInfo.getMamount()*(-1));//已收利息（已收款）减少
				}
				//【买入（回购）购回通知单】
				else if(secNoticeInfo.getTransactionTypeId() == CRAconstant.TransactionType.REPURCHASE_CAPITAL){
					//处理合同金额:待购回可用余额不变，待购回余额增加
					secContractDao.updateRepurchaseBalance(lSecContractId, transInfo.getMamount());//待购回余额增加
					//统一将合同状态的变更放到关机中处理，取消复核时不再处理  modify by wangzhen 2012-02-23
					//处理合同状态："已结束"
					//secContractDao.updateStatus(lSecContractId, SECConstant.ContractStatus.ACTIVE);//更新合同状态"执行中"
				}
				//【收到转让款项通知单】
				else if(secNoticeInfo.getTransactionTypeId() == CRAconstant.TransactionType.CAPITAL_PAYBACK){
					//处理合同金额:转让款项可用余额不变、转让款项余额增加
					secContractDao.updateTransferBalance(lSecContractId, transInfo.getMamount());//合同转让款项余额增加
					//统一将合同状态的变更放到关机中处理，取消复核时不再处理  modify by wangzhen 2012-02-23
					//处理合同状态：卖出买断：合同"执行中"、卖出回购：合同"未执行"
					//if(transInfo.getNsubTransactionTypeId() == SETTConstant.SubTransactionType.BREAK_NOTIFY){//卖出买断
					//	secContractDao.updateStatus(lSecContractId, SECConstant.ContractStatus.ACTIVE);//更新合同状态"执行中"
					//}else if(transInfo.getNsubTransactionTypeId() == SETTConstant.SubTransactionType.REPURCHASE_NOTIFY){//卖出回购
					//	secContractDao.updateStatus(lSecContractId, SECConstant.ContractStatus.NOTACTIVE);//更新合同状态"未执行"
					//}
				}
				//【利息支付通知单】
				else if(secNoticeInfo.getTransactionTypeId() == CRAconstant.TransactionType.INTEREST_PAYMENT){
					//处理合同金额:已付利息（待支付）增加，支付利息（已支付）减少
					secContractDao.updateWaitPaidInterest(lSecContractId, transInfo.getMamount());//已付利息（待支付）增加
					secContractDao.updatePaidInterest(lSecContractId, transInfo.getMamount()*(-1));//支付利息（已支付）减少
				}
				//【卖出（回购）购回通知单】
				else if(secNoticeInfo.getTransactionTypeId() == CRAconstant.TransactionType.ACCEPT_CAPITAL){
					//处理合同金额:待购回余额增加，待购回可用余额不变
					secContractDao.updateRepurchaseBalance(lSecContractId, transInfo.getMamount());//待购回余额增加
					//统一将合同状态的变更放到关机中处理，取消复核时不再处理  modify by wangzhen 2012-02-23
					//处理合同状态："执行中"
					//secContractDao.updateStatus(lSecContractId, SECConstant.ContractStatus.ACTIVE);//更新合同状态"执行中"
				}else{
					//无效通知单类型
				}
			}catch(Exception e){
				e.printStackTrace();
				throw new IRollbackException(ctx, e.getMessage(), e);
			}
			log.debug("资产转让:取消处理合同余额、状态变更----------------------结束------------------------------------------------------ */");
		}
		
		//删除会计分录
		glopOperation.deleteGLEntry(transInfo.getStransNo());

		log.debug("--------结束同业往来结算处理取消复核------------");
	}
}
