/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-7-19
 */
package com.iss.itreasury.treasuryplan.etl.extract.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.treasuryplan.util.TreasuryPlanBaseDataEntity;

/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class AbstractAmountInfo extends TreasuryPlanBaseDataEntity {
	private long id = -1;
	private long officeID = -1;                       //����ID
	private long currencyID = -1;                     //����ID
	private long moduleID = -1;                       //ģ��ID	
	private long amountDirection = -1;              //��������  		
	private String transactionName = null;          //������������
	private Timestamp executeDate = null;             //ִ������
	private Timestamp transactionDate = null;         //������������
	private long accountTypeId = -1;                  //�˻�����  
	private String glSubjectCode = null;              //��Ŀ�� 
	private String clientCode = null;                 //�˻���/�ʽ��˻�
	private String accountCode = null;                //�˻���/�ʽ��˻�
	private String contractCode = null;               //��ͬ��/�浥�� 
	private String payFormCode = null;                //�ſ�֪ͨ����
	private String counterpartName = null;            //���׶�������
	private String securitiesName = null;             //֤ȯ/��Ʒ����

	private String remark = null;                     //��ע	

	/**
	 * @return Returns the accountCode.
	 */
	public String getAccountCode() {
		return accountCode;
	}
	/**
	 * @param accountCode The accountCode to set.
	 */
	public void setAccountCode(String accountCode) {
		putUsedField("accountCode", accountCode);
		this.accountCode = accountCode;
	}
	/**
	 * @return Returns the accountTypeId.
	 */
	public long getAccountTypeId() {
		return accountTypeId;
	}
	/**
	 * @param accountTypeId The accountTypeId to set.
	 */
	public void setAccountTypeId(long accountTypeId) {
		putUsedField("accountTypeId", accountTypeId);
		this.accountTypeId = accountTypeId;
	}
	/**
	 * @return Returns the amountDirection.
	 */
	public long getAmountDirection() {
		return amountDirection;
	}
	/**
	 * @param amountDirection The amountDirection to set.
	 */
	public void setAmountDirection(long amountDirection) {
		putUsedField("amountDirection", amountDirection);
		this.amountDirection = amountDirection;
	}
	/**
	 * @return Returns the clientCode.
	 */
	public String getClientCode() {
		return clientCode;
	}
	/**
	 * @param clientCode The clientCode to set.
	 */
	public void setClientCode(String clientCode) {
		putUsedField("clientCode", clientCode);
		this.clientCode = clientCode;
	}
	/**
	 * @return Returns the contractCode.
	 */
	public String getContractCode() {
		return contractCode;
	}
	/**
	 * @param contractCode The contractCode to set.
	 */
	public void setContractCode(String contractCode) {
		putUsedField("contractCode", contractCode);
		this.contractCode = contractCode;
	}
	/**
	 * @return Returns the counterpartName.
	 */
	public String getCounterpartName() {
		return counterpartName;
	}
	/**
	 * @param counterpartName The counterpartName to set.
	 */
	public void setCounterpartName(String counterpartName) {
		putUsedField("counterpartName", counterpartName);
		this.counterpartName = counterpartName;
	}
	/**
	 * @return Returns the currencyID.
	 */
	public long getCurrencyID() {
		return currencyID;
	}
	/**
	 * @param currencyID The currencyID to set.
	 */
	public void setCurrencyID(long currencyID) {
		putUsedField("currencyID", currencyID);
		this.currencyID = currencyID;
	}
	/**
	 * @return Returns the executeDate.
	 */
	public Timestamp getExecuteDate() {
		return executeDate;
	}
	/**
	 * @param executeDate The executeDate to set.
	 */
	public void setExecuteDate(Timestamp executeDate) {
		putUsedField("executeDate", executeDate);
		this.executeDate = executeDate;
	}
	/**
	 * @return Returns the glSubjectCode.
	 */
	public String getGlSubjectCode() {
		return glSubjectCode;
	}
	/**
	 * @param glSubjectCode The glSubjectCode to set.
	 */
	public void setGlSubjectCode(String glSubjectCode) {
		putUsedField("glSubjectCode", glSubjectCode);
		this.glSubjectCode = glSubjectCode;
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
	 * @return Returns the moduleID.
	 */
	public long getModuleID() {
		return moduleID;
	}
	/**
	 * @param moduleID The moduleID to set.
	 */
	public void setModuleID(long moduleID) {
		putUsedField("moduleID", moduleID);
		this.moduleID = moduleID;
	}
	/**
	 * @return Returns the officeID.
	 */
	public long getOfficeID() {
		return officeID;
	}
	/**
	 * @param officeID The officeID to set.
	 */
	public void setOfficeID(long officeID) {
		putUsedField("officeID", officeID);
		this.officeID = officeID;
	}
	/**
	 * @return Returns the payFormCode.
	 */
	public String getPayFormCode() {
		return payFormCode;
	}
	/**
	 * @param payFormCode The payFormCode to set.
	 */
	public void setPayFormCode(String payFormCode) {
		putUsedField("payFormCode", payFormCode);
		this.payFormCode = payFormCode;
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
	 * @return Returns the securitiesName.
	 */
	public String getSecuritiesName() {
		return securitiesName;
	}
	/**
	 * @param securitiesName The securitiesName to set.
	 */
	public void setSecuritiesName(String securitiesName) {
		putUsedField("securitiesName", securitiesName);
		this.securitiesName = securitiesName;
	}
	/**
	 * @return Returns the transactionDate.
	 */
	public Timestamp getTransactionDate() {
		return transactionDate;
	}
	/**
	 * @param transactionDate The transactionDate to set.
	 */
	public void setTransactionDate(Timestamp transactionDate) {
		putUsedField("transactionDate", transactionDate);
		this.transactionDate = transactionDate;
	}
	/**
	 * @return Returns the transactionName.
	 */
	public String getTransactionName() {
		return transactionName;
	}
	/**
	 * @param transactionName The transactionName to set.
	 */
	public void setTransactionName(String transactionName) {
		putUsedField("transactionName", transactionName);
		this.transactionName = transactionName;
	}
}
