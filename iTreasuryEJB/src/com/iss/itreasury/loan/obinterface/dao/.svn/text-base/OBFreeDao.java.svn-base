package com.iss.itreasury.loan.obinterface.dao;

/**
 * @author yanhuang
 *免还申请接口
 */
import com.iss.itreasury.loan.obinterface.dataentity.*;
import java.sql.*;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
//import com.iss.itreasury.loan.util.*;

public class OBFreeDao {
	
	private static Log4j log4j = null;

	public OBFreeDao() throws Exception{
		log4j = new Log4j(Constant.ModuleType.EBANK, this);
	}
	
	public OBFreeInfo findByID(long freeID ) throws Exception
	{	
		OBFreeInfo info = new OBFreeInfo();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();

			
			String strSQL =
				" select a.*  "
					+ "  ,b.sName as InputUserName "
					+ " from ob_free a, OB_user b "
					+ "  where  a.nInputUserID=b.ID(+) "
					+ "  and a.ID = ?";

			
			ps = con.prepareStatement(strSQL);
			
			ps.setLong(1, freeID);
			log4j.info("ObFreeApplyEJB.findByID()\n");
			
			rs = ps.executeQuery();
			//
			log4j.info("findFreeApplyByID():success\n");
			if (rs.next()) {
								
				info.setID(rs.getLong("ID"));
				//log4j.info("set Free Data Entity:successs\n");
				info.setContractID(rs.getLong("NCONTRACTID"));
				//log4j.info("set Free Data Entity:successs\n");
				info.setLoanPayID(rs.getLong("NPAYFORMID"));
				//log4j.info("set Free Data Entity:successs\n");
				info.setFreeAmount(rs.getDouble("MFREEAMOUNT"));
				//log4j.info("set Free Data Entity:successs\n");
				info.setFreeDate(rs.getTimestamp("DTFREEDATE"));
				//log4j.info("set Free Data Entity:successs\n");
				info.setFreeRate(rs.getDouble("MINTEREST"));
				//log4j.info("set Free Data Entity:successs\n");
				info.setFreeReason(rs.getString("SFREEREASON"));
				//log4j.info("set Free Data Entity:successs\n");
				info.setStatusID(rs.getLong("NSTATUSID"));
				//log4j.info("set Free Data Entity:successs\n");
				info.setInputUserID(rs.getLong("NINPUTUSERID"));
				//log4j.info("set Free Data Entity:successs\n");
				info.setInputUserName(rs.getString("InputUserName"));
				//log4j.info("set Free Data Entity:successs\n");
				info.setInputDate(rs.getTimestamp("DTINPUTDATE"));
				//log4j.info("set Free Data Entity:successs\n");
				info.setConsignClientAccount(rs.getString("SACCOUNTNO"));
				
					
			}
			log4j.info("set Free Data Entity:successs\n");
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;

		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		} finally {
			try {
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		log4j.info("findbyid返回查询结果");
		return info;
	}
	public long updateOB(OBBackInfo info) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		long lResult = -1;
		int nIndex = 1;

		try {
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append("UPDATE OB_Free SET  ");
			
			if (info.getInID() > 0) {
				sbSQL.append(" ninid=? ,");
			}

			if (info.getStatusID() !=0) {
				sbSQL.append("NSTATUSID=? ,");
			}
			if (info.getUserID() > 0) {
				sbSQL.append("NHANDLEUSERID=? ,");
			}
			sbSQL.append(" ID=ID ");
			sbSQL.append("where ID=?");
			System.out.println(sbSQL);
			ps=con.prepareStatement(sbSQL.toString());
			if (info.getInID() > 0) {
				ps.setLong(nIndex++, info.getInID());
			}
			if (info.getStatusID() !=0) {
				ps.setLong(nIndex++, info.getStatusID());
			}
			if (info.getUserID() > 0) {
				ps.setLong(nIndex++, info.getUserID());
			}
			ps.setLong(nIndex++, info.getID());
			lResult = ps.executeUpdate();
			if (lResult < 1) {
				throw new IException("OB_EC19");
			} else {
				lResult = info.getID();
			}
			// 关闭数据库资源
			ps.close();
			ps = null;
			con.close();
			con = null;
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		} finally {
			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		log4j.info("update结果=" + lResult);
		return lResult;
	}
	
	
	public long updateOBStatus(long lInID,long lStatus) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		long lResult = -1;
		int nIndex = 1;
		long lUpdateStatus = -1;
		lUpdateStatus = lStatus;

		try {
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("UPDATE OB_Free SET NSTATUSID = ? ");
			sb.append(" where NINID = ?");
			//为了协调网银和一期在“执行日”上的一致性增加下面一段代码
			//用途：获取开关机状态和开机时间

			log4j.info(sb.toString());
			ps = con.prepareStatement(sb.toString());
			nIndex = 1;
			ps.setLong(nIndex++, lUpdateStatus);
			ps.setLong(nIndex++, lInID);	
			//执行update
			lResult = ps.executeUpdate();
			// 关闭数据库资源

			ps.close();
			ps = null;
			con.close();
			con = null;
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		} finally {
			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lResult;
	}

}
