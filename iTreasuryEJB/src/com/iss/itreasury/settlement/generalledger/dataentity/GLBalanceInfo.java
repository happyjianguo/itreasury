/*
 * Created on 2003-9-12
 *
 */
package com.iss.itreasury.settlement.generalledger.dataentity;

import java.sql.Timestamp;
import com.iss.itreasury.settlement.util.*;
import java.io.*;

/**
 *
 *  �������
 *  @author yqwu
 *
 */
public class GLBalanceInfo  implements Serializable
{
    long ID = -1;

    Timestamp GLDate = null; //�������
    long OfficeID = -1; //������ID
    long CurrencyID = -1; //���ֺ�
    String GLSubjectCode = ""; //��Ŀ����
    long BalanceDirection = -1; //��ǰ����
    double DebitBalance = 0.0; //���ս跽���
    double CreditBalance = 0.0; //���մ������
    double DebitAmount = 0.0; //�����ۼƽ跽������
    double CreditAmount = 0.0; //�����ۼƴ���������
    long DebitNumber = -1; //�����ۼƽ跽����
    long CreditNumber = -1; //�����ۼƴ�������
    long TransDirection = -1;

    //��������
    String OfficeName; //��������
    String CurrencyName; //��������
    String BalanceDirectionName; //��ǰ��������

    /**
     * @return
     */
    public long getBalanceDirection()
    {
        return BalanceDirection;
    }

    /**
     * @return
     */
    public double getCreditAmount()
    {
        return CreditAmount;
    }

    /**
     * @return
     */
    public double getCreditBalance()
    {
        return CreditBalance;
    }

    /**
     * @return
     */
    public long getCreditNumber()
    {
        return CreditNumber;
    }

    /**
     * @return
     */
    public long getCurrencyID()
    {
        return CurrencyID;
    }

    /**
     * @return
     */
    public double getDebitAmount()
    {
        return DebitAmount;
    }

    /**
     * @return
     */
    public double getDebitBalance()
    {
        return DebitBalance;
    }

    /**
     * @return
     */
    public long getDebitNumber()
    {
        return DebitNumber;
    }

    /**
     * @return
     */
    public Timestamp getGLDate()
    {
        return GLDate;
    }

    /**
     * @return
     */
    public String getGLSubjectCode()
    {
        return GLSubjectCode;
    }

    /**
     * @return
     */
    public long getID()
    {
        return ID;
    }

    /**
     * @return
     */
    public long getOfficeID()
    {
        return OfficeID;
    }

    /**
     * @param l
     */
    public void setBalanceDirection(long l)
    {
        BalanceDirection = l;
        this.BalanceDirectionName =
            SETTConstant.DebitOrCredit.getName(l);
    }

    /**
     * @param d
     */
    public void setCreditAmount(double d)
    {
        CreditAmount = d;
    }

    /**
     * @param d
     */
    public void setCreditBalance(double d)
    {
        CreditBalance = d;
    }

    /**
     * @param l
     */
    public void setCreditNumber(long l)
    {
        CreditNumber = l;
    }

    /**
     * @param l
     */
    public void setCurrencyID(long l)
    {
        CurrencyID = l;
        this.CurrencyName = SETTConstant.CurrencyType.getName(l);
    }

    /**
     * @param d
     */
    public void setDebitAmount(double d)
    {
        DebitAmount = d;
    }

    /**
     * @param d
     */
    public void setDebitBalance(double d)
    {
        DebitBalance = d;
    }

    /**
     * @param l
     */
    public void setDebitNumber(long l)
    {
        DebitNumber = l;
    }

    /**
     * @param date
     */
    public void setGLDate(Timestamp date)
    {
        GLDate = date;
    }

    /**
     * @param string
     */
    public void setGLSubjectCode(String string)
    {
        GLSubjectCode = string;
    }

    /**
     * @param l
     */
    public void setID(long l)
    {
        ID = l;
    }

    /**
     * @param l
     */
    public void setOfficeID(long l)
    {
        OfficeID = l;

        //��NameRef��Ӧ����ȡ��Exception��ȡ��try block
        try
        {
            NameRef.getOfficeNameByID(l);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @return
     */
    public String getBalanceDirectionName()
    {
        return BalanceDirectionName;
    }

    /**
     * @return
     */
    public String getCurrencyName()
    {
        return CurrencyName;
    }

    /**
     * @return
     */
    public String getOfficeName()
    {
        return OfficeName;
    }

	/**
	 * Returns the transDirection.
	 * @return long
	 */
	public long getTransDirection() {
		return TransDirection;
	}

	/**
	 * Sets the transDirection.
	 * @param transDirection The transDirection to set
	 */
	public void setTransDirection(long transDirection) {
		TransDirection = transDirection;
	}

}
