/**
 * @author XiaoQiao
 * @Date   2012-6-14
 */
package com.iss.itreasury.loan.reportelements.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.reportelements.dataentity.ContractExtQueryInfo;
import com.iss.itreasury.settlement.query.queryobj.BaseQueryObject;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.util.Database;


public class ContractExtDao extends BaseQueryObject{

	private Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
	boolean flag = false;
	
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
	Log4j logger = null;
	Connection conn = null;
	
	public void getFindSql(long  status,String startContract,String endContract,String orderType,String orderField){
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" l.id,lo.name as loanName,l.scontractcode,l.nborrowclientid,c.sName as clientName,");
		m_sbSelect.append(" l.mloanamount,l.nintervalnum, l.sdetailremarks,l.smanagername ");
		
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" loan_contractform l, client c,loan_loantypesetting lo");
		
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" l.nborrowclientid = c.id");
		m_sbWhere.append(" and lo.id=l.ntypeid");
		if(status == 1){//已补录
			m_sbWhere.append(" and (l.sdetailremarks is not null");
			m_sbWhere.append(" or l.smanagername is not null)");
		}
		if(status == 2){//未补录
			m_sbWhere.append(" and l.sdetailremarks is null");
			m_sbWhere.append(" and l.smanagername is null");
		}
		//除去status为1和2的情况，则为查询全部记录
		if(startContract != null && startContract.length() > 0){
			m_sbWhere.append(" and l.scontractcode >" + "'"+startContract+"'");
		}
		if(endContract != null && endContract.length() > 0){
			m_sbWhere.append(" and l.scontractcode <" + "'"+endContract+"'");
		}
		m_sbOrderBy  = new StringBuffer();
			m_sbOrderBy.append(" order by " + orderField + " " + orderType);
	}
	
	public void getFindById(long id){
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" l.id,l.scontractcode,l.sdetailremarks,l.smanagername");
		
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" loan_contractform l");
		
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" l.id=" + id);
		m_sbOrderBy  = new StringBuffer();
		m_sbOrderBy.append(" order by l.id");
	}
	
	public PageLoader getContractExtInfo(long status,String startContract,String endContract,String orderType,String orderField){
		getFindSql(status, startContract, endContract,orderType,orderField);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.loan.reportelements.dataentity.ContractExtQueryInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	
	public PageLoader getContractExtInfoById(long id){
		getFindById(id);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.loan.reportelements.dataentity.ContractExtQueryInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	
	public boolean update(ContractExtQueryInfo cExtInfo)throws ITreasuryDAOException,Exception{
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("update loan_contractform l");
		sBuffer.append(" set l.sdetailremarks = ?,l.smanagername = ?");
		sBuffer.append(" where l.id = ?");
		conn = Database.getConnection();
		PreparedStatement ps = conn.prepareStatement(sBuffer.toString());
		ps.setString(1, cExtInfo.getSDetailremarks());
	    ps.setString(2, cExtInfo.getSManagerName());
	    ps.setLong(3, cExtInfo.getId());
	    int i = ps.executeUpdate();
	    if(i != 0){
	    	flag = true;
	    }
	    if(i != 0){
	    	flag = true;
	    }
	    if(ps != null){
	    	ps.close();
	    	ps = null;
	    }
	    if(conn != null){
	    	conn.close();
	    	conn = null;
	    }
		return flag;
	}
}
