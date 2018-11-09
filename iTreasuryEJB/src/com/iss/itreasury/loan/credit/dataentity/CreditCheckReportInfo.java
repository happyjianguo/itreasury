/*
 * Created on 2005-6-23
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.credit.dataentity;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CreditCheckReportInfo
    extends ITreasuryBaseDataEntity {
  private long id = -1; //ID
  private long clientID = -1; //借款单位
  private String clientname="";
  private double amount = 0; //申请金额
  private double creditRate = 0; //信用比例
  private long statusID = -1; //检查报告状态
  private long inputUserID = -1; //录入人
  private Timestamp inputDateTime = null; //录入时间
  private long resultID = -1; //检查报告结果
  private String code = ""; //检查报告编号
  private long creditProductTypeID=-1;//授信产品类型id
  private long[] assureClientID = null; //担保单位
  private String[] assureClientName=null;
  private double[] assureRate = null; //担保比例
  private long[] creditTypeID = null; //授信额度类型（信用，担保）
  private double creditCheckReportID = 0; //授信额度检查报告
  private Timestamp startDate = null; //申请开始日期
  private Timestamp endDate = null; //申请结束日期
  private double productRate=0;//授信产品占用额度比例
  private double jkcreditamount=0;//该客户作为借款单位授信总金额信用部分
  private double jkassureamount=0;//该客户作为借款单位授信总金额担保部分
  private double usecreditamount=0;//该客户作为借款单位已用额度信用部分
  private double useassureamount=0;//该客户作为借款单位已用额度担保部分
  private double validcreditamount=0;//该客户作为借款单位可用额度信用部分
  private double validassureamount=0;//该客户作为借款单位可用额度担保部分
  private long creditresult=-1;//该客户作为借款单位信用部分检查结果
  private long assureresult=-1;//该客户作为借款单位担保部分检查结果
  private double[] dbcreditamount=null;//该客户作为担保单位授信总金额信用部分
  private double[] dbassureamount=null;//该客户作为担保单位授信总金额担保部分
  private double[] dbusecreditamount=null;//该客户作为担保单位已用额度信用部分
  private double[] dbuseassureamount=null;//该客户作为担保单位已用额度担保部分
  private double[] dbvalidcreditamount=null;//该客户作为担保单位可用额度信用部分
  private double[] dbvalidassureamount=null;//该客户作为担保单位可用额度担保部分
  private long[] dbcreditresult=null;//该客户作为担保单位信用部分检查结果
  private long[] dbassureresult=null;//该客户作为担保单位担保部分检查结果
  /**
   * @return Returns the amount.
   */
  public double getAmount() {
    return amount;
  }

  /**
   * @param amount The amount to set.
   */
  public void setAmount(double amount) {
    this.amount = amount;
    putUsedField("this.amount", this.amount);
  }

  /**
   * @return Returns the creditRate.
   */
  public double getCreditRate() {
    return creditRate;
  }

  /**
   * @param creditRate The creditRate to set.
   */
  public void setCreditRate(double creditRate) {
    this.creditRate = creditRate;
    putUsedField("this.creditRate", this.creditRate);
  }

  /**
   * @return Returns the clientID.
   */
  public long getClientID() {
    return clientID;
  }

  /**
   * @param clientID The clientID to set.
   */
  public void setClientID(long clientID) {
    this.clientID = clientID;
    putUsedField("this.clientID", this.clientID);
  }

  /**
   * @return Returns the code.
   */
  public String getCode() {
    return code;
  }

  /**
   * @param code The code to set.
   */
  public void setCode(String code) {
    this.code = code;
    putUsedField("this.code", this.code);
  }

  /**
   * @return Returns the endDate.
   */
  public Timestamp getEndDate() {
    return endDate;
  }

  /**
   * @param endDate The endDate to set.
   */
  public void setEndDate(Timestamp endDate) {
    this.endDate = endDate;
    putUsedField("this.endDate", this.endDate);
  }

  /**
   * @return Returns the id.
   */
  public long getId() {
    return id;
  }

  /**
   * @param id The id to set.
   */
  public void setId(long id) {
    this.id = id;
    putUsedField("this.id", this.id);
  }

  /**
   * @return Returns the inputDateTime.
   */
  public Timestamp getInputDateTime() {
    return inputDateTime;
  }

  /**
   * @param inputDateTime The inputDateTime to set.
   */
  public void setInputDateTime(Timestamp inputDateTime) {
    this.inputDateTime = inputDateTime;
    putUsedField("this.inputDateTime", this.inputDateTime);
  }

  /**
   * @return Returns the inputUserID.
   */
  public long getInputUserID() {
    return inputUserID;
  }

  /**
   * @param inputUserID The inputUserID to set.
   */
  public void setInputUserID(long inputUserID) {
    this.inputUserID = inputUserID;
    putUsedField("this.inputUserID", this.inputUserID);
  }

  /**
   * @return Returns the resultID.
   */
  public long getResultID() {
    return resultID;
  }

  /**
   * @param resultID The resultID to set.
   */
  public void setResultID(long resultID) {
    this.resultID = resultID;
    putUsedField("this.resultID", this.resultID);
  }

  /**
   * @return Returns the startDate.
   */
  public Timestamp getStartDate() {
    return startDate;
  }

  /**
   * @param startDate The startDate to set.
   */
  public void setStartDate(Timestamp startDate) {
    this.startDate = startDate;
    putUsedField("this.startDate", this.startDate);
  }

  /**
   * @return Returns the statusID.
   */
  public long getStatusID() {
    return statusID;
  }

  /**
   * @param statusID The statusID to set.
   */
  public void setStatusID(long statusID) {
    this.statusID = statusID;
    putUsedField("this.statusID", this.statusID);
  }

  /**
   * @return Returns the assureClientID.
   */
  public long[] getAssureClientID() {
    return assureClientID;
  }

  /**
   * @param assureClientID The assureClientID to set.
   */
  public void setAssureClientID(long[] assureClientID) {
    this.assureClientID = assureClientID;
    putUsedField("this.assureClientID", this.assureClientID);
  }

  /**
   * @return Returns the assureRate.
   */
  public double[] getAssureRate() {
    return assureRate;
  }

  /**
   * @param assureRate The assureRate to set.
   */
  public void setAssureRate(double[] assureRate) {
    this.assureRate = assureRate;
    putUsedField("this.assureRate", this.assureRate);
  }

  /**
   * @return Returns the creditTypeID.
   */
  public long[] getCreditTypeID() {
    return creditTypeID;
  }

  /**
   * @param creditTypeID The creditTypeID to set.
   */
  public void setCreditTypeID(long[] creditTypeID) {
    this.creditTypeID = creditTypeID;
    putUsedField("this.creditTypeID", this.creditTypeID);
  }

  /**
   * @param creditCheckReportID The creditCheckReportID to set.
   */

  public void setCreditCheckReportID(double creditCheckReportID) {
    this.creditCheckReportID = creditCheckReportID;
    putUsedField("this.creditCheckReportID", this.creditCheckReportID);
  }

  /**
   * @return Returns the creditCheckReportID.
   */
  public double getCreditCheckReportID() {
    return creditCheckReportID;
  }

