package com.iss.itreasury.settlement.comparisontrans.dataentity;

import java.util.Date;

import com.iss.itreasury.settlement.comparisontrans.bizlogic.ComparisonTransBiz;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.SessionMng;



public class ComparisonTransInfo {
	//查询条件
	private String bankAccountNo = null;//银行账号
	private String bankAccountName=null;//银行账户
	private String subjectCode = null;//对应的科目号
	private Date startDate = null;//查询的开始时间
	private Date endDate = null;//查询的结束时间
	private long bankAccountType = -1;//银行账户属性（1为一般户；2为集团户；3集团子账户；4备抵户;5资金池账户;6资金池子账户）
	//+++++++++++++++++++++修改
	private long officeID = -1 ; // 办事处
	private long currencyID = -1;  //币种
	private long bankID = -1;      //银企的银行ID	
	private long clientID = -1;    //客户ID
	//++++++++++++++++结束
	
	//显示信息
	private String stransno = "";//交易号
	private long directType = -1; //借贷方向
	private double amount =Double.NaN;//交易金额
	private Date dtexecute = null;//交易执行时间
	private String sabstract="";//摘要
	private String oppAccountNo = "";//对方账户编号
	private String oppAccountName = "";//对方账户名称
	public double getAmount()
	{
		return amount;
	}
	public void setAmount(double amount)
	{
		this.amount = amount;
	}
	public String getBankAccountName()
	{
		return bankAccountName;
	}
	public void setBankAccountName(String bankAccountName)
	{
		this.bankAccountName = bankAccountName;
	}
	public String getBankAccountNo()
	{
		return bankAccountNo;
	}
	public void setBankAccountNo(String bankAccountNo)
	{
		this.bankAccountNo = bankAccountNo;
	}
	public long getDirectType()
	{
		return directType;
	}
	public void setDirectType(long directType)
	{
		this.directType = directType;
	}
	public Date getDtexecute()
	{
		return dtexecute;
	}
	public void setDtexecute(Date dtexecute)
	{
		this.dtexecute = dtexecute;
	}
	public Date getEndDate()
	{
		return endDate;
	}
	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}
	public Date getStartDate()
	{
		return startDate;
	}
	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}
	public String getSubjectCode()
	{
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode)
	{
		this.subjectCode = subjectCode;
	}
	
	public void setStartDateString(String startDate) throws Exception
	{
		if(startDate == null || "".equals(startDate))
		{
			this.startDate = null;
			
		}
		else
		{
			this.startDate =
				ComparisonTransBiz.parseDate(startDate, DataFormat.FMT_DATE_YYYYMMDD);
		}
	}

	public void setEndDateString(String endDate) throws Exception
	{
		if(endDate == null || "".equals(endDate))
		{
			this.endDate = null;
			
		}
		else
		{
			this.endDate =
				ComparisonTransBiz.parseDate(endDate, DataFormat.FMT_DATE_YYYYMMDD);
		}
	}
	public String getStransno() {
		return stransno;
	}
	public void setStransno(String stransno) {
		this.stransno = stransno;
	}
	
	public long getBankID()
	{
	
		return bankID;
	}
	
	public void setBankID(long bankID)
	{
	
		this.bankID = bankID;
	}
	
	public long getClientID()
	{
	
		return clientID;
	}
	
	public void setClientID(long clientID)
	{
	
		this.clientID = clientID;
	}
	
	public long getCurrencyID()
	{
	
		return currencyID;
	}
	
	public void setCurrencyID(long currencyID)
	{
	
		this.currencyID = currencyID;
	}
	
	public long getOfficeID()
	{
	
		return officeID;
	}
	
	public void setOfficeID(long officeID)
	{
	
		this.officeID = officeID;
	}
	public String getOppAccountName() {
		return oppAccountName;
	}
	public void setOppAccountName(String oppAccountName) {
		this.oppAccountName = oppAccountName;
	}
	public String getOppAccountNo() {
		return oppAccountNo;
	}
	public void setOppAccountNo(String oppAccountNo) {
		this.oppAccountNo = oppAccountNo;
	}
	public String getSabstract() {
		return sabstract;
	}
	public void setSabstract(String sabstract) {
		this.sabstract = sabstract;
	}
	public long getBankAccountType() {
		return bankAccountType;
	}
	public void setBankAccountType(long bankAccountType) {
		this.bankAccountType = bankAccountType;
	}

}
