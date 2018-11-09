package com.iss.itreasury.loan.creditext.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.creditext.dataentity.BankCreditExtComplexQueryInfo;
import com.iss.itreasury.loan.creditext.dataentity.BankCreditExtComplexInfo;
import com.iss.itreasury.loan.creditext.dataentity.BankCreditExtComplexResultInfo;
import com.iss.itreasury.loan.util.LOANConstant.BankCreditVariety;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log4j;

public class BankCreditExtComplexDAO {
	private Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
	/**
	 * 数据库资源
	 */
	private Connection conn = null;
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	/**
	 * 初始化数据库
	 * @throws Exception
	 */
	public void init() throws Exception {
		try {
			if(conn == null)
				conn = Database.getConnection();
		} catch (Exception e) {
			throw e;
		}
	}
	
	//测试用的连接数据库
	public Connection getConnection() throws SQLException
    {
            conn = null;
        	try {
            String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
            String dbURL = "jdbc:oracle:thin:@10.10.26.41:1521:db9i";
            Class.forName(jdbcDriver).newInstance();
            conn = DriverManager.getConnection(dbURL, "ccgrp", "ccgrp");
        	}
        	catch (Exception e) {
        		throw new SQLException(e.getMessage());
        	}
        	
        return conn;
    }
	
	//	通过查询条件查询出符合条件的银行授信ID
	public List getBankCreditExtInfo(BankCreditExtComplexQueryInfo info) throws Exception {

		List result = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct a.id,a.scontractno,a.sbankname,a.nreuse, b.scompanycode,c.sName ");
		sql.append("from loan_bank_creditext a,loan_bank_creditext_split b,client c ");
		sql.append("where a.id=b.nbankcreditextid and a.nisvalid=1 and b.nisvalid=1 ");
		sql.append("and (0=? or a.syear=?) and (0=? or a.id=?) and b.scompanycode=c.sCode ");
		sql.append("and (0=? or a.nstatus=?) and (0=? or b.nofficeid=?) ");
		sql.append("and (0=? or a.sbankname=?) and (0=? or a.scompany=?) ");
		sql.append("and (0=? or a.dstartdate>=to_date(?,'yyyy-mm-dd')) ");
		sql.append("and (0=? or a.dstartdate<=to_date(?,'yyyy-mm-dd')) ");
		sql.append("and (0=? or a.denddate>=to_date(?,'yyyy-mm-dd')) ");
		sql.append("and (0=? or a.denddate<=to_date(?,'yyyy-mm-dd'))");
		
		try {
			
			pst = conn.prepareStatement(sql.toString());
			int i1 = (info.getYear() == "") ? 0 : 1;
			int i2 = (info.getId() == -1) ? 0 : 1;
			int i3 = (info.getStatus() == -1) ? 0 : 1;
			int i4 = (info.getOfficId() <= 1) ? 0 : 1;
			int i5 = (info.getBankName() == "") ? 0 : 1;
			int i6 = (info.getCompany() == "") ? 0 : 1;
			int i7 = (info.getStartDate1() == "") ? 0 : 1;
			int i8 = (info.getStartDate2() == "") ? 0 : 1;
			int i9 = (info.getEndDate1() == "") ? 0 : 1;
			int i10 = (info.getEndDate2() == "") ? 0 : 1;
			
			pst.setInt(1, i1);
			pst.setString(2, info.getYear());
			pst.setInt(3, i2);
			pst.setLong(4, info.getId());
			pst.setInt(5, i3);
			pst.setLong(6, info.getStatus());
			pst.setInt(7, i4);
			pst.setLong(8, info.getOfficId());
			pst.setInt(9, i5);
			pst.setString(10, info.getBankName());
			pst.setInt(11, i6);
			pst.setString(12, info.getCompany());
			pst.setInt(13, i7);
			pst.setString(14, info.getStartDate1());
			pst.setInt(15, i8);
			pst.setString(16, info.getStartDate2());
			pst.setInt(17, i9);
			pst.setString(18, info.getEndDate1());
			pst.setInt(19, i10);
			pst.setString(20, info.getEndDate2());
			rs = pst.executeQuery();
						
			while (rs.next()) {
				
				BankCreditExtComplexInfo info1 = new BankCreditExtComplexInfo();
				info1.setId(rs.getLong(1));
				info1.setContractNo(rs.getString(2));
				info1.setBankName(rs.getString(3));
				info1.setReuse(rs.getLong(4));
				info1.setCompanyCode(rs.getString(5));
				info1.setCompanyName(rs.getString(6));
				result.add(info1);
//			    log4j.print("银行授信信息：");
//				log4j.print(info1.getId()+"\t"+info1.getContractNo()+"\t"+info1.getBankName()+"\t"+info1.getReuse()+"\t"+info1.getCompanyCode()+"\t"+info1.getCompanyName());
			}
			
			closeResultSet();
			closeStatement();
		}
		catch (SQLException sqle) {
			throw sqle;
		}
		finally {
			closeResultSet();
			closeStatement();			
		}
				
		return result;
	}
	
