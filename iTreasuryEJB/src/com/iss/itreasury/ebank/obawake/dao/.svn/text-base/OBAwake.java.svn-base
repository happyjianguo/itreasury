/*
 * Copyright (c) 2003-2005 iss.com. All Rights Reserved.
 *
 * 总体功能说明：用于取得到期提醒的合同号
 *
 * 使用注意事项：
 * 1
 * 2
 *
 * 作者：hyzeng
 * time: 2004-2-19
 */
package com.iss.itreasury.ebank.obawake.dao;
import java.sql.*;
import java.util.Collection;
import java.util.Iterator;
import java.rmi.RemoteException;
import com.iss.itreasury.util.*;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.budget.constitute.dao.Budget_PlanDAO;
import com.iss.itreasury.budget.util.BUDGETConstant;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.ebank.obawake.dataentity.*;
import com.iss.itreasury.ebank.obbudget.bizlogic.OBBudgetBiz;
import com.iss.itreasury.ebank.obsetremind.bizlogic.OB_OperationReminderBiz;
import com.iss.itreasury.ebank.obsetremind.dataentity.OB_OperationReminderInfo;
import com.iss.itreasury.evoucher.util.VOUConstant;

public class OBAwake
{
	private Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
	public OBAwake()
	{
	}

	/*
	 * @author hyzeng
	 * @time 2004-2-19
	 * @param lCurrencyID
	 * @param lOfficeID
	 * function 对网上金融系统资金管理下的
	 * 所有交易申请业务的
	 * 未复核、未签认、已完成、已拒绝
	 * 状态进行提醒
	 */
	public String getCapInstrByStatus(OBAwakeCondition info) throws Exception
	{

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		String sResult = "";

		String sTransName = ""; //业务名称
		long lTotal = 0; //笔数
		String sTransStatus = ""; //业务状态
		long lTransStatus = -1; //业务状态
		long lTransType = -1; //业务类型
		long lTempType = -1;

		try
		{
			sb.append("SELECT COUNT(id) as total,nTransType,nStatus ");
			sb.append(" FROM ob_financeInstr ");
			sb.append(" WHERE nCurrencyId = ?");
			sb.append(" AND nClientID = ?");
			sb.append(" AND nStatus IN (" + OBConstant.SettInstrStatus.SAVE); //未复核
			sb.append("," + OBConstant.SettInstrStatus.CHECK); //未签认
			sb.append("," + OBConstant.SettInstrStatus.FINISH); //已完成
			sb.append("," + OBConstant.SettInstrStatus.REFUSE); //已拒绝
			sb.append(" )");
			sb.append(" AND TO_CHAR(dtConfirm,'YYYYMMDD') = TO_CHAR(SYSDATE,'YYYYMMDD')");
			sb.append(" GROUP BY nTransType,nStatus");

			log4j.info("网上金融系统资金管理下的所有交易申请业务的“未复核”、“未签认”:");
			log4j.info("\n" + sb.toString());
			ps = info.getCon().prepareStatement(sb.toString());
			ps.setLong(1, info.getCurrencyID());
			ps.setLong(2, info.getClientID());
			rs = ps.executeQuery();
			while (rs.next())
			{
				lTransType = rs.getLong("nTransType");
				sTransName = OBConstant.SettInstrType.getName(lTransType);

				if (lTransType == OBConstant.SettInstrType.CAPTRANSFER_SELF
					|| lTransType == OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT
					|| lTransType == OBConstant.SettInstrType.CAPTRANSFER_BANKPAY)
				{
					lTempType = OBConstant.QueryInstrType.CAPTRANSFER;
				}
				else if (lTransType == OBConstant.SettInstrType.OPENFIXDEPOSIT)
				{
					lTempType = OBConstant.QueryInstrType.OPENFIXDEPOSIT;
				}
				else if (lTransType == OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER)
				{
					lTempType = OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER;
				}
				else if (lTransType == OBConstant.SettInstrType.OPENNOTIFYACCOUNT)
				{
					lTempType = OBConstant.QueryInstrType.OPENNOTIFYACCOUNT;
				}
				else if (lTransType == OBConstant.SettInstrType.NOTIFYDEPOSITDRAW)
				{
					lTempType = OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW;
				}
				else if (lTransType == OBConstant.SettInstrType.TRUSTLOANRECEIVE)
				{
					lTempType = OBConstant.QueryInstrType.TRUSTLOANRECEIVE;
				}
				else if (lTransType == OBConstant.SettInstrType.CONSIGNLOANRECEIVE)
				{
					lTempType = OBConstant.QueryInstrType.CONSIGNLOANRECEIVE;
				}
				else if (lTransType == OBConstant.SettInstrType.INTERESTFEEPAYMENT)
				{
					lTempType = OBConstant.QueryInstrType.INTERESTFEEPAYMENT;
				}

				lTotal = rs.getLong("total");
				lTransStatus = rs.getLong("nStatus");
				sTransStatus = OBConstant.SettInstrStatus.getName(lTransStatus);

				if (lTransStatus == OBConstant.SettInstrStatus.SAVE || lTransStatus == OBConstant.SettInstrStatus.CHECK)
				{
					sResult += sTransName + "现有";

					sResult += "<a href='#' onClick=parent.parent.window.location";
					sResult += "='../capital/query/q002-c.jsp?lStatus=" + lTransStatus;
					sResult += "&sStartSubmit=" + DataFormat.getDateString(info.getCon());
					sResult += "&sEndSubmit=" + DataFormat.getDateString(info.getCon());
					sResult += "&lTransType=" + lTempType + "&fromAccountType=yes'>";
					sResult += lTotal;
					sResult += "</a>";

					sResult += "笔业务处于" + sTransStatus + "&nbsp;&nbsp;&nbsp;&nbsp;";
				}
				else if (lTransStatus == OBConstant.SettInstrStatus.FINISH || lTransStatus == OBConstant.SettInstrStatus.REFUSE)
				{
					sResult += "今天，有";

					sResult += "<a href='#' onClick=parent.parent.window.location";
					sResult += "='../capital/query/q002-c.jsp?lStatus=" + lTransStatus;
					sResult += "&sStartSubmit=" + DataFormat.getDateString(info.getCon());
					sResult += "&sEndSubmit=" + DataFormat.getDateString(info.getCon());
					sResult += "&lTransType=" + lTempType + "&fromAccountType=yes'>";
					sResult += lTotal;
					sResult += "</a>";

					sResult += "笔" + sTransName + sTransStatus + "&nbsp;&nbsp;&nbsp;&nbsp;";
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
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
		}
		return sResult;
	}
	
	/*
	 * @author leiyang
	 * @time 2007/06/23
	 * @param lContractID
	 * function 定期存款到期提醒
	 * 
	 */
	public String getOpenFixdePositMsg(OBAwakeCondition info, boolean bool) throws RemoteException, Exception
	{
		String sResult = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		long id = -1;
		String sDepositNo = ""; //定期存单号
		long lCount = 0; //到期天数
		String nowDate = Env.getSystemDateString(info.getOfficeID(), info.getCurrencyID());
		
		try
		{
			sb.append(" select sa.id subAccountId, sa.af_sdepositno DepositNo, sa.af_dtEnd-to_date('"+ nowDate + "','yyyy-mm-dd') lFate");
			sb.append(" from sett_subAccount sa,sett_Account a,Client c ,sett_accountType d");
			sb.append(" where a.ID=sa.nAccountID");
			sb.append(" and a.nClientID=c.ID");
			sb.append(" and a.nAccountTypeID=d.id");
			sb.append(" and d.nAccountGroupID= ?");
			sb.append(" and a.nOfficeID= ?");
			sb.append(" and a.nCurrencyID= ?");
			sb.append(" and a.nClientID = ?");
			sb.append(" and sa.nStatusID= ?");
			if(bool == true) {
				sb.append(" AND sa.af_dtEnd-to_date('"+ nowDate + "','yyyy-mm-dd') >=0");
				sb.append(" AND sa.af_dtEnd-to_date('"+ nowDate + "','yyyy-mm-dd') <=?");
				sb.append(" ORDER BY lFate");
			}
			else {
				sb.append(" AND sa.af_dtEnd-to_date('"+ nowDate + "','yyyy-mm-dd') <0");
				sb.append(" AND sa.af_dtEnd-to_date('"+ nowDate + "','yyyy-mm-dd') >=-?");
				sb.append(" ORDER BY lFate");
			}

			log4j.info(" 定期存款到期提醒: \n" + sb.toString());
			ps = info.getCon().prepareStatement(sb.toString());
			int index = 1;
			ps.setLong(index++, SETTConstant.AccountGroupType.FIXED);
			ps.setLong(index++, info.getOfficeID());
			ps.setLong(index++, info.getCurrencyID());
			ps.setLong(index++, info.getClientID());
			ps.setLong(index++, SETTConstant.SubAccountStatus.NORMAL);
			ps.setLong(index++, info.getOperationFate());

			rs = ps.executeQuery();
			while (rs.next())
			{
				id = rs.getLong("subAccountId");
				sDepositNo = rs.getString("DepositNo");
				lCount = rs.getLong("lFate");
				sResult += "定期开立单据号:";
				
				sResult += "<a href='../capital/query/q101-c.jsp?id="+ id +"' target=_blank >";
				sResult += sDepositNo;
				sResult += "</a>";
				
				if(bool == true) {
					if(lCount == 0) {
						sResult += "将在今天到期&nbsp;&nbsp;&nbsp;&nbsp;";
					}
					else {
						sResult += "将在" + lCount + "天后到期&nbsp;&nbsp;&nbsp;&nbsp;";
					}
				}
				else {
					sResult += "已过期" + Math.abs(lCount) + "天&nbsp;&nbsp;&nbsp;&nbsp;";
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
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
		}
		return sResult;
	}

	/**
	 * @author zwsun
	 * @time 2007/06/25
	 * @param flag true: 表示到期提醒，false表示过期提醒
	 * function 通知存款支取通知提醒
	 * 
	 */	
	private String getNotifyDepositDrawMsg(OBAwakeCondition info, boolean flag) throws RemoteException, Exception{
		String sResult = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sDepositNo = ""; //存单号
		long lCount = 0; //到期天数
		String nowDate = Env.getSystemDateString(info.getOfficeID(), info.getCurrencyID());
		
		try
		{
			sb.append(" SELECT s.id, s.depositNo, trunc(s.notifydate,'dd')-to_date('"+ nowDate + "','yyyy-mm-dd') as lFate");
			sb.append(" FROM sett_notifyDepositInform s,ob_user o");
			sb.append(" WHERE s.userid = o.id");
			sb.append(" AND s.statusID = ?");
			sb.append(" AND s.officeId =?");
			sb.append(" AND (s.moduleId =?");
			sb.append(" or s.moduleId =?)");
			if(flag){
				sb.append(" AND trunc(s.notifydate,'dd')-to_date('"+ nowDate + "','yyyy-mm-dd') >=0");
				sb.append(" AND trunc(s.notifydate,'dd')-to_date('"+ nowDate + "','yyyy-mm-dd') <=?");
			}else{
				sb.append(" AND trunc(s.notifydate,'dd')-to_date('"+ nowDate + "','yyyy-mm-dd') < 0");
				sb.append(" AND trunc(s.notifydate,'dd')-to_date('"+ nowDate + "','yyyy-mm-dd') >=-?");							
			}
			sb.append(" AND o.nclientid=?");
			sb.append(" ORDER BY lFate");

			log4j.info(" 通知存款支取通知提醒: \n" + sb.toString());
			ps = info.getCon().prepareStatement(sb.toString());
			int index = 1;
			ps.setLong(index++, SETTConstant.NotifyInformStatus.SAVE);
			ps.setLong(index++, info.getOfficeID());
			ps.setLong(index++, Constant.ModuleType.EBANK);
			ps.setLong(index++, Constant.ModuleType.SETTLEMENT);
			ps.setLong(index++, info.getOperationFate());
			ps.setLong(index++, info.getClientID());

			rs = ps.executeQuery();
			
			while (rs.next())
			{	
				sDepositNo = rs.getString("depositNo");
				lCount = rs.getLong("lFate");				
				sResult += "通知支取";
				
				sResult += "<a href='../capital/notifyinform/control/c004.jsp?id="+ rs.getLong("id") +"&control=display' target=_blank >";
				sResult += sDepositNo;
				sResult += "</a>";
				
				if(lCount == 0) {
					sResult += "将在今天到期&nbsp;&nbsp;&nbsp;&nbsp;";
				}
				else  if(flag){
					sResult += "将在" + lCount + "天后到期&nbsp;&nbsp;&nbsp;&nbsp;";
				}else{
					sResult += "已过期" + (-lCount) + "天&nbsp;&nbsp;&nbsp;&nbsp;";
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
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
		}
		return sResult;		
		
	}
	
	/*
	 * @author hyzeng
	 * @time 2004-2-19
	 * @param lContractID
	 * function 定期存款到期提醒
	 * 
	 */
	public String getFixedAttermMsg(OBAwakeCondition info) throws RemoteException, Exception
	{
		String sResult = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sDepositNo = ""; //定期存单号
		Timestamp tsEndDate = null; //到期日期
		double dAmount = 0; //定期存款金额
		Timestamp tsCurrent = Env.getSystemDateTime();
		long lCount = 0; //到期天数
		try
		{

			sb.append(" SELECT sDepositNo,dtEnd,mAmount,");
			sb.append(" (SYSDATE-dtEnd) as dtCount");
			sb.append(" FROM sett_TransOpenFixedDeposit");
			sb.append(" WHERE nStatusID = 3"); //状态为已复核
			sb.append(" AND nTransactionTypeID =" + SETTConstant.TransactionType.OPENFIXEDDEPOSIT); //定期开立
			sb.append(" AND nCurrencyId = ?");
			sb.append(" AND nClientID = ?");
			sb.append(" ORDER BY sDepositNo");

			log4j.info(" 定期存款到期提醒: \n" + sb.toString());
			ps = info.getCon().prepareStatement(sb.toString());
			ps.setLong(1, info.getCurrencyID());
			ps.setLong(2, info.getClientID());

			rs = ps.executeQuery();
			while (rs.next())
			{
				tsEndDate = rs.getTimestamp("dtEnd");
				sDepositNo = rs.getString("sDepositNo");
				dAmount = rs.getDouble("mAmount");
				lCount = rs.getLong("dtCount");

				if (lCount <= 0 && lCount >= -info.getAheadAwakeDays1()[0])
				{
					sResult += "<a href='#' onClick=parent.parent.window.location";
					sResult += "='../capital/fixed/f011-c.jsp'>";
					sResult += sDepositNo;
					sResult += "</a>";
					sResult += "将于" + DataFormat.formatDate(tsEndDate) + "到期&nbsp;&nbsp;&nbsp;&nbsp;";
				}
				else if (dAmount > 0 && lCount > 0)
				{
					sResult += "<a href='#' onClick=parent.parent.window.location";
					sResult += "='../capital/fixed/f011-c.jsp'>";
					sResult += sDepositNo;
					sResult += "</a>";
					sResult += "已经到期" + lCount + "天&nbsp;&nbsp;&nbsp;&nbsp;";
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
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
		}
		return sResult;
	}
	
	/*
	 * @author jiamiao
	 * @time 2006-3-26
	 * @param lContractID
	 * function 资金计划到期提醒
	 * 
	 */
	public String getOBCapitalPlanMsg() throws RemoteException, Exception
	{
		String sResult = "";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		Timestamp startdate = null; //原开始日期
		long period = -1; //原用款计划周期
		Timestamp oldstartdate = null; //上一个开始日期
		long oldperiod = -1; //上一个用款计划周期
		Timestamp modifydate = null;//修改时间
		try
		{

			sb.append(" select * from SETT_PERIODSETTING ");

			log4j.info(" 资金计划到期提醒: \n" + sb.toString());
			conn = Database.getConnection();
			ps = conn.prepareStatement(sb.toString());
			rs = ps.executeQuery();

			while (rs.next())
			{
				period = rs.getLong("PERIOD");
				startdate = rs.getTimestamp("STARTDATE");
				oldperiod = rs.getLong("OLDPERIOD");
				oldstartdate = rs.getTimestamp("OLDSTARTDATE");
				modifydate = rs.getTimestamp("MODIFYDATE");

				if(oldperiod > -1){
					if(oldstartdate != null){
						if(period != oldperiod || !startdate.equals(oldstartdate)){
							if((Env.getSystemDateTime().getTime() - modifydate.getTime()) < 5*86400*1000)
								sResult += "资金计划已被修改!&nbsp;&nbsp;&nbsp;&nbsp;";
						}
					}
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
			if (conn != null)
			{
				conn.close();
				conn = null;
			}
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
		}
		return sResult;
	}

	/*
	 * @author hyzeng
	 * @time 2004-2-19
	 * @param lCurrencyID
	 * @param lOfficeID
	 * function 贷款到期提醒
	 * 
	 */
	public String getLoanAttermMsg(OBAwakeCondition info) throws RemoteException, Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sResult = "";
		String sCode = ""; //放款通知单编号
		String sContractCode = ""; //合同编号
		Timestamp tsEndDate = null; //放款通知单到期日期
		long lCount = 0;

		try
		{
			sb.setLength(0);
			sb.append(" SELECT a.sCode,a.dtEnd,");
			sb.append(" a.id,a.nContractID,");
			sb.append(" b.sContractCode,(a.dtEnd-SYSDATE) as dtCount");
			sb.append(" FROM loan_payform a,loan_contractform b");
			sb.append(" WHERE a.nContractID = b.ID ");
			sb.append(" AND b.nCurrencyId = ?");
			sb.append(" AND b.nBorrowClientID = ?");
			sb.append(" AND a.nStatusID=" + LOANConstant.LoanPayNoticeStatus.USED);
			sb.append(" AND (b.nStatusID=" + LOANConstant.ContractStatus.ACTIVE);
			sb.append(" OR b.nStatusID=" + LOANConstant.ContractStatus.EXTEND + ")");
			sb.append(" ORDER BY b.sContractCode,a.sCode");

			log4j.info("贷款到期提醒:\n " + sb.toString());
			ps = info.getCon().prepareStatement(sb.toString());
			ps.setLong(1, info.getCurrencyID());
			ps.setLong(2, info.getClientID());
			rs = ps.executeQuery();
			while (rs.next())
			{

				sCode = rs.getString("sCode");
				sContractCode = rs.getString("sContractCode");
				tsEndDate = rs.getTimestamp("dtEnd");
				lCount = rs.getLong("dtCount");

				if (lCount >= 0 && lCount <= info.getAheadAwakeDays1()[1])
				{
					sResult += "合同" + sContractCode + " 放款单" + sCode + "将于";
					sResult += "<a href='#' onClick=parent.parent.window.location";
					sResult += "='settnotice/s001-c.jsp?lNoticeTypeID=";
					sResult += SETTConstant.LoanNoticeType.LoanMatureNotice;
					sResult += "&lContractIDFromCtrl=" + sContractCode;
					sResult += "&lContractIDToCtrl=" + sContractCode;
					sResult += "&lPayFormIDFromCtrl=" + sCode;
					sResult += "&lContractID=" + rs.getLong("nContractID");
					sResult += "&lLoanNoteID=" + rs.getLong("id");
					sResult += "&lPayFormIDToCtrl=" + sCode + "'>";
					sResult += DataFormat.formatDate(tsEndDate);
					sResult += "</a>";
					sResult += "到期&nbsp;&nbsp;&nbsp;&nbsp;";
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
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
		}

		return sResult;
	}

	/*
	* @author hyzeng
	* @time 2004-2-19
	* @param lCurrencyID
	* @param lOfficeID
	* function 季度结息到期提醒
	* 
	*/
	public String getInterestSettlementMsg(OBAwakeCondition info) throws RemoteException, Exception
	{
		String sResult = "";

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sContractCode = ""; //合同编号
		Timestamp tsEndDate = null; //复利设置日期 
		long lCount = 0;

		try
		{

			sb.setLength(0);
			sb.append(" SELECT a.dtCompoundInterest,b.sContractCode,");
			sb.append(" (a.dtCompoundInterest-SYSDATE) as dtCount,");
			sb.append(" b.id");
			sb.append(" FROM sett_CompoundInterestSetting a,loan_contractform b");
			sb.append(" WHERE a.nOfficeID = b.nOfficeID ");
			sb.append(" AND a.nCurrencyID = b.nCurrencyID");
			sb.append(" AND a.nStatusID = 1"); //状态为“正常”
			sb.append(" AND b.nStatusId IN ");
			sb.append("(" + LOANConstant.ContractStatus.ACTIVE);
			sb.append("," + LOANConstant.ContractStatus.EXTEND);
			sb.append("," + LOANConstant.ContractStatus.OVERDUE);
			sb.append("," + LOANConstant.ContractStatus.DELAYDEBT);
			sb.append("," + LOANConstant.ContractStatus.BADDEBT + ")");
			sb.append(" AND b.nCurrencyId = ?");
			sb.append(" AND b.nBorrowClientID = ?");
			sb.append(" ORDER BY b.sContractCode");

			Log.print("季度结息到期提醒:\n " + sb.toString());
			ps = info.getCon().prepareStatement(sb.toString());
			ps.setLong(1, info.getCurrencyID());
			ps.setLong(2, info.getClientID());
			rs = ps.executeQuery();

			while (rs.next())
			{

				sContractCode = rs.getString("sContractCode");
				tsEndDate = rs.getTimestamp("dtCompoundInterest");
				lCount = rs.getLong("dtCount");

				if (lCount >= 0 && lCount <= info.getAheadAwakeDays1()[2])
				{
					sResult += "合同";
					sResult += "<a href='#' onClick=parent.parent.window.location";
					sResult += "='settnotice/s001-c.jsp?lNoticeTypeID=";
					sResult += SETTConstant.LoanNoticeType.LoanInterestNotice;
					sResult += "&lContractID=" + rs.getLong("id");
					sResult += "&lContractIDFromCtrl=" + sContractCode;
					sResult += "&lContractIDToCtrl=" + sContractCode + "'>";
					sResult += sContractCode;
					sResult += "</a>";
					sResult += " 将于" + DataFormat.getChineseDateString(tsEndDate) + " 结息&nbsp;&nbsp;&nbsp;&nbsp;";
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

		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
		}
		return sResult;
	}

	/*
	 * @author hyzeng
	 * @time 2004-2-19
	 * @param lCurrencyID
	 * @param lOfficeID
	 * function 贷款逾期催收提醒
	 * 
	 */
	public String getOverDueMsg(OBAwakeCondition info) throws RemoteException, Exception
	{
		String sResult = "";

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sContractCode = ""; //合同编号
		String sPayFormCode = ""; //放款通知单编号
		long lOverDate = 0; //贷款逾期天数
		Timestamp tsEndDate = null; //贷款逾期催收单产生日期
		long lCount = 0;

		try
		{

			sb.setLength(0);
			sb.append(" SELECT a.sContractCode,a.sPayFormCode,");
			sb.append(" c.id,c.nContractID,");
			sb.append(" a.dtInterest,(SYSDATE - c.dtEnd) as overDate,");
			sb.append(" (a.dtInterest-SYSDATE) as dtCount");
			sb.append(" FROM sett_LoanNotice a,loan_contractform b,");
			sb.append(" loan_payform c,sett_subAccount d");
			sb.append(" WHERE a.sContractCode = b.sContractCode ");
			sb.append(" AND b.id = c.nContractID");
			sb.append(" AND a.sPayFormCode = c.sCode");
			sb.append(" AND c.id = d.al_nLoanNoteID");
			sb.append(" AND a.nStatusID = 1"); //状态为“正常”
			sb.append(" AND a.nFornType = 2"); //类型为“逾期贷款催收通知书”
			sb.append(" AND b.nCurrencyId = ?");
			sb.append(" AND b.nBorrowClientID = ?");
			sb.append(" AND d.mBalance > 0");
			sb.append(" ORDER BY a.sContractCode,a.sPayFormCode");

			Log.print("贷款逾期催收提醒:\n " + sb.toString());
			ps = info.getCon().prepareStatement(sb.toString());
			ps.setLong(1, info.getCurrencyID());
			ps.setLong(2, info.getClientID());
			rs = ps.executeQuery();

			while (rs.next())
			{

				sContractCode = rs.getString("sContractCode");
				sPayFormCode = rs.getString("sPayFormCode");
				lOverDate = rs.getLong("overDate");
				tsEndDate = rs.getTimestamp("dtInterest");
				lCount = rs.getLong("dtCount");

				if (lCount <= info.getAheadAwakeDays1()[3] && lOverDate > 0)
				{
					sResult += "合同" + sContractCode + " 下的放款单" + sPayFormCode + " 已经逾期";
					sResult += "<a href='#' onClick=parent.parent.window.location";
					sResult += "='settnotice/s001-c.jsp?lNoticeTypeID=";
					sResult += SETTConstant.LoanNoticeType.LoanDunNotice;
					sResult += "&lContractID=" + rs.getLong("nContractID");
					sResult += "&lLoanNoteID=" + rs.getLong("id");
					sResult += "&lContractIDFromCtrl=" + sContractCode;
					sResult += "&lContractIDToCtrl=" + sContractCode;
					sResult += "&lPayFormIDFromCtrl=" + sPayFormCode;
					sResult += "&lPayFormIDToCtrl=" + sPayFormCode + "'>";
					sResult += lOverDate;
					sResult += "</a>";
					sResult += "天&nbsp;&nbsp;&nbsp;&nbsp;";
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

		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
		}
		return sResult;
	}

	//Modify by leiyang date 2007/06/23
	/*
	 * @author hyzeng
	 * @time 2003-12-11
	 * @param 
	 * function 
	 */
	public void getAllAwakeContract() throws RemoteException, Exception
	{
		String sResult = "";

		Connection conn = null;
		//PreparedStatement ps = null;
		//ResultSet rs = null;
		//StringBuffer sb = new StringBuffer();

		long lCurrencyID = -1;
		long lClientID = -1;
		long lUserID = -1;
		long lOfficeID = -1;
		String sCurrencyID = "";
		String sClientID = "";
		String sUserID = "";
		String sOfficeID = "";
		OBAwakeCondition info = new OBAwakeCondition();

		try
		{
			sCurrencyID = (String) OBAwakeCondition.AwakeMSG.get("lCurrencyID");
			sClientID = (String) OBAwakeCondition.AwakeMSG.get("lClientID");
			sUserID = (String) OBAwakeCondition.AwakeMSG.get("lUserID");
			sOfficeID = (String) OBAwakeCondition.AwakeMSG.get("lOfficeID");
			if (sCurrencyID != null && !sCurrencyID.equals(""))
			{
				lCurrencyID = Long.parseLong(sCurrencyID);
			}
			if (sClientID != null && !sClientID.equals(""))
			{
				lClientID = Long.parseLong(sClientID);
			}
			if (sClientID != null && !sClientID.equals(""))
			{
			    lUserID = Long.parseLong(sUserID);
			}
			if (sClientID != null && !sClientID.equals(""))
			{
			    lOfficeID = Long.parseLong(sOfficeID);
			}

			//info.setCon(conn);
			info.setClientID(lClientID);
			info.setCurrencyID(lCurrencyID);
			info.setUserID(lUserID);
			info.setOfficeID(lOfficeID);
			
			OB_OperationReminderBiz obBiz = new OB_OperationReminderBiz();
			Collection coll = obBiz.findOperationReminder(info);
			
			conn = Database.getConnection();
			info.setCon(conn);
			
			if(coll != null) {
				Iterator it = coll.iterator();
				while(it.hasNext()) {
					OB_OperationReminderInfo orInfo = (OB_OperationReminderInfo) it.next();
					long operationId = orInfo.getOperationId();
					long operationFate = orInfo.getOperationFate();
					
					switch((int)operationId) {
						case (int)OBConstant.OperationReminderType.OPENFIXDEPOSIT :
							//定期存款到期提醒
							//Modify by leiyang date 2007/06/27
							if(operationFate != -1){
								info.setOperationFate(operationFate);
								sResult += getOpenFixdePositMsg(info, true);
							}
							break;
						case (int)OBConstant.OperationReminderType.NOTIFYDEPOSITDRAW :
							//通知支取到期提醒，Added by zwsun, 2007-6-25
							if(operationFate != -1){
								info.setOperationFate(operationFate);
								sResult += getNotifyDepositDrawMsg(info, true);
							}
							break;
						case (int)OBConstant.OperationReminderType.CAPTRANSFER :
							//对自动任务失败的提醒
							if(operationFate != -1){
								OBBudgetBiz biz = new OBBudgetBiz();
								long count = biz.getAllFailedDealCount(lOfficeID,lCurrencyID,lClientID);
								if(count > 0){
									sResult = sResult + " 共有<A href='../remindAutoTaskFail.jsp' target=_blank>&nbsp;"
											+ count +"&nbsp;</A>笔自动任务执行失败&nbsp;&nbsp;&nbsp;&nbsp;";
								}
							}
							break;
						//Modify by leiyang date 2007/06/27
						case (int)OBConstant.OperationReminderType.UNOPENFIXDEPOSIT :
							if(operationFate != -1){
								info.setOperationFate(operationFate);
								sResult += getOpenFixdePositMsg(info, false);
							}
							break;
						case (int)OBConstant.OperationReminderType.UNNOTIFYDEPOSITDRAW :
							//Added by zwsun, 2007/6/28, 通知支取过期提醒
							if(operationFate != -1){
								info.setOperationFate(operationFate);
								sResult += getNotifyDepositDrawMsg(info, false);
							}
							break;
					}
				}
			}
			
			
			
			//对网上银行打印申请的提醒
			sResult += getrefuseprintapplyByStatus(info);
			
			// 对网上金融系统资金管理下的业务进行状态提醒
			//sResult += getCapInstrByStatus(info);

			
			//定期存款到期提醒
			//sResult += getFixedAttermMsg(info);

			//贷款到期提醒 
			/*sResult += getLoanAttermMsg(info);

			//季度结息到期提醒
			sResult += getInterestSettlementMsg(info);

			//贷款逾期催收提醒
			sResult += getOverDueMsg(info);
			
			//预算提醒
			sResult += getAwakeApprove(info);//待审批的业务提醒
            sResult += getAwakeOfReceive(info);//待接收的业务提醒
            sResult += getAwakeFlexibleWarning(info);//超过预警比例的预警提醒
            sResult += getAwakeRigidWarning(info);//超过柔性比例的预警提醒
            
            //资金计划到期提醒
            sResult += getOBCapitalPlanMsg();
            */

			String strKey = String.valueOf(info.getClientID()) + String.valueOf(info.getCurrencyID()) + String.valueOf(info.getUserID()) + String.valueOf(info.getOfficeID());
			OBAwakeCondition.AwakeMSG.put(strKey, sResult);
			log4j.info("strKey:" + strKey);

			if (conn != null)
			{
				conn.close();
				conn = null;
			}
		}
		catch (RemoteException e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		finally
		{
			try
			{
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				Log.print(e.toString());
				throw new Exception(e.getMessage());
			}
		}
	}

	/**
     * 被拒绝的打印申请
     * @param info
     * @return
     * @throws Exception
     * add by zyyao 2007-8-3
     */
    public String getrefuseprintapplyByStatus(OBAwakeCondition info)throws Exception
    {
		String sResult = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		String nowDate = Env.getSystemDateString(info.getOfficeID(), info.getCurrencyID());
		
		try
		{
			strSQL.append(" select a.* from (select max(e.id) id, e.nprintcontentid, e.nprintcontentno, e.ntypeid  from ebank_printapply e where ");
			strSQL.append(" e.nofficeid = ? and e.ncurrency = ? and e.nclientid = ? and e.ninputuserid = ?  and e.IsEbank  = 1 ");
			strSQL.append(" group by e.nprintcontentid, e.nprintcontentno, e.ntypeid ) a, (select t.id ids from ebank_printapply t where t.nstatusid=? ) b ");
			strSQL.append(" where a.id = b.ids order by a.nprintcontentid ");
			log4j.info(" 被拒绝的打印申请: \n" + strSQL.toString());
			ps = info.getCon().prepareStatement(strSQL.toString());
			int index = 1;
			ps.setLong(index++, info.getOfficeID());
			ps.setLong(index++, info.getCurrencyID());
			ps.setLong(index++, info.getClientID());
			ps.setLong(index++, info.getUserID());
			ps.setLong(index++, VOUConstant.VoucherStatus.REFUSE);
			
			System.out.println("info.getClientID()========="+info.getClientID());
			System.out.println("info.getUserID()==========="+info.getUserID());
			
			rs = ps.executeQuery();
			while (rs.next())  
			{
				sResult += "打印申请";
				//<A href="/NASApp/iTreasury-ebank/print/control/c012_P.jsp?strSuccessPageURL=../view/v013_P.jsp&nextAction=view&strFailPageURL=../view/v012_P.jsp&strAction=toModify&payaccountno=06-01-0001-01&TransactionTypeID=2&TransNo=20060702060100061&lID=20164&_pageLoaderKey=-4574928153930518944">20060702060100061</a>
				sResult += "<A href='/NASApp/iTreasury-ebank/print/control/c012_P.jsp?strSuccessPageURL=../view/v014_P.jsp&nextAction=view&strFailPageURL=../view/v012_P.jsp&strAction=toModify&TransactionTypeID="+ rs.getLong("ntypeid") +"&TransNo="+ rs.getLong("nprintcontentno") +"&lID="+ rs.getLong("nprintcontentid") +"&control=display' target=_blank >";
				sResult += rs.getString("nprintcontentno");
				sResult += "</a>";
				sResult += "被拒绝&nbsp;&nbsp;&nbsp;&nbsp;";
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
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
		}
		
		return sResult;
	}
	
	/**
     * 得到待审批的业务提醒
     * 查询预算表中下一级审核人为当前单位的预算
     * @param info
     * @return
     * @throws Exception
     */
    public String getAwakeApprove(OBAwakeCondition info)throws Exception
    {
        String sResult = " ";
        Budget_PlanDAO dao = new Budget_PlanDAO();
        Collection c = dao.findUnCheckBudget(info.getClientID(),info.getUserID(),info.getOfficeID(),info.getCurrencyID());
        
        if(c != null && c.size()>0){
    		sResult="共有"+ c.size() +"笔待审批的业务  ";
    	}
        
        return sResult;
    }
    
    /**
     * 得到待接收的业务提醒
     * @param info
     * @return
     * @throws Exception
     */
    public String getAwakeOfReceive(OBAwakeCondition info)throws Exception
    {
        String sResult = " ";
        Budget_PlanDAO dao = new Budget_PlanDAO();
        Collection c = dao.findLowerClient(info.getClientID(),BUDGETConstant.ConstituteStatus.COMMIT,info.getOfficeID(),info.getCurrencyID());
        if(c != null && c.size()>0){
    		sResult="共有"+ c.size() +"笔待接收的业务  ";
    	}
        
        return sResult;
    }
    
    /**
     * 得到超过预警比例的预警提醒，针对刚性控制
     * @param info,当前办事处币种下的体系下的所有当前客户有权限的项目的检查
     * 以当前办事处ID和币种ID作为条件(用户ID是否也应该做为条件？)，查询预算体系表、预算项目模板表、预算执行情况汇总表查询项目的记录
     * 状态为目前默认正常都为1
     * 根据查询出来的实际执行数/预算数得到的值和预警比例比较，如大于预警比例则提示
     * @return
     * @throws Exception
     */
    public String getAwakeFlexibleWarning(OBAwakeCondition info)throws Exception
    {
    	PreparedStatement ps = null;
        StringBuffer sbSQL = null;
        ResultSet rs = null;
        String sResult = " ";
        String itemName="";//项目名称
        double WarnScale;//刚性比例
        double BudgetAmount=0.0;//预算数
        double ExecuteAmount=0.0;//实际执行数
        double lastDouble=0.0;//除后得到的数
        try{           
            sbSQL = new StringBuffer();
            sbSQL.append(" select a.itemName,c.warnScale,b.BudgetAmount,b.ExecuteAmount");
            sbSQL.append(" from Budget_Templet a,Budget_ItemSum b,Budget_System c");
            sbSQL.append(" WHERE c.id=a.budgetSystemID");
            sbSQL.append(" and a.budgetSystemID= b.BudgetSystemID");
            sbSQL.append(" and a.id=b.ItemID and a.StatusID=1");
            sbSQL.append(" and a.budgetType = "+BUDGETConstant.BudgetControlType.RIGID+"");
            sbSQL.append(" and a.controlType="+Constant.YesOrNo.YES+"");
            sbSQL.append(" and a.StatusID="+Constant.RecordStatus.VALID+"");
            sbSQL.append(" and b.StatusID = "+Constant.RecordStatus.VALID+"");
            sbSQL.append(" and c.StatusID="+Constant.RecordStatus.VALID+""); 
            sbSQL.append(" and c.OfficeID = ?");
            sbSQL.append(" and c.CurrencyID=?");
            sbSQL.append(" and b.ClientID = ?");
            sbSQL.append(" ORDER BY a.ID");
            ps = info.getCon().prepareStatement(sbSQL.toString());
			ps.setLong(1, info.getOfficeID());
			ps.setLong(2, info.getCurrencyID());
			ps.setLong(3, info.getClientID());
			
            rs = ps.executeQuery();        
            while (rs.next()){
            	itemName=rs.getString(1);
            	WarnScale=rs.getDouble(2);
            	BudgetAmount=rs.getDouble(3);
            	ExecuteAmount=rs.getDouble(4);
            	//预警比例如果为0，不做比较
            	if(WarnScale!=0){
            		if(BudgetAmount!=0.0 && BudgetAmount!=0){//判断预算数是否为0
            			lastDouble=ExecuteAmount/BudgetAmount;     //得到实际执行数/预算数得到的值               		
            			if(lastDouble>WarnScale){//除后得到的值如果大于预警比例，则提示
                			sResult+="项目"+itemName+"超出预警预算  ";            			
                		}            			
            		}           		
            	}
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }finally{
            if (rs != null){
				rs.close();
				rs = null;
			}
			if (ps != null){
				ps.close();
				ps = null;
			}
        }
        return sResult;
    }
    
    /**
     * 得到超过柔性比例的预警提醒，针对柔性控制
     * @param info,当前办事处币种下的体系下的所有当前客户有权限的项目的检查
     * 以当前办事处ID和币种ID作为条件(用户ID是否也应该做为条件？)，查询预算体系表、预算项目模板表、预算执行情况汇总表查询项目的记录
     * 状态为目前默认正常都为1
     * 根据查询出来的实际执行数/预算数得到的值和柔性比例比较，如大于柔性比例则提示
     * @return
     * @throws Exception
     */
    
    public String getAwakeRigidWarning(OBAwakeCondition info)throws Exception
    {
    	PreparedStatement ps = null;
        StringBuffer sbSQL = null;
        ResultSet rs = null;
        String sResult = " ";
        String itemName="";//项目名称
        double SuppleScale;//柔性比例
        double BudgetAmount=0.0;//预算数
        double ExecuteAmount=0.0;//实际执行数
        double lastDouble=0.0;//除后得到的数
        try{        
            sbSQL = new StringBuffer();
            sbSQL.append(" select a.itemName,a.SuppleScale,b.BudgetAmount,b.ExecuteAmount");
            sbSQL.append(" from Budget_Templet a,Budget_ItemSum b,Budget_System c");
            sbSQL.append(" WHERE c.id=a.budgetSystemID");
            sbSQL.append(" and a.budgetSystemID= b.BudgetSystemID");
            sbSQL.append(" and a.id=b.ItemID");
            sbSQL.append(" and a.budgetType = "+BUDGETConstant.BudgetControlType.FLEXIBLE+"");
            sbSQL.append(" and a.controlType="+Constant.YesOrNo.YES+"");
            sbSQL.append(" and a.StatusID="+Constant.RecordStatus.VALID+"");
            sbSQL.append(" and b.StatusID="+Constant.RecordStatus.VALID+"");
            sbSQL.append(" and c.StatusID="+Constant.RecordStatus.VALID+"");
            sbSQL.append(" and c.OfficeID = ? ");
            sbSQL.append(" and c.CurrencyID =  ?");
            sbSQL.append(" and b.ClientID = ?");
            sbSQL.append(" ORDER BY a.ID");
            ps = info.getCon().prepareStatement(sbSQL.toString());
			ps.setLong(1, info.getOfficeID());
			ps.setLong(2, info.getCurrencyID());
			ps.setLong(3, info.getClientID());
            rs = ps.executeQuery();        
            while (rs.next()){
            	itemName=rs.getString(1);
            	SuppleScale=rs.getDouble(2);
            	BudgetAmount=rs.getDouble(3);
            	ExecuteAmount=rs.getDouble(4);  
            	//柔性比例如果为0，不比较
            	if(SuppleScale!=0){
            		if(BudgetAmount!=0.0 && BudgetAmount!=0){//判断预算数是否为0
            			lastDouble=ExecuteAmount/BudgetAmount;     //得到实际执行数/预算数得到的值  
            			if(lastDouble>SuppleScale){//除后得到的值如果大于柔性比例，则提示
            				sResult+="项目"+itemName+"超出柔性预算  ";            			
            			}
            		}
            	}
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }finally{
            if (rs != null){
				rs.close();
				rs = null;
			}
			if (ps != null){
				ps.close();
				ps = null;
			}
        }
        return sResult;
    }
	
	/*
	 * @author hyzeng
	 * @time 2003-12-11
	 * @param args
	 * function
	 */
	public static void main(String[] args)
	{
		String sss = "";
		OBAwake a = new OBAwake();

		OBAwakeCondition.AwakeMSG.put("lCurrencyID", String.valueOf(1));
		OBAwakeCondition.AwakeMSG.put("lClientID", String.valueOf(1));

		try
		{
			a.getAllAwakeContract();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
