
package com.iss.itreasury.settlement.transmargindeposit.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;

/**
 * @author gqfang 保存证金存款开立交易实体类： 1、所有变量都为Private,设置只能用setXXX方法，得到只能用getXXX方法。
 *         2、包含变量、类型、默认值、说明 To change the template for this generated type
 *         comment go to Window>Preferences>Java>Code Generation>Code and
 *         Comments
 */
public class TransMarginOpenInfo implements Serializable
{

	// 主信息
	private long		ID									= -1;	// 唯一标识

	private long		OfficeID							= -1;	// 办事处标识

	private long		CurrencyID							= -1;	// 币种标识

	private String		TransNo								= "";	// 交易编号

	private long		TransactionTypeID					= -1;	// 交易类型（保证金开立、保证金支取）

	private long		ClientID							= -1;	// 保证金客户标识

	private String		ClientNo							= "";	// 保证金客户编号

	private String		ClientName							= "";	// 保证金客户名称

	private long		AccountID							= -1;	// 保证金账户ID

	private String		AccountNo							= "";	// 保证金账户编号

	private long		ContractID							= -1;	// 合同编号

	private long		LoanNoticeID						= -1;	// 担保收款通知单ID

	private String		DepositNo							= "";	// 存单编号（子保证金账户编号）

	private String		CertificationNo						= "";	// 证实书号

	private long		CertificationBankID					= -1;	// 证实书发放银行ID

	private double		Rate								= 0.0;	// 利率

	private Timestamp	StartDate							= null; // 开始(起始)日期

	private Timestamp	EndDate								= null; // 结束(截止)日期

	private long		DepositTerm							= -1;	// 定期存款期限（月）

	private long		InterestPlanID						= -1;	// 利率计划

	private long		NoticeDay							= -1;	// 通知存款支取日期（天）/通知存款类型（1天/7天）

	private long		CurrentAccountID					= -1;	// 付活期账户ID(保证金)

	private String		CurrentAccountNo					= "";	// 付活期账户号(保证金)

	private String		CurrentAccountClientName			= "";	// 付活期账户客户名称(保证金)

	private long		BankID								= -1;	// 付银行ID(保证金)

	private String		BankName							= "";	// 付银行名称(保证金)

	private long		CashFlowID							= -1;	// 现金流向ID(保证金)

	private String		CashFlowDesc						= "";	// 现金流向描述(保证金)

	private double		Amount								= 0.0;	// 交易金额(保证金)

	private long		CommissionCurrentAccountID			= -1;	// 付活期账户ID(手续费)

	private String		CommissionCurrentAccountNo			= "";	// 付活期账户号(手续费)

	private String		CommissionCurrentAccountClientName	= "";	// 付活期账户客户名称(手续费)

	private long		CommissionBankID					= -1;	// 付银行ID(手续费)

	private String		CommissionBankName					= "";	// 付银行名称(手续费)

	private long		CommissionCashFlowID				= -1;	// 现金流向ID(手续费)

	private String		CommissionCashFlowDesc				= "";	// 现金流向描述(手续费)

	private double		CommissionAmount					= 0.0;	// 交易金额(手续费)

	private Timestamp	InterestStartDate					= null; // 起息日

	private Timestamp	ExecuteDate							= null; // 执行日

	private Timestamp	InputDate							= null; // 录入日

	private Timestamp	ModifyDate							= null; // 修改时间：时分秒

	private long		AbstractID							= -1;	// 摘要ID

	private String		Abstract							= "";	// 摘要

	private long		InputUserID							= -1;	// 录入人ID

	private String		InputUserName						= "";	// 录入人名称

	private long		CheckUserID							= -1;	// 复核人ID

	private String		CheckUserName						= "";	// 复核人名称

	private String		CheckAbstract						= "";	// 复核/取消复核摘要

	private long		StatusID							= -1;	// 交易状态

	private String		InstructionNo						= "";	// 标识非结算系统产生的流水号

	private String		ConsignVoucherNo					= "";	// 委托付款凭证号

	private String		ConsignPassword						= "";	// 委托付款凭证号密码

	private String		BillNo								= "";	// 票据号

	private long		BillTypeID							= -1;	// 票据类型ID

	private long		BillBankID							= -1;	// 票据发放银行ID

	private long		ExtAcctID							= -1;	// 非财务公司账户ID

	private String		ExtAcctNo							= "";	// 非财务公司账户号

	private String		ExtClientName						= "";	// 非财务公司客户名称

