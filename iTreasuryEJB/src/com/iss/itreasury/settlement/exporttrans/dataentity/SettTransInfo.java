package com.iss.itreasury.settlement.exporttrans.dataentity;

import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;

/**
 * @author lenovo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SettTransInfo {

	private TransCurrentDepositInfo transCurrentDepositInfo = null;  //��̨������ϸ��Ϣ
	private ExportTransInfo exportTransInfo = null;   //�����������׼�¼��Ϣ
	
	private long id = -1;    //��̨�������ϢID
	private String branchName = "";      //����������
	private String branchAccountName = "";  //�������˻�����
	private String branchAccountNo = "";    //�������˻����
	private String strPayClientName = "";   //�������
	
	
	public String getBranchAccountName() {
		return branchAccountName;
	}
	public void setBranchAccountName(String branchAccountName) {
		this.branchAccountName = branchAccountName;
	}
	public String getBranchAccountNo() {
		return branchAccountNo;
	}
	public void setBranchAccountNo(String branchAccountNo) {
		this.branchAccountNo = branchAccountNo;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getStrPayClientName() {
		return strPayClientName;
	}
	public void setStrPayClientName(String strPayClientName) {
		this.strPayClientName = strPayClientName;
	}
	public ExportTransInfo getExportTransInfo() {
		return exportTransInfo;
	}
	public void setExportTransInfo(ExportTransInfo exportTransInfo) {
		this.exportTransInfo = exportTransInfo;
	}
	public TransCurrentDepositInfo getTransCurrentDepositInfo() {
		return transCurrentDepositInfo;
	}
	public void setTransCurrentDepositInfo(
			TransCurrentDepositInfo transCurrentDepositInfo) {
		this.transCurrentDepositInfo = transCurrentDepositInfo;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
}
