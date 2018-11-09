/*
 * Created on 2003-9-9
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transfixeddeposit.dataentity;
import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
/**
 * @author xrli
 *	定期存款续期转存交易实体类：
 *	1、所有变量都为Private,设置只能用setXXX方法，得到只能用getXXX方法。
 *	2、包含变量、类型、默认值、说明
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransFixedContinueInfo implements Serializable
{
	//主信息
	private long ID = -1; //唯一标识
	private String TransNo = ""; //交易编号
	private long TransactionTypeID = -1; //交易类型（定期开立、定期支取、定期续期转存、通知开立、通知支取）
	private long OfficeID = -1; //办事处标识
	private long CurrencyID = -1; //币种标识
	private long ClientID = -1; //定期客户标识
	private String ClientNo = ""; //定期客户编号
	private String ClientName = ""; //定期客户名称
	private long AccountID = -1; //主定期账户ID
	private String AccountNo = ""; //主定期账户编号
	private String DepositNo = ""; //存单编号（子定期账户编号）
	private String CertificationNo = ""; //证实书号
	private long CertificationBankID = -1; //证实书发放银行ID
	private String SealNo = ""; //印鉴卡号
	private long SealBankID = -1; //印鉴卡发放银行ID
	private double Rate = 0.0; //利率
	private Timestamp StartDate = null; //开始(起始)日期
	private Timestamp EndDate = null; //结束(截止)日期
	private long DepositTerm = -1; //定期存款期限（月）
	private long InterestPlanID = -1; //利率计划
	private long SubAccountID = -1; //子账户ID
	private double Amount = 0.0; //本金金额
	private Timestamp ExecuteDate = null; //续期转存执行日
	
	private String NewDepositNo = ""; //新存单编号（子定期账户编号）
	private String NewCertificationNo = ""; //新证实书号
	private long NewCertificationBankID = -1; //新证实书发放银行ID
	private double NewRate = 0.0; //新存单利率
	private Timestamp NewStartDate = null; //新存单开始(起始)日期
	private Timestamp NewEndDate = null; //新存单结束(截止)日期
	private long NewDepositTerm = -1; //新存单定期存款期限（月）
	private long NewInterestPlanID = -1; //新存单利率计划
	private double NewAmount = 0.0; //新存单本金金额
	private String NewSealNo = ""; //新印鉴卡号
	private long NewSealBankID = -1; //新印鉴卡发放银行ID	
	private double PreDrawInterest = 0.0; //计提利息
	private double PayableInterest = 0.0; //利息支付
	private double WithDrawInterest = 0.0; //本次支取利息（利息合计）
	private long IsCapitalAndInterestTransfer = -1; //是否本利续存  1 是 -1 否
	private long ReceiveInterestAccountID = -1; //收息账户ID
	private String ReceiveInterestAccountNo = ""; //收息账户号
	private long InterestPayTypeID = -1; //利息付款方式
	private long InterestBankID = -1; //利息收银行ID
	private String InterestBankName = ""; //利息收银行名称
	private String InterestExtAcctNo = ""; //利息非财务公司账户号
	private String InterestExtClientName = ""; //利息非财务公司客户名称
	private String InterestRemitInBank = ""; //利息非财务公司银行名称
	private String InterestRemitInProvince = ""; //省
	private String InterestRemitInCity = ""; //市	
	private long InterestCashFlowID = -1; //利息现金流向ID
	private String InterestCashFlowDesc = ""; //利息现金流向描述
	private String InterestExtBankNo;  //利息提入行号	
	private Timestamp InterestStartDate = null; //起息日
	private Timestamp ModifyDate = null; //修改时间：时分秒
	private Timestamp InputDate = null; //录入日期
	private long AbstractID = -1; //摘要ID
	private String Abstract = ""; //摘要
	private long InputUserID = -1; //录入人ID
	private String InputUserName = ""; //录入人名称
	private long CheckUserID = -1; //复核人ID
	private String CheckUserName = ""; //复核人名称
	private String CheckAbstract = ""; //复核/取消复核摘要
	private long StatusID = -1; //交易状态
	private String InstructionNo = ""; //标识非结算系统产生的流水号		
	private InutParameterInfo inutParameterInfo = null;
	private double advanceRate = 0.0;  //到期续存活期利率
	private double CurrentInterest = 0.0; //活期利息
	private long isAutoContinue = -1;
	private long autocontinuetype = -1;
	private long autocontinueaccountid = -1;
	
	public double getCurrentInterest() {
		return CurrentInterest;
	}

	public void setCurrentInterest(double currentInterest) {
		CurrentInterest = currentInterest;
	}

	public double getAdvanceRate() {
		return advanceRate;
	}

	public void setAdvanceRate(double advanceRate) {
		this.advanceRate = advanceRate;
	}

	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}

	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}

	/**
	 * 获取 -- 摘要
	 * @return
	 */
	public String getAbstract()
	{
		return Abstract;
	}

	/**
	 * 获取 -- 主定期账户ID
	 * @return
	 */
	public long getAccountID()
	{
		return AccountID;
	}

	/**
	 * 获取 -- 主定期账户编号
	 * @return
	 */
	public String getAccountNo()
	{
		return AccountNo;
	}

	/**
	 * 获取 -- 交易金额
	 * @return
	 */
	public double getAmount()
	{
		return Amount;
	}

	/**
	 * 获取 -- 证实书发放银行ID
	 * @return
	 */
	public long getCertificationBankID()
	{
		return CertificationBankID;
	}

	/**
	 * 获取 -- 证实书号
	 * @return
	 */
	public String getCertificationNo()
	{
		return CertificationNo;
	}

	/**
	 * 获取 -- 复核/取消复核摘要
	 * @return
	 */
	public String getCheckAbstract()
	{
		return CheckAbstract;
	}

	/**
	 * 获取 -- 复核人ID
	 * @return
	 */
	public long getCheckUserID()
	{
		return CheckUserID;
	}

	/**
	 * 获取 -- 复核人名称
	 * @return
	 */
	public String getCheckUserName()
	{
		return CheckUserName;
	}

	/**
	 * 获取 -- 定期客户标识
	 * @return
	 */
	public long getClientID()
	{
		return ClientID;
	}

	/**
	 * 获取 -- 定期客户名称
	 * @return
	 */
	public String getClientName()
	{
		return ClientName;
	}

	/**
	 * 获取 -- 定期客户编号
	 * @return
	 */
	public String getClientNo()
	{
		return ClientNo;
	}	

	/**
	 * 获取 -- 币种标识
	 * @return
	 */
	public long getCurrencyID()
	{
		return CurrencyID;
	}


	/**
	 * 获取 -- 存单编号（子定期账户编号）
	 * @return
	 */
	public String getDepositNo()
	{
		return DepositNo;
	}

	/**
	 * 获取 -- 定期存款期限（月）
	 * @return
	 */
	public long getDepositTerm()
	{
		return DepositTerm;
	}

	/**
	 * 获取 -- 结束(截止)日期
	 * @return
	 */
	public Timestamp getEndDate()
	{
		return EndDate;
	}

	/**
	 * 获取 -- 执行日
	 * @return
	 */
	public Timestamp getExecuteDate()
	{
		return ExecuteDate;
	}



	/**
	 * 获取 -- 唯一标识
	 * @return
	 */
	public long getID()
	{
		return ID;
	}

	/**
	 * 获取 -- 录入人ID
	 * @return
	 */
	public long getInputUserID()
	{
		return InputUserID;
	}

	/**
	 * 获取 -- 录入人名称
	 * @return
	 */
	public String getInputUserName()
	{
		return InputUserName;
	}

	/**
	 * 获取 -- 标识非结算系统产生的流水号
	 * @return
	 */
	public String getInstructionNo()
	{
		return InstructionNo;
	}

	/**
	 * 获取 -- 利率计划
	 * @return
	 */
	public long getInterestPlanID()
	{
		return InterestPlanID;
	}

	/**
	 * 获取 -- 起息日
	 * @return
	 */
	public Timestamp getInterestStartDate()
	{
		return InterestStartDate;
	}

	/**
	 * 获取 -- 修改时间：时分秒
	 * @return
	 */
	public Timestamp getModifyDate()
	{
		return ModifyDate;
	}

	
	/**
	 * 获取 -- 办事处标识
	 * @return
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}	

	/**
	 * 获取 -- 计提利息
	 * @return
	 */
	public double getPreDrawInterest()
	{
		return PreDrawInterest;
	}	

	/**
	 * 获取 -- 利率
	 * @return
	 */
	public double getRate()
	{
		return Rate;
	}

	/**
	 * 获取 -- 印鉴卡发放银行ID
	 * @return
	 */
	public long getSealBankID()
	{
		return SealBankID;
	}

	/**
	 * 获取 -- 印鉴卡号
	 * @return
	 */
	public String getSealNo()
	{
		return SealNo;
	}

	/**
	 * 获取 -- 开始(起始)日期
	 * @return
	 */
	public Timestamp getStartDate()
	{
		return StartDate;
	}

	/**
	 * 获取 -- 交易状态
	 * @return
	 */
	public long getStatusID()
	{
		return StatusID;
	}

	/**
	 * 获取 -- 子定期账户ID
	 * @return
	 */
	public long getSubAccountID()
	{
		return SubAccountID;
	}

	/**
	 * 获取 -- 交易类型（定期开立、定期支取、定期续期转存、通知开立、通知支取）
	 * @return
	 */
	public long getTransactionTypeID()
	{
		return TransactionTypeID;
	}

	

	/**
	 * 获取 -- 交易编号
	 * @return
	 */
	public String getTransNo()
	{
		return TransNo;
	}

	/**
	 * 设置 -- 摘要
	 * @param string
	 */
	public void setAbstract(String string)
	{
		if (string != null)
		{
			Abstract = string;
		}
	}

	/**
	 * 设置 -- 主定期账户ID
	 * @param l
	 */
	public void setAccountID(long l)
	{
		AccountID = l;
	}

	/**
	 * 设置 -- 主定期账户编号
	 * @param string
	 */
	public void setAccountNo(String string)
	{
		if (string != null)
		{
			AccountNo = string;
		}
	}

	/**
	 * 设置 -- 交易金额
	 * @param d
	 */
	public void setAmount(double d)
	{
		Amount = d;
	}



	/**
	 * 设置 -- 证实书发放银行ID
	 * @param l
	 */
	public void setCertificationBankID(long l)
	{
		CertificationBankID = l;
	}

	/**
	 * 设置 -- 证实书号
	 * @param string
	 */
	public void setCertificationNo(String string)
	{
		if (string != null)
		{
			CertificationNo = string;
		}
	}

	/**
	 * 设置 -- 复核/取消复核摘要
	 * @param string
	 */
	public void setCheckAbstract(String string)
	{
		if (string != null)
		{
			CheckAbstract = string;
		}
	}

	/**
	 * 设置 -- 复核人ID
	 * @param l
	 */
	public void setCheckUserID(long l)
	{
		CheckUserID = l;
	}

	/**
	 * 设置 -- 复核人名称
	 * @param string
	 */
	public void setCheckUserName(String string)
	{
		if (string != null)
		{
			CheckUserName = string;
		}
	}

	

	/**
	 * 设置 -- 定期客户标识
	 * @param l
	 */
	public void setClientID(long l)
	{
		ClientID = l;
	}

	/**
	 * 设置 -- 定期客户名称
	 * @param string
	 */
	public void setClientName(String string)
	{
		if (string != null)
		{
			ClientName = string;
		}
	}

	/**
	 * 设置 -- 定期客户编号
	 * @param string
	 */
	public void setClientNo(String string)
	{
		if (string != null)
		{
			ClientNo = string;
		}
	}

	


	/**
	 * 设置 -- 币种标识
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		CurrencyID = l;
	}

	/**
	 * 设置 -- 存单编号（子定期账户编号）
	 * @param string
	 */
	public void setDepositNo(String string)
	{
		if (string != null)
		{
			DepositNo = string;
		}
	}

	/**
	 * 设置 -- 定期存款期限（月）
	 * @param l
	 */
	public void setDepositTerm(long l)
	{
		DepositTerm = l;
	}

	/**
	 * 设置 -- 结束(截止)日期
	 * @param timestamp
	 */
	public void setEndDate(Timestamp timestamp)
	{
		EndDate = timestamp;
	}

	/**
	 * 设置 -- 执行日
	 * @param timestamp
	 */
	public void setExecuteDate(Timestamp timestamp)
	{
		ExecuteDate = timestamp;
	}

	/**
	 * 设置 -- 唯一标识
	 * @param l
	 */
	public void setID(long l)
	{
		ID = l;
	}

	/**
	 * 设置 -- 录入人ID
	 * @param l
	 */
	public void setInputUserID(long l)
	{
		InputUserID = l;
	}

	/**
	 * 设置 -- 录入人名称
	 * @param string
	 */
	public void setInputUserName(String string)
	{
		if (string != null)
		{
			InputUserName = string;
		}
	}

	/**
	 * 设置 -- 标识非结算系统产生的流水号
	 * @param string
	 */
	public void setInstructionNo(String string)
	{
		if (string != null)
		{
			InstructionNo = string;
		}
	}

	/**
	 * 设置 -- 利率计划
	 * @param l
	 */
	public void setInterestPlanID(long l)
	{
		InterestPlanID = l;
	}

	/**
	 * 设置 -- 起息日
	 * @param timestamp
	 */
	public void setInterestStartDate(Timestamp timestamp)
	{
		InterestStartDate = timestamp;
	}

	/**
	 * 设置 -- 修改时间：时分秒
	 * @param timestamp
	 */
	public void setModifyDate(Timestamp timestamp)
	{
		ModifyDate = timestamp;
	}

	
	/**
	 * 设置 -- 办事处标识
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		OfficeID = l;
	}

	

	/**
	 * 设置 -- 计提利息
	 * @param d
	 */
	public void setPreDrawInterest(double d)
	{
		PreDrawInterest = d;
	}

	

	/**
	 * 设置 -- 利率
	 * @param d
	 */
	public void setRate(double d)
	{
		Rate = d;
	}

	/**
	 * 设置 -- 印鉴卡发放银行ID
	 * @param l
	 */
	public void setSealBankID(long l)
	{
		SealBankID = l;
	}

	/**
	 * 设置 -- 印鉴卡号
	 * @param string
	 */
	public void setSealNo(String string)
	{
		if (string != null)
		{
			SealNo = string;
		}
	}

	/**
	 * 设置 -- 开始(起始)日期
	 * @param timestamp
	 */
	public void setStartDate(Timestamp timestamp)
	{
		StartDate = timestamp;
	}

	/**
	 * 设置 -- 交易状态
	 * @param l
	 */
	public void setStatusID(long l)
	{
		StatusID = l;
	}

	/**
	 * 设置 -- 子定期账户ID
	 * @param l
	 */
	public void setSubAccountID(long l)
	{
		SubAccountID = l;
	}

	/**
	 * 设置 -- 交易类型（定期开立、定期支取、定期续期转存、通知开立、通知支取）
	 * @param l
	 */
	public void setTransactionTypeID(long l)
	{
		TransactionTypeID = l;
	}

	
	/**
	 * 设置 -- 交易编号
	 * @param string
	 */
	public void setTransNo(String string)
	{
		if (string != null)
		{
			TransNo = string;
		}
	}

	/**
	 * 得到摘要ID
	 * @return
	 */
	public long getAbstractID() {
		return AbstractID;
	}

	/**
	 * 设置摘要ID
	 * @param l
	 */
	public void setAbstractID(long l) {
		AbstractID = l;
	}


	/**
	 * 得到收息银行ID
	 * @return
	 */
	public long getInterestBankID() {
		return InterestBankID;
	}

	/**
	 * 得到收息银行名称
	 * @return
	 */
	public String getInterestBankName() {
		return InterestBankName;
	}

	/**
	 * 得到利息现金流向描述
	 * @return
	 */
	public String getInterestCashFlowDesc() {
		return InterestCashFlowDesc;
	}

	/**
	 * 得到利息现金流向ID
	 * @return
	 */
	public long getInterestCashFlowID() {
		return InterestCashFlowID;
	}

	/**
	 * 得到市（利息相关）
	 * @return
	 */
	public String getInterestRemitInCity() {
		return InterestRemitInCity;
	}

	/**
	 * 得到非财务公司账户名称（利息相关）
	 * @return
	 */
	public String getInterestExtClientName() {
		return InterestExtClientName;
	}

	/**
	 * 得到非财务公司账户ID（利息相关）
	 * @return
	 */
	public String getInterestExtAcctNo() {
		return InterestExtAcctNo;
	}

	/**
	 * 得到非财务公司银行名称
	 * @return
	 */
	public String getInterestRemitInBank() {
		return InterestRemitInBank;
	}

	/**
	 * 得到利息提入行号
	 * @return
	 */
	public String getInterestExtBankNo() {
		return InterestExtBankNo;
	}

	/**
	 * 得到利息付款方式ID
	 * @return
	 */
	public long getInterestPayTypeID() {
		return InterestPayTypeID;
	}

	/**
	 * 得到 省（利息相关）
	 * @return
	 */
	public String getInterestRemitInProvince() {
		return InterestRemitInProvince;
	}

	/**
	 * 得到是否本利续存
	 * @return
	 */
	public long getIsCapitalAndInterestTransfer() {
		return IsCapitalAndInterestTransfer;
	}

	/**
	 * 得到新存单本金
	 * @return
	 */
	public double getNewAmount() {
		return NewAmount;
	}

	/**
	 * 得到新存单证实书发放银行ID
	 * @return
	 */
	public long getNewCertificationBankID() {
		return NewCertificationBankID;
	}

	/**
	 * 得到新证实书号
	 * @return
	 */
	public String getNewCertificationNo() {
		return NewCertificationNo;
	}

	/**
	 * 得到新存单号
	 * @return
	 */
	public String getNewDepositNo() {
		return NewDepositNo;
	}

	/**
	 * 得到新存单存款期限
	 * @return
	 */
	public long getNewDepositTerm() {
		return NewDepositTerm;
	}

	/**
	 * 得到新存单到期日
	 * @return
	 */
	public Timestamp getNewEndDate() {
		return NewEndDate;
	}

	/**
	 * 得到新存单利率计划
	 * @return
	 */
	public long getNewInterestPlanID() {
		return NewInterestPlanID;
	}

	/**
	 * 得到新存单利率
	 * @return
	 */
	public double getNewRate() {
		return NewRate;
	}

	/**
	 * 得到新存单起始日
	 * @return
	 */
	public Timestamp getNewStartDate() {
		return NewStartDate;
	}

	/**
	 * 得到利息支付
	 * @return
	 */
	public double getPayableInterest() {
		return PayableInterest;
	}

	/**
	 * 得到收息账户ID
	 * @return
	 */
	public long getReceiveInterestAccountID() {
		return ReceiveInterestAccountID;
	}

	/**
	 * 得到收息账户号
	 * @return
	 */
	public String getReceiveInterestAccountNo() {
		return ReceiveInterestAccountNo;
	}

	/**
	 * 设置收息银行ID
	 * @param l
	 */
	public void setInterestBankID(long l) {
		InterestBankID = l;
	}

	/**
	 * 设置收息银行名称
	 * @param string
	 */
	public void setInterestBankName(String string) {
		InterestBankName = string;
	}

	/**
	 * 设置利息现金流向描述
	 * @param string
	 */
	public void setInterestCashFlowDesc(String string) {
		InterestCashFlowDesc = string;
	}

	/**
	 * 设置利息现金流向ID
	 * @param l
	 */
	public void setInterestCashFlowID(long l) {
		InterestCashFlowID = l;
	}

	/**
	 * 设置市（利息相关）
	 * @param string
	 */
	public void setInterestRemitInCity(String string) {
		InterestRemitInCity = string;
	}

	/**
	 * 设置非财务公司账户名（利息相关）
	 * @param string
	 */
	public void setInterestExtClientName(String string) {
		InterestExtClientName = string;
	}

	/**
	 * 设置非财务公司账户号（利息相关）
	 * @param string
	 */
	public void setInterestExtAcctNo(String string) {
		InterestExtAcctNo = string;
	}

	/**
	 * 设置非财务公司银行名称
	 * @param string
	 */
	public void setInterestRemitInBank(String string) {
		InterestRemitInBank = string;
	}

	/**
	 * 设置利息提入行号
	 * @param string
	 */
	public void setInterestExtBankNo(String string) {
		InterestExtBankNo = string;
	}

	/**
	 * 设置利息付款方式ID
	 * @param l
	 */
	public void setInterestPayTypeID(long l) {
		InterestPayTypeID = l;
	}

	/**
	 * 设置省（利息相关）
	 * @param string
	 */
	public void setInterestRemitInProvince(String string) {
		InterestRemitInProvince = string;
	}

	/**
	 * 设置是否本利续存
	 * @param l
	 */
	public void setIsCapitalAndInterestTransfer(long l) {
		IsCapitalAndInterestTransfer = l;
	}

	/**
	 * 设置新存单本金
	 * @param d
	 */
	public void setNewAmount(double d) {
		NewAmount = d;
	}

	/**
	 * 设置新证实书发放银行ID
	 * @param l
	 */
	public void setNewCertificationBankID(long l) {
		NewCertificationBankID = l;
	}

	/**
	 * 设置新证实书号
	 * @param string
	 */
	public void setNewCertificationNo(String string) {
		NewCertificationNo = string;
	}

	/**
	 * 设置新存款单据号
	 * @param string
	 */
	public void setNewDepositNo(String string) {
		NewDepositNo = string;
	}

	/**
	 * 设置新存单存款期限
	 * @param l
	 */
	public void setNewDepositTerm(long l) {
		NewDepositTerm = l;
	}

	/**
	 * 设置新存单到期日
	 * @param timestamp
	 */
	public void setNewEndDate(Timestamp timestamp) {
		NewEndDate = timestamp;
	}

	/**
	 * 设置新存单利率计划
	 * @param l
	 */
	public void setNewInterestPlanID(long l) {
		NewInterestPlanID = l;
	}

	/**
	 * 设置新存单利率
	 * @param d
	 */
	public void setNewRate(double d) {
		NewRate = d;
	}

	/**
	 * 设置新存单开始日期
	 * @param timestamp
	 */
	public void setNewStartDate(Timestamp timestamp) {
		NewStartDate = timestamp;
	}

	/**
	 * 设置利息支付
	 * @param d
	 */
	public void setPayableInterest(double d) {
		PayableInterest = d;
	}

	/**
	 * 设置收息账户ID
	 * @param l
	 */
	public void setReceiveInterestAccountID(long l) {
		ReceiveInterestAccountID = l;
	}

	/**
	 * 设置收息账户号
	 * @param string
	 */
	public void setReceiveInterestAccountNo(String string) {
		ReceiveInterestAccountNo = string;
	}

	/**
	 * 得到本次支取利息
	 * @return
	 */
	public double getWithDrawInterest() {
		return PayableInterest + PreDrawInterest;
	}

	/**
	 * 设置本次支取利息
	 * @param d
	 */
	public void setWithDrawInterest(double d) {
		WithDrawInterest = d;
	}

	/**
	 * @return
	 */
	public long getNewSealBankID()
	{
		return NewSealBankID;
	}

	/**
	 * @return
	 */
	public String getNewSealNo()
	{
		return NewSealNo;
	}

	/**
	 * @param l
	 */
	public void setNewSealBankID(long l)
	{
		NewSealBankID = l;
	}

	/**
	 * @param string
	 */
	public void setNewSealNo(String string)
	{
		NewSealNo = string;
	}

	/**
	 * @return
	 */
	public Timestamp getInputDate()
	{
		return InputDate;
	}

	/**
	 * @param timestamp
	 */
	public void setInputDate(Timestamp timestamp)
	{
		InputDate = timestamp;
	}

	public long getIsAutoContinue() {
		return isAutoContinue;
	}

	public void setIsAutoContinue(long isAutoContinue) {
		this.isAutoContinue = isAutoContinue;
	}

	public long getAutocontinuetype() {
		return autocontinuetype;
	}

	public void setAutocontinuetype(long autocontinuetype) {
		this.autocontinuetype = autocontinuetype;
	}

	public long getAutocontinueaccountid() {
		return autocontinueaccountid;
	}

	public void setAutocontinueaccountid(long autocontinueaccountid) {
		this.autocontinueaccountid = autocontinueaccountid;
	}

}
