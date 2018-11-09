/*
* CommonParameter.java
* Created by gqzhang 2004��1��6��
 */
package com.iss.itreasury.ebank.obfinanceinstr.dataentity;
import java.sql.Timestamp;
import java.io.Serializable;
public class SubLoanAccountDetailInfo  implements Serializable
{
	public SubLoanAccountDetailInfo()
	{
	}
	private long lOfficeID = -1; //���´�
	private long lCurrencyID = -1; //����
	private long lSubAccountID = -1; //���˻�id
	private long lLoanAccountID = -1; //�����˺�
	private long lLoanNoteID = -1; //�ſ�֪ͨ��id
	private Timestamp tsClearInterest = null; //��Ϣ����
	private double dInterest = 0.0; //Ӧ��������Ϣ
	private double dCompoundInterest = 0.00; //Ӧ������
	private double dCompoundAmount = 0.00; //��������
	private Timestamp tsCompInterestStart = null; //������Ϣ��
	private double dCompRate = 0.00; //��������
	private double dOverDueInterest = 0.00; //Ӧ�����ڷ�Ϣ
	private double dOverDueAmount = 0.00; //Ӧ�����ڱ���
	private Timestamp tsOverDueStart = null; //��Ϣ��Ϣ��
	private double dOverDueRate = 0.00; //��Ϣ����
	private double dSuretyFee = 0.00; //Ӧ��������
	private Timestamp tsSuretyStart = null; //��������Ϣ��
	private double dSuretyRate = 0.00; //��������
	private double dCommission = 0.00; //Ӧ��������
	private Timestamp tsInterestStart = null; //������Ϣ��Ϣ��
	private double dInterestRate = 0.00; //������Ϣ����
	private Timestamp tsCommissionStart = null; //��������Ϣ��
	private double dCommissionRate = 0.00; //��������
	private double dInterestReceiveable = 0.00; //������Ϣ(�����廹ʹ��)         
	private double dInterestIncome = 0.00; //������Ϣ(�����廹ʹ��)   
	private double dInterestTax = 0.00; //��Ϣ˰��(�����廹ʹ��)
	private double dInterestTaxRate = 0.00;//��Ϣ˰��˰��(�����廹ʹ��)
	private double dTotal = 0.00; //Ӧ����Ϣ�ͷ���֮��
	
