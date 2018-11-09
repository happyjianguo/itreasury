package com.iss.itreasury.evoucher.voucheraccount.dataentity;

import com.iss.itreasury.evoucher.setting.dataentity.PRINTBaseDataEntity;

public class SubVoucherTypeInfo extends PRINTBaseDataEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 数据库字段
	private long id = -1 ;
	private long voucherid = -1 ;              // 关联主表ID';
	private long clientid  = -1 ;              // 客户ID
	private long accountid = -1 ;              // 账户ID';
	private long statusid  = -1 ;              // 状态';
	private String memo  = ""    ;             // 备注';
	
	// 其他情况使用
	private String accountno = "" ;            // 账户编号
	private String[] subIDs = null;            // 归口账户所关联的所有归口子账户数组
	private long vouchertypeid = -1 ;          // 回单属性
	private long placetypeid = -1 ;            // 归口属性
	private String accountName = "" ;          // 账户名称
	
	
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id",id);
	}
	
	public long getVoucherid() {
		return voucherid;
	}
	public void setVoucherid(long voucherid) {
		this.voucherid = voucherid;
		putUsedField("voucherid",voucherid);
	}
	
	public long getClientid() {
		return clientid;
	}
	public void setClientid(long clientid) {
		this.clientid = clientid;
		putUsedField("clientid",clientid);
	}
	
	public long getAccountid() {
		return accountid;
	}
	public void setAccountid(long accountid) {
		this.accountid = accountid;
		putUsedField("accountid",accountid);
	}
	
	public long getStatusid() {
		return statusid;
	}
	public void setStatusid(long statusid) {
		this.statusid = statusid;
		putUsedField("statusid",statusid);
	}
	
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
		putUsedField("memo",memo);
	}
	
	
	public String getAccountno() {
		return accountno;
	}
	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}
	public String[] getSubIDs() {
		return subIDs;
	}
	public void setSubIDs(String[] subIDs) {
		this.subIDs = subIDs;
	}
	public long getVouchertypeid() {
		return vouchertypeid;
	}
	public void setVouchertypeid(long vouchertypeid) {
		this.vouchertypeid = vouchertypeid;
	}
	public long getPlacetypeid() {
		return placetypeid;
	}
	public void setPlacetypeid(long placetypeid) {
		this.placetypeid = placetypeid;
	}
	
	
	
}
