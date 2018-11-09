/* Generated by Together */

package com.iss.itreasury.clientmanage.client.dataentity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Collection;

import com.iss.itreasury.clientmanage.dataentity.CimsBaseDataEntity;
import com.iss.itreasury.clientmanage.dataentity.ClientInfo;

/**
*���˿ͻ�������ϢdataEntity����Ӧ�ڱ�Client_CorporationInfo
* */
public class CorporationInfo extends CimsBaseDataEntity {
	

	private long clientid=-1;//�ͻ�id
	private String legalPerson ="";//���˴���
	private String legalPersonCodeCert="";//���˴��� 
	private String licenceCode="";//Ӫҵ�պ���
	private String financePermissionNo="";//��������֤����
	private String countryTaxRegisterNo="";//��˰�ǼǺ�
	private String localTaxRegisterNo="";//��˰�ǼǺ�
	private String registerPlace="";//ע��ص�1
	private String registerCurrencyType="";//ע�����1
	private String registerCapital1="";//ע���ʽ�1
	private String registerCurrencyType2="";//ע�����2
	private String registerCapital2="";//ע���ʽ�2
	private String registerCurrencyType3="";//ע�����3
	private String registerCapital3="";//ע���ʽ�3
	private String registerCurrencyType4="";//ע�����4
	private String registerCapital4="";//ע���ʽ�4
	private String registerCurrencyType5="";//ע�����5
	private String registerCapital5="";//ע���ʽ�5
	private String validateOrganization="";//���ʻ���
	private String dealScope="";//��Ӫ��Χ
    private String products="";//��Ҫ��Ʒ
    private String operations="";//��Ӫҵ���������г�
    private String capitalScope="";//�ʲ���ģ
    private String netCapital="";//���ʲ�
    private String productScope="";//������ģ
    private String zipCode="";//��ַ�ʱ�
    private String province="";//ʡ
    private String city="";//�� 
    private String address="";//��ַ
    private long isPartner=0;// �Ƿ���� 
    private long isMarkCompany=0;//�Ƿ����� 
    private String markPlace1="";//���еص�1
    private String stockNo1="";//��Ʊ����1
    private String markPlace2="";//���еص�2
    private String stockNo2="";//��Ʊ����2
    private String markPlace3="";//���еص�3
    private String stockNo3="";//��Ʊ����3
    private String markPlace4="";//���еص�4
    private String stockNo4="";//��Ʊ����4
    private String markPlace5="";//���еص�5
    private String stockNo5="";//��Ʊ����5
    private String loanCardNo="";//�����
    private String loanCardPwd="";//�������
    private Timestamp buildDate;//��������
    private long employeeNumber=0;//Ա������
    private String settlementContacter="";//����ҵ����ϵ��
    private String loanContacter="";//�Ŵ�ҵ����ϵ��
    private String phone="";//�绰
    private String fax="";//����
    private String website="";//��ַ
    private String email="";//����
    private Timestamp signStart;//ӡ������
    private long currentSignID=-1;//��ǰӡ��id
    private long clienttypeID1=-1;//�ͻ�����һ
    private long clienttypeID2=-1;//�ͻ����Զ�
    private long clienttypeID3=-1;//�ͻ�������
    private long clienttypeID4=-1;//�ͻ�������
    private long clienttypeID5=-1;//�ͻ�������
    private long clienttypeID6=-1;//�ͻ�������
    private long parentCorpID1=-1;//ĸ��˾һ
    private long parentCorpID2=-1;//ĸ��˾��
    private String budgetParent="";//Ԥ������ϼ���λ
    private String account="";//����˾�����
    private String bank1="";//��Ҫ��������1
    private String bank2="";//��Ҫ��������2
    private String bank3="";//��Ҫ��������3
    private String extendAccount1="";//���������˻���1
    private String extendAccount2="";//���������˻���2
    private String extendAccount3="";//���������˻���3
    private String creditLevelID="";//���õȼ�
    private String judgeLevelClient="";//���õȼ���������
	private Timestamp creditLevelDate;//���õȼ���������  ---�ͻ����õȼ�ռ�� add by xfma
	private String insideCreditLevel="";//�ڲ����õȼ�  ---�ͻ����õȼ�ռ�� add by xfma
	private double assessMark=0.00;//�ڲ����õȼ�  ---�ͻ����õȼ�ռ�� add by xfma
	private String riskLevel="";//���ռ���
	private String talentLevel="";//���ʵȼ�
	private long extendAttribute1=-1;//�ͻ���չ����1
	private long extendAttribute2=-1;//�ͻ���չ����2
	private long extendAttribute3=-1;//�ͻ���չ����3
	private long extendAttribute4=-1;//�ͻ���չ����4
	private long extendAttribute5=-1;//�ͻ���չ����5
	private long extendAttribute6=-1;//�ͻ���չ����6
	private long extendAttribute7=-1;//�ͻ���չ����7
	private long extendAttribute8=-1;//�ͻ���չ����8
	private String extendInfo1="";//�ͻ���չ��Ϣ1
	private String extendInfo2="";//�ͻ���չ��Ϣ2
	private String extendInfo3="";//�ͻ���չ��Ϣ3
	private String extendInfo4="";//�ͻ���չ��Ϣ4
	private String extendInfo5="";//�ͻ���չ��Ϣ5
	private String extendInfo6="";//�ͻ���չ��Ϣ6
	private String extendInfo7="";//�ͻ���չ��Ϣ7
	private String extendInfo8="";//�ͻ���չ��Ϣ8
	private String businessLicensePic="";//Ӫҵִ��ͼ������
	private String organizationPic="";//��֯�鹹ͼ����
	
