/*
 * Created on 2007-6-18
*/
package com.iss.itreasury.settlement.interest.dataentity;

import java.io.Serializable;

/**
 * ��������Ϣ����ʱ�������ѯ����
 * @author leiyang
 *
 */
public class InterestCalculationModeQueryEntity  implements Serializable {
	private long accountId = -1;  //���ʻ�ID
	private long subAccountId = -1;  //���ʻ�ID
	
	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public long getSubAccountId() {
		return subAccountId;
	}
	public void setSubAccountId(long subAccountId) {
		this.subAccountId = subAccountId;
	}
}
