/*
 * Created on 2004-02-02
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.obinstruction.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.sysframe.base.dataentity.BaseDataEntity;

/**
 * @author xrli
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QueryOBFinanceInstrInfo extends BaseDataEntity implements Serializable
{

	/**
	 * 
	 */
	public QueryOBFinanceInstrInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	private long OfficeID = -1;                      //办事处  
	private long CurrencyID = -1;                    //币种
	
	private long TransType = -1;                     //网银交易类型
	private long Status = -1;                        //指令状态
//	private Timestamp ConfirmDateFrom = null;        //确认时间
//	private Timestamp ConfirmDateTo = null;          //确认时间
	private double AmountFrom = 0.0;                 //交易金额
	private double AmountTo = 0.0;                   //交易金额
	private String DateRadio = "";						//日期选择
	private Timestamp ExecuteDateFrom = null;        //执行时间
	private Timestamp ExecuteDateTo = null;          //执行时间
	private Timestamp CheckDateFrom = null;        //执行时间
	private Timestamp CheckDateTo = null;          //执行时间
	private Timestamp SignDateFrom = null;        //执行时间
	private Timestamp SignDateTo = null;          //执行时间
	private String TransNo = "";                     //交易号
	private long InstructionNo = -1;                 //指令号
	
	private String AccountNoFrom = "";               //账户编号
    private String AccountNoTo = "";                 //账户编号
    
	private String ClientNoFrom = "";               //客户编号
	private String ClientNoTo = "";                 //客户编号
	
	private long Desc = 1;                           //排序方式
   	private long OrderField = 1;                      //排序字段

 	private long userID = -1;                      //登录人ID  为华联批量复核用作进行交叉复核判断 	
 	
 	private long Source = -1;                      //数据来源（1：柜台 2：网银 3：银行 4以上：SAP等外部系统）
 	private String ApplyCode = "";				   //申请指令编号
 	 
 	private String bankName = "";                  //汇入行全称
 	
 	private Timestamp openDate = null;          //当前开机日期
 	
 	private long id = -1;
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}	
	/**
	 * @return
	 */
	public double getAmountFrom() {
		return AmountFrom;
	}

	/**
	 * @return
	 */
	public double getAmountTo() {
		return AmountTo;
	}

	/**
	 * @return
	 */
//	public Timestamp getConfirmDateFrom() {
//		return ConfirmDateFrom;
//	}
//
//	/**
//	 * @return
//	 */
//	public Timestamp getConfirmDateTo() {
//		return ConfirmDateTo;
//	}
	

	/**
	 * @return
	 */
	public long getCurrencyID() {
		return CurrencyID;
	}

	/**
	 * @return
	 */
	public Timestamp getExecuteDateFrom() {
		return ExecuteDateFrom;
	}

	/**
	 * @return
	 */
	public Timestamp getExecuteDateTo() {
		return ExecuteDateTo;
	}

	/**
	 * @return
	 */
	public long getInstructionNo() {
		return InstructionNo;
	}

	/**
	 * @return
	 */
	public long getOfficeID() {
		return OfficeID;
	}

	/**
	 * @return
	 */
	public long getStatus() {
		return Status;
	}

	/**
	 * @return
	 */
	public String getTransNo() {
		return TransNo;
	}

	/**
	 * @return
	 */
	public long getTransType() {
		return TransType;
	}

	/**
	 * @param d
	 */
	public void setAmountFrom(double d) {
		AmountFrom = d;
	}

	/**
	 * @param d
	 */
	public void setAmountTo(double d) {
		AmountTo = d;
	}

	/**
	 * @param timestamp
	 */
