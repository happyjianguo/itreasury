package com.iss.itreasury.settlement.transfinance.dataentity;

import java.io.Serializable;

/**
 * @author afzhu
 * ���������㡢���������ѯ����ʵ����
 */
public class TransQueryFinanceNewInfo   implements Serializable{
	private String contractIDFrom;//��ͬ����
	private String contractIDTo;//��ͬ�ŵ�
	private int loanType;//��������
	private String executeDate;//�����ȡ��
	private String costType;//��������
	private long ntypeid;//��������ID
	private long nstatusid;//��ͬ״̬ID
	private long nofficeid;//���´�ID
	private long ncurrencyid;//����ID
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
