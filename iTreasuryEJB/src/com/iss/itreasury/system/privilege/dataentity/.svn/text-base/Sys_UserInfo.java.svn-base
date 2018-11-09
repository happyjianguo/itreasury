// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Sys_UserEntity.java

package com.iss.itreasury.system.privilege.dataentity;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;
import java.sql.Timestamp;

public class Sys_UserInfo extends ITreasuryBaseDataEntity
{


    private long Id;
    private String sName="";
    private String sCode="";
    private String sLoginNo="";
    private String sPassword="";
    private long nOfficeID;//所属办事处id
    private long authorizedOfficeID = -1; //授权办事处id
    private long nDepartmentID;
    private long nInputUserID;
    private Timestamp dtInput;
    private long nIsVirtualUser;
    private String sCurrencyID;
    private long nStatusID = -1;
    private String sCertNo = "";
    private String sCertCn = "";    //added by mzh_fu 2008/01/30　证书ＣＮ
    private String inputuserName ="";
    private long flag=0; //  add by zhanglei  2010-07-23
    private long nusergroupid = -1;
    
   	private long  nIsAdminUser = -1;
   	
   	private long nCheckUserID;    //复核人
    private Timestamp dtCheck;   //复核日期
   	
   	private Timestamp dtChangePassword;
   	//Added by zwsun, 2007-06-12, 权限分离，增加权限字段
   	private long purviewType;
   	
   	
   	//Added by jiangqi, 2011-01-24, 权限分离，增加权限字段,是否是指纹管理员
   	private long fingerPrintType;
   	
	public long getFingerPrintType() {
		return fingerPrintType;
	}

	public void setFingerPrintType(long fingerPrintType) {
		this.fingerPrintType = fingerPrintType;
		putUsedField("fingerPrintType", fingerPrintType);
	}
	
	public long getNusergroupid() {
		return nusergroupid;
	}

	public void setNusergroupid(long nusergroupid) {
		this.nusergroupid = nusergroupid;
		putUsedField("nusergroupid", nusergroupid);
	}

	public Timestamp getChangePassword() 
    {
		return dtChangePassword;
	}

	public void setChangePassword(Timestamp dtChangePassword) 
	{
		this.dtChangePassword = dtChangePassword;
		putUsedField("dtChangePassword", dtChangePassword);
	}

	public long getId()
    {
        return Id;
    }

    public void setId(long id)
    {
        Id = id;
        putUsedField("Id", id);
    }


    
    /**
     * @return Returns the input.
     */
    public Timestamp getInput()
    {
        return dtInput;
    }
    /**
     * @param input The input to set.
     */
    public void setInput(Timestamp input)
    {
        dtInput = input;
        putUsedField("dtInput", input);
    }
    /**
     * @return Returns the departmentID.
     */
    public long getDepartmentID()
    {
        return nDepartmentID;
    }
    /**
     * @param departmentID The departmentID to set.
     */
    public void setDepartmentID(long departmentID)
    {
        nDepartmentID = departmentID;
        putUsedField("nDepartmentID", departmentID);
    }
    //Added by zwsun, 2007-06-12, 权限分离
    public void setPurviewType(long purviewType){
    	this.purviewType=purviewType;
    	putUsedField("purviewType",purviewType);
    }
    public long getPurviewType(){
    	return purviewType;
    }
    
