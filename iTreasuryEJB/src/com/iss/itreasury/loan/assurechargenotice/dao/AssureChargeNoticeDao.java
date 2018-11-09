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
package com.iss.itreasury.loan.assurechargenotice.dao;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.assurechargenotice.dataentity.AssureChargeNoticeInfo;
import com.iss.itreasury.loan.assurechargenotice.dataentity.AssureChargeQueryInfo;
import com.iss.itreasury.loan.base.LoanDAO;
import com.iss.itreasury.loan.base.LoanDAOException;
import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.discount.dataentity.DiscountCredenceInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
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
public class AssureChargeNoticeDao extends LoanDAO
{
	protected Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
	public AssureChargeNoticeDao()
	{
		super("LOAN_ASSURECHARGEFORM");
	}
	public AssureChargeNoticeDao(String sequence)
	{
		super("LOAN_ASSURECHARGEFORM", sequence);
	}
	/*
	 * 
	 */
	public Collection findByMultiOption(AssureChargeQueryInfo qInfo) throws LoanDAOException
	{
		String strSelect = "";
		String strSQL = "";
		String strSQL1 = "";
		Vector v = new Vector();

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
            //计算记录总数				
            if (queryPurpose == 1) //for modify
            {
            	strSQL = "";
            	strSelect = " select count(*) ";
            	strSQL = " from LOAN_ASSURECHARGEFORM a,Loan_ContractForm b,Client c "
            			+ " where a.ContractID = b.ID and b.nBorrowClientID = c.ID(+) "
            			+ " and a.StatusID >= "
            			+ LOANConstant.AssureChargeNoticeStatus.SUBMIT
            			+ " and a.StatusID <= "
            			+ LOANConstant.AssureChargeNoticeStatus.CHECK
            			+ " and a.InputUserID = "
            			+ userID;
            	if (statusID == LOANConstant.AssureChargeNoticeStatus.SUBMIT)
            	{
            		strSQL += " and a.nextCheckLevel = 1 ";
            		strSQL += " and a.StatusID = " + LOANConstant.AssureChargeNoticeStatus.SUBMIT;
            	}
            	else
            	{
            		strSQL += " and a.StatusID = " + LOANConstant.AssureChargeNoticeStatus.CHECK;
            	}
            }
            else if (queryPurpose == 2) //for examine
            {
            	strSelect = " select count(*) ";
            	strSQL = " from LOAN_ASSURECHARGEFORM a,Loan_ContractForm b,Client c " 
            	    	+ " where a.ContractID = b.ID and b.nBorrowClientID = c.ID(+) "
            	    	+ " and " + strUser;
            	if (statusID == LOANConstant.AssureChargeNoticeStatus.SUBMIT)
            	{
            		strSQL += " and a.StatusID = " + LOANConstant.AssureChargeNoticeStatus.SUBMIT;
            	}
            	else
            	{
            		strSQL += " and a.StatusID = " + LOANConstant.AssureChargeNoticeStatus.SUBMIT;
            	}
            }

            //////////////////////查询条件////////////////////////////////////////////////////
            if (clientID != -1)
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
            
            ////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
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
            	throw new LoanDAOException("批量查询通知单笔数产生错误", e);
            }
            catch (SQLException e)
            {
            	throw new LoanDAOException("批量查询通知单笔数产生错误", e);
            }
            recordCount = recordCount / pageLineCount;
            if ((recordCount % pageLineCount) != 0)
            {
                recordCount++;
            }
            
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //返回需求的结果集
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
            		AssureChargeNoticeInfo assureChargeNoticeInfo = new AssureChargeNoticeInfo();
					assureChargeNoticeInfo.setId(rs1.getLong("ID")); //id
					assureChargeNoticeInfo.setContractID(rs1.getLong("CONTRACTID")); //CONTRACTID
            		assureChargeNoticeInfo.setOfficeID(rs1.getLong("OfficeID")); //办事处
            		assureChargeNoticeInfo.setCurrencyID(rs1.getLong("CurrencyID")); //币种
            		assureChargeNoticeInfo.setCode(rs1.getString("Code")); //申请书编号
            		assureChargeNoticeInfo.setClientID(rs1.getLong("ClientID")); //
            		assureChargeNoticeInfo.setAssureAmount(rs1.getDouble("AssureAmount"));	//承保金额
            		assureChargeNoticeInfo.setRecognizanceAccountID(rs1.getLong("RecognizanceAccountID"));	//保证金账户
            		assureChargeNoticeInfo.setRecognizanceAmount(rs1.getDouble("RecognizanceAmount")); //
            		assureChargeNoticeInfo.setAssureChargeAccountID(rs1.getLong("AssureChargeAccountID"));	//担保手续费账户
            		assureChargeNoticeInfo.setAssureChargeAmount(rs1.getDouble("AssureChargeAmount")); //
            		assureChargeNoticeInfo.setStartDate(rs1.getTimestamp("StartDate")); //担保开始日期
					assureChargeNoticeInfo.setEndDate(rs1.getTimestamp("EndDate"));//担保结束日期
            		assureChargeNoticeInfo.setIntervalNum(rs1.getLong("IntervalNum")); //期限
            		assureChargeNoticeInfo.setNextCheckUserID(rs1.getLong("NextCheckUserID")); //下一级审核人
            		assureChargeNoticeInfo.setNextCheckLevel(rs1.getLong("NextCheckLevel")); //下一级审核级别
            		assureChargeNoticeInfo.setInputUserID(rs1.getLong("InputUserID")); //录入人
            		assureChargeNoticeInfo.setInputDate(rs1.getTimestamp("InputDate")); //录入时间
            		assureChargeNoticeInfo.setStatusID(rs1.getLong("StatusID")); //状态
            		assureChargeNoticeInfo.setIsLowLevel(rs1.getLong("IsLowLevel")); //是否最低审核级别
					//表中没有的字段
            		assureChargeNoticeInfo.setClientName(rs1.getString("sName")); //
            		assureChargeNoticeInfo.setContractCode(rs1.getString("sContractCode")); //            		
            		assureChargeNoticeInfo.setRecordCount(recordCount); //记录数
            		assureChargeNoticeInfo.setPageCount(pageCount); //页数
					//新加的字段2006－3－20
					assureChargeNoticeInfo.setIsinterest(rs1.getLong("IsinteRest"));
					assureChargeNoticeInfo.setRate(rs1.getDouble("Rate"));
					assureChargeNoticeInfo.setRecrecognizanceAccountID(rs1.getLong("RecrecognizanceAccountid"));
            		v.add(assureChargeNoticeInfo);
            	}
            }
            catch (ITreasuryDAOException e)
            {
            	throw new LoanDAOException("批量查询申请书产生错误", e);
            }
            catch (SQLException e)
            {
            	throw new LoanDAOException("批量查询申请书产生错误", e);
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
		//定义相应操作常量
		//模块类型
		long lModuleID = ATInfo.getModuleID();
		//业务类型
		long lLoanTypeID = ATInfo.getLoanTypeID();
		//操作类型
		long lActionID = ATInfo.getActionID();
		long lApprovalContentID = ATInfo.getApprovalContentID();
		long lNextUserID = ATInfo.getNextUserID();
		//long lNextLevel = ATInfo.getNextLevel();
		long lApprovalID = ATInfo.getApprovalID();
		long lUserID = ATInfo.getInputUserID();
		
		AssureChargeNoticeInfo aInfo = new AssureChargeNoticeInfo();
		AssureChargeNoticeInfo tempInfo = new AssureChargeNoticeInfo();
		try
        {
            tempInfo = (AssureChargeNoticeInfo) findByID(lApprovalContentID,tempInfo.getClass());
        } 
		catch (ITreasuryDAOException e)
		{
			throw new LoanDAOException(e);
		}
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if (ATInfo.getCheckActionID() == LOANConstant.Actions.REJECT) //拒绝
		{
			aInfo.setId(lApprovalContentID);
			aInfo.setStatusID(LOANConstant.AssureChargeNoticeStatus.REFUSE);
			try
			{
				update(aInfo);
			}
			catch (ITreasuryDAOException e)
			{
				throw new LoanDAOException(e);
			}
		}
		if (ATInfo.getCheckActionID() == LOANConstant.Actions.CHECK) //审批
		{
			aInfo.setId(lApprovalContentID);
			aInfo.setStatusID(LOANConstant.AssureChargeNoticeStatus.SUBMIT);
			aInfo.setNextCheckUserID(lNextUserID);
			//aInfo.setNextCheckLevel(lNextLevel+1);
			//aInfo.setNextCheckLevel(tempInfo.getNextCheckLevel() + 1);
			
			ApprovalDelegation appbiz = new ApprovalDelegation();
			try 
			{
				
				aInfo.setNextCheckLevel(appbiz.findApprovalUserLevel(lApprovalID,lNextUserID));
				
			} 
			catch (Exception e) {
			}
			try
			{
				update(aInfo);
			}
			catch (ITreasuryDAOException e)
			{
				throw new LoanDAOException(e);
			}
		}
		if (ATInfo.getCheckActionID() == LOANConstant.Actions.CHECKOVER) //审批&&最后
		{
			aInfo.setId(lApprovalContentID);
			aInfo.setStatusID(LOANConstant.AssureChargeNoticeStatus.CHECK);
			aInfo.setNextCheckUserID(lNextUserID);
			try
			{
				update(aInfo);
			}
			catch (ITreasuryDAOException e)
			{
				throw new LoanDAOException(e);
			}
			//审批完成后需要做的操作
			doAfterCheckOver(lApprovalContentID);
		}
		if (ATInfo.getCheckActionID() == LOANConstant.Actions.RETURN) //修改
		{
			aInfo.setId(lApprovalContentID);
			aInfo.setStatusID(LOANConstant.AssureChargeNoticeStatus.SUBMIT);
			aInfo.setNextCheckUserID(ATInfo.getInputUserID());
			//置下一级审核为1
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
            strSQL = " select max(nvl(Code,0)) Code from " + strTableName + " where ContractID = " + lContractID;
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
            	throw new LoanDAOException("生成通知单编号产生错误", e);
            }
            catch (SQLException e)
            {
            	throw new LoanDAOException("生成通知单编号产生错误", e);
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
		AssureChargeNoticeInfo aInfo = new AssureChargeNoticeInfo();
		try
		{
			aInfo = (AssureChargeNoticeInfo) findByID(lApplyID, aInfo.getClass());
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
			strSQL = " select b.nstatusid contractStatusID,b.id contractID from LOAN_ASSURECHARGEFORM a,loan_contractform b "
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
				throw new LoanDAOException("取消通知单（更改合同状态）产生错误", e);
			}
			catch (SQLException e)
			{
				throw new LoanDAOException("取消通知单（更改合同状态）产生错误", e);
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
		AssureChargeNoticeInfo info = new AssureChargeNoticeInfo();

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
		    info = (AssureChargeNoticeInfo)dao.findByID(1,info.getClass());
		} catch (LoanDAOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		Log.print(info.toString());
		
		AssureChargeQueryInfo qInfo = new AssureChargeQueryInfo();
		Collection c = null;
		qInfo.setQueryPurpose(2);
		qInfo.setUserId(1);
		qInfo.setTransactionTypeId(1301);
		qInfo.setPageLineCount(100);
		qInfo.setPageNo(1);
		try
		{
			c = dao.findByMultiOption(qInfo);
			Iterator it = c.iterator();
			while (it.hasNext())
			{
				AssureChargeNoticeInfo aInfo = (AssureChargeNoticeInfo) it.next();
				Log.print(aInfo.toString());
			}
		}
		catch (LoanDAOException e)
		{
			e.printStackTrace();
		}
		*/
	}
	
	//	added by qhzhou 2007.6.27
	public AssureChargeNoticeInfo findByID(long nID) throws RemoteException,
	IRollbackException,IException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		String strSelect = null;
		String strSQL = null;
		AssureChargeNoticeInfo info=new AssureChargeNoticeInfo();
		try
		{
			con = Database.getConnection();
			strSelect = " select *";
			strSQL = " from loan_assurechargeform where id= "+nID;
			ps = con.prepareStatement(strSelect + strSQL);
			rs = ps.executeQuery();

			if (rs != null && rs.next())
			{
				info.setId(rs.getLong(1));
				info.setCurrencyID(rs.getLong(2));
				info.setOfficeID(rs.getLong(3));
				info.setContractID(rs.getLong(4));
				info.setExecuteDate(rs.getTimestamp(5));
				info.setCode(rs.getString(6));
				info.setRecognizanceAccountID(rs.getLong(7));
				info.setRecognizanceAmount(rs.getDouble(8));
				info.setAssureChargeAccountID(rs.getLong(9));
				info.setAssureChargeAmount(rs.getDouble(10));
				info.setAssureAmount(rs.getDouble(11));
				info.setStartDate(rs.getTimestamp(12));
				info.setEndDate(rs.getTimestamp(13));
				info.setIntervalNum(rs.getLong(14));
				info.setInputUserID(rs.getLong(15));
				info.setInputDate(rs.getTimestamp(16));
				info.setNextCheckUserID(rs.getLong(17));
				info.setNextCheckLevel(rs.getLong(18));
				info.setIsLowLevel(rs.getLong(19));
				info.setStatusID(rs.getLong(20));
				info.setRecrecognizanceAccountID(rs.getLong(21));
				info.setIsinterest(rs.getLong(22));
				info.setRate(rs.getDouble(23));	
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
	public long updateAssureChargeNoticeStatus(long lId,long statusId)throws IException {
		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;

		try {
			conn = Database.getConnection();
			strSQL = " update loan_assurechargeform  set STATUSID = ? where ID = ? ";

			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, statusId);
			ps.setLong(2, lId);

			lResult = ps.executeUpdate();

			ps.close();
			ps = null;
			conn.close();
			conn = null;

			if (lResult < 0) {
				Log.print(" update loan updateDiscountBillStatus error : "
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
		long lResult = -1;

		try {
			conn = Database.getConnection();
			String strSQL = "update loan_assurechargeform  set STATUSID = ? where ID = ? and STATUSID = ?";

			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, statusId);
			ps.setLong(2, lId);
			ps.setLong(3, LOANConstant.AssureChargeNoticeStatus.CHECK);

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
	
	/**
	 * Boxu Add 2008年3月12日 未收款金额(合同未做收款通知单金额)
	 * @param  statusId
	 * @return double
	 * @throws IException
	 */
	public double findUnPayAmountByID(long lContractID,long payID)throws IException 
	{
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		ContractInfo contractInfo = new ContractInfo();
		double unPayAmount = 0.0;
		double payAmount = 0.0;

		try 
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			
			if (lContractID > 0) 
			{
				sbSQL.append(" select sum(la.ASSUREAMOUNT) payAmount ");
				sbSQL.append(" from LOAN_ASSURECHARGEFORM la ");
				sbSQL.append(" where la.contractid = ? and la.statusid != ? and la.statusid != ? and la.statusid != 0 ");
				if(payID>0)
				{
					sbSQL.append(" and  la.ID<>? ");
				}
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lContractID);
				ps.setLong(2, LOANConstant.AssureChargeNoticeStatus.CANCEL);
				ps.setLong(3, LOANConstant.AssureChargeNoticeStatus.REFUSE);
				if(payID>0)
				{
					ps.setLong(4, payID);
				}
				rs = ps.executeQuery();

				if (rs.next()) 
				{
					payAmount = rs.getDouble("payAmount");
				}
				
				ps.close();
				ps = null;
				
				ContractDao ContractDao = new ContractDao();
				contractInfo = ContractDao.findByID(lContractID);
				
				//合同金额减去已生成收款通知单金额
				unPayAmount = UtilOperation.Arith.round(UtilOperation.Arith.sub(contractInfo.getExamineAmount(), payAmount), 2);
			}
		} 
		catch (Exception e) 
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		} 
		finally 
		{
			try 
			{
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
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
		
		return unPayAmount;
	}
	
	 /**
	  * 根据合同编号查找收保证金的账户ID
	  * @param strContractCode
	  * @return
	  * @throws RemoteException
	  * @throws IRollbackException
	  * @throws IException
	  */
	 
	public long findRecrecognizanceAccountID(String strContractCode) throws RemoteException,IRollbackException,IException {
		PreparedStatement ps = null;
		ResultSet     rs     = null;
		Connection    con    = null;
		String        strSQL = null;
		long          result = -1;
		try
		{
			con = Database.getConnection();
			strSQL = " select distinct(b.recrecognizanceaccountid) from loan_assurechargeform b,loan_contractform c " +
			                    " where b.contractid = c.id and c.scontractcode = '"+strContractCode+"'";
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				result = rs.getLong(1);
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
		
		return result;
	}
}
