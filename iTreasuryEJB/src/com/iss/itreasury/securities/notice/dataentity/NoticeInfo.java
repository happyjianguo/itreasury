package com.iss.itreasury.securities.notice.dataentity;
import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
/**
 * Title: iTreasury Description: Copyright: Copyright (c) 2003 Company:
 * iSoftStone
 * 
 * @author fanyang
 * @version Date of Creation 2004-3-1
 */
public class NoticeInfo extends SECBaseDataEntity { 
	private long id = -1;
	private String code = ""; //业务通知单编号
	private long deliveryOrderId = -1; //交割单ID
	private long transactionTypeId = -1; //交易类型
	private Timestamp executeDate = null; //收付款日期
	private long counterpartBankId = -1; //交易对手开户行
	private long counterpartAccountId = -1; //交易对手银行帐号
	private long companyBankId = -1; //公司开户行
	private long companyAccountId = -1; //公司银行帐号
	private long inputUserId = -1; //录入人
	private Timestamp inputDate = null; //录入时间
	private long updateUserId = -1; //修改人
	private Timestamp updateDate = null; //修改时间
	private long statusId = -1; //状态
	private long nextCheckUserId = -1;//下一个审核人
	private Timestamp timestamp = null;//时间戳
	private long nextCheckLevel = -1;//下一级审批级别
	private Timestamp publishEndDate = null;//发行终止日
	private double commissionChargeRate = 0.0;//手续费率
	//add by xwhe 
	private long officeId = -1; //办事处
	
	//为后来同合同关联的通知单加上
	private long contractId = -1;//合同ID/保单ID
	private double noticeAmount = 0.0;//本金金额
	private double noticePrice = 0.0;//交易价格
	private double noticeInterest = 0.0;//利息金额
	//private Timestamp executeDate = null;//执行日和上面的收付款日期共用
	private long directionId = -1;//收支方向
	private Timestamp StartInterestDate = null;//起息日
	private Timestamp endInterestDate = null;//结息日
	private double executeRate = 0.0;//执行利率
	private String remark = "";//备注
	private long accountTypeId = -1;//账户类型
	private long bankAccountId = -1;//银行账户开户行账号
	private long HNAccountId = -1;//华能财务账户开户行账号
	private long CurrentAccountId = -1;//活期账户账户编号
	
	//sec_noticeform表没有的(为后来同合同关联的通知单加上)
	private String contractCode = "";//合同编号
	private long currencyId = -1;//币种id
	
	
	//sec_noticeform表没有的
	private Timestamp deliveryOrderTimestamp = null;//交割单的时间戳
	private String counterpartBankName = "";//交易对手开户行名称
	private String counterpartAccountCode = "";//交易对手银行帐号
	private String counterpartAccountName = "";//交易对手帐户名称
	private String companyBankName = "";//公司开户行名称
	private String companyAccountCode = "";//公司银行帐号
	private String companyAccountName = "";//公司帐户名称
	private String inputUserName = "";//录入人名称
	private long pageCount = -1;//页面数
	private String systemTransactionCode = "";	//交易系统成交编号
	private Timestamp transactionDate = null;	//成交日期
	private String clientName = "";				//业务单位名称
	private String counterpartName = "";        //交易对手名称
	private Timestamp valueDate	= null;			//起息日
	private Timestamp maturityDate = null;		//还款日
	private double amount = 0.0;				//拆借金额
	private double rate	= 0.0;	//拆借利率
	private long term = -1;	//拆借期限
	private double suspenseInterest	= 0.0;	//应计利息
	private double interest = 0.0;	//实计利息
	private double maturityAmount = 0.0;	//到期还款金额/预付定金
	private double netIncome = 0.0;	//实际收付
	private double pledgeSecuritiesAmount = 0.0;//券面总额
	private Timestamp deliveryDate = null;//首次结算日
	private String pledgeSecuritiesCode = "";//抵押债券代码
	private String pledgeSecuritiesName = "";//抵押债券名称
	private double pledgeSecuritiesQuantity = 0;//抵押债券数量
	private double pledgeRate = 0.0;//折算比例
	private String securitiesName = "";//证券名称
	private double price = 0.0;//成交价格
	private double quantity = 0;//成交数量
	private String counterpartTrusteeCode = "";//交易对手债券托管帐号
	private double perSuspenseInterest = 0.0;//银行间国债二级的应计利息
	private double netPrice = 0.0;//净价
	private double netPriceAmount = 0.0;//净价金额
	private long accountId = -1;//交易帐号
	private String stockHolderAccountCode = "";//基金帐户代码
	private String stockHolderAccountName = "";//基金帐户名称
	private double tax = 0.0;//税费
	