    /**
     * @return Returns the inputUserID.
     */
    public long getInputUserID()
    {
        return nInputUserID;
    }
    /**
     * @param inputUserID The inputUserID to set.
     */
    public void setInputUserID(long inputUserID)
    {
        nInputUserID = inputUserID;
        putUsedField("nInputUserID", inputUserID);
    }
    /**
     * @return Returns the isAdminUser.
     */
    public long getIsAdminUser()
    {
        return nIsAdminUser;
    }
    /**
     * @param isAdminUser The isAdminUser to set.
     */
    public void setIsAdminUser(long isAdminUser)
    {
        nIsAdminUser = isAdminUser;
        putUsedField("nIsAdminUser", isAdminUser);
    }
    /**
     * @return Returns the isVirtualUser.
     */
    public long getIsVirtualUser()
    {
        return nIsVirtualUser;
    }
    /**
     * @param isVirtualUser The isVirtualUser to set.
     */
    public void setIsVirtualUser(long isVirtualUser)
    {
        nIsVirtualUser = isVirtualUser;
        putUsedField("nIsVirtualUser", isVirtualUser);
    }
    /**
     * @return Returns the officeID.
     */
    public long getOfficeID()
    {
        return nOfficeID;
    }
    /**
     * @param officeID The officeID to set.
     */
    public void setOfficeID(long officeID)
    {
        nOfficeID = officeID;
        putUsedField("nOfficeID", officeID);
    }
    /**
     * @return Returns the statusID.
     */
    public long getStatusID()
    {
        return nStatusID;
    }
    /**
     * @param statusID The statusID to set.
     */
    public void setStatusID(long statusID)
    {
        nStatusID = statusID;
        putUsedField("nStatusID", statusID);
    }
    /**
     * @return Returns the currencyID.
     */
    public String getCurrencyID()
    {
        return sCurrencyID;
    }
    /**
     * @param currencyID The currencyID to set.
     */
    public void setCurrencyID(String currencyID)
    {
        sCurrencyID = currencyID;
        putUsedField("sCurrencyID", currencyID);
    }
    /**
     * @return Returns the loginNo.
     */
    public String getLoginNo()
    {
        return sLoginNo;
    }
    /**
     * @return 返回 certNo。
     */
    public String getCertNo()
    {
        return sCertNo;
    }
    /**
     * @param certNo 要设置的 certNo。
     */
    public void setCertNo(String certNo)
    {
        sCertNo = certNo;
        putUsedField("sCertNo", certNo);
    }
    /**
     * @param loginNo The loginNo to set.
     */
    public void setLoginNo(String loginNo)
    {
        sLoginNo = loginNo;
        putUsedField("sLoginNo", loginNo);
    }
    /**
     * @return Returns the name.
     */
    public String getName()
    {
        return sName;
    }
    /**
     * @param name The name to set.
     */
    public void setName(String name)
    {
        sName = name;
        putUsedField("sName", name);
    }
    /**
     * @return Returns the password.
     */
    public String getPassword()
    {
        return sPassword;
    }
    /**
     * @param password The password to set.
     */
    public void setPassword(String password)
    {
        sPassword = password;
        putUsedField("sPassword", password);
    }
    
	/**
	 * @return Returns the sCode.
	 */
	public String getCode() {
		return sCode;
	}
	/**
	 * @param code The sCode to set.
	 */
	public void setCode(String code) 
	{
		sCode = code;
		putUsedField("sCode", code);
	}

	public String getInputuserName() {
		return inputuserName;
	}

	public void setInputuserName(String inputuserName) {
		this.inputuserName = inputuserName;
	}

	public Timestamp getCheck() {
		return dtCheck;
	}
	public void setCheck(Timestamp dtCheck) {
		this.dtCheck = dtCheck;
		 putUsedField("dtCheck", dtCheck);
	}
	public long getCheckUserID() {
		return nCheckUserID;
	}
	public void setCheckUserID(long checkUserID) {
		this.nCheckUserID = checkUserID;
		putUsedField("CheckUserID", nCheckUserID);
	}

    /**
     * @return 返回 sCertCn。
     */
    public String getCertCn()
    {
        return sCertCn;
    }
    /**
     * @param certCn 要设置的 sCertCn。
     */
    public void setCertCn(String certCn)
    {
    	sCertCn = certCn;
        putUsedField("sCertCn", certCn);
    }

	public long getFlag() {
		return flag;
	}

	public void setFlag(long flag) {
		this.flag = flag;
		putUsedField("flag",flag);
	}

	public long getAuthorizedOfficeID() {
		return authorizedOfficeID;
	}

	public void setAuthorizedOfficeID(long authorizedOfficeID) {
		this.authorizedOfficeID = authorizedOfficeID;
		putUsedField("authorizedOfficeID",authorizedOfficeID);
	}
}