	/*�ͻ����Ե�������*/
	private String clientTypeid1_name="";
	private String clientTypeid2_name="";
	private String clientTypeid3_name="";
	private String clientTypeid4_name="";
	private String clientTypeid5_name="";
	private String clientTypeid6_name="";
	
	/*�ϼ���λ��������*/
	private String parentCorpid1_name="";
	private String parentCorpid2_name="";
	
	/*�ͻ���չ���Ե�������*/
	private String extendAttribute1_name="";
	private String extendAttribute2_name="";
	private String extendAttribute3_name="";
	private String extendAttribute4_name="";
	private String extendAttribute5_name="";
	private String extendAttribute6_name="";
	private String extendAttribute7_name="";
	private String extendAttribute8_name="";

	


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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		putUsedField("address", address);
		this.address = address;
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
	
	public String getBudgetParent() {
		return budgetParent;
	}
	
	public void setBudgetParent(String budgetParent) {
		putUsedField("budgetParent", budgetParent);
		this.budgetParent = budgetParent;
	}
	
	public Timestamp getBuildDate() {
		return buildDate;
	}
	
	public void setBuildDate(Timestamp buildDate) {
		putUsedField("buildDate", buildDate);
		this.buildDate = buildDate;
	}
	
	public String getBusinessLicensePic() {
		return businessLicensePic;
	}
	
	public void setBusinessLicensePic(String businessLicensePic) {
		putUsedField("businessLicensePic", businessLicensePic);
		this.businessLicensePic = businessLicensePic;
	}
	
	public String getCapitalScope() {
		return capitalScope;
	}
	
	public void setCapitalScope(String capitalScope) {
		putUsedField("capitalScope", capitalScope);
		this.capitalScope = capitalScope;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		putUsedField("city", city);
		this.city = city;
	}
	
	public String getCountryTaxRegisterNo() {
		return countryTaxRegisterNo;
	}
	
	public void setCountryTaxRegisterNo(String countryTaxRegisterNo) {
		putUsedField("countryTaxRegisterNo", countryTaxRegisterNo);
		this.countryTaxRegisterNo = countryTaxRegisterNo;
	}
	
	public Timestamp getCreditLevelDate() {
		return creditLevelDate;
	}
	
	public void setCreditLevelDate(Timestamp creditLevelDate) {
		putUsedField("creditLevelDate", creditLevelDate);
		this.creditLevelDate = creditLevelDate;
	}

	public String getCreditLevelID() {
		return creditLevelID;
	}

	public long getCurrentSignID() {
		return currentSignID;
	}

	public void setCurrentSignID(long currentSignID) {
		this.currentSignID = currentSignID;
	}
	public void setCreditLevelID(String creditLevelID) {
		putUsedField("creditLevelID", creditLevelID);
		this.creditLevelID = creditLevelID;
	}
	
	public String getDealScope() {
		return dealScope;
	}

	public void setDealScope(String dealScope) {
		putUsedField("dealScope", dealScope);
		this.dealScope = dealScope;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		putUsedField("email", email);
		this.email = email;
	}
	
