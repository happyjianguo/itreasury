/*
 * Created on 2007-6-11
 *
 * 这是一个AccountInfo类的扩展类
 */
package com.iss.itreasury.settlement.account.dataentity;

import java.io.Serializable;
import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;

/**
 * @author leiyang
 *
 * 这是一个AccountInfo类的扩展类
 */
public class AccountExtendInfo extends SettlementBaseDataEntity implements Serializable {
	private long AccountExtendID = -1; // 扩展ID
	private long AccountID = -1; // 账户ID
	private long IsSoft = 0; // 是否柔性控制
	private long Status = -1; // 账户状态
	
	/**
	 * Returns the AccountExtendID.
	 * @return long
	 */
	public long getAccountExtendID() {
		return AccountExtendID;
	}
	
	/**
	 * set the AccountExtendID.
	 * @param accountExtendID
	 */
	public void setAccountExtendID(long accountExtendID) {
		AccountExtendID = accountExtendID;
	}
	
	/**
	 * Returns the AccountID.
	 * @return long
	 */
	public long getAccountID() {
		return AccountID;
	}
	
	/**
	 * set the AccountID.
	 * @param accountID
	 */
	public void setAccountID(long accountID) {
		AccountID = accountID;
	}
	
	/**
	 * Returns the IsSoft.
	 * @return long
	 */
	public long getIsSoft() {
		return IsSoft;
	}
	
	/**
	 * set the IsSoft.
	 * @param isSoft
	 */
	public void setIsSoft(long isSoft) {
		IsSoft = isSoft;
	}
	
	/**
	 * Returns the Status
	 * @return long
	 */
	public long getStatus() {
		return Status;
	}
	
	/**
	 * set ths Status
	 * @param status
	 */
	public void setStatus(long status) {
		Status = status;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer result = new StringBuffer(255);
		result.append("AccountExtendID=");
		result.append(this.getAccountExtendID());
		result.append("\r\n");
		result.append("AccountID=");
		result.append(this.getAccountID());
		result.append("\r\n");
		result.append("IsSoft=");
		result.append(this.getIsSoft());
		result.append("\r\n");
		result.append("Status=");
		result.append(this.getStatus());
		result.append("\r\n");
		return result.toString();
	}
}
