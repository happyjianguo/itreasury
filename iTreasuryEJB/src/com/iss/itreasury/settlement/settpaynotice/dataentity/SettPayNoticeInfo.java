/*
 * Created on 2004-9-10
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.settpaynotice.dataentity;

import java.sql.Timestamp;
import java.util.*;

import com.iss.itreasury.settlement.base.*;
import com.iss.itreasury.util.*;
/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SettPayNoticeInfo extends ITreasuryBaseDataEntity
{
	private long id=-1;
	private String sCode="";				//放款通知单代码
	private long nContractID=-1;			//合同id(fk_loan_contractform_id)
	private Timestamp dtOutDate=null;		//放款日期 /借款日期
	private double mAmount=0;				//金额
	private String sConsignAccount="";		//委托方账户号
	private long nBankInterestID=-1;		//银行利率id
	private double mCommissionRate=0;		//手续费率
	private double mSuretyFeeRate=0;		//担保费率
	private Timestamp dtStart=null;			//贷款起始日期
	private Timestamp dtEnd=null;			//贷款结束日期
	private String sReceiveClientName="";	//收款单位名称
	private String sRemitBank="";			//汇入行
	private String sCompanyLeader="";		//公司领导
	private String sHandlingPerson="";		//经办人
	private String sDepartmentLeader="";	//部门领导
	private long nStatusID=-1;				//状态
	private long nInputUserID=-1;			//录入人id
	private Timestamp dtInputDate=null;		//录入日期
	private long nNextCheckUserID=-1;		//审核人
	private long nSourceTypeID=-1;			//是信贷还是结算使用
	private long nGrantCurrentAccountID=-1; //发放至活期账户
	private long nGrantTypeID=-1;			//放款方式
	private String sRemitInProvince="";		//汇入地(省)
	private String sRemitInCity="";			//汇入地(市)
	private long nDrawNoticeID=-1;			//银团提款通知单标示
	private String sLoanAccount="";			//借款单位账号
	private String sCheckPerson="";			//复核人
	private long nAccountBankID=-1;			//开户银行id
	private double mInterestRate=0;			//贷款利率(委托贷款使用)
	private String sReceiveAccount="";		//收款单位账户号
	private long nNextCheckLevel=-1;		//下一个审批级别
	private double mStaidAdjustRate=0;		//固定浮动利率
	private double mAdjustRate=0;			//浮动比例

	private String sContractCode="";
	private double mContractAmount=0;
	private long nBorrowClientID=-1;
	/**
	 * @return
	 */
	public Timestamp getEnd()
	{
		return dtEnd;
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
	public Timestamp getOutDate()
	{
		return dtOutDate;
	}

	/**
	 * @return
	 */
	public Timestamp getStart()
	{
		return dtStart;
	}

	/**
	 * @return
	 */
	public double getAdjustRate()
	{
		return mAdjustRate;
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
	public double getCommissionRate()
	{
		return mCommissionRate;
	}

	/**
	 * @return
	 */
	public double getInterestRate()
	{
		return mInterestRate;
	}

	/**
	 * @return
	 */
	public double getStaidAdjustRate()
	{
		return mStaidAdjustRate;
	}

	/**
	 * @return
	 */
	public double getSuretyFeeRate()
	{
		return mSuretyFeeRate;
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
	public long getBankInterestID()
	{
		return nBankInterestID;
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
	public long getDrawNoticeID()
	{
		return nDrawNoticeID;
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
	public long getSourceTypeID()
	{
		return nSourceTypeID;
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
	public String getCheckPerson()
	{
		return sCheckPerson;
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
	public String getCompanyLeader()
	{
		return sCompanyLeader;
	}

	/**
	 * @return
	 */
	public String getConsignAccount()
	{
		return sConsignAccount;
	}

	/**
	 * @return
	 */
	public String getDepartmentLeader()
	{
		return sDepartmentLeader;
	}

	/**
	 * @return
	 */
	public String getHandlingPerson()
	{
		return sHandlingPerson;
	}

	/**
	 * @return
	 */
	public String getLoanAccount()
	{
		return sLoanAccount;
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
	public void setEnd(Timestamp timestamp)
	{
		dtEnd = timestamp;
		putUsedField("dtEnd", dtEnd);
	}

	/**
	 * @param timestamp
	 */
	public void setInputDate(Timestamp timestamp)
	{
		dtInputDate = timestamp;
		putUsedField("dtInputDate", dtInputDate);
	}

	/**
	 * @param timestamp
	 */
	public void setOutDate(Timestamp timestamp)
	{
		dtOutDate = timestamp;
		putUsedField("dtOutDate", dtOutDate);
	}

	/**
	 * @param timestamp
	 */
	public void setStart(Timestamp timestamp)
	{
		dtStart = timestamp;
		putUsedField("dtStart", dtStart);
	}

	/**
	 * @param d
	 */
	public void setAdjustRate(double d)
	{
		mAdjustRate = d;
		putUsedField("mAdjustRate", mAdjustRate);
	}

	/**
	 * @param d
	 */
	public void setAmount(double d)
	{
		mAmount = d;
		putUsedField("mAmount", mAmount);
	}

	/**
	 * @param d
	 */
	public void setCommissionRate(double d)
	{
		mCommissionRate = d;
		putUsedField("mCommissionRate", mCommissionRate);
	}

	/**
	 * @param d
	 */
	public void setInterestRate(double d)
	{
		mInterestRate = d;
		putUsedField("mInterestRate", mInterestRate);
	}

	/**
	 * @param d
	 */
	public void setStaidAdjustRate(double d)
	{
		mStaidAdjustRate = d;
		putUsedField("mStaidAdjustRate", mStaidAdjustRate);
	}

	/**
	 * @param d
	 */
	public void setSuretyFeeRate(double d)
	{
		mSuretyFeeRate = d;
		putUsedField("mSuretyFeeRate", mSuretyFeeRate);
	}

	/**
	 * @param l
	 */
	public void setAccountBankID(long l)
	{
		nAccountBankID = l;
		putUsedField("nAccountBankID", nAccountBankID);
	}

	/**
	 * @param l
	 */
	public void setBankInterestID(long l)
	{
		nBankInterestID = l;
		putUsedField("nBankInterestID", nBankInterestID);
	}

	/**
	 * @param l
	 */
	public void setContractID(long l)
	{
		nContractID = l;
		putUsedField("nContractID", nContractID);
	}

	/**
	 * @param l
	 */
	public void setDrawNoticeID(long l)
	{
		nDrawNoticeID = l;
		putUsedField("nDrawNoticeID", nDrawNoticeID);
	}

	/**
	 * @param l
	 */
	public void setGrantCurrentAccountID(long l)
	{
		nGrantCurrentAccountID = l;
		putUsedField("nGrantCurrentAccountID", nGrantCurrentAccountID);
	}

	/**
	 * @param l
	 */
	public void setGrantTypeID(long l)
	{
		nGrantTypeID = l;
		putUsedField("nGrantTypeID", nGrantTypeID);
	}

	/**
	 * @param l
	 */
	public void setInputUserID(long l)
	{
		nInputUserID = l;
		putUsedField("nInputUserID", nInputUserID);
	}

	/**
	 * @param l
	 */
	public void setNextCheckLevel(long l)
	{
		nNextCheckLevel = l;
		putUsedField("nNextCheckLevel", nNextCheckLevel);
	}

	/**
	 * @param l
	 */
	public void setNextCheckUserID(long l)
	{
		nNextCheckUserID = l;
		putUsedField("nNextCheckUserID", nNextCheckUserID);
	}

	/**
	 * @param l
	 */
	public void setSourceTypeID(long l)
	{
		nSourceTypeID = l;
		putUsedField("nSourceTypeID", nSourceTypeID);
	}

	/**
	 * @param l
	 */
	public void setStatusID(long l)
	{
		nStatusID = l;
		putUsedField("nStatusID", nStatusID);
	}

	/**
	 * @param string
	 */
	public void setCheckPerson(String string)
	{
		sCheckPerson = string;
		putUsedField("sCheckPerson", sCheckPerson);
	}

	/**
	 * @param string
	 */
	public void setCode(String string)
	{
		sCode = string;
		putUsedField("sCode", sCode);
	}

	/**
	 * @param string
	 */
	public void setCompanyLeader(String string)
	{
		sCompanyLeader = string;
		putUsedField("sCompanyLeader", sCompanyLeader);
	}

	/**
	 * @param string
	 */
	public void setConsignAccount(String string)
	{
		sConsignAccount = string;
		putUsedField("sConsignAccount", sConsignAccount);
	}

	/**
	 * @param string
	 */
	public void setDepartmentLeader(String string)
	{
		sDepartmentLeader = string;
		putUsedField("sDepartmentLeader", sDepartmentLeader);
	}

	/**
	 * @param string
	 */
	public void setHandlingPerson(String string)
	{
		sHandlingPerson = string;
		putUsedField("sHandlingPerson", sHandlingPerson);
	}

	/**
	 * @param string
	 */
	public void setLoanAccount(String string)
	{
		sLoanAccount = string;
		putUsedField("sLoanAccount", sLoanAccount);
	}

	/**
	 * @param string
	 */
	public void setReceiveAccount(String string)
	{
		sReceiveAccount = string;
		putUsedField("sReceiveAccount", sReceiveAccount);
	}

	/**
	 * @param string
	 */
	public void setReceiveClientName(String string)
	{
		sReceiveClientName = string;
		putUsedField("sReceiveClientName", sReceiveClientName);
	}

	/**
	 * @param string
	 */
	public void setRemitBank(String string)
	{
		sRemitBank = string;
		putUsedField("sRemitBank", sRemitBank);
	}

	/**
	 * @param string
	 */
	public void setRemitInCity(String string)
	{
		sRemitInCity = string;
		putUsedField("sRemitInCity", sRemitInCity);
	}

	/**
	 * @param string
	 */
	public void setRemitInProvince(String string)
	{
		sRemitInProvince = string;
		putUsedField("sRemitInProvince", sRemitInProvince);
	}



	/**
	 * @return
	 */
	public double getContractAmount()
	{
		return mContractAmount;
	}

	/**
	 * @return
	 */
	public String getContractCode()
	{
		return sContractCode;
	}

	/**
	 * @param d
	 */
	public void setContractAmount(double d)
	{
		mContractAmount = d;
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
	public long getBorrowClientID()
	{
		return nBorrowClientID;
	}

	/**
	 * @param l
	 */
	public void setBorrowClientID(long l)
	{
		nBorrowClientID = l;
	}

	/**
	 * @return
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * @param l
	 */
	public void setId(long l)
	{
		id = l;
		putUsedField("id", id);
	}

}
