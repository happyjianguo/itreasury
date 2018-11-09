package com.iss.itreasury.ebank.obfreeapply.dao;

/**
 * @author yanhuang
 *
 * 免还贷款
 */
import com.iss.itreasury.ebank.obfreeapply.dataentity.*;
import java.sql.*;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.ebank.util.*;

public class OBFreeApplyDao {

	private static Log4j log4j = null;

	public OBFreeApplyDao() {
		log4j = new Log4j(Constant.ModuleType.EBANK, this);
	}

	public FreeApplyInfo findFreeApplyByID(long lFreeApplyID)
		throws Exception {
		FreeApplyInfo info = new FreeApplyInfo();
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

			/*
			sbSQL.append(" SELECT * ");
			sbSQL.append(" FROM ob_free free ");
			sbSQL.append(" WHERE id=?");

			log4j.info("ob_free id=" + lFreeApplyID);
			ps = con.prepareStatement(sbSQL.toString());
			*/
			
			ps = con.prepareStatement(strSQL);
			
			ps.setLong(1, lFreeApplyID);
			log4j.info("ObFreeApplyEJB.findByID()\n");
			
			rs = ps.executeQuery();
			//
			log4j.info("findFreeApplyByID():success\n");
			if (rs.next()) {
								
				info.setID(rs.getLong("ID"));
				//log4j.info("set Free Data Entity:successs\n");
				info.setFreeCode(rs.getString("SINSTRUCTIONNO"));
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

	public long saveFreeApply(FreeApplyInfo info) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String lMaxID = ""; //流水号
		boolean bLocked = true;
		long ID = info.getID();
		//此交易中的付款方账号，（上存资金调回中收款方账号）
		//进行交易的登录单位的账号
		long lAccountID = -1;
		try {
			con = Database.getConnection();
			if (ID < 0) {
				String getID = "select nvl(max(ID)+1,1) ID from OB_Free";
				ps = con.prepareStatement(getID);
				rs = ps.executeQuery();
				if (rs.next()) {
					ID = rs.getLong(1);
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;

				//存储记录
			}
			StringBuffer sb = new StringBuffer();
			// get max(id)+1 as PK
			log4j.info("get max(id)+1 as PK");
			//调用BizCapital方法，得到最大流水号
			lMaxID = OBOperation.createInstrCode(OBConstant.SubModuleType.LOAN);
			sb.setLength(0);

			// insert into  拼写插入语句
			log4j.info("财务指令插入语句= \n");
			sb.append(" INSERT INTO OB_Free(  \n");
			sb.append("ID, SINSTRUCTIONNO, NCONTRACTID,MFREEAMOUNT,");
			sb.append("DTFREEDATE,SFREEREASON,NSTATUSID, ");
			sb.append(" SACCOUNTNO, \n");
			sb.append("NINPUTUSERID, DTINPUTDATE, MINTEREST, NPAYFORMID ) ");
			sb.append("VALUES \n");
			sb.append("(?,?,?,?,?,?,?,?,?,sysdate,?,?)");
			log4j.info("add插入语句=" + sb.toString());
			
			ps = con.prepareStatement(sb.toString());
			int nIndex = 1;
			//////// 第1行 8个字段

			log4j.info("lMaxID=" + lMaxID);
			ps.setLong(nIndex++, ID); // ID
			ps.setString(nIndex++, lMaxID); //流水号
			ps.setLong(nIndex++, info.getContractID()); // 合同号
			ps.setDouble(nIndex++, info.getFreeAmount()); // 免还金额
			ps.setTimestamp(nIndex++, info.getFreeDate()); //免还日期
			ps.setString(nIndex++, info.getFreeReason()); // 免还原因
			ps.setLong(nIndex++, OBConstant.LoanInstrStatus.SAVE); // 状态
			ps.setString(nIndex++, info.getConsignClientAccount()); //委托单位账号

			///////// 第2行  4字段
			ps.setLong(nIndex++, info.getInputUserID()); //输入员
			ps.setDouble(nIndex++, info.getFreeRate()); // 免还利率
			ps.setLong(nIndex++, info.getLoanPayID()); // 放款通知单ID

	
			ps.executeUpdate();

			ps.close();
			ps = null;
			//对数据进行解锁，此操作必不可少

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
		log4j.info("ID= :::::::::::::::::::::::::::::::::::::::" + ID);
		return ID;
	}

	public long updateFreeApply(FreeApplyInfo info) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		long lResult = -1;
		int nIndex = 1;

		try {
			//System.out.println("come in update method!~@@@@@@@@@@@@@@");
			con = Database.getConnection();
			
			String strSQL =
				" Update OB_FREE  "
					+ " set mFreeAmount = ? "
					+ "   , dtFreeDate = ? "
					+ "   , mInterest = ? "
					+ "   , sFreeReason = ? "
					+ "   , sAccountNo = ? "    
					+ " where ID = " +info.getID()
					+ " ";
			///////////////////////////////////////////////////////////
			System.out.println(strSQL);
			ps = con.prepareStatement(strSQL);
			///////////////////////////////////////////////////////////
		 nIndex = 1;
		 ps.setDouble(nIndex, info.getFreeAmount());
		 nIndex++;
		 ps.setTimestamp(nIndex, info.getFreeDate());
		 nIndex++;
		 ps.setDouble(nIndex, info.getFreeRate());
		 nIndex++;
		 ps.setString(nIndex, info.getFreeReason());
		 nIndex++;
		 ps.setString(nIndex, info.getConsignClientAccount());
		 //nIndex++;
		 lResult = ps.executeUpdate();
			System.out.println("execute SQL!~@@@@@@@@@@@@@@");
			if (lResult <= 0)
				{
					log4j.info("修改失败，返回值：" + lResult);
					lResult = -1;
				}
				else
				{
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

	public long updateStatus(long lFreeApplyID, long lStatus)
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
			sb.append("UPDATE OB_Free SET NSTATUSID = ? ");
			sb.append(" where ID = ?");
			//为了协调网银和一期在“执行日”上的一致性增加下面一段代码
			//用途：获取开关机状态和开机时间

			log4j.info(sb.toString());
			ps = con.prepareStatement(sb.toString());
			nIndex = 1;
			ps.setLong(nIndex++, lUpdateStatus);
			ps.setLong(nIndex++, lFreeApplyID);	
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
