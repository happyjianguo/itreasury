package com.iss.itreasury.settlement.mergedVoucher.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class MergedVoucherInfo  extends ITreasuryBaseDataEntity{
	private long id = -1;
	private long nOfficeId = -1;//办事处id
	private long nCurrencyId = -1;//币种id
	private String sSubjectCode = "";//科目代码
	private String sBatchNo = "";//合并批次号
	private long nTransactionTypeId = -1;//交易类型
	private long nTransDirection = -1;//交易方向
	private double mAmount = 0.0;//交易发生额
	private Timestamp dtExecute = null;//执行日
	private Timestamp tsExecuteStartDate = null;//执行开始日
	private Timestamp tsExecuteEndDate = null;//执行结束日
	private Timestamp dtIntereststStart = null;//起息日
	private String sAbstract = "";//摘要
	private long nPostStatusId = -1;//导账状态
	private long nStatusId = -1;//凭证状态
	//查询需要
	private String sBatchNoStart = "";//合并批次号开始
	private String sBatchNoEnd = "";//合并批次号结束
	
	public Timestamp getDtExecute() {
		return dtExecute;
	}
	public void setDtExecute(Timestamp dtExecute) {
		this.dtExecute = dtExecute;
		putUsedField("dtExecute", dtExecute);
	}
	public Timestamp getDtIntereststStart() {
		return dtIntereststStart;
	}
	public void setDtIntereststStart(Timestamp dtIntereststStart) {
		this.dtIntereststStart = dtIntereststStart;
		putUsedField("dtIntereststStart", dtIntereststStart);
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}
	public String getSAbstract() {
		return sAbstract;
	}
	public void setSAbstract(String abstract1) {
		sAbstract = abstract1;
		putUsedField("sAbstract", sAbstract);
	}
	public String getSBatchNo() {
		return sBatchNo;
	}
	public void setSBatchNo(String batchNo) {
		sBatchNo = batchNo;
		putUsedField("sBatchNo", sBatchNo);
	}
	public String getSSubjectCode() {
		return sSubjectCode;
	}
	public void setSSubjectCode(String subjectCode) {
		sSubjectCode = subjectCode;
		putUsedField("sSubjectCode", sSubjectCode);
	}
	public String getSBatchNoEnd() {
		return sBatchNoEnd;
	}
	public void setSBatchNoEnd(String batchNoEnd) {
		sBatchNoEnd = batchNoEnd;
	}
	public String getSBatchNoStart() {
		return sBatchNoStart;
	}
	public void setSBatchNoStart(String batchNoStart) {
		sBatchNoStart = batchNoStart;
	}
	public Timestamp getTsExecuteEndDate() {
		return tsExecuteEndDate;
	}
	public void setTsExecuteEndDate(Timestamp tsExecuteEndDate) {
		this.tsExecuteEndDate = tsExecuteEndDate;
	}
	public Timestamp getTsExecuteStartDate() {
		return tsExecuteStartDate;
	}
	public void setTsExecuteStartDate(Timestamp tsExecuteStartDate) {
		this.tsExecuteStartDate = tsExecuteStartDate;
	}
	public double getMAmount() {
		return mAmount;
	}
	public void setMAmount(double amount) {
		mAmount = amount;
	}
	public long getNCurrencyId() {
		return nCurrencyId;
	}
	public void setNCurrencyId(long currencyId) {
		nCurrencyId = currencyId;
	}
	public long getNOfficeId() {
		return nOfficeId;
	}
	public void setNOfficeId(long officeId) {
		nOfficeId = officeId;
	}
	public long getNPostStatusId() {
		return nPostStatusId;
	}
	public void setNPostStatusId(long postStatusId) {
		nPostStatusId = postStatusId;
	}
	public long getNTransactionTypeId() {
		return nTransactionTypeId;
	}
	public void setNTransactionTypeId(long transactionTypeId) {
		nTransactionTypeId = transactionTypeId;
	}
	public long getNTransDirection() {
		return nTransDirection;
	}
	public void setNTransDirection(long transDirection) {
		nTransDirection = transDirection;
	}
	public long getNStatusId() {
		return nStatusId;
	}
	public void setNStatusId(long statusId) {
		nStatusId = statusId;
	}
	
}
