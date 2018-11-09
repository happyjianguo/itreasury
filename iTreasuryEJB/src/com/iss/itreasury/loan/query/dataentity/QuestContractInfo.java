/*
 * questContractInfo.java
 *
 * Created on 2002��4��9��, ����4:10
 */

package com.iss.itreasury.loan.query.dataentity;

import java.sql.Timestamp;

/**
 *
 * @author  yzhang
 * @version 
 */
public class QuestContractInfo extends Object implements java.io.Serializable {

	/** Creates new questContractInfo */
	public static final long TX=1;
	public static final long LOAN=2;
	public static final long ZTX=3;
	public static final long DB=4;//���Ϻ���������������
	public static final long RZZL=10;//��������  ����
	public QuestContractInfo() {
		super();
	}

	//added by xiong fei 2010/05/24 ���ӵ�����ʽ��ͬʱ��������Ӧ��get,set����
	long iscredit = 0;//����
	long isrecognizance = 0;//��֤��
	long isassure = 0;//��֤
	long isimpawn = 0;//��Ѻ
	long ispledge = 0;//��Ѻ
	long isrepurchase = 0;//�ع�
	
	
	
	//��������
	public long LoanTypeID=-1;
	
	//��ͬ��ſ�ʼ
	public String minContractCode="";    
	
	//��ͬ��Ž���
	public String maxContractCode="";
	
	//��ͬ״̬
	public long statusID=-1;
	
	//�����ͬ״̬
	public String statusIDs;
	
	//���ֻ�Ʊ����
	public long tsDiscountTypeID=-1;	
    
    //���뵥λ
	long borrowClientID=-1;
	//���뵥λ��ʼ
	long minborrowClientID=-1;
	//���뵥λ����
	long maxborrowClientID=-1;
	
	//��Ʊ��ID
	long discountClientID=-1;
	//��Ʊ������
	String billDrawer="";
	
	//���뵥λ�˺�
	String borrowAccount="";
	
	//ί�е�λ
	long consignClientID=-1;
	
	//ί�е�λ�˺�
	String consignAccount="";
	
	//���ֽ�ʼ
	double minLoanAmount=0;
	
	//���ֽ�����
	double maxLoanAmount=0;
	
	//�������ڿ�ʼ
	Timestamp minStartDate=null;
	
	//�������ڽ���
	Timestamp maxStartDate=null;
/////////////////////////////////////////////////	
	//����������ڿ�ʼ
	Timestamp minEndDate = null;
	
	//����������ڽ�ֹ
	Timestamp maxEndDate = null;
	
	//������
	double minRate = 0.0;
	
	//����ֹ
	double maxRate = 0.0;
	
	//�������ڿ�ʼ
	Timestamp minDiscountDate = null;
	
	//��������ֹ
	Timestamp maxDiscountDate = null;
	
	//��������¼�����ڿ�ʼ
	Timestamp minDisccountInputDate = null;
	
	//��������¼�����ڿ�ʼ
	Timestamp maxDisccountInputDate = null;
//////////////////////////////////////////////////	
	//DiscountClientName
	String discountClientName ;
	//�����򷽸�Ϣ����
	double purchaserInterestRate = 0.00;
	//ʵ�����
	double checkAmount = 0.00;
	//ʵ����ʼ
	double minCheckAmount = 0.00;
	//ʵ�������ֹ
	double maxCheckAmount = 0.00;
	//���ֽ���˸���Ϣ
	double discountInterest = 0.00;
	//�����򷽸���Ϣ
	double discountPurchaserInterest = 0.00;
	//�򷽸�Ϣ��ʼ
	double minPayerRate = 0.00;
	//�򷽸�Ϣ��ֹ
	double maxPayerRate = 0.00;
	//���´�
	long officeID  = -1;
	//����
	long currencyID = -1;
//////////////////////////////////////////////////

	/**
	 * @return Returns the dB.
	 */
	public static long getDB()
	{
		return DB;
	}
	//����
	long intervalNum=-1;
	
	//��֤����
	private long assureTypeID=-1;
	
	//���õȼ�
	private long creditLevel=-1;
	
	//�Ƿ񼼸Ĵ���
	private long isTechnical=-1;
	
	//�Ƿ�ѭ��
	private long isCircle=-1;
	
	//�Ƿ�ɶ�
	private long isPartner=-1;
	
