/*
 * Created on 2007-4-12
 *
 * Title:				iTreasury
 * @author             	刘琰 
 * Company:             iSoftStone
 * @version				iTreasury3.2新增
 * Description:         产品化3.2在结算,网银新增审批流,该功能实现查询我的工作      
 */
package com.iss.itreasury.evoucher.mywork.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;

import com.iss.itreasury.evoucher.base.VoucherException;
import com.iss.itreasury.evoucher.mywork.dataentity.SettInutWorkInfo;
import com.iss.itreasury.evoucher.util.VOUConstant;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.approval.dataentity.InutApprovalWorkInfo;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
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
			
				String strSQL = "select ar.*,vt.nprintcontentid, vt.nprintcontentno,vt.ninputdate,vt.nclientid,vt.ndeptid,vt.ninputuserid,ar.OfficeID,ar.currencyid  "
								+" from sys_approvalrecord ar,print_printapply vt  "
								//+ " where ar.transid like '%,'||vt.id||',%'  "
								+ " where SUBSTR(ar.transid, 2, INSTR(ar.transid, ',', 1, 2)-2) = vt.id "
								+ " and ar.moduleid = "
								+ Constant.ModuleType.EVOUCHER
								+ " and ar.approvalentryid in (" 
								+ strApprovalEntryIDs + ")"		
								+ " and ar.OfficeID = " + qInfo.getOfficeID()
								+ " and ar.CurrencyID = " + qInfo.getCurrencyID()			
				                + " and vt.nstatusid = " + VOUConstant.VoucherStatus.APPROVALING;	
				//排序
				//strSQL += " order by orderpara ";
				if(qInfo.getTranstypeid()>0)
				{
					strSQL += " and ar.TransTypeID = "+qInfo.getTranstypeid();
				}
				
				prepareStatement(strSQL.toString());
				System.out.print("-------sql = "+strSQL);
				localRS = executeQuery();
				while (localRS.next())
				{
					SettInutWorkInfo workInfo = new SettInutWorkInfo();
					
					workInfo.setOfficeID(localRS.getLong("OfficeID"));
					workInfo.setCurrencyID(localRS.getLong("CurrencyID"));
					workInfo.setUserID(qInfo.getUserID());//待办人的id
					workInfo.setNprintcontentno(localRS.getString("nprintcontentno")); //交易号
					workInfo.setNprintcontentid(localRS.getLong("nprintcontentid")); //交易id
					workInfo.setLinkUrl(localRS.getString("LinkUrl"));
					workInfo.setTranstypeid(localRS.getLong("transtypeid"));
					workInfo.setNinputdate(localRS.getTimestamp("ninputdate"));  //申请时间 
					workInfo.setNclientid(localRS.getLong("nclientid")); //客户id
					workInfo.setNdeptid(localRS.getLong("ndeptid")); //部门id
					workInfo.setInputUserID(localRS.getLong("ninputuserid")); //申请人
					workInfo.setApprovalEntryID(localRS.getLong("ApprovalEntryID"));
					//交易金额
					workInfo.setReceiveamount( findNprintcontentAmount(workInfo.getNprintcontentid(),workInfo.getNprintcontentno()) );
					
					if ( workInfo.getNdeptid() == VOUConstant.PrintSection.FINANCECOMPANY )  //财务公司
					{
						workInfo.setUsername( com.iss.itreasury.settlement.util.NameRef.getUserNameByID(workInfo.getInputUserID()) );
						workInfo.setClientname( Env.getClientName() );
					}
					else if ( workInfo.getNdeptid() == VOUConstant.PrintSection.EBANKCUSTOMER )  //网上客户
					{
						//根据 UserID 取得用户名称
						workInfo.setUsername( com.iss.itreasury.ebank.util.NameRef.getUserNameByID(workInfo.getInputUserID()) );
						//通过客户ID查询客户名称
						workInfo.setClientname( com.iss.itreasury.ebank.util.NameRef.getClientNameByID(workInfo.getNclientid()) );
					}
					
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
								+ strApprovalEntryIDs + ")";
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
					//workInfo.setOfficeID(localRS.getLong("nofficeid"));
					//workInfo.setCurrencyID(localRS.getLong("ncurrencyid"));
					//sworkInfo.setUserID(qInfo.getUserID());//待办人的id
//					workInfo.setTransNo(localRS.getString("saccountno"));
//					workInfo.setTranstypeid(localRS.getLong("naccounttypeid"));
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
		m_sbSelect.append(" distinct ac.id id ,re.approvalentryid approvalEntryID ,re.transtypeid Transtypeid,re.transid transNo,\n ");
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
		if(conditionworkInfo.getTranstypeid()>0)
			m_sbWhere.append("  and re.transtypeid="+conditionworkInfo.getTranstypeid()+" \n");
		if(conditionworkInfo.getStatusID()>0)
			m_sbWhere.append(" and ac.nstatusid ="+conditionworkInfo.getStatusID()+" \n");
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
				"com.iss.itreasury.evoucher.mywork.dataentity.SettInutWorkInfo", null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
    }
	
	public void getRefuseWorkSql(SettInutWorkInfo conditionworkInfo,long ldesc,long lOrderField)
	{
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" re.transtypeid loanFormID,vt.ntypeid Transtypeid,re.transid transid, \n ");
		m_sbSelect.append(" vt.nprintcontentid, vt.nprintcontentno,vt.ninputdate,vt.nclientid,vt.ndeptid,vt.ninputuserid inputuserid, vt.nstatusid statusID ");
		//		 from
		m_sbFrom = new StringBuffer();
		m_sbFrom.append("  sys_approvalrecord re,print_printapply vt,SYS_HISTORYSTEP h \n");
		// where
		m_sbWhere = new StringBuffer();
		//m_sbWhere.append("   re.transid like '%,'||vt.id||',%' \n ");
		m_sbWhere.append("  SUBSTR(re.transid, 2, INSTR(re.transid, ',', 1, 2)-2) = vt.id \n ");
		m_sbWhere.append(" and re.transtypeid ="+VOUConstant.ApprovalType.REPRINTAPPLY);
		m_sbWhere.append(" and re.approvalentryid = h.entry_id ");
		m_sbWhere.append(" and h.action_key = 20 "); //审批操作为提交
		m_sbWhere.append(" and h.caller ="+conditionworkInfo.getUserID());
		if(conditionworkInfo.getOfficeID()>0)
			m_sbWhere.append(" and  re.officeid="+conditionworkInfo.getOfficeID()+" \n");
		if(conditionworkInfo.getCurrencyID()>0)
			m_sbWhere.append(" and re.currencyid="+conditionworkInfo.getCurrencyID()+" \n");
		if(conditionworkInfo.getModuleID()>0)
			m_sbWhere.append(" and re.moduleid="+conditionworkInfo.getModuleID()+" \n");
		if(conditionworkInfo.getTranstypeid()>0)
			m_sbWhere.append("  and re.transtypeid="+conditionworkInfo.getTranstypeid()+" \n");
		if(conditionworkInfo.getStatusID()>0)
			m_sbWhere.append(" and vt.nstatusid ="+conditionworkInfo.getStatusID()+" \n");
		if(conditionworkInfo.getUserID()>0)
			m_sbWhere.append(" and  ((vt.ninputuserid = "+conditionworkInfo.getUserID()+" and vt.ndeptid="+VOUConstant.PrintSection.FINANCECOMPANY+") or (vt.ndeptid="+VOUConstant.PrintSection.EBANKCUSTOMER+"   )  )  \n");
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
				case 5:
					m_sbOrderBy.append(" \n order by vt.ninputuserid" + strDesc);
					break;
				case 6:
					m_sbOrderBy.append(" \n order by vt.ninputdate" + strDesc);
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
				"com.iss.itreasury.evoucher.mywork.dataentity.SettInutWorkInfo", null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
    }
	
	public void getCancelApprovalSql(SettInutWorkInfo conditionworkInfo)
	{
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" re.linkUrl linkUrl,re.approvalentryid approvalEntryID ,re.transtypeid Transtypeid,re.transid transid, \n ");
		m_sbSelect.append(" vt.nprintcontentid, vt.nprintcontentno,vt.ninputdate,vt.nclientid,vt.ndeptid,vt.ninputuserid inputuserid \n");
		//		 from
		m_sbFrom = new StringBuffer();
		m_sbFrom.append("  sys_approvalrecord re,print_printapply vt \n");
		// where
		m_sbWhere = new StringBuffer();
		//m_sbWhere.append(" re.transid like '%,'||vt.id||',%' \n ");
		m_sbWhere.append(" SUBSTR(re.transid, 2, INSTR(re.transid, ',', 1, 2)-2) = vt.id \n ");
		
		m_sbWhere.append(" and re.officeid=" + conditionworkInfo.getOfficeID()
				+ " \n");
		m_sbWhere.append(" and re.currencyid=" + conditionworkInfo.getCurrencyID()
				+ " \n");
		m_sbWhere.append(" and re.moduleid=" + conditionworkInfo.getModuleID()
				+ " \n");
		m_sbWhere.append("  and re.lastappuserid = "
				+ conditionworkInfo.getUserID() + " \n");
		m_sbWhere.append("  and re.statusid = "
				+ SETTConstant.RecordStatus.VALID + " \n");
		m_sbWhere.append("  and vt.nstatusid = "
				+ VOUConstant.VoucherStatus.APPROVED + " \n");
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
			case 5:
				m_sbOrderBy.append(" \n order by vt.ninputuserid" + strDesc);
				break;
			case 6:
				m_sbOrderBy.append(" \n order by vt.ninputdate" + strDesc);
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
		//m_sbSelect.append(" vt.id id ,re.linkUrl linkUrl,re.approvalentryid approvalEntryID ,re.transtypeid Transtypeid,re.transid transNo,vt.payaccountno payAccountNo,vt.receiveaccountno receiveAccountNo, \n ");
		//m_sbSelect.append(" vt.payamount payAmount,vt.receiveamount receiveAmount,vt.inputuserid inputUserID,vt.execute execute");
		m_sbSelect.append("  ac.id id ,re.linkUrl linkUrl,re.approvalentryid approvalEntryID ,re.transtypeid Transtypeid,re.transid transNo,\n ");
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
		if(conditionworkInfo.getTranstypeid()>0)
		{	
			m_sbWhere.append("  and re.transtypeid="+conditionworkInfo.getTranstypeid()+" \n");
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
			if(conditionworkInfo.getTranstypeid()>0){
				if(conditionworkInfo.getTranstypeid()==SETTConstant.AccountCheckStatus.NEWSAVE_APPROVALED){
				   m_sbWhere.append("  and ac.ncheckstatusid="+SETTConstant.AccountCheckStatus.NEWSAVE_APPROVALED+" \n");
				}else if(conditionworkInfo.getTranstypeid()==SETTConstant.AccountCheckStatus.OLDSAVE_APPROVALED){
				   m_sbWhere.append("  and ac.ncheckstatusid="+SETTConstant.AccountCheckStatus.NEWSAVE_APPROVALED+" \n"); 
				}
			}else{
				m_sbWhere.append("  and ac.ncheckstatusid in("+SETTConstant.AccountCheckStatus.NEWSAVE_APPROVALED+","+SETTConstant.AccountCheckStatus.NEWSAVE_APPROVALED+")\n"); 
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
		m_sbSelect.append(" ac.saccountno AccountNo,ac.naccounttypeid AccountTypeID,ac.ninputuserid InputUserID,ac.dtopen OpenDate,ac.nstatusid StatusID,re.approvalentryid approvalEntryID ,re.transtypeid Transtypeid,re.transid transNo, \n ");
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
		if(conditionworkInfo.getTranstypeid()>0)
		{	
			m_sbWhere.append("  and re.transtypeid = "+conditionworkInfo.getTranstypeid()+" \n");
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
				"com.iss.itreasury.evoucher.mywork.dataentity.SettInutWorkInfo", null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
    }
	
	public void getFinishedWorkSql(SettInutWorkInfo conditionworkInfo)
	{
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" re.approvalentryid approvalEntryID ,re.transtypeid transtypeid,re.transid transNo, \n ");
		m_sbSelect.append(" vt.nprintcontentid, vt.nprintcontentno,vt.ninputdate,vt.nclientid,vt.ndeptid,vt.ninputuserid inputuserid, \n");
		//m_sbSelect.append(" vt.nprintcontentid, vt.nprintcontentno,vt.ninputdate,vt.nclientid,vt.ndeptid,decode(vt.ndeptid,"+VOUConstant.PrintSection.FINANCECOMPANY+",vt.ninputuserid,"+VOUConstant.PrintSection.EBANKCUSTOMER+",vt.nreceiveuserid) inputuserid, \n");
		//m_sbSelect.append(" his.stepname stepName,his.modelname wfDefineName,re.linkurl linkUrl,his.owner, \n ");
		m_sbSelect.append(" his.stepname stepName,his.modelname wfDefineName,re.linkurl linkUrl,his.caller owner, \n ");
		m_sbSelect.append(" his.entry_id entryID,his.action_code actionCode,his.step_code stepCode \n ");
		//		 from
		m_sbFrom = new StringBuffer();
		m_sbFrom.append("  v_os_histroystep his,sys_approvalrecord re,print_printapply vt \n");
		
		//add by dwj 20110930
		m_sbFrom.append(", (\n");
		m_sbFrom.append(" select  max(his.id) maxid, re.approvalentryid \n");
		m_sbFrom.append(" from v_os_histroystep his,sys_approvalrecord re,print_printapply vt \n");
		m_sbFrom.append(" where his.entry_id = re.approvalentryid \n");
		m_sbFrom.append(" and SUBSTR(re.transid, 2, INSTR(re.transid, ',', 1, 2)-2) = vt.id \n");
		
		if(conditionworkInfo.getOfficeID()>0)
		{	
			m_sbFrom.append(" and  re.officeid="+conditionworkInfo.getOfficeID()+" \n");
		}
		if(conditionworkInfo.getCurrencyID()>0)
		{	
			m_sbFrom.append(" and re.currencyid="+conditionworkInfo.getCurrencyID()+" \n");
		}
		if(conditionworkInfo.getModuleID()>0)
		{	
			m_sbFrom.append(" and re.moduleid="+conditionworkInfo.getModuleID()+" \n");
		}	
		if(conditionworkInfo.getTranstypeid()>0)
		{	
			m_sbFrom.append("  and re.transtypeid = "+conditionworkInfo.getTranstypeid()+" \n");
		}
		if(conditionworkInfo.getUserID()>0)
		{
			//m_sbFrom.append(" and his.owner = '"+conditionworkInfo.getUserID()+"' \n");
			//m_sbFrom.append(" and his.caller = '"+conditionworkInfo.getUserID()+"' \n");
			//m_sbFrom.append("and (vt.ninputuserid = '"+conditionworkInfo.getUserID()+"' or his.caller='"+conditionworkInfo.getUserID()+"') \n");
			m_sbFrom.append("and ((vt.ninputuserid = '"+conditionworkInfo.getUserID()+"' and vt.ndeptid = "+VOUConstant.PrintSection.FINANCECOMPANY+") or (vt.nreceiveuserid = '"+conditionworkInfo.getUserID()+"' and vt.ndeptid = "+VOUConstant.PrintSection.EBANKCUSTOMER+") or his.caller='"+conditionworkInfo.getUserID()+"') \n");
		}
		if(conditionworkInfo.getExecuteStart() !=null)
		{
			m_sbFrom.append(" and vt.execute>=to_date('"+conditionworkInfo.getExecuteStart().toString().substring(0, 10)+"','yyyy-mm-dd') \n");
		}
		if(conditionworkInfo.getExecuteEnd() !=null)
		{
			m_sbFrom.append(" and vt.execute<=to_date('"+conditionworkInfo.getExecuteEnd().toString().substring(0, 10)+"','yyyy-mm-dd') \n");
		}
		m_sbFrom.append("  group by re.approvalentryid) maxvoshi\n");
		//end add by dwj 20110930
		// where
		m_sbWhere = new StringBuffer();
		m_sbWhere.append("  his.entry_id = re.approvalentryid \n");
		//m_sbWhere.append(" and re.transid like '%,'||vt.id||',%' \n");
		m_sbWhere.append(" and SUBSTR(re.transid, 2, INSTR(re.transid, ',', 1, 2)-2) = vt.id \n");
		
		//add by dwj 20110930
		m_sbWhere.append(" and maxvoshi.maxid = his.id \n");
		//end add by dwj 20110930
		
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
		if(conditionworkInfo.getTranstypeid()>0)
		{	
			m_sbWhere.append("  and re.transtypeid = "+conditionworkInfo.getTranstypeid()+" \n");
		}
		if(conditionworkInfo.getUserID()>0)
		{
			//m_sbWhere.append(" and his.owner = '"+conditionworkInfo.getUserID()+"' \n");
			//m_sbWhere.append(" and his.caller = '"+conditionworkInfo.getUserID()+"' \n");
			m_sbWhere.append("and ((vt.ninputuserid = '"+conditionworkInfo.getUserID()+"' and vt.ndeptid = "+VOUConstant.PrintSection.FINANCECOMPANY+") or (vt.nreceiveuserid = '"+conditionworkInfo.getUserID()+"' and vt.ndeptid = "+VOUConstant.PrintSection.EBANKCUSTOMER+") or his.caller='"+conditionworkInfo.getUserID()+"') \n");
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
					m_sbOrderBy.append(" \n order by re.Transtypeid" + strDesc);
					break;
				case 2:
					m_sbOrderBy.append(" \n order by re.transid" + strDesc);
					break;
				case 5:
					m_sbOrderBy.append(" \n order by his.owner" + strDesc);
					break;
				case 6:
					m_sbOrderBy.append(" \n order by vt.ninputdate" + strDesc);
					break;
			}
		}
	}
	
	public double findNprintcontentAmount(long printcontentid,String printcontentno)throws VoucherException
	{
		double receiveamount = 0.0;  //交易金额（收款金额）
		StringBuffer sfSQL = new StringBuffer();
		Connection con = null;
		ResultSet rss = null;
		PreparedStatement ps = null;
		
		try
		{
			sfSQL.append(" select b.receiveamount from print_printapply a,sett_vtransaction b ");
			sfSQL.append(" where a.nprintcontentid=b.id and b.id = "+printcontentid+" and b.transno = "+printcontentno+" ");
			//sfSQL.append(" and a.id = "+lID+" ");
			
			con = Database.getConnection();
			
			System.out.println("查询交易金额语句SQL^^^^^^^^^^^"+sfSQL.toString());
			
			ps = con.prepareStatement(sfSQL.toString());
			rss = ps.executeQuery();
			
			if ( rss.next() )
			{
				receiveamount = rss.getDouble("receiveamount");
			}
		}
		catch(Exception ex)
		{
			throw new VoucherException();
		} 
		finally
		{
			try
			{
				rss.close();
				rss = null;
				ps.close();
				ps = null;
				con.close();
				con = null;
			}
			catch(Exception es)
			{
				throw new VoucherException();
			}	
		}
		
		System.out.println("交易金额=="+receiveamount);
		return receiveamount;
	}
}