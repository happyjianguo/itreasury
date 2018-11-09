/*
 * Created on 2004-2-4
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.ebank.obaccountinfo.dao;
import java.sql.Connection;
import com.iss.itreasury.util.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.ebank.obaccountinfo.dataentity.OBAccountBalanceCurrentInfo;
import com.iss.itreasury.ebank.obaccountinfo.dataentity.OBAccountBalanceFixedInfo;
import com.iss.itreasury.ebank.obaccountinfo.dataentity.OBAccountBalanceLoanInfo;
import com.iss.itreasury.ebank.obaccountinfo.dataentity.OBAccountQueryInfo;
import com.iss.itreasury.ebank.obaccountinfo.dataentity.OBAccountResultInfo;
import com.iss.itreasury.loan.contract.dataentity.RateInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.loan.util.LOANNameRef;
import com.iss.itreasury.settlement.account.dao.Sett_AccountTypeDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountTypeInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransAccountDetailWhereInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dao.Sett_TransFixedWithDrawDAO;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedDrawInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.settlement.util.SETTConstant.AccountCheckStatus;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

/**
 * @author yfsu
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class OBAccountBalanceQueryDao extends SettlementDAO
{
	/** Logger */
	protected Log4j log = new Log4j(Constant.ModuleType.EBANK, this);

	//查询活期账户信息
	public Collection seekCurrentBalace(long lClientID, long lCurrencyID, long lOfficeID) throws Exception
	{
		OBAccountBalanceCurrentInfo info = null;
		Collection cln = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{

			conn = this.getConnection();
			String sql =
				" select a.naccounttypeid, "
					+ " a.saccountno, "
					+ " a.id AccountID,"
					+ " round(b.mbalance,2) mbalance,  "
					+ " round(b.ac_mcapitallimitamount,2) ac_mcapitallimitamount"
					+ " from sett_account a,sett_subaccount b "
					+ " where a.id=b.naccountid "
					+ " and a.NOFFICEID = "
					+ lOfficeID
					+ "  "
					+ " and a.NCURRENCYID ="
					+ lCurrencyID
					+ "  "
					+ " and a.NCLIENTID = "
					+ lClientID
					+ " "
					+ " and a.nStatusID != "
					+ SETTConstant.AccountStatus.CLOSE
					+ " and a.nCheckStatusid = "
					+ SETTConstant.AccountCheckStatus.CHECK
					+ " and a.naccounttypeid in  "
					//modify by kenny(处理账户类型编码多办事处、多币种问题)(2007-07-03)
					+ " (select distinct c.id from sett_accounttype c where c.naccountgroupid="+
						SETTConstant.AccountGroupType.CURRENT+" and c.nstatusId=1 and c.officeId="+lOfficeID+" and c.currencyId="+lCurrencyID+")"
					//+ " and a.naccounttypeid not in(4,5,16,17) "
					//+ " and a.naccounttypeid<>97 "
					+ " order by a.naccounttypeid,a.saccountno ";

			log.info("SQL=====" + sql);

			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			cln = this.addCurrentOrConsignResultInfoIntoCln(rs, true);
		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}

		return cln;
	}
