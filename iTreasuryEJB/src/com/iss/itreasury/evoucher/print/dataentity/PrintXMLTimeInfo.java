package com.iss.itreasury.evoucher.print.dataentity;


public class PrintXMLTimeInfo
{
	private long id = -1;
	private String transNo = ""; 
	private String[] billName = null;
	private int deptID = 0;
	private long currencyID = -1;
	private long officeID = -1; 
	private long module = -1;
	private long TransTypeID = -1;
	private long opretionType = -1;
	private String TransIDs = "";
	private long printUser = -1;              //jzw 2010-05-21 针对电子签章添加打印用户信息
	private String isSave = "0";			  //jzw 2010-05-25 针对电子签章添加是否保存打印记录
	private long clientID = -1;				  //jzw 2010-05-25 针对电子签章添加登录客户信息
	private String inputDate = "";            //jzw 2010-05-25 针对电子签章添加交易录入时间
	private long inputUserID = -1;            //jzw 2010-05-25 针对电子签章添加交易录入人
	private long isPreview=-1;          	 //zhanglei 2010-08-07 是否是打印预览  1为是   -1 为否
	
	public String getTransIDs() 
	{
		return TransIDs;
	}

	public void setTransIDs(String transIDs) 
	{
		TransIDs = transIDs;
	}

	public long getTransTypeID() 
	{
		return TransTypeID;
	}

	public void setTransTypeID(long transTypeID) 
	{
		TransTypeID = transTypeID;
	}

	public long getOpretionType()
	{
		return opretionType;
	}

	public void setOpretionType(long opretionType)
	{
		this.opretionType = opretionType;
	}

	public String[] getBillName()
	{
		return billName;
	}
	
	public void setBillName(String[] billName)
	{
		this.billName = billName;
	}
	
	public long getCurrencyID()
	{
		return currencyID;
	}
	
	public void setCurrencyID(long currencyID)
	{
		this.currencyID = currencyID;
	}
	
	public int getDeptID()
	{
		return deptID;
	}
	
	public void setDeptID(int deptID)
	{
		this.deptID = deptID;
	}
	
	public long getId()
	{
		return id;
	}
	
	public void setId(long id)
	{
		this.id = id;
	}
	
	public long getModule()
	{
		return module;
	}
	
	public void setModule(long module)
	{
		this.module = module;
	}
	
	public long getOfficeID()
	{
		return officeID;
	}
	
	public void setOfficeID(long officeID)
	{
		this.officeID = officeID;
	}
	
	public String getTransNo()
	{
		return transNo;
	}
	
	public void setTransNo(String transNo)
	{
		this.transNo = transNo;
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

	public long getClientID() {
		return clientID;
	}

	public void setClientID(long clientID) {
		this.clientID = clientID;
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

	public long getIsPreview() {
		return isPreview;
	}

	public void setIsPreview(long isPreview) {
		this.isPreview = isPreview;
	}
	
}
