package com.iss.itreasury.loan.creditext.dataentity;

import javax.servlet.ServletRequest;

import com.iss.itreasury.util.ITreasuryException;

public class BankCreditExtComplexQueryInfo {
	private String year = "";			//授信年度
	private long   id = -1;				//银行授信ID
	private String company = "";		//授信主体
	private String bankName = "";		//授信银行
	private String startDate1 = "";		//授信开始日期-开始
	private String startDate2 = "";		//授信开始日期-结束
	private String endDate1 = "";		//授信结束日期-开始
	private String endDate2 = "";		//授信结束日期-结束
	private long   status = -1;			//授信状态
	private long   officeid = -1;		//结算中心代码
	
	
	 /**
	 * 继承父类的convertRequestToDataEntity方法，用以从Request中取出和AssureInfo绑定的参数，
	 * 并封装在dataentity中
	 */
    public void convertRequestToDataEntity(ServletRequest request)throws ITreasuryException{
    	
		String strTmp = "";
		strTmp = (String) request.getAttribute("id");
		if( strTmp != null && strTmp.length() > 0 )
		{
			id = (Long.valueOf(strTmp.trim())).longValue();
		}	
		strTmp = (String) request.getAttribute("year");
		if( strTmp != null && strTmp.length() > 0 )
		{
			year = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("company");
		if( strTmp != null && strTmp.length() > 0 )
		{
			company = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("bankName");
		if( strTmp != null && strTmp.length() > 0 )
		{
			bankName = strTmp.trim();
		}	
		strTmp = (String) request.getAttribute("company");
		if( strTmp != null && strTmp.length() > 0 )
		{
			company = strTmp.trim();
		}	
		
		strTmp = (String) request.getAttribute("startDate1");
		if( strTmp != null && strTmp.length() > 0 )
		{
			startDate1 = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("endDate1");
		if( strTmp != null && strTmp.length() > 0 )
		{
			endDate1 = strTmp.trim();
		}	
		strTmp = (String) request.getAttribute("startDate2");
		if( strTmp != null && strTmp.length() > 0 )
		{
			startDate2 = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("endDate2");
		if( strTmp != null && strTmp.length() > 0 )
		{
			endDate2 = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("status");
		if( strTmp != null && strTmp.length() > 0 )
		{
			
			status = (Long.valueOf(strTmp.trim())).longValue();
		}	
		strTmp = (String) request.getAttribute("officeid");
		if( strTmp != null && strTmp.length() > 0 )
		{
			
			officeid = (Long.valueOf(strTmp.trim())).longValue();
		}		
	
	}
	
	public String getYear() {
		return this.year;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getCompany() {
		return this.company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}
	
	public String getBankName() {
		return this.bankName;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public String getStartDate1() {
		return this.startDate1;
	}
	
	public void setStartDate1(String startDate1) {
		this.startDate1 = startDate1;
	}
	
	public String getStartDate2() {
		return this.startDate2;
	}
	
	public void setStartDate2(String startDate2) {
		this.startDate2 = startDate2;
	}
	
	public String getEndDate1() {
		return this.endDate1;
	}
	
	public void setEndDate1(String endDate1) {
		this.endDate1 = endDate1;
	}
	
	public String getEndDate2() {
		return this.endDate2;
	}
	
	public void setEndDate2(String endDate2) {
		this.endDate2 = endDate2;
	}
	
 	
	public long getOfficeid() {
		return officeid;
	}

	public void setOfficeid(long officeid) {
		this.officeid = officeid;
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

	public long getOfficId() {
		return this.officeid;
	}
	
	public void setOfficeId(long officeid) {
		this.officeid = officeid;
	}
}
