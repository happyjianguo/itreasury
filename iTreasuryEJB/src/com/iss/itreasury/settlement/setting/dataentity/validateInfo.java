package com.iss.itreasury.settlement.setting.dataentity;

public class validateInfo {
	private String operationName = "";
	private long operationNum = -1;
	
	//1.�˻� 2.ҵ�� 3.�ͻ�
	private long operationType = -1;
	
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public long getOperationNum() {
		return operationNum;
	}
	public void setOperationNum(long operationNum) {
		this.operationNum = operationNum;
	}
	public long getOperationType() {
		return operationType;
	}
	public void setOperationType(long operationType) {
		this.operationType = operationType;
	}
}
