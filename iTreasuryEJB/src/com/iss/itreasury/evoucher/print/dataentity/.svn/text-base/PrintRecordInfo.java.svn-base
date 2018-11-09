package com.iss.itreasury.evoucher.print.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.evoucher.base.VoucherBaseDataEntity;

public class PrintRecordInfo extends VoucherBaseDataEntity
{

	private long id = -1;
	private long nCurrencyID = -1;				//币种
	private long nOfficeID = -1;				//办事处
	private long nClientID = -1;				//成员单位id（针对于网银）
	private long nTransID = -1;					//交易id
	private long nTransTypeID = -1;				//交易类型
	private long nDeptID = -1;					//打印部门
	private long nBillID = -1;					//单据id
	private long nTempID = -1;					//模版id
	private long nPrintNum = -1;				//打印次数
	private Timestamp nInputDate = null;		//打印时间
	private long printUser = -1;                //jzw 2010-05-21 针对电子签章添加打印用户信息
	private String isSave = "";
	private String inputDate = "";            //jzw 2010-05-25 针对电子签章添加交易录入时间
	private long inputUserID = -1;              //jzw 2010-05-25 针对电子签章添加交易录入人
	private String nTransNO = "";
	
	//2007-7-17 Boxu add
	private String tempName = "";               //打印模版名称XML文件名
	private long moduleId = -1;                 //模块ID
	
	public long getModuleId()
	{
		return moduleId;
	}
	
	public void setModuleId(long moduleId)
	{
		this.moduleId = moduleId;
	}
	
	public String getTempName()
	{
		return tempName;
	}
	
	public void setTempName(String tempName)
	{
		this.tempName = tempName;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getNBillID() {
		return nBillID;
	}
	public void setNBillID(long billID) {
		nBillID = billID;
	}
	public long getNClientID() {
		return nClientID;
	}
	public void setNClientID(long clientID) {
		nClientID = clientID;
	}
	public long getNCurrencyID() {
		return nCurrencyID;
	}
	public void setNCurrencyID(long currencyID) {
		nCurrencyID = currencyID;
	}
	public long getNDeptID() {
		return nDeptID;
	}
	public void setNDeptID(long deptID) {
		nDeptID = deptID;
	}
	public Timestamp getNInputDate() {
		return nInputDate;
	}
	public void setNInputDate(Timestamp inputDate) {
		nInputDate = inputDate;
	}
	public long getNOfficeID() {
		return nOfficeID;
	}
	public void setNOfficeID(long officeID) {
		nOfficeID = officeID;
	}
	public long getNPrintNum() {
		return nPrintNum;
	}
	public void setNPrintNum(long printNum) {
		nPrintNum = printNum;
	}
	public long getNTempID() {
		return nTempID;
	}
	public void setNTempID(long tempID) {
		nTempID = tempID;
	}
	public long getNTransID() {
		return nTransID;
	}
	public void setNTransID(long transID) {
		nTransID = transID;
	}
	public long getNTransTypeID() {
		return nTransTypeID;
	}
	public void setNTransTypeID(long transTypeID) {
		nTransTypeID = transTypeID;
	}

	public long getPrintUser() {
		return printUser;
	}

	public void setPrintUser(long printUser) {
		this.printUser = printUser;
	}

	public String getIsSave() {
		return isSave;
	}

	public void setIsSave(String isSave) {
		this.isSave = isSave;
	}

	public String getInputDate() {
		return inputDate;
	}

	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}

	public long getInputUserID() {
		return inputUserID;
	}

	public void setInputUserID(long inputUserID) {
		this.inputUserID = inputUserID;
	}

	public String getNTransNO() {
		return nTransNO;
	}

	public void setNTransNO(String transNO) {
		nTransNO = transNO;
	}
	
}
