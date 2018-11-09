/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) radix(10) lradix(10) 
// Source File Name:   LogonUserBaseBean.java

package com.iss.system.dataentity;

import java.text.*;
import java.util.Date;

// Referenced classes of package com.iss.system.dataentity:
//            ILogonUserBean

public class LogonUserBaseBean
    implements ILogonUserBean
{

    public LogonUserBaseBean()
    {
        m_lSequenceNo = -1L;
        m_lCompanySequenceNo = -1L;
        m_lDepartmentSequenceNo = -1L;
        m_lCostCenterSequenceNo = -1L;
        m_lGender = 1L;
    }

    public long getSequenceNo()
    {
        return m_lSequenceNo;
    }

    public void setSequenceNo(long value)
    {
        m_lSequenceNo = value;
    }

    public String getName()
    {
        return m_strName;
    }

    public void setName(String value)
    {
        m_strName = value;
    }

    public String getSerialNo()
    {
        return m_strSerialNo;
    }

    public void setSerialNo(String value)
    {
        m_strSerialNo = value;
    }

    public String getBirthdayString()
    {
        return m_dtBirthday == null ? "" : MessageFormat.format("{0,date,yyyy-MM-dd}", new Object[] {
            m_dtBirthday
        });
    }

    public Date getBirthday()
    {
        return m_dtBirthday;
    }

    public void setBirthdayString(String value)
    {
        if(value != null && !value.equals(""))
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try
            {
                m_dtBirthday = sdf.parse(value);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void setBirthday(Date birthday)
    {
        m_dtBirthday = birthday;
    }

    public String getEducationLevel()
    {
        return m_strEducationLevel;
    }

    public void setEducationLevel(String value)
    {
        m_strEducationLevel = value;
    }

    public String getAddress()
    {
        return m_strAddress;
    }

    public void setAddress(String value)
    {
        m_strAddress = value;
    }

    public String getPosition()
    {
        return m_strPosition;
    }

    public void setPosition(String value)
    {
        m_strPosition = value;
    }

    public String getEMail()
    {
        return m_strEMail;
    }

    public void setEMail(String value)
    {
        m_strEMail = value;
    }

    public String getOfficePhone()
    {
        return m_strOfficePhone;
    }

    public void setOfficePhone(String value)
    {
        m_strOfficePhone = value;
    }

    public String getPhone()
    {
        return m_strPhone;
    }

    public void setPhone(String value)
    {
        m_strPhone = value;
    }

    public long getCompanySequenceNo()
    {
        return m_lCompanySequenceNo;
    }

    public void setCompanySequenceNo(long value)
    {
        m_lCompanySequenceNo = value;
    }

    public long getDepartmentSequenceNo()
    {
        return m_lDepartmentSequenceNo;
    }

    public void setDepartmentSequenceNo(long value)
    {
        m_lDepartmentSequenceNo = value;
    }

    public long getCostCenterSequenceNo()
    {
        return m_lCostCenterSequenceNo;
    }

    public void setCostCenterSequenceNo(long value)
    {
        m_lCostCenterSequenceNo = value;
    }

    public String getLogon()
    {
        return m_strLogon;
    }

    public void setLogon(String value)
    {
        m_strLogon = value;
    }

    public String getPassword()
    {
        return m_strPassword;
    }

    public String getPasswordHidden()
    {
        String strRetuen = "";
        if(m_strPassword != null)
        {
            for(int i = 0; i < m_strPassword.length(); i++)
                strRetuen = MessageFormat.format(strRetuen + "{0}", new Object[] {
                    "*"
                });

        }
        return strRetuen;
    }

    public void setPassword(String value)
    {
        m_strPassword = value;
    }

    public boolean getIsRowVailable()
    {
        return m_bIsRowVailable;
    }

    public void setIsRowVailable(boolean value)
    {
        m_bIsRowVailable = value;
    }

    public String getCompanyName()
    {
        return m_strCompanyName;
    }

    public void setCompanyName(String value)
    {
        m_strCompanyName = value;
    }

    public String getDepartmentName()
    {
        return m_strDepartmentName;
    }

    public void setDepartmentName(String value)
    {
        m_strDepartmentName = value;
    }

    public String getCostCenterName()
    {
        return m_strCostCenterName;
    }

    public void setCostCenterName(String value)
    {
        m_strCostCenterName = value;
    }

    public long[] getRight()
    {
        return m_lRight;
    }

    public void setRight(long value[])
    {
        m_lRight = value;
    }

    public String getConfirmPassword()
    {
        return m_strConfirmPassword;
    }

    public void setConfirmPassword(String confirmPassword)
    {
        m_strConfirmPassword = confirmPassword;
    }

    public long getGender()
    {
        return m_lGender;
    }

    public void setGender(long gender)
    {
        m_lGender = gender;
    }

    public boolean hasRight(long lRight)
    {
        boolean bHasRight = false;
        if(m_lRight == null)
            return false;
        for(int i = 0; i < m_lRight.length; i++)
        {
            if(lRight != m_lRight[i])
                continue;
            bHasRight = true;
            break;
        }

        return bHasRight;
    }

    public static final String CONFIG = "com.iss.system.iSSMessage_zh";
    public static final long STAFF_GENDER_MALE = 1L;
    public static final String STAFF_GENDER_MALE_KEY = "label.gender.male";
    public static final long STAFF_GENDER_FEMALE = 0L;
    public static final String STAFF_GENDER_FEMALE_KEY = "label.gender.female";
    public static final long STAFF_GENDERS[] = {
        1L, 0
    };
    public static final String STAFF_GENDER_KEYS[] = {
        "label.gender.male", "label.gender.female"
    };
    private long m_lSequenceNo;
    private String m_strName;
    private String m_strSerialNo;
    private String m_strEducationLevel;
    private String m_strAddress;
    private String m_strPosition;
    private String m_strEMail;
    private String m_strOfficePhone;
    private String m_strPhone;
    private long m_lCompanySequenceNo;
    private long m_lDepartmentSequenceNo;
    private long m_lCostCenterSequenceNo;
    private String m_strLogon;
    private String m_strPassword;
    private String m_strConfirmPassword;
    private boolean m_bIsRowVailable;
    private String m_strCompanyName;
    private String m_strDepartmentName;
    private String m_strCostCenterName;
    private Date m_dtBirthday;
    private long m_lGender;
    private long m_lRight[];

}



/***** DECOMPILATION REPORT *****

	DECOMPILED FROM: D:\My Documents\itreasury4.0\lib\iss_framework.jar


	TOTAL TIME: 16 ms


	JAD REPORTED MESSAGES/ERRORS:


	EXIT STATUS:	0


	CAUGHT EXCEPTIONS:

 ********************************/