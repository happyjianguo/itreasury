package com.iss.itreasury.ebank.obquery.dao;

import java.sql.*;
import java.util.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.*;
import com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao;
import com.iss.itreasury.ebank.obquery.dataentity.*;
import com.iss.itreasury.ebank.util.*;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.loan.loanpaynotice.dao.LoanPayNoticeDao;
//import com.iss.itreasury.util.DataFormat;

public class OBCapitalTransDao
{
	private static Log4j log4j = null;

	public OBCapitalTransDao()
	{
		log4j = new Log4j(Constant.ModuleType.EBANK, this);
	}

	/**
	 * 根据查询条件表单类，匹配出符合查询条件的资金划拨指令信息
	 * Create Date: 2003-8-13
	 * @param OBQueryInfo 查询条件表单类
	 * @return Collection  所有符合条件交易指令信息
	 * @exception Exception
	 */
	public Collection queryMatchTrans(OBQueryInfo qi) throws Exception
	{
		FinanceInfo info = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector vReturn = new Vector();
		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT fin.*, \n");
			sbSQL.append(" cfmUser.sname confirmUserName, checkUser.sname checkUserName, \n");
			sbSQL.append(" signUser.sname signUserName,delUser.sname delUserName, \n");
			sbSQL.append(" office.sname officename,cpfUser.sname DealUserName \n");
			sbSQL.append(" FROM OB_FinanceInstr fin, OB_User cfmUser, \n");
			sbSQL.append(" OB_User checkUser , OB_User signUser, \n");
			sbSQL.append(" OB_User delUser,office ,userinfo cpfUser \n");
			sbSQL.append(" WHERE fin.nStatus = " + qi.getStatus() + "\n");
			sbSQL.append(" AND fin.nconfirmuserid=cfmUser.id(+) \n");
			sbSQL.append(" AND fin.nsignuserid=signuser.id(+) \n");
			sbSQL.append(" AND fin.nDeleteuserid=delUser.id(+) \n");
			sbSQL.append(" AND fin.nCheckUserID=checkUser.id(+) \n");
			sbSQL.append(" AND fin.CPF_nOfficeid=office.id(+) \n");
			sbSQL.append(" AND fin.CPF_nDealUserId=cpfUser.id(+) \n");
			sbSQL.append(" and fin.sbatchno is null \n");
			
			if (qi.getCurrencyID() >0)
				sbSQL.append(" AND fin.nCurrencyID=" + qi.getCurrencyID() + " \n");
			sbSQL.append(" AND fin.nClientID=" + qi.getClientID() + " \n");

			//交易指令类型
			if (qi.getTransType() > 0)
			{
				sbSQL.append(
					" AND fin.ntranstype in( "
						+ OBConstant.SettInstrType.CAPTRANSFER_BANKPAY
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_OTHER
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_SELF
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_FINCOMPANYPAY
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_PAYSUBACCOUNT
						+ ","
						+ OBConstant.SettInstrType.BANKPAY_DOWNTRANSFER
						+ " ) ");
			}
			//业务复核匹配，查询未复核的记录：非登录人录入的本单位交易指令
			if (qi.getOperationTypeID() == OBConstant.QueryOperationType.CHECK)
			{
				sbSQL.append(" AND fin.nstatus = " + qi.getStatus());
				sbSQL.append(" AND fin.nConfirmUserID !=" + qi.getUserID());
				//匹配条件	
				sbSQL.append(" AND fin.NPAYERACCTID = " + qi.getPayerAcctID());
				if(qi.getRemitType()>0){
					sbSQL.append(" AND fin.NREMITTYPE = " + qi.getRemitType());					
				}
				if(qi.getPayeeAcctID()>0){
					sbSQL.append(" AND fin.NPAYEEACCTID = " + qi.getPayeeAcctID());					
				}								
				sbSQL.append(" AND fin.MAMOUNT = " + qi.getAmount());
				//sbSQL.append(" AND fin.DTEXECUTE = " + Timestamp.valueOf(qi.getExecuteDate().substring(0, 10)));
				if (qi.getExecuteDate() != null && qi.getExecuteDate().trim().length() > 0)
				{
					sbSQL.append(" AND fin.DTEXECUTE >= ? \n");
					sbSQL.append(" AND fin.DTEXECUTE <= ? \n");
				}
			}

			sbSQL.append("\n ORDER BY fin.dtconfirm ASC ,fin.NPAYERACCTID ");
			log4j.info("OBCapitalTransDao.queryMatchTrans():SQL==" + sbSQL.toString());
			System.out.println("sql="+sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			int nIndex = 1;
			if (qi.getExecuteDate() != null && qi.getExecuteDate().trim().length() > 0)
			{
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qi.getExecuteDate().trim() + " 00:00:00"));
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qi.getExecuteDate().trim() + " 23:59:59"));
			}


			rs = ps.executeQuery();
			vReturn = setInfo(rs);

			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			log4j.info("query result : " + vReturn.size());
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
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return vReturn.size() > 0 ? vReturn : null;
	}

	/**
	 * 根据查询条件表单类，匹配出符合查询条件的资金申领指令信息
	 * Create Date: 2003-8-13
	 * @param OBQueryInfo 查询条件表单类
	 * @return Collection  所有符合条件交易指令信息
	 * @exception Exception
	 */
	public Collection queryMatchApplyCapital(OBQueryInfo qi) throws Exception
	{
		FinanceInfo info = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector vReturn = new Vector();
		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT fin.*, \n");
			sbSQL.append(" cfmUser.sname confirmUserName, checkUser.sname checkUserName, \n");
			sbSQL.append(" signUser.sname signUserName,delUser.sname delUserName, \n");
			sbSQL.append(" office.sname officename,cpfUser.sname DealUserName \n");
			sbSQL.append(" FROM OB_FinanceInstr fin, OB_User cfmUser, \n");
			sbSQL.append(" OB_User checkUser , OB_User signUser, \n");
			sbSQL.append(" OB_User delUser,office ,userinfo cpfUser \n");
			sbSQL.append(" WHERE fin.nStatus >= " + OBConstant.SettInstrStatus.SAVE + "\n");
			sbSQL.append(" AND fin.nconfirmuserid=cfmUser.id(+) \n");
			sbSQL.append(" AND fin.nsignuserid=signuser.id(+) \n");
			sbSQL.append(" AND fin.nDeleteuserid=delUser.id(+) \n");
			sbSQL.append(" AND fin.nCheckUserID=checkUser.id(+) \n");
			sbSQL.append(" AND fin.CPF_nOfficeid=office.id(+) \n");
			sbSQL.append(" AND fin.CPF_nDealUserId=cpfUser.id(+) \n");
			if (qi.getCurrencyID() >0)
				sbSQL.append(" AND fin.nCurrencyID=" + qi.getCurrencyID() + " \n");
			sbSQL.append(" AND fin.nClientID=" + qi.getClientID() + " \n");

			//交易指令类型
			if (qi.getTransType() > 0)
			{
				sbSQL.append(" AND fin.ntranstype = " + OBConstant.SettInstrType.APPLYCAPITAL);
			}
			
			//业务复核匹配，查询未复核的记录：非登录人录入的本单位交易指令
			if (qi.getOperationTypeID() == OBConstant.QueryOperationType.CHECK)
			{
				sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.SAVE);
				sbSQL.append(" AND fin.nConfirmUserID !=" + qi.getUserID());
				//匹配条件	
				//sbSQL.append(" AND fin.NPAYERACCTID = " + qi.getPayerAcctID());
				//sbSQL.append(" AND fin.NREMITTYPE = " + qi.getRemitType());
				//sbSQL.append(" AND fin.NPAYEEACCTID = " + qi.getPayeeAcctID());
				sbSQL.append(" AND fin.MAMOUNT = " + qi.getAmount());
				//sbSQL.append(" AND fin.DTEXECUTE = " + Timestamp.valueOf(qi.getExecuteDate().substring(0, 10)));
				if (qi.getExecuteDate() != null && qi.getExecuteDate().trim().length() > 0)
				{
					sbSQL.append(" AND fin.DTEXECUTE >= ? \n");
					sbSQL.append(" AND fin.DTEXECUTE <= ? \n");
				}
			}

			sbSQL.append("\n ORDER BY fin.dtconfirm ASC ,fin.NPAYERACCTID ");
			log4j.info("OBCapitalTransDao.queryMatchTrans():SQL==" + sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			int nIndex = 1;
			if (qi.getExecuteDate() != null && qi.getExecuteDate().trim().length() > 0)
			{
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qi.getExecuteDate().trim() + " 00:00:00"));
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qi.getExecuteDate().trim() + " 23:59:59"));
			}

			rs = ps.executeQuery();
			vReturn = setInfo(rs);

			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			log4j.info("query result : " + vReturn.size());
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
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return vReturn.size() > 0 ? vReturn : null;
	}
	
	/**
		 * 根据查询条件表单类，匹配出符合查询条件的下属单位资金划拨指令信息
		 * Create Date: 2003-8-13
		 * @param OBQueryInfo 查询条件表单类
		 * @return Collection  所有符合条件交易指令信息
		 * @exception Exception
		 */
	public Collection queryMatchChildTrans(OBQueryInfo qi) throws Exception
	{
		FinanceInfo info = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector vReturn = new Vector();
		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT fin.*, \n");
			sbSQL.append(" cfmUser.sname confirmUserName, checkUser.sname checkUserName, \n");
			sbSQL.append(" signUser.sname signUserName,delUser.sname delUserName, \n");
			sbSQL.append(" office.sname officename,cpfUser.sname DealUserName \n");
			sbSQL.append(" FROM OB_FinanceInstr fin, OB_User cfmUser, \n");
			sbSQL.append(" OB_User checkUser , OB_User signUser, \n");
			sbSQL.append(" OB_User delUser,office ,userinfo cpfUser \n");
			sbSQL.append(" WHERE fin.nStatus = " + qi.getStatus()+ "\n");
			sbSQL.append(" AND fin.nconfirmuserid=cfmUser.id(+) \n");
			sbSQL.append(" AND fin.nsignuserid=signuser.id(+) \n");
			sbSQL.append(" AND fin.nDeleteuserid=delUser.id(+) \n");
			sbSQL.append(" AND fin.nCheckUserID=checkUser.id(+) \n");
			sbSQL.append(" AND fin.CPF_nOfficeid=office.id(+) \n");
			sbSQL.append(" AND fin.CPF_nDealUserId=cpfUser.id(+) \n");
			sbSQL.append(" AND fin.nCurrencyID=" + qi.getCurrencyID() + " \n");
			sbSQL.append(" AND fin.nClientID=" + qi.getClientID() + " \n");

			//交易指令类型
			if (qi.getTransType() > 0)
			{
				sbSQL.append(" AND fin.ntranstype = " + OBConstant.getTransTypeByQueryType(qi.getTransType()));
			}
			//业务复核匹配，查询未复核的记录：非登录人录入的本单位交易指令
			if (qi.getOperationTypeID() == OBConstant.QueryOperationType.CHECK)
			{
				sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.SAVE);
				sbSQL.append(" AND fin.nConfirmUserID !=" + qi.getUserID());
				//匹配条件	
				sbSQL.append(" AND fin.nChildClientid = " + qi.getChildClientID());
				sbSQL.append(" AND fin.NPAYERACCTID = " + qi.getPayerAcctID());
				if(qi.getRemitType()>0){
					sbSQL.append(" AND fin.NREMITTYPE = " + qi.getRemitType());					
				}
				if(qi.getPayeeAcctID()>0){
					sbSQL.append(" AND fin.NPAYEEACCTID = " + qi.getPayeeAcctID());					
				}				
				sbSQL.append(" AND fin.MAMOUNT = " + qi.getAmount());
				//sbSQL.append(" AND fin.DTEXECUTE = " + Timestamp.valueOf(qi.getExecuteDate().substring(0, 10)));
				if (qi.getExecuteDate() != null && qi.getExecuteDate().trim().length() > 0)
				{
					sbSQL.append(" AND fin.DTEXECUTE >= ? \n");
					sbSQL.append(" AND fin.DTEXECUTE <= ? \n");
				}
			}

			sbSQL.append("\n ORDER BY fin.dtconfirm ASC ,fin.NPAYERACCTID ");
			log4j.info("OBCapitalTransDao.queryMatchTrans():SQL==" + sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			int nIndex = 1;
			if (qi.getExecuteDate() != null && qi.getExecuteDate().trim().length() > 0)
			{
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qi.getExecuteDate().trim() + " 00:00:00"));
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qi.getExecuteDate().trim() + " 23:59:59"));
			}

			rs = ps.executeQuery();
			vReturn = setInfo(rs);

			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			log4j.info("query result : " + vReturn.size());
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
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return vReturn.size() > 0 ? vReturn : null;
	}

	public Collection queryMatchFixedOpen(OBQueryInfo qi) throws Exception
	{
		FinanceInfo info = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector vReturn = new Vector();
		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT fin.*, \n");
			sbSQL.append(" cfmUser.sname confirmUserName, checkUser.sname checkUserName, \n");
			sbSQL.append(" signUser.sname signUserName,delUser.sname delUserName, \n");
			sbSQL.append(" office.sname officename,cpfUser.sname DealUserName \n");
			sbSQL.append(" FROM OB_FinanceInstr fin, OB_User cfmUser, \n");
			sbSQL.append(" OB_User checkUser , OB_User signUser, \n");
			sbSQL.append(" OB_User delUser,office ,userinfo cpfUser \n");
			sbSQL.append(" WHERE fin.nStatus = " + qi.getStatus() + "\n");
			sbSQL.append(" AND fin.nconfirmuserid=cfmUser.id(+) \n");
			sbSQL.append(" AND fin.nsignuserid=signuser.id(+) \n");
			sbSQL.append(" AND fin.nDeleteuserid=delUser.id(+) \n");
			sbSQL.append(" AND fin.nCheckUserID=checkUser.id(+) \n");
			sbSQL.append(" AND fin.CPF_nOfficeid=office.id(+) \n");
			sbSQL.append(" AND fin.CPF_nDealUserId=cpfUser.id(+) \n");
			sbSQL.append(" AND fin.nCurrencyID=" + qi.getCurrencyID() + " \n");
			sbSQL.append(" AND fin.nClientID=" + qi.getClientID() + " \n");
			if(qi.getIsAutoContinue() > 0)
			{
				sbSQL.append(" AND fin.isautocontinue =" + qi.getIsAutoContinue() + " \n");
			}
			if(qi.getAutocontinuetype() > 0)
			{
				sbSQL.append(" AND fin.autocontinuetype =" + qi.getAutocontinuetype() + " \n");
			}
			if(qi.getAutocontinueaccountid() > 0)
			{
				sbSQL.append(" AND fin.autocontinueaccountid =" + qi.getAutocontinueaccountid() + " \n");
			}
			
			

			//交易指令类型
			if (qi.getTransType() > 0)
			{
				sbSQL.append(" AND fin.ntranstype = " + OBConstant.SettInstrType.OPENFIXDEPOSIT);
			}
			//业务复核匹配，查询未复核的记录：非登录人录入的本单位交易指令
			if (qi.getOperationTypeID() == OBConstant.QueryOperationType.CHECK)
			{
				sbSQL.append(" AND fin.nstatus = " + qi.getStatus());
				sbSQL.append(" AND fin.nConfirmUserID !=" + qi.getUserID());
				//匹配条件	
				sbSQL.append(" AND fin.NPAYERACCTID = " + qi.getPayerAcctID());
				if(qi.getPayeeAcctID()>0){
					sbSQL.append(" AND fin.NPAYEEACCTID = " + qi.getPayeeAcctID());					
				}					
				sbSQL.append(" AND fin.MAMOUNT = " + qi.getAmount());
				if (qi.getExecuteDate() != null && qi.getExecuteDate().trim().length() > 0)
				{
					sbSQL.append(" AND fin.DTEXECUTE >= ? \n");
					sbSQL.append(" AND fin.DTEXECUTE <= ? \n");
				}
				sbSQL.append(" AND fin.NFIXEDDEPOSITTIME = " + qi.getFixedDepositTime());
			}
			sbSQL.append("\n ORDER BY fin.dtconfirm ASC ,fin.NPAYERACCTID ");
			log4j.info("OBCapitalTransDao.queryMatchTrans():SQL==" + sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			int nIndex = 1;
			if (qi.getExecuteDate() != null && qi.getExecuteDate().trim().length() > 0)
			{
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qi.getExecuteDate().trim() + " 00:00:00"));
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qi.getExecuteDate().trim() + " 23:59:59"));
			}
			rs = ps.executeQuery();

			vReturn = setInfo(rs);

			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			log4j.info("query result : " + vReturn.size());
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
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return vReturn.size() > 0 ? vReturn : null;
	}
	/**
	 * 通知支取
	 * @param qi
	 * @return
	 * @throws Exception
	 */
	public Collection queryMatchFixedDraw(OBQueryInfo qi) throws Exception
	{
		FinanceInfo info = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector vReturn = new Vector();
		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT fin.*, \n");
			sbSQL.append(" cfmUser.sname confirmUserName, checkUser.sname checkUserName, \n");
			sbSQL.append(" signUser.sname signUserName,delUser.sname delUserName, \n");
			sbSQL.append(" office.sname officename,cpfUser.sname DealUserName \n");
			sbSQL.append(" FROM OB_FinanceInstr fin, OB_User cfmUser, \n");
			sbSQL.append(" OB_User checkUser , OB_User signUser, \n");
			sbSQL.append(" OB_User delUser,office ,userinfo cpfUser \n");
			sbSQL.append(" WHERE fin.nStatus = " + qi.getStatus() + "\n");
			sbSQL.append(" AND fin.nconfirmuserid=cfmUser.id(+) \n");
			sbSQL.append(" AND fin.nsignuserid=signuser.id(+) \n");
			sbSQL.append(" AND fin.nDeleteuserid=delUser.id(+) \n");
			sbSQL.append(" AND fin.nCheckUserID=checkUser.id(+) \n");
			sbSQL.append(" AND fin.CPF_nOfficeid=office.id(+) \n");
			sbSQL.append(" AND fin.CPF_nDealUserId=cpfUser.id(+) \n");
			sbSQL.append(" AND fin.nCurrencyID=" + qi.getCurrencyID() + " \n");
			sbSQL.append(" AND fin.nClientID=" + qi.getClientID() + " \n");

			//交易指令类型
			if (qi.getTransType() > 0)
			{
				sbSQL.append(" AND fin.ntranstype = " + OBConstant.getTransTypeByQueryType(qi.getTransType()));
			}
			//业务复核匹配，查询未复核的记录：非登录人录入的本单位交易指令
			if (qi.getOperationTypeID() == OBConstant.QueryOperationType.CHECK)
			{
				sbSQL.append(" AND fin.nstatus = " + qi.getStatus());
				sbSQL.append(" AND fin.nConfirmUserID !=" + qi.getUserID());
				//匹配条件	
				if(qi.getRemitType()>0){
					sbSQL.append(" AND fin.NREMITTYPE = " + qi.getRemitType());					
				}
				if(qi.getPayeeAcctID()>0){
					sbSQL.append(" AND fin.NPAYEEACCTID = " + qi.getPayeeAcctID());					
				}					
				if (qi.getExecuteDate() != null && qi.getExecuteDate().trim().length() > 0)
				{
					sbSQL.append(" AND fin.DTEXECUTE >= ? \n");
					sbSQL.append(" AND fin.DTEXECUTE <= ? \n");
				}
				sbSQL.append(" AND fin.NSUBACCOUNTID = " + qi.getSubAccountID());
				sbSQL.append(" AND fin.NINTERESTREMITTYPE = " + qi.getInterestRemitType());
				sbSQL.append(" AND fin.NINTERESTPAYEEACCTID = " + qi.getInterestPayeeAcctID());
				sbSQL.append(" AND fin.MAMOUNT = " + qi.getAmount());
				
			}
			sbSQL.append("\n ORDER BY fin.dtconfirm ASC ,fin.NPAYERACCTID ");
			log4j.info("OBCapitalTransDao.queryMatchTrans():SQL==" + sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			int nIndex = 1;
			if (qi.getExecuteDate() != null && qi.getExecuteDate().trim().length() > 0)
			{
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qi.getExecuteDate().trim() + " 00:00:00"));
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qi.getExecuteDate().trim() + " 23:59:59"));
			}
			rs = ps.executeQuery();

			vReturn = setInfo(rs);

			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			log4j.info("query result : " + vReturn.size());
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
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return vReturn.size() > 0 ? vReturn : null;
	}

	public Collection queryMatchNoticeOpen(OBQueryInfo qi) throws Exception
	{
		FinanceInfo info = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector vReturn = new Vector();
		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT fin.*, \n");
			sbSQL.append(" cfmUser.sname confirmUserName, checkUser.sname checkUserName, \n");
			sbSQL.append(" signUser.sname signUserName,delUser.sname delUserName, \n");
			sbSQL.append(" office.sname officename,cpfUser.sname DealUserName \n");
			sbSQL.append(" FROM OB_FinanceInstr fin, OB_User cfmUser, \n");
			sbSQL.append(" OB_User checkUser , OB_User signUser, \n");
			sbSQL.append(" OB_User delUser,office ,userinfo cpfUser \n");
			sbSQL.append(" WHERE fin.nStatus = " + qi.getStatus() + "\n");
			sbSQL.append(" AND fin.nconfirmuserid=cfmUser.id(+) \n");
			sbSQL.append(" AND fin.nsignuserid=signuser.id(+) \n");
			sbSQL.append(" AND fin.nDeleteuserid=delUser.id(+) \n");
			sbSQL.append(" AND fin.nCheckUserID=checkUser.id(+) \n");
			sbSQL.append(" AND fin.CPF_nOfficeid=office.id(+) \n");
			sbSQL.append(" AND fin.CPF_nDealUserId=cpfUser.id(+) \n");
			sbSQL.append(" AND fin.nCurrencyID=" + qi.getCurrencyID() + " \n");
			sbSQL.append(" AND fin.nClientID=" + qi.getClientID() + " \n");

			//交易指令类型
			if (qi.getTransType() > 0)
			{
				sbSQL.append(" AND fin.ntranstype = " + OBConstant.getTransTypeByQueryType(qi.getTransType()));
			}
			//业务复核匹配，查询未复核的记录：非登录人录入的本单位交易指令
			if (qi.getOperationTypeID() == OBConstant.QueryOperationType.CHECK)
			{
				sbSQL.append(" AND fin.nstatus = " + qi.getStatus());
				sbSQL.append(" AND fin.nConfirmUserID !=" + qi.getUserID());
				//匹配条件	
				sbSQL.append(" AND fin.NPAYERACCTID = " + qi.getPayerAcctID());
			
				if(qi.getPayeeAcctID()>0){
					sbSQL.append(" AND fin.NPAYEEACCTID = " + qi.getPayeeAcctID());					
				}					
				sbSQL.append(" AND fin.MAMOUNT = " + qi.getAmount());
				if (qi.getExecuteDate() != null && qi.getExecuteDate().trim().length() > 0)
				{
					sbSQL.append(" AND fin.DTEXECUTE >= ? \n");
					sbSQL.append(" AND fin.DTEXECUTE <= ? \n");
				}
				sbSQL.append(" AND fin.NNOTICEDAY = " + qi.getNoticeDay());
			}
			sbSQL.append("\n ORDER BY fin.dtconfirm ASC ,fin.NPAYERACCTID ");
			log4j.info("OBCapitalTransDao.queryMatchTrans():SQL==" + sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			int nIndex = 1;
			if (qi.getExecuteDate() != null && qi.getExecuteDate().trim().length() > 0)
			{
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qi.getExecuteDate().trim() + " 00:00:00"));
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qi.getExecuteDate().trim() + " 23:59:59"));
			}
			rs = ps.executeQuery();

			vReturn = setInfo(rs);

			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			log4j.info("query result : " + vReturn.size());
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
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return vReturn.size() > 0 ? vReturn : null;
	}

	public Collection queryMatchNoticeDraw(OBQueryInfo qi) throws Exception 
	{
		FinanceInfo info = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector vReturn = new Vector();
		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT fin.*, \n");
			sbSQL.append(" cfmUser.sname confirmUserName, checkUser.sname checkUserName, \n");
			sbSQL.append(" signUser.sname signUserName,delUser.sname delUserName, \n");
			sbSQL.append(" office.sname officename,cpfUser.sname DealUserName \n");
			sbSQL.append(" FROM OB_FinanceInstr fin, OB_User cfmUser, \n");
			sbSQL.append(" OB_User checkUser , OB_User signUser, \n");
			sbSQL.append(" OB_User delUser,office ,userinfo cpfUser \n");
			sbSQL.append(" WHERE fin.nStatus = " + qi.getStatus() + "\n");
			sbSQL.append(" AND fin.nconfirmuserid=cfmUser.id(+) \n");
			sbSQL.append(" AND fin.nsignuserid=signuser.id(+) \n");
			sbSQL.append(" AND fin.nDeleteuserid=delUser.id(+) \n");
			sbSQL.append(" AND fin.nCheckUserID=checkUser.id(+) \n");
			sbSQL.append(" AND fin.CPF_nOfficeid=office.id(+) \n");
			sbSQL.append(" AND fin.CPF_nDealUserId=cpfUser.id(+) \n");
			sbSQL.append(" AND fin.nCurrencyID=" + qi.getCurrencyID() + " \n");
			sbSQL.append(" AND fin.nClientID=" + qi.getClientID() + " \n");

			//交易指令类型
			if (qi.getTransType() > 0)
			{
				sbSQL.append(" AND fin.ntranstype = " + OBConstant.getTransTypeByQueryType(qi.getTransType()));
			}
			//业务复核匹配，查询未复核的记录：非登录人录入的本单位交易指令
			if (qi.getOperationTypeID() == OBConstant.QueryOperationType.CHECK)
			{
				sbSQL.append(" AND fin.nstatus = " + qi.getStatus());
				sbSQL.append(" AND fin.nConfirmUserID !=" + qi.getUserID());
				//匹配条件	
				if(qi.getRemitType()>0){
					sbSQL.append(" AND fin.NREMITTYPE = " + qi.getRemitType());					
				}
				if(qi.getPayeeAcctID()>0){
					sbSQL.append(" AND fin.NPAYEEACCTID = " + qi.getPayeeAcctID());					
				}					
				if (qi.getExecuteDate() != null && qi.getExecuteDate().trim().length() > 0)
				{
					sbSQL.append(" AND fin.DTEXECUTE >= ? \n");
					sbSQL.append(" AND fin.DTEXECUTE <= ? \n");
				}
				if(qi.getSubAccountID()>0){
					sbSQL.append(" AND fin.NSUBACCOUNTID = " + qi.getSubAccountID());
				}
				if(qi.getInterestRemitType()>0){
					sbSQL.append(" AND fin.NINTERESTREMITTYPE = " + qi.getInterestRemitType());
				}
				if(qi.getInterestPayeeAcctID()>0){
					sbSQL.append(" AND fin.NINTERESTPAYEEACCTID = " + qi.getInterestPayeeAcctID());
				}
				////////////	 add by fxzhang 2006-12-28
				sbSQL.append(" AND fin.MAMOUNT = " + qi.getAmount());
				
			}
			sbSQL.append("\n ORDER BY fin.dtconfirm ASC ,fin.NPAYERACCTID ");
			log4j.info("OBCapitalTransDao.queryMatchTrans():SQL==" + sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			int nIndex = 1;
			if (qi.getExecuteDate() != null && qi.getExecuteDate().trim().length() > 0)
			{
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qi.getExecuteDate().trim() + " 00:00:00"));
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qi.getExecuteDate().trim() + " 23:59:59"));
			}
			rs = ps.executeQuery();

			vReturn = setInfo(rs);

			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			log4j.info("query result : " + vReturn.size());
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
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return vReturn.size() > 0 ? vReturn : null;
	}

	public Collection queryMatchTrustLoan(OBQueryInfo qi) throws Exception
	{
		FinanceInfo info = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector vReturn = new Vector();
		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT fin.*, \n");
			sbSQL.append(" cfmUser.sname confirmUserName, checkUser.sname checkUserName, \n");
			sbSQL.append(" signUser.sname signUserName,delUser.sname delUserName, \n");
			sbSQL.append(" office.sname officename,cpfUser.sname DealUserName \n");
			sbSQL.append(" FROM OB_FinanceInstr fin, OB_User cfmUser, \n");
			sbSQL.append(" OB_User checkUser , OB_User signUser, \n");
			sbSQL.append(" OB_User delUser,office ,userinfo cpfUser \n");
			sbSQL.append(" WHERE fin.nStatus >= " + OBConstant.SettInstrStatus.SAVE + "\n");
			sbSQL.append(" AND fin.nconfirmuserid=cfmUser.id(+) \n");
			sbSQL.append(" AND fin.nsignuserid=signuser.id(+) \n");
			sbSQL.append(" AND fin.nDeleteuserid=delUser.id(+) \n");
			sbSQL.append(" AND fin.nCheckUserID=checkUser.id(+) \n");
			sbSQL.append(" AND fin.CPF_nOfficeid=office.id(+) \n");
			sbSQL.append(" AND fin.CPF_nDealUserId=cpfUser.id(+) \n");
			sbSQL.append(" AND fin.nCurrencyID=" + qi.getCurrencyID() + " \n");
			sbSQL.append(" AND fin.nClientID=" + qi.getClientID() + " \n");

			//交易指令类型
			if (qi.getTransType() > 0)
			{
				sbSQL.append(" AND fin.ntranstype = " + OBConstant.getTransTypeByQueryType(qi.getTransType()));
			}
			//业务复核匹配，查询未复核的记录：非登录人录入的本单位交易指令
			if (qi.getOperationTypeID() == OBConstant.QueryOperationType.CHECK)
			{
				sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.SAVE);
				sbSQL.append(" AND fin.nConfirmUserID !=" + qi.getUserID());
				//匹配条件	
				sbSQL.append(" AND fin.NPAYERACCTID = " + qi.getPayerAcctID());
				sbSQL.append(" AND fin.MAMOUNT = " + qi.getAmount());
				if (qi.getExecuteDate() != null && qi.getExecuteDate().trim().length() > 0)
				{
					sbSQL.append(" AND fin.DTEXECUTE >= ? \n");
					sbSQL.append(" AND fin.DTEXECUTE <= ? \n");
				}
				sbSQL.append(" AND fin.NCONTRACTID = " + qi.getContractID());
				sbSQL.append(" AND fin.NLOANNOTEID = " + qi.getLoanNoteID());
			}
			sbSQL.append("\n ORDER BY fin.dtconfirm ASC ,fin.NPAYERACCTID ");
			log4j.info("OBCapitalTransDao.queryMatchTrans():SQL==" + sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			int nIndex = 1;
			if (qi.getExecuteDate() != null && qi.getExecuteDate().trim().length() > 0)
			{
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qi.getExecuteDate().trim() + " 00:00:00"));
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qi.getExecuteDate().trim() + " 23:59:59"));
			}
			rs = ps.executeQuery();

			vReturn = setInfo(rs);

			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			log4j.info("query result : " + vReturn.size());
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
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return vReturn.size() > 0 ? vReturn : null;
	}

	public Collection queryMatchConsignLoan(OBQueryInfo qi) throws Exception
	{
		FinanceInfo info = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector vReturn = new Vector();
		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT fin.*, \n");
			sbSQL.append(" cfmUser.sname confirmUserName, checkUser.sname checkUserName, \n");
			sbSQL.append(" signUser.sname signUserName,delUser.sname delUserName, \n");
			sbSQL.append(" office.sname officename,cpfUser.sname DealUserName \n");
			sbSQL.append(" FROM OB_FinanceInstr fin, OB_User cfmUser, \n");
			sbSQL.append(" OB_User checkUser , OB_User signUser, \n");
			sbSQL.append(" OB_User delUser,office ,userinfo cpfUser \n");
			sbSQL.append(" WHERE fin.nStatus >= " + OBConstant.SettInstrStatus.SAVE + "\n");
			sbSQL.append(" AND fin.nconfirmuserid=cfmUser.id(+) \n");
			sbSQL.append(" AND fin.nsignuserid=signuser.id(+) \n");
			sbSQL.append(" AND fin.nDeleteuserid=delUser.id(+) \n");
			sbSQL.append(" AND fin.nCheckUserID=checkUser.id(+) \n");
			sbSQL.append(" AND fin.CPF_nOfficeid=office.id(+) \n");
			sbSQL.append(" AND fin.CPF_nDealUserId=cpfUser.id(+) \n");
			sbSQL.append(" AND fin.nCurrencyID=" + qi.getCurrencyID() + " \n");
			sbSQL.append(" AND fin.nClientID=" + qi.getClientID() + " \n");

			//交易指令类型
			if (qi.getTransType() > 0)
			{
				sbSQL.append(" AND fin.ntranstype = " + OBConstant.getTransTypeByQueryType(qi.getTransType()));
			}
			//业务复核匹配，查询未复核的记录：非登录人录入的本单位交易指令
			if (qi.getOperationTypeID() == OBConstant.QueryOperationType.CHECK)
			{
				sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.SAVE);
				sbSQL.append(" AND fin.nConfirmUserID !=" + qi.getUserID());
				//匹配条件	
				sbSQL.append(" AND fin.NPAYERACCTID = " + qi.getPayerAcctID());
				sbSQL.append(" AND fin.MAMOUNT = " + qi.getAmount());
				if (qi.getExecuteDate() != null && qi.getExecuteDate().trim().length() > 0)
				{
					sbSQL.append(" AND fin.DTEXECUTE >= ? \n");
					sbSQL.append(" AND fin.DTEXECUTE <= ? \n");
				}
				sbSQL.append(" AND fin.NCONTRACTID = " + qi.getContractID());
				sbSQL.append(" AND fin.NLOANNOTEID = " + qi.getLoanNoteID());
				sbSQL.append(" AND fin.MREALINTEREST = " + qi.getRealInterest());
				sbSQL.append(" AND fin.MREALCOMPOUNDINTEREST = " + qi.getRealCompoundInterest());
				sbSQL.append(" AND fin.MREALOVERDUEINTEREST = " + qi.getRealOverdueInterest());
				sbSQL.append(" AND fin.MREALCOMMISSION = " + qi.getRealCommission());
			}
			sbSQL.append("\n ORDER BY fin.dtconfirm ASC ,fin.NPAYERACCTID ");
			log4j.info("OBCapitalTransDao.queryMatchTrans():SQL==" + sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			int nIndex = 1;
			if (qi.getExecuteDate() != null && qi.getExecuteDate().trim().length() > 0)
			{
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qi.getExecuteDate().trim() + " 00:00:00"));
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qi.getExecuteDate().trim() + " 23:59:59"));
			}
			rs = ps.executeQuery();

			vReturn = setInfo(rs);

			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			log4j.info("query result : " + vReturn.size());
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
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return vReturn.size() > 0 ? vReturn : null;
	}

	public Collection queryMatchInterest(OBQueryInfo qi) throws Exception
	{
		FinanceInfo info = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector vReturn = new Vector();
		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT fin.*, \n");
			sbSQL.append(" cfmUser.sname confirmUserName, checkUser.sname checkUserName, \n");
			sbSQL.append(" signUser.sname signUserName,delUser.sname delUserName, \n");
			sbSQL.append(" office.sname officename,cpfUser.sname DealUserName \n");
			sbSQL.append(" FROM OB_FinanceInstr fin, OB_User cfmUser, \n");
			sbSQL.append(" OB_User checkUser , OB_User signUser, \n");
			sbSQL.append(" OB_User delUser,office ,userinfo cpfUser \n");
			sbSQL.append(" WHERE fin.nStatus >= " + OBConstant.SettInstrStatus.SAVE + "\n");
			sbSQL.append(" AND fin.nconfirmuserid=cfmUser.id(+) \n");
			sbSQL.append(" AND fin.nsignuserid=signuser.id(+) \n");
			sbSQL.append(" AND fin.nDeleteuserid=delUser.id(+) \n");
			sbSQL.append(" AND fin.nCheckUserID=checkUser.id(+) \n");
			sbSQL.append(" AND fin.CPF_nOfficeid=office.id(+) \n");
			sbSQL.append(" AND fin.CPF_nDealUserId=cpfUser.id(+) \n");
			sbSQL.append(" AND fin.nCurrencyID=" + qi.getCurrencyID() + " \n");
			sbSQL.append(" AND fin.nClientID=" + qi.getClientID() + " \n");

			//交易指令类型
			if (qi.getTransType() > 0)
			{
				sbSQL.append(" AND fin.ntranstype = " + OBConstant.getTransTypeByQueryType(qi.getTransType()));
			}
			//业务复核匹配，查询未复核的记录：非登录人录入的本单位交易指令
			if (qi.getOperationTypeID() == OBConstant.QueryOperationType.CHECK)
			{
				sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.SAVE);
				sbSQL.append(" AND fin.nConfirmUserID !=" + qi.getUserID());
				//匹配条件	
				sbSQL.append(" AND fin.NPAYERACCTID = " + qi.getPayerAcctID());
				sbSQL.append(" AND fin.MAMOUNT = " + qi.getAmount());
				if (qi.getExecuteDate() != null && qi.getExecuteDate().trim().length() > 0)
				{
					sbSQL.append(" AND fin.DTEXECUTE >= ? \n");
					sbSQL.append(" AND fin.DTEXECUTE <= ? \n");
				}
				if (qi.getClearInterest() != null && qi.getClearInterest().trim().length() > 0)
				{
					sbSQL.append(" AND fin.DTCLEARINTEREST >= ? \n");
					sbSQL.append(" AND fin.DTCLEARINTEREST <= ? \n");
				}
				sbSQL.append(" AND fin.NCONTRACTID = " + qi.getContractID());
				sbSQL.append(" AND fin.NLOANNOTEID = " + qi.getLoanNoteID());
				sbSQL.append(" AND fin.MREALINTEREST = " + qi.getRealInterest());
				sbSQL.append(" AND fin.MREALCOMPOUNDINTEREST = " + qi.getRealCompoundInterest());
				sbSQL.append(" AND fin.MREALOVERDUEINTEREST = " + qi.getRealOverdueInterest());
				sbSQL.append(" AND fin.MREALSURETYFEE = " + qi.getRealSuretyFee());
				sbSQL.append(" AND fin.MREALCOMMISSION = " + qi.getRealCommission());
				//todo			
			}
			sbSQL.append("\n ORDER BY fin.dtconfirm ASC ,fin.NPAYERACCTID ");
			log4j.info("OBCapitalTransDao.queryMatchTrans():SQL==" + sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			int nIndex = 1;
			if (qi.getExecuteDate() != null && qi.getExecuteDate().trim().length() > 0)
			{
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qi.getExecuteDate().trim() + " 00:00:00"));
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qi.getExecuteDate().trim() + " 23:59:59"));
			}
			if (qi.getClearInterest() != null && qi.getClearInterest().trim().length() > 0)
			{
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qi.getClearInterest().trim() + " 00:00:00"));
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qi.getClearInterest().trim() + " 23:59:59"));
			}
			rs = ps.executeQuery();

			vReturn = setInfo(rs);

			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			log4j.info("query result : " + vReturn.size());
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
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return vReturn.size() > 0 ? vReturn : null;
	}

	public Vector setInfo(ResultSet rs) throws Exception
	{
		FinanceInfo info = null;
		Vector vReturn = new Vector();
		OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
		Connection con = null;

		try
		{
			if (rs != null)
			{
				while (rs.next())
				{
					info = new FinanceInfo();
					info.setID(rs.getLong("ID")); // 指令序号
					info.setChildClientID(rs.getLong("nChildClientid")); //下属单位
					info.setClientID(rs.getLong("NCLIENTID")); // 交易提交单位
					info.setCurrencyID(rs.getLong("NCURRENCYID")); // 交易币种
					info.setTransType(rs.getLong("NTRANSTYPE")); // 网上交易类型
					info.setPayerAcctID(rs.getLong("NPAYERACCTID")); // 付款方账户ID				
					info.setPayeeAcctID(rs.getLong("NPAYEEACCTID")); //收款方账户ID
					info.setRemitType(rs.getLong("NREMITTYPE")); // 汇款方式
					info.setFixEdremark(rs.getString("fixedremark"));//备注
					info.setIsFixContinue(rs.getLong("isFixContinue"));
					// 收款方名称
					info.setAmount(rs.getDouble("MAMOUNT")); // 交易金额
					info.setExecuteDate(rs.getTimestamp("DTEXECUTE")); // 执行日
					info.setNote(rs.getString("SNOTE")); // 汇款用途/摘要
					info.setFixedDepositTime(rs.getLong("NFIXEDDEPOSITTIME")); // 定期存款期限（月）
					info.setContractID(rs.getLong("NContractID")); // 贷款合同ID
					info.setLoanNoteID(rs.getLong("NLoanNoteID")); //放款通知单ID
					info.setPayDate(rs.getTimestamp("DTPAY")); // 贷款放款日期
					info.setDepositNo(rs.getString("SDEPOSITNO")); //定期（通知）存款单据号
					info.setSubAccountID(rs.getLong("nSubAccountID")); //子账户ID
					info.setDepositStart(rs.getTimestamp("DTDEPOSITSTART")); //定期（通知）存款起始日
					info.setDepositRate(rs.getDouble("MDEPOSITRATE")); //定期存单利率
					info.setDepositAmount(rs.getDouble("MDEPOSITAMOUNT")); //存单金额（开立金额）
					info.setDepositBalance(rs.getDouble("MDEPOSITBALANCE")); //存单余额
					info.setClearInterest(rs.getTimestamp("dtClearInterest")); //结息日
					info.setInterestPayeeAcctID(rs.getLong("NINTERESTPAYEEACCTID")); //利息收款方账户ID
					info.setInterestRemitType(rs.getLong("NINTERESTREMITTYPE")); //利息付款方式
					info.setInterest(rs.getDouble("MINTEREST")); //应付贷款利息
					info.setCompoundInterest(rs.getDouble("MCOMPOUNDINTEREST")); //应付复利
					info.setOverdueInterest(rs.getDouble("MOVERDUEINTEREST")); //应付逾期罚息
					info.setSuretyFee(rs.getDouble("MSURETYFEE")); //应付担保费
					info.setCommission(rs.getDouble("MCOMMISSION")); //应付手续费
					info.setRealInterest(rs.getDouble("MREALINTEREST")); //实付贷款利息
					info.setRealCompoundInterest(rs.getDouble("MREALCOMPOUNDINTEREST")); //实付复利
					info.setRealOverdueInterest(rs.getDouble("MREALOVERDUEINTEREST")); //实付逾期罚息
					info.setRealSuretyFee(rs.getDouble("MREALSURETYFEE")); //实付担保费
					info.setRealCommission(rs.getDouble("MREALCOMMISSION")); //实付手续费
					info.setStatus(rs.getLong("NSTATUS")); // 指令状态
					info.setConfirmDate(rs.getTimestamp("DTCONFIRM")); //确认日期
					info.setConfirmUserID(rs.getLong("NCONFIRMUSERID")); //确认人ID	
					info.setConfirmUserName(rs.getString("confirmUserName")); // 确认人姓名
					info.setCheckDate(rs.getTimestamp("DTCHECK")); //复核日期
					info.setCheckUserID(rs.getLong("NCHECKUSERID")); //复核人ID
					info.setCheckUserName(rs.getString("checkUserName")); // 复核人姓名
					info.setSignDate(rs.getTimestamp("DTSIGN")); //签认日期
					info.setSignUserID(rs.getLong("NSIGNUSERID")); //签认人ID
					info.setSignUserName(rs.getString("signUserName")); // 签认人姓名
					info.setNoticeDay(rs.getLong("nnoticeday")); //通知存款品种
					info.setDeleteDate(rs.getTimestamp("DTDELETE")); //删除日期
					info.setDeleteUserID(rs.getLong("NDELETEUSERID")); //删除人ID
					info.setDeleteUserName(rs.getString("delUserName")); // 删除人姓名
					info.setDealDate(rs.getTimestamp("CPF_DTDEAL")); //CPF-处理日期
					info.setDealUserID(rs.getLong("CPF_NDEALUSERID")); //CPF-处理人ID
					info.setDealUserName(rs.getString("DealUserName")); // CPF-处理人姓名	
					info.setOfficeID(rs.getLong("CPF_NOFFICEID")); //CPF-默认办事处ID
					info.setTransNo(rs.getString("CPF_STRANSNO")); //CPF-交易号
					info.setFinishDate(rs.getTimestamp("CPF_DTFINISH")); //CPF-完成时间
					info.setReject(rs.getString("CPF_SREJECT")); //CPF-拒绝原因
					info.setDefaultTransType(rs.getLong("CPF_NDEFAULTTRANSTYPE")); // 默认交易类型
					info.setSubAccountID(rs.getLong("NSUBACCOUNTID")); //子账户
					info.setClearInterest(rs.getTimestamp("DTCLEARINTEREST")); //结昔日期	
					info.setInterestStart(rs.getTimestamp("DTINTERESTSTART"));
					info.setCompoundStart(rs.getTimestamp("DTCOMPOUNDINTERESTSTART"));
					info.setCompoundRate(rs.getDouble("MCOMPOUNDRATE"));
					info.setOverDueStart(rs.getTimestamp("DTOVERDUESTART"));
					info.setOverDueRate(rs.getDouble("MOVERDUERATE"));
					info.setSuretyStart(rs.getTimestamp("DTSURETYFEESTART"));
					info.setSuretyRate(rs.getDouble("MSURETYFEERATE"));
					info.setCommissionStart(rs.getTimestamp("DTCOMMISSIONSTART"));
					info.setCommissionRate(rs.getDouble("MCOMMISSIONRATE"));
					info.setInterestRate(rs.getDouble("MLOANREPAYMENTRATE"));
					info.setCompoundAmount(rs.getDouble("MCOMPOUNDAMOUNT"));
					info.setOverDueAmount(rs.getDouble("MOVERDUEAMOUNT"));
					info.setInterestReceiveable(rs.getDouble("MINTERESTRECEIVEABLE"));
					info.setInterestIncome(rs.getDouble("MINTERESTINCOME"));
					info.setRealInterestReceiveable(rs.getDouble("MREALINTERESTRECEIVEABLE"));
					info.setRealInterestIncome(rs.getDouble("MREALINTERESTINCOME"));
					info.setInterestTax(rs.getDouble("MINTERESTTAX"));
					info.setRealInterestTax(rs.getDouble("MREALINTERESTTAX"));
					info.setSDepositBillStartDate(rs.getTimestamp("FIXEDNEXTSTARTDATE"));
					info.setSDepositBillEndDate(rs.getTimestamp("FIXEDNEXTENDDATE"));
					info.setSDepositBillPeriod(rs.getLong("FIXEDNEXTPERIOD"));
					info.setSDepositInterestDeal(rs.getLong("FIXEDINTERESTDEAL"));
					info.setSDepositInterestToAccountID(rs.getLong("FIXEDINTERESTTOACCOUNTID"));
					info.setSDepositBillNo(rs.getString("SDEPOSITBILLNO"));
					info.setDtModify(rs.getTimestamp("dtmodify"));
					info.setIsAutoContinue(rs.getLong("isautocontinue"));
					info.setAutocontinuetype(rs.getLong("autocontinuetype"));
					info.setAutocontinueaccountid(rs.getLong("autocontinueaccountid"));
					info.setApplyCode(rs.getString("sapplycode"));
					info.setSource(rs.getLong("lsource"));
					
					//added by mzh_fu 2007/05/21 签名值
					info.setSignatureValue(rs.getString("SIGNATUREVALUE"));
					info.setActionStatus(rs.getLong("ACTIONSTATUS"));

					//合同编号以及放款通知单编号
					info.setLoanContractNo(NameRef.getContractNoByID(info.getContractID())); //贷款合同编号
					info.setLetOutCode(NameRef.getPayFormNoByID(info.getLoanNoteID())); //放款通知单编号
					//获取收款方和付款方的详细信息
					PayerOrPayeeInfo payerInfo = new PayerOrPayeeInfo();
					PayerOrPayeeInfo payeeInfo = new PayerOrPayeeInfo();
					PayerOrPayeeInfo interestpPayeeInfo = new PayerOrPayeeInfo();
					payerInfo = obFinanceInstrDao.getPayerOrPayeeInfoByID(info.getPayerAcctID(), info.getRemitType(), OBConstant.PayerOrPayee.PAYER);
					payeeInfo = obFinanceInstrDao.getPayerOrPayeeInfoByID(info.getPayeeAcctID(), info.getRemitType(), OBConstant.PayerOrPayee.PAYEE);
					interestpPayeeInfo = obFinanceInstrDao.getPayerOrPayeeInfoByID(info.getInterestPayeeAcctID(), info.getInterestRemitType(), OBConstant.PayerOrPayee.PAYEE);
					info.setPayerBankNo(payerInfo.getBankNo()); // 付款方银行编号				
					info.setPayerAcctNo(payerInfo.getAccountNo()); // 付款方账号
					info.setPayerName(payerInfo.getAccountName()); // 付款方名称
					info.setPayeeName(payeeInfo.getAccountName()); // 收款方名称
					info.setPayeeBankNo(payeeInfo.getBankNo()); // 收款方银行编号				
					info.setPayeeAcctNo(payeeInfo.getAccountNo()); // 收款方账号
					info.setPayeeProv(payeeInfo.getProv()); // 汇入省
					info.setPayeeCity(payeeInfo.getCity()); // 汇入市
					info.setPayeeBankName(payeeInfo.getBankName()); // 汇入行名称
					info.setSPayeeBankCNAPSNO(payeeInfo.getSPayeeBankCNAPSNO());
					info.setSPayeeBankExchangeNO(payeeInfo.getSPayeeBankExchangeNO());
					info.setSPayeeBankOrgNO(payeeInfo.getSPayeeBankOrgNO());
					info.setInterestPayeeName(interestpPayeeInfo.getAccountName()); //利息收款方名称
					info.setInterestPayeeBankNo(interestpPayeeInfo.getBankNo()); // 利息收款方银行编号				
					info.setInterestPayeeAcctNo(interestpPayeeInfo.getAccountNo()); // 利息收款方账号
					info.setInterestPayeeProv(interestpPayeeInfo.getProv()); // 利息汇入省
					info.setInterestPayeeCity(interestpPayeeInfo.getCity()); // 利息汇入市
					info.setInterestPayeeBankName(interestpPayeeInfo.getBankName()); // 利息汇入行名称
					info.setSInterestPayeeBankCNAPSNO(interestpPayeeInfo.getSPayeeBankCNAPSNO());
					info.setSInterestPayeeBankExchangeNO(interestpPayeeInfo.getSPayeeBankExchangeNO());
					info.setSInterestPayeeBankOrgNO(interestpPayeeInfo.getSPayeeBankOrgNO());
					// 获得账户的当前余额 
					AccountBalanceInfo abInfo = new AccountBalanceInfo();
					abInfo = obFinanceInstrDao.getCurrBalanceByAccountID(info.getPayerAcctID(), info.getCurrencyID(), info.getID());
					info.setCurrentBalance(abInfo.getCurrentBalance());
					info.setIsCycleAccount(abInfo.getIsCycleAccount());
					info.setMaxUsableAmount(abInfo.getMaxUsableAmount());
					info.setOverdraftAmount(abInfo.getOverdraftAmount());
					info.setUsableBalance(abInfo.getUsableBalance());
					


					//					下属单位
					if (info.getChildClientID() > 0)
					{
						info.setChildClientName(NameRef.getClientNameByID(info.getChildClientID()));
						info.setChildClientNo(NameRef.getClientCodeByID(info.getChildClientID()));
					}

					if (info.getLoanNoteID() > 0)
					{
						//获得当前放款通知单的贷款余额
						con = Database.getConnection();
						PreparedStatement ps = null;
						ResultSet rs1 = null;
						StringBuffer sbSQL = new StringBuffer();
						//根据放款通知单id得到子账户ID
						log4j.info("=============查找贷款余额开始=======");
						long lSubAccountID = -1;
						sbSQL.append("select ID from sett_SubAccount ");
						sbSQL.append("where AL_nLoanNoteID=");
						sbSQL.append(info.getLoanNoteID() + " \n");
						sbSQL.append("and nStatusId=1");
						log4j.info(sbSQL.toString());
						ps = con.prepareStatement(sbSQL.toString());
						rs1 = ps.executeQuery();
						if (rs1.next())
						{
							lSubAccountID = rs1.getLong(1);
							log4j.info("SubAccountId:" + lSubAccountID);
						}
						rs1.close();
						rs1 = null;
						ps.close();
						ps = null;
						sbSQL = new StringBuffer();
						if (lSubAccountID > 0)
						{
							if (info.getTransType() == OBConstant.SettInstrType.YTLOANRECEIVE)
							{
								sbSQL.append("select round((a.mAmount-nvl(dd.mAmount,0)),2) MBalance");
								sbSQL.append(" from LOAN_PAYFORM a,");
								sbSQL.append(" (select nvl(sum(aa.mAmount),0) mAmount,aa.nFormid nFormid ");
								sbSQL.append(" from SETT_SYNDICATIONLOANINTEREST aa, SETT_TRANSREPAYMENTLOAN bb");
								sbSQL.append(" where bb.id = aa.nsyndicationLoanReceiveID");
								sbSQL.append(" and  bb.nStatusID in (2,3)");
								sbSQL.append(" group by aa.nFormid) dd");
								sbSQL.append(" where a.id = dd.nFormid(+)");
								sbSQL.append(" and a.id=" + info.getLoanNoteID());
							}
							else
							{
								sbSQL.append("select MBalance ");
								sbSQL.append(" from SETT_SUBACCOUNT where ID=? ");
							}
							
							ps = con.prepareStatement(sbSQL.toString());
							
							if (info.getTransType() != OBConstant.SettInstrType.YTLOANRECEIVE)
							{
								ps.setLong(1, lSubAccountID);
							}
							
							rs1 = ps.executeQuery();
							if (rs1.next())
							{
								info.setBalance(rs1.getDouble("MBalance"));
								log4j.info("MBalance:" + info.getBalance());
							}
							rs1.close();
							rs1 = null;
							ps.close();
							ps = null;
						}
						log4j.info("=============查找贷款余额结束=======");
						con.close();
						con = null;

						LoanPayNoticeDao lpndao = new LoanPayNoticeDao();
						info.setRate(lpndao.getLatelyRate(info.getLoanNoteID(), null));
					}

					vReturn.add(info);
					info = null;
				}
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return vReturn;
	}
	
	/**
	 * 到期续存指令查询
	 * @param qi
	 * @return
	 * @throws Exception
	 */
	public Collection queryMatchDrivFixDeposit(OBQueryInfo qi) throws Exception
	{
		FinanceInfo info = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector vReturn = new Vector();
		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT fin.*, \n");
			sbSQL.append(" cfmUser.sname confirmUserName, checkUser.sname checkUserName, \n");
			sbSQL.append(" signUser.sname signUserName,delUser.sname delUserName, \n");
			sbSQL.append(" office.sname officename,cpfUser.sname DealUserName \n");
			sbSQL.append(" FROM OB_FinanceInstr fin, OB_User cfmUser, \n");
			sbSQL.append(" OB_User checkUser , OB_User signUser, \n");
			sbSQL.append(" OB_User delUser,office ,userinfo cpfUser \n");
			sbSQL.append(" WHERE fin.nStatus = " + qi.getStatus() + "\n");
			sbSQL.append(" AND fin.nconfirmuserid=cfmUser.id(+) \n");
			sbSQL.append(" AND fin.nsignuserid=signuser.id(+) \n");
			sbSQL.append(" AND fin.nDeleteuserid=delUser.id(+) \n");
			sbSQL.append(" AND fin.nCheckUserID=checkUser.id(+) \n");
			sbSQL.append(" AND fin.CPF_nOfficeid=office.id(+) \n");
			sbSQL.append(" AND fin.CPF_nDealUserId=cpfUser.id(+) \n");
			if (qi.getCurrencyID() >0)
				sbSQL.append(" AND fin.nCurrencyID=" + qi.getCurrencyID() + " \n");
			sbSQL.append(" AND fin.nClientID=" + qi.getClientID() + " \n");

			//交易指令类型
			if (qi.getTransType() > 0)
			{
				sbSQL.append(
					" AND fin.ntranstype ='"
						+ OBConstant.SettInstrType.DRIVEFIXDEPOSIT
						+ "' ");
			}
			//业务复核匹配，查询未复核的记录：非登录人录入的本单位交易指令
			if (qi.getOperationTypeID() == OBConstant.QueryOperationType.CHECK)
			{
				sbSQL.append(" AND fin.nstatus = " + qi.getStatus());
				sbSQL.append(" AND fin.nConfirmUserID !=" + qi.getUserID());
				//匹配条件	
				if(qi.getSDepositBillNo() != null && !qi.getSDepositBillNo().equals("")){
					sbSQL.append(" AND fin.SDEPOSITBILLNO = '" + qi.getSDepositBillNo() + "' ");
				}
				if(qi.getFixedInterestDeal()>0){
					sbSQL.append(" AND fin.FIXEDINTERESTDEAL = " + qi.getFixedInterestDeal());
					if(qi.getFixedInterestDeal() >1){
						if(qi.getLInterestToAccountID() != -1){
							sbSQL.append(" AND fin.FIXEDINTERESTTOACCOUNTID = " + qi.getLInterestToAccountID());
						}
					}
				}
				if(qi.getLFIXEDNEXTPERIOD()>0){
					sbSQL.append(" AND fin.FIXEDNEXTPERIOD = " + qi.getLFIXEDNEXTPERIOD());					
				}
				
				//新增是否自动续存的匹配
				sbSQL.append(" and fin.isautocontinue = "+qi.getIsAutoContinue());
				if(qi.getIsAutoContinue()==1)  //勾选自动续存
				{
					sbSQL.append(" and fin.autocontinuetype ="+qi.getAutocontinuetype());
					if(qi.getAutocontinuetype()==2)//自动续存类型为本金续存
					{
						sbSQL.append(" and fin.autocontinueaccountid ="+qi.getAutocontinueaccountid());
					}
					
				}
				
				if (qi.getStrNewStartDate() != null && qi.getStrNewStartDate().trim().length() > 0)
				{
					sbSQL.append(" AND fin.FIXEDNEXTSTARTDATE >= ? \n");
					sbSQL.append(" AND fin.FIXEDNEXTSTARTDATE <= ? \n");
				}
				sbSQL.append(" AND fin.MAMOUNT = " + qi.getAmount());
				//sbSQL.append(" AND fin.DTEXECUTE = " + Timestamp.valueOf(qi.getExecuteDate().substring(0, 10)));
				if (qi.getExecuteDate() != null && qi.getExecuteDate().trim().length() > 0)
				{
					sbSQL.append(" AND fin.DTEXECUTE >= ? \n");
					sbSQL.append(" AND fin.DTEXECUTE <= ? \n");
				}

			}

			sbSQL.append("\n ORDER BY fin.dtconfirm ASC ,fin.NPAYERACCTID ");
			log4j.info("OBCapitalTransDao.queryMatchTrans():SQL==" + sbSQL.toString());
			System.out.println("===============================sql="+sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			int nIndex = 1;
			if (qi.getStrNewStartDate() != null && qi.getStrNewStartDate().trim().length() > 0)
			{
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qi.getStrNewStartDate().trim() + " 00:00:00"));
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qi.getStrNewStartDate().trim() + " 23:59:59"));
			}
			if (qi.getExecuteDate() != null && qi.getExecuteDate().trim().length() > 0)
			{
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qi.getExecuteDate().trim() + " 00:00:00"));
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qi.getExecuteDate().trim() + " 23:59:59"));
			}
			System.out.println("ggggggggggggggggggggggggggggggggggggggggg");
			System.out.println("sbSQL="+sbSQL.toString());
			System.out.println("qi.getStrNewStartDate().trim()="+qi.getStrNewStartDate().trim());
			System.out.println("qi.getExecuteDate().trim()="+qi.getExecuteDate().trim());
			
			rs = ps.executeQuery();
			vReturn = setInfo(rs);

			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			log4j.info("query result : " + vReturn.size());
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
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return vReturn.size() > 0 ? vReturn : null;
	}
	
	/**
	 * 到期转存
	 * @param qi
	 * @return
	 * @throws Exception
	 */
	public Collection queryMatchChangeFixDdposit(OBQueryInfo qi) throws Exception
	{
		FinanceInfo info = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector vReturn = new Vector();
		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT fin.*, \n");
			sbSQL.append(" cfmUser.sname confirmUserName, checkUser.sname checkUserName, \n");
			sbSQL.append(" signUser.sname signUserName,delUser.sname delUserName, \n");
			sbSQL.append(" office.sname officename,cpfUser.sname DealUserName \n");
			sbSQL.append(" FROM OB_FinanceInstr fin, OB_User cfmUser, \n");
			sbSQL.append(" OB_User checkUser , OB_User signUser, \n");
			sbSQL.append(" OB_User delUser,office ,userinfo cpfUser \n");
			sbSQL.append(" WHERE fin.nStatus = " + qi.getStatus() + "\n");
			sbSQL.append(" AND fin.nconfirmuserid=cfmUser.id(+) \n");
			sbSQL.append(" AND fin.nsignuserid=signuser.id(+) \n");
			sbSQL.append(" AND fin.nDeleteuserid=delUser.id(+) \n");
			sbSQL.append(" AND fin.nCheckUserID=checkUser.id(+) \n");
			sbSQL.append(" AND fin.CPF_nOfficeid=office.id(+) \n");
			sbSQL.append(" AND fin.CPF_nDealUserId=cpfUser.id(+) \n");
			if (qi.getCurrencyID() >0)
				sbSQL.append(" AND fin.nCurrencyID=" + qi.getCurrencyID() + " \n");
			sbSQL.append(" AND fin.nClientID=" + qi.getClientID() + " \n");

			//交易指令类型
			if (qi.getTransType() > 0)
			{
				sbSQL.append(
					" AND fin.ntranstype ='"
						+ OBConstant.SettInstrType.CHANGEFIXDEPOSIT
						+ "' ");
			}
			//业务复核匹配，查询未复核的记录：非登录人录入的本单位交易指令
			if (qi.getOperationTypeID() == OBConstant.QueryOperationType.CHECK)
			{
				sbSQL.append(" AND fin.nstatus = " + qi.getStatus());
				sbSQL.append(" AND fin.nConfirmUserID !=" + qi.getUserID());
				//匹配条件	
				if(qi.getSDepositBillNo() != null && !qi.getSDepositBillNo().equals("")){
					sbSQL.append(" AND fin.SDEPOSITBILLNO = '" + qi.getSDepositBillNo() + "' ");
				}
				if(qi.getFixedInterestDeal()>0){
					sbSQL.append(" AND fin.FIXEDINTERESTDEAL = " + qi.getFixedInterestDeal());
					if(qi.getFixedInterestDeal() >1){
						if(qi.getLInterestToAccountID() != -1){
							sbSQL.append(" AND fin.FIXEDINTERESTTOACCOUNTID = " + qi.getLInterestToAccountID());
						}
					}
				}
				if(qi.getLFIXEDNEXTPERIOD()>0){
					sbSQL.append(" AND fin.FIXEDNEXTPERIOD = " + qi.getLFIXEDNEXTPERIOD());					
				}
				if (qi.getStrNewStartDate() != null && qi.getStrNewStartDate().trim().length() > 0)
				{
					sbSQL.append(" AND fin.FIXEDNEXTSTARTDATE >= ? \n");
					sbSQL.append(" AND fin.FIXEDNEXTSTARTDATE <= ? \n");
				}
				sbSQL.append(" AND fin.MAMOUNT = " + qi.getAmount());
				//sbSQL.append(" AND fin.DTEXECUTE = " + Timestamp.valueOf(qi.getExecuteDate().substring(0, 10)));
				if (qi.getExecuteDate() != null && qi.getExecuteDate().trim().length() > 0)
				{
					sbSQL.append(" AND fin.DTEXECUTE >= ? \n");
					sbSQL.append(" AND fin.DTEXECUTE <= ? \n");
				}
			}

			sbSQL.append("\n ORDER BY fin.dtconfirm ASC ,fin.NPAYERACCTID ");
			log4j.info("OBCapitalTransDao.queryMatchTrans():SQL==" + sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			int nIndex = 1;
			if (qi.getStrNewStartDate() != null && qi.getStrNewStartDate().trim().length() > 0)
			{
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qi.getStrNewStartDate().trim() + " 00:00:00"));
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qi.getStrNewStartDate().trim() + " 23:59:59"));
			}
			if (qi.getExecuteDate() != null && qi.getExecuteDate().trim().length() > 0)
			{
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qi.getExecuteDate().trim() + " 00:00:00"));
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qi.getExecuteDate().trim() + " 23:59:59"));
			}

			rs = ps.executeQuery();
			vReturn = setInfo(rs);

			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			log4j.info("query result : " + vReturn.size());
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
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return vReturn.size() > 0 ? vReturn : null;
	}
}