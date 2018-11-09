package com.iss.itreasury.loan.query.dataentity;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
import java.io.Serializable;
import java.sql.Timestamp;

public class QueryLoanApplyInfo implements Serializable
{	
	public static final long DB=8;
	public static final long RZZL=10;
	public static final long LOAN=2;
	//��ѯ��������
	private long queryPurpose;
	//��������
	private long loanType=-1;
	
	//�������ſ�ʼ
	private String maxApplyCode="";
	
	//�������Ž���
	private String minApplyCode="";
	
	//������״̬
	private long loanStatusID=-1;
	
	//��λ
	private long borrowClientID=-1;
	
	//��λ�˺�
	private String borrowAccount="";
	
	//�ͻ�����
	private long loanClientTypeID=-1;
	
	//���ܵ�λ
	private long parentCorpID=-1;
	
	//ί�е�λ
	private long consignClientID=-1;
	
	//ί�е�λ�˺�
	private String consignAccount="";
	
	//�����ʼ
	private double maxLoanAmount=0;
	
	//���������
	private double minLoanAmount=0;
	
	//���ʼʱ��
	private Timestamp maxLoanDate=null;
	
	//�������ʱ��
	private Timestamp minLoanDate=null;
	
	//��������
	private long intervalNum=-1;
	
	//�����ύ��ʼʱ��
	private Timestamp maxInputDate=null;
	
	//�����ύ����ʱ��
	private Timestamp minInputDate=null;
	
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
	
	//�����������
	private long inputUserID=-1;
	
	//�����ֶ�
	private long orderParam=-1;
	
	//desc
	private long desc=-1;	
	
	//��ѯ����
	private String queryLevel="";
	
	//ת�������ͣ����롢������
	private long inOrOut= -1;
	  
	//ת�������ࣨ��ϡ��ع���
	private long transDiscountType = -1;
	  
	//ת��������
	private long transDiscountTerm= -1;
	
	//���´�
	private long officeID  = -1;
	//����
	private long currencyID = -1;
	// �������ࣨ��ҵ�жһ�Ʊ�����гжһ�Ʊ��	
	private long tsDiscountTypeID =-1;	
    
	/**add by wmzheng at 2010-10-13 �����ѯ�Ż�**/
	//�������ͣ���ѡ��
	private String loanTypes = "";	
	//������״̬����ѡ��
	private String loanStatusIDs = "";
	//��λ��
	private long borrowClientIDFrom=-1;
	//��λ��
	private long borrowClientIDTo=-1;
	//ί�е�λ��
	private long consignClientIDFrom=-1;
	//ί�е�λ��
	private long consignClientIDTo=-1;	
	//ִ��������
	private double executeRateFrom = 0.0;
	//ִ��������
	private double executeRateTo = 0.0;	
	//������
	private long periodFrom = -1;
	//������
	private long periodTo = -1;
	/**add by wmzheng at 2010-10-13 �����ѯ�Ż�**/
	
	
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
	public long getLoanStatusID()
	{
		return loanStatusID;
	}

	/**
	 * @return
	 */
	public long getLoanType()
	{
		return loanType;
	}

	/**
	 * @return
	 */
	public String getMaxApplyCode()
	{
		return maxApplyCode;
	}

	/**
	 * @return
	 */
	public Timestamp getMaxInputDate()
	{
		return maxInputDate;
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
	public Timestamp getMaxLoanDate()
	{
		return maxLoanDate;
	}

	/**
	 * @return
	 */
	public String getMinApplyCode()
	{
		return minApplyCode;
	}

	/**
	 * @return
	 */
	public Timestamp getMinInputDate()
	{
		return minInputDate;
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
	public Timestamp getMinLoanDate()
	{
		return minLoanDate;
	}

	/**
	 * @return
	 */
	public long getParentCorpID()
	{
		return parentCorpID;
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
	public void setLoanStatusID(long l)
	{
		loanStatusID = l;
	}

	/**
	 * @param l
	 */
	public void setLoanType(long l)
	{
		loanType = l;
	}

	/**
	 * @param string
	 */
	public void setMaxApplyCode(String string)
	{
		maxApplyCode = string;
	}

	/**
	 * @param timestamp
	 */
	public void setMaxInputDate(Timestamp timestamp)
	{
		maxInputDate = timestamp;
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
	public void setMaxLoanDate(Timestamp timestamp)
	{
		maxLoanDate = timestamp;
	}

	/**
	 * @param string
	 */
	public void setMinApplyCode(String string)
	{
		minApplyCode = string;
	}

	/**
	 * @param timestamp
	 */
	public void setMinInputDate(Timestamp timestamp)
	{
		minInputDate = timestamp;
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
	public void setMinLoanDate(Timestamp timestamp)
	{
		minLoanDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setParentCorpID(long l)
	{
		parentCorpID = l;
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
	public long getOrderParam()
	{
		return orderParam;
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
	public void setOrderParam(long l)
	{
		orderParam = l;
	}

	/**
	 * @return
	 */
	public String getQueryLevel()
	{
		return queryLevel;
	}

	/**
	 * @param string
	 */
	public void setQueryLevel(String string)
	{
		queryLevel = string;
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

	public long getTsDiscountTypeID() {
		return tsDiscountTypeID;
	}

	public void setTsDiscountTypeID(long tsDiscountTypeID) {
		this.tsDiscountTypeID = tsDiscountTypeID;
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

	public double getExecuteRateFrom() {
		return executeRateFrom;
	}

	public void setExecuteRateFrom(double executeRateFrom) {
		this.executeRateFrom = executeRateFrom;
	}

	public double getExecuteRateTo() {
		return executeRateTo;
	}

	public void setExecuteRateTo(double executeRateTo) {
		this.executeRateTo = executeRateTo;
	}
	
	public String getLoanStatusIDs() {
		return loanStatusIDs;
	}

	public void setLoanStatusIDs(String loanStatusIDs) {
		this.loanStatusIDs = loanStatusIDs;
	}

	public String getLoanTypes() {
		return loanTypes;
	}

	public void setLoanTypes(String loanTypes) {
		this.loanTypes = loanTypes;
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

	public long getQueryPurpose() {
		return queryPurpose;
	}

	public void setQueryPurpose(long queryPurpose) {
		this.queryPurpose = queryPurpose;
	}

	
		
}