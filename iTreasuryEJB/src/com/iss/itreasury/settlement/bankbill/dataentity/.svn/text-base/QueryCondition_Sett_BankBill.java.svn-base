/*
 * Created on 2003-10-28
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.bankbill.dataentity;
import java.sql.*;
import java.io.Serializable;

/**
 * @author lgwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QueryCondition_Sett_BankBill implements Serializable{
	private String strOrderBy="NTYPEID";			//默认排序类型为"NTYPEID"
	private long lDesc = -2;//正反序
	private long lCurrentPageNo=1;
	
	private long lBankID 				= -1; 		//发票银行ID
	private long lTypeID 				= -1; 		//票据类型ID
	private String strBillNoStart 		= ""; 		//开始票据编号
	private String strBillNoEnd 		= ""; 		//截止票据编号
	private long lClientID 				= -1; 		//申领客户ID
	private String strClientNo 			= ""; 		//申领客户编号
	private String strClientName 		= ""; 		//申领客户名称
	private long lRegisterInputUserID 	= -1; 		//注册录入人ID
	private long lLostInputUserID 		= -1; 		//挂失录入人ID
	private Timestamp tsRegisterStart 	= null; 	//录入（注册）开始日期
	private Timestamp tsRegisterEnd 	= null; 	//录入（注册）截止日期
	private Timestamp tsRequireStart 	= null; 	//录入（申领）开始日期
	private Timestamp tsRequireEnd 		= null; 	//录入（申领）截止日期
	private Timestamp tsReportLostStart = null; 	//录入（挂失）开始日期
	private Timestamp tsReportLostEnd 	= null; 	//录入（挂失）截止日期
	private long lBillStatusID 			= -1; 		//票据状态ID
	
	/**
	 * @return Returns the lBankID.
	 */
	public long getLBankID() {
		return lBankID;
	}
	
	/**
	 * @param bankID The lBankID to set.
	 */
	public void setLBankID(long bankID) {
		lBankID = bankID;
	}
	
	public String getStrOrderBy(){
		return strOrderBy;
	}
	
	public void setStrOrderBy(String orderBy){
		strOrderBy=orderBy;
	}
	
	/**
	 * @return Returns the lBillStatusID.
	 */
	public long getLBillStatusID() {
		return lBillStatusID;
	}

	/**
	 * @param billStatusID The lBillStatusID to set.
	 */
	public void setLBillStatusID(long billStatusID) {
		lBillStatusID = billStatusID;
	}

	/**
	 * @return Returns the lDesc.
	 */
	public long getLDesc() {
		return lDesc;
	}

	/**
	 * @param desc The lDesc to set.
	 */
	public void setLDesc(long desc) {
		lDesc = desc;
	}

	/**
	 * @return Returns the lLostInputUserID.
	 */
	public long getLLostInputUserID() {
		return lLostInputUserID;
	}

	/**
	 * @param lostInputUserID The lLostInputUserID to set.
	 */
	public void setLLostInputUserID(long lostInputUserID) {
		lLostInputUserID = lostInputUserID;
	}

	/**
	 * @return Returns the lRegisterInputUserID.
	 */
	public long getLRegisterInputUserID() {
		return lRegisterInputUserID;
	}

	/**
	 * @param registerInputUserID The lRegisterInputUserID to set.
	 */
	public void setLRegisterInputUserID(long registerInputUserID) {
		lRegisterInputUserID = registerInputUserID;
	}

	/**
	 * @return Returns the lRequireClientID.
	 */
	public long getLClientID() {
		return lClientID;
	}

	/**
	 * @param requireClientID The lRequireClientID to set.
	 */
	public void setLClientID(long requireClientID) {
		lClientID = requireClientID;
	}

	/**
	 * @return Returns the lTypeID.
	 */
	public long getLTypeID() {
		return lTypeID;
	}

	/**
	 * @param typeID The lTypeID to set.
	 */
	public void setLTypeID(long typeID) {
		lTypeID = typeID;
	}

	/**
	 * @return Returns the strBillNoEnd.
	 */
	public String getStrBillNoEnd() {
		return strBillNoEnd;
	}

	/**
	 * @param strBillNoEnd The strBillNoEnd to set.
	 */
	public void setStrBillNoEnd(String strBillNoEnd) {
		this.strBillNoEnd = strBillNoEnd;
	}

	/**
	 * @return Returns the strBillNoStart.
	 */
	public String getStrBillNoStart() {
		return strBillNoStart;
	}

	/**
	 * @param strBillNoStart The strBillNoStart to set.
	 */
	public void setStrBillNoStart(String strBillNoStart) {
		this.strBillNoStart = strBillNoStart;
	}

	/**
	 * @return Returns the strRequireClientName.
	 */
	public String getStrClientName() {
		return strClientName;
	}

	/**
	 * @param strRequireClientName The strRequireClientName to set.
	 */
	public void setStrClientName(String strRequireClientName) {
		this.strClientName = strRequireClientName;
	}

	/**
	 * @return Returns the strRequireClientNo.
	 */
	public String getStrClientNo() {
		return strClientNo;
	}

	/**
	 * @param strRequireClientNo The strRequireClientNo to set.
	 */
	public void setStrClientNo(String strRequireClientNo) {
		this.strClientNo = strRequireClientNo;
	}

	/**
	 * @return Returns the tsRegisterEnd.
	 */
	public Timestamp getTsRegisterEnd() {
		return tsRegisterEnd;
	}

	/**
	 * @param tsRegisterEnd The tsRegisterEnd to set.
	 */
	public void setTsRegisterEnd(Timestamp tsRegisterEnd) {
		this.tsRegisterEnd = tsRegisterEnd;
	}

	/**
	 * @return Returns the tsRegisterStart.
	 */
	public Timestamp getTsRegisterStart() {
		return tsRegisterStart;
	}

	/**
	 * @param tsRegisterStart The tsRegisterStart to set.
	 */
	public void setTsRegisterStart(Timestamp tsRegisterStart) {
		this.tsRegisterStart = tsRegisterStart;
	}

	/**
	 * @return Returns the tsReportLostEnd.
	 */
	public Timestamp getTsReportLostEnd() {
		return tsReportLostEnd;
	}

	/**
	 * @param tsReportLostEnd The tsReportLostEnd to set.
	 */
	public void setTsReportLostEnd(Timestamp tsReportLostEnd) {
		this.tsReportLostEnd = tsReportLostEnd;
	}

	/**
	 * @return Returns the tsReportLostStart.
	 */
	public Timestamp getTsReportLostStart() {
		return tsReportLostStart;
	}

	/**
	 * @param tsReportLostStart The tsReportLostStart to set.
	 */
	public void setTsReportLostStart(Timestamp tsReportLostStart) {
		this.tsReportLostStart = tsReportLostStart;
	}

	/**
	 * @return Returns the tsRequireEnd.
	 */
	public Timestamp getTsRequireEnd() {
		return tsRequireEnd;
	}

	/**
	 * @param tsRequireEnd The tsRequireEnd to set.
	 */
	public void setTsRequireEnd(Timestamp tsRequireEnd) {
		this.tsRequireEnd = tsRequireEnd;
	}

	/**
	 * @return Returns the tsRequireStart.
	 */
	public Timestamp getTsRequireStart() {
		return tsRequireStart;
	}

	/**
	 * @param tsRequireStart The tsRequireStart to set.
	 */
	public void setTsRequireStart(Timestamp tsRequireStart) {
		this.tsRequireStart = tsRequireStart;
	}

	/**
	 * @return Returns the lCurrentPageNo.
	 */
	public long getLCurrentPageNo() {
		return lCurrentPageNo;
	}

	/**
	 * @param currentPageNo The lCurrentPageNo to set.
	 */
	public void setLCurrentPageNo(long currentPageNo) {
		lCurrentPageNo = currentPageNo;
	}

}
