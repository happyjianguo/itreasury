/*
 * Created on 2005-5-19
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

package com.iss.itreasury.settlement.comparisontrans.dataentity;

import java.util.Date;



/**
 * �����ֵ������Ϣ
 * 
 * @author ytcui To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Generation - Code and Comments
 */
public class BankInfo{
	private long id = -1;

	private String code = null;

	private String name = null;

	private String shortnames = null;

	private long currencyMappingGID = -1;// ����ӳ������id

	private String referenceCode = null; // �������

	private long rdstatus = -1;

	private Date inputtime = null;

	private long inputuserid = -1;

	private Date modifytime = null;

	private long modifyuserid = -1;
	
	private long upbankid = -1;//�ϼ�����id

	
	// ��������ֶ�
	private long grade = -1;// �ȼ�

	private String gradecode = "";// �ȼ�����
	
	private long upgrade = -1;// �ϼ����еȼ������ֶ�

	private String upgradecode = "";// �ϼ����еȼ����븨���ֶ�
	
	private long ifupdategrade=-1;//�жϿͻ��Ƿ��޸���������
	
	//��������ֶζ�
	private String uniteCode = ""; //���к�
	private String branchCode = ""; //���л�����
	private String branchSwiftCode = ""; //SWIFT����
	private String branchAreaSeg1 = ""; //�����е�ַ1
	private String branchAreaSeg2 = ""; //�����е�ַ2
	private String branchAreaSeg3 = ""; //�����е�ַ3
	private String name_en = "";	//Ӣ������
	
	private long   regionUTCTime = 0;	//UTCʱ��,Ĭ��Ϊ0:0

	/**
	 * @return the branchAreaSeg1
	 */
	public String getBranchAreaSeg1() {
		return branchAreaSeg1;
	}

	/**
	 * @param branchAreaSeg1 the branchAreaSeg1 to set
	 */
	public void setBranchAreaSeg1(String branchAreaSeg1) {
		this.branchAreaSeg1 = branchAreaSeg1;
	}

	/**
	 * @return the branchAreaSeg2
	 */
	public String getBranchAreaSeg2() {
		return branchAreaSeg2;
	}

	/**
	 * @param branchAreaSeg2 the branchAreaSeg2 to set
	 */
	public void setBranchAreaSeg2(String branchAreaSeg2) {
		this.branchAreaSeg2 = branchAreaSeg2;
	}

	/**
	 * @return the branchAreaSeg3
	 */
	public String getBranchAreaSeg3() {
		return branchAreaSeg3;
	}

	/**
	 * @param branchAreaSeg3 the branchAreaSeg3 to set
	 */
	public void setBranchAreaSeg3(String branchAreaSeg3) {
		this.branchAreaSeg3 = branchAreaSeg3;
	}

	/**
	 * @return the branchCode
	 */
	public String getBranchCode() {
		return branchCode;
	}

	/**
	 * @param branchCode the branchCode to set
	 */
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	/**
	 * @return the branchSwiftCode
	 */
	public String getBranchSwiftCode() {
		return branchSwiftCode;
	}

