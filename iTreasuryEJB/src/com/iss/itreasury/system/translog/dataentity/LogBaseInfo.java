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
		return "�����ɹ�";
	}
	
	public String getRemark(long TransType) {
		return "�����ɹ�"+TransType;
	}
	
	public String getTranstype()
	{
		return "δ֪����";
	}

	public void setOriginObjInfo(Object objInfo) {
		//to do nothing
	}

	public void setActionType(long actionType) {
		// to do nothing
		
	}

}
