package com.iss.itreasury.settlement.transfee.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.settlement.util.SETTConstant;
/**
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class TransFeeInfo implements java.io.Serializable
{
	private long ID = -1; //                          
	private long officeID = -1; //is '办事处';                         
	private long currencyID = -1; //is '币种';                    
	private String transNo = ""; //is '交易号';                       
	private long transactionTypeID = -1; //is '交易类型';  
	private long relatedTransTypeID = -1; //is '对应交易的类型';           
	private long relatedClientID = -1; //is '对应支付费用客户号';                      
	private long relatedAccountID = -1; //is '对应账户号';                     
	private String relatedFixedDepositNo = ""; //is '对应存单号';                
	private long relatedContractID = -1; //is '对应合同ID';                    
	private long relatedLoanNoteID = -1; //is '对应放款通知单ID';                    
	private long relatedSubAccountID = -1; //is '对应子账户ID';                  
	private String relatedTransNo = ""; //is '对应交易编号';                     
	private long feeTypeID = -1; //is '交易费用类型';                     
	private long feeBankID = -1; //is '交易费用开户行';                        
	private long accountID = -1; //is '支付费用账户号';                  
	private String extAcctNo = ""; //is '外部账户号';                     
	private String extAcctName = ""; //is '外部客户名称';  
	private long remitInBankID = -1; //is '汇入行';                    
	private long cashFlowID = -1; //is '现金流向';                    
	private double amount = 0.0; //is '金额';                        
	private String billNo = ""; //is '票据号';                        
	private long billTypeID = -1; //is '票据类型';                    
	private long billBankID = -1; //is '票据发放银行';                    
	private String payExtBankNo = ""; //is '提出行';                  
	private Timestamp interestStartDate = null; //is '起息日';                
	private Timestamp executeDate = null; //is '执行日';                      
	private Timestamp modifyDate = null; //is '修改时间：年月日时分秒';                       
	private long inputUserID = -1; //is '录入人';                   
	private Timestamp inputDate = null; //is '录入时间：年月日时分秒';                        
	private long checkUserID = -1; //is '复核人';                   
	private long signUserID = -1; //is '签认人';                    
	private long confirmUserID = -1; //is '确认人';                 
	private long confirmOfficeID = -1; //is '通存通兑对方办事处';               
	private long abstractID = -1; //is '摘要ID';                    
	private String strAbstract = ""; //is '摘要';                      
	private String checkAbstract = ""; //is '复核摘要';                 
	private String cancleCheckAbstract = ""; //is '取消复核摘要';           
	private String confirmAbstract = ""; //is '确认摘要';               
	private long[] statusIDs = null; //is '交易状态'               

	/**
	 * Constructor for TransFeeInfo.
	 */
	public TransFeeInfo()
	{
		super();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		StringBuffer sbResult = new StringBuffer(128);

		sbResult.append(this.getClass().getName() + " instance (hashCode=" + this.hashCode() + ")\r\n");
		sbResult.append("=========================================\r\n");

		//获得当前对象指定名称的Field对象
		java.lang.reflect.Field[] field = null;
		try
		{
			field = this.getClass().getDeclaredFields();

			if (field != null)
			{
				for (int i = 0; i < field.length; i++)
				{
					field[i].setAccessible(true);

					sbResult.append(field[i].getName() + " = ");
					sbResult.append(field[i].get(this) + ";\r\n");
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
		}

		return sbResult.toString();
	}

	/**
	 * perSave check whether there is repeated tran record for different
	 * transaction because different transaction has different check condition.
	 */
	public TransFeeInfo getQeureyInfo()
	{
		TransFeeInfo queryInfo = new TransFeeInfo();
		
		//按照不同业务类型校验重复交易
		queryInfo.setTransactionTypeID(this.getTransactionTypeID());
		
		//重复交易校验执行日
		queryInfo.setExecuteDate(this.getExecuteDate());
		
		//重复交易校验的交易状态为除过删除状态的所有有效交易
		queryInfo.setStatusIDs(
			new long[] {
				SETTConstant.TransactionStatus.CHECK,
				SETTConstant.TransactionStatus.CIRCLE,
				SETTConstant.TransactionStatus.CONFIRM,
				SETTConstant.TransactionStatus.NOTSIGN,
				SETTConstant.TransactionStatus.SAVE,
				SETTConstant.TransactionStatus.SIGN,
				SETTConstant.TransactionStatus.TEMPSAVE });

		queryInfo.setFeeTypeID(this.getFeeTypeID());
		queryInfo.setFeeBankID(this.getFeeBankID());
		queryInfo.setAccountID(this.getAccountID());
		queryInfo.setExtAcctNo(this.getExtAcctNo());
		queryInfo.setRemitInBankID(this.getRemitInBankID());
		queryInfo.setAmount(this.getAmount());
		
		return queryInfo;

	}
	
	/**
	 * Returns the abstractID.
	 * @return long
	 */
	public long getAbstractID()
	{
		return abstractID;
	}

	/**
	 * Returns the accountID.
	 * @return long
	 */
	public long getRelatedAccountID()
	{
		return relatedAccountID;
	}

	/**
	 * Returns the amount.
	 * @return double
	 */
	public double getAmount()
	{
		return amount;
	}

	/**
	 * Returns the bankID.
	 * @return long
	 */
	public long getFeeBankID()
	{
		return feeBankID;
	}

	/**
	 * Returns the billBankID.
	 * @return long
	 */
	public long getBillBankID()
	{
		return billBankID;
	}

	/**
	 * Returns the billNo.
	 * @return String
	 */
	public String getBillNo()
	{
		return billNo;
	}

	/**
	 * Returns the billTypeID.
	 * @return long
	 */
	public long getBillTypeID()
	{
		return billTypeID;
	}

	/**
	 * Returns the cancleCheckAbstract.
	 * @return String
	 */
	public String getCancleCheckAbstract()
	{
		return cancleCheckAbstract;
	}

	/**
	 * Returns the cashFlowID.
	 * @return long
	 */
	public long getCashFlowID()
	{
		return cashFlowID;
	}

	/**
	 * Returns the checkAbstract.
	 * @return String
	 */
	public String getCheckAbstract()
	{
		return checkAbstract;
	}

	/**
	 * Returns the checkUserID.
	 * @return long
	 */
	public long getCheckUserID()
	{
		return checkUserID;
	}

	/**
	 * Returns the clientID.
	 * @return long
	 */
	public long getRelatedClientID()
	{
		return relatedClientID;
	}

	/**
	 * Returns the confirmAbstract.
	 * @return String
	 */
	public String getConfirmAbstract()
	{
		return confirmAbstract;
	}

	/**
	 * Returns the confirmOfficeID.
	 * @return long
	 */
	public long getConfirmOfficeID()
	{
		return confirmOfficeID;
	}

	/**
	 * Returns the confirmUserID.
	 * @return long
	 */
	public long getConfirmUserID()
	{
		return confirmUserID;
	}

	/**
	 * Returns the contractID.
	 * @return long
	 */
	public long getRelatedContractID()
	{
		return relatedContractID;
	}

	/**
	 * Returns the currencyID.
	 * @return long
	 */
	public long getCurrencyID()
	{
		return currencyID;
	}

	/**
	 * Returns the executeDate.
	 * @return Timestamp
	 */
	public Timestamp getExecuteDate()
	{
		return executeDate;
	}

	/**
	 * Returns the extAcctName.
	 * @return String
	 */
	public String getExtAcctName()
	{
		return extAcctName;
	}

	/**
	 * Returns the extAcctNo.
	 * @return String
	 */
	public String getExtAcctNo()
	{
		return extAcctNo;
	}

	/**
	 * Returns the feeAccountID.
	 * @return long
	 */
	public long getAccountID()
	{
		return accountID;
	}

	/**
	 * Returns the feeTransNo.
	 * @return long
	 */
	public String getRelatedTransNo()
	{
		return relatedTransNo;
	}

	/**
	 * Returns the feeTypeID.
	 * @return long
	 */
	public long getFeeTypeID()
	{
		return feeTypeID;
	}

	/**
	 * Returns the fixedDepositNo.
	 * @return String
	 */
	public String getRelatedFixedDepositNo()
	{
		return relatedFixedDepositNo;
	}

	/**
	 * Returns the iD.
	 * @return long
	 */
	public long getID()
	{
		return ID;
	}

	/**
	 * Returns the inputDate.
	 * @return Timestamp
	 */
	public Timestamp getInputDate()
	{
		return inputDate;
	}

	/**
	 * Returns the inputUserID.
	 * @return long
	 */
	public long getInputUserID()
	{
		return inputUserID;
	}

	/**
	 * Returns the interestStartDate.
	 * @return Timestamp
	 */
	public Timestamp getInterestStartDate()
	{
		return interestStartDate;
	}

	/**
	 * Returns the loanNoteID.
	 * @return long
	 */
	public long getRelatedLoanNoteID()
	{
		return relatedLoanNoteID;
	}

	/**
	 * Returns the modifyDate.
	 * @return Timestamp
	 */
	public Timestamp getModifyDate()
	{
		return modifyDate;
	}

	/**
	 * Returns the officeID.
	 * @return long
	 */
	public long getOfficeID()
	{
		return officeID;
	}

	/**
	 * Returns the payExtBankNo.
	 * @return String
	 */
	public String getPayExtBankNo()
	{
		return payExtBankNo;
	}

	/**
	 * Returns the signUserID.
	 * @return long
	 */
	public long getSignUserID()
	{
		return signUserID;
	}

	/**
	 * Returns the statusID.
	 * @return long
	 */
	public long getStatusID()
	{
		long lResult = -1;
		if (this.statusIDs != null && this.statusIDs.length > 0)
		{
			lResult = this.statusIDs[0];
		}

		return lResult;
	}

	/**
	 * 用于当前Entity作为查询条件时
	 * @return long[]
	 */
	public long[] getStatusIDs()
	{
		return this.statusIDs;
	}

	/**
	 * Returns the a.
	 * @return String
	 */
	public String getAbstract()
	{
		return strAbstract;
	}

	/**
	 * Returns the subAccountID.
	 * @return long
	 */
	public long getRelatedSubAccountID()
	{
		return relatedSubAccountID;
	}

	/**
	 * Returns the transactionTypeID.
	 * @return long
	 */
	public long getTransactionTypeID()
	{
		return transactionTypeID;
	}

	/**
	 * Returns the transNo.
	 * @return String
	 */
	public String getTransNo()
	{
		return transNo;
	}

	/**
	 * Sets the abstractID.
	 * @param abstractID The abstractID to set
	 */
	public void setAbstractID(long abstractID)
	{
		this.abstractID = abstractID;
	}

	/**
	 * Sets the accountID.
	 * @param accountID The accountID to set
	 */
	public void setRelatedAccountID(long accountID)
	{
		this.relatedAccountID = accountID;
	}

	/**
	 * Sets the amount.
	 * @param amount The amount to set
	 */
	public void setAmount(double amount)
	{
		this.amount = amount;
	}

	/**
	 * Sets the bankID.
	 * @param bankID The bankID to set
	 */
	public void setFeeBankID(long bankID)
	{
		this.feeBankID = bankID;
	}

	/**
	 * Sets the billBankID.
	 * @param billBankID The billBankID to set
	 */
	public void setBillBankID(long billBankID)
	{
		this.billBankID = billBankID;
	}

	/**
	 * Sets the billNo.
	 * @param billNo The billNo to set
	 */
	public void setBillNo(String billNo)
	{
		this.billNo = billNo;
	}

	/**
	 * Sets the billTypeID.
	 * @param billTypeID The billTypeID to set
	 */
	public void setBillTypeID(long billTypeID)
	{
		this.billTypeID = billTypeID;
	}

	/**
	 * Sets the cancleCheckAbstract.
	 * @param cancleCheckAbstract The cancleCheckAbstract to set
	 */
	public void setCancleCheckAbstract(String cancleCheckAbstract)
	{
		this.cancleCheckAbstract = cancleCheckAbstract;
	}

	/**
	 * Sets the cashFlowID.
	 * @param cashFlowID The cashFlowID to set
	 */
	public void setCashFlowID(long cashFlowID)
	{
		this.cashFlowID = cashFlowID;
	}

	/**
	 * Sets the checkAbstract.
	 * @param checkAbstract The checkAbstract to set
	 */
	public void setCheckAbstract(String checkAbstract)
	{
		this.checkAbstract = checkAbstract;
	}

	/**
	 * Sets the checkUserID.
	 * @param checkUserID The checkUserID to set
	 */
	public void setCheckUserID(long checkUserID)
	{
		this.checkUserID = checkUserID;
	}

	/**
	 * Sets the clientID.
	 * @param clientID The clientID to set
	 */
	public void setRelatedClientID(long clientID)
	{
		this.relatedClientID = clientID;
	}

	/**
	 * Sets the confirmAbstract.
	 * @param confirmAbstract The confirmAbstract to set
	 */
	public void setConfirmAbstract(String confirmAbstract)
	{
		this.confirmAbstract = confirmAbstract;
	}

	/**
	 * Sets the confirmOfficeID.
	 * @param confirmOfficeID The confirmOfficeID to set
	 */
	public void setConfirmOfficeID(long confirmOfficeID)
	{
		this.confirmOfficeID = confirmOfficeID;
	}

	/**
	 * Sets the confirmUserID.
	 * @param confirmUserID The confirmUserID to set
	 */
	public void setConfirmUserID(long confirmUserID)
	{
		this.confirmUserID = confirmUserID;
	}

	/**
	 * Sets the contractID.
	 * @param contractID The contractID to set
	 */
	public void setRelatedContractID(long contractID)
	{
		this.relatedContractID = contractID;
	}

	/**
	 * Sets the currencyID.
	 * @param currencyID The currencyID to set
	 */
	public void setCurrencyID(long currencyID)
	{
		this.currencyID = currencyID;
	}

	/**
	 * Sets the executeDate.
	 * @param executeDate The executeDate to set
	 */
	public void setExecuteDate(Timestamp executeDate)
	{
		this.executeDate = executeDate;
	}

	/**
	 * Sets the extAcctName.
	 * @param extAcctName The extAcctName to set
	 */
	public void setExtAcctName(String extAcctName)
	{
		this.extAcctName = extAcctName;
	}

	/**
	 * Sets the extAcctNo.
	 * @param extAcctNo The extAcctNo to set
	 */
	public void setExtAcctNo(String extAcctNo)
	{
		this.extAcctNo = extAcctNo;
	}

	/**
	 * Sets the feeAccountID.
	 * @param feeAccountID The feeAccountID to set
	 */
	public void setAccountID(long accountID)
	{
		this.accountID = accountID;
	}

	/**
	 * Sets the feeTransNo.
	 * @param feeTransNo The feeTransNo to set
	 */
	public void setRelatedTransNo(String feeTransNo)
	{
		this.relatedTransNo = feeTransNo;
	}

	/**
	 * Sets the feeTypeID.
	 * @param feeTypeID The feeTypeID to set
	 */
	public void setFeeTypeID(long feeTypeID)
	{
		this.feeTypeID = feeTypeID;
	}

	/**
	 * Sets the fixedDepositNo.
	 * @param fixedDepositNo The fixedDepositNo to set
	 */
	public void setRelatedFixedDepositNo(String fixedDepositNo)
	{
		this.relatedFixedDepositNo = fixedDepositNo;
	}

	/**
	 * Sets the iD.
	 * @param iD The iD to set
	 */
	public void setID(long iD)
	{
		ID = iD;
	}

	/**
	 * Sets the inputDate.
	 * @param inputDate The inputDate to set
	 */
	public void setInputDate(Timestamp inputDate)
	{
		this.inputDate = inputDate;
	}

	/**
	 * Sets the inputUserID.
	 * @param inputUserID The inputUserID to set
	 */
	public void setInputUserID(long inputUserID)
	{
		this.inputUserID = inputUserID;
	}

	/**
	 * Sets the interestStartDate.
	 * @param interestStartDate The interestStartDate to set
	 */
	public void setInterestStartDate(Timestamp interestStartDate)
	{
		this.interestStartDate = interestStartDate;
	}

	/**
	 * Sets the loanNoteID.
	 * @param loanNoteID The loanNoteID to set
	 */
	public void setRelatedLoanNoteID(long loanNoteID)
	{
		this.relatedLoanNoteID = loanNoteID;
	}

	/**
	 * Sets the modifyDate.
	 * @param modifyDate The modifyDate to set
	 */
	public void setModifyDate(Timestamp modifyDate)
	{
		this.modifyDate = modifyDate;
	}

	/**
	 * Sets the officeID.
	 * @param officeID The officeID to set
	 */
	public void setOfficeID(long officeID)
	{
		this.officeID = officeID;
	}

	/**
	 * Sets the payExtBankNo.
	 * @param payExtBankNo The payExtBankNo to set
	 */
	public void setPayExtBankNo(String payExtBankNo)
	{
		this.payExtBankNo = payExtBankNo;
	}

	/**
	 * Sets the signUserID.
	 * @param signUserID The signUserID to set
	 */
	public void setSignUserID(long signUserID)
	{
		this.signUserID = signUserID;
	}

	/**
	 * Sets the statusID.
	 * @param statusID The statusID to set
	 */
	public void setStatusID(long statusID)
	{
		this.statusIDs = new long[] { statusID };
	}

	/**
	 * 用于当前Entity作为查询条件时
	 * @param statusIDs
	 */
	public void setStatusIDs(long[] statusIDs)
	{
		this.statusIDs = statusIDs;
	}

	/**
	 * Sets the a.
	 * @param a The a to set
	 */
	public void setAbstract(String a)
	{
		strAbstract = a;
	}

	/**
	 * Sets the subAccountID.
	 * @param subAccountID The subAccountID to set
	 */
	public void setRelatedSubAccountID(long subAccountID)
	{
		this.relatedSubAccountID = subAccountID;
	}

	/**
	 * Sets the transactionTypeID.
	 * @param transactionTypeID The transactionTypeID to set
	 */
	public void setTransactionTypeID(long transactionTypeID)
	{
		this.transactionTypeID = transactionTypeID;
	}

	/**
	 * Sets the transNo.
	 * @param transNo The transNo to set
	 */
	public void setTransNo(String transNo)
	{
		this.transNo = transNo;
	}

	/**
	 * Returns the relatedTransTypeID.
	 * @return long
	 */
	public long getRelatedTransTypeID()
	{
		return relatedTransTypeID;
	}

	/**
	 * Sets the relatedTransTypeID.
	 * @param relatedTransTypeID The relatedTransTypeID to set
	 */
	public void setRelatedTransTypeID(long relatedTransTypeID)
	{
		this.relatedTransTypeID = relatedTransTypeID;
	}

	/**
	 * Returns the remitInBankID.
	 * @return long
	 */
	public long getRemitInBankID()
	{
		return remitInBankID;
	}

	/**
	 * Sets the remitInBankID.
	 * @param remitInBankID The remitInBankID to set
	 */
	public void setRemitInBankID(long remitInBankID)
	{
		this.remitInBankID = remitInBankID;
	}

}
