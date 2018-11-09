package com.iss.itreasury.ebank.obaccountinfo.bizlogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;

public class OBAccountSignatureBiz {

	public boolean checkHasRight(long clientId, long officeId, long bzid,
			String nbilltypeId,long userId) throws SQLException, ITreasuryDAOException {
		long nbilltypeid = Long.parseLong(nbilltypeId);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		// 调试用
//		if (true) {
//			recordPrintInfo(clientId, officeId, bzid, nbilltypeId,userId);
//			return true;
//		}
		try {
			conn = Database.getConnection();
			stmt = conn.createStatement();
			String clientSql = "select count(*) as c from sett_signature where NOFFICEID="
					+ officeId + "  and NCLIENTID=" + clientId + "  ";
			rs = stmt.executeQuery(clientSql);
			int count = 0;
			while (rs.next()) {
				count = rs.getInt("c");
			}
			if (count == 0) {
				System.out.println("电子签章--检查客户权限=====没有权限");
				return false;
			}
			System.out.println("电子签章--检查客户权限=====有权限");
			count = 0;
			String officeSql = "select count(*) as c from sett_billtype_signature   where NOFFICEID="
					+ officeId
					+ "  and NCURRENCYID="
					+ bzid
					+ "  and  NBILLTYPEID=" + nbilltypeid;
			rs2 = stmt.executeQuery(officeSql);
			while (rs2.next()) {
				count = rs2.getInt("c");
			}
			if (count == 0) {
				System.out.println("电子签章--检查单据权限=====没有权限");
				return false;
			}
			recordPrintInfo(clientId, officeId, bzid, nbilltypeId,userId);
			System.out.println("电子签章--检查单据权限=====有权限");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs2 != null)
				rs2.close();
			if (rs != null)
				rs.close();
			if(stmt!=null){
				stmt.close();
			}
			if(conn!=null&&!conn.isClosed())
				conn.close();
		}
		return true;
	}

	protected long getPrint_printRecordID() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		long id = -1;
		try {
			conn = Database.getConnection();
			stmt = conn.createStatement();
			StringBuffer sb = new StringBuffer();
			sb
					.append("select seq_Print_printRecordID.nextval nextid from dual");
			rs = stmt.executeQuery(sb.toString());
			if (rs.next()) {
				id = rs.getLong("nextid");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return id;
	}


	public void recordPrintInfo(long clientId, long officeId, long bzid,
			String nbilltypeId,long userId) throws ITreasuryDAOException {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = Database.getConnection();
			stmt = conn.createStatement();
			long id = this.getPrint_printRecordID();
			String sql = "insert into sett_signature_printinfo(id, nofficeid,nclientid, ncurrencyid, nbilltypeid, printuser, dtprintdate, dtinputdate, inputuserid)"
					+ "values("+ id+ ","+ officeId+ ","+clientId+","+ bzid+ ",'"+ nbilltypeId+ "',"+ userId+ ",sysdate,sysdate," + userId + ")";
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
				try {
					if(stmt!=null)
					stmt.close();
					if(conn!=null&&!conn.isClosed())
						conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
		/**
		 *  记录电子签章打印日志
		 * @add by liaiyi 2013-04-02
		
		 */
		public void recordPrintInfo(long clientId, long officeId, long bzid,
				String nbilltypeId,long userId,String sTransNo,String nPrintCount) throws ITreasuryDAOException {
			Connection conn = null;
			Statement stmt = null;
			try {
				conn = Database.getConnection();
				stmt = conn.createStatement();
				long id = this.getPrint_printRecordID();
				String sql = "insert into sett_signature_printinfo(id, nofficeid,nclientid, ncurrencyid, nbilltypeid, printuser, dtprintdate, dtinputdate, inputuserid,sTransNo,nPrintCount)"
						+ "values("+ id+ ","+ officeId+ ","+clientId+","+ bzid+ ",'"+ nbilltypeId+ "',"+ userId+ ",sysdate,sysdate," + userId + ","+sTransNo+","+nPrintCount+")";
				stmt.executeUpdate(sql);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
					try {
						if(stmt!=null)
						stmt.close();
						if(conn!=null&&!conn.isClosed())
							conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
		}
	}
}
