/*
 * Created on 2004-7-5
 *
 * Title:				iTreasury
 * @author             	yfan 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */
package com.iss.itreasury.securities.securitiescontract.dataentity;

import java.sql.Timestamp;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SecuritiesContractInterestInfo
{
    private long officeID = -1;                          //������ID
	private long currencyID = -1;                        //���ֺ�
	private long[] contractID = null;             	 	 //��ͬID
	private String[] contractCode = null;             	 //��ͬCode
	private long transactionTypeID = -1;                 //��������
	private Timestamp interestDate = null;          	 //��������	
	private long inputUserID = -1;                       //¼����
	private Timestamp inputDate = null;          	 	 //¼������	
	
    /**
     * @return Returns the contractCode.
     */
    public String[] getContractCode()
    {
        return contractCode;
    }
    /**
     * @param contractCode The contractCode to set.
     */
    public void setContractCode(String[] contractCode)
    {
        this.contractCode = contractCode;
    }
    /**
     * @return Returns the contractID.
     */
    public long[] getContractID()
    {
        return contractID;
    }
    /**
     * @param contractID The contractID to set.
     */
    public void setContractID(long[] contractID)
    {
        this.contractID = contractID;
    }
    /**
     * @return Returns the currencyID.
     */
    public long getCurrencyID()
    {
        return currencyID;
    }
    /**
     * @param currencyID The currencyID to set.
     */
    public void setCurrencyID(long currencyID)
    {
        this.currencyID = currencyID;
    }
    /**
     * @return Returns the inputUserID.
     */
    public long getInputUserID()
    {
        return inputUserID;
    }
    /**
     * @param inputUserID The inputUserID to set.
     */
    public void setInputUserID(long inputUserID)
    {
        this.inputUserID = inputUserID;
    }
    /**
     * @return Returns the interestDate.
     */
    public Timestamp getInterestDate()
    {
        return interestDate;
    }
    /**
     * @param interestDate The interestDate to set.
     */
    public void setInterestDate(Timestamp interestDate)
    {
        this.interestDate = interestDate;
    }
    /**
     * @return Returns the officeID.
     */
    public long getOfficeID()
    {
        return officeID;
    }
    /**
     * @param officeID The officeID to set.
     */
    public void setOfficeID(long officeID)
    {
        this.officeID = officeID;
    }
    /**
     * @return Returns the transactionTypeID.
     */
    public long getTransactionTypeID()
    {
        return transactionTypeID;
    }
    /**
     * @param transactionTypeID The transactionTypeID to set.
     */
    public void setTransactionTypeID(long transactionTypeID)
    {
        this.transactionTypeID = transactionTypeID;
    }
    /**
     * @return Returns the inputDate.
     */
    public Timestamp getInputDate()
    {
        return inputDate;
    }
    /**
     * @param inputDate The inputDate to set.
     */
    public void setInputDate(Timestamp inputDate)
    {
        this.inputDate = inputDate;
    }
}
