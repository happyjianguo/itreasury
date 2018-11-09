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
	private String currentNo = "";//网银流水号

	private Timestamp accoutDate = null;//会计日期

	private String use = "";//用途

	private double amount = 0.0;//金额

	private long currencyId = -1;//币种

	private String creditAccountNo = "";//借方账号

	private String creditAccountName = "";//借方账号全称

	//private String lenderOpenAccountOrganName = "";//贷方账号开户机构名称

	private String debitAccountNo = "";//贷方账户号

	private String debitAccountName = "";//贷方账户全称

	//private String dataForBackup = "";//备用数据

	//private String consignPayerNo = "";//委托付款账号

	//private String consignPayerTotalCall = "";//委托付款账户全称

	private String transactionType = "";//交易类别

	//private String tradeStarterClientNo = "";//交易发起方客户号

	private String inputUserName = "";//制单

	private String checkUserName = "";//复核

	//private String director = "";//主管

	private Timestamp inputDate = null;//制单日期

	private Timestamp checkDate = null;//复核日期

	private String remark = "";//备注

	//private String tradeCode = "";//交易代码

	//private String approveDate = "";//审批日期

	//private String senderOperatorNo = "";//发送方操作员编号

	//private String lenderNoSubjectCode = "";//贷方账号科目代码

	//private String borrowerClientNo = "";//借方客户号

	//private String lenderClientNo = "";//贷方客户号

	//private String lenderClientName = "";//贷方客户名称

	//private String lenderMemberBankNo = "";//贷方成员行号

	//private String lenderExchangeNo = "";//贷方账户交换号

	//private String lenderUnionBankNo = "";//贷方账户联行号

	//private String counterNo = "";//柜台号

	//private String checkerCode = "";//复核员代码

	//private String directorCode = "";//主管代码

	//private String transferHandleType = "";//转账处理类型

	private long statusId = -1;//交易状态
	
	private long recordStatus= -1;//数据记录状态 1.未存入 2.已经存入 3.待定数据 4.不需要导入的数据 5.存入失败

	private long columnNumer = -1;//字段数目

	//private String processMaxLevel = "";//流程最大级别

	//private String handlingCharge = "";//手续费

	//private String levelTwoBankCode = "";//二级行代码

	//private String levelOneBankCode = "";//一级行代码

	//private String innerIntegradeAccount = "";//内部综合账户

	//private String signAccountNo = "";//签约账号

	//private String borrowerSubjectCode = "";//借方科目代码

	//private String isAudited = "";//是否稽核

	//private String auditClientNo = "";//稽核客户号

	//private String auditOperatorCode = "";//稽核操作员代码

	//private String tradeMonth = "";//交易月份

	//private String signAccountName = "";//签约账户名称

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