	//�ͻ�����
	private long loanClientTypeID=-1;
	
	//���ܵ�λ
	private long parentCorpID=-1;
	
	//�������״̬
	private long riskLevel=-1;
	
	//����������
	private long typeID1=-1;
	
	//����ҵ����1
	private long typeID2=-1;
	
	//����ҵ����2
	private long typeID3=-1;

	//��ͬ������
	private long inputUserID=-1;
		
	//�����ֶ�
	private long orderParam=-1;
	
	//desc
	private long desc=-1;	
	
	//��ѯ����
	private String queryLevel="";
	
	//��ѯĿ��		
	public long queryPurpose=LOAN;
	
	/***************��ӹ����ı�� 2003-3-30 qqgd***************/
	//�Ƿ���ʾ����״̬�ĺ�ͬ
	private boolean showEnd=true;
	
	//�Ƿ��ǲ�ѯ������Ϣ
	private boolean isGather=false;
	
	//ת�������ͣ����롢������
	private long inOrOut= -1;
	  
	//ת�������ࣨ��ϡ��ع���
	private long transDiscountType = -1;
	  
	//ת��������
	private long transDiscountTerm= -1;	
	
	/**add by wmzheng at 2010-10-14 �����ѯ�Ż�**/
	//�ͻ�����
	private long clientTypeID1 = -1;
	private long clientTypeID2 = -1;
	private long clientTypeID3 = -1;
	private long clientTypeID4 = -1;
	private long clientTypeID5 = -1;
	private long clientTypeID6 = -1;
	//��������(��ѡ)
	private String loanTypeIDs="";
	private String loanSubTypeIDs="";
	//�������״̬����ѡ��
	private String riskLevels = "";
	//��λ��
	private long borrowClientIDFrom=-1;
	//��λ��
	private long borrowClientIDTo=-1;
	//ί�е�λ��
	private long consignClientIDFrom=-1;
	//ί�е�λ��
	private long consignClientIDTo=-1;	
	//���������
	private double minLoanBalanceAmount = 0.0;
	//���������
	private double maxLoanBalanceAmount = 0.0;
	//��ͬ������
	private double contractRateFrom = 0.0;
	//��ͬ������
	private double contractRateTo = 0.0;	
	//������
	private long periodFrom = -1;
	//������
	private long periodTo = -1;
	//�������
	private Timestamp balanceDate = null;
	/**add by wmzheng at 2010-10-14 �����ѯ�Ż�**/
	
	
	public String getLoanSubTypeIDs() {
		return loanSubTypeIDs;
	}



	public void setLoanSubTypeIDs(String loanSubTypeIDs) {
		this.loanSubTypeIDs = loanSubTypeIDs;
	}



	public String getLoanTypeIDs() {
		return loanTypeIDs;
	}



	public void setLoanTypeIDs(String loanTypeIDs) {
		this.loanTypeIDs = loanTypeIDs;
	}


	public long getClientTypeID1() {
		return clientTypeID1;
	}



	public void setClientTypeID1(long clientTypeID1) {
		this.clientTypeID1 = clientTypeID1;
	}



	public long getClientTypeID2() {
		return clientTypeID2;
	}



	public void setClientTypeID2(long clientTypeID2) {
		this.clientTypeID2 = clientTypeID2;
	}



	public long getClientTypeID3() {
		return clientTypeID3;
	}



	public void setClientTypeID3(long clientTypeID3) {
		this.clientTypeID3 = clientTypeID3;
	}



	public long getClientTypeID4() {
		return clientTypeID4;
	}



	public void setClientTypeID4(long clientTypeID4) {
		this.clientTypeID4 = clientTypeID4;
	}



	public long getClientTypeID5() {
		return clientTypeID5;
	}



	public void setClientTypeID5(long clientTypeID5) {
		this.clientTypeID5 = clientTypeID5;
	}



	public long getClientTypeID6() {
		return clientTypeID6;
	}



	public void setClientTypeID6(long clientTypeID6) {
		this.clientTypeID6 = clientTypeID6;
	}



	/**
	 * @return
	 */
	public long getAssureTypeID()
	{
		return assureTypeID;
	}

	
	
	/**
	 * @return
	 */
	public String getBorrowAccount()
	{
		return borrowAccount;
	}

	/**
	 * @return
	 */
	public long getBorrowClientID()
	{
		return borrowClientID;
	}

