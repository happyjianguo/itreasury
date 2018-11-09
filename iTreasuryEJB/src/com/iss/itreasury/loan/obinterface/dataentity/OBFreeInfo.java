package com.iss.itreasury.loan.obinterface.dataentity;

/**
 * @author yanhuang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
import java.sql.Timestamp;
import com.iss.itreasury.util.*;
public class OBFreeInfo implements java.io.Serializable{
	
	private long ID = -1;                       //�⻹�����ʶ
	private String FreeCode = "";               //�⻹���
        
	private long ContractID = -1;               //��ͬ��ʾ
	private String ContractCode = "";           //��ͬ���
	private String ConsignClientName = "";      //ί�д��λ����
	private String ConsignClientAccount = "";   //ί�д��λ�˻���
    
	private long ClientID =-1;                  //��λID
	private String ClientName = "";             //��λ����
	//�ſλ����
	private String PayClientName = Env.getClientName(); 
        
	private long  LoanTypeID=-1;       //��������
	//private long CurrencyID;          //����
	//private long OfficeID;            //���´���ʾ
	//private String OfficeName;        //���´�����
	private double LoanAmount=0;			//������
	private double Amount=0;              //�����
	private double Balance=0;             //�����δ����
	private long IntervalNum=-1;           //��������
	private String LoanPurpose="";         //������;
	private float InterestRate=0;         //��������
	private Timestamp StartDate=null;        //���ʼʱ��
	private Timestamp EndDate=null;          //�������ʱ��
    
	private long LoanPayID = -1;        //�ſ�֪ͨ��ID
	private String LoanPayCode = "";    //�ſ�֪ͨ�����
	private double LoanPayAmount;       //�ſ���
	private double LoanPayBalance;      //�ſ����
	private double LoanPayInterest;	  //�ſ���Ϣ
	private double FreeAmount=0;          //�⻹���
	private double FreeRate=0;            //�⻹��Ϣ
	private Timestamp FreeDate=null;         //�⻹��ʼʱ��
	private String FreeReason="";          //�⻹ԭ��
	private String Status="";            //״̬����
	private long StatusID=-1;              //�⻹״̬ ���ύ�������
     
	private long  InputUserID=-1;          //¼���˱�ʾ
	private String InputUserName="";       //¼�������� һ�������
	private Timestamp InputDate=null;        //¼��ʱ��
	private long CheckUserID=-1;           //�����
	private String CheckUserName="";       //���������

	private long RecordCount=-1;             //�ܼ�¼��
	private long PageCount=-1;             //��ҳ��
	private double AllLoanAmount = 0 ;  //�����������
	private double AllFreeAmount = 0 ;  //���⻹���

	/**
	 * Returns the allFreeAmount.
	 * @return double
	 */
	public double getAllFreeAmount() {
		return AllFreeAmount;
	}

	/**
	 * Returns the allLoanAmount.
	 * @return double
	 */
	public double getAllLoanAmount() {
		return AllLoanAmount;
	}

	/**
	 * Returns the amount.
	 * @return double
	 */
	public double getAmount() {
		return Amount;
	}

	/**
	 * Returns the balance.
	 * @return double
	 */
	public double getBalance() {
		return Balance;
	}

	/**
	 * Returns the checkUserID.
	 * @return long
	 */
	public long getCheckUserID() {
		return CheckUserID;
	}

	/**
	 * Returns the checkUserName.
	 * @return String
	 */
	public String getCheckUserName() {
		return CheckUserName;
	}

	/**
	 * Returns the clientID.
	 * @return long
	 */
	public long getClientID() {
		return ClientID;
	}

	/**
	 * Returns the clientName.
	 * @return String
	 */
	public String getClientName() {
		return ClientName;
	}

	/**
	 * Returns the consignClientAccount.
	 * @return String
	 */
	public String getConsignClientAccount() {
		return ConsignClientAccount;
	}

	/**
	 * Returns the consignClientName.
	 * @return String
	 */
	public String getConsignClientName() {
		return ConsignClientName;
	}

	/**
	 * Returns the contractCode.
	 * @return String
	 */
	public String getContractCode() {
		return ContractCode;
	}

	/**
	 * Returns the contractID.
	 * @return long
	 */
	public long getContractID() {
		return ContractID;
	}

	/**
	 * Returns the endDate.
	 * @return Timestamp
	 */
	public Timestamp getEndDate() {
		return EndDate;
	}

	/**
	 * Returns the freeAmount.
	 * @return double
	 */
	public double getFreeAmount() {
		return FreeAmount;
	}

	/**
	 * Returns the freeCode.
	 * @return String
	 */
	public String getFreeCode() {
		return FreeCode;
	}

	/**
	 * Returns the freeDate.
	 * @return Timestamp
	 */
	public Timestamp getFreeDate() {
		return FreeDate;
	}

	/**
	 * Returns the freeRate.
	 * @return double
	 */
	public double getFreeRate() {
		return FreeRate;
	}

	/**
	 * Returns the freeReason.
	 * @return String
	 */
	public String getFreeReason() {
		return FreeReason;
	}

	/**
	 * Returns the iD.
	 * @return long
	 */
	public long getID() {
		return ID;
	}

	/**
	 * Returns the inputDate.
	 * @return Timestamp
	 */
	public Timestamp getInputDate() {
		return InputDate;
	}

	/**
	 * Returns the inputUserID.
	 * @return long
	 */
	public long getInputUserID() {
		return InputUserID;
	}

	/**
	 * Returns the inputUserName.
	 * @return String
	 */
	public String getInputUserName() {
		return InputUserName;
	}

	/**
	 * Returns the interestRate.
	 * @return float
	 */
	public float getInterestRate() {
		return InterestRate;
	}

	/**
	 * Returns the intervalNum.
	 * @return long
	 */
	public long getIntervalNum() {
		return IntervalNum;
	}

	/**
	 * Returns the loanPayAmount.
	 * @return double
	 */
	public double getLoanPayAmount() {
		return LoanPayAmount;
	}

	/**
	 * Returns the loanPayBalance.
	 * @return double
	 */
	public double getLoanPayBalance() {
		return LoanPayBalance;
	}

	/**
	 * Returns the loanPayCode.
	 * @return String
	 */
	public String getLoanPayCode() {
		return LoanPayCode;
	}

	/**
	 * Returns the loanPayID.
	 * @return long
	 */
	public long getLoanPayID() {
		return LoanPayID;
	}

	/**
	 * Returns the loanPurpose.
	 * @return String
	 */
	public String getLoanPurpose() {
		return LoanPurpose;
	}

	/**
	 * Returns the loanTypeID.
	 * @return long
	 */
	public long getLoanTypeID() {
		return LoanTypeID;
	}

	/**
	 * Returns the pageCount.
	 * @return long
	 */
	public long getPageCount() {
		return PageCount;
	}

	/**
	 * Returns the payClientName.
	 * @return String
	 */
	public String getPayClientName() {
		return PayClientName;
	}

	/**
	 * Returns the recordCount.
	 * @return long
	 */
	public long getRecordCount() {
		return RecordCount;
	}

	/**
	 * Returns the startDate.
	 * @return Timestamp
	 */
	public Timestamp getStartDate() {
		return StartDate;
	}

	/**
	 * Returns the status.
	 * @return String
	 */
	public String getStatus() {
		return Status;
	}

	/**
	 * Returns the statusID.
	 * @return long
	 */
	public long getStatusID() {
		return StatusID;
	}

	/**
	 * Sets the allFreeAmount.
	 * @param allFreeAmount The allFreeAmount to set
	 */
	public void setAllFreeAmount(double allFreeAmount) {
		AllFreeAmount = allFreeAmount;
	}

	/**
	 * Sets the allLoanAmount.
	 * @param allLoanAmount The allLoanAmount to set
	 */
	public void setAllLoanAmount(double allLoanAmount) {
		AllLoanAmount = allLoanAmount;
	}

	/**
	 * Sets the amount.
	 * @param amount The amount to set
	 */
	public void setAmount(double amount) {
		Amount = amount;
	}

	/**
	 * Sets the balance.
	 * @param balance The balance to set
	 */
	public void setBalance(double balance) {
		Balance = balance;
	}

	/**
	 * Sets the checkUserID.
	 * @param checkUserID The checkUserID to set
	 */
	public void setCheckUserID(long checkUserID) {
		CheckUserID = checkUserID;
	}

	/**
	 * Sets the checkUserName.
	 * @param checkUserName The checkUserName to set
	 */
	public void setCheckUserName(String checkUserName) {
		CheckUserName = checkUserName;
	}

	/**
	 * Sets the clientID.
	 * @param clientID The clientID to set
	 */
	public void setClientID(long clientID) {
		ClientID = clientID;
	}

	/**
	 * Sets the clientName.
	 * @param clientName The clientName to set
	 */
	public void setClientName(String clientName) {
		ClientName = clientName;
	}

	/**
	 * Sets the consignClientAccount.
	 * @param consignClientAccount The consignClientAccount to set
	 */
	public void setConsignClientAccount(String consignClientAccount) {
		ConsignClientAccount = consignClientAccount;
	}

	/**
	 * Sets the consignClientName.
	 * @param consignClientName The consignClientName to set
	 */
	public void setConsignClientName(String consignClientName) {
		ConsignClientName = consignClientName;
	}

	/**
	 * Sets the contractCode.
	 * @param contractCode The contractCode to set
	 */
	public void setContractCode(String contractCode) {
		ContractCode = contractCode;
	}

	/**
	 * Sets the contractID.
	 * @param contractID The contractID to set
	 */
	public void setContractID(long contractID) {
		ContractID = contractID;
	}

	/**
	 * Sets the endDate.
	 * @param endDate The endDate to set
	 */
	public void setEndDate(Timestamp endDate) {
		EndDate = endDate;
	}

	/**
	 * Sets the freeAmount.
	 * @param freeAmount The freeAmount to set
	 */
	public void setFreeAmount(double freeAmount) {
		FreeAmount = freeAmount;
	}

	/**
	 * Sets the freeCode.
	 * @param freeCode The freeCode to set
	 */
	public void setFreeCode(String freeCode) {
		FreeCode = freeCode;
	}

	/**
	 * Sets the freeDate.
	 * @param freeDate The freeDate to set
	 */
	public void setFreeDate(Timestamp freeDate) {
		FreeDate = freeDate;
	}

	/**
	 * Sets the freeRate.
	 * @param freeRate The freeRate to set
	 */
	public void setFreeRate(double freeRate) {
		FreeRate = freeRate;
	}

	/**
	 * Sets the freeReason.
	 * @param freeReason The freeReason to set
	 */
	public void setFreeReason(String freeReason) {
		FreeReason = freeReason;
	}

	/**
	 * Sets the iD.
	 * @param iD The iD to set
	 */
	public void setID(long iD) {
		ID = iD;
	}

	/**
	 * Sets the inputDate.
	 * @param inputDate The inputDate to set
	 */
	public void setInputDate(Timestamp inputDate) {
		InputDate = inputDate;
	}

	/**
	 * Sets the inputUserID.
	 * @param inputUserID The inputUserID to set
	 */
	public void setInputUserID(long inputUserID) {
		InputUserID = inputUserID;
	}

	/**
	 * Sets the inputUserName.
	 * @param inputUserName The inputUserName to set
	 */
	public void setInputUserName(String inputUserName) {
		InputUserName = inputUserName;
	}

	/**
	 * Sets the interestRate.
	 * @param interestRate The interestRate to set
	 */
	public void setInterestRate(float interestRate) {
		InterestRate = interestRate;
	}

	/**
	 * Sets the intervalNum.
	 * @param intervalNum The intervalNum to set
	 */
	public void setIntervalNum(long intervalNum) {
		IntervalNum = intervalNum;
	}

	/**
	 * Sets the loanPayAmount.
	 * @param loanPayAmount The loanPayAmount to set
	 */
	public void setLoanPayAmount(double loanPayAmount) {
		LoanPayAmount = loanPayAmount;
	}

	/**
	 * Sets the loanPayBalance.
	 * @param loanPayBalance The loanPayBalance to set
	 */
	public void setLoanPayBalance(double loanPayBalance) {
		LoanPayBalance = loanPayBalance;
	}

	/**
	 * Sets the loanPayCode.
	 * @param loanPayCode The loanPayCode to set
	 */
	public void setLoanPayCode(String loanPayCode) {
		LoanPayCode = loanPayCode;
	}

	/**
	 * Sets the loanPayID.
	 * @param loanPayID The loanPayID to set
	 */
	public void setLoanPayID(long loanPayID) {
		LoanPayID = loanPayID;
	}

	/**
	 * Sets the loanPurpose.
	 * @param loanPurpose The loanPurpose to set
	 */
	public void setLoanPurpose(String loanPurpose) {
		LoanPurpose = loanPurpose;
	}

	/**
	 * Sets the loanTypeID.
	 * @param loanTypeID The loanTypeID to set
	 */
	public void setLoanTypeID(long loanTypeID) {
		LoanTypeID = loanTypeID;
	}

	/**
	 * Sets the pageCount.
	 * @param pageCount The pageCount to set
	 */
	public void setPageCount(long pageCount) {
		PageCount = pageCount;
	}

	/**
	 * Sets the payClientName.
	 * @param payClientName The payClientName to set
	 */
	public void setPayClientName(String payClientName) {
		PayClientName = payClientName;
	}

	/**
	 * Sets the recordCount.
	 * @param recordCount The recordCount to set
	 */
	public void setRecordCount(long recordCount) {
		RecordCount = recordCount;
	}

	/**
	 * Sets the startDate.
	 * @param startDate The startDate to set
	 */
	public void setStartDate(Timestamp startDate) {
		StartDate = startDate;
	}

	/**
	 * Sets the status.
	 * @param status The status to set
	 */
	public void setStatus(String status) {
		Status = status;
	}

	/**
	 * Sets the statusID.
	 * @param statusID The statusID to set
	 */
	public void setStatusID(long statusID) {
		StatusID = statusID;
	}

	/**
	 * Returns the loanPayInterest.
	 * @return double
	 */
	public double getLoanPayInterest() {
		return LoanPayInterest;
	}

	/**
	 * Sets the loanPayInterest.
	 * @param loanPayInterest The loanPayInterest to set
	 */
	public void setLoanPayInterest(double loanPayInterest) {
		LoanPayInterest = loanPayInterest;
	}


	/**
	 * Returns the loanAmount.
	 * @return double
	 */
	public double getLoanAmount() {
		return LoanAmount;
	}

	/**
	 * Sets the loanAmount.
	 * @param loanAmount The loanAmount to set
	 */
	public void setLoanAmount(double loanAmount) {
		LoanAmount = loanAmount;
	}



}
