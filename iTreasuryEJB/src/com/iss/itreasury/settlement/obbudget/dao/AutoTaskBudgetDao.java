package com.iss.itreasury.settlement.obbudget.dao;

import java.sql.Connection;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.obbudget.dataentity.AutoTaskBudgetInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.system.dao.PageLoader;

public class AutoTaskBudgetDao extends SettlementDAO{
    public AutoTaskBudgetDao()
	{  
		super("SETT_AUTOTASK");
		super.setUseMaxID();
	}

	public AutoTaskBudgetDao(String tableName)
	{
		super(tableName);
		super.setUseMaxID();
	}

	public AutoTaskBudgetDao(String tableName, Connection conn)
	{
		super(tableName, conn);
		super.setUseMaxID();
	}
	
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
	
	//	翻页查询
	public PageLoader queryTransactionInfo(AutoTaskBudgetInfo info) throws Exception{
		getTransactionSQL(info);

		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.obbudget.dataentity.AutoTaskBudgetInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	
	/**
	 * 产生查询SQL
	 * @param info
	 */
	public void getTransactionSQL(AutoTaskBudgetInfo info){
		m_sbSelect = new StringBuffer();
		// select 
		m_sbSelect.append(" ID,accountId,branchId,status,officeId,currencyId,inputUserId,inputDate \n");
		// from 
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" SETT_AUTOTASK \n");
		// where 
		m_sbWhere = new StringBuffer();
		//m_sbWhere.append(" StatusID>0 ");
		
		m_sbWhere.append(" status = "+SETTConstant.AutoTaskStatus.SET);
		
		if (info.getOfficeId() > 0)
			m_sbWhere.append(" and OfficeID=" + info.getOfficeId() + "");
		if(info.getCurrencyId() > 0)
			m_sbWhere.append(" and currencyId = " + info.getCurrencyId() + "");
		if(info.getAccountId()>0){
			m_sbWhere.append(" and accountId = " + info.getAccountId() + "");
		}
		if(info.getBranchId()>0){
			m_sbWhere.append(" and branchId = "+info.getBranchId() + "");
		}

		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append(" order by inputdate desc");
	}
	
	//设置与取消设置自动任务
	public long deleteAutoTask(long id) throws Exception{
		initDAO();
		StringBuffer strSb = new StringBuffer();
		strSb.append("update sett_autotask set status = "+SETTConstant.AutoTaskStatus.NOTSET);
		strSb.append(" where id = "+id);
		try{
			transPS = transConn.prepareStatement(strSb.toString());
			id = transPS.executeUpdate();
		}catch(Exception e){
    		e.printStackTrace();
    	}finally{
			finalizeDAO();
		}		
		return id;
	}
	
	//批量设置与取消设置自动任务
	public long deleteAutoTask(String[] id) throws Exception{
		long returnId = -1;
		initDAO();
		StringBuffer strSb = new StringBuffer();
		strSb.append("update sett_autotask set status = "+SETTConstant.AutoTaskStatus.NOTSET);
		strSb.append(" where id in (");
		if(id != null && id.length > 0){
			for(int i = 0 ; i < (id.length-1) ; i ++){
				strSb.append(id[0]+",");
			}
			strSb.append(id[id.length-1]+")");
		}
		try{
			transPS = transConn.prepareStatement(strSb.toString());
			returnId = transPS.executeUpdate();
		}catch(Exception e){
    		e.printStackTrace();
    	}finally{
			finalizeDAO();
		}		
		return returnId;
	}
}
