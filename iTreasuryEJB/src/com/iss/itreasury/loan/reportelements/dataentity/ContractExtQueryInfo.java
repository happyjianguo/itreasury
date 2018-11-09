/**
 * @author XiaoQiao
 * @Date   2012-6-14
 */
package com.iss.itreasury.loan.reportelements.dataentity;


public class ContractExtQueryInfo {

	private long 	id;
	private String 	loanName;				//贷款类型
	private String 	sContractCode;			//合同编号
	private String 	clientName;				//借款单位
	private double 	mLoanAmount;		//贷款金额
	private double 	ninterValNum;			//期限
	private String 	sDetailremarks;			//借款明细备注
	private String 	sManagerName;			//经办客户经理及助理
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
