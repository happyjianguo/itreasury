/*
 * Created on 2003-9-8
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obfinanceinstr.dataentity;
/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.*;
import com.iss.itreasury.ebank.util.*;
import com.iss.sysframe.base.dataentity.BaseDataEntity;

public class Query_FinanceInfo  extends BaseDataEntity implements java.io.Serializable 
{
	/** Creates new FinaceInfo */
	public Query_FinanceInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	//批量复核-翻页查询-新增字段
	private long lID = -1; // 指令序号
	private long lCurrencyID = -1; // 交易币种
	private long lClientID = -1; // 交易提交单位 
	private long ntranstype =-1;  //网上交易类型（批量复核）
	private long NSTATUS = -1;       //指令状态（批量复核）
	private String sStartSubmit = ""; // 提交日期-从（批量复核）
	private String sEndSubmit = ""; // 提交日期-到（批量复核）
	private double dMinAmount = 0.0; // 交易金额-最小值（批量复核）
	private double dMaxAmount = 0.0; // 交易金额-最大值 （批量复核）
	private String sStartExe = ""; // 执行日期-从（批量复核）
	private String sEndExe = ""; // 执行日期-到（批量复核）
	private String spayeename=null;//收款方名称
	private long npayeracctid =-1;  //本金付款方账户ID（批量复核）
	private long npayeeacctid=-1;   //本金收款方账户ID（批量复核）
	private String saccountno=null; //付款方账户号（批量复核）
	private String spayeeacctno =null; //收款方账户（批量复核）
	private double mAmount = 0.0; //交易金额（批量复核）
	private Timestamp DTEXECUTE =null; //执行日（批量复核）
	private Timestamp DTCONFIRM =null;//提交日期（批量复核）
	private String NBSNAME =null;//汇款方式为内部转账时的收款方名称（批量复核）
	private String SEXTACCTNAME =null;//汇款方式为内部转账时的收款方名称2（批量复核）
	private String spayeebankname=null;//汇入行名称
	private long nUserID=-1;//用户ID
	private long lOfficeID = -1; // CPF-默认办事处ID
	private String sNote = ""; // 汇款用途/摘要
	private Timestamp dtModify = null;//修改日期
	private String sign = null;   //标示活期或定期业务（新奥项目新增）
	private long confirmUserID = -1;  //确认人id
	private String signatureValue = ""; //加密密文
	
	
	private String depositNo = "";  //存款单据号
	private long InterestPayeeAcctID = -1;  //利息收款方账户
	private long RemitType = -1; //汇款方式
	private long InterestRemitType = -1; //利息汇款方式
	private long noticeDay = -1;  //通知存款品种（天）
	private String depositBillNo = "";  //定期存单号
	private long depositBillPeriod = -1;  //定期存款期限
	private Timestamp depositBillStartDate = null;//新子账户开始日
	private long depositInterestDeal = -1;//新定期存款处理方式
	private long depositInterestToAccountID = -1; //利息转至活期账户id
	private long fixedDepositTime = -1; // 定期存款期限（月）
	private long timestamp = -1;   //时间戳

   public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
public long getDepositBillPeriod() {
		return depositBillPeriod;
	}
	public void setDepositBillPeriod(long depositBillPeriod) {
		this.depositBillPeriod = depositBillPeriod;
	}
	public String getDepositBillNo() {
		return depositBillNo;
	}
	public void setDepositBillNo(String depositBillNo) {
		this.depositBillNo = depositBillNo;
	}
	public long getInterestPayeeAcctID() {
		return InterestPayeeAcctID;
	}
	public void setInterestPayeeAcctID(long interestPayeeAcctID) {
		InterestPayeeAcctID = interestPayeeAcctID;
	}
	public long getRemitType() {
		return RemitType;
	}
	public void setRemitType(long remitType) {
		RemitType = remitType;
	}
	public long getInterestRemitType() {
		return InterestRemitType;
	}
	public void setInterestRemitType(long interestRemitType) {
		InterestRemitType = interestRemitType;
	}