/**
 * @return Returns the creditProductTypeID.
 */
public long getCreditProductTypeID() {
    return creditProductTypeID;
}
/**
 * @param creditProductTypeID The creditProductTypeID to set.
 */
public void setCreditProductTypeID(long creditProductTypeID) {
    this.creditProductTypeID = creditProductTypeID;
}
/**
 * @return Returns the productRate.
 */
public double getProductRate() {
    return productRate;
}
/**
 * @param productRate The productRate to set.
 */
public void setProductRate(double productRate) {
    this.productRate = productRate;
}
/**
 * @return Returns the assureresult.
 */
public long getAssureresult() {
    return assureresult;
}
/**
 * @param assureresult The assureresult to set.
 */
public void setAssureresult(long assureresult) {
    this.assureresult = assureresult;
}
/**
 * @return Returns the creditresult.
 */
public long getCreditresult() {
    return creditresult;
}
/**
 * @param creditresult The creditresult to set.
 */
public void setCreditresult(long creditresult) {
    this.creditresult = creditresult;
}
/**
 * @return Returns the czassureamount.
 */
public double getJkassureamount() {
    return jkassureamount;
}
/**
 * @param czassureamount The czassureamount to set.
 */
public void setJkassureamount(double czassureamount) {
    this.jkassureamount = czassureamount;
}
/**
 * @return Returns the czcreditamount.
 */
public double getJkcreditamount() {
    return jkcreditamount;
}
/**
 * @param czcreditamount The czcreditamount to set.
 */
