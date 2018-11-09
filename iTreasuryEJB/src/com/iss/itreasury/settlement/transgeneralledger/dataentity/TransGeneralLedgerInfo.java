package com.iss.itreasury.settlement.transgeneralledger.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.sysframe.base.dataentity.BaseDataEntity;

/**
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class TransGeneralLedgerInfo extends BaseDataEntity implements Serializable
{
	private long id = -1;
	private long ID = -1; //  is PK
	private long officeID = -1; //	is '���´�';                   
	private long currencyID = -1; //	is '����';                 
	private String transNo = ""; //	is '���׺�';                    
	private long transActionTypeID = -1; //	is '��������';          
	private long clientID = -1; //	is '�ͻ���';                   
	private long accountID = -1; //	is '�˻���';                  
	private long direction = -1; //	is '�������';                  
	private double amount = 0.0; //	is '���';                     
	private long generalLedgerOne = -1; //	is '����������1';           
	private long dirOne = -1; //	is '�������1';                     
	private double amountOne = 0.0; //	is '���1';                        
	private long generalLedgerTwo = -1; //	is '����������2';           
	private long dirTwo = -1; //	is '�������2';                     
	private double amountTwo = 0.0; //	is '���2';                        
	private long generalLedgerThree = -1; //	is '����������3';         
	private long dirThree = -1; //	is '�������3';                   
	private double amountThree = 0.0; //	is '���3';      
    //������������������������ �� �� �ӣ���������������������������	--
	private long generalLedgerFour = -1; //	is '����������4';         
	private long dirFour = -1; //	is '�������4';                   
	private double amountFour = 0.0; //	is '���4';
//----------------------------------------------------------------
	private String voucherNo = ""; //	is 'ί�и���ƾ֤��';                  
	private String voucherPWD = ""; //	is 'ί�и���ƾ֤����';                 
	private String billNo = ""; //	is 'Ʊ�ݺ�';                     
	private long billTypeID = -1; //	is 'Ʊ������';                 
	private long billBankID = -1; //	is 'Ʊ�ݷ�������';                 
	private String payExtBankNo = ""; //	is '�����';               
	private String receiveExtBankNo = ""; //	is '������';           
	private Timestamp interestStartDate = null; //	is '��Ϣ��';             
	private Timestamp executeDate = null; //	is 'ִ����';                  
	private Timestamp modifyDate = null; //	is '�޸�ʱ�䣺������ʱ����';                    
	private long inputUserID = -1; //	is '¼����';                
	private Timestamp inputDate = null; //	is '¼��ʱ�䣺������ʱ����';                     
	private long checkUserID = -1; //	is '������';                
	private long signUserID = -1; //	is 'ǩ����';                 
	private long confirmUserID = -1; //	is 'ȷ����';              
	private long confirmOfficeID = -1; //	is 'ͨ��ͨ�ҶԷ����´�';            
	private long abstractID = -1; //	is 'ժҪID';                 
	private String strAbstract = ""; //	is 'ժҪ';                   
	private String checkAbstract = ""; //	is '����ժҪ';              
	private String cancelCheckAbstract = ""; //	is 'ȡ������ժҪ';        
	private String confirmAbstract = ""; //	is 'ȷ��ժҪ';            
	private long[] statusIDs = null; //is '����״̬'                
	private double sumForSearch = 0.0; //	is '��ѯ��';  
	private InutParameterInfo inutParameterInfo = null;
	private TransGeneralLedgerInfo transGeneralLedgerInfo = null;
	
	/*
	 *  modified 20121113 zk BUG #16275 ���ӵ��ݹ�����ҵ���ӡ���跽�ʹ�����Ŀ��û��ȡ����ֵ
	 *  ����˻����ͺͶ�Ӧ��Ŀ
	 */
	private long nAccountTypeID = -1;	//�˻�����;
	private String sSubject = ""; 		//�˻���Ӧ��Ŀ��;
	
	
	public TransGeneralLedgerInfo getTransGeneralLedgerInfo() {
		return this.transGeneralLedgerInfo;
	}

	public void setTransGeneralLedgerInfo(
			TransGeneralLedgerInfo transGeneralLedgerInfo) {
		this.transGeneralLedgerInfo = transGeneralLedgerInfo;
	}

	/**
	 * Constructor for TransGeneralLedgerInfo.
	 */
	public TransGeneralLedgerInfo()
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

		//��õ�ǰ����ָ�����Ƶ�Field����
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
	public TransGeneralLedgerInfo getQeureyInfo()
	{
		TransGeneralLedgerInfo queryInfo = new TransGeneralLedgerInfo();
		
		//���ղ�ͬҵ������У���ظ�����
		queryInfo.setTransActionTypeID(this.getTransActionTypeID());
		
		//�ظ�����У��ִ����
		queryInfo.setExecuteDate(this.getExecuteDate());
		
		//�ظ�����У��Ľ���״̬Ϊ����ɾ��״̬��������Ч����
		queryInfo.setStatusIDs(
			new long[] {
				SETTConstant.TransactionStatus.CHECK,
				SETTConstant.TransactionStatus.CIRCLE,
				SETTConstant.TransactionStatus.CONFIRM,
				SETTConstant.TransactionStatus.NOTSIGN,
				SETTConstant.TransactionStatus.SAVE,
				SETTConstant.TransactionStatus.SIGN,
				SETTConstant.TransactionStatus.TEMPSAVE });

		queryInfo.setAccountID(this.getAccountID());
		queryInfo.setDirection(this.getDirection());
		queryInfo.setAmount(this.getAmount());
		
		queryInfo.setGeneralLedgerOne(this.getGeneralLedgerOne());
		queryInfo.setDirOne(this.getDirOne());
		queryInfo.setAmountOne(this.getAmountOne());
		
		queryInfo.setGeneralLedgerTwo(this.getGeneralLedgerTwo());
		queryInfo.setDirTwo(this.getDirTwo());
		queryInfo.setAmountTwo(this.getAmountTwo());
		
		queryInfo.setGeneralLedgerThree(this.getGeneralLedgerThree());
		queryInfo.setDirThree(this.getDirThree());
		queryInfo.setAmountThree(this.getAmountThree());
		
		
					
		//@TBD: we will add other trans type at here
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
	public long getAccountID()
	{
		return accountID;
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
	 * Returns the amountOne.
	 * @return double
	 */
	public double getAmountOne()
	{
		return amountOne;
	}

	/**
	 * Returns the amountThree.
	 * @return double
	 */
	public double getAmountThree()
	{
		return amountThree;
	}

	/**
	 * Returns the amountTwo.
	 * @return double
	 */
	public double getAmountTwo()
	{
		return amountTwo;
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
	 * Returns the cancelCheckAbstract.
	 * @return String
	 */
	public String getCancelCheckAbstract()
	{
		return cancelCheckAbstract;
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
	public long getClientID()
	{
		return clientID;
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
	 * Returns the currencyID.
	 * @return long
	 */
	public long getCurrencyID()
	{
		return currencyID;
	}

	/**
	 * Returns the direction.
	 * @return long
	 */
	public long getDirection()
	{
		return direction;
	}

	/**
	 * Returns the dirOne.
	 * @return long
	 */
	public long getDirOne()
	{
		return dirOne;
	}

	/**
	 * Returns the dirThree.
	 * @return long
	 */
	public long getDirThree()
	{
		return dirThree;
	}

	/**
	 * Returns the dirTwo.
	 * @return long
	 */
	public long getDirTwo()
	{
		return dirTwo;
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
	 * Returns the generalLedgerOne.
	 * @return long
	 */
	public long getGeneralLedgerOne()
	{
		return generalLedgerOne;
	}

	/**
	 * Returns the generalLedgerThree.
	 * @return long
	 */
	public long getGeneralLedgerThree()
	{
		return generalLedgerThree;
	}

	/**
	 * Returns the generalLedgerTwo.
	 * @return long
	 */
	public long getGeneralLedgerTwo()
	{
		return generalLedgerTwo;
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
	 * Returns the receiveExtBankNo.
	 * @return String
	 */
	public String getReceiveExtBankNo()
	{
		return receiveExtBankNo;
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
	 * ���ڵ�ǰEntity��Ϊ��ѯ����ʱ
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
	 * Returns the sumForSearch.
	 * @return double
	 */
	public double getSumForSearch()
	{
		return sumForSearch;
	}

	/**
	 * Returns the transActionTypeID.
	 * @return long
	 */
	public long getTransActionTypeID()
	{
		return transActionTypeID;
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
	 * Returns the voucherNo.
	 * @return String
	 */
	public String getVoucherNo()
	{
		return voucherNo;
	}

	/**
	 * Returns the voucherPWD.
	 * @return String
	 */
	public String getVoucherPWD()
	{
		return voucherPWD;
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
	public void setAccountID(long accountID)
	{
		this.accountID = accountID;
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
	 * Sets the amountOne.
	 * @param amountOne The amountOne to set
	 */
	public void setAmountOne(double amountOne)
	{
		this.amountOne = amountOne;
	}

	/**
	 * Sets the amountThree.
	 * @param amountThree The amountThree to set
	 */
	public void setAmountThree(double amountThree)
	{
		this.amountThree = amountThree;
	}

	/**
	 * Sets the amountTwo.
	 * @param amountTwo The amountTwo to set
	 */
	public void setAmountTwo(double amountTwo)
	{
		this.amountTwo = amountTwo;
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
	 * Sets the cancelCheckAbstract.
	 * @param cancelCheckAbstract The cancelCheckAbstract to set
	 */
	public void setCancelCheckAbstract(String cancelCheckAbstract)
	{
		this.cancelCheckAbstract = cancelCheckAbstract;
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
	public void setClientID(long clientID)
	{
		this.clientID = clientID;
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
	 * Sets the currencyID.
	 * @param currencyID The currencyID to set
	 */
	public void setCurrencyID(long currencyID)
	{
		this.currencyID = currencyID;
	}

	/**
	 * Sets the direction.
	 * @param direction The direction to set
	 */
	public void setDirection(long direction)
	{
		this.direction = direction;
	}

	/**
	 * Sets the dirOne.
	 * @param dirOne The dirOne to set
	 */
	public void setDirOne(long dirOne)
	{
		this.dirOne = dirOne;
	}

	/**
	 * Sets the dirThree.
	 * @param dirThree The dirThree to set
	 */
	public void setDirThree(long dirThree)
	{
		this.dirThree = dirThree;
	}

	/**
	 * Sets the dirTwo.
	 * @param dirTwo The dirTwo to set
	 */
	public void setDirTwo(long dirTwo)
	{
		this.dirTwo = dirTwo;
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
	 * Sets the generalLedgerOne.
	 * @param generalLedgerOne The generalLedgerOne to set
	 */
	public void setGeneralLedgerOne(long generalLedgerOne)
	{
		this.generalLedgerOne = generalLedgerOne;
	}

	/**
	 * Sets the generalLedgerThree.
	 * @param generalLedgerThree The generalLedgerThree to set
	 */
	public void setGeneralLedgerThree(long generalLedgerThree)
	{
		this.generalLedgerThree = generalLedgerThree;
	}

	/**
	 * Sets the generalLedgerTwo.
	 * @param generalLedgerTwo The generalLedgerTwo to set
	 */
	public void setGeneralLedgerTwo(long generalLedgerTwo)
	{
		this.generalLedgerTwo = generalLedgerTwo;
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
	 * Sets the receiveExtBankNo.
	 * @param receiveExtBankNo The receiveExtBankNo to set
	 */
	public void setReceiveExtBankNo(String receiveExtBankNo)
	{
		this.receiveExtBankNo = receiveExtBankNo;
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
	 * ���ڵ�ǰEntity��Ϊ��ѯ����ʱ
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
	 * Sets the sumForSearch.
	 * @param sumForSearch The sumForSearch to set
	 */
	public void setSumForSearch(double sumForSearch)
	{
		this.sumForSearch = sumForSearch;
	}

	/**
	 * Sets the transActionTypeID.
	 * @param transActionTypeID The transActionTypeID to set
	 */
	public void setTransActionTypeID(long transActionTypeID)
	{
		this.transActionTypeID = transActionTypeID;
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
	 * Sets the voucherNo.
	 * @param voucherNo The voucherNo to set
	 */
	public void setVoucherNo(String voucherNo)
	{
		this.voucherNo = voucherNo;
	}

	/**
	 * Sets the voucherPWD.
	 * @param voucherPWD The voucherPWD to set
	 */
	public void setVoucherPWD(String voucherPWD)
	{
		this.voucherPWD = voucherPWD;
	}

	public long getGeneralLedgerFour() {
		return generalLedgerFour;
	}

	public void setGeneralLedgerFour(long generalLedgerFour) {
		this.generalLedgerFour = generalLedgerFour;
	}

	public long getDirFour() {
		return dirFour;
	}

	public void setDirFour(long dirFour) {
		this.dirFour = dirFour;
	}

	public double getAmountFour() {
		return amountFour;
	}

	public void setAmountFour(double amountFour) {
		this.amountFour = amountFour;
	}
	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}

	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStrAbstract() {
		return strAbstract;
	}

	public void setStrAbstract(String strAbstract) {
		this.strAbstract = strAbstract;
	}

	public long getNAccountTypeID() {
		return nAccountTypeID;
	}

	public void setNAccountTypeID(long accountTypeID) {
		nAccountTypeID = accountTypeID;
	}

	public String getSSubject() {
		return sSubject;
	}

	public void setSSubject(String subject) {
		sSubject = subject;
	}

}
