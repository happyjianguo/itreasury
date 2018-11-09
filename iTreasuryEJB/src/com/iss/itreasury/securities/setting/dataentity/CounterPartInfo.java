/*
 * Created on 2004-3-10
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.securities.setting.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 * @author hjliu
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CounterPartInfo extends SECBaseDataEntity implements Serializable
{

	private long id     = -1;       //ID
	private String code = "";       //���׶��ֱ��
	private String name = "";       //���׶���ȫ��
	private String shortName = "";  //���׶�����д
	private long type = -1;         //���׶�������
	//      1=֤ȯ��˾
	//		2=�������˾
	//		3=��������
	//		4=����������
	//		5=������ҵ����
	//		6=������ҵ����
	//		7=���չ�˾
	//		8=���������н��ڻ���
	//		9=������λ
	private String businessLicenseCode    = ""; //Ӫҵִ�պ���
	private String financeLicenseCode     = ""; //�������֤���롡
	private String securitiesTrusteeCode  = ""; //ծȯ�й��ʺ�	��
	private long counterpartBankAccountId = -1; //Ĭ�Ͽ�����ID
    //----20034-03-25-----������-----����----���ݾ�---------
    private long isBank                   = -1; //�Ƿ񿪻�����
    private long isBankOfDeposit          = -1; //�Ƿ񿪻�Ӫҵ��
    private long isFundManagementCo       = -1; //�Ƿ񿪻��������˾
    private long isInterBankCounterpart   = -1; //�Ƿ����м佻�׶���
	private long isSecuritiesUnderwriter  = -1; //�Ƿ�ծȯ������
	private long isSecurityCo             = -1; //�Ƿ�ί�����ȯ��
	private long isInvestedCorporation    = -1; //�Ƿ�Ͷ����ҵ
	private long isOwnershipTransfer      = -1; //�Ƿ��Ȩ���÷�
	private long isFloaters               = -1; //�Ƿ�ծȯ������
	private long isConsigner              = -1; //�Ƿ��������ί�з�
	private long isPolicyHolder           = -1; //�Ƿ�Ͷ����	
	//-------------------------------------------------------
	private String legalPerson   =  ""; //����������
	private String contacter     =  ""; //��ϵ��
	private String address       =  ""; //��ַ
	private String phone         =  ""; //�绰��
	private String fax           =  ""; //����
	private String zipCode       =  ""; //�ʱ�
	private String email         =  ""; //�����ʼ�
	private String remark        =  ""; //��ע
    //----20034-03-25-----������-----����----���ݾ�---------
	private long    stockHoldQuantity = -1;    //���й�Ȩ����,��Ա�Ͷ����ҵ
	private double  stockHoldRate     = 0.0;   //���й�Ȩ����,��Ա�Ͷ����ҵ
	private long    industrySort      = -1;    //��ҵ�����Ա�Ͷ����ҵ
		
	private double  commisionRate     = 0.0;   //������������,����������ί�з�	
	private long    industryType      = -1;    //��ҵ����,���Ͷ����
	//-------------------------------------------------------
	private double creditLine    = 0.0; //������	��	��
	private Timestamp creditLineStartDate = null;  //���������ա�	��
	private Timestamp creditLineEndDate   = null;  //������ֹ�ա�	��
	private double debitLine              = 0.0;   //�����ȡ�
	private Timestamp debitLineStartDate  = null;  //���������ա�	��
	private Timestamp debitLineEndDate    = null;  //������ֹ�ա�
	private long    siteType      = -1;    //Ӫҵ����
	
	private long statusId        = -1;      //״̬ //0=��ɾ��	//	2=�ѱ���//	3=�Ѹ���
	private long inputUserId     = -1;      //¼���ˡ�
	private Timestamp inputDate  = null;    //¼��ʱ��	��
	private long updateUserId    = -1;      //�޸��ˡ�
	private Timestamp updateDate = null;    //�޸�ʱ�䡡
	private long checkUserId     = -1;      //������
	private Timestamp checkDate  = null;    //����ʱ��
	
	

	/**
	 * @return
	 */
	public String getAddress()
	{
		return address;
	}

	/**
	 * @return
	 */
	public String getBusinessLicenseCode()
	{
		return businessLicenseCode;
	}

	/**
	 * @return
	 */
	public Timestamp getCheckDate()
	{
		return checkDate;
	}

	/**
	 * @return
	 */
	public long getCheckUserId()
	{
		return checkUserId;
	}

	/**
	 * @return
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * @return
	 */
	public String getContacter()
	{
		return contacter;
	}

	/**
	 * @return
	 */
	public double getCreditLine()
	{
		return creditLine;
	}

	/**
	 * @return
	 */
	public Timestamp getCreditLineEndDate()
	{
		return creditLineEndDate;
	}

	/**
	 * @return
	 */
	public Timestamp getCreditLineStartDate()
	{
		return creditLineStartDate;
	}

	/**
	 * @return
	 */
	public double getDebitLine()
	{
		return debitLine;
	}

	/**
	 * @return
	 */
	public Timestamp getDebitLineEndDate()
	{
		return debitLineEndDate;
	}

	/**
	 * @return
	 */
	public Timestamp getDebitLineStartDate()
	{
		return debitLineStartDate;
	}

	/**
	 * @return
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @return
	 */
	public String getFax()
	{
		return fax;
	}

	/**
	 * @return
	 */
	public String getFinanceLicenseCode()
	{
		return financeLicenseCode;
	}

	/**
	 * @return
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * @return
	 */
	public Timestamp getInputDate()
	{
		return inputDate;
	}

	/**
	 * @return
	 */
	public long getInputUserId()
	{
		return inputUserId;
	}

	/**
	 * @return
	 */
	public String getLegalPerson()
	{
		return legalPerson;
	}

	/**
	 * @return
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return
	 */
	public String getPhone()
	{
		return phone;
	}

	/**
	 * @return
	 */
	public String getRemark()
	{
		return remark;
	}

	/**
	 * @return
	 */
	public String getSecuritiesTrusteeCode()
	{
		return securitiesTrusteeCode;
	}

	/**
	 * @return
	 */
	public String getShortName()
	{
		return shortName;
		
	}

	/**
	 * @return
	 */
	public long getStatusId()
	{
		return statusId;
	}

	/**
	 * @return
	 */
	public long getType()
	{
		return type;
	}

	/**
	 * @return
	 */
	public Timestamp getUpdateDate()
	{
		return updateDate;
	}

	/**
	 * @return
	 */
	public long getUpdateUserId()
	{
		return updateUserId;
	}

	/**
	 * @return
	 */
	public String getZipCode()
	{
		return zipCode;
	}

	/**
	 * @param string
	 */
	public void setAddress(String string)
	{
		address = string;
		putUsedField("address", string);
	}

	/**
	 * @param string
	 */
	public void setBusinessLicenseCode(String string)
	{
		businessLicenseCode = string;
		putUsedField("businessLicenseCode", string);
	}

	/**
	 * @param timestamp
	 */
	public void setCheckDate(Timestamp timestamp)
	{
		checkDate = timestamp;
		putUsedField("checkDate", timestamp);
	}

	/**
	 * @param l
	 */
	public void setCheckUserId(long l)
	{
		checkUserId = l;
		putUsedField("checkUserID", l);
	}

	/**
	 * @param string
	 */
	public void setCode(String string)
	{
		code = string;
		putUsedField("code", string);
		
	}

	/**
	 * @param string
	 */
	public void setContacter(String string)
	{
		contacter = string;
		putUsedField("contacter", string);
	}

	/**
	 * @param d
	 */
	public void setCreditLine(double d)
	{
		creditLine = d;
		putUsedField("creditLine", d);
	}

	/**
	 * @param timestamp
	 */
	public void setCreditLineEndDate(Timestamp timestamp)
	{
		creditLineEndDate = timestamp;
		putUsedField("creditLineEndDate", timestamp);
	}

	/**
	 * @param timestamp
	 */
	public void setCreditLineStartDate(Timestamp timestamp)
	{
		creditLineStartDate = timestamp;
		putUsedField("creditLineStartDate", timestamp);
	}

	/**
	 * @param d
	 */
	public void setDebitLine(double d)
	{
		debitLine = d;
		putUsedField("debitLine", d);
	}

	/**
	 * @param timestamp
	 */
	public void setDebitLineEndDate(Timestamp timestamp)
	{
		debitLineEndDate = timestamp;
		putUsedField("debitLineEndDate", timestamp);
		
	}

	/**
	 * @param timestamp
	 */
	public void setDebitLineStartDate(Timestamp timestamp)
	{
		debitLineStartDate = timestamp;
		putUsedField("debitLineStartDate", timestamp);		
		
	}

	/**
	 * @param string
	 */
	public void setEmail(String string)
	{
		email = string;
		putUsedField("email", string);
	}

	/**
	 * @param string
	 */
	public void setFax(String string)
	{
		fax = string;
		putUsedField("fax", string);
	}

	/**
	 * @param string
	 */
	public void setFinanceLicenseCode(String string)
	{
		financeLicenseCode = string;
		putUsedField("financeLicenseCode", string);
	}

	/**
	 * @param l
	 */
	public void setId(long l)
	{
		id = l;
		putUsedField("id", l);
	}

	/**
	 * @param timestamp
	 */
	public void setInputDate(Timestamp timestamp)
	{
		inputDate = timestamp;
		putUsedField("inputDate", timestamp);
	}

	/**
	 * @param l
	 */
	public void setInputUserId(long l)
	{
		inputUserId = l;
		putUsedField("inputUserID", l);
	}

	/**
	 * @param string
	 */
	public void setLegalPerson(String string)
	{
		legalPerson = string;
		putUsedField("legalPerson", string);
	}

	/**
	 * @param string
	 */
	public void setName(String string)
	{
		name = string;
		putUsedField("name", string);
	}

	/**
	 * @param string
	 */
	public void setPhone(String string)
	{
		phone = string;
		putUsedField("phone", string);
	}

	/**
	 * @param string
	 */
	public void setRemark(String string)
	{
		remark = string;
		putUsedField("remark", string);
	}

	/**
	 * @param string
	 */
	public void setSecuritiesTrusteeCode(String string)
	{
		securitiesTrusteeCode = string;
		putUsedField("securitiesTrusteeCode", string);
	}

	/**
	 * @param string
	 */
	public void setShortName(String string)
	{
		shortName = string;
		putUsedField("shortName", string);
	}

	/**
	 * @param l
	 */
	public void setStatusId(long l)
	{
		statusId = l;
		putUsedField("statusID", l);
	}

	/**
	 * @param l
	 */
	public void setType(long l)
	{
		type = l;
		putUsedField("type", l);
	}

	/**
	 * @param timestamp
	 */
	public void setUpdateDate(Timestamp timestamp)
	{
		updateDate = timestamp;
		putUsedField("updateDate", timestamp);
	}

	/**
	 * @param l
	 */
	public void setUpdateUserId(long l)
	{
		updateUserId = l;
		putUsedField("updateUserID", l);
	}

	/**
	 * @param string
	 */
	public void setZipCode(String string)
	{
		zipCode = string;
		putUsedField("zipCode", string);
	}

	/**
	 * @return
	 */
	public long getCounterpartBankAccountId()
	{
		return counterpartBankAccountId;
	}

	/**
	 * @param l
	 */
	public void setCounterpartBankAccountId(long l)
	{
		counterpartBankAccountId = l;
		putUsedField("counterpartBankAccountID", l);
	}

	
	/**
	 * @return
	 */
	public double getCommisionRate()
	{
		return commisionRate;
	}

	

	/**
	 * @return
	 */
	public long getIsBank()
	{
		return isBank;
	}

	/**
	 * @return
	 */
	public long getIsBankOfDeposit()
	{
		return isBankOfDeposit;
	}
  
	/**
	 * @return
	 */
	public long getIsConsigner()
	{
		return isConsigner;
	}

	/**
	 * @return
	 */
	public long getIsFloaters()
	{
		return isFloaters;
	}

	/**
	 * @return
	 */
	public long getIsFundManagementCo()
	{
		return isFundManagementCo;
	}

	/**
	 * @return
	 */
	public long getIsInterBankCounterpart()
	{
		return isInterBankCounterpart;
	}

	/**
	 * @return
	 */
	public long getIsInvestedCorporation()
	{
		return isInvestedCorporation;
	}

	/**
	 * @return
	 */
	public long getIsOwnershipTransfer()
	{
		return isOwnershipTransfer;
	}

	/**
	 * @return
	 */
	public long getIsPolicyHolder()
	{
		return isPolicyHolder;
	}

	/**
	 * @return
	 */
	public long getIsSecuritiesUnderwriter()
	{
		return isSecuritiesUnderwriter;
	}

	/**
	 * @return
	 */
	public long getIsSecurityCo()
	{
		return isSecurityCo;
	}

	/**
	 * @return
	 */
	public long getStockHoldQuantity()
	{
		return stockHoldQuantity;
	}

	/**
	 * @return
	 */
	public double getStockHoldRate()
	{
		return stockHoldRate;
	}

	/**
	 * @param d
	 */
	public void setCommisionRate(double d)
	{
		commisionRate = d;
		putUsedField("CommisionRate", d);
		
	}

	

	/**
	 * @param l
	 */
	public void setIsBank(long l)
	{
		isBank = l;
		putUsedField("isBank", l);
	}

	/**
	 * @param l
	 */
	public void setIsBankOfDeposit(long l)
	{
		isBankOfDeposit = l;
		putUsedField("isBankOfDeposit", l);
	}

	/**
	 * @param l
	 */
	public void setIsConsigner(long l)
	{
		isConsigner = l;
		putUsedField("isConsigner", l);
	}

	/**
	 * @param l
	 */
	public void setIsFloaters(long l)
	{
		isFloaters = l;
		putUsedField("isFloaters", l);
	}

	/**
	 * @param l
	 */
	public void setIsFundManagementCo(long l)
	{
		isFundManagementCo = l;
		putUsedField("isFundManagementCo", l);
	}

	/**
	 * @param l
	 */
	public void setIsInterBankCounterpart(long l)
	{
		isInterBankCounterpart = l;
		putUsedField("isInterBankCounterpart", l);
	}

	/**
	 * @param l
	 */
	public void setIsInvestedCorporation(long l)
	{
		isInvestedCorporation = l;
		putUsedField("isInvestedCorporation", l);
	}

	/**
	 * @param l
	 */
	public void setIsOwnershipTransfer(long l)
	{
		isOwnershipTransfer = l;
		putUsedField("isOwnershipTransfer", l);
	}

	/**
	 * @param l
	 */
	public void setIsPolicyHolder(long l)
	{
		isPolicyHolder = l;
		putUsedField("isPolicyHolder", l);
	}

	/**
	 * @param l
	 */
	public void setIsSecuritiesUnderwriter(long l)
	{
		isSecuritiesUnderwriter = l;
		putUsedField("isSecuritiesUnderwriter", l);
	}

	/**
	 * @param l
	 */
	public void setIsSecurityCo(long l)
	{
		isSecurityCo = l;
		putUsedField("isSecurityCo", l);
	}

	/**
	 * @param l
	 */
	public void setStockHoldQuantity(long l)
	{
		stockHoldQuantity = l;
		putUsedField("StockHoldQuantity", l);
	}

	/**
	 * @param d
	 */
	public void setStockHoldRate(double d)
	{
		stockHoldRate = d;
		putUsedField("StockHoldRate", d);
	}

	

	/**
	 * @return
	 */
	public long getIndustrySort()
	{
		return industrySort;
	}

	/**
	 * @return
	 */
	public long getIndustryType()
	{
		return industryType;
	}

	/**
	 * @param l
	 */
	public void setIndustrySort(long l)
	{
		industrySort = l;
		putUsedField("industrySort", l);
	}

	/**
	 * @param l
	 */
	public void setIndustryType(long l)
	{
		industryType = l;
		putUsedField("industryType", l);
	}

	/**
	 * Returns the siteType.
	 * @return long
	 */
	public long getSiteType() {
		return siteType;
	}

	/**
	 * Sets the siteType.
	 * @param siteType The siteType to set
	 */
	public void setSiteType(long siteType) {
		this.siteType = siteType;
		putUsedField("siteType", siteType);
	}

}
