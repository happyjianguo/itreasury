/*
 * Created on 2004-11-25
 *
 * Title:				iTreasury
 * @author             	yfan 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */
package com.iss.itreasury.loan.assuremanagementnotice.dao;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.assurechargenotice.dataentity.AssureChargeNoticeInfo;
import com.iss.itreasury.loan.assuremanagementnotice.dataentity.AssureManagementNoticeInfo;
import com.iss.itreasury.loan.assuremanagementnotice.dataentity.AssureManagementQueryInfo;
import com.iss.itreasury.loan.base.LoanDAO;
import com.iss.itreasury.loan.base.LoanDAOException;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.loan.util.LOANConstant.AssureManagementNoticeStatus;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.system.bizdelegation.ApprovalDelegation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AssureManagementNoticeDao extends LoanDAO
{
	protected Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
	public AssureManagementNoticeDao()
	{
		super("LOAN_ASSUREMANAGEMENTFORM");
	}
    /**
     * ����������ѯ
     * @param ContractID
     * @param CurrencyID
     * @param OfficeID
     * @param InputUserID
     * @param TypeID
     * @return
     * @throws LoanDAOException
     */
	public AssureManagementNoticeInfo findByCondition(long ContractID,
			long CurrencyID, long OfficeID, long InputUserID, long TypeID
			) throws LoanDAOException {
		String strSQL = "";
		AssureManagementNoticeInfo assureManagementNoticeInfo = new AssureManagementNoticeInfo();
		try
        {
		    try
            {
            	initDAO();
            }
            catch (ITreasuryDAOException e)
            {
            	throw new LoanDAOException(e);
            }
			strSQL = "select * from LOAN_ASSUREMANAGEMENTFORM where ContractID  = "+ContractID +
			                                                  " and CurrencyID  = "+CurrencyID +
			                                                  " and OfficeID    = "+OfficeID   +
			                                                  " and InputUserID = "+InputUserID+
			                                                  " and TypeID      = "+TypeID ;
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();
        	if (rs != null && rs.next())
        	{
        			assureManagementNoticeInfo.setId(rs.getLong("ID")); //id
					assureManagementNoticeInfo.setContractID(rs.getLong("ContractID")); //��ͬID
            		assureManagementNoticeInfo.setOfficeID(rs.getLong("OfficeID")); //���´�
            		assureManagementNoticeInfo.setCurrencyID(rs.getLong("CurrencyID")); //����
            		assureManagementNoticeInfo.setCode(rs.getString("Code")); //��������
            		assureManagementNoticeInfo.setTypeID(rs.getLong("TypeID")); //
            		assureManagementNoticeInfo.setAssureAmount(rs.getDouble("AssureAmount")); //
            		assureManagementNoticeInfo.setRecognizanceAccountID(rs.getLong("RecognizanceAccountID")); //
            		assureManagementNoticeInfo.setRecognizanceAmount(rs.getDouble("RecognizanceAmount")); //
            		assureManagementNoticeInfo.setAssureChargeAccountID(rs.getLong("AssureChargeAccountID")); //
            		assureManagementNoticeInfo.setAssureChargeAmount(rs.getDouble("AssureChargeAmount")); //
            		assureManagementNoticeInfo.setStartDate(rs.getTimestamp("StartDate")); //������ʼ����
					assureManagementNoticeInfo.setEndDate(rs.getTimestamp("EndDate")); //������������
					assureManagementNoticeInfo.setIntervalNum(rs.getLong("IntervalNum")); //����
            		assureManagementNoticeInfo.setNextCheckUserID(rs.getLong("NextCheckUserID")); //��һ�������
            		assureManagementNoticeInfo.setNextCheckLevel(rs.getLong("NextCheckLevel")); //��һ����˼���
            		assureManagementNoticeInfo.setInputUserID(rs.getLong("InputUserID")); //¼����
            		assureManagementNoticeInfo.setInputDate(rs.getTimestamp("InputDate")); //¼��ʱ��
            		assureManagementNoticeInfo.setStatusID(rs.getLong("StatusID")); //״̬
            		assureManagementNoticeInfo.setIsLowLevel(rs.getLong("IsLowLevel")); //�Ƿ������˼���
            }
	        try
	        {
	        	finalizeDAO();
	        }
	        catch (ITreasuryDAOException e)
	        {
	        	throw new LoanDAOException(e);
	        }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
		{
			try
            {
                finalizeDAO();
            } catch (ITreasuryDAOException e1)
            {
                e1.printStackTrace();
            }			
		}
		return assureManagementNoticeInfo;
	}
	/*
	 * 
	 */
	public Collection findByMultiOption(AssureManagementQueryInfo qInfo) throws LoanDAOException
	{
		String strSelect = "";
		String strSQL = "";
		String strSQL1 = "";
		Vector v = new Vector();

		long typeID = qInfo.getTypeID();
		long clientID = qInfo.getClientID();
		Timestamp startDate = qInfo.getQueryStartDate();
		Timestamp endDate = qInfo.getQueryEndDate();
		long statusID = qInfo.getStatusID();
		long userID = qInfo.getUserID();
		String strUser = qInfo.getStrUser();
		long queryPurpose = qInfo.getQueryPurpose();
		long contractIDStart = qInfo.getContractIDStart();
		long contractIDEnd = qInfo.getContractIDEnd();
		String contractCodeStart = qInfo.getContractCodeStart();
		String contractCodeEnd = qInfo.getContractCodeEnd();		
		long currencyID = qInfo.getCurrencyID();
		long officeID = qInfo.getOfficeID();
		
		long pageLineCount = qInfo.getPageLineCount();
		long pageNo = qInfo.getPageNo();
		long orderParam = qInfo.getOrderParam();
		long desc = qInfo.getDesc();
		String orderParamString = qInfo.getOrderParamString();
		long recordCount = -1;
		long pageCount = -1;
		long rowNumStart = -1;
		long rowNumEnd = -1;
		
		try
        {
            try
            {
            	initDAO();
            }
            catch (ITreasuryDAOException e)
            {
            	throw new LoanDAOException(e);
            }
            //�����¼����				
            if (queryPurpose == 1) //for modify
            {
            	strSQL = "";
            	strSelect = " select count(*) ";
            	strSQL = " from LOAN_ASSUREMANAGEMENTFORM a,Loan_ContractForm b,Client c "
            			+ " where a.ContractID = b.ID and b.nBorrowClientID = c.ID(+) "
            			+ " and a.StatusID >= "
            			+ LOANConstant.AssureManagementNoticeStatus.SUBMIT
            			+ " and a.StatusID <= "
            			+ LOANConstant.AssureManagementNoticeStatus.CHECK
            			+ " and a.InputUserID = "
            			+ userID;
            	if (statusID == LOANConstant.AssureManagementNoticeStatus.SUBMIT)
            	{
            		strSQL += " and a.nextCheckLevel = 1 ";
            		strSQL += " and a.StatusID = " + LOANConstant.AssureManagementNoticeStatus.SUBMIT;
            	}
            	else
            	{
            		strSQL += " and a.StatusID = " + LOANConstant.AssureManagementNoticeStatus.CHECK;
            	}
            }
            else if (queryPurpose == 2) //for examine
            {
            	strSelect = " select count(*) ";
            	strSQL = " from LOAN_ASSUREMANAGEMENTFORM a,Loan_ContractForm b,Client c " 
            	    	+ " where a.ContractID = b.ID and b.nBorrowClientID = c.ID(+) "
            	    	+ " and " + strUser;
            	if (statusID == LOANConstant.AssureManagementNoticeStatus.SUBMIT)
            	{
            		strSQL += " and a.StatusID = " + LOANConstant.AssureManagementNoticeStatus.SUBMIT;
            	}
            	else
            	{
            		strSQL += " and a.StatusID = " + LOANConstant.AssureManagementNoticeStatus.SUBMIT;
            	}
            }

            //////////////////////��ѯ����////////////////////////////////////////////////////
            if (typeID > 0)
            {
            	strSQL += " and a.TYPEID = " + typeID;
            }
            if (clientID > 0)
            {
            	strSQL += " and b.nBorrowClientID = " + clientID;
            }
            if (officeID > 0)
            {
            	strSQL += " and a.OfficeID = " + officeID;
            }
            if (currencyID > 0)
            {
            	strSQL += " and a.currencyID = " + currencyID;
            }
            if (startDate != null)
            {
            	strSQL += " and to_char(a.StartDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(startDate) + "'";
            }
            if (endDate != null)
            {
            	strSQL += " and to_char(a.StartDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(endDate) + "'";
            }
            if (contractCodeStart != "" && contractCodeStart.length() > 0)
            {
            	strSQL += " and b.sContractCode >= '" + contractCodeStart + "'";
            }
            if (contractCodeEnd != "" && contractCodeEnd.length() > 0)
            {
            	strSQL += " and b.sContractCode <= '" + contractCodeEnd + "'";
            }
            
            ////////////////////////////������//////////////////////////////////////////////////////////////////////
            int nIndex = 0;
            nIndex = orderParamString.indexOf(".");
            if (nIndex > 0)
            {
            	if (orderParamString.substring(0, nIndex).toLowerCase().equals("loan_assurechargeform"))
            	{
            		strSQL += " order by a." + orderParamString.substring(nIndex + 1);
            	} 
            	else if (orderParamString.substring(0, nIndex).toLowerCase().equals("loan_contractform"))
            	{
            		strSQL += " order by b." + orderParamString.substring(nIndex + 1);
            	}
            	else if (orderParamString.substring(0, nIndex).toLowerCase().equals("client"))
            	{
            		strSQL += " order by c." + orderParamString.substring(nIndex + 1);
            	}
            }
            else
            {
            	strSQL += " order by a.ID";
            }
            if (desc == Constant.PageControl.CODE_ASCORDESC_DESC)
            {
            	strSQL += " desc";
            }
            log4j.debug(strSelect + strSQL);
            try
            {
            	prepareStatement(strSelect + strSQL);
            	ResultSet rs = executeQuery();
            	if (rs != null && rs.next())
            	{
            		recordCount = rs.getLong(1);
            	}
            }
            catch (ITreasuryDAOException e)
            {
            	throw new LoanDAOException("������ѯ֪ͨ��������������", e);
            }
            catch (SQLException e)
            {
            	throw new LoanDAOException("������ѯ֪ͨ��������������", e);
            }
            recordCount = recordCount / pageLineCount;
            if ((recordCount % pageLineCount) != 0)
            {
                recordCount++;
            }
            
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //��������Ľ����
            rowNumStart = (pageNo - 1) * pageLineCount + 1;
            rowNumEnd = rowNumStart + pageLineCount - 1;
            strSelect = " select a.*,b.sContractCode,c.sName,c.id ClientID ";
            strSQL = " select * from ( select aa.*,rownum r from ( " + strSelect + strSQL;
            strSQL += " ) aa ) where r between " + rowNumStart + " and " + rowNumEnd;
            log4j.debug(strSQL);
            try
            {   
            	prepareStatement(strSQL);
            	ResultSet rs1 = executeQuery();
            	
            	while (rs1 != null && rs1.next())
            	{
            		AssureManagementNoticeInfo assureManagementNoticeInfo = new AssureManagementNoticeInfo();
					assureManagementNoticeInfo.setId(rs1.getLong("ID")); //id
					assureManagementNoticeInfo.setContractID(rs1.getLong("ContractID")); //��ͬID
            		assureManagementNoticeInfo.setOfficeID(rs1.getLong("OfficeID")); //���´�
            		assureManagementNoticeInfo.setCurrencyID(rs1.getLong("CurrencyID")); //����
            		assureManagementNoticeInfo.setCode(rs1.getString("Code")); //��������
            		assureManagementNoticeInfo.setClientID(rs1.getLong("ClientID")); //
            		assureManagementNoticeInfo.setTypeID(rs1.getLong("TypeID")); //
            		assureManagementNoticeInfo.setAssureAmount(rs1.getDouble("AssureAmount")); //
            		assureManagementNoticeInfo.setRecognizanceAccountID(rs1.getLong("RecognizanceAccountID")); //
            		assureManagementNoticeInfo.setRecognizanceAmount(rs1.getDouble("RecognizanceAmount")); //
            		assureManagementNoticeInfo.setAssureChargeAccountID(rs1.getLong("AssureChargeAccountID")); //
            		assureManagementNoticeInfo.setAssureChargeAmount(rs1.getDouble("AssureChargeAmount")); //
            		assureManagementNoticeInfo.setStartDate(rs1.getTimestamp("StartDate")); //������ʼ����
					assureManagementNoticeInfo.setEndDate(rs1.getTimestamp("EndDate")); //������������
					assureManagementNoticeInfo.setIntervalNum(rs1.getLong("IntervalNum")); //����
            		assureManagementNoticeInfo.setNextCheckUserID(rs1.getLong("NextCheckUserID")); //��һ�������
            		assureManagementNoticeInfo.setNextCheckLevel(rs1.getLong("NextCheckLevel")); //��һ����˼���
            		assureManagementNoticeInfo.setInputUserID(rs1.getLong("InputUserID")); //¼����
            		assureManagementNoticeInfo.setInputDate(rs1.getTimestamp("InputDate")); //¼��ʱ��
            		assureManagementNoticeInfo.setStatusID(rs1.getLong("StatusID")); //״̬
            		assureManagementNoticeInfo.setIsLowLevel(rs1.getLong("IsLowLevel")); //�Ƿ������˼���
					//����û�е��ֶ�
            		assureManagementNoticeInfo.setClientName(rs1.getString("sName")); //
            		assureManagementNoticeInfo.setContractCode(rs1.getString("sContractCode")); //
            		assureManagementNoticeInfo.setRecordCount(recordCount); //��¼��
            		assureManagementNoticeInfo.setPageCount(pageCount); //ҳ��
            		v.add(assureManagementNoticeInfo);
            	}
            }
            catch (ITreasuryDAOException e)
            {
            	throw new LoanDAOException("������ѯ�������������", e);
            }
            catch (SQLException e)
            {
            	throw new LoanDAOException("������ѯ�������������", e);
            }
            try
            {
            	finalizeDAO();
            }
            catch (ITreasuryDAOException e)
            {
            	throw new LoanDAOException(e);
            }
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
		{
			try
            {
                finalizeDAO();
            } catch (ITreasuryDAOException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }			
		}
		return (v.size() > 0 ? v : null);
	}
	public long check(ApprovalTracingInfo ATInfo) throws LoanDAOException
	{
	    long lMaxID = -1;
		long lSerialID = -1;
		long lStatusID = -1;
		long lResultID = -1;
		String strSQL = "";
		//������Ӧ��������
		//ģ������
		long lModuleID = ATInfo.getModuleID();
		//ҵ������
		long lLoanTypeID = ATInfo.getLoanTypeID();
		//��������
		long lActionID = ATInfo.getActionID();
		long lApprovalContentID = ATInfo.getApprovalContentID();
		long lNextUserID = ATInfo.getNextUserID();
		//long lNextLevel = ATInfo.getNextLevel();
		long lApprovalID = ATInfo.getApprovalID();
		long lUserID = ATInfo.getInputUserID();
		
		AssureManagementNoticeInfo aInfo = new AssureManagementNoticeInfo();
		AssureManagementNoticeInfo tempInfo = new AssureManagementNoticeInfo();
		try
        {
            tempInfo = (AssureManagementNoticeInfo) findByID(lApprovalContentID,tempInfo.getClass());
        } 
		catch (ITreasuryDAOException e)
		{
			throw new LoanDAOException(e);
		}
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if (ATInfo.getCheckActionID() == LOANConstant.Actions.REJECT) //�ܾ�
		{
			aInfo.setId(lApprovalContentID);
			aInfo.setStatusID(LOANConstant.AssureManagementNoticeStatus.REFUSE);
			try
			{
				update(aInfo);
			}
			catch (ITreasuryDAOException e)
			{
				throw new LoanDAOException(e);
			}
		}
		if (ATInfo.getCheckActionID() == LOANConstant.Actions.CHECK) //����
		{
			aInfo.setId(lApprovalContentID);
			aInfo.setStatusID(LOANConstant.AssureManagementNoticeStatus.SUBMIT);
			aInfo.setNextCheckUserID(lNextUserID);
			
			ApprovalDelegation appbiz = new ApprovalDelegation();
			try 
			{
				
				aInfo.setNextCheckLevel(appbiz.findApprovalUserLevel(lApprovalID,lNextUserID));
				
			} 
			catch (Exception e) {
			}
			//aInfo.setNextCheckLevel(tempInfo.getNextCheckLevel() + 1);
			try
			{
				update(aInfo);
			}
			catch (ITreasuryDAOException e)
			{
				throw new LoanDAOException(e);
			}
		}
		if (ATInfo.getCheckActionID() == LOANConstant.Actions.CHECKOVER) //����&&���
		{
			aInfo.setId(lApprovalContentID);
			aInfo.setStatusID(LOANConstant.AssureManagementNoticeStatus.CHECK);
			aInfo.setNextCheckUserID(lNextUserID);
			try
			{
				update(aInfo);
			}
			catch (ITreasuryDAOException e)
			{
				throw new LoanDAOException(e);
			}
			//������ɺ���Ҫ���Ĳ���
			doAfterCheckOver(lApprovalContentID);
		}
		if (ATInfo.getCheckActionID() == LOANConstant.Actions.RETURN) //�޸�
		{
			aInfo.setId(lApprovalContentID);
			aInfo.setStatusID(LOANConstant.AssureManagementNoticeStatus.SUBMIT);
			aInfo.setNextCheckUserID(ATInfo.getInputUserID());
			//����һ�����Ϊ1
			aInfo.setNextCheckLevel(1);
			try
			{
				update(aInfo);
			}
			catch (ITreasuryDAOException e)
			{
				throw new LoanDAOException(e);
			}
		}
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		log4j.debug("check end");

		return lApprovalContentID;
	}
	/*
	 *  (non-Javadoc)
	 * 
	 */
	public String getApplyCode(long lContractID) throws LoanDAOException
	{
	    String strSQL = "";
		String strCode = "000";
		long lCode = 0;
		Timestamp tsToday = Env.getSystemDateTime();
		String strYear = DataFormat.getDateString(tsToday).substring(2, 4);
		try
        {
            try
            {
            	initDAO();
            }
            catch (ITreasuryDAOException e)
            {
            	throw new LoanDAOException(e);
            }
            strSQL = " select max(nvl(Code,0)) Code from LOAN_ASSUREMANAGEMENTFORM where ContractID = " + lContractID;
            log4j.debug(strSQL);
            try
            {
            	prepareStatement(strSQL);
            	ResultSet rs = executeQuery();
            	if (rs != null && rs.next())
            	{
            		strCode = rs.getString(1);
            		log4j.debug(strCode);
            		if (strCode != null && strCode.length() > 0)
            		{
            			lCode = Long.parseLong(strCode) + 1;
            		}
            		else
            		{
            			lCode = 1;
            		}
            		strCode = DataFormat.formatInt(lCode, 3, true);
            	}
            }
            catch (ITreasuryDAOException e)
            {
            	throw new LoanDAOException("����֪ͨ����Ų�������", e);
            }
            catch (SQLException e)
            {
            	throw new LoanDAOException("����֪ͨ����Ų�������", e);
            }
            try
            {
            	finalizeDAO();
            }
            catch (ITreasuryDAOException e)
            {
            	throw new LoanDAOException(e);
            }
        } 
		catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		finally
		{
			try
            {
                finalizeDAO();
            } catch (ITreasuryDAOException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }			
		}
		log4j.debug(":::::::::: ::::strcode::::::" + strCode);
		return strCode;
	}
	/*
	 *  (non-Javadoc)
	 * 
	 */
	private void doAfterCheckOver(long lApplyID) throws LoanDAOException
	{
		/*
		AssureManagementNoticeInfo aInfo = new AssureManagementNoticeInfo();
		try
		{
			aInfo = (AssureManagementNoticeInfo) findByID(lApplyID, aInfo.getClass());
			if (aInfo != null)
			{
				if (aInfo.getTransactionTypeId() == LOANConstant.BusinessType.CAPITAL_REPURCHASE.CAPITAL_REPURCHASE
					|| aInfo.getTransactionTypeId() == LOANConstant.BusinessType.ENTRUST_FINANCING.ENTRUST_FINANCING
					|| aInfo.getTransactionTypeId() == LOANConstant.BusinessType.ENTRUSTED_FINANCING.ENTRUSTED_FINANCING
					|| aInfo.getTransactionTypeId() == LOANConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.FOREIGN_CURRENCY_INVESTMENT
					|| aInfo.getTransactionTypeId() == LOANConstant.BusinessType.BOND_UNDERWRITING.BOND_UNDERWRITING)
				{
					long contractID = doCreateContract(aInfo);
					if (aInfo.getTransactionTypeId() == LOANConstant.BusinessType.BOND_UNDERWRITING.BOND_UNDERWRITING)
						doCopyBondType(lApplyID, contractID);
				}
				else
				{
					aInfo = doFinishApply(aInfo);
				}
			}
		}
		catch (ITreasuryDAOException e)
		{
			throw new LoanDAOException(e);
		}
		*/
	}
	
	public void doAfterCancel(long lApplyID) throws LoanDAOException
	{
		String strSQL = "";
		long lStatusID = -1;
		long lContractID = -1;
		try
		{
			try
			{
				initDAO();
			}
			catch (ITreasuryDAOException e)
			{
				throw new LoanDAOException(e);
			}
			strSQL = " select b.nstatusid contractStatusID,b.id contractID from LOAN_ASSUREMANAGEMENTFORM a,loan_contractform b "
				+ " where a.contractid = b.id and a.id = " + lApplyID;
			log4j.debug(strSQL);
			try
			{
				prepareStatement(strSQL);
				ResultSet rs = executeQuery();
				if (rs != null && rs.next())
				{
					lStatusID = rs.getLong(1);
					lContractID = rs.getLong(2);
				}
				if( lStatusID == LOANConstant.ContractStatus.FINISH && lContractID>0 )
				{
					strSQL = "UPDATE loan_contractform SET nStatusID = " + LOANConstant.ContractStatus.ACTIVE
						+ " where id = " + lContractID;
					log4j.debug(strSQL);
					prepareStatement(strSQL);
					executeUpdate();
				}
			}
			catch (ITreasuryDAOException e)
			{
				throw new LoanDAOException("ȡ��֪ͨ�������ĺ�ͬ״̬����������", e);
			}
			catch (SQLException e)
			{
				throw new LoanDAOException("ȡ��֪ͨ�������ĺ�ͬ״̬����������", e);
			}
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				throw new LoanDAOException(e);
			}
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try
			{
				finalizeDAO();
			} catch (ITreasuryDAOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
		}
	}	
	
	public static void main(String[] args)
	{
		/* 
	    SEC_ApplyDAO dao = new SEC_ApplyDAO();
		AssureManagementNoticeInfo info = new AssureManagementNoticeInfo();

		info.setId(0);
		try {
		    info.setCode(dao.getApplyCode(1));
		} catch (LoanDAOException e) {
		    e.printStackTrace();
		}
		Timestamp tsDate = Env.getSystemDateTime();
		info.setTransactionTypeId(1101);
		info.setInputDate(tsDate);
		info.setInputUserId(1);
		info.setStatusId(1);
		try {
		    dao.add(info);
		} catch (LoanDAOException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		}
		
		
		info.setId(42);
		try {
		    info.setCode(dao.getApplyCode(1));
		} catch (LoanDAOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		Timestamp tsDate = Env.getSystemDateTime();
		info.setTransactionTypeId(1101);
		info.setInputDate(tsDate);
		info.setInputUserId(2);
		info.setStatusId(2);
		try {
		    dao.update(info);
		} catch (LoanDAOException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		}
		
		
		
		try {
		    info = (AssureManagementNoticeInfo)dao.findByID(1,info.getClass());
		} catch (LoanDAOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		Log.print(info.toString());
		*/
		AssureManagementNoticeDao dao = new AssureManagementNoticeDao();
		AssureManagementQueryInfo qInfo = new AssureManagementQueryInfo();
		Collection c = null;
		qInfo.setQueryPurpose(1);
		qInfo.setUserID(23);
		qInfo.setOfficeID(1);
		qInfo.setStatusID(2);
		//qInfo.setTypeID(2);
		qInfo.setPageLineCount(100);
		qInfo.setPageNo(1);
		try
		{
			c = dao.findByMultiOption(qInfo);
			Iterator it = null;
			if (c!= null)
			{
				it = c.iterator();
				while (it.hasNext())
				{
					AssureManagementNoticeInfo aInfo = (AssureManagementNoticeInfo) it.next();
					Log.print(aInfo.toString());
				}
			}
		}
		catch (LoanDAOException e)
		{
			e.printStackTrace();
		}
		//*/
	}
	//	added by qhzhou 2007.6.27
	public AssureManagementNoticeInfo findByID(long nID) throws RemoteException,
	IRollbackException,IException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		String strSelect = null;
		String strSQL = null;
		AssureManagementNoticeInfo info=new AssureManagementNoticeInfo();
		try
		{
			con = Database.getConnection();
			strSelect = " select *";
			strSQL = " from loan_assuremanagementform where id= "+nID;
			ps = con.prepareStatement(strSelect + strSQL);
			rs = ps.executeQuery();

			if (rs != null && rs.next())
			{
				info.setId(rs.getLong(1));
				info.setCurrencyID(rs.getLong(2));
				info.setOfficeID(rs.getLong(3));
				info.setContractID(rs.getLong(4));
				info.setTypeID(rs.getLong(5));
				info.setExecuteDate(rs.getTimestamp(6));
				info.setCode(rs.getString(7));
				info.setAssureAmount(rs.getDouble(8));
				info.setAssureChargeAccountID(rs.getLong(9));
				info.setAssureChargeAmount(rs.getDouble(10));
				info.setRecognizanceAccountID(rs.getLong(11));
				info.setRecognizanceAmount(rs.getDouble(12));
				info.setStartDate(rs.getTimestamp(13));
				info.setEndDate(rs.getTimestamp(14));
				info.setIntervalNum(rs.getLong(15));
				info.setReceiveAccount(rs.getString(16));
				info.setReceiveClientName(rs.getString(17));
				info.setRemitInProvince(rs.getString(18));
				info.setRemitInCity(rs.getString(19));
				info.setRemitInBank(rs.getString(20));
				info.setCurrentAccountID(rs.getLong(21));
				info.setCurrentAccountBalance(rs.getDouble(22));
				info.setAmendsAmount(rs.getDouble(23));
				info.setInputUserID(rs.getLong(24));
				info.setInputDate(rs.getTimestamp(25));
				info.setNextCheckUserID(rs.getLong(26));
				info.setNextCheckLevel(rs.getLong(27));
				info.setIsLowLevel(rs.getLong(28));
				info.setStatusID(rs.getLong(29));
				
			}
		}
		catch (Exception ex)
		{
			log4j.error(ex.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception ex)
			{
				log4j.error(ex.toString());
				throw new IException("Gen_E001");
			}
		}
		
		return info;
		
	}
	public long updateAssureManagementNoticeStatus(long lId,long statusId)throws IException {
		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;

		try {
			conn = Database.getConnection();
			strSQL = " update loan_assuremanagementform  set STATUSID = ? where ID = ? ";

			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, statusId);
			ps.setLong(2, lId);

			lResult = ps.executeUpdate();

			ps.close();
			ps = null;
			conn.close();
			conn = null;

			if (lResult < 0) {
				Log.print(" update loan updateAssureManagementNoticeStatus error : "
						+ lResult);
				return -1;
			} else {
				return lId;
			}
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		} finally {
			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception ex) {
				log4j.error(ex.toString());
				throw new IException("Gen_E001");
			}
		}
	}
	
	/**
	 * Modify by leiyang date 2007/07/27
	 * 
	 * @param lId
	 * @param statusId
	 * @return
	 * @throws IException
	 */
	public long updateStatusAndCheckStatus(long lId,long statusId)throws IException {
		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;

		try {
			conn = Database.getConnection();
			strSQL = "update loan_assuremanagementform  set STATUSID = ? where ID = ? and STATUSID = ?";

			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, statusId);
			ps.setLong(2, lId);
			ps.setLong(3, AssureManagementNoticeStatus.CHECK);

			lResult = ps.executeUpdate();

			ps.close();
			ps = null;
			conn.close();
			conn = null;

		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		} finally {
			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception ex) {
				log4j.error(ex.toString());
				throw new IException("Gen_E001");
			}
		}
		return lResult;
	}
	
	
}
