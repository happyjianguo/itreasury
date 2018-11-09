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
  
    private long payInterestAccountID     = -1 ;      //��Ϣ���跽���˻�ID
    private long receiveInterestAccountID = -1 ;      //��Ϣ���������˻�ID
    
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
     * ���ø�Ϣ���跽���˻�ID
     * @param mPayInterestAccountID
     */
    
    public void setPayInterestAccountID(long mPayInterestAccountID){
       this.payInterestAccountID  = mPayInterestAccountID;    
    }
    
    /**
     * ���ظ�Ϣ���跽���˻�ID
     * @return long
     */
    
    public long getPayInterestAccountID(){
       return payInterestAccountID;
    }
    
    
    /**
     * ������Ϣ���������˻�ID
     * @param mReceiveInterestAccountID
     */
    
    public void setReceiveInterestAccountID(long mReceiveInterestAccountID){
       this.receiveInterestAccountID = mReceiveInterestAccountID;
    }
    
    /**
     * ������Ϣ���������˻�ID
     * @return long
     */
    
    public long getReceiveInterestAccountID(){
       return receiveInterestAccountID;
    }




}