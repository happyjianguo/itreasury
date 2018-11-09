package com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dataentity;

import com.iss.itreasury.craftbrother.transferloancontract.counterparty.dataentity.CounterpartBankInfo;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;
import java.sql.Timestamp;

/**
 * 
 * ͬҵ����--�Ŵ��ʲ�ת�ú�ͬ��Ϣ
 * 
 * @author minzhao
 *
 */
public class TransferContractInfo extends ITreasuryBaseDataEntity {


	private long id = -1;							//����ID
	private long officeId = -1;						//���´�ID
	private long currencyId = -1;					//����ID
	private String contractCode = "";				//ת�ú�ͬ���
	private long applyId = -1;						//ת������ID
	private long transtypeId = -1;					//��������ID
	private long counterPartId = -1;				//���׶���ID
	private String counterPartName = "";			//���׶�������
	private Timestamp zstartDate = null;			//ת�����뿪ʼ����
	private Timestamp zendDate = null;				//ת�������������
	private double rate = 0.0;						//ת����������
	private long loanApplyId = -1;					//�Ŵ�ת������ID
	private long interestCountTypeId = -1;			//��Ϣ��ʽ
	private long chargeTypeId = -1;					//�����Ѽ��㷽ʽ
	private long commissionPaymentType = -1;	    //��������ȡ��ʽ
	private double chargeRate = 0.0;				//��������
	private Timestamp startDate = null;				//��ͬ��ʼ����
	private Timestamp endDate = null;				//��ͬ��������
	private double drate = 0.0;						//��ͬ����
	private String assureInfo = "";					//������Ϣ
	private String purpose = "";					//��;
	private String remark = "";						//��ע
	private long isurrogateinterest = -1;			//�Ƿ������Ϣ
	private long isurrogateamount = -1;			//�Ƿ���ձ���
	private long isNoticeBorrower = -1;				//�Ƿ�֪ͨ�����
	private long isRecourse = -1;					//�Ƿ���׷��Ȩ
	private long repurchaseAssureProtocol = -1;		//�ع�����Э��
	private long statusId = -1;						//��ͬ״̬
	private long inputUserId = -1;					//¼����
	private Timestamp inputDate = null;				//¼������
	private long attachId = -1;						//��������ID
	private Timestamp activeDate = null;			//��ͬ��������
	private double amount = 0.0;					//��ͬ���

	private long counterpartbankid=-1;				//���׶��ֿ�����id
	private long activeuser = -1;					//������
	
	private Timestamp preclearInterstDate = null;   //�ϴν�Ϣ��
	private Timestamp clearInterstDate = null;      //��Ϣ��
	
	
	private ContractdetailInfo[] contractdetailinfo=null;
	
	private InutParameterInfo inutparameterinfo=null;
	
	private CounterpartBankInfo counterpartbankinfo=null;
	
	private double sellamount=0.0;	//��ͬ���
	
	
	
	//��ѯ����
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
