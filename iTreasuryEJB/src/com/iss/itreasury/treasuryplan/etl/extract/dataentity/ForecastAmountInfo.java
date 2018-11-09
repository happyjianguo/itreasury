/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-7-6
 */
package com.iss.itreasury.treasuryplan.etl.extract.dataentity;

import java.sql.Timestamp;




/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ForecastAmountInfo extends AbstractAmountInfo{
	
	

	private double forecastAmount = 0.0;              //预测发生数	
	private long subAccountID = -1;              //当本类作为参数类时传递子账户ID,数据库无此字段
	
	private long clientID= -1;                    //当本类作为参数类时传递子账户ID,数据库无此字段 
	private long accountID = -1;                  //当本类作为参数类时传递账户ID,数据库无此字段  
    Timestamp af_dtend  = null;

	
	/**
     * @return Returns the af_dtend.
     */
    public Timestamp getAf_dtend()
    {
        return af_dtend;
    }

    /**
     * @param af_dtend The af_dtend to set.
     */
    public void setAf_dtend(Timestamp af_dtend)
    {
        this.af_dtend = af_dtend;
    }

    public ForecastAmountInfo(){
	}
	
	public ForecastAmountInfo(long officeID, long currencyID, Timestamp extractDate, Timestamp forcastDate,long moduleID){
		this.setOfficeID(officeID);
		this.setCurrencyID(currencyID);
		this.setExecuteDate(extractDate);
		this.setTransactionDate(forcastDate);
		this.setModuleID(moduleID);
	}	
	
	/**
	 * @return Returns the forecastAmount.
	 */
	public double getForecastAmount() {
		return forecastAmount;
	}
	/**
	 * @param forecastAmount The forecastAmount to set.
	 */
	public void setForecastAmount(double forecastAmount) {
		putUsedField("forecastAmount", forecastAmount);
		this.forecastAmount = forecastAmount;
	}
	/**
	 * @return Returns the subAccountID.
	 */
	public long getSubAccountID() {
		return subAccountID;
	}
	/**
	 * @param subAccountID The subAccountID to set.
	 */
	public void setSubAccountID(long subAccountID) {
		this.subAccountID = subAccountID;
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
	}
	/**
	 * @return Returns the accountid.
	 */
	public long getAccountID() {
		return accountID;
	}
	/**
	 * @param accountid The accountid to set.
	 */
	public void setAccountID(long accountID) {
		this.accountID = accountID;
	}
}
