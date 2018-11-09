package com.iss.itreasury.loan.creditext.dataentity;

import javax.servlet.ServletRequest;

import com.iss.itreasury.util.ITreasuryException;

//银行综合授信信息
public class BankCreditExtListInfo {
	private long 	bankCreditExtId = -1; 		// 银行授信ID
	private long 	variety = -1; 				// 授信品种  1-短期贷款2-中长期贷款3-信用证4-保函5-信贷证明6-承兑汇票
	private long 	currencyType = -1; 			// 币种 
	private double 	amount = 0.0; 				// 金额 
	private double 	exchangeRate = 0.0; 		// 汇率 
	private double  convertRMB = 0.0;			// 折合人民币	
	private long 	riskCoefficient = 100; 		// 风险系数 风险系数，默认100%

	/**
	 * 将页面传过来的数字类型的字符串去掉逗号
	 * 
	 * @param str
	 * @return
	 */
	public String toFloatString(String str) 
	{
		String temp = "";
		for (int i = 0; i < str.length(); i++) 
		{
			if (str.charAt(i) != ',') 
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
		strTmp = (String) request.getAttribute("variety");
		if (strTmp != null && strTmp.length() > 0) 
		{
			variety = (Long.valueOf(strTmp.trim())).longValue();
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
		strTmp = (String) request.getAttribute("riskCoefficient");
		if (strTmp != null && strTmp.length() > 0) 
		{
			riskCoefficient = (Long.valueOf(toFloatString(strTmp.trim()))).longValue();
		}
		strTmp = (String) request.getAttribute("listvariety");
		if (strTmp != null && strTmp.length() > 0) 
		{
			variety = (Long.valueOf(strTmp.trim())).longValue();
		}
		strTmp = (String) request.getAttribute("listcurrencyType");
		if (strTmp != null && strTmp.length() > 0) 
		{
			currencyType = (Long.valueOf(strTmp.trim())).longValue();
		}
		strTmp = (String) request.getAttribute("listamount");
		if (strTmp != null && strTmp.length() > 0) 
		{
			amount = (Double.valueOf(toFloatString(strTmp.trim()))).doubleValue();
		}		
		strTmp = (String) request.getAttribute("listexchangeRate");
		if (strTmp != null && strTmp.length() > 0) 
		{
			exchangeRate = (Double.valueOf(toFloatString(strTmp.trim()))).doubleValue();
		}
		strTmp = (String) request.getAttribute("listconvertRMB");
		if (strTmp != null && strTmp.length() > 0) 
		{
			convertRMB = (Double.valueOf(toFloatString(strTmp.trim()))).doubleValue();
		}
		strTmp = (String) request.getAttribute("listriskCoefficient");
		if (strTmp != null && strTmp.length() > 0) 
		{
			riskCoefficient = (Long.valueOf(toFloatString(strTmp.trim()))).longValue();
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

	public long getRiskCoefficient() {
		return riskCoefficient;
	}

	public void setRiskCoefficient(long riskCoefficient) {
		this.riskCoefficient = riskCoefficient;
	}

	public long getVariety() {
		return variety;
	}

	public void setVariety(long variety) {
		this.variety = variety;
	}

	public double getConvertRMB() {
		return convertRMB;
	}

	public void setConvertRMB(double convertRMB) {
		this.convertRMB = convertRMB;
	}

	
}
