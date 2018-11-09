package com.iss.itreasury.project.wisgfc.settlement.postsupervise.checkaccounts.dataentity;
import com.iss.itreasury.securities.util.SECBaseDataEntity;
import java.sql.Timestamp;
public   class    CheckInAccountInfo    extends  SECBaseDataEntity
{
	private long id = -1;
	private long NOFFICEID=-1; //机构号ID
	private long NCURRENCYID=-1;//币种号
	private String SSUBJECTCODE;//科目代码
	private String STRANSNO;//交易编号
	private long NTRANSACCOUNTID=-1;//交易帐户ID
	private long NSUBACCOUNTID=-1;//交易子帐户ID
	private long NTRANSACTIONTYPEID=-1;//交易类型
	private long NTRANSDIRECTION=-1;//交易方向
	private double MAMOUNT=0.00;//交易发生额
	private long NOPPACCOUNTID=-1;//对方帐户ID
	private long NOPPSUBACCOUNTID=-1;//对方子帐户ID
	private Timestamp DTEXECUTE=null;//执行日
	private Timestamp DTINTERESTSTART=null;//起息日
	private String SABSTRACT="";//摘要
	private long NSTATUSID=-1;//状态
	private String SBILLNO="";//票据号
	private String SBANKCHEQUENO="";//银行支票号
	private long NBILLTYPEID=-1;//票据类型
	private long NGROUP=-1;		//批组号
	private long NINTERESTBACKFLAG=-1;//利息倒填标志
	private long NSERIALNO=-1;//一付多收序列号
	private long NDISCOUNTBILLID=-1;//
	private long BUDGETITEMID=-1;//预算项目ID  
	private long BUDGETSTATUSID=-1;//是否导入预算标志 
	private String OPPACCOUNTNO="";//对方账户号
	private String OPPACCOUNTNAME="";//对方账户名称
	private long AMOUNTTYPE=-1;//金额类型
	private String dtinputdates="";//执行日由
	private String dtinputdatee="";//执行日至
	private String lbranchid="";//开户行
	private String AccountKind="";//账户组
	private String SACCOUNTNO="";//账户号
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAccountKind() {
		return AccountKind;
	}

	public void setAccountKind(String accountKind) {
		AccountKind = accountKind;
	}

	public long getAMOUNTTYPE() {
		return AMOUNTTYPE;
	}

	public void setAMOUNTTYPE(long amounttype) {
		AMOUNTTYPE = amounttype;
	}

	public long getBUDGETITEMID() {
		return BUDGETITEMID;
	}

	public void setBUDGETITEMID(long budgetitemid) {
		BUDGETITEMID = budgetitemid;
	}

	public long getBUDGETSTATUSID() {
		return BUDGETSTATUSID;
	}

	public void setBUDGETSTATUSID(long budgetstatusid) {
		BUDGETSTATUSID = budgetstatusid;
	}

	public Timestamp getDTEXECUTE() {
		return DTEXECUTE;
	}

	public void setDTEXECUTE(Timestamp dtexecute) {
		DTEXECUTE = dtexecute;
	}

	public String getDtinputdatee() {
		return dtinputdatee;
	}

	public void setDtinputdatee(String dtinputdatee) {
		this.dtinputdatee = dtinputdatee;
	}

	public String getDtinputdates() {
		return dtinputdates;
	}

	public void setDtinputdates(String dtinputdates) {
		this.dtinputdates = dtinputdates;
	}

	public Timestamp getDTINTERESTSTART() {
		return DTINTERESTSTART;
	}

	public void setDTINTERESTSTART(Timestamp dtintereststart) {
		DTINTERESTSTART = dtintereststart;
	}

	public String getLbranchid() {
		return lbranchid;
	}

	public void setLbranchid(String lbranchid) {
		this.lbranchid = lbranchid;
	}

	public double getMAMOUNT() {
		return MAMOUNT;
	}

	public void setMAMOUNT(double mamount) {
		MAMOUNT = mamount;
	}

	public long getNBILLTYPEID() {
		return NBILLTYPEID;
	}

	public void setNBILLTYPEID(long nbilltypeid) {
		NBILLTYPEID = nbilltypeid;
	}

	public long getNCURRENCYID() {
		return NCURRENCYID;
	}

