package com.iss.itreasury.ebank.approval.dao;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.ebank.approval.dataentity.InutApprovalRecordInfo;
import com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.ebank.approval.dataentity.OBInutWorkInfo;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.ebank.util.PagedStatement;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.system.approval.dataentity.InutApprovalWorkInfo;

import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.OBFSWorkflowManager;
import com.iss.system.dao.PageLoader;

public class OBQueryApprovalRecordDao extends ITreasuryDAO implements java.io.Serializable
{

	public OBQueryApprovalRecordDao()
	{

		// super("SYS_APPROVALRELATION");
	}

	// 业务类型
	public static final long	QUERYCURRENTWORK			= 1;

	public static final long	QUERYHISTORYWORK			= 2;

	public static final long	QUERYFINISHEDWORK			= 3;

	public static final long	QUERYWORKFORCANCELAPPROVE	= 4;

	StringBuffer				m_sbSelect					= null;

	StringBuffer				m_sbFrom					= null;

	StringBuffer				m_sbWhere					= null;

	StringBuffer				m_sbOrderBy					= null;

	private String mergeString(Object[] objs)
	{

		if (objs == null || objs.length == 0)
		{
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < objs.length; i++)
		{
			sb.append(objs[i].toString()).append(",");
		}
		return sb.toString().substring(0, sb.toString().length() - 1);
	} 

