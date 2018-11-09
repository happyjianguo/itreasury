package com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dataentity;

import com.iss.itreasury.craftbrother.transferloancontract.counterparty.dataentity.CounterpartBankInfo;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;
import java.sql.Timestamp;

/**
 * 
 * 同业往来--信贷资产转让合同信息
 * 
 * @author minzhao
 *
 */
public class TransferContractInfo extends ITreasuryBaseDataEntity {


	private long id = -1;							//主键ID
	private long officeId = -1;						//办事处ID
	private long currencyId = -1;					//币种ID
	private String contractCode = "";				//转让合同编号
	private long applyId = -1;						//转让申请ID
	private long transtypeId = -1;					//交易类型ID
	private long counterPartId = -1;				//交易对手ID
	private String counterPartName = "";			//交易对手名称
	private Timestamp zstartDate = null;			//转让申请开始日期
	private Timestamp zendDate = null;				//转让申请结束日期
	private double rate = 0.0;						//转让申请利率
	private long loanApplyId = -1;					//信贷转让申请ID
	private long interestCountTypeId = -1;			//结息方式
	private long chargeTypeId = -1;					//手续费计算方式
	private long commissionPaymentType = -1;	    //手续费收取方式
	private double chargeRate = 0.0;				//手续费率
	private Timestamp startDate = null;				//合同开始日期
	private Timestamp endDate = null;				//合同结束日期
	private double drate = 0.0;						//合同利率
	private String assureInfo = "";					//担保信息
	private String purpose = "";					//用途
	private String remark = "";						//备注
	private long isurrogateinterest = -1;			//是否代理收息
	private long isurrogateamount = -1;			//是否代收本金
	private long isNoticeBorrower = -1;				//是否通知借款人
	private long isRecourse = -1;					//是否有追索权
	private long repurchaseAssureProtocol = -1;		//回购担保协议
	private long statusId = -1;						//合同状态
	private long inputUserId = -1;					//录入人
	private Timestamp inputDate = null;				//录入日期
	private long attachId = -1;						//附件关联ID
	private Timestamp activeDate = null;			//合同激活日期
	private double amount = 0.0;					//合同金额

	private long counterpartbankid=-1;				//交易对手开户行id
	private long activeuser = -1;					//激活人
	
	private Timestamp preclearInterstDate = null;   //上次结息日
	private Timestamp clearInterstDate = null;      //结息日
	
	
	private ContractdetailInfo[] contractdetailinfo=null;
	
	private InutParameterInfo inutparameterinfo=null;
	
	private CounterpartBankInfo counterpartbankinfo=null;
	
	private double sellamount=0.0;	//合同余额
	
	
	
	//查询属性
	private String contractCodeFrom = "";
	private long contractIdFrom = -1;
	private String contractCodeTo = "";
	private long contractIdTo = -1;
	private double applyAmount = 0.0;
	private double applyAmountFrom = 0.0;
	private double applyAmountTo = 0.0;
	private String inputDateFrom = "";
	private String inputDateTo = "";
	private String counterPartCode = "";

		
	
	public double getApplyAmountFrom() {
		return applyAmountFrom;
	}

	public void setApplyAmountFrom(double applyAmountFrom) {
		this.applyAmountFrom = applyAmountFrom;
	}

	public double getApplyAmountTo() {
		return applyAmountTo;
	}

	public void setApplyAmountTo(double applyAmountTo) {
		this.applyAmountTo = applyAmountTo;
	}

	public String getContractCodeFrom() {
		return contractCodeFrom;
	}

	public void setContractCodeFrom(String contractCodeFrom) {
		this.contractCodeFrom = contractCodeFrom;
	}

	public String getContractCodeTo() {
		return contractCodeTo;
	}

	public void setContractCodeTo(String contractCodeTo) {
		this.contractCodeTo = contractCodeTo;
	}

	public String getInputDateFrom() {
		return inputDateFrom;
	}

	public void setInputDateFrom(String inputDateFrom) {
		this.inputDateFrom = inputDateFrom;
	}

