/*
 * Created on 2004-8-3
 *
 * Title:				iTreasury
 * @author             	yanliu 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */
package com.iss.itreasury.loan.transdiscountcredence.dataentity;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.loan.base.LoanBaseDataEntity;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.DataFormat;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransDiscountCredenceInfo extends LoanBaseDataEntity
{
    /*数据库对应属性*/
    private long id = -1;
    private Timestamp dtInputDate = null;  		//录入日期
    private long nDraftTypeID = -1;				//转贴现汇票种类
    private String sDraftCode = "";				//转贴现汇票号
    private Timestamp dtPublicDate = null;		//发票日
    private Timestamp dtAtTerm = null;			//到期日
    private String sApplyClient = "";			//借款单位
    private String sApplyAccount = "";			//申请单位账户号
    private String sApplyBank = "";				//申请单位开户行
    private String sAcceptClient = "";			//承兑单位名称
    private String sAcceptAccount = "";			//承兑单位账户号
    private String sAcceptBank = "";			//承兑单位银行
    private double mAmount = 0.0;				//（转）贴现凭证金额
    private double mInterest = 0.0;				//（转）贴现凭证利息
    private long nStatusID = -1;				//状态
    private long nInputUserID = -1;				//录入人
    private Timestamp dtCheckDate = null;		//审核日期
    private long nCheckNum = -1;
    private long nNextCheckUserID = -1;			//审核人
    private long nLastCheckUserID = -1;			//下一级审核人
    private long nOfficeID = -1;				//办事处
    private long nCurrencyID= -1;               //币种
    private String sCode = "";					//汇票号码
    private long nTypeID = -1;					//凭证种类
    private long nBillSourceTypeID = -1;		//票据来源
    private long nGrantTypeID = -1;				//放款方式
    private long nAccountBankID = -1;			//开户行ID
    private String sReceiveAccount = "";		//收款单位账户号
    private String sReceiveClientName = "";		//收款单位名称
    private String sRemitBank = "";				//汇入行
    private String sRemitInProvince = "";		//汇入省
    private String sRemitInCity = "";			//汇入市
    private long nGrantCurrentAccountID = -1;	//发放至活期账户
    private long nNextCheckLevel = -1;			//下一级审批级别
    private long nContractID = -1;				//合同ID
    private double mRate = 0.0;					//利率
    private Timestamp dtFillDate = null;		//填写日期
    private Timestamp dtRepurchaseDate = null;	//回购凭证的回购日期
    private long nTransDiscountCredenceID = -1;	//回购凭证所属转贴现凭证ID
    private long nRepurchaseTerm = -1;			//回购凭证的回购期限	
    /*凭证其他信息*/
    private long[] lAllBillID = null;			//凭证下所有票据ID数组
    private String sAllBillIDString = "";			//凭证下所有票据ID组成的字符串
    private String sContractCode = "";			//贴现合同编号 
	private double mCheckAmount = 0.0;			//贴现凭证核定金额
	private String sGrantCurrentAccountCode = "";	//发放至活期账户账户号
	private double dDiscountExamineAmount = 0.0;	//转贴现批准金额
	private double dDiscountCheckAmount = 0.0;		//转贴现核定金额
	private Timestamp dtDiscountDate = null;		//转贴现日
	private double dDiscountRate = 0.0;				//转贴现利率
	private long nDiscountTypeID = -1;				//转贴现种类
	private long nRepurchaseTypeID = -1;			//转贴现回购类型
	private long nInOrOut = -1;						//转贴现类型
	private String sTransDiscountCredenceCode = "";	//回购凭证所属转贴现凭证的凭证号
	private double dRepurchasedAmount = 0.0;		//已回购金额（回购凭证）
//	added by xwhe 2007/09/10审批流
	private InutParameterInfo inutParameterInfo = null;
	
	private long nCount = -1;
	private long attachId = -1;		//附件关联ID
	
	private long ApplyClientId = -1; 
	
    public long getApplyClientId() {
		return ApplyClientId;
	}

	public void setApplyClientId(long applyClientId) {
		ApplyClientId = applyClientId;
	}

	/* (non-Javadoc)
     * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
     */
    public long getId()
    {
        // TODO Auto-generated method stub
        return id;
    }

    /* (non-Javadoc)
     * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
     */
    public void setId(long l)
    {
        // TODO Auto-generated method stub
		id = l;
    }
    
	/**
	 * @return
	 */
	public Timestamp getAtTerm()
	{
		return dtAtTerm;
	}

	/**
	 * @return
	 */
	public Timestamp getCheckDate()
	{
		return dtCheckDate;
	}

	/**
	 * @return
	 */
	public Timestamp getFillDate()
	{
		return dtFillDate;
	}

	/**
	 * @return
	 */
	public Timestamp getInputDate()
	{
		return dtInputDate;
	}

	/**
	 * @return
	 */
	public Timestamp getPublicDate()
	{
		return dtPublicDate;
	}

	/**
	 * @return
	 */
	public double getAmount()
	{
		return mAmount;
	}

	/**
	 * @return
	 */
	public double getInterest()
	{
		return mInterest;
	}

	/**
	 * @return
	 */
	public double getRate()
	{
		return mRate;
	}

	/**
	 * @return
	 */
	public long getAccountBankID()
	{
		return nAccountBankID;
	}

	/**
	 * @return
	 */
	public long getBillSourceTypeID()
	{
		return nBillSourceTypeID;
	}

	/**
	 * @return
	 */
	public long getCheckNum()
	{
		return nCheckNum;
	}

	/**
	 * @return
	 */
	public long getContractID()
	{
		return nContractID;
	}

	/**
	 * @return
	 */
	public long getDraftTypeID()
	{
		return nDraftTypeID;
	}

	/**
	 * @return
	 */
	public long getGrantCurrentAccountID()
	{
		return nGrantCurrentAccountID;
	}

	/**
	 * @return
	 */
	public long getGrantTypeID()
	{
		return nGrantTypeID;
	}

	/**
	 * @return
	 */
	public long getInputUserID()
	{
		return nInputUserID;
	}

	/**
	 * @return
	 */
	public long getLastCheckUserID()
	{
		return nLastCheckUserID;
	}

	/**
	 * @return
	 */
	public long getNextCheckLevel()
	{
		return nNextCheckLevel;
	}

	/**
	 * @return
	 */
	public long getNextCheckUserID()
	{
		return nNextCheckUserID;
	}

	/**
	 * @return
	 */
	public long getOfficeID()
	{
		return nOfficeID;
	}

	/**
	 * @return
	 */
	public long getStatusID()
	{
		return nStatusID;
	}

	/**
	 * @return
	 */
	public long getTypeID()
	{
		return nTypeID;
	}

	/**
	 * @return
	 */
	public String getAcceptAccount()
	{
		return sAcceptAccount;
	}

	/**
	 * @return
	 */
	public String getAcceptBank()
	{
		return sAcceptBank;
	}

	/**
	 * @return
	 */
	public String getAcceptClient()
	{
		return sAcceptClient;
	}

	/**
	 * @return
	 */
	public String getApplyAccount()
	{
		return sApplyAccount;
	}

	/**
	 * @return
	 */
	public String getApplyBank()
	{
		return sApplyBank;
	}

	/**
	 * @return
	 */
	public String getApplyClient()
	{
		return sApplyClient;
	}

	/**
	 * @return
	 */
	public String getCode()
	{
		return sCode;
	}

	/**
	 * @return
	 */
	public String getDraftCode()
	{
		return sDraftCode;
	}

	/**
	 * @return
	 */
	public String getReceiveAccount()
	{
		return sReceiveAccount;
	}

	/**
	 * @return
	 */
	public String getReceiveClientName()
	{
		return sReceiveClientName;
	}

	/**
	 * @return
	 */
	public String getRemitBank()
	{
		return sRemitBank;
	}

	/**
	 * @return
	 */
	public String getRemitInCity()
	{
		return sRemitInCity;
	}

	/**
	 * @return
	 */
	public String getRemitInProvince()
	{
		return sRemitInProvince;
	}

	/**
	 * @param timestamp
	 */
	public void setAtTerm(Timestamp timestamp)
	{
		dtAtTerm = timestamp;
		putUsedField("dtAtterm", timestamp);
	}

	/**
	 * @param timestamp
	 */
	public void setCheckDate(Timestamp timestamp)
	{
		dtCheckDate = timestamp;
		putUsedField("dtCheckDate", timestamp);
	}

	/**
	 * @param timestamp
	 */
	public void setFillDate(Timestamp timestamp)
	{
		dtFillDate = timestamp;
		putUsedField("dtFillDate", timestamp);
	}

	/**
	 * @param timestamp
	 */
	public void setInputDate(Timestamp timestamp)
	{
		dtInputDate = timestamp;
		putUsedField("dtInputDate", timestamp);
	}

	/**
	 * @param timestamp
	 */
	public void setPublicDate(Timestamp timestamp)
	{
		dtPublicDate = timestamp;
		putUsedField("dtPublicDate", timestamp);
	}

	/**
	 * @param d
	 */
	public void setAmount(double d)
	{
		mAmount = d;
		putUsedField("mAmount", d);
	}

	/**
	 * @param d
	 */
	public void setInterest(double d)
	{
		mInterest = d;
		putUsedField("mInterest", d);
	}

	/**
	 * @param d
	 */
	public void setRate(double d)
	{
		mRate = d;
		putUsedField("mRate", d);
	}

	/**
	 * @param l
	 */
	public void setAccountBankID(long l)
	{
		nAccountBankID = l;
		putUsedField("nAccountBankID", l);
	}

	/**
	 * @param l
	 */
	public void setBillSourceTypeID(long l)
	{
		nBillSourceTypeID = l;
		putUsedField("nBillSourceTypeID", l);
	}

	/**
	 * @param l
	 */
	public void setCheckNum(long l)
	{
		nCheckNum = l;
		putUsedField("nCheckNum", l);
	}

	/**
	 * @param l
	 */
	public void setContractID(long l)
	{
		nContractID = l;
		putUsedField("nContractID", l);
	}

	/**
	 * @param l
	 */
	public void setDraftTypeID(long l)
	{
		nDraftTypeID = l;
		putUsedField("nDraftTypeID", l);
	}

	/**
	 * @param l
	 */
	public void setGrantCurrentAccountID(long l)
	{
		nGrantCurrentAccountID = l;
		putUsedField("nGrantCurrentAccountID", l);
	}

	/**
	 * @param l
	 */
	public void setGrantTypeID(long l)
	{
		nGrantTypeID = l;
		putUsedField("nGrantTypeID", l);
	}

	/**
	 * @param l
	 */
	public void setInputUserID(long l)
	{
		nInputUserID = l;
		putUsedField("nInputUserID", l);
	}

	/**
	 * @param l
	 */
	public void setLastCheckUserID(long l)
	{
		nLastCheckUserID = l;
		putUsedField("nLastCheckUserID", l);
	}

	/**
	 * @param l
	 */
	public void setNextCheckLevel(long l)
	{
		nNextCheckLevel = l;
		putUsedField("nNextCheckLevel", l);
	}

	/**
	 * @param l
	 */
	public void setNextCheckUserID(long l)
	{
		nNextCheckUserID = l;
		putUsedField("nNextCheckUserID", l);
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		nOfficeID = l;
		putUsedField("nOfficeID", l);
	}

	/**
	 * @param l
	 */
	public void setStatusID(long l)
	{
		nStatusID = l;
		putUsedField("nStatusID", l);
	}

	/**
	 * @param l
	 */
	public void setTypeID(long l)
	{
		nTypeID = l;
		putUsedField("nTypeID", l);
	}

	/**
	 * @param string
	 */
	public void setAcceptAccount(String string)
	{
		sAcceptAccount = string;
		putUsedField("sAcceptAccount", string);
	}

	/**
	 * @param string
	 */
	public void setAcceptBank(String string)
	{
		sAcceptBank = string;
		putUsedField("sAcceptBank", string);
	}

	/**
	 * @param string
	 */
	public void setAcceptClient(String string)
	{
		sAcceptClient = string;
		putUsedField("sAcceptClient", string);
	}

	/**
	 * @param string
	 */
	public void setApplyAccount(String string)
	{
		sApplyAccount = string;
		putUsedField("sApplyAccount", string);
	}

	/**
	 * @param string
	 */
	public void setApplyBank(String string)
	{
		sApplyBank = string;
		putUsedField("sApplyBank", string);
	}

	/**
	 * @param string
	 */
	public void setApplyClient(String string)
	{
		sApplyClient = string;
		putUsedField("sApplyClient", string);
	}

	/**
	 * @param string
	 */
	public void setCode(String string)
	{
		sCode = string;
		putUsedField("sCode", string);
	}

	/**
	 * @param string
	 */
	public void setDraftCode(String string)
	{
		sDraftCode = string;
		putUsedField("sDraftCode", string);
	}

	/**
	 * @param string
	 */
	public void setReceiveAccount(String string)
	{
		sReceiveAccount = string;
		putUsedField("sReceiveAccount", string);
	}

	/**
	 * @param string
	 */
	public void setReceiveClientName(String string)
	{
		sReceiveClientName = string;
		putUsedField("sReceiveClientName", string);
	}

	/**
	 * @param string
	 */
	public void setRemitBank(String string)
	{
		sRemitBank = string;
		putUsedField("sRemitBank", string);
	}

	/**
	 * @param string
	 */
	public void setRemitInCity(String string)
	{
		sRemitInCity = string;
		putUsedField("sRemitInCity", string);
	}

	/**
	 * @param string
	 */
	public void setRemitInProvince(String string)
	{
		sRemitInProvince = string;
		putUsedField("sRemitInProvince", string);
	}

	/**
	 * @return
	 */
	public long[] getAllBillID()
	{
		return lAllBillID;
	}

	/**
	 * @return
	 */
	public String getContractCode()
	{
		return sContractCode;
	}

	/**
	 * @param ls
	 */
	public void setAllBillID(long[] ls)
	{
		lAllBillID = ls;
		String strBillID ="";
		long lBillID = ls[0];
		strBillID += lBillID;
		for(int i=1;i<ls.length;i++)
		{				
			lBillID = ls[i];
			strBillID += ","+lBillID;
		}
		this.sAllBillIDString = strBillID;
	}

	/**
	 * @param string
	 */
	public void setContractCode(String string)
	{
		sContractCode = string;
	}

	/**
	 * @return
	 */
	public double getCheckAmount()
	{
		return mCheckAmount;
	}

	/**
	 * @param d
	 */
	public void setCheckAmount(double d)
	{
		mCheckAmount = d;
	}

	/**
	 * @return
	 */
	public String getGrantCurrentAccountCode()
	{
		return sGrantCurrentAccountCode;
	}

	/**
	 * @param string
	 */
	public void setGrantCurrentAccountCode(String string)
	{
		sGrantCurrentAccountCode = string;
	}

	/**
	 * @return
	 */
	public double getDiscountCheckAmount()
	{
		return dDiscountCheckAmount;
	}

	/**
	 * @return
	 */
	public double getDiscountExamineAmount()
	{
		return dDiscountExamineAmount;
	}

	/**
	 * @param d
	 */
	public void setDiscountCheckAmount(double d)
	{
		dDiscountCheckAmount = d;
	}

	/**
	 * @param d
	 */
	public void setDiscountExamineAmount(double d)
	{
		dDiscountExamineAmount = d;
	}

	/**
	 * @return
	 */
	public Timestamp getDiscountDate()
	{
		return dtDiscountDate;
	}

	/**
	 * @param timestamp
	 */
	public void setDiscountDate(Timestamp timestamp)
	{
		dtDiscountDate = timestamp;
	}

	/**
	 * @return
	 */
	public long getCount()
	{
		return nCount;
	}

	/**
	 * @param l
	 */
	public void setCount(long l)
	{
		nCount = l;
	}

	/**
	 * @return
	 */
	public double getDiscountRate()
	{
		return dDiscountRate;
	}

	/**
	 * @param d
	 */
	public void setDiscountRate(double d)
	{
		dDiscountRate = d;
	}

	/**
	 * @return
	 */
	public long getDiscountTypeID()
	{
		return nDiscountTypeID;
	}

	/**
	 * @return
	 */
	public long getRepurchaseTypeID()
	{
		return nRepurchaseTypeID;
	}

	/**
	 * @param l
	 */
	public void setDiscountTypeID(long l)
	{
		nDiscountTypeID = l;
	}

	/**
	 * @param l
	 */
	public void setRepurchaseTypeID(long l)
	{
		nRepurchaseTypeID = l;
	}

	/**
	 * @return
	 */
	public String getAllBillIDString()
	{
		return sAllBillIDString;
	}

	/**
	 * @param string
	 */
	public void setAllBillIDString(String string)
	{
		sAllBillIDString = string;
		Vector v = new Vector();
		long lBillID = -1;
		v = DataFormat.changeStringGroup(string);
		if( (v != null) && (v.size() > 0) )
		{
			long[] ls = new long[v.size()];
			Iterator it = v.iterator();
			int i =0;
			while (it.hasNext() && i<v.size())
			{
				try
				{
					lBillID = ((Long)it.next()).longValue();
				}
				catch(Exception e)
				{
					lBillID = -1;
				}
				ls[i] = lBillID;
				i++;
			}
			this.lAllBillID = ls;
		}		
	}

	/**
	 * @return
	 */
	public long getInOrOut()
	{
		return nInOrOut;
	}

	/**
	 * @param l
	 */
	public void setInOrOut(long l)
	{
		nInOrOut = l;
	}

	/**
	 * @return
	 */
	public Timestamp getRepurchaseDate()
	{
		return dtRepurchaseDate;
	}

	/**
	 * @param timestamp
	 */
	public void setRepurchaseDate(Timestamp timestamp)
	{
		dtRepurchaseDate = timestamp;
		putUsedField("dtRepurchaseDate", timestamp);
	}

	/**
	 * @return
	 */
	public long getRepurchaseTerm()
	{
		return nRepurchaseTerm;
	}

	/**
	 * @return
	 */
	public long getTransDiscountCredenceID()
	{
		return nTransDiscountCredenceID;
	}

	/**
	 * @param l
	 */
	public void setRepurchaseTerm(long l)
	{
		nRepurchaseTerm = l;
		putUsedField("NREPURCHASETERM", l);
	}

	/**
	 * @param l
	 */
	public void setTransDiscountCredenceID(long l)
	{
		nTransDiscountCredenceID = l;
		putUsedField("NTRANSDISCOUNTCREDENCEID", l);
	}

	/**
	 * @return
	 */
	public String getTransDiscountCredenceCode()
	{
		return sTransDiscountCredenceCode;
	}

	/**
	 * @param string
	 */
	public void setTransDiscountCredenceCode(String string)
	{
		sTransDiscountCredenceCode = string;
	}

	/**
	 * @return
	 */
	public double getRepurchasedAmount()
	{
		return dRepurchasedAmount;
	}

	/**
	 * @param d
	 */
	public void setRepurchasedAmount(double d)
	{
		dRepurchasedAmount = d;
	}

    /**
     * @return Returns the nCurrencyID.
     */
    public long getNCurrencyID() {
        return nCurrencyID;
    }
    /**
     * @param currencyID The nCurrencyID to set.
     */
    public void setNCurrencyID(long currencyID) {
        nCurrencyID = currencyID;
    }

	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}

	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}
	public long getAttachId() {
		return attachId;
	}
	public void setAttachId(long attachId) {
		this.attachId = attachId;
		putUsedField("attachId", attachId);
	}
}