//	查询活期账户信息
	public Collection seekCurrentBalace2(long lClientID, long lCurrencyID, long lOfficeID) throws Exception
	{
		OBAccountBalanceCurrentInfo info = null;
		Collection cln = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{

			conn = this.getConnection();
			String sql =
				" select a.naccounttypeid, "
					+ " a.saccountno, "
					+ " a.id AccountID,"
					+ " round(b.mbalance,2) mbalance,  "
					+ " round(b.ac_mcapitallimitamount,2) ac_mcapitallimitamount"
					+ " from sett_account a,sett_subaccount b "
					+ " where a.id=b.naccountid "
					+ " and a.NOFFICEID = "
					+ lOfficeID
					+ "  "
					+ " and a.NCURRENCYID ="
					+ lCurrencyID
					+ "  "
					+ " and a.NCLIENTID = "
					+ lClientID
					+ " "
					+ " and a.nStatusID != "
					+ SETTConstant.AccountStatus.CLOSE
					+ " and a.nCheckStatusid = "
					+ SETTConstant.AccountCheckStatus.CHECK
					+ " and a.naccounttypeid in  "
					//modify by kenny(处理账户类型编码多办事处、多币种问题)(2007-07-03)
					+ " (select distinct c.id from sett_accounttype c where c.naccountgroupid="+
						SETTConstant.AccountGroupType.CURRENT+" and c.nstatusId=1 and c.officeId="+lOfficeID+" and c.currencyId="+lCurrencyID+")"
					//+ " and a.naccounttypeid not in(4,5,16,17) "
					//+ " and a.naccounttypeid<>97 "
					+ " order by a.naccounttypeid,a.saccountno ";

			log.info("SQL=====活期" + sql);

			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			cln = this.addCurrentOrConsignResultInfoIntoCln2(rs, true);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}

		return cln;
	}
	public Collection seekCurrentBalace2(long lClientID, long lCurrencyID, long lOfficeID,long lUserID) throws Exception
	{
		OBAccountBalanceCurrentInfo info = null;
		Collection cln = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{

			conn = this.getConnection();
			String sql =
				" select a.naccounttypeid, "
					+ " a.saccountno, "
					+ " a.id AccountID,"
					+ " round(b.mbalance,2) mbalance,  "
					+ " round(b.ac_mcapitallimitamount,2) ac_mcapitallimitamount"
					+ " from sett_account a,sett_subaccount b,"
					+ " (select a.nClientID,a.saccountno,ai.id,ai.sname,ai.naccounttypeid "
					+ " from OB_AccountOwnedByUserQuery a, Sett_Account ai "
					+ " where ai.nStatusID=1 "
					+ " and a.saccountno=ai.saccountno "
					+ " and a.nUserID="+lUserID
					+ " and ai.ncurrencyid="+lCurrencyID
					+ " order by a.saccountno) al "
					+ " where a.id=b.naccountid "
					//+ " and a.NOFFICEID = "
					//+ lOfficeID
					+ "  "
					+ " and a.NCURRENCYID ="
					+ lCurrencyID
					+ "  "
					+ " and a.NCLIENTID = "
					+ lClientID
					+ " "
					+ " and a.nStatusID != "
					+ SETTConstant.AccountStatus.CLOSE
					+ " and a.nCheckStatusid = "
					+ SETTConstant.AccountCheckStatus.CHECK
					+ " and a.saccountno=al.saccountno "
					+ " and a.naccounttypeid in  "
					
					//modify by kenny(处理账户类型编码多办事处、多币种问题)(2007-07-03)
					+ " (select distinct c.id from sett_accounttype c where c.naccountgroupid="+
						SETTConstant.AccountGroupType.CURRENT+" and c.nstatusId=1 and c.currencyId="+lCurrencyID+")"
					//+ " and a.naccounttypeid not in(4,5,16,17) "
					//+ " and a.naccounttypeid<>97 "
					+ " order by a.naccounttypeid,a.saccountno ";
			
			
			System.out.println("c.currencyId="+lCurrencyID);
			log.info("SQL=====活期" + sql);

			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			cln = this.addCurrentOrConsignResultInfoIntoCln2(rs, true);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}

		return cln;
	}

	//查询委托账户信息
	public Collection seekConsignBalace(long lClientID, long lCurrencyID, long lOfficeID) throws Exception
	{
		OBAccountBalanceCurrentInfo info = null;
		Collection cln = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = this.getConnection();
			String sql =
				" select a.naccounttypeid, "
					+ " a.saccountno,"
					+ " a.id AccountID,"
					+ " round(b.mbalance,2) mbalance, "
					+ " round(b.ac_mcapitallimitamount,2) ac_mcapitallimitamount"
					+ " from sett_account a,sett_subaccount b "
					+ " where a.id=b.naccountid "
					+ " and a.NOFFICEID = "
					+ lOfficeID
					+ "  "
					+ " and a.NCURRENCYID ="
					+ lCurrencyID
					+ "  "
					+ " and a.NCLIENTID = "
					+ lClientID
					+ " "
					+ " and a.naccounttypeid in "
					//modify by kenny(处理账户类型编码多办事处、多币种问题)(2007-07-03)
					+ " (select distinct c.id from sett_accounttype c where c.naccountgroupid="+
						SETTConstant.AccountGroupType.CURRENT+" and c.nstatusId=1 and c.officeId="+lOfficeID+" and c.currencyId="+lCurrencyID+")"
					//+ " and a.naccounttypeid not in(4,5,16,17) "
					+ " order by a.naccounttypeid,a.saccountno ";

			log.info("consign SQL=====" + sql);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			cln = this.addCurrentOrConsignResultInfoIntoCln(rs, false);
		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}

		return cln;
	}
	public double findEarlyBalance(long nAccountID, Timestamp date) throws Exception
	{
		double mEarlyBalance = 0.0;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{

			String strSQL =
				"select nvl(sum(mbalance),0) as EarlyBalance from sett_dailyaccountbalance where naccountid="
					+ nAccountID
					+ " and dtDate= to_date('"
					+ DataFormat.formatDate(date)
					+ "','yyyy-mm-dd') ";
			log.info("Sql=" + strSQL);
			ps = con.prepareStatement(strSQL);
			//ps.setLong(1,nAccountID);	
			//ps.setTimestamp(2,date);		
			rs = ps.executeQuery();
			if (rs.next())
			{
				mEarlyBalance = rs.getDouble("EarlyBalance");
			}
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
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
		}
		return mEarlyBalance;
	}
	/**
	 * 查询活期账户明细
	 * @return
	 * @throws Exception
	 */
	public Collection findByCurrentAccountID(OBAccountQueryInfo info) throws Exception
	{
		ArrayList arrResult = new ArrayList();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			//String strSQL ="";
			StringBuffer buffer = new StringBuffer();

			buffer.append("select  \n");
			buffer.append("a.id as AccountID,  \n");
			buffer.append("d.stransno as TransNo,  \n");
			buffer.append("d.ntransactiontypeid as TransactionTypeID,  \n");
			buffer.append("d.sabstract as Abstract,  \n");
			buffer.append("d.NGROUP as SerialNo,  \n");
			buffer.append("s.mbalance as Banlance,  \n");
			buffer.append("d.noppaccountid OppAccountID,  \n");
			buffer.append("d.sbankchequeno as BankChequeNo,  \n");
			buffer.append("d.mamount as Amount,  \n");
			buffer.append("d.dtexecute as ExecuteDate,  \n");
			buffer.append("d.ntransdirection as TransDirection  \n");

			buffer.append("from \n");
			buffer.append("SETT_TRANSACCOUNTDETAIL d,sett_subAccount s,sett_account a \n");
			buffer.append("where  \n");
			buffer.append("d.nsubaccountid = s.id \n");
			buffer.append("and d.ntransaccountid = a.id \n");
			buffer.append("and d.nStatusID != 0 \n");
			buffer.append("and a.id = ? \n");
			//buffer.append("and a.NOFFICEID = ? \n");
			buffer.append("and a.NCURRENCYID = ? \n");
			buffer.append("and d.dtexecute=? \n");
			buffer.append("order by d.stransno asc \n");

			ps = con.prepareStatement(buffer.toString());
			log.info(buffer.toString());

			int index = 1;
			ps.setLong(index++, info.getAccountID());
			//ps.setLong(index++, info.getOfficeID());
			ps.setLong(index++, info.getCurrencyID());
			ps.setTimestamp(index++, info.getExecuteDate());

			rs = ps.executeQuery();
			OBAccountResultInfo resultInfo = null;
			while (rs.next())
			{
				resultInfo = new OBAccountResultInfo();

				resultInfo.setAccountID(rs.getLong("AccountID"));
				resultInfo.setTransNo(rs.getString("TransNo"));
				resultInfo.setTransactionTypeID(rs.getLong("TransActionTypeID"));
				resultInfo.setAccountNo(NameRef.getAccountNoByID(rs.getLong("AccountID")));
				//resultInfo.setDepositNo(rs.getString("DepositNo"));				
				resultInfo.setAmount(rs.getDouble("Amount"));
				//resultInfo.setStartDate(rs.getTimestamp("Startdate"));
				resultInfo.setExecuteDate(rs.getTimestamp("ExecuteDate"));
				resultInfo.setAbstract(rs.getString("Abstract"));
				resultInfo.setBalance(rs.getDouble("Banlance"));
				resultInfo.setOppAccountID(rs.getLong("OppAccountID"));
				resultInfo.setTransDirection(rs.getLong("TransDirection"));
				resultInfo.setBillNo(rs.getString("BankChequeNo"));
				resultInfo.setSerialNo(rs.getLong("SerialNo"));
				resultInfo.setEarlyBanlance(findEarlyBalance(info.getAccountID(), UtilOperation.getNextNDay(info.getExecuteDate(), -1)));

				arrResult.add(resultInfo);
			}
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
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}

		}
		return arrResult;

	}
	/**
	 * 查询贷款合同账户明细
	 * @return
	 * @throws Exception
	 */
	public Collection findByLoanAccountID(OBAccountQueryInfo info) throws Exception
	{
		ArrayList arrResult = new ArrayList();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			//String strSQL ="";
			StringBuffer buffer = new StringBuffer();

			buffer.append("select distinct  \n");
			buffer.append("a.id as AccountID,  \n");
			buffer.append("d.stransno as TransNo,  \n");
			buffer.append("d.ntransactiontypeid as TransactionTypeID,  \n");
			buffer.append("d.sabstract as Abstract,  \n");
			buffer.append("s.mbalance as Banlance,  \n");
			buffer.append("d.mamount as Amount,  \n");
			buffer.append("d.dtexecute as ExecuteDate,  \n");
			buffer.append("SETT_TRANSREPAYMENTLOAN.MREALINTEREST Interest,  \n");
			buffer.append("d.ntransdirection as TransDirection  \n");

			buffer.append("from \n");
			buffer.append("SETT_TRANSACCOUNTDETAIL d,sett_subAccount s,sett_account a,loan_PayForm payform, loan_ContractForm contractform, SETT_TRANSREPAYMENTLOAN \n");
			buffer.append("where  \n");
			buffer.append("d.nsubaccountid = s.id \n");
			buffer.append("and d.ntransaccountid = a.id \n");
			buffer.append("and  s.AL_nLoanNoteID = payform.ID \n");
			buffer.append("and payform.nContractID = contractform.ID \n");
			buffer.append("and d.stransno=SETT_TRANSREPAYMENTLOAN.stransno(+) \n");
			buffer.append("and d.nStatusID != 0 \n");
			buffer.append("and contractform.ID = ? \n");
			buffer.append("and a.NOFFICEID = ? \n");
			buffer.append("and a.NCURRENCYID = ? \n");
			buffer.append("and d.dtexecute=? \n");
			buffer.append("order by d.stransno asc \n");

			ps = con.prepareStatement(buffer.toString());
			log.info(buffer.toString());

			int index = 1;
			ps.setLong(index++, info.getContractID());
			ps.setLong(index++, info.getOfficeID());
			ps.setLong(index++, info.getCurrencyID());
			ps.setTimestamp(index++, info.getExecuteDate());

			rs = ps.executeQuery();
			while (rs.next())
			{
				OBAccountResultInfo resultInfo = new OBAccountResultInfo();

				resultInfo.setAccountID(rs.getLong("AccountID"));
				resultInfo.setTransNo(rs.getString("TransNo"));
				resultInfo.setTransactionTypeID(rs.getLong("TransActionTypeID"));
				resultInfo.setAccountNo(NameRef.getAccountNoByID(rs.getLong("AccountID")));
				//resultInfo.setDepositNo(rs.getString("DepositNo"));				
				resultInfo.setAmount(rs.getDouble("Amount"));
				//resultInfo.setStartDate(rs.getTimestamp("Startdate"));
				resultInfo.setExecuteDate(rs.getTimestamp("ExecuteDate"));
				resultInfo.setAbstract(rs.getString("Abstract"));
				resultInfo.setBalance(rs.getDouble("Banlance"));
				//resultInfo.setOppAccountID(rs.getLong("OppAccountID"));	
				resultInfo.setTransDirection(rs.getLong("TransDirection"));
				//resultInfo.setBillNo(rs.getString("BankChequeNo"));				

				arrResult.add(resultInfo);
			}
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
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}

		}
		return arrResult;

	}
	/**
	 * 查询通知账户明细
	 * @return
	 * @throws Exception
	 */
	public Collection findByNoticeAccountID(OBAccountQueryInfo info) throws Exception
	{
		ArrayList arrResult = new ArrayList();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			//String strSQL ="";
			StringBuffer buffer = new StringBuffer();

			buffer.append("select  \n");
			buffer.append("a.id as AccountID,  \n");
			buffer.append("d.stransno as TransNo,  \n");
			buffer.append("d.ntransactiontypeid as TransactionTypeID,  \n");
			buffer.append("s.af_sdepositno as DepositNo,  \n");
			buffer.append("d.sabstract as Abstract,  \n");
			buffer.append("d.dtexecute as ExecuteDate,  \n");
			buffer.append("s.minterest as Interest,  \n");
			buffer.append("s.af_mrate as Rate,  \n");
			buffer.append("d.dtexecute as ExecuteDate,  \n");
			buffer.append("d.mamount as Amount,  \n");
			buffer.append("d.ntransdirection as TransDirection  \n");

			buffer.append("from \n");
			buffer.append("SETT_TRANSACCOUNTDETAIL d,sett_subAccount s,sett_account a \n");
			buffer.append("where  \n");
			buffer.append("d.nsubaccountid = s.id \n");
			buffer.append("and d.ntransaccountid = a.id \n");
			buffer.append("and d.nStatusID != 0 \n");
			buffer.append("and s.id = ? \n");
			buffer.append("and d.dtexecute=? \n");

			ps = con.prepareStatement(buffer.toString());
			log.info(buffer.toString());

			int index = 1;
			ps.setLong(index++, info.getSubAccountID());
			ps.setTimestamp(index++, info.getExecuteDate());

			rs = ps.executeQuery();
			while (rs.next())
			{
				OBAccountResultInfo resultInfo = new OBAccountResultInfo();

				resultInfo.setAccountID(rs.getLong("AccountID"));
				resultInfo.setTransNo(rs.getString("TransNo"));
				resultInfo.setTransactionTypeID(rs.getLong("TransActionTypeID"));
				resultInfo.setAccountNo(NameRef.getAccountNoByID(rs.getLong("AccountID")));
				resultInfo.setDepositNo(rs.getString("DepositNo"));
				resultInfo.setAmount(rs.getDouble("Amount"));
				resultInfo.setExecuteDate(rs.getTimestamp("ExecuteDate"));
				resultInfo.setInterest(rs.getDouble("Interest"));
				resultInfo.setRate(rs.getDouble("Rate"));
				resultInfo.setAbstract(rs.getString("Abstract"));
				resultInfo.setTransDirection(rs.getLong("TransDirection"));
				arrResult.add(resultInfo);
			}
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
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}

		}
		return arrResult;

	}
	/**
	 * 查询定期账户明细
	 * @return
	 * @throws Exception
	 */
	public Collection findByFixedAccountID(OBAccountQueryInfo info) throws Exception
	{
		ArrayList arrResult = new ArrayList();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			//String strSQL ="";
			StringBuffer buffer = new StringBuffer();

			buffer.append("select  \n");
			buffer.append("a.id as AccountID,  \n");
			buffer.append("d.stransno as TransNo,  \n");
			buffer.append("d.ntransactiontypeid as TransactionTypeID,  \n");
			buffer.append("s.af_sdepositno as DepositNo,  \n");
			buffer.append("d.sabstract as Abstract,  \n");
			buffer.append("d.mamount as Amount,  \n");
			buffer.append("d.dtexecute as ExecuteDate,  \n");
			buffer.append("d.ntransdirection as TransDirection  \n");

			buffer.append("from \n");
			buffer.append("SETT_TRANSACCOUNTDETAIL d,sett_subAccount s,sett_account a \n");
			buffer.append("where  \n");
			buffer.append("d.nsubaccountid = s.id \n");
			buffer.append("and d.ntransaccountid = a.id \n");
            buffer.append("and s.naccountid = a.id \n");
			buffer.append("and d.nStatusID != 0 \n");
			buffer.append("and s.id = ? \n");
			buffer.append("and d.dtexecute=? \n");	
			buffer.append("order by d.stransno asc \n");

			ps = con.prepareStatement(buffer.toString());
			log.info(buffer.toString());

			int index = 1;
			ps.setLong(index++, info.getSubAccountID());
			ps.setTimestamp(index++,info.getExecuteDate());

			rs = ps.executeQuery();
			while (rs.next())
			{
				OBAccountResultInfo resultInfo = new OBAccountResultInfo();

				resultInfo.setAccountID(rs.getLong("AccountID"));
				resultInfo.setTransNo(rs.getString("TransNo"));
				resultInfo.setTransactionTypeID(rs.getLong("TransActionTypeID"));
				resultInfo.setAccountNo(NameRef.getAccountNoByID(rs.getLong("AccountID")));
				resultInfo.setDepositNo(rs.getString("DepositNo"));
				resultInfo.setAmount(rs.getDouble("Amount"));
				resultInfo.setExecuteDate(rs.getTimestamp("ExecuteDate"));
				resultInfo.setAbstract(rs.getString("Abstract"));
				resultInfo.setTransDirection(rs.getLong("TransDirection"));

				if (resultInfo.getTransactionTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
				{
					Sett_TransFixedWithDrawDAO fixedDao = new Sett_TransFixedWithDrawDAO();
					TransFixedDrawInfo fixedInfo = null;
					fixedInfo = fixedDao.findByTransNo(resultInfo.getTransNo());
					if (fixedInfo != null)
					{
						log.info("***************" + fixedInfo.getRate());
						log.info("***************" + fixedInfo.getDrawInterest());
						resultInfo.setRate(fixedInfo.getRate());
						resultInfo.setInterest(fixedInfo.getDrawInterest());
					}

				}
				arrResult.add(resultInfo);
			}
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
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}

		}
		return arrResult;

	}
	/**
	 * 查询定期账户明细
	 * @return
	 * @throws Exception
	 */
	public Collection findByFixedAccountIDDzd(OBAccountQueryInfo info) throws Exception
	{
		ArrayList arrResult = new ArrayList();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			//String strSQL ="";
			StringBuffer buffer = new StringBuffer();

			buffer.append("select  \n");
			buffer.append("a.id as AccountID,  \n");
			buffer.append("d.stransno as TransNo,  \n");
			buffer.append("d.ntransactiontypeid as TransactionTypeID,  \n");
			buffer.append("s.af_sdepositno as DepositNo,  \n");
			buffer.append("d.sabstract as Abstract,  \n");
			buffer.append("d.mamount as Amount,  \n");
			buffer.append("d.dtexecute as ExecuteDate,  \n");
			buffer.append("d.ntransdirection as TransDirection  \n");

			buffer.append("from \n");
			buffer.append("SETT_TRANSACCOUNTDETAIL d,sett_subAccount s,sett_account a \n");
			buffer.append("where  \n");
			buffer.append("d.nsubaccountid = s.id \n");
			buffer.append("and d.ntransaccountid = a.id \n");
			buffer.append("and d.nStatusID != 0 \n");
			buffer.append("and s.id = ? \n");
			buffer.append("and d.dtexecute=? \n");
			buffer.append("order by d.stransno asc \n");

			ps = con.prepareStatement(buffer.toString());
			log.info(buffer.toString());

			int index = 1;
			ps.setLong(index++, info.getSubAccountID());
			ps.setTimestamp(index++, info.getExecuteDate());

			rs = ps.executeQuery();
			while (rs.next())
			{
				OBAccountResultInfo resultInfo = new OBAccountResultInfo();

				resultInfo.setAccountID(rs.getLong("AccountID"));
				resultInfo.setTransNo(rs.getString("TransNo"));
				resultInfo.setTransactionTypeID(rs.getLong("TransActionTypeID"));
				resultInfo.setAccountNo(NameRef.getAccountNoByID(rs.getLong("AccountID")));
				resultInfo.setDepositNo(rs.getString("DepositNo"));
				resultInfo.setAmount(rs.getDouble("Amount"));
				resultInfo.setExecuteDate(rs.getTimestamp("ExecuteDate"));
				resultInfo.setAbstract(rs.getString("Abstract"));
				resultInfo.setTransDirection(rs.getLong("TransDirection"));
				arrResult.add(resultInfo);
			}
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
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}

		}
		return arrResult;

	}
	//查询定期账户信息
	//查询定期账户信息
	public Collection seekFixedBalace(long lClientID, long lCurrencyID, long lOfficeID, long lStatusID) throws Exception
	{
		OBAccountBalanceCurrentInfo info = null;
		Collection cln = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = this.getConnection();

			StringBuffer sqlBuffer = new StringBuffer();
			sqlBuffer.append(" select a.naccounttypeid, \n");
			sqlBuffer.append(" b.naccountid, ");
			sqlBuffer.append(" a.saccountno, ");
			sqlBuffer.append(" a.id AccountID,\n");
			sqlBuffer.append(" b.id SubAccountID, ");
			sqlBuffer.append(" b.af_sdepositno,");
			sqlBuffer.append(" b.af_dtstart,\n");
			sqlBuffer.append(" b.af_dtend,");
			sqlBuffer.append(" b.af_ndepositterm,");
			sqlBuffer.append(" b.af_nnoticeday,\n");
			sqlBuffer.append(" b.af_mrate,");
			sqlBuffer.append(" b.mopenamount,");
			sqlBuffer.append(" b.mbalance,");
			sqlBuffer.append(" b.nstatusid, \n");
			sqlBuffer.append(" c.naccountgroupid \n");

			sqlBuffer.append(" from sett_account a,sett_subaccount b,sett_accounttype c \n");
			sqlBuffer.append(" where a.id=b.naccountid \n");
			sqlBuffer.append(" and b.af_sdepositno is not null \n");
			sqlBuffer.append(" and a.NOFFICEID = " + lOfficeID + " \n");
			sqlBuffer.append(" and a.NCURRENCYID =" + lCurrencyID + " \n");
			sqlBuffer.append(" and a.NCLIENTID = " + lClientID + " \n");
			sqlBuffer.append(" and a.naccounttypeid=c.id \n");
			sqlBuffer.append(" and c.officeId= " + lOfficeID + " \n");
			sqlBuffer.append(" and c.currencyId= " + lCurrencyID + " \n");
			if (lStatusID < 0)
			{
				sqlBuffer.append(" and b.nStatusID !=" + SETTConstant.SubAccountStatus.FINISH + " \n");
			}
			sqlBuffer.append(" and b.nStatusID != 0 \n");
			sqlBuffer.append(" and a.naccounttypeid in \n");
			sqlBuffer.append(" (select distinct c.id from sett_accounttype c where c.naccountgroupid in ("+
				SETTConstant.AccountGroupType.FIXED+","+SETTConstant.AccountGroupType.NOTIFY+") and c.nstatusId=1 and c.officeId="+lOfficeID+" and c.currencyId="+lCurrencyID+") \n");
			sqlBuffer.append(" order by a.naccounttypeid,a.saccountno,b.af_sdepositno ");

			String sql = sqlBuffer.toString();
			/*String sql =
				" select a.naccounttypeid, "
				    + " b.naccountid, "
					+ " a.saccountno, "	
					+ " a.id AccountID,"
					+ " b.id SubAccountID, "				
					+ " b.af_sdepositno, "
					+ " b.af_dtstart, "
					+ " b.af_dtend, "
					+ " b.af_ndepositterm, "
					+ " b.af_nnoticeday, "
					+ " b.af_mrate, "
					+ " b.mopenamount, "
					+ " b.mbalance, "
					+ " b.nstatusid "
					+ " from sett_account a,sett_subaccount b "
					+ " where a.id=b.naccountid "
					+ " and b.af_sdepositno is not null   "
					
					+ " and a.NOFFICEID = " + lOfficeID + "  "
					+ " and a.NCURRENCYID =" + lCurrencyID + "  "
					+ " and a.NCLIENTID = " + lClientID + " "					
					
					+ " and a.naccounttypeid in  "
					+ " (select distinct c.id from sett_accounttype c where c.naccountgroupid=2) "
					+ " order by a.naccounttypeid,a.saccountno,b.af_sdepositno ";*/

			log.info("SQL=====\n" + sql);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			cln = this.addFixedResultInfoIntoCln(rs);
		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}

		return cln;
	}
	
	public Collection seekFixedBalace(long lClientID, long lCurrencyID, long lOfficeID, long lStatusID,long lUserID) throws Exception
	{
		OBAccountBalanceCurrentInfo info = null;
		Collection cln = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = this.getConnection();
			
			StringBuffer sqlBuffer = new StringBuffer();
			sqlBuffer.append(" select a.naccounttypeid, \n");
			sqlBuffer.append(" b.naccountid, ");
			sqlBuffer.append(" a.saccountno, ");
			sqlBuffer.append(" a.id AccountID,\n");
			sqlBuffer.append(" b.id SubAccountID, ");
			sqlBuffer.append(" b.af_sdepositno,");
			sqlBuffer.append(" b.af_dtstart,\n");
			sqlBuffer.append(" b.af_dtend,");
			sqlBuffer.append(" b.af_ndepositterm,");
			sqlBuffer.append(" b.af_nnoticeday,\n");
			sqlBuffer.append(" b.af_mrate,");
			sqlBuffer.append(" b.mopenamount,");
			sqlBuffer.append(" b.mbalance,");
			sqlBuffer.append(" b.nstatusid, \n");
			sqlBuffer.append(" c.naccountgroupid \n");

			sqlBuffer.append(" from sett_account a,sett_subaccount b,sett_accounttype c \n");
			sqlBuffer.append(" where a.id=b.naccountid \n");
			sqlBuffer.append(" and b.af_sdepositno is not null \n");
			//sqlBuffer.append(" and a.NOFFICEID = " + lOfficeID + " \n");
			sqlBuffer.append(" and a.NCURRENCYID =" + lCurrencyID + " \n");
			sqlBuffer.append(" and a.NCLIENTID = " + lClientID + " \n");
			sqlBuffer.append(" and a.naccounttypeid=c.id \n");
			//sqlBuffer.append(" and c.officeId= " + lOfficeID + " \n");
			sqlBuffer.append(" and c.currencyId= " + lCurrencyID + " \n");
			if (lStatusID < 0)
			{
				sqlBuffer.append(" and b.nStatusID !=" + SETTConstant.SubAccountStatus.FINISH + " \n");
			}
			sqlBuffer.append(" and b.nStatusID != 0 \n");
			sqlBuffer.append(" and a.naccounttypeid in \n");
			sqlBuffer.append(" (select distinct c.id from sett_accounttype c where c.naccountgroupid in ("+
			SETTConstant.AccountGroupType.FIXED+","+SETTConstant.AccountGroupType.NOTIFY+") and c.nstatusId=1 and c.currencyId="+lCurrencyID+") \n");
			sqlBuffer.append(" and  a.saccountno in \n");
			sqlBuffer.append(" ( select a.saccountno \n");
			sqlBuffer.append(" from OB_AccountOwnedByUserQuery a, Sett_Account ai \n");
			sqlBuffer.append(" where ai.nStatusID=1 \n");
			sqlBuffer.append(" and a.saccountno=ai.saccountno \n");
			sqlBuffer.append(" and a.nUserID=" + lUserID+" \n");
			sqlBuffer.append(" and ai.ncurrencyid="+ lCurrencyID+") \n");
			
			sqlBuffer.append(" order by a.naccounttypeid,a.saccountno,b.af_sdepositno ");
			
			String sql = sqlBuffer.toString();
			/*String sql =
				" select a.naccounttypeid, "
				    + " b.naccountid, "
					+ " a.saccountno, "	
					+ " a.id AccountID,"
					+ " b.id SubAccountID, "				
					+ " b.af_sdepositno, "
					+ " b.af_dtstart, "
					+ " b.af_dtend, "
					+ " b.af_ndepositterm, "
					+ " b.af_nnoticeday, "
					+ " b.af_mrate, "
					+ " b.mopenamount, "
					+ " b.mbalance, "
					+ " b.nstatusid "
					+ " from sett_account a,sett_subaccount b "
					+ " where a.id=b.naccountid "
					+ " and b.af_sdepositno is not null   "
					
					+ " and a.NOFFICEID = " + lOfficeID + "  "
					+ " and a.NCURRENCYID =" + lCurrencyID + "  "
					+ " and a.NCLIENTID = " + lClientID + " "					
					
					+ " and a.naccounttypeid in  "
					+ " (select distinct c.id from sett_accounttype c where c.naccountgroupid=2) "
					+ " order by a.naccounttypeid,a.saccountno,b.af_sdepositno ";*/

			log.info("SQL=====\n" + sql);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			cln = this.addFixedResultInfoIntoCln(rs);
		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}

		return cln;
	}
	
	//保证金账户
	public Collection seekMarginBalace(long lClientID, long lCurrencyID, long lOfficeID, long lStatusID) throws Exception
	{
		OBAccountBalanceFixedInfo info = null;
		ArrayList cln = new ArrayList();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = this.getConnection();

			StringBuffer sqlBuffer = new StringBuffer();
			sqlBuffer.append(" select a.naccounttypeid, \n");
			sqlBuffer.append(" b.naccountid, ");
			sqlBuffer.append(" a.saccountno, ");
			sqlBuffer.append(" a.id AccountID,\n");
			sqlBuffer.append(" b.id SubAccountID, ");
			sqlBuffer.append(" b.af_sdepositno,");
			sqlBuffer.append(" b.af_dtstart,\n");
			sqlBuffer.append(" b.af_dtend,");
			sqlBuffer.append(" b.af_ndepositterm,");
			sqlBuffer.append(" b.af_nnoticeday,\n");
			sqlBuffer.append(" b.af_mrate,");
			sqlBuffer.append(" b.mopenamount,");
			sqlBuffer.append(" b.mbalance,");
			sqlBuffer.append(" b.nstatusid, \n");
			sqlBuffer.append(" c.naccountgroupid \n");

			sqlBuffer.append(" from sett_account a,sett_subaccount b,sett_accounttype c \n");
			sqlBuffer.append(" where a.id=b.naccountid \n");
			sqlBuffer.append(" and b.af_sdepositno is not null \n");
			sqlBuffer.append(" and a.NOFFICEID = " + lOfficeID + " \n");
			sqlBuffer.append(" and a.NCURRENCYID =" + lCurrencyID + " \n");
			sqlBuffer.append(" and a.NCLIENTID = " + lClientID + " \n");
			sqlBuffer.append(" and a.naccounttypeid=c.id \n");
			sqlBuffer.append(" and c.officeId= " + lOfficeID + " \n");
			sqlBuffer.append(" and c.currencyId= " + lCurrencyID + " \n");
			if (lStatusID < 0)
			{
				sqlBuffer.append(" and b.nStatusID !=" + SETTConstant.SubAccountStatus.FINISH + " \n");
			}
			sqlBuffer.append(" and b.nStatusID != 0 \n");
			sqlBuffer.append(" and a.naccounttypeid in \n");
			sqlBuffer.append(" (select distinct c.id from sett_accounttype c where c.naccountgroupid in ("+
					SETTConstant.AccountGroupType.MARGIN+") and c.nstatusId=1 and c.officeId="+lOfficeID+" and c.currencyId="+lCurrencyID+") \n");
			sqlBuffer.append(" order by a.naccounttypeid,a.saccountno,b.af_sdepositno ");

			String sql = sqlBuffer.toString();
			/*String sql =
				" select a.naccounttypeid, "
				    + " b.naccountid, "
					+ " a.saccountno, "	
					+ " a.id AccountID,"
					+ " b.id SubAccountID, "				
					+ " b.af_sdepositno, "
					+ " b.af_dtstart, "
					+ " b.af_dtend, "
					+ " b.af_ndepositterm, "
					+ " b.af_nnoticeday, "
					+ " b.af_mrate, "
					+ " b.mopenamount, "
					+ " b.mbalance, "
					+ " b.nstatusid "
					+ " from sett_account a,sett_subaccount b "
					+ " where a.id=b.naccountid "
					+ " and b.af_sdepositno is not null   "
					
					+ " and a.NOFFICEID = " + lOfficeID + "  "
					+ " and a.NCURRENCYID =" + lCurrencyID + "  "
					+ " and a.NCLIENTID = " + lClientID + " "					
					
					+ " and a.naccounttypeid in  "
					+ " (select distinct c.id from sett_accounttype c where c.naccountgroupid=2) "
					+ " order by a.naccounttypeid,a.saccountno,b.af_sdepositno ";*/

			log.info("SQL=====\n" + sql);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

//			while(rs.next())
//			{
//				info = new OBAccountBalanceFixedInfo();
//				info.setNaccounttypeid(rs.getLong("naccounttypeid"));
//				info.setSaccountno(rs.getString("af_sdepositno"));
//				info.setAccountID(rs.getLong("AccountID"));
//				info.setSubAccountID(rs.getLong("SubAccountID"));
//				info.setAf_dtstart(rs.getTimestamp("af_dtstart"));
//				info.setAf_dtend(rs.getTimestamp("af_dtend"));
//				info.setAf_ndepositterm(rs.getLong("af_ndepositterm"));
//				info.setAf_mrate(rs.getDouble("af_mrate"));
//				info.setMopenamount(rs.getDouble("mopenamount"));
//				info.setMbalance(rs.getDouble("mbalance"));
//				info.setNstatusid(rs.getLong("nstatusid"));
//				cln.add(info);
//			}
			cln = (ArrayList) this.addFixedResultInfoIntoCln(rs);
		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}

		return cln;
	}

	//查询贴现账户信息
	public Collection seekDiscountBalace(long lClientID, long lCurrencyID, long lOfficeID) throws Exception
	{
		Collection cln = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = this.getConnection();
			String sql = " select distinct " 
				+ " a.naccounttypeid, " 
				+ " a.saccountno, " 
				+ " d.id ContractID,\n" 
				+ " a.id AccountID,"
				+ " d.SCONTRACTCODE, " 
				+ " d.nborrowclientid, \n" 
				+ " d.dtStartDate, " 
				+ " d.dtEndDate, " 
				+ " d.nIntervalNum, \n" 
				+ " d.mExamineAmount mAmount, "
				+ " d.nstatusid  \n"
				+ " from sett_account a,sett_subaccount b,loan_contractform d, "
				+ " loan_discountcredence e \n"
				+ " where a.id=b.naccountid \n"
				+ " and a.naccounttypeid in \n"
				+ " (select distinct c.id from sett_accounttype c where c.naccountgroupid in ("+
						SETTConstant.AccountGroupType.DISCOUNT+") and c.nstatusId=1 and c.currencyId="+lCurrencyID+") \n"
				+ " and d.id = e.nContractID \n"
				//+ " and a.NOFFICEID = "
				//+ lOfficeID
				//+ "  \n"
				+ " and a.NCURRENCYID ="
				+ lCurrencyID
				+ "  \n"
				+ " and a.NCLIENTID = "
				+ lClientID
				+ " \n"
				+ " and a.nStatusID != "
				+ SETTConstant.AccountStatus.CLOSE
				+ " \n"
				+ " and e.ID=b.al_nloannoteid \n"
				+ " and b.al_nloannoteid is not null \n"
						////test////
				//+" and d.SCONTRACTCODE in ('200132001-T','200331004-T','200331012-T','200133045-T','924051-T','200333003-T') "
				///////////
				+ " order by a.naccounttypeid,a.saccountno,d.SCONTRACTCODE ";

			log.info("查询贴现账户信息SQL======\n" + sql);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			//added by mzh_fu 2007/04/10 为解决多出一个小计的问题,增加下面两行
			//if(rs.next()){
				//rs.beforeFirst();
				
				cln = this.addLoanInfoIntoCln(rs, conn, lOfficeID, lCurrencyID);
			//}
		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}

		return cln;
	}
	
