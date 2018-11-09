/*
 * Created on 2004-9-1
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transabatement.dataentity;
import java.sql.Timestamp;
import java.util.Vector;
import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;
/**
 *
 * <p>Title: TransSpecialOperationInfo�� </p>
 * <p>Description:��ת���������Զ�����ҵ��,��Ӧdb���ݿ���SETT_TRANSABATEMENT�� </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: isoftstone</p>
 * @author gqzhang
 * @version 1.0
 */
public class TransAbatementInfo extends SettlementBaseDataEntity
{
	private long ID = -1; //                  NUMBER                         ID                 
	private long TransNOID = -1; //NUMBER        Y                ����ID             
	private long TransactionTypeID = -1; //NUMBER        Y                ��������           
	private String TransNo = ""; //VARCHAR2(30)  Y                ���׺�             
	private long OfficeID = -1; //NUMBER        Y                ���´�             
	private long CurrencyID = -1; //NUMBER        Y                ����               
	private long ClientID = -1; //NUMBER        Y                ת���������ͻ�ID   
	private long AccountID = -1; //NUMBER        Y                ת�����������˻�ID 
	private long ContractID = -1; //NUMBER        Y                ת����������ͬ��   
	private long DueBillID = -1; //NUMBER        Y                ת��������ƾ֤��   
	private long GLID = -1; //NUMBER        Y                ���� 
	private double TotalAmount = 0.0; //NUMBER(21,6)  Y                �����ܽ��         
	private double AmountFromDiscount = 0.0; //NUMBER(21,6)  Y                ��Դ�����ֵĽ��   
	private long DiscountGLID = -1; //NUMBER        Y                ��������           
	private double AmountFromReDiscount = 0.0; //NUMBER(21,6)  Y                ��Դ��ת���ֵĽ�� 
	private long ReDiscountGLID = -1; //NUMBER        Y                ת��������         
	private Timestamp InterestStartDate = null; //DATE          Y                ��Ϣ��             
	private Timestamp ExecuteDate = null; //DATE          Y                ִ����             
	private String Abstract = ""; //VARCHAR2(100) Y                ժҪ               
	private long AffrimOfficeID = -1; //NUMBER        Y                ȷ�ϰ��´�         
	private long InputUserID = -1; //NUMBER        Y                ¼����             
	private long CheckUserID = -1; //NUMBER        Y                ������             
	private long SignUserID = -1; //NUMBER        Y                ǩ����             
	private long AffrimUserID = -1; //NUMBER        Y                ȷ����             
	private Timestamp InputDate = null; //DATE          Y                ¼������           
	private Timestamp ModifyDate = null; //DATE          Y                �޸�����ʱ��       
	private Timestamp CheckDate = null; //DATE          Y                ��������           
	private Timestamp SignDate = null; //DATE          Y                ǩ������           
	private Timestamp AffirmDate = null; //DATE          Y                ȷ������           
	private String CheckAbstract = ""; //VARCHAR2(100) Y                ���˱�ע           
	private long StatusID = -1; //NUMBER        Y                ״̬               
	private long SubAccountID = -1; //NUMBER        Y    
	private long DiscountTypeID = -1; //ת�������� ���ʽ/�ع�ʽ
	private Timestamp DiscountDate = null; //ת������
	Vector vctTransAbatementDetailInfo = null;
	/**
	 * Returns the a.
	 * @return String
	 */
	public String getAbstract()
	{
		return Abstract;
	}
	/**
	 * Returns the accountID.
	 * @return long
	 */
	public long getAccountID()
	{
		return AccountID;
	}
	/**
	 * Returns the affirmDate.
	 * @return Timestamp
	 */
	public Timestamp getAffirmDate()
	{
		return AffirmDate;
	}
	/**
	 * Returns the affrimOfficeID.
	 * @return long
	 */
	public long getAffrimOfficeID()
	{
		return AffrimOfficeID;
	}
	/**
	 * Returns the affrimUserID.
	 * @return long
	 */
	public long getAffrimUserID()
	{
		return AffrimUserID;
	}
	/**
	 * Returns the amountFromDiscount.
	 * @return double
	 */
	public double getAmountFromDiscount()
	{
		return AmountFromDiscount;
	}
	/**
	 * Returns the amountFromReDiscount.
	 * @return double
	 */
	public double getAmountFromReDiscount()
	{
		return AmountFromReDiscount;
	}
	/**
	 * Returns the checkAbstract.
	 * @return String
	 */
	public String getCheckAbstract()
	{
		return CheckAbstract;
	}
	/**
	 * Returns the checkDate.
	 * @return Timestamp
	 */
	public Timestamp getCheckDate()
	{
		return CheckDate;
	}
	/**
	 * Returns the checkUserID.
	 * @return long
	 */
	public long getCheckUserID()
	{
		return CheckUserID;
	}
	/**
	 * Returns the clientID.
	 * @return long
	 */
	public long getClientID()
	{
		return ClientID;
	}
	/**
	 * Returns the contractID.
	 * @return long
	 */
	public long getContractID()
	{
		return ContractID;
	}
	/**
	 * Returns the currencyID.
	 * @return long
	 */
	public long getCurrencyID()
	{
		return CurrencyID;
	}
	/**
	 * Returns the discountGLID.
	 * @return long
	 */
	public long getDiscountGLID()
	{
		return DiscountGLID;
	}
	/**
	 * Returns the dueBillID.
	 * @return long
	 */
	public long getDueBillID()
	{
		return DueBillID;
	}
	/**
	 * Returns the executeDate.
	 * @return Timestamp
	 */
	public Timestamp getExecuteDate()
	{
		return ExecuteDate;
	}
	/**
	 * Returns the gLID.
	 * @return long
	 */
	public long getGLID()
	{
		return GLID;
	}
	/**
	 * Returns the iD.
	 * @return long
	 */
	public long getId()
	{
		return ID;
	}
	/**
	 * Returns the inputDate.
	 * @return Timestamp
	 */
	public Timestamp getInputDate()
	{
		return InputDate;
	}
	/**
	 * Returns the inputUserID.
	 * @return long
	 */
	public long getInputUserID()
	{
		return InputUserID;
	}
	/**
	 * Returns the interestStartDate.
	 * @return Timestamp
	 */
	public Timestamp getInterestStartDate()
	{
		return InterestStartDate;
	}
	/**
	 * Returns the modifyDate.
	 * @return Timestamp
	 */
	public Timestamp getModifyDate()
	{
		return ModifyDate;
	}
	/**
	 * Returns the officeID.
	 * @return long
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}
	/**
	 * Returns the reDiscountGLID.
	 * @return long
	 */
	public long getReDiscountGLID()
	{
		return ReDiscountGLID;
	}
	/**
	 * Returns the signDate.
	 * @return Timestamp
	 */
	public Timestamp getSignDate()
	{
		return SignDate;
	}
	/**
	 * Returns the signUserID.
	 * @return long
	 */
	public long getSignUserID()
	{
		return SignUserID;
	}
	/**
	 * Returns the statusID.
	 * @return long
	 */
	public long getStatusID()
	{
		return StatusID;
	}
	/**
	 * Returns the subAccountID.
	 * @return long
	 */
	public long getSubAccountID()
	{
		return SubAccountID;
	}
	/**
	 * Returns the totalAmount.
	 * @return double
	 */
	public double getTotalAmount()
	{
		return TotalAmount;
	}
	/**
	 * Returns the transactionTypeID.
	 * @return long
	 */
	public long getTransactionTypeID()
	{
		return TransactionTypeID;
	}
	/**
	 * Returns the transNo.
	 * @return String
	 */
	public String getTransNo()
	{
		return TransNo;
	}
	/**
	 * Sets the a.
	 * @param a The a to set
	 */
	public void setAbstract(String a)
	{
		Abstract = a;
		putUsedField("Abstract", a);
	}
	/**
	 * Sets the accountID.
	 * @param accountID The accountID to set
	 */
	public void setAccountID(long accountID)
	{
		AccountID = accountID;
		putUsedField("AccountID", accountID);
	}
	/**
	 * Sets the affirmDate.
	 * @param affirmDate The affirmDate to set
	 */
	public void setAffirmDate(Timestamp affirmDate)
	{
		AffirmDate = affirmDate;
		putUsedField("affirmDate", affirmDate);
	}
	/**
	 * Sets the affrimOfficeID.
	 * @param affrimOfficeID The affrimOfficeID to set
	 */
	public void setAffrimOfficeID(long affrimOfficeID)
	{
		AffrimOfficeID = affrimOfficeID;
		putUsedField("AffrimOfficeID", affrimOfficeID);
	}
	/**
	 * Sets the affrimUserID.
	 * @param affrimUserID The affrimUserID to set
	 */
	public void setAffrimUserID(long affrimUserID)
	{
		AffrimUserID = affrimUserID;
		putUsedField("AffrimUserID", affrimUserID);
	}
	/**
	 * Sets the amountFromDiscount.
	 * @param amountFromDiscount The amountFromDiscount to set
	 */
	public void setAmountFromDiscount(double amountFromDiscount)
	{
		AmountFromDiscount = amountFromDiscount;
		putUsedField("AmountFromDiscount", amountFromDiscount);
	}
	/**
	 * Sets the amountFromReDiscount.
	 * @param amountFromReDiscount The amountFromReDiscount to set
	 */
	public void setAmountFromReDiscount(double amountFromReDiscount)
	{
		AmountFromReDiscount = amountFromReDiscount;
		putUsedField("AmountFromReDiscount", amountFromReDiscount);
	}
	/**
	 * Sets the checkAbstract.
	 * @param checkAbstract The checkAbstract to set
	 */
	public void setCheckAbstract(String checkAbstract)
	{
		CheckAbstract = checkAbstract;
		putUsedField("CheckAbstract", checkAbstract);
	}
	/**
	 * Sets the checkDate.
	 * @param checkDate The checkDate to set
	 */
	public void setCheckDate(Timestamp checkDate)
	{
		CheckDate = checkDate;
		putUsedField("CheckDate", checkDate);
	}
	/**
	 * Sets the checkUserID.
	 * @param checkUserID The checkUserID to set
	 */
	public void setCheckUserID(long checkUserID)
	{
		CheckUserID = checkUserID;
		putUsedField("CheckUserID", checkUserID);
	}
	/**
	 * Sets the clientID.
	 * @param clientID The clientID to set
	 */
	public void setClientID(long clientID)
	{
		ClientID = clientID;
		putUsedField("ClientID", clientID);
	}
	/**
	 * Sets the contractID.
	 * @param contractID The contractID to set
	 */
	public void setContractID(long contractID)
	{
		ContractID = contractID;
		putUsedField("ContractID", contractID);
	}
	/**
	 * Sets the currencyID.
	 * @param currencyID The currencyID to set
	 */
	public void setCurrencyID(long currencyID)
	{
		CurrencyID = currencyID;
		putUsedField("CurrencyID", currencyID);
	}
	/**
	 * Sets the discountGLID.
	 * @param discountGLID The discountGLID to set
	 */
	public void setDiscountGLID(long discountGLID)
	{
		DiscountGLID = discountGLID;
		putUsedField("DiscountGLID", discountGLID);
	}
	/**
	 * Sets the dueBillID.
	 * @param dueBillID The dueBillID to set
	 */
	public void setDueBillID(long dueBillID)
	{
		DueBillID = dueBillID;
		putUsedField("DueBillID", dueBillID);
	}
	/**
	 * Sets the executeDate.
	 * @param executeDate The executeDate to set
	 */
	public void setExecuteDate(Timestamp executeDate)
	{
		ExecuteDate = executeDate;
		putUsedField("ExecuteDate", executeDate);
	}
	/**
	 * Sets the gLID.
	 * @param gLID The gLID to set
	 */
	public void setGLID(long gLID)
	{
		GLID = gLID;
		putUsedField("GLID", gLID);
	}
	/**
	 * Sets the iD.
	 * @param iD The iD to set
	 */
	public void setId(long iD)
	{
		ID = iD;
		putUsedField("ID", iD);
	}
	/**
	 * Sets the inputDate.
	 * @param inputDate The inputDate to set
	 */
	public void setInputDate(Timestamp inputDate)
	{
		InputDate = inputDate;
		putUsedField("InputDate", inputDate);
	}
	/**
	 * Sets the inputUserID.
	 * @param inputUserID The inputUserID to set
	 */
	public void setInputUserID(long inputUserID)
	{
		InputUserID = inputUserID;
		putUsedField("InputUserID", inputUserID);
	}
	/**
	 * Sets the interestStartDate.
	 * @param interestStartDate The interestStartDate to set
	 */
	public void setInterestStartDate(Timestamp interestStartDate)
	{
		InterestStartDate = interestStartDate;
		putUsedField("InterestStartDate", interestStartDate);
	}
	/**
	 * Sets the modifyDate.
	 * @param modifyDate The modifyDate to set
	 */
	public void setModifyDate(Timestamp modifyDate)
	{
		ModifyDate = modifyDate;
		putUsedField("ModifyDate", modifyDate);
	}
	/**
	 * Sets the officeID.
	 * @param officeID The officeID to set
	 */
	public void setOfficeID(long officeID)
	{
		OfficeID = officeID;
		putUsedField("OfficeID", officeID);
	}
	/**
	 * Sets the reDiscountGLID.
	 * @param reDiscountGLID The reDiscountGLID to set
	 */
	public void setReDiscountGLID(long reDiscountGLID)
	{
		ReDiscountGLID = reDiscountGLID;
		putUsedField("ReDiscountGLID", reDiscountGLID);
	}
	/**
	 * Sets the signDate.
	 * @param signDate The signDate to set
	 */
	public void setSignDate(Timestamp signDate)
	{
		SignDate = signDate;
		putUsedField("SignDate", signDate);
	}
	/**
	 * Sets the signUserID.
	 * @param signUserID The signUserID to set
	 */
	public void setSignUserID(long signUserID)
	{
		SignUserID = signUserID;
		putUsedField("SignUserID", signUserID);
	}
	/**
	 * Sets the statusID.
	 * @param statusID The statusID to set
	 */
	public void setStatusID(long statusID)
	{
		StatusID = statusID;
		putUsedField("StatusID", statusID);
	}
	/**
	 * Sets the subAccountID.
	 * @param subAccountID The subAccountID to set
	 */
	public void setSubAccountID(long subAccountID)
	{
		SubAccountID = subAccountID;
		putUsedField("SubAccountID", subAccountID);
	}
	/**
	 * Sets the totalAmount.
	 * @param totalAmount The totalAmount to set
	 */
	public void setTotalAmount(double totalAmount)
	{
		TotalAmount = totalAmount;
		putUsedField("TotalAmount", totalAmount);
	}
	/**
	 * Sets the transactionTypeID.
	 * @param transactionTypeID The transactionTypeID to set
	 */
	public void setTransactionTypeID(long transactionTypeID)
	{
		TransactionTypeID = transactionTypeID;
		putUsedField("TransactionTypeID", transactionTypeID);
	}
	/**
	 * Sets the transNo.
	 * @param transNo The transNo to set
	 */
	public void setTransNo(String transNo)
	{
		TransNo = transNo;
		putUsedField("TransNo", transNo);
	}
	/**
	 * Returns the transAbatementDetailInfo.
	 * @return Vector
	 */
	public Vector getTransAbatementDetailInfo()
	{
		return vctTransAbatementDetailInfo;
	}
	/**
	 * Sets the transAbatementDetailInfo.
	 * @param transAbatementDetailInfo The transAbatementDetailInfo to set
	 */
	public void setTransAbatementDetailInfo(Vector transAbatementDetailInfo)
	{
		vctTransAbatementDetailInfo = transAbatementDetailInfo;
	}
	/**
	 * Returns the transNOID.
	 * @return long
	 */
	public long getTransNOID()
	{
		return TransNOID;
	}
	/**
	 * Sets the transNOID.
	 * @param transNOID The transNOID to set
	 */
	public void setTransNOID(long transNOID)
	{
		TransNOID = transNOID;
		putUsedField("TransNOID", transNOID);
	}
	public static void main(java.lang.String[] args)
	{
	}
	/**
	 * Returns the discountDate.
	 * @return Timestamp
	 */
	public Timestamp getDiscountDate()
	{
		return DiscountDate;
	}

	/**
	 * Returns the discountTypeID.
	 * @return long
	 */
	public long getDiscountTypeID()
	{
		return DiscountTypeID;
	}

	/**
	 * Sets the discountDate.
	 * @param discountDate The discountDate to set
	 */
	public void setDiscountDate(Timestamp discountDate)
	{
		DiscountDate = discountDate;
	}

	/**
	 * Sets the discountTypeID.
	 * @param discountTypeID The discountTypeID to set
	 */
	public void setDiscountTypeID(long discountTypeID)
	{
		DiscountTypeID = discountTypeID;
	}

}
