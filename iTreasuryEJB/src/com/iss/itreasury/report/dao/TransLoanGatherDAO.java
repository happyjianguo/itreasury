package com.iss.itreasury.report.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.archivesmanagement.util.SETTConstant;
import com.iss.itreasury.report.dateentity.TransLoanGatherInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;

/**
 * 
 * @author Kloud Zhou
 * @date 2010-12-14
 * 贷款汇总帐查询DAO
 */
public class TransLoanGatherDAO {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 查询贷款明细交易
	 * @author yunzhou
	 * @date 2010-12-14
	 * @param strStartDate
	 * @param strEndDate
	 * @param lLoanTypeId
	 * @param lContractStatusId
	 * @return
	 * @throws Exception 
	 */
	public Collection findTransLoanDetail(String strStartDate,String strEndDate,long lLoanTypeId,String strContractStatusId) throws Exception {
		Collection coll = null;
		Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer strSql = null;
		
        try {
        	strSql = new StringBuffer();
        	strSql.append("select * ");
        	strSql.append("from (select fkjy.dtexecute          dtdate, ");
        	strSql.append("cc.name                 clientname, ");
        	strSql.append("cc.id                   clientId, ");
        	strSql.append("lc.scontractcode        contractcode, ");
//        	strSql.append("li.mrate                loanrate, ");
        	strSql.append("lc.minterestrate      loanrate, ");
        	strSql.append("sa.saccountno           loanaccount, ");
        	strSql.append("lc.dtenddate            enddate, ");
        	strSql.append("fkjy.sabstract          abstract, ");
        	strSql.append("lc.mloanamount          loanamount, ");
        	strSql.append("fkjy.mamount            transamount, ");
        	strSql.append("fkjy.ntransactiontypeid transtype, ");
        	strSql.append("ll.nsubtypeid           loantype, ");
        	strSql.append("lc.nstatusid            loanstatusid ");
        	strSql.append("from sett_transgrantloan fkjy, ");
        	strSql.append("client_clientinfo   cc, ");
        	strSql.append("loan_contractform   lc, ");
        	strSql.append("loan_loanform       ll, ");
        	strSql.append("sett_account        sa, ");
        	strSql.append("loan_interestrate   li ");
        	strSql.append("where fkjy.nloancontractid = lc.id ");
        	strSql.append("and fkjy.nloanaccountid = sa.id ");
        	strSql.append("and fkjy.ntransactiontypeid in(17,19) ");
        	strSql.append("and fkjy.nstatusid = 3 ");
        	strSql.append("and lc.nloanid = ll.id ");
        	strSql.append("and ll.nborrowclientid = cc.id ");
        	strSql.append("and li.id = lc.ninteresttypeid ");
        	strSql.append("union all ");
        	strSql.append("select hkjy.dtexecute          dtdate, ");
        	strSql.append("cc.name                 clientname, ");
        	strSql.append("cc.id                   clientId, ");
        	strSql.append("lc.scontractcode        contractcode, ");
//        	strSql.append("li.mrate                loanrate, ");
        	strSql.append("lc.minterestrate      loanrate, ");
        	strSql.append("sa.saccountno           loanaccount, ");
        	strSql.append("lc.dtenddate            enddate, ");
        	strSql.append("hkjy.sabstract          abstract, ");
        	strSql.append("lc.mloanamount          loanamount, ");
        	strSql.append("hkjy.mamount            transamount, ");
        	strSql.append("hkjy.ntransactiontypeid transtype, ");
        	strSql.append("ll.nsubtypeid           loantype, ");
        	strSql.append("lc.nstatusid            loanstatusid ");
        	strSql.append("from sett_transrepaymentloan hkjy, ");
        	strSql.append("client_clientinfo       cc, ");
        	strSql.append("loan_contractform       lc, ");
        	strSql.append("sett_account            sa, ");
        	strSql.append("loan_loanform           ll, ");
        	strSql.append("loan_interestrate       li ");
        	strSql.append("where hkjy.nloancontractid = lc.id ");
        	strSql.append("and hkjy.nloanaccountid = sa.id ");
        	strSql.append("and hkjy.nstatusid = 3 ");
        	strSql.append("and hkjy.ntransactiontypeid in(18,20) ");
        	strSql.append("and lc.nloanid = ll.id ");
        	strSql.append("and ll.nborrowclientid = cc.id ");
        	strSql.append("and li.id = lc.ninteresttypeid) ");
        	if(strStartDate != null && !strStartDate.equals("") && strEndDate != null && !strEndDate.equals("")) {
	        	strSql.append("where dtdate >= to_date('"+strStartDate+"', 'yyyy-mm-dd') ");
	        	strSql.append(" and dtdate <= to_date('"+strEndDate+"', 'yyyy-mm-dd') ");     
        	}
        	strSql.append(" and loantype = " + lLoanTypeId);
        	strSql.append(" and loanstatusid in " + strContractStatusId);
        	strSql.append(" order by dtdate,clientId");
        	System.out.println("查询贷款明细交易sql：" + strSql.toString());
        	
        	conn = Database.getConnection();
        	ps = conn.prepareStatement(strSql.toString());
        	rs = ps.executeQuery();
        	
        	if(rs != null) {
        		TransLoanGatherInfo gatherInfo = null;
        		coll = new ArrayList();
	        	while(rs.next()) {
	        		gatherInfo = new TransLoanGatherInfo();
	        		gatherInfo.setTsTransDate(rs.getTimestamp("dtdate"));
	        		gatherInfo.setStrClientName(rs.getString("clientname"));
	        		gatherInfo.setStrLoanContractCode(rs.getString("contractcode"));
	        		gatherInfo.setDbLoanRate(rs.getDouble("loanrate"));
	        		gatherInfo.setStrLoanAccountNo(rs.getString("loanaccount"));
	        		gatherInfo.setTsLoanEndDate(rs.getTimestamp("enddate"));
	        		gatherInfo.setStrAbstract(rs.getString("abstract"));
	        		gatherInfo.setDbLoanAmount(rs.getDouble("loanamount"));
	        		gatherInfo.setLTransType(rs.getLong("transtype"));
	        		if(gatherInfo.getLTransType() == SETTConstant.TransactionType.TRUSTLOANGRANT 
	        				|| gatherInfo.getLTransType() == SETTConstant.TransactionType.CONSIGNLOANGRANT) {
	        			gatherInfo.setDbGrantLoanAmount(rs.getDouble("transamount"));
	        		} else if(gatherInfo.getLTransType() == SETTConstant.TransactionType.TRUSTLOANRECEIVE
	        				|| gatherInfo.getLTransType() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE) {
	        			gatherInfo.setDbRepaymentAmount(rs.getDouble("transamount"));
	        		}
	        		coll.add(gatherInfo);
	        	}
	        	System.out.println("coll.size() = " + coll.size());
        	}
        	
        } catch(Exception e) {
        	e.printStackTrace();
        	throw new IException("查询贷款明细交易失败，请检查");
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
	
	public double getSumBalanceOfLoanGather(String strStartDate,String strEndDate,long lLoanTypeId,String strContractStatusId) throws Exception {
		Collection coll = null;
		Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer strSql = null;
        double dbSumBalance = 0.00;
		
        try {
        	strSql = new StringBuffer();
        	strSql.append("select sum(sd.mbalance) sumbal ");
        	strSql.append("from sett_dailyaccountbalance sd, ");
        	strSql.append("sett_subaccount          ss, ");
        	strSql.append("loan_contractform        lc, ");
        	strSql.append("loan_payform             lp, ");
        	strSql.append("loan_loanform            ll ");
        	strSql.append("where 1 = 1 ");
        	if(strStartDate != null && !strStartDate.equals("")) {
        		strSql.append("and sd.dtdate = to_date('" + strStartDate + "','yyyy-mm-dd') - 1 ");
        	}
        	strSql.append("and sd.nsubaccountid = ss.id ");
        	strSql.append(" and ss.al_nloannoteid = lp.id ");
        	strSql.append(" and lp.ncontractid = lc.id ");
        	strSql.append(" and lc.nloanid = ll.id ");
        	strSql.append(" and ll.nsubtypeid  = " + lLoanTypeId);
        	strSql.append(" and lc.nstatusid in " + strContractStatusId);
        	System.out.println("查询贷款期初余额sql：" + strSql.toString());
        	
        	conn = Database.getConnection();
        	ps = conn.prepareStatement(strSql.toString());
        	rs = ps.executeQuery();
        	
        	if(rs != null && rs.next()) {
        		dbSumBalance = rs.getDouble("sumbal");
        	}
        	System.out.println("贷款期初余额 dbSumBalance = " + dbSumBalance);
        } catch (Exception e) {
        	e.printStackTrace();
        	throw new IException("查询贷款期初余额失败，请检查");
        } finally {
        	if(rs != null)
        		rs.close();
        	if(ps != null)
        		ps.close();
        	if(conn != null)
        		conn.close();
        }
        
		return dbSumBalance;
	}
	
}
