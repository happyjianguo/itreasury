package com.iss.itreasury.codingrule.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.iss.itreasury.system.util.SYSConstant;
import com.iss.itreasury.util.Database;

public class EncodingRulesDao {

	/**
	 * 编码规则设置查询类
	 * add by liaiyi 2012-12-10
	 */
     public String queryCodingRuleDetailSQL(long officeid,long statusID ){
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM SYS_CodingRule s \n");
		sql.append(" where s.officeid ="+officeid+" and s.statusID ="+SYSConstant.CodingRuleStatus.SAVED +"\n");
		sql.append(" order by ID");
		
		return sql.toString();
	}
     
     public long queryCodingRuleDel(long id){
 		
 		String sql = "";
 		int num = -1;
 		Connection conn = null;
 		ResultSet rs = null;
 		PreparedStatement ps = null;
 		try {
			conn = Database.getConnection();
			sql = "select count(*) from sys_codingrulerelation where  CODINGRULEID = "+id ;
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				num = rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		return num;
 	}
     
     public long querySerialNumberDel(long id){
  		
  		String sql = "";
  		int num = -1;
  		Connection conn = null;
  		ResultSet rs = null;
  		PreparedStatement ps = null;
  		try {
 			conn = Database.getConnection();
 			sql = "select count(*) from sys_codingrulerelation where  serialnumberid = "+id ;
 			ps = conn.prepareStatement(sql);
 			rs = ps.executeQuery();
 			while(rs.next()){
 				num = rs.getInt(1);
 			}
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
  		return num;
  	}
     
}
