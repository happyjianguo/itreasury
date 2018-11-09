package com.iss.itreasury.securities.apply.dataentity;
import com.iss.itreasury.craftbrother.apply.dataentity.CraLoanContentInfo;
import com.iss.itreasury.securities.util.SECBaseDataEntity;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;

import java.sql.Timestamp;
import java.util.Collection;
public   class    ApplyInfo    extends  SECBaseDataEntity
{
	//SEC_ApplyForm�����е��ֶ�
	private long id = -1;
	private long officeId = -1; //���´�
	private long currencyId = -1; //����
	private String code = ""; //��������
	private long typeId = -1;  //ҵ������
	private long transactionTypeId = -1; //��������
	private long actionTypeId = -1;  //����������
	private long moduleId = -1;  //ģ��id
	private Timestamp transactionStartDate = null; //�ɽ����ڿ�ʼ��
	private Timestamp transactionEndDate = null; //�ɽ����ڽ�ֹ��
	private long clientId = -1; //ҵ��λ
	private long counterpartId = -1; //���׶���[ծȯ������/�������˾]
	private long accountId = -1; //�����ʺ�[�ʽ��ʺ�]
	private long securitiesId = -1; //֤ȯ����ID[��Ѻծȯ����ID/��תծ����ID]
	private double amount = 0; //ȫ��[�Ϲ����/�깺���]
	private double pledgeSecuritiesAmount = 0; //�����[ȯ���ܶ�]����λ��Ԫ��
	private double price = 0; //�ɽ��۸�[ת�ɼ۸�]
	private double margin = 0;//�ʲ�ת����������еĲ��
	private double quantity = 0; //�깺����[�ɽ�����/��طݶ�/ת�ɵ�ծȯ����]
	private double convertRate = 0; //�������[Ԥ���������]
	private double stockQuantity = 0; //ת�ɹ�Ʊ����
	private double commissionCharge = 0; //Ԥ�������ѷ���
	private long liquidateRate = -1; //�����ٶ�
	private long bidTypeId = -1; //Ͷ�귽ʽ
	private long term = -1; //�������[�ع�����]
	private long termTypeId = -1; //��������
	private double rate = 0; //�������[�ع�����]
	private long stockId = -1; //ת�ɹ�Ʊ����ID
	private String remark = ""; //��ע
	private long nextCheckUserId = -1; //��һ�������
	private long nextCheckLevel = -1; //��һ����˼���
	private long inputUserId = -1; //¼����
	private Timestamp inputDate = null; //¼��ʱ��
	private long updateUserId = -1; //�޸���
	private Timestamp updateDate = null; //�޸�ʱ��
	private long statusId = -1; //״̬
	private Timestamp timestamp = null; //ʵ����
	private double commissionChargeRate = 0; //��������
	private long settlementTypeID = -1; //��Ϣ��ʽID
	private long interestTypeID = -1; //��Ϣ��ʽID
	private String maturitySource = ""; //�����ʽ���Դ
	private double startRate = 0; //���ü�Ϣ��ʽ���ڳ�
	private double changeRate = 0; //���ü�Ϣ��ʽ���䶯��
	private long isLowLevel = 2; //�Ƿ���������������1-�ǣ�2-��
	//SEC_ApplyForm����û�е��ֶ�
	private String clientName = ""; //ҵ��λ����
	private String clientCode = ""; //ҵ��λ���
	private String counterpartName = ""; //���׶�������
	private String counterpartCode = ""; //���׶��ֱ��
	private String securitiesName = ""; //֤ȯ����
	private String securitiesCode = ""; //֤ȯ���
	private String accountNo = ""; //�ʽ��˻�����
	private String accountName = ""; //�ʽ��˻�����
	private String stockHolderAccountCode = ""; //�ɶ��˻�����
	private String stockName = ""; //ת�ɹ�Ʊ����
	private String stockCode = ""; //ת�ɹ�Ʊ���
	private long approvalID = -1; //��������ID
	//���׶������Ŷ������ֶ�
	private Timestamp creditStartDate = null; //���ſ�ʼ����
	private Timestamp creditEndDate = null; //���Ž�ֹ����
	private double creditAmount = 0; //���Ŷ��
	private long creditTerm = -1; //���������
	private double sumCreditAmount1 = 0; //���׶�����ʹ���ܶ��
	private double sumCreditAmount2 = 0; //���н��׶�����ʹ���ܶ��
	private long recordCount = 0; //��¼��
	private long pageCount = 0; //ҳ��
	private long ownerTypeId = 0; //��������
	private Timestamp maturityDate = null; //������
	private String bondName = "";
	private double bondScale = 0;
	private String capitalLandingCreditExtensionMessage = ""; //����������ʾ��Ϣ
	private long isSameCheckLevel = 1; //����������ˣ���ʾ������Ƿ���ͬ����˼��� ��1-�ǣ�2-��
	//���
	private double incomeRate = 0;  //�ع�������
	private double costAmount = 0;  //�ع��ɱ�
	private String bargainCode=""; // ��ͬ���
	
