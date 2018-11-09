
package com.iss.itreasury.settlement.transmargindeposit.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;

/**
 * @author gqfang 保证金存款支取交易实体类： 1、所有变量都为Private,设置只能用setXXX方法，得到只能用getXXX方法。
 *         2、包含变量、类型、默认值、说明 To change the template for this generated type
 *         comment go to Window>Preferences>Java>Code Generation>Code and
 *         Comments
 */
public class TransMarginWithdrawInfo implements Serializable
{

	// 主信息
	private long		ID									= -1;	// 唯一标识

	private long		OfficeID							= -1;	// 办事处标识

	private long		CurrencyID							= -1;	// 币种标识

	private String		TransNo								= "";	// 交易编号

	private long		TransactionTypeID					= -1;	// 交易类型（定期开立、定期支取、定期续期转存、通知开立、通知支取）

	private long		ClientID							= -1;	// 定期客户标识

	private String		ClientNo							= "";	// 定期客户编号

	private String		ClientName							= "";	// 定期客户名称

	private long		AccountID							= -1;	// 定期账户ID

	private String		AccountNo							= "";	// 定期账户编号

	private String		DepositNo							= "";	// 存单编号（子定期账户编号）

	private long		ContractID							= -1;	// 合同编号

	private long		LoanNoticeID						= -1;	// 担保收款通知单ID

	private String		CertificationNo						= "";	// 证实书号

	private long		CertificationBankID					= -1;	// 证实书发放银行ID

	private double		Rate								= 0.0;	// 利率

	private Timestamp	StartDate							= null; // 开始(起始)日期

	private Timestamp	EndDate								= null; // 结束(截止)日期

	private long		DepositTerm							= -1;	// 定期存款期限（月）

	private long		InterestPlanID						= -1;	// 利率计划

	private long		NoticeDay							= -1;	// 通知存款支取日期（天）/通知存款类型（1天/7天）

	private long		SubAccountID						= -1;	// 子定期账户ID

	private long		CurrentAccountID					= -1;	// 本金收活期账户ID

	private String		CurrentAccountNo					= "";	// 本金收活期账户号

	private String		CurrentAccountClientName			= "";	// 本金收活期账户客户名称

	private long		PayTypeID							= -1;	// 本金付款方式

	private long		BankID								= -1;	// 收银行ID

	private String		BankName							= "";	// 收银行名称

	// private long ExtAcctID = -1; //本金非财务公司账户ID
	private String		ExtAcctNo							= "";	// 本金非财务公司账户号

	private String		ExtClientName						= "";	// 本金非财务公司客户名称

	private String		RemitInBank							= "";	// 本金非财务公司银行名称

	private String		RemitInProvince						= "";	// 省

	private String		RemitInCity							= "";	// 市

	private long		CashFlowID							= -1;	// 现金流向ID

	private String		CashFlowDesc						= "";	// 现金流向描述

	private double		Amount								= 0.0;	// 本金

	private double		DrawAmount							= 0.0;	// 提前支取金额

	private double		DepositBalance						= 0.0;	// 存款余额

	private Timestamp	InterestStartDate					= null; // 起息日

	private Timestamp	ExecuteDate							= null; // 执行日

	private String		CapitalExtBankNo;							// 本金提入行号

	private String		SealNo								= "";	// 印鉴卡号

	private long		SealBankID							= -1;	// 印鉴卡发放银行ID

	private double		PreDrawInterest						= 0.0;	// 计提利息

	private double		StrikePreDrawInterest				= 0.0;	// 冲销计提利息

	private double		DrawInterest						= 0.0;	// 本次支取利息

	private long		IsPreDraw							= -1;	// 是否提前支取

	private long		IsTally								= -1;	// 是否记账

	private double		TotalUnpayInterest					= 0.0;	// 累计未结利息

	private double		PayableInterest						= 0.0;	// 利息支付

	private double		CurrentInterest						= 0.0;	// 活期利息

	private double		OtherInterest						= 0.0;	// 其它利息

	private double		TotalInterest						= 0.0;	// 利息合计

	private long		ReceiveInterestAccountID			= -1;	// 收息账户ID

	private String		ReceiveInterestAccountNo			= "";	// 收息账户号

	private String		ReceiveInterestAccountClientName	= "";	// 收息账户客户名称

	private long		InterestPayTypeID					= -1;	// 利息付款方式

	private long		InterestBankID						= -1;	// 利息付款银行ID

