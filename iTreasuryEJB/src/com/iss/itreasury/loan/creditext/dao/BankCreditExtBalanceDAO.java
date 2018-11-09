package com.iss.itreasury.loan.creditext.dao;

import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.iss.itreasury.loan.util.LOANConstant.BankCreditVariety;
import com.iss.itreasury.util.Database;

public class BankCreditExtBalanceDAO {
	//得到信用证余额(折合人民币)	
	public double getLetterCreditBalance(long id, String companyCode) throws Exception {
		return getSplitedListAmount(id, companyCode, BankCreditVariety.LETTEROFCREDIT) - getUsedLetterCredit(id, companyCode);
	}
	
	//得到信用证已使用金额(折合人民币)
	//sum(使用金额*汇率)	
	public double getUsedLetterCredit(long id, String companyCode) throws Exception {
		double balance = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select case when c.nreuse=0 then c.sum1 else c.sum2 end ");
		sql.append("from ");
		sql.append("(select b.nreuse,sum(case when a.nstatus<>5 then a.mamount*a.mexchangerate else 0 end) sum1, ");
		sql.append("sum(a.mamount*a.mexchangerate) sum2 ");
		sql.append("from loan_letter_credit a,loan_bank_creditext b ");
		sql.append("where b.id=a.nbankcreditextid and b.nisvalid=1 and a.nstatus<>0 ");
		sql.append("and a.nbankcreditextid=? and a.sapplycompanycode=? ");
		sql.append("group by b.nreuse) c ");
		
		try {
			conn = Database.getConnection();
			pst = conn.prepareStatement(sql.toString());
			pst.setLong(1, id);
			pst.setString(2, companyCode);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				balance = rs.getDouble(1);
				break;
			}
		}
		catch (SQLException sqle) {
			throw sqle;
		}
		finally {
			closeResultSet(rs);
			closeStatement(pst);
			closeConnection(conn);
		}
	