	private String		RemitInBank							= "";	// 非财务公司银行名称

	private String		RemitInProvince						= "";	// 省

	private String		RemitInCity							= "";	// 市

	private String		ExtBankNo							= "";	// 提入提出行号

	private String		SealNo								= "";	// 印鉴卡号

	private long		SealBankID							= -1;	// 印鉴卡发放银行ID

	private long		isInterest							= -1;	// 是否计息

	private long		interestAccountID					= -1;	// 收息方活期账户

	private InutParameterInfo inutParameterInfo = null;
	
	public long getInterestAccountID()
	{

		return interestAccountID;
	}


	public void setInterestAccountID(long interestAccountID)
	{

		this.interestAccountID = interestAccountID;
	}


	public long getIsInterest()
	{

		return isInterest;
	}


	public void setIsInterest(long isInterest)
	{

		this.isInterest = isInterest;
	}


	/**
	 * 获取 -- 摘要
	 * 
	 * @return
	 */
	public String getAbstract()
	{

		return Abstract;
	}


	/**
	 * 获取 -- 主定期账户ID
	 * 
	 * @return
	 */
	public long getAccountID()
	{

		return AccountID;
	}


	/**
	 * 获取 -- 主定期账户编号
	 * 
	 * @return
	 */
	public String getAccountNo()
	{

		return AccountNo;
	}


	/**
	 * 获取 -- 交易金额
	 * 
	 * @return
	 */
	public double getAmount()
	{

		return Amount;
	}


	/**
	 * 获取 -- 收/付银行ID
	 * 
	 * @return
	 */
	public long getBankID()
	{

		return BankID;
	}


	/**
	 * 获取 -- 收/付银行名称
	 * 
	 * @return
	 */
	public String getBankName()
	{

		return BankName;
	}


	/**
	 * 获取 -- 票据发放银行ID
	 * 
	 * @return
	 */
	public long getBillBankID()
	{

		return BillBankID;
	}


	/**
	 * 获取 -- 票据号
	 * 
	 * @return
	 */
	public String getBillNo()
	{

		return BillNo;
	}


	/**
	 * 获取 -- 票据类型ID
	 * 
	 * @return
	 */
	public long getBillTypeID()
	{

		return BillTypeID;
	}


	/**
	 * 获取 -- 现金流向描述
	 * 
	 * @return
	 */
	public String getCashFlowDesc()
	{

		return CashFlowDesc;
	}


	/**
	 * 获取 -- 现金流向ID
	 * 
	 * @return
	 */
	public long getCashFlowID()
	{

		return CashFlowID;
	}


	/**
	 * 获取 -- 证实书发放银行ID
	 * 
	 * @return
	 */
	public long getCertificationBankID()
	{

		return CertificationBankID;
	}


	/**
	 * 获取 -- 证实书号
	 * 
	 * @return
	 */
	public String getCertificationNo()
	{

		return CertificationNo;
	}


	/**
	 * 获取 -- 复核/取消复核摘要
	 * 
	 * @return
	 */
	public String getCheckAbstract()
	{

		return CheckAbstract;
	}


	/**
	 * 获取 -- 复核人ID
	 * 
	 * @return
	 */
	public long getCheckUserID()
	{

		return CheckUserID;
	}


	/**
	 * 获取 -- 复核人名称
	 * 
	 * @return
	 */
	public String getCheckUserName()
	{

		return CheckUserName;
	}


	/**
	 * 获取 -- 市
	 * 
	 * @return
	 */
	public String getRemitInCity()
	{

		return RemitInCity;
	}


	/**
	 * 获取 -- 定期客户标识
	 * 
	 * @return
	 */
	public long getClientID()
	{

		return ClientID;
	}


	/**
	 * 获取 -- 定期客户名称
	 * 
	 * @return
	 */
	public String getClientName()
	{

		return ClientName;
	}


	/**
	 * 获取 -- 定期客户编号
	 * 
	 * @return
	 */
	public String getClientNo()
	{

		return ClientNo;
	}


	/**
	 * 获取 -- 委托付款凭证号密码
	 * 
	 * @return
	 */
	public String getConsignPassword()
	{

		return ConsignPassword;
	}


	/**
	 * 获取 -- 委托付款凭证号
	 * 
	 * @return
	 */
	public String getConsignVoucherNo()
	{

		return ConsignVoucherNo;
	}


