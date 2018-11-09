package com.iss.itreasury.settlement.craftbrother.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.settlement.account.dataentity.ExternalAccountInfo;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class TransCraftbrotherInfo extends ITreasuryBaseDataEntity implements
		Serializable {
	//��������------------------------------------------------------------------------------------------------
	public  static final  long QUERYPURPOSE_MODIFY_LINKSEARCH = 1;
	public  static final  long QUERYPURPOSE_CHECK_LINKSEARCH  = 2;
	//���ݿ�ӳ���ֶ���Ϣ---------------------------------------------------------------------------------------
	private long id = -1;// PK
	private long nofficeId = -1;// ���´�
	private long ncurrencyId = -1;// ����
	private String stransNo = "";// ���׺�
	private long ntransactionTypeId = -1;// ���㽻�����͡�ת���ַ��ţ�ת�����ջأ�ת���ֹ��أ��ʲ�ת�÷��ţ��ʲ�ת���ջأ��ʽ��裬��践��ʲ�ת�÷��ţ��ʲ�ת���ջء�
	private long ncraBusinessTypeId = -1;// ͬҵҵ�����͡�ת���֡��ʲ�ת�á��ʽ��衿
	private long nsubTransactionTypeId = -1;// ͬҵ�������͡�������ϣ����빺�أ�������ϣ��������أ����룬�����
	private long ncounterpartId = -1;// ͬҵ���׶���ID
	private String ncounterpartName = "";// ͬҵ���׶�������
	private long nnoticeId = -1;// ͬҵ֪ͨ��ID
	private double mamount = 0.00;// ���
	private double minterest = 0.00;// ��Ϣ
	private double mrealamount = 0.00;// ʵ�ʽ��
	private double minterestRate = 0.00;// ����
	private long nbankId = -1;// ����˾�ո�������
	private Timestamp dtInterestStart = null;// ��Ϣ��
	private Timestamp dtRealInterest = null;//ʵ����/��������
	private Timestamp dtExecute = null;// ִ����
	private String sabstract = "";// ժҪ
	private String scheckAbstract = "";// ȡ������ժҪ
	private long nstatusId = -1;// ����״̬
	private Timestamp dtModify = null;// �޸�ʱ�䣺������ʱ����
	private Timestamp dtInput = null;// ¼������
	private long ninputUserId = -1;// ¼����
	private long ncheckUserId = -1;// ������
    //�����ֶ���Ϣ---------------------------------------------------------------------------------------------
	private long ncontractId = -1;
	private String scontractCode = null;
	private String snoticeCode = null;
	
	//start �����տ����  add by wangzhen  20120207  
	
	//�ǲ���˾�˻���
	private String sextAccountNo = "";
	//�ǲ���˾�ͻ�����
	private String sextClientName = "";
	//�ǲ���˾�����(ʡ)��
	private String sremitInProvince = "";
	//�ǲ���˾�����(��)��
	private String sremitInCity = "";
	//�ǲ���˾������������
	private String sremitInBank = "";
	//���к�
	private String spayeebankexchangeno="";
	//CNAPS��
	private String spayeebankcnapsno ="";
	//������
	private String spayeebankorgno ="";
	//end
	
	
	public long getNcontractId() {
		return ncontractId;
	}

	public void setNcontractId(long ncontractId) {
		this.ncontractId = ncontractId;
	}

	public String getScontractCode() {
		return scontractCode;
	}

	public void setScontractCode(String scontractCode) {
		this.scontractCode = scontractCode;
	}

	public String getSnoticeCode() {
		return snoticeCode;
	}

	public void setSnoticeCode(String snoticeCode) {
		this.snoticeCode = snoticeCode;
	}
	//�����ֶ���Ϣ---------------------------------------------------------------------------------------------
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}

	public long getNofficeId() {
		return nofficeId;
	}

	public void setNofficeId(long nofficeId) {
		this.nofficeId = nofficeId;
		putUsedField("nofficeId", nofficeId);
	}

	public long getNcurrencyId() {
		return ncurrencyId;
	}

	public void setNcurrencyId(long ncurrencyId) {
		this.ncurrencyId = ncurrencyId;
		putUsedField("ncurrencyId", ncurrencyId);
	}

	public String getStransNo() {
		return stransNo;
	}

	public void setStransNo(String stransNo) {
		this.stransNo = stransNo;
		putUsedField("stransNo", stransNo);
	}

	public long getNtransactionTypeId() {
		return ntransactionTypeId;
	}

	public void setNtransactionTypeId(long ntransactionTypeId) {
		this.ntransactionTypeId = ntransactionTypeId;
		putUsedField("ntransactionTypeId", ntransactionTypeId);
	}

	public long getNcraBusinessTypeId() {
		return ncraBusinessTypeId;
	}

	public void setNcraBusinessTypeId(long ncraBusinessTypeId) {
		this.ncraBusinessTypeId = ncraBusinessTypeId;
		putUsedField("ncraBusinessTypeId", ncraBusinessTypeId);
	}

	public long getNsubTransactionTypeId() {
		return nsubTransactionTypeId;
	}

	public void setNsubTransactionTypeId(long nsubTransactionTypeId) {
		this.nsubTransactionTypeId = nsubTransactionTypeId;
		putUsedField("nsubTransactionTypeId", nsubTransactionTypeId);
	}

	public long getNcounterpartId() {
		return ncounterpartId;
	}

	public void setNcounterpartId(long ncounterpartId) {
		this.ncounterpartId = ncounterpartId;
		putUsedField("ncounterpartId", ncounterpartId);
	}

	public String getNcounterpartName() {
		return ncounterpartName;
	}

	public void setNcounterpartName(String ncounterpartName) {
		this.ncounterpartName = ncounterpartName;
		putUsedField("ncounterpartName", ncounterpartName);
	}

	public long getNnoticeId() {
		return nnoticeId;
	}

	public void setNnoticeId(long nnoticeId) {
		this.nnoticeId = nnoticeId;
		putUsedField("nnoticeId", nnoticeId);
	}

	public double getMamount() {
		return mamount;
	}

	public void setMamount(double mamount) {
		this.mamount = mamount;
		putUsedField("mamount", mamount);
	}

	public double getMinterest() {
		return minterest;
	}

	public void setMinterest(double minterest) {
		this.minterest = minterest;
		putUsedField("minterest", minterest);
	}

	public double getMrealamount() {
		return mrealamount;
	}

	public void setMrealamount(double mrealamount) {
		this.mrealamount = mrealamount;
		putUsedField("mrealamount", mrealamount);
	}

	public double getMinterestRate() {
		return minterestRate;
	}

	public void setMinterestRate(double minterestRate) {
		this.minterestRate = minterestRate;
		putUsedField("minterestRate", minterestRate);
	}

	public long getNbankId() {
		return nbankId;
	}

	public void setNbankId(long nbankId) {
		this.nbankId = nbankId;
		putUsedField("nbankId", nbankId);
	}

	public Timestamp getDtInterestStart() {
		return dtInterestStart;
	}

	public void setDtInterestStart(Timestamp dtInterestStart) {
		this.dtInterestStart = dtInterestStart;
		putUsedField("dtInterestStart", dtInterestStart);
	}

	public Timestamp getDtRealInterest() {
		return dtRealInterest;
	}

	public void setDtRealInterest(Timestamp dtRealInterest) {
		this.dtRealInterest = dtRealInterest;
		putUsedField("dtRealInterest", dtRealInterest);
	}

	public Timestamp getDtExecute() {
		return dtExecute;
	}

	public void setDtExecute(Timestamp dtExecute) {
		this.dtExecute = dtExecute;
		putUsedField("dtExecute", dtExecute);
	}

	public String getSabstract() {
		return sabstract;
	}

	public void setSabstract(String sabstract) {
		this.sabstract = sabstract;
		putUsedField("sabstract", sabstract);
	}

	public String getScheckAbstract() {
		return scheckAbstract;
	}

	public void setScheckAbstract(String scheckAbstract) {
		this.scheckAbstract = scheckAbstract;
		putUsedField("scheckAbstract", scheckAbstract);
	}

	public long getNstatusId() {
		return nstatusId;
	}

	public void setNstatusId(long nstatusId) {
		this.nstatusId = nstatusId;
		putUsedField("nstatusId", nstatusId);
	}

	public Timestamp getDtModify() {
		return dtModify;
	}

	public void setDtModify(Timestamp dtModify) {
		this.dtModify = dtModify;
		putUsedField("dtModify", dtModify);
	}

	public Timestamp getDtInput() {
		return dtInput;
	}

	public void setDtInput(Timestamp dtInput) {
		this.dtInput = dtInput;
		putUsedField("dtInput", dtInput);
	}

	public long getNinputUserId() {
		return ninputUserId;
	}

	public void setNinputUserId(long ninputUserId) {
		this.ninputUserId = ninputUserId;
		putUsedField("ninputUserId", ninputUserId);
	}

	public long getNcheckUserId() {
		return ncheckUserId;
	}

	public void setNcheckUserId(long ncheckUserId) {
		this.ncheckUserId = ncheckUserId;
		putUsedField("ncheckUserId", ncheckUserId);
	}

	
	

	public String getSpayeebankexchangeno() {
		return spayeebankexchangeno;
	}

	public void setSpayeebankexchangeno(String spayeebankexchangeno) {
		this.spayeebankexchangeno = spayeebankexchangeno;
		putUsedField("spayeebankexchangeno",spayeebankexchangeno);
	}

	public String getSpayeebankcnapsno() {
		return spayeebankcnapsno;
	}

	public void setSpayeebankcnapsno(String spayeebankcnapsno) {
		this.spayeebankcnapsno = spayeebankcnapsno;
		putUsedField("spayeebankcnapsno",spayeebankcnapsno);
	}

	public String getSpayeebankorgno() {
		return spayeebankorgno;
	}

	public void setSpayeebankorgno(String spayeebankorgno) {
		this.spayeebankorgno = spayeebankorgno;
		putUsedField("spayeebankorgno",spayeebankorgno);
	}

	public String getSextAccountNo() {
		return sextAccountNo;
	}

	public void setSextAccountNo(String sextAccountNo) {
		this.sextAccountNo = sextAccountNo;
		putUsedField("sextAccountNo",sextAccountNo);
	}

	public String getSextClientName() {
		return sextClientName;
	}

	public void setSextClientName(String sextClientName) {
		this.sextClientName = sextClientName;
		putUsedField("sextClientName",sextClientName);
	}

	public String getSremitInProvince() {
		return sremitInProvince;
	}

	public void setSremitInProvince(String sremitInProvince) {
		this.sremitInProvince = sremitInProvince;
		putUsedField("sremitInProvince",sremitInProvince);
	}

	public String getSremitInCity() {
		return sremitInCity;
	}

	public void setSremitInCity(String sremitInCity) {
		this.sremitInCity = sremitInCity;
		putUsedField("sremitInCity",sremitInCity);
	}

	public String getSremitInBank() {
		return sremitInBank;
	}

	public void setSremitInBank(String sremitInBank) {
		this.sremitInBank = sremitInBank;
		putUsedField("sremitInBank",sremitInBank);
	}
	
	public ExternalAccountInfo getExternalAccountInfo() {
		ExternalAccountInfo extAccountInfo = new ExternalAccountInfo();
		//No id will be set because id will be gotten from external
		extAccountInfo.setOfficeID(nofficeId);
		extAccountInfo.setExtAcctNo(sextAccountNo);
		extAccountInfo.setExtAcctName(sextClientName);
		extAccountInfo.setBankName(sremitInBank);
		extAccountInfo.setProvince(sremitInProvince);
		extAccountInfo.setCity(sremitInCity);
		extAccountInfo.setNcurrencyID(ncurrencyId);
		extAccountInfo.setSpayeebankcnapsno(spayeebankcnapsno);
		extAccountInfo.setSpayeebankexchangeno(spayeebankexchangeno);
		extAccountInfo.setSpayeebankorgno(spayeebankorgno);
		return extAccountInfo;
	}

}
