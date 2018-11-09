/*
 * Created on 2005-5-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.ebank.system.dataentity;

import java.util.Date;

import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author mxzhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AccountInfo extends ITreasuryBaseDataEntity
{
	private long      id                   = -1;    //唯一标识       ID      Not ull      
	private String    accountNo            = null;  //账号         Code    Not null     
	private String    accountName          = null;  //账户名称       String  Not null     
	private long      currencyType         = -1;    //币种           ID	
	private String    currencyName         = null;  //币种名称
	private Date accountByOpenDate       = null;  //开户日期	 Date	              
	private long      accountStatus        = -1;    //账户状态	 Type    Not null     
	private long      bankId               = -1;    //银行类型	 ID	     
	private String    bankName         = null;  //银行名称
	private String    branchName           = null;  //开户行名称	 String  Not null     
	private long      countryId            = -1;    //开户所在国	 ID      Default -1	
	private String    branchAreaSeg1       = null;  //开户行所在地   ShortString Not null 
	private String    branchAreaSeg2       = null;  //开户行所在地   ShortString Not null 
	private String    branchAreaSeg3       = null;  //开户行所在地   String	              
	private String    branchCode           = null;  //银行机构号     Code	              
	private String    branchSwiftCode      = null;  //SWIFT代码      Code	              
	private long      accountPropertyOne   = -1;    //账户属性一     ID      Default -1   
    private String    accountPropertyOneName = null;
	private long      accountPropertyTwo   = -1;    //账户属性二     ID      Default -1
    private String      accountPropertyTwoName   = null;    
	private long      accountPropertyThree = -1;    //账户属性三     ID      Default -1
    private String      accountPropertyThreeName   = null;        
	private long      inputOrOutput        = -1;    //收支属性       Type	              
	private long      ownerType            = -1;    //账户所有者类型 Type    Not null     
	private long      clientId             = -1;    //对应客户id     ID      Default -1
	private String    clientName         = null;  //客户名称
	private long      declareClientId             = -1;    //申报客户id     ID      Default -1
	private long      subjectId            = -1;    //对应科目id     ID      Default -1   
	private long      upBankAcctId         = -1;    //上级银行账户id ID      Default -1   
	private long      isDirectLink         = -1;    //是否是直联账户 Type    Not null     
	private long      isCheck              = -1;    //是否已审核     Type    Default -1
	private long      regionUTCTime        = -1;    //UTC的时差
	private long      rdStatus             = -1;    //记录状态       Type    >0
	private Date modifyTime = null;//修改时间
	
	/**新增账户类型字段   --by chengli(2005-08-16)**/
	private long accountType=-1;//账户类型
	
	private long	isDirectionalVirement	= -1;	//是否定向划转(建行用)

	/**
	 * @return Returns the accountByOpenDate.
	 */
	public Date getAccountByOpenDate()
	{
		return accountByOpenDate;
	}
	/**
	 * @param accountByOpenDate The accountByOpenDate to set.
	 */
	public void setAccountByOpenDate(Date accountByOpenDate)
	{
		this.accountByOpenDate = accountByOpenDate;
		putUsedField("accountbyopendate", this.accountByOpenDate);
	}
	/**
	 * @return Returns the accountByOpenDate.
	 */
	public String getAccountByOpenDateString()
	{
		if(this.accountByOpenDate==null)
		{
			return "";
		}
		return DataFormat.formatDate(this.accountByOpenDate,1);
	}
	/**
	 * @param accountByOpenDate The accountByOpenDate to set.
	 */
	public void setAccountByOpenDateString(String accountByOpenDateString)
	{
		try
		{
			setAccountByOpenDate(DataFormat.parseDate(accountByOpenDateString, DataFormat.FMT_DATE_YYYYMMDD));
		} catch (Exception e)
		{
			e.printStackTrace();
		}	
	}
	/**
	 * @return Returns the regionUTCTime.
	 */
	public long getRegionUTCTime()
	{
		return regionUTCTime;
	}
	/**
	 * @param regionUTCTime The regionUTCTime to set.
	 */
	public void setRegionUTCTime(long regionUTCTime)
	{
		this.regionUTCTime = regionUTCTime;
		putUsedField("regionutctime", this.regionUTCTime);
	}
	public String getRegionUTCTimeString()
	{
		/*if(this.accountByOpenDate==null)
		{
			return "";
		}*/
		String strTemp = "";
		long lTemp = regionUTCTime;
		if(lTemp<0) 
		{
			strTemp="-";
			lTemp=-lTemp;
		}
		else strTemp="+";
		strTemp+=(lTemp/60)+":"+(Math.abs(lTemp%60));
		return strTemp;
	}
	/**
	 * @param regionUTCTime The regionUTCTime to set.
	 */
	public void setRegionUTCTimeString(String regionUTCTimeString)
	{
		String strTemp = "";
		//判断符号，兼容中英文符号
		if(regionUTCTimeString.indexOf("-")>=0 || regionUTCTimeString.indexOf("－")>=0)
		{
			strTemp = strTemp + "-";
			regionUTCTimeString = regionUTCTimeString.substring(1,regionUTCTimeString.length());
		}
		else
		{
			if(regionUTCTimeString.indexOf("+")>=0 || regionUTCTimeString.indexOf("＋")>=0)
			{
				regionUTCTimeString = regionUTCTimeString.substring(1,regionUTCTimeString.length());
			}
		}
		//分解
		String[] strTemps = null;
		if(regionUTCTimeString.indexOf(":")>=0)//英文字符
		{
			strTemps = DataFormat.splitString(regionUTCTimeString,":");
		}
		else//中文字符
		{
			strTemps = DataFormat.splitString(regionUTCTimeString,"：");
		}
		long time = Long.parseLong(strTemps[0])*60;//小时
		time = time + Long.parseLong(strTemps[1]);//分钟
		
		strTemp = strTemp + time;	
		
		setRegionUTCTime(Long.parseLong(strTemp));
	}
	/**
	 * @return Returns the accountName.
	 */
	public String getAccountName()
	{
		return accountName;
	}
	/**
	 * @param accountName The accountName to set.
	 */
	public void setAccountName(String accountName)
	{
		this.accountName = accountName;
		putUsedField("accountname", this.accountName);
	}
	/**
	 * @return Returns the accountNo.
	 */
	public String getAccountNo()
	{
		return accountNo;
	}
	/**
	 * @param accountNo The accountNo to set.
	 */
	public void setAccountNo(String accountNo)
	{
		this.accountNo = accountNo;
		putUsedField("accountno", this.accountNo);
	}
	/**
	 * @return Returns the accountPropertyOne.
	 */
	public long getAccountPropertyOne()
	{
		return accountPropertyOne;		
	}
	/**
	 * @param accountPropertyOne The accountPropertyOne to set.
	 */
	public void setAccountPropertyOne(long accountPropertyOne)
	{
		this.accountPropertyOne = accountPropertyOne;
		putUsedField("accountpropertyone", this.accountPropertyOne);
	}
	/**
	 * @return Returns the accountPropertyThree.
	 */
	public long getAccountPropertyThree()
	{
		return accountPropertyThree;
	}
	/**
	 * @param accountPropertyThree The accountPropertyThree to set.
	 */
	public void setAccountPropertyThree(long accountPropertyThree)
	{
		this.accountPropertyThree = accountPropertyThree;
		putUsedField("accountpropertythree", this.accountPropertyThree);
	}
	/**
	 * @return Returns the accountPropertyTwo.
	 */
	public long getAccountPropertyTwo()
	{
		return accountPropertyTwo;
	}
	/**
	 * @param accountPropertyTwo The accountPropertyTwo to set.
	 */
	public void setAccountPropertyTwo(long accountPropertyTwo)
	{
		this.accountPropertyTwo = accountPropertyTwo;
		putUsedField("accountpropertytwo", this.accountPropertyTwo);
	}
	/**
	 * @return Returns the accountStatus.
	 */
	public long getAccountStatus()
	{
		return accountStatus;
	}
	/**
	 * @param accountStatus The accountStatus to set.
	 */
	public void setAccountStatus(long accountStatus)
	{
		this.accountStatus = accountStatus;
		putUsedField("accountstatus", this.accountStatus);
	}
	/**
	 * @return Returns the bankId.
	 */
	public long getBankId()
	{
		return bankId;
	}
	/**
	 * @param bankId The bankId to set.
	 */
	public void setBankId(long bankId)
	{
		this.bankId = bankId;
		putUsedField("bankid", this.bankId);
	}
	/**
	 * @return Returns the branchAreaSeg1.
	 */
	public String getBranchAreaSeg1()
	{
		return branchAreaSeg1;
	}
	/**
	 * @param branchAreaSeg1 The branchAreaSeg1 to set.
	 */
	public void setBranchAreaSeg1(String branchAreaSeg1)
	{
		this.branchAreaSeg1 = branchAreaSeg1;
		putUsedField("branchareaseg1", this.branchAreaSeg1);
	}
	/**
	 * @return Returns the branchAreaSeg2.
	 */
	public String getBranchAreaSeg2()
	{
		return branchAreaSeg2;
	}
	/**
	 * @param branchAreaSeg2 The branchAreaSeg2 to set.
	 */
	public void setBranchAreaSeg2(String branchAreaSeg2)
	{
		this.branchAreaSeg2 = branchAreaSeg2;
		putUsedField("branchareaseg2", this.branchAreaSeg2);
	}
	/**
	 * @return Returns the branchAreaSeg3.
	 */
	public String getBranchAreaSeg3()
	{
		return branchAreaSeg3;
	}
	/**
	 * @param branchAreaSeg3 The branchAreaSeg3 to set.
	 */
	public void setBranchAreaSeg3(String branchAreaSeg3)
	{
		this.branchAreaSeg3 = branchAreaSeg3;
		putUsedField("branchareaseg3", this.branchAreaSeg3);
	}
	/**
	 * @return Returns the branchCode.
	 */
	public String getBranchCode()
	{
		return branchCode;
	}
	/**
	 * @param branchCode The branchCode to set.
	 */
	public void setBranchCode(String branchCode)
	{
		this.branchCode = branchCode;
		putUsedField("branchcode", this.branchCode);
	}
	/**
	 * @return Returns the branchName.
	 */
	public String getBranchName()
	{
		return branchName;
	}
	/**
	 * @param branchName The branchName to set.
	 */
	public void setBranchName(String branchName)
	{
		this.branchName = branchName;
		putUsedField("branchname", this.branchName);
	}
	/**
	 * @return Returns the branchSwiftCode.
	 */
	public String getBranchSwiftCode()
	{
		return branchSwiftCode;
	}
	/**
	 * @param branchSwiftCode The branchSwiftCode to set.
	 */
	public void setBranchSwiftCode(String branchSwiftCode)
	{
		this.branchSwiftCode = branchSwiftCode;
		putUsedField("branchswiftcode", this.branchSwiftCode);
	}
	/**
	 * @return Returns the clientId.
	 */
	public long getClientId()
	{
		return clientId;
	}
	/**
	 * @param clientId The clientId to set.
	 */
	public void setClientId(long clientId)
	{
		this.clientId = clientId;
		putUsedField("clientid", this.clientId);
	}
	/**
	 * @return Returns the countryId.
	 */
	public long getCountryId()
	{
		return countryId;
	}
	/**
	 * @param countryId The countryId to set.
	 */
	public void setCountryId(long countryId)
	{
		this.countryId = countryId;
		putUsedField("countryid", this.countryId);
	}
	/**
	 * @return Returns the currencyType.
	 */
	public long getCurrencyType()
	{
		return currencyType;
	}
	/**
	 * @param currencyType The currencyType to set.
	 */
	public void setCurrencyType(long currencyType)
	{
		this.currencyType = currencyType;
		putUsedField("currencytype", this.currencyType);
	}
	/**
	 * @return Returns the id.
	 */
	public long getId()
	{
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id)
	{
		this.id = id;
		putUsedField("id", this.id);
	}
	/**
	 * @return Returns the inputOrOutput.
	 */
	public long getInputOrOutput()
	{
		return inputOrOutput;
	}
	/**
	 * @param inputOrOutput The inputOrOutput to set.
	 */
	public void setInputOrOutput(long inputOrOutput)
	{
		this.inputOrOutput = inputOrOutput;
		putUsedField("inputoroutput", this.inputOrOutput);
	}
	/**
	 * @return Returns the isCheck.
	 */
	public long getIsCheck()
	{
		return isCheck;
	}
	/**
	 * @param isCheck The isCheck to set.
	 */
	public void setIsCheck(long isCheck)
	{
		this.isCheck = isCheck;
		putUsedField("ischeck", this.isCheck);
	}
	/**
	 * @return Returns the isDirectLink.
	 */
	public long getIsDirectLink()
	{
		return isDirectLink;
	}
	/**
	 * @param isDirectLink The isDirectLink to set.
	 */
	public void setIsDirectLink(long isDirectLink)
	{
		this.isDirectLink = isDirectLink;
		putUsedField("isdirectlink", this.isDirectLink);
	}
	/**
	 * @return Returns the ownerType.
	 */
	public long getOwnerType()
	{
		return ownerType;
	}
	/**
	 * @param ownerType The ownerType to set.
	 */
	public void setOwnerType(long ownerType)
	{
		this.ownerType = ownerType;
		putUsedField("ownertype", this.ownerType);
	}
	/**
	 * @return Returns the rdStatus.
	 */
	public long getRdStatus()
	{
		return rdStatus;
	}
	/**
	 * @param rdStatus The rdStatus to set.
	 */
	public void setRdStatus(long rdStatus)
	{
		this.rdStatus = rdStatus;
		putUsedField("rdstatus", this.rdStatus);
	}
	/**
	 * @return Returns the subjectId.
	 */
	public long getSubjectId()
	{
		return subjectId;
	}
	/**
	 * @param subjectId The subjectId to set.
	 */
	public void setSubjectId(long subjectId)
	{
		this.subjectId = subjectId;
		putUsedField("subjectid", this.subjectId);
	}
	/**
	 * @return Returns the upBankAcctId.
	 */
	public long getUpBankAcctId()
	{
		return upBankAcctId;
	}
	/**
	 * @param upBankAcctId The upBankAcctId to set.
	 */
	public void setUpBankAcctId(long upBankAcctId)
	{
		this.upBankAcctId = upBankAcctId;
		putUsedField("upBankAcctId", this.upBankAcctId);
	}
	/**
	 * @return Returns the modifytime.
	 */
	public Date getModifyTime()
	{
		return modifyTime;
	}
	/**
	 * @param modifytime The modifytime to set.
	 */
	public void setModifyTime(Date modifyTime)
	{
		this.modifyTime = modifyTime;
		putUsedField("modifytime", this.modifyTime);
	}
	/**
	 * @return Returns the accountByOpenDate.
	 */
	public String getModifyTimeString()
	{
		return "";
	}
	/**
	 * @param accountByOpenDate The accountByOpenDate to set.
	 */
	public void setModifyTimeString(String modifyTimeString)
	{
		try
		{
			setModifyTime(DataFormat.parseDate(modifyTimeString, DataFormat.FMT_DATE_YYYYMMDD));
		} catch (Exception e)
		{
			e.printStackTrace();
		}	
	}
	public long getAccountType()
	{
		return accountType;
	}
	public void setAccountType(long accountType)
	{
		this.accountType = accountType;
		putUsedField("accountType", this.accountType);
	}
	public long getDeclareClientId()
	{
		return declareClientId;
	}
	public void setDeclareClientId(long declareClientId)
	{
		this.declareClientId = declareClientId;
	}
	
	
	
    /**
     * @return Returns the isDirectionalVirement.
     */
    public long getIsDirectionalVirement() {
        return isDirectionalVirement;
    }
    /**
     * @param isDirectionalVirement The isDirectionalVirement to set.
     */
    public void setIsDirectionalVirement(long isDirectionalVirement) {
        this.isDirectionalVirement = isDirectionalVirement;
        putUsedField("isDirectionalVirement", isDirectionalVirement);
    }
    
	public String getBankName()
	{
		return bankName;
	}
	public void setBankName(String bankName)
	{
		this.bankName = bankName;
	}
	public String getClientName()
	{
		return clientName;
	}
	public void setClientName(String clientName)
	{
		this.clientName = clientName;
	}
	public String getCurrencyName()
	{
		return currencyName;
	}
	public void setCurrencyName(String currencyName)
	{
		this.currencyName = currencyName;
	}
    /**
     * @return Returns the accountPropertyOneName.
     */
    public String getAccountPropertyOneName() {
        return accountPropertyOneName;
    }
    /**
     * @param accountPropertyOneName The accountPropertyOneName to set.
     */
    public void setAccountPropertyOneName(String accountPropertyOneName) {
        this.accountPropertyOneName = accountPropertyOneName;
    }
    /**
     * @return Returns the accountPropertyThreeName.
     */
    public String getAccountPropertyThreeName() {
        return accountPropertyThreeName;
    }
    /**
     * @param accountPropertyThreeName The accountPropertyThreeName to set.
     */
    public void setAccountPropertyThreeName(String accountPropertyThreeName) {
        this.accountPropertyThreeName = accountPropertyThreeName;
    }
    /**
     * @return Returns the accountPropertyTwoName.
     */
    public String getAccountPropertyTwoName() {
        return accountPropertyTwoName;
    }
    /**
     * @param accountPropertyTwoName The accountPropertyTwoName to set.
     */
    public void setAccountPropertyTwoName(String accountPropertyTwoName) {
        this.accountPropertyTwoName = accountPropertyTwoName;
    }
    
    public static final long DEBIT 					= 1; //借
    public static final long CREDIT 					= 2; //贷
    
    private static final String NAME_DEBIT   		= "借";
    private static final String NAME_CREDIT     	= "贷";
    
    public static final String getName(long lCode)
    {
        String strReturn = ""; //初始化返回值
        switch ((int) lCode)
        {
            case (int) DEBIT:
                strReturn = NAME_DEBIT;
                break;
            case (int) CREDIT:
                strReturn = NAME_CREDIT;
                break;
        }
        return strReturn;
    }
}
