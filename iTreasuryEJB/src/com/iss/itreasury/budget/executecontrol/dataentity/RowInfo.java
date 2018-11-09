package com.iss.itreasury.budget.executecontrol.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Hashtable;

import com.iss.itreasury.budget.util.BUDGETConstant;

/**
 * @author weilu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RowInfo  implements Serializable{

    /**
	 * ÐÐºÅ
	 */
	private int rowNum=-1;
	private String itemNo = "";
	private String transNo = "";	
	private String contractCode = "";
	private String payFormCode = "";
	private String clientNo = "";
	private String accountNo = "";
	private String excuteAmount = "";
	private String executeDate = "";
	private long itemID=-1;
	private long clientID=-1;
	private long accountID=-1;
	private String itemName="";
	private long accountType=-1;
	
	private Hashtable errors = new Hashtable(); 
	
    public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getExcuteAmount() {
		return excuteAmount;
	}
	public void setExcuteAmount(String excuteAmount) {
		this.excuteAmount = excuteAmount;
	}
	public String getExecuteDate() {
		return executeDate;
	}
	public void setExecuteDate(String executeDate) {
		this.executeDate = executeDate;
	}
	public String getPayFormCode() {
		return payFormCode;
	}
	public void setPayFormCode(String payFormCode) {
		this.payFormCode = payFormCode;
	}
	public String getTransNo() {
		return transNo;
	}
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	public String getClientNo() {
        return clientNo;
    }
    /**
     * @param clientNo The clientNo to set.
     */
    public void setClientNo(String clientNo) {
        this.clientNo = clientNo;
    }
   
    public Hashtable getErrors() {
        return errors;
    }
    /**
     * @param errors The errors to set.
     */
    public void setErrors(Hashtable errors) {
        this.errors = errors;
    }
  
    public String getItemNo() {
        return itemNo;
    }
    /**
     * @param itemNo The itemNo to set.
     */
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }
  
 
    public int getRowNum() {
        return rowNum;
    }
    /**
     * @param rowNum The rowNum to set.
     */
    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }
  
   
    public void putError(String param,String msg){
		if(param==null || param.equals("")||msg==null || msg.equals("")){
			
		}else{
			errors.put(param,msg);
		}
	}
	public long getAccountID() {
		return accountID;
	}
	public void setAccountID(long accountID) {
		this.accountID = accountID;
	}
	public long getAccountType() {
		return accountType;
	}
	public void setAccountType(long accountType) {
		this.accountType = accountType;
	}
	public long getClientID() {
		return clientID;
	}
	public void setClientID(long clientID) {
		this.clientID = clientID;
	}
	public long getItemID() {
		return itemID;
	}
	public void setItemID(long itemID) {
		this.itemID = itemID;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
}
