package com.iss.itreasury.settlement.interest.dataentity;

import java.io.Serializable;

/**
 * 
 * @author Liuhuijun
 * Date of Creation    2003-11-05
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */



public class InterestAccountIDInfo implements Serializable{
  
    private long payInterestAccountID     = -1 ;      //付息（借方）账户ID
    private long receiveInterestAccountID = -1 ;      //收息（贷方）账户ID
    
    /**
     * constructor
     * @param mPayInterestAccountID 
     * @param mReceiveInterestAccountID
     */
    
    public InterestAccountIDInfo(long mPayInterestAccountID,long mReceiveInterestAccountID){
       this.payInterestAccountID     =  mPayInterestAccountID;
       this.receiveInterestAccountID =  mReceiveInterestAccountID;
    }
    
    /**
     * 设置付息（借方）账户ID
     * @param mPayInterestAccountID
     */
    
    public void setPayInterestAccountID(long mPayInterestAccountID){
       this.payInterestAccountID  = mPayInterestAccountID;    
    }
    
    /**
     * 返回付息（借方）账户ID
     * @return long
     */
    
    public long getPayInterestAccountID(){
       return payInterestAccountID;
    }
    
    
    /**
     * 设置收息（贷方）账户ID
     * @param mReceiveInterestAccountID
     */
    
    public void setReceiveInterestAccountID(long mReceiveInterestAccountID){
       this.receiveInterestAccountID = mReceiveInterestAccountID;
    }
    
    /**
     * 返回收息（贷方）账户ID
     * @return long
     */
    
    public long getReceiveInterestAccountID(){
       return receiveInterestAccountID;
    }




}