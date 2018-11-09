package com.iss.itreasury.loan.financingoperation.tradeacceptance.inform.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.loan.financingoperation.tradeacceptance.protocol.dataentity.LoanProtocolInfo;

public class LoanInformInfo implements Serializable{
	
	 private long id = -1;//ID	NUMBER primary key,
	 private long ncontractId = -1;//nContractID Number,--Ʊ�ݳж�Э��ID
	 private long nnoteTypeId = -1;//nNoteTypeID Number,--ҵ��֪ͨ������(1��ȡ������ 2���ڳж� 3�渶��Ϣ�ջ�)
	 private String scode = "";//sCode Varchar2(10),--ҵ��֪ͨ�����(����001)
	 private Timestamp dtExecuteDate = null;//dtExecuteDate Date,--ִ������
	 private Timestamp dtOutdate = null;//dtOutdate Date,--��Ʊ����
	 private Timestamp dtTodate = null;//dtTodate Date,--������
	 private double namount = -1;//nAmount Number(21,6),--���׽��(1 ������ 2 �жҽ�� 3 ��Ϣ�ܶ�)
	 private long npayAccountID = -1;//nPayAccountID number,--�����ڲ��˻�ID
	 private String spayBankAccountNO = "";//sPayBankAccountNO Varchar2(30),--���������˺�
	 private String spayBankAccountName = "";//sPayBankAccountName Varchar2(100),--���������˻�����
	 private String spayBankName = "";//sPayBankName Varchar2(100),--�����˻�������
	 private long nrecAccountID = -1;//nRecAccountID number,--�տ��ڲ��˻�ID
	 private String srecBankAccountNO = "";//sRecBankAccountNO Varchar2(30),--�տ������˺�
	 private String srecBankAccountName = "";//sRecBankAccountName Varchar2(100),--�տ������˻�����
	 private String srecBankName = "";//sRecBankName Varchar2(100),--�տ���������
	 private String srecBankProvince = "";//sRecBankProvince Varchar2(50),--�տ��������ʡ
	 private String srecBankCity = "";//sRecBankCity Varchar2(50),--�տ����������
	 private double ncapitalAmount = -1;// Number(21,6),--���� //2008-07-23
	 private long noverdueDay = -1;// Number,--��������
	 private double noverDueRate = -1;// Number(15,12),--�������� //2008-07-23
	 private double ninterestAmount = -1;// Number(21,6),--������ȡ��Ϣ //2008-07-23
	 private long nextcheckuserid = -1;// NUMBER,--��һ�������
	 private long nextchecklevel = -1;// NUMBER,--��һ����������
	 private long islowlevel = -1;// NUMBER,--��������
	 private long officeid = -1;//NUMBER,--���´�
	 private long currencyid =-1;// NUMBER,--����
	 private long status = -1;// NUMBER,--״̬
	 private long inputuserid = -1;// NUMBER,--¼����
	 private Timestamp inputdate = null;// DATE--¼��ʱ��
	 
	 private LoanProtocolInfo loanProtocolInfo = null;
	 
	 //��ʾ��
	 private double poundage = 0.0;//�ж�������	Poundage	Number(21,6)
	 private double npoundage = 0.0;//δ��������	Npoundage	Number(21,6)
	 
	 private double noverdueratesum =0.0;//��Ϣ��
	 private double ninterestamountsum = 0.0;//���ջ���Ϣ
	 private double nninterestamountsum = 0.0;//δ�ջ���Ϣ
	 
//	 add dwj 20080726 
	 private String fsaccountno = "";//�ڲ��ʻ����(���)
	 private String fsaccountnoname = "";//�ڲ��ʻ�����(���)
//	 end dwj 20080726
	 
	 
//	 add dwj 20080726 //������ʹ��
    private String heckOpinion="";//��������
    private long userID=-1;//��ǰ��½���û�
//	 end dwj 20080726
	 
