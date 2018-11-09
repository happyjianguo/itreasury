/*
 * Created on 2004-9-3
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.dataentity;
import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;
/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SubAccountTypeCurrentSettingInfo extends SettlementBaseDataEntity
{
	private long Id = -1;
	private long OfficeID = -1;
	private long CurrencyID = -1;
	private long AccountTypeID = -1;
	private String SubjectCode = "";
	private long StatusID = -1;
	private String PayInterestSubject = "";
	private String BookedInterestSubject = "";
	private String NegotiateInterestSubject ="";
	private String CommissionSubject = "";
	private long ClientID = -1;
	
	//added by mzh_fu 2008/04/29 国电需求“按账户下级分类”
	private long accountId = -1;	
	//added by xwhe 2008-05-1 国电需求“按存单下级分类”
	private String sDepositNO = "";
	/**
	 * Returns the accountTypeID.
	 * @return long
	 */
	public long getAccountTypeID()
	{
		return AccountTypeID;
	}

	/**
	 * Returns the bookedInterestSubject.
	 * @return String
	 */
	public String getBookedInterestSubject()
	{
		return BookedInterestSubject;
	}

	/**
	 * Returns the clientID.
	 * @return long
	 */
	public long getClientID()
	{
		return ClientID;
	}

	/**
	 * Returns the currencyID.
	 * @return long
	 */
	public long getCurrencyID()
	{
		return CurrencyID;
	}

	/**
	 * Returns the id.
	 * @return long
	 */
	public long getId()
	{
		return Id;
	}

	/**
	 * Returns the officeID.
	 * @return long
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}

	/**
	 * Returns the payInterestSubject.
	 * @return String
	 */
	public String getPayInterestSubject()
	{
		return PayInterestSubject;
	}

	/**
	 * Returns the statusID.
	 * @return long
	 */
	public long getStatusID()
	{
		return StatusID;
	}

	/**
	 * Returns the subjectCode.
	 * @return String
	 */
	public String getSubjectCode()
	{
		return SubjectCode;
	}

	/**
	 * Sets the accountTypeID.
	 * @param accountTypeID The accountTypeID to set
	 */
	public void setAccountTypeID(long accountTypeID)
	{
		AccountTypeID = accountTypeID;
		putUsedField("NACCOUNTTYPEID", accountTypeID);
	}

	/**
	 * Sets the bookedInterestSubject.
	 * @param bookedInterestSubject The bookedInterestSubject to set
	 */
	public void setBookedInterestSubject(String bookedInterestSubject)
	{
		BookedInterestSubject = bookedInterestSubject;
		putUsedField("SBOOKEDINTERESTSUBJECT", bookedInterestSubject);
	}

	/**
	 * Sets the clientID.
	 * @param clientID The clientID to set
	 */
	public void setClientID(long clientID)
	{
		ClientID = clientID;
		putUsedField("NCLIENTID", clientID);
	}

	/**
	 * Sets the currencyID.
	 * @param currencyID The currencyID to set
	 */
	public void setCurrencyID(long currencyID)
	{
		CurrencyID = currencyID;
		putUsedField("NCURRENCYID", currencyID);
	}

	/**
	 * Sets the id.
	 * @param id The id to set
	 */
	public void setId(long id)
	{
		Id = id;
		putUsedField("ID", id);
	}

	/**
	 * Sets the officeID.
	 * @param officeID The officeID to set
	 */
	public void setOfficeID(long officeID)
	{
		OfficeID = officeID;
		putUsedField("NOFFICEID", officeID);
	}

	/**
	 * Sets the payInterestSubject.
	 * @param payInterestSubject The payInterestSubject to set
	 */
	public void setPayInterestSubject(String payInterestSubject)
	{
		PayInterestSubject = payInterestSubject;
		putUsedField("SPAYINTERESTSUBJECT", payInterestSubject);
	}

	/**
	 * Sets the statusID.
	 * @param statusID The statusID to set
	 */
	public void setStatusID(long statusID)
	{
		StatusID = statusID;
		putUsedField("NSTATUSID", statusID);
	}

	/**
	 * Sets the subjectCode.
	 * @param subjectCode The subjectCode to set
	 */
	public void setSubjectCode(String subjectCode)
	{
		SubjectCode = subjectCode;
		putUsedField("SSUBJECTCODE", subjectCode);
	}

	/**
	 * Returns the negotiateInterestSubject.
	 * @return String
	 */
	public String getNegotiateInterestSubject()
	{
		return NegotiateInterestSubject;
	}

	/**
	 * Sets the negotiateInterestSubject.
	 * @param negotiateInterestSubject The negotiateInterestSubject to set
	 */
	public void setNegotiateInterestSubject(String negotiateInterestSubject)
	{
		NegotiateInterestSubject = negotiateInterestSubject;
		putUsedField("SNEGOTIATEINTERESTSUBJECT", negotiateInterestSubject);
	}

	
	public String getCommissionSubject()
	{
	
		return CommissionSubject;
	}

	
	public void setCommissionSubject(String commissionSubject)
	{
	
		CommissionSubject = commissionSubject;
		putUsedField("sCommissionSubject", commissionSubject);
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
		putUsedField("NACCOUNTID", accountId);
	}

	public String getSDepositNO() {
		return sDepositNO;
	}

	public void setSDepositNO(String depositNO) {
		sDepositNO = depositNO;
		putUsedField("SDEPOSITNO", depositNO);
	}

}
