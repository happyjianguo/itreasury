/*
 * Created on 2003-9-26
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transspecial.dataentity;
import java.io.Serializable;
/**
 *
 * <p>Title: SpecialOperationInfo Class </p>
 * <p>Description: 使用Value Object实现的特殊业务实体类，对应db数据库中sett_specialoperation表 </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: isoftstone</p>
 * @author yuqiangliao
 * @version 1.0
 */
public class SpecialOperationInfo implements Serializable
{
 
  //特殊业务删除标志
  private long nstatusid=-1;
  //内部编号
  private long id;
  //选定项目概述
  private String scontent= null;
  //特殊业务类型描述
  private String sname =null;
  //办事处编号
  private long nofficeid=-1;
  //币种
  private long nCurrencyID=-1;
  
  private long payRelation = 0 ;        //全哨修改 2010-5-26 增加了2个字段
  
  private long gatheringRelation = 0;   //全哨修改 2010-5-26 增加了2个字段

  // jiangqi 2011-07-01 特殊业务改造
  private long Ispayaccount = -1;//是否显示付款方客户信息
  private long Ispaybank = -1;//是否显示付款方开户行信息
  private long IspayGlEntry = -1;//是否显示付款方总账信息
  private long IsRecAccount = -1;//是否显示收款方客户信息
  private long IsRecBank = -1;//是否显示收款方开户行信息
  private long IsRecGlEntry = -1;//是否显示收款方总账信息    

  /**
   *获得特殊业务删除标志
   *
   * @return int
   */
  public long getNstatusid(){ return nstatusid; }

    /**
     *设置特殊业务删除标志
     *
     * @param nstatusid 业务删除标志位,类型 int
     */
  public void setNstatusid(long nstatusid){ this.nstatusid = nstatusid; }

    /**
     *获得内部编号
     * @return　long
     */
  public long getId(){ return id; }

  /**
   * 设置内部编号
   * @param id　内部编号，类型 long
   */
  public void setId(long id){ this.id = id; }

    /**
     *获得选定项目概述
     *
     * @return String
     */
  public String getScontent(){ return scontent; }

  /**
   * 设置选定项目概述
   *
   * @param scontent 选定项目概述，类型 String
   */
  public void setScontent(String scontent){ this.scontent = scontent; }

  /**
   * 获得特殊业务类型描述
   *
   * @return String
   */
  public String getSname(){ return sname; }

  /**
   * 设置特殊业务类型描述
   *
   * @param sname 特殊业务类型描述,类型 String
   */
  public void setSname(String sname){ this.sname = sname; }

  /**
   * 获得办事处编号
   *
   * @return long
   */
  public long getNofficeid()
  { 
	  return nofficeid; 
  }

  /**
   * 设置办事处编号
   *
   * @param nofficeid 特殊业务类型描述，类型 long
   */
  public void setNofficeid(long nofficeid)
  { 
	  this.nofficeid = nofficeid; 
  }
  /**
   * 获得办事处编号
   *
   * @return long
   */
  public long getNCurrencyID()
  { 
	  return nCurrencyID; 
  }

  /**
   * 设置办事处编号
   *
   * @param nofficeid 特殊业务类型描述，类型 long
   */
  public void setNCurrencyID(long nCurrencyID)
  { 
	  this.nCurrencyID = nCurrencyID; 
  }
  
public long getPayRelation() {
	return payRelation;
}

public void setPayRelation(long payRelation) {
	this.payRelation = payRelation;
}

public long getGatheringRelation() {
	return gatheringRelation;
}

public void setGatheringRelation(long gatheringRelation) {
	this.gatheringRelation = gatheringRelation;
}

/**
 * 获得 是否显示付款方客户信息 1 显示 0 不显示
 * @return the ispayaccount
 */
public long getIspayaccount() {
	return Ispayaccount;
}

/**
 * 设置 是否显示付款方客户信息 1 显示 0 不显示
 * @param ispayaccount the ispayaccount to set
 */
public void setIspayaccount(long ispayaccount) {
	Ispayaccount = ispayaccount;
}

/**
 * 获得 是否显示付款方开户行信息 1 显示 0 不显示
 * @return the ispaybank
 */
public long getIspaybank() {
	return Ispaybank;
}

/**
 * 设置 是否显示付款方开户行信息 1 显示 0 不显示
 * @param ispaybank the ispaybank to set
 */
public void setIspaybank(long ispaybank) {
	Ispaybank = ispaybank;
}

/**
 * 获得 是否显示付款方总账类信息 1 显示 0 不显示
 * @return the ispayGlEntry
 */
public long getIspayGlEntry() {
	return IspayGlEntry;
}

/**
 * 设置 是否显示付款方总账类信息 1 显示 0 不显示
 * @param ispayGlEntry the ispayGlEntry to set
 */
public void setIspayGlEntry(long ispayGlEntry) {
	IspayGlEntry = ispayGlEntry;
}

/**
 * 获得 是否显示收款方客户信息 1 显示 0 不显示
 * @return the isRecAccount
 */
public long getIsRecAccount() {
	return IsRecAccount;
}

/**
 * 设置 是否显示收款方客户信息 1 显示 0 不显示
 * @param isRecAccount the isRecAccount to set
 */
public void setIsRecAccount(long isRecAccount) {
	IsRecAccount = isRecAccount;
}

/**
 * 获得 是否显示收款方开户行信息 1 显示 0 不显示
 * @return the isRecBank
 */
public long getIsRecBank() {
	return IsRecBank;
}

/**
 * 设置 是否显示收款方开户行信息 1 显示 0 不显示
 * @param isRecBank the isRecBank to set
 */
public void setIsRecBank(long isRecBank) {
	IsRecBank = isRecBank;
}

/**
 * 获得 是否显示收款方总账类信息 1 显示 0 不显示
 * @return the isRecGlEntry
 */
public long getIsRecGlEntry() {
	return IsRecGlEntry;
}

/**
 * 设置 是否显示收款方总账类信息 1 显示 0 不显示
 * @param isRecGlEntry the isRecGlEntry to set
 */
public void setIsRecGlEntry(long isRecGlEntry) {
	IsRecGlEntry = isRecGlEntry;
}


}
