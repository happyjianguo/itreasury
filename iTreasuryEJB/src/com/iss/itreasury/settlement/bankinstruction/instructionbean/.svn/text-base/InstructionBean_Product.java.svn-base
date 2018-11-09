/**
 * 
 */
package com.iss.itreasury.settlement.bankinstruction.instructionbean;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


import com.iss.itreasury.settlement.account.bizlogic.Account;
import com.iss.itreasury.settlement.account.bizlogic.AccountHome;
import com.iss.itreasury.settlement.account.dao.Sett_AccountDAO;
import com.iss.itreasury.settlement.account.dao.sett_TransAccountDetailDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.account.dataentity.TransAccountDetailInfo;
import com.iss.itreasury.settlement.account.dataentity.TransBankDetailInfo;
import com.iss.itreasury.settlement.bankinstruction.IBankInstruction;
import com.iss.itreasury.settlement.bankinstruction.entity.CreateInstructionParam;
import com.iss.itreasury.settlement.bankinterface.bizlogic.BankInstructionOperation;
import com.iss.itreasury.settlement.bankinterface.dataentity.BankInstructionInfo;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.interest.dataentity.InterestQueryResultInfo;
import com.iss.itreasury.settlement.setting.dao.Sett_BranchDAO;
import com.iss.itreasury.settlement.setting.dao.Sett_FilialeAccountSetDAO;
import com.iss.itreasury.settlement.setting.dataentity.BankInstructionSettingInfo;
import com.iss.itreasury.settlement.setting.dataentity.BranchInfo;
import com.iss.itreasury.settlement.setting.dataentity.FilialeAccountInfo;
import com.iss.itreasury.settlement.transbakreserve.dataentity.TransBakReserveInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dao.Sett_TransCurrentDepositDAO;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransOnePayMultiReceiveInfo;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransDiscountDetailInfo;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransGrantDiscountInfo;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransRepaymentDiscountInfo;
import com.iss.itreasury.settlement.transfee.dao.Sett_TransFeeDAO;
import com.iss.itreasury.settlement.transfee.dataentity.TransFeeInfo;
import com.iss.itreasury.settlement.transferloancontract.dataentity.NoticeAndAgentDetailConditionInfo;
import com.iss.itreasury.settlement.transferloancontract.dataentity.NoticeAndAgentDetialResultInfo;
import com.iss.itreasury.settlement.transferloancontract.dataentity.TransferLoanContractInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedContinueInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedDrawInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedOpenInfo;
import com.iss.itreasury.settlement.transgeneralledger.dataentity.TransGeneralLedgerInfo;
import com.iss.itreasury.settlement.transloan.bizlogic.BankLoanQuery;
import com.iss.itreasury.settlement.transloan.dao.Sett_TransRepaymentLoanDAO;
import com.iss.itreasury.settlement.transloan.dataentity.BankLoanDrawInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo;
import com.iss.itreasury.settlement.transreserve.dataentity.TransReserveInfo;
import com.iss.itreasury.settlement.transspecial.dataentity.TransSpecialOperationInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.bankportal.integration.client.BPClientAgent;
import com.iss.itreasury.bankportal.integration.constant.RemitPriority;
import com.iss.itreasury.bankportal.integration.constant.TransType;
import com.iss.itreasury.bankportal.integration.info.ReqGetGeneralBankAcctInfo;
import com.iss.itreasury.bankportal.integration.info.ReqQueryInstructionInfo;
import com.iss.itreasury.bankportal.integration.info.RespGetGeneralBankAcctInfo;
import com.iss.itreasury.bankportal.integration.info.RespQueryInstructionInfo;
import com.iss.itreasury.bs.util.BranchIdentify;
import com.iss.itreasury.craftbrother.transferloancontract.counterparty.dao.CounterpartDao;
import com.iss.itreasury.craftbrother.transferloancontract.counterparty.dataentity.CounterpartBankInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transfercontract.bizlogic.TransferContractBiz;
import com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dataentity.TransferContractInfo;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.TransMarginOpenInfo;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.TransMarginWithdrawInfo;
import com.iss.itreasury.settlement.account.dao.Sett_SubAccountDAO;
import com.iss.itreasury.settlement.account.dataentity.SubAccountAssemblerInfo;
import com.iss.itreasury.settlement.transfinance.dataentity.TransReceiveFinanceInfo;
import com.iss.itreasury.settlement.transfinance.dataentity.TransReturnFinanceInfo;
import com.iss.itreasury.settlement.bankinterface.dao.Bs_BankAccountInfoDAO;
import com.iss.itreasury.settlement.bizdelegation.BankInstructionSettingDelegation;
import com.iss.itreasury.settlement.bizdelegation.TransferLoanContractDelegation;
import com.iss.itreasury.settlement.craftbrother.dataentity.TransCraftbrotherInfo;

/**
 * @author qijiang
 * 南航生成指令操作实现类
 * 修改历史:			
 * mzh_fu 2007/03/30 增加根据设置,判断是否生成指令(
 * 之前的类似于com.iss.itreasury.settlement.accountbook.bizlogic:isCreateInstruction(long bankID)这样的方法将永远返回true
 * 不再作为判断是否生成指令的条件)
 */


public class InstructionBean_Product implements IBankInstruction{

	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	
	/**
	 * 
	 */
	public InstructionBean_Product() { 
		super();
	}
	
	/**
	 * 根据账户ID得指令设置相关信息
	 * @param bankInstructionSettingQueryInfo
	 * @return BankInstructionSettingInfo
	 * @throws IException
	 */
	public BankInstructionSettingInfo getSettingInfoByAccountId(
			BankInstructionSettingInfo bankInstructionSettingQueryInfo)
			throws IException {
		
		return new BankInstructionSettingDelegation()
				.getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
	}
	/**
	 * 根据业务类型得指令设置相关信息(转账方式为可选条件)
	 * @param bankInstructionSettingQueryInfo
	 * @return BankInstructionSettingInfo
	 * @throws IException
	 */
	public BankInstructionSettingInfo getSettingInfoByTransType(
			BankInstructionSettingInfo bankInstructionSettingQueryInfo)
			throws IException {
		
		return new BankInstructionSettingDelegation()
				.getSettingInfoByTransType(bankInstructionSettingQueryInfo);
	}
	/**
	 * 根据账户ID得到该账户是否可以发送指令
	 * @param info 指令设置所需的信息
	 * @param accountId 账户ID
	 * @return
	 * @throws Exception
	 */
	private boolean isCreateBankInstructionByAccountId(
			BankInstructionSettingInfo info, long accountId) throws Exception {
		boolean blnReturn = false;
		try {
			blnReturn = new BankInstructionSettingDelegation()
					.isSendByAccountId(info, accountId);
		} catch (Exception e) {
			throw e;
		}
		return blnReturn;
	}

	/**
	 * 为多借多贷，定期部分支取,利息费用结算所作
	 * @param coll
	 * @throws IException
	 */
	public void createSpecialBankInstruction(Collection coll) throws IException {
		if(coll == null ||coll.size()<=0) {
			log.error("构造银行指令错误，指令参数为空！");
			throw new IException("构造银行指令错误，指令参数为空！");
		}
		try {
			Collection	colInstructionParams = new ArrayList();
			Collection colInstruction = null;
			int i=0;
			while(i<coll.size())
			{
				CreateInstructionParam param = (CreateInstructionParam)coll.toArray()[i];
				if (param.getTransactionTypeID()== SETTConstant.TransactionType.ONETOMULTI)//一付多收
				{	
					colInstruction = this.createOneToMultiIntruction(param);			
				}
				//定期支取
				else if(param.getTransactionTypeID()==SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER) {
					colInstruction = this.createWithdrawFixedDepositIntruction(param);
				}
				//定期开立
				else if (param.getTransactionTypeID() ==SETTConstant.TransactionType.OPENFIXEDDEPOSIT)
				{
					colInstruction = this.createOpenFixDepositIntruction(param,param.getTransactionTypeID());
				}
				//利息费用支付活期结息
				else if (param.getTransactionTypeID() == SETTConstant.TransactionType.INTEREST_FEE_PAY_CURRENT)
				{
					colInstruction = this.createInterestFeePayCurrentIntruction(param);
				}
				//利息费用支付保证金结息
				else if (param.getTransactionTypeID() == SETTConstant.TransactionType.INTEREST_FEE_PAY_MARGIN)
				{	
					colInstruction = this.createInterestFeePayMargnIntruction(param);
				}
				//利息费用支付自营贷款结息
				else if (param.getTransactionTypeID() == SETTConstant.TransactionType.TRUST_LOAN_INTEREST)
				{
					colInstruction = this.createTrustLoanInsterestIntruction(param);
				}
				//利息费用支付委贷结息
				else if (param.getTransactionTypeID() == SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST)
				{

					colInstruction = this.createConsignLoanInterestntruction(param);
				}
				
				if(colInstruction!=null && colInstruction.size()>0)
				{
					colInstructionParams.addAll(colInstruction);
				}
				i++;
			}
			if(colInstructionParams!=null&&colInstructionParams.size()>0)
			{
				BankInstructionOperation instructionOperation = new BankInstructionOperation();
			
				if(Config.getBoolean("settlement_BankInstruction_RemitPriority",false))
				{
					instructionOperation.addBankInstructions(updateRemitPriority(colInstructionParams));
				}
				else
				{
					instructionOperation.addBankInstructions(colInstructionParams);
				}
				
			}
		} catch (Exception e) {
            e.printStackTrace();
            throw new IException(e.getMessage());
		}
	}
	/**
	 * 生成银行付款指令
	 * @param param
	 * @throws IException
	 */
	public void createBankInstruction(CreateInstructionParam param) throws IException {
		log.debug("Enter into createBankInstruction(CreateInstructionParam param)");
		Collection	colInstructionParams = null;
		if (Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID,false)!= true ) {
			log.info("当前系统没有银企接口服务！");
			return;
		}
		
		log.info("指令参数1为："+param.getBankType());
		log.info("指令参数1为："+param.getCheckUserID());
		log.info("指令参数1为："+param.getCurrencyID());
		log.info("指令参数1为："+param.getOfficeID());
		log.info("指令参数1为："+param.getTransactionTypeID());
		log.info("指令参数1为："+param.getTransNo());
		if(param.getObjInfo() != null) {
			log.info("详细指令参数："+UtilOperation.dataentityToString(param.getObjInfo()));
		}
		
		
		if(param == null) {
			log.error("构造银行指令错误，指令参数为空！");
			throw new IException("构造银行指令错误，指令参数为空！");
		}
		if(param.getTransactionTypeID() == -1) {
			log.error("构造银行指令错误，交易类型ID参数为空！");
			throw new IException("构造银行指令错误，交易类型ID参数为空！");
		}
		if(param.getObjInfo() == null) {
			log.error("构造银行指令错误，交易对象参数为空！");
			throw new IException("构造银行指令错误，交易对象参数为空！");
		}
		/*if(param.getInputUserID() == -1) {
			log.info("非结算系统发起的交易，不形成银行指令！");
			return;
		}*/

		try {
			if(param.getTransactionTypeID() == 
				SETTConstant.TransactionType.FIXED_DEPOSIT_PREDRAW_INTEREST) {
				log.info("\"定期存款计提应付利息（含冲销）\"业务类型不形成银行指令！");				
			}
			else if(param.getTransactionTypeID() == 
				SETTConstant.TransactionType.TRUST_LOAN_PREDRAW_INTEREST) {
				log.info("\"自营贷款计提应收利息（含冲销）\"业务类型不形成银行指令！");				
			}
			//形成特殊业务指令
			else if(param.getTransactionTypeID() == 
				SETTConstant.TransactionType.SPECIALOPERATION) {

				colInstructionParams = this.createSpecialOperationIntruction(param);
			}
			//形成 机构-备付金上存
			else if(param.getTransactionTypeID() == 
				SETTConstant.TransactionType.JGBAKUPSAVE) {

				colInstructionParams = this.createBakAndReserveUpSaveOperationIntruction(param);
			}
			//形成 机构-准备金上存
			else if(param.getTransactionTypeID() == 
				SETTConstant.TransactionType.JGRESERVEUPSAVE) {

				colInstructionParams = this.createBakAndReserveUpSaveOperationIntruction(param);
			}
			//形成贴现发放指令
			else if(param.getTransactionTypeID()==
				SETTConstant.TransactionType.DISCOUNTGRANT) { 

				colInstructionParams = this.createGrantDiscountIntruction(param);
			}
         //形成转贴现发放指令
			else if(param.getTransactionTypeID()==
				SETTConstant.TransactionType.TRANSDISCOUNTGRANT) { 

				colInstructionParams = this.createGrantTransDiscountIntruction(param);
			}
			//形成委托贷款发放，自营贷款发放，银团贷款发放等业务指令
			else if(param.getTransactionTypeID()==
				SETTConstant.TransactionType.BANKGROUPLOANGRANT 
				|| param.getTransactionTypeID()==
					SETTConstant.TransactionType.TRUSTLOANGRANT 
				|| param.getTransactionTypeID()==
					SETTConstant.TransactionType.CONSIGNLOANGRANT) {
				
				colInstructionParams = this.createGrantLoanIntruction(param);
			}
			//形成委托贷款收回业务指令
			else if(param.getTransactionTypeID()==
					SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
				{

				colInstructionParams = this.createReceiveConsignLoanIntruction(param);
			}
			//形成自营贷款收回，银团贷款收回业务指令
			else if(param.getTransactionTypeID()==
				SETTConstant.TransactionType.TRUSTLOANRECEIVE
				|| param.getTransactionTypeID()==
					SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
				{
				
				colInstructionParams = this.createReceiveLoanIntruction(param);
			}
			//形成贴现收回业务指令
			else if(param.getTransactionTypeID()==
				SETTConstant.TransactionType.DISCOUNTRECEIVE)
				{

				colInstructionParams = this.createReceiveDiscountIntruction(param);
			}
			//形成通知存款支取、定期支取指令，后两个是为处理通知存款支取、定期支取的本金和利息指令而定义的类型
			else if(param.getTransactionTypeID()==
				SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER 
				|| param.getTransactionTypeID()==
					SETTConstant.TransactionType.NOTIFYDEPOSITDRAW 
					||  param.getTransactionTypeID()==
						SETTConstant.TransactionType.DRAW_PRINCIPAL
						||  param.getTransactionTypeID()==
							SETTConstant.TransactionType.DRAW_INTEREST) {
				//this.createWithdrawFixedDepositIntruction(param,param.getTransactionTypeID());
				colInstructionParams = this.createWithdrawFixedDepositIntruction(param);
			}
			//形成定期续期转存指令
			else if(param.getTransactionTypeID()==
				SETTConstant.TransactionType.FIXEDCONTINUETRANSFER) {
				
				colInstructionParams = this.createContinueFixedDepositIntruction(param);
			}
			//形成交易类型为银行付款－直接支付的指令
			else if(param.getTransactionTypeID()==
				SETTConstant.TransactionType.BANKPAY) {

				colInstructionParams = this.createBankPay(param);
			}
			//形成交易类型为下划成员单位、下拨子账户的指令
			else if(param.getTransactionTypeID()==SETTConstant.TransactionType.BANKPAY_DOWNTRANSFER || 
				param.getTransactionTypeID()==SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT) {
				
				colInstructionParams = this.createBankPayDown(param);
			}
			//形成总账交易的指令
			else if(param.getTransactionTypeID()==
				SETTConstant.TransactionType.GENERALLEDGER) {
				
				colInstructionParams = this.createGeneralLedgerborrowIntruction(param);
			}
			//形成委托存款的指令
			else if(param.getTransactionTypeID()==
				SETTConstant.TransactionType.CONSIGNSAVE) {

				colInstructionParams = this.createConsignIntruction(param);
				
			}
			//一付多收 指令
			else if (param.getTransactionTypeID()
					== SETTConstant.TransactionType.ONETOMULTI)//一付多收
			{	
				colInstructionParams = this.createOneToMultiIntruction(param);			
			}			
			//银行收款
			else if (param.getTransactionTypeID() == 
				SETTConstant.TransactionType.BANKRECEIVE)
			{
				colInstructionParams = this.createBankReceive(param);
			}
			//内部转账
			else if (param.getTransactionTypeID() == 
				SETTConstant.TransactionType.INTERNALVIREMENT)
			{
				colInstructionParams = this.createInternalvirmentIntruction(param);
			}
			//定期开立,通知开立
			else if (param.getTransactionTypeID() == 
				SETTConstant.TransactionType.OPENFIXEDDEPOSIT
				||param.getTransactionTypeID() == 
					SETTConstant.TransactionType.OPENNOTIFYACCOUNT)
			{
				colInstructionParams = this.createOpenFixDepositIntruction(param,param.getTransactionTypeID());
			}
			//利息费用支付活期结息
			else if (param.getTransactionTypeID() == 
				SETTConstant.TransactionType.INTEREST_FEE_PAY_CURRENT)
			{
				colInstructionParams = this.createInterestFeePayCurrentIntruction(param);
			}
			//利息费用支付保证金结息
			else if (param.getTransactionTypeID() == 
				SETTConstant.TransactionType.INTEREST_FEE_PAY_MARGIN)
			{	
				colInstructionParams = this.createInterestFeePayMargnIntruction(param);
			}
			//利息费用支付自营贷款结息
			else if (param.getTransactionTypeID() == 
				SETTConstant.TransactionType.TRUST_LOAN_INTEREST)
			{
				colInstructionParams = this.createTrustLoanInsterestIntruction(param);
			}
			//利息费用支付委贷结息
			else if (param.getTransactionTypeID() == 
				SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST)
			{

				colInstructionParams = this.createConsignLoanInterestntruction(param);
			}
			//利息支付－自营、委贷利息
			else if (param.getTransactionTypeID() == 
				SETTConstant.TransactionType.INTERESTFEEPAYMENT)
			{	
				colInstructionParams = this.createInterestPay(param);
			}
			//利息支付－自营担保费
			else if (param.getTransactionTypeID() == 
				SETTConstant.TransactionType.INTERESTFEEPAYMENT_SURETY)
			{

				colInstructionParams = this.createTrustLoanInterestTruestyPay(param);
			}
			//利息支付－委贷手续费
			else if (param.getTransactionTypeID() == 
				SETTConstant.TransactionType.INTERESTFEEPAYMENT_COMMISION)
			{

				colInstructionParams = this.createConsignLoanInterestCommisionPay(param);
			}
			//保证金开立
			else if(param.getTransactionTypeID()==SETTConstant.TransactionType.OPENMARGIN)
			{

				colInstructionParams = this.createOpenMarginDeposit(param);
			}
			
			//保证金支取
			else if(param.getTransactionTypeID()==SETTConstant.TransactionType.WITHDRAWMARGIN)
			{

				colInstructionParams = this.createWithdrawMarginDeposit(param);
			}
			//融资租赁收款
			else if (param.getTransactionTypeID()==SETTConstant.TransactionType.RECEIVEFINANCE)
			{

				colInstructionParams = this.createReceiveFinance(param);
			}
			//融资租赁付款
			else if (param.getTransactionTypeID()==SETTConstant.TransactionType.RETURNFINANCE)
			{

				colInstructionParams = this.createReturnFinance(param);
			}
			//信贷资产转让付款
			else if (param.getTransactionTypeID() == SETTConstant.TransactionType.TRANSFERPAY)
			{
				
				colInstructionParams = this.createCraTransferPay(param);
				
			}
			//信贷资产转让代收款
			else if (param.getTransactionTypeID() == SETTConstant.TransactionType.AGENTTRANSFERREPAY)
			{
				
				colInstructionParams = this.createCraTransferClientRePay(param);
				
			}
			//备付金上收银行指令
			else if (param.getTransactionTypeID() == SETTConstant.TransactionType.BAKUPRECEIVE)
			{
				
				log.info("\"备付金上收\"业务类型不形成银行指令！");
				
			}
			//准备金上收银行指令
			else if (param.getTransactionTypeID() == SETTConstant.TransactionType.RESERVEUPRECEIVE)
			{
				
				log.info("\"准备金上收\"业务类型不形成银行指令！");
				
			}
			//备付金调回银行指令
			else if (param.getTransactionTypeID() == SETTConstant.TransactionType.BAKRETURN)
			{
				
				colInstructionParams = this.createBakReserveReturnInstruction(param);
				
			}
			//准备金调回银行指令
			else if (param.getTransactionTypeID() == SETTConstant.TransactionType.RESERVERETURN)
			{
				
				colInstructionParams = this.createReserveReturnInstruction(param);
				
			}
			//同业往来（转贴现发放、转贴现购回、资金拆借、资金拆借返款资产转让、资产转让购回、资产转让利息支付）
			else if (param.getTransactionTypeID() ==SETTConstant.TransactionType.TRANS_DISCOUNT_GRANT
					||param.getTransactionTypeID() ==SETTConstant.TransactionType.TRANS_DISCOUNT_REPURCHASE
					||param.getTransactionTypeID() ==SETTConstant.TransactionType.FUND_ATTORN
					||param.getTransactionTypeID() ==SETTConstant.TransactionType.FUND_ATTORN_REPAY
					||param.getTransactionTypeID() ==SETTConstant.TransactionType.SAME_BUSINESS_GRANT
					||param.getTransactionTypeID() ==SETTConstant.TransactionType.SAME_BUSINESS_RECEIVE
					||param.getTransactionTypeID() ==SETTConstant.TransactionType.SAME_BUSINESS_INTERESTPROCESS)
			{
				colInstructionParams = this.createCraftBrotherInstruction(param);
			}
			else{
				log.info("不进行处理的交易类型:"+param.getTransactionTypeID());
				return;
			}
			if(colInstructionParams!=null&&colInstructionParams.size()>0)
			{
				BankInstructionOperation instructionOperation = new BankInstructionOperation();
				if(Config.getBoolean("settlement_BankInstruction_RemitPriority",false))
				{
					instructionOperation.addBankInstructions(updateRemitPriority(colInstructionParams));
				}
				else
				{
					instructionOperation.addBankInstructions(colInstructionParams);
				}
			}
		} catch (Exception e) {
			log.debug("-----保存银行转账指令失败");
            e.printStackTrace();
            throw new IException(e.getMessage());
		}
	}		
	
	
	private Collection createReturnFinance(CreateInstructionParam param) throws IException{
		
		if(param.getObjInfo() == null) {
			log.error("融资租赁还款指令错误，交易对象参数为空！");
			throw new IException("融资租赁还款指令错误，交易对象参数为空！");
		}
		Collection colInstructionParams = new ArrayList();
		log.info("生成融资租赁还款指令");
		TransReturnFinanceInfo transInfo=(TransReturnFinanceInfo)param.getObjInfo();

		try{
		    
			if(transInfo.getReturnCorpusAccountID()>0 ){
				//added by mzh_fu 2007/03/30 是否生成指令检验
				/*********************begin*****************************/
				long lAccouontID=transInfo.getReturnCorpusAccountID();
				
				BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
				bankInstructionSettingQueryInfo.setAccountId(lAccouontID);
				//added by mzh_fu 2007/09/27
				bankInstructionSettingQueryInfo.setTransModule(SETTConstant.TransModule.INSIDE_PAY);// 内转
				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
				
				if (_bankInstructionSettingInfo.getIsSend() != Constant.TRUE){ 
					return null;
				}
				/**********************end****************************/
				/**二级户账户模式*/
				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL)
				{
					
					//普通付款方式
					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
					{
						//付款方：活期账户对应的银行二级账户；收款方：银行二级账户对应的上级银行账户
							BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
							AccountInfo accountInfo=new AccountInfo();
							Sett_AccountDAO dao=new Sett_AccountDAO();
							accountInfo=dao.findByID(transInfo.getReturnCorpusAccountID());
							//付款方
							bankInstructionInfo.setPayAccountNo(accountInfo.getAccountNo());
							//
							//收款方
							ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
							instruction.setSystemId(1);//系统id
							instruction.setReferenceCode(accountInfo.getAccountNo());
							RespGetGeneralBankAcctInfo  generalbankacctinfo = new RespGetGeneralBankAcctInfo();
							generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
							if(generalbankacctinfo==null || generalbankacctinfo.getAccountInfo()==null ||generalbankacctinfo.getStatus()!=1){
								throw new Exception("未找到付款方对应的上级银行账户信息"); 
							}
							bankInstructionInfo.setReceiveAccountNo(generalbankacctinfo.getAccountInfo().getAccountNO());
							bankInstructionInfo.setReceiveAccountName(generalbankacctinfo.getAccountInfo().getAccountName());
							bankInstructionInfo.setReceiveBranchCodeOfBank(generalbankacctinfo.getAccountInfo().getBankCode());
							bankInstructionInfo.setReceiveBranchName(generalbankacctinfo.getAccountInfo().getBankName());
							bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctinfo.getAccountInfo().getBranchProvince());
							bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctinfo.getAccountInfo().getBranchCity());
							//
							//金额，其他信息
							bankInstructionInfo.setTransType(TransType.NORMAL);
							bankInstructionInfo.setAmount(transInfo.getCorpusAmount()+transInfo.getInterestAmount()-transInfo.getBailAmount());				
							bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
							bankInstructionInfo.setOfficeId(transInfo.getOfficeID());				
							bankInstructionInfo.setAbstract(transInfo.getAbstract());
							bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
							bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
							bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
							bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
							colInstructionParams.add(bankInstructionInfo);
					//代理汇兑方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
						
						
					//先拨后支方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
						
						
					}
					
				/**门户账户模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
						
				/**收支两条线模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
					}
				}
			//added by mzh_fu 2007/08/04 TODO
			else{
				////added by mzh_fu 账户模式与付款方式分开后,是否发送指令的控制
    			//转账方式:对外;业务类型:info.getTransactionTypeID()
    			BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingQueryInfo.setTransType(transInfo.getTransactionTypeID());
				bankInstructionSettingQueryInfo.setTransModule(SETTConstant.TransModule.BANK_PAY);
    			
				if(getSettingInfoByTransType(bankInstructionSettingQueryInfo).getIsSend() != Constant.TRUE){ 
					return null;
				}	
				
				//TODO 
			}
		}
		catch(Exception e){
			log.info("根据交易对象形成融资租赁还款指令时出错，无法形成指令！");
			e.printStackTrace();
			throw new IException("根据交易对象形成融资租赁还款指令时出错，无法形成指令！"+e.getMessage());
		}
		return colInstructionParams;
	}

	private Collection createReceiveFinance(CreateInstructionParam param) throws IException{
		if(param.getObjInfo() == null) {
			log.error("融资租赁收款指令错误，交易对象参数为空！");
			throw new IException("融资租赁收款指令错误，交易对象参数为空！");
		}
		log.info("生成融资租赁收款指令");
		Collection colInstructionParams = new ArrayList();
		try{
			TransReceiveFinanceInfo transInfo=(TransReceiveFinanceInfo)param.getObjInfo();
		    
			if(transInfo.getPayBailAccountID()>0 && transInfo.getBailAmount()>0){
				//added by mzh_fu 2007/03/30 是否生成指令检验
				/*********************begin*****************************/
				long lAccouontID=transInfo.getPayBailAccountID();

				BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
				bankInstructionSettingQueryInfo.setAccountId(lAccouontID);

				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
				
				if (_bankInstructionSettingInfo.getIsSend() != Constant.TRUE){ 
					return null;
				}
				
				/**二级户账户模式*/
				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL)
				{
					
					//普通付款方式
					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
					{
						//付款方:活期账户对应的银行二级账户；收款方：银行二级户对应的上级银行账户
						BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
						AccountInfo accountInfo=new AccountInfo();
						Sett_AccountDAO dao=new Sett_AccountDAO();
						accountInfo=dao.findByID(transInfo.getPayBailAccountID());
						//付款方
						bankInstructionInfo.setPayAccountNo(accountInfo.getAccountNo());
						//
						//收款方
						ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
						instruction.setSystemId(1);//系统id
						instruction.setReferenceCode(accountInfo.getAccountNo());
						RespGetGeneralBankAcctInfo  generalbankacctinfo = new RespGetGeneralBankAcctInfo();
						generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
						if(generalbankacctinfo==null|| generalbankacctinfo.getAccountInfo()==null||generalbankacctinfo.getStatus()!=1){
							throw new Exception("未找到付款方对应上级银行账户"); 
						}
						bankInstructionInfo.setReceiveAccountNo(generalbankacctinfo.getAccountInfo().getAccountNO());
						bankInstructionInfo.setReceiveAccountName(generalbankacctinfo.getAccountInfo().getAccountName());
						bankInstructionInfo.setReceiveBranchCodeOfBank(generalbankacctinfo.getAccountInfo().getBankCode());
						bankInstructionInfo.setReceiveBranchName(generalbankacctinfo.getAccountInfo().getBankName());
						bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctinfo.getAccountInfo().getBranchProvince());
						bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctinfo.getAccountInfo().getBranchCity());
						//
						//金额，其他信息
						bankInstructionInfo.setTransType(TransType.NORMAL);
						bankInstructionInfo.setAmount(transInfo.getBailAmount());				
						bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
						bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
						bankInstructionInfo.setAbstract(transInfo.getAbstract());
						bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
						bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
						bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
						bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
						colInstructionParams.add(bankInstructionInfo);
					//代理汇兑方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
						
						
					//先拨后支方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
						
						
					}
					
				/**门户账户模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
						
				/**收支两条线模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
					}
				/**********************end****************************/			
			}

			//手续费：付款方为活期时需要发送银行指令，收款方为付款方活期账户对应的上级银行账户，金额为手续费金额。
			if(transInfo.getPayPoundageAccountID()>0 && transInfo.getPoundageAmount()>0){
				//added by mzh_fu 2007/03/30 是否生成指令检验
				/*********************begin*****************************/
				long lAccouontID=transInfo.getPayPoundageAccountID();


				BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
				bankInstructionSettingQueryInfo.setAccountId(lAccouontID);

				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
				
				if (_bankInstructionSettingInfo.getIsSend() != Constant.TRUE){ 
					return null;
				}

				/**二级户账户模式*/
				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL)
				{
					
					//普通付款方式
					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
					{
						//付款方：；收款方：
						BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
						AccountInfo accountInfo=new AccountInfo();
						Sett_AccountDAO dao=new Sett_AccountDAO();
						accountInfo=dao.findByID(transInfo.getPayPoundageAccountID());
						//付款方
						bankInstructionInfo.setPayAccountNo(accountInfo.getAccountNo());
						//
						//收款方
						ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
						instruction.setSystemId(1);//系统id
						instruction.setReferenceCode(accountInfo.getAccountNo());
						RespGetGeneralBankAcctInfo  generalbankacctinfo = new RespGetGeneralBankAcctInfo();
						generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
						if(generalbankacctinfo==null|| generalbankacctinfo.getAccountInfo()==null||generalbankacctinfo.getStatus()!=1){
							throw new Exception("未找到付款方对应的银行账户信息"); 
						}
	
						bankInstructionInfo.setReceiveAccountNo(generalbankacctinfo.getAccountInfo().getAccountNO());
						bankInstructionInfo.setReceiveAccountName(generalbankacctinfo.getAccountInfo().getAccountName());
						bankInstructionInfo.setReceiveBranchCodeOfBank(generalbankacctinfo.getAccountInfo().getBankCode());
						bankInstructionInfo.setReceiveBranchName(generalbankacctinfo.getAccountInfo().getBankName());
						bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctinfo.getAccountInfo().getBranchProvince());
						bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctinfo.getAccountInfo().getBranchCity());
						//金额，其他信息
						bankInstructionInfo.setTransType(TransType.NORMAL);
						bankInstructionInfo.setAmount(transInfo.getPoundageAmount());				
						bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
						bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
						bankInstructionInfo.setAbstract(transInfo.getAbstract());
						bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
						bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
						bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
						bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
						colInstructionParams.add(bankInstructionInfo);
					//代理汇兑方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
						
						
					//先拨后支方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
						
						
					}
					
				/**门户账户模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
						
				/**收支两条线模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
					}
			}
		}
		catch(Exception e){
			log.info("根据交易对象形成融资租赁收款指令时出错，无法形成指令！");
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		return colInstructionParams;
	}

	private Collection createConsignLoanInterestCommisionPay(CreateInstructionParam param) throws IException{
		if(param.getObjInfo() == null) {
			log.error("构造手续费指令错误，交易对象参数为空！");
			throw new IException("构造手续费指令错误，交易对象参数为空！");
		}
		Collection colInstructionParams = new ArrayList();
		try{
			TransRepaymentLoanInfo transInfo = (TransRepaymentLoanInfo)param.getObjInfo();
			
			log.info("-----------开始生成手续费指令---------------");
			
			log.info("手续费指令对象："+UtilOperation.dataentityToString(transInfo));
			
			if(transInfo.getRealCommission()<=0)
			{
				log.info("手续费小于等于0，不发送指令");
				return null;
			}
			
			
			
			AccountInfo accountInfo=new AccountInfo();
			Sett_AccountDAO dao=new Sett_AccountDAO();
			if (transInfo.getPayInterestAccountID()>0)
			{
				BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
				//added by mzh_fu 2007/03/30 是否生成指令检验
				/*********************begin*****************************/
				long lAccouontID=transInfo.getPayInterestAccountID();
	
				BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
				bankInstructionSettingQueryInfo.setAccountId(lAccouontID);

				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
				
				if (_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) { 
					return null;
				}
				/**二级户账户模式*/
				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
					
					//普通付款方式
					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
						//付款方：活期账户对应的银行二级账户；收款方：银行二级账户的上级银行账户
						accountInfo=dao.findByID(transInfo.getPayInterestAccountID());
						//付款方
						bankInstructionInfo.setPayAccountNo(accountInfo.getAccountNo());
						//收款方
						ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
						instruction.setSystemId(1);//系统id
						instruction.setReferenceCode(accountInfo.getAccountNo());			
						RespGetGeneralBankAcctInfo  generalbankacctinfo = new RespGetGeneralBankAcctInfo();
						generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
						if(generalbankacctinfo==null|| generalbankacctinfo.getAccountInfo()==null||generalbankacctinfo.getStatus()!=1){
							throw new Exception(generalbankacctinfo.getMessage()); 
						}
			
						bankInstructionInfo.setReceiveAccountNo(generalbankacctinfo.getAccountInfo().getAccountNO());
						bankInstructionInfo.setReceiveAccountName(generalbankacctinfo.getAccountInfo().getAccountName());
						bankInstructionInfo.setReceiveBranchCodeOfBank(generalbankacctinfo.getAccountInfo().getBankCode());
						bankInstructionInfo.setReceiveBranchName(generalbankacctinfo.getAccountInfo().getBankName());
						bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctinfo.getAccountInfo().getBranchProvince());
						bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctinfo.getAccountInfo().getBranchCity());
						//
						bankInstructionInfo.setTransType(TransType.NORMAL);
						bankInstructionInfo.setAmount(transInfo.getRealCommission());				
						bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
						bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
						bankInstructionInfo.setAbstract(transInfo.getAbstract());
						bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
						bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
						bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
						bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
						colInstructionParams.add(bankInstructionInfo);
					//代理汇兑方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
						
						bankInstructionInfo.setTransType(TransType.AGENTVIREMENT);
						
					//先拨后支方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
						
						
					}
					
				/**门户账户模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
						
				/**收支两条线模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
					}
				}
		}catch (Exception e) {			
			log.info("根据交易对象形成担保费指令时出错，无法形成指令！");
			e.printStackTrace();
			throw new IException(e.getMessage(),e);			
		}
		return colInstructionParams;
	}
	
	private Collection createTrustLoanInterestTruestyPay(CreateInstructionParam param) throws IException{
		if(param.getObjInfo() == null) {
			log.error("构造保担保费指令错误，交易对象参数为空！");
			throw new IException("构造担保费指令错误，交易对象参数为空！");
		}
		Collection colInstructionParams = new ArrayList();
		try{
			TransRepaymentLoanInfo transInfo = (TransRepaymentLoanInfo)param.getObjInfo();
			
			log.info("-----------开始生成保担保费指令---------------");
			
			log.info("保担保费指令对象："+UtilOperation.dataentityToString(transInfo));
			
			if(transInfo.getRealSuretyFee()<=0)
			{
				log.info("担保费小于等于0，不发送指令");
				return null;
			}
			
			
			
			Sett_SubAccountDAO subAccountDao=new Sett_SubAccountDAO();
			SubAccountAssemblerInfo subAccountInfo=subAccountDao.findByID(transInfo.getSubAccountID());
			if (subAccountInfo.getSubAccountLoanInfo().getPaySuretyAccountID()>0&& subAccountInfo.getSubAccountLoanInfo().getReceiveSuretyAccountID()>0) {
				//added by mzh_fu 2007/03/30 是否生成指令检验
				BankInstructionSettingInfo bankInstructionSettingQueryInfos = new BankInstructionSettingInfo();
				bankInstructionSettingQueryInfos.setNOfficeId(param.getOfficeID());
				bankInstructionSettingQueryInfos.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingQueryInfos.setTransType(param.getTransactionTypeID());
				bankInstructionSettingQueryInfos.setAccountId(subAccountInfo.getSubAccountLoanInfo().getPaySuretyAccountID());
				BankInstructionSettingInfo bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfos);
				bankInstructionSettingQueryInfos.setAccountId(subAccountInfo.getSubAccountLoanInfo().getReceiveSuretyAccountID());
				BankInstructionSettingInfo bankInstructionSettingInfo1 = getSettingInfoByAccountId(bankInstructionSettingQueryInfos);
				if (bankInstructionSettingInfo.getIsSend() != Constant.TRUE && bankInstructionSettingInfo1.getIsSend() != Constant.TRUE) { 
					return null;
				}
				//当双方都是二级户 只发一条指令
				if(bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL && bankInstructionSettingInfo1.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL)
				{

					
					//普通付款方式
					if(bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY
						&&bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
						//逻辑：付款方：活期账户对应的银行二级户；收款方：活期账户对应的银行二级户

						BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
						AccountInfo accountInfo=new AccountInfo();
						Sett_AccountDAO dao=new Sett_AccountDAO();			
						accountInfo=dao.findByID(subAccountInfo.getSubAccountLoanInfo().getPaySuretyAccountID());
						bankInstructionInfo.setPayAccountNo(accountInfo.getAccountNo());
						accountInfo=dao.findByID(subAccountInfo.getSubAccountLoanInfo().getReceiveSuretyAccountID());
						bankInstructionInfo.setReceiveReferenceCode(accountInfo.getAccountNo());
							
						bankInstructionInfo.setAmount(transInfo.getRealSuretyFee());				
						bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
						bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
						bankInstructionInfo.setAbstract(transInfo.getAbstract());
						bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
						bankInstructionInfo.setTransType(TransType.NORMAL);
						bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
						bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
						bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
						colInstructionParams.add(bankInstructionInfo);
					//代理汇兑方式	
					}else if (bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT
							&&bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
						
						
					//先拨后支方式	
					}else if (bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY
							&&bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
					}
				}
				else
				{
					if(subAccountInfo.getSubAccountLoanInfo().getPaySuretyAccountID()>0)
					{
						/*********************begin*****************************/
						BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
						bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
						bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
						bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
						bankInstructionSettingQueryInfo.setAccountId(subAccountInfo.getSubAccountLoanInfo().getPaySuretyAccountID());
	
						BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
						if (_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) { 
							return null;
						}
						/**二级户账户模式*/
						if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
							
							//普通付款方式
							if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
								//逻辑：付款方：活期账户对应的银行二级户；收款方：银行二级户的上级银行账户
	
								BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
								AccountInfo accountInfo=new AccountInfo();
								Sett_AccountDAO dao=new Sett_AccountDAO();			
								accountInfo=dao.findByID(subAccountInfo.getSubAccountLoanInfo().getPaySuretyAccountID());
								bankInstructionInfo.setPayAccountNo(accountInfo.getAccountNo());
								
								ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
								instruction.setSystemId(1);//系统id
								instruction.setReferenceCode(accountInfo.getAccountNo());			
								RespGetGeneralBankAcctInfo  generalbankacctinfo = new RespGetGeneralBankAcctInfo();
								generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
								if(generalbankacctinfo==null|| generalbankacctinfo.getAccountInfo()==null||generalbankacctinfo.getStatus()!=1){
									throw new Exception("未找到活期账户对应的上级银行账户信息"); 
								}
					
								bankInstructionInfo.setReceiveAccountNo(generalbankacctinfo.getAccountInfo().getAccountNO());
								bankInstructionInfo.setReceiveAccountName(generalbankacctinfo.getAccountInfo().getAccountName());
								bankInstructionInfo.setReceiveBranchCodeOfBank(generalbankacctinfo.getAccountInfo().getBankCode());
								bankInstructionInfo.setReceiveBranchName(generalbankacctinfo.getAccountInfo().getBankName());
								bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctinfo.getAccountInfo().getBranchProvince());
								bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctinfo.getAccountInfo().getBranchCity());
									
								bankInstructionInfo.setAmount(transInfo.getRealSuretyFee());				
								bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
								bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
								bankInstructionInfo.setAbstract(transInfo.getAbstract());
								bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
								bankInstructionInfo.setTransType(TransType.NORMAL);
								bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
								bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
								bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
								colInstructionParams.add(bankInstructionInfo);
							//代理汇兑方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
								log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
								
								
							//先拨后支方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
								log.info("*********先拨后支，暂不考虑*********");
								
								
							}
							
						/**门户账户模式*/
						}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
								
								//普通付款方式
								if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
									log.info("*********财务公司对外付款，暂不考虑*********");
									
								
								//代理汇兑方式	
								}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
									log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
									
									
								//先拨后支方式	
								}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
									log.info("*********先拨后支，暂不考虑*********");
									
								}
								
						/**收支两条线模式*/
						}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
								
								//普通付款方式
								if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
									log.info("*********财务公司对外付款，暂不考虑*********");
									
								
								//代理汇兑方式	
								}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
									log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
									
									
								//先拨后支方式	
								}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
									log.info("*********先拨后支，暂不考虑*********");
									
								}
							}
					}
					if(subAccountInfo.getSubAccountLoanInfo().getReceiveSuretyAccountID()>0)
					{
						/*********************begin*****************************/
						BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
						bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
						bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
						bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
	
						bankInstructionSettingQueryInfo.setAccountId(subAccountInfo.getSubAccountLoanInfo().getReceiveSuretyAccountID());
						BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
						if (_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) { 
							return null;
						}
						/**二级户账户模式*/
						if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
							
							//普通付款方式
							if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
								//逻辑：付款方：银行二级户的上级银行账户；收款方：活期账户对应的银行二级账户
								BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
								AccountInfo accountInfo=new AccountInfo();
								Sett_AccountDAO dao=new Sett_AccountDAO();			
								accountInfo=dao.findByID(subAccountInfo.getSubAccountLoanInfo().getReceiveSuretyAccountID());
	
								ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
								instruction.setSystemId(1);//系统id
								instruction.setReferenceCode(accountInfo.getAccountNo());			
								RespGetGeneralBankAcctInfo  generalbankacctinfo = new RespGetGeneralBankAcctInfo();
								generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
								if(generalbankacctinfo==null|| generalbankacctinfo.getAccountInfo()==null||generalbankacctinfo.getStatus()!=1){
									throw new Exception("未找到活期账户对应的上级银行账户信息"); 
								}
					
								bankInstructionInfo.setPayBankAccountNO(generalbankacctinfo.getAccountInfo().getAccountNO());
								
								bankInstructionInfo.setReceiveReferenceCode(accountInfo.getAccountNo());
								
								bankInstructionInfo.setAmount(transInfo.getRealSuretyFee());				
								bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
								bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
								bankInstructionInfo.setAbstract(transInfo.getAbstract());
								bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
								bankInstructionInfo.setTransType(TransType.NORMAL);
								bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
								bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
								bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
								colInstructionParams.add(bankInstructionInfo);
							//代理汇兑方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
								log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
								
							//先拨后支方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
								log.info("*********先拨后支，暂不考虑*********");
							}
							
						/**门户账户模式*/
						}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
								
								//普通付款方式
								if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
									log.info("*********财务公司对外付款，暂不考虑*********");
									
								
								//代理汇兑方式	
								}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
									log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
									
									
								//先拨后支方式	
								}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
									log.info("*********先拨后支，暂不考虑*********");
									
								}
								
						/**收支两条线模式*/
						}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
								
								//普通付款方式
								if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
									log.info("*********财务公司对外付款，暂不考虑*********");
									
								
								//代理汇兑方式	
								}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
									log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
									
									
								//先拨后支方式	
								}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
									log.info("*********先拨后支，暂不考虑*********");
									
								}
							}
					}
				}

			}
		}
		catch (Exception e) {			
			log.info("根据交易对象形成担保费指令时出错，无法形成指令！");
			e.printStackTrace();
			throw new IException(e.getMessage(),e);			
		}
		return colInstructionParams;
		
	}
	
	/**
	 * 保证金支取
	 * @param obj
	 * @throws IException
	 */
	private Collection createWithdrawMarginDeposit(CreateInstructionParam param)throws IException{
		if(param.getObjInfo() == null) {
			log.error("构造保证金支取指令错误，交易对象参数为空！");
			throw new IException("构造保证金支取指令错误，交易对象参数为空！");
		}
		Collection colInstructionParams = new ArrayList();
		TransMarginWithdrawInfo info = (TransMarginWithdrawInfo)param.getObjInfo();
		log.info(UtilOperation.dataentityToString(info));
		try{
			log.info("------开始保证金支取指令生成");
			BankInstructionInfo bankInstructionInfo = new BankInstructionInfo();

			if(info.getBankID()>0){
				log.info("付方为开户行");
				
				////added by mzh_fu 账户模式与付款方式分开后,是否发送指令的控制
				BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
    			
				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByTransType(bankInstructionSettingQueryInfo);
				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE)
				{
					return null;
				}			
				/**二级户账户模式*/
				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL)
				{
						//逻辑：付款方：财务公司开户行；收款方：外部银行账户
						Sett_BranchDAO dao=new Sett_BranchDAO();
		    			BranchInfo branchInfo=dao.findByID(info.getBankID());
		    			//开户行没有设置发指令，就不发银行指令
		    			if(branchInfo.getIsAutoVirementByBank()==Constant.YesOrNo.YES)
		    			{
		    			
							bankInstructionInfo.setPayBankAccountNO(branchInfo.getBankAccountCode());
							bankInstructionInfo.setReceiveBranchName(info.getRemitInBank());
							bankInstructionInfo.setReceiveBranchAreaNameOfProvince(info.getRemitInProvince());
							bankInstructionInfo.setReceiveBranchAreaNameOfCity(info.getRemitInCity());
							bankInstructionInfo.setReceiveBankExchangeCode("");
							bankInstructionInfo.setReceiveAccountNo(info.getExtAcctNo());	
							bankInstructionInfo.setReceiveAccountName(info.getExtClientName());	
							bankInstructionInfo.setTransactionNo(info.getTransNo());
							bankInstructionInfo.setTransType(TransType.NORMAL);
							//金额，币种 等其他信息
							bankInstructionInfo.setAmount(info.getDrawAmount());				
							bankInstructionInfo.setCurrencyID(info.getCurrencyID());
							bankInstructionInfo.setOfficeId(info.getOfficeID());
							bankInstructionInfo.setAbstract(info.getAbstract());
							bankInstructionInfo.setTransactionNo(info.getTransNo());
							bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
							bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
							bankInstructionInfo.setSenderNo(String.valueOf(info.getInputUserID()));
							colInstructionParams.add(bankInstructionInfo);		
		    			}
					
				/**门户账户模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
						
				/**收支两条线模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
				}
			}
			else if(info.getCurrentAccountID()>0)
			{
				//转入活期账户
				AccountInfo accountInfo=new AccountInfo();
				Sett_AccountDAO dao=new Sett_AccountDAO();
				//added by mzh_fu 2007/03/30 是否生成指令检验
				/*********************begin*****************************/
				long lAccouontID=info.getCurrentAccountID();
				BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingQueryInfo.setTransType(info.getTransactionTypeID());
				bankInstructionSettingQueryInfo.setAccountId(lAccouontID);
				//added by mzh_fu 2007/09/27
				bankInstructionSettingQueryInfo.setTransModule(SETTConstant.TransModule.INSIDE_PAY);// 内转
				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
				
				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
					return null;				
				/**********************end****************************/
				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
					
					//普通付款方式
					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
					{
						//逻辑：付款方：银行二级账户对应的上级银行账户；收款方：活期账户对应的银行二级账户
						accountInfo=dao.findByID(info.getCurrentAccountID());				
						//取得上级账户信息
						ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
						instruction.setSystemId(1);//系统id
						instruction.setReferenceCode(accountInfo.getAccountNo());	

						RespGetGeneralBankAcctInfo  generalbankacctinfo = new RespGetGeneralBankAcctInfo();
						generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
						if(generalbankacctinfo==null|| generalbankacctinfo.getAccountInfo()==null||generalbankacctinfo.getStatus()!=1){
							throw new Exception("未找到活期账户对应的上级银行账户信息"); 
						}

						bankInstructionInfo.setPayBankAccountNO(generalbankacctinfo.getAccountInfo().getAccountNO());
						bankInstructionInfo.setPayAcctBankCode(generalbankacctinfo.getAccountInfo().getBankCode());
						bankInstructionInfo.setReceiveReferenceCode(accountInfo.getAccountNo());
						
						bankInstructionInfo.setTransactionNo(info.getTransNo());
						bankInstructionInfo.setTransType(TransType.NORMAL);
						//金额，币种 等其他信息
						bankInstructionInfo.setAmount(info.getDrawAmount());				
						bankInstructionInfo.setCurrencyID(info.getCurrencyID());
						bankInstructionInfo.setOfficeId(info.getOfficeID());
						bankInstructionInfo.setAbstract(info.getAbstract());
						bankInstructionInfo.setTransactionNo(info.getTransNo());
						bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
						bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
						bankInstructionInfo.setSenderNo(String.valueOf(info.getInputUserID()));
						colInstructionParams.add(bankInstructionInfo);									
					//代理汇兑方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
						
						bankInstructionInfo.setTransType(TransType.AGENTVIREMENT);
						
					//先拨后支方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
						
						
					}
					
				/**门户账户模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
						
				/**收支两条线模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
				}
				
			}				
		
		}catch(Exception e){
			//log.info("无法形成指令！"+e.getMessage());
			e.printStackTrace();
			throw new IException(e.getMessage(),e);		
		}
		return colInstructionParams;
	}
	
	/**
	 * 生成保证金开立指令
	 * @param obj
	 * @throws IException
	 */
	private Collection createOpenMarginDeposit(CreateInstructionParam param)throws IException{
		if(param.getObjInfo() == null) {
			log.error("构造保证金开立指令错误，交易对象参数为空！");
			throw new IException("构造保证金开立指令错误，交易对象参数为空！");
		}
		Collection colInstructionParams = new ArrayList();
		TransMarginOpenInfo info = new TransMarginOpenInfo();
		info = (TransMarginOpenInfo)param.getObjInfo();
		log.info(UtilOperation.dataentityToString(info));		

		try{
			log.info("------开始保证金开立指令生成");
			
			if (info.getCurrentAccountID()>0)
			{	
				AccountInfo accountInfo=new AccountInfo();
				Sett_AccountDAO dao=new Sett_AccountDAO();
				//added by mzh_fu 2007/03/30 是否生成指令检验
				/*********************begin*****************************/
				long lAccouontID=info.getCurrentAccountID();

				BankInstructionSettingInfo bankInstructionSettingInfo=new BankInstructionSettingInfo();
				bankInstructionSettingInfo.setNOfficeId(param.getOfficeID());
				bankInstructionSettingInfo.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingInfo.setTransType(info.getTransactionTypeID());
				bankInstructionSettingInfo.setAccountId(lAccouontID);
				
				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingInfo);
				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
					return null;	
				/**********************end****************************/

				/**二级户账户模式*/
				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
					
					//普通付款方式
					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
					{
						//逻辑：付款方：活期账户对应的银行二级账户；收款方：银行二级户的上级银行账户
						accountInfo=dao.findByID(info.getCurrentAccountID());
						BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
								
						//付款方信息，付款方位下属单位
						bankInstructionInfo.setPayAccountNo(accountInfo.getAccountNo());

						ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
						instruction.setSystemId(1);//系统id
						instruction.setReferenceCode(accountInfo.getAccountNo());			
						RespGetGeneralBankAcctInfo  generalbankacctinfo = new RespGetGeneralBankAcctInfo();
						generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
						if(generalbankacctinfo==null|| generalbankacctinfo.getAccountInfo()==null||generalbankacctinfo.getStatus()!=1){
							throw new Exception("未找到活期账户的上级银行账户信息"); 
						}

						bankInstructionInfo.setReceiveAccountNo(generalbankacctinfo.getAccountInfo().getAccountNO());
						bankInstructionInfo.setReceiveAccountName(generalbankacctinfo.getAccountInfo().getAccountName());
						bankInstructionInfo.setReceiveBranchCodeOfBank(generalbankacctinfo.getAccountInfo().getBankCode());
						bankInstructionInfo.setReceiveBranchName(generalbankacctinfo.getAccountInfo().getBankName());
						bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctinfo.getAccountInfo().getBranchProvince());
						bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctinfo.getAccountInfo().getBranchCity());
						bankInstructionInfo.setTransType(TransType.NORMAL);
						//金额，币种 等其他信息
						bankInstructionInfo.setAmount(info.getAmount());				
						bankInstructionInfo.setCurrencyID(info.getCurrencyID());
						bankInstructionInfo.setOfficeId(info.getOfficeID());
						bankInstructionInfo.setAbstract(info.getAbstract());
						bankInstructionInfo.setTransactionNo(info.getTransNo());
						bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
						bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
						bankInstructionInfo.setSenderNo(String.valueOf(info.getInputUserID()));
						colInstructionParams.add(bankInstructionInfo);
					//代理汇兑方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
						
					//先拨后支方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
						
						
					}
					
				/**门户账户模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
						
				/**收支两条线模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
				}
				
			}
			if (info.getCommissionCurrentAccountID()>0)
			{
				//added by mzh_fu 2007/03/30 是否生成指令检验
				/*********************begin*****************************/
				long lAccouontID=info.getCommissionCurrentAccountID();

				BankInstructionSettingInfo bankInstructionSettingInfo=new BankInstructionSettingInfo();
				bankInstructionSettingInfo.setNOfficeId(param.getOfficeID());
				bankInstructionSettingInfo.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingInfo.setTransType(info.getTransactionTypeID());
				bankInstructionSettingInfo.setAccountId(lAccouontID);
				
				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingInfo);
				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
					return null;	
				/**********************end****************************/

				/**二级户账户模式*/
				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
					
					//普通付款方式
					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
					{
						//逻辑：付款方：活期账户对应的上级银行账户；收款方：银行二级户的上级银行
						log.info("------开始保证金手续费指令生成");	
						
						AccountInfo accountInfo=new AccountInfo();
						Sett_AccountDAO dao=new Sett_AccountDAO();
						accountInfo=dao.findByID(info.getCommissionCurrentAccountID());
						BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
						//付款方信息，付款方位下属单位
						bankInstructionInfo.setPayAccountNo(accountInfo.getAccountNo());

						ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
						instruction.setSystemId(1);//系统id
						instruction.setReferenceCode(accountInfo.getAccountNo());			
						RespGetGeneralBankAcctInfo  generalbankacctinfo = new RespGetGeneralBankAcctInfo();
						generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
						if(generalbankacctinfo==null|| generalbankacctinfo.getAccountInfo()==null||generalbankacctinfo.getStatus()!=1){
							throw new Exception("未找到活期账户的上级银行账户"); 
						}

						bankInstructionInfo.setReceiveAccountNo(generalbankacctinfo.getAccountInfo().getAccountNO());
						bankInstructionInfo.setReceiveAccountName(generalbankacctinfo.getAccountInfo().getAccountName());
						bankInstructionInfo.setReceiveBranchCodeOfBank(generalbankacctinfo.getAccountInfo().getBankCode());
						bankInstructionInfo.setReceiveBranchName(generalbankacctinfo.getAccountInfo().getBankName());
						bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctinfo.getAccountInfo().getBranchProvince());
						bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctinfo.getAccountInfo().getBranchCity());

						//金额，币种 等其他信息
						bankInstructionInfo.setAmount(info.getCommissionAmount());				
						bankInstructionInfo.setCurrencyID(info.getCurrencyID());
						bankInstructionInfo.setOfficeId(info.getOfficeID());
						bankInstructionInfo.setAbstract(info.getAbstract());
						bankInstructionInfo.setTransactionNo(info.getTransNo());
						bankInstructionInfo.setTransType(TransType.NORMAL);
						bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
						bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
						bankInstructionInfo.setSenderNo(String.valueOf(info.getInputUserID()));
						colInstructionParams.add(bankInstructionInfo);
					//代理汇兑方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
						
					//先拨后支方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
						
						
					}
					
				/**门户账户模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
						
				/**收支两条线模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
				}
				
			}
		} catch (Exception e) {			
			//log.info("无法形成指令！"+e.getMessage());
			e.printStackTrace();
			throw new IException(e.getMessage(),e);			
		}
		return colInstructionParams;
		//生成手续费指令
		//createOpenMarginCommissionDeposit(param);
	}

	private void createOpenMarginCommissionDeposit(CreateInstructionParam param)throws IException{
		if(param.getObjInfo() == null) {
			log.error("构造保证金手续费指令错误，交易对象参数为空！");
			throw new IException("构造保证金手续费指令错误，交易对象参数为空！");
		}
		TransMarginOpenInfo info = new TransMarginOpenInfo();
		info = (TransMarginOpenInfo)param.getObjInfo();
		log.info(UtilOperation.dataentityToString(info));		

		try{
			if (info.getCommissionCurrentAccountID()>0)
			{
				//added by mzh_fu 2007/03/30 是否生成指令检验
				/*********************begin*****************************/
				long lAccouontID=info.getCommissionCurrentAccountID();

				BankInstructionSettingInfo bankInstructionSettingInfo=new BankInstructionSettingInfo();
				bankInstructionSettingInfo.setNOfficeId(param.getOfficeID());
				bankInstructionSettingInfo.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingInfo.setTransType(param.getTransactionTypeID());

				if(!isCreateBankInstructionByAccountId(bankInstructionSettingInfo,lAccouontID)){
					//不需要生成指令
					return ;
				}
				/**********************end****************************/
				log.info("------开始保证金手续费指令生成");	
	
				AccountInfo accountInfo=new AccountInfo();
				Sett_AccountDAO dao=new Sett_AccountDAO();
				accountInfo=dao.findByID(info.getCommissionCurrentAccountID());
				BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
		
				//付款方信息，付款方位下属单位
				bankInstructionInfo.setPayAccountNo(accountInfo.getAccountNo());

				ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
				log.info("根据内部账号 "+accountInfo.getAccountNo()+" 从监控取得开户行信息");
				instruction.setSystemId(1);//系统id
				instruction.setReferenceCode(accountInfo.getAccountNo());			

				//取得resp总账info
				RespGetGeneralBankAcctInfo  generalbankacctinfo = new RespGetGeneralBankAcctInfo();
				generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
				log.info("返回值"+generalbankacctinfo.getStatus());
				if(generalbankacctinfo==null|| generalbankacctinfo.getAccountInfo()==null||generalbankacctinfo.getStatus()!=1){
					log.info("根据内部账号 "+accountInfo.getAccountNo()+" 从监控取得开户行信息失败");
					throw new Exception(generalbankacctinfo.getMessage()); 
				}

				if(generalbankacctinfo!=null && generalbankacctinfo.getAccountInfo()!=null){
					log.info("得到的银行信息是"+UtilOperation.dataentityToString(generalbankacctinfo.getAccountInfo()));

					//bankInstructionInfo.setReceiveReferenceCode(generalbankacctinfo.getAccountInfo().getReferenceCode());
					bankInstructionInfo.setReceiveAccountNo(generalbankacctinfo.getAccountInfo().getAccountNO());
					bankInstructionInfo.setReceiveAccountName(generalbankacctinfo.getAccountInfo().getAccountName());
					bankInstructionInfo.setReceiveBranchCodeOfBank(generalbankacctinfo.getAccountInfo().getBankCode());
					bankInstructionInfo.setReceiveBranchName(generalbankacctinfo.getAccountInfo().getBankName());
					bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctinfo.getAccountInfo().getBranchProvince());
					bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctinfo.getAccountInfo().getBranchCity());
				}

				//金额，币种 等其他信息
				bankInstructionInfo.setAmount(info.getCommissionAmount());				
				bankInstructionInfo.setCurrencyID(info.getCurrencyID());
				bankInstructionInfo.setOfficeId(info.getOfficeID());
				bankInstructionInfo.setAbstract(info.getAbstract());
				bankInstructionInfo.setTransactionNo(info.getTransNo());
				bankInstructionInfo.setTransType(TransType.NORMAL);
				bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
				//bankInstructionInfo.setReceiveBank(branchInfo.getBankTypeID());
				bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
				bankInstructionInfo.setSenderNo(String.valueOf(info.getInputUserID()));
				log.info("生成的指令信息是："+UtilOperation.dataentityToString(bankInstructionInfo));
				BankInstructionOperation instructionOperation = new BankInstructionOperation();
				instructionOperation.addBankInstruction(bankInstructionInfo);
				log.info("------结束保证金开立付款指令生成");
			}
		} catch (Exception e) {			
			//log.info("无法形成指令！"+e.getMessage());
			e.printStackTrace();
			throw new IException("无法形成指令！"+e.getMessage());			
		}
	}

	/**
	 * 生成交易类型为“银行付款--直接支付”指令
	 * @param obj 交易对象
	 * @throws IException
	 */
	private Collection createBankPay(CreateInstructionParam param) throws IException {
		if(param.getObjInfo() == null) {
			log.error("构造银行付款指令错误，交易对象参数为空！");
			throw new IException("构造银行付款指令错误，交易对象参数为空！");
		}
		Collection colInstructionParams = new ArrayList();
		try {

			//通过结算交易号，获得结算对象
			TransCurrentDepositInfo info = new TransCurrentDepositInfo();
			info = (TransCurrentDepositInfo)param.getObjInfo();
			
			//根据付款方BankID，获得付款方对应的开户行信息
			BranchInfo branchInfo = new BranchInfo();
			Sett_BranchDAO branchDAO = new Sett_BranchDAO();			
			branchInfo = branchDAO.findByID(info.getBankID());
						
			//构造指令
			log.info("--------------开始生成指令------------");
			//当指定开户行不存在、指定开户行无银行类型、无银行账户号，交易中无下属单位账户信息和外部账户信息则不生成指令

			BankInstructionInfo bankInstructionInfo = new BankInstructionInfo();					
			
			AccountInfo accountInfo=new AccountInfo();
			Sett_AccountDAO dao=new Sett_AccountDAO();
			accountInfo=dao.findByID(info.getPayAccountID());

			//付款方信息
			//结算中心代成员单位对外付款
			if (info.getPayAccountID()>0)
			{
				//added by mzh_fu 2007/03/30 是否生成指令检验
				long lAccouontID=info.getPayAccountID();

				BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingQueryInfo.setTransType(info.getTransactionTypeID());
				bankInstructionSettingQueryInfo.setAccountId(lAccouontID);
				
				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
				
				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
					return null;			

				Bs_BankAccountInfoDAO bdao = new Bs_BankAccountInfoDAO();
				/**二级户账户模式*/
				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
					
					
					
					//普通付款方式
					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
						
						
						bankInstructionInfo.setPayAccountNo(accountInfo.getAccountNo());	
						bankInstructionInfo.setAgentAcctNoOfPay(branchInfo.getBankAccountCode());						

						bankInstructionInfo.setTransType(TransType.NORMAL);
					
					//代理汇兑方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款*********");
						bankInstructionInfo.setPayBankAccountNO(accountInfo.getAccountNo());
						bankInstructionInfo.setPayAccountName(accountInfo.getAccountName());

						bankInstructionInfo.setAgentAcctNoOfPay(branchInfo.getBankAccountCode());
						//取开户行设置对应的账户名称 modify by zcwang 2009-03-25
						//bankInstructionInfo.setAgentBankNameOfPay(bdao.getAcctNameByNo(branchInfo.getBankAccountCode()));
						bankInstructionInfo.setAgentBankNameOfPay(branchInfo.getEnterpriseName());
						bankInstructionInfo.setTransType(TransType.AGENTVIREMENT);
												
								
					//先拨后支方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支*********");
						//TODO
						bankInstructionInfo.setPayAccountNo(accountInfo.getAccountNo());					
						bankInstructionInfo.setPayAccountName(accountInfo.getAccountName());
						
						bankInstructionInfo.setAgentAcctNoOfPay(branchInfo.getBankAccountCode());
						bankInstructionInfo.setAgentBankNameOfPay(branchInfo.getBranchName());
						
						bankInstructionInfo.setTransType(TransType.TRANSFERTOPAY);
					}
					
				/**门户账户模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
						//门户模式下，银行没有设置发送指令就不发指令
						if(branchInfo.getIsAutoVirementByBank()!=Constant.YesOrNo.YES)
						{
							return null;
						}
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款*********");
							bankInstructionInfo.setPayBankAccountNO(branchInfo.getBankAccountCode());
							//取开户行设置对应的账户名称 modify by zcwang 2009-03-25
							//bankInstructionInfo.setPayAccountName(bdao.getAcctNameByNo(branchInfo.getBankAccountCode()));
							bankInstructionInfo.setPayAccountName(branchInfo.getEnterpriseName());
							bankInstructionInfo.setTransType(TransType.NORMAL);
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款*********");
							bankInstructionInfo.setPayBankAccountNO(accountInfo.getAccountNo());
							bankInstructionInfo.setPayAccountName(accountInfo.getAccountName());

							bankInstructionInfo.setAgentAcctNoOfPay(branchInfo.getBankAccountCode());
							//取开户行设置对应的账户名称 modify by zcwang 2009-03-25
							//bankInstructionInfo.setAgentBankNameOfPay(bdao.getAcctNameByNo(branchInfo.getBankAccountCode()));
							bankInstructionInfo.setAgentBankNameOfPay(branchInfo.getEnterpriseName());
							bankInstructionInfo.setTransType(TransType.AGENTVIREMENT);
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支*********");
							bankInstructionInfo.setPayAccountNo(accountInfo.getAccountNo());					
							bankInstructionInfo.setPayAccountName(accountInfo.getAccountName());
							
							bankInstructionInfo.setAgentAcctNoOfPay(branchInfo.getBankAccountCode());
							bankInstructionInfo.setAgentBankNameOfPay(branchInfo.getBranchName());
							
							bankInstructionInfo.setTransType(TransType.TRANSFERTOPAY);
						}
						
				/**收支两条线模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款*********");
							bankInstructionInfo.setPayBankAccountNO(branchInfo.getBankAccountCode());
							//取开户行设置对应的账户名称 modify by zcwang 2009-03-25
							//bankInstructionInfo.setPayAccountName(bdao.getAcctNameByNo(branchInfo.getBankAccountCode()));
							bankInstructionInfo.setPayAccountName(branchInfo.getEnterpriseName());
							bankInstructionInfo.setTransType(TransType.NORMAL);
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款*********");
							bankInstructionInfo.setPayBankAccountNO(accountInfo.getAccountNo());
							bankInstructionInfo.setPayAccountName(accountInfo.getAccountName());

							bankInstructionInfo.setAgentAcctNoOfPay(branchInfo.getBankAccountCode());
							//取开户行设置对应的账户名称 modify by zcwang 2009-03-25
							//bankInstructionInfo.setAgentBankNameOfPay(bdao.getAcctNameByNo(branchInfo.getBankAccountCode()));
							bankInstructionInfo.setAgentBankNameOfPay(branchInfo.getEnterpriseName());
							bankInstructionInfo.setTransType(TransType.AGENTVIREMENT);
                            
							//modify by xwhe 2008-12-12 如果是工行并且是同行添加一个参数加急
							try{
							   if((BranchIdentify.isSameBankName(branchInfo.getBranchName(),"工行")&& BranchIdentify.isSameBankName(branchInfo.getBranchName(),info.getRemitInBank())))
							    {
								   	bankInstructionInfo.setRemitPriority(RemitPriority.URGENT);
							    }
							}
							catch(Exception e)
							{
								bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
								e.printStackTrace();
								//判断工行指令是否为加急出错
								log.info("判断工行指令是否为加急出错，将指令汇款速度设置为normal："+e.getMessage());
							}
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支*********");
							bankInstructionInfo.setPayAccountNo(accountInfo.getAccountNo());					
							bankInstructionInfo.setPayAccountName(accountInfo.getAccountName());
							
							bankInstructionInfo.setAgentAcctNoOfPay(branchInfo.getBankAccountCode());
							bankInstructionInfo.setAgentBankNameOfPay(branchInfo.getBranchName());
							
							bankInstructionInfo.setTransType(TransType.TRANSFERTOPAY);
						}
				}
				
				
			}

			//收款方信息，收款方为外部银行账户
			bankInstructionInfo.setReceiveAccountNo(info.getExtAccountNo());
			bankInstructionInfo.setReceiveAccountName(info.getExtClientName());
			bankInstructionInfo.setReceiveBranchName(info.getRemitInBank());
			bankInstructionInfo.setReceiveBranchAreaNameOfProvince(info.getRemitInProvince());
			bankInstructionInfo.setReceiveBranchAreaNameOfCity(info.getRemitInCity());
			bankInstructionInfo.setReceiveBankExchangeCode("");
			bankInstructionInfo.setReceiveBranchCodeOfBank("");					
			bankInstructionInfo.setTransactionNo(info.getTransNo());
			bankInstructionInfo.setRecBankCode(info.getSpayeebankcnapsno());  //收款方CNAPS号
			bankInstructionInfo.setReceiveBankExchangeCode(info.getSpayeebankexchangeno()); //收款方联行号
			bankInstructionInfo.setReceiveBranchCodeOfBank(info.getSpayeebankorgno());//收款方机构号
			

			//金额，币种 等其他信息
			bankInstructionInfo.setAmount(info.getAmount());				
			bankInstructionInfo.setCurrencyID(info.getCurrencyID());
			bankInstructionInfo.setOfficeId(info.getOfficeID());//新增办事处id
			
			bankInstructionInfo.setAbstract(info.getAbstractStr());
			bankInstructionInfo.setTransactionNo(info.getTransNo());

			bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
			bankInstructionInfo.setReceiveBank(branchInfo.getBankTypeID());
			bankInstructionInfo.setTransTimeOfBank(info.getExecuteDate());		
			
			//Boxu Add 2010-12-09 如果打开结算手续费并且业务类型为银行付款，就从前台传入汇款速度
			if(Config.getBoolean(ConfigConstant.SETT_TRANSCOMMISSION,false)
			&& info.getTransactionTypeID() == SETTConstant.TransactionType.BANKPAY)
			{
				if(info.getIsUrgent() == Constant.remitSpeedType.GENERAL)
				{
					bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
				}
				else if(info.getIsUrgent() == Constant.remitSpeedType.RAPID)
				{
					bankInstructionInfo.setRemitPriority(RemitPriority.URGENT);
				}
			}
			
			//modify by xwhe 2008-12-12 如果是工行并且是同行添加一个参数加急
			if(bankInstructionInfo.getRemitPriority() < 0)
			{
				bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
			}
			
			bankInstructionInfo.setSenderNo(String.valueOf(info.getInputUserID()));					
			
			log.info("银行付款指令对象："+UtilOperation.dataentityToString(bankInstructionInfo));
			//保存下属单位到外部账户的付款指令											

			log.info("--------------结束生成指令------------");
			//BankInstructionOperation instructionOperation = new BankInstructionOperation();
			log.info("生成的指令信息是："+UtilOperation.dataentityToString(bankInstructionInfo));

			//instructionOperation.addBankInstruction(bankInstructionInfo);
			colInstructionParams.add(bankInstructionInfo);
			log.info("------结束内部转账指令生成");	
		} catch (Exception e) {
			log.error("生成银行付款指令失败");
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		return colInstructionParams;
	}
	
	/**
	 * 生成交易类型为“银行付款--下划成员单位”指令
	 * @param obj 交易对象
	 * @throws IException
	 */
	private Collection createBankPayDown(CreateInstructionParam param) throws IException {
		
		if(param.getObjInfo() == null) {
			log.error("构造银行付款指令错误，交易对象参数为空！");
			throw new IException("构造银行付款指令错误，交易对象参数为空！");
		}
		Collection colInstructionParams = new ArrayList();
		try {

			//通过结算交易号，获得结算对象
			TransCurrentDepositInfo info = new TransCurrentDepositInfo();
			info = (TransCurrentDepositInfo)param.getObjInfo();
			if(info.getPayAccountID() > 0)
			{
				BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
				bankInstructionSettingQueryInfo.setAccountId(info.getPayAccountID());
				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
				{
					return null;
				}
				AccountHome home =
					(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
				Account accounthome = (Account) home.create();
				/**二级户账户模式*/
				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
					
					//普通付款方式
					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
						log.info("*********普通付款，暂不考虑*********");
					
					//代理汇兑方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款 ，暂不考虑*********");
						
					//先拨后支方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
						
					}
					
				/**门户账户模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
						
				/**收支两条线模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							//根据付款方BankID，获得付款方对应的开户行信息
							BranchInfo branchInfo = new BranchInfo();
							Sett_BranchDAO branchDAO = new Sett_BranchDAO();			
							branchInfo = branchDAO.findByID(info.getBankID());
							//构造指令
							log.info("--------------开始生成指令------------");
							//当指定开户行不存在、指定开户行无银行类型、无银行账户号，交易中无下属单位账户信息和外部账户信息则不生成指令
									
							BankInstructionInfo bankInstructionInfo = new BankInstructionInfo();					
							
							AccountInfo accountInfo=new AccountInfo();
							Sett_AccountDAO dao=new Sett_AccountDAO();
							accountInfo=dao.findByID(info.getPayAccountID());
							
							log.info("*********开始生成下拨子账户的指令*********");
							
							//付款方信息（开户行）
							bankInstructionInfo.setPayBankAccountNO(branchInfo.getBankAccountCode());			
							bankInstructionInfo.setPayBranchName(branchInfo.getEnterpriseName());
							log.info("*********成员单位内部账户收款*********");	
											
							bankInstructionInfo.setReceiveReferenceCode(NameRef.getAccountNoByID(info.getPayAccountID()));				

							bankInstructionInfo.setReceiveBranchAreaNameOfProvince(info.getRemitInProvince());
							bankInstructionInfo.setReceiveBranchAreaNameOfCity(info.getRemitInCity());
							bankInstructionInfo.setReceiveBankExchangeCode("");
							bankInstructionInfo.setReceiveBranchCodeOfBank("");					
							bankInstructionInfo.setTransactionNo(info.getTransNo());
							
							//金额，币种 等其他信息
							bankInstructionInfo.setAmount(info.getAmount());				
							bankInstructionInfo.setCurrencyID(info.getCurrencyID());
							bankInstructionInfo.setOfficeId(info.getOfficeID());//新增办事处id
							bankInstructionInfo.setAbstract(info.getAbstractStr());
							bankInstructionInfo.setTransactionNo(info.getTransNo());
							
							bankInstructionInfo.setTransType(TransType.CAPITALPAY);
							
							bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
							bankInstructionInfo.setReceiveBank(branchInfo.getBankTypeID());
							bankInstructionInfo.setTransTimeOfBank(info.getExecuteDate());
							bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
							
							//modified by mzh_fu 2007/12/14 为解决网银行用款计划所作修改
							String strTempInputUser = String.valueOf(info.getInputUserID());
							if(info.getInputUserID()== Constant.MachineUser.InputUser)
								strTempInputUser = Constant.MachineUser.getName(Constant.MachineUser.InputUser);
							
							bankInstructionInfo.setSenderNo(strTempInputUser);				
							colInstructionParams.add(bankInstructionInfo);
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
				}
			}
			else
			{ 
				BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
    			
				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByTransType(bankInstructionSettingQueryInfo);
				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE)
				{
					return null;
				}
					
					AccountHome home =
						(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
					Account accounthome = (Account) home.create();
					/**二级户账户模式*/
					if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********普通付款，暂不考虑*********");
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款 ，暂不考虑*********");
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
						
					/**门户账户模式*/
					}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
							
							//普通付款方式
							if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
								log.info("*********财务公司对外付款，暂不考虑*********");
								
							
							//代理汇兑方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
								log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
								
								
							//先拨后支方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
								log.info("*********先拨后支，暂不考虑*********");
								
							}
							
					/**收支两条线模式*/
					}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
							
								//根据付款方BankID，获得付款方对应的开户行信息
								BranchInfo branchInfo = new BranchInfo();
								Sett_BranchDAO branchDAO = new Sett_BranchDAO();			
								branchInfo = branchDAO.findByID(info.getBankID());
								
								if(branchInfo.getIsAutoVirementByBank()==Constant.YesOrNo.YES)
								{
									//构造指令
									log.info("--------------开始生成指令------------");
									//当指定开户行不存在、指定开户行无银行类型、无银行账户号，交易中无下属单位账户信息和外部账户信息则不生成指令
											
									BankInstructionInfo bankInstructionInfo = new BankInstructionInfo();					
									
									AccountInfo accountInfo=new AccountInfo();
									Sett_AccountDAO dao=new Sett_AccountDAO();
									accountInfo=dao.findByID(info.getPayAccountID());
									
									log.info("*********开始生成下拨子账户的指令*********");
									
									//付款方信息（开户行）
									bankInstructionInfo.setPayBankAccountNO(branchInfo.getBankAccountCode());			
									bankInstructionInfo.setPayBranchName(branchInfo.getEnterpriseName());
									log.info("*********外部账户收款*********");
									
									//added by mzh_fu 2007/07/25 账户模式与付款方式分开后,是否发送指令的控制
									bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
									bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
									bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
									bankInstructionSettingQueryInfo.setTransType(info.getTransactionTypeID());
					    			
									if(getSettingInfoByTransType(bankInstructionSettingQueryInfo).getIsSend() != Constant.TRUE){ 
										return null;
									}
									
									//收款方信息，收款方为外部银行账户
									bankInstructionInfo.setReceiveAccountNo(info.getExtAccountNo());
									bankInstructionInfo.setReceiveAccountName(info.getExtClientName());
									bankInstructionInfo.setReceiveBranchName(info.getRemitInBank());		
									bankInstructionInfo.setReceiveBranchAreaNameOfProvince(info.getRemitInProvince());
									bankInstructionInfo.setReceiveBranchAreaNameOfCity(info.getRemitInCity());
									bankInstructionInfo.setReceiveBankExchangeCode("");
									bankInstructionInfo.setReceiveBranchCodeOfBank("");					
									bankInstructionInfo.setTransactionNo(info.getTransNo());
									
									//金额，币种 等其他信息
									bankInstructionInfo.setAmount(info.getAmount());				
									bankInstructionInfo.setCurrencyID(info.getCurrencyID());
									bankInstructionInfo.setOfficeId(info.getOfficeID());//新增办事处id
									bankInstructionInfo.setAbstract(info.getAbstractStr());
									bankInstructionInfo.setTransactionNo(info.getTransNo());
									
									bankInstructionInfo.setTransType(TransType.CAPITALPAY);
									
									bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
									bankInstructionInfo.setReceiveBank(branchInfo.getBankTypeID());
									bankInstructionInfo.setTransTimeOfBank(info.getExecuteDate());
									bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
									
									//modified by mzh_fu 2007/12/14 为解决网银行用款计划所作修改
									String strTempInputUser = String.valueOf(info.getInputUserID());
									if(info.getInputUserID()== Constant.MachineUser.InputUser)
										strTempInputUser = Constant.MachineUser.getName(Constant.MachineUser.InputUser);
									
									bankInstructionInfo.setSenderNo(strTempInputUser);				
									colInstructionParams.add(bankInstructionInfo);
								}
					}
				}
		} catch (Exception e) {
			log.error("生成银行付款指令失败");
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		return colInstructionParams;
	}
	
	/**
	 * 生成交易类型为“银行收款--直接收款”指令
	 * @param obj 交易对象
	 * @throws IException
	 */
	private Collection createBankReceive(CreateInstructionParam param) throws IException {
		if(param.getObjInfo() == null) {
			log.error("构造银行收款指令错误，交易对象参数为空！");
			throw new IException("构造银行收款指令错误，交易对象参数为空！");
		}
		Collection colInstructionParams = new ArrayList();
		try {
			//通过结算交易号，获得结算对象
			TransCurrentDepositInfo info = new TransCurrentDepositInfo();
			info = (TransCurrentDepositInfo)param.getObjInfo();

			//modified by mzh_fu 2008/02/04 
			long lAccouontID = info.getReceiveAccountID(); // 收款方(内部账号)

			BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
			bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
			bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
			bankInstructionSettingQueryInfo.setTransType(info.getTransactionTypeID());
			bankInstructionSettingQueryInfo.setAccountId(lAccouontID);

			BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
			
			/** 二级户账户模式 */
			if (_bankInstructionSettingInfo.getIsSend() != Constant.TRUE)
			{	
				return null;
			}
			if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
			{
				//普通付款方式
				if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
				{
					//逻辑：付款方：财务公司开户行；收款方：活期账户对应的银行二级账户
					BankInstructionInfo bankInstructionInfo = new BankInstructionInfo();
					//付款方
					Bs_BankAccountInfoDAO bdao = new Bs_BankAccountInfoDAO();			
					//根据收款方BankID，获得收款方对应的开户行信息
					BranchInfo branchInfo = new Sett_BranchDAO().findByID(info.getBankID());
					//取开户行设置对应的账户名称 modify by zcwang 2009-03-25
					//String accname = bdao.getAcctNameByNo(branchInfo.getBankAccountCode());
					
					bankInstructionInfo.setPayBankAccountNO(branchInfo.getBankAccountCode());//付款银行账户号
					bankInstructionInfo.setPayAcctBankCode(branchInfo.getBranchCode()); //付款账户银行编号	
					//取开户行设置对应的账户名称 modify by zcwang 2009-03-25
					//bankInstructionInfo.setPayAccountName(accname);//付款账户名称 
					bankInstructionInfo.setPayAccountName(branchInfo.getEnterpriseName());//付款账户名称
					bankInstructionInfo.setPayDepartmentName(branchInfo.getBankServiceName());//付款人单位全称 
					
					//收款方
					String bankno = bdao.getBankNo(info.getReceiveAccountID(),2);
					if (bankno == null || bankno.equalsIgnoreCase("")) {
						throw new IException("活期账户号没有对应的银行账户号");
					}
					
					AccountHome home =
						(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
					Account accounthome = (Account) home.create();
					AccountInfo accountInfo=accounthome.findAccountByID(lAccouontID);
					
					bankInstructionInfo.setReceiveReferenceCode(accountInfo.getAccountNo()); //关联号
					bankInstructionInfo.setReceiveAccountName(accountInfo.getAccountName());//收款账户名称
					bankInstructionInfo.setReceiveBranchName(accountInfo.getAccountName());//收款人开户行名称
		
					
					bankInstructionInfo.setTransactionNo(info.getTransNo());
					//金额，币种 等其他信息
					bankInstructionInfo.setAmount(info.getAmount());				
					bankInstructionInfo.setCurrencyID(info.getCurrencyID());
					bankInstructionInfo.setOfficeId(info.getOfficeID());
					bankInstructionInfo.setAbstract(info.getAbstractStr());
					bankInstructionInfo.setTransactionNo(info.getTransNo());
					bankInstructionInfo.setTransType(TransType.NORMAL);
					bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
					bankInstructionInfo.setReceiveBank(info.getBankID());
					bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
					bankInstructionInfo.setSenderNo(String.valueOf(info.getInputUserID()));
					
					colInstructionParams.add(bankInstructionInfo);
				}
				else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
					log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
					
					
				//先拨后支方式	
				}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
					log.info("*********先拨后支，暂不考虑*********");
					
					
				}
			}
			/**门户账户模式*/
			}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
					
					//普通付款方式
					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
						log.info("*********财务公司对外付款，暂不考虑*********");
						
					
					//代理汇兑方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
						
						
					//先拨后支方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
						
					}
					
			/**收支两条线模式*/
			}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
					
					//普通付款方式
					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
						log.info("*********财务公司对外付款，暂不考虑*********");
						
					
					//代理汇兑方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
						
						
					//先拨后支方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
						
					}
			}
			log.info("------结束内部转账指令生成");	
			
			log.info("--------------结束生成指令------------");
			
		} catch (Exception e) {
			log.error("生成银行收款指令失败");
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		return colInstructionParams;
	}
	
	
	/**
	 * 根据交易明细生成银行指令
	 * @param param
	 * @throws IException
	 */
	public void createBankInstructionFromTransDetail
		(CreateInstructionParam param) throws IException {
		
		log.debug("Enter into createBankInstructionFromTransDetail(CreateInstructionParam param)");
		
		if (Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID,false)!= true ) {
			log.info("当前系统没有提供银企接口服务！");
			return;
		}
		
		if(param == null) {
			log.error("构造银行指令错误，指令参数为空！");
			throw new IException("构造银行指令错误，指令参数为空！");
		}
		if(param.getTransNo()==null || "".equals(param.getTransNo())) {
			log.error("构造银行指令错误，结算交易号参数为空！");
			throw new IException("构造银行指令错误，结算交易号参数为空！");
		}
		if(param.getTransactionTypeID() == -1) {
			log.error("构造银行指令错误，交易类型ID参数为空！");
			throw new IException("构造银行指令错误，交易类型ID参数为空！");
		}
		if(param.getInputUserID() == -1) {
			log.info("非结算系统发起的交易，不形成银行指令！");
			return;
		}
		
		log.debug("BEGIN: 根据不同银行的不同交易类型，生成不同的付款指令！");
		log.info("交易号：" + param.getTransNo());
		log.info("交易类型：" + param.getTransactionTypeID());
		
		
		//根据交易类型和编号形成不同的指令BANKRECEIVE
		if (param.getTransactionTypeID() == SETTConstant.TransactionType.BANKRECEIVE) {
			
			log.info("银行收款、上收款项、成员单位收款和转成员单位收款，不形成银行指令！");
			return;
		}
		else if(param.getTransactionTypeID() == 
			SETTConstant.TransactionType.FIXED_DEPOSIT_PREDRAW_INTEREST) {
			
			log.info("\"定期存款计提应付利息（含冲销）\"业务类型不形成银行指令！");
			return;
		}
		else if(param.getTransactionTypeID() == 
			SETTConstant.TransactionType.TRUST_LOAN_PREDRAW_INTEREST) {
			
			log.info("\"自营贷款计提应收利息（含冲销）\"业务类型不形成银行指令！");
			return;
		}
		else if (param.getTransactionTypeID() == SETTConstant.TransactionType.ONETOMULTI)//一付多收
		{
			log.info("\"一付多收\"业务类型不形成指令！");
			return;
		}		
		//特殊业务只处理总账户到总账户的头寸调拨，即收付款方为总户，在AccountBookEJB中形成指令
		else if(param.getTransactionTypeID() == SETTConstant.TransactionType.SPECIALOPERATION) {
			log.info("特殊业务只处理总账户到总账户的头寸调拨！");
			return;
		}
		else if(param.getTransactionTypeID() == SETTConstant.TransactionType.JGBAKUPSAVE) {
			log.info("机构-备付金上存不在此处生成银行指令！");
			return;
		}
		else if(param.getTransactionTypeID() == SETTConstant.TransactionType.JGBAKDRAW) {
			log.info("机构-备付金提取不生成银行指令！");
			return;
		}
		else if(param.getTransactionTypeID() == SETTConstant.TransactionType.JGBAKINTEREST) {
			log.info("机构-备付金利息收入不生成银行指令！");
			return;
		}
		else if(param.getTransactionTypeID() == SETTConstant.TransactionType.JGRESERVEUPSAVE) {
			log.info("机构-准备金上存不在此处生成银行指令！");
			return;
		}
		else if(param.getTransactionTypeID() == SETTConstant.TransactionType.JGRESERVEDRAW) {
			log.info("机构-准备金提取不生成银行指令！");
			return;
		}
		else if(param.getTransactionTypeID() == SETTConstant.TransactionType.JGRESERVEINTERES) {
			log.info("机构-准备金利息收入不生成银行指令！");
			return;
		}
		else if(param.getTransactionTypeID() == SETTConstant.TransactionType.JGLENDING) {
			log.info("机构-资金拆入不生成银行指令！");
			return;
		}
		else if(param.getTransactionTypeID() == SETTConstant.TransactionType.JGLENDINGRETURN) {
			log.info("机构-资金拆入返款不生成银行指令！");
			return;
		}
		else if(param.getTransactionTypeID() == SETTConstant.TransactionType.JGLENDINGINTEREST) {
			log.info("机构-资金拆入利息支出不生成银行指令！");
			return;
		}
		else if (param.getTransactionTypeID() == SETTConstant.TransactionType.GENERALLEDGER){
			log.info("总账业务不形成指令！");
			return;
		}
		//交易费用处理
		else if(param.getTransactionTypeID() == SETTConstant.TransactionType.TRANSFEE) {		
			
			//查询交易费用表
			TransFeeInfo transFeeInfo = new TransFeeInfo();
			Sett_TransFeeDAO transFeeDao = new Sett_TransFeeDAO();
			transFeeInfo.setTransNo(param.getTransNo());
			
			Vector vTransFee = new Vector();
			vTransFee = transFeeDao.findByTransNo(param.getTransNo(),
					param.getOfficeID(),
					param.getCurrencyID());
			
			if(vTransFee == null || (vTransFee != null && vTransFee.size()==0)) {
				log.error("查询交易费用处理时出错，交易记录为空");
				throw new SettlementException("Sett_E307",null);
			}
			transFeeInfo = (TransFeeInfo)vTransFee.get(0);
			
			//交易费用处理记录中查不到账户ID，表示交易从总账户或银行，不需要形成指令
			if(transFeeInfo.getAccountID() < 0) { 
				log.info("交易号["+param.getTransNo()+"]的交易为从总账户或外部账户发起，不需要形成指令！");
				return;
			}			
		}
		//工行的“银行付款-财司代付”
		else if(param.getTransactionTypeID() == SETTConstant.TransactionType.BANKPAY) {
			log.info("\"银行付款-财司代付\"业务类型不形成指令！");
			return;
		}
		
		Vector v = new Vector();
		sett_TransAccountDetailDAO accountDetailDao = new sett_TransAccountDetailDAO();
		try
		{
			//根据交易编号查询交易记录
			v = accountDetailDao.findByTransNo(param.getTransNo(),
					param.getOfficeID(),
					param.getCurrencyID());
		} catch (SettlementException e)
		{
			e.printStackTrace();
			throw new SettlementException("Sett_E300", e);
		}
		// 如果找不到此交易,抛出异常
		if ( v==null || (v!=null && v.size()==0))
		{
			Log.print("交易记录为空");
			
			//交易类型为信托贷款收回（自营贷款收回）时，不产生账户交易明细，但产生贷款明细，
			//根据交易号查询sett_transrepaymentloan表，若表中的交易类型为“利息费用支付”时，不产生指令，且不抛出异常
			if(param.getTransactionTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE) {
				try {					
					Sett_TransRepaymentLoanDAO transRepaymentLoanDAO = new Sett_TransRepaymentLoanDAO();					
					if(transRepaymentLoanDAO.findByID(transRepaymentLoanDAO.getIDByTransNo(param.getTransNo()))
							.getTransactionTypeID()	== SETTConstant.TransactionType.INTERESTFEEPAYMENT) {
						
						log.info("交易类型为“利息费用支付”时，不产生银行指令！");
					}
				} catch (SQLException e) {					
					e.printStackTrace();					
					throw new SettlementException("Sett_E300", e);
				}									
			}
			else {
				throw new SettlementException("Sett_E307",null);
			}
		}
		
		long lAccountID =-1; 		//活期账户ID
		long lTransactionTypeID = -1;//交易类型
		long lAccountTypeID = -1;	//账户类型
		long lOppAccountID = -1;	//对方账户
		long upBankAccountID = -1;	//隶属开户行ID
		FilialeAccountInfo[] filialAccountInfo_pay_Arr = null;		//付款账户信息(数组)
		FilialeAccountInfo[] filialAccountInfo_receive_Arr = null;	//收款账户信息(数组)
		FilialeAccountInfo filialAccountInfo_pay = null;			//付款账户信息
		FilialeAccountInfo filialAccountInfo_receive = null;		//收款账户信息
		AccountInfo accountInfo = null;	//账户信息
		BranchInfo branchInfo = null;	//活期户隶属开户行信息
		TransCurrentDepositInfo transInfo = null;//活期交易信息
		BankInstructionInfo info = null;//最终传递的数据都封装在BankInstructionInfo中
		
		Sett_FilialeAccountSetDAO filialeAccountSetDao = new Sett_FilialeAccountSetDAO();
		BankInstructionOperation bankInstructionOperation = new BankInstructionOperation();	
		Sett_TransCurrentDepositDAO transDao = new Sett_TransCurrentDepositDAO();
		Sett_AccountDAO accountDao = new Sett_AccountDAO();
		Sett_BranchDAO branchDAO = new Sett_BranchDAO();
		
		log.debug("===========开始循环构造活期交易指令===============");
		for(int i=0;i<v.size();i++) {
			TransAccountDetailInfo accountDetailInfo = new TransAccountDetailInfo();
			accountDetailInfo = (TransAccountDetailInfo)v.get(i);
			
			log.debug("交易对象===================\n" + UtilOperation.dataentityToString(accountDetailInfo));
			
			lAccountID = accountDetailInfo.getTransAccountID();//得到ACOUNTID			
			lTransactionTypeID = accountDetailInfo.getTransactionTypeID();//得到交易类型
			lOppAccountID = accountDetailInfo.getOppAccountID();//对方账户
			
			//判断交易金额,小于等于0的都不形成指令
			if (accountDetailInfo.getAmount() <= 0.0)
			{
				Log.print("交易号为["+accountDetailInfo.getTransNo()+"]的交易金额小于等于0，跳过!");
				continue;
			}

			Log.print("账户ID:" + lAccountID);
			try
			{	//如果账户ID=-1,则此笔交易不生成指令,继续循环,否则,得到此账户相关信息
				if (lAccountID != -1) {
					accountInfo =  accountDao.findByID(lAccountID);
				}
				else {
					Log.print("交易号为["+accountDetailInfo.getTransNo()+"]的交易的账户ID小于零，此笔交易不生成指令!");
					continue;
				}
			} 
			catch (SQLException sqle) {
				log.error("accountDao.findByID(" + lAccountID + ")产生错误");
				throw new SettlementException("Sett_E301", sqle);
			}
			
			//如果查不到账户信息,系统出错,继续下一个循环
			if (accountInfo == null) {
				//throw new SettlementException("Sett_E301", null);
				continue;
			}
			
			//得到此账户类型
			lAccountTypeID = accountInfo.getAccountTypeID();
			//判断是否为活期户,针对活期户处理
			if (SETTConstant.AccountType.isCurrentAccountType(lAccountTypeID)
			        || SETTConstant.AccountType.isOtherDepositAccountType(lAccountTypeID))
			{
				if (lTransactionTypeID == SETTConstant.TransactionType.INTERNALVIREMENT)//内部转账
				{
					boolean tmpBool = false;
					Log.print("--------------内部转账---------------");
					//借贷方向:借
					if (accountDetailInfo.getTransDirection() == SETTConstant.DebitOrCredit.DEBIT)
					{
						Log.print("--------------借贷方向:借---------------");
						
						//先得到付款账户信息(活期户对应的银行账户)
						Log.print("--------------取付款账户信息开始---------------");
						try
						{	//根据结算账户得到银行二级账户信息
							filialAccountInfo_pay_Arr = filialeAccountSetDao.findRefFilialeAccountInfoBySettAccountId(lAccountID);
						}
						catch (Exception e1) {
							Log.print("ERROR:根据内部账户查询银行账户时出现异常！");
							throw new SettlementException("Sett_E302",e1);
						}
						if (filialAccountInfo_pay_Arr == null || filialAccountInfo_pay_Arr.length == 0)
						{
							Log.print("ERROR:根据内部账户找不到对应的银行账户，无法形成指令！");
							continue;
						}
						else {
							if (filialAccountInfo_pay_Arr.length > 1) {
								Log.print("ERROR:根据内部账户找到多个银行账户，无法形成指令！");
								continue;
							}
							else {
								tmpBool = true;
								filialAccountInfo_pay = filialAccountInfo_pay_Arr[0];
							}
						}
						
						Log.print("--------------取付款账户信息完毕---------------");
						
						try {
							if(this.checkBankType(filialAccountInfo_pay.getBankType())) {
								
								//如果付款方有唯一的银行账户，则生成指令
								if(tmpBool) {
									
									Log.print("--------------取收款账户信息开始---------------");
									try
									{	//根据结算账户得到银行账户信息
										filialAccountInfo_receive_Arr = filialeAccountSetDao.findRefFilialeAccountInfoBySettAccountId(lOppAccountID);
									} catch (Exception e1)
									{
										Log.print("ERROR:根据内部账户查询银行账户时出现异常！");
										throw new SettlementException("Sett_E302",e1);
									}
									
									if (filialAccountInfo_receive_Arr == null 
											|| filialAccountInfo_receive_Arr.length == 0
											|| filialAccountInfo_receive_Arr.length > 1)
									{
										//收款方找不到对应的银行账户或多个银行账户，则取收款方取付款方的总户
										log.info("找不到收款账户对应的银行账户或找到多条银行账户，收款方取付款方对应的总账户！");											
										
										Log.print("--------------取总户信息开始---------------");
										upBankAccountID = filialAccountInfo_pay.getUpBankAccountID();// 隶属开户行ID
										try { // 隶属开户行ID得到银行账户信息
											Log.print("隶属开户行ID:"	+ upBankAccountID);
											branchInfo = branchDAO.findByID(upBankAccountID);
										} catch (Exception e2) {
											Log.print("ERROR:根据隶属开户行ID查银行信息出错！");
											throw new SettlementException("Sett_E303", e2);
										}
										if (branchInfo == null) {
											Log.print("ERROR:找不到此开户行所对应的银行信息！");
											throw new SettlementException("Sett_E303", null);
										} else// 转换
										{
											filialAccountInfo_receive = transferToFilialeAccountInfo(branchInfo);
										}

										Log.print("--------------取收款账户信息完毕---------------");
									}
									else 
									{
										//判断付款方银行类型为工行，则按照如下形成银行指令
										if(filialAccountInfo_pay.getBankType()==SETTConstant.BankType.ICBC) {
											
											//如果收款方的二级户不是工行，则取付款方二级户对应的开户行
											if (filialAccountInfo_receive_Arr[0].getBankType()!=SETTConstant.BankType.ICBC) 
											{
												Log.print("--------------取付款账户对应总账户信息开始---------------");
												upBankAccountID = filialAccountInfo_pay.getUpBankAccountID();//隶属开户行ID
												try
												{	//隶属开户行ID得到银行账户信息
													Log.print("隶属开户行ID:" + upBankAccountID);
													branchInfo = branchDAO.findByID(upBankAccountID);
													Log.print(branchInfo);
												} catch (Exception e2)
												{
													Log.print("ERROR:根据隶属开户行ID查银行信息出错！");
													throw new SettlementException("Sett_E303",e2);
												}
												if (branchInfo == null)
												{
													Log.print("ERROR:找不到此开户行所对应的银行信息！");
													throw new SettlementException("Sett_E303",null);
												}
												else//转换
												{
													Log.print("收款方设置为付款方对应的总账户！");
													filialAccountInfo_receive = transferToFilialeAccountInfo(branchInfo);
												}
												
												Log.print("--------------取付款账户对应总账户信息结束---------------");
											}
											else {
												filialAccountInfo_receive = filialAccountInfo_receive_Arr[0];
											}
										}
										//判断银行类型为中国银行，则形成从收款方总户到二级户的一条指令
										else if(filialAccountInfo_pay.getBankType()==SETTConstant.BankType.BOC) {
											if(filialAccountInfo_receive.getBankType()==SETTConstant.BankType.ICBC) {
												Log.print("****中行的内部转账业务，从中行到工行，生成收款方总户到二级户的一条指令！！");
												
												Log.print("--------------中行到工行的内部转账，付款账户取其对应总账户信息开始---------------");
												upBankAccountID = filialAccountInfo_receive.getUpBankAccountID();//隶属开户行ID
												try
												{	//隶属开户行ID得到银行账户信息
													Log.print("隶属开户行ID:" + upBankAccountID);
													branchInfo = branchDAO.findByID(upBankAccountID);
													Log.print(branchInfo);
												} 
												catch (Exception e2)
												{
													Log.print("ERROR:根据隶属开户行ID查银行信息出错!");
													throw new SettlementException("Sett_E303",e2);
												}
												
												if (branchInfo == null)
												{
													Log.print("ERROR:找不到此开户行所对应的银行信息!");
													throw new SettlementException("Sett_E303",null);
												}
												else//转换
												{
													Log.print("付方设置为收款方对应的总账户！");
													filialAccountInfo_pay = transferToFilialeAccountInfo(branchInfo);
												}
												Log.print("--------------中行到工行的内部转账，付款账户取其对应总账户信息结束---------------");
											}
											else if(filialAccountInfo_receive.getBankType()==SETTConstant.BankType.BOC) {
												Log.print("*****中行之间内部转账，不形成指令！！******");
											}
										}								
									}
									Log.print("--------------取收款账户信息结束---------------");
									
									//组合信息
									info = transferToBankInstruction(filialAccountInfo_pay,filialAccountInfo_receive,accountDetailInfo,param.getCheckUserID());
									//组合完毕
									Log.print("---------把数据添加到Sett_BankInstruction表中-----");
									try
									{
										bankInstructionOperation.addBankInstruction(info);
									} catch (Exception e1)
									{
										Log.print("向Sett_BankInstruction表添加指令时出现错误");
										throw new SettlementException("Sett_E306", e1);
									}
								}
							}
							else {
								log.info("账户["+filialAccountInfo_pay.getBankAccountName()+"]所属银行类型"
										+filialAccountInfo_pay.getBankType()+"不需要形成银行指令！");
							}
						} 						 
						catch (SettlementException e) {
							e.printStackTrace();
							if (e.getErrorCode().equals("Sett_E306")
									|| e.getErrorCode().equals("Sett_E302")
									|| e.getErrorCode().equals("Sett_E303"))
								throw new IException(e.getErrorCode(), e);
						}
						catch (Exception e) {
							e.printStackTrace();
							throw new IException("判断账户["
									+filialAccountInfo_pay.getBankAccountName()+"]所属银行类型是否需要形成银行指令时出错！");
						}					
					}
				}
				else //其他所有业务类型
				{
					boolean bIsfilialeAccount = false;
					//借贷方向:借
					if (accountDetailInfo.getTransDirection() == SETTConstant.DebitOrCredit.DEBIT)
					{
						Log.print("--------------借贷方向:借---------------");
						
						//先得到付款账户信息(活期户对应的银行账户)
						Log.print("--------------取付款账户信息开始---------------");
						try
						{	//根据活期户ID得到银行账户信息
							filialAccountInfo_pay_Arr = filialeAccountSetDao.findRefFilialeAccountInfoBySettAccountId(lAccountID);
						} catch (Exception e1)
						{
							Log.print("ERROR:根据内部账户查询银行账户时出现异常！");
							throw new SettlementException("Sett_E302",e1);
						}
						if (filialAccountInfo_pay_Arr == null || filialAccountInfo_pay_Arr.length == 0)
						{
							Log.print("ERROR:根据内部账户找不到对应的银行账户，无法形成指令！");
							continue;
							//throw new SettlementException("Sett_E302",null);
						}
						else 
						{
							if (filialAccountInfo_pay_Arr.length > 1)
							{
								Log.print("ERROR:根据内部账户找到多个银行账户，无法形成指令！");
								continue;
								//throw new SettlementException("Sett_E304",null);
							}
							else
							{
								bIsfilialeAccount = true;
								filialAccountInfo_pay = filialAccountInfo_pay_Arr[0];
							}
						}						
						Log.print("--------------取付款账户信息完毕---------------");
						
						try {
							if(this.checkBankType(filialAccountInfo_pay.getBankType())) {

								//判断银行类型为工行，则按照如下形成银行指令
								if(filialAccountInfo_pay.getBankType()==SETTConstant.BankType.ICBC) {
									//取收款账户信息(现在统一为付款账户隶属开户行信息)
									Log.print("--------------取收款账户信息开始---------------");
									upBankAccountID = filialAccountInfo_pay.getUpBankAccountID();//隶属开户行ID
									try
									{	//隶属开户行ID得到银行账户信息
										Log.print("隶属开户行ID:" + upBankAccountID);
										branchInfo = branchDAO.findByID(upBankAccountID);
									} catch (Exception e2)
									{
										Log.print("ERROR:根据隶属开户行ID查银行信息出错!");
										throw new SettlementException("Sett_E303",e2);
									}
									if (branchInfo == null)
									{
										Log.print("ERROR:找不到此开户行所对应的银行信息!");
										throw new SettlementException("Sett_E303",null);
									}
									else//转换
									{
										filialAccountInfo_receive = transferToFilialeAccountInfo(branchInfo);
									}
									
									Log.print("--------------取收款账户信息完毕---------------");
									
									//组合信息
									info = transferToBankInstruction(filialAccountInfo_pay,filialAccountInfo_receive,accountDetailInfo,param.getCheckUserID());
									//组合完毕
									Log.print("---------把数据添加到Sett_BankInstruction表中-----");
									try
									{
										bankInstructionOperation.addBankInstruction(info);
									} catch (Exception e1)
									{
										Log.print("向Sett_BankInstruction表添加指令时出现错误");
										throw new SettlementException("Sett_E306", e1);
									}
								}
							}
							else {
								log.info("账户["+filialAccountInfo_pay.getBankAccountName()+"]所属银行类型"
										+filialAccountInfo_pay.getBankType()+"不需要形成银行指令！");
							}
						} 
						catch (SettlementException e) {
							e.printStackTrace();
							if (e.getErrorCode().equals("Sett_E306")
									|| e.getErrorCode().equals("Sett_E303"))
								throw new IException(e.getErrorCode(), e);
						}
						catch (Exception e) {
							e.printStackTrace();
							throw new IException("判断账户["
									+filialAccountInfo_pay.getBankAccountName()+"]所属银行类型是否需要形成银行指令时出错！");
						}
					}
					//借贷方向:贷
					else if (accountDetailInfo.getTransDirection() == SETTConstant.DebitOrCredit.CREDIT)
					{
						Log.print("--------------借贷方向:贷---------------");
						
						//先得到收款账户信息(活期户对应的银行账户)
						Log.print("--------------取收款账户信息开始---------------");
						try
						{	//根据活期户ID得到银行账户信息
							filialAccountInfo_receive_Arr = filialeAccountSetDao.findRefFilialeAccountInfoBySettAccountId(lAccountID);
						} catch (Exception e1)
						{
							Log.print("ERROR:根据内部账户查询银行账户时出现异常!");
							throw new SettlementException("Sett_E302",e1);
						}
						if (filialAccountInfo_receive_Arr == null || filialAccountInfo_receive_Arr.length == 0)
						{
							Log.print("ERROR:根据内部账户找不到对应的银行账户，无法形成指令！");
							continue;
							//throw new SettlementException("Sett_E302",null);
						}
						else 
						{
							if (filialAccountInfo_receive_Arr.length > 1)
							{
								Log.print("ERROR:根据内部账户找到多个银行账户，无法形成指令！");
								continue;
								//throw new SettlementException("Sett_E304",null);
							}
							else
							{
								filialAccountInfo_receive = filialAccountInfo_receive_Arr[0];
								
								log.info("收款账户对象===========\n"+UtilOperation.dataentityToString(filialAccountInfo_receive));
							}
						}
						Log.print("--------------取收款账户信息完毕---------------"); 

						try {
							if(this.checkBankType(filialAccountInfo_receive.getBankType())) {

								//判断银行类型为工行，则按照如下形成银行指令
								if(filialAccountInfo_receive.getBankType()==SETTConstant.BankType.ICBC) {
									
									//取付款账户信息(现在统一为收款账户隶属开户行信息)
									Log.print("--------------取付款账户信息开始---------------");
									upBankAccountID = filialAccountInfo_receive.getUpBankAccountID();//隶属开户行ID
									try
									{	//隶属开户行ID得到银行账户信息
										Log.print("隶属开户行ID:" + upBankAccountID);
										branchInfo = branchDAO.findByID(upBankAccountID);
										Log.print(branchInfo);
									} catch (Exception e2)
									{
										Log.print("ERROR:根据隶属开户行ID查银行信息出错!");
										throw new SettlementException("Sett_E303",e2);
									}
									if (branchInfo == null)
									{
										Log.print("ERROR:找不到此开户行所对应的银行信息!");
										throw new SettlementException("Sett_E303",null);
									}
									else//转换
									{
										filialAccountInfo_pay = transferToFilialeAccountInfo(branchInfo);
										log.info("付款账户对象===========\n"+UtilOperation.dataentityToString(filialAccountInfo_pay));
									}
									
									Log.print("--------------取付款账户信息完毕---------------");									
									
									//组合信息
									info = transferToBankInstruction(filialAccountInfo_pay,filialAccountInfo_receive,accountDetailInfo,param.getCheckUserID());
									//组合完毕
									Log.print("---------把数据添加到Sett_BankInstruction表中-----");
									try
									{
										bankInstructionOperation.addBankInstruction(info);
									} catch (Exception e1)
									{
										Log.print("向Sett_BankInstruction表添加指令时出现错误");
										throw new SettlementException("Sett_E306", e1);
									}
								}
							}
							else {
								log.info("账户["+filialAccountInfo_receive.getBankAccountName()+"]所属银行类型"
										+filialAccountInfo_receive.getBankType()+"不需要形成银行指令！");
							}
						}
						catch (SettlementException e) {
							e.printStackTrace();
							if (e.getErrorCode().equals("Sett_E306")
									|| e.getErrorCode().equals("Sett_E303"))
								throw new IException(e.getErrorCode(), e);
						}
						catch (Exception e) {
							e.printStackTrace();							
							throw new IException("判断账户["
									+filialAccountInfo_pay.getBankAccountName()+"]所属银行类型是否需要形成银行指令时出错！");
						}
					}
					else
					{
						Log.print("ERROR:借贷方向错误");
						throw new SettlementException("Sett_E305",null);
					}
				}
				
			}
			
		}	
		
		//发送银行指令
		log.info("开始将指令发送到结算系统，结算系统将指令发送到银行！");
		this.sendBankInstruction(info);
	}
	
	
	/**
	 * 开户行对象转换为下属单位账户对象
	 * @param branchInfo
	 * @return
	 */
	private FilialeAccountInfo transferToFilialeAccountInfo(BranchInfo branchInfo)
	{
		FilialeAccountInfo info = new FilialeAccountInfo();
		
		info.setBankAccountNo(branchInfo.getBankAccountCode());
		info.setBankAccountName(branchInfo.getEnterpriseName());
		//info.setFilialeName(branchInfo.getEnterpriseName());
		info.setBranchName(branchInfo.getBranchName());
		info.setNameOfProvince(branchInfo.getBranchProvince());
		info.setNameOfCity(branchInfo.getBranchCity());
		info.setBankType(branchInfo.getBankTypeID());
		info.setBankExchangeCode(branchInfo.getBankExchangeCode());
		info.setBranchCodeOfBank(branchInfo.getBranchCodeOfBank());
		
		return info;
	}
	
	
	/**
	 * 
	 * @author weilu
	 *
	 * To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Generation - Code and Comments
	 */
	private BankInstructionInfo transferToBankInstruction(FilialeAccountInfo info_pay,FilialeAccountInfo info_receive,TransAccountDetailInfo TADInfo,long lCheckUserID) 
	{
		BankInstructionInfo info = new BankInstructionInfo();
		info.setPayAccountNo(info_pay.getBankAccountNo());			//付款账户号
		info.setPayAccountName(info_pay.getBankAccountName());		//付款账户名称
		info.setPayBankExchangeCode(info_pay.getBankExchangeCode());//付款方联行号
		info.setPayBranchCodeOfBank(info_pay.getBranchCodeOfBank());//付款方机构号
		//info.setPayDepartmentName(info_pay.getFilialeName());	
		info.setPayBranchName(info_pay.getBranchName());			//付款人开户行名称
		info.setPayAreaNameOfProvince(info_pay.getNameOfProvince());//付款人开户行所在地省名称
		info.setPayAreaNameOfCity(info_pay.getNameOfCity());		//付款人开户行所在地城市名称
		info.setAmount(TADInfo.getAmount());						//金额
		info.setCurrencyID(TADInfo.getCurrencyID());				//交易币种
		info.setOfficeId(TADInfo.getOfficeID());
		info.setReceiveAccountNo(info_receive.getBankAccountNo());	//收款账户号
		info.setReceiveAccountName(info_receive.getBankAccountName());//收款账户名称
		info.setReceiveBankExchangeCode(info_receive.getBankExchangeCode());//付款方联行号
		info.setReceiveBranchCodeOfBank(info_receive.getBranchCodeOfBank());//付款方机构号
		//info.setReceiveDepartmentName(info_receive.getFilialeName());
		info.setReceiveBranchName(info_receive.getBranchName());	//收款人开户行名称
		info.setReceiveBranchAreaNameOfProvince(info_receive.getNameOfProvince());	//收款人开户行所在地省名称
		info.setReceiveBranchAreaNameOfCity(info_receive.getNameOfCity());			//收款人开户行所在地城市名称
		info.setAbstract(TADInfo.getAbstractStr());									//摘要
		//判断是否同行，“银行付款”和“财司代付”涉及到财务公司外部银行特殊处理
		if (TADInfo.getTransactionTypeID()==SETTConstant.TransactionType.BANKPAY)	
		{
			
		}
		else
		{
			if (info_pay.getBankType()==info_receive.getBankType())
				info.setIsSameBank(Constant.TRUE);
			else
				info.setIsSameBank(Constant.FALSE);
		}
		info.setSenderNo(String.valueOf(lCheckUserID));				//指令发送人(复核人)
		info.setCreateTime(Env.getSystemDate());					//指令创建时间(数据库时间)
		info.setStatusID(SETTConstant.BankInstructionStatus.SAVED);	//指令状态(已保存,未发送)
		info.setTransactionNo(TADInfo.getTransNo());				//指令对应业务地交易号
		info.setTransType(TADInfo.getTransactionTypeID());			//指令对应业务地类型
		
		//指令接收银行
		if (TADInfo.getTransDirection() == SETTConstant.DebitOrCredit.DEBIT)//方向为借:取付款方银行类型
		{
			info.setReceiveBank(info_pay.getBankType());
		}
		else
		{
			info.setReceiveBank(info_receive.getBankType());//方向为贷:取收款方银行类型
		}
		
		try {
			if (!this.checkBankType(info.getReceiveBank()))
			{
				Log.print("============开户行"+ info.getReceiveBank() +"不是发送指令指定银行==============");
				return null;
			}
		} catch (Exception e) {
			log.error("判断指令接收银行是否需要生成银行指令时出错！");
			e.printStackTrace();
		}
		
		log.info("指令对象："+UtilOperation.dataentityToString(info));		
		
		return info;
	}
	
	/**
	 * 判断传入的银行类型ID对应的银行是否需要生成银行指令
	 * @param bankTypeID 银行类型ID
	 * @return 需要返回true, 否则返回false
	 * @throws Exception
	 */
	private boolean checkBankType(long bankTypeID) throws Exception{
		
		boolean bCreateInstruction = false;
		
		try {
			ArrayList list = new ArrayList(8);
			list = Config.getArray(Config.INTEGRATION_SERVICE_TOSENDBANKINSTRUCTION_BANKTYPE, new ArrayList(8));
			long[] bankType = new long[list.size()];
			
			log.debug("交易传入的银行类型：" + bankTypeID);	
			for(int i=0;i<list.size();i++) {
				bankType[i] = Long.parseLong((String)list.get(i));
				if(bankTypeID == bankType[i]) {
					bCreateInstruction = true;
					log.debug("配置文件中需要生成指令的银行："+bankType[i]);
					break;
				}
			}
			
		} catch (Exception e) {
			log.error("判断账户所属银行类型时出错！");
			e.printStackTrace();
		}
		
		return bCreateInstruction;
	}
	
	/**
     * 发送银行指令,只是发送机制,并不是发送的具体实现方法
     * @param sTransNo
     * @throws IException
     */
    public void sendBankInstruction(Object sTransNo) throws IException
    {
    	try{
    		if (sTransNo!=null){
		    	BankInstructionInfo instruction=(BankInstructionInfo)sTransNo;
		    	BankInstructionOperation operation=new BankInstructionOperation();
		    	operation.addBankInstruction(instruction);
    		}
    	}
    	catch(Exception e){
    		throw new IException(e.getMessage());
    	}
    	
    }
    
    /**
	 * 特殊业务财务交易形成指令
	 * 
	 * @param Object
	 * @throws IException
	 */
    private Collection createSpecialOperationIntruction(CreateInstructionParam param) throws IException {
    	Collection colInstructionParams = new ArrayList();
    	try {
			
			TransSpecialOperationInfo transInfo = new TransSpecialOperationInfo();
			transInfo = (TransSpecialOperationInfo)param.getObjInfo();
			
			Sett_BranchDAO  branchDAO = new Sett_BranchDAO();	
			
			AccountHome home =
				(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
			Account accounthome = (Account) home.create();

			log.debug("-----------开始生成特殊业务银行指令---------------");

			BranchInfo bankInfo = null;
			
			//开户行对开户行
			if(transInfo.getNpaybankid()>0 && transInfo.getNreceivebankid()>0) { 
				log.debug("-----------开户行对开户行---------------");
				 long id_pay     = transInfo.getNpaybankid();			//付款开户行号
				 long id_receive = transInfo.getNreceivebankid();	   //收款开户行号

				//added by mzh_fu 2007/07/25 账户模式与付款方式分开后,是否发送指令的控制
    			//转账方式:对外;业务类型:info.getTransactionTypeID()
    			BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingQueryInfo.setTransType(transInfo.getNoperationtypeid());
    			
				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByTransType(bankInstructionSettingQueryInfo);
				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE)
				{
					return null;
				}
				/**二级户账户模式*/
				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
						//逻辑：付款方：财务公司开户行；收款方：财务公司开户行
						BranchInfo branchInfo_pay = new BranchInfo();
					 	BranchInfo branchInfo_receive = new BranchInfo();
						branchInfo_pay = branchDAO.findByID(id_pay);
						//付款银行没有设置发送指令就不发指令
						if(branchInfo_pay.getIsAutoVirementByBank()==Constant.YesOrNo.YES)
						{
						
									branchInfo_receive = branchDAO.findByID(id_receive);
									BankInstructionInfo bankInstructionInfo = new BankInstructionInfo();
									bankInstructionInfo.setPayAccountNo("");//付款账户号(付款关联号)
									bankInstructionInfo.setPayBankAccountNO(branchInfo_pay.getBankAccountCode());//付款银行账户号
									bankInstructionInfo.setPayAcctBankCode(branchInfo_pay.getBranchCode()); //付款账户银行编号
									
									Bs_BankAccountInfoDAO bdao = new Bs_BankAccountInfoDAO();
									//取开户行设置对应的账户名称 modify by zcwang 2009-03-25
									bankInstructionInfo.setPayAccountName(branchInfo_pay.getEnterpriseName());//付款账户名称	
									bankInstructionInfo.setPayDepartmentName(branchInfo_pay.getBankServiceName());//付款人单位全称           
									bankInstructionInfo.setAmount(transInfo.getMpayamount());//金额 
									bankInstructionInfo.setReceiveReferenceCode("");//收方的内部关联号
									bankInstructionInfo.setReceiveAccountNo(branchInfo_receive.getBankAccountCode());//收款账户号 
									//取开户行设置对应的账户名称 modify by zcwang 2009-03-25
									bankInstructionInfo.setReceiveAccountName(branchInfo_receive.getEnterpriseName());//收款账户名称
									bankInstructionInfo.setReceiveDepartmentName(branchInfo_receive.getEnterpriseName());//收款人单位全称           
									bankInstructionInfo.setReceiveBranchName(branchInfo_receive.getBranchName());//收款人开户行名称 
									//2007/04/27 modified by mzh_fu 根据项目经理胡志强的意见:以branch表的账户名称作为这里的开户行名称
									
									bankInstructionInfo.setReceiveBranchAreaNameOfProvince(branchInfo_receive.getBranchProvince());//收款人开户行所在地省名称 
									bankInstructionInfo.setReceiveBranchAreaNameOfCity(branchInfo_receive.getBranchCity());//收款人开户行所在地城市名称       
									bankInstructionInfo.setTransType(TransType.NORMAL);
									bankInstructionInfo.setComment(transInfo.getSabstract());//备注                     
									bankInstructionInfo.setStrAbstract(transInfo.getSabstract());//摘要                     
									bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getNinputuserid()));//指令发送人no             
									bankInstructionInfo.setCancellerNo("");//指令撤销人no 
									bankInstructionInfo.setCreateTime( Env.getSystemDateTime());//指令创建时间            
									bankInstructionInfo.setSendTime( Env.getSystemDateTime());//指令发送时间             
									bankInstructionInfo.setCancelTime(new Timestamp(0));//指令撤销时间             
									bankInstructionInfo.setModifyTime( Env.getSystemDateTime());//指令修改时间
									bankInstructionInfo.setStatusID(SETTConstant.BankInstructionStatus.SAVED);//指令状态                 
									bankInstructionInfo.setTransactionNo(transInfo.getStransno());//指令对应业务地交易号     
									bankInstructionInfo.setReceiveBank(transInfo.getNreceivebankid());//指令接收银行             
									bankInstructionInfo.setIDOfBankSeg1("");//指令在银行地对应标识 
									bankInstructionInfo.setPayAreaNameOfProvince("");//付款人开户行所在地省名称
									bankInstructionInfo.setPayAreaNameOfCity("");//付款人开户行所在地城市名称
									bankInstructionInfo.setCurrencyID(transInfo.getNcurrencyid());//交易币种
									bankInstructionInfo.setOfficeId(transInfo.getNofficeid());//办事处id
									bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);//汇款速度
									
									//新增中行所需的联行号，机构号，手续费账号
									bankInstructionInfo.setPayChargeAccountNo("");//手续费账号 
									bankInstructionInfo.setPayChargeBankExchangeCode("");//手续费账户联行号
									bankInstructionInfo.setPayChargeBranchCodeOfBank("");//手续费账户机构号				
									bankInstructionInfo.setRecNetStationName(""); //收款方网点名称				
									colInstructionParams.add(bankInstructionInfo);
						}
					
					
				/**门户账户模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
					//逻辑：付款方：财务公司开户行；收款方：财务公司开户行
					BranchInfo branchInfo_pay = new BranchInfo();
				 	BranchInfo branchInfo_receive = new BranchInfo();
					branchInfo_pay = branchDAO.findByID(id_pay);
					if(branchInfo_pay.getIsAutoVirementByBank()==Constant.YesOrNo.YES)
					{
							branchInfo_receive = branchDAO.findByID(id_receive);
							BankInstructionInfo bankInstructionInfo = new BankInstructionInfo();
							bankInstructionInfo.setPayAccountNo("");//付款账户号(付款关联号)
							bankInstructionInfo.setPayBankAccountNO(branchInfo_pay.getBankAccountCode());//付款银行账户号
							bankInstructionInfo.setPayAcctBankCode(branchInfo_pay.getBranchCode()); //付款账户银行编号
							Bs_BankAccountInfoDAO bdao = new Bs_BankAccountInfoDAO();
							//取开户行设置对应的账户名称 modify by zcwang 2009-03-25
							bankInstructionInfo.setPayAccountName(branchInfo_pay.getEnterpriseName());//付款账户名称	
							bankInstructionInfo.setPayDepartmentName(branchInfo_pay.getBankServiceName());//付款人单位全称           
							bankInstructionInfo.setAmount(transInfo.getMpayamount());//金额 
							bankInstructionInfo.setReceiveReferenceCode("");//收方的内部关联号
							bankInstructionInfo.setReceiveAccountNo(branchInfo_receive.getBankAccountCode());//收款账户号 
							//取开户行设置对应的账户名称 modify by zcwang 2009-03-25
							bankInstructionInfo.setReceiveAccountName(branchInfo_receive.getEnterpriseName());//收款账户名称
							bankInstructionInfo.setReceiveDepartmentName(branchInfo_receive.getEnterpriseName());//收款人单位全称           
							bankInstructionInfo.setReceiveBranchName(branchInfo_receive.getBranchName());//收款人开户行名称 
							//2007/04/27 modified by mzh_fu 根据项目经理胡志强的意见:以branch表的账户名称作为这里的开户行名称
							
							bankInstructionInfo.setReceiveBranchAreaNameOfProvince(branchInfo_receive.getBranchProvince());//收款人开户行所在地省名称 
							bankInstructionInfo.setReceiveBranchAreaNameOfCity(branchInfo_receive.getBranchCity());//收款人开户行所在地城市名称       
							bankInstructionInfo.setTransType(TransType.NORMAL);
							bankInstructionInfo.setComment(transInfo.getSabstract());//备注                     
							bankInstructionInfo.setStrAbstract(transInfo.getSabstract());//摘要                     
							bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getNinputuserid()));//指令发送人no             
							bankInstructionInfo.setCancellerNo("");//指令撤销人no 
							bankInstructionInfo.setCreateTime( Env.getSystemDateTime());//指令创建时间            
							bankInstructionInfo.setSendTime( Env.getSystemDateTime());//指令发送时间             
							bankInstructionInfo.setCancelTime(new Timestamp(0));//指令撤销时间             
							bankInstructionInfo.setModifyTime( Env.getSystemDateTime());//指令修改时间
							bankInstructionInfo.setStatusID(SETTConstant.BankInstructionStatus.SAVED);//指令状态                 
							bankInstructionInfo.setTransactionNo(transInfo.getStransno());//指令对应业务地交易号     
							bankInstructionInfo.setReceiveBank(transInfo.getNreceivebankid());//指令接收银行             
							bankInstructionInfo.setIDOfBankSeg1("");//指令在银行地对应标识 
							bankInstructionInfo.setPayAreaNameOfProvince("");//付款人开户行所在地省名称
							bankInstructionInfo.setPayAreaNameOfCity("");//付款人开户行所在地城市名称
							bankInstructionInfo.setCurrencyID(transInfo.getNcurrencyid());//交易币种
							bankInstructionInfo.setOfficeId(transInfo.getNofficeid());//办事处id
							bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);//汇款速度
							
							//新增中行所需的联行号，机构号，手续费账号
							bankInstructionInfo.setPayChargeAccountNo("");//手续费账号 
							bankInstructionInfo.setPayChargeBankExchangeCode("");//手续费账户联行号
							bankInstructionInfo.setPayChargeBranchCodeOfBank("");//手续费账户机构号				
							bankInstructionInfo.setRecNetStationName(""); //收款方网点名称				
							colInstructionParams.add(bankInstructionInfo);
					}
				
				
			/**门户账户模式*/
			}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
				//逻辑：付款方：财务公司开户行；收款方：财务公司开户行
				BranchInfo branchInfo_pay = new BranchInfo();
			 	BranchInfo branchInfo_receive = new BranchInfo();
				branchInfo_pay = branchDAO.findByID(id_pay);
				if(branchInfo_pay.getIsAutoVirementByBank()==Constant.YesOrNo.YES)
				{
						branchInfo_receive = branchDAO.findByID(id_receive);
					
						BankInstructionInfo bankInstructionInfo = new BankInstructionInfo();
		
						bankInstructionInfo.setPayAccountNo("");//付款账户号(付款关联号)
						bankInstructionInfo.setPayBankAccountNO(branchInfo_pay.getBankAccountCode());//付款银行账户号
						bankInstructionInfo.setPayAcctBankCode(branchInfo_pay.getBranchCode()); //付款账户银行编号
						
						Bs_BankAccountInfoDAO bdao = new Bs_BankAccountInfoDAO();
						//取开户行设置对应的账户名称 modify by zcwang 2009-03-25
						bankInstructionInfo.setPayAccountName(branchInfo_pay.getEnterpriseName());//付款账户名称 	
						bankInstructionInfo.setPayDepartmentName(branchInfo_pay.getBankServiceName());//付款人单位全称           
						bankInstructionInfo.setAmount(transInfo.getMpayamount());//金额 
						bankInstructionInfo.setReceiveReferenceCode("");//收方的内部关联号
						bankInstructionInfo.setReceiveAccountNo(branchInfo_receive.getBankAccountCode());//收款账户号 
						//取开户行设置对应的账户名称 modify by zcwang 2009-03-25
						bankInstructionInfo.setReceiveAccountName(branchInfo_receive.getEnterpriseName());//收款账户名称 
						bankInstructionInfo.setReceiveDepartmentName(branchInfo_receive.getEnterpriseName());//收款人单位全称           
						bankInstructionInfo.setReceiveBranchName(branchInfo_receive.getBranchName());//收款人开户行名称 
						//2007/04/27 modified by mzh_fu 根据项目经理胡志强的意见:以branch表的账户名称作为这里的开户行名称
						
						bankInstructionInfo.setReceiveBranchAreaNameOfProvince(branchInfo_receive.getBranchProvince());//收款人开户行所在地省名称 
						bankInstructionInfo.setReceiveBranchAreaNameOfCity(branchInfo_receive.getBranchCity());//收款人开户行所在地城市名称       
						bankInstructionInfo.setTransType(TransType.NORMAL);
						bankInstructionInfo.setComment(transInfo.getSabstract());//备注                     
						bankInstructionInfo.setStrAbstract(transInfo.getSabstract());//摘要                     
						bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getNinputuserid()));//指令发送人no             
						bankInstructionInfo.setCancellerNo("");//指令撤销人no 
						bankInstructionInfo.setCreateTime( Env.getSystemDateTime());//指令创建时间            
						bankInstructionInfo.setSendTime( Env.getSystemDateTime());//指令发送时间             
						bankInstructionInfo.setCancelTime(new Timestamp(0));//指令撤销时间             
						bankInstructionInfo.setModifyTime( Env.getSystemDateTime());//指令修改时间
						bankInstructionInfo.setStatusID(SETTConstant.BankInstructionStatus.SAVED);//指令状态                 
						bankInstructionInfo.setTransactionNo(transInfo.getStransno());//指令对应业务地交易号     
						bankInstructionInfo.setReceiveBank(transInfo.getNreceivebankid());//指令接收银行             
						bankInstructionInfo.setIDOfBankSeg1("");//指令在银行地对应标识 
						bankInstructionInfo.setPayAreaNameOfProvince("");//付款人开户行所在地省名称
						bankInstructionInfo.setPayAreaNameOfCity("");//付款人开户行所在地城市名称
						bankInstructionInfo.setCurrencyID(transInfo.getNcurrencyid());//交易币种
						bankInstructionInfo.setOfficeId(transInfo.getNofficeid());//办事处id
						bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);//汇款速度
						
						//新增中行所需的联行号，机构号，手续费账号
						bankInstructionInfo.setPayChargeAccountNo("");//手续费账号 
						bankInstructionInfo.setPayChargeBankExchangeCode("");//手续费账户联行号
						bankInstructionInfo.setPayChargeBranchCodeOfBank("");//手续费账户机构号				
						bankInstructionInfo.setRecNetStationName(""); //收款方网点名称				
						colInstructionParams.add(bankInstructionInfo);
				}
		}
			 
			}
			// 活期与开户行
			if((transInfo.getNpaybankid()>0 && transInfo.getNreceiveaccountid()>0)||(transInfo.getNpayaccountid()>0 && transInfo.getNreceivebankid()>0)) {
				
				//added by mzh_fu 2007/03/30 是否生成指令检验
				long lAccouontID = -1;
				BankInstructionSettingInfo bankInstructionSettingInfo=new BankInstructionSettingInfo();				
				
				log.debug("-----------活期与开户行---------------");
				if (transInfo.getNpaybankid()>0 && transInfo.getNreceiveaccountid()>0)
				{
					
					// added by mzh_fu 2007/07/25 账户模式与付款方式分开后,是否发送指令的控制
					lAccouontID = transInfo.getNreceiveaccountid();
					BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
					bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
					bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
					bankInstructionSettingQueryInfo.setTransType(transInfo.getNoperationtypeid());
					BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByTransType(bankInstructionSettingQueryInfo);
					bankInstructionSettingQueryInfo.setAccountId(lAccouontID);
					BankInstructionSettingInfo _bankInstructionSettingInfo1 = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
					if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE||_bankInstructionSettingInfo1.getIsSend() != Constant.TRUE)
					{
						return null;
					}
					/**二级户账户模式*/
					if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL
						&&_bankInstructionSettingInfo1.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
						
						//普通付款方式
						if(_bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							//逻辑：付款方：财务公司开户行；收款方：活期账户对应的银行二级账户
							log.debug("-----------开户行到活期(开户行为借方)---------------");						
							//根据付款方BankID，获得付款方对应的开户行信息
							BranchInfo branchInfo = new BranchInfo();	
							branchInfo = branchDAO.findByID(transInfo.getNpaybankid());
										
							//构造指令
							log.info("--------------开户行到活期行指令------------");
							//当指定开户行不存在、指定开户行无银行类型、无银行账户号，交易中无下属单位账户信息和外部账户信息则不生成指令

							BankInstructionInfo bankInstructionInfo = new BankInstructionInfo();					

							//根据收款方accountID，获得收款方信息
							AccountInfo accountInfo=new AccountInfo();
							Sett_AccountDAO dao=new Sett_AccountDAO();
							accountInfo=dao.findByID(transInfo.getNreceiveaccountid());
							
							log.info("*********开始生成下属单位到外部账户的银行付款指令*********");
							
							Bs_BankAccountInfoDAO bdao = new Bs_BankAccountInfoDAO();
							//取开户行设置对应的账户名称 modify by zcwang 2009-03-25
							//String accname = bdao.getAcctNameByNo(branchInfo.getBankAccountCode());
							
							//付款方信息，付款方为开户行
							bankInstructionInfo.setPayAccountNo("");
							bankInstructionInfo.setPayBankAccountNO(branchInfo.getBankAccountCode());
							bankInstructionInfo.setPayAccountName(branchInfo.getEnterpriseName());
							bankInstructionInfo.setPayAcctBankCode(branchInfo.getBranchCode());					

							//收款方信息，收款方为活期账户
							bankInstructionInfo.setReceiveReferenceCode(accountInfo.getAccountNo());
							bankInstructionInfo.setReceiveAccountNo("");
							bankInstructionInfo.setReceiveAccountName(accountInfo.getAccountName());
			
							bankInstructionInfo.setReceiveBankExchangeCode("");
							bankInstructionInfo.setReceiveBranchCodeOfBank("");					
							bankInstructionInfo.setTransactionNo(transInfo.getStransno());
							bankInstructionInfo.setTransType(TransType.NORMAL);
							//金额，币种 等其他信息
							bankInstructionInfo.setAmount(transInfo.getMpayamount());				
							bankInstructionInfo.setCurrencyID(transInfo.getNcurrencyid());
							bankInstructionInfo.setOfficeId(transInfo.getNofficeid());
							bankInstructionInfo.setAbstract(transInfo.getSabstract());
							bankInstructionInfo.setTransactionNo(transInfo.getStransno());
							bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
							bankInstructionInfo.setReceiveBank(branchInfo.getBankTypeID());
							bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
							bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getNinputuserid()));
							colInstructionParams.add(bankInstructionInfo);
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
							
						}
						
					/**门户账户模式*/
					}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
							
							//普通付款方式
							if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
								log.info("*********财务公司对外付款，暂不考虑*********");
								
							
							//代理汇兑方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
								log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
								
								
							//先拨后支方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
								log.info("*********先拨后支，暂不考虑*********");
								
							}
							
					/**收支两条线模式*/
					}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
							
							//普通付款方式
							if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
								log.info("*********财务公司对外付款，暂不考虑*********");
								
							
							//代理汇兑方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
								log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
								
								
							//先拨后支方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
								log.info("*********先拨后支，暂不考虑*********");
								
							}
					}
				}
				else if (transInfo.getNpayaccountid()>0 && transInfo.getNreceivebankid()>0)
				{
					log.info("--------------活期到开户行指令------------");
					//added by mzh_fu 2007/03/30 是否生成指令检验
					/*********************begin*****************************/
					lAccouontID=transInfo.getNpayaccountid();
					BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
					bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
					bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
					bankInstructionSettingQueryInfo.setTransType(transInfo.getNoperationtypeid());
					BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByTransType(bankInstructionSettingQueryInfo);
					bankInstructionSettingQueryInfo.setAccountId(lAccouontID);
					BankInstructionSettingInfo _bankInstructionSettingInfo1 = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
					if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE||_bankInstructionSettingInfo1.getIsSend() != Constant.TRUE)
					{
						return null;
					}
					/**********************end****************************/
					/**二级户账户模式*/
					if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL
						&&_bankInstructionSettingInfo1.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
						
						//普通付款方式
						if(_bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							//逻辑：付款方：活期账户对应的银行二级账户；收款方：财务公司开户行
							//根据收款方BankID，获得收款方对应的开户行信息
							BranchInfo branchInfo = new BranchInfo();								
							branchInfo = branchDAO.findByID(transInfo.getNreceivebankid());
							Bs_BankAccountInfoDAO bdao = new Bs_BankAccountInfoDAO();
							//取开户行设置对应的账户名称 modify by zcwang 2009-03-25
							//String accname = bdao.getAcctNameByNo(branchInfo.getBankAccountCode());
				
							BankInstructionInfo bankInstructionInfo = new BankInstructionInfo();					

							AccountInfo accountInfo=new AccountInfo();
							Sett_AccountDAO dao=new Sett_AccountDAO();
							accountInfo=dao.findByID(transInfo.getNpayaccountid());

							//付款方账户信息，
							bankInstructionInfo.setPayAccountNo(accountInfo.getAccountNo());

							//收款方信息
							bankInstructionInfo.setReceiveAccountNo(branchInfo.getBankAccountCode());
							bankInstructionInfo.setReceiveAccountName(branchInfo.getEnterpriseName()); //账户名称
							bankInstructionInfo.setReceiveDepartmentName(branchInfo.getEnterpriseName());
							
							bankInstructionInfo.setReceiveBranchName(branchInfo.getBranchName());
							//2007/04/27 modified by mzh_fu 根据项目经理胡志强的意见:以branch表的账户名称作为这里的开户行名称
							//bankInstructionInfo.setReceiveBranchName(branchInfo.getEnterpriseName());
							
							bankInstructionInfo.setReceiveBranchAreaNameOfProvince(branchInfo.getBranchProvince());					
							bankInstructionInfo.setReceiveBranchAreaNameOfCity(branchInfo.getBranchCity());
							
							bankInstructionInfo.setReceiveBankExchangeCode("");
							bankInstructionInfo.setReceiveBranchCodeOfBank("");					
							bankInstructionInfo.setTransactionNo(transInfo.getStransno());
							bankInstructionInfo.setTransType(TransType.NORMAL);
							//金额，币种 等其他信息
							bankInstructionInfo.setAmount(transInfo.getMpayamount());				
							bankInstructionInfo.setCurrencyID(transInfo.getNcurrencyid());
							bankInstructionInfo.setOfficeId(transInfo.getNofficeid());
							bankInstructionInfo.setAbstract(transInfo.getSabstract());
							bankInstructionInfo.setTransactionNo(transInfo.getStransno());
							bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
							bankInstructionInfo.setReceiveBank(branchInfo.getBankTypeID());
							bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
							bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getNinputuserid()));//指令发送人no 
							colInstructionParams.add(bankInstructionInfo);
							//代理汇兑方式	
						}else if (_bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
							
						}
						
					/**门户账户模式*/
					}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
							
							//普通付款方式
							if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
								log.info("*********财务公司对外付款，暂不考虑*********");
								
							
							//代理汇兑方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
								log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
								
								
							//先拨后支方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
								log.info("*********先拨后支，暂不考虑*********");
								
							}
							
					/**收支两条线模式*/
					}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
							
							//普通付款方式
							if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
								log.info("*********财务公司对外付款，暂不考虑*********");
								
							
							//代理汇兑方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
								log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
								
								
							//先拨后支方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
								log.info("*********先拨后支，暂不考虑*********");
								
							}
					}
				}
			}
			// 活期与总账
			if((transInfo.getNpaygeneralledgertypeid()>0 && transInfo.getNreceiveaccountid()>0)||(transInfo.getNreceivegeneralledgertypeid()>0 && transInfo.getNpayaccountid()>0)) { 
				
				//added by mzh_fu 2007/03/30 是否生成指令检验
				long lAccouontID=-1;
				BankInstructionSettingInfo bankInstructionSettingInfo=new BankInstructionSettingInfo();
				
				RespGetGeneralBankAcctInfo  generalbankacctinfo = new RespGetGeneralBankAcctInfo();
//	    		BankInstructionOperation bankInstructionOperation = new BankInstructionOperation();//实现info转换成银企接口info
	    		
	    		long payAccountID = transInfo.getNpayaccountid();
	    		long arriveAccountId = transInfo.getNreceiveaccountid();   	

				if(transInfo.getNreceivegeneralledgertypeid() > 0 )//借
				{
					log.debug("-----------活期账户为借---------------");					
					
					//added by mzh_fu 2007/07/25 账户模式与付款方式分开后,是否发送指令的控制
					lAccouontID = payAccountID;
					BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
					bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
					bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
					bankInstructionSettingQueryInfo.setTransType(transInfo.getNoperationtypeid());
					bankInstructionSettingQueryInfo.setAccountId(lAccouontID);
					BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
				
					if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE)
					{
						return null;
					}
					
					/**二级户账户模式*/
					if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							//逻辑：付款方：活期账户对应的银行二级账户；收款方：银行二级账户的上级银行账户
							ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
							instruction.setSystemId(1);//系统id
							instruction.setReferenceCode(accounthome.findAccountByID(payAccountID).getAccountNo());
				    		//取得resp总账info
				    		generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
				    		if(generalbankacctinfo==null ||generalbankacctinfo.getAccountInfo()==null)
				    		{
				    			throw new Exception("未找到活期账户的上级银行账户");
				    		}
				    		BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
							
							bankInstructionInfo.setPayAccountNo(accounthome.findAccountByID(payAccountID).getAccountNo());
							bankInstructionInfo.setPayBankAccountNO("");//付款银行账户号
							bankInstructionInfo.setPayAcctBankCode("");//付款账户银行编号
							bankInstructionInfo.setPayAccountName(accounthome.findAccountByID(payAccountID).getAccountName());//付款账户名称             
							bankInstructionInfo.setPayDepartmentName(accounthome.findAccountByID(payAccountID).getAccountName());//付款人单位全称           
							bankInstructionInfo.setPayBranchName(accounthome.findAccountByID(payAccountID).getAccountName());//付款人开户行名称
		
							//收款方信息
							bankInstructionInfo.setReceiveAccountNo(generalbankacctinfo.getAccountInfo().getAccountNO());
							bankInstructionInfo.setReceiveAccountName(generalbankacctinfo.getAccountInfo().getAccountName());					
							bankInstructionInfo.setReceiveReferenceCode("");
							bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctinfo.getAccountInfo().getBranchCity());
							bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctinfo.getAccountInfo().getBranchProvince());
							bankInstructionInfo.setReceiveDepartmentName(generalbankacctinfo.getAccountInfo().getBankDirectLinkCode());
							bankInstructionInfo.setReceiveBranchName(generalbankacctinfo.getAccountInfo().getBankName());
		
							bankInstructionInfo.setReceiveBankExchangeCode("");
							bankInstructionInfo.setReceiveBranchCodeOfBank("");					
							bankInstructionInfo.setTransactionNo(transInfo.getStransno());
							bankInstructionInfo.setTransType(TransType.NORMAL);
							//金额，币种 等其他信息
							bankInstructionInfo.setAmount(transInfo.getMpayamount());				
							bankInstructionInfo.setCurrencyID(transInfo.getNcurrencyid());
							bankInstructionInfo.setOfficeId(transInfo.getNofficeid());
							bankInstructionInfo.setAbstract(transInfo.getSabstract());
							bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
							bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
							bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getNinputuserid()));
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
							
						}
						
					/**门户账户模式*/
					}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
							
							//普通付款方式
							if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
								log.info("*********财务公司对外付款，暂不考虑*********");
								
							
							//代理汇兑方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
								log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
								
								
							//先拨后支方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
								log.info("*********先拨后支，暂不考虑*********");
								
							}
							
					/**收支两条线模式*/
					}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
							
							//普通付款方式
							if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
								log.info("*********财务公司对外付款，暂不考虑*********");
								
							
							//代理汇兑方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
								log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
								
								
							//先拨后支方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
								log.info("*********先拨后支，暂不考虑*********");
								
							}
					}
							
				}
				else//贷
				{
					log.debug("-----------活期账户为贷---------------");
					//added by mzh_fu 2007/03/30 是否生成指令检验
					/*********************begin*****************************/
					lAccouontID = arriveAccountId;

					BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
					bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
					bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
					bankInstructionSettingQueryInfo.setTransType(transInfo.getNoperationtypeid());
					bankInstructionSettingQueryInfo.setAccountId(lAccouontID);
					BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
				
					if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE)
					{
						return null;
					}
					/**********************end****************************/
					/**二级户账户模式*/
					if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							//逻辑：付款方：银行二级户的上级银行账户；收款方：活期账户对应的银行二级账户
							ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
							instruction.setSystemId(1);//系统id
							instruction.setReferenceCode(accounthome.findAccountByID(arriveAccountId).getAccountNo());
				    		//取得resp总账info
				    		generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
							if(generalbankacctinfo==null ||generalbankacctinfo.getAccountInfo()==null)
							{
								throw new Exception("未找到活期账户的上级银行账户信息");
							}
							
							BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
							
							bankInstructionInfo.setPayAccountNo("");
							bankInstructionInfo.setPayBankAccountNO(generalbankacctinfo.getAccountInfo().getAccountNO());//付款银行账户号
							bankInstructionInfo.setPayAcctBankCode(generalbankacctinfo.getAccountInfo().getBankCode());//付款账户银行编号
							bankInstructionInfo.setPayAccountName(generalbankacctinfo.getAccountInfo().getAccountName());//付款账户名称             
							bankInstructionInfo.setPayDepartmentName(generalbankacctinfo.getAccountInfo().getAccountName());//付款人单位全称           
							bankInstructionInfo.setPayBranchName(generalbankacctinfo.getAccountInfo().getAccountName());//付款人开户行名称

							//收款方信息
							bankInstructionInfo.setReceiveAccountNo("");
							bankInstructionInfo.setReceiveAccountName((accounthome.findAccountByID(arriveAccountId)).getAccountName());
							bankInstructionInfo.setReceiveReferenceCode((accounthome.findAccountByID(arriveAccountId)).getAccountNo());
							
							bankInstructionInfo.setReceiveBranchName(generalbankacctinfo.getAccountInfo().getBankName());
							
							bankInstructionInfo.setReceiveBankExchangeCode("");
							bankInstructionInfo.setReceiveBranchCodeOfBank("");					
							bankInstructionInfo.setTransactionNo(transInfo.getStransno());
							bankInstructionInfo.setTransType(TransType.NORMAL);
							//金额，币种 等其他信息
							bankInstructionInfo.setAmount(transInfo.getMpayamount());				
							bankInstructionInfo.setCurrencyID(transInfo.getNcurrencyid());
							bankInstructionInfo.setOfficeId(transInfo.getNofficeid());
							bankInstructionInfo.setAbstract(transInfo.getSabstract());
							bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
							bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
							bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getNinputuserid()));
							colInstructionParams.add(bankInstructionInfo);
							//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
							
						}
						
					/**门户账户模式*/
					}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
							
							//普通付款方式
							if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
								log.info("*********财务公司对外付款，暂不考虑*********");
								
							
							//代理汇兑方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
								log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
								
								
							//先拨后支方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
								log.info("*********先拨后支，暂不考虑*********");
								
							}
							
					/**收支两条线模式*/
					}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
							
							//普通付款方式
							if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
								log.info("*********财务公司对外付款，暂不考虑*********");
								
							
							//代理汇兑方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
								log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
								
								
							//先拨后支方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
								log.info("*********先拨后支，暂不考虑*********");
								
							}
					}
				}	
			}
			//外部银行与开户行，活期账户
			if((transInfo.getSextaccountno() != null && transInfo.getSextaccountno() != "" && transInfo.getNpayaccountid()>0) ||
					(transInfo.getSextaccountno() != null && transInfo.getSextaccountno() != "" && transInfo.getNpaybankid()>0)) { 
				log.debug("-----------外部银行与开户行，活期账户---------------");

				RespGetGeneralBankAcctInfo  generalbankacctinfo = new RespGetGeneralBankAcctInfo();
	    		BankInstructionOperation bankInstructionOperation = new BankInstructionOperation();//实现info转换成银企接口info

				if(transInfo.getNpayaccountid()>0 )//借
				{
					log.debug("-----------活期账户为借(活期账户到外部银行)---------------");
					
					long payAccountID = transInfo.getNpayaccountid();

					BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
					bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
					bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
					bankInstructionSettingQueryInfo.setTransType(transInfo.getNoperationtypeid());
					bankInstructionSettingQueryInfo.setAccountId(payAccountID);
					BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
				
					if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE)
					{
						return null;
					}				
					/**二级户账户模式*/
					if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							//逻辑：付款方：活期账户对应的银行二级账户；收款方：外部银行账户
							
				    		BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
							
							bankInstructionInfo.setPayAccountNo(accounthome.findAccountByID(payAccountID).getAccountNo());
							bankInstructionInfo.setPayBankAccountNO("");//付款银行账户号
							bankInstructionInfo.setPayAcctBankCode("");//付款账户银行编号
							bankInstructionInfo.setPayAccountName(accounthome.findAccountByID(payAccountID).getAccountName());//付款账户名称             
							bankInstructionInfo.setPayDepartmentName(accounthome.findAccountByID(payAccountID).getAccountName());//付款人单位全称           
							bankInstructionInfo.setPayBranchName(accounthome.findAccountByID(payAccountID).getAccountName());//付款人开户行名称
							
							
							//收款方信息，收款方为外部银行账户
							bankInstructionInfo.setReceiveAccountNo(transInfo.getSextaccountno());
							bankInstructionInfo.setReceiveAccountName(transInfo.getSextclientname());					
							bankInstructionInfo.setReceiveReferenceCode("");
							bankInstructionInfo.setReceiveBranchAreaNameOfCity(transInfo.getSremitincity());
							bankInstructionInfo.setReceiveBranchAreaNameOfProvince(transInfo.getSremitinprovince());
							bankInstructionInfo.setReceiveBranchName(transInfo.getSremitinbank());			
							
							bankInstructionInfo.setReceiveBankExchangeCode("");
							bankInstructionInfo.setReceiveBranchCodeOfBank("");					
							bankInstructionInfo.setTransactionNo(transInfo.getStransno());
							bankInstructionInfo.setTransType(TransType.NORMAL);
							//金额，币种 等其他信息
							bankInstructionInfo.setAmount(transInfo.getMpayamount());				
							bankInstructionInfo.setCurrencyID(transInfo.getNcurrencyid());
							bankInstructionInfo.setOfficeId(transInfo.getNofficeid());
							bankInstructionInfo.setAbstract(transInfo.getSabstract());
							bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
							bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
							bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getNinputuserid()));
							colInstructionParams.add(bankInstructionInfo);
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
							
						}
						
					/**门户账户模式*/
					}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
							
							//普通付款方式
							if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
								log.info("*********财务公司对外付款，暂不考虑*********");
								
							
							//代理汇兑方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
								log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
								
								
							//先拨后支方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
								log.info("*********先拨后支，暂不考虑*********");
								
							}
							
					/**收支两条线模式*/
					}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
							
							//普通付款方式
							if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
								log.info("*********财务公司对外付款，暂不考虑*********");
								
							
							//代理汇兑方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
								log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
								
								
							//先拨后支方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
								log.info("*********先拨后支，暂不考虑*********");
								
							}
					}
					
				}
				else
				{
					log.debug("-----------开户账户为借(开户账户到外部银行)---------------");
					
					long payBankID = transInfo.getNpaybankid();

					
	    			 //added by mzh_fu 2007/07/25 账户模式与付款方式分开后,是否发送指令的控制
					BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
					bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
					bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
					bankInstructionSettingQueryInfo.setTransType(transInfo.getNoperationtypeid());
					BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByTransType(bankInstructionSettingQueryInfo);
					
					if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE){ 
						return null;
					}
					/**二级户账户模式*/
					if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
						
						//逻辑：付款方：财务公司开户行；收款方：外部银行账户
						//根据付款方BankID，获得收款方对应的开户行信息
							BranchInfo branchInfo = new BranchInfo();								
							branchInfo = branchDAO.findByID(payBankID);
							//付款银行没有设置发送指令就不发指令
							if(branchInfo.getIsAutoVirementByBank()==Constant.YesOrNo.YES)
							{
								BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
								bankInstructionInfo.setPayAccountNo("");
								bankInstructionInfo.setPayBankAccountNO(branchInfo.getBankAccountCode());//付款银行账户号
								bankInstructionInfo.setPayAcctBankCode(branchInfo.getBankAccountCode());//付款账户银行编号
								bankInstructionInfo.setPayAccountName(branchInfo.getBankServiceName());//付款账户名称             
								bankInstructionInfo.setPayDepartmentName(branchInfo.getBankServiceName());//付款人单位全称           
								//2007/04/27 modified by mzh_fu 根据项目经理胡志强的意见:以branch表的账户名称作为这里的开户行名称
								bankInstructionInfo.setPayBranchName(branchInfo.getEnterpriseName());
	
								
			//					收款方信息，收款方为外部银行账户
								bankInstructionInfo.setReceiveAccountNo(transInfo.getSextaccountno());
								bankInstructionInfo.setReceiveAccountName(transInfo.getSextclientname());					
								bankInstructionInfo.setReceiveReferenceCode("");
								bankInstructionInfo.setReceiveBranchAreaNameOfCity(transInfo.getSremitincity());
								bankInstructionInfo.setReceiveBranchAreaNameOfProvince(transInfo.getSremitinprovince());
								bankInstructionInfo.setReceiveBranchName(transInfo.getSremitinbank());						
								
								bankInstructionInfo.setReceiveBankExchangeCode("");
								bankInstructionInfo.setReceiveBranchCodeOfBank("");					
								bankInstructionInfo.setTransactionNo(transInfo.getStransno());
								bankInstructionInfo.setTransType(TransType.NORMAL);
								//金额，币种 等其他信息
								bankInstructionInfo.setAmount(transInfo.getMpayamount());				
								bankInstructionInfo.setCurrencyID(transInfo.getNcurrencyid());
								bankInstructionInfo.setOfficeId(transInfo.getNofficeid());
								bankInstructionInfo.setAbstract(transInfo.getSabstract());
								bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
								bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
								bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getNinputuserid()));
								colInstructionParams.add(bankInstructionInfo);
							}
						
						
					/**门户账户模式*/
					}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){//逻辑：付款方：财务公司开户行；收款方：外部银行账户
						//根据付款方BankID，获得收款方对应的开户行信息
						BranchInfo branchInfo = new BranchInfo();								
						branchInfo = branchDAO.findByID(payBankID);
						//付款银行没有设置发送指令就不发指令
						if(branchInfo.getIsAutoVirementByBank()==Constant.YesOrNo.YES)
						{
						
							BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
							
							bankInstructionInfo.setPayAccountNo("");
							bankInstructionInfo.setPayBankAccountNO(branchInfo.getBankAccountCode());//付款银行账户号
							bankInstructionInfo.setPayAcctBankCode(branchInfo.getBankAccountCode());//付款账户银行编号
							bankInstructionInfo.setPayAccountName(branchInfo.getBankServiceName());//付款账户名称             
							bankInstructionInfo.setPayDepartmentName(branchInfo.getBankServiceName());//付款人单位全称           
							//2007/04/27 modified by mzh_fu 根据项目经理胡志强的意见:以branch表的账户名称作为这里的开户行名称
							bankInstructionInfo.setPayBranchName(branchInfo.getEnterpriseName());
	
							
		//					收款方信息，收款方为外部银行账户
							bankInstructionInfo.setReceiveAccountNo(transInfo.getSextaccountno());
							bankInstructionInfo.setReceiveAccountName(transInfo.getSextclientname());					
							bankInstructionInfo.setReceiveReferenceCode("");
							bankInstructionInfo.setReceiveBranchAreaNameOfCity(transInfo.getSremitincity());
							bankInstructionInfo.setReceiveBranchAreaNameOfProvince(transInfo.getSremitinprovince());
							bankInstructionInfo.setReceiveBranchName(transInfo.getSremitinbank());						
							
							bankInstructionInfo.setReceiveBankExchangeCode("");
							bankInstructionInfo.setReceiveBranchCodeOfBank("");					
							bankInstructionInfo.setTransactionNo(transInfo.getStransno());
							bankInstructionInfo.setTransType(TransType.NORMAL);
							//金额，币种 等其他信息
							bankInstructionInfo.setAmount(transInfo.getMpayamount());				
							bankInstructionInfo.setCurrencyID(transInfo.getNcurrencyid());
							bankInstructionInfo.setOfficeId(transInfo.getNofficeid());
							bankInstructionInfo.setAbstract(transInfo.getSabstract());
							bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
							bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
							bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getNinputuserid()));
							colInstructionParams.add(bankInstructionInfo);
						}
					}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){//逻辑：付款方：财务公司开户行；收款方：外部银行账户
						//根据付款方BankID，获得收款方对应的开户行信息
							BranchInfo branchInfo = new BranchInfo();								
							branchInfo = branchDAO.findByID(payBankID);
							//付款银行没有设置发送指令就不发指令
							if(branchInfo.getIsAutoVirementByBank()==Constant.YesOrNo.YES)
							{
							
								BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
								
								bankInstructionInfo.setPayAccountNo("");
								bankInstructionInfo.setPayBankAccountNO(branchInfo.getBankAccountCode());//付款银行账户号
								bankInstructionInfo.setPayAcctBankCode(branchInfo.getBankAccountCode());//付款账户银行编号
								bankInstructionInfo.setPayAccountName(branchInfo.getBankServiceName());//付款账户名称             
								bankInstructionInfo.setPayDepartmentName(branchInfo.getBankServiceName());//付款人单位全称           
								//2007/04/27 modified by mzh_fu 根据项目经理胡志强的意见:以branch表的账户名称作为这里的开户行名称
								bankInstructionInfo.setPayBranchName(branchInfo.getEnterpriseName());
	
								
			//					收款方信息，收款方为外部银行账户
								bankInstructionInfo.setReceiveAccountNo(transInfo.getSextaccountno());
								bankInstructionInfo.setReceiveAccountName(transInfo.getSextclientname());					
								bankInstructionInfo.setReceiveReferenceCode("");
								bankInstructionInfo.setReceiveBranchAreaNameOfCity(transInfo.getSremitincity());
								bankInstructionInfo.setReceiveBranchAreaNameOfProvince(transInfo.getSremitinprovince());
								bankInstructionInfo.setReceiveBranchName(transInfo.getSremitinbank());						
								
								bankInstructionInfo.setReceiveBankExchangeCode("");
								bankInstructionInfo.setReceiveBranchCodeOfBank("");					
								bankInstructionInfo.setTransactionNo(transInfo.getStransno());
								bankInstructionInfo.setTransType(TransType.NORMAL);
								//金额，币种 等其他信息
								bankInstructionInfo.setAmount(transInfo.getMpayamount());				
								bankInstructionInfo.setCurrencyID(transInfo.getNcurrencyid());
								bankInstructionInfo.setOfficeId(transInfo.getNofficeid());
								bankInstructionInfo.setAbstract(transInfo.getSabstract());
								bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
								bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
								bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getNinputuserid()));
								colInstructionParams.add(bankInstructionInfo);
							}
					}
					
				}
			}
			else if(transInfo.getNpayaccountid()>0 && transInfo.getNreceiveaccountid()>0)//内部转账
			{
				BankInstructionSettingInfo bankInstructionSettingQueryInfos = new BankInstructionSettingInfo();
				bankInstructionSettingQueryInfos.setNOfficeId(param.getOfficeID());
				bankInstructionSettingQueryInfos.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingQueryInfos.setTransType(transInfo.getNoperationtypeid());
				bankInstructionSettingQueryInfos.setAccountId(transInfo.getNpayaccountid());
				BankInstructionSettingInfo bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfos);
				bankInstructionSettingQueryInfos.setAccountId(transInfo.getNreceiveaccountid());
				BankInstructionSettingInfo bankInstructionSettingInfo1 = getSettingInfoByAccountId(bankInstructionSettingQueryInfos);
				if (bankInstructionSettingInfo.getIsSend() != Constant.TRUE && bankInstructionSettingInfo1.getIsSend() != Constant.TRUE) { 
					return null;
				}
				//当双方都是二级户 只发一条指令
				if(bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL && bankInstructionSettingInfo1.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL)
				{

					
					//普通付款方式
					if(bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY
						&&bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
						//逻辑：付款方：活期账户对应的银行二级户；收款方：活期账户对应的银行二级户

						BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
						AccountInfo accountInfo=new AccountInfo();
						Sett_AccountDAO dao=new Sett_AccountDAO();			
						accountInfo=dao.findByID(transInfo.getNpayaccountid());
						bankInstructionInfo.setPayAccountNo(accountInfo.getAccountNo());
						accountInfo=dao.findByID(transInfo.getNreceiveaccountid());
						bankInstructionInfo.setReceiveReferenceCode(accountInfo.getAccountNo());
							

						bankInstructionInfo.setAmount(transInfo.getMpayamount());//金额     
						bankInstructionInfo.setTransType(TransType.NORMAL);
						bankInstructionInfo.setComment(transInfo.getSabstract());//备注                     
						bankInstructionInfo.setStrAbstract(transInfo.getSabstract());//摘要                     
						bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getNinputuserid()));//指令发送人no             
						bankInstructionInfo.setCancellerNo("");//指令撤销人no 
						bankInstructionInfo.setCreateTime( Env.getSystemDateTime());//指令创建时间            
						bankInstructionInfo.setSendTime( Env.getSystemDateTime());//指令发送时间             
						bankInstructionInfo.setCancelTime(new Timestamp(0));//指令撤销时间             
						bankInstructionInfo.setModifyTime( Env.getSystemDateTime());//指令修改时间
						bankInstructionInfo.setStatusID(SETTConstant.BankInstructionStatus.SAVED);//指令状态                 
						bankInstructionInfo.setTransactionNo(transInfo.getStransno());//指令对应业务地交易号     
						bankInstructionInfo.setReceiveBank(transInfo.getNreceivebankid());//指令接收银行             
						bankInstructionInfo.setIDOfBankSeg1("");//指令在银行地对应标识 
						bankInstructionInfo.setPayAreaNameOfProvince("");//付款人开户行所在地省名称
						bankInstructionInfo.setPayAreaNameOfCity("");//付款人开户行所在地城市名称
						bankInstructionInfo.setCurrencyID(transInfo.getNcurrencyid());//交易币种
						bankInstructionInfo.setOfficeId(transInfo.getNofficeid());
						bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);//汇款速度
						
						bankInstructionInfo.setPayChargeAccountNo("");//手续费账号 
						bankInstructionInfo.setPayChargeBankExchangeCode("");//手续费账户联行号
						bankInstructionInfo.setPayChargeBranchCodeOfBank("");//手续费账户机构号
						bankInstructionInfo.setRecNetStationName(""); //收款方网点名称
						colInstructionParams.add(bankInstructionInfo);
					//代理汇兑方式	
					}else if (bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT
							&&bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
						
						
					//先拨后支方式	
					}else if (bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY
							&&bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
						
						
					}
				
				}	
			else
			{
				if(transInfo.getNpayaccountid()>0)
				{
//					added by mzh_fu 2007/03/30 是否生成指令检验
					/*********************begin*****************************/
					BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
					bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
					bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
					bankInstructionSettingQueryInfo.setTransType(transInfo.getNoperationtypeid());
					bankInstructionSettingQueryInfo.setAccountId(transInfo.getNpayaccountid());
					BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
					if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE)
					{
						return null;
					}
					/**********************end****************************/
					/**二级户账户模式*/
					if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							long id_pay     = transInfo.getNpayaccountid();			//付款账户号
							BankInstructionInfo bankInstructionInfo = new BankInstructionInfo();

							//获得付款方账户的上级银行账号单位
							ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
							instruction.setSystemId(1);
							AccountInfo payAccountInfo = accounthome.findAccountByID(id_pay);
							instruction.setReferenceCode(payAccountInfo.getAccountNo());
							RespGetGeneralBankAcctInfo generalbankacctinfoPay = BPClientAgent.getGeneralBankAcctInfo(instruction);
							if(generalbankacctinfoPay == null || generalbankacctinfoPay.getAccountInfo()==null)
							{
								throw new Exception("未找到付款方账户的上级银行账户信息");
							}
							
							bankInstructionInfo.setPayAccountNo(accounthome.findAccountByID(id_pay).getAccountNo());//付款账户号(付款关联号)
							bankInstructionInfo.setPayBankAccountNO("");//付款银行账户号
							bankInstructionInfo.setPayAcctBankCode(""); //付款账户银行编号
							bankInstructionInfo.setPayAccountName(accounthome.findAccountByID(id_pay).getAccountName());//付款账户名称             
							bankInstructionInfo.setPayDepartmentName(accounthome.findAccountByID(id_pay).getAccountName());//付款人单位全称           
							bankInstructionInfo.setPayBranchName("");//付款人开户行名称         
							                
							//收款方信息
							bankInstructionInfo.setReceiveAccountNo(generalbankacctinfoPay.getAccountInfo().getAccountNO());//收款账户号
							bankInstructionInfo.setReceiveAccountName(generalbankacctinfoPay.getAccountInfo().getAccountName());//收款账户名称
							bankInstructionInfo.setReceiveBranchName(generalbankacctinfoPay.getAccountInfo().getBankName());//收款人开户行名称
							bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctinfoPay.getAccountInfo().getBranchProvince());//收款人开户行所在地省名称
							bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctinfoPay.getAccountInfo().getBranchCity());//收款人开户行所在地城市名称
							
							bankInstructionInfo.setAmount(transInfo.getMpayamount());//金额     
							bankInstructionInfo.setTransType(TransType.NORMAL);
							bankInstructionInfo.setComment(transInfo.getSabstract());//备注                     
							bankInstructionInfo.setStrAbstract(transInfo.getSabstract());//摘要                     
							bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getNinputuserid()));//指令发送人no             
							bankInstructionInfo.setCancellerNo("");//指令撤销人no 
							bankInstructionInfo.setCreateTime( Env.getSystemDateTime());//指令创建时间            
							bankInstructionInfo.setSendTime( Env.getSystemDateTime());//指令发送时间             
							bankInstructionInfo.setCancelTime(new Timestamp(0));//指令撤销时间             
							bankInstructionInfo.setModifyTime( Env.getSystemDateTime());//指令修改时间
							bankInstructionInfo.setStatusID(SETTConstant.BankInstructionStatus.SAVED);//指令状态                 
							bankInstructionInfo.setTransactionNo(transInfo.getStransno());//指令对应业务地交易号     
							bankInstructionInfo.setReceiveBank(transInfo.getNreceivebankid());//指令接收银行             
							bankInstructionInfo.setIDOfBankSeg1("");//指令在银行地对应标识 
							bankInstructionInfo.setPayAreaNameOfProvince("");//付款人开户行所在地省名称
							bankInstructionInfo.setPayAreaNameOfCity("");//付款人开户行所在地城市名称
							bankInstructionInfo.setCurrencyID(transInfo.getNcurrencyid());//交易币种
							bankInstructionInfo.setOfficeId(transInfo.getNofficeid());
							bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);//汇款速度
							
							bankInstructionInfo.setPayChargeAccountNo("");//手续费账号 
							bankInstructionInfo.setPayChargeBankExchangeCode("");//手续费账户联行号
							bankInstructionInfo.setPayChargeBranchCodeOfBank("");//手续费账户机构号
							bankInstructionInfo.setRecNetStationName(""); //收款方网点名称
							colInstructionParams.add(bankInstructionInfo);
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
							
						}
						
					/**门户账户模式*/
					}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
							
							//普通付款方式
							if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
								log.info("*********财务公司对外付款，暂不考虑*********");
								
							
							//代理汇兑方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
								log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
								
								
							//先拨后支方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
								log.info("*********先拨后支，暂不考虑*********");
								
							}
							
					/**收支两条线模式*/
					}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
							
							//普通付款方式
							if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
								log.info("*********财务公司对外付款，暂不考虑*********");
								
							
							//代理汇兑方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
								log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
								
								
							//先拨后支方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
								log.info("*********先拨后支，暂不考虑*********");
								
							}
					}
				}
				if(transInfo.getNreceiveaccountid()>0)
				{
					//added by mzh_fu 2007/03/30 是否生成指令检验
					/*********************begin*****************************/
					BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
					bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
					bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
					bankInstructionSettingQueryInfo.setTransType(transInfo.getNoperationtypeid());
					bankInstructionSettingQueryInfo.setAccountId(transInfo.getNreceiveaccountid());
					BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
					if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE)
					{
						return null;
					}
					/**********************end****************************/
					/**二级户账户模式*/
					if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							long id_receive = transInfo.getNreceiveaccountid();	   //收款账户号	

							BankInstructionInfo bankInstructionInfo = new BankInstructionInfo();

							//获得付款方账户的上级银行账号单位
							ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
							instruction.setSystemId(1);
							AccountInfo payAccountInfo = accounthome.findAccountByID(id_receive);
							instruction.setReferenceCode(payAccountInfo.getAccountNo());
							RespGetGeneralBankAcctInfo generalbankacctinfoPay = BPClientAgent.getGeneralBankAcctInfo(instruction);
							if(generalbankacctinfoPay == null || generalbankacctinfoPay.getAccountInfo()==null)
							{
								throw new Exception("未找到收款方账户的上级银行账户信息");
							}
							bankInstructionInfo.setPayBankAccountNO(generalbankacctinfoPay.getAccountInfo().getAccountNO())  ;                  
							bankInstructionInfo.setReceiveAccountNo("");//收款账户号               
							bankInstructionInfo.setReceiveAccountName(accounthome.findAccountByID(id_receive).getAccountName());//收款账户名称             
							bankInstructionInfo.setReceiveDepartmentName(accounthome.findAccountByID(id_receive).getAccountName());//收款人单位全称           
							bankInstructionInfo.setReceiveBranchName(accounthome.findAccountByID(id_receive).getAccountName());//收款人开户行名称        
							bankInstructionInfo.setTransType(TransType.NORMAL);
							bankInstructionInfo.setAmount(transInfo.getMpayamount());//金额 
							bankInstructionInfo.setComment(transInfo.getSabstract());//备注                     
							bankInstructionInfo.setStrAbstract(transInfo.getSabstract());//摘要                     
							bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getNinputuserid()));//指令发送人no             
							bankInstructionInfo.setCancellerNo("");//指令撤销人no 
							bankInstructionInfo.setCreateTime( Env.getSystemDateTime());//指令创建时间            
							bankInstructionInfo.setSendTime( Env.getSystemDateTime());//指令发送时间             
							bankInstructionInfo.setCancelTime(new Timestamp(0));//指令撤销时间             
							bankInstructionInfo.setModifyTime( Env.getSystemDateTime());//指令修改时间
							bankInstructionInfo.setStatusID(SETTConstant.BankInstructionStatus.SAVED);//指令状态                 
							bankInstructionInfo.setTransactionNo(transInfo.getStransno());//指令对应业务地交易号     
							bankInstructionInfo.setReceiveBank(transInfo.getNreceivebankid());//指令接收银行             
							bankInstructionInfo.setIDOfBankSeg1("");//指令在银行地对应标识 
							bankInstructionInfo.setPayAreaNameOfProvince("");//付款人开户行所在地省名称
							bankInstructionInfo.setPayAreaNameOfCity("");//付款人开户行所在地城市名称
							bankInstructionInfo.setCurrencyID(transInfo.getNcurrencyid());//交易币种
							bankInstructionInfo.setOfficeId(transInfo.getNofficeid());
							//bankInstructionInfo.setIsSameBank("");//同行标志（true或false）
							bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);//汇款速度
							//bankInstructionInfo.setChargesBorneType();//费用承担方
							
							bankInstructionInfo.setPayChargeAccountNo("");//手续费账号 
							bankInstructionInfo.setPayChargeBankExchangeCode("");//手续费账户联行号
							bankInstructionInfo.setPayChargeBranchCodeOfBank("");//手续费账户机构号
							bankInstructionInfo.setReceiveReferenceCode(accounthome.findAccountByID(id_receive).getAccountNo());//收方的内部关联号
							bankInstructionInfo.setRecNetStationName(""); //收款方网点名称
							colInstructionParams.add(bankInstructionInfo);
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
							
						}
						
					/**门户账户模式*/
					}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
							
							//普通付款方式
							if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
								log.info("*********财务公司对外付款，暂不考虑*********");
								
							
							//代理汇兑方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
								log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
								
								
							//先拨后支方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
								log.info("*********先拨后支，暂不考虑*********");
								
							}
							
					/**收支两条线模式*/
					}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
							
							//普通付款方式
							if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
								log.info("*********财务公司对外付款，暂不考虑*********");
								
							
							//代理汇兑方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
								log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
								
								
							//先拨后支方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
								log.info("*********先拨后支，暂不考虑*********");
								
							}
						}
					}
				}
			}
			log.debug("-----------生成特殊业务银行指令结束---------------");
			
		} catch (Exception e) {			
			log.info("根据交易对象形成特殊业务银行指令时出错，无法形成指令！");
			e.printStackTrace();
			throw new IException(e.getMessage(),e);			
		}
		return colInstructionParams;
    }
    
    /**
	 * 特殊业务财务交易形成指令
	 * 
	 * @param Object
	 * @throws IException
	 */
    private Collection createBakAndReserveUpSaveOperationIntruction(CreateInstructionParam param) throws IException {
    	Collection colInstructionParams = new ArrayList();
    	try {
			
			TransSpecialOperationInfo transInfo = new TransSpecialOperationInfo();
			transInfo = (TransSpecialOperationInfo)param.getObjInfo();
			
			Sett_BranchDAO  branchDAO = new Sett_BranchDAO();	


			log.debug("-----------开始生成 机构  备付金上存 准备金上存 银行指令---------------");
			
			BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
			bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
			bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
			bankInstructionSettingQueryInfo.setTransType(transInfo.getNtransactiontypeid());
			
			BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByTransType(bankInstructionSettingQueryInfo);
			if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE)
			{
				return null;
			}

				
			
				if(transInfo.getNreceivebankid()>0)
				{
					
					BranchInfo branchInfo = new BranchInfo();								
					branchInfo = branchDAO.findByID(transInfo.getNreceivebankid());
					//Bs_BankAccountInfoDAO bdao = new Bs_BankAccountInfoDAO();
					//取开户行设置对应的账户名称 modify by zcwang 2009-03-25
					//String accname = bdao.getAcctNameByNo(branchInfo.getBankAccountCode());
		
					//付款银行没有设置发送指令就不发指令
					if(branchInfo.getIsAutoVirementByBank()==Constant.YesOrNo.YES)
					{
					
						BankInstructionInfo bankInstructionInfo = new BankInstructionInfo();					
		
						//付款方信息，付款方为开户行
						bankInstructionInfo.setPayAccountNo("");
						bankInstructionInfo.setPayBankAccountNO(branchInfo.getBankAccountCode());
						bankInstructionInfo.setPayAccountName(branchInfo.getEnterpriseName());
						bankInstructionInfo.setPayAcctBankCode(branchInfo.getBranchCode());					
						bankInstructionInfo.setPayBranchName(branchInfo.getBranchName());
						
						//收款方信息，收款方为外部银行账户
						bankInstructionInfo.setReceiveAccountNo(transInfo.getSextaccountno());
						bankInstructionInfo.setReceiveAccountName(transInfo.getSextclientname());					
						bankInstructionInfo.setReceiveReferenceCode("");
						bankInstructionInfo.setReceiveBranchAreaNameOfCity(transInfo.getSremitincity());
						bankInstructionInfo.setReceiveBranchAreaNameOfProvince(transInfo.getSremitinprovince());
						bankInstructionInfo.setReceiveBranchName(transInfo.getSremitinbank());			
						
						bankInstructionInfo.setReceiveBankExchangeCode(transInfo.getSpayeebankexchangeno());
						bankInstructionInfo.setReceiveBranchCodeOfBank("");					
						bankInstructionInfo.setTransactionNo(transInfo.getStransno());
						bankInstructionInfo.setTransType(TransType.NORMAL);
		
						//金额，币种 等其他信息
						bankInstructionInfo.setAmount(transInfo.getMpayamount());				
						bankInstructionInfo.setCurrencyID(transInfo.getNcurrencyid());
						bankInstructionInfo.setOfficeId(transInfo.getNofficeid());
						bankInstructionInfo.setAbstract(transInfo.getSabstract());
						bankInstructionInfo.setTransactionNo(transInfo.getStransno());
						bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
						bankInstructionInfo.setReceiveBank(branchInfo.getBankTypeID());
						bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
						bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getNinputuserid()));
		
						colInstructionParams.add(bankInstructionInfo);
				}
			}
			log.debug("-----------生成特殊业务银行指令结束---------------");
			
		} catch (Exception e) {			
			log.info("根据交易对象形成特殊业务银行指令时出错，无法形成指令！");
			e.printStackTrace();
			throw new IException(e.getMessage(),e);			
		}
		return colInstructionParams;
    }
    /**
	 * 一付多收财务交易形成指令
	 * 
	 * @param Object
	 * @throws IException
	 */
    private Collection createOneToMultiIntruction(CreateInstructionParam param) throws IException {
    	
    		Collection colInstructionParams = new ArrayList();
    		try {
    		//生成银行指令新逻辑 start
    		TransOnePayMultiReceiveInfo transInfo = new TransOnePayMultiReceiveInfo();
			transInfo = (TransOnePayMultiReceiveInfo)param.getObjInfo();
			
			
			log.debug("-----------开始生成一付多收业务银行指令---------------");
			if(transInfo.getAccountID() >0 && transInfo.getTypeID() == SETTConstant.ReceiveOrPay.PAY) 
			{
				BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
				bankInstructionSettingQueryInfo.setAccountId(transInfo.getAccountID());

				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
				{
					return null;
				}
				BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
				AccountHome home =
					(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
				Account accounthome = (Account) home.create();
				/**二级户账户模式*/
				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
					
					//普通付款方式
					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
						//逻辑：付款方: 内部活期账户对应的银行二级户；收款方：银行二级户的上级银行账户

						//付款方
						bankInstructionInfo.setPayAccountNo(accounthome.findAccountByID(transInfo.getAccountID()).getAccountNo());	
						//
						//获得付款方账户的上级银行账号单位
						ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
						instruction.setSystemId(1);
						AccountInfo accountInfo = accounthome.findAccountByID(transInfo.getAccountID());
						instruction.setReferenceCode(accountInfo.getAccountNo());
						RespGetGeneralBankAcctInfo generalbankacctInfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
						if(generalbankacctInfo == null || generalbankacctInfo.getAccountInfo()==null)
						{
							throw new Exception("未找到付款方账户的上级银行账户信息");
						}
						//
						//收款方
						bankInstructionInfo.setReceiveAccountNo(generalbankacctInfo.getAccountInfo().getAccountNO());//收款账户号
						bankInstructionInfo.setReceiveAccountName(generalbankacctInfo.getAccountInfo().getAccountName());//收款账户名称
						bankInstructionInfo.setReceiveBranchName(generalbankacctInfo.getAccountInfo().getBankName());//收款人开户行名称
						bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctInfo.getAccountInfo().getBranchProvince());//收款人开户行所在地省名称
						bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctInfo.getAccountInfo().getBranchCity());//收款人开户行所在地城市名称
						
						bankInstructionInfo.setTransType(TransType.NORMAL);
					
					//代理汇兑方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
						
						bankInstructionInfo.setTransType(TransType.AGENTVIREMENT);
						
					//先拨后支方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
						
						
					}
					
				/**门户账户模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
						
				/**收支两条线模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
				}
				//金额，币种 等其他信息
				bankInstructionInfo.setAmount(transInfo.getAmount());				
				bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
				bankInstructionInfo.setOfficeId(transInfo.getOfficeID());//办事处id
				bankInstructionInfo.setAbstract(transInfo.getAbstract());
				bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
				bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
				bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
				bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
				
				colInstructionParams.add(bankInstructionInfo);
			}
			if(transInfo.getAccountID() >0 && transInfo.getTypeID() == SETTConstant.ReceiveOrPay.RECEIVE) 
			{
				BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
				bankInstructionSettingQueryInfo.setAccountId(transInfo.getAccountID());

				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
				{
					return null;
				}
				BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
				AccountHome home =
					(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
				Account accounthome = (Account) home.create();
				/**二级户账户模式*/
				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
					
					//普通付款方式
					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
						//逻辑：付款方:银行二级户的上级银行账户；收款方：内部活期账户对应的银行二级户

						//获得付款方账户的上级银行账号单位
						ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
						instruction.setSystemId(1);
						AccountInfo accountInfo = accounthome.findAccountByID(transInfo.getAccountID());
						instruction.setReferenceCode(accountInfo.getAccountNo());
						RespGetGeneralBankAcctInfo generalbankacctInfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
						if(generalbankacctInfo == null || generalbankacctInfo.getAccountInfo()==null)
						{
							throw new Exception("未找到付款方账户的上级银行账户信息");
						}
						//
						
				 		//付款方信息
						bankInstructionInfo.setPayBankAccountNO(generalbankacctInfo.getAccountInfo().getAccountNO());//付款银行账户号
						bankInstructionInfo.setPayAcctBankCode(generalbankacctInfo.getAccountInfo().getBankCode()); //付款账户银行编号
						bankInstructionInfo.setPayBranchName(generalbankacctInfo.getAccountInfo().getBankName());
						bankInstructionInfo.setPayAreaNameOfProvince(generalbankacctInfo.getAccountInfo().getBranchProvince());
						bankInstructionInfo.setPayAreaNameOfCity(generalbankacctInfo.getAccountInfo().getBranchCity());

						//收款方信息
						bankInstructionInfo.setReceiveReferenceCode(accountInfo.getAccountNo());//收款账户号
						bankInstructionInfo.setReceiveAccountName(accountInfo.getAccountName());//收款账户名称
						bankInstructionInfo.setReceiveBranchName(accountInfo.getAccountName());//收款人开户行名称
						//
						
						bankInstructionInfo.setTransType(TransType.NORMAL);
					
					//代理汇兑方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款 ，暂不考虑*********");
						
					//先拨后支方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
						
					}
					
				/**门户账户模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
						
				/**收支两条线模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
				}
				//金额，币种 等其他信息
				bankInstructionInfo.setAmount(transInfo.getAmount());				
				bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
				bankInstructionInfo.setOfficeId(transInfo.getOfficeID());//办事处id
				bankInstructionInfo.setAbstract(transInfo.getAbstract());
				bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
				bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
				bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
				bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
				
				colInstructionParams.add(bankInstructionInfo);
			}
			if(transInfo.getBankID() >0 && transInfo.getTypeID() == SETTConstant.ReceiveOrPay.RECEIVE) 
			{
				BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
    			
				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByTransType(bankInstructionSettingQueryInfo);
				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE)
				{
					return null;
				}
				BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
				AccountHome home =
					(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
				Account accounthome = (Account) home.create();
				/**二级户账户模式*/
				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
					
						//逻辑：付款方: 财务公司开户行；收款方：外部收款方银行账号
						//付款方: 财务公司开户行
						Sett_BranchDAO  branchDAO = new Sett_BranchDAO();
						BranchInfo branchInfo = branchDAO.findByID(transInfo.getBankID()); 
						//开户行没有设置发指令，就不发银行指令
						if(branchInfo.getIsAutoVirementByBank()==Constant.YesOrNo.YES)
						{
							bankInstructionInfo.setPayBankAccountNO(branchInfo.getBankAccountCode());//付款银行账户号
							bankInstructionInfo.setPayAccountName(branchInfo.getBankServiceName());//付款账户名称             
							bankInstructionInfo.setPayDepartmentName(branchInfo.getBankServiceName());//付款人单位全称
							//收款方信息，收款方为外部银行账户
							bankInstructionInfo.setReceiveAccountNo(transInfo.getExtAccountNo());
							bankInstructionInfo.setReceiveAccountName(transInfo.getExtClientName());
							bankInstructionInfo.setReceiveBranchName(transInfo.getRemitInBank());
							bankInstructionInfo.setReceiveBranchAreaNameOfProvince(transInfo.getRemitInProvince());
							bankInstructionInfo.setReceiveBranchAreaNameOfCity(transInfo.getRemitInCity());
							bankInstructionInfo.setReceiveBankExchangeCode("");
							bankInstructionInfo.setReceiveBranchCodeOfBank("");					
							//
							
							bankInstructionInfo.setTransType(TransType.NORMAL);
						}
					
				/**门户账户模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
						
				/**收支两条线模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
				}
				
				//金额，币种 等其他信息
				bankInstructionInfo.setAmount(transInfo.getAmount());				
				bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
				bankInstructionInfo.setOfficeId(transInfo.getOfficeID());//办事处id
				bankInstructionInfo.setAbstract(transInfo.getAbstract());
				bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
				bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
				bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
				bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
				
				colInstructionParams.add(bankInstructionInfo);
			}
			
		
    	} catch (Exception e) {			
			log.info("根据交易对象形成一付多收业务指令时出错，无法形成指令！");
			e.printStackTrace();
			throw new IException(e.getMessage(),e);			
		}
    	return colInstructionParams;
    }
    
    
    /**
	 * 贴现发放交易形成指令
	 * 修改历史:mzh_fu 2007/03/30 将原Object参数改为CreateInstructionParam类型
	 * 
	 * @param Object
	 * @throws IException
	 */
    private Collection createGrantDiscountIntruction(CreateInstructionParam param) throws IException {
    	Collection colInstructionParams = new ArrayList();
    	try {
    		
    		TransGrantDiscountInfo transInfo = new TransGrantDiscountInfo();
    		transInfo = (TransGrantDiscountInfo)param.getObjInfo();
    		
    		Sett_BranchDAO  branchDAO = new Sett_BranchDAO();	
			
			AccountHome home =
				(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
			Account accounthome = (Account) home.create();

    		if(transInfo.getDepositAccountID() > 0)
    		{
    			log.debug("-----------开始生成贴现发放银行指令---发放至活期账户---------------");

    			//added by mzh_fu 2007/03/30 是否生成指令检验
    			/*********************begin*****************************/
    			long lAccouontID=transInfo.getDepositAccountID();

    			BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
    			bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
    			bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
    			bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
    			bankInstructionSettingQueryInfo.setAccountId(lAccouontID);
    			//added by mzh_fu 2007/09/27
    			bankInstructionSettingQueryInfo.setTransModule(SETTConstant.TransModule.INSIDE_PAY);// 内转

				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
				
				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
					return null;		
    			
    			/**********************end****************************/
				/**二级户账户模式*/
				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
					
					//普通付款方式
					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
						//逻辑：付款方：银行二级账户的上级银行账户；收款方：活期账户对应的银行二级账户
						//获得活期账户的上级单位银行账号
		    			ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
		    			instruction.setSystemId(1);//系统id
		    			instruction.setReferenceCode(accounthome.findAccountByID(transInfo.getDepositAccountID()).getAccountNo());
		    			RespGetGeneralBankAcctInfo  generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
		    			if(generalbankacctinfo==null ||generalbankacctinfo.getAccountInfo()==null)
		    			{
		    				throw new Exception("未找到活期账户的上级银行账户信息");
		    			}
		    			//二级账户信息
		    			AccountInfo account = accounthome.findAccountByID(transInfo.getDepositAccountID());

		    			BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
		    			
		    			bankInstructionInfo.setPayAccountNo("");//付款账户号(付款关联号)
						bankInstructionInfo.setPayBankAccountNO(generalbankacctinfo.getAccountInfo().getAccountNO());//付款银行账户号
						bankInstructionInfo.setPayAcctBankCode(generalbankacctinfo.getAccountInfo().getBankCode()); //付款账户银行编号
						bankInstructionInfo.setPayAccountName(generalbankacctinfo.getAccountInfo().getAccountName());//付款账户名称             
						bankInstructionInfo.setPayDepartmentName(generalbankacctinfo.getAccountInfo().getAccountName());//付款人单位全称           
						bankInstructionInfo.setPayBranchName(generalbankacctinfo.getAccountInfo().getAccountName());//付款人开户行名称         
						bankInstructionInfo.setAmount(transInfo.getDiscountAmount());//金额
						bankInstructionInfo.setReceiveReferenceCode(account.getAccountNo());//收方的内部关联号
						bankInstructionInfo.setReceiveAccountNo(account.getAccountNo());//收款账户号               
						bankInstructionInfo.setReceiveAccountName(account.getAccountName());//收款账户名称             
						bankInstructionInfo.setReceiveDepartmentName(account.getAccountName());//收款人单位全称           
						bankInstructionInfo.setReceiveBranchName(account.getAccountName());//收款人开户行名称        
						bankInstructionInfo.setReceiveBranchAreaNameOfProvince("");//收款人开户行所在地省名称 
						bankInstructionInfo.setReceiveBranchAreaNameOfCity("");//收款人开户行所在地城市名称  
						bankInstructionInfo.setTransType(TransType.NORMAL);
						bankInstructionInfo.setComment(transInfo.getAbstract());//备注                     
						bankInstructionInfo.setStrAbstract(transInfo.getAbstract());//摘要                     
						bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));//指令发送人no             
						bankInstructionInfo.setCancellerNo("");//指令撤销人no 
						bankInstructionInfo.setCreateTime( Env.getSystemDateTime());//指令创建时间            
						bankInstructionInfo.setSendTime( Env.getSystemDateTime());//指令发送时间             
						bankInstructionInfo.setCancelTime(new Timestamp(0));//指令撤销时间             
						bankInstructionInfo.setModifyTime( Env.getSystemDateTime());//指令修改时间
						bankInstructionInfo.setStatusID(SETTConstant.BankInstructionStatus.SAVED);//指令状态                 
						bankInstructionInfo.setTransactionNo(transInfo.getTransNo());//指令对应业务地交易号     
						bankInstructionInfo.setIDOfBankSeg1("");//指令在银行地对应标识 
						bankInstructionInfo.setPayAreaNameOfProvince("");//付款人开户行所在地省名称
						bankInstructionInfo.setPayAreaNameOfCity("");//付款人开户行所在地城市名称
						bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());//交易币种
						bankInstructionInfo.setOfficeId(transInfo.getOfficeID());//办事处id
						bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);//汇款速度
						
						//新增中行所需的联行号，机构号，手续费账号	 
						bankInstructionInfo.setPayChargeAccountNo("");//手续费账号 
						bankInstructionInfo.setPayChargeBankExchangeCode("");//手续费账户联行号
						bankInstructionInfo.setPayChargeBranchCodeOfBank("");//手续费账户机构号				
						bankInstructionInfo.setRecNetStationName(""); //收款方网点名称
						colInstructionParams.add(bankInstructionInfo);
	    			//代理汇兑方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
						
					//先拨后支方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
						
						
					}
					
				/**门户账户模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
						
				/**收支两条线模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
				}

    		}
    		
    		else if ( transInfo.getDiscountAccountID() > 0 && transInfo.getBankID()>0)
    		{
    			log.debug("-----------开始生成贴现发放银行指令---发放至银行账户---------------");
    			
	    		//added by mzh_fu 2007/08/04 是否发送指令的校验		
    			BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
				bankInstructionSettingQueryInfo.setTransModule(SETTConstant.TransModule.BANK_PAY);
    			
				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByTransType(bankInstructionSettingQueryInfo);
				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE)
				{
					return null;
				}
    			  
				/**二级户账户模式*/
				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
					//逻辑：付款方：财务公司开户行；收款方：外部银行账户
						AccountInfo account = accounthome.findAccountByID(transInfo.getDiscountAccountID());//开户行账户信息
						BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
		    			BranchInfo branchInfo = branchDAO.findByID(transInfo.getBankID()); 
		    			//开户行没有设置发指令，就不发银行指令
						if(branchInfo.getIsAutoVirementByBank()==Constant.YesOrNo.YES)
						{
			    			bankInstructionInfo.setPayAccountNo("");//付款账户号(付款关联号)
							//bankInstructionInfo.setPayBankAccountNO(account.getAccountNo());//付款银行账户号
			    			bankInstructionInfo.setPayBankAccountNO(branchInfo.getBankAccountCode());
			    			
							bankInstructionInfo.setPayAcctBankCode(""); //付款账户银行编号
							bankInstructionInfo.setPayAccountName(account.getAccountName());//付款账户名称             
							bankInstructionInfo.setPayDepartmentName(account.getAccountName());//付款人单位全称           
							bankInstructionInfo.setPayBranchName(account.getAccountName());//付款人开户行名称         
							bankInstructionInfo.setAmount(transInfo.getDiscountAmount());//金额
							bankInstructionInfo.setReceiveReferenceCode("");//收方的内部关联号
							bankInstructionInfo.setReceiveAccountNo(transInfo.getExtAcctNo());//收款账户号               
							bankInstructionInfo.setReceiveAccountName(transInfo.getExtAcctName());//收款账户名称             
							bankInstructionInfo.setReceiveDepartmentName(transInfo.getExtAcctName());//收款人单位全称           
							bankInstructionInfo.setReceiveBranchName(transInfo.getBankName());//收款人开户行名称        
							bankInstructionInfo.setReceiveBranchAreaNameOfProvince(transInfo.getProvince());//收款人开户行所在地省名称 
							bankInstructionInfo.setReceiveBranchAreaNameOfCity(transInfo.getCity());//收款人开户行所在地城市名称       
							bankInstructionInfo.setTransType(TransType.NORMAL);
							bankInstructionInfo.setComment(transInfo.getAbstract());//备注                     
							bankInstructionInfo.setStrAbstract(transInfo.getAbstract());//摘要                     
							bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));//指令发送人no             
							bankInstructionInfo.setCancellerNo("");//指令撤销人no 
							bankInstructionInfo.setCreateTime( Env.getSystemDateTime());//指令创建时间            
							bankInstructionInfo.setSendTime( Env.getSystemDateTime());//指令发送时间             
							bankInstructionInfo.setCancelTime(new Timestamp(0));//指令撤销时间             
							bankInstructionInfo.setModifyTime( Env.getSystemDateTime());//指令修改时间
							bankInstructionInfo.setStatusID(SETTConstant.BankInstructionStatus.SAVED);//指令状态                 
							bankInstructionInfo.setTransactionNo(transInfo.getTransNo());//指令对应业务地交易号     
							bankInstructionInfo.setIDOfBankSeg1("");//指令在银行地对应标识 
							bankInstructionInfo.setPayAreaNameOfProvince("");//付款人开户行所在地省名称
							bankInstructionInfo.setPayAreaNameOfCity("");//付款人开户行所在地城市名称
							bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());//交易币种
							bankInstructionInfo.setOfficeId(transInfo.getOfficeID());//办事处id
							bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);//汇款速度
							
							//新增中行所需的联行号，机构号，手续费账号
							bankInstructionInfo.setPayChargeAccountNo("");//手续费账号 
							bankInstructionInfo.setPayChargeBankExchangeCode("");//手续费账户联行号
							bankInstructionInfo.setPayChargeBranchCodeOfBank("");//手续费账户机构号				
							bankInstructionInfo.setRecNetStationName(""); //收款方网点名称
							colInstructionParams.add(bankInstructionInfo);
						}

				/**门户账户模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
						AccountInfo account = accounthome.findAccountByID(transInfo.getDiscountAccountID());//开户行账户信息
						BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
		    			BranchInfo branchInfo = branchDAO.findByID(transInfo.getBankID()); 
						
							
						//付款银行没有设置发送指令就不发指令
						if(branchInfo.getIsAutoVirementByBank()==Constant.YesOrNo.YES)
						{
							bankInstructionInfo.setPayAccountNo("");//付款账户号(付款关联号)
							//bankInstructionInfo.setPayBankAccountNO(account.getAccountNo());//付款银行账户号
			    			bankInstructionInfo.setPayBankAccountNO(branchInfo.getBankAccountCode());
			    			
							bankInstructionInfo.setPayAcctBankCode(""); //付款账户银行编号
							bankInstructionInfo.setPayAccountName(account.getAccountName());//付款账户名称             
							bankInstructionInfo.setPayDepartmentName(account.getAccountName());//付款人单位全称           
							bankInstructionInfo.setPayBranchName(account.getAccountName());//付款人开户行名称         
							bankInstructionInfo.setAmount(transInfo.getDiscountAmount());//金额
							bankInstructionInfo.setReceiveReferenceCode("");//收方的内部关联号
							bankInstructionInfo.setReceiveAccountNo(transInfo.getExtAcctNo());//收款账户号               
							bankInstructionInfo.setReceiveAccountName(transInfo.getExtAcctName());//收款账户名称             
							bankInstructionInfo.setReceiveDepartmentName(transInfo.getExtAcctName());//收款人单位全称           
							bankInstructionInfo.setReceiveBranchName(transInfo.getBankName());//收款人开户行名称        
							bankInstructionInfo.setReceiveBranchAreaNameOfProvince(transInfo.getProvince());//收款人开户行所在地省名称 
							bankInstructionInfo.setReceiveBranchAreaNameOfCity(transInfo.getCity());//收款人开户行所在地城市名称       
							bankInstructionInfo.setTransType(TransType.NORMAL);
							bankInstructionInfo.setComment(transInfo.getAbstract());//备注                     
							bankInstructionInfo.setStrAbstract(transInfo.getAbstract());//摘要                     
							bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));//指令发送人no             
							bankInstructionInfo.setCancellerNo("");//指令撤销人no 
							bankInstructionInfo.setCreateTime( Env.getSystemDateTime());//指令创建时间            
							bankInstructionInfo.setSendTime( Env.getSystemDateTime());//指令发送时间             
							bankInstructionInfo.setCancelTime(new Timestamp(0));//指令撤销时间             
							bankInstructionInfo.setModifyTime( Env.getSystemDateTime());//指令修改时间
							bankInstructionInfo.setStatusID(SETTConstant.BankInstructionStatus.SAVED);//指令状态                 
							bankInstructionInfo.setTransactionNo(transInfo.getTransNo());//指令对应业务地交易号     
							bankInstructionInfo.setIDOfBankSeg1("");//指令在银行地对应标识 
							bankInstructionInfo.setPayAreaNameOfProvince("");//付款人开户行所在地省名称
							bankInstructionInfo.setPayAreaNameOfCity("");//付款人开户行所在地城市名称
							bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());//交易币种
							bankInstructionInfo.setOfficeId(transInfo.getOfficeID());//办事处id
							bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);//汇款速度
							
							//新增中行所需的联行号，机构号，手续费账号
							bankInstructionInfo.setPayChargeAccountNo("");//手续费账号 
							bankInstructionInfo.setPayChargeBankExchangeCode("");//手续费账户联行号
							bankInstructionInfo.setPayChargeBranchCodeOfBank("");//手续费账户机构号				
							bankInstructionInfo.setRecNetStationName(""); //收款方网点名称
							colInstructionParams.add(bankInstructionInfo);
						}
						log.info("*********财务公司门户模式，贴现发放*********");
							
						
						
				/**收支两条线模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
					}
    		}
    		if(transInfo.getSignBillAccountID()  > 0 && transInfo.getInterestOfSign() > 0)
			{
    			//added by mzh_fu 2007/03/30 是否生成指令检验
    			/*********************begin*****************************/
    			long lAccouontID=transInfo.getSignBillAccountID();
    			BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
    			bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
    			bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
    			bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());  
    			bankInstructionSettingQueryInfo.setAccountId(lAccouontID);
    			//added by mzh_fu 2007/09/27
    			bankInstructionSettingQueryInfo.setTransModule(SETTConstant.TransModule.INSIDE_PAY);// 内转
    			BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
    			if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
					return null;	
    			/**********************end****************************/
    			/**二级户账户模式*/
				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
					
					//普通付款方式
					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
					//逻辑：付款方：活期账户对应的银行二级账户；收款方：银行二级账户的上级银行账户
    				//出票人账户信息
    				AccountInfo account_Bill = accounthome.findAccountByID(transInfo.getSignBillAccountID());
    				ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
	    			instruction.setSystemId(1);//系统id
	    			instruction.setReferenceCode(accounthome.findAccountByID(transInfo.getDepositAccountID()).getAccountNo());
	    			RespGetGeneralBankAcctInfo  generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
    				instruction.setSystemId(1);//系统id
    				instruction.setReferenceCode(account_Bill.getAccountNo());
    				
    				RespGetGeneralBankAcctInfo  generalbankacctinfo_Bill = BPClientAgent.getGeneralBankAcctInfo(instruction);
    				
    				if(generalbankacctinfo == null || generalbankacctinfo.getAccountInfo()==null||generalbankacctinfo.getStatus()!=1){
    					throw new Exception("未找到活期账户的上级银行账户信息"); 
    				}
				
    				log.debug("-----------开始生成贴现发放银行指令---发放至活期账户--出票人到总户---------------");
    				    				 				
    				BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
        			
        			bankInstructionInfo.setPayAccountNo(account_Bill.getAccountNo());//付款账户号(付款关联号)     
    				bankInstructionInfo.setAmount(transInfo.getInterestOfSign());//金额
    				bankInstructionInfo.setReceiveReferenceCode("");//收方的内部关联号
    				bankInstructionInfo.setReceiveAccountNo(generalbankacctinfo_Bill.getAccountInfo().getAccountNO());//收款账户号               
    				bankInstructionInfo.setReceiveAccountName(generalbankacctinfo_Bill.getAccountInfo().getAccountName());//收款账户名称             
    				bankInstructionInfo.setReceiveDepartmentName(generalbankacctinfo_Bill.getAccountInfo().getAccountName());//收款人单位全称           
    				bankInstructionInfo.setReceiveBranchName(generalbankacctinfo_Bill.getAccountInfo().getBankName());//收款人开户行名称        
    				bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctinfo_Bill.getAccountInfo().getBranchProvince());//收款人开户行所在地省名称 
    				bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctinfo_Bill.getAccountInfo().getBranchCity());//收款人开户行所在地城市名称       
    				bankInstructionInfo.setTransType(TransType.NORMAL);
    				bankInstructionInfo.setComment(transInfo.getAbstract());//备注                     
    				bankInstructionInfo.setStrAbstract(transInfo.getAbstract());//摘要                     
    				bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));//指令发送人no             
    				bankInstructionInfo.setCancellerNo("");//指令撤销人no 
    				bankInstructionInfo.setCreateTime( Env.getSystemDateTime());//指令创建时间            
    				bankInstructionInfo.setSendTime( Env.getSystemDateTime());//指令发送时间             
    				bankInstructionInfo.setCancelTime(new Timestamp(0));//指令撤销时间             
    				bankInstructionInfo.setModifyTime( Env.getSystemDateTime());//指令修改时间
    				bankInstructionInfo.setStatusID(SETTConstant.BankInstructionStatus.SAVED);//指令状态                 
    				bankInstructionInfo.setTransactionNo(transInfo.getTransNo());//指令对应业务地交易号     
    				bankInstructionInfo.setIDOfBankSeg1("");//指令在银行地对应标识 
    				bankInstructionInfo.setPayAreaNameOfProvince("");//付款人开户行所在地省名称
    				bankInstructionInfo.setPayAreaNameOfCity("");//付款人开户行所在地城市名称
    				bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());//交易币种
    				bankInstructionInfo.setOfficeId(transInfo.getOfficeID());//办事处id
    				bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);//汇款速度
    				
    				//新增中行所需的联行号，机构号，手续费账号
    				bankInstructionInfo.setPayChargeAccountNo("");//手续费账号 
    				bankInstructionInfo.setPayChargeBankExchangeCode("");//手续费账户联行号
    				bankInstructionInfo.setPayChargeBranchCodeOfBank("");//手续费账户机构号				
    				bankInstructionInfo.setRecNetStationName(""); //收款方网点名称
    				colInstructionParams.add(bankInstructionInfo);
    				//代理汇兑方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
						
					//先拨后支方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
						
						
					}
					
				/**门户账户模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
						
				/**收支两条线模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
			}
		  }

			
		} catch (Exception e) {			
			log.info("根据交易对象形成贴现发放银行指令时出错，无法形成指令！");
			e.printStackTrace();
			throw new IException(e.getMessage(),e);			
		}
		return colInstructionParams;
    }
    
    /**
	 * 自营贷款发放、委托贷款发放等形成指令
	 * 
	 * @param Object
	 * @throws IException
	 */
    private Collection createGrantLoanIntruction(CreateInstructionParam param) throws IException {
    	Collection colInstructionParams = new ArrayList();
    	try {
			
    		TransGrantLoanInfo info = new TransGrantLoanInfo();
    		info = (TransGrantLoanInfo)param.getObjInfo();
    		
    		AccountHome home =
				(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
			Account accounthome = (Account) home.create();
			
			RespGetGeneralBankAcctInfo  generalbankacctinfo = new RespGetGeneralBankAcctInfo();
			
			Sett_BranchDAO branchDAO = new Sett_BranchDAO();			

    		if(info.getDepositAccountID() > 0 )//放款到活期账户
    		{
    			//added by mzh_fu 2007/03/30 是否生成指令检验
    			/*********************begin*****************************/
    			long lAccouontID=info.getDepositAccountID();

				BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingQueryInfo.setTransType(info.getTransactionTypeID());
				bankInstructionSettingQueryInfo.setTransModule(SETTConstant.TransModule.INSIDE_PAY);
				bankInstructionSettingQueryInfo.setAccountId(lAccouontID);
				
				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
				
				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
					return null;			
				/**二级户账户模式*/
				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
					
					//普通付款方式
					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
					{
	    				//逻辑：付款方：银行二级账户的上级银行账户；收款方：活期账户对应的银行二级账户
		    			log.debug("------开始自营贷款发放指令生成--------");
		    			ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
		    			instruction.setSystemId(1);
		    			instruction.setReferenceCode(accounthome.findAccountByID(info.getDepositAccountID()).getAccountNo());
		    			generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(instruction);//获得活期账户的上级银行账号单位
		    			if(generalbankacctinfo==null || generalbankacctinfo.getAccountInfo()==null)
		    			{
		    				throw new Exception("未找到收款方对应的上级账户信息");
		    			}
		    			long id_receive = info.getDepositAccountID();	   //收款账户号
		
		    			BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
		    			
		    			bankInstructionInfo.setPayAccountNo("");//付款账户号(付款关联号)
						bankInstructionInfo.setPayBankAccountNO(generalbankacctinfo.getAccountInfo().getAccountNO());//付款银行账户号
						bankInstructionInfo.setPayAcctBankCode(generalbankacctinfo.getAccountInfo().getBankCode()); //付款账户银行编号
						bankInstructionInfo.setPayAccountName(generalbankacctinfo.getAccountInfo().getAccountName());//付款账户名称             
						bankInstructionInfo.setPayDepartmentName(generalbankacctinfo.getAccountInfo().getAccountName());//付款人单位全称           
						bankInstructionInfo.setPayBranchName("");//付款人开户行名称         
						bankInstructionInfo.setAmount(info.getAmount());//金额
						bankInstructionInfo.setReceiveReferenceCode(accounthome.findAccountByID(id_receive).getAccountNo());//收方的内部关联号
						bankInstructionInfo.setReceiveAccountNo("");//收款账户号               
						bankInstructionInfo.setReceiveAccountName(accounthome.findAccountByID(id_receive).getAccountName());//收款账户名称             
						bankInstructionInfo.setReceiveDepartmentName(accounthome.findAccountByID(id_receive).getAccountName());//收款人单位全称           
						bankInstructionInfo.setReceiveBranchName(accounthome.findAccountByID(id_receive).getAccountName());//收款人开户行名称        
						bankInstructionInfo.setReceiveBranchAreaNameOfProvince(info.getProvince());//收款人开户行所在地省名称 
						bankInstructionInfo.setReceiveBranchAreaNameOfCity(info.getCity());//收款人开户行所在地城市名称       
						bankInstructionInfo.setComment(info.getAbstract());//备注                     
						bankInstructionInfo.setStrAbstract(info.getAbstract());//摘要                     
						bankInstructionInfo.setSenderNo(String.valueOf(info.getInputUserID()));//指令发送人no             
						bankInstructionInfo.setCancellerNo("");//指令撤销人no 
						bankInstructionInfo.setCreateTime( Env.getSystemDateTime());//指令创建时间            
						bankInstructionInfo.setSendTime( Env.getSystemDateTime());//指令发送时间             
						bankInstructionInfo.setCancelTime(new Timestamp(0));//指令撤销时间             
						bankInstructionInfo.setModifyTime( Env.getSystemDateTime());//指令修改时间
						bankInstructionInfo.setStatusID(SETTConstant.BankInstructionStatus.SAVED);//指令状态                 
						bankInstructionInfo.setTransactionNo(info.getTransNo());//指令对应业务地交易号     
						bankInstructionInfo.setTransType(TransType.NORMAL);//指令对应业务地类型       
						bankInstructionInfo.setReceiveBank(info.getTransactionTypeID());//指令接收银行             
						bankInstructionInfo.setIDOfBankSeg1("");//指令在银行地对应标识 
						bankInstructionInfo.setPayAreaNameOfProvince("");//付款人开户行所在地省名称
						bankInstructionInfo.setPayAreaNameOfCity("");//付款人开户行所在地城市名称
						bankInstructionInfo.setCurrencyID(info.getCurrencyID());//交易币种
						bankInstructionInfo.setOfficeId(info.getOfficeID());
						//bankInstructionInfo.setIsSameBank("");//同行标志（true或false）
						bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);//汇款速度
						//bankInstructionInfo.setChargesBorneType();//费用承担方
						
						//新增中行所需的联行号，机构号，手续费账号
						bankInstructionInfo.setPayChargeAccountNo("");//手续费账号 
						bankInstructionInfo.setPayChargeBankExchangeCode("");//手续费账户联行号
						bankInstructionInfo.setPayChargeBranchCodeOfBank("");//手续费账户机构号				
						bankInstructionInfo.setRecNetStationName(""); //收款方网点名称
						colInstructionParams.add(bankInstructionInfo);
						log.debug("------自营贷款发放或委托贷款发放等指令生成结束--------"); 
	    			
						//	代理汇兑方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
						
						
					//先拨后支方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
						
						
					}
					
				/**门户账户模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
						
				/**收支两条线模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
					}
    		}
    		else
    		{    			
    			log.debug("------开始自营贷款发放或委托贷款发放---发放在银行账户等指令生成--------");
    			//收款方为外部银行
	    		//added by mzh_fu 2007/07/25 是否发送指令的校验		
    			//转账方式:对外;业务类型:info.getTransactionTypeID()
    			BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
    			bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
    			bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
    			bankInstructionSettingQueryInfo.setTransType(info.getTransactionTypeID());
    			bankInstructionSettingQueryInfo.setTransModule(SETTConstant.TransModule.BANK_PAY);
				BankInstructionSettingInfo  _bankInstructionSettingInfo = getSettingInfoByTransType(bankInstructionSettingQueryInfo);
				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) { 
					return null;
				}
				/**二级户账户模式*/
				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
					
		    			//逻辑：付款方：财务公司开户行；收款方：外部银行账户
		    			BranchInfo branchInfo = branchDAO.findByID(info.getBankID()); 
		    			BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
		    			//开户行没有设置发指令，就不发银行指令
						if(branchInfo.getIsAutoVirementByBank()==Constant.YesOrNo.YES)
						{
			    			bankInstructionInfo.setPayAccountNo("");//付款账户号(付款关联号)
							bankInstructionInfo.setPayBankAccountNO(branchInfo.getBankAccountCode());//付款银行账户号
							bankInstructionInfo.setPayAcctBankCode(""); //付款账户银行编号
							bankInstructionInfo.setPayAccountName(branchInfo.getBankServiceName());//付款账户名称             
							bankInstructionInfo.setPayDepartmentName(branchInfo.getBankServiceName());//付款人单位全称           
							bankInstructionInfo.setPayBranchName("");//付款人开户行名称         
							bankInstructionInfo.setAmount(info.getAmount());//金额
							bankInstructionInfo.setReceiveReferenceCode("");//收方的内部关联号
							bankInstructionInfo.setReceiveAccountNo(info.getExtAcctNo());//收款账户号               
							bankInstructionInfo.setReceiveAccountName(info.getExtAcctName());//收款账户名称             
							bankInstructionInfo.setReceiveDepartmentName(branchInfo.getEnterpriseName());//收款人单位全称           
							bankInstructionInfo.setReceiveBranchName(branchInfo.getBranchName());//收款人开户行名称        
							bankInstructionInfo.setReceiveBranchAreaNameOfProvince(info.getProvince());//收款人开户行所在地省名称 
							bankInstructionInfo.setReceiveBranchAreaNameOfCity(info.getCity());//收款人开户行所在地城市名称       
							bankInstructionInfo.setComment(info.getAbstract());//备注                     
							bankInstructionInfo.setStrAbstract(info.getAbstract());//摘要                     
							bankInstructionInfo.setSenderNo(String.valueOf(info.getInputUserID()));//指令发送人no             
							bankInstructionInfo.setCancellerNo("");//指令撤销人no 
							bankInstructionInfo.setCreateTime( Env.getSystemDateTime());//指令创建时间            
							bankInstructionInfo.setSendTime( Env.getSystemDateTime());//指令发送时间             
							bankInstructionInfo.setCancelTime(new Timestamp(0));//指令撤销时间             
							bankInstructionInfo.setModifyTime( Env.getSystemDateTime());//指令修改时间
							bankInstructionInfo.setStatusID(SETTConstant.BankInstructionStatus.SAVED);//指令状态                 
							bankInstructionInfo.setTransactionNo(info.getTransNo());//指令对应业务地交易号     
							bankInstructionInfo.setTransType(TransType.NORMAL);//指令对应业务地类型       
							bankInstructionInfo.setReceiveBank(info.getTransactionTypeID());//指令接收银行             
							bankInstructionInfo.setIDOfBankSeg1("");//指令在银行地对应标识 
							bankInstructionInfo.setPayAreaNameOfProvince("");//付款人开户行所在地省名称
							bankInstructionInfo.setPayAreaNameOfCity("");//付款人开户行所在地城市名称
							bankInstructionInfo.setCurrencyID(info.getCurrencyID());//交易币种
							bankInstructionInfo.setOfficeId(info.getOfficeID());
							//bankInstructionInfo.setIsSameBank("");//同行标志（true或false）
							bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);//汇款速度
							//bankInstructionInfo.setChargesBorneType();//费用承担方
							
							//新增中行所需的联行号，机构号，手续费账号
							bankInstructionInfo.setPayBankExchangeCode(branchInfo.getBankExchangeCode());//付款方联行号 
							bankInstructionInfo.setPayBranchCodeOfBank(branchInfo.getBranchCodeOfBank());//付款方机构号	 
							bankInstructionInfo.setReceiveBankExchangeCode(branchInfo.getBankExchangeCode());//收款方联行号 
							bankInstructionInfo.setReceiveBranchCodeOfBank(branchInfo.getBranchCodeOfBank());//收款方机构号	 
							bankInstructionInfo.setPayChargeAccountNo("");//手续费账号 
							bankInstructionInfo.setPayChargeBankExchangeCode("");//手续费账户联行号
							bankInstructionInfo.setPayChargeBranchCodeOfBank("");//手续费账户机构号				
							bankInstructionInfo.setRecNetStationName(""); //收款方网点名称
							colInstructionParams.add(bankInstructionInfo);
							log.debug("------自营贷款发放或委托贷款发放等指令生成结束--------"); 
						}
					}
    		}
			// add by zcwang 2008-10-06 银团贷款发放 ，代理费 活期账户支取 专用
    		double commissionAmount = 0.00;
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
					}catch (Exception e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
			}
			//从活期账户收取手续费(如果存在手续费)
			if(info.getPayCommisionAccountID() > 0 && commissionAmount > 0)
			{
				long lAccouontID=info.getPayCommisionAccountID();

				BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
				bankInstructionSettingQueryInfo.setAccountId(lAccouontID);

				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
				
				if (_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
				{ 
					return null;
				}
				/**二级户账户模式*/
				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
						{
							//逻辑：付款方: 内部活期账户对应的银行二级户；收款方：银行二级户的上级银行账户
							log.debug("------从活期账户收取手续费 从付手续费账户支出--------");
							BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
							ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
							instruction.setSystemId(1);//系统id
							instruction.setReferenceCode(accounthome.findAccountByID(info.getPayCommisionAccountID()).getAccountNo());
							generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(instruction);//获得付息账户的上级银行账号单位
							if(generalbankacctinfo==null || generalbankacctinfo.getAccountInfo()==null)
			    			{
			    				throw new Exception("未找到活期账户对应的上级账户信息");
			    			}
							//付方信息
							bankInstructionInfo.setPayAccountNo((accounthome.findAccountByID(info.getPayCommisionAccountID())).getAccountNo());                                  
							bankInstructionInfo.setPayBankAccountNO("");//付款银行账户号
							bankInstructionInfo.setPayAcctBankCode("");//付款账户银行编号
							bankInstructionInfo.setPayAccountName((accounthome.findAccountByID(info.getPayCommisionAccountID())).getAccountName());//付款账户名称             
							bankInstructionInfo.setPayDepartmentName("");//付款人单位全称           
							bankInstructionInfo.setPayBranchName("");//付款人开户行名称
							
							//收方信息
							bankInstructionInfo.setReceiveAccountNo(generalbankacctinfo.getAccountInfo().getAccountNO());
							bankInstructionInfo.setReceiveAccountName(generalbankacctinfo.getAccountInfo().getAccountName());
							bankInstructionInfo.setReceiveReferenceCode("");
							bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctinfo.getAccountInfo().getBranchCity());
							bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctinfo.getAccountInfo().getBranchProvince());
							
							bankInstructionInfo.setReceiveBranchName(generalbankacctinfo.getAccountInfo().getBankName());
		
							bankInstructionInfo.setReceiveBankExchangeCode("");
							bankInstructionInfo.setReceiveBranchCodeOfBank("");					
							bankInstructionInfo.setTransactionNo(info.getTransNo());
							bankInstructionInfo.setTransType(TransType.NORMAL);
							//金额，币种 等其他信息
							bankInstructionInfo.setAmount(commissionAmount);				
							bankInstructionInfo.setCurrencyID(info.getCurrencyID());
							bankInstructionInfo.setOfficeId(info.getOfficeID());
							bankInstructionInfo.setAbstract(info.getAbstract());
							bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
							bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
							bankInstructionInfo.setSenderNo(String.valueOf(info.getInputUserID()));
							colInstructionParams.add(bankInstructionInfo);
							//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
							
						}
						
						/**门户账户模式*/
					}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
								
								//普通付款方式
								if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
									log.info("*********财务公司对外付款，暂不考虑*********");
									
								
								//代理汇兑方式	
								}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
									log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
									
									
								//先拨后支方式	
								}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
									log.info("*********先拨后支，暂不考虑*********");
									
								}
								
						/**收支两条线模式*/
					}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
							
								//普通付款方式
								if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
									log.info("*********财务公司对外付款，暂不考虑*********");
									
								
								//代理汇兑方式	
								}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
									log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
									
									
								//先拨后支方式	
								}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
									log.info("*********先拨后支，暂不考虑*********");
									
								}
					}
				}
    		
		} catch (Exception e) {			
			log.info("根据交易对象形成自营贷款发放或委托贷款发放等指令时出错，无法形成指令！");
			e.printStackTrace();
			throw new IException(e.getMessage(),e);			
		}
		return colInstructionParams;
    }
    
    
    /**
	 * 委托贷款收回形成指令
	 * 
	 * @param Object
	 * @throws IException
	 */
    private Collection createReceiveConsignLoanIntruction(CreateInstructionParam param) throws IException {
    	Collection colInstructionParams = new ArrayList();
    	try {
    		
    		TransRepaymentLoanInfo info = new TransRepaymentLoanInfo();
    		info = (TransRepaymentLoanInfo)param.getObjInfo();

    		log.debug("委托贷款收回");
    		log.info("委托代款info信息："+UtilOperation.dataentityToString(info));
    		
    		
    		
			if(info.getAmount() > 0)
			{
				
				//added by mzh_fu 2007/09/19免还时不发送指令
	    		if(info.getDepositAccountID()<0 && info.getBankID()<0)
	    			return null;
	    		AccountHome home =
					(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
				Account accounthome = (Account) home.create();
				
				
				
				log.info("委托代款info信息："+UtilOperation.dataentityToString(info));
				//进行委托收回操作
				log.debug("------开始委托贷款收回指令生成--------");
				//本金与利息分笔处理
				if(info.getCapitalAndInterstDealway()==SETTConstant.CapitalAndInterestDealWay.DIVIDE_DEAL)
				{
					//从借款人活期账户收回本金到委托人活期账户(分笔)
					if(info.getDepositAccountID()>0 && info.getConsignDepositAccountID()>0)
					{	
						BankInstructionSettingInfo bankInstructionSettingQueryInfos = new BankInstructionSettingInfo();
						bankInstructionSettingQueryInfos.setNOfficeId(param.getOfficeID());
						bankInstructionSettingQueryInfos.setNCurrencyId(param.getCurrencyID());
						bankInstructionSettingQueryInfos.setTransType(param.getTransactionTypeID());
						bankInstructionSettingQueryInfos.setAccountId(info.getDepositAccountID());
						BankInstructionSettingInfo bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfos);
						bankInstructionSettingQueryInfos.setAccountId(info.getConsignDepositAccountID());
						BankInstructionSettingInfo bankInstructionSettingInfo1 = getSettingInfoByAccountId(bankInstructionSettingQueryInfos);
						if (bankInstructionSettingInfo.getIsSend() != Constant.TRUE && bankInstructionSettingInfo1.getIsSend() != Constant.TRUE) { 
							return null;
						}
						//当双方都是二级户 只发一条指令
						if(bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL && bankInstructionSettingInfo1.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL)
						{

							
							//普通付款方式
							if(bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY
								&&bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
								//逻辑：付款方：活期账户对应的银行二级户；收款方：活期账户对应的银行二级户

								BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
								AccountInfo accountInfo=new AccountInfo();
								Sett_AccountDAO dao=new Sett_AccountDAO();			
								accountInfo=dao.findByID(info.getDepositAccountID());
								bankInstructionInfo.setPayAccountNo(accountInfo.getAccountNo());
								accountInfo=dao.findByID(info.getConsignDepositAccountID());
								bankInstructionInfo.setReceiveReferenceCode(accountInfo.getAccountNo());
									
								bankInstructionInfo.setTransactionNo(info.getTransNo());
								bankInstructionInfo.setTransType(TransType.NORMAL);
								//金额，币种 等其他信息
								bankInstructionInfo.setAmount(info.getAmount());				
								bankInstructionInfo.setCurrencyID(info.getCurrencyID());
								bankInstructionInfo.setOfficeId(info.getOfficeID());
								bankInstructionInfo.setAbstract(info.getAbstract());
								bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
								bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
								bankInstructionInfo.setSenderNo(String.valueOf(info.getInputUserID()));
								
								colInstructionParams.add(bankInstructionInfo);
							//代理汇兑方式	
							}else if (bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT
									&&bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.AGENT){
								log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
								
								
							//先拨后支方式	
							}else if (bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY
									&&bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
								log.info("*********先拨后支，暂不考虑*********");
								
								
							}
						
						}	
					else
					{
						if(info.getDepositAccountID()>0)
						{
							BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
							bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
							bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
							bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
							bankInstructionSettingQueryInfo.setAccountId(info.getDepositAccountID());

							BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
							
							if (_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
							{ 
								return null;
							}
							/**二级户账户模式*/
							if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
								
								//普通付款方式
								if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
								{
									
									//逻辑：付款方: 内部活期账户对应的银行二级户；收款方：银行二级户对应的上级银行账户
									RespGetGeneralBankAcctInfo  generalbankacctinfo = new RespGetGeneralBankAcctInfo();
									ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
									instruction.setSystemId(1);
									instruction.setReferenceCode(accounthome.findAccountByID(info.getDepositAccountID()).getAccountNo());
									generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(instruction);//获得活期账户的上级银行账号单位
									if(generalbankacctinfo==null || generalbankacctinfo.getAccountInfo()==null)
					    			{
					    				throw new Exception("未找到活期账户对应的上级账户信息");
					    			}
									log.debug("------从借款人活期账户收回本金到委托人活期账户--------");
									BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
									
									//付方信息
									bankInstructionInfo.setPayAccountNo((accounthome.findAccountByID(info.getDepositAccountID())).getAccountNo());
									bankInstructionInfo.setPayBankAccountNO("");//付款银行账户号
									bankInstructionInfo.setPayAcctBankCode("");//付款账户银行编号
									bankInstructionInfo.setPayAccountName((accounthome.findAccountByID(info.getDepositAccountID())).getAccountName());//付款账户名称             
									bankInstructionInfo.setPayDepartmentName((accounthome.findAccountByID(info.getDepositAccountID())).getAccountName());//付款人单位全称           
									bankInstructionInfo.setPayBranchName("");//付款人开户行名称
									
									//收方信息
									bankInstructionInfo.setReceiveAccountNo(generalbankacctinfo.getAccountInfo().getAccountNO());
									bankInstructionInfo.setReceiveAccountName(generalbankacctinfo.getAccountInfo().getAccountName());
									bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctinfo.getAccountInfo().getBranchCity());
									bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctinfo.getAccountInfo().getBranchProvince());
									bankInstructionInfo.setReceiveBranchName(generalbankacctinfo.getAccountInfo().getBankName());
									
									
									bankInstructionInfo.setReceiveBankExchangeCode("");
									bankInstructionInfo.setReceiveBranchCodeOfBank("");					
									bankInstructionInfo.setTransactionNo(info.getTransNo());
									bankInstructionInfo.setTransType(TransType.NORMAL);
									//金额，币种 等其他信息
									bankInstructionInfo.setAmount(info.getAmount());				
									bankInstructionInfo.setCurrencyID(info.getCurrencyID());
									bankInstructionInfo.setOfficeId(info.getOfficeID());
									bankInstructionInfo.setAbstract(info.getAbstract());
									bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
									bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
									bankInstructionInfo.setSenderNo(String.valueOf(info.getInputUserID()));
									
									colInstructionParams.add(bankInstructionInfo);
								//代理汇兑方式	
								}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
									log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
									
									
								//先拨后支方式	
								}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
									log.info("*********先拨后支，暂不考虑*********");
									
									
								}
								
								/**门户账户模式*/
							}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
										
										//普通付款方式
										if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
											log.info("*********财务公司对外付款，暂不考虑*********");
											
										
										//代理汇兑方式	
										}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
											log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
											
											
										//先拨后支方式	
										}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
											log.info("*********先拨后支，暂不考虑*********");
											
										}
										
								/**收支两条线模式*/
							}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
									
										//普通付款方式
										if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
											log.info("*********财务公司对外付款，暂不考虑*********");
											
										
										//代理汇兑方式	
										}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
											log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
											
											
										//先拨后支方式	
										}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
											log.info("*********先拨后支，暂不考虑*********");
											
										}
							}
						}
						if(info.getConsignDepositAccountID()>0)
						{
							BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
							bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
							bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
							bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
							bankInstructionSettingQueryInfo.setAccountId(info.getConsignDepositAccountID());

							BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
							
							if (_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
							{ 
								return null;
							}
							/**二级户账户模式*/
							if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
								
								//普通付款方式
								if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
								{
									
									//逻辑：付款方: 银行二级账户的上级银行账户；收款方：内部活期账户对应的银行二级
									RespGetGeneralBankAcctInfo  generalbankacctinfo = new RespGetGeneralBankAcctInfo();
									ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
									instruction.setSystemId(1);
									instruction.setReferenceCode(accounthome.findAccountByID(info.getConsignDepositAccountID()).getAccountNo());
									generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(instruction);//获得活期账户的上级银行账号单位
									if(generalbankacctinfo==null || generalbankacctinfo.getAccountInfo()==null)
					    			{
					    				throw new Exception("未找到活期账户对应的上级账户信息");
					    			}
									log.debug("------从借款人活期账户收回本金到委托人活期账户--------");
									BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
									
									//付方信息
									bankInstructionInfo.setPayBankAccountNO(generalbankacctinfo.getAccountInfo().getAccountNO());
									
									//收方信息
									bankInstructionInfo.setReceiveReferenceCode((accounthome.findAccountByID(info.getConsignDepositAccountID())).getAccountNo());
									
									bankInstructionInfo.setReceiveBankExchangeCode("");
									bankInstructionInfo.setReceiveBranchCodeOfBank("");					
									bankInstructionInfo.setTransactionNo(info.getTransNo());
									bankInstructionInfo.setTransType(TransType.NORMAL);
									//金额，币种 等其他信息
									bankInstructionInfo.setAmount(info.getAmount());				
									bankInstructionInfo.setCurrencyID(info.getCurrencyID());
									bankInstructionInfo.setOfficeId(info.getOfficeID());
									bankInstructionInfo.setAbstract(info.getAbstract());
									bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
									bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
									bankInstructionInfo.setSenderNo(String.valueOf(info.getInputUserID()));
									
									colInstructionParams.add(bankInstructionInfo);
								//代理汇兑方式	
								}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
									log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
									
									
								//先拨后支方式	
								}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
									log.info("*********先拨后支，暂不考虑*********");
									
									
								}
								
								/**门户账户模式*/
							}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
										
										//普通付款方式
										if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
											log.info("*********财务公司对外付款，暂不考虑*********");
											
										
										//代理汇兑方式	
										}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
											log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
											
											
										//先拨后支方式	
										}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
											log.info("*********先拨后支，暂不考虑*********");
											
										}
										
								/**收支两条线模式*/
							}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
									
										//普通付款方式
										if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
											log.info("*********财务公司对外付款，暂不考虑*********");
											
										
										//代理汇兑方式	
										}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
											log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
											
											
										//先拨后支方式	
										}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
											log.info("*********先拨后支，暂不考虑*********");
											
										}
								}
							}
						
						}
					}
					//从开户行到委托人活期账户
					else if(info.getBankID()>0 && info.getConsignDepositAccountID() > 0)
					{
						//added by mzh_fu 2007/03/30 是否生成指令检验
						/*********************begin*****************************/
						
						BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
						bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
						bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
						bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());

						BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByTransType(bankInstructionSettingQueryInfo);
						bankInstructionSettingQueryInfo.setAccountId(info.getConsignDepositAccountID());
						BankInstructionSettingInfo _bankInstructionSettingInfo1= getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
						if (_bankInstructionSettingInfo.getIsSend() != Constant.TRUE
								||_bankInstructionSettingInfo1.getIsSend() != Constant.TRUE) 
						{ 
							return null;
						}
						/**二级户账户模式*/
						if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL
							&&_bankInstructionSettingInfo1.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
							
							//普通付款方式
							if(_bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
								{
								//逻辑：付款方:财务公司开户行；收款方：银行二级户的上级银行账户
									log.debug("------从财务公司开户行到利息收入账户号--------");
									BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
									
									//付款方: 财务公司开户行
									Sett_BranchDAO  branchDAO = new Sett_BranchDAO();
									BranchInfo branchInfo = branchDAO.findByID(info.getBankID()); 
									bankInstructionInfo.setPayBankAccountNO(branchInfo.getBankAccountCode());//付款银行账户号
									bankInstructionInfo.setPayAccountName(branchInfo.getBankServiceName());//付款账户名称             
									bankInstructionInfo.setPayDepartmentName(branchInfo.getBankServiceName());//付款人单位全称
									//收方信息
									bankInstructionInfo.setReceiveReferenceCode((accounthome.findAccountByID(info.getConsignDepositAccountID())).getAccountNo());	
									
									bankInstructionInfo.setTransType(TransType.NORMAL);
									
			
									//金额，币种 等其他信息
									bankInstructionInfo.setTransactionNo(info.getTransNo());
									bankInstructionInfo.setAmount(info.getAmount() );				
									bankInstructionInfo.setCurrencyID(info.getCurrencyID());
									bankInstructionInfo.setOfficeId(info.getOfficeID());
									bankInstructionInfo.setAbstract(info.getAbstract());
									bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
									bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
									bankInstructionInfo.setSenderNo(String.valueOf(info.getInputUserID()));
									colInstructionParams.add(bankInstructionInfo);
									

									//代理汇兑方式	
								}else if (_bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.AGENT){
									log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
									
									
								//先拨后支方式	
								}else if (_bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
									log.info("*********先拨后支，暂不考虑*********");
									
									
								}
								
								/**门户账户模式*/
							}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
										
										//普通付款方式
										if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
											log.info("*********财务公司对外付款，暂不考虑*********");
											
										
										//代理汇兑方式	
										}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
											log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
											
											
										//先拨后支方式	
										}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
											log.info("*********先拨后支，暂不考虑*********");
											
										}
										
								/**收支两条线模式*/
							}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
									
										//普通付款方式
										if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
											log.info("*********财务公司对外付款，暂不考虑*********");
											
										
										//代理汇兑方式	
										}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
											log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
											
											
										//先拨后支方式	
										}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
											log.info("*********先拨后支，暂不考虑*********");
											
										}
							}
		
					}
					//从付息方活期账户到利息收入账户号
					if(info.getPayInterestAccountID() > 0 && info.getReceiveInterestAccountID() > 0 && (info.getRealInterest()+info.getRealCompoundInterest()+info.getOverDueInterest()) > 0)
					{
						
						if(info.getPayInterestAccountID() > 0)
						{
							BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
							bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
							bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
							bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
							bankInstructionSettingQueryInfo.setAccountId(info.getPayInterestAccountID());
	
							BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
							if (_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
							{ 
								return null;
							}
							/**二级户账户模式*/
							if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
								
								//普通付款方式
								if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
									{
									//逻辑：付款方: 内部活期账户对应的银行二级户；收款方：银行二级户对应的上级银行账户
										log.debug("------从付息方活期账户到委托人活期账户--------");
										
										BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
										bankInstructionInfo.setPayAccountNo((accounthome.findAccountByID(info.getPayInterestAccountID())).getAccountNo());
										
										RespGetGeneralBankAcctInfo  generalbankacctInfo = new RespGetGeneralBankAcctInfo();
										ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
										instruction.setSystemId(1);
										instruction.setReferenceCode(accounthome.findAccountByID(info.getPayInterestAccountID()).getAccountNo());
										generalbankacctInfo = BPClientAgent.getGeneralBankAcctInfo(instruction);//获得活期账户的上级银行账号单位
										if(generalbankacctInfo==null || generalbankacctInfo.getAccountInfo()==null)
						    			{
						    				throw new Exception("未找到付款账户对应的上级账户信息");
						    			}    
										
										//收款方
		        						bankInstructionInfo.setReceiveAccountNo(generalbankacctInfo.getAccountInfo().getAccountNO());//收款账户号
		        						bankInstructionInfo.setReceiveAccountName(generalbankacctInfo.getAccountInfo().getAccountName());//收款账户名称
		        						bankInstructionInfo.setReceiveBranchName(generalbankacctInfo.getAccountInfo().getBankName());//收款人开户行名称
		        						bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctInfo.getAccountInfo().getBranchProvince());//收款人开户行所在地省名称
		        						bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctInfo.getAccountInfo().getBranchCity());//收款人开户行所在地城市名称
											
										
										bankInstructionInfo.setTransType(TransType.NORMAL);
										
				
										//金额，币种 等其他信息
										bankInstructionInfo.setTransactionNo(info.getTransNo());
										bankInstructionInfo.setAmount(info.getRealInterest()+info.getRealCompoundInterest()+info.getOverDueInterest() );				
										bankInstructionInfo.setCurrencyID(info.getCurrencyID());
										bankInstructionInfo.setOfficeId(info.getOfficeID());
										bankInstructionInfo.setAbstract(info.getAbstract());
										bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
										bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
										bankInstructionInfo.setSenderNo(String.valueOf(info.getInputUserID()));
										colInstructionParams.add(bankInstructionInfo);
										
	
										//代理汇兑方式	
									}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
										log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
										
										
									//先拨后支方式	
									}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
										log.info("*********先拨后支，暂不考虑*********");
										
										
									}
									
									/**门户账户模式*/
								}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
											
											//普通付款方式
											if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
												log.info("*********财务公司对外付款，暂不考虑*********");
												
											
											//代理汇兑方式	
											}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
												log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
												
												
											//先拨后支方式	
											}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
												log.info("*********先拨后支，暂不考虑*********");
												
											}
											
									/**收支两条线模式*/
								}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
										
											//普通付款方式
											if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
												log.info("*********财务公司对外付款，暂不考虑*********");
												
											
											//代理汇兑方式	
											}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
												log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
												
												
											//先拨后支方式	
											}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
												log.info("*********先拨后支，暂不考虑*********");
												
											}
								}
							}
							if(info.getReceiveInterestAccountID() > 0)
							{
								BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
								bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
								bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
								bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
		
								bankInstructionSettingQueryInfo.setAccountId(info.getReceiveInterestAccountID());
								BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
								if (_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
								{ 
									return null;
								}
								/**二级户账户模式*/
								if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
									
									//普通付款方式
									if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
										{
										//逻辑：付款方: 银行二级户对应的上级银行账户；收款方：内部活期账户对应的银行二级户
											log.debug("------从付息方活期账户到委托人活期账户--------");
											//付款方
											BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
											RespGetGeneralBankAcctInfo  generalbankacctInfo = new RespGetGeneralBankAcctInfo();
											ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
											instruction.setSystemId(1);
											instruction.setReferenceCode(accounthome.findAccountByID(info.getReceiveInterestAccountID()).getAccountNo());
											generalbankacctInfo = BPClientAgent.getGeneralBankAcctInfo(instruction);//获得活期账户的上级银行账号单位
											if(generalbankacctInfo==null || generalbankacctInfo.getAccountInfo()==null)
							    			{
							    				throw new Exception("未找到收款账户对应的上级账户信息");
							    			}         
											bankInstructionInfo.setPayBankAccountNO(generalbankacctInfo.getAccountInfo().getAccountNO());
											//收方信息
											bankInstructionInfo.setReceiveReferenceCode((accounthome.findAccountByID(info.getReceiveInterestAccountID())).getAccountNo());	
											
											bankInstructionInfo.setTransType(TransType.NORMAL);
											
					
											//金额，币种 等其他信息
											bankInstructionInfo.setTransactionNo(info.getTransNo());
											bankInstructionInfo.setAmount(info.getRealInterest()+info.getRealCompoundInterest()+info.getOverDueInterest()-info.getRealInterestTax() );				
											bankInstructionInfo.setCurrencyID(info.getCurrencyID());
											bankInstructionInfo.setOfficeId(info.getOfficeID());
											bankInstructionInfo.setAbstract(info.getAbstract());
											bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
											bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
											bankInstructionInfo.setSenderNo(String.valueOf(info.getInputUserID()));
											colInstructionParams.add(bankInstructionInfo);
											
		
											//代理汇兑方式	
										}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
											log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
											
											
										//先拨后支方式	
										}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
											log.info("*********先拨后支，暂不考虑*********");
											
											
										}
										
										/**门户账户模式*/
									}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
												
												//普通付款方式
												if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
													log.info("*********财务公司对外付款，暂不考虑*********");
													
												
												//代理汇兑方式	
												}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
													log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
													
													
												//先拨后支方式	
												}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
													log.info("*********先拨后支，暂不考虑*********");
													
												}
												
										/**收支两条线模式*/
									}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
											
												//普通付款方式
												if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
													log.info("*********财务公司对外付款，暂不考虑*********");
													
												
												//代理汇兑方式	
												}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
													log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
													
													
												//先拨后支方式	
												}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
													log.info("*********先拨后支，暂不考虑*********");
													
												}
										}						
								}
					}
					//从付息开户行到利息收入账户号
					else if(info.getInterestBankID()>0 && info.getReceiveInterestAccountID() > 0 && (info.getRealInterest()+info.getRealCompoundInterest()+info.getOverDueInterest()) > 0)
					{
						//added by mzh_fu 2007/03/30 是否生成指令检验
						/*********************begin*****************************/
						
						BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
						bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
						bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
						bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());

						BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByTransType(bankInstructionSettingQueryInfo);
						bankInstructionSettingQueryInfo.setAccountId(info.getReceiveInterestAccountID());
						BankInstructionSettingInfo _bankInstructionSettingInfo1= getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
						if (_bankInstructionSettingInfo.getIsSend() != Constant.TRUE
								||_bankInstructionSettingInfo1.getIsSend() != Constant.TRUE) 
						{ 
							return null;
						}
						/**二级户账户模式*/
						if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL
							&&_bankInstructionSettingInfo1.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
							
							//普通付款方式
							if(_bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
								{
								//逻辑：付款方:财务公司开户行；收款方：银行二级户的上级银行账户
									log.debug("------从财务公司开户行到利息收入账户号--------");
									BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
									
									//付款方: 财务公司开户行
									Sett_BranchDAO  branchDAO = new Sett_BranchDAO();
									BranchInfo branchInfo = branchDAO.findByID(info.getInterestBankID()); 
									bankInstructionInfo.setPayBankAccountNO(branchInfo.getBankAccountCode());//付款银行账户号
									bankInstructionInfo.setPayAccountName(branchInfo.getBankServiceName());//付款账户名称             
									bankInstructionInfo.setPayDepartmentName(branchInfo.getBankServiceName());//付款人单位全称
									//收方信息
									bankInstructionInfo.setReceiveReferenceCode((accounthome.findAccountByID(info.getReceiveInterestAccountID())).getAccountNo());	
									
									bankInstructionInfo.setTransType(TransType.NORMAL);
									
			
									//金额，币种 等其他信息
									bankInstructionInfo.setTransactionNo(info.getTransNo());
									bankInstructionInfo.setAmount(info.getRealInterest()+info.getRealCompoundInterest()+info.getOverDueInterest()-info.getRealInterestTax() );				
									bankInstructionInfo.setCurrencyID(info.getCurrencyID());
									bankInstructionInfo.setOfficeId(info.getOfficeID());
									bankInstructionInfo.setAbstract(info.getAbstract());
									bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
									bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
									bankInstructionInfo.setSenderNo(String.valueOf(info.getInputUserID()));
									colInstructionParams.add(bankInstructionInfo);
									

									//代理汇兑方式	
								}else if (_bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.AGENT){
									log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
									
									
								//先拨后支方式	
								}else if (_bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
									log.info("*********先拨后支，暂不考虑*********");
									
									
								}
								
								/**门户账户模式*/
							}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
										
										//普通付款方式
										if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
											log.info("*********财务公司对外付款，暂不考虑*********");
											
										
										//代理汇兑方式	
										}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
											log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
											
											
										//先拨后支方式	
										}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
											log.info("*********先拨后支，暂不考虑*********");
											
										}
										
								/**收支两条线模式*/
							}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
									
										//普通付款方式
										if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
											log.info("*********财务公司对外付款，暂不考虑*********");
											
										
										//代理汇兑方式	
										}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
											log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
											
											
										//先拨后支方式	
										}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
											log.info("*********先拨后支，暂不考虑*********");
											
										}
							}
		
					}

				}
				//本金和利息汇总处理
				else if (info.getCapitalAndInterstDealway()==SETTConstant.CapitalAndInterestDealWay.SUM_DEAL)
				{
					//从借款人活期账户收回到委托人活期账户
					if(info.getDepositAccountID()>0 && info.getConsignDepositAccountID()>0)
					{
						
						if(info.getDepositAccountID()>0)
						{
							BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
							bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
							bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
							bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
							bankInstructionSettingQueryInfo.setAccountId(info.getDepositAccountID());

							BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
							
							if (_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
							{ 
								return null;
							}
							/**二级户账户模式*/
							if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
								
								//普通付款方式
								if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
								{
									
									//逻辑：付款方: 内部活期账户对应的银行二级户；收款方：银行二级户对应的上级银行账户
									RespGetGeneralBankAcctInfo  generalbankacctinfo = new RespGetGeneralBankAcctInfo();
									ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
									instruction.setSystemId(1);
									instruction.setReferenceCode(accounthome.findAccountByID(info.getDepositAccountID()).getAccountNo());
									generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(instruction);//获得活期账户的上级银行账号单位
									if(generalbankacctinfo==null || generalbankacctinfo.getAccountInfo()==null)
					    			{
					    				throw new Exception("未找到活期账户对应的上级账户信息");
					    			}
									log.debug("------从借款人活期账户收回本金到委托人活期账户--------");
									BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
									
									//付方信息
									bankInstructionInfo.setPayAccountNo((accounthome.findAccountByID(info.getDepositAccountID())).getAccountNo());
									bankInstructionInfo.setPayBankAccountNO("");//付款银行账户号
									bankInstructionInfo.setPayAcctBankCode("");//付款账户银行编号
									bankInstructionInfo.setPayAccountName((accounthome.findAccountByID(info.getDepositAccountID())).getAccountName());//付款账户名称             
									bankInstructionInfo.setPayDepartmentName((accounthome.findAccountByID(info.getDepositAccountID())).getAccountName());//付款人单位全称           
									bankInstructionInfo.setPayBranchName("");//付款人开户行名称
									
									//收方信息
									bankInstructionInfo.setReceiveAccountNo(generalbankacctinfo.getAccountInfo().getAccountNO());
									bankInstructionInfo.setReceiveAccountName(generalbankacctinfo.getAccountInfo().getAccountName());
									bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctinfo.getAccountInfo().getBranchCity());
									bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctinfo.getAccountInfo().getBranchProvince());
									bankInstructionInfo.setReceiveBranchName(generalbankacctinfo.getAccountInfo().getBankName());
									
									
									bankInstructionInfo.setReceiveBankExchangeCode("");
									bankInstructionInfo.setReceiveBranchCodeOfBank("");					
									bankInstructionInfo.setTransactionNo(info.getTransNo());
									bankInstructionInfo.setTransType(TransType.NORMAL);
									//金额，币种 等其他信息
									bankInstructionInfo.setAmount(info.getAmount()+info.getRealInterest()+info.getRealCompoundInterest()+info.getOverDueInterest());				
									bankInstructionInfo.setCurrencyID(info.getCurrencyID());
									bankInstructionInfo.setOfficeId(info.getOfficeID());
									bankInstructionInfo.setAbstract(info.getAbstract());
									bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
									bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
									bankInstructionInfo.setSenderNo(String.valueOf(info.getInputUserID()));
									
									colInstructionParams.add(bankInstructionInfo);
								//代理汇兑方式	
								}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
									log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
									
									
								//先拨后支方式	
								}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
									log.info("*********先拨后支，暂不考虑*********");
									
									
								}
								
								/**门户账户模式*/
							}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
										
										//普通付款方式
										if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
											log.info("*********财务公司对外付款，暂不考虑*********");
											
										
										//代理汇兑方式	
										}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
											log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
											
											
										//先拨后支方式	
										}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
											log.info("*********先拨后支，暂不考虑*********");
											
										}
										
								/**收支两条线模式*/
							}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
									
										//普通付款方式
										if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
											log.info("*********财务公司对外付款，暂不考虑*********");
											
										
										//代理汇兑方式	
										}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
											log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
											
											
										//先拨后支方式	
										}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
											log.info("*********先拨后支，暂不考虑*********");
											
										}
							}
						}
						if(info.getConsignDepositAccountID()>0)
						{
							BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
							bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
							bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
							bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
							bankInstructionSettingQueryInfo.setAccountId(info.getConsignDepositAccountID());

							BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
							
							if (_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
							{ 
								return null;
							}
							/**二级户账户模式*/
							if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
								
								//普通付款方式
								if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
								{
									
									//逻辑：付款方: 银行二级账户得上级银行账户；收款方：内部活期账户对应的银行二级
									RespGetGeneralBankAcctInfo  generalbankacctinfo = new RespGetGeneralBankAcctInfo();
									ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
									instruction.setSystemId(1);
									instruction.setReferenceCode(accounthome.findAccountByID(info.getConsignDepositAccountID()).getAccountNo());
									generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(instruction);//获得活期账户的上级银行账号单位
									if(generalbankacctinfo==null || generalbankacctinfo.getAccountInfo()==null)
					    			{
					    				throw new Exception("未找到活期账户对应的上级账户信息");
					    			}
									log.debug("------从借款人活期账户收回本金到委托人活期账户--------");
									BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
									
									//付方信息
									bankInstructionInfo.setPayBankAccountNO(generalbankacctinfo.getAccountInfo().getAccountNO());
									
									//收方信息
									bankInstructionInfo.setReceiveReferenceCode((accounthome.findAccountByID(info.getConsignDepositAccountID())).getAccountNo());
									
									bankInstructionInfo.setReceiveBankExchangeCode("");
									bankInstructionInfo.setReceiveBranchCodeOfBank("");					
									bankInstructionInfo.setTransactionNo(info.getTransNo());
									bankInstructionInfo.setTransType(TransType.NORMAL);
									//金额，币种 等其他信息
									bankInstructionInfo.setAmount(info.getAmount()+info.getRealInterest()+info.getRealCompoundInterest()+info.getOverDueInterest()-info.getRealInterestTax());				
									bankInstructionInfo.setCurrencyID(info.getCurrencyID());
									bankInstructionInfo.setOfficeId(info.getOfficeID());
									bankInstructionInfo.setAbstract(info.getAbstract());
									bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
									bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
									bankInstructionInfo.setSenderNo(String.valueOf(info.getInputUserID()));
									
									colInstructionParams.add(bankInstructionInfo);
								//代理汇兑方式	
								}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
									log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
									
									
								//先拨后支方式	
								}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
									log.info("*********先拨后支，暂不考虑*********");
									
									
								}
								
								/**门户账户模式*/
							}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
										
										//普通付款方式
										if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
											log.info("*********财务公司对外付款，暂不考虑*********");
											
										
										//代理汇兑方式	
										}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
											log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
											
											
										//先拨后支方式	
										}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
											log.info("*********先拨后支，暂不考虑*********");
											
										}
										
								/**收支两条线模式*/
							}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
									
										//普通付款方式
										if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
											log.info("*********财务公司对外付款，暂不考虑*********");
											
										
										//代理汇兑方式	
										}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
											log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
											
											
										//先拨后支方式	
										}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
											log.info("*********先拨后支，暂不考虑*********");
											
										}
								}
							}
						
					}
					//从开户行到委托人活期账户
					else if(info.getBankID()>0 && info.getConsignDepositAccountID() > 0)
					{
						//added by mzh_fu 2007/03/30 是否生成指令检验
						/*********************begin*****************************/
						
						BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
						bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
						bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
						bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());

						BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByTransType(bankInstructionSettingQueryInfo);
						bankInstructionSettingQueryInfo.setAccountId(info.getConsignDepositAccountID());
						BankInstructionSettingInfo _bankInstructionSettingInfo1= getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
						if (_bankInstructionSettingInfo.getIsSend() != Constant.TRUE
								||_bankInstructionSettingInfo1.getIsSend() != Constant.TRUE) 
						{ 
							return null;
						}
						/**二级户账户模式*/
						if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL
							&&_bankInstructionSettingInfo1.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
							
							//普通付款方式
							if(_bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
								{
								//逻辑：付款方:财务公司开户行；收款方：内部活期账户的银行二级户
									log.debug("------从财务公司开户行到利息收入账户号--------");
									BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
									
									//付款方: 财务公司开户行
									Sett_BranchDAO  branchDAO = new Sett_BranchDAO();
									BranchInfo branchInfo = branchDAO.findByID(info.getBankID()); 
									bankInstructionInfo.setPayBankAccountNO(branchInfo.getBankAccountCode());//付款银行账户号
									bankInstructionInfo.setPayAccountName(branchInfo.getBankServiceName());//付款账户名称             
									bankInstructionInfo.setPayDepartmentName(branchInfo.getBankServiceName());//付款人单位全称
									//收方信息
									bankInstructionInfo.setReceiveReferenceCode((accounthome.findAccountByID(info.getConsignDepositAccountID())).getAccountNo());	
									
									bankInstructionInfo.setTransType(TransType.NORMAL);
									
			
									//金额，币种 等其他信息
									bankInstructionInfo.setTransactionNo(info.getTransNo());
									bankInstructionInfo.setAmount(info.getAmount()+info.getRealInterest()+info.getRealCompoundInterest()+info.getOverDueInterest()-info.getRealInterestTax());			
									bankInstructionInfo.setCurrencyID(info.getCurrencyID());
									bankInstructionInfo.setOfficeId(info.getOfficeID());
									bankInstructionInfo.setAbstract(info.getAbstract());
									bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
									bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
									bankInstructionInfo.setSenderNo(String.valueOf(info.getInputUserID()));
									colInstructionParams.add(bankInstructionInfo);
									

									//代理汇兑方式	
								}else if (_bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.AGENT){
									log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
									
									
								//先拨后支方式	
								}else if (_bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
									log.info("*********先拨后支，暂不考虑*********");
									
									
								}
								
								/**门户账户模式*/
							}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
										
										//普通付款方式
										if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
											log.info("*********财务公司对外付款，暂不考虑*********");
											
										
										//代理汇兑方式	
										}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
											log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
											
											
										//先拨后支方式	
										}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
											log.info("*********先拨后支，暂不考虑*********");
											
										}
										
								/**收支两条线模式*/
							}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
									
										//普通付款方式
										if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
											log.info("*********财务公司对外付款，暂不考虑*********");
											
										
										//代理汇兑方式	
										}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
											log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
											
											
										//先拨后支方式	
										}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
											log.info("*********先拨后支，暂不考虑*********");
											
										}
							}
		
					}

				}
				//从活期账户收取手续费(如果存在手续费)
				if(info.getCommissionAccountID() > 0 && info.getRealCommission() > 0)
				{
					long lAccouontID=info.getCommissionAccountID();

					BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
					bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
					bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
					bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
					bankInstructionSettingQueryInfo.setAccountId(lAccouontID);

					BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
					
					if (_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
					{ 
						return null;
					}
				/**二级户账户模式*/
				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
					
							//普通付款方式
							if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
							{
								//逻辑：付款方: 内部活期账户对应的银行二级户；收款方：银行二级户的上级银行账户
								log.debug("------从活期账户收取手续费 从付手续费账户支出--------");
								BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
								RespGetGeneralBankAcctInfo  generalbankacctinfo = new RespGetGeneralBankAcctInfo();
								ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
								instruction.setSystemId(1);//系统id
								instruction.setReferenceCode(accounthome.findAccountByID(info.getCommissionAccountID()).getAccountNo());
								generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(instruction);//获得付息账户的上级银行账号单位
								if(generalbankacctinfo==null || generalbankacctinfo.getAccountInfo()==null)
				    			{
				    				throw new Exception("未找到活期账户对应的上级账户信息");
				    			}
								//付方信息
								bankInstructionInfo.setPayAccountNo((accounthome.findAccountByID(info.getCommissionAccountID())).getAccountNo());                                  
								bankInstructionInfo.setPayBankAccountNO("");//付款银行账户号
								bankInstructionInfo.setPayAcctBankCode("");//付款账户银行编号
								bankInstructionInfo.setPayAccountName((accounthome.findAccountByID(info.getCommissionAccountID())).getAccountName());//付款账户名称             
								bankInstructionInfo.setPayDepartmentName("");//付款人单位全称           
								bankInstructionInfo.setPayBranchName("");//付款人开户行名称
								
								//收方信息
								bankInstructionInfo.setReceiveAccountNo(generalbankacctinfo.getAccountInfo().getAccountNO());
								bankInstructionInfo.setReceiveAccountName(generalbankacctinfo.getAccountInfo().getAccountName());
								bankInstructionInfo.setReceiveReferenceCode("");
								bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctinfo.getAccountInfo().getBranchCity());
								bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctinfo.getAccountInfo().getBranchProvince());
								
								bankInstructionInfo.setReceiveBranchName(generalbankacctinfo.getAccountInfo().getBankName());
			
								bankInstructionInfo.setReceiveBankExchangeCode("");
								bankInstructionInfo.setReceiveBranchCodeOfBank("");					
								bankInstructionInfo.setTransactionNo(info.getTransNo());
								bankInstructionInfo.setTransType(TransType.NORMAL);
								//金额，币种 等其他信息
								bankInstructionInfo.setAmount(info.getRealCommission());				
								bankInstructionInfo.setCurrencyID(info.getCurrencyID());
								bankInstructionInfo.setOfficeId(info.getOfficeID());
								bankInstructionInfo.setAbstract(info.getAbstract());
								bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
								bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
								bankInstructionInfo.setSenderNo(String.valueOf(info.getInputUserID()));
								colInstructionParams.add(bankInstructionInfo);
								//代理汇兑方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
								log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
								
								
							//先拨后支方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
								log.info("*********先拨后支，暂不考虑*********");
								
								
							}
							
							/**门户账户模式*/
						}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
									
									//普通付款方式
									if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
										log.info("*********财务公司对外付款，暂不考虑*********");
										
									
									//代理汇兑方式	
									}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
										log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
										
										
									//先拨后支方式	
									}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
										log.info("*********先拨后支，暂不考虑*********");
										
									}
									
							/**收支两条线模式*/
						}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
								
									//普通付款方式
									if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
										log.info("*********财务公司对外付款，暂不考虑*********");
										
									
									//代理汇兑方式	
									}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
										log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
										
										
									//先拨后支方式	
									}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
										log.info("*********先拨后支，暂不考虑*********");
										
									}
						}
					}
				
	    	}
			//进行委托贷款结息操作
			else if(info.getAmount() == 0)
			{
				log.info("委托代款info信息："+UtilOperation.dataentityToString(info));
	    		AccountHome home =
					(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
				Account accounthome = (Account) home.create();

				log.info("委托代款info信息："+UtilOperation.dataentityToString(info));
				//进行委托收回操作
				//从付息方活期账户到利息收入账户号
				if(info.getPayInterestAccountID() > 0 && info.getReceiveInterestAccountID() > 0 && (info.getRealInterest()+info.getRealCompoundInterest()+info.getOverDueInterest()) > 0)
				{
				
					if(info.getPayInterestAccountID() > 0)
					{
						BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
						bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
						bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
						bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
						bankInstructionSettingQueryInfo.setAccountId(info.getPayInterestAccountID());

						BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
						if (_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
						{ 
							return null;
						}
						/**二级户账户模式*/
						if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
							
							//普通付款方式
							if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
								{
								//逻辑：付款方: 内部活期账户对应的银行二级户；收款方：银行二级户对应的上级银行账户
									log.debug("------从付息方活期账户到委托人活期账户--------");
									
									BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
									bankInstructionInfo.setPayAccountNo((accounthome.findAccountByID(info.getPayInterestAccountID())).getAccountNo());
									
									RespGetGeneralBankAcctInfo  generalbankacctInfo = new RespGetGeneralBankAcctInfo();
									ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
									instruction.setSystemId(1);
									instruction.setReferenceCode(accounthome.findAccountByID(info.getPayInterestAccountID()).getAccountNo());
									generalbankacctInfo = BPClientAgent.getGeneralBankAcctInfo(instruction);//获得活期账户的上级银行账号单位
									if(generalbankacctInfo==null || generalbankacctInfo.getAccountInfo()==null)
					    			{
					    				throw new Exception("未找到付款账户对应的上级账户信息");
					    			}    
									
									//收款方
	        						bankInstructionInfo.setReceiveAccountNo(generalbankacctInfo.getAccountInfo().getAccountNO());//收款账户号
	        						bankInstructionInfo.setReceiveAccountName(generalbankacctInfo.getAccountInfo().getAccountName());//收款账户名称
	        						bankInstructionInfo.setReceiveBranchName(generalbankacctInfo.getAccountInfo().getBankName());//收款人开户行名称
	        						bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctInfo.getAccountInfo().getBranchProvince());//收款人开户行所在地省名称
	        						bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctInfo.getAccountInfo().getBranchCity());//收款人开户行所在地城市名称
										
									
									bankInstructionInfo.setTransType(TransType.NORMAL);
									
			
									//金额，币种 等其他信息
									bankInstructionInfo.setTransactionNo(info.getTransNo());
									bankInstructionInfo.setAmount(info.getRealInterest()+info.getRealCompoundInterest()+info.getOverDueInterest() );				
									bankInstructionInfo.setCurrencyID(info.getCurrencyID());
									bankInstructionInfo.setOfficeId(info.getOfficeID());
									bankInstructionInfo.setAbstract(info.getAbstract());
									bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
									bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
									bankInstructionInfo.setSenderNo(String.valueOf(info.getInputUserID()));
									colInstructionParams.add(bankInstructionInfo);
									

									//代理汇兑方式	
								}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
									log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
									
									
								//先拨后支方式	
								}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
									log.info("*********先拨后支，暂不考虑*********");
									
									
								}
								
								/**门户账户模式*/
							}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
										
										//普通付款方式
										if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
											log.info("*********财务公司对外付款，暂不考虑*********");
											
										
										//代理汇兑方式	
										}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
											log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
											
											
										//先拨后支方式	
										}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
											log.info("*********先拨后支，暂不考虑*********");
											
										}
										
								/**收支两条线模式*/
							}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
									
										//普通付款方式
										if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
											log.info("*********财务公司对外付款，暂不考虑*********");
											
										
										//代理汇兑方式	
										}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
											log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
											
											
										//先拨后支方式	
										}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
											log.info("*********先拨后支，暂不考虑*********");
											
										}
							}
						}
						if(info.getReceiveInterestAccountID() > 0)
						{
							BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
							bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
							bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
							bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
	
							bankInstructionSettingQueryInfo.setAccountId(info.getReceiveInterestAccountID());
							BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
							if (_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
							{ 
								return null;
							}
							/**二级户账户模式*/
							if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
								
								//普通付款方式
								if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
									{
									//逻辑：付款方: 银行二级户对应的上级银行账户；收款方：内部活期账户对应的银行二级户
										log.debug("------从付息方活期账户到委托人活期账户--------");
										//付款方
										BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
										RespGetGeneralBankAcctInfo  generalbankacctInfo = new RespGetGeneralBankAcctInfo();
										ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
										instruction.setSystemId(1);
										instruction.setReferenceCode(accounthome.findAccountByID(info.getReceiveInterestAccountID()).getAccountNo());
										generalbankacctInfo = BPClientAgent.getGeneralBankAcctInfo(instruction);//获得活期账户的上级银行账号单位
										if(generalbankacctInfo==null || generalbankacctInfo.getAccountInfo()==null)
						    			{
						    				throw new Exception("未找到收款账户对应的上级账户信息");
						    			}         
										bankInstructionInfo.setPayBankAccountNO(generalbankacctInfo.getAccountInfo().getAccountNO());
										//收方信息
										bankInstructionInfo.setReceiveReferenceCode((accounthome.findAccountByID(info.getReceiveInterestAccountID())).getAccountNo());	
										
										bankInstructionInfo.setTransType(TransType.NORMAL);
										
				
										//金额，币种 等其他信息
										bankInstructionInfo.setTransactionNo(info.getTransNo());
										bankInstructionInfo.setAmount(info.getRealInterest()+info.getRealCompoundInterest()+info.getOverDueInterest()-info.getRealInterestTax() );				
										bankInstructionInfo.setCurrencyID(info.getCurrencyID());
										bankInstructionInfo.setOfficeId(info.getOfficeID());
										bankInstructionInfo.setAbstract(info.getAbstract());
										bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
										bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
										bankInstructionInfo.setSenderNo(String.valueOf(info.getInputUserID()));
										colInstructionParams.add(bankInstructionInfo);
										
	
										//代理汇兑方式	
									}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
										log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
										
										
									//先拨后支方式	
									}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
										log.info("*********先拨后支，暂不考虑*********");
										
										
									}
									
									/**门户账户模式*/
								}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
											
											//普通付款方式
											if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
												log.info("*********财务公司对外付款，暂不考虑*********");
												
											
											//代理汇兑方式	
											}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
												log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
												
												
											//先拨后支方式	
											}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
												log.info("*********先拨后支，暂不考虑*********");
												
											}
											
									/**收支两条线模式*/
								}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
										
											//普通付款方式
											if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
												log.info("*********财务公司对外付款，暂不考虑*********");
												
											
											//代理汇兑方式	
											}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
												log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
												
												
											//先拨后支方式	
											}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
												log.info("*********先拨后支，暂不考虑*********");
												
											}
									}						
						}
				}
				//从付息开户行到利息收入账户号
				else if(info.getInterestBankID()>0 && info.getReceiveInterestAccountID() > 0 && (info.getRealInterest()+info.getRealCompoundInterest()+info.getOverDueInterest()) > 0)
				{
					//added by mzh_fu 2007/03/30 是否生成指令检验
					/*********************begin*****************************/
					
					BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
					bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
					bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
					bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());

					BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByTransType(bankInstructionSettingQueryInfo);
					bankInstructionSettingQueryInfo.setAccountId(info.getReceiveInterestAccountID());
					BankInstructionSettingInfo _bankInstructionSettingInfo1= getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
					if (_bankInstructionSettingInfo.getIsSend() != Constant.TRUE
							||_bankInstructionSettingInfo1.getIsSend() != Constant.TRUE) 
					{ 
						return null;
					}
					/**二级户账户模式*/
					if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL
						&&_bankInstructionSettingInfo1.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
						
						//普通付款方式
						if( _bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
							{
							//逻辑：付款方:财务公司开户行；收款方：银行二级户的上级银行账户
								log.debug("------从财务公司开户行到利息收入账户号--------");
								BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
								
								//付款方: 财务公司开户行
								Sett_BranchDAO  branchDAO = new Sett_BranchDAO();
								BranchInfo branchInfo = branchDAO.findByID(info.getInterestBankID()); 
								bankInstructionInfo.setPayBankAccountNO(branchInfo.getBankAccountCode());//付款银行账户号
								bankInstructionInfo.setPayAccountName(branchInfo.getBankServiceName());//付款账户名称             
								bankInstructionInfo.setPayDepartmentName(branchInfo.getBankServiceName());//付款人单位全称
								//收方信息
								bankInstructionInfo.setReceiveReferenceCode((accounthome.findAccountByID(info.getReceiveInterestAccountID())).getAccountNo());	
								
								bankInstructionInfo.setTransType(TransType.NORMAL);
								
		
								//金额，币种 等其他信息
								bankInstructionInfo.setTransactionNo(info.getTransNo());
								bankInstructionInfo.setAmount(info.getRealInterest()+info.getRealCompoundInterest()+info.getOverDueInterest()-info.getRealInterestTax() );				
								bankInstructionInfo.setCurrencyID(info.getCurrencyID());
								bankInstructionInfo.setOfficeId(info.getOfficeID());
								bankInstructionInfo.setAbstract(info.getAbstract());
								bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
								bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
								bankInstructionInfo.setSenderNo(String.valueOf(info.getInputUserID()));
								colInstructionParams.add(bankInstructionInfo);
								

								//代理汇兑方式	
							}else if (_bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.AGENT){
								log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
								
								
							//先拨后支方式	
							}else if (_bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
								log.info("*********先拨后支，暂不考虑*********");
								
								
							}
							
							/**门户账户模式*/
						}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
									
									//普通付款方式
									if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
										log.info("*********财务公司对外付款，暂不考虑*********");
										
									
									//代理汇兑方式	
									}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
										log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
										
										
									//先拨后支方式	
									}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
										log.info("*********先拨后支，暂不考虑*********");
										
									}
									
							/**收支两条线模式*/
						}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
								
									//普通付款方式
									if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
										log.info("*********财务公司对外付款，暂不考虑*********");
										
									
									//代理汇兑方式	
									}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
										log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
										
										
									//先拨后支方式	
									}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
										log.info("*********先拨后支，暂不考虑*********");
										
									}
						}
	
				}
				//从活期账户收取手续费(如果存在手续费)
				if(info.getCommissionAccountID() > 0 && info.getRealCommission() > 0)
				{
					long lAccouontID=info.getCommissionAccountID();

					BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
					bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
					bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
					bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
					bankInstructionSettingQueryInfo.setAccountId(lAccouontID);

					BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
					
					if (_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
					{ 
						return null;
					}
				/**二级户账户模式*/
				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
					
							//普通付款方式
							if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
							{
								//逻辑：付款方: 内部活期账户对应的银行二级户；收款方：银行二级户的上级银行账户
								log.debug("------从活期账户收取手续费 从付手续费账户支出--------");
								BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
								RespGetGeneralBankAcctInfo  generalbankacctinfo = new RespGetGeneralBankAcctInfo();
					    		ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();

								instruction.setSystemId(1);//系统id
								instruction.setReferenceCode(accounthome.findAccountByID(info.getCommissionAccountID()).getAccountNo());
								generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(instruction);//获得付息账户的上级银行账号单位
								if(generalbankacctinfo==null || generalbankacctinfo.getAccountInfo()==null)
				    			{
				    				throw new Exception("未找到活期账户对应的上级账户信息");
				    			}
								//付方信息
								bankInstructionInfo.setPayAccountNo((accounthome.findAccountByID(info.getCommissionAccountID())).getAccountNo());                                  
								bankInstructionInfo.setPayBankAccountNO("");//付款银行账户号
								bankInstructionInfo.setPayAcctBankCode("");//付款账户银行编号
								bankInstructionInfo.setPayAccountName((accounthome.findAccountByID(info.getCommissionAccountID())).getAccountName());//付款账户名称             
								bankInstructionInfo.setPayDepartmentName("");//付款人单位全称           
								bankInstructionInfo.setPayBranchName("");//付款人开户行名称
								
								//收方信息
								bankInstructionInfo.setReceiveAccountNo(generalbankacctinfo.getAccountInfo().getAccountNO());
								bankInstructionInfo.setReceiveAccountName(generalbankacctinfo.getAccountInfo().getAccountName());
								bankInstructionInfo.setReceiveReferenceCode("");
								bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctinfo.getAccountInfo().getBranchCity());
								bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctinfo.getAccountInfo().getBranchProvince());
								
								bankInstructionInfo.setReceiveBranchName(generalbankacctinfo.getAccountInfo().getBankName());
			
								bankInstructionInfo.setReceiveBankExchangeCode("");
								bankInstructionInfo.setReceiveBranchCodeOfBank("");					
								bankInstructionInfo.setTransactionNo(info.getTransNo());
								bankInstructionInfo.setTransType(TransType.NORMAL);
								//金额，币种 等其他信息
								bankInstructionInfo.setAmount(info.getRealCommission());				
								bankInstructionInfo.setCurrencyID(info.getCurrencyID());
								bankInstructionInfo.setOfficeId(info.getOfficeID());
								bankInstructionInfo.setAbstract(info.getAbstract());
								bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
								bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
								bankInstructionInfo.setSenderNo(String.valueOf(info.getInputUserID()));
								colInstructionParams.add(bankInstructionInfo);
								//代理汇兑方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
								log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
								
								
							//先拨后支方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
								log.info("*********先拨后支，暂不考虑*********");
								
								
							}
							
							/**门户账户模式*/
						}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
									
									//普通付款方式
									if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
										log.info("*********财务公司对外付款，暂不考虑*********");
										
									
									//代理汇兑方式	
									}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
										log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
										
										
									//先拨后支方式	
									}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
										log.info("*********先拨后支，暂不考虑*********");
										
									}
									
							/**收支两条线模式*/
						}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
								
									//普通付款方式
									if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
										log.info("*********财务公司对外付款，暂不考虑*********");
										
									
									//代理汇兑方式	
									}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
										log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
										
										
									//先拨后支方式	
									}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
										log.info("*********先拨后支，暂不考虑*********");
										
									}
						}
					}
			}
		} catch (Exception e) {			
			log.info("根据交易对象形成委托贷款收回等指令时出错，无法形成指令！");
			e.printStackTrace();
			throw new IException(e.getMessage(),e);			
		}
		return colInstructionParams;
    }
    
    /**
	 * 自营贷款收回形成指令
	 * 
	 * @param Object
	 * @throws IException
	 */
    private Collection createReceiveLoanIntruction(CreateInstructionParam param) throws IException {
    	
		//自营贷款收回生成银行指令新逻辑start
    	Collection colInstructionParams = new ArrayList();
   try {
		TransRepaymentLoanInfo transInfo = new TransRepaymentLoanInfo();
		transInfo = (TransRepaymentLoanInfo)param.getObjInfo();   
		
		
		//自营贷款收回
		if(transInfo.getAmount() > 0)
		{
			log.debug("------自营贷款收回指令生成开始--------");
				//分笔
	    		if(transInfo.getCapitalAndInterstDealway()==SETTConstant.CapitalAndInterestDealWay.DIVIDE_DEAL)
	    		{
	    			   //本金处理方式
	    				if(transInfo.getDepositAccountID() >0) 
	        			{
	    					BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
	        				BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
	        				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
	        				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
	        				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
	        				bankInstructionSettingQueryInfo.setAccountId(transInfo.getDepositAccountID());

	        				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
	        				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
	        				{
	        					return null;
	        				}
	        				
	        				AccountHome home =
	        					(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
	        				Account accounthome = (Account) home.create();
	        				/**二级户账户模式*/
	        				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
	        					
	        					//普通付款方式
	        					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
	        						//逻辑：付款方: 内部活期账户对应的银行二级户；收款方：银行二级户的上级银行账户

	        						//付款方
	        						bankInstructionInfo.setPayAccountNo(accounthome.findAccountByID(transInfo.getDepositAccountID()).getAccountNo());	
	        						//
	        						//获得付款方账户的上级银行账号单位
	        						ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
	        						instruction.setSystemId(1);
	        						AccountInfo accountInfo = accounthome.findAccountByID(transInfo.getDepositAccountID());
	        						instruction.setReferenceCode(accountInfo.getAccountNo());
	        						RespGetGeneralBankAcctInfo generalbankacctInfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
	        						if(generalbankacctInfo==null || generalbankacctInfo.getAccountInfo()==null)
					    			{
	        							throw new Exception("未找到付款方账户的上级账户信息");
					    			}
	        						//
	        						//收款方
	        						bankInstructionInfo.setReceiveAccountNo(generalbankacctInfo.getAccountInfo().getAccountNO());//收款账户号
	        						bankInstructionInfo.setReceiveAccountName(generalbankacctInfo.getAccountInfo().getAccountName());//收款账户名称
	        						bankInstructionInfo.setReceiveBranchName(generalbankacctInfo.getAccountInfo().getBankName());//收款人开户行名称
	        						bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctInfo.getAccountInfo().getBranchProvince());//收款人开户行所在地省名称
	        						bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctInfo.getAccountInfo().getBranchCity());//收款人开户行所在地城市名称
	        						bankInstructionInfo.setAmount(transInfo.getAmount());
	        						bankInstructionInfo.setTransType(TransType.NORMAL);
	        					
	        					
	        					//代理汇兑方式	
	        					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
	        						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
	        						
	        						
	        					//先拨后支方式	
	        					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
	        						log.info("*********先拨后支，暂不考虑*********");
	        						
	        						
	        					}
	        					
	        				/**门户账户模式*/
	        				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
	        						
	        						//普通付款方式
	        						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
	        							log.info("*********财务公司对外付款，暂不考虑*********");
	        							
	        						
	        						//代理汇兑方式	
	        						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
	        							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
	        							
	        							
	        						//先拨后支方式	
	        						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
	        							log.info("*********先拨后支，暂不考虑*********");
	        							
	        						}
	        						
	        				/**收支两条线模式*/
	        				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
	        						
	        						//普通付款方式
	        						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
	        							log.info("*********财务公司对外付款，暂不考虑*********");
	        							
	        						
	        						//代理汇兑方式	
	        						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
	        							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
	        							
	        							
	        						//先拨后支方式	
	        						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
	        							log.info("*********先拨后支，暂不考虑*********");
	        							
	        						}
	        				}
	        				//币种 等其他信息
	        				bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
	        				bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
	        				bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
	        				bankInstructionInfo.setAbstract(transInfo.getAbstract());
	        				bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
	        				bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
	        				bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
	        				colInstructionParams.add(bankInstructionInfo);
        			}
		    		//从付息账户收回利息
					if(transInfo.getPayInterestAccountID()>0 && (transInfo.getRealInterest()+transInfo.getRealCompoundInterest()+transInfo.getRealOverDueInterest()) >0 )
					{
							BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
	        				BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
	        				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
	        				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
	        				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
	        				bankInstructionSettingQueryInfo.setAccountId(transInfo.getPayInterestAccountID());

	        				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
	        				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
	        				{
	        					return null;
	        				}
	        				
	        				AccountHome home =
	        					(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
	        				Account accounthome = (Account) home.create();
	        				/**二级户账户模式*/
	        				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
	        					
	        					//普通付款方式
	        					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
	        						//逻辑：付款方: 内部活期账户对应的银行二级户；收款方：银行二级户的上级银行账户

	        						//付款方
	        						bankInstructionInfo.setPayAccountNo(accounthome.findAccountByID(transInfo.getPayInterestAccountID()).getAccountNo());	
	        						//
	        						//获得付款方账户的上级银行账号单位
	        						ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
	        						instruction.setSystemId(1);
	        						AccountInfo accountInfo = accounthome.findAccountByID(transInfo.getPayInterestAccountID());
	        						instruction.setReferenceCode(accountInfo.getAccountNo());
	        						RespGetGeneralBankAcctInfo generalbankacctInfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
	        						if(generalbankacctInfo==null || generalbankacctInfo.getAccountInfo()==null)
					    			{
	        							throw new Exception("未找到付款方账户的上级账户信息");
					    			}
	        						//
	        						//收款方
	        						bankInstructionInfo.setReceiveAccountNo(generalbankacctInfo.getAccountInfo().getAccountNO());//收款账户号
	        						bankInstructionInfo.setReceiveAccountName(generalbankacctInfo.getAccountInfo().getAccountName());//收款账户名称
	        						bankInstructionInfo.setReceiveBranchName(generalbankacctInfo.getAccountInfo().getBankName());//收款人开户行名称
	        						bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctInfo.getAccountInfo().getBranchProvince());//收款人开户行所在地省名称
	        						bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctInfo.getAccountInfo().getBranchCity());//收款人开户行所在地城市名称
	        						bankInstructionInfo.setAmount(transInfo.getRealInterest()+transInfo.getRealCompoundInterest()+transInfo.getRealOverDueInterest() );	
	        						bankInstructionInfo.setTransType(TransType.NORMAL);
	        					
	        					//代理汇兑方式	
	        					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
	        						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
	        						
	        						
	        					//先拨后支方式	
	        					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
	        						log.info("*********先拨后支，暂不考虑*********");
	        						
	        						
	        					}
	        					
	        				/**门户账户模式*/
	        				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
	        						
	        						//普通付款方式
	        						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
	        							log.info("*********财务公司对外付款，暂不考虑*********");
	        							
	        						
	        						//代理汇兑方式	
	        						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
	        							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
	        							
	        							
	        						//先拨后支方式	
	        						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
	        							log.info("*********先拨后支，暂不考虑*********");
	        							
	        						}
	        						
	        				/**收支两条线模式*/
	        				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
	        						
	        						//普通付款方式
	        						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
	        							log.info("*********财务公司对外付款，暂不考虑*********");
	        							
	        						
	        						//代理汇兑方式	
	        						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
	        							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
	        							
	        							
	        						//先拨后支方式	
	        						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
	        							log.info("*********先拨后支，暂不考虑*********");
	        							
	        						}
	        				}
	        				//币种 等其他信息
	        				bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
	        				bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
	        				bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
	        				bankInstructionInfo.setAbstract(transInfo.getAbstract());
	        				bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
	        				bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
	        				bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
	        				colInstructionParams.add(bankInstructionInfo);
	        			}
						
			}
			else if(transInfo.getCapitalAndInterstDealway()==SETTConstant.CapitalAndInterestDealWay.SUM_DEAL)
			{
				 //本金+利息处理方式
				if(transInfo.getDepositAccountID() >0) 
    			{
					BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
    				BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
    				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
    				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
    				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
    				bankInstructionSettingQueryInfo.setAccountId(transInfo.getDepositAccountID());

    				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
    				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
    				{
    					return null;
    				}
    				
    				AccountHome home =
    					(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
    				Account accounthome = (Account) home.create();
    				/**二级户账户模式*/
    				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
    					
    					//普通付款方式
    					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
    						//逻辑：付款方: 内部活期账户对应的银行二级户；收款方：银行二级户的上级银行账户

    						//付款方
    						bankInstructionInfo.setPayAccountNo(accounthome.findAccountByID(transInfo.getDepositAccountID()).getAccountNo());	
    						//
    						//获得付款方账户的上级银行账号单位
    						ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
    						instruction.setSystemId(1);
    						AccountInfo accountInfo = accounthome.findAccountByID(transInfo.getDepositAccountID());
    						instruction.setReferenceCode(accountInfo.getAccountNo());
    						RespGetGeneralBankAcctInfo generalbankacctInfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
    						if(generalbankacctInfo==null || generalbankacctInfo.getAccountInfo()==null)
			    			{
    							throw new Exception("未找到付款方账户的上级账户信息");
			    			}
    						//
    						//收款方
    						bankInstructionInfo.setReceiveAccountNo(generalbankacctInfo.getAccountInfo().getAccountNO());//收款账户号
    						bankInstructionInfo.setReceiveAccountName(generalbankacctInfo.getAccountInfo().getAccountName());//收款账户名称
    						bankInstructionInfo.setReceiveBranchName(generalbankacctInfo.getAccountInfo().getBankName());//收款人开户行名称
    						bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctInfo.getAccountInfo().getBranchProvince());//收款人开户行所在地省名称
    						bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctInfo.getAccountInfo().getBranchCity());//收款人开户行所在地城市名称
    						bankInstructionInfo.setAmount(transInfo.getAmount() + transInfo.getRealInterest()+transInfo.getRealCompoundInterest()+transInfo.getRealOverDueInterest() );	
    						bankInstructionInfo.setTransType(TransType.NORMAL);
    					
    					
    					//代理汇兑方式	
    					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
    						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
    						
    						
    					//先拨后支方式	
    					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
    						log.info("*********先拨后支，暂不考虑*********");
    						
    						
    					}
    					
    				/**门户账户模式*/
    				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
    						
    						//普通付款方式
    						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
    							log.info("*********财务公司对外付款，暂不考虑*********");
    							
    						
    						//代理汇兑方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
    							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
    							
    							
    						//先拨后支方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
    							log.info("*********先拨后支，暂不考虑*********");
    							
    						}
    						
    				/**收支两条线模式*/
    				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
    						
    						//普通付款方式
    						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
    							log.info("*********财务公司对外付款，暂不考虑*********");
    							
    						
    						//代理汇兑方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
    							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
    							
    							
    						//先拨后支方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
    							log.info("*********先拨后支，暂不考虑*********");
    							
    						}
    					}
    				//币种 等其他信息
    				bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
    				bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
    				bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
    				bankInstructionInfo.setAbstract(transInfo.getAbstract());
    				bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
    				bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
    				bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
    				colInstructionParams.add(bankInstructionInfo);
    				}
				}
	    		//从活期账户付出担保费到活期账户(付收担保费账户号都存在)
				if(transInfo.getPaySuretyAccountID() >0 && transInfo.getReceiveSuretyAccountID() > 0 && transInfo.getRealSuretyFee()>0)
				{
					BankInstructionSettingInfo bankInstructionSettingQueryInfos = new BankInstructionSettingInfo();
					bankInstructionSettingQueryInfos.setNOfficeId(param.getOfficeID());
					bankInstructionSettingQueryInfos.setNCurrencyId(param.getCurrencyID());
					bankInstructionSettingQueryInfos.setTransType(param.getTransactionTypeID());
					bankInstructionSettingQueryInfos.setAccountId(transInfo.getPaySuretyAccountID());
					BankInstructionSettingInfo bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfos);
					bankInstructionSettingQueryInfos.setAccountId(transInfo.getReceiveSuretyAccountID());
					BankInstructionSettingInfo bankInstructionSettingInfo1 = getSettingInfoByAccountId(bankInstructionSettingQueryInfos);
					if (bankInstructionSettingInfo.getIsSend() != Constant.TRUE && bankInstructionSettingInfo1.getIsSend() != Constant.TRUE) { 
						return null;
					}
					//当双方都是二级户 只发一条指令
					if(bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL && bankInstructionSettingInfo1.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL)
					{

						
						//普通付款方式
						if(bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY
							&&bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							//逻辑：付款方：活期账户对应的银行二级户；收款方：活期账户对应的银行二级户

							BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
							AccountInfo accountInfo=new AccountInfo();
							Sett_AccountDAO dao=new Sett_AccountDAO();			
							accountInfo=dao.findByID(transInfo.getPaySuretyAccountID());
							bankInstructionInfo.setPayAccountNo(accountInfo.getAccountNo());
							accountInfo=dao.findByID(transInfo.getReceiveSuretyAccountID());
							bankInstructionInfo.setReceiveReferenceCode(accountInfo.getAccountNo());
								

							bankInstructionInfo.setTransType(TransType.NORMAL);
	
							//币种 等其他信息
							bankInstructionInfo.setAmount(transInfo.getRealSuretyFee());	
		    				bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
		    				bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
		    				bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
		    				bankInstructionInfo.setAbstract(transInfo.getAbstract());
		    				bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
		    				bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
		    				bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
		    				colInstructionParams.add(bankInstructionInfo);	
							
						//代理汇兑方式	
						}else if (bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT
								&&bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY
								&&bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
							
						}
					
					}	
				else
				{
					if(transInfo.getPaySuretyAccountID() >0)
					{
						BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
	    				BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
	    				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
	    				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
	    				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
	    				bankInstructionSettingQueryInfo.setAccountId(transInfo.getPaySuretyAccountID());
	    				//付款
	    				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
	    				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE)
	    				{
	    					return null;
	    				}
	    				
	    				AccountHome home =
	    					(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
	    				Account accounthome = (Account) home.create();
	    				/**二级户账户模式*/
	    				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
	    					
	    					//普通付款方式
	    					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
	    						//逻辑：付款方: 内部活期账户对应的银行二级户；收款方： 银行二级户对应的上级银行账户
	    						//付款方
								bankInstructionInfo.setPayAccountNo((accounthome.findAccountByID(transInfo.getPaySuretyAccountID())).getAccountNo());         
				    			//获得付款方账户的上级银行账号单位
								ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
								instruction.setSystemId(1);
								AccountInfo accountInfo = accounthome.findAccountByID(transInfo.getPaySuretyAccountID());
								instruction.setReferenceCode(accountInfo.getAccountNo());
								RespGetGeneralBankAcctInfo generalbankacctInfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
								if(generalbankacctInfo==null || generalbankacctInfo.getAccountInfo()==null)
				    			{
									throw new Exception("未找到付款方账户的上级账户信息");
				    			}
								//
								//收款方
								bankInstructionInfo.setReceiveAccountNo(generalbankacctInfo.getAccountInfo().getAccountNO());//收款账户号
								bankInstructionInfo.setReceiveAccountName(generalbankacctInfo.getAccountInfo().getAccountName());//收款账户名称
								bankInstructionInfo.setReceiveBranchName(generalbankacctInfo.getAccountInfo().getBankName());//收款人开户行名称
								bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctInfo.getAccountInfo().getBranchProvince());//收款人开户行所在地省名称
								bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctInfo.getAccountInfo().getBranchCity());//收款人开户行所在地城市名称
				    			//
								bankInstructionInfo.setAmount(transInfo.getRealSuretyFee());	
	    						bankInstructionInfo.setTransType(TransType.NORMAL);
	    					
	    					//代理汇兑方式	
	    					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
	    						log.info("*********财务公司代成员单位对外付款 ，暂不考虑*********");
	    						
	    					//先拨后支方式	
	    					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
	    						log.info("*********先拨后支，暂不考虑*********");
	    						
	    					}
	    					
	    				/**门户账户模式*/
	    				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
	    						
	    						//普通付款方式
	    						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
	    							log.info("*********财务公司对外付款，暂不考虑*********");
	    							
	    						
	    						//代理汇兑方式	
	    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
	    							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
	    							
	    							
	    						//先拨后支方式	
	    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
	    							log.info("*********先拨后支，暂不考虑*********");
	    							
	    						}
	    						
	    				/**收支两条线模式*/
	    				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
	    						
	    						//普通付款方式
	    						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
	    							log.info("*********财务公司对外付款，暂不考虑*********");
	    							
	    						
	    						//代理汇兑方式	
	    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
	    							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
	    							
	    							
	    						//先拨后支方式	
	    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
	    							log.info("*********先拨后支，暂不考虑*********");
	    							
	    						}
	    				}
	    				//币种 等其他信息
	    				bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
	    				bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
	    				bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
	    				bankInstructionInfo.setAbstract(transInfo.getAbstract());
	    				bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
	    				bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
	    				bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
	    				colInstructionParams.add(bankInstructionInfo);	
					}
					if(transInfo.getReceiveSuretyAccountID() > 0)
					{
						BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
	    				BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
	    				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
	    				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
	    				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
	    				bankInstructionSettingQueryInfo.setAccountId(transInfo.getPaySuretyAccountID());
	    				
	    				//收款
	    				bankInstructionSettingQueryInfo.setAccountId(transInfo.getReceiveSuretyAccountID());
	    				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
	    				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE)
	    				{
	    					return null;
	    				}
	    				AccountHome home =
	    					(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
	    				Account accounthome = (Account) home.create();
	    				/**二级户账户模式*/
	    				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
	    					
	    					//普通付款方式
	    					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
	    						//逻辑：付款方: 银行二级户对应的上级银行账户；收款方： 内部活期账户对应的银行二级户
	    						//付款方
				    			//获得收款方账户的上级银行账号单位
								ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
								instruction.setSystemId(1);
								AccountInfo accountInfo = accounthome.findAccountByID(transInfo.getReceiveSuretyAccountID());
								instruction.setReferenceCode(accountInfo.getAccountNo());
								RespGetGeneralBankAcctInfo generalbankacctInfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
								if(generalbankacctInfo==null || generalbankacctInfo.getAccountInfo()==null)
				    			{
									throw new Exception("未找到收款款方账户的上级账户信息");
				    			}
								//
								bankInstructionInfo.setPayBankAccountNO(generalbankacctInfo.getAccountInfo().getAccountNO());
								//收方信息
								bankInstructionInfo.setReceiveReferenceCode((accounthome.findAccountByID(transInfo.getReceiveSuretyAccountID())).getAccountNo());			
	    						//
								bankInstructionInfo.setAmount(transInfo.getRealSuretyFee());	
	    						bankInstructionInfo.setTransType(TransType.NORMAL);
	    					
	    					//代理汇兑方式	
	    					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
	    						log.info("*********财务公司代成员单位对外付款 ，暂不考虑*********");
	    						
	    					//先拨后支方式	
	    					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
	    						log.info("*********先拨后支，暂不考虑*********");
	    						
	    					}
	    					
	    				/**门户账户模式*/
	    				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
	    						
	    						//普通付款方式
	    						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
	    							log.info("*********财务公司对外付款，暂不考虑*********");
	    							
	    						
	    						//代理汇兑方式	
	    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
	    							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
	    							
	    							
	    						//先拨后支方式	
	    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
	    							log.info("*********先拨后支，暂不考虑*********");
	    							
	    						}
	    						
	    				/**收支两条线模式*/
	    				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
	    						
	    						//普通付款方式
	    						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
	    							log.info("*********财务公司对外付款，暂不考虑*********");
	    							
	    						
	    						//代理汇兑方式	
	    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
	    							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
	    							
	    							
	    						//先拨后支方式	
	    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
	    							log.info("*********先拨后支，暂不考虑*********");
	    							
	    						}
	    				}
	    				//币种 等其他信息
	    				bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
	    				bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
	    				bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
	    				bankInstructionInfo.setAbstract(transInfo.getAbstract());
	    				bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
	    				bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
	    				bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
	    				colInstructionParams.add(bankInstructionInfo);	
					}
				}
			}
			//从开户行付出担保费到活期账户
			else if (transInfo.getSuretyBankID() >0 && transInfo.getReceiveSuretyAccountID() > 0 && transInfo.getRealSuretyFee()>0)
			{
					BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
    				BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
    				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
    				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
    				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
    				//付款
    				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByTransType(bankInstructionSettingQueryInfo);
    				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE)
    				{
    					return null;
    				}
    				//收款
    				bankInstructionSettingQueryInfo.setAccountId(transInfo.getReceiveSuretyAccountID());
    				BankInstructionSettingInfo _bankInstructionSettingInfo1 = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
    				if(_bankInstructionSettingInfo1.getIsSend() != Constant.TRUE)
    				{
    					return null;
    				}
    				AccountHome home =
    					(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
    				Account accounthome = (Account) home.create();
    				/**二级户账户模式*/
    				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL
    					&&_bankInstructionSettingInfo1.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
    					
    					//普通付款方式
    					if(_bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
    						//付款方: 财务公司开户行
							Sett_BranchDAO  branchDAO = new Sett_BranchDAO();
							BranchInfo branchInfo = branchDAO.findByID(transInfo.getSuretyBankID()); 
							bankInstructionInfo.setPayBankAccountNO(branchInfo.getBankAccountCode());//付款银行账户号
							bankInstructionInfo.setPayAccountName(branchInfo.getBankServiceName());//付款账户名称             
							bankInstructionInfo.setPayDepartmentName(branchInfo.getBankServiceName());//付款人单位全称
    						//收款方
    						bankInstructionInfo.setReceiveReferenceCode(accounthome.findAccountByID(transInfo.getReceiveSuretyAccountID()).getAccountNo());	
    						//
    						bankInstructionInfo.setAmount(transInfo.getRealSuretyFee());	
    						bankInstructionInfo.setTransType(TransType.NORMAL);
    					
    					//代理汇兑方式	
    					}else if (_bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.AGENT){
    						log.info("*********财务公司代成员单位对外付款 ，暂不考虑*********");
    						
    					//先拨后支方式	
    					}else if (_bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
    						log.info("*********先拨后支，暂不考虑*********");
    						
    					}
    					
    				/**门户账户模式*/
    				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
    						
    						//普通付款方式
    						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
    							log.info("*********财务公司对外付款，暂不考虑*********");
    							
    						
    						//代理汇兑方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
    							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
    							
    							
    						//先拨后支方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
    							log.info("*********先拨后支，暂不考虑*********");
    							
    						}
    						
    				/**收支两条线模式*/
    				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
    						
    						//普通付款方式
    						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
    							log.info("*********财务公司对外付款，暂不考虑*********");
    							
    						
    						//代理汇兑方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
    							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
    							
    							
    						//先拨后支方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
    							log.info("*********先拨后支，暂不考虑*********");
    							
    						}
    				}
    				//币种 等其他信息
    				bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
    				bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
    				bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
    				bankInstructionInfo.setAbstract(transInfo.getAbstract());
    				bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
    				bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
    				bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
    				colInstructionParams.add(bankInstructionInfo);	
			}
			log.debug("------自营贷款收回指令生成结束--------");
    	}
		//进行利息处理
		else if(transInfo.getAmount() == 0)
		{
			log.debug("------自营贷款收回-利息支付处理指令生成开始--------");
			
			if(transInfo.getPayInterestAccountID()>0 && (transInfo.getRealInterest()+transInfo.getRealCompoundInterest()+transInfo.getRealOverDueInterest())> 0)
			{
					BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
    				BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
    				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
    				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
    				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
    				bankInstructionSettingQueryInfo.setAccountId(transInfo.getPayInterestAccountID());

    				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
    				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
    				{
    					return null;
    				}
    				
    				AccountHome home =
    					(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
    				Account accounthome = (Account) home.create();
    				/**二级户账户模式*/
    				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
    					
    					//普通付款方式
    					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
    						//逻辑：付款方: 内部活期账户对应的银行二级户；收款方：银行二级户的上级银行账户

    						//付款方
    						bankInstructionInfo.setPayAccountNo(accounthome.findAccountByID(transInfo.getPayInterestAccountID()).getAccountNo());	
    						//
    						//获得付款方账户的上级银行账号单位
    						ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
    						instruction.setSystemId(1);
    						AccountInfo accountInfo = accounthome.findAccountByID(transInfo.getPayInterestAccountID());
    						instruction.setReferenceCode(accountInfo.getAccountNo());
    						RespGetGeneralBankAcctInfo generalbankacctInfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
    						if(generalbankacctInfo==null || generalbankacctInfo.getAccountInfo()==null)
			    			{
    							throw new Exception("未找到付款方账户的上级账户信息");
			    			}
    						//
    						//收款方
    						bankInstructionInfo.setReceiveAccountNo(generalbankacctInfo.getAccountInfo().getAccountNO());//收款账户号
    						bankInstructionInfo.setReceiveAccountName(generalbankacctInfo.getAccountInfo().getAccountName());//收款账户名称
    						bankInstructionInfo.setReceiveBranchName(generalbankacctInfo.getAccountInfo().getBankName());//收款人开户行名称
    						bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctInfo.getAccountInfo().getBranchProvince());//收款人开户行所在地省名称
    						bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctInfo.getAccountInfo().getBranchCity());//收款人开户行所在地城市名称
    						bankInstructionInfo.setAmount(transInfo.getRealInterest()+transInfo.getRealCompoundInterest()+transInfo.getRealOverDueInterest() );	
    						bankInstructionInfo.setTransType(TransType.NORMAL);
    					
    					//代理汇兑方式	
    					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
    						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
    						
    						
    					//先拨后支方式	
    					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
    						log.info("*********先拨后支，暂不考虑*********");
    						
    						
    					}
    					
    				/**门户账户模式*/
    				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
    						
    						//普通付款方式
    						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
    							log.info("*********财务公司对外付款，暂不考虑*********");
    							
    						
    						//代理汇兑方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
    							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
    							
    							
    						//先拨后支方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
    							log.info("*********先拨后支，暂不考虑*********");
    							
    						}
    						
    				/**收支两条线模式*/
    				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
    						
    						//普通付款方式
    						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
    							log.info("*********财务公司对外付款，暂不考虑*********");
    							
    						
    						//代理汇兑方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
    							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
    							
    							
    						//先拨后支方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
    							log.info("*********先拨后支，暂不考虑*********");
    							
    						}
    				}
    				//币种 等其他信息
    				bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
    				bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
    				bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
    				bankInstructionInfo.setAbstract(transInfo.getAbstract());
    				bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
    				bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
    				bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
    				colInstructionParams.add(bankInstructionInfo);
    			}
			//从活期账户付出担保费到活期账户(付收担保费账户号都存在)
			if(transInfo.getPaySuretyAccountID() >0 && transInfo.getReceiveSuretyAccountID() > 0 && transInfo.getRealSuretyFee()>0)
			{
				BankInstructionSettingInfo bankInstructionSettingQueryInfos = new BankInstructionSettingInfo();
				bankInstructionSettingQueryInfos.setNOfficeId(param.getOfficeID());
				bankInstructionSettingQueryInfos.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingQueryInfos.setTransType(param.getTransactionTypeID());
				bankInstructionSettingQueryInfos.setAccountId(transInfo.getPaySuretyAccountID());
				BankInstructionSettingInfo bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfos);
				bankInstructionSettingQueryInfos.setAccountId(transInfo.getReceiveSuretyAccountID());
				BankInstructionSettingInfo bankInstructionSettingInfo1 = getSettingInfoByAccountId(bankInstructionSettingQueryInfos);
				if (bankInstructionSettingInfo.getIsSend() != Constant.TRUE && bankInstructionSettingInfo1.getIsSend() != Constant.TRUE) { 
					return null;
				}
				//当双方都是二级户 只发一条指令
				if(bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL && bankInstructionSettingInfo1.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL)
				{

					
					//普通付款方式
					if(bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY
						&&bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
						//逻辑：付款方：活期账户对应的银行二级户；收款方：活期账户对应的银行二级户

						BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
						AccountInfo accountInfo=new AccountInfo();
						Sett_AccountDAO dao=new Sett_AccountDAO();			
						accountInfo=dao.findByID(transInfo.getPaySuretyAccountID());
						bankInstructionInfo.setPayAccountNo(accountInfo.getAccountNo());
						accountInfo=dao.findByID(transInfo.getReceiveSuretyAccountID());
						bankInstructionInfo.setReceiveReferenceCode(accountInfo.getAccountNo());
							

						bankInstructionInfo.setTransType(TransType.NORMAL);

						//币种 等其他信息
						bankInstructionInfo.setAmount(transInfo.getRealSuretyFee());
	    				bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
	    				bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
	    				bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
	    				bankInstructionInfo.setAbstract(transInfo.getAbstract());
	    				bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
	    				bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
	    				bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
	    				colInstructionParams.add(bankInstructionInfo);	
						
					//代理汇兑方式	
					}else if (bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT
							&&bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
						
						
					//先拨后支方式	
					}else if (bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY
							&&bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
						
						
					}
				
				}	
			else
			{
				if(transInfo.getPaySuretyAccountID() >0)
				{
					BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
    				BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
    				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
    				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
    				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
    				bankInstructionSettingQueryInfo.setAccountId(transInfo.getPaySuretyAccountID());
    				//付款
    				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
    				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE)
    				{
    					return null;
    				}
    				
    				AccountHome home =
    					(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
    				Account accounthome = (Account) home.create();
    				/**二级户账户模式*/
    				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
    					
    					//普通付款方式
    					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
    						//逻辑：付款方: 内部活期账户对应的银行二级户；收款方： 银行二级户对应的上级银行账户
    						//付款方
							bankInstructionInfo.setPayAccountNo((accounthome.findAccountByID(transInfo.getPaySuretyAccountID())).getAccountNo());         
			    			//获得付款方账户的上级银行账号单位
							ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
							instruction.setSystemId(1);
							AccountInfo accountInfo = accounthome.findAccountByID(transInfo.getPaySuretyAccountID());
							instruction.setReferenceCode(accountInfo.getAccountNo());
							RespGetGeneralBankAcctInfo generalbankacctInfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
							if(generalbankacctInfo==null || generalbankacctInfo.getAccountInfo()==null)
			    			{
								throw new Exception("未找到付款方账户的上级账户信息");
			    			}
							//
							//收款方
							bankInstructionInfo.setReceiveAccountNo(generalbankacctInfo.getAccountInfo().getAccountNO());//收款账户号
							bankInstructionInfo.setReceiveAccountName(generalbankacctInfo.getAccountInfo().getAccountName());//收款账户名称
							bankInstructionInfo.setReceiveBranchName(generalbankacctInfo.getAccountInfo().getBankName());//收款人开户行名称
							bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctInfo.getAccountInfo().getBranchProvince());//收款人开户行所在地省名称
							bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctInfo.getAccountInfo().getBranchCity());//收款人开户行所在地城市名称
			    			//
							bankInstructionInfo.setAmount(transInfo.getRealSuretyFee());	
    						bankInstructionInfo.setTransType(TransType.NORMAL);
    					
    					//代理汇兑方式	
    					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
    						log.info("*********财务公司代成员单位对外付款 ，暂不考虑*********");
    						
    					//先拨后支方式	
    					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
    						log.info("*********先拨后支，暂不考虑*********");
    						
    					}
    					
    				/**门户账户模式*/
    				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
    						
    						//普通付款方式
    						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
    							log.info("*********财务公司对外付款，暂不考虑*********");
    							
    						
    						//代理汇兑方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
    							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
    							
    							
    						//先拨后支方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
    							log.info("*********先拨后支，暂不考虑*********");
    							
    						}
    						
    				/**收支两条线模式*/
    				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
    						
    						//普通付款方式
    						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
    							log.info("*********财务公司对外付款，暂不考虑*********");
    							
    						
    						//代理汇兑方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
    							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
    							
    							
    						//先拨后支方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
    							log.info("*********先拨后支，暂不考虑*********");
    							
    						}
    				}
    				//币种 等其他信息
    				bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
    				bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
    				bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
    				bankInstructionInfo.setAbstract(transInfo.getAbstract());
    				bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
    				bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
    				bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
    				colInstructionParams.add(bankInstructionInfo);	
				}
				if(transInfo.getReceiveSuretyAccountID() > 0)
				{
					BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
    				BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
    				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
    				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
    				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
    				bankInstructionSettingQueryInfo.setAccountId(transInfo.getPaySuretyAccountID());
    				
    				//收款
    				bankInstructionSettingQueryInfo.setAccountId(transInfo.getReceiveSuretyAccountID());
    				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
    				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE)
    				{
    					return null;
    				}
    				AccountHome home =
    					(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
    				Account accounthome = (Account) home.create();
    				/**二级户账户模式*/
    				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
    					
    					//普通付款方式
    					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
    						//逻辑：付款方: 银行二级户对应的上级银行账户；收款方： 内部活期账户对应的银行二级户
    						//付款方
			    			//获得收款方账户的上级银行账号单位
							ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
							instruction.setSystemId(1);
							AccountInfo accountInfo = accounthome.findAccountByID(transInfo.getReceiveSuretyAccountID());
							instruction.setReferenceCode(accountInfo.getAccountNo());
							RespGetGeneralBankAcctInfo generalbankacctInfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
							if(generalbankacctInfo==null || generalbankacctInfo.getAccountInfo()==null)
			    			{
								throw new Exception("未找到收款款方账户的上级账户信息");
			    			}
							//
							bankInstructionInfo.setPayBankAccountNO(generalbankacctInfo.getAccountInfo().getAccountNO());
							//收方信息
							bankInstructionInfo.setReceiveReferenceCode((accounthome.findAccountByID(transInfo.getReceiveSuretyAccountID())).getAccountNo());			
    						//
							bankInstructionInfo.setAmount(transInfo.getRealSuretyFee());	
    						bankInstructionInfo.setTransType(TransType.NORMAL);
    					
    					//代理汇兑方式	
    					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
    						log.info("*********财务公司代成员单位对外付款 ，暂不考虑*********");
    						
    					//先拨后支方式	
    					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
    						log.info("*********先拨后支，暂不考虑*********");
    						
    					}
    					
    				/**门户账户模式*/
    				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
    						
    						//普通付款方式
    						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
    							log.info("*********财务公司对外付款，暂不考虑*********");
    							
    						
    						//代理汇兑方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
    							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
    							
    							
    						//先拨后支方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
    							log.info("*********先拨后支，暂不考虑*********");
    							
    						}
    						
    				/**收支两条线模式*/
    				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
    						
    						//普通付款方式
    						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
    							log.info("*********财务公司对外付款，暂不考虑*********");
    							
    						
    						//代理汇兑方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
    							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
    							
    							
    						//先拨后支方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
    							log.info("*********先拨后支，暂不考虑*********");
    							
    						}
    				}
    				//币种 等其他信息
    				bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
    				bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
    				bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
    				bankInstructionInfo.setAbstract(transInfo.getAbstract());
    				bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
    				bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
    				bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
    				colInstructionParams.add(bankInstructionInfo);	
					}
				}
			}
			//从开户行付出担保费到活期账户
			else if (transInfo.getSuretyBankID() >0 && transInfo.getReceiveSuretyAccountID() > 0 && transInfo.getRealSuretyFee()>0)
			{
					BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
    				BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
    				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
    				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
    				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
    				//付款
    				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByTransType(bankInstructionSettingQueryInfo);
    				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE)
    				{
    					return null;
    				}
    				//收款
    				bankInstructionSettingQueryInfo.setAccountId(transInfo.getReceiveSuretyAccountID());
    				BankInstructionSettingInfo _bankInstructionSettingInfo1 = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
    				if(_bankInstructionSettingInfo1.getIsSend() != Constant.TRUE)
    				{
    					return null;
    				}
    				AccountHome home =
    					(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
    				Account accounthome = (Account) home.create();
    				/**二级户账户模式*/
    				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL
    					&&_bankInstructionSettingInfo1.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
    					
    					//普通付款方式
    					if(_bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
    						//付款方: 财务公司开户行
							Sett_BranchDAO  branchDAO = new Sett_BranchDAO();
							BranchInfo branchInfo = branchDAO.findByID(transInfo.getSuretyBankID()); 
							bankInstructionInfo.setPayBankAccountNO(branchInfo.getBankAccountCode());//付款银行账户号
							bankInstructionInfo.setPayAccountName(branchInfo.getBankServiceName());//付款账户名称             
							bankInstructionInfo.setPayDepartmentName(branchInfo.getBankServiceName());//付款人单位全称
    						//收款方
    						bankInstructionInfo.setReceiveReferenceCode(accounthome.findAccountByID(transInfo.getReceiveSuretyAccountID()).getAccountNo());	
    						//
    						bankInstructionInfo.setAmount(transInfo.getRealSuretyFee());	
    						bankInstructionInfo.setTransType(TransType.NORMAL);
    					
    					//代理汇兑方式	
    					}else if (_bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.AGENT){
    						log.info("*********财务公司代成员单位对外付款 ，暂不考虑*********");
    						
    					//先拨后支方式	
    					}else if (_bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
    						log.info("*********先拨后支，暂不考虑*********");
    						
    					}
    					
    				/**门户账户模式*/
    				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
    						
    						//普通付款方式
    						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
    							log.info("*********财务公司对外付款，暂不考虑*********");
    							
    						
    						//代理汇兑方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
    							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
    							
    							
    						//先拨后支方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
    							log.info("*********先拨后支，暂不考虑*********");
    							
    						}
    						
    				/**收支两条线模式*/
    				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
    						
    						//普通付款方式
    						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
    							log.info("*********财务公司对外付款，暂不考虑*********");
    							
    						
    						//代理汇兑方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
    							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
    							
    							
    						//先拨后支方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
    							log.info("*********先拨后支，暂不考虑*********");
    							
    						}
    				}
    				//币种 等其他信息
    				bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
    				bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
    				bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
    				bankInstructionInfo.setAbstract(transInfo.getAbstract());
    				bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
    				bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
    				bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
    				colInstructionParams.add(bankInstructionInfo);	
			}
			log.debug("------自营贷款收回-利息支付处理指令生成结束--------");
		}

	
		//end
		} catch (Exception e) {			
			log.info("根据交易对象形成自营贷款收回收回时出错，无法形成指令！");
			e.printStackTrace();
			throw new IException(e.getMessage(),e);			
		}
		return colInstructionParams;
    }
    
    /**
	 * 贴现收回交易形成指令
	 * 
	 * @param Object
	 * @throws IException
	 */
    private Collection createReceiveDiscountIntruction(CreateInstructionParam param) throws IException {
    	Collection colInstructionParams = new ArrayList();
    	try {
    		
    		TransRepaymentDiscountInfo transInfo = new TransRepaymentDiscountInfo();
    		transInfo = (TransRepaymentDiscountInfo)param.getObjInfo();
    		
    		RespGetGeneralBankAcctInfo  generalbankacctinfo = new RespGetGeneralBankAcctInfo();
    		
    		AccountHome home =
				(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
			Account accounthome = (Account) home.create();

			log.debug("-----------开始生成贴现收回银行指令---------------");
			//进行退票处理
			if(transInfo.getNDepositAccountID() > 0)
			{

				long lAccouontID=transInfo.getNDepositAccountID();

				BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
				bankInstructionSettingQueryInfo.setAccountId(lAccouontID);

				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
				if (_bankInstructionSettingInfo.getIsSend() != Constant.TRUE)
				{
					return null;
				}
				/**二级户账户模式*/
				if (_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL) { 						
					
					//	普通付款方式
					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
					{
						//逻辑：付款方：活期账户对应的银行二级账户；收款方：银行二级账户的上级银行账户
						ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
						instruction.setSystemId(1);
						instruction.setReferenceCode(accounthome.findAccountByID(transInfo.getNDepositAccountID()).getAccountNo());
						generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(instruction);//获得活期账户的上级银行账号单位
						if(generalbankacctinfo==null || generalbankacctinfo.getAccountInfo()==null)
		    			{
							throw new Exception("未找到活期账户的上级账户信息");
		    			}
						log.debug("-----------贴现收回--二级户到总户---------------");
						//进行退票处理
						if(transInfo.getNIsReturned() > 0){
							log.debug("-----------退票-(实付贴现金额+罚息)--------------");
							double disAmount = transInfo.getMReturnedAmount()+transInfo.getMOverDueInterest();
							BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
							
							//付方信息
							bankInstructionInfo.setPayAccountNo((accounthome.findAccountByID(transInfo.getNDepositAccountID())).getAccountNo());
							bankInstructionInfo.setPayBankAccountNO("");//付款银行账户号
							bankInstructionInfo.setPayAcctBankCode("");//付款账户银行编号
							bankInstructionInfo.setPayAccountName((accounthome.findAccountByID(transInfo.getNDepositAccountID())).getAccountName());//付款账户名称             
							bankInstructionInfo.setPayDepartmentName((accounthome.findAccountByID(transInfo.getNDepositAccountID())).getAccountName());//付款人单位全称           
							bankInstructionInfo.setPayBranchName("");//付款人开户行名称
							
							//收方信息
							bankInstructionInfo.setReceiveAccountNo(generalbankacctinfo.getAccountInfo().getAccountNO());
							bankInstructionInfo.setReceiveAccountName(generalbankacctinfo.getAccountInfo().getAccountName());
							bankInstructionInfo.setReceiveReferenceCode("");
							bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctinfo.getAccountInfo().getBranchCity());
							bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctinfo.getAccountInfo().getBranchProvince());
							bankInstructionInfo.setReceiveBranchName(generalbankacctinfo.getAccountInfo().getBankName());
							
							
							bankInstructionInfo.setReceiveBankExchangeCode("");
							bankInstructionInfo.setReceiveBranchCodeOfBank("");					
							bankInstructionInfo.setTransactionNo(transInfo.getSTransNo());
							bankInstructionInfo.setTransType(TransType.NORMAL);
							//金额，币种 等其他信息
							bankInstructionInfo.setAmount(disAmount);				
							bankInstructionInfo.setCurrencyID(transInfo.getNCurrencyID());
							bankInstructionInfo.setOfficeId(transInfo.getNOfficeID());
							bankInstructionInfo.setAbstract(transInfo.getSAbstract());
							bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
							bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
							bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getNInputUserID()));
							colInstructionParams.add(bankInstructionInfo);
						}
						
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
							
						}
						
					/**门户账户模式*/
					}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
							
							//普通付款方式
							if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
								log.info("*********财务公司对外付款，暂不考虑*********");
								
							
							//代理汇兑方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
								log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
								
								
							//先拨后支方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
								log.info("*********先拨后支，暂不考虑*********");
								
							}
							
					/**收支两条线模式*/
					}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
							
							//普通付款方式
							if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
								log.info("*********财务公司对外付款，暂不考虑*********");
								
							
							//代理汇兑方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
								log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
								
								
							//先拨后支方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
								log.info("*********先拨后支，暂不考虑*********");
								
							}
					}
		}
		//进行非退票处理
		if(transInfo.getNCurrentAccountID() > 0)
		{
			//added by mzh_fu 2007/03/30 是否生成指令检验
			/*********************begin*****************************/
			long lAccouontID=transInfo.getNCurrentAccountID();

			BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
			bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
			bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
			bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
			bankInstructionSettingQueryInfo.setAccountId(lAccouontID);

			BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
			if (_bankInstructionSettingInfo.getIsSend() != Constant.TRUE)
			{
				return null;
			}
			/**二级户账户模式*/
			if (_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL) { 	

				//普通付款方式
				if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
				{
					//逻辑：付款方：活期账户对应的银行二级账户；银行二级账户的上级银行账户
					ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
					instruction.setSystemId(1);
					instruction.setReferenceCode(accounthome.findAccountByID(transInfo.getNCurrentAccountID()).getAccountNo());
					generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(instruction);//获得活期账户的上级银行账号单位
					if(generalbankacctinfo==null || generalbankacctinfo.getAccountInfo()==null)
	    			{
						throw new Exception("未找到活期账户的上级账户信息");
	    			}
					log.debug("-----------贴现收回--二级户到总户---------------");
			
					log.debug("-----------不退票-(票面金额)---------------");
					double disAmount = transInfo.getMAmount();
					BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
					
					//付方信息
					bankInstructionInfo.setPayAccountNo((accounthome.findAccountByID(transInfo.getNCurrentAccountID())).getAccountNo());
					bankInstructionInfo.setPayBankAccountNO("");//付款银行账户号
					bankInstructionInfo.setPayAcctBankCode("");//付款账户银行编号
					bankInstructionInfo.setPayAccountName((accounthome.findAccountByID(transInfo.getNCurrentAccountID())).getAccountName());//付款账户名称             
					bankInstructionInfo.setPayDepartmentName((accounthome.findAccountByID(transInfo.getNCurrentAccountID())).getAccountName());//付款人单位全称           
					bankInstructionInfo.setPayBranchName("");//付款人开户行名称
					
					//收方信息
					bankInstructionInfo.setReceiveAccountNo(generalbankacctinfo.getAccountInfo().getAccountNO());
					bankInstructionInfo.setReceiveAccountName(generalbankacctinfo.getAccountInfo().getAccountName());
					bankInstructionInfo.setReceiveReferenceCode("");
					bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctinfo.getAccountInfo().getBranchCity());
					bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctinfo.getAccountInfo().getBranchProvince());
					bankInstructionInfo.setReceiveBranchName(generalbankacctinfo.getAccountInfo().getBankName());
					
					
					bankInstructionInfo.setReceiveBankExchangeCode("");
					bankInstructionInfo.setReceiveBranchCodeOfBank("");					
					bankInstructionInfo.setTransactionNo(transInfo.getSTransNo());
					bankInstructionInfo.setTransType(TransType.NORMAL);
					//金额，币种 等其他信息
					bankInstructionInfo.setAmount(disAmount);				
					bankInstructionInfo.setCurrencyID(transInfo.getNCurrencyID());
					bankInstructionInfo.setOfficeId(transInfo.getNOfficeID());
					bankInstructionInfo.setAbstract(transInfo.getSAbstract());
					bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
					bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
					bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getNInputUserID()));
					colInstructionParams.add(bankInstructionInfo);
					//代理汇兑方式	
				}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
					log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
					
				//先拨后支方式	
				}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
					log.info("*********先拨后支，暂不考虑*********");
					
					
				}
				
			/**门户账户模式*/
			}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
					
					//普通付款方式
					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
						log.info("*********财务公司对外付款，暂不考虑*********");
						
					
					//代理汇兑方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
						
						
					//先拨后支方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
						
					}
					
			/**收支两条线模式*/
			}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
					
					//普通付款方式
					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
						log.info("*********财务公司对外付款，暂不考虑*********");
						
					
					//代理汇兑方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
						
						
					//先拨后支方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
						
					}
			}
		}
			

			
		} catch (Exception e) {			
			log.info("根据交易对象形成贴现收回银行指令时出错，无法形成指令！");
			e.printStackTrace();
			throw new IException(e.getMessage(),e);			
		}
		return colInstructionParams;
    }
    
    /**
	 * 定期支取、通知存款支取交易形成指令
	 * 
	 * @param Object
	 * @throws IException
	 */
    private Collection createWithdrawFixedDepositIntruction(CreateInstructionParam param) throws IException {	
    	
    	Collection colInstructionParams = new ArrayList();
    	try {
    		//定期支取、通知存款支取生成银行指令新逻辑 start
    		TransFixedDrawInfo transInfo = new TransFixedDrawInfo();
    		transInfo = (TransFixedDrawInfo)param.getObjInfo();
    		

    		log.info("定期支取、通知存款支取付款指令对象："+UtilOperation.dataentityToString(transInfo));
			 
    		//交易金额
        	double transAmount = 0.0;
    		log.info("------开始定期支取、通知存款支取银行指令生成");
    		
    		//分笔
    		if(transInfo.getCapitalAndInterestDealWay()==SETTConstant.CapitalAndInterestDealWay.DIVIDE_DEAL)
    		{
    				//本金处理方式:收活期账户
    				if(transInfo.getCurrentAccountID() >0) 
        			{
    					BankInstructionInfo bankInstructionInfo = new BankInstructionInfo();
        				BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
        				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
        				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
        				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
        				bankInstructionSettingQueryInfo.setAccountId(transInfo.getCurrentAccountID());
        				bankInstructionSettingQueryInfo.setTransModule(SETTConstant.TransModule.INSIDE_PAY);
        				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
        				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
        				{
        					return null;
        				}
        				
        				AccountHome home =
        					(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
        				Account accounthome = (Account) home.create();
        				/**二级户账户模式*/
        				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
        					
        					//普通付款方式
        					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
        						//逻辑：付款方: 银行二级户的上级银行账户；收款方：内部活期账户对应的银行二级户

        						//获得收款方账户的上级银行账号单位
        						ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
        						instruction.setSystemId(1);
        						AccountInfo accountInfo = accounthome.findAccountByID(transInfo.getCurrentAccountID());
        						instruction.setReferenceCode(accountInfo.getAccountNo());
        						RespGetGeneralBankAcctInfo generalbankacctInfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
        						if(generalbankacctInfo==null || generalbankacctInfo.getAccountInfo()==null)
        		    			{
        							throw new Exception("未找到收款方账户的上级账户信息");
        		    			}
        						//
        						//付款方
        						bankInstructionInfo.setPayBankAccountNO(generalbankacctInfo.getAccountInfo().getAccountNO());//付款账户号
        						//收款方
        						bankInstructionInfo.setReceiveReferenceCode(accounthome.findAccountByID(transInfo.getCurrentAccountID()).getAccountNo());	
        						//
        						bankInstructionInfo.setTransType(TransType.NORMAL);
        					
        					//代理汇兑方式	
        					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
        						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
        						
        						
        					//先拨后支方式	
        					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
        						log.info("*********先拨后支，暂不考虑*********");
        						
        					}
        					
        				/**门户账户模式*/
        				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
        						
        						//普通付款方式
        						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
        							log.info("*********财务公司对外付款，暂不考虑*********");
        							
        						
        						//代理汇兑方式	
        						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
        							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
        							
        							
        						//先拨后支方式	
        						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
        							log.info("*********先拨后支，暂不考虑*********");
        							
        						}
        						
        				/**收支两条线模式*/
        				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
        						
        						//普通付款方式
        						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
        							log.info("*********财务公司对外付款，暂不考虑*********");
        							
        						
        						//代理汇兑方式	
        						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
        							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
        							
        							
        						//先拨后支方式	
        						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
        							log.info("*********先拨后支，暂不考虑*********");
        							
        						}
        				}
        				//金额，币种 等其他信息
            			if (transInfo.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)
                		{
                			//定期转活期（定期支取）
                			log.info("------定期转活期--------");
                			transAmount = transInfo.getDrawAmount();
                		}
                		else if (transInfo.getTransactionTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW )
                		{ //通知存款支取
                			log.info("------通知存款支取--------");
                			transAmount = transInfo.getDrawAmount();
                		}	
            			bankInstructionInfo.setAmount(transAmount);				
            			bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
            			bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
            			bankInstructionInfo.setAbstract(transInfo.getAbstract());
            			bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
            			bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
            			bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
            			bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
            			colInstructionParams.add(bankInstructionInfo);
        			}
    				//本金处理方式
    				else if(transInfo.getBankID() >0) 
        			{
    					BankInstructionInfo bankInstructionInfo = new BankInstructionInfo();
        				BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
        				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
        				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
        				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
        				bankInstructionSettingQueryInfo.setTransModule(SETTConstant.TransModule.BANK_PAY);
        				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByTransType(bankInstructionSettingQueryInfo);
        				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE)
        				{
        					return null;
        				}
        				
        				AccountHome home =
        					(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
        				Account accounthome = (Account) home.create();
        				/**二级户账户模式*/
        				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
        					
        						//逻辑：付款方: 财务公司开户行；收款方：外部收款方银行账号
        						//付款方: 财务公司开户行
        						Sett_BranchDAO  branchDAO = new Sett_BranchDAO();
        						BranchInfo branchInfo = branchDAO.findByID(transInfo.getBankID()); 
        						//开户行没有设置发指令，就不发银行指令
        						if(branchInfo.getIsAutoVirementByBank()==Constant.YesOrNo.YES)
        						{
	        						bankInstructionInfo.setPayBankAccountNO(branchInfo.getBankAccountCode());//付款银行账户号
	        						bankInstructionInfo.setPayAccountName(branchInfo.getBankServiceName());//付款账户名称             
	        						bankInstructionInfo.setPayDepartmentName(branchInfo.getBankServiceName());//付款人单位全称
	        						//收款方信息，收款方为外部银行账户
	        						bankInstructionInfo.setReceiveAccountNo(transInfo.getExtAcctNo());
	        						bankInstructionInfo.setReceiveAccountName(transInfo.getExtClientName());
	        						bankInstructionInfo.setReceiveBranchName(transInfo.getRemitInBank());
	        						bankInstructionInfo.setReceiveBranchAreaNameOfProvince(transInfo.getRemitInProvince());
	        						bankInstructionInfo.setReceiveBranchAreaNameOfCity(transInfo.getRemitInCity());
	        						bankInstructionInfo.setReceiveBankExchangeCode("");
	        						bankInstructionInfo.setReceiveBranchCodeOfBank("");					
	        						//
	        						
	        						bankInstructionInfo.setTransType(TransType.NORMAL);
        						}
        					
        				/**门户账户模式*/
        				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
        						
        						//普通付款方式
        						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
        							log.info("*********财务公司对外付款，暂不考虑*********");
        							
        						
        						//代理汇兑方式	
        						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
        							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
        							
        							
        						//先拨后支方式	
        						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
        							log.info("*********先拨后支，暂不考虑*********");
        							
        						}
        						
        				/**收支两条线模式*/
        				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
        						
        						//普通付款方式
        						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
        							log.info("*********财务公司对外付款，暂不考虑*********");
        							
        						
        						//代理汇兑方式	
        						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
        							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
        							
        							
        						//先拨后支方式	
        						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
        							log.info("*********先拨后支，暂不考虑*********");
        							
        						}
        				}
        				//金额，币种 等其他信息
            			if (transInfo.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)
                		{
                			//定期转活期（定期支取）
                			log.info("------定期转活期--------");
                			transAmount = transInfo.getDrawAmount();
                		}
                		else if (transInfo.getTransactionTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW )
                		{ //通知存款支取
                			log.info("------通知存款支取--------");
                			transAmount = transInfo.getDrawAmount();
                		}	
            			bankInstructionInfo.setAmount(transAmount);				
            			bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
            			bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
            			bankInstructionInfo.setAbstract(transInfo.getAbstract());
            			bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
            			bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
            			bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
            			bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));	
            			colInstructionParams.add(bankInstructionInfo);
        		}
    			//利息处理方式：收息为活期户
    			if(transInfo.getReceiveInterestAccountID() >0 &&(transInfo.getTotalInterest()>0 || transInfo.getNoticeTotalInterest()>0)) 
        		{
    					BankInstructionInfo bankInstructionInfo = new BankInstructionInfo();
        				BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
        				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
        				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
        				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
        				bankInstructionSettingQueryInfo.setAccountId(transInfo.getReceiveInterestAccountID());
        				bankInstructionSettingQueryInfo.setTransModule(SETTConstant.TransModule.INSIDE_PAY);
        				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
        				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
        				{
        					return null;
        				}
        				
        				AccountHome home =
        					(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
        				Account accounthome = (Account) home.create();
        				/**二级户账户模式*/
        				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
        					
        					//普通付款方式
        					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
        						//逻辑：付款方: 银行二级户的上级银行账户；收款方：内部活期账户对应的银行二级户

        						//获得收款方账户的上级银行账号单位
        						ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
        						instruction.setSystemId(1);
        						AccountInfo accountInfo = accounthome.findAccountByID(transInfo.getReceiveInterestAccountID());
        						instruction.setReferenceCode(accountInfo.getAccountNo());
        						RespGetGeneralBankAcctInfo generalbankacctInfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
        						if(generalbankacctInfo==null || generalbankacctInfo.getAccountInfo()==null)
        		    			{
        							throw new Exception("未找到收款方账户的上级账户信息");
        		    			}
        						//
        						//付款方
        						bankInstructionInfo.setPayBankAccountNO(generalbankacctInfo.getAccountInfo().getAccountNO());//付款账户号
        						//收款方
        						bankInstructionInfo.setReceiveReferenceCode(accounthome.findAccountByID(transInfo.getReceiveInterestAccountID()).getAccountNo());	
        						//
        						bankInstructionInfo.setTransType(TransType.NORMAL);
        					
        					//代理汇兑方式	
        					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
        						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
        						
        						
        					//先拨后支方式	
        					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
        						log.info("*********先拨后支，暂不考虑*********");
        						
        						
        					}
        					
        				/**门户账户模式*/
        				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
        						
        						//普通付款方式
        						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
        							log.info("*********财务公司对外付款，暂不考虑*********");
        							
        						
        						//代理汇兑方式	
        						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
        							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
        							
        							
        						//先拨后支方式	
        						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
        							log.info("*********先拨后支，暂不考虑*********");
        							
        						}
        						
        				/**收支两条线模式*/
        				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
        						
        						//普通付款方式
        						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
        							log.info("*********财务公司对外付款，暂不考虑*********");
        							
        						
        						//代理汇兑方式	
        						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
        							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
        							
        							
        						//先拨后支方式	
        						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
        							log.info("*********先拨后支，暂不考虑*********");
        							
        						}
        				}
        				//金额，币种 等其他信息
            			if (transInfo.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)
                		{
                			//定期转活期（定期支取）
                			log.info("------定期转活期--------");
                			transAmount=transInfo.getTotalInterest();
                		}
                		else if (transInfo.getTransactionTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW )
                		{ //通知存款支取
                			log.info("------通知存款支取--------");
                			transAmount = transInfo.getNoticeTotalInterest();
                		}	
            			bankInstructionInfo.setAmount(transAmount);				
            			bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
            			bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
            			bankInstructionInfo.setAbstract(transInfo.getAbstract());
            			bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
            			bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
            			bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
            			bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
            			colInstructionParams.add(bankInstructionInfo);
        			}
    				//利息处理方式：收息为开户行
        			else if(transInfo.getInterestBankID() >0 &&(transInfo.getTotalInterest()>0 || transInfo.getNoticeTotalInterest()>0)) 
        			{
        				BankInstructionInfo bankInstructionInfo = new BankInstructionInfo();
        				BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
        				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
        				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
        				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
        				bankInstructionSettingQueryInfo.setTransModule(SETTConstant.TransModule.BANK_PAY);
        				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByTransType(bankInstructionSettingQueryInfo);
        				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE)
        				{
        					return null;
        				}
        				
        				AccountHome home =
        					(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
        				Account accounthome = (Account) home.create();
        				/**二级户账户模式*/
        				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
        					
        						//逻辑：付款方: 财务公司开户行；收款方：外部收款方银行账号
        						//付款方: 财务公司开户行
        						Sett_BranchDAO  branchDAO = new Sett_BranchDAO();
        						BranchInfo branchInfo = branchDAO.findByID(transInfo.getInterestBankID()); 
        						bankInstructionInfo.setPayBankAccountNO(branchInfo.getBankAccountCode());//付款银行账户号
        						bankInstructionInfo.setPayAccountName(branchInfo.getBankServiceName());//付款账户名称             
        						bankInstructionInfo.setPayDepartmentName(branchInfo.getBankServiceName());//付款人单位全称
        						//收款方信息，收款方为外部银行账户
        						bankInstructionInfo.setReceiveAccountNo(transInfo.getInterestExtAcctNo());
        						bankInstructionInfo.setReceiveAccountName(transInfo.getInterestExtClientName());
        						bankInstructionInfo.setReceiveBranchName(transInfo.getInterestRemitInBank());
        						bankInstructionInfo.setReceiveBranchAreaNameOfProvince(transInfo.getInterestRemitInProvince());
        						bankInstructionInfo.setReceiveBranchAreaNameOfCity(transInfo.getInterestRemitInCity());
        						bankInstructionInfo.setReceiveBankExchangeCode("");
        						bankInstructionInfo.setReceiveBranchCodeOfBank("");					
        						//
        						
        						bankInstructionInfo.setTransType(TransType.NORMAL);
        					
        					
        				/**门户账户模式*/
        				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
        						
        						//普通付款方式
        						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
        							log.info("*********财务公司对外付款，暂不考虑*********");
        							
        						
        						//代理汇兑方式	
        						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
        							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
        							
        							
        						//先拨后支方式	
        						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
        							log.info("*********先拨后支，暂不考虑*********");
        							
        						}
        						
        				/**收支两条线模式*/
        				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
        						
        						//普通付款方式
        						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
        							log.info("*********财务公司对外付款，暂不考虑*********");
        							
        						
        						//代理汇兑方式	
        						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
        							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
        							
        							
        						//先拨后支方式	
        						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
        							log.info("*********先拨后支，暂不考虑*********");
        							
        						}
        				}
        				//金额，币种 等其他信息
            			if (transInfo.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)
                		{
                			//定期转活期（定期支取）
                			log.info("------定期转活期--------");
                			transAmount = transInfo.getTotalInterest();
                		}
                		else if (transInfo.getTransactionTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW )
                		{ //通知存款支取
                			log.info("------通知存款支取--------");
                			transAmount = transInfo.getNoticeTotalInterest();
                		}	
            			bankInstructionInfo.setAmount(transAmount);				
            			bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
            			bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
            			bankInstructionInfo.setAbstract(transInfo.getAbstract());
            			bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
            			bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
            			bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
            			bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
            			colInstructionParams.add(bankInstructionInfo);
    			}
    		}
    	    //汇总
    		else
    		{
    			//本金＋利息，处理方式，汇总：本金和利息的收款方内部户是同一个(内部转账)
    			if(transInfo.getCurrentAccountID() >0) 
    			{
    				BankInstructionInfo bankInstructionInfo = new BankInstructionInfo();
    				BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
    				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
    				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
    				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
    				bankInstructionSettingQueryInfo.setAccountId(transInfo.getCurrentAccountID());
    				bankInstructionSettingQueryInfo.setTransModule(SETTConstant.TransModule.INSIDE_PAY);
    				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
    				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
    				{
    					return null;
    				}
    				
    				AccountHome home =
    					(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
    				Account accounthome = (Account) home.create();
    				/**二级户账户模式*/
    				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
    					
    					//普通付款方式
    					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
    						//逻辑：付款方: 银行二级户的上级银行账户；收款方：内部活期账户对应的银行二级户

    						//获得收款方账户的上级银行账号单位
    						ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
    						instruction.setSystemId(1);
    						AccountInfo accountInfo = accounthome.findAccountByID(transInfo.getCurrentAccountID());
    						instruction.setReferenceCode(accountInfo.getAccountNo());
    						RespGetGeneralBankAcctInfo generalbankacctInfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
    						if(generalbankacctInfo==null || generalbankacctInfo.getAccountInfo()==null)
    		    			{
    							throw new Exception("未找到收款方账户的上级账户信息");
    		    			}
    						//
    						//付款方
    						bankInstructionInfo.setPayBankAccountNO(generalbankacctInfo.getAccountInfo().getAccountNO());//付款账户号
    						//收款方
    						bankInstructionInfo.setReceiveReferenceCode(accounthome.findAccountByID(transInfo.getCurrentAccountID()).getAccountNo());	
    						//
    						bankInstructionInfo.setTransType(TransType.NORMAL);
    					
    					//代理汇兑方式	
    					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
    						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
    						
    						
    					//先拨后支方式	
    					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
    						log.info("*********先拨后支，暂不考虑*********");
    						
    						
    					}
    					
    				/**门户账户模式*/
    				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
    						
    						//普通付款方式
    						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
    							log.info("*********财务公司对外付款，暂不考虑*********");
    							
    						
    						//代理汇兑方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
    							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
    							
    							
    						//先拨后支方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
    							log.info("*********先拨后支，暂不考虑*********");
    							
    						}
    						
    				/**收支两条线模式*/
    				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
    						
    						//普通付款方式
    						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
    							log.info("*********财务公司对外付款，暂不考虑*********");
    							
    						
    						//代理汇兑方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
    							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
    							
    							
    						//先拨后支方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
    							log.info("*********先拨后支，暂不考虑*********");
    							
    						}
    				}
    				//金额，币种 等其他信息
        			if (transInfo.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)
            		{
            			//定期转活期（定期支取）
            			log.info("------定期转活期--------");
            			transAmount = transInfo.getDrawAmount()+transInfo.getTotalInterest();
            		}
            		else if (transInfo.getTransactionTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW )
            		{ //通知存款支取
            			log.info("------通知存款支取--------");
            			transAmount = transInfo.getDrawAmount()+transInfo.getNoticeTotalInterest();
            		}	
        			bankInstructionInfo.setAmount(transAmount);				
        			bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
        			bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
        			bankInstructionInfo.setAbstract(transInfo.getAbstract());
        			bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
        			bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
        			bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
        			bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
        			colInstructionParams.add(bankInstructionInfo);
    			}
    			//本金＋利息，处理方式，汇总：本金和利息的收款方是同一个银行(银行付款)
    			else if(transInfo.getBankID() >0) 
    			{
    				BankInstructionInfo bankInstructionInfo = new BankInstructionInfo();
    				BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
    				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
    				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
    				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
    				bankInstructionSettingQueryInfo.setTransModule(SETTConstant.TransModule.BANK_PAY);
    				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByTransType(bankInstructionSettingQueryInfo);
    				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE)
    				{
    					return null;
    				}
    				
    				AccountHome home =
    					(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
    				Account accounthome = (Account) home.create();
    				/**二级户账户模式*/
    				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
    					
    						//逻辑：付款方: 财务公司开户行；收款方：外部收款方银行账号
    						//付款方: 财务公司开户行
    						Sett_BranchDAO  branchDAO = new Sett_BranchDAO();
    						BranchInfo branchInfo = branchDAO.findByID(transInfo.getBankID()); 
    						//开户行没有设置发指令，就不发银行指令
    						if(branchInfo.getIsAutoVirementByBank()==Constant.YesOrNo.YES)
    						{
	    						bankInstructionInfo.setPayBankAccountNO(branchInfo.getBankAccountCode());//付款银行账户号
	    						bankInstructionInfo.setPayAccountName(branchInfo.getBankServiceName());//付款账户名称             
	    						bankInstructionInfo.setPayDepartmentName(branchInfo.getBankServiceName());//付款人单位全称
	    						//收款方信息，收款方为外部银行账户
	    						bankInstructionInfo.setReceiveAccountNo(transInfo.getExtAcctNo());
	    						bankInstructionInfo.setReceiveAccountName(transInfo.getExtClientName());
	    						bankInstructionInfo.setReceiveBranchName(transInfo.getRemitInBank());
	    						bankInstructionInfo.setReceiveBranchAreaNameOfProvince(transInfo.getRemitInProvince());
	    						bankInstructionInfo.setReceiveBranchAreaNameOfCity(transInfo.getRemitInCity());
	    						bankInstructionInfo.setReceiveBankExchangeCode("");
	    						bankInstructionInfo.setReceiveBranchCodeOfBank("");					
	    						//
	    						bankInstructionInfo.setTransType(TransType.NORMAL);
    						}
    					
    				/**门户账户模式*/
    				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
    						
    						//普通付款方式
    						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
    							log.info("*********财务公司对外付款，暂不考虑*********");
    							
    						
    						//代理汇兑方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
    							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
    							
    							
    						//先拨后支方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
    							log.info("*********先拨后支，暂不考虑*********");
    							
    						}
    						
    				/**收支两条线模式*/
    				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
    						
    						//普通付款方式
    						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
    							log.info("*********财务公司对外付款，暂不考虑*********");
    							
    						
    						//代理汇兑方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
    							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
    							
    							
    						//先拨后支方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
    							log.info("*********先拨后支，暂不考虑*********");
    							
    						}
    				}
    				
    				//金额，币种 等其他信息
        			if (transInfo.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)
            		{
            			//定期转活期（定期支取）
            			log.info("------定期转活期--------");
            			transAmount = transInfo.getDrawAmount()+transInfo.getTotalInterest();
            		}
            		else if (transInfo.getTransactionTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW )
            		{ //通知存款支取
            			log.info("------通知存款支取--------");
            			transAmount = transInfo.getDrawAmount()+transInfo.getNoticeTotalInterest();
            		}	
        			bankInstructionInfo.setAmount(transAmount);				
        			bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
        			bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
        			bankInstructionInfo.setAbstract(transInfo.getAbstract());
        			bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
        			bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
        			bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
        			bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
        			colInstructionParams.add(bankInstructionInfo);
    			}
    		}
			
    		log.info("------生成定期支取、通知存款支取银行指令结束");
    		//end
		} catch (Exception e) {			
			log.info("对象形成定期支取或通知存款指令时出错，无法形成指令！"+e.getMessage());
			e.printStackTrace();
			throw new IException(e.getMessage(),e);			
		}
		return colInstructionParams;
    }
    
    /**
	 * 定期开立、通知存款开立交易形成指令
	 * 
	 * @param Object
	 * @throws IException
	 */
    private Collection createOpenFixDepositIntruction(CreateInstructionParam param,long transType) throws IException {    	
    	
    		Collection colInstructionParams = new ArrayList();
    		try {
    		//生成银行指令新逻辑，start
    		TransFixedOpenInfo transInfo = new TransFixedOpenInfo();
    		transInfo = (TransFixedOpenInfo)param.getObjInfo();
    		log.info("------开始定期开立、通知存款开立付款指令生成");
    		BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
			if(transInfo.getCurrentAccountID()>0) 
			{
				BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
				bankInstructionSettingQueryInfo.setAccountId(transInfo.getCurrentAccountID());

				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
				{
					return null;
				}
				
				AccountHome home =
					(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
				Account accounthome = (Account) home.create();
				/**二级户账户模式*/
				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
					
					//普通付款方式
					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
						//逻辑：付款方: 内部活期账户对应的银行二级户；收款方：银行二级户的上级银行账户

						//付款方
						bankInstructionInfo.setPayAccountNo(accounthome.findAccountByID(transInfo.getCurrentAccountID()).getAccountNo());	
						//
						//获得付款方账户的上级银行账号单位
						ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
						instruction.setSystemId(1);
						AccountInfo accountInfo = accounthome.findAccountByID(transInfo.getCurrentAccountID());
						instruction.setReferenceCode(accountInfo.getAccountNo());
						RespGetGeneralBankAcctInfo generalbankacctInfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
						if(generalbankacctInfo==null || generalbankacctInfo.getAccountInfo()==null)
		    			{
							throw new Exception("未找到付款方账户的上级账户信息");
		    			}
						//
						//收款方
						bankInstructionInfo.setReceiveAccountNo(generalbankacctInfo.getAccountInfo().getAccountNO());//收款账户号
						bankInstructionInfo.setReceiveAccountName(generalbankacctInfo.getAccountInfo().getAccountName());//收款账户名称
						bankInstructionInfo.setReceiveBranchName(generalbankacctInfo.getAccountInfo().getBankName());//收款人开户行名称
						bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctInfo.getAccountInfo().getBranchProvince());//收款人开户行所在地省名称
						bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctInfo.getAccountInfo().getBranchCity());//收款人开户行所在地城市名称
						
						bankInstructionInfo.setTransType(TransType.NORMAL);
						//金额，币种 等其他信息
						bankInstructionInfo.setAmount(transInfo.getAmount());				
						bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
						bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
						bankInstructionInfo.setAbstract(transInfo.getAbstract());
						bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
						bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
						bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
						bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
						
						colInstructionParams.add(bankInstructionInfo);
					//代理汇兑方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
						
						bankInstructionInfo.setTransType(TransType.AGENTVIREMENT);
						
					//先拨后支方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
						
						
					}
					
				/**门户账户模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
						
				/**收支两条线模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
				}
				
			}
			
    		log.info("------结束定期开立、通知存款开立付款指令生成");
    		//end
    		
		} catch (Exception e) {			
			//log.info("无法形成指令！"+e.getMessage());
			e.printStackTrace();
			throw new IException(e.getMessage(),e);			
		}
		return colInstructionParams;
    }
    
    /**
	 * 定期续期转存交易类型形成指令
	 * 
	 * @param Object
	 * @throws IException
	 */
    private Collection createContinueFixedDepositIntruction(CreateInstructionParam param) throws IException {    	
    		//定期续期转存生成银行指令新逻辑start
		Collection colInstructionParams = new ArrayList();
    	try
    	{
    		TransFixedContinueInfo transInfo = new TransFixedContinueInfo();
    		transInfo = (TransFixedContinueInfo)param.getObjInfo();
    		log.info("------开始定期续期转存交易付款指令生成，利息");
    		
			if(transInfo.getReceiveInterestAccountID()>0) 
			{
				BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
				BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
				bankInstructionSettingQueryInfo.setAccountId(transInfo.getReceiveInterestAccountID());
				bankInstructionSettingQueryInfo.setTransModule(SETTConstant.TransModule.INSIDE_PAY);
				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
				{
					return null;
				}
				
				AccountHome home =
					(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
				Account accounthome = (Account) home.create();
				/**二级户账户模式*/
				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
					
					//普通付款方式
					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
						//逻辑：付款方: 银行二级户的上级银行账户；收款方：内部活期账户对应的银行二级户

						//获得收款方账户的上级银行账号单位
						ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
						instruction.setSystemId(1);
						AccountInfo accountInfo = accounthome.findAccountByID(transInfo.getReceiveInterestAccountID());
						instruction.setReferenceCode(accountInfo.getAccountNo());
						RespGetGeneralBankAcctInfo generalbankacctInfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
						if(generalbankacctInfo==null || generalbankacctInfo.getAccountInfo()==null)
		    			{
							throw new Exception("未找到收款方账户的上级账户信息");
		    			}
						//
						//付款方
						bankInstructionInfo.setPayBankAccountNO(generalbankacctInfo.getAccountInfo().getAccountNO());//付款账户号
						//收款方
						bankInstructionInfo.setReceiveReferenceCode(accounthome.findAccountByID(transInfo.getReceiveInterestAccountID()).getAccountNo());	
						//
						//金额，币种 等其他信息
						bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
						bankInstructionInfo.setAmount(transInfo.getPayableInterest() + transInfo.getPreDrawInterest());				
						bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
						bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
						bankInstructionInfo.setAbstract(transInfo.getAbstract());
						bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
						bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
						bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
						bankInstructionInfo.setTransType(TransType.NORMAL);
						colInstructionParams.add(bankInstructionInfo);
					//代理汇兑方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
						
						bankInstructionInfo.setTransType(TransType.AGENTVIREMENT);
						
					//先拨后支方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
						
						
					}
					
				/**门户账户模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
						
				/**收支两条线模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
				}
				
			}
			else if(transInfo.getInterestBankID() >0) 
			{
				BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
				BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
				bankInstructionSettingQueryInfo.setTransModule(SETTConstant.TransModule.BANK_PAY);
				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByTransType(bankInstructionSettingQueryInfo);
				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE)
				{
					return null;
				}
				
				AccountHome home =
					(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
				Account accounthome = (Account) home.create();
				/**二级户账户模式*/
				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
					
						//逻辑：付款方: 财务公司开户行；收款方：外部收款方银行账号
						//付款方: 财务公司开户行
						Sett_BranchDAO  branchDAO = new Sett_BranchDAO();
						BranchInfo branchInfo = branchDAO.findByID(transInfo.getInterestBankID()); 
						//付款开户行没有设置发指令，就不发银行指令
						if(branchInfo.getIsAutoVirementByBank()==Constant.YesOrNo.YES)
						{
							bankInstructionInfo.setPayBankAccountNO(branchInfo.getBankAccountCode());//付款银行账户号
							bankInstructionInfo.setPayAccountName(branchInfo.getBankServiceName());//付款账户名称             
							bankInstructionInfo.setPayDepartmentName(branchInfo.getBankServiceName());//付款人单位全称
							//收款方信息，收款方为外部银行账户
							bankInstructionInfo.setReceiveAccountNo(transInfo.getInterestExtAcctNo());
							bankInstructionInfo.setReceiveAccountName(transInfo.getInterestExtClientName());
							bankInstructionInfo.setReceiveBranchName(transInfo.getInterestRemitInBank());
							bankInstructionInfo.setReceiveBranchAreaNameOfProvince(transInfo.getInterestRemitInProvince());
							bankInstructionInfo.setReceiveBranchAreaNameOfCity(transInfo.getInterestRemitInCity());
							bankInstructionInfo.setReceiveBankExchangeCode("");
							bankInstructionInfo.setReceiveBranchCodeOfBank("");					
							//
							//金额，币种 等其他信息
							bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
							bankInstructionInfo.setAmount(transInfo.getPayableInterest() + transInfo.getPreDrawInterest() );				
							bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
							bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
							bankInstructionInfo.setAbstract(transInfo.getAbstract());
							bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
							bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
							bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
							bankInstructionInfo.setTransType(TransType.NORMAL);
							colInstructionParams.add(bankInstructionInfo);
						}
					
				/**门户账户模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
						
				/**收支两条线模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
				}
				
			}
    		log.debug("-----结束定期续期转存交易付款指令生成，利息");
    		//end
			
		} catch (Exception e) {			
			log.info("根据交易对象形成定期续期转存交易指令时出错，无法形成指令！");
			e.printStackTrace();
			throw new IException(e.getMessage(),e);			
		}
		return colInstructionParams;
    }
    
    /**
	 * 总账交易形成指令:借,活期账户为付款方
	 * 				 贷,活期账户为收款方
	 * @param Object
	 * @throws IException
	 */
    private Collection createGeneralLedgerborrowIntruction(CreateInstructionParam param) throws IException {
    	Collection colInstructionParams = new ArrayList();
    	try {
    		TransGeneralLedgerInfo transInfo = new TransGeneralLedgerInfo();//总账info
    		transInfo = (TransGeneralLedgerInfo)param.getObjInfo();
    		
       		RespGetGeneralBankAcctInfo  generalbankacctinfo = new RespGetGeneralBankAcctInfo();
    		BankInstructionOperation bankInstructionOperation = new BankInstructionOperation();//实现info转换成银企接口info
    		
    		AccountHome home =
				(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
			Account accounthome = (Account) home.create();
    		
			//added by mzh_fu 2007/03/30 是否生成指令检验
			/*********************begin*****************************/
			long lAccouontID=transInfo.getAccountID();
			BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
			bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
			bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
			bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
			bankInstructionSettingQueryInfo.setAccountId(lAccouontID);

			BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
			if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
			{
				return null;
			}
			/**********************end****************************/
			if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL)
			{
				//普通付款方式
				if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
				{
					
		    		//取得resp总账info
		    		generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(bankInstructionOperation.converGeneralInfoToInstruction(transInfo));
		    		if(generalbankacctinfo==null || generalbankacctinfo.getAccountInfo()==null)
	    			{
						throw new Exception("未找到活期账户的上级账户信息");
	    			}
					log.debug("-----------开始生成总账银行指令---------------");
					
					log.info("总账发放指令对象："+UtilOperation.dataentityToString(transInfo));
		
					//TransBankDetailInfo tbdi = new TransBankDetailInfo();
					
					if(transInfo.getDirOne() != 1 )//借
					{
						//逻辑：付款方：活期账户对应的银行二级账户；收款方：银行二级账户的上级银行账户
						BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
						//付方信息
						bankInstructionInfo.setPayAccountNo((accounthome.findAccountByID(transInfo.getAccountID())).getAccountNo());
						bankInstructionInfo.setPayBankAccountNO("");//付款银行账户号
						bankInstructionInfo.setPayAcctBankCode("");//付款账户银行编号
						bankInstructionInfo.setPayAccountName((accounthome.findAccountByID(transInfo.getAccountID())).getAccountName());//付款账户名称             
						bankInstructionInfo.setPayDepartmentName((accounthome.findAccountByID(transInfo.getAccountID())).getAccountName());//付款人单位全称           
						bankInstructionInfo.setPayBranchName("");//付款人开户行名称
						//收方信息
						bankInstructionInfo.setReceiveAccountNo(generalbankacctinfo.getAccountInfo().getAccountNO());
						bankInstructionInfo.setReceiveAccountName(generalbankacctinfo.getAccountInfo().getAccountName());
						bankInstructionInfo.setReceiveReferenceCode("");
						bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctinfo.getAccountInfo().getBranchCity());
						bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctinfo.getAccountInfo().getBranchProvince());
						
						bankInstructionInfo.setReceiveDepartmentName(generalbankacctinfo.getAccountInfo().getBankDirectLinkCode());
						bankInstructionInfo.setReceiveBranchName(generalbankacctinfo.getAccountInfo().getBankName());
		
						bankInstructionInfo.setReceiveBankExchangeCode("");
						bankInstructionInfo.setReceiveBranchCodeOfBank("");					
						bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
		
						//金额，币种 等其他信息
						bankInstructionInfo.setAmount(transInfo.getAmount());				
						bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
						bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
						bankInstructionInfo.setAbstract(transInfo.getAbstract());
						bankInstructionInfo.setTransType(TransType.NORMAL);
						bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
						bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
						bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
						colInstructionParams.add(bankInstructionInfo);
					}
					else//贷
					{
						//逻辑：付款方：银行二级账户的上级银行账户；收款方：活期账户对应的银行二级账户
						log.debug("-----------活期账户为贷---------------");
						
						BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
						
						bankInstructionInfo.setPayAccountNo("");
						bankInstructionInfo.setPayBankAccountNO(generalbankacctinfo.getAccountInfo().getAccountNO());//付款银行账户号
						bankInstructionInfo.setPayAcctBankCode(generalbankacctinfo.getAccountInfo().getBankCode());//付款账户银行编号
						bankInstructionInfo.setPayAccountName(generalbankacctinfo.getAccountInfo().getAccountName());//付款账户名称             
						bankInstructionInfo.setPayDepartmentName(generalbankacctinfo.getAccountInfo().getAccountName());//付款人单位全称           
						bankInstructionInfo.setPayBranchName(generalbankacctinfo.getAccountInfo().getAccountName());//付款人开户行名称
		
						//收款方信息，收款方为外部银行账户
						bankInstructionInfo.setReceiveAccountNo("");
						bankInstructionInfo.setReceiveAccountName((accounthome.findAccountByID(transInfo.getAccountID())).getAccountName());
						bankInstructionInfo.setReceiveReferenceCode((accounthome.findAccountByID(transInfo.getAccountID())).getAccountNo());
						
		
						bankInstructionInfo.setReceiveBankExchangeCode("");
						bankInstructionInfo.setReceiveBranchCodeOfBank("");					
						bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
						//金额，币种 等其他信息
						bankInstructionInfo.setAmount(transInfo.getAmount());				
						bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
						bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
						bankInstructionInfo.setAbstract(transInfo.getAbstract());
						bankInstructionInfo.setTransType(TransType.NORMAL);
						bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
						bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
						bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
						colInstructionParams.add(bankInstructionInfo);
						//代理汇兑方式	
					}
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
						
						
					//先拨后支方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
						
						
					}
	    		}
				/**门户账户模式*/
				else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
						
				/**收支两条线模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
				}
		} catch (Exception e) {			
			log.info("根据交易对象形成总账银行指令时出错，无法形成指令！");
			e.printStackTrace();
			throw new IException(e.getMessage(),e);			
		}
		return colInstructionParams;
    }
    
    /**
	 * 内部转账形成指令
	 * 
	 * @param Object
     * @throws Exception
	 */
    private Collection createInternalvirmentIntruction(CreateInstructionParam param) throws IException {
    	
    	if(param.getObjInfo() == null) {
			log.error("构造银行付款指令错误，交易对象参数为空！");
			throw new IException("构造银行付款指令错误，交易对象参数为空！");
		}
    	Collection colInstructionParams = new ArrayList();
    	try {
    		
    		TransCurrentDepositInfo info = new TransCurrentDepositInfo();
    		info = (TransCurrentDepositInfo)param.getObjInfo();       	
    		AccountHome home =
				(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
			Account accounthome = (Account) home.create();
    		log.info("--------------开始内部转账指令------------");

    		//boolean tmpBool = false;

			 long id_pay     = info.getPayAccountID();			//付款账户号
			 long id_receive = info.getReceiveAccountID();	   //收款账户号	
			 	BankInstructionSettingInfo bankInstructionSettingQueryInfos = new BankInstructionSettingInfo();
				bankInstructionSettingQueryInfos.setNOfficeId(param.getOfficeID());
				bankInstructionSettingQueryInfos.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingQueryInfos.setTransType(param.getTransactionTypeID());
				bankInstructionSettingQueryInfos.setAccountId(id_pay);
				BankInstructionSettingInfo bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfos);
				bankInstructionSettingQueryInfos.setAccountId(id_receive);
				BankInstructionSettingInfo bankInstructionSettingInfo1 = getSettingInfoByAccountId(bankInstructionSettingQueryInfos);
				if (bankInstructionSettingInfo.getIsSend() != Constant.TRUE && bankInstructionSettingInfo1.getIsSend() != Constant.TRUE) { 
					return null;
				}
				//当双方都是二级户 只发一条指令
//				if(bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL && bankInstructionSettingInfo1.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL)
//				{
				//当双方都是二级户 ，需要判断配置项，是只发一条指令还是发两条；远逻辑是只要两边都是二级户，就生成一条指令，现在需要读配置项。
				//南航新需求要生成两条转账指令
				if(bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL 
					&& bankInstructionSettingInfo1.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL
					&& Config.getBoolean(ConfigConstant.SETT_CREATEINTERNALVIRMENTINTRUCTION_ONLYONE,true))
				{
					
					
					//普通付款方式
					if(bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY
						&&bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
						//逻辑：付款方：活期账户对应的银行二级户；收款方：活期账户对应的银行二级户

						BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
						AccountInfo accountInfo=new AccountInfo();
						Sett_AccountDAO dao=new Sett_AccountDAO();			
						accountInfo=dao.findByID(id_pay);
						bankInstructionInfo.setPayAccountNo(accountInfo.getAccountNo());
						accountInfo=dao.findByID(id_receive);
						bankInstructionInfo.setReceiveReferenceCode(accountInfo.getAccountNo());
							

						bankInstructionInfo.setTransType(TransType.NORMAL);

						///其他信息
						bankInstructionInfo.setAmount(info.getAmount());//金额 
						bankInstructionInfo.setStrAbstract(info.getAbstractStr());//摘要
						bankInstructionInfo.setSenderNo(String.valueOf(info.getInputUserID()));//指令发送人no
						bankInstructionInfo.setCancellerNo("");//指令撤销人no 
						bankInstructionInfo.setCreateTime( Env.getSystemDateTime());//指令创建时间            
						bankInstructionInfo.setSendTime( Env.getSystemDateTime());//指令发送时间             
						bankInstructionInfo.setCancelTime(new Timestamp(0));//指令撤销时间             
						bankInstructionInfo.setModifyTime( Env.getSystemDateTime());//指令修改时间
						bankInstructionInfo.setStatusID(SETTConstant.BankInstructionStatus.SAVED);//指令状态                 
						bankInstructionInfo.setTransactionNo(info.getTransNo());//指令对应业务地交易号     
						bankInstructionInfo.setTransType(TransType.NORMAL);				
						bankInstructionInfo.setIDOfBankSeg1("");//指令在银行地对应标识 
						bankInstructionInfo.setPayAreaNameOfProvince("");//付款人开户行所在地省名称
						bankInstructionInfo.setPayAreaNameOfCity("");//付款人开户行所在地城市名称
						bankInstructionInfo.setCurrencyID(info.getCurrencyID());//交易币种
						bankInstructionInfo.setOfficeId(info.getOfficeID());
						bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);//汇款速度
		
						bankInstructionInfo.setPayChargeAccountNo("");//手续费账号 
						bankInstructionInfo.setPayChargeBankExchangeCode("");//手续费账户联行号
						bankInstructionInfo.setPayChargeBranchCodeOfBank("");//手续费账户机构号				
						bankInstructionInfo.setRecNetStationName(""); //收款方网点名称
		
						colInstructionParams.add(bankInstructionInfo);
						
					//代理汇兑方式	
					}else if (bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT
							&&bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
						
						
					//先拨后支方式	
					}else if (bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY
							&&bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
						
						
					}
				
				}	
			else
			{
			
				if(id_pay>0)
				{
				
				 //modified by mzh_fu 2008/02/04
				BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingQueryInfo.setTransType(info.getTransactionTypeID());
				bankInstructionSettingQueryInfo.setAccountId(id_pay);
				
				BankInstructionSettingInfo _bankInstructionSettingInfo_pay = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
				
				if(_bankInstructionSettingInfo_pay.getIsSend()!= Constant.TRUE)
					return null;
				if(_bankInstructionSettingInfo_pay.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL && _bankInstructionSettingInfo_pay.getIsSend()== Constant.TRUE)
				{
					//普通付款方式
					if(_bankInstructionSettingInfo_pay.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
					{
						// 逻辑：付款方：活期账户对应的银行二级账户；收款方：银行二级账户的上级银行账户
						BankInstructionInfo pay_bankInstructionInfo = new BankInstructionInfo();				
					
						//获得付款方账户的上级银行账号单位
						ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
						instruction.setSystemId(1);
						AccountInfo payAccountInfo = accounthome.findAccountByID(id_pay);
						instruction.setReferenceCode(payAccountInfo.getAccountNo());
						RespGetGeneralBankAcctInfo generalbankacctinfoPay = BPClientAgent.getGeneralBankAcctInfo(instruction);
						if(generalbankacctinfoPay == null || generalbankacctinfoPay.getAccountInfo()==null)
						{
							throw new Exception("未找到付款方账户的上级账户信息");
						}
						//付款方信息
						pay_bankInstructionInfo.setPayAccountNo(payAccountInfo.getAccountNo());//付款账户号(付款关联号)
						pay_bankInstructionInfo.setPayBankAccountNO("");//付款银行账户号
						pay_bankInstructionInfo.setPayAcctBankCode(""); //付款账户银行编号
						pay_bankInstructionInfo.setPayAccountName(payAccountInfo.getAccountName());//付款账户名称
						pay_bankInstructionInfo.setPayDepartmentName(payAccountInfo.getAccountName());//付款人单位全称
						
						//收款方信息
						pay_bankInstructionInfo.setReceiveAccountNo(generalbankacctinfoPay.getAccountInfo().getAccountNO());//收款账户号
						pay_bankInstructionInfo.setReceiveAccountName(generalbankacctinfoPay.getAccountInfo().getAccountName());//收款账户名称
						pay_bankInstructionInfo.setReceiveBranchName(generalbankacctinfoPay.getAccountInfo().getBankName());//收款人开户行名称
						pay_bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctinfoPay.getAccountInfo().getBranchProvince());//收款人开户行所在地省名称
						pay_bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctinfoPay.getAccountInfo().getBranchCity());//收款人开户行所在地城市名称
						
						//其他信息
						pay_bankInstructionInfo.setAmount(info.getAmount());//金额 
						pay_bankInstructionInfo.setStrAbstract(info.getAbstractStr());//摘要
						pay_bankInstructionInfo.setSenderNo(String.valueOf(info.getInputUserID()));//指令发送人no
						pay_bankInstructionInfo.setCancellerNo("");//指令撤销人no 
						pay_bankInstructionInfo.setCreateTime( Env.getSystemDateTime());//指令创建时间            
						pay_bankInstructionInfo.setSendTime( Env.getSystemDateTime());//指令发送时间             
						pay_bankInstructionInfo.setCancelTime(new Timestamp(0));//指令撤销时间             
						pay_bankInstructionInfo.setModifyTime( Env.getSystemDateTime());//指令修改时间
						pay_bankInstructionInfo.setStatusID(SETTConstant.BankInstructionStatus.SAVED);//指令状态                 
						pay_bankInstructionInfo.setTransactionNo(info.getTransNo());//指令对应业务地交易号     
						//pay_bankInstructionInfo.setTransType(info.getTransactionTypeID());//指令对应业务地类型
						pay_bankInstructionInfo.setTransType(TransType.NORMAL);				
						//pay_bankInstructionInfo.setReceiveBank(info.getTransactionTypeID());//指令接收银行             
						pay_bankInstructionInfo.setIDOfBankSeg1("");//指令在银行地对应标识 
						pay_bankInstructionInfo.setPayAreaNameOfProvince("");//付款人开户行所在地省名称
						pay_bankInstructionInfo.setPayAreaNameOfCity("");//付款人开户行所在地城市名称
						pay_bankInstructionInfo.setCurrencyID(info.getCurrencyID());//交易币种
						pay_bankInstructionInfo.setOfficeId(info.getOfficeID());
						pay_bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);//汇款速度
		
						pay_bankInstructionInfo.setPayChargeAccountNo("");//手续费账号 
						pay_bankInstructionInfo.setPayChargeBankExchangeCode("");//手续费账户联行号
						pay_bankInstructionInfo.setPayChargeBranchCodeOfBank("");//手续费账户机构号				
						pay_bankInstructionInfo.setRecNetStationName(""); //收款方网点名称
		
						colInstructionParams.add(pay_bankInstructionInfo);
					//代理汇兑方式	
					}else if (_bankInstructionSettingInfo_pay.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
						
						
					//先拨后支方式	
					}else if (_bankInstructionSettingInfo_pay.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
						
						
					}
	    		}
				/**门户账户模式*/
				else if(_bankInstructionSettingInfo_pay.getAccountModule() == SETTConstant.AccountModule.PORTAL){
						
						//普通付款方式
						if(_bankInstructionSettingInfo_pay.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo_pay.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo_pay.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
						
				/**收支两条线模式*/
				}else if(_bankInstructionSettingInfo_pay.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
						
						//普通付款方式
						if(_bankInstructionSettingInfo_pay.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo_pay.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo_pay.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
				}
			}
			if(id_receive>0)
			{
				
				 //modified by mzh_fu 2008/02/04
				BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingQueryInfo.setTransType(info.getTransactionTypeID());
				bankInstructionSettingQueryInfo.setAccountId(id_receive);
				BankInstructionSettingInfo _bankInstructionSettingInfo_receive = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
				
				if(_bankInstructionSettingInfo_receive.getIsSend()!= Constant.TRUE)
					return null;
				if(_bankInstructionSettingInfo_receive.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL && _bankInstructionSettingInfo_receive.getIsSend()== Constant.TRUE)
				{
					//普通付款方式
					if(_bankInstructionSettingInfo_receive.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
					{
						//逻辑：付款方：银行二级账户的上级银行账户；收款方：活期账户对应的银行二级账户
						BankInstructionInfo receive_bankInstructionInfo = new BankInstructionInfo();
						
						//获得收款方账户的上级银行账号单位
						ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
						instruction.setSystemId(1);
						AccountInfo receiveAccountInfo = accounthome.findAccountByID(id_receive);
						instruction.setReferenceCode(receiveAccountInfo.getAccountNo());
						RespGetGeneralBankAcctInfo generalbankacctinfoReceive = BPClientAgent.getGeneralBankAcctInfo(instruction);
						
						if(generalbankacctinfoReceive == null || generalbankacctinfoReceive.getAccountInfo()==null)
						{
							throw new Exception("未找到收款方账户的上级账户信息");
						}
						
				 		//付款方信息
						receive_bankInstructionInfo.setPayBankAccountNO(generalbankacctinfoReceive.getAccountInfo().getAccountNO());//付款银行账户号
						receive_bankInstructionInfo.setPayAcctBankCode(generalbankacctinfoReceive.getAccountInfo().getBankCode()); //付款账户银行编号
						receive_bankInstructionInfo.setPayBranchName(generalbankacctinfoReceive.getAccountInfo().getBankName());
						receive_bankInstructionInfo.setPayAreaNameOfProvince(generalbankacctinfoReceive.getAccountInfo().getBranchProvince());
						receive_bankInstructionInfo.setPayAreaNameOfCity(generalbankacctinfoReceive.getAccountInfo().getBranchCity());
		
						//收款方信息
						receive_bankInstructionInfo.setReceiveReferenceCode(receiveAccountInfo.getAccountNo());//收款账户号
						receive_bankInstructionInfo.setReceiveAccountName(receiveAccountInfo.getAccountName());//收款账户名称
						receive_bankInstructionInfo.setReceiveBranchName(receiveAccountInfo.getAccountName());//收款人开户行名称
						
						//其他信息
						receive_bankInstructionInfo.setAmount(info.getAmount());//金额 
						receive_bankInstructionInfo.setStrAbstract(info.getAbstractStr());//摘要
						receive_bankInstructionInfo.setSenderNo(String.valueOf(info.getInputUserID()));//指令发送人no
						receive_bankInstructionInfo.setCancellerNo("");//指令撤销人no 
						receive_bankInstructionInfo.setCreateTime( Env.getSystemDateTime());//指令创建时间            
						receive_bankInstructionInfo.setSendTime( Env.getSystemDateTime());//指令发送时间             
						receive_bankInstructionInfo.setCancelTime(new Timestamp(0));//指令撤销时间             
						receive_bankInstructionInfo.setModifyTime( Env.getSystemDateTime());//指令修改时间
						receive_bankInstructionInfo.setStatusID(SETTConstant.BankInstructionStatus.SAVED);//指令状态                 
						receive_bankInstructionInfo.setTransactionNo(info.getTransNo());//指令对应业务地交易号     
						receive_bankInstructionInfo.setTransType(TransType.NORMAL);//指令对应业务地类型       
						//receive_bankInstructionInfo.setReceiveBank(info.getTransactionTypeID());//指令接收银行             
						receive_bankInstructionInfo.setIDOfBankSeg1("");//指令在银行地对应标识 
						receive_bankInstructionInfo.setPayAreaNameOfProvince("");//付款人开户行所在地省名称
						receive_bankInstructionInfo.setPayAreaNameOfCity("");//付款人开户行所在地城市名称
						receive_bankInstructionInfo.setCurrencyID(info.getCurrencyID());//交易币种
						receive_bankInstructionInfo.setOfficeId(info.getOfficeID());
						receive_bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);//汇款速度
		 
						receive_bankInstructionInfo.setPayChargeAccountNo("");//手续费账号 
						receive_bankInstructionInfo.setPayChargeBankExchangeCode("");//手续费账户联行号
						receive_bankInstructionInfo.setPayChargeBranchCodeOfBank("");//手续费账户机构号				
						receive_bankInstructionInfo.setRecNetStationName(""); //收款方网点名称
		
						colInstructionParams.add(receive_bankInstructionInfo);
					}
					//代理汇兑方式	
					else if (_bankInstructionSettingInfo_receive.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
						
						
					//先拨后支方式	
					}else if (_bankInstructionSettingInfo_receive.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
						
						
					}
				/**门户账户模式*/
				}else if(_bankInstructionSettingInfo_receive.getAccountModule() == SETTConstant.AccountModule.PORTAL){
						
						//普通付款方式
						if(_bankInstructionSettingInfo_receive.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo_receive.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo_receive.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
						
				/**收支两条线模式*/
				}else if(_bankInstructionSettingInfo_receive.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
						
						//普通付款方式
						if(_bankInstructionSettingInfo_receive.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo_receive.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo_receive.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
					}

				}
			} 
    	}
		catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		return colInstructionParams;
    }
    
    
    /**
	 * 委托存款形成指令
	 * 
	 * @param Object
	 * @throws IException
	 */
    private Collection createConsignIntruction(CreateInstructionParam param) throws IException {
    	Collection colInstructionParams = new ArrayList();
    	try {
			TransCurrentDepositInfo info = (TransCurrentDepositInfo) param.getObjInfo();
				if(info.getPayAccountID() > 0)
		    	{
					//added by mzh_fu 2007/03/30 是否生成指令检验
					/*********************begin*****************************/
					long lAccouontID=info.getPayAccountID();

					BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
					bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
					bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
					bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
					bankInstructionSettingQueryInfo.setAccountId(lAccouontID);
					
					BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
					
					if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
						return null;
					
					/**二级户账户模式*/
					if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
						{
						//逻辑：付款方：活期账户对应的银行二级账户；收款方：银行二级账户的上级银行账户
							Log.print("---------------资金来源为活期--------------");
					 		AccountHome home =
								(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
							Account accounthome = (Account) home.create();
							BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
							long id_pay = info.getPayAccountID();//付款账户号
							ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
							//获得付款方账户的上级银行账号单位
							instruction.setSystemId(1);
							AccountInfo payAccountInfo = accounthome.findAccountByID(id_pay);
							instruction.setReferenceCode(payAccountInfo.getAccountNo());
							RespGetGeneralBankAcctInfo generalbankacctinfoPay = BPClientAgent.getGeneralBankAcctInfo(instruction);
							if(generalbankacctinfoPay==null || generalbankacctinfoPay.getAccountInfo()==null)
			    			{
								throw new Exception("未找到付款账户的上级账户信息");
			    			}
							//付款方
							bankInstructionInfo.setPayAccountNo(payAccountInfo.getAccountNo());
							//收款方
							bankInstructionInfo.setReceiveAccountNo(generalbankacctinfoPay.getAccountInfo().getAccountNO());
							bankInstructionInfo.setReceiveAccountName(generalbankacctinfoPay.getAccountInfo().getAccountName());
							bankInstructionInfo.setReceiveBranchCodeOfBank(generalbankacctinfoPay.getAccountInfo().getBankCode());
							bankInstructionInfo.setReceiveBranchName(generalbankacctinfoPay.getAccountInfo().getBankName());
							bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctinfoPay.getAccountInfo().getBranchProvince());
							bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctinfoPay.getAccountInfo().getBranchCity());
							//
							bankInstructionInfo.setPayBranchName("");//付款人开户行名称
							bankInstructionInfo.setAmount(info.getAmount());//金额
							bankInstructionInfo.setComment(info.getAbstractStr());//备注
							bankInstructionInfo.setStrAbstract(info.getAbstractStr());//摘要
							bankInstructionInfo.setSenderNo(String.valueOf(info.getInputUserID()));//指令发送人no
							bankInstructionInfo.setCancellerNo("");//指令撤销人no
							bankInstructionInfo.setCreateTime( Env.getSystemDateTime());//指令创建时间
							bankInstructionInfo.setSendTime( Env.getSystemDateTime());//指令发送时间
							bankInstructionInfo.setCancelTime(new Timestamp(0));//指令撤销时间
							bankInstructionInfo.setModifyTime( Env.getSystemDateTime());//指令修改时间
							bankInstructionInfo.setStatusID(SETTConstant.BankInstructionStatus.SAVED);//指令状态
							bankInstructionInfo.setTransactionNo(info.getTransNo());//指令对应业务地交易号
							bankInstructionInfo.setTransType(TransType.NORMAL);//指令对应业务地类型
							bankInstructionInfo.setReceiveBank(info.getTransactionTypeID());//指令接收银行
							bankInstructionInfo.setIDOfBankSeg1("");//指令在银行地对应标识
							bankInstructionInfo.setPayAreaNameOfProvince("");//付款人开户行所在地省名称
							bankInstructionInfo.setPayAreaNameOfCity("");//付款人开户行所在地城市名称
							bankInstructionInfo.setCurrencyID(info.getCurrencyID());//交易币种
							bankInstructionInfo.setOfficeId(info.getOfficeID());
							bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);//汇款速度
		
							//新增中行所需的联行号，机构号，手续费账号
							bankInstructionInfo.setPayChargeAccountNo("");//手续费账号 
							bankInstructionInfo.setPayChargeBankExchangeCode("");//手续费账户联行号
							bankInstructionInfo.setPayChargeBranchCodeOfBank("");//手续费账户机构号					
							bankInstructionInfo.setRecNetStationName(""); //收款方网点名称
							colInstructionParams.add(bankInstructionInfo);
							
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
							
						}
						
					/**门户账户模式*/
					}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
							
							//普通付款方式
							if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
								log.info("*********财务公司对外付款，暂不考虑*********");
								
							
							//代理汇兑方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
								log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
								
								
							//先拨后支方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
								log.info("*********先拨后支，暂不考虑*********");
								
							}
							
					/**收支两条线模式*/
					}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
							
							//普通付款方式
							if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
								log.info("*********财务公司对外付款，暂不考虑*********");
								
							
							//代理汇兑方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
								log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
								
								
							//先拨后支方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
								log.info("*********先拨后支，暂不考虑*********");
								
							}
		    		}
				}
    		}
			catch (Exception e) {
				e.printStackTrace();
				throw new IException(e.getMessage(),e);
			}
			return colInstructionParams;
		}
    
    	private Collection createInterestPay(CreateInstructionParam param) throws IException{
    		
    		if(param.getObjInfo() == null) {
    			log.error("构造利息支付指令错误，交易对象参数为空！");
    			throw new IException("构造利息支付指令错误，交易对象参数为空！");
    		}
    		Collection colInstructionParams = new ArrayList();
    		try {
    		
    		TransRepaymentLoanInfo transInfo = (TransRepaymentLoanInfo)param.getObjInfo();
    		
    		log.info("-----------开始生成利息费用支付活期结息指令---------------");
			
			log.info("利息费用支付活期结息指令对象："+UtilOperation.dataentityToString(transInfo));
	
    		AccountInfo accountInfo=new AccountInfo();
			Sett_AccountDAO dao=new Sett_AccountDAO();
			long lReceiveInterestAccountID = -1;
			if (transInfo.getReceiveSuretyAccountID()>0) 
			{
				lReceiveInterestAccountID = transInfo.getReceiveSuretyAccountID();
			}
			else if (transInfo.getReceiveInterestAccountID()>0) 
			{
				lReceiveInterestAccountID = transInfo.getReceiveInterestAccountID();
			}
           if(transInfo.getPayInterestAccountID()>0 &&(transInfo.getRealInterest() + transInfo.getRealCompoundInterest() + transInfo.getRealOverDueInterest())>0)
           {
        	
    		//自营或委贷的付款方都是活期账户时
    		if(transInfo.getPayInterestAccountID()>0){
    			BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
				//added by mzh_fu 2007/03/30 是否生成指令检验
				long lAccouontID=transInfo.getPayInterestAccountID();

				BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingQueryInfo.setTransType(transInfo.getTransactionTypeID());
				bankInstructionSettingQueryInfo.setAccountId(lAccouontID);
				
				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
				
				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
					return null;

				/**二级户账户模式*/
				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
					//普通付款方式
					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
					{
					//逻辑：付款方：活期账户对应的银行二级账户；收款方：银行二级账户的上级银行账户
					accountInfo=dao.findByID(transInfo.getPayInterestAccountID());	
					bankInstructionInfo.setPayAccountNo(accountInfo.getAccountNo());

	    			log.info("自营或委贷的付款方");
					//付款方为活期账户的上级账户
					//取得上级账户信息    			
					ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
					instruction.setSystemId(1);//系统id
					instruction.setReferenceCode(accountInfo.getAccountNo());	
					
					RespGetGeneralBankAcctInfo  generalbankacctinfo = new RespGetGeneralBankAcctInfo();
					generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
					if(generalbankacctinfo==null||generalbankacctinfo.getAccountInfo()==null|| generalbankacctinfo.getStatus()!=1){
						throw new Exception("未找到活期账户的上级银行账户信息"); 
					}		
				
					bankInstructionInfo.setReceiveAccountNo(generalbankacctinfo.getAccountInfo().getAccountNO());
					bankInstructionInfo.setReceiveAccountName(generalbankacctinfo.getAccountInfo().getAccountName());
					bankInstructionInfo.setReceiveBranchCodeOfBank(generalbankacctinfo.getAccountInfo().getBankCode());
					bankInstructionInfo.setReceiveBranchName(generalbankacctinfo.getAccountInfo().getBankName());
					bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctinfo.getAccountInfo().getBranchProvince());
					bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctinfo.getAccountInfo().getBranchCity());
					bankInstructionInfo.setTransType(TransType.NORMAL);
					//代理汇兑方式	
				}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
					log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
					
					bankInstructionInfo.setTransType(TransType.AGENTVIREMENT);
					
				//先拨后支方式	
				}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
					log.info("*********先拨后支，暂不考虑*********");
					
					
				}
				
			/**门户账户模式*/
			}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
					
					//普通付款方式
					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
						log.info("*********财务公司对外付款，暂不考虑*********");
						
					
					//代理汇兑方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
						
						
					//先拨后支方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
						
					}
					
			/**收支两条线模式*/
			}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
					
					//普通付款方式
					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
						log.info("*********财务公司对外付款，暂不考虑*********");
						
					
					//代理汇兑方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
						
						
					//先拨后支方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
						
					}
    		}
				//金额，币种 等其他信息
				bankInstructionInfo.setAmount(transInfo.getRealInterest() + transInfo.getRealCompoundInterest() + transInfo.getRealOverDueInterest());				
				bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
				bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
				bankInstructionInfo.setAbstract(transInfo.getAbstract());
				bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
				bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
				bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
				bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
				colInstructionParams.add(bankInstructionInfo);
    		}
    		//委贷的收款方是活期账户时
    		if(lReceiveInterestAccountID>0){
    			BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
				//added by mzh_fu 2007/03/30 是否生成指令检验
				long lAccouontID=lReceiveInterestAccountID;

				BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingQueryInfo.setTransType(transInfo.getTransactionTypeID());
				bankInstructionSettingQueryInfo.setAccountId(lAccouontID);
				
				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
				
				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
					return null;

				/**二级户账户模式*/
				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
					//普通付款方式
					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
					{
						//逻辑：付款方：银行二级账户的上级银行账户；收款方：活期账户对应的银行二级账户
						accountInfo=dao.findByID(lReceiveInterestAccountID);	
						
	
		    			log.info("委托贷款收款方");
						//收款方为活期账户
						//取得上级账户信息
						ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
						instruction.setSystemId(1);//系统id
						instruction.setReferenceCode(accountInfo.getAccountNo());
						
						RespGetGeneralBankAcctInfo  generalbankacctinfo = new RespGetGeneralBankAcctInfo();
						generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
						if(generalbankacctinfo==null||generalbankacctinfo.getAccountInfo()==null||generalbankacctinfo.getStatus()!=1){
							throw new Exception("未找到活期账户的上级银行账户信息"); 
						}
					
						bankInstructionInfo.setPayBankAccountNO(generalbankacctinfo.getAccountInfo().getAccountNO());
						bankInstructionInfo.setReceiveReferenceCode(accountInfo.getAccountNo());
						bankInstructionInfo.setTransType(TransType.NORMAL);
						
					//代理汇兑方式	
			}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
				log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
				
				
			//先拨后支方式	
			}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
				log.info("*********先拨后支，暂不考虑*********");
				
				
			}
			
		/**门户账户模式*/
		}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
				
				//普通付款方式
				if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
					log.info("*********财务公司对外付款，暂不考虑*********");
					
				
				//代理汇兑方式	
				}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
					log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
					
					
				//先拨后支方式	
				}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
					log.info("*********先拨后支，暂不考虑*********");
					
				}
				
		/**收支两条线模式*/
		}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
				
				//普通付款方式
				if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
					log.info("*********财务公司对外付款，暂不考虑*********");
					
				
				//代理汇兑方式	
				}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
					log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
					
					
				//先拨后支方式	
				}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
					log.info("*********先拨后支，暂不考虑*********");
					
				}
    		}
				//金额，币种 等其他信息
				bankInstructionInfo.setAmount(transInfo.getRealInterest() + transInfo.getRealCompoundInterest() + transInfo.getRealOverDueInterest()-transInfo.getRealInterestTax());				
				bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
				bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
				bankInstructionInfo.setAbstract(transInfo.getAbstract());
				bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
				bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
				bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
				bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
				
				colInstructionParams.add(bankInstructionInfo);
    		}
        }
        //从付息开户行到利息收入账户号
		else if(transInfo.getInterestBankID()>0 && lReceiveInterestAccountID > 0 && (transInfo.getRealInterest() + transInfo.getRealCompoundInterest() + transInfo.getRealOverDueInterest()-transInfo.getRealInterestTax())>0)
		{
				//added by mzh_fu 2007/03/30 是否生成指令检验
				/*********************begin*****************************/
				
				BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());

				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByTransType(bankInstructionSettingQueryInfo);
				bankInstructionSettingQueryInfo.setAccountId(lReceiveInterestAccountID);
				BankInstructionSettingInfo _bankInstructionSettingInfo1= getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
				if (_bankInstructionSettingInfo.getIsSend() != Constant.TRUE
						||_bankInstructionSettingInfo1.getIsSend() != Constant.TRUE) 
				{ 
					return null;
				}
				/**二级户账户模式*/
				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL
					&&_bankInstructionSettingInfo1.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
					
					//普通付款方式
					if( _bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
						{
						//逻辑：付款方:财务公司开户行；收款方：银行二级户的上级银行账户
							log.debug("------从财务公司开户行到利息收入账户号--------");
							BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
							
							//付款方: 财务公司开户行
							Sett_BranchDAO  branchDAO = new Sett_BranchDAO();
							BranchInfo branchInfo = branchDAO.findByID(transInfo.getInterestBankID()); 
							bankInstructionInfo.setPayBankAccountNO(branchInfo.getBankAccountCode());//付款银行账户号
							bankInstructionInfo.setPayAccountName(branchInfo.getBankServiceName());//付款账户名称             
							bankInstructionInfo.setPayDepartmentName(branchInfo.getBankServiceName());//付款人单位全称
							//收方信息
							bankInstructionInfo.setReceiveReferenceCode((dao.findByID(lReceiveInterestAccountID)).getAccountNo());	
							
							bankInstructionInfo.setTransType(TransType.NORMAL);
							
	
							//金额，币种 等其他信息
							bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
							bankInstructionInfo.setAmount(transInfo.getRealInterest() + transInfo.getRealCompoundInterest() + transInfo.getRealOverDueInterest()-transInfo.getRealInterestTax());				
							bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
							bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
							bankInstructionInfo.setAbstract(transInfo.getAbstract());
							bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
							bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
							bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
							colInstructionParams.add(bankInstructionInfo);
							

							//代理汇兑方式	
						}else if (_bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
							
						}
						
						/**门户账户模式*/
					}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
								
								//普通付款方式
								if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
									log.info("*********财务公司对外付款，暂不考虑*********");
									
								
								//代理汇兑方式	
								}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
									log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
									
									
								//先拨后支方式	
								}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
									log.info("*********先拨后支，暂不考虑*********");
									
								}
								
						/**收支两条线模式*/
					}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
							
								//普通付款方式
								if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
									log.info("*********财务公司对外付款，暂不考虑*********");
									
								
								//代理汇兑方式	
								}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
									log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
									
									
								//先拨后支方式	
								}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
									log.info("*********先拨后支，暂不考虑*********");
									
								}
					}

			}
           //从活期账户收取手续费(如果存在手续费)
			if(transInfo.getCommissionAccountID() > 0 && transInfo.getRealCommission() > 0)
			{
				long lAccouontID=transInfo.getCommissionAccountID();

				BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
				bankInstructionSettingQueryInfo.setAccountId(lAccouontID);

				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
				
				if (_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
				{ 
					return null;
				}
			/**二级户账户模式*/
			if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
				
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
						{
							//逻辑：付款方: 内部活期账户对应的银行二级户；收款方：银行二级户的上级银行账户
							log.debug("------从活期账户收取手续费 从付手续费账户支出--------");
							BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
							RespGetGeneralBankAcctInfo  generalbankacctinfo = new RespGetGeneralBankAcctInfo();
				    		ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();

							instruction.setSystemId(1);//系统id
							instruction.setReferenceCode(dao.findByID(lAccouontID).getAccountNo());
							generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(instruction);//获得付息账户的上级银行账号单位
							if(generalbankacctinfo==null || generalbankacctinfo.getAccountInfo()==null)
			    			{
			    				throw new Exception("未找到活期账户对应的上级账户信息");
			    			}
							//付方信息
							bankInstructionInfo.setPayAccountNo((dao.findByID(lAccouontID)).getAccountNo());                                  
							
							//收方信息
							bankInstructionInfo.setReceiveAccountNo(generalbankacctinfo.getAccountInfo().getAccountNO());
							bankInstructionInfo.setReceiveAccountName(generalbankacctinfo.getAccountInfo().getAccountName());
							bankInstructionInfo.setReceiveReferenceCode("");
							bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctinfo.getAccountInfo().getBranchCity());
							bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctinfo.getAccountInfo().getBranchProvince());
							
							bankInstructionInfo.setReceiveBranchName(generalbankacctinfo.getAccountInfo().getBankName());
		
							bankInstructionInfo.setReceiveBankExchangeCode("");
							bankInstructionInfo.setReceiveBranchCodeOfBank("");					
							bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
							bankInstructionInfo.setTransType(TransType.NORMAL);
							//金额，币种 等其他信息
							bankInstructionInfo.setAmount(transInfo.getRealCommission());				
							bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
							bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
							bankInstructionInfo.setAbstract(transInfo.getAbstract());
							bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
							bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
							bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
							colInstructionParams.add(bankInstructionInfo);
							//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
							
						}
						
						/**门户账户模式*/
					}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
								
								//普通付款方式
								if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
									log.info("*********财务公司对外付款，暂不考虑*********");
									
								
								//代理汇兑方式	
								}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
									log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
									
									
								//先拨后支方式	
								}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
									log.info("*********先拨后支，暂不考虑*********");
									
								}
								
						/**收支两条线模式*/
					}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
							
								//普通付款方式
								if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
									log.info("*********财务公司对外付款，暂不考虑*********");
									
								
								//代理汇兑方式	
								}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
									log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
									
									
								//先拨后支方式	
								}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
									log.info("*********先拨后支，暂不考虑*********");
									
								}
					}
				}
			//从活期账户付出担保费到活期账户(付收担保费账户号都存在)
			if(transInfo.getPayInterestAccountID() >0 && lReceiveInterestAccountID > 0 && transInfo.getRealSuretyFee()>0)
			{
				BankInstructionSettingInfo bankInstructionSettingQueryInfos = new BankInstructionSettingInfo();
				bankInstructionSettingQueryInfos.setNOfficeId(param.getOfficeID());
				bankInstructionSettingQueryInfos.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingQueryInfos.setTransType(param.getTransactionTypeID());
				bankInstructionSettingQueryInfos.setAccountId(transInfo.getPayInterestAccountID());
				BankInstructionSettingInfo bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfos);
				bankInstructionSettingQueryInfos.setAccountId(lReceiveInterestAccountID);
				BankInstructionSettingInfo bankInstructionSettingInfo1 = getSettingInfoByAccountId(bankInstructionSettingQueryInfos);
				if (bankInstructionSettingInfo.getIsSend() != Constant.TRUE && bankInstructionSettingInfo1.getIsSend() != Constant.TRUE) { 
					return null;
				}
				//当双方都是二级户 只发一条指令
				if(bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL && bankInstructionSettingInfo1.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL)
				{

					
					//普通付款方式
					if(bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY
						&&bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
						//逻辑：付款方：活期账户对应的银行二级户；收款方：活期账户对应的银行二级户

						BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
						accountInfo=dao.findByID(transInfo.getPayInterestAccountID());
						bankInstructionInfo.setPayAccountNo(accountInfo.getAccountNo());
						accountInfo=dao.findByID(lReceiveInterestAccountID);
						bankInstructionInfo.setReceiveReferenceCode(accountInfo.getAccountNo());
							

						bankInstructionInfo.setTransType(TransType.NORMAL);

						//币种 等其他信息
						bankInstructionInfo.setAmount(transInfo.getRealSuretyFee());	
	    				bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
	    				bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
	    				bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
	    				bankInstructionInfo.setAbstract(transInfo.getAbstract());
	    				bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
	    				bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
	    				bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
	    				colInstructionParams.add(bankInstructionInfo);	
						
					//代理汇兑方式	
					}else if (bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT
							&&bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
						
						
					//先拨后支方式	
					}else if (bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY
							&&bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
						
						
					}
				
				}	
			else
			{
				if(transInfo.getPayInterestAccountID() >0)
				{
					BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
    				BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
    				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
    				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
    				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
    				bankInstructionSettingQueryInfo.setAccountId(transInfo.getPayInterestAccountID());
    				//付款
    				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
    				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE)
    				{
    					return null;
    				}
    				
    				AccountHome home =
    					(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
    				Account accounthome = (Account) home.create();
    				/**二级户账户模式*/
    				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
    					
    					//普通付款方式
    					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
    						//逻辑：付款方: 内部活期账户对应的银行二级户；收款方： 银行二级户对应的上级银行账户
    						//付款方
							bankInstructionInfo.setPayAccountNo((accounthome.findAccountByID(transInfo.getPayInterestAccountID())).getAccountNo());         
			    			//获得付款方账户的上级银行账号单位
							ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
							instruction.setSystemId(1);
							accountInfo = accounthome.findAccountByID(transInfo.getPayInterestAccountID());
							instruction.setReferenceCode(accountInfo.getAccountNo());
							RespGetGeneralBankAcctInfo generalbankacctInfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
							if(generalbankacctInfo==null || generalbankacctInfo.getAccountInfo()==null)
			    			{
								throw new Exception("未找到付款方账户的上级账户信息");
			    			}
							//
							//收款方
							bankInstructionInfo.setReceiveAccountNo(generalbankacctInfo.getAccountInfo().getAccountNO());//收款账户号
							bankInstructionInfo.setReceiveAccountName(generalbankacctInfo.getAccountInfo().getAccountName());//收款账户名称
							bankInstructionInfo.setReceiveBranchName(generalbankacctInfo.getAccountInfo().getBankName());//收款人开户行名称
							bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctInfo.getAccountInfo().getBranchProvince());//收款人开户行所在地省名称
							bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctInfo.getAccountInfo().getBranchCity());//收款人开户行所在地城市名称
			    			//
							bankInstructionInfo.setAmount(transInfo.getRealSuretyFee());	
    						bankInstructionInfo.setTransType(TransType.NORMAL);
    					
    					//代理汇兑方式	
    					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
    						log.info("*********财务公司代成员单位对外付款 ，暂不考虑*********");
    						
    					//先拨后支方式	
    					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
    						log.info("*********先拨后支，暂不考虑*********");
    						
    					}
    					
    				/**门户账户模式*/
    				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
    						
    						//普通付款方式
    						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
    							log.info("*********财务公司对外付款，暂不考虑*********");
    							
    						
    						//代理汇兑方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
    							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
    							
    							
    						//先拨后支方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
    							log.info("*********先拨后支，暂不考虑*********");
    							
    						}
    						
    				/**收支两条线模式*/
    				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
    						
    						//普通付款方式
    						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
    							log.info("*********财务公司对外付款，暂不考虑*********");
    							
    						
    						//代理汇兑方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
    							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
    							
    							
    						//先拨后支方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
    							log.info("*********先拨后支，暂不考虑*********");
    							
    						}
    				}
    				//币种 等其他信息
    				bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
    				bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
    				bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
    				bankInstructionInfo.setAbstract(transInfo.getAbstract());
    				bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
    				bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
    				bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
    				colInstructionParams.add(bankInstructionInfo);	
				}
				if(lReceiveInterestAccountID > 0)
				{
					BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
    				BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
    				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
    				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
    				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
    				bankInstructionSettingQueryInfo.setAccountId(lReceiveInterestAccountID);
    				
    				//收款
    				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
    				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE)
    				{
    					return null;
    				}
    				AccountHome home =
    					(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
    				Account accounthome = (Account) home.create();
    				/**二级户账户模式*/
    				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
    					
    					//普通付款方式
    					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
    						//逻辑：付款方: 银行二级户对应的上级银行账户；收款方： 内部活期账户对应的银行二级户
    						//付款方
			    			//获得收款方账户的上级银行账号单位
							ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
							instruction.setSystemId(1);
							accountInfo = accounthome.findAccountByID(lReceiveInterestAccountID);
							instruction.setReferenceCode(accountInfo.getAccountNo());
							RespGetGeneralBankAcctInfo generalbankacctInfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
							if(generalbankacctInfo==null || generalbankacctInfo.getAccountInfo()==null)
			    			{
								throw new Exception("未找到收款款方账户的上级账户信息");
			    			}
							//
							bankInstructionInfo.setPayBankAccountNO(generalbankacctInfo.getAccountInfo().getAccountNO());
							//收方信息
							bankInstructionInfo.setReceiveReferenceCode((accounthome.findAccountByID(lReceiveInterestAccountID)).getAccountNo());			
    						//
							bankInstructionInfo.setAmount(transInfo.getRealSuretyFee());	
    						bankInstructionInfo.setTransType(TransType.NORMAL);
    					
    					//代理汇兑方式	
    					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
    						log.info("*********财务公司代成员单位对外付款 ，暂不考虑*********");
    						
    					//先拨后支方式	
    					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
    						log.info("*********先拨后支，暂不考虑*********");
    						
    					}
    					
    				/**门户账户模式*/
    				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
    						
    						//普通付款方式
    						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
    							log.info("*********财务公司对外付款，暂不考虑*********");
    							
    						
    						//代理汇兑方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
    							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
    							
    							
    						//先拨后支方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
    							log.info("*********先拨后支，暂不考虑*********");
    							
    						}
    						
    				/**收支两条线模式*/
    				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
    						
    						//普通付款方式
    						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
    							log.info("*********财务公司对外付款，暂不考虑*********");
    							
    						
    						//代理汇兑方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
    							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
    							
    							
    						//先拨后支方式	
    						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
    							log.info("*********先拨后支，暂不考虑*********");
    							
    						}
    				}
    				//币种 等其他信息
    				bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
    				bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
    				bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
    				bankInstructionInfo.setAbstract(transInfo.getAbstract());
    				bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
    				bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
    				bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
    				colInstructionParams.add(bankInstructionInfo);	
				}
			}
		}
		//从开户行付出担保费到活期账户
		if (transInfo.getInterestBankID() >0 && lReceiveInterestAccountID > 0 && transInfo.getRealSuretyFee()>0)
		{
			BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
			BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
			bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
			bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
			bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
			//付款
			BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByTransType(bankInstructionSettingQueryInfo);
			if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE)
			{
				return null;
			}
			//收款
			bankInstructionSettingQueryInfo.setAccountId(lReceiveInterestAccountID);
			BankInstructionSettingInfo _bankInstructionSettingInfo1 = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
			if(_bankInstructionSettingInfo1.getIsSend() != Constant.TRUE)
			{
				return null;
			}
			AccountHome home =
				(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
			Account accounthome = (Account) home.create();
			/**二级户账户模式*/
			if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL
				&&_bankInstructionSettingInfo1.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
				
				//普通付款方式
				if(_bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
					//付款方: 财务公司开户行
					Sett_BranchDAO  branchDAO = new Sett_BranchDAO();
					BranchInfo branchInfo = branchDAO.findByID(transInfo.getInterestBankID()); 
					bankInstructionInfo.setPayBankAccountNO(branchInfo.getBankAccountCode());//付款银行账户号
					bankInstructionInfo.setPayAccountName(branchInfo.getBankServiceName());//付款账户名称             
					bankInstructionInfo.setPayDepartmentName(branchInfo.getBankServiceName());//付款人单位全称
					//收款方
					bankInstructionInfo.setReceiveReferenceCode(accounthome.findAccountByID(lReceiveInterestAccountID).getAccountNo());	
					//
					bankInstructionInfo.setAmount(transInfo.getRealSuretyFee());	
					bankInstructionInfo.setTransType(TransType.NORMAL);
				
				//代理汇兑方式	
				}else if (_bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.AGENT){
					log.info("*********财务公司代成员单位对外付款 ，暂不考虑*********");
					
				//先拨后支方式	
				}else if (_bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
					log.info("*********先拨后支，暂不考虑*********");
					
				}
				
			/**门户账户模式*/
			}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
					
					//普通付款方式
					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
						log.info("*********财务公司对外付款，暂不考虑*********");
						
					
					//代理汇兑方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
						
						
					//先拨后支方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
						
					}
					
			/**收支两条线模式*/
			}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
					
					//普通付款方式
					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
						log.info("*********财务公司对外付款，暂不考虑*********");
						
					
					//代理汇兑方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
						
						
					//先拨后支方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
						
					}
			}
			//币种 等其他信息
			bankInstructionInfo.setTransactionNo(transInfo.getTransNo());
			bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
			bankInstructionInfo.setOfficeId(transInfo.getOfficeID());
			bankInstructionInfo.setAbstract(transInfo.getAbstract());
			bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
			bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
			bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getInputUserID()));
			colInstructionParams.add(bankInstructionInfo);	
	}

		} catch (Exception e) {			
			log.info("根据交易对象形成利息费用支付活期结息指令时出错，无法形成指令！");
			e.printStackTrace();
			throw new IException(e.getMessage(),e);			
		}
		return colInstructionParams;
    }
    
    
    /**
	 * 利息费用支付活期结息指令:活期账户为收利息方
	 * 				 
	 * @param Object
	 * @throws IException
	 */
    private Collection createInterestFeePayCurrentIntruction(CreateInstructionParam param) throws IException {
    	Collection colInstructionParams = new ArrayList();
    	try {
			if(param.getObjInfo()==null)
			{
				log.info("指令信息为空");
				throw new Exception("活期利息结算指令信息为空");				
			}
    		InterestQueryResultInfo transInfo = new InterestQueryResultInfo();//总账info
    		transInfo = (InterestQueryResultInfo)param.getObjInfo();
    		
       		RespGetGeneralBankAcctInfo  generalbankacctinfo = new RespGetGeneralBankAcctInfo();
    		
    		if(transInfo.getInterest() <=0)
			{
				log.info("利息小于等于0，不发送指令");
				return null;
			}

			long lAccouontID=transInfo.getAccountID();

			BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
			bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
			bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
			bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
			bankInstructionSettingQueryInfo.setAccountId(lAccouontID);

			BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
			
			if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
			{
				return null;
			}
			/**二级户账户模式*/
			if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
				//普通付款方式
				if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
				{
					//逻辑：付款方：银行二级账户的上级银行账户；收款方：活期账户对应的银行二级账户
		    		AccountHome home =
						(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
					Account accounthome = (Account) home.create();
		  			  		
		    		//取得resp总账info
					ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
					instruction.setSystemId(1);
					instruction.setReferenceCode(accounthome.findAccountByID(transInfo.getAccountID()).getAccountNo());
					
					generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(instruction);//获得活期账户的上级银行账号单位
					
					if(generalbankacctinfo==null||generalbankacctinfo.getAccountInfo()==null||generalbankacctinfo.getStatus()!=1){
						throw new Exception("未找到活期账户的上级银行账户信息"); 
					}
					

					BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
					
					bankInstructionInfo.setPayAccountNo("");
					bankInstructionInfo.setPayBankAccountNO(generalbankacctinfo.getAccountInfo().getAccountNO());//付款银行账户号
					bankInstructionInfo.setPayAcctBankCode(generalbankacctinfo.getAccountInfo().getBankCode());//付款账户银行编号
					bankInstructionInfo.setPayAccountName(generalbankacctinfo.getAccountInfo().getAccountName());//付款账户名称             
					bankInstructionInfo.setPayDepartmentName(generalbankacctinfo.getAccountInfo().getAccountName());//付款人单位全称           
					bankInstructionInfo.setPayBranchName(generalbankacctinfo.getAccountInfo().getAccountName());//付款人开户行名称
					
					
					//收款方信息，收款方为外部银行账户
					bankInstructionInfo.setReceiveAccountNo("");
					bankInstructionInfo.setReceiveAccountName((accounthome.findAccountByID(transInfo.getAccountID())).getAccountName());
					bankInstructionInfo.setReceiveReferenceCode((accounthome.findAccountByID(transInfo.getAccountID())).getAccountNo());
					
					bankInstructionInfo.setReceiveBankExchangeCode("");
					bankInstructionInfo.setReceiveBranchCodeOfBank("");					
					bankInstructionInfo.setTransactionNo(transInfo.getExchangeNo());
					bankInstructionInfo.setTransType(TransType.NORMAL);
					//金额，币种 等其他信息
					
					log.info("协定存款户超过50万，利息增加协定利息");
					log.info("活期利息： ="+transInfo.getInterest());
					log.info("协定存款利息 "+transInfo.getNegotiateInterest());
					
					bankInstructionInfo.setAmount(transInfo.getInterest()+transInfo.getNegotiateInterest());				
					bankInstructionInfo.setCurrencyID(param.getCurrencyID());
					bankInstructionInfo.setOfficeId(param.getOfficeID());
					bankInstructionInfo.setAbstract("");
					bankInstructionInfo.setTransactionNo(param.getTransNo());
					bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
					bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
					bankInstructionInfo.setSenderNo(String.valueOf(param.getInputUserID()));
					colInstructionParams.add(bankInstructionInfo);
				//代理汇兑方式	
				}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
					log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
					
				//先拨后支方式	
				}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
					log.info("*********先拨后支，暂不考虑*********");
					
					
				}
				
			/**门户账户模式*/
			}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
					
					//普通付款方式
					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
						log.info("*********财务公司对外付款，暂不考虑*********");
						
					
					//代理汇兑方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
						
						
					//先拨后支方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
						
					}
					
			/**收支两条线模式*/
			}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
					
					//普通付款方式
					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
						log.info("*********财务公司对外付款，暂不考虑*********");
						
					
					//代理汇兑方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
						
						
					//先拨后支方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
						
					}
	    		}

		} catch (Exception e) {			
			log.info("根据交易对象形成利息费用支付活期结息指令时出错，无法形成指令！");
			e.printStackTrace();
			throw new IException(e.getMessage(),e);			
		}
		return colInstructionParams;
    }
    
    /**
	 * 利息费用支付保证金结息指令:活期账户为收利息方
	 * 				 
	 * @param Object
	 * @throws IException
	 */
    private Collection createInterestFeePayMargnIntruction(CreateInstructionParam param) throws IException {
    	Collection colInstructionParams = new ArrayList();
    	try {
			if(param.getObjInfo()==null)
			{
				log.info("指令信息为空");
				throw new Exception("活期利息结算指令信息为空");				
			}
    		InterestQueryResultInfo transInfo = new InterestQueryResultInfo();//总账info
    		transInfo = (InterestQueryResultInfo)param.getObjInfo();
    		
       		RespGetGeneralBankAcctInfo  generalbankacctinfo = new RespGetGeneralBankAcctInfo();
    		
    		if(transInfo.getInterest() <=0)
			{
				log.info("利息小于等于0，不发送指令");
				return null;
			}
    		
    		AccountHome home =
				(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
			Account accounthome = (Account) home.create();
			Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO();
			log.info("============================transInfo.getAccountID()="+transInfo.getAccountID());
			log.info("============================transInfo.getFixedDepositNo()="+transInfo.getFixedDepositNo());
			SubAccountAssemblerInfo subAccountAssemblerInfo = sett_SubAccountDAO.findByFixedDepositNo(
				transInfo.getAccountID(),transInfo.getFixedDepositNo());
			log.info("========subAccountAssemblerInfo.().getInterestAccountID()="+subAccountAssemblerInfo.getSubAccountCurrenctInfo().getInterestAccountID());
			
			if(subAccountAssemblerInfo.getSubAccountCurrenctInfo().getInterestAccountID()<0){
				log.info("利息账户ID为－1，不进行结息");
				return null;
			}
			
			//added by mzh_fu 2007/03/30 是否生成指令检验
			/*********************begin*****************************/
			long lAccouontID=subAccountAssemblerInfo.getSubAccountCurrenctInfo().getInterestAccountID();

			BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
			bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
			bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
			bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
			bankInstructionSettingQueryInfo.setAccountId(lAccouontID);

			BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
			
			if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
			{
				return null;
			}
			/**二级户账户模式*/
			if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
				//普通付款方式
				if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
				{
					//逻辑：付款方：银行二级账户的上级银行账户；收款方：活期账户对应的银行二级账户
					AccountInfo accountInfo = accounthome.findAccountByID(
							subAccountAssemblerInfo.getSubAccountCurrenctInfo().getInterestAccountID());
		    		//取得resp总账info
					ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
					instruction.setSystemId(1);
					instruction.setReferenceCode(accountInfo.getAccountNo());
					generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(instruction);//获得活期账户的上级银行账号单位
					
					if(generalbankacctinfo==null||generalbankacctinfo.getAccountInfo()==null||generalbankacctinfo.getStatus()!=1){
						throw new Exception("未找到活期账户的上级银行账户信息"); 
					}
					
					BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
						
					bankInstructionInfo.setPayAccountNo("");
					bankInstructionInfo.setPayBankAccountNO(generalbankacctinfo.getAccountInfo().getAccountNO());//付款银行账户号
					bankInstructionInfo.setPayAcctBankCode(generalbankacctinfo.getAccountInfo().getBankCode());//付款账户银行编号
					bankInstructionInfo.setPayAccountName(generalbankacctinfo.getAccountInfo().getAccountName());//付款账户名称             
					bankInstructionInfo.setPayDepartmentName(generalbankacctinfo.getAccountInfo().getAccountName());//付款人单位全称           
					bankInstructionInfo.setPayBranchName(generalbankacctinfo.getAccountInfo().getAccountName());//付款人开户行名称

					//收款方信息
					bankInstructionInfo.setReceiveAccountNo("");
					bankInstructionInfo.setReceiveAccountName(accountInfo.getAccountName());
					bankInstructionInfo.setReceiveReferenceCode(accountInfo.getAccountNo());
					
					bankInstructionInfo.setReceiveBankExchangeCode("");
					bankInstructionInfo.setReceiveBranchCodeOfBank("");					
					bankInstructionInfo.setTransactionNo(transInfo.getExchangeNo());
					bankInstructionInfo.setTransType(TransType.NORMAL);
					//金额，币种 等其他信息
					bankInstructionInfo.setAmount(transInfo.getInterest());				
					bankInstructionInfo.setCurrencyID(param.getCurrencyID());
					bankInstructionInfo.setOfficeId(param.getOfficeID());
					bankInstructionInfo.setAbstract("");
					bankInstructionInfo.setTransactionNo(param.getTransNo());
					bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
					bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
					bankInstructionInfo.setSenderNo(String.valueOf(param.getInputUserID()));
					colInstructionParams.add(bankInstructionInfo);
					//代理汇兑方式	
				}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
					log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
					
				//先拨后支方式	
				}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
					log.info("*********先拨后支，暂不考虑*********");
					
					
				}
				
			/**门户账户模式*/
			}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
					
					//普通付款方式
					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
						log.info("*********财务公司对外付款，暂不考虑*********");
						
					
					//代理汇兑方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
						
						
					//先拨后支方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
						
					}
					
			/**收支两条线模式*/
			}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
					
					//普通付款方式
					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
						log.info("*********财务公司对外付款，暂不考虑*********");
						
					
					//代理汇兑方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
						
						
					//先拨后支方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
						
					}
	    		}

			
		} catch (Exception e) {			
			log.info("根据交易对象形成利息费用支付保证金结息指令时出错，无法形成指令！");
			e.printStackTrace();
			throw new IException(e.getMessage(),e);			
		}
		return colInstructionParams;
    }
    
    /**
	 * 利息费用支付自营贷款结息:收利息方
	 * 				 
	 * @param Object
	 * @throws IException
	 */
    private Collection createTrustLoanInsterestIntruction(CreateInstructionParam param) throws IException {
    	Collection colInstructionParams = new ArrayList();
    	try {
			
    		InterestQueryResultInfo info = new InterestQueryResultInfo();//总账info
    		info = (InterestQueryResultInfo)param.getObjInfo();
    		
       		RespGetGeneralBankAcctInfo  generalbankacctinfo = new RespGetGeneralBankAcctInfo();
    		BankInstructionOperation bankInstructionOperation = new BankInstructionOperation();//实现info转换成银企接口info

    		AccountHome home =
				(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
			Account accounthome = (Account) home.create();

			log.debug("-----------开始生成利息费用支付自营贷款结息---------------");
			
			log.info("利息费用支付自营贷款结息对象："+UtilOperation.dataentityToString(info));
	
			//从付息账户收回利息
			if(info.getPayInterestAccountID()>0 && (info.getInterest() > 0 ||info.getCompoundInterest()>0 || info.getForfeitInterest()>0) && (info.getInterestType() == SETTConstant.InterestFeeType.INTEREST || info.getInterestType() == SETTConstant.InterestFeeType.COMPOUNDINTEREST || info.getInterestType() == SETTConstant.InterestFeeType.FORFEITINTEREST))
			{
				
				//added by mzh_fu 2007/03/30 是否生成指令检验
				/*********************begin*****************************/
				long lAccouontID=info.getPayInterestAccountID();

				BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
				bankInstructionSettingQueryInfo.setAccountId(lAccouontID);

				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
				{
					return null;
				}
				/**二级户账户模式*/
				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
					//普通付款方式
					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
					{
						//逻辑：付款方：活期账户对应的银行二级账户；收款方：银行二级账户的上级银行账户
						log.debug("------从付息账户收回利息分笔处理--------");
						BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
						ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
						instruction.setSystemId(1);
						instruction.setReferenceCode(accounthome.findAccountByID(info.getPayInterestAccountID()).getAccountNo());
						generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(instruction);//获得付息账户的上级银行账号单位
						if(generalbankacctinfo==null ||generalbankacctinfo.getAccountInfo()==null)
						{
							throw new Exception("未找到活期账户上级银行账户信息");
						}
						//付方信息
						bankInstructionInfo.setPayAccountNo((accounthome.findAccountByID(info.getPayInterestAccountID())).getAccountNo());
						bankInstructionInfo.setPayBankAccountNO("");//付款银行账户号
						bankInstructionInfo.setPayAcctBankCode("");//付款账户银行编号
						bankInstructionInfo.setPayAccountName((accounthome.findAccountByID(info.getPayInterestAccountID())).getAccountName());//付款账户名称             
						bankInstructionInfo.setPayDepartmentName((accounthome.findAccountByID(info.getPayInterestAccountID())).getAccountName());//付款人单位全称           
						bankInstructionInfo.setPayBranchName("");//付款人开户行名称
						
						//收方信息
						bankInstructionInfo.setReceiveAccountNo(generalbankacctinfo.getAccountInfo().getAccountNO());
						bankInstructionInfo.setReceiveAccountName(generalbankacctinfo.getAccountInfo().getAccountName());
						bankInstructionInfo.setReceiveReferenceCode("");
						bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctinfo.getAccountInfo().getBranchCity());
						bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctinfo.getAccountInfo().getBranchProvince());
						
						//bankInstructionInfo.setReceiveDepartmentName(info.getExtClientName());
						bankInstructionInfo.setReceiveBranchName(generalbankacctinfo.getAccountInfo().getBankName());

						bankInstructionInfo.setReceiveBankExchangeCode("");
						bankInstructionInfo.setReceiveBranchCodeOfBank("");					
						bankInstructionInfo.setTransactionNo(param.getTransNo());
						bankInstructionInfo.setTransType(TransType.NORMAL);
						//金额，币种 等其他信息
						if(info.getInterestType() == SETTConstant.InterestFeeType.INTEREST)
						{
							bankInstructionInfo.setAmount(info.getInterest() );	
						}
						else if(info.getInterestType() == SETTConstant.InterestFeeType.COMPOUNDINTEREST )
						{
							bankInstructionInfo.setAmount(info.getCompoundInterest() );	
						}
						else if(info.getInterestType() == SETTConstant.InterestFeeType.FORFEITINTEREST )
						{
							bankInstructionInfo.setAmount(info.getForfeitInterest() );	
						}
						bankInstructionInfo.setCurrencyID(param.getCurrencyID());
						bankInstructionInfo.setOfficeId(param.getOfficeID());
						bankInstructionInfo.setAbstract("");
						bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
						bankInstructionInfo.setSenderNo(String.valueOf(param.getInputUserID()));
						bankInstructionInfo.setCreateTime( Env.getSystemDateTime());//指令创建时间
						bankInstructionInfo.setSendTime( Env.getSystemDateTime());//指令发送时间
						bankInstructionInfo.setCancelTime(new Timestamp(0));//指令撤销时间
						bankInstructionInfo.setModifyTime( Env.getSystemDateTime());//指令修改时间
						bankInstructionInfo.setStatusID(SETTConstant.BankInstructionStatus.SAVED);//指令状态
						colInstructionParams.add(bankInstructionInfo);
						//代理汇兑方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
						
					//先拨后支方式	
					}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
						
						
					}
					
				/**门户账户模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
						
				/**收支两条线模式*/
				}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
						
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
							log.info("*********财务公司对外付款，暂不考虑*********");
							
						
						//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
						}
		    		}
				
			}
			
			//从活期账户付出担保费到活期账户(如果担保费存在)
			if(info.getAssuranceCharge() > 0 && info.getInterestType() == SETTConstant.InterestFeeType.ASSURE)
			{
				
				log.info("------从活期账户付出担保费到活期账户(付收担保费账户号都存在)--------");
				
				
				Sett_SubAccountDAO subAccountDao=new Sett_SubAccountDAO();
				SubAccountAssemblerInfo subAccountInfo=subAccountDao.findByID(info.getSubAccountID());
				//added by mzh_fu 2007/03/30 是否生成指令检验
				/*********************begin*****************************/
				BankInstructionSettingInfo bankInstructionSettingQueryInfos = new BankInstructionSettingInfo();
				bankInstructionSettingQueryInfos.setNOfficeId(param.getOfficeID());
				bankInstructionSettingQueryInfos.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingQueryInfos.setTransType(param.getTransactionTypeID());
				bankInstructionSettingQueryInfos.setAccountId(subAccountInfo.getSubAccountLoanInfo().getPaySuretyAccountID());
				BankInstructionSettingInfo bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfos);
				bankInstructionSettingQueryInfos.setAccountId(subAccountInfo.getSubAccountLoanInfo().getReceiveSuretyAccountID());
				BankInstructionSettingInfo bankInstructionSettingInfo1 = getSettingInfoByAccountId(bankInstructionSettingQueryInfos);
				if (bankInstructionSettingInfo.getIsSend() != Constant.TRUE && bankInstructionSettingInfo1.getIsSend() != Constant.TRUE) { 
					return null;
				}
				//当双方都是二级户 只发一条指令
				if(bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL && bankInstructionSettingInfo1.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL)
				{

					
					//普通付款方式
					if(bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY
						&&bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
						//逻辑：付款方：活期账户对应的银行二级户；收款方：活期账户对应的银行二级户

						BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
						AccountInfo accountInfo=new AccountInfo();
						Sett_AccountDAO dao=new Sett_AccountDAO();			
						accountInfo=dao.findByID(subAccountInfo.getSubAccountLoanInfo().getPaySuretyAccountID());
						bankInstructionInfo.setPayAccountNo(accountInfo.getAccountNo());
						accountInfo=dao.findByID(subAccountInfo.getSubAccountLoanInfo().getReceiveSuretyAccountID());
						bankInstructionInfo.setReceiveReferenceCode(accountInfo.getAccountNo());
							

						bankInstructionInfo.setTransType(TransType.NORMAL);
						//金额，币种 等其他信息
						bankInstructionInfo.setTransactionNo(param.getTransNo());
						bankInstructionInfo.setAmount(info.getAssuranceCharge());				
						bankInstructionInfo.setCurrencyID(param.getCurrencyID());
						bankInstructionInfo.setOfficeId(param.getOfficeID());
						bankInstructionInfo.setAbstract("");
						bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
						bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
						bankInstructionInfo.setSenderNo(String.valueOf(param.getInputUserID()));
						colInstructionParams.add(bankInstructionInfo);
						
						
					//代理汇兑方式	
					}else if (bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT
							&&bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.AGENT){
						log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
						
						
					//先拨后支方式	
					}else if (bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY
							&&bankInstructionSettingInfo1.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
						log.info("*********先拨后支，暂不考虑*********");
						
						
					}
				
				}	
			else
			{
				if(subAccountInfo.getSubAccountLoanInfo().getPaySuretyAccountID()>0)
				{
					BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
					long lAccouontID=subAccountInfo.getSubAccountLoanInfo().getPaySuretyAccountID();

					BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
					bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
					bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
					bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
					bankInstructionSettingQueryInfo.setAccountId(lAccouontID);

					BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
					if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
					{
						return null;
					}
					/**二级户账户模式*/
					if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
						{
							//逻辑：付款方：活期账户对应银行二级账户；收款方：银行二级账户的上级银行账户
							AccountInfo accountInfo=new AccountInfo();
							Sett_AccountDAO dao=new Sett_AccountDAO();			
							accountInfo=dao.findByID(subAccountInfo.getSubAccountLoanInfo().getPaySuretyAccountID());
							//added by mzh_fu 2007/03/30 是否生成指令检验
							bankInstructionInfo.setPayAccountNo(accountInfo.getAccountNo());
							
							//获得付款方账户的上级银行账号单位
							ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
							instruction.setSystemId(1);
							AccountInfo payAccountInfo = accounthome.findAccountByID(lAccouontID);
							instruction.setReferenceCode(payAccountInfo.getAccountNo());
							RespGetGeneralBankAcctInfo generalbankacctinfoPay = BPClientAgent.getGeneralBankAcctInfo(instruction);
							if(generalbankacctinfoPay == null || generalbankacctinfoPay.getAccountInfo()==null)
							{
								throw new Exception("未找到付款方账户的上级账户信息");
							}
							//收款方信息
							bankInstructionInfo.setReceiveAccountNo(generalbankacctinfoPay.getAccountInfo().getAccountNO());//收款账户号
							bankInstructionInfo.setReceiveAccountName(generalbankacctinfoPay.getAccountInfo().getAccountName());//收款账户名称
							bankInstructionInfo.setReceiveBranchName(generalbankacctinfoPay.getAccountInfo().getBankName());//收款人开户行名称
							bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctinfoPay.getAccountInfo().getBranchProvince());//收款人开户行所在地省名称
							bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctinfoPay.getAccountInfo().getBranchCity());//收款人开户行所在地城市名称
							
							bankInstructionInfo.setTransType(TransType.NORMAL);
							//金额，币种 等其他信息
							bankInstructionInfo.setTransactionNo(param.getTransNo());
							bankInstructionInfo.setAmount(info.getAssuranceCharge());				
							bankInstructionInfo.setCurrencyID(param.getCurrencyID());
							bankInstructionInfo.setOfficeId(param.getOfficeID());
							bankInstructionInfo.setAbstract("");
							bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
							bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
							bankInstructionInfo.setSenderNo(String.valueOf(param.getInputUserID()));
							colInstructionParams.add(bankInstructionInfo);
							//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
							
						}
						
					/**门户账户模式*/
					}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
							
							//普通付款方式
							if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
								log.info("*********财务公司对外付款，暂不考虑*********");
								
							
							//代理汇兑方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
								log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
								
								
							//先拨后支方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
								log.info("*********先拨后支，暂不考虑*********");
								
							}
							
					/**收支两条线模式*/
					}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
							
							//普通付款方式
							if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
								log.info("*********财务公司对外付款，暂不考虑*********");
								
							
							//代理汇兑方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
								log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
								
								
							//先拨后支方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
								log.info("*********先拨后支，暂不考虑*********");
								
							}
			    		}
				}
				if(subAccountInfo.getSubAccountLoanInfo().getReceiveSuretyAccountID()>0)
				{
					BankInstructionInfo bankInstructionInfo=new BankInstructionInfo();
					long lAccouontID=subAccountInfo.getSubAccountLoanInfo().getReceiveSuretyAccountID();

					BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
					bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
					bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
					bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
					bankInstructionSettingQueryInfo.setAccountId(lAccouontID);

					BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
					if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
					{
						return null;
					}
					/**二级户账户模式*/
					if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
						//普通付款方式
						if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
						{
							//逻辑：付款方：银行二级账户的上级银行账户；收款方：活期账户对应的银行二级账户
							AccountInfo accountInfo=new AccountInfo();
							Sett_AccountDAO dao=new Sett_AccountDAO();	
							//获得付款方账户的上级银行账号单位
							ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
							instruction.setSystemId(1);
							AccountInfo payAccountInfo = accounthome.findAccountByID(lAccouontID);
							instruction.setReferenceCode(payAccountInfo.getAccountNo());
							RespGetGeneralBankAcctInfo generalbankacctinfoPay = BPClientAgent.getGeneralBankAcctInfo(instruction);
							if(generalbankacctinfoPay == null || generalbankacctinfoPay.getAccountInfo()==null)
							{
								throw new Exception("未找到付款方账户的上级账户信息");
							}
							
							accountInfo=dao.findByID(subAccountInfo.getSubAccountLoanInfo().getReceiveSuretyAccountID());
							bankInstructionInfo.setPayBankAccountNO(generalbankacctinfoPay.getAccountInfo().getAccountNO());	
							bankInstructionInfo.setReceiveReferenceCode(accountInfo.getAccountNo());
							
							accountInfo=dao.findByID(subAccountInfo.getSubAccountLoanInfo().getPaySuretyAccountID());
							
							//added by mzh_fu 2007/03/30 是否生成指令检验
							bankInstructionInfo.setPayAccountNo(accountInfo.getAccountNo());
							
							bankInstructionInfo.setTransType(TransType.NORMAL);
							//金额，币种 等其他信息
							bankInstructionInfo.setTransactionNo(param.getTransNo());
							bankInstructionInfo.setAmount(info.getAssuranceCharge());				
							bankInstructionInfo.setCurrencyID(param.getCurrencyID());
							bankInstructionInfo.setOfficeId(param.getOfficeID());
							bankInstructionInfo.setAbstract("");
							bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
							bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
							bankInstructionInfo.setSenderNo(String.valueOf(param.getInputUserID()));
							colInstructionParams.add(bankInstructionInfo);
							//代理汇兑方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
							log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
							
						//先拨后支方式	
						}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
							log.info("*********先拨后支，暂不考虑*********");
							
							
						}
						
					/**门户账户模式*/
					}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
							
							//普通付款方式
							if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
								log.info("*********财务公司对外付款，暂不考虑*********");
								
							
							//代理汇兑方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
								log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
								
								
							//先拨后支方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
								log.info("*********先拨后支，暂不考虑*********");
								
							}
							
					/**收支两条线模式*/
					}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
							
							//普通付款方式
							if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
								log.info("*********财务公司对外付款，暂不考虑*********");
								
							
							//代理汇兑方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
								log.info("*********财务公司代成员单位对外付款，暂不考虑*********");
								
								
							//先拨后支方式	
							}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
								log.info("*********先拨后支，暂不考虑*********");
								
							}
			    		}
				}
			}
				
			}
		} catch (Exception e) {			
			log.info("根据交易对象形成利息费用支付活期结息指令时出错，无法形成指令！");
			e.printStackTrace();
			throw new IException(e.getMessage(),e);			
		}
		return colInstructionParams;
    }
    
    
    /**
	 * 利息费用支付委贷结息:收利息方
	 * 				 
	 * @param Object
	 * @throws IException
	 */
    private Collection createConsignLoanInterestntruction(CreateInstructionParam param) throws IException {
    	Collection colInstructionParams = new ArrayList();
    	try {
    		if (param.getObjInfo()==null){
    			log.info("指令信息为空");
    			throw new Exception("指令信息为空，无法生成银行指令");
    		}
			log.info("委托贷款结息处理");
    		InterestQueryResultInfo transInfo = new InterestQueryResultInfo();//总账info
    		transInfo = (InterestQueryResultInfo)param.getObjInfo();

    		log.debug("-----------开始生成委托贷款结息指令---------------");
			
			log.info("委托贷款结息指令对象："+UtilOperation.dataentityToString(transInfo));

			
    		AccountInfo accountInfo=new AccountInfo();
			Sett_AccountDAO dao=new Sett_AccountDAO();
			
			
			if(transInfo.getPayInterestAccountID()>0)
			{
				BankInstructionInfo bankInstructionInfo = new BankInstructionInfo();
				long lAccouontID=transInfo.getPayInterestAccountID();

				BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
				bankInstructionSettingQueryInfo.setAccountId(lAccouontID);

				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
				{
					return null;
				}
				/**二级户账户模式*/
				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
					//普通付款方式
					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
					{
						//逻辑：付款方：活期账户对应的银行二级账户；收款方：银行二级账户的上级银行账户
						accountInfo=dao.findByID(transInfo.getPayInterestAccountID());		
						
						bankInstructionInfo.setPayAccountNo(accountInfo.getAccountNo());
						if(transInfo.getPayInterestAccountID()>0 && (transInfo.getInterest()>0 || transInfo.getCompoundInterest()>0 || transInfo.getForfeitInterest()>0)){
															
							//收款方信息
							ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
							RespGetGeneralBankAcctInfo  generalbankacctinfo = new RespGetGeneralBankAcctInfo();
							//收款方信息，收款方为外部银行账户					
							instruction=new ReqGetGeneralBankAcctInfo();
							generalbankacctinfo = new RespGetGeneralBankAcctInfo();
							instruction.setSystemId(1);//系统id
							instruction.setReferenceCode(bankInstructionInfo.getPayAccountNo());	
							generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
							
							if(generalbankacctinfo==null||generalbankacctinfo.getAccountInfo()==null||generalbankacctinfo.getStatus()!=1){
								throw new Exception("未找到活期账户的上级银行账户信息");
								
							}
							bankInstructionInfo.setReceiveAccountNo(generalbankacctinfo.getAccountInfo().getAccountNO());
							bankInstructionInfo.setReceiveAccountName(generalbankacctinfo.getAccountInfo().getAccountName());
							bankInstructionInfo.setReceiveBranchCodeOfBank(generalbankacctinfo.getAccountInfo().getBankCode());
							bankInstructionInfo.setReceiveBranchName(generalbankacctinfo.getAccountInfo().getBankName());
							bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctinfo.getAccountInfo().getBranchProvince());
							bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctinfo.getAccountInfo().getBranchCity());
								
								
							//金额，币种 等其他信息
//							金额，币种 等其他信息
							if(transInfo.getInterestType() == SETTConstant.InterestFeeType.INTEREST)
							{
								bankInstructionInfo.setAmount(transInfo.getInterest() );	
							}
							else if(transInfo.getInterestType() == SETTConstant.InterestFeeType.COMPOUNDINTEREST )
							{
								bankInstructionInfo.setAmount(transInfo.getCompoundInterest() );	
							}
							else if(transInfo.getInterestType() == SETTConstant.InterestFeeType.FORFEITINTEREST )
							{
								bankInstructionInfo.setAmount(transInfo.getForfeitInterest() );	
							}
							
						}
						else if (transInfo.getHandlingCharge()>0){
							log.info("委托贷款手续费结息");				

							//收款方信息，收款方为外部银行账户
							//取得resp总账info
							ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
							RespGetGeneralBankAcctInfo  generalbankacctinfo = new RespGetGeneralBankAcctInfo();
							instruction.setSystemId(1);//系统id
							instruction.setReferenceCode(accountInfo.getAccountNo());	
							generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
							if(generalbankacctinfo==null||generalbankacctinfo.getAccountInfo()==null||generalbankacctinfo.getStatus()!=1){
								throw new Exception("未找到活期账户的上级银行账户信息"); 
							}
							
							bankInstructionInfo.setReceiveAccountNo(generalbankacctinfo.getAccountInfo().getAccountNO());
							bankInstructionInfo.setReceiveAccountName(generalbankacctinfo.getAccountInfo().getAccountName());
							bankInstructionInfo.setReceiveBranchCodeOfBank(generalbankacctinfo.getAccountInfo().getBankCode());
							bankInstructionInfo.setReceiveBranchName(generalbankacctinfo.getAccountInfo().getBankName());
							bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctinfo.getAccountInfo().getBranchProvince());
							bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctinfo.getAccountInfo().getBranchCity());
							//金额，币种 等其他信息
							bankInstructionInfo.setAmount(transInfo.getHandlingCharge());				
						}
						else
						{
							log.info("金额为0，不生成指令");
							return null;
						}
						bankInstructionInfo.setTransType(TransType.NORMAL);
						bankInstructionInfo.setTransactionNo(param.getTransNo());
						bankInstructionInfo.setCurrencyID(param.getCurrencyID());
						bankInstructionInfo.setOfficeId(param.getOfficeID());
						bankInstructionInfo.setAbstract("");
						bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
						bankInstructionInfo.setSenderNo(String.valueOf(param.getInputUserID()));//指令发送人no
						bankInstructionInfo.setCreateTime( Env.getSystemDateTime());//指令创建时间
						bankInstructionInfo.setSendTime( Env.getSystemDateTime());//指令发送时间
						bankInstructionInfo.setCancelTime(new Timestamp(0));//指令撤销时间
						bankInstructionInfo.setModifyTime( Env.getSystemDateTime());//指令修改时间
						bankInstructionInfo.setStatusID(SETTConstant.BankInstructionStatus.SAVED);//指令状态
						colInstructionParams.add(bankInstructionInfo);
					}
				}
					
			}
			if(transInfo.getRecieveInterestAccountID()>0)
			{
				BankInstructionInfo bankInstructionInfo = new BankInstructionInfo();
				long lAccouontID=transInfo.getRecieveInterestAccountID();

				BankInstructionSettingInfo bankInstructionSettingQueryInfo = new BankInstructionSettingInfo();
				bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
				bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
				bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
				bankInstructionSettingQueryInfo.setAccountId(lAccouontID);

				BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
				if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
				{
					return null;
				}
				/**二级户账户模式*/
				if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
					//普通付款方式
					if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY)
					{
						//逻辑：付款方：银行二级账户的上级银行账户；收款方：活期账户对应的银行二级账户
						
						if(transInfo.getRecieveInterestAccountID()>0 && (transInfo.getInterest()>0|| transInfo.getCompoundInterest()>0 || transInfo.getForfeitInterest()>0) ){
															
							accountInfo=dao.findByID(transInfo.getRecieveInterestAccountID());							
							
							ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
							RespGetGeneralBankAcctInfo  generalbankacctinfo = new RespGetGeneralBankAcctInfo();
							instruction.setSystemId(1);//系统id
							instruction.setReferenceCode(accountInfo.getAccountNo());	
							generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
							
							if(generalbankacctinfo==null||generalbankacctinfo.getAccountInfo()==null||generalbankacctinfo.getStatus()!=1){
								throw new Exception("未找到活期账户的上级银行账户信息");
								
							}
							
							bankInstructionInfo.setPayBankAccountNO(generalbankacctinfo.getAccountInfo().getAccountNO());
							//收款方信息
							bankInstructionInfo.setReceiveReferenceCode(accountInfo.getAccountNo());
							//金额，币种 等其他信息
							if(transInfo.getInterestType() == SETTConstant.InterestFeeType.INTEREST)
							{
								bankInstructionInfo.setAmount(transInfo.getInterest()-transInfo.getInterestTaxCharge() );	
							}
							else if(transInfo.getInterestType() == SETTConstant.InterestFeeType.COMPOUNDINTEREST )
							{
								bankInstructionInfo.setAmount(transInfo.getCompoundInterest() );	
							}
							else if(transInfo.getInterestType() == SETTConstant.InterestFeeType.FORFEITINTEREST )
							{
								bankInstructionInfo.setAmount(transInfo.getForfeitInterest() );	
							}
							
						}
						else if (transInfo.getHandlingCharge()>0){
							log.info("委托贷款手续费结息");				

							//收款方信息，收款方为外部银行账户
							accountInfo=dao.findByID(transInfo.getRecieveInterestAccountID());	
							ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
							RespGetGeneralBankAcctInfo  generalbankacctinfo = new RespGetGeneralBankAcctInfo();
							instruction.setSystemId(1);//系统id
							instruction.setReferenceCode(accountInfo.getAccountNo());	
							generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(instruction);
							if(generalbankacctinfo==null||generalbankacctinfo.getAccountInfo()==null||generalbankacctinfo.getStatus()!=1){
								throw new Exception("未找到活期账户的上级银行账户信息"); 
							}
							bankInstructionInfo.setPayBankAccountNO(generalbankacctinfo.getAccountInfo().getAccountNO());
							//收款方信息
							bankInstructionInfo.setReceiveReferenceCode(accountInfo.getAccountNo());
							//金额，币种 等其他信息
							bankInstructionInfo.setAmount(transInfo.getHandlingCharge());				
						}
						else
						{
							log.info("金额为0，不生成指令");
							return null;
						}
						bankInstructionInfo.setTransType(TransType.NORMAL);
						bankInstructionInfo.setTransactionNo(param.getTransNo());
						bankInstructionInfo.setCurrencyID(param.getCurrencyID());
						bankInstructionInfo.setOfficeId(param.getOfficeID());
						bankInstructionInfo.setAbstract("");
						bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
						bankInstructionInfo.setSenderNo(String.valueOf(param.getInputUserID()));//指令发送人no
						bankInstructionInfo.setCreateTime( Env.getSystemDateTime());//指令创建时间
						bankInstructionInfo.setSendTime( Env.getSystemDateTime());//指令发送时间
						bankInstructionInfo.setCancelTime(new Timestamp(0));//指令撤销时间
						bankInstructionInfo.setModifyTime( Env.getSystemDateTime());//指令修改时间
						bankInstructionInfo.setStatusID(SETTConstant.BankInstructionStatus.SAVED);//指令状态
						colInstructionParams.add(bankInstructionInfo);
						}
					}
					
				}
				
		} catch (Exception e) {			
			log.info("根据交易对象形成利息费用支付活期结息指令时出错，无法形成指令！");
			e.printStackTrace();
			throw new IException(e.getMessage(),e);			
		}
		return colInstructionParams;
    }

    /**
	 * 从结算模块发送交易付款指令到银行
	 * @param bankInstructionInfo
	 * @throws Exception
	 */
	private void sendSettModuleToBank(BankInstructionInfo bankInstructionInfo)
			throws Exception {
		Context jndiContext = null;
		QueueConnectionFactory queueConnectionFactory = null;
		QueueConnection queueConnection = null;
		QueueSession queueSession = null;
		Queue queue = null;
		QueueSender queueSender = null;
		ObjectMessage message = null;
		final int NUM_MSGS = 1;
		
		// 以下发送消息bean, 不能再有任何数据库jdbc或EJB的调用。
		System.out.println(" ===== Message queue is sending ....pls wait.");
		try {
			jndiContext = new InitialContext();
		} catch (NamingException e) {
			System.out.println("Could not create JNDI " + "context: "
					+ e.toString());
			throw new Exception();
		}

		try {
			queueConnectionFactory = (QueueConnectionFactory) jndiContext
					.lookup("java:comp/env/jms/MyQcf");
			queue = (Queue) jndiContext.lookup("java:comp/env/jms/MyQueue");
		} catch (NamingException e) {
			System.out.println("JNDI lookup failed: " + e.toString());
			throw new Exception();
		}

		try {
			queueConnection = queueConnectionFactory.createQueueConnection();
			queueSession = queueConnection.createQueueSession(false,
					Session.AUTO_ACKNOWLEDGE);
			queueSender = queueSession.createSender(queue);
			message = queueSession.createObjectMessage();			
			message.setObject(bankInstructionInfo);

			queueSender.send(message);

			System.out.println(" Message queue had been sent.");

		} catch (Throwable e) {
			System.out.println("Exception occurred: " + e.toString());
			throw new Exception();
		} finally {
			if (queueConnection != null) {
				try {
					queueSender.close();
					queueSession.close();
					queueConnection.close();

					System.out.println("closed queueConnection resources");
				} catch (JMSException e) {
				}
			} // if

		} // finally
	}

    /**
     * 拦截器,在进行相关操作前的检查
     * @return 
     */
	public boolean intercept() {
		return false;
	}

	/**
	    * 取消复核:实现机制:只有此交易号对应的指令全部失败或已撤销才能够取消复核
	    * @param stransNo 交易号
	    */
	   public void cancelCheck(String stransNo) throws IException 
	   {
	     if (Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID,false) != true )
	   return ;
	     Log.print("-----------进入检查此交易指令状态方法:cancelCheck----------------");
	     BankInstructionInfo[] bankInstructionInfo = null;
	  try
	  {
	   //BankInstruction bankInstrction = new BankInstruction();
	  	//调用接口方法,传交易号,返回对象
	    Log.print("-------------------根据交易号查询银行指令TransNo:"+ stransNo +"-----------------------");
	    ReqQueryInstructionInfo info=new ReqQueryInstructionInfo();
		ReqQueryInstructionInfo.ConditionInfo conditioInfo=info.new ConditionInfo();
		conditioInfo.setTransNO(stransNo);
		/**conditionInfo中给出查询条件**/
		ReqQueryInstructionInfo.ConditionInfo[] conditionInfos=new ReqQueryInstructionInfo.ConditionInfo[1];
		conditionInfos[0]=conditioInfo;
		info.setSystemId(1);
		info.setConditions(conditionInfos);
		
		RespQueryInstructionInfo respinfo = BPClientAgent.queryInstruction(info);
		if (respinfo != null) {
			RespQueryInstructionInfo.ResultInfo[] resultInfo = respinfo.getResults();
			if(resultInfo != null)
			{
				for(int i = 0;i < resultInfo.length; i++)
				{
					if (resultInfo[i].getStatus() != 0 && resultInfo[i].getStatus() != 10)//不是辙消状态
					{
						 throw new IException("存在未撤销的指令,无法取消复核");
					}
				}
			}
		}
	  }catch(Exception e)
	  {
	   Log.print("根据交易号查询指令时出现异常"+e.getMessage());
	   throw new IException("存在未撤销的指令,无法取消复核", e);
	  }
	  Log.print("-------------------查询指令结束-----------------------");
	}
	   /**
		 * 转贴现业务财务交易形成指令
		 * 
		 * @param Object
		 * @throws IException
		 */
	    private Collection createGrantTransDiscountIntruction(CreateInstructionParam param) throws IException {
	    	Collection colInstructionParams = new ArrayList();
	    	try {
	    		TransDiscountDetailInfo transInfo = new TransDiscountDetailInfo();
				transInfo = (TransDiscountDetailInfo)param.getObjInfo();				
				Sett_BranchDAO  branchDAO = new Sett_BranchDAO();					
				AccountHome home =
					(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
				Account accounthome = (Account) home.create();
				log.debug("-----------开始生成转贴现业务银行指令---------------");
				BranchInfo bankInfo = null;				
				if(transInfo.getNBankID()>0 ) { 
					
					log.debug("-----------开户行对外部帐户---------------");			 
	    			BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
					bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
					bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
					bankInstructionSettingQueryInfo.setTransType(transInfo.getNTransactionTypeID());
	    			
					if(getSettingInfoByTransType(bankInstructionSettingQueryInfo).getIsSend() != Constant.TRUE){ 
						return null;
					}				 
				 	BranchInfo branchInfo_pay = new BranchInfo();
				 	BranchInfo branchInfo_receive = new BranchInfo();
					branchInfo_pay = branchDAO.findByID(transInfo.getNBankID());
				
					BankInstructionInfo bankInstructionInfo = new BankInstructionInfo();

					bankInstructionInfo.setPayAccountNo("");//付款账户号(付款关联号)
					bankInstructionInfo.setPayBankAccountNO(branchInfo_pay.getBankAccountCode());//付款银行账户号
					bankInstructionInfo.setPayAcctBankCode(branchInfo_pay.getBranchCode()); //付款账户银行编号
					
					Bs_BankAccountInfoDAO bdao = new Bs_BankAccountInfoDAO();
					//取开户行设置对应的账户名称 modify by zcwang 2009-03-25
					//bankInstructionInfo.setPayAccountName(accname);//付款账户名称    				
					bankInstructionInfo.setPayAccountName(branchInfo_pay.getEnterpriseName());//付款账户名称	
					bankInstructionInfo.setPayDepartmentName(branchInfo_pay.getBankServiceName());//付款人单位全称           
					bankInstructionInfo.setAmount(transInfo.getMDiscountBillAmount()-transInfo.getMInterest());//金额 
				               
					bankInstructionInfo.setStrAbstract(transInfo.getSAbstract());//摘要                     
					bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getNInputUserID()));//指令发送人no             
					bankInstructionInfo.setCancellerNo("");//指令撤销人no 
					bankInstructionInfo.setCreateTime( Env.getSystemDateTime());//指令创建时间            
					bankInstructionInfo.setSendTime( Env.getSystemDateTime());//指令发送时间             
					bankInstructionInfo.setCancelTime(new Timestamp(0));//指令撤销时间             
					bankInstructionInfo.setModifyTime( Env.getSystemDateTime());//指令修改时间
					bankInstructionInfo.setTransTimeOfBank(transInfo.getDtExecute());	//执行时间
					bankInstructionInfo.setStatusID(SETTConstant.BankInstructionStatus.SAVED);//指令状态                 
					bankInstructionInfo.setTransactionNo(transInfo.getSTransNo());//指令对应业务地交易号     
					bankInstructionInfo.setTransType(TransType.NORMAL);//指令对应业务地类型       
				//	bankInstructionInfo.setReceiveBank(transInfo.getNreceivebankid());//指令接收银行             
					bankInstructionInfo.setIDOfBankSeg1("");//指令在银行地对应标识 
					bankInstructionInfo.setPayAreaNameOfProvince("");//付款人开户行所在地省名称
					bankInstructionInfo.setPayAreaNameOfCity("");//付款人开户行所在地城市名称
					bankInstructionInfo.setCurrencyID(transInfo.getNCurrencyID());//交易币种
					bankInstructionInfo.setOfficeId(transInfo.getNOfficeID());//办事处id
					bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);//汇款速度
					bankInstructionInfo.setPayChargeAccountNo("");//手续费账号 
					bankInstructionInfo.setPayChargeBankExchangeCode("");//手续费账户联行号
					bankInstructionInfo.setPayChargeBranchCodeOfBank("");//手续费账户机构号				
					bankInstructionInfo.setRecNetStationName(""); //收款方网点名称									
				  			    
                    // 收款方信息，收款方为外部银行账户
					bankInstructionInfo.setReceiveAccountNo(transInfo.getSExtAccountNo());
					bankInstructionInfo.setReceiveAccountName(transInfo.getSExtClientName());
					bankInstructionInfo.setReceiveBranchName(transInfo.getSRemitinBank());
					bankInstructionInfo.setReceiveBranchAreaNameOfProvince(transInfo.getSRemitinProvince());
					bankInstructionInfo.setReceiveBranchAreaNameOfCity(transInfo.getSRemitinCity());
					bankInstructionInfo.setReceiveBankExchangeCode("");
					bankInstructionInfo.setReceiveBranchCodeOfBank("");					
					bankInstructionInfo.setTransactionNo(transInfo.getSTransNo());
					colInstructionParams.add(bankInstructionInfo);
				}
	    		
	    	}
	    	catch (Exception e) {			
				log.info("根据交易对象形成转贴现业务银行指令时出错，无法形成指令！");
				e.printStackTrace();
				throw new IException(e.getMessage(),e);
	    	}
	    	return colInstructionParams;
	    }
	    
		/**
		 * 生成交易类型为“信贷资产转让付款”指令
		 * @param obj 交易对象
		 * @throws IException
		 */
		private Collection createCraTransferPay(CreateInstructionParam param) throws IException {
			if(param.getObjInfo() == null) {
				log.error("构造信贷资产转让付款指令错误，交易对象参数为空！");
				throw new IException("构造信贷资产转让付款指令错误，交易对象参数为空！");
			}
			Collection colInstructionParams = new ArrayList();
			try {

				//通过结算交易号，获得结算对象
				TransferLoanContractInfo transInfo = new TransferLoanContractInfo();
				transInfo = (TransferLoanContractInfo)param.getObjInfo();				
				//根据付款方BankID，获得付款方对应的开户行信息
				BranchInfo branchInfo = new BranchInfo();
				Sett_BranchDAO branchDAO = new Sett_BranchDAO();			
				//根据收款方BankID,获得收款方对应的开户行信息
				CounterpartBankInfo bankInfo = new CounterpartBankInfo();
				CounterpartDao cDao = new CounterpartDao();				
				//构造指令									;
				//开户行对开户行
				if(transInfo.getPAYBANKID()>0 && transInfo.getRECEIVEBANKID()>0) { 
					log.debug("-----------开户行对开户行---------------");
					 long id_pay     = transInfo.getPAYBANKID();			//付款开户行号
					 long id_receive = transInfo.getRECEIVEBANKID();	   //收款开户行号

					//added by mzh_fu 2007/07/25 账户模式与付款方式分开后,是否发送指令的控制
	    			//转账方式:对外;业务类型:info.getTransactionTypeID()
	    			BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
					bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
					bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
					bankInstructionSettingQueryInfo.setTransType(transInfo.getTRANSACTIONTYPEID());
	    			
					BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByTransType(bankInstructionSettingQueryInfo);
					if(_bankInstructionSettingInfo.getIsSend() == Constant.TRUE)
					{
                        //逻辑：付款方：财务公司开户行；收款方：交易对手开户行
						BranchInfo branchInfo_pay = new BranchInfo();
						CounterpartBankInfo branchInfo_receive = new CounterpartBankInfo();
						branchInfo_pay = branchDAO.findByID(id_pay);
						branchInfo_receive.setId(id_receive);
						branchInfo_receive = cDao.findInfoByID(branchInfo_receive);	
						//付款银行没有设置发送指令就不发指令
					    if(branchInfo_pay.getIsAutoVirementByBank()==Constant.YesOrNo.YES)
					     {						
							BankInstructionInfo bankInstructionInfo = new BankInstructionInfo();		
							bankInstructionInfo.setPayAccountNo("");//付款账户号(付款关联号)
							bankInstructionInfo.setPayBankAccountNO(branchInfo_pay.getBankAccountCode());//付款银行账户号
							
							bankInstructionInfo.setPayAccountName(branchInfo_pay.getEnterpriseName());//付款账户名称	
							bankInstructionInfo.setPayDepartmentName(branchInfo_pay.getBankServiceName());//付款人单位全称           
							bankInstructionInfo.setAmount(transInfo.getAMOUNT()+transInfo.getINTEREST());//金额 
							bankInstructionInfo.setReceiveReferenceCode("");//收方的内部关联号
							bankInstructionInfo.setReceiveAccountNo(branchInfo_receive.getCounterpartbankno());//收款账户号 
							bankInstructionInfo.setReceiveAccountName(branchInfo_receive.getCounterpartbankname());//收款账户名称
							bankInstructionInfo.setReceiveDepartmentName(NameRef.getCounterpartNameByID(branchInfo_receive.getCounterpartid()));//收款人单位全称                    
							bankInstructionInfo.setReceiveBranchName(branchInfo_receive.getCounterparname());//收款人开户行名称 					
							bankInstructionInfo.setReceiveBranchAreaNameOfProvince(branchInfo_receive.getProvince());//收款人开户行所在地省名称 
							bankInstructionInfo.setReceiveBranchAreaNameOfCity(branchInfo_receive.getCity());//收款人开户行所在地城市名称       
							bankInstructionInfo.setTransType(TransType.NORMAL);
							//bankInstructionInfo.setComment(transInfo.getSabstract());//备注                     
							bankInstructionInfo.setStrAbstract(transInfo.getSABSTRACT());//摘要                     
							bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getINPUTUSERID()));//指令发送人no             
							bankInstructionInfo.setCancellerNo("");//指令撤销人no 
							bankInstructionInfo.setCreateTime( Env.getSystemDateTime());//指令创建时间            
							bankInstructionInfo.setSendTime( Env.getSystemDateTime());//指令发送时间             
							bankInstructionInfo.setCancelTime(new Timestamp(0));//指令撤销时间             
							bankInstructionInfo.setModifyTime( Env.getSystemDateTime());//指令修改时间
							bankInstructionInfo.setStatusID(SETTConstant.BankInstructionStatus.SAVED);//指令状态                 
							bankInstructionInfo.setTransactionNo(transInfo.getSTRANSNO());//指令对应业务地交易号     
							bankInstructionInfo.setReceiveBank(transInfo.getRECEIVEBANKID());//指令接收银行             
							bankInstructionInfo.setIDOfBankSeg1("");//指令在银行地对应标识 
							bankInstructionInfo.setPayAreaNameOfProvince("");//付款人开户行所在地省名称
							bankInstructionInfo.setPayAreaNameOfCity("");//付款人开户行所在地城市名称
							bankInstructionInfo.setCurrencyID(transInfo.getCURRENCYID());//交易币种
							bankInstructionInfo.setOfficeId(transInfo.getOFFICEID());//办事处id
							bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);//汇款速度
							bankInstructionInfo.setPayChargeAccountNo("");//手续费账号 
							bankInstructionInfo.setPayChargeBankExchangeCode("");//手续费账户联行号
							bankInstructionInfo.setPayChargeBranchCodeOfBank("");//手续费账户机构号				
							bankInstructionInfo.setRecNetStationName(""); //收款方网点名称				
							colInstructionParams.add(bankInstructionInfo);
						 }
				    }			 
			    }
			}
                 catch (Exception e) {
				log.error("生成信贷资产转让付款指令失败");
				e.printStackTrace();
				throw new IException(e.getMessage(),e);
			}
			return colInstructionParams;
		}

		/**
		 * 生成交易类型为“信贷资产转让收款（代收）”指令
		 * @param obj 交易对象
		 * @throws IException
		 */
		private Collection createCraTransferClientRePay(CreateInstructionParam param) throws IException {
			if(param.getObjInfo() == null) {
				log.error("构造信贷资产转让收款（代收）指令错误，交易对象参数为空！");
				throw new IException("构造信贷资产转让收款（代收）指令错误，交易对象参数为空！");
			}
			Collection colInstructionParams = new ArrayList();
			try {

				//通过结算交易号，获得结算对象
				TransferLoanContractInfo info = new TransferLoanContractInfo();
				info = (TransferLoanContractInfo)param.getObjInfo();
				
				//构造指令
				log.info("--------------开始生成指令------------");
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
			    	log.debug("--------信贷资产转让收款（代收） 开始------------");
			    	Iterator it = coll.iterator();
			    	while(it.hasNext())
			    	{
			    		NoticeAndAgentDetialResultInfo resultInfo = (NoticeAndAgentDetialResultInfo)it.next();

						//付款方信息
						if (resultInfo.getPayAccountID()>0)
						{
							//added by mzh_fu 2007/03/30 是否生成指令检验
							long lAccouontID=resultInfo.getPayAccountID();

							BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
							bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
							bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
							bankInstructionSettingQueryInfo.setTransType(param.getTransactionTypeID());
							bankInstructionSettingQueryInfo.setAccountId(lAccouontID);
							
							BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByAccountId(bankInstructionSettingQueryInfo);
							
							if(_bankInstructionSettingInfo.getIsSend() == Constant.TRUE) 
							{

							/**二级户账户模式*/
							if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.TWOLEVEL){
								//普通付款方式
								if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
									// 逻辑：付款方：活期账户对应的银行二级账户；收款方：银行二级账户的上级银行账户
									BankInstructionInfo pay_bankInstructionInfo = new BankInstructionInfo();				
								
									//获得付款方账户的上级银行账号单位
									ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
									instruction.setSystemId(1);
									AccountHome home =
										(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
									Account accounthome = (Account) home.create();
									AccountInfo payAccountInfo = accounthome.findAccountByID(lAccouontID);
									instruction.setReferenceCode(payAccountInfo.getAccountNo());
									RespGetGeneralBankAcctInfo generalbankacctinfoPay = BPClientAgent.getGeneralBankAcctInfo(instruction);
									if(generalbankacctinfoPay == null || generalbankacctinfoPay.getAccountInfo()==null)
									{
										throw new Exception("未找到付款方账户的上级账户信息");
									}
									//付款方信息
									pay_bankInstructionInfo.setPayAccountNo(payAccountInfo.getAccountNo());//付款账户号(付款关联号)
									pay_bankInstructionInfo.setPayBankAccountNO("");//付款银行账户号
									pay_bankInstructionInfo.setPayAcctBankCode(""); //付款账户银行编号
									pay_bankInstructionInfo.setPayAccountName(payAccountInfo.getAccountName());//付款账户名称
									pay_bankInstructionInfo.setPayDepartmentName(payAccountInfo.getAccountName());//付款人单位全称
									
									//收款方信息
									pay_bankInstructionInfo.setReceiveAccountNo(generalbankacctinfoPay.getAccountInfo().getAccountNO());//收款账户号
									pay_bankInstructionInfo.setReceiveAccountName(generalbankacctinfoPay.getAccountInfo().getAccountName());//收款账户名称
									pay_bankInstructionInfo.setReceiveBranchName(generalbankacctinfoPay.getAccountInfo().getBankName());//收款人开户行名称
									pay_bankInstructionInfo.setReceiveBranchAreaNameOfProvince(generalbankacctinfoPay.getAccountInfo().getBranchProvince());//收款人开户行所在地省名称
									pay_bankInstructionInfo.setReceiveBranchAreaNameOfCity(generalbankacctinfoPay.getAccountInfo().getBranchCity());//收款人开户行所在地城市名称
									
									//其他信息
									if(contractInfo.getCommissionPaymentType()==CRAconstant.CommisonPayType.FANHUAN)
									{
										pay_bankInstructionInfo.setAmount(resultInfo.getBalance()+resultInfo.getInterest());//金额 
									}
									else
									{
										if(contractInfo.getChargeTypeId()==CRAconstant.ChargeCommisonPayType.CHARGEAMOUNT)
										{
											if(info.getAMOUNT()>0)
											{
												pay_bankInstructionInfo.setAmount(resultInfo.getBalance()+resultInfo.getInterest()-UtilOperation.Arith.round((info.getCOMMISSION()*resultInfo.getBalance()/info.getAMOUNT()),2));//金额 
											}
											else
											{
												pay_bankInstructionInfo.setAmount(resultInfo.getBalance()+resultInfo.getInterest());//金额 
											}
										}
										else if(contractInfo.getChargeTypeId()==CRAconstant.ChargeCommisonPayType.CHARGEINTEREST || contractInfo.getChargeTypeId()==CRAconstant.ChargeCommisonPayType.CHARGEOTHER)
										{
											if(info.getINTEREST()>0)
											{
												pay_bankInstructionInfo.setAmount(resultInfo.getBalance()+resultInfo.getInterest()-UtilOperation.Arith.round((info.getCOMMISSION()*resultInfo.getInterest()/info.getINTEREST()),2));//金额
											}
											else
											{
												pay_bankInstructionInfo.setAmount(resultInfo.getBalance()+resultInfo.getInterest());//金额
											}
										}
										else
										{
											pay_bankInstructionInfo.setAmount(resultInfo.getBalance() + resultInfo.getInterest());
										}
										
									}
									
									pay_bankInstructionInfo.setStrAbstract(info.getSABSTRACT());//摘要
									pay_bankInstructionInfo.setSenderNo(String.valueOf(info.getINPUTUSERID()));//指令发送人no
									pay_bankInstructionInfo.setCancellerNo("");//指令撤销人no 
									pay_bankInstructionInfo.setCreateTime( Env.getSystemDateTime());//指令创建时间            
									pay_bankInstructionInfo.setSendTime( Env.getSystemDateTime());//指令发送时间             
									pay_bankInstructionInfo.setCancelTime(new Timestamp(0));//指令撤销时间             
									pay_bankInstructionInfo.setModifyTime( Env.getSystemDateTime());//指令修改时间
									pay_bankInstructionInfo.setStatusID(SETTConstant.BankInstructionStatus.SAVED);//指令状态                 
									pay_bankInstructionInfo.setTransactionNo(info.getSTRANSNO());//指令对应业务地交易号     
									pay_bankInstructionInfo.setTransType(TransType.NORMAL);				
									pay_bankInstructionInfo.setIDOfBankSeg1("");//指令在银行地对应标识 
									pay_bankInstructionInfo.setPayAreaNameOfProvince("");//付款人开户行所在地省名称
									pay_bankInstructionInfo.setPayAreaNameOfCity("");//付款人开户行所在地城市名称
									pay_bankInstructionInfo.setCurrencyID(info.getCURRENCYID());//交易币种
									pay_bankInstructionInfo.setOfficeId(info.getOFFICEID());
									pay_bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);//汇款速度
					
									colInstructionParams.add(pay_bankInstructionInfo);
									
								//代理汇兑方式	
								}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
									log.info("*********财务公司代成员单位对外付款*********");
															
											
								//先拨后支方式	
								}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
									log.info("*********先拨后支*********");
								}
								
							/**门户账户模式*/
							}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.PORTAL){
									
									//普通付款方式
									if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
										log.info("*********财务公司对外付款*********");
									
									//代理汇兑方式	
									}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
										log.info("*********财务公司代成员单位对外付款*********");
										
									//先拨后支方式	
									}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
										log.info("*********先拨后支*********");
									}
									
							/**收支两条线模式*/
							}else if(_bankInstructionSettingInfo.getAccountModule() == SETTConstant.AccountModule.INCOMEEXPENSE){
									
									//普通付款方式
									if(_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.COMMON_PAY){
										log.info("*********财务公司对外付款*********");
									
									//代理汇兑方式	
									}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.AGENT){
										log.info("*********财务公司代成员单位对外付款*********");
										
									//先拨后支方式	
									}else if (_bankInstructionSettingInfo.getPayModule() == SETTConstant.PayModule.FIRSTALLOCATE_LASTPAY){
										log.info("*********先拨后支*********");
									}
							}
							}
						}
			    	}
			    }

				log.info("------结束信贷资产转让收款（代收）指令生成");	
			} catch (Exception e) {
				log.error("生成信贷资产转让收款（代收）指令失败");
				e.printStackTrace();
				throw new IException(e.getMessage(),e);
			}
			return colInstructionParams;
		}
		
		/**
		 * 生成交易类型为“备付金调回”指令
		 * @param obj 交易对象
		 * @throws IException
		 */
		private Collection createBakReserveReturnInstruction(CreateInstructionParam param) throws IException {
			if(param.getObjInfo() == null) {
				log.error("构造备付金调回指令错误，交易对象参数为空！");
				throw new IException("构造备付金调回指令错误，交易对象参数为空！");
			}
			Collection colInstructionParams = new ArrayList();
			try {

				//通过结算交易号，获得结算对象
				TransBakReserveInfo info = new TransBakReserveInfo();
				info = (TransBakReserveInfo)param.getObjInfo();
				
				//根据付款方BankID，获得付款方对应的开户行信息
				BranchInfo branchInfo = new BranchInfo();
				Sett_BranchDAO branchDAO = new Sett_BranchDAO();			
				branchInfo = branchDAO.findByID(info.getBankid());
							
				//构造指令
				log.info("--------------开始生成指令------------");
				//当指定开户行不存在、指定开户行无银行类型、无银行账户号，交易中无下属单位账户信息和外部账户信息则不生成指令

				BankInstructionInfo bankInstructionInfo = new BankInstructionInfo();					
				
				//AccountInfo accountInfo=new AccountInfo();
				//Sett_AccountDAO dao=new Sett_AccountDAO();
				//accountInfo=dao.findByID(info.getBakaccountid());

				//付款方信息
				//结算中心代成员单位对外付款
				if (info.getBakaccountid()>0)
				{
					//added by mzh_fu 2007/03/30 是否生成指令检验
					//long lAccouontID=info.getBakaccountid();

					BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
					bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
					bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
					bankInstructionSettingQueryInfo.setTransType(info.getNtransactiontypeid());
					
					BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByTransType(bankInstructionSettingQueryInfo);
					
					if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
					{
						return null;			
					}
					else
					{
						bankInstructionInfo.setPayAccountNo("");	//付款账户号(付款关联号)
						bankInstructionInfo.setPayBankAccountNO(branchInfo.getBankAccountCode());//付款银行账户号
						bankInstructionInfo.setPayAcctBankCode(branchInfo.getBranchCode());//付款账户银行编号
						//bankInstructionInfo.setPayAccountName(branchInfo.getBranchName());

                        bankInstructionInfo.setPayAccountName(branchInfo.getEnterpriseName());//付款账户名称        
                       // bankInstructionInfo.setPayDepartmentName(branchInfo.getBankServiceName());//付款人单位全称     
                        
						bankInstructionInfo.setTransType(TransType.NORMAL);
							
					}
					
				}

				//收款方信息，收款方为外部银行账户
				bankInstructionInfo.setReceiveAccountNo(info.getSextaccountno());
				bankInstructionInfo.setReceiveAccountName(info.getSextclientname());
				bankInstructionInfo.setReceiveBranchName(info.getSremitinbank());
				bankInstructionInfo.setReceiveBranchAreaNameOfProvince(info.getSremitinprovince());
				bankInstructionInfo.setReceiveBranchAreaNameOfCity(info.getSremitincity());
				bankInstructionInfo.setReceiveBankExchangeCode("");
				bankInstructionInfo.setReceiveBranchCodeOfBank("");					
				bankInstructionInfo.setTransactionNo(info.getStransno());
				bankInstructionInfo.setRecBankCode(info.getSpayeebankcnapsno());  //收款方CNAPS号
				bankInstructionInfo.setReceiveBankExchangeCode(info.getSpayeebankexchangeno()); //收款方联行号
				bankInstructionInfo.setReceiveBranchCodeOfBank(info.getSpayeebankorgno());//收款方机构号
				

				//金额，币种 等其他信息
				bankInstructionInfo.setAmount(info.getAmount());				
				bankInstructionInfo.setCurrencyID(info.getNcurrencyid());
				bankInstructionInfo.setOfficeId(info.getNofficeid());//新增办事处id
				
				bankInstructionInfo.setAbstract(info.getSabstract());
				bankInstructionInfo.setTransactionNo(info.getStransno());

				bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
				bankInstructionInfo.setReceiveBank(branchInfo.getBankTypeID());
				bankInstructionInfo.setTransTimeOfBank(info.getDtexecute());		

				bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);

				
				bankInstructionInfo.setSenderNo(String.valueOf(info.getNinputuserid()));					
				
				log.info("备付金调回指令对象："+UtilOperation.dataentityToString(bankInstructionInfo));
				//保存下属单位到外部账户的付款指令											

				log.info("--------------结束生成指令------------");
				//BankInstructionOperation instructionOperation = new BankInstructionOperation();
				log.info("生成的指令信息是："+UtilOperation.dataentityToString(bankInstructionInfo));

				//instructionOperation.addBankInstruction(bankInstructionInfo);
				colInstructionParams.add(bankInstructionInfo);
				log.info("------结束备付金调回指令生成");	
			} catch (Exception e) {
				log.error("生成备付金调回指令失败");
				e.printStackTrace();
				throw new IException(e.getMessage(),e);
			}
			return colInstructionParams;
		}

		
		
		/**
		 * 生成交易类型为“准备金调回”指令
		 * @param obj 交易对象
		 * @throws IException
		 */
		private Collection createReserveReturnInstruction(CreateInstructionParam param) throws IException {
			if(param.getObjInfo() == null) {
				log.error("构造准备金调回指令错误，交易对象参数为空！");
				throw new IException("构造银行付款指令错误，交易对象参数为空！");
			}
			Collection colInstructionParams = new ArrayList();
			try {

				//通过结算交易号，获得结算对象
				TransReserveInfo info = new TransReserveInfo();
				info = (TransReserveInfo)param.getObjInfo();
				
				//根据付款方BankID，获得付款方对应的开户行信息
				BranchInfo branchInfo = new BranchInfo();
				Sett_BranchDAO branchDAO = new Sett_BranchDAO();			
				branchInfo = branchDAO.findByID(info.getBankid());
							
				//构造指令
				log.info("--------------开始生成指令------------");
				//当指定开户行不存在、指定开户行无银行类型、无银行账户号，交易中无下属单位账户信息和外部账户信息则不生成指令

				BankInstructionInfo bankInstructionInfo = new BankInstructionInfo();					
				
				//AccountInfo accountInfo=new AccountInfo();
				//Sett_AccountDAO dao=new Sett_AccountDAO();
				//accountInfo=dao.findByID(info.getReserveaccountid());

				//付款方信息
				//结算中心代成员单位对外付款
				if (info.getReserveaccountid()>0)
				{
					//added by mzh_fu 2007/03/30 是否生成指令检验
					//long lAccouontID=info.getReserveaccountid();

					BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
					bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
					bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
					bankInstructionSettingQueryInfo.setTransType(info.getNtransactiontypeid());
					
					BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByTransType(bankInstructionSettingQueryInfo);
					
					if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE) 
					{
						return null;			
					}
					else
					{
						bankInstructionInfo.setPayAccountNo("");	//付款账户号(付款关联号)
						bankInstructionInfo.setPayBankAccountNO(branchInfo.getBankAccountCode());//付款银行账户号
						bankInstructionInfo.setPayAcctBankCode(branchInfo.getBranchCode());//付款账户银行编号
						//bankInstructionInfo.setPayAccountName(branchInfo.getBranchName());

                        bankInstructionInfo.setPayAccountName(branchInfo.getEnterpriseName());//付款账户名称        
                       // bankInstructionInfo.setPayDepartmentName(branchInfo.getBankServiceName());//付款人单位全称     
                        
						bankInstructionInfo.setTransType(TransType.NORMAL);
							
					}
					
					
				}

				//收款方信息，收款方为外部银行账户
				bankInstructionInfo.setReceiveAccountNo(info.getSextaccountno());
				bankInstructionInfo.setReceiveAccountName(info.getSextclientname());
				bankInstructionInfo.setReceiveBranchName(info.getSremitinbank());
				bankInstructionInfo.setReceiveBranchAreaNameOfProvince(info.getSremitinprovince());
				bankInstructionInfo.setReceiveBranchAreaNameOfCity(info.getSremitincity());
				bankInstructionInfo.setReceiveBankExchangeCode("");
				bankInstructionInfo.setReceiveBranchCodeOfBank("");					
				bankInstructionInfo.setTransactionNo(info.getStransno());
				bankInstructionInfo.setRecBankCode(info.getSpayeebankcnapsno());  //收款方CNAPS号
				bankInstructionInfo.setReceiveBankExchangeCode(info.getSpayeebankexchangeno()); //收款方联行号
				bankInstructionInfo.setReceiveBranchCodeOfBank(info.getSpayeebankorgno());//收款方机构号
				

				//金额，币种 等其他信息
				bankInstructionInfo.setAmount(info.getAmount());				
				bankInstructionInfo.setCurrencyID(info.getNcurrencyid());
				bankInstructionInfo.setOfficeId(info.getNofficeid());//新增办事处id
				
				bankInstructionInfo.setAbstract(info.getSabstract());
				bankInstructionInfo.setTransactionNo(info.getStransno());

				bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
				bankInstructionInfo.setReceiveBank(branchInfo.getBankTypeID());
				bankInstructionInfo.setTransTimeOfBank(info.getDtexecute());		

				bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);

				
				bankInstructionInfo.setSenderNo(String.valueOf(info.getNinputuserid()));					
				
				log.info("准备金调回指令对象："+UtilOperation.dataentityToString(bankInstructionInfo));
				//保存下属单位到外部账户的付款指令											

				log.info("--------------结束生成指令------------");
				//BankInstructionOperation instructionOperation = new BankInstructionOperation();
				log.info("生成的指令信息是："+UtilOperation.dataentityToString(bankInstructionInfo));

				//instructionOperation.addBankInstruction(bankInstructionInfo);
				colInstructionParams.add(bankInstructionInfo);
				log.info("------结束准备金调回指令生成");	
			} catch (Exception e) {
				log.error("生成准备金调回指令失败");
				e.printStackTrace();
				throw new IException(e.getMessage(),e);
			}
			return colInstructionParams;
		}
		
		/**
		 * 统一生成同业往来相关交易的银行指令
		 * @param obj 交易对象
		 * @throws IException
		 */
		private Collection createCraftBrotherInstruction(CreateInstructionParam param) throws IException {
			if(param.getObjInfo() == null) {
				log.error("构造同业往来相关交易银行指令错误，交易对象参数为空！");
				throw new IException("构造同业往来相关交易银行指令错误，交易对象参数为空！");
			}
			Collection colInstructionParams = new ArrayList();
			try {
				
				//通过结算交易号，获得结算对象
				TransCraftbrotherInfo info = new TransCraftbrotherInfo();
				info = (TransCraftbrotherInfo)param.getObjInfo();
				
				//根据付款方BankID，获得付款方对应的开户行信息
				BranchInfo branchInfo = new BranchInfo();
				Sett_BranchDAO branchDAO = new Sett_BranchDAO();			
				branchInfo = branchDAO.findByID(info.getNbankId());
				
				//开户行没有设置发指令，就不发银行指令
    			if(branchInfo.getIsAutoVirementByBank()!=Constant.YesOrNo.YES)
			    {
    				return null;
			    }
				
    			BankInstructionSettingInfo bankInstructionSettingQueryInfo=new BankInstructionSettingInfo();
    			bankInstructionSettingQueryInfo.setNOfficeId(param.getOfficeID());
    			bankInstructionSettingQueryInfo.setNCurrencyId(param.getCurrencyID());
    			bankInstructionSettingQueryInfo.setTransType(info.getNtransactionTypeId());
    			BankInstructionSettingInfo _bankInstructionSettingInfo = getSettingInfoByTransType(bankInstructionSettingQueryInfo);
    			if(_bankInstructionSettingInfo.getIsSend() != Constant.TRUE)
    			{
    				return null;
    			}
    			
				//构造指令
				log.info("--------------开始生成指令------------");

				BankInstructionInfo bankInstructionInfo = new BankInstructionInfo();
				
				bankInstructionInfo.setPayBankAccountNO(branchInfo.getBankAccountCode());
				bankInstructionInfo.setPayAccountName(branchInfo.getEnterpriseName());
				bankInstructionInfo.setPayAcctBankCode(branchInfo.getBranchCode());					
				bankInstructionInfo.setPayBranchName(branchInfo.getBranchName());
				bankInstructionInfo.setTransType(TransType.NORMAL);
				
				//收款方信息，收款方为外部银行账户
				bankInstructionInfo.setReceiveAccountNo(info.getSextAccountNo());
				bankInstructionInfo.setReceiveAccountName(info.getSextClientName());
				bankInstructionInfo.setReceiveBranchName(info.getSremitInBank());
				bankInstructionInfo.setReceiveBranchAreaNameOfProvince(info.getSremitInProvince());
				bankInstructionInfo.setReceiveBranchAreaNameOfCity(info.getSremitInCity());		
				bankInstructionInfo.setRecBankCode(info.getSpayeebankcnapsno());  //收款方CNAPS号
				bankInstructionInfo.setReceiveBankExchangeCode(info.getSpayeebankexchangeno()); //收款方联行号
				bankInstructionInfo.setReceiveBranchCodeOfBank(info.getSpayeebankorgno());//收款方机构号
				
				//金额，币种 等其他信息
				if(info.getNtransactionTypeId()==SETTConstant.TransactionType.TRANS_DISCOUNT_GRANT&&(info.getNsubTransactionTypeId()== SETTConstant.SubTransactionType.BREAK_INVEST||info.getNsubTransactionTypeId()== SETTConstant.SubTransactionType.REPURCHASE_INVEST))
				{
					//转贴现发放，买入买断和买入回购取实际金额（=票据金额-利息）
					bankInstructionInfo.setAmount(info.getMrealamount());	
				}else{
					bankInstructionInfo.setAmount(info.getMamount());
				}
				bankInstructionInfo.setCurrencyID(info.getNcurrencyId());
				bankInstructionInfo.setOfficeId(info.getNofficeId());//新增办事处id
				bankInstructionInfo.setAbstract(info.getSabstract());
				bankInstructionInfo.setTransactionNo(info.getStransNo());
				bankInstructionInfo.setCreateTime(Env.getSystemDateTime());
				bankInstructionInfo.setReceiveBank(branchInfo.getBankTypeID());
				bankInstructionInfo.setTransTimeOfBank(info.getDtExecute());	
				bankInstructionInfo.setSenderNo(String.valueOf(info.getNinputUserId()));	
				if(bankInstructionInfo.getRemitPriority() < 0)
				{
					bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
				}			

				colInstructionParams.add(bankInstructionInfo);
				if(info.getNtransactionTypeId()==SETTConstant.TransactionType.FUND_ATTORN_REPAY
						&&info.getNsubTransactionTypeId()== SETTConstant.SubTransactionType.CAPITAL_IN)
				{
					//如果是拆入返款业务，应该生成两条银行指令，此处追加一条利息指令 add by wangzhen 2012-02-27
					BankInstructionInfo bankInstructionInfonew = new BankInstructionInfo();
					bankInstructionInfonew=(BankInstructionInfo)bankInstructionInfo.clone();
					bankInstructionInfonew.setAmount(info.getMinterest());		
					colInstructionParams.add(bankInstructionInfonew);
				}
				log.info("------结束同业往来相关业务指令生成");	
			} catch (Exception e) {
				log.error("生成同业往来相关业务指令失败");
				e.printStackTrace();
				throw new IException(e.getMessage(),e);
			}
			return colInstructionParams;
		}
		
		/**
		 * 设置汇款速度（如果付款方是工行，设置汇款速度为：加急）
		 * @param coll
		 * @return
		 */
		public Collection updateRemitPriority(Collection coll)
		{
			try{
				
				if(coll!=null&&coll.size()>0)
				{
					int i=0;
					while(i<coll.size())
					{
					  BankInstructionInfo info =(BankInstructionInfo)coll.toArray()[i];
					  String payBankName = "";
					  Bs_BankAccountInfoDAO bs_Dao = new Bs_BankAccountInfoDAO();
					  if(info.getPayAccountNo()!=null && !"".equals(info.getPayAccountNo()))
					  {
						 //得到内部户的ID
						long payAccountId = NameRef.getAccountIdByNo(info.getPayAccountNo());
						//通过内部户ID,得到银行账户号
						String payBankNo = bs_Dao.getBankNo(payAccountId, 1);
						//得到账户的开户行名称
						if(!"".equals(payBankNo))
						{
							payBankName = bs_Dao.getBranchNameByNo(payBankNo);
						}
						else
						{
							payBankNo = bs_Dao.getBankNo(payAccountId, 2);
							if(!"".equals(payBankNo))
							{
								payBankName = bs_Dao.getBranchNameByNo(payBankNo);
							}
						}
						
					  }
					  else if(info.getAgentAcctNoOfPay()!=null && !"".equals(info.getAgentAcctNoOfPay()))
					  {
						  payBankName = bs_Dao.getBranchNameByNo(info.getAgentAcctNoOfPay());
					  }
					  else
					  {
						  payBankName = bs_Dao.getBranchNameByNo(info.getPayBankAccountNO());
					  }
					  if(!"".equals(payBankName))
					  {
						  try
						  {
							  if((BranchIdentify.isSameBankName(payBankName,"工行")))
							    {									  
								   info.setRemitPriority(RemitPriority.URGENT);
							    } 
						  }catch(Exception e)
						  {
							  info.setRemitPriority(RemitPriority.NORMAL);
							   e.printStackTrace();
								//判断工行指令是否为加急出错
								log.info("判断工行指令是否为加急出错，原来指令汇款状态置为普通："+e.getMessage());
						  }
					  }
					   i++;
					}
				}
			}
			catch(Exception e)
			{								
				e.printStackTrace();
				//判断工行指令是否为加急出错
				log.info("判断工行指令是否为加急出错："+e.getMessage());
			}
			return coll;
		}

}
