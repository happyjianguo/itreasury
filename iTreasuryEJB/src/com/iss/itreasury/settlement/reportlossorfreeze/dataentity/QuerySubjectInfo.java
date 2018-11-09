package com.iss.itreasury.settlement.reportlossorfreeze.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
/**
 * Title: iTreasury Description: Copyright: Copyright (c) 2003 Company:
 * iSoftStone
 * <p>Description:　挂失冻结业务,对应db数据库中SETT_REPORTLOSSORFREEZE表 </p>
 * @author jinchen
 * @version Date of Creation 2004-11-24
 */
public class QuerySubjectInfo extends SettlementBaseDataEntity
{   
    private Timestamp Dtexecute = null;	//执行日
    private Timestamp StartDate= null;//开始日期
    private Timestamp EndDate= null;//到期日期
    private Timestamp Dtintereststart = null;	//起息日
   
    private Timestamp DtintereststartFrom = null; //起息日  从
    private Timestamp DtintereststartTo = null;  //起息日 至
    private long Id = -1;	//   Number ID
    private String sTransNo = "";	//交易号
    private long Ntransactiontypeid= -1;	//交易类型
    private String Ssubjectcode= "";	//科目号
    private double mAmount = 0.0;	//金额
    private long Ntransdirection=-1;//借贷方向

    private String  SEXTCLIENTNAME="";	//第三方账户名  
    private String SEXTACCOUNTNO= "";//第三方账户号  
    private long   AccountId = -1;	// 账户ID    
    private String AccountNo = "";	//账户号  
    private String AccountName = "";	//账户名  
    private String Sabstract = "";	//摘要
    private long  nInputUserId = -1;	//录入人ID
    private String InputUserName ="";	//录入人
    private long nCheckUserId = -1;	//复核人ID	
    private String CheckUserName ="";	//复核人
    private String sbankaccountcode="" ;//账户编号用于银行收款的借方和银行付款的贷方
    private String senterprisename="";//账户名称用于银行收款的借方和银行付款的贷方
    
    /**
     * 
     */
    private long nreceiveaccountid=-1;
    private long npayaccountid=-1;
    private long nbankid=-1;//银行
    
    private long CurrencyId = -1; 
    private long OfficeId = -1;	
    
    private String strTransNoFrom = "";  //交易号 从
    private String strTransNoTo = "";  //交易号 到
    
    private String strTransactionType = "";  //交易类型

	public String getStrTransactionType() {
		return strTransactionType;
	}
	public void setStrTransactionType(String strTransactionType) {
		this.strTransactionType = strTransactionType;
	}
	public String getStrTransNoFrom() {
		return strTransNoFrom;
	}
	public void setStrTransNoFrom(String strTransNoFrom) {
		this.strTransNoFrom = strTransNoFrom;
	}
	public String getStrTransNoTo() {
		return strTransNoTo;
	}
	public void setStrTransNoTo(String strTransNoTo) {
		this.strTransNoTo = strTransNoTo;
	}
	public Timestamp getStartDate() {
		return StartDate;
	}
	public void setStartDate(Timestamp startDate) {
		StartDate = startDate;
	}
	public Timestamp getEndDate() {
		return EndDate;
	}
	public void setEndDate(Timestamp endDate) {
		EndDate = endDate;
	}
	public long getCurrencyId() {
		return CurrencyId;
	}
	public void setCurrencyId(long currencyId) {
		CurrencyId = currencyId;
	}
	public long getOfficeId() {
		return OfficeId;
	}
	public void setOfficeId(long officeId) {
		OfficeId = officeId;
	}
	
	public Timestamp getDtexecute() {
		return Dtexecute;
	}
	public void setDtexecute(Timestamp dtexecute) {
		Dtexecute = dtexecute;
	}
	public Timestamp getDtintereststart() {
		return Dtintereststart;
	}
	public void setDtintereststart(Timestamp dtintereststart) {
		Dtintereststart = dtintereststart;
	}
	public long getId() {
		return Id;
	}
	public void setId(long id) {
		Id = id;
	}
	
