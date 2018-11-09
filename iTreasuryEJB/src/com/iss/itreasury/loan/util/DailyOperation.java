package com.iss.itreasury.loan.util;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.settlement.util.*;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.loan.credit.bizlogic.*;

/**
 * @author yzhang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class DailyOperation
{
	/*
		    */
	public boolean dealContractForShutDown(Connection conn, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		PreparedStatement ps = null;
		PreparedStatement pstemp = null;
		ResultSet rs = null;
		boolean bTransCheck = true;
		long lContractFinishResultCount = 0;
		long lDiscountFinishResultCount = 0;
		long lAssureFinishResultCount = 0;
		Timestamp tsToday = null;
		String strSQL = null;
		java.util.Calendar calendar = Calendar.getInstance();
		//modify by xwhe 2009-06-02 
		boolean isfinish_WT_contract = Config.getBoolean(ConfigConstant.SETTLEMENT_FINISH_WT_CONTRACT, false);
		try
		{
			System.out.println("");
			System.out.println("信贷关机**********处理执行中合同***************");
			System.out.println("");
			//一般贷款合同
			strSQL = " select nLoanContractID from sett_TransGrantLoan where dtExecute = ? and nStatusID in (3,4,5,6) and nOfficeID=" + lOfficeID + " and nCurrencyID =  " + lCurrencyID;
			strSQL = strSQL + " union all ";
			//贴现合同
			strSQL = strSQL + " select nDiscountContractID from sett_TransGrantDiscount where dtExecute = ? and nStatusID in (3,4,5,6) and nOfficeID=" + lOfficeID + " and nCurrencyID =  " + lCurrencyID;
			strSQL = strSQL + " union all ";
			//担保合同
			//strSQL = strSQL + " select ContractID from loan_AssureChargeForm where ExecuteDate = ? and StatusID in (3,4) and OfficeID=" + lOfficeID + " and CurrencyID =  " + lCurrencyID;
			strSQL = strSQL + " select ContractID from loan_AssureChargeForm where StatusID =4 and OfficeID=" + lOfficeID + " and CurrencyID =  " + lCurrencyID;
			strSQL = strSQL + " union all ";
			//融资租赁合同
			strSQL = strSQL + " select ncontractid from SETT_TransReceiveFinance where dtExecute = ? and nStatusID in (3,4,5,6) and nOfficeID=" + lOfficeID + " and nCurrencyID =  " + lCurrencyID;
			ps = conn.prepareStatement(strSQL);
			ps.setTimestamp(1, tsDate);
			ps.setTimestamp(2, tsDate);
			ps.setTimestamp(3, tsDate);
			//ps.setTimestamp(4, tsDate);
			rs = ps.executeQuery();
			while (rs.next())
			{
				strSQL = "update loan_contractform set nStatusID = " + LOANConstant.ContractStatus.ACTIVE + " where nStatusID = " + LOANConstant.ContractStatus.NOTACTIVE + " and ID = " + rs.getLong(1);
				pstemp = conn.prepareStatement(strSQL);
				pstemp.executeUpdate();
				pstemp.close();
				pstemp = null;
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			//待补充
			System.out.println("");
			System.out.println("信贷关机**********处理结束合同：贷款合同)***************");
			System.out.println("");
			//calendar.setTime(Env.getSystemDate());
			calendar.setTime(tsDate);
			tsToday = DataFormat.getDateTime(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE), 0, 0, 0);
			strSQL = "";
			strSQL =
				" Select aa.ID,sContractCode,aa.niscircle,aa.dtEndDate, aa.nTypeID, nvl(aa.mExamineAmount,0) mAmount, "
					+ " nvl(bb.mBalance,0) mBalance,nvl(bb.mWTbalance, 0) mWTbalance, nvl(bb.mRepayment,0) mRepayment "
					+ " From "
					+ " ( "
					+ "   Select a.ID,a.sContractCode,a.niscircle,dtEndDate,nTypeID,nvl(mExamineAmount,0) mExamineAmount "
					+ "   From loan_contractform a "
					+ "   Where ( a.nStatusID = ? Or a.nStatusID = ? Or a.nStatusID = ? Or a.nStatusID = ? Or a.nStatusID = ? ) "
					+ "   And nOfficeID = "
					+ lOfficeID
					+ "   And nCurrencyID = "
					+ lCurrencyID
					+ "   And nTypeID != "
					+ LOANConstant.LoanType.TX
					+ " ) aa, "
					+ " ( "
					+ "  select b.nContractID,sum(nvl(a.mOpenAmount,0)-nvl(a.mBalance,0)) mRepayment,sum(nvl(a.mBalance,0) + nvl(a.mInterest,0) + nvl(a.AL_mSuretyFee,0) + nvl(a.AL_mCommission,0) + nvl(a.AL_mOverDueInterest,0) + nvl(a.AL_mCompoundInterest,0) ) mBalance,sum(nvl(a.mBalance, 0) + nvl(a.mInterest, 0)+ nvl(a.AL_mOverDueInterest,0) + nvl(a.AL_mCompoundInterest,0)) mWTbalance  "
					+ "    from sett_subAccount a,loan_payform b,sett_Account c,sett_accountType d "
					+ "    where  AL_nLoanNoteID = b.ID and a.nAccountID = c.ID and c.nAccountTypeID = d.id and d.nAccountGroupID != "
					+ SETTConstant.AccountGroupType.DISCOUNT
					+ "    and a.nStatusID in ( "
					+ SETTConstant.SubAccountStatus.NORMAL
					+ " , " 
					+ SETTConstant.SubAccountStatus.FINISH
					+ ") "
					+ "    group by nContractID "
					+ " ) bb "
					+ " Where aa.ID = bb.nContractID ";
			System.out.println(strSQL);
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, LOANConstant.ContractStatus.ACTIVE);
			ps.setLong(2, LOANConstant.ContractStatus.EXTEND);
			ps.setLong(3, LOANConstant.ContractStatus.OVERDUE);
			ps.setLong(4, LOANConstant.ContractStatus.DELAYDEBT);
			ps.setLong(5, LOANConstant.ContractStatus.BADDEBT);
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				long lContractID = rs.getLong("ID");
				Timestamp dtEndDate = rs.getTimestamp("dtEndDate");
				double mBalance = rs.getDouble("mBalance");
				double mWTbalance = rs.getDouble("mWTbalance");
				double mRepayment = rs.getDouble("mRepayment");
				double mAmount = rs.getDouble("mAmount");
				 long  nTypeID = rs.getLong("nTypeID");
				long nIsCircle = rs.getLong("niscircle");
				System.out.println("信贷关机&&&&&&&&&&&tsToday=" + tsToday);
				System.out.println("信贷关机&&&&&&&&&&&dtEndDate=" + dtEndDate);
				System.out.println("信贷关机&&&&&&&&&&&mBalance=" + mBalance);
				System.out.println("信贷关机&&&&&&&&&&&mRepayment=" + mRepayment);
				System.out.println("信贷关机&&&&&&&&&&&mAmount=" + mAmount);
				System.out.println("信贷关机&&&&&&&&&&&nTypeID=" + nTypeID);
				System.out.println("信贷关机&&&&&&&&&&&nIsCircle=" + nIsCircle);
				System.out.println("信贷关机&&&&&&&&&&&lContractID=" + lContractID);
				System.out.println("信贷关机&&&&&&&&&&&sContractCode=" + rs.getString("sContractCode"));
				if (nTypeID == LOANConstant.LoanType.ZGXE)
				{
					//已经到期，并且余额为零
					System.out.println("信贷关机**************************");
					if (dtEndDate != null && (DataFormat.getDateString(dtEndDate).compareTo(DataFormat.getDateString(tsToday)) <= 0))
					{
						System.out.println("信贷关机***************已经到期*****" + rs.getString("sContractCode"));
						if (mBalance < 0.01)
						{
							System.out.println("信贷关机***************并且余额为零*****" + rs.getString("sContractCode"));
							strSQL = "update loan_contractform set nStatusID = ? where ID = ? ";
							pstemp = conn.prepareStatement(strSQL);
							pstemp.setLong(1, LOANConstant.ContractStatus.FINISH);
							pstemp.setLong(2, lContractID);
							pstemp.executeUpdate();
							pstemp.close();
							pstemp = null;
							lContractFinishResultCount++;
						}
					}
				}
				else if (nTypeID == LOANConstant.LoanType.WT && isfinish_WT_contract)
				{
					System.out.println("信贷关机===========================");
					//已经到期，并且余额为零
					if (dtEndDate != null && (DataFormat.getDateString(dtEndDate).compareTo(DataFormat.getDateString(tsToday)) <= 0))
					{
						System.out.println("信贷关机==============已经到期============" + rs.getString("sContractCode"));
						if (mWTbalance <0.01)
						{
							System.out.println("信贷关机==============并且余额为零============" + rs.getString("sContractCode"));
							strSQL = "update loan_contractform set nStatusID = ? where ID = ? ";
							pstemp = conn.prepareStatement(strSQL);
							pstemp.setLong(1, LOANConstant.ContractStatus.FINISH);
							pstemp.setLong(2, lContractID);
							pstemp.executeUpdate();
							pstemp.close();
							pstemp = null;
							lContractFinishResultCount++;
						}
					}
					//无论是否到期，收回金额等于合同金额
					if (mRepayment == mAmount && mWTbalance < 0.01 && nIsCircle != 1)
					{
						
							System.out.println("信贷关机==============无论是否到期，收回金额等于合同金额============" + rs.getString("sContractCode"));
							strSQL = "update loan_contractform set nStatusID = ? where ID = ? ";
							pstemp = conn.prepareStatement(strSQL);
							pstemp.setLong(1, LOANConstant.ContractStatus.FINISH);
							pstemp.setLong(2, lContractID);
							pstemp.executeUpdate();
							pstemp.close();
							pstemp = null;
							lContractFinishResultCount++;
						
					}
				}
				else
				{
					System.out.println("信贷关机===========================");
					//已经到期，并且余额为零
					if (dtEndDate != null && (DataFormat.getDateString(dtEndDate).compareTo(DataFormat.getDateString(tsToday)) <= 0))
					{
						System.out.println("信贷关机==============已经到期============" + rs.getString("sContractCode"));
						if (mBalance <0.01)
						{
							System.out.println("信贷关机==============并且余额为零============" + rs.getString("sContractCode"));
							strSQL = "update loan_contractform set nStatusID = ? where ID = ? ";
							pstemp = conn.prepareStatement(strSQL);
							pstemp.setLong(1, LOANConstant.ContractStatus.FINISH);
							pstemp.setLong(2, lContractID);
							pstemp.executeUpdate();
							pstemp.close();
							pstemp = null;
							lContractFinishResultCount++;
						}
					}
					//无论是否到期，收回金额等于合同金额
					if (mRepayment == mAmount && mBalance < 0.01 && nIsCircle != 1)
					{
						
							System.out.println("信贷关机==============无论是否到期，收回金额等于合同金额============" + rs.getString("sContractCode"));
							strSQL = "update loan_contractform set nStatusID = ? where ID = ? ";
							pstemp = conn.prepareStatement(strSQL);
							pstemp.setLong(1, LOANConstant.ContractStatus.FINISH);
							pstemp.setLong(2, lContractID);
							pstemp.executeUpdate();
							pstemp.close();
							pstemp = null;
							lContractFinishResultCount++;
						
					}
				}
			}
			System.out.println(DataFormat.getDateString(tsToday) + " 结束的贷款合同数：" + lContractFinishResultCount);
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			System.out.println("");
			System.out.println("信贷关机**********处理结束合同：贴现合同)***************");
			System.out.println("");
			strSQL = "";
			strSQL =
				" Select aa.ID, aa.dtEndDate, aa.nIsCircle, nvl(aa.mExamineAmount,0) mAmount, "
					+ " nvl(bb.mBalance,0) mBalance, nvl(bb.mRepayment,0) mRepayment "
					+ " From "
					+ " ( "
					+ "   Select a.ID,dtEndDate,nIsCircle,nvl(mExamineAmount,0) mExamineAmount "
					+ "   From loan_contractform a "
					+ "   Where ( a.nStatusID = ?  )   "
					+ "   And a.nOfficeID = "
					+ lOfficeID
					+ "   And a.nCurrencyID = "
					+ lCurrencyID
					+ "   And a.nTypeID = "
					+ LOANConstant.LoanType.TX
					+ " ) aa, "
					+ " ( "
					+ "    select b.nContractID,sum(nvl(a.mOpenAmount,0)-nvl(a.mBalance,0)) mRepayment,sum(nvl(a.mBalance,0)) mBalance "
					+ "    from sett_subAccount a,loan_DiscountCredence b,sett_Account c ,sett_accountType d"
					+ "    where  AL_nLoanNoteID = b.ID and a.nAccountID = c.ID and c.nAccountTypeID = d.id and d.nAccountGroupID = "
					+ SETTConstant.AccountGroupType.DISCOUNT
					+ "    and a.nStatusID in ( "
					+ SETTConstant.SubAccountStatus.NORMAL
					+ " , " 
					+ SETTConstant.SubAccountStatus.FINISH
					+ " ) "
					+ "    group by nContractID "
					+ " ) bb "
					+ " Where aa.ID = bb.nContractID ";
			System.out.println(strSQL);
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, LOANConstant.ContractStatus.ACTIVE);
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				long lContractID = rs.getLong("ID");
				Timestamp dtEndDate = rs.getTimestamp("dtEndDate");
				double mBalance = rs.getDouble("mBalance");
				double mRepayment = rs.getDouble("mRepayment");
				double mAmount = rs.getDouble("mAmount");
				long nIsCircle = rs.getLong("nIsCircle");
				//已经到期，并且余额为零
				if (dtEndDate != null && (DataFormat.getDateString(dtEndDate).compareTo(DataFormat.getDateString(tsToday)) <= 0))
				{
					if (mBalance< 0.01)
					{
						strSQL = "update loan_contractform set nStatusID = ? where ID = ? ";
						pstemp = conn.prepareStatement(strSQL);
						pstemp.setLong(1, LOANConstant.ContractStatus.FINISH);
						pstemp.setLong(2, lContractID);
						pstemp.executeUpdate();
						pstemp.close();
						pstemp = null;
						lDiscountFinishResultCount++;
					}
				}
				//无论是否到期，收回金额等于合同金额
				if (mRepayment == mAmount)
				{
					strSQL = "update loan_contractform set nStatusID = ? where ID = ? ";
					pstemp = conn.prepareStatement(strSQL);
					pstemp.setLong(1, LOANConstant.ContractStatus.FINISH);
					pstemp.setLong(2, lContractID);
					pstemp.executeUpdate();
					pstemp.close();
					pstemp = null;
					lDiscountFinishResultCount++;
				}
			}
			System.out.println(DataFormat.getDateString(tsToday) + " 结束的贴现合同数：" + lDiscountFinishResultCount);
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			System.out.println("");
			System.out.println("信贷关机**********处理结束合同：担保合同)***************");
			System.out.println("");
			strSQL = "";
			
			/*strSQL = " select a.ID,a.dtEndDate,sum(nvl(b.RecognizanceAmount,0)) InRecognizanceAmount,"
			    	+ " sum(nvl(c.RecognizanceAmount,0)) OutRecognizanceAmount,sum(nvl(d.mAmount,0)) RecognizanceAmount,sum(nvl(c.ASSUREAMOUNT,0)) ASSUREAMOUNT "
					+ " from loan_ContractForm a,loan_AssureChargeForm b,loan_AssureManagementForm c,loan_LoanContractAssure d "
					+ " where a.ID = b.ContractID(+) and a.ID = c.ContractID(+) and a.ID = d.nContractID(+) "
					+ " and a.nTypeID = "
					+ LOANConstant.LoanType.DB
					+ " and a.nStatusID in ( "
					+ LOANConstant.ContractStatus.NOTACTIVE
					+ " , " 
					+ LOANConstant.ContractStatus.ACTIVE
					+ " ) "
					+ " and b.StatusID(+) = "
					+ LOANConstant.AssureChargeNoticeStatus.CHECK
					+ " and c.StatusID(+) = "
					+ LOANConstant.AssureManagementNoticeStatus.CHECK
					+ " and d.nAssureTypeID(+) = "
					+ LOANConstant.AssureType.RECOGNIZANCE
					+ " and d.nStatusID(+) = "
					+ Constant.RecordStatus.VALID
					+ " group by a.ID,a.dtEndDate ";*/
			
			//已经到期，并且合同保证金=已收的保证金=已还的保证金
			/*	if (dtEndDate != null && (DataFormat.getDateString(dtEndDate).compareTo(DataFormat.getDateString(tsToday)) <= 0))
				{*/
					/*if (RecognizanceAmount == assureamount && RecognizanceAmount >0)
					{
						strSQL = "update loan_contractform set nStatusID = ? where ID = ? ";
						pstemp = conn.prepareStatement(strSQL);
						pstemp.setLong(1, LOANConstant.ContractStatus.FINISH);
						pstemp.setLong(2, lContractID);
						pstemp.executeUpdate();
						pstemp.close();
						pstemp = null;
						lAssureFinishResultCount++;
					}*/
				//}
				//无论是否到期，合同保证金=已收的保证金=已还的保证金
				/*
				if (RecognizanceAmount == InRecognizanceAmount && RecognizanceAmount == OutRecognizanceAmount)
				{
					strSQL = "update loan_contractform set nStatusID = ? where ID = ? ";
					pstemp = conn.prepareStatement(strSQL);
					pstemp.setLong(1, LOANConstant.ContractStatus.FINISH);
					pstemp.setLong(2, lContractID);
					pstemp.executeUpdate();
					pstemp.close();
					pstemp = null;
					lAssureFinishResultCount++;
				}
				*/
			
			//Modify by chuanliu date 2007/08/16
			strSQL = "select a.id ID,a.Dtenddate EndDate,a.mloanamount ContractAssureAmount,"
				+ " b.assureamount ChargeAssureAmount,b.RecognizanceAmount InRecognizanceAmount,"
				+ " c.RecognizanceAmount OutRecognizanceAmount," 
