package com.iss.itreasury.ebank.obdrawnotice.dao;

/**
 * @author yanhuang
 *
 */

import com.iss.itreasury.ebank.obdrawnotice.dataentity.*;
import java.sql.*;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.ebank.util.*;
import com.iss.itreasury.util.*;
public class OBDrawNoticeDao {

	private static Log4j log4j = null;

	public OBDrawNoticeDao() {
		log4j = new Log4j(Constant.ModuleType.EBANK, this);
	}
	public DrawNoticeInfo findDrawNoticeByID(long lLoanDrawNoticeID)
		throws Exception {
		DrawNoticeInfo Info = new DrawNoticeInfo();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = Database.getConnection();
			String strSQL =
				" select a.*  "
					+ "  ,b.sName as InputUserName "
					+ " from ob_yt_drawform a, ob_User b "
					+ "  where  a.nInputUserID=b.ID(+) "
					+ "     and a.ID = "
					+ lLoanDrawNoticeID
					+ " ";
			log4j.info("SQL :" + strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			//
			if (rs != null && rs.next()) {
				Info.setID(rs.getLong("ID"));
				Info.setContractID(rs.getLong("nContractID"));
				Info.setCode(rs.getString("SINSTRUCTIONNO"));
				Info.setDrawAmount(rs.getDouble("mAmount"));
				Info.setInputUserID(rs.getLong("nInputUserID"));
				Info.setInputDate(rs.getTimestamp("dtInput"));
				Info.setStatusID(rs.getLong("nStatusID"));
				Info.setInputUserName(rs.getString("InputUserName"));
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
		} catch (SQLException e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
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
			} catch (SQLException e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return Info;
	}
	public long saveDrawNotice(DrawNoticeInfo info) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lMaxID = -1; //流水号
		long ID = info.getID();
		String strCode = null;
		String strSQL=null;
		long lResult = -1;
		
		try {
			con = Database.getConnection();
			if (info.getID() <= 0) //新增
				{
				Log.print("--new--");
				strCode = OBOperation.createInstrCode(OBConstant.SubModuleType.LOAN);
				//首先获得新id
				strSQL = "select nvl(max(ID)+1,1) ID from ob_yt_drawform ";
				ps = con.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs.next()) {
					lMaxID = rs.getLong("ID");
				}
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				///////////////////////新增//////////////////////////
				if (lMaxID > 0) {
					strSQL =
						"insert into ob_yt_drawform ( "
							+ " ID, nContractID,SINSTRUCTIONNO "
	+" , mAmount, nInputUserID "
		+ " , dtInput, nStatusID "
		+ " "
		+ " ) "
		+ " values (?, ?, ?, ?, ?,  SYSDATE, ?)";
		
					log4j.info(strSQL);
					log4j.info("lMaxID=" + lMaxID);
					log4j.info("strCode=" + strCode);
					ps = con.prepareStatement(strSQL);
					int nIndex = 1;
					ps.setLong(nIndex, lMaxID);
					nIndex++;

					ps.setLong(nIndex, info.getContractID());
					nIndex++;

					ps.setString(nIndex, strCode);
					nIndex++;
					ps.setDouble(nIndex, info.getDrawAmount());
					nIndex++;
					ps.setLong(nIndex, info.getInputUserID());
					nIndex++;
					ps.setLong(nIndex, OBConstant.LoanInstrStatus.SUBMIT);
					//TODO
					lResult = ps.executeUpdate();
					
					if (ps != null) {
						ps.close();
						ps = null;
					}
					if (lResult < 0) {
						log4j.info("insert into failed：" + lResult);
						lResult = -1;
					} else {
						lResult = lMaxID;
	
					}
				} //end lMaxID>0  可以新增
			}

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
		log4j.info("Insert into ob_yt_drawform Success!!!! And return :"+lResult);
		return lResult;
	}
	public long updateDrawNotice(DrawNoticeInfo info) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		long lResult = -1;
		int nIndex = 1;
		String strSQL = null;
		try {
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();

			strSQL = " Update ob_yt_drawform  " + " set mAmount = ? "
				//+ " ,nInputUserID = ? "
		+ "   , dtInput = SYSDATE "
		+ "   , nStatusID = ? "
		+ " where ID = "
		+ info.getID()
		+ " ";
			log4j.info(strSQL);
			ps = con.prepareStatement(strSQL);
			nIndex = 1;
			ps.setDouble(nIndex, info.getDrawAmount());
			nIndex++;
			//ps.setLong(nIndex, ldninfo.getInputUserID());
			//nIndex++;
			//ps.setLong(nIndex, ldninfo.getInputUserID());
			//nIndex++;
			//ps.setTimestamp(nIndex, ldninfo.getInputDate());
			//nIndex++;
			ps.setLong(nIndex, OBConstant.LoanInstrStatus.SUBMIT);
			lResult = ps.executeUpdate();
			if (lResult < 0) {
				//修改失败，返回值 -1
				lResult = -1;
			} else {
				lResult = info.getID();
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
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
	public long updateStatus(long lDrawNoticeID, long lStatus)
		throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		long lResult = -1;
		int nIndex = 1;
		long lUpdateStatus = -1;
		lUpdateStatus = lStatus;

		try {
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("UPDATE ob_yt_drawform SET NSTATUSID = ? ");
			sb.append(" where ID = ?");
			//为了协调网银和一期在“执行日”上的一致性增加下面一段代码
			//用途：获取开关机状态和开机时间

			log4j.info(sb.toString());
			ps = con.prepareStatement(sb.toString());
			nIndex = 1;
			ps.setLong(nIndex++, lUpdateStatus);
			ps.setLong(nIndex++, lDrawNoticeID);
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
