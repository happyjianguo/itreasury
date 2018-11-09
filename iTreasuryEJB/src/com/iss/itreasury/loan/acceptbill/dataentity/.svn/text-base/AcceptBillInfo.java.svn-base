package com.iss.itreasury.loan.acceptbill.dataentity;

import java.sql.Date;

import javax.servlet.ServletRequest;

import com.iss.itreasury.loan.base.LoanBaseDataEntity;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.ITreasuryException;

public class AcceptBillInfo extends  LoanBaseDataEntity{
	private  long 		id = -1;       			//承兑汇票信息
	private  String 	code = "";				//内部流水号
	private  long		officeId = -1;			//办事处ID
	private  String 	billClient = "";			//出票单位代码
	private  String 	billClientName = "";			//出票单位名称
	private  String 	billID = "";				//汇票编号
	private  String 	contractNo = "";			//合同/协议号
	private  String 	projectName = "";		//项目名称
	private  String 	outTicketDate = "";		//出票日期
	private  double     money = 0.0;				//金额
	private  long       mCurrencyID = -1;		//金额币种
	private  double     exchangeRate = 0.0;		//与人民币汇率
	private  double     convertRMB = 0.0;			//折合人民币
	private  double     convertRMB0 = 0.0;			//修改之前的折合人民币
    private  long       conferContractNo = -1;	//授信合同号ID
    private  String     conferContractCode = "";	//授信合同号
    private  String     certificateBank = "";	//开证银行
    private  String     payee = "";				//收款人
    private  String     payeeAccountID = "";		//收款人账号
    private  String     startDate = "";			//签发日期
	private  String     endDate = "";			//到期日期
    private  long       statusID = -1;			//状态 1为未使用 2为已使用
    private  String     remark = "";				//备注
    
    //页面表单字段
    private  String       fromStartDate = "";		//签发开始时间
    private  String 	  endStartDate = "";		//签发结束时间
    private  String 	  fromEndDate = "";		//到期开始时间
    private  String 	  endEndDate = "";			//到期结束时间
    
 
    public void  formatString()
	{
    	remark = DataFormat.formatString(remark);		
	}
    
