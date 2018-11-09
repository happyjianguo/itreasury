package com.iss.itreasury.project.gzbfcl.loan.dataentity;

import java.sql.Timestamp;
import com.iss.itreasury.loan.base.LoanBaseDataEntity;

/**
 * 
 * @author sunjing
 * 资信鉴证查询实体类
 *
 */
public class CreditAuthenticationQueryInfo extends LoanBaseDataEntity
{
	private long ClientId =  -1 ; //客户ID
	private long ClientIdFrom = -1 ; //客户ID开始
	private long ClientIdTo = -1 ; //客户ID结束
	private Timestamp InputTime = null ; //录入时间
	private Timestamp InputTimeFrom = null ; //录入时间开始
	private Timestamp InputTimeTo = null ; //录入时间结束
	private long StatusId = -1 ; //状态
	private long OfficeId = -1 ; //办事处ID
	private long CurrencyId = -1 ; //币种
	/**
	 * @return the clientId
	 */
	public long getClientId() {
		return ClientId;
	}
	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(long clientId) {
		ClientId = clientId;
		putUsedField("clientId", clientId);
	}
	/**
	 * @return the clientIdFrom
	 */
	public long getClientIdFrom() {
		return ClientIdFrom;
	}
	/**
	 * @param clientIdFrom the clientIdFrom to set
	 */
	public void setClientIdFrom(long clientIdFrom) {
		ClientIdFrom = clientIdFrom;
		putUsedField("clientIdFrom", clientIdFrom);
	}
	/**
	 * @return the clientIdTo
	 */
	public long getClientIdTo() {
		return ClientIdTo;
	}
	/**
	 * @param clientIdTo the clientIdTo to set
	 */
	public void setClientIdTo(long clientIdTo) {
		ClientIdTo = clientIdTo;
		putUsedField("clientIdTo", clientIdTo);
	}
	/**
	 * @return the inputTime
	 */
	public Timestamp getInputTime() {
		return InputTime;
	}
	/**
	 * @param inputTime the inputTime to set
	 */
	public void setInputTime(Timestamp inputTime) {
		InputTime = inputTime;
		putUsedField("inputTime", inputTime);
	}
	/**
	 * @return the inputTimeFrom
	 */
	public Timestamp getInputTimeFrom() {
		return InputTimeFrom;
	}
	/**
	 * @param inputTimeFrom the inputTimeFrom to set
	 */
	public void setInputTimeFrom(Timestamp inputTimeFrom) {
		InputTimeFrom = inputTimeFrom;
		putUsedField("inputTimeFrom", inputTimeFrom);
	}
	/**
	 * @return the inputTimeTo
	 */
	public Timestamp getInputTimeTo() {
		return InputTimeTo;
	}
	/**
	 * @param inputTimeTo the inputTimeTo to set
	 */
	public void setInputTimeTo(Timestamp inputTimeTo) {
		InputTimeTo = inputTimeTo;
		putUsedField("inputTimeTo", inputTimeTo);
	}
	/**
	 * @return the statusId
	 */
	public long getStatusId() {
		return StatusId;
	}
	/**
	 * @param statusId the statusId to set
	 */
	public void setStatusId(long statusId) {
		StatusId = statusId;
		putUsedField("statusId", statusId);
	}
	/**
	 * @return the officeId
	 */
	public long getOfficeId() {
		return OfficeId;
	}
	/**
	 * @param officeId the officeId to set
	 */
	public void setOfficeId(long officeId) {
		OfficeId = officeId;
		putUsedField("officeId", officeId);
	}
	/**
	 * @return the currencyId
	 */
	public long getCurrencyId() {
		return CurrencyId;
	}
	/**
	 * @param currencyId the currencyId to set
	 */
	public void setCurrencyId(long currencyId) {
		CurrencyId = currencyId;
		putUsedField("currencyId", currencyId);
	}


	
	

}
