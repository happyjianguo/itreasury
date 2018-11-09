package com.iss.itreasury.clientmanage.client.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.clientmanage.dataentity.CimsBaseDataEntity;

public class ClientBusinessInfo extends CimsBaseDataEntity{
	
	private long officeID=-1;//�������´�id
	private String code="";//�ͻ���� 
	private String name="";//���� 
	private long clientid=-1;//�ͻ�id
	private String account="";//����˾�����
    private String bank1="";//��Ҫ��������1
    private String bank2="";//��Ҫ��������2
    private String bank3="";//��Ҫ��������3
    private String extendAccount1="";//���������˻���1
    private String extendAccount2="";//���������˻���2
    private String extendAccount3="";//���������˻���3
    private String loanCardNo="";//�����
    private String loanCardPwd="";//�������
    private String riskLevel="";//���ռ���
	private String talentLevel="";//���ʵȼ�
    private String creditLevelID="";//���õȼ�
    private String judgeLevelClient="";//���õȼ���������
	private Timestamp creditLevelDate;//���õȼ���������  
	private String insideCreditLevel="";//�ڲ����õȼ�  
	private double assessMark=0.00;//���õȼ��������� 
	

	
	public long getOfficeID() {
		return officeID;
	}
	
	public void setOfficeID(long officeID) {
		putUsedField("officeID", officeID);
		this.officeID = officeID;
	}
    
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		putUsedField("code", code);
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		putUsedField("name", name);
		this.name = name;
	}

	public long getClientid() {
		return clientid;
	}
	
	public void setClientid(long clientid) {
		putUsedField("clientid", clientid);
		this.clientid = clientid;
	}
	
	public String getAccount() {
		return account;
	}
	
	public void setAccount(String account) {
		putUsedField("account", account);
		this.account = account;
	}
	
	public String getBank1() {
		return bank1;
	}
	
	public void setBank1(String bank1) {
		putUsedField("bank1", bank1);
		this.bank1 = bank1;
	}

	public String getBank2() {
		return bank2;
	}
	
	public void setBank2(String bank2) {
		putUsedField("bank2", bank2);
		this.bank2 = bank2;
	}
	
	public String getBank3() {
		return bank3;
	}
	
	public void setBank3(String bank3) {
		putUsedField("bank3", bank3);
		this.bank3 = bank3;
	}
	
	public String getExtendAccount1() {
		return extendAccount1;
	}
	
	public void setExtendAccount1(String extendAccount1) {
		putUsedField("extendAccount1", extendAccount1);
		this.extendAccount1 = extendAccount1;
	}
	
	public String getExtendAccount2() {
		return extendAccount2;
	}

	public void setExtendAccount2(String extendAccount2) {
		putUsedField("extendAccount2", extendAccount2);
		this.extendAccount2 = extendAccount2;
	}
	
	public String getExtendAccount3() {
		return extendAccount3;
	}
	
	public void setExtendAccount3(String extendAccount3) {
		putUsedField("extendAccount3", extendAccount3);
		this.extendAccount3 = extendAccount3;
	}
	
	public String getLoanCardNo() {
		return loanCardNo;
	}
	
	public void setLoanCardNo(String loanCardNo) {
		putUsedField("loanCardNo", loanCardNo);
		this.loanCardNo = loanCardNo;
	}

	public String getLoanCardPwd() {
		return loanCardPwd;
	}
	
	public void setLoanCardPwd(String loanCardPwd) {
		putUsedField("loanCardPwd", loanCardPwd);
		this.loanCardPwd = loanCardPwd;
	}
	
	public String getRiskLevel() {
		return riskLevel;
	}
	
	public void setRiskLevel(String riskLevel) {
		putUsedField("riskLevel", riskLevel);
		this.riskLevel = riskLevel;
	}
	
	public String getTalentLevel() {
		return talentLevel;
	}
	
	public void setTalentLevel(String talentLevel) {
		putUsedField("talentLevel", talentLevel);
		this.talentLevel = talentLevel;
	}
	
	public String getCreditLevelID() {
		return creditLevelID;
	}

	public void setCreditLevelID(String creditLevelID) {
		putUsedField("creditLevelID", creditLevelID);
		this.creditLevelID = creditLevelID;
	}
	
	public String getJudgeLevelClient() {
		return judgeLevelClient;
	}
	
	public void setJudgeLevelClient(String judgeLevelClient) {
		putUsedField("judgeLevelClient", judgeLevelClient);
		this.judgeLevelClient = judgeLevelClient;
	}
	
	public Timestamp getCreditLevelDate() {
		return creditLevelDate;
	}
	
	public void setCreditLevelDate(Timestamp creditLevelDate) {
		putUsedField("creditLevelDate", creditLevelDate);
		this.creditLevelDate = creditLevelDate;
	}
	
	public String getInsideCreditLevel() {
		return insideCreditLevel;
	}
	
	public void setInsideCreditLevel(String insideCreditLevel) {
		putUsedField("insideCreditLevel", insideCreditLevel);
		this.insideCreditLevel = insideCreditLevel;
	}
	
	public double getAssessMark() {
		return assessMark;
	}

	public void setAssessMark(double assessMark) {
		putUsedField("assessMark", assessMark);
		this.assessMark = assessMark;
	}
}
