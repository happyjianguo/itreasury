/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-9
 */
package com.iss.itreasury.securities.securitiesaccount.dataentity;
import java.sql.Timestamp;
import com.iss.itreasury.securities.util.SECBaseDataEntity;
/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class AccountTypeInfo extends SECBaseDataEntity
{
	private long id = -1;                             //记录的ID
	private long recordId = -1;                       //记录的ID
	private long officeID = -1;                       //办事处ID
	private long currencyID = -1;                     //币种
	private long businessAttributeId = -1;            //业务性质 
	private long accountType = -1;                    //帐务类型
	private String termType = null;                   //期限类型
	private long term = -1;                           //期限
	private long termTypeID = -1;	
	private long clientID = -1;                       //业务单位
	private String seatCode = null;                   //证券业务席位号 
	private long counterpartID = -1;                  //交易对手/开户营业部ID
	private long accountID = -1;                      //开户资金帐号ID
	private long securitiesID = -1;                   //证券ID
	private String principalSubject = null;           //科目号：本金/成本/面值  
	private String interestSubject = null;            //科目号：利息/收益/支出 	
	private String marginSubject = null;              //科目号：证券销售差价收入/损失 
	private String suspenseInterestSubject = null;    //科目号：应计利息/损益调整
	private String unrealizedGainSubject = null;      //科目号：未实现利得
	private String discountSubject = null;            //科目号：折价  
	private String premiumSubject = null;             //科目号：溢价
	private String depreciateSubject = null;          //科目号：跌价/减值准备
	private String commissionSubject = null;          //科目号：手续费/佣金
	private String stampDutySubject = null;           //科目号：印花税 
	private String businessTaxSubject = null;         //科目号：营业税 
	private String overdueFineSubject = null;         //科目号：罚息
	private String reserveCapitalSubject = null;      //科目号：资本公积
	private String suspenseSubject = null;            //科目号：挂帐
	private String receivableSubject = null;          //科目号：其他应收款  
	private String payableSubject = null;             //科目号：其他应付款
	private long statusID = -1;                       //状态 
	private long inputUserID = -1;                    //录入人
	private Timestamp inputDate = null;               //录入时间
	private long updateUserID = -1;                   //修改人 
	private Timestamp updateDate = null;              //修改时间
	private long checkUserID = -1;                    //复核人
	private Timestamp checkDate = null;               //复核时间
	private long orderType = -1;                      //排序字段
	private long descOrAsc = -1;                      //升序或降序排列
	/**
	 * @return Returns the accountID.
	 */
	public long getAccountID()
	{
		return accountID;
	}
	/**
	 * @param accountID The accountID to set.
	 */
	public void setAccountID(long accountID)
	{
		putUsedField("accountID", accountID);
		this.accountID = accountID;
	}
	/**
	 * @return Returns the accountType.
	 */
	public long getAccountType()
	{
		return accountType;
	}
	/**
	 * @param accountType The accountType to set.
	 */
	public void setAccountType(long accountType)
	{
		putUsedField("accountType", accountType);
		this.accountType = accountType;
	}
	/**
	 * @return Returns the businessTaxSubject.
	 */
	public String getBusinessTaxSubject()
	{
		return businessTaxSubject;
	}
	/**
	 * @param businessTaxSubject The businessTaxSubject to set.
	 */
	public void setBusinessTaxSubject(String businessTaxSubject)
	{
		putUsedField("businessTaxSubject", businessTaxSubject);
		this.businessTaxSubject = businessTaxSubject;
	}
	/**
	 * @return Returns the checkDate.
	 */
	public Timestamp getCheckDate()
	{
		return checkDate;
	}
	/**
	 * @param checkDate The checkDate to set.
	 */
	public void setCheckDate(Timestamp checkDate)
	{
		putUsedField("checkDate", checkDate);
		this.checkDate = checkDate;
	}
	/**
	 * @return Returns the checkUserID.
	 */
	public long getCheckUserID()
	{
		return checkUserID;
	}
	/**
	 * @param checkUserID The checkUserID to set.
	 */
	public void setCheckUserID(long checkUserID)
	{
		putUsedField("checkUserID", checkUserID);
		this.checkUserID = checkUserID;
	}
	/**
	 * @return Returns the clientID.
	 */
	public long getClientID()
	{
		return clientID;
	}
	/**
	 * @param clientID The clientID to set.
	 */
	public void setClientID(long clientID)
	{
		putUsedField("clientID", clientID);
		this.clientID = clientID;
	}
	/**
	 * @return Returns the commissionSubject.
	 */
	public String getCommissionSubject()
	{
		return commissionSubject;
	}
	/**
	 * @param commissionSubject The commissionSubject to set.
	 */
	public void setCommissionSubject(String commissionSubject)
	{
		putUsedField("commissionSubject", commissionSubject);
		this.commissionSubject = commissionSubject;
	}
	/**
	 * @return Returns the counterpartID.
	 */
	public long getCounterpartID()
	{
		return counterpartID;
	}
	/**
	 * @param counterpartID The counterpartID to set.
	 */
	public void setCounterpartID(long counterpartID)
	{
		putUsedField("counterpartID", counterpartID);
		this.counterpartID = counterpartID;
	}
	/**
	 * @return Returns the currencyID.
	 */
	public long getCurrencyID()
	{
		return currencyID;
	}
	/**
	 * @param currencyID The currencyID to set.
	 */
	public void setCurrencyID(long currencyID)
	{
		putUsedField("currencyID", currencyID);
		this.currencyID = currencyID;
	}
	/**
	 * @return Returns the depreciateSubject.
	 */
	public String getDepreciateSubject()
	{
		return depreciateSubject;
	}
	/**
	 * @param depreciateSubject The depreciateSubject to set.
	 */
	public void setDepreciateSubject(String depreciateSubject)
	{
		putUsedField("depreciateSubject", depreciateSubject);
		this.depreciateSubject = depreciateSubject;
	}
	/**
	 * @return Returns the discountSubject.
	 */
	public String getDiscountSubject()
	{
		return discountSubject;
	}
	/**
	 * @param discountSubject The discountSubject to set.
	 */
	public void setDiscountSubject(String discountSubject)
	{
		putUsedField("discountSubject", discountSubject);
		this.discountSubject = discountSubject;
	}
	/**
	 * @return Returns the inputDate.
	 */
	public Timestamp getInputDate()
	{
		return inputDate;
	}
	/**
	 * @param inputDate The inputDate to set.
	 */
	public void setInputDate(Timestamp inputDate)
	{
		putUsedField("inputDate", inputDate);
		this.inputDate = inputDate;
	}
	/**
	 * @return Returns the inputUserID.
	 */
	public long getInputUserID()
	{
		return inputUserID;
	}
	/**
	 * @param inputUserID The inputUserID to set.
	 */
	public void setInputUserID(long inputUserID)
	{
		putUsedField("inputUserID", inputUserID);
		this.inputUserID = inputUserID;
	}
	/**
	 * @return Returns the interestSubject.
	 */
	public String getInterestSubject()
	{
		return interestSubject;
	}
	/**
	 * @param interestSubject The interestSubject to set.
	 */
	public void setInterestSubject(String interestSubject)
	{
		putUsedField("interestSubject", interestSubject);
		this.interestSubject = interestSubject;
	}
	/**
	 * @return Returns the marginSubject.
	 */
	public String getMarginSubject()
	{
		return marginSubject;
	}
	/**
	 * @param marginSubject The marginSubject to set.
	 */
	public void setMarginSubject(String marginSubject)
	{
		putUsedField("marginSubject", marginSubject);
		this.marginSubject = marginSubject;
	}
	/**
	 * @return Returns the officeID.
	 */
	public long getOfficeID()
	{
		return officeID;
	}
	/**
	 * @param officeID The officeID to set.
	 */
	public void setOfficeID(long officeID)
	{
		putUsedField("officeID", officeID);
		this.officeID = officeID;
	}
	/**
	 * @return Returns the overdueFineSubject.
	 */
	public String getOverdueFineSubject()
	{
		return overdueFineSubject;
	}
	/**
	 * @param overdueFineSubject The overdueFineSubject to set.
	 */
	public void setOverdueFineSubject(String overdueFineSubject)
	{
		putUsedField("overdueFineSubject", overdueFineSubject);
		this.overdueFineSubject = overdueFineSubject;
	}
	/**
	 * @return Returns the premiumSubject.
	 */
	public String getPremiumSubject()
	{
		return premiumSubject;
	}
	/**
	 * @param premiumSubject The premiumSubject to set.
	 */
	public void setPremiumSubject(String premiumSubject)
	{
		putUsedField("premiumSubject", premiumSubject);
		this.premiumSubject = premiumSubject;
	}
	/**
	 * @return Returns the principalSubject.
	 */
	public String getPrincipalSubject()
	{
		return principalSubject;
	}
	/**
	 * @param principalSubject The principalSubject to set.
	 */
	public void setPrincipalSubject(String principalSubject)
	{
		putUsedField("principalSubject", principalSubject);
		this.principalSubject = principalSubject;
	}
	/**
	 * @return Returns the reserveCapitalSubject.
	 */
	public String getReserveCapitalSubject()
	{
		return reserveCapitalSubject;
	}
	/**
	 * @param reserveCapitalSubject The reserveCapitalSubject to set.
	 */
	public void setReserveCapitalSubject(String reserveCapitalSubject)
	{
		putUsedField("reserveCapitalSubject", reserveCapitalSubject);
		this.reserveCapitalSubject = reserveCapitalSubject;
	}
	/**
	 * @return Returns the seatCode.
	 */
	public String getSeatCode()
	{
		return seatCode;
	}
	/**
	 * @param seatCode The seatCode to set.
	 */
	public void setSeatCode(String seatCode)
	{
		putUsedField("seatCode", seatCode);
		this.seatCode = seatCode;
	}
	/**
	 * @return Returns the securitiesID.
	 */
	public long getSecuritiesID()
	{
		return securitiesID;
	}
	/**
	 * @param securitiesID The securitiesID to set.
	 */
	public void setSecuritiesID(long securitiesID)
	{
		putUsedField("securitiesID", securitiesID);
		this.securitiesID = securitiesID;
	}
	/**
	 * @return Returns the stampDutySubject.
	 */
	public String getStampDutySubject()
	{
		return stampDutySubject;
	}
	/**
	 * @param stampDutySubject The stampDutySubject to set.
	 */
	public void setStampDutySubject(String stampDutySubject)
	{
		putUsedField("stampDutySubject", stampDutySubject);
		this.stampDutySubject = stampDutySubject;
	}
	/**
	 * @return Returns the statusID.
	 */
	public long getStatusID()
	{
		return statusID;
	}
	/**
	 * @param statusID The statusID to set.
	 */
	public void setStatusID(long statusID)
	{
		putUsedField("statusID", statusID);
		this.statusID = statusID;
	}
	/**
	 * @return Returns the suspenseInterestSubject.
	 */
	public String getSuspenseInterestSubject()
	{
		return suspenseInterestSubject;
	}
	/**
	 * @param suspenseInterestSubject The suspenseInterestSubject to set.
	 */
	public void setSuspenseInterestSubject(String suspenseInterestSubject)
	{
		putUsedField("suspenseInterestSubject", suspenseInterestSubject);
		this.suspenseInterestSubject = suspenseInterestSubject;
	}
	/**
	 * @return Returns the suspenseSubject.
	 */
	public String getSuspenseSubject()
	{
		return suspenseSubject;
	}
	/**
	 * @param suspenseSubject The suspenseSubject to set.
	 */
	public void setSuspenseSubject(String suspenseSubject)
	{
		putUsedField("suspenseSubject", suspenseSubject);
		this.suspenseSubject = suspenseSubject;
	}
	/**
	 * @return Returns the term.
	 */
	public long getTerm()
	{
		return term;
	}
	/**
	 * @param term The term to set.
	 */
	public void setTerm(long term)
	{
		putUsedField("term", term);
		this.term = term;
	}
	/**
	 * @return Returns the termType.
	 */
	public String getTermType()
	{
		return termType;
	}
	/**
	 * @param termType The termType to set.
	 */
	public void setTermType(String termType)
	{
		putUsedField("termType", termType);
		this.termType = termType;
	}
	/**
	 * @return Returns the unrealizedGainSubject.
	 */
	public String getUnrealizedGainSubject()
	{
		return unrealizedGainSubject;
	}
	/**
	 * @param unrealizedGainSubject The unrealizedGainSubject to set.
	 */
	public void setUnrealizedGainSubject(String unrealizedGainSubject)
	{
		putUsedField("unrealizedGainSubject", unrealizedGainSubject);
		this.unrealizedGainSubject = unrealizedGainSubject;
	}
	/**
	 * @return Returns the updateDate.
	 */
	public Timestamp getUpdateDate()
	{
		return updateDate;
	}
	/**
	 * @param updateDate The updateDate to set.
	 */
	public void setUpdateDate(Timestamp updateDate)
	{
		putUsedField("updateDate", updateDate);
		this.updateDate = updateDate;
	}
	/**
	 * @return Returns the updateUserID.
	 */
	public long getUpdateUserID()
	{
		return updateUserID;
	}
	/**
	 * @param updateUserID The updateUserID to set.
	 */
	public void setUpdateUserID(long updateUserID)
	{
		putUsedField("updateUserID", updateUserID);
		this.updateUserID = updateUserID;
	}
	/**
	 * @return
	 */
	public long getBusinessAttributeId()
	{
		return businessAttributeId;
	}
	/**
	 * @param l
	 */
	public void setBusinessAttributeId(long l)
	{
		businessAttributeId = l;
	}
	/**
	 * @return
	 */
	public long getOrderType()
	{
		return orderType;
	}
	/**
	 * @param l
	 */
	public void setOrderType(long l)
	{
		orderType = l;
	}
	/**
	 * @return
	 */
	public long getRecordId()
	{
		return recordId;
	}
	/**
	 * @param l
	 */
	public void setRecordId(long l)
	{
		recordId = l;
	}
	/**
	 * @return
	 */
	public long getId() 
	{
		return this.id;
	}
	/**
	 * @param l
	 */
	public void setId(long id) 
	{
		this.id = id;
		putUsedField("id", id);		
	}
	/**
	 * @return Returns the payableSubject.
	 */
	public String getPayableSubject() {
		return payableSubject;
	}
	/**
	 * @param payableSubject The payableSubject to set.
	 */
	public void setPayableSubject(String payableSubject) {
		putUsedField("payableSubject", payableSubject);
		this.payableSubject = payableSubject;
	}
	/**
	 * @return Returns the receivableSubject.
	 */
	public String getReceivableSubject() {
		return receivableSubject;
	}
	/**
	 * @param receivableSubject The receivableSubject to set.
	 */
	public void setReceivableSubject(String receivableSubject) {
		putUsedField("receivableSubject", receivableSubject);
		this.receivableSubject = receivableSubject;
	}
	/**
	 * @return
	 */
	public long getTermTypeID()
	{
		return termTypeID;
	}

	/**
	 * @param l
	 */
	public void setTermTypeID(long termTypeID)
	{
		putUsedField("termTypeID", termTypeID);
		this.termTypeID = termTypeID;
	}

	/**
	 * @return
	 */
	public long getDescOrAsc()
	{
		return descOrAsc;
	}

	/**
	 * @param l
	 */
	public void setDescOrAsc(long l)
	{
		descOrAsc = l;
	}

}