		return balance;
	}
	
	//	得到保函余额(折合人民币)
	public double getLetterGuaranteeBalance(long id, String companyCode) throws Exception {
		return getSplitedListAmount(id, companyCode, BankCreditVariety.LETTEROFIGUARANTEE) - getUsedLetterGuarantee(id, companyCode);
	}
	
	//得到保函已使用金额(折合人民币)
	//sum(使用金额*汇率)
	public double getUsedLetterGuarantee(long id, String companyCode) throws Exception {
		double balance = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select case when c.nreuse=0 then c.sum1 else c.sum2 end ");
		sql.append("from ");
		sql.append("(select b.nreuse,sum(case when a.nstatus<>2 then a.mamount*a.mexchangerate else 0 end) sum1, ");
		sql.append("sum(a.mamount*a.mexchangerate) sum2 ");
		sql.append("from loan_letter_guarantee a,loan_bank_creditext b ");
		sql.append("where b.id=a.nbankcreditextid and b.nisvalid=1 and a.nstatus<>0 ");
		sql.append("and a.nbankcreditextid=? and a.sapplycompanycode=? ");
		sql.append("group by b.nreuse) c ");		
		
		try {
			conn = Database.getConnection();
			pst = conn.prepareStatement(sql.toString());
			pst.setLong(1, id);
			pst.setString(2, companyCode);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				balance = rs.getDouble(1);
				break;
			}
		}
		catch (SQLException sqle) {
			throw sqle;
		}
		finally {
			closeResultSet(rs);
			closeStatement(pst);
			closeConnection(conn);
		}
	
		return balance;
	}
	
	//	得到信贷证明余额(折合人民币)
	public double getCreditProveBalance(long id, String companyCode) throws Exception {
		return getSplitedListAmount(id, companyCode, BankCreditVariety.PROVEOFCREDIT) - getUsedCreditProve(id, companyCode);
	}
	
	//得到信贷证明已使用金额(折合人民币)
	//sum(使用金额*汇率)
	public double getUsedCreditProve(long id, String companyCode) throws Exception {
		double balance = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select case when c.nreuse=0 then c.sum1 else c.sum2 end ");
		sql.append("from ");
		sql.append("(select b.nreuse,sum(case when a.statusid<>2 then a.money*a.exchangerate else 0 end) sum1, ");
		sql.append("sum(a.money*a.exchangerate) sum2 ");
		sql.append("from loan_creditprove a,loan_bank_creditext b ");
		sql.append("where b.id=a.confercontractno and b.nisvalid=1 and a.statusid<>0 ");
		sql.append("and a.confercontractno=? and a.applyclient=? ");
		sql.append("group by b.nreuse) c ");
		
		try {
			conn = Database.getConnection();
			pst = conn.prepareStatement(sql.toString());
			pst.setLong(1, id);
			pst.setString(2, companyCode);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				balance = rs.getDouble(1);
				break;
			}
		}
		catch (SQLException sqle) {
			throw sqle;
		}
		finally {
			closeResultSet(rs);
			closeStatement(pst);
			closeConnection(conn);
		}
	
		return balance;
	}
	
	//	得到承兑汇票余额(折合人民币)
	public double getAcceptBillBalance(long id, String companyCode) throws Exception {
		return getSplitedListAmount(id, companyCode, BankCreditVariety.ACCEPTBILL) - getUsedAcceptBill(id, companyCode);
	}
	
	//得到承兑汇票已使用金额(折合人民币)
	//sum(使用金额*汇率)
	public double getUsedAcceptBill(long id, String companyCode) throws Exception {
		double balance = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select case when c.nreuse=0 then c.sum1 else c.sum2 end ");
		sql.append("from ");
		sql.append("(select b.nreuse,sum(case when a.statusid<>2 then a.money*a.exchangerate else 0 end) sum1,");
		sql.append("sum(a.money*a.exchangerate) sum2 ");
		sql.append("from loan_acceptbill a,loan_bank_creditext b ");
		sql.append("where b.id=a.confercontractno and b.nisvalid=1 and a.statusid<>0 ");
		sql.append("and a.confercontractno=? and a.billclient=? ");
		sql.append("group by b.nreuse) c");

		try {
			conn = Database.getConnection();
			pst = conn.prepareStatement(sql.toString());
			pst.setLong(1, id);
			pst.setString(2, companyCode);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				balance = rs.getDouble(1);
				break;
			}
		}
		catch (SQLException sqle) {
			throw sqle;
		}
		finally {
			closeResultSet(rs);
			closeStatement(pst);
			closeConnection(conn);
		}
	
		return balance;
	}
	
	//	得到银行短期贷款余额(折合人民币)
	public double getShortTermLoanBalance(long id, String companyCode) throws Exception {
		return getSplitedListAmount(id, companyCode, BankCreditVariety.SHORTTERMLOAN) - getUsedShortTermLoan(id, companyCode);
	}
	
	//	得到银行短期贷款已使用金额(折合人民币)
	//sum(使用金额*汇率)
	public double getUsedShortTermLoan(long id, String companyCode) throws Exception {
		double balance = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select case when c.nreuse=0 then c.sum1 else c.sum2 end ");
		sql.append("from ");
		sql.append("(select b.nreuse,sum(case when a.statusid<>2 then a.amount*a.exchangerate else 0 end) sum1,");
		sql.append("sum(a.amount*a.exchangerate) sum2 ");
		sql.append("from loan_bankloan a,loan_bank_creditext b ");
		sql.append("where b.id=a.confercontractno and b.nisvalid=1 and a.statusid<>0 and a.loantype=1 ");
		sql.append("and a.confercontractno=? and a.loanclient=? ");
		sql.append("group by b.nreuse) c");
		
		try {
			conn = Database.getConnection();
			pst = conn.prepareStatement(sql.toString());
			pst.setLong(1, id);
			pst.setString(2, companyCode);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				balance = rs.getDouble(1);
				break;
			}
		}
		catch (SQLException sqle) {
			throw sqle;
		}
		finally {
			closeResultSet(rs);
			closeStatement(pst);
			closeConnection(conn);
		}
	
		return balance;
	}
	
	//	得到银行中长期贷款余额(折合人民币)
	public double getLongTermLoanBalance(long id, String companyCode) throws Exception {
		return getSplitedListAmount(id, companyCode, BankCreditVariety.LONGTERMLOAN) - getUsedLongTermLoan(id, companyCode);
	}
	
	//	得到银行中长期贷款已使用金额(折合人民币)
	//sum(使用金额*汇率)
	public double getUsedLongTermLoan(long id, String companyCode) throws Exception {
		double balance = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select case when c.nreuse=0 then c.sum1 else c.sum2 end ");
		sql.append("from ");
		sql.append("(select b.nreuse,sum(case when a.statusid<>2 then a.amount*a.exchangerate else 0 end) sum1,");
		sql.append("sum(a.amount*a.exchangerate) sum2 ");
		sql.append("from loan_bankloan a,loan_bank_creditext b ");
		sql.append("where b.id=a.confercontractno and b.nisvalid=1 and a.statusid<>0 and a.loantype=2 ");
		sql.append("and a.confercontractno=? and a.loanclient=? ");
		sql.append("group by b.nreuse) c");

		try {
			conn = Database.getConnection();
			pst = conn.prepareStatement(sql.toString());
			pst.setLong(1, id);
			pst.setString(2, companyCode);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				balance = rs.getDouble(1);
				break;
			}
		}
		catch (SQLException sqle) {
			throw sqle;
		}
		finally {
			closeResultSet(rs);
			closeStatement(pst);
			closeConnection(conn);
		}
	
		return balance;
	}
	
	//得到分解额度(折合人民币)
	//分解额度*汇率/风险系数
	public double getSplitedListAmount(long id, String companyCode, long variety) throws Exception {
		double amount = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select b.nexchangerate*c.mamount/b.nriskcoefficient*100 amount ");
		sql.append("from loan_bank_creditext a, loan_bank_creditext_list b,loan_bank_creditext_split c ");
		sql.append("where a.id=b.nbankcreditextid and a.id=c.nbankcreditextid and b.nvariety=c.nvariety and a.nisvalid=1 ");
		sql.append("and c.nisvalid=1 and c.nvariety=? and c.nbankcreditextid=? and c.scompanycode=?");
		
		try {
			conn = Database.getConnection();
			pst = conn.prepareStatement(sql.toString());
			pst.setLong(1, variety);
			pst.setLong(2, id);
			pst.setString(3, companyCode);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				amount = rs.getDouble(1);
				break;
			}
		}
		catch (SQLException sqle) {
			throw sqle;
		}
		finally {
			closeResultSet(rs);
			closeStatement(pst);
			closeConnection(conn);
		}
	
		return amount;
	}
	
	//	得到混用额度已使用金额(折合人民币)
	public double[] getSplitedMixInfo(long id, String companyCode) throws Exception {
		double[] amount = null;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select b.nexchangerate*c.mamount,b.nriskcoefficient1,b.nriskcoefficient2, ");
		sql.append("b.nriskcoefficient3,b.nriskcoefficient4,b.nriskcoefficient5,b.nriskcoefficient6 ");
		sql.append("from loan_bank_creditext a, loan_bank_creditext_mix b,loan_bank_creditext_split c ");
		sql.append("where a.id=b.nbankcreditextid and a.id=c.nbankcreditextid and a.nisvalid=1 and c.nisvalid=1 ");
		sql.append("and c.nvariety=0 and c.nbankcreditextid=? and c.scompanycode=?");

		try {
			conn = Database.getConnection();
			pst = conn.prepareStatement(sql.toString());
			pst.setLong(1, id);
			pst.setString(2, companyCode);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				amount = new double[7];
				amount[0] = rs.getDouble(1);
				amount[1] = rs.getDouble(2);
				amount[2] = rs.getDouble(3);
				amount[3] = rs.getDouble(4);
				amount[4] = rs.getDouble(5);
				amount[5] = rs.getDouble(6);
				amount[6] = rs.getDouble(7);
				break;
			}
		}
		catch (SQLException sqle) {
			throw sqle;
		}
		finally {
			closeResultSet(rs);
			closeStatement(pst);
			closeConnection(conn);
		}
	
		return amount;
	}
	
	// 得到担保信息总额(折合人民币)
	// 根据条件得到担保信息，包括担保金额（折合人民币）、被担保业务类型和被担保人
	public String[] getAssureAmount(long id, String companyCode, long variety) throws Exception {
		String[] result = null;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select a.amount*a.exchangerate,a.warranteename,a.assuretype ");
		sql.append("from loan_assure a ");
		sql.append("where a.confercontractno=? and instr(a.warranteename,?,1,1)>0 and instr(a.assuretype,?,1,1)>0");
			
		try {
			conn = Database.getConnection();
			pst = conn.prepareStatement(sql.toString());
			pst.setLong(1, id);
			pst.setString(2, companyCode);
			pst.setString(3, Long.toString(variety));
			rs = pst.executeQuery();
			
			while (rs.next()) {
				result = new String[3];
				result[0] = Double.toString(rs.getDouble(1));
				result[1] = rs.getString(2);
				result[2] = rs.getString(3);
				break;
			}
		}
		catch (SQLException sqle) {
			throw sqle;
		}
		finally {
			closeResultSet(rs);
			closeStatement(pst);
			closeConnection(conn);
		}
	
		return result;
	}
	
	// 得到短期贷款的担保总额(折合人民币)
	public double getShortTermAssureAmount(long id, String companyCode) throws Exception {
		double amount = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(case when a.statusid<> 2 then a.amount*a.exchangerate else 0 end) ");
		sql.append("from loan_bankloan a ");
		sql.append("where a.statusid<>0 and a.loantype=1 and a.confercontractno=? and instr(?, a.loanclient,1,1)>0");
			
		try {
			conn = Database.getConnection();
			pst = conn.prepareStatement(sql.toString());
			pst.setLong(1, id);
			pst.setString(2, companyCode);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				amount = rs.getDouble(1);
				break;
			}
		}
		catch (SQLException sqle) {
			throw sqle;
		}
		finally {
			closeResultSet(rs);
			closeStatement(pst);
			closeConnection(conn);
		}
	
		return amount;
	}
	
	//得到中长期贷款的担保总额(折合人民币)
	public double getLongTermAssureAmount(long id, String companyCode) throws Exception {
		double amount = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(case when a.statusid<> 2 then a.amount*a.exchangerate else 0 end) ");
		sql.append("from loan_bankloan a ");
		sql.append("where a.statusid<>0 and a.loantype=2 and a.confercontractno=? and instr(?, a.loanclient,1,1)>0");
			
		try {
			conn = Database.getConnection();
			pst = conn.prepareStatement(sql.toString());
			pst.setLong(1, id);
			pst.setString(2, companyCode);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				amount = rs.getDouble(1);
				break;
			}
		}
		catch (SQLException sqle) {
			throw sqle;
		}
		finally {
			closeResultSet(rs);
			closeStatement(pst);
			closeConnection(conn);
		}
	
		return amount;
	}
	
	//得到信用证的担保总额(折合人民币)
	public double getLetterCreditAssureAmount(long id, String companyCode) throws Exception {
		double amount = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(case when a.nstatus<> 5 then a.mamount*a.mexchangerate else 0 end) ");
		sql.append("from loan_letter_credit a ");
		sql.append("where a.nstatus<>0 and a.nbankcreditextid=? and instr(?, a.sapplycompanycode,1,1)>0");
			
		try {
			conn = Database.getConnection();
			pst = conn.prepareStatement(sql.toString());
			pst.setLong(1, id);
			pst.setString(2, companyCode);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				amount = rs.getDouble(1);
				break;
			}
		}
		catch (SQLException sqle) {
			throw sqle;
		}
		finally {
			closeResultSet(rs);
			closeStatement(pst);
			closeConnection(conn);
		}
	
		return amount;
	}
	
	//得到保函的担保总额(折合人民币)
	public double getLetterGuaranteeAssureAmount(long id, String companyCode) throws Exception {
		double amount = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(case when a.nstatus<> 2 then a.mamount*a.mexchangerate else 0 end) ");
		sql.append("from loan_letter_guarantee a ");
		sql.append("where a.nstatus<>0 and a.nbankcreditextid=? and instr(?, a.sapplycompanycode,1,1)>0");
			
		try {
			conn = Database.getConnection();
			pst = conn.prepareStatement(sql.toString());
			pst.setLong(1, id);
			pst.setString(2, companyCode);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				amount = rs.getDouble(1);
				break;
			}
		}
		catch (SQLException sqle) {
			throw sqle;
		}
		finally {
			closeResultSet(rs);
			closeStatement(pst);
			closeConnection(conn);
		}
	
		return amount;
	}
	
	//得到信贷证明的担保总额(折合人民币)
	public double getCreditProveAssureAmount(long id, String companyCode) throws Exception {
		double amount = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(case when a.statusid<> 2 then a.money*a.exchangerate else 0 end) ");
		sql.append("from loan_creditprove a ");
		sql.append("where a.statusid<>0 and a.confercontractno=? and instr(?, a.applyclient,1,1)>0");
		
		try {
			conn = Database.getConnection();
			pst = conn.prepareStatement(sql.toString());
			pst.setLong(1, id);
			pst.setString(2, companyCode);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				amount = rs.getDouble(1);
				break;
			}
		}
		catch (SQLException sqle) {
			throw sqle;
		}
		finally {
			closeResultSet(rs);
			closeStatement(pst);
			closeConnection(conn);
		}
	
		return amount;
	}
	
	//得到承兑汇票的担保总额(折合人民币)
	public double getAcceptBillAssureAmount(long id, String companyCode) throws Exception {
		double amount = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(case when a.statusid<> 2 then a.money*a.exchangerate else 0 end) ");
		sql.append("from loan_acceptbill a ");
		sql.append("where a.statusid<>0 and a.confercontractno=? and instr(?, a.billclient,1,1)>0");
		
		try {
			conn = Database.getConnection();
			pst = conn.prepareStatement(sql.toString());
			pst.setLong(1, id);
			pst.setString(2, companyCode);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				amount = rs.getDouble(1);
				break;
			}
		}
		catch (SQLException sqle) {
			throw sqle;
		}
		finally {
			closeResultSet(rs);
			closeStatement(pst);
			closeConnection(conn);
		}
	
		return amount;
	}
	
