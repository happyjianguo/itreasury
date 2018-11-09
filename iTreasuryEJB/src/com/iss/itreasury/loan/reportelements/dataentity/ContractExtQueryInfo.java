/**
 * @author XiaoQiao
 * @Date   2012-6-14
 */
package com.iss.itreasury.loan.reportelements.dataentity;


public class ContractExtQueryInfo {

	private long 	id;
	private String 	loanName;				//��������
	private String 	sContractCode;			//��ͬ���
	private String 	clientName;				//��λ
	private double 	mLoanAmount;		//������
	private double 	ninterValNum;			//����
	private String 	sDetailremarks;			//�����ϸ��ע
	private String 	sManagerName;			//����ͻ���������
	private long 	Order;
	private long 	Desc;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLoanName() {
		return loanName;
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	public String getSContractCode() {
		return sContractCode;
	}
	public void setSContractCode(String contractCode) {
		sContractCode = contractCode;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getSDetailremarks() {
		return sDetailremarks;
	}
	public void setSDetailremarks(String detailremarks) {
		sDetailremarks = detailremarks;
	}
	public String getSManagerName() {
		return sManagerName;
	}
	public void setSManagerName(String managerName) {
		sManagerName = managerName;
	}
	public double getMLoanAmount() {
		return mLoanAmount;
	}
	public void setMLoanAmount(double loanAmount) {
		mLoanAmount = loanAmount;
	}
	public double getNinterValNum() {
		return ninterValNum;
	}
	public void setNinterValNum(double ninterValNum) {
		this.ninterValNum = ninterValNum;
	}
	public long getOrder() {
		return Order;
	}
	public void setOrder(long order) {
		Order = order;
	}
	public long getDesc() {
		return Desc;
	}
	public void setDesc(long desc) {
		Desc = desc;
	}
	
	
}