	/**
	 * 获取 -- 币种标识
	 * 
	 * @return
	 */
	public long getCurrencyID()
	{

		return CurrencyID;
	}


	/**
	 * 获取 -- 收/付活期账户客户名称
	 * 
	 * @return
	 */
	public String getCurrentAccountClientName()
	{

		return CurrentAccountClientName;
	}


	/**
	 * 获取 -- 收/付活期账户ID
	 * 
	 * @return
	 */
	public long getCurrentAccountID()
	{

		return CurrentAccountID;
	}


	/**
	 * 获取 -- 收/付活期账户号
	 * 
	 * @return
	 */
	public String getCurrentAccountNo()
	{

		return CurrentAccountNo;
	}


	/**
	 * 获取 -- 存单编号（子定期账户编号）
	 * 
	 * @return
	 */
	public String getDepositNo()
	{

		return DepositNo;
	}


	/**
	 * 获取 -- 定期存款期限（月）
	 * 
	 * @return
	 */
	public long getDepositTerm()
	{

		return DepositTerm;
	}


	/**
	 * 获取 -- 结束(截止)日期
	 * 
	 * @return
	 */
	public Timestamp getEndDate()
	{

		return EndDate;
	}


	/**
	 * 获取 -- 执行日
	 * 
	 * @return
	 */
	public Timestamp getExecuteDate()
	{

		return ExecuteDate;
	}


	/**
	 * 获取 -- 非财务公司账户ID
	 * 
	 * @return
	 */
	public long getExtAcctID()
	{

		return ExtAcctID;
	}


	/**
	 * 获取 -- 非财务公司账户名称
	 * 
	 * @return
	 */
	public String getExtClientName()
	{

		return ExtClientName;
	}


	/**
	 * 获取 -- 非财务公司账户号
	 * 
	 * @return
	 */
	public String getExtAcctNo()
	{

		return ExtAcctNo;
	}


	/**
	 * 获取 -- 提入提出行号
	 * 
	 * @return
	 */
	public String getExtBankNo()
	{

		return ExtBankNo;
	}


	/**
	 * 获取 -- 唯一标识
	 * 
	 * @return
	 */
	public long getID()
	{

		return ID;
	}


	/**
	 * 获取 -- 录入人ID
	 * 
	 * @return
	 */
	public long getInputUserID()
	{

		return InputUserID;
	}


	/**
	 * 获取 -- 录入人名称
	 * 
	 * @return
	 */
	public String getInputUserName()
	{

		return InputUserName;
	}


	/**
	 * 获取 -- 标识非结算系统产生的流水号
	 * 
	 * @return
	 */
	public String getInstructionNo()
	{

		return InstructionNo;
	}


	/**
	 * 获取 -- 利率计划
	 * 
	 * @return
	 */
	public long getInterestPlanID()
	{

		return InterestPlanID;
	}


	/**
	 * 获取 -- 起息日
	 * 
	 * @return
	 */
	public Timestamp getInterestStartDate()
	{

		return InterestStartDate;
	}


	/**
	 * 获取 -- 修改时间：时分秒
	 * 
	 * @return
	 */
	public Timestamp getModifyDate()
	{

		return ModifyDate;
	}


	/**
	 * 获取 -- 通知存款支取日期（天）
	 * 
	 * @return
	 */
	public long getNoticeDay()
	{

		return NoticeDay;
	}


	/**
	 * 获取 -- 办事处标识
	 * 
	 * @return
	 */
	public long getOfficeID()
	{

		return OfficeID;
	}


	/**
	 * 获取 -- 省
	 * 
	 * @return
	 */
	public String getRemitInProvince()
	{

		return RemitInProvince;
	}


	/**
	 * 获取 -- 利率
	 * 
	 * @return
	 */
	public double getRate()
	{

		return Rate;
	}


	/**
	 * 获取 -- 印鉴卡发放银行ID
	 * 
	 * @return
	 */
	public long getSealBankID()
	{

		return SealBankID;
	}


	/**
	 * 获取 -- 印鉴卡号
	 * 
	 * @return
	 */
	public String getSealNo()
	{

		return SealNo;
	}


	/**
	 * 获取 -- 开始(起始)日期
	 * 
	 * @return
	 */
	public Timestamp getStartDate()
	{

		return StartDate;
	}


	/**
	 * 获取 -- 交易状态
	 * 
	 * @return
	 */
	public long getStatusID()
	{

		return StatusID;
	}


