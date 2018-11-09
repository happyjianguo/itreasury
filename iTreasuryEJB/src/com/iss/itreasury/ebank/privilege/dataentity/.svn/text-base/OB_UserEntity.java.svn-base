// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   OB_UserEntity.java

package com.iss.itreasury.ebank.privilege.dataentity;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;
import java.sql.Timestamp;

public class OB_UserEntity extends ITreasuryBaseDataEntity
{

    private long Id;
    private String name;
    private String login;
    private String passWord;
    private long officeID;
    private long departmentID;
    private long inputUserID;
    private Timestamp inputDate;
    private long isVirtualUser;
    private String currencyID;
    private long statusID;

    public OB_UserEntity()
    {
        Id = -1L;
        name = "";
        login = "";
        passWord = "";
        officeID = -1L;
        departmentID = -1L;
        inputUserID = -1L;
        inputDate = null;
        isVirtualUser = -1L;
        currencyID = "";
        statusID = -1L;
    }

    public String getCurrencyID() 
    {
        return currencyID;
    }

    public void setCurrencyID(String currencyID)
    {
        this.currencyID = currencyID;
        putUsedField("CurrencyID", currencyID);
    }

    public long getDepartmentID()
    {
        return departmentID;
    }

    public void setDepartmentID(long departmentID)
    {
        this.departmentID = departmentID;
        putUsedField("departmentID", departmentID);
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

    public Timestamp getInputDate()
    {
        return inputDate;
    }

    public void setInputDate(Timestamp inputDate)
    {
        this.inputDate = inputDate;
        putUsedField("inputDate", inputDate);
    }

    public long getInputUserID()
    {
        return inputUserID;
    }

    public void setInputUserID(long inputUserID)
    {
        this.inputUserID = inputUserID;
        putUsedField("inputUserID", inputUserID);
    }

    public long getIsVirtualUser()
    {
        return isVirtualUser;
    }

    public void setIsVirtualUser(long isVirtualUser)
    {
        this.isVirtualUser = isVirtualUser;
        putUsedField("isVirtualUser", isVirtualUser);
    }

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
        putUsedField("login", login);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
        putUsedField("name", name);
    }

    public long getOfficeID()
    {
        return officeID;
    }

    public void setOfficeID(long officeID)
    {
        this.officeID = officeID;
        putUsedField("officeID", officeID);
    }

    public String getPassWord()
    {
        return passWord;
    }

    public void setPassWord(String passWord)
    {
        this.passWord = passWord;
        putUsedField("passWord", passWord);
    }

    public long getStatusID()
    {
        return statusID;
    }

    public void setStatusID(long statusID)
    {
        this.statusID = statusID;
        putUsedField("statusID", statusID);
    }
}
