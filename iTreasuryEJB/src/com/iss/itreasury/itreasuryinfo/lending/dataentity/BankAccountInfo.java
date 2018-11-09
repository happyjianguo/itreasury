package com.iss.itreasury.itreasuryinfo.lending.dataentity;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class BankAccountInfo extends ITreasuryBaseDataEntity {

	private static final long serialVersionUID = -8402258177773308311L;
	
	private long id;                  /** ID 主键 */
	private String sAccountNo;        /** 账户号（根据账户号判断是否存入，如果已经存入，则不保存） */
	private String sAccounName;       /** 账户名称 */
	private long nStatusId;           /** 状态( 0 无效  1 有效) */
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}
	public String getsAccountNo() {
		return sAccountNo;
	}
	public void setsAccountNo(String sAccountNo) {
		this.sAccountNo = sAccountNo;
		putUsedField("sAccountNo", sAccountNo);
	}
	public String getsAccounName() {
		return sAccounName;
	}
	public void setsAccounName(String sAccounName) {
		this.sAccounName = sAccounName;
		putUsedField("sAccounName", sAccounName);
	}
	public long getnStatusId() {
		return nStatusId;
	}
	public void setnStatusId(long nStatusId) {
		this.nStatusId = nStatusId;
		putUsedField("nStatusId", nStatusId);
	}
	
}