	/**
	 * 获取 -- 交易类型（定期开立、定期支取、定期续期转存、通知开立、通知支取）
	 * 
	 * @return
	 */
	public long getTransactionTypeID()
	{

		return TransactionTypeID;
	}


	/**
	 * 获取 -- 交易编号
	 * 
	 * @return
	 */
	public String getTransNo()
	{

		return TransNo;
	}


	/**
	 * 设置 -- 摘要
	 * 
	 * @param string
	 */
	public void setAbstract(String string)
	{

		if (string != null) {
			Abstract = string;
		}
	}


	/**
	 * 设置 -- 主定期账户ID
	 * 
	 * @param l
	 */
	public void setAccountID(long l)
	{

		AccountID = l;
	}


	/**
	 * 设置 -- 主定期账户编号
	 * 
	 * @param string
	 */
	public void setAccountNo(String string)
	{

		if (string != null) {
			AccountNo = string;
		}
	}


	/**
	 * 设置 -- 交易金额
	 * 
	 * @param d
	 */
	public void setAmount(double d)
	{

		Amount = d;
	}


	/**
	 * 设置 -- 收/付银行ID
	 * 
	 * @param l
	 */
	public void setBankID(long l)
	{

		BankID = l;
	}


	/**
	 * 设置 -- 收/付银行名称
	 * 
	 * @param string
	 */
	public void setBankName(String string)
	{

		if (string != null) {
			BankName = string;
		}
	}


	/**
	 * 设置 -- 票据发放银行ID
	 * 
	 * @param l
	 */
	public void setBillBankID(long l)
	{

		BillBankID = l;
	}


	/**
	 * 设置 -- 票据号
	 * 
	 * @param string
	 */
	public void setBillNo(String string)
	{

		if (string != null) {
			BillNo = string;
		}
	}


	/**
	 * 设置 -- 票据类型ID
	 * 
	 * @param l
	 */
	public void setBillTypeID(long l)
	{

		BillTypeID = l;
	}


	/**
	 * 设置 -- 现金流向描述
	 * 
	 * @param string
	 */
	public void setCashFlowDesc(String string)
	{

		if (string != null) {
			CashFlowDesc = string;
		}
	}


	/**
	 * 设置 -- 现金流向ID
	 * 
	 * @param l
	 */
	public void setCashFlowID(long l)
	{

		CashFlowID = l;
	}


	/**
	 * 设置 -- 证实书发放银行ID
	 * 
	 * @param l
	 */
	public void setCertificationBankID(long l)
	{

		CertificationBankID = l;
	}


	/**
	 * 设置 -- 证实书号
	 * 
	 * @param string
	 */
	public void setCertificationNo(String string)
	{

		if (string != null) {
			CertificationNo = string;
		}
	}


	/**
	 * 设置 -- 复核/取消复核摘要
	 * 
	 * @param string
	 */
	public void setCheckAbstract(String string)
	{

		if (string != null) {
			CheckAbstract = string;
		}
	}


	/**
	 * 设置 -- 复核人ID
	 * 
	 * @param l
	 */
	public void setCheckUserID(long l)
	{

		CheckUserID = l;
	}


	/**
	 * 设置 -- 复核人名称
	 * 
	 * @param string
	 */
	public void setCheckUserName(String string)
	{

		if (string != null) {
			CheckUserName = string;
		}
	}


	/**
	 * 设置 -- 市
	 * 
	 * @param string
	 */
	public void setRemitInCity(String string)
	{

		if (string != null) {
			RemitInCity = string;
		}
	}


	/**
	 * 设置 -- 定期客户标识
	 * 
	 * @param l
	 */
	public void setClientID(long l)
	{

		ClientID = l;
	}


	/**
	 * 设置 -- 定期客户名称
	 * 
	 * @param string
	 */
	public void setClientName(String string)
	{

		if (string != null) {
			ClientName = string;
		}
	}


	/**
	 * 设置 -- 定期客户编号
	 * 
	 * @param string
	 */
	public void setClientNo(String string)
	{

		if (string != null) {
			ClientNo = string;
		}
	}


	/**
	 * 设置 -- 委托付款凭证号密码
	 * 
	 * @param string
	 */
	public void setConsignPassword(String string)
	{

		if (string != null) {
			ConsignPassword = string;
		}
	}


	/**
	 * 设置 -- 委托付款凭证号
	 * 
	 * @param string
	 */
	public void setConsignVoucherNo(String string)
	{

		if (string != null) {
			ConsignVoucherNo = string;
		}
	}