//	public Connection getConnection() throws SQLException {
//		String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
//        String dbURL = "jdbc:oracle:thin:@10.10.26.41:1521:db9i";
//        try {
//        	Class.forName(jdbcDriver).newInstance();
//        	return DriverManager.getConnection(dbURL, "ccgrp", "ccgrp");
//        }
//        catch (SQLException sqle) {
//        	throw sqle;
//        }
//        catch (Exception e) {
//        	throw new SQLException(e.getMessage());
//        }
//    }
	
	public void closeConnection(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	public void closeStatement(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	public void closeResultSet(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
//		BankCreditExtBalanceDAO dao = new BankCreditExtBalanceDAO();
		try {	
//			double amount1 = dao.getSplitedListAmount(1, "01-0001", 1);
//			double used1 = dao.getUsedShortTermLoan(1, "01-0001");
//			double balance1 = dao.getShortTermLoanBalance(1,"01-0001");
//			System.out.println("short term loan balance is " + amount1+"\t"+used1+"\t"+ balance1);
//			
//			double amount2 = dao.getSplitedListAmount(1, "01-0001", 2);
//			double used2 = dao.getUsedLongTermLoan(1, "01-0001");
//			double balance2 = dao.getLongTermLoanBalance(1,"01-0001");
//			System.out.println("long term loan balance is " + amount2+"\t"+ used2+"\t"+balance2);
//			
//			double amount3 = dao.getSplitedListAmount(1, "01-0001", 3);
//			double used3 = dao.getUsedLetterCredit(1, "01-0001");
//			double balance3 = dao.getLetterCreditBalance(1,"01-0001");
//			System.out.println("letter of credit balance is " +amount3+"\t"+ used3+"\t"+balance3);
//			
//			double amount4 = dao.getSplitedListAmount(1, "01-0001", 4);
//			double used4 = dao.getUsedLetterGuarantee(1,"01-0001");
//			double balance4 = dao.getLetterGuaranteeBalance(1,"01-0001");			
//			System.out.println("letter of guarantee balance is " +amount4+"\t"+ used4+"\t"+balance4);
//			
//			double amount5 = dao.getSplitedListAmount(1, "01-0001", 5);
//			double used5= dao.getUsedCreditProve(1, "01-0001");
//			double balance5 = dao.getCreditProveBalance(1,"01-0001");
//			System.out.println("credit prove balance is " +amount5+"\t"+ used5+"\t"+balance5);
//			
//			double amount6 = dao.getSplitedListAmount(1, "01-0001", 6);
//			double used6 = dao.getUsedAcceptBill(1, "01-0001");
//			double balance6 = dao.getAcceptBillBalance(1,"01-0001");
//			System.out.println("accept bill balance is " + amount6+"\t"+used6+"\t"+balance6);
			
			//得到每种授信品种的分解余额（折合人民币）
//			for (int i = 1; i < 7; i++) {
//				double mixamount = dao.getSplitedMixAmount(1, "05-0001", i);
//				System.out.println(i+"\t"+mixamount);
//			}
			
//			String[] assureInfo = dao.getAssureAmount(10,"34",1);
//			if (assureInfo != null) {
//				System.out.println(assureInfo[0]+"\t"+assureInfo[1]+"\t"+assureInfo[2]);
//			}
			
//			double assureAmount1 = dao.getShortTermAssureAmount(1, "01-0001");
//			double assureAmount2 = dao.getLongTermAssureAmount(1, "01-0001");
//			double assureAmount3 = dao.getLetterCreditAssureAmount(1, "01-0001");
//			double assureAmount4 = dao.getLetterGuaranteeAssureAmount(1, "01-0001");
//			double assureAmount5 = dao.getCreditProveAssureAmount(1, "01-0001");
//			double assureAmount6 = dao.getAcceptBillAssureAmount(1, "01-0001,fsda");
//			System.out.println("used assure amount:\n"+assureAmount1+"\t"+assureAmount2+"\t"+assureAmount3+"\t"+assureAmount4+"\t"+assureAmount5+"\t"+assureAmount6);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