	public long getEmployeeNumber() {
		return employeeNumber;
	}
	
	public void setEmployeeNumber(long employeeNumber) {
		putUsedField("employeeNumber", employeeNumber);
		this.employeeNumber = employeeNumber;
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
	
	public String getExtendInfo1() {
		return extendInfo1;
	}

	public void setExtendInfo1(String extendInfo1) {
		putUsedField("extendInfo1", extendInfo1);
		this.extendInfo1 = extendInfo1;
	}
	
	public String getExtendInfo2() {
		return extendInfo2;
	}
	
	public void setExtendInfo2(String extendInfo2) {
		putUsedField("extendInfo2", extendInfo2);
		this.extendInfo2 = extendInfo2;
	}
	
	public String getExtendInfo3() {
		return extendInfo3;
	}
	
	public void setExtendInfo3(String extendInfo3) {
		putUsedField("extendInfo3", extendInfo3);
		this.extendInfo3 = extendInfo3;
	}
	
	public String getExtendInfo4() {
		return extendInfo4;
	}
	
	public void setExtendInfo4(String extendInfo4) {
		putUsedField("extendInfo4", extendInfo4);
		this.extendInfo4 = extendInfo4;
	}

	public String getExtendInfo5() {
		return extendInfo5;
	}
	
	public void setExtendInfo5(String extendInfo5) {
		putUsedField("extendInfo5", extendInfo5);
		this.extendInfo5 = extendInfo5;
	}
	
	public String getExtendInfo6() {
		return extendInfo6;
	}
	
	public void setExtendInfo6(String extendInfo6) {
		putUsedField("extendInfo6", extendInfo6);
		this.extendInfo6 = extendInfo6;
	}
	
	public String getExtendInfo7() {
		return extendInfo7;
	}
	
	public void setExtendInfo7(String extendInfo7) {
		putUsedField("extendInfo7", extendInfo7);
		this.extendInfo7 = extendInfo7;
	}
	
	public String getExtendInfo8() {
		return extendInfo8;
	}
	
	public void setExtendInfo8(String extendInfo8) {
		putUsedField("extendInfo8", extendInfo8);
		this.extendInfo8 = extendInfo8;
	}
	
	public String getFax() {
		return fax;
	}
	
	public void setFax(String fax) {
		putUsedField("fax", fax);
		this.fax = fax;
	}
	
	public String getFinancePermissionNo() {
		return financePermissionNo;
	}
	
	public void setFinancePermissionNo(String financePermissionNo) {
		putUsedField("financePermissionNo", financePermissionNo);
		this.financePermissionNo = financePermissionNo;
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

	public long getIsMarkCompany() {
		return isMarkCompany;
	}
	
	public void setIsMarkCompany(long isMarkCompany) {
		putUsedField("isMarkCompany", isMarkCompany);
		this.isMarkCompany = isMarkCompany;
	}
	
	public long getIsPartner() {
		return isPartner;
	}
	
	public void setIsPartner(long isPartner) {
		putUsedField("isPartner", isPartner);
		this.isPartner = isPartner;
	}
	
	public String getJudgeLevelClient() {
		return judgeLevelClient;
	}
	
	public void setJudgeLevelClient(String judgeLevelClient) {
		putUsedField("judgeLevelClient", judgeLevelClient);
		this.judgeLevelClient = judgeLevelClient;
	}
	
	public String getLegalPerson() {
		return legalPerson;
	}
	
	public void setLegalPerson(String legalPerson) {
		putUsedField("legalPerson", legalPerson);
		this.legalPerson = legalPerson;
	}
	
	public String getLegalPersonCodeCert() {
		return legalPersonCodeCert;
	}
	
	public void setLegalPersonCodeCert(String legalPersonCodeCert) {
		putUsedField("legalPersonCodeCert", legalPersonCodeCert);
		this.legalPersonCodeCert = legalPersonCodeCert;
	}
	
	public String getLicenceCode() {
		return licenceCode;
	}
	
	public void setLicenceCode(String licenceCode) {
		putUsedField("licenceCode", licenceCode);
		this.licenceCode = licenceCode;
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
	
	public String getLoanContacter() {
		return loanContacter;
	}
	
	public void setLoanContacter(String loanContacter) {
		putUsedField("loanContacter", loanContacter);
		this.loanContacter = loanContacter;
	}
	
	public String getLocalTaxRegisterNo() {
		return localTaxRegisterNo;
	}
	
	public void setLocalTaxRegisterNo(String localTaxRegisterNo) {
		putUsedField("localTaxRegisterNo", localTaxRegisterNo);
		this.localTaxRegisterNo = localTaxRegisterNo;
	}
	
	public String getMarkPlace1() {
		return markPlace1;
	}
	
	public void setMarkPlace1(String markPlace1) {
		putUsedField("markPlace1", markPlace1);
		this.markPlace1 = markPlace1;
	}
	
	public String getMarkPlace2() {
		return markPlace2;
	}
	
	public void setMarkPlace2(String markPlace2) {
		putUsedField("markPlace2", markPlace2);
		this.markPlace2 = markPlace2;
	}
	
	public String getMarkPlace3() {
		return markPlace3;
	}
	
	public void setMarkPlace3(String markPlace3) {
		putUsedField("markPlace3", markPlace3);
		this.markPlace3 = markPlace3;
	}
	
	public String getMarkPlace4() {
		return markPlace4;
	}
	
	public void setMarkPlace4(String markPlace4) {
		putUsedField("markPlace4", markPlace4);
		this.markPlace4 = markPlace4;
	}
	
	public String getMarkPlace5() {
		return markPlace5;
	}
	
	public void setMarkPlace5(String markPlace5) {
		putUsedField("markPlace5", markPlace5);
		this.markPlace5 = markPlace5;
	}
	
	public String getNetCapital() {
		return netCapital;
	}
	
	public void setNetCapital(String netCapital) {
		putUsedField("netCapital", netCapital);
		this.netCapital = netCapital;
	}
	
	/**
	 * @return Returns the parentCorpID1.
	 */
	public long getParentCorpID1() {
		return parentCorpID1;
	}
	/**
	 * @param parentCorpID1 The parentCorpID1 to set.
	 */
	public void setParentCorpID1(long parentCorpID1) {
		putUsedField("parentCorpID1", parentCorpID1);
		this.parentCorpID1 = parentCorpID1;
	}
	/**
	 * @return Returns the parentCorpID2.
	 */
	public long getParentCorpID2() {
		return parentCorpID2;
	}
	/**
	 * @param parentCorpID2 The parentCorpID2 to set.
	 */
	public void setParentCorpID2(long parentCorpID2) {
		putUsedField("parentCorpID2", parentCorpID2);
		this.parentCorpID2 = parentCorpID2;
	}
	public String getOperations() {
		return operations;
	}
	
	public void setOperations(String operations) {
		putUsedField("operations", operations);
		this.operations = operations;
	}

	public String getOrganizationPic() {
		return organizationPic;
	}

	public void setOrganizationPic(String organizationPic) {
		putUsedField("organizationPic", organizationPic);
		this.organizationPic = organizationPic;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		putUsedField("phone", phone);
		this.phone = phone;
	}
	
	public String getProducts() {
		return products;
	}
	
	public void setProducts(String products) {
		putUsedField("products", products);
		this.products = products;
	}
	
	public String getProductScope() {
		return productScope;
	}

	public void setProductScope(String productScope) {
		putUsedField("productScope", productScope);
		this.productScope = productScope;
	}
	
	public String getProvince() {
		return province;
	}
	
	public void setProvince(String province) {
		putUsedField("province", province);
		this.province = province;
	}
	
	public String getRegisterCapital1() {
		return registerCapital1;
	}
	
	public void setRegisterCapital1(String registerCapital1) {
		putUsedField("registerCapital1", registerCapital1);
		this.registerCapital1 = registerCapital1;
	}
	
	public String getRegisterCapital2() {
		return registerCapital2;
	}
	
	public void setRegisterCapital2(String registerCapital2) {
		putUsedField("registerCapital2", registerCapital2);
		this.registerCapital2 = registerCapital2;
	}
	
	public String getRegisterCapital3() {
		return registerCapital3;
	}
	
	public void setRegisterCapital3(String registerCapital3) {
		putUsedField("registerCapital3", registerCapital3);
		this.registerCapital3 = registerCapital3;
	}
	
	public String getRegisterCapital4() {
		return registerCapital4;
	}

	public void setRegisterCapital4(String registerCapital4) {
		putUsedField("registerCapital4", registerCapital4);
		this.registerCapital4 = registerCapital4;
	}
	
	public String getRegisterCapital5() {
		return registerCapital5;
	}
	
	public void setRegisterCapital5(String registerCapital5) {
		putUsedField("registerCapital5", registerCapital5);
		this.registerCapital5 = registerCapital5;
	}
	
	public String getRegisterCurrencyType() {
		return registerCurrencyType;
	}
	
	public void setRegisterCurrencyType(String registerCurrencyType) {
		putUsedField("registerCurrencyType", registerCurrencyType);
		this.registerCurrencyType = registerCurrencyType;
	}
	
	public String getRegisterCurrencyType2() {
		return registerCurrencyType2;
	}
	
	public void setRegisterCurrencyType2(String registerCurrencyType2) {
		putUsedField("registerCurrencyType2", registerCurrencyType2);
		this.registerCurrencyType2 = registerCurrencyType2;
	}
	
	public String getRegisterCurrencyType3() {
		return registerCurrencyType3;
	}
	
	public void setRegisterCurrencyType3(String registerCurrencyType3) {
		putUsedField("registerCurrencyType3", registerCurrencyType3);
		this.registerCurrencyType3 = registerCurrencyType3;
	}
	
	public String getRegisterCurrencyType4() {
		return registerCurrencyType4;
	}
	
	public void setRegisterCurrencyType4(String registerCurrencyType4) {
		putUsedField("registerCurrencyType4", registerCurrencyType4);
		this.registerCurrencyType4 = registerCurrencyType4;
	}
	
	public String getRegisterCurrencyType5() {
		return registerCurrencyType5;
	}
	
	public void setRegisterCurrencyType5(String registerCurrencyType5) {
		putUsedField("registerCurrencyType5", registerCurrencyType5);
		this.registerCurrencyType5 = registerCurrencyType5;
	}
	
	public String getRegisterPlace() {
		return registerPlace;
	}
	
	public void setRegisterPlace(String registerPlace) {
		putUsedField("registerPlace", registerPlace);
		this.registerPlace = registerPlace;
	}
	
	public String getRiskLevel() {
		return riskLevel;
	}
	
	public void setRiskLevel(String riskLevel) {
		putUsedField("riskLevel", riskLevel);
		this.riskLevel = riskLevel;
	}
	
	public String getSettlementContacter() {
		return settlementContacter;
	}
	
	public void setSettlementContacter(String settlementContacter) {
		putUsedField("settlementContacter", settlementContacter);
		this.settlementContacter = settlementContacter;
	}
	
	public Timestamp getSignStart() {
		return signStart;
	}
	
	public void setSignStart(Timestamp signStart) {
		putUsedField("signStart", signStart);
		this.signStart = signStart;
	}
	
	public String getStockNo1() {
		return stockNo1;
	}
	
	public void setStockNo1(String stockNo1) {
		putUsedField("stockNo1", stockNo1);
		this.stockNo1 = stockNo1;
	}
	
	public String getStockNo2() {
		return stockNo2;
	}

	public void setStockNo2(String stockNo2) {
		putUsedField("stockNo2", stockNo2);
		this.stockNo2 = stockNo2;
	}

	public String getStockNo3() {
		return stockNo3;
	}
	
	public void setStockNo3(String stockNo3) {
		putUsedField("stockNo3", stockNo3);
		this.stockNo3 = stockNo3;
	}
	
	public String getStockNo4() {
		return stockNo4;
	}
	
	public void setStockNo4(String stockNo4) {
		putUsedField("stockNo4", stockNo4);
		this.stockNo4 = stockNo4;
	}
	
	public String getStockNo5() {
		return stockNo5;
	}
	
	public void setStockNo5(String stockNo5) {
		putUsedField("stockNo5", stockNo5);
		this.stockNo5 = stockNo5;
	}
	
	public String getTalentLevel() {
		return talentLevel;
	}
	
	public void setTalentLevel(String talentLevel) {
		putUsedField("talentLevel", talentLevel);
		this.talentLevel = talentLevel;
	}

	public String getValidateOrganization() {
		return validateOrganization;
	}

	public void setValidateOrganization(String validateOrganization) {
		putUsedField("validateOrganization", validateOrganization);
		this.validateOrganization = validateOrganization;
	}
	
	public String getWebsite() {
		return website;
	}
	
	public void setWebsite(String website) {
		putUsedField("website", website);
		this.website = website;
	}
	
	public String getZipCode() {
		return zipCode;
	}
	
	public void setZipCode(String zipCode) {
		putUsedField("zipCode", zipCode);
		this.zipCode = zipCode;
	}	
	/**
	 * @return Returns the clienttypeID1.
	 */
	public long getClienttypeID1() {
		return clienttypeID1;
	}
	/**
	 * @param clienttypeID1 The clienttypeID1 to set.
	 */
	public void setClienttypeID1(long clienttypeID1) {
		putUsedField("clienttypeID1",clienttypeID1);
		this.clienttypeID1 = clienttypeID1;
	}
	/**
	 * @return Returns the clienttypeID2.
	 */
	public long getClienttypeID2() {
		return clienttypeID2;
	}
	/**
	 * @param clienttypeID2 The clienttypeID2 to set.
	 */
	public void setClienttypeID2(long clienttypeID2) {
		putUsedField("clienttypeID2",clienttypeID2);
		this.clienttypeID2 = clienttypeID2;
	}
	/**
	 * @return Returns the clienttypeID3.
	 */
	public long getClienttypeID3() {
		return clienttypeID3;
	}
	/**
	 * @param clienttypeID3 The clienttypeID3 to set.
	 */
	public void setClienttypeID3(long clienttypeID3) {
		putUsedField("clienttypeID3",clienttypeID3);
		this.clienttypeID3 = clienttypeID3;
	}
	/**
	 * @return Returns the clienttypeID4.
	 */
	public long getClienttypeID4() {
		return clienttypeID4;
	}
	/**
	 * @param clienttypeID4 The clienttypeID4 to set.
	 */
	public void setClienttypeID4(long clienttypeID4) {
		putUsedField("clienttypeID4",clienttypeID4);
		this.clienttypeID4 = clienttypeID4;
	}
	/**
	 * @return Returns the clienttypeID5.
	 */
	public long getClienttypeID5() {
		return clienttypeID5;
	}
	/**
	 * @param clienttypeID5 The clienttypeID5 to set.
	 */
	public void setClienttypeID5(long clienttypeID5) {
		putUsedField("clienttypeID5",clienttypeID5);
		this.clienttypeID5 = clienttypeID5;
	}
	/**
	 * @return Returns the clienttypeID6.
	 */
	public long getClienttypeID6() {
		return clienttypeID6;
	}
	/**
	 * @param clienttypeID6 The clienttypeID6 to set.
	 */
	public void setClienttypeID6(long clienttypeID6) {
		putUsedField("clienttypeID6",clienttypeID6);
		this.clienttypeID6 = clienttypeID6;
	}
	/**
	 * @return Returns the extendAttribute1.
	 */
	public long getExtendAttribute1() {
		return extendAttribute1;
	}
	/**
	 * @param extendAttribute1 The extendAttribute1 to set.
	 */
	public void setExtendAttribute1(long extendAttribute1) {
		putUsedField("extendAttribute1",extendAttribute1);
		this.extendAttribute1 = extendAttribute1;
	}
	/**
	 * @return Returns the extendAttribute2.
	 */
	public long getExtendAttribute2() {
		return extendAttribute2;
	}
	/**
	 * @param extendAttribute2 The extendAttribute2 to set.
	 */
	public void setExtendAttribute2(long extendAttribute2) {
		putUsedField("extendAttribute2",extendAttribute2);
		this.extendAttribute2 = extendAttribute2;
	}
	/**
	 * @return Returns the extendAttribute3.
	 */
	public long getExtendAttribute3() {
		return extendAttribute3;
	}
	/**
	 * @param extendAttribute3 The extendAttribute3 to set.
	 */
	public void setExtendAttribute3(long extendAttribute3) {
		putUsedField("extendAttribute3",extendAttribute3);
		this.extendAttribute3 = extendAttribute3;
	}
	/**
	 * @return Returns the extendAttribute4.
	 */
	public long getExtendAttribute4() {
		return extendAttribute4;
	}
	/**
	 * @param extendAttribute4 The extendAttribute4 to set.
	 */
	public void setExtendAttribute4(long extendAttribute4) {
		putUsedField("extendAttribute4",extendAttribute4);
		this.extendAttribute4 = extendAttribute4;
	}
	/**
	 * @return Returns the extendAttribute5.
	 */
	public long getExtendAttribute5() {
		return extendAttribute5;
	}
	/**
	 * @param extendAttribute5 The extendAttribute5 to set.
	 */
	public void setExtendAttribute5(long extendAttribute5) {
		putUsedField("extendAttribute5",extendAttribute5);
		this.extendAttribute5 = extendAttribute5;
	}
	/**
	 * @return Returns the extendAttribute6.
	 */
	public long getExtendAttribute6() {
		return extendAttribute6;
	}
	/**
	 * @param extendAttribute6 The extendAttribute6 to set.
	 */
	public void setExtendAttribute6(long extendAttribute6) {
		putUsedField("extendAttribute6",extendAttribute6);
		this.extendAttribute6 = extendAttribute6;
	}
	/**
	 * @return Returns the extendAttribute7.
	 */
	public long getExtendAttribute7() {
		return extendAttribute7;
	}
	/**
	 * @param extendAttribute7 The extendAttribute7 to set.
	 */
	public void setExtendAttribute7(long extendAttribute7) {
		putUsedField("extendAttribute7",extendAttribute7);
		this.extendAttribute7 = extendAttribute7;
	}
	/**
	 * @return Returns the extendAttribute8.
	 */
	public long getExtendAttribute8() {
		return extendAttribute8;
	}
	/**
	 * @param extendAttribute8 The extendAttribute8 to set.
	 */
	public void setExtendAttribute8(long extendAttribute8) {
		putUsedField("extendAttribute8",extendAttribute8);
		this.extendAttribute8 = extendAttribute8;
	}
    public Collection getPartnerInfo(){ return partnerInfo; }

    public void setPartnerInfo(Collection partnerInfo){ this.partnerInfo = partnerInfo; }

    public Collection getManagementInfo(){ return managementInfo; }

    public void setManagementInfo(Collection managementInfo){ this.managementInfo = managementInfo; }

    public Collection getInvestInfo(){ return investInfo; }

    public void setInvestInfo(Collection investInfo){ this.investInfo = investInfo; }

    public Collection getEnterpriseMemoInfo(){ return enterpriseMemoInfo; }

    public void setEnterpriseMemoInfo(Collection enterpriseMemoInfo){ this.enterpriseMemoInfo = enterpriseMemoInfo; }

    public Collection getEnterpriseLawInfo(){ return enterpriseLawInfo; }

    public void setEnterpriseLawInfo(Collection enterpriseLawInfo){ this.enterpriseLawInfo = enterpriseLawInfo; }

    public ClientInfo getClientInfo(){ return clientInfo; }

    public void setClientInfo(ClientInfo clientInfo){ this.clientInfo = clientInfo; }

    private Collection partnerInfo;        //��ҵ�ʱ�������Ϣ,��PartnerDataEntity���
    private Collection managementInfo;	   //��ҵ��������Ϣ����ManagementDataEntity���
    private Collection investInfo;         //��ҵ����Ͷ����Ϣ����InvestDataEntity���
    private Collection enterpriseMemoInfo; //��ҵ���¼���Ϣ����EnterpriseMemoDataEntity���
    private Collection enterpriseLawInfo;  //��ҵ������Ϣ����EnterpriseLawDataEntity���
    private ClientInfo clientInfo;         //��ҵ������Ϣ���ǵ�ǰ���˿ͻ��ĸ���Ϣ




	public String getClientTypeid1_name() {
		return clientTypeid1_name;
	}

	public void setClientTypeid1_name(String clientTypeid1_name) {
		this.clientTypeid1_name = clientTypeid1_name;
	}

	public String getClientTypeid2_name() {
		return clientTypeid2_name;
	}

	public void setClientTypeid2_name(String clientTypeid2_name) {
		this.clientTypeid2_name = clientTypeid2_name;
	}

	public String getClientTypeid3_name() {
		return clientTypeid3_name;
	}

	public void setClientTypeid3_name(String clientTypeid3_name) {
		this.clientTypeid3_name = clientTypeid3_name;
	}

	public String getClientTypeid4_name() {
		return clientTypeid4_name;
	}

	public void setClientTypeid4_name(String clientTypeid4_name) {
		this.clientTypeid4_name = clientTypeid4_name;
	}

	public String getClientTypeid5_name() {
		return clientTypeid5_name;
	}

	public void setClientTypeid5_name(String clientTypeid5_name) {
		this.clientTypeid5_name = clientTypeid5_name;
	}

	public String getClientTypeid6_name() {
		return clientTypeid6_name;
	}

	public void setClientTypeid6_name(String clientTypeid6_name) {
		this.clientTypeid6_name = clientTypeid6_name;
	}

	public String getExtendAttribute1_name() {
		return extendAttribute1_name;
	}

	public void setExtendAttribute1_name(String extendAttribute1_name) {
		this.extendAttribute1_name = extendAttribute1_name;
	}

	public String getExtendAttribute2_name() {
		return extendAttribute2_name;
	}

	public void setExtendAttribute2_name(String extendAttribute2_name) {
		this.extendAttribute2_name = extendAttribute2_name;
	}

	public String getExtendAttribute3_name() {
		return extendAttribute3_name;
	}

	public void setExtendAttribute3_name(String extendAttribute3_name) {
		this.extendAttribute3_name = extendAttribute3_name;
	}

	public String getExtendAttribute4_name() {
		return extendAttribute4_name;
	}

	public void setExtendAttribute4_name(String extendAttribute4_name) {
		this.extendAttribute4_name = extendAttribute4_name;
	}

	public String getExtendAttribute5_name() {
		return extendAttribute5_name;
	}

	public void setExtendAttribute5_name(String extendAttribute5_name) {
		this.extendAttribute5_name = extendAttribute5_name;
	}

	public String getExtendAttribute6_name() {
		return extendAttribute6_name;
	}

	public void setExtendAttribute6_name(String extendAttribute6_name) {
		this.extendAttribute6_name = extendAttribute6_name;
	}

	public String getExtendAttribute7_name() {
		return extendAttribute7_name;
	}

	public void setExtendAttribute7_name(String extendAttribute7_name) {
		this.extendAttribute7_name = extendAttribute7_name;
	}

	public String getExtendAttribute8_name() {
		return extendAttribute8_name;
	}

	public void setExtendAttribute8_name(String extendAttribute8_name) {
		this.extendAttribute8_name = extendAttribute8_name;
	}

	public String getParentCorpid1_name() {
		return parentCorpid1_name;
	}

	public void setParentCorpid1_name(String parentCorpid1_name) {
		this.parentCorpid1_name = parentCorpid1_name;
	}

	public String getParentCorpid2_name() {
		return parentCorpid2_name;
	}

	public void setParentCorpid2_name(String parentCorpid2_name) {
		this.parentCorpid2_name = parentCorpid2_name;
	}
public String toStringForLog(){ 
		
		StringBuffer res = new StringBuffer();
		try
		{ 
			Class model =Class.forName("com.iss.itreasury.clientmanage.client.dataentity.CorporationInfoForSwitch");
			Method[] methods = model.getDeclaredMethods();
			Object object = model.newInstance();

		for (int i = 0; i < methods.length; i++)
		{
			if ((methods[i].getName().startsWith("get")  )) 
			{
			Method getMethod = model.getMethod(methods[i].getName(), new Class[]{});
			String mName = getMethod.getName();
		
		   res.append(getMethod.invoke(object, new Object[]{})+"=" );
		

					Method tmp=getClass().getMethod(mName.substring(0, mName.length()-7), new Class[]{});
					
					Object o = tmp.invoke(this, new Object[]{});
					if (o == null)
					{
						res.append(" null ;");
						continue;
					}
					if (o instanceof Double)
					{
						res.append("" + ((Double) o).doubleValue() + " ;");
					}
					if (o instanceof Float)
					{
						res.append("" + ((Float) o).floatValue() + " ;");
					}
					else if (o instanceof Long)
					{
						res.append("" + ((Long) o).longValue() + " ;");
					}
					else if (o instanceof String)
					{
						res.append("" + (String) o + ";");
					}
					else if (o instanceof Timestamp)
					{
						res.append("" + ((Timestamp) o).toString() + " ;");
					}
					else 
						continue;
				}}}
				catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
				catch (InvocationTargetException e)
				{
					e.printStackTrace();
				}
				catch (NoSuchMethodException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        } catch (ClassNotFoundException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();}
		            catch (InstantiationException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            }

		
		return res.toString();		
	}
	

   
}