	/**
	 * 设置 -- 币种标识
	 * 
	 * @param l
	 */
	public void setCurrencyID(long l)
	{

		CurrencyID = l;
	}


	/**
	 * 设置 -- 收/付活期账户客户名称
	 * 
	 * @param string
	 */
	public void setCurrentAccountClientName(String string)
	{

		if (string != null) {
			CurrentAccountClientName = string;
		}
	}


	/**
	 * 设置 -- 收/付活期账户ID
	 * 
	 * @param l
	 */
	public void setCurrentAccountID(long l)
	{

		CurrentAccountID = l;
	}


	/**
	 * 设置 -- 收/付活期账户号
	 * 
	 * @param string
	 */
	public void setCurrentAccountNo(String string)
	{

		if (string != null) {
			CurrentAccountNo = string;
		}
	}


	/**
	 * 设置 -- 存单编号（子定期账户编号）
	 * 
	 * @param string
	 */
	public void setDepositNo(String string)
	{

		if (string != null) {
			DepositNo = string;
		}
	}


	/**
	 * 设置 -- 定期存款期限（月）
	 * 
	 * @param l
	 */
	public void setDepositTerm(long l)
	{

		DepositTerm = l;
	}


	/**
	 * 设置 -- 结束(截止)日期
	 * 
	 * @param timestamp
	 */
	public void setEndDate(Timestamp timestamp)
	{

		EndDate = timestamp;
	}


	/**
	 * 设置 -- 执行日
	 * 
	 * @param timestamp
	 */
	public void setExecuteDate(Timestamp timestamp)
	{

		ExecuteDate = timestamp;
	}


	/**
	 * 设置 -- 非财务公司账户ID
	 * 
	 * @param l
	 */
	public void setExtAcctID(long l)
	{

		ExtAcctID = l;
	}


	/**
	 * 设置 -- 非财务公司账户名称
	 * 
	 * @param string
	 */
	public void setExtClientName(String string)
	{

		if (string != null) {
			ExtClientName = string;
		}
	}


	/**
	 * 设置 -- 非财务公司账户号
	 * 
	 * @param string
	 */
	public void setExtAcctNo(String string)
	{

		if (string != null) {
			ExtAcctNo = string;
		}
	}


	/**
	 * 设置 -- 提入提出行号
	 * 
	 * @param string
	 */
	public void setExtBankNo(String string)
	{

		if (string != null) {
			ExtBankNo = string;
		}
	}


	/**
	 * 设置 -- 唯一标识
	 * 
	 * @param l
	 */
	public void setID(long l)
	{

		ID = l;
	}


	/**
	 * 设置 -- 录入人ID
	 * 
	 * @param l
	 */
	public void setInputUserID(long l)
	{

		InputUserID = l;
	}


	/**
	 * 设置 -- 录入人名称
	 * 
	 * @param string
	 */
	public void setInputUserName(String string)
	{

		if (string != null) {
			InputUserName = string;
		}
	}


	/**
	 * 设置 -- 标识非结算系统产生的流水号
	 * 
	 * @param string
	 */
	public void setInstructionNo(String string)
	{

		if (string != null) {
			InstructionNo = string;
		}
	}


	/**
	 * 设置 -- 利率计划
	 * 
	 * @param l
	 */
	public void setInterestPlanID(long l)
	{

		InterestPlanID = l;
	}


	/**
	 * 设置 -- 起息日
	 * 
	 * @param timestamp
	 */
	public void setInterestStartDate(Timestamp timestamp)
	{

		InterestStartDate = timestamp;
	}


	/**
	 * 设置 -- 修改时间：时分秒
	 * 
	 * @param timestamp
	 */
	public void setModifyDate(Timestamp timestamp)
	{

		ModifyDate = timestamp;
	}


	/**
	 * 设置 -- 通知存款支取日期（天）
	 * 
	 * @param l
	 */
	public void setNoticeDay(long l)
	{

		NoticeDay = l;
	}


	/**
	 * 设置 -- 办事处标识
	 * 
	 * @param l
	 */
	public void setOfficeID(long l)
	{

		OfficeID = l;
	}


	/**
	 * 设置 -- 省
	 * 
	 * @param string
	 */
	public void setRemitInProvince(String string)
	{

		if (string != null) {
			RemitInProvince = string;
		}
	}


	/**
	 * 设置 -- 利率
	 * 
	 * @param d
	 */
	public void setRate(double d)
	{

		Rate = d;
	}


