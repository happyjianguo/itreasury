package com.iss.itreasury.loan.aftercredit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.iss.system.dao.PageLoader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.aftercredit.dataentity.AfterCreditFileInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

/**
 * 
 * @author lipeng
 * 
 */
public class AfterCreditFileDao extends ITreasuryDAO {

	private static Log4j log4j = null;

	public AfterCreditFileDao() {

		super("loan_aftercreditfile");
		setUseMaxID();
		log4j = new Log4j(Constant.ModuleType.LOAN, this);

	}

	public AfterCreditFileDao(Connection conn) {

		super("loan_aftercreditfile", conn);
		setUseMaxID();
		log4j = new Log4j(Constant.ModuleType.LOAN, this);

	}

	/**
	 * 增加一条上传excel数据到loan_aftercreditfile表中
	 * 
	 * @param afterCreditFileInfo
	 * @throws SQLException
	 */
	public long saveUploadFile(AfterCreditFileInfo afterCreditFileInfo) {

		long id = -1;

		try {
			initDAO();

			id = add(afterCreditFileInfo);

		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			id = -1;
			e.printStackTrace();

		} finally {

			try {
				finalizeDAO();
			} catch (ITreasuryDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return id;

	}

	/**
	 * 给上传文件赋上贷后调查报告ID
	 * 
	 * @param reportID
	 * @param ids
	 * @return
	 * @throws IException
	 */
	public long updateUploadFile(String reportID, String[] ids)
			throws IException {

		long flag = -1;// 是否插入成功
		PreparedStatement ps = null;

		try {
			initDAO();
			String SQL = "update loan_aftercreditfile set reportid=? where id in ("
					+ ids + ")";
			try {
				ps.executeUpdate(SQL);
			} catch (SQLException e) {

				flag = -1;
				throw new IException("更新贷后调查报告上传文件失败！！！");
			}

		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();

		} finally {

			try {
				finalizeDAO();
			} catch (ITreasuryDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return flag;

	}

	/**
	 * 查询对应调查报告的上传文件
	 * 
	 * @param ids
	 * @return
	 * @throws IException
	 * @throws SQLException
	 */
	public List queryUploadFile(String ids) throws IException, SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		List list = new ArrayList();

		String sql = "select a.id as fileid ,c.id as checkreportID, c.checkreportcode as checkreportcode,b.sname as upUserName,a.upfiledate as upFiledate, a.fileName as newFileName, a.oldfileName as oldFileName"
				+ "  from loan_aftercreditfile a,userinfo b ,loan_loanaftercheckreport c  "
				+ "where b.id=a.upuserid and a.reportid=c.id(+) and a.id in("
				+ ids + ")";
		try {
			con = Database.getConnection();

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {

				AfterCreditFileInfo acf = new AfterCreditFileInfo();

				acf.setId(rs.getLong("fileid"));
				acf.setCheckReportCode(rs.getString("checkreportcode"));
				acf.setUpUserName(rs.getString("upUserName"));
				acf.setUpFileDate(rs.getTimestamp("upFiledate"));
				acf.setFileName(rs.getString("newFileName"));
				acf.setOldfileName(rs.getString("OldFileName"));

				list.add(acf);

			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			log4j.error(e.toString());
		} finally {

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}
		}

		return list;

	}
	/**
	 * 查询已经上传过的文件
	 * @param ids
	 * @return
	 * @throws IException
	 * @throws SQLException
	 */
	public List queryExistUploadFile(String reportid) throws IException, SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		List list = new ArrayList();

		String sql = "select a.id as fileid ,c.id as checkreportID, c.checkreportcode as checkreportcode,b.sname as upUserName,a.upfiledate as upFiledate, a.fileName as newFileName, a.oldfileName as oldFileName"
				+ "  from loan_aftercreditfile a,userinfo b ,loan_loanaftercheckreport c  "
				+ "where b.id=a.upuserid and a.reportid=c.id and c.id ="
				+ reportid + "";
		try {
			con = Database.getConnection();

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {

				AfterCreditFileInfo acf = new AfterCreditFileInfo();

				acf.setId(rs.getLong("fileid"));
				acf.setCheckReportCode(rs.getString("checkreportcode"));
				acf.setUpUserName(rs.getString("upUserName"));
				acf.setUpFileDate(rs.getTimestamp("upFiledate"));
				acf.setFileName(rs.getString("newFileName"));
				acf.setOldfileName(rs.getString("OldFileName"));

				list.add(acf);

			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			log4j.error(e.toString());
		} finally {

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}
		}

		return list;

	}
	
	/**
	 * 删除对应文件名的上传文件
	 * 
	 * @param fileName
	 * @return
	 * @throws IException
	 * @throws SQLException
	 */
	public boolean deleteUploadFile(String fileName) throws IException, SQLException {

		PreparedStatement ps = null;
		
		Connection con = null;
		boolean deleteFlag=false;

		String sql = "delete  from loan_aftercreditfile  where filename = '"+fileName+"'";
				
		try {
			con = Database.getConnection();

			ps = con.prepareStatement(sql);

			deleteFlag=ps.execute();
			
			
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			log4j.error(e.toString());
		
		} finally {

		
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}
		}
		
		return deleteFlag;

	}
	
	

}
