/*
 * Created on 2003-9-18
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
import com.iss.itreasury.util.*;
public class QueryCapForm implements java.io.Serializable
{

	/** Creates new QueryForm */
	public QueryCapForm () 
	{
	}
	// 可选条件
	private long lTransType = -1; // 网上银行交易类型
	private String sStartSubmit = ""; // 提交日期-从
	private String sEndSubmit = ""; // 提交日期-到
	private long lStatus = -1; // 交易指令状态
	private double dMinAmount = 0.0; // 交易金额-最小值
	private double dMaxAmount = 0.0; // 交易金额-最大值
	private String sStartExe = ""; // 执行日期-从
	private String sEndExe = ""; // 执行日期-到
	private String sContractNo = ""; //合同号
	private String sDepositNo = ""; //存款单据号
	private long lContractID = -1; //合同ID
	private long lDepositID = -1; //存款单据ID
	private long lChildClientID = -1;//下属单位ID
	private String sChildClientNo = "";//下属单位编号
	// 必选条件
	private long lOperationTypeID=-1;//根据页面菜单，取值来自Notes
	private long lClientID = -1;//取值session
	private long lCurrencyID = -1;//取值session
	private long lUserID = -1;//取值session
	// 排序和分页
	private long lTotalPages = -1;
	private long lPageNo = -1;
	private long lLinesOfPage = -1;
	
	// 可选条件(是否排序)
	private boolean OrderBy = false;
	private long nEbankStatus = -1;
	
	//fro 新奥
	private long nmodule=-1;//属于哪个模块 －-1为网银，其余为结算中心
	private long scopemodule=-1;//查询范围
    private String sign = "";   //标示活期或定期业务
    private String applyCodeFrom = null; //业务申请编号从
    private String applyCodeTo = null; //业务申请编号到    

	
	/**
	 * @return
	 */
	public double getMaxAmount() {
		return dMaxAmount;
	}
	
	public String getFormatMaxAmount()
	{
		return DataFormat.formatDisabledAmount(dMaxAmount);
	}
	/**
	 * @return
	 */
	public double getMinAmount() {
		return dMinAmount;
	}
	
	public String getFormatMinAmount()
	{
		return DataFormat.formatDisabledAmount(dMinAmount);
	}

	/**
	 * @return
	 */
	public long getClientID() {
		return lClientID;
	}

	/**
	 * @return
	 */
	public long getCurrencyID() {
		return lCurrencyID;
	}

	/**
	 * @return
	 */
	public long getLinesOfPage() {
		return lLinesOfPage;
	}

	/**
	 * @return
	 */
	public long getOperationTypeID() {
		return lOperationTypeID;
	}

	/**
	 * @return
	 */
	public long getPageNo() {
		return lPageNo;
	}

	/**
	 * @return
	 */
	public long getStatus() {
		return lStatus;
	}

	/**
	 * @return
	 */
	public long getTotalPages() {
		return lTotalPages;
	}

	/**
	 * @return
	 */
	public long getTransType() {
		return lTransType;
	}

	/**
	 * @return
	 */
	public long getUserID() {
		return lUserID;
	}

	/**
	 * @return
	 */
	public String getContractNo() {
		return sContractNo;
	}

	/**
	 * @return
	 */
	public String getEndExe() {
		return sEndExe;
	}

	/**
	 * @return
	 */
	public String getEndSubmit() {
		return sEndSubmit;
	}

	/**
	 * @return
	 */
	public String getStartExe() {
		return sStartExe;
	}

	/**
	 * @return
	 */
	public String getStartSubmit() {
		return sStartSubmit;
	}

	/**
	 * @param d
	 */
	public void setMaxAmount(double d) {
		dMaxAmount = d;
	}

	/**
	 * @param d
	 */
	public void setMinAmount(double d) {
		dMinAmount = d;
	}

	/**
	 * @param l
	 */
	public void setClientID(long l) {
		lClientID = l;
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l) {
		lCurrencyID = l;
	}

	/**
	 * @param l
	 */
	public void setLinesOfPage(long l) {
		lLinesOfPage = l;
	}

	/**
	 * @param l
	 */
	public void setOperationTypeID(long l) {
		lOperationTypeID = l;
	}

	/**
	 * @param l
	 */
	public void setPageNo(long l) {
		lPageNo = l;
	}

	/**
	 * @param l
	 */
	public void setStatus(long l) {
		lStatus = l;
	}

	/**
	 * @param l
	 */
	public void setTotalPages(long l) {
		lTotalPages = l;
	}

	/**
	 * @param l
	 */
	public void setTransType(long l) {
		lTransType = l;
	}

	/**
	 * @param l
	 */
	public void setUserID(long l) {
		lUserID = l;
	}

	/**
	 * @param string
	 */
	public void setContractNo(String string) {
		sContractNo = string;
	}

	/**
	 * @param string
	 */
	public void setEndExe(String string) {
		sEndExe = string;
	}

	/**
	 * @param string
	 */
	public void setEndSubmit(String string) {
		sEndSubmit = string;
	}

	/**
	 * @param string
	 */
	public void setStartExe(String string) {
		sStartExe = string;
	}

	/**
	 * @param string
	 */
	public void setStartSubmit(String string) {
		sStartSubmit = string;
	}

	/**
	 * Returns the depositNo.
	 * @return String
	 */
	public String getDepositNo() {
		return sDepositNo;
	}

	/**
	 * Sets the depositNo.
	 * @param depositNo The depositNo to set
	 */
	public void setDepositNo(String depositNo) {
		sDepositNo = depositNo;
	}

	/**
	 * Returns the contractID.
	 * @return long
	 */
	public long getContractID() {
		return lContractID;
	}

	/**
	 * Returns the depositID.
	 * @return long
	 */
	public long getDepositID() {
		return lDepositID;
	}

	/**
	 * Sets the contractID.
	 * @param contractID The contractID to set
	 */
	public void setContractID(long contractID) {
		lContractID = contractID;
	}

	/**
	 * Sets the depositID.
	 * @param depositID The depositID to set
	 */
	public void setDepositID(long depositID) {
		lDepositID = depositID;
	}

	/**
	 * Returns the orderBy.
	 * @return boolean
	 */
	public boolean isOrderBy() {
		return OrderBy;
	}

	/**
	 * Sets the orderBy.
	 * @param orderBy The orderBy to set
	 */
	public void setOrderBy(boolean orderBy) {
		OrderBy = orderBy;
	}

	/**
	 * @return
	 */
	public long getChildClientID()
	{
		return lChildClientID;
	}

	/**
	 * @return
	 */
	public String getChildClientNo()
	{
		return sChildClientNo;
	}

	/**
	 * @param l
	 */
	public void setChildClientID(long l)
	{
		lChildClientID = l;
	}

	/**
	 * @param string
	 */
	public void setChildClientNo(String string)
	{
		sChildClientNo = string;
	}
	/**
	 * @param l
	 */
	public void setNmodule(long l) {
		nmodule = l;
	}
	public void setScopemodule(long l) {
		scopemodule = l;
	}

	/**
	 * @return Returns the nmodule.
	 */
	public long getNmodule() {
		return nmodule;
	}
	/**
	 * @return Returns the scopemodule.
	 */
	public long getScopemodule() {
		return scopemodule;
	}

    /**
     * @return Returns the applyCodeFrom.
     */
    public String getApplyCodeFrom() {
        return applyCodeFrom;
    }

    /**
     * @return Returns the applyCodeTo.
     */
    public String getApplyCodeTo() {
        return applyCodeTo;
    }

    /**
     * @param applyCodeFrom The applyCodeFrom to set.
     */
    public void setApplyCodeFrom(String applyCodeFrom) {
        this.applyCodeFrom = applyCodeFrom;
    }

    /**
     * @param applyCodeTo The applyCodeTo to set.
     */
    public void setApplyCodeTo(String applyCodeTo) {
        this.applyCodeTo = applyCodeTo;
    }

	public long getNEbankStatus() {
		return nEbankStatus;
	}

	public void setNEbankStatus(long ebankStatus) {
		nEbankStatus = ebankStatus;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
    
    
}

