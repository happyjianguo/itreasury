package com.iss.itreasury.securities.util;
import java.sql.*;
import java.util.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.settlement.util.*;
import com.iss.itreasury.securities.util.*;
import com.iss.itreasury.securities.securitiescontract.dao.*;
/**
 * @author yfan
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class DailyOperation
{
	/*
	public boolean dealContractForShutDown(Connection conn, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		PreparedStatement ps = null;
		PreparedStatement pstemp = null;
		ResultSet rs = null;
		boolean bTransCheck = true;
		long lOverDueResultCount = 0;
		long lContractFinishResultCount = 0;
		long lDiscountFinishResultCount = 0;
		Timestamp tsToday = null;
		String strSQL = null;
		java.util.Calendar calendar = Calendar.getInstance();
		try
		{
			System.out.println("");
			System.out.println("**********处理执行中合同***************");
			System.out.println("");
			strSQL = " select nLoanContractID from sett_TransGrantLoan where dtExecute = ? and nStatusID in (3,4,5,6) and nOfficeID=" + lOfficeID + " and nCurrencyID =  " + lCurrencyID;
			strSQL = strSQL + " union all ";
			strSQL = strSQL + " select nDiscountContractID from sett_TransGrantDiscount where dtExecute = ? and nStatusID in (3,4,5,6) and nOfficeID=" + lOfficeID + " and nCurrencyID =  " + lCurrencyID;
			ps = conn.prepareStatement(strSQL);
			ps.setTimestamp(1, tsDate);
			ps.setTimestamp(2, tsDate);
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
			System.out.println("**********处理结束合同：贷款合同)***************");
			System.out.println("");
			//calendar.setTime(Env.getSystemDate());
			calendar.setTime(tsDate);
			tsToday = DataFormat.getDateTime(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE), 0, 0, 0);
			strSQL = "";
			strSQL =
				" Select aa.ID,sContractCode, aa.dtEndDate, aa.nTypeID, nvl(aa.mExamineAmount,0) mAmount, "
					+ " nvl(bb.mBalance,0) mBalance, nvl(bb.mRepayment,0) mRepayment "
					+ " From "
					+ " ( "
					+ "   Select a.ID,a.sContractCode,dtEndDate,nTypeID,nvl(mExamineAmount,0) mExamineAmount "
					+ "   From loan_contractform a "
					+ "   Where ( a.nStatusID = ? Or a.nStatusID = ? Or a.nStatusID = ? Or a.nStatusID = ? Or a.nStatusID = ? ) "
					+ "   And nOfficeID = "
					+ lOfficeID
					+ "   And nCurrencyID = "
					+ lCurrencyID
					+ "   And nTypeID != "
					+ SETTConstant.SettLoanType.TX
					+ " ) aa, "
					+ " ( "
					+ "    select b.nContractID,sum(nvl(a.mOpenAmount,0)-nvl(a.mBalance,0)) mRepayment,sum(nvl(a.mBalance,0) + nvl(a.mInterest,0) + nvl(a.AL_mSuretyFee,0) + nvl(a.AL_mCommission,0) + nvl(a.AL_mOverDueInterest,0) + nvl(a.AL_mCompoundInterest,0) ) mBalance "
					+ "    from sett_subAccount a,loan_payform b,sett_Account c "
					+ "    where  AL_nLoanNoteID = b.ID and a.nAccountID = c.ID and c.nAccountTypeID != "
					+ SETTConstant.AccountType.DISCOUNT
					+ "    and a.nStatusID in ( "
					+ SETTConstant.SubAccountStatus.NORMAL
					+ " ) "
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
				double mRepayment = rs.getDouble("mRepayment");
				double mAmount = rs.getDouble("mAmount");
				long nIsCircle = rs.getLong("nTypeID");
				System.out.println("&&&&&&&&&&&tsToday=" + tsToday);
				System.out.println("&&&&&&&&&&&dtEndDate=" + dtEndDate);
				System.out.println("&&&&&&&&&&&mBalance=" + mBalance);
				System.out.println("&&&&&&&&&&&mRepayment=" + mRepayment);
				System.out.println("&&&&&&&&&&&mAmount=" + mAmount);
				System.out.println("&&&&&&&&&&&nIsCircle=" + nIsCircle);
				System.out.println("&&&&&&&&&&&lContractID=" + lContractID);
				System.out.println("&&&&&&&&&&&sContractCode=" + rs.getString("sContractCode"));
				if (nIsCircle == LOANConstant.LoanType.ZGXEDQ || nIsCircle == LOANConstant.LoanType.ZGXEZCQ)
				{
					//已经到期，并且余额为零
					System.out.println("**************************");
					if (dtEndDate != null && (DataFormat.getDateString(dtEndDate).compareTo(DataFormat.getDateString(tsToday)) <= 0))
					{
						System.out.println("***************已经到期*****" + rs.getString("sContractCode"));
						if (mBalance == 0)
						{
							System.out.println("***************并且余额为零*****" + rs.getString("sContractCode"));
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
				else
				{
					System.out.println("===========================");
					//已经到期，并且余额为零
					if (dtEndDate != null && (DataFormat.getDateString(dtEndDate).compareTo(DataFormat.getDateString(tsToday)) <= 0))
					{
						System.out.println("==============已经到期============" + rs.getString("sContractCode"));
						if (mBalance == 0)
						{
							System.out.println("==============并且余额为零============" + rs.getString("sContractCode"));
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
					if (mRepayment == mAmount && mBalance == 0)
					{
						System.out.println("==============无论是否到期，收回金额等于合同金额============" + rs.getString("sContractCode"));
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
			//System.out.println(Function.getDateString(tsToday) + " 逾期的合同数：" + lOverDueResultCount);
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			System.out.println("");
			System.out.println("**********处理结束合同：贴现合同)***************");
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
					+ SETTConstant.SettLoanType.TX
					+ " ) aa, "
					+ " ( "
					+ "    select b.nContractID,sum(nvl(a.mOpenAmount,0)-nvl(a.mBalance,0)) mRepayment,sum(nvl(a.mBalance,0)) mBalance "
					+ "    from sett_subAccount a,loan_payform b,sett_Account c "
					+ "    where  AL_nLoanNoteID = b.ID and a.nAccountID = c.ID and c.nAccountTypeID = "
					+ SETTConstant.AccountType.DISCOUNT
					+ "    and a.nStatusID in ( "
					+ SETTConstant.SubAccountStatus.NORMAL
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
					if (mBalance == 0)
					{
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
				if (mRepayment == mAmount)
				{
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
			//System.out.println(Function.getDateString(tsToday) + " 逾期的合同数：" + lOverDueResultCount);
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
		System.out.println("**********处理结束合同退出***************");
		System.out.println("");
		return bTransCheck;
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
					+ "   Select a.ID,dtEndDate,nTypeID,mExamineAmount "
					+ "   From loan_contractform a "
					+ "   Where ( a.nStatusID = ? Or a.nStatusID = ? ) "
					+ "   And a.nOfficeID = "
					+ lOfficeID
					+ "   And a.nCurrencyID = "
					+ lCurrencyID
					+ " ) aa, "
					+ " ( "
					+ "    select b.nContractID,sum(a.mOpenAmount-a.mBalance) mRepayment,sum(a.mBalance + a.mInterest + a.AL_mSuretyFee + a.AL_mCommission + a.AL_mOverDueInterest + a.AL_mCompoundInterest ) mBalance "
					+ "    from sett_subAccount a,loan_payform b,sett_Account c "
					+ "    where  AL_nLoanNoteID = b.ID and a.nAccountID = c.ID and c.nAccountTypeID != "
					+ SETTConstant.AccountType.DISCOUNT
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
						strSQL = "update loan_contractform set nStatusID = ? where ID = ? ";
						pstemp = conn.prepareStatement(strSQL);
						pstemp.setLong(1, LOANConstant.ContractStatus.OVERDUE);
						pstemp.setLong(2, lContractID);
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
			strSQL = " select ID from loan_contractform where trunc(dtEndDate,'DD') + 90 = trunc(sysdate,'DD') and nStatusID = ? ";
			//System.out.println(strSQL);
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, LOANConstant.ContractStatus.OVERDUE);
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
    */
	public boolean dealApply(Connection conn, Timestamp tsDate) throws Exception
	{
		PreparedStatement ps = null;
		boolean bTransCheck = true;
		long lApplyFinishResultCount = 0;
		Timestamp tsToday = null;
		String strSQL = null;
		java.util.Calendar calendar = Calendar.getInstance();
		try
		{
			System.out.println("");
			System.out.println("**********处理已结束申请***************");
			System.out.println("");
			//calendar.setTime(Env.getSystemDate());
			calendar.setTime(tsDate);
			tsToday = DataFormat.getDateTime(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE), 0, 0, 0);
			strSQL = "";
			strSQL = " update SEC_ApplyForm set StatusID = ? where to_char(TransactionEndDate,'YYYY-MM-DD') < ? and StatusID in (1,2,3) ";
			Log.print(strSQL);
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, SECConstant.ApplyFormStatus.COMPLETED);
			ps.setString(2, DataFormat.getDateString(tsToday));
			lApplyFinishResultCount = ps.executeUpdate();
			Log.print(" "+DataFormat.getDateString(tsToday)+" 共处理到期申请 "+lApplyFinishResultCount+" 笔。");
			ps.close();
			ps = null;			
		}
		catch (Exception e)
		{
			bTransCheck = false;
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			throw new Exception(e.toString());
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
			catch (Exception e)
			{
				throw new Exception(e.toString());
			}
		}
		System.out.println("");
		System.out.println("**********处理已结束申请***************");
		System.out.println("");
		return bTransCheck;
	}
	public void dealContract(Connection conn,Timestamp tsToday) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL="";
		long contractID=-1;
		Timestamp endTime=null;
		SecuritiesContractDao dao = new SecuritiesContractDao();
		long toEnd[]=new long[1000];
		int i=0;
		/*
		strSQL="select * from sec_applyContract where statusID="+SECConstant.ContractStatus.ACTIVE ;
		System.out.println(strSQL);
		ps=conn.prepareStatement(strSQL);
		rs=ps.executeQuery() ;
		
		while (rs.next())
		{
			 contractID=rs.getLong("ID");
			 endTime=rs.getTimestamp("TRANSACTIONENDDATE");
			 System.out.println("TransactionEndDate+"+endTime);	 
			 if (endTime==null) continue;
			 if (tsToday.after( endTime ))
			 {
			 	toEnd[i++]=contractID;
				System.out.println("toEnd:"+contractID);
			 }
		}*/
		
		;
		
		strSQL="select c.id from sec_applycontract c,(select c.id, "+
               "sum(noticeamount) noticeamount from Sec_Noticeform n,"+
               " sec_applycontract c where n.transactiontypeid = 7103 "+
               "and n.contractid = c.id and n.statusid >= 3 group by c.id) aa "+
               "where (c.amount <= aa.noticeamount and c.id = aa.id and c.statusid = 5) "+
               "or  (c.transactionenddate < sysdate and c.statusid = 5) group by c.id" ;
               
				System.out.println(strSQL);
				ps=conn.prepareStatement(strSQL);
				rs=ps.executeQuery() ;
		
				while (rs.next())
				{
					 contractID=rs.getLong("ID");
					 toEnd[i++]=contractID;
					 System.out.println("toEnd:"+contractID);
					 
				}
		
		
		
		dao.endContract( toEnd );
		if (rs!=null)
		{
			rs.close();
			rs=null;
		}
		if (ps!=null)
		{
			ps.close();
			ps=null;
		}
	}
	public void dealAll() throws Exception
	{
	    Timestamp tsToday = Env.getSystemDateTime();        
		try
		{
		    Connection conn = Database.getConnection();
		    //处理已结束申请
		    dealApply(conn, tsToday);
		    //更新合同状态
			updateContractStatus(conn);
			
			dealContract(conn,tsToday);
		    conn.close();
		    conn = null;
		}
		catch (Exception e)
		{
			throw new Exception(e.toString());
		}		
	}

	/**
	* 扫描所有的合同,将其中状态是未执行的状态改为已执行,条件是未执行状态的合同
	* 下至少有一个通知单的状态是已审核,已使用或已完成
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识  
	* @param tsDate 开/关机时间 
	* Description: 
	* ----待确定
	*/
	public boolean updateContractStatus(Connection conn) throws Exception
	{
		boolean bIsPassed = true;
		PreparedStatement ps= null;
		try
		{
			String sql = "";
			sql = " update sec_applycontract set statusid = " + SECConstant.ContractStatus.ACTIVE
				+ " where id in ( "
				+ " select distinct a.id from sec_applycontract a,sec_noticeform b "
				+ " where a.id =  b.contractid "
				+ " and a.statusid = " + SECConstant.ContractStatus.NOTACTIVE
				//+ " and a.currencyid = " + lCurrencyID
				//+ " and a.officeid = " + lOfficeID
				+ " and b.statusid in (" + SECConstant.NoticeFormStatus.CHECKED + "," + SECConstant.NoticeFormStatus.USED + "," + SECConstant.NoticeFormStatus.COMPLETED + "," + SECConstant.NoticeFormStatus.POSTED + "))";
			System.out.println("合同状态修改开始!!!!!!!!!!!!!!!");	
			System.out.println("strsql="+sql);
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			System.out.println("合同状态修改结束!!!!!!!!!!!!!!!");	
//			if (bIsPassed)
//			{
//				
//			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			bIsPassed = false;
			// 释放数据库连接等
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
		}
		finally
		{
			// 释放数据库连接等
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
		}
		return bIsPassed;
	}

	public static void main(String[] args)
	{
		try
		{
			/*
			 */
			DailyOperation a = new DailyOperation();
			Connection conn = Database.getConnection();
			Timestamp tsDate = DataFormat.getDateTime( conn );
			System.out.println(tsDate);
			//a.dealContractForOpen(conn,1,1,tsDate);
			//a.dealContractForShutDown(conn, 1, 1, tsDate);
			a.dealContract(conn,tsDate);
		}
		catch (Exception e)
		{
		}
	}
}