public void setJkcreditamount(double czcreditamount) {
    this.jkcreditamount = czcreditamount;
}
/**
 * @return Returns the useassureamount.
 */
public double getUseassureamount() {
    return useassureamount;
}
/**
 * @param useassureamount The useassureamount to set.
 */
public void setUseassureamount(double useassureamount) {
    this.useassureamount = useassureamount;
}
/**
 * @return Returns the usecreditamount.
 */
public double getUsecreditamount() {
    return usecreditamount;
}
/**
 * @param usecreditamount The usecreditamount to set.
 */
public void setUsecreditamount(double usecreditamount) {
    this.usecreditamount = usecreditamount;
}
/**
 * @return Returns the validassureamount.
 */
public double getValidassureamount() {
    return validassureamount;
}
/**
 * @param validassureamount The validassureamount to set.
 */
public void setValidassureamount(double validassureamount) {
    this.validassureamount = validassureamount;
}
/**
 * @return Returns the validcreditamount.
 */
public double getValidcreditamount() {
    return validcreditamount;
}
/**
 * @param validcreditamount The validcreditamount to set.
 */
public void setValidcreditamount(double validcreditamount) {
    this.validcreditamount = validcreditamount;
}
/**
 * @return Returns the dbassureamount.
 */
public double[] getDbassureamount() {
    return dbassureamount;
}
/**
 * @param dbassureamount The dbassureamount to set.
 */
public void setDbassureamount(double[] dbassureamount) {
    this.dbassureamount = dbassureamount;
}
/**
 * @return Returns the dbassureresult.
 */
public long[] getDbassureresult() {
    return dbassureresult;
}
/**
 * @param dbassureresult The dbassureresult to set.
 */
public void setDbassureresult(long[] dbassureresult) {
    this.dbassureresult = dbassureresult;
}
/**
 * @return Returns the dbcreditamount.
 */
public double[] getDbcreditamount() {
    return dbcreditamount;
}
/**
 * @param dbcreditamount The dbcreditamount to set.
 */
public void setDbcreditamount(double[] dbcreditamount) {
    this.dbcreditamount = dbcreditamount;
}
/**
 * @return Returns the dbcreditresult.
 */
public long[] getDbcreditresult() {
    return dbcreditresult;
}
/**
 * @param dbcreditresult The dbcreditresult to set.
 */
public void setDbcreditresult(long[] dbcreditresult) {
    this.dbcreditresult = dbcreditresult;
}
/**
 * @return Returns the dbuseassureamount.
 */
public double[] getDbuseassureamount() {
    return dbuseassureamount;
}
/**
 * @param dbuseassureamount The dbuseassureamount to set.
 */
public void setDbuseassureamount(double[] dbuseassureamount) {
    this.dbuseassureamount = dbuseassureamount;
}
/**
 * @return Returns the dbusecreditamount.
 */
public double[] getDbusecreditamount() {
    return dbusecreditamount;
}
/**
 * @param dbusecreditamount The dbusecreditamount to set.
 */
public void setDbusecreditamount(double[] dbusecreditamount) {
    this.dbusecreditamount = dbusecreditamount;
}
/**
 * @return Returns the dbvalidassureamount.
 */
public double[] getDbvalidassureamount() {
    return dbvalidassureamount;
}
/**
 * @param dbvalidassureamount The dbvalidassureamount to set.
 */
public void setDbvalidassureamount(double[] dbvalidassureamount) {
    this.dbvalidassureamount = dbvalidassureamount;
}
/**
 * @return Returns the dbvalidcreditamount.
 */
public double[] getDbvalidcreditamount() {
    return dbvalidcreditamount;
}
/**
 * @param dbvalidcreditamount The dbvalidcreditamount to set.
 */
public void setDbvalidcreditamount(double[] dbvalidcreditamount) {
    this.dbvalidcreditamount = dbvalidcreditamount;
}
/**
 * @return Returns the clientname.
 */
public String getClientname() {
    return clientname;
}
/**
 * @param clientname The clientname to set.
 */
public void setClientname(String clientname) {
    this.clientname = clientname;
}
/**
 * @return Returns the assureClientName.
 */
public String[] getAssureClientName() {
    return assureClientName;
}
/**
 * @param assureClientName The assureClientName to set.
 */
public void setAssureClientName(String[] assureClientName) {
    this.assureClientName = assureClientName;
}
}
