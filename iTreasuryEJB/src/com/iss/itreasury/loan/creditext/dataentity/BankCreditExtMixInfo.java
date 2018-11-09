package com.iss.itreasury.loan.creditext.dataentity;

import javax.servlet.ServletRequest;

import com.iss.itreasury.util.ITreasuryException;

//�������Ż�϶����Ϣ
public class BankCreditExtMixInfo {
	private long 	bankCreditExtId = -1; 		// ��������ID
	private long 	currencyType = -1;			//���֣�����1��ʾ����ң���2��ʾ��Ԫ$��3��ʾŷԪ?��4��ʾ��Ԫ��
	private double 	amount = 0.0;				//���Ž��
	private double 	exchangeRate = 0.0;			//���ʣ�������ҵĻ���ֵ
	private double   convertRMB = 0.0;			// �ۺ������	
	private long 	riskCoefficient1 = 100;		//���ڴ������ϵ��
	private long 	riskCoefficient2 = 100;		//�г��ڴ������ϵ��
	private long 	riskCoefficient3 = 100;		//����֤����ϵ��
	private long 	riskCoefficient4 = 100;		//��������ϵ��
	private long 	riskCoefficient5 = 100;		//�Ŵ�֤������ϵ��
	private long 	riskCoefficient6 = 100;		//�жһ�Ʊ����ϵ��
	
   /**
     * ��ҳ�洫�������������͵��ַ���ȥ������
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
	 * �̳и����convertRequestToDataEntity���������Դ�Request��ȡ����AssureInfo�󶨵Ĳ�����
	 * ����װ��dataentity��
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