//	查询贷款账户信息
	 public Collection seekLoanBalace(long lClientID, long lCurrencyID, long lOfficeID) throws Exception
	  {
		  Collection cln = null;
		  Connection conn = null;
		  PreparedStatement ps = null;
		  ResultSet rs = null;
		  
		  try
		  {
			  conn = this.getConnection();
			  String sql = " select distinct " 
				  + " a.naccounttypeid, " 
				  + " a.saccountno, " 
				  + " d.id ContractID," 
				  + " a.id AccountID,\n"
				  //+ " b.al_nloannoteid, "
				  +" d.SCONTRACTCODE, " 
				  + " d.nborrowclientid, " 
				  + " d.dtStartDate, " 
				  + " d.dtEndDate, " 
				  + " d.nIntervalNum, \n" 
				  + " d.mExamineAmount mAmount, "
				  //+ " e.id,"
				  +" d.nstatusid  \n"
				  + " from sett_account a,sett_subaccount b,loan_contractform d, "
				  + " loan_payform e,loan_interestRate f,client g \n"
				  + " where a.id=b.naccountid "
				  + " and a.naccounttypeid in  \n"
					+ " (select distinct c.id from sett_accounttype c where c.naccountgroupid in ("+
					SETTConstant.AccountGroupType.TRUST+","+SETTConstant.AccountGroupType.CONSIGN+","+SETTConstant.AccountGroupType.DISCOUNT+") and c.nstatusId=1 and c.officeId="+lOfficeID+" and c.currencyId="+lCurrencyID+") \n"
				  + " and d.id = e.nContractID "
				  + " and a.NOFFICEID = "
				  + lOfficeID
				  + "  \n"
				  + " and a.NCURRENCYID ="
				  + lCurrencyID
				  + "  "
				  + " and a.NCLIENTID = "
				  + lClientID
				  + " \n"
				  + " and a.nStatusID != "
				  + SETTConstant.AccountStatus.CLOSE
				  + " \n"
				  + " and e.NBANKINTERESTID = f.id(+) \n"
				  + " and d.nConsignClientID = g.id(+)  \n"
				  + " and e.ID=b.al_nloannoteid "
				  + " and b.al_nloannoteid is not null \n"
				  ////test////
				  //+" and d.SCONTRACTCODE in ('200132001-T','200331004-T','200331012-T','200133045-T','924051-T','200333003-T') "
				  ///////////
				  +" order by a.naccounttypeid,a.saccountno,d.SCONTRACTCODE ";

			  log.info("SQL======\n" + sql);
			  ps = conn.prepareStatement(sql);
			  rs = ps.executeQuery();

			  cln = this.addLoanInfoIntoCln(rs, conn, lOfficeID, lCurrencyID);
			  Collection cDiscount = seekDiscountBalace(lClientID, lCurrencyID, lOfficeID);
			  if (cln == null )
			  {
				cln = cDiscount;
			  }
			  else if (cDiscount != null && cDiscount.size()>1)
			  {
				cln.addAll(cDiscount);
			  }
		  }
		  finally
		  {
			  this.cleanup(rs);
			  this.cleanup(ps);
			  this.cleanup(conn);
		  }

		  return cln;
	  }

	  public Collection seekLoanBalace(long lClientID, long lCurrencyID, long lOfficeID,long lUserID) throws Exception
	  {
		  Collection cln = null;
		  Connection conn = null;
		  PreparedStatement ps = null;
		  ResultSet rs = null;
		  
		  try
		  {
			  conn = this.getConnection();
			  String sql = " select distinct " 
				  + " a.naccounttypeid, " 
				  + " a.saccountno, " 
				  + " d.id ContractID," 
				  + " a.id AccountID,\n"
				  //+ " b.al_nloannoteid, "
				  +" d.SCONTRACTCODE, " 
				  + " d.nborrowclientid, " 
				  + " d.dtStartDate, " 
				  + " d.dtEndDate, " 
				  + " d.nIntervalNum, \n" 
				  + " d.mExamineAmount mAmount, "
				  //+ " e.id,"
				  +" d.nstatusid  \n"
				  + " from sett_account a,sett_subaccount b,loan_contractform d, "
				  + " loan_payform e,loan_interestRate f,client g \n"
				  + " where a.id=b.naccountid "
				  + " and a.naccounttypeid in  \n"
					+ " (select distinct c.id from sett_accounttype c where c.naccountgroupid in ("+
					SETTConstant.AccountGroupType.TRUST+","+SETTConstant.AccountGroupType.CONSIGN+","+SETTConstant.AccountGroupType.DISCOUNT+") and c.nstatusId=1 and c.officeId="+lOfficeID+" and c.currencyId="+lCurrencyID+") \n"
				  + " and d.id = e.nContractID "
				  //+ " and a.NOFFICEID = "
				  //+ lOfficeID
				  + "  \n"
				  + " and a.NCURRENCYID ="
				  + lCurrencyID
				  + "  "
				  + " and a.NCLIENTID = "
				  + lClientID
				  + " \n"
				  + " and a.nStatusID != "
				  + SETTConstant.AccountStatus.CLOSE
				  + " \n"
				  + " and e.NBANKINTERESTID = f.id(+) \n"
				  + " and d.nConsignClientID = g.id(+)  \n"
				  + " and e.ID=b.al_nloannoteid "
				  + " and b.al_nloannoteid is not null \n"
				  ////test////
				  //+" and d.SCONTRACTCODE in ('200132001-T','200331004-T','200331012-T','200133045-T','924051-T','200333003-T') "
				  ///////////
				  + " and  a.saccountno in \n"
			  	  + " ( select a.saccountno \n"
			  	  + " from OB_AccountOwnedByUserQuery a, Sett_Account ai \n"
			  	  + " where ai.nStatusID=1 \n"
			  	  + " and a.saccountno=ai.saccountno \n"
			  	  + " and a.nUserID=" + lUserID+" \n"
			  	  + " and ai.ncurrencyid="+ lCurrencyID+") \n"
				  + " order by a.naccounttypeid,a.saccountno,d.SCONTRACTCODE ";

			  log.info("SQL======\n" + sql);
			  ps = conn.prepareStatement(sql);
			  rs = ps.executeQuery();

			  cln = this.addLoanInfoIntoCln(rs, conn, lOfficeID, lCurrencyID);
			  Collection cDiscount = seekDiscountBalace(lClientID, lCurrencyID, lOfficeID);
			  if (cln == null )
			  {
				cln = cDiscount;
			  }
			  else if (cDiscount != null && cDiscount.size()>1)
			  {
				cln.addAll(cDiscount);
			  }
		  }
		  finally
		  {
			  this.cleanup(rs);
			  this.cleanup(ps);
			  this.cleanup(conn);
		  }

		  return cln;
	  }

	//将活期账户的查询的结果集转化Collection
	private Collection addCurrentOrConsignResultInfoIntoCln(ResultSet rs, boolean isCurrent) throws Exception
	{
		ArrayList list = new ArrayList();
		OBAccountBalanceCurrentInfo info = null;
		OBAccountBalanceCurrentInfo subSuminfo = null;

		long nFrtAcctType = -1; //起始记录的账户类型
		long nAcctType = -1; //账户类型
		double subsum = 0.00; //小计
		double sum = 0.00; //活期小计

		while (rs.next())
		{
			info = new OBAccountBalanceCurrentInfo();

			nAcctType = rs.getLong("naccounttypeid");

			if (nAcctType == nFrtAcctType)
			{
				subsum += rs.getDouble("mbalance");
				sum += rs.getDouble("mbalance");
				//设置账户类型为"";
				info.setNaccounttypeid(0);
				//设置账号
				info.setSaccountno(rs.getString("saccountno"));
				//设置资金余额
				info.setMbalance(rs.getDouble("mbalance"));
				//设置最低余额限制
				info.setAc_mcapitallimitamount(rs.getDouble("ac_mcapitallimitamount"));
				info.setAccountID(rs.getLong("AccountID"));
				list.add(info);
			}
			else
			{
				//如果nFrtAcctType不为-1
				if (nFrtAcctType != nAcctType && nFrtAcctType != -1)
				{
					subSuminfo = new OBAccountBalanceCurrentInfo();
					nFrtAcctType = nAcctType;
					//添加小计信息 
					subSuminfo.setNaccounttypeid(0);
					//添加"小计"和"活期小计信息" -1 小计  -2  活期小计
					subSuminfo.setSaccountno("-1");
					subSuminfo.setMbalance(subsum);
					list.add(subSuminfo);

					subsum = 0.0;
				}
				else
				{
					nFrtAcctType = nAcctType;
				}
				subsum += rs.getDouble("mbalance");
				sum += rs.getDouble("mbalance");

				//添加本条记录
				info.setNaccounttypeid(rs.getLong("naccounttypeid"));
				info.setSaccountno(rs.getString("saccountno"));
				info.setAccountID(rs.getLong("AccountID"));
				info.setMbalance(rs.getDouble("mbalance"));
				info.setAc_mcapitallimitamount(rs.getDouble("ac_mcapitallimitamount"));

				list.add(info);
			}
		}

		//添加最后一条小计信息
		subSuminfo = new OBAccountBalanceCurrentInfo();
		subSuminfo.setNaccounttypeid(0);
		subSuminfo.setSaccountno("-1");
		subSuminfo.setMbalance(subsum);
		list.add(subSuminfo);

		//如果是活期,则添加活期小计信息
		if (isCurrent)
		{
			info = new OBAccountBalanceCurrentInfo();
			info.setSaccountno("-2");
			info.setMbalance(sum);
			list.add(info);
		}

		return list;
	}
	//将活期账户的查询的结果集转化Collection去除getNaccounttypeid为12的
	private Collection addCurrentOrConsignResultInfoIntoCln2(ResultSet rs, boolean isCurrent) throws Exception
	{
		ArrayList list = new ArrayList();
		OBAccountBalanceCurrentInfo info = null;
		OBAccountBalanceCurrentInfo subSuminfo = null;

		long nFrtAcctType = -1; //起始记录的账户类型
		long nAcctType = -1; //账户类型
		
		double subsum = 0.00; //小计
		double sum = 0.00; //活期小计

		while (rs.next())
		{
			info = new OBAccountBalanceCurrentInfo();
					
			nAcctType = rs.getLong("naccounttypeid");
       
			if(nAcctType!=12){
			if (nAcctType == nFrtAcctType)
			{
				  
				  subsum += rs.getDouble("mbalance");
				  sum += rs.getDouble("mbalance");
			 
					
				
				
				//设置账户类型为"";
				info.setNaccounttypeid(0);
				//设置账号
				info.setSaccountno(rs.getString("saccountno"));
				//设置资金余额
				info.setMbalance(rs.getDouble("mbalance"));
				//设置最低余额限制
				info.setAc_mcapitallimitamount(rs.getDouble("ac_mcapitallimitamount"));
				info.setAccountID(rs.getLong("AccountID"));
				list.add(info);
			}
			else
			{
				//如果nFrtAcctType不为-1
				if (nFrtAcctType != nAcctType && nFrtAcctType != -1)
				{
					subSuminfo = new OBAccountBalanceCurrentInfo();
					nFrtAcctType = nAcctType;
					//添加小计信息 
					subSuminfo.setNaccounttypeid(0);
					//添加"小计"和"活期小计信息" -1 小计  -2  活期小计
					subSuminfo.setSaccountno("-1");
					subSuminfo.setMbalance(subsum);
					list.add(subSuminfo);

					subsum = 0.0;
				}
				else
				{
					nFrtAcctType = nAcctType;
				}
				
				subsum += rs.getDouble("mbalance");
				sum += rs.getDouble("mbalance");
				
				//添加本条记录
				info.setNaccounttypeid(rs.getLong("naccounttypeid"));
				info.setSaccountno(rs.getString("saccountno"));
				info.setAccountID(rs.getLong("AccountID"));
				info.setMbalance(rs.getDouble("mbalance"));
				info.setAc_mcapitallimitamount(rs.getDouble("ac_mcapitallimitamount"));

				list.add(info);
			}
			}
		}

		//添加最后一条小计信息
			
			subSuminfo = new OBAccountBalanceCurrentInfo();
			
			subSuminfo.setNaccounttypeid(0);
			subSuminfo.setSaccountno("-1");
			subSuminfo.setMbalance(subsum);
			list.add(subSuminfo);
		

		//如果是活期,则添加活期小计信息
		if (info!=null&&isCurrent&&info.getNaccounttypeid()!=12)
		{
			info = new OBAccountBalanceCurrentInfo();
			info.setSaccountno("-2");
			info.setMbalance(sum);
			list.add(info);
		}

		return list;
	}

	//将定期账户的查询的结果集转化Collection
	private Collection addFixedResultInfoIntoCln(ResultSet rs) throws Exception
	{
		ArrayList list = new ArrayList();
		OBAccountBalanceFixedInfo info = null;
		OBAccountBalanceFixedInfo subSumInfo = null;
		OBAccountBalanceFixedInfo tmpInfo = null;

		//起始账户类型
		long nFrtAcctType = -1;
		//起始账号
		String sFrtAcctNo = null;

		long nAcctType = -1;
		String sAcctNo = null;

		double subSumOfCapital = 0.0;
		double subSumOfBalance = 0.0;

		while (rs.next())
		{
			info = new OBAccountBalanceFixedInfo();
			//设置主账号
			info.setNaccountid(rs.getLong("naccountid"));
			info.setSubAccountID(rs.getLong("SubAccountID"));
			info.setAccountID(rs.getLong("AccountID"));
			tmpInfo = new OBAccountBalanceFixedInfo();
			//取得本条记录的账户类型
			nAcctType = rs.getLong("naccountgroupid");

			//取得本条记录的账号
			sAcctNo = rs.getString("saccountno");

			//如果账户类型与初始账户类型相同
			if (nAcctType == nFrtAcctType)
			{
				//设置账户类型为0;  表示本条记录中账户记录不为空,但是不必显示				
				info.setNaccounttypeid(0);

				//如果账户类型相同，账号也相同 
				if (sAcctNo.equals(sFrtAcctNo))
				{
					//将账户类型设置为0,表示存在,但是不必显示
					info.setNaccounttypeid(0);
					//将账号栏显示为存单号
					info.setSaccountno(rs.getString("af_sdepositno"));

					//设置存入日 、 到期日;
					info.setAf_dtstart(rs.getTimestamp("af_dtstart"));
					info.setAf_dtend(rs.getTimestamp("af_dtend"));

					//如果是通知存款，则显示品种；如果是定期存款，显示期限
					if (nAcctType == 3)
					{
						info.setNtype(6);
						info.setAf_ndepositterm(rs.getLong("af_nnoticeday"));
					}
					//品种
					else if (nAcctType == 2)
					{
						info.setNtype(5);
						info.setAf_ndepositterm(rs.getLong("af_ndepositterm"));
					}
					//利率
					info.setAf_mrate(rs.getDouble("af_mrate"));
					//设置存款金额和存款余额
					info.setMopenamount(rs.getDouble("mopenamount"));
					info.setMbalance(rs.getDouble("mbalance"));
					//设置备注
					info.setNstatusid(rs.getLong("nstatusid"));

					list.add(info);

					//统计小计值
					subSumOfCapital += rs.getDouble("mopenamount");
					subSumOfBalance += rs.getDouble("mbalance");
				}
				//账户类型相同，但账号不同
				else
				{
					//将账户类型设置为0,表示存在,但是不必显示
					tmpInfo.setNaccounttypeid(0);
					//将账号栏显示为账号
					tmpInfo.setSaccountno(rs.getString("saccountno"));
					//将其他栏位都设置为空 ；
					// 存入日 、 到期日都置为空
					tmpInfo.setAf_dtstart(null);
					tmpInfo.setAf_dtend(null);
					// 期限栏显示空   标示为-2
					tmpInfo.setAf_ndepositterm(-2);
					// 存款金额栏置为空,标示为-1;
					tmpInfo.setMopenamount(-1);
					// 存款余额栏置为空,标示为-1;
					tmpInfo.setMbalance(-1);
					// 备注栏置为空,标示为-1
					tmpInfo.setNstatusid(-1);

					//添加上面的记录
					list.add(tmpInfo);

					//将账户类型设置为0,表示存在,但是不必显示
					info.setNaccounttypeid(0);
					//将账号栏显示为存单号
					info.setSaccountno(rs.getString("af_sdepositno"));

					//设置存入日 ， 到期日
					info.setAf_dtstart(rs.getTimestamp("af_dtstart"));
					info.setAf_dtend(rs.getTimestamp("af_dtend"));

					//如果是通知存款，则显示品种；如果是定期存款，显示期限
					if (nAcctType == 3)
					{
						info.setNtype(6);
						info.setAf_ndepositterm(rs.getLong("af_nnoticeday"));
					}
					//品种
					else if (nAcctType == 2)
					{
						info.setNtype(5);
						info.setAf_ndepositterm(rs.getLong("af_ndepositterm"));
					}
					//利率
					info.setAf_mrate(rs.getDouble("af_mrate"));
					//设置存款金额 、 存款余额 ； 设置备注 ；
					info.setMopenamount(rs.getDouble("mopenamount"));
					info.setMbalance(rs.getDouble("mbalance"));
					info.setNstatusid(rs.getLong("nstatusid"));

					list.add(info);

					//统计存款金额、存款余额
					subSumOfCapital += rs.getDouble("mopenamount");
					subSumOfBalance += rs.getDouble("mbalance");

					//设置初始账户类型为本条记录的账户类型;
					nFrtAcctType = nAcctType;
					//设置初始账号为本条记录的账号
					sFrtAcctNo = sAcctNo;
				}
			}
			//账户类型不同，则账号不可能相同！
			else //如果账户类型与初始账户类型不同,且账户类型不为空
				{
				if (nFrtAcctType != -1)
				{
					subSumInfo = new OBAccountBalanceFixedInfo();
					/* 设置上一个小计值*/
					// 将账户类型值设置为 － 1 
					subSumInfo.setNaccounttypeid(-1);
					// 账号设置为空 
					subSumInfo.setSaccountno("");
					// 存入日 、 到期日都置为空
					subSumInfo.setAf_dtstart(null);
					subSumInfo.setAf_dtend(null);
					// 期限栏显示空   标示为-1
					subSumInfo.setAf_ndepositterm(-1);
					// 利率栏显示 “ 小计 ”   标示为-2
					subSumInfo.setAf_mrate(-2);
					// 存款金额栏置为subSumOfCapital 
					subSumInfo.setMopenamount(subSumOfCapital);
					// 存款余额栏置为subSumOfBalance  
					subSumInfo.setMbalance(subSumOfBalance);
					// 备注栏置为空,标示为-1
					subSumInfo.setNstatusid(-1);

					list.add(subSumInfo);
				}

				//设置初始账户类型为本条记录的账户类型;
				nFrtAcctType = nAcctType;
				//设置初始账号为本条记录的账号
				sFrtAcctNo = sAcctNo;
				tmpInfo.setNaccounttypeid(rs.getLong("naccounttypeid"));
				tmpInfo.setSaccountno(rs.getString("saccountno"));
				tmpInfo.setAf_dtstart(null);
				tmpInfo.setAf_dtend(null);
				// 期限栏显示空   标示为-1
				tmpInfo.setAf_ndepositterm(-1);
				// 利率栏显示空,   标示为-1
				tmpInfo.setAf_mrate(-1);
				// 存款金额栏置为空,标示为-1;
				tmpInfo.setMopenamount(-1);
				// 存款余额栏置为空,标示为-1;
				tmpInfo.setMbalance(-1);
				// 备注栏置为空,标示为-1
				tmpInfo.setNstatusid(-1);

				//添加上面的记录
				list.add(tmpInfo);

				//将小计值置为0.00;
				subSumOfCapital = 0.00;
				subSumOfBalance = 0.00;

				//如果账号相同
				if (sFrtAcctNo.equals(sAcctNo))
				{
					//将账户类型设置为0,表示存在,但是不必显示
					info.setNaccounttypeid(0);
					//将账号栏显示为存单号
					info.setSaccountno(rs.getString("af_sdepositno"));

					//设置存入日 、 到期日 
					info.setAf_dtstart(rs.getTimestamp("af_dtstart"));
					info.setAf_dtend(rs.getTimestamp("af_dtend"));

					//如果是通知存款，则显示品种；如果是定期存款，显示期限
					if (nAcctType == 3)
					{
						info.setNtype(6);
						info.setAf_ndepositterm(rs.getLong("af_nnoticeday"));
					}
					//品种
					else if (nAcctType == 2)
					{
						info.setNtype(5);
						info.setAf_ndepositterm(rs.getLong("af_ndepositterm"));
					}
					//利率
					info.setAf_mrate(rs.getDouble("af_mrate"));
					//设置存款金额 、 存款余额 ； 设置备注 ；
					info.setMopenamount(rs.getDouble("mopenamount"));
					info.setMbalance(rs.getDouble("mbalance"));
					info.setNstatusid(rs.getLong("nstatusid"));

					list.add(info);

					//统计小计值
					subSumOfCapital += rs.getDouble("mopenamount");
					subSumOfBalance += rs.getDouble("mbalance");
				}
				else //账号不同
					{
					//将账户类型设置为0,表示存在,但是不必显示
					info.setNaccounttypeid(0);
					//将账号栏显示为账号
					info.setSaccountno(rs.getString("saccountno"));
					/*将其他栏位都设置为空 */
					// 存入日 、 到期日都置为空
					info.setAf_dtstart(null);
					info.setAf_dtend(null);
					// 期限栏显示空   标示为-2
					info.setAf_ndepositterm(-2);
					// 存款金额栏置为空,标示为-1;
					info.setMopenamount(-1);
					// 存款余额栏置为空,标示为-1;
					info.setMbalance(-1);
					// 备注栏置为空,标示为-1
					info.setNstatusid(-1);

					//添加上面的记录
					list.add(info);

					//将账户类型设置为0,表示存在,但是不必显示
					info.setNaccounttypeid(0);
					//将账号栏显示为存单号
					info.setSaccountno(rs.getString("af_sdepositno"));

					//设置存入日 、 到期日 
					info.setAf_dtstart(rs.getTimestamp("af_dtstart"));
					info.setAf_dtend(rs.getTimestamp("af_dtend"));
					//如果是通知存款，则显示品种；如果是定期存款，显示期限
					if (nAcctType == 3)
					{
						info.setNtype(6);
						info.setAf_ndepositterm(rs.getLong("af_nnoticeday"));
					}
					//品种
					else if (nAcctType == 2)
					{
						info.setNtype(5);
						info.setAf_ndepositterm(rs.getLong("af_ndepositterm"));
					}
					//利率
					info.setAf_mrate(rs.getDouble("af_mrate"));
					//设置存款金额 、 存款余额 ； 设置备注 ；
					info.setMopenamount(rs.getDouble("mopenamount"));
					info.setMbalance(rs.getDouble("mbalance"));
					info.setNstatusid(rs.getLong("nstatusid"));

					list.add(info);

					//统计存款金额、存款余额
					//统计小计值
					subSumOfCapital += rs.getDouble("mopenamount");
					subSumOfBalance += rs.getDouble("mbalance");

					//设置初始账户类型为本条记录的账户类型;
					nFrtAcctType = nAcctType;
				}
			}
		}
		subSumInfo = new OBAccountBalanceFixedInfo();
		//将账户类型值设置为 － 1 
		subSumInfo.setNaccounttypeid(-1);
		// 账号设置为空 
		subSumInfo.setSaccountno("");
		// 存入日 、 到期日都置为空
		subSumInfo.setAf_dtstart(null);
		subSumInfo.setAf_dtend(null);
		// 期限栏显示空, 标示为-1
		subSumInfo.setAf_ndepositterm(-1);
		// 利率栏显示 “ 小计 ”   标示为-2
		subSumInfo.setAf_mrate(-2);
		// 存款金额栏置为subSumOfCapital 
		subSumInfo.setMopenamount(subSumOfCapital);
		// 存款余额栏置为subSumOfBalance  
		subSumInfo.setMbalance(subSumOfBalance);
		// 备注栏置为空,标示为-1
		subSumInfo.setNstatusid(-1);

		list.add(subSumInfo);
		return list;
	}

	//通过放款通知单取得贷款余额
	public double getLoanBalanceByLoanNoteID(long nLoanNoteID, Connection conn) throws Exception
	{
		double dRtn = 0.0;
		Connection connInternal = null;
		if (conn == null)
		{
			connInternal = this.getConnection();
		}
		else
		{
			connInternal = conn;
		}
		//Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			//conn = this.getConnection();
			String sql = " select sum(mbalance) mLoanBalance from sett_subaccount " + " where al_nloannoteid is not null " + " and al_nloannoteid=" + nLoanNoteID + " group by al_nloannoteid ";
			ps = connInternal.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				dRtn = rs.getDouble("mLoanBalance");
			}
		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			if (conn == null)
			{
				this.cleanup(connInternal);
			}
		}

		return dRtn;
	}
	//通过合同号取得贷款余额
	public double getLoanBalanceByContractID(long lContarctID, Connection conn) throws Exception
	{
		double dRtn = 0.0;
		Connection connInternal = null;
		if (conn == null)
		{
			connInternal = this.getConnection();
		}
		else
		{
			connInternal = conn;
		}
		//Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			//conn = this.getConnection();
			String sql =
				" select sum(mbalance) mLoanBalance from sett_subaccount a,loan_contractform b,loan_payform c "
					+ " where al_nloannoteid is not null "
					+ " and al_nloannoteid= c.id and b.id = c.nContractID and b.id = "
					+ lContarctID;
			ps = connInternal.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next())
			{
				dRtn = rs.getDouble("mLoanBalance");
			}
		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			if (conn == null)
			{
				this.cleanup(connInternal);
			}
		}

		return dRtn;
	}

	//	通过放款通知单取得贷款余额
	public double getLoanHisBalanceByLoanNoteID(QueryTransAccountDetailWhereInfo info) throws Exception
	{
		double dRtn = 0.0;
		if (info.getLoanNoteID() <= 0)
		{
			return dRtn;
		}
		Connection connInternal = null;
		connInternal = this.getConnection();

		//Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			//conn = this.getConnection();
			String sql = " select sum(b.mbalance) mLoanBalance  ";
			sql += " from sett_subaccount a,sett_DailyAccountBalance b";
			sql += " where a.id = b.nSubaccountid ";
			sql += " and a.al_nloannoteid=" + info.getLoanNoteID();
			sql += " and b.nAccountId="+info.getAccountID();
			sql += " and b.dtDate = to_date('" + DataFormat.formatDate(DataFormat.getPreviousDate(info.getStartDate())) + "','yyyy-mm-dd') ";
			sql += " group by a.al_nloannoteid ";
			log.info("通过放款通知单取得贷款余额: \n"+sql);
			ps = connInternal.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				dRtn = rs.getDouble("mLoanBalance");
			}
		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(connInternal);
		}

		return dRtn;
	}
	
	/**
	 * 通过放款通知单取得贷款计息余额
	 * @author 马现福
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public double getLoanHisBalanceByLoanNoteIDForIntr(QueryTransAccountDetailWhereInfo info) throws Exception
	{
		double dRtn = 0.0;
		if (info.getLoanNoteID() <= 0)
		{
			return dRtn;
		}
		Connection connInternal = null;
		connInternal = this.getConnection();

		//Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			//conn = this.getConnection();
			String sql = " select sum(nvl(b.Minterestbalance,0)+nvl(b.ac_mnegotiatebalance,0)) mLoanBalance  ";
			sql += " from sett_subaccount a,sett_DailyAccountBalance b";
			sql += " where a.id = b.nSubaccountid ";
			sql += " and a.al_nloannoteid=" + info.getLoanNoteID();
			sql += " and b.nAccountId="+info.getAccountID();
			sql += " and b.dtDate = to_date('" + DataFormat.formatDate(DataFormat.getPreviousDate(info.getStartDate())) + "','yyyy-mm-dd') ";
			sql += " group by a.al_nloannoteid ";
			log.info("通过放款通知单取得贷款余额: \n"+sql);
			ps = connInternal.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				dRtn = rs.getDouble("mLoanBalance");
			}
		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(connInternal);
		}

		return dRtn;
	}
	
	//通过合同号取得贷款余额
	public double getLoanHisBalanceByContractID(QueryTransAccountDetailWhereInfo info) throws Exception
	{
		double dRtn = 0.0;
		if (info.getContractID() <= 0)
		{
			return dRtn;
		}
		Connection connInternal = null;

		connInternal = this.getConnection();

		//Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			//conn = this.getConnection();
			String sql = " select sum(c.mbalance) mLoanBalance ";
			sql += " from sett_subaccount a,loan_payform b,sett_DailyAccountBalance c";
			sql += " where a.id = c.nSubaccountid ";
			sql += " and a.al_nloannoteid= b.id ";
			sql += " and b.nContractID=" + info.getContractID();
			sql += " and c.nAccountId="+info.getAccountID();
			sql += " and c.dtDate = to_date('" + DataFormat.formatDate(DataFormat.getPreviousDate(info.getStartDate())) + "','yyyy-mm-dd') ";
			log.info("通过合同号取得贷款余额: \n"+sql);
			ps = connInternal.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				dRtn = rs.getDouble("mLoanBalance");
			}
		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(connInternal);
		}

		return dRtn;
	}

	/**
	 * 通过合同号取得贷款计息余额
	 * @author 马现福
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public double getLoanHisBalanceByContractIDForIntr(QueryTransAccountDetailWhereInfo info) throws Exception
	{
		double dRtn = 0.0;
		if (info.getContractID() <= 0)
		{
			return dRtn;
		}
		Connection connInternal = null;

		connInternal = this.getConnection();

		//Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			//conn = this.getConnection();
			String sql = " select sum(nvl(c.Minterestbalance,0)+nvl(c.ac_mnegotiatebalance,0)) mLoanBalance ";
			sql += " from sett_subaccount a,loan_payform b,sett_DailyAccountBalance c";
			sql += " where a.id = c.nSubaccountid ";
			sql += " and a.al_nloannoteid= b.id ";
			sql += " and b.nContractID=" + info.getContractID();
			sql += " and c.nAccountId="+info.getAccountID();
			sql += " and c.dtDate = to_date('" + DataFormat.formatDate(DataFormat.getPreviousDate(info.getStartDate())) + "','yyyy-mm-dd') ";
			log.info("通过合同号取得贷款余额: \n"+sql);
			ps = connInternal.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				dRtn = rs.getDouble("mLoanBalance");
			}
		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(connInternal);
		}

		return dRtn;
	}

	
	/**
	* 得到执行利率，参数lLoanID和lContractID必传入一个，不传入的话请设置为-1。
	* Create Date: 2003-10-15
	* @param lLoanID 贷款ID
	* @param lContractID 合同ID
	* @param tsDate 时间，如传入为NULL值或空串则默认为当前日期。
	* @return double 执行利率
	* @exception Exception
	*/
	private RateInfo getLatelyRate(Connection con, long lLoanID, long lContractID, Timestamp tsDate) throws Exception
	{
		RateInfo info = new RateInfo();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conInternal = null;
		try
		{
			if (con == null)
			{
				conInternal = this.getConnection();
			}
			else
			{
				conInternal = con;
			}
			if (tsDate == null || tsDate.equals(""))
			{
				tsDate = DataFormat.getDateTime(conInternal);
			}
			StringBuffer sbSQL = new StringBuffer();
			if (lContractID > 0)
			{
				sbSQL.append(" SELECT a.dtStartDate,b.mRate, b.ID,a.mAdjustRate,a.mStaidAdjustRate ");
				sbSQL.append(" FROM loan_rateAdjustContractDetail a,loan_interestRate b,loan_contractForm c");
				sbSQL.append(" WHERE c.ID = a.nContractID ");
				sbSQL.append(" AND TO_CHAR(a.dtStartDate,'yyyymmdd')<= TO_CHAR(?,'yyyymmdd')");
				sbSQL.append(" AND a.nBankInterestID = b.id(+) ");
				sbSQL.append(" AND a.nContractID = ? ");
				sbSQL.append(" ORDER BY  a.dtStartDate DESC ");
				log.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				ps.setTimestamp(1, tsDate);
				ps.setLong(2, lContractID);
				rs = ps.executeQuery();
				if (rs.next())
				{
					info.setLateBasicRate(rs.getDouble("mRate")); //调整后的基准利率
                    //======ninh 2004-06-22 需求变更 增加固定浮动利率===执行利率算法改变===//
					info.setLateRate(info.getLateBasicRate() * (1 + rs.getDouble("mAdjustRate") / 100) + rs.getDouble("mStaidAdjustRate"));
					info.setLateAdjustRate(rs.getDouble("mAdjustRate"));
					info.setLateStaidAdjustRate(rs.getDouble("mStaidAdjustRate"));
					//调整后的利率
					info.setLateBankInterestID(rs.getLong("ID"));
					//调整后的基准利率ID	
					info.setAdjustDate(rs.getTimestamp("dtStartDate")); //调整时间
					System.out.println("1" + info.getLateRate());
				}
				cleanup(rs);
				cleanup(ps);
			}
			sbSQL.setLength(0);
			sbSQL.append(" SELECT b.nTypeID,b.mInterestRate,b.nBankInterestID,b.mAdjustRate,b.mStaidAdjustRate,c.mRate ");
			sbSQL.append(" FROM loan_contractForm b,loan_interestRate c");
			sbSQL.append(" WHERE b.id = ? ");
			sbSQL.append(" AND b.nBankInterestID = c.id(+) ");
			ps = conInternal.prepareStatement(sbSQL.toString());
			ps.setLong(1, lContractID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				long loanTypeID = rs.getLong("nTypeID");
				if ((loanTypeID == LOANConstant.LoanType.WT))
				{
					info.setBasicRate(rs.getDouble("mInterestRate"));
				}
				else
				{
					info.setBasicRate(rs.getDouble("mRate"));
				}
				
				 //未调整的基准利率
				info.setBankInterestID(rs.getLong("nBankInterestID"));
				//未调整的利率ID
				//======ninh 2004-06-22 需求变更 增加固定浮动利率===执行利率算法改变===//
				info.setRate(info.getBasicRate() * (1 + rs.getDouble("mAdjustRate") / 100) + rs.getDouble("mStaidAdjustRate"));
				info.setAdjustRate(rs.getDouble("mAdjustRate"));
				info.setStaidAdjustRate(rs.getDouble("mStaidAdjustRate"));
				//未调整的利率
			}
			else
			{
				sbSQL.setLength(0);
				sbSQL.append("SELECT mInterestRate,nBankInterestID,mAdjustRate,mStaidAdjustRate FROM loan_loanform WHERE id = ? ");
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lLoanID);
				rs = ps.executeQuery();
				if (rs.next())
				{
					info.setBasicRate(rs.getDouble("mInterestRate"));
					//未调整的基准利率
					info.setBankInterestID(rs.getLong("nBankInterestID"));
					//未调整的利率ID
					//======ninh 2004-06-22 需求变更 增加固定浮动利率===执行利率算法改变===//
					info.setRate(rs.getDouble("mInterestRate") * (1 + rs.getDouble("mAdjustRate") / 100) + rs.getDouble("mStaidAdjustRate"));
					//未调整的利率
				}
			}
			//如果利率未调整过，取未调整的值
			if (info.getLateBankInterestID() <= 0)
			{
				info.setLateBankInterestID(info.getBankInterestID());
				info.setLateBasicRate(info.getBasicRate());
				info.setLateRate(info.getRate());
				info.setLateAdjustRate(info.getAdjustRate());
				info.setLateStaidAdjustRate(info.getStaidAdjustRate());
			}
		}
		catch (Exception e)
		{
			log.error(e.toString());
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
				if (con == null)
				{
					conInternal.close();
					conInternal = null;
				}
			}
			catch (Exception e)
			{
				log.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return info;
	}
	//将贷款账户的查询的结果集转化Collection
	private Collection addLoanInfoIntoCln(ResultSet rs, Connection conn, long lOfficeID, long lCurrencyID) throws Exception
	{
		ArrayList list = new ArrayList();
		OBAccountBalanceLoanInfo info = null;
		OBAccountBalanceLoanInfo subSumInfo = null;
		OBAccountBalanceLoanInfo tmpInfo = null;
		long DISCOUNTACCOUNT  = SETTConstant.AccountGroupType.DISCOUNT ; // 贴现帐户类型组
		Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
	    AccountTypeInfo accountTypeInfo = null;
		//起始账户类型
		long nFrtAcctType = -1;
		//起始账号
		String sFrtAcctNo = null;

		long nAcctType = -1;
		String sAcctNo = null;

		double subSumOfCapital = 0.0;
		double subSumOfBalance = 0.0;

		double mLoanBalance = 0.0;

		while (rs.next())
		{
			info = new OBAccountBalanceLoanInfo();
			tmpInfo = new OBAccountBalanceLoanInfo();
			//取得本条记录的账户类型
			nAcctType = rs.getLong("naccounttypeid");

			info.setAccountID(rs.getLong("AccountID"));
			info.setContractID(rs.getLong("contractID"));

			RateInfo rateInfo = new RateInfo();
			rateInfo = getLatelyRate(conn, -1, info.getContractID(), Env.getSystemDate(lOfficeID, lCurrencyID));

			//调整后执行利率
			info.setRate(rateInfo.getLateRate());

			//取得本条记录的账号
			sAcctNo = rs.getString("saccountno");

			System.out.println("=========nAcctType=======:" + nAcctType);
			//如果账户类型与初始账户类型相同
			if (nAcctType == nFrtAcctType)
			{
				//设置账户类型为0;  表示本条记录中账户记录不为空,但是不必显示				
				info.setNaccounttypeid(0);

				//如果账户类型相同，账号也相同 
				if (sAcctNo.equals(sFrtAcctNo))
				{
					//将账户类型设置为0,表示存在,但是不必显示
					info.setNaccounttypeid(0);
					//将账号栏显示为合同号
					info.setSaccountno(rs.getString("SCONTRACTCODE"));
					//设置借款单位
					info.setNborrowclientid(rs.getLong("nborrowclientid"));
					//设置起始日 、 到期日;	
					info.setDtStartDate(rs.getTimestamp("dtStartDate"));
					info.setDtEndDate(rs.getTimestamp("dtEndDate"));
					//期限
					info.setNIntervalNum(rs.getLong("nIntervalNum"));
					//设置贷款款金额和贷款余额
					info.setMAmount(rs.getDouble("mAmount"));
					accountTypeInfo = sett_AccountTypeDAO.findByID(nAcctType);
					if( accountTypeInfo.getAccountGroupID() == DISCOUNTACCOUNT){
						mLoanBalance = this.getDiscountLoanBalanceByContractID(rs.getLong("contractID"), conn);
						info.setRate(this.getRateByContractID( rs.getLong("contractID"), 3));
					}
					else{
						mLoanBalance = this.getLoanBalanceByContractID(rs.getLong("contractID"), conn);
					}
					//mLoanBalance = this.getLoanBalanceByContractID(rs.getLong("contractID"), conn);
					info.setLoanBalance(mLoanBalance);
					//设置利率  调用方法取执行利率
					//设置合同状态
					info.setNstatusid(rs.getLong("nstatusid"));

					info.setContractID(rs.getLong("contractID"));
					list.add(info);

					//统计小计值
					subSumOfCapital += rs.getDouble("mAmount");
					subSumOfBalance += mLoanBalance;
				}
				//账户类型相同，但账号不同
				else
				{
					//将账户类型设置为0,表示存在,但是不必显示
					tmpInfo.setNaccounttypeid(0);
					//将账号栏显示为账号
					tmpInfo.setSaccountno(rs.getString("saccountno"));
					//将其他栏位都设置为空 ；
					//设置借款单位为空 标示为－1
					tmpInfo.setNborrowclientid(-1);
					//起始日 、 到期日都置为空
					tmpInfo.setDtStartDate(null);
					tmpInfo.setDtEndDate(null);
					//期限栏显示 “ 小计 ”   标示为-2	
					tmpInfo.setNIntervalNum(-2);
					//贷款金额栏置为-1
					tmpInfo.setMAmount(-1);
					//贷款余额栏置为-1  
					tmpInfo.setLoanBalance(-1);
					//设置利率(执行利率)为－1
					//设置合同状态为－1	
					tmpInfo.setNstatusid(-1);

					//添加上面的记录
					list.add(tmpInfo);

					//将账户类型设置为0,表示存在,但是不必显示
					info.setNaccounttypeid(0);
					//将账号栏显示为合同号
					info.setSaccountno(rs.getString("SCONTRACTCODE"));
					//设置借款单位
					info.setNborrowclientid(rs.getLong("nborrowclientid"));
					//设置起始日 、 到期日;	
					info.setDtStartDate(rs.getTimestamp("dtStartDate"));
					info.setDtEndDate(rs.getTimestamp("dtEndDate"));
					//期限
					info.setNIntervalNum(rs.getLong("nIntervalNum"));
					//设置贷款款金额和贷款余额
					info.setMAmount(rs.getDouble("mAmount"));
					mLoanBalance = this.getLoanBalanceByContractID(rs.getLong("contractID"), conn);
					info.setLoanBalance(mLoanBalance);
					//设置利率  调用方法取执行利率
					//设置合同状态
					info.setNstatusid(rs.getLong("nstatusid"));

					list.add(info);

					//统计存款金额、存款余额
					subSumOfCapital += rs.getDouble("mAmount");
					subSumOfBalance += mLoanBalance;

					sFrtAcctNo = sAcctNo;
				}
			}
			//账户类型不同，则账号不可能相同！
			else //如果账户类型与初始账户类型不同,且账户类型不为空
				{
				if (nFrtAcctType != -1)
				{
					subSumInfo = new OBAccountBalanceLoanInfo();
					/* 设置上一个小计值*/
					// 将账户类型值设置为 － 1 
					subSumInfo.setNaccounttypeid(-1);
					// 账号设置为空 
					subSumInfo.setSaccountno("");
					//设置借款单位为空 标示为－1
					subSumInfo.setNborrowclientid(-1);
					//起始日 、 到期日都置为空	
					subSumInfo.setDtStartDate(null);
					subSumInfo.setDtEndDate(null);
					//期限栏显示 “ 小计 ”   标示为-2	
					subSumInfo.setNIntervalNum(-2);
					//贷款金额栏置为subSumOfCapital 	
					subSumInfo.setMAmount(subSumOfCapital);
					//贷款余额栏置为subSumOfBalance
					subSumInfo.setLoanBalance(subSumOfBalance);
					//设置利率为－1
					subSumInfo.setRate(-1);
					//设置合同状态为－1	
					subSumInfo.setNstatusid(-1);

					list.add(subSumInfo);
				}

				//设置初始账户类型为本条记录的账户类型;
				nFrtAcctType = nAcctType;
				//设置初始账号为本条记录的账号
				sFrtAcctNo = sAcctNo;
				tmpInfo.setNaccounttypeid(rs.getLong("naccounttypeid"));
				tmpInfo.setSaccountno(rs.getString("saccountno"));

				//设置借款单位为空 标示为－1
				tmpInfo.setNborrowclientid(-1);
				//起始日 、 到期日都置为空	
				tmpInfo.setDtStartDate(null);
				tmpInfo.setDtEndDate(null);
				//期限栏显示空   标示为-1	
				tmpInfo.setNIntervalNum(-1);
				//贷款金额栏置为-1
				tmpInfo.setMAmount(-1);
				//贷款余额栏置为-1
				tmpInfo.setLoanBalance(-1);
				//设置利率为－1
				tmpInfo.setRate(-1);
				//设置合同状态为－1
				tmpInfo.setNstatusid(-1);

				//添加上面的记录
				list.add(tmpInfo);

				//将小计值置为0.00;
				subSumOfCapital = 0.00;
				subSumOfBalance = 0.00;

				//如果账号相同
				if (sFrtAcctNo.equals(sAcctNo))
				{
					//将账户类型设置为0,表示存在,但是不必显示
					info.setNaccounttypeid(0);
					//将账号栏显示为合同号
					info.setSaccountno(rs.getString("SCONTRACTCODE"));
					//设置借款单位
					info.setNborrowclientid(rs.getLong("nborrowclientid"));
					//设置起始日 、 到期日 
					info.setDtStartDate(rs.getTimestamp("dtStartDate"));
					info.setDtEndDate(rs.getTimestamp("dtEndDate"));
					//设置期限
					info.setNIntervalNum(rs.getLong("nIntervalNum"));
					//设置贷款金额 贷款余额
					info.setMAmount(rs.getDouble("mAmount"));
					accountTypeInfo = sett_AccountTypeDAO.findByID(nAcctType);
					if( accountTypeInfo.getAccountGroupID() == DISCOUNTACCOUNT){
						mLoanBalance = this.getDiscountLoanBalanceByContractID(rs.getLong("contractID"), conn);
						info.setRate(this.getRateByContractID( rs.getLong("contractID"), 3));
					}
					else{
						mLoanBalance = this.getLoanBalanceByContractID(rs.getLong("contractID"), conn);
					}
					info.setLoanBalance(mLoanBalance);
					//设置利率  调用方法取执行利率
					//设置合同状态
					info.setNstatusid(rs.getLong("nstatusid"));
					//添加本条记录
					list.add(info);

					//统计小计值
					subSumOfCapital += rs.getDouble("mAmount");
					subSumOfBalance += mLoanBalance;
				}
				else //账号不同
					{
					//将账户类型设置为0,表示存在,但是不必显示
					info.setNaccounttypeid(0);
					//将账号栏显示为账号
					info.setSaccountno(rs.getString("saccountno"));
					/*将其他栏位都设置为空 */
					//设置借款单位为空 标示为－1
					info.setNborrowclientid(-1);
					//起始日 、 到期日都置为空	
					info.setDtStartDate(null);
					info.setDtEndDate(null);
					//期限栏显示空   标示为-1	
					info.setNIntervalNum(rs.getLong("nIntervalNum"));
					//贷款金额栏置为空 标示为－1	
					info.setMAmount(-1);
					//贷款余额栏置为空 标示为－1	
					info.setMAmount(-1);
					//设置利率为－1
					info.setRate(-1);
					//设置合同状态为－1	
					info.setNstatusid(-1);

					//添加上面的记录
					list.add(info);

					//将账户类型设置为0,表示存在,但是不必显示
					info.setNaccounttypeid(0);
					//将账号栏显示为合同号
					info.setSaccountno(rs.getString("SCONTRACTCODE"));
					//设置借款单位
					info.setNborrowclientid(rs.getLong("nborrowclientid"));
					//设置起始日 、 到期日 
					info.setDtStartDate(rs.getTimestamp("dtStartDate"));
					info.setDtEndDate(rs.getTimestamp("dtEndDate"));
					//设置期限
					info.setNIntervalNum(rs.getLong("nIntervalNum"));
					//设置存款金额 、 存款余额
					info.setMAmount(rs.getDouble("mAmount"));
					mLoanBalance = this.getLoanBalanceByContractID(rs.getLong("contractID"), conn);
					info.setLoanBalance(mLoanBalance);
					//设置利率  调用方法取执行利率
					//设置合同状态
					info.setNstatusid(rs.getLong("nstatusid"));

					list.add(info);

					//统计贷款金额、贷款余额
					//统计小计值
					subSumOfCapital += rs.getDouble("mAmount");
					subSumOfBalance += mLoanBalance;
				}
			}
		}
		subSumInfo = new OBAccountBalanceLoanInfo();
		//将账户类型值设置为 － 1 
		subSumInfo.setNaccounttypeid(-1);
		// 账号设置为空 
		subSumInfo.setSaccountno("");
		//起始日 、 到期日都置为空	
		subSumInfo.setDtStartDate(null);
		subSumInfo.setDtEndDate(null);
		//期限栏显示 “ 小计 ”   标示为-2	
		subSumInfo.setNIntervalNum(-2);
		//贷款金额栏置为subSumOfCapital
		subSumInfo.setMAmount(subSumOfCapital);
		//贷款余额栏置为subSumOfBalance  
		subSumInfo.setLoanBalance(subSumOfBalance);
		//设置利率为－1
		subSumInfo.setRate(-1);
		//设置合同状态为－1
		subSumInfo.setNstatusid(-1);

		list.add(subSumInfo);
		return list;
	}

	public static void main(String[] args)
	{
		try
		{
			/*
			 */
			OBAccountBalanceQueryDao a = new OBAccountBalanceQueryDao();
			//Connection conn = Database.getConnection();
			//Timestamp tsDate = new Timestamp(103, 11, 7, 1, 1, 1, 0);
			//System.out.println(tsDate);
			//a.dealContractForOpen(conn,1,1,tsDate);
			Collection c = a.seekMarginBalace(71,1,1,1);
			System.out.println("size="+c.size());
		}
		catch (Exception e)
		{
		}
	}
	private double[] getPsFromCurrentAccountInfo(PreparedStatement ps,ResultSet rs,Connection con,OBAccountQueryInfo info,int type) throws Exception{
		int MorY = -1;
		if(type==0){//月份
			MorY = 7;
		}
		if(type==1){//年份
			MorY = 4;
		}
		double[] resulhj = new double[2];
		java.util.Arrays.fill(resulhj, 0);
		StringBuffer buffer = new StringBuffer();
		buffer
				.append(
						"select sum(d.mamount) mamount , d.ntransdirection from SETT_TRANSACCOUNTDETAIL d,sett_subAccount s,sett_account a  \n")
				.append(
						"where d.nsubaccountid = s.id and d.ntransaccountid = a.id and d.nStatusID != 0   \n")
				.append(
						"and a.id = ? and a.NOFFICEID = ? and a.NCURRENCYID = ? \n")
				.append("and SUBSTR(to_char(d.dtexecute,'yyyy-mm-dd'),0,")
				.append(MorY)
				.append(") = ? \n")
				.append(
						"group by d.ntransdirection order by d.ntransdirection \n");
		ps = con.prepareStatement(buffer.toString());
		log.info(buffer.toString());
		int index = 1;
		ps.setLong(index++, info.getAccountID());
		ps.setLong(index++, info.getOfficeID());
		ps.setLong(index++, info.getCurrencyID());
		ps.setString(index++, DataFormat.formatDate(info.getExecuteDate())
				.substring(0, MorY));
		rs = ps.executeQuery();
		if(rs.next()){
			if(rs.getInt(2) == SETTConstant.DebitOrCredit.DEBIT)
				resulhj[0] = rs.getDouble(1);
			if(rs.getInt(2) == SETTConstant.DebitOrCredit.CREDIT)
				resulhj[1] = rs.getDouble(1);
		}
		if(rs.next()){
			resulhj[1] = rs.getDouble(1);
		}
		rs = null;
		ps = null;
		return resulhj;
	}
	public double[] findByCurrentAccountInfo(OBAccountQueryInfo info) throws Exception
	{
		double[] resulhj = new double[4];
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			double[] tmp = getPsFromCurrentAccountInfo(ps,rs,con,info,0);
			if(tmp!=null){
				
				resulhj[0] = tmp[0];
				resulhj[1] = tmp[1];
			}else{
				resulhj[0] = 0;
				resulhj[1] = 0;
			}
				tmp= null;
				tmp = getPsFromCurrentAccountInfo(ps,rs,con,info,1);
			if(tmp!=null){
				resulhj[2] = tmp[0];
				resulhj[3] = tmp[1];
				}else{
					resulhj[2] = 0;
					resulhj[3] = 0;
			}
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
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}

		}
		return resulhj;

	}
	/**
	 * 
	 * @param lContarctID
	 * @param conn
	 * @return
	 */
	private double getDiscountLoanBalanceByContractID(long lContarctID, Connection conn) throws Exception{
	double dRtn = 0.0;
	Connection connInternal = null;
	if (conn == null)
	{
		connInternal = this.getConnection();
	}
	else
	{
		connInternal = conn;
	}
	PreparedStatement ps = null;
	ResultSet rs = null;
	try
	{
		String sql =
			" select sum(mbalance) mLoanBalance from loan_contractform a ,loan_discountcredence b ,sett_subaccount c \n"
				+ " where c.al_nloannoteid = b.id and b.ncontractid = a.id and a.id = "
				+ lContarctID ;
		log.debug("sql = "  + sql );
		ps = connInternal.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next())
		{
			dRtn = rs.getDouble("mLoanBalance");
		}
	}
	finally
	{
		this.cleanup(rs);
		this.cleanup(ps);
		if (conn == null)
		{
			this.cleanup(connInternal);
		}
	}

	return dRtn;
}
	 /**
	    * 用ID或code获得名称方法的公用方法2
	    * @param strSQL                 sql
	    * @param strField                   要得到的字段
	    * @return
	    */
	   public static double getRateByContractID( long contractID , long type ){
	       PreparedStatement ps = null;
	       ResultSet rs = null;
	       Connection conn = null;
	       double rate = 0.0 ;
	       String sql = " select MDISCOUNTRATE rate from loan_contractform where ntypeid = " + type + " and id = " + contractID ;
	       try
	       {
	           try {
				conn = Database.getConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	           ps = conn.prepareStatement( sql );
	           rs = ps.executeQuery();
	           while (rs.next())
	           {
	        	   rate = rs.getDouble( "rate");
	           }
	       }
	       catch (SQLException e)
	       {
	           e.printStackTrace();
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
	           catch (Exception _ex)
	           {
	               System.out.println("关闭数据库连接时发生数据库错误！");
	           }
	       }
	       return rate ;
	   } 
	   
	   /**
	    * 通过账户ID，查询该账户的开户机构及开户币种
	    * @param strSQL                 sql
	    * @param strField                   要得到的字段
	    * @return
	    */
	   public OBAccountQueryInfo getOfficeAndCurrencyByAccountID( long accountid){
		   OBAccountQueryInfo info = new OBAccountQueryInfo();
	       PreparedStatement ps = null;
	       ResultSet rs = null;
	       Connection conn = null;
	       String sql = " select nofficeid,ncurrencyid from sett_account where id = " + accountid + " ";
	       try
	       {
	           try {
				conn = Database.getConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
	           ps = conn.prepareStatement( sql );
	           rs = ps.executeQuery();
	           while (rs.next())
	           {
	        	   info.setOfficeID(rs.getLong("nofficeid"));
	        	   info.setCurrencyID(rs.getLong("ncurrencyid"));
	           }
	       }
	       catch (SQLException e)
	       {
	           e.printStackTrace();
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
	           catch (Exception _ex)
	           {
	               System.out.println("关闭数据库连接时发生数据库错误！");
	           }
	       }
	       return info ;
	   } 
}
