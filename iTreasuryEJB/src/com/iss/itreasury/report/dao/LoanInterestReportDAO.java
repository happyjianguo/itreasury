package com.iss.itreasury.report.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.report.dateentity.LoanContractInfo;
import com.iss.itreasury.report.dateentity.LoanInterestReportInfo;
import com.iss.itreasury.report.dateentity.SettLoanInterestInfo;
import com.iss.itreasury.treasuryplan.util.TPConstant.DataSource;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;

/**
 * 应收应付利息报表数据生成及查询DAO
 * @author yunzhou
 * @date 2011-01-17
 */
public class LoanInterestReportDAO {
	
	/**
	 * 保存应收应付利息报表数据
	 * @author yunzhou
	 * @date 2011-01-17
	 * @param interestReportInfo
	 * @param conn
	 * @return Connection con
	 * @throws Exception
	 */
	public Connection saveLoanInterestReport(LoanInterestReportInfo interestReportInfo,Connection conn) throws Exception {
		
		StringBuffer strSql = null;
		PreparedStatement ps = null;
		
		try {
			//如果传入的Conn数据源为空，则重新获取一个数据源
			if(conn == null)
				conn = Database.getConnection();
			
			strSql = new StringBuffer();
			strSql.append("insert into sett_loaninterestreport values ");
			strSql.append("(seq_loaninterestreport.nextval, ");
			strSql.append(" " + interestReportInfo.getLContractId() + ", ");
			strSql.append(" " + interestReportInfo.getLLoanAccountId() + ", ");
			strSql.append(" " + interestReportInfo.getLLoanReceivedAccountId() + ", ");
			strSql.append(" " + interestReportInfo.getLLoanClientId() + ", ");
			strSql.append(" to_date('" + interestReportInfo.getStrInterestDate() + "','yyyy-mm-dd'), ");
			strSql.append(" to_date('" + interestReportInfo.getStrDate() + "','yyyy-mm-dd'), ");
			strSql.append(" " + interestReportInfo.getDbYSInterest() + ", ");
			strSql.append(" " + interestReportInfo.getDbSSInterest() + ", ");
			strSql.append(" " + interestReportInfo.getDbWSInterest() + ", ");
			strSql.append(" " + interestReportInfo.getDbSQQInterest() + ", ");
			strSql.append(" " + interestReportInfo.getDbSBQInterest() + ", ");
			strSql.append(" " + interestReportInfo.getDbBQQXInterest()+ ") ");
			System.out.println("saveLoanInterestReport Sql : " + strSql.toString());
			
			ps = conn.prepareStatement(strSql.toString());
			ps.execute();
			
		} catch(Exception e) {
			e.printStackTrace();
			throw new IException("保存应收应付利息报表数据失败");
		} finally {
			if(ps != null)
				ps.close();
		}
		
		return conn;
	}
	
	/**
	 * 清空应收应付利息报表对应表中的所有数据
	 * @author yunzhou
	 * @date 2011-01-17
	 * @param conn
	 * @return Connection con
	 * @throws Exception
	 */
	public Connection cleanLoanInterestReport(Connection conn) throws Exception {
		
		StringBuffer strSql = null;
		PreparedStatement ps = null;
		
		try {
			//如果传入的Conn数据源为空，则重新获取一个数据源
			if(conn == null)
				conn = Database.getConnection();
			
			strSql = new StringBuffer();
			strSql.append("delete from sett_loaninterestreport ");
			System.out.println("cleanLoanInterestReport Sql : " + strSql.toString());
			
			ps = conn.prepareStatement(strSql.toString());
			ps.execute();
			
		} catch(Exception e) {
			e.printStackTrace();
			throw new IException("清空应收应付利息报表对应表中的所有数据失败");
		} finally {
			if(ps != null)
				ps.close();
		}
		
		return conn;
	}
	
