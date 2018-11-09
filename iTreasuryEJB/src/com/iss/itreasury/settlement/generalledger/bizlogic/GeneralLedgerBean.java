package com.iss.itreasury.settlement.generalledger.bizlogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.settlement.account.bizlogic.AccountOperation;
import com.iss.itreasury.settlement.account.dao.Sett_SubAccountDAO;
import com.iss.itreasury.settlement.account.dataentity.SubAccountLoanInfo;
import com.iss.itreasury.settlement.assistant.dao.AssistantDao;
import com.iss.itreasury.settlement.assistant.dataentity.AssistantInfo;
import com.iss.itreasury.settlement.bankinstruction.BankInstructionFactory;
import com.iss.itreasury.settlement.bankinstruction.IBankInstruction;
//import com.iss.itreasury.settlement.bankinstruction.dao.BankInstructionDAO;
import com.iss.itreasury.settlement.generalledger.dao.Sett_GLEntryDefinitionDAO;
import com.iss.itreasury.settlement.generalledger.dao.Sett_GLEntryDefinitionTempDAO;
import com.iss.itreasury.settlement.generalledger.dao.Sett_GLSubjectDefinitionDAO;
import com.iss.itreasury.settlement.generalledger.dao.Sett_GeneralLedgerDAO;
import com.iss.itreasury.settlement.generalledger.dao.sett_GLBalanceDAO;
import com.iss.itreasury.settlement.generalledger.dao.sett_GLEntryDAO;
import com.iss.itreasury.settlement.generalledger.dataentity.GLBalanceInfo;
import com.iss.itreasury.settlement.generalledger.dataentity.GLEntryDefinitionInfo;
import com.iss.itreasury.settlement.generalledger.dataentity.GLEntryInfo;
import com.iss.itreasury.settlement.generalledger.dataentity.GLSubjectDefinitionInfo;
import com.iss.itreasury.settlement.generalledger.dataentity.GenerateGLEntryParam;
import com.iss.itreasury.settlement.setting.dao.OfficeClearingAccountSetDao;
import com.iss.itreasury.settlement.transcurrentdeposit.dao.Sett_TransCurrentDepositDAO;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.transferloancontract.dataentity.TransferLoanAmountDetailinfo;
import com.iss.itreasury.settlement.transloan.bizlogic.BankLoanQuery;
import com.iss.itreasury.settlement.transloan.dataentity.BankLoanDrawInfo;
import com.iss.itreasury.settlement.transloan.dataentity.SyndicationLoanInterestInfo;
import com.iss.itreasury.settlement.transspecial.dao.Sett_TransSpecialOperationDAO;
import com.iss.itreasury.settlement.transspecial.dataentity.TransSpecialOperationInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.settlement.util.NameRef;

//add by dwj 20120202
import com.iss.itreasury.settlement.craftbrother.dao.TransCraftbrotherDAO;
import com.iss.itreasury.loan.transdiscountcontract.dataentity.TransDiscountContractBillInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.transdiscount.dao.Sett_TransGrantDiscountDAO;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransGrantDiscountInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountAssemblerInfo;
import com.iss.itreasury.loan.transdiscountcredence.dao.TransDiscountCredenceDAO;
import com.iss.itreasury.settlement.craftbrother.dataentity.TransCraftbrotherInfo;
import com.iss.itreasury.loan.transdiscountcredence.dataentity.TransDiscountCredenceInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.settlement.transloan.dao.Sett_TransGrantLoanDAO;
import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
import com.iss.itreasury.loan.attornmentapply.dataentity.AttornmentContractInfo;
//end add by dwj 20120202


/**
 * Title:        		iTreasury
 * Description:         总账操作的公用类，供由容器管理事务的操作和非容器管理事务的操作公用          
 * Copyright (c) 2003 Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2003-11-24
 */
public class GeneralLedgerBean {
	