	public void setNCURRENCYID(long ncurrencyid) {
		NCURRENCYID = ncurrencyid;
	}

	public long getNDISCOUNTBILLID() {
		return NDISCOUNTBILLID;
	}

	public void setNDISCOUNTBILLID(long ndiscountbillid) {
		NDISCOUNTBILLID = ndiscountbillid;
	}

	public long getNGROUP() {
		return NGROUP;
	}

	public void setNGROUP(long ngroup) {
		NGROUP = ngroup;
	}

	public long getNINTERESTBACKFLAG() {
		return NINTERESTBACKFLAG;
	}

	public void setNINTERESTBACKFLAG(long ninterestbackflag) {
		NINTERESTBACKFLAG = ninterestbackflag;
	}

	public long getNOFFICEID() {
		return NOFFICEID;
	}

	public void setNOFFICEID(long nofficeid) {
		NOFFICEID = nofficeid;
	}

	public long getNOPPACCOUNTID() {
		return NOPPACCOUNTID;
	}

	public void setNOPPACCOUNTID(long noppaccountid) {
		NOPPACCOUNTID = noppaccountid;
	}

	public long getNOPPSUBACCOUNTID() {
		return NOPPSUBACCOUNTID;
	}

	public void setNOPPSUBACCOUNTID(long noppsubaccountid) {
		NOPPSUBACCOUNTID = noppsubaccountid;
	}

	public long getNSERIALNO() {
		return NSERIALNO;
	}

	public void setNSERIALNO(long nserialno) {
		NSERIALNO = nserialno;
	}

	public long getNSTATUSID() {
		return NSTATUSID;
	}

	public void setNSTATUSID(long nstatusid) {
		NSTATUSID = nstatusid;
	}

	public long getNSUBACCOUNTID() {
		return NSUBACCOUNTID;
	}

	public void setNSUBACCOUNTID(long nsubaccountid) {
		NSUBACCOUNTID = nsubaccountid;
	}

	public long getNTRANSACCOUNTID() {
		return NTRANSACCOUNTID;
	}

	public void setNTRANSACCOUNTID(long ntransaccountid) {
		NTRANSACCOUNTID = ntransaccountid;
	}

	public long getNTRANSACTIONTYPEID() {
		return NTRANSACTIONTYPEID;
	}

	public void setNTRANSACTIONTYPEID(long ntransactiontypeid) {
		NTRANSACTIONTYPEID = ntransactiontypeid;
	}

	public long getNTRANSDIRECTION() {
		return NTRANSDIRECTION;
	}

	public void setNTRANSDIRECTION(long ntransdirection) {
		NTRANSDIRECTION = ntransdirection;
	}

	public String getOPPACCOUNTNAME() {
		return OPPACCOUNTNAME;
	}

	public void setOPPACCOUNTNAME(String oppaccountname) {
		OPPACCOUNTNAME = oppaccountname;
	}

	public String getOPPACCOUNTNO() {
		return OPPACCOUNTNO;
	}

	public void setOPPACCOUNTNO(String oppaccountno) {
		OPPACCOUNTNO = oppaccountno;
	}

	public String getSABSTRACT() {
		return SABSTRACT;
	}

	public void setSABSTRACT(String sabstract) {
		SABSTRACT = sabstract;
	}

	public String getSBANKCHEQUENO() {
		return SBANKCHEQUENO;
	}

	public void setSBANKCHEQUENO(String sbankchequeno) {
		SBANKCHEQUENO = sbankchequeno;
	}

	public String getSBILLNO() {
		return SBILLNO;
	}

	public void setSBILLNO(String sbillno) {
		SBILLNO = sbillno;
	}

	public String getSSUBJECTCODE() {
		return SSUBJECTCODE;
	}

	public void setSSUBJECTCODE(String ssubjectcode) {
		SSUBJECTCODE = ssubjectcode;
	}

	public String getSTRANSNO() {
		return STRANSNO;
	}

	public void setSTRANSNO(String stransno) {
		STRANSNO = stransno;
	}

	public String getSACCOUNTNO() {
		return SACCOUNTNO;
	}

	public void setSACCOUNTNO(String saccountno) {
		SACCOUNTNO = saccountno;
	}

	
}