//	public void setConfirmDateFrom(Timestamp timestamp) {
//		ConfirmDateFrom = timestamp;
//	}
//
//	/**
//	 * @param timestamp
//	 */
//	public void setConfirmDateTo(Timestamp timestamp) {
//		ConfirmDateTo = timestamp;
//	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l) {
		CurrencyID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setExecuteDateFrom(Timestamp timestamp) {
		ExecuteDateFrom = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setExecuteDateTo(Timestamp timestamp) {
		ExecuteDateTo = timestamp;
	}

	/**
	 * @param l
	 */
	public void setInstructionNo(long l) {
		InstructionNo = l;
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l) {
		OfficeID = l;
	}

	/**
	 * @param l
	 */
	public void setStatus(long l) {
		Status = l;
	}

	/**
	 * @param string
	 */
	public void setTransNo(String string) {
		TransNo = string;
	}

	/**
	 * @param l
	 */
	public void setTransType(long l) {
		TransType = l;
	}

	/**
	 * @return
	 */
	public long getDesc() {
		return Desc;
	}

	/**
	 * @return
	 */
	public long getOrderField() {
		return OrderField;
	}

	/**
	 * @param l
	 */
	public void setDesc(long l) {
		Desc = l;
	}

	/**
	 * @param l
	 */
	public void setOrderField(long l) {
		OrderField = l;
	}

	/**
	 * @return
	 */
	public String getAccountNoFrom() {
		return AccountNoFrom;
	}

	/**
	 * @return
	 */
	public String getAccountNoTo() {
		return AccountNoTo;
	}

	/**
	 * @param string
	 */
	public void setAccountNoFrom(String string) {
		AccountNoFrom = string;
	}

	/**
	 * @param string
	 */
	public void setAccountNoTo(String string) {
		AccountNoTo = string;
	}

	/**
	 * @return
	 */
	public String getClientNoFrom()
	{
		return ClientNoFrom;
	}

	/**
	 * @return
	 */
	public String getClientNoTo()
	{
		return ClientNoTo;
	}

	/**
	 * @param string
	 */
	public void setClientNoFrom(String string)
	{
		ClientNoFrom = string;
	}

	/**
	 * @param string
	 */
	public void setClientNoTo(String string)
	{
		ClientNoTo = string;
	}

	/**
	 * @return 返回 checkDateFrom。
	 */
	public Timestamp getCheckDateFrom() {
		return CheckDateFrom;
	}

	/**
	 * @param checkDateFrom 要设置的 checkDateFrom。
	 */
	public void setCheckDateFrom(Timestamp checkDateFrom) {
		CheckDateFrom = checkDateFrom;
	}

	/**
	 * @return 返回 checkDateTo。
	 */
	public Timestamp getCheckDateTo() {
		return CheckDateTo;
	}

	/**
	 * @param checkDateTo 要设置的 checkDateTo。
	 */
	public void setCheckDateTo(Timestamp checkDateTo) {
		CheckDateTo = checkDateTo;
	}

	/**
	 * @return 返回 signDateFrom。
	 */
	public Timestamp getSignDateFrom() {
		return SignDateFrom;
	}

	/**
	 * @param signDateFrom 要设置的 signDateFrom。
	 */
	public void setSignDateFrom(Timestamp signDateFrom) {
		SignDateFrom = signDateFrom;
	}

	/**
	 * @return 返回 signDateTo。
	 */
	public Timestamp getSignDateTo() {
		return SignDateTo;
	}

	/**
	 * @param signDateTo 要设置的 signDateTo。
	 */
	public void setSignDateTo(Timestamp signDateTo) {
		SignDateTo = signDateTo;
	}

	/**
	 * @return 返回 dateRadio。
	 */
	public String getDateRadio() {
		return DateRadio;
	}

	/**
	 * @param dateRadio 要设置的 dateRadio。
	 */
	public void setDateRadio(String dateRadio) {
		DateRadio = dateRadio;
	}

	public long getSource() {
		return Source;
	}

	public void setSource(long source) {
		Source = source;
	}

	public String getApplyCode() {
		return ApplyCode;
	}

	public void setApplyCode(String applyCode) {
		ApplyCode = applyCode;
	}

	public Timestamp getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Timestamp openDate) {
		this.openDate = openDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