//				+ "d.mAmount ContractRecognizance," 
				+ " c.assureamount RecallAmount"
				+ " from loan_ContractForm a," //--贷款合同
				+ " loan_AssureChargeForm b," //--担保收款通知单
				+ " loan_AssureManagementForm c," //--保后处理通知单
//				+ " loan_LoanContractAssure d," //--合同担保记录
				+ " sett_SubAccount e" //--子帐户表
				+ " where a.id = b.contractid"
				+ " and a.id = c.contractid"
//				+ " and a.id = d.ncontractid"
				+ " and b.id = e.AL_NLOANNOTEID"
				+ " and a.nofficeid = ?"
				+ " and a.ncurrencyid = ?"
				+ " and a.nTypeID = ?"
				+ " and a.nStatusID = ?"
				+ " and b.statusid = ?"
				+ " and c.statusid = ?"
//				+ " and d.nAssureTypeID = ?"
//				+ " and d.nstatusid = ?"
				+ " and e.nstatusid = ?"
				+ " and e.mbalance = 0"
				+ " and e.minterest < 0.01";
			System.out.println(strSQL);
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, lOfficeID);
			ps.setLong(2, lCurrencyID);
			ps.setLong(3, LOANConstant.LoanType.DB);
			ps.setLong(4, LOANConstant.ContractStatus.ACTIVE);
			ps.setLong(5, LOANConstant.AssureChargeNoticeStatus.USED);
			ps.setLong(6, LOANConstant.AssureManagementNoticeStatus.USED);