	public void putSplitMixAmount(long id, String companyCode, BankCreditExtComplexResultInfo info) throws Exception {

		StringBuffer sql = new StringBuffer();
		sql.append("select a.mamount*b.nexchangerate,b.nriskcoefficient1,b.nriskcoefficient2, ");
		sql.append("b.nriskcoefficient3,b.nriskcoefficient4,b.nriskcoefficient5,b.nriskcoefficient6 ");
		sql.append("from loan_bank_creditext_split a,loan_bank_creditext_mix b ");
		sql.append("where a.nvariety=0 and a.nbankcreditextid=b.nbankcreditextid and a.nisvalid=1 ");
		sql.append("and a.nbankcreditextid=? and a.scompanycode=? ");
		
		try {
			
			pst = conn.prepareStatement(sql.toString());
			pst.setLong(1, id);
			pst.setString(2, companyCode);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				log4j.print("分配的混合额度及风险系数：");
				log4j.print(info.getContractNo()+":\t"+info.getCompanyName()+"0\t" + rs.getDouble(1)+"\t"+ rs.getDouble(2)+ "\t"+rs.getDouble(3)+"\t"+ rs.getDouble(4)+"\t"+ rs.getDouble(5)+"\t"+ rs.getDouble(6)+"\t"+ rs.getDouble(7));
				info.setSplitedMixAmount(rs.getDouble(1));
				info.setMixRiskCoefficient1(rs.getDouble(2));
				info.setMixRiskCoefficient2(rs.getDouble(3));
				info.setMixRiskCoefficient3(rs.getDouble(4));
				info.setMixRiskCoefficient4(rs.getDouble(5));
				info.setMixRiskCoefficient5(rs.getDouble(6));
				info.setMixRiskCoefficient6(rs.getDouble(7));
				break;
			}
			
			closeResultSet();
			closeStatement();
		}
		catch (SQLException sqle) {
			throw sqle;
		}
		finally {
			closeResultSet();
			closeStatement();			
		}
		
	}
	
	public void putSplitListAmount(long id, String companyCode, BankCreditExtComplexResultInfo info) throws Exception {

		StringBuffer sql = new StringBuffer();
		sql.append("select a.nvariety, a.mamount*b.nexchangerate,b.nriskcoefficient ");
		sql.append("from loan_bank_creditext_split a,loan_bank_creditext_list b ");
		sql.append("where a.nbankcreditextid=b.nbankcreditextid and a.nvariety=b.nvariety ");
		sql.append("and a.nbankcreditextid=? and a.scompanycode=? and a.nisvalid=1 ");
		sql.append("order by nvariety");
		
		try {
		
			pst = conn.prepareStatement(sql.toString());
			pst.setLong(1, id);
			pst.setString(2, companyCode);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				putSplitedAmount(info, rs.getInt(1), rs.getDouble(2), rs.getDouble(3));				
			}
			
			closeResultSet();
			closeStatement();
		
		}
		catch (SQLException sqle) {
			throw sqle;
		}
		finally {
			closeResultSet();
			closeStatement();			
		}
		
	}
	
	public void putUseListAmount(long id, String companyCode, long reuse, BankCreditExtComplexResultInfo info) throws Exception {

		StringBuffer sql = new StringBuffer();
		sql.append("select j.sum1,j.sum2,l.sum1,l.sum2,b.sum1,b.sum2,d.sum1,d.sum2,f.sum1,f.sum2,h.sum1,h.sum2 ");
		sql.append("from ");
		sql.append("(select ");
		sql.append("sum(case when a.nstatus<>5 then a.mamount*a.mexchangerate else 0 end) sum1,");
		sql.append("sum(a.mamount*a.mexchangerate) sum2 ");
		sql.append("from loan_letter_credit a ");
		sql.append("where a.nbankcreditextid=? and a.nstatus<>0 and a.sapplycompanycode=?) b, ");
		sql.append("(select ");
		sql.append("sum(case when c.nstatus<>2 then c.mamount*c.mexchangerate else 0 end) sum1,");
		sql.append("sum(c.mamount*c.mexchangerate) sum2 ");
		sql.append("from loan_letter_guarantee c ");
		sql.append("where c.nbankcreditextid=? and c.nstatus<>0 and c.sapplycompanycode=?) d, ");
		sql.append("(select ");
		sql.append("sum(case when e.statusid<>2 then e.money*e.exchangerate else 0 end) sum1,");
		sql.append("sum(e.money*e.exchangerate) sum2 ");
		sql.append("from loan_creditprove e ");
		sql.append("where e.confercontractno=? and e.statusid<>0 and e.applyclient=?) f, ");
		sql.append("(select ");
		sql.append("sum(case when g.statusid<>2 then g.money*g.exchangerate else 0 end) sum1,");
		sql.append("sum(g.money*g.exchangerate) sum2 ");
		sql.append("from loan_acceptbill g ");
		sql.append("where g.confercontractno=? and g.statusid<>0 and g.billclient=?) h,");
		sql.append("(select ");
		sql.append("sum(case when i.statusid<>2 then i.amount*i.exchangerate else 0 end) sum1,");
		sql.append("sum(i.amount*i.exchangerate) sum2 ");
		sql.append("from loan_bankloan i ");
		sql.append("where i.confercontractno=? and i.statusid<>0 and i.loantype=1 and i.loanclient=?) j, ");
		sql.append("(select ");
		sql.append("sum(case when k.statusid<>2 then k.amount*k.exchangerate else 0 end) sum1,");
		sql.append("sum(k.amount*k.exchangerate) sum2 ");
		sql.append("from loan_bankloan k ");
		sql.append("where k.confercontractno=? and k.statusid<>0 and k.loantype=2 and k.loanclient=?) l");
		
		try {
			
			pst = conn.prepareStatement(sql.toString());
			pst.setLong(1, id);
			pst.setString(2, companyCode);
			pst.setLong(3, id);
			pst.setString(4, companyCode);
			pst.setLong(5, id);
			pst.setString(6, companyCode);
			pst.setLong(7, id);
			pst.setString(8, companyCode);
			pst.setLong(9, id);
			pst.setString(10, companyCode);
			pst.setLong(11, id);
			pst.setString(12, companyCode);
			rs = pst.executeQuery();

			while (rs.next()) {
				if(reuse == 0)
				{
					info.setUsingSLAmount(rs.getDouble(1));
					info.setUsingLLAmount(rs.getDouble(3));
					info.setUsingLCAmount(rs.getDouble(5));
					info.setUsingLGAmount(rs.getDouble(7));
					info.setUsingCPAmount(rs.getDouble(9));
					info.setUsingABAmount(rs.getDouble(11));
				}
				else
				{
					info.setUsingSLAmount(rs.getDouble(2));
					info.setUsingLLAmount(rs.getDouble(4));
					info.setUsingLCAmount(rs.getDouble(6));
					info.setUsingLGAmount(rs.getDouble(8));
					info.setUsingCPAmount(rs.getDouble(10));
					info.setUsingABAmount(rs.getDouble(12));
				}				
				info.setUsedSLAmount(rs.getDouble(2));				
				info.setUsedLLAmount(rs.getDouble(4));				
				info.setUsedLCAmount(rs.getDouble(6));				
				info.setUsedLGAmount(rs.getDouble(8));				
				info.setUsedCPAmount(rs.getDouble(10));				
				info.setUsedABAmount(rs.getDouble(12));			
				break;				
			}
			closeResultSet();
			closeStatement();
			
		}
		catch (SQLException sqle) {
			throw sqle;
		}
		finally {
			closeResultSet();
			closeStatement();			
		}
		
	}
	
	private void putSplitedAmount(BankCreditExtComplexResultInfo info, int variety, double amount, double riskCoefficient) {
//		log4j.print("分配的综合额度，品种，额度，风险系数");
//		log4j.print(info.getContractNo()+"\t"+info.getCompanyName()+"\t" + variety+"\t"+amount+"\t"+riskCoefficient);
		switch (variety) {
			case (int)BankCreditVariety.SHORTTERMLOAN:
				info.setSplitedSLAmount(amount);
				info.setSLRiskCoefficient(riskCoefficient);
				break;
			case (int)BankCreditVariety.LONGTERMLOAN:
				info.setSplitedLLAmount(amount);
				info.setLlRiskCoefficient(riskCoefficient);
				break;
			case (int)BankCreditVariety.LETTEROFCREDIT:
				info.setSplitedLCAmount(amount);
				info.setLCRiskCoefficient(riskCoefficient);
				break;
			case (int)BankCreditVariety.LETTEROFIGUARANTEE:
				info.setSplitedLGAmount(amount);
				info.setLGRiskCoefficient(riskCoefficient);
				break;
			case (int)BankCreditVariety.PROVEOFCREDIT:
				info.setSplitedCPAmount(amount);
				info.setCPRiskCoefficient(riskCoefficient);
				break;
			case (int)BankCreditVariety.ACCEPTBILL:
				info.setSplitedABAmount(amount);
				info.setABRiskCoefficient(riskCoefficient);
				break;
			default:
				break;
		}
	}
	
	/**
	 * 关闭数据库资源
	 */
	
	public void closeConnection() {
		try {
			if (conn != null) 
 			{
 				conn.close();
 				conn = null;
 			}
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	public void closeStatement() {
		try {
			if (pst != null) 
 			{
 				pst.close();
 				pst = null;
 			}
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	public void closeResultSet() {
		try {
			if (rs != null) 
 			{
 				rs.close();
 				rs = null;
 			}
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
}
