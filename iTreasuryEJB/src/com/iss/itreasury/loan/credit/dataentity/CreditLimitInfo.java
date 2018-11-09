/*
 * Created on 2004-8-11
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.credit.dataentity;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;
import java.sql.Timestamp;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CreditLimitInfo
    extends ITreasuryBaseDataEntity {
  private long id = -1;
  private long clientID = -1;

  private double creditAmount = 0; //信用额度
  private double amount = 0; //授信额度
  private double assureAmount = 0; //担保额度

  private long statusID = -1;
  private Timestamp startDate = null;
  private Timestamp endDate = null;
  private long inputUserID = -1;
  private Timestamp inputDate = null;
  private String remark = "";

  private long addOrMinus = -1; // 增加或减少
  private double creditChange = 0; //变化的额度

  private double canUseAmount = 0; //可用额度
  private double applyAmount = 0; //已申请占用额度
  private double usedAmount = 0; //已使用额度
  
  private long officeId = -1;        //办事处
  private long currencyId = -1;      //币种
  private long creditTypeId = -1;    //授信品种
  
  

    public long getOfficeId() {
		return officeId;
	}
	
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
		putUsedField("officeId", officeId);
	}
	
	public long getCurrencyId() {
		return currencyId;
	}
	
	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
		putUsedField("currencyId", currencyId);
	}
	
	public long getCreditTypeId() {
		return creditTypeId;
	}
	
	public void setCreditTypeId(long creditTypeId) {
		this.creditTypeId = creditTypeId;
		putUsedField("creditTypeId", creditTypeId);
	}

/**
   * @return
   */
  public long getId() {
    return id;
  }

  /**
   * @param l
   */
  public void setId(long l) {
    id = l;
    putUsedField("ID", id);
  }

  /**
   * @return
   */
  public long getClientID() {
    return clientID;
  }

  /**
   * @return
   */
  public double getCreditAmount() {
    return creditAmount;
  }

  /**
   * @return
   */
  public double getAmount() {
    return amount;
  }

  /**
   * @return
   */
  public double getAssureAmount() {
    return assureAmount;
  }

  /**
   * @return
   */
  public Timestamp getEndDate() {
    return endDate;
  }

  /**
   * @return
   */
  public Timestamp getInputDate() {
    return inputDate;
  }

  /**
   * @return
   */
  public long getInputUserID() {
    return inputUserID;
  }

  /**
   * @return
   */
  public Timestamp getStartDate() {
    return startDate;
  }

  /**
   * @return
   */
  public long getStatusID() {
    return statusID;
  }

  /**
   * @param l
   */
  public void setClientID(long l) {
    clientID = l;
    putUsedField("clientID", clientID);
  }

////////////////////////////////////////////////////////////////////////////////////////
  /**
   * @param d
   */
  public void setCreditAmount(double d) {
    creditAmount = d;
    putUsedField("creditAmount", creditAmount);
  }

  /**
   * @param d
   */
  public void setAmount(double d) {
    amount = d;
    putUsedField("amount", amount);
  }

  /**
   * @param d
   */
  public void setAssureAmount(double d) {
    assureAmount = d;
    putUsedField("assureAmount", assureAmount);
  }

////////////////////////////////////////////////////////////////////////////////////////////////////
  /**
   * @param timestamp
   */
  public void setEndDate(Timestamp timestamp) {
    endDate = timestamp;
    putUsedField("endDate", endDate);
  }

  /**
   * @param timestamp
   */
  public void setInputDate(Timestamp timestamp) {
    inputDate = timestamp;
    putUsedField("inputDate", inputDate);
  }

  /**
   * @param l
   */
  public void setInputUserID(long l) {
    inputUserID = l;
    putUsedField("inputUserID", inputUserID);
  }

  /**
   * @param timestamp
   */
  public void setStartDate(Timestamp timestamp) {
    startDate = timestamp;
    putUsedField("startDate", startDate);
  }

  /**
   * @param l
   */
  public void setStatusID(long l) {
    statusID = l;
    putUsedField("statusID", statusID);
  }

  /**
   * @return
   */
  public double getApplyAmount() {
    return applyAmount;
  }

  /**
   * @return
   */
  public double getCanUseAmount() {
    return canUseAmount;
  }

  /**
   * @return
   */
  public String getRemark() {
    return remark;
  }

  /**
   * @return
   */
  public double getUsedAmount() {
    return usedAmount;
  }

  /**
   * @param d
   */
  public void setApplyAmount(double d) {
    applyAmount = d;
  }

  /**
   * @param d
   */
  public void setCanUseAmount(double d) {
    canUseAmount = d;
  }

  /**
   * @param string
   */
  public void setRemark(String string) {
    remark = string;
    putUsedField("remark", remark);
  }

  /**
   * @param d
   */
  public void setUsedAmount(double d) {
    usedAmount = d;
  }

  /**
   * @return
   */
  public long getAddOrMinus() {
    return addOrMinus;
  }

  /**
   * @return
   */
  public double getCreditChange() {
    return creditChange;
  }

  /**
   * @param l
   */
  public void setAddOrMinus(long l) {
    addOrMinus = l;
  }

  /**
   * @param d
   */
  public void setCreditChange(double d) {
    creditChange = d;
  }

}
