package com.iss.itreasury.evoucher.printcontrol.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.evoucher.base.VoucherBaseDataEntity;

public class PrintApplyInfo extends VoucherBaseDataEntity 
{

	private long id = -1;
	private long nOfficeID = -1;
	private long nCurrencyID = -1;
	private long nTransID = -1;//交易id
	private long nDeptID = -1;//打印部门
	private long nBillID = -1;//单据id
	private long ntempid = -1;//模版id
	private long nstatusid = -1;//状态
	private long isebank = -1;//是否网银申请
	private long ischapter = -1;//是否签章
	private long nclientid = -1;//成员单位id
	private long nInputUserId = -1;
	private Timestamp nInputDate = null;
	
	/**/
	private String nTransNo = "";//交易编号
	private long lTransTypeID = -1;//交易类型id
	private long operationtypeid = -1;  //特殊交易类型
	private long lPrintNum = -1;//打印次数
	private String strBillName = "";//单据名称
	private String strTempName = "";//模版名称
	private long lCoupletNo = -1;
	 private double Receiveamount; //交易金额
	//Boxu add 2007-7-17
	private long moduleId = -1;  //模块ID
	
	//add by dwj 20110930 接收人
    private long nreceiveuserid = -1;
    //end add by dwj 20110930
	
	public long getOperationtypeid() {
		return operationtypeid;
	}

	public void setOperationtypeid(long operationtypeid) {
		this.operationtypeid = operationtypeid;
	}

	public long getModuleId()
	{
		return moduleId;
	}
	
	public void setModuleId(long moduleId)
	{
		this.moduleId = moduleId;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getIschapter() {
		return ischapter;
	}
	public void setIschapter(long ischapter) {
		this.ischapter = ischapter;
	}
	public long getIsebank() {
		return isebank;
	}
	public void setIsebank(long isebank) {
		this.isebank = isebank;
	}
	public long getNBillID() {
		return nBillID;
	}
	public void setNBillID(long billID) {
		nBillID = billID;
	}
	public long getNclientid() {
		return nclientid;
	}
	public void setNclientid(long nclientid) {
		this.nclientid = nclientid;
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
	public long getNOfficeID() {
		return nOfficeID;
	}
	public void setNOfficeID(long officeID) {
		nOfficeID = officeID;
	}
	public long getNstatusid() {
		return nstatusid;
	}
	public void setNstatusid(long nstatusid) {
		this.nstatusid = nstatusid;
	}
	public long getNtempid() {
		return ntempid;
	}
	public void setNtempid(long ntempid) {
		this.ntempid = ntempid;
	}
	public long getNTransID() {
		return nTransID;
	}
	public void setNTransID(long transID) {
		nTransID = transID;
	}
	public long getLCoupletNo() {
		return lCoupletNo;
	}
	public void setLCoupletNo(long coupletNo) {
		lCoupletNo = coupletNo;
	}
	public long getLPrintNum() {
		return lPrintNum;
	}
	public void setLPrintNum(long printNum) {
		lPrintNum = printNum;
	}
	public long getLTransTypeID() {
		return lTransTypeID;
	}
	public void setLTransTypeID(long transTypeID) {
		lTransTypeID = transTypeID;
	}
	public String getNTransNo() {
		return nTransNo;
	}
	public void setNTransNo(String transNo) {
		nTransNo = transNo;
	}
	public String getStrBillName() {
		return strBillName;
	}
	public void setStrBillName(String strBillName) {
		this.strBillName = strBillName;
	}
	public String getStrTempName() {
		return strTempName;
	}
	public void setStrTempName(String strTempName) {
		this.strTempName = strTempName;
	}
	public Timestamp getNInputDate() {
		return nInputDate;
	}
	public void setNInputDate(Timestamp inputDate) {
		nInputDate = inputDate;
	}
	public long getNInputUserId() {
		return nInputUserId;
	}
	public void setNInputUserId(long inputUserId) {
		nInputUserId = inputUserId;
	}

	public double getReceiveamount() {
		return Receiveamount;
	}

	public void setReceiveamount(double receiveamount) {
		Receiveamount = receiveamount;
	}

	public long getNreceiveuserid() {
		return nreceiveuserid;
	}

	public void setNreceiveuserid(long nreceiveuserid) {
		this.nreceiveuserid = nreceiveuserid;
	}
	
	
}
