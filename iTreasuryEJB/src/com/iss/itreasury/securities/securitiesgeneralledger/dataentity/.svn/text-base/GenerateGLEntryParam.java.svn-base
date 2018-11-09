/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-9
 */
package com.iss.itreasury.securities.securitiesgeneralledger.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.securities.deliveryorder.dataentity.DeliveryOrderInfo;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.setting.dataentity.TransactionTypeInfo;
import com.iss.itreasury.securities.util.SECBaseDataEntity;
import com.iss.itreasury.securities.util.NameRef;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.util.Env;

/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class GenerateGLEntryParam extends SECBaseDataEntity {
	private long officeID = -1;                          //办事处编号ID，指做交易的办事处编号
	private long currencyID = -1;                        //交易币种  
	private long transactionType = -1;					 //交易类型	
	private long subTransactionType = -1;				 //交易子类	
	private long accountType = -1;						 //账务类型	
	private Timestamp executeDate = null;				 //执行日	
	private Timestamp transactionDate = null;			 //成交日	
	private Timestamp deliveryDate = null;   			 //交割日
	private String deliverOrderCode = null;					 //交割单Code	
	private String abstractStr = null;                   //摘要
	private long inputUserID = -1;						 //录入人	
	private long checkUserID = -1;                       //复核人
	private long cashflow = -1;                          //现金流向,可空
	private long entryType = -1;						 //分录类型,可空
	private long termTypeID = -1;						 //期限类型，可空
	private long term = -1;                              //期限
	private String seatCode = null;						 //证券业务席位号，可空 	
	private long payCounterpartID = -1;                  //付方开户营业部/交易对手，可空  
	private long payClientID = -1;                       //付方业务单位，可空
	private long payCapitalAccountID = -1;               //付方资金帐户ID，可空
	private long payBankID = -1;               			//付方银行ID，可空
	private long paySecuritiesID = -1;                   //付方证券ID，可空 
	private long receiveCounterpartID = -1;              //收方开户营业部/交易对手，可空
	private long receiveClientID = -1;                   //收方业务单位，可空 
	private long receiveCapitalAccountID = -1;           //收方资金帐户ID，可空
	private long receiveBankID = -1;           			//收方银行帐户ID，可空	
	private long receiveSecuritiesID = -1;               //收方证券ID，可空
	private double netIncome = 0.0;                      //实际收付
	private double principal = 0.0;                      //本金/成本/面值
	private double netPrincipal = 0.0;                      //本金/成本/面值(净价)	
	private double interest = 0.0;                       //利息/收益/支出
	private double margin = 0.0;                         //证券销售/差价收入/损失
	private double suspenseInterest = 0.0;               //应计利息/损益调整
	private double unrealizedGain = 0.0;                 //未实现利得 
	private double commission = 0.0;                     //手续费/佣金 
	private double discount = 0.0;                       //折价
	private double premium = 0.0;						 //溢价	
	private double stampDuty = 0.0;                      //印花税
	private double businessTax = 0.0;                    //营业税金及附加
	private double overdueFine = 0.0;                    //罚息 
	private double stockJobberNetIncome = 0.0;           //券商实收付
	private double stockExchangeNetIncome = 0.0;		 //证交所实收付
	private double ventureCapital = 0.0;                 //结算风险基金
	
	public GenerateGLEntryParam(){
	}
	
	public GenerateGLEntryParam(DeliveryOrderInfo doInfo) throws SecuritiesException{
		TransactionTypeInfo transTypeInfo = doInfo.getTransactionTypeInfo();
		officeID = doInfo.getOfficeId();
		currencyID = doInfo.getCurrencyId();

		receiveCounterpartID = doInfo.getCounterpartId();
		payCounterpartID = doInfo.getCounterpartId();		
		termTypeID = doInfo.getTermTypeId();
		abstractStr = doInfo.getRemark();
		//if(transTypeInfo.getCapitalDirection() == SECConstant.Direction.PAY){
			//payCapitalAccountID = NameRef.getAccountIDFromDeliveryOrder(doInfo, false);
			//payCapitalAccountID = doInfo.getAccountId();
		//}else if(transTypeInfo.getCapitalDirection() == SECConstant.Direction.RECEIVE){
			//receiveCapitalAccountID = NameRef.getAccountIDFromDeliveryOrder(doInfo, false);
			//receiveCapitalAccountID = doInfo.getAccountId();
		//}else{
		//}
		payBankID = doInfo.getCompanyBankId();
		receiveBankID = doInfo.getCompanyBankId();
		
		if(transTypeInfo.getStockDirection() == 3){//一付一收
			payClientID = doInfo.getCompanyAccountId();
			receiveClientID = doInfo.getClientId();
			payCapitalAccountID = doInfo.getCounterpartAccountId();
			receiveCapitalAccountID = doInfo.getAccountId();		
			paySecuritiesID = doInfo.getOppositeSecuritiesId();
			receiveSecuritiesID = doInfo.getSecuritiesId();			
			receiveCounterpartID = new Long(NameRef.getCounterpartIDByAccountID(receiveCapitalAccountID)).longValue();
			payCounterpartID = new Long(NameRef.getCounterpartIDByAccountID(payCapitalAccountID)).longValue();					
		}else{
			payClientID = doInfo.getClientId();
			receiveClientID = doInfo.getClientId();			
			payCapitalAccountID = doInfo.getAccountId();
			receiveCapitalAccountID = doInfo.getAccountId();				
			paySecuritiesID = doInfo.getSecuritiesId();
			receiveSecuritiesID = doInfo.getSecuritiesId();			
		}


		executeDate = Env.getSecuritiesSystemDate(doInfo.getOfficeId(), doInfo.getCurrencyId());
		inputUserID = doInfo.getInputUserId();
		checkUserID = doInfo.getCheckUserId();
		

		transactionType = doInfo.getTransactionTypeId();
		deliverOrderCode = doInfo.getCode();
		
		netIncome = doInfo.getNetIncome();                      //实际收付
		principal = doInfo.getAmount();                      //本金/成本/面值
		netPrincipal = doInfo.getNetPriceAmount();                      //本金/成本/面值		
		interest = doInfo.getInterest();                       //利息/收益/支出
		//margin = doInfo.getm;                         //证券销售/差价收入/损失
		suspenseInterest = 0.0;               //应计利息/损益调整
		unrealizedGain = 0.0;                 //未实现利得 
		commission = doInfo.getTax();                     //手续费/佣金 
		discount = 0.0;                       //折价
		premium = 0.0;						 //溢价	
		stampDuty = 0.0;                      //印花税
		businessTax = 0.0;                    //营业税金及附加
		overdueFine = 0.0;                    //罚息 
		stockJobberNetIncome = 0.0;           //券商实收付
		stockExchangeNetIncome = 0.0;		 //证交所实收付
		ventureCapital = 0.0;                 //结算风险基金		
	}	

	public long getId(){
		return -1;
	}
	public void setId(long id){
	}
	/**
	 * @return Returns the abstractStr.
	 */
	public String getAbstractStr() {
		return abstractStr;
	}
	/**
	 * @param abstractStr The abstractStr to set.
	 */
	public void setAbstractStr(String abstractStr) {
		putUsedField("abstractStr", abstractStr);
		this.abstractStr = abstractStr;
	}
	/**
	 * @return Returns the businessTax.
	 */
	public double getBusinessTax() {
		return businessTax;
	}
	/**
	 * @param businessTax The businessTax to set.
	 */
	public void setBusinessTax(double businessTax) {
		putUsedField("businessTax", businessTax);
		this.businessTax = businessTax;
	}
	/**
	 * @return Returns the cashflow.
	 */
	public long getCashflow() {
		return cashflow;
	}
	/**
	 * @param cashflow The cashflow to set.
	 */
	public void setCashflow(long cashflow) {
		putUsedField("cashflow", cashflow);
		this.cashflow = cashflow;
	}
	/**
	 * @return Returns the checkUserID.
	 */
	public long getCheckUserID() {
		return checkUserID;
	}
	/**
	 * @param checkUserID The checkUserID to set.
	 */
	public void setCheckUserID(long checkUserID) {
		putUsedField("checkUserID", checkUserID);
		this.checkUserID = checkUserID;
	}
	/**
	 * @return Returns the commission.
	 */
	public double getCommission() {
		return commission;
	}
	/**
	 * @param commission The commission to set.
	 */
	public void setCommission(double commission) {
		putUsedField("commission", commission);
		this.commission = commission;
	}
	/**
	 * @return Returns the currencyID.
	 */
	public long getCurrencyID() {
		return currencyID;
	}
	/**
	 * @param currencyID The currencyID to set.
	 */
	public void setCurrencyID(long currencyID) {
		putUsedField("currencyID", currencyID);
		this.currencyID = currencyID;
	}
	/**
	 * @return Returns the deliverOrderID.
	 */
	public String getDeliverOrderCode() {
		return deliverOrderCode;
	}
	/**
	 * @param deliverOrderID The deliverOrderID to set.
	 */
	public void setDeliverOrderCode(String deliverOrderCode) {
		putUsedField("deliverOrderCode", deliverOrderCode);
		this.deliverOrderCode = deliverOrderCode;
	}
	/**
	 * @return Returns the deliveryDate.
	 */
	public Timestamp getDeliveryDate() {
		return deliveryDate;
	}
	/**
	 * @param deliveryDate The deliveryDate to set.
	 */
	public void setDeliveryDate(Timestamp deliveryDate) {
		putUsedField("deliveryDate", deliveryDate);
		this.deliveryDate = deliveryDate;
	}
	/**
	 * @return Returns the discount.
	 */
	public double getDiscount() {
		return discount;
	}
	/**
	 * @param discount The discount to set.
	 */
	public void setDiscount(double discount) {
		putUsedField("discount", discount);
		this.discount = discount;
	}
	/**
	 * @return Returns the entryType.
	 */
	public long getEntryType() {
		return entryType;
	}
	/**
	 * @param entryType The entryType to set.
	 */
	public void setEntryType(long entryType) {
		putUsedField("entryType", entryType);
		this.entryType = entryType;
	}
	/**
	 * @return Returns the executeDate.
	 */
	public Timestamp getExecuteDate() {
		return executeDate;
	}
	/**
	 * @param executeDate The executeDate to set.
	 */
	public void setExecuteDate(Timestamp executeDate) {
		putUsedField("executeDate", executeDate);
		this.executeDate = executeDate;
	}
	/**
	 * @return Returns the inputUserID.
	 */
	public long getInputUserID() {
		return inputUserID;
	}
	/**
	 * @param inputUserID The inputUserID to set.
	 */
	public void setInputUserID(long inputUserID) {
		putUsedField("inputUserID", inputUserID);
		this.inputUserID = inputUserID;
	}
	/**
	 * @return Returns the interest.
	 */
	public double getInterest() {
		return interest;
	}
	/**
	 * @param interest The interest to set.
	 */
	public void setInterest(double interest) {
		putUsedField("interest", interest);
		this.interest = interest;
	}
	/**
	 * @return Returns the margin.
	 */
	public double getMargin() {
		return margin;
	}
	/**
	 * @param margin The margin to set.
	 */
	public void setMargin(double margin) {
		putUsedField("margin", margin);
		this.margin = margin;
	}
	/**
	 * @return Returns the netIncome.
	 */
	public double getNetIncome() {
		return netIncome;
	}
	/**
	 * @param netIncome The netIncome to set.
	 */
	public void setNetIncome(double netIncome) {
		putUsedField("netIncome", netIncome);
		this.netIncome = netIncome;
	}
	/**
	 * @return Returns the officeID.
	 */
	public long getOfficeID() {
		return officeID;
	}
	/**
	 * @param officeID The officeID to set.
	 */
	public void setOfficeID(long officeID) {
		putUsedField("officeID", officeID);
		this.officeID = officeID;
	}
	/**
	 * @return Returns the overdueFine.
	 */
	public double getOverdueFine() {
		return overdueFine;
	}
	/**
	 * @param overdueFine The overdueFine to set.
	 */
	public void setOverdueFine(double overdueFine) {
		putUsedField("overdueFine", overdueFine);
		this.overdueFine = overdueFine;
	}
	/**
	 * @return Returns the payCapitalAccountID.
	 */
	public long getPayCapitalAccountID() {
		return payCapitalAccountID;
	}
	/**
	 * @param payCapitalAccountID The payCapitalAccountID to set.
	 */
	public void setPayCapitalAccountID(long payCapitalAccountID) {
		putUsedField("payCapitalAccountID", payCapitalAccountID);
		this.payCapitalAccountID = payCapitalAccountID;
	}
	/**
	 * @return Returns the payClientID.
	 */
	public long getPayClientID() {
		return payClientID;
	}
	/**
	 * @param payClientID The payClientID to set.
	 */
	public void setPayClientID(long payClientID) {
		putUsedField("payClientID", payClientID);
		this.payClientID = payClientID;
	}
	/**
	 * @return Returns the payCounterpartID.
	 */
	public long getPayCounterpartID() {
		return payCounterpartID;
	}
	/**
	 * @param payCounterpartID The payCounterpartID to set.
	 */
	public void setPayCounterpartID(long payCounterpartID) {
		putUsedField("payCounterpartID", payCounterpartID);
		this.payCounterpartID = payCounterpartID;
	}
	/**
	 * @return Returns the paySecuritiesID.
	 */
	public long getPaySecuritiesID() {
		return paySecuritiesID;
	}
	/**
	 * @param paySecuritiesID The paySecuritiesID to set.
	 */
	public void setPaySecuritiesID(long paySecuritiesID) {
		putUsedField("paySecuritiesID", paySecuritiesID);
		this.paySecuritiesID = paySecuritiesID;
	}
	/**
	 * @return Returns the premium.
	 */
	public double getPremium() {
		return premium;
	}
	/**
	 * @param premium The premium to set.
	 */
	public void setPremium(double premium) {
		putUsedField("premium", premium);
		this.premium = premium;
	}
	/**
	 * @return Returns the principal.
	 */
	public double getPrincipal() {
		return principal;
	}
	/**
	 * @param principal The principal to set.
	 */
	public void setPrincipal(double principal) {
		putUsedField("principal", principal);
		this.principal = principal;
	}
	/**
	 * @return Returns the receiveCapitalAccountID.
	 */
	public long getReceiveCapitalAccountID() {
		return receiveCapitalAccountID;
	}
	/**
	 * @param receiveCapitalAccountID The receiveCapitalAccountID to set.
	 */
	public void setReceiveCapitalAccountID(long receiveCapitalAccountID) {
		putUsedField("receiveCapitalAccountID", receiveCapitalAccountID);
		this.receiveCapitalAccountID = receiveCapitalAccountID;
	}
	/**
	 * @return Returns the receiveClientID.
	 */
	public long getReceiveClientID() {
		return receiveClientID;
	}
	/**
	 * @param receiveClientID The receiveClientID to set.
	 */
	public void setReceiveClientID(long receiveClientID) {
		putUsedField("receiveClientID", receiveClientID);
		this.receiveClientID = receiveClientID;
	}
	/**
	 * @return Returns the receiveCounterpartID.
	 */
	public long getReceiveCounterpartID() {
		return receiveCounterpartID;
	}
	/**
	 * @param receiveCounterpartID The receiveCounterpartID to set.
	 */
	public void setReceiveCounterpartID(long receiveCounterpartID) {
		putUsedField("receiveCounterpartID", receiveCounterpartID);
		this.receiveCounterpartID = receiveCounterpartID;
	}
	/**
	 * @return Returns the receiveSecuritiesID.
	 */
	public long getReceiveSecuritiesID() {
		return receiveSecuritiesID;
	}
	/**
	 * @param receiveSecuritiesID The receiveSecuritiesID to set.
	 */
	public void setReceiveSecuritiesID(long receiveSecuritiesID) {
		putUsedField("receiveSecuritiesID", receiveSecuritiesID);
		this.receiveSecuritiesID = receiveSecuritiesID;
	}
	/**
	 * @return Returns the seatCode.
	 */
	public String getSeatCode() {
		return seatCode;
	}
	/**
	 * @param seatCode The seatCode to set.
	 */
	public void setSeatCode(String seatCode) {
		putUsedField("seatCode", seatCode);
		this.seatCode = seatCode;
	}
	/**
	 * @return Returns the stampDuty.
	 */
	public double getStampDuty() {
		return stampDuty;
	}
	/**
	 * @param stampDuty The stampDuty to set.
	 */
	public void setStampDuty(double stampDuty) {
		putUsedField("stampDuty", stampDuty);
		this.stampDuty = stampDuty;
	}
	/**
	 * @return Returns the stockExchangeNetIncome.
	 */
	public double getStockExchangeNetIncome() {
		return stockExchangeNetIncome;
	}
	/**
	 * @param stockExchangeNetIncome The stockExchangeNetIncome to set.
	 */
	public void setStockExchangeNetIncome(double stockExchangeNetIncome) {
		putUsedField("stockExchangeNetIncome", stockExchangeNetIncome);
		this.stockExchangeNetIncome = stockExchangeNetIncome;
	}
	/**
	 * @return Returns the stockJobberNetIncome.
	 */
	public double getStockJobberNetIncome() {
		return stockJobberNetIncome;
	}
	/**
	 * @param stockJobberNetIncome The stockJobberNetIncome to set.
	 */
	public void setStockJobberNetIncome(double stockJobberNetIncome) {
		putUsedField("stockJobberNetIncome", stockJobberNetIncome);
		this.stockJobberNetIncome = stockJobberNetIncome;
	}
	/**
	 * @return Returns the subTransactionType.
	 */
	public long getSubTransactionType() {
		return subTransactionType;
	}
	/**
	 * @param subTransactionType The subTransactionType to set.
	 */
	public void setSubTransactionType(long subTransactionType) {
		putUsedField("subTransactionType", subTransactionType);
		this.subTransactionType = subTransactionType;
	}
	/**
	 * @return Returns the suspenseInterest.
	 */
	public double getSuspenseInterest() {
		return suspenseInterest;
	}
	/**
	 * @param suspenseInterest The suspenseInterest to set.
	 */
	public void setSuspenseInterest(double suspenseInterest) {
		putUsedField("suspenseInterest", suspenseInterest);
		this.suspenseInterest = suspenseInterest;
	}
	/**
	 * @return Returns the term.
	 */
	public long getTerm() {
		return term;
	}
	/**
	 * @param term The term to set.
	 */
	public void setTerm(long term) {
		putUsedField("term", term);
		this.term = term;
	}
	/**
	 * @return Returns the termType.
	 */
	public long getTermTypeID() {
		return termTypeID;
	}
	/**
	 * @param termType The termType to set.
	 */
	public void setTermTypeID(long termTypeID) {
		putUsedField("termTypeID", termTypeID);
		this.termTypeID = termTypeID;
	}
	/**
	 * @return Returns the transactionDate.
	 */
	public Timestamp getTransactionDate() {
		return transactionDate;
	}
	/**
	 * @param transactionDate The transactionDate to set.
	 */
	public void setTransactionDate(Timestamp transactionDate) {
		putUsedField("transactionDate", transactionDate);
		this.transactionDate = transactionDate;
	}
	/**
	 * @return Returns the transactionType.
	 */
	public long getTransactionType() {
		return transactionType;
	}
	/**
	 * @param transactionType The transactionType to set.
	 */
	public void setTransactionType(long transactionType) {
		putUsedField("transactionType", transactionType);
		this.transactionType = transactionType;
	}
	/**
	 * @return Returns the unrealizedGain.
	 */
	public double getUnrealizedGain() {
		return unrealizedGain;
	}
	/**
	 * @param unrealizedGain The unrealizedGain to set.
	 */
	public void setUnrealizedGain(double unrealizedGain) {
		putUsedField("unrealizedGain", unrealizedGain);
		this.unrealizedGain = unrealizedGain;
	}
	/**
	 * @return Returns the ventureCapital.
	 */
	public double getVentureCapital() {
		return ventureCapital;
	}
	/**
	 * @param ventureCapital The ventureCapital to set.
	 */
	public void setVentureCapital(double ventureCapital) {
		putUsedField("ventureCapital", ventureCapital);
		this.ventureCapital = ventureCapital;
	}

	/**
	 * @return Returns the accountType.
	 */
	public long getAccountType() {
		return accountType;
	}
	/**
	 * @param accountType The accountType to set.
	 */
	public void setAccountType(long accountType) {
		putUsedField("accountType", accountType);
		this.accountType = accountType;
	}
	/**
	 * @return Returns the payBankID.
	 */
	public long getPayBankID() {
		return payBankID;
	}
	/**
	 * @param payBankID The payBankID to set.
	 */
	public void setPayBankID(long payBankID) {
		putUsedField("payBankID", payBankID);
		this.payBankID = payBankID;
	}
	/**
	 * @return Returns the receiveBankID.
	 */
	public long getReceiveBankID() {
		return receiveBankID;
	}
	/**
	 * @param receiveBankID The receiveBankID to set.
	 */
	public void setReceiveBankID(long receiveBankID) {
		putUsedField("receiveBankID", receiveBankID);
		this.receiveBankID = receiveBankID;
	}
	/**
	 * @return Returns the netPrincipal.
	 */
	public double getNetPrincipal() {
		return netPrincipal;
	}
	/**
	 * @param netPrincipal The netPrincipal to set.
	 */
	public void setNetPrincipal(double netPrincipal) {
		putUsedField("netPrincipal", netPrincipal);
		this.netPrincipal = netPrincipal;
	}
	
}
