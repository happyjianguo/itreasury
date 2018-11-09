/*
 * Created on 2003-10-15
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.contract.dao;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.iss.itreasury.bill.util.BILLConstant;
import com.iss.itreasury.loan.attornmentapply.dataentity.AttornmentContractInfo;
import com.iss.itreasury.loan.contract.dataentity.AssureInfo;
import com.iss.itreasury.loan.contract.dataentity.ContractAmountInfo;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.contract.dataentity.ContractQueryInfo;
import com.iss.itreasury.loan.contract.dataentity.RateInfo;
import com.iss.itreasury.loan.contract.dataentity.YTFormatInfo;
import com.iss.itreasury.loan.contract.dataentity.YTInfo;
import com.iss.itreasury.loan.contractcontent.dao.ContractContentDao;
import com.iss.itreasury.loan.contractcontent.dataentity.ContractContentInfo;
import com.iss.itreasury.loan.discount.dao.DiscountDao;
import com.iss.itreasury.loan.loanapply.dao.FormAssureDao;
import com.iss.itreasury.loan.loanapply.dao.LoanRepayPlanDao;
import com.iss.itreasury.loan.loanapply.dataentity.LoanApplyInfo;
import com.iss.itreasury.loan.loanapply.dataentity.LoanPlanDetailInfo;
import com.iss.itreasury.loan.loanapply.dataentity.LoanQueryInfo;
import com.iss.itreasury.loan.risklevel.dataentity.RiskLevelInfo;
import com.iss.itreasury.loan.setting.dao.LoanTypeRelationDao;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.loan.util.LOANNameRef;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.system.bizdelegation.ApprovalDelegation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.CreateCodeManager;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Config;

/**
 * @author hyzeng
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ContractDao {
	private static Log4j log4j = null;

	public ContractDao() {
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
	}

	/**
	 * 查询待办任务
	 * 
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	public Collection queryWaitOperation(ContractQueryInfo contractQueryInfo)
			throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		List lstReturn = new ArrayList();

		strSQL = "select a.*,b.name subTypeName,c.name borrowclientname from loan_contractform a,Loan_LoanTypeSetting b ,client_clientinfo c"
				+ " where a.ncurrencyid = ? and a.nofficeid = ? "
				+ " and a.ninputuserid = ? and a.nstatusid = ?  and b.id = a.nsubtypeid and c.id = a.nborrowclientid"
				+ " order by a.id desc";

		try {
			conn = Database.getConnection();
			ps = conn.prepareStatement(strSQL);

			ps.setLong(1, contractQueryInfo.getCurrencyID());
			ps.setLong(2, contractQueryInfo.getOfficeID());
			ps.setLong(3, contractQueryInfo.getUserID());
			ps.setLong(4, LOANConstant.ContractStatus.SAVE);

			rs = ps.executeQuery();

			while (rs != null && rs.next()) {
				ContractInfo info = new ContractInfo();
				info.setContractID(rs.getLong("ID")); // 合同的ID
				info.setLoanID(rs.getLong("nLoanID")); // 贷款ID
				info.setLoanTypeID(rs.getLong("nTypeId"));
				info.setSubTypeID(rs.getLong("nsubtypeid"));
				info.setSubTypeName(rs.getString("subTypeName"));
				info.setApplyCode(rs.getString("sApplyCode")); // 申请书编号
				info.setContractCode(rs.getString("sContractCode")); // 合同编号
				// info.setBorrowClientName(rs.getString("nborrowclientid")); //
				// 借款单位
				info.setLoanAmount(rs.getDouble("mLoanAmount")); // 借款金额
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // 批准金额
				info.setCheckAmount(rs.getDouble("mCheckAmount")); // 汇总贴现核定金额
				// info.setInputUserName(rs.getString("InputUserName")); // 录入人
				info.setDiscountRate(rs.getDouble("mDiscountRate")); // 贴现利率
				// cec
				info.setIsPurchaserInterest(rs.getLong("IsPurchaserInterest")); // 是否买方付息
				info.setDiscountClientID(rs.getLong("DiscountClientID")); // 出票人
				info.setDiscountClientName(rs.getString("DiscountClientName")); // 出票人名称
				info.setPurchaserInterestRate(rs
						.getDouble("PurchaserInterestRate")); // 买方付息比例

				// RateInfo rInfo = new RateInfo();
				// rInfo = getLatelyRate(-1, info.getContractID(), null);
				// info.setInterestRate(rInfo.getLateRate()); // 执行利率
				// info.setLateRateString(rInfo.getLateRateString());// 利率，字符串格式
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款起始日期
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款到期日期
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 期限
				info.setInputDate(rs.getTimestamp("dtInputDate")); // 合同录入日期
				// info.setStatusID(rs.getLong("nStatusID")); // 合同状态
				// info.setStatus(LOANConstant.ContractStatus.getName(info
				// .getStatusID()));
				// 合同状态名称

				info.setAssureChargeRate(rs.getDouble("ASSURECHARGERATE")); // 担保费率

				info.setClientName(rs.getString("borrowclientname"));// 借款单位

				lstReturn.add(info);
			}

		} catch (Exception e) {
			throw e;
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
				throw new IException("Gen_E001");
			}
		}
		return lstReturn;
	}

	/**
	 * 根据条件查询合同信息(修改) Create Date: 2003-10-15
	 * 
	 * @param ContractQueryInfo
	 *            查询条件
	 * @return Collection
	 * @exception Exception
	 */
	public Collection queryForUpdate(ContractQueryInfo qInfo) throws Exception {
		Vector v = new Vector();

		// 分页变量
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		int lIndex = 1;

		String strSQL = "";
		String strSQL_pre = "";
		String strSQL_con = "";
		String strSQL_order = "";

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = Database.getConnection();

			// 计算记录总数
			strSQL_pre = " SELECT COUNT(*) FROM loan_ContractForm a";

			// 查询条件
			strSQL_con = " WHERE a.nTypeID=? ";
			strSQL_con += " AND a.nInputUserID=?";
			strSQL_con += " AND a.nStatusID >=" + Constant.RecordStatus.VALID;
			strSQL_con += " AND a.nStatusID <="
					+ LOANConstant.ContractStatus.SUBMIT;
			strSQL_con += " AND a.nCurrencyID=?";
			strSQL_con += " AND a.nOfficeID=?";

			// lContractIDFrom 合同编号起
			if (qInfo.getContractIDFrom() > 0) {
				strSQL_con += " AND a.ID >= ?";
			}

			// lContractIDTo 合同编号止
			if (qInfo.getContractIDTo() > 0) {
				strSQL_con += " AND a.ID <= ?";
			}

			// lConsignClientID委托单位ID
			if (qInfo.getConsignClientID() > 0) {
				strSQL_con += " AND a.nConsignClientID = ?";
			}

			// lClientID借款单位ID
			if (qInfo.getClientID() > 0) {
				strSQL_con += " AND a.nBorrowClientID = ?";
			}

			// dAmountFrom金额起
			if (qInfo.getAmountFrom() > 0) {
				strSQL_con += " AND a.mExamineAmount >= ?";
			}

			// dAmountTo金额止
			if (qInfo.getAmountTo() > 0) {
				strSQL_con += " AND a.mExamineAmount <= ?";
			}

			// tsLoanStart贷款日期起
			if (qInfo.getLoanStart() != null) {
				strSQL_con += " AND TO_CHAR(a.dtStartDate,'yyyy-mm-dd') >= TO_CHAR(?,'yyyy-mm-dd')";
			}

			// tsLoanEnd贷款日期止
			if (qInfo.getLoanEnd() != null) {
				strSQL_con += " AND TO_CHAR(a.dtEndDate,'yyyy-mm-dd') <=TO_CHAR(?,'yyyy-mm-dd')";
			}

			// lIntervalNum期限
			if (qInfo.getIntervalNum() > 0) {
				strSQL_con += " AND a.nIntervalNum = ?";
			}

			// tsInputStart 合同录入日期起
			if (qInfo.getInputStart() != null) {
				strSQL_con += " AND TO_CHAR(a.dtInputDate,'yyyy-mm-dd') >=TO_CHAR(?,'yyyy-mm-dd')";
			}

			// tsInputStart 合同录入日期止
			if (qInfo.getInputEnd() != null) {
				strSQL_con += " AND TO_CHAR(a.dtInputDate,'yyyy-mm-dd') <=TO_CHAR(?,'yyyy-mm-dd')";
			}

			// lStatusID 借款合同状态
			if (qInfo.getStatusID() > 0) {
				strSQL_con += " AND a.nStatusID = ?";
			}

			// lStatusID 借款合同状态
			if (qInfo.getStatusID() < 0
					|| qInfo.getStatusID() == LOANConstant.LoanStatus.SUBMIT) {
				// strSQL_con += " AND a.nNextCheckUserID = ?";
				strSQL_con += " AND a.nNextCheckLevel = 1 ";
			}

			strSQL = strSQL_pre + strSQL_con;
			log4j.info(strSQL);
			System.out.println(strSQL);
			ps = con.prepareStatement(strSQL);

			ps.setLong(lIndex++, qInfo.getTypeID());
			ps.setLong(lIndex++, qInfo.getUserID());
			ps.setLong(lIndex++, qInfo.getCurrencyID());
			ps.setLong(lIndex++, qInfo.getOfficeID());

			// lContractIDFrom 合同编号起
			if (qInfo.getContractIDFrom() > 0) {
				ps.setLong(lIndex++, qInfo.getContractIDFrom());
			}

			// lContractIDTo 合同编号止
			if (qInfo.getContractIDTo() > 0) {
				ps.setLong(lIndex++, qInfo.getContractIDTo());
			}

			// lConsignClientID委托单位ID
			if (qInfo.getConsignClientID() > 0) {
				ps.setLong(lIndex++, qInfo.getConsignClientID());
			}

			// lClientID借款单位ID
			if (qInfo.getClientID() > 0) {
				ps.setLong(lIndex++, qInfo.getClientID());
			}

			// dAmountFrom金额起
			if (qInfo.getAmountFrom() > 0) {
				ps.setDouble(lIndex++, qInfo.getAmountFrom());
			}

			// dAmountTo金额止
			if (qInfo.getAmountTo() > 0) {
				ps.setDouble(lIndex++, qInfo.getAmountTo());
			}

			if (qInfo.getLoanStart() != null) {
				ps.setTimestamp(lIndex++, qInfo.getLoanStart());
			}

			if (qInfo.getLoanEnd() != null) {
				ps.setTimestamp(lIndex++, qInfo.getLoanEnd());
			}

			// lIntervalNum期限
			if (qInfo.getIntervalNum() > 0) {
				ps.setLong(lIndex++, qInfo.getIntervalNum());
			}

			if (qInfo.getInputStart() != null) {
				ps.setTimestamp(lIndex++, qInfo.getInputStart());
			}

			if (qInfo.getInputEnd() != null) {
				ps.setTimestamp(lIndex++, qInfo.getInputEnd());
			}

			// lStatusID 借款合同状态
			if (qInfo.getStatusID() > 0) {
				ps.setLong(lIndex++, qInfo.getStatusID());
			}

			// lStatusID 借款合同状态
			if (qInfo.getStatusID() < 0
					|| qInfo.getStatusID() == LOANConstant.LoanStatus.SUBMIT) {
				// ps.setLong(lIndex++, qInfo.getUserID());
			}

			rs = ps.executeQuery();

			if (rs != null && rs.next()) {
				lRecordCount = rs.getLong(1);
			}
			log4j.info("lRecordCount:" + lRecordCount);

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}

			// 计算总页数
			if (lRecordCount > qInfo.getPageLineCount()) {
				lPageCount = lRecordCount / qInfo.getPageLineCount();
				if ((lRecordCount % qInfo.getPageLineCount()) != 0) {
					lPageCount++;
				}
			} else if (lRecordCount > 0
					&& lRecordCount <= qInfo.getPageLineCount()) {
				lPageCount = 1;
			} else {
				lPageCount = 0;
			}

			// 返回需求的结果集

			// 分页显示，显示下一页
			lRowNumStart = (qInfo.getPageNo() - 1) * qInfo.getPageLineCount()
					+ 1;
			lRowNumEnd = lRowNumStart + qInfo.getPageLineCount() - 1;

			switch ((int) qInfo.getOrderParam()) {
			case 1: // 按合申请书编号排序
				strSQL_order += " ORDER BY a.sApplyCode";
				break;
			case 2: // 按合同编号排序
				strSQL_order += " ORDER BY a.sContractCode";
				break;
			case 3: // 按借款单位排序
				strSQL_order += " ORDER BY c.sName";
				break;
			case 4: // 按金额排序
				strSQL_order += " ORDER BY a.mLoanAmount";
				break;
			case 5: // 按执行利率排序
				strSQL_order += " ";
				break;
			case 6: // 按贷款日期排序
				strSQL_order += " ORDER BY a.dtStartDate";
				break;
			case 7: // 按期限排序
				strSQL_order += " ORDER BY a.nIntervalNum";
				break;
			case 8: // 按合同录入日期排序
				strSQL_order += " ORDER BY a.dtInputDate";
				break;
			case 9: // 按合同状态排序
				strSQL_order += " ORDER BY a.nStatusID";
				break;
			case 10: // 按批准金额排序
				strSQL_order += " ORDER BY a.mExamineAmount";
				break;
			case 11: // 按汇总贴现核定金额排序
				strSQL_order += " ORDER BY a.mCheckAmount";
				break;
			case 12: // 按到期日期排序
				strSQL_order += " ORDER BY a.dtEndDate";
				break;
			case 13: // 按录入人排序
				strSQL_order += " ORDER BY a.nInputUserID";
				break;
			default:
				strSQL_order += " ORDER BY a.sContractCode DESC";
			}

			if (qInfo.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC) {
				strSQL_order += " DESC";
			}

			// got the sql sentence
			strSQL_pre = "SELECT * FROM ";
			strSQL_pre += " ( SELECT all_record.*,ROWNUM num FROM";
			strSQL_pre += " ( SELECT a.*,";
			strSQL_pre += " c.sName,";
			strSQL_pre += " u.sName as InputUserName";
			strSQL_pre += " FROM  loan_ContractForm a,";
			strSQL_pre += " client c,userinfo u";

			strSQL_con += " AND a.nInputUserID=u.ID(+)";
			strSQL_con += " AND a.nBorrowClientID=c.ID(+)";
			strSQL_con += strSQL_order;
			strSQL_con += ")all_record ";
			strSQL_con += ") WHERE num BETWEEN ? AND ?";

			strSQL = strSQL_pre + strSQL_con;
			log4j.info(strSQL);
			ps = con.prepareStatement(strSQL);
			lIndex = 1;
			ps.setLong(lIndex++, qInfo.getTypeID());
			ps.setLong(lIndex++, qInfo.getUserID());
			ps.setLong(lIndex++, qInfo.getCurrencyID());
			ps.setLong(lIndex++, qInfo.getOfficeID());

			System.out.println(strSQL);
			System.out.println(qInfo.getCurrencyID());
			System.out.println(qInfo.getUserID());
			System.out.println(qInfo.getTypeID());

			// lContractIDFrom 合同编号起
			if (qInfo.getContractIDFrom() > 0) {
				ps.setLong(lIndex++, qInfo.getContractIDFrom());
			}

			// lContractIDTo 合同编号止
			if (qInfo.getContractIDTo() > 0) {
				ps.setLong(lIndex++, qInfo.getContractIDTo());
			}

			// lConsignClientID委托单位ID
			if (qInfo.getConsignClientID() > 0) {
				ps.setLong(lIndex++, qInfo.getConsignClientID());
			}

			// lClientID借款单位ID
			if (qInfo.getClientID() > 0) {
				ps.setLong(lIndex++, qInfo.getClientID());
			}

			// dAmountFrom金额起
			if (qInfo.getAmountFrom() > 0) {
				ps.setDouble(lIndex++, qInfo.getAmountFrom());
			}

			// dAmountTo金额止
			if (qInfo.getAmountTo() > 0) {
				ps.setDouble(lIndex++, qInfo.getAmountTo());
			}

			if (qInfo.getLoanStart() != null) {
				ps.setTimestamp(lIndex++, qInfo.getLoanStart());
			}

			if (qInfo.getLoanEnd() != null) {
				ps.setTimestamp(lIndex++, qInfo.getLoanEnd());
			}

			// lIntervalNum期限
			if (qInfo.getIntervalNum() > 0) {
				ps.setLong(lIndex++, qInfo.getIntervalNum());
			}

			if (qInfo.getInputStart() != null) {
				ps.setTimestamp(lIndex++, qInfo.getInputStart());
			}

			if (qInfo.getInputEnd() != null) {
				ps.setTimestamp(lIndex++, qInfo.getInputEnd());
			}

			// lStatusID 借款合同状态
			if (qInfo.getStatusID() > 0) {
				ps.setLong(lIndex++, qInfo.getStatusID());
			}

			// lStatusID 借款合同状态
			if (qInfo.getStatusID() < 0
					|| qInfo.getStatusID() == LOANConstant.LoanStatus.SUBMIT) {
				// ps.setLong(lIndex++, qInfo.getUserID());
			}

			ps.setLong(lIndex++, lRowNumStart); // 给入起始行号
			ps.setLong(lIndex++, lRowNumEnd); // 给入结束行号

			rs = ps.executeQuery();

			while (rs != null && rs.next()) {
				ContractInfo info = new ContractInfo();
				info.setContractID(rs.getLong("ID")); // 合同的ID
				info.setLoanID(rs.getLong("nLoanID")); // 贷款ID
				info.setApplyCode(rs.getString("sApplyCode")); // 申请书编号
				info.setContractCode(rs.getString("sContractCode")); // 合同编号
				info.setBorrowClientName(rs.getString("sName")); // 借款单位
				info.setLoanAmount(rs.getDouble("mLoanAmount")); // 借款金额
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // 批准金额
				info.setCheckAmount(rs.getDouble("mCheckAmount")); // 汇总贴现核定金额
				info.setInputUserName(rs.getString("InputUserName")); // 录入人
				info.setDiscountRate(rs.getDouble("mDiscountRate")); // 贴现利率
				// cec
				info.setIsPurchaserInterest(rs.getLong("IsPurchaserInterest")); // 是否买方付息
				info.setDiscountClientID(rs.getLong("DiscountClientID")); // 出票人
				info.setDiscountClientName(rs.getString("DiscountClientName")); // 出票人名称
				info.setPurchaserInterestRate(rs
						.getDouble("PurchaserInterestRate")); // 买方付息比例

				RateInfo rInfo = new RateInfo();
				rInfo = getLatelyRate(-1, info.getContractID(), null);
				info.setInterestRate(rInfo.getLateRate()); // 执行利率
				info.setLateRateString(rInfo.getLateRateString());// 利率，字符串格式
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款起始日期
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款到期日期
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 期限
				info.setInputDate(rs.getTimestamp("dtInputDate")); // 合同录入日期
				info.setStatusID(rs.getLong("nStatusID")); // 合同状态
				info.setStatus(LOANConstant.ContractStatus.getName(info
						.getStatusID()));
				// 合同状态名称

				info.setAssureChargeRate(rs.getDouble("ASSURECHARGERATE")); // 担保费率

				info.setPageCount(lPageCount); // 记录总的页面数

				v.addElement(info);
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
		}

		catch (Exception e) {
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
		return (v.size() > 0 ? v : null);
	}

	public Collection findContractForAttornment(ContractQueryInfo qInfo)
	throws Exception {
		Vector v = new Vector();
		
		int lIndex = 1;
		
		String strSQL = "";
		String strSQL_con = "";
		String strSQL_pre = "";
		String strSQL_order = "";
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = Database.getConnection();
		
			// 查询条件
			strSQL_con = " WHERE 1=1 ";
			strSQL_con += " AND a.nStatusID >="
					+ LOANConstant.ContractStatus.ACTIVE;
			strSQL_con += " AND a.nStatusID <="
					+ LOANConstant.ContractStatus.EXTEND;
			// strSQL_con += " AND a.nTypeID >=" + LOANConstant.LoanType.ZYDQ;
			// strSQL_con += " AND a.nTypeID <=" + LOANConstant.LoanType.ZYZCQ;
			strSQL_con += " AND a.nTypeID =" + LOANConstant.LoanType.ZY;
		
			// lContractIDFrom 合同编号起
			if (qInfo.getContractIDFrom() > 0) {
				strSQL_con += " AND a.ID >= " + qInfo.getContractIDFrom();
			}
		
			// lContractIDTo 合同编号止
			if (qInfo.getContractIDTo() > 0) {
				strSQL_con += " AND a.ID <= " + qInfo.getContractIDTo();
			}
		
			// lClientID借款单位ID
			if (qInfo.getClientID() > 0) {
				strSQL_con += " AND a.nBorrowClientID = " + qInfo.getClientID();
			}
			/**
			// dAmountFrom金额起
			if (qInfo.getAmountFrom() > 0) {
				strSQL_con += " AND a.mExamineAmount >= "
						+ qInfo.getAmountFrom();
			}
		
			// dAmountTo金额止
			if (qInfo.getAmountTo() > 0) {
				strSQL_con += " AND a.mExamineAmount <= " + qInfo.getAmountTo();
			}
			*/
			
			// dAmountFrom金额起
			if (qInfo.getAmountFrom() > 0) {
				strSQL_con += " AND b.mAmount >= "
						+ qInfo.getAmountFrom();
			}
		
			// dAmountTo金额止
			if (qInfo.getAmountTo() > 0) {
				strSQL_con += " AND b.mAmount <= " + qInfo.getAmountTo();
			}
			
			// lStatusID 借款合同状态
			if (qInfo.getStatusID() > 0) {
				strSQL_con += " AND a.nStatusID = " + qInfo.getStatusID();
			}
		
			// 贷款类型
			if (qInfo.getTypeID() > 0) {
				strSQL_con += " and a.nTypeID = " + qInfo.getTypeID();
			}
			//办事处
			if (qInfo.getOfficeID() > 0) {
				strSQL_con += " and  a.nofficeid = " + qInfo.getOfficeID();
			}
			//币种
			if (qInfo.getTypeID() > 0) {
				strSQL_con += " and a.ncurrencyid = " + qInfo.getCurrencyID();
			}
			strSQL_order += " ORDER BY a.sContractCode, b.scode";
		
			if (qInfo.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC) {
				strSQL_order += " DESC";
			}
		
			// got the sql sentence
			strSQL_pre = " SELECT all_record.*,ROWNUM num FROM";
			strSQL_pre += " ( SELECT a.*,";
			strSQL_pre += " c.sName,";
			strSQL_pre += " u.sName as InputUserName, ";
			strSQL_pre += " b.id payid, b.scode paycode, b.mamount payamount, b.dtstart startdate, b.dtend enddate ";
			strSQL_pre += " FROM loan_ContractForm a, loan_payform b, ";
			strSQL_pre += " client c,userinfo u";
		
			strSQL_con += " and a.id = b.ncontractid";
			strSQL_con += " AND a.nInputUserID=u.ID(+)";
			strSQL_con += " AND a.nBorrowClientID=c.ID(+)";
			strSQL_con += strSQL_order;
			strSQL_con += ")all_record ";
		
			strSQL = strSQL_pre + strSQL_con;
			log4j.info("查找可用合同sql="+strSQL);
			ps = con.prepareStatement(strSQL);
		
			rs = ps.executeQuery();
		
			while (rs != null && rs.next()) {
				ContractInfo info = new ContractInfo();
				info.setContractID(rs.getLong("ID")); // 合同的ID
				info.setLoanID(rs.getLong("nLoanID")); // 贷款ID
				info.setLoanTypeID(rs.getLong("nTypeID"));
				info.setApplyCode(rs.getString("sApplyCode")); // 申请书编号
				info.setContractCode(rs.getString("sContractCode")); // 合同编号
				info.setBorrowClientName(rs.getString("sName")); // 借款单位
				info.setLoanAmount(rs.getDouble("mLoanAmount")); // 借款金额
				
				//info.setExamineAmount(rs.getDouble("mExamineAmount")); // 批准金额
				info.setExamineAmount(rs.getDouble("payamount")); // 批准金额
				
				info.setCheckAmount(rs.getDouble("mCheckAmount")); // 汇总贴现核定金额
				info.setInputUserName(rs.getString("InputUserName")); // 录入人
				info.setDiscountRate(rs.getDouble("mDiscountRate")); // 贴现利率
				RateInfo rInfo = new RateInfo();
				rInfo = getLatelyRate(-1, info.getContractID(), null);
				info.setInterestRate(rInfo.getLateRate()); // 执行利率
				
				//info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款起始日期
				//info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款到期日期
				info.setLoanStart(rs.getTimestamp("startdate")); // 贷款起始日期
				info.setLoanEnd(rs.getTimestamp("enddate")); // 贷款到期日期
				
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 期限
				info.setInputDate(rs.getTimestamp("dtInputDate")); // 合同录入日期
				info.setStatusID(rs.getLong("nStatusID")); // 合同状态
				info.setStatus(LOANConstant.ContractStatus.getName(info.getStatusID()));
				
				ContractAmountInfo aInfo = new ContractAmountInfo();
				long payID = rs.getLong("payid");
				//aInfo = getLateAmount(info.getContractID());
				
				//当前合同余额
				aInfo = getGuoDianLateAmount(info.getContractID(), payID);
				info.setBalance(aInfo.getBalanceAmount());  //子账户余额
				
				//合同放款单已经转让总金额
				info.setLastAttornmentAmount(this.sumLastAttornmentAmount(payID));
				//info.setLastAttornmentAmount(rs.getDouble("lastAttornmentAmount"));
				
				//合同本金减去提前还款申请后的余额
				//info.setBalanceForAttornment(aInfo.getBalanceAmount() - aInfo.getAheadAmount());
				//直接从子账户获得账户余额,这里的余额是在结算还款后的金额,不再减去贷款还款单金额
				info.setBalanceForAttornment(aInfo.getBalanceAmount());
				
				info.setLeftoversAttornmentAmount(rs.getDouble("leftoversAttornmentAmount")); // 债权余额
				
				info.setPayID(payID);
				info.setPayCode(rs.getString("paycode"));
				
				v.addElement(info);
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
		}
		
		catch (Exception e) {
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
		return (v.size() > 0 ? v : null);

	}


	/**
	 * 根据条件查询合同信息(审核) Create Date: 2003-10-15
	 * 
	 * @param ContractQueryInfo
	 *            查询条件
	 * @return Collection
	 * @exception Exception
	 */
	public Collection queryForExamine(ContractQueryInfo qInfo) throws Exception {
		Vector v = new Vector();

		// 分页变量
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		int lIndex = 1;

		String strSQL = "";
		String strSQL_pre = "";
		String strSQL_con = "";
		String strSQL_order = "";

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		LoanTypeRelationDao loanTypeDao = new LoanTypeRelationDao();
		long[] a_SubLoanType = null;
		String nextUserAndTypeSql = " ( ";

		try {
			// 获得真正来审批这个记录的人（真实或传给的虚拟的人！）
			ApprovalDelegation appBiz = new ApprovalDelegation();
			long lModuleID = Constant.ModuleType.LOAN; // 模块类型
			long lActionID = Constant.ApprovalAction.LOAN_CONTRACT; // 合同审核

			/*
			 * modify by yanliu 系统升级后，贷款类型分大类型和子类型，查询的时候，查询条件中是大类型，
			 * 而得到实际审批人的方法（findTheVeryUser）中贷款类型参数是子类型，所以修改查询方法。
			 */
			// 获取所有子类型
			a_SubLoanType = loanTypeDao.getAllSetSubLoanTypeID(qInfo
					.getOfficeID(), qInfo.getCurrencyID(), new long[] { qInfo
					.getTypeID() });
			if (a_SubLoanType != null && a_SubLoanType.length > 0) {
				for (int i = 0; i < a_SubLoanType.length; i++) {
					nextUserAndTypeSql = nextUserAndTypeSql
							+ " (a.nNextCheckUserID in "
							+ appBiz.findTheVeryUser(Constant.ModuleType.LOAN,
									a_SubLoanType[i],
									Constant.ApprovalAction.LOAN_CONTRACT,
									qInfo.getOfficeID(), qInfo.getCurrencyID(),
									qInfo.getUserID()) + " and a.NSUBTYPEID = "
							+ a_SubLoanType[i] + ")";
					if (i < a_SubLoanType.length - 1)
						nextUserAndTypeSql += " or ";
					else
						nextUserAndTypeSql += " ) ";
				}
			} else {
				return null;
			}

			// String strUser = appBiz.findTheVeryUser(lModuleID, qInfo
			// .getTypeID(), lActionID, qInfo.getOfficeID(), qInfo
			// .getCurrencyID(), qInfo.getUserID());
			// qInfo.setUserID(lUserID);
			con = Database.getConnection();

			// 计算记录总数
			strSQL_pre = " SELECT COUNT(*) FROM loan_ContractForm a";

			// 查询条件
			strSQL_con = " WHERE a.nTypeID=? ";
			strSQL_con += " AND ( " + nextUserAndTypeSql;
			strSQL_con += " OR (a.nNextCheckUserID=-2";
			strSQL_con += " AND a.nInputUserID=?))";
			strSQL_con += " AND (a.nStatusID ="
					+ LOANConstant.ContractStatus.SUBMIT;
			strSQL_con += " OR a.nStatusID ="
					+ LOANConstant.ContractStatus.CHECK + ")";
			strSQL_con += " AND a.nCurrencyID=?";
			strSQL_con += " AND a.nOfficeID=?";

			// lContractIDFrom 合同编号起
			if (qInfo.getContractIDFrom() > 0) {
				strSQL_con += " AND a.ID >= ?";
			}

			// lContractIDTo 合同编号止
			if (qInfo.getContractIDTo() > 0) {
				strSQL_con += " AND a.ID <= ?";
			}

			// lConsignClientID委托单位ID
			if (qInfo.getConsignClientID() > 0) {
				strSQL_con += " AND a.nConsignClientID = ?";
			}

			// lClientID借款单位ID
			if (qInfo.getClientID() > 0) {
				strSQL_con += " AND a.nBorrowClientID = ?";
			}

			// dAmountFrom金额起
			if (qInfo.getAmountFrom() > 0) {
				strSQL_con += " AND a.mExamineAmount >= ?";
			}

			// dAmountTo金额止
			if (qInfo.getAmountTo() > 0) {
				strSQL_con += " AND a.mExamineAmount <= ?";
			}

			// tsLoanStart贷款日期起
			if (qInfo.getLoanStart() != null) {
				strSQL_con += " AND TO_CHAR(a.dtStartDate,'yyyy-mm-dd') >= TO_CHAR(?,'yyyy-mm-dd')";
			}

			// tsLoanEnd贷款日期止
			if (qInfo.getLoanEnd() != null) {
				strSQL_con += " AND TO_CHAR(a.dtEndDate,'yyyy-mm-dd') <=TO_CHAR(?,'yyyy-mm-dd')";
			}

			// lIntervalNum期限
			if (qInfo.getIntervalNum() > 0) {
				strSQL_con += " AND a.nIntervalNum = ?";
			}

			// tsInputStart 合同录入日期起
			if (qInfo.getInputStart() != null) {
				strSQL_con += " AND TO_CHAR(a.dtInputDate,'yyyy-mm-dd') >=TO_CHAR(?,'yyyy-mm-dd')";
			}

			// tsInputStart 合同录入日期止
			if (qInfo.getInputEnd() != null) {
				strSQL_con += " AND TO_CHAR(a.dtInputDate,'yyyy-mm-dd') <=TO_CHAR(?,'yyyy-mm-dd')";
			}

			// lStatusID 借款合同状态
			if (qInfo.getStatusID() > 0) {
				strSQL_con += " AND a.nStatusID = ?";
			}

			strSQL = strSQL_pre + strSQL_con;
			System.out.println(strSQL);
			log4j.info(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(lIndex++, qInfo.getTypeID());
			ps.setLong(lIndex++, qInfo.getUserID());
			ps.setLong(lIndex++, qInfo.getCurrencyID());
			ps.setLong(lIndex++, qInfo.getOfficeID());

			// lContractIDFrom 合同编号起
			if (qInfo.getContractIDFrom() > 0) {
				ps.setLong(lIndex++, qInfo.getContractIDFrom());
			}

			// lContractIDTo 合同编号止
			if (qInfo.getContractIDTo() > 0) {
				ps.setLong(lIndex++, qInfo.getContractIDTo());
			}

			// lConsignClientID委托单位ID
			if (qInfo.getConsignClientID() > 0) {
				ps.setLong(lIndex++, qInfo.getConsignClientID());
			}

			// lClientID借款单位ID
			if (qInfo.getClientID() > 0) {
				ps.setLong(lIndex++, qInfo.getClientID());
			}

			// dAmountFrom金额起
			if (qInfo.getAmountFrom() > 0) {
				ps.setDouble(lIndex++, qInfo.getAmountFrom());
			}

			// dAmountTo金额止
			if (qInfo.getAmountTo() > 0) {
				ps.setDouble(lIndex++, qInfo.getAmountTo());
			}

			if (qInfo.getLoanStart() != null) {
				ps.setTimestamp(lIndex++, qInfo.getLoanStart());
			}

			if (qInfo.getLoanEnd() != null) {
				ps.setTimestamp(lIndex++, qInfo.getLoanEnd());
			}

			// lIntervalNum期限
			if (qInfo.getIntervalNum() > 0) {
				ps.setLong(lIndex++, qInfo.getIntervalNum());
			}

			if (qInfo.getInputStart() != null) {
				ps.setTimestamp(lIndex++, qInfo.getInputStart());
			}

			if (qInfo.getInputEnd() != null) {
				ps.setTimestamp(lIndex++, qInfo.getInputEnd());
			}

			// lStatusID 借款合同状态
			if (qInfo.getStatusID() > 0) {
				ps.setLong(lIndex++, qInfo.getStatusID());
			}

			rs = ps.executeQuery();

			if (rs != null && rs.next()) {
				lRecordCount = rs.getLong(1);
			}
			log4j.info("lRecordCount:" + lRecordCount);

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}

			// 计算总页数
			if (lRecordCount > qInfo.getPageLineCount()) {
				lPageCount = lRecordCount / qInfo.getPageLineCount();
				if ((lRecordCount % qInfo.getPageLineCount()) != 0) {
					lPageCount++;
				}
			} else if (lRecordCount > 0
					&& lRecordCount <= qInfo.getPageLineCount()) {
				lPageCount = 1;
			} else {
				lPageCount = 0;
			}

			// 返回需求的结果集

			// 分页显示，显示下一页
			lRowNumStart = (qInfo.getPageNo() - 1) * qInfo.getPageLineCount()
					+ 1;
			lRowNumEnd = lRowNumStart + qInfo.getPageLineCount() - 1;

			switch ((int) qInfo.getOrderParam()) {
			case 1: // 按合申请书编号排序
				strSQL_order += " ORDER BY a.sApplyCode";
				break;
			case 2: // 按合同编号排序
				strSQL_order += " ORDER BY a.sContractCode";
				break;
			case 3: // 按借款单位排序
				strSQL_order += " ORDER BY c.sName";
				break;
			case 4: // 按金额排序
				strSQL_order += " ORDER BY a.mLoanAmount";
				break;
			case 5: // 按执行利率排序
				strSQL_order += " ";
				break;
			case 6: // 按贷款日期排序
				strSQL_order += " ORDER BY a.dtStartDate";
				break;
			case 7: // 按期限排序
				strSQL_order += " ORDER BY a.nIntervalNum";
				break;
			case 8: // 按合同录入日期排序
				strSQL_order += " ORDER BY a.dtInputDate";
				break;
			case 9: // 按合同状态排序
				strSQL_order += " ORDER BY a.nStatusID";
				break;
			case 10: // 按批准金额排序
				strSQL_order += " ORDER BY a.mExamineAmount";
				break;
			case 11: // 按汇总贴现核定金额排序
				strSQL_order += " ORDER BY a.mCheckAmount";
				break;
			case 12: // 按到期日期排序
				strSQL_order += " ORDER BY a.dtEndDate";
				break;
			case 13: // 按录入人排序
				strSQL_order += " ORDER BY a.nInputUserID";
				break;
			default:
				strSQL_order += " ORDER BY a.sContractCode DESC";
			}

			if (qInfo.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC) {
				strSQL_order += " DESC";
			}

			// got the sql sentence
			strSQL_pre = "SELECT * FROM ";
			strSQL_pre += " ( SELECT all_record.*,ROWNUM num FROM";
			strSQL_pre += " ( SELECT a.*,";
			strSQL_pre += " c.sName,";
			strSQL_pre += " u.sName as InputUserName";
			strSQL_pre += " FROM  loan_ContractForm a,";
			strSQL_pre += " client c,userinfo u";

			strSQL_con += " AND a.nInputUserID=u.ID(+)";
			strSQL_con += " AND a.nBorrowClientID=c.ID(+)";
			strSQL_con += strSQL_order;
			strSQL_con += ")all_record ";
			strSQL_con += ") WHERE num BETWEEN ? AND ?";

			strSQL = strSQL_pre + strSQL_con;
			log4j.info(strSQL);
			ps = con.prepareStatement(strSQL);
			lIndex = 1;

			ps.setLong(lIndex++, qInfo.getTypeID());
			ps.setLong(lIndex++, qInfo.getUserID());
			ps.setLong(lIndex++, qInfo.getCurrencyID());
			ps.setLong(lIndex++, qInfo.getOfficeID());

			// lContractIDFrom 合同编号起
			if (qInfo.getContractIDFrom() > 0) {
				ps.setLong(lIndex++, qInfo.getContractIDFrom());
			}

			// lContractIDTo 合同编号止
			if (qInfo.getContractIDTo() > 0) {
				ps.setLong(lIndex++, qInfo.getContractIDTo());
			}

			// lConsignClientID委托单位ID
			if (qInfo.getConsignClientID() > 0) {
				ps.setLong(lIndex++, qInfo.getConsignClientID());
			}

			// lClientID借款单位ID
			if (qInfo.getClientID() > 0) {
				ps.setLong(lIndex++, qInfo.getClientID());
			}

			// dAmountFrom金额起
			if (qInfo.getAmountFrom() > 0) {
				ps.setDouble(lIndex++, qInfo.getAmountFrom());
			}

			// dAmountTo金额止
			if (qInfo.getAmountTo() > 0) {
				ps.setDouble(lIndex++, qInfo.getAmountTo());
			}

			if (qInfo.getLoanStart() != null) {
				ps.setTimestamp(lIndex++, qInfo.getLoanStart());
			}

			if (qInfo.getLoanEnd() != null) {
				ps.setTimestamp(lIndex++, qInfo.getLoanEnd());
			}

			// lIntervalNum期限
			if (qInfo.getIntervalNum() > 0) {
				ps.setLong(lIndex++, qInfo.getIntervalNum());
			}

			if (qInfo.getInputStart() != null) {
				ps.setTimestamp(lIndex++, qInfo.getInputStart());
			}

			if (qInfo.getInputEnd() != null) {
				ps.setTimestamp(lIndex++, qInfo.getInputEnd());
			}

			// lStatusID 借款合同状态
			if (qInfo.getStatusID() > 0) {
				ps.setLong(lIndex++, qInfo.getStatusID());
			}

			ps.setLong(lIndex++, lRowNumStart); // 给入起始行号
			ps.setLong(lIndex++, lRowNumEnd); // 给入结束行号

			rs = ps.executeQuery();

			while (rs != null && rs.next()) {
				ContractInfo info = new ContractInfo();
				info.setContractID(rs.getLong("ID")); // 合同的ID
				info.setLoanID(rs.getLong("nLoanID")); // 贷款ID
				info.setApplyCode(rs.getString("sApplyCode")); // 申请书编号
				info.setContractCode(rs.getString("sContractCode")); // 合同编号
				info.setBorrowClientName(rs.getString("sName")); // 借款单位
				info.setLoanAmount(rs.getDouble("mLoanAmount")); // 借款金额
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // 批准金额
				info.setCheckAmount(rs.getDouble("mCheckAmount")); // 汇总贴现核定金额
				info.setInputUserName(rs.getString("InputUserName")); // 录入人
				info.setDiscountRate(rs.getDouble("mDiscountRate")); // 贴现利率
				RateInfo rInfo = new RateInfo();
				rInfo = getLatelyRate(-1, info.getContractID(), null);
				info.setInterestRate(rInfo.getLateRate()); // 执行利率
				info.setLateRateString(rInfo.getLateRateString());// 利率，字符串格式
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款起始日期
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款到期日期
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 期限
				info.setInputDate(rs.getTimestamp("dtInputDate")); // 合同录入日期
				info.setStatusID(rs.getLong("nStatusID")); // 合同状态
				info.setStatus(LOANConstant.ContractStatus.getName(info
						.getStatusID()));
				// 合同状态名称

				info.setAssureChargeRate(rs.getDouble("ASSURECHARGERATE")); // 担保费率

				info.setPageCount(lPageCount); // 记录总的页面数

				info.setNextCheckLevel(rs.getLong("nNextCheckLevel")); // 下一个审核级别

				v.addElement(info);
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
		}

		catch (Exception e) {
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
		return (v.size() > 0 ? v : null);
	}

	/**
	 * 得到执行利率，参数lLoanID和lContractID必传入一个，不传入的话请设置为-1。 Create Date: 2003-10-15
	 * 
	 * @param lLoanID
	 *            贷款ID
	 * @param lContractID
	 *            合同ID
	 * @param tsDate
	 *            时间，如传入为NULL值或空串则默认为当前日期。
	 * @return double 执行利率
	 * @exception Exception
	 */
	public RateInfo getLatelyRate(long lLoanID, long lContractID,
			Timestamp tsDate) throws Exception {
		RateInfo info = new RateInfo();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		StringBuffer sbSQL = new StringBuffer();
		boolean bIsHaveLate = false;
		long lInterestTypeID = -1;
		long lLoanTypeID = -1;
		String strRate = "";

		try {
			con = Database.getConnection();

			if (tsDate == null || tsDate.equals("")) {
				tsDate = DataFormat.getDateTime(con);
			}

			// 取得利率类型
			if (lContractID > 0) {
				sbSQL.setLength(0);
				sbSQL
						.append(" SELECT b.nTypeID,b.nInterestTypeID,b.nLiborRateID,nvl(b.mInterestRate,0) mInterestRate,b.nBankInterestID, ");
				sbSQL
						.append(" nvl(b.mAdjustRate,0) mAdjustRate,nvl(b.mStaidAdjustRate,0) mStaidAdjustRate,nvl(c.mRate,0) mRate,d.LiborName,d.IntervalNum ");
				sbSQL
						.append(" FROM loan_contractForm b,loan_interestRate c,loan_liborInterestRate d ");
				sbSQL.append(" WHERE 1 = 1 ");
				sbSQL.append(" AND nvl(b.nBankInterestID,-1) = c.ID(+) ");
				sbSQL.append(" AND nvl(b.nLiborRateID,-1) = d.ID(+) ");
				sbSQL.append(" AND b.ID = ? ");
				// log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lContractID);
				rs = ps.executeQuery();

				if (rs.next()) {
					// 利率类型
					lInterestTypeID = rs.getLong("nInterestTypeID");
					// 贷款类型
					lLoanTypeID = rs.getLong("nTypeID");
					// 未调整的基准利率
					// if ((lLoanTypeID == LOANConstant.LoanType.WT) ||
					// (lLoanTypeID == LOANConstant.LoanType.WTTJTH))
					if (lLoanTypeID == LOANConstant.LoanType.WT
							|| lLoanTypeID == LOANConstant.LoanType.RZZL) {
						info.setBasicRate(rs.getDouble("mInterestRate"));
					} else {
						info.setBasicRate(rs.getDouble("mRate"));
					}
					// 未调整的利率ID
					info.setBankInterestID(rs.getLong("nBankInterestID"));
					// 未调整的利率
					info.setRate(info.getBasicRate()
							* (1 + rs.getDouble("mAdjustRate") / 100)
							+ rs.getDouble("mStaidAdjustRate"));
					info.setAdjustRate(rs.getDouble("mAdjustRate"));
					info.setStaidAdjustRate(rs.getDouble("mStaidAdjustRate"));
					// Libor利率ID
					info.setLiborRateID(rs.getLong("nLiborRateID"));
					// Libor利率名称
					info.setLiborName(rs.getString("LiborName"));
					// Libor利率期限
					info.setLiborIntervalNum(rs.getLong("IntervalNum"));
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
			} else if (lLoanID > 0) {
				sbSQL.setLength(0);
				sbSQL
						.append(" SELECT b.nInterestTypeID, b.nTypeID,b.mInterestRate,b.nBankInterestID,b.mAdjustRate,b.mStaidAdjustRate,b.nLiborRateID,c.mRate,d.LiborName,d.IntervalNum ");
				sbSQL
						.append(" FROM loan_loanForm b,loan_interestRate c,loan_liborInterestRate d ");
				sbSQL.append(" WHERE 1 = 1 ");
				sbSQL.append(" AND nvl(b.nBankInterestID,-1) = c.ID(+) ");
				sbSQL.append(" AND nvl(b.nLiborRateID,-1) = d.ID(+) ");
				sbSQL.append(" AND b.ID = ? ");
				// log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lLoanID);
				rs = ps.executeQuery();

				if (rs.next()) {
					// 利率类型
					lInterestTypeID = rs.getLong("nInterestTypeID");
					// 贷款类型
					lLoanTypeID = rs.getLong("nTypeID");
					// 未调整的基准利率
					// if ((lLoanTypeID == LOANConstant.LoanType.WT) ||
					// (lLoanTypeID == LOANConstant.LoanType.WTTJTH))
					if (lLoanTypeID == LOANConstant.LoanType.WT
							|| lLoanTypeID == LOANConstant.LoanType.RZZL) {
						info.setBasicRate(rs.getDouble("mInterestRate"));
					} else {
						info.setBasicRate(rs.getDouble("mRate"));
					}
					// 未调整的利率ID
					info.setBankInterestID(rs.getLong("nBankInterestID"));
					// 未调整的利率
					info.setRate(info.getBasicRate()
							* (1 + rs.getDouble("mAdjustRate") / 100)
							+ rs.getDouble("mStaidAdjustRate"));
					info.setAdjustRate(rs.getDouble("mAdjustRate"));
					info.setStaidAdjustRate(rs.getDouble("mStaidAdjustRate"));
					// Libor利率ID
					info.setLiborRateID(rs.getLong("nLiborRateID"));
					// Libor利率名称
					info.setLiborName(rs.getString("LiborName"));
					// Libor利率期限
					info.setLiborIntervalNum(rs.getLong("IntervalNum"));
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
			} else {
				return null;
			}

			if (lInterestTypeID == LOANConstant.InterestRateType.BANK) {
				// 通过合同ID取利率
				if (lContractID > 0) {
					sbSQL.setLength(0);
					sbSQL
							.append(" SELECT a.dtStartDate,b.mRate as mRate1,a.mRate as mRate2,a.nBankInterestID as nBankInterestID , b.ID,a.mAdjustRate,a.mStaidAdjustRate ");
					sbSQL
							.append(" FROM loan_rateAdjustContractDetail a,loan_interestRate b ");

					// //modified by mzh_fu 2007/07/19 增加条件 status !=
					// Constant.RecordStatus.INVALID
					// sbSQL.append(" WHERE 1 = 1 ");
					sbSQL.append(" WHERE a.status != "
							+ Constant.RecordStatus.INVALID);

					sbSQL
							.append(" AND TO_CHAR(a.dtStartDate,'yyyy-mm-dd')<= TO_CHAR(?,'yyyy-mm-dd') ");
					sbSQL.append(" AND a.nBankInterestID = b.id(+) ");
					sbSQL.append(" AND a.nContractID = ? ");
					sbSQL.append(" ORDER BY a.dtStartDate DESC ");

					ps = con.prepareStatement(sbSQL.toString());
					ps.setTimestamp(1, tsDate);
					ps.setLong(2, lContractID);
					log4j.info(sbSQL.toString());
					rs = ps.executeQuery();

					if (rs.next()) {
						if (rs.getLong("nBankInterestID") == -1) {
							info.setLateBasicRate(rs.getDouble("mRate2")); // 调整后的基准利率
						} else {
							info.setLateBasicRate(rs.getDouble("mRate1"));
						}// 调整后的基准利率
						// ======ninh 2004-06-22 需求变更 增加固定浮动利率===执行利率算法改变===//
						info.setLateRate(info.getLateBasicRate()
								* (1 + rs.getDouble("mAdjustRate") / 100)
								+ rs.getDouble("mStaidAdjustRate"));

						info.setLateAdjustRate(rs.getDouble("mAdjustRate"));
						// 调整后的利率
						info.setLateStaidAdjustRate(rs
								.getDouble("mStaidAdjustRate"));
						// 调整后的基准利率ID
						info.setLateBankInterestID(rs.getLong("ID"));
						// 调整生效时间
						info.setAdjustDate(rs.getTimestamp("dtStartDate"));
						bIsHaveLate = true;
						// 调整后的利率格式化
						info.setLateRateString(DataFormat.formatRate(info
								.getLateRate(), 6));
					}
					rs.close();
					rs = null;
					ps.close();
					ps = null;
				}
				// 如果利率未调整过，取未调整的原始利率值
				if (!bIsHaveLate) {
					info.setLateBankInterestID(info.getBankInterestID());
					info.setLateBasicRate(info.getBasicRate());
					info.setLateAdjustRate(info.getAdjustRate());
					info.setLateStaidAdjustRate(info.getStaidAdjustRate());
					info.setLateRate(info.getRate());
					info.setLateRateString(info.getFormatRate());
				}
			} else if (lInterestTypeID == LOANConstant.InterestRateType.LIBOR) {
				strRate = info.getLiborName();
				if (info.getAdjustRate() < 0) {
					strRate = strRate
							+ " - "
							+ DataFormat.formatRate(java.lang.Math.abs(info
									.getAdjustRate()));
				} else if (info.getAdjustRate() > 0) {
					strRate = strRate
							+ " + "
							+ DataFormat.formatRate(java.lang.Math.abs(info
									.getAdjustRate()));
				}
				if (info.getStaidAdjustRate() < 0) {
					strRate = strRate
							+ " - "
							+ DataFormat.formatRate(java.lang.Math.abs(info
									.getStaidAdjustRate()));
				} else if (info.getStaidAdjustRate() > 0) {
					strRate = strRate
							+ " + "
							+ DataFormat.formatRate(java.lang.Math.abs(info
									.getStaidAdjustRate()));
				}
				info.setLateRateString(strRate);

				info.setAdjustRate(info.getAdjustRate());
				info.setStaidAdjustRate(info.getStaidAdjustRate());
			}
			// 针对委托贷款
			else {
				info.setLateBankInterestID(info.getBankInterestID());
				info.setLateBasicRate(info.getBasicRate());
				info.setLateAdjustRate(info.getAdjustRate());
				info.setLateStaidAdjustRate(info.getStaidAdjustRate());
				info.setLateRate(info.getRate());
				info.setLateRateString(info.getFormatRate());
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
		return info;
	}

	public double getBalanceForAttornment(long conID) throws Exception {
		double realBalance = 0;
		double balance = 0;
		double aheadAmount = 0;
		ContractAmountInfo amountInfo = null;

		amountInfo = getLateAmount(conID);
		balance = amountInfo.getBalanceAmount();
		aheadAmount = amountInfo.getAheadAmount();
		realBalance = balance - aheadAmount;

		return realBalance;
	}

	// Boxu Update 2008年11月6日 资产转让按放款单进行转让
	public double getPayformBalanceForAttornment(long lPayID) throws Exception {
		double realBalance = 0;
		double balance = 0;
		double aheadAmount = 0;
		ContractAmountInfo amountInfo = null;

		amountInfo = getPayformLateAmount(lPayID);
		balance = amountInfo.getBalanceAmount();
		aheadAmount = amountInfo.getAheadAmount();

		// 这里不减去提前还款单金额,获取子账户实际金额
		// realBalance = balance - aheadAmount;
		realBalance = balance;

		return realBalance;
	}

	/**
	 * 得到合同当前金额 Create Date: 2003-10-15
	 * 
	 * @param lContractID
	 *            合同ID
	 * @return ContractAmountInfo
	 * @exception Exception
	 */
	public ContractAmountInfo getLateAmount(long lContractID) throws Exception {
		ContractAmountInfo info = new ContractAmountInfo();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		try {
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();

			long type = -1;
			if (lContractID > 0) {
				sbSQL.append(" SELECT nTypeid");
				sbSQL.append(" FROM loan_contractform ");
				sbSQL.append(" WHERE id = ?");

				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lContractID);
				rs = ps.executeQuery();

				if (rs.next()) {
					type = rs.getLong(1);
				}
				ps.close();
				ps = null;

				// if (type == LOANConstant.LoanType.YTDQ || type ==
				// LOANConstant.LoanType.YTZCQ)
				if (type == LOANConstant.LoanType.YT) {
					info = getYTLateAmount(lContractID);
				} else {
					sbSQL.setLength(0);
					sbSQL
							.append(" SELECT SUM(nvl(a.mOpenAmount,0)) OpenAmount,SUM(nvl(a.mBalance,0)) Balance,SUM(nvl(a.mInterest,0)) Interest");
					sbSQL
							.append(" FROM sett_subAccount a,(select ID,nContractID,nstatusid from loan_PayForm union select ID,nContractID,nstatusid from loan_DiscountCredence) b");
					sbSQL.append(" WHERE a.AL_nLoanNoteID = b.ID ");
					sbSQL.append(" AND b.nContractID = ? and a.nstatusid<>0 ");
					// added by mzh_fu 2007/08/20
					sbSQL.append(" AND b.nstatusid <>0 ");

					log4j.info(sbSQL.toString());
					ps = con.prepareStatement(sbSQL.toString());
					ps.setLong(1, lContractID);
					rs = ps.executeQuery();

					if (rs.next()) {
						info.setBalanceAmount(rs.getDouble("Balance")); // 合同余额
						info.setOpenAmount(rs.getDouble("OpenAmount")); // 合同已发放金额
						info.setRepayAmount(rs.getDouble("OpenAmount")
								- rs.getDouble("Balance")); // 合同已还金额
						info.setInterestAmount(rs.getDouble("Interest")); // 合同应收利息
					}
					ps.close();
					ps = null;

					sbSQL.setLength(0);
					sbSQL
							.append(" SELECT c.mExamineAmount,c.nTypeID,c.NISCIRCLE");
					sbSQL.append(" FROM loan_contractform c");
					sbSQL.append(" WHERE c.id = ? ");

					log4j.info(sbSQL.toString());
					ps = con.prepareStatement(sbSQL.toString());
					ps.setLong(1, lContractID);
					rs = ps.executeQuery();

					double dTmp = 0;
					int NISCIRCLE = 0;
					if (rs.next()) {
						NISCIRCLE = rs.getInt("NISCIRCLE");
						// if (rs.getLong("nTypeID") ==
						// LOANConstant.LoanType.ZGXEDQ || rs.getLong("nTypeID")
						// == LOANConstant.LoanType.ZGXEZCQ)
						if (type == LOANConstant.LoanType.ZGXE) {
							dTmp = rs.getDouble("mExamineAmount")
									- info.getBalanceAmount();
						} else {
							dTmp = rs.getDouble("mExamineAmount")
									- info.getOpenAmount();
						}
						info.setUnPayAmount(dTmp); // 未发放金额
					}
					ps.close();
					ps = null;

					// 循环贷款已还金额（2009-10-12）
					if (NISCIRCLE == 1) {
						sbSQL.setLength(0);
						sbSQL
								.append(" select nvl(sum(nvl(c.mAmount,0)), 0) backAmount");
						sbSQL.append(" from sett_transRepaymentloan c");
						sbSQL
								.append(" where c.nloancontractid = ? and c.nstatusid = "
										+ SETTConstant.TransactionStatus.CHECK);

						log4j.info(sbSQL.toString());
						ps = con.prepareStatement(sbSQL.toString());
						ps.setLong(1, lContractID);
						rs = ps.executeQuery();

						if (rs.next()) {
							dTmp = dTmp + rs.getDouble("backAmount");
							info.setUnPayAmount(dTmp); // 未发放金额
						}
						ps.close();
						ps = null;
					}

					sbSQL.setLength(0);
					sbSQL.append(" SELECT sum(mAmount) as aheadAmount ");
					sbSQL.append(" FROM LOAN_AHEADREPAYFORM");
					sbSQL.append(" WHERE nContractID = ? ");
					sbSQL.append(" and nStatusID in (2,3) ");

					log4j.info(sbSQL.toString());
					ps = con.prepareStatement(sbSQL.toString());
					ps.setLong(1, lContractID);
					rs = ps.executeQuery();

					double aheadAmount = 0;
					if (rs.next()) {
						aheadAmount = rs.getDouble("aheadAmount");
						info.setAheadAmount(aheadAmount);
					}
					ps.close();
					ps = null;

				}
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
		return info;
	}

	/**
	 * 得到合同放款单当前金额 Boxu Update 2008年11月6日 资产转让按放款单进行转让
	 * 
	 * @param lPayID
	 *            放款单ID
	 * @return ContractAmountInfo
	 * @exception Exception
	 */
	public ContractAmountInfo getPayformLateAmount(long lPayID)
			throws Exception {
		ContractAmountInfo info = new ContractAmountInfo();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		try {
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();

			if (lPayID > 0) {
				sbSQL.setLength(0);
				sbSQL
						.append(" SELECT SUM(nvl(a.mOpenAmount,0)) OpenAmount,SUM(nvl(a.mBalance,0)) Balance,SUM(nvl(a.mInterest,0)) Interest");
				sbSQL.append(" FROM sett_subAccount a, loan_PayForm b");
				sbSQL.append(" WHERE a.AL_nLoanNoteID = b.ID ");
				sbSQL
						.append(" AND b.id = ? and a.al_nloannoteid = ? and a.nstatusid<>0 ");
				sbSQL.append(" AND b.nstatusid <> 0 ");

				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lPayID);
				ps.setLong(2, lPayID);
				rs = ps.executeQuery();

				if (rs.next()) {
					info.setBalanceAmount(rs.getDouble("Balance")); // 合同余额
					info.setOpenAmount(rs.getDouble("OpenAmount")); // 合同已发放金额
					info.setRepayAmount(rs.getDouble("OpenAmount")
							- rs.getDouble("Balance")); // 合同已还金额
					info.setInterestAmount(rs.getDouble("Interest")); // 合同应收利息
				}
				ps.close();
				ps = null;

				sbSQL.setLength(0);
				sbSQL.append(" SELECT sum(mAmount) as aheadAmount ");
				sbSQL.append(" FROM LOAN_AHEADREPAYFORM ");
				sbSQL.append(" WHERE nloanpaynoticeid = ? ");
				sbSQL.append(" and nStatusID in (2,3) ");

				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lPayID);
				rs = ps.executeQuery();

				double aheadAmount = 0;
				if (rs.next()) {
					aheadAmount = rs.getDouble("aheadAmount");
					info.setAheadAmount(aheadAmount);
				}
			}
		} catch (Exception e) {
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
				throw new IException("Gen_E001");
			}
		}

		return info;
	}

	/**
	 * 得到合同当前金额 Create Date: 2003-10-15
	 * 
	 * @param lContractID
	 *            合同ID
	 * @return ContractAmountInfo
	 * @exception Exception
	 */
	public ContractAmountInfo getGuoDianLateAmount(long lContractID, long lPayID)
			throws Exception {
		ContractAmountInfo info = new ContractAmountInfo();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		try {
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();

			long type = -1;
			if (lContractID > 0) {
				sbSQL.append(" SELECT nTypeid");
				sbSQL.append(" FROM loan_contractform ");
				sbSQL.append(" WHERE id = ?");

				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lContractID);
				rs = ps.executeQuery();

				if (rs.next()) {
					type = rs.getLong(1);
				}
				ps.close();
				ps = null;

				// if (type == LOANConstant.LoanType.YTDQ || type ==
				// LOANConstant.LoanType.YTZCQ)
				if (type == LOANConstant.LoanType.YT) {
					info = getYTLateAmount(lContractID);
				} else {
					sbSQL.setLength(0);
					sbSQL
							.append(" SELECT SUM(nvl(a.mOpenAmount,0)) OpenAmount,SUM(nvl(a.mBalance,0)) Balance,SUM(nvl(a.mInterest,0)) Interest");
					sbSQL
							.append(" FROM sett_subAccount a,(select ID,nContractID,nstatusid from loan_PayForm union select ID,nContractID,nstatusid from loan_DiscountCredence) b");
					sbSQL.append(" WHERE a.AL_nLoanNoteID = b.ID ");
					sbSQL
							.append(" AND b.nContractID = ? and a.al_nloannoteid = ? and a.nstatusid <> 0 ");
					sbSQL.append(" AND b.nstatusid <> 0 ");

					log4j.info(sbSQL.toString());
					ps = con.prepareStatement(sbSQL.toString());
					ps.setLong(1, lContractID);
					ps.setLong(2, lPayID);
					rs = ps.executeQuery();

					if (rs.next()) {
						info.setBalanceAmount(rs.getDouble("Balance")); // 合同余额
						info.setOpenAmount(rs.getDouble("OpenAmount")); // 合同已发放金额
						info.setRepayAmount(rs.getDouble("OpenAmount")
								- rs.getDouble("Balance")); // 合同已还金额
						info.setInterestAmount(rs.getDouble("Interest")); // 合同应收利息
					}
					ps.close();
					ps = null;

					sbSQL.setLength(0);
					sbSQL.append(" SELECT c.mExamineAmount,c.nTypeID");
					sbSQL.append(" FROM loan_contractform c");
					sbSQL.append(" WHERE c.id = ? ");

					log4j.info(sbSQL.toString());
					ps = con.prepareStatement(sbSQL.toString());
					ps.setLong(1, lContractID);
					rs = ps.executeQuery();

					double dTmp = 0;
					if (rs.next()) {
						// if (rs.getLong("nTypeID") ==
						// LOANConstant.LoanType.ZGXEDQ || rs.getLong("nTypeID")
						// == LOANConstant.LoanType.ZGXEZCQ)
						if (type == LOANConstant.LoanType.ZGXE) {
							dTmp = rs.getDouble("mExamineAmount")
									- info.getBalanceAmount();
						} else {
							dTmp = rs.getDouble("mExamineAmount")
									- info.getOpenAmount();
						}
						info.setUnPayAmount(dTmp); // 未发放金额
					}
					ps.close();
					ps = null;

					sbSQL.setLength(0);
					sbSQL.append(" SELECT sum(mAmount) as aheadAmount ");
					sbSQL.append(" FROM LOAN_AHEADREPAYFORM");
					sbSQL.append(" WHERE nContractID = ? ");
					sbSQL.append(" and nStatusID in (2,3) ");

					log4j.info(sbSQL.toString());
					ps = con.prepareStatement(sbSQL.toString());
					ps.setLong(1, lContractID);
					rs = ps.executeQuery();

					double aheadAmount = 0;
					if (rs.next()) {
						aheadAmount = rs.getDouble("aheadAmount");
						info.setAheadAmount(aheadAmount);
					}
					ps.close();
					ps = null;

				}
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

		return info;
	}

	/**
	 * 得到银团合同当前金额 Create Date: 2003-10-15
	 * 
	 * @param lContractID
	 *            合同ID
	 * @return ContractAmountInfo
	 * @exception Exception
	 */
	public ContractAmountInfo getYTLateAmount(long lContractID)
			throws Exception {
		ContractAmountInfo info = new ContractAmountInfo();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		try {
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();

			sbSQL.setLength(0);
			sbSQL.append(" SELECT SUM(b.mAmount) OpenAmount,");
			sbSQL
					.append(" SUM(ROUND((b.mAmount-nvl(dd.mAmount,0)),2)) Balance");
			sbSQL.append(" FROM sett_subAccount a,loan_payform b");

			sbSQL
					.append(" ,(SELECT NVL(SUM(aa.mAmount),0) mAmount,aa.nFormid nFormid");
			sbSQL
					.append(" FROM SETT_SYNDICATIONLOANINTEREST aa, SETT_TRANSREPAYMENTLOAN bb");
			sbSQL.append(" WHERE bb.id = aa.nsyndicationLoanReceiveID");
			sbSQL.append(" AND bb.nStatusID=3");
			sbSQL.append(" GROUP BY aa.nFormid) dd");

			sbSQL.append(" WHERE a.AL_nLoanNoteID = b.ID ");
			sbSQL.append(" AND b.id = dd.nFormid(+)");
			sbSQL.append(" AND b.nContractID = ? ");
			// added by mzh_fu 2007/08/17
			sbSQL.append(" AND b.nstatusid <> 0 ");
			sbSQL.append(" AND a.nstatusid <> 0 ");

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lContractID);
			rs = ps.executeQuery();

			if (rs.next()) {
				info.setBalanceAmount(rs.getDouble("Balance")); // 合同余额
				info.setOpenAmount(rs.getDouble("OpenAmount")); // 合同已发放金额
				info.setRepayAmount(rs.getDouble("OpenAmount")
						- rs.getDouble("Balance")); // 合同已还金额
			}
			ps.close();
			ps = null;

			sbSQL.setLength(0);
			sbSQL.append(" SELECT c.mExamineAmount,c.nTypeID");
			sbSQL.append(" FROM loan_contractform c");
			sbSQL.append(" WHERE c.id = ? ");

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lContractID);
			rs = ps.executeQuery();

			double dTmp = 0;
			if (rs.next()) {
				dTmp = rs.getDouble("mExamineAmount") - info.getOpenAmount();
				info.setUnPayAmount(dTmp); // 未发放金额
			}
			ps.close();
			ps = null;

			sbSQL.setLength(0);
			sbSQL.append(" SELECT sum(mAmount) as aheadAmount ");
			sbSQL.append(" FROM LOAN_AHEADREPAYFORM");
			sbSQL.append(" WHERE nContractID = ? ");
			sbSQL.append(" and nStatusID in (2,3) ");

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lContractID);
			rs = ps.executeQuery();

			double aheadAmount = 0;
			if (rs.next()) {
				aheadAmount = rs.getDouble("aheadAmount");
				info.setAheadAmount(aheadAmount);
			}
			ps.close();
			ps = null;

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
		return info;
	}

	/**
	 * 根据合同ID查询合同是否结束返回执行日期 Create Date: 2006-3-16
	 * 
	 * @param lID
	 *            合同ID
	 * @return ContractInfo 合同详细信息
	 * @throws Exception
	 * @exception Exception
	 */
	public Timestamp findfinshDate(long lID) throws Exception {
		PreparedStatement ps = null;
		String strSQL = "";
		ResultSet rs = null;
		Connection con = null;
		Timestamp finshDate = null;
		try {
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT MAX(pay.DTEXECUTE) AS DTEXECUTE ,");
			sbSQL
					.append(" pay.NLOANCONTRACTID as id FROM SETT_TRANSREPAYMENTLOAN pay , ");
			sbSQL.append(" SETT_TRANSREPAYMENTDISCOUNT paycount where 1=1 ");
			sbSQL
					.append(" AND pay.NLOANCONTRACTID = paycount.NDISCOUNTCONTRACTID ");
			sbSQL.append(" AND pay.NSTATUSID=").append(
					LOANConstant.ContractStatus.FINISH);
			sbSQL.append(" AND pay.NLOANCONTRACTID=? ");
			sbSQL.append(" group by pay.NLOANCONTRACTID ");
			System.out.println(sbSQL.toString());
			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			if (rs.next()) {
				finshDate = rs.getTimestamp("DTEXECUTE");
			}
		} catch (SQLException e) {
			throw e;
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
				throw e;
			}
		}
		return finshDate;
	}

	public ContractInfo findContractBasicInfoByID(long lID) throws Exception {
		ContractInfo info = null;
		PreparedStatement ps = null;
		String strSQL = "";
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.getConnection();
			strSQL = "select * from loan_contractform where id = ? ";
			ps = con.prepareStatement(strSQL.toString());
			ps.setLong(1, lID);

			rs = ps.executeQuery();

			if (rs.next()) {
				info = new ContractInfo();
				info.setLoanTypeID(rs.getLong("NTYPEID"));
				info.setSubTypeID(rs.getLong("NSUBTYPEID"));
				info.setContractCode(rs.getString("SCONTRACTCODE"));
				info.setLoanAmount(rs.getDouble("MLOANAMOUNT"));
				info.setApplyCode(rs.getString("SAPPLYCODE"));
			}

		} catch (Exception e) {
			throw e;
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
					if (!con.isClosed())
						con.close();
					con = null;
				}
			} catch (Exception e) {
				throw e;
			}
		}
		return info;
	}

	/**
	 * 得到合同详细信息 Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            合同ID
	 * @return ContractInfo 合同详细信息
	 * @exception Exception
	 */
	public ContractInfo findByID(long lID) throws Exception {
		ContractInfo info = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		ResultSet rs4 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		ResultSet rs5 = null;
		PreparedStatement ps5 = null;
		String strSQL = "";
		ResultSet rs = null;
		Connection con = null;

		try {
			con = Database.getConnection();
			Timestamp lastExecDate = findfinshDate(lID);
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT a.*,");
			sbSQL.append(" b.sCode as sClientCode,b.sName as sClientName,");
			sbSQL.append(" b.sAccount as ClientAccount,");
			sbSQL.append(" iu.sName as InputUser,cu.sName as CheckUser,");
			sbSQL.append(" m.AssureAmount,");
			sbSQL.append(" l.id as PlanID,");
			sbSQL.append(" c.sname as consignname , c.scode as consigncode , ");
			sbSQL
					.append(" c.saddress as consignaddress ,c.sbank1 as consignbank,");
			sbSQL.append(" c.SPROVINCE as cSPROVINCE ,c.SCITY as cSCITY,");
			sbSQL
					.append(" c.sAccount as consignaccount , c.sZipCode as consignzip,");
			sbSQL.append(" b.sname as borrowname,b.scode as borrowcode,");
			sbSQL
					.append(" b.saddress as borrowaddress,b.sbank1 as borrowbank,");
			sbSQL
					.append(" b.sAccount as borrowaccount , b.sZipCode as borrowzip ,");
			sbSQL.append(" b.SPROVINCE as SPROVINCE , b.SCITY as SCITY ,");
			sbSQL.append(" e.sname as assurename,e.scode as assurecode,");
			sbSQL
					.append(" e.saddress as assureaddress,e.sbank1 as assurebank,");
			sbSQL
					.append(" e.SPROVINCE as assureSPROVINCE,e.SCITY as assureSCITY,");
			sbSQL
					.append(" e.sAccount as assureaccount , e.sZipCode as assurezip ");
			sbSQL.append(" FROM loan_contractForm a,client b,");
			sbSQL.append(" userInfo iu,userInfo cu,loan_loanContractPlan l,");
			sbSQL.append(" (SELECT SUM(mAmount)as AssureAmount,nContractID ");
			sbSQL.append(" FROM loan_loanContractAssure");
			sbSQL.append(" WHERE nStatusID = " + Constant.RecordStatus.VALID);
			sbSQL.append(" GROUP BY nContractID ) m");
			sbSQL.append(" ,client c, client e, loan_loancontractassure f");
			sbSQL.append(" WHERE a.id = m.nContractID(+)");
			sbSQL.append(" AND a.id = l.nContractID(+)");
			sbSQL.append(" AND l.nStatusID(+)=" + Constant.RecordStatus.VALID);
			// 2004-01-15
			// ninh
			sbSQL.append(" AND a.nInputUserID = iu.id(+)");
			sbSQL.append(" AND a.nNextCheckUserID = cu.id(+)");
			sbSQL.append(" AND a.nBorrowClientID = b.id(+)");
			sbSQL.append(" AND a.id = ? ");
			sbSQL.append(" AND a.nconsignclientid = c.id(+) ");
			sbSQL.append(" AND a.id = f.ncontractid(+) and e.id(+) = f.id ");

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lID);

			rs = ps.executeQuery();

			if (rs.next()) {
				info = new ContractInfo();
				info.setLastExecDate(lastExecDate);// 合同最后执行日期
				info.setIsBuyInto(rs.getLong("isBuyInto"));
				info.setLoanID(rs.getLong("nLoanID")); // 贷款ID
				info.setContractID(rs.getLong("id")); // 合同ID
				info.setOfficeID(rs.getLong("NOFFICEID")); // 办事处id
				info.setCurrencyID(rs.getLong("NCURRENCYID")); // 币种
				info.setInputDate(rs.getTimestamp("dtInputDate")); // 合同录入日期
				info.setLoanTypeID(rs.getLong("nTypeID")); // 贷款类型
				info.setSubTypeID(rs.getLong("nSubTypeID")); // 贷款zi类型
				info.setIsCircle(rs.getLong("niscircle"));// 是否循环贷款
				info.setContractCode(rs.getString("sContractCode")); // 合同编号
				info.setApplyCode(rs.getString("sApplyCode")); // 申请书编号
				info.setBorrowClientID(rs.getLong("nBorrowClientID")); // 借款单位ID
				info.setBorrowClientName(rs.getString("sClientName")); // 借款单位
				info.setBorrowClientCode(rs.getString("sClientCode")); // 客户编号
				info.setLoanAmount(rs.getDouble("mLoanAmount")); // 借款金额
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 期限
				info.setLoanReason(rs.getString("SLOANREASON"));// 借款原因
				info.setOther(rs.getString("SOTHER")); // 其他
				info.setRiskLevel(rs.getLong("nRiskLevel")); // 合同风险等级
				info.setInterestTypeID(rs.getLong("nInterestTypeID")); // 利率类型
				info.setLiborRateID(rs.getLong("nLiborRateID")); // Libor利率ID
				info.setDiscountDate(rs.getTimestamp("dtDiscountDate"));// 贴现日期
				info.setPurchaseAmount(rs.getDouble("MPURCHASERAMOUNT"));
				info.setDiscountAccrual(rs.getDouble("MDISCOUNTACCRUAL"));
				RateInfo rInfo = new RateInfo();
				rInfo = getLatelyRate(-1, info.getContractID(), null);
				// added by xiong fei 2010-07-27 RZZL利率获取
				if (rs.getLong("nTypeID") == LOANConstant.LoanType.RZZL) {
					info.setLateRateString(String.valueOf(this
							.getLatelyRateForRZZL(
									rs.getLong("id"),
									Env.getSystemDate(rs.getLong("NOFFICEID"),
											rs.getLong("NCURRENCYID")))
							.getRate()));
				} else {
					info.setLateRateString(rInfo.getLateRateString()); // 执行利率
				}
				info.setInterestRate(rInfo.getLateRate());// 利率，字符串格式
				info.setRate(rInfo.getRate()); // 调整前利率
				info.setBasicInterestRate(rInfo.getLateBasicRate()); // 基准利率
				info.setBankInterestID(rInfo.getLateBankInterestID());
				info.setLastAttornmentAmount(rs
						.getDouble("lastAttornmentAmount"));

				// ======ninh 2004-06-22 需求变更 增加固定浮动利率======//
				// info.setStaidAdjustRate(rs.getDouble("mStaidAdjustRate"));
				info.setStaidAdjustRate(rInfo.getLateStaidAdjustRate());
				// 银行利率ID
				// info.setAdjustRate(rs.getDouble("mAdjustRate")); //利率调整
				info.setAdjustRate(rInfo.getLateAdjustRate());

				// =========gqfang，Libor利率需求的需要
				if (rs.getLong("nInterestTypeID") == LOANConstant.InterestRateType.LIBOR) {
					info.setStaidAdjustRate(rInfo.getStaidAdjustRate());
					info.setAdjustRate(rInfo.getAdjustRate());
				}

				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 合同起始日期
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 合同结束日期
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // 借款用途
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // 审批金额
				info.setExamineSelfAmount(rs.getDouble("mExamineSelfAmount")); // 批准财务公司承贷金额
				info.setAssureAmount(rs.getDouble("AssureAmount")); // 有担保贷款总额
				info.setChargeRate(rs.getDouble("mChargeRate")); // 手续费率
				info.setChargeRateType(rs.getLong("nChargeRateTypeID"));

				info.setIsRemitCompoundInterest(rs
						.getLong("isremitcompoundinterest"));// 是否计算复利
				info.setIsRemitOverDueInterest(rs
						.getLong("isremitoverdueinterest"));// 是否计算逾期
				info.setOverDueAdjustRate(rs.getDouble("overdueadjustrate"));// 比例浮动
				info.setOverDueStaidAdjustRate(rs
						.getDouble("overduestaidadjustrate"));// 固定浮动
				if(info.getIsRemitOverDueInterest()>0){
				info.setOverDueInterestRate((Double.valueOf(info
						.getLateRateString()).doubleValue())
						* (1 + info.getOverDueAdjustRate() / 100)
						+ info.getOverDueStaidAdjustRate()); // 逾期执行利率
				}else{
					info.setOverDueInterestRate(info.getOverDueStaidAdjustRate());
				}
				// 手续费率类型
				info.setClientID(rs.getLong("nConsignClientID")); // 委托单位

				if (info.getClientID() > 0) {
					info.setClientName(getClientName(info.getClientID()));
				}

				if (info.getExamineAmount() != 0) // 担保金额占审批金额的比例
				{
					info.setAssureRate(info.getAssureAmount()
							/ info.getExamineAmount());
				} else {
					info.setAssureRate(0);
				}

				if ((info.getExamineAmount() - info.getAssureAmount()) > 0) {
					info.setCreditAmount(info.getExamineAmount()
							- info.getAssureAmount());
				} else {
					info.setCreditAmount(0);
				}

				// 信用贷款总额（审批金额 - 担保金额）

				if (info.getExamineAmount() != 0) // 信用贷款金额占审批金额的比例
				{
					info.setCreditRate(info.getCreditAmount()
							/ info.getExamineAmount());
				} else {
					info.setCreditRate(0);
				}

				// ----------------添加放款通知单信息。
				String sql = "select nloanpaynoticeid from loan_rateadjustpaycondition where ncontractid=?";
				ps3 = con.prepareStatement(sql);
				ps3.setLong(1, lID);
				rs3 = ps3.executeQuery();
				String loanNoticeId = "";
				while (rs3.next()) {
					if (rs3.getString("nloanpaynoticeid") != null
							&& !"-1".equals(rs3.getString("nloanpaynoticeid"))) {
						loanNoticeId = rs3.getString("nloanpaynoticeid");
						sql = "select scode from LOAN_PayForm  where id="
								+ loanNoticeId;
						ps5 = con.prepareStatement(sql);
						rs5 = ps5.executeQuery();
						while (rs5.next()) {
							loanNoticeId = rs5.getString("SCODE");
						}
					}
					if (rs3.getString("nloanpaynoticeid") != null
							&& "-1".equals(rs3.getString("nloanpaynoticeid"))) {
						sql = "select  SCODE   from  LOAN_PayForm where NCONTRACTID=?  order by scode";
						ps4 = con.prepareStatement(sql);
						ps4.setLong(1, lID);
						rs4 = ps4.executeQuery();
						String lNids = "";
						while (rs4.next()) {
							String lNid = rs4.getString("SCODE");
							lNids += lNid + "，";
						}
						if (lNids.indexOf("，") != -1) {
							lNids = lNids.substring(0, lNids.lastIndexOf("，"));
						}
						loanNoticeId = lNids;
					}
				}
				info.setPayCode(loanNoticeId);
				// ------------------------------------

				info.setAreaType(rs.getLong("nTypeID1")); // 按地区分类
				info.setIndustryType1(rs.getLong("nTypeID2")); // 按行业分类1
				info.setIndustryType2(rs.getLong("nTypeID3")); // 按行业分类2
				info.setIndustryType3(rs.getLong("nTypeID4")); // 按行业分类3

				info.setAssure(getAssure(info.getContractID())); // 保证信息
				info
						.setContractContent(getContractContent(info
								.getContractID())); // 合同文本信息
				info.setYTInfo(getFormatYT(getYT(info.getContractID()))); // 银团信息
				// info.setScale(rs.getDouble("mSelfAmount"));

				info.setLoanAccount(rs.getString("ClientAccount")); // 贷款单位账号
				info.setInputUserID(rs.getLong("nInputUserID")); // 录入人id
				info.setInputUserName(rs.getString("InputUser")); // 录入人
				info.setCheckUserName(rs.getString("CheckUser")); // 审核人
				info.setStatusID(rs.getLong("nStatusID")); // 合同状态
				info.setStatus(LOANConstant.ContractStatus.getName(info
						.getStatusID())); // 合同状态名称

				info.setPlanVersionID(rs.getLong("PlanID")); // 合同执行计划版本号ID
				info.setDiscountRate(rs.getDouble("mDiscountRate")); // 贴现利率
				info.setCheckAmount(rs.getDouble("mCheckAmount")); // 汇总贴现核定金额
				info.setIsPurchaserInterest(rs.getLong("IsPurchaserInterest")); // 是否买方付息
				info.setDiscountClientID(rs.getLong("discountClientID")); // 出票人
				info.setDiscountClientName(rs.getString("discountClientName")); // 出票人名称
				info.setPurchaserInterestRate(rs
						.getDouble("purchaserInterestRate")); // 买方付息比例

				// 担保业务相关
				info.setAssureChargeRate(rs.getDouble("AssureChargeRate")); // 担保费率
				info.setAssureChargeTypeID(rs.getLong("AssureChargeTypeID")); // 担保费收取方式
				info.setBeneficiary(rs.getString("Beneficiary")); // 受益人
				info.setAssureTypeID1(rs.getLong("AssureTypeID1")); // 担保类型1
				info.setAssureTypeID2(rs.getLong("AssureTypeID2")); // 担保类型2

				// 融资租赁新增
				info.setChargeAmount(rs.getDouble("MCHARGEAMOUNT"));
				info.setInterestCountTypeID(rs.getLong("NINTERESTCOUNTTYPEID"));
				info.setMatureNominalAmount(rs
						.getDouble("MMATURENOMINALAMOUNT"));
				// 融资租赁新增结束

				ContractAmountInfo aInfo = new ContractAmountInfo();
				aInfo = getLateAmount(info.getContractID());
				info.setAInfo(aInfo); // 合同金额
				info.setBalance(aInfo.getBalanceAmount()); // 合同当前余额
				info.setBalanceForAttornment(aInfo.getBalanceAmount()
						- aInfo.getAheadAmount());

				// added by xiong fei 2010/05/26 融资租赁新增
				info.setOrigionAmount(rs.getDouble("morigionamount"));
				info.setPreAmount(rs.getDouble("mpreamount"));
				info.setChargeAmountRate(rs.getDouble("mchargeamountrate"));
				info.setIsRepurchase(rs.getLong("isrepurchase"));

				info.setIsCredit(rs.getLong("niscredit")); // 信用
				info.setIsAssure(rs.getLong("nisassure")); // 保证
				info.setIsImpawn(rs.getLong("nisimpawn")); // 抵押
				info.setIsPledge(rs.getLong("nispledge")); // 质押
				info.setIsRecognizance(rs.getLong("IsRecognizance"));// 保证金

				// 以下是为打印取出的字段
				// 委托相关信息
				info.setConsignName(rs.getString("consignname"));
				info.setConsignCode(rs.getString("consigncode"));
				info.setConsignAddress((rs.getString("cSPROVINCE") == null ? ""
						: rs.getString("cSPROVINCE") + " 省 ")
						+ (rs.getString("cSCITY") == null ? "" : rs
								.getString("cSCITY")
								+ " 市 ")
						+ (rs.getString("consignaddress") == null ? "" : rs
								.getString("consignaddress")));
				info.setConsignBank(rs.getString("consignbank"));
				info.setConsignAccount(rs.getString("consignaccount"));
				info.setConsignZip(rs.getString("consignzip"));

				// 借款相关信息
				info.setBorrowName(rs.getString("borrowname"));
				info.setBorrowCode(rs.getString("borrowcode"));
				info.setBorrowAddress((rs.getString("SPROVINCE") == null ? ""
						: (rs.getString("SPROVINCE") + " 省 "))
						+ (rs.getString("SCITY") == null ? "" : (rs
								.getString("SCITY") + " 市 "))
						+ (rs.getString("borrowaddress") == null ? "" : rs
								.getString("borrowaddress")));
				// info
				// .setBorrowAddress(rs.getString("SPROVINCE")+"省"
				// + rs.getString("SCITY")+"市"
				// + rs.getString("borrowaddress"));
				info.setBorrowBank(rs.getString("borrowbank"));
				info.setBorrowAccount(rs.getString("borrowaccount"));
				info.setBorrowZip(rs.getString("borrowzip"));

				// 担保相关信息
				info.setAssureName(rs.getString("assurename"));
				info.setAssureCode(rs.getString("assurecode"));
				info
						.setAssureAddress((rs.getString("assureSPROVINCE") == null ? ""
								: rs.getString("assureSPROVINCE") + " 省 ")
								+ (rs.getString("assureSCITY") == null ? ""
										: rs.getString("assureSCITY") + " 市 ")
								+ (rs.getString("assureaddress") == null ? ""
										: rs.getString("assureaddress")));
				info.setAssureBank(rs.getString("assurebank"));
				info.setAssureAccount(rs.getString("assureaccount"));
				info.setAssureZip(rs.getString("assurezip"));
				//
				info.setLeftoversAttornmentAmount(rs
						.getDouble("leftoversAttornmentAmount")); // 债权余额
				// 下一个审核级别
				info.setNextCheckLevel(rs.getLong("nNextCheckLevel"));

				// 取展期数目
				strSQL = "select count(id) from LOAN_EXTENDFORM where"
						+ " NCONTRACTID=" + info.getContractID()
						+ " and nStatusID<>" + Constant.RecordStatus.INVALID;
				ps2 = con.prepareStatement(strSQL);
				rs2 = ps2.executeQuery();
				if (rs2.next()) {
					info.setExtendCount(rs2.getLong(1));
				}
				if (rs2 != null) {
					rs2.close();
					rs2 = null;
				}
				if (ps2 != null) {
					ps2.close();
					ps2 = null;
				}
				if (rs5 != null) {
					rs5.close();
					rs5 = null;
				}
				if (ps5 != null) {
					ps5.close();
					ps5 = null;
				}
				if (rs3 != null) {
					rs3.close();
					rs3 = null;
				}
				if (ps3 != null) {
					ps3.close();
					ps3 = null;
				}
				if (rs4 != null) {
					rs4.close();
					rs4 = null;
				}
				if (ps4 != null) {
					ps4.close();
					ps4 = null;
				}
				// 取免还数目
				strSQL = " select count(id) from LOAN_FREEFORM where NCONTRACTID="
						+ info.getContractID()
						+ " and nStatusID<>"
						+ Constant.RecordStatus.INVALID;
				ps2 = con.prepareStatement(strSQL);
				rs2 = ps2.executeQuery();
				if (rs2.next()) {
					info.setFreeCount(rs2.getLong(1));
					System.out.println("" + info.getFreeCount());
				}
				if (rs2 != null) {
					rs2.close();
					rs2 = null;
				}
				if (ps2 != null) {
					ps2.close();
					ps2 = null;
				}

				// 如果是担保或融资租赁
				if (info.getLoanTypeID() == LOANConstant.LoanType.DB) {

					// 取得担保的保证金金额
					strSQL = "select nvl(SUM(mAmount),0) FROM loan_loanContractAssure WHERE nStatusID = "
							+ Constant.RecordStatus.VALID
							+ " and nContractID = "
							+ lID
							+ " and NASSURETYPEID = "
							+ LOANConstant.AssureType.RECOGNIZANCE;

					ps2 = con.prepareStatement(strSQL);
					rs2 = ps2.executeQuery();
					if (rs2.next()) {
						info.setRecognizanceAmount(rs2.getDouble(1));
						System.out.println(" RecognizanceAmount = "
								+ info.getRecognizanceAmount());
					}

					// 取得该客户所有担保的总金额（收款通知单、保后处理用）
					strSQL = "select nvl(sum(mexamineamount),0) sumDBAmount FROM loan_contractform "
							+ " WHERE nStatusID in("
							+ LOANConstant.ContractStatus.NOTACTIVE
							+ ","
							+ LOANConstant.ContractStatus.ACTIVE
							+ ")"
							+ " and nborrowclientid = "
							+ info.getBorrowClientID()
							+ " and ntypeid = "
							+ LOANConstant.LoanType.DB;

					ps2 = con.prepareStatement(strSQL);
					rs2 = ps2.executeQuery();
					if (rs2.next()) {
						info.setSumAssureAmount(rs2.getDouble(1));
						System.out.println(" SumAssureAmount = "
								+ info.getSumAssureAmount());
					}

					// 担保余额--该合同下，所有收款通知单的承保金额-所有保后通知单的撤保金额
					strSQL = "select (aa.receiveAmount-bb.returnAmount) currentBalance from"
							+ "(select nvl(sum(assureAmount),0) receiveAmount from loan_assurechargeform "
							+ " WHERE statusid = "
							+ LOANConstant.AssureChargeNoticeStatus.USED
							+ " and contractid = "
							+ lID
							+ " ) aa,"
							+ "(select nvl(sum(assureAmount),0) returnAmount from loan_assuremanagementform "
							+ " WHERE statusid = "
							+ LOANConstant.AssureManagementNoticeStatus.USED
							+ " and contractid = " + lID + " ) bb";

					ps2 = con.prepareStatement(strSQL);
					rs2 = ps2.executeQuery();
					if (rs2.next()) {
						info.setAssureBalance(rs2.getDouble(1));
						System.out.println(" AssureBalance = "
								+ info.getAssureBalance());
					}
					// /*
					// 担保总余额--该客户所有担保的总余额=所有收款通知单的承保金额-所有保后通知单的撤保金额
					strSQL = "select (aa.receiveAmount-bb.returnAmount) currentBalance from"
							+ "(select nvl(sum(assureAmount),0) receiveAmount from loan_assurechargeform "
							+ " WHERE statusid = "
							+ LOANConstant.AssureChargeNoticeStatus.USED
							+ " and contractid in "
							+ "(select id from loan_contractform where nStatusID in("
							+ LOANConstant.ContractStatus.NOTACTIVE
							+ ","
							+ LOANConstant.ContractStatus.ACTIVE
							+ ")"
							+ " and nborrowclientid = "
							+ info.getBorrowClientID()
							+ " and ntypeid = "
							+ LOANConstant.LoanType.DB
							+ ")"
							+ " ) aa,"
							+ "(select nvl(sum(assureAmount),0) returnAmount from loan_assuremanagementform "
							+ " WHERE statusid = "
							+ LOANConstant.AssureManagementNoticeStatus.USED
							+ " and contractid in "
							+ "(select id from loan_contractform where nStatusID in("
							+ LOANConstant.ContractStatus.NOTACTIVE
							+ ","
							+ LOANConstant.ContractStatus.ACTIVE
							+ ")"
							+ " and nborrowclientid = "
							+ info.getBorrowClientID()
							+ " and ntypeid = "
							+ LOANConstant.LoanType.DB + ")" + " ) bb";

					ps2 = con.prepareStatement(strSQL);
					rs2 = ps2.executeQuery();
					if (rs2.next()) {
						info.setSumAssureBanlance(rs2.getDouble(1));
						System.out.println(" SumAssureBanlance = "
								+ info.getAssureBalance());
					}

					// 取得该合同下已收保证金
					strSQL = "select (aa.receiveAmount-bb.returnAmount) currentAmount from"
							+ "(select nvl(sum(recognizanceamount),0) receiveAmount from loan_assurechargeform "
							+ " WHERE statusid = "
							+ LOANConstant.AssureChargeNoticeStatus.USED
							+ " and contractid = "
							+ lID
							+ " ) aa,"
							+ "(select nvl(sum(recognizanceamount),0) returnAmount from loan_assuremanagementform "
							+ " WHERE statusid = "
							+ LOANConstant.AssureManagementNoticeStatus.USED
							+ " and contractid = " + lID + " ) bb";

					ps2 = con.prepareStatement(strSQL);
					rs2 = ps2.executeQuery();
					if (rs2.next()) {
						info.setCurrentRecognizanceAmount(rs2.getDouble(1));
						System.out.println(" CurrentRecognizanceAmount = "
								+ info.getCurrentRecognizanceAmount());
					}

					/*
					 * //取得该合同已经收取的手续费 strSQL = "select
					 * nvl(sum(ASSURECHARGEAMOUNT),0) receiveAmount from
					 * loan_assurechargeform " + " WHERE statusid = " +
					 * LOANConstant.AssureChargeNoticeStatus.CHECK + " and
					 * contractid = " + lID;
					 * 
					 * ps2 = con.prepareStatement(strSQL); rs2 =
					 * ps2.executeQuery(); if (rs2.next()) {
					 * info.setReceiveAssureChargeAmount(rs2.getDouble(1));
					 * System.out.println(" ReceiveAssureChargeAmount = " +
					 * info.getReceiveAssureChargeAmount()); }
					 * 
					 * //取得该合同已经收取的保证金 strSQL = "select
					 * nvl(sum(RECOGNIZANCEAMOUNT),0) receiveAmount from
					 * loan_assurechargeform " + " WHERE statusid = " +
					 * LOANConstant.AssureChargeNoticeStatus.CHECK + " and
					 * contractid = " + lID;
					 * 
					 * ps2 = con.prepareStatement(strSQL); rs2 =
					 * ps2.executeQuery(); if (rs2.next()) {
					 * info.setReceiveRecognizanceAmount(rs2.getDouble(1));
					 * System.out.println(" ReceiveRecognizanceAmount = " +
					 * info.getReceiveRecognizanceAmount()); }
					 */
					if (rs2 != null) {
						rs2.close();
						rs2 = null;
					}
					if (ps2 != null) {
						ps2.close();
						ps2 = null;
					}
					// */
				} else if (info.getLoanTypeID() == LOANConstant.LoanType.RZZL) {
					// add by zwxiao 2010-07-10 取得融资租赁的最新的利率
					RateInfo rateInfo = getLatelyRateForRZZL(lID, null);
					info.setAdjustRate(rateInfo.getAdjustRate());
					info.setStaidAdjustRate(rateInfo.getStaidAdjustRate());
					info.setRate(rateInfo.getRate());
					info.setBasicInterestRate(rateInfo.getLateBasicRate()); // 基准利率
					// 取得融资租赁保证金金额
					strSQL = "select nvl(SUM(mAmount),0) FROM loan_loanContractAssure WHERE nStatusID = "
							+ Constant.RecordStatus.VALID
							+ " and nContractID = "
							+ lID
							+ " and NASSURETYPEID = "
							+ LOANConstant.AssureType.RECOGNIZANCE;

					ps2 = con.prepareStatement(strSQL);
					rs2 = ps2.executeQuery();
					if (rs2.next()) {
						info.setRecognizanceAmount(rs2.getDouble(1));
					}

					// 取得该合同已收保证金总金额（收款通知单、还款通知单用）
					strSQL = " select nvl(sum(recognizanceamount),0) receiveamount from loan_assurechargeform "
							+ " WHERE statusid = "
							+ LOANConstant.AssureChargeNoticeStatus.USED
							+ " and contractid = " + lID;

					ps2 = con.prepareStatement(strSQL);
					rs2 = ps2.executeQuery();
					if (rs2.next()) {
						info.setReceivedRecognizanceAmount(rs2.getDouble(1));
					}

					// 取得该合同已还保证金总金额（还款通知单用）
					// modified by xiong fei 2010-08-10 已还保证金总额从结算表里取
					strSQL = " select nvl(sum(MBAILAMOUNT),0) returnAmount from SETT_TRANSRETURNFINANCE "
							+ " WHERE NSTATUSID = "
							+ LOANConstant.AheadRepayStatus.CHECK
							+ " and ncontractid = " + lID;

					ps2 = con.prepareStatement(strSQL);
					rs2 = ps2.executeQuery();
					if (rs2.next()) {
						info.setReturnedRecognizanceAmount(rs2.getDouble(1));
					}

					/*
					 * add by yunchang date 2010-08-19 function
					 * 累计已经收保证金（结算--贷款--融资租赁还款--业务处理）
					 */
					strSQL = " select nvl(sum(mbailamount), 0) returnamount from sett_transreceivefinance where nstatusid = "
							+ LOANConstant.LoanPayNoticeStatus.CHECK
							+ " and ncontractid = " + lID + " ";
					ps2 = con.prepareStatement(strSQL);
					rs2 = ps2.executeQuery();
					if (rs2.next()) {
						info.setContractHireAmountForYSALL(rs2.getDouble(1));
					}

					// 取得该合同下有效的收款通知单所收保证金总额（限制一个合同下只有一个保证金>0的收款通知单用）
					strSQL = " select nvl(sum(RECOGNIZANCEAMOUNT),0) returnAmount from LOAN_LEASEHOLDREPAYFORM "
							+ " WHERE NSTATUSID in( "
							+ LOANConstant.AssureChargeNoticeStatus.SUBMIT
							+ ","
							+ LOANConstant.AssureChargeNoticeStatus.CHECK
							+ ","
							+ LOANConstant.AssureChargeNoticeStatus.USED
							+ ") " + " and contractid = " + lID;

					ps2 = con.prepareStatement(strSQL);
					rs2 = ps2.executeQuery();
					if (rs2.next()) {
						info.setCurrentRecognizanceAmount(rs2.getDouble(1));
					}

					if (rs2 != null) {
						rs2.close();
						rs2 = null;
					}
					if (ps2 != null) {
						ps2.close();
						ps2 = null;
					}
				}
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
			throw new IException("Gen_E001");
		} finally {
			try {
				if (rs2 != null) {
					rs2.close();
					rs2 = null;
				}
				if (ps2 != null) {
					ps2.close();
					ps2 = null;
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
				throw new IException("Gen_E001");
			}
		}
		return info;

	}

	/**
	 * 得到合同详细信息 Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            合同ID 放款单lPayID
	 * @return ContractInfo 合同详细信息
	 * @exception Exception
	 */
	public ContractInfo findGuoDianByID(long lID, long lPayID) throws Exception {
		ContractInfo info = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		String strSQL = "";
		ResultSet rs = null;
		Connection con = null;

		try {
			con = Database.getConnection();
			// Timestamp lastExecDate = findfinshDate(lID);
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT a.*,");
			sbSQL.append(" b.sCode as sClientCode,b.sName as sClientName,");
			sbSQL.append(" b.sAccount as ClientAccount,");
			sbSQL.append(" iu.sName as InputUser,cu.sName as CheckUser,");
			sbSQL.append(" m.AssureAmount,");
			sbSQL.append(" l.id as PlanID,");
			sbSQL.append(" c.sname as consignname , c.scode as consigncode , ");
			sbSQL
					.append(" c.saddress as consignaddress ,c.sbank1 as consignbank,");
			sbSQL.append(" c.SPROVINCE as cSPROVINCE ,c.SCITY as cSCITY,");
			sbSQL
					.append(" c.sAccount as consignaccount , c.sZipCode as consignzip,");
			sbSQL.append(" b.sname as borrowname,b.scode as borrowcode,");
			sbSQL
					.append(" b.saddress as borrowaddress,b.sbank1 as borrowbank,");
			sbSQL
					.append(" b.sAccount as borrowaccount , b.sZipCode as borrowzip ,");
			sbSQL.append(" b.SPROVINCE as SPROVINCE , b.SCITY as SCITY ,");
			sbSQL.append(" e.sname as assurename,e.scode as assurecode,");
			sbSQL
					.append(" e.saddress as assureaddress,e.sbank1 as assurebank,");
			sbSQL
					.append(" e.SPROVINCE as assureSPROVINCE,e.SCITY as assureSCITY,");
			sbSQL
					.append(" e.sAccount as assureaccount , e.sZipCode as assurezip, ");
			sbSQL
					.append(" lp.id payid, lp.scode paycode, lp.mamount payamount, lp.dtstart startdate, lp.dtend enddate ");
			sbSQL
					.append(" FROM loan_contractForm a,client b, loan_payform lp, ");
			sbSQL.append(" userInfo iu,userInfo cu,loan_loanContractPlan l,");
			sbSQL.append(" (SELECT SUM(mAmount)as AssureAmount,nContractID ");
			sbSQL.append(" FROM loan_loanContractAssure");
			sbSQL.append(" WHERE nStatusID = " + Constant.RecordStatus.VALID);
			sbSQL.append(" GROUP BY nContractID ) m");
			sbSQL.append(" ,client c, client e, loan_loancontractassure f");
			sbSQL.append(" WHERE a.id = m.nContractID(+)");
			sbSQL.append(" AND a.id = lp.ncontractid ");
			sbSQL.append(" AND a.id = l.nContractID(+)");
			sbSQL.append(" AND l.nStatusID(+)=" + Constant.RecordStatus.VALID);
			sbSQL.append(" AND a.nInputUserID = iu.id(+)");
			sbSQL.append(" AND a.nNextCheckUserID = cu.id(+)");
			sbSQL.append(" AND a.nBorrowClientID = b.id(+)");
			sbSQL.append(" AND lp.id = ? ");
			sbSQL.append(" AND a.nconsignclientid = c.id(+) ");
			sbSQL.append(" AND a.id = f.ncontractid(+) and e.id(+) = f.id ");

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lPayID);

			rs = ps.executeQuery();

			if (rs.next()) {
				info = new ContractInfo();
				// info.setLastExecDate(lastExecDate);// 合同最后执行日期
				info.setLoanID(rs.getLong("nLoanID")); // 贷款ID
				info.setContractID(rs.getLong("id")); // 合同ID
				info.setOfficeID(rs.getLong("NOFFICEID")); // 办事处id
				info.setCurrencyID(rs.getLong("NCURRENCYID")); // 币种
				info.setInputDate(rs.getTimestamp("dtInputDate")); // 合同录入日期
				info.setLoanTypeID(rs.getLong("nTypeID")); // 贷款类型
				info.setSubTypeID(rs.getLong("nSubTypeID")); // 贷款zi类型
				info.setIsCircle(rs.getLong("niscircle"));// 是否循环贷款
				info.setContractCode(rs.getString("sContractCode")); // 合同编号
				info.setApplyCode(rs.getString("sApplyCode")); // 申请书编号
				info.setBorrowClientID(rs.getLong("nBorrowClientID")); // 借款单位ID
				info.setBorrowClientName(rs.getString("sClientName")); // 借款单位
				info.setBorrowClientCode(rs.getString("sClientCode")); // 客户编号
				info.setLoanAmount(rs.getDouble("mLoanAmount")); // 借款金额
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 期限
				info.setLoanReason(rs.getString("SLOANREASON"));// 借款原因
				info.setOther(rs.getString("SOTHER")); // 其他
				info.setRiskLevel(rs.getLong("nRiskLevel")); // 合同风险等级
				info.setInterestTypeID(rs.getLong("nInterestTypeID")); // 利率类型
				info.setLiborRateID(rs.getLong("nLiborRateID")); // Libor利率ID
				info.setDiscountDate(rs.getTimestamp("dtDiscountDate"));// 贴现日期
				RateInfo rInfo = new RateInfo();
				rInfo = getLatelyRate(-1, info.getContractID(), null);
				info.setInterestRate(rInfo.getLateRate()); // 执行利率
				info.setLateRateString(rInfo.getLateRateString());// 利率，字符串格式
				info.setRate(rInfo.getRate()); // 调整前利率
				info.setBasicInterestRate(rInfo.getLateBasicRate()); // 基准利率
				info.setBankInterestID(rInfo.getLateBankInterestID());
				info.setLastAttornmentAmount(rs
						.getDouble("lastAttornmentAmount"));

				info.setPayID(rs.getLong("payid"));
				info.setPayCode(rs.getString("paycode"));

				// ======ninh 2004-06-22 需求变更 增加固定浮动利率======//
				// info.setStaidAdjustRate(rs.getDouble("mStaidAdjustRate"));
				info.setStaidAdjustRate(rInfo.getLateStaidAdjustRate());
				// 银行利率ID
				// info.setAdjustRate(rs.getDouble("mAdjustRate")); //利率调整
				info.setAdjustRate(rInfo.getLateAdjustRate());

				// =========gqfang，Libor利率需求的需要
				if (rs.getLong("nInterestTypeID") == LOANConstant.InterestRateType.LIBOR) {
					info.setStaidAdjustRate(rInfo.getStaidAdjustRate());
					info.setAdjustRate(rInfo.getAdjustRate());
				}

				// info.setLoanStart(rs.getTimestamp("dtStartDate")); // 合同起始日期
				// info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 合同结束日期
				info.setLoanStart(rs.getTimestamp("startdate")); // 合同起始日期
				info.setLoanEnd(rs.getTimestamp("enddate")); // 合同结束日期

				info.setLoanPurpose(rs.getString("sLoanPurpose")); // 借款用途
				info.setExamineAmount(rs.getDouble("payamount")); // 审批金额
				info.setExamineSelfAmount(rs.getDouble("mExamineSelfAmount")); // 批准财务公司承贷金额
				info.setAssureAmount(rs.getDouble("AssureAmount")); // 有担保贷款总额
				info.setChargeRate(rs.getDouble("mChargeRate")); // 手续费率
				info.setChargeRateType(rs.getLong("nChargeRateTypeID"));

				// 手续费率类型
				info.setClientID(rs.getLong("nConsignClientID")); // 委托单位

				if (info.getClientID() > 0) {
					info.setClientName(getClientName(info.getClientID()));
				}

				if (info.getExamineAmount() != 0) // 担保金额占审批金额的比例
				{
					info.setAssureRate(info.getAssureAmount()
							/ info.getExamineAmount());
				} else {
					info.setAssureRate(0);
				}

				if ((info.getExamineAmount() - info.getAssureAmount()) > 0) {
					info.setCreditAmount(info.getExamineAmount()
							- info.getAssureAmount());
				} else {
					info.setCreditAmount(0);
				}

				// 信用贷款总额（审批金额 - 担保金额）

				if (info.getExamineAmount() != 0) // 信用贷款金额占审批金额的比例
				{
					info.setCreditRate(info.getCreditAmount()
							/ info.getExamineAmount());
				} else {
					info.setCreditRate(0);
				}

				info.setAreaType(rs.getLong("nTypeID1")); // 按地区分类
				info.setIndustryType1(rs.getLong("nTypeID2")); // 按行业分类1
				info.setIndustryType2(rs.getLong("nTypeID3")); // 按行业分类2
				info.setIndustryType3(rs.getLong("nTypeID4")); // 按行业分类3

				info.setAssure(getAssure(info.getContractID())); // 保证信息
				info
						.setContractContent(getContractContent(info
								.getContractID())); // 合同文本信息
				info.setYTInfo(getFormatYT(getYT(info.getContractID()))); // 银团信息
				// info.setScale(rs.getDouble("mSelfAmount"));

				info.setLoanAccount(rs.getString("ClientAccount")); // 贷款单位账号
				info.setInputUserID(rs.getLong("nInputUserID")); // 录入人id
				info.setInputUserName(rs.getString("InputUser")); // 录入人
				info.setCheckUserName(rs.getString("CheckUser")); // 审核人
				info.setStatusID(rs.getLong("nStatusID")); // 合同状态
				info.setStatus(LOANConstant.ContractStatus.getName(info
						.getStatusID())); // 合同状态名称

				info.setPlanVersionID(rs.getLong("PlanID")); // 合同执行计划版本号ID
				info.setDiscountRate(rs.getDouble("mDiscountRate")); // 贴现利率
				info.setCheckAmount(rs.getDouble("mCheckAmount")); // 汇总贴现核定金额
				info.setIsPurchaserInterest(rs.getLong("IsPurchaserInterest")); // 是否买方付息
				info.setDiscountClientID(rs.getLong("discountClientID")); // 出票人
				info.setDiscountClientName(rs.getString("discountClientName")); // 出票人名称
				info.setPurchaserInterestRate(rs
						.getDouble("purchaserInterestRate")); // 买方付息比例

				// 担保业务相关
				info.setAssureChargeRate(rs.getDouble("AssureChargeRate")); // 担保费率
				info.setAssureChargeTypeID(rs.getLong("AssureChargeTypeID")); // 担保费收取方式
				info.setBeneficiary(rs.getString("Beneficiary")); // 受益人
				info.setAssureTypeID1(rs.getLong("AssureTypeID1")); // 担保类型1
				info.setAssureTypeID2(rs.getLong("AssureTypeID2")); // 担保类型2

				// 融资租赁新增
				info.setChargeAmount(rs.getDouble("MCHARGEAMOUNT"));
				info.setInterestCountTypeID(rs.getLong("NINTERESTCOUNTTYPEID"));
				info.setMatureNominalAmount(rs
						.getDouble("MMATURENOMINALAMOUNT"));
				// 融资租赁新增结束

				ContractAmountInfo aInfo = new ContractAmountInfo();
				aInfo = getGuoDianLateAmount(info.getContractID(), info
						.getPayID());
				info.setAInfo(aInfo); // 合同金额
				info.setBalance(aInfo.getBalanceAmount()); // 合同当前余额

				// info.setBalanceForAttornment(aInfo.getBalanceAmount() -
				// aInfo.getAheadAmount());
				info.setBalanceForAttornment(aInfo.getBalanceAmount());

				info.setIsCredit(rs.getLong("niscredit")); // 信用
				info.setIsAssure(rs.getLong("nisassure")); // 保证
				info.setIsImpawn(rs.getLong("nisimpawn")); // 抵押
				info.setIsPledge(rs.getLong("nispledge")); // 质押
				info.setIsRecognizance(rs.getLong("IsRecognizance"));// 保证金

				// 以下是为打印取出的字段
				// 委托相关信息
				info.setConsignName(rs.getString("consignname"));
				info.setConsignCode(rs.getString("consigncode"));
				info.setConsignAddress((rs.getString("cSPROVINCE") == null ? ""
						: rs.getString("cSPROVINCE") + " 省 ")
						+ (rs.getString("cSCITY") == null ? "" : rs
								.getString("cSCITY")
								+ " 市 ")
						+ (rs.getString("consignaddress") == null ? "" : rs
								.getString("consignaddress")));
				info.setConsignBank(rs.getString("consignbank"));
				info.setConsignAccount(rs.getString("consignaccount"));
				info.setConsignZip(rs.getString("consignzip"));

				// 借款相关信息
				info.setBorrowName(rs.getString("borrowname"));
				info.setBorrowCode(rs.getString("borrowcode"));
				info.setBorrowAddress((rs.getString("SPROVINCE") == null ? ""
						: (rs.getString("SPROVINCE") + " 省 "))
						+ (rs.getString("SCITY") == null ? "" : (rs
								.getString("SCITY") + " 市 "))
						+ (rs.getString("borrowaddress") == null ? "" : rs
								.getString("borrowaddress")));
				info.setBorrowBank(rs.getString("borrowbank"));
				info.setBorrowAccount(rs.getString("borrowaccount"));
				info.setBorrowZip(rs.getString("borrowzip"));

				// 担保相关信息
				info.setAssureName(rs.getString("assurename"));
				info.setAssureCode(rs.getString("assurecode"));
				info
						.setAssureAddress((rs.getString("assureSPROVINCE") == null ? ""
								: rs.getString("assureSPROVINCE") + " 省 ")
								+ (rs.getString("assureSCITY") == null ? ""
										: rs.getString("assureSCITY") + " 市 ")
								+ (rs.getString("assureaddress") == null ? ""
										: rs.getString("assureaddress")));
				info.setAssureBank(rs.getString("assurebank"));
				info.setAssureAccount(rs.getString("assureaccount"));
				info.setAssureZip(rs.getString("assurezip"));

				// info.setLeftoversAttornmentAmount(rs.getDouble("leftoversAttornmentAmount"));
				// // 债权余额

				// 放款单可用金额
				// double balance = getPayformBalanceForAttornment(lPayID);
				// 已经转让的金额
				double useBalance = sumLastAttornmentAmount(lPayID);
				// 可转让金额
				info.setLeftoversAttornmentAmount(info
						.getBalanceForAttornment()
						- useBalance);

				// 下一个审核级别
				info.setNextCheckLevel(rs.getLong("nNextCheckLevel"));

				// 取展期数目
				strSQL = "select count(id) from LOAN_EXTENDFORM where"
						+ " NCONTRACTID=" + info.getContractID()
						+ " and nStatusID<>" + Constant.RecordStatus.INVALID;
				ps2 = con.prepareStatement(strSQL);
				rs2 = ps2.executeQuery();
				if (rs2.next()) {
					info.setExtendCount(rs2.getLong(1));
				}
				if (rs2 != null) {
					rs2.close();
					rs2 = null;
				}
				if (ps2 != null) {
					ps2.close();
					ps2 = null;
				}

				// 取免还数目
				strSQL = " select count(id) from LOAN_FREEFORM where NCONTRACTID="
						+ info.getContractID()
						+ " and nStatusID<>"
						+ Constant.RecordStatus.INVALID;
				ps2 = con.prepareStatement(strSQL);
				rs2 = ps2.executeQuery();
				if (rs2.next()) {
					info.setFreeCount(rs2.getLong(1));
					System.out.println("" + info.getFreeCount());
				}
				if (rs2 != null) {
					rs2.close();
					rs2 = null;
				}
				if (ps2 != null) {
					ps2.close();
					ps2 = null;
				}
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
			throw new IException("Gen_E001");
		} finally {
			try {
				if (rs2 != null) {
					rs2.close();
					rs2 = null;
				}
				if (ps2 != null) {
					ps2.close();
					ps2 = null;
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
				throw new IException("Gen_E001");
			}
		}

		return info;
	}

	/**
	 * 得到担保信息 Create Date: 2003-10-15
	 * 
	 * @param lContractID
	 *            合同ID
	 * @return Collection 担保信息
	 * @exception Exception
	 */
	public Collection getAssure(long lContractID) throws Exception {
		Vector vResult = new Vector();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		try {
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			
			sbSQL.append(" select a.nassuretypeid,a.mamount,a.nistopassure,a.clienttype,c.sCode,c.sName,c.sContacter,c.sPhone,d.mExamineAmount ");
			sbSQL.append(" from loan_loanContractAssure a, client c, loan_contractForm d ");
			sbSQL.append(" WHERE a.nContractID = d.id ");
			sbSQL.append(" AND a.nClientID = c.id ");
			sbSQL.append(" and a.clienttype ="+LOANConstant.LoanClientType.INTERIOR);
			sbSQL.append(" AND a.nContractID ="+lContractID);
			sbSQL.append(" union ");
			sbSQL.append(" select a.nassuretypeid,a.mamount,a.nistopassure,a.clienttype,c.code as scode,c.name as sname,c.linkman as sContacter,c.tel as sPhone,d.mExamineAmount ");
			sbSQL.append(" from loan_loanContractAssure a,client_extclientinfo c,loan_contractForm d ");
			sbSQL.append(" WHERE a.nContractID = d.id ");
			sbSQL.append(" AND a.nClientID = c.id ");
			sbSQL.append(" and a.clienttype ="+LOANConstant.LoanClientType.EXTERIOR);
			sbSQL.append(" AND a.nContractID ="+lContractID);
			sbSQL.append(" order by scode desc");
			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				AssureInfo info = new AssureInfo();
				info.setClientCode(rs.getString("sCode")); // 客户编号
				info.setClientName(rs.getString("sName")); // 单位名称
				info.setAssureType(rs.getLong("nAssureTypeID")); // 担保方式ID
				info.setAssureTypeName(LOANConstant.AssureType.getName(info
						.getAssureType()));
				// 担保方式名称
				info.setContact(rs.getString("sContacter")); // 联系人
				info.setPhone(rs.getString("sPhone")); // 电话
				info.setAmount(rs.getDouble("mAmount")); // 担保金额
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // 审批金额
				
				if (info.getExamineAmount() != 0) // 担保比例
				{
					info.setRate(info.getAmount() / info.getExamineAmount());
				} else {
					info.setRate(0);
				}
				info.setIsTopAssure(rs.getLong("nIsTopAssure")); // 是否最高额担保

				vResult.add(info);
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
		return vResult.size() > 0 ? vResult : null;

	}

	/**
	 * 得到银团信息 Create Date: 2003-10-15
	 * 
	 * @param lContractID
	 *            合同ID
	 * @return YTInfo 银团信息
	 * @exception Exception
	 */
	public Collection getYT(long lContractID) throws Exception {
		Vector vResult = new Vector();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		try {
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT a.* ");
			sbSQL.append(" FROM loan_YT_LoanContractBankAssign a");
			sbSQL.append(" WHERE a.nContractID = ? ");
			/** ** ninh 2003-12-02 begind *** */
			sbSQL.append(" ORDER BY nIsHead ");
			// sbSQL.append(" ORDER BY nIsHead DESC");
			/** ***********不用降序**** end **** */

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lContractID);
			rs = ps.executeQuery();

			while (rs.next()) {
				YTInfo info = new YTInfo();
				info.setAttendBankID(rs.getLong("nAttendBankID")); // 银行名称在参与行设置的ID
				info.setBankName(rs.getString("sBankName")); // 银行名称
				info.setAssureAmount(rs.getDouble("mAssureAmount")); // 担保金额
				info.setCreditAmount(rs.getDouble("mCreditAmount")); // 信用金额
				info.setLoanAmount(info.getAssureAmount()
						+ info.getCreditAmount());
				// 承贷金额
				info.setLoanRate(rs.getBigDecimal("mRate")); // 承贷比例
				info.setIsHead(rs.getLong("nIsHead")); // 是否牵头行
				vResult.add(info);
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
		return vResult.size() > 0 ? vResult : null;

	}

	/**
	 * 修改合同信息 Create Date: 2003-10-15
	 * 
	 * @param ContractInfo
	 *            合同信息
	 * @return long 如大于0表示成功，小于等于0表示失败
	 * @exception Exception
	 */
	public long update(ContractInfo info) throws Exception {
		PreparedStatement ps = null;
		Connection con = null;
		long lResult = -1;

		try {
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" UPDATE loan_contractForm");
			sbSQL.append(" SET nStatusID = ? "); // 合同状态

			/* TOCONFIG—TODELETE */
			/*
			 * 产品化不再区分项目 ninh 2005-03-24
			 */

			// 胡志强(kewen hu)修改(2004-03-12)
			// if (Env.getProjectName().equals(Constant.ProjectName.CNMEF))
			// {
			// if (info.getContractCode() != null &&
			// !"".equals(info.getContractCode()))
			// {
			// sbSQL.append(" ,sContractCode = '" + info.getContractCode() +
			// "'");
			// }
			// }
			/* TOCONFIG—END */

			// 华联需求，合同编号能修改，add by zwxiao 2008-4-16
			if (info.getContractCode() != null
					&& !info.getContractCode().equals("")) {
				sbSQL.append(" ,sContractCode = ?"); // 合同编号
			}

			if (info.getLoanStart() != null && !info.getLoanStart().equals("")) {
				sbSQL.append(" ,dtStartDate = ?"); // 合同起始日期
			}

			if (info.getLoanEnd() != null && !info.getLoanEnd().equals("")) {
				sbSQL.append(" ,dtEndDate = ?"); // 合同结束日期
			}

			if (info.getAreaType() > 0) {
				sbSQL.append(" ,nTypeID1 = ?"); // 按地区分类
			}

			if (info.getIndustryType1() > 0) {
				sbSQL.append(" ,nTypeID2 = ?"); // 按行业分类1
			}

			if (info.getIndustryType2() > 0) {
				sbSQL.append(" ,nTypeID3 = ?"); // 按行业分类2
			}

			if (info.getIndustryType3() > 0) {
				sbSQL.append(" ,nTypeID4 = ?"); // 按行业分类3
			}

			if (info.getCheckUserID() > 0 || info.getCheckUserID() < -1) {
				sbSQL.append(" ,nNextCheckUserID = ?"); // 下一审核人
			}

			if (info.getCheckUserID() < -1) {
				sbSQL.append(" ,nNextCheckLevel = 1"); // 下一个审核级别
			}

			sbSQL.append(" WHERE id = ? "); // 合同ID

			/* TOCONFIG—TODELETE */
			/*
			 * 产品化不再区分项目 ninh 2005-03-24
			 */

			// 胡志强(kewen hu)修改(2004-03-12)
			// if (Env.getProjectName().equals(Constant.ProjectName.CNMEF))
			// {
			// if (info.getContractCode() != null &&
			// !"".equals(info.getContractCode()))
			// {
			// sbSQL.append(" AND NVL((SELECT ID FROM loan_contractForm \n");
			// sbSQL.append(" WHERE sContractCode = '" + info.getContractCode()
			// + "' \n");
			// sbSQL.append(" AND nStatusID != " +
			// LOANConstant.ContractStatus.CANCEL + " \n");
			// sbSQL.append(" AND nCurrencyID = " + info.getCurrencyID() + "
			// \n");
			// sbSQL.append(" AND nOfficeID = " + info.getOfficeID() + " \n");
			// sbSQL.append(" ),0) IN (0," + info.getContractID() + ") \n");
			// }
			// }
			/* TOCONFIG—END */

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			int index = 1;
			ps.setLong(index++, info.getStatusID());

			// 华联需求，合同编号能修改，add by zwxiao 2008-4-16
			if (info.getContractCode() != null
					&& !info.getContractCode().equals("")) {
				ps.setString(index++, info.getContractCode());
			}

			if (info.getLoanStart() != null && !info.getLoanStart().equals("")) {
				ps.setTimestamp(index++, info.getLoanStart());
			}

			if (info.getLoanEnd() != null && !info.getLoanEnd().equals("")) {
				ps.setTimestamp(index++, info.getLoanEnd());
			}

			if (info.getAreaType() > 0) {
				ps.setLong(index++, info.getAreaType());
			}

			if (info.getIndustryType1() > 0) {
				ps.setLong(index++, info.getIndustryType1());
			}

			if (info.getIndustryType2() > 0) {
				ps.setLong(index++, info.getIndustryType2());
			}

			if (info.getIndustryType3() > 0) {
				ps.setLong(index++, info.getIndustryType3());
			}

			if (info.getCheckUserID() > 0 || info.getCheckUserID() < -1) {
				ps.setLong(index++, info.getCheckUserID());
			}

			ps.setLong(index++, info.getContractID());

			lResult = ps.executeUpdate();

			if (info.getYT() != null) {
				lResult = updateYT(info.getYT());
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

	public long updateContractStatus(ContractInfo info) throws Exception {
		PreparedStatement ps = null;
		Connection con = null;
		long lResult = -1;

		try {
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" UPDATE loan_contractForm");
			sbSQL.append(" SET nStatusID = ? where id = ? "); // 合同状态

			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, info.getStatusID());
			ps.setLong(2, info.getContractID());

			lResult = ps.executeUpdate();

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

	/**
	 * Modify by leiyang date 2007/07/27
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long updateStatusAndCheckStatus(ContractInfo info) throws Exception {
		PreparedStatement ps = null;
		Connection con = null;
		long lResult = -1;

		try {
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" UPDATE loan_contractForm");
			sbSQL
					.append(" SET nStatusID = ? where id = ? and nStatusID in (?,?)");

			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, info.getStatusID());
			ps.setLong(2, info.getContractID());
			ps.setLong(3, LOANConstant.ContractStatus.CHECK);
			ps.setLong(4, LOANConstant.ContractStatus.NOTACTIVE);

			lResult = ps.executeUpdate();

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

	/**
	 * 修改银团信息 Create Date: 2003-10-15
	 * 
	 * @param Collection
	 *            银团信息
	 * @return long 如大于0表示成功，小于等于0表示失败
	 * @exception Exception
	 */
	public long updateYT(Collection c) throws Exception {
		PreparedStatement ps = null;
		Connection con = null;
		long lResult = -1;
		Iterator it = null;
		YTInfo info = new YTInfo();
		StringBuffer sbSQL = new StringBuffer();

		try {
			if (c != null) {
				con = Database.getConnection();

				it = c.iterator();
				if (it.hasNext()) {
					info = (YTInfo) it.next();
					sbSQL.append(" DELETE loan_YT_LoanContractBankAssign");
					sbSQL.append(" WHERE nContractID = ? ");

					log4j.info(sbSQL.toString() + "\nContractID="
							+ info.getContractID());

					ps = con.prepareStatement(sbSQL.toString());
					ps.setLong(1, info.getContractID());
					lResult = ps.executeUpdate();
				}

				Iterator it1 = c.iterator();
				while (it1.hasNext()) {
					info = (YTInfo) it1.next();
					sbSQL.setLength(0);
					sbSQL.append(" INSERT INTO loan_YT_LoanContractBankAssign");
					sbSQL.append(" (nContractID,"); // 合同ID
					sbSQL.append(" sBankName,"); // 银行名称
					sbSQL.append(" mCreditAmount,"); // 信用金额
					sbSQL.append(" mAssureAmount,"); // 担保金额
					sbSQL.append(" mRate,"); // 承贷比例
					sbSQL.append(" nIsHead"); // 是否牵头行
					sbSQL.append(", nAttendBankID"); // 银行名称在参与行设置的ID
					sbSQL.append(" ) ");
					sbSQL.append(" VALUES ");
					// sbSQL.append("(?,?,?,?,?,?)");
					sbSQL.append("(?,?,?,?,?,?,?)");

					log4j.info(sbSQL.toString());
					ps = con.prepareStatement(sbSQL.toString());
					int nIndex = 1;
					ps.setLong(nIndex++, info.getContractID());
					ps.setString(nIndex++, info.getBankName());
					ps.setDouble(nIndex++, info.getCreditAmount());
					ps.setDouble(nIndex++, info.getAssureAmount());
					ps.setBigDecimal(nIndex++, info.getLoanRate());
					ps.setLong(nIndex++, info.getIsHead());
					ps.setLong(nIndex++, info.getAttendBankID());
					lResult = ps.executeUpdate();
				}
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

	/**
	 * 得到银团信息 Create Date: 2003-10-15
	 * 
	 * @param Collection
	 *            银团信息
	 * @return YTFormatInfo 银团信息
	 * @exception Exception
	 */
	public YTFormatInfo getFormatYT(Collection c) throws Exception {
		YTFormatInfo info = null;

		try {
			int len = 0;

			if (c != null) {
				len = c.size();
				if (len > 0) {
					String[] lAttendBankID = new String[len]; //
					String[] sBankName = new String[len]; // 银行名称
					String[] dLoanAmount = new String[len]; // 承贷金额
					String[] dLoanRate = new String[len]; // 承贷比例
					BigDecimal[] dLoanRate1 = new BigDecimal[len]; // 承贷比例
					String[] dAssureAmount = new String[len]; // 担保金额
					String[] dCreditAmount = new String[len]; // 信用金额
					String[] lIsAhead = new String[len]; // 是否牵头行

					Iterator it = c.iterator();
					if (it != null) {

						for (int i = 0; it.hasNext(); i++) {
							YTInfo yInfo1 = new YTInfo();
							yInfo1 = (YTInfo) it.next();
							lAttendBankID[i] = DataFormat.formatString(""
									+ yInfo1.getAttendBankID());
							sBankName[i] = yInfo1.getBankName() == null ? ""
									: yInfo1.getBankName();
							dLoanAmount[i] = DataFormat
									.formatDisabledAmount(yInfo1
											.getLoanAmount());

							if (yInfo1.getLoanRate() != null) {
								dLoanRate[i] = DataFormat.format(yInfo1
										.getLoanRate(), 6);
							} else {
								dLoanRate[i] = "";
							}

							dAssureAmount[i] = DataFormat
									.formatDisabledAmount(yInfo1
											.getAssureAmount());
							dCreditAmount[i] = DataFormat
									.formatDisabledAmount(yInfo1
											.getCreditAmount());
							lIsAhead[i] = String.valueOf(yInfo1.getIsHead());
							dLoanRate1[i] = yInfo1.getLoanRate();
						}
					}

					info = new YTFormatInfo();
					info.setAttendBankID(lAttendBankID);
					info.setBankName(sBankName);
					info.setLoanAmount(dLoanAmount);
					info.setLoanRate(dLoanRate);
					info.setAssureAmount(dAssureAmount);
					info.setCreditAmount(dCreditAmount);
					info.setIsAhead(lIsAhead);
					info.setLoanRate1(dLoanRate1);
				}
			}
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}

		return info;
	}

	/**
	 * 得到合同文本信息 Create Date: 2003-10-15
	 * 
	 * @param lContractID
	 *            合同ID
	 * @return Collection 合同文本信息
	 * @exception Exception
	 */
	public Collection getContractContent(long lContractID) throws Exception {
		Vector vResult = new Vector();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		long lContractType = -1;
		long lLoanType = -1;

		try {
			ContractContentDao dao = new ContractContentDao();

			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL
					.append(" SELECT a.*,c.sName,'' as sAssureCode, -1 as nAssureTypeID ");
			sbSQL
					.append(" FROM loan_ContractContent a,loan_ContractForm b,Client c");
			sbSQL.append(" WHERE a.nContractID = b.ID");
			sbSQL.append(" AND b.nBorrowClientID = c.ID");
			sbSQL.append(" AND (a.nContractTypeID = "
					+ LOANConstant.ContractType.LOAN);
			sbSQL.append(" OR a.nContractTypeID = "
					+ LOANConstant.ContractType.CONSIGN);
			sbSQL.append(" OR a.nContractTypeID = "
					+ LOANConstant.ContractType.SHPF_KLGNBHXY);
			sbSQL.append(" OR a.nContractTypeID = "
					+ LOANConstant.ContractType.SHPF_RZTenancy);
			sbSQL.append(" OR a.nContractTypeID = "
					+ LOANConstant.ContractType.TX + ")");
			sbSQL.append(" AND a.nContractID = ?");
			sbSQL.append(" UNION ");
			sbSQL.append(" SELECT a.*,c.sName,b.sAssureCode,b.nAssureTypeID ");
			sbSQL
					.append(" FROM loan_ContractContent a,loan_loanContractAssure b,Client c");
			sbSQL.append(" WHERE a.nContractID = b.nContractID");
			sbSQL.append(" AND a.nParentID = b.ID");
			sbSQL.append(" AND b.nClientID = c.ID");
			sbSQL.append(" AND b.nStatusID = " + Constant.RecordStatus.VALID);
			sbSQL.append(" AND a.nContractTypeID != "
					+ LOANConstant.ContractType.LOAN);
			sbSQL.append(" AND a.nContractTypeID != "
					+ LOANConstant.ContractType.TX);
			sbSQL.append(" AND a.nContractID = ?");
			log4j.info("sql:" + sbSQL.toString());
			log4j.info("lContractID:" + lContractID);
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lContractID);
			ps.setLong(2, lContractID);
			rs = ps.executeQuery();

			while (rs.next()) {
				ContractContentInfo info = new ContractContentInfo();
				info.setID(rs.getLong("ID")); // ContentID
				// info.setSerialNo(rs.getLong("nSerialNo")); //序列号
				info.setContractTypeID(rs.getLong("nContractTypeID")); // 合同类型ID
				info.setContractType(LOANConstant.ContractType.getName(info
						.getContractTypeID()));
				// 合同类型
				System.out.println("合同类型:" + info.getContractType());
				System.out.println("合同类型:" + info.getContractType());
				System.out.println("合同类型:" + info.getContractType());
				info.setClientName(rs.getString("sName")); // 单位名称
				info.setAssureTypeID(rs.getLong("nAssureTypeID")); // 保证类型
				info.setCode(rs.getString("sAssureCode")); // 保证合同编号
				String sPageName = dao.getContractJspName(info.getID(), info
						.getContractTypeID());
				System.out.println("JSP文件名:" + sPageName);
				info.setPageName(sPageName);
				info.setDocName(rs.getString("sDocName"));

				vResult.add(info);
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
		return vResult.size() > 0 ? vResult : null;

	}

	/**
	 * 删除LOAN_CONTRACTFORM，LOAN_LOANCONTRACTASSURE，LOAN_LOANCONTRACTPLAN
	 * LOAN_LOANCONTRACTPLANDETAIL
	 * 
	 * @param loanID
	 * @return
	 * @throws Exception
	 */
	public long deleteByLoanID(long loanID) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = "";
		long ret = -1;
		long contractID = -1;
		long planVersionID = -1;

		try {
			conn = Database.getConnection();
			/* 首先获得必要的删除主键---合同ID和计划版本 */
			strSQL = "select ID from LOAN_CONTRACTFORM where NLOANID=" + loanID;
			ps = conn.prepareStatement(strSQL);

			rs = ps.executeQuery();
			if (rs.next()) {
				contractID = rs.getLong("ID");
			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			strSQL = "select ID from LOAN_LOANCONTRACTPLAN where NCONTRACTID="
					+ contractID;
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				planVersionID = rs.getLong("ID");
			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}

			/* 删除合同内容信息 */
			ContractContentDao conDao = new ContractContentDao();
			conDao.deleteContractContent(contractID);

			log4j.print("Deleting ContractContent");

			/* 删除计划相信信息 */
			strSQL = "delete from LOAN_LOANCONTRACTPLANDETAIL where NCONTRACTPLANID="
					+ planVersionID;
			ps = conn.prepareStatement(strSQL);
			log4j.print("Deleting Loan_loanContractPlanDetail");
			ps.executeUpdate();
			if (ps != null) {
				ps.close();
				ps = null;
			}

			/* 删除计划版本信息 */
			strSQL = "delete from LOAN_LOANCONTRACTPLAN where NCONTRACTID="
					+ contractID;
			ps = conn.prepareStatement(strSQL);
			log4j.print("Deleting loan_loanContractPlan");
			ps.executeUpdate();
			if (ps != null) {
				ps.close();
				ps = null;
			}

			/* 删除保证信息 */
			strSQL = "delete from LOAN_LOANCONTRACTASSURE where NCONTRACTID="
					+ contractID;
			ps = conn.prepareStatement(strSQL);
			log4j.print("Deleting loan_loanContractAssure");
			ps.executeUpdate();
			if (ps != null) {
				ps.close();
				ps = null;
			}

			/* 删除LOAN_CONTRACTFORM里的相关信息 */
			strSQL = "delete from LOAN_CONTRACTFORM where id=" + contractID;
			ps = conn.prepareStatement(strSQL);
			log4j.print("Deleting loan_contractForm");
			ps.executeUpdate();
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
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
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
		return 1;
	}

	/**
	 * 贷款申请审批通过最后一级，生成贷款合同。
	 * 
	 * @param loanID
	 *            贷款申请ID
	 * @param loanTypeID
	 *            贷款申请子类型
	 * @return
	 * @throws Exception
	 */
	public long insert(long loanID, long loanTypeID) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		Connection conn = null;
		String strSQL = "";
		String strSQL1 = "";
		String contractCode = "";
		boolean isyt = false;
		long ret = -1;
		int n = -1;
		String fileName = "";
		ContractContentDao conDao = new ContractContentDao();
		FormAssureDao assDao = new FormAssureDao();
		LoanRepayPlanDao planDao = new LoanRepayPlanDao();

		long contractID = -1;
		long tmpLong = -1;
		long assureID = -1;
		long planID = -1;
		long planDetailID = -1;

		try {
			if (loanTypeID == LOANConstant.LoanType.YT)
				isyt = true;

			conn = Database.getConnection();
			/* 首先获得LOAN_CONTRACTFORM 的 MAXID */
			strSQL = "select nvl(max(ID)+1,1) oID from LOAN_CONTRACTFORM";
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				contractID = rs.getLong("oID");
			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}

			ContractDao cdo = new ContractDao();
			contractCode = cdo.getContractCode(loanID);
			System.out.println("====================contractCode:"
					+ contractCode);
			/*
			 * 王利(li wang)修改(2006-03-15) 根据配置文件去区分项目 contractCode—start
			 */

			/* contractCode—END */
			System.out.println("开始复制贷款申请表");

			/* 复制信息从loan_loanForm 到loan_contractForm */
			strSQL = "insert into LOAN_CONTRACTFORM (id,nloanid,scontractcode,isextend,ntypeid,ncurrencyid,nofficeid,sapplycode,"
					+ "nconsignclientid,nborrowclientid,mloanamount,sloanreason,sloanpurpose,sother,niscircle,nissalebuy,nistechnical,"
					// added by xiong fei 2010/05/25
					// 在复制信息的时候把回购也复制进去，把原价，首付款，手续费率都复制进去
					+ "morigionamount,mpreamount,mchargeamountrate,"
					+ "ninputuserid,dtinputdate,niscredit,nisassure,nisimpawn,isrepurchase,nispledge,ninteresttypeid,mexamineamount,nintervalnum,"
					+ "nbankinterestid,nstatusid,nnextcheckuserid,mchargerate,dtstartdate,dtenddate,iscanmodify,nchargeratetypeid,"
					+ "sclientinfo,mselfamount,nrisklevel,ntypeid1,ntypeid2,ntypeid3,ntypeid4,nbankacceptpo,nbizacceptpo,mcheckamount,mdiscountrate,"
					+ "dtdiscountdate,minterestrate,madjustrate,mstaidadjustrate,nNextCheckLevel,mExamineSelfAmount,IsPurchaserInterest,DiscountClientID,DiscountClientName,PurchaserInterestRate "
					+ ",projectInfo,AssureChargeRate,AssureChargeTypeID,Beneficiary,AssureTypeID1,AssureTypeID2,IsRecognizance,nLiborRateID "
					+ ",nsubtypeid,NCREDITCHECKREPORTID,NINTERESTCOUNTTYPEID,MCHARGEAMOUNT,MMATURENOMINALAMOUNT,MDISCOUNTACCRUAL,MPURCHASERAMOUNT "
					+ ",isremitcompoundinterest, isremitoverdueinterest, overdueadjustrate, overduestaidadjustrate, isbuyinto "
					+ " ) (select ?,?,?,?,NTYPEID,NCURRENCYID,NOFFICEID,SAPPLYCODE,"
					+ "NCONSIGNCLIENTID,NBORROWCLIENTID,MLOANAMOUNT,SLOANREASON,SLOANPURPOSE,SOTHER,NISCIRCLE,NISSALEBUY,NISTECHNICAL,"
					// added by xiong fei 2010/05/25
					// 在复制信息的时候把回购也复制进去,把原价，首付款，手续费率都复制进去
					+ "morigionamount,mpreamount,mchargeamountrate,"
					+ "NINPUTUSERID,DTINPUTDATE,NISCREDIT,NISASSURE,NISIMPAWN,isrepurchase,NISPLEDGE,NINTERESTTYPEID,MEXAMINEAMOUNT,NINTERVALNUM,"
					+ "NBANKINTERESTID,"
					+ LOANConstant.ContractStatus.SAVE
					+ ",nInputUserID,MCHARGERATE,DTSTARTDATE,DTENDDATE,ISCANMODIFY,NCHARGERATETYPEID,"
					+ "SCLIENTINFO,MSELFAMOUNT,"
					+ LOANConstant.VentureLevel.A
					+ ",NTYPEID1,NTYPEID2,NTYPEID3,NTYPEID4,NBANKACCEPTPO,NBIZACCEPTPO,MCHECKAMOUNT,MDISCOUNTRATE,"
					+ "DTDISCOUNTDATE,MINTERESTRATE,MADJUSTRATE,mstaidadjustrate,1,mExamineSelfAmount,IsPurchaserInterest,DiscountClientID,DiscountClientName,PurchaserInterestRate "
					+ ",projectInfo,AssureChargeRate,AssureChargeTypeID,Beneficiary,AssureTypeID1,AssureTypeID2,IsRecognizance,nLiborRateID "
					+ ",nsubtypeid,NCREDITCHECKREPORTID,NINTERESTCOUNTTYPEID,MCHARGEAMOUNT,MMATURENOMINALAMOUNT,MDISCOUNTACCRUAL,MPURCHASERAMOUNT "
					+ ",isremitcompoundinterest, isremitoverdueinterest, overdueadjustrate, overduestaidadjustrate, ISBUYINTO "
					+ " from loan_loanForm where id=?)";
			log4j.print(strSQL);
			ps = conn.prepareStatement(strSQL);
			n = 1;
			ps.setLong(n++, contractID);
			ps.setLong(n++, loanID);
			ps.setString(n++, contractCode);
			ps.setLong(n++, -1);
			ps.setLong(n++, loanID);
			ret = ps.executeUpdate();
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}

			if (isyt) // 银团申请，拷贝承贷比例明细
			{ /*
				 * rs.close(); }//
				 */

				strSQL = "insert into loan_yt_loancontractbankassign "
						+ " ( ncontractid,sbankname "
						+ "   ,mcreditamount,massureamount"
						+ "   ,mrate,nishead " + " ) "
						+ " ( select ?,sbankname "
						+ "        ,l.mExamineSelfAmount,massureamount "
						+ "        ,mrate,nishead "
						+ " from loan_yt_loanformbankassign,loan_loanform l "
						+ " where l.id = nloanid" + " and nloanid = ? "
						+ " and nIsHead = ? )";

				log4j.print(strSQL);
				ps = conn.prepareStatement(strSQL);
				ps.setLong(1, contractID);
				ps.setLong(2, loanID);

				ps.setLong(3, LOANConstant.IsHead.YES);
				ret = ps.executeUpdate();
				if (ps != null) {
					ps.close();
					ps = null;
				}
			}

			/* 获得期限 */

			/*
			 * 直接从贷款申请表中 得到贷款开始日期和结束日期 long intervalNum = -1; strSQL = "select
			 * nIntervalNum from loan_loanForm where id=" + loanID; ps =
			 * conn.prepareStatement(strSQL); rs = ps.executeQuery(); if
			 * (rs.next()) { intervalNum = rs.getLong("nIntervalNum");
			 * log4j.print("get IntervalNum:" + intervalNum); } if (rs != null) {
			 * rs.close(); rs = null; } if (ps != null) { ps.close(); ps = null; }
			 */
			/* 将合同的默认开始和结束时间改成想要得时间 */
			/*
			 * log4j.print("开始更改默认得开始时间和结束时间"); Timestamp startDate =
			 * planDao.findDefaultStartDate(loanID); log4j.print("startDate:" +
			 * startDate); // SEFC新增担保类型,担保无放款计划,默认开始时间即为录入时间 if (loanTypeID ==
			 * LOANConstant.LoanType.DB) { strSQL = "select dtinputdate from
			 * LOAN_CONTRACTFORM where id=" + contractID; ps =
			 * conn.prepareStatement(strSQL); rs = ps.executeQuery(); if (rs !=
			 * null && rs.next()) { startDate = rs.getTimestamp("dtinputdate"); }
			 * if (rs != null) { rs.close(); rs = null; } if (ps != null) {
			 * ps.close(); ps = null; } }
			 * 
			 * Timestamp endDate = null; if (startDate != null) { endDate =
			 * DataFormat.getNextMonth(startDate, (int) intervalNum); } if
			 * (startDate != null && intervalNum > 0) { strSQL = "update
			 * loan_ContractForm set dtStartDate=?,dtEndDate=? where id=?"; ps =
			 * conn.prepareStatement(strSQL); ps.setTimestamp(1, startDate);
			 * ps.setTimestamp(2, endDate); ps.setLong(3, contractID);
			 * log4j.print(strSQL); ps.execute(); if (ps != null) { ps.close();
			 * ps = null; } }
			 */
			/* 复制loan_loanContractAssure从loan_loanFormAssure */
			System.out.println("开始复制保证信息表");

			Vector assVector = (Vector) assDao.findByLoanID(loanID, -1, 1);
			long assCount = -1;
			com.iss.itreasury.loan.loanapply.dataentity.AssureInfo assInfo = null;

			if ((assVector != null) && (assVector.size() > 0)) {
				/* 取合同表保证表中assureInfo的最大ID */
				strSQL = "select nvl(max(ID)+1,1) oID from LOAN_LOANCONTRACTASSURE";
				ps = conn.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs.next()) {
					assureID = rs.getLong("oID");
				}

				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				strSQL = "insert into LOAN_LOANCONTRACTASSURE (id,ncontractid,nassuretypeid,nfillquestionary,nclientid,mamount,simpawname,"
						+ "simpawquality,simpawstatus,mpledgeamount,mpledgerate,nstatusid,sassurecode,nIsTopAssure,Clienttype) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				assCount = assVector.size();
				for (int i = 0; i < assCount; i++) {
					n = 1;
					ps = conn.prepareStatement(strSQL);
					assInfo = (com.iss.itreasury.loan.loanapply.dataentity.AssureInfo) assVector
							.get(i);
					ps.setLong(n++, assureID++);
					// System.out.println("$$$$$$$$$$$$$$$$$$$$="+contractID);
					ps.setLong(n++, contractID);
					ps.setLong(n++, assInfo.getAssureTypeID());
					ps.setLong(n++, assInfo.getFillQuestionary());
					ps.setLong(n++, assInfo.getClientID());
					ps.setDouble(n++, assInfo.getAmount());
					ps.setString(n++, assInfo.getImpawName());
					ps.setString(n++, assInfo.getImpawQuality());
					ps.setString(n++, assInfo.getImpawStatus());
					ps.setDouble(n++, assInfo.getPledgeAmount());
					ps.setDouble(n++, assInfo.getPledgeRate());
					ps.setLong(n++, assInfo.getStatusID());
					ps.setString(n++, assInfo.getAssureCode());
					ps.setLong(n++, assInfo.getIsTopAssure());
					ps.setLong(n++, assInfo.getClientType());
					ps.executeUpdate();
					if (ps != null) {
						ps.close();
						ps = null;
					}
					if (!isyt && !(loanTypeID == LOANConstant.LoanType.MFXD)) {

						// 抵押合同

						if (assInfo.getAssureTypeID() == LOANConstant.AssureType.PLEDGE) {

							System.out.println("抵押合同");

							if (loanTypeID != LOANConstant.LoanType.TX) {
								// //上海浦发 最高额抵押合同/抵押合同

								// //先判断是否最高额抵押

								if (assInfo.getIsTopAssure() == Constant.YesOrNo.YES) {
									System.out.println("最高额抵押合同");
									// //生成合同文本，插入一条记录
									fileName = conDao
											.addSHPFPledgeZGE(assureID - 1);
									ContractContentInfo info = new ContractContentInfo();
									info.setParentID(assureID - 1);
									info.setContractID(contractID);
									info
											.setContractTypeID(LOANConstant.ContractType.ZGEPLEDGE);
									info.setDocName(fileName);
									conDao.saveContractContent(info);
								} else {
									fileName = conDao
											.addSHPFPledge(assureID - 1);
									ContractContentInfo info = new ContractContentInfo();
									info.setParentID(assureID - 1);
									info.setContractID(contractID);
									info
											.setContractTypeID(LOANConstant.ContractType.PLEDGE);
									info.setDocName(fileName);
									conDao.saveContractContent(info);
								}
							}

						}

					}

				} // end for
			} // end if assvector

			/* 复制loan_loanContractPlan从loan_loanFormPlan */
			System.out.println("开始复制loan_loanContractPlan");
			strSQL = "select nvl(max(ID)+1,1) oID from LOAN_LOANCONTRACTPLAN";
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				planID = rs.getLong("oID");
			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}

			long lplanR = -1;

			strSQL = "insert into LOAN_LOANCONTRACTPLAN(id,nloanid,ncontractid,nplanversion,nstatusid,sreason,nisused,nusertype,DTInputDate)"
					+ "(select ?,?,?,nplanversion,nstatusid,sreason,nisused,nusertype,sysdate from loan_loanFormPlan where nLoanID=?)";
			ps = conn.prepareStatement(strSQL);
			n = 1;
			ps.setLong(n++, planID);
			ps.setLong(n++, loanID);
			ps.setLong(n++, contractID);
			ps.setLong(n++, loanID);
			lplanR = ps.executeUpdate();
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (lplanR < 0) {
				log4j.info(" apply no do plan ");
				throw new IException("Loan_E020");
			}

			/* 复制LOAN_LOANCONTRACTPLANDETAIL 从LOAN_LOANFORMPLANDETAIL */
			System.out.println("开始复制LOAN_LOANCONTRACTPLANDETAIL");

			Vector planVector = (Vector) planDao.findByLoanID(loanID, 1, 10000,
					99, 1);
			LoanPlanDetailInfo planInfo = null;
			long planCount = -1;

			if ((planVector != null) && (planVector.size() > 0)) {
				planCount = planVector.size();
				strSQL = "select nvl(max(ID)+1,1) oID from LOAN_LOANCONTRACTPLANDETAIL";
				ps = conn.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs.next()) {
					planDetailID = rs.getLong("oID");
				}
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				strSQL = "insert into LOAN_LOANCONTRACTPLANDETAIL(id,ncontractplanid,dtplandate,npaytypeid,"
						+ "mamount,stype,dtmodifydate,nlastextendid,nlastoverdueid,nlastversionplanid,MINTERESTAMOUNT,MRECOGNIZANCEAMOUNT)"
						+ "values (?,?,?,?,?,?,?,?,?,?,?,? )";
				for (int i = 0; i < planCount; i++) {
					planInfo = (LoanPlanDetailInfo) planVector.get(i);
					ps = conn.prepareStatement(strSQL);
					n = 1;
					ps.setLong(n++, planDetailID++);
					ps.setLong(n++, planID);
					ps.setTimestamp(n++, planInfo.getPlanDate());
					ps.setLong(n++, planInfo.getPayTypeID());
					ps.setDouble(n++, planInfo.getAmount());
					ps.setString(n++, planInfo.getType());
					ps.setTimestamp(n++, planInfo.getModifyDate());
					ps.setLong(n++, planInfo.getLastExtendID());
					ps.setLong(n++, planInfo.getLastOverdueID());
					ps.setLong(n++, planInfo.getLastVersionPlanID());
					ps.setDouble(n++, planInfo.getInterestAmount());
					ps.setDouble(n++, planInfo.getRecognizanceAmount());
					ps.execute();
					if (rs != null) {
						rs.close();
						rs = null;
					}
					if (ps != null) {
						ps.close();
						ps = null;
					}
				} // end for
			} // end if planVector

			if (loanTypeID == LOANConstant.LoanType.TX) {
				long lLoanBillID = -1;
				long lContractBillID = -1;
				strSQL = "select nvl(max(ID)+1,1) ContractBillID from Loan_DiscountContractBill";
				ps = conn.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs.next()) {
					lContractBillID = rs.getLong("ContractBillID");
				}
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				// 复制贴现票据从Loan_DiscountFormBill到Loan_DiscountContractBill
				Log.print("开始复制Loan_DiscountFormBill");
				strSQL = "select ID from Loan_DiscountFormBill where nLoanID = "
						+ loanID;
				ps = conn.prepareStatement(strSQL);
				rs = ps.executeQuery();

				while (rs.next()) {
					lLoanBillID = rs.getLong("ID");
					DiscountDao dao = new DiscountDao();
					long draftTypeId = dao.getBillTypeId(loanID);
					Log.print("lLoanBillID = " + lLoanBillID);
					strSQL1 = "insert into Loan_DiscountContractBill(ID,nContractID,nBillSourcetypeId,nSellStatusId,nSerialNo,sUserName,"
							+ "sBank,nIsLocal,dtCreate,dtEnd,sCode,mAmount,nStatusID,nDiscountCredenceID,"
							+ "nAddDays,OB_nDiscountCredenceID,nAcceptPOTypeID,sFormerOwner,mCheckAmount,NCHECKSTATUS,NBILLSTATUSID,nofficeid,ncurrencyid,ndrafttypeid,nStorageStatusID,payee) "
							+ "(select "
							+ lContractBillID++
							+ ","
							+ contractID
							+ ","
							+ LOANConstant.BillSourceType.DISCOUN
							+ ","
							+ Constant.YesOrNo.NO
							+ ",nSerialNo,sUserName,sBank,nIsLocal,dtCreate,dtEnd,sCode,"
							+ "mAmount,nStatusID,nDiscountCredenceID,nAddDays,OB_nDiscountCredenceID,"
							+ "nAcceptPOTypeID,sFormerOwner,mCheckAmount,"
							+ LOANConstant.CheckDiscountBillStatus.WSC
							+ ","
							+ LOANConstant.BillStatus.KC
							+ ",nofficeid,ncurrencyid "
							+ ","
							+ draftTypeId
							+ ","
							+ BILLConstant.DraftInOrOut.IN
							+ ",payee"
							+ "  from Loan_DiscountFormBill "
							+ "where ID = "
							+ lLoanBillID + ")";
					Log.print(strSQL1);
					ps1 = conn.prepareStatement(strSQL1);
					ps1.executeUpdate();
					ps1.close();
					ps1 = null;
				}
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
			}

			/* 生成合同文本 */

			if (loanTypeID == LOANConstant.LoanType.ZY) {
				System.out.println("人名币借款合同（自营 短期/中长期）");

				System.out.println("开始生成合同文本");

				fileName = conDao.addSHPFLoan(contractID);

				ContractContentInfo info = new ContractContentInfo();
				info.setParentID(contractID);
				info.setContractID(contractID);
				info.setContractTypeID(LOANConstant.ContractType.LOAN);
				info.setDocName(fileName);
				conDao.saveContractContent(info);
			}

			if (loanTypeID == LOANConstant.LoanType.WT) {
				System.out.println("委托贷款合同");

				ContractContentInfo info = new ContractContentInfo();

				info.setDocName(fileName);
				conDao.saveContractContent(info);

				fileName = conDao.addSHPFWTDKWT(contractID);
				// fileName=conDao.addCECWTDKWT(contractID);
				info.setContractTypeID(LOANConstant.ContractType.LOAN);

				info.setParentID(contractID);
				info.setContractID(contractID);
				info.setDocName(fileName);
				conDao.saveContractContent(info);

				/* TOCONFIG—END */
			}

			if (loanTypeID == LOANConstant.LoanType.ZGXE) {
				System.out.println("最高限额借款合同");

				fileName = conDao.addSHPFLoan(contractID);

				/* TOCONFIG—END */

				ContractContentInfo info = new ContractContentInfo();
				info.setParentID(contractID);
				info.setContractID(contractID);
				info.setContractTypeID(LOANConstant.ContractType.LOAN);
				info.setDocName(fileName);
				conDao.saveContractContent(info);
				// //先判断是否最高额抵押

			}

			if (loanTypeID == LOANConstant.LoanType.DB) {
				// 上海浦发新增担保业务 生成国内保函协议 modified by liwang 2006-03-27

				System.out.println("浦发银行：国内保函协议");
				fileName = conDao.addSHPFKLGNBHXY(contractID);

				ContractContentInfo info = new ContractContentInfo();
				info.setParentID(contractID);
				info.setContractID(contractID);
				info.setContractTypeID(LOANConstant.ContractType.SHPF_KLGNBHXY);
				info.setDocName(fileName);
				conDao.saveContractContent(info);
			}
			if (loanTypeID == LOANConstant.LoanType.RZZL) {
				// 上海浦发新增担保业务 生成融资租赁合同 modified by liwang 2006-04-10

				fileName = conDao.addSHPFRZZLHT(contractID);
				ContractContentInfo info = new ContractContentInfo();
				info.setParentID(contractID);
				info.setContractID(contractID);
				info
						.setContractTypeID(LOANConstant.ContractType.SHPF_RZTenancy);

				info.setDocName(fileName);
				conDao.saveContractContent(info);

			}

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
		} catch (IException ie) {
			ie.printStackTrace();
			throw ie;
		} catch (Exception e) {
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
			e.printStackTrace();
			throw e;
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
				// log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}

		return contractID;
	}

	/**
	 * 得到合同文本信息 Create Date: 2003-10-15
	 * 
	 * @param lContractID
	 *            合同ID
	 * @return Collection 合同文本信息
	 * @exception Exception
	 */
	public String getClientName(long lClientID) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		String sClientName = "";

		try {
			if (lClientID > 0) {
				con = Database.getConnection();

				StringBuffer sbSQL = new StringBuffer();
				sbSQL.append(" SELECT sName from Client where ID = "
						+ lClientID);

				ps = con.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();

				if (rs.next()) {
					sClientName = rs.getString(1);
				}

				rs.close();
				rs = null;

				ps.close();
				ps = null;

				con.close();
				con = null;

			}
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
		return sClientName;
	}

	/**
	 * 得到审批贷款类型 Create Date: 2003-10-15
	 * 
	 * @param lLoanTypeID
	 *            合同贷款类型
	 * @return long 审批贷款类型
	 * @exception Exception
	 */
	public long getApprovalLoanType(long lLoanTypeID) throws Exception {
		long ApprovalLoanType = -1;
		try {
			if (lLoanTypeID == LOANConstant.LoanType.TX) {
				ApprovalLoanType = Constant.ApprovalLoanType.TX;
			} else if (lLoanTypeID == LOANConstant.LoanType.ZTX) {
				ApprovalLoanType = Constant.ApprovalLoanType.ZTX;
			} else if (lLoanTypeID == LOANConstant.LoanType.WT) {
				ApprovalLoanType = Constant.ApprovalLoanType.WT;
			} else if (lLoanTypeID == LOANConstant.LoanType.RZZL) {
				ApprovalLoanType = Constant.ApprovalLoanType.RZZL;
			}
			// else if (lLoanTypeID == LOANConstant.LoanType.WTTJTH)
			// {
			// ApprovalLoanType = Constant.ApprovalLoanType.WTTJTH;
			// }
			else if (lLoanTypeID == LOANConstant.LoanType.YT) {
				ApprovalLoanType = Constant.ApprovalLoanType.YTDQ;
			}
			// else if (lLoanTypeID == LOANConstant.LoanType.YTZCQ)
			// {
			// ApprovalLoanType = Constant.ApprovalLoanType.YTZCQ;
			// }
			else if (lLoanTypeID == LOANConstant.LoanType.ZGXE) {
				ApprovalLoanType = Constant.ApprovalLoanType.ZGXEDQ;
			}
			// else if (lLoanTypeID == LOANConstant.LoanType.ZGXEZCQ)
			// {
			// ApprovalLoanType = Constant.ApprovalLoanType.ZGXEZCQ;
			// }
			else if (lLoanTypeID == LOANConstant.LoanType.ZY) {
				ApprovalLoanType = Constant.ApprovalLoanType.ZYDQ;
			}
			// else if (lLoanTypeID == LOANConstant.LoanType.ZYZCQ)
			// {
			// ApprovalLoanType = Constant.ApprovalLoanType.ZYZCQ;
			// }
			else if (lLoanTypeID == LOANConstant.LoanType.MFXD) {
				ApprovalLoanType = Constant.ApprovalLoanType.MFXD;
			} else if (lLoanTypeID == LOANConstant.LoanType.DB) {
				ApprovalLoanType = Constant.ApprovalLoanType.DB;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ApprovalLoanType;
	}

	public long updateContractNextCheckLevel(long lContractID,
			long lNextCheckLevel) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		long lResult = -1;

		try {
			conn = Database.getConnection();
			sb.append(" update loan_ContractForm set ");
			if (lNextCheckLevel > 0) {
				sb.append(" nNextCheckLevel = " + lNextCheckLevel);
			} else {
				sb.append(" nNextCheckLevel = nNextCheckLevel + 1");
			}
			sb.append(" where id = ? ");

			System.out.println(sb.toString());
			ps = conn.prepareStatement(sb.toString());

			ps.setLong(1, lContractID);
			lResult = ps.executeUpdate();

			// rs.close();
			// rs = null;

			ps.close();
			ps = null;

			conn.close();
			conn = null;
		} catch (Exception e) {
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

		return lResult;
	}

	public void setLastAttornment(Collection col, long direction)
			throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		String strSQL = "";
		ArrayList conCollection = null;
		long conID = -1;
		double thisAtt = 0;
		AttornmentContractInfo attInfo = null;

		try {
			if (col == null || col.size() == 0) {
				return;
			}
			conCollection = (ArrayList) col;
			con = Database.getConnection();

			for (int i = 0; i < conCollection.size(); i++) {
				attInfo = (AttornmentContractInfo) conCollection.get(i);
				conID = attInfo.getContractId();
				ContractDao cDao = new ContractDao();
				ContractInfo cInfo = cDao.findByID(conID);
				thisAtt = attInfo.getAttornmentAmount();
				if (direction == 2) // 拒绝或者修改
				{
					strSQL = "update loan_contractform set lastAttornmentAmount=(decode(lastAttornmentAmount,null,0,lastAttornmentAmount) - "
							+ thisAtt
							+ " ) , leftoversAttornmentAmount= " // added by
							// qhzhou
							// 2008-04-19
							+ (cInfo.getBalance()
									- attInfo.getLastAttornmentAmount() + thisAtt)
							+ " where id=" + conID;
					log4j.print(strSQL);
					
				} else {
					strSQL = "update loan_contractform set lastAttornmentAmount=(decode(lastAttornmentAmount,null,0,lastAttornmentAmount) + "
							+ thisAtt + " ) where id=" + conID;
					log4j.print(strSQL);
				}

				ps = con.prepareStatement(strSQL);

				log4j.print(strSQL);
				ps.execute();

				ps.close();
				ps = null;
			}

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

	}

	// 设置未转让债权余额
	public void setLeftoversAttornment(long lContractID,
			double leftoversAttornment) throws Exception {
		if (leftoversAttornment < 0)
			throw new Exception("可用转让债权余额必须大于等于零");
		PreparedStatement ps = null;
		Connection con = null;
		String strSQL = "";
		strSQL = "update loan_contractform set leftoversattornmentamount="
				+ leftoversAttornment + " where id=" + lContractID;
		log4j.print(strSQL);
		try {
			con = Database.getConnection();
			ps = con.prepareStatement(strSQL);

			log4j.print(strSQL);
			ps.execute();

			ps.close();
			ps = null;

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
	}

	/**
	 * 增加参与行信息 Create Date: 2003-10-15
	 * 
	 * @param Collection
	 *            银团信息
	 * @return long 如大于0表示成功，小于等于0表示失败
	 * @exception Exception
	 */
	public long addMember(Collection c) throws Exception {
		PreparedStatement ps = null;
		Connection con = null;
		long lResult = -1;
		Iterator it = null;
		YTInfo info = new YTInfo();
		StringBuffer sbSQL = new StringBuffer();
		ResultSet rs = null;
		long id = 0;

		try {
			if (c != null) {
				con = Database.getConnection();

				it = c.iterator();

				while (it.hasNext()) {
					info = (YTInfo) it.next();
					sbSQL.setLength(0);
					sbSQL.append(" SELECT MAX(nIsHead) ");
					sbSQL.append(" FROM loan_YT_LoanContractBankAssign");
					sbSQL
							.append(" WHERE nContractID = "
									+ info.getContractID());
					log4j.info(sbSQL.toString() + "\n");
					ps = con.prepareStatement(sbSQL.toString());
					rs = ps.executeQuery();

					if (rs != null && rs.next()) {
						id = rs.getLong(1) + 1;
					}
					log4j.info("id = " + id + "\n");

					sbSQL.setLength(0);
					sbSQL.append(" INSERT INTO loan_YT_LoanContractBankAssign");
					sbSQL.append(" (nContractID,"); // 合同ID
					sbSQL.append(" sBankName,"); // 银行名称
					sbSQL.append(" mCreditAmount,"); // 信用金额
					sbSQL.append(" mAssureAmount,"); // 担保金额
					sbSQL.append(" mRate,"); // 承贷比例
					sbSQL.append(" nIsHead"); // 是否牵头行
					sbSQL.append(", nAttendBankID"); // 银行名称在参与行设置的ID
					sbSQL.append(" ) ");
					sbSQL.append(" VALUES ");
					sbSQL.append("(?,?,?,?,?,?,?)");

					log4j.info(sbSQL.toString() + "\n");
					ps = con.prepareStatement(sbSQL.toString());
					int nIndex = 1;
					ps.setLong(nIndex++, info.getContractID());
					ps.setString(nIndex++, info.getBankName());
					ps.setDouble(nIndex++, info.getCreditAmount());
					ps.setDouble(nIndex++, info.getAssureAmount());
					ps.setBigDecimal(nIndex++, info.getLoanRate());
					ps.setLong(nIndex++, id);
					ps.setLong(nIndex++, info.getAttendBankID());
					lResult = ps.executeUpdate();
				}
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

	/**
	 * 删除参与行信息 Create Date: 2003-10-15
	 * 
	 * @param Collection
	 *            银团信息
	 * @return long 如大于0表示成功，小于等于0表示失败
	 * @exception Exception
	 */
	public long delMember(Collection c) throws Exception {
		PreparedStatement ps = null;
		Connection con = null;
		long lResult = -1;
		Iterator it = null;
		YTInfo info = new YTInfo();
		StringBuffer sbSQL = new StringBuffer();

		try {
			if (c != null) {
				con = Database.getConnection();

				it = c.iterator();

				while (it.hasNext()) {
					info = (YTInfo) it.next();
					sbSQL.setLength(0);
					sbSQL.append(" DELETE loan_YT_LoanContractBankAssign");
					sbSQL.append(" WHERE nContractID = ?"); // 合同ID
					sbSQL.append(" AND nIsHead = ?"); // 是否牵头行

					log4j.info(sbSQL.toString());
					ps = con.prepareStatement(sbSQL.toString());
					int nIndex = 1;
					ps.setLong(nIndex++, info.getContractID());
					ps.setLong(nIndex++, info.getIsHead());
					lResult = ps.executeUpdate();
				}
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

	/*
	 * 贴现合同审核以后需要修改该合同下票据的卖出状态
	 */
	public long updateContractBillSellStatus(long lContractID, long lSellStatus)
			throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		long lResult = -1;

		try {
			conn = Database.getConnection();
			sb.append(" update LOAN_DISCOUNTCONTRACTBILL set NSELLSTATUSID = "
					+ lSellStatus);
			sb.append(" where NCONTRACTID = " + lContractID);

			System.out.println(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			lResult = ps.executeUpdate();

			ps.close();
			ps = null;

			conn.close();
			conn = null;
		} catch (Exception e) {
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

		return lResult;
	}

	// 浦发银行生成
	public String getContractCode(long loanID) throws IException, Exception {
		HashMap map = new HashMap();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String contractCode = "";
		try {
			// String data=Env.getSystemDateString();
			// String year="";
			// String month="";
			// String day="";
			String strSQL = "";
			/*
			 * String serialNo="";
			 * 
			 * 
			 * String susernum=""; String tempcode=""; long tmpid=-1;
			 * 
			 * year=data.substring(0, 4); month=data.substring(5, 7);
			 * day=data.substring(8, 10); year=year+month+day;
			 * System.out.println("year:"+year);
			 */
			// 获取编号 add by zcwang 2007-8-2
			conn = Database.getConnection();
			strSQL = "select * from loan_loanForm t where t.id=" + loanID;
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				map.put("officeID", rs.getObject("NOFFICEID").toString());
				map.put("currencyID", rs.getObject("NCURRENCYID").toString());
				map.put("moduleID", String.valueOf(Constant.ModuleType.LOAN));
				map.put("transTypeID", rs.getObject("NTYPEID").toString());
				map
						.put("subTransTypeID", rs.getObject("NSUBTYPEID")
								.toString());
				map.put("actionID", String
						.valueOf(Constant.CodingruleAction.LOAN_CONTRACT));
				map.put("clientID", rs.getObject("NBORROWCLIENTID").toString());
			}

			//

			// 取3位客户经理号
			strSQL = "";
			strSQL += "select substr(ui.scode,1,3) as scode from loan_loanForm ll,CLIENT_CLIENTINFO c,userinfo ui";
			strSQL += " where ll.NBORROWCLIENTID=c.id and ll.id=" + loanID;
			strSQL += " and  c.CUSTOMERMANAGERUSERID=ui.id";
			// strSQL = "select ui.susernum from USERINFO ui,loan_loanForm ll
			// where ll.NINPUTUSERID=ui.id and ll.id="+loanID;
			System.out.println("strSQL:" + strSQL);

			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			// 取客户经理号
			if (rs.next() && rs.getString("scode") != null
					&& rs.getString("scode").length() > 0) {
				// susernum =
				// DataFormat.formatInt(DataFormat.parseLong(rs.getString("scode")),3);
				map.put("clientCode", rs.getString("scode"));
				System.out.println("取出客户经理号 = :" + rs.getString("scode"));
			} else// 取不出客户经理号,取录入人号
			{
				strSQL = "";
				strSQL += "select substr(ui.scode,1,3) as scode from loan_loanForm ll,CLIENT_CLIENTINFO c,userinfo ui ";
				strSQL += "where ll.NBORROWCLIENTID=c.id  and ll.id=" + loanID
						+ " and ll.ninputuserid=ui.id";
				System.out.println("strSQL:" + strSQL);
				ps = conn.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs.next() && rs.getString("scode") != null
						&& rs.getString("scode").length() > 0) {
					// susernum =
					// DataFormat.formatInt(DataFormat.parseLong(rs.getString("scode")),3);
					map.put("clientCode", rs.getString("scode"));
					System.out.println("取出录入人号 = :" + rs.getString("scode"));
				} else {
					throw new IException("取不到客户经理号，不能通过审核，请检查！");
				}
			}
			contractCode = CreateCodeManager.createCode(map);
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			// System.out.println("susernum:"+susernum);
			// 取3位贷款大类型号
			/*
			 * String sType =
			 * LOANNameRef.getNameByID("NTYPEID","loan_loanform","id",String.valueOf(loanID),null);
			 * 
			 * 
			 * long TypeID = -1; String loanTypeCode = ""; if( sType != null &&
			 * sType.length() > 0 ) { TypeID = Long.valueOf(sType).longValue(); }
			 * loanTypeCode=DataFormat.formatInt(TypeID,3);
			 * System.out.println("loanTypeCode:"+loanTypeCode); //获得最大流水号
			 * strSQL = "select max(substr(c.scontractcode,12,3)) as serialNo
			 * from loan_contractform c where length(c.SCONTRACTCODE)=17 and
			 * c.scontractcode like '%"+data.substring(0, 4)+"%'";
			 * System.out.println("strSQL:"+strSQL); ps =
			 * conn.prepareStatement(strSQL); rs = ps.executeQuery(); if
			 * (rs.next() && rs.getString("serialNo")!=null &&
			 * rs.getString("serialNo").length()>0) { serialNo =
			 * DataFormat.formatInt(DataFormat.parseLong(rs.getString("serialNo"))+1,3); }
			 * else { serialNo = "001"; } if (rs != null) { rs.close(); rs =
			 * null; } if (ps != null) { ps.close(); ps = null; } if (conn !=
			 * null) { conn.close(); conn = null; } //获取贷款合同号
			 * contractCode=year+susernum+serialNo+loanTypeCode;
			 * 
			 * System.out.println("contractCode:"+contractCode);
			 */
			// 审核提交结束产生合同编号
		} catch (IException ie) {
			log4j.error(ie.toString());
			throw ie;
		} catch (Exception e) {
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
			e.printStackTrace();

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
				throw new IException("Gen_E001");
			}
		}
		return contractCode;
	}

	// Modify by leiyang date 2007/07/11
	/**
	 * 校验放款通知单状态
	 */
	public long checkNextState(long contractId, long loanTypeId)
			throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		long lResult = -1;

		try {
			conn = Database.getConnection();
			strSQL.append("select * from");
			switch ((int) loanTypeId) {
			case (int) LOANConstant.LoanType.ZY:
				strSQL
						.append(" loan_payform t where t.ncontractid = ? and t.nStatusId not in(?,?)");

				ps = conn.prepareStatement(strSQL.toString());
				ps.setLong(1, contractId);
				ps.setLong(2, Constant.RecordStatus.INVALID);
				ps.setLong(3, LOANConstant.LoanPayNoticeStatus.REFUSE);
				break;
			case (int) LOANConstant.LoanType.WT:
				strSQL
						.append(" loan_payform t where t.ncontractid = ? and t.nStatusId not in(?,?)");

				ps = conn.prepareStatement(strSQL.toString());
				ps.setLong(1, contractId);
				ps.setLong(2, Constant.RecordStatus.INVALID);
				ps.setLong(3, LOANConstant.LoanPayNoticeStatus.REFUSE);
				break;
			case (int) LOANConstant.LoanType.TX:
				strSQL
						.append(" loan_discountcredence t where t.ncontractid = ? and t.nStatusId not in(?,?)");

				ps = conn.prepareStatement(strSQL.toString());
				ps.setLong(1, contractId);
				ps.setLong(2, Constant.RecordStatus.INVALID);
				ps.setLong(3, LOANConstant.DiscountCredenceStatus.REFUSE);
				break;
			case (int) LOANConstant.LoanType.YT:
				strSQL
						.append(" Loan_YT_DrawForm t where t.ncontractid = ? and t.nStatusId not in(?,?)");

				ps = conn.prepareStatement(strSQL.toString());
				ps.setLong(1, contractId);
				ps.setLong(2, Constant.RecordStatus.INVALID);
				ps.setLong(3, LOANConstant.LoanDrawNoticeStatus.REFUSE);
				break;
			case (int) LOANConstant.LoanType.DB:
				strSQL
						.append(" LOAN_ASSURECHARGEFORM t where t.contractid = ? and t.StatusId not in(?,?)");

				ps = conn.prepareStatement(strSQL.toString());
				ps.setLong(1, contractId);
				ps.setLong(2, Constant.RecordStatus.INVALID);
				ps.setLong(3, LOANConstant.AssureChargeNoticeStatus.REFUSE);
				break;
			case (int) LOANConstant.LoanType.RZZL:
				strSQL
						.append(" LOAN_ASSURECHARGEFORM t where t.contractid = ? and t.StatusId not in(?,?)");

				ps = conn.prepareStatement(strSQL.toString());
				ps.setLong(1, contractId);
				ps.setLong(2, Constant.RecordStatus.INVALID);
				ps.setLong(3, LOANConstant.LoanPayNoticeStatus.REFUSE);
				break;
			}

			rs = ps.executeQuery();
			System.out.println(strSQL);

			while (rs.next()) {
				lResult = lResult + 1;
			}

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
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
		return lResult;
	}

	public static void main(String args[]) {
		try {
			String contractCode = "";
			ContractDao cdao = new ContractDao();

			String strcode = cdao.getContractCode(165);
			// long lReturn = cdao.updateContractNextCheckLevel(1, -1);
			// System.out.println(cdao.getLatelyRate(-1, 507,
			// null).getLateRate());
			/*
			 * for (int i = 0; i < 2; i++) { ContractInfo info = new
			 * ContractInfo(); info = (ContractInfo)cdao.findByID(10);
			 * System.out.println(info.getBorrowAddress()); if (info != null) {
			 * System.out.println("合同号：" + DataFormat.formatInt(i,3) + "\t" +
			 * "利率：" + info.getLateRateString()); } }
			 * //System.out.println("----------------" + lReturn);
			 */
			System.out.println("合同号：" + strcode);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询未放款金额(合同未做放款单金额) by 2008-3-11 Modify by leiyang date 2008-07-15
	 * 区分是否循环贷款
	 * 
	 * @param lContractID
	 *            合同ID
	 * @return double
	 * @exception Exception
	 */
	public double findUnPayAmountByID(long lContractID, long payID)
			throws Exception {
		double unPayAmount = 0.0;
		double payAmount = 0.0;
		ContractInfo contractInfo = new ContractInfo();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		try {
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();

			if (lContractID > 0) {
				sbSQL.append(" select sum(lp.mamount) payAmount ");
				sbSQL.append(" from loan_PayForm lp ");
				sbSQL
						.append(" where lp.ncontractid = ? and lp.nstatusid != ? and lp.nstatusid != ? and lp.nstatusid != 0 ");
				if (payID > 0) {
					sbSQL.append(" and  lp.ID<>? ");
				}
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lContractID);
				ps.setLong(2, LOANConstant.LoanPayNoticeStatus.REFUSE);
				ps.setLong(3, LOANConstant.LoanPayNoticeStatus.CANCEL);
				if (payID > 0) {
					ps.setLong(4, payID);
				}
				rs = ps.executeQuery();

				if (rs.next()) {
					payAmount = rs.getDouble("payAmount");
				}

				ps.close();
				ps = null;

				contractInfo = this.findByID(lContractID);

				// 是否循环贷款
				if (contractInfo.getIsCircle() == 1) {
					// 查询未放款金额 = 合同金额 + 已还款金额 - 已发放金额
					unPayAmount = UtilOperation.Arith.sub(UtilOperation.Arith
							.add(contractInfo.getExamineAmount(), contractInfo
									.getAInfo().getRepayAmount()), contractInfo
							.getAInfo().getOpenAmount());
				} else {
					// 合同金额减去已生成放款单金额
					unPayAmount = UtilOperation.Arith
							.round(UtilOperation.Arith.sub(contractInfo
									.getExamineAmount(), payAmount), 2);
				}
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

		return unPayAmount;
	}

	/**
	 * 判断修改后的合同编号是否在表中已经存在 add by zwxiao 2008-4-16
	 * 
	 * @param lContractID
	 *            合同ID
	 * @return double
	 * @exception Exception
	 */
	public long hasNoContractNo(ContractInfo info) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		long lResult = -1;
		try {
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL
					.append(" select count(*) contractCount from loan_contractForm");
			sbSQL.append(" WHERE id != ? "); // 合同ID
			if (info.getContractCode() != null
					&& !info.getContractCode().equals("")) {
				sbSQL.append("  and sContractCode = ? "); // 合同 modify by xwhe
			}
			sbSQL.append("  and nstatusid !="
					+ LOANConstant.ContractStatus.CANCEL); // 过滤掉取消的合同状态
			sbSQL.append("  and NCURRENCYID =" + info.getCurrencyID()); // add
			// by
			// zwxiao
			// 2010-08-02
			// 加上办事处和币种的条件
			sbSQL.append("  and NOFFICEID =" + info.getOfficeID());
			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			int index = 1;
			if (info.getContractID() > 0) {
				ps.setLong(index++, info.getContractID());
			}
			if (info.getContractCode() != null
					&& !info.getContractCode().equals("")) {
				ps.setString(index++, info.getContractCode());
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				lResult = rs.getLong("contractCount");
			}
		} catch (Exception e) {
			log4j.error(e.toString());
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
		return lResult;
	}

	// 判断合同leftoversattornmentamount字段是否为空
	public boolean isNull_leftoversattornmentamount(long lContractID)
			throws Exception {
		boolean lResult = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select * from loan_contractForm");
			sbSQL
					.append(" WHERE id = ? and leftoversattornmentamount is not null"); // 合同ID
			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			int index = 1;
			if (lContractID > 0) {
				ps.setLong(index++, lContractID);
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				lResult = false;
			} else {
				lResult = true;
			}
		} catch (Exception e) {
			log4j.error(e.toString());
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
		return lResult;
	}

	/**
	 * 得到合同部分信息 add by xwhe
	 * 
	 * @param lID
	 *            合同ID
	 * @return ContractInfo 合同详细信息
	 * @exception Exception
	 */
	public ContractInfo findContractInfoByID(long lID) throws Exception {
		ContractInfo info = null;
		PreparedStatement ps = null;
		String strSQL = "";
		ResultSet rs = null;
		Connection con = null;

		try {
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT a.* ");
			sbSQL.append(" FROM loan_contractForm a ");
			sbSQL.append(" where a.id = ? ");

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lID);

			rs = ps.executeQuery();
			if (rs.next()) {
				info = new ContractInfo();
				info.setLoanID(rs.getLong("nLoanID")); // 贷款ID
				info.setContractID(rs.getLong("id")); // 合同ID
				info.setOfficeID(rs.getLong("NOFFICEID")); // 办事处id
				info.setCurrencyID(rs.getLong("NCURRENCYID")); // 币种
				info.setInputDate(rs.getTimestamp("dtInputDate")); // 合同录入日期
				info.setContractCode(rs.getString("sContractCode")); // 合同编号
				info.setApplyCode(rs.getString("sApplyCode")); // 申请书编号
				info.setBorrowClientID(rs.getLong("nBorrowClientID")); // 借款单位ID
				info.setLoanAmount(rs.getDouble("mLoanAmount")); // 借款金额
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 期限
				info.setLoanReason(rs.getString("SLOANREASON"));// 借款原因
				info.setOther(rs.getString("SOTHER")); // 其他
				info.setRiskLevel(rs.getLong("nRiskLevel")); // 合同风险等级
				info.setInterestTypeID(rs.getLong("nInterestTypeID")); // 利率类型
				info.setLiborRateID(rs.getLong("nLiborRateID")); // Libor利率ID
				info.setDiscountDate(rs.getTimestamp("dtDiscountDate"));// 贴现日期
				info.setClientID(rs.getLong("nConsignClientID")); // 委托单位
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
		return info;

	}

	/**
	 * 得到已经转让的贷款合同放款单总金额 Boxu Add 2008年11月6日
	 * 
	 * @param lID
	 *            放款单ID
	 * @return double 已经转让总金额
	 * @exception Exception
	 */
	public double sumLastAttornmentAmount(long lPayID) throws Exception {
		double lastAttornmentAmount = 0.0;
		StringBuffer sbSQL = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		try {
			con = Database.getConnection();
			sbSQL = new StringBuffer();
			sbSQL
					.append(" select sum(sa.attornmentamount) attornmentamount from sec_attornmentcontract sa ,SEC_AttornmentApplyForm b,sec_applyform c,sec_applycontract d");
			sbSQL.append(" where 1 = 1 ");
			sbSQL.append(" and sa.attornmentapplyid=b.id");
			sbSQL.append(" and b.repurchaseapplyid=c.id ");
			sbSQL.append(" and c.id=d.applyid ");
			sbSQL.append(" and ((d.statusid!="+SECConstant.ContractStatus.FINISH +" and d.transactiontypeid="+SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY+") or d.transactiontypeid!="+SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY+" ) ");
			sbSQL.append(" and sa.statusid in ( ");
			sbSQL.append(" " + LOANConstant.AttornmentStatus.SAVE + ", ");
			sbSQL.append(" " + LOANConstant.AttornmentStatus.SUBMIT + ", ");
			sbSQL.append(" " + LOANConstant.AttornmentStatus.CHECK + ", ");
			sbSQL.append(" " + LOANConstant.AttornmentStatus.USED + ", ");
			sbSQL.append(" " + LOANConstant.AttornmentStatus.APPROVALING + " ");
			sbSQL.append(" ) and sa.payid = ? ");
			log4j.info("sql="+sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lPayID);

			rs = ps.executeQuery();
			if (rs.next()) {
				lastAttornmentAmount = rs.getDouble("attornmentamount");
			}
		} catch (Exception e) {
			e.printStackTrace();
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
				throw new IException("Gen_E001");
			}
		}

		return lastAttornmentAmount;
	}

	// add by zwxiao 2010-07-10 通过时间得到最新的融资租赁的利率
	public RateInfo getLatelyRateForRZZL(long lContractID, Timestamp tsDate)
			throws Exception {
		RateInfo info = new RateInfo();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		StringBuffer sbSQL = new StringBuffer();

		try {
			con = Database.getConnection();

			if (tsDate == null || tsDate.equals("")) {
				tsDate = DataFormat.getDateTime(con);
			}

			// 取得利率类型
			sbSQL.setLength(0);
			// 查询合同是否做过利率调整(生效日期小于查询日期)
			sbSQL
					.append(" select * from loan_rateadjustpaycondition aa where aa.nstatusid = "
							+ LOANConstant.LoanStatus.CHECK
							+ " and aa.dtvalidate<=to_date('"
							+ DataFormat.formatDate(tsDate)
							+ "','yyyy-MM-dd') and aa.ncontractid = ? order by id desc ");
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lContractID);
			rs = ps.executeQuery();

			if (rs.next()) {// 如果做过调整取得调整的信息
				info.setLateBasicRate(rs.getDouble("mRate"));
				info.setRate(info.getLateBasicRate()
						* (1 + rs.getDouble("mAdjustRate") / 100)
						+ rs.getDouble("mStaidAdjustRate"));
				info.setAdjustRate(rs.getDouble("mAdjustRate"));
				info.setStaidAdjustRate(rs.getDouble("mStaidAdjustRate"));
			} else {// 如果没有做过调整就取得原始的合同利率
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				sbSQL.setLength(0);
				sbSQL
						.append(" select * from loan_contractform bb where bb.id = "
								+ lContractID);
				ps = con.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				if (rs.next()) {
					info.setLateBasicRate(rs.getDouble("mInterestRate"));
					info.setRate(info.getLateBasicRate());
					info.setAdjustRate(0.00);
					info.setStaidAdjustRate(0.00);
				}
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
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
		return info;
	}

	// add by zwxiao 2010-07-10 通过时间得到最新的融资租赁的利率（计划调整使用）
	public RateInfo getLatelyRateForRZZLPlan(long lContractID, Timestamp tsDate)
			throws Exception {
		RateInfo info = new RateInfo();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		StringBuffer sbSQL = new StringBuffer();

		try {
			con = Database.getConnection();

			if (tsDate == null || tsDate.equals("")) {
				tsDate = DataFormat.getDateTime(con);
			}

			// 取得利率类型
			sbSQL.setLength(0);
			// 查询合同是否做过利率调整(生效日期小于查询日期)
			sbSQL
					.append(" select * from loan_rateadjustpaycondition aa where aa.nstatusid > 0 and aa.dtvalidate<to_date('"
							+ DataFormat.formatDate(tsDate)
							+ "','yyyy-MM-dd') and aa.ncontractid = ? order by id desc ");
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lContractID);
			rs = ps.executeQuery();

			if (rs.next()) {// 如果做过调整取得调整的信息
				info.setLateBasicRate(rs.getDouble("mRate"));
				info.setRate(info.getLateBasicRate()
						* (1 + rs.getDouble("mAdjustRate") / 100)
						+ rs.getDouble("mStaidAdjustRate"));
				info.setAdjustRate(rs.getDouble("mAdjustRate"));
				info.setStaidAdjustRate(rs.getDouble("mStaidAdjustRate"));
			} else {// 如果没有做过调整就取得原始的合同利率
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				sbSQL.setLength(0);
				sbSQL
						.append(" select * from loan_contractform bb where bb.id = "
								+ lContractID);
				ps = con.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				if (rs.next()) {
					info.setLateBasicRate(rs.getDouble("mInterestRate"));
					info.setRate(info.getLateBasicRate());
					info.setAdjustRate(0.00);
					info.setStaidAdjustRate(0.00);
				}
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
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
		return info;
	}

	/**
	 * 通过ID得到风险状态，贷后调查报告用。
	 * 
	 * @param contractID
	 * @return
	 * @throws IException
	 */
	public RiskLevelInfo getRiskLevelByID(long contractID) throws IException {

		PreparedStatement ps = null;
		RiskLevelInfo riskInfo = new RiskLevelInfo();
		ResultSet rs = null;
		Connection con = null;

		try {
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT a.nrisklevel,a.nofficeid,a.ncurrencyid ");
			sbSQL.append(" FROM loan_contractForm a ");
			sbSQL.append(" where a.id = ? ");

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, contractID);

			rs = ps.executeQuery();
			if (rs.next()) {
				riskInfo.lContractOldRiskLevel = rs.getLong("nrisklevel");
				riskInfo.currencyid = rs.getLong("ncurrencyid");
				riskInfo.officeid = rs.getLong("nofficeid");
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
		return riskInfo;

	}

	/**
	 * 得到距离当前时间最近一次的合同风险状态
	 * 
	 * @param contractID
	 * @return
	 * @throws IException
	 */
	public RiskLevelInfo getOldRiskLevelByID(long contractID) throws IException {

		PreparedStatement ps = null;

		ResultSet rs = null;
		Connection con = null;
		RiskLevelInfo riskInfo = new RiskLevelInfo();
		try {
			con = Database.getConnection();
			String sbSQL = "select a.id,a.noldlevel as nrisklevel,a.nofficeid,a.ncurrencyid,a.ncontractid from loan_risklevel a,(select max(DTINPUTDATE) as maxdate from loan_risklevel where ncontractid   =? "
					+ " and nnextcheckuserid =-123) b where TO_CHAR(b.maxdate,'yyyymmdd')<= TO_CHAR(sysdate,'yyyymmdd') and a.ncontractid=? "
					+ " and a.nnextcheckuserid=-123 and rownum=1 order by id desc";

			log4j.info(sbSQL);
			ps = con.prepareStatement(sbSQL);
			ps.setLong(1, contractID);
			ps.setLong(2, contractID);

			rs = ps.executeQuery();
			if (rs.next()) {
				riskInfo.lContractOldRiskLevel = rs.getLong("nrisklevel");
				riskInfo.currencyid = rs.getLong("ncurrencyid");
				riskInfo.officeid = rs.getLong("nofficeid");
				riskInfo.m_lID = rs.getLong("id");
				riskInfo.m_lContractID = rs.getLong("ncontractid");
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
		return riskInfo;

	}

	public long initDiscountInterest() throws RemoteException, IException {
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		Connection conn = null;
		Connection conn1 = null;
		String strSQL = null;
		long ncontractid = -1;// 合同ID
		double dDiscountRate = 0; // 利率
		Timestamp tsDiscountDate = null; // 计息日
		double dCheckAmount = 0; // 实付金额

		Timestamp tsEnd = null; // 贴现到期日期
		String strEnd = ""; // 贴现日期
		long nDays = 0; // 实际贴现天数
		double dAmount = 0; // 票据金额
		double dAccrual = 0; // 每张票据贴现利息
//		double dAccrualTotal = 0;// 该凭证下所有票据的总利息
//		double dTotalAmount = 0; // 总票据金额
//		double dTotalRealAmount = 0; // 总票据实付金额
		double purchaserInterestRate = 0;
		long rValue = 0;
		try {
			conn = Database.getConnection();

			strSQL = " select id,purchaserInterestRate,mDiscountrate,dtDiscountdate from Loan_ContractForm a where a.ntypeid=3  ";

			ps = conn.prepareStatement(strSQL);
			// ps.setLong(1, lDiscountContractID);
			rs = ps.executeQuery();
			while (rs.next()) {
				purchaserInterestRate = rs.getDouble("purchaserInterestRate");// 买方付息比例
				tsDiscountDate = rs.getTimestamp("dtDiscountdate");// 贴现计息日
				dDiscountRate = rs.getDouble("mDiscountrate");// 贴现利率
				ncontractid = rs.getLong("id");
				double dAccrualTotal = 0;// 该凭证下所有票据的总利息
				double dTotalAmount = 0; // 总票据金额
				double dTotalRealAmount = 0; // 总票据实付金额
				if (ncontractid > 0) {
					
					// 更新记录
					strSQL = " select MAMOUNT,mCheckAmount,NISLOCAL,NADDDAYS,DTEND from Loan_DiscountContractBill where ncontractid=? ";
					conn1 = Database.getConnection();
					Log.print(strSQL);
					ps1 = conn1.prepareStatement(strSQL);
					ps1.setLong(1, ncontractid);
					rs1 = ps1.executeQuery();
					while (rs1.next()) {
						tsEnd = rs1.getTimestamp("DTEND");
						dAmount = rs1.getDouble("MAMOUNT");
						dCheckAmount = rs1.getDouble("mCheckAmount");
						nDays = 0;
						if (tsEnd != null && tsDiscountDate != null) {
							strEnd = tsEnd.toString();
							tsEnd = new java.sql.Timestamp(new Integer(strEnd
									.substring(0, 4)).intValue() - 1900,
									new Integer(strEnd.substring(5, 7))
											.intValue() - 1, new Integer(strEnd
											.substring(8, 10)).intValue(), 0,
									0, 0, 0);
							nDays = (int) java.lang.Math
									.ceil((tsEnd.getTime() - tsDiscountDate
											.getTime()) / 86400000)
									+ rs1.getInt("NADDDAYS"); // 加上节假日增加计息天数
						}
						if (nDays >= 0) {
							if (rs1.getLong("NISLOCAL") == LOANConstant.YesOrNo.NO) {
								nDays = nDays + 3;
							}
							System.out.println("the dDiscountRate is "
									+ dDiscountRate);
							System.out.println("the dAmount is " + dAmount);
							System.out.println("the nDays is " + nDays);
							System.out.println("the dAccrual is " + dAccrual);
							dAccrual = dAmount * (dDiscountRate / 360) * 0.01
									* nDays;
							System.out.println("the RealdAccrual is "
									+ dAccrual);

							System.out.println("the RealdAccrual is "
									+ dAccrual);
							dAccrualTotal += dAccrual;
							dTotalAmount += dAmount;
							dTotalRealAmount += dCheckAmount;
						}
					}
					rs1.close();
					rs1 = null;
					ps1.close();
					ps1 = null;
					conn1.close();
					conn1 = null;
					dAccrualTotal = DataFormat.formatDouble(dAccrualTotal, 2);
					System.out.println("汇票金额=" + dTotalAmount);
					System.out.println("汇票利息=" + dAccrualTotal);
					System.out.println("实付金额=" + dTotalRealAmount);
					strSQL = " update loan_contractform set MDISCOUNTACCRUAL=?, MPURCHASERAMOUNT=? where ID=? ";
					if (conn1 == null) {
						conn1 = Database.getConnection();
					}
					Log.print(strSQL);
					ps1 = conn1.prepareStatement(strSQL);
					// ps.setDouble(1, DataFormat.formatDouble(dTotalAmount));
					ps1.setDouble(1, DataFormat.formatDouble(dAccrualTotal)
							- DataFormat.formatDouble(dAccrualTotal
									* purchaserInterestRate * 0.01));
					ps1.setDouble(2, DataFormat.formatDouble(dAccrualTotal
							* purchaserInterestRate * 0.01));
					ps1.setLong(3, ncontractid);

					ps1.executeUpdate();
					rValue = 1;
					ps1.close();
					ps1 = null;

					conn1.close();
					conn1 = null;
				}
				

			}

		} catch (Exception e) {
			rValue = -1;
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
			} catch (Exception ex) {
				log4j.error(ex.toString());

			}
		}

		return rValue;
	}
	public long clearLastOrLeftOverAttormentAmount(long lContractID) throws Exception {
		PreparedStatement ps = null;
		Connection con = null;
		long lResult = -1;

		try {
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" UPDATE loan_contractForm");
			sbSQL.append(" SET lastattornmentamount = null , leftoversattornmentamount = null where id = ? "); // 合同状态
			
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lContractID);
			
			lResult = ps.executeUpdate();
			
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