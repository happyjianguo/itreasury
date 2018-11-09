package com.iss.itreasury.clientmanage.client.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.clientmanage.dataentity.CimsBaseDataEntity;

public class ClientContactInfo extends CimsBaseDataEntity{
	
	private long officeID=-1;//�������´�id
	private String code="";//�ͻ���� 
	private String name="";//���� 
	private long clientid=-1;//�ͻ�id
	private String country="";//����
	private String province="";//ʡ
    private String city="";//�� 
    private String address="";//��ַ
    private String phone="";//�绰
    private String fax="";//����
    private String website="";//��ַ
    private String email="";//����
    private String settlementContacter="";//����ҵ����ϵ��
    private String loanContacter="";//�Ŵ�ҵ����ϵ��
    private long customerManagerUserID=-1;//�����Ŀͻ�����
    private String queryPassword="";//��ѯ����
    private Timestamp inputDate;//������ϵ����
    private long serviceLevel=-1;//���񼶱�

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
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		putUsedField("country", country);
		this.country = country;
	}
	
	public String getProvince() {
		return province;
	}
	
	public void setProvince(String province) {
		putUsedField("province", province);
		this.province = province;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		putUsedField("city", city);
		this.city = city;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		putUsedField("address", address);
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		putUsedField("phone", phone);
		this.phone = phone;
	}
	
	public String getFax() {
		return fax;
	}
	
	public void setFax(String fax) {
		putUsedField("fax", fax);
		this.fax = fax;
	}
	
	public String getWebsite() {
		return website;
	}
	
	public void setWebsite(String website) {
		putUsedField("website", website);
		this.website = website;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		putUsedField("email", email);
		this.email = email;
	}
	
	public String getSettlementContacter() {
		return settlementContacter;
	}
	
	public void setSettlementContacter(String settlementContacter) {
		putUsedField("settlementContacter", settlementContacter);
		this.settlementContacter = settlementContacter;
	}
	
	public String getLoanContacter() {
		return loanContacter;
	}
	
	public void setLoanContacter(String loanContacter) {
		putUsedField("loanContacter", loanContacter);
		this.loanContacter = loanContacter;
	}
	
	public long getCustomerManagerUserID() {
		return customerManagerUserID;
	}
	
	public void setCustomerManagerUserID(long customerManagerUserID) {
		putUsedField("customerManagerUserID", customerManagerUserID);
		this.customerManagerUserID = customerManagerUserID;
	}
	
	public String getQueryPassword() {
		return queryPassword;
	}

	public void setQueryPassword(String queryPassword) {
		putUsedField("queryPassword", queryPassword);
		this.queryPassword = queryPassword;
	}
	
	public Timestamp getInputDate() {
		return inputDate;
	}

	public void setInputDate(Timestamp inputDate) {
		putUsedField("inputDate", inputDate);
		this.inputDate = inputDate;
	}
	
	public long getServiceLevel() {
		return serviceLevel;
	}
	
	public void setServiceLevel(long serviceLevel) {
		putUsedField("serviceLevel", serviceLevel);
		this.serviceLevel = serviceLevel;
	}

}
