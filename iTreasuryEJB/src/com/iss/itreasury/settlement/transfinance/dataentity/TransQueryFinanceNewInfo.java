package com.iss.itreasury.settlement.transfinance.dataentity;

import java.io.Serializable;

/**
 * @author afzhu
 * 融资租金匡算、批量还款查询条件实体类
 */
public class TransQueryFinanceNewInfo   implements Serializable{
	private String contractIDFrom;//合同号由
	private String contractIDTo;//合同号到
	private int loanType;//贷款种类
	private String executeDate;//租金收取日
	private String costType;//费用类型
	private long ntypeid;//贷款类型ID
	private long nstatusid;//合同状态ID
	private long nofficeid;//办事处ID
	private long ncurrencyid;//币种ID
	public String getContractIDFrom() {
		return contractIDFrom;
	}
	public void setContractIDFrom(String contractIDFrom) {
		this.contractIDFrom = contractIDFrom;
	}
	public String getContractIDTo() {
		return contractIDTo;
	}
	public void setContractIDTo(String contractIDTo) {
		this.contractIDTo = contractIDTo;
	}
	public int getLoanType() {
		return loanType;
	}
	public void setLoanType(int loanType) {
		this.loanType = loanType;
	}
	public String getExecuteDate() {
		return executeDate;
	}
	public void setExecuteDate(String executeDate) {
		this.executeDate = executeDate;
	}
	public String getCostType() {
		return costType;
	}
	public void setCostType(String costType) {
		this.costType = costType;
	}
	public long getNtypeid() {
		return ntypeid;
	}
	public void setNtypeid(long ntypeid) {
		this.ntypeid = ntypeid;
	}
	public long getNstatusid() {
		return nstatusid;
	}
	public void setNstatusid(long nstatusid) {
		this.nstatusid = nstatusid;
	}
	public long getNofficeid() {
		return nofficeid;
	}
	public void setNofficeid(long nofficeid) {
		this.nofficeid = nofficeid;
	}
	public long getNcurrencyid() {
		return ncurrencyid;
	}
	public void setNcurrencyid(long ncurrencyid) {
		this.ncurrencyid = ncurrencyid;
	}
}