	/**
	 * @return
	 */
	public String getConsignAccount()
	{
		return consignAccount;
	}

	/**
	 * @return
	 */
	public long getConsignClientID()
	{
		return consignClientID;
	}

	/**
	 * @return
	 */
	public long getCreditLevel()
	{
		return creditLevel;
	}

	/**
	 * @return
	 */
	public long getDesc()
	{
		return desc;
	}

	/**
	 * @return
	 */
	public long getIntervalNum()
	{
		return intervalNum;
	}

	/**
	 * @return
	 */
	public long getIsCircle()
	{
		return isCircle;
	}

	/**
	 * @return
	 */
	public long getIsPartner()
	{
		return isPartner;
	}

	/**
	 * @return
	 */
	public long getIsTechnical()
	{
		return isTechnical;
	}

	/**
	 * @return
	 */
	public long getLoanClientTypeID()
	{
		return loanClientTypeID;
	}

	/**
	 * @return
	 */
	public long getLoanTypeID()
	{
		return LoanTypeID;
	}

	/**
	 * @return
	 */
	public String getMaxContractCode()
	{
		return maxContractCode;
	}

	/**
	 * @return
	 */
	public double getMaxLoanAmount()
	{
		return maxLoanAmount;
	}

	/**
	 * @return
	 */
	public Timestamp getMaxStartDate()
	{
		return maxStartDate;
	}

	/**
	 * @return
	 */
	public String getMinContractCode()
	{
		return minContractCode;
	}

	/**
	 * @return
	 */
	public double getMinLoanAmount()
	{
		return minLoanAmount;
	}

	/**
	 * @return
	 */
	public Timestamp getMinStartDate()
	{
		return minStartDate;
	}

	/**
	 * @return
	 */
	public long getOrderParam()
	{
		return orderParam;
	}

	/**
	 * @return
	 */
	public long getParentCorpID()
	{
		return parentCorpID;
	}

	/**
	 * @return
	 */
	public String getQueryLevel()
	{
		return queryLevel;
	}

	/**
	 * @return
	 */
	public long getRiskLevel()
	{
		return riskLevel;
	}

	/**
	 * @return
	 */
	public long getStatusID()
	{
		return statusID;
	}

	/**
	 * @return
	 */
	public long getTypeID1()
	{
		return typeID1;
	}

	/**
	 * @return
	 */
	public long getTypeID2()
	{
		return typeID2;
	}

	/**
	 * @return
	 */
	public long getTypeID3()
	{
		return typeID3;
	}

	/**
	 * @param l
	 */
	public void setAssureTypeID(long l)
	{
		assureTypeID = l;
	}

	/**
	 * @param string
	 */
	public void setBorrowAccount(String string)
	{
		borrowAccount = string;
	}

	/**
	 * @param l
	 */
	public void setBorrowClientID(long l)
	{
		borrowClientID = l;
	}

	/**
	 * @param string
	 */
	public void setConsignAccount(String string)
	{
		consignAccount = string;
	}

	/**
	 * @param l
	 */
	public void setConsignClientID(long l)
	{
		consignClientID = l;
	}

	/**
	 * @param l
	 */
	public void setCreditLevel(long l)
	{
		creditLevel = l;
	}

	/**
	 * @param l
	 */
	public void setDesc(long l)
	{
		desc = l;
	}

	/**
	 * @param l
	 */
	public void setIntervalNum(long l)
	{
		intervalNum = l;
	}

	/**
	 * @param l
	 */
	public void setIsCircle(long l)
	{
		isCircle = l;
	}

	/**
	 * @param l
	 */
	public void setIsPartner(long l)
	{
		isPartner = l;
	}

	/**
	 * @param l
	 */
	public void setIsTechnical(long l)
	{
		isTechnical = l;
	}

	/**
	 * @param l
	 */
	public void setLoanClientTypeID(long l)
	{
		loanClientTypeID = l;
	}

	/**
	 * @param l
	 */
	public void setLoanTypeID(long l)
	{
		LoanTypeID = l;
	}

	/**
	 * @param string
	 */
	public void setMaxContractCode(String string)
	{
		maxContractCode = string;
	}

	/**
	 * @param d
	 */
	public void setMaxLoanAmount(double d)
	{
		maxLoanAmount = d;
	}

