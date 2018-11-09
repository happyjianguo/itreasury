/*                                                                    
 * Created on 2003-10-23                                              
 *                                                                    
 * To change the template for this generated file go to               
 * Window - Preferences - Java - Code Generation - Code and Comments  
 */                                                                   
package com.iss.itreasury.settlement.interest.dataentity;             
                                                                      
import java.io.Serializable;                                          
import java.sql.Timestamp;                                            

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;
                                                                      
/**                                                                   
 * @author xrli                                                     
 *                                                                    
 * To change the template for this generated type comment go to       
 * Window - Preferences - Java - Code Generation - Code and Comments  
 */                                                                   
public class InterestSettmentInfo extends SettlementBaseDataEntity   
{
	private long ID = -1;                                  //����              
	private long OfficeID = -1;                            //����ID            
	private long CurrencyID = -1;                          //����ID            
	private String TransNo = "";                           //���׺�            
	private long TransactionTypeID = -1;                   //��������          
	private long AccountID = -1;                           //���˻�ID 
	private String AccountNo = "";                         //�˻��� 
	private String ContractNo = "";                        //��ͬ��
	private String PayFormNo = "";                         //�ſ�֪ͨ����        
	private long SubAccountID = -1;                        //���˻�ID          
	private long AccountTypeID = -1;                       //�˻�����          
	private long InterestType = -1;                        //��Ϣ����          
	private long OperationType = -1;                       //��������          
	private Timestamp InterestSettlementDate = null;	   //��Ϣ��            
	private Timestamp InterestStartDate = null;	           //��Ϣ��            
	private Timestamp InterestEndDate = null;	           //��Ϣ��	            
	private long InterestDays = -1;                        //��Ϣ����          
	private double BaseBalance = 0.0;                      //��Ϣ���          
	private double Rate = 0.0;                             //ִ������          
	private double PecisionInterest = 0.0;                 //��ȷ����Ϣֵ      
	private double Interest = 0.0;                         //��Ϣֵ            
	private double NegotiateBalance = 0.0;                 //Э������Ϣ���  
	private double NegotiateRate = 0.0;                    //Э������          
	private double NegotiatePecisionInterest = 0.0;        //��ȷ��Э����Ϣֵ  
	private double NegotiateInterest = 0.0;                //Э����Ϣֵ        
	private double InterestTaxRate = 0.0;                  //��Ϣ˰��          
	private double InterestTax = 0.0;                      //��Ϣ˰            
	private long PayInterestAccountID = -1;                //��Ϣ���跽���˻���
	private long ReceiveInterestAccountID = -1;            //��Ϣ���������˻���
	private Timestamp ExecuteDate = null;	               //ִ����            
	private long InputUserID = -1;                         //¼����            
	private String Abstract = "";                          //ժҪ              
	private String CheckAbstract = "";                     //����ժҪ          
	private long IsSave = -1;                              //�Ƿ񱣴�/�Ƿ��Ϣ 
	private long IsKeepAccount = -1;                       //�Ƿ����          
	private long IsSuccess = -1;                           //ִ���Ƿ�ɹ�      
	private String FaultReason = "";                       //ʧ��ԭ��	
	private long TransInterestFeeID = -1;                  //��Ӧ����ID
	private Timestamp AutoExecuteDate = null;	           //�Զ�ִ��ʱ��
	private long BatchNo = -1;                             //���κ�   
	private long StatusID = -1;                            //״̬  
	//add by yanliu
	private String FixedDepositNo = null;				   //���ڴ浥��
	private double LoanPreDrawInterest = 0.0;			   //���������Ϣ
	//add by boxu
	private Timestamp inputClearInterest = null;	           //ҳ��¼���Ϣ��
	
	private boolean isClearPartInterest = false;
	
	public Timestamp getInputClearInterest() {
		return inputClearInterest;
	}

	public void setInputClearInterest(Timestamp inputClearInterest) {
		this.inputClearInterest = inputClearInterest;
	}

	/**
	 * @return
	 */
	public String getAbstract()
	{
		return Abstract;
	}

	/**
	 * @return
	 */
	public long getAccountID()
	{
		return AccountID;
	}

	/**
	 * @return
	 */
	public long getAccountTypeID()
	{
		return AccountTypeID;
	}

	/**
	 * @return
	 */
	public double getBaseBalance()
	{
		return BaseBalance;
	}

	/**
	 * @return
	 */
	public String getCheckAbstract()
	{
		return CheckAbstract;
	}

