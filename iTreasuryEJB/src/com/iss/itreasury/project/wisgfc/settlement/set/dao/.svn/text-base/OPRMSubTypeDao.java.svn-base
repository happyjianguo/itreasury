package com.iss.itreasury.project.wisgfc.settlement.set.dao;

import java.sql.SQLException;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.project.wisgfc.settlement.set.dataentity.OPRMSubTypeInfo;
import com.iss.itreasury.util.Constant;

/**
 * @author xlchang 2010-10-29
 * 多借多贷子类型数据库操作类
 */
public class OPRMSubTypeDao extends ITreasuryDAO{

public OPRMSubTypeDao() {
		super("sett_opmrsubtype","SEQ_SETT_OPRMSubType");
}

/**
 * 查找多借多贷子类型信息
 * @return
 * @throws ITreasuryDAOException 
 */
public Collection findByCondition(OPRMSubTypeInfo info) throws ITreasuryDAOException {
	return super.findByCondition(info, " order by scode ");
}

/**
 * 根据页面上的查询条件查找多借多贷子类型信息
 * @return
 * @throws ITreasuryDAOException 
 * @throws SQLException 
 */
public Collection findByQueryCondition(OPRMSubTypeInfo info) throws ITreasuryDAOException, SQLException {
	StringBuffer sb = new StringBuffer();
	Collection c = null;
	try {		
		initDAO();
		sb.append("select *  from ");		
		sb.append(strTableName);
		sb.append(" s where s.nstatusid = ? and s.nofficeid = ? and s.ncurrencyid = ?");	
		if (info.getQ_startCode() != null && info.getQ_startCode().length() > 0) {
			sb.append(" and s.scode >= ? ");
		}
		if (info.getQ_endCode() != null && info.getQ_endCode().length() > 0) {
			sb.append(" and s.scode <= ? ");
		}
		sb.append(" order by s.scode ");
		prepareStatement(sb.toString());
		int index = 1;
		transPS.setLong(index++,Constant.RecordStatus.VALID);
		transPS.setLong(index++,info.getNOfficeID());
		transPS.setLong(index++,info.getNCurrencyID());
		if (info.getQ_startCode() != null && info.getQ_startCode().length() > 0) {
			transPS.setString(index++,info.getQ_startCode());
		}
		if (info.getQ_endCode() != null && info.getQ_endCode().length() > 0) {
			transPS.setString(index++,info.getQ_endCode());
		}
		executeQuery();
		c = getDataEntitiesFromResultSet(OPRMSubTypeInfo.class);
	} finally {
		finalizeDAO();
	}
	return c;
}
/**
 * 获得多借多贷子类型编码
 * @param info
 * @return
 * @throws ITreasuryDAOException
 * @throws SQLException
 */
public String getNewCode(OPRMSubTypeInfo info) throws ITreasuryDAOException, SQLException {
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
		OPRMSubTypeInfo rInfo =(OPRMSubTypeInfo)getDataEntityFromResultSet(OPRMSubTypeInfo.class);
		if (rInfo != null) {
			code = rInfo.getSCode();
		}
	} finally {
		finalizeDAO();
	}
	return code;
}

/**
 * 保存多借多贷子类型信息
 * @param info
 * @throws ITreasuryDAOException
 */
public void add(OPRMSubTypeInfo info) throws ITreasuryDAOException {
	super.add(info);
}

/**
 * 更新多借多贷子类型信息
 * @param info
 * @throws ITreasuryDAOException
 */
public void update(OPRMSubTypeInfo info) throws ITreasuryDAOException {
	super.update(info);
}

/**
 * 检查子类是否被使用
 * @param info
 * @return
 * @throws ITreasuryDAOException
 * @throws SQLException
 */
public boolean usedCheck(OPRMSubTypeInfo info) throws ITreasuryDAOException, SQLException {
	StringBuffer sb = new StringBuffer();
	long count = 0;
	try {		
		initDAO();
		sb.append("select count(*) num from sett_TransOnePayMultiReceive t");
		sb.append(" where t.nstatusid > ? and t.nsubtypeid = ? ");
		prepareStatement(sb.toString());
		int index = 1;
		transPS.setLong(index++,Constant.RecordStatus.INVALID);
		transPS.setLong(index++,info.getId());
		transRS = executeQuery();
		if (transRS.next()) {
			count = transRS.getLong("num");
		}		
	} finally {
		finalizeDAO();
	}
	return count == 0? true : false;
}


}
