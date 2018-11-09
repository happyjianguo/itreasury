/*
 * Created on 2004-8-13
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.integrationcredit.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CreditLimitQueryInfo
    extends ITreasuryBaseDataEntity {
  private long id = -1;
  private long officeID = -1;   //办事处ID
  private long currencyID = -1;   //币种ID
  private long startClientID = -1; //客户ID开始
  private long endClientID = -1; //客户ID结束
  private String startClientCode = ""; //客户号开始
  private String endClientCode = ""; //客户号结束
  private double startAmount = 0; //授信额度开始
  private double endAmount = 0; //授信额度结束
  private double amount = 0; // 授信额度
  private long creditTypeID = -1;   //授信品种ID
  private long statusID = -1; //状态
  private String startInputDate = null; //录入日期开始
  private String endInputDate = null; //录入日期结束
  private long clientID = -1;//客户ID
  private long changeOperID = -1;  //变更操作ID
  private double currentAmount =0; //授信额度
  private long activeStatusID = -1 ;
  
  private Timestamp startDate = null;       //开始日期
  private Timestamp endDate = null;         //结束日期
  
  private String orderParamString = "";
  private long desc = -1;
  private long queryPurpose = -1; //1　链接查找  2 激活/取消激活 3 冻结/取消冻结 7 查询
  
  private long officeId=-1; //added by mzh_fu 2007/03/22 为解决查询未区分办事处的问题
  
  
  
  //--------------------------目前没有授信信用、担保业务----------------------------------------
  private double startCreditAmount = 0; //信用额度开始
  private double endCreditAmount = 0; //信用额度结束
  private double startAssureAmount = 0; //担保额度开始
  private double endAssureAmount = 0; //担保额度结束

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
  }

  /**
   * @return
   */
  public long getQueryPurpose() {
    return queryPurpose;
  }

  /**
   * @param l
   */
  public void setQueryPurpose(long l) {
    queryPurpose = l;
  }

  /**
   * @return
   */
  public long getDesc() {
    return desc;
  }

  /**
   * @return
   */
  public String getOrderParamString() {
    return orderParamString;
  }

  /**
   * @param l
   */
  public void setDesc(long l) {
    desc = l;
  }

  /**
   * @param string
   */
  public void setOrderParamString(String string) {
    orderParamString = string;
  }

  /**
   * @return
   */
  public long getEndClientID() {
    return endClientID;
  }

  /**
   * @return
   */
  public double getEndAmount() {
    return endAmount;
  }

  /**
   * @return
   */
  public long getStartClientID() {
    return startClientID;
  }

  /**
   * @return
   */
  public double getStartAmount() {
    return startAmount;
  }

  /**
   * @param l
   */
  public void setEndClientID(long l) {
    endClientID = l;
  }

  /**
   * @param d
   */
  public void setEndAmount(double d) {
    endAmount = d;
  }

  /**
   * @param l
   */
  public void setStartClientID(long l) {
    startClientID = l;
  }

  /**
   * @param d
   */
  public void setStartAmount(double d) {
    startAmount = d;
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
  public void setStatusID(long l) {
    statusID = l;
  }

  /**
   * @return
   */
  public String getEndInputDate() {
    return endInputDate;
  }

  /**
   * @return
   */
  public String getStartInputDate() {
    return startInputDate;
  }

  /**
   * @param string
   */
  public void setEndInputDate(String string) {
    endInputDate = string;
  }

  /**
   * @param string
   */
  public void setStartInputDate(String string) {
    startInputDate = string;
  }

  /**
   * @return
   */
  public long getClientID() {
    return clientID;
  }

  /**
   * @param l
   */
  public void setClientID(long l) {
    clientID = l;
  }

  /**
   * @return
   */
  public String getEndClientCode() {
    return endClientCode;
  }

  /**
   * @return
   */
  public String getStartClientCode() {
    return startClientCode;
  }

  /**
   * @param string
   */
  public void setEndClientCode(String string) {
    endClientCode = string;
  }

  /**
   * @param string
   */
  public void setStartClientCode(String string) {
    startClientCode = string;
  }

public long getOfficeId() {
	return officeId;
}

public void setOfficeId(long officeId) {
	this.officeId = officeId;
}

public double getEndAssureAmount() {
	return endAssureAmount;
}

public void setEndAssureAmount(double endAssureAmount) {
	this.endAssureAmount = endAssureAmount;
}

public double getEndCreditAmount() {
	return endCreditAmount;
}

public void setEndCreditAmount(double endCreditAmount) {
	this.endCreditAmount = endCreditAmount;
}

public double getStartAssureAmount() {
	return startAssureAmount;
}

public void setStartAssureAmount(double startAssureAmount) {
	this.startAssureAmount = startAssureAmount;
}

public double getStartCreditAmount() {
	return startCreditAmount;
}

public void setStartCreditAmount(double startCreditAmount) {
	this.startCreditAmount = startCreditAmount;
}

public long getCreditTypeID() {
	return creditTypeID;
}

public void setCreditTypeID(long creditTypeID) {
	this.creditTypeID = creditTypeID;
}

/**
 * @return
 */
public double getAmount() {
  return amount;
}

public void setAmount(double am) {
    amount = am;
   // putUsedField("amount", amount);
  }

/**
 * @param timestamp
 */
public void setStartDate(Timestamp timestamp) {
  startDate = timestamp;
  putUsedField("startDate", startDate);
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
public Timestamp getEndDate() {
  return endDate;
}
/**
 * @param timestamp
 */
public void setEndDate(Timestamp timestamp) {
  endDate = timestamp;
  putUsedField("endDate", endDate);
}

public long getChangeOperID() {
	return changeOperID;
}

public void setChangeOperID(long changeOperID) {
	this.changeOperID = changeOperID;
}

public double getCurrentAmount() {
	return currentAmount;
}

public void setCurrentAmount(double currentAmount) {
	this.currentAmount = currentAmount;
}

public long getCurrencyID() {
	return currencyID;
}

public void setCurrencyID(long currencyID) {
	this.currencyID = currencyID;
}

public long getOfficeID() {
	return officeID;
}

public void setOfficeID(long officeID) {
	this.officeID = officeID;
}

public long getActiveStatusID() {
	return activeStatusID;
}

public void setActiveStatusID(long activeStatusID) {
	this.activeStatusID = activeStatusID;
}


}
