/*
 * Copyright (c) 2003-2005 iss.com. All Rights Reserved.
 *
 * ���幦��˵��������ȡ�õ������ѵĺ�ͬ��
 *
 * ʹ��ע�����
 * 1
 * 2
 *
 * ���ߣ�hyzeng
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
	 * function �����Ͻ���ϵͳ�ʽ�����µ�
	 * ���н�������ҵ���
	 * δ���ˡ�δǩ�ϡ�����ɡ��Ѿܾ�
	 * ״̬��������
	 */
	public String getCapInstrByStatus(OBAwakeCondition info) throws Exception
	{

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		String sResult = "";

		String sTransName = ""; //ҵ������
		long lTotal = 0; //����
		String sTransStatus = ""; //ҵ��״̬
		long lTransStatus = -1; //ҵ��״̬
		long lTransType = -1; //ҵ������
		long lTempType = -1;

		try
		{
			sb.append("SELECT COUNT(id) as total,nTransType,nStatus ");
			sb.append(" FROM ob_financeInstr ");
			sb.append(" WHERE nCurrencyId = ?");
			sb.append(" AND nClientID = ?");
			sb.append(" AND nStatus IN (" + OBConstant.SettInstrStatus.SAVE); //δ����
			sb.append("," + OBConstant.SettInstrStatus.CHECK); //δǩ��
			sb.append("," + OBConstant.SettInstrStatus.FINISH); //�����
			sb.append("," + OBConstant.SettInstrStatus.REFUSE); //�Ѿܾ�
			sb.append(" )");
			sb.append(" AND TO_CHAR(dtConfirm,'YYYYMMDD') = TO_CHAR(SYSDATE,'YYYYMMDD')");
			sb.append(" GROUP BY nTransType,nStatus");

			log4j.info("���Ͻ���ϵͳ�ʽ�����µ����н�������ҵ��ġ�δ���ˡ�����δǩ�ϡ�:");
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
					sResult += sTransName + "����";

					sResult += "<a href='#' onClick=parent.parent.window.location";
					sResult += "='../capital/query/q002-c.jsp?lStatus=" + lTransStatus;
					sResult += "&sStartSubmit=" + DataFormat.getDateString(info.getCon());
					sResult += "&sEndSubmit=" + DataFormat.getDateString(info.getCon());
					sResult += "&lTransType=" + lTempType + "&fromAccountType=yes'>";
					sResult += lTotal;
					sResult += "</a>";

					sResult += "��ҵ����" + sTransStatus + "&nbsp;&nbsp;&nbsp;&nbsp;";
				}
				else if (lTransStatus == OBConstant.SettInstrStatus.FINISH || lTransStatus == OBConstant.SettInstrStatus.REFUSE)
				{
					sResult += "���죬��";

					sResult += "<a href='#' onClick=parent.parent.window.location";
					sResult += "='../capital/query/q002-c.jsp?lStatus=" + lTransStatus;
					sResult += "&sStartSubmit=" + DataFormat.getDateString(info.getCon());
					sResult += "&sEndSubmit=" + DataFormat.getDateString(info.getCon());
					sResult += "&lTransType=" + lTempType + "&fromAccountType=yes'>";
					sResult += lTotal;
					sResult += "</a>";

					sResult += "��" + sTransName + sTransStatus + "&nbsp;&nbsp;&nbsp;&nbsp;";
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
	 * function ���ڴ�������
	 * 
	 */
	public String getOpenFixdePositMsg(OBAwakeCondition info, boolean bool) throws RemoteException, Exception
	{
		String sResult = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		long id = -1;
		String sDepositNo = ""; //���ڴ浥��
		long lCount = 0; //��������
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

			log4j.info(" ���ڴ�������: \n" + sb.toString());
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
				sResult += "���ڿ������ݺ�:";
				
				sResult += "<a href='../capital/query/q101-c.jsp?id="+ id +"' target=_blank >";
				sResult += sDepositNo;
				sResult += "</a>";
				
				if(bool == true) {
					if(lCount == 0) {
						sResult += "���ڽ��쵽��&nbsp;&nbsp;&nbsp;&nbsp;";
					}
					else {
						sResult += "����" + lCount + "�����&nbsp;&nbsp;&nbsp;&nbsp;";
					}
				}
				else {
					sResult += "�ѹ���" + Math.abs(lCount) + "��&nbsp;&nbsp;&nbsp;&nbsp;";
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
	 * @param flag true: ��ʾ�������ѣ�false��ʾ��������
	 * function ֪ͨ���֧ȡ֪ͨ����
	 * 
	 */	
	private String getNotifyDepositDrawMsg(OBAwakeCondition info, boolean flag) throws RemoteException, Exception{
		String sResult = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sDepositNo = ""; //�浥��
		long lCount = 0; //��������
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

			log4j.info(" ֪ͨ���֧ȡ֪ͨ����: \n" + sb.toString());
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
				sResult += "֪֧ͨȡ";
				
				sResult += "<a href='../capital/notifyinform/control/c004.jsp?id="+ rs.getLong("id") +"&control=display' target=_blank >";
				sResult += sDepositNo;
				sResult += "</a>";
				
				if(lCount == 0) {
					sResult += "���ڽ��쵽��&nbsp;&nbsp;&nbsp;&nbsp;";
				}
				else  if(flag){
					sResult += "����" + lCount + "�����&nbsp;&nbsp;&nbsp;&nbsp;";
				}else{
					sResult += "�ѹ���" + (-lCount) + "��&nbsp;&nbsp;&nbsp;&nbsp;";
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
	 * function ���ڴ�������
	 * 
	 */
	public String getFixedAttermMsg(OBAwakeCondition info) throws RemoteException, Exception
	{
		String sResult = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sDepositNo = ""; //���ڴ浥��
		Timestamp tsEndDate = null; //��������
		double dAmount = 0; //���ڴ����
		Timestamp tsCurrent = Env.getSystemDateTime();
		long lCount = 0; //��������
		try
		{

			sb.append(" SELECT sDepositNo,dtEnd,mAmount,");
			sb.append(" (SYSDATE-dtEnd) as dtCount");
			sb.append(" FROM sett_TransOpenFixedDeposit");
			sb.append(" WHERE nStatusID = 3"); //״̬Ϊ�Ѹ���
			sb.append(" AND nTransactionTypeID =" + SETTConstant.TransactionType.OPENFIXEDDEPOSIT); //���ڿ���
			sb.append(" AND nCurrencyId = ?");
			sb.append(" AND nClientID = ?");
			sb.append(" ORDER BY sDepositNo");

			log4j.info(" ���ڴ�������: \n" + sb.toString());
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
					sResult += "����" + DataFormat.formatDate(tsEndDate) + "����&nbsp;&nbsp;&nbsp;&nbsp;";
				}
				else if (dAmount > 0 && lCount > 0)
				{
					sResult += "<a href='#' onClick=parent.parent.window.location";
					sResult += "='../capital/fixed/f011-c.jsp'>";
					sResult += sDepositNo;
					sResult += "</a>";
					sResult += "�Ѿ�����" + lCount + "��&nbsp;&nbsp;&nbsp;&nbsp;";
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
	 * function �ʽ�ƻ���������
	 * 
	 */
	public String getOBCapitalPlanMsg() throws RemoteException, Exception
	{
		String sResult = "";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		Timestamp startdate = null; //ԭ��ʼ����
		long period = -1; //ԭ�ÿ�ƻ�����
		Timestamp oldstartdate = null; //��һ����ʼ����
		long oldperiod = -1; //��һ���ÿ�ƻ�����
		Timestamp modifydate = null;//�޸�ʱ��
		try
		{

			sb.append(" select * from SETT_PERIODSETTING ");

			log4j.info(" �ʽ�ƻ���������: \n" + sb.toString());
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
								sResult += "�ʽ�ƻ��ѱ��޸�!&nbsp;&nbsp;&nbsp;&nbsp;";
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
	 * function ���������
	 * 
	 */
	public String getLoanAttermMsg(OBAwakeCondition info) throws RemoteException, Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sResult = "";
		String sCode = ""; //�ſ�֪ͨ�����
		String sContractCode = ""; //��ͬ���
		Timestamp tsEndDate = null; //�ſ�֪ͨ����������
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

			log4j.info("���������:\n " + sb.toString());
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
					sResult += "��ͬ" + sContractCode + " �ſ" + sCode + "����";
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
					sResult += "����&nbsp;&nbsp;&nbsp;&nbsp;";
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
	* function ���Ƚ�Ϣ��������
	* 
	*/
	public String getInterestSettlementMsg(OBAwakeCondition info) throws RemoteException, Exception
	{
		String sResult = "";

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sContractCode = ""; //��ͬ���
		Timestamp tsEndDate = null; //������������ 
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
			sb.append(" AND a.nStatusID = 1"); //״̬Ϊ��������
			sb.append(" AND b.nStatusId IN ");
			sb.append("(" + LOANConstant.ContractStatus.ACTIVE);
			sb.append("," + LOANConstant.ContractStatus.EXTEND);
			sb.append("," + LOANConstant.ContractStatus.OVERDUE);
			sb.append("," + LOANConstant.ContractStatus.DELAYDEBT);
			sb.append("," + LOANConstant.ContractStatus.BADDEBT + ")");
			sb.append(" AND b.nCurrencyId = ?");
			sb.append(" AND b.nBorrowClientID = ?");
			sb.append(" ORDER BY b.sContractCode");

			Log.print("���Ƚ�Ϣ��������:\n " + sb.toString());
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
					sResult += "��ͬ";
					sResult += "<a href='#' onClick=parent.parent.window.location";
					sResult += "='settnotice/s001-c.jsp?lNoticeTypeID=";
					sResult += SETTConstant.LoanNoticeType.LoanInterestNotice;
					sResult += "&lContractID=" + rs.getLong("id");
					sResult += "&lContractIDFromCtrl=" + sContractCode;
					sResult += "&lContractIDToCtrl=" + sContractCode + "'>";
					sResult += sContractCode;
					sResult += "</a>";
					sResult += " ����" + DataFormat.getChineseDateString(tsEndDate) + " ��Ϣ&nbsp;&nbsp;&nbsp;&nbsp;";
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
	 * function �������ڴ�������
	 * 
	 */
	public String getOverDueMsg(OBAwakeCondition info) throws RemoteException, Exception
	{
		String sResult = "";

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sContractCode = ""; //��ͬ���
		String sPayFormCode = ""; //�ſ�֪ͨ�����
		long lOverDate = 0; //������������
		Timestamp tsEndDate = null; //�������ڴ��յ���������
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
			sb.append(" AND a.nStatusID = 1"); //״̬Ϊ��������
			sb.append(" AND a.nFornType = 2"); //����Ϊ�����ڴ������֪ͨ�顱
			sb.append(" AND b.nCurrencyId = ?");
			sb.append(" AND b.nBorrowClientID = ?");
			sb.append(" AND d.mBalance > 0");
			sb.append(" ORDER BY a.sContractCode,a.sPayFormCode");

			Log.print("�������ڴ�������:\n " + sb.toString());
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
					sResult += "��ͬ" + sContractCode + " �µķſ" + sPayFormCode + " �Ѿ�����";
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
					sResult += "��&nbsp;&nbsp;&nbsp;&nbsp;";
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
							//���ڴ�������
							//Modify by leiyang date 2007/06/27
							if(operationFate != -1){
								info.setOperationFate(operationFate);
								sResult += getOpenFixdePositMsg(info, true);
							}
							break;
						case (int)OBConstant.OperationReminderType.NOTIFYDEPOSITDRAW :
							//֪֧ͨȡ�������ѣ�Added by zwsun, 2007-6-25
							if(operationFate != -1){
								info.setOperationFate(operationFate);
								sResult += getNotifyDepositDrawMsg(info, true);
							}
							break;
						case (int)OBConstant.OperationReminderType.CAPTRANSFER :
							//���Զ�����ʧ�ܵ�����
							if(operationFate != -1){
								OBBudgetBiz biz = new OBBudgetBiz();
								long count = biz.getAllFailedDealCount(lOfficeID,lCurrencyID,lClientID);
								if(count > 0){
									sResult = sResult + " ����<A href='../remindAutoTaskFail.jsp' target=_blank>&nbsp;"
											+ count +"&nbsp;</A>���Զ�����ִ��ʧ��&nbsp;&nbsp;&nbsp;&nbsp;";
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
							//Added by zwsun, 2007/6/28, ֪֧ͨȡ��������
							if(operationFate != -1){
								info.setOperationFate(operationFate);
								sResult += getNotifyDepositDrawMsg(info, false);
							}
							break;
					}
				}
			}
			
			
			
			//���������д�ӡ���������
			sResult += getrefuseprintapplyByStatus(info);
			
			// �����Ͻ���ϵͳ�ʽ�����µ�ҵ�����״̬����
			//sResult += getCapInstrByStatus(info);

			
			//���ڴ�������
			//sResult += getFixedAttermMsg(info);

			//��������� 
			/*sResult += getLoanAttermMsg(info);

			//���Ƚ�Ϣ��������
			sResult += getInterestSettlementMsg(info);

			//�������ڴ�������
			sResult += getOverDueMsg(info);
			
			//Ԥ������
			sResult += getAwakeApprove(info);//��������ҵ������
            sResult += getAwakeOfReceive(info);//�����յ�ҵ������
            sResult += getAwakeFlexibleWarning(info);//����Ԥ��������Ԥ������
            sResult += getAwakeRigidWarning(info);//�������Ա�����Ԥ������
            
            //�ʽ�ƻ���������
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
     * ���ܾ��Ĵ�ӡ����
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
			log4j.info(" ���ܾ��Ĵ�ӡ����: \n" + strSQL.toString());
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
				sResult += "��ӡ����";
				//<A href="/NASApp/iTreasury-ebank/print/control/c012_P.jsp?strSuccessPageURL=../view/v013_P.jsp&nextAction=view&strFailPageURL=../view/v012_P.jsp&strAction=toModify&payaccountno=06-01-0001-01&TransactionTypeID=2&TransNo=20060702060100061&lID=20164&_pageLoaderKey=-4574928153930518944">20060702060100061</a>
				sResult += "<A href='/NASApp/iTreasury-ebank/print/control/c012_P.jsp?strSuccessPageURL=../view/v014_P.jsp&nextAction=view&strFailPageURL=../view/v012_P.jsp&strAction=toModify&TransactionTypeID="+ rs.getLong("ntypeid") +"&TransNo="+ rs.getLong("nprintcontentno") +"&lID="+ rs.getLong("nprintcontentid") +"&control=display' target=_blank >";
				sResult += rs.getString("nprintcontentno");
				sResult += "</a>";
				sResult += "���ܾ�&nbsp;&nbsp;&nbsp;&nbsp;";
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
     * �õ���������ҵ������
     * ��ѯԤ�������һ�������Ϊ��ǰ��λ��Ԥ��
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
    		sResult="����"+ c.size() +"�ʴ�������ҵ��  ";
    	}
        
        return sResult;
    }
    
    /**
     * �õ������յ�ҵ������
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
    		sResult="����"+ c.size() +"�ʴ����յ�ҵ��  ";
    	}
        
        return sResult;
    }
    
    /**
     * �õ�����Ԥ��������Ԥ�����ѣ���Ը��Կ���
     * @param info,��ǰ���´������µ���ϵ�µ����е�ǰ�ͻ���Ȩ�޵���Ŀ�ļ��
     * �Ե�ǰ���´�ID�ͱ���ID��Ϊ����(�û�ID�Ƿ�ҲӦ����Ϊ������)����ѯԤ����ϵ��Ԥ����Ŀģ���Ԥ��ִ��������ܱ��ѯ��Ŀ�ļ�¼
     * ״̬ΪĿǰĬ��������Ϊ1
     * ���ݲ�ѯ������ʵ��ִ����/Ԥ�����õ���ֵ��Ԥ�������Ƚϣ������Ԥ����������ʾ
     * @return
     * @throws Exception
     */
    public String getAwakeFlexibleWarning(OBAwakeCondition info)throws Exception
    {
    	PreparedStatement ps = null;
        StringBuffer sbSQL = null;
        ResultSet rs = null;
        String sResult = " ";
        String itemName="";//��Ŀ����
        double WarnScale;//���Ա���
        double BudgetAmount=0.0;//Ԥ����
        double ExecuteAmount=0.0;//ʵ��ִ����
        double lastDouble=0.0;//����õ�����
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
            	//Ԥ���������Ϊ0�������Ƚ�
            	if(WarnScale!=0){
            		if(BudgetAmount!=0.0 && BudgetAmount!=0){//�ж�Ԥ�����Ƿ�Ϊ0
            			lastDouble=ExecuteAmount/BudgetAmount;     //�õ�ʵ��ִ����/Ԥ�����õ���ֵ               		
            			if(lastDouble>WarnScale){//����õ���ֵ�������Ԥ������������ʾ
                			sResult+="��Ŀ"+itemName+"����Ԥ��Ԥ��  ";            			
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
     * �õ��������Ա�����Ԥ�����ѣ�������Կ���
     * @param info,��ǰ���´������µ���ϵ�µ����е�ǰ�ͻ���Ȩ�޵���Ŀ�ļ��
     * �Ե�ǰ���´�ID�ͱ���ID��Ϊ����(�û�ID�Ƿ�ҲӦ����Ϊ������)����ѯԤ����ϵ��Ԥ����Ŀģ���Ԥ��ִ��������ܱ��ѯ��Ŀ�ļ�¼
     * ״̬ΪĿǰĬ��������Ϊ1
     * ���ݲ�ѯ������ʵ��ִ����/Ԥ�����õ���ֵ�����Ա����Ƚϣ���������Ա�������ʾ
     * @return
     * @throws Exception
     */
    
    public String getAwakeRigidWarning(OBAwakeCondition info)throws Exception
    {
    	PreparedStatement ps = null;
        StringBuffer sbSQL = null;
        ResultSet rs = null;
        String sResult = " ";
        String itemName="";//��Ŀ����
        double SuppleScale;//���Ա���
        double BudgetAmount=0.0;//Ԥ����
        double ExecuteAmount=0.0;//ʵ��ִ����
        double lastDouble=0.0;//����õ�����
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
            	//���Ա������Ϊ0�����Ƚ�
            	if(SuppleScale!=0){
            		if(BudgetAmount!=0.0 && BudgetAmount!=0){//�ж�Ԥ�����Ƿ�Ϊ0
            			lastDouble=ExecuteAmount/BudgetAmount;     //�õ�ʵ��ִ����/Ԥ�����õ���ֵ  
            			if(lastDouble>SuppleScale){//����õ���ֵ����������Ա���������ʾ
            				sResult+="��Ŀ"+itemName+"��������Ԥ��  ";            			
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
