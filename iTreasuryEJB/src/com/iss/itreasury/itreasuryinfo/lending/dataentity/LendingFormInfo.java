package com.iss.itreasury.itreasuryinfo.lending.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;
/**
 * ���ڴ�Ų��뵥��Ϣ���������ݿ������ֶεĴ�ȡ����
 * @author gmqiu
 *
 */
public class LendingFormInfo extends ITreasuryBaseDataEntity {

	private static final long serialVersionUID = 8087603860836118678L;
	
	private long id = -1;                       /** ID������ */
	private long nOfficeId = -1;	            /** ���´����ID */
	private long nCurrencyId = -1;	            /** ����ID */
	private String sCode = "";	                /** ���뵥��� */
	private Timestamp dtStartDate = null;		/** �������ڣ���ʼ���ڣ�*/
	private Timestamp dtEndDate = null;			/** �������ڣ������գ�*/
	private Timestamp dtPayDate = null;			/** ʵ�ʻ����� */
	private double mAmount = 0.0;		        /** ������ */
	private double mPayRate = 0.0;		        /** �������ʣ������ʣ� */
	private double mRate = 0.0;		            /** ������ʣ������ʣ� */
	private String sCounterParty = "";		    /** ���ַ����� */
	private String sRecBankAccountNo = "";	    /** �տ��˻��ţ������ʽ��أ� */
	private String sRecBankAccountName = "";	/** �տ��˻����������ʽ��أ� */
	private long nStatusId = -1;	            /** ����״̬(0--��ɾ��,1--���뱣��,3--�Ѳ���,4--�����,5--�ѻ���) */
	private long nInputUserId = -1;	            /** ����¼����ID */
	private Timestamp dtInputDate = null;		/** ����¼������ */
	private long nCheckUserId = -1;			    /** ���븴����ID */
	private Timestamp dtCheckDate = null;		/** ���븴������ */
	private long nPayInputUserId = -1;	        /** ����¼����ID */
	private Timestamp dtPayInputDate = null;	/** ����¼������ */
	private long nPayCheckUserId = -1;	        /** �������ID */
	private Timestamp dtPayCheckDate = null;	/** ��������� */
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}
	public long getnOfficeId() {
		return nOfficeId;
	}
	public void setnOfficeId(long nOfficeId) {
		this.nOfficeId = nOfficeId;
		putUsedField("nOfficeId", nOfficeId);
	}
	public long getnCurrencyId() {
		return nCurrencyId;
	}
	public void setnCurrencyId(long nCurrencyId) {
		this.nCurrencyId = nCurrencyId;
		putUsedField("nCurrencyId", nCurrencyId);
	}
	public String getsCode() {
		return sCode;
	}
	public void setsCode(String sCode) {
		this.sCode = sCode;
		putUsedField("sCode", sCode);
	}
	public Timestamp getDtStartDate() {
		return dtStartDate;
	}
	public void setDtStartDate(Timestamp dtStartDate) {
		this.dtStartDate = dtStartDate;
		putUsedField("dtStartDate", dtStartDate);
	}
	public Timestamp getDtEndDate() {
		return dtEndDate;
	}
	public void setDtEndDate(Timestamp dtEndDate) {
		this.dtEndDate = dtEndDate;
		putUsedField("dtEndDate", dtEndDate);
	}
	public Timestamp getDtPayDate() {
		return dtPayDate;
	}
	public void setDtPayDate(Timestamp dtPayDate) {
		this.dtPayDate = dtPayDate;
		putUsedField("dtPayDate", dtPayDate);
	}
	public double getmAmount() {
		return mAmount;
	}
	public void setmAmount(double mAmount) {
		this.mAmount = mAmount;
		putUsedField("mAmount", mAmount);
	}
	public double getmPayRate() {
		return mPayRate;
	}
	public void setmPayRate(double mPayRate) {
		this.mPayRate = mPayRate;
		putUsedField("mPayRate", mPayRate);
	}
	public double getmRate() {
		return mRate;
	}
	public void setmRate(double mRate) {
		this.mRate = mRate;
		putUsedField("mRate", mRate);
	}
	public String getsCounterParty() {
		return sCounterParty;
	}
	public void setsCounterParty(String sCounterParty) {
		this.sCounterParty = sCounterParty;
		putUsedField("sCounterParty", sCounterParty);
	}
	public String getsRecBankAccountNo() {
		return sRecBankAccountNo;
	}
	public void setsRecBankAccountNo(String sRecBankAccountNo) {
		this.sRecBankAccountNo = sRecBankAccountNo;
		putUsedField("sRecBankAccountNo", sRecBankAccountNo);
	}
	public String getsRecBankAccountName() {
		return sRecBankAccountName;
	}
	public void setsRecBankAccountName(String sRecBankAccountName) {
		this.sRecBankAccountName = sRecBankAccountName;
		putUsedField("sRecBankAccountName", sRecBankAccountName);
	}
	public long getnStatusId() {
		return nStatusId;
	}
	public void setnStatusId(long nStatusId) {
		this.nStatusId = nStatusId;
		putUsedField("nStatusId", nStatusId);
	}
	public long getnInputUserId() {
		return nInputUserId;
	}
	public void setnInputUserId(long nInputUserId) {
		this.nInputUserId = nInputUserId;
		putUsedField("nInputUserId", nInputUserId);
	}
	public Timestamp getDtInputDate() {
		return dtInputDate;
	}
	public void setDtInputDate(Timestamp dtInputDate) {
		this.dtInputDate = dtInputDate;
		putUsedField("dtInputDate", dtInputDate);
	}
	public long getnCheckUserId() {
		return nCheckUserId;
	}
	public void setnCheckUserId(long nCheckUserId) {
		this.nCheckUserId = nCheckUserId;
		putUsedField("nCheckUserId", nCheckUserId);
	}
	public Timestamp getDtCheckDate() {
		return dtCheckDate;
	}
	public void setDtCheckDate(Timestamp dtCheckDate) {
		this.dtCheckDate = dtCheckDate;
		putUsedField("dtCheckDate", dtCheckDate);
	}
	public long getnPayInputUserId() {
		return nPayInputUserId;
	}
	public void setnPayInputUserId(long nPayInputUserId) {
		this.nPayInputUserId = nPayInputUserId;
		putUsedField("nPayInputUserId", nPayInputUserId);
	}
	public Timestamp getDtPayInputDate() {
		return dtPayInputDate;
	}
	public void setDtPayInputDate(Timestamp dtPayInputDate) {
		this.dtPayInputDate = dtPayInputDate;
		putUsedField("dtPayInputDate", dtPayInputDate);
	}
	public long getnPayCheckUserId() {
		return nPayCheckUserId;
	}
	public void setnPayCheckUserId(long nPayCheckUserId) {
		this.nPayCheckUserId = nPayCheckUserId;
		putUsedField("nPayCheckUserId", nPayCheckUserId);
	}
	public Timestamp getDtPayCheckDate() {
		return dtPayCheckDate;
	}
	public void setDtPayCheckDate(Timestamp dtPayCheckDate) {
		this.dtPayCheckDate = dtPayCheckDate;
		putUsedField("dtPayCheckDate", dtPayCheckDate);
	}

	private String nInputUserName;          /** ����¼���� */
	private String nCheckUserName;          /** ���븴���� */
	private String nPayInputUserName;       /** ����¼���� */
	private String nPayCheckUserName;       /** ������� */

	public String getnInputUserName() {
		return nInputUserName;
	}
	public void setnInputUserName(String nInputUserName) {
		this.nInputUserName = nInputUserName;
	}
	public String getnCheckUserName() {
		return nCheckUserName;
	}
	public void setnCheckUserName(String nCheckUserName) {
		this.nCheckUserName = nCheckUserName;
	}
	public String getnPayInputUserName() {
		return nPayInputUserName;
	}
	public void setnPayInputUserName(String nPayInputUserName) {
		this.nPayInputUserName = nPayInputUserName;
	}
	public String getnPayCheckUserName() {
		return nPayCheckUserName;
	}
	public void setnPayCheckUserName(String nPayCheckUserName) {
		this.nPayCheckUserName = nPayCheckUserName;
	}
	
}
