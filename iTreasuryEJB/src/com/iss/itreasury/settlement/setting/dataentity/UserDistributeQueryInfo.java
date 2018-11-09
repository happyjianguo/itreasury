/**
 * 
 */
package com.iss.itreasury.settlement.setting.dataentity;

import java.sql.Date;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;

/**
 * @author zyzhu
 * 
 */
public class UserDistributeQueryInfo extends SettlementBaseDataEntity {

	private long id =-1;
	private long userIDFrom = -1;   //客户id 从
	private String userNoFrom = "";  //客户编号 从
	private long userIDTo = -1;   //客户id 至
	private String userNoTo = "";  //客户编号 至
	private long isDirect = -1;  //是否为直接下级
	private long hasAuthority = -1;  //是否有权限
	private long officeId=-1;    //办事处id
	private long currencyId=-1;	 //币种
	public long getOfficeId() {
		return officeId;
	}
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
	}
	public long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}
	public long getUserIDFrom() {
		return userIDFrom;
	}
	public void setUserIDFrom(long userIDFrom) {
		this.userIDFrom = userIDFrom;
		putUsedField("userIDFrom", userIDFrom);
	}
	public long getUserIDTo() {
		return userIDTo;
	}
	public void setUserIDTo(long userIDTo) {
		this.userIDTo = userIDTo;
		putUsedField("userIDTo", userIDTo);
	}
	public long getIsDirect() {
		return isDirect;
	}
	public void setIsDirect(long isDirect) {
		this.isDirect = isDirect;
		putUsedField("isDirect", isDirect);
	}
	public long getHasAuthority() {
		return hasAuthority;
	}
	public void setHasAuthority(long hasAuthority) {
		this.hasAuthority = hasAuthority;
		putUsedField("hasAuthority", hasAuthority);
	}
	public String getUserNoFrom() {
		return userNoFrom;
	}
	public void setUserNoFrom(String userNoFrom) {
		this.userNoFrom = userNoFrom;
		putUsedField("userNoFrom", userNoFrom);
	}
	public String getUserNoTo() {
		return userNoTo;
	}
	public void setUserNoTo(String userNoTo) {
		this.userNoTo = userNoTo;
		putUsedField("userNoTo", userNoTo);
	}
	
}
