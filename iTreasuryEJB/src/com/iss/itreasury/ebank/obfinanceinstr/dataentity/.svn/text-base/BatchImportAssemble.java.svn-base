/*
 * Created on 2005-8-26
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.ebank.obfinanceinstr.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author xintan
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BatchImportAssemble implements Serializable
{

	/**
	 * 
	 */
	public BatchImportAssemble()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	private long lImportUserID = -1;			//导入用户ID
	private Timestamp dtImportDate = null ; 		//导入时间
	private long lImportStatus = -1; 			// 导入状态
	private String sExceptionInfo = ""; 		// 异常信息
	private String[] strArray = null;		// 信息数组
	private String sRepeatNote=""; //收付款方、金额重复提示信息
	private FinanceInfo financeInfo = null;	
//	private OBBudgetImportInfo obBudgetImportInfo = null;//预算设置实体类
//	private StockTempImportInfo stockTempImportInfo = null;//控股设置实体类

    private long lErrInfoID=-1;
	/**
	 * @return Returns the errInfoID.
	 */
	public long getErrInfoID() {
		return lErrInfoID;
	}

	/**
	 * @param errInfoID The errInfoID to set.
	 */
	public void setErrInfoID(long errInfoID) {
		lErrInfoID = errInfoID;
	}

	/**
	 * @return
	 */
	public FinanceInfo getFinanceInfo()
	{
		return financeInfo;
	}

	/**
	 * @return
	 */
	public long getImportStatus()
	{
		return lImportStatus;
	}

	/**
	 * @return
	 */
	public long getImportUserID()
	{
		return lImportUserID;
	}

	/**
	 * @return
	 */
	public String getExceptionInfo()
	{
		return sExceptionInfo;
	}

	/**
	 * @param info
	 */
	public void setFinanceInfo(FinanceInfo info)
	{
		financeInfo = info;
	}

	/**
	 * @param l
	 */
	public void setImportStatus(long l)
	{
		lImportStatus = l;
	}

	/**
	 * @param l
	 */
	public void setImportUserID(long l)
	{
		lImportUserID = l;
	}

	/**
	 * @param string
	 */
	public void setExceptionInfo(String string)
	{
		sExceptionInfo = string;
	}

	/**
	 * @return
	 */
	public Timestamp getImportDate()
	{
		return dtImportDate;
	}

	/**
	 * @param timestamp
	 */
	public void setImportDate(Timestamp timestamp)
	{
		dtImportDate = timestamp;
	}


	/**
	 * @return
	 */
	public String[] getStrArray()
	{
		return strArray;
	}

	/**
	 * @param strings
	 */
	public void setStrArray(String[] strings)
	{
		strArray = strings;
	}

	public void setRepeatNote(String repeatNote) 
	{		
		this.sRepeatNote = repeatNote;
	}
	
	public String getRepeatNote() 
	{		
		return sRepeatNote;
	}
	
//	/**
//	 * @return Returns the obBudgetImportInfo.
//	 */
//	public OBBudgetImportInfo getObBudgetImportInfo() {
//		return obBudgetImportInfo;
//	}
//	/**
//	 * @param obBudgetImportInfo The obBudgetImportInfo to set.
//	 */
//	public void setObBudgetImportInfo(OBBudgetImportInfo obBudgetImportInfo) {
//		this.obBudgetImportInfo = obBudgetImportInfo;
//	}
//	
//	public StockTempImportInfo getStockTempImportInfo() {
//		return stockTempImportInfo;
//	}
//	public void setStockTempImportInfo(StockTempImportInfo stockTempImportInfo) {
//		this.stockTempImportInfo = stockTempImportInfo;
//	}
}
