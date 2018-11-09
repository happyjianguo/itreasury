package com.iss.itreasury.settlement.transreserve.dataentity;



import java.sql.Timestamp;
import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;

public class TransReserveInfo  extends SettlementBaseDataEntity{
	
	private long id =-1;
	private long nofficeid =-1;             //办事处(操作机构id)
	private long ncurrencyid =-1;           //币种
	private String stransno = "";           //交易号
	private long ntransactiontypeid =-1;    //交易类型
	private long branchid =-1;              //机构ID
	private long reserveaccountid =-1;      //机构准备金账户id
	private long bakaccountid =-1;          //机构备付金账户id
	private long payorreceivetype=-1;       //存款来源方式，1备付金转账，2银行收款/银行汇款
	private long bankid =-1;                //付款,汇入银行id
	private long lBankAccountID =-1;        //付款,汇入银行账户id
	private String sremitinbank ="";        //付款汇入行名称  
	private String sextaccountno ="";       //银行帐户号(外部账户)
	private String sextclientname ="";      //银行客户名称
	private String sremitinprovince ="";    //汇入省
	private String sremitincity ="";        //汇入市
	private String spayeebankexchangeno ="";//汇入行联行号
	private String spayeebankcnapsno ="";   //汇入行CNAPS号
	private String spayeebankorgno ="";     //汇入行机构号
	private double amount =0.00;            //交易金额
	private Timestamp dtintereststart =null;//起息日
	private Timestamp dtexecute =null;      //执行日
	private Timestamp dtmodify =null;       //修改时间：年月日时分秒
	private long ninputuserid =-1;          //录入人
	private Timestamp dtinput =null;        //录入时间：年月日时分秒
	private long ncheckuserid =-1;          //复核人
	private long nabstractid =-1;           //摘要ID
	private String sabstract ="";           //摘要
	private String scheckabstract ="";      //复核摘要
	private long nstatusid =-1;             //交易状态
	private long[] transStatusIDs =null;      //查询交易状态
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getNofficeid() {
		return nofficeid;
	}
	public void setNofficeid(long nofficeid) {
		this.nofficeid = nofficeid;
	}
	public long getNcurrencyid() {
		return ncurrencyid;
	}
	public void setNcurrencyid(long ncurrencyid) {
		this.ncurrencyid = ncurrencyid;
	}
	public String getStransno() {
		return stransno;
	}
	public void setStransno(String stransno) {
		this.stransno = stransno;
	}
	public long getNtransactiontypeid() {
		return ntransactiontypeid;
	}
	public void setNtransactiontypeid(long ntransactiontypeid) {
		this.ntransactiontypeid = ntransactiontypeid;
	}
	public long getBranchid() {
		return branchid;
	}
	public void setBranchid(long branchid) {
		this.branchid = branchid;
	}
	public long getReserveaccountid() {
		return reserveaccountid;
	}
	public void setReserveaccountid(long reserveaccountid) {
		this.reserveaccountid = reserveaccountid;
	}
	public long getBakaccountid() {
		return bakaccountid;
	}
	public void setBakaccountid(long bakaccountid) {
		this.bakaccountid = bakaccountid;
	}
	public long getPayorreceivetype() {
		return payorreceivetype;
	}
	public void setPayorreceivetype(long payorreceivetype) {
		this.payorreceivetype = payorreceivetype;
	}
	public long getBankid() {
		return bankid;
	}
	public void setBankid(long bankid) {
		this.bankid = bankid;
	}
	public String getSremitinbank() {
		return sremitinbank;
	}
	public void setSremitinbank(String sremitinbank) {
		this.sremitinbank = sremitinbank;
	}
	public String getSextaccountno() {
		return sextaccountno;
	}
	public void setSextaccountno(String sextaccountno) {
		this.sextaccountno = sextaccountno;
	}
	public String getSextclientname() {
		return sextclientname;
	}
	public void setSextclientname(String sextclientname) {
		this.sextclientname = sextclientname;
	}
	public String getSremitinprovince() {
		return sremitinprovince;
	}
	public void setSremitinprovince(String sremitinprovince) {
		this.sremitinprovince = sremitinprovince;
	}
	public String getSremitincity() {
		return sremitincity;
	}
	public void setSremitincity(String sremitincity) {
		this.sremitincity = sremitincity;
	}
	public String getSpayeebankexchangeno() {
		return spayeebankexchangeno;
	}
	public void setSpayeebankexchangeno(String spayeebankexchangeno) {
		this.spayeebankexchangeno = spayeebankexchangeno;
	}
	public String getSpayeebankcnapsno() {
		return spayeebankcnapsno;
	}
	public void setSpayeebankcnapsno(String spayeebankcnapsno) {
		this.spayeebankcnapsno = spayeebankcnapsno;
	}
	public String getSpayeebankorgno() {
		return spayeebankorgno;
	}
	public void setSpayeebankorgno(String spayeebankorgno) {
		this.spayeebankorgno = spayeebankorgno;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Timestamp getDtintereststart() {
		return dtintereststart;
	}
	public void setDtintereststart(Timestamp dtintereststart) {
		this.dtintereststart = dtintereststart;
	}
	public Timestamp getDtexecute() {
		return dtexecute;
	}
	public void setDtexecute(Timestamp dtexecute) {
		this.dtexecute = dtexecute;
	}
	public Timestamp getDtmodify() {
		return dtmodify;
	}
	public void setDtmodify(Timestamp dtmodify) {
		this.dtmodify = dtmodify;
	}
	public long getNinputuserid() {
		return ninputuserid;
	}
	public void setNinputuserid(long ninputuserid) {
		this.ninputuserid = ninputuserid;
	}
	public Timestamp getDtinput() {
		return dtinput;
	}
	public void setDtinput(Timestamp dtinput) {
		this.dtinput = dtinput;
	}
	public long getNcheckuserid() {
		return ncheckuserid;
	}
	public void setNcheckuserid(long ncheckuserid) {
		this.ncheckuserid = ncheckuserid;
	}
	public long getNabstractid() {
		return nabstractid;
	}
	public void setNabstractid(long nabstractid) {
		this.nabstractid = nabstractid;
	}
	public String getSabstract() {
		return sabstract;
	}
	public void setSabstract(String sabstract) {
		this.sabstract = sabstract;
	}
	public String getScheckabstract() {
		return scheckabstract;
	}
	public void setScheckabstract(String scheckabstract) {
		this.scheckabstract = scheckabstract;
	}
	public long getNstatusid() {
		return nstatusid;
	}
	public void setNstatusid(long nstatusid) {
		this.nstatusid = nstatusid;
	}
	/**
	 * @return the lBankAccountID
	 */
	public long getLBankAccountID() {
		return lBankAccountID;
	}
	/**
	 * @param bankAccountID the lBankAccountID to set
	 */
	public void setLBankAccountID(long bankAccountID) {
		this.lBankAccountID = bankAccountID;
	}
	/**
	 * @return the transStatusIDs
	 */
	public long[] getTransStatusIDs() {
		return transStatusIDs;
	}
	/**
	 * @param transStatusIDs the transStatusIDs to set
	 */
	public void setTransStatusIDs(long[] transStatusIDs) {
		this.transStatusIDs = transStatusIDs;
	}

}
