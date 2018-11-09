package com.iss.itreasury.settlement.craftbrother.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class CalcNoticeInfo extends ITreasuryBaseDataEntity implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2059104376482293711L;
	private long Id = -1;
	private String Code = "";
	private long contractID = -1;
	private String contractCode = "";
	private long counterpartId = -1;
	private String counterpartName = "";
	private long inorout = -1;
	private long discountType = -1;
	private Timestamp transDate = null;
	private Timestamp interestStartDate = null;
	private Timestamp interestEndDate = null;
	private Timestamp repurchaseDate = null;
	private double amount = 0.00;
	private double rate = 0.00;
	private double interest = 0.00;//本次计算利息
	private long days = 0;//计息天数
	private double summPredrawInterest = 0.00;//累计计提利息
	private long makeUserId = -1;//生成人
	private Timestamp makeDate = null;//生成日期
	private long craTransTypeId = -1;//同业交易类型
	private double totalInterest = 0.00;  //利息总额
	private double sumAmortizeInterest = 0.00;  //已摊销利息总额
	private double sumNotAmortizeInterest = 0.00; //未摊销利息总额
	private String draftMessage = "";

	public String getDraftMessage() {
		return draftMessage;
	}

	public void setDraftMessage(String draftMessage) {
		this.draftMessage = draftMessage;
	}

	public double getTotalInterest() {
		return totalInterest;
	}

	public void setTotalInterest(double totalInterest) {
		this.totalInterest = totalInterest;
	}

	public long getId() {
		return this.Id;
	}

	public void setId(long id) {
		this.Id = id;
	}

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public long getContractID() {
		return contractID;
	}

	public void setContractID(long contractID) {
		this.contractID = contractID;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public long getCounterpartId() {
		return counterpartId;
	}

	public void setCounterpartId(long counterpartId) {
		this.counterpartId = counterpartId;
	}

	public String getCounterpartName() {
		return counterpartName;
	}

	public void setCounterpartName(String counterpartName) {
		this.counterpartName = counterpartName;
	}
	
	public long getInorout() {
		return inorout;
	}

	public void setInorout(long inorout) {
		this.inorout = inorout;
	}

	public long getDiscountType() {
		return discountType;
	}

	public void setDiscountType(long discountType) {
		this.discountType = discountType;
	}

	public Timestamp getTransDate() {
		return transDate;
	}

	public void setTransDate(Timestamp transDate) {
		this.transDate = transDate;
	}
	
	public Timestamp getInterestStartDate() {
		return interestStartDate;
	}

	public void setInterestStartDate(Timestamp interestStartDate) {
		this.interestStartDate = interestStartDate;
	}

	public Timestamp getInterestEndDate() {
		return interestEndDate;
	}

	public void setInterestEndDate(Timestamp interestEndDate) {
		this.interestEndDate = interestEndDate;
	}
	
	public Timestamp getRepurchaseDate() {
		return repurchaseDate;
	}

	public void setRepurchaseDate(Timestamp repurchaseDate) {
		this.repurchaseDate = repurchaseDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getRate() {
		return rate;
	}

	public long getDays() {
		return days;
	}

	public void setDays(long days) {
		this.days = days;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public double getSummPredrawInterest() {
		return summPredrawInterest;
	}

	public void setSummPredrawInterest(double summPredrawInterest) {
		this.summPredrawInterest = summPredrawInterest;
	}

	public long getMakeUserId() {
		return makeUserId;
	}

	public void setMakeUserId(long makeUserId) {
		this.makeUserId = makeUserId;
	}

	public Timestamp getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(Timestamp makeDate) {
		this.makeDate = makeDate;
	}

	public long getCraTransTypeId() {
		return craTransTypeId;
	}

	public void setCraTransTypeId(long craTransTypeId) {
		this.craTransTypeId = craTransTypeId;
	}

	public double getSumAmortizeInterest() {
		return sumAmortizeInterest;
	}

	public void setSumAmortizeInterest(double sumAmortizeInterest) {
		this.sumAmortizeInterest = sumAmortizeInterest;
	}

	public double getSumNotAmortizeInterest() {
		return sumNotAmortizeInterest;
	}

	public void setSumNotAmortizeInterest(double sumNotAmortizeInterest) {
		this.sumNotAmortizeInterest = sumNotAmortizeInterest;
	}


	
}
