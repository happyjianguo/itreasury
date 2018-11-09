package com.iss.itreasury.settlement.craftbrother.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

public class CraInterestCalcInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7240061695554938607L;

	private long subTransactionTypeId = -1;// 业务子类型
	
	private long counterpartId = -1;// 交易对手ID
	
	private String counterPartName = null;//交易对手名称
	
	private String contractCodeFrom = null;// 合同号 由
	
	private String contractCodeTo = null;// 合同号 到
	
	private String credenceCodeFrom = null;// 凭证号 由

	private String credenceCodeTo = null;// 凭证号 到
	
	private Timestamp endInterestDate = null;// 结息日
	
	private long[] interestTypes = null;//利息费用类型
	
	private long officeId = -1;//办事处
	
	private long currencyId = -1;//币种

	public long getSubTransactionTypeId() {
		return subTransactionTypeId;
	}

	public void setSubTransactionTypeId(long subTransactionTypeId) {
		this.subTransactionTypeId = subTransactionTypeId;
	}

	public long getCounterpartId() {
		return counterpartId;
	}

	public void setCounterpartId(long counterpartId) {
		this.counterpartId = counterpartId;
	}

	public String getContractCodeFrom() {
		return contractCodeFrom;
	}

	public void setContractCodeFrom(String contractCodeFrom) {
		this.contractCodeFrom = contractCodeFrom;
	}

	public String getContractCodeTo() {
		return contractCodeTo;
	}

	public void setContractCodeTo(String contractCodeTo) {
		this.contractCodeTo = contractCodeTo;
	}

	public String getCredenceCodeFrom() {
		return credenceCodeFrom;
	}

	public void setCredenceCodeFrom(String credenceCodeFrom) {
		this.credenceCodeFrom = credenceCodeFrom;
	}

	public String getCredenceCodeTo() {
		return credenceCodeTo;
	}

	public void setCredenceCodeTo(String credenceCodeTo) {
		this.credenceCodeTo = credenceCodeTo;
	}

	public Timestamp getEndInterestDate() {
		return endInterestDate;
	}

	public void setEndInterestDate(Timestamp endInterestDate) {
		this.endInterestDate = endInterestDate;
	}

	public long[] getInterestTypes() {
		return interestTypes;
	}

	public void setInterestTypes(long[] interestTypes) {
		this.interestTypes = interestTypes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
	
}
