package com.iss.itreasury.settlement.transferinterest.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 * @author xwhe
 *
 * 利息费用结算页面查询条件信息实体。
 * 该类主要用来保存页面的查询条件，并作为查询Dao的方法参数。
 * 该实体必须遵守JavaBean的规范，以便实现从JSP页面到实体的自动赋值的功能，
 * 简化应用程序的开发过程。
 * 为了下一个页面显示的需要，本实体中同时保存数据库访问需要的关键字段值，比如：ID,
 * 和页面显示的内容，比如：No。
 */
public class CraInterestInfo implements Serializable{
	private long OfficeID = -1;	                    //办事处ID
	private long CurrencyID = -1;	                //币种ID
	private long UserID  = -1 ;                     //操作人
	private long lCraContractIDFrom = -1;           //转让合同 ID (开始)
    private String strCraContractNoFrom = "";       //转让合同编号(开始)
    private long lCraContractIDTo = -1;             //转让合同 ID (结束)
    private String strCraContractNoTo = "";         //转让合同编号(结束)
    private Timestamp dtClearInterest = null;       //结息日
    private long lCraCounterID = -1;	            //交易对手ID
    private String strCraCounterCode = "";          //交易对手编号
    private long lCraContractTypeId = -1;           //转让合同类型
	public long getCurrencyID() {
		return CurrencyID;
	}
	public void setCurrencyID(long currencyID) {
		CurrencyID = currencyID;
	}
	public Timestamp getDtClearInterest() {
		return dtClearInterest;
	}
	public void setDtClearInterest(Timestamp dtClearInterest) {
		this.dtClearInterest = dtClearInterest;
	}
	public long getLCraContractIDFrom() {
		return lCraContractIDFrom;
	}
	public void setLCraContractIDFrom(long craContractIDFrom) {
		lCraContractIDFrom = craContractIDFrom;
	}
	public long getLCraContractIDTo() {
		return lCraContractIDTo;
	}
	public void setLCraContractIDTo(long craContractIDTo) {
		lCraContractIDTo = craContractIDTo;
	}
	public long getLCraContractTypeId() {
		return lCraContractTypeId;
	}
	public void setLCraContractTypeId(long craContractTypeId) {
		lCraContractTypeId = craContractTypeId;
	}
	public long getLCraCounterID() {
		return lCraCounterID;
	}
	public void setLCraCounterID(long craCounterID) {
		lCraCounterID = craCounterID;
	}
	public long getOfficeID() {
		return OfficeID;
	}
	public void setOfficeID(long officeID) {
		OfficeID = officeID;
	}
	public String getStrCraContractNoFrom() {
		return strCraContractNoFrom;
	}
	public void setStrCraContractNoFrom(String strCraContractNoFrom) {
		this.strCraContractNoFrom = strCraContractNoFrom;
	}
	public String getStrCraContractNoTo() {
		return strCraContractNoTo;
	}
	public void setStrCraContractNoTo(String strCraContractNoTo) {
		this.strCraContractNoTo = strCraContractNoTo;
	}
	public String getStrCraCounterCode() {
		return strCraCounterCode;
	}
	public void setStrCraCounterCode(String strCraCounterCode) {
		this.strCraCounterCode = strCraCounterCode;
	}
	public long getUserID() {
		return UserID;
	}
	public void setUserID(long userID) {
		UserID = userID;
	}

}