	/**
	 * 设置 -- 印鉴卡发放银行ID
	 * 
	 * @param l
	 */
	public void setSealBankID(long l)
	{

		SealBankID = l;
	}


	/**
	 * 设置 -- 印鉴卡号
	 * 
	 * @param string
	 */
	public void setSealNo(String string)
	{

		if (string != null) {
			SealNo = string;
		}
	}


	/**
	 * 设置 -- 开始(起始)日期
	 * 
	 * @param timestamp
	 */
	public void setStartDate(Timestamp timestamp)
	{

		StartDate = timestamp;
	}


	/**
	 * 设置 -- 交易状态
	 * 
	 * @param l
	 */
	public void setStatusID(long l)
	{

		StatusID = l;
	}


	/**
	 * 设置 -- 交易类型（定期开立、定期支取、定期续期转存、通知开立、通知支取）
	 * 
	 * @param l
	 */
	public void setTransactionTypeID(long l)
	{

		TransactionTypeID = l;
	}


	/**
	 * 设置 -- 交易编号
	 * 
	 * @param string
	 */
	public void setTransNo(String string)
	{

		if (string != null) {
			TransNo = string;
		}
	}


	/**
	 * 得到摘要ID
	 * 
	 * @return
	 */
	public long getAbstractID()
	{

		return AbstractID;
	}


	/**
	 * 设置摘要ID
	 * 
	 * @param l
	 */
	public void setAbstractID(long l)
	{

		AbstractID = l;
	}


	/**
	 * 得到非财务公司银行名称
	 * 
	 * @return
	 */
	public String getRemitInBank()
	{

		return RemitInBank;
	}


	/**
	 * 设置非财务公司银行名称
	 * 
	 * @param string
	 */
	public void setRemitInBank(String string)
	{

		RemitInBank = string;
	}


	/**
	 * 得到录入日期
	 * 
	 * @return
	 */
	public Timestamp getInputDate()
	{

		return InputDate;
	}


	/**
	 * 设置录入日期
	 * 
	 * @param timestamp
	 */
	public void setInputDate(Timestamp timestamp)
	{

		InputDate = timestamp;
	}


	public long getLoanNoticeID()
	{

		return LoanNoticeID;
	}


	public void setLoanNoticeID(long loanNoticeID)
	{

		LoanNoticeID = loanNoticeID;
	}


	public long getContractID()
	{

		return ContractID;
	}


	public void setContractID(long contractID)
	{

		ContractID = contractID;
	}


	public double getCommissionAmount()
	{

		return CommissionAmount;
	}


	public void setCommissionAmount(double commissionAmount)
	{

		CommissionAmount = commissionAmount;
	}


	public long getCommissionBankID()
	{

		return CommissionBankID;
	}


	public void setCommissionBankID(long commissionBankID)
	{

		CommissionBankID = commissionBankID;
	}


	public String getCommissionBankName()
	{

		return CommissionBankName;
	}


	public void setCommissionBankName(String commissionBankName)
	{

		CommissionBankName = commissionBankName;
	}


	public String getCommissionCashFlowDesc()
	{

		return CommissionCashFlowDesc;
	}


	public void setCommissionCashFlowDesc(String commissionCashFlowDesc)
	{

		CommissionCashFlowDesc = commissionCashFlowDesc;
	}


	public long getCommissionCashFlowID()
	{

		return CommissionCashFlowID;
	}


	public void setCommissionCashFlowID(long commissionCashFlowID)
	{

		CommissionCashFlowID = commissionCashFlowID;
	}


	public String getCommissionCurrentAccountClientName()
	{

		return CommissionCurrentAccountClientName;
	}


	public void setCommissionCurrentAccountClientName(String commissionCurrentAccountClientName)
	{

		CommissionCurrentAccountClientName = commissionCurrentAccountClientName;
	}


	public long getCommissionCurrentAccountID()
	{

		return CommissionCurrentAccountID;
	}


	public void setCommissionCurrentAccountID(long commissionCurrentAccountID)
	{

		CommissionCurrentAccountID = commissionCurrentAccountID;
	}


	public String getCommissionCurrentAccountNo()
	{

		return CommissionCurrentAccountNo;
	}


	public void setCommissionCurrentAccountNo(String commissionCurrentAccountNo)
	{

		CommissionCurrentAccountNo = commissionCurrentAccountNo;
	}


	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}


	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}

}