/*
 * Created on 2008-2-18
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.exporttrans.dataentity;

import com.iss.itreasury.ebank.obsystem.dataentity.ClientCapInfo;
import com.iss.itreasury.settlement.obinstruction.dataentity.OBFinanceInfo;

/**
 * @author lenovo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ObTransInfo {

	private OBFinanceInfo obFinanceInfo = null;      //����������Ϣ��ϸ
	private ExportTransInfo exportTransInfo = null;   //�����������׼�¼��Ϣ
	private ClientCapInfo clientcapinfo = null;       //�����տ��Ϣ
	
	private String branchName = "";      //����������
	private String branchAccountName = "";  //�������˻�����
	private String branchAccountNo = "";    //�������˻����
	private String strPayClientName = "";   //�������
	
	private long clientID = -1;    //�ͻ�ID
	private long currencyID = -1;  //����ID
	
	public long getClientID() {
		return clientID;
	}
	public void setClientID(long clientID) {
		this.clientID = clientID;
	}
	public long getCurrencyID() {
		return currencyID;
	}
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}
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
	public ExportTransInfo getExportTransInfo() {
		return exportTransInfo;
	}
	public void setExportTransInfo(ExportTransInfo exportTransInfo) {
		this.exportTransInfo = exportTransInfo;
	}
	public OBFinanceInfo getObFinanceInfo() {
		return obFinanceInfo;
	}
	public void setObFinanceInfo(OBFinanceInfo obFinanceInfo) {
		this.obFinanceInfo = obFinanceInfo;
	}
	public String getStrPayClientName() {
		return strPayClientName;
	}
	public void setStrPayClientName(String strPayClientName) {
		this.strPayClientName = strPayClientName;
	}
	public ClientCapInfo getClientcapinfo() {
		return clientcapinfo;
	}
	public void setClientcapinfo(ClientCapInfo clientcapinfo) {
		this.clientcapinfo = clientcapinfo;
	}
}
