/*
 * Created on 2004-9-10
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.settpaynotice.dao;
import java.sql.*;
import java.util.*;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.base.SettlementDAO;
import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.settlement.settcontract.dataentity.SettContractInfo;
import com.iss.itreasury.settlement.settcontract.dataentity.SettContractQueryInfo;

import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.loanpaynotice.dataentity.*;
import com.iss.itreasury.settlement.settpaynotice.dataentity.*;
import com.iss.system.dao.PageLoader;
/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SettPayNoticeDAO extends SettlementDAO
{
	protected Log4j log4j = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	public SettPayNoticeDAO()
	{
		//super("Loan_LoanForm");
		super("loan_payForm", true);
	}
	public SettPayNoticeDAO(Connection conn)
	{
		//super("Loan_LoanForm");
		super("loan_payForm", true, conn);
	}
	public Collection findByMultiOption(SettPayNoticeQueryInfo qInfo) throws ITreasuryDAOException
	{
		Collection c = null;
		String strSQL = "";
		long queryPurpose = qInfo.getQueryPurpose();
		try
		{
			/*-----------------init DAO --------------------*/
			initDAO();
			/*----------------end of init------------------*/
			strSQL = "select a.*,b.nBorrowClientID,b.sContractCode,b.mExamineAmount as mContractAmount from loan_payForm a,loan_contractForm b" + " where 1=1" + " and b.id=a.nContractID";
			if (queryPurpose == 1) //修改查找
			{
				strSQL += " and a.nStatusID=" + SETTConstant.SettPayNoticeStatus.SUBMIT;
				strSQL += " and a.nInputUserID=" + qInfo.getInputUserID();
			}
			else if (queryPurpose == 2) //复合查找
			{
				strSQL += " and ( (a.nStatusID=" + SETTConstant.SettPayNoticeStatus.SUBMIT + " and a.nInputUserID<>" + qInfo.getInputUserID() + ") or (a.nStatusID="
						+ SETTConstant.SettPayNoticeStatus.CHECK + " and a.NNEXTCHECKUSERID=" + qInfo.getInputUserID() + " ) )";
			}
			if (qInfo.getOfficeID() > 0)
			{
				strSQL += " and b.nOfficeID=" + qInfo.getOfficeID();
			}
			if (qInfo.getCurrencyID() > 0)
			{
				strSQL += " and b.nCurrencyID=" + qInfo.getCurrencyID();
			}
			if (qInfo.getClientID() > 0)
			{
				strSQL += " and b.NBORROWCLIENTID=" + qInfo.getClientID();
			}
			if (qInfo.getContractID() > 0)
			{
				strSQL += " and b.ID=" + qInfo.getContractID();
			}
			if (qInfo.getStartAmount() > 0) //授信额度开始
			{
				strSQL += " and b.mExamineAmount>=" + qInfo.getStartAmount();
			}
			if (qInfo.getEndAmount() > 0) //授信额度结束
			{
				strSQL += " and b.mExamineAmount<=" + qInfo.getEndAmount();
			}
			if (qInfo.getStatusID() > 0)
			{
				strSQL += " and a.nStatusID=" + qInfo.getStatusID();
			}
			//录入日期开始
			if (qInfo.getStartInputDate() != null)
			{
				strSQL += " and a.dtINPUTDATE>=to_date('" + qInfo.getStartInputDate();
				strSQL += "','yyyy-mm-dd')";
			}
			//录入日期结束
			if (qInfo.getEndInputDate() != null)
			{
				strSQL += " and a.dtINPUTDATE<=to_date('" + qInfo.getEndInputDate();
				strSQL += "','yyyy-mm-dd')";
			}
			////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
			int nIndex = 0;
			String orderParamString = qInfo.getOrderParamString();
			nIndex = orderParamString.indexOf(".");
			if (nIndex > 0)
			{
				if (orderParamString.substring(0, nIndex).toLowerCase().equals("loan_payForm"))
				{
					strSQL += " order by " + orderParamString.substring(nIndex + 1);
				}
			}
			else
			{
				strSQL += " order by a.ID";
			}
			if (qInfo.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strSQL += " desc";
			}
			log4j.info("sql= \n" + strSQL);
			prepareStatement(strSQL);
			executeQuery();
			c = getDataEntitiesFromResultSet(SettPayNoticeInfo.class);
			/*----------------finalize Dao-----------------*/
			finalizeDAO();
			/*----------------end of finalize---------------*/
		}
		catch (Exception e)
		{
			throw new ITreasuryDAOException("查询放款通知单出错", e);
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				throw e;
			}
		}
		return (c.size() > 0 ? c : null);
	}
	
	/**
	 * 通知单查询分页ld新增
	 * @param SettPayNoticeQueryInfo info
	 * @return PageLoader
	 * @throws ITreasuryDAOException
	 */
	public PageLoader getPayNoticeByMultioption(SettPayNoticeQueryInfo qInfo)throws ITreasuryDAOException {
		String m_sbSelect=" a.ID,a.SCODE CODE,a.NCONTRACTID CONTRACTID,a.DTOUTDATE OUTDATE,a.MAMOUNT AMOUNT,a.SCONSIGNACCOUNT CONSIGNACCOUNT,a.NBANKINTERESTID BANKINTERESTID," +
				"a.MCOMMISSIONRATE COMMISSIONRATE,a.MSURETYFEERATE SURETYFEERATE,a.DTSTART,a.DTEND end,a.SRECEIVECLIENTNAME RECEIVECLIENTNAME,a.SREMITBANK REMITBANK," +
				"a.SCOMPANYLEADER COMPANYLEADER,a.SHANDLINGPERSON HANDLINGPERSON,a.SDEPARTMENTLEADER DEPARTMENTLEADER,a.NSTATUSID STATUSID,a.NINPUTUSERID INPUTUSERID,a.DTINPUTDATE INPUTDATE," +
				"a.NNEXTCHECKUSERID NEXTCHECKUSERID,a.NSOURCETYPEID SOURCETYPEID,a.NGRANTCURRENTACCOUNTID GRANTCURRENTACCOUNTID,a.NGRANTTYPEID GRANTTYPEID," +
				"a.SREMITINPROVINCE REMITINPROVINCE,a.SREMITINCITY REMITINCITY,a.NDRAWNOTICEID DRAWNOTICEID,a.SLOANACCOUNT LOANACCOUNT,a.SCHECKPERSON CHECKPERSON," +
				"a.NACCOUNTBANKID ACCOUNTBANKID,a.MINTERESTRATE INTERESTRATE,a.SRECEIVEACCOUNT RECEIVEACCOUNT,a.NNEXTCHECKLEVEL NEXTCHECKLEVEL,a.MSTAIDADJUSTRATE STAIDADJUSTRATE," +
				"a.MADJUSTRATE ADJUSTRATE,a.NINTERESTTYPEID INTERESTTYPEID,a.NLIBORRATEID LIBORRATEID,a.NOFFICEID OFFICEID,a.NCURRENCYID CURRENCYID,a.NINTERVALNOTICENUM INTERVALNOTICENUM," +
				"a.RATEADJUSTDATE,b.nBorrowClientID BorrowClientID,b.sContractCode ContractCode,b.mExamineAmount as ContractAmount ";
		String m_sbFrom=" loan_payForm a,loan_contractForm b ";
		String m_sbWhere=" 1=1 and b.id=a.nContractID ";
		
		long queryPurpose = qInfo.getQueryPurpose();
		if (queryPurpose == 1) //修改查找
		{
			m_sbWhere += " and a.nStatusID="+ SETTConstant.SettPayNoticeStatus.CHECK;
			m_sbWhere += " and a.nInputUserID=" + qInfo.getInputUserID();
			
		}
		else if (queryPurpose == 2) //复合查找
			{
			m_sbWhere += " and ( (a.nStatusID=" + SETTConstant.SettPayNoticeStatus.SUBMIT + " and a.nInputUserID<>" + qInfo.getInputUserID() + ") or (a.nStatusID="
					+ SETTConstant.SettPayNoticeStatus.CHECK + " and a.NNEXTCHECKUSERID=" + qInfo.getInputUserID() + " ) )";
		}
		if (qInfo.getClientID() > 0)
		{
			m_sbWhere += " and b.NBORROWCLIENTID=" + qInfo.getClientID();
		}
		if (qInfo.getOfficeID() > 0)
		{
			m_sbWhere += " and b.nOfficeID=" + qInfo.getOfficeID();
		}
		if (qInfo.getCurrencyID() > 0)
		{
			m_sbWhere += " and b.nCurrencyID=" + qInfo.getCurrencyID();
		}
		if (qInfo.getContractID() > 0)
		{
			m_sbWhere += " and b.ID=" + qInfo.getContractID();
		}
		if (qInfo.getStartAmount() > 0) //授信额度开始
		{
			m_sbWhere += " and b.mExamineAmount>=" + qInfo.getStartAmount();
		}
		if (qInfo.getEndAmount() > 0) //授信额度结束
		{
			m_sbWhere += " and b.mExamineAmount<=" + qInfo.getEndAmount();
		}
		//录入日期开始
		if (qInfo.getStartInputDate() != null)
		{
			m_sbWhere += " and a.dtINPUTDATE>=to_date('" + qInfo.getStartInputDate();
			m_sbWhere += "','yyyy-mm-dd')";
		}
		//录入日期结束
		if (qInfo.getEndInputDate() != null)
		{
			m_sbWhere += " and a.dtINPUTDATE<=to_date('" + qInfo.getEndInputDate();
			m_sbWhere += "','yyyy-mm-dd')";
		}
		if (qInfo.getStatusID() > 0)
		{
			m_sbWhere += " and a.nStatusID" + qInfo.getStatusID();
		}
		
		String m_sbOrderBy="";
		int nIndex = 0;
		String orderParamString = qInfo.getOrderParamString();
		nIndex = orderParamString.indexOf(".");
		if (nIndex > 0)
		{
			if (orderParamString.substring(0, nIndex).toLowerCase().equals("loan_payForm"))
			{
				m_sbOrderBy +=" order by "+ orderParamString.substring(nIndex + 1);
			}
		}
		else
		{
			m_sbOrderBy += " order by a.ID ";
		}
		if (qInfo.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC)
		{
			m_sbOrderBy += " desc";
		}
		
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom,
			m_sbSelect,
			m_sbWhere,
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.settpaynotice.dataentity.SettPayNoticeInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy + " ");
		return pageLoader;
	}
	
	/**
	 * 得到执行利率，参数lLoanPayNoticeID
	 * Create Date: 2003-10-15
	 * @param lLoanPayNoticeID 放款通知单ID
	 * @param tsDate 时间，如传入为NULL值或空串则默认为当前日期。
	 * @return double 执行利率
	 * @exception Exception
	 */
	public double getLatelyRate(long lLoanPayNoticeID, Timestamp tsDate) throws Exception
	{
		double dResult = 100;
		double dRate = 100;
		double dAdjustRate = 100;
		double dStaidAdjustRate = 100;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		try
		{
			con = Database.getConnection();
			if (tsDate == null || tsDate.equals(""))
			{
				tsDate = DataFormat.getDateTime(con);
			}
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT b.mrate, a.MSTAIDADJUSTRATE, a.MADJUSTRATE FROM LOAN_RATEADJUSTPAYDETAIL a ,loan_interestRate b  ");
			sbSQL.append(" WHERE a.NLOANPAYNOTICEID = ?  ");
			sbSQL.append(" AND TO_CHAR(a.dtStartDate,'yyyymmdd')<= TO_CHAR(?,'yyyymmdd')");
			sbSQL.append(" and a.nBankInterestID = b.id(+) ");
			sbSQL.append(" ORDER BY  a.dtStartDate DESC ");
			System.out.println("sbSQl=" + sbSQL.toString());
			//log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lLoanPayNoticeID);
			ps.setTimestamp(2, tsDate);
			rs = ps.executeQuery();
			if (rs.next())
			{
				dRate = rs.getDouble("mRate");
				dAdjustRate = rs.getDouble("MADJUSTRATE");
				dStaidAdjustRate = rs.getDouble("MSTAIDADJUSTRATE");
				dResult = (dRate * (1 + dAdjustRate / 100)) + dStaidAdjustRate;
			}
			else if (lLoanPayNoticeID > 0)
			{
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				sbSQL.setLength(0);
				sbSQL.append(" SELECT decode( b.mRate,null,a.mInterestRate,0,a.mInterestRate ,b.mRate) mRate ");
				sbSQL.append(" FROM loan_payform a,loan_interestRate b ");
				sbSQL.append(" WHERE a.nBankInterestID = b.id(+) ");
				sbSQL.append(" AND a.id = ?");
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lLoanPayNoticeID);
				rs = ps.executeQuery();
				if (rs.next())
				{
					dRate = rs.getDouble("mRate");
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				sbSQL.setLength(0);
				sbSQL.append(" SELECT nvl(a.MADJUSTRATE,0) MADJUSTRATE, nvl(a.MSTAIDADJUSTRATE,0) MSTAIDADJUSTRATE");
				sbSQL.append(" FROM loan_payform a,loan_contractform b ");
				sbSQL.append(" WHERE a.nContractID = b.ID ");
				sbSQL.append(" AND a.id = ?");
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lLoanPayNoticeID);
				rs = ps.executeQuery();
				if (rs.next())
				{
					dAdjustRate = rs.getDouble("MADJUSTRATE");
					dStaidAdjustRate = rs.getDouble("MSTAIDADJUSTRATE");
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				dResult = (dRate * (1 + dAdjustRate / 100)) + dStaidAdjustRate;
			}
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
			/*
			 * 放款通知单的浮动利率直接从放款通知单表loan_payform中取得，不从合同表中取
			 * 通知单的执行利率算法：贷款执行利率＝基准利率*(1(＋/－)浮动比例)＋/-固定浮动点
			 * modify by yanliu 2004-06-22
			 */
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
		return dResult;
	}
	public Collection getRateAdjustInfo(long payNoticeID, Timestamp tsDate) throws Exception
	{
		Vector v = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		try
		{
			conn = Database.getConnection();
			if (tsDate == null || tsDate.equals(""))
			{
				tsDate = DataFormat.getDateTime(conn);
			}
			strSQL = " SELECT b.mrate, a.dtStartDate ,a.MSTAIDADJUSTRATE, a.MADJUSTRATE FROM LOAN_RATEADJUSTPAYDETAIL a ,loan_interestRate b ,LOAN_RATEADJUSTPAYCONDITION c "
					+ " WHERE a.NLOANPAYNOTICEID = ?  " + " AND TO_CHAR(c.dtInputDate,'yyyymmdd')<= TO_CHAR(?,'yyyymmdd')" + " and a.nBankInterestID = b.id(+) "
					+ " and a.NADJUSTCONDITIONID = c.id(+) " + " ORDER BY  a.dtStartDate DESC ";
			log4j.info(strSQL.toString());
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, payNoticeID);
			ps.setTimestamp(2, tsDate);
			rs = ps.executeQuery();
			while (rs.next())
			{
				PayNoticeAdjustInfo rateInfo = new PayNoticeAdjustInfo();
				double dRate = rs.getDouble("mRate");
				double dAdjustRate = rs.getDouble("MADJUSTRATE");
				double dStaidAdjustRate = rs.getDouble("MSTAIDADJUSTRATE");
				double dResult = (dRate * (1 + dAdjustRate / 100)) + dStaidAdjustRate;
				rateInfo.setInterestRate(dResult);
				rateInfo.setStartDate(rs.getTimestamp("dtStartDate"));
				log4j.print("--------------");
				log4j.print(rateInfo.getStartDate() + "====" + rateInfo.getInterestRate());
				log4j.print("--------------");
				v.add(rateInfo);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw e;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
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
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return v;
	}
	/**
	 * 得到放款通知单的指定费率的值
	 * Create Date: 2003-11-6
	 * @param lRateTypeID 费率类型（利率、代办费等）
	 * @param lLoanPayNoticeID 放款通知单ID
	 * @param tsDate 时间，如传入为NULL值或空串则默认为当前日期。
	 * @return PayNoticeRateInfo 放款通知单利率对象
	 * @exception Exception
	 */
	public PayNoticeRateInfo getRateValue(long lRateTypeID, long lLoanPayNoticeID, Timestamp tsDate) throws Exception
	{
		double dResult = 100;
		double dRate = 100;
		double dAdjustRate = 100;
		double dStaidAdjustRate = 100;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		PayNoticeRateInfo oRateInfo = null;
		StringBuffer sbSQL = new StringBuffer();
		;
		try
		{
			con = Database.getConnection();
			switch ((int) lRateTypeID)
			{
				//利率
				case (int) Constant.RateType.INTEREST :
					/*
					 * 目前暂时没有考虑外汇
					 * 如果系统支持外汇，可以在此处首先查询出放款通知单用银行利率还是LIBOR利率
					 */
					if (tsDate == null || tsDate.equals(""))
					{
						tsDate = DataFormat.getDateTime(con);
					}
					sbSQL.setLength(0);
					sbSQL.append(" SELECT b.mrate, a.MSTAIDADJUSTRATE, a.MADJUSTRATE FROM LOAN_RATEADJUSTPAYDETAIL a ,loan_interestRate b  ");
					sbSQL.append(" WHERE a.NLOANPAYNOTICEID = ?  ");
					sbSQL.append(" AND TO_CHAR(a.dtStartDate,'yyyymmdd')<= TO_CHAR(?,'yyyymmdd')");
					sbSQL.append(" and a.nBankInterestID = b.id(+) ");
					sbSQL.append(" ORDER BY  a.dtStartDate DESC ");
					System.out.println("sbSQl=" + sbSQL.toString());
					log4j.info(sbSQL.toString());
					ps = con.prepareStatement(sbSQL.toString());
					ps.setLong(1, lLoanPayNoticeID);
					ps.setTimestamp(2, tsDate);
					rs = ps.executeQuery();
					if (rs.next())
					{
						dRate = rs.getDouble("mRate");
						dAdjustRate = rs.getDouble("MADJUSTRATE");
						dStaidAdjustRate = rs.getDouble("MSTAIDADJUSTRATE");
						dResult = (dRate * (1 + dAdjustRate / 100)) + dStaidAdjustRate;
					}
					else if (lLoanPayNoticeID > 0)
					{
						rs.close();
						rs = null;
						ps.close();
						ps = null;
						sbSQL.setLength(0);
						sbSQL.append(" SELECT decode( b.mRate,null,a.mInterestRate ,b.mRate) mRate ");
						sbSQL.append(" FROM loan_payform a,loan_interestRate b ");
						sbSQL.append(" WHERE a.nBankInterestID = b.id(+) ");
						sbSQL.append(" AND a.id = ?");
						ps = con.prepareStatement(sbSQL.toString());
						ps.setLong(1, lLoanPayNoticeID);
						rs = ps.executeQuery();
						if (rs.next())
						{
							dResult = rs.getDouble("mRate");
						}
						rs.close();
						rs = null;
						ps.close();
						ps = null;
						sbSQL.setLength(0);
						sbSQL.append(" SELECT nvl(a.MADJUSTRATE,0) MADJUSTRATE, nvl(a.MSTAIDADJUSTRATE,0) MSTAIDADJUSTRATE ");
						sbSQL.append(" FROM loan_payform a,loan_contractform b ");
						sbSQL.append(" WHERE a.nContractID = b.ID ");
						sbSQL.append(" AND a.id = ?");
						ps = con.prepareStatement(sbSQL.toString());
						ps.setLong(1, lLoanPayNoticeID);
						rs = ps.executeQuery();
						if (rs.next())
						{
							dResult = (dResult * (1 + rs.getDouble("MADJUSTRATE") / 100)) + rs.getDouble("MSTAIDADJUSTRATE");
						}
					}
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
					oRateInfo = new PayNoticeRateInfo();
					oRateInfo.setInterestRate(dResult);
					oRateInfo.setRateStyle(Constant.RateType.getRateStyle(Constant.RateType.INTEREST));
					oRateInfo.setRateType(Constant.RateType.INTEREST);
					break;
				case (int) Constant.RateType.AGENT :
					break;
				case (int) Constant.RateType.ASSURE :
					break;
				case (int) Constant.RateType.CHARGE :
					sbSQL.setLength(0);
					sbSQL.append(" SELECT a.mChargeRate FROM loan_contractform a,loan_payform b ");
					sbSQL.append(" where a.ID = b.nContractID and b.ID = ? ");
					System.out.println("sbSQl=" + sbSQL.toString());
					log4j.info(sbSQL.toString());
					ps = con.prepareStatement(sbSQL.toString());
					ps.setLong(1, lLoanPayNoticeID);
					rs = ps.executeQuery();
					if (rs.next())
					{
						dResult = rs.getDouble("mChargeRate");
					}
					rs.close();
					rs = null;
					ps.close();
					ps = null;
					oRateInfo = new PayNoticeRateInfo();
					oRateInfo.setInterestRate(dResult);
					oRateInfo.setRateStyle(Constant.RateType.getRateStyle(Constant.RateType.CHARGE));
					oRateInfo.setRateType(Constant.RateType.CHARGE);
					break;
				case (int) Constant.RateType.FINE :
					break;
				default :
					break;
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
		return oRateInfo;
	}
	//获得放款通知单编号
	public long getPayNoticeID() throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		String strSQL = null;
		long ret = -1;
		try
		{
			con = Database.getConnection();
			strSQL = " select Seq_Loan_PayFrm_DiscountCred.Nextval from dual ";
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				ret = rs.getLong(1);
			}
			rs.close();
			ps.close();
		}
		catch (Exception e)
		{
			log4j.error("catch a error");
			throw e;
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
				throw ex;
			}
		}
		return ret;
	}
	protected long geSequenceID() throws ITreasuryDAOException
	{
		/**
		 * 此方法只能在DAO中被调用，即不重新创建数据库资源，因此也不需要 关闭数据库资源
		 */
		long id = -1;
		String strSeqName = "SEQ_" + strTableName;
		String sql = "SELECT Seq_Loan_PayFrm_DiscountCred.nextval nextid from dual";
		//prepareStatement(sql);
		PreparedStatement localPS = null;
		ResultSet localRS = null;
		try
		{ //内部维护RS和PS，否则将会产生冲突,但Connection使用同一个
			localPS = transConn.prepareStatement(sql);
			localRS = localPS.executeQuery();
			if (localRS.next())
			{
				id = localRS.getLong("nextid");
			}
			if (localRS != null)
				localRS.close();
			if (localPS != null)
				localPS.close();
		}
		catch (SQLException e)
		{
			new ITreasuryDAOException("数据库获取ID产异常", e);
		}
		return id;
	}
	//获得放款通知单编号
	public String getPayNoticeCode(long lContractID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		String strSQL = null;
		String sTemp = "";
		long lCode = 0;
		String sReturnCode = "";
		try
		{
			con = Database.getConnection();
			strSQL = "select count(*) from loan_payform  where NCONTRACTID = ?";
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lContractID);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lCode = rs.getLong(1) + 1;
			}
			rs.close();
			ps.close();
			//凑齐三位
			if (lCode < 10)
			{
				sReturnCode = "00" + lCode;
			}
			else if (lCode < 100 && lCode >= 10)
			{
				sReturnCode = "0" + lCode;
			}
			if (lCode >= 100)
			{
				sReturnCode = "" + lCode;
			}
		}
		catch (Exception e)
		{
			log4j.error("catch a error");
			throw e;
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
				throw ex;
			}
		}
		return sReturnCode;
	}
}