//			ps.setLong(7, LOANConstant.AssureType.RECOGNIZANCE);
//			ps.setLong(8, Constant.RecordStatus.VALID);
			ps.setLong(7, SETTConstant.SubAccountStatus.FINISH);
			rs = ps.executeQuery();
			
			while (rs != null && rs.next())
			{
				long lContractID = rs.getLong("ID");
				Timestamp endDate = rs.getTimestamp("EndDate");
				double ContractAssureAmount = rs.getDouble("ContractAssureAmount");//合同上的担保金额
				double ChargeAssureAmount = rs.getDouble("ChargeAssureAmount");//收款通知单的承保金额
				double InRecognizanceAmount = rs.getDouble("InRecognizanceAmount");//已收保证金
				double OutRecognizanceAmount = rs.getDouble("OutRecognizanceAmount");//已退保证金
//				double ContractRecognizance = rs.getDouble("ContractRecognizance");//合同上的保证金
				double RecallAmount = rs.getDouble("RecallAmount");//保后处理撤保金额
				
				
				//合同下所有收款通知单的承保金额
				double ChargeAssureAmountAll = getContractChargeAssureAmountAll(conn,lContractID);
				//合同下所有保后通知单的撤保金额
				double RecallAmountAll = getRecallAmountAll(conn,lContractID);
				//合同下所有收款通知单的收取保证金金额
				double InRecognizanceAmountAll = getInRecognizanceAmountAll(conn,lContractID);
				//合同下所有保后通知单的退保证金金额
				double OutRecognizanceAmountAll = getOutRecognizanceAmountAll(conn,lContractID);
				
				
				//合同到期或已过期时
				if(tsDate.compareTo(endDate) >= 0){
					//承保金额 <= 合同担保金额
					if(ChargeAssureAmount <= ContractAssureAmount) {
						//该合同下，所有收款通知单的承保金额＝所有保后通知单的撤保金额
						if(ChargeAssureAmountAll == RecallAmountAll){
							//该合同下，所有收款通知单的收取保证金金额＝所有保后通知单的退保证金金额
							if(InRecognizanceAmountAll == OutRecognizanceAmountAll) {
								//修改担保合同状态为已结束
								strSQL = "update loan_contractform set nStatusID = ? where ID = ? ";
								pstemp = conn.prepareStatement(strSQL);
								pstemp.setLong(1, LOANConstant.ContractStatus.FINISH);
								pstemp.setLong(2, lContractID);
								pstemp.executeUpdate();
								pstemp.close();
								pstemp = null;
								
								lAssureFinishResultCount++;
							}
						}
					}
				}
				//合同未到期
				else {
					//承保金额 = 合同担保金额
					if(ChargeAssureAmount == ContractAssureAmount) {
						//该合同下，所有收款通知单的承保金额＝所有保后通知单的撤保金额
						if(ChargeAssureAmountAll == RecallAmountAll){
							//该合同下，所有收款通知单的收取保证金金额＝所有保后通知单的退保证金金额
							if(InRecognizanceAmountAll == OutRecognizanceAmountAll) {
								//该合同下，合同上的保证金=所有收款通知单的收取保证金金额
//								if(ContractRecognizance == InRecognizanceAmountAll) {
									//修改担保合同状态为已结束
									strSQL = "update loan_contractform set nStatusID = ? where ID = ? ";
									pstemp = conn.prepareStatement(strSQL);
									pstemp.setLong(1, LOANConstant.ContractStatus.FINISH);
									pstemp.setLong(2, lContractID);
									pstemp.executeUpdate();
									pstemp.close();
									pstemp = null;
									
									lAssureFinishResultCount++;
//								}
							}
						}
					}
				}
			}
			
			
			//added by mzh_fu date 2007/12/19 无保证金无手续费的处理
			strSQL = "select a.id ID,a.Dtenddate EndDate,a.mloanamount ContractAssureAmount,"
				+ " b.assureamount ChargeAssureAmount,b.RecognizanceAmount InRecognizanceAmount,"
				+ " c.RecognizanceAmount OutRecognizanceAmount," 
