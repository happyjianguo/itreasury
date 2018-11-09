package com.iss.itreasury.loan.bankloan.dao;
/**
 * @author yyhe
 * Created on 2006-10-20
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.iss.itreasury.loan.bankloan.dataentity.PayandrepayFactInfo;
import com.iss.itreasury.util.Database;

public class PayandrepayFactDao {
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	//新增，将info里的信息insert到表loan_PayandrepayFact里
	public long add(PayandrepayFactInfo info) throws Exception
	{
		long lret = -1;
		String strSQL = "INSERT INTO loan_PayandrepayFact VALUES((SELECT NVL(MAX(id)+1,1) id FROM loan_PayandrepayFact),?,?,to_date(?,'yyyy-mm-dd'),?)";
		try{
			conn = Database.getConnection();
			pstmt = conn.prepareStatement(strSQL);
			pstmt.setLong(1,info.getBankLoanID());
			pstmt.setDouble(2,info.getAmount());
			pstmt.setString(3,info.getExecuteDate());
			pstmt.setLong(4,info.getStatusID());
			lret = pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			if(pstmt != null){
				pstmt.close();
				pstmt = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
		}
		return lret;
	}
	
	//	修改，将info里的信息update到表loan_PayandrepayFact里
	public long modify(PayandrepayFactInfo info) throws Exception
	{
		long lret = -1;
		StringBuffer strSQL = new StringBuffer("UPDATE loan_PayandrepayFact SET ");
		strSQL.append("amount=?,executeDate=to_date(?,'yyyy-mm-dd'),");
		strSQL.append("statusID=? WHERE id=?");
		try{
			conn = Database.getConnection();
			pstmt = conn.prepareStatement(strSQL.toString());
			pstmt.setDouble(1,info.getAmount());
			pstmt.setString(2,info.getExecuteDate());
			pstmt.setLong(3,info.getStatusID());
			pstmt.setLong(4,info.getId());
			lret = pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			if(pstmt != null){
				pstmt.close();
				pstmt = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
		}
		return lret;
	}
	
	//	删除，将info里的信息update到表loan_PayandrepayFact里
	public long delete(String[] id) throws Exception
	{
		long lret = -1;
		StringBuffer strSQL = new StringBuffer("DELETE FROM loan_PayandrepayFact WHERE id IN(");
		if(id != null && id.length >0){
			for(int i=0;i<id.length;i++){
				strSQL.append(id[i]+ ",");
			}
			strSQL = strSQL.deleteCharAt(strSQL.length()-1);
		}
		strSQL.append(")");
		System.out.println(strSQL.toString());
		try{
			conn = Database.getConnection();
			pstmt = conn.prepareStatement(strSQL.toString());
			lret = pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			if(pstmt != null){
				pstmt.close();
				pstmt = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
		}
		return lret;
	}
	
	//	查询，按info里的信息查询表loan_PayandrepayFact
	//返回由PayandrepayFactInfo组成的集合
	public PayandrepayFactInfo findById(long id) throws Exception
	{
		PayandrepayFactInfo info = null;
		String strSQL = "SELECT * FROM loan_PayandrepayFact WHERE id=?";
		try{
			conn = Database.getConnection();
			pstmt = conn.prepareStatement(strSQL);
			pstmt.setLong(1,id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				info = new PayandrepayFactInfo();
				info.setId(rs.getLong("id"));
				info.setBankLoanID(rs.getLong("bankLoanID"));
				info.setAmount(rs.getDouble("amount"));
				info.setExecuteDate(rs.getDate("executeDate").toString());
				info.setStatusID(rs.getLong("statusID"));
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			if(rs != null){
				rs.close();
				rs = null;
			}
			if(pstmt != null){
				pstmt.close();
				pstmt = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
		}
		return info;
	}
	
	public List findAll(long id) throws Exception{
		List cret = null;
		String strSQL = "SELECT * FROM loan_PayandrepayFact WHERE bankLoanID=? ORDER BY id DESC";
		try{
			conn = Database.getConnection();
			pstmt = conn.prepareStatement(strSQL);
			pstmt.setLong(1,id);
			rs = pstmt.executeQuery();
			cret = new ArrayList();
			while(rs.next()){
				PayandrepayFactInfo info = new PayandrepayFactInfo();
				info.setId(rs.getLong("id"));
				info.setBankLoanID(rs.getLong("bankLoanID"));
				info.setAmount(rs.getDouble("amount"));
				info.setExecuteDate(rs.getDate("executeDate").toString());
				info.setStatusID(rs.getLong("statusID"));
				cret.add(info);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			if(rs != null){
				rs.close();
				rs = null;
			}
			if(pstmt != null){
				pstmt.close();
				pstmt = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
		}
		return cret;
	}
	
}
