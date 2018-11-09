/*
 * Created on 2003-11-7
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.enddayprocess.process;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.closesystem.CloseSystemMain;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.account.bizlogic.AccountBean;
import com.iss.itreasury.settlement.account.bizlogic.AccountOperation;
import com.iss.itreasury.settlement.account.dao.Sett_AccountDAO;
import com.iss.itreasury.settlement.account.dao.Sett_SubAccountDAO;
import com.iss.itreasury.settlement.account.dataentity.SubAccountAssemblerInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountFixedInfo;
import com.iss.itreasury.settlement.account.dataentity.TransAccountDetailInfo;
import com.iss.itreasury.settlement.accountbook.bizlogic.AccountBookEJB;
import com.iss.itreasury.settlement.accountbook.bizlogic.AccountBookOperation;
import com.iss.itreasury.settlement.bankinstruction.BankInstructionFactory;
import com.iss.itreasury.settlement.bankinstruction.IBankInstruction;
import com.iss.itreasury.settlement.bankinstruction.entity.CreateInstructionParam;
import com.iss.itreasury.settlement.enddayprocess.resultinfo.AccountInfo;
import com.iss.itreasury.settlement.generalledger.bizlogic.GeneralLedgerBean;
import com.iss.itreasury.settlement.generalledger.dataentity.GenerateGLEntryParam;
import com.iss.itreasury.settlement.interest.bizlogic.InterestOperation;
import com.iss.itreasury.settlement.interest.dataentity.InterestsInfo;
import com.iss.itreasury.settlement.logger.bizlogic.OpenCloseLogBiz;
import com.iss.itreasury.settlement.logger.bizlogic.OpenCloseLogFactory;
import com.iss.itreasury.settlement.setting.bizlogic.SettInterestRateBiz;
import com.iss.itreasury.settlement.setting.dataentity.InterestRateInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dao.Sett_TransFixedContinueDAO;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedContinueInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.SessionMng;
/**
 * @author yychen
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EndDayProcess extends SettlementDAO
{
	public EndDayProcess()
	{
	}
	/**
	* 校验是否存在没有完成的交易（没有复核、没有确认）
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识  
	* @param tsDate 开/关机时间 
	* Description:  
	*/
	public boolean checkUnCompleteTransaction(Connection conn, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lTransTypeID = -1;
		long lTransId = -1;
		String strTransNo = "";
		boolean bRtn = true;
		boolean bTemp = true;
		long openCloseId = -1;
		OpenCloseLogBiz ocLog =  null;
		try
		{
			ocLog =  OpenCloseLogFactory.getInstance(lOfficeID,lCurrencyID,false);
			
			Log.print("***进入方法：校验是否存在没有完成的交易（没有复核、没有确认;暂存交易且执行日是开机日），add by ForestMing***");

			//conn = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();

			//所有的业务，校验是否有，暂存且执行日是开机日的交易
			sbSQL = null;
			sbSQL = new StringBuffer();
			sbSQL.append(" select TransactionTypeID,count(*) count ");
			sbSQL.append(" from sett_vtransaction ");
			sbSQL.append(" where StatusID=" + SETTConstant.TransactionStatus.TEMPSAVE);
			if (lOfficeID > 0)
			{
				sbSQL.append(" and OfficeID=" + lOfficeID);
			}
			if (lCurrencyID > 0)
			{
				sbSQL.append(" and CurrencyID=" + lCurrencyID);
			}
			if (tsDate != null)
			{
				sbSQL.append(" and Execute=to_date('" + DataFormat.getDateString(tsDate)+"','yyyy-mm-dd')");
			}

			sbSQL.append(" group by TransactionTypeID");
			Log.print("SQL = " + sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				lTransTypeID = rs.getLong("TransactionTypeID");
				long lCount = rs.getLong("count");
				if (lCount > 0)
				{
					bRtn = false;
					bTemp = false;
					CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, Constant.ModuleType.SETTLEMENT, SETTConstant.TransactionType.getName(lTransTypeID) + "中有" + lCount + "笔执行日是今天的暂存单据没有处理");
				}
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			
			//Modify by leiyang date 2007/06/28
			//添加所有的业务，校验是否有，暂存且执行日是开机日的交易的日志信息
			if(bTemp == false) {
				if(openCloseId == -1) {
					openCloseId = ocLog.addCloseLogReturnId(SETTConstant.CloseSystemLogType.BUSINESS,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"业务校验失败");
				}
				
				sbSQL = null;
				sbSQL = new StringBuffer();
				sbSQL.append(" select * from sett_vtransaction");
				sbSQL.append(" where StatusID=" + SETTConstant.TransactionStatus.TEMPSAVE);
				if (lOfficeID > 0)
				{
					sbSQL.append(" and OfficeID=" + lOfficeID);
				}
				if (lCurrencyID > 0)
				{
					sbSQL.append(" and CurrencyID=" + lCurrencyID);
				}
				if (tsDate != null)
				{
					sbSQL.append(" and Execute=to_date('" + DataFormat.getDateString(tsDate)+"','yyyy-mm-dd')");
				}
	
				Log.print("SQL = " + sbSQL.toString());
				ps = conn.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				while (rs != null && rs.next())
				{
					lTransId = rs.getLong("Id");
					lTransTypeID = rs.getLong("TransactionTypeID");
					strTransNo = rs.getString("TransNo");
					ocLog.addCloseDetailLog(openCloseId, lTransTypeID, lTransId, strTransNo, SETTConstant.CloseSystemLogType.BUSINESS);
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
			}
			
			bTemp = true;

			//所有的业务，校验是否有已保存，但没复核的单据
			sbSQL = null;
			sbSQL = new StringBuffer();
			sbSQL.append(" select distinct TransactionTypeID,TransNo ");
			sbSQL.append(" from  sett_vtransaction ");
			sbSQL.append(" where StatusID=" + SETTConstant.TransactionStatus.SAVE);
			if (lOfficeID > 0)
			{
				sbSQL.append(" and OfficeID=" + lOfficeID);
			}
			if (lCurrencyID > 0)
			{
				sbSQL.append(" and CurrencyID=" + lCurrencyID);
			}
			if (tsDate != null)
			{
				sbSQL.append(" and Execute=to_date('" + DataFormat.getDateString(tsDate)+"','yyyy-mm-dd')");
			}

			
			sbSQL.append(" order by TransactionTypeID,TransNo");
			Log.print("SQL = " + sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				bRtn = false;
				bTemp = false;
				lTransTypeID = rs.getLong("TransactionTypeID");
				strTransNo = rs.getString("TransNo");

				CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, Constant.ModuleType.SETTLEMENT, SETTConstant.TransactionType.getName(lTransTypeID) + "中交易号为" + strTransNo + "的单据没有复核");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			
			//Modify by leiyang date 2007/06/28
			//添加所有的业务，校验是否有已保存，但没复核的单据的日志信息
			if(bTemp == false) {
				if(openCloseId == -1) {
					openCloseId = ocLog.addCloseLogReturnId(SETTConstant.CloseSystemLogType.BUSINESS,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"业务校验失败");
				}
				
				sbSQL = null;
				sbSQL = new StringBuffer();
				sbSQL.append(" select * from sett_vtransaction");
				sbSQL.append(" where StatusID=" + SETTConstant.TransactionStatus.SAVE);
				if (lOfficeID > 0)
				{
					sbSQL.append(" and OfficeID=" + lOfficeID);
				}
				if (lCurrencyID > 0)
				{
					sbSQL.append(" and CurrencyID=" + lCurrencyID);
				}
				if (tsDate != null)
				{
					sbSQL.append(" and Execute=to_date('" + DataFormat.getDateString(tsDate)+"','yyyy-mm-dd')");
				}
				
				Log.print("SQL = " + sbSQL.toString());
				ps = conn.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				while (rs != null && rs.next())
				{
					lTransId = rs.getLong("Id");
					lTransTypeID = rs.getLong("TransactionTypeID");
					strTransNo = rs.getString("TransNo");
					ocLog.addCloseDetailLog(openCloseId, lTransTypeID, lTransId, strTransNo, SETTConstant.CloseSystemLogType.BUSINESS);
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
			}

			bTemp = true;
			
			//所有的业务，校验是否有审批中的单据
			sbSQL = null;
			sbSQL = new StringBuffer();
			sbSQL.append(" select distinct TransactionTypeID,TransNo ");
			sbSQL.append(" from  sett_vtransaction ");
			sbSQL.append(" where StatusID=" + SETTConstant.TransactionStatus.APPROVALING);
			if (lOfficeID > 0)
			{
				sbSQL.append(" and OfficeID=" + lOfficeID);
			}
			if (lCurrencyID > 0)
			{
				sbSQL.append(" and CurrencyID=" + lCurrencyID);
			}
			if (tsDate != null)
			{
				sbSQL.append(" and Execute=to_date('" + DataFormat.getDateString(tsDate)+"','yyyy-mm-dd')");
			}

			sbSQL.append(" order by TransactionTypeID,TransNo");
			Log.print("SQL = " + sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				bRtn = false;
				bTemp = false;
				lTransTypeID = rs.getLong("TransactionTypeID");
				strTransNo = rs.getString("TransNo");

				CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, Constant.ModuleType.SETTLEMENT, SETTConstant.TransactionType.getName(lTransTypeID) + "中交易号为" + strTransNo + "的单据没有审批");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			
			//Modify by leiyang date 2007/06/28
			//添加所有的业务，校验是否有审批中的单据的日志信息
			if(bTemp == false) {
				if(openCloseId == -1) {
					openCloseId = ocLog.addCloseLogReturnId(SETTConstant.CloseSystemLogType.BUSINESS,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"业务校验失败");
				}
				
				sbSQL = null;
				sbSQL = new StringBuffer();
				sbSQL.append(" select * from sett_vtransaction");
				sbSQL.append(" where StatusID=" + SETTConstant.TransactionStatus.APPROVALING);
				if (lOfficeID > 0)
				{
					sbSQL.append(" and OfficeID=" + lOfficeID);
				}
				if (lCurrencyID > 0)
				{
					sbSQL.append(" and CurrencyID=" + lCurrencyID);
				}
				if (tsDate != null)
				{
					sbSQL.append(" and Execute=to_date('" + DataFormat.getDateString(tsDate)+"','yyyy-mm-dd')");
				}
	
				Log.print("SQL = " + sbSQL.toString());
				ps = conn.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				while (rs != null && rs.next())
				{
					lTransId = rs.getLong("Id");
					lTransTypeID = rs.getLong("TransactionTypeID");
					strTransNo = rs.getString("TransNo");
					ocLog.addCloseDetailLog(openCloseId, lTransTypeID, lTransId, strTransNo, SETTConstant.CloseSystemLogType.BUSINESS);
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
			}
			
			bTemp = true;
			
			//所有的业务，校验是否有已审批，未复核的单据
			sbSQL = null;
			sbSQL = new StringBuffer();
			sbSQL.append(" select distinct TransactionTypeID,TransNo ");
			sbSQL.append(" from  sett_vtransaction ");
			sbSQL.append(" where StatusID=" + SETTConstant.TransactionStatus.APPROVALED);
			if (lOfficeID > 0)
			{
				sbSQL.append(" and OfficeID=" + lOfficeID);
			}
			if (lCurrencyID > 0)
			{
				sbSQL.append(" and CurrencyID=" + lCurrencyID);
			}
			if (tsDate != null)
			{
				sbSQL.append(" and Execute=to_date('" + DataFormat.getDateString(tsDate)+"','yyyy-mm-dd')");
			}

			sbSQL.append(" order by TransactionTypeID,TransNo");
			Log.print("SQL = " + sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				bRtn = false;
				bTemp = false;
				lTransTypeID = rs.getLong("TransactionTypeID");
				strTransNo = rs.getString("TransNo");

				CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, Constant.ModuleType.SETTLEMENT, SETTConstant.TransactionType.getName(lTransTypeID) + "中交易号为" + strTransNo + "的单据没有复核");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			
			
			//Modify by leiyang date 2007/06/28
			//添加所有的业务，校验是否有已审批，未复核的单据的日志信息
			if(bTemp == false) {
				if(openCloseId == -1) {
					openCloseId = ocLog.addCloseLogReturnId(SETTConstant.CloseSystemLogType.BUSINESS,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"业务校验失败");
				}
				sbSQL = null;
				sbSQL = new StringBuffer();
				sbSQL.append(" select * from sett_vtransaction");
				sbSQL.append(" where StatusID=" + SETTConstant.TransactionStatus.APPROVALED);
				if (lOfficeID > 0)
				{
					sbSQL.append(" and OfficeID=" + lOfficeID);
				}
				if (lCurrencyID > 0)
				{
					sbSQL.append(" and CurrencyID=" + lCurrencyID);
				}
				if (tsDate != null)
				{
					sbSQL.append(" and Execute=to_date('" + DataFormat.getDateString(tsDate)+"','yyyy-mm-dd')");
				}
				Log.print("SQL = " + sbSQL.toString());
				ps = conn.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				while (rs != null && rs.next())
				{
					lTransId = rs.getLong("Id");
					lTransTypeID = rs.getLong("TransactionTypeID");
					strTransNo = rs.getString("TransNo");
					ocLog.addCloseDetailLog(openCloseId, lTransTypeID, lTransId, strTransNo, SETTConstant.CloseSystemLogType.BUSINESS);
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
			}
			
			bTemp = true;

			//对于一付多收\多笔贷款收回业务，还要校验是否有已复核，但没勾账的单据
			sbSQL = null;
			sbSQL = new StringBuffer();
			sbSQL.append(" select distinct TransactionTypeID,TransNo ");
			sbSQL.append(" from  sett_vtransaction ");
			sbSQL.append(" where StatusID=" + SETTConstant.TransactionStatus.CHECK);
			sbSQL.append(" and TransactionTypeID in (" + SETTConstant.TransactionType.ONETOMULTI+","+SETTConstant.TransactionType.MULTILOANRECEIVE+")");
			if (lOfficeID > 0)
			{
				sbSQL.append(" and OfficeID=" + lOfficeID);
			}
			if (lCurrencyID > 0)
			{
				sbSQL.append(" and CurrencyID=" + lCurrencyID);
			}
			if (tsDate != null)
			{
				sbSQL.append(" and Execute=to_date('" + DataFormat.getDateString(tsDate)+"','yyyy-mm-dd')");
			}

			sbSQL.append(" order by TransactionTypeID,TransNo");
			Log.print("SQL = " + sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				bRtn = false;
				bTemp = false;
				lTransTypeID = rs.getLong("TransactionTypeID");
				strTransNo = rs.getString("TransNo");

				CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, Constant.ModuleType.SETTLEMENT, SETTConstant.TransactionType.getName(lTransTypeID) + "中交易号为" + strTransNo + "的单据没有勾账");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			
			//Modify by leiyang date 2007/06/28
			//添加对于一付多收\多笔贷款收回业务，还要校验是否有已复核，但没勾账的单据的日志信息
			if(bTemp == false) {
				if(openCloseId == -1) {
					openCloseId = ocLog.addCloseLogReturnId(SETTConstant.CloseSystemLogType.BUSINESS,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"业务校验失败");
				}
				sbSQL = null;
				sbSQL = new StringBuffer();
				sbSQL.append(" select * from sett_vtransaction");
				sbSQL.append(" where StatusID=" + SETTConstant.TransactionStatus.CHECK);
				sbSQL.append(" and TransactionTypeID in (" + SETTConstant.TransactionType.ONETOMULTI+","+SETTConstant.TransactionType.MULTILOANRECEIVE+")");
				if (lOfficeID > 0)
				{
					sbSQL.append(" and OfficeID=" + lOfficeID);
				}
				if (lCurrencyID > 0)
				{
					sbSQL.append(" and CurrencyID=" + lCurrencyID);
				}
				if (tsDate != null)
				{
					sbSQL.append(" and Execute=to_date('" + DataFormat.getDateString(tsDate)+"','yyyy-mm-dd')");
				}
				Log.print("SQL = " + sbSQL.toString());
				ps = conn.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				while (rs != null && rs.next())
				{
					lTransId = rs.getLong("Id");
					lTransTypeID = rs.getLong("TransactionTypeID");
					strTransNo = rs.getString("TransNo");
					ocLog.addCloseDetailLog(openCloseId, lTransTypeID, lTransId, strTransNo, SETTConstant.CloseSystemLogType.BUSINESS);
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
			}
			
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
			e.printStackTrace();throw new RemoteException("remote exception : " + e.toString());
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
				e.printStackTrace();throw new RemoteException("remote exception : " + e.toString());
			}
		}
		return bRtn;
	}
	/**
	* 检查其他办事处是否存在没有完成的交易（未复核、未确认等）
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识  
	* @param tsDate 开/关机时间 
	* Description:  
	*/
	public boolean checkOtherOfficeUnCompleteTransaction(Connection conn, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		return true;
	}
	/**
	* 校验是否存在不合法的会计科目
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识  
	* @param tsDate 开/关机时间 
	* Description:  
	*/
	public boolean checkUnAvailableGL(Connection conn, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		return true;
	}
	/**
	* 校验会计分录是否平衡
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识  
	* @param tsDate 开/关机时间 
	* Description:  
	*/
	public boolean checkGLBalance(Connection conn, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		return true;
	}
	/**
	* 校验对账单（目前没有）/校验累积未复核金额是否为0。
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识  
	* @param tsDate 开/关机时间 
	* Description:  
	*/
	public boolean checkAccountBalance(Connection conn, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		return true;
		/*
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strAccountNo = "";
		boolean bRtn = true;
		try
		{
			Log.print("***进入方法：校验对账单（目前没有）/校验累积未复核金额是否为0，add by ForestMing,20040401***");

			conn = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();

			sbSQL = null;
			sbSQL = new StringBuffer();
			
			sbSQL.append(" select a.sAccountNo ");
			sbSQL.append(" from sett_SubAccount sa,sett_Account a ");
			sbSQL.append(" where a.ID=sa.nAccountID and mUncheckPaymentAmount<>0 and sa.nStatusID>0");
			if (lOfficeID > 0)
			{
				sbSQL.append(" and a.nOfficeID=" + lOfficeID);
			}
			if (lCurrencyID > 0)
			{
				sbSQL.append(" and a.nCurrencyID=" + lCurrencyID);
			}
			sbSQL.append(" order by a.sAccountNo");
			Log.print("SQL = " + sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				bRtn = false;
				strAccountNo = rs.getString("sAccountNo");
				ShutDownMonitor.addDealMessage(lOfficeID, lCurrencyID, Constant.ModuleType.SETTLEMENT, "账户["+strAccountNo+"]的累计未复核金额不为0");					
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;

		}
		catch (Exception e)
		{
			System.out.println(e.toString());
			e.printStackTrace();throw new RemoteException("remote exception : " + e.toString());
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
				e.printStackTrace();throw new RemoteException("remote exception : " + e.toString());
			}
		}
		return bRtn;
	*/
	}
	/**
	* 检查是否存在发放利息没有成功的设置  
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识  
	* @param tsDate 开/关机时间 
	* Description:  
	*/
	public boolean checkFailedGrantInterest(Connection conn, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		return true;
	}
	/**
	 * 查找透支的账户
	 */
	public Collection findOverAccount(long lOfficeID, long lCurrencyID, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) throws RemoteException
	{
		Vector returnVector = new Vector();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		try
		{
			con = Database.getConnection();
			StringBuffer sbRecord = new StringBuffer();
			StringBuffer sbCondition = new StringBuffer();
			StringBuffer sbEnd = new StringBuffer();
			StringBuffer sbCount = new StringBuffer();
			if (lOfficeID == 1)
			{
				sbCondition.append(
					" Where a.nAccountID=b.ID and b.NClientID = c.ID(+) and b.nCurrencyID =" + lCurrencyID + "  and   a.mBalance <0 and b.NStatusID=  " + SETTConstant.AccountStatus.NORMAL);
			}
			else
			{
				sbCondition.append(
					" Where a.nAccountID=b.ID and b.NClientID = c.ID(+) and b.NOfficeID =  "
						+ lOfficeID
						+ " and b.nCurrencyID = "
						+ lCurrencyID
						+ "  and   a.mBalance <0 and b.NStatusID=  "
						+ SETTConstant.AccountStatus.NORMAL);
			}
			sbEnd.append(" ORDER BY  ");
			switch ((int) lOrderParam)
			{
				case 1 :
					sbEnd.append("sAccountNo");
					break;
				case 2 :
					sbEnd.append("AccountName");
					break;
				case 3 :
					sbEnd.append("ClientCode");
					break;
				case 4 :
					sbEnd.append("ClientName");
					break;
				case 5 :
					sbEnd.append("NAccountTypeID");
					break;
				case 6 :
					sbEnd.append("mAmount");
					break;
				case 7 :
					sbEnd.append("NAccountStatusID");
					break;
				case 8 :
					sbEnd.append("nCurrencyID");
					break;
				default :
					sbEnd.append("sAccountNo");
			}
			if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
				sbEnd.append(" DESC");
			else
				sbEnd.append(" ASC");
			//System.out.println(sb.toString());
			sbRecord.append(
				" SELECT * FROM ( SELECT b.nCurrencyID,b.sAccountNo sAccountNo,b.sName AccountName,b.NAccountTypeID NAccountTypeID,a.mBalance mAmount,b.NStatusID NStatusID,c.sCode ClientCode,c.sName ClientName FROM Sett_SubAccount a,Sett_Account b,Client c ");
			sbRecord.append(sbCondition.toString());
			sbRecord.append(sbEnd.toString());
			sbRecord.append(" ) aa ");
			ps = con.prepareStatement(sbRecord.toString());
			System.out.println("*****" + sbRecord.toString());
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				AccountInfo accountInfo = new AccountInfo();
				accountInfo.m_lCurrencyID = rs.getLong("nCurrencyID"); ////币种
				accountInfo.m_strAccountNo = rs.getString("sAccountNo"); ////账户号
				accountInfo.m_strName = rs.getString("AccountName"); ////账户名称
				accountInfo.m_strClientCode = rs.getString("ClientCode"); ////客户编码
				accountInfo.m_strInputUserName = rs.getString("ClientName"); ///客户名称
				accountInfo.m_lAccountTypeID = rs.getLong("NAccountTypeID"); ////账户类型
				accountInfo.m_dAmount = rs.getDouble("mAmount"); ///余额
				//accountInfo.m_lAccountStatusID = rs.getLong("NStatusID"); ////账户zhangtai
				accountInfo.m_lPageCount = lPageCount;
				returnVector.addElement(accountInfo);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
			e.printStackTrace();throw new RemoteException("remote exception : " + e.toString());
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
				e.printStackTrace();throw new RemoteException("remote exception : " + e.toString());
			}
		}
		System.out.println("22222*****************sbRecord**********");
		return (returnVector.size() > 0 ? returnVector : null);
	}
	
	/**
	 * 查找透支的备付金账户
	 * 分两种情况
	 * 1.如果是总部查询，将查出所有在总部开户的备付金账户
	 * 2.如果是分支机构，将查出本机构在总部开立的备付金账户
	 */
	public Collection findOverBakAccount(long lOfficeID, long lCurrencyID, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) throws RemoteException
	{
		Vector returnVector = new Vector();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lPageCount = -1;
		try
		{
			con = Database.getConnection();
			StringBuffer sbRecord = new StringBuffer();
			StringBuffer sbCondition = new StringBuffer();
			StringBuffer sbEnd = new StringBuffer();
			if (lOfficeID == Env.getHQOFFICEID())
			{//如果是总部
				sbCondition.append(
					" Where a.nAccountID=b.ID and b.nAccounttypeID=d.ID and b.NClientID = c.ID and b.nCurrencyID =" + lCurrencyID + "  and  a.mBalance <0 and b.NStatusID=  " + SETTConstant.AccountStatus.NORMAL+"and d.naccountgroupid=  " + SETTConstant.AccountGroupType.BAK);
			}
			else
			{//否则是分支机构
				sbCondition.append(
					" Where a.nAccountID=b.ID and b.nAccounttypeID=d.ID and b.NClientID = c.ID and b.NOfficeID =  "
						+ Env.getHQOFFICEID()
						+ " and b.nCurrencyID = "
						+ lCurrencyID
						+ "  and   a.mBalance <0 and b.NStatusID=  "
						+ SETTConstant.AccountStatus.NORMAL
						+" and d.naccountgroupid=  " + SETTConstant.AccountGroupType.BAK
						+" and c.isinstitutionalclient=  " + lOfficeID);//账户对应的机构客户ID client视图中的isinstitutionalclient字段对应的是分支机构的ID(office表中的ID)
			}
			sbEnd.append(" ORDER BY  ");
			switch ((int) lOrderParam)
			{
				case 1 :
					sbEnd.append("sAccountNo");
					break;
				case 2 :
					sbEnd.append("AccountName");
					break;
				case 3 :
					sbEnd.append("ClientCode");
					break;
				case 4 :
					sbEnd.append("ClientName");
					break;
				case 5 :
					sbEnd.append("NAccountTypeID");
					break;
				case 6 :
					sbEnd.append("mAmount");
					break;
				case 7 :
					sbEnd.append("NAccountStatusID");
					break;
				case 8 :
					sbEnd.append("nCurrencyID");
					break;
				default :
					sbEnd.append("sAccountNo");
			}
			if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
				sbEnd.append(" DESC");
			else
				sbEnd.append(" ASC");
			//System.out.println(sb.toString());
			sbRecord.append(
				" SELECT * FROM ( SELECT b.nCurrencyID,b.sAccountNo sAccountNo,b.sName AccountName,b.NAccountTypeID NAccountTypeID,a.mBalance mAmount,b.NStatusID NStatusID,c.sCode ClientCode,c.sName ClientName FROM Sett_SubAccount a,Sett_Account b,Client c,Sett_Accounttype d ");
			sbRecord.append(sbCondition.toString());
			sbRecord.append(sbEnd.toString());
			sbRecord.append(" ) aa ");
			ps = con.prepareStatement(sbRecord.toString());
			System.out.println("*****" + sbRecord.toString());
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				AccountInfo accountInfo = new AccountInfo();
				accountInfo.m_lCurrencyID = rs.getLong("nCurrencyID"); ////币种
				accountInfo.m_strAccountNo = rs.getString("sAccountNo"); ////账户号
				accountInfo.m_strName = rs.getString("AccountName"); ////账户名称
				accountInfo.m_strClientCode = rs.getString("ClientCode"); ////客户编码
				accountInfo.m_strInputUserName = rs.getString("ClientName"); ///客户名称
				accountInfo.m_lAccountTypeID = rs.getLong("NAccountTypeID"); ////账户类型
				accountInfo.m_dAmount = rs.getDouble("mAmount"); ///余额
				//accountInfo.m_lAccountStatusID = rs.getLong("NStatusID"); ////账户zhangtai
				accountInfo.m_lPageCount = lPageCount;
				returnVector.addElement(accountInfo);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
			e.printStackTrace();throw new RemoteException("remote exception : " + e.toString());
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
				e.printStackTrace();throw new RemoteException("remote exception : " + e.toString());
			}
		}
		System.out.println("22222*****************sbRecord**********");
		return (returnVector.size() > 0 ? returnVector : null);
	}
	/**
	* 结算模块日终处理----账户
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识  
	* @param tsDate 开/关机时间 
	* Description: 
	*  
	*/
	public boolean settEndAccount(Connection conn, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		PreparedStatement ps = null;
		PreparedStatement psBatch = null;
		ResultSet rs = null;
		StringBuffer sbSQL = new StringBuffer();
		boolean bIsPassed = true;
		try
		{
			boolean bHaveData = false;
			sbSQL.setLength(0);
			sbSQL.append(" select a.nAccountID,a.ID,a.mBalance,a.mInterest,a.Af_mRate,0 mSumBalance,b.nStatusID, ");
			sbSQL.append(" Al_mPreDrawInterest,Al_mCommission,Al_mSuretyFee,Al_mOverDueInterest,Al_mInterestTax,Al_mCompoundInterest   ");
			sbSQL.append(" from Sett_SubAccount a,Sett_Account b  ");
			sbSQL.append(" where a.nAccountID = b.ID ");
			sbSQL.append(" and b.nOfficeID = ? and b.nCurrencyID = ? ");
			Log.print(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lOfficeID);
			ps.setLong(2, lCurrencyID);
			rs = ps.executeQuery();
			sbSQL.setLength(0);
			sbSQL.append(" insert into Sett_DailyAccountBalance ");
			sbSQL.append("( ");
			sbSQL.append(" nAccountID,nSubAccountID,dtDate,mBalance,mInterestBalance,mInterest,mDailyInterest,nAccountStatusID ");
			sbSQL.append(" ) ");
			sbSQL.append("values(?,?,?,?,?,?,?,?)");
			Log.print(sbSQL.toString());
			psBatch = conn.prepareStatement(sbSQL.toString());
			while (rs != null && rs.next())
			{
				bHaveData = true;
				int iIndex = 1;
				psBatch.setLong(iIndex++, rs.getLong("nAccountID"));
				psBatch.setLong(iIndex++, rs.getLong("ID"));
				psBatch.setTimestamp(iIndex++, tsDate);
				psBatch.setDouble(iIndex++, rs.getDouble("mBalance"));
				psBatch.setDouble(iIndex++, rs.getDouble("mBalance"));
				psBatch.setDouble(iIndex++, rs.getDouble("mInterest"));
				psBatch.setDouble(iIndex++, rs.getDouble("mInterest"));
				psBatch.setLong(iIndex++, rs.getLong("nStatusID"));
				psBatch.addBatch();
			}
			if (bHaveData)
			{
				psBatch.executeBatch();
				psBatch.close();
				psBatch = null;
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Log.print(e.toString());
			bIsPassed = false;
			e.printStackTrace();throw new Exception(e.getMessage());
		}
		finally
		{
			try
			{
				if (psBatch != null)
				{
					psBatch.close();
					psBatch = null;
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
				e.printStackTrace();
			}
		}
		return bIsPassed;
	}
	/**
	* 结算模块日终处理----总账
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识  
	* @param tsDate 开/关机时间 
	* Description: 
	*  
	*/
	public boolean settEndGL(Connection conn, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		return true;
	}
	/**
	* 结算模块抽取数据 
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识  
	* @param tsDate 开/关机时间 
	* Description: 
	* ------待确定
	*/
	public boolean ExtractPlanData(Connection conn, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		boolean bIsPassed = true;
		try
		{
		}
		catch (Exception e)
		{
			e.printStackTrace();
			bIsPassed = false;
		}
		return bIsPassed;
	}
	/**
	* 设置开/关机处理状态
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识
	* @param lStatusID 状态标识
	*/
	public static void setDealStatusID(long lOfficeID, long lCurrencyID, long lStatusID) throws Exception
	{
		PreparedStatement ps = null;
		StringBuffer sbRecord = new StringBuffer();
		Connection conn = null;
		try
		{
			conn = Database.getConnection();
			sbRecord.setLength(0);
			sbRecord.append(" update sett_OfficeTime set nCloseSystemStatusID = ? where nOfficeID = ? and nCurrencyID = ?  ");
			ps = conn.prepareStatement(sbRecord.toString());
			ps.setLong(1, lStatusID);
			ps.setLong(2, lOfficeID);
			ps.setLong(3, lCurrencyID);
			ps.executeUpdate();
			//Log.print(sbRecord.toString());
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			e.printStackTrace();throw new Exception(e.getMessage());
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
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();throw new Exception("remote exception : " + e.toString());
			}
		};
	}
	/**
	* 取得开/关机处理状态
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识
	* @param lModelID 模块标识 
	* return 处理状态
	*/
	public static long getDealStatusID(long lOfficeID, long lCurrencyID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbRecord = new StringBuffer();
		long lStatusID = -1;
		Connection conn = null;
		try
		{
			conn = Database.getConnection();
			sbRecord.setLength(0);
			sbRecord.append(" select nCloseSystemStatusID from  sett_OfficeTime where nOfficeID = ? and nCurrencyID = ?  ");
			ps = conn.prepareStatement(sbRecord.toString());
			ps.setLong(1, lOfficeID);
			ps.setLong(2, lCurrencyID);
			rs = ps.executeQuery();
			//Log.print(sbRecord.toString());
			if (rs.next())
			{
				lStatusID = rs.getLong(1);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
				throw e;
			}
		};
		return lStatusID;
	}
	/**
	* 设置系统状态
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识
	* @param lModelID 模块标识
	* @param strMessage 关机信息 
	*/
	public static void setSystemStatusID(long lOfficeID, long lCurrencyID, long lStatusID) throws Exception
	{
		PreparedStatement ps = null;
		StringBuffer sbRecord = new StringBuffer();
		Connection conn = null;
		try
		{
			conn = Database.getConnection();
			sbRecord.setLength(0);
			sbRecord.append(" update sett_OfficeTime set sSystemStatusDesc = ? where nOfficeID = ? and nCurrencyID = ?  ");
			ps = conn.prepareStatement(sbRecord.toString());
			ps.setString(1, Constant.SystemStatus.getName(lStatusID));
			ps.setLong(2, lOfficeID);
			ps.setLong(3, lCurrencyID);
			ps.executeUpdate();
			//Log.print(sbRecord.toString());
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
			e.printStackTrace();throw new Exception(e.getMessage());
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
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();throw new Exception("remote exception : " + e.toString());
			}
		};
	}
	/**
	* 取得系统状态
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识
	* @param lModelID 模块标识 
	* return 处理状态
	*/
	public static long getSystemStatusID(long lOfficeID, long lCurrencyID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbRecord = new StringBuffer();
		long lStatusID = -1;
		Connection conn = null;
		try
		{
			conn = Database.getConnection();
			sbRecord.setLength(0);
			sbRecord.append(" select sSystemStatusDesc from  sett_OfficeTime where nOfficeID = ? and nCurrencyID = ?  ");
			ps = conn.prepareStatement(sbRecord.toString());
			ps.setLong(1, lOfficeID);
			ps.setLong(2, lCurrencyID);
			rs = ps.executeQuery();
			//Log.print(sbRecord.toString());
			if (rs.next())
			{
				lStatusID = Constant.SystemStatus.getID(rs.getString(1));
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
				throw e;
			}
		};
		return lStatusID;
	}
	/**
			* 设置系统时间
			* @param lOfficeID 办事处标识
			* @param lCurrencyID 币种标识
			* @param lModelID 模块标识
			* @param strMessage 关机信息 
			*/
	public static void setSystemDate(long lOfficeID, long lCurrencyID, Timestamp tsSystemDate) throws Exception
	{
		PreparedStatement ps = null;
		StringBuffer sbRecord = new StringBuffer();
		Connection conn = null;
		try
		{
			conn = Database.getConnection();
			sbRecord.setLength(0);
			sbRecord.append(" update sett_OfficeTime set dtOpenDate = ? where nOfficeID = ? and nCurrencyID = ?  ");
			ps = conn.prepareStatement(sbRecord.toString());
			ps.setTimestamp(1, tsSystemDate);
			ps.setLong(2, lOfficeID);
			ps.setLong(3, lCurrencyID);
			ps.executeUpdate();
			//Log.print(sbRecord.toString());
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
			e.printStackTrace();throw new Exception(e.getMessage());
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
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();throw new Exception("remote exception : " + e.toString());
			}
		}
	}
	/**
	* 取得系统时间
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识
	* @param lModelID 模块标识 
	* return 处理状态
	*/
	public static Timestamp getSystemDate(long lOfficeID, long lCurrencyID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbRecord = new StringBuffer();
		Timestamp tsSystemDate = null;
		Connection conn = null;
		try
		{
			conn = Database.getConnection();
			sbRecord.setLength(0);
			sbRecord.append(" select dtOpenDate from  sett_OfficeTime where nOfficeID = ? and nCurrencyID = ?  ");
			ps = conn.prepareStatement(sbRecord.toString());
			ps.setLong(1, lOfficeID);
			ps.setLong(2, lCurrencyID);
			rs = ps.executeQuery();
			//Log.print(sbRecord.toString());
			if (rs.next())
			{
				tsSystemDate = rs.getTimestamp(1);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
				throw e;
			}
		};
		return tsSystemDate;
	}
	/**
			* 结算修改系统时间、状态
			* @param lOfficeID 办事处标识
			* @param lCurrencyID 币种标识  
			* @param lSystemStatusID 开/关机时间 
			* @param tsDate 开/关机时间 
			* Description:  
			*/
	public boolean setSystemStatusAndDate(Connection conn, long lOfficeID, long lCurrencyID, long lSystemStatusID, Timestamp tsDate) throws Exception
	{
		PreparedStatement ps = null;
		StringBuffer sbSQL = new StringBuffer();
		boolean bIsPassed = true;
		try
		{
			String SequenceName = "seq_Trans" + DataFormat.formatInt(lOfficeID, 2) + DataFormat.formatInt(lCurrencyID, 2);
			try
			{
				if (lSystemStatusID == Constant.ShutDownAction.OPEN && bIsPassed)
				{
					sbSQL.setLength(0);
					sbSQL.append(" drop sequence " + SequenceName);
					ps = conn.prepareStatement(sbSQL.toString());
					Log.print(sbSQL.toString());
					long lResult = ps.executeUpdate();
					ps.close();
					ps = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			try
			{
				if (lSystemStatusID == Constant.ShutDownAction.OPEN && bIsPassed)
				{
					sbSQL.setLength(0);
					sbSQL.append(" create sequence " + SequenceName);
					sbSQL.append(" increment by 1 ");
					sbSQL.append(" nomaxvalue ");
					sbSQL.append(" nominvalue ");
					sbSQL.append(" nocycle ");
					sbSQL.append(" noorder  ");
					ps = conn.prepareStatement(sbSQL.toString());
					Log.print(sbSQL.toString());
					long lResult = ps.executeUpdate();
					ps.close();
					ps = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			if (bIsPassed)
			{
				sbSQL.setLength(0);
				sbSQL.append(" update sett_OfficeTime set sSystemStatusDesc='" + Constant.SystemStatus.getName(lSystemStatusID) + "', dtOpenDate =?  ");
				sbSQL.append(" where nOfficeID =  " + lOfficeID + " and nCurrencyID = " + lCurrencyID);
				ps = conn.prepareStatement(sbSQL.toString());
				ps.setTimestamp(1, tsDate);
				Log.print(sbSQL.toString());
				long lResult = ps.executeUpdate();
				ps.close();
				ps = null;
				if (lResult > 0)
				{
					bIsPassed = true;
				}
				else
				{
					bIsPassed = false;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			bIsPassed = false;
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
			catch (Exception e)
			{
				throw e;
			}
		};
		return bIsPassed;
	}
	/**
	* 其它处理：
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识
	* @param lModelID 模块标识
	* @param strMessage 关机信息 
	*/
	public boolean otherProcess(Connection conn,long lOfficeID, long lCurrencyID,long lSystemStatusID, Timestamp tsSystemDate) throws Exception
	{
		boolean bIsPassed = true;
		PreparedStatement ps = null;
		StringBuffer sbRecord = new StringBuffer(); 
        //modify by xwhe 2009-06-02 
		boolean isfinish_WT_contract = Config.getBoolean(ConfigConstant.SETTLEMENT_FINISH_WT_CONTRACT, false);
		try
		{ 
			Log.print("****开始对自营贷款和委托贷款是否结清进行处理*****");
			//modify by forest,20050202,当余额利息合计非常小，小于1分钱时，系统也认为已结清。
			sbRecord = null;
			sbRecord = new StringBuffer(); 
			sbRecord.append(" update  sett_SubAccount set nStatusID="+SETTConstant.SubAccountStatus.FINISH+",dtFinish=? \n");
			sbRecord.append(" where ID in ( \n");
			sbRecord.append("   select sa.ID from sett_SubAccount sa,sett_Account a,sett_accountType at \n");
			sbRecord.append("   where sa.nAccountID=a.ID and a.nAccountTypeID=at.id and at.nAccountGroupID="+ 
				SETTConstant.AccountGroupType.TRUST +" and at.nstatusId=1 and at.officeId="+lOfficeID+" and at.currencyId="+lCurrencyID+" and sa.nStatusID="+SETTConstant.SubAccountStatus.NORMAL+" \n");
			sbRecord.append("		and (mBalance+mInterest+AL_mPreDrawInterest+AL_mSuretyFee+AL_mOverDueInterest+AL_mCompoundInterest)<0.01 \n");
			sbRecord.append("   union \n");
			sbRecord.append("   select sa.ID from sett_SubAccount sa,sett_Account a,sett_accountType at \n");
			sbRecord.append("   where sa.nAccountID=a.ID and a.nAccountTypeID=at.id and at.nAccountGroupID=" + 
				SETTConstant.AccountGroupType.CONSIGN + " and at.nstatusId=1 and at.officeId="+lOfficeID+" and at.currencyId="+lCurrencyID+" and sa.nStatusID="+SETTConstant.SubAccountStatus.NORMAL+" \n");
			if(isfinish_WT_contract)
			{
			sbRecord.append("  and (mBalance+mInterest+AL_mPreDrawInterest+AL_mOverDueInterest+AL_mCompoundInterest)<0.01 \n");	
			}
			else
			{
			sbRecord.append("  and (mBalance+mInterest+AL_mPreDrawInterest+AL_mCommission+AL_mOverDueInterest+AL_mCompoundInterest)<0.01 \n");
			}
			sbRecord.append(" ) \n");
			Log.print(sbRecord.toString());
			ps = conn.prepareStatement(sbRecord.toString());
			ps.setTimestamp(1, tsSystemDate);
			ps.executeUpdate();
			ps.close();
			ps = null; 
			Log.print("****结束对自营贷款和委托贷款是否结清进行处理*****");
			
			sbRecord = null;
			sbRecord = new StringBuffer(); 
			sbRecord.append(" update sett_SubAccount set AF_mPredrawInterest=0,AL_mPredrawInterest = 0 ");
			sbRecord.append(" where dtFinish = ? and sett_SubAccount.nStatusID = ?  and (AF_mPredrawInterest!=0 or AL_mPredrawInterest!=0 ) ");
			sbRecord.append(" and sett_SubAccount.nAccountID in (select ID from sett_Account where nOfficeID=? and nCurrencyID =? )   ");
			ps = conn.prepareStatement(sbRecord.toString());
			ps.setTimestamp(1, tsSystemDate);
			ps.setLong(2, SETTConstant.SubAccountStatus.FINISH);
			ps.setLong(3, lOfficeID);
			ps.setLong(4, lCurrencyID);
			ps.executeUpdate();
			//Log.print(sbRecord.toString());
			ps.close();
			ps = null; 
		}
		catch (Exception e)
		{
			bIsPassed = false;
			e.printStackTrace();
			if (ps != null)
			{
				ps.close();
				ps = null;
			} 
			e.printStackTrace();throw new Exception(e.getMessage());
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
				bIsPassed = false;
				e.printStackTrace();throw new Exception("remote exception : " + e.toString());
			}
		};
		return bIsPassed;
	}
	
	/**
	* 校验是否存在协定存款的到期日在关机日前的交易
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识  
	* @param tsDate 开/关机时间 
	* Description:  
	*/
	public boolean checkNegotiate( Connection conn,long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean bRtn = true;
		try
		{
			Log.print("***进入方法：校验是否存在协定存款的到期日在关机日前的交易，add by feiye***");

			//用传进来的数据库连接即可在此不用重新得到
			//conn = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			
			sbSQL=null;
			sbSQL= new StringBuffer();
			/*
			sbSQL.append(" select acc.sAccountNo ");
			sbSQL.append(" from sett_account acc,sett_accounttype accType,sett_subAccount subAcc ");
			sbSQL.append(" where  ");
			sbSQL.append(" accType.nAccountGroupID=  '"+SETTConstant.AccountGroupType.CURRENT +"'");	//活期账户组
			sbSQL.append(" and acc.nAccountTypeID=accType.id  ");
			sbSQL.append(" and accType.nstatusId=1 and accType.officeId="+lOfficeID+" and accType.currencyId="+lCurrencyID+" ");
			sbSQL.append(" and subAcc.nAccountId=acc.id  ");
			sbSQL.append(" and subAcc.AC_NISNEGOTIATE=1  ");	//钩选了协定存款
			sbSQL.append(" and acc.NSTATUSID != "+SETTConstant.AccountStatus.CLOSE);	//该账户没有被清户
			*/
			
			sbSQL.append(" select acc.sAccountNo ");
			sbSQL.append(" from sett_account acc,sett_accounttype accType,sett_subAccount subAcc ");
			sbSQL.append(" where  ");
			sbSQL.append(" accType.nAccountGroupID=  '"+SETTConstant.AccountGroupType.CURRENT +"'");	//活期帐户组
			sbSQL.append(" and acc.nAccountTypeID=accType.id  ");
			sbSQL.append(" and subAcc.nAccountId=acc.id  ");
			sbSQL.append(" and subAcc.AC_NISNEGOTIATE=1  ");	//钩选了协定存款
			sbSQL.append(" and acc.NSTATUSID != 0  ");	//该帐户没有被清户(常量类中没有定义,不是清户状态,可能是手动修改)
			sbSQL.append(" and acc.NSTATUSID != '"+SETTConstant.AccountStatus.CLOSE +"'");	//该帐户没有被清户
			//注: SETTConstant.AccountStatus.CLOSE为帐户清户状态

			if (lOfficeID > 0)			//办事处
			{
				sbSQL.append(" and acc.nOfficeID=" + lOfficeID);
			}
			if (lCurrencyID > 0)		//币种
			{
				sbSQL.append(" and acc.nCurrencyID=" + lCurrencyID);
			}
			if (tsDate != null)				//协定存款的日期和关机的日期作对比 (看是否存在到期日小于当天的信息)
			{
				sbSQL.append(" and subAcc.AC_DTNEGOTIATIONENDDATE < = to_date('" + DataFormat.getDateString(tsDate)+"','yyyy-mm-dd')");
			}

			Log.print("SQL = " + sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			
			StringBuffer strAccountNo=new StringBuffer();
			String strAccountNoTmp="";
			while (rs != null && rs.next())		//循环得到所有的数据
			{
				strAccountNoTmp=rs.getString("sAccountNo");
				strAccountNo.append(strAccountNoTmp+",");
				bRtn = false;
			}
			if(bRtn==false){
				//弹出协定存款不满足条件的信息
				CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, Constant.ModuleType.SETTLEMENT, "账户号：" + strAccountNo.toString().substring(0,strAccountNo.toString().length()-1) + "的协定存款的到期日在今天关机之前");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
			e.printStackTrace();throw new RemoteException("remote exception : " + e.toString());
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
				e.printStackTrace();throw new RemoteException("remote exception : " + e.toString());
			}
		}
		return bRtn;
	}
	/**
	* 校验活期账户在关机日前的交易
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识  
	* @param tsDate 开/关机时间 
	* Description:  
	*/
	public boolean checkCurrentAcount( Connection conn,long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean bRtn = true;
		try
		{
			Log.print("***进入方法：校验是否存在未复核的活期账户在关机日前，add by xwhe***");
		    StringBuffer sbSQL = new StringBuffer();
			
			sbSQL=null;
			sbSQL= new StringBuffer();
					
			sbSQL.append(" select acc.sAccountNo ");
			sbSQL.append(" from sett_account acc,sett_accounttype accType,sett_subAccount subAcc ");
			sbSQL.append(" where  ");
			sbSQL.append(" accType.nAccountGroupID=  '"+SETTConstant.AccountGroupType.CURRENT +"'");	//活期帐户组
			sbSQL.append(" and acc.nAccountTypeID=accType.id  ");
			sbSQL.append(" and subAcc.nAccountId=acc.id  ");			
			sbSQL.append(" and acc.ncheckstatusid  in( "+SETTConstant.AccountCheckStatus.OLDSAVE +","+SETTConstant.AccountCheckStatus.OLDSAVE_APPROVALING +","+SETTConstant.AccountCheckStatus.OLDSAVE_APPROVALED+")");	//该帐户复核后被修改
			//注: SETTConstant.AccountStatus.CLOSE为帐户清户状态
			sbSQL.append(" and acc.NSTATUSID != 0  ");	//
			sbSQL.append(" and acc.NSTATUSID != '"+SETTConstant.AccountStatus.CLOSE +"'");	//该帐户没有被清户
			if (lOfficeID > 0)			//办事处
			{
				sbSQL.append(" and acc.nOfficeID=" + lOfficeID);
			}
			if (lCurrencyID > 0)		//币种
			{
				sbSQL.append(" and acc.nCurrencyID=" + lCurrencyID);
			}
			if (tsDate != null)			//协定存款的日期和关机的日期作对比 (看是否存在到期日小于当天的信息)
			{
				//sbSQL.append(" and subAcc.AC_DTNEGOTIATIONENDDATE > = to_date('" + DataFormat.getDateString(tsDate)+"','yyyy-mm-dd')");
			}

			Log.print("SQL = " + sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			
			StringBuffer strAccountNo=new StringBuffer();
			String strAccountNoTmp="";
			while (rs != null && rs.next())		//循环得到所有的数据
			{
				strAccountNoTmp=rs.getString("sAccountNo");
				strAccountNo.append(strAccountNoTmp+",");
				bRtn = false;
			}
			if(bRtn==false){
				//弹出活期账户不满足条件的信息
				CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, Constant.ModuleType.SETTLEMENT, "活期账户号：" + strAccountNo.toString().substring(0,strAccountNo.toString().length()-1) + "在今天关机之前未复核");
				CloseSystemMain.setM_message("活期账户号：" + strAccountNo.toString().substring(0,strAccountNo.toString().length()-1) + "在今天关机之前未复核");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
			e.printStackTrace();throw new RemoteException("remote exception : " + e.toString());
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
				e.printStackTrace();throw new RemoteException("remote exception : " + e.toString());
			}
		}
		return bRtn;
	}
	/**
	* author xwhe 2008-06-11 
	* 合同的起始日期在第一笔放款时修改
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识  
	* @param tsDate 开/关机时间 
	* Description:  
	*/
	public boolean setSystemContractDate( Connection conn,long lOfficeID, long lCurrencyID,long lSystemStatusID, Timestamp tsDate) throws Exception
	{
		PreparedStatement ps = null;
		PreparedStatement pstemp = null;
		ResultSet rs = null;
		boolean bRtn = true;
		try
		{
			Log.print("***进入方法：查找符合的合同号，add by xwhe***");
		    StringBuffer sbSQL = new StringBuffer();
		
			sbSQL.append(" select e.nloancontractid,e.nloannoteid,f.dtstart,f.dtend ");
			sbSQL.append(" from ( " );
			sbSQL.append(" select min(id) id, nloancontractid ");
			sbSQL.append(" from ( " );
			sbSQL.append(" select * from SETT_TRANSGRANTLOAN aa ");
			sbSQL.append(" where 1=1 ");
			if (lOfficeID > 0)
			{
			sbSQL.append(" and aa.nofficeid = "  + lOfficeID);
			}
			if (lCurrencyID > 0)		
			{
			sbSQL.append(" and aa.nCurrencyID = " + lCurrencyID);
			}
			sbSQL.append(" and aa.nstatusid  = " + SETTConstant.TransactionStatus.CHECK );
			sbSQL.append(" and aa.nloancontractid not in (");
			sbSQL.append(" select nloancontractid from  SETT_TRANSGRANTLOAN ");
			sbSQL.append(" where ");
			sbSQL.append(" dtexecute < to_date('" + DataFormat.getDateString(tsDate)+"','yyyy-mm-dd')");
			if (lOfficeID > 0)
			{
			sbSQL.append(" and nofficeid = "+ lOfficeID);
			}
			if (lCurrencyID > 0)		
			{
			sbSQL.append(" and ncurrencyid = " + lCurrencyID);
			}
			sbSQL.append(" and nstatusid = " + SETTConstant.TransactionStatus.CHECK + ")");
			sbSQL.append(" order by aa.nloancontractid, aa.dtmodify ) d ");
			sbSQL.append(" group by nloancontractid) dd, SETT_TRANSGRANTLOAN e,loan_payform f , loan_contractform c ");
			sbSQL.append(" where ");
			sbSQL.append(" e.id = dd.id ");
			sbSQL.append(" and f.id = e.nloannoteid ");			
			sbSQL.append(" and dd.nloancontractid = c.id ");
			sbSQL.append(" and e.nloancontractid = c.id ");
			sbSQL.append(" and f.ncontractid = c.id ");
			sbSQL.append(" and c.niscircle <> 1 ");
			//modify by xwhe  2008-12-02 从初始化后的合同开始日期和结束日期进行修改
			sbSQL.append(" and f.dtstart > to_date ('2008-12-02','yyyy-mm-dd')");
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while (rs != null && rs.next())	//循环得到所有的数据
			{
				long lContractID = rs.getLong("nloancontractid");
				long lLoanNoteID = rs.getLong("nloannoteid");
				Timestamp dtStart = rs.getTimestamp("dtstart");
				Timestamp dtEnd = rs.getTimestamp("dtend");
				if(lContractID > 0)
				{
					String strSQL = null;
					strSQL = " update loan_contractform set dtmodifystartdate = dtstartdate, dtmodifyenddate = dtenddate, dtstartdate = ? ,dtenddate = add_months( ? ,nintervalnum ) , nloannoteid = ? where ID = ? ";
					pstemp = conn.prepareStatement(strSQL);
					pstemp.setTimestamp(1, dtStart);
					pstemp.setTimestamp(2, dtStart);
					pstemp.setLong(3, lLoanNoteID);
					pstemp.setLong(4, lContractID);
					pstemp.executeUpdate();
					pstemp.close();
					pstemp = null;
				}
			}	
				rs.close();
				rs = null;
				ps.close();
				ps = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RemoteException("remote exception : " + e.toString());
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
				throw new RemoteException("remote exception : " + e.toString());
			}
		}
		return bRtn;
	}		
				
	public static void main(String args[]){
		/*
		System.out.println("   aaaaaaaaaaaaaaaaaaaaaaaaaa");
		
		EndDayProcess end=new EndDayProcess();
		
		java.util.Date dt=new java.util.Date(2006,4,8);
		Timestamp dtTemp=new Timestamp(dt.getTime());
		
		try{
			boolean flag=end.checkNegotiate(1,1,dtTemp);
			System.out.println("    得到返回值为"+flag);
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("   bbbbbbbbbbbbbbbbbbbbbb");
		*/
	}
	  
	
	/**
	* 修改转贴现凭证信息
	*/
	public static void modifyDiscount() throws Exception
	{
		PreparedStatement ps = null;
		StringBuffer sbRecord = new StringBuffer();
		Connection conn = null;
		try
		{
			conn = Database.getConnection();
			sbRecord.setLength(0);
			sbRecord.append(" update loan_discountcredence t set t.nstatusid=4 where  t.nstatusid=3  ");
			System.out.println("修改转贴现凭证信息SQL----"+sbRecord.toString());
			ps = conn.prepareStatement(sbRecord.toString());
			ps.executeUpdate();
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
			e.printStackTrace();throw new Exception(e.getMessage());
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
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();throw new Exception("update exception : " + e.toString());
			}
		}
    }
	public boolean fixedDepositAutoContinue(Connection transConn, long lOfficeID, long lCurrencyID, Timestamp tsDate)throws Exception
	{
		boolean isPassed = false;
		Collection coll = null;
		TransFixedContinueInfo info =null;
		SubAccountFixedInfo  fixedInfo = null;
		long id = -1;
		OpenCloseLogBiz ocLog =  null;
		long openCloseId = -1;
		Connection conn = null;
		try
		{
			if(transConn==null)//外部传入的链接为空时，数据库链接自维护
			{
				conn = Database.getConnection();
				conn.setAutoCommit(false);
			}
			else
			{
				conn = transConn;
			}
			InterestOperation io = new InterestOperation(conn);
			UtilOperation utilOperation = new UtilOperation(conn);
			AccountBookOperation accountBookOperation = new AccountBookOperation();
			long fixedWithdrawSubAccountID =-1;
			AccountBean accBean = new AccountBean(conn);
			GeneralLedgerBean ledgerBean = new GeneralLedgerBean(conn);
			Sett_TransFixedContinueDAO continueDao = new Sett_TransFixedContinueDAO(conn);
			Sett_SubAccountDAO subAccountDao = new Sett_SubAccountDAO(conn);
			Sett_AccountDAO sett_accountDao = new Sett_AccountDAO();
			ocLog =  OpenCloseLogFactory.getInstance(lOfficeID,lCurrencyID,false);
			SettInterestRateBiz rateBiz = new SettInterestRateBiz();
			//查询当天全部到期且需要自动续存定期存款单据
			coll = subAccountDao.findAutoContinueData(lOfficeID,  lCurrencyID,  tsDate);
			Iterator it= coll.iterator();
			if(coll.size()!=0)
			{
				openCloseId = ocLog.addCloseLogReturnId(SETTConstant.CloseSystemLogType.FIXEDDEPOSITAUTOCONTINUE,SETTConstant.OpenCloseStatus.SUCCEED,"自动续存业务处理成功");
			}
			while(it.hasNext())
			{
				fixedInfo = new SubAccountFixedInfo();
				info = new TransFixedContinueInfo();
				fixedInfo = (SubAccountFixedInfo)it.next();
				info.setSubAccountID(fixedInfo.getID());
				info.setAmount(fixedInfo.getBalance());
			    info.setExecuteDate(tsDate);
			    
			    //计算定期利息
				//基本信息
			    info.setAccountID(fixedInfo.getAccountID());
			    info.setClientID(NameRef.getClientIDByAccountID(fixedInfo.getAccountID()));
			    info.setStartDate(fixedInfo.getStartDate());
			    info.setEndDate(fixedInfo.getEndDate());
			    info.setDepositTerm(fixedInfo.getDepositTerm());
			    info.setRate(fixedInfo.getRate());
			    info.setAmount(fixedInfo.getBalance());

				try 
				{
					String strNewDepositNo = utilOperation.getOpenDepositNoBackGround(info.getAccountID());
					info.setNewDepositNo(strNewDepositNo);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
					throw e;
				}
				
				InterestsInfo ioInfo = new InterestsInfo();
				try
				{
					double baseAmount = 0.0;
					baseAmount = info.getAmount();
					ioInfo = io.calculateFixedDepositAccountInterest(info.getSubAccountID(), baseAmount, info.getExecuteDate());
				}
				catch (IException e)
				{
					throw e;
				}
				
				info.setPreDrawInterest(ioInfo.getPreDrawInterest());
				info.setPayableInterest(ioInfo.getInterestPayment());
			    
			    //计算定期利息结束
				//开始保存新定期续存业务
				
				info.setAbstract("定期到期自动续存业务,原定期存单号为:"+fixedInfo.getDepositNo());
				info.setCheckAbstract("定期到期自动续存业务复核,原定期存单号为:"+fixedInfo.getDepositNo());
				info.setCheckUserID(Constant.MachineUser.CheckUser);
				info.setCurrencyID(lCurrencyID);
				info.setExecuteDate(tsDate);
				info.setInputUserID(Constant.MachineUser.InputUser);
				info.setModifyDate(tsDate);
				info.setOfficeID(lOfficeID);
				info.setStatusID(SETTConstant.TransactionStatus.CHECK);
				info.setSubAccountID(fixedInfo.getID());
				info.setTransactionTypeID(SETTConstant.TransactionType.FIXEDCONTINUETRANSFER);
				if(fixedInfo.getAutoContinueType()==1)
				{
					info.setIsCapitalAndInterestTransfer(fixedInfo.getAutoContinueType());
				}
				info.setNewAmount(fixedInfo.getBalance());
				info.setDepositNo(fixedInfo.getDepositNo());
				info.setInterestStartDate(tsDate);
				
				//定期查询最新的利率
				double newRate = 0;
				InterestRateInfo rateInfo = new InterestRateInfo();
				
				rateInfo.setnOfficeid(lOfficeID);
				rateInfo.setnCurrencyid(lCurrencyID);
				rateInfo.setDtEffective(tsDate);
				rateInfo.setnAccounttypeid(SETTConstant.SettRateType.FIXED);
				rateInfo.setnFixeddepositmonthid(info.getDepositTerm());
				
				newRate = rateBiz.findLastInterestRate(rateInfo);
				if(newRate==0)
				{
					throw new IException("查询最新定期利率出错");
				}
				
				info.setNewDepositTerm(info.getDepositTerm());
				info.setNewEndDate(DataFormat.getNewNextMonth(tsDate,(int)info.getDepositTerm()));
				info.setNewRate(newRate);
				info.setNewStartDate(tsDate);
				info.setReceiveInterestAccountID(fixedInfo.getInterestAccountID());
				info.setWithDrawInterest(info.getPreDrawInterest());
				info.setInputDate(tsDate);  
				info.setIsAutoContinue(fixedInfo.getIsAutoContinue());
				info.setAutocontinuetype(fixedInfo.getAutoContinueType());
				info.setAutocontinueaccountid(fixedInfo.getInterestAccountID());
				
				//保存交易开始
				if (!continueDao.checkDepositNo(info)) {
					throw new IException("定期存单号重复！");
				}
				String transNo = utilOperation.getNewTransactionNo(info
							.getOfficeID(), info.getCurrencyID(), info
							.getTransactionTypeID());
				info.setTransNo(transNo);
				id = continueDao.add(info);
				info.setID(id);
				//开始校验账户信息
				if (info.getAccountID() > 0)
				{
					try {
						accBean.validateAccountStatus(info.getAccountID(),AccountBean.TRANSTYPE_WITHDRAW);
					} catch (IException e) {
						e.printStackTrace();
						throw e;
					}
				}
				//校验账户信息结束
				//保存结束
				
				
				info = continueDao.findByID(id);
				//复核交易
				

				long newFixedSubAccountID = -1;
				long currentSubAccountID = -1;
				log.debug("---------开始checkContinueFixedDeposit-----------");
				TransAccountDetailInfo tadi = new TransAccountDetailInfo();
				//定期支取
				log.debug("---------开始定期支取-----------");
				
				tadi.setOfficeID(info.getOfficeID());
				tadi.setCurrencyID(info.getCurrencyID());
				tadi.setTransactionTypeID(info.getTransactionTypeID());
				tadi.setDtExecute(info.getExecuteDate());
				tadi.setTransNo(info.getTransNo());
				tadi.setAmount(info.getAmount());
				tadi.setDtInterestStart(info.getInterestStartDate());
				tadi.setAbstractStr(info.getAbstract());
				tadi.setTransSubAccountID(info.getSubAccountID());
				tadi.setAbstractID(info.getAbstractID());
				//开立时在账户子系统中设置对方子账户（即新开里账户ID）
				tadi.setTransAccountID(info.getAccountID());
				tadi.setFixedDepositNo(info.getDepositNo());
				tadi.setAmount(info.getAmount());
				//账户金额查询区分金额类型增加
				tadi.setAmountType(SETTConstant.AmountType.AmountType_1);
				
				//为账户对账单信息查询 所加
				if(info.getAccountID()>0)
				{
					try {
						com.iss.itreasury.settlement.account.dataentity.AccountInfo accountInfo = sett_accountDao.findByID(info.getAccountID());
						tadi.setOppAccountNo(accountInfo.getAccountNo());
						tadi.setOppAccountName(accountInfo.getAccountName());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				try
				{

					log.debug("--------开始到期续存处理计提利息和计提日期------------");
					long lSubAccountID = -1;
					try
					{
						lSubAccountID = accBean.getFixedSubAccoutIDByAccoutIDAndFixedDepositNo(tadi.getTransAccountID(), tadi.getFixedDepositNo());
						tadi.setTransSubAccountID(lSubAccountID);
							
						if (lSubAccountID == -1)
						{
							throw new IException("无法获得主账户ID是:" + tadi.getTransAccountID() + " 定期存单号是: " + tadi.getFixedDepositNo() + " 的定期子账户，交易失败");
						}
						
						accBean.ContinueFixedPreDrawInterest(tadi, "check");
					}
					catch (IException e)
					{
						throw e;
					}
					log.debug("--------结束到期续存处理计提利息和计提日期------------");
					
				}
				catch (Exception e)
				{
					throw e;
				}

				log.debug("--------开始定期支取withdrawFix------------");
				long lSubAccountID = -1;
				try
				{
					lSubAccountID =
						accBean.getFixedSubAccoutIDByAccoutIDAndFixedDepositNo(tadi.getTransAccountID(), tadi.getFixedDepositNo());
					
					tadi.setTransSubAccountID(lSubAccountID);
					tadi.setCommonOperation(false);
					if (lSubAccountID == -1)
					{
						throw new IException(
							"无法获得主账户ID是:" + tadi.getTransAccountID() + " 定期存单号是: " + tadi.getFixedDepositNo() + " 的定期子账户，交易失败");
					}

					fixedWithdrawSubAccountID=accBean.withdraw(tadi);
				}
				catch (IException e)
				{
					throw  e;
				}
				log.debug("--------结束定期支取withdrawFix------------");
			
				//long fixedWithdrawSubAccountID = accountOperation.withdrawFix(tadi);
				log.debug("---------结束定期支取-----------");
				//定期开空户（定期信息），返回值=新定期子账户ID
				log.debug("---------开始开立定期子账户-----------");
				SubAccountFixedInfo safi = new SubAccountFixedInfo();
				try
				{
					safi.setAccountID(info.getAccountID());
					safi.setOpenDate(info.getNewStartDate());
					safi.setClearInterestDate(info.getNewStartDate());
					if (info.getIsCapitalAndInterestTransfer() == 1)
						safi.setOpenAmount(info.getAmount() + info.getWithDrawInterest());
					else
						safi.setOpenAmount(info.getAmount());
					safi.setDepositNo(info.getNewDepositNo());
					safi.setDepositTerm(info.getNewDepositTerm());
					safi.setEndDate(info.getNewEndDate());
					safi.setPreDrawDate(info.getExecuteDate());
					
					safi.setPreDrawDate(info.getNewStartDate());
					safi.setRate(info.getNewRate());
					safi.setSealBankID(info.getNewSealBankID());
					safi.setSealNo(info.getNewSealNo());
					safi.setStartDate(info.getNewStartDate());
					safi.setIsInterest(1);
					safi.setIsAutoContinue(info.getIsAutoContinue());
					safi.setAutoContinueType(info.getAutocontinuetype());
					safi.setInterestAccountID(info.getAutocontinueaccountid());
				}
				catch (Exception e)
				{
					throw new IException( "无法获得新的定期存单号，交易失败");
				}

				log.debug("---------开始openFixSubAccount-----------");

				if (accBean.validateAccountStatus(safi.getAccountID(), AccountBean.TRANSTYPE_IRRELATIVE) == SETTConstant.AccountStatus.NORMAL
					/*&& isOverDraft(safi.getAccountID(), 0.0) == false*/
					)
				{
					log.debug("---------定期子账户没有透支-----------");
					try
					{
						log.debug("---------Sett_SubAccountDAO增加定期账户-----------");
						newFixedSubAccountID = subAccountDao.addSubAccountFix(safi);

					}
					catch (Exception e)
					{
						throw e;
					}
				}

				log.debug("---------结束openFixSubAccount-----------");
			
				//newFixedSubAccountID = accountOperation.openFixSubAccount(safi);
				log.debug("---------结束开立定期子账户,新开立定期子账户ID: " + newFixedSubAccountID + "-----------");
				//定期存入
				log.debug("---------开始定期存入-----------");
				tadi.setOfficeID(info.getOfficeID());
				tadi.setCurrencyID(info.getCurrencyID());
				tadi.setTransactionTypeID(info.getTransactionTypeID());
				tadi.setDtExecute(info.getExecuteDate());
				tadi.setTransNo(info.getTransNo());
				tadi.setAmount(info.getAmount());
				//交易方向在账户中设置
				tadi.setDtInterestStart(info.getInterestStartDate());
				tadi.setAbstractStr(info.getAbstract());
				tadi.setTransSubAccountID(info.getSubAccountID());
				tadi.setAbstractID(info.getAbstractID());
				//开立时在账户子系统中设置对方子账户（即新开里账户ID）
				tadi.setTransAccountID(info.getAccountID());
				tadi.setFixedDepositNo(info.getNewDepositNo());
				if (info.getIsCapitalAndInterestTransfer() == 1)
				{
					tadi.setAmount(info.getAmount() + info.getWithDrawInterest());
					tadi.setAmountType(SETTConstant.AmountType.AmountType_11);
				}
				else
				{
					tadi.setAmount(info.getAmount());
					tadi.setAmountType(SETTConstant.AmountType.AmountType_1);
				}
				
				//为账户对账单信息查询 所加
				if(info.getAccountID()>0)
				{
					try {
						com.iss.itreasury.settlement.account.dataentity.AccountInfo accountInfo = sett_accountDao.findByID(info.getAccountID());
						tadi.setOppAccountNo(accountInfo.getAccountNo());
						tadi.setOppAccountName(accountInfo.getAccountName());
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				tadi.setAmountType(SETTConstant.AmountType.AmountType_2);
				
				tadi.setTransSubAccountID(newFixedSubAccountID);
				

				log.debug("----------开始定期存入--------------");
				try
				{
					if (tadi.getTransSubAccountID() < 0)
					{
						long lSubAccountID1 =
							accBean.getFixedSubAccoutIDByAccoutIDAndFixedDepositNo(tadi.getTransAccountID(), tadi.getFixedDepositNo());
						tadi.setTransSubAccountID(lSubAccountID1);
					}

					log.debug("账户ID是:" + tadi.getTransSubAccountID());
					if (tadi.getTransSubAccountID() == -1)
					{
						throw new IException(
							"无法获得主账户ID是:" + tadi.getTransAccountID() + " 定期存单号是: " + tadi.getFixedDepositNo() + " 的定期子账户，交易失败");
					}

					accBean.depoist(tadi);
				}
				catch (IException e)
				{
					throw e;
				}
				log.debug("----------结束定期存入--------------");
			
				
				
				//accountOperation.depositFix(tadi);
				log.debug("---------结束定期存入-----------");
				if (info.getReceiveInterestAccountID() > 0)
				{ //利息转活期
					tadi.setOfficeID(info.getOfficeID());
					tadi.setCurrencyID(info.getCurrencyID());
					tadi.setTransactionTypeID(info.getTransactionTypeID());
					tadi.setDtExecute(info.getExecuteDate());
					tadi.setTransNo(info.getTransNo());
					tadi.setAmount(info.getAmount());
					//交易方向在账户中设置
					tadi.setDtInterestStart(info.getInterestStartDate());
					tadi.setAbstractStr(info.getAbstract());
					tadi.setTransSubAccountID(info.getSubAccountID());
					tadi.setAbstractID(info.getAbstractID());
					//开立时在账户子系统中设置对方子账户（即新开里账户ID）
					tadi.setTransAccountID(info.getReceiveInterestAccountID());
					tadi.setAmount(info.getWithDrawInterest());
						//为账户对账单信息查询 所加
					if(info.getAccountID()>0)
					{
						try {
							com.iss.itreasury.settlement.account.dataentity.AccountInfo accountInfo = sett_accountDao.findByID(info.getAccountID());
							tadi.setOppAccountNo(accountInfo.getAccountNo());
							tadi.setOppAccountName(accountInfo.getAccountName());
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					tadi.setAmountType(SETTConstant.AmountType.AmountType_2);
					currentSubAccountID =accBean.depositCurrent(tadi);
				}
				//@TBD通存通兑处理InterbranchSettlement()
				
				//产生会计分录
				GenerateGLEntryParam param = new GenerateGLEntryParam();
				/**
				 * 如果收/付款银行账户ID>0，则本金 流向lPrincipalType =2银行，否则本金流向lPrincipalType =1 
				 */
				long lPrincipalType = SETTConstant.CapitalType.INTERNAL;
				long lInterestType = -1;
				if (info.getInterestBankID() > 0)
				{
					//本金流向是 银行
					lInterestType = SETTConstant.CapitalType.BANK;
				}
				else
				{
					//本金流向是 内部转账
					lInterestType = SETTConstant.CapitalType.INTERNAL;
				}
				//分录类型 无关
				long lEntryType = -1;
				if (info.getIsCapitalAndInterestTransfer() == 1)
				{
					lEntryType = SETTConstant.EntryType.MERGER;
				}
				else
				{
					lEntryType = SETTConstant.EntryType.DIVIDE;
				}
				//收款方账户
				long receiveAccountID = newFixedSubAccountID;
				//付款方账户
				long payAccountID = fixedWithdrawSubAccountID;
				//收息账户ID
				long receiveInterestAccountID = currentSubAccountID;
				//long principalBankID =
				//利息开户行ID
				long interestBankID = info.getInterestBankID();
				//发生额
				double dAmount = info.getAmount();
				//利息合计
				double totalInterest = info.getWithDrawInterest();
				//计提利息
				double preDrawInterest = info.getPreDrawInterest();
				//未计提利息
				double unPreDrawInterest = info.getPayableInterest();
				
				//Boxu Add 2008年2月12日 活期利息(逾期利息)
				double overTimeInterest = info.getCurrentInterest();
				
				//本息合计
				double totalPrincipalAndInterest = dAmount + totalInterest;
				//豁免利息
				double remissionInterest = info.getPreDrawInterest();
				
				param.setOfficeID(info.getOfficeID());
				param.setCurrencyID(info.getCurrencyID());
				param.setTransactionTypeID(info.getTransactionTypeID());
				param.setExecuteDate(info.getExecuteDate());
				param.setInterestStartDate(info.getInterestStartDate());
				param.setTransNo(info.getTransNo());
				param.setAbstractStr(info.getAbstract());
				param.setInputUserID(info.getInputUserID());
				param.setCheckUserID(info.getCheckUserID());
				param.setPrincipalType(lPrincipalType);
				param.setEntryType(lEntryType);
				param.setReceiveAccountID(receiveAccountID);
				param.setPayAccountID(payAccountID);
				param.setReceiveInterestAccountID(receiveInterestAccountID);
				param.setInterestBankID(interestBankID);
				param.setInterestType(lInterestType);
				param.setPrincipalOrTransAmount(dAmount);
				param.setTotalInterest(totalInterest);
				param.setPreDrawInterest(preDrawInterest);
				param.setUnPreDrawInterest(unPreDrawInterest);
				param.setTotalPrincipalAndInterest(totalPrincipalAndInterest);
				
				//Boxu Add 2008年2月12日 活期利息分录
				param.setOverTimeInterest(overTimeInterest);
				
				//Boxu Add 2008年3月24日
				param.setRemissionInterest(remissionInterest);  //红字 豁免/冲销计提利息/正常利息
				boolean res = ledgerBean.generateGLEntry(param);
				if (!res)
				{
					throw new IException("产生会计分录错误2");
				}		
				
				//是否有银企接口
				boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
				//是否需要生成银行指令
				boolean bCreateInstruction = false;
				long bankID = info.getInterestBankID();
				try {
					//调用此方法后，bankID的值变为银行类型ID
					bCreateInstruction = accountBookOperation.isCreateInstruction(bankID);
				} catch (Exception e1) {
					log.error("判断传入的银行ID是否需要生成银行指令出错！");
					e1.printStackTrace();
				}
				
				try
				{
					if(bIsValid && bCreateInstruction) {
						log.debug("------开始定期续期转存交易指令生成，利息--------");
						try {
							log.debug("------开始定期续期转存交易指令生成，利息--------");
							//构造参数
							CreateInstructionParam instructionParam = new CreateInstructionParam();
							instructionParam.setTransactionTypeID(info.getTransactionTypeID());
							instructionParam.setObjInfo(info);
							instructionParam.setOfficeID(info.getOfficeID());
							instructionParam.setCurrencyID(info.getCurrencyID());
							instructionParam.setCheckUserID(info.getCheckUserID());
							instructionParam.setBankType(bankID);
							instructionParam.setInputUserID(info.getInputUserID());
							
							//生成银行指令并保存
							IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
							bankInstruction.createBankInstruction(instructionParam);
							
							log.debug("------生成定期续期转存交易指令成功，利息--------");
							
						} catch (Exception e) {
							log.error("生成定期续期转存交易指令失败");
							e.printStackTrace();
							throw  e;
						}
						log.debug("------结束定期续期转存交易指令生成，利息--------");
					}
				}
				catch (Exception e)
				{
					throw e;
				}
				
				//交易符合结束
				ocLog.addCloseDetailLog(openCloseId, info.getTransactionTypeID(), info.getID(), info.getTransNo(), SETTConstant.CloseSystemLogType.FIXEDDEPOSITAUTOCONTINUE);
			}
			if(transConn ==null)
			{
				conn.commit();
			}
			isPassed = true;
		} 
		catch (Exception e) 
		{
			if(transConn == null)
			{
				conn.rollback();
			}
			openCloseId = ocLog.addCloseLogReturnId(SETTConstant.CloseSystemLogType.FIXEDDEPOSITAUTOCONTINUE,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,"自动续存业务处理失败\n"+e.getMessage());
			//ocLog.addCloseDetailLog(openCloseId,SETTConstant.TransactionType.AUTOFIXEDCONTINUETRANSFER, fixedInfo.getID(), info.getDepositNo(), SETTConstant.CloseSystemLogType.FIXEDDEPOSITAUTOCONTINUE);
			ocLog.addCloseDetailLog(openCloseId,SETTConstant.TransactionType.FIXEDCONTINUETRANSFER, fixedInfo.getID(), info.getDepositNo(), SETTConstant.CloseSystemLogType.FIXEDDEPOSITAUTOCONTINUE);
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if(transConn == null)
			{
				if(conn!=null)
				{
					conn.close();
					conn=null;
				}
			}
		}
			return isPassed;
		}
}