	/**
	 * 获取合同信息及对应的结息计划设置
	 * @author yunzhou
	 * @date 2011-01-17
	 * @return
	 * @throws Exception
	 */
	public Collection getLoanContractList() throws Exception {
		Collection coll = null;
		StringBuffer strSql = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = Database.getConnection();
			strSql = new StringBuffer();
			strSql.append("select distinct lc.id, ");
			strSql.append(" lc.scontractcode, ");
			strSql.append(" lc.nsubtypeid, ");
			strSql.append(" lc.ntypeid, ");
			strSql.append(" decode(sc.cleartype, null, 2, sc.cleartype) cleartype, ");
			strSql.append(" decode(sc.cleartime, null, 21, sc.cleartime) cleartime ");
			strSql.append(" from loan_contractform lc, sett_clearinterestplan sc ");
			strSql.append(" where lc.ntypeid in (1, 2) ");
			strSql.append(" and lc.nstatusid in (5, 6, 7, 8, 9, 10) ");
			strSql.append(" and lc.nsubtypeid = sc.subloantype(+) ");
			System.out.println("strSql = " + strSql);
			ps = conn.prepareStatement(strSql.toString());
			rs = ps.executeQuery();
			
			if( rs != null ) {
				coll = new ArrayList();
				LoanContractInfo contractInfo = null;
				while(rs.next()) {
					contractInfo = new LoanContractInfo();
					contractInfo.setLContractId(rs.getLong("id"));
					contractInfo.setLContractSubTypeId(rs.getLong("nsubtypeid"));
					contractInfo.setLContractTypeId(rs.getLong("ntypeid"));
					contractInfo.setStrContractCode(rs.getString("scontractcode"));
					contractInfo.setLCleartype(rs.getLong("cleartype"));
					contractInfo.setLCleartime(rs.getLong("cleartime"));
					coll.add(contractInfo);
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			throw new IException("获取贷款合同信息失败");
		} finally {
			if(rs != null)
				rs.close();
			if(ps != null)
				ps.close();
			if(conn != null)
				conn.close();
		}
		
		return coll;
	}
	
	/**
	 * 查询时间范围内的结息交易
	 * @param strStartDate
	 * @param strEndDate
	 * @param lLoanContractId
	 * @return Collection coll
	 * @throws Exception 
	 */
	public Collection getTransLoanInterestList(String strStartDate,String strEndDate,long lLoanContractId) throws Exception {
		Collection coll = null;	
		Connection conn = null;
		StringBuffer strSql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			strSql = new StringBuffer();
			strSql.append("SELECT LC.NBORROWCLIENTID AS CLIENTID,SV.* FROM (SELECT   strt.stransno,");
			strSql.append(" strt.nloancontractid contractid,                                                           ");
			strSql.append("             strt.ntransactiontypeid transtypeid,                                           ");
			strSql.append("             strt.nloanaccountid loanaccid,                                                 ");
			strSql.append("             strt.nclientid,                                                                ");
			strSql.append("             strt.nreceiveinterestaccountid revaccid,                                       ");
			strSql.append("             strt.minterest as minterest,                                                                ");
			strSql.append("             strt.dtexecute,                                                                ");
			strSql.append("             strt.dtinterestclear                                                           ");
			strSql.append("      FROM   sett_transrepaymentloan strt                                                   ");
			strSql.append("     WHERE   strt.ntransactiontypeid IN (18,20,45) AND strt.nstatusid = 3                 ");
//			strSql.append("    UNION                                                                                   ");
//			strSql.append("    SELECT   strt.stransno,");
//			strSql.append(" strt.nloancontractid contractid,                                                           ");
//			strSql.append("             strt.ntransactiontypeid transtypeid,                                           ");
//			strSql.append("             strt.nloanaccountid loanaccid,                                                 ");
//			strSql.append("             strt.nclientid,                                                                ");
//			strSql.append("             strt.nreceiveinterestaccountid revaccid,                                       ");
//			strSql.append("             strt.minterest as minterest,                                                                ");
//			strSql.append("             strt.dtexecute,                                                                ");
//			strSql.append("             strt.dtinterestclear                                                           ");
//			strSql.append("      FROM   sett_transrepaymentloan strt                                                   ");
//			strSql.append("     WHERE   strt.ntransactiontypeid IN (18, 45) AND strt.nstatusid = 3                 ");
			strSql.append("     and strt.minterest>0                                                                   ");
			strSql.append("    UNION                                                                                   ");
			strSql.append("    SELECT   stis.stransno,                                                                 ");
			strSql.append("             lp.ncontractid contractid,                                                     ");
			strSql.append("             stis.ntransactiontypeid transtypeid,                                           ");
			strSql.append("             stis.naccountid loanaccid,                                                     ");
			strSql.append("             sa.nclientid,                                                                  ");
			strSql.append("             stis.nreceiveinterestaccountid revaccid,                                       ");
			strSql.append("             stis.minterest,                                                        ");
			strSql.append("             stis.dtexecute,                                                                ");
			strSql.append("             stis.dtinterestsettlement dtinterestclear                                      ");
			strSql.append("      FROM   sett_transinterestsettlement stis,                                             ");
			strSql.append("             loan_payform lp,                                                               ");
			strSql.append("             sett_subaccount ss,                                                            ");
			strSql.append("             sett_account sa                                                                ");
			strSql.append("     WHERE       stis.ntransactiontypeid IN (104, 106)                                      ");
			strSql.append("             AND stis.nstatusid = 3                                                         ");
			strSql.append("             AND lp.id = ss.al_nloannoteid                                                  ");
			strSql.append("             AND ss.id = stis.nsubaccountid                                                 ");
			strSql.append("             AND sa.id = ss.naccountid) SV,LOAN_CONTRACTFORM LC WHERE LC.ID = SV.CONTRACTID ");
			//特殊判断
			if(strStartDate!= null && strStartDate.equals(strEndDate)){
				strSql.append(" and sv.dtinterestclear >= to_date('" + strStartDate + "','yyyy-mm-dd') ");
			}else{
				strSql.append(" and sv.dtinterestclear > to_date('" + strStartDate + "','yyyy-mm-dd') ");
			}
			strSql.append(" and sv.dtinterestclear <= to_date('" + strEndDate + "','yyyy-mm-dd') ");
			strSql.append(" and sv.contractid = " + lLoanContractId);
			strSql.append(" order by sv.dtexecute");
			System.out.println("strSql = " + strSql.toString());
		
			conn = Database.getConnection();
			ps = conn.prepareStatement(strSql.toString());
			rs = ps.executeQuery();
			if(rs != null) {
				coll = new ArrayList();
				SettLoanInterestInfo interestInfo = null;
				while(rs.next()) {
					interestInfo = new SettLoanInterestInfo();
					interestInfo.setDbInterest(rs.getDouble("MINTEREST"));
					interestInfo.setLClientId(rs.getLong("CLIENTID"));
					interestInfo.setLContractId(rs.getLong("CONTRACTID"));
					interestInfo.setLLoanAccountId(rs.getLong("LOANACCID"));
					interestInfo.setLReceiveAccountId(rs.getLong("REVACCID"));
					interestInfo.setLTransTypeId(rs.getLong("TRANSTYPEID"));
					interestInfo.setStrTransNo(rs.getString("STRANSNO"));
					interestInfo.setTsExecuteDate(rs.getTimestamp("DTEXECUTE"));
					interestInfo.setTsInterestDate(rs.getTimestamp("DTINTERESTCLEAR"));
					coll.add(interestInfo);
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			throw new IException("查询结息交易失败");
		} finally {
			if(rs != null)
				rs.close();
			if(ps != null)
				ps.close();
			if(conn != null)
				conn.close();
		}
		
		return coll;
	}
	
	/**
	 * 获取当日的合同的累计利息 累计利息包括 正常利息、复利、罚息
	 * @author yunzhou
	 * @date 2011-01-21
	 * @param strDate
	 * @param lContractId
	 * @return
	 * @throws Exception
	 */
	public double getLoanInterest(String strDate,long lContractId) throws Exception {
		double dbLoanInterest = 0.00;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSql = null;
		
		try {
			strSql = new StringBuffer();
			strSql.append("select round(sum(sd.minterest + sd.mforfeitinterest + sd.mcompounddailyinterset + sd.mcompoundinterest),2) loaninterest ");
			strSql.append("  from sett_dailyaccountbalance sd ");
			strSql.append(" where sd.nsubaccountid in ");
			strSql.append("       (select ss.id ");
			strSql.append("          from sett_subaccount ss, loan_payform lp ");
			strSql.append("         where lp.ncontractid = " + lContractId);
			strSql.append("           and lp.id = ss.al_nloannoteid) ");
			strSql.append("      and sd.dtdate = to_date('" + strDate + "','yyyy-mm-dd') - 1 ");
			
			conn = Database.getConnection();
			ps = conn.prepareStatement(strSql.toString());
			rs = ps.executeQuery();
			
			if(rs != null && rs.next()) { 
				dbLoanInterest = rs.getDouble("loaninterest");
			}	
		} catch(Exception e) {
			e.printStackTrace();
			throw new IException("查询贷款利息合计失败");
		} finally {
			if(rs != null)
				rs.close();
			if(ps != null)
				ps.close();
			if(conn != null)
				conn.close();
		}
		
		
		return dbLoanInterest;
	}
}
 