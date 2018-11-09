/*
 * Created on 2003-9-26
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.loan.loanapply.dataentity;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
import java.io.Serializable;
public class AssureInfo implements Serializable
{
    /*
    1   ID  NUMBER  Y    
    2   "nLoanID"   NUMBER  Y   贷款ID(FK_reference_loan_LoanForm_ID)
    3   "nAssureTypeID" NUMBER  Y   保证类型
    4   "nFillQuestionary"  NUMBER      是否填写调查表
    5   "nClientID" NUMBER  Y   担保客户
    6   "mAmount"   NUMBER(21,6)        金额
    7   "sImpawName"    VARCHAR2(100)       质押动产名称
    8   "sImpawQuality" VARCHAR2(100)       质押数量
    9   "sImpawStatus"  VARCHAR2(100)       质押状况
    10  "mPledGeAmount" NUMBER(21,6)        抵押财产总价
    11  "mPledgeRate"   NUMBER(15,12)       抵押率
    12  "nStatusID" NUMBER  Y   状态
    13  "sAssureCode"   VARCHAR2(100)       保证编码
    */
    private long    ID=-1;
    private long    loanID=-1;
    private long    assureTypeID=-1;
    private long    fillQuestionary=-1;
    private long    clientID=-1;
    private double  amount=0;
    private String  impawName="";
    private String  impawQuality="";
    private String  impawStatus="";
    private double  pledgeAmount=0;
    private double  pledgeRate=0;
    private long    statusID=-1;
    private String  assureCode="";
    private long    isTopAssure=2;	//是否为最高额担保
    
    
    private long nimpawType=-1;
    
    private String clientName="";
    private String clientCode="";
    private String clientPhone="";
    private String clientContacter="";
    private String clientFax="";
    private String clientProvince="";
    private String clientCity="";
    private String clientAddress="";
    private String clientBank1="";
    private String clientBankAccount1="";
    //added by xiong fei 2010/05/24 融资租赁新增回购条件，下面同时生成get,set方法
    private String repurchaseCondition = "";
    
    private long clientType = -1;  //客户类型
    
    
    public long getClientType() {
		return clientType;
	}
	public void setClientType(long clientType) {
		this.clientType = clientType;
	}
	public long getNimpawType()
    {
    return 	nimpawType;
    }
    public void setNimpawType(long nimpawType)
    {
    	this.nimpawType=nimpawType;
    }
    /**
     * @return
     */
    public double getAmount()
    {
        return amount;
    }

    /**
     * @return
     */
    public String getAssureCode()
    {
        return assureCode;
    }

    /**
     * @return
     */
    public long getAssureTypeID()
    {
        return assureTypeID;
    }

    /**
     * @return
     */
    public long getClientID()
    {
        return clientID;
    }

    /**
     * @return
     */
    public long getFillQuestionary()
    {
        return fillQuestionary;
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
    public String getImpawName()
    {
        return impawName;
    }

    /**
     * @return
     */
    public String getImpawQuality()
    {
        return impawQuality;
    }

    /**
     * @return
     */
    public String getImpawStatus()
    {
        return impawStatus;
    }

    /**
     * @return
     */
    public long getLoanID()
    {
        return loanID;
    }

    /**
     * @return
     */
    public double getPledgeAmount()
    {
        return pledgeAmount;
    }

    /**
     * @return
     */
    public double getPledgeRate()
    {
        return pledgeRate;
    }

    /**
     * @return
     */
    public long getStatusID()
    {
        return statusID;
    }

    /**
     * @param d
     */
    public void setAmount(double d)
    {
        amount = d;
    }

    /**
     * @param string
     */
    public void setAssureCode(String string)
    {
        assureCode = string;
    }

    /**
     * @param l
     */
    public void setAssureTypeID(long l)
    {
        assureTypeID = l;
    }

    /**
     * @param l
     */
    public void setClientID(long l)
    {
        clientID = l;
    }

    /**
     * @param l
     */
    public void setFillQuestionary(long l)
    {
        fillQuestionary = l;
    }

    /**
     * @param l
     */
    public void setID(long l)
    {
        ID = l;
    }

    /**
     * @param string
     */
    public void setImpawName(String string)
    {
        impawName = string;
    }

    /**
     * @param string
     */
    public void setImpawQuality(String string)
    {
        impawQuality = string;
    }

    /**
     * @param string
     */
    public void setImpawStatus(String string)
    {
        impawStatus = string;
    }

    /**
     * @param l
     */
    public void setLoanID(long l)
    {
        loanID = l;
    }

    /**
     * @param d
     */
    public void setPledgeAmount(double d)
    {
        pledgeAmount = d;
    }

    /**
     * @param d
     */
    public void setPledgeRate(double d)
    {
        pledgeRate = d;
    }

    /**
     * @param l
     */
    public void setStatusID(long l)
    {
        statusID = l;
    }

    /**
     * @return
     */
    public String getClientCode()
    {
        return clientCode;
    }

    /**
     * @return
     */
    public String getClientContacter()
    {
        return clientContacter;
    }

    /**
     * @return
     */
    public String getClientFax()
    {
        return clientFax;
    }

    /**
     * @return
     */
    public String getClientName()
    {
        return clientName;
    }

    /**
     * @return
     */
    public String getClientPhone()
    {
        return clientPhone;
    }

    /**
     * @param string
     */
    public void setClientCode(String string)
    {
        clientCode = string;
    }

    /**
     * @param string
     */
    public void setClientContacter(String string)
    {
        clientContacter = string;
    }

    /**
     * @param string
     */
    public void setClientFax(String string)
    {
        clientFax = string;
    }

    /**
     * @param string
     */
    public void setClientName(String string)
    {
        clientName = string;
    }

    /**
     * @param string
     */
    public void setClientPhone(String string)
    {
        clientPhone = string;
    }

	/**
	 * @return
	 */
	public String getClientAddress()
	{
		return clientAddress;
	}

	/**
	 * @return
	 */
	public String getClientCity()
	{
		return clientCity;
	}

	/**
	 * @return
	 */
	public String getClientProvince()
	{
		return clientProvince;
	}

	/**
	 * @param string
	 */
	public void setClientAddress(String string)
	{
		clientAddress = string;
	}

	/**
	 * @param string
	 */
	public void setClientCity(String string)
	{
		clientCity = string;
	}

	/**
	 * @param string
	 */
	public void setClientProvince(String string)
	{
		clientProvince = string;
	}

	/**
	 * @return
	 */
	public String getClientBank1()
	{
		return clientBank1;
	}

	/**
	 * @return
	 */
	public String getClientBankAccount1()
	{
		return clientBankAccount1;
	}

	/**
	 * @param string
	 */
	public void setClientBank1(String string)
	{
		clientBank1 = string;
	}

	/**
	 * @param string
	 */
	public void setClientBankAccount1(String string)
	{
		clientBankAccount1 = string;
	}

    /**
     * @return Returns the isTopAssure.
     */
    public long getIsTopAssure()
    {
        return isTopAssure;
    }
    /**
     * @param isTopAssure The isTopAssure to set.
     */
    public void setIsTopAssure(long isTopAssure)
    {
        this.isTopAssure = isTopAssure;
    }
	public String getRepurchaseCondition() {
		return repurchaseCondition;
	}
	public void setRepurchaseCondition(String repurchaseCondition) {
		this.repurchaseCondition = repurchaseCondition;
	}
}