//				+ "d.mAmount ContractRecognizance," 
				+ " c.assureamount RecallAmount"
				+ " from loan_ContractForm a," //--贷款合同
				+ " loan_AssureChargeForm b," //--担保收款通知单
				+ " loan_AssureManagementForm c " //--保后处理通知单
//				+ " loan_LoanContractAssure d," //--合同担保记录
				+ " where a.id = b.contractid"
				+ " and a.id = c.contractid"
//				+ " and a.id = d.ncontractid"
				+ " and a.nofficeid = ?"
				+ " and a.ncurrencyid = ?"
				+ " and a.nTypeID = ?"
				+ " and a.nStatusID = ?"
				+ " and b.statusid = ?"
				+ " and c.statusid = ?";
//				+ " and d.nAssureTypeID = ?"
//				+ " and d.nstatusid = ?"

			System.out.println(strSQL);
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, lOfficeID);
			ps.setLong(2, lCurrencyID);
			ps.setLong(3, LOANConstant.LoanType.DB);
			ps.setLong(4, LOANConstant.ContractStatus.ACTIVE);
			ps.setLong(5, LOANConstant.AssureChargeNoticeStatus.USED);
			ps.setLong(6, LOANConstant.AssureManagementNoticeStatus.USED);
//			ps.setLong(7, LOANConstant.AssureType.RECOGNIZANCE);
//			ps.setLong(8, Constant.RecordStatus.VALID);
			//ps.setLong(7, SETTConstant.SubAccountStatus.FINISH);
			rs = ps.executeQuery();
			
			while (rs != null && rs.next())
			{
				long lContractID = rs.getLong("ID");
				Timestamp endDate = rs.getTimestamp("EndDate");
				double ContractAssureAmount = rs.getDouble("ContractAssureAmount");//合同上的担保金额
				double ChargeAssureAmount = rs.getDouble("ChargeAssureAmount");//收款通知单的承保金额
				double InRecognizanceAmount = rs.getDouble("InRecognizanceAmount");//已收保证金
				double OutRecognizanceAmount = rs.getDouble("OutRecognizanceAmount");//已退保证金
//				double ContractRecognizance = rs.getDouble("ContractRecognizance");//合同上的保证金
				double RecallAmount = rs.getDouble("RecallAmount");//保后处理撤保金额
				
				
				//合同下所有收款通知单的承保金额
				double ChargeAssureAmountAll = getContractChargeAssureAmountAll(conn,lContractID);
				//合同下所有保后通知单的撤保金额
				double RecallAmountAll = getRecallAmountAll(conn,lContractID);
				//合同下所有收款通知单的收取保证金金额
				double InRecognizanceAmountAll = getInRecognizanceAmountAll(conn,lContractID);
				//合同下所有保后通知单的退保证金金额
				double OutRecognizanceAmountAll = getOutRecognizanceAmountAll(conn,lContractID);
				
				
				//合同到期或已过期时
				if(tsDate.compareTo(endDate) >= 0){
					//承保金额 <= 合同担保金额
					if(ChargeAssureAmount <= ContractAssureAmount) {
						//该合同下，所有收款通知单的承保金额＝所有保后通知单的撤保金额
						if(ChargeAssureAmountAll == RecallAmountAll){
							//该合同下，所有收款通知单的收取保证金金额＝所有保后通知单的退保证金金额
							if(InRecognizanceAmountAll == OutRecognizanceAmountAll) {
								//修改担保合同状态为已结束
								strSQL = "update loan_contractform set nStatusID = ? where ID = ? ";
								pstemp = conn.prepareStatement(strSQL);
								pstemp.setLong(1, LOANConstant.ContractStatus.FINISH);
								pstemp.setLong(2, lContractID);
								pstemp.executeUpdate();
								pstemp.close();
								pstemp = null;
								
								lAssureFinishResultCount++;
							}
						}
					}
				}
				//合同未到期
				else {
					//承保金额 = 合同担保金额
					if(ChargeAssureAmount == ContractAssureAmount) {
						//该合同下，所有收款通知单的承保金额＝所有保后通知单的撤保金额
						if(ChargeAssureAmountAll == RecallAmountAll){
							//该合同下，所有收款通知单的收取保证金金额＝所有保后通知单的退保证金金额
							if(InRecognizanceAmountAll == OutRecognizanceAmountAll) {
								//该合同下，合同上的保证金=所有收款通知单的收取保证金金额
//								if(ContractRecognizance == InRecognizanceAmountAll) {
									//修改担保合同状态为已结束
									strSQL = "update loan_contractform set nStatusID = ? where ID = ? ";
									pstemp = conn.prepareStatement(strSQL);
									pstemp.setLong(1, LOANConstant.ContractStatus.FINISH);
									pstemp.setLong(2, lContractID);
									pstemp.executeUpdate();
									pstemp.close();
									pstemp = null;
									
									lAssureFinishResultCount++;
//								}
							}
						}
					}
				}
			}
			
			System.out.println(DataFormat.getDateString(tsToday) + " 结束的担保合同数：" + lAssureFinishResultCount);
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			
			System.out.println("");
			System.out.println("信贷关机**********处理开始合同：融资租赁合同)***************");
			System.out.println("");
			strSQL="";
