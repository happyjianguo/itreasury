package com.iss.itreasury.project.wisgfc.tran.trustcollection.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class TrustCollectionentity extends ITreasuryBaseDataEntity {
	
	private static final long serialVersionUID = 1L;
	private long id = -1;                    //主键
	private String contractNO ="";         //合同号
	private String accountNO="";           //账户号
	private Timestamp accountingDate=null;  //记账日期
	private String sabstract="";          //摘要
	private double collectionAmount=0.00;   //托收金额
	private long status=-1;               //状态
	private String remarks="";            //备注
	private long isvalidate=-1;           //是否校验
	private String batchEntity = "";      //导入批次号
	public String getBatchEntity() {
		return batchEntity;
	}

	public void setBatchEntity(String batchEntity) {
		this.batchEntity = batchEntity;
		putUsedField("batchEntity", batchEntity);
	}

	public long getIsvalidate() {
		return isvalidate;
	}

	public void setIsvalidate(long isvalidate) {
		this.isvalidate = isvalidate;
		putUsedField("isvalidate",isvalidate);
	}

	public String getContractNO() {
		return contractNO;
	}

	public void setContractNO(String contractNO) {
		this.contractNO = contractNO;
		putUsedField("contractNO",contractNO);
	}

	public String getAccountNO() {
		return accountNO;
	}

	public void setAccountNO(String accountNO) {
		this.accountNO = accountNO;
		putUsedField("accountNO",accountNO);
	}

	public Timestamp getAccountingDate() {
		return accountingDate;
	}

	public void setAccountingDate(Timestamp accountingDate) {
		this.accountingDate = accountingDate;
		putUsedField("accountingDate",accountingDate);
	}

	public String getSabstract() {
		return sabstract;
	}

	public void setSabstract(String sabstract) {
		this.sabstract = sabstract;
		putUsedField("abstract",sabstract);
	}

	public double getCollectionAmount() {
		return collectionAmount;
	}

	public void setCollectionAmount(double collectionAmount) {
		this.collectionAmount = collectionAmount;
		putUsedField("collectionAmount",collectionAmount);
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
		putUsedField("status",status);
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
		putUsedField("remarks",remarks);
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
		this.putUsedField("id", id);
	}

}