	/**
	 * @return
	 */
	public long getCurrencyID()
	{
		return CurrencyID;
	}

	/**
	 * @return
	 */
	public Timestamp getExecuteDate()
	{
		return ExecuteDate;
	}

	/**
	 * @return
	 */
	public String getFaultReason()
	{
		return FaultReason;
	}

	/**
	 * @return
	 */
	public long getID()
	{
		return ID;
	}

	/**
	 * @return
	 */
	public long getInputUserID()
	{
		return InputUserID;
	}

	/**
	 * @return
	 */
	public double getInterest()
	{
		return Interest;
	}

	/**
	 * @return
	 */
	public long getInterestDays()
	{
		return InterestDays;
	}

	/**
	 * @return
	 */
	public Timestamp getInterestEndDate()
	{
		return InterestEndDate;
	}

	/**
	 * @return
	 */
	public Timestamp getInterestSettlementDate()
	{
		return InterestSettlementDate;
	}

	/**
	 * @return
	 */
	public Timestamp getInterestStartDate()
	{
		return InterestStartDate;
	}

	/**
	 * @return
	 */
	public double getInterestTax()
	{
		return InterestTax;
	}

	/**
	 * @return
	 */
	public double getInterestTaxRate()
	{
		return InterestTaxRate;
	}

	/**
	 * @return
	 */
	public long getInterestType()
	{
		return InterestType;
	}

	/**
	 * @return
	 */
	public long getIsKeepAccount()
	{
		return IsKeepAccount;
	}

	/**
	 * @return
	 */
	public long getIsSave()
	{
		return IsSave;
	}

	/**
	 * @return
	 */
	public long getIsSuccess()
	{
		return IsSuccess;
	}

	/**
	 * @return
	 */
	public double getNegotiateBalance()
	{
		return NegotiateBalance;
	}

	/**
	 * @return
	 */
	public double getNegotiateInterest()
	{
		return NegotiateInterest;
	}

	/**
	 * @return
	 */
	public double getNegotiatePecisionInterest()
	{
		return NegotiatePecisionInterest;
	}

	/**
	 * @return
	 */
	public double getNegotiateRate()
	{
		return NegotiateRate;
	}

	/**
	 * @return
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}

	/**
	 * @return
	 */
	public long getOperationType()
	{
		return OperationType;
	}

	/**
	 * @return
	 */
	public long getPayInterestAccountID()
	{
		return PayInterestAccountID;
	}

	/**
	 * @return
	 */
	public double getPecisionInterest()
	{
		return PecisionInterest;
	}

	/**
	 * @return
	 */
	public double getRate()
	{
		return Rate;
	}

	/**
	 * @return
	 */
	public long getReceiveInterestAccountID()
	{
		return ReceiveInterestAccountID;
	}

	/**
	 * @return
	 */
	public long getSubAccountID()
	{
		return SubAccountID;
	}

	/**
	 * @return
	 */
	public long getTransactionTypeID()
	{
		return TransactionTypeID;
	}

	/**
	 * @return
	 */
	public long getTransInterestFeeID()
	{
		return TransInterestFeeID;
	}

	/**
	 * @return
	 */
	public String getTransNo()
	{
		return TransNo;
	}

	/**
	 * @param string
	 */
	public void setAbstract(String string)
	{
		Abstract = string;
	}

	/**
	 * @param l
	 */
	public void setAccountID(long l)
	{
		AccountID = l;
	}

	/**
	 * @param l
	 */
	public void setAccountTypeID(long l)
	{
		AccountTypeID = l;
	}

	/**
	 * @param d
	 */
	public void setBaseBalance(double d)
	{
		BaseBalance = d;
	}