	public String getInputDateTo() {
		return inputDateTo;
	}

	public void setInputDateTo(String inputDateTo) {
		this.inputDateTo = inputDateTo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}

	public Timestamp getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(Timestamp activeDate) {
		this.activeDate = activeDate;
		putUsedField("dtactive", activeDate);
	}

	public long getApplyId() {
		return applyId;
	}

	public void setApplyId(long applyId) {
		this.applyId = applyId;
		putUsedField("applyId", applyId);
	}

	public String getAssureInfo() {
		return assureInfo;
	}

	public void setAssureInfo(String assureInfo) {
		this.assureInfo = assureInfo;
		putUsedField("assureInfo", assureInfo);
	}

	public long getAttachId() {
		return attachId;
	}

	public void setAttachId(long attachId) {
		this.attachId = attachId;
		putUsedField("attachId", attachId);
	}

	public double getChargeRate() {
		return chargeRate;
	}

	public void setChargeRate(double chargeRate) {
		this.chargeRate = chargeRate;
		putUsedField("chargeRate", chargeRate);
	}

	public long getChargeTypeId() {
		return chargeTypeId;
	}

	public void setChargeTypeId(long chargeTypeId) {
		this.chargeTypeId = chargeTypeId;
		putUsedField("chargeTypeId", chargeTypeId);
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
		putUsedField("contractCode", contractCode);
	}

	public long getCounterPartId() {
		return counterPartId;
	}

	public void setCounterPartId(long counterPartId) {
		this.counterPartId = counterPartId;
		putUsedField("counterPartId", counterPartId);
	}

	public String getCounterPartName() {
		return counterPartName;
	}

	public void setCounterPartName(String counterPartName) {
		this.counterPartName = counterPartName;
		//putUsedField("counterPartName", counterPartName);
	}