	/**
	 * @param timestamp
	 */
	public void setMaxStartDate(Timestamp timestamp)
	{
		maxStartDate = timestamp;
	}

	/**
	 * @param string
	 */
	public void setMinContractCode(String string)
	{
		minContractCode = string;
	}

	/**
	 * @param d
	 */
	public void setMinLoanAmount(double d)
	{
		minLoanAmount = d;
	}

	/**
	 * @param timestamp
	 */
	public void setMinStartDate(Timestamp timestamp)
	{
		minStartDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setOrderParam(long l)
	{
		orderParam = l;
	}

	/**
	 * @param l
	 */
	public void setParentCorpID(long l)
	{
		parentCorpID = l;
	}

	/**
	 * @param string
	 */
	public void setQueryLevel(String string)
	{
		queryLevel = string;
	}

	/**
	 * @param l
	 */
	public void setRiskLevel(long l)
	{
		riskLevel = l;
	}

	/**
	 * @param l
	 */
	public void setStatusID(long l)
	{
		statusID = l;
	}

	/**
	 * @param l
	 */
	public void setTypeID1(long l)
	{
		typeID1 = l;
	}

	/**
	 * @param l
	 */
	public void setTypeID2(long l)
	{
		typeID2 = l;
	}

	/**
	 * @param l
	 */
	public void setTypeID3(long l)
	{
		typeID3 = l;
	}

	/**
	 * @return
	 */
	public long getInputUserID()
	{
		return inputUserID;
	}

	/**
	 * @param l
	 */
	public void setInputUserID(long l)
	{
		inputUserID = l;
	}

	/**
	 * @return
	 */
	public long getQueryPurpose()
	{
		return queryPurpose;
	}

	/**
	 * @param l
	 */
	public void setQueryPurpose(long l)
	{
		queryPurpose = l;
	}


	/**
	 * @return
	 */
	public boolean isShowEnd() {
		return showEnd;
	}

	/**
	 * @param b
	 */
	public void setShowEnd(boolean b) {
		showEnd = b;
	}

	/**
	 * @return
	 */
	public boolean getGather() {
		return isGather;
	}

	/**
	 * @param b
	 */
	public void setGather(boolean b) {
		isGather = b;
	}

	/**
	 * @return
	 */
	public long getInOrOut()
	{
		return inOrOut;
	}

	/**
	 * @return
	 */
	public long getTransDiscountTerm()
	{
		return transDiscountTerm;
	}

	/**
	 * @return
	 */
	public long getTransDiscountType()
	{
		return transDiscountType;
	}

	/**
	 * @param l
	 */
	public void setInOrOut(long l)
	{
		inOrOut = l;
	}

	/**
	 * @param l
	 */
	public void setTransDiscountTerm(long l)
	{
		transDiscountTerm = l;
	}

	/**
	 * @param l
	 */
	public void setTransDiscountType(long l)
	{
		transDiscountType = l;
	}

	/**
	 * @return Returns the maxEndDate.
	 */
	public Timestamp getMaxEndDate()
	{
		return maxEndDate;
	}
	/**
	 * @param maxEndDate The maxEndDate to set.
	 */
	public void setMaxEndDate(Timestamp maxEndDate)
	{
		this.maxEndDate=maxEndDate;
	}
	/**
	 * @return Returns the maxRate.
	 */
	public double getMaxRate()
	{
		return maxRate;
	}
	/**
	 * @param maxRate The maxRate to set.
	 */
	public void setMaxRate(double maxRate)
	{
		this.maxRate=maxRate;
	}
	/**
	 * @return Returns the minEndDate.
	 */
	public Timestamp getMinEndDate()
	{
		return minEndDate;
	}
	/**
	 * @param minEndDate The minEndDate to set.
	 */
	public void setMinEndDate(Timestamp minEndDate)
	{
		this.minEndDate=minEndDate;
	}
	/**
	 * @return Returns the minRate.
	 */
	public double getMinRate()
	{
		return minRate;
	}
	/**
	 * @param minRate The minRate to set.
	 */
	public void setMinRate(double minRate)
	{
		this.minRate=minRate;
	}
	/**
	 * @return Returns the maxDiscountDate.
	 */
	public Timestamp getMaxDiscountDate()
	{
		return maxDiscountDate;
	}
	/**
	 * @param maxDiscountDate The maxDiscountDate to set.
	 */
	public void setMaxDiscountDate(Timestamp maxDiscountDate)
	{
		this.maxDiscountDate=maxDiscountDate;
	}
	/**
	 * @return Returns the minDiscountDate.
	 */
	public Timestamp getMinDiscountDate()
	{
		return minDiscountDate;
	}
	/**
	 * @param minDiscountDate The minDiscountDate to set.
	 */
	public void setMinDiscountDate(Timestamp minDiscountDate)
	{
		this.minDiscountDate=minDiscountDate;
	}
	/**
	 * @return Returns the discountClientName.
	 */
	public String getDiscountClientName()
	{
		return discountClientName;
	}
	/**
	 * @param discountClientName The discountClientName to set.
	 */
	public void setDiscountClientName(String discountClientName)
	{
		this.discountClientName=discountClientName;
	}
    /**
     * @return Returns the checkAmount.
     */
    public double getCheckAmount()
    {
        return checkAmount;
    }
    /**
     * @param checkAmount The checkAmount to set.
     */
    public void setCheckAmount(double checkAmount)
    {
        this.checkAmount = checkAmount;
    }
    /**
     * @return Returns the discountInterest.
     */
    public double getDiscountInterest()
    {
        return discountInterest;
    }
    /**
     * @param discountInterest The discountInterest to set.
     */
    public void setDiscountInterest(double discountInterest)
    {
        this.discountInterest = discountInterest;
    }
    /**
     * @return Returns the discountPurchaserInterest.
     */
    public double getDiscountPurchaserInterest()
    {
        return discountPurchaserInterest;
    }
    /**
     * @param discountPurchaserInterest The discountPurchaserInterest to set.
     */
    public void setDiscountPurchaserInterest(double discountPurchaserInterest)
    {
        this.discountPurchaserInterest = discountPurchaserInterest;
    }
    /**
     * @return Returns the purchaserInterestRate.
     */
    public double getPurchaserInterestRate()
    {
        return purchaserInterestRate;
    }
    /**
     * @param purchaserInterestRate The purchaserInterestRate to set.
     */
    public void setPurchaserInterestRate(double purchaserInterestRate)
    {
        this.purchaserInterestRate = purchaserInterestRate;
    }
	/**
	 * @return Returns the lOAN.
	 */
	public static long getLOAN() {
		return LOAN;
	}
	/**
	 * @return Returns the tX.
	 */
	public static long getTX() {
		return TX;
	}
	/**
	 * @return Returns the zTX.
	 */
	public static long getZTX() {
		return ZTX;
	}
	/**
	 * @return Returns the currencyID.
	 */
	public long getCurrencyID() {
		return currencyID;
	}
	/**
	 * @param currencyID The currencyID to set.
	 */
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}
	/**
	 * @return Returns the officeID.
	 */
	public long getOfficeID() {
		return officeID;
	}
	/**
	 * @param officeID The officeID to set.
	 */
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}
	/**
	 * @return Returns the isGather.
	 */
	public boolean isGather() {
		return isGather;
	}



	public long getTsDiscountTypeID() {
		return tsDiscountTypeID;
	}



	public void setTsDiscountTypeID(long tsDiscountTypeID) {
		this.tsDiscountTypeID = tsDiscountTypeID;
	}



	public String getStatusIDs() {
		return statusIDs;
	}



	public void setStatusIDs(String statusIDs) {
		this.statusIDs = statusIDs;
	}



	public long getIscredit() {
		return iscredit;
	}



	public void setIscredit(long iscredit) {
		this.iscredit = iscredit;
	}



	public long getIsrecognizance() {
		return isrecognizance;
	}



	public void setIsrecognizance(long isrecognizance) {
		this.isrecognizance = isrecognizance;
	}



	public long getIsassure() {
		return isassure;
	}



	public void setIsassure(long isassure) {
		this.isassure = isassure;
	}



	public long getIsimpawn() {
		return isimpawn;
	}



	public void setIsimpawn(long isimpawn) {
		this.isimpawn = isimpawn;
	}



	public long getIspledge() {
		return ispledge;
	}



	public void setIspledge(long ispledge) {
		this.ispledge = ispledge;
	}



	public long getIsrepurchase() {
		return isrepurchase;
	}



	public void setIsrepurchase(long isrepurchase) {
		this.isrepurchase = isrepurchase;
	}




	public Timestamp getMinDisccountInputDate() {
		return minDisccountInputDate;
	}

	public Timestamp getBalanceDate() {
		return balanceDate;
	}





	public void setMinDisccountInputDate(Timestamp minDisccountInputDate) {
		this.minDisccountInputDate = minDisccountInputDate;
	}

	public void setBalanceDate(Timestamp balanceDate) {
		this.balanceDate = balanceDate;
	}





	public Timestamp getMaxDisccountInputDate() {
		return maxDisccountInputDate;
	}



	public void setMaxDisccountInputDate(Timestamp maxDisccountInputDate) {
		this.maxDisccountInputDate = maxDisccountInputDate;
	}



	public double getMinPayerRate() {
		return minPayerRate;
	}



	public void setMinPayerRate(double minPayerRate) {
		this.minPayerRate = minPayerRate;
	}



	public double getMaxPayerRate() {
		return maxPayerRate;
	}



	public void setMaxPayerRate(double maxPayerRate) {
		this.maxPayerRate = maxPayerRate;
	}



	public double getMinCheckAmount() {
		return minCheckAmount;
	}



	public void setMinCheckAmount(double minCheckAmount) {
		this.minCheckAmount = minCheckAmount;
	}



	public double getMaxCheckAmount() {
		return maxCheckAmount;
	}



	public void setMaxCheckAmount(double maxCheckAmount) {
		this.maxCheckAmount = maxCheckAmount;
	}



	public long getDiscountClientID() {
		return discountClientID;
	}



	public void setDiscountClientID(long discountClientID) {
		this.discountClientID = discountClientID;
	}



	public long getMinborrowClientID() {
		return minborrowClientID;
	}



	public void setMinborrowClientID(long minborrowClientID) {
		this.minborrowClientID = minborrowClientID;
	}



	public long getMaxborrowClientID() {
		return maxborrowClientID;
	}



	public void setMaxborrowClientID(long maxborrowClientID) {
		this.maxborrowClientID = maxborrowClientID;
	}










	public long getBorrowClientIDFrom() {
		return borrowClientIDFrom;
	}



	public void setBorrowClientIDFrom(long borrowClientIDFrom) {
		this.borrowClientIDFrom = borrowClientIDFrom;
	}



	public long getBorrowClientIDTo() {
		return borrowClientIDTo;
	}



	public void setBorrowClientIDTo(long borrowClientIDTo) {
		this.borrowClientIDTo = borrowClientIDTo;
	}



	public long getConsignClientIDFrom() {
		return consignClientIDFrom;
	}



	public void setConsignClientIDFrom(long consignClientIDFrom) {
		this.consignClientIDFrom = consignClientIDFrom;
	}



	public long getConsignClientIDTo() {
		return consignClientIDTo;
	}



	public void setConsignClientIDTo(long consignClientIDTo) {
		this.consignClientIDTo = consignClientIDTo;
	}


	public double getContractRateFrom() {
		return contractRateFrom;
	}



	public void setContractRateFrom(double contractRateFrom) {
		this.contractRateFrom = contractRateFrom;
	}



	public double getContractRateTo() {
		return contractRateTo;
	}



	public void setContractRateTo(double contractRateTo) {
		this.contractRateTo = contractRateTo;
	}



	public double getMaxLoanBalanceAmount() {
		return maxLoanBalanceAmount;
	}



	public void setMaxLoanBalanceAmount(double maxLoanBalanceAmount) {
		this.maxLoanBalanceAmount = maxLoanBalanceAmount;
	}



	public double getMinLoanBalanceAmount() {
		return minLoanBalanceAmount;
	}



	public void setMinLoanBalanceAmount(double minLoanBalanceAmount) {
		this.minLoanBalanceAmount = minLoanBalanceAmount;
	}


	public long getPeriodFrom() {
		return periodFrom;
	}



	public void setPeriodFrom(long periodFrom) {
		this.periodFrom = periodFrom;
	}



	public long getPeriodTo() {
		return periodTo;
	}



	public void setPeriodTo(long periodTo) {
		this.periodTo = periodTo;
	}



	public String getRiskLevels() {
		return riskLevels;
	}



	public void setRiskLevels(String riskLevels) {
		this.riskLevels = riskLevels;
	}



	public String getBillDrawer() {
		return billDrawer;
	}



	public void setBillDrawer(String billDrawer) {
		this.billDrawer = billDrawer;
	}




}