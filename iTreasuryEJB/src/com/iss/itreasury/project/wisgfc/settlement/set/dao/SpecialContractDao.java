package com.iss.itreasury.project.wisgfc.settlement.set.dao;

import java.sql.SQLException;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.project.wisgfc.settlement.set.dataentity.SpecialContractInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

/**
 * @author xlchang 2010-11-16
 * 特约合同维护数据库操作类
 */
public class SpecialContractDao extends ITreasuryDAO{
	private static Log4j log4j = new Log4j(Constant.ModuleType.SETTLEMENT);
	
	private StringBuffer m_sbSelect = null;
	private StringBuffer m_sbFrom = null;
	private StringBuffer m_sbWhere = null;
	private StringBuffer m_sbOrderBy = null;
	
	public SpecialContractDao() {
		super("sett_specialContract","seq_sett_specialContract");
}

/**
 * 查找特约合同信息
 * @return
 * @throws ITreasuryDAOException 
 */
public Collection findByCondition(SpecialContractInfo info) throws ITreasuryDAOException {
	return super.findByCondition(info, " order by scode ");
}

/**
 * 获得特约合同编码
 * @param info
 * @return
 * @throws ITreasuryDAOException
 * @throws SQLException
 */
public String getNewCode(SpecialContractInfo info) throws ITreasuryDAOException, SQLException {
	String code = "";
	StringBuffer sb = new StringBuffer();
	try {		
		initDAO();
		sb.append("select trim(decode(sign(code-9),1,to_char(code),to_char(code,'00'))) scode from (");
		sb.append("select nvl(max(to_number(s.scode))+1,1) code from ");
		sb.append(strTableName);
		sb.append(" s where s.nstatusid > ? and s.nofficeid = ? and s.ncurrencyid = ?)");				
		prepareStatement(sb.toString());
		int index = 1;
		transPS.setLong(index++,Constant.RecordStatus.INVALID);
		transPS.setLong(index++,info.getNOfficeID());
		transPS.setLong(index++,info.getNCurrencyID());
		executeQuery();
		SpecialContractInfo rInfo =(SpecialContractInfo)getDataEntityFromResultSet(SpecialContractInfo.class);
		if (rInfo != null) {
			code = rInfo.getSCode();
		}
	} finally {
		finalizeDAO();
	}
	return code;
}

/**
 * 保存特约合同信息
 * @param info
 * @throws ITreasuryDAOException
 */
public void add(SpecialContractInfo info) throws ITreasuryDAOException {
	super.add(info);
}

/**
 * 更新特约合同信息
 * @param info
 * @throws ITreasuryDAOException
 */
public void update(SpecialContractInfo info) throws ITreasuryDAOException {
	super.update(info);
}

//翻页查询
public PageLoader querySpecialContractInfo(SpecialContractInfo info) throws Exception	{
	getSpecialContractSQL(info);
	PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
	pageLoader.initPageLoader(
		new AppContext(),
		m_sbFrom.toString(),
		m_sbSelect.toString(),
		m_sbWhere.toString(),
		(int) Constant.PageControl.CODE_PAGELINECOUNT,
		"com.iss.itreasury.project.wisgfc.settlement.set.dataentity.SpecialContractInfo",
		null);
	pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
	return pageLoader;
}

/**
 * 产生查询SQL  
 * @param info
 *
 */
public void getSpecialContractSQL(SpecialContractInfo info) throws Exception {			
	//select 	
	m_sbSelect = new StringBuffer();		
	m_sbSelect.append(" * ");
	
	//from 
	m_sbFrom = new StringBuffer();
	m_sbFrom.append(" " + strTableName + " s \n") ;
	
	//where 
	m_sbWhere = new StringBuffer();
	m_sbWhere.append(" 1 = 1 ");
	m_sbWhere.append(" and s.nstatusid =  " + Constant.RecordStatus.VALID + " \n");
	m_sbWhere.append(" and s.nofficeid =  " + info.getNOfficeID() + " \n");
	m_sbWhere.append(" and s.ncurrencyid = " + info.getNCurrencyID() + " \n");	
	
	//查询条件-合同号
	if (info.getQ_contractCode() != null && info.getQ_contractCode().length() > 0) {
		m_sbWhere.append(" and s.scode like '%" + info.getQ_contractCode() + "%' \n");
	}
	//查询条件-付款单位id
	if (info.getQ_payClientID() > 0) {
		m_sbWhere.append(" and s.npayclientid = " + info.getQ_payClientID());  
	}
	 //查询条件-账户号id
	if (info.getQ_payAccountID() > 0) {
		m_sbWhere.append(" and s.npayaccountid = " + info.getQ_payAccountID());
	}	
	
	//order by 
	m_sbOrderBy = new StringBuffer();
	m_sbOrderBy.append(" order by s.scode");		
}


}
