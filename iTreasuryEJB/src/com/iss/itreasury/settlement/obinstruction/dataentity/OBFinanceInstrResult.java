/**
 * 
 */
package com.iss.itreasury.settlement.obinstruction.dataentity;

import java.util.HashMap;
import java.util.Set;

import com.iss.itreasury.dataentity.BaseDataEntity;
import com.iss.itreasury.util.ActionMessages;

/**
 * @author issuser
 *
 */
public class OBFinanceInstrResult extends BaseDataEntity {
	
	private long recevieCount = -1; //共接收笔数
	private long successCount = -1; //处理成功笔数
	private long failedCount = -1; //失败笔数
	
	public HashMap failedMap = new HashMap();

	public long getFailedCount() {
		return failedCount;
	}

	public void setFailedCount(long failedCount) {
		this.failedCount = failedCount;
	}

	public long getRecevieCount() {
		return recevieCount;
	}

	public void setRecevieCount(long recevieCount) {
		this.recevieCount = recevieCount;
	}

	public long getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(long successCount) {
		this.successCount = successCount;
	}
	
	public void addFailedMessage(String transNo, String strErrMessage){
		failedMap.put(transNo, strErrMessage);
	}
	
	public void exportResultMessage(ActionMessages actionMessage){
		
		actionMessage.addMessage("成功处理" + this.successCount + "笔指令");
		actionMessage.addMessage("失败处理" + this.failedCount + "笔指令");
		
		Set keySet = this.failedMap.keySet();
		
		String[] strTransNos = (String[]) keySet.toArray(new String[0]);
		for(int i=0; i<strTransNos.length; i++){
			actionMessage.addMessage("[" + strTransNos[i] + "]，" + this.failedMap.get(strTransNos[i]));
		} 		
	}
}