	public long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
		putUsedField("currencyId", currencyId);
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
		putUsedField("endDate", endDate);
	}

	public Timestamp getInputDate() {
		return inputDate;
	}

	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
		putUsedField("inputDate", inputDate);
	}

	public long getInputUserId() {
		return inputUserId;
	}

	public void setInputUserId(long inputUserId) {
		this.inputUserId = inputUserId;
		putUsedField("inputUserId", inputUserId);
	}

	public long getInterestCountTypeId() {
		return interestCountTypeId;
	}

	public void setInterestCountTypeId(long interestCountTypeId) {
		this.interestCountTypeId = interestCountTypeId;
		putUsedField("interestCountTypeId", interestCountTypeId);
	}

	public long getIsNoticeBorrower() {
		return isNoticeBorrower;
	}

	public void setIsNoticeBorrower(long isNoticeBorrower) {
		this.isNoticeBorrower = isNoticeBorrower;
		putUsedField("isNoticeBorrower", isNoticeBorrower);
	}

	public long getIsRecourse() {
		return isRecourse;
	}

	public void setIsRecourse(long isRecourse) {
		this.isRecourse = isRecourse;
		putUsedField("isRecourse", isRecourse);
	}

	public long getIsurrogateamount() {
		return isurrogateamount;
	}

	public void setIsurrogateamount(long isurrogateamount) {
		this.isurrogateamount = isurrogateamount;
		putUsedField("isurrogateamount", isurrogateamount);
	}

	public long getIsurrogateinterest() {
		return isurrogateinterest;
	}

	public void setIsurrogateinterest(long isurrogateinterest) {
		this.isurrogateinterest = isurrogateinterest;
		putUsedField("isurrogateinterest", isurrogateinterest);
	}

	public long getLoanApplyId() {
		return loanApplyId;
	}

	public void setLoanApplyId(long loanApplyId) {
		this.loanApplyId = loanApplyId;
		putUsedField("loanApplyId", loanApplyId);
	}

	public long getOfficeId() {
		return officeId;
	}

	public void setOfficeId(long officeId) {
		this.officeId = officeId;
		putUsedField("officeId", officeId);
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
		putUsedField("purpose", purpose);
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
		putUsedField("srate", rate);
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
		putUsedField("remark", remark);
	}

	public long getRepurchaseAssureProtocol() {
		return repurchaseAssureProtocol;
	}

	public void setRepurchaseAssureProtocol(long repurchaseAssureProtocol) {
		this.repurchaseAssureProtocol = repurchaseAssureProtocol;
		putUsedField("repurchaseAssureProtocol", repurchaseAssureProtocol);
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
		putUsedField("startDate", startDate);
	}

	public long getStatusId() {
		return statusId;
	}

	public void setStatusId(long statusId) {
		this.statusId = statusId;
		putUsedField("statusId", statusId);
	}

	public long getTranstypeId() {
		return transtypeId;
	}

	public void setTranstypeId(long transtypeId) {
		this.transtypeId = transtypeId;
		putUsedField("transtypeId", transtypeId);
	}

	public long getContractIdFrom() {
		return contractIdFrom;
	}

	public void setContractIdFrom(long contractIdFrom) {
		this.contractIdFrom = contractIdFrom;
	}

	public long getContractIdTo() {
		return contractIdTo;
	}

	public void setContractIdTo(long contractIdTo) {
		this.contractIdTo = contractIdTo;
	}

	public String getCounterPartCode() {
		return counterPartCode;
	}

	public void setCounterPartCode(String counterPartCode) {
		this.counterPartCode = counterPartCode;
	}

	public double getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(double applyAmount) {
		this.applyAmount = applyAmount;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
		putUsedField("amount", amount);
	}

	public double getDrate() {
		return drate;
	}

	public void setDrate(double drate) {
		this.drate = drate;
		putUsedField("drate", drate);
	}

	public Timestamp getZendDate() {
		return zendDate;
	}

	public void setZendDate(Timestamp zendDate) {
		this.zendDate = zendDate;
		putUsedField("zendDate", zendDate);
	}

	public Timestamp getZstartDate() {
		return zstartDate;
	}

	public void setZstartDate(Timestamp zstartDate) {
		this.zstartDate = zstartDate;
		putUsedField("zstartDate", zstartDate);
	}


	public ContractdetailInfo[] getContractdetailinfo() {
		return contractdetailinfo;
	}

	public void setContractdetailinfo(ContractdetailInfo[] contractdetailinfo) {
		this.contractdetailinfo = contractdetailinfo;
	}

	public InutParameterInfo getInutparameterinfo() {
		return inutparameterinfo;
	}

	public void setInutparameterinfo(InutParameterInfo inutparameterinfo) {
		this.inutparameterinfo = inutparameterinfo;
	}



	public long getCommissionPaymentType() {
		return commissionPaymentType;
	}

	public void setCommissionPaymentType(long commissionPaymentType) {
		this.commissionPaymentType = commissionPaymentType;
		putUsedField("commissionPaymentType", commissionPaymentType);
	}

	public long getActiveuser() {
		return activeuser;
	}

	public void setActiveuser(long activeuser) {
		this.activeuser = activeuser;
	}


	public long getCounterpartbankid() {
		return counterpartbankid;
	}

	public void setCounterpartbankid(long counterpartbankid) {
		this.counterpartbankid = counterpartbankid;
		putUsedField("counterpartbankid", counterpartbankid);
	}

	public CounterpartBankInfo getCounterpartbankinfo() {
		return counterpartbankinfo;
	}

	public void setCounterpartbankinfo(CounterpartBankInfo counterpartbankinfo) {
		this.counterpartbankinfo = counterpartbankinfo;
	}

	public Timestamp getPreclearInterstDate() {
		return preclearInterstDate;
	}

	public void setPreclearInterstDate(Timestamp preclearInterstDate) {
		this.preclearInterstDate = preclearInterstDate;
	}

	public Timestamp getClearInterstDate() {
		return clearInterstDate;
	}

	public void setClearInterstDate(Timestamp clearInterstDate) {
		this.clearInterstDate = clearInterstDate;
	}

	public double getSellamount() {
		return sellamount;
	}

	public void setSellamount(double sellamount) {
		this.sellamount = sellamount;
	}

	
	
}
