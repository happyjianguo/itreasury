/*
 * Created on 2003-9-2
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.account.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ClientInfo implements Serializable
{
	private long ClientID = -1; // 客户ID
	private String ClientCode = ""; // 客户编号
	private String ClientName = ""; // 客户名称
	private String ClientSimpleName = ""; // 客户简称
	private long OfficeID = -1; // 办事处ID
	private String LegalPerson = ""; // 法人代码
    //
	private long EnterpriseTypeID = -1; // 企业类型
    private long IndustryTypeID = -1; // 行业分类
    private long ClientTypeID = -1; // 客户分类
	private long EnterpriseTypeID1 = -1;//客户属性1
	private long EnterpriseTypeID2 = -1;//客户属性2
	private long EnterpriseTypeID3 = -1;//客户属性3
	private long EnterpriseTypeID4 = -1;//客户属性4
	private long EnterpriseTypeID5 = -1;//客户属性5
	private long EnterpriseTypeID6 = -1;//客户属性6
    //
    private long ExtendAttribute1 = -1;//扩展属性1
    private long ExtendAttribute2 = -1;//扩展属性2
    private long ExtendAttribute3 = -1;//扩展属性3
    private long ExtendAttribute4 = -1;//扩展属性4
    //
	private long ParentCorpID1 = -1; // 母公司1
	private long ParentCorpID2 = -1; // 母公司2
	private String Email = ""; // Email
	private String Address = ""; // 客户地址
	private String ZipCode = ""; // 邮政编码
	private String Contactor = ""; // 联系人
	private String Phone = ""; // 联系电话
	private String Fax = ""; // 传真
	private Timestamp SealStartDate = null; // 印鉴启用日期
	private long SealID = -1; // 当前印鉴ID
	private long InputUserID = -1; // 录入人ID
	private Timestamp InputDate = null; // 录入时间
	private long ModifyUserID = -1; // 修改人ID
	private Timestamp ModifyDate = null; // 修改时间
	private long PageCount = 1;
	private long StatusID = 1;
	private String Account="";
	private String QueryPassWord = "";//查询密码
	
	private long AccountGroupID = -1; //账户组id
	public String SAPClientCode =""; //SAP客户编码
	public String SAPSupplierCode =""; //SAP供应商编码
	/**
	 * Returns the address.
	 * @return String
	 */
	public String getAddress()
	{
		return Address;
	}

	/**
	 * Returns the clientCode.
	 * @return String
	 */
	public String getClientCode()
	{
		return ClientCode;
	}

	/**
	 * Returns the clientID.
	 * @return long
	 */
	public long getClientID()
	{
		return ClientID;
	}

	/**
	 * Returns the clientName.
	 * @return String
	 */
	public String getClientName()
	{
		return ClientName;
	}

	/**
	 * Returns the contactor.
	 * @return String
	 */
	public String getContactor()
	{
		return Contactor;
	}

	/**
	 * Returns the corpIndustryID.
	 * @return long
	 */
	public long getClientTypeID()
	{
		return ClientTypeID;
	}

	/**
	 * Returns the corpNatureID.
	 * @return long
	 */
	public long getEnterpriseTypeID()
	{
		return EnterpriseTypeID;
	}

	/**
	 * Returns the email.
	 * @return String
	 */
	public String getEmail()
	{
		return Email;
	}

	/**
	 * Returns the fax.
	 * @return String
	 */
	public String getFax()
	{
		return Fax;
	}

	/**
	 * Returns the inputDate.
	 * @return Timestamp
	 */
	public Timestamp getInputDate()
	{
		return InputDate;
	}

	/**
	 * Returns the inputUserID.
	 * @return long
	 */
	public long getInputUserID()
	{
		return InputUserID;
	}

	/**
	 * Returns the legalPerson.
	 * @return String
	 */
	public String getLegalPerson()
	{
		return LegalPerson;
	}

	/**
	 * Returns the modifyDate.
	 * @return Timestamp
	 */
	public Timestamp getModifyDate()
	{
		return ModifyDate;
	}

	/**
	 * Returns the modifyUserID.
	 * @return long
	 */
	public long getModifyUserID()
	{
		return ModifyUserID;
	}

	/**
	 * Returns the officeID.
	 * @return long
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}

	/**
	 * Returns the parentCorpID.
	 * @return long
	 */
	public long getParentCorpID1()
	{
		return ParentCorpID1;
	}

	/**
	 * Returns the phone.
	 * @return String
	 */
	public String getPhone()
	{
		return Phone;
	}

	/**
	 * Returns the sealID.
	 * @return long
	 */
	public long getSealID()
	{
		return SealID;
	}

	/**
	 * Returns the sealStartDate.
	 * @return Timestamp
	 */
	public Timestamp getSealStartDate()
	{
		return SealStartDate;
	}

	/**
	 * Returns the zipCode.
	 * @return String
	 */
	public String getZipCode()
	{
		return ZipCode;
	}

	/**
	 * Sets the address.
	 * @param address The address to set
	 */
	public void setAddress(String address)
	{
		Address = address;
	}

	/**
	 * Sets the clientCode.
	 * @param clientCode The clientCode to set
	 */
	public void setClientCode(String clientCode)
	{
		ClientCode = clientCode;
	}

	/**
	 * Sets the clientID.
	 * @param clientID The clientID to set
	 */
	public void setClientID(long clientID)
	{
		ClientID = clientID;
	}

	/**
	 * Sets the clientName.
	 * @param clientName The clientName to set
	 */
	public void setClientName(String clientName)
	{
		ClientName = clientName;
	}

	/**
	 * Sets the contactor.
	 * @param contactor The contactor to set
	 */
	public void setContactor(String contactor)
	{
		Contactor = contactor;
	}

	/**
	 * Sets the corpIndustryID.
	 * @param corpIndustryID The corpIndustryID to set
	 */
	public void setClientTypeID(long corpIndustryID)
	{
		ClientTypeID = corpIndustryID;
	}

	/**
	 * Sets the corpNatureID.
	 * @param corpNatureID The corpNatureID to set
	 */
	public void setEnterpriseTypeID(long corpNatureID)
	{
		EnterpriseTypeID = corpNatureID;
	}

	/**
	 * Sets the email.
	 * @param email The email to set
	 */
	public void setEmail(String email)
	{
		Email = email;
	}

	/**
	 * Sets the fax.
	 * @param fax The fax to set
	 */
	public void setFax(String fax)
	{
		Fax = fax;
	}

	/**
	 * Sets the inputDate.
	 * @param inputDate The inputDate to set
	 */
	public void setInputDate(Timestamp inputDate)
	{
		InputDate = inputDate;
	}

	/**
	 * Sets the inputUserID.
	 * @param inputUserID The inputUserID to set
	 */
	public void setInputUserID(long inputUserID)
	{
		InputUserID = inputUserID;
	}

	/**
	 * Sets the legalPerson.
	 * @param legalPerson The legalPerson to set
	 */
	public void setLegalPerson(String legalPerson)
	{
		LegalPerson = legalPerson;
	}

	/**
	 * Sets the modifyDate.
	 * @param modifyDate The modifyDate to set
	 */
	public void setModifyDate(Timestamp modifyDate)
	{
		ModifyDate = modifyDate;
	}

	/**
	 * Sets the modifyUserID.
	 * @param modifyUserID The modifyUserID to set
	 */
	public void setModifyUserID(long modifyUserID)
	{
		ModifyUserID = modifyUserID;
	}

	/**
	 * Sets the officeID.
	 * @param officeID The officeID to set
	 */
	public void setOfficeID(long officeID)
	{
		OfficeID = officeID;
	}

	/**
	 * Sets the parentCorpID.
	 * @param parentCorpID The parentCorpID to set
	 */
	public void setParentCorpID1(long parentCorpID)
	{
		ParentCorpID1 = parentCorpID;
	}

	/**
	 * Sets the phone.
	 * @param phone The phone to set
	 */
	public void setPhone(String phone)
	{
		Phone = phone;
	}

	/**
	 * Sets the sealID.
	 * @param sealID The sealID to set
	 */
	public void setSealID(long sealID)
	{
		SealID = sealID;
	}

	/**
	 * Sets the sealStartDate.
	 * @param sealStartDate The sealStartDate to set
	 */
	public void setSealStartDate(Timestamp sealStartDate)
	{
		SealStartDate = sealStartDate;
	}

	/**
	 * Sets the zipCode.
	 * @param zipCode The zipCode to set
	 */
	public void setZipCode(String zipCode)
	{
		ZipCode = zipCode;
	}

	public void setPageCount(long pageCount)
	{
		PageCount = pageCount;
	}

	public long getPageCount()
	{
		return PageCount;
	}

	public long getStatusID()
	{
		return StatusID;
	}

	public void setStatusID(long statusID)
	{
		StatusID = statusID;
	}

	public long getIndustryTypeID()
	{
		return IndustryTypeID;
	}

	public void setIndustryTypeID(long industryTypeID)
	{
		IndustryTypeID = industryTypeID;
	}

	/**
	 * @return
	 */
	public long getParentCorpID2()
	{
		return ParentCorpID2;
	}

	/**
	 * @param parentCorpID2
	 */
	public void setParentCorpID2(long parentCorpID2)
	{
		ParentCorpID2 = parentCorpID2;
	}

	/**
	 * @return Returns the clientSimpleName.
	 */
	public String getClientSimpleName()
	{
		return ClientSimpleName;
	}
	/**
	 * @param clientSimpleName The clientSimpleName to set.
	 */
	public void setClientSimpleName(String clientSimpleName)
	{
		ClientSimpleName = clientSimpleName;
	}
	/**
	 * @return
	 */
	public String getAccount()
	{
		return Account;
	}

	/**
	 * @param string
	 */
	public void setAccount(String string)
	{
		Account = string;
	}

	/**
	 * @return Returns the extendAttribute1.
	 */
	public long getExtendAttribute1()
	{
		return ExtendAttribute1;
	}
	/**
	 * @param extendAttribute1 The extendAttribute1 to set.
	 */
	public void setExtendAttribute1(long extendAttribute1)
	{
		ExtendAttribute1 = extendAttribute1;
	}
	/**
	 * @return Returns the extendAttribute2.
	 */
	public long getExtendAttribute2()
	{
		return ExtendAttribute2;
	}
	/**
	 * @param extendAttribute2 The extendAttribute2 to set.
	 */
	public void setExtendAttribute2(long extendAttribute2)
	{
		ExtendAttribute2 = extendAttribute2;
	}
	/**
	 * @return Returns the extendAttribute3.
	 */
	public long getExtendAttribute3()
	{
		return ExtendAttribute3;
	}
	/**
	 * @param extendAttribute3 The extendAttribute3 to set.
	 */
	public void setExtendAttribute3(long extendAttribute3)
	{
		ExtendAttribute3 = extendAttribute3;
	}
	/**
	 * @return Returns the extendAttribute4.
	 */
	public long getExtendAttribute4()
	{
		return ExtendAttribute4;
	}
	/**
	 * @param extendAttribute4 The extendAttribute4 to set.
	 */
	public void setExtendAttribute4(long extendAttribute4)
	{
		ExtendAttribute4 = extendAttribute4;
	}
	/**
	 * @return Returns the queryPassWord.
	 */
	public String getQueryPassWord()
	{
		return QueryPassWord;
	}
	/**
	 * @param queryPassWord The queryPassWord to set.
	 */
	public void setQueryPassWord(String queryPassWord)
	{
		QueryPassWord = queryPassWord;
	}
	
	/**
	 * @return Returns the enterpriseTypeID1.
	 */
	public long getEnterpriseTypeID1() {
		return EnterpriseTypeID1;
	}
	/**
	 * @param enterpriseTypeID1 The enterpriseTypeID1 to set.
	 */
	public void setEnterpriseTypeID1(long enterpriseTypeID1) {
		EnterpriseTypeID1 = enterpriseTypeID1;
	}
	/**
	 * @return Returns the enterpriseTypeID2.
	 */
	public long getEnterpriseTypeID2() {
		return EnterpriseTypeID2;
	}
	/**
	 * @param enterpriseTypeID2 The enterpriseTypeID2 to set.
	 */
	public void setEnterpriseTypeID2(long enterpriseTypeID2) {
		EnterpriseTypeID2 = enterpriseTypeID2;
	}
	/**
	 * @return Returns the enterpriseTypeID3.
	 */
	public long getEnterpriseTypeID3() {
		return EnterpriseTypeID3;
	}
	/**
	 * @param enterpriseTypeID3 The enterpriseTypeID3 to set.
	 */
	public void setEnterpriseTypeID3(long enterpriseTypeID3) {
		EnterpriseTypeID3 = enterpriseTypeID3;
	}
	/**
	 * @return Returns the enterpriseTypeID4.
	 */
	public long getEnterpriseTypeID4() {
		return EnterpriseTypeID4;
	}
	/**
	 * @param enterpriseTypeID4 The enterpriseTypeID4 to set.
	 */
	public void setEnterpriseTypeID4(long enterpriseTypeID4) {
		EnterpriseTypeID4 = enterpriseTypeID4;
	}
	/**
	 * @return Returns the enterpriseTypeID5.
	 */
	public long getEnterpriseTypeID5() {
		return EnterpriseTypeID5;
	}
	/**
	 * @param enterpriseTypeID5 The enterpriseTypeID5 to set.
	 */
	public void setEnterpriseTypeID5(long enterpriseTypeID5) {
		EnterpriseTypeID5 = enterpriseTypeID5;
	}
	/**
	 * @return Returns the enterpriseTypeID6.
	 */
	public long getEnterpriseTypeID6() {
		return EnterpriseTypeID6;
	}
	/**
	 * @param enterpriseTypeID6 The enterpriseTypeID6 to set.
	 */
	public void setEnterpriseTypeID6(long enterpriseTypeID6) {
		EnterpriseTypeID6 = enterpriseTypeID6;
	}

	public long getAccountGroupID() {
		return AccountGroupID;
	}

	public void setAccountGroupID(long accountGroupID) {
		AccountGroupID = accountGroupID;
	}

	public String getSAPClientCode() {
		return SAPClientCode;
	}

	public void setSAPClientCode(String clientCode) {
		SAPClientCode = clientCode;
	}

	public String getSAPSupplierCode() {
		return SAPSupplierCode;
	}

	public void setSAPSupplierCode(String supplierCode) {
		SAPSupplierCode = supplierCode;
	}
}
