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
	
	//Added by leiyang 2008/01/21 �����Ӹ���Ŀ��ѯʱҲ���ã���ѯ�ֶ�ʹ�ÿ�ĿID
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
	
	//���������˻�ID���Ҷ�Ӧ�Ŀ�Ŀ����
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
	
	//Added by leiyang 2008/01/22 �����Ӹ���Ŀ��ѯʱҲ���ã���ѯ�ֶ�ʹ�ÿ�ĿID
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
	 * �ս����˻���(New)
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
	 * @author ���ָ�
	 * 2008/06/23
	 * �ſ�֪ͨ������excel
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
	 * �����������ܲ�ѯ
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
	
	//Added by leiyang 2008/01/22 �����Ӹ���Ŀ��ѯ��Ŀ�����ϸ��ϢʱҲ���ã���ѯ�ֶ�ʹ�ÿ�ĿID
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
	 *�������κ�������״̬����Ϊ���κź�״̬
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
	 *�������κ����õ����׺Ų���Ϊ���κ�
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
	 *���ݽ��׺����õ����κŲ���Ϊ���׺�
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
	 *�������κ����õ�������Ϣ
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
	 *���ݽ��׺ź�״̬���õ�������Ϣ
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
	 * �ս��Ŀ���ܲ�ѯ
	 * ��ѯ����ʹ��AccountRecordConditionInfo Bean
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
	 * �ս��Ŀ���ܲ�ѯ
	 * ��ѯ����ʹ��AccountRecordConditionInfo Bean
	 * �ս��Ŀ���ܲ�ѯ�����ý����ٴο�Ŀ��ϸ��ѯ
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
	 * �ս��Ŀ���ܲ�ѯ(��ѯ���ڱ������)
	 * ��ѯ����ʹ��AccountRecordConditionInfo Bean
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
	 * �ս��Ŀ���ܲ�ѯ(��ѯ������Ŀ����)
	 * ��ѯ����ʹ��AccountRecordConditionInfo Bean
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
