package com.iss.itreasury.ebank.obinterest.dao;
import java.sql.*;
import java.util.Vector;
import com.iss.itreasury.settlement.interest.dataentity.LoanNoticeInfo;
import com.iss.itreasury.util.*;
import com.iss.itreasury.settlement.util.*;
public class OBInterestDao
{
	private static Log4j log4j = null;
	public OBInterestDao()
	{
		log4j = new Log4j(Constant.ModuleType.EBANK, this);
	}
	/**
	 * Method queryLoanNotice.
	 * 根据info中的放款通知单id以及通知书类型查询表sett_LoanNotice中的相关记录
	 * 如果存在相关记录则返回查询结果集 
	 * 如果不存在主键为-1,并且包含子账户id,和贷款账户id的info
	 * @param info
	 * @return Vector
	 * @throws Exception
	 */
	public Vector queryLoanNotice(LoanNoticeInfo info) throws Exception, IException
	{
		Vector vctResult = new Vector();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = new StringBuffer();
		long lSubAccountID = -1;
		long lAccountID = -1;
		Timestamp dtClearInterest = null;
		try
		{
			con = Database.getConnection();
			String strSQL = " select a.*,b.dtend from SETT_SUBACCOUNT a,loan_payform b where a.AL_NLOANNOTEID=? " +
				"and a.NSTATUSID=? and a.AL_NLOANNOTEID= b.id";
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, info.getLoanNoteID());
			ps.setLong(2, Constant.RecordStatus.VALID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lSubAccountID = rs.getLong("ID");
				lAccountID = rs.getLong("NACCOUNTID");
				dtClearInterest = rs.getTimestamp("dtend"); //放款通知单的结息日期
				log4j.info("==========子账户id:" + lSubAccountID);
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
			if (lSubAccountID > 0)
			{
				sbSQL = new StringBuffer();
				sbSQL.append("select * from sett_LoanNotice where NSUBACCOUNTID=? and NFORNTYPE=? ");
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lSubAccountID);
				log4j.info("通知书类型:" + info.getFormTypeID());
				ps.setLong(2, info.getFormTypeID());
				rs = ps.executeQuery();
				while (rs.next())
				{
					log4j.info("======存在通知书===");
					info = new LoanNoticeInfo();
					info.setID(rs.getLong("ID"));
					info.setOfficeID(rs.getLong("NOFFICEID"));
					info.setCurrencyID(rs.getLong("NCURRENCYID"));
					info.setAccountID(rs.getLong("NACCOUNTID"));
					info.setSubAccountID(rs.getLong("NSUBACCOUNTID"));
					info.setContractNo(rs.getString("SCONTRACTCODE"));
					info.setAssureContractNo(rs.getString("SASSURECODECODE"));
					info.setPayFormNo(rs.getString("SPAYFORMCODE"));
					info.setBorrowClientName(rs.getString("SBORROWCLIENTNAME"));
					info.setAssureClientName(rs.getString("SASSURECLIENTNAME"));
					info.setFormTypeID(rs.getLong("NFORNTYPE"));
					info.setFormYear(rs.getString("SFORMYEAR"));
					info.setFormNo(rs.getString("SFORMCODE"));
					info.setFormNum(rs.getLong("NFORMNO"));
					info.setExecuteDate(rs.getTimestamp("DTEXECUTE"));
					info.setClearInterestDate(rs.getTimestamp("DTINTEREST"));
					info.setLoanAmount(rs.getDouble("MOPENAMOUNT"));
					info.setLoanStartDate(rs.getTimestamp("DTSTART"));
					info.setLoanEndDate(rs.getTimestamp("DTEND"));
					info.setLoanTerm(rs.getLong("NINTERVALNUM"));
					info.setOriginalContractRate(rs.getDouble("MCONTRACTINTERESTRATE"));
					info.setLoanBalance(rs.getDouble("MBALANCE"));
					info.setExecuteRate(rs.getDouble("MEXECUTEINTERESTRATE"));
					info.setNewExecuteRate(rs.getDouble("MNEWEXECUTEINTERESTRATE"));
					info.setExecuteRateValidDate(rs.getTimestamp("DTINTERESTRATEVALID"));
					info.setInterest(rs.getDouble("MINTEREST"));
					info.setSuretyFee(rs.getDouble("MSURETYFEE"));
					info.setCompoundInterest(rs.getDouble("MCOMPOUNDINTEREST"));
					info.setCommissionRate(rs.getDouble("MCOMMISSIONRATE"));
					info.setCommission(rs.getDouble("MCOMMISSION"));
					info.setOverDueInterestRate(rs.getDouble("MOVERCOMMISSIONRATE"));
					info.setOverDueInterest(rs.getDouble("MOVERDUEINTEREST"));
					info.setAllInterest(rs.getDouble("MALLINTEREST"));
					info.setTotalInterest(rs.getDouble("MTOTAL"));
					info.setStatusID(rs.getLong("NSTATUSID"));
					vctResult.add(info);
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
				if (con != null)
				{
					con.close();
					con = null;
				}
				if (vctResult.isEmpty())
				{
					log4j.info("======不存在通知书===");
					info.setID(-1);
					info.setSubAccountID(lSubAccountID);
					info.setAccountID(lAccountID);
					info.setClearInterestDate(dtClearInterest);
					log4j.info("============结息日期:" + info.getClearInterestDate());
				}
			}
			else
			{
				throw new IException("没有找到放款通知单对应的子账户！");
			}
			vctResult.add(info);
		}
		catch (Exception e)
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
		return vctResult;
	}
	public long[] findPayFormByContractID(long lContractID) throws Exception, IException
	{
		long[] rtnLoanNoteID = new long[] { -1 };
		int lLength = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = new StringBuffer();
		try
		{
			con = Database.getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append("select  count(*) num from loan_payform where NCONTRACTID=? and nstatusid >0 ");
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lContractID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lLength = rs.getInt("num");
				log4j.info("数组长度：" + lLength);
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
			if (lLength > 0)
			{
				rtnLoanNoteID = new long[lLength];
				sbSQL = new StringBuffer();
				sbSQL.append("select id from loan_payform where NCONTRACTID=? and nstatusid >0 ");
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lContractID);
				rs = ps.executeQuery();
				int i = 0;
				while (rs.next())
				{
					rtnLoanNoteID[i] = rs.getLong("id");
					i++;
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
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (Exception e)
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
		return rtnLoanNoteID;
	}
	public static void main(String[] args)
	{
		try
		{
			OBInterestDao obInterestDao = new OBInterestDao();
			LoanNoticeInfo info = new LoanNoticeInfo();
			info.setFormTypeID(3);
			info.setLoanNoteID(1544);
			info.setContractID(274);
			/*long[] tempLong = obInterestDao.findPayFormByContractID(info.getContractID());
			log4j.info("=====:" + tempLong[0]);
			*/
			Vector vctResult = obInterestDao.queryLoanNotice(info);
			if (vctResult != null)
			{
				log4j.info("not null");
				info = (LoanNoticeInfo) vctResult.elementAt(0);
				log4j.info("=====:" + info.getCurrencyID());
				log4j.info("=====:" + info.getOfficeID());
				log4j.info("=====:" + info.getSubAccountID());
			}
			
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
}