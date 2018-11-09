package com.iss.itreasury.settlement.transferinterest.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
/**
 * @author 
 *
 * 利息费用结算页面查询结果信息实体。
 * 该类主要用来保存页面的查询结果，在页面上显示。
 * 该实体必须遵守JavaBean的规范，以便实现从JSP页面到实体的自动赋值的功能，
 * 简化应用程序的开发过程。
 * 该实体中的一些信息直接来自数据库，另外一些信息来自算息模块的计算。
 */
public class InterestQueryResultInfo implements Serializable {
	
	private long craContractID = -1;                      //转让合同ID
	private String craContractNo = "" ;                   //转让合同号
	private String craTransActionType = "";               //转让合同类型
	private long craTransActionID = -1;                   //转让合同类型ID
	private long craCounterpartID = -1;                   //交易对手ID
	private String craCounterPartName = "";               //交易对手名称
	private String loanContractNo = "";                   //贷款合同号
    private Timestamp StartDate = null;                   //开始日期
    private Timestamp EndDate = null;                     //结束日期
    private long Days = -1;                               //天数 
    private double Balance = 0.00;                        //余额
    private double Interest = 0.00;                       //利息
    private double DrawingInterest = 0.00;                //计提利息
    private String Creator = "";                          //生成人
    private Timestamp CreateDate = null;                  //生成日期
    private String ExchangeNo = "";                       //交易号
	private long PayInterestAccountID = -1;               //付息账户号
	private double InterestRate = 0.00;                   //利率
	private String loanNoteNo = "" ;                      //贷款通知单号
    private long loanNoteID = -1;                         //贷款通知单ID
    private Collection coll = null;
    private double commission = 0.00;                     //手续费
    private long borrowClientID = -1;                     //借款人ID
    private long craContractDetailID = -1;                //转让合同明细表ID
	public long getCraContractDetailID() {
		return craContractDetailID;
	}
	public void setCraContractDetailID(long craContractDetailID) {
		this.craContractDetailID = craContractDetailID;
	}
	public double getCommission() {
		return commission;
	}
	public void setCommission(double commission) {
		this.commission = commission;
	}
	public Collection getColl() {
		return coll;
	}
	public void setColl(Collection coll) {
		this.coll = coll;
	}
	public double getBalance() {
		return Balance;
	}
	public void setBalance(double balance) {
		Balance = balance;
	}
	public String getCraContractNo() {
		return craContractNo;
	}
	public void setCraContractNo(String craContractNo) {
		this.craContractNo = craContractNo;
	}
	public String getCraCounterPartName() {
		return craCounterPartName;
	}
	public void setCraCounterPartName(String craCounterPartName) {
		this.craCounterPartName = craCounterPartName;
	}
	public long getCraTransActionID() {
		return craTransActionID;
	}
	public void setCraTransActionID(long craTransActionID) {
		this.craTransActionID = craTransActionID;
	}
	public Timestamp getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(Timestamp createDate) {
		CreateDate = createDate;
	}
	public String getCreator() {
		return Creator;
	}
	public void setCreator(String creator) {
		Creator = creator;
	}
	public long getDays() {
		return Days;
	}
	public void setDays(long days) {
		Days = days;
	}
	public double getDrawingInterest() {
		return DrawingInterest;
	}
	public void setDrawingInterest(double drawingInterest) {
		DrawingInterest = drawingInterest;
	}
	public Timestamp getEndDate() {
		return EndDate;
	}
	public void setEndDate(Timestamp endDate) {
		EndDate = endDate;
	}
	public String getExchangeNo() {
		return ExchangeNo;
	}
	public void setExchangeNo(String exchangeNo) {
		ExchangeNo = exchangeNo;
	}
	public double getInterest() {
		return Interest;
	}
	public void setInterest(double interest) {
		Interest = interest;
	}
	public double getInterestRate() {
		return InterestRate;
	}
	public void setInterestRate(double interestRate) {
		InterestRate = interestRate;
	}
	public String getLoanContractNo() {
		return loanContractNo;
	}
	public void setLoanContractNo(String loanContractNo) {
		this.loanContractNo = loanContractNo;
	}
	public long getPayInterestAccountID() {
		return PayInterestAccountID;
	}
	public void setPayInterestAccountID(long payInterestAccountID) {
		PayInterestAccountID = payInterestAccountID;
	}
	public Timestamp getStartDate() {
		return StartDate;
	}
	public void setStartDate(Timestamp startDate) {
		StartDate = startDate;
	}
	public long getCraContractID() {
		return craContractID;
	}
	public void setCraContractID(long craContractID) {
		this.craContractID = craContractID;
	}
	public long getCraCounterpartID() {
		return craCounterpartID;
	}
	public void setCraCounterpartID(long craCounterpartID) {
		this.craCounterpartID = craCounterpartID;
	}
	public String getCraTransActionType() {
		return craTransActionType;
	}
	public void setCraTransActionType(String craTransActionType) {
		this.craTransActionType = craTransActionType;
	}
	public long getLoanNoteID() {
		return loanNoteID;
	}
	public void setLoanNoteID(long loanNoteID) {
		this.loanNoteID = loanNoteID;
	}
	public String getLoanNoteNo() {
		return loanNoteNo;
	}
	public void setLoanNoteNo(String loanNoteNo) {
		this.loanNoteNo = loanNoteNo;
	}
	public long getBorrowClientID() {
		return borrowClientID;
	}
	public void setBorrowClientID(long borrowClientID) {
		this.borrowClientID = borrowClientID;
	}
    

}
