/*
 * Created on 2004-8-11
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.integrationcredit.dataentity;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;
import java.sql.Timestamp;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CreditLimitInfo extends ITreasuryBaseDataEntity {
  private long id = -1;
  private long officeID = -1;   //办事处ID
  private long currencyID = -1;   //币种ID
  private long clientID = -1;
  private double amount = 0; //授信额度
  private long creditTypeID = -1;   //授信品种ID
  private long creditModeID = -1;    //授信方式ID
  private long statusID = -1;               //状态
  private Timestamp startDate = null;       //开始日期(授信额度)
  private Timestamp endDate = null;         //结束日期(授信额度)
  private long inputUserID = -1;       //录入人ID
  private Timestamp inputDate = null;       //录入日期
  private long activeUserID = -1;       //激活人ID
  private Timestamp activeDate = null;  //激活日期
  private long freeUserID = -1;         //冻结人ID
  private Timestamp freeDate = null;  //冻结日期
  
  //查询用
  private double tieupAmount = 0.0; //已占用金额
  
  
  
  /*--------------------------------------------不用的字段----------------------*/
  private double currentAmount = 0; //授信额度（当前额度）
  private long cID = -1; 		//副表ID
  private long cActiveUserID = -1;      //副表激活人ID
  private long changeTypeID = -1;       // 增加或减少
  //private double changeAmount = 0; 	//变化的额度
  private long cstatusID=-1;		// 变更授信状态
  private String remark = "";
  private long addOrMinus = -1; // 增加或减少
  private double creditChange = 0; //变化的额度
  private double canUseAmount = 0; //可用额度
  private double applyAmount = 0; //已申请占用额度
  private double usedAmount = 0; //已使用额度
  private double zyApplyAmount = 0;  //自营贷款已申请占用额度
  private double zyUsedAmount = 0;   //自营贷款已使用额度
  private double spApplyAmount = 0;  //商票已申请占用额度
  private double spUsedAmount = 0;   //商票已使用额度
  private boolean isSameInterval = true;  //贷款的开始日期和结束日期是否在同一授信额度区间内 true是 false否
  private double creditAmount = 0; //信用额度
  private double assureAmount = 0; //担保额度
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
  public double getAmount() {
    return amount;
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
  public long getActiveUserID() {
    return activeUserID;
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

  /**
   * @param d
   */
  public void setAmount(double d) {
    amount = d;
    putUsedField("amount", amount);
  }

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
   * @param l
   */
  public void setActiveUserID(long l) {
    activeUserID = l;
    putUsedField("activeUserID", activeUserID);
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
  

  public long getCreditTypeID() {
  	return creditTypeID;
  }

  public void setCreditTypeID(long creditTypeID) {
  	this.creditTypeID = creditTypeID;
  	putUsedField("creditTypeID", creditTypeID);
  }
  
  public Timestamp getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(Timestamp activeDate) {
		this.activeDate = activeDate;
		putUsedField("activeDate", activeDate);
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

	public long getCreditModeID() {
		return creditModeID;
	}

	public void setCreditModeID(long creditModeID) {
		this.creditModeID = creditModeID;
		putUsedField("creditModeID", creditModeID);
	}
	

	public long getFreeUserID() {
		return freeUserID;
	}

	public void setFreeUserID(long freeUserID) {
		this.freeUserID = freeUserID;
		putUsedField("freeUserID", freeUserID);
	}

	public Timestamp getFreeDate() {
		return freeDate;
	}

	public void setFreeDate(Timestamp freeDate) {
		this.freeDate = freeDate;
		putUsedField("freeDate", freeDate);
	}
	
	public double getTieupAmount() {
		return tieupAmount;
	}

	public void setTieupAmount(double tieupAmount) {
		this.tieupAmount = tieupAmount;
	}

	
	
	
	/************************************************************** 不使用的get 、 set/
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

public double getSpApplyAmount() {
	return spApplyAmount;
}

public void setSpApplyAmount(double spApplyAmount) {
	this.spApplyAmount = spApplyAmount;
}

public double getSpUsedAmount() {
	return spUsedAmount;
}

public void setSpUsedAmount(double spUsedAmount) {
	this.spUsedAmount = spUsedAmount;
}

public double getZyApplyAmount() {
	return zyApplyAmount;
}

public void setZyApplyAmount(double zyApplyAmount) {
	this.zyApplyAmount = zyApplyAmount;
}

public double getZyUsedAmount() {
	return zyUsedAmount;
}

public void setZyUsedAmount(double zyUsedAmount) {
	this.zyUsedAmount = zyUsedAmount;
}

public double getCurrentAmount() {
	return currentAmount;
}

public void setCurrentAmount(double currentAmount) {
	this.currentAmount = currentAmount;
}

public double getCreditAmount() {
	return creditAmount;
}

public void setCreditAmount(double creditAmount) {
	this.creditAmount = creditAmount;
}

public double getAssureAmount() {
	return assureAmount;
}

public void setAssureAmount(double assureAmount) {
	this.assureAmount = assureAmount;
}


public long getCActiveUserID() {
	return cActiveUserID;
}

public void setCActiveUserID(long activeUserID) {
	cActiveUserID = activeUserID;
}

/*public double getChangeAmount() {
	return changeAmount;
}

public void setChangeAmount(double changeAmount) {
	this.changeAmount = changeAmount;
}*/

public long getChangeTypeID() {
	return changeTypeID;
}

public void setChangeTypeID(long changeTypeID) {
	this.changeTypeID = changeTypeID;
}

public long getCID() {
	return cID;
}

public void setCID(long cid) {
	cID = cid;
}

public long getCstatusID() {
	return cstatusID;
}

public void setCstatusID(long cstatusID) {
	this.cstatusID = cstatusID;
}

public boolean isSameInterval() {
	return isSameInterval;
}

public void setSameInterval(boolean isSameInterval) {
	this.isSameInterval = isSameInterval;
}
}
