/*
 * Created on 2004-3-24
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.freeapply.dao;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.contract.dataentity.ContractAmountInfo;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.freeapply.dataentity.FreeApplyInfo;
import com.iss.itreasury.loan.freeapply.dataentity.FreeApplyQueryInfo;
import com.iss.itreasury.loan.loanpaynotice.dao.LoanPayNoticeDao;
import com.iss.itreasury.loan.loanpaynotice.dataentity.LoanPayNoticeInfo;
import com.iss.itreasury.loan.obinterface.dao.OBLoanDao;
import com.iss.itreasury.loan.setting.dao.LoanTypeRelationDao;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.system.bizdelegation.ApprovalDelegation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;

/**
 * @author hyzeng
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FreeApplyDao {

	private Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this); //

	public FreeApplyInfo findFreeApplyByID(long ID) throws Exception {
		FreeApplyInfo Info = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = ""; // 主SQL语句
		try {
			// ////////////////////////////
			log4j.info("查找免还申请");
			con = Database.getConnection();
			strSQL = " select a.*  " + "  ,b.sName as InputUserName "
					+ "  ,c.sName as CheckUserName "
					+ " from loan_freeForm a, UserInfo b, UserInfo c "
					+ "  where  a.nInputUserID=b.ID(+) "
					+ "     and a.nNextCheckUserID = c.ID(+)   "
					+ "     and a.ID = " + ID + " ";
			log4j.info("免还申请 SQL :" + strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				Info = new FreeApplyInfo();
				Info.setID(rs.getLong("ID"));
				Info.setFreeCode(rs.getString("sCode"));
				Info.setContractID(rs.getLong("nContractID"));
				Info.setLoanPayID(rs.getLong("nPayFormID"));
				Info.setFreeAmount(rs.getDouble("mFreeAmount"));
				Info.setFreeDate(rs.getTimestamp("dtFreeDate"));
				Info.setFreeRate(rs.getDouble("mInterest"));
				Info.setFreeReason(rs.getString("sFreeReason"));
				Info.setStatusID(rs.getLong("nStatusID"));
				Info.setInputUserID(rs.getLong("nInputUserID"));
				Info.setInputUserName(rs.getString("InputUserName"));
				Info.setInputDate(rs.getTimestamp("dtInputDate"));
				Info.setCheckUserID(rs.getLong("nNextCheckUserID"));
				Info.setCheckUserName(rs.getString("CheckUserName"));
				Info.setConsignClientAccount(rs.getString("sAccountNo"));
				Info.setNextCheckLevel(rs.getLong("nNextCheckLevel"));
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
		}
		// /////////////////////////////////////////
		finally {
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

	/**
	 * 查询免还，操作Loan_FreeForm 表
	 * 
	 * @param lOfficeID
	 *            办事处标识
	 * @param lUserID
	 *            用户标识
	 * @param lActionID
	 *            动作类型，提交链结查找，或者复核链接查找
	 * @param lContractIDFrom
	 *            借款合同编号（由）
	 * @param lContractIDTo
	 *            借款合同编号（到）
	 * @param lClientID
	 *            借款单位标识
	 * @param dAmountFrom
	 *            金额（由）
	 * @param dAmountTo
	 *            金额（到）
	 * @param tsFrom
	 *            贷款期限（由）
	 * @param tsTo
	 *            贷款期限（到）
	 * @param lIntervalNum
	 *            期限（月）
	 * @param lStatusID
	 *            状态
	 * 
	 * @param lPageLineCount
	 *            行数
	 * @param lPageNo
	 *            页码
	 * @param lOrderParam
	 *            排序列
	 * @param lDesc
	 *            顺序
	 */
	public Collection findFreeApplyByMultiOption(FreeApplyQueryInfo qInfo)
			throws IException, RemoteException {
		int nType = qInfo.getType();
		long lOfficeID = qInfo.getOfficeId();
		long lCurrencyID = qInfo.getCurrencyId();
		long lUserID = qInfo.getUserID();
		long lActionID = qInfo.getActionID();
		long lContractIDFrom = qInfo.getContractIDFrom();
		long lContractIDTo = qInfo.getContractIDTo();
		long lClientID = qInfo.getClientID();
		double dAmountFrom = qInfo.getAmountFrom();
		double dAmountTo = qInfo.getAmountTo();
		Timestamp tsFrom = qInfo.getTsFrom();
		Timestamp tsTo = qInfo.getTsTo();
		long lIntervalNum = qInfo.getIntervalNum();
		long lStatusID = qInfo.getStatusID();
		long lPageLineCount = qInfo.getPageLineCount();
		long lPageNo = qInfo.getPageNo();
		long lOrderParam = qInfo.getOrderParam();
		long lDesc = qInfo.getDesc();

		Vector vReturn = new Vector(); // 当前页结果集
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int nIndex = 0;
		String strSQL = ""; // 主SQL语句
		String strSQL_Count = ""; // SQL计算语句
		String strSQL_Select = ""; // SQL查找语句
		String strSQL_Table = ""; // SQL的表以及其之间联系语句
		String strSQL_Option = ""; // SQL查找条件
		String strSQL_Order = ""; // SQL排序条件
		String strNextSql = "";
		long lRecordCount = -1; // 总记录数
		// double dLoanSum = 0.0; //满足条件的合同贷款总金额
		long lPageCount = -1; // 总页数
		long lRowNumStart = -1; // 开始记录
		long lRowNumEnd = -1; // 结束记录
		// 定义相应操作常量
		// 贷款
		long lModuleID = Constant.ModuleType.LOAN;
		// 模块类型
		long lLoanTypeID = (long) nType;
		long lFreeActionID = Constant.ApprovalAction.FREE_APPLY;
		try {
			log4j.info("查询免还");
			con = Database.getConnection();
			strSQL_Count = " select count(*) ";
			strSQL_Table = "    from loan_freeForm a "
					+ "      ,loan_contractForm b,CLIENT c "
					+ "       ,loan_payForm d "
					+ "      ,UserInfo u1,UserInfo u2 "
					+ " where a.nContractID=b.ID(+)  "
					+ "   and b.NBORROWCLIENTID=c.ID(+) "
					+ "    and a.nPayFormID=d.ID "
					+ "   and a.nInputUserID=u1.ID(+) "
					+ "   and a.nNextCheckUserID=u2.ID(+) " + "";
			strSQL_Option = " ";
			if (nType > -1) {
				strSQL_Option += " and b.nTypeID = " + nType;
			}
			if (lOfficeID > -1) {
				strSQL_Option += " and b.nOfficeID = " + lOfficeID;
			}
			if (lOfficeID > -1) {
				strSQL_Option += " and a.nOfficeID = " + lOfficeID;
			}
			if (lCurrencyID > -1) {
				strSQL_Option += " and a.nCurrencyID = " + lCurrencyID;
			}
			if (lContractIDFrom > -1) {
				strSQL_Option += " and b.ID >= " + lContractIDFrom;
			}
			if (lContractIDTo > -1) {
				strSQL_Option += " and b.ID <= " + lContractIDTo;
			}
			if (lClientID > -1) {
				strSQL_Option += " and b.NBORROWCLIENTID = " + lClientID;
			}
			if (dAmountFrom > 0) {
				strSQL_Option += " and b.MEXAMINEAMOUNT >= ? ";
			}
			if (dAmountTo > 0) {
				strSQL_Option += " and b.MEXAMINEAMOUNT <= ? ";
			}
			if (tsFrom != null) {
				strSQL_Option += " and TO_CHAR(b.DTSTARTDATE,'yyyy-mm-dd') "
						+ " >= TO_CHAR(?,'yyyy-mm-dd') ";
			}
			if (tsTo != null) {
				strSQL_Option += " and TO_CHAR(b.DTENDDATE,'yyyy-mm-dd') "
						+ " <= TO_CHAR(?,'yyyy-mm-dd') ";
			}
			if (lIntervalNum > 0) {
				strSQL_Option += " and b.nIntervalNum = " + lIntervalNum;
			}
			if (lActionID == LOANConstant.FreeApplyStatus.SUBMIT)// 修改查找
			{
				strSQL_Option += " and a.nStatusID= "
						+ LOANConstant.FreeApplyStatus.SUBMIT;
				// strSQL_Option += " and a.nNextCheckUserID= " + lUserID;
				strSQL_Option += " and a.nNextCheckLevel = 1 ";
				strSQL_Option += " and a.nInputUserID= " + lUserID;
			} else if (lActionID == LOANConstant.FreeApplyStatus.CHECK)// 审核查找
			{
				ApprovalDelegation appBiz = new ApprovalDelegation();
				LoanTypeRelationDao loanTypeDao = new LoanTypeRelationDao();
				// 获得真正来审批这个记录的人（真实或传给的虚拟的人！）
				String strUserID = "";
				// strUserID =
				// appBiz.findTheVeryUser(lModuleID,lLoanTypeID,lFreeActionID,lOfficeID,lCurrencyID,lUserID);
				long[] a_SubLoanType = loanTypeDao.getAllSetSubLoanTypeID(
						lOfficeID, lCurrencyID, new long[] { lLoanTypeID });
				if (a_SubLoanType != null && a_SubLoanType.length > 0) 
				{
					strNextSql = " ( ";
					for (int i = 0; i < a_SubLoanType.length; i++) 
					{
						strUserID = appBiz.findTheVeryUser(lModuleID,a_SubLoanType[i], lFreeActionID, lOfficeID,lCurrencyID, lUserID);
						if (strUserID != null ) 
						{
							strNextSql += " ( b.NSUBTYPEID = "
									+ a_SubLoanType[i]
									+ " and a.NNEXTCHECKUSERID in "
									+ strUserID +" ) ";
						} 
						if ( i < a_SubLoanType.length - 1) 
						{
							strNextSql += " or ";
						} 
						else 
						{
							strNextSql += " ) ";
						}
					}
				} 
				else 
				{
					return null;
				}
				
				if(lStatusID == LOANConstant.FreeApplyStatus.SUBMIT)
				{
					strSQL_Option += " and a.nStatusID = "
							+ LOANConstant.FreeApplyStatus.SUBMIT
							+ " and " + strNextSql;
				}
				else if(lStatusID == LOANConstant.FreeApplyStatus.CHECK)
				{
					strSQL_Option += " and a.nStatusID = "
						+ LOANConstant.FreeApplyStatus.CHECK;
				}
				else
				{
					strSQL_Option += " and ("
						+"  (a.nStatusID = "
						+ LOANConstant.FreeApplyStatus.SUBMIT
						+ " and " + strNextSql
						+ " ) or ( "
						+ " a.nStatusID = "
						+ LOANConstant.FreeApplyStatus.CHECK
						+"))";
				}
				
			}
			strSQL = strSQL_Count + strSQL_Table + strSQL_Option;
			log4j.info("  SQL=  " + strSQL);
			ps = con.prepareStatement(strSQL);
			// 查找条件设置
			nIndex = 1;
			if (dAmountFrom > 0) {
				ps.setDouble(nIndex, dAmountFrom);
				nIndex++;
			}
			if (dAmountTo > 0) {
				ps.setDouble(nIndex, dAmountTo);
				nIndex++;
			}
			if (tsFrom != null) //
			{
				ps.setTimestamp(nIndex, tsFrom);
				nIndex++;
			}
			if (tsTo != null) //
			{
				ps.setTimestamp(nIndex, tsTo);
				nIndex++;
			}
			rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				lRecordCount = rs.getLong(1); // 得到总记录数
				log4j.info("记录数=" + lRecordCount);
			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			// 计算总页数
			lPageCount = lRecordCount / lPageLineCount;
			if ((lRecordCount % lPageLineCount) != 0) {
				lPageCount++;
			}
			log4j.info("总记录数=" + lRecordCount);
			log4j.info("总页数=" + lPageCount);
			if (lRecordCount > 0) {
				// 返回结果集， 分页显示，显示下一页
				lRowNumStart = (lPageNo - 1) * lPageLineCount + 1; // 开始
				lRowNumEnd = lRowNumStart + lPageLineCount - 1; // 结束
				if (lRowNumEnd > lRecordCount) {
					lRowNumEnd = lRecordCount;
				}
				switch ((int) lOrderParam) {
				case 1: // 按合同编号排序
					strSQL_Order += " order by b.sContractCode ";
					break;
				case 2: // 放款编号
					strSQL_Order += " order by d.sCode ";
					break;
				case 3: // 免还编号
					strSQL_Order += " order by a.sCode ";
					break;
				case 4: // 按贷款单位名称排序
					strSQL_Order += " order by c.sName ";
					break;
				case 5: // 按贷款金额排序
					strSQL_Order += " order by b.MEXAMINEAMOUNT ";
					break;
				case 6: // 按贷款余额排序
					strSQL_Order += " order by a.mFreeAmount ";
					break;
				case 7: // 按贷款日期排序
					strSQL_Order += " order by b.dtStartDate ";
					break;
				case 8: // 按贷款期限排序
					strSQL_Order += " order by b.nIntervalNum ";
					break;
				case 9: // 按免还申请状态排序
					strSQL_Order += " order by a.nStatusID ";
					break;
				default:
					strSQL_Order += "";
				}
				if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC) {
					strSQL_Order += " desc ";
				}
				// /////////////////////////////////////////////////////////
				strSQL_Select = " select a.ID as FreeApplyID " // 免还ID
						+ " ,a.sCode as FreeCode " // 免还编号
						+ " ,a.nContractID as ContractID " // 合同ID
						+ " ,b.sContractCode as ContractCode " // 合同编号
						+ " ,d.ID as LoanPayID " + " ,d.sCode as LoanPayCode "
						+ " ,b.mLoanAmount " // 贷款金额
						+ " ,b.MEXAMINEAMOUNT"// 批准金额
						+ " ,b.nIntervalNum "// 贷款期限
						+ " ,b.dtStartDate,b.dtEndDate  "// 贷款日期
						+ " ,a.mFreeAmount " //
						+ " ,a.dtFreeDate " + " ,a.mInterest "
						+ " ,a.sAccountNo " + " ,a.sFreeReason "
						+ " ,a.nStatusID " + " ,a.nInputUserID "
						+ " ,u1.sName as InputUserName "
						+ " ,a.nNextCheckUserID "
						+ " ,u2.sName as CheckUserName "
						+ " ,c.sName as ClientName " // 借款单位
						+ " ,nvl(a.nNextCheckLevel,1) nNextCheckLevel " // 下一个审核级别
						+ "  ";
				strSQL = " select * from ( select b.*, rownum num from "
						+ " ( " + strSQL_Select + strSQL_Table + strSQL_Option
						+ strSQL_Order + " ) b )"
						+ " WHERE num BETWEEN ? AND ? ";
				// strSQL_Select+strSQL_Table+strSQL_Option+strSQL_Order;
				log4j.info("免还查找信息 SQL: " + strSQL);
				ps = con.prepareStatement(strSQL);
				// 查找条件设置
				nIndex = 1;
				if (dAmountFrom > 0) {
					ps.setDouble(nIndex, dAmountFrom);
					nIndex++;
				}
				if (dAmountTo > 0) {
					ps.setDouble(nIndex, dAmountTo);
					nIndex++;
				}
				if (tsFrom != null) //
				{
					ps.setTimestamp(nIndex, tsFrom);
					nIndex++;
				}
				if (tsTo != null) //
				{
					ps.setTimestamp(nIndex, tsTo);
					nIndex++;
				}
				ps.setLong(nIndex, lRowNumStart); // 给入起始行号
				nIndex++;
				ps.setLong(nIndex, lRowNumEnd); // 给入结束行号
				rs = ps.executeQuery();
				while (rs != null && rs.next()) {
					FreeApplyInfo Info = new FreeApplyInfo();
					Info.setID(rs.getLong("FreeApplyID"));
					Info.setFreeCode(rs.getString("FreeCode"));
					Info.setContractID(rs.getLong("ContractID"));
					Info.setContractCode(rs.getString("ContractCode"));
					Info.setLoanPayID(rs.getLong("LoanPayID"));
					Info.setLoanPayCode(rs.getString("LoanPayCode"));
					Info.setClientName(rs.getString("ClientName"));
					// Info.setAmount(rs.getDouble("mLoanAmount"));
					Info.setAmount(rs.getDouble("MEXAMINEAMOUNT"));
					ContractAmountInfo aInfo = new ContractAmountInfo();
					ContractInfo cInfo = new ContractInfo();
					ContractDao dao = new ContractDao();
					aInfo = dao.getLateAmount(Info.getContractID());
					cInfo.setAInfo(aInfo); // 合同金额
					Info.setBalance(cInfo.getAInfo().getBalanceAmount());// 合同当前余额
					Info.setStartDate(rs.getTimestamp("dtStartDate"));
					Info.setEndDate(rs.getTimestamp("dtEndDate"));
					Info.setIntervalNum(rs.getLong("nIntervalNum"));
					Info.setStatusID(rs.getLong("nStatusID"));
					Info.setPageCount(lPageCount);
					Info.setNextCheckLevel(rs.getLong("nNextCheckLevel"));
					// -------------------------------------------------//
					vReturn.addElement(Info);
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

		// ////////////////////////
		return vReturn.size() <= 0 ? null : vReturn;
	}

	/**
	 * 保存免还申请，操作Loan_FreeForm 表
	 * 
	 * @param lFreeApplyID
	 *            免还申请标识 ，如果<=0新增，否则修改
	 * @param lPayFormID
	 *            合同标识//现在是放款单标识
	 * @param dLoanAmount
	 *            贷款金额
	 * @param lIntervalNum
	 *            年期
	 * @param dLoanBalance
	 *            贷款余额
	 * @param tsEnd
	 *            到期日
	 * @param dFreeAmount
	 *            免还金额
	 * @param strConsignAccoutn,委托账户
	 * @param tsFreeDate
	 *            免还日期
	 * @param strFreeReason
	 *            免还原因
	 * @param dInterest
	 *            免还利息
	 * @param lInputUserID
	 *            录入人
	 * @param tsInputDate
	 *            录入时间
	 */
	public long saveFreeApply(FreeApplyInfo info) throws IException,
			RemoteException {
		long lOfficeID = info.getOfficeID();
		long lCurrencyID = info.getCurrencyID();
		long lFreeApplyID = info.getID();
		long lPayFormID = info.getLoanPayID();
		double dLoanAmount = info.getAmount();
		long lIntervalNum = info.getIntervalNum();
		double dLoanBalance = info.getBalance();
		Timestamp tsEnd = info.getEndDate();
		double dFreeAmount = info.getFreeAmount();
		String sConsignAccount = info.getConsignClientAccount();
		Timestamp tsFreeDate = info.getFreeDate();
		String strFreeReason = info.getFreeReason();
		double dInterest = info.getFreeRate();
		long lInputUserID = info.getInputUserID();
		Timestamp tsInputDate = info.getInputDate();

		long lResult = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int nIndex = 0;
		String strSQL = ""; // 主SQL语句
		long lMaxID = -1;
		long lStatusID = -1;
	//	long lContractID = 1;
		long lContractID = info.getContractID();
		String strFreeApplyCode = "";

		// 定义相应操作常量
		// 贷款
		long lModuleID = Constant.ModuleType.LOAN;
		// 模块类型
		long lLoanTypeID = Constant.ApprovalLoanType.OTHER;
		long lActionID = Constant.ApprovalAction.FREE_APPLY;

		long lApprovalID = -1;
		ApprovalDelegation appbiz = new ApprovalDelegation();
		// ApprovalDao appdao = new ApprovalDao(con);

		try {
			// 连结数据库
			con = Database.getConnection();
			if (lFreeApplyID <= 0) // 新增
			{
				log4j.info("免还新增");
				strSQL = "select nContractID  " + " from loan_payform "
						+ " where ID = " + lPayFormID;
				log4j.info("查找放款单的对应合同ID SQL" + strSQL);
				ps = con.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs.next()) {
					lContractID = rs.getLong(1);
					log4j.info("合同ID" + lContractID);
				}
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				strFreeApplyCode = getFreeApplyCode(lContractID);
				// 首先获得免还的新id
				strSQL = "select nvl(max(ID)+1,1) ID from loan_freeForm ";
				ps = con.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs.next()) {
					lMaxID = rs.getLong("ID");
					log4j.info("增加免还，获得免还ID" + lMaxID);
				}
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				// /////////////////////新增//////////////////////////
				if (lMaxID > 0) {
					strSQL = "insert into loan_freeForm ( "
							+ " ID,nOfficeID,nCurrencyID, nContractID,nPayFormID "//
							+ " , mFreeAmount, dtFreeDate, mInterest "
							+ " , sFreeReason, sAccountNo, nInputUserID "
							+ " ,nNextCheckUserID, dtInputDate, nStatusID "
							+ " ,sCode,nNextCheckLevel"
							+ " ) "
							+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE,?,?,1)";
					log4j.info("免还新增 SQL" + strSQL);
					ps = con.prepareStatement(strSQL);
					nIndex = 1;
					ps.setLong(nIndex, lMaxID);
					nIndex++;
					ps.setLong(nIndex, lOfficeID);
					nIndex++;
					ps.setLong(nIndex, lCurrencyID);
					nIndex++;
					ps.setLong(nIndex, lContractID);
					nIndex++;
					ps.setLong(nIndex, lPayFormID);
					nIndex++;
					ps.setDouble(nIndex, dFreeAmount);
					nIndex++;
					ps.setTimestamp(nIndex, tsFreeDate);
					nIndex++;
					ps.setDouble(nIndex, dInterest);
					nIndex++;
					ps.setString(nIndex, strFreeReason);
					nIndex++;
					ps.setString(nIndex, sConsignAccount);
					nIndex++;
					ps.setLong(nIndex, lInputUserID);
					nIndex++;
					ps.setLong(nIndex, lInputUserID);
					nIndex++;
					ps.setLong(nIndex, Constant.RecordStatus.VALID);
					nIndex++;
					ps.setString(nIndex, strFreeApplyCode);
					lResult = ps.executeUpdate();
					if (ps != null) {
						ps.close();
						ps = null;
					}
					if (lResult < 0) {
						log4j.info("新增失败，返回值：" + lResult);
						lResult = -1;
					} else {
						lResult = lMaxID;
					}
				} // end lMaxID>0 可以新增
			} else // lFreeApplyID > 0 修改
			{
				// ---------是否审核后重新提交-------------//
				// 首先获得免还的状态
				strSQL = "select nStatusID from loan_freeForm "
						+ " where ID = " + lFreeApplyID;
				ps = con.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs.next()) {
					lStatusID = rs.getLong("nStatusID");
					log4j.info("获得免还的状态:" + lStatusID);
				}
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (lStatusID == LOANConstant.FreeApplyStatus.CHECK) {
					// 获得ApprovalID
					lApprovalID = appbiz.getApprovalID(lModuleID, lLoanTypeID,
							lActionID, lOfficeID, lCurrencyID);
					// --------如果是审核后重新提交--------//
					try {
						// 删除以前的审核意见记录（物理删除）
						appbiz.deleteApprovalTracing(lModuleID, lLoanTypeID,
								lActionID, lOfficeID, lCurrencyID,
								lFreeApplyID, 1);
						log4j.info("删除以前的审核意见记录（物理删除）");
					} catch (Exception e) {
						log4j.error(e.toString());
						throw new IException("Gen_E001");
					}
				}

				// -----------------修改-------------------//
				strSQL = " Update loan_freeForm  " + " set mFreeAmount = ? "
						+ "   , dtFreeDate = ? " + "   , mInterest = ? "
						+ "   , sFreeReason = ? " + "   , sAccountNo = ? "
						// + " , nInputUserID = ? "
						+ "   , nNextCheckUserID = ? " + "   , nStatusID = ? "
						+ "   , nNextCheckLevel = 1 " + " where ID = "
						+ lFreeApplyID + " ";
				log4j.info("免还修改 SQL" + strSQL);
				ps = con.prepareStatement(strSQL);
				nIndex = 1;
				ps.setDouble(nIndex, dFreeAmount);
				nIndex++;
				ps.setTimestamp(nIndex, tsFreeDate);
				nIndex++;
				ps.setDouble(nIndex, dInterest);
				nIndex++;
				ps.setString(nIndex, strFreeReason);
				nIndex++;
				ps.setString(nIndex, sConsignAccount);
				nIndex++;
				ps.setLong(nIndex, lInputUserID);
				nIndex++;
				ps.setLong(nIndex, LOANConstant.FreeApplyStatus.SUBMIT);
				// nIndex++;
				lResult = ps.executeUpdate();
				if (lResult < 0) {
					log4j.info("修改失败，返回值：" + lResult);
					lResult = -1;
				} else {
					lResult = lFreeApplyID;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
			}
			con.close();
			con = null;
		} catch (SQLException e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		// ////////////////////////////////////////
		finally {
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

	/*
	 * 获得提款通知单编号 return String sReturnCode
	 */
	private String getFreeApplyCode(long lContractID) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		String strSQL = null;
		String sTemp = "";
		long lCode = 1;
		String sReturnCode = "";
		try {
			con = Database.getConnection();
			strSQL = "select nvl(max(sCode),'MH0') sCode "
					+ " from Loan_freeform where nContractID = " + lContractID
					+ " and nStatusID > 0 ";
			Log.print(strSQL);
			ps = con.prepareStatement(strSQL);
			// ps.setLong(1,lContractID);
			rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				sTemp = rs.getString("sCode");
				sTemp = sTemp.substring(2);
				lCode = Long.parseLong(sTemp) + 1;
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

			// 凑齐三位
			sReturnCode = "MH" + DataFormat.formatInt((int) lCode, 3, true);

		} catch (Exception e) {
			log4j.error("catch a error");
			throw e;
		} finally {
			try {
			} catch (Exception ex) {
			}
		}
		return sReturnCode;

	}
	
	//更新状态
  public long updateLoanStatus(long loanID, long userID, long newStatusID)
	throws Exception {
  PreparedStatement ps = null;
  Connection conn = null;
  String strSQL = null;
   long lResult = -1;

try {
	conn = Database.getConnection();
	strSQL = " update loan_freeform set nstatusid=? where id=?";

	ps = conn.prepareStatement(strSQL);
	ps.setLong(1, newStatusID);
	ps.setLong(2, loanID);

	lResult = ps.executeUpdate();
	/**
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
	**/
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
		strSQL = " update loan_freeform set nstatusid=? where id=? and nstatusid=?";
	
		ps = conn.prepareStatement(strSQL);
		ps.setLong(1, newStatusID);
		ps.setLong(2, loanID);
		ps.setLong(3, LOANConstant.FreeApplyStatus.CHECK);
	
		lResult = ps.executeUpdate();

		cleanup(ps);
		cleanup(conn);

	} catch (Exception e) {
	
		cleanup(ps);
		cleanup(conn);
		log4j.error(e.toString());
		throw new IException("Gen_E001");
	} finally {
	
		cleanup(ps);
		cleanup(conn);
	}
	return lResult;
  }
  
  /**
	 * @param payID 放款通知单ID
	 * @param rePayID 还款通知单ID
	 * @return 查询未还款金额
	 * @throws RemoteException
	 * @throws IException
	 */
	public double findUnRePayAmountByID(long payID,long rePayID) throws Exception {
		double unRePayAmount = 0.0;
		double rePayAmount = 0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		try 
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			
			if (payID > 0) 
			{
				sbSQL.append(" select sum(lep.MFREEAMOUNT) RepayAmount ");
				sbSQL.append(" from loan_freeform lep ");
				sbSQL.append(" where lep.NPAYFORMID = ? and lep.nstatusid != ? and lep.nstatusid != ? and lep.nstatusid != ? and lep.nstatusid != 0 ");
				if(rePayID>0)
				{
					sbSQL.append(" and  lep.ID<>? ");
				}
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, payID);
				ps.setLong(2, LOANConstant.FreeApplyStatus.REFUSE);
				ps.setLong(3, LOANConstant.FreeApplyStatus.CANCEL);
				ps.setLong(4, LOANConstant.FreeApplyStatus.USED);
				if(rePayID>0)
				{
					ps.setLong(5, rePayID);
				}
				rs = ps.executeQuery();

				if (rs.next()) 
				{
					rePayAmount = rs.getDouble("RepayAmount");
				}
				
				ps.close();
				ps = null;
				LoanPayNoticeDao payDao = new LoanPayNoticeDao();
				LoanPayNoticeInfo pinfo = payDao.findLoanPayNoticeByID(payID);
				
				//放款金额减去已生成还款单金额
				unRePayAmount = UtilOperation.Arith.round(UtilOperation.Arith.sub(pinfo.getBalance(), rePayAmount), 2);
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
		catch (Exception e) 
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		} 
		finally 
		{
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
			} 
			catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		
		return unRePayAmount;
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
	
	

}
