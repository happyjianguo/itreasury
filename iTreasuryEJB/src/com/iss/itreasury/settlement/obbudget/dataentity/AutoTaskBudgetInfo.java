/*
 * Created on 2007-5-22
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.obbudget.dataentity;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

import java.sql.Timestamp;

/**
 * @author lenovo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AutoTaskBudgetInfo extends ITreasuryBaseDataEntity 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1693544111191645135L;
	
	private long id = -1;    //���ID
	private long accountId = -1;   //�ÿ��˻���
	private long branchId = -1;    //�����к�
	private long status = -1;      //״̬
	private long officeId = -1;    //���´�
	private long currencyId = -1;  //����
	private long inputUserId = -1; //¼����
	private Timestamp inputDate = null; //¼��ʱ��
	
	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
		putUsedField("accountId", accountId);
	}
	public long getBranchId() {
		return branchId;
	}
	public void setBranchId(long branchId) {
		this.branchId = branchId;
		putUsedField("branchId", branchId);
	}
	public long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
		putUsedField("currencyId", currencyId);
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
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
	public long getOfficeId() {
		return officeId;
	}
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
		putUsedField("officeId", officeId);
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
		putUsedField("status", status);
	}
	
	
   
}
