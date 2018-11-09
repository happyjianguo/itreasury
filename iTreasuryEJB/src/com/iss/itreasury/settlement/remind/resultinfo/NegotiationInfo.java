/**
 * 
 */
package com.iss.itreasury.settlement.remind.resultinfo;

import java.io.SerializablePermission;
import java.sql.Timestamp;

/**
 * @author zyzhu
 * 
 */
public class NegotiationInfo implements java.io.Serializable {

	// 定义成员变量
	private long accountId = -1;		//账户ID

	private long clientId = -1;		//客户ID

	private Timestamp endDate = null;		//到期日期

	/**
	 * 账户ID
	 * @return 返回 accountId。
	 */
	public long getAccountId() {
		return accountId;
	}

	/**
	 * 账户ID
	 * @param accountId 要设置的 accountId。
	 */
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	/**
	 * 客户ID
	 * @return 返回 clientId。
	 */
	public long getClientId() {
		return clientId;
	}

	/**
	 * 客户ID
	 * @param clientId 要设置的 clientId。
	 */
	public void setClientId(long clientId) {
		this.clientId = clientId;
	}

	/**
	 * 到期日期
	 * @return 返回 endDate。
	 */
	public Timestamp getEndDate() {
		return endDate;
	}

	/**
	 * 到期日期
	 * @param endDate 要设置的 endDate。
	 */
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

}
