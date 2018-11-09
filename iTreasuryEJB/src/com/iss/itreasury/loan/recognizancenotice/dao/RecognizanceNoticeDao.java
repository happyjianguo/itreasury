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
package com.iss.itreasury.loan.recognizancenotice.dao;

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
import com.iss.itreasury.loan.recognizancenotice.dataentity.RecognizanceNoticeInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.loan.util.LOANConstant.RecognizanceNoticeStatus;
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
public class RecognizanceNoticeDao extends LoanDAO
{
	protected Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
	public RecognizanceNoticeDao()
	{
		super("LOAN_RECOGNIZANCENOTICEFORM");
	}
	/**
	 * 获得保证金保后处理通知单的单据号
	 * @param lContractID
	 * @return
	 * @throws LoanDAOException
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
            strSQL = " select max(nvl(Code,0)) Code from LOAN_RECOGNIZANCENOTICEFORM where ContractID = " + lContractID;
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
            	throw new LoanDAOException("生成保证金保后处理通知单编号产生错误", e);
            }
            catch (SQLException e)
            {
            	throw new LoanDAOException("生成保证金保后处理通知单编号产生错误", e);
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
	
	public long updateRecognizanceNoticeStatus(long lId,long statusId)throws IException {
		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;

		try {
			conn = Database.getConnection();
			strSQL = " update LOAN_RECOGNIZANCENOTICEFORM  set STATUSID = ? where ID = ? ";

			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, statusId);
			ps.setLong(2, lId);

			lResult = ps.executeUpdate();

			ps.close();
			ps = null;
			conn.close();
			conn = null;

			if (lResult < 0) {
				Log.print(" update loan updateRecognizanceNoticeStatus error : "
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
			strSQL = " select b.nstatusid contractStatusID,b.id contractID from LOAN_RECOGNIZANCENOTICEFORM a,loan_contractform b "
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
	
	public RecognizanceNoticeInfo findByID(long nID) throws RemoteException,IRollbackException,IException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		String strSelect = null;
		String strSQL = null;
		RecognizanceNoticeInfo info=new RecognizanceNoticeInfo();
		try
		{
			con = Database.getConnection();
			strSelect = " select *";
			strSQL = " from LOAN_RECOGNIZANCENOTICEFORM where id= "+nID;
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
				info.setRecognizanceAccountID(rs.getLong(8));
				info.setRecognizanceAmount(rs.getDouble(9));
				info.setRZZLbalance(rs.getDouble(10));
				info.setSumRecognizance(rs.getDouble(11));
				info.setInputUserID(rs.getLong(12));
				info.setInputDate(rs.getTimestamp(13));
				info.setNextCheckUserID(rs.getLong(14));
				info.setNextCheckLevel(rs.getLong(15));
				info.setIsLowLevel(rs.getLong(16));
				info.setStatusID(rs.getLong(17));
				info.setLSubTypeID(rs.getLong(18));
				info.setLTypeID(rs.getLong(19));
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
	
	public long updateStatusAndCheckStatus(long lId,long statusId)throws IException {
		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;

		try {
			conn = Database.getConnection();
			strSQL = "update LOAN_RECOGNIZANCENOTICEFORM  set STATUSID = ? where ID = ? and STATUSID = ?";

			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, statusId);
			ps.setLong(2, lId);
			ps.setLong(3, RecognizanceNoticeStatus.CHECK);

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
