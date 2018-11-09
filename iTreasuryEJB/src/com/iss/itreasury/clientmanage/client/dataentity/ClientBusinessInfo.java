package com.iss.itreasury.clientmanage.client.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.clientmanage.dataentity.CimsBaseDataEntity;

public class ClientBusinessInfo extends CimsBaseDataEntity{
	
	private long officeID=-1;//所属办事处id
	private String code="";//客户编号 
	private String name="";//姓名 
	private long clientid=-1;//客户id
	private String account="";//财务公司账务号
    private String bank1="";//主要开户银行1
    private String bank2="";//主要开户银行2
    private String bank3="";//主要开户银行3
    private String extendAccount1="";//开户银行账户号1
    private String extendAccount2="";//开户银行账户号2
    private String extendAccount3="";//开户银行账户号3
    private String loanCardNo="";//贷款卡号
    private String loanCardPwd="";//贷款卡密码
    private String riskLevel="";//风险级别
	private String talentLevel="";//资质等级
    private String creditLevelID="";//信用等级
    private String judgeLevelClient="";//信用等级评定机构
	private Timestamp creditLevelDate;//信用等级评定日期  
	private String insideCreditLevel="";//内部信用等级  
	private double assessMark=0.00;//信用等级评级分数 
	

	
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
