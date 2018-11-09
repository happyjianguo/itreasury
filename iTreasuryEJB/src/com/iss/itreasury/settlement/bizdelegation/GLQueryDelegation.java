/*
 * Created on 2003-9-27
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.bizdelegation;
import java.sql.Timestamp;
import java.util.Collection;

import com.iss.itreasury.settlement.generalledger.dao.sett_GLEntryDAO;
import com.iss.itreasury.settlement.query.paraminfo.AccountRecordConditionInfo;
import com.iss.itreasury.settlement.query.queryobj.QueryGL;
import com.iss.itreasury.settlement.query.resultinfo.AccountRecordInfo;
import com.iss.itreasury.settlement.query.resultinfo.PrintGLInfo;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import com.iss.system.dao.PageLoader;
/**
 * @author yychen
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GLQueryDelegation
{
	public Collection findGLTransType(String strAccount,long lOfficeID, long lCurrencyID, Timestamp tsDateStart, Timestamp tsDateEnd,long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) throws IException
	{
		Collection coll = null;
		try
		{
			QueryGL glquery = new QueryGL();
			coll = glquery.findGLTransType(strAccount,lOfficeID, lCurrencyID, tsDateStart, tsDateEnd,lPageLineCount, lPageNo, lOrderParam, lDesc);
		}
		catch (Exception e)
		{
			e.printStackTrace();throw new IException();
		}
		return coll;
	}
	
	//Added by leiyang 2008/01/21 在用子父科目查询时也可用，查询字段使用科目ID
	public Collection findGLTransType(long lAccount,long lOfficeID, long lCurrencyID, Timestamp tsDateStart, Timestamp tsDateEnd,long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) throws IException
	{
		Collection coll = null;
		try
		{
			QueryGL glquery = new QueryGL();
			coll = glquery.findGLTransType(lAccount,lOfficeID, lCurrencyID, tsDateStart, tsDateEnd,lPageLineCount, lPageNo, lOrderParam, lDesc);
		}
		catch (Exception e)
		{
			e.printStackTrace();throw new IException();
		}
		return coll;
	}
	
	//根据所的账户ID查找对应的科目集合
	public Collection findGLTransTypeForAll(long lOfficeID, long lCurrencyID, Timestamp tsDateStart, Timestamp tsDateEnd,long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) throws IException
	{
		Collection coll = null;
		try
		{
			QueryGL glquery = new QueryGL();
			coll = glquery.findGLTransTypeForAll(lOfficeID, lCurrencyID, tsDateStart, tsDateEnd,lPageLineCount, lPageNo, lOrderParam, lDesc);
		}
		catch (Exception e)
		{
			e.printStackTrace();throw new IException();
		}
		return coll;
	}
	
	//Added by leiyang 2008/01/22 在用子父科目查询时也可用，查询字段使用科目ID
	public Collection findGLDetails(long lAccount, String strAccount, String strTransNo, long lOfficeID, long lCurrencyID, long lTypeID, long lTransTypeID, Timestamp tsDateStart, Timestamp tsDateEnd, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) throws IException
	{
		Collection coll = null;
		try
		{
			QueryGL glquery = new QueryGL();
			coll = glquery.findGLDetails(lAccount,strAccount, strTransNo, lOfficeID, lCurrencyID, lTypeID, lTransTypeID, tsDateStart, tsDateEnd, lPageLineCount, lPageNo, lOrderParam, lDesc);
		}
		catch (Exception e)
		{
			Log.print("e.getMessage()" + e.getMessage());
			e.printStackTrace();throw new IException();
		}
		return coll;
	}
	
	public Collection findGLDetails(String strRootAccount,String strAccount, String strTransNo, long lOfficeID, long lCurrencyID, long lTypeID, long lTransTypeID, Timestamp tsDateStart, Timestamp tsDateEnd, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) throws IException
	{
		Collection coll = null;
		try
		{
			QueryGL glquery = new QueryGL();
			Log.print("strRootAccount=" + strRootAccount);
			Log.print("strAccount=" + strAccount);
			coll = glquery.findGLDetails(strRootAccount,strAccount, strTransNo, lOfficeID, lCurrencyID, lTypeID, lTransTypeID, tsDateStart, tsDateEnd, lPageLineCount, lPageNo, lOrderParam, lDesc);
			Log.print("strAccount=" + strAccount);
		}
		catch (Exception e)
		{
			Log.print("e.getMessage()" + e.getMessage());
			e.printStackTrace();throw new IException();
		}
		return coll;
	}
	
	public Collection findDailyAccountRecord(long lOfficeID, long lCurrencyID, Timestamp tsDateStart, Timestamp tsDateEnd, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) throws IException
	{
		Collection coll = null;
		try
		{
			QueryGL glquery = new QueryGL();
			coll = glquery.findDailyAccountRecord(lOfficeID, lCurrencyID, tsDateStart, tsDateEnd, lPageLineCount, lPageNo, lOrderParam, lDesc);
		}
		catch (Exception e)
		{
			e.printStackTrace();throw new IException();
		}
		return coll;
	}
	
	/**
	 * Added by leiyang 2008/01/28
	 * 日结会计账汇总(New)
	 */
	public Collection findDailyAccountRecordNew(long lOfficeID, long lCurrencyID, Timestamp tsDateStart, Timestamp tsDateEnd, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc,String strSubjectStart,
			String strSubjectEnd,
	        long iflk) throws IException
	{
		Collection coll = null;
		try
		{
			QueryGL glquery = new QueryGL();
			coll = glquery.findDailyAccountRecordNew(lOfficeID, lCurrencyID, tsDateStart, tsDateEnd, lPageLineCount, lPageNo, lOrderParam, lDesc,strSubjectStart,
			        strSubjectEnd,
			        iflk);
		}
		catch (Exception e)
		{
			e.printStackTrace();throw new IException();
		}
		return coll;
	}
	
	/**
	 * @author 马现福
	 * 2008/06/23
	 * 放款通知单导出excel
	 */
	public Collection findPayFormInfo(long lOfficeID, long lCurrencyID,String StrContractCode, String strDateFrom, String strDateTo, long lOrderParam, long lDesc) throws IException
	{
		Collection coll = null;
		try
		{
			QueryGL glquery = new QueryGL();
			coll = glquery.findPayFormInfo(lOfficeID, lCurrencyID,StrContractCode, strDateFrom, strDateTo,lOrderParam, lDesc);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException();
		}
		return coll;
	}
	
	/**
	 * Added by leiyang 2008/03/11
	 * 开户行余额汇总查询
	 */
	public Collection findBankAccountRecord(long lOfficeID, long lCurrencyID, Timestamp tsDate, String strBankCodeStart, String strBankCodeEnd, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) throws IException
	{
		Collection coll = null;
		try
		{
			QueryGL glquery = new QueryGL();
			coll = glquery.findBankAccountRecord(lOfficeID, lCurrencyID, tsDate, strBankCodeStart, strBankCodeEnd, lPageLineCount, lPageNo, lOrderParam, lDesc);
		}
		catch (Exception e)
		{
			e.printStackTrace();throw new IException();
		}
		return coll;
	}
	
	public PageLoader findDailyAccountRecordrj(long lOfficeID, long lCurrencyID, Timestamp tsDateStart, Timestamp tsDateEnd, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) throws IException
	{
		PageLoader pageLoader = null;
		try
		{
			QueryGL glquery = new QueryGL();
			pageLoader = glquery.findDailyAccountRecordrj(lOfficeID, lCurrencyID, tsDateStart, tsDateEnd, lPageLineCount, lPageNo, lOrderParam, lDesc);
		}
		catch (Exception e)
		{
			e.printStackTrace();throw new IException();
		}
		return pageLoader;
	}
	public AccountRecordInfo findDailyAccountRecordhz(long lOfficeID, long lCurrencyID, Timestamp tsDateStart, Timestamp tsDateEnd, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) throws IException
	{
		AccountRecordInfo accountRecordInfo = null;
		try
		{
			QueryGL glquery = new QueryGL();
			accountRecordInfo = glquery.findDailyAccountRecordhz(lOfficeID, lCurrencyID, tsDateStart, tsDateEnd, lPageLineCount, lPageNo, lOrderParam, lDesc);
		}
		catch (Exception e)
		{
			e.printStackTrace();throw new IException();
		}
		return accountRecordInfo;
	}
	public Collection findTransactionAccountRecord(long lOfficeID, long lCurrencyID, long lTransactionNoID, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) throws IException
	{
		Collection coll = null;
		try
		{
			QueryGL glquery = new QueryGL();
		}
		catch (Exception e)
		{
			e.printStackTrace();throw new IException();
		}
		return coll;
	}
	public Collection PrintGL(long lOfficeID,long lCurrencyID,PrintGLInfo printGLInfo)throws IException
	{
		Collection coll = null;
		try
		{
			QueryGL glquery = new QueryGL();
			coll=glquery.PrintGL(lOfficeID,lCurrencyID,printGLInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();throw new IException();
		}
		return coll;
		
	}
	public Collection PrintGLfy(long lOfficeID,long lCurrencyID,PrintGLInfo printGLInfo, long lPageLineCount, long lPageNo)throws IException
	{
		Collection coll = null;
		try
		{
			QueryGL glquery = new QueryGL();
			coll=glquery.PrintGLfy(lOfficeID,lCurrencyID,printGLInfo, lPageLineCount, lPageNo);
		}
		catch (Exception e)
		{
			e.printStackTrace();throw new IException();
		}
		return coll;
		
	}
	
	public double getGLBalance(long lOfficeID,long lCurrencyID,PrintGLInfo printGLInfo) throws IException
	{
		double balance=0.0;
		try
		{
			QueryGL glquery = new QueryGL();
			balance=glquery.getGLBalance(lOfficeID,lCurrencyID,printGLInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();throw new IException();
		}	
		return balance;
		
	}
	
	//Added by leiyang 2008/01/22 在用子父科目查询科目金额详细信息时也可用，查询字段使用科目ID
	public AccountRecordInfo getDailyAccountRecord(long lAccount, long lOfficeID, long lCurrencyID, Timestamp tsDateStart, Timestamp tsDateEnd) throws IException
	{
		AccountRecordInfo accountRecordInfo = null;
		try
		{
			QueryGL glquery = new QueryGL();
			accountRecordInfo = glquery.getDailyAccountRecord(lAccount, lOfficeID, lCurrencyID, tsDateStart, tsDateEnd);
		}
		catch (Exception e)
		{
			e.printStackTrace();throw new IException();
		}
		return accountRecordInfo;
	}
	
	/**
	 * 
	 *根据批次号来更新状态参数为批次号和状态
	 *
	 **/
	public void updateBySbatchNo(String sbatchno,long lStatus) throws IException{
		sett_GLEntryDAO entryDAO = new sett_GLEntryDAO(); 
		try {
			entryDAO.updateBySbatchNo(sbatchno, lStatus);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException();
		}
	}
	
	/**
	 * 
	 *根据批次号来得到交易号参数为批次号
	 *
	 **/
	public Collection findTransNoBySbatchNo(String sbatchno) throws IException{
		sett_GLEntryDAO entryDAO = new sett_GLEntryDAO(); 
		Collection findTransNoColl = null;
		try {
			findTransNoColl = entryDAO.findTransNoBySbatchNo(sbatchno);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException();
		}
		return findTransNoColl;
	}
	
	/**
	 * 
	 *根据交易号来得到批次号参数为交易号
	 *
	 **/
	public String findSbatchNoByTransNo(String stransNo) throws IException{
		sett_GLEntryDAO entryDAO = new sett_GLEntryDAO(); 
		String sbatchNo = "";
		try {
			sbatchNo = entryDAO.findSbatchNoByTransNo(stransNo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException();
		}
		return sbatchNo;
	}
	
	/**
	 * 
	 *根据批次号来得到整个信息
	 *
	 **/
	public Collection findAllBySbatchNo(String sbatchno) throws IException{
		sett_GLEntryDAO entryDAO = new sett_GLEntryDAO(); 
		Collection findAllColl = null;
		try {
			findAllColl = entryDAO.findAllBySbatchNo(sbatchno);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException();
		}
		return findAllColl;
	}
	
	/**
	 * 
	 *根据交易号和状态来得到整个信息
	 *
	 **/
	public Collection findByTransNoAndStatusID(String stranNo, long statusID) throws Exception{
		sett_GLEntryDAO entryDAO = new sett_GLEntryDAO();
		Collection findAllColl = null;
		try {
			findAllColl = entryDAO.findByTransNoAndStatusID(stranNo,statusID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException();
		}
		return findAllColl;
	}
	
	/*******************************************************************************************************
	 *
	 */
	
	/**
	 * Create 2008-12-25
	 * 日结科目汇总查询
	 * 查询条件使用AccountRecordConditionInfo Bean
	 * 
	 */
	public PageLoader findDailyAccountRecord(AccountRecordConditionInfo conditionInfo)
		throws IException
	{
		PageLoader pageLoader = null;
		try
		{
			QueryGL glquery = new QueryGL();
			pageLoader = glquery.findDailyAccountRecord(conditionInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException();
		}
		return pageLoader;
	}
	
	/**
	 * Create 2008-12-25
	 * 日结科目汇总查询
	 * 查询条件使用AccountRecordConditionInfo Bean
	 * 日结科目汇总查询，不用进行再次科目明细查询
	 * @param conditionInfo
	 */
	public Collection findDailyAccountRecordCollection(AccountRecordConditionInfo conditionInfo)
		throws IException
	{
		Collection coll = null;
		try
		{
			QueryGL glquery = new QueryGL();
			coll = glquery.findDailyAccountRecordCollection(conditionInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException();
		}
		return coll;
	}
	
	/**
	 * Create 2008-12-25
	 * 日结科目汇总查询(查询表内表外汇总)
	 * 查询条件使用AccountRecordConditionInfo Bean
	 * 
	 */
	public AccountRecordInfo findDailyAccountRecordCount(AccountRecordConditionInfo conditionInfo)
		throws IException
	{
		AccountRecordInfo accountRecordInfo = null;
		try
		{
			QueryGL glquery = new QueryGL();
			accountRecordInfo = glquery.findDailyAccountRecordCount(conditionInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException();
		}
		return accountRecordInfo;
	}

	/**
	 * Create 2008-12-25
	 * 日结科目汇总查询(查询单个科目汇总)
	 * 查询条件使用AccountRecordConditionInfo Bean
	 */
	public AccountRecordInfo getDailyAccountRecord(AccountRecordConditionInfo conditionInfo)
		throws IException
	{
		AccountRecordInfo accountRecordInfo = null;
		try
		{
			QueryGL glquery = new QueryGL();
			accountRecordInfo = glquery.getDailyAccountRecord(conditionInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException();
		}
		return accountRecordInfo;
	}
}
