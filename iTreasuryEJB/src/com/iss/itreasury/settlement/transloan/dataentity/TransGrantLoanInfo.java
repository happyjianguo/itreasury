/*
 * 创建日期 2003-10-6
 */
package com.iss.itreasury.settlement.transloan.dataentity;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.sysframe.base.dataentity.BaseDataEntity;

/**
 * 信托贷款和委托贷款的通用info类
 * @author yqwu
 */
public class TransGrantLoanInfo extends BaseDataEntity implements Serializable
{
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}
	public void setId(long id) {
		// TODO Auto-generated method stub
		
	}
	//交易状态 0已删除1暂存2保存（未复核）3已复核4未签认5已签认6已确认7已勾账
	private long[] StatusID = null;
	/**
	 * 取消复核摘要
	 */
	private String CheckAbstract = "";
	/**
	 * 摘要
	 */
	private String Abstract = "";
	/**
	 * 摘要ID
	 */
	private long AbstractID = -1;
	/**
	 * 复核人
	 */
	private long CheckUserID = -1;
	/**
	 * 录入人
	 */
	private long InputUserID = -1;
	/**
	 * 录入日期
	 */
	private Timestamp InputDate;
	/**
	 * 修改时间
	 */
	private Timestamp Modify;
	/**
	 * 执行日
	 */
	private Timestamp Execute;
	/**
	 * 起息日
	 */
	private Timestamp InterestStart;
	/**
	 * 现金流向
	 */
	private long CashFlowID = -1;
	/**
	 * 金额
	 */
	private double Amount;
	/**
	 * 汇入行市
	 */
	private String City = "";
	/**
	 * 汇入行省
	 */
	private String Province = "";
	/**
	 * 外部汇入行名称
	 */
	private String BankName = "";
	/**
	 * 外部客户名称
	 */
	private String ExtAcctName = "";
	/**
	 * 外部账户号
	 */
	private String ExtAcctNo = "";
	/**
	 * 汇出行ID
	 */
	private long BankID = -1;
	/**
	 * 放款方式
	 * 1=电汇
	 * 2=票汇
	 * 3=信汇
	 * 4=支票
	 */
	private long PayTypeID = -1;
	/**
	 * 利息税费生效日期
	 * 委托贷款专用
	 */
	private Timestamp InterestTaxRateVauleDate;
	/**
	 * 利息税费率
	 * 委托贷款专用
	 */
	private double InterestTaxRate;
	/**
	 * 利息税费率计划
	 * 委托贷款专用
	 */
	private long InterestTaxPlanId;
	/**
	 * 付手续费账户号
	 * 委托贷款专用
	 */
	private long PayCommisionAccountID = -1;
	/**
	 * 收担保费账户号（担保方）
	 * 信托贷款专用
	 */
	private long ReceiveSuretyFeeAccountID = -1;
	/**
	 * 付担保费账户号（贷款方）
	 * 信托贷款专用
	 */
	private long PaySuretyFeeAccountID = -1;
	/**
	 * 收息账户号
	 * 委托贷款专用
	 */
	private long ReceiveInterestAccountID = -1;
	/**
	 * 活期存款账户ID
	 */
	private long DepositAccountID = -1;
	/**
	 * 付息账户号
	 */
	private long PayInterestAccountID = -1;
	/**
	 * 是否后补贷款账户处理
	 * 委托贷款专用
	 */
	private boolean KeepAccount;
	/**
	 * 委托存款账户号
	 * 委托贷款专用
	 */
	private long ConsignDepositAccountID = -1;
	/**
	 * 转期通知单
	 */
	private long ExtendFormID = -1;
	/**
	 * 放款通知单
	 */
	private long LoanNoteID = -1;
	/**
	 * 贷款合同号
	 */
	private long LoanContractID = -1;
	/**
	 * 贷款账户
	 */
	private long LoanAccountID = -1;
	/**
	 * 交易类型
	 */
	private long TransactionTypeID = -1;
	/**
	 * 交易号
	 */
	private String TransNo = "";
	/**
	 * 币种
	 */
	private long CurrencyID = -1;
	/**
	 * 办事处
	 */
	private long OfficeID = -1;
	/**
	 * 提入行No
	 */
	private String ExtBankNo = "";
	/**
	 * ID
	 */
	private long ID = -1;
	/**
	 * 排序
	 */
	private long AscOrDesc = -1;
	/**
	 * 排序字段
	 */
	private long OrderByType = -1;
	/**
	* 代理费利率
	*/
	private double CommissionRate = 0.0;
	//Added by zwsun, 2007-06-20, 审批流
	private InutParameterInfo inutParameterInfo=null;
	
	//银团的成员行信息
	  ArrayList SyndicationLoan =null;
	/**
	 * @return
	 */
	public String getAbstract()
	{
		return Abstract;
	}
	/**
	 * @return
	 */
	public long getAbstractID()
	{
		return AbstractID;
	}
	/**
	 * @return
	 */
	public double getAmount()
	{
		return Amount;
	}
	/**
	 * @return
	 */
	public long getBankID()
	{
		return BankID;
	}
	/**
	 * @return
	 */
	public String getBankName()
	{
		return BankName;
	}
	/**
	 * @return
	 */
	public long getCashFlowID()
	{
		return CashFlowID;
	}
	/**
	 * @return
	 */
	public String getCheckAbstract()
	{
		return CheckAbstract;
	}
	/**
	 * @return
	 */
	public long getCheckUserID()
	{
		return CheckUserID;
	}
	/**
	 * @return
	 */
	public String getCity()
	{
		return City;
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
	public Timestamp getExecute()
	{
		return Execute;
	}
	/**
	 * @return
	 */
	public String getExtAcctName()
	{
		return ExtAcctName;
	}
	/**
	 * @return
	 */
	public String getExtAcctNo()
	{
		return ExtAcctNo;
	}
	/**
	 * @return
	 */
	public long getExtendFormID()
	{
		return ExtendFormID;
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
	public Timestamp getInputDate()
	{
		return InputDate;
	}
	/**
	 * @return
	 */
	public long getInputUserID()
	{
		return InputUserID;
	}
	/**
	 * @return
	 */
	public Timestamp getInterestStart()
	{
		return InterestStart;
	}
	/**
	 * @return
	 */
	public double getInterestTaxRate()
	{
		return InterestTaxRate;
	}
	
	
	
	public long getInterestTaxPlanId()
	{
	
		return InterestTaxPlanId;
	}
	
	public void setInterestTaxPlanId(long interestTaxPlanId)
	{
	
		InterestTaxPlanId = interestTaxPlanId;
	}
	/**
	 * @return
	 */
	public Timestamp getInterestTaxRateVauleDate()
	{
		return InterestTaxRateVauleDate;
	}
	/**
	 * @return
	 */
	public boolean isKeepAccount()
	{
		return KeepAccount;
	}
	/**
	 * @return
	 */
	public long getLoanAccountID()
	{
		return LoanAccountID;
	}
	/**
	 * @return
	 */
	public long getLoanContractID()
	{
		return LoanContractID;
	}
	/**
	 * @return
	 */
	public long getLoanNoteID()
	{
		return LoanNoteID;
	}
	/**
	 * @return
	 */
	public Timestamp getModify()
	{
		return Modify;
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
	public long getPayCommisionAccountID()
	{
		return PayCommisionAccountID;
	}
	/**
	 * @return
	 */
	public long getPayInterestAccountID()
	{
		return PayInterestAccountID;
	}
	/**
	 * @return
	 */
	public long getPaySuretyFeeAccountID()
	{
		return PaySuretyFeeAccountID;
	}
	/**
	 * @return
	 */
	public long getPayTypeID()
	{
		return PayTypeID;
	}
	/**
	 * @return
	 */
	public String getProvince()
	{
		return Province;
	}
	/**
	 * @return
	 */
	public long getReceiveInterestAccountID()
	{
		return ReceiveInterestAccountID;
	}
	/**
	 * @return
	 */
	public long getReceiveSuretyFeeAccountID()
	{
		return ReceiveSuretyFeeAccountID;
	}
	/**
		 * Returns the statusID.
		 * @return long
		 */
	public long getStatusID()
	{
		long lResult = -1;
		if (this.StatusID != null && this.StatusID.length > 0)
		{
			lResult = this.StatusID[0];
		}
		return lResult;
	}
	/**
	 * 用于当前Entity作为查询条件时
	 * @return long[]
	 */
	public long[] getStatusIDs()
	{
		return this.StatusID;
	}
	/**
	 * @return
	 */
	public long getTransactionTypeID()
	{
		return TransactionTypeID;
	}
	/**
	 * @return
	 */
	public String getTransNo()
	{
		return TransNo;
	}
	/**
	 * @param string
	 */
	public void setAbstract(String string)
	{
		Abstract = string;
	}
	/**
	 * @param l
	 */
	public void setAbstractID(long l)
	{
		AbstractID = l;
	}
	/**
	 * @param d
	 */
	public void setAmount(double d)
	{
		Amount = d;
	}
	/**
	 * @param l
	 */
	public void setBankID(long l)
	{
		BankID = l;
	}
	/**
	 * @param string
	 */
	public void setBankName(String string)
	{
		BankName = string;
	}
	/**
	 * @param l
	 */
	public void setCashFlowID(long l)
	{
		CashFlowID = l;
	}
	/**
	 * @param string
	 */
	public void setCheckAbstract(String string)
	{
		CheckAbstract = string;
	}
	/**
	 * @param l
	 */
	public void setCheckUserID(long l)
	{
		CheckUserID = l;
	}
	/**
	 * @param string
	 */
	public void setCity(String string)
	{
		City = string;
	}
	/**
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		CurrencyID = l;
	}
	/**
	 * @param Timestamp
	 */
	public void setExecute(Timestamp Timestamp)
	{
		Execute = Timestamp;
	}
	/**
	 * @param string
	 */
	public void setExtAcctName(String string)
	{
		ExtAcctName = string;
	}
	/**
	 * @param string
	 */
	public void setExtAcctNo(String string)
	{
		ExtAcctNo = string;
	}
	/**
	 * @param l
	 */
	public void setExtendFormID(long l)
	{
		ExtendFormID = l;
	}
	/**
	 * @param l
	 */
	public void setID(long l)
	{
		ID = l;
	}
	/**
	 * @param Timestamp
	 */
	public void setInputDate(Timestamp Timestamp)
	{
		InputDate = Timestamp;
	}
	/**
	 * @param l
	 */
	public void setInputUserID(long l)
	{
		InputUserID = l;
	}
	/**
	 * @param Timestamp
	 */
	public void setInterestStart(Timestamp Timestamp)
	{
		InterestStart = Timestamp;
	}
	/**
	 * @param d
	 */
	public void setInterestTaxRate(double d)
	{
		InterestTaxRate = d;
	}
	/**
	 * @param Timestamp
	 */
	public void setInterestTaxRateVauleDate(Timestamp Timestamp)
	{
		InterestTaxRateVauleDate = Timestamp;
	}
	/**
	 * @param b
	 */
	public void setKeepAccount(boolean b)
	{
		KeepAccount = b;
	}
	/**
	 * @param l
	 */
	public void setLoanAccountID(long l)
	{
		LoanAccountID = l;
	}
	/**
	 * @param l
	 */
	public void setLoanContractID(long l)
	{
		LoanContractID = l;
	}
	/**
	 * @param l
	 */
	public void setLoanNoteID(long l)
	{
		LoanNoteID = l;
	}
	/**
	 * @param Timestamp
	 */
	public void setModify(Timestamp Timestamp)
	{
		Modify = Timestamp;
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
	public void setPayCommisionAccountID(long l)
	{
		PayCommisionAccountID = l;
	}
	/**
	 * @param l
	 */
	public void setPayInterestAccountID(long l)
	{
		PayInterestAccountID = l;
	}
	/**
	 * @param l
	 */
	public void setPaySuretyFeeAccountID(long l)
	{
		PaySuretyFeeAccountID = l;
	}
	/**
	 * @param l
	 */
	public void setPayTypeID(long l)
	{
		PayTypeID = l;
	}
	/**
	 * @param string
	 */
	public void setProvince(String string)
	{
		Province = string;
	}
	/**
	 * @param l
	 */
	public void setReceiveInterestAccountID(long l)
	{
		ReceiveInterestAccountID = l;
	}
	/**
	 * @param l
	 */
	public void setReceiveSuretyFeeAccountID(long l)
	{
		ReceiveSuretyFeeAccountID = l;
	}
	/**
	 * Sets the statusID.
	 * @param statusID The statusID to set
	 */
	public void setStatusID(long statusID)
	{
		this.StatusID = new long[] { statusID };
	}
	/**
	 * 用于当前Entity作为查询条件时
	 * @param statusIDs
	 */
	public void setStatusID(long[] statusIDs)
	{
		this.StatusID = statusIDs;
	}
	/**
	 * @param l
	 */
	public void setTransactionTypeID(long l)
	{
		TransactionTypeID = l;
	}
	/**
	 * @param l
	 */
	public void setTransNo(String s)
	{
		TransNo = s;
	}
	/**
	 * @return
	 */
	public long getDepositAccountID()
	{
		return DepositAccountID;
	}
	/**
	 * @param l
	 */
	public void setDepositAccountID(long l)
	{
		DepositAccountID = l;
	}
	/**
	 * @return
	 */
	public long getConsignDepositAccountID()
	{
		return ConsignDepositAccountID;
	}
	/**
	 * @param l
	 */
	public void setConsignDepositAccountID(long l)
	{
		ConsignDepositAccountID = l;
	}
	public boolean equals(Object obj)
	{
		boolean returnValue = false;
		if (obj != null)
		{
			if (obj == this)
			{
				returnValue = true;
			}
			else
				if (obj instanceof TransGrantLoanInfo && ((TransGrantLoanInfo) obj).getID() == this.getID())
				{
					returnValue = true;
				}
		}
		return returnValue;
	}
	/**
	 * @return
	 */
	public long getAscOrDesc()
	{
		return AscOrDesc;
	}
	/**
	 * @return
	 */
	public long getOrderByType()
	{
		return OrderByType;
	}
	/**
	 * @param l
	 */
	public void setAscOrDesc(long l)
	{
		AscOrDesc = l;
	}
	/**
	 * @param l
	 */
	public void setOrderByType(long l)
	{
		OrderByType = l;
	}
	/**
	 * @return Returns the ExtBankNo.
	 */
	public String getExtBankNo()
	{
		return ExtBankNo;
	}
	/**
	 * @param extBankNo The ExtBankNo to set.
	 */
	public void setExtBankNo(String extBankNo)
	{
		ExtBankNo = extBankNo;
	}
	/**
	 * Returns the commissionRate.
	 * @return double
	 */
	public double getCommissionRate()
	{
		return CommissionRate;
	}

	/**
	 * Sets the commissionRate.
	 * @param commissionRate The commissionRate to set
	 */
	public void setCommissionRate(double commissionRate)
	{
		CommissionRate = commissionRate;
	}

	

	/**
	 * @return
	 */
	public ArrayList getSyndicationLoan() {
		return SyndicationLoan;
	}

	/**
	 * @param list
	 */
	public void setSyndicationLoan(ArrayList list) {
		SyndicationLoan = list;
	}
	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}
	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}

}
