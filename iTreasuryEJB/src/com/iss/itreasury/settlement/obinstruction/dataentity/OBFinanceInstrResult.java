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
	
	private long recevieCount = -1; //�����ձ���
	private long successCount = -1; //����ɹ�����
	private long failedCount = -1; //ʧ�ܱ���
	
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
		
		actionMessage.addMessage("�ɹ�����" + this.successCount + "��ָ��");
		actionMessage.addMessage("ʧ�ܴ���" + this.failedCount + "��ָ��");
		
		Set keySet = this.failedMap.keySet();
		
		String[] strTransNos = (String[]) keySet.toArray(new String[0]);
		for(int i=0; i<strTransNos.length; i++){
			actionMessage.addMessage("[" + strTransNos[i] + "]��" + this.failedMap.get(strTransNos[i]));
		} 		
	}
}
