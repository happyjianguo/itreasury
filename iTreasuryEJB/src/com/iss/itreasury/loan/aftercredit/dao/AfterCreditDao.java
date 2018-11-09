package com.iss.itreasury.loan.aftercredit.dao;

import java.util.*;
import java.rmi.RemoteException;
import java.sql.*;

import com.iss.itreasury.dao.SettlementDAO;

import com.iss.itreasury.loan.aftercredit.dataentity.AfterCreditAccountInfo;
import com.iss.itreasury.loan.aftercredit.dataentity.AfterCreditReportInfo;
import com.iss.itreasury.loan.aftercredit.dataentity.GageAnalyseInfo;
import com.iss.itreasury.loan.aftercredit.dataentity.SearchForReportInfo;
import com.iss.itreasury.loan.aftercredit.dataentity.AfterCreditFiveSortInfo;
import com.iss.itreasury.loan.aftercredit.dataentity.FiveSortDetailInfo;
import com.iss.itreasury.loan.checkfrequency.dataentity.ContractFrequencyInfo;
import com.iss.itreasury.loan.contract.dao.ContractDao;

import com.iss.itreasury.loan.aftercredit.dataentity.Sortresolution_Explain_Info;
import com.iss.itreasury.loan.assureresmanage.dataentity.LOANGageInforMationSearchInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.approval.dataentity.ApprovalSettingInfo;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.system.approval.dataentity.InutApprovalWorkInfo;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.system.bizdelegation.ApprovalDelegation;
import com.iss.itreasury.system.util.SYSConstant;
import com.iss.itreasury.util.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.sql.Connection;

import javax.ejb.SessionContext;

import com.iss.itreasury.ebank.util.OBConstant;

import com.iss.itreasury.loan.mywork.dataentity.AfterCreditWorkInfo;
import com.iss.itreasury.loan.mywork.dataentity.LoanMyWorkInfo;
import com.iss.itreasury.loan.obinterface.dao.OBLoanDao;
import com.iss.itreasury.loan.risklevel.dataentity.RiskLevelInfo;

import com.iss.itreasury.loan.util.LOANConstant;

import com.iss.itreasury.util.Database;

import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.SessionMng;
import com.iss.itreasury.util.Constant.ApprovalAction;
import com.iss.system.dao.PageLoader;

public class AfterCreditDao extends SettlementDAO {

	private static Log4j log4j = null;

	private SessionContext context;
	protected StringBuffer m_sbSelect = null;
	protected StringBuffer m_sbFrom = null;

	protected StringBuffer m_sbWhere = null;

	protected StringBuffer m_sbOrderBy = null;

	public AfterCreditDao() {
		super("loan_loanaftercheckreport");
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
	}

	public AfterCreditDao(AfterCreditFiveSortInfo info) {
		super("loan_loanafterfivesort");
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
	}

	public AfterCreditDao(AfterCreditReportInfo info) {
		super("loan_loanaftercheckreport");
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
	}

	/**
	 * 根据检察频率查询合同信息
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public List queryInfoList(long checkfrequency, long borrowclientid,
			String checkyear, String checkqm, String checktemp)
			throws Exception {
		List list = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL
					.append(" SELECT c.sname as csname,ct.id as lid,ct.Nloanid as nloanid,ct.Scontractcode as scontractcode,");
			sbSQL
					.append(" ct.Mloanamount as mloanamount,ct.Nrisklevel as nrisklevel,ct.Dtenddate as dtenddate, ct.Nborrowclientid as nborrowclientid, ");
			sbSQL
					.append(" ct.Dtstartdate as dtstartdate,ct.Checkfrequency as checkfrequency ,e.checkreportcode as  checkreportcode,e.id as reportid");
			sbSQL.append(" FROM loan_contractform  ct ,client c , ");
			sbSQL
					.append(" (select * from loan_loanaftercheckreport d where 1=1 ");

			sbSQL.append(" and  d.checktype = ? ");
			if (!checkyear.equals("")) {
				sbSQL.append(" and  d.checkyear = ? ");
			}
			if (!checkqm.equals("")) {
				sbSQL.append(" and  d.checkqm = ? ");
			}
			if (!checktemp.equals("")) {
				sbSQL.append(" and  d.checktemp =? ");
			}
			sbSQL.append(" and  d.status not in (0,3,6))  e ");
			sbSQL.append("  Where  ct.nstatusid >= "
					+ LOANConstant.ContractStatus.ACTIVE);
			sbSQL.append("  and ct.nstatusid <= "
					+ LOANConstant.ContractStatus.BADDEBT);
			sbSQL.append("  and ct.ntypeid in(1,2,3,4,8,80,200) ");
			sbSQL.append(" and ct.Nborrowclientid=c.ID ");
			sbSQL.append(" and  checkfrequency=? ");
			sbSQL.append(" and  ct.id =e.loancontractid(+) ");

			if (borrowclientid > 0) {
				sbSQL.append(" and  nborrowclientid=? ");
			}

			System.out
					.println("-----------------------------------------strSql==="
							+ sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			int n = 1;
			ps.setLong(n++, checkfrequency);
			if (!checkyear.equals("")) {
				ps.setString(n++, checkyear);
			}
			if (!checkqm.equals("")) {
				ps.setLong(n++, Long.parseLong(checkqm));
			}
			if (!checktemp.equals("")) {
				ps.setTimestamp(n++, DataFormat.getDateTime(checktemp));
			}

			ps.setLong(n++, checkfrequency);
			if (borrowclientid > 0) {
				ps.setLong(n++, borrowclientid);
			}
			rs = ps.executeQuery();

			while (rs.next()) {
				System.out
						.println("------------------------------------------------ID--------------------:::"
								+ rs.getLong("lid"));
				ContractFrequencyInfo contractfrequencyinfo = new ContractFrequencyInfo();
				contractfrequencyinfo.setId(rs.getLong("lid"));
				contractfrequencyinfo.setNborrowclientid(rs
						.getLong("nborrowclientid"));
				contractfrequencyinfo.setNloanid(rs.getLong("nloanid"));
				contractfrequencyinfo.setSname(rs.getString("csname"));
				contractfrequencyinfo.setScontractcode(rs
						.getString("scontractcode"));
				contractfrequencyinfo.setMloanamount(rs
						.getDouble("mloanamount"));
				contractfrequencyinfo.setNrisklevel(rs.getLong("nrisklevel"));
				contractfrequencyinfo
						.setDtenddate(rs.getTimestamp("dtenddate"));
				contractfrequencyinfo.setDtstartdate(rs
						.getTimestamp("dtstartdate"));
				contractfrequencyinfo.setCheckfrequency(rs
						.getLong("checkfrequency"));
				contractfrequencyinfo.setCheckreportcode(rs
						.getString("checkreportcode"));
				contractfrequencyinfo.setCheckreportid(rs.getLong("reportid"));
				list.add(contractfrequencyinfo);
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
			log4j.error(e.toString());
			// try {
			// throw new IException("Gen_E001");
			// } catch (IException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
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
				// throw new IException("Gen_E001");
			}
		}

		return list;

	}

	/**
	 * 根据检察频率查询合同信息
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public List queryInfoList(long checkfrequency, long borrowclientid,
			String checkyear, String checkqm, String checktemp,
			long inputUserID, long nCurrencyID, long nOfficeID)
			throws Exception {
		List list = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL
					.append(" SELECT c.sname as csname,type.name as nsubTypeName,ct.id as lid,ct.Nloanid as nloanid,ct.Scontractcode as scontractcode,");
			sbSQL
					.append(" ct.NSUBTYPEID as nsubtypeid,ct.Mloanamount as mloanamount,ct.Nrisklevel as nrisklevel,ct.Dtenddate as dtenddate, ct.Nborrowclientid as nborrowclientid, ");
			sbSQL
					.append(" ct.Dtstartdate as dtstartdate,ct.Checkfrequency as checkfrequency ,e.checkreportcode as  checkreportcode,e.id as reportid");
			sbSQL
					.append(" FROM loan_contractform  ct ,client c ,loan_loantypesetting type, ");
			sbSQL
					.append(" (select * from loan_loanaftercheckreport d where 1=1 ");

			sbSQL.append(" and  d.checktype = ? ");
			if (!checkyear.equals("")) {
				sbSQL.append(" and  d.checkyear = ? ");
			}
			if (!checkqm.equals("")) {
				sbSQL.append(" and  d.checkqm = ? ");
			}
			if (!checktemp.equals("")) {
				sbSQL.append(" and  d.checktemp =? ");
			}
			sbSQL.append(" and  d.status not in (0,6,-1))  e ");
			sbSQL.append("  Where  ct.nstatusid >= "
					+ LOANConstant.ContractStatus.ACTIVE);
			sbSQL.append("  and ct.nstatusid <= "
					+ LOANConstant.ContractStatus.BADDEBT);
			sbSQL.append("  and ct.ntypeid in(1,2,3,4,8,80,200) ");
			sbSQL.append(" and ct.Nborrowclientid=c.ID ");
			sbSQL.append(" and  checkfrequency=? ");
			sbSQL.append(" and  ct.id =e.loancontractid(+) ");

			if (borrowclientid > 0) {
				sbSQL.append(" and  nborrowclientid=? ");
			}

			sbSQL
					.append(" and ct.ninputuserid = ? and ct.NOFFICEID=? and ct.NCURRENCYID=? and ct.nsubtypeid=type.id "); // DEBUG
																															// 10291
																															// by
																															// lcliu
			// @ 08-12-18

			System.out
					.println("-----------------------------------------strSql==="
							+ sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			int n = 1;
			ps.setLong(n++, checkfrequency);
			if (!checkyear.equals("")) {
				ps.setString(n++, checkyear);
			}
			if (!checkqm.equals("")) {
				ps.setLong(n++, Long.parseLong(checkqm));
			}
			if (!checktemp.equals("")) {
				ps.setTimestamp(n++, DataFormat.getDateTime(checktemp));
			}

			ps.setLong(n++, checkfrequency);
			if (borrowclientid > 0) {
				ps.setLong(n++, borrowclientid);
			}

			ps.setLong(n++, inputUserID); // DEBUG 10291 by lcliu @ 08-12-18
			ps.setLong(n++, nOfficeID);// add by fulihe
			ps.setLong(n++, nCurrencyID);// add by fulihe
			rs = ps.executeQuery();

			while (rs.next()) {

				ContractFrequencyInfo contractfrequencyinfo = new ContractFrequencyInfo();
				contractfrequencyinfo.setId(rs.getLong("lid"));
				contractfrequencyinfo.setNborrowclientid(rs
						.getLong("nborrowclientid"));
				contractfrequencyinfo.setNloanid(rs.getLong("nloanid"));
				contractfrequencyinfo.setSname(rs.getString("csname"));
				contractfrequencyinfo.setScontractcode(rs
						.getString("scontractcode"));
				contractfrequencyinfo.setMloanamount(rs
						.getDouble("mloanamount"));
				contractfrequencyinfo.setNrisklevel(rs.getLong("nrisklevel"));
				contractfrequencyinfo
						.setDtenddate(rs.getTimestamp("dtenddate"));
				contractfrequencyinfo.setDtstartdate(rs
						.getTimestamp("dtstartdate"));
				contractfrequencyinfo.setCheckfrequency(rs
						.getLong("checkfrequency"));
				contractfrequencyinfo.setCheckreportcode(rs
						.getString("checkreportcode"));
				contractfrequencyinfo.setCheckreportid(rs.getLong("reportid"));
				contractfrequencyinfo.setNsubtypeid(rs.getLong("nsubtypeid"));
				contractfrequencyinfo.setSubTypeName(rs
						.getString("nsubTypeName"));
				list.add(contractfrequencyinfo);
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
			log4j.error(e.toString());
			// try {
			// throw new IException("Gen_E001");
			// } catch (IException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
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
				// throw new IException("Gen_E001");
			}
		}

		return list;

	}

	/**
	 * 提交审批流方法
	 * 
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long submitApplyAndApprovalInit(AfterCreditReportInfo info)
			throws RemoteException, IRollbackException {

		long lReturn = -1;
		try {
			InutParameterInfo pInfo = info.getInutParameterInfo();
			// 提交审批
			lReturn = FSWorkflowManager.initApproval(pInfo);
			if (lReturn > 0) {
				// 更新状态到审批中
				this.updateAfterCheckStatus(info.getId(),
						LOANConstant.AfterCreditStatus.APPROVALING);
			}
		} catch (Exception e) {
			throw new IRollbackException(context, e.getMessage(), e);
		}
		return info.getId();

	}

	/**
	 * 提交审批成功后更新状态为审批中
	 * 
	 * @param lID
	 *            loan_loanaftercheckreport表主键
	 * @param lCheckStatus
	 *            更新状态为审批中
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long updateAfterCheckStatus(long lID, long lCheckStatus)
			throws RemoteException, IRollbackException {
		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		int lReturn = 0;
		try {
			conn = Database.getConnection();
			strSQL = " Update loan_loanaftercheckreport Set STATUS=?  Where ID=?";
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, lCheckStatus);
			ps.setLong(2, lID);
			lReturn = ps.executeUpdate();
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(context, e.getMessage(), e);
		} finally {
			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception ex) {
				throw new IRollbackException(context, ex.getMessage(), ex);
			}
		}

		return new Long(lReturn).longValue();
	}

	/**
	 * 新增贷后检查报告
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long insert(AfterCreditReportInfo info) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;

		long ID = this.getMaxID();

		try {
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			log.info("");
			strSQL = "insert into loan_loanaftercheckreport ("
					+ "id,checkreportcode,clientid,loancontractid,checkmode,checkyear,checkqm,checktemp,checktype,changecircs,"
					+ "creditnote,propertyrightclass,personnelclass,investclass,lawclass,otherclass,advice,conclusion,"
					+ "status,inputuserid,inputdate,officeid,currencyid,nextcheckuserid,nextchecklevel,islowlevel,department,effectdate"
					+ ") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,?)";
			ps = conn.prepareStatement(strSQL);
			int n = 1;
			ps.setLong(n++, ID);
			ps.setString(n++, info.getCheckreportcode());
			ps.setLong(n++, info.getClientid());
			ps.setLong(n++, info.getLoancontractid());
			ps.setLong(n++, info.getCheckmode());
			ps.setString(n++, info.getCheckyear());
			ps.setLong(n++, info.getCheckqm());
			ps.setTimestamp(n++, info.getChecktemp());
			ps.setLong(n++, info.getChecktype());
			ps.setString(n++, info.getChangecircs());
			ps.setString(n++, info.getCreditnote());
			ps.setString(n++, info.getPropertyrightclass());
			ps.setString(n++, info.getPersonnelclass());
			ps.setString(n++, info.getInvestclass());
			ps.setString(n++, info.getLawclass());
			ps.setString(n++, info.getOtherclass());
			ps.setLong(n++, info.getAdvice());
			ps.setString(n++, info.getConclusion());
			ps.setLong(n++, info.getStatus());
			ps.setLong(n++, info.getInputuserid());
			ps.setTimestamp(n++, info.getInputdate());
			ps.setLong(n++, info.getOfficeid());
			ps.setLong(n++, info.getCurrencyid());
			ps.setLong(n++, info.getNextcheckuserid());
			ps.setLong(n++, info.getNextchecklevel());
			ps.setLong(n++, info.getIslowlevel());
			ps.setString(n++, info.getDepartment());
			ps.setTimestamp(n++, info.getEffectDate());
			// ///////
			/*
			 * //ps.setLong(n++, info.getChecktype()); //ps.setString(n++,
			 * info.getCheckyear()); //ps.setLong(n++, info.getCheckqm());
			 * //if(info.getChecktemp() != null) {
			 * System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@" +
			 * info.getChecktemp()); ps.setTimestamp(n++, info.getChecktemp()); }
			 */
			lResult = ps.executeUpdate();
			System.out
					.println("************************lll*********************"
							+ lResult);
			if (lResult == -1) {
				ID = -1;
				conn.rollback();
			} else {
				if (info.getCheckreportcode().equals("")) {
					ID = -1;
					conn.rollback();
				} else {
//					strSQL = "UPDATE loan_contractform SET ncreditcheckreportid = "
//							+ ID
//							+ ",NRISKLEVEL="
//							+ info.getAdvice()
//							+ " WHERE nloanid = "
//							+ info.getLoancontractid()
//							+ "";
//					System.out.println("the contractid is "
//							+ info.getLoancontractid());
//					log4j.info(strSQL);
//
//					ps = conn.prepareStatement(strSQL);
//
//					lResult = ps.executeUpdate();
					if (!info.getIds().equals("")) {
						strSQL = "update loan_aftercreditfile set REPORTID="
								+ ID + " where id in (" + info.getIds() + ")";
						log4j.info(strSQL);

						ps = conn.prepareStatement(strSQL);

						lResult = ps.executeUpdate();
					}

					if (lResult == -1) {
						ID = -1;
						conn.rollback();
					} else {
						conn.commit();
					}
				}
			}

			cleanup(rs);
			cleanup(ps);
			cleanup(conn);

