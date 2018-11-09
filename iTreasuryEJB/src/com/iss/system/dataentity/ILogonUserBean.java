/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) radix(10) lradix(10) 
// Source File Name:   ILogonUserBean.java

package com.iss.system.dataentity;

import java.util.Date;

public interface ILogonUserBean
{

    public abstract String getAddress();

    public abstract void setAddress(String s);

    public abstract long getSequenceNo();

    public abstract void setSequenceNo(long l);

    public abstract String getName();

    public abstract void setName(String s);

    public abstract String getSerialNo();

    public abstract Date getBirthday();

    public abstract void setSerialNo(String s);

    public abstract void setBirthday(Date date);

    public abstract String getEducationLevel();

    public abstract void setEducationLevel(String s);

    public abstract String getPosition();

    public abstract void setPosition(String s);

    public abstract String getEMail();

    public abstract void setEMail(String s);

    public abstract String getOfficePhone();

    public abstract void setOfficePhone(String s);

    public abstract String getPhone();

    public abstract void setPhone(String s);

    public abstract long getCompanySequenceNo();

    public abstract void setCompanySequenceNo(long l);

    public abstract long getDepartmentSequenceNo();

    public abstract void setDepartmentSequenceNo(long l);

    public abstract long getCostCenterSequenceNo();

    public abstract void setCostCenterSequenceNo(long l);

    public abstract String getLogon();

    public abstract void setLogon(String s);

    public abstract String getPassword();

    public abstract void setPassword(String s);

    public abstract String getCompanyName();

    public abstract void setCompanyName(String s);

    public abstract String getDepartmentName();

    public abstract void setDepartmentName(String s);

    public abstract String getCostCenterName();

    public abstract void setCostCenterName(String s);

    public abstract long[] getRight();

    public abstract void setRight(long al[]);

    public abstract long getGender();

    public abstract void setGender(long l);

    public abstract boolean hasRight(long l);
}



/***** DECOMPILATION REPORT *****

	DECOMPILED FROM: D:\My Documents\itreasury4.0\lib\iss_framework.jar


	TOTAL TIME: 16 ms


	JAD REPORTED MESSAGES/ERRORS:


	EXIT STATUS:	0


	CAUGHT EXCEPTIONS:

 ********************************/