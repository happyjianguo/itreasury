package com.iss.itreasury.loan.query.dataentity;

import java.sql.Timestamp;

import com.iss.sysframe.base.dataentity.BaseDataEntity;

public class QueryDiscountInfo extends BaseDataEntity {
	
	private long tsDiscountTypeID = -1;				//贴现汇票种类
	private String minContractCode = null;    		//合同编号开始
	private String maxContractCode = null;			//合同编号结束
	private long minborrowClientID = -1;			//申请单位开始
	private long maxborrowClientID = -1;			//申请单位结束
	private long inputUserID = -1;					//合同管理人
	private String billDrawer = null;				//出票人名称
	private double minLoanAmount = 0.00;			//贴现金额开始
	private double maxLoanAmount = 0.00;			//贴现金额结束
	private double minCheckAmount = 0.00;			//实付金额开始
	private double maxCheckAmount = 0.00;			//实付金额终止
	private double minPayerRate = 0.00;				//买方付息开始
	private double maxPayerRate = 0.00;				//买方付息终止
	private double minRate = 0.00;					//利率起
	private double maxRate = 0.00;					//利率止
	private Timestamp minStartDate = null;			//贷款日期开始
	private Timestamp maxStartDate = null;			//贷款日期结束
	private Timestamp minEndDate = null;			//贷款结束日期开始
	private Timestamp maxEndDate = null;			//贷款结束日期截止
	private Timestamp minDiscountDate = null;		//贴现日期开始
	private Timestamp maxDiscountDate = null;		//贴现日期止
	private Timestamp minDisccountInputDate = null;	//贴现申请录入日期开始
	private Timestamp maxDisccountInputDate = null;	//贴现申请录入日期开始
	private String statusIDs = null;				//多个合同状态
	private String credenceStatusIDs = null;		//多个凭证状态
	private long officeID = -1;						//办事处
	private long currencyID = -1;					//币种
	private long queryPurpose = -1;					//查询目的
	private long contractID = -1; 					//查询贴现凭证时的合同ID
	
	
	//查询相关信息
	double dExamineAmount = 0.00;
	double dRealAmount = 0.00;
	double dAccrual = 0.00;
	double dDiscountRate = 0.00;
	double dTotalDiscountAccrual = 0.00;
	double dTotalPurchaserAmount = 0.00;
	double dPurchaserRate = 0.00;
	Timestamp tsDiscountDate = null;
	

	public long getTsDiscountTypeID() {
		return tsDiscountTypeID;
	}

	public void setTsDiscountTypeID(long tsDiscountTypeID) {
		this.tsDiscountTypeID = tsDiscountTypeID;
	}

	public String getMinContractCode() {
		return minContractCode;
	}

	public void setMinContractCode(String minContractCode) {
		this.minContractCode = minContractCode;
	}

	public String getMaxContractCode() {
		return maxContractCode;
	}

	public void setMaxContractCode(String maxContractCode) {
		this.maxContractCode = maxContractCode;
	}

	public long getMinborrowClientID() {
		return minborrowClientID;
	}

	public void setMinborrowClientID(long minborrowClientID) {
		this.minborrowClientID = minborrowClientID;
	}

	public long getMaxborrowClientID() {
		return maxborrowClientID;
	}

	public void setMaxborrowClientID(long maxborrowClientID) {
		this.maxborrowClientID = maxborrowClientID;
	}

	public long getInputUserID() {
		return inputUserID;
	}

	public void setInputUserID(long inputUserID) {
		this.inputUserID = inputUserID;
	}

	public String getBillDrawer() {
		return billDrawer;
	}

	public void setBillDrawer(String billDrawer) {
		this.billDrawer = billDrawer;
	}

	public double getMinLoanAmount() {
		return minLoanAmount;
	}

	public void setMinLoanAmount(double minLoanAmount) {
		this.minLoanAmount = minLoanAmount;
	}

	public double getMaxLoanAmount() {
		return maxLoanAmount;
	}

	public void setMaxLoanAmount(double maxLoanAmount) {
		this.maxLoanAmount = maxLoanAmount;
	}

	public double getMinCheckAmount() {
		return minCheckAmount;
	}

	public void setMinCheckAmount(double minCheckAmount) {
		this.minCheckAmount = minCheckAmount;
	}

	public double getMaxCheckAmount() {
		return maxCheckAmount;
	}

	public void setMaxCheckAmount(double maxCheckAmount) {
		this.maxCheckAmount = maxCheckAmount;
	}

	public double getMinPayerRate() {
		return minPayerRate;
	}

	public void setMinPayerRate(double minPayerRate) {
		this.minPayerRate = minPayerRate;
	}

