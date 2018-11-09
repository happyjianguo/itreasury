package com.iss.itreasury.settlement.importdata.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * Title: iTreasury Description: Copyright: Copyright (c) 2003 Company:
 * iSoftStone
 * 
 * @author yehuang
 * @version Date of Creation 2003-9-8
 */
public class ImportdataInfo extends ITreasuryBaseDataEntity {
    private long id = -1;
	private String currentNo = "";//������ˮ��

	private Timestamp accoutDate = null;//�������

	private String use = "";//��;

	private double amount = 0.0;//���

	private long currencyId = -1;//����

	private String creditAccountNo = "";//�跽�˺�

	private String creditAccountName = "";//�跽�˺�ȫ��

	//private String lenderOpenAccountOrganName = "";//�����˺ſ�����������

	private String debitAccountNo = "";//�����˻���

	private String debitAccountName = "";//�����˻�ȫ��

	//private String dataForBackup = "";//��������

	//private String consignPayerNo = "";//ί�и����˺�

	//private String consignPayerTotalCall = "";//ί�и����˻�ȫ��

	private String transactionType = "";//�������

	//private String tradeStarterClientNo = "";//���׷��𷽿ͻ���

	private String inputUserName = "";//�Ƶ�

	private String checkUserName = "";//����

	//private String director = "";//����

	private Timestamp inputDate = null;//�Ƶ�����

	private Timestamp checkDate = null;//��������

	private String remark = "";//��ע

	//private String tradeCode = "";//���״���

	//private String approveDate = "";//��������

	//private String senderOperatorNo = "";//���ͷ�����Ա���

	//private String lenderNoSubjectCode = "";//�����˺ſ�Ŀ����

	//private String borrowerClientNo = "";//�跽�ͻ���

	//private String lenderClientNo = "";//�����ͻ���

	//private String lenderClientName = "";//�����ͻ�����

	//private String lenderMemberBankNo = "";//������Ա�к�

	//private String lenderExchangeNo = "";//�����˻�������

	//private String lenderUnionBankNo = "";//�����˻����к�

	//private String counterNo = "";//��̨��

	//private String checkerCode = "";//����Ա����

	//private String directorCode = "";//���ܴ���

	//private String transferHandleType = "";//ת�˴�������

	private long statusId = -1;//����״̬
	
	private long recordStatus= -1;//���ݼ�¼״̬ 1.δ���� 2.�Ѿ����� 3.�������� 4.����Ҫ��������� 5.����ʧ��

	private long columnNumer = -1;//�ֶ���Ŀ

	//private String processMaxLevel = "";//������󼶱�

	//private String handlingCharge = "";//������

	//private String levelTwoBankCode = "";//�����д���

	//private String levelOneBankCode = "";//һ���д���

	//private String innerIntegradeAccount = "";//�ڲ��ۺ��˻�

	//private String signAccountNo = "";//ǩԼ�˺�

	//private String borrowerSubjectCode = "";//�跽��Ŀ����

	//private String isAudited = "";//�Ƿ����

	//private String auditClientNo = "";//���˿ͻ���

	//private String auditOperatorCode = "";//���˲���Ա����

	//private String tradeMonth = "";//�����·�

	//private String signAccountName = "";//ǩԼ�˻�����

