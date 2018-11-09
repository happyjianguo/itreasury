package com.iss.itreasury.report.dateentity;

/**
 * 贷款合同及相关结息计划的设置信息
 * @author yunzhou
 * @date 2011-01-17
 *
 */
public class LoanContractInfo {

	private long lContractId = -1;			//贷款合同id
	
	private String strContractCode = "";	//贷款合同编号
		
	private long lContractSubTypeId = -1;	//贷款合同子类型
	
	private long lContractTypeId = -1;	//贷款合同类型
	
	private long lCleartype = -1;			//贷款合同结息方式，2按月，3按季
	
	private long lCleartime = -1;			//贷款合同结息日期
	
	

	public long getLContractTypeId() {
		return lContractTypeId;
	}

	public void setLContractTypeId(long contractTypeId) {
		lContractTypeId = contractTypeId;
	}

	public LoanContractInfo() {
		
	}

	public long getLCleartime() {
		return lCleartime;
	}

	public void setLCleartime(long cleartime) {
		lCleartime = cleartime;
	}

	public long getLCleartype() {
		return lCleartype;
	}

	public void setLCleartype(long cleartype) {
		lCleartype = cleartype;
	}

	public long getLContractId() {
		return lContractId;
	}

	public void setLContractId(long contractId) {
		lContractId = contractId;
	}

	public long getLContractSubTypeId() {
		return lContractSubTypeId;
	}

	public void setLContractSubTypeId(long contractSubTypeId) {
		lContractSubTypeId = contractSubTypeId;
	}

	public String getStrContractCode() {
		return strContractCode;
	}

	public void setStrContractCode(String strContractCode) {
		this.strContractCode = strContractCode;
	}
	
	
}
