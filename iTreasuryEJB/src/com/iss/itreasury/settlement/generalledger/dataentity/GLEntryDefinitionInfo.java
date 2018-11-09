/*
 * Created on 2003-9-11
 *
 */
package com.iss.itreasury.settlement.generalledger.dataentity;

import java.io.*;

import com.iss.itreasury.settlement.util.*;

/**
 * 
 * 分录设置定义
 * @author yqwu
 * 
 */
public class GLEntryDefinitionInfo implements Serializable
{
    long ID=-1;

    long OfficeID=-1; //机构ID
    long CurrencyID=-1; //币种ID 
    long TransactionType=-1; //交易类型
	long SubTransactionType = 0; //交易子类型    
    long CapitalType=-1; //资金流向
    long EntryType=-1; //分录类型
    long TransDirection=-1; //借贷方向
    long SubjectType=-1; //科目类型
    String SubjectCode=""; //科目号
    long AmountDirection=-1; //金额方向
    long AmountType = -1; //金额类型
    long OfficeType = -1; //机构类型

    
    //辅助属性
    String CurrencyName=""; //币种
    String TransactionTypeName=""; //交易类型名称
	String SubTransactionTypeName=""; //交易子类型名称    
    String CapitalTypeName=""; //资金流向名称
    String EntryTypeName=""; //分录类型名称
    String DirectionName=""; //借贷方向名称
    String SubjectTypeName=""; //科目类型名称
    String AmountDirectionName=""; //金额方向名称
    String AmountTypeName=""; //金额类型
    String OfficeTypeName=""; //机构类型
    /**
     * @return
     */
    public long getAmountDirection()
    {
        return AmountDirection;
    }

    /**
     * @return
     */
    public long getAmountType()
    {
        return AmountType;
    }

    /**
     * @return
     */
    public long getCapitalType()
    {
        return CapitalType;
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
    public long getDirection()
    {
        return TransDirection;
    }

    /**
     * @return
     */
    public long getEntryType()
    {
        return EntryType;
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
     * @return
     */
    public String getSubjectCode()
    {
        return SubjectCode;
    }

    /**
     * @return
     */
    public long getSubjectType()
    {
        return SubjectType;
    }

    /**
     * @return
     */
    public long getTransactionType()
    {
        return TransactionType;
    }

    /**
     * @param l
     */
    public void setAmountDirection(long l)
    {
        AmountDirection = l;
        this.AmountDirectionName = SETTConstant.AmountDirection.getName(l);
    }

    /**
     * @param l
     */
    public void setAmountType(long l)
    {
        AmountType = l;
        this.AmountTypeName=SETTConstant.AmountType.getName(l);
    }

    /**
     * @param l
     */
    public void setCapitalType(long l)
    {
        CapitalType = l;
        this.CapitalTypeName = SETTConstant.CapitalType.getName(l);

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
     * @param l
     */
    public void setDirection(long l)
    {
		TransDirection = l;
        this.DirectionName = SETTConstant.DebitOrCredit.getName(l);
    }

    /**
     * @param l
     */
    public void setEntryType(long l)
    {
        EntryType = l;
        this.EntryTypeName = SETTConstant.EntryType.getName(l);
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
    }

    /**
     * @param l
     */
    public void setSubjectCode(String l)
    {
        SubjectCode = l;
    }

    /**
     * @param l
     */
    public void setSubjectType(long l)
    {
        SubjectType = l;
        this.SubjectTypeName=SETTConstant.EntrySubjectType.getName(l);
    }

    /**
     * @param l
     */
    public void setTransactionType(long l)
    {
        TransactionType = l;
        this.TransactionTypeName = SETTConstant.TransactionType.getName(l);
    }

    /**
     * @return
     */
    public String getAmountDirectionName()
    {
        return AmountDirectionName;
    }

    /**
     * @return
     */
    public String getAmountTypeName()
    {
        return AmountTypeName;
    }

    /**
     * @return
     */
    public String getCapitalTypeName()
    {
        return CapitalTypeName;
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
    public String getDirectionName()
    {
        return DirectionName;
    }

    /**
     * @return
     */
    public String getEntryTypeName()
    {
        return EntryTypeName;
    }

    /**
     * @return
     */
    public String getSubjectTypeName()
    {
        return SubjectTypeName;
    }

    /**
     * @return
     */
    public String getTransactionTypeName()
    {
        return TransactionTypeName;
    }


	/**
	 * Returns the subTransactionType.
	 * @return long
	 */
	public long getSubTransactionType() {
		return SubTransactionType;
	}

	/**
	 * Returns the subTransactionTypeName.
	 * @return String
	 */
	public String getSubTransactionTypeName() {
		return SubTransactionTypeName;
	}

	/**
	 * Sets the subTransactionType.
	 * @param subTransactionType The subTransactionType to set
	 */
	public void setSubTransactionType(long subTransactionType) {
		SubTransactionType = subTransactionType;
		SubTransactionTypeName = SETTConstant.SubTransactionType.getName(subTransactionType); 
	}

	/**
	 * Sets the subTransactionTypeName.
	 * @param subTransactionTypeName The subTransactionTypeName to set
	 */
	public void setSubTransactionTypeName(String subTransactionTypeName) {
		SubTransactionTypeName = subTransactionTypeName;
	}

	public long getOfficeType() {
		return OfficeType;
	}

	public void setOfficeType(long officeType) {
		OfficeType = officeType;
		OfficeTypeName = SETTConstant.OfficeType.getName(officeType); 
	}

	public String getOfficeTypeName() {
		return OfficeTypeName;
	}

}
