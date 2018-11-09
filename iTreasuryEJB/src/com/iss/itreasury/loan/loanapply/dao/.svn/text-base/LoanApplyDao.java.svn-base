package com.iss.itreasury.loan.loanapply.dao;

/**
 * <p>Title: iTreasury </p> 
 * <p>Description: 贷款申请所涉及的DB操作</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: iSoftstone</p>
 * @Author: gump
 * @version 1.0
 * @Date: 2003-09-25
 */

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.loanapply.dataentity.LoanApplyInfo;
import com.iss.itreasury.loan.loanapply.dataentity.AssureInfo;
import com.iss.itreasury.loan.loanapply.dataentity.LoanBasicInfo;
import com.iss.itreasury.loan.loanapply.dataentity.LoanCreateInfo;
import com.iss.itreasury.loan.loanapply.dataentity.LoanExaminePassInfo;
import com.iss.itreasury.loan.loanapply.dataentity.LoanPropertyInfo;
import com.iss.itreasury.loan.loanapply.dataentity.LoanQueryInfo;
import com.iss.itreasury.loan.obinterface.dao.OBLoanDao;
import com.iss.itreasury.loan.setting.dao.LoanTypeRelationDao;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.system.bizdelegation.ApprovalDelegation;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.CreateCodeManager;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;

public class LoanApplyDao {

	// ///////////////////////////////////////////////////////////////////////
	private Log4j log = new Log4j(Constant.ModuleType.LOAN, this);

