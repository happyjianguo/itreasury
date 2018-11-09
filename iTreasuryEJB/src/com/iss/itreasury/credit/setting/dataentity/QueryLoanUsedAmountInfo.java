/**
 * 
 */
package com.iss.itreasury.credit.setting.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author xintan
 *
 */
public class QueryLoanUsedAmountInfo extends ITreasuryBaseDataEntity {
	
	private long id = -1; //ÊÚÐÅid
	private long officeId = -1;
	private long currencyId = -1;
	private long clientId = -1;
	private long loanType = -1;
	private Timestamp checkDate = null;
	private Timestamp checkDateStart = null;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getClientId() {
		return clientId;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
	}

	public long getLoanType() {
		return loanType;
	}

	public void setLoanType(long loanType) {
		this.loanType = loanType;
	}

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

	public Timestamp getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Timestamp checkDate) {
		this.checkDate = checkDate;
	}

	public Timestamp getCheckDateStart() {
		return checkDateStart;
	}

	public void setCheckDateStart(Timestamp checkDateStart) {
		this.checkDateStart = checkDateStart;
	}
}