    /**
     * @return Returns the accoutDate.
     */
    public Timestamp getAccoutDate() {
        return accoutDate;
    }
    /**
     * @param accoutDate The accoutDate to set.
     */
    public void setAccoutDate(Timestamp accoutDate) {
        putUsedField("accoutDate", accoutDate);
        this.accoutDate = accoutDate;
    }
    /**
     * @return Returns the amount.
     */
    public double getAmount() {
        return amount;
    }
    /**
     * @param amount The amount to set.
     */
    public void setAmount(double amount) {
        putUsedField("amount", amount);
        this.amount = amount;
    }
    /**
     * @return Returns the checkDate.
     */
    public Timestamp getCheckDate() {
        return checkDate;
    }
    /**
     * @param checkDate The checkDate to set.
     */
    public void setCheckDate(Timestamp checkDate) {
        putUsedField("checkDate", checkDate);
        this.checkDate = checkDate;
    }
    /**
     * @return Returns the checkUserName.
     */
    public String getCheckUserName() {
        return checkUserName;
    }
    /**
     * @param checkUserName The checkUserName to set.
     */
    public void setCheckUserName(String checkUserName) {
        putUsedField("checkUserName", checkUserName);
        this.checkUserName = checkUserName;
    }
    /**
     * @return Returns the columnNumer.
     */
    public long getColumnNumer() {
        return columnNumer;
    }
    /**
     * @param columnNumer The columnNumer to set.
     */
    public void setColumnNumer(long columnNumer) {
        //putUsedField("columnNumer", columnNumer);
        this.columnNumer = columnNumer;
    }
    /**
     * @return Returns the creditAccountName.
     */
    public String getCreditAccountName() {
        return creditAccountName;
    }
    /**
     * @param creditAccountName The creditAccountName to set.
     */
    public void setCreditAccountName(String creditAccountName) {
        putUsedField("creditAccountName", creditAccountName);
        this.creditAccountName = creditAccountName;
    }
    /**
     * @return Returns the creditAccountNo.
     */
    public String getCreditAccountNo() {
        return creditAccountNo;
    }
    /**
     * @param creditAccountNo The creditAccountNo to set.
     */
    public void setCreditAccountNo(String creditAccountNo) {
        putUsedField("creditAccountNo", creditAccountNo);
        this.creditAccountNo = creditAccountNo;
    }
    /**
     * @return Returns the currencyId.
     */
    public long getCurrencyId() {
        return currencyId;
    }
    /**
     * @param currencyId The currencyId to set.
     */
    public void setCurrencyId(long currencyId) {
        putUsedField("currencyId", currencyId);
        this.currencyId = currencyId;
    }
    /**
     * @return Returns the currentNo.
     */
    public String getCurrentNo() {
        return currentNo;
    }
    /**
     * @param currentNo The currentNo to set.
     */
    public void setCurrentNo(String currentNo) {
        putUsedField("currentNo", currentNo);
        this.currentNo = currentNo;
    }
    /**
     * @return Returns the debitAccountName.
     */
    public String getDebitAccountName() {
        return debitAccountName;
    }
    /**
     * @param debitAccountName The debitAccountName to set.
     */
    public void setDebitAccountName(String debitAccountName) {
        putUsedField("debitAccountName", debitAccountName);
        this.debitAccountName = debitAccountName;
    }
    /**
     * @return Returns the debitAccountNo.
     */
    public String getDebitAccountNo() {
        return debitAccountNo;
    }
    /**
     * @param debitAccountNo The debitAccountNo to set.
     */
    public void setDebitAccountNo(String debitAccountNo) {
        putUsedField("debitAccountNo", debitAccountNo);
        this.debitAccountNo = debitAccountNo;
    }
    /**
     * @return Returns the id.
     */
    public long getId() {
        return id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(long id) {
        putUsedField("id", id);
        this.id = id;
    }
    /**
     * @return Returns the inputDate.
     */
    public Timestamp getInputDate() {
        return inputDate;
    }
    /**
     * @param inputDate The inputDate to set.
     */
    public void setInputDate(Timestamp inputDate) {
        putUsedField("inputDate", inputDate);
        this.inputDate = inputDate;
    }
    /**
     * @return Returns the inputUserName.
     */
    public String getInputUserName() {
        return inputUserName;
    }
    /**
     * @param inputUserName The inputUserName to set.
     */
    public void setInputUserName(String inputUserName) {
        putUsedField("inputUserName", inputUserName);
        this.inputUserName = inputUserName;
    }
    /**
     * @return Returns the recordStatus.
     */
    public long getRecordStatus() {
        return recordStatus;
    }
    /**
     * @param recordStatus The recordStatus to set.
     */
    public void setRecordStatus(long recordStatus) {
        putUsedField("recordStatus", recordStatus);
        this.recordStatus = recordStatus;
    }
    /**
     * @return Returns the remark.
     */
    public String getRemark() {
        return remark;
    }
    /**
     * @param remark The remark to set.
     */
    public void setRemark(String remark) {
        putUsedField("remark", remark);
        this.remark = remark;
    }
    /**
     * @return Returns the statusId.
     */
    public long getStatusId() {
        return statusId;
    }
    /**
     * @param statusId The statusId to set.
     */
    public void setStatusId(long statusId) {
        putUsedField("statusId", statusId);
        this.statusId = statusId;
    }
    /**
     * @return Returns the transactionType.
     */
    public String getTransactionType() {
        return transactionType;
    }
    /**
     * @param transactionType The transactionType to set.
     */
    public void setTransactionType(String transactionType) {
        putUsedField("transactionType", transactionType);
        this.transactionType = transactionType;
    }
    /**
     * @return Returns the use.
     */
    public String getUse() {
        return use;
    }
    /**
     * @param use The use to set.
     */
    public void setUse(String use) {
        putUsedField("use", use);
        this.use = use;
    }
}