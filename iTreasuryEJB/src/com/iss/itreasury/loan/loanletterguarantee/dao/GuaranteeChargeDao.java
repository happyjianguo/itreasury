package com.iss.itreasury.loan.loanletterguarantee.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import com.iss.itreasury.loan.loanletterguarantee.dataentity.GuaranteeChargeInfo;
import com.iss.itreasury.util.Database;

public class GuaranteeChargeDao {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	//新增
	public long add(GuaranteeChargeInfo info) throws Exception
	{
		long lret = -1;
		String strSQL = "INSERT INTO loan_Letter_Guarantee_Charge(id,GUARANTEEID,nSection,nRate,dPaymentDate,mPaymentAmout) VALUES((SELECT NVL(MAX(id)+1,1) id FROM loan_Letter_Guarantee_Charge),?,?,?,to_date(?,'yyyy-mm-dd'),?)";
		try{
			conn = Database.get_jdbc_connection();
			pstmt = conn.prepareStatement(strSQL);
			pstmt.setLong(1,info.getGuaranteeID());
			pstmt.setLong(2,info.getNSection());
			pstmt.setDouble(3,info.getNRate());
			pstmt.setString(4,info.getDPaymentDate());
			pstmt.setDouble(5,info.getMPaymentAmout());
			
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
	
	//	删除
	public long delete(String[] id) throws Exception
	{
		long lret = -1;
		StringBuffer strSQL = new StringBuffer("DELETE FROM loan_Letter_Guarantee_Charge WHERE id IN(");
		if(id != null && id.length >0){
			for(int i=0;i<id.length;i++){
				strSQL.append(id[i]+ ",");
			}
			strSQL = strSQL.deleteCharAt(strSQL.length()-1);
		}
		strSQL.append(")");
		System.out.println(strSQL.toString());
		try{
			conn = Database.get_jdbc_connection();
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
	
	//	查询
	//返回由GuaranteeChargeInfo组成的集合
	public Collection findAll(long id) throws Exception
	{
		Collection vret = null;
		String strSQL = "SELECT * FROM loan_Letter_Guarantee_Charge WHERE GUARANTEEID=? ORDER BY id DESC";
		try{
			conn = Database.get_jdbc_connection();
			pstmt = conn.prepareStatement(strSQL);
			pstmt.setLong(1,id);
			rs = pstmt.executeQuery();
			vret = new ArrayList();
			while(rs.next()){
				GuaranteeChargeInfo info = new GuaranteeChargeInfo();
				info.setId(rs.getLong("id"));
				info.setGuaranteeID(rs.getLong("guaranteeID"));
				info.setNSection(rs.getLong("nSection"));
				info.setNRate(rs.getDouble("nRate"));
				info.setDPaymentDate(rs.getDate("dPaymentDate").toString());
				info.setMPaymentAmout(rs.getDouble("mPaymentAmout"));
				
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
