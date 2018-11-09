/*
 * Created on 2003-11-11
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.query.paraminfo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author xrli
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QueryFixedDepositInfo implements Serializable
{

	/**
	 * 
	 */
	public QueryFixedDepositInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	private long OfficeID = -1;                      //���´�  
	private long CurrencyID = -1;                    //����
	private String ClientNoFrom = "";                //�ͻ����
    private String ClientNoTo = "";                  //�ͻ����
    private String ClientNameFrom = "";              //�ͻ�����
    private String ClientNameTo = "";                //�ͻ�����
    private String AccountNoFrom = "";               //�˻����
    private String AccountNoTo = "";                 //�˻����
    //add by 2012-05-18 ���ָ���˻����
	private String appointAccountNo = "";
    private long DepositOrgin = -1;                  //�����Դ
    private long ClientSort = -1;                    //�ͻ����� 
    private long ClientType = -1;  					 //��ҵ����
    private long AccountType = -1;  				 //�˻�����
    private long ParentCompany1  = -1;                //�ϼ���λ1
	private long ParentCompany2  = -1;                //�ϼ���λ2
    private long ClientManager = -1;     			  //�ͻ�����id
	private long EnterpriseTypeID1 = -1;//�ͻ�����1
	private long EnterpriseTypeID2 = -1;//�ͻ�����2
	private long EnterpriseTypeID3 = -1;//�ͻ�����3
	private long EnterpriseTypeID4 = -1;//�ͻ�����4
	private long EnterpriseTypeID5 = -1;//�ͻ�����5
	private long EnterpriseTypeID6 = -1;//�ͻ�����6
    private long IsFixedDeposit = -1;                 //�Ƿ��ѯ���ڴ��   
    private String FixedDepositNoFrom = "";           //���ڵ��ݺ�         
    private String FixedDepositNoTo = "";             //���ڵ��ݺ�	     
    private Timestamp FixedStartDateFrom = null;      //���ڿ�ʼ����      
    private Timestamp FixedStartDateTo = null;        //���ڿ�ʼ����       
	private Timestamp FixedEndDateFrom = null;        //���ڽ�������         
	private Timestamp FixedEndDateTo = null;          //���ڽ�������    
	private long FixedDepositStatus = -1;             //����״̬       
	private long FixedDepositTermFrom = -1;           //���ڴ������
	private long FixedDepositTermTo = -1;             //���ڴ������      
	private double FixedAmountFrom = 0.0;             //���ڽ��      
	private double FixedAmountTo = 0.0;               //���ڽ��  
	private double FixedRate = 0.0;                   //��������  
	private Timestamp FixedEndDate = null;            //���ڽ�ֹ����  
	                                                            
	private long IsNoticeDeposit = -1;                //�Ƿ��ѯ֪ͨ��� 
    private String NoticeDepositNoFrom = "";          //֪ͨ���ݺ�
    private String NoticeDepositNoTo = "";            //֪ͨ���ݺ�	
    private long NoticeDays =-1;                      //֪ͨ�������                         
    private Timestamp NoticeStartDateFrom = null;     //֪ͨ��ʼ����	
    private Timestamp NoticeStartDateTo = null;       //֪ͨ��ʼ����	
	private long NoticeDepositStatus = -1;	          //֪ͨ���״̬
	private double NoticeBalanceFrom = 0.0;           //֪ͨ������
	private double NoticeBalanceTo = 0.0;             //֪ͨ������
	private double NoticeDrawAmountFrom = 0.0;        //֪ͨ���֧ȡ���
	private double NoticeDrawAmountTo = 0.0;          //֪ͨ���֧ȡ��� 
	private Timestamp NoticeEndDate = null;           //֪ͨ����ֹ����	
	
	private long ExtendAttribute1 = -1;					//��չ����1
    private long ExtendAttribute2 = -1;					//��չ����2
    private long ExtendAttribute3 = -1;					//��չ����3
    private long ExtendAttribute4 = -1;					//��չ����4
    
    private String ExtendAttribute5 = "";               //��չ����5
    
    // ��֤�����ѯ
    private long IsMarginDeposit = -1;		// �Ƿ��ѯ��֤����
    private String MarginDepositNoFrom = "";	// ��֤����ݺ�
    private String MarginDepositNoTo = "";
    private Timestamp MarginStartDateFrom = null; // ��ʼ����
    private Timestamp MarginStartDateTo = null;
    private Timestamp MarginEndDateFrom = null;	// ��������
    private Timestamp MarginEndDateTo = null;
	private long MarginDepositStatus = -1;		// ��֤����״̬
	private double MarginAmountFrom = 0.0;		// ��֤������
	private double MarginAmountTo = 0.0;
	private double MarginRate = 0.0;	// ����
	private Timestamp MarginEndDate = null;	// ���ڽ�ֹ����
	
    
	private long IsLeaching  = -1;                    //�˿�    
    private long Desc = 1;                            //����ʽ
    private long OrderField = 1;                      //�����ֶ�
    
    private long DepositNoChoose = -1;				//��ѯ�浥��ʽѡ��
    
    private long unit = 1;
    private String ControlSource = null;
    
	/**
	 * @return
	 */
	public String getAccountNoFrom()
	{
		return AccountNoFrom;
	}

	/**
	 * @return
	 */
	public String getAccountNoTo()
	{
		return AccountNoTo;
	}

	/**
	 * @return
	 */
	public String getClientNameFrom()
	{
		return ClientNameFrom;
	}

	/**
	 * @return Returns the clientManager.
	 */
	public long getClientManager() {
		return ClientManager;
	}
	/**
	 * @param clientManager The clientManager to set.
	 */
	public void setClientManager(long clientManager) {
		ClientManager = clientManager;
	}
	/**
	 * @return
	 */
	
	public String getClientNameTo()
	{
		return ClientNameTo;
	}

	/**
	 * @return
	 */
	public String getClientNoFrom()
	{
		return ClientNoFrom;
	}

	/**
	 * @return
	 */
	public String getClientNoTo()
	{
		return ClientNoTo;
	}

	/**
	 * @return
	 */
	public long getClientSort()
	{
		return ClientSort;
	}

	/**
	 * @return
	 */
	public long getClientType()
	{
		return ClientType;
	}

	/**
	 * @return
	 */
	public long getDepositOrgin()
	{
		return DepositOrgin;
	}

	/**
	 * @return
	 */
	public long getDesc()
	{
		return Desc;
	}

	/**
	 * @return
	 */
	public double getFixedAmountFrom()
	{
		return FixedAmountFrom;
	}

	/**
	 * @return
	 */
	public double getFixedAmountTo()
	{
		return FixedAmountTo;
	}

	/**
	 * @return
	 */
	public String getFixedDepositNoFrom()
	{
		return FixedDepositNoFrom;
	}

	/**
	 * @return
	 */
	public String getFixedDepositNoTo()
	{
		return FixedDepositNoTo;
	}

	/**
	 * @return
	 */
	public long getFixedDepositStatus()
	{
		return FixedDepositStatus;
	}
	

	/**
	 * @return
	 */
	public Timestamp getFixedEndDate()
	{
		return FixedEndDate;
	}

	/**
	 * @return
	 */
	public Timestamp getFixedEndDateFrom()
	{
		return FixedEndDateFrom;
	}

	/**
	 * @return
	 */
	public Timestamp getFixedEndDateTo()
	{
		return FixedEndDateTo;
	}

	/**
	 * @return
	 */
	public double getFixedRate()
	{
		return FixedRate;
	}

	/**
	 * @return
	 */
	public Timestamp getFixedStartDateFrom()
	{
		return FixedStartDateFrom;
	}

	/**
	 * @return
	 */
	public Timestamp getFixedStartDateTo()
	{
		return FixedStartDateTo;
	}

	/**
	 * @return
	 */
	public long getIsFixedDeposit()
	{
		return IsFixedDeposit;
	}

	/**
	 * @return
	 */
	public long getIsLeaching()
	{
		return IsLeaching;
	}

	/**
	 * @return
	 */
	public long getIsNoticeDeposit()
	{
		return IsNoticeDeposit;
	}

	/**
	 * @return
	 */
	public double getNoticeBalanceFrom()
	{
		return NoticeBalanceFrom;
	}

	/**
	 * @return
	 */
	public double getNoticeBalanceTo()
	{
		return NoticeBalanceTo;
	}

	/**
	 * @return
	 */
	public String getNoticeDepositNoFrom()
	{
		return NoticeDepositNoFrom;
	}

	/**
	 * @return
	 */
	public String getNoticeDepositNoTo()
	{
		return NoticeDepositNoTo;
	}

	/**
	 * @return
	 */
	public long getNoticeDepositStatus()
	{
		return NoticeDepositStatus;
	}

	/**
	 * @return
	 */
	public double getNoticeDrawAmountFrom()
	{
		return NoticeDrawAmountFrom;
	}

	/**
	 * @return
	 */
	public double getNoticeDrawAmountTo()
	{
		return NoticeDrawAmountTo;
	}

	/**
	 * @return
	 */
	public Timestamp getNoticeEndDate()
	{
		return NoticeEndDate;
	}

	/**
	 * @return
	 */
	public Timestamp getNoticeStartDateFrom()
	{
		return NoticeStartDateFrom;
	}

	/**
	 * @return
	 */
	public Timestamp getNoticeStartDateTo()
	{
		return NoticeStartDateTo;
	}

	/**
	 * @return
	 */
	public long getOrderField()
	{
		return OrderField;
	}

	

	/**
	 * @param string
	 */
	public void setAccountNoFrom(String string)
	{
		AccountNoFrom = string;
	}

	/**
	 * @param string
	 */
	public void setAccountNoTo(String string)
	{
		AccountNoTo = string;
	}

	/**
	 * @param string
	 */
	public void setClientNameFrom(String string)
	{
		ClientNameFrom = string;
	}

	/**
	 * @param string
	 */
	public void setClientNameTo(String string)
	{
		ClientNameTo = string;
	}

	/**
	 * @param string
	 */
	public void setClientNoFrom(String string)
	{
		ClientNoFrom = string;
	}

	/**
	 * @param string
	 */
	public void setClientNoTo(String string)
	{
		ClientNoTo = string;
	}

	/**
	 * @param l
	 */
	public void setClientSort(long l)
	{
		ClientSort = l;
	}

	/**
	 * @param l
	 */
	public void setClientType(long l)
	{
		ClientType = l;
	}

	/**
	 * @param l
	 */
	public void setDepositOrgin(long l)
	{
		DepositOrgin = l;
	}

	/**
	 * @param l
	 */
	public void setDesc(long l)
	{
		Desc = l;
	}

	/**
	 * @param d
	 */
	public void setFixedAmountFrom(double d)
	{
		FixedAmountFrom = d;
	}

	/**
	 * @param d
	 */
	public void setFixedAmountTo(double d)
	{
		FixedAmountTo = d;
	}

	/**
	 * @param string
	 */
	public void setFixedDepositNoFrom(String string)
	{
		FixedDepositNoFrom = string;
	}

	/**
	 * @param string
	 */
	public void setFixedDepositNoTo(String string)
	{
		FixedDepositNoTo = string;
	}

	/**
	 * @param l
	 */
	public void setFixedDepositStatus(long l)
	{
		FixedDepositStatus = l;
	}
	

	/**
	 * @param timestamp
	 */
	public void setFixedEndDate(Timestamp timestamp)
	{
		FixedEndDate = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setFixedEndDateFrom(Timestamp timestamp)
	{
		FixedEndDateFrom = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setFixedEndDateTo(Timestamp timestamp)
	{
		FixedEndDateTo = timestamp;
	}

	/**
	 * @param d
	 */
	public void setFixedRate(double d)
	{
		FixedRate = d;
	}

	/**
	 * @param timestamp
	 */
	public void setFixedStartDateFrom(Timestamp timestamp)
	{
		FixedStartDateFrom = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setFixedStartDateTo(Timestamp timestamp)
	{
		FixedStartDateTo = timestamp;
	}

	/**
	 * @param l
	 */
	public void setIsFixedDeposit(long l)
	{
		IsFixedDeposit = l;
	}

	/**
	 * @param l
	 */
	public void setIsLeaching(long l)
	{
		IsLeaching = l;
	}

	/**
	 * @param l
	 */
	public void setIsNoticeDeposit(long l)
	{
		IsNoticeDeposit = l;
	}

	/**
	 * @param d
	 */
	public void setNoticeBalanceFrom(double d)
	{
		NoticeBalanceFrom = d;
	}

	/**
	 * @param d
	 */
	public void setNoticeBalanceTo(double d)
	{
		NoticeBalanceTo = d;
	}

	/**
	 * @param string
	 */
	public void setNoticeDepositNoFrom(String string)
	{
		NoticeDepositNoFrom = string;
	}

	/**
	 * @param string
	 */
	public void setNoticeDepositNoTo(String string)
	{
		NoticeDepositNoTo = string;
	}

	/**
	 * @param l
	 */
	public void setNoticeDepositStatus(long l)
	{
		NoticeDepositStatus = l;
	}

	/**
	 * @param d
	 */
	public void setNoticeDrawAmountFrom(double d)
	{
		NoticeDrawAmountFrom = d;
	}

	/**
	 * @param d
	 */
	public void setNoticeDrawAmountTo(double d)
	{
		NoticeDrawAmountTo = d;
	}

	/**
	 * @param timestamp
	 */
	public void setNoticeEndDate(Timestamp timestamp)
	{
		NoticeEndDate = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setNoticeStartDateFrom(Timestamp timestamp)
	{
		NoticeStartDateFrom = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setNoticeStartDateTo(Timestamp timestamp)
	{
		NoticeStartDateTo = timestamp;
	}

	/**
	 * @param l
	 */
	public void setOrderField(long l)
	{
		OrderField = l;
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
	public long getOfficeID()
	{
		return OfficeID;
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		CurrencyID = l;
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		OfficeID = l;
	}

	/**
	 * @return
	 */
	public long getFixedDepositTermFrom()
	{
		return FixedDepositTermFrom;
	}

	/**
	 * @return
	 */
	public long getFixedDepositTermTo()
	{
		return FixedDepositTermTo;
	}

	/**
	 * @param l
	 */
	public void setFixedDepositTermFrom(long l)
	{
		FixedDepositTermFrom = l;
	}

	/**
	 * @param l
	 */
	public void setFixedDepositTermTo(long l)
	{
		FixedDepositTermTo = l;
	}

	/**
	 * @return
	 */
	public long getParentCompany1()
	{
		return ParentCompany1;
	}

	/**
	 * @return
	 */
	public long getParentCompany2()
	{
		return ParentCompany2;
	}

	/**
	 * @param l
	 */
	public void setParentCompany1(long l)
	{
		ParentCompany1 = l;
	}

	/**
	 * @param l
	 */
	public void setParentCompany2(long l)
	{
		ParentCompany2 = l;
	}

	/**
	 * @return
	 */
	public long getNoticeDays()
	{
		return NoticeDays;
	}

	/**
	 * @param l
	 */
	public void setNoticeDays(long l)
	{
		NoticeDays = l;
	}

	/**
	 * @return Returns the extendAttribute1.
	 */
	public long getExtendAttribute1()
	{
		return ExtendAttribute1;
	}
	/**
	 * @param extendAttribute1 The extendAttribute1 to set.
	 */
	public void setExtendAttribute1(long extendAttribute1)
	{
		ExtendAttribute1 = extendAttribute1;
	}
	/**
	 * @return Returns the extendAttribute2.
	 */
	public long getExtendAttribute2()
	{
		return ExtendAttribute2;
	}
	/**
	 * @param extendAttribute2 The extendAttribute2 to set.
	 */
	public void setExtendAttribute2(long extendAttribute2)
	{
		ExtendAttribute2 = extendAttribute2;
	}
	/**
	 * @return Returns the extendAttribute3.
	 */
	public long getExtendAttribute3()
	{
		return ExtendAttribute3;
	}
	/**
	 * @param extendAttribute3 The extendAttribute3 to set.
	 */
	public void setExtendAttribute3(long extendAttribute3)
	{
		ExtendAttribute3 = extendAttribute3;
	}
	/**
	 * @return Returns the extendAttribute4.
	 */
	public long getExtendAttribute4()
	{
		return ExtendAttribute4;
	}
	/**
	 * @param extendAttribute4 The extendAttribute4 to set.
	 */
	public void setExtendAttribute4(long extendAttribute4)
	{
		ExtendAttribute4 = extendAttribute4;
	}
	/**
	 * @return Returns the accountType.
	 */
	public String getExtendAttribute5() {
		return ExtendAttribute5;
	}
	/**
	 * @return Returns the accountType.
	 */
	public void setExtendAttribute5(String extendAttribute5) {
		ExtendAttribute5 = extendAttribute5;
	}
	/**
	 * @return Returns the accountType.
	 */
	public long getAccountType() {
		return AccountType;
	}
	/**
	 * @param accountType The accountType to set.
	 */
	public void setAccountType(long accountType) {
		AccountType = accountType;
	}
	
	/**
	 * @return Returns the enterpriseTypeID1.
	 */
	public long getEnterpriseTypeID1() {
		return EnterpriseTypeID1;
	}
	/**
	 * @param enterpriseTypeID1 The enterpriseTypeID1 to set.
	 */
	public void setEnterpriseTypeID1(long enterpriseTypeID1) {
		EnterpriseTypeID1 = enterpriseTypeID1;
	}
	/**
	 * @return Returns the enterpriseTypeID2.
	 */
	public long getEnterpriseTypeID2() {
		return EnterpriseTypeID2;
	}
	/**
	 * @param enterpriseTypeID2 The enterpriseTypeID2 to set.
	 */
	public void setEnterpriseTypeID2(long enterpriseTypeID2) {
		EnterpriseTypeID2 = enterpriseTypeID2;
	}
	/**
	 * @return Returns the enterpriseTypeID3.
	 */
	public long getEnterpriseTypeID3() {
		return EnterpriseTypeID3;
	}
	/**
	 * @param enterpriseTypeID3 The enterpriseTypeID3 to set.
	 */
	public void setEnterpriseTypeID3(long enterpriseTypeID3) {
		EnterpriseTypeID3 = enterpriseTypeID3;
	}
	/**
	 * @return Returns the enterpriseTypeID4.
	 */
	public long getEnterpriseTypeID4() {
		return EnterpriseTypeID4;
	}
	/**
	 * @param enterpriseTypeID4 The enterpriseTypeID4 to set.
	 */
	public void setEnterpriseTypeID4(long enterpriseTypeID4) {
		EnterpriseTypeID4 = enterpriseTypeID4;
	}
	/**
	 * @return Returns the enterpriseTypeID5.
	 */
	public long getEnterpriseTypeID5() {
		return EnterpriseTypeID5;
	}
	/**
	 * @param enterpriseTypeID5 The enterpriseTypeID5 to set.
	 */
	public void setEnterpriseTypeID5(long enterpriseTypeID5) {
		EnterpriseTypeID5 = enterpriseTypeID5;
	}
	/**
	 * @return Returns the enterpriseTypeID6.
	 */
	public long getEnterpriseTypeID6() {
		return EnterpriseTypeID6;
	}
	/**
	 * @param enterpriseTypeID6 The enterpriseTypeID6 to set.
	 */
	public void setEnterpriseTypeID6(long enterpriseTypeID6) {
		EnterpriseTypeID6 = enterpriseTypeID6;
	}
	/**
	 * @return Returns the depositNoChoose.
	 */
	public long getDepositNoChoose() {
		return DepositNoChoose;
	}
	/**
	 * @param depositNoChoose The depositNoChoose to set.
	 */
	public void setDepositNoChoose(long depositNoChoose) {
		DepositNoChoose = depositNoChoose;
	}

	/**
	 * �Ƿ��ѯ��֤����
	 * @return ���� isMarginDeposit��
	 */
	public long getIsMarginDeposit() {
		return IsMarginDeposit;
	}

	/**
	 * �Ƿ��ѯ��֤����
	 * @param isMarginDeposit Ҫ���õ� isMarginDeposit��
	 */
	public void setIsMarginDeposit(long isMarginDeposit) {
		IsMarginDeposit = isMarginDeposit;
	}

	/**
	 * ��֤������ - ��ʼ
	 * @return ���� marginAmountFrom��
	 */
	public double getMarginAmountFrom() {
		return MarginAmountFrom;
	}

	/**
	 * ��֤������ - ��ʼ
	 * @param marginAmountFrom Ҫ���õ� marginAmountFrom��
	 */
	public void setMarginAmountFrom(double marginAmountFrom) {
		MarginAmountFrom = marginAmountFrom;
	}

	/**
	 * ��֤������ - ����
	 * @return ���� marginAmountTo��
	 */
	public double getMarginAmountTo() {
		return MarginAmountTo;
	}

	/**
	 * ��֤������ - ����
	 * @param marginAmountTo Ҫ���õ� marginAmountTo��
	 */
	public void setMarginAmountTo(double marginAmountTo) {
		MarginAmountTo = marginAmountTo;
	}

	/**
	 * ��֤����ݺ� - ��ʼ
	 * @return ���� marginDepositNoFrom��
	 */
	public String getMarginDepositNoFrom() {
		return MarginDepositNoFrom;
	}

	/**
	 * ��֤����ݺ� - ��ʼ
	 * @param marginDepositNoFrom Ҫ���õ� marginDepositNoFrom��
	 */
	public void setMarginDepositNoFrom(String marginDepositNoFrom) {
		MarginDepositNoFrom = marginDepositNoFrom;
	}

	/**
	 * ��֤����ݺ� - ����
	 * @return ���� marginDepositNoTo��
	 */
	public String getMarginDepositNoTo() {
		return MarginDepositNoTo;
	}

	/**
	 * ��֤����ݺ� - ����
	 * @param marginDepositNoTo Ҫ���õ� marginDepositNoTo��
	 */
	public void setMarginDepositNoTo(String marginDepositNoTo) {
		MarginDepositNoTo = marginDepositNoTo;
	}

	/**
	 * ��֤����״̬
	 * @return ���� marginDepositStatus��
	 */
	public long getMarginDepositStatus() {
		return MarginDepositStatus;
	}

	/**
	 * ��֤����״̬
	 * @param marginDepositStatus Ҫ���õ� marginDepositStatus��
	 */
	public void setMarginDepositStatus(long marginDepositStatus) {
		MarginDepositStatus = marginDepositStatus;
	}

	/**
	 * ���ڽ�ֹ����
	 * @return ���� marginEndDate��
	 */
	public Timestamp getMarginEndDate() {
		return MarginEndDate;
	}

	/**
	 * ���ڽ�ֹ����
	 * @param marginEndDate Ҫ���õ� marginEndDate��
	 */
	public void setMarginEndDate(Timestamp marginEndDate) {
		MarginEndDate = marginEndDate;
	}

	/**
	 * �������� - ��ʼ
	 * @return ���� marginEndDateFrom��
	 */
	public Timestamp getMarginEndDateFrom() {
		return MarginEndDateFrom;
	}

	/**
	 * �������� - ��ʼ
	 * @param marginEndDateFrom Ҫ���õ� marginEndDateFrom��
	 */
	public void setMarginEndDateFrom(Timestamp marginEndDateFrom) {
		MarginEndDateFrom = marginEndDateFrom;
	}

	/**
	 * �������� - ����
	 * @return ���� marginEndDateTo��
	 */
	public Timestamp getMarginEndDateTo() {
		return MarginEndDateTo;
	}

	/**
	 * �������� - ����
	 * @param marginEndDateTo Ҫ���õ� marginEndDateTo��
	 */
	public void setMarginEndDateTo(Timestamp marginEndDateTo) {
		MarginEndDateTo = marginEndDateTo;
	}

	/**
	 * ����
	 * @return ���� marginRate��
	 */
	public double getMarginRate() {
		return MarginRate;
	}

	/**
	 * ����
	 * @param marginRate Ҫ���õ� marginRate��
	 */
	public void setMarginRate(double marginRate) {
		MarginRate = marginRate;
	}

	/**
	 * ��ʼ���� - ��ʼ
	 * @return ���� marginStartDateFrom��
	 */
	public Timestamp getMarginStartDateFrom() {
		return MarginStartDateFrom;
	}

	/**
	 * ��ʼ���� - ��ʼ
	 * @param marginStartDateFrom Ҫ���õ� marginStartDateFrom��
	 */
	public void setMarginStartDateFrom(Timestamp marginStartDateFrom) {
		MarginStartDateFrom = marginStartDateFrom;
	}

	/**
	 * ��ʼ���� - ����
	 * @return ���� marginStartDateTo��
	 */
	public Timestamp getMarginStartDateTo() {
		return MarginStartDateTo;
	}

	/**
	 * ��ʼ���� - ����
	 * @param marginStartDateTo Ҫ���õ� marginStartDateTo��
	 */
	public void setMarginStartDateTo(Timestamp marginStartDateTo) {
		MarginStartDateTo = marginStartDateTo;
	}

	//add by 2012-05-18 ���ָ���˻����
	public String getAppointAccountNo() {
		return appointAccountNo;
	}

	public void setAppointAccountNo(String appointAccountNo) {
		this.appointAccountNo = appointAccountNo;
	}

	public long getUnit() {
		return unit;
	}

	public void setUnit(long unit) {
		this.unit = unit;
	}

	public String getControlSource() {
		return ControlSource;
	}

	public void setControlSource(String controlSource) {
		ControlSource = controlSource;
	}
	
}