	private long securitiesId = -1;//可转债名称
	private long oppositeSecuritiesId = -1;//转成股票名称
	private double oppositeQuantity = -1;//转成的股票数量
	
	//委托理财的已发放委托金额和已收回委托金额
	private double buyBackAmount = 0.0; //已发放委托金额
	private double receivedAmount = 0.0;//已收回委托金额

	private InutParameterInfo inutParameterInfo = null;
	
	private long attachId = -1;		//附件关联ID
	
	private long typeId = -1; //业务大类型
	
	private long actionTypeId = -1;  //交易子类型
	
	private long moduleId = -1;  //模块id
	
	private String province = ""; //汇入地（省）
	private String city = ""; //汇入地（市）
	private double adjustment = 0.0;//购回通知单中的调整项

	public double getAdjustment() {
		return adjustment;
	}
	public void setAdjustment(double adjustment) {
		this.adjustment = adjustment;
		putUsedField("adjustment", adjustment);
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
		putUsedField("province", province);
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
		putUsedField("city", city);
	}
	public long getTypeId() {
		return typeId;
	}
	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}
	public long getActionTypeId() {
		return actionTypeId;
	}
	public void setActionTypeId(long actionTypeId) {
		this.actionTypeId = actionTypeId;
	}
	public long getAttachId() {
		return attachId;
	}
	public void setAttachId(long attachId) {
		this.attachId = attachId;
		putUsedField("attachId", attachId);
	}
	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}
	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}
	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code The code to set.
	 */
	public void setCode(String code) {
		this.code = code;
		putUsedField("code", code);
	}
	/**
	 * @return Returns the companyAccountId.
	 */
	public long getCompanyAccountId() {
		return companyAccountId;
	}
	/**
	 * @param companyAccountId The companyAccountId to set.
	 */
	public void setCompanyAccountId(long companyAccountId) {
		this.companyAccountId = companyAccountId;
		putUsedField("companyAccountId", companyAccountId);
	}
	/**
	 * @return Returns the companyBankId.
	 */
	public long getCompanyBankId() {
		return companyBankId;
	}
	/**
	 * @param companyBankId The companyBankId to set.
	 */
	public void setCompanyBankId(long companyBankId) {
		this.companyBankId = companyBankId;
		putUsedField("companyBankId", companyBankId);
	}
	/**
	 * @return Returns the counterpartAccountId.
	 */
	public long getCounterpartAccountId() {
		return counterpartAccountId;
	}
	/**
	 * @param counterpartAccountId The counterpartAccountId to set.
	 */
	public void setCounterpartAccountId(long counterpartAccountId) {
		this.counterpartAccountId = counterpartAccountId;
		putUsedField("counterpartAccountId", counterpartAccountId);
	}
	/**
	 * @return Returns the counterpartBankId.
	 */
	public long getCounterpartBankId() {
		return counterpartBankId;
	}
	/**
	 * @param counterpartBankId The counterpartBankId to set.
	 */
	public void setCounterpartBankId(long counterpartBankId) {
		this.counterpartBankId = counterpartBankId;
		putUsedField("counterpartBankId", counterpartBankId);
	}
	/**
	 * @return Returns the deliveryOrderId.
	 */
	public long getDeliveryOrderId() {
		return deliveryOrderId;
	}
	/**
	 * @param deliveryOrderId The deliveryOrderId to set.
	 */
	public void setDeliveryOrderId(long deliveryOrderId) {
		this.deliveryOrderId = deliveryOrderId;
		putUsedField("deliveryOrderId", deliveryOrderId);
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
		this.executeDate = executeDate;
		putUsedField("executeDate", executeDate);
	}
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);        
	}
	/**
	 * @return Returns the inputDate.
	 */
	public Timestamp getInputDate() {
		return inputDate;
	}
	/**
	 * @param inputDate The inputDate to set.
	 */
	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
		putUsedField("inputDate", inputDate);
	}
	/**
	 * @return Returns the inputUserId.
	 */
	public long getInputUserId() {
		return inputUserId;
	}
	/**
	 * @param inputUserId The inputUserId to set.
	 */
	public void setInputUserId(long inputUserId) {
		this.inputUserId = inputUserId;
		putUsedField("inputUserId", inputUserId);
	}
	/**
	 * @return Returns the nextCheckUserId.
	 */
	public long getNextCheckUserId() {
		return nextCheckUserId;
	}
	/**
	 * @param nextCheckUserId The nextCheckUserId to set.
	 */
	public void setNextCheckUserId(long nextCheckUserId) {
		this.nextCheckUserId = nextCheckUserId;
		putUsedField("nextCheckUserId", nextCheckUserId);
	}
	/**
	 * @return Returns the statusId.
	 */
	public long getStatusId() {
		return statusId;
	}
	/**
	 * @param statusId The statusId to set.
	 */
	public void setStatusId(long statusId) {
		this.statusId = statusId;
		putUsedField("statusId", statusId);
	}
	/**
	 * @return Returns the transactionTypeId.
	 */
	public long getTransactionTypeId() {
		return transactionTypeId;
	}
	/**
	 * @param transactionTypeId The transactionTypeId to set.
	 */
	public void setTransactionTypeId(long transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
		putUsedField("transactionTypeId", transactionTypeId);
	}
	/**
	 * @return Returns the updateDate.
	 */
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	/**
	 * @param updateDate The updateDate to set.
	 */
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
		putUsedField("updateDate", updateDate);
	}
	/**
	 * @return Returns the updateUserId.
	 */
	public long getUpdateUserId() {
		return updateUserId;
	}
	/**
	 * @param updateUserId The updateUserId to set.
	 */
	public void setUpdateUserId(long updateUserId) {
		this.updateUserId = updateUserId;
		putUsedField("updateUserId", updateUserId);
	}
	/**
	 * @return Returns the timestamp.
	 */
	public Timestamp getTimestamp() {
		return timestamp;
	}
	/**
	 * @param timestamp The timestamp to set.
	 */
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
		putUsedField("timestamp", timestamp);
	}
	/**
	 * @return Returns the companyAccountName.
	 */
	public String getCompanyAccountName() {
		return companyAccountName;
	}
	/**
	 * @param companyAccountName The companyAccountName to set.
	 */
	public void setCompanyAccountName(String companyAccountName) {
		this.companyAccountName = companyAccountName;
	}
	/**
	 * @return Returns the companyBankName.
	 */
	public String getCompanyBankName() {
		return companyBankName;
	}
	/**
	 * @param companyBankName The companyBankName to set.
	 */
	public void setCompanyBankName(String companyBankName) {
		this.companyBankName = companyBankName;
	}
	/**
	 * @return Returns the counterpartAccountName.
	 */
	public String getCounterpartAccountName() {
		return counterpartAccountName;
	}
	/**
	 * @param counterpartAccountName The counterpartAccountName to set.
	 */
	public void setCounterpartAccountName(String counterpartAccountName) {
		this.counterpartAccountName = counterpartAccountName;
	}
	/**
	 * @return Returns the counterpartBankName.
	 */
	public String getCounterpartBankName() {
		return counterpartBankName;
	}
	/**
	 * @param counterpartBankName The counterpartBankName to set.
	 */
	public void setCounterpartBankName(String counterpartBankName) {
		this.counterpartBankName = counterpartBankName;
	}
	/**
	 * @return Returns the companyAccountCode.
	 */
	public String getCompanyAccountCode() {
		return companyAccountCode;
	}
	/**
	 * @param companyAccountCode The companyAccountCode to set.
	 */
	public void setCompanyAccountCode(String companyAccountCode) {
		this.companyAccountCode = companyAccountCode;
	}
	/**
	 * @return Returns the counterpartAccountCode.
	 */
	public String getCounterpartAccountCode() {
		return counterpartAccountCode;
	}
	/**
	 * @param counterpartAccountCode The counterpartAccountCode to set.
	 */
	public void setCounterpartAccountCode(String counterpartAccountCode) {
		this.counterpartAccountCode = counterpartAccountCode;
	}
	/**
	 * @return Returns the inputUserName.
	 */
	public String getInputUserName() {
		return inputUserName;
	}
	/**
	 * @param inputUserName The inputUserName to set.
	 */
	public void setInputUserName(String inputUserName) {
		this.inputUserName = inputUserName;
	}
	/**
	 * @return Returns the pageCount.
	 */
	public long getPageCount() {
		return pageCount;
	}
	/**
	 * @param pageCount The pageCount to set.
	 */
	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}
	/**
	 * @return Returns the systemTransactionCode.
	 */
	public String getSystemTransactionCode() {
		return systemTransactionCode;
	}
	/**
	 * @param systemTransactionCode The systemTransactionCode to set.
	 */
	public void setSystemTransactionCode(String systemTransactionCode) {
		this.systemTransactionCode = systemTransactionCode;
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
		this.transactionDate = transactionDate;
	}
	/**
	 * @return Returns the clientName.
	 */
	public String getClientName() {
		return clientName;
	}
	/**
	 * @param clientName The clientName to set.
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	/**
	 * @return Returns the counterpartName.
	 */
	public String getCounterpartName() {
		return counterpartName;
	}
	/**
	 * @param counterpartName The counterpartName to set.
	 */
	public void setCounterpartName(String counterpartName) {
		this.counterpartName = counterpartName;
	}
	/**
	 * @return Returns the maturityDate.
	 */
	public Timestamp getMaturityDate() {
		return maturityDate;
	}
	/**
	 * @param maturityDate The maturityDate to set.
	 */
	public void setMaturityDate(Timestamp maturityDate) {
		this.maturityDate = maturityDate;
	}
	/**
	 * @return Returns the valueDate.
	 */
	public Timestamp getValueDate() {
		return valueDate;
	}
	/**
	 * @param valueDate The valueDate to set.
	 */
	public void setValueDate(Timestamp valueDate) {
		this.valueDate = valueDate;
	}
	/**
	 * @return Returns the amount.
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * @param amount The amount to set.
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	/**
	 * @return Returns the rate.
	 */
	public double getRate() {
		return rate;
	}
	/**
	 * @param rate The rate to set.
	 */
	public void setRate(double rate) {
		this.rate = rate;
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
		this.term = term;
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
		this.interest = interest;
	}
	/**
	 * @return Returns the maturityAmount.
	 */
	public double getMaturityAmount() {
		return maturityAmount;
	}
	/**
	 * @param maturityAmount The maturityAmount to set.
	 */
	public void setMaturityAmount(double maturityAmount) {
		this.maturityAmount = maturityAmount;
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
		this.suspenseInterest = suspenseInterest;
	}
	/**
	 * @return
	 */
	public double getNetIncome()
	{
		return netIncome;
	}

	/**
	 * @param d
	 */
	public void setNetIncome(double d)
	{
		netIncome = d;
	}

	/**
	 * @return Returns the pledgeSecuritiesAmount.
	 */
	public double getPledgeSecuritiesAmount()
	{
		return pledgeSecuritiesAmount;
	}
	/**            
	 * @param pledgeSecuritiesAmount The pledgeSecuritiesAmount to set.
	 */
	public void setPledgeSecuritiesAmount(double pledgeSecuritiesAmount)
	{
		this.pledgeSecuritiesAmount = pledgeSecuritiesAmount;
	}
	/**
	 * @return Returns the deliveryDate.
	 */
	public Timestamp getDeliveryDate()
	{
		return deliveryDate;
	}
	/**
	 * @param deliveryDate The deliveryDate to set.
	 */
	public void setDeliveryDate(Timestamp deliveryDate)
	{
		this.deliveryDate = deliveryDate;
	}
	/**
	 * @return Returns the pledgeRate.
	 */
	public double getPledgeRate()
	{
		return pledgeRate;
	}
	/**
	 * @param pledgeRate The pledgeRate to set.
	 */
	public void setPledgeRate(double pledgeRate)
	{
		this.pledgeRate = pledgeRate;
	}
	/**
	 * @return Returns the pledgeSecuritiesCode.
	 */
	public String getPledgeSecuritiesCode()
	{
		return pledgeSecuritiesCode;
	}
	/**
	 * @param pledgeSecuritiesCode The pledgeSecuritiesCode to set.
	 */
	public void setPledgeSecuritiesCode(String pledgeSecuritiesCode)
	{
		this.pledgeSecuritiesCode = pledgeSecuritiesCode;
	}
	/**
	 * @return Returns the pledgeSecuritiesName.
	 */
	public String getPledgeSecuritiesName()
	{
		return pledgeSecuritiesName;
	}
	/**
	 * @param pledgeSecuritiesName The pledgeSecuritiesName to set.
	 */
	public void setPledgeSecuritiesName(String pledgeSecuritiesName)
	{
		this.pledgeSecuritiesName = pledgeSecuritiesName;
	}
	/**
	 * @return Returns the pledgeSecuritiesQuantity.
	 */
	public double getPledgeSecuritiesQuantity()
	{
		return pledgeSecuritiesQuantity;
	}
	/**
	 * @param pledgeSecuritiesQuantity The pledgeSecuritiesQuantity to set.
	 */
	public void setPledgeSecuritiesQuantity(double pledgeSecuritiesQuantity)
	{
		this.pledgeSecuritiesQuantity = pledgeSecuritiesQuantity;
	}
	/**
	 * @return Returns the securitiesName.
	 */
	public String getSecuritiesName()
	{
		return securitiesName;
	}
	/**
	 * @param securitiesName The securitiesName to set.
	 */
	public void setSecuritiesName(String securitiesName)
	{
		this.securitiesName = securitiesName;
	}
	/**
	 * @return Returns the price.
	 */
	public double getPrice()
	{
		return price;
	}
	/**
	 * @param price The price to set.
	 */
	public void setPrice(double price)
	{
		this.price = price;
	}
	/**
	 * @return Returns the quantity.
	 */
	public double getQuantity()
	{
		return quantity;
	}
	/**
	 * @param quantity The quantity to set.
	 */
	public void setQuantity(double quantity)
	{
		this.quantity = quantity;
	}
	/**
	 * @return Returns the counterpartTrusteeCode.
	 */
	public String getCounterpartTrusteeCode()
	{
		return counterpartTrusteeCode;
	}
	/**
	 * @param counterpartTrusteeCode The counterpartTrusteeCode to set.
	 */
	public void setCounterpartTrusteeCode(String counterpartTrusteeCode)
	{
		this.counterpartTrusteeCode = counterpartTrusteeCode;
	}
	/**
	 * @return Returns the netPrice.
	 */
	public double getNetPrice()
	{
		return netPrice;
	}
	/**
	 * @param netPrice The netPrice to set.
	 */
	public void setNetPrice(double netPrice)
	{
		this.netPrice = netPrice;
	}
	/**
	 * @return Returns the netPriceAmount.
	 */
	public double getNetPriceAmount()
	{
		return netPriceAmount;
	}
	/**
	 * @param netPriceAmount The netPriceAmount to set.
	 */
	public void setNetPriceAmount(double netPriceAmount)
	{
		this.netPriceAmount = netPriceAmount;
	}
	/**
	 * @return Returns the perSuspenseInterest.
	 */
	public double getPerSuspenseInterest()
	{
		return perSuspenseInterest;
	}
	/**
	 * @param perSuspenseInterest The perSuspenseInterest to set.
	 */
	public void setPerSuspenseInterest(double perSuspenseInterest)
	{
		this.perSuspenseInterest = perSuspenseInterest;
	}
	/**
	 * @return Returns the accountId.
	 */
	public long getAccountId()
	{
		return accountId;
	}
	/**
	 * @param accountId The accountId to set.
	 */
	public void setAccountId(long accountId)
	{
		this.accountId = accountId;
	}        
	/**
	 * @return Returns the stockHolderAccountCode.
	 */
	public String getStockHolderAccountCode()
	{
		return stockHolderAccountCode;
	}
	/**
	 * @param stockHolderAccountCode The stockHolderAccountCode to set.
	 */
	public void setStockHolderAccountCode(String stockHolderAccountCode)
	{
		this.stockHolderAccountCode = stockHolderAccountCode;
	}
	/**
	 * @return Returns the stockHolderAccountName.
	 */
	public String getStockHolderAccountName()
	{
		return stockHolderAccountName;
	}
	/**
	 * @param stockHolderAccountName The stockHolderAccountName to set.
	 */
	public void setStockHolderAccountName(String stockHolderAccountName)
	{
		this.stockHolderAccountName = stockHolderAccountName;
	}
	/**
	 * @return Returns the deliveryOrderTimestamp.
	 */
	public Timestamp getDeliveryOrderTimestamp()
	{
		return deliveryOrderTimestamp;
	}
	/**
	 * @param deliveryOrderTimestamp The deliveryOrderTimestamp to set.
	 */
	public void setDeliveryOrderTimestamp(Timestamp deliveryOrderTimestamp)
	{
		this.deliveryOrderTimestamp = deliveryOrderTimestamp;
	}
	/**
	 * @return Returns the tax.
	 */
	public double getTax()
	{
		return tax;
	}
	/**
	 * @param tax The tax to set.
	 */
	public void setTax(double tax)
	{
		this.tax = tax;
	}
	/**
	 * @return Returns the noticeAmount.
	 */
	public double getNoticeAmount()
	{
		return noticeAmount;
	}
	/**
	 * @param noticeAmount The noticeAmount to set.
	 */
	public void setNoticeAmount(double noticeAmount)
	{
		this.noticeAmount = noticeAmount;
		putUsedField("noticeAmount", noticeAmount);
	}
	/**
	 * @return Returns the accountTypeId.
	 */
	public long getAccountTypeId()
	{
		return accountTypeId;
	}
	/**
	 * @param accountTypeId The accountTypeId to set.
	 */
	public void setAccountTypeId(long accountTypeId)
	{
		this.accountTypeId = accountTypeId;
		putUsedField("accountTypeId", accountTypeId);
	}
	/**
	 * @return Returns the bankAccountId.
	 */
	public long getBankAccountId()
	{
		return bankAccountId;
	}
	/**
	 * @param bankAccountId The bankAccountId to set.
	 */
	public void setBankAccountId(long bankAccountId)
	{
		this.bankAccountId = bankAccountId;
		putUsedField("bankAccountId", bankAccountId);
	}
	/**
	 * @return Returns the contractId.
	 */
	public long getContractId()
	{
		return contractId;
	}
	/**
	 * @param contractId The contractId to set.
	 */
	public void setContractId(long contractId)
	{
		this.contractId = contractId;
		putUsedField("contractId", contractId);
	}
	/**
	 * @return Returns the currentAccountId.
	 */
	public long getCurrentAccountId()
	{
		return CurrentAccountId;
	}
	/**
	 * @param currentAccountId The currentAccountId to set.
	 */
	public void setCurrentAccountId(long currentAccountId)
	{
		CurrentAccountId = currentAccountId;
		putUsedField("currentAccountId", currentAccountId);
	}
	/**
	 * @return Returns the directionId.
	 */
	public long getDirectionId()
	{
		return directionId;
	}
	/**
	 * @param directionId The directionId to set.
	 */
	public void setDirectionId(long directionId)
	{
		this.directionId = directionId;
		putUsedField("directionId", directionId);
	}
	/**
	 * @return Returns the endInterestDate.
	 */
	public Timestamp getEndInterestDate()
	{
		return endInterestDate;
	}
	/**
	 * @param endInterestDate The endInterestDate to set.
	 */
	public void setEndInterestDate(Timestamp endInterestDate)
	{
		this.endInterestDate = endInterestDate;
		putUsedField("endInterestDate", endInterestDate);
	}
	/**
	 * @return Returns the executeRate.
	 */
	public double getExecuteRate()
	{
		return executeRate;
	}
	/**
	 * @param executeRate The executeRate to set.
	 */
	public void setExecuteRate(double executeRate)
	{
		this.executeRate = executeRate;
		putUsedField("executeRate", executeRate);
	}
	/**
	 * @return Returns the hNAccountId.
	 */
	public long getHNAccountId()
	{
		return HNAccountId;
	}
	/**
	 * @param accountId The hNAccountId to set.
	 */
	public void setHNAccountId(long accountId)
	{
		HNAccountId = accountId;
		putUsedField("accountId", accountId);
	}
	/**
	 * @return Returns the noticeInterest.
	 */
	public double getNoticeInterest()
	{
		return noticeInterest;
	}
	/**
	 * @param noticeInterest The noticeInterest to set.
	 */
	public void setNoticeInterest(double noticeInterest)
	{
		this.noticeInterest = noticeInterest;
		putUsedField("noticeInterest", noticeInterest);
	}
	/**
	 * @return Returns the noticePrice.
	 */
	public double getNoticePrice()
	{
		return noticePrice;
	}
	/**
	 * @param noticePrice The noticePrice to set.
	 */
	public void setNoticePrice(double noticePrice)
	{
		this.noticePrice = noticePrice;
		putUsedField("noticePrice", noticePrice);
	}
	/**
	 * @return Returns the remark.
	 */
	public String getRemark()
	{
		return remark;
	}
	/**
	 * @param remark The remark to set.
	 */
	public void setRemark(String remark)
	{
		this.remark = remark;
		putUsedField("remark", remark);
	}
	/**
	 * @return Returns the startInterestDate.
	 */
	public Timestamp getStartInterestDate()
	{
		return StartInterestDate;
	}
	/**
	 * @param startInterestDate The startInterestDate to set.
	 */
	public void setStartInterestDate(Timestamp startInterestDate)
	{
		StartInterestDate = startInterestDate;
		putUsedField("startInterestDate", startInterestDate);
	}
	/**
	 * @return Returns the contractCode.
	 */
	public String getContractCode()
	{
		return contractCode;
	}
	/**
	 * @param contractCode The contractCode to set.
	 */
	public void setContractCode(String contractCode)
	{
		this.contractCode = contractCode;
	}
	/**
	 * @return Returns the currencyId.
	 */
	public long getCurrencyId()
	{
		return currencyId;
	}
	/**
	 * @param currencyId The currencyId to set.
	 */
	public void setCurrencyId(long currencyId)
	{
		this.currencyId = currencyId;
		putUsedField("currencyId", currencyId);
	}


	/**
	 * @return
	 */
	public long getNextCheckLevel()
	{
		return nextCheckLevel;
	}

	/**
	 * @param nextCheckLevel
	 */
	public void setNextCheckLevel(long nextCheckLevel)
	{
		this.nextCheckLevel = nextCheckLevel;
		putUsedField("nextCheckLevel", nextCheckLevel);
	}

	/**
	 * @return
	 */
	public double getOppositeQuantity()
	{
		return oppositeQuantity;
	}

	/**
	 * @return
	 */
	public long getOppositeSecuritiesId()
	{
		return oppositeSecuritiesId;
	}

	/**
	 * @return
	 */
	public long getSecuritiesId()
	{
		return securitiesId;
	}

	/**
	 * @param d
	 */
	public void setOppositeQuantity(double d)
	{
		oppositeQuantity = d;
	}

	/**
	 * @param l
	 */
	public void setOppositeSecuritiesId(long l)
	{
		oppositeSecuritiesId = l;
	}

	/**
	 * @param l
	 */
	public void setSecuritiesId(long l)
	{
		securitiesId = l;
	}

	/**
	 * @return
	 */
	public Timestamp getPublishEndDate()
	{
		return publishEndDate;
	}

	/**
	 * @param timestamp
	 */
	public void setPublishEndDate(Timestamp publishEndDate)
	{
		this.publishEndDate = publishEndDate;
		putUsedField("publishEndDate", publishEndDate);
	}

	/**
	 * @return
	 */
	public double getCommissionChargeRate()
	{
		return commissionChargeRate;
	}

	/**
	 * @param d
	 */
	public void setCommissionChargeRate(double d)
	{
		commissionChargeRate = d;
		putUsedField("commissionChargeRate", commissionChargeRate);
	}

	/**
	 * @return
	 */
	public double getBuyBackAmount()
	{
		return buyBackAmount;
	}

	/**
	 * @return
	 */
	public double getReceivedAmount()
	{
		return receivedAmount;
	}

	/**
	 * @param d
	 */
	public void setBuyBackAmount(double d)
	{
		buyBackAmount = d;
	}

	/**
	 * @param d
	 */
	public void setReceivedAmount(double d)
	{
		receivedAmount = d;
	}
	public long getOfficeId() {
		return officeId;
	}
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
		putUsedField("officeId", officeId);
	}
	public long getModuleId() {
		return moduleId;
	}
	public void setModuleId(long moduleId) {
		this.moduleId = moduleId;
	}

}