	public Collection queryMyWork(OBInutWorkInfo qInfo) throws IException
	{

		Vector v_Return = new Vector();
		ResultSet localRS = null;
		try
		{
			this.initDAO();
			// 构造查询类
			InutParameterInfo pInfo = new InutParameterInfo();
			pInfo.setSessionMng(qInfo.getSessionMng());
			// 调用待办业务查询接口
			HashMap hm = new HashMap();

			switch ((int) qInfo.getQueryPurpose())
			{
				case (int) QUERYCURRENTWORK :
					hm = OBFSWorkflowManager.getCurrentList(pInfo);
					break;
				case (int) QUERYHISTORYWORK :
					hm = OBFSWorkflowManager.getHistoryList(pInfo);
					break;
				case (int) QUERYFINISHEDWORK :
					hm = OBFSWorkflowManager.getFinishedList(pInfo);
					break;
				case (int) QUERYWORKFORCANCELAPPROVE :
					hm = OBFSWorkflowManager.getFinishedList(pInfo);
					break;
			}
			System.out.println("hm.size = " + hm.size());
			if (hm != null && hm.size() > 0)
			{
				String strApprovalEntryIDs = this.mergeString(hm.keySet().toArray());

				String strSQL = "select distinct ft.id,ar.*,ft.* " + " from ob_approvalrecord ar,ob_financeinstr ft"
						+ " where ft.id = ar.transid and ar.approvalentryid in (" + strApprovalEntryIDs + ")";
				if (qInfo.getTransactionTypeID() > 0)
				{
					strSQL += " and ar.transtypeid = " + qInfo.getTransactionTypeID();
				}
				// 排序
				 strSQL += " order by ft.id  ";

				System.out.println("sql = " + strSQL.toString());
				prepareStatement(strSQL.toString());
				localRS = executeQuery();

				while (localRS.next())
				{

					OBInutWorkInfo workInfo = new OBInutWorkInfo();

					workInfo.setOfficeID(localRS.getLong("OfficeID"));
					workInfo.setCurrencyID(localRS.getLong("CurrencyID"));

					workInfo.setTransNo(localRS.getString("ID")); // 交易编号
					workInfo.setTransactionTypeID(localRS.getLong("TransTypeID"));

					workInfo.setExecute(localRS.getTimestamp("DTExecute")); // 交易日期
					workInfo.setInputUserID(localRS.getLong("nConfirmUserID")); // 录入人ID
					workInfo.setPayAmount(localRS.getDouble("mAmount"));

					long payAccountID = localRS.getLong("nPayerAcctID"); // 付款方账户ID
					String payAccountNO = getPayAccountNOByID(payAccountID);
					workInfo.setPayAccountNo(payAccountNO);

					long recAccountID = localRS.getLong("nPayeeAcctID"); // 收款方账户ID
				    //String recAccountNO = getRecAccountNOByID(recAccountID);
					//modify by xwhe 2008-05-7
					long transType = localRS.getLong("TransTypeID");
					long remitType = localRS.getLong("NremitType");
					//逐笔付款(内部转账),定期支取(内部转账),通知支取(内部转账)
					if( transType == OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT
							|| (transType == OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER && remitType== OBConstant.SettRemitType.INTERNALVIREMENT)
							|| (transType ==OBConstant.SettInstrType.NOTIFYDEPOSITDRAW && remitType==OBConstant.SettRemitType.INTERNALVIREMENT))
					{	
					String recAccountNO = NameRef.getAccountNoByID(recAccountID);
					workInfo.setReceiveAccountNo(recAccountNO);
					}
					else
					{
					String recAccountNO = getRecAccountNOByID(recAccountID);
					workInfo.setReceiveAccountNo(recAccountNO);
					}
					workInfo.setApprovalEntryID(localRS.getLong("ApprovalEntryID"));
					workInfo.setLinkUrl(localRS.getString("LinkUrl"));
					// 定期续存
					if (transType == OBConstant.SettInstrType.DRIVEFIXDEPOSIT)
					{	
						workInfo.setReceiveAccountNo("");
					}
					// 通知开立和定期开立/modify by xwhe 2008-05-09
					if (transType == OBConstant.SettInstrType.OPENNOTIFYACCOUNT || transType== OBConstant.SettInstrType.OPENFIXDEPOSIT)
					{
						// 从结算表取
						String recAccountNoTemp = getPayAccountNOByID(recAccountID);
						workInfo.setReceiveAccountNo(recAccountNoTemp);
					}

					// 对应的审批流引擎的待办信息
					workInfo.setInutWorkInfo((InutApprovalWorkInfo) hm.get(String
							.valueOf(workInfo.getApprovalEntryID())));
					if(!(transType==OBConstant.SettInstrType.BUDGETNEW
							||transType==OBConstant.SettInstrType.BUDGETADJUST || transType==OBConstant.SettInstrType.ONLINEBANK_BANKPAY)){
						v_Return.add(workInfo);
					}
					/*
					if(!(transType==OBConstant.SettInstrType.ONLINEBANK_BANKPAY)){
						v_Return.add(workInfo);
					}
					*/
					
				}
				
				
				
				//Modified by zwsun, 2007/7/20, 用款计划待办业务
				if(qInfo.getTransactionTypeID()<=0
						||qInfo.getTransactionTypeID()==OBConstant.SettInstrType.BUDGETNEW
						||qInfo.getTransactionTypeID()==OBConstant.SettInstrType.BUDGETADJUST){
					strSQL = "select ar.*, b.inputuser, b.inputdate, b.startdate, b.enddate, b.id as budgetId " + " from ob_approvalrecord ar, ob_budget b"
							+ " where ar.transid=b.id and ar.approvalentryid in (" + strApprovalEntryIDs + ")";
					if (qInfo.getTransactionTypeID() > 0)
					{
						strSQL += " and ar.transtypeid = " + qInfo.getTransactionTypeID();
					}
					System.out.println("sql = " + strSQL.toString());
					prepareStatement(strSQL.toString());
					localRS = executeQuery();
					while (localRS.next())
					{

						OBInutWorkInfo workInfo = new OBInutWorkInfo();

						workInfo.setOfficeID(localRS.getLong("OfficeID"));
						workInfo.setCurrencyID(localRS.getLong("CurrencyID"));

						workInfo.setTransNo(Long.toString(localRS.getLong("budgetId"))); // 预算id
						workInfo.setTransactionTypeID(localRS.getLong("TransTypeID"));
						workInfo.setExecute(localRS.getTimestamp("inputdate")); // 录入日期
						workInfo.setInputUserID(localRS.getLong("inputuser")); // 录入人ID
						workInfo.setBudgetStartDate(localRS.getDate("startdate"));//用款计划开始日期
						workInfo.setBudgetEndDate(localRS.getDate("enddate"));//用款计划结束日期	
						//得到总预算金额
						//begin
						strSQL = "select sum(amount) as budgetAmount from ob_budget a where a.parentbudgetid = "+localRS.getLong("budgetId");
						prepareStatement(strSQL.toString());
						ResultSet budgetRS = executeQuery();
						while(budgetRS.next()){
							workInfo.setPayAmount(budgetRS.getDouble("budgetAmount"));						
						}
						//end
						workInfo.setApprovalEntryID(localRS.getLong("ApprovalEntryID"));
						workInfo.setLinkUrl(localRS.getString("LinkUrl"));
						// 对应的审批流引擎的待办信息
						workInfo.setInutWorkInfo((InutApprovalWorkInfo) hm.get(String
								.valueOf(workInfo.getApprovalEntryID())));
						if(localRS.getLong("transtypeid")==OBConstant.SettInstrType.BUDGETNEW
								||localRS.getLong("transtypeid")==OBConstant.SettInstrType.BUDGETADJUST){
							v_Return.add(workInfo);
						}
					}
				}	
				
				
				//Modified by liangwang, 2007/7/20, 银行付款待办业务
				if(qInfo.getTransactionTypeID()<=0
						||qInfo.getTransactionTypeID()==OBConstant.SettInstrType.ONLINEBANK_BANKPAY){
					strSQL = "select distinct bk.id,ar.*,bk.* " + " from ob_approvalrecord ar,OB_BANKPAY bk"
					+ " where bk.id = ar.transid and ar.approvalentryid in (" + strApprovalEntryIDs + ")";
					if (qInfo.getTransactionTypeID() > 0)
					{
						strSQL += " and ar.transtypeid = " + qInfo.getTransactionTypeID();
					}
					strSQL += " order by bk.id " ;
					System.out.println("sql = " + strSQL.toString());
					prepareStatement(strSQL.toString());
					localRS = executeQuery();
					while (localRS.next())
					{

						OBInutWorkInfo workInfo = new OBInutWorkInfo();

						workInfo.setOfficeID(localRS.getLong("OfficeID"));
						workInfo.setCurrencyID(localRS.getLong("CurrencyID"));

						workInfo.setTransNo(localRS.getString("id")); // 交易编号
						workInfo.setTransactionTypeID(localRS.getLong("TransTypeID"));

						workInfo.setExecute(localRS.getTimestamp("DTExecute")); // 交易日期
						workInfo.setInputUserID(localRS.getLong("nConfirmUserID")); // 录入人ID
						workInfo.setPayAmount(localRS.getDouble("mamount"));

						long payAccountID = localRS.getLong("npayeracctid"); // 付款方账户ID
						String payAccountNO = getPayAccountNOByIDForJSP_BankPay(payAccountID);
						workInfo.setPayAccountNo(payAccountNO);
						

						long recAccountID = localRS.getLong("npayeeacctid"); // 收款方账户ID
						String recAccountNO = getRecAccountNOByID(recAccountID);
						workInfo.setReceiveAccountNo(recAccountNO);

						workInfo.setApprovalEntryID(localRS.getLong("ApprovalEntryID"));
						workInfo.setLinkUrl(localRS.getString("LinkUrl"));
					
						// 对应的审批流引擎的待办信息
						workInfo.setInutWorkInfo((InutApprovalWorkInfo) hm.get(String
								.valueOf(workInfo.getApprovalEntryID())));
						if(localRS.getLong("transtypeid")==OBConstant.SettInstrType.ONLINEBANK_BANKPAY){
							v_Return.add(workInfo);
						}
					
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001", e);
		}
		finally
		{
			this.finalizeDAO();
		}

		return v_Return;
	}

	/**
	 * 取消审批 信息列表
	 * 
	 * @throws Exception
	 */
	public Collection queryWorkForCancelApprove(OBInutWorkInfo qInfo) throws Exception
	{

		Vector v_Return = new Vector();
		ResultSet localRS = null;
		String strSQL = "";
		try
		{
			initDAO();
			strSQL = "select distinct ft.NTRANSTYPE transType," + // 交易类型
					"ft.ID transNO," + // 交易编号
					"ft.NPAYERACCTID payAccountID," + // 付款方
					"ft.NPAYEEACCTID recAccountID," + // 收款方
					"ft.FIXEDINTERESTTOACCOUNTID fixedInterestToAccountID," + // 利息出入账户
					"ft.MAMOUNT amount," + // 交易金额
					"ft.NCONFIRMUSERID inputUserID ," + // 录入人
					"ft.NREMITTYPE  nremittype," +   //汇款方式
					"ft.DTEXECUTE execute , " + "ap.APPROVALENTRYID approveEntryID, " + "ap.LINKURL linkUrl "
					+ "from OB_APPROVALRECORD ap , OB_FINANCEINSTR ft where 1=1 ";

			strSQL = strSQL + " and ap.transID = ft.ID ";
			strSQL = strSQL + " and ap.STATUSID = " + Constant.RecordStatus.VALID + " ";
			if (qInfo.getTransactionTypeID() > 0)
			{
				strSQL = strSQL + " and ft.ntransType = " + qInfo.getTransactionTypeID();
			}
			// 是否是自动复核
			if (!OBFSWorkflowManager.isAutoCheck())
			{
				if (qInfo.getUserID() > 0)
				{
					strSQL = strSQL + " and ap.LASTAPPUSERID = " + qInfo.getUserID();
				}
				strSQL = strSQL +" and ft.NSTATUS="+OBConstant.SettInstrStatus.APPROVALED;
			}
			else
			{
				if (qInfo.getUserID() > 0)
				{
					strSQL = strSQL + " and ft.NCHECKUSERID = " + qInfo.getUserID();
				}
				strSQL = strSQL +" and ft.NSTATUS="+OBConstant.SettInstrStatus.CHECK;
			}
			strSQL = strSQL + " order by ft.ID desc";
			System.out.println("取消审批显示列表=" + strSQL.toString());
			prepareStatement(strSQL.toString());
			localRS = executeQuery();
			while (localRS.next())
			{

				OBInutWorkInfo workInfo = new OBInutWorkInfo();

				workInfo.setTransNo(localRS.getString("transNO")); // 交易编号
				workInfo.setTransactionTypeID(localRS.getLong("transType"));

				workInfo.setExecute(localRS.getTimestamp("execute")); // 交易日期
				workInfo.setInputUserID(localRS.getLong("inputUserID")); // 录入人ID
				workInfo.setPayAmount(localRS.getDouble("amount"));

				long payAccountID = localRS.getLong("payAccountID"); // 付款方账户ID
				String payAccountNO = getPayAccountNOByID(payAccountID);
				workInfo.setPayAccountNo(payAccountNO);

				long recAccountID = localRS.getLong("recAccountID"); // 收款方账户ID
			    //String recAccountNO = getRecAccountNOByID(recAccountID);
				//modify by xwhe 2008-05-07
				long transType = localRS.getLong("transType");
				long nremittype= localRS.getLong("nremittype");
               //逐笔付款(内部转账),定期支取(内部转账),通知支取(内部转账)
				if( nremittype == OBConstant.SettRemitType.INTERNALVIREMENT)
				{	
				String recAccountNO = NameRef.getAccountNoByID(recAccountID);
				workInfo.setReceiveAccountNo(recAccountNO);
				}
				else
				{
				String recAccountNO = getRecAccountNOByID(recAccountID);
				workInfo.setReceiveAccountNo(recAccountNO);
				}
				workInfo.setApprovalEntryID(localRS.getLong("approveEntryID"));
				workInfo.setLinkUrl(localRS.getString("linkUrl"));

				// 到期续存
				// 如果将利息转出到活期帐户,则收款方为此活期帐户,否则为空
				// 利息转出到的账户,此账户维护在收款方信息中
				
				if (transType == OBConstant.SettInstrType.DRIVEFIXDEPOSIT)
				{
					//到期续存收款方为空
					workInfo.setReceiveAccountNo("");
				}

				// 通知开立
				if (transType == OBConstant.SettInstrType.OPENNOTIFYACCOUNT || transType== OBConstant.SettInstrType.OPENFIXDEPOSIT)
				{
					// 从结算表取
					String recAccountNoTemp = getPayAccountNOByID(recAccountID);
					workInfo.setReceiveAccountNo(recAccountNoTemp);
				}
				if(!(transType==OBConstant.SettInstrType.BUDGETNEW
						||transType==OBConstant.SettInstrType.BUDGETADJUST 
						|| transType==OBConstant.SettInstrType.ONLINEBANK_BANKPAY)){
					v_Return.add(workInfo);
				}
				/*
				if(!(transType==OBConstant.SettInstrType.ONLINEBANK_BANKPAY)){
					v_Return.add(workInfo);
				}
				*/

			}
			//Modified by zwsun, 2007/7/20, 用款计划待办业务
			if(qInfo.getTransactionTypeID()<=0
					||qInfo.getTransactionTypeID()==OBConstant.SettInstrType.BUDGETNEW
					||qInfo.getTransactionTypeID()==OBConstant.SettInstrType.BUDGETADJUST){
				strSQL = "select ap.transTypeId transType," + // 交易类型
				"ft.ID transNO," + // 交易编号
				"ft.inputUser inputUserID ," + // 录入人
				"ft.startDate, ft.endDate, "+
				"ft.inputDate execute , " + "ap.APPROVALENTRYID approveEntryID, " + "ap.LINKURL linkUrl "
				+ "from OB_APPROVALRECORD ap , OB_BUDGET ft where 1=1 ";
				strSQL = strSQL + " and ap.transID = ft.ID ";
				strSQL = strSQL + " and ap.STATUSID = " + Constant.RecordStatus.VALID + " ";
				if (qInfo.getTransactionTypeID() > 0)
				{
					strSQL = strSQL + " and ap.transTypeId = " + qInfo.getTransactionTypeID();
				}
				// 是否是自动复核
				/*if (!OBFSWorkflowManager.isAutoCheck())
				{
					if (qInfo.getUserID() > 0)
					{
						strSQL = strSQL + " and ap.LASTAPPUSERID = " + qInfo.getUserID();
					}
					strSQL = strSQL +" and ft.STATUS="+OBConstant.SettInstrStatus.APPROVALED;
				}
				else
				{*/
					if (qInfo.getUserID() > 0)
					{
						strSQL = strSQL + " and ft.checkuser = " + qInfo.getUserID();
					}
					strSQL = strSQL +" and ft.STATUS="+OBConstant.OBBudgetStatus.APPROVE;
//				}
				System.out.println("sql = " + strSQL.toString());
				prepareStatement(strSQL.toString());
				localRS = executeQuery();
				while (localRS.next())
				{

					OBInutWorkInfo workInfo = new OBInutWorkInfo();

					workInfo.setTransNo(Long.toString(localRS.getLong("transNO"))); // 预算id
					workInfo.setTransactionTypeID(localRS.getLong("transType"));
					workInfo.setExecute(localRS.getTimestamp("execute")); // 录入日期
					workInfo.setInputUserID(localRS.getLong("inputUserID")); // 录入人ID
					workInfo.setBudgetStartDate(localRS.getDate("startDate"));//用款计划开始日期
					workInfo.setBudgetEndDate(localRS.getDate("endDate"));//用款计划结束日期	
					//得到总预算金额
					//begin
					strSQL = "select sum(amount) as budgetAmount from ob_budget a where a.parentbudgetid = "+localRS.getLong("transNo");
					prepareStatement(strSQL.toString());
					ResultSet budgetRS = executeQuery();
					while(budgetRS.next()){
						workInfo.setPayAmount(budgetRS.getDouble("budgetAmount"));						
					}
					//end
					workInfo.setApprovalEntryID(localRS.getLong("approveEntryID"));
					workInfo.setLinkUrl(localRS.getString("linkUrl"));

					if(localRS.getLong("transType")==OBConstant.SettInstrType.BUDGETNEW
							||localRS.getLong("transType")==OBConstant.SettInstrType.BUDGETADJUST){
						v_Return.add(workInfo);
					}
				}
			}
			
			
			//Modified by liangwang, 2007/7/20, 银行付款待办业务
			if(qInfo.getTransactionTypeID()<=0
					||qInfo.getTransactionTypeID()==OBConstant.SettInstrType.ONLINEBANK_BANKPAY){
			
				
				strSQL = "select ft.NTRANSTYPE transType," + // 交易类型
				"ft.ID transNO," + // 交易编号
				"ft.NPAYERACCTID payAccountID," + // 付款方
				"ft.NPAYEEACCTID recAccountID," + // 收款方
				//"ft.FIXEDINTERESTTOACCOUNTID fixedInterestToAccountID," + // 利息出入账户
				"ft.MAMOUNT amount," + // 交易金额
				"ft.NCONFIRMUSERID inputUserID ," + // 录入人
				"ft.DTEXECUTE execute , " + "ap.APPROVALENTRYID approveEntryID, " + "ap.LINKURL linkUrl "
				+ "from OB_APPROVALRECORD ap , OB_BANKPAY ft where 1=1 ";
				strSQL = strSQL + " and ap.transID = ft.ID ";
				strSQL = strSQL + " and ap.STATUSID = " + Constant.RecordStatus.VALID + " ";
				if (qInfo.getTransactionTypeID() > 0)
				{
					strSQL = strSQL + " and ft.NTRANSTYPE = " + qInfo.getTransactionTypeID();
				}
				// 是否是自动复核
				if (!OBFSWorkflowManager.isAutoCheck())
				{
					if (qInfo.getUserID() > 0)
					{
						strSQL = strSQL + " and ap.LASTAPPUSERID = " + qInfo.getUserID();
					}
					strSQL = strSQL +" and ft.NSTATUS="+OBConstant.SettInstrStatus.APPROVALED;
				}
				else
				{
					if (qInfo.getUserID() > 0)
					{
						strSQL = strSQL + " and ft.NCHECKUSERID = " + qInfo.getUserID();
					}
					strSQL = strSQL +" and ft.NSTATUS="+OBConstant.SettInstrStatus.CHECK;
				}
				strSQL = strSQL + " order by transNO desc ";
				System.out.print("银行直联SQL："+ strSQL);
				prepareStatement(strSQL.toString());
				localRS = executeQuery();
				while (localRS.next())
				{

					OBInutWorkInfo workInfo = new OBInutWorkInfo();

					workInfo.setTransNo(localRS.getString("transNO")); // 交易编号
					workInfo.setTransactionTypeID(localRS.getLong("transType"));

					workInfo.setExecute(localRS.getTimestamp("execute")); // 交易日期
					workInfo.setInputUserID(localRS.getLong("inputUserID")); // 录入人ID
					workInfo.setPayAmount(localRS.getDouble("amount"));

					long payAccountID = localRS.getLong("payAccountID"); // 付款方账户ID
					String payAccountNO = getPayAccountNOByIDForJSP_BankPay(payAccountID);
					workInfo.setPayAccountNo(payAccountNO);

					long recAccountID = localRS.getLong("recAccountID"); // 收款方账户ID
					String recAccountNO = getRecAccountNOByID(recAccountID);
					workInfo.setReceiveAccountNo(recAccountNO);

					workInfo.setApprovalEntryID(localRS.getLong("approveEntryID"));
					workInfo.setLinkUrl(localRS.getString("linkUrl"));

					
					if(localRS.getLong("transType")==OBConstant.SettInstrType.ONLINEBANK_BANKPAY){
						v_Return.add(workInfo);
					}
	
			
	
				}
			}
	
	
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001", e);
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e1)
			{
				e1.printStackTrace();
			}
		}

		return v_Return;
	}
	/**
	 * Added by zwsun, 2007/7/26
	 * 查询预算已办任务
	 * 
	 * @throws Exception
	 */
	public PageLoader getHistoryListBudget(OBInutWorkInfo conditionworkInfo, long ldesc, long lOrderField) throws Exception
	{

		getHistoryListBudgetSql(conditionworkInfo, ldesc, lOrderField);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
				.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(new AppContext(), m_sbFrom.toString(), m_sbSelect.toString(), m_sbWhere.toString(),
				(int) Constant.PageControl.CODE_PAGELINECOUNT,
				"com.iss.itreasury.ebank.approval.dataentity.OBInutResultInfo", null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}	
	private void getHistoryListBudgetSql(OBInutWorkInfo conditionworkInfo, long ldesc, long lOrderField)
	{

		m_sbSelect = new StringBuffer();
		m_sbSelect
				.append(" ft.id id,ft.inputUser inputUserID,ft.inputDate execute,ar.transtypeId transactionTypeID,ar.linkurl linkUrl, \n ");
		m_sbSelect
				.append(" ft.startDate budgetStartDate, ft.endDate budgetEndDate, ");		
		m_sbSelect
				.append(" his.entry_id entryID,his.action_code actionCode,his.step_code stepCode,his.stepname stepName,his.modelname wfDefineName,his.owner owner, \n");
		m_sbSelect
				.append(" (select sum(amount) from ob_budget where parentBudgetId=ft.id) payAmount");

		// from
		m_sbFrom = new StringBuffer();
		m_sbFrom.append("  v_os_ob_histroystep his,ob_approvalrecord ar,ob_budget ft \n");
		// where
		m_sbWhere = new StringBuffer();
		m_sbWhere.append("  his.entry_id = ar.approvalentryid \n");
		m_sbWhere.append("  and ft.id = ar.transid \n");
		m_sbWhere.append("  and his.owner = '" + conditionworkInfo.getUserID() + "' \n");	
		
		if (conditionworkInfo.getTransactionTypeID() > 0)
		{
			m_sbWhere.append(" and  ar.transtypeid =" + conditionworkInfo.getTransactionTypeID() + " \n");
		}

		if (conditionworkInfo.getDtExecuteFrom() != null)
			m_sbWhere.append("		  and nvl(ft.inputDate,to_date('9000-01-01','yyyy-mm-dd')) >= to_date('"
					+ DataFormat.getDateString(conditionworkInfo.getDtExecuteFrom()) + "','yyyy-mm-dd')	");
		if (conditionworkInfo.getDtExecuteTo() != null)
			m_sbWhere.append("		  and nvl(ft.inputDate,to_date('9000-01-01','yyyy-mm-dd')) <= to_date('"
					+ DataFormat.getDateString(conditionworkInfo.getDtExecuteTo()) + "','yyyy-mm-dd')	");

		m_sbOrderBy = new StringBuffer();
		String strDesc = ldesc == 2 ? " desc " : " asc";
		if (lOrderField > 0)
		{
			switch ((int) lOrderField)
			{
				case 1 :
					m_sbOrderBy.append(" \n order by ft.inputDate" + strDesc);
					break;
			}
		}
	}
	
	
	/**
	 * Added by zwsun, 2007/7/26
	 * 查询预算已办任务
	 * 
	 * @throws Exception
	 */
	public PageLoader getHistoryListBankPay(OBInutWorkInfo conditionworkInfo, long ldesc, long lOrderField) throws Exception
	{

		getHistoryListBankPaySql(conditionworkInfo, ldesc, lOrderField);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
				.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(new AppContext(), m_sbFrom.toString(), m_sbSelect.toString(), m_sbWhere.toString(),
				(int) Constant.PageControl.CODE_PAGELINECOUNT,
				"com.iss.itreasury.ebank.approval.dataentity.OBInutResultInfo", null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}	
	private void getHistoryListBankPaySql(OBInutWorkInfo conditionworkInfo, long ldesc, long lOrderField)
	{


		m_sbSelect = new StringBuffer();
		m_sbSelect
				.append(" ft.ID financeinstrID,ft.nConfirmUserID inputUserID,ft.dtexecute execute,ft.ntranstype transactionTypeID,ar.linkurl linkUrl, \n ");
		m_sbSelect
				.append(" his.entry_id entryID,his.action_code actionCode,his.step_code stepCode,his.stepname stepName,his.modelname wfDefineName,his.owner owner, \n");
		m_sbSelect
				.append(" ft.npayeracctid payAccountID ,ft.npayeeacctid receiveAccountID,ft.mAmount payAmount");

		// from
		m_sbFrom = new StringBuffer();
		m_sbFrom.append("  v_os_ob_histroystep his,ob_approvalrecord ar,OB_BANKPAY ft \n");
		// where
		m_sbWhere = new StringBuffer();
		m_sbWhere.append("  his.entry_id = ar.approvalentryid \n");
		m_sbWhere.append("  and ft.id = ar.transid \n");
		m_sbWhere.append("  and his.owner = '" + conditionworkInfo.getUserID() + "' \n");

		if (conditionworkInfo.getTransactionTypeID() > 0)
		{
			m_sbWhere.append(" and  ar.transtypeid =" + conditionworkInfo.getTransactionTypeID() + " \n");
		}

		if (conditionworkInfo.getDtExecuteFrom() != null)
			m_sbWhere.append("		  and nvl(ft.dtexecute,to_date('9000-01-01','yyyy-mm-dd')) >= to_date('"
					+ DataFormat.getDateString(conditionworkInfo.getDtExecuteFrom()) + "','yyyy-mm-dd')	");
		if (conditionworkInfo.getDtExecuteTo() != null)
			m_sbWhere.append("		  and nvl(ft.dtexecute,to_date('9000-01-01','yyyy-mm-dd')) <= to_date('"
					+ DataFormat.getDateString(conditionworkInfo.getDtExecuteTo()) + "','yyyy-mm-dd')	");

		m_sbOrderBy = new StringBuffer();
		String strDesc = ldesc == 2 ? " desc " : " asc";
		if (lOrderField > 0)
		{
			switch ((int) lOrderField)
			{
				case 1 :
					m_sbOrderBy.append(" \n order by ft.dtExecute" + strDesc);
					break;
			}
		}
	}
	
	/**
	 * 查询已办任务
	 * 
	 * @throws Exception
	 */
	public PageLoader getHistoryList(OBInutWorkInfo conditionworkInfo, long ldesc, long lOrderField) throws Exception
	{

		getHistoryListSql(conditionworkInfo, ldesc, lOrderField);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
				.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(new AppContext(), m_sbFrom.toString(), m_sbSelect.toString(), m_sbWhere.toString(),
				(int) Constant.PageControl.CODE_PAGELINECOUNT1,
				"com.iss.itreasury.ebank.approval.dataentity.OBInutResultInfo", null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}

	public void getHistoryListSql(OBInutWorkInfo conditionworkInfo, long ldesc, long lOrderField)
	{

		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" DISTINCT * \n");
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" ( SELECT DISTINCT ft.ID  financeinstrID,ft.nConfirmUserID inputUserID,ft.dtexecute  execute,ft.ntranstype transactionTypeID,ar.linkurl linkUrl,\n ");
		m_sbFrom.append(" his.entry_id   entryID,his.action_code   actionCode,his.step_code   stepCode,his.stepname  stepName, his.modelname wfDefineName,");
		m_sbFrom.append("his.owner owner,ft.npayeracctid  payAccountID,ft.npayeeacctid  receiveAccountID,ft.mAmount payAmount, ft.nstatus nremittype");
		m_sbFrom.append(" FROM v_os_ob_histroystep his, ob_approvalrecord ar, OB_BANKPAY ft");
		m_sbFrom.append(" where his.entry_id = ar.approvalentryid \n");
		m_sbFrom.append("  and ft.id = ar.transid \n");
		m_sbFrom.append("  and his.owner = '" + conditionworkInfo.getUserID() + "' \n");
		if (conditionworkInfo.getTransactionTypeID() > 0)
		{
			m_sbFrom.append(" and  ar.transtypeid =" + conditionworkInfo.getTransactionTypeID() + " \n");
		}
		if (conditionworkInfo.getDtExecuteFrom() != null)
			m_sbFrom.append("		  and nvl(ft.dtexecute,to_date('9000-01-01','yyyy-mm-dd')) >= to_date('"
					+ DataFormat.getDateString(conditionworkInfo.getDtExecuteFrom()) + "','yyyy-mm-dd')	");
		if (conditionworkInfo.getDtExecuteTo() != null)
			m_sbFrom.append("		  and nvl(ft.dtexecute,to_date('9000-01-01','yyyy-mm-dd')) <= to_date('"
					+ DataFormat.getDateString(conditionworkInfo.getDtExecuteTo()) + "','yyyy-mm-dd')	");	
		m_sbFrom.append("UNION \n");
		m_sbFrom.append(" SELECT DISTINCT ft.ID financeinstrID,ft.nConfirmUserID inputUserID,ft.dtexecute execute,ft.ntranstype transactionTypeID,ar.linkurl linkUrl, \n ");
		m_sbFrom.append(" his.entry_id entryID,his.action_code actionCode,his.step_code stepCode,his.stepname stepName,his.modelname wfDefineName,his.owner owner, \n");
		m_sbFrom.append(" ft.npayeracctid payAccountID ,ft.npayeeacctid receiveAccountID,ft.mAmount payAmount, ft.nremittype nremittype");
		m_sbFrom.append(" from v_os_ob_histroystep his,ob_approvalrecord ar,ob_financeinstr ft \n");
		m_sbFrom.append("  where his.entry_id = ar.approvalentryid \n");
		m_sbFrom.append("  and ft.id = ar.transid \n");
		m_sbFrom.append("  and his.owner = '" + conditionworkInfo.getUserID() + "' \n");
		if (conditionworkInfo.getTransactionTypeID() > 0)
		{
			m_sbFrom.append(" and  ar.transtypeid =" + conditionworkInfo.getTransactionTypeID() + " \n");
		}

		if (conditionworkInfo.getDtExecuteFrom() != null)
			m_sbFrom.append("		  and nvl(ft.dtexecute,to_date('9000-01-01','yyyy-mm-dd')) >= to_date('"
					+ DataFormat.getDateString(conditionworkInfo.getDtExecuteFrom()) + "','yyyy-mm-dd')	");
		if (conditionworkInfo.getDtExecuteTo() != null)
			m_sbFrom.append("		  and nvl(ft.dtexecute,to_date('9000-01-01','yyyy-mm-dd')) <= to_date('"
					+ DataFormat.getDateString(conditionworkInfo.getDtExecuteTo()) + "','yyyy-mm-dd') \n");
		m_sbFrom.append(" ) \n");
		m_sbWhere = new StringBuffer();
		m_sbWhere.append("  1=1 \n");

		m_sbOrderBy = new StringBuffer();		
		m_sbOrderBy.append(" \n order by FINANCEINSTRID desc" );
				
	
	}

	// 从结算表中取账号
	private String getPayAccountNOByID(long accountID) throws Exception
	{

		String accountNO = "";
		ResultSet rs = null;
		try
		{

			String sql = "select t.* from sett_account t where t.id = " + accountID + "";

			prepareStatement(sql);
			rs = executeQuery();
			while (rs.next())
			{
				accountNO = rs.getString("saccountno");
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		return accountNO;
	}

	// 从网银的收款方信息表中取收款账号
	private String getRecAccountNOByID(long accountID) throws Exception
	{

		String accountNO = "";
		ResultSet rs = null;

		String sql = "select t.* from ob_payeeinfo t where t.id = " + accountID + "";

		try
		{
			prepareStatement(sql);
			rs = executeQuery();
			while (rs.next())
			{
				accountNO = rs.getString("spayeeacctno");
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		return accountNO;
	}

	// 从结算表中取账号 供页面调用
	public String getPayAccountNOByIDForJSP(long accountID, long fixedInterestToAccountID, long transType)
			throws Exception
	{

		String accountNO = "";
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try
		{

//			if (transType == OBConstant.SettInstrType.DRIVEFIXDEPOSIT)
//			{
//				return accountNO = "";
//			}

			String sql = "select t.* from sett_account t where t.id = " + accountID + "";

			con = Database.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				accountNO = rs.getString("saccountno");
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
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
				;
			}
		}
		return accountNO;
	}	
	// 从网银的收款方信息表中取收款账号 供页面调用
	public String getRecAccountNOByIDForJSP(long accountID, long fixedInterestToAccountID, long transType)
			throws Exception
	{

		String accountNO = "";
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		try
		{
			String sql = "select t.* from ob_payeeinfo t where t.id = " + accountID + "";
			if (transType == OBConstant.SettInstrType.DRIVEFIXDEPOSIT)
			{
				sql = "select t.* from ob_payeeinfo t where t.id = " + fixedInterestToAccountID + "";
			}
			// 通知开立和定期开立//modify by xwhe 2008-05-09
			else if (transType == OBConstant.SettInstrType.OPENNOTIFYACCOUNT || transType == OBConstant.SettInstrType.OPENFIXDEPOSIT)
			{
				sql = "select t.* from sett_account t where t.id = " + accountID + "";
			}

			con = Database.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{				
				if (transType == OBConstant.SettInstrType.OPENNOTIFYACCOUNT ||transType == OBConstant.SettInstrType.OPENFIXDEPOSIT)
				{
					accountNO = rs.getString("saccountno");
				}
				else
				{
					accountNO = rs.getString("spayeeacctno");
				}

			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
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
				;
			}
		}
		return accountNO;
	}
	
	
	// 通过付款方账号ID，获得银行直联的付款方账户号
	public String getPayAccountNOByIDForJSP_BankPay(long accountID)
			throws Exception
	{

		String accountNO = "";
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try
		{
			String sql = "select s_accountno from bs_bankaccountinfo  where n_id = " + accountID + "";
			con = Database.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				accountNO = rs.getString("s_accountno");
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
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
				;
			}
		}
		return accountNO;
	}

}
