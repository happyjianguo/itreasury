/*
 * Created on 2006-10-16
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.loan.capitalprove.dataentity;

import java.util.Date;

import com.iss.itreasury.util.DataFormat;

/**
 * @author yyhe
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CapitalProveInfo implements java.io.Serializable{
	
	private long id = -1; 
	private long officeId = -1;
	private String code = "";//�ڲ���ˮ��
	private long conferContractNo = 0;//���ź�ͬID
	private String contractNo = "";
	private String applyClient = "";//���뵥λ���
	private String applyClientName = "";//���뵥λ����
	private String certificateBank ="";//��֤����
	private String capitalProveID = "";//����֤�����
	private String beneficiaryName = ""; //����������
	private String projectName = ""; //��Ŀ����
	private long applyMonth = -1; //��������
	private String startDate = ""; //��ʼ����
	private String endDate = ""; //��������
	private double charge = -1; //������
	private long statusID = -1; //״̬
	private String applyPurpose = ""; //������;
	private String remark = ""; //��ע
	
    //Ϊ�˲�ѯ���������
	String fromStartDate ="";//��ʼ������
	String endStartDate ="";//��ʼ���ڵ�
	
	String fromEndDate ="";//����������
	String endEndDate ="";//�������ڵ�	
	
	
	
	public String getApplyClientName() {
		return applyClientName;
	}
	public void setApplyClientName(String applyClientName) {
		this.applyClientName = applyClientName;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public long getOfficeId() {
		return officeId;
	}
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
	}
	public String getApplyClient() {
		return applyClient;
	}
	public void setApplyClient(String applyClient) {
		this.applyClient = applyClient;
	}
	public long getApplyMonth() {
		return applyMonth;
	}
	public void setApplyMonth(long applyMonth) {
		this.applyMonth = applyMonth;
	}
	public String getApplyPurpose() {
		return applyPurpose;
	}
	public void setApplyPurpose(String applyPurpose) {
		this.applyPurpose = applyPurpose;
	}
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	public String getCapitalProveID() {
		return capitalProveID;
	}
	public void setCapitalProveID(String capitalProveID) {
		this.capitalProveID = capitalProveID;
	}
	public String getCertificateBank() {
		return certificateBank;
	}
	public void setCertificateBank(String certificateBank) {
		this.certificateBank = certificateBank;
	}
	public double getCharge() {
		return charge;
	}
	public void setCharge(double charge) {
		this.charge = charge;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public long getConferContractNo() {
		return conferContractNo;
	}
	public void setConferContractNo(long conferContractNo) {
		this.conferContractNo = conferContractNo;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getEndEndDate() {
		return endEndDate;
	}
	public void setEndEndDate(String endEndDate) {
		this.endEndDate = endEndDate;
	}
	public String getEndStartDate() {
		return endStartDate;
	}
	public void setEndStartDate(String endStartDate) {
		this.endStartDate = endStartDate;
	}
	public String getFromEndDate() {
		return fromEndDate;
	}
	public void setFromEndDate(String fromEndDate) {
		this.fromEndDate = fromEndDate;
	}
	public String getFromStartDate() {
		return fromStartDate;
	}
	public void setFromStartDate(String fromStartDate) {
		this.fromStartDate = fromStartDate;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public long getStatusID() {
		return statusID;
	}
	public void setStatusID(long statusID) {
		this.statusID = statusID;
	}
	public void setStartDate(Date startDate) {
		this.startDate = DataFormat.formatDate(startDate,1);
	}
	public void setEndDate(Date endDate) {
		this.endDate = DataFormat.formatDate(endDate,1);
	}

}
