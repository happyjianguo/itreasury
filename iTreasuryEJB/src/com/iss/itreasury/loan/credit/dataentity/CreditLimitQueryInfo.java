/*
 * Created on 2004-8-13
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.credit.dataentity;

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
  private long startClientID = -1; //客户号开始
  private long endClientID = -1; //客户号结束
  private String startClientCode = ""; //客户号开始
  private String endClientCode = ""; //客户号结束
  private double startAmount = 0; //授信额度开始
  private double endAmount = 0; //授信额度结束
  private double startCreditAmount = 0; //信用额度开始
  private double endCreditAmount = 0; //信用额度结束
  private double startAssureAmount = 0; //担保额度开始
  private double endAssureAmount = 0; //担保额度结束
  private long statusID = -1; //状态
  private String startInputDate = null; //录入日期开始
  private String endInputDate = null; //录入日期结束
  private long clientID = -1;

  private String orderParamString = "";
  private long desc = -1;
  private long queryPurpose = -1; //1 修改 2 审核 3 激活 4 取消激活 5 冻结 6 取消冻结 7 查询
  
  private long officeId=-1; //added by mzh_fu 2007/03/22 为解决查询未区分办事处的问题

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

////新加的信用额度和担保额度的set和get方法
  /**
   * @param d
   */
  public void setStartCreditAmount(double d) {
    startCreditAmount = d;
  }

  /**
   * @param d
   */
  public void setStartAssureAmount(double d) {
    startAssureAmount = d;
  }

  /**
   * @return
   */
  public double getStartCreditAmount() {
    return startCreditAmount;
  }

  /**
   * @return
   */
  public double getStartAssureAmount() {
    return startAssureAmount;
  }

  /**
   * @param d
   */
  public void setEndCreditAmount(double d) {
    endCreditAmount = d;
  }

  /**
   * @param d
   */
  public void setEndAssureAmount(double d) {
    endAssureAmount = d;
  }

  /**
   * @return
   */
  public double getEndCreditAmount() {
    return endCreditAmount;
  }

  /**
   * @return
   */
  public double getEndAssureAmount() {
    return endAssureAmount;
  }

//////////////////////////////////////////////////////////
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

}
