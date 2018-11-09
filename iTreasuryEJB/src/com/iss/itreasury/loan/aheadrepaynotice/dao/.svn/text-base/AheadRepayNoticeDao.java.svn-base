/*
 * Created on 2003-12-15
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.aheadrepaynotice.dao;

import java.util.*;
import java.rmi.RemoteException;
import java.sql.*;

import com.iss.itreasury.loan.contract.dataentity.*;
import com.iss.itreasury.loan.contract.dao.*;
//import com.iss.itreasury.system.bizlogic.ApprovalBiz;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.system.bizdelegation.*;
import com.iss.itreasury.loan.setting.dao.LoanTypeRelationDao;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.aheadrepaynotice.dataentity.*;
import com.iss.itreasury.loan.loanpaynotice.dao.*;
import com.iss.itreasury.loan.loanpaynotice.dataentity.LoanPayNoticeInfo;

/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AheadRepayNoticeDao
{

	private static Log4j log4j = null;

	public AheadRepayNoticeDao()
	{
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
	}

	/**
	 * 根据条件查询(修改)
	 * Create Date: 2003-10-15
	 * @param AheadRepayNoticeQueryInfo 查询条件 
	 * @return Collection
	 * @exception Exception
	 */
	public Collection queryForUpdate(AheadRepayNoticeQueryInfo qInfo) throws Exception
	{
		Vector v = new Vector();

		//分页变量
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

		try
		{
			con = Database.getConnection();

			//计算记录总数
			strSQL_pre = " SELECT COUNT(*) ";
			strSQL_pre += " FROM loan_AheadRepayForm a,loan_ContractForm b";

			//查询条件
			strSQL_con = " WHERE b.id = a.nContractID";
			strSQL_con += " AND a.nStatusID =" + LOANConstant.AheadRepayStatus.SUBMIT;
			//strSQL_con += " AND a.nNextCheckUserID =? "; 
			strSQL_con += " and a.nNextCheckLevel = 1 ";
			strSQL_con += " AND a.nInputUserID=?";
			strSQL_con += " AND a.nCurrencyID=?";
			strSQL_con += " AND a.nOfficeID=?";

			//lClientID借款单位ID
			if (qInfo.getClientID() > 0)
			{
				strSQL_con += " AND b.nBorrowClientID = ?";
			}

			//lContractIDTo 合同编号
			if (qInfo.getContractID() > 0)
			{
				strSQL_con += " AND b.ID = ?";
			}

			//dAmountFrom金额起
			if (qInfo.getAmountFrom() > 0)
			{
				strSQL_con += " AND a.mAmount >= ?";
			}

			//dAmountTo金额止
			if (qInfo.getAmountTo() > 0)
			{
				strSQL_con += " AND a.mAmount <= ?";
			}

			//录入日期起
			if (qInfo.getInputDateFrom() != null)
			{
				strSQL_con += " AND TO_CHAR(a.dtInputDate,'yyyy-mm-dd') >= TO_CHAR(?,'yyyy-mm-dd')";
			}

			//录入日期止
			if (qInfo.getInputDateTo() != null)
			{
				strSQL_con += " AND TO_CHAR(a.dtInputDate,'yyyy-mm-dd') <=TO_CHAR(?,'yyyy-mm-dd')";
			}

			strSQL = strSQL_pre + strSQL_con;
			log4j.info(strSQL);
			System.out.println(strSQL);
			ps = con.prepareStatement(strSQL);

			//ps.setLong(lIndex++, qInfo.getInputUserID());
			ps.setLong(lIndex++, qInfo.getInputUserID());
			ps.setLong(lIndex++, qInfo.getCurrencyID());
			ps.setLong(lIndex++, qInfo.getOfficeID());

			//lClientID借款单位ID
			if (qInfo.getClientID() > 0)
			{
				ps.setLong(lIndex++, qInfo.getClientID());
			}

			// 合同编号
			if (qInfo.getContractID() > 0)
			{
				ps.setLong(lIndex++, qInfo.getContractID());
			}

			//dAmountFrom金额起
			if (qInfo.getAmountFrom() > 0)
			{
				ps.setDouble(lIndex++, qInfo.getAmountFrom());
			}

			//dAmountTo金额止
			if (qInfo.getAmountTo() > 0)
			{
				ps.setDouble(lIndex++, qInfo.getAmountTo());
			}

			//录入日期起
			if (qInfo.getInputDateFrom() != null)
			{
				ps.setTimestamp(lIndex++, qInfo.getInputDateFrom());
			}

			//录入日期止
			if (qInfo.getInputDateTo() != null)
			{
				ps.setTimestamp(lIndex++, qInfo.getInputDateTo());
			}

			rs = ps.executeQuery();

			if (rs != null && rs.next())
			{
				lRecordCount = rs.getLong(1);
			}
			log4j.info("lRecordCount:" + lRecordCount);

			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}

			//计算总页数
			if (lRecordCount > qInfo.getPageLineCount())
			{
				lPageCount = lRecordCount / qInfo.getPageLineCount();
				if ((lRecordCount % qInfo.getPageLineCount()) != 0)
				{
					lPageCount++;
				}
			}
			else if (lRecordCount > 0 && lRecordCount <= qInfo.getPageLineCount())
			{
				lPageCount = 1;
			}
			else
			{
				lPageCount = 0;
			}

			//返回需求的结果集

			//分页显示，显示下一页
			lRowNumStart = (qInfo.getPageNo() - 1) * qInfo.getPageLineCount() + 1;
			lRowNumEnd = lRowNumStart + qInfo.getPageLineCount() - 1;

			switch ((int) qInfo.getOrderParam())
			{
				case 1 : //按合同编号排序
					strSQL_order += " ORDER BY b.sContractCode";
					break;
				case 2 : //按借款单位排序
					strSQL_order += " ORDER BY c.sName";
					break;
				case 3 : //按放款通知单排序
					strSQL_order += " ORDER BY d.sCode";
					break;
				case 4 : //按放款金额排序
					strSQL_order += " ORDER BY d.mAmount";
					break;
				case 5 : //按提前还款金额排序
					strSQL_order += " ORDER BY a.mAmount";
					break;
				case 6 : //按录入日期排序
					strSQL_order += " ORDER BY a.dtInputDate";
					break;
				case 7 : //按通知单状态排序
					strSQL_order += " ORDER BY a.nStatusID";
					break;
				case 8 : //按是否提前还款
					strSQL_order += " ORDER BY a.nIsAhead";
					break;
				default :
					strSQL_order += " ORDER BY a.dtInputDate DESC";
			}

			if (qInfo.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strSQL_order += " DESC";
			}

			//got the sql sentence
			strSQL_pre = "SELECT * FROM ";
			strSQL_pre += " ( SELECT all_record.*,ROWNUM num FROM";
			strSQL_pre += " ( SELECT a.*,";
			strSQL_pre += " b.sContractCode,c.sName,";
			strSQL_pre += " d.sCode as PayCode,d.mAmount as PayAmount";
			strSQL_pre += " FROM loan_AheadRepayForm a,loan_ContractForm b,";
			strSQL_pre += " Client c,loan_PayForm d";

			strSQL_con += " AND b.nBorrowClientID=c.ID(+)";
			strSQL_con += " AND a.nLoanPayNoticeID=d.ID";
			strSQL_con += strSQL_order;
			strSQL_con += ")all_record ";
			strSQL_con += ") WHERE num BETWEEN ? AND ?";

			strSQL = strSQL_pre + strSQL_con;
			log4j.info(strSQL);
			ps = con.prepareStatement(strSQL);
			lIndex = 1;
			//ps.setLong(lIndex++, qInfo.getInputUserID());
			ps.setLong(lIndex++, qInfo.getInputUserID());
			ps.setLong(lIndex++, qInfo.getCurrencyID());
			ps.setLong(lIndex++, qInfo.getOfficeID());

			//lClientID借款单位ID
			if (qInfo.getClientID() > 0)
			{
				ps.setLong(lIndex++, qInfo.getClientID());
			}

			// 合同编号
			if (qInfo.getContractID() > 0)
			{
				ps.setLong(lIndex++, qInfo.getContractID());
			}

			//dAmountFrom金额起
			if (qInfo.getAmountFrom() > 0)
			{
				ps.setDouble(lIndex++, qInfo.getAmountFrom());
			}

			//dAmountTo金额止
			if (qInfo.getAmountTo() > 0)
			{
				ps.setDouble(lIndex++, qInfo.getAmountTo());
			}

			if (qInfo.getInputDateFrom() != null)
			{
				ps.setTimestamp(lIndex++, qInfo.getInputDateFrom());
			}

			if (qInfo.getInputDateTo() != null)
			{
				ps.setTimestamp(lIndex++, qInfo.getInputDateTo());
			}

			ps.setLong(lIndex++, lRowNumStart); //给入起始行号
			ps.setLong(lIndex++, lRowNumEnd); //给入结束行号

			rs = ps.executeQuery();

			while (rs != null && rs.next())
			{
				AheadRepayNoticeInfo info = new AheadRepayNoticeInfo();
				info.setID(rs.getLong("id")); //提前还款通知单ID
				info.setContractCode(rs.getString("sContractCode")); //合同编号
				info.setClientName(rs.getString("sName")); //贷款单位
				info.setLetoutNoticeCode(rs.getString("PayCode")); //放款通知单编号
				info.setLetoutNoticeAmount(rs.getDouble("PayAmount")); //放款金额
				info.setAmount(rs.getDouble("mAmount")); //提前还款金额
				info.setInputDate(rs.getTimestamp("dtInputDate")); //录入日期
				info.setStatus(LOANConstant.AheadRepayStatus.getName(rs.getLong("nStatusID")));
				//通知单状态
				info.setIsAhead(rs.getLong("nIsAhead"));//是否提前还款
				info.setPageCount(lPageCount); //记录总的页面数
				v.addElement(info);
			}
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
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
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return (v.size() > 0 ? v : null);
	}

	/**
	* 根据条件查询合同信息(审核)
	* Create Date: 2003-10-15
	* @param ContractQueryInfo 查询条件
	* @return Collection
	* @exception Exception
	*/
	public Collection queryForExamine(AheadRepayNoticeQueryInfo qInfo) throws Exception
	{
		Vector v = new Vector();

		//分页变量
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

		try
		{
			//获得真正来审批这个记录的人（真实或传给的虚拟的人！）
			ApprovalDelegation appBiz = new ApprovalDelegation();
			long lModuleID = Constant.ModuleType.LOAN; //模块类型
			long lActionID = Constant.ApprovalAction.AHEADREPAY_NOTICE; //提前还款通知单审核
			//String strUser = appBiz.findTheVeryUser(lModuleID, Constant.ApprovalLoanType.OTHER, lActionID,qInfo.getOfficeID(),qInfo.getCurrencyID(), qInfo.getInputUserID());

			con = Database.getConnection();

			//计算记录总数
			strSQL_pre = " SELECT COUNT(*) ";
			strSQL_pre += " FROM loan_AheadRepayForm a,loan_ContractForm b";

			//查询条件
			strSQL_con = " WHERE b.id = a.nContractID";
            /*
			strSQL_con += " AND (a.nNextCheckUserID IN " + strUser;
			strSQL_con += " OR (a.nNextCheckUserID=-2";
			strSQL_con += " AND a.nInputUserID=?))";
			strSQL_con += " AND (a.nStatusID =" + LOANConstant.AheadRepayStatus.SUBMIT;
			strSQL_con += " OR a.nStatusID =" + LOANConstant.AheadRepayStatus.CHECK + ")";
            //*/
			//没有 “贴现“、“担保“、“转贴现”、“融资租赁”、“银团”贷款类型
			LoanTypeRelationDao loanTypeDao = new LoanTypeRelationDao();
			long[] loanTypeId = {LOANConstant.LoanType.ZY,LOANConstant.LoanType.WT,
					LOANConstant.LoanType.ZGXE,LOANConstant.LoanType.MFXD,
					LOANConstant.LoanType.OTHER
			}; 
			String strUser = null;
			long[] a_SubLoanType = loanTypeDao.getAllSetSubLoanTypeID(
					qInfo.getOfficeID(),qInfo.getCurrencyID(), loanTypeId );
			if (a_SubLoanType != null && a_SubLoanType.length > 0) {
				strSQL_con += " AND ((( ";
				for (int i = 0; i < a_SubLoanType.length; i++) {
					strUser  =	 appBiz.findTheVeryUser(lModuleID,
						a_SubLoanType[i], lActionID,qInfo.getOfficeID(),qInfo.getCurrencyID(),qInfo.getInputUserID());
					strSQL_con += " a.nNextCheckUserID IN " + strUser ;
					if (i < a_SubLoanType.length - 1)
						strSQL_con += " or ";
					else
						strSQL_con += " ) ";
					}					
			}else{
				return null;
			}
			
           // strSQL_con += " AND ((a.nNextCheckUserID IN " + strUser ;
            strSQL_con += "   AND a.nStatusID =" + LOANConstant.AheadRepayStatus.SUBMIT;
            strSQL_con += "     )";
            strSQL_con += " OR (a.nNextCheckUserID=-2 ";
            strSQL_con += " AND (a.nStatusID =" + LOANConstant.AheadRepayStatus.CHECK ;
            strSQL_con += " OR a.nStatusID =" + LOANConstant.AheadRepayStatus.USED + ")";
            strSQL_con += "     ))";
            
			strSQL_con += " AND a.nCurrencyID=?";
			strSQL_con += " AND a.nOfficeID=?";

			//lClientID借款单位ID
			if (qInfo.getClientID() > 0)
			{
				strSQL_con += " AND b.nBorrowClientID = ?";
			}

			//lContractIDTo 合同编号
			if (qInfo.getContractID() > 0)
			{
				strSQL_con += " AND b.ID = ?";
			}

			//dAmountFrom金额起
			if (qInfo.getAmountFrom() > 0)
			{
				strSQL_con += " AND a.mAmount >= ?";
			}

			//dAmountTo金额止
			if (qInfo.getAmountTo() > 0)
			{
				strSQL_con += " AND a.mAmount <= ?";
			}

			//录入日期起
			if (qInfo.getInputDateFrom() != null)
			{
				strSQL_con += " AND TO_CHAR(a.dtInputDate,'yyyy-mm-dd') >= TO_CHAR(?,'yyyy-mm-dd')";
			}

			//录入日期止
			if (qInfo.getInputDateTo() != null)
			{
				strSQL_con += " AND TO_CHAR(a.dtInputDate,'yyyy-mm-dd') <=TO_CHAR(?,'yyyy-mm-dd')";
			}

			//状态
			if (qInfo.getStatusID() > 0)
			{
				strSQL_con += " AND a.nStatusID = ?";
			}

			strSQL = strSQL_pre + strSQL_con;
			log4j.info(strSQL);
			System.out.println(strSQL);
			ps = con.prepareStatement(strSQL);

			//ps.setLong(lIndex++, qInfo.getInputUserID());
			ps.setLong(lIndex++, qInfo.getCurrencyID());
			ps.setLong(lIndex++, qInfo.getOfficeID());

			//lClientID借款单位ID
			if (qInfo.getClientID() > 0)
			{
				ps.setLong(lIndex++, qInfo.getClientID());
			}

			// 合同编号
			if (qInfo.getContractID() > 0)
			{
				ps.setLong(lIndex++, qInfo.getContractID());
			}

			//dAmountFrom金额起
			if (qInfo.getAmountFrom() > 0)
			{
				ps.setDouble(lIndex++, qInfo.getAmountFrom());
			}

			//dAmountTo金额止
			if (qInfo.getAmountTo() > 0)
			{
				ps.setDouble(lIndex++, qInfo.getAmountTo());
			}

			//录入日期起
			if (qInfo.getInputDateFrom() != null)
			{
				ps.setTimestamp(lIndex++, qInfo.getInputDateFrom());
			}

			//录入日期止
			if (qInfo.getInputDateTo() != null)
			{
				ps.setTimestamp(lIndex++, qInfo.getInputDateTo());
			}

			//状态
			if (qInfo.getStatusID() > 0)
			{
				ps.setLong(lIndex++, qInfo.getStatusID());
			}

			rs = ps.executeQuery();

			if (rs != null && rs.next())
			{
				lRecordCount = rs.getLong(1);
			}
			log4j.info("lRecordCount:" + lRecordCount);

			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}

			//计算总页数
			if (lRecordCount > qInfo.getPageLineCount())
			{
				lPageCount = lRecordCount / qInfo.getPageLineCount();
				if ((lRecordCount % qInfo.getPageLineCount()) != 0)
				{
					lPageCount++;
				}
			}
			else if (lRecordCount > 0 && lRecordCount <= qInfo.getPageLineCount())
			{
				lPageCount = 1;
			}
			else
			{
				lPageCount = 0;
			}

			//返回需求的结果集

			//分页显示，显示下一页
			lRowNumStart = (qInfo.getPageNo() - 1) * qInfo.getPageLineCount() + 1;
			lRowNumEnd = lRowNumStart + qInfo.getPageLineCount() - 1;

			switch ((int) qInfo.getOrderParam())
			{
				case 1 : //按合同编号排序
					strSQL_order += " ORDER BY b.sContractCode";
					break;
				case 2 : //按借款单位排序
					strSQL_order += " ORDER BY c.sName";
					break;
				case 3 : //按放款通知单排序
					strSQL_order += " ORDER BY d.sCode";
					break;
				case 4 : //按放款金额排序
					strSQL_order += " ORDER BY d.mAmount";
					break;
				case 5 : //按提前还款金额排序
					strSQL_order += " ORDER BY a.mAmount";
					break;
				case 6 : //按录入日期排序
					strSQL_order += " ORDER BY a.dtInputDate";
					break;
				case 7 : //按通知单状态排序
					strSQL_order += " ORDER BY a.nStatusID";
					break;
				case 8 : //按是否提前还款
					strSQL_order += " ORDER BY a.nIsAhead";
					break;
				default :
					strSQL_order += " ORDER BY a.dtInputDate DESC";
			}

			if (qInfo.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strSQL_order += " DESC";
			}

			//got the sql sentence
			strSQL_pre = "SELECT * FROM ";
			strSQL_pre += " ( SELECT all_record.*,ROWNUM num FROM";
			strSQL_pre += " ( SELECT a.*,";
			strSQL_pre += " b.sContractCode,c.sName,";
			strSQL_pre += " d.sCode as PayCode,d.mAmount as PayAmount";
			strSQL_pre += " FROM loan_AheadRepayForm a,loan_ContractForm b,";
			strSQL_pre += " Client c,loan_PayForm d";

			strSQL_con += " AND b.nBorrowClientID=c.ID(+)";
			strSQL_con += " AND a.nLoanPayNoticeID=d.ID";
			strSQL_con += strSQL_order;
			strSQL_con += ")all_record ";
			strSQL_con += ") WHERE num BETWEEN ? AND ?";

			strSQL = strSQL_pre + strSQL_con;
			log4j.info(strSQL);
			ps = con.prepareStatement(strSQL);
			lIndex = 1;
			//ps.setLong(lIndex++, qInfo.getInputUserID());
			ps.setLong(lIndex++, qInfo.getCurrencyID());
			ps.setLong(lIndex++, qInfo.getOfficeID());

			//lClientID借款单位ID
			if (qInfo.getClientID() > 0)
			{
				ps.setLong(lIndex++, qInfo.getClientID());
			}

			// 合同编号
			if (qInfo.getContractID() > 0)
			{
				ps.setLong(lIndex++, qInfo.getContractID());
			}

			//dAmountFrom金额起
			if (qInfo.getAmountFrom() > 0)
			{
				ps.setDouble(lIndex++, qInfo.getAmountFrom());
			}

			//dAmountTo金额止
			if (qInfo.getAmountTo() > 0)
			{
				ps.setDouble(lIndex++, qInfo.getAmountTo());
			}

			if (qInfo.getInputDateFrom() != null)
			{
				ps.setTimestamp(lIndex++, qInfo.getInputDateFrom());
			}

			if (qInfo.getInputDateTo() != null)
			{
				ps.setTimestamp(lIndex++, qInfo.getInputDateTo());
			}

			//状态
			if (qInfo.getStatusID() > 0)
			{
				ps.setLong(lIndex++, qInfo.getStatusID());
			}

			ps.setLong(lIndex++, lRowNumStart); //给入起始行号
			ps.setLong(lIndex++, lRowNumEnd); //给入结束行号

			rs = ps.executeQuery();

			while (rs != null && rs.next())
			{
				AheadRepayNoticeInfo info = new AheadRepayNoticeInfo();
				info.setID(rs.getLong("id")); //提前还款通知单ID
				info.setContractCode(rs.getString("sContractCode")); //合同编号
				info.setClientName(rs.getString("sName")); //贷款单位
				info.setLetoutNoticeCode(rs.getString("PayCode")); //放款通知单编号
				info.setLetoutNoticeAmount(rs.getDouble("PayAmount")); //放款金额
				info.setAmount(rs.getDouble("mAmount")); //提前还款金额
				info.setInputDate(rs.getTimestamp("dtInputDate")); //录入日期
				info.setStatus(LOANConstant.AheadRepayStatus.getName(rs.getLong("nStatusID")));
				//通知单状态
				info.setIsAhead(rs.getLong("nIsAhead"));//是否提前还款
				info.setPageCount(lPageCount); //记录总的页面数
				v.addElement(info);
			}
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
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
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return (v.size() > 0 ? v : null);
	}

	/**
	* 得到放款通知单当前金额
	* Create Date: 2003-10-15
	* @param lID 放款通知单ID
	* @return double
	* @exception Exception
	*/
	public double getLateAmount(long lID) throws Exception
	{
		double dBalance = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();

			if (lID > 0)
			{
				sbSQL.append(" SELECT SUM(a.mBalance) Balance");
				sbSQL.append(" FROM sett_subAccount a,loan_payform b");
				sbSQL.append(" WHERE a.AL_nLoanNoteID = b.ID ");
				sbSQL.append(" AND b.id = ? ");

				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lID);
				rs = ps.executeQuery();

				if (rs.next())
				{
					dBalance = rs.getDouble("Balance");
				}
			}

			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
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
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return dBalance;
	}

	/**
	* 得到提前还款通知单详细信息
	* Create Date: 2003-10-15
	* @param lID 提前还款通知单ID
	* @return AheadRepayNoticeInfo 提前还款通知单详细信息
	* @exception Exception
	*/
	public AheadRepayNoticeInfo findByID(long lID) throws Exception
	{		AheadRepayNoticeInfo info = new AheadRepayNoticeInfo();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT a.*,");
			sbSQL.append(" b.id as ContractID,b.sContractCode,");
			sbSQL.append(" b.nIntervalNum,b.mExamineAmount,b.mAdjustRate,b.ntypeid,b.nsubtypeid,");
			sbSQL.append(" c.sCode as PayCode,c.mInterestRate,");
			sbSQL.append(" c.mAmount as PayAmount,c.id as PayID,");
			sbSQL.append(" d.sName as clientName,e.sName as userName");
            
            sbSQL.append(" ,pp.id as PlanID ");//2004-01-15 ninh
            
			sbSQL.append(" FROM loan_AheadRepayForm a,loan_ContractForm b,");
			sbSQL.append(" loan_PayForm c,Client d,userInfo e");
            
            sbSQL.append(" ,loan_loancontractplan pp ");//2004-01-15 ninh
            
			sbSQL.append(" WHERE b.id = a.nContractID");
			sbSQL.append(" AND c.ID=a.nLoanPayNoticeID");
			sbSQL.append(" AND b.nBorrowClientID=d.ID(+)");
			sbSQL.append(" AND a.nInputUserID = e.id(+)");

            sbSQL.append(" and b.id=pp.nContractID(+) ");//2004-01-15 ninh
            sbSQL.append(" and pp.nStatusID(+) = "+Constant.RecordStatus.VALID);//2004-01-15 ninh
            
			sbSQL.append(" AND a.id = ?");

            sbSQL.append(" order by pp.id desc ");//2004-01-15 ninh
            
			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lID);
             
			rs = ps.executeQuery();
			
			ContractDao dao = new ContractDao();
			LoanPayNoticeDao payDao = new LoanPayNoticeDao();

			if (rs.next())
			{
				
				info.setID(rs.getLong("id"));
				info.setCode(rs.getString("sCode")); //通知单编号
				info.setClientName(rs.getString("clientName")); //借款单位
				info.setContractCode(rs.getString("sContractCode")); //合同编号
				info.setContractID(rs.getLong("ContractID")); //合同ID
				info.setIntervalNum(rs.getLong("nIntervalNum")); //贷款期限
				info.setContractAmount(rs.getDouble("mExamineAmount")); //合同金额
				info.setLoanType(rs.getLong("ntypeid"));
				info.setLoanSubType(rs.getLong("nsubtypeid"));
				info.setPBackDate(rs.getTimestamp("payDate"));
				info.setBalanceAmount(rs.getDouble("interestAmount"));
               
                info.setPlanID(rs.getLong("PlanID"));//计划标识
                Log.print("PlanID:"+rs.getLong("PlanID")); 
                
				ContractAmountInfo cInfo = dao.getLateAmount(info.getContractID());
				info.setContractBalance(cInfo.getBalanceAmount()); //合同余额
				info.setLetoutNoticeCode(rs.getString("PayCode")); //放款通知单编号
				
				info.setLetoutNoticeRate(payDao.getLatelyRate(rs.getLong("PayID"),null));//放款通知单执行利率
				
				//放款通知单利率,提前还款利率
				info.setLetoutNoticeAmount(rs.getDouble("PayAmount")); //放款通知单金额
				info.setLetoutNoticeID(rs.getLong("PayID")); //放款通知单ID
				LoanPayNoticeInfo pinfo = payDao.findLoanPayNoticeByID(rs.getLong("PayID"));
				info.setLetoutNoticeBalance(pinfo.getBalance()); //放款通知单余额
				
				info.setAmount(rs.getDouble("mAmount")); //提前还款金额
				info.setNextCheckUserID(rs.getLong("nNextCheckUserID")); //审核人
				info.setNextCheckLevel(rs.getLong("nNextCheckLevel"));	//下一个审核级别
				info.setInputUserID(rs.getLong("nInputUserID")); //录入人ID
				info.setInputUserName(rs.getString("userName")); //录入人
				info.setInputDate(rs.getTimestamp("dtInputDate")); //录入时间
				info.setIsAhead(rs.getLong("nIsAhead"));//是否提前还款
				info.setStatusID(rs.getLong("nStatusID")); //状态
				info.setStatus(LOANConstant.AheadRepayStatus.getName(info.getStatusID())); 
				//状态 

			}

			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
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
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return info;

	}

	/**
	 * 生成提前还款通知单。
	 * @param AheadRepayNoticeInfo
	 * @return long
	 * @throws Exception
	 */
	public long insert(AheadRepayNoticeInfo info) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = "";

		long lID = -1;
		long lCode = 0;
		String sCode="";

		try
		{
			if (info != null)
			{
				conn = Database.getConnection();
				/*首先获得loan_AheadRepayForm 的 MAXID */
				strSQL = " SELECT NVL(MAX(ID)+1,1) ID";
				strSQL += " FROM loan_AheadRepayForm ";

				ps = conn.prepareStatement(strSQL);
				log4j.info(strSQL);
				rs = ps.executeQuery();
				if (rs.next())
				{
					lID = rs.getLong("ID");
				}
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
		
				strSQL = " SELECT ";
				strSQL += "  max(l.scode) ";
				strSQL += " FROM loan_AheadRepayForm l ";
				strSQL += " WHERE l.nContractID = ? ";
				strSQL += " and l.nstatusid != 6 ";
				ps = conn.prepareStatement(strSQL);
				
				ps.setLong(1, info.getContractID());
				log4j.info(strSQL);
				rs = ps.executeQuery();
				if (rs.next())
				{
					sCode = rs.getString(1);
					if(sCode==null)
					{
						lCode = 1;
					}else
					{
						lCode = Long.parseLong(sCode.substring(2))+1;
					}
					
				}
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				
				if (lCode < 10)
				{
					sCode = "TQ00" + lCode;
				}
				else if (lCode < 100 && lCode >= 10)
				{
					sCode = "TQ0" + lCode;
				}
				if (lCode >= 100)
				{
					sCode = "TQ" + lCode;
				}

				log4j.info("生成提前还款通知单：" + sCode);

				/* 复制信息从loan_loanForm 到loan_contractForm */
				strSQL = "INSERT INTO loan_AheadRepayForm ";
				strSQL += " (ID,nOfficeID,nCurrencyID,nContractID,";
				strSQL += " nLoanPayNoticeID,mAmount,sCode,nInputUserID,";
				strSQL += " dtInputDate,nNextCheckUserID,nStatusID,nNextCheckLevel,nIsAhead,INTERESTAMOUNT,PAYDATE,BATCHID)";
				strSQL += " values (?,?,?,?,?,?,?,?,sysdate,?,?,1,?,?,?,?)";

				log4j.info(strSQL);
				ps = conn.prepareStatement(strSQL);
				int n = 1;
				ps.setLong(n++, lID);
				ps.setLong(n++, info.getOfficeID());
				ps.setLong(n++, info.getCurrencyID());
				ps.setLong(n++, info.getContractID());
				ps.setLong(n++, info.getLetoutNoticeID());
				ps.setDouble(n++, info.getAmount());
				ps.setString(n++, sCode);
				ps.setLong(n++, info.getInputUserID());
				ps.setLong(n++, info.getInputUserID());
				ps.setLong(n++, LOANConstant.AheadRepayStatus.SUBMIT);
				ps.setLong(n++, info.getIsAhead());
				
				ps.setDouble(n++, info.getBalanceAmount());
				ps.setTimestamp(n++, info.getPBackDate());
				ps.setLong(n++, info.getBatchID());

				ps.executeUpdate();
				ps.close();
				ps = null;

			}
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (conn != null)
			{
				conn.close();
				conn = null;
			}
		}
		catch (Exception e)
		{
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (conn != null)
			{
				conn.close();
				conn = null;
			}
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lID;
	}

	/**
	* 修改提前还款通知单信息
	* Create Date: 2003-10-15
	* @param AheadRepayNoticeInfo
	* @return long 如大于0表示成功，小于等于0表示失败
	* @exception Exception
	*/
	public long update(AheadRepayNoticeInfo info) throws Exception
	{
		PreparedStatement ps = null;
		Connection con = null;
		long lResult = -1;

		try
		{
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" UPDATE loan_AheadRepayForm ");
			sbSQL.append(" SET nStatusID = ? "); //状态

			if (info.getAmount() > 0)
			{
				sbSQL.append(" ,mAmount = ?");
			}

			if (info.getNextCheckUserID() > 0 || info.getNextCheckUserID() < -1)
			{
				sbSQL.append(" ,nNextCheckUserID = ?"); //下一审核人
			}
			if(info.getPBackDate()!=null)
			{
				sbSQL.append(" ,PAYDATE = to_date('"+DataFormat.formatDate(info.getPBackDate())+"','yyyy-mm-dd')"); //匡算日期
			}
			if(info.getBalanceAmount()>0)
			{
				sbSQL.append(" ,INTERESTAMOUNT = "+info.getBalanceAmount()); //匡算利息
			}
			//是否提前还款
			if (info.getIsAhead() > 0)
			{
				sbSQL.append(" ,nIsAhead = " + info.getIsAhead()); //是否提前还款
			}
			
			//modify by zwxiao 2010-07-21 与上面判断重复，会导致系统异常
			//是还款利息
//			if (info.getBalanceAmount() > 0)
//			{
//				sbSQL.append(" ,interestamount = " + info.getBalanceAmount()); //是否提前还款
//			}
			
			/**2007/03/20 mzh_fu  begin*/
			//（如果下级审核人非最后一级）
			if (info.getNextCheckUserID() > 0)
			{
			    sbSQL.append(" ,nNextCheckLevel = nNextCheckLevel + 1"); //下一个审核级别
			}
			/* 如果是返回修改时,不需要改变下一个审核级别*/
			/** 2007/03/20 mzh_fu
			//返回修改
			if (info.getNextCheckLevel() > 0)
			{
				sbSQL.append(" ,nNextCheckLevel = " + info.getNextCheckLevel()); //下一个审核级别
			}
			*/
			/**2007/03/20 mzh_fu  end*/
			
			sbSQL.append(" WHERE id = ? "); //提前还款通知单ID

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			int index = 1;
			ps.setLong(index++, info.getStatusID());

			if (info.getAmount() > 0)
			{
				ps.setDouble(index++, info.getAmount());
			}

			if (info.getNextCheckUserID() > 0 || info.getNextCheckUserID() < -1)
			{
				ps.setLong(index++, info.getNextCheckUserID());
			}

			ps.setLong(index++, info.getID());

			lResult = ps.executeUpdate();

			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
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
			try
			{
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lResult;

	}
	
	/**
	 * Modify by leiyang date 2007/07/27
	 * 
	 * @param id
	 * @param statusId
	 * @return
	 * @throws Exception
	 */
	public long updateStatusAndCheckStatus(long id, long statusId) throws Exception
	{
		PreparedStatement ps = null;
		Connection con = null;
		long lResult = -1;

		try
		{
			con = Database.getConnection();

			String sbSQL =  "UPDATE loan_AheadRepayForm SET nStatusID = ? where id = ? and nStatusID = ?";
			
			ps = con.prepareStatement(sbSQL);
			ps.setLong(1, statusId);
			ps.setLong(2, id);
			ps.setLong(3, LOANConstant.AheadRepayStatus.CHECK);

			lResult = ps.executeUpdate();

			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
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
			try
			{
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
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
	public double findUnRePayAmountByID(long payID,long batchID,long rePayID) throws Exception {
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
				sbSQL.append(" select sum(lep.mamount) RepayAmount ");
				sbSQL.append(" from loan_AheadRepayForm lep ");
				sbSQL.append(" where lep.NLOANPAYNOTICEID = ? and lep.nstatusid != ? and lep.nstatusid != ? and lep.nstatusid != ? and lep.nstatusid != 0 ");
				if(batchID>0){
					sbSQL.append(" and  lep.batchid <> "+batchID);
				}
				if(rePayID>0)
				{
					sbSQL.append(" and  lep.ID<>? ");
				}
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, payID);
				ps.setLong(2, LOANConstant.AheadRepayStatus.REFUSE);
				ps.setLong(3, LOANConstant.AheadRepayStatus.CANCEL);
				ps.setLong(4, LOANConstant.AheadRepayStatus.USED);
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
	
	/**
	 * 查找合同下的所有放款通知单
	 * @param 
	 */
	public Collection queryForRepayNotice(AheadRepayNoticeQueryInfo qInfo) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		LoanPayNoticeDao dao = new LoanPayNoticeDao();
		Timestamp tsDate = null;
		Vector v = new Vector();
		try
		{
			con = Database.getConnection();
			tsDate = DataFormat.getDateTime(con);
			StringBuffer sb = new StringBuffer();
			sb.append(" select a.*,b.ID nContractID,b.sContractCode,b.NOFFICEID, ");
			sb.append(" round(c.mRate,6) as mrate,");
			sb.append(" d.sname as sInputName,e.saccountno as sGrantCurrentAccount,e.sname as sGrantName,");
			sb.append(" f.sname as sBorrowClientName,");
			sb.append(" b.mloanamount,b.NINTERVALNUM,g.mrate as mContractRate,b.SLOANPURPOSE,");
			sb.append(" f.SZIPCODE,f.sPhone,f.sAddress,b.ntypeid,h.sname as sConsignClientname, ");
			sb.append(" i.mInterest as subInterest,i.mBalance as subBalance,j.sname as accname,j.sCode as acccode");
			sb.append(" from loan_payform a,loan_contractform b,loan_interestrate c,userinfo d,");
			sb.append("  sett_account e,client f,loan_interestrate g,client h ");
			sb.append("  ,sett_subaccount i ,sett_Branch j");
			sb.append("  where a.ncontractid = b.id(+) ");
			sb.append("  and a.nBankInterestID = c.id(+) ");
			sb.append("  and a.ninputuserid = d.id(+) ");
			sb.append("  and a.Ngrantcurrentaccountid = e.id(+) ");
			sb.append("  and b.nborrowclientid = f.id(+) ");
			sb.append("  and b.NBANKINTERESTID = g.id(+) ");
			sb.append("  and b.nconsignclientid = h.id(+) ");
			sb.append("  and i.AL_nLoanNoteID(+) = a.ID and i.nStatusID(+) = ? ");
			sb.append("  and a.nstatusid = "+LOANConstant.LoanPayNoticeStatus.USED);
			sb.append("  and a.ncontractid = ? and a.nAccountBankID = j.id(+)");
			//update by xfma 2008-11-25 按放款单的编号排序
			sb.append("  order by a.SCODE");
			ps = con.prepareStatement(sb.toString());
			ps.setLong(1, Constant.RecordStatus.VALID);
			ps.setLong(2, qInfo.getContractID());
			System.out.println(sb.toString());
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				LoanPayNoticeInfo info = new LoanPayNoticeInfo();
				info.setID(rs.getLong("ID"));
				info.setDrawNoticeID(rs.getLong("NDRAWNOTICEID"));
				info.setContractID(rs.getLong("nContractID"));
				info.setContractCode(rs.getString("sContractCode"));
				info.setLoanClientName(rs.getString("sBorrowClientName"));
				info.setLoanAmount(rs.getDouble("mLoanAmount"));
				info.setIntervalNum(rs.getLong("NINTERVALNUM"));
				//已发放金额info.set
				info.setContractRate(rs.getDouble("mContractRate"));
				info.setLoanPurpose(rs.getString("sLoanPurpose"));
				info.setOutDate(rs.getTimestamp("dtoutdate"));
				info.setCode(rs.getString("sCode"));
				info.setLoanZipCode(rs.getString("sZipCode"));
				info.setLoanPhone(rs.getString("sPhone"));
				info.setLoanAddress(rs.getString("sAddress"));
				info.setLoanTypeID(rs.getLong("nTypeID"));
				info.setGrantTypeID(rs.getLong("nGrantTypeID"));
				info.setGrantCurrentAccount(rs.getString("sGrantCurrentAccount"));
				info.setGrantCurrentName(rs.getString("sGrantName"));
				info.setAmount(rs.getDouble("mAmount"));
				info.setConsignClientName(rs.getString("sConsignClientName"));
				info.setConsignAccount(rs.getString("sConsignAccount"));
				//info.setInterestRate(rs.getDouble("mRate"));
				info.setCommissionRate(rs.getDouble("Mcommissionrate"));
				info.setSuretyFeeRate(rs.getDouble("mSuretyFeeRate"));
				info.setStart(rs.getTimestamp("dtStart"));
				info.setInputDate(rs.getTimestamp("DTINPUTDATE"));
				info.setEnd(rs.getTimestamp("dtEnd"));
				info.setCompanyLeader(rs.getString("sCompanyLeader"));
				info.setDepartmentLeader(rs.getString("sDepartmentLeader"));
				info.setCheckPerson(rs.getString("sCheckPerson"));
				info.setHandlingPerson(rs.getString("sHandlingPerson"));
				info.setInterest(rs.getDouble("subInterest"));
				info.setBalance(rs.getDouble("subBalance"));
				info.setAccountBankID(rs.getLong("nAccountBankID"));
				info.setAccountBankCode(rs.getString("acccode"));
				info.setAccountBankName(rs.getString("accname"));
				info.setGrantCurrentAccountID(rs.getLong("NGRANTCURRENTACCOUNTID"));
				info.setReceiveClientCode(rs.getString("sReceiveAccount"));
				info.setReceiveClientName(rs.getString("sReceiveClientName"));
				info.setRemitinProvince(rs.getString("sRemitinProvince"));
				info.setRemitinCity(rs.getString("sRemitinCity"));
				info.setRemitBank(rs.getString("sRemitBank"));
				info.setBankInterestID(rs.getLong("nBankInterestID"));
				info.setLoanAccount(rs.getString("sloanaccount"));
				info.setInterestRate(dao.getLatelyRate(info.getID(), null));
				info.setStatusID(rs.getLong("nStatusID"));
				info.setInputUserID(rs.getLong("nInputUserID"));
				info.setInputUserName(rs.getString("sInputName"));
				info.setWTInterestRate(rs.getDouble("minterestrate"));
				info.setReceiveAccount(rs.getString("sReceiveAccount"));
				//System.out.println("%%%%%%%% info.getOutDate()="+info.getOutDate());
				info.setOfficeID(rs.getLong("NOFFICEID"));
				info.setMbalance(rs.getDouble("subBalance"));
				info.setNextCheckLevel(rs.getLong("nNextCheckLevel"));
				info.setPreAdjustRate(rs.getDouble("MADJUSTRATE"));
				info.setPreStaidAdjustRate(rs.getDouble("MSTAIDADJUSTRATE"));
				info.setPreBasicInterestRate(rs.getDouble("mRate"));
				info.setInterestTypeID(rs.getLong("nInterestTypeID"));
				info.setLiborRateID(rs.getLong("nLiborRateID"));
				info.setIntervalNoticeNum(rs.getDouble("nIntervalNoticeNum"));
				v.addElement(info);
			}			
			rs.close();
			rs = null;
			ps.close();
			ps = null;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new Exception("remote exception : " + e.toString());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return v;
	}
	public static void main(String args[])
	{
		try
		{
			;

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * 生成提前还款通知单。
	 * @param AheadRepayNoticeInfo
	 * @return long
	 * @throws Exception
	 */
	public long insertBatch(BatchRepayNoticeInfo bInfo) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = "";

		long lID = -1;
		
		long lCode = 0;
		String sCode="";

		try
		{
			if (bInfo != null)
			{
				conn = Database.getConnection();
				/*首先获得LOAN_BATCHREPAYFORM 的 MAXID */
				strSQL = " SELECT NVL(MAX(ID)+1,1) ID";
				strSQL += " FROM LOAN_BATCHREPAYFORM ";

				ps = conn.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs.next())
				{
					lID = rs.getLong("ID");
				}
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}

				strSQL = " SELECT ";
				strSQL += " count(id)";
				strSQL += " FROM LOAN_BATCHREPAYFORM where NCONTRACTID=?";
			//	strSQL += " WHERE ID = ?";
				

				ps = conn.prepareStatement(strSQL);
				ps.setLong(1, bInfo.getContractID());
				rs = ps.executeQuery();
				if (rs.next())
				{
					lCode = rs.getLong(1)+1;
				}
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				
				if (lCode < 10)
				{
					sCode = "PL00" + lCode;
				}
				else if (lCode < 100 && lCode >= 10)
				{
					sCode = "PL0" + lCode;
				}
				if (lCode >= 100)
				{
					sCode = "PL" + lCode;
				}
				

				System.out.println("生成批量还款通知单：" + sCode);

				strSQL = "INSERT INTO LOAN_BATCHREPAYFORM ";
				strSQL += " (ID,nOfficeID,nCurrencyID,nContractID,";
				strSQL += " mAmount,sCode,nInputUserID,";
				strSQL += " dtInputDate,nStatusID,nIsAhead,PAYDATE,REPAYID,nisrepayinterest,minterest)";
				strSQL += " values (?,?,?,?,?,?,?,sysdate,?,?,?,?,?,?)";

				log4j.info(strSQL);
				ps = conn.prepareStatement(strSQL);
				int n = 1;
				ps.setLong(n++, lID);
				ps.setLong(n++, bInfo.getOfficeID());
				ps.setLong(n++, bInfo.getCurrencyID());
				ps.setLong(n++, bInfo.getContractID());
				ps.setDouble(n++, bInfo.getAmount());
				ps.setString(n++, sCode);
				ps.setLong(n++, bInfo.getInputUserID());
				ps.setLong(n++, LOANConstant.AheadRepayStatus.SUBMIT);
				ps.setLong(n++, bInfo.getIsAhead());
				ps.setTimestamp(n++, bInfo.getPBackDate());
				ps.setString(n++, bInfo.getRePayID());
				ps.setLong(n++, bInfo.getNisrepayinterest());
				ps.setDouble(n++, bInfo.getMinterest());
				ps.executeUpdate();
				ps.close();
				ps = null;

			}
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (conn != null)
			{
				conn.close();
				conn = null;
			}
		}
		catch (Exception e)
		{
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (conn != null)
			{
				conn.close();
				conn = null;
			}
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lID;
	}

	public long updateBatch(BatchRepayNoticeInfo bInfo)throws IException{
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer strSQL = new StringBuffer();
		long lResult = -1;

		try {
			conn = Database.getConnection();
			strSQL.append(" update LOAN_BATCHREPAYFORM ");
			strSQL.append(" set nStatusID = ? "); //状态

			if (bInfo.getAmount() > 0)
			{
				strSQL.append(" ,mAmount = " + bInfo.getAmount());
			}
			strSQL.append("  ,paydate = ? "); 
			strSQL.append("  ,minterest = ? "); 
			strSQL.append(" WHERE id = ? "); //提前还款通知单ID
			log4j.info(strSQL.toString());
			ps = conn.prepareStatement(strSQL.toString());
			int index = 1;
			ps.setLong(index++, bInfo.getStatusID());
			ps.setTimestamp(index++, bInfo.getPBackDate());
			ps.setDouble(index++, bInfo.getMinterest());
			ps.setLong(index++, bInfo.getID());
			lResult = ps.executeUpdate();
			ps.close();
			ps = null;
			conn.close();
			conn = null;

			if (lResult < 0) {
				Log.print(" update loan LOAN_BATCHREPAYFORM error : "
						+ lResult);
				return -1;
			} else {
				return bInfo.getID();
			}
		} catch (Exception e) {
			throw new IException("Gen_E001");
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
				throw new IException("Gen_E001");
			}
		}
		
	}
	
	/**
	 * 取消批量还款单
	 * @author 马现福 2008-11-26
	 * @param bInfo
	 * @return
	 * @throws IException
	 */
	public long cancelBatchRepayNotice(BatchRepayNoticeInfo bInfo)throws IException{
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer strSQL = new StringBuffer();
		StringBuffer strRpSQL = new StringBuffer();
		long lResult = -1;

		try {
			conn = Database.getConnection();
			//取消批量还款单
			strSQL.append(" update LOAN_BATCHREPAYFORM ");
			strSQL.append(" set nStatusID = "+LOANConstant.AheadRepayStatus.CANCEL); //置为已取消状态
			strSQL.append(" WHERE id = "+bInfo.getID()); //批量还款通知单ID
			log4j.info(strSQL.toString());
			ps = conn.prepareStatement(strSQL.toString());
			long lResult1 = ps.executeUpdate();
			//取消批量还款单下的所有还款单
			strRpSQL.append(" update loan_aheadrepayform ");
			strRpSQL.append(" set nStatusID = "+LOANConstant.AheadRepayStatus.CANCEL); //置为已取消状态
			strRpSQL.append(" WHERE batchid = "+bInfo.getID()); //批量还款通知单ID
			log4j.info(strRpSQL.toString());
			ps = conn.prepareStatement(strRpSQL.toString());
			long lResult2 = ps.executeUpdate();
			ps.close();
			ps = null;
			conn.close();
			conn = null;

			if (lResult1 < 0 ||lResult2 < 0) {
				Log.print(" update loan LOAN_BATCHREPAYFORM error : "
						+ lResult1+","+ lResult2);
			} else {
				lResult = bInfo.getID();
			}
		} catch (Exception e) {
			throw new IException("Gen_E001");
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
				throw new IException("Gen_E001");
			}
		}
		return lResult;
	}
	
	/**
	* 得到批量还款通知单详细信息
	* Create Date: 2008-10-7
	* @param lID 批量还款通知单ID
	* @return BatchRepayNoticeInfo 提前还款通知单详细信息
	* @exception Exception
	*/
	public BatchRepayNoticeInfo findBatchAheadRepayByID(long lID) throws Exception
	{		BatchRepayNoticeInfo info = new BatchRepayNoticeInfo();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT a.*,");
			sbSQL.append(" b.id as ContractID,b.sContractCode,");
			sbSQL.append(" b.nIntervalNum,b.mExamineAmount,b.mAdjustRate,b.ntypeid,b.nsubtypeid,");
		    //sbSQL.append(" c.sCode as PayCode,c.mInterestRate,");
			//sbSQL.append(" c.mAmount as PayAmount,");
			sbSQL.append(" d.sName as clientName,e.sName as userName");          
			sbSQL.append(" FROM loan_batchrepayform a,loan_ContractForm b,");
			sbSQL.append(" Client d,userInfo e");
                       
			sbSQL.append(" WHERE b.id = a.nContractID");
			sbSQL.append(" AND b.nBorrowClientID=d.ID(+)");
			sbSQL.append(" AND a.nInputUserID = e.id(+)");
			sbSQL.append(" AND a.id = ?");

            sbSQL.append(" order by a.id desc ");//2004-01-15 ninh
            
			System.out.print(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lID);
             
			rs = ps.executeQuery();
			
			ContractDao dao = new ContractDao();
			LoanPayNoticeDao payDao = new LoanPayNoticeDao();

			if (rs.next())
			{
				
				info.setID(rs.getLong("id"));
				info.setCode(rs.getString("sCode")); //通知单编号
				info.setClientName(rs.getString("clientName")); //借款单位
				info.setContractCode(rs.getString("sContractCode")); //合同编号
				info.setContractID(rs.getLong("ContractID")); //合同ID
				info.setIntervalNum(rs.getLong("nIntervalNum")); //贷款期限
				info.setContractAmount(rs.getDouble("mExamineAmount")); //合同金额
				info.setLoanType(rs.getLong("ntypeid"));
				info.setLoanSubType(rs.getLong("nsubtypeid"));
				info.setPBackDate(rs.getTimestamp("payDate"));
			//	info.setBalanceAmount(rs.getDouble("interestAmount"));
               
                
				ContractAmountInfo cInfo = dao.getLateAmount(info.getContractID());
				info.setContractBalance(cInfo.getBalanceAmount()); //合同余额
			//	info.setLetoutNoticeCode(rs.getString("PayCode")); //放款通知单编号
				
			//	info.setLetoutNoticeRate(payDao.getLatelyRate(rs.getLong("PayID"),null));//放款通知单执行利率
				
				//放款通知单利率,提前还款利率
			//	info.setLetoutNoticeAmount(rs.getDouble("PayAmount")); //放款通知单金额
			//	info.setLetoutNoticeID(rs.getLong("PayID")); //放款通知单ID
			//	LoanPayNoticeInfo pinfo = payDao.findLoanPayNoticeByID(rs.getLong("PayID"));
			//	info.setLetoutNoticeBalance(pinfo.getBalance()); //放款通知单余额
				
				info.setAmount(rs.getDouble("mAmount")); //提前还款金额
				info.setInputUserID(rs.getLong("nInputUserID")); //录入人ID
				info.setInputUserName(rs.getString("userName")); //录入人
				info.setInputDate(rs.getTimestamp("dtInputDate")); //录入时间
				info.setIsAhead(rs.getLong("nIsAhead"));//是否提前还款
				info.setStatusID(rs.getLong("nStatusID")); //状态
				info.setStatus(LOANConstant.AheadRepayStatus.getName(info.getStatusID())); 
				info.setNisrepayinterest(rs.getLong("nisrepayinterest"));//是否归还利息
				info.setMinterest(rs.getDouble("minterest")); //批量还款总利息
				//状态 

			}

			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
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
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return info;

	}
	/**
	 * 查找批量还款单下的所有放款通知单
	 * @param 
	 */
	public Collection querySaveForRepayNotice(BatchRepayNoticeInfo qInfo) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Timestamp tsDate = null;
		LoanPayNoticeDao dao = new LoanPayNoticeDao();
		Vector v = new Vector();
		try
		{
			con = Database.getConnection();
			tsDate = DataFormat.getDateTime(con);
			StringBuffer sb = new StringBuffer();
			sb.append(" select a.*,b.ID nContractID,b.sContractCode,b.NOFFICEID, ");
			sb.append(" round(c.mRate,6) as mrate,");
			sb.append(" d.sname as sInputName,e.saccountno as sGrantCurrentAccount,e.sname as sGrantName,");
			sb.append(" f.sname as sBorrowClientName,");
			sb.append(" b.mloanamount,b.NINTERVALNUM,g.mrate as mContractRate,b.SLOANPURPOSE,");
			sb.append(" f.SZIPCODE,f.sPhone,f.sAddress,b.ntypeid,h.sname as sConsignClientname, ");
			sb.append(" i.mInterest as subInterest,i.mBalance as subBalance,j.sname as accname,j.sCode as acccode");
			sb.append(" from loan_payform a,loan_contractform b,loan_interestrate c,userinfo d,");
			sb.append("  sett_account e,client f,loan_interestrate g,client h ");
			sb.append("  ,sett_subaccount i ,sett_Branch j");
			sb.append("  where a.ncontractid = b.id(+) ");
			sb.append("  and a.nBankInterestID = c.id(+) ");
			sb.append("  and a.ninputuserid = d.id(+) ");
			sb.append("  and a.Ngrantcurrentaccountid = e.id(+) ");
			sb.append("  and b.nborrowclientid = f.id(+) ");
			sb.append("  and b.NBANKINTERESTID = g.id(+) ");
			sb.append("  and b.nconsignclientid = h.id(+) ");
			sb.append("  and i.AL_nLoanNoteID(+) = a.ID and i.nStatusID(+) = ? ");
			sb.append("  and a.nstatusid = "+LOANConstant.LoanPayNoticeStatus.USED);
			sb.append("  and a.ncontractid = ? and a.nAccountBankID = j.id(+)");
			//update by xfma 2008-11-25 按放款单的编号排序
			sb.append("  order by a.SCODE");
			ps = con.prepareStatement(sb.toString());
			ps.setLong(1, Constant.RecordStatus.VALID);
			ps.setLong(2, qInfo.getContractID());
			System.out.println(sb.toString());
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				LoanPayNoticeInfo info = new LoanPayNoticeInfo();
				info.setID(rs.getLong("ID"));
				info.setDrawNoticeID(rs.getLong("NDRAWNOTICEID"));
				info.setLoanClientName(rs.getString("sBorrowClientName"));
				info.setLoanAmount(rs.getDouble("mLoanAmount"));
				info.setIntervalNum(rs.getLong("NINTERVALNUM"));
				//已发放金额info.set
				info.setContractRate(rs.getDouble("mContractRate"));
				info.setLoanPurpose(rs.getString("sLoanPurpose"));
				info.setOutDate(rs.getTimestamp("dtoutdate"));
				info.setCode(rs.getString("sCode"));
				info.setLoanZipCode(rs.getString("sZipCode"));
				info.setLoanPhone(rs.getString("sPhone"));
				info.setLoanAddress(rs.getString("sAddress"));
				info.setLoanTypeID(rs.getLong("nTypeID"));
				info.setGrantTypeID(rs.getLong("nGrantTypeID"));
				info.setGrantCurrentAccount(rs.getString("sGrantCurrentAccount"));
				info.setGrantCurrentName(rs.getString("sGrantName"));
				info.setAmount(rs.getDouble("mAmount"));
				info.setConsignClientName(rs.getString("sConsignClientName"));
				info.setConsignAccount(rs.getString("sConsignAccount"));
				//info.setInterestRate(rs.getDouble("mRate"));
				info.setCommissionRate(rs.getDouble("Mcommissionrate"));
				info.setSuretyFeeRate(rs.getDouble("mSuretyFeeRate"));
				info.setStart(rs.getTimestamp("dtStart"));
				info.setInputDate(rs.getTimestamp("DTINPUTDATE"));
				info.setEnd(rs.getTimestamp("dtEnd"));
				info.setCompanyLeader(rs.getString("sCompanyLeader"));
				info.setDepartmentLeader(rs.getString("sDepartmentLeader"));
				info.setCheckPerson(rs.getString("sCheckPerson"));
				info.setHandlingPerson(rs.getString("sHandlingPerson"));
				info.setInterest(rs.getDouble("subInterest"));
				info.setBalance(rs.getDouble("subBalance"));
				info.setAccountBankID(rs.getLong("nAccountBankID"));
				info.setAccountBankCode(rs.getString("acccode"));
				info.setAccountBankName(rs.getString("accname"));
				info.setGrantCurrentAccountID(rs.getLong("NGRANTCURRENTACCOUNTID"));
				info.setReceiveClientCode(rs.getString("sReceiveAccount"));
				info.setReceiveClientName(rs.getString("sReceiveClientName"));
				info.setRemitinProvince(rs.getString("sRemitinProvince"));
				info.setRemitinCity(rs.getString("sRemitinCity"));
				info.setRemitBank(rs.getString("sRemitBank"));
				info.setBankInterestID(rs.getLong("nBankInterestID"));
				info.setLoanAccount(rs.getString("sloanaccount"));
				info.setInterestRate(dao.getLatelyRate(info.getID(), null));
				info.setStatusID(rs.getLong("nStatusID"));
				info.setInputUserID(rs.getLong("nInputUserID"));
				info.setInputUserName(rs.getString("sInputName"));
				info.setWTInterestRate(rs.getDouble("minterestrate"));
				info.setReceiveAccount(rs.getString("sReceiveAccount"));
				//System.out.println("%%%%%%%% info.getOutDate()="+info.getOutDate());
				info.setOfficeID(rs.getLong("NOFFICEID"));
				info.setMbalance(rs.getDouble("subBalance"));
				info.setNextCheckLevel(rs.getLong("nNextCheckLevel"));
				info.setPreAdjustRate(rs.getDouble("MADJUSTRATE"));
				info.setPreStaidAdjustRate(rs.getDouble("MSTAIDADJUSTRATE"));
				info.setPreBasicInterestRate(rs.getDouble("mRate"));
				info.setInterestTypeID(rs.getLong("nInterestTypeID"));
				info.setLiborRateID(rs.getLong("nLiborRateID"));
				info.setIntervalNoticeNum(rs.getDouble("nIntervalNoticeNum"));
				v.addElement(info);
			}			
			rs.close();
			rs = null;
			ps.close();
			ps = null;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new Exception("remote exception : " + e.toString());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return v;
	}
	/**
	* 得到还款通知单详细信息
	* Create Date: 2008-10-7
	* @param lID 放款通知单ID
	* @return AheadRepayNoticeInfo 提前还款通知单详细信息
	* @exception Exception
	*/
	public AheadRepayNoticeInfo findAheaeInfoByPayID(LoanPayNoticeInfo lInfo) throws Exception
	{		
		AheadRepayNoticeInfo info = new AheadRepayNoticeInfo();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT a.* ");        
			sbSQL.append(" FROM loan_aheadrepayform a,loan_payform b,loan_batchrepayform c ");
			sbSQL.append(" WHERE a.nloanpaynoticeid = b.id ");
			sbSQL.append(" and a.batchid = c.id ");
			sbSQL.append(" and b.id = ?");
			sbSQL.append(" and c.id = ?");
			sbSQL.append(" and a.nstatusid not in( "+LOANConstant.AheadRepayStatus.CANCEL+")");
			sbSQL.append(" and a.batchid is not null ");
            sbSQL.append(" order by a.id desc ");            
			System.out.print(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lInfo.getID());  
			ps.setLong(2, lInfo.getActionID());
			rs = ps.executeQuery();
			if (rs.next())
			{				
				info.setID(rs.getLong("id"));
				info.setCode(rs.getString("SCode")); 
				info.setPBackDate(rs.getTimestamp("payDate"));  //还款日期 		
				info.setAmount(rs.getDouble("mAmount")); //提前还款金额
				info.setIsAhead(rs.getLong("nIsAhead"));//是否提前还款
				info.setStatusID(rs.getLong("nStatusID")); //状态
				info.setStatus(LOANConstant.AheadRepayStatus.getName(info.getStatusID())); 
				info.setBalanceAmount(rs.getDouble("interestamount")); //利息  2010,6,9 fhx
				
				//状态 
			}

			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
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
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return info;

	}
	/**
	 * 查找批量还款单下的所有放款通知单
	 * @param 
	 */
	public Collection findAheadInfoByBatchID(long id) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector v = new Vector();
		try
		{
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append(" select a.* from loan_aheadrepayform a where a.batchid = ? ");
			sb.append(" and a.nstatusid not in(  "+LOANConstant.AheadRepayStatus.CANCEL+")" );		
			System.out.println(sb.toString());
			ps = con.prepareStatement(sb.toString());
			ps.setLong(1,id);   
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				AheadRepayNoticeInfo info = new AheadRepayNoticeInfo();
				info.setID(rs.getLong("ID"));			
				v.addElement(info);
			}			
			rs.close();
			rs = null;
			ps.close();
			ps = null;
		}
		catch (Exception e)
		{
 			log4j.error(e.toString());
			throw new Exception("remote exception : " + e.toString());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return v;
	}
	/**
	 * 查找批量还款单下的所有放款通知单
	 * @param 
	 */
	public Collection queryAllRepayNotice(BatchRepayNoticeInfo qInfo) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Timestamp tsDate = null;
		LoanPayNoticeDao dao = new LoanPayNoticeDao();
		Vector v = new Vector();
		try
		{
			con = Database.getConnection();
			tsDate = DataFormat.getDateTime(con);
			StringBuffer sb = new StringBuffer();
			sb.append(" select a.*,b.ID nContractID,b.sContractCode,b.NOFFICEID, ");
			sb.append(" round(c.mRate,6) as mrate,");
			sb.append(" d.sname as sInputName,e.saccountno as sGrantCurrentAccount,e.sname as sGrantName,");
			sb.append(" f.sname as sBorrowClientName,");
			sb.append(" b.mloanamount,b.NINTERVALNUM,g.mrate as mContractRate,b.SLOANPURPOSE,");
			sb.append(" f.SZIPCODE,f.sPhone,f.sAddress,b.ntypeid,h.sname as sConsignClientname, ");
			sb.append(" i.mInterest as subInterest,i.mBalance as subBalance,j.sname as accname,j.sCode as acccode");
			sb.append(" from loan_payform a,loan_contractform b,loan_interestrate c,userinfo d,");
			sb.append("  sett_account e,client f,loan_interestrate g,client h ");
			sb.append("  ,sett_subaccount i ,sett_Branch j");
			sb.append("  where a.ncontractid = b.id(+) ");
			sb.append("  and a.nBankInterestID = c.id(+) ");
			sb.append("  and a.ninputuserid = d.id(+) ");
			sb.append("  and a.Ngrantcurrentaccountid = e.id(+) ");
			sb.append("  and b.nborrowclientid = f.id(+) ");
			sb.append("  and b.NBANKINTERESTID = g.id(+) ");
			sb.append("  and b.nconsignclientid = h.id(+) ");
			sb.append("  and i.AL_nLoanNoteID(+) = a.ID and i.nStatusID(+) = ? ");
			sb.append("  and a.nstatusid = "+LOANConstant.LoanPayNoticeStatus.USED);
			sb.append("  and a.ncontractid = ? and a.nAccountBankID = j.id(+)");
			//update by xfma 2008-11-25 按放款单的编号排序
			sb.append("  order by a.SCODE");
			ps = con.prepareStatement(sb.toString());
			ps.setLong(1, Constant.RecordStatus.VALID);
			ps.setLong(2, qInfo.getContractID());
			System.out.println(sb.toString());
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				LoanPayNoticeInfo info = new LoanPayNoticeInfo();
				info.setID(rs.getLong("ID"));
				info.setDrawNoticeID(rs.getLong("NDRAWNOTICEID"));
				info.setLoanClientName(rs.getString("sBorrowClientName"));
				info.setLoanAmount(rs.getDouble("mLoanAmount"));
				info.setIntervalNum(rs.getLong("NINTERVALNUM"));
				//已发放金额info.set
				info.setContractRate(rs.getDouble("mContractRate"));
				info.setLoanPurpose(rs.getString("sLoanPurpose"));
				info.setOutDate(rs.getTimestamp("dtoutdate"));
				info.setCode(rs.getString("sCode"));
				info.setLoanZipCode(rs.getString("sZipCode"));
				info.setLoanPhone(rs.getString("sPhone"));
				info.setLoanAddress(rs.getString("sAddress"));
				info.setLoanTypeID(rs.getLong("nTypeID"));
				info.setGrantTypeID(rs.getLong("nGrantTypeID"));
				info.setGrantCurrentAccount(rs.getString("sGrantCurrentAccount"));
				info.setGrantCurrentName(rs.getString("sGrantName"));
				info.setAmount(rs.getDouble("mAmount"));
				info.setConsignClientName(rs.getString("sConsignClientName"));
				info.setConsignAccount(rs.getString("sConsignAccount"));
				//info.setInterestRate(rs.getDouble("mRate"));
				info.setCommissionRate(rs.getDouble("Mcommissionrate"));
				info.setSuretyFeeRate(rs.getDouble("mSuretyFeeRate"));
				info.setStart(rs.getTimestamp("dtStart"));
				info.setInputDate(rs.getTimestamp("DTINPUTDATE"));
				info.setEnd(rs.getTimestamp("dtEnd"));
				info.setCompanyLeader(rs.getString("sCompanyLeader"));
				info.setDepartmentLeader(rs.getString("sDepartmentLeader"));
				info.setCheckPerson(rs.getString("sCheckPerson"));
				info.setHandlingPerson(rs.getString("sHandlingPerson"));
				info.setInterest(rs.getDouble("subInterest"));
				info.setBalance(rs.getDouble("subBalance"));
				info.setAccountBankID(rs.getLong("nAccountBankID"));
				info.setAccountBankCode(rs.getString("acccode"));
				info.setAccountBankName(rs.getString("accname"));
				info.setGrantCurrentAccountID(rs.getLong("NGRANTCURRENTACCOUNTID"));
				info.setReceiveClientCode(rs.getString("sReceiveAccount"));
				info.setReceiveClientName(rs.getString("sReceiveClientName"));
				info.setRemitinProvince(rs.getString("sRemitinProvince"));
				info.setRemitinCity(rs.getString("sRemitinCity"));
				info.setRemitBank(rs.getString("sRemitBank"));
				info.setBankInterestID(rs.getLong("nBankInterestID"));
				info.setLoanAccount(rs.getString("sloanaccount"));
				info.setInterestRate(dao.getLatelyRate(info.getID(), null));
				info.setStatusID(rs.getLong("nStatusID"));
				info.setInputUserID(rs.getLong("nInputUserID"));
				info.setInputUserName(rs.getString("sInputName"));
				info.setWTInterestRate(rs.getDouble("minterestrate"));
				info.setReceiveAccount(rs.getString("sReceiveAccount"));
				//System.out.println("%%%%%%%% info.getOutDate()="+info.getOutDate());
				info.setOfficeID(rs.getLong("NOFFICEID"));
				info.setMbalance(rs.getDouble("subBalance"));
				info.setNextCheckLevel(rs.getLong("nNextCheckLevel"));
				info.setPreAdjustRate(rs.getDouble("MADJUSTRATE"));
				info.setPreStaidAdjustRate(rs.getDouble("MSTAIDADJUSTRATE"));
				info.setPreBasicInterestRate(rs.getDouble("mRate"));
				info.setInterestTypeID(rs.getLong("nInterestTypeID"));
				info.setLiborRateID(rs.getLong("nLiborRateID"));
				info.setIntervalNoticeNum(rs.getDouble("nIntervalNoticeNum"));
				v.addElement(info);
			}			
			rs.close();
			rs = null;
			ps.close();
			ps = null;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new Exception("remote exception : " + e.toString());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return v;
	}
	/**
	 * 查找批量还款单下的所有放款通知单
	 * @param 
	 */
	public Collection queryDoApprovalRepayNotice(BatchRepayNoticeInfo qInfo) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Timestamp tsDate = null;
		LoanPayNoticeDao dao = new LoanPayNoticeDao();
		Vector v = new Vector();
		try
		{
			con = Database.getConnection();
			tsDate = DataFormat.getDateTime(con);
			StringBuffer sb = new StringBuffer();
			sb.append(" select a.*,m.BATCHID, ");
			sb.append(" round(a.minterestrate,6) as mrate ,i.mBalance as subBalance ");
			sb.append(" from loan_payform a,loan_aheadrepayform m,loan_batchrepayform n,");
			sb.append(" sett_account e, ");
			sb.append(" sett_subaccount i ");
			sb.append("  where  m.batchid = n.id ");
			sb.append("  and m.ncontractid = n.ncontractid ");
			sb.append("  and m.nloanpaynoticeid = a.id ");
			sb.append("  and n.ncontractid = a.ncontractid ");
			sb.append("  and a.Ngrantcurrentaccountid = e.id(+) ");
			sb.append("  and i.AL_nLoanNoteID(+) = a.ID and i.nStatusID(+) = ? ");
			sb.append("  and a.nstatusid = "+LOANConstant.LoanPayNoticeStatus.USED);
			sb.append("  and a.ncontractid = ? and  n.id = ?");
			sb.append("  and m.nStatusID != "+LOANConstant.AheadRepayStatus.CANCEL);
			sb.append("  and n.nStatusID != "+LOANConstant.AheadRepayStatus.CANCEL);
			//update by xfma 2008-11-26 按放款单的编号排序
			sb.append("  order by a.SCODE");
			ps = con.prepareStatement(sb.toString());
			ps.setLong(1, Constant.RecordStatus.VALID);
			ps.setLong(2, qInfo.getContractID());
			ps.setLong(3, qInfo.getID());
			System.out.println(sb.toString());
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				LoanPayNoticeInfo info = new LoanPayNoticeInfo();
				info.setID(rs.getLong("ID"));
				info.setCode(rs.getString("sCode"));
				info.setInterestRate(rs.getDouble("mrate"));
				info.setAmount(rs.getDouble("mAmount"));
				info.setBalance(rs.getDouble("subBalance"));
				info.setActionID(rs.getLong("batchID"));
				v.addElement(info);
			}			
			rs.close();
			rs = null;
			ps.close();
			ps = null;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new Exception("remote exception : " + e.toString());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return v;
	}
	
	/**
	 * 查找批量还款单下的所有已使用还款通知单
	 * @author 马现福 2008-11-16
	 * @param BatchRepayNoticeInfo
	 */
	public Collection queryUsedRepayNotice(BatchRepayNoticeInfo qInfo) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector v = new Vector();
		try
		{
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append(" select * from loan_aheadrepayform m ");
			sb.append("  where m.batchid = ? ");
			sb.append("  and m.nstatusid = "+LOANConstant.AheadRepayStatus.USED);
			sb.append("  order by m.sCode");
			ps = con.prepareStatement(sb.toString());
			ps.setLong(1, qInfo.getID());
			System.out.println(sb.toString());
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				AheadRepayNoticeInfo info = new AheadRepayNoticeInfo();
				info.setID(rs.getLong("ID"));
				info.setCode(rs.getString("sCode"));
				info.setLetoutNoticeID(rs.getLong("NLOANPAYNOTICEID"));
				info.setContractID(rs.getLong("NCONTRACTID"));
				info.setAmount(rs.getDouble("mAmount"));
				info.setBatchID(rs.getLong("batchID"));
				v.addElement(info);
			}			
			rs.close();
			rs = null;
			ps.close();
			ps = null;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new Exception("remote exception : " + e.toString());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return v;
	}
}
