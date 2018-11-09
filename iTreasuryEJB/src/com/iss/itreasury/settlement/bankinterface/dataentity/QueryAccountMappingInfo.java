/*
 * Created on 2004-11-29
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.bankinterface.dataentity;
import java.io.Serializable;
import java.sql.Timestamp;
/**
 * ���Ա��嵥���ݶ���
 * 
 * @author ytcui
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class QueryAccountMappingInfo implements Serializable {

	private String bankAccountNo = null;//�����˺�
	private String subjectCode = null;//��Ŀ����
	private long withinAccountID = -1;//�ڲ��˻�ID
	private Timestamp startDate = null; //��ʼ����
	private Timestamp endDate = null; //��������
	private long transDirection = -1;//���׷���
	private long statusID = -1;//����״̬	
	private long mappingID = -1;//ƥ���
	
	/**
	 * @return Returns the bankAccountNo.
	 */
	public String getBankAccountNo() {
		return bankAccountNo;
	}
	/**
	 * @param bankAccountNo The bankAccountNo to set.
	 */
	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}
	/**
	 * @return Returns the endDate.
	 */
	public Timestamp getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate The endDate to set.
	 */
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return Returns the startDate.
	 */
	public Timestamp getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate The startDate to set.
	 */
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return Returns the statusID.
	 */
	public long getStatusID() {
		return statusID;
	}
	/**
	 * @param statusID The statusID to set.
	 */
	public void setStatusID(long statusID) {
		this.statusID = statusID;
	}
	/**
	 * @return Returns the subjectCode.
	 */
	public String getSubjectCode() {
		return subjectCode;
	}
	/**
	 * @param subjectCode The subjectCode to set.
	 */
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	/**
	 * @return Returns the mappingID.
	 */
	public long getMappingID() {
		return mappingID;
	}
	/**
	 * @param mappingID The mappingID to set.
	 */
	public void setMappingID(long mappingID) {
		this.mappingID = mappingID;
	}
	/**
	 * @return Returns the transDirection.
	 */
	public long getTransDirection() {
		return transDirection;
	}
	/**
	 * @param transDirection The transDirection to set.
	 */
	public void setTransDirection(long transDirection) {
		this.transDirection = transDirection;
	}
	/**
	 * @return Returns the withinAccountID.
	 */
	public long getWithinAccountID() {
		return withinAccountID;
	}
	/**
	 * @param withinAccountID The withinAccountID to set.
	 */
	public void setWithinAccountID(long withinAccountID) {
		this.withinAccountID = withinAccountID;
	}
	
}
