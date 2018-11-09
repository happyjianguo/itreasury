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
	private String code = "";       //交易对手编号
	private String name = "";       //交易对手全称
	private String shortName = "";  //交易对手缩写
	private long type = -1;         //交易对手类型
	//      1=证券公司
	//		2=基金管理公司
	//		3=中央银行
	//		4=政策性银行
	//		5=国有商业银行
	//		6=其它商业银行
	//		7=保险公司
	//		8=其它非银行金融机构
	//		9=其它单位
	private String businessLicenseCode    = ""; //营业执照号码
	private String financeLicenseCode     = ""; //金融许可证号码　
	private String securitiesTrusteeCode  = ""; //债券托管帐号	　
	private long counterpartBankAccountId = -1; //默认开户行ID
    //----20034-03-25-----需求变更-----新增----刘惠军---------
    private long isBank                   = -1; //是否开户银行
    private long isBankOfDeposit          = -1; //是否开户营业部
    private long isFundManagementCo       = -1; //是否开户基金管理公司
    private long isInterBankCounterpart   = -1; //是否银行间交易对手
	private long isSecuritiesUnderwriter  = -1; //是否债券分销商
	private long isSecurityCo             = -1; //是否委托理财券商
	private long isInvestedCorporation    = -1; //是否被投资企业
	private long isOwnershipTransfer      = -1; //是否股权受让方
	private long isFloaters               = -1; //是否债券发行人
	private long isConsigner              = -1; //是否受托理财委托方
	private long isPolicyHolder           = -1; //是否投保人	
	//-------------------------------------------------------
	private String legalPerson   =  ""; //法定代表人
	private String contacter     =  ""; //联系人
	private String address       =  ""; //地址
	private String phone         =  ""; //电话　
	private String fax           =  ""; //传真
	private String zipCode       =  ""; //邮编
	private String email         =  ""; //电子邮件
	private String remark        =  ""; //备注
    //----20034-03-25-----需求变更-----新增----刘惠军---------
	private long    stockHoldQuantity = -1;    //持有股权数量,针对被投资企业
	private double  stockHoldRate     = 0.0;   //持有股权比例,针对被投资企业
	private long    industrySort      = -1;    //行业类别，针对被投资企业
		
	private double  commisionRate     = 0.0;   //代理手续费率,针对受托理财委托方	
	private long    industryType      = -1;    //行业类型,针对投保人
	//-------------------------------------------------------
	private double creditLine    = 0.0; //拆出额度	　	　
	private Timestamp creditLineStartDate = null;  //拆出额度起日　	　
	private Timestamp creditLineEndDate   = null;  //拆出额度止日　	　
	private double debitLine              = 0.0;   //拆入额度　
	private Timestamp debitLineStartDate  = null;  //拆入额度起日　	　
	private Timestamp debitLineEndDate    = null;  //拆入额度止日　
	private long    siteType      = -1;    //营业性质
	
	private long statusId        = -1;      //状态 //0=已删除	//	2=已保存//	3=已复核
	private long inputUserId     = -1;      //录入人　
	private Timestamp inputDate  = null;    //录入时间	　
	private long updateUserId    = -1;      //修改人　
	private Timestamp updateDate = null;    //修改时间　
	private long checkUserId     = -1;      //复核人
	private Timestamp checkDate  = null;    //复核时间
	
	

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