	public double getMaxPayerRate() {
		return maxPayerRate;
	}

	public void setMaxPayerRate(double maxPayerRate) {
		this.maxPayerRate = maxPayerRate;
	}

	public double getMinRate() {
		return minRate;
	}

	public void setMinRate(double minRate) {
		this.minRate = minRate;
	}

	public double getMaxRate() {
		return maxRate;
	}

	public void setMaxRate(double maxRate) {
		this.maxRate = maxRate;
	}

	public Timestamp getMinStartDate() {
		return minStartDate;
	}

	public void setMinStartDate(Timestamp minStartDate) {
		this.minStartDate = minStartDate;
	}

	public Timestamp getMaxStartDate() {
		return maxStartDate;
	}

	public void setMaxStartDate(Timestamp maxStartDate) {
		this.maxStartDate = maxStartDate;
	}

	public Timestamp getMinEndDate() {
		return minEndDate;
	}

	public void setMinEndDate(Timestamp minEndDate) {
		this.minEndDate = minEndDate;
	}

	public Timestamp getMaxEndDate() {
		return maxEndDate;
	}

	public void setMaxEndDate(Timestamp maxEndDate) {
		this.maxEndDate = maxEndDate;
	}

	public Timestamp getMinDiscountDate() {
		return minDiscountDate;
	}

	public void setMinDiscountDate(Timestamp minDiscountDate) {
		this.minDiscountDate = minDiscountDate;
	}

	public Timestamp getMaxDiscountDate() {
		return maxDiscountDate;
	}

	public void setMaxDiscountDate(Timestamp maxDiscountDate) {
		this.maxDiscountDate = maxDiscountDate;
	}

	public Timestamp getMinDisccountInputDate() {
		return minDisccountInputDate;
	}

	public void setMinDisccountInputDate(Timestamp minDisccountInputDate) {
		this.minDisccountInputDate = minDisccountInputDate;
	}

	public Timestamp getMaxDisccountInputDate() {
		return maxDisccountInputDate;
	}

	public void setMaxDisccountInputDate(Timestamp maxDisccountInputDate) {
		this.maxDisccountInputDate = maxDisccountInputDate;
	}

	public String getStatusIDs() {
		return statusIDs;
	}

	public void setStatusIDs(String statusIDs) {
		this.statusIDs = statusIDs;
	}

	public long getOfficeID() {
		return officeID;
	}

	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}

	public long getCurrencyID() {
		return currencyID;
	}

	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}

	public long getQueryPurpose() {
		return queryPurpose;
	}

	public void setQueryPurpose(long queryPurpose) {
		this.queryPurpose = queryPurpose;
	}
	
	//查询相关信息
	public double getDExamineAmount() {
		return dExamineAmount;
	}

	public void setDExamineAmount(double examineAmount) {
		dExamineAmount = examineAmount;
	}

	public double getDRealAmount() {
		return dRealAmount;
	}

	public void setDRealAmount(double realAmount) {
		dRealAmount = realAmount;
	}

	public double getDAccrual() {
		return dAccrual;
	}

	public void setDAccrual(double accrual) {
		dAccrual = accrual;
	}

	public double getDDiscountRate() {
		return dDiscountRate;
	}

	public void setDDiscountRate(double discountRate) {
		dDiscountRate = discountRate;
	}

	public double getDTotalDiscountAccrual() {
		return dTotalDiscountAccrual;
	}

	public void setDTotalDiscountAccrual(double totalDiscountAccrual) {
		dTotalDiscountAccrual = totalDiscountAccrual;
	}

	public double getDTotalPurchaserAmount() {
		return dTotalPurchaserAmount;
	}

	public void setDTotalPurchaserAmount(double totalPurchaserAmount) {
		dTotalPurchaserAmount = totalPurchaserAmount;
	}

	public double getDPurchaserRate() {
		return dPurchaserRate;
	}

	public void setDPurchaserRate(double purchaserRate) {
		dPurchaserRate = purchaserRate;
	}

	public Timestamp getTsDiscountDate() {
		return tsDiscountDate;
	}

	public void setTsDiscountDate(Timestamp tsDiscountDate) {
		this.tsDiscountDate = tsDiscountDate;
	}
	
	public String getCredenceStatusIDs() {
		return credenceStatusIDs;
	}

	public void setCredenceStatusIDs(String credenceStatusIDs) {
		this.credenceStatusIDs = credenceStatusIDs;
	}

	public long getContractID() {
		return contractID;
	}

	public void setContractID(long contractID) {
		this.contractID = contractID;
	}

	
	@Override
	public long getId() {
		return 0;
	}

	@Override
	public void setId(long id) {
		
	}

}
