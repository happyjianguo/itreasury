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

import com.iss.itreasury.loan.bankloan.dataentity.InterestDetailInfo;
import com.iss.itreasury.util.Database;

public class InterestDetailDao {
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	//新增，将info里的信息insert到表loan_InterestDetail里
	public long add(InterestDetailInfo info) throws Exception
	{
		long lret = -1;
		String strSQL = "INSERT INTO loan_InterestDetail VALUES((SELECT NVL(MAX(id)+1,1) id FROM loan_InterestDetail),?,?,to_date(?,'yyyy-mm-dd'))";
		try{
			conn = Database.getConnection();
			pstmt = conn.prepareStatement(strSQL);
			pstmt.setDouble(1,info.getRate());
			pstmt.setLong(2,info.getBankLoanID());
			pstmt.setString(3,info.getStartDate());
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
	
	//	删除，将info里的信息update到表loan_InterestDetail里
	public long delete(String[] id) throws Exception
	{
		long lret = -1;
		StringBuffer strSQL = new StringBuffer("DELETE FROM loan_InterestDetail WHERE id IN(");
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
	
	//	查询，按info里的信息查询表loan_InterestDetail
	//返回由InterestDetailInfo组成的集合
	public List findAll(long id) throws Exception
	{
		List vret = null;
		String strSQL = "SELECT * FROM loan_InterestDetail WHERE bankLoanID=? ORDER BY id DESC";
		try{
			conn = Database.getConnection();
			pstmt = conn.prepareStatement(strSQL);
			pstmt.setLong(1,id);
			rs = pstmt.executeQuery();
			vret = new ArrayList();
			while(rs.next()){
				InterestDetailInfo info = new InterestDetailInfo();
				info.setId(rs.getLong("id"));
				info.setRate(rs.getDouble("rate"));
				info.setBankLoanID(rs.getLong("bankLoanID"));
				info.setStartDate(rs.getDate("startDate").toString());
				vret.add(info);
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
		return vret;
	}

}
