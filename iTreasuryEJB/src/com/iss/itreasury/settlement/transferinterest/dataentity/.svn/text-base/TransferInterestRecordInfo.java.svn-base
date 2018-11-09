package com.iss.itreasury.settlement.transferinterest.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class TransferInterestRecordInfo extends ITreasuryBaseDataEntity implements Serializable{


	private long id	= -1;							//主键id
	private long officeid = -1;						//办事处id
	private long currencyid	= -1;					//币种id
	private String stransno	= "";					//交易号
	private Timestamp dtstart = null;				//起息日
	private Timestamp dtend	= null;					//终息日
	private long days = -1;							//天数
	private double amount = 0.0;					//金额
	private double minterest = 0.0;					//利息
	private double drate = 0.0;						//执行利率
	private Timestamp dtexecute = null;				//执行日
	private String sabstract = "";					//摘要
	private Timestamp dtinterestsettlement = null;	//结息日期
	private long cracontractid = -1;				//转让合同id
	private long ninteresttype = -1;				//利息类型
	private long statusid = -1;						//状态
	private long inputuserid = -1;					//录入人
	private Timestamp inputdate = null;				//录入日期
	
	private String cracontractcode = "";			//转让合同号
	private String inputusername = "";				//录入人名称
	private String strinterestsettlement = "";		//查询条件 结息日
	
	private long IsSave = -1;                       //是否保存/是否结息 
	private long IsKeepAccount = -1;                //是否记账          
	private long IsSuccess = -1;                    //执行是否成功      
	private String FaultReason = "";                //失败原因
	private long InterestType = -1;                 //利息类型          
	private long OperationType = -1;                //操作类型  
	private long transActionTypeID = -1;            //交易类型 
	private double preDrawInterest = 0.0;           //计提利息
	private double commission = 0.0;                //手续费
    private long craContractDetailID = -1;          //转让合同明细表ID
    
    private long payInterestAccountID = -1;         //付息账户号ID
	
	public long getPayInterestAccountID() {
		return payInterestAccountID;
	}

	public void setPayInterestAccountID(long payInterestAccountID) {
		this.payInterestAccountID = payInterestAccountID;
	}

	public double getCommission() {
		return commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	public String getStrinterestsettlement() {
		return strinterestsettlement;
	}

	public void setStrinterestsettlement(String strinterestsettlement) {
		this.strinterestsettlement = strinterestsettlement;
	}

	public String getInputusername() {
		return inputusername;
	}

	public void setInputusername(String inputusername) {
		this.inputusername = inputusername;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
		putUsedField("amount", amount);
	}

	public String getCracontractcode() {
		return cracontractcode;
	}

	public void setCracontractcode(String cracontractcode) {
		this.cracontractcode = cracontractcode;
	}

	public long getCracontractid() {
		return cracontractid;
	}

	public void setCracontractid(long cracontractid) {
		this.cracontractid = cracontractid;
		putUsedField("cracontractid", cracontractid);
	}

	public long getCurrencyid() {
		return currencyid;
	}

	public void setCurrencyid(long currencyid) {
		this.currencyid = currencyid;
		putUsedField("currencyid", currencyid);
	}

	public long getDays() {
		return days;
	}

	public void setDays(long days) {
		this.days = days;
		putUsedField("days", days);
	}

	public double getDrate() {
		return drate;
	}

	public void setDrate(double drate) {
		this.drate = drate;
		putUsedField("drate", drate);
	}

	public Timestamp getDtend() {
		return dtend;
	}

	public void setDtend(Timestamp dtend) {
		this.dtend = dtend;
		putUsedField("dtend", dtend);
	}

	public Timestamp getDtexecute() {
		return dtexecute;
	}

	public void setDtexecute(Timestamp dtexecute) {
		this.dtexecute = dtexecute;
		putUsedField("dtexecute", dtexecute);
	}

	public Timestamp getDtinterestsettlement() {
		return dtinterestsettlement;
	}

	public void setDtinterestsettlement(Timestamp dtinterestsettlement) {
		this.dtinterestsettlement = dtinterestsettlement;
		putUsedField("dtinterestsettlement", dtinterestsettlement);
	}

	public Timestamp getDtstart() {
		return dtstart;
	}

	public void setDtstart(Timestamp dtstart) {
		this.dtstart = dtstart;
		putUsedField("dtstart", dtstart);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}

	public Timestamp getInputdate() {
		return inputdate;
	}

	public void setInputdate(Timestamp inputdate) {
		this.inputdate = inputdate;
		putUsedField("inputdate", inputdate);
	}

	public long getInputuserid() {
		return inputuserid;
	}

	public void setInputuserid(long inputuserid) {
		this.inputuserid = inputuserid;
		putUsedField("inputuserid", inputuserid);
	}

	public double getMinterest() {
		return minterest;
	}

	public void setMinterest(double minterest) {
		this.minterest = minterest;
		putUsedField("minterest", minterest);
	}

	public long getOfficeid() {
		return officeid;
	}

	public void setOfficeid(long officeid) {
		this.officeid = officeid;
		putUsedField("officeid", officeid);
	}

	public String getSabstract() {
		return sabstract;
	}

	public void setSabstract(String sabstract) {
		this.sabstract = sabstract;
		putUsedField("sabstract", sabstract);
	}

	public long getStatusid() {
		return statusid;
	}

	public void setStatusid(long statusid) {
		this.statusid = statusid;
		putUsedField("statusid", statusid);
	}

	public String getStransno() {
		return stransno;
	}

	public void setStransno(String stransno) {
		this.stransno = stransno;
		putUsedField("stransno", stransno);
	}

	public long getNinteresttype() {
		return ninteresttype;
	}

	public void setNinteresttype(long ninteresttype) {
		this.ninteresttype = ninteresttype;
	}

	public String getFaultReason() {
		return FaultReason;
	}

	public void setFaultReason(String faultReason) {
		FaultReason = faultReason;
	}

	public long getIsKeepAccount() {
		return IsKeepAccount;
	}

	public void setIsKeepAccount(long isKeepAccount) {
		IsKeepAccount = isKeepAccount;
	}

	public long getIsSave() {
		return IsSave;
	}

	public void setIsSave(long isSave) {
		IsSave = isSave;
	}

	public long getIsSuccess() {
		return IsSuccess;
	}

	public void setIsSuccess(long isSuccess) {
		IsSuccess = isSuccess;
	}

	public long getInterestType() {
		return InterestType;
	}

	public void setInterestType(long interestType) {
		InterestType = interestType;
	}

	public long getOperationType() {
		return OperationType;
	}

	public void setOperationType(long operationType) {
		OperationType = operationType;
	}

	public long getTransActionTypeID() {
		return transActionTypeID;
	}

	public void setTransActionTypeID(long transActionTypeID) {
		this.transActionTypeID = transActionTypeID;
	}

	public double getPreDrawInterest() {
		return preDrawInterest;
	}

	public void setPreDrawInterest(double preDrawInterest) {
		this.preDrawInterest = preDrawInterest;
	}

	public long getCraContractDetailID() {
		return craContractDetailID;
	}

	public void setCraContractDetailID(long craContractDetailID) {
		this.craContractDetailID = craContractDetailID;
	}
	
	
}
