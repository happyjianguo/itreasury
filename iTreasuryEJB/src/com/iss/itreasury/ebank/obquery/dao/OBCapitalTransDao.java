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
	 * ���ݲ�ѯ�������࣬ƥ������ϲ�ѯ�������ʽ𻮲�ָ����Ϣ
	 * Create Date: 2003-8-13
	 * @param OBQueryInfo ��ѯ��������
	 * @return Collection  ���з�����������ָ����Ϣ
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

			//����ָ������
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
			//ҵ�񸴺�ƥ�䣬��ѯδ���˵ļ�¼���ǵ�¼��¼��ı���λ����ָ��
			if (qi.getOperationTypeID() == OBConstant.QueryOperationType.CHECK)
			{
				sbSQL.append(" AND fin.nstatus = " + qi.getStatus());
				sbSQL.append(" AND fin.nConfirmUserID !=" + qi.getUserID());
				//ƥ������	
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
	 * ���ݲ�ѯ�������࣬ƥ������ϲ�ѯ�������ʽ�����ָ����Ϣ
	 * Create Date: 2003-8-13
	 * @param OBQueryInfo ��ѯ��������
	 * @return Collection  ���з�����������ָ����Ϣ
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

			//����ָ������
			if (qi.getTransType() > 0)
			{
				sbSQL.append(" AND fin.ntranstype = " + OBConstant.SettInstrType.APPLYCAPITAL);
			}
			
			//ҵ�񸴺�ƥ�䣬��ѯδ���˵ļ�¼���ǵ�¼��¼��ı���λ����ָ��
			if (qi.getOperationTypeID() == OBConstant.QueryOperationType.CHECK)
			{
				sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.SAVE);
				sbSQL.append(" AND fin.nConfirmUserID !=" + qi.getUserID());
				//ƥ������	
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
		 * ���ݲ�ѯ�������࣬ƥ������ϲ�ѯ������������λ�ʽ𻮲�ָ����Ϣ
		 * Create Date: 2003-8-13
		 * @param OBQueryInfo ��ѯ��������
		 * @return Collection  ���з�����������ָ����Ϣ
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

			//����ָ������
			if (qi.getTransType() > 0)
			{
				sbSQL.append(" AND fin.ntranstype = " + OBConstant.getTransTypeByQueryType(qi.getTransType()));
			}
			//ҵ�񸴺�ƥ�䣬��ѯδ���˵ļ�¼���ǵ�¼��¼��ı���λ����ָ��
			if (qi.getOperationTypeID() == OBConstant.QueryOperationType.CHECK)
			{
				sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.SAVE);
				sbSQL.append(" AND fin.nConfirmUserID !=" + qi.getUserID());
				//ƥ������	
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
			
			

			//����ָ������
			if (qi.getTransType() > 0)
			{
				sbSQL.append(" AND fin.ntranstype = " + OBConstant.SettInstrType.OPENFIXDEPOSIT);
			}
			//ҵ�񸴺�ƥ�䣬��ѯδ���˵ļ�¼���ǵ�¼��¼��ı���λ����ָ��
			if (qi.getOperationTypeID() == OBConstant.QueryOperationType.CHECK)
			{
				sbSQL.append(" AND fin.nstatus = " + qi.getStatus());
				sbSQL.append(" AND fin.nConfirmUserID !=" + qi.getUserID());
				//ƥ������	
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
	 * ֪֧ͨȡ
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

			//����ָ������
			if (qi.getTransType() > 0)
			{
				sbSQL.append(" AND fin.ntranstype = " + OBConstant.getTransTypeByQueryType(qi.getTransType()));
			}
			//ҵ�񸴺�ƥ�䣬��ѯδ���˵ļ�¼���ǵ�¼��¼��ı���λ����ָ��
			if (qi.getOperationTypeID() == OBConstant.QueryOperationType.CHECK)
			{
				sbSQL.append(" AND fin.nstatus = " + qi.getStatus());
				sbSQL.append(" AND fin.nConfirmUserID !=" + qi.getUserID());
				//ƥ������	
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

			//����ָ������
			if (qi.getTransType() > 0)
			{
				sbSQL.append(" AND fin.ntranstype = " + OBConstant.getTransTypeByQueryType(qi.getTransType()));
			}
			//ҵ�񸴺�ƥ�䣬��ѯδ���˵ļ�¼���ǵ�¼��¼��ı���λ����ָ��
			if (qi.getOperationTypeID() == OBConstant.QueryOperationType.CHECK)
			{
				sbSQL.append(" AND fin.nstatus = " + qi.getStatus());
				sbSQL.append(" AND fin.nConfirmUserID !=" + qi.getUserID());
				//ƥ������	
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

			//����ָ������
			if (qi.getTransType() > 0)
			{
				sbSQL.append(" AND fin.ntranstype = " + OBConstant.getTransTypeByQueryType(qi.getTransType()));
			}
			//ҵ�񸴺�ƥ�䣬��ѯδ���˵ļ�¼���ǵ�¼��¼��ı���λ����ָ��
			if (qi.getOperationTypeID() == OBConstant.QueryOperationType.CHECK)
			{
				sbSQL.append(" AND fin.nstatus = " + qi.getStatus());
				sbSQL.append(" AND fin.nConfirmUserID !=" + qi.getUserID());
				//ƥ������	
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

			//����ָ������
			if (qi.getTransType() > 0)
			{
				sbSQL.append(" AND fin.ntranstype = " + OBConstant.getTransTypeByQueryType(qi.getTransType()));
			}
			//ҵ�񸴺�ƥ�䣬��ѯδ���˵ļ�¼���ǵ�¼��¼��ı���λ����ָ��
			if (qi.getOperationTypeID() == OBConstant.QueryOperationType.CHECK)
			{
				sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.SAVE);
				sbSQL.append(" AND fin.nConfirmUserID !=" + qi.getUserID());
				//ƥ������	
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

			//����ָ������
			if (qi.getTransType() > 0)
			{
				sbSQL.append(" AND fin.ntranstype = " + OBConstant.getTransTypeByQueryType(qi.getTransType()));
			}
			//ҵ�񸴺�ƥ�䣬��ѯδ���˵ļ�¼���ǵ�¼��¼��ı���λ����ָ��
			if (qi.getOperationTypeID() == OBConstant.QueryOperationType.CHECK)
			{
				sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.SAVE);
				sbSQL.append(" AND fin.nConfirmUserID !=" + qi.getUserID());
				//ƥ������	
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

			//����ָ������
			if (qi.getTransType() > 0)
			{
				sbSQL.append(" AND fin.ntranstype = " + OBConstant.getTransTypeByQueryType(qi.getTransType()));
			}
			//ҵ�񸴺�ƥ�䣬��ѯδ���˵ļ�¼���ǵ�¼��¼��ı���λ����ָ��
			if (qi.getOperationTypeID() == OBConstant.QueryOperationType.CHECK)
			{
				sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.SAVE);
				sbSQL.append(" AND fin.nConfirmUserID !=" + qi.getUserID());
				//ƥ������	
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
					info.setID(rs.getLong("ID")); // ָ�����
					info.setChildClientID(rs.getLong("nChildClientid")); //������λ
					info.setClientID(rs.getLong("NCLIENTID")); // �����ύ��λ
					info.setCurrencyID(rs.getLong("NCURRENCYID")); // ���ױ���
					info.setTransType(rs.getLong("NTRANSTYPE")); // ���Ͻ�������
					info.setPayerAcctID(rs.getLong("NPAYERACCTID")); // ����˻�ID				
					info.setPayeeAcctID(rs.getLong("NPAYEEACCTID")); //�տ�˻�ID
					info.setRemitType(rs.getLong("NREMITTYPE")); // ��ʽ
					info.setFixEdremark(rs.getString("fixedremark"));//��ע
					info.setIsFixContinue(rs.getLong("isFixContinue"));
					// �տ����
					info.setAmount(rs.getDouble("MAMOUNT")); // ���׽��
					info.setExecuteDate(rs.getTimestamp("DTEXECUTE")); // ִ����
					info.setNote(rs.getString("SNOTE")); // �����;/ժҪ
					info.setFixedDepositTime(rs.getLong("NFIXEDDEPOSITTIME")); // ���ڴ�����ޣ��£�
					info.setContractID(rs.getLong("NContractID")); // �����ͬID
					info.setLoanNoteID(rs.getLong("NLoanNoteID")); //�ſ�֪ͨ��ID
					info.setPayDate(rs.getTimestamp("DTPAY")); // ����ſ�����
					info.setDepositNo(rs.getString("SDEPOSITNO")); //���ڣ�֪ͨ�����ݺ�
					info.setSubAccountID(rs.getLong("nSubAccountID")); //���˻�ID
					info.setDepositStart(rs.getTimestamp("DTDEPOSITSTART")); //���ڣ�֪ͨ�������ʼ��
					info.setDepositRate(rs.getDouble("MDEPOSITRATE")); //���ڴ浥����
					info.setDepositAmount(rs.getDouble("MDEPOSITAMOUNT")); //�浥��������
					info.setDepositBalance(rs.getDouble("MDEPOSITBALANCE")); //�浥���
					info.setClearInterest(rs.getTimestamp("dtClearInterest")); //��Ϣ��
					info.setInterestPayeeAcctID(rs.getLong("NINTERESTPAYEEACCTID")); //��Ϣ�տ�˻�ID
					info.setInterestRemitType(rs.getLong("NINTERESTREMITTYPE")); //��Ϣ���ʽ
					info.setInterest(rs.getDouble("MINTEREST")); //Ӧ��������Ϣ
					info.setCompoundInterest(rs.getDouble("MCOMPOUNDINTEREST")); //Ӧ������
					info.setOverdueInterest(rs.getDouble("MOVERDUEINTEREST")); //Ӧ�����ڷ�Ϣ
					info.setSuretyFee(rs.getDouble("MSURETYFEE")); //Ӧ��������
					info.setCommission(rs.getDouble("MCOMMISSION")); //Ӧ��������
					info.setRealInterest(rs.getDouble("MREALINTEREST")); //ʵ��������Ϣ
					info.setRealCompoundInterest(rs.getDouble("MREALCOMPOUNDINTEREST")); //ʵ������
					info.setRealOverdueInterest(rs.getDouble("MREALOVERDUEINTEREST")); //ʵ�����ڷ�Ϣ
					info.setRealSuretyFee(rs.getDouble("MREALSURETYFEE")); //ʵ��������
					info.setRealCommission(rs.getDouble("MREALCOMMISSION")); //ʵ��������
					info.setStatus(rs.getLong("NSTATUS")); // ָ��״̬
					info.setConfirmDate(rs.getTimestamp("DTCONFIRM")); //ȷ������
					info.setConfirmUserID(rs.getLong("NCONFIRMUSERID")); //ȷ����ID	
					info.setConfirmUserName(rs.getString("confirmUserName")); // ȷ��������
					info.setCheckDate(rs.getTimestamp("DTCHECK")); //��������
					info.setCheckUserID(rs.getLong("NCHECKUSERID")); //������ID
					info.setCheckUserName(rs.getString("checkUserName")); // ����������
					info.setSignDate(rs.getTimestamp("DTSIGN")); //ǩ������
					info.setSignUserID(rs.getLong("NSIGNUSERID")); //ǩ����ID
					info.setSignUserName(rs.getString("signUserName")); // ǩ��������
					info.setNoticeDay(rs.getLong("nnoticeday")); //֪ͨ���Ʒ��
					info.setDeleteDate(rs.getTimestamp("DTDELETE")); //ɾ������
					info.setDeleteUserID(rs.getLong("NDELETEUSERID")); //ɾ����ID
					info.setDeleteUserName(rs.getString("delUserName")); // ɾ��������
					info.setDealDate(rs.getTimestamp("CPF_DTDEAL")); //CPF-��������
					info.setDealUserID(rs.getLong("CPF_NDEALUSERID")); //CPF-������ID
					info.setDealUserName(rs.getString("DealUserName")); // CPF-����������	
					info.setOfficeID(rs.getLong("CPF_NOFFICEID")); //CPF-Ĭ�ϰ��´�ID
					info.setTransNo(rs.getString("CPF_STRANSNO")); //CPF-���׺�
					info.setFinishDate(rs.getTimestamp("CPF_DTFINISH")); //CPF-���ʱ��
					info.setReject(rs.getString("CPF_SREJECT")); //CPF-�ܾ�ԭ��
					info.setDefaultTransType(rs.getLong("CPF_NDEFAULTTRANSTYPE")); // Ĭ�Ͻ�������
					info.setSubAccountID(rs.getLong("NSUBACCOUNTID")); //���˻�
					info.setClearInterest(rs.getTimestamp("DTCLEARINTEREST")); //��������	
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
					
					//added by mzh_fu 2007/05/21 ǩ��ֵ
					info.setSignatureValue(rs.getString("SIGNATUREVALUE"));
					info.setActionStatus(rs.getLong("ACTIONSTATUS"));

					//��ͬ����Լ��ſ�֪ͨ�����
					info.setLoanContractNo(NameRef.getContractNoByID(info.getContractID())); //�����ͬ���
					info.setLetOutCode(NameRef.getPayFormNoByID(info.getLoanNoteID())); //�ſ�֪ͨ�����
					//��ȡ�տ�͸������ϸ��Ϣ
					PayerOrPayeeInfo payerInfo = new PayerOrPayeeInfo();
					PayerOrPayeeInfo payeeInfo = new PayerOrPayeeInfo();
					PayerOrPayeeInfo interestpPayeeInfo = new PayerOrPayeeInfo();
					payerInfo = obFinanceInstrDao.getPayerOrPayeeInfoByID(info.getPayerAcctID(), info.getRemitType(), OBConstant.PayerOrPayee.PAYER);
					payeeInfo = obFinanceInstrDao.getPayerOrPayeeInfoByID(info.getPayeeAcctID(), info.getRemitType(), OBConstant.PayerOrPayee.PAYEE);
					interestpPayeeInfo = obFinanceInstrDao.getPayerOrPayeeInfoByID(info.getInterestPayeeAcctID(), info.getInterestRemitType(), OBConstant.PayerOrPayee.PAYEE);
					info.setPayerBankNo(payerInfo.getBankNo()); // ������б��				
					info.setPayerAcctNo(payerInfo.getAccountNo()); // ����˺�
					info.setPayerName(payerInfo.getAccountName()); // �������
					info.setPayeeName(payeeInfo.getAccountName()); // �տ����
					info.setPayeeBankNo(payeeInfo.getBankNo()); // �տ���б��				
					info.setPayeeAcctNo(payeeInfo.getAccountNo()); // �տ�˺�
					info.setPayeeProv(payeeInfo.getProv()); // ����ʡ
					info.setPayeeCity(payeeInfo.getCity()); // ������
					info.setPayeeBankName(payeeInfo.getBankName()); // ����������
					info.setSPayeeBankCNAPSNO(payeeInfo.getSPayeeBankCNAPSNO());
					info.setSPayeeBankExchangeNO(payeeInfo.getSPayeeBankExchangeNO());
					info.setSPayeeBankOrgNO(payeeInfo.getSPayeeBankOrgNO());
					info.setInterestPayeeName(interestpPayeeInfo.getAccountName()); //��Ϣ�տ����
					info.setInterestPayeeBankNo(interestpPayeeInfo.getBankNo()); // ��Ϣ�տ���б��				
					info.setInterestPayeeAcctNo(interestpPayeeInfo.getAccountNo()); // ��Ϣ�տ�˺�
					info.setInterestPayeeProv(interestpPayeeInfo.getProv()); // ��Ϣ����ʡ
					info.setInterestPayeeCity(interestpPayeeInfo.getCity()); // ��Ϣ������
					info.setInterestPayeeBankName(interestpPayeeInfo.getBankName()); // ��Ϣ����������
					info.setSInterestPayeeBankCNAPSNO(interestpPayeeInfo.getSPayeeBankCNAPSNO());
					info.setSInterestPayeeBankExchangeNO(interestpPayeeInfo.getSPayeeBankExchangeNO());
					info.setSInterestPayeeBankOrgNO(interestpPayeeInfo.getSPayeeBankOrgNO());
					// ����˻��ĵ�ǰ��� 
					AccountBalanceInfo abInfo = new AccountBalanceInfo();
					abInfo = obFinanceInstrDao.getCurrBalanceByAccountID(info.getPayerAcctID(), info.getCurrencyID(), info.getID());
					info.setCurrentBalance(abInfo.getCurrentBalance());
					info.setIsCycleAccount(abInfo.getIsCycleAccount());
					info.setMaxUsableAmount(abInfo.getMaxUsableAmount());
					info.setOverdraftAmount(abInfo.getOverdraftAmount());
					info.setUsableBalance(abInfo.getUsableBalance());
					


					//					������λ
					if (info.getChildClientID() > 0)
					{
						info.setChildClientName(NameRef.getClientNameByID(info.getChildClientID()));
						info.setChildClientNo(NameRef.getClientCodeByID(info.getChildClientID()));
					}

					if (info.getLoanNoteID() > 0)
					{
						//��õ�ǰ�ſ�֪ͨ���Ĵ������
						con = Database.getConnection();
						PreparedStatement ps = null;
						ResultSet rs1 = null;
						StringBuffer sbSQL = new StringBuffer();
						//���ݷſ�֪ͨ��id�õ����˻�ID
						log4j.info("=============���Ҵ�����ʼ=======");
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
						log4j.info("=============���Ҵ���������=======");
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
	 * ��������ָ���ѯ
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

			//����ָ������
			if (qi.getTransType() > 0)
			{
				sbSQL.append(
					" AND fin.ntranstype ='"
						+ OBConstant.SettInstrType.DRIVEFIXDEPOSIT
						+ "' ");
			}
			//ҵ�񸴺�ƥ�䣬��ѯδ���˵ļ�¼���ǵ�¼��¼��ı���λ����ָ��
			if (qi.getOperationTypeID() == OBConstant.QueryOperationType.CHECK)
			{
				sbSQL.append(" AND fin.nstatus = " + qi.getStatus());
				sbSQL.append(" AND fin.nConfirmUserID !=" + qi.getUserID());
				//ƥ������	
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
				
				//�����Ƿ��Զ������ƥ��
				sbSQL.append(" and fin.isautocontinue = "+qi.getIsAutoContinue());
				if(qi.getIsAutoContinue()==1)  //��ѡ�Զ�����
				{
					sbSQL.append(" and fin.autocontinuetype ="+qi.getAutocontinuetype());
					if(qi.getAutocontinuetype()==2)//�Զ���������Ϊ��������
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
	 * ����ת��
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

			//����ָ������
			if (qi.getTransType() > 0)
			{
				sbSQL.append(
					" AND fin.ntranstype ='"
						+ OBConstant.SettInstrType.CHANGEFIXDEPOSIT
						+ "' ");
			}
			//ҵ�񸴺�ƥ�䣬��ѯδ���˵ļ�¼���ǵ�¼��¼��ı���λ����ָ��
			if (qi.getOperationTypeID() == OBConstant.QueryOperationType.CHECK)
			{
				sbSQL.append(" AND fin.nstatus = " + qi.getStatus());
				sbSQL.append(" AND fin.nConfirmUserID !=" + qi.getUserID());
				//ƥ������	
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