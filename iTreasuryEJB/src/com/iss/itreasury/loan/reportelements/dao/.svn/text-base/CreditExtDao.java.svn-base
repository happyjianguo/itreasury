/**
 * @author XiaoQiao
 * @Date   2012-6-19
 */
package com.iss.itreasury.loan.reportelements.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.reportelements.dataentity.ContractExtQueryInfo;
import com.iss.itreasury.loan.reportelements.dataentity.CreditExtQueryInfo;
import com.iss.itreasury.settlement.query.queryobj.BaseQueryObject;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.util.Database;


public class CreditExtDao extends BaseQueryObject{

	private Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
	boolean flag = false;
	
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
	Log4j logger = null;
	Connection conn = null;
	
	public void getFindSql(long  status,String startId,String endId,String ordertype,String orderfield){
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" c.id,c.creditcode,c.clientId,cl.sName,cl.scode,c.creditmodel,c.creditamount,c.startdate,c.enddate,c.scheckcode");
		
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" credit_amountsetup c,client cl");
		
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" c.clientid=cl.id");
		if(status == 1){//已补录
			m_sbWhere.append(" and c.scheckcode is not null");
		}
		if(status == 2){//未补录
			m_sbWhere.append(" and c.scheckcode is null");
		}
		//除去status为1和2的情况，则为查询全部记录
		if(startId != null && startId.length() > 0){
			m_sbWhere.append(" and cl.scode >" + "'"+startId+"'");
		}
		if(endId != null && endId.length() > 0){
			m_sbWhere.append(" and cl.scode <" + "'"+endId+"'");
		}
		m_sbOrderBy  = new StringBuffer();
		
		m_sbOrderBy.append(" order by " + orderfield + " " + ordertype);
	
	}
		
	public void getFindById(long id){
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" l.id,l.creditcode,l.scheckcode");
		
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" credit_amountsetup l");
		
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" l.id=" + id);
		m_sbOrderBy  = new StringBuffer();
		m_sbOrderBy.append(" order by l.id");
	}
	
	public PageLoader getCreditExtInfo(long status,String startId,String endId,String ordertype,String orderField){
		getFindSql(status, startId, endId,ordertype,orderField);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.loan.reportelements.dataentity.CreditExtQueryInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	
	public PageLoader getCreditExtInfoById(long id){
		getFindById(id);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.loan.reportelements.dataentity.CreditExtQueryInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	
	
	public boolean update(CreditExtQueryInfo cInfo)throws ITreasuryDAOException,Exception{
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("update credit_amountsetup t");
		sBuffer.append(" set t.scheckcode=?");
		sBuffer.append(" where t.id = ?");
		conn = Database.getConnection();
		PreparedStatement ps = conn.prepareStatement(sBuffer.toString());
		ps.setString(1, cInfo.getScheckCode());
	    ps.setLong(2, cInfo.getId());
	    int i = ps.executeUpdate();
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
