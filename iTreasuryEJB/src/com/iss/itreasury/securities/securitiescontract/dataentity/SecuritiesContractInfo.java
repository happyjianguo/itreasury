/*
 * Created on 2004-4-19
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.securities.securitiescontract.dataentity;

import com.iss.itreasury.securities.util.SECBaseDataEntity;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;

import java.sql.Timestamp;
/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SecuritiesContractInfo extends SECBaseDataEntity{

	private long 		id = -1;			
	private long 		clientId=-1;			//ҵ��λID
	private long 		counterpartId=-1;		//���׶��֣�ȯ�̣�ί�з�������У�������
	private long 		counterpartBankId=-1;	//���׶��ֿ�����
	private long 		accountId=-1;			//�����˺ţ��ʽ��˺�
	private double		amount=0;				//�ع����ڳ�������������ί�н��
	private double 		price=0;				//ת�ü۸�
	private double		margin=0;				//���for�������
	private long 		currencyId=-1;			//����
	private long		officeId=-1;			//����˾
	private long		term=-1;				//������ޣ��ع����ޣ�ί�����ޣ�������ޣ���������
	private long		termTypeId=-1;			//��������
	private double		rate=0;					//������ʣ��ع����ʣ�Ԥ�������ʣ�������
	private String		remark="";				//��ע������ί�����ݣ������Ϣ��ʽ
	private long		nextCheckUserId=-1;		//��һ�������
	private long		inputUserId=-1;			//¼����
	private Timestamp	inputDate=null;			//¼��ʱ��
	private long 		updateUserId=-1;		//�޸���
	private Timestamp	updateDate=null;		//�޸�ʱ��
	private long 		statusId=-1;			//��ͬ״̬
	private Timestamp	timeStamp=null;			//ʱ���
	private double		commissionChargeRate=0;			//��������
	private long		settlementTypeId=-1;	//��Ϣ��ʽID
	private long		interestTypeId=-1;		//��Ϣ��ʽID��ί���ʲ���ʽID��������ʽID
	private String		maturitySource="";		//�����ʽ���Դ
	private double		startRate=0;			//���ü�Ϣ��ʽ���ڳ�
	private double		changeRate=0;			//���ü�Ϣ��ʽ���䶯��
	private long		needInform=-1;			//�Ƿ�֪ͨԭ�����
	private String		code="";				//��ͬ���
	private long		applyId=-1;				//��Ӧ������ID
	private Timestamp	activeDate=null;		//����ʱ��
	private long		TransactionTypeId=-1;	//       ��������
	private Timestamp	TransactionStartDate=null;	//�ɽ����ڿ�ʼ�գ��ع�����ͬ��ʼ����
	private Timestamp	TransactionEndDate=null;	//�ɽ����ڽ����գ��ع�����ͬ�������ڣ�������
	private Timestamp	TransactionDate=null;	//�����գ���ͬ��Ч�գ�Ԥ�Ƹ�����
                         
	
	private String		clientName="";			//ҵ��λ����
	private String 		counterpartName="";		//���׶�������
	private String		counterpartCode="";		//���׶��ֱ��
	private String		inputUserName="";		//¼��������
	private String 		nextCheckUserName="";	//��һ�����������
	
	private long		lastCheckUserId=-1;		//��������
	private String		lastCheckUserName="";	//������������
	private long		userCheckLevel=-1;		//��ǰ�û�����˼���

	private double		receivedAmount=0;		//����ȡ�ع����,�ѷ���Ͷ�ʽ����յ����н��ѷ���ί�н�������ծȯ���
	private double		buyBackAmount=0;		//�ѹ��ؽ����ջ�Ͷ�ʽ��ѷ������н����ջ�ί�н���֧������ծȯ���
	private double		balance=0;				//��ͬ����ͬ����ͬ����ͬ��δ����ծȯ���
	private Timestamp	interestUpdateDate=null;//�ۼ�Ӧ����Ϣ������(Ϊ����ۼ�Ӧ����Ϣ)
	private Timestamp 	lastEndInterestDate=null;//�ϴν�Ϣ��
	private double 		interestBalance=0;		//�ۼ�Ӧ����Ϣ
	private Timestamp	interestBeginDate=null; //��һ����ȡ�ع�������Ϣ��
	
	private double		boldScale=0;			//ծȯ�ܹ�ģ
	private String		bondName="";			//ծȯ����
	private long		nextCheckLevel=-1;		//����������	
	private long		stockHolderID = -1;		//�ֹ��˱��
	
	private Timestamp   planModifyDate = null;	//ִ�мƻ��޸�����	
	private long 		planDetailCount = -1;	//��ǰ�汾��ֵ�мƻ���ϸ����
	
	private double		contractInterest = 0;		//��ͬ������Ϣ
	private Timestamp	contractInterestDate = null;//��ͬ��Ϣ��������
	
	private String 		stockHolderIDArr = "";		//ί����ƹɶ��˻�����
	private String 		counterpartBankIDArr = "";  //ί����ƿ���Ӫҵ��
	private String 		AccountIDArr = "";			//ί������ʽ��ʺ�
	
	//���
	private double incomeRate = 0;  //�ع�������
	private double costAmount = 0;  //�ع��ɱ�
	
	private String bargainCode=null; // ��ͬ���
	
	private double transferBalance = 0;  			//ת�ÿ������
	private double availableTransferBalance =0;  	//ת�ÿ���������
	private double repurchaseBalance = 0;  			//���������
	private double availableRepurchaseBalance =0;   //�����ؿ������
	private double receivedInterest	=0;  			//������Ϣ�����տ
	private double waitReceivedInterest	=0;  		//������Ϣ�����տ
	private double paidInterest =0;  				//�Ѹ���Ϣ����֧����
	private double waitPaidInterest =0;  			//�Ѹ���Ϣ����֧����
	
//	modify by xwhe date:2007-09-13
	
    private InutParameterInfo inutParameterInfo = null;
    private long attachId = -1;		//��������ID
	private long attornmentapplyid = -1;	//�����˺ţ��ʽ��˺�
	public long getAttornmentapplyid() {
		return attornmentapplyid;
	}
	public void setAttornmentapplyid(long attornmentapplyid) {
		this.attornmentapplyid = attornmentapplyid;
		putUsedField("attornmentapplyid", attornmentapplyid);
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
	public long getId() {
		return id;
	}

	/**
	 * @param l
	 */
	public void setId(long l) {
		id = l;
		putUsedField("id", id);
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
	public Timestamp getActiveDate()
	{
		return activeDate;
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
	public long getApplyId()
	{
		return applyId;
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
	public long getCounterpartBankId()
	{
		return counterpartBankId;
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
	public long getCurrencyId()
	{
		return currencyId;
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
	public long getInterestTypeId()
	{
		return interestTypeId;
	}

	/**
	 * @return
	 */
	public String getMaturitySource()
	{
		return maturitySource;
	}

	/**
	 * @return
	 */
	public long getNeedInform()
	{
		return needInform;
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
	public long getOfficeId()
	{
		return officeId;
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
	public long getSettlementTypeId()
	{
		return settlementTypeId;
	}

	/**
	 * @return
	 */
	public double getStartRate()
	{
		return startRate;
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
	public Timestamp getTimeStamp()
	{
		return timeStamp;
	}

	/**
	 * @return
	 */
	public Timestamp getTransactionDate()
	{
		return TransactionDate;
	}

	/**
	 * @return
	 */
	public Timestamp getTransactionEndDate()
	{
		return TransactionEndDate;
	}

	/**
	 * @return
	 */
	public Timestamp getTransactionStartDate()
	{
		return TransactionStartDate;
	}

	/**
	 * @return
	 */
	public long getTransactionTypeId()
	{
		return TransactionTypeId;
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
	
	public double getTransferBalance() {
		return transferBalance;
	}
	public double getAvailableTransferBalance() {
		return availableTransferBalance;
	}
	public double getRepurchaseBalance() {
		return repurchaseBalance;
	}
	public double getAvailableRepurchaseBalance() {
		return availableRepurchaseBalance;
	}
	public double getReceivedInterest() {
		return receivedInterest;
	}
	public double getWaitReceivedInterest() {
		return waitReceivedInterest;
	}
	public double getPaidInterest() {
		return paidInterest;
	}
	public double getWaitPaidInterest() {
		return waitPaidInterest;
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
	 * @param timestamp
	 */
	public void setActiveDate(Timestamp timestamp)
	{
		activeDate = timestamp;
		putUsedField("activeDate", activeDate);
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
	 * @param l
	 */
	public void setApplyId(long l)
	{
		applyId = l;
		putUsedField("applyID", applyId);
	}

	/**
	 * @param l
	 */
	public void setClientId(long l)
	{
		clientId = l;
		putUsedField("clientID", clientId);
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
	 * @param l
	 */
	public void setCounterpartBankId(long l)
	{
		counterpartBankId = l;
		putUsedField("counterpartBankId", counterpartBankId);
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
	public void setCurrencyId(long l)
	{
		currencyId = l;
		putUsedField("currencyId", currencyId);
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
	public void setInterestTypeId(long l)
	{
		interestTypeId = l;
		putUsedField("interestTypeId", interestTypeId);
	}

	/**
	 * @param string
	 */
	public void setMaturitySource(String string)
	{
		maturitySource = string;
		putUsedField("maturitySource", maturitySource);
	}

	/**
	 * @param l
	 */
	public void setNeedInform(long l)
	{
		needInform = l;
		putUsedField("needInform", needInform);
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
	 * @param l
	 */
	public void setOfficeId(long l)
	{
		officeId = l;
		putUsedField("officeId", officeId);
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
	 * @param l
	 */
	public void setSettlementTypeId(long l)
	{
		settlementTypeId = l;
		putUsedField("settlementTypeId", settlementTypeId);
	}

	/**
	 * @param d
	 */
	public void setStartRate(double d)
	{
		startRate = d;
		putUsedField("startRate", startRate);
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
	public void setTimeStamp(Timestamp timestamp)
	{
		timeStamp = timestamp;
		putUsedField("timeStamp", timeStamp);
	}

	/**
	 * @param timestamp
	 */
	public void setTransactionDate(Timestamp timestamp)
	{
		TransactionDate = timestamp;
		putUsedField("transActionDate", TransactionDate);
	}

	/**
	 * @param timestamp
	 */
	public void setTransactionEndDate(Timestamp timestamp)
	{
		TransactionEndDate = timestamp;
		putUsedField("TransactionEndDate", TransactionEndDate);
	}

	/**
	 * @param timestamp
	 */
	public void setTransactionStartDate(Timestamp timestamp)
	{
		TransactionStartDate = timestamp;
		putUsedField("TransActionStartDate", TransactionStartDate);
	}

	/**
	 * @param l
	 */
	public void setTransactionTypeId(long l)
	{
		TransactionTypeId = l;
		putUsedField("TransactionTypeId", TransactionTypeId);
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
	 * @return
	 */
	public String getClientName()
	{
		return clientName;
	}

	/**
	 * @return
	 */
	public String getCounterpartName()
	{
		return counterpartName;
	}

	/**
	 * @return
	 */
	public String getInputUserName()
	{
		return inputUserName;
	}

	/**
	 * @return
	 */
	public String getNextCheckUserName()
	{
		return nextCheckUserName;
	}

	/**
	 * @param string
	 */
	public void setClientName(String string)
	{
		clientName = string;
	}

	/**
	 * @param string
	 */
	public void setCounterpartName(String string)
	{
		counterpartName = string;
	}

	/**
	 * @param string
	 */
	public void setInputUserName(String string)
	{
		inputUserName = string;
	}

	/**
	 * @param string
	 */
	public void setNextCheckUserName(String string)
	{
		nextCheckUserName = string;
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
	public double getChangeRate()
	{
		return changeRate;
	}

	/**
	 * @param d
	 */
	public void setChangeRate(double d)
	{
		changeRate = d;
		putUsedField("changeRate", changeRate);
	}

	/**
	 * @return
	 */
	public String getCounterpartCode()
	{
		return counterpartCode;
	}

	/**
	 * @param string
	 */
	public void setCounterpartCode(String string)
	{
		counterpartCode = string;
	}

	/**
	 * @return
	 */
	public long getLastCheckUserId()
	{
		return lastCheckUserId;
	}

	/**
	 * @return
	 */
	public String getLastCheckUserName()
	{
		return lastCheckUserName;
	}

	/**
	 * @return
	 */
	public long getUserCheckLevel()
	{
		return userCheckLevel;
	}

	/**
	 * @param l
	 */
	public void setLastCheckUserId(long l)
	{
		lastCheckUserId = l;
	}

	/**
	 * @param string
	 */
	public void setLastCheckUserName(String string)
	{
		lastCheckUserName = string;
	}

	/**
	 * @param l
	 */
	public void setUserCheckLevel(long l)
	{
		userCheckLevel = l;
	}

	/**
	 * @return
	 */
	public double getBalance()
	{
		return balance;
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
	 * @return
	 */
	public Timestamp getInterestUpdateDate()
	{
		return interestUpdateDate;
	}

	/**
	 * @param d
	 */
	public void setBalance(double d)
	{
		balance = d;
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

	/**
	 * @param timestamp
	 */
	public void setInterestUpdateDate(Timestamp timestamp)
	{
		interestUpdateDate = timestamp;
		putUsedField("InterestUpdateDate", interestUpdateDate);
	}

	/**
	 * @return
	 */
	public Timestamp getLastEndInterestDate()
	{
		return lastEndInterestDate;
	}

	/**
	 * @param timestamp
	 */
	public void setLastEndInterestDate(Timestamp timestamp)
	{
		lastEndInterestDate = timestamp;
		putUsedField("lastEndInterestDate", lastEndInterestDate);
	}

	/**
	 * @return
	 */
	public double getInterestBalance()
	{
		return interestBalance;
	}

	/**
	 * @return
	 */
	public Timestamp getInterestBeginDate()
	{
		return interestBeginDate;
		
	}

	/**
	 * @param d
	 */
	public void setInterestBalance(double d)
	{
		interestBalance = d;
		putUsedField("interestBalance", interestBalance);
	}

	/**
	 * @param timestamp
	 */
	public void setInterestBeginDate(Timestamp timestamp)
	{
		interestBeginDate = timestamp;
		putUsedField("interestBeginDate", interestBeginDate);
	}

	/**
	 * @return
	 */
	public double getBoldScale()
	{
		return boldScale;
	}

	/**
	 * @param d
	 */
	public void setBoldScale(double d)
	{
		boldScale = d;
	}

	/**
	 * @return
	 */
	public String getBondName()
	{
		return bondName;
	}

	/**
	 * @return
	 */
	public long getNextCheckLevel()
	{
		return nextCheckLevel;
	}

	/**
	 * @param string
	 */
	public void setBondName(String string)
	{
		bondName = string;
		putUsedField("BondName", bondName);
	}

	/**
	 * @param l
	 */
	public void setNextCheckLevel(long l)
	{
		nextCheckLevel = l;
		putUsedField("nextCheckLevel", nextCheckLevel);
	}

	/**
	 * @return
	 */
	public long getStockHolderID()
	{
		return stockHolderID;
	}

	/**
	 * @param l
	 */
	public void setStockHolderID(long l)
	{
		stockHolderID = l;
		putUsedField("stockHolderID", stockHolderID);
	}

	/**
	 * @return
	 */
	public Timestamp getPlanModifyDate()
	{
		return planModifyDate;
	}

	/**
	 * @param timestamp
	 */
	public void setPlanModifyDate(Timestamp timestamp)
	{
		planModifyDate = timestamp;
	}

	/**
	 * @return
	 */
	public long getPlanDetailCount()
	{
		return planDetailCount;
	}

	/**
	 * @param l
	 */
	public void setPlanDetailCount(long l)
	{
		planDetailCount = l;
	}

    /**
     * @return Returns the contractInterest.
     */
    public double getContractInterest()
    {
        return contractInterest;
    }
    /**
     * @param contractInterest The contractInterest to set.
     */
    public void setContractInterest(double contractInterest)
    {
        this.contractInterest = contractInterest;
        putUsedField("contractInterest", contractInterest);
    }
    /**
     * @return Returns the contractInterestDate.
     */
    public Timestamp getContractInterestDate()
    {
        return contractInterestDate;
    }
    /**
     * @param contractInterestDate The contractInterestDate to set.
     */
    public void setContractInterestDate(Timestamp contractInterestDate)
    {
        this.contractInterestDate = contractInterestDate;
        putUsedField("contractInterestDate", contractInterestDate);
    }
	/**
	 * @return
	 */
	public String getAccountIDArr()
	{
		return AccountIDArr;
	}

	/**
	 * @return
	 */
	public String getCounterpartBankIDArr()
	{
		return counterpartBankIDArr;
	}

	/**
	 * @return
	 */
	public String getStockHolderIDArr()
	{
		return stockHolderIDArr;
	}

	/**
	 * @param string
	 */
	public void setAccountIDArr(String string)
	{
		AccountIDArr = string;
		putUsedField("AccountIDArr", AccountIDArr);
	}

	/**
	 * @param string
	 */
	public void setCounterpartBankIDArr(String string)
	{
		counterpartBankIDArr = string;
		putUsedField("counterpartBankIDArr", counterpartBankIDArr);
	}

	/**
	 * @param string
	 */
	public void setStockHolderIDArr(String string)
	{
		stockHolderIDArr = string;
		putUsedField("stockHolderIDArr", stockHolderIDArr);
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
	
	public void setTransferBalance(double transferBalance) {
		this.transferBalance = transferBalance;
		putUsedField("transferBalance", transferBalance);
	}
	public void setAvailableTransferBalance(double availableTransferBalance) {
		this.availableTransferBalance = availableTransferBalance;
		putUsedField("availableTransferBalance", availableTransferBalance);
	}
	public void setRepurchaseBalance(double repurchaseBalance) {
		this.repurchaseBalance = repurchaseBalance;
		putUsedField("repurchaseBalance", repurchaseBalance);
	}
	public void setAvailableRepurchaseBalance(double availableRepurchaseBalance) {
		this.availableRepurchaseBalance = availableRepurchaseBalance;
		putUsedField("availableRepurchaseBalance", availableRepurchaseBalance);
	}
	public void setReceivedInterest(double receivedInterest) {
		this.receivedInterest = receivedInterest;
		putUsedField("receivedInterest", receivedInterest);
	}
	public void setWaitReceivedInterest(double waitReceivedInterest) {
		this.waitReceivedInterest = waitReceivedInterest;
		putUsedField("waitReceivedInterest", waitReceivedInterest);
	}
	public void setPaidInterest(double paidInterest) {
		this.paidInterest = paidInterest;
		putUsedField("paidInterest", paidInterest);
	}
	public void setWaitPaidInterest(double waitPaidInterest) {
		this.waitPaidInterest = waitPaidInterest;
		putUsedField("waitPaidInterest", waitPaidInterest);
	}
}
