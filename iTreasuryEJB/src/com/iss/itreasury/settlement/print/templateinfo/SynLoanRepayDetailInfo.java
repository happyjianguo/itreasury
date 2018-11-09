package com.iss.itreasury.settlement.print.templateinfo;
/**
 * @author gqzhang
 *承载银团贷款收回分行明细信息 To  change this generated comment edit the template variable
 *"typecomment": Window>Preferences>Java>Templates. To enable and disable the
 *creation of type comments go to Window>Preferences>Java>Code Generation.
 */
public class SynLoanRepayDetailInfo
{
	//贷款收回明细表
	//1收款单位名称
	private String ReceiveAmountUnitName = "";
	//2承贷比例
	private String LoanRate = "";
	//3开户银行
	private String OpenBank = "";
	//4银行账号
	private String BankAccountNo = "";
	//5收款金额
	private String ReciveAmount = "";
	//贷款利息明细表
	//1收息单位名称
	private String ReveiveInterestUnitName = "";
	//2开户银行
	//3银行账号
	//4应收利息
	private String ReceiveInterest = "";
	/**
	 * Returns the bankAccountNo.
	 * @return String
	 */
	public String getBankAccountNo()
	{
		return BankAccountNo;
	}

	/**
	 * Returns the loanRate.
	 * @return String
	 */
	public String getLoanRate()
	{
		return LoanRate;
	}

	/**
	 * Returns the openBank.
	 * @return String
	 */
	public String getOpenBank()
	{
		return OpenBank;
	}

	/**
	 * Returns the receiveAmountUnitName.
	 * @return String
	 */
	public String getReceiveAmountUnitName()
	{
		return ReceiveAmountUnitName;
	}

	/**
	 * Returns the receiveInterest.
	 * @return String
	 */
	public String getReceiveInterest()
	{
		return ReceiveInterest;
	}

	/**
	 * Returns the reciveAmount.
	 * @return String
	 */
	public String getReciveAmount()
	{
		return ReciveAmount;
	}

	/**
	 * Returns the reveiveInterestUnitName.
	 * @return String
	 */
	public String getReveiveInterestUnitName()
	{
		return ReveiveInterestUnitName;
	}

	/**
	 * Sets the bankAccountNo.
	 * @param bankAccountNo The bankAccountNo to set
	 */
	public void setBankAccountNo(String bankAccountNo)
	{
		BankAccountNo = bankAccountNo;
	}

	/**
	 * Sets the loanRate.
	 * @param loanRate The loanRate to set
	 */
	public void setLoanRate(String loanRate)
	{
		LoanRate = loanRate;
	}

	/**
	 * Sets the openBank.
	 * @param openBank The openBank to set
	 */
	public void setOpenBank(String openBank)
	{
		OpenBank = openBank;
	}

	/**
	 * Sets the receiveAmountUnitName.
	 * @param receiveAmountUnitName The receiveAmountUnitName to set
	 */
	public void setReceiveAmountUnitName(String receiveAmountUnitName)
	{
		ReceiveAmountUnitName = receiveAmountUnitName;
	}

	/**
	 * Sets the receiveInterest.
	 * @param receiveInterest The receiveInterest to set
	 */
	public void setReceiveInterest(String receiveInterest)
	{
		ReceiveInterest = receiveInterest;
	}

	/**
	 * Sets the reciveAmount.
	 * @param reciveAmount The reciveAmount to set
	 */
	public void setReciveAmount(String reciveAmount)
	{
		ReciveAmount = reciveAmount;
	}

	/**
	 * Sets the reveiveInterestUnitName.
	 * @param reveiveInterestUnitName The reveiveInterestUnitName to set
	 */
	public void setReveiveInterestUnitName(String reveiveInterestUnitName)
	{
		ReveiveInterestUnitName = reveiveInterestUnitName;
	}

}