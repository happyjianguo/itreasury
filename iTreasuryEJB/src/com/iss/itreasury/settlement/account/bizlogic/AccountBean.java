package com.iss.itreasury.settlement.account.bizlogic;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import com.iss.itreasury.craftbrother.transferloancontract.transfercontract.bizlogic.TransferContractBiz;
import com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dataentity.TransferContractInfo;
import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.loanpaynotice.dao.LoanPayNoticeDao;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.account.dao.Sett_AccountDAO;
import com.iss.itreasury.settlement.account.dao.Sett_AccountTypeDAO;
import com.iss.itreasury.settlement.account.dao.Sett_CapitalSuperviseSettingDAO;
import com.iss.itreasury.settlement.account.dao.Sett_CurrencySubjectDAO;
import com.iss.itreasury.settlement.account.dao.Sett_ExternalAccountDAO;
import com.iss.itreasury.settlement.account.dao.Sett_SubAccounTtype_FixedDAO;
import com.iss.itreasury.settlement.account.dao.Sett_SubAccountDAO;
import com.iss.itreasury.settlement.account.dao.Sett_SubAccountType_LoanDAO;
import com.iss.itreasury.settlement.account.dao.sett_TransAccountDetailDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.account.dataentity.AccountTypeInfo;
import com.iss.itreasury.settlement.account.dataentity.ExternalAccountInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountAssemblerInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountCurrentInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountFixedInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountLoanInfo;
import com.iss.itreasury.settlement.account.dataentity.TransAccountDetailInfo;
import com.iss.itreasury.settlement.bizdelegation.AccountSystemDelegation;
import com.iss.itreasury.settlement.dataentity.AccountSystemInfo;
import com.iss.itreasury.settlement.generalledger.bizlogic.GeneralLedgerOperation;
import com.iss.itreasury.settlement.generalledger.dao.Sett_GLSubjectDefinitionDAO;
import com.iss.itreasury.settlement.generalledger.dataentity.GLSubjectDefinitionInfo;
import com.iss.itreasury.settlement.reportlossorfreeze.dao.Sett_ReportLossOrFreezeDAO;
import com.iss.itreasury.settlement.reportlossorfreeze.dataentity.ReportLossOrFreezeInfo;
import com.iss.itreasury.settlement.setting.dao.Sett_OtherAccountType_OtherSettingDAO;
import com.iss.itreasury.settlement.setting.dao.Sett_SubAccountType_CurrentSettingDAO;
import com.iss.itreasury.settlement.setting.dataentity.SubAccountTypeCurrentSettingInfo;
import com.iss.itreasury.settlement.setting.dataentity.SubAccountTypeFixedDepositInfo;
import com.iss.itreasury.settlement.setting.dataentity.SubAccountTypeLoanSettingInfo;
import com.iss.itreasury.settlement.transfinancialmargin.dataentity.TransFinancialMarginInfo;
import com.iss.itreasury.settlement.transloan.dao.Loan_DAO;
import com.iss.itreasury.settlement.transloan.dataentity.ContractFormInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;

/**
 * Title: iTreasury Description: 账户操作的公用类，供由容器管理事务的操作和非容器管理事务的操作公用 Copyright:
 * Copyright (c) 2003 Company: iSoftStone
 * 
 * @author yehuang
 * @version Date of Creation 2003-11-23
 */
public class AccountBean
{
    private Connection conn = null;

