// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Sys_UserEntity.java

package com.iss.itreasury.ebank.privilege.dataentity;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.ebank.privilege.dao.OB_UserDAO;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;
import java.sql.Timestamp;
import java.util.Collection;

public class OB_UserInfo extends ITreasuryBaseDataEntity
{

   /* ID           NUMBER                         登录人ID（主键） 
    SNAME        VARCHAR2(50)                   登录人名称       
    NCLIENTID    NUMBER                         客户ID           
    NCURRENCYID  NUMBER        Y                币种ID           
    SLOGINNO     VARCHAR2(50)                   登录名           
    SPASSWORD    VARCHAR2(50)  Y                密码             
    NINPUTUSERID NUMBER                         创建人ID         
    DTINPUT      DATE                   Sysdate 创建日期         
    NSTATUS      NUMBER                         状态             
    NSAID        NUMBER        Y                管理员ID         
    SPRVGLEVEL   VARCHAR2(10)  Y                权限级别         
    SCURRENCYID  VARCHAR2(200) Y                                 
    NUSERGROUPID NUMBER        Y                                 
    NOFFICEID    NUMBER        Y       */
    
    private long Id;
    private String sName;
    private String sLoginNo;
    private String sPassword;
    private long nOfficeID;
    private long nDepartmentID;
    private long nInputUserID;
    private Timestamp dtInput;
    private long nIsVirtualUser;
    private long nUserGroupId;
    private long nStatus;
    private String sPrvgLevel;
    private long nSaid;
    private long nClientId;
    private long nCurrencyId;
    private String sCurrencyId;
    private String sCertNo = "";
   
   	//private long  nIsAdminUser = -1; 
 
   	private String sClientCode;
   	private String sClientName;
   	private String sOfficeName;
   	private long lTotalPages;
   	private String sInputUserName;
   	//add by wjliu -2007-4-19增加复核字段:  
   	private long checkUserID;
   	private Timestamp checkDate;
   	private Timestamp dtChangePassword;
   	private long nUserNumber = -1;
   	private String exchangePassword = "";
   	//added by mzh_fu 2008/02/02
   	String[] sAccountNo = null;
   	String sCertCn = "" ;
   	private long isAdmin = -1;
   	private long isBelongToClient = -1;
   	private Timestamp dtModify = null;
 