//			strSQL ="select ncontractid,amount ramount,camount lamount "
//						+"from (select sum(a.AMOUNT + a.interestamount) amount, a.contractid " 
//								+" from loan_leaseholdrepayform a "
//								+" where a.nstatusid = ? "
//								+" group by a.contractid) aa, "
//							    +" (select b.ncontractplanid, sum(b.mamount + b.minterestamount) camount "
//								+" from loan_loancontractplandetail b "
//								+" group by b.ncontractplanid) bb, "
//								+" loan_loancontractplan c, "
//								+" loan_contractform d " 
//								+" where "
//								+" d.nofficeid=? "
//								+" and d.ncurrencyid=? "
//								+" and d.id= c.ncontractid "
//								+" and aa.contractid = c.NCONTRACTID "
//								+" and c.id = bb.NCONTRACTPLANID "
//								+" and c.nisused=2 "
//								+" and c.nstatusid=1 "
//								+" and  d.nstatusid=? ";
//			System.out.println(strSQL);
//			ps = conn.prepareStatement(strSQL);
//			ps.setLong(1, LOANConstant.LoanPayNoticeStatus.USED);
//			ps.setLong(2, lOfficeID);
//			ps.setLong(3, lCurrencyID);
//			ps.setLong(4, LOANConstant.ContractStatus.ACTIVE);
//			rs = ps.executeQuery();
//			while (rs != null && rs.next())
//			{
//				long lContractID = rs.getLong("ncontractid");
//				double mRepayment = rs.getDouble("ramount");
//				double mAmount = rs.getDouble("lamount");
//				if(mRepayment==mAmount)
//				{
//					strSQL = "update loan_contractform set nStatusID = ? where ID = ? ";
//					pstemp = conn.prepareStatement(strSQL);
//					pstemp.setLong(1, LOANConstant.ContractStatus.FINISH);
//					pstemp.setLong(2, lContractID);
//					pstemp.executeUpdate();
//					pstemp.close();
//					pstemp = null;
//				}
//			}
			//modify by zwxiao 2010-06-30 修改融资租赁合同状态为已结束的逻辑，当合同做了保后处理并且已经复核才能
			//将合同状态修改为已结束
			strSQL =" select a.ncontractid as ncontractid "+
			  " from sett_transfinancialmargin a "+
			  " where a.nstatusid = "+SETTConstant.TransactionStatus.CHECK+
			  " and a.dtexecute = to_Date('"+DataFormat.formatDate(tsToday)+"', 'yyyy-MM-dd') ";
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs != null && rs.next()){
				long lContractID = rs.getLong("ncontractid");
				strSQL = "update loan_contractform set nStatusID = ? where ID = ? ";
				pstemp = conn.prepareStatement(strSQL);
				pstemp.setLong(1, LOANConstant.ContractStatus.FINISH);
				pstemp.setLong(2, lContractID);
				pstemp.executeUpdate();
				pstemp.close();
				pstemp = null;
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			System.out.println("");
			System.out.println("信贷关机**********处理结束合同：融资租赁合同)***************");
			System.out.println("");
			
			
			
			strSQL="";
			strSQL ="select sum(m.mdiscountbillamount) pAmount,sum(m.discountcredenceamount) cAmount," +
					"NDISCOUNTCONTRACTID,NINOROUT,NDISCOUNTTYPEID ,n.mcheckamount,m.mdiscountbillamount," +
					"m.discountcredenceamount " +
					" from sett_transcredence m, loan_contractform n " +
					" where DTEXECUTE = ? and m.nStatusID in (3,4,5,6) " +
					" and m.nOfficeID=" + lOfficeID + " and m.nCurrencyID =  " + lCurrencyID +" and m.ndiscountcontractid=n.id " +
					" and m.nstatusid = 3 " +
					" group by m.NDISCOUNTCONTRACTID, NINOROUT, NDISCOUNTTYPEID, mcheckamount, m.mdiscountbillamount, m.discountcredenceamount ";
			System.out.println(strSQL);
			ps = conn.prepareStatement(strSQL);
			ps.setTimestamp(1, tsDate);
			System.out.println("我要看的sql为："+strSQL);
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				long lContractID = rs.getLong("NDISCOUNTCONTRACTID");
				double mpayment = rs.getDouble("pAmount");//票据汇总金额
				double mAmount = rs.getDouble("cAmount");//凭证汇总金额
				double hAmount = rs.getDouble("mcheckamount");//合同票面金额
				long nDiscountTypeID = rs.getLong("NDISCOUNTTYPEID");//买断回购
				long typeID = rs.getLong("NINOROUT");//买入或卖出
				//当业务为回购的业务时，修改合同状态为执行中
				if(nDiscountTypeID==LOANConstant.TransDiscountType.REPURCHASE)
				{
					strSQL = "update loan_contractform set nStatusID = ? where ID = ? ";
					pstemp = conn.prepareStatement(strSQL);
					pstemp.setLong(1, LOANConstant.ContractStatus.ACTIVE);
					pstemp.setLong(2, lContractID);
					pstemp.executeUpdate();
					pstemp.close();
					pstemp = null;
				}
				//当业务为买断的业务时，该转贴现合同关联的所有结算已复核转贴现交易累计票面金额小于合同票面金额，修改转贴现合同合同状态为执行中
				if((nDiscountTypeID==LOANConstant.TransDiscountType.BUYBREAK) && (mpayment< hAmount ))
				{   
					strSQL = "update loan_contractform set nStatusID = ? where ID = ? ";
					pstemp = conn.prepareStatement(strSQL);
					pstemp.setLong(1, LOANConstant.ContractStatus.ACTIVE);
					pstemp.setLong(2, lContractID);
					pstemp.executeUpdate();
					pstemp.close();
					pstemp = null;
					
				}
				//当业务为买断业务类型时，该转贴现合同关联的所有结算已复核转贴现交易累计票面金额等于合同金额票面金额
				if((nDiscountTypeID==LOANConstant.TransDiscountType.BUYBREAK) && (mpayment == hAmount ))
				{   
					strSQL = "update loan_contractform set nStatusID = ? where ID = ? ";
					pstemp = conn.prepareStatement(strSQL);
					pstemp.setLong(1, LOANConstant.ContractStatus.FINISH);
					pstemp.setLong(2, lContractID);
					pstemp.executeUpdate();
					pstemp.close();
					pstemp = null;
					
				}
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			System.out.println("");
			System.out.println("信贷关机**********处理结束合同：转贴现合同)***************");
			System.out.println("");
			
			//转贴现买入买断（合同变为执行中）
			strSQL="";
			strSQL = " update LOAN_ContractForm a "+
					" set a.nstatusid ="+LOANConstant.ContractStatus.ACTIVE+
					" where a.id in ( select l.id from LOAN_ContractForm l,loan_discountcredence d" +
					" where d.ncontractid=l.id " +
					" and l.ntypeid ="+LOANConstant.LoanType.ZTX+
					" and l.nofficeid ="+lOfficeID+
					" and l.ncurrencyid ="+lCurrencyID+
					" and l.nstatusid = "+LOANConstant.ContractStatus.NOTACTIVE+
					" and l.NINOROUT ="+LOANConstant.TransDiscountInOrOut.IN+
					" and l.NDISCOUNTTYPEID ="+LOANConstant.TransDiscountType.BUYBREAK+
			        " and d.nstatusid="+LOANConstant.DiscountCredenceStatus.USED+")"; 
			ps = conn.prepareStatement(strSQL);
			ps.executeUpdate();
			if(ps!=null)
			{
				ps.close();
				ps = null;
			}			
			
			//转贴现买入买断（将票据到期的合同变为已结束）
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String closeDate = df.format(tsDate);
			strSQL="";
			strSQL = " update LOAN_ContractForm c "+
					" set c.nstatusid ="+LOANConstant.ContractStatus.FINISH+
					" where c.id in " +
					" (select a.id "+
					" from LOAN_ContractForm a, loan_discountcontractbill b "+
					" where a.ntypeid ="+LOANConstant.LoanType.ZTX+
					" and a.nofficeid ="+lOfficeID+
					" and a.ncurrencyid ="+lCurrencyID+
					" and a.nstatusid in ("+LOANConstant.ContractStatus.NOTACTIVE+","+LOANConstant.ContractStatus.ACTIVE+") "+
					" and a.NINOROUT ="+LOANConstant.TransDiscountInOrOut.IN+
					" and a.NDISCOUNTTYPEID ="+LOANConstant.TransDiscountType.BUYBREAK+
					" and a.id = b.ncontractid "+
					" and b.id not in (select b.id from sett_transcraftbrother s, loan_discountcredence l,loan_discountcontractbill b,rtransdiscountcredencebill f "+
					" where s.nnoticeid = l.id and s.NTRANSACTIONTYPEID = "+SETTConstant.TransactionType.TRANS_DISCOUNT_GRANT+"and s.NCRABUSINESSTYPEID ="+LOANConstant.LoanType.ZTX+
					" and s.NSUBTRANSACTIONTYPEID="+SETTConstant.SubTransactionType.BREAK_NOTIFY+" and s.nstatusid="+SETTConstant.TransactionStatus.CHECK+
					" and f.transdiscountcredenceid = l.id and b.id = f.discountcontractbillid)"+
					" group by a.id "+
					" having to_char(max(b.dtend), 'yyyy-mm-dd') <= '"+closeDate+"') ";
			ps = conn.prepareStatement(strSQL);
			ps.executeUpdate();
			if(ps!=null)
			{
				ps.close();
				ps = null;
			}
  			
			//转贴现卖出买断(若某合同下的所有凭证的状态都为“已使用”，则将此合同状态变成已结束)
			strSQL="";
			strSQL = " update LOAN_ContractForm a "+
					" set a.nstatusid ="+LOANConstant.ContractStatus.FINISH+
					" where a.id in ( select l.id from LOAN_ContractForm l,loan_discountcredence d" +
					" where d.ncontractid=l.id and l.ntypeid ="+LOANConstant.LoanType.ZTX+
					" and l.nofficeid ="+lOfficeID+
					" and l.ncurrencyid ="+lCurrencyID+
					" and l.nstatusid = "+LOANConstant.ContractStatus.NOTACTIVE+
					" and l.NINOROUT ="+LOANConstant.TransDiscountInOrOut.OUT+
					" and l.NDISCOUNTTYPEID ="+LOANConstant.TransDiscountType.BUYBREAK+
					" group by l.id "+
					" having max(d.nstatusid)="+LOANConstant.DiscountCredenceStatus.USED+
					" and min(d.nstatusid)="+LOANConstant.DiscountCredenceStatus.USED+")";
			ps = conn.prepareStatement(strSQL);
			ps.executeUpdate();
			if(ps!=null)
			{
				ps.close();
				ps = null;
			}
			
			//回购
			strSQL="";
			strSQL = " update LOAN_ContractForm a "+
					" set a.nstatusid ="+LOANConstant.ContractStatus.ACTIVE+
					" where a.id in ( select l.id from LOAN_ContractForm l,loan_discountcredence d" +
					" where d.ncontractid=l.id " +
					" and l.ntypeid ="+LOANConstant.LoanType.ZTX+
					" and l.nofficeid ="+lOfficeID+
					" and l.ncurrencyid ="+lCurrencyID+
					" and l.nstatusid = "+LOANConstant.ContractStatus.NOTACTIVE+
					" and l.NDISCOUNTTYPEID ="+LOANConstant.TransDiscountType.REPURCHASE+
			        " and d.nstatusid="+LOANConstant.DiscountCredenceStatus.USED+")";
			ps = conn.prepareStatement(strSQL);
			ps.executeUpdate();
			if(ps!=null)
			{
				ps.close();
				ps = null;
			}			
			
			
			//回购(当票据全部购回时，既该合同下转贴现购回的凭证金额总和等于该合同买入或卖出回购凭证的金额时，改变状态)
			strSQL="";
			strSQL=" update LOAN_ContractForm a "+
				   " set a.nstatusid ="+LOANConstant.ContractStatus.FINISH+
				   " where a.id in (select e.id  from ( select a.id,sum(l.mamount) outsumamount "+
				   " from sett_transcraftbrother s, loan_discountcredence l,LOAN_ContractForm a "+
				   " where s.nnoticeid = l.id  and l.ncontractid=a.id and s.NTRANSACTIONTYPEID ="+SETTConstant.TransactionType.TRANS_DISCOUNT_GRANT+
				   " and s.NCRABUSINESSTYPEID ="+LOANConstant.LoanType.ZTX+
				   " and l.nofficeid ="+lOfficeID+
				   " and l.ncurrencyid ="+lCurrencyID+
				   " and s.NSUBTRANSACTIONTYPEID in ("+SETTConstant.SubTransactionType.REPURCHASE_NOTIFY+","+SETTConstant.SubTransactionType.REPURCHASE_INVEST+") "+
				   " and s.nstatusid="+SETTConstant.TransactionStatus.CHECK+
				   " and a.NDISCOUNTTYPEID ="+LOANConstant.TransDiscountType.REPURCHASE+
				   " and l.ntypeid ="+LOANConstant.CredenceType.TRANSDISCOUNTCREDENCE+
				   " and l.nstatusid = "+LOANConstant.DiscountCredenceStatus.USED+ 
				   " and a.nstatusid="+LOANConstant.ContractStatus.ACTIVE+
				   " group by a.id )e ," +
				   " (select a.id,sum(l.mamount) insumamount " +
				   " from sett_transcraftbrother s, loan_discountcredence l,LOAN_ContractForm a" +
				   " where  s.nnoticeid = l.id and l.ncontractid=a.id and s.NTRANSACTIONTYPEID ="+SETTConstant.TransactionType.TRANS_DISCOUNT_REPURCHASE+
				   " and s.NCRABUSINESSTYPEID ="+LOANConstant.LoanType.ZTX+
				   " and l.nofficeid ="+lOfficeID+
				   " and l.ncurrencyid ="+lCurrencyID+
				   " and s.NSUBTRANSACTIONTYPEID in ("+SETTConstant.SubTransactionType.REPURCHASE_NOTIFY+","+SETTConstant.SubTransactionType.REPURCHASE_INVEST+") "+
				   " and s.nstatusid="+SETTConstant.TransactionStatus.CHECK+
				   " and a.NDISCOUNTTYPEID ="+LOANConstant.TransDiscountType.REPURCHASE+	
				   " and l.ntypeid ="+LOANConstant.CredenceType.REPURCHASECREDENCE+
				   " and l.nstatusid = "+LOANConstant.DiscountCredenceStatus.USED+ 
				   " and a.nstatusid="+LOANConstant.ContractStatus.ACTIVE+
				   " group by a.id ) f where e.id=f.id and e.outsumamount=f.insumamount )";
			ps = conn.prepareStatement(strSQL);
			ps.executeUpdate();
			if(ps!=null)
			{
				ps.close();
				ps = null;
			}			
			
			//信贷资产转让
			//买入买断 卖出买断 合同(更新状态为已结束)
			strSQL="";
			strSQL=" update SEC_APPLYCONTRACT s "+
					" set s.statusid ="+LOANConstant.ContractStatus.FINISH+
					" where s.id in (select s.id "+
					" from SEC_APPLYCONTRACT s, SEC_NoticeForm n "+
					" where  s.id = n.contractid "+
					" and s.transactiontypeid in ("+SECConstant.BusinessType.CAPITAL_REPURCHASE.INVEST_BREAK+","+SECConstant.BusinessType.CAPITAL_REPURCHASE.BREAK_NOTIFY+")"+
					" and s.statusid ="+LOANConstant.ContractStatus.NOTACTIVE+
					" and n.statusid ="+SECConstant.NoticeFormStatus.USED+
					" and s.officeid ="+lOfficeID+
					" and s.currencyid ="+lCurrencyID+
					" and n.officeid ="+lOfficeID+
					" and n.currencyid ="+lCurrencyID+
					" )";
			ps = conn.prepareStatement(strSQL);
			ps.executeUpdate();
			if(ps!=null)
			{
				ps.close();
				ps = null;
			}			
			
			//买入回购 卖出回购 合同(更新状态为执行中)
			strSQL="";
			strSQL=" update SEC_APPLYCONTRACT s "+
					" set s.statusid ="+LOANConstant.ContractStatus.ACTIVE+
					" where s.id in (select s.id "+
					" from SEC_APPLYCONTRACT s, SEC_NoticeForm n "+
					" where  s.id = n.contractid "+
					" and s.transactiontypeid in ("+SECConstant.BusinessType.CAPITAL_REPURCHASE.AVERAGE_NOTIFY+","+SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY+")"+
					" and s.statusid ="+LOANConstant.ContractStatus.NOTACTIVE+
					" and n.statusid ="+SECConstant.NoticeFormStatus.USED+
					" and s.officeid ="+lOfficeID+
					" and s.currencyid ="+lCurrencyID+
					" and n.officeid ="+lOfficeID+
					" and n.currencyid ="+lCurrencyID+
					" )";

			ps = conn.prepareStatement(strSQL);			
			ps.executeUpdate();
			if(ps!=null)
			{
				ps.close();
				ps = null;
			}				
			
			//买入回购合同  买入回购通知单 卖出回购合同 卖出回购通知单 （合同状态更新为已结束）
			strSQL="";
			strSQL = " update SEC_APPLYCONTRACT c "+
					" set c.statusid ="+LOANConstant.ContractStatus.FINISH+
					" where c.id in (select c.id "+
					" from SEC_APPLYCONTRACT c, SEC_NoticeForm n "+
					" where n.contractid = c.id "+
					" and c.transactiontypeid in ("+SECConstant.BusinessType.CAPITAL_REPURCHASE.AVERAGE_NOTIFY+","+SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY+")"+
					" and n.transactiontypeid in ("+SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_CAPITAL+","+SECConstant.BusinessType.CAPITAL_REPURCHASE.ACCEPT_CAPITAL+")"+
					" and c.statusid ="+LOANConstant.ContractStatus.ACTIVE+
					" and n.statusid ="+SECConstant.NoticeFormStatus.USED+
					" and c.officeid ="+lOfficeID+
					" and c.currencyid ="+lCurrencyID+
					" and n.officeid ="+lOfficeID+
					" and n.currencyid ="+lCurrencyID+") ";
			ps = conn.prepareStatement(strSQL);
			ps.executeUpdate();
			if(ps!=null)
			{
				ps.close();
				ps = null;
			}				
			
			
			//自营贷款补录合同(执行中)
			strSQL="";
			strSQL= " update loan_contractForm c "+
					" set c.nstatusid ="+LOANConstant.ContractStatus.ACTIVE+
					" where c.id in (select c.id "+
					" from loan_contractForm c, loan_payform p "+
					" where c.id = p.ncontractid "+
					" and c.nstatusid ="+LOANConstant.ContractStatus.NOTACTIVE+
					" and p.nstatusid ="+LOANConstant.LoanPayNoticeStatus.USED+
					" and c.nofficeid ="+lOfficeID+
					" and c.ncurrencyid ="+lCurrencyID+
					" and p.nofficeid ="+lOfficeID+
					" and p.ncurrencyid ="+lCurrencyID+
					" and c.ntypeid ="+LOANConstant.LoanType.ZY+
					" and c.isbuyinto =1) ";
			ps = conn.prepareStatement(strSQL);
			ps.executeUpdate();
			if(ps!=null)
			{
				ps.close();
				ps = null;
			}	

			//自营贷款补录合同(如果到期则更新状态为已结束)
			strSQL="";
			strSQL = " update loan_contractForm c "+
					" set c.nstatusid ="+LOANConstant.ContractStatus.FINISH+
					" where c.id in (select c.id "+
					" from loan_contractForm c, loan_payform p "+
					" where c.id = p.ncontractid "+
					" and c.nstatusid in("+LOANConstant.ContractStatus.NOTACTIVE+","+LOANConstant.ContractStatus.ACTIVE+")"+
					" and p.nstatusid in ("+LOANConstant.LoanPayNoticeStatus.CHECK+","+LOANConstant.LoanPayNoticeStatus.USED+")"+
					" and c.nofficeid ="+lOfficeID+
					" and c.ncurrencyid ="+lCurrencyID+
					" and p.nofficeid ="+lOfficeID+
					" and p.ncurrencyid ="+lCurrencyID+
					" and c.ntypeid ="+LOANConstant.LoanType.ZY+
					" and to_char(c.dtenddate,'yyyy-mm-dd')<= '"+closeDate+"'"+
					" and c.isbuyinto =1) ";
			ps = conn.prepareStatement(strSQL);
			ps.executeUpdate();
			if(ps!=null)
			{
				ps.close();
				ps = null;
			}			
			
			/****************************************************************************
			****************************************************************************
			*****************************************************************************
			*测试代码结束*/
			
			
			
		}
		catch (Exception e)
		{
			bTransCheck = false;
			e.printStackTrace();
			if (pstemp != null)
			{
				pstemp.close();
				pstemp = null;
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
			throw new Exception(e.getMessage());
		}
		finally
		{
			try
			{
				if (pstemp != null)
				{
					pstemp.close();
					pstemp = null;
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
			}
			catch (Exception e)
			{
				throw new Exception("remote exception : " + e.toString());
			}
		}
		System.out.println("");
		System.out.println("**********处理结束合同退出***************");
		System.out.println("");
		return bTransCheck;
	}
	
	//Modify by leiyang date 2007/07/06
	//合同下所有收款通知单的承保金额
	public double getContractChargeAssureAmountAll(Connection conn, long ContractID) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		double amountAll = 0.0;
		String strSQL = null;
		try
		{
			strSQL = " select sum(assureamount) ChargeAssureAmountAll  from loan_AssureChargeForm t"
				+ " where t.contractid = ?"
				+ " and t.statusid = ?";
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, ContractID);
			ps.setLong(2, LOANConstant.AssureChargeNoticeStatus.USED);
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				amountAll = rs.getDouble("ChargeAssureAmountAll");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			System.out.println("合同号为：" + ContractID + "  的  合同下所有收款通知单的承保金额  为：" + amountAll);
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
			throw new Exception(e.getMessage());
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
			}
			catch (Exception e)
			{
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return amountAll;
	}
	
	//Modify by leiyang date 2007/07/06
	//合同下所有收款通知单的收取保证金金额
	public double getInRecognizanceAmountAll(Connection conn, long ContractID) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		double amountAll = 0.0;
		String strSQL = null;
		try
		{
			strSQL = " select sum(RecognizanceAmount) InRecognizanceAmountAll  from loan_AssureChargeForm t"
				+ " where t.contractid = ?"
				+ " and t.statusid = ?";
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, ContractID);
			ps.setLong(2, LOANConstant.AssureChargeNoticeStatus.USED);
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				amountAll = rs.getDouble("InRecognizanceAmountAll");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			System.out.println("合同号为：" + ContractID + "  的  合同下所有收款通知单的收取保证金金额  为：" + amountAll);
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
			throw new Exception(e.getMessage());
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
			}
			catch (Exception e)
			{
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return amountAll;
	}
	
	//Modify by leiyang date 2007/07/06
	//合同下所有保后通知单的撤保金额
	public double getRecallAmountAll(Connection conn, long ContractID) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		double amountAll = 0.0;
		String strSQL = null;
		try
		{
			strSQL = " select sum(assureamount) RecallAmountAll  from loan_AssureManagementForm t"
				+ " where t.contractid = ?"
				+ " and t.statusid = ?";
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, ContractID);
			ps.setLong(2, LOANConstant.AssureManagementNoticeStatus.USED);
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				amountAll = rs.getDouble("RecallAmountAll");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			System.out.println("合同号为：" + ContractID + "  的  合同下所有保后通知单的撤保金额  为：" + amountAll);
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
			throw new Exception(e.getMessage());
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
			}
			catch (Exception e)
			{
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return amountAll;
	}
	
	//Modify by leiyang date 2007/07/06
	//合同下所有保后通知单的退保证金金额
	public double getOutRecognizanceAmountAll(Connection conn, long ContractID) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		double amountAll = 0.0;
		String strSQL = null;
		try
		{
			strSQL = " select sum(RecognizanceAmount) OutRecognizanceAmountAll  from loan_AssureManagementForm t"
				+ " where t.contractid = ?"
				+ " and t.statusid = ?";
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, ContractID);
			ps.setLong(2, LOANConstant.AssureManagementNoticeStatus.USED);
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				amountAll = rs.getDouble("OutRecognizanceAmountAll");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			System.out.println("合同号为：" + ContractID + "  的  合同下所有保后通知单的退保证金金额  为：" + amountAll);
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
			throw new Exception(e.getMessage());
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
			}
			catch (Exception e)
			{
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return amountAll;
	}
	
	public boolean dealContractForOpen(Connection conn, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		PreparedStatement ps = null;
		PreparedStatement pstemp = null;
		ResultSet rs = null;
		boolean bTransCheck = true;
		long lOverDueResultCount = 0;
		long lContractFinishResultCount = 0;
		Timestamp tsToday = null;
		String strSQL = null;
		java.util.Calendar calendar = Calendar.getInstance();
		try
		{
			System.out.println("");
			System.out.println("**********处理逾期合同***************");
			System.out.println("");
			//calendar.setTime(Env.getSystemDate());
			calendar.setTime(tsDate);
			tsToday = DataFormat.getDateTime(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE), 0, 0, 0);
			strSQL = "";
			strSQL =
				" Select aa.ID, aa.dtEndDate, aa.nTypeID, nvl(aa.mExamineAmount,0) mAmount, "
					+ " nvl(bb.mBalance,0) mBalance, nvl(bb.mRepayment,0) mRepayment "
					+ " From "
					+ " ( "
					//+ "   Select a.ID,dtEndDate,nTypeID,mExamineAmount "
					+ "   Select a.ID,bb.dtend dtEndDate,nTypeID,mExamineAmount "
					//+ "   From loan_contractform a "
			
					//modify by xwhe 2009-01-13 过滤掉已经还请的放款单的子账户
					//该SQL分为两部分，前部分查询合同下所有放款通知单的合同ID+放款单结束日期 ，后半部分查询的是合同ID+所有放款单对应子账户的利息和余额的和
					//通过合同号关联起来显示，显示是以放款结束日期+利息和余额（和） 形式 ，这样就导致有的放款单的子账户已经还清了还是被关联查询出来。
					//更新的SQL为把前半部分已经还清的放款单进行过滤，只保留未还清的。
			//		+ "   From loan_contractform a,loan_payform bb "
					+ "   From loan_contractform a , (select payform.* from loan_payform payform, sett_subaccount subaccount"
                    +"    where payform.id = subaccount.al_nloannoteid "
                    +"    and payform.nstatusid = " +LOANConstant.LoanPayNoticeStatus.USED
                    +"    and subaccount.nstatusid = "+SETTConstant.SubAccountStatus.NORMAL+") bb "
                    
					+"   Where ( a.nStatusID = ? Or a.nStatusID = ? ) "
					//ADD by zcwang 2008-4-16
					+ " and bb.ncontractid = a.id "
					+ " and bb.nstatusid = " + LOANConstant.LoanPayNoticeStatus.USED
					//end
					+ "   And a.nOfficeID = "
					+ lOfficeID
					+ "   And a.nCurrencyID = "
					+ lCurrencyID
					+ " ) aa, "
					+ " ( "
					+ "    select b.nContractID,sum(a.mOpenAmount-a.mBalance) mRepayment,sum(a.mBalance + a.mInterest + a.AL_mSuretyFee + a.AL_mCommission + a.AL_mOverDueInterest + a.AL_mCompoundInterest ) mBalance "
					+ "    from sett_subAccount a,loan_payform b,sett_Account c ,sett_accountType d"
					+ "    where  AL_nLoanNoteID = b.ID and a.nAccountID = c.ID and c.nAccountTypeID = d.id and d.nAccountGroupID !="
					+ SETTConstant.AccountGroupType.DISCOUNT
					+ "    and a.nStatusID in ( "
					+ SETTConstant.SubAccountStatus.NORMAL
					+ " ) "
					+ "    group by nContractID "
					+ " ) bb "
					+ " Where aa.ID = bb.nContractID ";
			//System.out.println(strSQL);
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, LOANConstant.ContractStatus.ACTIVE);
			ps.setLong(2, LOANConstant.ContractStatus.EXTEND);
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				long lContractID = rs.getLong("ID");
				Timestamp dtEndDate = rs.getTimestamp("dtEndDate");
				double mBalance = rs.getDouble("mBalance");
				double mRepayment = rs.getDouble("mRepayment");
				double mAmount = rs.getDouble("mAmount");
				long nIsCircle = rs.getLong("nTypeID");
				if (dtEndDate != null && DataFormat.getDateString(tsToday).compareTo(DataFormat.getDateString(dtEndDate)) > 0)
				{
					if (mBalance > 0)
					{
						//strSQL = "update loan_contractform set nStatusID = ?  where ID = ? ";
						strSQL = "update loan_contractform set nStatusID = ?, OVERDUEDATE = ? where ID = ? ";
						pstemp = conn.prepareStatement(strSQL);
						//pstemp.setLong(1, LOANConstant.ContractStatus.OVERDUE);
						//pstemp.setLong(2, lContractID);
						pstemp.setLong(1, LOANConstant.ContractStatus.OVERDUE);
						pstemp.setTimestamp(2,dtEndDate);
						pstemp.setLong(3, lContractID);
						pstemp.executeUpdate();
						pstemp.close();
						pstemp = null;
						lOverDueResultCount++;
					}
				}
			}
			System.out.println(DataFormat.getDateString(tsToday) + " 逾期的合同数：" + lOverDueResultCount);
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			System.out.println("");
			System.out.println("**********处理呆滞合同***************");
			System.out.println("");
			strSQL = "";
			//strSQL = " select ID from loan_contractform where trunc(dtEndDate,'DD') + 90 <= trunc(?,'DD') and nStatusID = ? and nOfficeID = ? and nCurrencyID = ?";
			strSQL = " select ID from loan_contractform where trunc(OVERDUEDATE,'DD') + 90 <= trunc(?,'DD') and nStatusID = ? and nOfficeID = ? and nCurrencyID = ?";
			//System.out.println(strSQL);
			ps = conn.prepareStatement(strSQL);	
			
			ps.setTimestamp(1, tsToday);
			ps.setLong(2, LOANConstant.ContractStatus.OVERDUE);
			ps.setLong(3, lOfficeID);
			ps.setLong(4, lCurrencyID);
			
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				long lContractID = rs.getLong("ID");
				strSQL = "update loan_contractform set nStatusID = ? where ID = ? ";
				pstemp = conn.prepareStatement(strSQL);
				pstemp.setLong(1, LOANConstant.ContractStatus.DELAYDEBT);
				pstemp.setLong(2, lContractID);
				pstemp.executeUpdate();
				pstemp.close();
				pstemp = null;
			}
			System.out.println(DataFormat.getDateString(tsToday) + " 呆滞的合同数：" + lOverDueResultCount);
			rs.close();
			rs = null;
			ps.close();
			ps = null;
		}
		catch (Exception e)
		{
			bTransCheck = false;
			e.printStackTrace();
			if (pstemp != null)
			{
				pstemp.close();
				pstemp = null;
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
			throw new Exception(e.getMessage());
		}
		finally
		{
			try
			{
				if (pstemp != null)
				{
					pstemp.close();
					pstemp = null;
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
			}
			catch (Exception e)
			{
				throw new Exception("remote exception : " + e.toString());
			}
		}
		System.out.println("");
		System.out.println("**********处理逾期合同退出***************");
		System.out.println("");
		return bTransCheck;
	}
	/**
	 * 系统-权限自动取消
	 *
	 */
	public void annulApprove(Connection conn) throws Exception
	{
		PreparedStatement ps = null;
		String strSQL = null;
		try
		{
			strSQL="UPDATE loan_approvalchangeitem  SET  nstatusid= ?  WHERE  trunc(endDate,'DD') <= trunc(sysdate,'DD') ";
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1,Constant.RecordStatus.INVALID);
			ps.executeUpdate();
			ps.close();
			ps = null;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
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
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	
	public void dealCredit(Connection conn,Timestamp tsToday) throws Exception
	{
		CreditBean biz = new CreditBean();
		biz.autoActive( tsToday );
	}
	public void dealAll() throws Exception
	{
		Timestamp tsToday = Env.getSystemDateTime();
		Connection conn = null;
		try
		{
			conn = Database.getConnection();
			annulApprove(conn);
			//Modify by leiyang 20081210
			//注释信贷授信状态更新任务
			//dealCredit(conn,tsToday);
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			throw new Exception(e.toString());
		}
		finally
		{   if(conn!=null)
			{conn.close();
			 conn=null;
			}
		}
	}
	
	public static void main(String[] args)
	{
		Connection conn = null;
		try
		{
			/*
			 */
			DailyOperation a = new DailyOperation();
			conn = Database.getConnection();
//			Timestamp tsDate = new Timestamp(103, 11, 7, 1, 1, 1, 0);
//			System.out.println(tsDate);
//			//a.dealContractForOpen(conn,1,1,tsDate);
//			a.dealContractForShutDown(conn, 1, 1, tsDate);
			a.annulApprove(conn);
		}
		catch (Exception e)
		{
		}
		finally
		{
			try
			{
				if(conn!=null)
				{
					conn.close();
					conn=null;
				}
			}
			catch(Exception e)
			{
					
			}
		}
	}
}
