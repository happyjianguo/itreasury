/*
 * Created on 2005-6-23
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.budget.awake.dataentity;

import java.sql.Connection;
import java.util.HashMap;

/**
 * @author weilu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AwakeInfo {

//  业务提醒信息
    public static HashMap AwakeMSG = new HashMap();
    private long OfficeID = -1;//办事处标识
    private long CurrencyID = -1;//币种标识
    private long clientID = -1;		//单位ID
    private long userID = -1;		//用户ID
    private Connection con = null;
    
    
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
     * @return Returns the con.
     */
    public Connection getCon() {
        return con;
    }
    /**
     * @param con The con to set.
     */
    public void setCon(Connection con) {
        this.con = con;
    }
    /**
     * @return Returns the currencyID.
     */
    public long getCurrencyID() {
        return CurrencyID;
    }
    /**
     * @param currencyID The currencyID to set.
     */
    public void setCurrencyID(long currencyID) {
        CurrencyID = currencyID;
    }
    /**
     * @return Returns the officeID.
     */
    public long getOfficeID() {
        return OfficeID;
    }
    /**
     * @param officeID The officeID to set.
     */
    public void setOfficeID(long officeID) {
        OfficeID = officeID;
    }
    
    /**
     * @return Returns the userID.
     */
    public long getUserID() {
        return userID;
    }
    /**
     * @param userID The userID to set.
     */
    public void setUserID(long userID) {
        this.userID = userID;
    }
}
