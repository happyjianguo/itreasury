/*
 * Credate date 2007/06/21
 */
package com.iss.itreasury.ebank.obsetremind.dataentity;

/**
 * author leiyang
 * Modify by leiyang date 2007/06/21
 */
import java.sql.Timestamp;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class OB_OperationReminderInfo extends ITreasuryBaseDataEntity {

	private long Id = -1;  //ID
	private long officeId = -1;  //���´�ID
	private long currencyId = -1;  //����ID
	private long clientId = -1;  //�ͻ�ID
	private long operationId = -1;  //��Ҫ���ѵ�ҵ������
	private long operationFate = 0;  //������������
	private long inputUserId;  //¼���û�ID
	private Timestamp inputDate;  //¼��ʱ��
	private Timestamp modifyDate;  //�޸�ʱ��
	private long statusId;  //״̬ID
	

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}
	
	public long getClientId() {
		return clientId;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
	}

	public long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
	}

	public Timestamp getInputDate() {
		return inputDate;
	}

	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
	}

	public long getInputUserId() {
		return inputUserId;
	}

	public void setInputUserId(long inputUserId) {
		this.inputUserId = inputUserId;
	}

	public Timestamp getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Timestamp modifyDate) {
		this.modifyDate = modifyDate;
	}

	public long getOfficeId() {
		return officeId;
	}

	public void setOfficeId(long officeId) {
		this.officeId = officeId;
	}

	public long getOperationFate() {
		return operationFate;
	}

	public void setOperationFate(long operationFate) {
		this.operationFate = operationFate;
	}

	public long getOperationId() {
		return operationId;
	}

	public void setOperationId(long operationId) {
		this.operationId = operationId;
	}

	public long getStatusId() {
		return statusId;
	}

	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}
}
