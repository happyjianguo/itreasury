/*
 * Created on 2004-8-19
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.integrationcredit.dataentity;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CreditInfo {
  private CreditLimitInfo limitInfo = null;
  private CreditProductInfo productInfo = null;
  private CreditLimitChangeInfo  changeInfo=null;

  /**
   * @return
   */
  public CreditLimitInfo getLimitInfo() {
    return limitInfo;
  }

  /**
   * @return
   */
  public CreditProductInfo getProductInfo() {
    return productInfo;
  }

  /**
   * @param info
   */
  public void setLimitInfo(CreditLimitInfo info) {
    limitInfo = info;
  }

  /**
   * @param info
   */
  public void setProductInfo(CreditProductInfo info) {
    productInfo = info;
  }

public CreditLimitChangeInfo getChangeInfo() {
	return changeInfo;
}

public void setChangeInfo(CreditLimitChangeInfo changeInfo) {
	this.changeInfo = changeInfo;
}

}
