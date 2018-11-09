/*
 * Created on 2007-4-12
 *
 * Title:				iTreasury
 * @author             	刘琰 
 * Company:             iSoftStone
 * @version				iTreasury3.2新增
 * Description:         产品化3.2在结算,网银新增审批流,该功能实现查询我的工作      
 */
package com.iss.itreasury.settlement.mywork.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;

import com.iss.itreasury.settlement.mywork.dataentity.SettInutWorkInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.approval.dataentity.InutApprovalWorkInfo;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;

public class SettInutWorkDao extends ITreasuryDAO implements java.io.Serializable
{	
	public SettInutWorkDao()
	{
		//super("SYS_APPROVALRELATION");
	}
	
	//业务类型
	public static final long QUERYCURRENTWORK = 1;
	public static final long QUERYHISTORYWORK = 2;
	public static final long QUERYFINISHEDWORK = 3;
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
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
		return sb.toString().substring(0, sb.toString().length()-1);
	}
	
	public Collection queryMyWork(SettInutWorkInfo qInfo) throws IException
	{
		Vector v_Return = new Vector();
		ResultSet localRS = null;
		
		try
		{
			this.initDAO();
			//构造查询类
			InutParameterInfo pInfo = new InutParameterInfo();
			pInfo.setUserID(qInfo.getUserID());
			//调用待办业务查询接口
			HashMap hm = new HashMap();
			
			switch ((int)qInfo.getQueryPurpose())
			{
				case (int)QUERYCURRENTWORK:
					hm = FSWorkflowManager.getCurrentList(pInfo);
					break;
				case (int)QUERYHISTORYWORK:
					hm = FSWorkflowManager.getHistoryList(pInfo);
					break;
				case (int)QUERYFINISHEDWORK:
					hm = FSWorkflowManager.getFinishedList(pInfo);
					break;
			}	
							
			if(hm!=null && hm.size()>0)
			{
				String strApprovalEntryIDs = this.mergeString(hm.keySet().toArray());
				//INSTR('"+strApprovalEntryIDs+"',ar.approvalentryid) orderpara ,
			
				String strSQL = "select ar.*,vt.* "
								+" from sys_approvalrecord ar,sett_vtransaction vt"
								+ " where vt.transno = ar.transid and ar.moduleid = "
								+ Constant.ModuleType.SETTLEMENT
								+ " and ar.approvalentryid in ("
								+ strApprovalEntryIDs + ")";
			
								//+ " and ar.OfficeID = " + qInfo.getOfficeID()
								//+ " and ar.CurrencyID = " + qInfo.getCurrencyID();				
				
				//排序
				//strSQL += " order by orderpara ";
					if(qInfo.getTransactionTypeID()>0)
					{
						strSQL += " and ar.TransTypeID = "+qInfo.getTransactionTypeID();
					}
				
				prepareStatement(strSQL.toString());
				//System.out.print("-------sql = "+strSQL);
				localRS = executeQuery();
				while (localRS.next())
				{
					SettInutWorkInfo workInfo = new SettInutWorkInfo();
					
					workInfo.setOfficeID(localRS.getLong("OfficeID"));
					workInfo.setCurrencyID(localRS.getLong("CurrencyID"));
					workInfo.setUserID(qInfo.getUserID());//待办人的id
					workInfo.setTransNo(localRS.getString("transno"));
					workInfo.setTransactionTypeID(localRS.getLong("TransactionTypeID"));
					workInfo.setInterestStart(localRS.getTimestamp("InterestStart"));
					workInfo.setExecute(localRS.getTimestamp("Execute"));
					workInfo.setStatusID(localRS.getLong("StatusID"));
					workInfo.setInputUserID(localRS.getLong("InputUserID"));				
					workInfo.setPayAmount(localRS.getDouble("PayAmount"));				
					workInfo.setReceiveAmount(localRS.getDouble("receiveAmount"));					
					workInfo.setBankID(localRS.getLong("bankID"));				
					workInfo.setContractID(localRS.getLong("contractID"));					
					workInfo.setLoanFormID(localRS.getLong("LoanFormID"));					
					workInfo.setDepositNo(localRS.getString("DepositNo"));
					workInfo.setPayAccountNo(localRS.getString("PayAccountNo"));				
					workInfo.setReceiveAccountNo(localRS.getString("ReceiveAccountNo"));				
					workInfo.setPayClientName(localRS.getString("PayClientName"));
					workInfo.setReceiveClientName(localRS.getString("ReceiveClientName"));
					//workInfo.setBankAccountCode(localRS.getString("BankAccountCode"));
					workInfo.setApprovalEntryID(localRS.getLong("ApprovalEntryID"));
					workInfo.setLinkUrl(localRS.getString("LinkUrl"));
				
					//对应的审批流引擎的待办信息
					workInfo.setInutWorkInfo((InutApprovalWorkInfo)hm.get(String.valueOf(workInfo.getApprovalEntryID())));
					
					v_Return.add(workInfo);
				}
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001",e);
		}
		finally
		{
			this.finalizeDAO();
		}
		
		return v_Return;
	}
	
	
	public Collection queryAdjustMyWork(SettInutWorkInfo qInfo) throws IException
	{
		Vector v_Return = new Vector();
		ResultSet localRS = null;
		
		try
		{
			this.initDAO();
			//构造查询类
			InutParameterInfo pInfo = new InutParameterInfo();
			pInfo.setUserID(qInfo.getUserID());
			//调用待办业务查询接口
			HashMap hm = new HashMap();
			
			switch ((int)qInfo.getQueryPurpose())
			{
				case (int)QUERYCURRENTWORK:
					hm = FSWorkflowManager.getCurrentList(pInfo);
					break;
				case (int)QUERYHISTORYWORK:
					hm = FSWorkflowManager.getHistoryList(pInfo);
					break;
				case (int)QUERYFINISHEDWORK:
					hm = FSWorkflowManager.getFinishedList(pInfo);
					break;
			}	
							
			if(hm!=null && hm.size()>0)
			{
				String strApprovalEntryIDs = this.mergeString(hm.keySet().toArray());
				//INSTR('"+strApprovalEntryIDs+"',ar.approvalentryid) orderpara ,
			
				String strSQL = "select ar.*,aj.ndeposittype depositType,aj.naccountid accountID,aj.ncontractid contractID," 
					            +" aj.nduebillid duebillID,aj.dtexecute execute"
								+" from sys_approvalrecord ar,sett_adjustinterest aj"
								+ " where aj.naccountid = ar.transid and ar.moduleid = "
								+ Constant.ModuleType.SETTLEMENT
								+ " and ar.approvalentryid in ("
								+ strApprovalEntryIDs + ")";
			
								//+ " and ar.OfficeID = " + qInfo.getOfficeID()
								//+ " and ar.CurrencyID = " + qInfo.getCurrencyID();				
				
				//排序
				//strSQL += " order by orderpara ";
					if(qInfo.getTransactionTypeID()>0)
					{
						strSQL += " and ar.TransTypeID = "+qInfo.getTransactionTypeID();
					}
				
				prepareStatement(strSQL.toString());
				//System.out.print("-------sql = "+strSQL);
				localRS = executeQuery();
				while (localRS.next())
				{
					SettInutWorkInfo workInfo = new SettInutWorkInfo();
					
					workInfo.setOfficeID(localRS.getLong("OfficeID"));
					workInfo.setCurrencyID(localRS.getLong("CurrencyID"));
					workInfo.setAccountID(localRS.getLong("naccountid"));
					workInfo.setContractID(localRS.getLong("ncontractid"));
					workInfo.setDuebillID(localRS.getLong("nduebillid"));
					workInfo.setDepositType(localRS.getLong("ndeposittype"));
					workInfo.setExecute(localRS.getTimestamp("dtexecute"));
					workInfo.setApprovalEntryID(localRS.getLong("ApprovalEntryID"));
					workInfo.setLinkUrl(localRS.getString("LinkUrl"));
				
					//对应的审批流引擎的待办信息
					workInfo.setInutWorkInfo((InutApprovalWorkInfo)hm.get(String.valueOf(workInfo.getApprovalEntryID())));
					
					v_Return.add(workInfo);
				}
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001",e);
		}
		finally
		{
			this.finalizeDAO();
		}
		
		return v_Return;
	}
	
	/**
	 * 查询账户开立，已修改账户的代办业务
	 * @author haoliang
	 * @param SettInutWorkInfo qInfo
	 * @return Collection
	 * @throws IException
	 * @date 2007-06-21
	 */
	public Collection queryAccountMyWork(SettInutWorkInfo qInfo) throws IException
	{
		Vector v_Return = new Vector();
		ResultSet localRS = null;
		String strSQL = "";
		try
		{
			this.initDAO();
			//构造查询类
			InutParameterInfo pInfo = new InutParameterInfo();
			pInfo.setUserID(qInfo.getUserID());
			//调用待办业务查询接口
			HashMap hm = new HashMap();
			
			switch ((int)qInfo.getQueryPurpose())
			{
				case (int)QUERYCURRENTWORK:
					hm = FSWorkflowManager.getCurrentList(pInfo);
					break;
				case (int)QUERYHISTORYWORK:
					hm = FSWorkflowManager.getHistoryList(pInfo);
					break;
				case (int)QUERYFINISHEDWORK:
					hm = FSWorkflowManager.getFinishedList(pInfo);
					break;
			}	
							
			if(hm!=null && hm.size()>0)
			{
				String strApprovalEntryIDs = this.mergeString(hm.keySet().toArray());
			    strSQL = "select ap.*,ac.*"
								+" from sys_approvalrecord ap,sett_account ac"
								+ " where ac.saccountno = ap.transid and ap.moduleid = "
								+ Constant.ModuleType.SETTLEMENT
								+ " and ap.approvalentryid in ("
								+ strApprovalEntryIDs + ") and ac.NSTATUSID<>0";
				System.out.println("AccountCurrentWork--strSQL===="+strSQL);
				prepareStatement(strSQL.toString());
				localRS = executeQuery();
				while (localRS.next())
				{
					
					SettInutWorkInfo workInfo = new SettInutWorkInfo();
					workInfo.setAccountNo(localRS.getString("saccountno"));
					workInfo.setAccountTypeID(localRS.getLong("naccounttypeid"));
					workInfo.setOpenDate(localRS.getTimestamp("dtopen"));
					workInfo.setStatusID(localRS.getLong("nstatusid"));
					workInfo.setInputUserID(localRS.getLong("ninputuserid"));
					workInfo.setTransactionTypeID(localRS.getLong("transtypeid"));
					//workInfo.setOfficeID(localRS.getLong("nofficeid"));
					//workInfo.setCurrencyID(localRS.getLong("ncurrencyid"));
					//sworkInfo.setUserID(qInfo.getUserID());//待办人的id
//					workInfo.setTransNo(localRS.getString("saccountno"));
//					workInfo.setTransactionTypeID(localRS.getLong("naccounttypeid"));
//					workInfo.setStatusID(localRS.getLong("nstatusid"));
//					//workInfo.setInterestStart(localRS.getTimestamp("InterestStart"));
//					workInfo.setExecute(localRS.getTimestamp("Execute"));
//					workInfo.setInputUserID(localRS.getLong("InputUserID"));				
//					workInfo.setPayAmount(localRS.getDouble("PayAmount"));				
//					workInfo.setReceiveAmount(localRS.getDouble("receiveAmount"));					
//					workInfo.setBankID(localRS.getLong("bankID"));				
//					workInfo.setContractID(localRS.getLong("contractID"));					
//					workInfo.setLoanFormID(localRS.getLong("LoanFormID"));					
//					workInfo.setDepositNo(localRS.getString("DepositNo"));
//					workInfo.setPayAccountNo(localRS.getString("PayAccountNo"));				
//					workInfo.setReceiveAccountNo(localRS.getString("ReceiveAccountNo"));				
//					workInfo.setPayClientName(localRS.getString("PayClientName"));
//					workInfo.setReceiveClientName(localRS.getString("ReceiveClientName"));
//					//workInfo.setBankAccountCode(localRS.getString("BankAccountCode"));
					
					workInfo.setApprovalEntryID(localRS.getLong("ApprovalEntryID"));
					workInfo.setLinkUrl(localRS.getString("LinkUrl"));
				    
					//对应的审批流引擎的待办信息
					workInfo.setInutWorkInfo((InutApprovalWorkInfo)hm.get(String.valueOf(workInfo.getApprovalEntryID())));
					v_Return.add(workInfo);
				}
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001",e);
		}
		finally
		{
			this.finalizeDAO();
		}
		
		return v_Return;
	}
	
	/**
     * 查询拒绝账户业务
     * @throws Exception 
     */
	public PageLoader queryAccountRefuseWork(SettInutWorkInfo conditionworkInfo,long ldesc,long lOrderField) throws Exception
    {
		getAccountRefuseWorkSql(conditionworkInfo,ldesc,lOrderField);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(new AppContext(), m_sbFrom.toString(), m_sbSelect.toString(), m_sbWhere.toString(), (int) Constant.PageControl.CODE_PAGELINECOUNT,
				"com.iss.itreasury.settlement.mywork.dataentity.SettInutWorkInfo", null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
    }
	
	public void getAccountRefuseWorkSql(SettInutWorkInfo conditionworkInfo,long ldesc,long lOrderField)
	{
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" distinct ac.id id ,re.approvalentryid approvalEntryID ,re.transtypeid transactionTypeID,re.transid transNo,\n ");
		m_sbSelect.append(" ac.saccountno AccountNo,ac.naccounttypeid AccountTypeID,ac.ninputuserid InputUserID,ac.dtopen OpenDate,ac.nstatusid StatusID");
		m_sbFrom = new StringBuffer();
		m_sbFrom.append("  sys_approvalrecord re,sett_account ac \n");
		m_sbWhere = new StringBuffer();
		m_sbWhere.append("  re.transid = ac.saccountno \n");
		if(conditionworkInfo.getOfficeID()>0)
			m_sbWhere.append(" and  re.officeid="+conditionworkInfo.getOfficeID()+" \n");
		if(conditionworkInfo.getCurrencyID()>0)
			m_sbWhere.append(" and re.currencyid="+conditionworkInfo.getCurrencyID()+" \n");
		if(conditionworkInfo.getModuleID()>0)
			m_sbWhere.append(" and re.moduleid="+conditionworkInfo.getModuleID()+" \n");
		if(conditionworkInfo.getTransactionTypeID()>0)
			m_sbWhere.append("  and re.transtypeid="+conditionworkInfo.getTransactionTypeID()+" \n");
		//if(conditionworkInfo.getStatusID()>0)
			m_sbWhere.append(" and ( ac.ncheckstatusid ="+SETTConstant.AccountCheckStatus.NEWSAVE+" or ac.ncheckstatusid ="+SETTConstant.AccountCheckStatus.OLDSAVE+" ) \n");
		if(conditionworkInfo.getUserID()>0)
			m_sbWhere.append(" and ac.ninputuserid = "+conditionworkInfo.getUserID()+" \n");
		m_sbOrderBy = new StringBuffer();
		String strDesc = ldesc == 2 ? " desc " : " asc";
		if(lOrderField>0)
		{
//			switch ((int) lOrderField)
//			{
//				case 1:
//					m_sbOrderBy.append(" \n order by re.transtypeid" + strDesc);
//					break;
//				case 2:
//					m_sbOrderBy.append(" \n order by re.transid" + strDesc);
//					break;
//				case 3:
//					m_sbOrderBy.append(" \n order by vt.payaccountno" + strDesc);
//					break;
//				case 4:
//					m_sbOrderBy.append(" \n order by vt.receiveaccountno" + strDesc);
//					break;
//				case 5:
//					m_sbOrderBy.append(" \n order by re.inputuserid" + strDesc);
//					break;
//				case 6:
//					m_sbOrderBy.append(" \n order by re.execute" + strDesc);
//					break;
//			}
		}
	}
	
	
	/**
     * 查询拒绝业务
     * @throws Exception 
     */
	public PageLoader queryRefuseWork(SettInutWorkInfo conditionworkInfo,long ldesc,long lOrderField) throws Exception
    {
		getRefuseWorkSql(conditionworkInfo,ldesc,lOrderField);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(new AppContext(), m_sbFrom.toString(), m_sbSelect.toString(), m_sbWhere.toString(), (int) Constant.PageControl.CODE_PAGELINECOUNT,
				"com.iss.itreasury.settlement.mywork.dataentity.SettInutWorkInfo", null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
    }
	
	public void getRefuseWorkSql(SettInutWorkInfo conditionworkInfo,long ldesc,long lOrderField)
	{
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" distinct vt.id id ,re.transtypeid transactionTypeID,re.transid transNo,vt.payaccountno payAccountNo,vt.receiveaccountno receiveAccountNo, \n ");
		m_sbSelect.append(" vt.payamount payAmount,vt.receiveamount receiveAmount,vt.inputuserid inputUserID,vt.execute execute");
		//		 from
		m_sbFrom = new StringBuffer();
		m_sbFrom.append("  sys_approvalrecord re,sett_vtransaction vt \n");
		// where
		m_sbWhere = new StringBuffer();
		m_sbWhere.append("  re.transid = vt.transno \n");
		if(conditionworkInfo.getOfficeID()>0)
			m_sbWhere.append(" and  re.officeid="+conditionworkInfo.getOfficeID()+" \n");
		if(conditionworkInfo.getCurrencyID()>0)
			m_sbWhere.append(" and re.currencyid="+conditionworkInfo.getCurrencyID()+" \n");
		if(conditionworkInfo.getModuleID()>0)
			m_sbWhere.append(" and re.moduleid="+conditionworkInfo.getModuleID()+" \n");
		if(conditionworkInfo.getTransactionTypeID()>0)
			m_sbWhere.append("  and re.transtypeid="+conditionworkInfo.getTransactionTypeID()+" \n");
		if(conditionworkInfo.getStatusID()>0)
			m_sbWhere.append(" and vt.statusid ="+conditionworkInfo.getStatusID()+" \n");
		if(conditionworkInfo.getUserID()>0)
			m_sbWhere.append(" and vt.inputuserid = "+conditionworkInfo.getUserID()+" \n");
		m_sbOrderBy = new StringBuffer();
		String strDesc = ldesc == 2 ? " desc " : " asc";
		if(lOrderField>0)
		{
			switch ((int) lOrderField)
			{
				case 1:
					m_sbOrderBy.append(" \n order by re.transtypeid" + strDesc);
					break;
				case 2:
					m_sbOrderBy.append(" \n order by re.transid" + strDesc);
					break;
				case 3:
					m_sbOrderBy.append(" \n order by vt.payaccountno" + strDesc);
					break;
				case 4:
					m_sbOrderBy.append(" \n order by vt.receiveaccountno" + strDesc);
					break;
				case 5:
					m_sbOrderBy.append(" \n order by re.inputuserid" + strDesc);
					break;
				case 6:
					m_sbOrderBy.append(" \n order by re.execute" + strDesc);
					break;
			}
		}
	}
	
	/**
     * 查询取消审批业务
     * @throws Exception 
     */
	public PageLoader queryCancelApproval(SettInutWorkInfo conditionworkInfo) throws Exception
    {
		getCancelApprovalSql(conditionworkInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(new AppContext(), m_sbFrom.toString(), m_sbSelect.toString(), m_sbWhere.toString(), (int) Constant.PageControl.CODE_PAGELINECOUNT,
				"com.iss.itreasury.settlement.mywork.dataentity.SettInutWorkInfo", null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
    }
	
	public void getCancelApprovalSql(SettInutWorkInfo conditionworkInfo)
	{
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" vt.id id ,re.linkUrl linkUrl,re.approvalentryid approvalEntryID ,re.transtypeid transactionTypeID,re.transid transNo,vt.payaccountno payAccountNo,vt.receiveaccountno receiveAccountNo, \n ");
		m_sbSelect.append(" vt.payamount payAmount,vt.receiveamount receiveAmount,vt.inputuserid inputUserID,vt.execute execute");
		//		 from
		m_sbFrom = new StringBuffer();
		m_sbFrom.append("  sys_approvalrecord re,sett_vtransaction vt \n");
		// where
		m_sbWhere = new StringBuffer();
		m_sbWhere.append("  re.transid = vt.transno \n");
		if(conditionworkInfo.getOfficeID()>0)
		{	
			m_sbWhere.append(" and  re.officeid="+conditionworkInfo.getOfficeID()+" \n");
		}
		if(conditionworkInfo.getCurrencyID()>0)
		{	
			m_sbWhere.append(" and re.currencyid="+conditionworkInfo.getCurrencyID()+" \n");
		}
		if(conditionworkInfo.getModuleID()>0)
		{	
			m_sbWhere.append(" and re.moduleid="+conditionworkInfo.getModuleID()+" \n");
		}	
		if(conditionworkInfo.getTransactionTypeID()>0)
		{	
			m_sbWhere.append("  and re.transtypeid="+conditionworkInfo.getTransactionTypeID()+" \n");
		}
		if(conditionworkInfo.getExecute()!=null)
		{
			String time = conditionworkInfo.getExecute().toString();
			time = time.substring(0, 10);
			m_sbWhere.append("  and vt.execute="+"to_date('" + time + "','yyyy-mm-dd') \n");
		}
		if(FSWorkflowManager.isAutoCheck())//自动复核
		{
			m_sbWhere.append("  and vt.checkuserid="+conditionworkInfo.getUserID()+" \n");
			m_sbWhere.append("  and vt.statusid="+SETTConstant.TransactionStatus.CHECK+" \n");
			m_sbWhere.append("  and re.statusid="+SETTConstant.RecordStatus.VALID+" \n");
		}
		else//手动复核
		{
			m_sbWhere.append("  and re.lastappuserid="+conditionworkInfo.getUserID()+" \n");
			m_sbWhere.append("  and re.statusid="+SETTConstant.RecordStatus.VALID+" \n");
			m_sbWhere.append("  and vt.statusid="+SETTConstant.TransactionStatus.APPROVALED+" \n");
		}	
		m_sbOrderBy = new StringBuffer();
		String strDesc = conditionworkInfo.getDesc() == 2 ? " desc " : " asc";
		if(conditionworkInfo.getOrderField()>0)
		{
			switch ((int) conditionworkInfo.getOrderField())
			{
				case 1:
					m_sbOrderBy.append(" \n order by re.transtypeid" + strDesc);
					break;
				case 2:
					m_sbOrderBy.append(" \n order by re.transid" + strDesc);
					break;
				case 3:
					m_sbOrderBy.append(" \n order by vt.payaccountno" + strDesc);
					break;
				case 4:
					m_sbOrderBy.append(" \n order by vt.receiveaccountno" + strDesc);
					break;
				case 5:
					m_sbOrderBy.append(" \n order by re.inputuserid" + strDesc);
					break;
				case 6:
					m_sbOrderBy.append(" \n order by re.execute" + strDesc);
					break;
			}
		}
	}
	
	
	/**
     * 查询取消账户审批业务
     * @throws Exception 
     */
	public PageLoader queryAccountCancelApproval(SettInutWorkInfo conditionworkInfo) throws Exception
    {
		getCancelAccountApprovalSql(conditionworkInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(new AppContext(), m_sbFrom.toString(), m_sbSelect.toString(), m_sbWhere.toString(), (int) Constant.PageControl.CODE_PAGELINECOUNT,
				"com.iss.itreasury.settlement.mywork.dataentity.SettInutWorkInfo", null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
    }
	
	public void getCancelAccountApprovalSql(SettInutWorkInfo conditionworkInfo)
	{
		m_sbSelect = new StringBuffer();
		//m_sbSelect.append(" vt.id id ,re.linkUrl linkUrl,re.approvalentryid approvalEntryID ,re.transtypeid transactionTypeID,re.transid transNo,vt.payaccountno payAccountNo,vt.receiveaccountno receiveAccountNo, \n ");
		//m_sbSelect.append(" vt.payamount payAmount,vt.receiveamount receiveAmount,vt.inputuserid inputUserID,vt.execute execute");
		m_sbSelect.append("  ac.id id ,re.linkUrl linkUrl,re.approvalentryid approvalEntryID ,re.transtypeid transactionTypeID,re.transid transNo,\n ");
		m_sbSelect.append(" ac.saccountno AccountNo,ac.naccounttypeid AccountTypeID,ac.ninputuserid InputUserID,ac.dtopen OpenDate,ac.nstatusid StatusID");
		//		 from
		m_sbFrom = new StringBuffer();
		m_sbFrom.append("  sys_approvalrecord re,sett_account ac \n");
		// where
		m_sbWhere = new StringBuffer();
		m_sbWhere.append("  re.transid = ac.saccountno \n");
		if(conditionworkInfo.getOfficeID()>0)
		{	
			m_sbWhere.append(" and  re.officeid="+conditionworkInfo.getOfficeID()+" \n");
		}
		if(conditionworkInfo.getCurrencyID()>0)
		{	
			m_sbWhere.append(" and re.currencyid="+conditionworkInfo.getCurrencyID()+" \n");
		}
		if(conditionworkInfo.getModuleID()>0)
		{	
			m_sbWhere.append(" and re.moduleid="+conditionworkInfo.getModuleID()+" \n");
		}	
		if(conditionworkInfo.getTransactionTypeID()>0)
		{	
			m_sbWhere.append("  and re.transtypeid="+conditionworkInfo.getTransactionTypeID()+" \n");
		}
		if(FSWorkflowManager.isAutoCheck())//自动复核
		{
			m_sbWhere.append("  and ac.ncheckuserid="+conditionworkInfo.getUserID()+" \n");
			m_sbWhere.append("  and ac.ncheckstatusid="+SETTConstant.AccountCheckStatus.CHECK+" \n");
			m_sbWhere.append("  and re.statusid="+SETTConstant.RecordStatus.VALID+" \n");
		}
		else//手动复核
		{
			m_sbWhere.append("  and re.lastappuserid="+conditionworkInfo.getUserID()+" \n");
			m_sbWhere.append("  and re.statusid="+SETTConstant.RecordStatus.VALID+" \n");
			if(conditionworkInfo.getTransactionTypeID()>0){
				if(conditionworkInfo.getTransactionTypeID()==SETTConstant.TransactionType.ACCOUNTOPEN){
				   m_sbWhere.append("  and ac.ncheckstatusid="+SETTConstant.AccountCheckStatus.NEWSAVE_APPROVALED+" \n");
				}else if(conditionworkInfo.getTransactionTypeID()==SETTConstant.TransactionType.ACCOUNTMODIFY){
				   m_sbWhere.append("  and ac.ncheckstatusid="+SETTConstant.AccountCheckStatus.OLDSAVE_APPROVALED+" \n"); 
				}
			}else{
				m_sbWhere.append("  and ac.ncheckstatusid in("+SETTConstant.AccountCheckStatus.NEWSAVE_APPROVALED+","+SETTConstant.AccountCheckStatus.OLDSAVE_APPROVALED+")\n"); 
			}
		}	
		m_sbOrderBy = new StringBuffer();
		String strDesc = conditionworkInfo.getDesc() == 2 ? " desc " : " asc";
		if(conditionworkInfo.getOrderField()>0)
		{
//			switch ((int) conditionworkInfo.getOrderField())
//			{
//				case 1:
//					m_sbOrderBy.append(" \n order by re.transtypeid" + strDesc);
//					break;
//				case 2:
//					m_sbOrderBy.append(" \n order by re.transid" + strDesc);
//					break;
//				case 3:
//					m_sbOrderBy.append(" \n order by vt.payaccountno" + strDesc);
//					break;
//				case 4:
//					m_sbOrderBy.append(" \n order by vt.receiveaccountno" + strDesc);
//					break;
//				case 5:
//					m_sbOrderBy.append(" \n order by re.inputuserid" + strDesc);
//					break;
//				case 6:
//					m_sbOrderBy.append(" \n order by re.execute" + strDesc);
//					break;
//			}
		}
	}
	
	
	/**
	 * 查询办结账户业务
	 * @author haoliang
	 * @param SettInutWorkInfo inutworkInfo
	 * @return Collection
	 */
	public PageLoader queryAccountFinishedWork(SettInutWorkInfo conditionworkInfo) throws Exception
    {
		getAccountFinishedWorkSql(conditionworkInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(new AppContext(), m_sbFrom.toString(), m_sbSelect.toString(), m_sbWhere.toString(), (int) Constant.PageControl.CODE_PAGELINECOUNT,
				"com.iss.itreasury.settlement.mywork.dataentity.SettInutWorkInfo", null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
    }
	public void getAccountFinishedWorkSql(SettInutWorkInfo conditionworkInfo)
	{
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" ac.saccountno AccountNo,ac.naccounttypeid AccountTypeID,ac.ninputuserid InputUserID,ac.dtopen OpenDate,ac.nstatusid StatusID,re.approvalentryid approvalEntryID ,re.transtypeid transactionTypeID,re.transid transNo, \n ");
		m_sbSelect.append(" his.stepname stepName,his.modelname wfDefineName,re.linkurl linkUrl,his.owner, \n ");
		m_sbSelect.append(" his.entry_id entryID,his.action_code actionCode,his.step_code stepCode \n ");
		//		 from
		m_sbFrom = new StringBuffer();
		m_sbFrom.append("  v_os_histroystep his,sys_approvalrecord re,sett_account ac \n");
		// where
		m_sbWhere = new StringBuffer();
		m_sbWhere.append("  his.entry_id = re.approvalentryid \n");
		m_sbWhere.append(" and re.transid = ac.saccountno \n");
		if(conditionworkInfo.getOfficeID()>0)
		{	
			m_sbWhere.append(" and  re.officeid="+conditionworkInfo.getOfficeID()+" \n");
		}
		if(conditionworkInfo.getCurrencyID()>0)
		{	
			m_sbWhere.append(" and re.currencyid="+conditionworkInfo.getCurrencyID()+" \n");
		}
		if(conditionworkInfo.getModuleID()>0)
		{	
			m_sbWhere.append(" and re.moduleid="+conditionworkInfo.getModuleID()+" \n");
		}	
		if(conditionworkInfo.getTransactionTypeID()>0)
		{	
			m_sbWhere.append("  and re.transtypeid = "+conditionworkInfo.getTransactionTypeID()+" \n");
		}
		if(conditionworkInfo.getUserID()>0)
		{
			m_sbWhere.append(" and his.owner = '"+conditionworkInfo.getUserID()+"' \n");
		}
		m_sbOrderBy = new StringBuffer();
		String strDesc = conditionworkInfo.getDesc() == 2 ? " desc " : " asc";
		if(conditionworkInfo.getOrderField()>0)
		{
			switch ((int) conditionworkInfo.getOrderField())
			{
				case 1:
					m_sbOrderBy.append(" \n order by ac.naccounttypeid" + strDesc);
					break;
				case 2:
					m_sbOrderBy.append(" \n order by ac.saccountno" + strDesc);
					break;
				case 3:
					m_sbOrderBy.append(" \n order by ac.dtopen" + strDesc);
					break;
				
			}
		}
	}
	
	
	
	
	/**
     * 查询办结业务
     * @throws Exception 
     */
	public PageLoader queryFinishedWork(SettInutWorkInfo conditionworkInfo) throws Exception
    {
		getFinishedWorkSql(conditionworkInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(new AppContext(), m_sbFrom.toString(), m_sbSelect.toString(), m_sbWhere.toString(), (int) Constant.PageControl.CODE_PAGELINECOUNT,
				"com.iss.itreasury.settlement.mywork.dataentity.SettInutWorkInfo", null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
    }
	
	public void getFinishedWorkSql(SettInutWorkInfo conditionworkInfo)
	{
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" vt.id id ,re.approvalentryid approvalEntryID ,re.transtypeid transactionTypeID,re.transid transNo,vt.payaccountno payAccountNo,vt.receiveaccountno receiveAccountNo, \n ");
		m_sbSelect.append(" vt.payamount payAmount,vt.receiveamount receiveAmount,vt.inputuserid inputUserID,vt.execute execute, \n");
		m_sbSelect.append(" his.stepname stepName,his.modelname wfDefineName,re.linkurl linkUrl,his.owner, \n ");
		m_sbSelect.append(" his.entry_id entryID,his.action_code actionCode,his.step_code stepCode \n ");
		//		 from
		m_sbFrom = new StringBuffer();
		m_sbFrom.append("  v_os_histroystep his,sys_approvalrecord re,sett_vtransaction vt \n");
		// where
		m_sbWhere = new StringBuffer();
		m_sbWhere.append("  his.entry_id = re.approvalentryid \n");
		m_sbWhere.append(" and re.transid = vt.transno \n");
		if(conditionworkInfo.getOfficeID()>0)
		{	
			m_sbWhere.append(" and  re.officeid="+conditionworkInfo.getOfficeID()+" \n");
		}
		if(conditionworkInfo.getCurrencyID()>0)
		{	
			m_sbWhere.append(" and re.currencyid="+conditionworkInfo.getCurrencyID()+" \n");
		}
		if(conditionworkInfo.getModuleID()>0)
		{	
			m_sbWhere.append(" and re.moduleid="+conditionworkInfo.getModuleID()+" \n");
		}	
		if(conditionworkInfo.getTransactionTypeID()>0)
		{	
			m_sbWhere.append("  and vt.transactiontypeid = "+conditionworkInfo.getTransactionTypeID()+" \n");
		}
		if(conditionworkInfo.getUserID()>0)
		{
			m_sbWhere.append(" and his.owner = '"+conditionworkInfo.getUserID()+"' \n");
		}
		if(conditionworkInfo.getExecuteStart() !=null)
		{
			m_sbWhere.append(" and vt.execute>=to_date('"+conditionworkInfo.getExecuteStart().toString().substring(0, 10)+"','yyyy-mm-dd') \n");
		}
		if(conditionworkInfo.getExecuteEnd() !=null)
		{
			m_sbWhere.append(" and vt.execute<=to_date('"+conditionworkInfo.getExecuteEnd().toString().substring(0, 10)+"','yyyy-mm-dd') \n");
		}
		m_sbOrderBy = new StringBuffer();
		String strDesc = conditionworkInfo.getDesc() == 2 ? " desc " : " asc";
		if(conditionworkInfo.getOrderField()>0)
		{
			switch ((int) conditionworkInfo.getOrderField())
			{
				case 1:
					m_sbOrderBy.append(" \n order by vt.transactiontypeid" + strDesc);
					break;
				case 2:
					m_sbOrderBy.append(" \n order by re.transid" + strDesc);
					break;
				case 3:
					m_sbOrderBy.append(" \n order by vt.payaccountno" + strDesc);
					break;
				case 4:
					m_sbOrderBy.append(" \n order by vt.receiveaccountno" + strDesc);
					break;
				case 5:
					m_sbOrderBy.append(" \n order by his.owner" + strDesc);
					break;
				case 6:
					m_sbOrderBy.append(" \n order by vt.execute" + strDesc);
					break;
			}
		}
	}
	
}
