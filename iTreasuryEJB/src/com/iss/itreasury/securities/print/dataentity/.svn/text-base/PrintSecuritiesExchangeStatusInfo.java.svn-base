/*
 * 创建日期 2004-5-11
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package com.iss.itreasury.securities.print.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
 
import com.iss.itreasury.securities.util.SECBaseDataEntity;
/**
 * @author 王怡
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class PrintSecuritiesExchangeStatusInfo extends SECBaseDataEntity	implements Serializable {
	
	//一些说明：页面上需要的东西的计算方法
	
	//收支方向:NameRef.getReceiveOrPayDirectionByTransactionTypeId(transactionTypeId)
	
	//实际盈亏:实际收付－单位成本×成交数量（如果 单位成本 为零，则 实际盈亏 也为零）
	
	//交易金额:noticeAmount+noticeInterest

	//业务类型(ID)
	private long businesstypeId = -1;
	//交易类型(ID)
	private long transactionTypeId = -1;
	//业务通知单(CODE)
	private String noticeFormCode = "";
	//成交日期Timestamp
	private Timestamp transactionDate = null;
	//业务单位(ID)
	private long clientId = -1;
	//股东帐户名称
	private long stockHolderAccountId = -1;
	//交易对手(ID)
	private long counterPartId = -1;
	//起息日
	private Timestamp valueDate = null;
	//还款日 购回日期 到期结算日
	private Timestamp maturityDate = null;
	//资产回购开始日 委托理财开始日 合同生效日开始日
	private Timestamp transactionStartDate = null;
	//资产回购结束日 委托理财结束日 合同生效日结束日
	private Timestamp transactionEndDate = null;
	//抵押债券名称(ID)
	private long pledgeSecuritiesId = -1;
	//开户营业部名称
	private long bankOfDepositId = -1;
	//资金帐号(ID) 交易帐号(ID)
	private long accountId = -1;
	//证券(ID) 可转债名称 (ID)
	private long securitiesId = -1;
	//结算日 首次结算日
	private Timestamp deliveryDate = null;
	//转成股票名称(ID)
	private long oppositeSecuritiesId = -1;
	//成交价格 转股价格 全价
	private double price  =0.0;
	//净价 净值
	private double netPrice  =0.0;
	//成交数量 转成的债券数量 份额
	private double quantity = 0.0;
	//券面总额
	private double pledgeSecuritiesAmount = 0.0;
	//抵押标准券 抵押债券数量
	private double pledgeSecuritiesQuantity = 0.0;
	//折算比例
	private double pledgeRate = 0.0;
	//转成的股票数量
	private long oppositeQuantity = -1;
	//成交金额 拆借金额 转股金额 全价金额 回购金额 委托金额 存款金额 承销金额
	private double amount = 0.0;
	//承销方式ID
	private long interestTypeId= -1;
	//手续费率
	private double commissionChargeRate = 0.0;
	//币种(ID)
	private long currencyId = -1;
	//净价金额
	private double netPriceAmount = 0.0;
	//预付定金比例 拆借利率 回购利率 利率 收益率
	private double rate = 0.0;
	//结息方式(ID)
	private long settlementTypeId = -1;
	//本金金额
	private double noticeAmount = 0.0;
	//交易执行日
	private Timestamp executeDate = null;
	//实付利息 实收收益金额
	private double noticeInterest = 0.0;
	//拆借期限 回购期限
	private long term = -1;
	//应计利息
	private double suspenseInterest = 0.0;
	//实计利息
	private double Interest = 0.0;
	//预付定金 到期还款金额 购回金额
	private double maturityAmount = 0.0;
	//税费
	private double tax = 0.0;
	//实际收付 不足一股的剩余收入
	private double netIncome = 0.0;
	//单位成本(全价)
	private double unitCost = 0.0;
	//单位成本(净价)
	private double unitNetCost = 0.0;
	//状态
	private long statusId = -1;
	
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
	 */
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
	 */
	public void setId(long id) {
		// TODO Auto-generated method stub

	}



	/**
	 * @return
	 */
	public long getAccountId() {
		return accountId;
	}

	/**
	 * @return
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @return
	 */
	public long getBankOfDepositId() {
		return bankOfDepositId;
	}

	/**
	 * @return
	 */
	public long getBusinesstypeId() {
		return businesstypeId;
	}

	/**
	 * @return
	 */
	public long getClientId() {
		return clientId;
	}

	/**
	 * @return
	 */
	public double getCommissionChargeRate() {
		return commissionChargeRate;
	}

	/**
	 * @return
	 */
	public long getCounterPartId() {
		return counterPartId;
	}

	/**
	 * @return
	 */
	public long getCurrencyId() {
		return currencyId;
	}

	/**
	 * @return
	 */
	public Timestamp getDeliveryDate() {
		return deliveryDate;
	}

	/**
	 * @return
	 */
	public Timestamp getExecuteDate() {
		return executeDate;
	}

	/**
	 * @return
	 */
	public double getInterest() {
		return Interest;
	}

	/**
	 * @return
	 */
	public long getInterestTypeId() {
		return interestTypeId;
	}

	/**
	 * @return
	 */
	public double getMaturityAmount() {
		return maturityAmount;
	}

	/**
	 * @return
	 */
	public Timestamp getMaturityDate() {
		return maturityDate;
	}

	/**
	 * @return
	 */
	public double getNetIncome() {
		return netIncome;
	}

	/**
	 * @return
	 */
	public double getNetPrice() {
		return netPrice;
	}

	/**
	 * @return
	 */
	public double getNetPriceAmount() {
		return netPriceAmount;
	}

	/**
	 * @return
	 */
	public double getNoticeAmount() {
		return noticeAmount;
	}

	/**
	 * @return
	 */
	public String getNoticeFormCode() {
		return noticeFormCode;
	}

	/**
	 * @return
	 */
	public double getNoticeInterest() {
		return noticeInterest;
	}

	/**
	 * @return
	 */
	public long getOppositeQuantity() {
		return oppositeQuantity;
	}

	/**
	 * @return
	 */
	public long getOppositeSecuritiesId() {
		return oppositeSecuritiesId;
	}

	/**
	 * @return
	 */
	public double getPledgeRate() {
		return pledgeRate;
	}

	/**
	 * @return
	 */
	public double getPledgeSecuritiesAmount() {
		return pledgeSecuritiesAmount;
	}

	/**
	 * @return
	 */
	public long getPledgeSecuritiesId() {
		return pledgeSecuritiesId;
	}

	/**
	 * @return
	 */
	public double getPledgeSecuritiesQuantity() {
		return pledgeSecuritiesQuantity;
	}

	/**
	 * @return
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @return
	 */
	public double getQuantity() {
		return quantity;
	}

	/**
	 * @return
	 */
	public double getRate() {
		return rate;
	}

	/**
	 * @return
	 */
	public long getSecuritiesId() {
		return securitiesId;
	}

	/**
	 * @return
	 */
	public long getSettlementTypeId() {
		return settlementTypeId;
	}

	/**
	 * @return
	 */
	public long getStatusId() {
		return statusId;
	}

	/**
	 * @return
	 */
	public long getStockHolderAccountId() {
		return stockHolderAccountId;
	}

	/**
	 * @return
	 */
	public double getSuspenseInterest() {
		return suspenseInterest;
	}

	/**
	 * @return
	 */
	public double getTax() {
		return tax;
	}

	/**
	 * @return
	 */
	public long getTerm() {
		return term;
	}

	/**
	 * @return
	 */
	public Timestamp getTransactionDate() {
		return transactionDate;
	}

	/**
	 * @return
	 */
	public Timestamp getTransactionEndDate() {
		return transactionEndDate;
	}

	/**
	 * @return
	 */
	public Timestamp getTransactionStartDate() {
		return transactionStartDate;
	}

	/**
	 * @return
	 */
	public long getTransactionTypeId() {
		return transactionTypeId;
	}

	/**
	 * @return
	 */
	public double getUnitCost() {
		return unitCost;
	}

	/**
	 * @return
	 */
	public double getUnitNetCost() {
		return unitNetCost;
	}

	/**
	 * @return
	 */
	public Timestamp getValueDate() {
		return valueDate;
	}

	/**
	 * @param l
	 */
	public void setAccountId(long l) {
		accountId = l;
	}

	/**
	 * @param d
	 */
	public void setAmount(double d) {
		amount = d;
	}

	/**
	 * @param l
	 */
	public void setBankOfDepositId(long l) {
		bankOfDepositId = l;
	}

	/**
	 * @param l
	 */
	public void setBusinesstypeId(long l) {
		businesstypeId = l;
	}

	/**
	 * @param l
	 */
	public void setClientId(long l) {
		clientId = l;
	}

	/**
	 * @param d
	 */
	public void setCommissionChargeRate(double d) {
		commissionChargeRate = d;
	}

	/**
	 * @param l
	 */
	public void setCounterPartId(long l) {
		counterPartId = l;
	}

	/**
	 * @param l
	 */
	public void setCurrencyId(long l) {
		currencyId = l;
	}

	/**
	 * @param timestamp
	 */
	public void setDeliveryDate(Timestamp timestamp) {
		deliveryDate = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setExecuteDate(Timestamp timestamp) {
		executeDate = timestamp;
	}

	/**
	 * @param d
	 */
	public void setInterest(double d) {
		Interest = d;
	}

	/**
	 * @param l
	 */
	public void setInterestTypeId(long l) {
		interestTypeId = l;
	}

	/**
	 * @param d
	 */
	public void setMaturityAmount(double d) {
		maturityAmount = d;
	}

	/**
	 * @param timestamp
	 */
	public void setMaturityDate(Timestamp timestamp) {
		maturityDate = timestamp;
	}

	/**
	 * @param d
	 */
	public void setNetIncome(double d) {
		netIncome = d;
	}

	/**
	 * @param d
	 */
	public void setNetPrice(double d) {
		netPrice = d;
	}

	/**
	 * @param d
	 */
	public void setNetPriceAmount(double d) {
		netPriceAmount = d;
	}

	/**
	 * @param d
	 */
	public void setNoticeAmount(double d) {
		noticeAmount = d;
	}

	/**
	 * @param string
	 */
	public void setNoticeFormCode(String string) {
		noticeFormCode = string;
	}

	/**
	 * @param d
	 */
	public void setNoticeInterest(double d) {
		noticeInterest = d;
	}

	/**
	 * @param l
	 */
	public void setOppositeQuantity(long l) {
		oppositeQuantity = l;
	}

	/**
	 * @param l
	 */
	public void setOppositeSecuritiesId(long l) {
		oppositeSecuritiesId = l;
	}

	/**
	 * @param d
	 */
	public void setPledgeRate(double d) {
		pledgeRate = d;
	}

	/**
	 * @param d
	 */
	public void setPledgeSecuritiesAmount(double d) {
		pledgeSecuritiesAmount = d;
	}

	/**
	 * @param l
	 */
	public void setPledgeSecuritiesId(long l) {
		pledgeSecuritiesId = l;
	}

	/**
	 * @param d
	 */
	public void setPledgeSecuritiesQuantity(double d) {
		pledgeSecuritiesQuantity = d;
	}

	/**
	 * @param d
	 */
	public void setPrice(double d) {
		price = d;
	}

	/**
	 * @param d
	 */
	public void setQuantity(double d) {
		quantity = d;
	}

	/**
	 * @param d
	 */
	public void setRate(double d) {
		rate = d;
	}

	/**
	 * @param l
	 */
	public void setSecuritiesId(long l) {
		securitiesId = l;
	}

	/**
	 * @param l
	 */
	public void setSettlementTypeId(long l) {
		settlementTypeId = l;
	}

	/**
	 * @param l
	 */
	public void setStatusId(long l) {
		statusId = l;
	}

	/**
	 * @param l
	 */
	public void setStockHolderAccountId(long l) {
		stockHolderAccountId = l;
	}

	/**
	 * @param d
	 */
	public void setSuspenseInterest(double d) {
		suspenseInterest = d;
	}

	/**
	 * @param d
	 */
	public void setTax(double d) {
		tax = d;
	}

	/**
	 * @param l
	 */
	public void setTerm(long l) {
		term = l;
	}

	/**
	 * @param timestamp
	 */
	public void setTransactionDate(Timestamp timestamp) {
		transactionDate = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setTransactionEndDate(Timestamp timestamp) {
		transactionEndDate = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setTransactionStartDate(Timestamp timestamp) {
		transactionStartDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setTransactionTypeId(long l) {
		transactionTypeId = l;
	}

	/**
	 * @param d
	 */
	public void setUnitCost(double d) {
		unitCost = d;
	}

	/**
	 * @param d
	 */
	public void setUnitNetCost(double d) {
		unitNetCost = d;
	}

	/**
	 * @param timestamp
	 */
	public void setValueDate(Timestamp timestamp) {
		valueDate = timestamp;
	}

}
