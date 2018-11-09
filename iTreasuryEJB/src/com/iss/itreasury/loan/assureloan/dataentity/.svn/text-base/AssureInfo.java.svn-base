package com.iss.itreasury.loan.assureloan.dataentity;

import java.sql.Date;

import javax.servlet.ServletRequest;

import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.ITreasuryException;

/**
 * @author yyhe ,fxzhang
 * Created on 2006-10-30
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AssureInfo implements java.io.Serializable{
	
	private long id = -1; 
	private String code = "";//内部流水号
	private String assureContractNo = "";//担保合同号
	private long assureKind = -1;//担保种类
	private long assureMode = -1;//担保方式
	private String assureType0 = "";   //修改之前的被担保业务类型
	private String assureType = "";   //被担保业务类型
	private long conferContractNo0 = -1;//修改之前的授信合同号ID
	private long conferContractNo = -1;//授信合同号ID
	private String conferContractCode = "";//授信合同号
	private String beneficiaryName = ""; //受益人
	private String cautionerName = ""; //担保人
	private String warranteeName0 = ""; //修改之前的被担保人
	private String warranteeName = ""; //被担保人
	private double assureWorth = 0.0; //担保品价值
	private double amount = 0.0; //担保金额
	private long currencyID = -1; //担保金额币种
	private double exchangeRate = 0.0; //与人民币汇率
	private double convertRMB = 0.0; //折合人民币
	private String startDate = ""; //开始日期
	private String endDate = ""; //结束日期	
	private String remark = ""; //备注
	
    //为了查询而设的属性
	String fromStartDate ="";//开始日期由
	String endStartDate ="";//开始日期到
	
	String fromEndDate ="";//结束日期由
	String endEndDate ="";//结束日期到
	
	public void  formatString()
	{
		startDate = DataFormat.formatString(startDate);
		endDate = DataFormat.formatString(endDate);		
		remark = DataFormat.formatString(remark);		
		
		if(!startDate.equals(""))
		{
			startDate = startDate.substring(0,10);		
		}
		if(!endDate.equals(""))
		{			
			endDate = endDate.substring(0,10);			
		}			
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
	 * 继承父类的convertRequestToDataEntity方法，用以从Request中取出和AssureInfo绑定的参数，
	 * 并封装在dataentity中
	 */
    public void convertRequestToDataEntity(ServletRequest request)throws ITreasuryException{
    	
		String strTmp = "";
		strTmp = (String) request.getAttribute("id");
		if( strTmp != null && strTmp.length() > 0 )
		{
			id = Long.parseLong(strTmp.trim());
		}		
		strTmp = (String) request.getAttribute("code");
		if( strTmp != null && strTmp.length() > 0 )
		{
			code = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("assureContractNo");
		if( strTmp != null && strTmp.length() > 0 )
		{
			assureContractNo = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("assureKind");
		if( strTmp != null && strTmp.length() > 0 )
		{
			assureKind = (Long.valueOf(strTmp.trim())).longValue();
		}	
		strTmp = (String) request.getAttribute("assureMode");
		if( strTmp != null && strTmp.length() > 0 )
		{
			assureMode = (Long.valueOf(strTmp.trim())).longValue();
		}
		strTmp = (String) request.getAttribute("assureType0");
		if( strTmp != null && strTmp.length() > 0 )
		{
			assureType0 = strTmp.trim();
		}	
		strTmp = (String) request.getAttribute("assureType");
		if( strTmp != null && strTmp.length() > 0 )
		{
			assureType = strTmp.trim();
		}	
		strTmp = (String) request.getAttribute("conferContractNo0");
		if( strTmp != null && strTmp.length() > 0 )
		{
			conferContractNo0 = Long.valueOf(strTmp.trim()).longValue();
		}	
		strTmp = (String) request.getAttribute("conferContractNo");
		if( strTmp != null && strTmp.length() > 0 )
		{
			conferContractNo = Long.valueOf(strTmp.trim()).longValue();
		}		
		strTmp = (String) request.getAttribute("beneficiaryName");
		if( strTmp != null && strTmp.length() > 0 )
		{
			beneficiaryName = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("assureWorth");
		if( strTmp != null && strTmp.length() > 0 )
		{
			
			assureWorth = (Double.valueOf(toFloatString(strTmp.trim()))).doubleValue();
		}	
		strTmp = (String) request.getAttribute("amount");
		if( strTmp != null && strTmp.length() > 0 )
		{
			
			amount = (Double.valueOf(toFloatString(strTmp.trim()))).doubleValue();
		}		
		strTmp = (String) request.getAttribute("currencyID");
		if( strTmp != null && strTmp.length() > 0 )
		{
			currencyID = (Long.valueOf(strTmp.trim())).longValue();
		}		
		strTmp = (String) request.getAttribute("exchangeRate");
		if( strTmp != null && strTmp.length() > 0 )
		{
			exchangeRate = (Double.valueOf(toFloatString(strTmp.trim()))).doubleValue();
		}		
		strTmp = (String) request.getAttribute("convertRMB");
		if( strTmp != null && strTmp.length() > 0 )
		{
			convertRMB = (Double.valueOf(toFloatString(strTmp.trim()))).doubleValue();
		}		
		strTmp = (String) request.getAttribute("cautionerName");
		if( strTmp != null && strTmp.length() > 0 )
		{
			cautionerName = strTmp.trim();
		}	
		strTmp = (String) request.getAttribute("warranteeName0");
		if( strTmp != null && strTmp.length() > 0 )
		{
			warranteeName0 = strTmp.trim();
		}
		strTmp = (String) request.getAttribute("warranteeName");
		if( strTmp != null && strTmp.length() > 0 )
		{
			warranteeName = strTmp.trim();
		}			
		strTmp = (String) request.getAttribute("startDate");
		if( strTmp != null && strTmp.length() > 0 )
		{
			startDate = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("endDate");
		if( strTmp != null && strTmp.length() > 0 )
		{
			endDate = strTmp.trim();
		}				
		strTmp = (String) request.getAttribute("remark");
		if( strTmp != null && strTmp.length() > 0 )
		{
			remark = strTmp.trim();
		}		
		
		
		
		strTmp = (String) request.getAttribute("fromStartDate");
		if( strTmp != null && strTmp.length() > 0 )
		{
			fromStartDate = strTmp.trim();
		}	
		strTmp = (String) request.getAttribute("endStartDate");
		if( strTmp != null && strTmp.length() > 0 )
		{
			endStartDate = strTmp.trim();
		}	
		strTmp = (String) request.getAttribute("fromEndDate");
		if( strTmp != null && strTmp.length() > 0 )
		{
			fromEndDate = strTmp.trim();
		}	
		strTmp = (String) request.getAttribute("endEndDate");
		if( strTmp != null && strTmp.length() > 0 )
		{
			endEndDate = strTmp.trim();
		}	

	}
    
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getAssureContractNo() {
		return assureContractNo;
	}
	public void setAssureContractNo(String assureContractNo) {
		this.assureContractNo = assureContractNo;
	}
	public long getAssureKind() {
		return assureKind;
	}
	public void setAssureKind(long assureKind) {
		this.assureKind = assureKind;
	}
	public long getAssureMode() {
		return assureMode;
	}
	public void setAssureMode(long assureMode) {
		this.assureMode = assureMode;
	}
	public double getAssureWorth() {
		return assureWorth;
	}
	public void setAssureWorth(double assureWorth) {
		this.assureWorth = assureWorth;
	}
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	public String getCautionerName() {
		return cautionerName;
	}
	public void setCautionerName(String cautionerName) {
		this.cautionerName = cautionerName;
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
	public double getConvertRMB() {
		return convertRMB;
	}
	public void setConvertRMB(double convertRMB) {
		this.convertRMB = convertRMB;
	}
	public long getCurrencyID() {
		return currencyID;
	}
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
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
	public String getWarranteeName() {
		return warranteeName;
	}
	public void setWarranteeName(String warranteeName) {
		this.warranteeName = warranteeName;
	}

	/**
	 * 以下两个set方法用以将从数据库中取出来的Date类型的数据转换为String类型的。
	 * @param startDate,endDate
	 */
	
	public void setStartDate(Date startDate)
	{
	    this.startDate = startDate.toString();
	}
	
	public void setEndDate(Date endDate)
	{
	    this.endDate = endDate.toString();
	}
	public String getAssureType() {
		return assureType;
	}
	public void setAssureType(String assureType) {
		this.assureType = assureType;
	}
	public String getConferContractCode() {
		return conferContractCode;
	}
	public void setConferContractCode(String conferContractCode) {
		this.conferContractCode = conferContractCode;
	}
	public String getAssureType0() {
		return assureType0;
	}
	public void setAssureType0(String assureType0) {
		this.assureType0 = assureType0;
	}
	public long getConferContractNo0() {
		return conferContractNo0;
	}
	public void setConferContractNo0(long conferContractNo0) {
		this.conferContractNo0 = conferContractNo0;
	}
	public String getWarranteeName0() {
		return warranteeName0;
	}
	public void setWarranteeName0(String warranteeName0) {
		this.warranteeName0 = warranteeName0;
	}
}