	/**
	 * @param branchSwiftCode the branchSwiftCode to set
	 */
	public void setBranchSwiftCode(String branchSwiftCode) {
		this.branchSwiftCode = branchSwiftCode;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the currencyMappingGID
	 */
	public long getCurrencyMappingGID() {
		return currencyMappingGID;
	}

	/**
	 * @param currencyMappingGID the currencyMappingGID to set
	 */
	public void setCurrencyMappingGID(long currencyMappingGID) {
		this.currencyMappingGID = currencyMappingGID;
	}

	/**
	 * @return the grade
	 */
	public long getGrade() {
		return grade;
	}

	/**
	 * @param grade the grade to set
	 */
	public void setGrade(long grade) {
		this.grade = grade;
	}

	/**
	 * @return the gradecode
	 */
	public String getGradecode() {
		return gradecode;
	}

	/**
	 * @param gradecode the gradecode to set
	 */
	public void setGradecode(String gradecode) {
		this.gradecode = gradecode;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the ifupdategrade
	 */
	public long getIfupdategrade() {
		return ifupdategrade;
	}

	/**
	 * @param ifupdategrade the ifupdategrade to set
	 */
	public void setIfupdategrade(long ifupdategrade) {
		this.ifupdategrade = ifupdategrade;
	}

	/**
	 * @return the inputtime
	 */
	public Date getInputtime() {
		return inputtime;
	}

	/**
	 * @param inputtime the inputtime to set
	 */
	public void setInputtime(Date inputtime) {
		this.inputtime = inputtime;
	}

	/**
	 * @return the inputuserid
	 */
	public long getInputuserid() {
		return inputuserid;
	}

	/**
	 * @param inputuserid the inputuserid to set
	 */
	public void setInputuserid(long inputuserid) {
		this.inputuserid = inputuserid;
	}

	/**
	 * @return the modifytime
	 */
	public Date getModifytime() {
		return modifytime;
	}

	/**
	 * @param modifytime the modifytime to set
	 */
	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	/**
	 * @return the modifyuserid
	 */
	public long getModifyuserid() {
		return modifyuserid;
	}

	/**
	 * @param modifyuserid the modifyuserid to set
	 */
	public void setModifyuserid(long modifyuserid) {
		this.modifyuserid = modifyuserid;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name_en
	 */
	public String getName_en() {
		return name_en;
	}

	/**
	 * @param name_en the name_en to set
	 */
	public void setName_en(String name_en) {
		this.name_en = name_en;
	}

	/**
	 * @return the rdstatus
	 */
	public long getRdstatus() {
		return rdstatus;
	}

	/**
	 * @param rdstatus the rdstatus to set
	 */
	public void setRdstatus(long rdstatus) {
		this.rdstatus = rdstatus;
	}

	/**
	 * @return the referenceCode
	 */
	public String getReferenceCode() {
		return referenceCode;
	}

	/**
	 * @param referenceCode the referenceCode to set
	 */
	public void setReferenceCode(String referenceCode) {
		this.referenceCode = referenceCode;
	}

	/**
	 * @return the regionUTCTime
	 */
	public long getRegionUTCTime() {
		return regionUTCTime;
	}

	/**
	 * @param regionUTCTime the regionUTCTime to set
	 */
	public void setRegionUTCTime(long regionUTCTime) {
		this.regionUTCTime = regionUTCTime;
	}

	/**
	 * @return the shortnames
	 */
	public String getShortnames() {
		return shortnames;
	}

	/**
	 * @param shortnames the shortnames to set
	 */
	public void setShortnames(String shortnames) {
		this.shortnames = shortnames;
	}

	/**
	 * @return the uniteCode
	 */
	public String getUniteCode() {
		return uniteCode;
	}

	/**
	 * @param uniteCode the uniteCode to set
	 */
	public void setUniteCode(String uniteCode) {
		this.uniteCode = uniteCode;
	}

	/**
	 * @return the upbankid
	 */
	public long getUpbankid() {
		return upbankid;
	}

	/**
	 * @param upbankid the upbankid to set
	 */
	public void setUpbankid(long upbankid) {
		this.upbankid = upbankid;
	}

	/**
	 * @return the upgrade
	 */
	public long getUpgrade() {
		return upgrade;
	}

	/**
	 * @param upgrade the upgrade to set
	 */
	public void setUpgrade(long upgrade) {
		this.upgrade = upgrade;
	}

	/**
	 * @return the upgradecode
	 */
	public String getUpgradecode() {
		return upgradecode;
	}

	/**
	 * @param upgradecode the upgradecode to set
	 */
	public void setUpgradecode(String upgradecode) {
		this.upgradecode = upgradecode;
	}
	

}
