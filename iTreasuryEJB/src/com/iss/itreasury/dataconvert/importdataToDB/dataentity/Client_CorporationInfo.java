/*
 * Created on 2006-3-22
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToDB.dataentity;

import java.sql.Timestamp;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Client_CorporationInfo {
    private long id=-1;
    //���˴���
    private String legalPerson="";
    //���˴���
    private String legalPersonCodeCert="";
    //Ӫҵִ�պ���
    private String licenceCode="";
    //�������֤����
    private String financePermissionNo="";
    //��˰�ǼǺ�
    private String countryTaxRegisterNo="";
    //��˰�ǼǺ�
    private String localTaxRegisterNo="";
    //ע��ص�
    private String registerPlace="";
    //ע�����1
    private String registerCurrencyType="";
    //ע���ʱ�1
    private String registerCapital1="";
    //ע�����2
    private String registerCurrencyType2="";
    //ע���ʱ�2
    private String registerCapital2="";
    //ע�����3
    private String registerCurrencyType3="";
    //ע���ʱ�3
    private String registerCapital3="";
    //ע�����4
    private String registerCurrencyType4="";
    //ע���ʱ�4
    private String registerCapital4="";
    //ע�����5
    private String registerCurrencyType5="";
    //ע���ʱ�5
    private String registerCapital5="";
    //���ʻ���
    private String validateOrganization="";
    //��Ӫ��Χ
    private String dealScope="";
    //��Ҫ��Ʒ�Ͳ���
    private String products="";
    //��Ӫҵ�������г�
    private String operations="";
    //�ʲ���ģ
    private String capitalScope="";
    //���ʲ�
    private String netCapital="";
    //������ģ
    private String productScope="";
    //��ַ-�ʱ�
    private String zipCode="";
    //��ַ-ʡ
    private String province="";
    //��ַ-��
    private String city="";
    //��ַ
    private String address="";
    //�Ƿ����˾�ɶ�
    private long isPartner=-1;
    //�Ƿ����й�˾
    private long isMarkCompany=-1;
    //���еص�1
    private String markPlace1="";
    //��Ʊ����1 
    private String stockNo1="";
    //���еص�2
    private String markPlace2="";
    //��Ʊ����2 
    private String stockNo2="";
    //���еص�3
    private String markPlace3="";
    //��Ʊ����3 
    private String stockNo3="";
    //���еص�4
    private String markPlace4="";
    //��Ʊ����4 
    private String stockNo4="";
    //���еص�5
    private String markPlace5="";
    //��Ʊ����5 
    private String stockNo5="";
    //�����
    private String loanCardNo="";
    //�������
    private String loanCardPwd="";
    //��������
    private Timestamp buildDate=null;
    //Ա������
    private long employeeNumber=-1;
    //����ҵ����ϵ��
    private String settlementContacter="";
    //�Ŵ�ҵ����ϵ��
    private String loanContacter="";
    //�绰
    private String phone="";
    //����
    private String fax="";
    //��ַ
    private String webSite="";
    //��������
    private String eMail="";
    //�ͻ�����һ
    private long clientTypeId1=-1;
    //�ͻ����Զ�
    private long clientTypeId2=-1;
    //�ͻ�������
    private long clientTypeId3=-1;
    //�ͻ�������
    private long clientTypeId4=-1;
    //�ͻ�������
    private long clientTypeId5=-1;
    //�ͻ�������
    private long clientTypeId6=-1;
    //ĸ��˾1(�ϼ����ܲ���)
    private long parentCorpId1=-1;
    //ĸ��˾2(�ϼ����ܲ���)
    private long parentCorpId2=-1;
    //Ԥ������ϼ���λ
    private String budgetParent="";
    //����˾�˻���
    private String account="";
    //��Ҫ��������1
    private String bank1="";
    //��Ҫ��������2
    private String bank2="";
    //��Ҫ��������3
    private String bank3="";
    //���������˻���1
    private String extendAccount1="";
    //���������˻���2
    private String extendAccount2="";
    //���������˻���3
    private String extendAccount3="";
    //���õȼ�
    private String creditLevelId="";
    //���õȼ���������
    private String judgeLevelClient="";
    //���õȼ���������
    private Timestamp creditLevelDate=null;
    //�ڲ����õȼ�
    private String insideCreditLevel="";
    //���ռ���
    private String riskLevel="";
    //���ʵȼ�
    private String talentLevel="";
    //�ͻ���չ����1
    private long extendAttribute1=-1;
    //�ͻ���չ����2
    private long extendAttribute2=-1;
    //�ͻ���չ����3
    private long extendAttribute3=-1;
    //�ͻ���չ����4
    private long extendAttribute4=-1;
    //�ͻ���չ����5
    private long extendAttribute5=-1;
    //�ͻ���չ����6
    private long extendAttribute6=-1;
    //�ͻ���չ����7
    private long extendAttribute7=-1;
    //�ͻ���չ����8
    private long extendAttribute8=-1;
    //�ͻ���չ��Ϣ1
    private String extendInfo1="";
    //�ͻ���չ��Ϣ2
    private String extendInfo2="";
    //�ͻ���չ��Ϣ3
    private String extendInfo3="";
    //�ͻ���չ��Ϣ4
    private String extendInfo4="";
    //�ͻ���չ��Ϣ5
    private String extendInfo5="";
    //�ͻ���չ��Ϣ6
    private String extendInfo6="";
    //�ͻ���չ��Ϣ7
    private String extendInfo7="";
    //�ͻ���չ��Ϣ8
    private String extendInfo8="";
    //�ͻ�id
    private long clientId=-1;

    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getBank1() {
        return bank1;
    }
    public void setBank1(String bank1) {
        this.bank1 = bank1;
    }
    public String getBank2() {
        return bank2;
    }
    public void setBank2(String bank2) {
        this.bank2 = bank2;
    }
    public String getBank3() {
        return bank3;
    }
    public void setBank3(String bank3) {
        this.bank3 = bank3;
    }
    public Timestamp getBuildDate() {
        return buildDate;
    }
    public void setBuildDate(Timestamp buildDate) {
        this.buildDate = buildDate;
    }
    public String getCapitalScope() {
        return capitalScope;
    }
    public void setCapitalScope(String capitalScope) {
        this.capitalScope = capitalScope;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public long getClientTypeId1() {
        return clientTypeId1;
    }
    public void setClientTypeId1(long clientTypeId1) {
        this.clientTypeId1 = clientTypeId1;
    }
    public long getClientTypeId2() {
        return clientTypeId2;
    }
    public void setClientTypeId2(long clientTypeId2) {
        this.clientTypeId2 = clientTypeId2;
    }
    public long getClientTypeId3() {
        return clientTypeId3;
    }
    public void setClientTypeId3(long clientTypeId3) {
        this.clientTypeId3 = clientTypeId3;
    }
    public long getClientTypeId4() {
        return clientTypeId4;
    }
    public void setClientTypeId4(long clientTypeId4) {
        this.clientTypeId4 = clientTypeId4;
    }
    public long getClientTypeId5() {
        return clientTypeId5;
    }
    public void setClientTypeId5(long clientTypeId5) {
        this.clientTypeId5 = clientTypeId5;
    }
    public long getClientTypeId6() {
        return clientTypeId6;
    }
    public void setClientTypeId6(long clientTypeId6) {
        this.clientTypeId6 = clientTypeId6;
    }
    public String getCountryTaxRegisterNo() {
        return countryTaxRegisterNo;
    }
    public void setCountryTaxRegisterNo(String countryTaxRegisterNo) {
        this.countryTaxRegisterNo = countryTaxRegisterNo;
    }
    public Timestamp getCreditLevelDate() {
        return creditLevelDate;
    }
    public void setCreditLevelDate(Timestamp creditLevelDate) {
        this.creditLevelDate = creditLevelDate;
    }
    public String getCreditLevelId() {
        return creditLevelId;
    }
    public void setCreditLevelId(String creditLevelId) {
        this.creditLevelId = creditLevelId;
    }
    public String getDealScope() {
        return dealScope;
    }
    public void setDealScope(String dealScope) {
        this.dealScope = dealScope;
    }
    public String getEMail() {
        return eMail;
    }
    public void setEMail(String mail) {
        eMail = mail;
    }
    public long getEmployeeNumber() {
        return employeeNumber;
    }
    public void setEmployeeNumber(long employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
    public String getExtendAccount1() {
        return extendAccount1;
    }
    public void setExtendAccount1(String extendAccount1) {
        this.extendAccount1 = extendAccount1;
    }
    public String getExtendAccount2() {
        return extendAccount2;
    }
    public void setExtendAccount2(String extendAccount2) {
        this.extendAccount2 = extendAccount2;
    }
    public String getExtendAccount3() {
        return extendAccount3;
    }
    public void setExtendAccount3(String extendAccount3) {
        this.extendAccount3 = extendAccount3;
    }
    public long getExtendAttribute1() {
        return extendAttribute1;
    }
    public void setExtendAttribute1(long extendAttribute1) {
        this.extendAttribute1 = extendAttribute1;
    }
    public long getExtendAttribute2() {
        return extendAttribute2;
    }
    public void setExtendAttribute2(long extendAttribute2) {
        this.extendAttribute2 = extendAttribute2;
    }
    public long getExtendAttribute3() {
        return extendAttribute3;
    }
    public void setExtendAttribute3(long extendAttribute3) {
        this.extendAttribute3 = extendAttribute3;
    }
    public long getExtendAttribute4() {
        return extendAttribute4;
    }
    public void setExtendAttribute4(long extendAttribute4) {
        this.extendAttribute4 = extendAttribute4;
    }
    public long getExtendAttribute5() {
        return extendAttribute5;
    }
    public void setExtendAttribute5(long extendAttribute5) {
        this.extendAttribute5 = extendAttribute5;
    }
    public long getExtendAttribute6() {
        return extendAttribute6;
    }
    public void setExtendAttribute6(long extendAttribute6) {
        this.extendAttribute6 = extendAttribute6;
    }
    public long getExtendAttribute7() {
        return extendAttribute7;
    }
    public void setExtendAttribute7(long extendAttribute7) {
        this.extendAttribute7 = extendAttribute7;
    }
    public long getExtendAttribute8() {
        return extendAttribute8;
    }
    public void setExtendAttribute8(long extendAttribute8) {
        this.extendAttribute8 = extendAttribute8;
    }
    public String getExtendInfo1() {
        return extendInfo1;
    }
    public void setExtendInfo1(String extendInfo1) {
        this.extendInfo1 = extendInfo1;
    }
    public String getExtendInfo2() {
        return extendInfo2;
    }
    public void setExtendInfo2(String extendInfo2) {
        this.extendInfo2 = extendInfo2;
    }
    public String getExtendInfo3() {
        return extendInfo3;
    }
    public void setExtendInfo3(String extendInfo3) {
        this.extendInfo3 = extendInfo3;
    }
    public String getExtendInfo4() {
        return extendInfo4;
    }
    public void setExtendInfo4(String extendInfo4) {
        this.extendInfo4 = extendInfo4;
    }
    public String getExtendInfo5() {
        return extendInfo5;
    }
    public void setExtendInfo5(String extendInfo5) {
        this.extendInfo5 = extendInfo5;
    }
    public String getExtendInfo6() {
        return extendInfo6;
    }
    public void setExtendInfo6(String extendInfo6) {
        this.extendInfo6 = extendInfo6;
    }
    public String getExtendInfo7() {
        return extendInfo7;
    }
    public void setExtendInfo7(String extendInfo7) {
        this.extendInfo7 = extendInfo7;
    }
    public String getExtendInfo8() {
        return extendInfo8;
    }
    public void setExtendInfo8(String extendInfo8) {
        this.extendInfo8 = extendInfo8;
    }
    public String getFax() {
        return fax;
    }
    public void setFax(String fax) {
        this.fax = fax;
    }
    public String getFinancePermissionNo() {
        return financePermissionNo;
    }
    public void setFinancePermissionNo(String financePermissionNo) {
        this.financePermissionNo = financePermissionNo;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getInsideCreditLevel() {
        return insideCreditLevel;
    }
    public void setInsideCreditLevel(String insideCreditLevel) {
        this.insideCreditLevel = insideCreditLevel;
    }
    public long getIsMarkCompany() {
        return isMarkCompany;
    }
    public void setIsMarkCompany(long isMarkCompany) {
        this.isMarkCompany = isMarkCompany;
    }
    public long getIsPartner() {
        return isPartner;
    }
    public void setIsPartner(long isPartner) {
        this.isPartner = isPartner;
    }
    public String getJudgeLevelClient() {
        return judgeLevelClient;
    }
    public void setJudgeLevelClient(String judgeLevelClient) {
        this.judgeLevelClient = judgeLevelClient;
    }
    public String getLegalPerson() {
        return legalPerson;
    }
    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }
    public String getLegalPersonCodeCert() {
        return legalPersonCodeCert;
    }
    public void setLegalPersonCodeCert(String legalPersonCodeCert) {
        this.legalPersonCodeCert = legalPersonCodeCert;
    }
    public String getLicenceCode() {
        return licenceCode;
    }
    public void setLicenceCode(String licenceCode) {
        this.licenceCode = licenceCode;
    }
    public String getLoanCardNo() {
        return loanCardNo;
    }
    public void setLoanCardNo(String loanCardNo) {
        this.loanCardNo = loanCardNo;
    }
    public String getLoanCardPwd() {
        return loanCardPwd;
    }
    public void setLoanCardPwd(String loanCardPwd) {
        this.loanCardPwd = loanCardPwd;
    }
    public String getLoanContacter() {
        return loanContacter;
    }
    public void setLoanContacter(String loanContacter) {
        this.loanContacter = loanContacter;
    }
    public String getLocalTaxRegisterNo() {
        return localTaxRegisterNo;
    }
    public void setLocalTaxRegisterNo(String localTaxRegisterNo) {
        this.localTaxRegisterNo = localTaxRegisterNo;
    }
    public String getMarkPlace1() {
        return markPlace1;
    }
    public void setMarkPlace1(String markPlace1) {
        this.markPlace1 = markPlace1;
    }
    public String getMarkPlace2() {
        return markPlace2;
    }
    public void setMarkPlace2(String markPlace2) {
        this.markPlace2 = markPlace2;
    }
    public String getMarkPlace3() {
        return markPlace3;
    }
    public void setMarkPlace3(String markPlace3) {
        this.markPlace3 = markPlace3;
    }
    public String getMarkPlace4() {
        return markPlace4;
    }
    public void setMarkPlace4(String markPlace4) {
        this.markPlace4 = markPlace4;
    }
    public String getMarkPlace5() {
        return markPlace5;
    }
    public void setMarkPlace5(String markPlace5) {
        this.markPlace5 = markPlace5;
    }
    public String getNetCapital() {
        return netCapital;
    }
    public void setNetCapital(String netCapital) {
        this.netCapital = netCapital;
    }
    public String getOperations() {
        return operations;
    }
    public void setOperations(String operations) {
        this.operations = operations;
    }
    public long getParentCorpId1() {
        return parentCorpId1;
    }
    public void setParentCorpId1(long parentCorpId1) {
        this.parentCorpId1 = parentCorpId1;
    }
    public long getParentCorpId2() {
        return parentCorpId2;
    }
    public void setParentCorpId2(long parentCorpId2) {
        this.parentCorpId2 = parentCorpId2;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getProducts() {
        return products;
    }
    public void setProducts(String products) {
        this.products = products;
    }
    public String getProductScope() {
        return productScope;
    }
    public void setProductScope(String productScope) {
        this.productScope = productScope;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getRegisterCapital1() {
        return registerCapital1;
    }
    public void setRegisterCapital1(String registerCapital1) {
        this.registerCapital1 = registerCapital1;
    }
    public String getRegisterCapital2() {
        return registerCapital2;
    }
    public void setRegisterCapital2(String registerCapital2) {
        this.registerCapital2 = registerCapital2;
    }
    public String getRegisterCapital3() {
        return registerCapital3;
    }
    public void setRegisterCapital3(String registerCapital3) {
        this.registerCapital3 = registerCapital3;
    }
    public String getRegisterCapital4() {
        return registerCapital4;
    }
    public void setRegisterCapital4(String registerCapital4) {
        this.registerCapital4 = registerCapital4;
    }
    public String getRegisterCapital5() {
        return registerCapital5;
    }
    public void setRegisterCapital5(String registerCapital5) {
        this.registerCapital5 = registerCapital5;
    }
    public String getRegisterCurrencyType() {
        return registerCurrencyType;
    }
    public void setRegisterCurrencyType(String registerCurrencyType) {
        this.registerCurrencyType = registerCurrencyType;
    }
    public String getRegisterCurrencyType2() {
        return registerCurrencyType2;
    }
    public void setRegisterCurrencyType2(String registerCurrencyType2) {
        this.registerCurrencyType2 = registerCurrencyType2;
    }
    public String getRegisterCurrencyType3() {
        return registerCurrencyType3;
    }
    public void setRegisterCurrencyType3(String registerCurrencyType3) {
        this.registerCurrencyType3 = registerCurrencyType3;
    }
    public String getRegisterCurrencyType4() {
        return registerCurrencyType4;
    }
    public void setRegisterCurrencyType4(String registerCurrencyType4) {
        this.registerCurrencyType4 = registerCurrencyType4;
    }
    public String getRegisterCurrencyType5() {
        return registerCurrencyType5;
    }
    public void setRegisterCurrencyType5(String registerCurrencyType5) {
        this.registerCurrencyType5 = registerCurrencyType5;
    }
    public String getRegisterPlace() {
        return registerPlace;
    }
    public void setRegisterPlace(String registerPlace) {
        this.registerPlace = registerPlace;
    }
    public String getRiskLevel() {
        return riskLevel;
    }
    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }
    public String getSettlementContacter() {
        return settlementContacter;
    }
    public void setSettlementContacter(String settlementContacter) {
        this.settlementContacter = settlementContacter;
    }
    public String getStockNo1() {
        return stockNo1;
    }
    public void setStockNo1(String stockNo1) {
        this.stockNo1 = stockNo1;
    }
    public String getStockNo2() {
        return stockNo2;
    }
    public void setStockNo2(String stockNo2) {
        this.stockNo2 = stockNo2;
    }
    public String getStockNo3() {
        return stockNo3;
    }
    public void setStockNo3(String stockNo3) {
        this.stockNo3 = stockNo3;
    }
    public String getStockNo4() {
        return stockNo4;
    }
    public void setStockNo4(String stockNo4) {
        this.stockNo4 = stockNo4;
    }
    public String getStockNo5() {
        return stockNo5;
    }
    public void setStockNo5(String stockNo5) {
        this.stockNo5 = stockNo5;
    }
    public String getTalentLevel() {
        return talentLevel;
    }
    public void setTalentLevel(String talentLevel) {
        this.talentLevel = talentLevel;
    }
    public String getValidateOrganization() {
        return validateOrganization;
    }
    public void setValidateOrganization(String validateOrganization) {
        this.validateOrganization = validateOrganization;
    }
    public String getWebSite() {
        return webSite;
    }
    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }
    public String getZipCode() {
        return zipCode;
    }
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    public long getClientId() {
        return clientId;
    }
    public void setClientId(long clientId) {
        this.clientId = clientId;
    }
    public String getBudgetParent() {
        return budgetParent;
    }
    public void setBudgetParent(String budgetParent) {
        this.budgetParent = budgetParent;
    }
}