    //add dwj 20081029
    private double nncapitalamount = 0.0;//δ�ձ���
    //end add dwj 20081029
    
//	 add dwj 20081114
	 private String fsaccountno1 = "";//�ڲ��ʻ����(�տ�)
	 private String fsaccountnoname1 = "";//�ڲ��ʻ�����(�տ�)
//	 end dwj 20081114
	 
	 //add dwj 20081114
	 private long noverdueid = -1;
	 //end add dwj 20081114
    
	public long getCurrencyid() {
		return currencyid;
	}
	public void setCurrencyid(long currencyid) {
		this.currencyid = currencyid;
	}
	public Timestamp getDtExecuteDate() {
		return dtExecuteDate;
	}
	public void setDtExecuteDate(Timestamp dtExecuteDate) {
		this.dtExecuteDate = dtExecuteDate;
	}
	public Timestamp getDtOutdate() {
		return dtOutdate;
	}
	public void setDtOutdate(Timestamp dtOutdate) {
		this.dtOutdate = dtOutdate;
	}
	public Timestamp getDtTodate() {
		return dtTodate;
	}
	public void setDtTodate(Timestamp dtTodate) {
		this.dtTodate = dtTodate;	
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Timestamp getInputdate() {
		return inputdate;
	}
	public void setInputdate(Timestamp inputdate) {
		this.inputdate = inputdate;
	}
	public long getInputuserid() {
		return inputuserid;
	}
	public void setInputuserid(long inputuserid) {
		this.inputuserid = inputuserid;
	}
	public long getIslowlevel() {
		return islowlevel;
	}
	public void setIslowlevel(long islowlevel) {
		this.islowlevel = islowlevel;
	}
	public double getNamount() {
		return namount;
	}
	public void setNamount(double namount) {
		this.namount = namount;
	}
	public double getNcapitalAmount() {
		return ncapitalAmount;
	}
	public void setNcapitalAmount(double ncapitalAmount) {
		this.ncapitalAmount = ncapitalAmount;
	}
	public long getNcontractId() {
		return ncontractId;
	}
	public void setNcontractId(long ncontractId) {
		this.ncontractId = ncontractId;
	}
	public long getNextchecklevel() {
		return nextchecklevel;
	}
	public void setNextchecklevel(long nextchecklevel) {
		this.nextchecklevel = nextchecklevel;
	}
	public long getNextcheckuserid() {
		return nextcheckuserid;
	}
	public void setNextcheckuserid(long nextcheckuserid) {
		this.nextcheckuserid = nextcheckuserid;
	}
	public double getNinterestAmount() {
		return ninterestAmount;
	}
	public void setNinterestAmount(double ninterestAmount) {
		this.ninterestAmount = ninterestAmount;
	}
	public long getNnoteTypeId() {
		return nnoteTypeId;
	}
	public void setNnoteTypeId(long nnoteTypeId) {
		this.nnoteTypeId = nnoteTypeId;
	}
	public long getNoverdueDay() {
		return noverdueDay;
	}
	public void setNoverdueDay(long noverdueDay) {
		this.noverdueDay = noverdueDay;
	}
	public double getNoverDueRate() {
		return noverDueRate;
	}
	public void setNoverDueRate(double noverDueRate) {
		this.noverDueRate = noverDueRate;
	}
	public long getNpayAccountID() {
		return npayAccountID;
	}
	public void setNpayAccountID(long npayAccountID) {
		this.npayAccountID = npayAccountID;
	}
	public long getNrecAccountID() {
		return nrecAccountID;
	}
	public void setNrecAccountID(long nrecAccountID) {
		this.nrecAccountID = nrecAccountID;
	}
	public long getOfficeid() {
		return officeid;
	}
	public void setOfficeid(long officeid) {
		this.officeid = officeid;
	}
	public String getScode() {
		return scode;
	}
	public void setScode(String scode) {
		this.scode = scode;
	}
	public String getSpayBankAccountName() {
		return spayBankAccountName;
	}
	public void setSpayBankAccountName(String spayBankAccountName) {
		this.spayBankAccountName = spayBankAccountName;
	}
	public String getSpayBankAccountNO() {
		return spayBankAccountNO;
	}
	public void setSpayBankAccountNO(String spayBankAccountNO) {
		this.spayBankAccountNO = spayBankAccountNO;
	}
	public String getSpayBankName() {
		return spayBankName;
	}
	public void setSpayBankName(String spayBankName) {
		this.spayBankName = spayBankName;
	}
	public String getSrecBankAccountName() {
		return srecBankAccountName;
	}
	public void setSrecBankAccountName(String srecBankAccountName) {
		this.srecBankAccountName = srecBankAccountName;
	}
	public String getSrecBankAccountNO() {
		return srecBankAccountNO;
	}
	public void setSrecBankAccountNO(String srecBankAccountNO) {
		this.srecBankAccountNO = srecBankAccountNO;
	}
	public String getSrecBankCity() {
		return srecBankCity;
	}
	public void setSrecBankCity(String srecBankCity) {
		this.srecBankCity = srecBankCity;
	}
	public String getSrecBankName() {
		return srecBankName;
	}
	public void setSrecBankName(String srecBankName) {
		this.srecBankName = srecBankName;
	}
	public String getSrecBankProvince() {
		return srecBankProvince;
	}
	public void setSrecBankProvince(String srecBankProvince) {
		this.srecBankProvince = srecBankProvince;
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public LoanProtocolInfo getLoanProtocolInfo() {
		return loanProtocolInfo;
	}
	public void setLoanProtocolInfo(LoanProtocolInfo loanProtocolInfo) {
		this.loanProtocolInfo = loanProtocolInfo;
	}
	public double getNpoundage() {
		return npoundage;
	}
	public void setNpoundage(double npoundage) {
		this.npoundage = npoundage;
	}
	public double getPoundage() {
		return poundage;
	}
	public void setPoundage(double poundage) {
		this.poundage = poundage;
	}
	public String getFsaccountno() {
		return fsaccountno;
	}
	public void setFsaccountno(String fsaccountno) {
		this.fsaccountno = fsaccountno;
	}
	public String getFsaccountnoname() {
		return fsaccountnoname;
	}
	public void setFsaccountnoname(String fsaccountnoname) {
		this.fsaccountnoname = fsaccountnoname;
	}
	public String getHeckOpinion() {
		return heckOpinion;
	}
	public void setHeckOpinion(String heckOpinion) {
		this.heckOpinion = heckOpinion;
	}
	public long getUserID() {
		return userID;
	}
	public void setUserID(long userID) {
		this.userID = userID;
	}
	public double getNinterestamountsum() {
		return ninterestamountsum;
	}
	public void setNinterestamountsum(double ninterestamountsum) {
		this.ninterestamountsum = ninterestamountsum;
	}
	public double getNninterestamountsum() {
		return nninterestamountsum;
	}
	public void setNninterestamountsum(double nninterestamountsum) {
		this.nninterestamountsum = nninterestamountsum;
	}
	public double getNoverdueratesum() {
		return noverdueratesum;
	}
	public void setNoverdueratesum(double noverdueratesum) {
		this.noverdueratesum = noverdueratesum;
	}
	public double getNncapitalamount() {
		return nncapitalamount;
	}
	public void setNncapitalamount(double nncapitalamount) {
		this.nncapitalamount = nncapitalamount;
	}
	public String getFsaccountno1() {
		return fsaccountno1;
	}
	public void setFsaccountno1(String fsaccountno1) {
		this.fsaccountno1 = fsaccountno1;
	}
	public String getFsaccountnoname1() {
		return fsaccountnoname1;
	}
	public void setFsaccountnoname1(String fsaccountnoname1) {
		this.fsaccountnoname1 = fsaccountnoname1;
	}
	public long getNoverdueid() {
		return noverdueid;
	}
	public void setNoverdueid(long noverdueid) {
		this.noverdueid = noverdueid;
	}

}
