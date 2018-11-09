package com.iss.itreasury.loan.creditext.dataentity;

import javax.servlet.ServletRequest;

import com.iss.itreasury.util.ITreasuryException;

public class BankCreditExtQueryInfo {
	private long	id = -1;			//授信合同号		
	private String	year = "";					//授信年度	
	private String 	bankName = "";				//授信银行	
	private String 	company = "";				//授信主体		
	private long	status	= -1;				//授信状态		1-执行中2-已结束3-已取消
	private String 	startDate1 ="";				//授信起始日期1
	private String 	startDate2 ="";				//授信起始日期2	
	private String 	endDate1 ="";				//授信结束日期1
	private String 	endDate2 ="";				//授信结束日期2	
	private String 	operationDate1 ="";			//办理日期1
	private String 	operationDate2 ="";			//办理日期2
	private long officeId = -1; //added by mzh_fu 2007/03/26 区分办事处
	
	  /**
	 * 继承父类的convertRequestToDataEntity方法，用以从Request中取出和AssureInfo绑定的参数，
	 * 并封装在dataentity中
	 */
    public void convertRequestToDataEntity(ServletRequest request)throws ITreasuryException{
    	
		String strTmp = "";
		strTmp = (String) request.getAttribute("id");
		if( strTmp != null && strTmp.length() > 0 )
		{
			id = Long.valueOf(strTmp.trim()).longValue();
		}	
		strTmp = (String) request.getAttribute("year");
		if( strTmp != null && strTmp.length() > 0 )
		{
			year = strTmp.trim();
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
		strTmp = (String) request.getAttribute("status");
		if( strTmp != null && strTmp.length() > 0 )
		{
			status = Long.valueOf(strTmp.trim()).longValue();
		}					
		strTmp = (String) request.getAttribute("startDate1");
		if( strTmp != null && strTmp.length() > 0 )
		{
			startDate1 = strTmp.trim();
		}	
		strTmp = (String) request.getAttribute("startDate2");
		if( strTmp != null && strTmp.length() > 0 )
		{
			startDate2 = strTmp.trim();
		}	
		strTmp = (String) request.getAttribute("endDate1");
		if( strTmp != null && strTmp.length() > 0 )
		{
			endDate1 = strTmp.trim();
		}	
		strTmp = (String) request.getAttribute("endDate2");
		if( strTmp != null && strTmp.length() > 0 )
		{
			endDate2 = strTmp.trim();
		}			
		strTmp = (String) request.getAttribute("operationDate1");
		if( strTmp != null && strTmp.length() > 0 )
		{
			operationDate1 = strTmp.trim();
		}	
		strTmp = (String) request.getAttribute("operationDate2");
		if( strTmp != null && strTmp.length() > 0 )
		{
			operationDate2 = strTmp.trim();
		}			
    }
	
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEndDate1() {
		return endDate1;
	}
	public void setEndDate1(String endDate1) {
		this.endDate1 = endDate1;
	}
	public String getEndDate2() {
		return endDate2;
	}
	public void setEndDate2(String endDate2) {
		this.endDate2 = endDate2;
	}
	public String getOperationDate1() {
		return operationDate1;
	}
	public void setOperationDate1(String operationDate1) {
		this.operationDate1 = operationDate1;
	}
	public String getOperationDate2() {
		return operationDate2;
	}
	public void setOperationDate2(String operationDate2) {
		this.operationDate2 = operationDate2;
	}
	public String getStartDate1() {
		return startDate1;
	}
	public void setStartDate1(String startDate1) {
		this.startDate1 = startDate1;
	}
	public String getStartDate2() {
		return startDate2;
	}
	public void setStartDate2(String startDate2) {
		this.startDate2 = startDate2;
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}

	public long getOfficeId() {
		return officeId;
	}

	public void setOfficeId(long officeId) {
		this.officeId = officeId;
	}
	
	
}