	private long ispurcharse=-1;	// �Ƿ����
	//Added by zwsun, 2007/09/06 ����������
	private InutParameterInfo inutParameterInfo=null; 
	private long attachId = -1;		//��������ID
	private long creditId = -1;    //���Ź���ID
	
	private Collection craColl = null; //�����ͬ��Ϣ
	
	public Collection getCraColl() {
		return craColl;
	}
	public void setCraColl(Collection craColl) {
		this.craColl = craColl;
	}
	public long getCreditId() {
		return creditId;
	}
	public void setCreditId(long creditId) {
		this.creditId = creditId;
		putUsedField("creditId",creditId);
	}
	public long getAttachId() {
		return attachId;
	}
	public void setAttachId(long attachId) {
		this.attachId = attachId;
		putUsedField("attachId", attachId);
	}	
	public long getIspurcharse() {
		return ispurcharse;
	}

	public void setIspurcharse(long ispurcharse) {
		this.ispurcharse = ispurcharse;
		putUsedField("ispurcharse", ispurcharse);
	}

	public String getBargainCode(){
		return bargainCode;
	}
	
	public void setBargainCode(String bargainCode){
		this.bargainCode=bargainCode;
		putUsedField("bargainCode", bargainCode);
	}
	/**
	 * @return
	 */
	public long getAccountId()
	{
		return accountId;
	}
	/**
	 * @return
	 */
	public double getAmount()
	{
		return amount;
	}
	/**
	 * @return
	 */
	public double getQuantity()
	{
		return quantity;
	}
	/**
	 * @return
	 */
	public long getBidTypeId()
	{
		return bidTypeId;
	}
	/**
	 * @return
	 */
	public long getClientId()
	{
		return clientId;
	}
	/**
	 * @return
	 */
	public String getCode()
	{
		return code;
	}
	/**
	 * @return
	 */
	public double getCommissionCharge()
	{
		return commissionCharge;
	}
	/**
	 * @return
	 */
	public double getConvertRate()
	{
		return convertRate;
	}
	/**
	 * @return
	 */
	public long getCounterpartId()
	{
		return counterpartId;
	}
	/**
	 * @return
	 */
	public long getId()
	{
		return id;
	}
	/**
	 * @return
	 */
	public Timestamp getInputDate()
	{
		return inputDate;
	}
	/**
	 * @return
	 */
	public long getInputUserId()
	{
		return inputUserId;
	}
	/**
	 * @return
	 */
	public long getLiquidateRate()
	{
		return liquidateRate;
	}
	/**
	 * @return
	 */
	public long getNextCheckUserId()
	{
		return nextCheckUserId;
	}
	/**
	 * @return
	 */
	public double getPrice()
	{
		return price;
	}
	/**
	 * @return
	 */
	public double getMargin()
	{
		return margin;
	}
	/**
	 * @return
	 */
	public double getRate()
	{
		return rate;
	}
	/**
	 * @return
	 */
	public String getRemark()
	{
		return remark;
	}
	/**
	 * @return
	 */
	public double getPledgeSecuritiesAmount()
	{
		return pledgeSecuritiesAmount;
	}
	/**
	 * @return
	 */
	public long getSecuritiesId()
	{
		return securitiesId;
	}
	/**
	 * @return
	 */
	public long getStatusId()
	{
		return statusId;
	}
	/**
	 * @return
	 */
	public long getStockId()
	{
		return stockId;
	}
	/**
	 * @return
	 */
	public double getStockQuantity()
	{
		return stockQuantity;
	}
	/**
	 * @return
	 */
	public long getTerm()
	{
		return term;
	}
	/**
	 * @return
	 */
	public long getTermTypeId()
	{
		return termTypeId;
	}
	/**
	 * @return
	 */
	public Timestamp getTransactionEndDate()
	{
		return transactionEndDate;
	}
	/**
	 * @return
	 */
	public Timestamp getTransactionStartDate()
	{
		return transactionStartDate;
	}
	/**
	 * @return
	 */
	public long getTransactionTypeId()
	{
		return transactionTypeId;
	}
	/**
	 * @return
	 */
	public Timestamp getUpdateDate()
	{
		return updateDate;
	}
	/**
	 * @return
	 */
	public long getUpdateUserId()
	{
		return updateUserId;
	}
	/**
	 * @param l
	 */
	public void setAccountId(long l)
	{
		accountId = l;
		putUsedField("accountId", accountId);
	}
	/**
	 * @param d
	 */
	public void setAmount(double d)
	{
		amount = d;
		putUsedField("amount", amount);
	}
	/**
	 * @param d
	 */
	public void setQuantity(double d)
	{
		quantity = d;
		putUsedField("quantity", quantity);
	}
	/**
	 * @param l
	 */
	public void setBidTypeId(long l)
	{
		bidTypeId = l;
		putUsedField("bidTypeId", bidTypeId);
	}
	/**
	 * @param l
	 */
	public void setClientId(long l)
	{
		clientId = l;
		putUsedField("clientId", clientId);
	}
	/**
	 * @param string
	 */
	public void setCode(String string)
	{
		code = string;
		putUsedField("code", code);
	}
	/**
	 * @param d
	 */
	public void setCommissionCharge(double d)
	{
		commissionCharge = d;
		putUsedField("commissionCharge", commissionCharge);
	}
	/**
	 * @param d
	 */
	public void setConvertRate(double d)
	{
		convertRate = d;
		putUsedField("convertRate", convertRate);
	}
	/**
	 * @param l
	 */
	public void setCounterpartId(long l)
	{
		counterpartId = l;
		putUsedField("counterpartId", counterpartId);
	}
	/**
	 * @param l
	 */
	public void setId(long l)
	{
		id = l;
		putUsedField("id", id);
	}
	/**
	 * @param timestamp
	 */
	public void setInputDate(Timestamp timestamp)
	{
		inputDate = timestamp;
		putUsedField("inputDate", inputDate);
	}
	/**
	 * @param l
	 */
	public void setInputUserId(long l)
	{
		inputUserId = l;
		putUsedField("inputUserId", inputUserId);
	}
	/**
	 * @param l
	 */
	public void setLiquidateRate(long l)
	{
		liquidateRate = l;
		putUsedField("liquidateRate", liquidateRate);
	}
	/**
	 * @param l
	 */
	public void setNextCheckUserId(long l)
	{
		nextCheckUserId = l;
		putUsedField("nextCheckUserId", nextCheckUserId);
	}
	/**
	 * @param d
	 */
	public void setPrice(double d)
	{
		price = d;
		putUsedField("price", price);
	}
	/**
	 * @param d
	 */
	public void setMargin(double d)
	{
		margin = d;
		putUsedField("margin", margin);
	}
	/**
	 * @param d
	 */
	public void setRate(double d)
	{
		rate = d;
		putUsedField("rate", rate);
	}
	/**
	 * @param string
	 */
	public void setRemark(String string)
	{
		remark = string;
		putUsedField("remark", remark);
	}
	/**
	 * @param d
	 */
	public void setPledgeSecuritiesAmount(double d)
	{
		pledgeSecuritiesAmount = d;
		putUsedField("pledgeSecuritiesAmount", pledgeSecuritiesAmount);
	}
	/**
	 * @param l
	 */
	public void setSecuritiesId(long l)
	{
		securitiesId = l;
		putUsedField("securitiesId", securitiesId);
	}
	/**
	 * @param l
	 */
	public void setStatusId(long l)
	{
		statusId = l;
		putUsedField("statusId", statusId);
	}
	/**
	 * @param l
	 */
	public void setStockId(long l)
	{
		stockId = l;
		putUsedField("stockId", stockId);
	}
	/**
	 * @param d
	 */
	public void setStockQuantity(double d)
	{
		stockQuantity = d;
		putUsedField("stockQuantity", stockQuantity);
	}
	/**
	 * @param l
	 */
	public void setTerm(long l)
	{
		term = l;
		putUsedField("term", term);
	}
	/**
	 * @param l
	 */
	public void setTermTypeId(long l)
	{
		termTypeId = l;
		putUsedField("termTypeId", termTypeId);
	}
	/**
	 * @param timestamp
	 */
	public void setTransactionEndDate(Timestamp timestamp)
	{
		transactionEndDate = timestamp;
		putUsedField("transactionEndDate", transactionEndDate);
	}
	/**
	 * @param timestamp
	 */
	public void setTransactionStartDate(Timestamp timestamp)
	{
		transactionStartDate = timestamp;
		putUsedField("transactionStartDate", transactionStartDate);
	}
	/**
	 * @param l
	 */
	public void setTransactionTypeId(long l)
	{
		transactionTypeId = l;
		putUsedField("transactionTypeId", transactionTypeId);
	}
	/**
	 * @param timestamp
	 */
	public void setUpdateDate(Timestamp timestamp)
	{
		updateDate = timestamp;
		putUsedField("updateDate", updateDate);
	}
	/**
	 * @param l
	 */
	public void setUpdateUserId(long l)
	{
		updateUserId = l;
		putUsedField("updateUserId", updateUserId);
	}
	/**
	 * @return Returns the timestamp.
	 */
	public Timestamp getTimestamp()
	{
		return timestamp;
	}
	/**
	 * @param timestamp The timestamp to set.
	 */
	public void setTimestamp(Timestamp timestamp)
	{
		this.timestamp = timestamp;
		putUsedField("timestamp", timestamp);
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
	 * @return Returns the approvalID.
	 */
	public long getApprovalID()
	{
		return approvalID;
	}
	/**
	 * @param approvalID The approvalID to set.
	 */
	public void setApprovalID(long approvalID)
	{
		this.approvalID = approvalID;
	}
	/**
	 * @return Returns the officeId.
	 */
	public long getOfficeId()
	{
		return officeId;
	}
	/**
	 * @param officeId The officeId to set.
	 */
	public void setOfficeId(long officeId)
	{
		this.officeId = officeId;
		putUsedField("officeId", officeId);
	}
	/**
	 * @return Returns the clientName.
	 */
	public String getClientName()
	{
		return clientName;
	}
	/**
	 * @param clientName The clientName to set.
	 */
	public void setClientName(String clientName)
	{
		this.clientName = clientName;
	}
	/**
	 * @return Returns the counterpartName.
	 */
	public String getCounterpartName()
	{
		return counterpartName;
	}
	/**
	 * @param counterpartName The counterpartName to set.
	 */
	public void setCounterpartName(String counterpartName)
	{
		this.counterpartName = counterpartName;
	}
	/**
	 * @return Returns the pageCount.
	 */
	public long getPageCount()
	{
		return pageCount;
	}
	/**
	 * @param pageCount The pageCount to set.
	 */
	public void setPageCount(long pageCount)
	{
		this.pageCount = pageCount;
	}
	/**
	 * @return Returns the recordCount.
	 */
	public long getRecordCount()
	{
		return recordCount;
	}
	/**
	 * @param recordCount The recordCount to set.
	 */
	public void setRecordCount(long recordCount)
	{
		this.recordCount = recordCount;
	}
	/**
	 * @return Returns the clientCode.
	 */
	public String getClientCode()
	{
		return clientCode;
	}
	/**
	 * @param clientCode The clientCode to set.
	 */
	public void setClientCode(String clientCode)
	{
		this.clientCode = clientCode;
	}
	/**
	 * @return Returns the counterpartCode.
	 */
	public String getCounterpartCode()
	{
		return counterpartCode;
	}
	/**
	 * @param counterpartCode The counterpartCode to set.
	 */
	public void setCounterpartCode(String counterpartCode)
	{
		this.counterpartCode = counterpartCode;
	}
	/**
	 * @return Returns the creditAmount.
	 */
	public double getCreditAmount()
	{
		return creditAmount;
	}
	/**
	 * @param creditAmount The creditAmount to set.
	 */
	public void setCreditAmount(double creditAmount)
	{
		this.creditAmount = creditAmount;
	}
	/**
	 * @return Returns the creditEndDate.
	 */
	public Timestamp getCreditEndDate()
	{
		return creditEndDate;
	}
	/**
	 * @param creditEndDate The creditEndDate to set.
	 */
	public void setCreditEndDate(Timestamp creditEndDate)
	{
		this.creditEndDate = creditEndDate;
	}
	/**
	 * @return Returns the creditStartDate.
	 */
	public Timestamp getCreditStartDate()
	{
		return creditStartDate;
	}
	/**
	 * @param creditStartDate The creditStartDate to set.
	 */
	public void setCreditStartDate(Timestamp creditStartDate)
	{
		this.creditStartDate = creditStartDate;
	}
	/**
	 * @return Returns the creditTerm.
	 */
	public long getCreditTerm()
	{
		return creditTerm;
	}
	/**
	 * @param creditTerm The creditTerm to set.
	 */
	public void setCreditTerm(long creditTerm)
	{
		this.creditTerm = creditTerm;
	}
	/**
	 * @return Returns the sumCreditAmount1.
	 */
	public double getSumCreditAmount1()
	{
		return sumCreditAmount1;
	}
	/**
	 * @param sumCreditAmount1 The sumCreditAmount1 to set.
	 */
	public void setSumCreditAmount1(double sumCreditAmount1)
	{
		this.sumCreditAmount1 = sumCreditAmount1;
	}
	/**
	 * @return Returns the sumCreditAmount2.
	 */
	public double getSumCreditAmount2()
	{
		return sumCreditAmount2;
	}
	/**
	 * @param sumCreditAmount2 The sumCreditAmount2 to set.
	 */
	public void setSumCreditAmount2(double sumCreditAmount2)
	{
		this.sumCreditAmount2 = sumCreditAmount2;
	}
	/**
	 * @return Returns the securitiesCode.
	 */
	public String getSecuritiesCode()
	{
		return securitiesCode;
	}
	/**
	 * @param securitiesCode The securitiesCode to set.
	 */
	public void setSecuritiesCode(String securitiesCode)
	{
		this.securitiesCode = securitiesCode;
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
	 * @return Returns the accountNo.
	 */
	public String getAccountNo()
	{
		return accountNo;
	}
	/**
	 * @param accountNo The accountNo to set.
	 */
	public void setAccountNo(String accountNo)
	{
		this.accountNo = accountNo;
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
	 * @return Returns the accountName.
	 */
	public String getAccountName()
	{
		return accountName;
	}
	/**
	 * @param accountName The accountName to set.
	 */
	public void setAccountName(String accountName)
	{
		this.accountName = accountName;
	}
	/**
	 * @return Returns the stockCode.
	 */
	public String getStockCode()
	{
		return stockCode;
	}
	/**
	 * @param stockCode The stockCode to set.
	 */
	public void setStockCode(String stockCode)
	{
		this.stockCode = stockCode;
	}
	/**
	 * @return Returns the stockName.
	 */
	public String getStockName()
	{
		return stockName;
	}
	/**
	 * @param stockName The stockName to set.
	 */
	public void setStockName(String stockName)
	{
		this.stockName = stockName;
	}
	/**
	 * @return Returns the changeRate.
	 */
	public double getChangeRate()
	{
		return changeRate;
	}
	/**
	 * @param changeRate The changeRate to set.
	 */
	public void setChangeRate(double changeRate)
	{
		this.changeRate = changeRate;
		putUsedField("changeRate", changeRate);
	}
	/**
	 * @return Returns the commissionChargeRate.
	 */
	public double getCommissionChargeRate()
	{
		return commissionChargeRate;
	}
	/**
	 * @param commissionChargeRate The commissionChargeRate to set.
	 */
	public void setCommissionChargeRate(double commissionChargeRate)
	{
		this.commissionChargeRate = commissionChargeRate;
		putUsedField("commissionChargeRate", commissionChargeRate);
	}
	/**
	 * @return Returns the interestTypeID.
	 */
	public long getInterestTypeID()
	{
		return interestTypeID;
	}
	/**
	 * @param interestTypeID The interestTypeID to set.
	 */
	public void setInterestTypeID(long interestTypeID)
	{
		this.interestTypeID = interestTypeID;
		putUsedField("interestTypeID", interestTypeID);
	}
	/**
	 * @return Returns the maturitySource.
	 */
	public String getMaturitySource()
	{
		return maturitySource;
	}
	/**
	 * @param maturitySource The maturitySource to set.
	 */
	public void setMaturitySource(String maturitySource)
	{
		this.maturitySource = maturitySource;
		putUsedField("maturitySource", maturitySource);
	}
	/**
	 * @return Returns the settlementTypeID.
	 */
	public long getSettlementTypeID()
	{
		return settlementTypeID;
	}
	/**
	 * @param settlementTypeID The settlementTypeID to set.
	 */
	public void setSettlementTypeID(long settlementTypeID)
	{
		this.settlementTypeID = settlementTypeID;
		putUsedField("settlementTypeID", settlementTypeID);
	}
	/**
	 * @return Returns the startRate.
	 */
	public double getStartRate()
	{
		return startRate;
	}
	/**
	 * @param startRate The startRate to set.
	 */
	public void setStartRate(double startRate)
	{
		this.startRate = startRate;
		putUsedField("startRate", startRate);
	}
	/**
	 * @return Returns the nextCheckLevel.
	 */
	public long getNextCheckLevel()
	{
		return nextCheckLevel;
	}
	/**
	 * @param nextCheckLevel The nextCheckLevel to set.
	 */
	public void setNextCheckLevel(long nextCheckLevel)
	{
		this.nextCheckLevel = nextCheckLevel;
		putUsedField("nextCheckLevel", nextCheckLevel);
	}
	/**
	 * Returns the ownerTypeId.
	 * @return long
	 */
	public long getOwnerTypeId()
	{
		return ownerTypeId;
	}
	/**
	 * @param l
	 */
	public void setOwnerTypeId(long l)
	{
		ownerTypeId = l;
		putUsedField("OwnerTypeId", ownerTypeId);
	}
	/**
	 * @return
	 */
	public Timestamp getMaturityDate()
	{
		return maturityDate;
	}
	/**
	 * @param timestamp
	 */
	public void setMaturityDate(Timestamp timestamp)
	{
		maturityDate = timestamp;
		putUsedField("maturityDate", maturityDate);
	}
	/**
	 * @return
	 */
	public String getBondName()
	{
		return bondName;
	}
	/**
	 * @param string
	 */
	public void setBondName(String string)
	{
		bondName = string;
		putUsedField("bondName", bondName);
	}
	/**
	 * @return
	 */
	public double getBondScale()
	{
		return bondScale;
	}
	/**
	 * @param d
	 */
	public void setBondScale(double d)
	{
		bondScale = d;
	}
	/**
	 * @return Returns the isLowLevel.
	 */
	public long getIsLowLevel()
	{
		return isLowLevel;
	}
	/**
	 * @param isLowLevel The isLowLevel to set.
	 */
	public void setIsLowLevel(long isLowLevel)
	{
		this.isLowLevel = isLowLevel;
		putUsedField("isLowLevel", isLowLevel);
	}
	/**
	 * Returns the capitalLandingCreditExtensionMessage.
	 * @return String
	 */
	public String getCapitalLandingCreditExtensionMessage()
	{
		return capitalLandingCreditExtensionMessage;
	}
	/**
	 * Sets the capitalLandingCreditExtensionMessage.
	 * @param capitalLandingCreditExtensionMessage The capitalLandingCreditExtensionMessage to set
	 */
	public void setCapitalLandingCreditExtensionMessage(String capitalLandingCreditExtensionMessage)
	{
		this.capitalLandingCreditExtensionMessage = capitalLandingCreditExtensionMessage;
	}
	/**
	 * Returns the isSameCheckLevel.
	 * @return long
	 */
	public long getIsSameCheckLevel()
	{
		return isSameCheckLevel;
	}

	/**
	 * Sets the isSameCheckLevel.
	 * @param isSameCheckLevel The isSameCheckLevel to set
	 */
	public void setIsSameCheckLevel(long isSameCheckLevel)
	{
		this.isSameCheckLevel = isSameCheckLevel;
	}

	
	/**
	 * @return Returns the incomeRate.
	 */
	public double getIncomeRate() {
		return incomeRate;
	}
	/**
	 * @param incomeRate The incomeRate to set.
	 */
	public void setIncomeRate(double incomeRate) {
		this.incomeRate = incomeRate;
		putUsedField("incomeRate", incomeRate);
	}
	
	/**
	 * @return Returns the costAmount.
	 */
	public double getCostAmount() {
		return costAmount;
	}
	/**
	 * @param costAmount The costAmount to set.
	 */
	public void setCostAmount(double costAmount) {
		this.costAmount = costAmount;
		putUsedField("costAmount", costAmount);
	}

	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}

	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
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
	public long getModuleId() {
		return moduleId;
	}
	public void setModuleId(long moduleId) {
		this.moduleId = moduleId;
	}

}
