/*
 * Created on 2006-10-16
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.loan.capitalprove.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import com.iss.itreasury.loan.base.LoanDAO;
import com.iss.itreasury.loan.capitalprove.dataentity.CapitalProveInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.system.dao.PageLoader;


/**
 * @author yyhe
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CapitalProveDao extends LoanDAO {
	
	public CapitalProveDao(){
		super("loan_capitalprove");
	}
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	//  新增资信证明，将info里的信息insert到表loan_CapitalProve里
	public long add(CapitalProveInfo info) throws Exception
	{
		long lret = -1;
		String strSQL = "insert into loan_capitalprove values ((select nvl(max(id)+1,1) id from loan_capitalprove),?,?,?,?,?,?,?,?,?,to_date(?,'yyyy-mm-dd'),to_date(?,'yyyy-mm-dd'),?,?,?,?)";
		try{
			conn = Database.getConnection();
			pstmt = conn.prepareStatement(strSQL);
			pstmt.setLong(1,info.getOfficeId());
			pstmt.setString(2,info.getCode());
			pstmt.setLong(3,info.getConferContractNo());
			pstmt.setString(4,info.getApplyClient());
			pstmt.setString(5,info.getCertificateBank());
			pstmt.setString(6,info.getCapitalProveID());
			pstmt.setString(7,info.getBeneficiaryName());
			pstmt.setString(8,info.getProjectName());
			pstmt.setLong(9,info.getApplyMonth());
			pstmt.setString(10,info.getStartDate());
			pstmt.setString(11,info.getEndDate());
			pstmt.setDouble(12,info.getCharge());
			pstmt.setLong(13,info.getStatusID());
			pstmt.setString(14,info.getApplyPurpose());
			pstmt.setString(15,info.getRemark());
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
	
	//	修改资信证明，将info里的信息update到表loan_CapitalProve里
	public long modify(CapitalProveInfo info) throws Exception
	{
		long lret = -1;
		StringBuffer strSQL = new StringBuffer("update loan_capitalprove set "); 
		strSQL.append("confercontractno=?,applyclient=?,certificatebank=?,");
		strSQL.append("capitalproveid=?,beneficiaryname=?,projectname=?,");
		strSQL.append("applymonth=?,startdate=to_date(?,'yyyy-mm-dd'),");
		strSQL.append("enddate=to_date(?,'yyyy-mm-dd'),charge=?,statusid=?,");
		strSQL.append("applypurpose=?,remark=? where id=?");
		try{
			conn = Database.getConnection();
			pstmt = conn.prepareStatement(strSQL.toString());
			pstmt.setLong(1,info.getConferContractNo());
			pstmt.setString(2,info.getApplyClient());
			pstmt.setString(3,info.getCertificateBank());
			pstmt.setString(4,info.getCapitalProveID());
			pstmt.setString(5,info.getBeneficiaryName());
			pstmt.setString(6,info.getProjectName());
			pstmt.setLong(7,info.getApplyMonth());
			pstmt.setString(8,info.getStartDate());
			pstmt.setString(9,info.getEndDate());
			pstmt.setDouble(10,info.getCharge());
			pstmt.setLong(11,info.getStatusID());
			pstmt.setString(12,info.getApplyPurpose());
			pstmt.setString(13,info.getRemark());
			pstmt.setLong(14,info.getId());
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
	
	//	删除资信证明，将info里的信息update到表loan_CapitalProve里
	public long delete(long id) throws Exception
	{
		long lret = -1;
		String strSQL = "UPDATE loan_capitalProve SET statusID = 0 WHERE id=?";
		try{
			conn = Database.getConnection();
			pstmt = conn.prepareStatement(strSQL);
			pstmt.setLong(1,id);
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
	
    //根据ID查询资信证明
	public CapitalProveInfo findByID(long id) throws Exception{
		CapitalProveInfo cInfo = null;
		String strSQL = "select e.*,f.name applyClientName from (select c.*,d.scontractno contractno " +
				"from loan_capitalprove c left join loan_bank_creditext d on c.confercontractno = d.id) e " +
				"left join client_clientinfo f on e.applyclient=f.code  WHERE e.id=?";
		try{
			conn = Database.getConnection();
			pstmt = conn.prepareStatement(strSQL);
			pstmt.setLong(1,id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				cInfo = new CapitalProveInfo();
				cInfo.setId(rs.getLong("id"));
				cInfo.setOfficeId(rs.getLong("officeId"));
				cInfo.setCode(rs.getString("code"));
				cInfo.setConferContractNo(rs.getLong("conferContractNo"));
				cInfo.setContractNo(rs.getString("contractNo"));
				cInfo.setApplyClient(rs.getString("applyClient"));
				cInfo.setApplyClientName(rs.getString("applyClientName"));
				cInfo.setCertificateBank(rs.getString("certificateBank"));
				cInfo.setCapitalProveID(rs.getString("capitalProveID"));
				cInfo.setBeneficiaryName(rs.getString("beneficiaryName"));
				cInfo.setProjectName(rs.getString("projectName"));
				cInfo.setApplyMonth(rs.getLong("applyMonth"));
				cInfo.setStartDate(DataFormat.formatDate(rs.getDate("startDate"),1));
				cInfo.setEndDate(DataFormat.formatDate(rs.getDate("endDate"),1));
				cInfo.setCharge(rs.getLong("charge"));
				cInfo.setStatusID(rs.getLong("statusID"));
				cInfo.setApplyPurpose(rs.getString("applyPurpose"));
				cInfo.setRemark(rs.getString("remark"));
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
		return cInfo;
		
	}
    //获得所有的资信证明
//	public Collection findAllCapitalProve() throws Exception{
//		Collection cret = null;
//		String strSQL = "SELECT * FROM loan_capitalProve ORDER BY code";
//		try{
//			conn = Database.get_jdbc_connection();
//			pstmt = conn.prepareStatement(strSQL);
//			rs = pstmt.executeQuery();
//			cret = new ArrayList();
//			while(rs.next()){
//				CapitalProveInfo cInfo =  new CapitalProveInfo();
//				cInfo.setId(rs.getLong("id"));
//				cInfo.setCode(rs.getString("code"));
//				cInfo.setConferContractNo(rs.getLong("conferContractNo"));
//				cInfo.setApplyClient(rs.getString("applyClient"));
//				cInfo.setCertificateBank(rs.getString("certificateBank"));
//				cInfo.setCapitalProveID(rs.getString("capitalProveID"));
//				cInfo.setBeneficiaryName(rs.getString("beneficiaryName"));
//				cInfo.setProjectName(rs.getString("projectName"));
//				cInfo.setApplyMonth(rs.getLong("applyMonth"));
//				cInfo.setStartDate(DataFormat.formatDate(rs.getDate("startDate"),1));
//				cInfo.setEndDate(DataFormat.formatDate(rs.getDate("endDate"),1));
//				cInfo.setCharge(rs.getLong("charge"));
//				cInfo.setStatusID(rs.getLong("statusID"));
//				cInfo.setApplyPurpose(rs.getString("applyPurpose"));
//				cInfo.setRemark(rs.getString("remark"));
//				cret.add(cInfo);
//			}
//		}catch(Exception e){
//			
//			e.printStackTrace();
//			throw e;
//		}finally{
//			if(rs != null){
//				rs.close();
//				rs = null;
//			}
//			if(pstmt != null){
//				pstmt.close();
//				pstmt = null;
//			}
//			if(conn != null){
//				conn.close();
//				conn = null;
//			}
//		}
//		return cret;
//	}
	//	查询资信证明，按info里的信息查询表loan_CapitalProve
	//返回由CapitalProveInfo组成的集合
//	public Collection findByCapitalProve(CapitalProveInfo info) throws Exception
//	{
//		Collection cret = null;
//		String strTemp = null;
//		long lTemp = 0;
//		StringBuffer strSQL = new StringBuffer("SELECT * FROM loan_capitalProve WHERE statusID>0");
//		
//		lTemp = info.getConferContractNo();
//		if(lTemp > 0){
//			strSQL.append(" and conferContractNo=" + lTemp);
//		}
//		
//		strTemp = info.getApplyClient();
//		if(strTemp != null && !strTemp.equals("")){
//			strSQL.append(" and applyClient='" + strTemp + "'" );
//		}
//		
//		strTemp = info.getCertificateBank();
//		if(strTemp != null && !strTemp.equals("")){
//			strSQL.append(" and certificateBank='" + strTemp + "'");
//		}
//		
//		lTemp = info.getApplyMonth();
//		if(lTemp > 0){
//			strSQL.append(" and applyMonth=" + lTemp);
//		}
//		
//		strTemp = info.getFromStartDate();
//		if(strTemp != null && !strTemp.equals("")){
//			strSQL.append(" and startDate>=to_date('" + strTemp + "','yyyy-mm-dd')");
//		}
//		
//		strTemp = info.getEndStartDate();
//		if(strTemp != null && !strTemp.equals("")){
//			strSQL.append(" and startDate<=to_date('" + strTemp + "','yyyy-mm-dd')");
//		}
//		
//		strTemp = info.getFromEndDate();
//		if(strTemp != null && !strTemp.equals("")){
//			strSQL.append(" and endDate>=to_date('" + strTemp + "','yyyy-mm-dd')");
//		}
//		
//		strTemp = info.getEndEndDate();
//		if(strTemp != null && !strTemp.equals("")){
//			strSQL.append(" and endDate<=to_date('" + strTemp + "','yyyy-mm-dd')");
//		}
//		
//		lTemp = info.getStatusID();
//		if(lTemp > 0){
//			strSQL.append(" and statusID=" + lTemp);
//		}
//		
//		strSQL.append(" ORDER BY code");
//		System.out.println(strSQL.toString());
//		try{
//			conn = Database.get_jdbc_connection();
//			pstmt = conn.prepareStatement(strSQL.toString());
//			rs = pstmt.executeQuery();
//			cret = new ArrayList();
//			while(rs.next()){
//				CapitalProveInfo cInfo =  new CapitalProveInfo();
//				cInfo.setId(rs.getLong("id"));
//				cInfo.setCode(rs.getString("code"));
//				cInfo.setConferContractNo(rs.getLong("conferContractNo"));
//				cInfo.setApplyClient(rs.getString("applyClient"));
//				cInfo.setCertificateBank(rs.getString("certificateBank"));
//				cInfo.setCapitalProveID(rs.getString("capitalProveID"));
//				cInfo.setBeneficiaryName(rs.getString("beneficiaryName"));
//				cInfo.setProjectName(rs.getString("projectName"));
//				cInfo.setApplyMonth(rs.getLong("applyMonth"));
//				cInfo.setStartDate(DataFormat.formatDate(rs.getDate("startDate"),1));
//				cInfo.setEndDate(DataFormat.formatDate(rs.getDate("endDate"),1));
//				cInfo.setCharge(rs.getLong("charge"));
//				cInfo.setStatusID(rs.getLong("statusID"));
//				cInfo.setApplyPurpose(rs.getString("applyPurpose"));
//				cInfo.setRemark(rs.getString("remark"));
//				cret.add(cInfo);
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//			
//			throw e;
//		}finally{
//			if(rs != null){
//				rs.close();
//				rs = null;
//			}
//			if(pstmt != null){
//				pstmt.close();
//				pstmt = null;
//			}
//			if(conn != null){
//				conn.close();
//				conn = null;
//			}
//		}
//		return cret;
//	}
	
	//生成内部流水号
	public String getNextCode(String prefix) throws Exception {
		String strSQL = "select code from loan_capitalprove where code like ?";
		String strTemp = prefix;
		String newCode = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(Env.getSystemDateTime());
		strTemp += calendar.get(Calendar.YEAR)+""+(calendar.get(Calendar.MONTH)+1);
		try{
			conn = Database.getConnection();
			pstmt = conn.prepareStatement(strSQL);
			pstmt.setString(1,strTemp + "%");
			rs = pstmt.executeQuery();
			int iCode = 0;
			while(rs.next()){
				String scode = rs.getString("code");
				scode = scode.substring(strTemp.length(),scode.length());
				if(scode != null && !scode.equals("")){
					int iTemp = Integer.parseInt(scode);
					if(iCode < iTemp){
						iCode = iTemp;
					}
				}
			}
			iCode += 1;
			newCode = "" +iCode;
			while(newCode.length() < 4){
				newCode = "0" + newCode;
			}
			newCode = strTemp + newCode;
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
		return newCode;
	}

	public PageLoader findWithPage(CapitalProveInfo info) throws Exception{
		
		String[] sql = getSQL(info);
		
		PageLoader pageLoader = null;
		try{
		pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		
		pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2], (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.loan.capitalprove.dataentity.CapitalProveInfo", null);
		
		pageLoader.setOrderBy(" " + sql[3] + " ");	
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return pageLoader;

	}
	
	public PageLoader findAllCapitalProveInfo(long officeId) throws Exception{
		String[] sql = new String[4];
		sql[0] = " e.*,f.name applyClientName ";
		sql[1] = " (select c.*,d.scontractno contractno from loan_capitalprove c left join " +
				"loan_bank_creditext d on c.confercontractno = d.id) e left join client_clientinfo " +
				"f on e.applyclient=f.code ";
		sql[2] = " e.statusID > 0 and e.officeId=" + officeId;
		String strDesc = " desc ";
		StringBuffer oBuf = new StringBuffer();
		oBuf.append(" order by e.code" + strDesc);
		sql[3] = oBuf.toString();
		
		PageLoader pageLoader = null;
		try{
			pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		
			pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2], (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.loan.capitalprove.dataentity.CapitalProveInfo", null);
		
			pageLoader.setOrderBy(" " + sql[3] + " ");	
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}

		return pageLoader;
	}
	
	private String[] getSQL(CapitalProveInfo info) throws Exception{
		
		String[] sql = new String[4];
		StringBuffer strSQL = new StringBuffer();
		String strTemp = null;
		long lTemp = -1;
		
		//select
		sql[0] =" e.*,f.name applyClientName ";
		//from
		sql[1] =" (select c.*,d.scontractno contractno from loan_capitalprove c left join " +
				"loan_bank_creditext d on c.confercontractno = d.id) e left join client_clientinfo " +
				"f on e.applyclient=f.code ";
		//where
		sql[2] =" e.statusID > 0 and e.officeId=" + info.getOfficeId();

		lTemp = info.getConferContractNo();
		if(lTemp > 0){
			strSQL.append(" and e.conferContractNo=" + lTemp);
		}
		
		strTemp = info.getApplyClient();
		if(strTemp != null && !strTemp.equals("")){
			strSQL.append(" and e.applyClient='" + strTemp + "'" );
		}
		
		strTemp = info.getCertificateBank();
		if(strTemp != null && !strTemp.equals("")){
			strSQL.append(" and e.certificateBank='" + strTemp + "'");
		}
		
		lTemp = info.getApplyMonth();
		if(lTemp > 0){
			strSQL.append(" and e.applyMonth=" + lTemp);
		}
		
		strTemp = info.getFromStartDate();
		if(strTemp != null && !strTemp.equals("")){
			strSQL.append(" and e.startDate>=to_date('" + strTemp + "','yyyy-mm-dd')");
		}
		
		strTemp = info.getEndStartDate();
		if(strTemp != null && !strTemp.equals("")){
			strSQL.append(" and e.startDate<=to_date('" + strTemp + "','yyyy-mm-dd')");
		}
		
		strTemp = info.getFromEndDate();
		if(strTemp != null && !strTemp.equals("")){
			strSQL.append(" and e.endDate>=to_date('" + strTemp + "','yyyy-mm-dd')");
		}
		
		strTemp = info.getEndEndDate();
		if(strTemp != null && !strTemp.equals("")){
			strSQL.append(" and e.endDate<=to_date('" + strTemp + "','yyyy-mm-dd')");
		}
		
		lTemp = info.getStatusID();
		if(lTemp > 0){
			strSQL.append(" and e.statusID=" + lTemp);
		}
		
		sql[2] = sql[2] + strSQL.toString();
		
		
		//order
		String strDesc = " desc ";
		StringBuffer oBuf = new StringBuffer();
		oBuf.append(" order by e.code" + strDesc);
		sql[3] = oBuf.toString();
		
		System.out.println("**********SQL**********"+sql[0]+"  "+sql[1]+"   "+sql[2]+"   "+sql[3]);
		
		return sql;

	}
}