	private String		InterestBankName					= "";	// 利息付款银行名称

	// private long InterestExtAcctID = -1; //利息非财务公司账户号
	private String		InterestExtAcctNo					= "";	// 利息非财务公司账户号

	private String		InterestExtClientName				= "";	// 利息非财务公司客户名称

	private String		InterestRemitInBank					= "";	// 利息非财务公司银行名称

	private String		InterestRemitInProvince				= "";	// 省

	private String		InterestRemitInCity					= "";	// 市

	private long		InterestCashFlowID					= -1;	// 利息现金流向ID

	private String		InterestCashFlowDesc				= "";	// 利息现金流向描述

	private String		InterestExtBankNo;							// 利息提入行号

	private String		InterestExtBankName					= "";	// 利息提入行名称

	private long		ConfirmOfficeID						= -1;	// 通存通兑对方办事处ID

	private String		ConfirmOfficeName					= "";	// 通存通兑对方办事处名称

	private long		CapitalAndInterestDealWay			= -1;	// 本金/利息处理办法（汇总处理/分笔处理）

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

	private Timestamp	InputDate							= null; // 执行日

	private InutParameterInfo inutParameterInfo = null;
	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}


	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
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
	 * 获取 -- 本金/利息处理办法
	 * 
	 * @return
	 */
	public long getCapitalAndInterestDealWay()
	{

		return CapitalAndInterestDealWay;
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
	 * 获取 -- 存款余额
	 * 
	 * @return
	 */
	public double getDepositBalance()
	{

		return DepositBalance;
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
	 * 获取 -- 非财务公司银行名称
	 * 
	 * @return
	 */
	public String getRemitInBank()
	{

		return RemitInBank;
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
	 * 获取 -- 本金付款方式
	 * 
	 * @return
	 */
	public long getPayTypeID()
	{

		return PayTypeID;
	}


	/**
	 * 获取 -- 计提利息
	 * 
	 * @return
	 */
	public double getPreDrawInterest()
	{

		return PreDrawInterest;
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
	 * 获取 -- 子定期账户ID
	 * 
	 * @return
	 */
	public long getSubAccountID()
	{

		return SubAccountID;
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
	 * 设置 -- 本金/利息处理办法
	 * 
	 * @param l
	 */
	public void setCapitalAndInterestDealWay(long l)
	{

		CapitalAndInterestDealWay = l;
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
	 * 设置 -- 存款余额
	 * 
	 * @param d
	 */
	public void setDepositBalance(double d)
	{

		DepositBalance = d;
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
	 * 设置 -- 非财务公司银行名称
	 * 
	 * @param string
	 */
	public void setRemitInBank(String string)
	{

		if (string != null) {
			RemitInBank = string;
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
	 * 设置 -- 本金付款方式
	 * 
	 * @param l
	 */
	public void setPayTypeID(long l)
	{

		PayTypeID = l;
	}


	/**
	 * 设置 -- 计提利息
	 * 
	 * @param d
	 */
	public void setPreDrawInterest(double d)
	{

		PreDrawInterest = d;
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
	 * 设置 -- 子定期账户ID
	 * 
	 * @param l
	 */
	public void setSubAccountID(long l)
	{

		SubAccountID = l;
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
	 * 得到本金提入行号
	 * 
	 * @return
	 */
	public String getCapitalExtBankNo()
	{

		return CapitalExtBankNo;
	}


	/**
	 * 得到通存通兑对方办事处ID
	 * 
	 * @return
	 */
	public long getConfirmOfficeID()
	{

		return ConfirmOfficeID;
	}


	/**
	 * 得到通存通兑对方办事处名称
	 * 
	 * @return
	 */
	public String getConfirmOfficeName()
	{

		return ConfirmOfficeName;
	}


	/**
	 * 得到活期利息
	 * 
	 * @return
	 */
	public double getCurrentInterest()
	{

		return CurrentInterest;
	}


	/**
	 * 得到支取金额
	 * 
	 * @return
	 */
	public double getDrawAmount()
	{

		return DrawAmount;
	}


	/**
	 * 得到本次支取利息
	 * 
	 * @return
	 */
	public double getDrawInterest()
	{

		return DrawInterest;
	}


	/**
	 * 得到收息银行ID
	 * 
	 * @return
	 */
	public long getInterestBankID()
	{

		return InterestBankID;
	}


	/**
	 * 得到收息银行名称
	 * 
	 * @return
	 */
	public String getInterestBankName()
	{

		return InterestBankName;
	}


	/**
	 * 得到利息现金流向描述
	 * 
	 * @return
	 */
	public String getInterestCashFlowDesc()
	{

		return InterestCashFlowDesc;
	}


	/**
	 * 得到利息现金流向ID
	 * 
	 * @return
	 */
	public long getInterestCashFlowID()
	{

		return InterestCashFlowID;
	}


	/**
	 * 得到市（利息相关）
	 * 
	 * @return
	 */
	public String getInterestRemitInCity()
	{

		return InterestRemitInCity;
	}


	/**
	 * 得到利息非财务公司账户名称
	 * 
	 * @return
	 */
	public String getInterestExtClientName()
	{

		return InterestExtClientName;
	}


	/**
	 * 得到利息非财务公司账户号
	 * 
	 * @return
	 */
	public String getInterestExtAcctNo()
	{

		return InterestExtAcctNo;
	}


	/**
	 * 得到利息提入行名称
	 * 
	 * @return
	 */
	public String getInterestExtBankName()
	{

		return InterestExtBankName;
	}


	/**
	 * 得到利息提入行号
	 * 
	 * @return
	 */
	public String getInterestExtBankNo()
	{

		return InterestExtBankNo;
	}


	/**
	 * 得到利息付款方式ID
	 * 
	 * @return
	 */
	public long getInterestPayTypeID()
	{

		return InterestPayTypeID;
	}


	/**
	 * 得到省（利息相关）
	 * 
	 * @return
	 */
	public String getInterestRemitInProvince()
	{

		return InterestRemitInProvince;
	}


	/**
	 * 得到是否提前支取
	 * 
	 * @return
	 */
	public long getIsPreDraw()
	{

		return IsPreDraw;
	}


	/**
	 * 得到是否记账
	 * 
	 * @return
	 */
	public long getIsTally()
	{

		return IsTally;
	}


	/**
	 * 得到其他利息
	 * 
	 * @return
	 */
	public double getOtherInterest()
	{

		return OtherInterest;
	}


	/**
	 * 得到利息支付
	 * 
	 * @return
	 */
	public double getPayableInterest()
	{

		return PayableInterest;
	}


	/**
	 * 得到收息账户客户名称
	 * 
	 * @return
	 */
	public String getReceiveInterestAccountClientName()
	{

		return ReceiveInterestAccountClientName;
	}


	/**
	 * 得到收息账户ID
	 * 
	 * @return
	 */
	public long getReceiveInterestAccountID()
	{

		return ReceiveInterestAccountID;
	}


	/**
	 * 得到收息账户号
	 * 
	 * @return
	 */
	public String getReceiveInterestAccountNo()
	{

		return ReceiveInterestAccountNo;
	}


	/**
	 * 得到冲销计提利息
	 * 
	 * @return
	 */
	public double getStrikePreDrawInterest()
	{

		return StrikePreDrawInterest;
	}


	/**
	 * 得到累计未结利息
	 * 
	 * @return
	 */
	public double getTotalUnpayInterest()
	{

		return TotalUnpayInterest;
	}


	/**
	 * 设置本金提入行号
	 * 
	 * @param string
	 */
	public void setCapitalExtBankNo(String string)
	{

		CapitalExtBankNo = string;
	}


	/**
	 * 设置通存通兑对方办事处Id
	 * 
	 * @param l
	 */
	public void setConfirmOfficeID(long l)
	{

		ConfirmOfficeID = l;
	}


	/**
	 * 设置通存通兑对方办事处名称
	 * 
	 * @param string
	 */
	public void setConfirmOfficeName(String string)
	{

		ConfirmOfficeName = string;
	}


	/**
	 * 设置活期利息
	 * 
	 * @param d
	 */
	public void setCurrentInterest(double d)
	{

		CurrentInterest = d;
	}


	/**
	 * 设置支取金额
	 * 
	 * @param d
	 */
	public void setDrawAmount(double d)
	{

		DrawAmount = d;
	}


	/**
	 * 设置本次支取利息
	 * 
	 * @param d
	 */
	public void setDrawInterest(double d)
	{

		DrawInterest = d;
	}


	/**
	 * 设置收息银行ID
	 * 
	 * @param l
	 */
	public void setInterestBankID(long l)
	{

		InterestBankID = l;
	}


	/**
	 * 设置收息银行名称
	 * 
	 * @param string
	 */
	public void setInterestBankName(String string)
	{

		InterestBankName = string;
	}


	/**
	 * 设置利息现金流向描述
	 * 
	 * @param string
	 */
	public void setInterestCashFlowDesc(String string)
	{

		InterestCashFlowDesc = string;
	}


	/**
	 * 设置利息现金流向Id
	 * 
	 * @param l
	 */
	public void setInterestCashFlowID(long l)
	{

		InterestCashFlowID = l;
	}


	/**
	 * 设置市（利息相关）
	 * 
	 * @param string
	 */
	public void setInterestRemitInCity(String string)
	{

		InterestRemitInCity = string;
	}


	/**
	 * 设置非财务公司名称（利息相关）
	 * 
	 * @param string
	 */
	public void setInterestExtClientName(String string)
	{

		InterestExtClientName = string;
	}


	/**
	 * 设置非财务公司账户号（利息相关）
	 * 
	 * @param string
	 */
	public void setInterestExtAcctNo(String string)
	{

		InterestExtAcctNo = string;
	}


	/**
	 * 设置利息提入行名称
	 * 
	 * @param string
	 */
	public void setInterestExtBankName(String string)
	{

		InterestExtBankName = string;
	}


	/**
	 * 设置利息提入行号
	 * 
	 * @param string
	 */
	public void setInterestExtBankNo(String string)
	{

		InterestExtBankNo = string;
	}


	/**
	 * 设置利息付款方式
	 * 
	 * @param l
	 */
	public void setInterestPayTypeID(long l)
	{

		InterestPayTypeID = l;
	}


	/**
	 * 设置省（利息相关）
	 * 
	 * @param string
	 */
	public void setInterestRemitInProvince(String string)
	{

		InterestRemitInProvince = string;
	}


	/**
	 * 设置是否提前支取
	 * 
	 * @param l
	 */
	public void setIsPreDraw(long l)
	{

		IsPreDraw = l;
	}


	/**
	 * 设置是否记账
	 * 
	 * @param l
	 */
	public void setIsTally(long l)
	{

		IsTally = l;
	}


	/**
	 * 设置其他利息
	 * 
	 * @param d
	 */
	public void setOtherInterest(double d)
	{

		OtherInterest = d;
	}


	/**
	 * 设置利息支付
	 * 
	 * @param d
	 */
	public void setPayableInterest(double d)
	{

		PayableInterest = d;
	}


	/**
	 * 设置收息账户客户名称
	 * 
	 * @param string
	 */
	public void setReceiveInterestAccountClientName(String string)
	{

		ReceiveInterestAccountClientName = string;
	}


	/**
	 * 设置收息账户ID
	 * 
	 * @param l
	 */
	public void setReceiveInterestAccountID(long l)
	{

		ReceiveInterestAccountID = l;
	}


	/**
	 * 设置收息账户号
	 * 
	 * @param string
	 */
	public void setReceiveInterestAccountNo(String string)
	{

		ReceiveInterestAccountNo = string;
	}


	/**
	 * 设置冲销计提利息
	 * 
	 * @param d
	 */
	public void setStrikePreDrawInterest(double d)
	{

		StrikePreDrawInterest = d;
	}


	/**
	 * 设置累计未结利息
	 * 
	 * @param d
	 */
	public void setTotalUnpayInterest(double d)
	{

		TotalUnpayInterest = d;
	}


	/**
	 * 得到利息非财务公司银行名称
	 * 
	 * @return
	 */
	public String getInterestRemitInBank()
	{

		return InterestRemitInBank;
	}


	/**
	 * 设置利息非财务公司银行名称
	 * 
	 * @param string
	 */
	public void setInterestRemitInBank(String string)
	{

		InterestRemitInBank = string;
	}


	/**
	 * 得到定期支取合计利息
	 * 
	 * @return
	 */
	public double getTotalInterest()
	{

		TotalInterest = PayableInterest + PreDrawInterest + CurrentInterest + OtherInterest;
		return TotalInterest;
	}


	/**
	 * 得到通知支取合计利息
	 * 
	 * @return
	 */
	public double getNoticeTotalInterest()
	{

		TotalInterest = DrawInterest + TotalUnpayInterest;
		return TotalInterest;
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


	/**
	 * 利息合计
	 * 
	 * @param d
	 */
	public void setTotalInterest(double d)
	{

		TotalInterest = d;
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

}