/*
 * Created on 2004-8-11
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.credit.dataentity;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;
import java.sql.*;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CreditProductInfo
    extends ITreasuryBaseDataEntity {
  private long id = -1;
  private long creditTypeID = -1;
  private long isControl = -1;
  private long statusID = -1;
  private double engrossRate = 0;
  private long updateUserID = -1;
  private Timestamp updateTime = null;
  private long controlTypeID=-1;
  private long creditControlTypeID=-1;
  private long assureControlTypeID=-1;
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
  public long getCreditTypeID() {
    return creditTypeID;
  }

  /**
   * @return
   */
  public double getEngrossRate() {
    return engrossRate;
  }

  /**
   * @return
   */
  public long getStatusID() {
    return statusID;
  }

  /**
   * @return
   */
  public Timestamp getUpdateTime() {
    return updateTime;
  }

  /**
   * @return
   */
  public long getUpdateUserID() {
    return updateUserID;
  }

  /**
   * @return
   */
  public long getIsControl() {
    return isControl;
  }

  /**
   * @param l
   */
  public void setCreditTypeID(long l) {
    creditTypeID = l;
    putUsedField("creditTypeID", creditTypeID);
  }

  /**
   * @param d
   */
  public void setEngrossRate(double d) {
    engrossRate = d;
    putUsedField("engrossRate", engrossRate);
  }

  /**
   * @param l
   */
  public void setStatusID(long l) {
    statusID = l;
    putUsedField("statusID", statusID);
  }

  /**
   * @param timestamp
   */
  public void setUpdateTime(Timestamp timestamp) {
    updateTime = timestamp;
    putUsedField("updateTime", updateTime);
  }

  /**
   * @param l
   */
  public void setUpdateUserID(long l) {
    updateUserID = l;
    putUsedField("updateUserID", updateUserID);
  }

  /**
   * @param l
   */
  public void setIsControl(long l) {
    isControl = l;
    putUsedField("isControl", isControl);
  }

/**
 * @return Returns the assureAmountControl.
 */
public long getAssureControlTypeID() {
    return assureControlTypeID;
}
/**
 * @param assureAmountControl The assureAmountControl to set.
 */
public void setAssureControlTypeID(long aac) {
    assureControlTypeID=aac;
    putUsedField("assureControlTypeID", assureControlTypeID);
}
/**
 * @return Returns the creditAmountControl.
 */
public long getCreditControlTypeID() {
    return creditControlTypeID;
}
/**
 * @param creditAmountControl The creditAmountControl to set.
 */
public void setCreditControlTypeID(long cac) {
    creditControlTypeID=cac;
    putUsedField("creditControlTypeID", creditControlTypeID);
}
/**
 * @return Returns the totalAmountControl.
 */
public long getControlTypeID() {
    return controlTypeID;
}
/**
 * @param totalAmountControl The totalAmountControl to set.
 */
public void setControlTypeID(long tac) {
    controlTypeID=tac;
    putUsedField("controlTypeID", controlTypeID);
}
}