	//������
	private long ContractType = -1;				//��ͬ����
	/**
	 * Returns the commission.
	 * @return double
	 */
	public double getCommission()
	{
		return dCommission;
	}
	/**
	 * Returns the commissionRate.
	 * @return double
	 */
	public double getCommissionRate()
	{
		return dCommissionRate;
	}
	/**
	 * Returns the compoundAmount.
	 * @return double
	 */
	public double getCompoundAmount()
	{
		return dCompoundAmount;
	}
	/**
	 * Returns the compoundInterest.
	 * @return double
	 */
	public double getCompoundInterest()
	{
		return dCompoundInterest;
	}
	/**
	 * Returns the compRate.
	 * @return double
	 */
	public double getCompRate()
	{
		return dCompRate;
	}
	/**
	 * Returns the interest.
	 * @return double
	 */
	public double getInterest()
	{
		return dInterest;
	}
	/**
	 * Returns the interestRate.
	 * @return double
	 */
	public double getInterestRate()
	{
		return dInterestRate;
	}
	/**
	 * Returns the overDueAmount.
	 * @return double
	 */
	public double getOverDueAmount()
	{
		return dOverDueAmount;
	}
	/**
	 * Returns the overDueInterest.
	 * @return double
	 */
	public double getOverDueInterest()
	{
		return dOverDueInterest;
	}
	/**
	 * Returns the overDueRate.
	 * @return double
	 */
	public double getOverDueRate()
	{
		return dOverDueRate;
	}
	/**
	 * Returns the suretyFee.
	 * @return double
	 */
	public double getSuretyFee()
	{
		return dSuretyFee;
	}
	/**
	 * Returns the suretyRate.
	 * @return double
	 */
	public double getSuretyRate()
	{
		return dSuretyRate;
	}
	/**
	 * Returns the total.
	 * @return double
	 */
	public double getTotal()
	{
		return dTotal;
	}
	/**
	 * Returns the currencyID.
	 * @return long
	 */
	public long getCurrencyID()
	{
		return lCurrencyID;
	}
	/**
	 * Returns the loanAccountID.
	 * @return long
	 */
	public long getLoanAccountID()
	{
		return lLoanAccountID;
	}
	/**
	 * Returns the loanNoteID.
	 * @return long
	 */
	public long getLoanNoteID()
	{
		return lLoanNoteID;
	}
	/**
	 * Returns the officeID.
	 * @return long
	 */
	public long getOfficeID()
	{
		return lOfficeID;
	}
	/**
	 * Returns the subAccountID.
	 * @return long
	 */
	public long getSubAccountID()
	{
		return lSubAccountID;
	}
	/**
	 * Returns the clearInterest.
	 * @return Timestamp
	 */
	public Timestamp getClearInterest()
	{
		return tsClearInterest;
	}
	/**
	 * Returns the commissionStart.
	 * @return Timestamp
	 */
	public Timestamp getCommissionStart()
	{
		return tsCommissionStart;
	}
	/**
	 * Returns the compInterestStart.
	 * @return Timestamp
	 */
	public Timestamp getCompInterestStart()
	{
		return tsCompInterestStart;
	}
	/**
	 * Returns the interestStart.
	 * @return Timestamp
	 */
	public Timestamp getInterestStart()
	{
		return tsInterestStart;
	}
	/**
	 * Returns the overDueStart.
	 * @return Timestamp
	 */
	public Timestamp getOverDueStart()
	{
		return tsOverDueStart;
	}
	/**
	 * Returns the suretyStart.
	 * @return Timestamp
	 */
	public Timestamp getSuretyStart()
	{
		return tsSuretyStart;
	}
	/**
	 * Sets the commission.
	 * @param commission The commission to set
	 */
	public void setCommission(double commission)
	{
		dCommission = commission;
	}
	/**
	 * Sets the commissionRate.
	 * @param commissionRate The commissionRate to set
	 */
	public void setCommissionRate(double commissionRate)
	{
		dCommissionRate = commissionRate;
	}
	/**
	 * Sets the compoundAmount.
	 * @param compoundAmount The compoundAmount to set
	 */
	public void setCompoundAmount(double compoundAmount)
	{
		dCompoundAmount = compoundAmount;
	}
	/**
	 * Sets the compoundInterest.
	 * @param compoundInterest The compoundInterest to set
	 */
	public void setCompoundInterest(double compoundInterest)
	{
		dCompoundInterest = compoundInterest;
	}
	/**
	 * Sets the compRate.
	 * @param compRate The compRate to set
	 */
	public void setCompRate(double compRate)
	{
		dCompRate = compRate;
	}
	/**
	 * Sets the interest.
	 * @param interest The interest to set
	 */
	public void setInterest(double interest)
	{
		dInterest = interest;
	}
	/**
	 * Sets the interestRate.
	 * @param interestRate The interestRate to set
	 */
	public void setInterestRate(double interestRate)
	{
		dInterestRate = interestRate;
	}
	/**
	 * Sets the overDueAmount.
	 * @param overDueAmount The overDueAmount to set
	 */
	public void setOverDueAmount(double overDueAmount)
	{
		dOverDueAmount = overDueAmount;
	}
	/**
	 * Sets the overDueInterest.
	 * @param overDueInterest The overDueInterest to set
	 */
	public void setOverDueInterest(double overDueInterest)
	{
		dOverDueInterest = overDueInterest;
	}
	/**
	 * Sets the overDueRate.
	 * @param overDueRate The overDueRate to set
	 */
	public void setOverDueRate(double overDueRate)
	{
		dOverDueRate = overDueRate;
	}
	/**
	 * Sets the suretyFee.
	 * @param suretyFee The suretyFee to set
	 */
	public void setSuretyFee(double suretyFee)
	{
		dSuretyFee = suretyFee;
	}
	/**
	 * Sets the suretyRate.
	 * @param suretyRate The suretyRate to set
	 */
	public void setSuretyRate(double suretyRate)
	{
		dSuretyRate = suretyRate;
	}
	/**
	 * Sets the total.
	 * @param total The total to set
	 */
	public void setTotal(double total)
	{
		dTotal = total;
	}
	/**
	 * Sets the currencyID.
	 * @param currencyID The currencyID to set
	 */
	public void setCurrencyID(long currencyID)
	{
		lCurrencyID = currencyID;
	}
	/**
	 * Sets the loanAccountID.
	 * @param loanAccountID The loanAccountID to set
	 */
	public void setLoanAccountID(long loanAccountID)
	{
		lLoanAccountID = loanAccountID;
	}
	/**
	 * Sets the loanNoteID.
	 * @param loanNoteID The loanNoteID to set
	 */
	public void setLoanNoteID(long loanNoteID)
	{
		lLoanNoteID = loanNoteID;
	}
	/**
	 * Sets the officeID.
	 * @param officeID The officeID to set
	 */
	public void setOfficeID(long officeID)
	{
		lOfficeID = officeID;
	}
	/**
	 * Sets the subAccountID.
	 * @param subAccountID The subAccountID to set
	 */
	public void setSubAccountID(long subAccountID)
	{
		lSubAccountID = subAccountID;
	}
	/**
	 * Sets the clearInterest.
	 * @param clearInterest The clearInterest to set
	 */
	public void setClearInterest(Timestamp clearInterest)
	{
		tsClearInterest = clearInterest;
	}
	/**
	 * Sets the commissionStart.
	 * @param commissionStart The commissionStart to set
	 */
	public void setCommissionStart(Timestamp commissionStart)
	{
		tsCommissionStart = commissionStart;
	}
	/**
	 * Sets the compInterestStart.
	 * @param compInterestStart The compInterestStart to set
	 */
	public void setCompInterestStart(Timestamp compInterestStart)
	{
		tsCompInterestStart = compInterestStart;
	}
	/**
	 * Sets the interestStart.
	 * @param interestStart The interestStart to set
	 */
	public void setInterestStart(Timestamp interestStart)
	{
		tsInterestStart = interestStart;
	}
	/**
	 * Sets the overDueStart.
	 * @param overDueStart The overDueStart to set
	 */
	public void setOverDueStart(Timestamp overDueStart)
	{
		tsOverDueStart = overDueStart;
	}
	/**
	 * Sets the suretyStart.
	 * @param suretyStart The suretyStart to set
	 */
	public void setSuretyStart(Timestamp suretyStart)
	{
		tsSuretyStart = suretyStart;
	}
	/**
	 * Returns the interestIncome.
	 * @return double
	 */
	public double getInterestIncome()
	{
		return dInterestIncome;
	}
	/**
	 * Returns the interestReceiveable.
	 * @return double
	 */
	public double getInterestReceiveable()
	{
		return dInterestReceiveable;
	}
	/**
	 * Sets the interestIncome.
	 * @param interestIncome The interestIncome to set
	 */
	public void setInterestIncome(double interestIncome)
	{
		dInterestIncome = interestIncome;
	}
	/**
	 * Sets the interestReceiveable.
	 * @param interestReceiveable The interestReceiveable to set
	 */
	public void setInterestReceiveable(double interestReceiveable)
	{
		dInterestReceiveable = interestReceiveable;
	}
	/**
	 * Returns the interestTax.
	 * @return double
	 */
	public double getInterestTax()
	{
		return dInterestTax;
	}

	/**
	 * Sets the interestTax.
	 * @param interestTax The interestTax to set
	 */
	public void setInterestTax(double interestTax)
	{
		dInterestTax = interestTax;
	}

	/**
	 * Returns the interestTaxRate.
	 * @return double
	 */
	public double getInterestTaxRate()
	{
		return dInterestTaxRate;
	}

	/**
	 * Sets the interestTaxRate.
	 * @param interestTaxRate The interestTaxRate to set
	 */
	public void setInterestTaxRate(double interestTaxRate)
	{
		dInterestTaxRate = interestTaxRate;
	}

	/**
	 * @return
	 */
	public long getContractType() {
		return ContractType;
	}

	/**
	 * @param l
	 */
	public void setContractType(long l) {
		ContractType = l;
	}

}
