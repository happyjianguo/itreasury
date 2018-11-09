package com.iss.itreasury.settlement.accountsystem.dataentity;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author leiyang3
 *
 */
public class AccountRelationSettingInfo extends ITreasuryBaseDataEntity {

	private long id = -1;
  	private long nAccountSystemId = -1;
  	private long nAccountId = -1;
  	private long nStatusId = -1;
  	private long nOfficeId = -1;
  	private long nCurrencyId = -1;
  	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}
	public long getNAccountId() {
		return nAccountId;
	}
	public void setNAccountId(long accountId) {
		nAccountId = accountId;
		putUsedField("nAccountId", nAccountId);
	}
	public long getNAccountSystemId() {
		return nAccountSystemId;
	}
	public void setNAccountSystemId(long accountSystemId) {
		nAccountSystemId = accountSystemId;
		putUsedField("nAccountSystemId", nAccountSystemId);
	}
	public long getNCurrencyId() {
		return nCurrencyId;
	}
	public void setNCurrencyId(long currencyId) {
		nCurrencyId = currencyId;
		putUsedField("nCurrencyId", nCurrencyId);
	}
	public long getNOfficeId() {
		return nOfficeId;
	}
	public void setNOfficeId(long officeId) {
		nOfficeId = officeId;
		putUsedField("nOfficeId", nOfficeId);
	}
	public long getNStatusId() {
		return nStatusId;
	}
	public void setNStatusId(long statusId) {
		nStatusId = statusId;
		putUsedField("nStatusId", nStatusId);
	}
}