    private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);

    final static public long TRANSTYPE_IRRELATIVE = 0; 

    final static public long TRANSTYPE_WITHDRAW = 1;

    final static public long TRANSTYPE_DEPOSIT = 2;

    /** 非容器管理事务的账户处理操作构造函数 */
    public AccountBean(Connection conn)
    {
        this.conn = conn;
    }

    /** 容器管理事务的账户处理操作构造函数 */
    public AccountBean()
    {
    }

    /**
     * 方法说明：活期支取。
     * 
     * @param tadi
     * @return 子账户ID
     * @throws RemoteException
     * @throws IRollbackException
     */
    public long withdrawCurrent(TransAccountDetailInfo tadi) throws IException,IRollbackException
    {
        log.debug("--------开始活期支取withdrawCurrent------------");
        long subAccountID = this.getCurrentSubAccoutIDByAccoutID(tadi.getTransAccountID());
        tadi.setTransSubAccountID(subAccountID);
        withdraw(tadi);
        log.debug("--------结束活期支取withdrawCurrent------------");
        return subAccountID;
    }

    /**
     * 方法说明：取消活期支取。
     * 
     * @param tadi
     * @return @throws
     *         RemoteException
     * @throws IRollbackException
     */
    public void cancelWithdrawCurrent(TransAccountDetailInfo tadi) throws IException,IRollbackException
    {
        //		long subAccountID =
        // this.getCurrentSubAccoutIDByAccoutID(tadi.getTransAccountID());
        //		
        //		// 检查账户状态是否正常，账户余额是否透支
        //		if (validateAccountStatus(tadi.getTransAccountID()) ==
        // SETTConstant.AccountStatus.NORMAL)
        //		{
        //			// 增加累计未复核金额
        //			addUncheckAmount(tadi.getTransAccountID(),tadi.getTransSubAccountID(),
        // tadi.getAmount());
        //			// 增加账户余额
        //			addAccountBalance(subAccountID, tadi.getAmount());
        //		}
        long subAccountID = getCurrentSubAccoutIDByAccoutID(tadi.getTransAccountID());
        tadi.setTransSubAccountID(subAccountID);
        cancelWithdraw(tadi);
    }

    /** private method for cancel withdraw */
    public void cancelWithdraw(TransAccountDetailInfo tadi) throws IException,IRollbackException
    {
        //注意：先增加账户余额再增加未复核金额
        // 检查账户状态是否正常，账户余额是否透支
        if (validateAccountStatus(tadi.getTransAccountID(), TRANSTYPE_DEPOSIT) == SETTConstant.AccountStatus.NORMAL)
        {
            if(!(tadi.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER))
            {
                validateSubAccountStatus(tadi.getTransSubAccountID(),TRANSTYPE_DEPOSIT);
            }
            //validateSubAccountStatus(tadi.getTransSubAccountID(),TRANSTYPE_DEPOSIT);
            // 增加账户余额
            long lSubAccountID = tadi.getTransSubAccountID();
            if (lSubAccountID == -1)
            {
                throw new IException(true, "交易的子账户不存在，交易失败", null);
            }
            addAccountBalance(lSubAccountID, tadi.getAmount());
            // 增加累计未复核金额
            if (tadi.isCommonOperation())
            	addUncheckAmount4Recog(tadi.getTransAccountID(),tadi.getTransSubAccountID(), tadi.getAmount());
        
        }
    }

    /**
     * 方法说明：增加累计未复核付款金额。
     * 保存时：未复核累计付款金额增加，参数dPaymount为正数；复核时：未复核累计付款金额减少，参数dPaymount为负数
     * 
     * @param lAccountID
     * @param lOppAccountID
     *            如果是-1表明不用考虑对方账户
     * @param dPayAmount
     * @return @throws
     *         RemoteException
     * @isCancelOperation 如果是取消复核操作调用增加未复核金额则不检查透支
     * @throws IRollbackException
     */
    public void addUncheckAmount(long lAccountID, long lOppAccountID, long subAccountID, double dPayAmount, long lTransType) throws IException,IRollbackException
    {
        log.debug("-------开始增加子账户 " + subAccountID + " 的累计未复核金额---------");
        log.debug("-------金额是 " + dPayAmount + "---------");
        Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO(conn);
        boolean isOverDraft = true;
        boolean isNeedCheckOverDraft = true;
        log.debug("---------开始月度累计发生额限制校验-----------");
        if (lOppAccountID != -1)
            isNeedCheckOverDraft = isNeedMonthLimitCheck(lAccountID, lOppAccountID);
        if (isNeedCheckOverDraft)
        {
        	//Add by shuangniu,2011-05-18,加入日累计发生额控制
        	this.isOverDayLimit(lAccountID, subAccountID, dPayAmount, true);
            //Add by Forest,2004-04-01,加入月度累计发生额限制
            this.isOverMonthLimit(lAccountID, subAccountID, dPayAmount, true);
        }
        log.debug("---------结束月度累计发生额限制校验-----------");
        log.debug("---------开始校验付款子账户是否透支-----------");
        isOverDraft = isOverDraft(lAccountID, subAccountID, dPayAmount, true);
        log.debug("---------结束校验付款子账户是否透支-----------");
        if (validateAccountStatus(lAccountID, TRANSTYPE_WITHDRAW) == SETTConstant.AccountStatus.NORMAL && !isOverDraft)
        { 
            log.debug("---------子账户没有透支-----------");
            try
            {
                if(!( lTransType == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER))
                {
                    //validateSubAccountStatus(subAccountID,TRANSTYPE_WITHDRAW);
                }
                
                log.info("增加累计未复核付款子账户:" + subAccountID + " 金额:" + dPayAmount);
                subAccountDAO.updateUncheckPaymentAmount(subAccountID, dPayAmount);
            }
            catch (Exception e)
            {
                throw new IException(true, e.getMessage(), e);
            }
        }
        log.debug("-------结束增加累计未复核金额---------");
    }
    
    /**
     * 增加累计未复核付款金额。对融资租赁 保证金 保后处理。
     * @param lAccountID      主账户ID
     * @param subAccountID    子账户ID
     * @param dPayAmount      子账户ID对应的帐户当前余额  MBALANCE
     * @throws IException
     * @throws IRollbackException
     */
    public void addUncheckAmount4Recog(long lAccountID, long subAccountID, double dPayAmount) throws IException,IRollbackException
    {
        log.debug("-------开始增加子账户 " + subAccountID + " 的累计未复核金额---------");
        log.debug("-------金额是 " + dPayAmount + "---------");
        Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO(conn);
        boolean isOverDraft = true;
        this.isOverMonthLimit(lAccountID, subAccountID, dPayAmount, true);
       
        log.debug("---------结束月度累计发生额限制校验-----------");
        log.debug("---------开始校验付款子账户是否透支-----------");
        isOverDraft = isOverDraft(lAccountID, subAccountID, dPayAmount, true);
        log.debug("---------结束校验付款子账户是否透支-----------");
        if (validateAccountStatus(lAccountID, TRANSTYPE_WITHDRAW) == SETTConstant.AccountStatus.NORMAL && !isOverDraft)
        { 
            log.debug("---------子账户没有透支-----------");
            try
            {
                log.info("增加累计未复核付款子账户:" + subAccountID + " 金额:" + dPayAmount);
                subAccountDAO.updateUncheckPaymentAmount(subAccountID, dPayAmount);
            }
            catch (Exception e)
            {
                throw new IException(true, e.getMessage(), e);
            }
        }
        log.debug("-------结束增加累计未复核金额---------");
    }

    /**
     * 方法说明：活期存入。
     * 
     * @param tadi
     * @return 子账户ID
     * @throws RemoteException
     * @throws IRollbackException
     */
    public long depositCurrent(TransAccountDetailInfo tadi) throws IException,IRollbackException
    {
        log.debug("----------开始活期存入--------------");
        long lSubAccountID = this.getCurrentSubAccoutIDByAccoutID(tadi.getTransAccountID());
        tadi.setTransSubAccountID(lSubAccountID);
        if (lSubAccountID == -1)
        {
            throw new IException(true, "无法获得主账户ID是:" + tadi.getTransAccountID() + " 的定期子账户，交易失败", null);
        }
        depoist(tadi);
        log.debug("----------结束活期存入--------------");
        return lSubAccountID;
    }

    /** withdraw from account for fixed and current transaction */
    public long withdraw(TransAccountDetailInfo tadi) throws IException,IRollbackException
    {
        long lSubAccountID = -1;
        validateOpenDate(tadi);
        // 检查账户状态是否正常，账户余额是否透支
        double amount = 0.0;
        if (!tadi.isCommonOperation()) //一般的业务操作在保存时已经检查透支，在这里不进行透支检查(即检查金额是0，利息相关操作没有保存操作，因此要进行透支检查)
            amount = tadi.getAmount();
        
        /**modified by mzh_fu 2008/03/10 为代企业资金管理-资金下拨业务所做的修改 begin*/
       // if (validateAccountStatus(tadi.getTransAccountID(), TRANSTYPE_WITHDRAW) == SETTConstant.AccountStatus.NORMAL && isOverDraft(tadi.getTransAccountID(), tadi.getTransSubAccountID(), amount, true) == false)
       // {
        if (tadi.getTransactionTypeID() == SETTConstant.TransactionType.CAPITALDOWN) {
			if (validateAccountStatus(tadi.getTransAccountID(),
					TRANSTYPE_WITHDRAW) != SETTConstant.AccountStatus.NORMAL
					|| isOverDraft(tadi, amount, true) == true) {
				
				return lSubAccountID;
			}
		} else {
			if (validateAccountStatus(tadi.getTransAccountID(),
					TRANSTYPE_WITHDRAW) != SETTConstant.AccountStatus.NORMAL
					|| isOverDraft(tadi.getTransAccountID(), tadi
							.getTransSubAccountID(), amount, true) == true) {

				return lSubAccountID;
			}
		}
        /**modified by mzh_fu 2008/03/10 为代企业资金管理-资金下拨业务所做的修改 end*/
        
            if(!(tadi.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER))
            {
                validateSubAccountStatus(tadi.getTransSubAccountID(),TRANSTYPE_WITHDRAW);
            }
            
            log.debug("--------账户状态正常，账户余额不透支------------");
            log.debug("--------交易类型ID是: " + tadi.getTransactionTypeID() + "----------");
            //if
            // (SETTConstant.TransactionType.isCurrentTransaction(tadi.getTransactionTypeID())
            // ||
            // SETTConstant.TransactionType.isFixedTransaction(tadi.getTransactionTypeID()))
            //{
            // 扣除累计未复核金额
            if (tadi.isCommonOperation())
            {
                log.debug("--------扣除子账户　" + tadi.getTransSubAccountID() + "　的累计未复核金额------------");
                subtractUncheckAmount(tadi.getTransSubAccountID(), tadi.getAmount());
            }
            //}
            // 减少账户余额
            log.debug("--------减少账户余额: " + tadi.getAmount() + "----------");
            lSubAccountID = tadi.getTransSubAccountID();
            if (lSubAccountID == -1)
            {
                throw new IException(true, "没有获得可用的子账户，交易失败", null);
            }
            subtractAccountBalance(lSubAccountID, tadi.getAmount());
            // 生成交易明细账
            sett_TransAccountDetailDAO taddao = new sett_TransAccountDetailDAO(conn);
            log.debug("--------生成交易明细账------------");
            //tadi.setTransSubAccountID(lSubAccountID);
            try
            {
                log.debug(UtilOperation.dataentityToString(tadi));
                long accountType = getAccountTypeBySubAccountID(lSubAccountID);
                AccountTypeInfo accountTypeInfo = SETTConstant.AccountType.getAccountTypeInfoByAccountTypeID(accountType);
                if (accountTypeInfo != null)
                {
                	//modify by kevin(刘连凯)2011-07-19 添加当账户组为备付金、准备金时，计入借方,拆借账户计入贷方
                	// 贷款账户与其它账户交易方向相反
                    if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST ||
                    	accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN || 
                    	accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT ||
                    	accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN ||
                    	accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING||                    	
                    	accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
                    {
                        tadi.setTransDirection(SETTConstant.ControlDirection.CREDIT);
                    }
                    else
                    {
                        tadi.setTransDirection(SETTConstant.ControlDirection.DEBIT);
                    }
                }
                else
                {
                	throw new Exception("找不到此账户类型!");
                }

                tadi.setStatusID(SETTConstant.TransactionStatus.CHECK);
                taddao.add(tadi);
            }
            catch (Exception e1)
            {
                throw new IException(true, e1.getMessage(), e1);
            }
       // }
        return lSubAccountID;
    }
    
    public long withdraw4Recog(TransAccountDetailInfo tadi) throws IException,IRollbackException
    {
    	long lSubAccountID = -1;
        validateOpenDate(tadi);
        // 检查账户状态是否正常，账户余额是否透支
        
        if(!(tadi.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER))
        {
            validateSubAccountStatus(tadi.getTransSubAccountID(),TRANSTYPE_WITHDRAW);
        }
        
        log.debug("--------账户状态正常，账户余额不透支------------");
        log.debug("--------交易类型ID是: " + tadi.getTransactionTypeID() + "----------");

        // 扣除累计未复核金额
        if (tadi.isCommonOperation())
        {
            log.debug("--------扣除子账户　" + tadi.getTransSubAccountID() + "　的累计未复核金额------------");
            subtractUncheckAmount(tadi.getTransSubAccountID(), tadi.getAmount());
        }

        // 减少账户余额
        log.debug("--------减少账户余额: " + tadi.getAmount() + "----------");
        lSubAccountID = tadi.getTransSubAccountID();
        if (lSubAccountID == -1)
        {
            throw new IException(true, "没有获得可用的子账户，交易失败", null);
        }
        subtractAccountBalance(lSubAccountID, tadi.getAmount());
        // 生成交易明细账
        sett_TransAccountDetailDAO taddao = new sett_TransAccountDetailDAO(conn);
        log.debug("--------生成交易明细账------------");
        try
        {
            log.debug(UtilOperation.dataentityToString(tadi));
            long accountType = getAccountTypeBySubAccountID(lSubAccountID);
            AccountTypeInfo accountTypeInfo = SETTConstant.AccountType.getAccountTypeInfoByAccountTypeID(accountType);
            if (accountTypeInfo != null)
            {
            	// 贷款账户与其它账户交易方向相反
                if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST ||
                	accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN || 
                	accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT ||
                	accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN ||
                	accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
                {
                    tadi.setTransDirection(SETTConstant.ControlDirection.CREDIT);
                }
                else
                {
                    tadi.setTransDirection(SETTConstant.ControlDirection.DEBIT);
                }
            }
            else
            {
            	throw new Exception("找不到此账户类型!");
            }

            tadi.setStatusID(SETTConstant.TransactionStatus.CHECK);
            taddao.add(tadi);
        }
        catch (Exception e1)
        {
            throw new IException(true, e1.getMessage(), e1);
        }
        return lSubAccountID;
    }

    /** 定期活期存入 */
    public void depoist(TransAccountDetailInfo tadi) throws IException,IRollbackException
    {
        // 检查账户状态是否正常，账户余额是否透支
        try
        {
            long lSubAccountID = -1;
            log.debug("--------开始存入----------");
           // if (validateAccountStatus(tadi.getTransAccountID(), TRANSTYPE_DEPOSIT) == SETTConstant.AccountStatus.NORMAL && validateMinSinglePayAmount(tadi.getTransAccountID(), tadi.getAmount()))
           //{
            if (validateAccountStatus(tadi.getTransAccountID(), TRANSTYPE_DEPOSIT) == SETTConstant.AccountStatus.NORMAL)
            {
            	//modified by mzh_fu 2007-05-01
//            	if(validateMinSinglePayAmount(tadi.getTransAccountID(), tadi.getAmount())){
//            		
//            	}else{            		
//            		throw new IException(true, "交易金额小于该账户的起存金额，交易失败", null);
//            	}
            	
                if(!(tadi.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER))
                {
                    validateSubAccountStatus(tadi.getTransSubAccountID(),TRANSTYPE_DEPOSIT);
                }
                //validateSubAccountStatus(tadi.getTransSubAccountID(),TRANSTYPE_DEPOSIT);
                log.debug("--------账户状态正常------------");
                lSubAccountID = tadi.getTransSubAccountID();
                if (lSubAccountID == -1)
                {
                    throw new IException(true, "交易的子账户不存在，交易失败", null);
                }
                //Sett_SubAccountDAO subAccDAO = new Sett_SubAccountDAO(conn);
                //SubAccountAssemblerInfo assInfo =
                // subAccDAO.findByID(lSubAccountID);
                //Timestamp dtOpen =
                // assInfo.getSubAccountCurrenctInfo().getOpenDate();
                validateOpenDate(tadi);
                // 增加账户余额
                log.debug("--------增加账户余额: " + tadi.getAmount() + "----------");
                addAccountBalance(lSubAccountID, tadi.getAmount());
                // 生成交易明细账
                long accountType = getAccountTypeBySubAccountID(lSubAccountID);
                AccountTypeInfo accountTypeInfo = SETTConstant.AccountType.getAccountTypeInfoByAccountTypeID(accountType);
                if (accountTypeInfo != null)
                {
                	//modify by kevin(刘连凯)2011-07-19 添加当账户组为备付金、准备金时，计入贷方,拆借账户计入借方
                    if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST ||
                       	accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN || 
                       	accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT ||
                       	accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN ||
                    	accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING ||
                       	accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
                    {
                        //其他贷款账户
                        tadi.setTransDirection(SETTConstant.ControlDirection.DEBIT);
                    }
                    else
                    {
                        tadi.setTransDirection(SETTConstant.ControlDirection.CREDIT);
                    }
                }
                else
                {
                	throw new IException(true,"找不到此账户类型!",null);
                }

                tadi.setStatusID(SETTConstant.TransactionStatus.CHECK);
                log.debug("--------生成交易明细账------------");
                sett_TransAccountDetailDAO taddao = new sett_TransAccountDetailDAO(conn);
                taddao.add(tadi);
                log.debug("--------结束存入----------");
            }
        }
        catch (SQLException e)
        {
            throw new IException(true, e.getMessage(), e);
        }
    }

    private void validateOpenDate(TransAccountDetailInfo tadi) throws IException
    {
        Sett_AccountDAO accDAO = new Sett_AccountDAO(conn);
        AccountInfo accInfo;
        try
        {
            accInfo = accDAO.findByID(tadi.getTransAccountID());
        }
        catch (SQLException e)
        {
            throw new IException(true, e.getMessage(), e);
        }
        String accountNo = accInfo.getAccountNo();
        Timestamp dtOpen = accInfo.getOpenDate();
        Timestamp dtInterestStart = tadi.getDtInterestStart();
        if( dtInterestStart != null )
        {
            if (dtInterestStart.before(dtOpen))
            {
                throw new IException(true, "交易的起息日早于账户" + accountNo + " 的开立日期，交易失败", null);
            }
        }
    }

    /**
     * 方法说明：取消活期存入。
     * 
     * @param tadi
     * @return @throws
     *         RemoteException
     * @throws IRollbackException
     */
    public void cancelDepositCurrent(TransAccountDetailInfo tadi) throws IException,IRollbackException
    {
        //		// 检查账户状态是否正常，账户余额是否透支
        //		if (validateAccountStatus(tadi.getTransAccountID()) ==
        // SETTConstant.AccountStatus.NORMAL
        //				&& isOverDraft(tadi.getTransAccountID(), tadi.getAmount()) == false)
        //		{
        //			// 减少账户余额
        //			subtractAccountBalance(tadi.getTransAccountID(), tadi.getAmount());
        //		}
        log.debug("AccoutEJB::cancelDepositCurrent start");
        long lSubAccountID = getCurrentSubAccoutIDByAccoutID(tadi.getTransAccountID());
        log.debug("AccoutEJB::getCurrentSubAccoutIDByAccoutID end---------------");
        tadi.setTransSubAccountID(lSubAccountID);
        if (lSubAccountID == -1)
        {
            throw new IException(true, "无法获得主账户ID是:" + tadi.getTransAccountID() + " 的定期子账户，交易失败", null);
        }
        log.debug("AccoutEJB::cancelDeposit start");
        cancelDeposit(tadi);
        log.debug("AccoutEJB::cancelDeposit end");
    }

    public void cancelDeposit(TransAccountDetailInfo tadi) throws IException,IRollbackException
    {
        // 检查账户状态是否正常，账户余额是否透支
        log.debug("----cancelDeposit start------");
        System.out.println("AccountBean帐号:"+tadi.getTransAccountID()+"--------------");
        if (validateAccountStatus(tadi.getTransAccountID(), TRANSTYPE_WITHDRAW) == SETTConstant.AccountStatus.NORMAL && isOverDraft(tadi.getTransAccountID(), tadi.getTransSubAccountID(), tadi.getAmount(), false) == false)
        {
            if(!(tadi.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER))
            {
                validateSubAccountStatus(tadi.getTransSubAccountID(),TRANSTYPE_WITHDRAW);
            }
            //validateSubAccountStatus(tadi.getTransSubAccountID(),TRANSTYPE_WITHDRAW);
            // 减少账户余额
            subtractAccountBalance(tadi.getTransSubAccountID(), tadi.getAmount());
        }
        else
        {
            throw new IException(true, "账户透支或者账户状态不正确,交易失败", null);
        }
        log.debug("----cancelDeposit end------");
    }

    /**
     * 方法说明：扣除累计未复核付款金额。
     * 保存时：未复核累计付款金额增加，参数dPaymount为正数；复核时：未复核累计付款金额减少，参数dPaymount为负数
     * 
     * @param lSubAccountID
     * @param dPayAmount
     * @return @throws
     *         RemoteException
     * @throws IRollbackException
     */
    public void subtractUncheckAmount(long lSubAccountID, double dPayAmount) throws IException
    {
        log.debug("-------开始扣除子账户 " + lSubAccountID + " 的累计未复核金额---------");
        log.debug("-------金额是 " + dPayAmount + "---------");
        long lReturn = -1;
        Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO(conn);
        try
        {
            subAccountDAO.updateUncheckPaymentAmount(lSubAccountID, -dPayAmount);
        }
        catch (SQLException e)
        {
            throw new IException(true, e.getMessage(), e);
        }
        log.debug("-------结束扣除累计未复核金额---------");
    }
    
    public long validateAccountStatus(long lAccountID, long transType) throws IException,IRollbackException
    {
    	log.debug("----------validateAccountStatus开始--------------");
        long lReturn = SETTConstant.AccountStatus.NORMAL;
        Sett_AccountDAO adao = new Sett_AccountDAO(conn);
        SubAccountAssemblerInfo assembler = null;
        SubAccountCurrentInfo saci = null;
        Sett_SubAccountDAO saDao = new Sett_SubAccountDAO(conn);
        AccountInfo ai = null;
        try
        {
            ai = adao.findByID(lAccountID);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            throw new IException(true, e.getMessage(), e);
        }
        log.debug("---------该账户信息为:------------" + ai);
        log.debug(UtilOperation.dataentityToString(ai));
        if (ai == null)
        {
            // 提示“付款账户ＸＸ不存在”
            //throw new IException("Sett_E117", ai.getAccountNo());
            throw new IRollbackException(null,"Sett_E117", ai.getAccountNo());
        }
        if (ai.getStatusID() == SETTConstant.AccountStatus.REPORTLOSS)
        {
            // 提示“付款账户ＸＸ已挂失”
            //throw new IException("Sett_E124", ai.getAccountNo());
            throw new IRollbackException(null,"Sett_E124", ai.getAccountNo());
        }
        //封存的账户不能进行支取(即不能付款)
        if (transType == TRANSTYPE_WITHDRAW &&  ai.getStatusID() == SETTConstant.AccountStatus.FREEZE)
        {
            // 提示“付款账户ＸＸ已封存”
            //throw new IException("Sett_E120", ai.getAccountNo());
            throw new IRollbackException(null,"Sett_E120", ai.getAccountNo());
        }
        if (ai.getStatusID() == SETTConstant.AccountStatus.ALLFREEZE)
        {
            // 提示“付款账户ＸＸ已不收不付冻结”
            //throw new IException("Sett_E119", ai.getAccountNo());
            throw new IRollbackException(null,"Sett_E123", ai.getAccountNo());
        }
        if (ai.getStatusID() == SETTConstant.AccountStatus.SEALUP)
        {
            // 提示“付款账户ＸＸ已封存”
            //throw new IException("Sett_E125", ai.getAccountNo());
            throw new IRollbackException(null,"Sett_E125", ai.getAccountNo());
        }
        if (ai.getStatusID() == SETTConstant.AccountStatus.CLOSE)
        {
            // 提示“付款账户ＸＸ已清户”
            //throw new IException("Sett_E119", ai.getAccountNo());
            throw new IRollbackException(null,"Sett_E119", ai.getAccountNo());
        }
        log.debug("----------validateAccountStatus结束--------------");
        return lReturn;
    }
    public void validateSubAccountStatus( long lSubAccountId, long transType) throws IException
    {
        log.debug("----------validateSubAccountStatus开始--------------");
        
        long lSubAccountStstus = -1;
        SubAccountFixedInfo info = new SubAccountFixedInfo();
        Sett_ReportLossOrFreezeDAO sett_ReportLossOrFreezeDAO = new Sett_ReportLossOrFreezeDAO(
                "Sett_ReportLossOrFreeze", conn);
        Sett_SubAccountDAO saDao = new Sett_SubAccountDAO(conn);
        Sett_AccountDAO adao = new Sett_AccountDAO(conn);
        AccountInfo ai = null;
        
        try
        {
            
                lSubAccountStstus = saDao.findStatusByID(lSubAccountId);
                info = saDao.findFixedSubAccountInfoByID(lSubAccountId);
                ai = adao.findByID(info.getAccountID());
                System.out.println("accountno:("+ai.getAccountNo()+")"+"depositno:("+info.getDepositNo()+")");
                
//            else
//            {
//                lSubAccountStstus = sett_ReportLossOrFreezeDAO.findSubAccountStatus(lAccountId,strDepositNo);
//            }
            
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            throw new IException(true, e.getMessage(), e);
        }
        //log.debug("---------该账户信息为:------------" + ai);
        //log.debug(UtilOperation.dataentityToString(ai));
        if (lSubAccountStstus == -1)
        {
            
            if(info.getDepositNo()==null || "".equals(info.getDepositNo()))
            {
                throw new IException("Sett_E324" , ai.getAccountNo());
            }
            else
            {
                throw new IException("Sett_E328" , ai.getAccountNo(), info.getDepositNo());
            }
            // 提示“该账户不存在”
            
        }
        if (lSubAccountStstus == SETTConstant.SubAccountStatus.REPORTLOSS)
        {
            // 提示“该账号已挂失”
            if(info.getDepositNo()==null || "".equals(info.getDepositNo()))
            {
                throw new IException("Sett_E321" , ai.getAccountNo());
            }
            else
            {
                throw new IException("Sett_E325" , ai.getAccountNo(), info.getDepositNo());
            }
        }
        //封存的账户不能进行支取(即不能付款)
        if (transType == TRANSTYPE_WITHDRAW && lSubAccountStstus == SETTConstant.SubAccountStatus.ONLYPAYFREEZE)
        {
            // 提示“只付不收冻结”
            if(info.getDepositNo()==null || "".equals(info.getDepositNo()))
            {
                throw new IException("Sett_E322" , ai.getAccountNo());
            }
            else
            {
                throw new IException("Sett_E326" , ai.getAccountNo(), info.getDepositNo());
            }
        }
        if (lSubAccountStstus == SETTConstant.SubAccountStatus.ALLFREEZE)
        {
            // 提示“不付不收冻结”
            if(info.getDepositNo()==null || "".equals(info.getDepositNo()))
            {
                throw new IException("Sett_E323" , ai.getAccountNo());
            }
            else
            {
                throw new IException("Sett_E327" , ai.getAccountNo(), info.getDepositNo());
            }
        } 
        
        log.debug("----------validateAccountStatus结束--------------");
        //return lReturn;
    }


    /**
     * 方法说明：判断是否透支
     * 
     * @param lAccountID
     * @param dPayAmount
     * @return: 透支返回 true; 不透支返回false
     * @throws RemoteException,IRollbackException
     * @throws IRollbackException
     */
    public boolean isOverDraft(long lAccountID, long subAccountID, double dPayAmount, boolean isNeedCheckMaxSinglePayAmount) throws IException
    {
        log.debug("--------开始对主账户:" + lAccountID + " 子账户:" + subAccountID + " 进行透支检查-----------");
        boolean bReturn = true;
        Sett_AccountDAO aDao = new Sett_AccountDAO(conn);
        Sett_SubAccountDAO saDao = new Sett_SubAccountDAO(conn);
        AccountInfo ai = null;
        SubAccountAssemblerInfo assembler = null;
        //SubAccountCurrentInfo saci = null;
        try
        {
            ai = aDao.findByID(lAccountID);
            if (ai != null)
            {
                assembler = saDao.findByID(subAccountID);
                if (assembler == null)
                    throw new IException(true, "无法找到子账户" + subAccountID + "所对应的信息，账户余额透支检查错误,交易失败", null);
            }
            else
            {
                throw new IException(true, "无法找到账户" + lAccountID + "所对应的信息，账户余额透支检查错误,交易失败", null);
            }
        }
        catch (Exception e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            throw new IException(true, e1.getMessage(), e1);
        }
        log.debug("---------判断账户类型------------");
        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
        AccountTypeInfo accountTypeInfo = null;
        try {
			accountTypeInfo = sett_AccountTypeDAO.findByID(ai.getAccountTypeID());
		} catch (SQLException e) {
			e.printStackTrace();
		}
        String accountNo = ai.getAccountNo();
        if (accountTypeInfo == null) {
        	throw new IException("账户类型为空");
        }
        
        if (accountTypeInfo != null) {
	        if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT ||
	        	accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK ||
	        	accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE ||
	        	accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING ||
	        	accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT)
	        {
	            log.debug("判断活期账户是否透支,支取金额:" + dPayAmount);
	            SubAccountCurrentInfo saci = assembler.getSubAccountCurrenctInfo();
	            if (saci != null)
	            {
	                //lSubAccountID = saci.getID();
	                //log.debug("---------获得活期支取子账户ID" + lSubAccountID +
	                // "--------------");
	                log.debug("单笔最高金额限制=" + ai.getMaxSinglePayAmount());
	                // 1、支取金额 > 单笔最高金额限制，提示“付款金额大于单笔最高金额限制”
	                if (isNeedCheckMaxSinglePayAmount && dPayAmount > ai.getMaxSinglePayAmount() && ai.getMaxSinglePayAmount() > 0)
	                {
	                    throw new IException("Sett_E115", String.valueOf(ai.getMaxSinglePayAmount()));
	                }
	                //Sett_AccountDAO accDAO = new Sett_AccountDAO();
	                //double sumMonthAmout = accDAO.getMonthSumAmount(ai,
	                // ai.getOfficeID(), ai.getCurrencyID());
	                /*
	                 * //2004-04-01, Forest注释掉，原因是增加了一个方法单独判断月度累计发生额限制
	                 * log.debug("月度累计发生额限制=" + saci.getMonthLimitAmount()); double
	                 * monthSumAmount = 0.0; try { monthSumAmount =
	                 * aDao.getMonthSumAmount(lAccountID, ai.getOfficeID(),
	                 * ai.getCurrencyID()); } catch (Exception e) { throw new
	                 * IException(true, e.getMessage(), e); } log.debug("月度累计发生额=" +
	                 * monthSumAmount); if (isNeedCheckMaxSinglePayAmount &&
	                 * saci.getMonthLimitAmount() != 0 && monthSumAmount +
	                 * dPayAmount > saci.getMonthLimitAmount()) { throw new
	                 * IException("账户:" + accountNo + "活期存款账户的月度累计发生额超过限制,交易失败"); }
	                 */
	                log.debug("当前余额 - 累计未复核金额 - 支取金额=" + (saci.getBalance() - saci.getDailyUncheckAmount() - dPayAmount));
	                log.debug("最低余额=" + saci.getCapitalLimitAmount());
	                // 2、当前余额 - 累计未复核金额 - 支取金额 < 最低余额，提示“账户余额低于最低余额”
	                log.debug("当前余额：" + saci.getBalance());
	                log.debug("累计未复核金额：" + saci.getDailyUncheckAmount());
	                log.debug("支取金额：" + dPayAmount);
	                double limitAmount = 0.0;
	                int level = 0;
	                //如果允许透支则checkAmount为可透支金额，否则缺省为资金限制金额
	                //修改成 透支金额和限制金额分离
	                double checkAmount = saci.getCapitalLimitAmount();
	                //不能透支是1;能透支是-1;
	                if (saci.getIsOverDraft() != 1)
	                { //允许透支
	                    log.debug("----账户允许透支-----");
	                    if (saci.getFirstLimitTypeID() == SETTConstant.AccountOverDraftType.ALL)
	                    {
	                        limitAmount = saci.getFirstLimitAmount();
	                        level = 1;
	                    }
	                    else if (saci.getSecondLimitTypeID() == SETTConstant.AccountOverDraftType.CONSIGN)
	                    {
	                        limitAmount = saci.getSecondLimitAmount();
	                        level = 2;
	                    }
	                    else if (saci.getThirdLimitTypeID() == SETTConstant.AccountOverDraftType.INTEREST)
	                    {
	                        limitAmount = saci.getThirdLimitAmount();
	                        level = 3;
	                    }
	                    else
	                    {
	                        limitAmount =0.0;
	                    }
	                    limitAmount = (-1) * limitAmount;
	                    //checkAmount = limitAmount;
	                    log.debug("----账户" + level + "级限制金额:" + limitAmount + "-----");
	                }
	                //金额-未复核金额-支取金额-限制金额 ;是否小于 透支金额
	                if (UtilOperation.Arith.sub(UtilOperation.Arith.sub(UtilOperation.Arith.sub(UtilOperation.Arith.round(saci.getBalance(), 2), UtilOperation.Arith.round(saci.getDailyUncheckAmount(), 2)), UtilOperation.Arith.round(dPayAmount, 2)),UtilOperation.Arith.round(checkAmount, 2)) < limitAmount)
	                {
	                    throw new IException("Sett_E116", accountNo);
	                }
	
	                // 透支检查未完.......
	                bReturn = false;
	            }
	        }
	        else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.FIXED ||
	        	accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY ||
	        	accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN)
	        {
	            log.debug("判断定期(通知存款、保证金存款)账户是否透支,支取金额:" + dPayAmount);
	            SubAccountFixedInfo safi = assembler.getSubAccountFixedInfo();
	            if (safi != null)
	            {
	                // 2、当前余额 - 累计未复核金额 - 支取金额 < 最低余额，提示“账户余额低于最低余额”
	                log.debug("当前余额：" + safi.getBalance());
	                log.debug("累计未复核金额：" + safi.getDailyUncheckAmount());
	                log.debug("支取金额：" + dPayAmount);
	                log.info("当前余额 - 累计未复核金额 - 支取金额=" + (safi.getBalance() - safi.getDailyUncheckAmount() - dPayAmount));
	                if (UtilOperation.Arith.sub(UtilOperation.Arith.sub(UtilOperation.Arith.round(safi.getBalance(), 2), UtilOperation.Arith.round(safi.getDailyUncheckAmount(), 2)), UtilOperation.Arith.round(dPayAmount, 2)) < 0.0)
	                {
	                    //throw new IException("Sett_E116", String.valueOf(0));
	                	throw new IException("Sett_E116", accountNo);
	                }
	                log.debug("---------未超过单笔最低金额限制-----------");
	                bReturn = false;
	            }
	        }
	        else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST || 
	        		accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN ||
	        		accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT ||
	        		accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT ||
	        		accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN)
	        {
	            log.debug("判断贷款账户是否透支,支取金额:" + dPayAmount);
	            SubAccountLoanInfo sali = assembler.getSubAccountLoanInfo();
	            log.debug("======================sali=" + sali);
	            if (sali != null)
	            {
	                log.debug("当前余额：" + sali.getBalance());
	                log.debug("累计未复核金额：" + sali.getDailyUncheckAmount());
	                log.debug("支取金额：" + dPayAmount);
	                // 2、当前余额 - 累计未复核金额 - 支取金额 < 最低余额，提示“账户余额低于最低余额”
	                log.info("当前余额 - 累计未复核金额 - 支取金额=" + (sali.getBalance() - sali.getDailyUncheckAmount() - dPayAmount));
	                if (UtilOperation.Arith.sub(UtilOperation.Arith.sub(UtilOperation.Arith.round(sali.getBalance(), 2), UtilOperation.Arith.round(sali.getDailyUncheckAmount(), 2)), UtilOperation.Arith.round(dPayAmount, 2)) < 0.0)
	                {
	                    throw new IException("Sett_E116", accountNo);
	                }
	                bReturn = false;
	            }
	        }
	        
	        //added by mzh_fu 2008/02/29　账户体系校验，只针对备付金，准备金，拆借账户组类型
	        if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK
	        	|| accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE
	        	|| accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING){
	        	
	        	AccountSystemInfo accountSystemInfo = new AccountSystemInfo();
	        	accountSystemInfo.setNAccountId(lAccountID);
	        	accountSystemInfo.setDPayAmount(dPayAmount);
	        	
	        	if(!new AccountSystemDelegation().isPassAccountSystemVerify(accountSystemInfo)){
	        		throw new IException("Sett_E116", accountNo);
	        	}
	        	 bReturn = false;
	        }	        
        }        
        
        if (!bReturn)
            log.debug("-----------" + "账户" + lAccountID + "没有透支---------------");
        else
            throw new IException("账户余额透支检查错误,交易失败");
        return bReturn;
    }

	/**
	 * 方法说明：判断账户余额支付累计未复核交易金额后是否还有余额支付此交易金额（实际余额-累计未复核金额-交易金额<0,不考虑账户是否允许透支）
	 * 
	 * @param lAccountID
	 * @param dPayAmount
	 *            @return: 不足 返回true; 足 返回false
	 * @throws RemoteException,IRollbackException
	 * @throws IRollbackException
	 */
    public boolean isLackBalanceToDraft(long lAccountID, long subAccountID, double dPayAmount, boolean isNeedCheckMaxSinglePayAmount) throws IException
    {
        log.debug("--------开始对主账户:" + lAccountID + " 子账户:" + subAccountID + " 进行余额-----------");
        boolean bReturn = true;
        Sett_AccountDAO aDao = new Sett_AccountDAO(conn);
        Sett_SubAccountDAO saDao = new Sett_SubAccountDAO(conn);
        AccountInfo ai = null;
        SubAccountAssemblerInfo assembler = null;
        //SubAccountCurrentInfo saci = null;
        try
        {
            ai = aDao.findByID(lAccountID);
            if (ai != null)
            {
                assembler = saDao.findByID(subAccountID);
                if (assembler == null)
                    throw new IException(true, "无法找到子账户" + subAccountID + "所对应的信息，账户余额透支检查错误,交易失败", null);
            }
            else
            {
                throw new IException(true, "无法找到账户" + lAccountID + "所对应的信息，账户余额透支检查错误,交易失败", null);
            }
        }
        catch (Exception e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            throw new IException(true, e1.getMessage(), e1);
        }
        log.debug("---------判断账户类型------------");
        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
        AccountTypeInfo accountTypeInfo = null;
        try {
			accountTypeInfo = sett_AccountTypeDAO.findByID(ai.getAccountTypeID());
		} catch (SQLException e) {
			e.printStackTrace();
		}
        String accountNo = ai.getAccountNo();
        if (accountTypeInfo == null) {
        	throw new IException("账户类型为空");
        }
        
        if (accountTypeInfo != null) {
	        if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK ||
	        	accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE ||
	        	accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING )
	        {
	        	
	            SubAccountCurrentInfo saci = assembler.getSubAccountCurrenctInfo();
	            if (saci != null)
	            {

	                log.debug("当前余额 - 累计未复核金额 - 支取金额=" + (saci.getBalance() - saci.getDailyUncheckAmount() - dPayAmount));
	                //log.debug("最低余额=" + saci.getCapitalLimitAmount());
	                // 2、当前余额 - 累计未复核金额 - 支取金额 < 最低余额，提示“账户余额低于最低余额”
	                log.debug("当前余额：" + saci.getBalance());
	                log.debug("累计未复核金额：" + saci.getDailyUncheckAmount());
	                log.debug("支取金额：" + dPayAmount);

	                if (UtilOperation.Arith.sub(UtilOperation.Arith.sub(UtilOperation.Arith.round(saci.getBalance(), 2), UtilOperation.Arith.round(saci.getDailyUncheckAmount(), 2)), UtilOperation.Arith.round(dPayAmount, 2)) < 0.0)
	                {
	                    //throw new IException("Sett_E116", String.valueOf(0));
	                	throw new IException("Sett_E116", accountNo);
	                }	
	                // 透支检查未完.......
	                bReturn = false;
	            }
	        }
    
	        //added by mzh_fu 2008/02/29　账户体系校验，只针对活期账户组
	        if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK
		        	|| accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE
		        	|| accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING){
	        	
	        	AccountSystemInfo accountSystemInfo = new AccountSystemInfo();
	        	accountSystemInfo.setNAccountId(lAccountID);
	        	accountSystemInfo.setDPayAmount(dPayAmount);
	        	if(!new AccountSystemDelegation().isPassAccountSystemVerify(accountSystemInfo)){
	        		throw new IException("Sett_E116", accountNo);
	        	}
	        	 bReturn = false;
	        }	        
        }        
        
        if (!bReturn)
            log.debug("-----------" + "账户" + lAccountID + "账户余额支付累计未复核交易金额后,还有余额支付此交易金额---------------");
        else
            throw new IException("账户余额支付累计未复核交易金额后,是否还有余额支付此交易金额,检查错误,交易失败");
        return bReturn;
    }


    /**
     * 方法说明：判断是否透支(代企业资金管理专用)
     * 
     * @param tadi 
     * @param dPayAmount
     * @return: 透支返回 true; 不透支返回false
     * @throws RemoteException,IRollbackException
     * @throws IRollbackException
     */
    public boolean isOverDraft(TransAccountDetailInfo tadi,double dPayAmount, boolean isNeedCheckMaxSinglePayAmount) throws IException
    {
    	long lAccountID =  tadi.getTransAccountID();
    	long subAccountID = tadi.getTransSubAccountID();
    	
        log.debug("--------开始对主账户:" + lAccountID + " 子账户:" + subAccountID + " 进行透支检查-----------");
        boolean bReturn = true;
        Sett_AccountDAO aDao = new Sett_AccountDAO(conn);
        Sett_SubAccountDAO saDao = new Sett_SubAccountDAO(conn);
        AccountInfo ai = null;
        SubAccountAssemblerInfo assembler = null;
        //SubAccountCurrentInfo saci = null;
        try
        {
            ai = aDao.findByID(lAccountID);
            if (ai != null)
            {
                assembler = saDao.findByID(subAccountID);
                if (assembler == null)
                    throw new IException(true, "无法找到子账户" + subAccountID + "所对应的信息，账户余额透支检查错误,交易失败", null);
            }
            else
            {
                throw new IException(true, "无法找到账户" + lAccountID + "所对应的信息，账户余额透支检查错误,交易失败", null);
            }
        }
        catch (Exception e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            throw new IException(true, e1.getMessage(), e1);
        }
        log.debug("---------判断账户类型------------");
        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
        AccountTypeInfo accountTypeInfo = null;
        try {
			accountTypeInfo = sett_AccountTypeDAO.findByID(ai.getAccountTypeID());
		} catch (SQLException e) {
			e.printStackTrace();
		}
        String accountNo = ai.getAccountNo();
        if (accountTypeInfo == null) {
        	throw new IException("账户类型为空");
        }
        
        if (accountTypeInfo != null) {
	        if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT ||
	        	accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT)
	        {
	            log.debug("判断活期账户是否透支,支取金额:" + dPayAmount);
	            SubAccountCurrentInfo saci = assembler.getSubAccountCurrenctInfo();
	            if (saci != null)
	            {
	                //lSubAccountID = saci.getID();
	                //log.debug("---------获得活期支取子账户ID" + lSubAccountID +
	                // "--------------");
	                log.debug("单笔最高金额限制=" + ai.getMaxSinglePayAmount());
	                // 1、支取金额 > 单笔最高金额限制，提示“付款金额大于单笔最高金额限制”
	                if (isNeedCheckMaxSinglePayAmount && dPayAmount > ai.getMaxSinglePayAmount() && ai.getMaxSinglePayAmount() > 0)
	                {
	                    throw new IException("Sett_E115", String.valueOf(ai.getMaxSinglePayAmount()));
	                }
	                //Sett_AccountDAO accDAO = new Sett_AccountDAO();
	                //double sumMonthAmout = accDAO.getMonthSumAmount(ai,
	                // ai.getOfficeID(), ai.getCurrencyID());
	                /*
	                 * //2004-04-01, Forest注释掉，原因是增加了一个方法单独判断月度累计发生额限制
	                 * log.debug("月度累计发生额限制=" + saci.getMonthLimitAmount()); double
	                 * monthSumAmount = 0.0; try { monthSumAmount =
	                 * aDao.getMonthSumAmount(lAccountID, ai.getOfficeID(),
	                 * ai.getCurrencyID()); } catch (Exception e) { throw new
	                 * IException(true, e.getMessage(), e); } log.debug("月度累计发生额=" +
	                 * monthSumAmount); if (isNeedCheckMaxSinglePayAmount &&
	                 * saci.getMonthLimitAmount() != 0 && monthSumAmount +
	                 * dPayAmount > saci.getMonthLimitAmount()) { throw new
	                 * IException("账户:" + accountNo + "活期存款账户的月度累计发生额超过限制,交易失败"); }
	                 */
	                log.debug("当前余额 - 累计未复核金额 - 支取金额=" + (saci.getBalance() - saci.getDailyUncheckAmount() - dPayAmount));
	                log.debug("最低余额=" + saci.getCapitalLimitAmount());
	                // 2、当前余额 - 累计未复核金额 - 支取金额 < 最低余额，提示“账户余额低于最低余额”
	                log.debug("当前余额：" + saci.getBalance());
	                log.debug("累计未复核金额：" + saci.getDailyUncheckAmount());
	                log.debug("支取金额：" + dPayAmount);
	                double limitAmount = 0.0;
	                int level = 0;
	                //如果允许透支则checkAmount为可透支金额，否则缺省为资金限制金额
	                //修改成 透支金额和限制金额分离
	                double checkAmount = saci.getCapitalLimitAmount();
	                //不能透支是1;能透支是-1;
	                if (saci.getIsOverDraft() != 1)
	                { //允许透支
	                    log.debug("----账户允许透支-----");
	                    if (saci.getFirstLimitTypeID() == SETTConstant.AccountOverDraftType.ALL)
	                    {
	                        limitAmount = saci.getFirstLimitAmount();
	                        level = 1;
	                    }
	                    else if (saci.getSecondLimitTypeID() == SETTConstant.AccountOverDraftType.CONSIGN)
	                    {
	                        limitAmount = saci.getSecondLimitAmount();
	                        level = 2;
	                    }
	                    else if (saci.getThirdLimitTypeID() == SETTConstant.AccountOverDraftType.INTEREST)
	                    {
	                        limitAmount = saci.getThirdLimitAmount();
	                        level = 3;
	                    }
	                    else
	                    {
	                        limitAmount =0.0;
	                    }
	                    limitAmount = (-1) * limitAmount;
	                    //checkAmount = limitAmount;
	                    log.debug("----账户" + level + "级限制金额:" + limitAmount + "-----");
	                }
	                //金额-未复核金额-支取金额-限制金额 ;是否小于 透支金额
	                if (UtilOperation.Arith.sub(UtilOperation.Arith.sub(UtilOperation.Arith.sub(UtilOperation.Arith.round(saci.getBalance(), 2), UtilOperation.Arith.round(saci.getDailyUncheckAmount(), 2)), UtilOperation.Arith.round(dPayAmount, 2)),UtilOperation.Arith.round(checkAmount, 2)) < limitAmount)
	                {
	                    throw new IException("Sett_E116", accountNo);
	                }
	
	                // 透支检查未完.......
	                bReturn = false;
	            }
	        }
	        else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.FIXED ||
	        	accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY ||
	        	accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN)
	        {
	            log.debug("判断定期(通知存款、保证金存款)账户是否透支,支取金额:" + dPayAmount);
	            SubAccountFixedInfo safi = assembler.getSubAccountFixedInfo();
	            if (safi != null)
	            {
	                // 2、当前余额 - 累计未复核金额 - 支取金额 < 最低余额，提示“账户余额低于最低余额”
	                log.debug("当前余额：" + safi.getBalance());
	                log.debug("累计未复核金额：" + safi.getDailyUncheckAmount());
	                log.debug("支取金额：" + dPayAmount);
	                log.info("当前余额 - 累计未复核金额 - 支取金额=" + (safi.getBalance() - safi.getDailyUncheckAmount() - dPayAmount));
	                if (UtilOperation.Arith.sub(UtilOperation.Arith.sub(UtilOperation.Arith.round(safi.getBalance(), 2), UtilOperation.Arith.round(safi.getDailyUncheckAmount(), 2)), UtilOperation.Arith.round(dPayAmount, 2)) < 0.0)
	                {
	                    //throw new IException("Sett_E116", String.valueOf(0));
	                	throw new IException("Sett_E116", accountNo);
	                }
	                log.debug("---------未超过单笔最低金额限制-----------");
	                bReturn = false;
	            }
	        }
	        else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST || 
	        		accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN ||
	        		accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT ||
	        		accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT ||
	        		accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN)
	        {
	            log.debug("判断贷款账户是否透支,支取金额:" + dPayAmount);
	            SubAccountLoanInfo sali = assembler.getSubAccountLoanInfo();
	            log.debug("======================sali=" + sali);
	            if (sali != null)
	            {
	                log.debug("当前余额：" + sali.getBalance());
	                log.debug("累计未复核金额：" + sali.getDailyUncheckAmount());
	                log.debug("支取金额：" + dPayAmount);
	                // 2、当前余额 - 累计未复核金额 - 支取金额 < 最低余额，提示“账户余额低于最低余额”
	                log.info("当前余额 - 累计未复核金额 - 支取金额=" + (sali.getBalance() - sali.getDailyUncheckAmount() - dPayAmount));
	                if (UtilOperation.Arith.sub(UtilOperation.Arith.sub(UtilOperation.Arith.round(sali.getBalance(), 2), UtilOperation.Arith.round(sali.getDailyUncheckAmount(), 2)), UtilOperation.Arith.round(dPayAmount, 2)) < 0.0)
	                {
	                    throw new IException("Sett_E116", accountNo);
	                }
	                bReturn = false;
	            }
	        }
	        
	        //added by mzh_fu 2008/03/10　账户体系校验，只针对活期账户组
	        if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT){
	        	//TODO 对资金下拨业务,上级账户的下拨金额的校验
	        }	        
        }        
        
        if (!bReturn)
            log.debug("-----------" + "账户" + lAccountID + "没有透支---------------");
        else
            throw new IException("账户余额透支检查错误,交易失败");
        return bReturn;
    }
    
    
    public static void main (String[] args) {
    	AccountBean accountBean = new AccountBean();
    	try {
    		System.out.println("0000000000");
    		accountBean.isOverDraft(447,661,654321,false);
    		System.out.println("0000000001");
    	} catch (Exception e) {
    		System.out.println("0000000010");
    	}
    }
    /**
     * 方法说明：判断是否透支
     * 
     * @param lAccountID
     * @param dPayAmount
     * @return: 透支返回 true; 不透支返回false
     * @throws RemoteException,IRollbackException
     * @throws IRollbackException
     */
    public SubAccountAssemblerInfo getBalanceBySubAccountID(long lAccountID, long subAccountID) throws IException
    {
        Sett_AccountDAO aDao = new Sett_AccountDAO(conn);
        Sett_SubAccountDAO saDao = new Sett_SubAccountDAO(conn);
        AccountInfo ai = null;
        SubAccountAssemblerInfo assembler = null;
        try
        {
            ai = aDao.findByID(lAccountID);
            if (ai != null)
            {
                assembler = saDao.findByID(subAccountID);
                if (assembler == null)
                    throw new IException(true, "无法找到子账户" + subAccountID + "所对应的信息，取得账户余额失败", null);
            }
            else
            {
                throw new IException(true, "无法找到账户" + lAccountID + "所对应的信息，取得账户余额失败", null);
            }
        }
        catch (Exception e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            throw new IException(true, e1.getMessage(), e1);
        }
        String accountNo = ai.getAccountNo();
        log.debug("---------判断账户类型------------");
		long accountTypeID = ai.getAccountTypeID();
        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
        AccountTypeInfo accountTypeInfo = null;
        try {
			accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (accountTypeInfo != null) {
	        if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT ||
	        	accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT)
	        {
	            SubAccountCurrentInfo saci = assembler.getSubAccountCurrenctInfo();
	            if (saci != null)
	            {
	                double limitAmount = 0.0;
	                int level = 0;
	                //如果允许透支则checkAmount为可透支金额，否则缺省为资金限制金额
	                double checkAmount = saci.getCapitalLimitAmount();
	                if (saci.getIsOverDraft() != 1)
	                { //允许透支
	                    log.debug("----账户允许透支-----");
	                    if (saci.getFirstLimitTypeID() == SETTConstant.AccountOverDraftType.ALL)
	                    {
	                        limitAmount = saci.getFirstLimitAmount();
	                        level = 1;
	                    }
	                    else if (saci.getSecondLimitAmount() == SETTConstant.AccountOverDraftType.CONSIGN)
	                    {
	                        limitAmount = saci.getSecondLimitAmount();
	                        level = 2;
	                    }
	                    else if (saci.getThirdLimitAmount() != SETTConstant.AccountOverDraftType.INTEREST)
	                    {
	                        limitAmount = saci.getThirdLimitAmount();
	                        level = 3;
	                    }
	                    else
	                    {
	                        limitAmount = saci.getCapitalLimitAmount();
	                    }
	                    log.debug("----账户" + level + "级限制金额:" + limitAmount + "-----");
	                }
	                saci.setFirstLimitAmount(limitAmount);
	            }
	        }
		}
        return assembler;
    }

    /**
     * 方法说明：判断是否超过月度累计发生额限制 Add by Forest,2004-04-01
     * 
     * @param lAccountID
     * @param dPayAmount
     * @return: 透支返回 true; 不透支返回false
     * @throws RemoteException,IRollbackException
     * @throws IRollbackException
     */
    public boolean isOverMonthLimit(long lAccountID, long subAccountID, double dPayAmount, boolean isNeedCheckMaxSinglePayAmount) throws IException
    {
        log.debug("--------开始对主账户:" + lAccountID + " 子账户:" + subAccountID + " 进行月度累计发生额检查-----------");
        boolean bReturn = true;
        Sett_AccountDAO aDao = new Sett_AccountDAO(conn);
        Sett_SubAccountDAO saDao = new Sett_SubAccountDAO(conn);
        AccountInfo ai = null;
        SubAccountAssemblerInfo assembler = null;
        try
        {
            ai = aDao.findByID(lAccountID);
            if (ai != null)
            {
                assembler = saDao.findByID(subAccountID);
                if (assembler == null)
                    throw new IException(true, "无法找到子账户" + subAccountID + "所对应的信息，账户月度累计发生额检查错误,交易失败", null);
            }
            else
            {
                throw new IException(true, "无法找到账户" + lAccountID + "所对应的信息，账户月度累计发生额检查错误,交易失败", null);
            }
            String accountNo = ai.getAccountNo();
	        log.debug("---------判断账户类型------------");
			long accountTypeID = ai.getAccountTypeID();
	        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
	        AccountTypeInfo accountTypeInfo = null;
	        try {
				accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (accountTypeInfo != null) {
	            if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT ||
	            	accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT)
	            {
	                log.info("判断月度累计发生额,支取金额:" + dPayAmount);
	                SubAccountCurrentInfo saci = assembler.getSubAccountCurrenctInfo();
	                if (saci != null)
	                {
	                    log.debug("月度累计发生额限制=" + saci.getMonthLimitAmount());
	                    double monthSumAmount = 0.0;
	                    monthSumAmount = aDao.getMonthSumAmount(lAccountID, ai.getOfficeID(), ai.getCurrencyID());
	                    log.debug("月度累计发生额=" + monthSumAmount);
	                    if (isNeedCheckMaxSinglePayAmount && saci.getMonthLimitAmount() != 0 && monthSumAmount + dPayAmount > saci.getMonthLimitAmount())
	                    {
	                        throw new IException("账户[" + accountNo + "]的月度累计发生额超过限制,交易失败");
	                    }
	                    else
	                    {
	                        bReturn = false;
	                    }
	                }
	            }
	        }
        }
        catch (IException ie)
        {
            throw ie;
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
            throw new IException(true, e1.getMessage(), e1);
        }
        return bReturn;
    }
    
    /**
     * 方法说明：判断是否超过日累计发生额限制 Add by shuangniu,2011-05-18
     * 
     * @param lAccountID
     * @param dPayAmount
     * @return: 透支返回 true; 不透支返回false
     * @throws RemoteException,IRollbackException
     * @throws IRollbackException
     */
    public boolean isOverDayLimit(long lAccountID, long subAccountID, double dPayAmount, boolean isNeedCheckMaxSinglePayAmount) throws IException
    {
        log.debug("--------开始对主账户:" + lAccountID + " 子账户:" + subAccountID + " 进行日累计发生额检查-----------");
        boolean bReturn = true;
        Sett_AccountDAO aDao = new Sett_AccountDAO(conn);
        Sett_SubAccountDAO saDao = new Sett_SubAccountDAO(conn);
        AccountInfo ai = null;
        SubAccountAssemblerInfo assembler = null;
        try
        {
            ai = aDao.findByID(lAccountID);
            if (ai != null)
            {
                assembler = saDao.findByID(subAccountID);
                if (assembler == null)
                    throw new IException(true, "无法找到子账户" + subAccountID + "所对应的信息，账户日累计发生额检查错误,交易失败", null);
            }
            else
            {
                throw new IException(true, "无法找到账户" + lAccountID + "所对应的信息，账户日累计发生额检查错误,交易失败", null);
            }
            String accountNo = ai.getAccountNo();
	        log.debug("---------判断账户类型------------");
			long accountTypeID = ai.getAccountTypeID();
	        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
	        AccountTypeInfo accountTypeInfo = null;
	        try {
				accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (accountTypeInfo != null) {
	            if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT ||
	            	accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT)
	            {
	                log.info("判断日累计发生额,支取金额:" + dPayAmount);
	                SubAccountCurrentInfo saci = assembler.getSubAccountCurrenctInfo();
	                if (saci != null)
	                {
	                    log.debug("日累计发生额限制=" + saci.getDayLimitAmount());
	                    double daySumAmount = 0.0;
	                    daySumAmount = aDao.getDaySumAmount(lAccountID, ai.getOfficeID(), ai.getCurrencyID());
	                    log.debug("日累计发生额=" + daySumAmount);
	                    if (isNeedCheckMaxSinglePayAmount && saci.getDayLimitAmount() != 0 && daySumAmount + dPayAmount > saci.getDayLimitAmount())
	                    {
	                        throw new IException("账户[" + accountNo + "]的日累计发生额超过限制,交易失败");
	                    }
	                    else
	                    {
	                        bReturn = false;
	                    }
	                }
	            }
	        }
        }
        catch (IException ie)
        {
            throw ie;
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
            throw new IException(true, e1.getMessage(), e1);
        }
        return bReturn;
    }

    /**
     * 方法说明：增加账户余额。
     * 
     * @param lAccountID
     * @param dPayAmount
     * @return @throws
     *         RemoteException
     * @throws IRollbackException
     */
    public long addAccountBalance(long lSubAccountID, double dPayAmount) throws IException
    {
        long lReturn = -1;
        try
        {
            Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO(conn);
            //			When cancel fix withdraw transaction add account balance, if
            // account status is close, then the status of account will be
            // changed to normal
            long accountType = getAccountTypeBySubAccountID(lSubAccountID);
	        log.debug("---------判断账户类型------------");
			//long accountTypeID = resultInfo.getAccountTypeID();
	        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
	        AccountTypeInfo accountTypeInfo = null;
	        try {
				accountTypeInfo = sett_AccountTypeDAO.findByID(accountType);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//modify by kevin(刘连凯)2011-07-19 
			// 添加当账户组为备付金、准备金或拆借账户时，不进行清户操作
			if (accountTypeInfo != null) {
	            if (accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.CURRENT &&
	            	accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.OTHERDEPOSIT
	            	&& accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.BAK
	            	&& accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.RESERVE
	            	&&	accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.LENDING )
	            {
	                SubAccountFixedInfo fixSubAccountInfo = subAccountDAO.findByID(lSubAccountID).getSubAccountFixedInfo();
	                if (fixSubAccountInfo.getStatusID() == SETTConstant.SubAccountStatus.FINISH)
	                {
	                    log.debug("更新子账户" + lSubAccountID + "状态从清户到正常");
	                    subAccountDAO.updateFinishDateAndStatus(lSubAccountID, SETTConstant.SubAccountStatus.NORMAL, null);
	                }
	            }
	            log.debug("更新子账户" + lSubAccountID + "余额到" + dPayAmount);
	            lReturn = subAccountDAO.updateAccountBalance(lSubAccountID, dPayAmount);
	        }
        }
        catch (Exception e)
        {
            throw new IException(true, e.getMessage(), e);
        }
        return lReturn;
    }

    /**
     * 方法说明：减少账户余额。
     * 
     * @param lAccountID
     * @param dPayAmount
     * @return 子账户ID
     * @throws RemoteException
     * @throws IRollbackException
     */
    public long subtractAccountBalance(long lSubAccountID, double dPayAmount) throws IException
    {
        log.debug("-------开始扣除子账户" + lSubAccountID + "的余额" + dPayAmount);
        long lReturn = -1;
        Sett_AccountDAO aDao = new Sett_AccountDAO(conn);
        Sett_SubAccountDAO saDao = new Sett_SubAccountDAO(conn);
        try
        {
            Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO(conn);
            //			When fix withdraw transaction subtract account balance, if
            // currentbalance = 0, the account will be colsed
            long accountType = getAccountTypeBySubAccountID(lSubAccountID);
	        log.debug("---------判断账户类型------------");
			//long accountTypeID = resultInfo.getAccountTypeID();
	        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
	        AccountTypeInfo accountTypeInfo = null;
	        try {
				accountTypeInfo = sett_AccountTypeDAO.findByID(accountType);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//modify by kevin(刘连凯)2011-07-16
			//说明：当账户组为备付金、准备金或拆借时都不进行子账户清户的操作
			if (accountTypeInfo != null) {
	            if (accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.CURRENT &&
	            	accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.OTHERDEPOSIT
	            	&&accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.BAK
	            	&&accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.RESERVE
	            	&& accountTypeInfo.getAccountGroupID() != SETTConstant.AccountGroupType.LENDING)
	            {
	                double balance = 0.0;
	                long accountID = -1;
	                SubAccountLoanInfo loanSubAccountInfo = null;
	                SubAccountFixedInfo fixSubAccountInfo = null;
	                if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.FIXED ||
	                	accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY)
	                {
	                    fixSubAccountInfo = saDao.findByID(lSubAccountID).getSubAccountFixedInfo();
	                    balance = fixSubAccountInfo.getBalance();
	                    accountID = fixSubAccountInfo.getAccountID();
	                    AccountInfo ai = aDao.findByID(accountID);
	                    if (balance - dPayAmount <= 0)
	                    {
	                        saDao.updateFinishDateAndStatus(lSubAccountID, SETTConstant.SubAccountStatus.FINISH, Env.getSystemDate(ai.getOfficeID(), ai.getCurrencyID()));
	                    }
	                }
	                else
	                {
	                    loanSubAccountInfo = saDao.findByID(lSubAccountID).getSubAccountLoanInfo();
	                    balance = loanSubAccountInfo.getBalance();
	                    accountID = loanSubAccountInfo.getAccountID();
	                    AccountInfo ai = aDao.findByID(accountID);
	                    log.debug("balance - dPayAmount:" + (balance - dPayAmount));
	                    log.debug("loanSubAccountInfo.getInterest():" + loanSubAccountInfo.getInterest());
	                    log.debug("loanSubAccountInfo.getCompoundInterest(): " + loanSubAccountInfo.getCompoundInterest());
	                    log.debug("loanSubAccountInfo.getOverDueInterest(): " + loanSubAccountInfo.getOverDueInterest());
	                    log.debug("loanSubAccountInfo.getCommission(): " + loanSubAccountInfo.getCommission());
	                    log.debug("loanSubAccountInfo.getSuretyFee()" + loanSubAccountInfo.getSuretyFee());
	                    log.debug("loanSubAccountInfo.getInterestTax()" + loanSubAccountInfo.getInterestTax());
	                    if (balance - dPayAmount <= 0 && loanSubAccountInfo.getInterest() <= 0 && loanSubAccountInfo.getCompoundInterest() <= 0 && loanSubAccountInfo.getOverDueInterest() <= 0 && loanSubAccountInfo.getCommission() <= 0 && loanSubAccountInfo.getSuretyFee() <= 0 && loanSubAccountInfo.getInterestTax() <= 0)
	                    { //判断本金及利息是否为空，如果为空才可以清户
	                        saDao.updateFinishDateAndStatus(lSubAccountID, SETTConstant.SubAccountStatus.FINISH, Env.getSystemDate(ai.getOfficeID(), ai.getCurrencyID()));
	                    }
	                }
	                log.debug("-----******余额" + balance);
	            }
	            subAccountDAO.updateAccountBalance(lSubAccountID, -dPayAmount);
	        }
        }
        catch (Exception e)
        {
            throw new IException(true, e.getMessage(), e);
        }
        log.debug("----结束扣除账户余额操作------");
        return lReturn;
    }

    public long getCurrentSubAccoutIDByAccoutID(long accoutID) throws IException
    {
        long subAccountID = -1;
        SubAccountAssemblerInfo assembler = null;
        SubAccountCurrentInfo saci = null;
        Sett_AccountDAO aDao = new Sett_AccountDAO(conn);
        Sett_SubAccountDAO saDao = new Sett_SubAccountDAO(conn);
        AccountInfo ai = null;
        long accountType = -1;
        try
        {
            ai = aDao.findByID(accoutID);
            accountType = ai.getAccountTypeID();
            assembler = saDao.findByAccountID(accoutID);
        }
        catch (Exception e)
        {
            throw new IException(true, "无法查询到账户: " + accoutID + " 对应的子账户，交易失败!!", e);
        }
        if (assembler != null && assembler.getSubAccountCurrenctInfo() != null && assembler.getSubAccountCurrenctInfo().getID() != -1)
        {
            subAccountID = assembler.getSubAccountCurrenctInfo().getID();
        }
        else
        {
            throw new IException(true, "无法查询到账户: " + accoutID + " 对应的活期子账户，交易失败!!", null);
        }
        return subAccountID;
    }

    public long getFixedSubAccoutIDByAccoutIDAndFixedDepositNo(long accoutID, String strFixedDepositNo) throws IException
    {
        long subAccountID = -1;
        SubAccountAssemblerInfo assembler = null;
        SubAccountCurrentInfo saci = null;
        //Sett_AccountDAO aDao = new Sett_AccountDAO();
        Sett_SubAccountDAO saDao = new Sett_SubAccountDAO(conn);
        AccountInfo ai = null;
        long accountType = -1;
        log.debug("----开始getFixedSubAccoutIDByAccoutIDAndFixedDepositNo------");
        try
        {
            //ai = aDao.findByID(accoutID);
        	System.out.println("1	LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL      accoutID = "+accoutID);
        	System.out.println("2	LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL      strFixedDepositNo = "+strFixedDepositNo);
            assembler = saDao.findByFixedDepositNo(accoutID, strFixedDepositNo);
            System.out.println("3	LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL      assembler = "+assembler);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new IException(true, "无法获得主账户ID是:" + accoutID + " 定期存单号是: " + strFixedDepositNo + "的定期子账户，交易失败@", null);
        }
        log.debug("" + assembler);
       
        if (assembler != null && assembler.getSubAccountFixedInfo() != null && assembler.getSubAccountFixedInfo().getID() != -1)
        {
            log.debug("" + assembler.getSubAccountFixedInfo());
            log.debug(UtilOperation.dataentityToString(assembler.getSubAccountFixedInfo()));
            subAccountID = assembler.getSubAccountFixedInfo().getID();
        }
        else
        {
            throw new IException(true, "无法获得主账户ID是:" + accoutID + " 定期存单号是: " + strFixedDepositNo + " 的定期子账户，交易失败@@@@!", null);
        }
        log.debug("----结束getFixedSubAccoutIDByAccoutIDAndFixedDepositNo------");
        return subAccountID;
    }
    
    public Collection getFixedSubAccoutIDAndAmount(long accoutID,long nContractID) throws IException
    {
    	Collection returnC = null;
    	
        Sett_SubAccountDAO saDao = new Sett_SubAccountDAO(conn);
        log.debug("----getFixedSubAccoutIDAndAmount------");
        try
        {
				returnC = saDao.findByConditions4Recog(accoutID, nContractID);
			
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new IException(true, "无法获得主账户ID是:" + accoutID + "的子账户，交易失败", null);
        }
        catch (Exception e) {
			e.printStackTrace();
		}
        log.debug("" + returnC);
       
        if (returnC == null )
        {
            throw new IException(true, "无法获得主账户ID是:" + accoutID + " 的子账户，交易失败@@!", null);
        }
        log.debug("----getFixedSubAccoutIDAndAmount------");
        return returnC;
    }
    
    public Collection getFixedSubAccoutIDAndAmoun4CancelCheck(long accoutID,long nContractID) throws IException
    {
    	Collection returnC = null;
    	
        Sett_SubAccountDAO saDao = new Sett_SubAccountDAO(conn);
        log.debug("----getFixedSubAccoutIDAndAmount------");
        try
        {
				returnC = saDao.findByConditions4RecogCancelCheck(accoutID, nContractID);
			
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new IException(true, "无法获得主账户ID是:" + accoutID + "的子账户，交易失败", null);
        }
        catch (Exception e) {
			e.printStackTrace();
		}
        log.debug("" + returnC);
       
        if (returnC == null )
        {
            throw new IException(true, "无法获得主账户ID是:" + accoutID + " 的子账户，交易失败@@!", null);
        }
        log.debug("----getFixedSubAccoutIDAndAmount------");
        return returnC;
    }

    public long getLoanSubAccountIDByAccountIDAndLoanNoteID(long accoutID, long loanNoteID) throws IException
    {
        long subAccountID = -1;
        SubAccountAssemblerInfo assembler = null;
        SubAccountCurrentInfo saci = null;
        //Sett_AccountDAO aDao = new Sett_AccountDAO();
        Sett_SubAccountDAO saDao = new Sett_SubAccountDAO(conn);
        AccountInfo ai = null;
        long accountType = -1;
        try
        {
            //ai = aDao.findByID(accoutID);
            assembler = saDao.findByLoanNoteID(accoutID, loanNoteID);
        }
        catch (SQLException e)
        {
            throw new IException(true, "无法获得主账户是:" + NameRef.getAccountNoByID(accoutID) + " 放款通知单号是: " + NameRef.getPayFormNoByID(loanNoteID) + " 的贷款子账户，交易失败", e);
        }
        if (assembler != null && assembler.getSubAccountFixedInfo() != null && assembler.getSubAccountFixedInfo().getID() != -1)
        {
            subAccountID = assembler.getSubAccountFixedInfo().getID();
        }
        else
        {
            throw new IException(true, "无法获得主账户是:" + NameRef.getAccountNoByID(accoutID) + " 放款通知单号是: " + NameRef.getPayFormNoByID(loanNoteID) + " 的贷款子账户，交易失败", null);
        }
        return subAccountID;
    }

    /**
     * added by mzh_fu 2007/08/08
     * @param accoutID
     * @param loanNoteID
     * @param status
     * @return
     * @throws IException
     */
    public long getLoanSubAccountIDByAccountIDAndLoanNoteIDAndStatus(long accoutID, long loanNoteID,long lStatus) throws IException
    {
        long subAccountID = -1;
        SubAccountAssemblerInfo assembler = null;
        SubAccountCurrentInfo saci = null;
        //Sett_AccountDAO aDao = new Sett_AccountDAO();
        Sett_SubAccountDAO saDao = new Sett_SubAccountDAO(conn);
        AccountInfo ai = null;
        long accountType = -1;
        try
        {
            //ai = aDao.findByID(accoutID);
            assembler = saDao.findByLoanNoteIDAndStatus(accoutID, loanNoteID,lStatus);
        }
        catch (SQLException e)
        {
            throw new IException(true, "无法获得主账户ID是:" + accoutID + " 放款通知单号是: " + loanNoteID + " 的贷款子账户，交易失败", e);
        }
        if (assembler != null && assembler.getSubAccountFixedInfo() != null && assembler.getSubAccountFixedInfo().getID() != -1)
        {
            subAccountID = assembler.getSubAccountFixedInfo().getID();
        }
        else
        {
            throw new IException(true, "无法获得主账户ID是:" + accoutID + " 放款通知单号是: " + loanNoteID + " 的贷款子账户，交易失败", null);
        }
        return subAccountID;
    }
    /**
     * 删除账户交易明细表的交易明细
     * 
     * @param strTransNo
     *            交易编号
     * @return @throws
     *         RemoteException
     * @throws IRollbackException
     */
    public long deleteTransAccountDetail(String strTransNo) throws IException
    {
        long lReturn = -1;
        sett_TransAccountDetailDAO dao = new sett_TransAccountDetailDAO();
        try
        {
            lReturn = dao.deleteByTransNo(strTransNo);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException(true, e.getMessage(), e);
        }
        return lReturn;
    }

    /**
     * @param lSubAccountID
     * @return @throws
     *         IRollbackException
     */
    public String getSubjectBySubAccountID(long lSubAccountID, int subjectType) throws IException
    {
        log.debug("--------开始getSubjectBySubAccountID,子账户ID是:" + lSubAccountID + "-----------");
        String strSubject = null;
        //根据子账户ID，从账户子表sett_SubAccount中查找主账户
        long accountTypeID = -1;
        try
        {
            Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO(conn);
           
            	if(lSubAccountID<0)
            	{
            		throw new IException("找不到该活期子账户信息,导致生成会计分录失败");
            	}
           
           
            SubAccountAssemblerInfo subAccountAssemblerInfo = subAccountDAO.findByID(lSubAccountID);
            if(null == subAccountAssemblerInfo.getSubAccountCurrenctInfo())
            {
            	throw new IException("找不到该活期子账户");
            }
            	long accoontID = subAccountAssemblerInfo.getSubAccountCurrenctInfo().getAccountID();
            
            
            log.debug("-------------对应的主账户ID是: " + accoontID + "---------------");
            long depositTerm = subAccountAssemblerInfo.getSubAccountFixedInfo().getDepositTerm();
            long noticeDay = subAccountAssemblerInfo.getSubAccountFixedInfo().getNoticeDay();
            long loanNoteID = subAccountAssemblerInfo.getSubAccountLoanInfo().getLoanNoteID();
            accountTypeID = -1;
            Sett_AccountDAO accountDAO = new Sett_AccountDAO(conn);
            AccountInfo accountInfo = accountDAO.findByID(accoontID);
            strSubject = accountInfo.getSubject();
            accountTypeID = accountInfo.getAccountTypeID();
            log.debug("-------------AccountTypeID: " + accountTypeID + "---------------"); 
			log.debug("-------------科目类型: " + subjectType + "---------------");
            if ((subjectType == AccountOperation.SUBJECT_TYPE_ACCOUNT && strSubject == null) || (subjectType == AccountOperation.SUBJECT_TYPE_INTEREST || subjectType == AccountOperation.SUBJECT_TYPE_PREDRAWINTEREST || subjectType == AccountOperation.SUBJECT_TYPE_INTERESTTAX  || subjectType == AccountOperation.SUBJECT_TYPE_COMMISSION || subjectType == AccountOperation.SUBJECT_TYPE_NEGOTIATEINTEREST))
            { //账户中没有保存科目信息，从账户类型编码设置附加表查找科目
                log.debug("---------账户中没有保存科目信息 或者是取利息/计提利息科目，开始从账户类型编码设置附加表查找科目------------");
                long accountGroupType = SETTConstant.AccountType.getAccountGroupTypeIDByAccountTypeID(accountTypeID);
                log.debug("-------------AccountGroupType: " + accountGroupType + "---------------");
                Sett_AccountTypeDAO accountTypeDAO = new Sett_AccountTypeDAO(conn);
                AccountTypeInfo accountTypeInfo = accountTypeDAO.findByID(accountTypeID);
                log.debug("-------------AccountTypeInfo:---------------");
                log.debug(UtilOperation.dataentityToString(accountTypeInfo));
                long isExistSubClass = accountTypeInfo.getIsExistSubClass(); 
                log.debug("subjectType: " + subjectType);
                log.debug("isExistSubClass: " + isExistSubClass);
                //2010年4月27日修改，需要将手续费挂在账户科目下
                if (isExistSubClass != 1)
                { //无下级分类
                    log.debug("---------取账户科目号，无下级分类-------------");
                    Sett_CurrencySubjectDAO currencySubjectDAO = new Sett_CurrencySubjectDAO(conn);
                    strSubject = currencySubjectDAO.findSubjectCodeByTableNameAndAccoutTypeIDAndCurrencyIDAndOfficeID("Sett_accounttype", accountTypeID, accountInfo.getCurrencyID(), accountInfo.getOfficeID());
                    log.debug("-------------1.科目号是: " + strSubject + "---------------");
                }
                else if (isExistSubClass == 1)
                {
                    if(accountGroupType == SETTConstant.AccountGroupType.CURRENT
							|| accountGroupType == SETTConstant.AccountGroupType.OTHERDEPOSIT
							|| accountGroupType == SETTConstant.AccountGroupType.MARGIN
							|| accountGroupType == SETTConstant.AccountGroupType.BAK
							|| accountGroupType == SETTConstant.AccountGroupType.RESERVE
							|| accountGroupType == SETTConstant.AccountGroupType.LENDING
                    )
                    {
						log.debug("---------活期(保证金)下级分类-------------");
						log.debug("---------subjectType-------------:"+subjectType);
						Sett_SubAccountType_CurrentSettingDAO subAccountType_CurrentSettingDAO = new Sett_SubAccountType_CurrentSettingDAO();
						
						//added by mzh_fu 2008/04/29 国电需求，对于活期增加按账户下级分类；保证金按账户、存单下级分类
						if(accountGroupType == SETTConstant.AccountGroupType.MARGIN){							
							SubAccountTypeFixedDepositInfo subAccountTypeFixedDepositInfo = new SubAccountTypeFixedDepositInfo();
							subAccountTypeFixedDepositInfo.setAccountId(subAccountAssemblerInfo.getSubAccountFixedInfo().getAccountID());
							subAccountTypeFixedDepositInfo.setDepositNo(subAccountAssemblerInfo.getSubAccountFixedInfo().getDepositNo());
							
							accountTypeInfo.setSubAccountTypeCurrentSettingInfo(null);
							accountTypeInfo.setSubAccountTypeFixedDepositInfo(subAccountTypeFixedDepositInfo);							
						}else{														
							SubAccountTypeCurrentSettingInfo subAccountTypeCurrentSettingInfo = new SubAccountTypeCurrentSettingInfo();
							subAccountTypeCurrentSettingInfo.setAccountId(subAccountAssemblerInfo.getSubAccountCurrenctInfo().getAccountID());
							
							accountTypeInfo.setSubAccountTypeFixedDepositInfo(null);
							accountTypeInfo.setSubAccountTypeCurrentSettingInfo(subAccountTypeCurrentSettingInfo);
						}
						
						switch (subjectType)
						{ 	
							case AccountOperation.SUBJECT_TYPE_ACCOUNT:
							{
								log.debug("---------取账户对应的科目-------------");
								strSubject = subAccountType_CurrentSettingDAO.findAccountSubjectCodeByAccountTypeIDAndClientID(accountTypeInfo, accountInfo.getOfficeID(), accountInfo.getCurrencyID(),accountInfo.getClientID());
							}
								break;
							case AccountOperation.SUBJECT_TYPE_INTEREST:
							{
								log.debug("---------取利息支出对应的科目-------------");
								strSubject = subAccountType_CurrentSettingDAO.findPayInterestSubjectCodeByAccountTypeIDAndClientID(accountTypeInfo, accountInfo.getOfficeID(), accountInfo.getCurrencyID(),accountInfo.getClientID());
							}
								break;
							case AccountOperation.SUBJECT_TYPE_PREDRAWINTEREST:
							{
								log.debug("---------取计提利息对应的科目-------------");
								strSubject = subAccountType_CurrentSettingDAO.findPredrawInterestSubjectCodeByAccountTypeIDAndClientID(accountTypeInfo,accountInfo.getOfficeID(), accountInfo.getCurrencyID(),accountInfo.getClientID());
							}
								break;
							case AccountOperation.SUBJECT_TYPE_NEGOTIATEINTEREST:
							{
								log.debug("---------取协定利息支出对应的科目-------------");
								strSubject = subAccountType_CurrentSettingDAO.findNegotiateInterestSubjectCodeByAccountTypeIDAndClientID(accountTypeInfo,accountInfo.getOfficeID(), accountInfo.getCurrencyID(),accountInfo.getClientID());
							}
								break;
							case AccountOperation.SUBJECT_TYPE_COMMISSION:
							{
								log.debug("---------取手续费对应的科目-------------");
								strSubject = subAccountType_CurrentSettingDAO.findCommissionSubjectCodeByAccountTypeIDAndClientID(accountTypeInfo,accountInfo.getOfficeID(), accountInfo.getCurrencyID(),accountInfo.getClientID());
							}
								break;
						}
                    
                    }           
					else  if (accountGroupType == SETTConstant.AccountGroupType.FIXED
							||accountGroupType == SETTConstant.AccountGroupType.NOTIFY
)
                    {
                        log.debug("---------定期下级分类-------------");
                        Log.print("accountGroupType : " + accountGroupType);
                        Log.print("noticeDay : " + noticeDay);
                        Log.print("depositTerm : " + depositTerm);
                        Sett_SubAccounTtype_FixedDAO subAccounTtype_FixedDAO = new Sett_SubAccounTtype_FixedDAO(conn);
                        if (depositTerm == -1 && noticeDay == -1)
                            throw new IException(true, "账户信息(定期存款期限或通知存款天数)不正确，交易失败", null);
                        //if (depositTerm == -1 && noticeDay != -1) //是通知存款
                        //	
                        Log.print("subjectType : " + subjectType);
                        if (noticeDay > 0)
                            depositTerm = noticeDay;
                        
                        //added by mzh_fu 2008/04/29 国电需求，对于定期、通知增加按账户和存单下级分类
                        SubAccountTypeFixedDepositInfo subAccountTypeFixedDepositInfo = new SubAccountTypeFixedDepositInfo();
                        subAccountTypeFixedDepositInfo.setAccountId(subAccountAssemblerInfo.getSubAccountFixedInfo().getAccountID());                        
                        subAccountTypeFixedDepositInfo.setDepositNo(subAccountAssemblerInfo.getSubAccountFixedInfo().getDepositNo());
                        
                        accountTypeInfo.setSubAccountTypeFixedDepositInfo(subAccountTypeFixedDepositInfo);
                        
                        switch (subjectType)
                        {
                            case AccountOperation.SUBJECT_TYPE_ACCOUNT:
                            {
                                log.debug("---------取账户对应的科目-------------");
                                strSubject = subAccounTtype_FixedDAO.findAccountSubjectCodeByAccountTypeIDAndFixDepositMonthID(accountTypeInfo, depositTerm, accountInfo.getOfficeID(), accountInfo.getCurrencyID(),accountInfo.getClientID());
                            }
                                break;
                            case AccountOperation.SUBJECT_TYPE_INTEREST:
                            {
                                log.debug("---------取利息对应的科目-------------");
                                strSubject = subAccounTtype_FixedDAO.findInterestSubjectCodeByAccountTypeIDAndFixDepositMonthID(accountTypeInfo, depositTerm, accountInfo.getOfficeID(), accountInfo.getCurrencyID(),accountInfo.getClientID());
                            }
                                break;
                            case AccountOperation.SUBJECT_TYPE_PREDRAWINTEREST:
                            {
                                log.debug("---------取计提利息对应的科目-------------");
                                strSubject = subAccounTtype_FixedDAO.findPredrawInterestSubjectCodeByAccountTypeIDAndFixDepositMonthID(accountTypeInfo, depositTerm, accountInfo.getOfficeID(), accountInfo.getCurrencyID(),accountInfo.getClientID());
                            }
                                break;
                        } 
                        log.debug("-------------2.科目号是: " + strSubject + "---------------");
                    }
                    else if (accountGroupType == SETTConstant.AccountGroupType.TRUST
							||accountGroupType == SETTConstant.AccountGroupType.CONSIGN
							||accountGroupType == SETTConstant.AccountGroupType.DISCOUNT
							||accountGroupType == SETTConstant.AccountGroupType.OTHERLOAN
							||accountGroupType == SETTConstant.AccountGroupType.YT)
                    {
                        log.debug("---------贷款下级分类-------------");
                        Sett_SubAccountType_LoanDAO subAccounTtype_LoanDAO = new Sett_SubAccountType_LoanDAO(conn);
                        Loan_DAO loanDAO = new Loan_DAO(conn);
                        ContractDao contractDao = new ContractDao();
                        long year = -1;
                        long loanTypeID = -1;
                        long intervalNum = -1;
                        long consignClientID = -1;
                        long draftType = -1; 
						long clientID = -1; //客户
                        long reDiscountType = -1; //转贴现种类
                        long assureTypeID = -1;//担保类型
                        log.debug("---------账户类型ID:" + accountTypeID);
                        
                        //added by mzh_fu 2008/04/29 国电需求，对于自营贷款、委托贷款增加按合同和放款通知单下级分类，其它贷款业务不做处理
                        accountTypeInfo.setSubAccountTypeLoanSettingInfo(null);
                        
                        if (accountGroupType == SETTConstant.AccountGroupType.TRUST ||
                        	accountGroupType == SETTConstant.AccountGroupType.CONSIGN || 
                        	accountGroupType == SETTConstant.AccountGroupType.YT)
                        {
                            log.debug("---------账户类型是贷款账户-------------");
                            ContractFormInfo contractFormInfo = loanDAO.getContractInfoByLoanNoteID(loanNoteID);
							
                            GregorianCalendar sCalendar = new GregorianCalendar();
                            sCalendar.setTime(contractFormInfo.getLoanStart());
                            year = sCalendar.get(Calendar.YEAR);
                            loanTypeID = contractFormInfo.getLoanTypeID();
                            intervalNum = contractFormInfo.getIntervalNum();
                            //从合同中取出委托方ID
                            consignClientID = contractFormInfo.getClientID();
                            //新增客户作为账户类型编码设置查询科目的条件--Added By Huang Ye @2004-11-09
                            clientID = accountInfo.getClientID();
                            //取合同担保方式
							ContractInfo contractInfo = contractDao.findByID(contractFormInfo.getContractID());
                            
                            if(contractInfo.getIsCredit() == Constant.YesOrNo.YES)
                            {//是否信用
								assureTypeID = LOANConstant.AssureType.CREDIT;
                            }
                            if( assureTypeID <0  && contractInfo.getIsAssure() == Constant.YesOrNo.YES)
                            {//是否担保
								assureTypeID = LOANConstant.AssureType.ASSURE;
                            }
							if( assureTypeID <0  && contractInfo.getIsImpawn() == Constant.YesOrNo.YES)
							{//是否质押
							    assureTypeID = LOANConstant.AssureType.IMPAWN;
							}
							if( assureTypeID <0  && contractInfo.getIsPledge() == Constant.YesOrNo.YES)
							{//是否抵押
								assureTypeID = LOANConstant.AssureType.PLEDGE;
							}
							log.debug("---------担保方式-------------："+assureTypeID);
                            //accountTypeInfo.setIsDraftType(-1);
                            //accountTypeInfo.setIsClientID(-1);
                            //accountTypeInfo.setIsReDiscountType(-1);
							
							//added by mzh_fu 2008/04/29 国电需求，对于自营贷款、委托贷款增加按合同和放款通知单下级分类，其它贷款业务不做处理
							//银团贷款也需要明细到合同和放款通知单
							//if(accountGroupType != SETTConstant.AccountGroupType.YT){
								SubAccountTypeLoanSettingInfo subAccountTypeLoanSettingInfo = new SubAccountTypeLoanSettingInfo();
								subAccountTypeLoanSettingInfo.setContractId(new LoanPayNoticeDao().findContractIdByNoticeId(subAccountAssemblerInfo.getSubAccountLoanInfo().getLoanNoteID()));
								subAccountTypeLoanSettingInfo.setLoanNoteId(subAccountAssemblerInfo.getSubAccountLoanInfo().getLoanNoteID());
							
								accountTypeInfo.setSubAccountTypeLoanSettingInfo(subAccountTypeLoanSettingInfo);
							//}							
                        }
                        else if (accountGroupType == SETTConstant.AccountGroupType.DISCOUNT)
                        {
                            log.debug("---------账户类型是贴现账户-------------");
                            Loan_DAO lDAO = new Loan_DAO();
                            ContractFormInfo contractFormInfo = loanDAO.getContractInfoByCredenceID(loanNoteID);
                            draftType = lDAO.getAcceptPoTypeIDByDiscountCredenceID(loanNoteID);
                            intervalNum = lDAO.getIntervalNumOfFromContractInfo(contractFormInfo.getContractID());
                            clientID = contractFormInfo.getClientID();

                            //贴现账户不匹配以下条件
                            accountTypeInfo.setIsLoanType(-1);
                            //accountTypeInfo.setIsLoanMonth(-1);
                            accountTypeInfo.setIsLoanYear(-1);
                            accountTypeInfo.setIsCosign(-1);
                            accountTypeInfo.setIsReDiscountType(-1);
							accountTypeInfo.setIsAssure(-1);

                        }
                        else
                        {
                            Loan_DAO lDAO = new Loan_DAO();

                            ContractFormInfo contractFormInfo = loanDAO.getContractInfoByCredenceID(loanNoteID);
                            draftType = lDAO.getAcceptPoTypeIDByDiscountCredenceID(loanNoteID);
                            intervalNum = lDAO.getIntervalNumOfFromContractInfo(contractFormInfo.getContractID());
                            clientID = contractFormInfo.getClientID();
                            reDiscountType = contractFormInfo.getDiscountTypeID();
                            //转贴现账户不匹配以下条件
                            accountTypeInfo.setIsLoanType(-1);
                            accountTypeInfo.setIsLoanYear(-1);
                            accountTypeInfo.setIsCosign(-1);
							accountTypeInfo.setIsAssure(-1);
                        }
                        
                        //loanDAO.
                        switch (subjectType)
                        {
                            case AccountOperation.SUBJECT_TYPE_ACCOUNT:
                            {
                                log.debug("---------取账户对应的科目-------------");
                                strSubject = subAccounTtype_LoanDAO.findAccountSubjectCode(accountTypeInfo, loanTypeID, consignClientID, intervalNum, draftType, year, clientID, reDiscountType,assureTypeID, accountInfo.getOfficeID(), accountInfo.getCurrencyID());
                            }
                                break;
                            case AccountOperation.SUBJECT_TYPE_INTEREST:
                            {
                                log.debug("---------取利息对应的科目-------------");
                                strSubject = subAccounTtype_LoanDAO.findInterestSubjectCode(accountTypeInfo, loanTypeID, consignClientID, intervalNum, draftType, year, clientID, reDiscountType,assureTypeID, accountInfo.getOfficeID(), accountInfo.getCurrencyID());
                            }
                                break;
                            case AccountOperation.SUBJECT_TYPE_PREDRAWINTEREST:
                            {
                                log.debug("---------取计提利息对应的科目-------------");
                                strSubject = subAccounTtype_LoanDAO.findPredrawInterestSubjectCode(accountTypeInfo, loanTypeID, consignClientID, intervalNum, draftType, year, clientID, reDiscountType, assureTypeID,accountInfo.getOfficeID(), accountInfo.getCurrencyID());
                            }
                                break;
                            case AccountOperation.SUBJECT_TYPE_INTERESTTAX:
                            {
                                log.debug("---------取利息税费对应的科目-------------");
                                strSubject = subAccounTtype_LoanDAO.findInterestTaxSubjectCode(accountTypeInfo, loanTypeID, consignClientID, intervalNum, draftType, year, clientID, reDiscountType, assureTypeID, accountInfo.getOfficeID(), accountInfo.getCurrencyID());
                            }
                                break;
							case AccountOperation.SUBJECT_TYPE_COMMISSION:
							{
								log.debug("---------取手续费对应的科目-------------");
								strSubject = subAccounTtype_LoanDAO.findCommissionSubjectCode(accountTypeInfo, loanTypeID, consignClientID, intervalNum, draftType, year, clientID, reDiscountType,assureTypeID, accountInfo.getOfficeID(), accountInfo.getCurrencyID());
							}
								break;
                                
                        }
                        log.debug("-------------2.科目号是: " + strSubject + "---------------");
                    }
                }
            }
        }
        catch (SQLException e)
        {
            throw new IException(true, e.getMessage(), e);
        }
        catch (Exception e)
        {
            throw new IException(true, e.getMessage(), e);
        }
        log.debug("-----------开始校验科目号合法性----------------");
        String accountTypeName = "";
		try {
			
			accountTypeName = NameRef.getAccountTypeNameByAccountTypeID(accountTypeID);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			 throw new IException(true, e.getMessage(), e);
		}
        
        if (strSubject == null || strSubject.compareToIgnoreCase("") == 0)
        {
            throw new IException(true, "账户类型" + accountTypeName + "所属科目号为空", null);
        }
        GeneralLedgerOperation glOperation = new GeneralLedgerOperation(conn);
        //GLSubjectDefinitionInfo glSubjectDefInfo1 =
        // glOperation.findBySubjectOldCode(strSubject);
        //GLSubjectDefinitionInfo glSubjectDefInfo2 =
        // glOperation.findBySubjectCode(strSubject);
        if (!glOperation.isExistSubeject(strSubject, conn))
            throw new IException(true, "账户类型" + accountTypeName + "所属科目:" + strSubject + " 不存在或已删除", null); //else
        log.debug("--------结束getSubjectBySubAccountID,科目号是: " + strSubject + "-----------");
        return strSubject;
    }
    /**
     * @param lCracontractID
     * @return @throws
     * IRollbackException
     */
    public String getSubjectByOther(long lCracontractID, int subjectType) throws IException
    {
        String strSubject = null;
        long accountTypeID = -1;
        try
        {
        	TransferContractBiz biz = new TransferContractBiz();
             
         	 if(lCracontractID<0)
         	 {
         		throw new IException("找不到该转让合同信息,导致生成会计分录失败");
         	 }  
         	TransferContractInfo contractInfo = biz.findInfoById(lCracontractID);
             if(null == contractInfo)
             {
         	   throw new IException("找不到该转让合同信息");
             }
         
            if (    subjectType == AccountOperation.SUBJECT_TYPE_REPURCHASE_NOTIFY || 
            		subjectType == AccountOperation.SUBJECT_TYPE_EXPENSE || 
            		subjectType == AccountOperation.SUBJECT_TYPE_HANDLE || 
            		subjectType == AccountOperation.SUBJECT_TYPE_ZYAMOUNT  || 
            		subjectType == AccountOperation.SUBJECT_TYPE_MDAMOUNT || 
            		subjectType == AccountOperation.SUBJECT_TYPE_MDINTEREST ||
            		subjectType == AccountOperation.SUBJECT_TYPE_ZRCOMMISSION )
            { 
                long accountGroupType = SETTConstant.AccountGroupType.OTHER;            

                if(accountGroupType == SETTConstant.AccountGroupType.OTHER)
                    {
					
						Sett_OtherAccountType_OtherSettingDAO otherAccountType_otherSettingDAO = new Sett_OtherAccountType_OtherSettingDAO();
			
						switch (subjectType)
						{ 	
							case AccountOperation.SUBJECT_TYPE_REPURCHASE_NOTIFY:
							{
								log.debug("---------取卖出回购金融资产款对应的科目-------------");
								strSubject = otherAccountType_otherSettingDAO.findRepurchaseSubjectCodeByAccountTypeIDAndClientID(lCracontractID, contractInfo.getOfficeId(), contractInfo.getCurrencyId(),contractInfo.getCounterPartId());
							}
								break;
							case AccountOperation.SUBJECT_TYPE_EXPENSE:
							{
								log.debug("---------取金融企业往来利息支出对应的科目-------------");
								strSubject = otherAccountType_otherSettingDAO.findExpenseSubjectCodeByAccountTypeIDAndClientID(lCracontractID, contractInfo.getOfficeId(), contractInfo.getCurrencyId(),contractInfo.getCounterPartId());
							}
								break;
							case AccountOperation.SUBJECT_TYPE_HANDLE:
							{
								log.debug("---------取金融企业往来应付利息对应的科目------------");
								strSubject = otherAccountType_otherSettingDAO.findHandleSubjectCodeByAccountTypeIDAndClientID(lCracontractID, contractInfo.getOfficeId(), contractInfo.getCurrencyId(),contractInfo.getCounterPartId());
							}
								break;
							case AccountOperation.SUBJECT_TYPE_ZYAMOUNT:
							{
								log.debug("---------取自营贷款本金对应的科目-------------");
								strSubject = otherAccountType_otherSettingDAO.findZyAmountSubjectCodeByAccountTypeIDAndClientID(lCracontractID,contractInfo.getOfficeId(), contractInfo.getCurrencyId(),contractInfo.getCounterPartId());
							}
								break;
							case AccountOperation.SUBJECT_TYPE_MDAMOUNT:
							{
								log.debug("---------取应付卖断本金对应的科目-------------");
								strSubject = otherAccountType_otherSettingDAO.findMDAmountSubjectCodeByAccountTypeIDAndClientID(lCracontractID,contractInfo.getOfficeId(), contractInfo.getCurrencyId(),contractInfo.getCounterPartId());
							}
								break;
							case AccountOperation.SUBJECT_TYPE_MDINTEREST:
							{
								log.debug("---------取应付卖断利息对应的科目-------------");
								strSubject = otherAccountType_otherSettingDAO.findMDInterestSubjectCodeByAccountTypeIDAndClientID(lCracontractID,contractInfo.getOfficeId(), contractInfo.getCurrencyId(),contractInfo.getCounterPartId());
							}
								break;
							case AccountOperation.SUBJECT_TYPE_ZRCOMMISSION:
							{
								log.debug("---------取手续费及佣金收入对应的科目-------------");
								strSubject = otherAccountType_otherSettingDAO.findZRCommissionSubjectCodeByAccountTypeIDAndClientID(lCracontractID,contractInfo.getOfficeId(), contractInfo.getCurrencyId(),contractInfo.getCounterPartId());
							}
								break;
						}
                    
                    }               
                }
        }
        catch (SQLException e)
        {
            throw new IException(true, e.getMessage(), e);
        }
        catch (Exception e)
        {
            throw new IException(true, e.getMessage(), e);
        }
        log.debug("-----------开始校验科目号合法性----------------");
        String accountTypeName = "";
		try {
			
			accountTypeName = NameRef.getContractCodeById(lCracontractID);
			
		} catch (Exception e) {
			 throw new IException(true, e.getMessage(), e);
		}
        
        if (strSubject == null || strSubject.compareToIgnoreCase("") == 0)
        {
            throw new IException(true, "转让合同" + accountTypeName + "所属科目号为空", null);
        }
        GeneralLedgerOperation glOperation = new GeneralLedgerOperation(conn);
        if (!glOperation.isExistSubeject(strSubject, conn))
            throw new IException(true, "转让合同" + accountTypeName + "所属科目:" + strSubject + " 不存在或已删除", null); //else
        log.debug("--------结束getSubjectBySubAccountID,科目号是: " + strSubject + "-----------");
        return strSubject;
    }


    public long getAccountTypeBySubAccountID(long subAccountID) throws IException
    {
        long accountType = -1;
        Sett_SubAccountDAO saDao = new Sett_SubAccountDAO(conn);
        try
        {
            accountType = saDao.getAccountTypeBySubAccountID(subAccountID);
        }
        catch (SQLException e)
        {
            throw new IException(true, e.getMessage(), e);
        }
        return accountType;
    }

    /** 存入时验证存入金额是否超过最小起存金额 */
    private boolean validateMinSinglePayAmount(long accountID, double depositAmount) throws SQLException
    {
        Sett_AccountDAO sDAO = new Sett_AccountDAO(conn);
        try
        {
            AccountInfo saInfo = sDAO.findByID(accountID);
            if (saInfo.getMinSinglePayAmount() == 0 || depositAmount >= saInfo.getMinSinglePayAmount())
                return true;
        }
        catch (Exception e)
        {
            throw ((SQLException) e);
        }
        return false;
    }

    /**
     * 检查是否需要月度累计发生额限制。 检查活期、委存、定期、通知账户的账户是否是同一个客户，为月度累计发生额检查使用
     * 一个活期账户向同一客户号下的活期、委存、定期、通知账户的转账，不作月度累计发生额限制
     * 
     * @return false 表示是同一个客户下的账户，不需要进行月度累计发生额检查 true
     *         不是同一客户下的账户或者不需要检查是否同一个客户，需要进行月度累计发生额检查
     */
    private boolean isNeedMonthLimitCheck(long accountID, long oppAccountID) throws IException
    {
        log.debug("***进入判断是否需要月度累计发生额限制:accountID=" + accountID + " oppAccountID=" + oppAccountID + "***");
        if (oppAccountID == -1)
        {
            log.debug("***对方账户没有，需要月度累计发生额限制***");
            return true;
        }
        Sett_AccountDAO sDAO = new Sett_AccountDAO(conn);
        AccountInfo account1 = null;
        AccountInfo account2 = null;
        try
        {
            account1 = sDAO.findByID(accountID);
            account2 = sDAO.findByID(oppAccountID);
        }
        catch (Exception e)
        {
            throw new IException(true, e.getMessage(), e);
        }
        if (account1 != null && account2 != null)
        {
            log.debug("***对方账户类型为=" + account2.getAccountTypeID() + "***");
	        log.debug("---------判断账户类型------------");
			long accountTypeID = account2.getAccountTypeID();
	        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
	        AccountTypeInfo accountTypeInfo = null;
	        try {
				accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (accountTypeInfo != null) {
	            if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT ||
	            	accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.FIXED ||
	            	accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY ||
	            	accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT ||
	            	accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN)
	            {
	                log.debug("***当前客户ID=" + account1.getClientID() + " 对方客户ID=" + account2.getClientID() + "***");
	                if (account1.getClientID() == account2.getClientID())
	                { //账户类型为以上类型需要比较客户类型
	                    log.debug("***同一客户下交易，不需月度累计发生额限制***");
	                    return false;
	                }
	                else
	                    return true;
	            }
	            else
	            { //不需要比较客户类型，需要进行透支检查
	                return true;
	            }
	        }
			else
            { //不需要比较客户类型，需要进行透支检查
                return true;
            }
        }
        else
        {
            throw new IException(true, "没有查到对应账户信息，交易失败", null);
        }
    }

    /**
     * 对母子公司的账户的余额检查 1.如果是上级单位，则如果（即上级单位该付款账户余额 – 对应整个体系的所有负息资金账户余额合计 +
     * 所有下属单位纳入资金集中管理的正常活期存款账户余额合计） >= （付款金额）， 则可以付款，否则不能付款。此时的活期存款账户余额可能为负数。
     * 2.如果是下属单位，则有两个检查： （1）、首先检查（下属单位该付款账户的余额） >=
     * （付款金额）,付款金额是否足够，如果不足够则不能进行交易；如果足够则继续下面的检查。
     * （2）、然后检查（上级单位所有实行资金集中管理的活期存款账户余额 -对应整个体系的所有负息资金账户余额合计 +
     * 所有下属单位纳入资金集中管理的正常活期账户余额）>= （付款金额），则可以付款；否则不能付款。
     * 3.如果是实行资金集中管理的上级单位活期账户的付款交易时，系统不再进行
     * “账户是否不能透支”的判断。3、如果是实行资金集中管理的上级单位活期账户的付款交易时，系统不再进行 “账户是否不能透支”的判断。
     * 
     * @return 0 表示不需要做该检查,需要进行正常的透支检查(isOverDraft)
     * @return 1 表示在资金管理体系之内并且已经进行了检查，不需要进行下面的检查
     */
    private long checkOverDraftForCooperation(AccountInfo accountInfo, SubAccountAssemblerInfo assembler, double dPayAmount) throws IException
    {
        log.debug("--------开始对主账户:" + accountInfo.getAccountID() + " 子账户:" + assembler.getSubAccountCurrenctInfo().getID() + " 进行母子公司透支检查-----------");
        int accountType = getBelongToAccountType(accountInfo.getAccountID());
        if (accountType == 0) //无匹配记录或无需检查,返回
            return 0;
        Sett_CapitalSuperviseSettingDAO dao = new Sett_CapitalSuperviseSettingDAO();
        SubAccountCurrentInfo subAccountCurrentInfo = assembler.getSubAccountCurrenctInfo();
        if (accountType == Sett_CapitalSuperviseSettingDAO.ACCOUNT_TYPE_PARENT)
        {
            long clientID = accountInfo.getClientID();
            //对应整个体系的所有负息资金账户余额合计
            double[] balncesOfDebitInterestAccount = dao.sumDebitInterestBlanceOfOneClient(clientID);
            //所有下属单位纳入资金集中管理的正常活期存款账户余额合计
            double[] balncesOfCurrent = dao.sumCurrentBlanceOfOneClient(clientID);
            //上级单位该付款账户余额
            double[] parentBalance = dao.sumSuperAccountBalanceBySuperAccountID(accountInfo.getAccountID());
            //可用余额=上级单位该付款账户余额 – 对应整个体系的所有负息资金账户余额合计 +
            // 所有下属单位纳入资金集中管理的正常活期存款账户余额合计
            log.debug("--------账户是上级单位---------");
            log.debug("--------上级单位该付款账户余额(parentBalance):" + parentBalance);
            log.debug("--------上级单位该付款账户累计未复核金额:" + subAccountCurrentInfo.getDailyUncheckAmount());
            log.debug("--------对应整个体系的所有负息资金账户余额合计(balncesOfDebitInterestAccount):" + balncesOfDebitInterestAccount[0]);
            log.debug("--------对应整个体系的所有负息资金账户累计未复核金额:" + balncesOfDebitInterestAccount[1]);
            log.debug("--------所有下属单位纳入资金集中管理的正常活期存款账户余额合计(balncesOfCurrent):" + balncesOfCurrent[0]);
            log.debug("--------所有下属单位纳入资金集中管理的正常活期存款资金账户累计未复核金额:" + balncesOfCurrent[1]);
            log.debug("--------可用余额=上级单位该付款账户余额 - abs(对应整个体系的所有负息资金账户余额合计) + 所有下属单位纳入资金集中管理的正常活期存款账户余额合计---------");
            double balance = (parentBalance[0] - parentBalance[1]) - java.lang.Math.abs(balncesOfDebitInterestAccount[0] - balncesOfDebitInterestAccount[1]) + (balncesOfCurrent[0] - balncesOfCurrent[1]);
            log.debug("--------可用余额(balance):" + balance);
            log.debug("--------支取金额(dPayAmount):" + dPayAmount);
            if (balance < dPayAmount)
                throw new IException("Sett_E116", String.valueOf(0));
        }
        else if (accountType == Sett_CapitalSuperviseSettingDAO.ACCOUNT_TYPE_SUB)
        {
            long clientID = dao.getSuperClientIDByChildAccountID(accountInfo.getAccountID());
            //对应整个体系的所有负息资金账户余额合计
            double balncesOfDebitInterestAccount[] = dao.sumDebitInterestBlanceOfOneClient(clientID);
            //所有下属单位纳入资金集中管理的正常活期存款账户余额合计
            double balncesOfCurrent[] = dao.sumCurrentBlanceOfOneClient(clientID);
            //下属单位该付款账户的余额
            if (subAccountCurrentInfo.getBalance() - subAccountCurrentInfo.getDailyUncheckAmount() < dPayAmount)
                throw new IException("Sett_E116", String.valueOf(0));
            //上级单位所有实行资金集中管理的活期存款账户余额 -对应整个体系的所有负息资金账户余额合计
            //+ 所有下属单位纳入资金集中管理的正常活期账户余额）>= （付款金额）
            //上级单位所有实行资金集中管理的活期存款账户余额
            double balanceOfSuperAccount[] = dao.sumSuperAccountBalanceByChildAccountID(accountInfo.getAccountID());
            log.debug("--------账户是下级单位---------");
            log.debug("--------上级单位该付款账户余额(balanceOfSuperAccount):" + balanceOfSuperAccount[0]);
            log.debug("--------上级单位该付款账户累计未复核金额:" + balanceOfSuperAccount[1]);
            log.debug("--------对应整个体系的所有负息资金账户余额合计(balncesOfDebitInterestAccount):" + balncesOfDebitInterestAccount[0]);
            log.debug("--------对应整个体系的所有负息资金账户累计未复核金额:" + balncesOfDebitInterestAccount[1]);
            log.debug("--------所有下属单位纳入资金集中管理的正常活期存款账户余额合计(balncesOfCurrent):" + balncesOfCurrent[0]);
            log.debug("--------所有下属单位纳入资金集中管理的正常活期存款账户累计未复核金额:" + balncesOfCurrent[1]);
            log.debug("--------可用余额=上级单位所有实行资金集中管理的活期存款账户余额 -abs(对应整个体系的所有负息资金账户余额合计)+ 所有下属单位纳入资金集中管理的正常活期账户余额---------");
            log.debug("--------支取金额(dPayAmount):" + dPayAmount);
            double balance = (balanceOfSuperAccount[0] - balanceOfSuperAccount[1]) - java.lang.Math.abs(balncesOfDebitInterestAccount[0] - balncesOfDebitInterestAccount[1]) + (balncesOfCurrent[0] - balncesOfCurrent[1]);
            log.debug("--------可用余额(balance):" + balance);
            if (balance < dPayAmount)
                throw new IException("Sett_E116", String.valueOf(0));
        }
        else
            return 0; //不在资金管理体系之内，继续做下面的透支检查
        log.debug("--------结束母子公司透支检查-----------");
        return 1;
    }

    /**
     * 获取账户的类型
     * 
     * @return 0: 没有记录,不进行母子公司账户检查 1: 母公司 2: 子公司
     */
    public int getBelongToAccountType(long accountID) throws IException
    {
        Sett_CapitalSuperviseSettingDAO dao = new Sett_CapitalSuperviseSettingDAO();
        long parentNum = dao.countRecordByAccountID(accountID, Sett_CapitalSuperviseSettingDAO.ACCOUNT_TYPE_PARENT);
        if (parentNum > 0)
            return Sett_CapitalSuperviseSettingDAO.ACCOUNT_TYPE_PARENT;
        long subNum = dao.countRecordByAccountID(accountID, Sett_CapitalSuperviseSettingDAO.ACCOUNT_TYPE_SUB);
        if (subNum > 0)
            return Sett_CapitalSuperviseSettingDAO.ACCOUNT_TYPE_SUB;
        return 0;
    }
    /**
     * 挂失、解挂、换发证书、冻结、解冻
     * 
     * @return 0: 
     */
    public long reportLossOrFreeze(ReportLossOrFreezeInfo info,long lOperationType) throws IException
    {
        Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO(conn);
        Sett_ReportLossOrFreezeDAO sett_ReportLossOrFreezeDAO = new Sett_ReportLossOrFreezeDAO(
                "Sett_ReportLossOrFreeze", conn);
        Sett_AccountDAO accountDAO = new Sett_AccountDAO(conn);
        long lSubAccountID = -1;
        long lResult = -1 ;
        double freezeAmount = 0;
        try
        {
            if(info == null)
            {
                throw new IException("Sett_E056");
            }
            if(info.getTransActionType()==SETTConstant.TransactionType.CHANGECERTIFICATE)
            {
                if(lOperationType == SETTConstant.TransactionOperate.CHECK)
                {
                    lSubAccountID =sett_ReportLossOrFreezeDAO.findSubAccountId(info.getAccountId(),info.getOldDepositNo());
	               
                }
                else
                {
                    lSubAccountID =sett_ReportLossOrFreezeDAO.findSubAccountId(info.getAccountId(),info.getNewDepositNo());
	               
                }
            
                
            } 
            else
            {
                lSubAccountID =sett_ReportLossOrFreezeDAO.findSubAccountId(info.getAccountId(),info.getOldDepositNo());
               
            }
        
           
            
////        挂失、冻结、换发证书在保存时修改状态；解挂、解冻在保存时复核时修改状态
            if(info.getTransActionType()==SETTConstant.TransactionType.REPORTLOSS || info.getTransActionType()==SETTConstant.TransactionType.FREEZE || info.getTransActionType()==SETTConstant.TransactionType.CHANGECERTIFICATE)
            {
                freezeAmount = sett_ReportLossOrFreezeDAO.findCapitalLimitAmountById(lSubAccountID);
                double dtemp =1;	//	比较部分解冻后限制金额
                dtemp = freezeAmount-info.getFreezeAmount();
                ///如果保存则修改修改状态，否则恢复状态
                if(lOperationType == SETTConstant.TransactionOperate.CHECK)
                {
                    lResult = subAccountDAO.updateStatus(lSubAccountID,info.getSubAccountNewStatus());
                    /*
                     * 活期账号还要更改账号状态 
                     */
                    if(info.getTransActionType()==SETTConstant.TransactionType.FREEZE && (info.getOldDepositNo() == null || "".equals(info.getOldDepositNo()) ))
                    {
                        lResult = accountDAO.updateStatus(info.getAccountId(),SETTConstant.AccountStatus.convertFromSubAccount(info.getSubAccountNewStatus()));
                    }
////                如果是换发证书，则修改证书信息；
                    if(info.getTransActionType()==SETTConstant.TransactionType.CHANGECERTIFICATE)
                    {
                        subAccountDAO.updateFixedForm(lSubAccountID,info.getNewDepositNo());
                    }
////                如果是部分冻结，则修改冻结金额；
                    if(info.getTransActionType()==SETTConstant.TransactionType.FREEZE && info.getSubAccountNewStatus()==SETTConstant.SubAccountStatus.PARTFREEZE)
                    {
                        subAccountDAO.addCapitalLimitAmount(lSubAccountID,info.getFreezeAmount());
                    }
                    
                }
                else if(lOperationType == SETTConstant.TransactionOperate.DELETE) 
                {
                	
                	/*
                    if(info.getTransActionType()==SETTConstant.TransactionType.FREEZE && info.getSubAccountNewStatus()==SETTConstant.SubAccountStatus.PARTFREEZE)
                    {
                        if(dtemp == 0)
                        {
                            lResult = subAccountDAO.updateStatus(lSubAccountID,info.getSubAccountOldStatus());
                            /*
                             * 活期账号还要更改账号状态
                             */
                	/*
                            if(info.getTransActionType()==SETTConstant.TransactionType.FREEZE && (info.getOldDepositNo() == null || "".equals(info.getOldDepositNo()) ))
                            {
                                lResult = accountDAO.updateStatus(info.getAccountId(),SETTConstant.AccountStatus.convertFromSubAccount(info.getSubAccountOldStatus()));
                            }
                        }    
                    }
                    else
                    {
                        lResult = subAccountDAO.updateStatus(lSubAccountID,info.getSubAccountOldStatus());
                        /*
                         * 活期账号还要更改账号状态
                         */
                	/*
                        if(info.getTransActionType()==SETTConstant.TransactionType.FREEZE && (info.getOldDepositNo() == null || "".equals(info.getOldDepositNo()) ))
                        {
                            lResult = accountDAO.updateStatus(info.getAccountId(),SETTConstant.AccountStatus.convertFromSubAccount(info.getSubAccountOldStatus()));
                        }
                    }
                  */
                   
////                如果是换发证书，则修改证书信息；
                    if(info.getTransActionType()==SETTConstant.TransactionType.CHANGECERTIFICATE)
                    {
                        subAccountDAO.updateFixedForm(lSubAccountID,info.getOldDepositNo());
                    }
                    ////如果是部分冻结，则修改冻结金额；
                    /*
                    if(info.getTransActionType()==SETTConstant.TransactionType.FREEZE && info.getSubAccountNewStatus()==SETTConstant.SubAccountStatus.PARTFREEZE)
                    {
                       subAccountDAO.reduceCapitalLimitAmount(lSubAccountID,info.getFreezeAmount());
                    }
                    */
                }
                else if(lOperationType == SETTConstant.TransactionOperate.CANCELCHECK)
                {
                	lResult = subAccountDAO.updateStatus(lSubAccountID,info.getSubAccountOldStatus());
                    /*
                     * 活期账号还要更改账号状态
                     */
                    if(info.getTransActionType()==SETTConstant.TransactionType.FREEZE && (info.getOldDepositNo() == null || "".equals(info.getOldDepositNo()) ))
                    {
                        lResult = accountDAO.updateStatus(info.getAccountId(),SETTConstant.AccountStatus.convertFromSubAccount(info.getSubAccountOldStatus()));
                    }
                
                   //如果是部分冻结，则修改冻结金额；
                   if(info.getTransActionType()==SETTConstant.TransactionType.FREEZE && info.getSubAccountNewStatus()==SETTConstant.SubAccountStatus.PARTFREEZE)
                   {
                   
                    subAccountDAO.reduceCapitalLimitAmount(lSubAccountID,info.getFreezeAmount());
                    
                   }
                }
                
            }
            else if (info.getTransActionType()==SETTConstant.TransactionType.REPORTFIND || info.getTransActionType()==SETTConstant.TransactionType.DEFREEZE)
            {
                freezeAmount = sett_ReportLossOrFreezeDAO.findCapitalLimitAmountById(lSubAccountID);
                double dtemp =1;	//	比较部分解冻后限制金额
                dtemp = freezeAmount-info.getFreezeAmount();
                
//              /如果复核则修改修改状态，否则恢复状态
                if(lOperationType == SETTConstant.TransactionOperate.CHECK )
                {
                    if(info.getTransActionType()==SETTConstant.TransactionType.DEFREEZE && info.getSubAccountOldStatus()==SETTConstant.SubAccountStatus.PARTFREEZE)
                    {	
                        if(dtemp<0)
                        {
                            throw new IException("发生业务异常解冻金额大于已冻结金额");
                        }
                        if(dtemp >= 0)	
                        {
                            lResult = subAccountDAO.updateStatus(lSubAccountID,info.getSubAccountNewStatus());
                            /*
                             * 活期账号还要更改账号状态
                             */
                            if(info.getTransActionType()==SETTConstant.TransactionType.DEFREEZE && (info.getOldDepositNo() == null || "".equals(info.getOldDepositNo()) ))
                            {
                                lResult = accountDAO.updateStatus(info.getAccountId(),SETTConstant.AccountStatus.convertFromSubAccount(info.getSubAccountNewStatus()));
                            }
                        }
                        /*
                        else
                        {
                            lResult = subAccountDAO.updateStatus(lSubAccountID,info.getSubAccountOldStatus());
                            /*
                             * 活期账号还要更改账号状态
                             */
                        /*
                            if(info.getTransActionType()==SETTConstant.TransactionType.DEFREEZE && (info.getOldDepositNo() == null || "".equals(info.getOldDepositNo()) ))
                            {
                                lResult = accountDAO.updateStatus(info.getAccountId(),SETTConstant.AccountStatus.convertFromSubAccount(info.getSubAccountOldStatus()));
                            }
                        }
                         */
                        
                    }
                    else
                    {
                        /*
                         * 活期账号还要更改账号状态
                         */
                        if(info.getTransActionType()==SETTConstant.TransactionType.DEFREEZE && (info.getOldDepositNo() == null || "".equals(info.getOldDepositNo()) ))
                        {
                            lResult = accountDAO.updateStatus(info.getAccountId(),SETTConstant.AccountStatus.convertFromSubAccount(info.getSubAccountNewStatus()));
                        }
                        lResult = subAccountDAO.updateStatus(lSubAccountID,info.getSubAccountNewStatus());
                    } 
                    
                    //如果是部分冻结，则修改冻结金额；
                    if(info.getTransActionType()==SETTConstant.TransactionType.DEFREEZE && info.getSubAccountOldStatus()==SETTConstant.SubAccountStatus.PARTFREEZE)
                    {
                      
                        subAccountDAO.reduceCapitalLimitAmount(lSubAccountID,info.getFreezeAmount());
                    }
                }
                else if(lOperationType == SETTConstant.TransactionOperate.CANCELCHECK) 
                {
                   
                        lResult = subAccountDAO.updateStatus(lSubAccountID,info.getSubAccountOldStatus());
                        /*
                         * 活期账号还要更改账号状态
                         */
                        if(info.getTransActionType()==SETTConstant.TransactionType.DEFREEZE && (info.getOldDepositNo() == null || "".equals(info.getOldDepositNo()) ))
                        {
                            lResult = accountDAO.updateStatus(info.getAccountId(),SETTConstant.AccountStatus.convertFromSubAccount(info.getSubAccountOldStatus()));
                        }
                    
                    
                   
                    //如果是部分解冻，则修改冻结金额；
                    if(info.getTransActionType()==SETTConstant.TransactionType.DEFREEZE && info.getSubAccountOldStatus()==SETTConstant.SubAccountStatus.PARTFREEZE)
                    {
                       
                        subAccountDAO.addCapitalLimitAmount(lSubAccountID,info.getFreezeAmount());
                        
                    }
                }                  
            }   
        }
        catch(IException ef)
        {
            throw ef;
        } 
        catch(Exception e)
        {
            throw new IException("Sett_E057");
        } 
        return lResult;
    }

    //担保收款业务,手续费科目查询.    定期计提利息科目查询.
    public GLSubjectDefinitionInfo getSubjectByAccountID(long AccountID, long subAccountID, int subjectType) throws IException
	{
	    String strSubject = null;
	    long accountTypeID = -1;
	    GLSubjectDefinitionInfo subjectDefinitionInfo = new GLSubjectDefinitionInfo();
	    try
	    {
	        long accoontID = AccountID;
	        accountTypeID = -1;
	        Sett_AccountDAO accountDAO = new Sett_AccountDAO(conn);
	        AccountInfo accountInfo = accountDAO.findByID(accoontID);
	        strSubject = accountInfo.getSubject();
	        accountTypeID = accountInfo.getAccountTypeID();	        
	        
	        
	        //added by mzh_fu 2008/05/12 国电需求
        	if(subAccountID<0)
        	{
        		throw new IException("找不到该活期子账户信息,导致生成会计分录失败");
        	}
       
        	Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO(conn);
	        SubAccountAssemblerInfo subAccountAssemblerInfo = subAccountDAO.findByID(subAccountID);
	        if(null == subAccountAssemblerInfo.getSubAccountCurrenctInfo())
	        {
	        	throw new IException("找不到该活期子账户");
	        }    
	        
	        
	        
	        if ((subjectType == AccountOperation.SUBJECT_TYPE_ACCOUNT && strSubject == null) || (subjectType == AccountOperation.SUBJECT_TYPE_INTEREST || subjectType == AccountOperation.SUBJECT_TYPE_PREDRAWINTEREST || subjectType == AccountOperation.SUBJECT_TYPE_INTERESTTAX  || subjectType == AccountOperation.SUBJECT_TYPE_COMMISSION || subjectType == AccountOperation.SUBJECT_TYPE_NEGOTIATEINTEREST))
	        {
	            long accountGroupType = com.iss.itreasury.settlement.util.SETTConstant.AccountType.getAccountGroupTypeIDByAccountTypeID(accountTypeID);
	            Sett_AccountTypeDAO accountTypeDAO = new Sett_AccountTypeDAO(conn);
	            AccountTypeInfo accountTypeInfo = accountTypeDAO.findByID(accountTypeID);
	            long isExistSubClass = accountTypeInfo.getIsExistSubClass();
	            
	            if (subjectType == AccountOperation.SUBJECT_TYPE_ACCOUNT && isExistSubClass != 1)
	            {
	                Sett_CurrencySubjectDAO currencySubjectDAO = new Sett_CurrencySubjectDAO(conn);
	                strSubject = currencySubjectDAO.findSubjectCodeByTableNameAndAccoutTypeIDAndCurrencyIDAndOfficeID("Sett_accounttype", accountTypeID, accountInfo.getCurrencyID(), accountInfo.getOfficeID());
	            } 
	            else if( isExistSubClass == 1 
            	      && (accountGroupType == SETTConstant.AccountGroupType.CURRENT
					  || accountGroupType == SETTConstant.AccountGroupType.OTHERDEPOSIT
					  || accountGroupType == SETTConstant.AccountGroupType.MARGIN) )
	            {
	                Sett_SubAccountType_CurrentSettingDAO subAccountType_CurrentSettingDAO = new Sett_SubAccountType_CurrentSettingDAO();
	                
					//added by mzh_fu 2008/04/29 国电需求，对于活期增加按账户下级分类；保证金按账户、存单下级分类
					if(accountGroupType == SETTConstant.AccountGroupType.MARGIN){							
						SubAccountTypeFixedDepositInfo subAccountTypeFixedDepositInfo = new SubAccountTypeFixedDepositInfo();
						subAccountTypeFixedDepositInfo.setAccountId(subAccountAssemblerInfo.getSubAccountFixedInfo().getAccountID());
						subAccountTypeFixedDepositInfo.setDepositNo(subAccountAssemblerInfo.getSubAccountFixedInfo().getDepositNo());
						
						accountTypeInfo.setSubAccountTypeCurrentSettingInfo(null);
						accountTypeInfo.setSubAccountTypeFixedDepositInfo(subAccountTypeFixedDepositInfo);							
					}else{														
						SubAccountTypeCurrentSettingInfo subAccountTypeCurrentSettingInfo = new SubAccountTypeCurrentSettingInfo();
						subAccountTypeCurrentSettingInfo.setAccountId(subAccountAssemblerInfo.getSubAccountCurrenctInfo().getAccountID());
						
						accountTypeInfo.setSubAccountTypeFixedDepositInfo(null);
						accountTypeInfo.setSubAccountTypeCurrentSettingInfo(subAccountTypeCurrentSettingInfo);
					}
	                
	                
	                switch(subjectType)
		            {
						case AccountOperation.SUBJECT_TYPE_ACCOUNT:
						{
							log.debug("---------取账户对应的科目-------------");
							strSubject = subAccountType_CurrentSettingDAO.findAccountSubjectCodeByAccountTypeIDAndClientID(accountTypeInfo, accountInfo.getOfficeID(), accountInfo.getCurrencyID(),accountInfo.getClientID());
						}
						break;
						case AccountOperation.SUBJECT_TYPE_INTEREST:
						{
							log.debug("---------取利息支出对应的科目-------------");
							strSubject = subAccountType_CurrentSettingDAO.findPayInterestSubjectCodeByAccountTypeIDAndClientID(accountTypeInfo, accountInfo.getOfficeID(), accountInfo.getCurrencyID(),accountInfo.getClientID());
						}
						break;
						case AccountOperation.SUBJECT_TYPE_PREDRAWINTEREST:
						{
							log.debug("---------取计提利息对应的科目-------------");
							strSubject = subAccountType_CurrentSettingDAO.findPredrawInterestSubjectCodeByAccountTypeIDAndClientID(accountTypeInfo,accountInfo.getOfficeID(), accountInfo.getCurrencyID(),accountInfo.getClientID());
						}
						break;
						case AccountOperation.SUBJECT_TYPE_NEGOTIATEINTEREST:
						{
							log.debug("---------取协定利息支出对应的科目-------------");
							strSubject = subAccountType_CurrentSettingDAO.findNegotiateInterestSubjectCodeByAccountTypeIDAndClientID(accountTypeInfo,accountInfo.getOfficeID(), accountInfo.getCurrencyID(),accountInfo.getClientID());
						}
						break;
						case AccountOperation.SUBJECT_TYPE_COMMISSION:
						{
							log.debug("---------取手续费对应的科目-------------");
							strSubject = subAccountType_CurrentSettingDAO.findCommissionSubjectCodeByAccountTypeIDAndClientID(accountTypeInfo,accountInfo.getOfficeID(), accountInfo.getCurrencyID(),accountInfo.getClientID());
						}
						break;
		            }
	            }
				else if ( accountGroupType == SETTConstant.AccountGroupType.FIXED
					   || accountGroupType == SETTConstant.AccountGroupType.NOTIFY )
                {
					
/*		            Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO(conn);
		            SubAccountAssemblerInfo subAccountAssemblerInfo = subAccountDAO.findByID(subAccountID);
		            
		            if(null == subAccountAssemblerInfo.getSubAccountCurrenctInfo())
		            {
		            	throw new IException("找不到该活期子账户");
		            }
*/
		            log.debug("-------------对应的主账户ID是: " + accoontID + "---------------");
		            long depositTerm = subAccountAssemblerInfo.getSubAccountFixedInfo().getDepositTerm();
		            long noticeDay = subAccountAssemblerInfo.getSubAccountFixedInfo().getNoticeDay();

                    Sett_SubAccounTtype_FixedDAO subAccounTtype_FixedDAO = new Sett_SubAccounTtype_FixedDAO(conn);
                    
                    
                    //added by mzh_fu 2008/04/29 国电需求，对于定期、通知增加按账户和存单下级分类
                    SubAccountTypeFixedDepositInfo subAccountTypeFixedDepositInfo = new SubAccountTypeFixedDepositInfo();
                    subAccountTypeFixedDepositInfo.setAccountId(subAccountAssemblerInfo.getSubAccountFixedInfo().getAccountID());                        
                    subAccountTypeFixedDepositInfo.setDepositNo(subAccountAssemblerInfo.getSubAccountFixedInfo().getDepositNo());
                    
                    accountTypeInfo.setSubAccountTypeFixedDepositInfo(subAccountTypeFixedDepositInfo);
                    
                    
                    if (depositTerm == -1 && noticeDay == -1)
                    {
                        throw new IException(true, "账户信息(定期存款期限或通知存款天数)不正确，交易失败", null);
                    }

                    if (noticeDay > 0)
                    {
                        depositTerm = noticeDay;
                    }
                    switch (subjectType)
                    {
                        case AccountOperation.SUBJECT_TYPE_ACCOUNT:
                        {
                            log.debug("---------取账户对应的科目-------------");
                            strSubject = subAccounTtype_FixedDAO.findAccountSubjectCodeByAccountTypeIDAndFixDepositMonthID(accountTypeInfo, depositTerm, accountInfo.getOfficeID(), accountInfo.getCurrencyID(),accountInfo.getClientID());
                        }
                            break;
                        case AccountOperation.SUBJECT_TYPE_INTEREST:
                        {
                            log.debug("---------取利息对应的科目-------------");
                            strSubject = subAccounTtype_FixedDAO.findInterestSubjectCodeByAccountTypeIDAndFixDepositMonthID(accountTypeInfo, depositTerm, accountInfo.getOfficeID(), accountInfo.getCurrencyID(),accountInfo.getClientID());
                        }
                            break;
                        case AccountOperation.SUBJECT_TYPE_PREDRAWINTEREST:
                        {
                            log.debug("---------取计提利息对应的科目-------------");
                            strSubject = subAccounTtype_FixedDAO.findPredrawInterestSubjectCodeByAccountTypeIDAndFixDepositMonthID(accountTypeInfo, depositTerm, accountInfo.getOfficeID(), accountInfo.getCurrencyID(),accountInfo.getClientID());
                        }
                            break;
                    }
                }
	        }
	        if(strSubject == null || strSubject.compareToIgnoreCase("") == 0)
	        {
	            strSubject = "";
	        }
	        
	        GeneralLedgerOperation glOperation = new GeneralLedgerOperation(conn);
	        if(!glOperation.isExistSubeject(strSubject, conn))
	        {
	            strSubject = "";
	        } 
	        else
	        {
	            Sett_GLSubjectDefinitionDAO subjectDefinitionDAO = new Sett_GLSubjectDefinitionDAO();
	            subjectDefinitionInfo = subjectDefinitionDAO.findByCode(strSubject);
	        }
	    }
	    catch(SQLException e)
	    {
	        throw new IException(true, e.getMessage(), e);
	    }
	    catch(Exception e)
	    {
	        throw new IException(true, e.getMessage(), e);
	    }

	    return subjectDefinitionInfo;
	}

	/**
	 * 到期续存处理计提利息和计提日期
	 * @param tadi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
    public long ContinueFixedPreDrawInterest(TransAccountDetailInfo tadi, String strCheckType) throws IException
    {
        long subAccountID = -1;
        Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conn);
        
        try
        {
        	subAccountID = tadi.getTransSubAccountID();
            sett_SubAccountDAO.ContinueFixedPreDrawInterest(subAccountID, strCheckType);
        }
        catch (SQLException e)
        {
            throw new IException(true, "到期续存处理计提利息和计提日期", e);
        }
        
        return subAccountID;
    }
    
    /**
     * 方法说明：根据子账户ID，得到客户编号
	 * Boxu Add 2008年4月30日
     * @param lSubAccountID
     * @return String
     * @throws IRollbackException
     */
    public String findClientCodeBySubAccountID(long lSubAccountID) throws IException
    {
        String clientCode = "";
        try
        {
            Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO(conn);
        	if(lSubAccountID < 0)
        	{
        		throw new IException("找不到子账户信息");
        	}
        	clientCode = subAccountDAO.findClientCodeBySubAccountID(lSubAccountID);
        }
    	catch (SQLException e)
        {
            throw new IException(true, "到期续存处理计提利息和计提日期", e);
        }        
           
        return clientCode;
    }
    
    /**
     * 方法说明：根据子账户ID，得到账户类型
	 * Boxu Add 2008年4月30日
     * @param lSubAccountID
     * @return String
     * @throws IRollbackException
     */
    public long findAccountTypeBySubAccountID(long lSubAccountID) throws IException
    {
        long accountType = -1;
        try
        {
            Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO(conn);
        	if(lSubAccountID < 0)
        	{
        		throw new IException("找不到子账户信息");
        	}
        	accountType = subAccountDAO.findAccountTypeBySubAccountID(lSubAccountID);
        }
    	catch (SQLException e)
        {
            throw new IException(true, "到期续存处理计提利息和计提日期", e);
        }        
           
        return accountType;
    }
    
    /**
	 * 修改外部银行账户信息
	 * 
	 * @param lID
	 *            外部银行账户ID
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long updateExtAccount(ExternalAccountInfo info) throws IException
	{
		long lReturn = -1;
		Sett_ExternalAccountDAO dao = new Sett_ExternalAccountDAO();
		try
		{
			lReturn = dao.update(info);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(true, e.getMessage(), e);
		}	
		return lReturn;
	}
	
	/**
	 * 删除外部银行账户信息
	 * 
	 * @param lID
	 *            外部银行账户ID
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long deletePayee(long lID) throws IException
	{
		long lReturn = -1;
		Sett_ExternalAccountDAO dao = new Sett_ExternalAccountDAO();
		try
		{
			lReturn = dao.deletePayee(lID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException(true, e.getMessage(), e);
		}
		return lReturn;
	}
	
	  /**
     * 方法说明：取消复核时更新子账户手续费金额
	 * Boxu Add 2008年4月30日
     * @param lSubAccountID
     * @return String
     * @throws IRollbackException
     */
    public long updateCommission(TransAccountDetailInfo info) throws RemoteException, IRollbackException
    {
        long accountType = -1;
        try
        {
            Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO(conn);
        	if(info.getTransSubAccountID() < 0)
        	{
        		throw new IException("找不到子账户信息");
        	}
        	accountType = subAccountDAO.updateCommission(info);
        }
    	catch (Exception e)
        {
            e.printStackTrace();
        }                   
        return accountType;
    }
    /**
     * 方法说明：查询账户当前可用余额
	 * minzhao Add 2010年11月28日
     * @param lAccountID
     * @throws IRollbackException
     */
    public double findAvailableBalance(long lAccountID,long lOfficeID,long lCurrencyID) throws RemoteException, IRollbackException
    {
    	double AvailableBalance = -1;
        try
        {
            Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO(conn);
            AvailableBalance = subAccountDAO.findAvailableBalance(lAccountID,lOfficeID,lCurrencyID);
        }
    	catch (Exception e)
        {
            e.printStackTrace();
        }                   
        return AvailableBalance;
    }
}