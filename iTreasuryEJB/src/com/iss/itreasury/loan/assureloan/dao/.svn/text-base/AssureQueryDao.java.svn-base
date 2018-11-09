package com.iss.itreasury.loan.assureloan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.iss.itreasury.loan.assureloan.dataentity.AssureQueryInfo;
import com.iss.itreasury.loan.assureloan.dataentity.AssureTempInfo;
import com.iss.itreasury.loan.assureloan.dataentity.AssureUsedInfo;
import com.iss.itreasury.util.Database;

public class AssureQueryDao {

	public List getAssrueInfo(AssureQueryInfo info) throws Exception {
		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();		
		sql.append("select a.assurecontractno,a.amount*a.exchangerate amount,a.confercontractno,a.assuretype,a.warranteename ");
		sql.append("from loan_assure a ");
		sql.append("where (-1=? or a.code=?) ");
		sql.append("and (-1=? or a.assurecontractno=?) ");
		sql.append("and (-1=? or a.assurekind=?) ");
		sql.append("and (-1=? or a.assuremode=?) ");
		sql.append("and (-1=? or a.startdate>=to_date(?,'yyyy-mm-dd')) ");
		sql.append("and (-1=? or a.startdate<=to_date(?,'yyyy-mm-dd')) ");
		sql.append("and (-1=? or a.enddate>=to_date(?,'yyyy-mm-dd')) ");
		sql.append("and (-1=? or a.enddate<=to_date(?,'yyyy-mm-dd')) ");
		
		try {
			conn = Database.getConnection();
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, info.getCode() == null ? -1 : 0);
			pstmt.setString(2, info.getCode());
			pstmt.setInt(3, info.getContractNo() == null ? -1 : 0);
			pstmt.setString(4, info.getContractNo());
			pstmt.setInt(5, info.getAssureKind());
			pstmt.setInt(6, info.getAssureKind());
			pstmt.setInt(7, info.getAssureMode());
			pstmt.setInt(8, info.getAssureMode());
			pstmt.setInt(9, info.getStartDate1() == null ? -1 : 0);
			pstmt.setString(10, info.getStartDate1());
			pstmt.setInt(11, info.getStartDate2() == null ? -1 : 0);
			pstmt.setString(12, info.getStartDate2());
			pstmt.setInt(13, info.getEndDate1() == null ? -1 : 0);
			pstmt.setString(14, info.getEndDate1());
			pstmt.setInt(15, info.getEndDate2() == null ? -1 : 0);
			pstmt.setString(16, info.getEndDate2());
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				AssureTempInfo info1 = new AssureTempInfo();
				info1.setContractNo(rs.getString(1));
				info1.setAmount(rs.getDouble(2));
				info1.setBankCreditId(rs.getLong(3));
				info1.setVariety(rs.getString(4));
				info1.setCompanyCode(rs.getString(5));
				list.add(info1);
			}
		}
		catch (Exception e) {
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
		
		return list;
	}
	
	public ArrayList[] getClient(String startClient, String endClient) throws Exception {		
		ArrayList lstCode = new ArrayList();
		ArrayList lstName = new ArrayList();
		ArrayList[] rtnClient = new ArrayList[]{lstCode, lstName};
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();		
		sql.append("select a.scode,a.sname ");
		sql.append("from client a  ");
		sql.append("where (1=? or a.sCode>=?) and (1=? or a.sCode<=?) ");
		sql.append("and a.nStatusID=1 ");
		
		try {
			conn = Database.getConnection();
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, startClient == null ? 1 : 0);
			pstmt.setString(2, startClient);
			pstmt.setInt(3, endClient == null ? 1 : 0);
			pstmt.setString(4, endClient);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {				
				lstCode.add(rs.getString(1));
				lstName.add(rs.getString(2));				
			}			
		}
		catch (Exception e) {
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
		
		return rtnClient;
	}
	
	public List getUsedAssureInfo(long id, String companyCode, String assureType) throws Exception {
		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();		
		sql.append("select a.loanclient,b.sName,1,sum(a.amount*a.exchangerate) ");
		sql.append("from loan_bankloan a,client b ");
		sql.append("where a.statusid<>0 and a.statusid<>2 and a.loantype=1 and a.loanclient=b.sCode ");
		sql.append("and a.confercontractno=? and instr(?,'1',1,1)>0 and instr(?,a.loanclient,1,1)>0 ");
		sql.append("group by a.loanclient,b.sName ");
		sql.append("union ");
		sql.append("select a.loanclient,b.sName,2,sum(a.amount*a.exchangerate) ");
		sql.append("from loan_bankloan a,client b ");
		sql.append("where a.statusid<>0 and a.statusid<>2 and a.loantype=2 and a.loanclient=b.sCode ");
		sql.append("and a.confercontractno=? and instr(?,'2',1,1)>0 and instr(?,a.loanclient,1,1)>0 ");
		sql.append("group by a.loanclient,b.sName ");
		sql.append("union ");
		sql.append("select a.sapplycompanycode,b.sName,3,sum(a.mamount*a.mexchangerate) ");
		sql.append("from loan_letter_credit a,client b ");
		sql.append("where a.nstatus<>0 and a.nstatus<> 5 and a.sapplycompanycode=b.sCode ");
		sql.append("and a.nbankcreditextid=? and instr(?,'3',1,1)>0 and instr(?,a.sapplycompanycode,1,1)>0 ");
		sql.append("group by a.sapplycompanycode, b.sName ");
		sql.append("union ");
		sql.append("select a.sapplycompanycode,b.sName,4,sum(a.mamount*a.mexchangerate) ");
		sql.append("from loan_letter_guarantee a, client b ");
		sql.append("where a.nstatus<>0 and a.nstatus<>2 and a.sapplycompanycode=b.sCode ");
		sql.append("and a.nbankcreditextid=? and instr(?,'4',1,1)>0 and instr(?,a.sapplycompanycode,1,1)>0 ");
		sql.append("group by a.sapplycompanycode,b.sName ");
		sql.append("union ");
		sql.append("select a.applyclient,b.sName,5,sum(a.money*a.exchangerate) ");
		sql.append("from loan_creditprove a, client b ");
		sql.append("where a.statusid<>0 and a.statusid<> 2 and a.applyclient=b.sCode ");
		sql.append("and a.confercontractno=? and instr(?,'5',1,1)>0 and instr(?,a.applyclient,1,1)>0 ");
		sql.append("group by a.applyclient,b.sName ");
		sql.append("union ");
		sql.append("select a.billclient,b.sName,6,sum(a.money*a.exchangerate) ");
		sql.append("from loan_acceptbill a, client b ");
		sql.append("where a.statusid<>0 and a.statusid<>2 and a.billclient=b.sCode ");
		sql.append("and a.confercontractno=? and instr(?,'6',1,1)>0 and instr(?,a.billclient,1,1)>0 ");
		sql.append("group by a.billclient,b.sName ");

		try {
			conn = Database.getConnection();
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, id);
			pstmt.setString(3, companyCode);
			pstmt.setString(2, assureType);
			pstmt.setLong(4, id);
			pstmt.setString(6, companyCode);
			pstmt.setString(5, assureType);
			pstmt.setLong(7, id);
			pstmt.setString(9, companyCode);
			pstmt.setString(8, assureType);
			pstmt.setLong(10, id);
			pstmt.setString(12, companyCode);
			pstmt.setString(11, assureType);
			pstmt.setLong(13, id);
			pstmt.setString(15, companyCode);
			pstmt.setString(14, assureType);
			pstmt.setLong(16, id);
			pstmt.setString(18, companyCode);
			pstmt.setString(17, assureType);			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				AssureUsedInfo info = new AssureUsedInfo();
				info.setClientCode(rs.getString(1));
				info.setClientName(rs.getString(2));
				info.setVariety(rs.getInt(3));
				info.setAmount(rs.getDouble(4));
				list.add(info);
			}
		}
		catch (Exception e) {
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
	
		return list;
	}
	

}
