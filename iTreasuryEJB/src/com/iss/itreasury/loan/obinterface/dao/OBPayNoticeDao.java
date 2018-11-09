package com.iss.itreasury.loan.obinterface.dao;

/**
 * @author yanhuang
 *
 */
import com.iss.itreasury.loan.obinterface.dataentity.*;
import java.sql.*;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
public class OBPayNoticeDao {

	private static Log4j log4j = null;

	public OBPayNoticeDao() throws Exception{
		log4j = new Log4j(Constant.ModuleType.EBANK, this);
	}
	public OBPayNoticeInfo findByID(long lPayID)throws Exception
	{
		OBPayNoticeInfo info = new OBPayNoticeInfo();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		//LoanPayNoticeDao dao = new LoanPayNoticeDao() ;
	
		try {
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append(" select a.*,b.sname as sInputName,c.sname as accname,c.sCode as acccodec,d.sname as sGrantName ,d.saccountno as sGrantCurrentAccount");
			sb.append(" from OB_DUEBILL a,ob_user b,sett_Branch c,sett_account d");
	
			sb.append("  where a.ninputuserid = b.id(+) ");
			sb.append(" and a.nAccountBankID = c.id(+) ");
			sb.append("  and a.Ngrantcurrentaccountid = d.id(+) ");
			sb.append("  and a.id = ? ");
	
			ps = con.prepareStatement(sb.toString());
			ps.setLong(1, lPayID);
			System.out.println(sb.toString());
			rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				info.setID(rs.getLong("ID"));
				info.setCode(rs.getString("SINSTRUCTIONNO"));
				info.setContractID(rs.getLong("NCONTRACTID"));
				info.setOutDate(rs.getTimestamp("DTOUTDATE"));
				info.setAmount(rs.getDouble("MAMOUNT"));
				info.setConsignAccount(rs.getString("SCONSIGNACCOUNT"));
				info.setBankInterestID(rs.getLong("NBANKINTERESTID"));
				info.setCommissionRate(rs.getDouble("MCOMMISSIONRATE"));
				info.setSuretyFeeRate(rs.getDouble("MSURETYFEERATE"));
				info.setStart(rs.getTimestamp("DTSTART"));
				info.setEnd(rs.getTimestamp("DTEND"));
				info.setReceiveClientName(rs.getString("SRECEIVECLIENTNAME"));
				info.setReceiveAccount(rs.getString("SRECEIVEACCOUNT"));
				info.setRemitBank(rs.getString("SREMITBANK"));
				info.setCompanyLeader(rs.getString("SCOMPANYLEADER"));
				info.setHandlingPerson(rs.getString("SHANDLINGPERSON"));
				info.setDepartmentLeader(rs.getString("SDEPARTMENTLEADER"));
				info.setStatusID(rs.getLong("NSTATUSID"));
				info.setInputUserID(rs.getLong("NINPUTUSERID"));
				info.setInputUserName(rs.getString("sInputName"));
				info.setInputDate(rs.getTimestamp("DTINPUTDATE"));
				info.setGrantCurrentAccountID(
					rs.getLong("NGRANTCURRENTACCOUNTID"));
				info.setGrantCurrentAccount(rs.getString("sGrantCurrentAccount"));
				info.setGrantCurrentName(rs.getString("sGrantName"));
					
				info.setGrantTypeID(rs.getLong("NGRANTTYPEID"));
				info.setRemitinProvince(rs.getString("SREMITINPROVINCE"));
				info.setRemitinCity(rs.getString("SREMITINCITY"));
				info.setDrawNoticeID(rs.getLong("NDRAWNOTICEID"));
				info.setWTInterestRate( rs.getDouble( "MINTERESTRATE" ) ) ;//基准利率
				info.setLoanAccount(rs.getString("SLOANACCOUNT"));
				info.setAccountBankID(rs.getLong("NACCOUNTBANKID"));
				info.setAccountBankCode(rs.getString("acccodec"));
				info.setAccountBankName(rs.getString("accname"));
			}
	
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			sb.setLength(0);
	
			con.close();
			con = null;
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new Exception("remote exception : " + e.toString());
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
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return info;
	}
	public long updateOB(OBBackInfo info)throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		long lResult = -1;
		int nIndex = 1;

		try {
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append("UPDATE OB_DUEBILL SET  ");
			
			if (info.getInID() > 0) {
				sbSQL.append(" ninid=?, ");
			}

			if (info.getStatusID() !=0) {
				sbSQL.append(" NSTATUSID=?, ");
			}
			if (info.getUserID() > 0) {
				sbSQL.append(" NHANDLEUSERID=?, ");
			}
			sbSQL.append(" ID=ID ");
			sbSQL.append("where ID=?");
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
	public long updateOBStatus(long lInID, long lStatus)throws Exception
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
			sb.append("UPDATE OB_DUEBILL SET NSTATUSID = ? ");
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