	public Timestamp getDtModify() {
		return dtModify;
	}
	public void setDtModify(Timestamp dtModify) {
		this.dtModify = dtModify;
		putUsedField("dtModify", dtModify);
	}
	/**
     * @return Returns the inputUserName.
     */
    public String getInputUserName()
    {
        return sInputUserName;
    }
    /**
     * @param inputUserName The inputUserName to set.
     */
    public void setInputUserName(String inputUserName)
    {
        sInputUserName = inputUserName;
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
     * @return 返回 certNo。
     */
    public String getSCertNo()
    {
        return sCertNo;
    }
    /**
     * @param certNo 要设置的 certNo。
     */
    public void setSCertNo(String certNo)
    {
        sCertNo = certNo;
        putUsedField("sCertNo", certNo);
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
    public long getNDepartmentID()
    {
        return nDepartmentID;
    }
    /**
     * @param departmentID The departmentID to set.
     */
    public void setNDepartmentID(long departmentID)
    {
        nDepartmentID = departmentID;
        putUsedField("nDepartmentID", departmentID);
    }
    /**
     * @return Returns the inputUserID.
     */
    public long getNInputUserID()
    {
        return nInputUserID;
    }
    /**
     * @param inputUserID The inputUserID to set.
     */
    public void setNInputUserID(long inputUserID)
    {
        nInputUserID = inputUserID;
        putUsedField("nInputUserID", inputUserID);
    }
    /**
     * @return Returns the isAdminUser.
     */
//    public long getNIsAdminUser()
//    {
//        return nIsAdminUser;
//    }
//    /**
//     * @param isAdminUser The isAdminUser to set.
//     */
//    public void setNIsAdminUser(long isAdminUser)
//    {
//        nIsAdminUser = isAdminUser;
//        putUsedField("nIsAdminUser", isAdminUser);
//    }
    /**
     * @return Returns the isVirtualUser.
     */
    public long getNIsVirtualUser()
    {
        return nIsVirtualUser;
    }
    /**
     * @param isVirtualUser The isVirtualUser to set.
     */
    public void setNIsVirtualUser(long isVirtualUser)
    {
        nIsVirtualUser = isVirtualUser;
        putUsedField("nIsVirtualUser", isVirtualUser);
    }
    /**
     * @return Returns the officeID.
     */
    public long getNOfficeID()
    {
        return nOfficeID;
    }
    /**
     * @param officeID The officeID to set.
     */
    public void setNOfficeID(long officeID)
    {
        nOfficeID = officeID;
        putUsedField("nOfficeID", officeID);
    }
   
  
    /**
     * @return Returns the currencyID.
     */
    public String getSCurrencyId()
    {
        return sCurrencyId;
    }
    /**
     * @param currencyID The currencyID to set.
     */
    public void setSCurrencyId(String currencyID)
    {
        sCurrencyId = currencyID;
        putUsedField("sCurrencyId", currencyID);
    }
    /**
     * @return Returns the loginNo.
     */
    public String getSLoginNo()
    {
        return sLoginNo;
    }
    /**
     * @param loginNo The loginNo to set.
     */
    public void setSLoginNo(String loginNo)
    {
        sLoginNo = loginNo;
        putUsedField("sLoginNo", loginNo);
    }
    /**
     * @return Returns the name.
     */
    public String getSName()
    {
        return sName;
    }
    /**
     * @param name The name to set.
     */
    public void setSName(String name)
    {
        sName = name;
        putUsedField("sName", name);
    }
    /**
     * @return Returns the password.
     */
    public String getSPassword()
    {
        return sPassword;
    }
    /**
     * @param password The password to set.
     */
    public void setSPassword(String password)
    {
        sPassword = password;
        putUsedField("sPassword", password);
    }
    /**
     * @return Returns the clientId.
     */
    public long getNClientId()
    {
        return nClientId;
    }
    /**
     * @param clientId The clientId to set.
     */
    public void setNClientId(long clientId)
    {
        nClientId = clientId;
        putUsedField("nClientId", clientId);
    }
    /**
     * @return Returns the currencyId.
     */
    public long getNCurrencyId()
    {
        return nCurrencyId;
    }
    /**
     * @param currencyId The currencyId to set.
     */
    public void setNCurrencyId(long currencyId)
    {
        nCurrencyId = currencyId;
        putUsedField("nCurrencyId", currencyId);
    }
    /**
     * @return Returns the said.
     */
    public long getNSaid()
    {
        return nSaid;
    }
    /**
     * @param said The said to set.
     */
    public void setNSaid(long said)
    {
        nSaid = said;
        putUsedField("nSaid", said);
    }
    /**
     * @return Returns the status.
     */
    public long getNStatus()
    {
        return nStatus;
    }
    /**
     * @param status The status to set.
     */
    public void setNStatus(long status)
    {
        nStatus = status;
        putUsedField("nStatus", status);
    }
    /**
     * @return Returns the prvgLevel.
     */
    public String getPrvgLevel()
    {
        return sPrvgLevel;
    }
    /**
     * @param prvgLevel The prvgLevel to set.
     */
    public void setPrvgLevel(String prvgLevel)
    {
        sPrvgLevel = prvgLevel;
    }
    /**
     * @return Returns the totalPages.
     */
    public long getTotalPages()
    {
        return lTotalPages;
    }
    /**
     * @param totalPages The totalPages to set.
     */
    public void setTotalPages(long totalPages)
    {
        lTotalPages = totalPages;
    }
    /**
     * @return Returns the clientCode.
     */
    public String getClientCode()
    {
        return sClientCode;
    }
    /**
     * @param clientCode The clientCode to set.
     */
    public void setClientCode(String clientCode)
    {
        sClientCode = clientCode;
    }
    /**
     * @return Returns the clientName.
     */
    public String getClientName()
    {
        return sClientName;
    }
    /**
     * @param clientName The clientName to set.
     */
    public void setClientName(String clientName)
    {
        sClientName = clientName;
    }
    /**
     * @return Returns the officeName.
     */
    public String getOfficeName()
    {
        return sOfficeName;
    }
    /**
     * @param officeName The officeName to set.
     */
    public void setOfficeName(String officeName)
    {
        sOfficeName = officeName;
    }
	/**
	 * @return Returns the nUserGroupId.
	 */
	public long getUserGroupId() {
		return nUserGroupId;
	}
	/**
	 * @param userGroupId The nUserGroupId to set.
	 */
	public void setUserGroupId(long userGroupId) {
		nUserGroupId = userGroupId;
	}
    public static void main(String[] argv)
    {
       
    }
	public Timestamp getCheckDate() {
		return checkDate;
	}
	public long getCheckUserID() {
		return checkUserID;
		
	}
	public void setCheckDate(Timestamp checkDate) {
		this.checkDate = checkDate;
		putUsedField("checkDate", checkDate);
	}
	public void setCheckUserID(long checkUserID) {
		this.checkUserID = checkUserID;
		putUsedField("checkUserID", checkUserID);
	}
    public Timestamp getDtChangePassword() {
		return dtChangePassword;
	}
	public void setDtChangePassword(Timestamp dtChangePassword) {
		this.dtChangePassword = dtChangePassword;
		putUsedField("dtChangePassword", dtChangePassword);
	}
	public String[] getSAccountNo() {
		return sAccountNo;
	}
	public void setSAccountNo(String[] accountNo) {
		sAccountNo = accountNo;
	}
	public String getSCertCn() {
		return sCertCn;
	}
	public void setSCertCn(String certCn) {
		sCertCn = certCn;
		putUsedField("sCertCn", certCn);
	}
	public long getNUserNumber() {
		return nUserNumber;
	}
	public void setNUserNumber(long userNumber) {
		nUserNumber = userNumber;
		putUsedField("nUserNumber", nUserNumber);
	}
	public String getExchangePassword() {
		return exchangePassword;
	}
	public void setExchangePassword(String exchangePassword) {
		this.exchangePassword = exchangePassword;
		putUsedField("exchangePassword", exchangePassword);
	}
	public long getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(long isAdmin) {
		this.isAdmin = isAdmin;
	}
	public long getIsBelongToClient() {
		return isBelongToClient;
	}
	public void setIsBelongToClient(long isBelongToClient) {
		this.isBelongToClient = isBelongToClient;
	}

}
