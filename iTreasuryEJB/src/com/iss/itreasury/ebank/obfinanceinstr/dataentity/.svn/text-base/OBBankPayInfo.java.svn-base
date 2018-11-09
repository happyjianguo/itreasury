/*
 * Created on Sep 12, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.ebank.obfinanceinstr.dataentity;

import java.sql.Date;
import java.sql.Timestamp;

import com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author ykliu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class OBBankPayInfo  extends ITreasuryBaseDataEntity
{
    
    private long id  = -1;//id
    private long nclientid = -1;//客户id
    private long ncurrencyid = -1;//币种id
    private long ntranstype = -1;//交易类型
    private double mamount  =  0.00;//交易金额
    private Timestamp dtexecute = null;//交易日期（执行日期）
    private String snote = "";//备注
    private long nstatus = -1;//状态
    private long nconfirmuserid = -1;//确认user
    private Timestamp dtconfirm = null;//确认时间
    private long ncheckuserid = -1;//复核人
    private Timestamp dtcheck = null;//复核时间
    private long nsignuserid = -1;//签认人
    private Timestamp dtsign = null;//签认时间
    private long ndeleteuserid = -1;//删除人
    private Timestamp dtdelete = null;//删除时间
    private long niscanaccept = -1;//是否可以发送指令
    private long npayeracctid = -1;//付款方账户id 
    private long npayeeacctid = -1;//收款方账户id
    private String spayeeacctno = ""; //收款方账号SPAYEEACCTNO
    private String spayeeacctname = "";//收款方账户名称SPAYEEACCTNAME
    private String spayeebankname = "";//收款方银行名称SPAYEEBANKNAME
    private String spayeeprov = "";//收款方所在省SPAYEEPROV
    private String spayeecity = "";//收款方所在市
    private long nuserid= -1;      //用户ID（网银业务处理放大镜查询新增）
    private String bankname=null;   //付款方银行名称（网银业务处理放大镜查询新增）
    private String currentbalance=null;//可用余额（网银业务处理放大镜查询新增）
    private String accountno=null;     //付款方账户号 （网银业务处理放大镜查询新增）
    private long id1=-1;               //n_id（网银业务处理放大镜查询新增）
    //新加入三个变量
   
    private String name="";//付款方名称
    private String branchname = ""; //付款方银行名称
    private double balance =0.00; //付款方当前余额
    private String cancelNote="";
    
    
    //下面三个变量为新奥项目所加
    private long nmodule=-1;//属于哪个模块（网银，结算）的交易
    private long budgetSystemID=-1;//预算体系
    private long budgetItemID=-1;//预算项目
    private long budgetStatus = -1 ; //预算的状态
    
    private long nauditinguserid = -1;//审核用户
    private Timestamp dtauditing = null;//审核时间
    //非数据库字段
    private long bankPortalStatus = -1;//
    
    private String bankconnectnumber = null;//银行联行号
    private String departmentnumber = null;//机构号
    private String bankCNAPSNo = null;//CNAPS号
    
    
	private InutParameterInfo inutParameterInfo = null;
	
	
	//银行直联批量复核-翻页查询-新增字段
	private String sStartSubmit = ""; // 提交日期-从
	private String sEndSubmit = ""; // 提交日期-到
	private double dMinAmount = 0.0; // 交易金额-最小值
	private double dMaxAmount = 0.0; // 交易金额-最大值
	private String sStartExe = ""; // 执行日期-从
	private String sEndExe = ""; // 执行日期-到
	private String s_accountno=null;//付款方账号
	private long lOfficeID = -1; // CPF-默认办事处ID
	private String nInputUserID =""; //录入人id
	private long nEbankStatus=-1; //银行指令状态

    
	public String getBankCNAPSNo() {
		return bankCNAPSNo;
	}
	public void setBankCNAPSNo(String bankCNAPSNo) {
		this.bankCNAPSNo = bankCNAPSNo;
	}
    
    
     
    public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}
	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}
	/**
     * @return Returns the dtcheck.
     */
    public Timestamp getDtcheck()
    {
        return dtcheck;
    }
    /**
     * @param dtcheck The dtcheck to set.
     */
    public void setDtcheck(Timestamp dtcheck)
    {
        this.dtcheck = dtcheck;
        putUsedField("dtcheck", dtcheck);
    }
    /**
     * @return Returns the dtconfirm.
     */
    public Timestamp getDtconfirm()
    {
        return dtconfirm;
    }
    public String getFormatDtconfirm()
	{
		return DataFormat.getDateString(dtconfirm);
	}
    /**
     * @param dtconfirm The dtconfirm to set.
     */
    public void setDtconfirm(Timestamp dtconfirm)
    {
        this.dtconfirm = dtconfirm;
        putUsedField("dtconfirm",dtconfirm );
    }
    /**
     * @return Returns the dtdelete.
     */
    public Timestamp getDtdelete()
    {
        return dtdelete;
    }
    /**
     * @param dtdelete The dtdelete to set.
     */
    public void setDtdelete(Timestamp dtdelete)
    {
        this.dtdelete = dtdelete;
        putUsedField("dtdelete", dtdelete);
    }
    /**
     * @return Returns the dtexecute.
     */
    public Timestamp getDtexecute()
    {
        return dtexecute;
    }

    public String getFormatExecuteDate()
	{
		return DataFormat.getDateString(dtexecute);
	}

    public String getFormatDtexecute()
	{
		return DataFormat.getDateString(dtexecute);
	}

    /**
     * @param dtexecute The dtexecute to set.
     */
    public void setDtexecute(Timestamp dtexecute)
    {
        this.dtexecute = dtexecute;
        putUsedField("dtexecute", dtexecute);
    }
    /**
     * @return Returns the dtsign.
     */
    public Timestamp getDtsign()
    {
        return dtsign;
    }
    /**
     * @param dtsign The dtsign to set.
     */
    public void setDtsign(Timestamp dtsign)
    {
        this.dtsign = dtsign;
        putUsedField("dtsign", dtsign);
    }
    /**
     * @return Returns the id.
     */
    public long getId()
    {
        return id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(long id)
    {
        this.id = id;
        putUsedField("id", id);
    }
     
    /**
     * @return Returns the niscanaccept.
     */
    public long getNiscanaccept() {
        return niscanaccept;
    }
    /**
     * @param niscanaccept The niscanaccept to set.
     */
    public void setNiscanaccept(long niscanaccept) {
        this.niscanaccept = niscanaccept;
    }
    /**
     * @return Returns the mamount.
     */
    public double getMamount()
    {
        return mamount;
    }
    public String getFormatMAmount()
	{
		return DataFormat.formatDisabledAmount(mamount);
	}
    /**
     * @param mamount The mamount to set.
     */
    public void setMamount(double mamount)
    {
        this.mamount = mamount;
        putUsedField("mamount", mamount);
    }
    public String getFormatAmount()
	{
		return DataFormat.formatDisabledAmount(mamount);
	}
    
    /**
     * @return Returns the ncheckuserid.
     */
    public long getNcheckuserid()
    {
        return ncheckuserid;
    }
    /**
     * @param ncheckuserid The ncheckuserid to set.
     */
    public void setNcheckuserid(long ncheckuserid)
    {
        this.ncheckuserid = ncheckuserid;
        putUsedField("ncheckuserid", ncheckuserid);
    }
    /**
     * @return Returns the nclientid.
     */
    public long getNclientid()
    {
        return nclientid;
    }
    /**
     * @param nclientid The nclientid to set.
     */
    public void setNclientid(long nclientid)
    {
        this.nclientid = nclientid;
        putUsedField("nclientid", nclientid);
    }
    /**
     * @return Returns the nconfirmuserid.
     */
    public long getNconfirmuserid()
    {
        return nconfirmuserid;
    }
    /**
     * @param nconfirmuserid The nconfirmuserid to set.
     */
    public void setNconfirmuserid(long nconfirmuserid)
    {
        this.nconfirmuserid = nconfirmuserid;
        putUsedField("nconfirmuserid", nconfirmuserid);
    }
    /**
     * @return Returns the ncurrencyid.
     */
    public long getNcurrencyid()
    {
        return ncurrencyid;
    }
    /**
     * @param ncurrencyid The ncurrencyid to set.
     */
    public void setNcurrencyid(long ncurrencyid)
    {
        this.ncurrencyid = ncurrencyid;
        putUsedField("ncurrencyid", ncurrencyid);
    }
    /**
     * @return Returns the ndeleteuserid.
     */
    public long getNdeleteuserid()
    {
        return ndeleteuserid;
    }
    /**
     * @param ndeleteuserid The ndeleteuserid to set.
     */
    public void setNdeleteuserid(long ndeleteuserid)
    {
        this.ndeleteuserid = ndeleteuserid;
        putUsedField("ndeleteuserid", ndeleteuserid);
    }
    
    /**
     * @return Returns the npayeeacctid.
     */
    public long getNpayeeacctid() {
        return npayeeacctid;
    }
    /**
     * @param npayeeacctid The npayeeacctid to set.
     */
    public void setNpayeeacctid(long npayeeacctid) {
        this.npayeeacctid = npayeeacctid;
        putUsedField("npayeeacctid", npayeeacctid);
    }
    /**
     * @return Returns the npayeracctid.
     */
    public long getNpayeracctid() {
        return npayeracctid;
    }
    /**
     * @param npayeracctid The npayeracctid to set.
     */
    public void setNpayeracctid(long npayeracctid) {
        this.npayeracctid = npayeracctid;
        putUsedField("npayeracctid", npayeracctid);
    }
    /**
     * @return Returns the nsignuserid.
     */
    public long getNsignuserid()
    {
        return nsignuserid;
    }
    /**
     * @param nsignuserid The nsignuserid to set.
     */
    public void setNsignuserid(long nsignuserid)
    {
        this.nsignuserid = nsignuserid;
        putUsedField("nsignuserid", nsignuserid);
    }
    /**
     * @return Returns the nstatus.
     */
    public long getNstatus()
    {
        return nstatus;
    }
    /**
     * @param nstatus The nstatus to set.
     */
    public void setNstatus(long nstatus)
    {
        this.nstatus = nstatus;
        putUsedField("nstatus", nstatus);
    }
    /**
     * @return Returns the ntranstype.
     */
    public long getNtranstype()
    {
        return ntranstype;
    }
    /**
     * @param ntranstype The ntranstype to set.
     */
    public void setNtranstype(long ntranstype)
    {
        this.ntranstype = ntranstype;
        putUsedField("ntranstype", ntranstype);
    }
    /**
     * @return Returns the snote.
     */
    public String getSnote()
    {
        return snote;
    }
    /**
     * @param snote The snote to set.
     */
    public void setSnote(String snote)
    {
        this.snote = snote;
        putUsedField("snote", snote);
    }
    
    /**
     * @return Returns the spayeeacctname.
     */
    public String getSpayeeacctname()
    {
        return spayeeacctname;
    }
    /**
     * @param spayeeacctname The spayeeacctname to set.
     */
    public void setSpayeeacctname(String spayeeacctname)
    {
        this.spayeeacctname = spayeeacctname;
        putUsedField("spayeeacctname", spayeeacctname);
    }
    /**
     * @return Returns the spayeeacctno.
     */
    public String getSpayeeacctno()
    {
        return spayeeacctno;
    }
    /**
     * @param spayeeacctno The spayeeacctno to set.
     */
    public void setSpayeeacctno(String spayeeacctno)
    {
        this.spayeeacctno = spayeeacctno;
        putUsedField("spayeeacctno", spayeeacctno);
    }
    /**
     * @return Returns the spayeebankname.
     */
    public String getSpayeebankname()
    {
        return spayeebankname;
    }
    /**
     * @param spayeebankname The spayeebankname to set.
     */
    public void setSpayeebankname(String spayeebankname)
    {
        this.spayeebankname = spayeebankname;
        putUsedField("spayeebankname", spayeebankname);
    }
    /**
     * @return Returns the spayeecity.
     */
    public String getSpayeecity()
    {
        return spayeecity;
    }
    /**
     * @param spayeecity The spayeecity to set.
     */
    public void setSpayeecity(String spayeecity)
    {
        this.spayeecity = spayeecity;
        putUsedField("spayeecity", spayeecity);
    }
    /**
     * @return Returns the spayeeprov.
     */
    public String getSpayeeprov()
    {
        return spayeeprov;
    }
    /**
     * @param spayeeprov The spayeeprov to set.
     */
    public void setSpayeeprov(String spayeeprov)
    {
        this.spayeeprov = spayeeprov;
        putUsedField("spayeeprov", spayeeprov);
    }
    
    
    /**
     * @return Returns the bankPortalStatus.
     */
    public long getBankPortalStatus() {
        return bankPortalStatus;
    }
    /**
     * @param bankPortalStatus The bankPortalStatus to set.
     */
    public void setBankPortalStatus(long bankPortalStatus) {
        this.bankPortalStatus = bankPortalStatus;
    }
	

	/**
	 * @return Returns the nmodule.
	 */
	public long getNmodule() {
		return nmodule;
	}
	/**
	 * @param nmodule The nmodule to set.
	 */
	public void setNmodule(long nmodule) {
		this.nmodule = nmodule;
		putUsedField("nmodule", nmodule);
	}
	
	/**
	 * @return Returns the budgetItemID.
	 */
	public long getBudgetItemID() {
		return budgetItemID;
	}
	/**
	 * @param budgetItemID The budgetItemID to set.
	 */
	public void setBudgetItemID(long budgetItemID) {
		this.budgetItemID = budgetItemID;
		putUsedField("budgetItemID", budgetItemID);
	}
	/**
	 * @return Returns the budgetSystemID.
	 */
	public long getBudgetSystemID() {
		return budgetSystemID;
	}
	/**
	 * @param budgetSystemID The budgetSystemID to set.
	 */
	public void setBudgetSystemID(long budgetSystemID) {
		this.budgetSystemID = budgetSystemID;
		putUsedField("budgetSystemID", budgetSystemID);
	}
	public long getBudgetStatus() {
		return budgetStatus;
	}
	public void setBudgetStatus(long budgetStatus) {
		this.budgetStatus = budgetStatus;
		putUsedField("budgetStatus", budgetStatus);
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
		putUsedField("name", name);
	}
	public Timestamp getDtauditing() {
		return dtauditing;
	}
	public void setDtauditing(Timestamp dtauditing) {
		this.dtauditing = dtauditing;
		putUsedField("dtauditing", dtauditing);
	}
	public long getNauditinguserid() {
		return nauditinguserid;
	}
	public void setNauditinguserid(long nauditinguserid) {
		this.nauditinguserid = nauditinguserid;
		putUsedField("nauditinguserid", nauditinguserid);
	}
	public String getBankconnectnumber() {
		return bankconnectnumber;
	}
	public void setBankconnectnumber(String bankconnectnumber) {
		this.bankconnectnumber = bankconnectnumber;
		putUsedField("bankconnectnumber", bankconnectnumber);
	}
	public String getDepartmentnumber() {
		return departmentnumber;
	}
	public void setDepartmentnumber(String departmentnumber) {
		this.departmentnumber = departmentnumber;
		putUsedField("departmentnumber", departmentnumber);
	}

	public String getBranchname() {
		return branchname;
	}
	public void setBranchname(String branchname) {
		this.branchname = branchname;
		putUsedField("branchname", branchname);
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
		putUsedField("balance", balance);
	}
	 public String getFormatBalance()
		{
			return DataFormat.formatDisabledAmount(balance);
		}
	    

	public String getSStartSubmit() {
		return sStartSubmit;
	}
	public void setSStartSubmit(String startSubmit) {
		sStartSubmit = startSubmit;
		putUsedField("startSubmit", startSubmit);
	}
	public String getSEndSubmit() {
		return sEndSubmit;
	}
	public void setSEndSubmit(String endSubmit) {
		sEndSubmit = endSubmit;
		putUsedField("endSubmit", endSubmit);
	}
	public double getDMinAmount() {
		return dMinAmount;
	}
	public void setDMinAmount(double minAmount) {
		dMinAmount = minAmount;
		putUsedField("minAmount", minAmount);
	}
	public double getDMaxAmount() {
		return dMaxAmount;
	}
	public void setDMaxAmount(double maxAmount) {
		dMaxAmount = maxAmount;
		putUsedField("dMaxAmount", dMaxAmount);
	}
	public String getSStartExe() {
		return sStartExe;
	}
	public void setSStartExe(String startExe) {
		sStartExe = startExe;
		putUsedField("startExe", startExe);
	}
	public String getSEndExe() {
		return sEndExe;
	}
	public void setSEndExe(String endExe) {
		sEndExe = endExe;
		putUsedField("sEndExe", sEndExe);
	}
	public String getS_accountno() {
		return s_accountno;
	}
	public void setS_accountno(String s_accountno) {
		this.s_accountno = s_accountno;
		putUsedField("s_accountno", s_accountno);
	}
	public String getCancelNote() {
		return cancelNote;
	}
	public void setCancelNote(String cancelNote) {
		this.cancelNote = cancelNote;
		putUsedField("cancelNote", cancelNote);
	}
	public long getLOfficeID() {
		return lOfficeID;
	}
	public void setLOfficeID(long officeID) {
		lOfficeID = officeID;
		putUsedField("lOfficeID", lOfficeID);
	}
	public String getNInputUserID() {
		return nInputUserID;
	}
	public void setNInputUserID(String inputUserID) {
		nInputUserID = inputUserID;
		putUsedField("nInputUserID", nInputUserID);
	}
	public long getNEbankStatus() {
		return nEbankStatus;
	}
	public void setNEbankStatus(long ebankStatus) {
		nEbankStatus = ebankStatus;
		putUsedField("nEbankStatus", nEbankStatus);
	}
	public long getNuserid() {
		return nuserid;
	}
	public void setNuserid(long nuserid) {
		this.nuserid = nuserid;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getCurrentbalance() {
		return currentbalance;
	}
	public void setCurrentbalance(String currentbalance) {
		this.currentbalance = currentbalance;
	}
	public String getAccountno() {
		return accountno;
	}
	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}
	public long getId1() {
		return id1;
	}
	public void setId1(long id1) {
		this.id1 = id1;
	}

	
}