	private Connection conn = null;
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);		
	/**非容器管理事务的处理操作构造函数*/		
	public GeneralLedgerBean(){
	}
	/**容器管理事务的处理操作构造函数*/
	public GeneralLedgerBean(Connection conn){
		this.conn = conn;
	}	


	/**
	 * 交易产生会计分录
	 * @return 成功返回true, 失败返回false
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public boolean generateGLEntry(GenerateGLEntryParam param) throws IException
	{
		/*  TOCONFIG—TODELETE  */ 
		/*
		if(Env.getProjectName().compareToIgnoreCase(Constant.ProjectName.ICBC) == 0)
		{//工行－联通项目不生成会计分录，直接返回
			return true;
		}
		*/
		/*  TOCONFIG—END  */ 
		
		try
		{    
			log.info("构造会计分录的对象：" + param.toString());
			
			//获取参数
			//TransInfo transInfo = param.getTransInfo();
			long lPrincipalType = param.getPrincipalType();
			long lInterestType = param.getInterestType();
			long lCommisionType = param.getCommisionType();
			long lEntryType = param.getEntryType();
			long lSubTransactionType = param.getSubTransactionType();
			String sMultiCode = param.getMultiCode();
			long receiveAccountID = param.getReceiveAccountID(); //收款方账户ID，可空
			long payAccountID = param.getPayAccountID(); //付款方账户ID，可空  
			long receiveInterestAccountID = param.getReceiveInterestAccountID();
			//收息账户ID，可空
			long payInterestAccountID = param.getPayInterestAccountID();
			//付息账户ID，可空   
			long vouchReceiveAccountID = param.getVouchReceiveAccountID();
			//委托收款方账户ID，可空
			long vouchPayAccountID = param.getVouchPayAccountID();
			//委托付款方账户ID，可空
			long receieveSuertyFeeAccountID = param.getReceieveSuertyFeeAccountID();
			//收担保费账户ID，可空
			long paySuertyFeeAccountID = param.getPaySuertyFeeAccountID();
			//付担保费账户ID，可空
			long payCommissionAccountID = param.getPayCommissionAccountID();
			//付手续费账户ID，可空
			long principalBankID = param.getPrincipalBankID(); //本金开户行ID，可空
			long genaralLedgerTypeID = param.getGenaralLedgerTypeID();//总账类ID，可空
			long interestBankID = param.getInterestBankID(); //本金开户行ID，可空
			long feeBankID = param.getFeeBankID(); //费用开户行ID，可空
			double principalOrTransAmount = param.getPrincipalOrTransAmount();
			//本金/交易金额
			double totalInterest = param.getTotalInterest(); //利息合计，可空
			double preDrawInterest = param.getPreDrawInterest(); //计提利息，可空
			double unPreDrawInterest = param.getUnPreDrawInterest(); //未计提利息，可空
			double overTimeInterest = param.getOverTimeInterest(); //逾期利息，可空
			double overFee = param.getOverFee(); //罚息，可空	
			double compoundInterest = param.getCompoundInterest(); //复利，可空
			double suretyFee = param.getSuretyFee(); //担保费，可空
			double commissionFee = param.getCommissionFee(); //手续费，可空
			double interestTaxFee = param.getInterestTaxFee(); //利息税费，可空
			double totalPrincipalAndInterest = param.getTotalPrincipalAndInterest();
			double RemitAmount = param.getRemitAmount();
			//本息合计，可空	
			double remissionInterest = param.getRemissionInterest(); //豁免利息，可空
			double reallyReceiveInterest = param.getReallyReceiveInterest();
			
			//融资租赁处理
			long receiveFinanceAccountID = param.getReceiveFinanceAccountID();		//add by feiye 2006.5.26
			//收手续费账户ID，可空
			long payFinanceAccountID = param.getPayFinanceAccountID();				//add by feiye 2006.5.26
			//付手续费账户ID，可空
			double FinanceAllAmount = param.getFinanceAllAmount();			//add by feiye 2006.5.26
			//融保本息合计
			
			//add by dwj 20120202 移值同业往来(参考方正项目)
			//资产转让
			double marginAmount = param.getMarginAmount();	//【买入买断差额】 = 【转让价格】 - 【转让金额】
			double attornAmount = param.getAttornAmount();	//【转让金额】
			double attornPrice  = param.getAttornPrice();	//【转让价格】
			double adjustFee    = param.getAdjustFee();		//【费用调整】
			//end add by dwj 20120202
			
			//融资租赁活期本息合计
			double currentPrincipalAndInterest = param.getCurrentPrincipalAndInterest();// add by zwxiao 2010-08-17
			
			//融资租赁开户行本息合计
			double bankPrincipalAndInterest = param.getBankPrincipalAndInterest();// add by zwxiao 2010-08-17
			
			//付方备付金子账户
			long paybakSubAccountID = param.getPayBakAccountID();

			//收方备付金子账户
			long receivebakSubAccountID = param.getReceiveBakAccountID();
			
			//实收利息，可空     	 	
			
			//帐户类型
			//long accountType = -1;
			
			log.debug("-----------开始产生会计分录-----------");
			//生成会计分录定义DAO
			Sett_GLEntryDefinitionDAO gLEntryDefinitionDAO = new Sett_GLEntryDefinitionDAO(conn);
			Sett_GLEntryDefinitionTempDAO gLEntryDefinitionTempDAO = new Sett_GLEntryDefinitionTempDAO(conn);
			log.debug("----------根据交易类型，分录类型查询会计分录定义--------------");
			ArrayList gLEntryDefinitions = null;
			ArrayList gLEntryDefinitionsTemp = null;
			try
			{
				boolean isneedcheck = Config.getBoolean(ConfigConstant.SETT_GLENTRYDEFINITION_ISNEEDCHECK,false);
				if(isneedcheck == true){
					gLEntryDefinitionsTemp = (ArrayList) gLEntryDefinitionTempDAO.findUncheckGLEntryDefinitionsByTransType(param.getTransactionTypeID(),param.getCurrencyID(),param.getOfficeID());
					if(gLEntryDefinitionsTemp != null && gLEntryDefinitionsTemp.size() > 0){
						
						throw new IException(true,"该业务类型存在未复核的会计分录设置",null);
					}
				}
				gLEntryDefinitions = (ArrayList) gLEntryDefinitionDAO.findAllByTransactionTypeIDAndEntryType(param.getTransactionTypeID(), lEntryType,lSubTransactionType,param.getCurrencyID(),param.getOfficeID());
				
			}
			catch (SQLException e)
			{
				e.printStackTrace();throw new IException(true, e.getMessage(), e);
			}
			log.debug("-----查询到" + gLEntryDefinitions.size() + "个会计分录定义----------");
			if (gLEntryDefinitions != null && gLEntryDefinitions.size() > 0)
			{
				for (int i = 0; i < gLEntryDefinitions.size(); i++)
				{
					//客户编号
					String clientno = "";
					
					GLEntryDefinitionInfo tmp = (GLEntryDefinitionInfo) gLEntryDefinitions.get(i);
					log.debug("--------会计分录定义" + i + "是:------------");
					log.debug(UtilOperation.dataentityToString(tmp));
					//机构类型是"无关",不做以下匹配 
					if (tmp.getOfficeType() != SETTConstant.CapitalType.IRRESPECTIVE)
					{
						log.debug("--------机构类型不是无关--------");
						if(tmp.getOfficeType() == SETTConstant.OfficeType.PAY)
						{
							if(param.getPayofficeID() == param.getParentofficeID())
							{
								log.debug("付款方机构是总部，匹配下一个会计分录定义");
								continue;
							}
						}
						if(tmp.getOfficeType() == SETTConstant.OfficeType.RECEIVE)
						{
							if(param.getReceiveofficeID() == param.getParentofficeID())
							{
								log.debug("收款方机构是总部，匹配下一个会计分录定义");
								continue;
							}
						}
					}
					
					//资金流向是"无关",不做以下匹配 
					if (tmp.getCapitalType() != SETTConstant.CapitalType.IRRESPECTIVE)
					{
						log.debug("--------资金流向不是无关--------");
						//如果金额类型是"本金/交易金额" 比较"资金流向"是否相同			
						if ((tmp.getAmountType() == SETTConstant.AmountType.AmountType_1 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_11|| tmp.getAmountType() == SETTConstant.AmountType.AmountType_14) && lPrincipalType != tmp.getCapitalType())
						{
							log.debug("金额类型是本金/交易金额  本息合计 资金流向不同，匹配下一个会计分录定义");
							continue;
						}
						//如果金额类型是"利息合计" "计提利息" "未计提利息" "逾期利息" "罚息" "复利" ，比较利息类型是否相同
						if ((tmp.getAmountType() == SETTConstant.AmountType.AmountType_2 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_3 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_4 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_5 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_6 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_7 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_13) && lInterestType != tmp.getCapitalType())
						{
							log.debug("金额类型是利息合计 计提利息 未计提利息 逾期利息　罚息 复利, 实收利息 利息类型不同，匹配下一个会计分录定义");
							continue;
						}
						//如果金额类型是"担保费" "手续费"，比较"费用流向"是否相同
						System.out.println("..........................	qqqqqqqqqqqqqqqqqqqqqqqqq    lCommisionType = "+lCommisionType);
						System.out.println("..........................	343243243243243    tmp.getCapitalType() = "+tmp.getCapitalType());
						if ((tmp.getAmountType() == 8 || tmp.getAmountType() == 9) && lCommisionType != tmp.getCapitalType())
						{
							log.debug("金额类型是担保费 手续费, 费用流向，匹配下一个会计分录定义");
							continue;
						}
						if( tmp.getAmountType() == SETTConstant.AmountType.AmountType_16 &&  param.getTransactionTypeID()==SETTConstant.TransactionType.TRANSFERREPAY )
						{
							log.debug("金额类型是手续费且是卖出卖断收款, 费用流向，匹配下一个会计分录定义");
							continue;
						}
					}
					
					//add by dwj 20120202  移值同业往来(参考方正项目)
					//同业资产转让买入买断发放,差额选择性生成分录
					if(tmp.getTransactionType() == SETTConstant.TransactionType.SAME_BUSINESS_GRANT && tmp.getSubTransactionType() == SETTConstant.SubTransactionType.BREAK_INVEST)
					{
						if(tmp.getAmountType() == SETTConstant.AmountType.AmountType_22 && marginAmount < 0 && tmp.getDirection() == SETTConstant.DebitOrCredit.DEBIT){//差额为负，记“贷”方
							continue;
						}
						if(tmp.getAmountType() == SETTConstant.AmountType.AmountType_22 && marginAmount > 0 && tmp.getDirection() == SETTConstant.DebitOrCredit.CREDIT){//差额为正，记“借”方
							continue;
						}
					}
					//同业资产转让卖出买断发放,差额选择性生成分录
					if(tmp.getTransactionType() == SETTConstant.TransactionType.SAME_BUSINESS_GRANT && tmp.getSubTransactionType() == SETTConstant.SubTransactionType.BREAK_NOTIFY)
					{
						if(tmp.getAmountType() == SETTConstant.AmountType.AmountType_22 && marginAmount < 0 && tmp.getDirection() == SETTConstant.DebitOrCredit.CREDIT){//差额为负，记“借”方
							continue;
						}
						if(tmp.getAmountType() == SETTConstant.AmountType.AmountType_22 && marginAmount > 0 && tmp.getDirection() == SETTConstant.DebitOrCredit.DEBIT){//差额为正，记“贷”方
							continue;
						}
					}
					
					//同业资产转让购回【 买入(回购)购回】费用调整部分选择性生成分录
					if(tmp.getTransactionType() == SETTConstant.TransactionType.SAME_BUSINESS_RECEIVE && tmp.getSubTransactionType() == SETTConstant.SubTransactionType.REPURCHASE_INVEST)
					{
						if(tmp.getAmountType() == SETTConstant.AmountType.AmountType_23 && adjustFee < 0 && tmp.getDirection() == SETTConstant.DebitOrCredit.CREDIT){//费用调整为负，记“借”方
							continue;
						}
						if(tmp.getAmountType() == SETTConstant.AmountType.AmountType_23 && adjustFee > 0 && tmp.getDirection() == SETTConstant.DebitOrCredit.DEBIT){//费用调整为正，记“贷”方
							continue;
						}
					}
					
					//同业资产转让购回【 卖出(回购)购回】费用调整部分选择性生成分录
					if(tmp.getTransactionType() == SETTConstant.TransactionType.SAME_BUSINESS_RECEIVE && tmp.getSubTransactionType() == SETTConstant.SubTransactionType.REPURCHASE_NOTIFY)
					{
						if(tmp.getAmountType() == SETTConstant.AmountType.AmountType_23 && adjustFee < 0 && tmp.getDirection() == SETTConstant.DebitOrCredit.DEBIT){//费用调整为负，记“贷”方
							continue;
						}
						if(tmp.getAmountType() == SETTConstant.AmountType.AmountType_23 && adjustFee > 0 && tmp.getDirection() == SETTConstant.DebitOrCredit.CREDIT){//费用调整为正，记“借”方
							continue;
						}
					}
					//end add by dwj 20120202
					
					double dAmount = 0.0;
					log.debug("---------金额类型是: " + tmp.getAmountType() + "------------");
					switch ((int) tmp.getAmountType())
					{
						case SETTConstant.AmountType.AmountType_1 :
							{
								dAmount = principalOrTransAmount;
							}
							break;
						case SETTConstant.AmountType.AmountType_2 :
							{
								dAmount = totalInterest;
							}
							break;
						case SETTConstant.AmountType.AmountType_3 :
							{
								dAmount = preDrawInterest;
							}
							break;
						case SETTConstant.AmountType.AmountType_4 :
							{
								dAmount = unPreDrawInterest;
							}
							break;
						case SETTConstant.AmountType.AmountType_5 :
							{
								dAmount = overTimeInterest;
							}
							break;
						case SETTConstant.AmountType.AmountType_6 :
							{
								dAmount = overFee;
							}
							break;
						case SETTConstant.AmountType.AmountType_7 :
							{
								dAmount = compoundInterest;
							}
							break;
						case SETTConstant.AmountType.AmountType_8 :
							{
								dAmount = suretyFee;
							}
							break;
						case SETTConstant.AmountType.AmountType_9 :
							{
								dAmount = commissionFee;
							}
							break;
						case SETTConstant.AmountType.AmountType_10 :
							{
								dAmount = interestTaxFee;
							}
							break;
						case SETTConstant.AmountType.AmountType_11 :
							{
								dAmount = totalPrincipalAndInterest;
							}
							break;
						case SETTConstant.AmountType.AmountType_12 :
							{
								dAmount = remissionInterest;
							}
							break;
						case SETTConstant.AmountType.AmountType_13 :
							{
								dAmount = reallyReceiveInterest;
							}
							break;
						case SETTConstant.AmountType.AmountType_14 :
								{
									dAmount = RemitAmount;
								}
								break;
						case SETTConstant.AmountType.AmountType_15 ://融资租赁保证金本息合计
						{
							dAmount = FinanceAllAmount;
						}
						break;
						case SETTConstant.AmountType.AmountType_16 ://手续费及佣金
						{
							dAmount = commissionFee;
						}
						break;
						case SETTConstant.AmountType.AmountType_17 ://融资租赁活期本息合计
						{
							dAmount = currentPrincipalAndInterest;
						}
						break;
						case SETTConstant.AmountType.AmountType_18 ://融资租赁开户行本息合计
						{
							dAmount = bankPrincipalAndInterest;
						}
						break;
						//add by dwj 同步方正
						case SETTConstant.AmountType.AmountType_20 ://转让金额
						{
							dAmount = attornAmount;
						}
						break;	
						case SETTConstant.AmountType.AmountType_21 ://转让价格
						{
							dAmount = attornPrice;
						}
						break;	
						case SETTConstant.AmountType.AmountType_22 ://差额
						{
							dAmount = Math.abs(marginAmount);
						}
						break;	
						case SETTConstant.AmountType.AmountType_23 ://费用调整
						{
							dAmount = Math.abs(adjustFee);
						}
						break;
						//end add by dwj 
						default :
							return false;
					}
					log.debug("---------金额是: " + dAmount + "------------");
					if (dAmount == 0.0)
						continue;
						
					int subjectType = (int) tmp.getSubjectType();
					log.debug("-----会计分录定义的科目号是：" + subjectType + "--------");
					//根据科目类型、账户ID获得科目号
					String strSubject = null;
					//银团详细信息科目类型 add by zcwang 2007-6-27
					String YTstrSubject[] = null;
					//银团详细信息金额  add by zcwang 2007-6-27
					double YTAmount[] = null;
					//信贷转让明细金额 add by xwhe 
					String ZYstrSubject[] = null;
					
					//add by dwj 20120202 移值同业往来(参考方正项目)
					//资产转让：自营贷款本金科目类型
					String[] ZYZRstrSubject = null;
					//资产转让：自营贷款放款单转让详细金额
					double[] ZYZRAmount = null;
					//资产转让：自营贷款放款单计提利息
					double[] ZYZRPerDrawInterest = null;
					//资产转让：自营贷款放款单未计提利息
					double[] ZYZRInterest = null;
					//资产转让：自营贷款贷款客户信息，用于生成辅助核算信息
					String[] ZYZRClientNO = null;
					
					
					//转贴现卖出买断：票面金额，本息合计科目类型
					String[] ZTXstrSubject = null;
					//转贴现卖出买断：金额，本息合计单张金额、贴现贷款/转贴现本金
					double[] ZTXAmount = null;
					//贴现利息
					//double[] ZTXDiscountInterest = null;
					//实收利息，利息收入
					//double[] ZTXRealInterest = null;
					
					//转贴现卖出买断：客户信息，用于生成辅助核算信息
					String[] ZTXClientNO = null;
					//end add by dwj 20120202 移值同业往来(参考方正项目)
					
					//生成账户子系统接口类
					log.debug("--------根据子账户ID查询对应科目号------------");
					log.debug("--------科目类型是：" + subjectType + "-----------");
					//conn可能为空或者不为空，如果是空，则表明是由EJB初使化本类，AccountOperation将
					//初使化AccountEJB，否则则为Java类调用，构造函数不做任何操作
					
					AccountOperation accountOperation = new AccountOperation(conn);
					
					switch (subjectType)
					{
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_1 :
						{
							log.debug("科目类型1账户接口:"+accountOperation);
							strSubject = accountOperation.getSubjectBySubAccountID(receiveAccountID,AccountOperation.SUBJECT_TYPE_ACCOUNT);
							clientno = accountOperation.findClientCodeBySubAccountID(receiveAccountID);
							//accountType = accountOperation.findAccountTypeBySubAccountID(receiveAccountID);
						}
						break;
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_2 :
						{
						  
							strSubject = accountOperation.getSubjectBySubAccountID(payAccountID,AccountOperation.SUBJECT_TYPE_ACCOUNT);
							clientno = accountOperation.findClientCodeBySubAccountID(payAccountID);
							//accountType = accountOperation.findAccountTypeBySubAccountID(payAccountID);
							log.info("-------付款方科目名称是：" + strSubject + "-------");
						}
						break;
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_3 :
						{
							strSubject = accountOperation.getSubjectBySubAccountID(receiveInterestAccountID,AccountOperation.SUBJECT_TYPE_ACCOUNT);
							clientno = accountOperation.findClientCodeBySubAccountID(receiveInterestAccountID);
							//accountType = accountOperation.findAccountTypeBySubAccountID(receiveInterestAccountID);
						}
						break;
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_4 :
						{
							strSubject = accountOperation.getSubjectBySubAccountID(payInterestAccountID,AccountOperation.SUBJECT_TYPE_ACCOUNT);
							clientno = accountOperation.findClientCodeBySubAccountID(payInterestAccountID);
							//accountType = accountOperation.findAccountTypeBySubAccountID(payInterestAccountID);
						}
						break;
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_5 :
						{
							strSubject = accountOperation.getSubjectBySubAccountID(vouchReceiveAccountID,AccountOperation.SUBJECT_TYPE_ACCOUNT);
							clientno = accountOperation.findClientCodeBySubAccountID(vouchReceiveAccountID);
							//accountType = accountOperation.findAccountTypeBySubAccountID(vouchReceiveAccountID);
						}
						break;
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_6 :
						{
							strSubject = accountOperation.getSubjectBySubAccountID(vouchPayAccountID,AccountOperation.SUBJECT_TYPE_ACCOUNT);
							clientno = accountOperation.findClientCodeBySubAccountID(vouchPayAccountID);
							//accountType = accountOperation.findAccountTypeBySubAccountID(vouchPayAccountID);
						}
						break;
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_7 :
						{
							strSubject = accountOperation.getSubjectBySubAccountID(receieveSuertyFeeAccountID,AccountOperation.SUBJECT_TYPE_ACCOUNT);
							clientno = accountOperation.findClientCodeBySubAccountID(receieveSuertyFeeAccountID);
							//accountType = accountOperation.findAccountTypeBySubAccountID(receieveSuertyFeeAccountID);
						}
						break;
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_8 :
						{
							strSubject = accountOperation.getSubjectBySubAccountID(paySuertyFeeAccountID,AccountOperation.SUBJECT_TYPE_ACCOUNT);
						
							clientno = accountOperation.findClientCodeBySubAccountID(paySuertyFeeAccountID);
							//accountType = accountOperation.findAccountTypeBySubAccountID(paySuertyFeeAccountID);
						}
						break;
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_9 :
						{
							strSubject = accountOperation.getSubjectBySubAccountID(payCommissionAccountID,AccountOperation.SUBJECT_TYPE_ACCOUNT);
							clientno = accountOperation.findClientCodeBySubAccountID(payCommissionAccountID);
							//accountType = accountOperation.findAccountTypeBySubAccountID(payCommissionAccountID);
						}
						break;
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_10 :
						{
						    if(param.getTransactionTypeID()==SETTConstant.TransactionType.TRANSFERPAY 
									|| param.getTransactionTypeID()==SETTConstant.TransactionType.TRANSFERREPAY )
						    {
								 if (param.getGenaralLedgerTypeID()> 0)
								{
									 Sett_GeneralLedgerDAO glDAO = new Sett_GeneralLedgerDAO();
									 strSubject = glDAO.findSubjectCodeByID(param.getGenaralLedgerTypeID());									
								}
								else
								{
								if (tmp.getAmountType() == SETTConstant.AmountType.AmountType_1 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_11 || tmp.getAmountType()==SETTConstant.AmountType.AmountType_16)
								{
									strSubject = accountOperation.getSubjectByBankID(principalBankID);
								}
								else if (tmp.getAmountType() == SETTConstant.AmountType.AmountType_2 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_3 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_4 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_5 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_6 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_7|| tmp.getAmountType() == SETTConstant.AmountType.AmountType_13)
									strSubject = accountOperation.getSubjectByBankID(interestBankID);
								else if (tmp.getAmountType() == SETTConstant.AmountType.AmountType_8 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_9)
									strSubject = accountOperation.getSubjectByBankID(feeBankID);										
								clientno = "";									 
								 }
						    }
						    else
						    {
							if (tmp.getAmountType() == SETTConstant.AmountType.AmountType_1 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_11 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_18)
							{
								strSubject = accountOperation.getSubjectByBankID(principalBankID);
							}
							else if (tmp.getAmountType() == SETTConstant.AmountType.AmountType_2 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_3 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_4 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_5 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_6 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_7|| tmp.getAmountType() == SETTConstant.AmountType.AmountType_13)
								strSubject = accountOperation.getSubjectByBankID(interestBankID);
							else if (tmp.getAmountType() == SETTConstant.AmountType.AmountType_8 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_9)
								strSubject = accountOperation.getSubjectByBankID(feeBankID);
							
							clientno = "";
						    }
						}
						break;
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_11 :
						{
							//update by dwj 20120202 移值同业往来(参考方正项目)
							if(param.getTransactionTypeID() == SETTConstant.TransactionType.SAME_BUSINESS_GRANT && param.getSubTransactionType()== SETTConstant.SubTransactionType.BREAK_NOTIFY)
							{
								//资产转让卖出买断发放
								if(param.getAttornmentContractList() != null && param.getAttornmentContractList().size() > 0){
									ZYZRstrSubject = new String[param.getAttornmentContractList().size()];
									ZYZRClientNO = new String[param.getAttornmentContractList().size()];
									ZYZRInterest = new double[param.getAttornmentContractList().size()];
									Sett_TransGrantLoanDAO sett_TransGrantLoanDAO = new Sett_TransGrantLoanDAO();
									Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO();
									TransGrantLoanInfo tmpGrantLoanInfo = null;
									long lLoanPayTmpId = -1;
									long lLoanAccountTmpId = -1;
									SubAccountAssemblerInfo tmpSubAccountAssemblerInfo = null;
									SubAccountLoanInfo tmpSubAccountLoanInfo = null;
									
									for(int n=0;n<param.getAttornmentContractList().size();n++)
									{   
										lLoanPayTmpId = -1;
										lLoanAccountTmpId = -1; 
										tmpSubAccountAssemblerInfo = null;
										tmpSubAccountLoanInfo = null;
										AttornmentContractInfo attornmentContractInfo = (AttornmentContractInfo)param.getAttornmentContractList().get(n);
										lLoanPayTmpId = attornmentContractInfo.getPayId();
										
										tmpGrantLoanInfo = sett_TransGrantLoanDAO.findByLoanNoteID(lLoanPayTmpId);
										lLoanAccountTmpId = (tmpGrantLoanInfo == null ? -1 : tmpGrantLoanInfo.getLoanAccountID());
										
										tmpSubAccountAssemblerInfo = subAccountDAO.findByLoanNoteID(lLoanAccountTmpId, lLoanPayTmpId);
										if(tmpSubAccountAssemblerInfo != null){
											tmpSubAccountLoanInfo = tmpSubAccountAssemblerInfo.getSubAccountLoanInfo();
										}
										if(tmpSubAccountLoanInfo != null){
											strSubject = accountOperation.getSubjectBySubAccountID(tmpSubAccountLoanInfo.getID(),AccountOperation.SUBJECT_TYPE_INTEREST);
											
											ZYZRstrSubject[n] = strSubject;
											ZYZRInterest[n] = DataFormat.formatDouble(tmpSubAccountLoanInfo.getInterest())-DataFormat.formatDouble(tmpSubAccountLoanInfo.getPreDrawInterest());
											ZYZRClientNO[n] = NameRef.getClientCodeByAccountID(lLoanAccountTmpId);
										}
									}
								}
							
							}
							else
							{
								strSubject = accountOperation.getSubjectBySubAccountID(receiveAccountID,AccountOperation.SUBJECT_TYPE_INTEREST);
								//Modify by leiyang 2008-07-02
								//判断自营贷款结息时生成的会计分录中，利息税费科目不附加辅助核算值
								//clientno = accountOperation.findClientCodeBySubAccountID(receiveAccountID);
								//add by dwj 20110526 添加辅助核算项
								//clientno = "";
								clientno = accountOperation.findClientCodeBySubAccountID(receiveAccountID);
								//end add by dwj 20110526
								//accountType = accountOperation.findAccountTypeBySubAccountID(receiveAccountID);
							}
							//update by dwj 20120202 移值同业往来(参考方正项目)
						}
						break;
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_12 :
						{
							strSubject = accountOperation.getSubjectBySubAccountID(payAccountID,AccountOperation.SUBJECT_TYPE_INTEREST);
							clientno = accountOperation.findClientCodeBySubAccountID(payAccountID);
							//accountType = accountOperation.findAccountTypeBySubAccountID(payAccountID);
						}
						break;
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_13 :
						{
							strSubject = accountOperation.getSubjectBySubAccountID(receiveAccountID,AccountOperation.SUBJECT_TYPE_INTEREST);
							clientno = accountOperation.findClientCodeBySubAccountID(receiveAccountID);
							//accountType = accountOperation.findAccountTypeBySubAccountID(receiveAccountID);
						}
						break;
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_14 :
						{
							strSubject = accountOperation.getSubjectBySubAccountID(payAccountID,AccountOperation.SUBJECT_TYPE_INTEREST);
							clientno = accountOperation.findClientCodeBySubAccountID(payAccountID);
							//accountType = accountOperation.findAccountTypeBySubAccountID(payAccountID);
						}
						break;
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_15 :
						{
							//update by dwj 20120202 移值同业往来(参考方正项目)
							//资产转让卖出买断发放
							if(param.getTransactionTypeID() == SETTConstant.TransactionType.SAME_BUSINESS_GRANT && param.getSubTransactionType()== SETTConstant.SubTransactionType.BREAK_NOTIFY)
							{
								if(param.getAttornmentContractList() != null && param.getAttornmentContractList().size() > 0){
									ZYZRstrSubject = new String[param.getAttornmentContractList().size()];
									ZYZRClientNO = new String[param.getAttornmentContractList().size()];
									ZYZRPerDrawInterest = new double[param.getAttornmentContractList().size()];
									Sett_TransGrantLoanDAO sett_TransGrantLoanDAO = new Sett_TransGrantLoanDAO();
									Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO();
									TransGrantLoanInfo tmpGrantLoanInfo = null;
									long lLoanPayTmpId = -1;
									long lLoanAccountTmpId = -1;
									long lSubLoanAccountTmpId = -1;
									SubAccountAssemblerInfo tmpSubAccountAssemblerInfo = null;
									SubAccountLoanInfo tmpSubAccountLoanInfo = null;
									
									for(int n=0;n<param.getAttornmentContractList().size();n++)
									{   
										lLoanPayTmpId = -1;
										lLoanAccountTmpId = -1; 
										tmpSubAccountAssemblerInfo = null;
										tmpSubAccountLoanInfo = null;
										AttornmentContractInfo attornmentContractInfo = (AttornmentContractInfo)param.getAttornmentContractList().get(n);
										lLoanPayTmpId = attornmentContractInfo.getPayId();
										
										tmpGrantLoanInfo = sett_TransGrantLoanDAO.findByLoanNoteID(lLoanPayTmpId);
										lLoanAccountTmpId = (tmpGrantLoanInfo == null ? -1 : tmpGrantLoanInfo.getLoanAccountID());
										
										tmpSubAccountAssemblerInfo = subAccountDAO.findByLoanNoteID(lLoanAccountTmpId, lLoanPayTmpId);
										if(tmpSubAccountAssemblerInfo != null){
											tmpSubAccountLoanInfo = tmpSubAccountAssemblerInfo.getSubAccountLoanInfo();
										}
										if(tmpSubAccountLoanInfo != null){
											strSubject = accountOperation.getSubjectBySubAccountID(tmpSubAccountLoanInfo.getID(),AccountOperation.SUBJECT_TYPE_PREDRAWINTEREST);
											
											ZYZRstrSubject[n] = strSubject;
											ZYZRPerDrawInterest[n] = DataFormat.formatDouble(tmpSubAccountLoanInfo.getPreDrawInterest());
											ZYZRClientNO[n] = NameRef.getClientCodeByAccountID(lLoanAccountTmpId);
										}
										
									}
								}
							
							}
							else
							{
								strSubject = accountOperation.getSubjectBySubAccountID(receiveAccountID,AccountOperation.SUBJECT_TYPE_PREDRAWINTEREST);
								//Modify by leiyang 2008-07-02
								//判断自营贷款结息时生成的会计分录中，利息税费科目不附加辅助核算值
								//clientno = accountOperation.findClientCodeBySubAccountID(receiveAccountID);
								//add by dwj 20110526 添加辅助核算项
								//clientno = "";
								clientno = accountOperation.findClientCodeBySubAccountID(receiveAccountID);
								//end add by dwj 20110526
								//accountType = accountOperation.findAccountTypeBySubAccountID(receiveAccountID);
							}					
						}
						break;
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_16 :
						{
							strSubject = accountOperation.getSubjectBySubAccountID(payAccountID,AccountOperation.SUBJECT_TYPE_PREDRAWINTEREST);
							clientno = accountOperation.findClientCodeBySubAccountID(payAccountID);
							//accountType = accountOperation.findAccountTypeBySubAccountID(payAccountID);
						}
						break;
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_17 :
						{
							strSubject = accountOperation.getSubjectBySubAccountID(receiveAccountID,AccountOperation.SUBJECT_TYPE_INTERESTTAX);
							
							//判断委托贷款结息时生成的会计分录中，利息税费科目不附加辅助核算值
							//clientno = accountOperation.findClientCodeBySubAccountID(receiveAccountID);
							clientno = "";
						}
						break;
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_18 :
						{
							strSubject = accountOperation.getSubjectBySubAccountID(payAccountID,AccountOperation.SUBJECT_TYPE_INTERESTTAX);
							
							//判断委托贷款结息时生成的会计分录中，利息税费科目不附加辅助核算值
							//clientno = accountOperation.findClientCodeBySubAccountID(payAccountID);
							clientno = "";
						}
						break;	
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_19 :
						{
							strSubject = accountOperation.getSubjectBySubAccountID(receiveAccountID,AccountOperation.SUBJECT_TYPE_NEGOTIATEINTEREST);
							clientno = accountOperation.findClientCodeBySubAccountID(receiveAccountID);
							//accountType = accountOperation.findAccountTypeBySubAccountID(receiveAccountID);
						}
						break;
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_20 :
						{
							strSubject = accountOperation.getSubjectBySubAccountID(payAccountID,AccountOperation.SUBJECT_TYPE_NEGOTIATEINTEREST);
							clientno = accountOperation.findClientCodeBySubAccountID(payAccountID);
							//accountType = accountOperation.findAccountTypeBySubAccountID(payAccountID);
						}
						break;
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_21 :
						{
							strSubject = accountOperation.getSubjectBySubAccountID(receiveAccountID,AccountOperation.SUBJECT_TYPE_COMMISSION);
							clientno = accountOperation.findClientCodeBySubAccountID(receiveAccountID);
							//accountType = accountOperation.findAccountTypeBySubAccountID(receiveAccountID);
						}	
						break;	
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_22 :
						{
							strSubject = accountOperation.getSubjectBySubAccountID(payAccountID,AccountOperation.SUBJECT_TYPE_COMMISSION);
							clientno = accountOperation.findClientCodeBySubAccountID(payAccountID);
							//accountType = accountOperation.findAccountTypeBySubAccountID(payAccountID);
						}
						break;
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_23 :
					{
						strSubject = accountOperation.getSubjectByGLTypeID(genaralLedgerTypeID);
						//clientno = accountOperation.getAccountToClientNo(receiveAccountID);
						clientno = "";
					}
					break;
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_24 :	//融资租赁保证金收款方帐户
					{
						System.out.println("融资租赁保证金收款方帐户:"+receiveFinanceAccountID);
						strSubject = accountOperation.getSubjectBySubAccountID(receiveFinanceAccountID,AccountOperation.SUBJECT_TYPE_ACCOUNT);
						clientno = accountOperation.findClientCodeBySubAccountID(receiveFinanceAccountID);
						//accountType = accountOperation.findAccountTypeBySubAccountID(receiveFinanceAccountID);
					}
					break;
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_25 :	//融资租赁保证金付款方帐户
					{
						System.out.println("融资租赁保证金付款方帐户:"+payFinanceAccountID);
						strSubject = accountOperation.getSubjectBySubAccountID(payFinanceAccountID,AccountOperation.SUBJECT_TYPE_ACCOUNT);
						clientno = accountOperation.findClientCodeBySubAccountID(payFinanceAccountID);
						//accountType = accountOperation.findAccountTypeBySubAccountID(payFinanceAccountID);
					}
					break;
					//add by zcwang 2007-6-27 增加银团贷款参与行科目类型
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_26 :
					{	
						
						if(param.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANRECEIVE 
								|| param.getTransactionTypeID()==SETTConstant.TransactionType.YT_LOAN_INTEREST 
								|| param.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANGRANT
								|| param.getTransactionTypeID()==SETTConstant.TransactionType.YT_LOAN_PREDRAW_INTEREST)
						{  
							
							if(tmp.getAmountType() == SETTConstant.AmountType.AmountType_1 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_11)
							{
								if(param.getList()!=null && param.getList().size()>0)
								{
									YTstrSubject=new String[param.getList().size()];
									YTAmount =new double[param.getList().size()];
									for(int n=0;n<param.getList().size();n++)
									{
										if(param.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
										{
											SyndicationLoanInterestInfo syndicationLoanInterestInfo = new SyndicationLoanInterestInfo();
											syndicationLoanInterestInfo = (SyndicationLoanInterestInfo)param.getList().get(n);	
											strSubject = NameRef.getSubjectByBankID(syndicationLoanInterestInfo.getBankID());
											YTstrSubject[n]=strSubject;
											YTAmount[n]=syndicationLoanInterestInfo.getAmount();
										}
										else if(param.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANGRANT)
										{
											BankLoanDrawInfo resultInfo = new BankLoanDrawInfo();
											resultInfo = (BankLoanDrawInfo)param.getList().get(n);	
											strSubject = NameRef.getSubjectByBankID(resultInfo.getBankID());
											YTstrSubject[n]=strSubject;
											//YTAmount[n]=dAmount*(resultInfo.getRate()/100);
											YTAmount[n]=resultInfo.getDrawAmount();
										
										}
										
									}
								}
							}
							else if(tmp.getAmountType() == SETTConstant.AmountType.AmountType_2 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_3 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_4 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_5 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_6 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_7|| tmp.getAmountType() == SETTConstant.AmountType.AmountType_13)
							{
								
								if(param.getList()!=null && param.getList().size()>0)
								{
									YTstrSubject=new String[param.getList().size()];
									YTAmount =new double[param.getList().size()];
									double allDrawAmount = 0.00;
									if(param.getTransactionTypeID()==SETTConstant.TransactionType.YT_LOAN_INTEREST
											 || param.getTransactionTypeID()==SETTConstant.TransactionType.YT_LOAN_PREDRAW_INTEREST)
									{
										for(int n=0;n<param.getList().size();n++)
										{
											BankLoanDrawInfo resultInfo = new BankLoanDrawInfo();
											resultInfo = (BankLoanDrawInfo)param.getList().get(n);
											allDrawAmount=allDrawAmount+resultInfo.getDrawAmount();
										}
									}
									for(int n=0;n<param.getList().size();n++)
									{   if(param.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
										{
											SyndicationLoanInterestInfo syndicationLoanInterestInfo = new SyndicationLoanInterestInfo();
											syndicationLoanInterestInfo = (SyndicationLoanInterestInfo)param.getList().get(n);
											strSubject = NameRef.getSubjectByBankID(syndicationLoanInterestInfo.getBankID());
											YTstrSubject[n]=strSubject;
											YTAmount[n]=syndicationLoanInterestInfo.getInterest()+syndicationLoanInterestInfo.getCompoundInterest()+syndicationLoanInterestInfo.getForpeitInterest();
										}
										else if(param.getTransactionTypeID()==SETTConstant.TransactionType.YT_LOAN_INTEREST
												 || param.getTransactionTypeID()==SETTConstant.TransactionType.YT_LOAN_PREDRAW_INTEREST)
										{
											BankLoanDrawInfo resultInfo = new BankLoanDrawInfo();
											resultInfo = (BankLoanDrawInfo)param.getList().get(n);	
											strSubject = NameRef.getSubjectByBankID(resultInfo.getBankID());
											YTstrSubject[n]=strSubject;
											//YTAmount[n]=dAmount*(resultInfo.getRate()/100);
											YTAmount[n]= dAmount * resultInfo.getDrawAmount()/allDrawAmount;
										
										}
									}
								}
							}
							}
						else
						{
							System.out.println("银团贷款对应的开户行ID:"+principalBankID);
							strSubject = NameRef.getSubjectByBankID(principalBankID);
						}
					}
					break;
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_27 :	//金融利息支出科目
					{
						if(param.getTransactionTypeID()==SETTConstant.TransactionType.TRANSFERPAY)
						{
						    strSubject = accountOperation.getSubjectByOther(param.getCraContractID(),AccountOperation.SUBJECT_TYPE_EXPENSE);
						    clientno = accountOperation.findClientCodeBySubAccountID(payInterestAccountID);
						}
						else if(param.getTransactionTypeID()==SETTConstant.TransactionType.REPURCHASEDRAW)
						{
							strSubject = accountOperation.getSubjectByOther(param.getCraContractID(),AccountOperation.SUBJECT_TYPE_EXPENSE);
						}
						else
						{
							
						}
					}
					break;
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_28 :	//金融企业往来应付利息科目
					{
						if(param.getTransactionTypeID()==SETTConstant.TransactionType.TRANSFERPAY)
						{
						    strSubject = accountOperation.getSubjectByOther(param.getCraContractID(),AccountOperation.SUBJECT_TYPE_HANDLE);
						    //clientno = accountOperation.findClientCodeBySubAccountID(payInterestAccountID);
						}
						else if(param.getTransactionTypeID()==SETTConstant.TransactionType.REPURCHASEDRAW)
						{
							strSubject = accountOperation.getSubjectByOther(param.getCraContractID(),AccountOperation.SUBJECT_TYPE_HANDLE);
						}
						else
						{
							
						}
					}
					break;
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_29 :	//卖出回购金融资产款
					{
						if(param.getTransactionTypeID()==SETTConstant.TransactionType.TRANSFERPAY)
						{
						    strSubject = accountOperation.getSubjectByOther(param.getCraContractID(),AccountOperation.SUBJECT_TYPE_REPURCHASE_NOTIFY);
						    //clientno = accountOperation.findClientCodeBySubAccountID(payInterestAccountID);
						}
						else if(param.getTransactionTypeID()==SETTConstant.TransactionType.TRANSFERREPAY)
						{
							 strSubject = accountOperation.getSubjectByOther(param.getCraContractID(),AccountOperation.SUBJECT_TYPE_REPURCHASE_NOTIFY);
						}
					}
					break;
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_30 :	//自营贷款本金
					{
					//   if(param.getTransactionTypeID()==SETTConstant.TransactionType.TRANSFERPAY)
					//	{
					//	    strSubject = accountOperation.getSubjectByOther(param.getCraContractID(),AccountOperation.SUBJECT_TYPE_ZYAMOUNT);
					//	    //clientno = accountOperation.findClientCodeBySubAccountID(payInterestAccountID);
					//	}
					//	else if(param.getTransactionTypeID()==SETTConstant.TransactionType.TRANSFERREPAY)
					//	{
					//		 strSubject = accountOperation.getSubjectByOther(param.getCraContractID(),AccountOperation.SUBJECT_TYPE_ZYAMOUNT);
					//	}
						//update by dwj 20120206
						if(param.getTransactionTypeID()==SETTConstant.TransactionType.TRANS_DISCOUNT_GRANT
								||param.getTransactionTypeID()==SETTConstant.TransactionType.TRANS_DISCOUNT_RECEIVE
								||param.getTransactionTypeID()==SETTConstant.TransactionType.TRANS_DISCOUNT_REPURCHASE
								||param.getTransactionTypeID()==SETTConstant.TransactionType.TRANS_DISCOUNT_PREDRAW_INTEREST
								||param.getTransactionTypeID()==SETTConstant.TransactionType.FUND_ATTORN
								||param.getTransactionTypeID()==SETTConstant.TransactionType.FUND_ATTORN_REPAY
								||param.getTransactionTypeID()==SETTConstant.TransactionType.SAME_BUSINESS_GRANT
								||param.getTransactionTypeID()==SETTConstant.TransactionType.SAME_BUSINESS_RECEIVE
								||param.getTransactionTypeID()==SETTConstant.TransactionType.SAME_BUSINESS_INTERESTPROCESS
								||param.getTransactionTypeID()==SETTConstant.TransactionType.SAME_BUSINESS_INTERESTRECEIVE)
						{
							if(param.getAttornmentContractList() != null && param.getAttornmentContractList().size() > 0)
							{

								ZYZRstrSubject = new String[param.getAttornmentContractList().size()];
								ZYZRAmount = new double[param.getAttornmentContractList().size()];
								ZYZRClientNO = new String[param.getAttornmentContractList().size()];//方正生成辅助核算信息
								Sett_TransGrantLoanDAO sett_TransGrantLoanDAO = new Sett_TransGrantLoanDAO();
								Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO();
								TransGrantLoanInfo tmpGrantLoanInfo = null;
								long lLoanPayTmpId = -1;
								long lLoanAccountTmpId = -1; 
								SubAccountAssemblerInfo tmpSubAccountAssemblerInfo = null;
								SubAccountLoanInfo tmpSubAccountLoanInfo = null;
								for(int n=0;n<param.getAttornmentContractList().size();n++)
								{   
									lLoanPayTmpId = -1;
									lLoanAccountTmpId = -1; 
									tmpSubAccountAssemblerInfo = null;
									tmpSubAccountLoanInfo = null;
									AttornmentContractInfo attornmentContractInfo = (AttornmentContractInfo)param.getAttornmentContractList().get(n);
									lLoanPayTmpId = attornmentContractInfo.getPayId();
									tmpGrantLoanInfo = sett_TransGrantLoanDAO.findByLoanNoteID(lLoanPayTmpId);
									lLoanAccountTmpId = (tmpGrantLoanInfo == null ? -1 : tmpGrantLoanInfo.getLoanAccountID());
									tmpSubAccountAssemblerInfo = subAccountDAO.findByLoanNoteID(lLoanAccountTmpId, lLoanPayTmpId);
									if(tmpSubAccountAssemblerInfo != null){
										tmpSubAccountLoanInfo = tmpSubAccountAssemblerInfo.getSubAccountLoanInfo();
									}else{//查找结清状态的子账户
										tmpSubAccountAssemblerInfo = subAccountDAO.findByLoanNoteID1(lLoanAccountTmpId, lLoanPayTmpId);
										if(tmpSubAccountAssemblerInfo != null){
											tmpSubAccountLoanInfo = tmpSubAccountAssemblerInfo.getSubAccountLoanInfo();
										}
									}
									if(tmpSubAccountLoanInfo != null){
										strSubject = accountOperation.getSubjectBySubAccountID(tmpSubAccountLoanInfo.getID(),AccountOperation.SUBJECT_TYPE_ACCOUNT);
									}
									ZYZRstrSubject[n] = strSubject;
									ZYZRAmount[n] = attornmentContractInfo.getAttornmentAmount();
									ZYZRClientNO[n] = NameRef.getClientCodeByAccountID(lLoanAccountTmpId);
								}
							
							}
							else
							{
								strSubject = new TransCraftbrotherDAO().getSubjectCode(param.getCraBusinessType(), param.getCounterpartId(), lSubTransactionType, subjectType, param.getOfficeID(), param.getCurrencyID());
							}
						}
						else //update by dwj 20120206
						{
							if(param.getList()!=null && param.getList().size()>0)
							{
								TransferLoanAmountDetailinfo detailinfo=null;
								Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conn);
								SubAccountLoanInfo subInfo = new SubAccountLoanInfo();
								ZYstrSubject=new String[param.getList().size()];
								for(int n=0;n<param.getList().size();n++)
								{						   
								   detailinfo=(TransferLoanAmountDetailinfo)param.getList().get(n);
								   subInfo.setLoanNoteID(detailinfo.getNoticeformid());
								   subInfo = sett_SubAccountDAO.querySubInfo(subInfo);
								   strSubject = accountOperation.getSubjectBySubAccountID(subInfo.getID(),AccountOperation.SUBJECT_TYPE_ACCOUNT);
								   ZYstrSubject[n]=strSubject;
								}
							}
						}
					}
					break;
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_31 :	//手续费及佣金收入
					{
						if(param.getTransactionTypeID()==SETTConstant.TransactionType.TRANSFERPAY)
						{
						    strSubject = accountOperation.getSubjectByOther(param.getCraContractID(),AccountOperation.SUBJECT_TYPE_ZRCOMMISSION);
						    //clientno = accountOperation.findClientCodeBySubAccountID(payInterestAccountID);
						}
						else if(param.getTransactionTypeID()==SETTConstant.TransactionType.TRANSFERREPAY)
						{
							strSubject = accountOperation.getSubjectByOther(param.getCraContractID(),AccountOperation.SUBJECT_TYPE_ZRCOMMISSION);
						}
						else if(param.getTransactionTypeID()==SETTConstant.TransactionType.AGENTTRANSFERREPAY ||param.getTransactionTypeID()==SETTConstant.TransactionType.BREAKINTERESTNOTIFY)
						{
							strSubject = accountOperation.getSubjectByOther(param.getCraContractID(),AccountOperation.SUBJECT_TYPE_ZRCOMMISSION);
						}
					}
					break;
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_32 :	//应付卖断利息
					{
						if(param.getTransactionTypeID()==SETTConstant.TransactionType.TRANSFERPAY)
						{
						    strSubject = accountOperation.getSubjectByOther(param.getCraContractID(),AccountOperation.SUBJECT_TYPE_MDINTEREST);
						    //clientno = accountOperation.findClientCodeBySubAccountID(payInterestAccountID);
						}
						else if(param.getTransactionTypeID()==SETTConstant.TransactionType.AGENTTRANSFERREPAY || param.getTransactionTypeID()==SETTConstant.TransactionType.BREAKINTERESTNOTIFY)
						{
							strSubject = accountOperation.getSubjectByOther(param.getCraContractID(),AccountOperation.SUBJECT_TYPE_MDINTEREST);
						}
					}
					break;
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_33 :	//应付卖断本金
					{
						if(param.getTransactionTypeID()==SETTConstant.TransactionType.TRANSFERPAY)
						{
						    strSubject = accountOperation.getSubjectByOther(param.getCraContractID(),AccountOperation.SUBJECT_TYPE_MDAMOUNT);
						   // clientno = accountOperation.findClientCodeBySubAccountID(payInterestAccountID);
						}
						else if(param.getTransactionTypeID()==SETTConstant.TransactionType.AGENTTRANSFERREPAY)
						{
							 strSubject = accountOperation.getSubjectByOther(param.getCraContractID(),AccountOperation.SUBJECT_TYPE_MDAMOUNT);
						}
					}
					break;
					
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_34 :
					{
						log.debug("科目类型:收方备付金账户");
						strSubject = accountOperation.getSubjectBySubAccountID(receivebakSubAccountID,AccountOperation.SUBJECT_TYPE_ACCOUNT);
						log.info("-------付方备付金账户科目名称是：" + strSubject + "-------");
					}
					break;
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_35 :
					{
						log.debug("科目类型:付方备付金账户");
						strSubject = accountOperation.getSubjectBySubAccountID(paybakSubAccountID,AccountOperation.SUBJECT_TYPE_ACCOUNT);
						log.info("-------付方备付金账户科目名称是：" + strSubject + "-------");
					}
					break;
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_36 :
					{
						log.debug("科目类型:收方机构备付金科目");
						OfficeClearingAccountSetDao ocadao = new OfficeClearingAccountSetDao();
						strSubject = ocadao.getSubjectByOfficeAndCurrency(param.getReceiveofficeID(), param.getCurrencyID());
						ocadao=null;
						log.info("-------收方机构备付金科目名称是：" + strSubject + "-------");
					}
					break;
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_37 :
					{
						log.debug("科目类型:付方机构备付金科目");
						OfficeClearingAccountSetDao ocadao = new OfficeClearingAccountSetDao();
						strSubject = ocadao.getSubjectByOfficeAndCurrency(param.getPayofficeID(), param.getCurrencyID());
						ocadao=null;
						log.info("-------付方机构备付金科目名称是：" + strSubject + "-------");
					}
					break;
					//add by dwj 移值同业往来(参考方正项目)
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_38:
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_39:
						strSubject = new TransCraftbrotherDAO().getSubjectCode(param.getCraBusinessType(), param.getCounterpartId(), lSubTransactionType, subjectType, param.getOfficeID(), param.getCurrencyID());
						break;
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_40:
					{
						if(param.getTransactionTypeID() == SETTConstant.TransactionType.TRANS_DISCOUNT_GRANT && param.getSubTransactionType()== SETTConstant.SubTransactionType.BREAK_NOTIFY){//转贴现卖出买断发放
							if(param.getTransDiscountContractBillList() != null && param.getTransDiscountContractBillList().size() > 0){
								ZTXstrSubject = new String[param.getTransDiscountContractBillList().size()];
								ZTXAmount = new double[param.getTransDiscountContractBillList().size()];
								ZTXClientNO = new String[param.getTransDiscountContractBillList().size()];
								
								//票据贴现发放信息
								TransGrantDiscountInfo transGrantDiscountInfo = null;
								Sett_TransGrantDiscountDAO gdDao = new Sett_TransGrantDiscountDAO();
								long lLoanPayTmpId = -1;
								long lLoanAccountTmpId = -1; 
								SubAccountAssemblerInfo tmpSubAccountAssemblerInfo = null;
								SubAccountLoanInfo tmpSubAccountLoanInfo = null;
								
								Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO();
								
								for(int n =0;n<param.getTransDiscountContractBillList().size();n++){
									lLoanPayTmpId = -1;
									lLoanAccountTmpId = -1; 
									TransDiscountContractBillInfo tdcBillInfo = (TransDiscountContractBillInfo)param.getTransDiscountContractBillList().get(n);
									if(tdcBillInfo != null && tdcBillInfo.getBillSourceTypeID()==LOANConstant.BillSourceType.DISCOUN){//贴现买入
										transGrantDiscountInfo = gdDao.findDiscountInfoByDiscountBillID(tdcBillInfo.getId());
										lLoanAccountTmpId = (transGrantDiscountInfo == null ? -1 : transGrantDiscountInfo.getDiscountAccountID());
										lLoanPayTmpId = tdcBillInfo.getDiscountCredenceID();
										tmpSubAccountAssemblerInfo = subAccountDAO.findByLoanNoteID1(lLoanAccountTmpId, lLoanPayTmpId);
										if(tmpSubAccountAssemblerInfo != null){
											tmpSubAccountLoanInfo = tmpSubAccountAssemblerInfo.getSubAccountLoanInfo();
										}
										if(tmpSubAccountLoanInfo != null){
											strSubject = accountOperation.getSubjectBySubAccountID(tmpSubAccountLoanInfo.getID(),AccountOperation.SUBJECT_TYPE_ACCOUNT);
										}
										ZTXClientNO[n] = NameRef.getClientCodeByAccountID(lLoanAccountTmpId);
										
									}else{//转贴现买入
										TransCraftbrotherDAO transDao = new TransCraftbrotherDAO();
										TransCraftbrotherInfo transInfo = transDao.findBreakInByBillId(tdcBillInfo.getId());
										//update by dwj 20120210
										//strSubject = new TransCraftbrotherDAO().getSubjectCode(transInfo.getNcraBusinessTypeId(), transInfo.getNcounterpartId(),transInfo.getNsubTransactionTypeId(), SETTConstant.EntrySubjectType.SUBJECT_TYPE_34, param.getOfficeID(), param.getCurrencyID());//买入时票面金额记收款方科目
										strSubject = new TransCraftbrotherDAO().getSubjectCode(transInfo.getNcraBusinessTypeId(), transInfo.getNcounterpartId(),transInfo.getNsubTransactionTypeId(), SETTConstant.EntrySubjectType.SUBJECT_TYPE_38, param.getOfficeID(), param.getCurrencyID());//买入时票面金额记收款方科目
										
										//strSubject = new TransCraftbrotherDAO().getSubjectCode(param.getCraBusinessType(), param.getCounterpartId(), lSubTransactionType, subjectType, param.getOfficeID(), param.getCurrencyID());
										ZTXClientNO[n] = "";
									}
									ZTXstrSubject[n] = strSubject;
									ZTXAmount[n] = tdcBillInfo.getAmount();
									
								}
							}
						}else{
							strSubject = new TransCraftbrotherDAO().getSubjectCode(param.getCraBusinessType(), param.getCounterpartId(), lSubTransactionType, subjectType, param.getOfficeID(), param.getCurrencyID());
						}
					}
						break;
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_41://收款方对应的利息收入科目
					{
						if(param.getTransactionTypeID() == SETTConstant.TransactionType.TRANS_DISCOUNT_GRANT && param.getSubTransactionType()== SETTConstant.SubTransactionType.BREAK_NOTIFY){//转贴现卖出买断发放
							if(param.getTransDiscountContractBillList() != null && param.getTransDiscountContractBillList().size() > 0){
								ZTXstrSubject = new String[param.getTransDiscountContractBillList().size()];
								ZTXAmount = new double[param.getTransDiscountContractBillList().size()];
								ZTXClientNO = new String[param.getTransDiscountContractBillList().size()];
								
								//同业交易信息
								long lCredenceTmpId = -1;
								long lTransDiscountContraceTmpId = -1;
								double lTransDiscountBillTmpInterest = 0.00;
								
								TransCraftbrotherDAO transDao = new TransCraftbrotherDAO();
								TransDiscountCredenceDAO credenceDao = new TransDiscountCredenceDAO("loan_discountcredence");
								TransCraftbrotherInfo transInfo = transDao.findByTransNo(param.getTransNo());
								TransDiscountCredenceInfo credenceInfo = null;
								credenceInfo = credenceDao.findCredenceInfoByID(transInfo.getNnoticeId());
								lTransDiscountContraceTmpId = credenceInfo.getContractID();
								
								
								//票据贴现发放信息
								TransGrantDiscountInfo transGrantDiscountInfo = null;
								Sett_TransGrantDiscountDAO gdDao = new Sett_TransGrantDiscountDAO();
								long lLoanPayTmpId = -1;
								long lLoanAccountTmpId = -1; 
								SubAccountAssemblerInfo tmpSubAccountAssemblerInfo = null;
								SubAccountLoanInfo tmpSubAccountLoanInfo = null;
								
								Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO();
								
								for(int n =0;n<param.getTransDiscountContractBillList().size();n++){
									lLoanPayTmpId = -1;
									lLoanAccountTmpId = -1; 
									TransDiscountContractBillInfo tdcBillInfo = (TransDiscountContractBillInfo)param.getTransDiscountContractBillList().get(n);
									if(tdcBillInfo != null && tdcBillInfo.getBillSourceTypeID()==LOANConstant.BillSourceType.DISCOUN){//贴现买入
										transGrantDiscountInfo = gdDao.findDiscountInfoByDiscountBillID(tdcBillInfo.getId());
										lLoanAccountTmpId = (transGrantDiscountInfo == null ? -1 : transGrantDiscountInfo.getDiscountAccountID());
										ZTXClientNO[n] = NameRef.getClientCodeByAccountID(lLoanAccountTmpId);	
									}else{//转贴现买入
										
										ZTXClientNO[n] = "";
									}
									strSubject = new TransCraftbrotherDAO().getSubjectCode(param.getCraBusinessType(), param.getCounterpartId(), lSubTransactionType, subjectType, param.getOfficeID(), param.getCurrencyID());
									
									lTransDiscountBillTmpInterest = transDao.getTransDiscountContractBillInterest(lTransDiscountContraceTmpId, tdcBillInfo.getId());
									ZTXstrSubject[n] = strSubject;
									ZTXAmount[n] =  UtilOperation.Arith.sub(DataFormat.formatDouble(tdcBillInfo.getAccrual(),2) , DataFormat.formatDouble(lTransDiscountBillTmpInterest,2));//利息收入=买入利息-卖出利息
									
								}
							}
						}else{
							strSubject = new TransCraftbrotherDAO().getSubjectCode(param.getCraBusinessType(), param.getCounterpartId(), lSubTransactionType, subjectType, param.getOfficeID(), param.getCurrencyID());
						}
					}
						break;
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_42:
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_43:
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_44:
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_45:
						strSubject = new TransCraftbrotherDAO().getSubjectCode(param.getCraBusinessType(), param.getCounterpartId(), lSubTransactionType, subjectType, param.getOfficeID(), param.getCurrencyID());
						break;
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_46:
					{
						if(param.getTransactionTypeID() == SETTConstant.TransactionType.TRANS_DISCOUNT_GRANT && param.getSubTransactionType()== SETTConstant.SubTransactionType.BREAK_NOTIFY){//转贴现卖出买断发放
							if(param.getTransDiscountContractBillList() != null && param.getTransDiscountContractBillList().size() > 0){
								ZTXstrSubject = new String[param.getTransDiscountContractBillList().size()];
								ZTXAmount = new double[param.getTransDiscountContractBillList().size()];
								ZTXClientNO = new String[param.getTransDiscountContractBillList().size()];
								
								//票据贴现发放信息
								TransGrantDiscountInfo transGrantDiscountInfo = null;
								Sett_TransGrantDiscountDAO gdDao = new Sett_TransGrantDiscountDAO();
								long lLoanPayTmpId = -1;
								long lLoanAccountTmpId = -1; 
								SubAccountAssemblerInfo tmpSubAccountAssemblerInfo = null;
								SubAccountLoanInfo tmpSubAccountLoanInfo = null;
								
								Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO();
								
								for(int n =0;n<param.getTransDiscountContractBillList().size();n++){
									lLoanPayTmpId = -1;
									lLoanAccountTmpId = -1; 
									TransDiscountContractBillInfo tdcBillInfo = (TransDiscountContractBillInfo)param.getTransDiscountContractBillList().get(n);
									if(tdcBillInfo != null && tdcBillInfo.getBillSourceTypeID()==LOANConstant.BillSourceType.DISCOUN){//贴现买入
										transGrantDiscountInfo = gdDao.findDiscountInfoByDiscountBillID(tdcBillInfo.getId());
										lLoanAccountTmpId = (transGrantDiscountInfo == null ? -1 : transGrantDiscountInfo.getDiscountAccountID());
										lLoanPayTmpId = tdcBillInfo.getDiscountCredenceID();
										tmpSubAccountAssemblerInfo = subAccountDAO.findByLoanNoteID1(lLoanAccountTmpId, lLoanPayTmpId);
										if(tmpSubAccountAssemblerInfo != null){
											tmpSubAccountLoanInfo = tmpSubAccountAssemblerInfo.getSubAccountLoanInfo();
										}
										if(tmpSubAccountLoanInfo != null){
											strSubject = accountOperation.getSubjectBySubAccountID(tmpSubAccountLoanInfo.getID(),AccountOperation.SUBJECT_TYPE_PREDRAWINTEREST);
										}
										ZTXClientNO[n] = NameRef.getClientCodeByAccountID(lLoanAccountTmpId);
										
									}else{//转贴现买入
										TransCraftbrotherDAO transDao = new TransCraftbrotherDAO();
										TransCraftbrotherInfo transInfo = transDao.findBreakInByBillId(tdcBillInfo.getId());
										//update by dwj 20120210
										//strSubject = new TransCraftbrotherDAO().getSubjectCode(transInfo.getNcraBusinessTypeId(), transInfo.getNcounterpartId(),transInfo.getNsubTransactionTypeId(), SETTConstant.EntrySubjectType.SUBJECT_TYPE_41, param.getOfficeID(), param.getCurrencyID());
										strSubject = new TransCraftbrotherDAO().getSubjectCode(transInfo.getNcraBusinessTypeId(), transInfo.getNcounterpartId(),transInfo.getNsubTransactionTypeId(), SETTConstant.EntrySubjectType.SUBJECT_TYPE_45, param.getOfficeID(), param.getCurrencyID());
										ZTXClientNO[n] = "";
									}
									
									ZTXstrSubject[n] = strSubject;
									ZTXAmount[n] =  DataFormat.formatDouble(tdcBillInfo.getAccrual(),2);//买入利息
								}
							}
						}else{
							strSubject = new TransCraftbrotherDAO().getSubjectCode(param.getCraBusinessType(), param.getCounterpartId(), lSubTransactionType, subjectType, param.getOfficeID(), param.getCurrencyID());
						}
					}
						break;
					//end add by dwj 移值同业往来(参考方正项目)
					
					//end 
					case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_99 :
						{
							strSubject = tmp.getSubjectCode();
							clientno = "";
						}
						break;
					default :
						return false;
					}
					
					//无法确定合适的会计科目
					if (strSubject == null && subjectType!= SETTConstant.EntrySubjectType.SUBJECT_TYPE_30 )
					{
						log.debug("---------无法获得合适的会计科目-------------");
						//continue;
						throw new IException(true, "无法获得合适的会计科目,交易失败",null);
					}
					log.debug("-----------科目号是" + strSubject + "-----------");
					/////////////////////////////						
					////////////////////////////
					//借贷方向 －－金额方向 2是红字
					if (tmp.getAmountDirection() == SETTConstant.AmountDirection.RED)
						dAmount = (-1) * dAmount;
					//获取借贷方向
					long lDirection = tmp.getDirection();
					GLEntryInfo gLEntryInfo = new GLEntryInfo();
					// set info fields
					//保留
					long Group;
					long Type;
					
					gLEntryInfo.setOfficeID(param.getOfficeID());
					//开始通存通兑交易判断此条分录所属办事处
					if(param.getSubTransactionType()==SETTConstant.SubTransactionType.DIFOFFICE)
					{//如果是通存通兑交易，则需要判断此条分录所属办事处
						if(subjectType == SETTConstant.EntrySubjectType.SUBJECT_TYPE_1
								||subjectType == SETTConstant.EntrySubjectType.SUBJECT_TYPE_36)
						{//如果是收方
							gLEntryInfo.setOfficeID(param.getReceiveofficeID());
						}
						else if(subjectType == SETTConstant.EntrySubjectType.SUBJECT_TYPE_2
								||subjectType == SETTConstant.EntrySubjectType.SUBJECT_TYPE_37)
						{//如果是付方
							gLEntryInfo.setOfficeID(param.getPayofficeID());
						}
						else if(subjectType == SETTConstant.EntrySubjectType.SUBJECT_TYPE_34
								||subjectType == SETTConstant.EntrySubjectType.SUBJECT_TYPE_35)
						{//如果是备付金账户，则都属于总部的分录
							gLEntryInfo.setOfficeID(param.getParentofficeID());
						}
					}
					gLEntryInfo.setCurrencyID(param.getCurrencyID());
					gLEntryInfo.setSubjectCode(strSubject);
					gLEntryInfo.setTransNo(param.getTransNo());
					gLEntryInfo.setTransactionTypeID(param.getTransactionTypeID());
					gLEntryInfo.setTransDirection(tmp.getDirection());
					gLEntryInfo.setAmount(dAmount);
					gLEntryInfo.setExecute(param.getExecuteDate());
					gLEntryInfo.setInterestStart(param.getInterestStartDate());
					gLEntryInfo.setAbstract(param.getAbstractStr());
					gLEntryInfo.setMultiCode(sMultiCode);
					gLEntryInfo.setInputUserID(param.getInputUserID());
					gLEntryInfo.setCheckUserID(param.getCheckUserID());
					//设为已复核
					gLEntryInfo.setStatusID(3);
					sett_GLEntryDAO gLEntryDAO = new sett_GLEntryDAO(conn);
					log.debug("---------产生会计分录到会计分录表------------");
					//add by dwj 20120203 移值同业往来(参考方正项目)
					if(tmp.getSubjectType()==SETTConstant.EntrySubjectType.SUBJECT_TYPE_11)
					{
						if(param.getTransactionTypeID() == SETTConstant.TransactionType.SAME_BUSINESS_GRANT && param.getSubTransactionType()== SETTConstant.SubTransactionType.BREAK_NOTIFY){//资产转让卖出买断发放
							if(param.getAttornmentContractList() != null && param.getAttornmentContractList().size() > 0){
								if(ZYZRstrSubject != null && ZYZRInterest != null){
									for(int n=0;n<param.getAttornmentContractList().size();n++)
									{ 
										try
										{
											gLEntryInfo.setSubjectCode(ZYZRstrSubject[n]);
											gLEntryInfo.setAmount(ZYZRInterest[n]);
											long lGLId = gLEntryDAO.add(gLEntryInfo);
											if(Config.getBoolean(ConfigConstant.SETTLEMENT_NEEDCLIENTASSISTANT, false))
											{
												sett_GLEntryDAO gldao = new sett_GLEntryDAO(conn);
												AssistantInfo assistantInfo = new AssistantInfo();
												assistantInfo.setAssitantName("客商辅助核算");
												assistantInfo.setAssitantValue(ZYZRClientNO[n]);
												assistantInfo.setGlentryID(lGLId);
												assistantInfo.setModifyUserID(param.getInputUserID());
												assistantInfo.setStatusId(1);
												gldao.addAssitant(assistantInfo);
											}
										}
										catch(SQLException e)
										{
											e.printStackTrace();throw new IException(true, e.getMessage(), e);
										}
									}
								}
							}
						}
						else{
							try
							{								
								long returnid = gLEntryDAO.add(gLEntryInfo);
								
								if(Config.getBoolean(ConfigConstant.SETTLEMENT_NEEDCLIENTASSISTANT, false))
								{
									sett_GLEntryDAO gldao = new sett_GLEntryDAO(conn);
									AssistantInfo assistantInfo = new AssistantInfo();
									assistantInfo.setAssitantName("客商辅助核算");
									assistantInfo.setAssitantValue(clientno);
									assistantInfo.setGlentryID(returnid);
									assistantInfo.setModifyUserID(param.getInputUserID());
									assistantInfo.setStatusId(1);
									gldao.addAssitant(assistantInfo);
								}
								
							}
							catch (SQLException e)
							{
								e.printStackTrace();throw new IException(true, e.getMessage(), e);
							}
						}
					}
					else if(tmp.getSubjectType()==SETTConstant.EntrySubjectType.SUBJECT_TYPE_15)
					{
						if(param.getTransactionTypeID() == SETTConstant.TransactionType.SAME_BUSINESS_GRANT && param.getSubTransactionType()== SETTConstant.SubTransactionType.BREAK_NOTIFY){//资产转让卖出买断发放
							if(param.getAttornmentContractList() != null && param.getAttornmentContractList().size() > 0){
								if(ZYZRstrSubject != null && ZYZRPerDrawInterest != null){
									for(int n=0;n<param.getAttornmentContractList().size();n++)
									{ 
										try
										{
											gLEntryInfo.setSubjectCode(ZYZRstrSubject[n]);
											gLEntryInfo.setAmount(ZYZRPerDrawInterest[n]);
											long lGLId = gLEntryDAO.add(gLEntryInfo);
											if(Config.getBoolean(ConfigConstant.SETTLEMENT_NEEDCLIENTASSISTANT, false))
											{
												sett_GLEntryDAO gldao = new sett_GLEntryDAO(conn);
												AssistantInfo assistantInfo = new AssistantInfo();
												assistantInfo.setAssitantName("客商辅助核算");
												assistantInfo.setAssitantValue(ZYZRClientNO[n]);
												assistantInfo.setGlentryID(lGLId);
												assistantInfo.setModifyUserID(param.getInputUserID());
												assistantInfo.setStatusId(1);
												gldao.addAssitant(assistantInfo);
											}
										}
										catch(SQLException e)
										{
											e.printStackTrace();throw new IException(true, e.getMessage(), e);
										}
									}
								}
							}
						}
						else{
							try
							{								
								long returnid = gLEntryDAO.add(gLEntryInfo);
								
								if(Config.getBoolean(ConfigConstant.SETTLEMENT_NEEDCLIENTASSISTANT, false))
								{
									sett_GLEntryDAO gldao = new sett_GLEntryDAO(conn);
									AssistantInfo assistantInfo = new AssistantInfo();
									assistantInfo.setAssitantName("客商辅助核算");
									assistantInfo.setAssitantValue(clientno);
									assistantInfo.setGlentryID(returnid);
									assistantInfo.setModifyUserID(param.getInputUserID());
									assistantInfo.setStatusId(1);
									gldao.addAssitant(assistantInfo);
								}
								
							}
							catch (SQLException e)
							{
								e.printStackTrace();throw new IException(true, e.getMessage(), e);
							}
						}
					}
					//end add by dwj 20120203 移值同业往来(参考方正项目)
					else if( tmp.getSubjectType()==SETTConstant.EntrySubjectType.SUBJECT_TYPE_26)
					{
						if(param.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANRECEIVE
								|| param.getTransactionTypeID()==SETTConstant.TransactionType.YT_LOAN_INTEREST 
								|| param.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANGRANT
								|| param.getTransactionTypeID()==SETTConstant.TransactionType.YT_LOAN_PREDRAW_INTEREST)
						{
							if(param.getList()!=null && param.getList().size()>0)
							{
								if(YTstrSubject!=null && YTAmount!=null)
								{
									for(int n=0;n<param.getList().size();n++)
									{
										try
										{
											gLEntryInfo.setSubjectCode(YTstrSubject[n]);
											gLEntryInfo.setAmount(YTAmount[n]);
											gLEntryDAO.add(gLEntryInfo);
										}
										catch (SQLException e)
										{
											e.printStackTrace();throw new IException(true, e.getMessage(), e);
										}
									}
								}
								else
								{
									throw new IException(true, "银团贷款银团详细信息有误，分录产生失败",null);
								}
							}
						}
						/* 银团贷款收手续费，原逻辑，注释 modify zcwang 2008-10-07
						else if(param.getTransactionTypeID()==SETTConstant.TransactionType.YT_LOAN_COMMISION_FEE)
						{
							try
							{
								gLEntryDAO.add(gLEntryInfo);
							}
							catch (SQLException e)
							{
								e.printStackTrace();throw new IException(true, e.getMessage(), e);
							}
						}
						 end */ 
					}
					else if( tmp.getSubjectType()==SETTConstant.EntrySubjectType.SUBJECT_TYPE_30)
					{
						//add by dwj 20120206
						if(param.getTransactionTypeID()==SETTConstant.TransactionType.SAME_BUSINESS_GRANT||param.getTransactionTypeID()==SETTConstant.TransactionType.SAME_BUSINESS_RECEIVE)
						{
							if(param.getAttornmentContractList() != null && param.getAttornmentContractList().size() > 0)
							{
								if(ZYZRstrSubject != null && ZYZRAmount != null){
									for(int n=0;n<param.getAttornmentContractList().size();n++)
									{ 
										try
										{
											gLEntryInfo.setSubjectCode(ZYZRstrSubject[n]);
											gLEntryInfo.setAmount(ZYZRAmount[n]);
											long lGLId = gLEntryDAO.add(gLEntryInfo);
											if(Config.getBoolean(ConfigConstant.SETTLEMENT_NEEDCLIENTASSISTANT, false))
											{
												sett_GLEntryDAO gldao = new sett_GLEntryDAO(conn);
												AssistantInfo assistantInfo = new AssistantInfo();
												assistantInfo.setAssitantName("客商辅助核算");
												assistantInfo.setAssitantValue(ZYZRClientNO[n]);
												assistantInfo.setGlentryID(lGLId);
												assistantInfo.setModifyUserID(param.getInputUserID());
												assistantInfo.setStatusId(1);
												gldao.addAssitant(assistantInfo);
											}
										}
										catch(SQLException e)
										{
											e.printStackTrace();throw new IException(true, e.getMessage(), e);
										}
									}
								}
							}
						}
						else //end add by dwj 20120206
						{
							if(param.getList()!=null && param.getList().size()>0)
							{
								TransferLoanAmountDetailinfo detailinfo=null;							
								for(int n=0;n<param.getList().size();n++)
								{
									detailinfo=(TransferLoanAmountDetailinfo)param.getList().get(n);
									try
									{
										gLEntryInfo.setAmount(detailinfo.getAmount());
									  //strSubject = accountOperation.getSubjectByOther(detailinfo.getContractid(),detailinfo.getNoticeformid(),detailinfo.getBorrowClientID(),AccountOperation.SUBJECT_TYPE_ZYAMOUNT);								
										gLEntryInfo.setSubjectCode(ZYstrSubject[n]);
										if(ZYstrSubject[n] == null)
										{
											log.debug("---------无法获得合适的会计科目-------------");
											throw new IException(true, "无法获得合适的会计科目,交易失败",null);
										}
										gLEntryDAO.add(gLEntryInfo);
									}
									catch (SQLException e)
									{
										e.printStackTrace();throw new IException(true, e.getMessage(), e);
									}
								}
								
							}
						}
					}
					//add by dwj 20120206
					else if(tmp.getSubjectType()==SETTConstant.EntrySubjectType.SUBJECT_TYPE_40)
					{
						//贴现贷款/转贴现本金，明细到每张票
						if(param.getTransactionTypeID() == SETTConstant.TransactionType.TRANS_DISCOUNT_GRANT && param.getSubTransactionType()== SETTConstant.SubTransactionType.BREAK_NOTIFY){//转贴现卖出买断发放
							if(param.getTransDiscountContractBillList() != null && param.getTransDiscountContractBillList().size() > 0){
								if(ZTXstrSubject != null && ZTXAmount != null){
									for(int n=0;n<param.getTransDiscountContractBillList().size();n++)
									{ 
										try
										{
											gLEntryInfo.setSubjectCode(ZTXstrSubject[n]);
											gLEntryInfo.setAmount(ZTXAmount[n]);
											long lGLId = gLEntryDAO.add(gLEntryInfo);
											
											if(Config.getBoolean(ConfigConstant.SETTLEMENT_NEEDCLIENTASSISTANT, false))
											{
												sett_GLEntryDAO gldao = new sett_GLEntryDAO(conn);
												AssistantInfo assistantInfo = new AssistantInfo();
												assistantInfo.setAssitantName("客商辅助核算");
												assistantInfo.setAssitantValue(ZTXClientNO[n]);
												assistantInfo.setGlentryID(lGLId);
												assistantInfo.setModifyUserID(param.getInputUserID());
												assistantInfo.setStatusId(1);
												gldao.addAssitant(assistantInfo);
											}
										}
										catch(SQLException e)
										{
											e.printStackTrace();throw new IException(true, e.getMessage(), e);
										}
									}
								}
							}
						}else{
							try
							{								
								long returnid = gLEntryDAO.add(gLEntryInfo);
								
								if(Config.getBoolean(ConfigConstant.SETTLEMENT_NEEDCLIENTASSISTANT, false))
								{
									sett_GLEntryDAO gldao = new sett_GLEntryDAO(conn);
									AssistantInfo assistantInfo = new AssistantInfo();
									assistantInfo.setAssitantName("客商辅助核算");
									assistantInfo.setAssitantValue(clientno);
									assistantInfo.setGlentryID(returnid);
									assistantInfo.setModifyUserID(param.getInputUserID());
									assistantInfo.setStatusId(1);
									gldao.addAssitant(assistantInfo);
								}
								
							}
							catch (SQLException e)
							{
								e.printStackTrace();throw new IException(true, e.getMessage(), e);
							}
						}
					
					}
					else if(tmp.getSubjectType()==SETTConstant.EntrySubjectType.SUBJECT_TYPE_41)
					{

						if(param.getTransactionTypeID() == SETTConstant.TransactionType.TRANS_DISCOUNT_GRANT && param.getSubTransactionType()== SETTConstant.SubTransactionType.BREAK_NOTIFY){//转贴现卖出买断发放,实收利息/税后利息/协定利息,明细到每张票
							if(param.getTransDiscountContractBillList() != null && param.getTransDiscountContractBillList().size() > 0){
								if(ZTXstrSubject != null && ZTXAmount != null){
									for(int n=0;n<param.getTransDiscountContractBillList().size();n++)
									{ 
										try
										{
											gLEntryInfo.setSubjectCode(ZTXstrSubject[n]);
											gLEntryInfo.setAmount(ZTXAmount[n]);
											long lGLId = gLEntryDAO.add(gLEntryInfo);
											
											if(Config.getBoolean(ConfigConstant.SETTLEMENT_NEEDCLIENTASSISTANT, false))
											{
												sett_GLEntryDAO gldao = new sett_GLEntryDAO(conn);
												AssistantInfo assistantInfo = new AssistantInfo();
												assistantInfo.setAssitantName("客商辅助核算");
												assistantInfo.setAssitantValue(ZTXClientNO[n]);
												assistantInfo.setGlentryID(lGLId);
												assistantInfo.setModifyUserID(param.getInputUserID());
												assistantInfo.setStatusId(1);
												gldao.addAssitant(assistantInfo);
											}
										}
										catch(SQLException e)
										{
											e.printStackTrace();throw new IException(true, e.getMessage(), e);
										}
									}
								}
							}
						}else{
							try
							{								
								long returnid = gLEntryDAO.add(gLEntryInfo);
								
								if(Config.getBoolean(ConfigConstant.SETTLEMENT_NEEDCLIENTASSISTANT, false))
								{
									sett_GLEntryDAO gldao = new sett_GLEntryDAO(conn);
									AssistantInfo assistantInfo = new AssistantInfo();
									assistantInfo.setAssitantName("客商辅助核算");
									assistantInfo.setAssitantValue(clientno);
									assistantInfo.setGlentryID(returnid);
									assistantInfo.setModifyUserID(param.getInputUserID());
									assistantInfo.setStatusId(1);
									gldao.addAssitant(assistantInfo);
								}
								
							}
							catch (SQLException e)
							{
								e.printStackTrace();throw new IException(true, e.getMessage(), e);
							}
						}
					
					}
					else if(tmp.getSubjectType()==SETTConstant.EntrySubjectType.SUBJECT_TYPE_46)
					{
						if(param.getTransactionTypeID() == SETTConstant.TransactionType.TRANS_DISCOUNT_GRANT && param.getSubTransactionType()== SETTConstant.SubTransactionType.BREAK_NOTIFY){//转贴现卖出买断发放,买入利息，明细到每张票
							if(param.getTransDiscountContractBillList() != null && param.getTransDiscountContractBillList().size() > 0){
								if(ZTXstrSubject != null && ZTXAmount != null){
									for(int n=0;n<param.getTransDiscountContractBillList().size();n++)
									{ 
										try
										{
											gLEntryInfo.setSubjectCode(ZTXstrSubject[n]);
											gLEntryInfo.setAmount(ZTXAmount[n]);
											long lGLId = gLEntryDAO.add(gLEntryInfo);
											
											if(Config.getBoolean(ConfigConstant.SETTLEMENT_NEEDCLIENTASSISTANT, false))
											{
												sett_GLEntryDAO gldao = new sett_GLEntryDAO(conn);
												AssistantInfo assistantInfo = new AssistantInfo();
												assistantInfo.setAssitantName("客商辅助核算");
												assistantInfo.setAssitantValue(ZTXClientNO[n]);
												assistantInfo.setGlentryID(lGLId);
												assistantInfo.setModifyUserID(param.getInputUserID());
												assistantInfo.setStatusId(1);
												gldao.addAssitant(assistantInfo);
											}
										}
										catch(SQLException e)
										{
											e.printStackTrace();throw new IException(true, e.getMessage(), e);
										}
									}
								}
							}
						}else{
							try
							{								
								long returnid = gLEntryDAO.add(gLEntryInfo);
								
								if(Config.getBoolean(ConfigConstant.SETTLEMENT_NEEDCLIENTASSISTANT, false))
								{
									sett_GLEntryDAO gldao = new sett_GLEntryDAO(conn);
									AssistantInfo assistantInfo = new AssistantInfo();
									assistantInfo.setAssitantName("客商辅助核算");
									assistantInfo.setAssitantValue(clientno);
									assistantInfo.setGlentryID(returnid);
									assistantInfo.setModifyUserID(param.getInputUserID());
									assistantInfo.setStatusId(1);
									gldao.addAssitant(assistantInfo);
								}
								
							}
							catch (SQLException e)
							{
								e.printStackTrace();throw new IException(true, e.getMessage(), e);
							}
						}
					}
					//end add by dwj 20120206
					else
					{
						try
						{
							//gLEntryDAO.add(gLEntryInfo);
							
							long returnid = gLEntryDAO.add(gLEntryInfo);
							
							//sett_GLEntryDAO gLEntrydao = new sett_GLEntryDAO(conn);
							//long lvalue = -1;
							//lvalue = gLEntrydao.checkSubject(strSubject, accountType);
							
							//增加辅助核算信息
							//if(lvalue > 0)
							//{
							//增加配置文件是否生成辅助核算项
							System.out.println("----是否增加辅助核算-----"+Config.getBoolean(ConfigConstant.SETTLEMENT_NEEDCLIENTASSISTANT, false));
							System.out.println("----客户编号-----"+clientno);
							if(Config.getBoolean(ConfigConstant.SETTLEMENT_NEEDCLIENTASSISTANT, false))
							{
								sett_GLEntryDAO gldao = new sett_GLEntryDAO(conn);
								AssistantInfo assistantInfo = new AssistantInfo();
								assistantInfo.setAssitantName("客商辅助核算");
								assistantInfo.setAssitantValue(clientno);
								assistantInfo.setGlentryID(returnid);
								assistantInfo.setModifyUserID(param.getInputUserID());
								assistantInfo.setStatusId(1);
								gldao.addAssitant(assistantInfo);
							}
							//}
							
						}
						catch (SQLException e)
						{
							e.printStackTrace();throw new IException(true, e.getMessage(), e);
						}
					}
					log.debug("第" + i + "笔会计分录产生完成");
				} //for end
				if(param.isTrialBalance()){
									//检查本交易号产生的分录是否借贷平衡
					log.debug("-----检查本交易号产生的分录是否借贷平衡-----");
					boolean checkRes = checkTransDCBalance(param.getTransNo());
					if (!checkRes)
					{
						log.debug("-----借贷平衡不平衡，分录产生失败-------");
						throw new IException(true, "借贷不平衡，分录产生失败",null);
					}
				}
			}
			else
			{
				throw new IException(true, "无法找到对应的会计分录定义，交易失败",null);
			}			
/*
			log.print("----------------是否生成银行指令(会计分录):"+ param.isAutoCreateBankInstruction() + "-----------------");
			//是否有银企接口
			boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
			
			if (param.isAutoCreateBankInstruction() && bIsValid)
			{

				//判断是否有形成指令的配置银行
				ArrayList list = new ArrayList(8);
				list = Config.getArray(Config.INTEGRATION_SERVICE_TOSENDBANKINSTRUCTION_BANKTYPE, new ArrayList(8));
				
				if(bIsValid && list.size() > 0) {
					Log.print("*******************开始产生银行指令**************************");
					//生成指令
					try {
						log.info("交易类型ID ============== "+param.getTransactionTypeID());
						//构造参数
						CreateInstructionParam instructionParam = new CreateInstructionParam();
						instructionParam.setTransactionTypeID(param.getTransactionTypeID());
						instructionParam.setTransNo(param.getTransNo());
						instructionParam.setOfficeID(param.getOfficeID());
						instructionParam.setCurrencyID(param.getCurrencyID());
						instructionParam.setCheckUserID(param.getCheckUserID());
						instructionParam.setInputUserID(param.getInputUserID());
						
						//生成银行指令并保存
						IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
						bankInstruction.createBankInstructionFromTransDetail(instructionParam);
				
					} catch (SettlementException e) {
						log.error("生成银行指令失败");
						e.printStackTrace();
						if (!e.getErrorCode().equals("Sett_E302"))
							e.printStackTrace();throw new IException(e.getErrorCode(), e);
					}
					Log.print("*******************产生银行指令结束**************************");
				}
			}
*/
			return true;
		}
		catch (Exception ex)
		{
			throw new IException(true, ex.getMessage(),null);
		}
	}
	
	/**
	 * 按交易检查借贷平衡
	 * @return 成功返回true, 失败返回false
	 * @throws IRollbackException
	 */
	public boolean checkTransDCBalance(String transNo) throws IException
	{
		try
		{
			//		1.	调用sett_GLEntryDAO.searchByTransNo(strTransNo)，在会计分录表sett_GLEntry中，选取所有sett_GLEntry.sTransNo = strTransNo 且sett_GLEntry.nStatusID = 3的记录。
			sett_GLEntryDAO gLEntryDAO = new sett_GLEntryDAO(conn);
			long status = SETTConstant.TransactionStatus.CHECK;
			Collection c;
			try
			{
				c = gLEntryDAO.findByTransNoAndStatusID(transNo, status);
			}
			catch (SQLException e)
			{
				e.printStackTrace();throw new IException(true, e.getMessage(), e);
			}
			if (c == null || c.size() == 0)
				return true;
			Iterator i = c.iterator();
			double lendAmount = 0.0;
			double loanAmount = 0.0;
			while (i.hasNext())
			{
				GLEntryInfo gLEntryInfo = (GLEntryInfo) i.next();
				long transDirection = gLEntryInfo.getTransDirection();
				if ((int) transDirection == 1)					//			2.	累计借方金额 = Sum(sett_GLEntry.mAmount) where  sett_GLEntry.nTransDirection = 1；
					lendAmount += gLEntryInfo.getAmount();
				else if ((int) transDirection == 2)					//			累计贷方金额 = Sum(sett_GLEntry.mAmount) where  sett_GLEntry.nTransDirection = 2；  
					loanAmount += gLEntryInfo.getAmount();
			}
			//		3.	如果累计借方金额<>累计贷方金额，则返回0，否则返回1。
			log.debug("----借方累计金额" + lendAmount + "---------");
			log.debug("----贷方累计金额" + loanAmount + "---------");
			//if (lendAmount != loanAmount)
			if(!UtilOperation.Arith.equal(loanAmount, lendAmount, 4))
				return false;
			else
				return true;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new IException(true, ex.getMessage(),null);
		}
	}	
	/**
	 * 增加新的会计分录
	 * @param stransNo 交易号
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public void deleteGLEntry(String stransNo) throws IException
	{
		sett_GLEntryDAO dao = new sett_GLEntryDAO(conn);
		Collection batchNoColl = null;
		Collection batchNoCollT = null;
		try
		{
			batchNoColl = dao.findByTransNoAndStatusID(stransNo,SETTConstant.EntryStatus.MERGED);
			if(batchNoColl!=null&&batchNoColl.size()>0){
				throw new IException(true, "该凭证已经合并,不能取消复核",null);
			}
			
			//增加辅助核算的删除操作
			batchNoCollT = dao.findByTransNoAndStatusID(stransNo,SETTConstant.EntryStatus.CHECKED);
			AssistantDao assistantDao = new AssistantDao(conn);
			assistantDao.cancelCheckDelete(batchNoCollT);
			
			dao.deleteByTransNo(stransNo);
			
			//是否有银企接口
			boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
			if (bIsValid)
			{
				IBankInstruction bankInstruction = BankInstructionFactory.getInstance();			
				bankInstruction.cancelCheck(stransNo);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new IException(true, ex.getMessage(),ex);
		}
	}

	public boolean isExistSubeject(String subjectCode) throws IException{
		Sett_GLSubjectDefinitionDAO dao = new Sett_GLSubjectDefinitionDAO(conn);
		try
		{
			return dao.isExistSubeject(subjectCode);
		}
		catch (SQLException e)
		{
			e.printStackTrace();throw new IException(true, e.getMessage(),e);
		}		
	}

	/**
	 * 	单条分录过总账
	 *  将 一条分录入到总账中，一般是关机后，系统将调用本方法，逐条将所有分录过到总账中。
 	*/
	private void postGLEntry(long officeID, long currencyID, String subjectCode, long transDirection,double amount,Timestamp execDate) throws IException{
		GLBalanceInfo glBalanceInfo = new GLBalanceInfo();
		glBalanceInfo.setOfficeID(officeID);
		glBalanceInfo.setCurrencyID(currencyID);
		glBalanceInfo.setGLSubjectCode(subjectCode);
		glBalanceInfo.setGLDate(execDate);
		Collection c = null;

		log.debug("开始单条分录过总账");
		
		try {
			sett_GLBalanceDAO glBalanceDAO = new sett_GLBalanceDAO(conn);
			Sett_GLSubjectDefinitionDAO glSubjectDefDAO = new Sett_GLSubjectDefinitionDAO(conn);
			
			c = glBalanceDAO.findByCondition(glBalanceInfo);
			
			if(c.size() == 0){
				log.debug("没有查询到总账余额信息，产生新记录");
				GLSubjectDefinitionInfo glSubjectDefInfo = glSubjectDefDAO.findByOldCode(subjectCode);
				glSubjectDefInfo.setBalanceDirection(SETTConstant.SubjectAttribute.getDirection(glSubjectDefInfo.getSubjectType()));
				if(glSubjectDefInfo != null && glSubjectDefInfo.getID() > 0){				
					GLBalanceInfo tmp = new GLBalanceInfo(); 
					if(glSubjectDefInfo.getBalanceDirection() == 9)
						tmp.setBalanceDirection(1);
					else
						tmp.setBalanceDirection(glSubjectDefInfo.getBalanceDirection());
						
					if(transDirection == SETTConstant.DebitOrCredit.DEBIT){
						tmp.setDebitAmount(amount);
						tmp.setCreditAmount(0.0);
						tmp.setDebitNumber(1);
						tmp.setCreditNumber(0);
						if(tmp.getBalanceDirection() == SETTConstant.DebitOrCredit.DEBIT){
							tmp.setDebitBalance(amount);
						}else if(tmp.getBalanceDirection() == SETTConstant.DebitOrCredit.CREDIT){
							tmp.setCreditBalance((-1)*amount);
						}else{
							throw new IException(true,"过账发生错误，过账失败",null);
						}
					}else{
						tmp.setCreditAmount(amount);
						tmp.setDebitNumber(0);
						tmp.setCreditNumber(1);
						if(tmp.getBalanceDirection() == SETTConstant.DebitOrCredit.DEBIT){
								tmp.setDebitBalance((-1)*amount);
							}else{
								tmp.setCreditBalance(amount);					
							}	
				    }
				
					tmp.setOfficeID(officeID);
					tmp.setCurrencyID(currencyID);
					tmp.setGLDate(execDate);
					tmp.setGLSubjectCode(subjectCode);
					glBalanceDAO.add(tmp);
				}else{
					throw new IException(true,"无法找到科目号: "+subjectCode + " 对应的分录设置, 过账失败",null);
				}
				
			
			
			}else{		
			    log.debug("查询到总账余额信息，更新记录");
				Iterator it = c.iterator();			    
				while(it.hasNext()){
					GLBalanceInfo tmp = (GLBalanceInfo) it.next();
					if(transDirection == SETTConstant.DebitOrCredit.DEBIT){
						tmp.setDebitAmount(tmp.getDebitAmount() + amount);
						tmp.setDebitNumber(tmp.getDebitNumber() + 1);
						if(tmp.getBalanceDirection() == SETTConstant.DebitOrCredit.DEBIT){
							tmp.setDebitBalance(tmp.getDebitBalance() + amount);
						}else{
							tmp.setCreditBalance(tmp.getCreditBalance() - amount);
						}
					}else{
						tmp.setCreditAmount(tmp.getCreditAmount() + amount);
						tmp.setCreditNumber(tmp.getCreditNumber() + 1);
						if(tmp.getBalanceDirection() == SETTConstant.DebitOrCredit.DEBIT){
							tmp.setDebitBalance(tmp.getDebitBalance() - amount);
						}else{
							tmp.setCreditBalance(tmp.getCreditBalance() + amount);
						}
					}
					log.debug("更新信息为:");
					log.debug(UtilOperation.dataentityToString(tmp));
					glBalanceDAO.update(tmp);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();throw new IException(true,e.getMessage(),e);
		}
		log.debug("结束单条分录过总账");		
		
	}
	
	/**
	 * 建立每日初始总账
 	*/
	public void createSODGLBalance(long officeID, long currencyID, Timestamp today)  throws IException{
		try {
			log.debug("开始建立每日初始总账");			
			sett_GLBalanceDAO glBalanceDAO = new sett_GLBalanceDAO(conn);			
			GLBalanceInfo glBalanceInfo = new GLBalanceInfo();
			//Timestamp today = Env.getSystemDate(officeID, currencyID);
			Timestamp yestaday = UtilOperation.getNextNDay(today, -1);
			glBalanceInfo.setGLDate(yestaday);
			//modify by fulihe 为glBalanceInfo增加办事处和币种，用以查询时区分办事处和币种
			glBalanceInfo.setCurrencyID(currencyID);
			glBalanceInfo.setOfficeID(officeID);
			Collection c = glBalanceDAO.findByCondition(glBalanceInfo);
			if(c != null && !c.isEmpty()){
				int i = 0;
				Iterator it = c.iterator();
				while(it.hasNext()){
					GLBalanceInfo tmp = (GLBalanceInfo) it.next();
					i++;
					tmp.setGLDate(today);
					tmp.setDebitAmount(0.0);
					tmp.setCreditAmount(0.0);
					tmp.setDebitNumber(0);
					tmp.setCreditNumber(0);
					log.debug("开始增加第"+ i + "笔:");
					log.debug(UtilOperation.dataentityToString(tmp));
					glBalanceDAO.add(tmp);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();throw new IException(true,e.getMessage(),e);
		}
		log.debug("结束建立每日初始总账");		
		//glBalanceInfo.setGLDate();		
	}

	/**	日终分录过总账*/
	public void postEODGLEntries(long officeID, long currencyID)  throws IException{
		sett_GLEntryDAO glEntryDAO = new sett_GLEntryDAO(conn);
	
		Timestamp today = Env.getSystemDate(officeID, currencyID);
		log.debug("开始日终分录过总账,　今天是:"+ today.toString());		
		Collection c = null;

		try {
			c = glEntryDAO.findByExecuteDate(today, officeID, currencyID);
			log.debug("本日有"+ c.size() + "条分录需要过账");
		} catch (Exception e) {
			e.printStackTrace();throw new IException(true,e.getMessage(),e);
			
		}
		int i = 0;
		if(c != null && !c.isEmpty()){
			Iterator it = c.iterator();
			while(it.hasNext()){
				GLEntryInfo tmp = (GLEntryInfo) it.next();
				i++; 
				log.debug("正在处理第"+ i + "条分录");
				postGLEntry(officeID, currencyID, tmp.getSubjectCode(), tmp.getTransDirection(),tmp.getAmount(),today);
			}
		}
		log.debug("结束日终分录过总账");		
	}
				
}
