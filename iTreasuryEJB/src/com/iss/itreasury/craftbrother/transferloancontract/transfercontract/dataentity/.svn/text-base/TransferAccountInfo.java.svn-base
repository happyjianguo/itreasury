package com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class TransferAccountInfo extends ITreasuryBaseDataEntity {

	private long id = -1;							//主键ID
	private long officeId = -1;						//办事处ID
	private long currencyId = -1;					//币种ID
	private long zcontractid = -1;					//转让合同ID
	private long transtypeid = -1;					//业务类型（卖出回购，卖出卖断）
	private long operationtypeid = -1;				//操作类型（交易对手，成员单位）
	private long loannoteid = -1;					//放款通知单ID
	private double zamount = 0.0;					//转让余额
	private Timestamp lastclearinterest = null;		//上次结息日
	private Timestamp clearinterest = null;			//结息日
	private double remaininterest = 0.0;			//剩余利息
	private double preremaininterest = 0.0;			//上次剩余利息
	private double drawinterest = 0.0;				//计提利息
	private double perdrawinterest = 0.0;			//上次计提利息
	private Timestamp drawdate = null;				//计提日期
	private Timestamp perdrawdate = null;			//上次计提日期
	private long statusid = -1;						//状态
	
	public Timestamp getClearinterest() {
		return clearinterest;
	}
	public void setClearinterest(Timestamp clearinterest) {
		this.clearinterest = clearinterest;
		putUsedField("clearinterest", clearinterest);
	}
	public long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
		putUsedField("currencyId", currencyId);
	}
	public Timestamp getDrawdate() {
		return drawdate;
	}
	public void setDrawdate(Timestamp drawdate) {
		this.drawdate = drawdate;
		putUsedField("drawdate", drawdate);
	}
	public double getDrawinterest() {
		return drawinterest;
	}
	public void setDrawinterest(double drawinterest) {
		this.drawinterest = drawinterest;
		putUsedField("drawinterest", drawinterest);
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}
	public Timestamp getLastclearinterest() {
		return lastclearinterest;
	}
	public void setLastclearinterest(Timestamp lastclearinterest) {
		this.lastclearinterest = lastclearinterest;
		putUsedField("lastclearinterest", lastclearinterest);
	}
	public long getLoannoteid() {
		return loannoteid;
	}
	public void setLoannoteid(long loannoteid) {
		this.loannoteid = loannoteid;
		putUsedField("loannoteid", loannoteid);
	}
	public long getOfficeId() {
		return officeId;
	}
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
		putUsedField("officeId", officeId);
	}
	public long getOperationtypeid() {
		return operationtypeid;
	}
	public void setOperationtypeid(long operationtypeid) {
		this.operationtypeid = operationtypeid;
		putUsedField("operationtypeid", operationtypeid);
	}
	public Timestamp getPerdrawdate() {
		return perdrawdate;
	}
	public void setPerdrawdate(Timestamp perdrawdate) {
		this.perdrawdate = perdrawdate;
		putUsedField("perdrawdate", perdrawdate);
	}
	public double getPerdrawinterest() {
		return perdrawinterest;
	}
	public void setPerdrawinterest(double perdrawinterest) {
		this.perdrawinterest = perdrawinterest;
		putUsedField("perdrawinterest", perdrawinterest);
	}
	public double getPreremaininterest() {
		return preremaininterest;
	}
	public void setPreremaininterest(double preremaininterest) {
		this.preremaininterest = preremaininterest;
		putUsedField("preremaininterest", preremaininterest);
	}
	public double getRemaininterest() {
		return remaininterest;
	}
	public void setRemaininterest(double remaininterest) {
		this.remaininterest = remaininterest;
		putUsedField("remaininterest", remaininterest);
	}
	public long getStatusid() {
		return statusid;
	}
	public void setStatusid(long statusid) {
		this.statusid = statusid;
		putUsedField("statusid", statusid);
	}
	public long getTranstypeid() {
		return transtypeid;
	}
	public void setTranstypeid(long transtypeid) {
		this.transtypeid = transtypeid;
		putUsedField("transtypeid", transtypeid);
	}
	public double getZamount() {
		return zamount;
	}
	public void setZamount(double zamount) {
		this.zamount = zamount;
		putUsedField("zamount", zamount);
	}
	public long getZcontractid() {
		return zcontractid;
	}
	public void setZcontractid(long zcontractid) {
		this.zcontractid = zcontractid;
		putUsedField("zcontractid", zcontractid);
	}
	
	
}
