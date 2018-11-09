/*
 * Created on 2003-12-8
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.report.dataentity;

/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

public class ReportContractDetailInfo implements java.io.Serializable
{ 
    private long ContractId = -1;
    /**
     * @return Returns the contractId.
     */
    public long getContractId()
    {
        return ContractId;
    }
    /**
     * @param contractId The contractId to set.
     */
    public void setContractId(long contractId)
    {
        ContractId = contractId;
    }
	private String LoanType = ""; //贷款种类
	private String ContractCode = ""; //合同号
	private String ClientName = ""; //借款单位
	private String ContractRate = ""; //合同利率
	private String LoanStart; //借款起始日期
	private String LoanEnd; //借款结束日期
	private String CreditAmount = ""; //授信额度
	private String AssureType = ""; //担保方式
	private String LoanStatus = ""; //贷款状态
	private String CheckAmount="";  //贷款本金
	
	

	/**
	 * @return
	 */
	public String getAssureType()
	{
		return AssureType;
	}

	/**
	 * @return
	 */
	public String getClientName()
	{
		return ClientName;
	}

	/**
	 * @return
	 */
	public String getContractCode()
	{
		return ContractCode;
	}

	/**
	 * @return
	 */
	public String getContractRate()
	{
		return ContractRate;
	}

	/**
	 * @return
	 */
	public String getCreditAmount()
	{
		return CreditAmount;
	}

	/**
	 * @return
	 */
	public String getLoanEnd()
	{
		return LoanEnd;
	}

	/**
	 * @return
	 */
	public String getLoanStart()
	{
		return LoanStart;
	}

	/**
	 * @return
	 */
	public String getLoanStatus()
	{
		return LoanStatus;
	}

	/**
	 * @return
	 */
	public String getLoanType()
	{
		return LoanType;
	}

	/**
	 * @param string
	 */
	public void setAssureType(String string)
	{
		AssureType = string;
	}

	/**
	 * @param string
	 */
	public void setClientName(String string)
	{
		ClientName = string;
	}

	/**
	 * @param string
	 */
	public void setContractCode(String string)
	{
		ContractCode = string;
	}

	/**
	 * @param string
	 */
	public void setContractRate(String string)
	{
		ContractRate = string;
	}

	/**
	 * @param string
	 */
	public void setCreditAmount(String string)
	{
		CreditAmount = string;
	}

	/**
	 * @param string
	 */
	public void setLoanEnd(String string)
	{
		LoanEnd = string;
	}

	/**
	 * @param string
	 */
	public void setLoanStart(String string)
	{
		LoanStart = string;
	}

	/**
	 * @param string
	 */
	public void setLoanStatus(String string)
	{
		LoanStatus = string;
	}

	/**
	 * @param string
	 */
	public void setLoanType(String string)
	{
		LoanType = string;
	}

	/**
	 * @return
	 */
	public String getCheckAmount() {
		return CheckAmount;
	}

	/**
	 * @param string
	 */
	public void setCheckAmount(String string) {
		CheckAmount = string;
	}

}
