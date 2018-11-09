/*
 * Created on 2004-12-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.transdiscount.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author ygzhao
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DiscountBillInfo extends ITreasuryBaseDataEntity implements
        Serializable
{
    private long Id = -1;//
    private long nContractID = -1;//合同ID
    private long nSerialNo = -1;//序列号
    private String sUserName = "";//原始出票人
    private String sBank = "";//承兑银行
    private long nIsLocal = -1;//是否本地
    private Timestamp dtCreate = null;//出票日
    private Timestamp dtEnd = null;//到期日
    private String sCode = "";//汇票号码
    private double mAmount = 0.0;//汇票金额
    private long nStatusID = -1;//状态（是否删除）
    private long nAddDays = -1;//节假日增加计息天数
    private long nDiscountCredenceid = -1;//贴现凭证ID(FK_reference_loan_DiscountCredence_ID)
    private long ob_nDiscountCredenceID = -1;//网银的贴现凭证ID
    private long nAcceptpotypeID = -1;//票据类型
    private String sFormerOwner = "";//贴现单位直接前手
    private double mCheckAmount = 0.0;//汇票实付金额
    private long nBillSourceTypeID = -1;//
    private long nIsCanSell = -1;//
    private Timestamp repurchaseDate = null;//
    private long repurchaseTerm = -1;//
    private long nSellStatusID = -1;//
    
    /**
     * @return Returns the dtCreate.
     */
    public Timestamp getDtCreate()
    {
        return dtCreate;
    }
    /**
     * @param dtCreate The dtCreate to set.
     */
    public void setDtCreate(Timestamp dtCreate)
    {
        this.dtCreate = dtCreate;
    }
    /**
     * @return Returns the dtEnd.
     */
    public Timestamp getDtEnd()
    {
        return dtEnd;
    }
    /**
     * @param dtEnd The dtEnd to set.
     */
    public void setDtEnd(Timestamp dtEnd)
    {
        this.dtEnd = dtEnd;
    }
    /**
     * @return Returns the iD.
     */
    public long getId()
    {
        return Id;
    }
    /**
     * @param id The iD to set.
     */
    public void setId(long id)
    {
        Id = id;
    }
    /**
     * @return Returns the mAmount.
     */
    public double getMAmount()
    {
        return mAmount;
    }
    /**
     * @param amount The mAmount to set.
     */
    public void setMAmount(double amount)
    {
        mAmount = amount;
    }
    /**
     * @return Returns the mCheckAmount.
     */
    public double getMCheckAmount()
    {
        return mCheckAmount;
    }
    /**
     * @param checkAmount The mCheckAmount to set.
     */
    public void setMCheckAmount(double checkAmount)
    {
        mCheckAmount = checkAmount;
    }
    /**
     * @return Returns the nAcceptpotypeID.
     */
    public long getNAcceptpotypeID()
    {
        return nAcceptpotypeID;
    }
    /**
     * @param acceptpotypeID The nAcceptpotypeID to set.
     */
    public void setNAcceptpotypeID(long acceptpotypeID)
    {
        nAcceptpotypeID = acceptpotypeID;
    }
    /**
     * @return Returns the nAddDays.
     */
    public long getNAddDays()
    {
        return nAddDays;
    }
    /**
     * @param addDays The nAddDays to set.
     */
    public void setNAddDays(long addDays)
    {
        nAddDays = addDays;
    }
    /**
     * @return Returns the nBillSourceTypeID.
     */
    public long getNBillSourceTypeID()
    {
        return nBillSourceTypeID;
    }
    /**
     * @param billSourceTypeID The nBillSourceTypeID to set.
     */
    public void setNBillSourceTypeID(long billSourceTypeID)
    {
        nBillSourceTypeID = billSourceTypeID;
    }
    /**
     * @return Returns the nContractID.
     */
    public long getNContractID()
    {
        return nContractID;
    }
    /**
     * @param contractID The nContractID to set.
     */
    public void setNContractID(long contractID)
    {
        nContractID = contractID;
    }
    /**
     * @return Returns the nDiscountCredenceid.
     */
    public long getNDiscountCredenceid()
    {
        return nDiscountCredenceid;
    }
    /**
     * @param discountCredenceid The nDiscountCredenceid to set.
     */
    public void setNDiscountCredenceid(long discountCredenceid)
    {
        nDiscountCredenceid = discountCredenceid;
    }
    /**
     * @return Returns the nIsCanSell.
     */
    public long getNIsCanSell()
    {
        return nIsCanSell;
    }
    /**
     * @param isCanSell The nIsCanSell to set.
     */
    public void setNIsCanSell(long isCanSell)
    {
        nIsCanSell = isCanSell;
    }
    /**
     * @return Returns the nIsLocal.
     */
    public long getNIsLocal()
    {
        return nIsLocal;
    }
    /**
     * @param isLocal The nIsLocal to set.
     */
    public void setNIsLocal(long isLocal)
    {
        nIsLocal = isLocal;
    }
    /**
     * @return Returns the nSellStatusID.
     */
    public long getNSellStatusID()
    {
        return nSellStatusID;
    }
    /**
     * @param sellStatusID The nSellStatusID to set.
     */
    public void setNSellStatusID(long sellStatusID)
    {
        nSellStatusID = sellStatusID;
    }
    /**
     * @return Returns the nSerialNo.
     */
    public long getNSerialNo()
    {
        return nSerialNo;
    }
    /**
     * @param serialNo The nSerialNo to set.
     */
    public void setNSerialNo(long serialNo)
    {
        nSerialNo = serialNo;
    }
    /**
     * @return Returns the nStatusID.
     */
    public long getNStatusID()
    {
        return nStatusID;
    }
    /**
     * @param statusID The nStatusID to set.
     */
    public void setNStatusID(long statusID)
    {
        nStatusID = statusID;
    }
    /**
     * @return Returns the ob_nDiscountCredenceID.
     */
    public long getOb_nDiscountCredenceID()
    {
        return ob_nDiscountCredenceID;
    }
    /**
     * @param ob_nDiscountCredenceID The ob_nDiscountCredenceID to set.
     */
    public void setOb_nDiscountCredenceID(long ob_nDiscountCredenceID)
    {
        this.ob_nDiscountCredenceID = ob_nDiscountCredenceID;
    }
    /**
     * @return Returns the repurchaseDate.
     */
    public Timestamp getRepurchaseDate()
    {
        return repurchaseDate;
    }
    /**
     * @param repurchaseDate The repurchaseDate to set.
     */
    public void setRepurchaseDate(Timestamp repurchaseDate)
    {
        this.repurchaseDate = repurchaseDate;
    }
    /**
     * @return Returns the repurchaseTerm.
     */
    public long getRepurchaseTerm()
    {
        return repurchaseTerm;
    }
    /**
     * @param repurchaseTerm The repurchaseTerm to set.
     */
    public void setRepurchaseTerm(long repurchaseTerm)
    {
        this.repurchaseTerm = repurchaseTerm;
    }
    /**
     * @return Returns the sBank.
     */
    public String getSBank()
    {
        return sBank;
    }
    /**
     * @param bank The sBank to set.
     */
    public void setSBank(String bank)
    {
        sBank = bank;
    }
    /**
     * @return Returns the sCode.
     */
    public String getSCode()
    {
        return sCode;
    }
    /**
     * @param code The sCode to set.
     */
    public void setSCode(String code)
    {
        sCode = code;
    }
    /**
     * @return Returns the sFormerOwner.
     */
    public String getSFormerOwner()
    {
        return sFormerOwner;
    }
    /**
     * @param formerOwner The sFormerOwner to set.
     */
    public void setSFormerOwner(String formerOwner)
    {
        sFormerOwner = formerOwner;
    }
    /**
     * @return Returns the sUserName.
     */
    public String getSUserName()
    {
        return sUserName;
    }
    /**
     * @param userName The sUserName to set.
     */
    public void setSUserName(String userName)
    {
        sUserName = userName;
    }
}
