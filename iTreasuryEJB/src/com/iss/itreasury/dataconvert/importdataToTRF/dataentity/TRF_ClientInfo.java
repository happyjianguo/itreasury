/*
 * Created on 2006-3-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToTRF.dataentity;

import java.sql.Timestamp;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TRF_ClientInfo {
    //�ͻ�id
    private long id=-1;
    //�������´����
    private String officeCode="";
    //�ͻ����
    private String code="";
    //�ͻ���������
    private String name="";
    //�ͻ����ļ��
    private String simpleName="";
    //�ͻ����ı���
    private String name2="";
    //�ͻ�Ӣ������
    private String engName="";
    //�ͻ�Ӣ�ļ��
    private String simpleEngName="";
    //�ͻ�Ӣ�ı���
    private String engName2="";
    //�ͻ���������
    private String clientBaseType="";
    //ϵͳ��־
    private String systemIdentify="";
    //��ѯ����
    private String queryPassword="";
    //�����ͻ�����
    private String customerManagerUser="";
    //����
    private String country="";
    //������ϵ����
    private Timestamp inputDate=null;
    //���񼶱�
    private String serviceLevel="";
    //¼����
    private String inputUser="";
    //¼��ʱ��
    private Timestamp inputTime=null;
    //�޸���
    private String modifyUser="";
    //�޸�ʱ��
    private Timestamp modifyTime=null;
    //״̬
    private String status="";
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
    private String isPartner="";
    //�Ƿ����й�˾
    private String isMarkCompany="";
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
    private String clientType1="";
    //�ͻ����Զ�
    private String clientType2="";
    //�ͻ�������
    private String clientType3="";
    //�ͻ�������
    private String clientType4="";
    //�ͻ�������
    private String clientType5="";
    //�ͻ�������
    private String clientType6="";
    //ĸ��˾1(�ϼ����ܲ���)
    private String parentCorp1="";
    //ĸ��˾2(�ϼ����ܲ���)
    private String parentCorp2="";
    //Ԥ������ϼ���λ
    private String budgetParentCorp="";
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
    private String extendAttribute1="";
    //�ͻ���չ����2
    private String extendAttribute2="";
    //�ͻ���չ����3
    private String extendAttribute3="";
    //�ͻ���չ����4
    private String extendAttribute4="";
    //�ͻ���չ����5
    private String extendAttribute5="";
    //�ͻ���չ����6
    private String extendAttribute6="";
    //�ͻ���չ����7
    private String extendAttribute7="";
    //�ͻ���չ����8
    private String extendAttribute8="";
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
    public String getBudgetParentCorp() {
        return budgetParentCorp;
    }
    public void setBudgetParentCorp(String budgetParentCorp) {
        this.budgetParentCorp = budgetParentCorp;
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
    public String getClientType1() {
        return clientType1;
    }
    public void setClientType1(String clientType1) {
        this.clientType1 = clientType1;
    }
    public String getClientType2() {
        return clientType2;
    }
    public void setClientType2(String clientType2) {
        this.clientType2 = clientType2;
    }
    public String getClientType3() {
        return clientType3;
    }
    public void setClientType3(String clientType3) {
        this.clientType3 = clientType3;
    }
    public String getClientType4() {
        return clientType4;
    }
    public void setClientType4(String clientType4) {
        this.clientType4 = clientType4;
    }
    public String getClientType5() {
        return clientType5;
    }
    public void setClientType5(String clientType5) {
        this.clientType5 = clientType5;
    }
    public String getClientType6() {
        return clientType6;
    }
    public void setClientType6(String clientType6) {
        this.clientType6 = clientType6;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
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
    public String getCustomerManagerUser() {
        return customerManagerUser;
    }
    public void setCustomerManagerUser(String customerManagerUser) {
        this.customerManagerUser = customerManagerUser;
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
    public String getEngName() {
        return engName;
    }
    public void setEngName(String engName) {
        this.engName = engName;
    }
    public String getEngName2() {
        return engName2;
    }
    public void setEngName2(String engName2) {
        this.engName2 = engName2;
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
    public String getExtendAttribute1() {
        return extendAttribute1;
    }
    public void setExtendAttribute1(String extendAttribute1) {
        this.extendAttribute1 = extendAttribute1;
    }
    public String getExtendAttribute2() {
        return extendAttribute2;
    }
    public void setExtendAttribute2(String extendAttribute2) {
        this.extendAttribute2 = extendAttribute2;
    }
    public String getExtendAttribute3() {
        return extendAttribute3;
    }
    public void setExtendAttribute3(String extendAttribute3) {
        this.extendAttribute3 = extendAttribute3;
    }
    public String getExtendAttribute4() {
        return extendAttribute4;
    }
    public void setExtendAttribute4(String extendAttribute4) {
        this.extendAttribute4 = extendAttribute4;
    }
    public String getExtendAttribute5() {
        return extendAttribute5;
    }
    public void setExtendAttribute5(String extendAttribute5) {
        this.extendAttribute5 = extendAttribute5;
    }
    public String getExtendAttribute6() {
        return extendAttribute6;
    }
    public void setExtendAttribute6(String extendAttribute6) {
        this.extendAttribute6 = extendAttribute6;
    }
    public String getExtendAttribute7() {
        return extendAttribute7;
    }
    public void setExtendAttribute7(String extendAttribute7) {
        this.extendAttribute7 = extendAttribute7;
    }
    public String getExtendAttribute8() {
        return extendAttribute8;
    }
    public void setExtendAttribute8(String extendAttribute8) {
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
    public Timestamp getInputDate() {
        return inputDate;
    }
    public void setInputDate(Timestamp inputDate) {
        this.inputDate = inputDate;
    }
    public Timestamp getInputTime() {
        return inputTime;
    }
    public void setInputTime(Timestamp inputTime) {
        this.inputTime = inputTime;
    }
    public String getInputUser() {
        return inputUser;
    }
    public void setInputUser(String inputUser) {
        this.inputUser = inputUser;
    }
    public String getInsideCreditLevel() {
        return insideCreditLevel;
    }
    public void setInsideCreditLevel(String insideCreditLevel) {
        this.insideCreditLevel = insideCreditLevel;
    }
    public String getIsMarkCompany() {
        return isMarkCompany;
    }
    public void setIsMarkCompany(String isMarkCompany) {
        this.isMarkCompany = isMarkCompany;
    }
    public String getIsPartner() {
        return isPartner;
    }
    public void setIsPartner(String isPartner) {
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
    public Timestamp getModifyTime() {
        return modifyTime;
    }
    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }
    public String getModifyUser() {
        return modifyUser;
    }
    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName2() {
        return name2;
    }
    public void setName2(String name2) {
        this.name2 = name2;
    }
    public String getNetCapital() {
        return netCapital;
    }
    public void setNetCapital(String netCapital) {
        this.netCapital = netCapital;
    }
    public String getOfficeCode() {
        return officeCode;
    }
    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }
    public String getOperations() {
        return operations;
    }
    public void setOperations(String operations) {
        this.operations = operations;
    }
    public String getParentCorp1() {
        return parentCorp1;
    }
    public void setParentCorp1(String parentCorp1) {
        this.parentCorp1 = parentCorp1;
    }
    public String getParentCorp2() {
        return parentCorp2;
    }
    public void setParentCorp2(String parentCorp2) {
        this.parentCorp2 = parentCorp2;
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
    public String getQueryPassword() {
        return queryPassword;
    }
    public void setQueryPassword(String queryPassword) {
        this.queryPassword = queryPassword;
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
    public String getServiceLevel() {
        return serviceLevel;
    }
    public void setServiceLevel(String serviceLevel) {
        this.serviceLevel = serviceLevel;
    }
    public String getSettlementContacter() {
        return settlementContacter;
    }
    public void setSettlementContacter(String settlementContacter) {
        this.settlementContacter = settlementContacter;
    }
    public String getSimpleEngName() {
        return simpleEngName;
    }
    public void setSimpleEngName(String simpleEngName) {
        this.simpleEngName = simpleEngName;
    }
    public String getSimpleName() {
        return simpleName;
    }
    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
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
    public String getSystemIdentify() {
        return systemIdentify;
    }
    public void setSystemIdentify(String systemIdentify) {
        this.systemIdentify = systemIdentify;
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
    public String getClientBaseType() {
        return clientBaseType;
    }
    public void setClientBaseType(String clientBaseType) {
        this.clientBaseType = clientBaseType;
    }
}