			if (lResult < 0) {
				log.info("insert  fail");
				return -1;
			} else {
				return ID;
			}
		} catch (Exception e) {
			if (conn != null) {
				conn.rollback();
			}
			e.printStackTrace();
			return -1;
		} finally {
			if (rs != null) {
				cleanup(rs);
			}
			if (ps != null) {
				cleanup(ps);
			}
			if (conn != null) {
				cleanup(conn);
			}
		}

	}

	/**
	 * 更新贷后检查报告
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long updateReport(AfterCreditReportInfo info) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;

		// long ID = this.getMaxID();
		long ID = info.getId();
		log.info("");
		try {
			conn = Database.getConnection();
			conn.setAutoCommit(false);

			log.info("");
			strSQL = "update  loan_loanaftercheckreport set "
					+ "clientid=?,loancontractid=?,checkmode=?,changecircs=?,"// checkyear=?,checkqm=?,checktemp=?,checktype=?,
					+ "creditnote=?,propertyrightclass=?,personnelclass=?,investclass=?,lawclass=?,otherclass=?,advice=?,conclusion=?,"
					+ "status=?,inputuserid=?,inputdate=?,officeid=?,currencyid=?,nextcheckuserid=?,nextchecklevel=?,islowlevel=?,department=?,effectdate=? where id=?";

			ps = conn.prepareStatement(strSQL);
			int n = 1;

			// ps.setString(n++, info.getCheckreportcode());
			ps.setLong(n++, info.getClientid());
			ps.setLong(n++, info.getLoancontractid());
			ps.setLong(n++, info.getCheckmode());
			// ps.setString(n++, info.getCheckyear());
			// ps.setLong(n++, info.getCheckqm());
			// ps.setTimestamp(n++, info.getChecktemp());
			// ps.setLong(n++, info.getChecktype());
			ps.setString(n++, info.getChangecircs());
			ps.setString(n++, info.getCreditnote());
			ps.setString(n++, info.getPropertyrightclass());
			ps.setString(n++, info.getPersonnelclass());
			ps.setString(n++, info.getInvestclass());
			ps.setString(n++, info.getLawclass());
			ps.setString(n++, info.getOtherclass());
			ps.setLong(n++, info.getAdvice());
			ps.setString(n++, info.getConclusion());
			ps.setLong(n++, info.getStatus());
			ps.setLong(n++, info.getInputuserid());
			ps.setTimestamp(n++, info.getInputdate());
			ps.setLong(n++, info.getOfficeid());
			ps.setLong(n++, info.getCurrencyid());
			ps.setLong(n++, info.getNextcheckuserid());
			ps.setLong(n++, info.getNextchecklevel());
			ps.setLong(n++, info.getIslowlevel());
			ps.setString(n++, info.getDepartment());
			ps.setTimestamp(n++, info.getEffectDate());
			ps.setLong(n++, ID);

			lResult = ps.executeUpdate();
			ps.close();
			ps = null;
			/*
			 * DEBUG 8518 by lcliu @ 08-11-6 cleanup(rs); cleanup(ps);
			 * cleanup(conn);
			 */
			if (!info.getIds().equals("")) {
				strSQL = "update loan_aftercreditfile set reportid=" + ID
						+ " where id in (" + info.getIds() + ")";
				ps = conn.prepareStatement(strSQL);
				lResult = ps.executeUpdate();
			}
			if (lResult == -1) {
				ID = -1;
				conn.rollback();
			} else {
				conn.commit();
			}
			return ID;
		} catch (Exception e) {

			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 获取最大ID
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long getMaxID() throws Exception {
		long lMaxID = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String strSQL = "select nvl(max(id)+1,1) from loan_loanaftercheckreport";
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				lMaxID = rs.getLong(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(con);

		}
		return lMaxID;
	}

	/**
	 * 担保品分析最大ID
	 * 
	 * @return
	 * @throws Exception
	 */
	public long getMaxGageID() throws Exception {
		long lMaxID = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String strSQL = "select nvl(max(id)+1,1) from loan_loanaftergageanalyse";
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				lMaxID = rs.getLong(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(con);

		}
		return lMaxID;
	}

	public long getMaxID(String table) throws Exception {
		long lMaxID = -1;
		Connection con = Database.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String strSQL = "select nvl(max(id)+1,1) from " + table;
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				lMaxID = rs.getLong(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
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
		return lMaxID;
	}

	/**
	 * 返回同一天，最大的流水号(3位)//////////////////////////////没完成
	 * 
	 * 
	 * @return
	 * @throws
	 */
	public long getMaxSerial(String sCode) throws Exception {
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		long serial = -1;
		try {
			String hql = "select max(to_number(substr(a.checkreportcode, length(a.checkreportcode)-2, length(a.checkreportcode)))) as a "
					+ " from loan_loanaftercheckreport a where a.checkreportcode like '"
					+ sCode + "%'";
			System.out.println("the sql is !@#$%^&*" + hql);
			ps = con.prepareStatement(hql);
			rs = ps.executeQuery();
			if (rs.next()) {
				serial = rs.getLong(1);
			} else {
				serial = 0;
			}
		} catch (Exception e) {
			log4j.error(e.toString());
			// throw new IException("Gen_E001");
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
		return serial;
	}

	/**
	 * 贷后检查报告修改查询/
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public List getReportCreditSearch(SearchForReportInfo searchforreportinfo)
			throws Exception {
		List list = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		String lastCheckUser = null;

		long lModuleID = Constant.ModuleType.LOAN;// 贷款
		long lLoanTypeID = 9;
		long lActionID = Constant.ApprovalAction.DH_1;// 授信
		ApprovalDelegation appbiz = new ApprovalDelegation();

		AfterCreditReportInfo aftercreditreportinfo = null;
		// long ActionID = searchforreportinfo.getActionID();

		try {
			con = Database.getConnection();

			StringBuffer strSQL = new StringBuffer();

			strSQL
					.append("select c.scode as scode ,c.sname as sname,b.scontractcode as scontractcode,a.inputuserid as inputuserid, a.id as ID,a.clientid as clientid,a.loancontractid as loancontractid,a.inputdate as inputdate,a.checkreportcode as checkreportcode,a.inputuserid as inputuserid,a.status as status,b.nrisklevel as nrisklevel,a.checktype as checktype,a.checkyear as checkyear,a.checkqm as checkqm,a.checktemp as checktemp,a.nextchecklevel  as nextchecklevel  from loan_loanaftercheckreport a  ,loan_contractform b ,client c where a.clientid=c.id and a.loancontractid = b.id ");

			/*
			 * if (ActionID == 1) { strSQL.append(" and (a.status = 1 )");
			 *  } else if (ActionID == 3) {
			 * 
			 * strSQL.append(" and (a.status = 1 or a.status = 2 )");
			 *  }
			 */

			// strSQL.append (" and b.nstatusid >=
			// "+LOANConstant.ContractStatus.ACTIVE);
			// strSQL.append (" and b.nstatusid <=
			// "+LOANConstant.ContractStatus.BADDEBT);
			// strSQL.append (" and b.ntypeid in(1,2,3,4,8,80,200) ");
			if (!searchforreportinfo.getClientFrom().equals("")
					&& !searchforreportinfo.getClientTo().equals("")) {
				strSQL.append("  and a.clientid between ? and ? ");
			}
			if (!searchforreportinfo.getCodeFrom().equals("")
					&& !searchforreportinfo.getCodeTo().equals("")) {
				strSQL.append("  and a.loancontractid between ? and ? ");
			}
			if (!searchforreportinfo.getDateFrom().equals("")
					&& !searchforreportinfo.getDateTo().equals("")) {
				strSQL
						.append("  and a.inputdate between to_timestamp(?,'yyyy-mm-dd') and to_timestamp(?,'yyyy-mm-dd') ");
			}
			if (searchforreportinfo.getRiskType() > 0) {
				strSQL.append("  and a.ADVICE = ? ");

			}
			if (!searchforreportinfo.getCreditType().equals("-1")) {
				strSQL.append("  and  b.nsubtypeid = ? ");

			}
			if (searchforreportinfo.getStatus() > 0
					|| searchforreportinfo.getStatus() == -2) {
				strSQL.append("  and a.status = ? ");

			}

			// 贷后检查区间
			if (!searchforreportinfo.getCheckType().equals("")) {
				strSQL.append("  and a.checktype = ? ");
			}
			if (!searchforreportinfo.getCheckYear().equals("")) {
				strSQL.append("  and a.checkyear = ? ");
			}
			if (!searchforreportinfo.getCheckqm().equals("")) {
				strSQL.append("  and a.checkqm = ? ");
			}
			if (!searchforreportinfo.getCheckTemp().equals("")) {
				strSQL
						.append("  and a.checktemp = to_timestamp(?,'yyyy-mm-dd') ");
			}

			// 修改查询 只查询是本人录入的

			strSQL.append("  and a.inputuserid =? and a.status not in (-1,-2) ");

			strSQL.append("  order by checkreportcode desc ");
			System.out
					.println("strSQL.toString()=+++++++++++++++++++++++++++++++="
							+ strSQL.toString());

			// System.out.println("strSQL=" + strSQL);
			ps = con.prepareStatement(strSQL.toString());
			int n = 1;
			if (!searchforreportinfo.getClientFrom().equals("")) {
				ps.setString(n++, searchforreportinfo.getClientFrom());
			}
			if (!searchforreportinfo.getClientTo().equals("")) {

				ps.setString(n++, searchforreportinfo.getClientTo());
			}
			if (!searchforreportinfo.getCodeFrom().equals("")) {
				ps.setString(n++, searchforreportinfo.getCodeFrom());
			}
			if (!searchforreportinfo.getCodeTo().equals("")) {
				ps.setString(n++, searchforreportinfo.getCodeTo());
			}
			if (!searchforreportinfo.getDateFrom().equals("")) {
				ps.setString(n++, searchforreportinfo.getDateFrom());
			}
			if (!searchforreportinfo.getDateTo().equals("")) {
				ps.setString(n++, searchforreportinfo.getDateTo());
			}
			if (searchforreportinfo.getRiskType() > 0) {
				ps.setLong(n++, searchforreportinfo.getRiskType());
			}

			if (!searchforreportinfo.getCreditType().equals("-1")) {
				ps.setString(n++, searchforreportinfo.getCreditType());

			}
			if (searchforreportinfo.getStatus() > 0
					|| searchforreportinfo.getStatus() == -2) {
				ps.setLong(n++, searchforreportinfo.getStatus());

			}
			// 贷后检查区间
			if (!searchforreportinfo.getCheckType().equals("")) {
				ps.setString(n++, searchforreportinfo.getCheckType());
			}
			if (!searchforreportinfo.getCheckYear().equals("")) {
				ps.setString(n++, searchforreportinfo.getCheckYear());
			}
			if (!searchforreportinfo.getCheckqm().equals("")) {
				ps.setString(n++, searchforreportinfo.getCheckqm());
			}
			if (!searchforreportinfo.getCheckTemp().equals("")) {
				ps.setString(n++, searchforreportinfo.getCheckTemp());
			}

			ps.setLong(n++, searchforreportinfo.getInputuserid());

			rs = ps.executeQuery();
			while (rs.next()) {
				aftercreditreportinfo = new AfterCreditReportInfo();
				aftercreditreportinfo.setId(rs.getLong("ID"));
				aftercreditreportinfo.setCheckreportcode(rs
						.getString("checkreportcode"));
				aftercreditreportinfo.setAdvice(rs.getLong("nrisklevel"));
				aftercreditreportinfo.setClientid(rs.getLong("clientid"));
				aftercreditreportinfo
						.setInputdate(rs.getTimestamp("inputdate"));
				aftercreditreportinfo.setInputuserid(rs.getLong("inputuserid"));
				aftercreditreportinfo.setStatus(rs.getLong("status"));
				aftercreditreportinfo.setChecktype(rs.getLong("checktype"));
				aftercreditreportinfo.setCheckyear(rs.getString("checkyear"));
				aftercreditreportinfo.setCheckqm(rs.getLong("checkqm"));
				aftercreditreportinfo
						.setChecktemp(rs.getTimestamp("checktemp"));
				aftercreditreportinfo.setClientcode(rs.getString("scode"));
				aftercreditreportinfo.setClientname(rs.getString("sname"));
				aftercreditreportinfo.setContractcode(rs
						.getString("scontractcode"));
				aftercreditreportinfo.setInputuserid(rs.getLong("inputuserid"));

				// add by dwj 20081125
				aftercreditreportinfo.setNextchecklevel(rs
						.getLong("nextchecklevel"));

				// lastCheckUser = appbiz.getLastCheckPerson(lModuleID,
				// lLoanTypeID, lActionID, 1, 1, aftercreditreportinfo
				// .getId());
				// aftercreditreportinfo.setLastCheckUser(lastCheckUser);
				// end by dwj 20081125

				list.add(aftercreditreportinfo);
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
			log4j.error(e.toString());
			// try {
			// throw new IException("Gen_E001");
			// } catch (IException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
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
				// throw new IException("Gen_E001");
			}
		}

		return list;

	}

	/**
	 * 贷后检查报告审批查询
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public List getReportCreditApproveSearch(
			SearchForReportInfo searchforreportinfo, long userid)
			throws Exception {
		List list = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		String lastCheckUser = null;

		long lModuleID = Constant.ModuleType.LOAN;// 贷款
		long lLoanTypeID = 9;
		long lActionID = Constant.ApprovalAction.DH_1;
		ApprovalDelegation appbiz = new ApprovalDelegation();

		AfterCreditReportInfo aftercreditreportinfo = null;
		try {
			con = Database.getConnection();

			StringBuffer strSQL = new StringBuffer();

			strSQL
					.append("select c.scode as scode ,c.sname as sname,b.scontractcode as scontractcode,a.inputuserid as inputuserid,a.id as ID,a.clientid as clientid,a.loancontractid as loancontractid,a.inputdate as inputdate,a.checkreportcode as checkreportcode,a.inputuserid as inputuserid,a.checkuserid as checkuserid,a.checkdate as checkdate,a.status as status,b.nrisklevel as nrisklevel,a.checktype as checktype,a.checkyear as checkyear,a.checkqm as checkqm,a.checktemp as checktemp,a.nextchecklevel  as nextchecklevel from loan_loanaftercheckreport a  ,loan_contractform b ,client c where a.clientid=c.id and a.loancontractid = b.id and ( a.status ="
							+ LOANConstant.StatusType.UNCHECK
							+ " or a.status="
							+ LOANConstant.StatusType.CHECKED + " )  ");

			if (!searchforreportinfo.getClientFrom().equals("")
					&& !searchforreportinfo.getClientTo().equals("")) {
				strSQL.append("  and a.clientid between ? and ? ");
			}
			if (!searchforreportinfo.getCodeFrom().equals("")
					&& !searchforreportinfo.getCodeTo().equals("")) {
				strSQL.append("  and a.loancontractid between ? and ? ");
			}
			if (!searchforreportinfo.getDateFrom().equals("")
					&& !searchforreportinfo.getDateTo().equals("")) {
				strSQL
						.append("  and a.inputdate between to_timestamp(?,'yyyy-mm-dd') and to_timestamp(?,'yyyy-mm-dd') ");
			}
			if (searchforreportinfo.getRiskType() > 0) {
				strSQL.append("  and b.nrisklevel = ? ");

			}
			// 复核人不能为录入人
			if (userid > 0) {
				strSQL.append(" and a.inputuserid <> ? ");
			}
			// if(!searchforreportinfo.getCreditType().equals("-1"))
			if (longTrim(searchforreportinfo.getCreditType()) > 0) {
				strSQL.append("  and  b.nsubtypeid = ? ");

			}
			if (searchforreportinfo.getStatus() > 0) {
				strSQL.append("  and a.status = ? ");

			}
			// 贷后检查区间
			if (!searchforreportinfo.getCheckType().equals("")) {
				strSQL.append("  and a.checktype = ? ");
			}
			if (!searchforreportinfo.getCheckYear().equals("")) {
				strSQL.append("  and a.checkyear = ? ");
			}
			if (!searchforreportinfo.getCheckqm().equals("")) {
				strSQL.append("  and a.checkqm = ? ");
			}
			if (!searchforreportinfo.getCheckTemp().equals("")) {
				strSQL
						.append("  and a.checktemp = to_timestamp(?,'yyyy-mm-dd') ");
			}
			strSQL.append("  order by checkreportcode desc ");
			System.out
					.println("strSQL.toString()=+++++++++++++++++++++++++++++++="
							+ strSQL.toString());

			// System.out.println("strSQL=" + strSQL);
			ps = con.prepareStatement(strSQL.toString());
			int n = 1;
			if (!searchforreportinfo.getClientFrom().equals("")) {
				ps.setString(n++, searchforreportinfo.getClientFrom());
			}
			if (!searchforreportinfo.getClientTo().equals("")) {

				ps.setString(n++, searchforreportinfo.getClientTo());
			}
			if (!searchforreportinfo.getCodeFrom().equals("")) {
				ps.setString(n++, searchforreportinfo.getCodeFrom());
			}
			if (!searchforreportinfo.getCodeTo().equals("")) {
				ps.setString(n++, searchforreportinfo.getCodeTo());
			}
			if (!searchforreportinfo.getDateFrom().equals("")) {
				ps.setString(n++, searchforreportinfo.getDateFrom());
			}
			if (!searchforreportinfo.getDateTo().equals("")) {
				ps.setString(n++, searchforreportinfo.getDateTo());
			}
			if (searchforreportinfo.getRiskType() > 0) {
				ps.setLong(n++, searchforreportinfo.getRiskType());
			}
			if (userid > 0) {
				ps.setLong(n++, userid);
			}
			// if(!searchforreportinfo.getCreditType().equals("-1"))
			if (longTrim(searchforreportinfo.getCreditType()) > 0) {
				ps.setString(n++, searchforreportinfo.getCreditType());

			}
			if (searchforreportinfo.getStatus() > 0) {
				ps.setLong(n++, searchforreportinfo.getStatus());

			}
			// 贷后检查区间
			if (!searchforreportinfo.getCheckType().equals("")) {
				ps.setString(n++, searchforreportinfo.getCheckType());
			}
			if (!searchforreportinfo.getCheckYear().equals("")) {
				ps.setString(n++, searchforreportinfo.getCheckYear());
			}
			if (!searchforreportinfo.getCheckqm().equals("")) {
				ps.setString(n++, searchforreportinfo.getCheckqm());
			}
			if (!searchforreportinfo.getCheckTemp().equals("")) {
				ps.setString(n++, searchforreportinfo.getCheckTemp());
			}

			rs = ps.executeQuery();
			while (rs.next()) {
				aftercreditreportinfo = new AfterCreditReportInfo();
				aftercreditreportinfo.setId(rs.getLong("ID"));
				aftercreditreportinfo.setCheckreportcode(rs
						.getString("checkreportcode"));
				aftercreditreportinfo.setAdvice(rs.getLong("nrisklevel"));
				aftercreditreportinfo.setClientid(rs.getLong("clientid"));
				aftercreditreportinfo
						.setInputdate(rs.getTimestamp("inputdate"));
				aftercreditreportinfo.setInputuserid(rs.getLong("inputuserid"));
				aftercreditreportinfo.setStatus(rs.getLong("status"));

				aftercreditreportinfo.setCheckyear(rs.getString("checkyear"));
				aftercreditreportinfo.setCheckqm(rs.getLong("checkqm"));
				aftercreditreportinfo
						.setChecktemp(rs.getTimestamp("checktemp"));
				aftercreditreportinfo.setClientcode(rs.getString("scode"));
				aftercreditreportinfo.setClientname(rs.getString("sname"));
				aftercreditreportinfo.setContractcode(rs
						.getString("scontractcode"));
				aftercreditreportinfo.setInputuserid(rs.getLong("inputuserid"));
				aftercreditreportinfo.setLoancontractid(rs
						.getLong("loancontractid"));

				// add by dwj 20081125
				aftercreditreportinfo.setNextchecklevel(rs
						.getLong("nextchecklevel"));

				aftercreditreportinfo.setCheckuserid(rs.getLong("checkuserid"));
				aftercreditreportinfo
						.setCheckdate(rs.getTimestamp("checkdate"));

				lastCheckUser = appbiz.getLastCheckPerson(lModuleID,
						lLoanTypeID, lActionID, 1, 1, aftercreditreportinfo
								.getId());
				aftercreditreportinfo.setLastCheckUser(lastCheckUser);
				// end by dwj 20081125
				list.add(aftercreditreportinfo);
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
			log4j.error(e.toString());
			// try {
			// throw new IException("Gen_E001");
			// } catch (IException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
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
				// throw new IException("Gen_E001");
			}
		}

		return list;

	}

	// 转换成 long
	public long longTrim(String strTemp) {
		long lon = -1;
		if (strTemp != null) {
			try {
				lon = Long.parseLong(strTemp);
			} catch (Exception exc) {
				lon = -1;
			}
		}
		return lon;
	}

	/**
	 * 
	 */
	public AfterCreditReportInfo selectReportInfoById(long id) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		AfterCreditReportInfo aftercreditreportinfo = new AfterCreditReportInfo();

		try {
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select  a.id         as  id,   ");
			sbSQL.append(" a.checkreportcode    as checkreportcode,");
			sbSQL.append(" a.clientid           as clientid,");
			sbSQL.append(" a.loancontractid     as loancontractid,");
			sbSQL.append(" a.checkmode          as checkmode,");
			sbSQL.append(" a.checkyear          as checkyear,");
			sbSQL.append(" a.checkqm            as checkqm,");
			sbSQL.append(" a.checktemp          as checktemp,");
			sbSQL.append(" a.checktype          as checktype,");
			sbSQL.append(" a.changecircs        as changecircs,");
			sbSQL.append(" a.creditnote         as creditnote,");
			sbSQL.append(" a.propertyrightclass as propertyrightclass,");
			sbSQL.append(" a.personnelclass     as personnelclass,");
			sbSQL.append(" a.investclass        as investclass,");
			sbSQL.append(" a.lawclass           as lawclass,");
			sbSQL.append(" a.otherclass         as otherclass,");
			sbSQL.append(" a.advice             as advice,");
			sbSQL.append(" a.conclusion         as conclusion,");
			sbSQL.append(" a.status             as status,");
			sbSQL.append(" a.inputuserid        as inputuserid,");
			sbSQL.append(" a.inputdate          as inputdate,");
			sbSQL.append(" a.officeid           as officeid,");
			sbSQL.append(" a.currencyid         as currencyid,");
			sbSQL.append(" a.nextcheckuserid    as nextcheckuserid,");
			sbSQL.append(" a.nextchecklevel     as nextchecklevel,");
			sbSQL.append(" a.islowlevel         as islowlevel,");
			sbSQL.append(" a.department         as department,");
			sbSQL.append(" b.scontractcode      as scontractcode,");
			sbSQL.append(" a.effectdate      as effectdate");

			sbSQL
					.append(" from loan_loanaftercheckreport a ,loan_contractform b where a.loancontractid=b.id and a.id=?");

			System.out
					.println("--------------------------???????????---------------strSql==="
							+ sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			int n = 1;
			ps.setLong(n++, id);

			rs = ps.executeQuery();
			while (rs.next()) {

				aftercreditreportinfo.setId(rs.getLong("id"));
				aftercreditreportinfo.setCheckreportcode(rs
						.getString("checkreportcode"));
				aftercreditreportinfo.setClientid(rs.getLong("clientid"));
				aftercreditreportinfo.setLoancontractid(rs
						.getLong("loancontractid"));
				aftercreditreportinfo.setCheckmode(rs.getLong("checkmode"));
				aftercreditreportinfo.setCheckyear(rs.getString("checkyear"));
				aftercreditreportinfo.setCheckqm(rs.getLong("checkqm"));
				aftercreditreportinfo
						.setChecktemp(rs.getTimestamp("checktemp"));
				aftercreditreportinfo.setChecktype(rs.getLong("checktype"));
				aftercreditreportinfo.setChangecircs(rs
						.getString("changecircs"));
				aftercreditreportinfo.setCreditnote(rs.getString("creditnote"));
				aftercreditreportinfo.setPropertyrightclass(rs
						.getString("propertyrightclass"));
				aftercreditreportinfo.setPersonnelclass(rs
						.getString("personnelclass"));
				aftercreditreportinfo.setInvestclass(rs
						.getString("investclass"));
				aftercreditreportinfo.setLawclass(rs.getString("lawclass"));
				aftercreditreportinfo.setOtherclass(rs.getString("otherclass"));
				aftercreditreportinfo.setAdvice(rs.getLong("advice"));

				aftercreditreportinfo.setConclusion(rs.getString("conclusion"));
				aftercreditreportinfo.setStatus(rs.getLong("status"));
				aftercreditreportinfo.setInputuserid(rs.getLong("inputuserid"));
				aftercreditreportinfo
						.setInputdate(rs.getTimestamp("inputdate"));
				aftercreditreportinfo.setOfficeid(rs.getLong("officeid"));
				aftercreditreportinfo.setCurrencyid(rs.getLong("currencyid"));
				aftercreditreportinfo.setNextcheckuserid(rs
						.getLong("nextcheckuserid"));
				aftercreditreportinfo.setNextchecklevel(rs
						.getLong("nextchecklevel"));
				aftercreditreportinfo.setIslowlevel(rs.getLong("islowlevel"));
				aftercreditreportinfo.setContractcode(rs
						.getString("scontractcode"));
				aftercreditreportinfo.setDepartment(rs.getString("department"));
				aftercreditreportinfo.setEffectDate(rs.getTimestamp("effectdate"));

				String strSQL = "select * from loan_loanaftergageanalyse where reportid = "
						+ aftercreditreportinfo.getId();
				ps = con.prepareStatement(strSQL);
				ResultSet resultSet = ps.executeQuery();

				GageAnalyseInfo gageAnalyseInfo = new GageAnalyseInfo();
				while (resultSet.next()) {
					gageAnalyseInfo.setId(resultSet.getLong("id"));
					gageAnalyseInfo.setReportid(resultSet.getLong("reportid"));
					gageAnalyseInfo.setCredit(resultSet.getLong("credit"));
					gageAnalyseInfo.setAssure(resultSet.getLong("assure"));
					gageAnalyseInfo.setWarrantorname(resultSet
							.getString("warrantorname"));
					gageAnalyseInfo.setCommissary(resultSet
							.getString("commissary"));
					gageAnalyseInfo.setConnection(resultSet
							.getString("connection"));
					gageAnalyseInfo.setCreditlevel(resultSet
							.getString("creditlevel"));
					gageAnalyseInfo.setCreditamount(resultSet
							.getDouble("creditamount"));
					gageAnalyseInfo.setOperationbalance(resultSet
							.getDouble("operationbalance"));
					gageAnalyseInfo.setBewrite(resultSet.getString("bewrite"));
					gageAnalyseInfo.setDroit(resultSet.getString("droit"));
					gageAnalyseInfo
							.setEstimate(resultSet.getDouble("estimate"));
					gageAnalyseInfo.setImpawnrate(resultSet
							.getDouble("impawnrate"));
					gageAnalyseInfo
							.setIscheckin(resultSet.getLong("ischeckin"));
					gageAnalyseInfo.setIsinsure(resultSet.getLong("isinsure"));
					gageAnalyseInfo.setIsnotarization(resultSet
							.getLong("isnotarization"));
					gageAnalyseInfo.setStatus(resultSet.getLong("status"));
				}
				aftercreditreportinfo.setGageanalyse(gageAnalyseInfo);
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
			log4j.error(e.toString());
			// try {
			// throw new IException("Gen_E001");
			// } catch (IException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
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
				// throw new IException("Gen_E001");
			}
		}

		return aftercreditreportinfo;

	}

	/**
	 * 取消检查报告，
	 * 
	 * @param
	 */
	public long cancelReportByID(long reportID) throws RemoteException,
			IException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		String strSQL = null;
		long lResult = 0;

		try {
			con = Database.getConnection();

			strSQL = " update loan_loanaftercheckreport set status = ? where ID = ? ";
			ps = con.prepareStatement(strSQL);

			ps.setLong(1, LOANConstant.reportState.DELETE);
			ps.setLong(2, reportID);

			lResult = ps.executeUpdate();

			ps.close();
			ps = null;
			con.close();
			con = null;

			if (lResult > 0) {
				return 1; // sucess
			} else {
				return 0; // lost because tech reason
			}
		} catch (Exception ex) {
			log4j.error(ex.toString());
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
			} catch (Exception ex) {
				log4j.error(ex.toString());
				throw new IException("Gen_E001");
			}
		}

	}

	/**
	 * 取消检查报告，
	 * 
	 * @param
	 */
	public long cancelSortByID(long sortID) throws RemoteException, IException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		String strSQL = null;
		long lResult = 0;

		try {
			con = Database.getConnection();

			strSQL = " update loan_loanafterfivesort set status = ? where ID = ? ";
			ps = con.prepareStatement(strSQL);

			ps.setLong(1, LOANConstant.reportState.DELETE);
			ps.setLong(2, sortID);

			lResult = ps.executeUpdate();

			ps.close();
			ps = null;
			con.close();
			con = null;

			if (lResult > 0) {
				return 1; // sucess
			} else {
				return 0; // lost because tech reason
			}
		} catch (Exception ex) {
			log4j.error(ex.toString());
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
			} catch (Exception ex) {
				log4j.error(ex.toString());
				throw new IException("Gen_E001");
			}
		}

	}

	// //////////////////////////////////////////////贷款五级分类///////////////////////////////////////////////////////////

	/**
	 * 根据检察频率查询合同信息 方法二
	 * 
	 * @param checkfrequency
	 * @return
	 * @throws Exception
	 */

	public List queryInfoList(long checkfrequency, String year, String QM,
			String temp) throws Exception {
		List list = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		/*
		 * select
		 * c.Scode,c.Sname,ct.id,ct.sContractCode,ct.Mloanamount,ct.Nrisklevel,ct.Dtenddate,ct.Checkfrequency,ct.nstatusid,r.CHECKREPORTCODE,r.Advice
		 * from loan_ContractForm ct, Client c, (select
		 * id,loancontractid,CHECKREPORTCODE,Advice from
		 * loan_loanaftercheckreport where 1=1 and CheckYear ='2008'
		 * --（年,根据页面的输入） and CheckQM = 2 --（季度，根据页面的输入） and status >= 1
		 * --(审批通过后的状态，在同一贷后检查区间，同一贷款合同，应该只有一个有效的贷后检查报告，数据库中，状态的值有1-5，哪个值才是有效的呢？) )
		 * r ???????????????????????????????????????????????????????????? where
		 * 1=1 and ct.nstatusid >= 5 and ct.nstatusid <= 9
		 * --(合同状态为：执行中、已展期、已逾期、呆滞、呆帐) and ct.Nborrowclientid=c.ID and
		 * r.loancontractid(+)=ct.id and ct.checkfrequency=1 --（检查频率为季度） order
		 * by c.Scode,ct.sContractCode
		 */
		try {
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL
					.append(" SELECT c.sname as csname,type.name as subTypeName,ct.id as lid,ct.Nloanid as nloanid,ct.Scontractcode as scontractcode,");
			sbSQL
					.append(" ct.Mloanamount as mloanamount,ct.Nrisklevel as nrisklevel,ct.Dtenddate as dtenddate, ct.Nborrowclientid as nborrowclientid, ");
			sbSQL
					.append(" ct.Dtstartdate as dtstartdate,ct.Checkfrequency as checkfrequency,r.Checkreportcode as checkreportcode,r.id as reportid,r.Advice as advice ");
			sbSQL
					.append(" FROM loan_contractform  ct ,client c,LOAN_LOANTYPESETTING type, ");
			sbSQL
					.append(" (select id,loancontractid,CHECKREPORTCODE,Advice from loan_loanaftercheckreport ");
			sbSQL.append(" where 1=1 ");
			if (!year.equals("")) {
				sbSQL.append(" and CheckYear = ? ");
			}
			if (!QM.equals("")) {
				sbSQL.append(" and CheckQM = ? ");
			}
			if (!temp.equals("")) {
				sbSQL.append(" and Checktemp = ? ");
			}
			sbSQL.append(" and status != 3 "); // 3为取消
			sbSQL.append(" ) r     ");
			sbSQL.append(" WHERE 1=1 and ct.nstatusid >= 5 ");
			sbSQL.append(" and ct.nstatusid <= 9 ");
			sbSQL.append(" and ct.Nborrowclientid = c.ID ");
			sbSQL.append(" and r.loancontractid(+) = ct.id ");
			sbSQL
					.append(" and  ct.checkfrequency = ? and ct.nsubtypeid=type.id ");
			sbSQL.append(" order by c.Scode,ct.sContractCode");
			System.out
					.println("-----------------------------------------strSql==="
							+ sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			int n = 1;
			if (!year.equals("")) {
				ps.setString(n++, year);
			}
			if (!QM.equals("")) {
				ps.setLong(n++, Long.parseLong(QM));
			}
			if (!temp.equals("")) {
				ps.setTimestamp(n++, DataFormat.getDateTime(temp));
			}

			ps.setLong(n++, checkfrequency);
			rs = ps.executeQuery();
			while (rs.next()) {
				System.out
						.println("------------------------------------------------ID--------------------:::"
								+ rs.getLong("lid"));
				ContractFrequencyInfo contractfrequencyinfo = new ContractFrequencyInfo();
				contractfrequencyinfo.setId(rs.getLong("lid"));
				contractfrequencyinfo.setNloanid(rs.getLong("nloanid"));
				contractfrequencyinfo.setSname(rs.getString("csname"));
				contractfrequencyinfo.setScontractcode(rs
						.getString("scontractcode"));
				contractfrequencyinfo.setMloanamount(rs
						.getDouble("mloanamount"));
				contractfrequencyinfo.setNrisklevel(rs.getLong("nrisklevel"));
				contractfrequencyinfo
						.setDtenddate(rs.getTimestamp("dtenddate"));
				contractfrequencyinfo.setDtstartdate(rs
						.getTimestamp("dtstartdate"));
				contractfrequencyinfo.setCheckfrequency(rs
						.getLong("checkfrequency"));
				contractfrequencyinfo.setCheckreportcode(rs
						.getString("checkreportcode"));
				contractfrequencyinfo.setAdvice(rs.getLong("advice"));
				contractfrequencyinfo.setCheckreportid(rs.getLong("reportid"));
				contractfrequencyinfo.setSubTypeName(rs
						.getString("subTypeName"));

				list.add(contractfrequencyinfo);
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
			log4j.error(e.toString());
			// try {
			// throw new IException("Gen_E001");
			// } catch (IException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
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
				// throw new IException("Gen_E001");
			}
		}

		return list;

	}

	/**
	 * 贷款五级分类查询合同信息，不根据检查频率
	 * 
	 * @param checkfrequency
	 * @return
	 * @throws Exception
	 */

	public List queryInfoList() throws Exception {
		List list = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		/*
		 * select
		 * c.Scode,c.Sname,ct.id,ct.sContractCode,ct.Mloanamount,ct.Nrisklevel,ct.Dtenddate,ct.Checkfrequency,ct.nstatusid,r.CHECKREPORTCODE,r.Advice
		 * from loan_ContractForm ct, Client c, (select
		 * id,loancontractid,CHECKREPORTCODE,Advice from
		 * loan_loanaftercheckreport where 1=1 and CheckYear ='2008'
		 * --（年,根据页面的输入） and CheckQM = 2 --（季度，根据页面的输入） and status >= 1
		 * --(审批通过后的状态，在同一贷后检查区间，同一贷款合同，应该只有一个有效的贷后检查报告，数据库中，状态的值有1-5，哪个值才是有效的呢？) )
		 * r ???????????????????????????????????????????????????????????? where
		 * 1=1 and ct.nstatusid >= 5 and ct.nstatusid <= 9
		 * --(合同状态为：执行中、已展期、已逾期、呆滞、呆帐) and ct.Nborrowclientid=c.ID and
		 * r.loancontractid(+)=ct.id and ct.checkfrequency=1 --（检查频率为季度） order
		 * by c.Scode,ct.sContractCode
		 */
		try {
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL
					.append(" SELECT c.sname as csname,type.name as subTypeName,ct.id as lid,ct.Nloanid as nloanid,ct.Scontractcode as scontractcode,");
			sbSQL
					.append(" ct.Mloanamount as mloanamount,ct.Nrisklevel as nrisklevel,ct.Dtenddate as dtenddate, ct.Nborrowclientid as nborrowclientid, ");
			sbSQL
					.append(" ct.Dtstartdate as dtstartdate,ct.Checkfrequency as checkfrequency,r.Checkreportcode as checkreportcode,r.id as reportid,r.Advice as advice ");
			sbSQL
					.append(" FROM loan_contractform  ct ,client c,LOAN_LOANTYPESETTING type, ");
			sbSQL
					.append(" (select id,loancontractid,CHECKREPORTCODE,Advice from loan_loanaftercheckreport ");
			sbSQL.append(" where 1=1 ");
			sbSQL.append(" and status <> "
					+ SYSConstant.CodingRuleStatus.DELETED + " and status <> "
					+ LOANConstant.StatusType.UNCHECK); // 不显示删除状态和未审核状态的记录
			sbSQL.append(" ) r     ");
			sbSQL.append(" WHERE 1=1 and ct.nstatusid >= 5 ");
			sbSQL.append(" and ct.nstatusid <= 9 ");
			sbSQL
					.append(" and ct.Nborrowclientid = c.ID and ct.nsubtypeid=type.id ");
			sbSQL.append(" and r.loancontractid(+) = ct.id ");
			sbSQL.append(" order by c.Scode,ct.sContractCode");
			System.out
					.println("-----------------------------------------strSql==="
							+ sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				System.out
						.println("------------------------------------------------ID--------------------:::"
								+ rs.getLong("lid"));
				ContractFrequencyInfo contractfrequencyinfo = new ContractFrequencyInfo();
				contractfrequencyinfo.setId(rs.getLong("lid"));
				contractfrequencyinfo.setNloanid(rs.getLong("nloanid"));
				contractfrequencyinfo.setSname(rs.getString("csname"));
				contractfrequencyinfo.setScontractcode(rs
						.getString("scontractcode"));
				contractfrequencyinfo.setMloanamount(rs
						.getDouble("mloanamount"));
				contractfrequencyinfo.setNrisklevel(rs.getLong("nrisklevel"));
				contractfrequencyinfo
						.setDtenddate(rs.getTimestamp("dtenddate"));
				contractfrequencyinfo.setDtstartdate(rs
						.getTimestamp("dtstartdate"));
				contractfrequencyinfo.setCheckfrequency(rs
						.getLong("checkfrequency"));
				contractfrequencyinfo.setCheckreportcode(rs
						.getString("checkreportcode"));
				contractfrequencyinfo.setAdvice(rs.getLong("advice"));
				contractfrequencyinfo.setCheckreportid(rs.getLong("reportid"));
				contractfrequencyinfo.setSubTypeName(rs
						.getString("subTypeName"));
				list.add(contractfrequencyinfo);
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
			log4j.error(e.toString());
			// try {
			// throw new IException("Gen_E001");
			// } catch (IException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
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
				// throw new IException("Gen_E001");
			}
		}

		return list;

	}

	/**
	 * 贷款五级分类
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */

	public long insert(AfterCreditFiveSortInfo info) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		List list = null;
		long lResult = -1;
		FiveSortDetailInfo detailInfo = new FiveSortDetailInfo();

		long ID = this.getMaxID("loan_loanafterfivesort");

		long detailId = this.getMaxID("loan_loanafterfivesortdetail");
		long sum = -1;
		try {
			conn = Database.getConnection();

			/** 编码规则* */
			String codeSQL = " select to_char(sysdate,'yy') from dual ";
			String sYear = "";
			String curID = "";
			// long lCurID=cInfo.getCurrencyID();
			String applyCode = "";
			// long lTypeID = cInfo.getSubTypeId();

			// curID = LOANConstant.LoanType.getTypeCode(lTypeID);
			// curID = LOANNameRef.getSubLoanTypeCodeByID(lTypeID);
			curID = LOANConstant.SubLoanType.getCode(12);// lSubTypeID TODO
			System.out.println("codeSQL______________________________="
					+ codeSQL);
			ps = conn.prepareStatement(codeSQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				sYear = rs.getString(1);
			}
			cleanup(rs);
			cleanup(ps);

			log4j.info("sYear:" + sYear + " curID:" + curID);
			System.out.println("sYear:" + sYear + " curID:" + curID);
			int nLen = curID.length() + sYear.length() + 1;

			codeSQL = "select nvl(max(to_number(substr(sortcode,"
					+ (nLen + 1)
					+ ",4))),0)+1 from loan_loanafterfivesort where sortcode like 'S"
					+ sYear + curID + "%'";
			ps = conn.prepareStatement(codeSQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				long ll = rs.getLong(1);

				if (ll < 10) {
					applyCode = "S" + sYear + curID + "000" + ll;
				} else if (ll < 100) {
					applyCode = "S" + sYear + curID + "00" + ll;
				} else if (ll < 1000) {
					applyCode = "S" + sYear + curID + "0" + ll;
				} else {
					applyCode = "S" + sYear + curID + ll;
				}

			}
			cleanup(rs);
			cleanup(ps);

			/** 编码规则* */

			System.out
					.println("applyCode=____________________________________________="
							+ applyCode);

			conn.setAutoCommit(false);

			/* 开始执行插入 */
			strSQL = "insert into loan_loanafterfivesort ("
					+ " id,sortcode,fivesorttype,checkyear, "
					+ " checkqm,checktemp,inputdate,inputuserid,status,nextcheckuserid, "
					+ " nextchecklevel,islowlevel,officeid,currencyid,loantype "
					+ ") values (?, ?, ?, ?, ?, ?, sysdate, ?, ?, ?, ?, ?, ?, ?,? )";
			ps = conn.prepareStatement(strSQL);
			int n = 1;
			System.out
					.println("applyCode=____________________________________________="
							+ applyCode);
			ps.setLong(n++, ID);
			ps.setString(n++, applyCode);
			ps.setLong(n++, info.getFivesorttype());
			ps.setString(n++, info.getCheckyear());
			ps.setLong(n++, info.getCheckqm());
			ps.setTimestamp(n++, info.getChecktemp());
			// ps.setTimestamp(n++, info.getInputdate());
			ps.setLong(n++, info.getInputuserid());
			ps.setLong(n++, info.getStatus());
			ps.setLong(n++, info.getNextcheckuserid());
			ps.setLong(n++, info.getNextchecklevel());
			ps.setLong(n++, info.getIslowlevel());
			ps.setLong(n++, info.getOfficeid());
			ps.setLong(n++, info.getCurrencyid());
			ps.setLong(n++, info.getLoantype());
			lResult = ps.executeUpdate();

			list = info.getDetailinfolist();

			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					detailInfo = (FiveSortDetailInfo) list.get(i);
					if (detailInfo != null) {
						sum = sum + 1;
						detailInfo.setFivesortid(ID);
						this.insert(detailInfo, conn, (detailId + sum), ID);
					}
				}

			}

			conn.commit();
			if (lResult < 0) {
				return -1;
			} else {
				return ID;
			}

		} catch (Exception e) {

			e.printStackTrace();
			conn.rollback();
			return -1;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					System.out.println("=======rs已关=======");
				}
				rs = null;
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (Exception e) {
					System.out.println("=======ps已关=======");
				}
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}

	}

	private long insert(FiveSortDetailInfo info, Connection conn, long credId,
			long fivesortID) throws Exception {
		long sign = -1;
		PreparedStatement ps = null;
		String strSQL = null;

		try {
			strSQL = "insert into loan_loanafterfivesortdetail ("
					+ " id,fivesortid,contractid,sortresolution, "
					+ " explain,status,inputuserid,inputdate,checkreportid,presort "
					+ ") values (?, ?, ?, ?, ?, ?, ?, sysdate, ?,?)";
			ps = conn.prepareStatement(strSQL);
			int n = 1;

			ps.setLong(n++, credId);
			ps.setLong(n++, fivesortID);
			ps.setLong(n++, info.getContractid());
			ps.setLong(n++, info.getSortresolution());
			ps.setString(n++, info.getExplain());
			ps.setLong(n++, info.getStatus());
			ps.setLong(n++, info.getInputuserid());
			// ps.setTimestamp(n++, info.getInputdate());
			ps.setLong(n++, info.getCheckreportid());
			ps.setLong(n++, info.getPresort());

			sign = ps.executeUpdate();
		} finally {
			if (ps != null) {
				ps.close();
				ps = null;
			}
		}

		return sign;
	}

	public long updateFiveSort(AfterCreditFiveSortInfo info) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		List list = null;
		long lResult = -1;
		FiveSortDetailInfo detailInfo = new FiveSortDetailInfo();

		// long ID=this.getMaxID("loan_loanafterfivesort");

		// long detailId=this.getMaxID("loan_loanafterfivesortdetail");
		// long sum = -1;

		try {
			conn = Database.getConnection();

			conn.setAutoCommit(false);

			/* 开始执行插入 */
			strSQL = "update  loan_loanafterfivesort set";
			if (info.getFivesorttype() > 0) {
				strSQL = strSQL + " fivesorttype=?,";
			}

			if (info.getCheckyear() != null && !"".equals(info.getCheckyear())) {

				strSQL = strSQL + " checkyear=?,";
			}
			if (info.getCheckqm() > 0) {

				strSQL = strSQL + " checkqm=?,";
			}
			if (info.getChecktemp() != null && !"".equals(info.getChecktemp())) {

				strSQL = strSQL + " checktemp=?,";
			}

			strSQL = strSQL + " status=?,nextcheckuserid=?, ";// inputdate=?,inputuserid=?,
			strSQL = strSQL
					+ " nextchecklevel=?,islowlevel=?,officeid=?,currencyid= ? where id = ? ";
			ps = conn.prepareStatement(strSQL);
			int n = 1;

			if (info.getFivesorttype() > 0) {
				ps.setLong(n++, info.getFivesorttype());
			}
			if (info.getCheckyear() != null && !"".equals(info.getCheckyear())) {

				ps.setString(n++, info.getCheckyear());
			}
			if (info.getCheckqm() > 0) {

				ps.setLong(n++, info.getCheckqm());
			}
			if (info.getChecktemp() != null && !"".equals(info.getChecktemp())) {

				ps.setTimestamp(n++, info.getChecktemp());
			}
			// ps.setTimestamp(n++, info.getInputdate());
			// ps.setLong(n++, info.getInputuserid());
			ps.setLong(n++, info.getStatus());
			ps.setLong(n++, info.getNextcheckuserid());
			ps.setLong(n++, info.getNextchecklevel());
			ps.setLong(n++, info.getIslowlevel());
			ps.setLong(n++, info.getOfficeid());
			ps.setLong(n++, info.getCurrencyid());
			ps.setLong(n++, info.getId());
			lResult = ps.executeUpdate();

			list = info.getDetailinfolist();

			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					detailInfo = (FiveSortDetailInfo) list.get(i);
					if (detailInfo != null) {

						detailInfo.setFivesortid(info.getId());
						this.updateFiveSortDetail(detailInfo, conn);
					}
				}

			}

			conn.commit();

		} catch (Exception e) {

			e.printStackTrace();
			conn.rollback();
			return -1;
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
		return (lResult > 0 ? info.getId() : -1);
	}

	private long updateFiveSortDetail(FiveSortDetailInfo info, Connection conn)
			throws Exception {
		long sign = -1;
		PreparedStatement ps = null;
		String strSQL = null;

		try {
			strSQL = "update  loan_loanafterfivesortdetail set "
					+ " sortresolution=?,"
					+ " explain=?,status=? where id = ? ";// fivesortid=?,contractid=?,,inputdate=
			// ?,inputuserid=?
			ps = conn.prepareStatement(strSQL);
			int n = 1;

			// ps.setLong(n++, info.getFivesortid());
			// ps.setLong(n++, info.getContractid());
			ps.setLong(n++, info.getSortresolution());
			ps.setString(n++, info.getExplain());
			ps.setLong(n++, info.getStatus());
			// ps.setLong(n++, info.getInputuserid());
			// ps.setTimestamp(n++, info.getInputdate());
			ps.setLong(n++, info.getId());

			sign = ps.executeUpdate();
		} finally {
			if (ps != null) {
				ps.close();
				ps = null;
			}
		}

		return sign;
	}

	/**
	 * 
	 */
	public List selectSortDetailBySortID(long id) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		List SortDetailList = new ArrayList();
		FiveSortDetailInfo fiveSortDetailInfo = null;
		try {
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL
					.append(" select * from  loan_loanafterfivesortdetail detail where detail.fivesortid =? ");

			System.out
					.println("--------------------------???????????---------------strSql==="
							+ sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			int n = 1;
			ps.setLong(n++, id);

			rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println("id===============" + rs.getLong("id"));
				fiveSortDetailInfo = new FiveSortDetailInfo();
				fiveSortDetailInfo.setId(rs.getLong("id"));
				fiveSortDetailInfo.setFivesortid(rs.getLong("fivesortid"));
				fiveSortDetailInfo.setContractid(rs.getLong("contractid"));
				fiveSortDetailInfo.setSortresolution(rs
						.getLong("sortresolution"));
				fiveSortDetailInfo.setExplain(rs.getString("explain"));
				fiveSortDetailInfo.setStatus(rs.getLong("status"));
				fiveSortDetailInfo.setInputdate(rs.getTimestamp("inputdate"));
				fiveSortDetailInfo.setInputuserid(rs.getLong("inputuserid"));
				SortDetailList.add(fiveSortDetailInfo);
			}
			System.out.println("0000000000000000" + SortDetailList.get(0));
			System.out.println("0000000000000000" + SortDetailList.get(1));

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
			// try {
			// throw new IException("Gen_E001");
			// } catch (IException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
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
				// throw new IException("Gen_E001");
			}
		}

		return SortDetailList;

	}

	/**
	 * 贷后五级分类修改查询
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public List getFiveSortSearch(SearchForReportInfo searchforreportinfo)
			throws Exception {
		List list = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		AfterCreditFiveSortInfo fivesortinfo = null;
		try {
			con = Database.getConnection();

			StringBuffer strSQL = new StringBuffer();

			strSQL
					.append("SELECT a.id as ID ,a.sortcode as sortcode, a.fivesorttype as fivesorttype,a.inputdate as inputdate ,a.status as status,a.checkyear as checkyear,a.checkqm as checkqm,a.checktemp as checktemp FROM loan_loanafterfivesort a    ");
			strSQL.append(" where (a.status = "
					+ LOANConstant.StatusType.UNCHECK + ")");

			if (!searchforreportinfo.getCodeFrom().equals("")
					&& !searchforreportinfo.getCodeTo().equals("")) {
				strSQL.append("  and ID between ? and ? ");
			}
			if (!searchforreportinfo.getDateFrom().equals("")
					&& !searchforreportinfo.getDateTo().equals("")) {
				strSQL
						.append("  and a.inputdate between to_timestamp(?,'yyyy-mm-dd') and to_timestamp(?,'yyyy-mm-dd') ");
			}
			if (searchforreportinfo.getStatus() > 0) {
				strSQL.append("  and a.status = ? ");
			}

			// 贷后检查区间
			if (!searchforreportinfo.getCheckType().equals("")) {
				strSQL.append("  and fivesorttype = ? ");
			}
			if (!searchforreportinfo.getCheckYear().equals("")) {
				strSQL.append("  and checkyear = ? ");
			}
			if (!searchforreportinfo.getCheckqm().equals("")) {
				strSQL.append("  and checkqm = ? ");
			}
			if (!searchforreportinfo.getCheckTemp().equals("")) {
				strSQL
						.append("  and checktemp = to_timestamp(?,'yyyy-mm-dd') ");
			}

			// 修改查询 只查询是本人录入的
			strSQL.append("  and a.inputuserid =? ");
			strSQL.append("  order by sortcode desc ");
			System.out
					.println("strSQL.toString()=+++++++++++++++++++++++++++++++="
							+ strSQL.toString());

			// System.out.println("strSQL=" + strSQL);
			ps = con.prepareStatement(strSQL.toString());
			int n = 1;

			if (!searchforreportinfo.getCodeFrom().equals("")) {
				ps.setString(n++, searchforreportinfo.getCodeFrom());
			}
			if (!searchforreportinfo.getCodeTo().equals("")) {
				ps.setString(n++, searchforreportinfo.getCodeTo());
			}
			if (!searchforreportinfo.getDateFrom().equals("")) {
				ps.setString(n++, searchforreportinfo.getDateFrom());
			}
			if (!searchforreportinfo.getDateTo().equals("")) {
				ps.setString(n++, searchforreportinfo.getDateTo());
			}
			if (searchforreportinfo.getStatus() > 0) {
				ps.setLong(n++, searchforreportinfo.getStatus());

			}
			// 贷后检查区间
			if (!searchforreportinfo.getCheckType().equals("")) {
				ps.setString(n++, searchforreportinfo.getCheckType());
			}
			if (!searchforreportinfo.getCheckYear().equals("")) {
				ps.setString(n++, searchforreportinfo.getCheckYear());
			}
			if (!searchforreportinfo.getCheckqm().equals("")) {
				ps.setString(n++, searchforreportinfo.getCheckqm());
			}
			if (!searchforreportinfo.getCheckTemp().equals("")) {
				ps.setString(n++, searchforreportinfo.getCheckTemp());
			}
			ps.setLong(n++, searchforreportinfo.getInputuserid());

			// /////////////////////////////
			rs = ps.executeQuery();
			while (rs.next()) {
				fivesortinfo = new AfterCreditFiveSortInfo();
				fivesortinfo.setId(rs.getLong("ID"));
				fivesortinfo.setSortcode(rs.getString("sortcode"));
				fivesortinfo.setFivesorttype(rs.getLong("fivesorttype"));
				fivesortinfo.setInputdate(rs.getTimestamp("inputdate"));

				fivesortinfo.setStatus(rs.getLong("status"));
				fivesortinfo.setCheckyear(rs.getString("checkyear"));
				fivesortinfo.setCheckqm(rs.getLong("checkqm"));
				fivesortinfo.setChecktemp(rs.getTimestamp("checktemp"));
				list.add(fivesortinfo);
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
			log4j.error(e.toString());
			// try {
			// throw new IException("Gen_E001");
			// } catch (IException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
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
				// throw new IException("Gen_E001");
			}
		}

		return list;

	}

	/**
	 * 贷后五级分类审批查询
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public List getFiveSortApproveSearch(
			SearchForReportInfo searchforreportinfo, long userid)
			throws Exception {
		List list = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		String lastCheckUser = null;
		long lModuleID = Constant.ModuleType.LOAN;// 贷款
		long lLoanTypeID = 10;
		long lActionID = Constant.ApprovalAction.DH_2;
		ApprovalDelegation appbiz = new ApprovalDelegation();

		AfterCreditFiveSortInfo fivesortinfo = null;
		try {
			con = Database.getConnection();

			StringBuffer strSQL = new StringBuffer();

			strSQL
					.append("SELECT a.id as ID ,a.sortcode as sortcode, a.fivesorttype as fivesorttype,a.inputdate as inputdate ,a.status as status,a.checkyear as checkyear,a.checkqm as checkqm,a.checktemp as checktemp,a.nextchecklevel as nextchecklevel,a.checkuserid checkuserid , a.checkdate checkdate FROM loan_loanafterfivesort a  where  ( a.status ="
							+ LOANConstant.StatusType.UNCHECK
							+ " or a.status="
							+ LOANConstant.StatusType.CHECKED + " ) ");

			if (!searchforreportinfo.getCodeFrom().equals("")
					&& !searchforreportinfo.getCodeTo().equals("")) {
				strSQL.append("  and ID between ? and ? ");
			}
			if (!searchforreportinfo.getDateFrom().equals("")
					&& !searchforreportinfo.getDateTo().equals("")) {
				strSQL
						.append("  and a.inputdate between to_timestamp(?,'yyyy-mm-dd') and to_timestamp(?,'yyyy-mm-dd') ");
			}
			// if(userid>0)
			// {
			// strSQL.append(" and nextcheckuserid= ? ");
			// }
			// 贷后检查区间
			if (!searchforreportinfo.getCheckType().equals("")) {
				strSQL.append("  and fivesorttype = ? ");
			}
			if (!searchforreportinfo.getCheckYear().equals("")) {
				strSQL.append("  and checkyear = ? ");
			}
			if (!searchforreportinfo.getCheckqm().equals("")) {
				strSQL.append("  and checkqm = ? ");
			}
			if (!searchforreportinfo.getCheckTemp().equals("")) {
				strSQL
						.append("  and checktemp = to_timestamp(?,'yyyy-mm-dd') ");
			}
			// if(searchforreportinfo!=null&&searchforreportinfo.getNextcheckuserid()>0)
			// {
			// strSQL.append(" and nextcheckuserid = ? ");
			// }

			// 复核人不能为录入人
			if (userid > 0) {
				strSQL.append(" and a.inputuserid <> ? ");
			}
			strSQL.append("  order by sortcode desc ");

			System.out
					.println("strSQL.toString()=+++++++++++++++++++++++++++++++="
							+ strSQL.toString());

			// System.out.println("strSQL=" + strSQL);
			ps = con.prepareStatement(strSQL.toString());
			int n = 1;

			if (!searchforreportinfo.getCodeFrom().equals("")) {
				ps.setString(n++, searchforreportinfo.getCodeFrom());
			}
			if (!searchforreportinfo.getCodeTo().equals("")) {
				ps.setString(n++, searchforreportinfo.getCodeTo());
			}
			if (!searchforreportinfo.getDateFrom().equals("")) {
				ps.setString(n++, searchforreportinfo.getDateFrom());
			}
			if (!searchforreportinfo.getDateTo().equals("")) {
				ps.setString(n++, searchforreportinfo.getDateTo());
			}
			// if(userid>0)
			// {
			// ps.setLong(n++, userid);
			// }
			// 贷后检查区间
			if (!searchforreportinfo.getCheckType().equals("")) {
				ps.setString(n++, searchforreportinfo.getCheckType());
			}
			if (!searchforreportinfo.getCheckYear().equals("")) {
				ps.setString(n++, searchforreportinfo.getCheckYear());
			}
			if (!searchforreportinfo.getCheckqm().equals("")) {
				ps.setString(n++, searchforreportinfo.getCheckqm());
			}
			if (!searchforreportinfo.getCheckTemp().equals("")) {
				ps.setString(n++, searchforreportinfo.getCheckTemp());
			}
			// if(searchforreportinfo!=null&&searchforreportinfo.getNextcheckuserid()>0)
			// {
			// ps.setLong(n++, searchforreportinfo.getNextcheckuserid());
			// }
			if (userid > 0) {
				ps.setLong(n++, userid);
			}
			// /////////////////////////////
			rs = ps.executeQuery();
			while (rs.next()) {
				fivesortinfo = new AfterCreditFiveSortInfo();
				fivesortinfo.setId(rs.getLong("ID"));
				fivesortinfo.setSortcode(rs.getString("sortcode"));
				fivesortinfo.setFivesorttype(rs.getLong("fivesorttype"));
				fivesortinfo.setInputdate(rs.getTimestamp("inputdate"));

				fivesortinfo.setStatus(rs.getLong("status"));
				fivesortinfo.setCheckyear(rs.getString("checkyear"));
				fivesortinfo.setCheckqm(rs.getLong("checkqm"));
				fivesortinfo.setChecktemp(rs.getTimestamp("checktemp"));

				// add by dwj 20081125
				fivesortinfo.setNextchecklevel(rs.getLong("nextchecklevel"));

				fivesortinfo.setCheckuserid(rs.getLong("checkuserid"));
				fivesortinfo.setCheckdate(rs.getTimestamp("checkdate"));

				lastCheckUser = appbiz.getLastCheckPerson(lModuleID,
						lLoanTypeID, lActionID, 1, 1, fivesortinfo.getId());

				fivesortinfo.setLastCheckUser(lastCheckUser);// 最后审核人
				// end by dwj 20081125

				list.add(fivesortinfo);
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
			log4j.error(e.toString());
			// try {
			// throw new IException("Gen_E001");
			// } catch (IException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
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
				// throw new IException("Gen_E001");
			}
		}

		return list;

	}

	/**
	 * 贷后五级分类查询所有
	 * 
	 * @param searchforreportinfo
	 * @return
	 * @throws Exception
	 */
	public List getFiveSortSearchAll(SearchForReportInfo searchforreportinfo)
			throws Exception {
		List list = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		String lastCheckUser = null;
		long lModuleID = Constant.ModuleType.LOAN;// 贷款
		long lLoanTypeID = 10;
		long lActionID = Constant.ApprovalAction.DH_2;
		ApprovalDelegation appbiz = new ApprovalDelegation();

		AfterCreditFiveSortInfo fivesortinfo = null;
		try {
			con = Database.getConnection();

			StringBuffer strSQL = new StringBuffer();

			strSQL
					.append("SELECT a.id as ID ,a.sortcode as sortcode, a.fivesorttype as fivesorttype,a.inputdate as inputdate ,a.status as status,a.checkyear as checkyear,a.checkqm as checkqm,a.checktemp as checktemp,a.nextchecklevel as nextchecklevel,a.checkuserid as checkuserid,a.checkdate as checkdate FROM loan_loanafterfivesort a    ");
			strSQL.append(" where (a.status in (1,2) )");

			if (!searchforreportinfo.getCodeFrom().equals("")
					&& !searchforreportinfo.getCodeTo().equals("")) {
				strSQL.append("  and ID between ? and ? ");
			}
			if (!searchforreportinfo.getDateFrom().equals("")
					&& !searchforreportinfo.getDateTo().equals("")) {
				strSQL
						.append("  and a.inputdate between to_timestamp(?,'yyyy-mm-dd') and to_timestamp(?,'yyyy-mm-dd') ");
			}
			if (searchforreportinfo.getStatus() > 0
					|| searchforreportinfo.getStatus() == -2) {
				strSQL.append("  and a.status = ? ");
			}

			// 贷后检查区间
			if (!searchforreportinfo.getCheckType().equals("")) {
				strSQL.append("  and fivesorttype = ? ");
			}
			if (!searchforreportinfo.getCheckYear().equals("")) {
				strSQL.append("  and checkyear = ? ");
			}
			if (!searchforreportinfo.getCheckqm().equals("")) {
				strSQL.append("  and checkqm = ? ");
			}
			if (!searchforreportinfo.getCheckTemp().equals("")) {
				strSQL
						.append("  and checktemp = to_timestamp(?,'yyyy-mm-dd') ");
			}

			// 修改查询 只查询是本人录入的
			// strSQL.append(" and a.inputuserid =? ");
			strSQL.append("  order by sortcode desc ");
			System.out
					.println("strSQL.toString()=+++++++++++++++++++++++++++++++="
							+ strSQL.toString());

			// System.out.println("strSQL=" + strSQL);
			ps = con.prepareStatement(strSQL.toString());
			int n = 1;

			if (!searchforreportinfo.getCodeFrom().equals("")) {
				ps.setString(n++, searchforreportinfo.getCodeFrom());
			}
			if (!searchforreportinfo.getCodeTo().equals("")) {
				ps.setString(n++, searchforreportinfo.getCodeTo());
			}
			if (!searchforreportinfo.getDateFrom().equals("")) {
				ps.setString(n++, searchforreportinfo.getDateFrom());
			}
			if (!searchforreportinfo.getDateTo().equals("")) {
				ps.setString(n++, searchforreportinfo.getDateTo());
			}
			if (searchforreportinfo.getStatus() > 0
					|| searchforreportinfo.getStatus() == -2) {
				ps.setLong(n++, searchforreportinfo.getStatus());

			}
			// 贷后检查区间
			if (!searchforreportinfo.getCheckType().equals("")) {
				ps.setString(n++, searchforreportinfo.getCheckType());
			}
			if (!searchforreportinfo.getCheckYear().equals("")) {
				ps.setString(n++, searchforreportinfo.getCheckYear());
			}
			if (!searchforreportinfo.getCheckqm().equals("")) {
				ps.setString(n++, searchforreportinfo.getCheckqm());
			}
			if (!searchforreportinfo.getCheckTemp().equals("")) {
				ps.setString(n++, searchforreportinfo.getCheckTemp());
			}
			// ps.setLong(n++, searchforreportinfo.getInputuserid());

			// /////////////////////////////
			rs = ps.executeQuery();
			while (rs.next()) {
				fivesortinfo = new AfterCreditFiveSortInfo();
				fivesortinfo.setId(rs.getLong("ID"));
				fivesortinfo.setSortcode(rs.getString("sortcode"));
				fivesortinfo.setFivesorttype(rs.getLong("fivesorttype"));
				fivesortinfo.setInputdate(rs.getTimestamp("inputdate"));

				fivesortinfo.setStatus(rs.getLong("status"));
				fivesortinfo.setCheckyear(rs.getString("checkyear"));
				fivesortinfo.setCheckqm(rs.getLong("checkqm"));
				fivesortinfo.setChecktemp(rs.getTimestamp("checktemp"));

				// add by dwj 20081125
				fivesortinfo.setNextchecklevel(rs.getLong("nextchecklevel"));

				fivesortinfo.setCheckuserid(rs.getLong("checkuserid"));
				fivesortinfo.setCheckdate(rs.getTimestamp("checkdate"));

				lastCheckUser = appbiz.getLastCheckPerson(lModuleID,
						lLoanTypeID, lActionID, 1, 1, fivesortinfo.getId());

				fivesortinfo.setLastCheckUser(lastCheckUser);// 最后审核人
				// end by dwj 20081125

				list.add(fivesortinfo);
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
			log4j.error(e.toString());
			// try {
			// throw new IException("Gen_E001");
			// } catch (IException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
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
				// throw new IException("Gen_E001");
			}
		}

		return list;

	}

	// 逻辑删除 五级分类
	public long deleteByID(long ID, long stats) throws Exception {
		long result = -1;

		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "";
		try {
			conn = Database.getConnection();
			sql = " update  loan_loanafterfivesort set Status=" + stats
					+ " where ID=" + ID;
			log4j.info("add sql = \n" + sql);
			ps = conn.prepareStatement(sql);
			result = ps.executeUpdate();
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());

			}
		}
		return result;
	}

	/**
	 * 逻辑删除 贷后检查报告
	 * 
	 * @param id
	 * @param StatusID
	 * @return
	 */
	public long update(long id, long StatusID) {
		long flat = -1;

		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;

		try {
			log4j.info("\n=======调用保存方法　ＤＡＯ1====");
			conn = Database.getConnection();

			strSQL = "update loan_loanaftercheckreport " + " set Status=? "
					+ " where ID=? ";
			System.out.println("O_____________________" + strSQL);
			ps = conn.prepareStatement(strSQL);
			int n = 1;
			ps.setLong(n++, StatusID);
			ps.setLong(n++, id);
			ps.executeUpdate();
			log4j.info("\n=======调用保存方法　ＤＡＯ2====");
			cleanup(ps);
			cleanup(conn);

			if (flat < 0) {
				log.info("update  fail:" + flat);
				return -1;
			} else {
				return flat;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			try {
				cleanup(ps);
				cleanup(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	// wp
	public List select_Explain_sortresolution(long id) throws Exception {
		List list = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		Sortresolution_Explain_Info sortresolution_explain = null;

		try {
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL
					.append(" select a.id as ID,a.explain as explain,a.sortresolution as sortresolution, ");
			sbSQL
					.append(" b.fivesorttype as fivesorttype,b.checkyear as checkyear,b.checkqm as checkqm,b.checktemp as checktemp ");
			sbSQL
					.append(" from loan_loanafterfivesortdetail a,loan_loanafterfivesort b where a.fivesortid=b.id and b.id=?");

			System.out
					.println("-----------------------------------------strSql==="
							+ sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			int n = 1;
			ps.setLong(n++, id);
			rs = ps.executeQuery();

			while (rs.next()) {
				System.out
						.println("------------------------------------------------sortresolution--------------------:::"
								+ rs.getLong("sortresolution"));
				sortresolution_explain = new Sortresolution_Explain_Info();
				sortresolution_explain.setId(rs.getLong("ID"));
				sortresolution_explain.setExplain(rs.getString("explain"));
				sortresolution_explain.setSortaftersolution(rs
						.getLong("sortresolution"));
				// add 2008-9-18
				sortresolution_explain.setFivesorttype(rs
						.getLong("fivesorttype"));
				sortresolution_explain.setCheckyear(rs.getString("checkyear"));
				sortresolution_explain.setCheckqm(rs.getLong("checkqm"));
				sortresolution_explain.setChecktemp(rs
						.getTimestamp("checktemp"));

				// sortresolution_explain.setInputdate(rs.getTimestamp("inputdate"));
				// sortresolution_explain.setSortcode(rs.getLong("code"));
				list.add(sortresolution_explain);
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
			log4j.error(e.toString());
			e.printStackTrace();
			// try {
			// throw new IException("Gen_E001");
			// } catch (IException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
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
				// throw new IException("Gen_E001");
			}
		}

		return list;

	}

	// 执行v003页面的表单显示
	public AfterCreditAccountInfo selectAfterCrdAccountInfo(long id)
			throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		AfterCreditAccountInfo aftercreditaccountinfo = new AfterCreditAccountInfo();

		try {
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append("select ");
			sbSQL.append(" c.id as cid,");// 客户ID
			sbSQL.append(" c.scode as cscode,");// 客户编号
			sbSQL.append(" c.sname as csname,");// 客户名称
			sbSQL.append(" c.scapital as cscapital,");// 注册资金
			sbSQL.append(" tt.creditlevel as creditlevel,");// 等级
			sbSQL.append(" tt.creditamount as creditamount,");// 授信额度
			sbSQL.append(" tt.creditgradeid as creditgradeid,");// 信用等级id
			sbSQL.append(" c.NEXTENDATTRIBUTE1 as EXTENDATTRIBUTE1,");// 信用等级id
			sbSQL.append(" c.NEXTENDATTRIBUTE2 as EXTENDATTRIBUTE2,");// 信用等级id
			sbSQL.append(" c.NCLIENTTYPEID1 as CLIENTTYPEID1,");// 信用等级id
			sbSQL.append(" c.NCLIENTTYPEID2 as CLIENTTYPEID2");// 信用等级id
			// sbSQL.append(" tt.creditamount1 as creditamount1");//信用额度
			sbSQL.append(" from client c, ");
			// sbSQL.append(" (select * from loan_creditgrade a where a.status
			// in
			// ("+LOANConstant.LoanCreditgrade.YISHENPI+","+LOANConstant.LoanCreditgrade.YIFUHE+")
			// ) grade,");//评级表 a.status=2 表示当前生效
			// sbSQL.append(" (select d.clientid dclientid, ");//客户ID
			// sbSQL.append(" sum(e.creditamount) creditamount,");//授信额度
			// sbSQL.append(" sum(decode(e.credittype,1,e.creditamount,0))
			// creditamount1");//信用额度
			// sbSQL.append(" from LOAN_CREDITCHECKANDRATIFY d,");//核定表
			// sbSQL.append(" Loan_Credititemdetail e");//详细表
			// sbSQL.append(" where d.id = e.reportid ");
			// sbSQL.append(" and d.status=4");//d.status=4 表示当前生效
			// sbSQL.append(" group by d.clientid) tt");
			// sbSQL.append(" where c.id = grade.clientid(+)");
			sbSQL.append(" (select lc.id creditgradeid,lc.creditlevel,");
			sbSQL.append(" ca.clientid dclientid,");
			sbSQL.append(" sum(ca.creditamount) creditamount");
			sbSQL.append(" from loan_creditgrade lc,credit_amountsetup ca");
			sbSQL.append(" where lc.id = ca.creditgradeid");
			sbSQL.append(" and lc.status in ("
					+ LOANConstant.LoanCreditgrade.YISHENPI + ","
					+ LOANConstant.LoanCreditgrade.YIFUHE + ")");
			sbSQL.append(" and ca.startdate <= to_date('"
					+ DataFormat.formatDate(Env.getSystemDate())
					+ "','yyyy-mm-dd')");
			sbSQL.append(" and ca.enddate >= to_date('"
					+ DataFormat.formatDate(Env.getSystemDate())
					+ "','yyyy-mm-dd')");
			sbSQL
					.append(" group by lc.id,lc.creditlevel,ca.clientid,ca.creditgradeid");
			sbSQL.append(" ) tt");
			sbSQL.append(" where c.id = tt.dclientid(+)");
			sbSQL.append(" and c.id = ?");
			System.out
					.println("-----------------------------------------strSql==="
							+ sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			int n = 1;
			ps.setLong(n++, id);
			// ps.setLong(n++, id);
			// ps.setLong(n++, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				System.out
						.println("------------------------------------------------ID--------------------:::"
								+ rs.getString("csname"));

				aftercreditaccountinfo.setScode(rs.getString("cscode"));
				aftercreditaccountinfo.setSname(rs.getString("csname"));
				aftercreditaccountinfo.setScapital(rs.getString("cscapital"));
				aftercreditaccountinfo.setCreditlevel(rs
						.getString("creditlevel"));
				aftercreditaccountinfo.setCreditamount(rs
						.getDouble("creditamount"));
				aftercreditaccountinfo.setCreditgradeid(rs
						.getLong("creditgradeid"));
				aftercreditaccountinfo.setClientTypeid1(rs
						.getLong("CLIENTTYPEID1"));
				aftercreditaccountinfo.setClientTypeid2(rs
						.getLong("CLIENTTYPEID2"));
				aftercreditaccountinfo.setExtendAttribute1(rs
						.getLong("EXTENDATTRIBUTE1"));
				aftercreditaccountinfo.setExtendAttribute2(rs
						.getLong("EXTENDATTRIBUTE2"));
				// aftercreditaccountinfo.setCamount(rs.getDouble("creditamount1"));

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
			log4j.error(e.toString());
			// try {
			// throw new IException("Gen_E001");
			// } catch (IException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
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
				// throw new IException("Gen_E001");
			}
		}

		return aftercreditaccountinfo;

	}

	/*
	 * 
	 */
	public AfterCreditFiveSortInfo selectFiveSortInfoById(long id)
			throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		AfterCreditFiveSortInfo aftercreditFivesortinfo = new AfterCreditFiveSortInfo();
		FiveSortDetailInfo fiveSortDetailInfo = null;
		List detailinfolist = new ArrayList();
		try {
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select  *  from loan_loanafterfivesort where id=? ");

			System.out
					.println("--------------------------???????????---------------strSql==="
							+ sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			int n = 1;
			ps.setLong(n++, id);

			rs = ps.executeQuery();
			if (rs.next()) {

				aftercreditFivesortinfo.setId(rs.getLong("id"));
				aftercreditFivesortinfo.setSortcode(rs.getString("sortcode"));
				aftercreditFivesortinfo.setFivesorttype(rs
						.getLong("fivesorttype"));
				aftercreditFivesortinfo.setCheckyear(rs.getString("checkyear"));
				aftercreditFivesortinfo.setCheckqm(rs.getLong("checkqm"));
				aftercreditFivesortinfo.setChecktemp(rs
						.getTimestamp("checktemp"));
				aftercreditFivesortinfo.setInputdate(rs
						.getTimestamp("inputdate"));
				aftercreditFivesortinfo.setInputuserid(rs
						.getLong("inputuserid"));
				aftercreditFivesortinfo.setStatus(rs.getLong("status"));
				aftercreditFivesortinfo.setNextcheckuserid(rs
						.getLong("nextcheckuserid"));
				aftercreditFivesortinfo.setNextchecklevel(rs
						.getLong("nextchecklevel"));
				aftercreditFivesortinfo.setOfficeid(rs.getLong("officeid"));
				aftercreditFivesortinfo.setIslowlevel(rs.getLong("islowlevel"));
				aftercreditFivesortinfo.setCurrencyid(rs.getLong("currencyid"));
				aftercreditFivesortinfo.setCheckuserid(rs
						.getLong("checkuserid"));
				aftercreditFivesortinfo.setLoantype(rs.getLong("loantype"));

				sbSQL.setLength(0);
				sbSQL.append(" select ct.sname       as csname, ");
				sbSQL.append(" detail.id             as ID,     ");
				sbSQL.append(" c.id                  as contractid, ");
				sbSQL.append(" c.mloanamount         as mloanamount, ");
				sbSQL.append(" c.dtstartdate         as dtstartdate, ");
				sbSQL.append(" c.dtenddate           as dtenddate, ");
				sbSQL.append(" c.scontractcode       as contractcode, ");
				sbSQL.append(" c.Nrisklevel          as nrisklevel, ");
				sbSQL.append(" r.Checkreportcode     as checkreportcode, ");
				sbSQL.append(" r.Advice              as advice, ");
				sbSQL.append(" detail.sortresolution as sortresolution, ");
				sbSQL.append(" detail.PRESORT        as PRESORT, ");
				sbSQL.append(" detail.CHECKREPORTID  as dCHECKREPORTID, ");
				sbSQL.append(" detail.explain        as explain ");
				sbSQL
						.append(" from loan_loanafterfivesortdetail  detail,loan_loanafterfivesort s, loan_contractform c , client ct, ");
				// sbSQL.append(" (select
				// id,loancontractid,CHECKREPORTCODE,Advice from
				// loan_loanaftercheckreport ");
				// sbSQL.append(" where status >= 1)r ");
				sbSQL.append(" loan_loanaftercheckreport r ");
				sbSQL.append(" where c.id = detail.contractid ");
				sbSQL.append(" and s.id = detail.fivesortid  ");
				sbSQL.append(" and fivesortid=? ");
				// sbSQL.append(" and r.loancontractid = c.id ");
				sbSQL.append(" and c.Nborrowclientid = ct.ID ");
				sbSQL.append(" and detail.CHECKREPORTID = r.id(+) ");

				System.out
						.println("--------------------------???????????---------------strSql==="
								+ sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, id);
				rs = ps.executeQuery();
				while (rs.next()) {
					fiveSortDetailInfo = new FiveSortDetailInfo();
					fiveSortDetailInfo.setId(rs.getLong("ID"));
					fiveSortDetailInfo.setContractid(rs.getLong("contractid"));
					fiveSortDetailInfo.setClientname(rs.getString("csname"));
					fiveSortDetailInfo.setContractcode(rs
							.getString("contractcode"));
					// fiveSortDetailInfo.setRisklevel(rs.getLong("nrisklevel"));
					fiveSortDetailInfo.setRisklevel(rs.getLong("PRESORT"));// 调整前的分类
					fiveSortDetailInfo.setCheckreportcode(rs
							.getString("checkreportcode"));
					fiveSortDetailInfo.setAdvice(rs.getLong("advice"));
					fiveSortDetailInfo.setSortresolution(rs
							.getLong("sortresolution"));
					fiveSortDetailInfo.setPresort(rs.getLong("presort"));
					fiveSortDetailInfo.setExplain(rs.getString("explain"));
					fiveSortDetailInfo.setCheckreportid(rs
							.getLong("dCHECKREPORTID"));
					fiveSortDetailInfo.setMloanamount(rs
							.getDouble("mloanamount"));
					fiveSortDetailInfo.setDtenddate(rs
							.getTimestamp("dtenddate"));
					fiveSortDetailInfo.setDtstartdate(rs
							.getTimestamp("dtstartdate"));

					detailinfolist.add(fiveSortDetailInfo);
				}
				aftercreditFivesortinfo.setDetailinfolist(detailinfolist);
				System.out.println("sizeist+-------------------------" + id);
				System.out.println("sizeist+-------------------------"
						+ detailinfolist.size());
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
			log4j.error(e.toString());
			// try {
			// throw new IException("Gen_E001");
			// } catch (IException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
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
				// throw new IException("Gen_E001");
			}
		}

		return aftercreditFivesortinfo;

	}

	// /////////////////////////////////////////////审批/////////////////////////////////////////////////////////
	/**
	 * 贷后检查报告
	 */
	/*
	 * long lApprovalContentID, String sOpinion, long lUserID, long lNextUserID,
	 * long lAction, long lCurrencyID, long lOfficeID
	 */
	public long checkReport(AfterCreditReportInfo reportinfo)
			throws RemoteException, IException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		long lStatusID = -1;
		long lResultID = -1;
		String strSQL = "";

		long lApprovalContentID = reportinfo.getId();
		String sOpinion = reportinfo.getHeckOpinion();
		long lUserID = reportinfo.getUserID();
		long lNextUserID = reportinfo.getNextcheckuserid();
		long lAction = reportinfo.getLAction();
		long lCurrencyID = reportinfo.getCurrencyid();
		long lOfficeID = reportinfo.getOfficeid();
		// 审核人
		long checkUserID = reportinfo.getCheckuserid();
		Timestamp checkDate = reportinfo.getCheckdate();

		// 定义相应操作常量
		// 模块类型
		long lModuleID = Constant.ModuleType.LOAN;
		// 业务类型
		long lLoanTypeID = 9;// getLoanSubTypeID(lApprovalContentID);//Constant.ApprovalLoanType.TX;
		// 操作类型
		long lActionID = Constant.ApprovalAction.DH_1;
		long lApprovalID = -1;
		long lLevel = -1;

		ApprovalSettingInfo appInfo = new ApprovalSettingInfo();
		ApprovalTracingInfo info = new ApprovalTracingInfo();
		ApprovalDelegation appbiz = new ApprovalDelegation();

		try {
			// //获得ApprovalID
			// lApprovalID =
			// appbiz.getApprovalID(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID);
			// //下一级审核人级别
			// lLevel = appbiz.findApprovalUserLevel(lApprovalID, lNextUserID);
			// Log.print("下一级审核人级别：" + lLevel);
			// //审批设置
			// appInfo = appbiz.findApprovalSetting(lApprovalID);

			conn = Database.getConnection();

			// //////////////////////////////////////////////////////////////////////////////////////////////////////////////
			strSQL = "";
			if (lAction == 1) // 拒绝 取消审核
			{
				// //逻辑删除
				// appbiz.deleteApprovalTracing(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID,lApprovalContentID,2);
				//
				// lStatusID = Constant.RecordStatus.INVALID;
				// lResultID = Constant.ApprovalDecision.REFUSE;
				strSQL = "update loan_loanaftercheckreport set Status="
						+ LOANConstant.StatusType.UNCHECK
						+ ", checkuserid = -1, checkdate = null where ID="
						+ lApprovalContentID;
			}
			// if (lAction == 2) //审批
			// {
			// lStatusID = Constant.RecordStatus.VALID;
			// lResultID = Constant.ApprovalDecision.PASS;
			// if (appInfo.getIsPass() == Constant.YesOrNo.YES && lLevel > 0)
			// {
			// strSQL = "update loan_loanaftercheckreport set NextCheckUserID="
			// + lNextUserID + ", Status=" + LOANConstant.LoanStatus.SUBMIT + ",
			// NextCheckLevel=" + lLevel + " where ID=" + lApprovalContentID;
			// Log.print("更新下一个审核级别（可越级）：" + lLevel);
			// }
			// else
			// {
			// strSQL = "update loan_loanaftercheckreport set NextCheckUserID="
			// + lNextUserID + ", Status=" + LOANConstant.LoanStatus.SUBMIT + ",
			// NextCheckLevel=NextCheckLevel+1 where ID=" + lApprovalContentID;
			// Log.print("更新下一个审核级别（不可越级）：" + lLevel);
			// }
			// }

			if (lAction == 2) // 审批&&最后 审核
			{
				// lStatusID = Constant.RecordStatus.VALID;
				// lResultID = Constant.ApprovalDecision.FINISH;
				// strSQL = " update loan_loanaftercheckreport set
				// NextCheckUserID = "
				// + lNextUserID + ", Status = " +
				// LOANConstant.reportState.CHECK
				// + " where ID = " + lApprovalContentID;
				strSQL = " update loan_loanaftercheckreport set CheckUserID = ?, CheckDate = ?,Status = ? "
						+ " where ID = ? ";

			}
			// if (lAction == 4) //修改
			// {
			// lStatusID = Constant.RecordStatus.VALID;
			// lResultID = Constant.ApprovalDecision.RETURN;
			// strSQL = " update loan_loanaftercheckreport set
			// NextCheckUserID=InputUserID, Status=" +
			// LOANConstant.LoanStatus.SUBMIT + ", NextCheckLevel=1 where ID=" +
			// lApprovalContentID;
			//						
			//						
			// }
			int n = 1;

			Log.print(strSQL);
			ps = conn.prepareStatement(strSQL);
			Log.print("SQL end");
			if (lAction == 2) {
				ps.setLong(n++, checkUserID);
				ps.setTimestamp(n++, checkDate);
				ps.setLong(n++, LOANConstant.StatusType.CHECKED);
				ps.setLong(n++, lApprovalContentID);
			}
			ps.executeUpdate();
			ps.close();
			ps = null;

			// //////////////////////////////////////////////////////////////////////////////////////////////////////////////

			// info.setModuleID(lModuleID);
			// info.setLoanTypeID(lLoanTypeID);
			// info.setActionID(lActionID);
			// info.setApprovalContentID(lApprovalContentID);
			// //info.setSerialID(lSerialID);
			// info.setUserID(lUserID);
			// info.setNextUserID(lNextUserID);
			// info.setOpinion(sOpinion);
			// info.setResultID(lResultID);
			// info.setStatusID(lStatusID);
			// info.setOfficeID(lOfficeID);
			// info.setCurrencyID(lCurrencyID);
			// Log.print("saveApprovalTracing begin");
			// appbiz.saveApprovalTracing(info);
			// Log.print("saveApprovalTracing end");

			conn.close();
			conn = null;
			// ////////////////////
		} catch (Exception ex) {
			log4j.error(ex.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception ex) {
				log4j.error(ex.toString());
				throw new IException("Gen_E001");
			}
		}
		Log.print("checkReport end");
		return lApprovalContentID;
	}

	/**
	 * 贷后五级分类
	 */
	/*
	 * long lApprovalContentID, String sOpinion, long lUserID, long lNextUserID,
	 * long lAction, long lCurrencyID, long lOfficeID
	 */
	public long checkFiveLevelSort(AfterCreditFiveSortInfo fivelevelsortinfo)
			throws RemoteException, IException, Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		long lStatusID = -1;
		long lResultID = -1;
		String strSQL = "";
		long lResult = -1; // 执行状态

		long lApprovalContentID = fivelevelsortinfo.getId();
		String sOpinion = fivelevelsortinfo.getHeckOpinion();
		long lUserID = fivelevelsortinfo.getUserID();
		long lNextUserID = fivelevelsortinfo.getNextcheckuserid();
		long lAction = fivelevelsortinfo.getLAction();
		long lCurrencyID = fivelevelsortinfo.getCurrencyid();
		long lOfficeID = fivelevelsortinfo.getOfficeid();
		//
		List detaillist = fivelevelsortinfo.getDetailinfolist();
		long checkUserID = fivelevelsortinfo.getCheckuserid();
		Timestamp checkDate = fivelevelsortinfo.getCheckdate();
		// 定义相应操作常量
		// 模块类型
		long lModuleID = Constant.ModuleType.LOAN;
		// 业务类型
		long lLoanTypeID = 10;// getLoanSubTypeID(lApprovalContentID);//Constant.ApprovalLoanType.TX;
		// 操作类型
		long lActionID = Constant.ApprovalAction.DH_2;
		long lApprovalID = -1;
		long lLevel = -1;

		ApprovalSettingInfo appInfo = new ApprovalSettingInfo();
		ApprovalTracingInfo info = new ApprovalTracingInfo();
		ApprovalDelegation appbiz = new ApprovalDelegation();

		FiveSortDetailInfo detailInfo = new FiveSortDetailInfo();

		try {
			// //获得ApprovalID
			// lApprovalID =
			// appbiz.getApprovalID(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID);
			// //下一级审核人级别
			// lLevel = appbiz.findApprovalUserLevel(lApprovalID, lNextUserID);
			// Log.print("下一级审核人级别：" + lLevel);
			// //审批设置
			// appInfo = appbiz.findApprovalSetting(lApprovalID);

			conn = Database.getConnection();
			conn.setAutoCommit(false);

			// //////////////////////////////////////////////////////////////////////////////////////////////////////////////
			strSQL = "";
			if (lAction == 1) // 取消审核
			{
				// //逻辑删除
				// appbiz.deleteApprovalTracing(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID,lApprovalContentID,2);
				//
				// lStatusID = Constant.RecordStatus.INVALID;
				// lResultID = Constant.ApprovalDecision.REFUSE;
				// strSQL = "update loan_loanafterfivesort set Status=" +
				// LOANConstant.LoanStatus.REFUSE + " where ID=" +
				// lApprovalContentID;
				strSQL = "update loan_loanafterfivesort set Status="
						+ LOANConstant.StatusType.UNCHECK
						+ ", checkuserid = -1, checkdate = null where ID="
						+ lApprovalContentID;
			}
			if (lAction == 2) // 审核
			{
				// lStatusID = Constant.RecordStatus.VALID;
				// lResultID = Constant.ApprovalDecision.PASS;
				// if (appInfo.getIsPass() == Constant.YesOrNo.YES && lLevel >
				// 0)
				// {
				// strSQL = "update loan_loanafterfivesort set NextCheckUserID="
				// + lNextUserID + ", Status=" + LOANConstant.LoanStatus.SUBMIT
				// + ", NextCheckLevel=" + lLevel + " where ID=" +
				// lApprovalContentID;
				// Log.print("更新下一个审核级别（可越级）：" + lLevel);
				// }
				// else
				// {
				// strSQL = "update loan_loanafterfivesort set NextCheckUserID="
				// + lNextUserID + ", Status=" + LOANConstant.LoanStatus.SUBMIT
				// + ", NextCheckLevel=NextCheckLevel+1 where ID=" +
				// lApprovalContentID;
				// Log.print("更新下一个审核级别（不可越级）：" + lLevel);
				// }
				strSQL = " update loan_loanafterfivesort set CheckUserID = ?, CheckDate = ?,Status = ? "
						+ " where ID = ? ";
			}

			// if (lAction == 3) //审批&&最后
			// {
			// lStatusID = Constant.RecordStatus.VALID;
			// lResultID = Constant.ApprovalDecision.FINISH;
			// strSQL = " update loan_loanafterfivesort set NextCheckUserID = "
			// + lNextUserID + ", Status = " + LOANConstant.reportState.CHECK
			// + " where ID = " + lApprovalContentID;
			//							
			// }
			// if (lAction == 4) //修改
			// {
			// lStatusID = Constant.RecordStatus.VALID;
			// lResultID = Constant.ApprovalDecision.RETURN;
			// strSQL = " update loan_loanafterfivesort set
			// NextCheckUserID=InputUserID, Status=" +
			// LOANConstant.LoanStatus.SUBMIT + ", NextCheckLevel=1 where ID=" +
			// lApprovalContentID;
			// }

			int n = 1;
			Log.print(strSQL);
			ps = conn.prepareStatement(strSQL);
			Log.print("SQL end");
			if (lAction == 2) {
				ps.setLong(n++, checkUserID);
				ps.setTimestamp(n++, checkDate);
				ps.setLong(n++, LOANConstant.StatusType.CHECKED);
				ps.setLong(n++, lApprovalContentID);
			}
			lResult = ps.executeUpdate();
			ps.close();
			ps = null;
			// 更新合同风险级别
			this.updateContractSort(conn, lApprovalContentID, lAction);
			// ////////////////////
			conn.commit();
		} catch (Exception ex) {
			log4j.error(ex.toString());
			conn.rollback();
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception ex) {
				log4j.error(ex.toString());
				throw new IException("Gen_E001");
			}
		}
		Log.print("checkReport end");
		return lResult;
	}

	public long examineUpdate(AfterCreditReportInfo lepInfo) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		long loanID = lepInfo.getId();
		long userID = lepInfo.getUserID();
		long nextUserID = lepInfo.getNextcheckuserid();
		long statusID = lepInfo.getStatus();
		long lResult = -1;
		long lInterestTypeID = -1;
		long liborRateID = -1;

		try {
			conn = Database.getConnection();
			sb.append(" update loan_loanaftercheckreport set ");
			sb.append(" nextcheckuserid = ? ");
			sb.append(" where id=?");

			log4j.print(sb.toString());
			System.out.println(sb.toString());
			ps = conn.prepareStatement(sb.toString());

			int i = 1;
			ps.setLong(i++, nextUserID);
			ps.setLong(i++, loanID);
			lResult = ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		} catch (Exception e) {
			cleanup(ps);
			cleanup(conn);
			e.printStackTrace();
		} finally {
			cleanup(ps);
			cleanup(conn);
		}

		return lResult;
	}

	public long updateLoanStatus(long loanID, long userID, long newStatusID)
			throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;

		try {
			conn = Database.getConnection();
			strSQL = " update loan_loanaftercheckreport set Status=? where ID=?";

			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, newStatusID);
			ps.setLong(2, loanID);

			lResult = ps.executeUpdate();
			if (lResult > 0) // 如果是网银提交过来，更改网银申请的状态
			{
				long lStatusID = -4;
				long lReturnTmp = -1;

				if ((newStatusID == LOANConstant.LoanStatus.SAVE)
						|| (newStatusID == LOANConstant.LoanStatus.SUBMIT)) {
					lStatusID = OBConstant.LoanInstrStatus.ACCEPT;
				} else if (newStatusID == LOANConstant.LoanStatus.CANCEL) // 已取消
				{
					lStatusID = OBConstant.LoanInstrStatus.CANCEL;
				} else if (newStatusID == LOANConstant.LoanStatus.CHECK) // 已审核
				{
					lStatusID = OBConstant.LoanInstrStatus.APPROVE;
				} else if (newStatusID == LOANConstant.LoanStatus.REFUSE) // 已拒绝
				{
					lStatusID = OBConstant.LoanInstrStatus.REFUSE;
				} else if (newStatusID == Constant.RecordStatus.INVALID) // 已删除
				{
					lStatusID = OBConstant.LoanInstrStatus.DELETE;
				}

				if (lStatusID > -4) {
					OBLoanDao dao = new OBLoanDao(conn);
					lReturnTmp = dao.updateOBStatus(loanID, lStatusID);
					if (lReturnTmp <= 0) {
						Log.print("===不是网银提交过来的===");
					} else {
						Log.print("===更新网银贷款申请状态成功===");
					}
				}
			}
			cleanup(ps);
			cleanup(conn);

			if (lResult < 0) {
				log4j.info("update loan property info error:" + lResult);
				return -1;
			} else {
				return loanID;
			}
		} catch (Exception e) {

			cleanup(ps);
			cleanup(conn);
			throw e;
		} finally {

			cleanup(ps);
			cleanup(conn);
		}

	}

	public long updateLoanNextCheckLevel(long lLoanID, long lNextCheckLevel)
			throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		long lResult = -1;

		try {
			conn = Database.getConnection();
			sb.append(" update loan_loanaftercheckreport set ");
			if (lNextCheckLevel > 0) {
				sb.append(" nextchecklevel = " + lNextCheckLevel);
			} else {
				sb.append(" nextchecklevel = nextchecklevel + 1");
			}
			sb.append(" where id = ? ");

			log4j.print(sb.toString());
			System.out.println(sb.toString());
			ps = conn.prepareStatement(sb.toString());

			ps.setLong(1, lLoanID);
			lResult = ps.executeUpdate();

			cleanup(ps);
			cleanup(conn);
		} catch (Exception e) {
			cleanup(ps);
			cleanup(conn);
			e.printStackTrace();
		} finally {
			cleanup(ps);
			cleanup(conn);
		}

		return lResult;
	}

	/**
	 * 贷后五级分类
	 */
	public long fiveSortUpdate(AfterCreditFiveSortInfo lepInfo)
			throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		long loanID = lepInfo.getId();
		long userID = lepInfo.getUserID();
		long nextUserID = lepInfo.getNextcheckuserid();
		long statusID = lepInfo.getStatus();
		long lResult = -1;
		long lInterestTypeID = -1;
		long liborRateID = -1;

		try {
			conn = Database.getConnection();
			sb.append(" update loan_loanafterfivesort set ");
			sb.append(" nextcheckuserid = ? ");
			sb.append(" where id=?");

			log4j.print(sb.toString());
			System.out.println(sb.toString());
			ps = conn.prepareStatement(sb.toString());

			int i = 1;
			ps.setLong(i++, nextUserID);
			ps.setLong(i++, loanID);
			lResult = ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		} catch (Exception e) {
			cleanup(ps);
			cleanup(conn);
			e.printStackTrace();
		} finally {
			cleanup(ps);
			cleanup(conn);
		}

		return lResult;
	}

	public long updateLoanStatuss(long loanID, long userID, long newStatusID)
			throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;

		try {
			conn = Database.getConnection();
			strSQL = " update loan_loanafterfivesort set Status=? where ID=?";

			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, newStatusID);
			ps.setLong(2, loanID);

			lResult = ps.executeUpdate();
			if (lResult > 0) // 如果是网银提交过来，更改网银申请的状态
			{
				long lStatusID = -4;
				long lReturnTmp = -1;

				if ((newStatusID == LOANConstant.LoanStatus.SAVE)
						|| (newStatusID == LOANConstant.LoanStatus.SUBMIT)) {
					lStatusID = OBConstant.LoanInstrStatus.ACCEPT;
				} else if (newStatusID == LOANConstant.LoanStatus.CANCEL) // 已取消
				{
					lStatusID = OBConstant.LoanInstrStatus.CANCEL;
				} else if (newStatusID == LOANConstant.LoanStatus.CHECK) // 已审核
				{
					lStatusID = OBConstant.LoanInstrStatus.APPROVE;
				} else if (newStatusID == LOANConstant.LoanStatus.REFUSE) // 已拒绝
				{
					lStatusID = OBConstant.LoanInstrStatus.REFUSE;
				} else if (newStatusID == Constant.RecordStatus.INVALID) // 已删除
				{
					lStatusID = OBConstant.LoanInstrStatus.DELETE;
				}

				if (lStatusID > -4) {
					OBLoanDao dao = new OBLoanDao(conn);
					lReturnTmp = dao.updateOBStatus(loanID, lStatusID);
					if (lReturnTmp <= 0) {
						Log.print("===不是网银提交过来的===");
					} else {
						Log.print("===更新网银贷款申请状态成功===");
					}
				}
			}
			cleanup(ps);
			cleanup(conn);

			if (lResult < 0) {
				log4j.info("update loan property info error:" + lResult);
				return -1;
			} else {
				return loanID;
			}
		} catch (Exception e) {

			cleanup(ps);
			cleanup(conn);
			throw e;
		} finally {

			cleanup(ps);
			cleanup(conn);
		}

	}

	public long updateLoanNextCheckLevels(long lLoanID, long lNextCheckLevel)
			throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		long lResult = -1;

		try {
			conn = Database.getConnection();
			sb.append(" update loan_loanafterfivesort set ");
			if (lNextCheckLevel > 0) {
				sb.append(" nextchecklevel = " + lNextCheckLevel);
			} else {
				sb.append(" nextchecklevel = nextchecklevel + 1");
			}
			sb.append(" where id = ? ");

			log4j.print(sb.toString());
			System.out.println(sb.toString());
			ps = conn.prepareStatement(sb.toString());

			ps.setLong(1, lLoanID);
			lResult = ps.executeUpdate();

			cleanup(ps);
			cleanup(conn);
		} catch (Exception e) {
			cleanup(ps);
			cleanup(conn);
			e.printStackTrace();
		} finally {
			cleanup(ps);
			cleanup(conn);
		}

		return lResult;
	}

	public PageLoader mainTenanceSearch(SearchForReportInfo searchforreportinfo)
			throws Exception {
		String[] sql = getMainTenanceSearchSQL(searchforreportinfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
				.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader
				.initPageLoader(
						new AppContext(),
						sql[1],
						sql[0],
						sql[2],
						(int) Constant.PageControl.CODE_PAGELINECOUNT,
						"com.iss.itreasury.loan.aftercredit.dataentity.AfterCreditReportInfo",
						null);
		pageLoader.setOrderBy(" " + sql[3] + " ");
		return pageLoader;
	}

	private String[] getMainTenanceSearchSQL(
			SearchForReportInfo searchforreportinfo) {

		long ActionID = searchforreportinfo.getActionID();
		String[] mainTenanceSQL = new String[4];
		StringBuffer sbSQL1 = new StringBuffer();
		// sbSQL1.append(" GAGE.ID AS GAGEID,GAGE.GAGECODE AS
		// GAGECODE,GAGE.GAGUENAME AS GAGENAME,GAGE.GAGUEPROPERTY AS
		// GAGEPROPERTY, ");
		// sbSQL1.append(" GAGE.GAGUETYPE AS GAGETYPE,GAGE.FREEUNITSID AS
		// FREEUNITSID,GAGE.PLEDGEAMOUNT AS PLEDGEAMOUNT,GAGE.APPRAISVALUE AS
		// APPRAISVALUE, ");
		// sbSQL1.append(" DECODE(GAGE.FEEPERSONTYPE,1,(SELECT C.SNAME FROM
		// CLIENT C WHERE C.ID = GAGE.FREEUNITSID), ");
		// sbSQL1.append(" (SELECT E.SNAME FROM LOAN_EXTERIORUNITS E WHERE E.ID
		// = GAGE.FREEUNITSID)) AS FREEUNITSNAME, ");
		// sbSQL1.append(" DECODE(GAGE.FEEPERSONTYPE,1,(SELECT
		// C.SLEGALPERSONCODECERT FROM CLIENT C WHERE C.ID = GAGE.FREEUNITSID),
		// ");
		// sbSQL1.append(" (SELECT E.SLEGALPERSONCODECERT FROM
		// LOAN_EXTERIORUNITS E WHERE E.ID = GAGE.FREEUNITSID)) AS
		// SLEGALPERSONCODECERT, ");
		// sbSQL1.append(" (SELECT U.SNAME FROM USERINFO U WHERE U.ID =
		// GAGE.INPUTUSERID) AS USERNAME, ");
		// sbSQL1.append(" GAGE.INPUTDATE AS INPUTDATE,GAGE.STATUS AS
		// STATUS,GAGE.CREDITASSUREAMOUNT CREDITASSUREAMOUNT ");
		sbSQL1
				.append("  c.scode as clientcode ,c.sname as clientname,b.nsubtypeid as nsubTypeID,b.scontractcode as contractcode,a.inputuserid as inputuserid, a.id as ID,a.clientid as clientid,a.loancontractid as loancontractid,a.inputdate as inputdate,a.checkreportcode as checkreportcode,a.status as status,a.advice as advice,a.checktype as checktype,a.checkyear as checkyear,a.checkqm as checkqm,a.checktemp as checktemp,a.nextchecklevel  as nextchecklevel,a.checkUserID as checkUserID,a.checkDate as checkDate");
		mainTenanceSQL[0] = sbSQL1.toString();

		mainTenanceSQL[1] = " loan_loanaftercheckreport a  ,loan_contractform b ,client c ";

		StringBuffer sbSQL2 = new StringBuffer();
		sbSQL2
				.append(" a.clientid=c.id and a.status not in (0,-1,-2) and a.loancontractid = b.id and b.nofficeid="
						+ searchforreportinfo.getNofficeID()
						+ " and b.NCURRENCYID="
						+ searchforreportinfo.getNcurrencyID());

		/*
		 * if (ActionID == 1) { sbSQL2.append(" and (a.status = 1 )");
		 *  } else if (ActionID == 3) {
		 * 
		 * sbSQL2.append(" and (a.status = 1 or a.status = 2 )");
		 *  }
		 */

		// strSQL.append (" and b.nstatusid >=
		// "+LOANConstant.ContractStatus.ACTIVE);
		// strSQL.append (" and b.nstatusid <=
		// "+LOANConstant.ContractStatus.BADDEBT);
		// strSQL.append (" and b.ntypeid in(1,2,3,4,8,80,200) ");
		if (!searchforreportinfo.getClientFrom().equals("")
				&& !searchforreportinfo.getClientTo().equals("")) {
			sbSQL2.append("  and a.clientid between "
					+ searchforreportinfo.getClientFrom() + " and "
					+ searchforreportinfo.getClientTo() + " ");
		}
		if (!searchforreportinfo.getCodeFrom().equals("")
				&& !searchforreportinfo.getCodeTo().equals("")) {
			sbSQL2.append("  and a.loancontractid between "
					+ searchforreportinfo.getCodeFrom() + " and "
					+ searchforreportinfo.getCodeTo() + " ");
		}
		if (!searchforreportinfo.getDateFrom().equals("")
				&& !searchforreportinfo.getDateTo().equals("")) {
			sbSQL2.append("  and a.inputdate between to_timestamp('"
					+ searchforreportinfo.getDateFrom()
					+ "','yyyy-mm-dd') and to_timestamp('"
					+ searchforreportinfo.getDateTo() + "','yyyy-mm-dd') ");
		}
		if (searchforreportinfo.getRiskType() > 0) {
			sbSQL2.append("  and a.ADVICE = "
					+ searchforreportinfo.getRiskType() + " ");

		}
		if (!searchforreportinfo.getCreditType().equals("-1")) {
			sbSQL2.append("  and  b.nsubtypeid = "
					+ searchforreportinfo.getCreditType() + " ");

		}
		if (searchforreportinfo.getStatus() >= 0
				|| searchforreportinfo.getStatus() == -2) {
			sbSQL2.append("  and a.status = " + searchforreportinfo.getStatus()
					+ " ");

		}

		// 贷后检查区间
		if (!searchforreportinfo.getCheckType().equals("")) {
			sbSQL2.append("  and a.checktype = "
					+ searchforreportinfo.getCheckType() + " ");
		}
		if (!searchforreportinfo.getCheckYear().equals("")) {
			sbSQL2.append("  and a.checkyear = "
					+ searchforreportinfo.getCheckYear() + " ");
		}
		if(!searchforreportinfo.getCheckType().equals("3")){
		if (!searchforreportinfo.getCheckqm().equals("-1")) {
			sbSQL2.append("  and a.checkqm = "
					+ searchforreportinfo.getCheckqm() + " ");
		}
		}
		if (!searchforreportinfo.getCheckTemp().equals("")) {
			sbSQL2.append("  and trunc(a.checktemp) = to_date('"
					+ searchforreportinfo.getCheckTemp() + "','yyyy-mm-dd') ");
		}

		// 修改查询 只查询是本人录入的
//		if (ActionID == 1)// 2008-12-20
//		{
//			sbSQL2.append("  and a.inputuserid ="
//					+ searchforreportinfo.getInputuserid() + " ");
//		}
		// strSQL.append(" order by checkreportcode desc ");

		// sbSQL2.append(" GAGE.OFFICEID =
		// "+loanGageInfoMationSearchInfo.getOfficeID()+" AND GAGE.CURRENCYID =
		// "+loanGageInfoMationSearchInfo.getCurrencyID());
		// if(loanGageInfoMationSearchInfo.getGagueProperty() > 0)
		// {
		// sbSQL2.append(" AND GAGE.GAGUEPROPERTY =
		// "+loanGageInfoMationSearchInfo.getGagueProperty());
		// }
		// if(loanGageInfoMationSearchInfo.getGagueType() > 0)
		// {
		// sbSQL2.append(" AND GAGE.GAGUETYPE =
		// "+loanGageInfoMationSearchInfo.getGagueType());
		// }
		// if(!loanGageInfoMationSearchInfo.getGagueName().equals(""))
		// {
		// sbSQL2.append(" AND GAGE.GAGUENAME LIKE '%"+
		// loanGageInfoMationSearchInfo.getGagueName() +"%'");
		// }
		// if(!loanGageInfoMationSearchInfo.getGagueCode().equals(""))
		// {
		// sbSQL2.append(" AND GAGE.GAGECODE LIKE '%"+
		// loanGageInfoMationSearchInfo.getGagueCode() +"%'");
		// }
		// if(loanGageInfoMationSearchInfo.getFeePersonFrom() > 0)
		// {
		// sbSQL2.append(" AND GAGE.FREEUNITSID >=
		// "+loanGageInfoMationSearchInfo.getFeePersonFrom());
		// }
		// if(loanGageInfoMationSearchInfo.getFeePersonTo() > 0)
		// {
		// sbSQL2.append(" AND GAGE.FREEUNITSID <=
		// "+loanGageInfoMationSearchInfo.getFeePersonTo());
		// }
		// if(loanGageInfoMationSearchInfo.getFeePersonType() > 0)
		// {
		// sbSQL2.append(" AND GAGE.FEEPERSONTYPE =
		// "+loanGageInfoMationSearchInfo.getFeePersonType());
		// }
		// if(loanGageInfoMationSearchInfo.getPledgeAmountFrom() > 0)
		// {
		// sbSQL2.append(" AND GAGE.PLEDGEAMOUNT >=
		// "+loanGageInfoMationSearchInfo.getPledgeAmountFrom());
		// }
		// if(loanGageInfoMationSearchInfo.getPledgeAmountTo() > 0)
		// {
		// sbSQL2.append(" AND GAGE.PLEDGEAMOUNT <=
		// "+loanGageInfoMationSearchInfo.getPledgeAmountTo());
		// }
		// if(loanGageInfoMationSearchInfo.getAppraisValueFrom() > 0)
		// {
		// sbSQL2.append(" AND GAGE.appraisvalue >=
		// "+loanGageInfoMationSearchInfo.getAppraisValueFrom());
		// }
		// if(loanGageInfoMationSearchInfo.getAppraisValueTo() > 0)
		// {
		// sbSQL2.append(" AND GAGE.appraisvalue <=
		// "+loanGageInfoMationSearchInfo.getAppraisValueTo());
		// }
		// if(loanGageInfoMationSearchInfo.getInputUserID() > 0)
		// {
		// sbSQL2.append(" AND GAGE.INPUTUSERID =
		// "+loanGageInfoMationSearchInfo.getInputUserID());
		// }
		// if(!loanGageInfoMationSearchInfo.getInputDateFrom().equals(""))
		// {
		// sbSQL2.append(" AND GAGE.INPUTDATE >=
		// to_date('"+loanGageInfoMationSearchInfo.getInputDateFrom()+"','yyyy-mm-dd')");
		// }
		// if(!loanGageInfoMationSearchInfo.getInputDateTo().equals(""))
		// {
		// sbSQL2.append(" AND GAGE.INPUTDATE <=
		// to_date('"+loanGageInfoMationSearchInfo.getInputDateTo()+"','yyyy-mm-dd')");
		// }
		// if(loanGageInfoMationSearchInfo.getGagueStatus() > 0)
		// {
		// sbSQL2.append(" AND GAGE.STATUS =
		// "+loanGageInfoMationSearchInfo.getGagueStatus());
		//					
		// }
		// else
		// {
		// if(lActionID == LOANConstant.Actions.QUERY)
		// {
		// sbSQL2.append(" AND GAGE.STATUS
		// in("+LOANConstant.GageStatus.SAVE+","+LOANConstant.GageStatus.BECOME_EFFECTIVE+","+LOANConstant.GageStatus.BECOME_INVALID+")");
		// }
		// else
		// {
		// sbSQL2.append(" AND GAGE.STATUS
		// in("+LOANConstant.GageStatus.SAVE+","+LOANConstant.GageStatus.BECOME_EFFECTIVE+")");
		// }
		// }
		mainTenanceSQL[2] = sbSQL2.toString();

		// mainTenanceSQL[3] =
		// this.getTenanceSearchDescSQL(searchforreportinfo.getOrderParam(),searchforreportinfo.getDesc());
		mainTenanceSQL[3] = "";

		return mainTenanceSQL;
	}

	// public String getTenanceSearchDescSQL(long orderParam,long desc)
	// {
	// String strDesc = desc == 1 ? " desc " : "";
	// StringBuffer sbSQL3 = new StringBuffer();
	// switch ((int) orderParam)
	// {
	// case 1 : sbSQL3.append(" ORDER BY GAGECODE " + strDesc); break;
	// case 2 : sbSQL3.append(" ORDER BY GAGENAME " + strDesc); break;
	// case 3 : sbSQL3.append(" ORDER BY GAGEPROPERTY " + strDesc); break;
	// case 4 : sbSQL3.append(" ORDER BY GAGETYPE " + strDesc); break;
	// case 5 : sbSQL3.append(" ORDER BY FREEUNITSNAME " + strDesc); break;
	// case 6 : sbSQL3.append(" ORDER BY SLEGALPERSONCODECERT" + strDesc);
	// break;
	// case 7 : sbSQL3.append(" ORDER BY appraisvalue " + strDesc); break;
	// case 8 : sbSQL3.append(" ORDER BY USERNAME " + strDesc); break;
	// case 9 : sbSQL3.append(" ORDER BY INPUTDATE " + strDesc); break;
	// case 10 : sbSQL3.append(" ORDER BY STATUS " + strDesc); break;
	// default :sbSQL3.append(" ORDER BY GAGEID " + strDesc); break;
	// }
	// return sbSQL3.toString();
	// }
	public PageLoader mainTenanceSearch2(SearchForReportInfo searchforreportinfo)
			throws Exception {
		String[] sql = getMainTenanceSearchSQL2(searchforreportinfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
				.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader
				.initPageLoader(
						new AppContext(),
						sql[1],
						sql[0],
						sql[2],
						(int) Constant.PageControl.CODE_PAGELINECOUNT,
						"com.iss.itreasury.loan.aftercredit.dataentity.AfterCreditReportInfo",
						null);
		pageLoader.setOrderBy(" " + sql[3] + " ");
		return pageLoader;
	}

	private String[] getMainTenanceSearchSQL2(
			SearchForReportInfo searchforreportinfo) {

		long ActionID = searchforreportinfo.getActionID();
		String[] mainTenanceSQL = new String[4];
		StringBuffer sbSQL1 = new StringBuffer();

		sbSQL1
				.append(" c.scode as clientcode ,c.sname as clientname,b.scontractcode as contractcode,a.id as ID,a.clientid as clientid,a.loancontractid as loancontractid,a.inputdate as inputdate,a.checkreportcode as checkreportcode,a.inputuserid as inputuserid,a.checkuserid as checkuserid,a.checkdate as checkdate,a.status as status,b.nrisklevel as advice,a.checktype as checktype,a.checkyear as checkyear,a.checkqm as checkqm,a.checktemp as checktemp,a.nextchecklevel  as nextchecklevel ");
		mainTenanceSQL[0] = sbSQL1.toString();

		mainTenanceSQL[1] = " loan_loanaftercheckreport a  ,loan_contractform b ,client c ";

		StringBuffer sbSQL2 = new StringBuffer();
		sbSQL2.append(" a.clientid=c.id and a.loancontractid = b.id ");

		if (ActionID == 1) {
			sbSQL2.append(" and (a.status = " + LOANConstant.StatusType.UNCHECK
					+ " )");

		} else if (ActionID == 2) {

			sbSQL2.append(" and (a.status = " + LOANConstant.StatusType.UNCHECK
					+ " or a.status = " + LOANConstant.StatusType.CHECKED
					+ " )");

		}

		if (!searchforreportinfo.getClientFrom().equals("")
				&& !searchforreportinfo.getClientTo().equals("")) {
			sbSQL2.append("  and a.clientid between "
					+ searchforreportinfo.getClientFrom() + " and "
					+ searchforreportinfo.getClientTo() + " ");
		}
		if (!searchforreportinfo.getCodeFrom().equals("")
				&& !searchforreportinfo.getCodeTo().equals("")) {
			sbSQL2.append("  and a.loancontractid between "
					+ searchforreportinfo.getCodeFrom() + " and "
					+ searchforreportinfo.getCodeTo() + " ");
		}
		if (!searchforreportinfo.getDateFrom().equals("")
				&& !searchforreportinfo.getDateTo().equals("")) {
			sbSQL2.append("  and a.inputdate between to_timestamp('"
					+ searchforreportinfo.getDateFrom()
					+ "','yyyy-mm-dd') and to_timestamp('"
					+ searchforreportinfo.getDateTo() + "','yyyy-mm-dd') ");
		}
		if (searchforreportinfo.getRiskType() > 0) {
			sbSQL2.append("  and b.nrisklevel = "
					+ searchforreportinfo.getRiskType() + " ");

		}
		// 复核人不能为录入人
		if (searchforreportinfo.getNuserid() > 0) {
			sbSQL2.append(" and a.inputuserid <> "
					+ searchforreportinfo.getNuserid() + " ");
		}
		if (!searchforreportinfo.getCreditType().equals("-1")) {
			sbSQL2.append("  and  b.nsubtypeid = "
					+ searchforreportinfo.getCreditType() + " ");

		}
		if (searchforreportinfo.getStatus() > 0
				|| searchforreportinfo.getStatus() == -2) {
			sbSQL2.append("  and a.status = " + searchforreportinfo.getStatus()
					+ " ");

		}

		// 贷后检查区间
		if (!searchforreportinfo.getCheckType().equals("")) {
			sbSQL2.append("  and a.checktype = "
					+ searchforreportinfo.getCheckType() + " ");
		}
		if (!searchforreportinfo.getCheckYear().equals("")) {
			sbSQL2.append("  and a.checkyear = "
					+ searchforreportinfo.getCheckYear() + " ");
		}
		if (!searchforreportinfo.getCheckqm().equals("")) {
			sbSQL2.append("  and a.checkqm = "
					+ searchforreportinfo.getCheckqm() + " ");
		}
		if (!searchforreportinfo.getCheckTemp().equals("")) {
			sbSQL2.append("  and a.checktemp = to_timestamp('"
					+ searchforreportinfo.getCheckTemp() + "','yyyy-mm-dd') ");
		}

		// strSQL.append(" order by checkreportcode desc ");

		mainTenanceSQL[2] = sbSQL2.toString();

		// mainTenanceSQL[3] =
		// this.getTenanceSearchDescSQL(searchforreportinfo.getOrderParam(),searchforreportinfo.getDesc());
		mainTenanceSQL[3] = "";

		return mainTenanceSQL;
	}

	public PageLoader mainTenanceFiveSortSearch(
			SearchForReportInfo searchforreportinfo) throws Exception {
		String[] sql = getMainTenanceFiveSortSearchSQL(searchforreportinfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
				.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader
				.initPageLoader(
						new AppContext(),
						sql[1],
						sql[0],
						sql[2],
						(int) Constant.PageControl.CODE_PAGELINECOUNT,
						"com.iss.itreasury.loan.aftercredit.dataentity.AfterCreditFiveSortInfo",
						null);
		pageLoader.setOrderBy(" " + sql[3] + " ");
		return pageLoader;
	}

	private String[] getMainTenanceFiveSortSearchSQL(
			SearchForReportInfo searchforreportinfo) {

		long ActionID = searchforreportinfo.getActionID();
		String[] mainTenanceSQL = new String[4];
		StringBuffer sbSQL1 = new StringBuffer();

		sbSQL1
				.append(" a.id as ID ,a.sortcode as sortcode, a.fivesorttype as fivesorttype,a.inputdate as inputdate ,a.status as status,a.checkyear as checkyear,a.checkqm as checkqm,a.checktemp as checktemp,a.checkuserid as checkuserid,a.checkdate as checkdate,a.loantype,a.inputuserid ");
		mainTenanceSQL[0] = sbSQL1.toString();

		mainTenanceSQL[1] = " loan_loanafterfivesort a ";

		StringBuffer sbSQL2 = new StringBuffer();
		sbSQL2.append(" a.status in (" + LOANConstant.StatusType.UNCHECK + ","
				+ LOANConstant.StatusType.CHECKED + ")");

		if (!searchforreportinfo.getCodeFrom().equals("")
				&& !searchforreportinfo.getCodeTo().equals("")) {
			sbSQL2.append("  and ID between "
					+ searchforreportinfo.getCodeFrom() + " and "
					+ searchforreportinfo.getCodeTo() + " ");
		}
		if (!searchforreportinfo.getDateFrom().equals("")
				&& !searchforreportinfo.getDateTo().equals("")) {
			sbSQL2.append("  and a.inputdate between to_timestamp('"
					+ searchforreportinfo.getDateFrom()
					+ "','yyyy-mm-dd') and to_timestamp('"
					+ searchforreportinfo.getDateTo() + "','yyyy-mm-dd') ");
		}
		if (searchforreportinfo.getStatus() > 0) {
			sbSQL2.append("  and a.status = " + searchforreportinfo.getStatus()
					+ " ");
		}

		// 贷后检查区间
		if (!searchforreportinfo.getCheckType().equals("")) {
			sbSQL2.append("  and fivesorttype = "
					+ searchforreportinfo.getCheckType() + " ");
		}
		if (!searchforreportinfo.getCheckYear().equals("")) {
			sbSQL2.append("  and checkyear = "
					+ searchforreportinfo.getCheckYear() + " ");
		}
		if (!searchforreportinfo.getCheckqm().equals("")) {
			sbSQL2.append("  and checkqm = " + searchforreportinfo.getCheckqm()
					+ " ");
		}
		if (!searchforreportinfo.getCheckTemp().equals("")) {
			sbSQL2.append("  and checktemp = to_timestamp('"
					+ searchforreportinfo.getCheckTemp() + "','yyyy-mm-dd') ");
		}

		if (ActionID != 3) {
			// 修改查询 只查询未复核状态的
			sbSQL2.append(" and a.status in ("
					+ LOANConstant.StatusType.UNCHECK + ")");
			// 修改查询 只查询是本人录入的
			sbSQL2.append("  and a.inputuserid = "
					+ searchforreportinfo.getInputuserid() + " ");
		}

		// strSQL.append(" order by sortcode desc ");
		// System.out.println("strSQL.toString()=+++++++++++++++++++++++++++++++="
		// + strSQL.toString());

		// if(ActionID==1)
		// {
		// sbSQL2.append ( " and (a.status = "+LOANConstant.StatusType.UNCHECK+"
		// )");
		//				
		// }else if(ActionID==2)
		// {
		//					
		// sbSQL2.append ( " and (a.status = "+LOANConstant.StatusType.UNCHECK+"
		// or a.status = "+LOANConstant.StatusType.CHECKED+" )");
		//					
		// }

		// strSQL.append(" order by checkreportcode desc ");

		mainTenanceSQL[2] = sbSQL2.toString();

		// mainTenanceSQL[3] =
		// this.getTenanceSearchDescSQL(searchforreportinfo.getOrderParam(),searchforreportinfo.getDesc());
		mainTenanceSQL[3] = "";

		return mainTenanceSQL;
	}

	public PageLoader mainTenanceFiveSortSearch2(
			SearchForReportInfo searchforreportinfo) throws Exception {
		String[] sql = getMainTenanceFiveSortSearchSQL2(searchforreportinfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
				.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader
				.initPageLoader(
						new AppContext(),
						sql[1],
						sql[0],
						sql[2],
						(int) Constant.PageControl.CODE_PAGELINECOUNT,
						"com.iss.itreasury.loan.aftercredit.dataentity.AfterCreditFiveSortInfo",
						null);
		pageLoader.setOrderBy(" " + sql[3] + " ");
		return pageLoader;
	}

	private String[] getMainTenanceFiveSortSearchSQL2(
			SearchForReportInfo searchforreportinfo) {

		long ActionID = searchforreportinfo.getActionID();
		String[] mainTenanceSQL = new String[4];
		StringBuffer sbSQL1 = new StringBuffer();
		long userid = searchforreportinfo.getInputuserid();

		sbSQL1
				.append(" a.id as ID ,a.sortcode as sortcode, a.fivesorttype as fivesorttype,a.inputdate as inputdate ,a.status as status,a.checkyear as checkyear,a.checkqm as checkqm,a.checktemp as checktemp,a.nextchecklevel as nextchecklevel,a.checkuserid checkuserid , a.checkdate checkdate,a.loantype,a.inputuserid ");
		mainTenanceSQL[0] = sbSQL1.toString();

		mainTenanceSQL[1] = " loan_loanafterfivesort a ";

		StringBuffer sbSQL2 = new StringBuffer();
		sbSQL2.append(" (a.status = " + LOANConstant.StatusType.UNCHECK
				+ " or a.status=" + LOANConstant.StatusType.CHECKED + " )");

		if (!searchforreportinfo.getCodeFrom().equals("")
				&& !searchforreportinfo.getCodeTo().equals("")) {
			sbSQL2.append("  and ID between "
					+ searchforreportinfo.getCodeFrom() + " and "
					+ searchforreportinfo.getCodeTo() + " ");
		}
		if (!searchforreportinfo.getDateFrom().equals("")
				&& !searchforreportinfo.getDateTo().equals("")) {
			sbSQL2.append("  and a.inputdate between to_timestamp('"
					+ searchforreportinfo.getDateFrom()
					+ "','yyyy-mm-dd') and to_timestamp('"
					+ searchforreportinfo.getDateTo() + "','yyyy-mm-dd') ");
		}
		if (searchforreportinfo.getStatus() > 0) {
			sbSQL2.append("  and a.status = " + searchforreportinfo.getStatus()
					+ " ");
		}

		// 贷后检查区间
		if (!searchforreportinfo.getCheckType().equals("")) {
			sbSQL2.append("  and fivesorttype = "
					+ searchforreportinfo.getCheckType() + " ");
		}
		if (!searchforreportinfo.getCheckYear().equals("")) {
			sbSQL2.append("  and checkyear = "
					+ searchforreportinfo.getCheckYear() + " ");
		}
		if (!searchforreportinfo.getCheckqm().equals("")) {
			sbSQL2.append("  and checkqm = " + searchforreportinfo.getCheckqm()
					+ " ");
		}
		if (!searchforreportinfo.getCheckTemp().equals("")) {
			sbSQL2.append("  and checktemp = to_timestamp('"
					+ searchforreportinfo.getCheckTemp() + "','yyyy-mm-dd') ");
		}

		// 复核人不能为录入人
		if (userid > 0) {
			sbSQL2.append(" and a.inputuserid <> " + userid + " ");
		}

		mainTenanceSQL[2] = sbSQL2.toString();

		// mainTenanceSQL[3] =
		// this.getTenanceSearchDescSQL(searchforreportinfo.getOrderParam(),searchforreportinfo.getDesc());
		mainTenanceSQL[3] = "";

		return mainTenanceSQL;
	}

	/*
	 * 修改合同风险级别（五级分类）
	 * 
	 */
	public long updateContractSort(Connection conn, long id, long lAction)
			throws Exception {
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		try {
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.setLength(0);
			sbSQL.append(" select * ");
			sbSQL.append(" from loan_loanafterfivesortdetail ");
			sbSQL.append(" where fivesortid=? ");
			sbSQL.append(" and PRESORT is not null ");
			sbSQL.append(" and PRESORT > 0 ");
			sbSQL.append(" and PRESORT != sortresolution ");// 如果分类有变化

			System.out
					.println("--------------------------???????????---------------strSql==="
							+ sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, id);
			rs = ps.executeQuery();
			StringBuffer sbSQL2 = new StringBuffer();
			while (rs.next()) {
				long lPreRisk = -1;
				long lCurRisk = -1;
				long lContractID = -1;
				lPreRisk = rs.getLong("PRESORT");
				lCurRisk = rs.getLong("sortresolution");
				lContractID = rs.getLong("contractid");
				if (lPreRisk > 0 && lCurRisk > 0 && lContractID > 0) {
					sbSQL2.setLength(0);
					if (lAction == 1) // 取消审核
					{
						sbSQL2
								.append(" update loan_contractform  set NRISKLEVEL = "
										+ lPreRisk
										+ " where id = "
										+ lContractID);
					} else if (lAction == 2) // 审核
					{
						sbSQL2
								.append(" update loan_contractform  set NRISKLEVEL = "
										+ lCurRisk
										+ " where id = "
										+ lContractID);
					}
					ps2 = conn.prepareStatement(sbSQL2.toString());
					System.out.println("------更新合同风险级别------strSql==="
							+ sbSQL2.toString());
					ps2.executeUpdate();
				}
			}
		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (ps2 != null) {
					ps2.close();
					ps2 = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
			}
		}
		return 1;
	}

	/*
	 * 此区间是否已做过五级分类，还未符合
	 * 
	 */
	public long HaveBeenSorted(String year, String qm) throws Exception {
		long lInputUserID = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		try {
			conn = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.setLength(0);
			sbSQL.append(" select * ");
			sbSQL.append(" from loan_loanafterfivesort ");
			sbSQL.append(" where checkyear = " + year);
			sbSQL.append(" and checkqm = " + qm);
			sbSQL.append(" and Status = 1 ");
			System.out.println("-----------strSql===" + sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				lInputUserID = rs.getLong("inputuserid");
			}
		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
			}
		}
		return lInputUserID;
	}

	/**
	 * 贷款五级分类查询合同信息，不根据检查频率(增加贷款类型参数)
	 * 
	 * @param checkfrequency
	 * @return
	 * @throws Exception
	 */

	public List queryInfoList(long lloantypeID) throws Exception {
		List list = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL
					.append(" SELECT c.sname as csname,ct.id as lid,ct.Nloanid as nloanid,ct.Scontractcode as scontractcode,");
			sbSQL
					.append(" ct.Mloanamount as mloanamount,ct.Nrisklevel as nrisklevel,ct.Dtenddate as dtenddate, ct.Nborrowclientid as nborrowclientid, ");
			sbSQL
					.append(" ct.Dtstartdate as dtstartdate,ct.Checkfrequency as checkfrequency,r.Checkreportcode as checkreportcode,r.id as reportid,r.Advice as advice ");
			sbSQL.append(" FROM loan_contractform  ct ,client c, ");
			sbSQL
					.append(" (select id,loancontractid,CHECKREPORTCODE,Advice from loan_loanaftercheckreport ");
			sbSQL.append(" where 1=1 ");
			sbSQL.append(" and status <> "
					+ SYSConstant.CodingRuleStatus.DELETED + " and status <> "
					+ LOANConstant.StatusType.UNCHECK); // 不显示删除状态和未审核状态的记录
			sbSQL.append(" ) r     ");
			sbSQL.append(" WHERE 1=1 and ct.nstatusid >= 5 ");
			sbSQL.append(" and ct.nstatusid <= 9 ");
			if (lloantypeID > 0) {
				sbSQL.append(" and ct.ntypeid = " + lloantypeID);
			}
			sbSQL.append(" and ct.Nborrowclientid = c.ID ");
			sbSQL.append(" and r.loancontractid(+) = ct.id ");
			sbSQL.append(" order by c.Scode,ct.sContractCode");
			System.out
					.println("-----------------------------------------strSql==="
							+ sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				System.out
						.println("------------------------------------------------ID--------------------:::"
								+ rs.getLong("lid"));
				ContractFrequencyInfo contractfrequencyinfo = new ContractFrequencyInfo();
				contractfrequencyinfo.setId(rs.getLong("lid"));
				contractfrequencyinfo.setNloanid(rs.getLong("nloanid"));
				contractfrequencyinfo.setSname(rs.getString("csname"));
				contractfrequencyinfo.setScontractcode(rs
						.getString("scontractcode"));
				contractfrequencyinfo.setMloanamount(rs
						.getDouble("mloanamount"));
				contractfrequencyinfo.setNrisklevel(rs.getLong("nrisklevel"));
				contractfrequencyinfo
						.setDtenddate(rs.getTimestamp("dtenddate"));
				contractfrequencyinfo.setDtstartdate(rs
						.getTimestamp("dtstartdate"));
				contractfrequencyinfo.setCheckfrequency(rs
						.getLong("checkfrequency"));
				contractfrequencyinfo.setCheckreportcode(rs
						.getString("checkreportcode"));
				contractfrequencyinfo.setAdvice(rs.getLong("advice"));
				contractfrequencyinfo.setCheckreportid(rs.getLong("reportid"));
				list.add(contractfrequencyinfo);
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
			log4j.error(e.toString());
			// try {
			// throw new IException("Gen_E001");
			// } catch (IException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
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
				// throw new IException("Gen_E001");
			}
		}

		return list;

	}

	private String mergeString(Object[] objs) {
		if (objs == null || objs.length == 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < objs.length; i++) {
			sb.append(objs[i].toString()).append(",");
		}
		return sb.toString().substring(0, sb.toString().length() - 1);
	}

	/**
	 * 取消审批时执行此方法
	 * 
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long doCancelApproval(AfterCreditReportInfo info)
			throws RemoteException, IRollbackException {
		long lReturn = -1;
		// LoanPayNoticeDao paydao = null;
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();

		try {
			// 取消审批
			lReturn = updateCheckStatus(info.getId(),
					LOANConstant.AfterCreditStatus.SUBMIT,
					info.getContractID(), info.getAdvice(),info.getInputuserid(), 3,info.getEffectDate());
			System.out.println("the lReturn is  " + lReturn);
			if (lReturn > 0) {
				// 将审批记录表内的该交易的审批记录状态置为无效
				if (inutParameterInfo.getApprovalEntryID() > 0) {
					FSWorkflowManager.cancelApprovalRecord(inutParameterInfo);
				}
			}

		} catch (Exception e) {
			throw new IRollbackException(context, e.getMessage(), e);
		}
		return lReturn;
	}

	/**
	 * 查询我的工作中的业务
	 * 
	 * @param loanMyWorkInfo
	 * @return
	 * @throws IException
	 */
	public Object queryMyWork(LoanMyWorkInfo loanMyWorkInfo) throws IException {
		try {

			switch ((int) loanMyWorkInfo.getQueryPurpose()) {

			case (int) LOANConstant.WorkType.WAITDEALWITHWORK:
				return queryWaitDealWithWork(loanMyWorkInfo);

			case (int) LOANConstant.WorkType.CURRENTWORK:
				return loadCurrentWorkList(loanMyWorkInfo);

			case (int) LOANConstant.WorkType.HISTORYWORK:
				// hm = FSWorkflowManager.getHistoryList(pInfo);
				return loadFinishedWorkList(loanMyWorkInfo);

			case (int) LOANConstant.WorkType.FINISHEDWORK:
				return loadFinishedWorkList(loanMyWorkInfo);

			case (int) LOANConstant.WorkType.REFUSEWORK:
				return loadRefuseWorkList(loanMyWorkInfo);

			case (int) LOANConstant.WorkType.CANCELAPPROVAL:
				return loadCancelApprovalList(loanMyWorkInfo);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("Gen_E001", e);
		} finally {
			this.finalizeDAO();
		}
		return new Vector();
	}

	/**
	 * 取消审批业务查询
	 * 
	 * @param loanMyWorkInfo
	 * @return
	 * @throws Exception
	 */
	private PageLoader loadCancelApprovalList(LoanMyWorkInfo loanMyWorkInfo)
			throws Exception {
		getCancelApprovalSql(loanMyWorkInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
				.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(new AppContext(), m_sbFrom.toString(),
				m_sbSelect.toString(), m_sbWhere.toString(),
				(int) Constant.PageControl.CODE_PAGELINECOUNT,
				"com.iss.itreasury.loan.mywork.dataentity.AfterCreditWorkInfo",
				null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}

	/**
	 * 查询取消审批业务SQL
	 * 
	 * @param loanMyWorkInfo
	 * @throws IException
	 */
	protected void getCancelApprovalSql(LoanMyWorkInfo loanMyWorkInfo)
			throws IException {
		// select
		m_sbSelect = new StringBuffer();
		m_sbSelect
				.append(" vl.*, f.id as contractid,re.approvalentryid approvalEntryID,re.linkurl linkUrl,re.id approvalrecordId,u.sname as inputusername,c.sname as clientname,f.scontractcode as contractcode,f.nsubtypeid as loanSubTypeId");

		// from
		m_sbFrom = new StringBuffer();
		m_sbFrom
				.append(" sys_approvalrecord re,LOAN_LOANAFTERCHECKREPORT vl,userinfo u,client c,loan_contractform f");

		// where
		m_sbWhere = new StringBuffer();
		m_sbWhere
				.append(" re.transid = vl.Id and u.id=vl.inputuserid and c.id = vl.clientid and f.id=vl.loancontractid");
		m_sbWhere.append(" and re.officeid = " + loanMyWorkInfo.getOfficeID());
		m_sbWhere.append(" and re.currencyid = "
				+ loanMyWorkInfo.getCurrencyID());
		m_sbWhere.append(" and re.moduleid = " + loanMyWorkInfo.getModuleID());
		m_sbWhere.append(" and re.actionId = " + ApprovalAction.DH_1);
		// m_sbWhere.append(" and re.transtypeid = vl.loanSubTypeId");
		m_sbWhere.append(" and re.lastappuserid = "
				+ loanMyWorkInfo.getUserID());
		m_sbWhere.append(" and re.statusid = "
				+ SETTConstant.RecordStatus.VALID);
		m_sbWhere.append(" and vl.status = " + LOANConstant.LoanStatus.CHECK);

		// order by
		m_sbOrderBy = new StringBuffer();

		String strDesc = loanMyWorkInfo.getDesc() == 2 ? " desc " : " asc";

		long lOrderField = loanMyWorkInfo.getOrderField();

		if (lOrderField > 0) {
			switch ((int) lOrderField) {
			case 1:
				m_sbOrderBy.append(" order by vl.loanTypeId " + strDesc);
				break;
			case 2:
				m_sbOrderBy.append(" order by vl.loanSubTypeId " + strDesc);
				break;
			case 3:
				m_sbOrderBy.append(" order by vl.borrowclientid " + strDesc);
				break;
			case 4:
				m_sbOrderBy.append(" order by vl.contractcode " + strDesc);
				break;
			case 5:
				m_sbOrderBy.append(" \n order by vl.code " + strDesc);
				break;
			case 6:
				m_sbOrderBy.append(" order by vl.inputuserId " + strDesc);
				break;
			case 7:
				m_sbOrderBy.append(" order by vl.inputdate " + strDesc);
				break;
			default:
				m_sbOrderBy.append(" order by approvalrecordId  desc");
				break;
			}
		} else {
			m_sbOrderBy.append(" order by approvalrecordId desc");
		}
	}

	/**
	 * 查询拒绝业务
	 * 
	 * @param loanMyWorkInfo
	 * @return
	 * @throws Exception
	 */
	private PageLoader loadRefuseWorkList(LoanMyWorkInfo loanMyWorkInfo)
			throws Exception {
		getRefuseWorkSql(loanMyWorkInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
				.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(new AppContext(), m_sbFrom.toString(),
				m_sbSelect.toString(), m_sbWhere.toString(),
				(int) Constant.PageControl.CODE_PAGELINECOUNT,
				"com.iss.itreasury.loan.mywork.dataentity.AfterCreditWorkInfo",
				null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}

	/**
	 * 查询拒绝业务SQL
	 * 
	 * @param loanMyWorkInfo
	 * @throws IException
	 */
	protected void getRefuseWorkSql(LoanMyWorkInfo loanMyWorkInfo)
			throws IException {
		m_sbSelect = new StringBuffer();
		m_sbSelect
				.append(" distinct vl.*,f.scontractcode as contractcode,f.nsubtypeid as loanSubTypeId,f.id as contractid,c.sname as clientname,u.sname as inputusername,re.transtypeid transtypeid,re.transid transid \n");

		m_sbFrom = new StringBuffer();
		m_sbFrom
				.append(" (select distinct t.officeid,t.currencyid,t.moduleid,t.transtypeid,t.actionid,t.transid from sys_approvalrecord t) re,LOAN_LOANAFTERCHECKREPORT vl,client c,userinfo u,loan_contractform f \n");

		m_sbWhere = new StringBuffer();
		m_sbWhere
				.append(" re.transid = vl.id and c.id=vl.clientid and u.id=vl.inputuserid and f.id=vl.loancontractid \n");
		m_sbWhere.append(" and re.officeid=" + loanMyWorkInfo.getOfficeID()
				+ " \n");
		m_sbWhere.append(" and re.currencyid=" + loanMyWorkInfo.getCurrencyID()
				+ " \n");
		m_sbWhere.append(" and re.moduleid=" + loanMyWorkInfo.getModuleID()
				+ " \n");
		m_sbWhere.append(" and vl.status = "
				+ loanMyWorkInfo.getStatusID());
		m_sbWhere.append(" and re.actionid = " + ApprovalAction.DH_1);
		// m_sbWhere.append(" or vl.status = " +
		// LOANConstant.AfterCreditStatus.SUBMIT +")");
		m_sbWhere.append(" and vl.inputuserid = " + loanMyWorkInfo.getUserID()
				+ " \n");
		System.out.println("已办业务-sbSQL:\n"
				+m_sbSelect+"\n"+m_sbFrom+"\n"+m_sbWhere+"\n");
		m_sbOrderBy = new StringBuffer();
		String strDesc = loanMyWorkInfo.getDesc() == 2 ? " desc " : " asc";

		long lOrderField = loanMyWorkInfo.getOrderField();
		if (lOrderField > 0) {
			switch ((int) lOrderField) {
			case 1:
				m_sbOrderBy.append(" \n order by vl.loanTypeId " + strDesc);
				break;
			case 2:
				m_sbOrderBy.append(" \n order by vl.loanSubTypeId " + strDesc);
				break;
			case 4:
				m_sbOrderBy.append(" \n order by vl.borrowclientid" + strDesc);
				break;
			case 5:
				m_sbOrderBy.append(" \n order by vl.contractcode " + strDesc);
				break;
			case 6:
				m_sbOrderBy.append(" \n order by vl.code " + strDesc);
				break;
			case 7:
				m_sbOrderBy.append(" \n order by vl.inputuserid " + strDesc);
				break;
			case 8:
				m_sbOrderBy.append(" \n order by vl.inputdate " + strDesc);
				break;
			}
		} else {
			m_sbOrderBy.append(" \n order by vl.inputdate desc");
		}
	}

	/**
	 * 查询已办业务
	 * 
	 * @param loanMyWorkInfo
	 * @return
	 * @throws Exception
	 */
	private PageLoader loadFinishedWorkList(LoanMyWorkInfo loanMyWorkInfo)
			throws Exception {

		getFinishedWorkSql(loanMyWorkInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
				.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(new AppContext(), m_sbFrom.toString(),
				m_sbSelect.toString(), m_sbWhere.toString(),
				(int) Constant.PageControl.CODE_PAGELINECOUNT,
				"com.iss.itreasury.loan.mywork.dataentity.AfterCreditWorkInfo",
				null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}

	/**
	 * 查询已办业务SQL
	 * 
	 * @param loanMyWorkInfo
	 */
	protected void getFinishedWorkSql(LoanMyWorkInfo loanMyWorkInfo) {
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" vl.* ,re.approvalentryid approvalEntryID, \n ");
		m_sbSelect
				.append(" his.stepname stepName,his.modelname wfDefineName,re.linkurl linkUrl,his.caller owner, \n ");
		m_sbSelect
				.append(" his.entry_id entryID,his.action_code actionCode,his.step_code stepCode,u.sname as inputusername,c.sname as clientname,f.scontractcode as contractcode \n ");
		// from
		m_sbFrom = new StringBuffer();
		m_sbFrom
				.append("  v_os_histroystep his,sys_approvalrecord re,LOAN_LOANAFTERCHECKREPORT vl,userinfo u,client c,loan_contractform f \n");
		m_sbFrom.append(" ,(SELECT max(his.id) hisid  from v_os_histroystep his,sys_approvalrecord re,LOAN_LOANAFTERCHECKREPORT vl,userinfo u,client c,loan_contractform f \n");
		m_sbFrom.append(" WHERE his.entry_id = re.approvalentryid and f.id=vl.loancontractid and c.id=vl.clientid and u.id=vl.inputuserid \n");
		m_sbFrom.append(" and re.transid = vl.Id \n");
		m_sbFrom.append("  and re.officeid=" + loanMyWorkInfo.getOfficeID()+ " \n");
		m_sbFrom.append(" and re.currencyid=" + loanMyWorkInfo.getCurrencyID()
				+ " \n");
		m_sbFrom.append(" and re.moduleid=" + loanMyWorkInfo.getModuleID()
				+ " \n");
		m_sbFrom.append(" and (vl.inputuserid = '"+loanMyWorkInfo.getUserID()+"' or his.caller='"+loanMyWorkInfo.getUserID()+"') \n");
		m_sbFrom.append(" and re.actionId = " + ApprovalAction.DH_1 + " \n");
		m_sbFrom.append("  group by his.entry_id ) maxhis\n");
		// where
		m_sbWhere = new StringBuffer();
		m_sbWhere
				.append("  his.entry_id = re.approvalentryid and f.id=vl.loancontractid and c.id=vl.clientid and u.id=vl.inputuserid \n");
		m_sbWhere.append(" and re.transid = vl.Id \n");
		m_sbWhere.append(" and re.officeid=" + loanMyWorkInfo.getOfficeID()
				+ " \n");
		m_sbWhere.append(" and re.currencyid=" + loanMyWorkInfo.getCurrencyID()
				+ " \n");
		m_sbWhere.append(" and re.moduleid=" + loanMyWorkInfo.getModuleID()
				+ " \n");
		m_sbWhere.append(" and (vl.inputuserid = '"+loanMyWorkInfo.getUserID()+"' or his.caller='"+loanMyWorkInfo.getUserID()+"') \n");
		m_sbWhere.append(" and re.actionId = " + ApprovalAction.DH_1 + " \n");
		m_sbWhere.append(" and maxhis.hisid = his.id ");

		m_sbOrderBy = new StringBuffer();
		String strDesc = loanMyWorkInfo.getDesc() == 2 ? " desc " : " asc";
		long lOrderField = loanMyWorkInfo.getOrderField();
		if (lOrderField > 0) {
			switch ((int) lOrderField) {
			case 1:
				m_sbOrderBy.append(" \n order by vl.loanTypeId " + strDesc);
				break;
			case 2:
				m_sbOrderBy.append(" \n order by vl.loanSubTypeId " + strDesc);
				break;
			case 3:
				m_sbOrderBy.append(" \n order by vl.actionId " + strDesc);
				break;
			case 4:
				m_sbOrderBy.append(" \n order by vl.borrowclientid " + strDesc);
				break;
			case 5:
				m_sbOrderBy.append(" \n order by vl.contractcode " + strDesc);
				break;
			case 6:
				m_sbOrderBy.append(" \n order by vl.code " + strDesc);
				break;
			case 7:
				m_sbOrderBy.append(" \n order by his.owner " + strDesc);
				break;
			case 8:
				m_sbOrderBy.append(" \n order by vl.inputuserId " + strDesc);
				break;
			case 9:
				m_sbOrderBy.append(" \n order by vl.inputdate " + strDesc);
				break;
			}
		} else {
			m_sbOrderBy.append(" \n order by vl.inputdate desc");
		}
	}

	/**
	 * 待审批业务
	 * 
	 * @param loanMyWorkInfo
	 * @return
	 */
	private Collection loadCurrentWorkList(LoanMyWorkInfo loanMyWorkInfo)
			throws IException {
		try {
			// 构造查询类
			InutParameterInfo pInfo = new InutParameterInfo();
			pInfo.setUserID(loanMyWorkInfo.getUserID());
			// 调用待办业务查询接口
			HashMap hm = new HashMap();

			hm = FSWorkflowManager.getCurrentList(pInfo);

			if (hm != null && hm.size() > 0) {
				loanMyWorkInfo.setApprovalEntryIDs(mergeString(hm.keySet()
						.toArray()));
				loanMyWorkInfo.setWorkList(hm);

				return queryCurrentWork(loanMyWorkInfo);
			}

		} catch (Exception e) {
			throw new IException("查询待办任务失败", e);
		}
		return new Vector();
	}

	/**
	 * 得到待审批业务列表
	 * 
	 * @param loanMyWorkInfo
	 * @return
	 * @throws IException
	 */
	protected Collection queryCurrentWork(LoanMyWorkInfo loanMyWorkInfo)
			throws IException {
		Vector v_Return = new Vector();
		StringBuffer sbSQL = new StringBuffer("");

		sbSQL
				.append("select f.id as contractid,c.id as clientid,f.nsubtypeid,ar.*,vl.id as rid,vl.status,vl.inputdate,vl.advice,vl.checkType,vl.checkYear, vl.checkreportcode,f.scontractcode,vl.inputuserid,u.sname as inputUserName,c.sname");
		sbSQL
				.append(" from sys_approvalrecord ar,loan_loanaftercheckreport vl,userinfo u,client c,loan_contractform f ");
		sbSQL.append(" where ar.moduleid = " + Constant.ModuleType.LOAN);
		sbSQL
				.append(" and f.id = vl.LOANCONTRACTID and c.id = vl.clientid and ar.OfficeID = "
						+ loanMyWorkInfo.getOfficeID());
		sbSQL.append(" and vl.inputuserid=u.id and ar.currencyid = "
				+ loanMyWorkInfo.getCurrencyID());
		sbSQL.append(" and ar.transId = vl.Id  and ar.actionId = "
				+ ApprovalAction.DH_1);
		sbSQL.append(" and ar.approvalentryid in (");
		sbSQL.append(loanMyWorkInfo.getApprovalEntryIDs() + ")");
		sbSQL.append(" order by ar.id desc ");

		System.out.println("待办业务-贷后调查报告====" + sbSQL);
		try {
			initDAO();
			prepareStatement(sbSQL.toString());
			executeQuery();

			while (transRS.next()) {
				AfterCreditWorkInfo workInfo = new AfterCreditWorkInfo();

				workInfo.setId(transRS.getLong("rid"));
				workInfo.setCurrencyID(transRS.getLong("currencyid"));
				workInfo.setOfficeID(transRS.getLong("officeid"));
				workInfo.setContractCode(transRS.getString("scontractcode"));
				workInfo.setInputDate(transRS.getTimestamp("inputdate"));
				workInfo.setStatus(transRS.getLong("status"));
				workInfo.setClientName(transRS.getString("sname"));
				workInfo.setInputUserName(transRS.getString("inputUserName"));
				workInfo.setAdvice(transRS.getLong("advice"));
				workInfo.setCheckType(transRS.getLong("checkType"));
				workInfo.setCheckYear(transRS.getString("checkYear"));
				workInfo.setApprovalEntryID(transRS.getLong("ApprovalEntryID"));
				workInfo.setLinkUrl(transRS.getString("LinkUrl"));
				workInfo.setCheckReportCode(transRS
						.getString("checkreportcode"));
				workInfo.setContractID(transRS.getLong("contractid"));
				workInfo.setClientid(transRS.getLong("clientid"));
				// 对应的审批流引擎的待办信息
				workInfo.setInutWorkInfo((InutApprovalWorkInfo) loanMyWorkInfo
						.getWorkList().get(
								String.valueOf(workInfo.getApprovalEntryID())));

				v_Return.add(workInfo);
			}

		} catch (Exception e) {
			throw new IException("Gen_E001", e);
		} finally {
			finalizeDAO();
		}
		return v_Return;
	}

	/**
	 * 待提交业务
	 * 
	 * @param loanMyWorkInfo
	 * @return
	 * @throws IException
	 */
	protected Collection queryWaitDealWithWork(LoanMyWorkInfo loanMyWorkInfo)
			throws IException {
		Vector v_Return = new Vector();
		String sql = "select f.nsubtypeid,r.status,r.inputdate,c.sname,r.id,r.advice,r.checkType,r.checkYear, r.checkreportcode,f.scontractcode,r.inputuserid,u.sname as uname from loan_loanaftercheckreport r,"
				+ " client c, loan_contractform f,userinfo u where r.status =1"
				+ " and r.clientid =c.id and r.LOANCONTRACTID=f.id and r.inputuserid=u.id "
				+ " and r.currencyid="
				+ loanMyWorkInfo.getCurrencyID()
				+ " and r.officeid=" + loanMyWorkInfo.getOfficeID()+" and r.INPUTUSERID="+loanMyWorkInfo.getUserID();

		try {
			initDAO();
			prepareStatement(sql);
			executeQuery();

			while (transRS.next()) {
				AfterCreditWorkInfo workInfo = new AfterCreditWorkInfo();

				// 贷后调查报告
				workInfo.setId(transRS.getLong("id"));
				workInfo.setCheckReportCode(transRS
						.getString("checkreportcode"));
				workInfo.setCheckType(transRS.getLong("checkType"));
				workInfo.setCheckYear(transRS.getString("checkYear"));
				workInfo.setContractCode(transRS.getString("scontractcode"));
				workInfo.setInputUserName(transRS.getString("uname"));
				workInfo.setInputDate(transRS.getTimestamp("inputdate"));
				workInfo.setClientName(transRS.getString("sname"));
				workInfo.setAdvice(transRS.getLong("advice"));
				workInfo.setStatus(transRS.getLong("status"));
				workInfo.setLoanSubTypeId(transRS.getLong("nsubtypeid"));
				v_Return.add(workInfo);
			}
		} catch (Exception e) {
			throw new IException("Gen_E001", e);
		} finally {
			finalizeDAO();
		}
		return v_Return;
	}
	/**
	 * 取消申请时执行此方法
	 * @param info
	 * @return
	 */
	public long cancelApply(AfterCreditReportInfo info){
		long flat = -1;

		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;

		try {
			log4j.info("\n取消申请方法开始");
			conn = Database.getConnection();

			strSQL = "update loan_loanaftercheckreport " + " set Status=? "
					+ " where ID=? ";
			System.out.println("O_____________________" + strSQL);
			ps = conn.prepareStatement(strSQL);
			int n = 1;
			ps.setLong(n++, LOANConstant.AfterCreditStatus.INVALID);
			ps.setLong(n++, info.getId());
			ps.executeUpdate();
			log4j.info("\n=======取消申请方法结束====");
			cleanup(ps);
			cleanup(conn);

			if (flat < 0) {
				log.info("update  fail:" + flat);
				return -1;
			} else {
				return flat;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			try {
				cleanup(ps);
				cleanup(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * 审批通过或者拒绝时执行
	 * 
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long doApproval(AfterCreditReportInfo info) throws RemoteException,
			IRollbackException {
		long lId = info.getId();
		long lReturn = -1;
		InutParameterInfo pInfo = info.getInutParameterInfo();
		try {

			// 提交审批
			pInfo.setDataEntity(info);
			InutParameterInfo _inutParameterInfo = FSWorkflowManager
					.doApproval(pInfo);
			// 如果是最后一级,且为审批通过,更新状态为已审批
			if (_inutParameterInfo.isLastLevel()) {

				this.updateCheckStatus(lId,
						LOANConstant.AfterCreditStatus.CHECK, info
								.getContractID(), info.getAdvice(),info.getInputuserid(), 1,info.getEffectDate());
				lReturn = LOANConstant.AfterCreditStatus.CHECK;

			}
			// 如果是最后一级,且为审批拒绝,更新状态为已保存
			else if (_inutParameterInfo.isRefuse()) {
				this.updateCheckStatus(lId,
						LOANConstant.AfterCreditStatus.SUBMIT, info
								.getContractID(), info.getAdvice(),info.getInputuserid(), 2,info.getEffectDate());
				lReturn = LOANConstant.AfterCreditStatus.REFUSE;
			} else {
				lReturn = LOANConstant.AfterCreditStatus.APPROVALING;
			}

		} catch (Exception e) {

			throw new IRollbackException(context, e.getMessage(), e);
		}
		return lReturn;
	}

	/**
	 * 审批后更新状态时执行
	 * 
	 * @param lID
	 *            loan_loanaftercheckreport主键
	 * @param lCheckStatus
	 *            状态
	 * @param contractID
	 *            合同ID
	 * @param flag
	 *            1-审批通过 2-审批拒绝 3-取消审批
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long updateCheckStatus(long lID, long lCheckStatus, long contractID,
			long riskLevel,long userid, long flag,Timestamp effectDate) throws RemoteException,
			IRollbackException {
		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		ContractDao contract = new ContractDao();
		RiskLevelInfo riskInfo = new RiskLevelInfo();
		long oldRiskLevel = -1;
		long newRiskLevel = riskLevel;
		int lReturn = 0;
		try {
			conn = Database.getConnection();
			strSQL = " Update loan_loanaftercheckreport Set STATUS=?  Where ID=?";
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, lCheckStatus);
			ps.setLong(2, lID);
			lReturn = ps.executeUpdate();
			ps.close();
			ps = null;
			if (flag == 1) {
				riskInfo = contract.getRiskLevelByID(contractID);
				oldRiskLevel = riskInfo.lContractOldRiskLevel;
				RiskLevelInfo riskInfo2 = new RiskLevelInfo();
				riskInfo2.lContractOldRiskLevel = oldRiskLevel;
				riskInfo2.lContractNewRiskLevel = riskLevel;
				riskInfo2.m_dtInputDate = Env.getSystemDate();
				riskInfo2.m_effectDate = effectDate;
				riskInfo2.m_lID = -1;
				riskInfo2.m_lContractID=contractID;
				riskInfo2.m_lStatusID = 3;//代表审批通过
				riskInfo2.m_lInputUserID = userid;
				riskInfo2.officeid = riskInfo.officeid;
				riskInfo2.currencyid = riskInfo.currencyid;
				this.saveRiskLevel(riskInfo2);
			}
			if(flag==3){
				riskInfo = contract.getOldRiskLevelByID(contractID);
				newRiskLevel = riskInfo.lContractOldRiskLevel;//给newRiskLevel重新赋值
				//RiskModifyStatus
				this.saveRiskLevel(riskInfo);
			}
			strSQL = "UPDATE loan_contractform SET ncreditcheckreportid = "
				+ lID + ",nrisklevel=" + newRiskLevel + " WHERE id = "
				+ contractID + "";
			System.out.println("the contractid is " + contractID);
			log4j.info(strSQL);
	
			ps = conn.prepareStatement(strSQL);
	
			lReturn = ps.executeUpdate();
			ps.close();
			ps = null;
			
			
			conn.close();
			conn = null;
		} catch (Exception e) {
			e.printStackTrace();

			throw new IRollbackException(context, e.getMessage(), e);
		} finally {
			try {
				if (ps != null)
					ps.close();
				ps = null;
				if (conn != null)
					conn.close();
				conn = null;
			} catch (Exception ex) {

				throw new IRollbackException(context, ex.getMessage(), ex);
			}
		}

		return new Long(lReturn).longValue();
	}
	/**
	 * 插入到loan_risklevel中
	 * @param lID loan_risklevel主键
	 * @param lOfficeID offcieID
	 * @param lCurrencyID  币种ID
	 * @param lContractID  合同ID
	 * @param lOldRiskLevelID  旧风险状态
	 * @param lNewRiskLevelID  新风险状态
	 * @param lUserID  操作员
	 * @param tsDate   录入日期
	 * @param strChangeReason 更改原因
	 * @param effectDate  生效日
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long saveRiskLevel(RiskLevelInfo riskInfo)
			throws RemoteException, IRollbackException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1, lMaxID = -1;
		try {
			if (riskInfo.m_lID < 0) // 新增
			{
				conn = Database.getConnection();
				// 获得最大ID＋1
				strSQL = " select nvl(max(id),0)+1 from loan_risklevel ";
				ps = conn.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs.next()) {
					lMaxID = rs.getLong(1);
					lResult = lMaxID;
					rs.close();
					rs = null;
					ps.close();
					ps = null;
				} else {
					rs.close();
					rs = null;
					ps.close();
					ps = null;
					conn.close();
					conn = null;
					return -1;
				}

				strSQL = "insert into loan_risklevel(id,ncontractid,"
						+ " ninputuserid,dtinputdate,NOLDLEVEL,NCHANGELEVEL,"
						+ " nstatusid,nnextcheckuserid,schangereason,nNextCheckLevel,"
						+ " NCURRENCYID,NOFFICEID,EFFECTDATE) "
						+ " values(?,?,?,sysdate,?,?,?,?,?,1,?,?,?)";
				ps = conn.prepareStatement(strSQL);
				ps.setLong(1, lMaxID);
				ps.setLong(2, riskInfo.m_lContractID);
				ps.setLong(3, riskInfo.m_lInputUserID);
				ps.setLong(4, riskInfo.lContractOldRiskLevel);
				ps.setLong(5, riskInfo.lContractNewRiskLevel);
				ps.setLong(6, riskInfo.m_lStatusID);
				ps.setLong(7, -123);//标志是从贷后调查报告增加过来的
				ps.setString(8, "贷后调查报告增加");
				ps.setLong(9, riskInfo.currencyid);
				ps.setLong(10, riskInfo.officeid);
				ps.setTimestamp(11, riskInfo.m_effectDate);
				if ((lResult = ps.executeUpdate()) < 1) {
					System.out.println("error.insert.loan_risklevel");
				} else {
					lResult = lMaxID;
				}
				ps.close();
				ps = null;

			} else // 修改
			{
				conn = Database.getConnection();
				strSQL = "update loan_risklevel set NSTATUSID = ? "
						+ " where id = ?";//将loan_risklevel表中数据置为无效。
				ps = conn.prepareStatement(strSQL);
				ps.setLong(1, LOANConstant.RiskModifyStatus.cancelApproval);
				ps.setLong(2, riskInfo.m_lID);
				if ((lResult = ps.executeUpdate()) < 1) {
					System.out.println("error.update.loan_risklevel");
				} else {
					lResult = riskInfo.m_lID;
				}
				ps.close();
				ps = null;
				
				conn.close();
				conn = null;

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			//modified by mzh_fu 2007/08/08
			//throw new RemoteException(ex.getMessage());
			throw new IRollbackException(context, ex.getMessage(), ex);
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
				if (conn != null) {
					conn.close();
					conn = null;
				}

			} catch (Exception ex) {
				//modified by mzh_fu 2007/08/08
				//throw new RemoteException(ex.getMessage());
				throw new IRollbackException(context, ex.getMessage(), ex);
			}
		}
		return lResult;
	}
	
	public PageLoader queryReportAddSearch(long checkfrequency, long borrowclientid,
			String checkyear, String checkqm, String checktemp,
			long inputUserID, long nCurrencyID, long nOfficeID)throws Exception {
			String[] sql = getReportAddSearchSQL( checkfrequency,  borrowclientid,checkyear,  checkqm,checktemp,inputUserID, nCurrencyID, nOfficeID);
				PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
				pageLoader.initPageLoader(
				new AppContext(),
				sql[1],
				sql[0],
				sql[2],
				(int) Constant.PageControl.CODE_PAGELINECOUNT,
				"com.iss.itreasury.loan.checkfrequency.dataentity.ContractFrequencyInfo",
				null);
				pageLoader.setOrderBy(" " + sql[3] + " ");
				return pageLoader;
	}
	
	
	
	private String[] getReportAddSearchSQL(long checkfrequency, long borrowclientid,
			String checkyear, String checkqm, String checktemp,
			long inputUserID, long nCurrencyID, long nOfficeID) {

		
		String[] mainTenanceSQL = new String[4];
		StringBuffer sbSQL1 = new StringBuffer();
		StringBuffer sbSQL2 = new StringBuffer();
		StringBuffer sbSQL3 = new StringBuffer();
		
		sbSQL1.append(" c.sname as sname,type.name as subTypeName,ct.id as id,ct.Nloanid as nloanid,ct.Scontractcode as scontractcode,ct.NSUBTYPEID as nsubtypeid,ct.Mloanamount as mloanamount,ct.Nrisklevel as nrisklevel,ct.Dtenddate as dtenddate, ct.Nborrowclientid as nborrowclientid,ct.Dtstartdate as dtstartdate,ct.Checkfrequency as checkfrequency ,e.checkreportcode as checkreportcode,e.id as ncreditcheckreportid");
         
	   

		
		if (!checkyear.equals("")) {
			sbSQL2.append(" and  d.checkyear = "+checkyear);
		}

		if (!checkqm.equals("")) {
			sbSQL2.append(" and  d.checkqm = "+checkqm);
		}
		if (!checktemp.equals("")) {
			sbSQL2.append(" and  TRUNC(d.checktemp) = +To_Date('"+checktemp+"','yyyy-mm-dd')");
		}
		
		sbSQL3.append("   ct.nstatusid >= "+ LOANConstant.ContractStatus.ACTIVE);
		sbSQL3.append("  and ct.nstatusid <= "+ LOANConstant.ContractStatus.BADDEBT);
		sbSQL3.append("  and ct.ntypeid in(1,2,3,4,8,80,200) ");
		sbSQL3.append(" and ct.Nborrowclientid=c.ID ");
		sbSQL3.append(" and  checkfrequency= "+checkfrequency);
		sbSQL3.append(" and  ct.id =e.loancontractid(+) ");

		if (borrowclientid > 0) {
			sbSQL3.append(" and  nborrowclientid= "+borrowclientid);
		}

		sbSQL3.append(" and ct.ninputuserid = "+inputUserID+" and ct.NOFFICEID= "+nOfficeID +" and ct.NCURRENCYID="+nCurrencyID+" and ct.nsubtypeid=type.id ");
		mainTenanceSQL[1] = " loan_contractform  ct ,client c ,loan_loantypesetting type,(select * from loan_loanaftercheckreport d where 1=1 and  d.checktype ="+ checkfrequency+ " and  d.status not in (0,6,-1) "+sbSQL2.toString()+" )e";
		mainTenanceSQL[0] = sbSQL1.toString();

		mainTenanceSQL[2] = sbSQL3.toString();

		mainTenanceSQL[3] = "";

		return mainTenanceSQL;
	}

	

}
