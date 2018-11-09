package com.iss.itreasury.project.gzbfcl.loan.dataentity;

import java.sql.Timestamp;
import com.iss.itreasury.loan.base.LoanBaseDataEntity;
/**
 * 
 * @author sunjing
 * ���ż�֤ҵ��ʵ����
 *
 */
public class CreditAuthenticationInfo extends LoanBaseDataEntity 
{
	private long Id = -1 ;
	private String TransNo = "" ; //ҵ����
	private long OfficeId = -1 ; //���´�ID
	private long CurrencyId = -1 ; //����
	private long ClientId =  -1 ; //�ͻ�ID
	private String TransType = "" ; //ҵ������
	private double CommissionAmount = 0 ; //�����ѽ��
	private long InputUserId = -1 ; //¼����ID
	private Timestamp InputTime = null ; //¼��ʱ��
	private long StatusId = -1 ; //״̬
	private String TransContent = "" ; //ҵ������
	private	long ModifyUserId = -1 ; //�޸���ID
	private Timestamp ModifyTime = null ; //�޸�ʱ��
	private String clientname = null ; //�ͻ�����
	private String inputuser = null ; //¼��������
	private String ModifyUser = null ;//�޸�������
	
	/**
	 * @return the modifyUser
	 */
	public String getModifyUser() {
		return ModifyUser;
	}
	/**
	 * @param modifyUser the modifyUser to set
	 */
	public void setModifyUser(String modifyUser) {
		ModifyUser = modifyUser;
		putUsedField("modifyUser", modifyUser);
	}
	/**
	 * @return the clientname
	 */
	public String getClientname() {
		return clientname;
	}
	/**
	 * @param clientname the clientname to set
	 */
	public void setClientname(String clientname) {
		this.clientname = clientname;
		putUsedField("clientname", clientname);
	}
	/**
	 * @return the inputuser
	 */
	public String getInputuser() {
		return inputuser;
	}
	/**
	 * @param inputuser the inputuser to set
	 */
	public void setInputuser(String inputuser) {
		this.inputuser = inputuser;
		putUsedField("inputuser", inputuser);
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return Id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		Id = id;
		putUsedField("id", id);
	}
	/**
	 * @return the transNo
	 */
	public String getTransNo() {
		return TransNo;
	}
	/**
	 * @param transNo the transNo to set
	 */
	public void setTransNo(String transNo) {
		TransNo = transNo;
		putUsedField("transNo", transNo);
	}
	/**
	 * @return the officeId
	 */
	public long getOfficeId() {
		return OfficeId;
	}
	/**
	 * @param officeId the officeId to set
	 */
	public void setOfficeId(long officeId) {
		OfficeId = officeId;
		putUsedField("officeId", officeId);
	}
	/**
	 * @return the currencyId
	 */
	public long getCurrencyId() {
		return CurrencyId;
	}
	/**
	 * @param currencyId the currencyId to set
	 */
	public void setCurrencyId(long currencyId) {
		CurrencyId = currencyId;
		putUsedField("currencyId", currencyId);
	}
	/**
	 * @return the clientId
	 */
	public long getClientId() {
		return ClientId;
	}
	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(long clientId) {
		ClientId = clientId;
		putUsedField("clientId", clientId);
	}
	/**
	 * @return the transType
	 */
	public String getTransType() {
		return TransType;
	}
	/**
	 * @param transType the transType to set
	 */
	public void setTransType(String transType) {
		TransType = transType;
		putUsedField("transType", transType);
	}
	/**
	 * @return the commissionAmount
	 */
	public double getCommissionAmount() {
		return CommissionAmount;
	}
	/**
	 * @param commissionAmount the commissionAmount to set
	 */
	public void setCommissionAmount(double commissionAmount) {
		CommissionAmount = commissionAmount;
		putUsedField("commissionAmount", commissionAmount);
	}
	/**
	 * @return the inputUserId
	 */
	public long getInputUserId() {
		return InputUserId;
	}
	/**
	 * @param inputUserId the inputUserId to set
	 */
	public void setInputUserId(long inputUserId) {
		InputUserId = inputUserId;
		putUsedField("inputUserId", inputUserId);
	}
	/**
	 * @return the inputTime
	 */
	public Timestamp getInputTime() {
		return InputTime;
	}
	/**
	 * @param inputTime the inputTime to set
	 */
	public void setInputTime(Timestamp inputTime) {
		InputTime = inputTime;
		putUsedField("inputTime", inputTime);
	}
	/**
	 * @return the statusId
	 */
	public long getStatusId() {
		return StatusId;
	}
	/**
	 * @param statusId the statusId to set
	 */
	public void setStatusId(long statusId) {
		StatusId = statusId;
		putUsedField("statusId", statusId);
	}
	/**
	 * @return the transContent
	 */
	public String getTransContent() {
		return TransContent;
	}
	/**
	 * @param transContent the transContent to set
	 */
	public void setTransContent(String transContent) {
		TransContent = transContent;
		putUsedField("transContent", transContent);
	}
	/**
	 * @return the modifyUserId
	 */
	public long getModifyUserId() {
		return ModifyUserId;
	}
	/**
	 * @param modifyUserId the modifyUserId to set
	 */
	public void setModifyUserId(long modifyUserId) {
		ModifyUserId = modifyUserId;
		putUsedField("modifyUserId", modifyUserId);
	}
	/**
	 * @return the modifyTime
	 */
	public Timestamp getModifyTime() {
		return ModifyTime;
	}
	/**
	 * @param modifyTime the modifyTime to set
	 */
	public void setModifyTime(Timestamp modifyTime) {
		ModifyTime = modifyTime;
		putUsedField("modifyTime", modifyTime);
	}
	
	
	
	
	
}
