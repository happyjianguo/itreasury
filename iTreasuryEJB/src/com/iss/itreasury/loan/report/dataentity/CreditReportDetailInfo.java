/*
 * Created on 2004-8-25
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.report.dataentity;

import java.util.Collection;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CreditReportDetailInfo {
  private long lineType = -1; //1 标题 2 企业信息 3 小计，合计
  private String sNo = "";
  private String clientName = "";
  private String firstBalance = "";
  private CreditReportCell[] cellInfo = null;
  private String unUsedCredit = "";
  private String remark = "";
  private String creditLevel = "";
  private String creditAmount = "";
  private String unGrant;

  /**
   * @return
   */
  public String getClientName() {
    return clientName;
  }

  /**
   * @return
   */
  public long getLineType() {
    return lineType;
  }

  /**
   * @return
   */
  public String getSNo() {
    return sNo;
  }

  /**
   * @param string
   */
  public void setClientName(String string) {
    clientName = string;
  }

  /**
   * @param l
   */
  public void setLineType(long l) {
    lineType = l;
  }

  /**
   * @param string
   */
  public void setSNo(String string) {
    sNo = string;
  }

  /**
   * @return
   */
  public String getFirstBalance() {
    return firstBalance;
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
  public String getUnUsedCredit() {
    return unUsedCredit;
  }

  /**
   * @param string
   */
  public void setFirstBalance(String string) {
    firstBalance = string;
  }

  /**
   * @param string
   */
  public void setRemark(String string) {
    remark = string;
  }

  /**
   * @param string
   */
  public void setUnUsedCredit(String string) {
    unUsedCredit = string;
  }

  /**
   * @return
   */
  public String getCreditAmount() {
    return creditAmount;
  }

  /**
   * @return
   */
  public String getCreditLevel() {
    return creditLevel;
  }

  /**
   * @param string
   */
  public void setCreditAmount(String string) {
    creditAmount = string;
  }

  /**
   * @param string
   */
  public void setCreditLevel(String string) {
    creditLevel = string;
  }

  /**
   * @return
   */
  public CreditReportCell[] getCellInfo() {
    return cellInfo;
  }

  public String getUnGrant() {
    return unGrant;
  } /**
   * @param cells
   */
  public void setCellInfo(CreditReportCell[] cells) {
    cellInfo = cells;
  }

  public void setUnGrant(String unGrant) {
    this.unGrant = unGrant;
  }

}