    /**
     * 将页面传过来的数字类型的字符串去掉逗号
     * @param str
     * @return
     */
    public String toFloatString(String str)
    {
    	String temp="";
    	for(int i=0;i<str.length();i++)
    	{
    		if(str.charAt(i)!=',')
    		{
    			temp += str.charAt(i);
    		}
    	}
    	return temp;   	
    }
    /**
	 * 继承父类的convertRequestToDataEntity方法，用以从Request中取出和AcceptBillInfo绑定的参数，
	 * 并封装在dataentity中
	 */
    public void convertRequestToDataEntity(ServletRequest request)throws ITreasuryException{
		//super.convertRequestToDataEntity(request);
	
		String strTmp = "";
		strTmp = (String) request.getAttribute("id");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			id = Long.parseLong(strTmp.trim());
		}		
		strTmp = (String) request.getAttribute("code");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			code = strTmp.trim();
		}	
		strTmp = (String) request.getAttribute("officeId");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			officeId = (Long.valueOf(strTmp.trim())).longValue();
		}	
		strTmp = (String) request.getAttribute("billClient");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			billClient = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("billClientName");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			billClientName = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("billID");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			billID = strTmp.trim();
		}	
		strTmp = (String) request.getAttribute("contractNo");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			contractNo = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("projectName");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			projectName = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("outTicketDate");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			outTicketDate = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("money");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			
			money = (Double.valueOf(toFloatString(strTmp.trim()))).doubleValue();
		}		
		strTmp = (String) request.getAttribute("mCurrencyID");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			mCurrencyID = (Long.valueOf(strTmp.trim())).longValue();
		}		
		strTmp = (String) request.getAttribute("exchangeRate");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			exchangeRate = (Double.valueOf(toFloatString(strTmp.trim()))).doubleValue();
		}		
		strTmp = (String) request.getAttribute("convertRMB");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			convertRMB = (Double.valueOf(toFloatString(strTmp.trim()))).doubleValue();
		}		
		strTmp = (String) request.getAttribute("convertRMB0");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			convertRMB0 = (Double.valueOf(toFloatString(strTmp.trim()))).doubleValue();
		}		
		strTmp = (String) request.getAttribute("conferContractNo");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			conferContractNo = Long.valueOf(strTmp.trim()).longValue();
		}	
		strTmp = (String) request.getAttribute("conferContractCode");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			conferContractCode = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("certificateBank");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			certificateBank = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("payee");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			payee = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("payeeAccountID");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			payeeAccountID = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("startDate");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			startDate = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("endDate");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			endDate = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("statusID");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			statusID = (Long.valueOf(strTmp.trim())).longValue();
		}		
		strTmp = (String) request.getAttribute("remark");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			remark = strTmp.trim();
		}		
		
		
		
		strTmp = (String) request.getAttribute("fromStartDate");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			fromStartDate = strTmp.trim();
		}	
		strTmp = (String) request.getAttribute("endStartDate");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			endStartDate = strTmp.trim();
		}	
		strTmp = (String) request.getAttribute("fromEndDate");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			fromEndDate = strTmp.trim();
		}	
		strTmp = (String) request.getAttribute("endEndDate");
		if( strTmp != null && strTmp.trim().length() > 0 )
		{
			endEndDate = strTmp.trim();
		}	
	
	}
    
    //  将dataentity中为空的属性值转换为空字符串
	public void nullToString()
	{	
		if(code == null)
		{
			code = "";
		}
		if(billClient == null)
		{
			billClient = "";
		}
		if(billID == null)
		{
			billID = "";
		}
		if(contractNo == null)
		{
			contractNo = "";
		}
		if(projectName == null)
		{
			projectName = "";
		}
		if(outTicketDate == null)
		{
			outTicketDate = "";
		}
		if(certificateBank == null)
		{
			certificateBank = "";
		}
		if(payee == null)
		{
			payee = "";
		}
		if(payeeAccountID == null)
		{
			payeeAccountID = "";
		}
		if(startDate == null)
		{
			startDate = "";
		}
		if(endDate == null)
		{
			endDate = "";
		}
		if(remark == null)
		{
			remark = "";
		}
		if(fromStartDate == null)
		{
			fromStartDate = "";
		}
		if(endStartDate == null)
		{
			endStartDate = "";
		}
		if(fromEndDate == null)
		{
			fromEndDate = "";
		}
		if(endEndDate == null)
		{
			endEndDate = "";
		}
	}

	public String getBillClient() {
		return billClient;
	}

	public void setBillClient(String billClient) {
		this.billClient = billClient;
	}

	public String getBillID() {
		return billID;
	}

	public void setBillID(String billID) {
		this.billID = billID;
	}

	public String getCertificateBank() {
		return certificateBank;
	}

	public void setCertificateBank(String certificateBank) {
		this.certificateBank = certificateBank;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}



	public long getConferContractNo() {
		return conferContractNo;
	}
	public void setConferContractNo(long conferContractNo) {
		this.conferContractNo = conferContractNo;
	}
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public double getConvertRMB() {
		return convertRMB;
	}

	public void setConvertRMB(double convertRMB) {
		this.convertRMB = convertRMB;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEndEndDate() {
		return endEndDate;
	}

	public void setEndEndDate(String endEndDate) {
		this.endEndDate = endEndDate;
	}

	public String getEndStartDate() {
		return endStartDate;
	}

	public void setEndStartDate(String endStartDate) {
		this.endStartDate = endStartDate;
	}

	public double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getFromEndDate() {
		return fromEndDate;
	}

	public void setFromEndDate(String fromEndDate) {
		this.fromEndDate = fromEndDate;
	}

	public String getFromStartDate() {
		return fromStartDate;
	}

	public void setFromStartDate(String fromStartDate) {
		this.fromStartDate = fromStartDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getMCurrencyID() {
		return mCurrencyID;
	}

	public void setMCurrencyID(long currencyID) {
		mCurrencyID = currencyID;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getOutTicketDate() {
		return outTicketDate;
	}

	public void setOutTicketDate(String outTicketDate) {
		this.outTicketDate = outTicketDate;
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

	public String getPayeeAccountID() {
		return payeeAccountID;
	}

	public void setPayeeAccountID(String payeeAccountID) {
		this.payeeAccountID = payeeAccountID;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public long getStatusID() {
		return statusID;
	}

	public void setStatusID(long statusID) {
		this.statusID = statusID;
	}
	
	/**
	 * 以下三个set方法用以将从数据库中取出来的Date类型的数据转换为String类型的。
	 * @param outTicketDate,startDate,endDate
	 */
	public void setOutTicketDate(Date outTicketDate)
    {
	    this.outTicketDate =outTicketDate.toString();
	}
	
	public void setStartDate(Date startDate)
	{
	    this.startDate = startDate.toString();
	}
	
	public void setEndDate(Date endDate)
	{
	    this.endDate = endDate.toString();
	}
	public long getOfficeId() {
		return officeId;
	}
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
	}

	public String getBillClientName() {
		return billClientName;
	}

	public void setBillClientName(String billClientName) {
		this.billClientName = billClientName;
	}

	public String getConferContractCode() {
		return conferContractCode;
	}

	public void setConferContractCode(String conferContractCode) {
		this.conferContractCode = conferContractCode;
	}

	public double getConvertRMB0() {
		return convertRMB0;
	}

	public void setConvertRMB0(double convertRMB0) {
		this.convertRMB0 = convertRMB0;
	}
	   
}