public String getDepositNo() {
		return depositNo;
	}
	public void setDepositNo(String depositNo) {
		this.depositNo = depositNo;
	}
	public long getConfirmUserID() {
		return confirmUserID;
	}
	public void setConfirmUserID(long confirmUserID) {
		this.confirmUserID = confirmUserID;
	}
	public String getSignatureValue() {
		return signatureValue;
	}
	public void setSignatureValue(String signatureValue) {
		this.signatureValue = signatureValue;
	}
	public String getStartSubmit() {
		return sStartSubmit;
	}
	public void setStartSubmit(String string) {
		sStartSubmit = string;
	}
	public String getSStartSubmit() {
		return sStartSubmit;
	}
	public void setSStartSubmit(String string) {
		sStartSubmit = string;
	}
	public String getEndSubmit() {
		return sEndSubmit;
	}
	public void setEndSubmit(String string) {
		sEndSubmit = string;
	}
	public String getSEndSubmit() {
		return sEndSubmit;
	}
	public void setSEndSubmit(String string) {
		sEndSubmit = string;
	}
	public double getMinAmount() {
		return dMinAmount;
	}
	public double getDMinAmount() {
		return dMinAmount;
	}
	public String getFormatMinAmount()
	{
		return DataFormat.formatDisabledAmount(dMinAmount);
	}
	public void setMinAmount(double d) {
		dMinAmount = d;
	}
	public void setDMinAmount(double d) {
		dMinAmount = d;
	}
	public double getMaxAmount() {
		return dMaxAmount;
	}
	public double getDMaxAmount() {
		return dMaxAmount;
	}
	public String getFormatMaxAmount()
	{
		return DataFormat.formatDisabledAmount(dMaxAmount);
	}
	public void setMaxAmount(double d) {
		dMaxAmount = d;
	}
	public void setDMaxAmount(double d) {
		dMaxAmount = d;
	}
	public String getStartExe() {
		return sStartExe;
	}
	public String getSStartExe() {
		return sStartExe;
	}
	public void setStartExe(String string) {
		sStartExe = string;
	}
	public void setSStartExe(String string) {
		sStartExe = string;
	}
	public String getEndExe() {
		return sEndExe;
	}
	public void setEndExe(String string) {
		sEndExe = string;
	}
	public String getSEndExe() {
		return sEndExe;
	}
	public void setSEndExe(String string) {
		sEndExe = string;
	}

	/**
	 * @return
	 */
	public long getClientID()
	{
		return lClientID;
	}
	/**
	 * @return
	 */

	public long getCurrencyID()
	{
		return lCurrencyID;
	}


	/**
	 * @return
	 */
	public long getID()
	{
		return lID;
	}
	/**
	 * @return
	 */
	
	public long getOfficeID()
	{
		return lOfficeID;
	}
	/**
	 * @return
	 */

	public String getNote()
	{
		return sNote;
	}
	/**
	 * @return
	 */
	
	

	public void setClientID(long l)
	{
		lClientID = l;
	}
	
	public void setCurrencyID(long l)
	{
		lCurrencyID = l;
	}
	
	
	/**
	 * @param l
	 */

	public void setID(long l)
	{
		lID = l;
	}
	/**
	 * @param l
	 */
	
	/**
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		lOfficeID = l;
	}
	/**
	 * @param l
	 */
	
	/**
	 * @param l
	 */
	
	/**
	 * @param string
	 */
	
	public void setNote(String string)
	{
		sNote = string;
	}
	/**
	 * @param string
	 */
	
	
	
	
	
	
	
	
	
	

	public String getSNote()
	{
		return sNote;
	}
	/**
	 * Returns the sOfficeName.
	 * @return String
	 */

	public void setSNote(String sNote)
	{
		this.sNote = sNote;
	}
	/**
	 * Sets the sOfficeName.
	 * @param sOfficeName The sOfficeName to set
	 */
	
	public long getLClientID() {
		return lClientID;
	}
	public void setLClientID(long clientID) {
		lClientID = clientID;
	}
	
	public void setLCurrencyID(long currencyID) {
		lCurrencyID = currencyID;
	}
	
	public long getLID() {
		return lID;
	}
	public void setLID(long lid) {
		lID = lid;
	}
	
	public long getLOfficeID() {
		return lOfficeID;
	}
	public void setLOfficeID(long officeID) {
		lOfficeID = officeID;
	}
	
	public Timestamp getDtModify() {
		return dtModify;
	}
	public void setDtModify(Timestamp dtModify) {
		this.dtModify = dtModify;
	}
	public double getMAmount() {
		return mAmount;
	}
	public String getFormatMAmount()
	{
		return DataFormat.formatDisabledAmount(mAmount);
	}
	public void setMAmount(double a) {
		mAmount = a;
	}
	
	public Timestamp getDTEXECUTE() {
		return DTEXECUTE;
	}
	public String getFormatDTEXECUTE()
	{
		return DataFormat.getDateString(DTEXECUTE);
	}
	public void setDTEXECUTE(Timestamp dtexecute) {
		DTEXECUTE = dtexecute;
	}
	public long getNSTATUS() {
		return NSTATUS;
	}
	public void setNSTATUS(long nstatus) {
		NSTATUS = nstatus;
	}
	public long getNtranstype() {
		return ntranstype;
	}
	public void setNtranstype(long ntranstype) {
		this.ntranstype = ntranstype;
	}
	public long getNpayeracctid() {
		return npayeracctid;
	}
	public void setNpayeracctid(long npayeracctid) {
		this.npayeracctid = npayeracctid;
	}
	
	public Timestamp getDtconfirm() {
		return DTCONFIRM;
	}
	public String getFormatDtconfirm()
	{
		return DataFormat.getDateString(DTCONFIRM);
	}
	
	public void setDtconfirm(Timestamp dfirm) {
		DTCONFIRM = dfirm;
	}
	public String getSpayeebankname() {
		return spayeebankname;
	}
	public void setSpayeebankname(String spayeebankname) {
		this.spayeebankname = spayeebankname;
	}
	public String getSpayeename() {
		return spayeename;
	}
	public void setSpayeename(String spayeename) {
		this.spayeename = spayeename;
	}
	public String getSpayeeacctno() {
		return spayeeacctno;
	}
	public void setSpayeeacctno(String spayeeacctno) {
		this.spayeeacctno = spayeeacctno;
	}
	public long getNpayeeacctid() {
		return npayeeacctid;
	}
	public void setNpayeeacctid(long npayeeacctid) {
		this.npayeeacctid = npayeeacctid;
	}
	
	public String getSaccountno() {
		return saccountno;
	}
	public void setSaccountno(String saccountno) {
		this.saccountno = saccountno;
	}
	public String getNBSNAME() {
		return NBSNAME;
	}
	public void setNBSNAME(String nbsname) {
		NBSNAME = nbsname;
	}
	public String getSEXTACCTNAME() {
		return SEXTACCTNAME;
	}
	public void setSEXTACCTNAME(String sextacctname) {
		SEXTACCTNAME = sextacctname;
	}
	public long getNUserID() {
		return nUserID;
	}
	public void setNUserID(long userID) {
		nUserID = userID;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public long getNoticeDay() {
		return noticeDay;
	}
	public void setNoticeDay(long noticeDay) {
		this.noticeDay = noticeDay;
	}
	public Timestamp getDepositBillStartDate() {
		return depositBillStartDate;
	}
	public void setDepositBillStartDate(Timestamp depositBillStartDate) {
		this.depositBillStartDate = depositBillStartDate;
	}
	public long getDepositInterestDeal() {
		return depositInterestDeal;
	}
	public void setDepositInterestDeal(long depositInterestDeal) {
		this.depositInterestDeal = depositInterestDeal;
	}
	public long getDepositInterestToAccountID() {
		return depositInterestToAccountID;
	}
	public void setDepositInterestToAccountID(long depositInterestToAccountID) {
		this.depositInterestToAccountID = depositInterestToAccountID;
	}
	public long getFixedDepositTime() {
		return fixedDepositTime;
	}
	public void setFixedDepositTime(long fixedDepositTime) {
		this.fixedDepositTime = fixedDepositTime;
	}
	
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setId(long id) {
		// TODO Auto-generated method stub
		
	}
}
	