	/**
	 * @param string
	 */
	public void setCheckAbstract(String string)
	{
		CheckAbstract = string;
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		CurrencyID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setExecuteDate(Timestamp timestamp)
	{
		ExecuteDate = timestamp;
	}

	/**
	 * @param string
	 */
	public void setFaultReason(String string)
	{
		FaultReason = string;
	}

	/**
	 * @param l
	 */
	public void setID(long l)
	{
		ID = l;
	}

	/**
	 * @param l
	 */
	public void setInputUserID(long l)
	{
		InputUserID = l;
	}

	/**
	 * @param d
	 */
	public void setInterest(double d)
	{
		Interest = d;
	}

	/**
	 * @param l
	 */
	public void setInterestDays(long l)
	{
		InterestDays = l;
	}

	/**
	 * @param timestamp
	 */
	public void setInterestEndDate(Timestamp timestamp)
	{
		InterestEndDate = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setInterestSettlementDate(Timestamp timestamp)
	{
		InterestSettlementDate = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setInterestStartDate(Timestamp timestamp)
	{
		InterestStartDate = timestamp;
	}

	/**
	 * @param d
	 */
	public void setInterestTax(double d)
	{
		InterestTax = d;
	}

	/**
	 * @param d
	 */
	public void setInterestTaxRate(double d)
	{
		InterestTaxRate = d;
	}

	/**
	 * @param l
	 */
	public void setInterestType(long l)
	{
		InterestType = l;
	}

	/**
	 * @param l
	 */
	public void setIsKeepAccount(long l)
	{
		IsKeepAccount = l;
	}

	/**
	 * @param l
	 */
	public void setIsSave(long l)
	{
		IsSave = l;
	}

	/**
	 * @param l
	 */
	public void setIsSuccess(long l)
	{
		IsSuccess = l;
	}

	/**
	 * @param d
	 */
	public void setNegotiateBalance(double d)
	{
		NegotiateBalance = d;
	}

	/**
	 * @param d
	 */
	public void setNegotiateInterest(double d)
	{
		NegotiateInterest = d;
	}

	/**
	 * @param d
	 */
	public void setNegotiatePecisionInterest(double d)
	{
		NegotiatePecisionInterest = d;
	}

	/**
	 * @param d
	 */
	public void setNegotiateRate(double d)
	{
		NegotiateRate = d;
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		OfficeID = l;
	}

	/**
	 * @param l
	 */
	public void setOperationType(long l)
	{
		OperationType = l;
	}

	/**
	 * @param l
	 */
	public void setPayInterestAccountID(long l)
	{
		PayInterestAccountID = l;
	}

	/**
	 * @param d
	 */
	public void setPecisionInterest(double d)
	{
		PecisionInterest = d;
	}

	/**
	 * @param d
	 */
	public void setRate(double d)
	{
		Rate = d;
	}

	/**
	 * @param l
	 */
	public void setReceiveInterestAccountID(long l)
	{
		ReceiveInterestAccountID = l;
	}

	/**
	 * @param l
	 */
	public void setSubAccountID(long l)
	{
		SubAccountID = l;
	}

	/**
	 * @param l
	 */
	public void setTransactionTypeID(long l)
	{
		TransactionTypeID = l;
	}

	/**
	 * @param l
	 */
	public void setTransInterestFeeID(long l)
	{
		TransInterestFeeID = l;
	}

	/**
	 * @param string
	 */
	public void setTransNo(String string)
	{
		TransNo = string;
	}

	/**
	 * @return
	 */
	public String getAccountNo()
	{
		return AccountNo;
	}

	/**
	 * @return
	 */
	public String getContractNo()
	{
		return ContractNo;
	}

	/**
	 * @return
	 */
	public String getPayFormNo()
	{
		return PayFormNo;
	}

	/**
	 * @param string
	 */
	public void setAccountNo(String string)
	{
		AccountNo = string;
	}

	/**
	 * @param string
	 */
	public void setContractNo(String string)
	{
		ContractNo = string;
	}

	/**
	 * @param string
	 */
	public void setPayFormNo(String string)
	{
		PayFormNo = string;
	}

	/**
	 * @return
	 */
	public long getStatusID()
	{
		return StatusID;
	}

	/**
	 * @param l
	 */
	public void setStatusID(long l)
	{
		StatusID = l;
	}

	/**
	 * @return
	 */
	public Timestamp getAutoExecuteDate()
	{
		return AutoExecuteDate;
	}

	/**
	 * @return
	 */
	public long getBatchNo()
	{
		return BatchNo;
	}

	/**
	 * @param timestamp
	 */
	public void setAutoExecuteDate(Timestamp timestamp)
	{
		AutoExecuteDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setBatchNo(long l)
	{
		BatchNo = l;
	}

	public String getFixedDepositNo() {
		return FixedDepositNo;
	}

	public void setFixedDepositNo(String fixedDepositNo) {
		FixedDepositNo = fixedDepositNo;
	}

	public double getLoanPreDrawInterest() {
		return LoanPreDrawInterest;
	}

	public void setLoanPreDrawInterest(double loanPreDrawInterest) {
		LoanPreDrawInterest = loanPreDrawInterest;
	}

	public boolean isClearPartInterest() {
		return isClearPartInterest;
	}

	public void setClearPartInterest(boolean isClearPartInterest) {
		this.isClearPartInterest = isClearPartInterest;
	}

}                                           