	public String getSsubjectcode() {
		return Ssubjectcode;
	}
	public void setSsubjectcode(String ssubjectcode) {
		Ssubjectcode = ssubjectcode;
	}

	public long getNtransdirection() {
		return Ntransdirection;
	}
	public void setNtransdirection(long ntransdirection) {
		Ntransdirection = ntransdirection;
	}
	public long getAccountId() {
		return AccountId;
	}
	public void setAccountId(long accountId) {
		AccountId = accountId;
	}
	
	
	
	
	public String getSabstract() {
		return Sabstract;
	}
	public void setSabstract(String sabstract) {
		Sabstract = sabstract;
	}
	public long getNInputUserId() {
		return nInputUserId;
	}
	public void setNInputUserId(long inputUserId) {
		nInputUserId = inputUserId;
	}
	public String getCheckUserName() {
		return CheckUserName;
	}
	public void setCheckUserName(String checkUserName) {
		CheckUserName = checkUserName;
	}
	public String getInputUserName() {
		return InputUserName;
	}
	public void setInputUserName(String inputUserName) {
		InputUserName = inputUserName;
	}
	
	public String getSTransNo() {
		return sTransNo;
	}
	public void setSTransNo(String transNo) {
		sTransNo = transNo;
	}
	public long getNtransactiontypeid() {
		return Ntransactiontypeid;
	}
	public void setNtransactiontypeid(long ntransactiontypeid) {
		Ntransactiontypeid = ntransactiontypeid;
	}
	public double getMAmount() {
		return mAmount;
	}
	public void setMAmount(double amount) {
		mAmount = amount;
	}
	public long getNCheckUserId() {
		return nCheckUserId;
	}
	public void setNCheckUserId(long checkUserId) {
		nCheckUserId = checkUserId;
	}
	public String getAccountNo() {
		return AccountNo;
	}
	public void setAccountNo(String accountNo) {
		AccountNo = accountNo;
	}
	public String getAccountName() {
		return AccountName;
	}
	public void setAccountName(String accountName) {
		AccountName = accountName;
	}
	public String getSEXTCLIENTNAME() {
		return SEXTCLIENTNAME;
	}
	public void setSEXTCLIENTNAME(String sextclientname) {
		SEXTCLIENTNAME = sextclientname;
	}
	public String getSEXTACCOUNTNO() {
		return SEXTACCOUNTNO;
	}
	public void setSEXTACCOUNTNO(String sextaccountno) {
		SEXTACCOUNTNO = sextaccountno;
	}
	public long getNreceiveaccountid() {
		return nreceiveaccountid;
	}
	public void setNreceiveaccountid(long nreceiveaccountid) {
		this.nreceiveaccountid = nreceiveaccountid;
	}
	public long getNpayaccountid() {
		return npayaccountid;
	}
	public void setNpayaccountid(long npayaccountid) {
		this.npayaccountid = npayaccountid;
	}
	public long getNbankid() {
		return nbankid;
	}
	public void setNbankid(long nbankid) {
		this.nbankid = nbankid;
	}
	public String getSbankaccountcode() {
		return sbankaccountcode;
	}
	public void setSbankaccountcode(String sbankaccountcode) {
		this.sbankaccountcode = sbankaccountcode;
	}
	public String getSenterprisename() {
		return senterprisename;
	}
	public void setSenterprisename(String senterprisename) {
		this.senterprisename = senterprisename;
	}
	public Timestamp getDtintereststartFrom() {
		return DtintereststartFrom;
	}
	public void setDtintereststartFrom(Timestamp dtintereststartFrom) {
		DtintereststartFrom = dtintereststartFrom;
	}
	public Timestamp getDtintereststartTo() {
		return DtintereststartTo;
	}
	public void setDtintereststartTo(Timestamp dtintereststartTo) {
		DtintereststartTo = dtintereststartTo;
	}

    
   
   
}