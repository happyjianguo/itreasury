/*
 * Created on 2004-8-11
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.integrationcredit.dataentity;

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
  private long officeID = -1;   //办事处ID
  private long currencyID = -1;   //币种ID

  private long creditTypeID = -1;      //授信品种
  private long loanTypeID = -1;        //贷款类型
  private long isControl = -1;         //是否进行额度控制
  private long statusID = -1;          //状态
  private double engrossRate = 0.0;      //控制比例
  private long updateUserID = -1;        //更新用户ID 
  private Timestamp updateTime = null;   //更新时间
  private long controlTypeID=-1;          //控制方式
  
  //--------------------------目前没有授信信用、担保业务----------------------------------------
  //private long creditControlTypeID=-1;
  //private long assureControlTypeID=-1;
  
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

public long getCurrencyID() {
	return currencyID;
}

public void setCurrencyID(long currencyID) {
	this.currencyID = currencyID;
	putUsedField("currencyID", currencyID);
}

public long getOfficeID() {
	return officeID;
}

public void setOfficeID(long officeID) {
	this.officeID = officeID;
	putUsedField("officeID", officeID);
}

public long getCreditTypeID() {
	return creditTypeID;
}

public void setCreditTypeID(long creditTypeID) {
	this.creditTypeID = creditTypeID;
	putUsedField("creditTypeID", creditTypeID);
}

public long getLoanTypeID() {
	return loanTypeID;
}

public void setLoanTypeID(long loanTypeID) {
	this.loanTypeID = loanTypeID;
	putUsedField("loanTypeID", loanTypeID);
}

/*public long getAssureControlTypeID() {
return assureControlTypeID;
}

public void setAssureControlTypeID(long assureControlTypeID) {
this.assureControlTypeID = assureControlTypeID;
}*/

/*public long getCreditControlTypeID() {
return creditControlTypeID;
}

public void setCreditControlTypeID(long creditControlTypeID) {
this.creditControlTypeID = creditControlTypeID;
}*/

}
