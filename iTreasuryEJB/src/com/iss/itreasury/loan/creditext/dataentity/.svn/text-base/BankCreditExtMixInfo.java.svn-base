package com.iss.itreasury.loan.creditext.dataentity;

import javax.servlet.ServletRequest;

import com.iss.itreasury.util.ITreasuryException;

//银行授信混合额度信息
public class BankCreditExtMixInfo {
	private long 	bankCreditExtId = -1; 		// 银行授信ID
	private long 	currencyType = -1;			//币种，其中1表示人民币￥，2表示美元$，3表示欧元?，4表示日元￥
	private double 	amount = 0.0;				//授信金额
	private double 	exchangeRate = 0.0;			//汇率，与人民币的汇率值
	private double   convertRMB = 0.0;			// 折合人民币	
	private long 	riskCoefficient1 = 100;		//短期贷款风险系数
	private long 	riskCoefficient2 = 100;		//中长期贷款风险系数
	private long 	riskCoefficient3 = 100;		//信用证风险系数
	private long 	riskCoefficient4 = 100;		//保函风险系数
	private long 	riskCoefficient5 = 100;		//信贷证明风险系数
	private long 	riskCoefficient6 = 100;		//承兑汇票风险系数
	
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
	public void convertRequestToDataEntity(ServletRequest request)
			throws ITreasuryException 
	{
		String strTmp = "";
		strTmp = (String) request.getAttribute("bankCreditExtId");
		if (strTmp != null && strTmp.length() > 0) 
		{
			bankCreditExtId = (Long.valueOf(strTmp.trim())).longValue();
		}	
		strTmp = (String) request.getAttribute("currencyType");
		if (strTmp != null && strTmp.length() > 0) 
		{
			currencyType = (Long.valueOf(strTmp.trim())).longValue();
		}
		strTmp = (String) request.getAttribute("amount");
		if (strTmp != null && strTmp.length() > 0) 
		{
			amount = (Double.valueOf(toFloatString(strTmp.trim()))).doubleValue();
		}		
		strTmp = (String) request.getAttribute("exchangeRate");
		if (strTmp != null && strTmp.length() > 0) 
		{
			exchangeRate = (Double.valueOf(toFloatString(strTmp.trim()))).doubleValue();
		}
		strTmp = (String) request.getAttribute("convertRMB");
		if (strTmp != null && strTmp.length() > 0) 
		{
			convertRMB = (Double.valueOf(toFloatString(strTmp.trim()))).doubleValue();
		}
		strTmp = (String) request.getAttribute("riskCoefficient1");
		if (strTmp != null && strTmp.length() > 0) 
		{
			riskCoefficient1 = (Long.valueOf(toFloatString(strTmp.trim()))).longValue();
		}
		strTmp = (String) request.getAttribute("riskCoefficient2");
		if (strTmp != null && strTmp.length() > 0) 
		{
			riskCoefficient2 = (Long.valueOf(toFloatString(strTmp.trim()))).longValue();
		}
		strTmp = (String) request.getAttribute("riskCoefficient3");
		if (strTmp != null && strTmp.length() > 0) 
		{
			riskCoefficient3 = (Long.valueOf(toFloatString(strTmp.trim()))).longValue();
		}
		strTmp = (String) request.getAttribute("riskCoefficient4");
		if (strTmp != null && strTmp.length() > 0) 
		{
			riskCoefficient4 = (Long.valueOf(toFloatString(strTmp.trim()))).longValue();
		}
		strTmp = (String) request.getAttribute("riskCoefficient5");
		if (strTmp != null && strTmp.length() > 0) 
		{
			riskCoefficient5 = (Long.valueOf(toFloatString(strTmp.trim()))).longValue();
		}
		strTmp = (String) request.getAttribute("riskCoefficient6");
		if (strTmp != null && strTmp.length() > 0) 
		{
			riskCoefficient6 = (Long.valueOf(toFloatString(strTmp.trim()))).longValue();
		}
	}

	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}

	public long getBankCreditExtId() {
		return bankCreditExtId;
	}

	public void setBankCreditExtId(long bankCreditExtId) {
		this.bankCreditExtId = bankCreditExtId;
	}

	public long getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(long currencyType) {
		this.currencyType = currencyType;
	}
	public double getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public long getRiskCoefficient1() {
		return riskCoefficient1;
	}
	public void setRiskCoefficient1(long riskCoefficient1) {
		this.riskCoefficient1 = riskCoefficient1;
	}
	public long getRiskCoefficient2() {
		return riskCoefficient2;
	}
	public void setRiskCoefficient2(long riskCoefficient2) {
		this.riskCoefficient2 = riskCoefficient2;
	}
	public long getRiskCoefficient3() {
		return riskCoefficient3;
	}
	public void setRiskCoefficient3(long riskCoefficient3) {
		this.riskCoefficient3 = riskCoefficient3;
	}
	public long getRiskCoefficient4() {
		return riskCoefficient4;
	}
	public void setRiskCoefficient4(long riskCoefficient4) {
		this.riskCoefficient4 = riskCoefficient4;
	}
	public long getRiskCoefficient5() {
		return riskCoefficient5;
	}
	public void setRiskCoefficient5(long riskCoefficient5) {
		this.riskCoefficient5 = riskCoefficient5;
	}
	public long getRiskCoefficient6() {
		return riskCoefficient6;
	}
	public void setRiskCoefficient6(long riskCoefficient6) {
		this.riskCoefficient6 = riskCoefficient6;
	}

	public double getConvertRMB() {
		return convertRMB;
	}

	public void setConvertRMB(double convertRMB) {
		this.convertRMB = convertRMB;
	}
	

}
