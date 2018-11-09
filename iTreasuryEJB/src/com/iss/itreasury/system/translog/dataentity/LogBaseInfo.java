/**
 * 
 */
package com.iss.itreasury.system.translog.dataentity;

/**
 * @author Administrator
 *
 */
public class LogBaseInfo implements ILogTemplate {
	
	
	public String getRemark() {
		return "操作成功";
	}
	
	public String getRemark(long TransType) {
		return "操作成功"+TransType;
	}
	
	public String getTranstype()
	{
		return "未知交易";
	}

	public void setOriginObjInfo(Object objInfo) {
		//to do nothing
	}

	public void setActionType(long actionType) {
		// to do nothing
		
	}

}
