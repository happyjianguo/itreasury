/*
 * Created on 2003-9-26
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transcompatibility.dataentity;
import java.sql.Timestamp;
import java.util.Vector;
import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;
/**
 *
 * <p>Title: TransSpecialOperationInfo�� </p>
 * <p>Description:������ҵ������,��Ӧdb���ݿ���Sett_ TransCompatibility�� </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: isoftstone</p>
 * @author gqzhang
 * @version 1.0
 */
public class TransCompatibilityInfo extends SettlementBaseDataEntity
{
	private long ID = -1; //Number	ID	
	private String TransNo = ""; //Code	���׺�	
	private long TransactionNOID = -1; //Number	����ID	���������TransactionNo
	private long TransactionTypeID = -1; //	Number	��������	���������Sett_CompatibilitySetting
	private long OfficeID = -1; //	Number	���´�	
	private long CurrencyID = -1; //	Number	����	
	private Timestamp InterestStartDate = null; //	DateTime	��Ϣ��	
	private Timestamp ExecuteDate = null; //	DateTime	ִ����	
	private String Abstract = ""; //Abstract	ժҪ	
	private long StatusID = -1; //	Number	״̬	ȡֵ�ڳ������壺�ݴ桢���桢���ˡ�δǩ�ϡ�ǩ�ϡ�ȷ��
	private long AffrimOfficeID = -1; //	Number	ȷ�ϰ��´�	
	private long InputUserID = -1; //	Number 	¼����	
	private long CheckUserID = -1; //	Number	������	
	private long SignUserID = -1; //	Number	ǩ����	
	private long AffrimUserID = -1; //	Number	ȷ����	
	private Timestamp InputDate = null; //DateTime	¼������	
	private Timestamp ModifyDate = null; //	DateTime	�޸�����ʱ��	��ʱ���֡����datatime����
	private Timestamp CheckDate = null; //	DateTime	��������	
	private Timestamp SignDate = null; //	DateTime	ǩ������	
	private Timestamp AffirmDate = null; //DateTime	ȷ������
	private String CheckAbstract = null; //String ������ȡ�����˱�ע
	private long OperationTypeID = -1; //���ݽ�������	
	Vector vctTransCompatibilityDetailInfo = null;
	/**
	 * Returns the a.
	 * @return String
	 */
	public String getAbstract()
	{
		return Abstract;
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
	 * Returns the currencyID.
	 * @return long
	 */
	public long getCurrencyID()
	{
		return CurrencyID;
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
	 * Returns the transactionNOID.
	 * @return long
	 */
	public long getTransactionNOID()
	{
		return TransactionNOID;
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
		putUsedField("Abstract", Abstract);
	}
	/**
	 * Sets the affirmDate.
	 * @param affirmDate The affirmDate to set
	 */
	public void setAffirmDate(Timestamp affirmDate)
	{
		AffirmDate = affirmDate;
		putUsedField("AffirmDate", AffirmDate);
	}
	/**
	 * Sets the affrimOfficeID.
	 * @param affrimOfficeID The affrimOfficeID to set
	 */
	public void setAffrimOfficeID(long affrimOfficeID)
	{
		AffrimOfficeID = affrimOfficeID;
		putUsedField("AffrimOfficeID", AffrimOfficeID);
	}
	/**
	 * Sets the affrimUserID.
	 * @param affrimUserID The affrimUserID to set
	 */
	public void setAffrimUserID(long affrimUserID)
	{
		AffrimUserID = affrimUserID;
		putUsedField("AffrimOfficeID", AffrimOfficeID);
	}
	/**
	 * Sets the checkDate.
	 * @param checkDate The checkDate to set
	 */
	public void setCheckDate(Timestamp checkDate)
	{
		CheckDate = checkDate;
		putUsedField("CheckDate", CheckDate);
	}
	/**
	 * Sets the checkUserID.
	 * @param checkUserID The checkUserID to set
	 */
	public void setCheckUserID(long checkUserID)
	{
		CheckUserID = checkUserID;
		putUsedField("CheckUserID", CheckUserID);
	}
	/**
	 * Sets the currencyID.
	 * @param currencyID The currencyID to set
	 */
	public void setCurrencyID(long currencyID)
	{
		CurrencyID = currencyID;
		putUsedField("CurrencyID", CurrencyID);
	}
	/**
	 * Sets the executeDate.
	 * @param executeDate The executeDate to set
	 */
	public void setExecuteDate(Timestamp executeDate)
	{
		ExecuteDate = executeDate;
		putUsedField("ExecuteDate", ExecuteDate);
	}
	/**
	 * Sets the iD.
	 * @param iD The iD to set
	 */
	public void setId(long iD)
	{
		ID = iD;
		putUsedField("ID", ID);
	}
	/**
	 * Sets the inputDate.
	 * @param inputDate The inputDate to set
	 */
	public void setInputDate(Timestamp inputDate)
	{
		InputDate = inputDate;
		putUsedField("InputDate", InputDate);
	}
	/**
	 * Sets the inputUserID.
	 * @param inputUserID The inputUserID to set
	 */
	public void setInputUserID(long inputUserID)
	{
		InputUserID = inputUserID;
		putUsedField("InputUserID", InputUserID);
	}
	/**
	 * Sets the interestStartDate.
	 * @param interestStartDate The interestStartDate to set
	 */
	public void setInterestStartDate(Timestamp interestStartDate)
	{
		InterestStartDate = interestStartDate;
		putUsedField("InterestStartDate", InterestStartDate);
	}
	/**
	 * Sets the modifyDate.
	 * @param modifyDate The modifyDate to set
	 */
	public void setModifyDate(Timestamp modifyDate)
	{
		ModifyDate = modifyDate;
		putUsedField("ModifyDate", ModifyDate);
	}
	/**
	 * Sets the officeID.
	 * @param officeID The officeID to set
	 */
	public void setOfficeID(long officeID)
	{
		OfficeID = officeID;
		putUsedField("OfficeID", OfficeID);
	}
	/**
	 * Sets the signDate.
	 * @param signDate The signDate to set
	 */
	public void setSignDate(Timestamp signDate)
	{
		SignDate = signDate;
		putUsedField("SignDate", SignDate);
	}
	/**
	 * Sets the signUserID.
	 * @param signUserID The signUserID to set
	 */
	public void setSignUserID(long signUserID)
	{
		SignUserID = signUserID;
		putUsedField("SignUserID", SignUserID);
	}
	/**
	 * Sets the statusID.
	 * @param statusID The statusID to set
	 */
	public void setStatusID(long statusID)
	{
		StatusID = statusID;
		putUsedField("StatusID", StatusID);
	}
	/**
	 * Sets the transactionNOID.
	 * @param transactionNOID The transactionNOID to set
	 */
	public void setTransactionNOID(long transactionNOID)
	{
		TransactionNOID = transactionNOID;
		putUsedField("TransactionNOID", TransactionNOID);
	}
	/**
	 * Sets the transactionTypeID.
	 * @param transactionTypeID The transactionTypeID to set
	 */
	public void setTransactionTypeID(long transactionTypeID)
	{
		TransactionTypeID = transactionTypeID;
		putUsedField("TransactionTypeID", TransactionTypeID);
	}
	/**
	 * Sets the transNo.
	 * @param transNo The transNo to set
	 */
	public void setTransNo(String transNo)
	{
		TransNo = transNo;
		putUsedField("TransNo", TransNo);
	}
	/**
	 * Returns the transCompatibilityDetailInfo.
	 * @return Vector
	 */
	public Vector getTransCompatibilityDetailInfo()
	{
		return vctTransCompatibilityDetailInfo;
	}
	/**
	 * Sets the transCompatibilityDetailInfo.
	 * @param transCompatibilityDetailInfo The transCompatibilityDetailInfo to set
	 */
	public void setTransCompatibilityDetailInfo(Vector transCompatibilityDetailInfo)
	{
		vctTransCompatibilityDetailInfo = transCompatibilityDetailInfo;
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
	 * Sets the checkAbstract.
	 * @param checkAbstract The checkAbstract to set
	 */
	public void setCheckAbstract(String checkAbstract)
	{
		CheckAbstract = checkAbstract;
		putUsedField("CheckAbstract", checkAbstract);
	}
	/**
	 * Returns the operationTypeID.
	 * @return long
	 */
	public long getOperationTypeID()
	{
		return OperationTypeID;
	}
	/**
	 * Sets the operationTypeID.
	 * @param operationTypeID The operationTypeID to set
	 */
	public void setOperationTypeID(long operationTypeID)
	{
		OperationTypeID = operationTypeID;
		putUsedField("OperationTypeID", operationTypeID);
	}
}
