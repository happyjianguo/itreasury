package com.iss.itreasury.settlement.integration.client.info;

import java.io.Serializable;

public class SettResultInfo implements Serializable {
	private String transNo= null;	//���㽻�׺�
	private long   status = -1;		//���㽻��״̬��� 1����Ч 0����Ч
	private String message = null;	//���㽻��״̬���� 
	private String moduleType = null;  //ģ������
	
	public String getTransNo() {
		return transNo;
	}
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getModuleType() {
		return moduleType;
	}
	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}

}