	private void cleanup(ResultSet rs) throws SQLException {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException sqle) {
		}
	}

	private void cleanup(CallableStatement cs) throws SQLException {
		try {
			if (cs != null) {
				cs.close();
				cs = null;
			}
		} catch (SQLException sqle) {
		}
	}

	private void cleanup(PreparedStatement ps) throws SQLException {
		try {
			if (ps != null) {
				ps.close();
				ps = null;
			}
		} catch (SQLException sqle) {
		}
	}

	private void cleanup(Connection con) throws SQLException {
		try {
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (SQLException sqle) {
		}
	}

	// ///////////////////////////////////////////////////////////////////////////

	/**
	 * <ol>
	 * <b>保存贷款初始信息</b>
	 * <ul>
	 * <li>操作数据库表LoanInfo
	 * <li>根据LoanInfo中的LoanID来决定是新增还是修改
	 * <li>如果是新增，首先获得loanID和获取申请号
	 * <li>如果是修改，则根据loanID来更新纪录
	 * </ul>
	 * </ol>
	 * 
	 * @param cInfo
	 *            LoanCreateInfo
	 * @return long 成功返回1，失败返回0
	 */
	public long saveLoanCreate(LoanCreateInfo cInfo) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long loanID = -1;
		String tmpApplyCode = "";
		String applyCode = "";
		long lResult = -1;

		try {
			conn = Database.getConnection();
			if (cInfo.getLoanID() <= 0) // 新建贷款
			{
				// 首先得到现有贷款的最大ID
				strSQL = "select nvl(max(ID)+1,1) oID from loan_LoanForm";
				ps = conn.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs.next()) {
					loanID = rs.getLong("oID");
				}
				cleanup(rs);
				cleanup(ps);
				if (loanID < 1) {
					log.info("Create Loan Error:get next ID:" + loanID);
					return -1;
				} else {
					log.info("Create Loan:get next ID:" + loanID);
				}

				// 获得申请书编号
//				strSQL = " select to_char(sysdate,'yy') from dual ";
//				String sYear = "";
				String curID = "";
				// long lCurID=cInfo.getCurrencyID();
				long lTypeID = cInfo.getSubTypeId();

				// curID = LOANConstant.LoanType.getTypeCode(lTypeID);
				// curID = LOANNameRef.getSubLoanTypeCodeByID(lTypeID);
				
				//获得申请书编号 add by zcwang 2007-8-1
				curID = LOANConstant.SubLoanType.getCode(lTypeID);// lSubTypeID
				HashMap map = new HashMap();
				map.put("officeID",String.valueOf(cInfo.getOfficeID()));
				map.put("currencyID",String.valueOf(cInfo.getCurrencyID()));
				map.put("moduleID",String.valueOf(Constant.ModuleType.LOAN));
				map.put("transTypeID",String.valueOf(cInfo.getTypeID()));
				map.put("actionID",String.valueOf(Constant.CodingruleAction.LOAN_APPLY));
				map.put("subTransTypeID",String.valueOf(cInfo.getSubTypeId()));
				//map.put("clientID",rs.getObject("NBORROWCLIENTID").toString());
				applyCode=CreateCodeManager.createCode(map);
				cInfo.setApplyCode(applyCode);   //回填申请书编号
				//
/*																	// TODO

				ps = conn.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs.next()) {
					sYear = rs.getString(1);
				}
				cleanup(rs);
				cleanup(ps);

				log.info("sYear:" + sYear + " curID:" + curID);
				System.out.println("sYear:" + sYear + " curID:" + curID);
				int nLen = curID.length() + sYear.length() + 1;

				strSQL = "select nvl(max(substr(sApplyCode,"
						+ (nLen + 1)
						+ ",3)),0)+1 from loan_LoanForm where sApplyCode like 'A"
						+ sYear + curID + "%'";
				ps = conn.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs.next()) {
					long ll = rs.getLong(1);

					if (ll < 10) {
						applyCode = "A" + sYear + curID + "00" + ll;
					} else if (ll < 100) {
						applyCode = "A" + sYear + curID + "0" + ll;
					} else {
						applyCode = "A" + sYear + curID + ll;
					}

				}
				cleanup(rs);
				cleanup(ps);
*/
				log.info("Create loan:get applyCode:" + applyCode);
				System.out.println("Create loan:get applyCode:" + applyCode);

				// 插入贷款新建信息
				strSQL = "Insert into loan_LoanForm(ID, NTYPEID, NCURRENCYID, NOFFICEID, SAPPLYCODE,NCONSIGNCLIENTID, "
						+ "NBORROWCLIENTID, MLOANAMOUNT,SLOANREASON, SLOANPURPOSE, SOTHER, "
						+ "NISCIRCLE,NISSALEBUY, NISTECHNICAL,NINPUTUSERID,DTINPUTDATE, "
						+ "NISCREDIT, NISASSURE,NISIMPAWN, NISPLEDGE,NSTATUSID,nNextCheckUserID,nNextCheckLevel,mStaidAdjustRate,IsRecognizance,ASSURECHARGERATE,NINTERVALNUM"
						+ ",SellClientID "
						+ ",NSUBTYPEID,isbuyinto"
						+ " )"
						+ " values (?,?,?,?,?,?,?,0,'','','',-1,-1,-1,?,sysdate,-1,-1,-1,-1,?,?,1,0,-1,0,0,?,?,?)";

				ps = conn.prepareStatement(strSQL);
				int n = 1;
				ps.setLong(n++, loanID);
				ps.setLong(n++, cInfo.getTypeID());
				ps.setLong(n++, cInfo.getCurrencyID());
				ps.setLong(n++, cInfo.getOfficeID());
				ps.setString(n++, applyCode);
				ps.setLong(n++, cInfo.getConsignClientID());
				ps.setLong(n++, cInfo.getBorrowClientID());
				ps.setLong(n++, cInfo.getInputUserID());
				ps.setLong(n++, LOANConstant.LoanStatus.SAVE);
				ps.setLong(n++, cInfo.getInputUserID());
				ps.setLong(n++, cInfo.getSellClientID());
				ps.setLong(n++, cInfo.getSubTypeId());
				ps.setLong(n++, cInfo.getIsBuyInto());

				lResult = ps.executeUpdate();
				if (lResult < 0) {
					log.info("create a loan Failed:" + lResult);
				}
			} else {
				// 更新记录
				strSQL = "update loan_LoanForm set DINPUTDATE=sysdate";
				if (cInfo.getTypeID() > 0)
					strSQL = strSQL + " ,NTYPEID=?";
				if (cInfo.getCurrencyID() > 0)
					strSQL = strSQL + " ,NCURRENCYID=?";
				if (cInfo.getOfficeID() > 0)
					strSQL = strSQL + " ,NOFFICEID=?";
				if (cInfo.getConsignClientID() > 0)
					strSQL = strSQL + " ,NCONSIGNCLIENTID=?";
				if (cInfo.getBorrowClientID() > 0)
					strSQL = strSQL + " ,NBORROWCLIENTID=?";
				if (cInfo.getInputUserID() > 0)
					strSQL = strSQL + " ,NINPUTUSERID=?";
				if (cInfo.getSellClientID() > 0)
					strSQL = strSQL + " ,SellClientID=?";

				strSQL = strSQL + " where ID=?";
				int nindex = 1;
				ps = conn.prepareStatement(strSQL);

				if (cInfo.getTypeID() > 0)
					ps.setLong(nindex++, cInfo.getTypeID());
				if (cInfo.getCurrencyID() > 0)
					ps.setLong(nindex++, cInfo.getCurrencyID());
				if (cInfo.getOfficeID() > 0)
					ps.setLong(nindex++, cInfo.getOfficeID());
				if (cInfo.getConsignClientID() > 0)
					ps.setLong(nindex++, cInfo.getConsignClientID());
				if (cInfo.getBorrowClientID() > 0)
					ps.setLong(nindex++, cInfo.getBorrowClientID());
				if (cInfo.getInputUserID() > 0)
					ps.setLong(nindex++, cInfo.getInputUserID());
				if (cInfo.getSellClientID() > 0)
					ps.setLong(nindex++, cInfo.getSellClientID());
				ps.setLong(nindex++, cInfo.getLoanID());

				loanID = cInfo.getLoanID();
				lResult = ps.executeUpdate();
				if (lResult < 0) {
					log.info("update loan create info error:" + lResult);
				}
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);

		} catch (Exception e) {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			e.printStackTrace();
			throw e;

		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return (lResult < 0 ? lResult : loanID);
	}

	/**
	 * <ol>
	 * <b>保存贷款基本信息</b>
	 * <ul>
	 * <li>操作数据库表LoanInfo
	 * </ul>
	 * </ol>
	 * 
	 * @param bInfo
	 *            LoanBasicInfo
	 * @return long 成功返回1，失败返回0
	 */
	public long saveLoanBasic(LoanBasicInfo bInfo) throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		// ResultSet rs = null;
		String strSQL = null;
		// long lLoanYTAssignID = -1;
		// long lConYTAssignID = -1;
		long lResult = -1;

		try {
			conn = Database.getConnection();
			strSQL = " update loan_LoanForm set mLoanAmount=?, "
					+ "sLoanReason=?, sLoanPurpose=?, sOther=? ,nBankInterestID=? , "
					+ "mChargeRate=?, nIntervalNum=?, dtStartDate=?, dtEndDate=?, "
					+ "sClientInfo=?, mSelfAmount=?, mInterestRate=?,mStaidAdjustRate=? "
					+ ", ProjectInfo=?, AssureChargeRate=?, AssureChargeTypeID=? "
					+ ", Beneficiary=?, AssureTypeID1=?, AssureTypeID2=? "
					+ ", selfRate = ?,sellContractAmount = ? "
					+ ", nInterestTypeID = ?,nLiborRateID = ? "
					+ ",NINTERESTCOUNTTYPEID = ?,MCHARGEAMOUNT = ? "
					//added by xiong fei 2010/05/17
					+ ", MMATURENOMINALAMOUNT = ?,MCHARGEAMOUNTRATE = ? "
					+ ",MORIGIONAMOUNT = ?,MPREAMOUNT = ? "+ " where ID=? ";
			System.out.println(strSQL);
			ps = conn.prepareStatement(strSQL);
			int n = 1;
			ps.setDouble(n++, bInfo.getLoanAmount());
			// ps.setLong(n++,bInfo.getLoanIntervalNum() );
			ps.setString(n++, bInfo.getLoanReason());
			ps.setString(n++, bInfo.getLoanPurpose());
			ps.setString(n++, bInfo.getOther());
			ps.setLong(n++, bInfo.getBankInterestID());
			ps.setDouble(n++, bInfo.getChargeRate());
			ps.setLong(n++, bInfo.getIntervalNum());
			ps.setTimestamp(n++, bInfo.getStartDate());
			ps.setTimestamp(n++, bInfo.getEndDate());
			ps.setString(n++, bInfo.getClientInfo());
			ps.setDouble(n++, bInfo.getSaleAmount());
			ps.setDouble(n++, bInfo.getInterestRate());
			ps.setDouble(n++, bInfo.getStaidAdjustRate()); // =ninh 2004-06-22
															// 需求变更 增加固定浮动利率=//
			ps.setString(n++, bInfo.getProjectInfo());// =ninh 2004-11-22 需求变更
														// 上海电气增加项目简介=//
			ps.setDouble(n++, bInfo.getAssureChargeRate()); // 担保业务相关
			ps.setLong(n++, bInfo.getAssureChargeTypeID()); // 担保业务相关
			ps.setString(n++, bInfo.getBeneficiary()); // 担保业务相关
			ps.setLong(n++, bInfo.getAssureTypeID1()); // 担保业务相关
			ps.setLong(n++, bInfo.getAssureTypeID2()); // 担保业务相关
			ps.setDouble(n++, bInfo.getSelfRate());
			ps.setDouble(n++, bInfo.getSellContractAmount());
			ps.setLong(n++, bInfo.getInterestTypeID());
			ps.setLong(n++, bInfo.getLiborRateID());
			// 浦发融资租赁新增
			ps.setLong(n++, bInfo.getInterestCountTypeID());// 利息计算方式
			ps.setDouble(n++, bInfo.getChargeAmount());// 手续给金额
			ps.setDouble(n++, bInfo.getMatureNominalAmount());// 名义货价
			
			//added by xiong fei 2010/05/17
			ps.setDouble(n++, bInfo.getChargeAmountRate());//手续费率
			ps.setDouble(n++, bInfo.getOrigionAmount());//租赁物原价
			ps.setDouble(n++, bInfo.getPreAmount());//首付款
			
			// 浦发融资租赁新增结束
			ps.setLong(n++, bInfo.getLoanID());

			lResult = ps.executeUpdate();
			cleanup(ps);

			if (bInfo.getLoanType() == LOANConstant.LoanType.YT) {
				// loan_yt_loanformbankassign申请
				strSQL = " delete loan_yt_loanformbankassign  "
						+ " where nLoanID = ? ";
				Log.print(strSQL);
				ps = conn.prepareStatement(strSQL);
				ps.setLong(1, bInfo.getLoanID());
				ps.executeUpdate();
				cleanup(ps);
				// ---------------
				strSQL = " insert into loan_yt_loanformbankassign  "
						+ " (   mRate " + "     ,nLoanID  "
						+ "     ,sbankname  " + "     ,mcreditamount "
						+ "     ,massureamount " + "     ,nIsHead " + "   )"
						+ "  values (?,?,?,0,0,?)";
				Log.print(strSQL);
				Log.print("bInfo.getRate()=" + bInfo.getRate());
				ps = conn.prepareStatement(strSQL);
				ps.setBigDecimal(1, bInfo.getRate());
				ps.setLong(2, bInfo.getLoanID());
				ps.setString(3, Env.getClientName());
				ps.setLong(4, LOANConstant.IsHead.YES);
				// ps.setLong(5,lLoanYTAssignID);
				lResult = ps.executeUpdate();
			}
			// cleanup(rs);
			cleanup(ps);
			cleanup(conn);

			if (lResult < 0) {
				log.info("update loan basic info error:" + lResult);
				return lResult;
			} else {
				return bInfo.getLoanID();
			}

		} catch (Exception e) {
			cleanup(ps);
			cleanup(conn);
			e.printStackTrace();
			throw e;
		} finally {
			cleanup(ps);
			cleanup(conn);
		}
	}

	/**
	 * <ol>
	 * <b>保存贷款性质信息</b>
	 * <ul>
	 * <li>操作数据库表LoanInfo
	 * </ul>
	 * </ol>
	 * 
	 * @param pInfo
	 *            LoanPropertyInfo
	 * @return long 成功返回1，失败返回0
	 */
	public long saveLoanProperty(LoanPropertyInfo pInfo) throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;
		try {
			conn = Database.getConnection();
			strSQL = " update loan_LoanForm set nIsCircle=?, nIsSaleBuy=?, "
					+ "nIsTechnical=?, nIsCredit=?, nIsAssure=? ,nIsImpawn=?, nIsPledge=?, IsRecognizance=?,isRepurchase=? where ID=? ";

			ps = conn.prepareStatement(strSQL);

			int n = 1;
			ps.setLong(n++, pInfo.getIsCircle());
			ps.setLong(n++, pInfo.getIsSaleBuy());
			ps.setLong(n++, pInfo.getIsTechnical());
			ps.setLong(n++, pInfo.getIsCredit());
			ps.setLong(n++, pInfo.getIsAssure());
			ps.setLong(n++, pInfo.getIsImpawn());
			ps.setLong(n++, pInfo.getIsPledge());
			ps.setLong(n++, pInfo.getIsRecognizance());
			ps.setLong(n++, pInfo.getIsRepurchase());
			ps.setLong(n++, pInfo.getLoanID());

			lResult = ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
			if (lResult < 0) {
				log.info("update loan property info error:" + lResult);
				return -1;
			} else {
				return pInfo.getLoanID();
			}
		} catch (Exception e) {

			cleanup(ps);
			cleanup(conn);
			e.printStackTrace();
			throw e;
		} finally {
			cleanup(ps);
			cleanup(conn);
		}

	}

	public long updateLoanStatus(long loanID, long userID, long newStatusID)
			throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;

		try {
			conn = Database.getConnection();
			strSQL = " update loan_LoanForm set nStatusID=? where ID=?";

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
				log.info("update loan property info error:" + lResult);
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
	
	/**
	 * Modify by leiyang date 2007/07/27
	 * 
	 * @param loanID
	 * @param userID
	 * @param newStatusID
	 * @return
	 * @throws Exception
	 */
	public long updateStatusAndCheckStatus(long loanID, long userID, long newStatusID) throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;
		
		try {
			conn = Database.getConnection();
			strSQL = " update loan_LoanForm set nStatusID=? where ID=? and nStatusID=?";
		
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, newStatusID);
			ps.setLong(2, loanID);
			ps.setLong(3, LOANConstant.LoanStatus.CHECK);
		
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
		} catch (Exception e) {
			cleanup(ps);
			cleanup(conn);
			throw new IException("Gen_E001");
		} finally {
			cleanup(ps);
			cleanup(conn);
		}
		return lResult;
	}
	

	public long setCheckUserBack(long loanID) throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		String strSQL = null;
		long lResult = -1;
		long typeID = -1;
		long inputUserID = -1;
		long nextUserID = -1;
		try {
			conn = Database.getConnection();

			strSQL = " update loan_LoanForm set nNextCheckUserID = nInputUserID,nNextCheckLevel = 1 where ID = ? ";
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, loanID);

			lResult = ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
			if (lResult < 0) {
				log.info("setCheckUserBack error:" + lResult);
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

	public LoanApplyInfo findByID(long loanID) throws Exception {
		LoanApplyInfo applyInfo = new LoanApplyInfo();

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		String inputUserName = "";

		try {
			ContractDao dao = new ContractDao();
			conn = Database.getConnection();
			strSQL = " select a.*,b.mRate,c.scontractcode from loan_LoanForm a "
					+ " ,loan_yt_loanformbankassign b,loan_contractform c "
					+ " where a.ID=? "
					+ " and b.nLoanID(+)=a.ID "
					+ " and a.id = c.nloanid(+)"
					// + " and b.nIshead= " + LOANConstant.IsHead.YES
					+ "";
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, loanID);

			rs = ps.executeQuery();

			if (rs.next()) {
				applyInfo.setID(rs.getLong("ID"));
				log.print("find loan infoID:" + applyInfo.getID());
				System.out.println("find loan infoID:" + applyInfo.getID());
				applyInfo.setIsBuyInto(rs.getLong("isbuyinto"));
				applyInfo.setTypeID(rs.getLong("ntypeid"));
				applyInfo.setCurrencyID(rs.getLong("ncurrencyid"));
				applyInfo.setOfficeID(rs.getLong("nofficeid"));
				applyInfo.setApplyCode(rs.getString("sapplycode"));
				applyInfo.setConsignClientID(rs.getLong("nconsignclientid"));
				applyInfo.setBorrowClientID(rs.getLong("nborrowclientid"));
				applyInfo.setInputUserID(rs.getLong("ninputuserid"));
				applyInfo.setInputDate(rs.getTimestamp("dtinputdate"));
				applyInfo.setLoanAmount(rs.getDouble("mloanamount"));
				// applyInfo.setLoanIntervalNum(rs.getLong("nloanintervalnum"));
				applyInfo.setLoanReason(rs.getString("sloanreason"));
				applyInfo.setLoanPurpose(rs.getString("sloanpurpose"));
				applyInfo.setOther(rs.getString("sother"));
				applyInfo.setBankInterestID(rs.getLong("nbankinterestid"));
				applyInfo.setLiborRateID(rs.getLong("nLiborRateID"));
				applyInfo.setChargeRate(rs.getDouble("mchargerate"));
				applyInfo.setIntervalNum(rs.getLong("nintervalnum"));
				applyInfo.setStartDate(rs.getTimestamp("dtstartdate"));
				applyInfo.setEndDate(rs.getTimestamp("dtenddate"));
				applyInfo.setClientInfo(rs.getString("sclientinfo"));
				applyInfo.setProjectInfo(rs.getString("ProjectInfo"));// 项目简介
				applyInfo.setSelfAmount(rs.getDouble("mselfamount"));
				applyInfo.setExamineSelfAmount(rs.getDouble("mExamineselfamount"));
				applyInfo.setSelfScale(rs.getDouble("mRate")); // 华能承贷比例
				applyInfo.setContractCode(rs.getString("scontractcode"));
				// 担保业务相关
				applyInfo.setAssureChargeRate(rs.getDouble("AssureChargeRate")); // 担保费率
				applyInfo.setAssureChargeTypeID(rs.getLong("AssureChargeTypeID")); // 担保费收取方式
				applyInfo.setBeneficiary(rs.getString("Beneficiary")); // 受益人
				applyInfo.setAssureTypeID1(rs.getLong("AssureTypeID1")); // 担保类型1
				applyInfo.setAssureTypeID2(rs.getLong("AssureTypeID2")); // 担保类型2
				// 浦发融资租赁业务新增
				applyInfo.setInterestCountTypeID(rs.getLong("NINTERESTCOUNTTYPEID"));// 利息计算方式
				applyInfo.setChargeAmount(rs.getDouble("MCHARGEAMOUNT"));// 手续费金额
				applyInfo.setMatureNominalAmount(rs.getDouble("MMATURENOMINALAMOUNT"));// 到期名义货价
				
				//added by xiong fei 2010/05/17
				applyInfo.setChargeAmountRate(rs.getDouble("MCHARGEAMOUNTRATE"));//手续费率
				applyInfo.setOrigionAmount(rs.getDouble("MORIGIONAMOUNT"));//租赁物原价
				applyInfo.setPreAmount(rs.getDouble("MPREAMOUNT"));//首付款
				
				// 浦发融资租赁业务结束

				// RateInfo rInfo = new RateInfo();
				// rInfo = dao.getLatelyRate(loanID,-1,null);
				applyInfo.setInterestRate(rs.getDouble("mInterestRate"));

				// ======ninh 2004-06-22 需求变更 增加固定浮动利率======//
				applyInfo.setStaidAdjustRate(rs.getDouble("mStaidAdjustRate"));

				applyInfo.setIsCircle(rs.getLong("niscircle"));
				applyInfo.setIsSaleBuy(rs.getLong("nissalebuy"));
				applyInfo.setIsTechnical(rs.getLong("nistechnical"));
				applyInfo.setIsCredit(rs.getLong("niscredit"));
				applyInfo.setIsAssure(rs.getLong("nisassure"));
				applyInfo.setIsImpawn(rs.getLong("nisimpawn"));
				applyInfo.setIsPledge(rs.getLong("nispledge"));
				applyInfo.setIsRecognizance(rs.getLong("IsRecognizance"));
				applyInfo.setInterestTypeID(rs.getLong("nInterestTypeID"));
				applyInfo.setExamineAmount(rs.getDouble("mExamineAmount"));
				applyInfo.setStatusID(rs.getLong("nStatusID"));
				applyInfo.setNextCheckUserID(rs.getLong("nNextCheckUserID"));
				applyInfo.setIsCanModify(rs.getLong("IsCanModify"));
				applyInfo.setChargeRateTypeID(rs.getLong("nChargeRateTypeID"));
				applyInfo.setRiskLevel(rs.getLong("nRiskLevel"));
				applyInfo.setTypeID1(rs.getLong("nTypeID1"));
				applyInfo.setTypeID2(rs.getLong("nTypeID2"));
				applyInfo.setTypeID3(rs.getLong("nTypeID3"));
				applyInfo.setNBankAcceptPO(rs.getLong("nBankAcceptPO"));
				applyInfo.setNBizAcceptPO(rs.getLong("nBizAcceptPO"));
				applyInfo.setCheckAmount(rs.getDouble("mCheckAmount"));
				applyInfo.setDiscountRate(rs.getDouble("mDiscountRate"));
				applyInfo.setDiscountDate(rs.getTimestamp("dtDiscountDate"));
				applyInfo.setAdjustRate(rs.getDouble("mAdjustRate"));
				applyInfo.setNextCheckLevel(rs.getLong("nNextCheckLevel"));
				applyInfo.setIsLowLevel(rs.getLong("isLowLevel")); //
				applyInfo.setSubTypeId(rs.getLong("NSUBTYPEID"));// 加入子类id
				applyInfo.setSellClientID(rs.getLong("sellclientid"));
				applyInfo.setCheckReportId(rs.getLong("NCREDITCHECKREPORTID"));// 加入授信检查报告id
				applyInfo.setSelfRate(rs.getDouble("selfRate"));
				applyInfo.setIsRepurchase(rs.getLong("isRepurchase"));
				applyInfo.setSellContractAmount(rs.getDouble("SellContractAmount"));
				applyInfo.setIsRemitCompoundInterest(rs.getLong("isremitcompoundinterest"));
				applyInfo.setIsRemitOverDueInterest(rs.getLong("isremitoverdueinterest"));
				applyInfo.setOverDueAdjustRate(rs.getDouble("overdueadjustrate"));
				applyInfo.setOverDueStaidAdjustRate(rs.getDouble("overduestaidadjustrate"));
				//applyInfo.setOverDueInterestRate(applyInfo.getInterestRate() * (1 + applyInfo.getOverDueAdjustRate() / 100) + applyInfo.getOverDueStaidAdjustRate());
				//System.out.print("======"+applyInfo.getInterestRate()+"============");
				//added by mzh_fu 2007/05/30 意见
				applyInfo.setCheckOpinion(rs.getString("CHECKOPINION"));
				
				applyInfo.setAdjustRateTerm(rs.getInt("adjustrateterm"));

				FormAssureDao fadao = new FormAssureDao();
				Collection c = fadao.findByLoanID(applyInfo.getID(), 1, Constant.PageControl.CODE_ASCORDESC_DESC);
				applyInfo.setAssureInfo(c);

				LoanRepayPlanDao planDao = new LoanRepayPlanDao();
				long planCount = planDao.findCountByLoanID(loanID);
				applyInfo.setPlanDetailCount(planCount);

				long planVersion = planDao.findVersionByLoanID(loanID);
				applyInfo.setPlanVersion(planVersion);

				applyInfo.setPlanPayAmount(planDao.findSumPlanAmount(rs.getLong("ID"), LOANConstant.PlanType.PAY));
				applyInfo.setPlanRepayAmount(planDao.findSumPlanAmount(rs.getLong("ID"), LOANConstant.PlanType.REPAY));
				// System.out.println(applyInfo.getPlanPayAmount());
				// System.out.println(applyInfo.getPlanRepayAmount());
			}
			cleanup(rs);
			cleanup(ps);
			strSQL = " select sName from userInfo where ID=? ";
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, applyInfo.getInputUserID());
			rs = ps.executeQuery();
			if (rs.next()) {
				applyInfo.setInputUserName(rs.getString("sName"));
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		} catch (Exception e) {
			e.printStackTrace();
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			throw e;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return applyInfo;
	}
	
	/**
	 * 查询待办任务
	 * 
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	public Collection queryWaitOperation(LoanQueryInfo loanQueryInfo)
			throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		List lstReturn = new ArrayList();

		strSQL = "select a.*,b.name subTypeName,c.name borrowclientname from loan_loanform a ,Loan_LoanTypeSetting b ,client_clientinfo c "
				+ " where a.ncurrencyid = ? and a.nofficeid = ? "
				+ " and a.ninputuserid = ? and a.nstatusid = ? and b.id = a.nsubtypeid and c.id = a.nborrowclientid "
				+ " order by a.id desc";

		try {
			conn = Database.getConnection();
			ps = conn.prepareStatement(strSQL);

			ps.setLong(1, loanQueryInfo.getCurrencyID());
			ps.setLong(2, loanQueryInfo.getOfficeID());
			ps.setLong(3, loanQueryInfo.getInputUserID());
			ps.setLong(4, LOANConstant.LoanStatus.SAVE);

			rs = ps.executeQuery();

			while (rs != null && rs.next()) {
				LoanApplyInfo applyInfo = new LoanApplyInfo();
				applyInfo.setID(rs.getLong("ID"));
				applyInfo.setTypeID(rs.getLong("ntypeid"));
				applyInfo.setCurrencyID(rs.getLong("ncurrencyid"));
				applyInfo.setOfficeID(rs.getLong("nofficeid"));
				applyInfo.setApplyCode(rs.getString("sapplycode"));
				applyInfo.setConsignClientID(rs.getLong("nconsignclientid"));
				applyInfo.setBorrowClientID(rs.getLong("nborrowclientid"));
				applyInfo.setInputUserID(rs.getLong("ninputuserid"));
				applyInfo.setInputDate(rs.getTimestamp("dtinputdate"));
				applyInfo.setLoanAmount(rs.getDouble("mloanamount"));
				applyInfo.setLoanReason(rs.getString("sloanreason"));
				applyInfo.setLoanPurpose(rs.getString("sloanpurpose"));
				applyInfo.setOther(rs.getString("sother"));
				applyInfo.setBankInterestID(rs.getLong("nbankinterestid"));
				applyInfo.setLiborRateID(rs.getLong("NLIBORRATEID"));
				applyInfo.setChargeRate(rs.getDouble("mchargerate"));
				applyInfo.setIntervalNum(rs.getLong("nintervalnum"));
				applyInfo.setStartDate(rs.getTimestamp("dtstartdate"));
				applyInfo.setEndDate(rs.getTimestamp("dtenddate"));
				applyInfo.setClientInfo(rs.getString("sclientinfo"));
				applyInfo.setSelfAmount(rs.getDouble("mselfamount"));
				applyInfo.setInterestRate(rs.getDouble("minterestrate"));
				applyInfo.setIsCircle(rs.getLong("niscircle"));
				applyInfo.setIsSaleBuy(rs.getLong("nissalebuy"));
				applyInfo.setIsTechnical(rs.getLong("nistechnical"));
				applyInfo.setIsCredit(rs.getLong("niscredit"));
				applyInfo.setIsAssure(rs.getLong("nisassure"));
				applyInfo.setIsImpawn(rs.getLong("nisimpawn"));
				applyInfo.setIsPledge(rs.getLong("nispledge"));
				applyInfo.setIsRecognizance(rs.getLong("IsRecognizance"));
				applyInfo.setInterestTypeID(rs.getLong("nInterestTypeID"));
				applyInfo.setExamineAmount(rs.getDouble("mExamineAmount"));
				applyInfo.setStatusID(rs.getLong("nStatusID"));
				applyInfo.setNextCheckUserID(rs.getLong("nNextCheckUserID"));
				applyInfo.setIsCanModify(rs.getLong("IsCanModify"));
				applyInfo.setChargeRateTypeID(rs.getLong("nChargeRateTypeID"));
				applyInfo.setRiskLevel(rs.getLong("nRiskLevel"));
				applyInfo.setTypeID1(rs.getLong("nTypeID1"));
				applyInfo.setTypeID2(rs.getLong("nTypeID2"));
				applyInfo.setTypeID3(rs.getLong("nTypeID3"));
				applyInfo.setNBankAcceptPO(rs.getLong("nBankAcceptPO"));
				applyInfo.setNBizAcceptPO(rs.getLong("nBizAcceptPO"));
				applyInfo.setCheckAmount(rs.getDouble("mCheckAmount"));
				applyInfo.setDiscountRate(rs.getDouble("mDiscountRate"));
				applyInfo.setDiscountDate(rs.getTimestamp("dtDiscountDate"));
				applyInfo.setAdjustRate(rs.getDouble("mAdjustRate"));
				applyInfo.setNextCheckLevel(rs.getLong("nNextCheckLevel"));
				// 担保业务相关
				applyInfo.setAssureChargeRate(rs.getDouble("AssureChargeRate")); // 担保费率
				applyInfo.setAssureChargeTypeID(rs
						.getLong("AssureChargeTypeID")); // 担保费收取方式
				applyInfo.setBeneficiary(rs.getString("Beneficiary")); // 受益人
				applyInfo.setAssureTypeID1(rs.getLong("AssureTypeID1")); // 担保类型1
				applyInfo.setAssureTypeID2(rs.getLong("AssureTypeID2")); // 担保类型2
				applyInfo.setSubTypeId(rs.getLong("NSUBTYPEID"));

				// ======ninh 2004-06-22 需求变更 增加固定浮动利率======//
				applyInfo.setStaidAdjustRate(rs.getDouble("mStaidAdjustRate"));
				
				applyInfo.setSubTypeName(rs.getString("SUBTYPENAME"));
				applyInfo.setBorrowClientName(rs.getString("borrowclientname"));
				

				lstReturn.add(applyInfo);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return lstReturn;
	}
	
	public Collection query(LoanQueryInfo qInfo) throws Exception {
		/*
		 * modify by wjliu 改了页面上贷款模块中申请书编号查询的方式,之前是以申请书编号的ID区间做为查询条件
		 * 现在加一个以编号的区间做查询条件 2007/3/5
		 */
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		String strSelect = null;
		String strSQL = null;
		Vector v = new Vector();

		long loanType = qInfo.getLoanType();
		long consignClientID = qInfo.getConsignClientID();
		long borrowClientID = qInfo.getBorrowClientID();
		long startID = qInfo.getStartID();
		long endID = qInfo.getEndID();
		String startApplyCode = qInfo.getStartApplyCode();
		String endApplyCode = qInfo.getEndApplyCode();
		long intervalNum = qInfo.getIntervalNum();
		double startAmount = qInfo.getStartAmount();
		double endAmount = qInfo.getEndAmount();
		Timestamp startDate = qInfo.getStartDate();
		Timestamp endDate = qInfo.getEndDate();
		long statusID = qInfo.getStatusID();
		long inputUserID = qInfo.getInputUserID();
		long nextUserID = qInfo.getNextCheckUserID();
		long queryPurpose = qInfo.getQueryPurpose();
		long currencyID = qInfo.getCurrencyID();
		long officeID = qInfo.getOfficeID();

		long pageLineCount = qInfo.getPageLineCount();
		long pageNo = qInfo.getPageNo();
		long orderParam = qInfo.getOrderParam();
		long desc = qInfo.getDesc();

		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		long pageInfo[] = new long[2];
		long approvalID = -1;

		long lModuleID = Constant.ModuleType.LOAN;
		long lActionID = Constant.ApprovalAction.LOAN_APPLY;
		ApprovalDelegation appbiz = new ApprovalDelegation();
		LoanTypeRelationDao loanTypeDao = new LoanTypeRelationDao();
		long[] a_SubLoanType = null;

		try {
			con = Database.getConnection();
			// 计算记录总数

			if (queryPurpose == 1) // for modify
			{

				/* 取该种贷款的对应的approvalSetting ID */
				// ApprovalDelegation approvalDao = new ApprovalDelegation();
				approvalID = appbiz.getApprovalID(Constant.ModuleType.LOAN,
						loanType, Constant.ApprovalAction.LOAN_APPLY, officeID,
						currencyID);

				System.out.println("----get approvalID:" + approvalID);
				strSQL = "";
				cleanup(rs);
				cleanup(ps);
				/** ***************end of get approval Setting ID ************** */

				strSelect = " select count(*) ";
				strSQL = " from loan_loanForm aa where nStatusID>="
						+ LOANConstant.LoanStatus.SAVE + " and nInputUserID="
						+ inputUserID + " and nTypeID=" + loanType;
				strSQL += " and nCurrencyID = " + currencyID
						+ "and nOfficeID = " + officeID; // 加上币种和办事处作为查询的约束条件

				// ////////////////////查询条件////////////////////////////////////////////////////
				if ((statusID == -1) || (statusID == 0)) {
					strSQL = strSQL
							+ " and ( nStatusID="
							+ LOANConstant.LoanStatus.SAVE
							+ " or ((nStatusID="
							+ LOANConstant.LoanStatus.SUBMIT
							+ ") and ( nNextCheckLevel=1 ) ) "
							+ " or ((nStatusID="
							+ LOANConstant.LoanStatus.CHECK
							+ ") and id not in (select nLoanID from loan_contractForm cc where cc.nStatusID>="
							+ LOANConstant.ContractStatus.NOTACTIVE + " )) ) ";
				} else if (statusID == LOANConstant.LoanStatus.SUBMIT) {
					// strSQL = strSQL + " and
					// (nStatusID="+LOANConstant.LoanStatus.SUBMIT+" and
					// (nNextCheckUserID=nInputUserID )) ";
					strSQL = strSQL + " and (nStatusID="
							+ LOANConstant.LoanStatus.SUBMIT
							+ " and ( nNextCheckLevel=1 )) ";
				} else if (statusID == LOANConstant.LoanStatus.CHECK) {
					strSQL = strSQL
							+ " and (nStatusID="
							+ LOANConstant.LoanStatus.CHECK
							+ " and aa.id not in (select nLoanID from loan_contractForm cc where cc.nStatusID>="
							+ LOANConstant.ContractStatus.NOTACTIVE + " )) ";
				} else if (statusID == LOANConstant.LoanStatus.SAVE) {
					strSQL = strSQL + " and nStatusID="
							+ LOANConstant.LoanStatus.SAVE;
				}
			} else if (queryPurpose == 2) // for examine
			{
				String nextUserAndTypeSql = " ( ";
				String nextUser = "";
				/*
				 * modify by yanliu 系统升级后，贷款类型分大类型和子类型，查询的时候，查询条件中是大类型，
				 * 而得到实际审批人的方法（findTheVeryUser）中贷款类型参数是子类型，所以修改查询方法。
				 */
				// 获取所有子类型
				a_SubLoanType = loanTypeDao.getAllSetSubLoanTypeID(officeID,
						currencyID, new long[] { loanType });
				if (a_SubLoanType != null && a_SubLoanType.length > 0) {
					for (int i = 0; i < a_SubLoanType.length; i++) {
						nextUserAndTypeSql = nextUserAndTypeSql
								+ " ( nNextCheckUserID in "
								+ appbiz.findTheVeryUser(
										Constant.ModuleType.LOAN,
										a_SubLoanType[i],
										Constant.ApprovalAction.LOAN_APPLY,
										officeID, currencyID, nextUserID)
								+ " and NSUBTYPEID = " + a_SubLoanType[i] + ")";
						if (i < a_SubLoanType.length - 1)
							nextUserAndTypeSql += " or ";
						else
							nextUserAndTypeSql += " ) ";
					}
				} else {
					return null;
				}
				// nextUser = appbiz.findTheVeryUser(Constant.ModuleType.LOAN,
				// loanType,
				// Constant.ApprovalAction.LOAN_APPLY,officeID,currencyID,
				// nextUserID);

				strSelect = " select count(*) ";
				// strSQL = " from loan_loanForm aa where " + " nNextCheckUserID
				// in " + nextUser + " and nTypeID=" + loanType;
				strSQL = " from loan_loanForm aa where nTypeID=" + loanType
						+ " and " + nextUserAndTypeSql;

				if (statusID == LOANConstant.LoanStatus.SUBMIT) {
					strSQL += " and nStatusID="
							+ LOANConstant.LoanStatus.SUBMIT;
				} else if (statusID == LOANConstant.LoanStatus.CHECK) {
					strSQL += " and nStatusID=" + LOANConstant.LoanStatus.CHECK;
				} else {
					strSQL += " and (nStatusID="
							+ LOANConstant.LoanStatus.CHECK + " or nStatusID= "
							+ LOANConstant.LoanStatus.SUBMIT + ") ";
				}

				if (currencyID > 0) {
					strSQL += " and ncurrencyid = " + currencyID;
				}
				if (officeID > 0) {
					strSQL += " and NOFFICEID = " + officeID;
				}
			}

			if (consignClientID != -1) {
				strSQL = strSQL + " and nConsignClientID =" + consignClientID;
			}

			if (borrowClientID != -1) {
				strSQL = strSQL + " and nBorrowClientID =" + borrowClientID;

			}

			if (startID != -1) {
				strSQL = strSQL + " and ID > = " + startID;
			}
			if (endID != -1) {
				strSQL = strSQL + " and ID < = " + endID;
			}
			if (!"".equals(startApplyCode)) {
				strSQL = strSQL + " and sapplycode > = '" + startApplyCode
						+ "'";
			}
			if (!"".equals(endApplyCode)) {
				strSQL = strSQL + " and sapplycode < = '" + endApplyCode + "'";
			}

			if (startAmount != 0) {
				strSQL = strSQL + " and mLoanAmount>=" + startAmount;
			}

			if (endAmount != 0) {
				strSQL = strSQL + " and mLoanAmount<=" + endAmount;
			}

			if (intervalNum != -1) {
				strSQL = strSQL + " and nIntervalNum=" + intervalNum;
			}

			if (startDate != null) {
				strSQL = strSQL + " and TRUNC(dtInputDate)>= To_Date('"
						+ DataFormat.getDateString(startDate)
						+ "','yyyy-mm-dd') ";
			}

			if (endDate != null) {
				strSQL = strSQL + " and TRUNC(dtInputDate)< =To_Date('"
						+ DataFormat.getDateString(endDate)
						+ "','yyyy-mm-dd') ";
			}

			// //////////////////////////排序处理//////////////////////////////////////////////////////////////////////
			switch ((int) orderParam) {
			case 1:
				strSQL += " order by ID";
				break;
			case 2:
				strSQL += " order by nBorrowClientID";
				break;
			case 3:
				strSQL += " order by mLoanAmount";
				break;
			case 4:
				strSQL += " order by nIntervalNum";
				break;
			case 5:
				strSQL += " order by dtInputDate";
				break;
			case 6:
				strSQL += " order by nStatusID";
				break;
			case 7:
				strSQL += " order by nConsignClientID";
				break;
			case 8:
				strSQL += " order by ASSURECHARGERATE";
				break;
			default:
				strSQL += " order by ID ";
				break;
			}

			if (desc == Constant.PageControl.CODE_ASCORDESC_DESC) {
				strSQL += " desc";
			}

			System.out.println(strSelect + strSQL);
			ps = con.prepareStatement(strSelect + strSQL);
			rs = ps.executeQuery();

			if (rs != null && rs.next()) {
				lRecordCount = rs.getLong(1);
			}

			rs.close();
			rs = null;
			ps.close();
			ps = null;

			lPageCount = lRecordCount / pageLineCount;

			if ((lRecordCount % pageLineCount) != 0) {
				lPageCount++;
			}
			pageInfo[0] = lRecordCount;
			pageInfo[1] = lPageCount;
			v.add(pageInfo);

			// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// 返回需求的结果集
			lRowNumStart = (pageNo - 1) * pageLineCount + 1;
			lRowNumEnd = lRowNumStart + pageLineCount - 1;

			strSQL = "select * from  ( select aa.*,rownum r from ( select * "
					+ strSQL;
			strSQL = strSQL + " ) aa ) where r between " + lRowNumStart
					+ " and " + lRowNumEnd;

			System.out.println(strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();

			while (rs != null && rs.next()) {
				LoanApplyInfo applyInfo = new LoanApplyInfo();
				applyInfo.setID(rs.getLong("ID"));
				applyInfo.setTypeID(rs.getLong("ntypeid"));
				applyInfo.setCurrencyID(rs.getLong("ncurrencyid"));
				applyInfo.setOfficeID(rs.getLong("nofficeid"));
				applyInfo.setApplyCode(rs.getString("sapplycode"));
				applyInfo.setConsignClientID(rs.getLong("nconsignclientid"));
				applyInfo.setBorrowClientID(rs.getLong("nborrowclientid"));
				applyInfo.setInputUserID(rs.getLong("ninputuserid"));
				applyInfo.setInputDate(rs.getTimestamp("dtinputdate"));
				applyInfo.setLoanAmount(rs.getDouble("mloanamount"));
				applyInfo.setLoanReason(rs.getString("sloanreason"));
				applyInfo.setLoanPurpose(rs.getString("sloanpurpose"));
				applyInfo.setOther(rs.getString("sother"));
				applyInfo.setBankInterestID(rs.getLong("nbankinterestid"));
				applyInfo.setLiborRateID(rs.getLong("NLIBORRATEID"));
				applyInfo.setChargeRate(rs.getDouble("mchargerate"));
				applyInfo.setIntervalNum(rs.getLong("nintervalnum"));
				applyInfo.setStartDate(rs.getTimestamp("dtstartdate"));
				applyInfo.setEndDate(rs.getTimestamp("dtenddate"));
				applyInfo.setClientInfo(rs.getString("sclientinfo"));
				applyInfo.setSelfAmount(rs.getDouble("mselfamount"));
				applyInfo.setInterestRate(rs.getDouble("minterestrate"));
				applyInfo.setIsCircle(rs.getLong("niscircle"));
				applyInfo.setIsSaleBuy(rs.getLong("nissalebuy"));
				applyInfo.setIsTechnical(rs.getLong("nistechnical"));
				applyInfo.setIsCredit(rs.getLong("niscredit"));
				applyInfo.setIsAssure(rs.getLong("nisassure"));
				applyInfo.setIsImpawn(rs.getLong("nisimpawn"));
				applyInfo.setIsPledge(rs.getLong("nispledge"));
				applyInfo.setIsRecognizance(rs.getLong("IsRecognizance"));
				applyInfo.setInterestTypeID(rs.getLong("nInterestTypeID"));
				applyInfo.setExamineAmount(rs.getDouble("mExamineAmount"));
				applyInfo.setStatusID(rs.getLong("nStatusID"));
				applyInfo.setNextCheckUserID(rs.getLong("nNextCheckUserID"));
				applyInfo.setIsCanModify(rs.getLong("IsCanModify"));
				applyInfo.setChargeRateTypeID(rs.getLong("nChargeRateTypeID"));
				applyInfo.setRiskLevel(rs.getLong("nRiskLevel"));
				applyInfo.setTypeID1(rs.getLong("nTypeID1"));
				applyInfo.setTypeID2(rs.getLong("nTypeID2"));
				applyInfo.setTypeID3(rs.getLong("nTypeID3"));
				applyInfo.setNBankAcceptPO(rs.getLong("nBankAcceptPO"));
				applyInfo.setNBizAcceptPO(rs.getLong("nBizAcceptPO"));
				applyInfo.setCheckAmount(rs.getDouble("mCheckAmount"));
				applyInfo.setDiscountRate(rs.getDouble("mDiscountRate"));
				applyInfo.setDiscountDate(rs.getTimestamp("dtDiscountDate"));
				applyInfo.setAdjustRate(rs.getDouble("mAdjustRate"));
				applyInfo.setNextCheckLevel(rs.getLong("nNextCheckLevel"));
				// 担保业务相关
				applyInfo.setAssureChargeRate(rs.getDouble("AssureChargeRate")); // 担保费率
				applyInfo.setAssureChargeTypeID(rs
						.getLong("AssureChargeTypeID")); // 担保费收取方式
				applyInfo.setBeneficiary(rs.getString("Beneficiary")); // 受益人
				applyInfo.setAssureTypeID1(rs.getLong("AssureTypeID1")); // 担保类型1
				applyInfo.setAssureTypeID2(rs.getLong("AssureTypeID2")); // 担保类型2
				applyInfo.setSubTypeId(rs.getLong("NSUBTYPEID"));

				// ======ninh 2004-06-22 需求变更 增加固定浮动利率======//
				applyInfo.setStaidAdjustRate(rs.getDouble("mStaidAdjustRate"));

				String lastCheckUser = appbiz.getLastCheckPerson(lModuleID,
						applyInfo.getSubTypeId(), lActionID, 1, 1, applyInfo
								.getID());
				System.out.println("typeID:" + applyInfo.getTypeID()
						+ " loanID:" + applyInfo.getID() + "getLastCheckUser:"
						+ lastCheckUser);
				applyInfo.setLastCheckUserName(lastCheckUser);
				v.add(applyInfo);
			}
			cleanup(rs);
			cleanup(ps);

			int count = v.size();
			for (int i = 1; i < count; i++) {
				LoanApplyInfo laInfo = (LoanApplyInfo) v.get(i);
				long laClientID = laInfo.getBorrowClientID();
				long laWtClientID = laInfo.getConsignClientID();
				String laClientName = "";
				String laWtClientName = "";
				if (laClientID > 0) {
					strSQL = "select sName from client where ID=" + laClientID;
					ps = con.prepareStatement(strSQL);
					rs = ps.executeQuery();
					if (rs.next()) {
						laClientName = rs.getString("sName");
						laInfo.setBorrowClientName(laClientName);
					}
					cleanup(rs);
					cleanup(ps);
				}
				if (laWtClientID > 0) {
					strSQL = "select sName from client where ID="
							+ laWtClientID;
					ps = con.prepareStatement(strSQL);
					rs = ps.executeQuery();
					if (rs.next()) {
						laWtClientName = rs.getString("sName");
						laInfo.setConsignClientName(laWtClientName);
					}
					cleanup(rs);
					cleanup(ps);
				}

			}
			cleanup(con);
		} catch (Exception e) {
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			throw e;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}

		return (v.size() > 0 ? v : null);

	}

	private String getAddress(String strProvince, String strCity,
			String sAddress) throws Exception {
		String strAddress = "";
		try {
			if (strProvince != null && strProvince.trim().length() > 0) {
				strAddress = strProvince + "省";
			}
			if (strCity != null && strCity.trim().length() > 0) {
				strAddress = strAddress + strCity + "市";
			}
			strAddress = strAddress + sAddress;
		} catch (Exception e) {
			log.error(e.toString());
			throw new IException("Gen_E001");
		}
		return strAddress;
	}

	public long examineUpdate(LoanExaminePassInfo lepInfo) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		long loanID = lepInfo.getLoanID();
		double amount = lepInfo.getAmount();
		double selfAmount = lepInfo.getSelfAmount();
		double selfScale = lepInfo.getSelfScale();
		long period = lepInfo.getPeriod();
		long rateID = lepInfo.getRateID();
		double adjustRate = lepInfo.getAdjustRate();
		double interestRate = lepInfo.getInterestRate();
		double agentRate = lepInfo.getAgentRate();
		long repayType = lepInfo.getRepayType();
		String option = lepInfo.getOption();
		long userID = lepInfo.getUserID();
		long nextUserID = lepInfo.getNextUserID();
		long statusID = lepInfo.getStatusID();
		long lResult = -1;
		long lInterestTypeID = -1;
		long liborRateID = -1;
		long isRemitCompoundInterest = lepInfo.getIsRemitCompoundInterest();
		long isRemitOverDueInterest = lepInfo.getIsRemitOverDueInterest();
		double overDueaAjustRate = lepInfo.getOverDueAdjustRate();
		double overDueStaidAdjustRate = lepInfo.getOverDueStaidAdjustRate();
		liborRateID = lepInfo.getLiborRateID();
		lInterestTypeID = lepInfo.getInterestTypeID();
		// ======ninh 2004-06-22 需求变更 增加固定浮动利率======//
		double staidAdjustRate = lepInfo.getStaidAdjustRate(); // 固定浮动利率

		double mInterestRate = lepInfo.getMInterestRate();

		try {
			conn = Database.getConnection();
			sb.append(" update loan_loanForm set ");
			
			//modified by mzh_fu 2007/05/28 去掉nNextCheckUserID,增加CHECKOPINION(审核意见)
			//sb.append(" nNextCheckUserID = ? ");
			sb.append(" CHECKOPINION = ? ");

			if (amount > 0)
				sb.append(" ,MEXAMINEAMOUNT = ? ");
			if (selfAmount > 0)
				sb.append(" ,MEXAMINESELFAMOUNT= ? ");
			if (period > 0)
				sb.append(" ,nIntervalNum = ? ");
			if (rateID > 0) {
				sb.append(" ,NBANKINTERESTID = ? ");
			}
			if(lepInfo.getLoanType() == LOANConstant.LoanType.WT){
			sb.append(" ,MINTERESTRATE = ? ");
			}
			if (liborRateID > 0) {
				sb.append(" ,NLIBORRATEID = ? ");
			}
			if (lInterestTypeID > 0) {
				sb.append(" ,NINTERESTTYPEID = ? ");
			}
			if (Math.abs(adjustRate) >= 0)// 判断对象赋值则修改
				sb.append(" ,MADJUSTRATE = ? ");
			if (agentRate >= 0 && (lepInfo.getLoanType() == LOANConstant.LoanType.WT||lepInfo.getLoanType() == LOANConstant.LoanType.YT))
				sb.append(" ,MCHARGERATE = ? ");
			if (interestRate > 0 && lepInfo.getLoanType() != LOANConstant.LoanType.WT )
				sb.append(" ,MinterestRate = ? ");
			if (repayType > 0)
				sb.append(" ,NCHARGERATETYPEID = ? ");
			// ======ninh 2004-06-22 需求变更 增加固定浮动利率======//
			// if ( staidAdjustRate>0 )
			if (Math.abs(staidAdjustRate) >= 0)// 判断对象赋值则修改
				sb.append(" ,MstaidAdjustRate = ? ");

			if(lepInfo.getAdjustRateTerm() >= 0)
			{
				sb.append(" ,adjustrateterm = ? ");
			}
				sb.append(" ,isremitcompoundinterest = ? ");
				sb.append(" ,isremitoverdueinterest = ? ");
				sb.append(" ,overdueadjustrate = ? ");
				sb.append(" ,overduestaidadjustrate = ? ");
			
			sb.append(" where id=?");

			log.print(sb.toString());
			System.out.println(sb.toString());
			ps = conn.prepareStatement(sb.toString());

			int i = 1;
			//modified by mzh_fu 2007/05/28 去掉nNextCheckUserID,增加CHECKOPINION(审核意见)
			//ps.setLong(i++, nextUserID);
			ps.setString(i++, option);
			
			if (amount > 0)
				ps.setDouble(i++, amount);
			if (selfAmount > 0)
				ps.setDouble(i++, selfAmount);
			if (period > 0)
				ps.setLong(i++, period);
			if (rateID > 0) {
				ps.setLong(i++, rateID);
				
			}
			if(lepInfo.getLoanType() == LOANConstant.LoanType.WT){
			    ps.setDouble(i++, mInterestRate);
			}
			if (liborRateID > 0)
				ps.setLong(i++, liborRateID);
			if (lInterestTypeID > 0)
				ps.setLong(i++, lInterestTypeID);
			if (Math.abs(adjustRate) >= 0)
				ps.setDouble(i++, adjustRate);
			if (agentRate >= 0 && (lepInfo.getLoanType() == LOANConstant.LoanType.WT||lepInfo.getLoanType() == LOANConstant.LoanType.YT))
				ps.setDouble(i++, agentRate);
			if (interestRate > 0 && lepInfo.getLoanType() != LOANConstant.LoanType.WT)
				ps.setDouble(i++, interestRate);
			if (repayType > 0)
				ps.setLong(i++, repayType);
			// ======ninh 2004-06-22 需求变更 增加固定浮动利率======//
			if (Math.abs(staidAdjustRate) >=0 )
				ps.setDouble(i++, staidAdjustRate);

			if(lepInfo.getAdjustRateTerm() >= 0)
			{
				ps.setInt(i++, lepInfo.getAdjustRateTerm());
			}
				ps.setLong(i++, isRemitCompoundInterest);
				ps.setLong(i++, isRemitOverDueInterest);
				ps.setDouble(i++, overDueaAjustRate);
				ps.setDouble(i++, overDueStaidAdjustRate);
			
			
			ps.setLong(i++, loanID);
			lResult = ps.executeUpdate();
			cleanup(ps);

			if (lepInfo.getLoanType() == LOANConstant.LoanType.YT) {
				// loan_yt_loanformbankassign申请
				sb.setLength(0);
				sb.append(" update loan_yt_loanformbankassign  ");
				sb.append(" set mRate = ?");
				sb.append(" where nLoanID = ? ");
				sb.append(" and nIsHead = 1 ");

				Log.print(sb.toString());
				ps = conn.prepareStatement(sb.toString());
				ps.setBigDecimal(1, DataFormat
						.div(selfAmount * 100, amount, 20));
				ps.setLong(2, lepInfo.getLoanID());
				lResult = ps.executeUpdate();
				cleanup(ps);
			}

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

	public long updateLoanNextCheckLevel(long lLoanID, long lNextCheckLevel)
			throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		long lResult = -1;

		try {
			conn = Database.getConnection();
			sb.append(" update loan_loanForm set ");
			if (lNextCheckLevel > 0) {
				sb.append(" nNextCheckLevel = " + lNextCheckLevel);
			} else {
				sb.append(" nNextCheckLevel = nNextCheckLevel + 1");
			}
			sb.append(" where id = ? ");

			log.print(sb.toString());
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

	/*
	 * private long loanID=-1; //流水号 private long typeID=-1; //贷款种类 private long
	 * currencyID=-1; //货币代码 private long officeID=-1; //办事处代码 private String
	 * applyCode=""; //贷款申请号 private long consignClientID=-1; //委托单位代码 private
	 * long borrowClientID=-1; //借款单位 private long inputUserID=-1; //录入人代码
	 * private Timestamp inputDate=null; //录入时间
	 */

	public static void main(String[] args) {
		LoanApplyDao lDao = new LoanApplyDao();
		long ret = -1;

		LoanCreateInfo cInfo = new LoanCreateInfo();
		LoanBasicInfo bInfo = new LoanBasicInfo();
		LoanPropertyInfo pInfo = new LoanPropertyInfo();

		cInfo.setTypeID(3);
		cInfo.setCurrencyID(1);
		cInfo.setOfficeID(1);
		cInfo.setConsignClientID(1);
		cInfo.setBorrowClientID(2);
		cInfo.setInputUserID(9);

		bInfo.setLoanID(4);
		bInfo.setLoanType(3);
		bInfo.setLoanAmount(1000);

		bInfo.setLoanReason("");
		bInfo.setLoanPurpose("");
		bInfo.setOther("dsf");
		bInfo.setBankInterestID(9);
		bInfo.setChargeRate(1);
		bInfo.setIntervalNum(1);
		bInfo.setClientInfo("sdf");
		bInfo.setSaleAmount(123);
		bInfo.setInterestRate(12);

		pInfo.setLoanID(4);
		pInfo.setLoanType(3);
		pInfo.setIsCircle(1);
		pInfo.setIsSaleBuy(1);
		pInfo.setIsTechnical(1);
		pInfo.setIsCredit(1);
		pInfo.setIsAssure(1);
		pInfo.setIsImpawn(1);
		pInfo.setIsPledge(1);

		try {
			LoanApplyInfo applyInfo = null;
			applyInfo = lDao.findByID(124);
			System.out.println(applyInfo.getID());
			// ret=lDao.saveLoanCreate(cInfo);
			// ret=lDao.saveLoanBasic(bInfo);
			// ret=lDao.saveLoanProperty(pInfo);
			// System.out.println(applyInfo);
			LoanQueryInfo qInfo = new LoanQueryInfo();
			qInfo.setConsignClientID(21);
			qInfo.setBorrowClientID(23);
			qInfo.setDesc(1);
			qInfo.setEndAmount(100000);
			qInfo.setEndID(100);
			qInfo.setEndDate(DataFormat.getDateTime(2004, 10, 10, 10, 10, 10));
			qInfo.setIntervalNum(12);
			qInfo.setLoanType(3);
			qInfo.setOrderParam(1);
			qInfo.setPageLineCount(10);
			qInfo.setStartAmount(10);
			qInfo
					.setStartDate(DataFormat.getDateTime(2002, 10, 10, 10, 10,
							10));
			qInfo.setStartID(1);
			qInfo.setStatusID(0);

			// Vector c=(Vector)lDao.query(qInfo);

			// System.out.println(c.size() );

			LoanExaminePassInfo peInfo = new LoanExaminePassInfo();
			peInfo.setLoanID(1);
			peInfo.setAmount(100);
			peInfo.setSelfAmount(100);
			peInfo.setPeriod(12);
			peInfo.setRateID(12);
			peInfo.setAdjustRate(10);
			peInfo.setAgentRate(10);
			peInfo.setNextUserID(10);

			// ret=lDao.examineUpdate(peInfo);
			// lDao.insert(389,3);
			// System.out.println("ret:"+ret);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** 胡志强(kewen hu)添加(2004-03-10) */
	/**
	 * 通过贷款ID取合同编号
	 * 
	 * @param long
	 *            lLoanID
	 * @return String ContractCode
	 * @exception Exception
	 */
	public String findContratCodeByLoanID(long lLoanID) throws Exception {
		String sReturn = "";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		strSQL
				.append("SELECT sContractCode FROM loan_ContractForm WHERE nLoanID = "
						+ lLoanID + " \n");
		log.print("SQL=" + strSQL.toString());
		try {
			conn = Database.getConnection();
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				sReturn = rs.getString("sContractCode");
			}
		} catch (Exception se) {
			throw se;
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}
		return sReturn == null || sReturn == "" ? "" : sReturn;
	}

	// 有授信检查报告的修改审批级别
	public long updateLoanCheckLevel(long loanID, long isLowLevel, long reportid)
			throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;

		try {
			conn = Database.getConnection();
			strSQL = " update loan_LoanForm set isLowLevel = ?,NCREDITCHECKREPORTID= ?  where ID = ? ";

			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, isLowLevel);
			ps.setLong(2, reportid);
			ps.setLong(3, loanID);

			lResult = ps.executeUpdate();

			cleanup(ps);
			cleanup(conn);

			if (lResult < 0) {
				log.info(" update loan isLowLevel error : " + lResult);
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

	public long updateLoanCheckLevel(long loanID, long isLowLevel)
			throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;

		try {
			conn = Database.getConnection();
			strSQL = " update loan_LoanForm set isLowLevel = ? where ID = ? ";

			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, isLowLevel);
			ps.setLong(2, loanID);

			lResult = ps.executeUpdate();

			cleanup(ps);
			cleanup(conn);

			if (lResult < 0) {
				log.info(" update loan isLowLevel error : " + lResult);
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

	/*
	 * 操作表loan_loanform 通过id查找贷款申请人，申请金额 author weihuang 贷款申请页面的授信检查报告的匹配要用到
	 */
	public LoanApplyInfo findBorrowInfoByID(long id) throws Exception {
		LoanApplyInfo lainfo = new LoanApplyInfo();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		try {
			conn = Database.getConnection();
			strSQL = "select MLOANAMOUNT,NBORROWCLIENTID,NTYPEID from loan_loanform where id="
					+ id;
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				lainfo.setBorrowClientID(rs.getLong("NBORROWCLIENTID"));
				lainfo.setLoanAmount(rs.getDouble("MLOANAMOUNT"));
				lainfo.setTypeID(rs.getLong("NTYPEID"));
			}

		} catch (Exception e) {
			e.printStackTrace();
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
		return lainfo;

	}

	/*
	 * 通过id查找贷款申请中有担保方的担保人，担保金额 @author weihuang 操作表LOAN_LOANFORMASSURE TODO To
	 * change the template for this generated type comment go to Window -
	 * Preferences - Java - Code Style - Code Templates
	 */
	public Collection findAssureByID(long id) throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList al = new ArrayList();
		Connection conn = null;
		String strSQL = null;
		try {
			conn = Database.getConnection();
			strSQL = " select MAMOUNT,NCLIENTID from LOAN_LOANFORMASSURE where NLOANID="
					+ id;
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				AssureInfo assinfo = new AssureInfo();
				assinfo.setAmount(rs.getDouble("MAMOUNT"));
				assinfo.setClientID(rs.getLong("NCLIENTID"));
				al.add(assinfo);
			}
		} catch (Exception e) {
			e.printStackTrace();

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
		return al;

	}

	
	public long savezl(LoanApplyInfo lainfo) throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		// ResultSet rs = null;
		String strSQL = null;
		// long lLoanYTAssignID = -1;
		// long lConYTAssignID = -1;
		long lResult = -1;

		try {
			conn = Database.getConnection();
			
			//modified by qhzhou 2007-07-19
			//strSQL = " update loan_LoanForm set CHECKOPINION=? " + " where ID=? ";
			strSQL = " update loan_LoanForm set CHECKOPINION=? , MEXAMINEAMOUNT=?, nstatusid=? " + " where ID=? ";
			System.out.println(strSQL);
			ps = conn.prepareStatement(strSQL);
			int n = 1;
			ps.setString(n++, lainfo.getCheckOpinion());
			
			//added by qhzhou 2007-07-19
			ps.setDouble(n++, lainfo.getExamineAmount());
			ps.setLong(n++, LOANConstant.LoanStatus.SAVE);
			
			ps.setLong(n++, lainfo.getID());
			
			lResult = ps.executeUpdate();
			cleanup(ps);
			System.out.println("!!!!!"+strSQL);

			if (lResult < 0) {
				log.info("update loan_loanForm info error:" + lResult);
				return lResult;
			} else {
				return lainfo.getLoanID();
			}

		} catch (Exception e) {
			cleanup(ps);
			cleanup(conn);
			e.printStackTrace();
			throw e;
		} finally {
			cleanup(ps);
			cleanup(conn);
		}
	}
	
	
	public long updateLoanStatusdb(LoanApplyInfo lainfo) throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		// ResultSet rs = null;
		String strSQL = null;
		// long lLoanYTAssignID = -1;
		// long lConYTAssignID = -1;
		long lResult = -1;

		try {
			conn = Database.getConnection();
			strSQL = " update loan_LoanForm t set t.checkopinion=? ,t.MEXAMINEAMOUNT=?, t.mcheckamount = ?, t.nstatusid=? where t.ID=? ";
			System.out.println(strSQL);
			ps = conn.prepareStatement(strSQL);
			int n = 1;
			System.out.println("5555555555555"+lainfo.getCheckOpinion());
			ps.setString(n++, lainfo.getCheckOpinion());
			
			if(lainfo.getTypeID()==LOANConstant.LoanType.DB){
				ps.setDouble(n++, lainfo.getLoanAmount());
			}else{
				ps.setDouble(n++, lainfo.getExamineAmount());
			}
			ps.setDouble(n++, lainfo.getCheckAmount());
			ps.setLong(n++, LOANConstant.LoanStatus.SAVE);
			ps.setLong(n++, lainfo.getID());

			lResult = ps.executeUpdate();
			cleanup(ps);
			System.out.println("!!!!!"+strSQL);

			if (lResult < 0) {
				log.info("update loan_loanForm info error:" + lResult);
				return lResult;
			} else {
				return lainfo.getLoanID();
			}

		} catch (Exception e) {
			cleanup(ps);
			cleanup(conn);
			e.printStackTrace();
			throw e;
		} finally {
			cleanup(ps);
			cleanup(conn);
		}
	}
	
	//Modify by leiyang date 2007/07/10
	/**
	 * 检查合同状态
	 */
	public long checkContractState(long loanId) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = null;
		long lResult = -1;

		try {
			conn = Database.getConnection();
			strSQL = "select * from loan_contractform t where t.nloanId = ? and t.nStatusId not in(?,?,?)";

			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, loanId);
			ps.setLong(2, Constant.RecordStatus.INVALID);
			ps.setLong(3, LOANConstant.ContractStatus.CANCEL);
			ps.setLong(4, LOANConstant.ContractStatus.REFUSE);
			rs = ps.executeQuery();
			System.out.println(strSQL);
			
			while(rs.next()) {
				lResult = lResult + 1;
			}
			
			cleanup(ps);
		} catch (Exception e) {
			cleanup(ps);
			cleanup(conn);
			e.printStackTrace();
			throw e;
		} finally {
			cleanup(ps);
			cleanup(conn);
		}
		return lResult;
	}
	
	/**
	 * 国机项目组要求等额本息的合同的保证金金额为3期的租金，计算3期的保证金
	 * param: lLoanID  流水号
	 * author: xyzhang
	 * time:2010-08-03
	 * 
	 * */
	public double autoAccount(long lLoanID)throws Exception{
	  	  double dTotal = 0;//本金
	  	  double dRate = 0;//利率
	      long lInterestCountType = -1;//利息计算方式
	      long nPayType = -1;//租金偿还方式
	      long lRepayNum = -1;//偿还频率
	      long lIntervalNum = -1;//期限
	      double payment_interest_rate = 0;
	      double principalTemp = 0;
	      double account = 0;
          /*
           * add by yunchang
           * date 2010-08-27 11点26分
           * 增加   租赁物到期残值(融资租赁)
           * 增加   租赁物到期残值(融资租赁)所产生的利息
           */
          double mMatureNominalAmount = 0.0d; 
          double mMatureNominalRateAmount = 0.0d;	      
	        
	      Connection con = null;
	      PreparedStatement ps = null;
	      ResultSet rs = null;
	        
	        try {
				con = Database.getConnection();
				StringBuffer sb = new StringBuffer();
				
				sb.append("SELECT * FROM loan_LoanForm where id = "+lLoanID);             
	          ps = con.prepareStatement(sb.toString());
	          rs = ps.executeQuery();
	          if (rs.next())
	          {
	              dTotal = rs.getDouble("MLOANAMOUNT");
	              dRate = rs.getDouble("MINTERESTRATE");
	              lInterestCountType = rs.getLong("NINTERESTCOUNTTYPEID");
	              nPayType = rs.getLong("ASSURECHARGETYPEID");
	              lIntervalNum = rs.getLong("NINTERVALNUM");  
                  /*
                   * add by yunchang
                   * date 2010-08-27 11点26分
                   * 增加   租赁物到期残值(融资租赁)
                   */                  
                  mMatureNominalAmount = rs.getDouble("MMATURENOMINALAMOUNT");	              
	          }
	          if(nPayType > 0){
	          	switch ( (int)nPayType )
	              {
	                  case  (int)LOANConstant.ChargeRatePayType.YEAR  :     //年
	               	       lRepayNum = 12;
	                  	   break;
	                  case  (int)LOANConstant.ChargeRatePayType.HALFYEAR  :     //半年
	                         lRepayNum = 6;
	                         break;
	                  case (int)LOANConstant.ChargeRatePayType.QUARTER  :     //季
	                        lRepayNum = 3;    
	                        break;
	                  case  (int)LOANConstant.ChargeRatePayType.MONTH :    //月
	                  	  lRepayNum = 1;    
	                  	  break;
	              }
	          }
	          if(lInterestCountType == LOANConstant.InterestCountType.AVERAGEAMOUNT){//计算机利率和本息金额
	            payment_interest_rate = dRate*lRepayNum/12/100;   
				/*
                 * add by yunchang
                 * date 2010-08-27 11点26分
                 * 增加   租赁物到期残值(融资租赁) 每期所产生的利息
                 */     
				mMatureNominalRateAmount = mMatureNominalAmount * payment_interest_rate;
		          /*
		           * add by yunchang
		           * date 2010-09-01 11:22
		           * function 融资租赁的到期残值单独算利息
		           *          所以融资租赁的金额应该先减去到期残值
		           */				
			    principalTemp = (dTotal - mMatureNominalAmount)*payment_interest_rate*Math.pow((1+payment_interest_rate),lIntervalNum/lRepayNum)/(Math.pow((1+payment_interest_rate),lIntervalNum/lRepayNum)-1);
			    /*
			     * add by yunchang
			     * date 2010-08-27 13:27
			     * function 等本每次还款的金额   添加上到期残值所产生的利息
			     */
			    principalTemp = principalTemp + mMatureNominalRateAmount ;
			    //四舍五入
			    principalTemp = DataFormat.formatDouble(principalTemp);
			    long marginAmountCount = Config.getLong(Config.LOAN_FINANCE_MARGINAMOUNT, 0);
			    account = principalTemp*marginAmountCount;
	          }
	          } catch (Exception e) {
				e.printStackTrace();
			}finally{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
	       
	  	  return account;
	    }
	
	
	/*
	 * private long loanID=-1; //流水号 private long loanType=-1; //贷款种类 private
	 * long isCircle=0; //是否循环 private long isSaleBuy=-1; //是否卖方贷款 private long
	 * isTechnical=0; //是否技改贷款 private long isCredit=0; //是否信用保证 private long
	 * isAssure=0; //是否担保 private long isImpawn=0; //是否质押 private long
	 * isPledge=0; //是否抵押
	 */
}
