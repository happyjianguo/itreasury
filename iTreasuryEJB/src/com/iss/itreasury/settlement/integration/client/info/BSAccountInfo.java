package com.iss.itreasury.settlement.integration.client.info;

public class BSAccountInfo implements java.io.Serializable{
	private String referenceCode	= "";	//������	
	private String branchCode	= "";	//�������˻�
	
	private AccountTransInfo[] transInfos = null;	//������Ϣ
		
	public String getBranchCode()
	{
		return branchCode;
	}
	public void setBranchCode(String accountNO)
	{
		this.branchCode = accountNO;
	}
	public String getReferenceCode() {
		return referenceCode;
	}
	public void setReferenceCode(String referenceCode) {
		this.referenceCode = referenceCode;
	}
	public AccountTransInfo[] getTransInfos() {
		return transInfos;
	}
	public void setTransInfos(AccountTransInfo[] transInfos) {
		this.transInfos = transInfos;
	}		
}
