package com.iss.itreasury.system.translog.dao;

import com.iss.itreasury.system.logger.dataentity.QueryLoggerInfo;
import com.iss.itreasury.system.translog.dataentity.QueryTransLogInfo;

public class LogInfoDao {
	
	/**
	 * 日志查询dao
	 * @author zk 2012-12-13
	 *
	 */
   public String queryLogInfo(QueryTransLogInfo transLogInfo){
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select a.departmentname, a.username,a.logtype,a.functionpointdescription,a.maininfo,to_char(a.accesstime,'hh24:mi:ss') time, \n");
		sql.append(" 	a.status,a.accesstime,a.clientip,a.currencyid \n");
		sql.append("  from sys_translog_view a \n");
		sql.append(" where 1=1 and a.officeid= "+transLogInfo.getOfficeid()+" \n");
		if (transLogInfo.getCurrencyid() > -1) {
			sql.append(" and a.currencyid= "+transLogInfo.getCurrencyid()+" \n");
		}
		if (transLogInfo.getQueryuserid() != null && transLogInfo.getQueryuserid().length() > 0 && !"-1".equals(transLogInfo.getQueryuserid())) {
			sql.append(" and a.userid = '"+transLogInfo.getQueryuserid()+"' \n");
		}
		if (transLogInfo.getStartdate() != null && transLogInfo.getStartdate().length() > 0) {
			sql.append(" and a.accesstime >= to_date('"+transLogInfo.getStartdate()+"','yyyy-mm-dd') \n");
		}
		if (transLogInfo.getEnddate() != null && transLogInfo.getEnddate().length() > 0) {
			sql.append(" and a.accesstime <= to_date('"+transLogInfo.getEnddate()+"','yyyy-mm-dd')+1 \n");
		}
		if (transLogInfo.getStarttime() != null && transLogInfo.getStarttime().length() > 0) {
			sql.append(" and substr(to_char(a.accesstime,'yyyy-mm-dd hh24:mi:ss'),instr(to_char(a.accesstime,'yyyy-mm-dd hh24:mi:ss'),' ')+1,8) >= '"+transLogInfo.getStarttime()+"' \n");
		}
		if (transLogInfo.getEndtime() != null && transLogInfo.getEndtime().length() > 0) {
			sql.append(" and substr(to_char(a.accesstime,'yyyy-mm-dd hh24:mi:ss'),INSTR(to_char(a.accesstime,'yyyy-mm-dd hh24:mi:ss'),' ')+1,8) <= '"+transLogInfo.getEndtime()+"' \n");
		}
		if (transLogInfo.getQuerylogtype() != null && transLogInfo.getQuerylogtype().length() > 0 && !"0".equals(transLogInfo.getQuerylogtype())) {
			sql.append(" and a.logtype = '"+transLogInfo.getQuerylogtype()+"' \n");
		}
		if (transLogInfo.getQuerystatus() != null && transLogInfo.getQuerystatus().length() > 0 && !"2".equals(transLogInfo.getQuerystatus())) {
			sql.append(" and a.status = '"+transLogInfo.getQuerystatus()+"' \n");
		}
		if (transLogInfo.getDepartmentid() > -1) {
			sql.append(" and a.departmentid = "+transLogInfo.getDepartmentid()+" \n");
		}
		if (transLogInfo.getClientid() > -1) {
			sql.append(" and a.clientid = "+transLogInfo.getClientid()+" \n");
		}
		if (transLogInfo.getUsertype() > -1) {
			sql.append(" and a.usertype = "+transLogInfo.getUsertype()+" \n");
		}
		
		return sql.toString();
		
	}
   /**
    * 操作日志查询dao
	* @author zk 2012-12-14
    */
   	public String queryOperationLogInfo(QueryLoggerInfo loggerInfo){
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select s.id as id,s.moduleID as moduleid,s.officeID as officeid,s.currencyID as currencyid,s.accessTime as accesstime,to_char(s.accessTime,'hh24:mi:ss') time,s.userID as userid, \n");
		sql.append(" 	s.userName as username,s.functionPointCode as functionpointcode,s.functionPointDescription as functionpointdescription,s.remoteIP as remoteip,s.remoteHost as remotehost, \n");
		sql.append(" 	s.sbusinesstype as businesstype,s.stranscode as transcode,s.sactiontype as actiontype,s.sresult as result,s.sfailreason as failreason, d.departmentname \n");
		sql.append("  from sys_logger_btnlevel s, department d,userinfo u \n");
		sql.append(" where 1=1 and s.officeid="+loggerInfo.getOfficeID()+" and s.userid=u.id and u.ndepartmentid = d.id(+) \n");
		if (loggerInfo.getModuleID() > 0) {
			sql.append(" and s.moduleid= "+loggerInfo.getModuleID()+" \n");
		}
		if (loggerInfo.getCurrencyID() > 0) {
			sql.append(" and s.currencyid= "+loggerInfo.getCurrencyID()+" \n");
		}
		if (loggerInfo.getUserName() != null && loggerInfo.getUserName().length() > 0) {
			sql.append(" and s.username='"+loggerInfo.getUserName()+"' \n");
		}
		if (loggerInfo.getStartDate() != null && loggerInfo.getStartDate().length() > 0) {
			sql.append(" and s.accesstime>=to_date('"+loggerInfo.getStartDate()+"','yyyy-mm-dd hh24:mi:ss') \n");
		}
		if (loggerInfo.getEndDate() != null && loggerInfo.getEndDate().length() > 0) {
			sql.append(" and s.accesstime<=(to_date('"+loggerInfo.getEndDate()+ "','yyyy-mm-dd hh24:mi:ss')) \n");
		}
		if (loggerInfo.getResult() != null && loggerInfo.getResult().length() > 0) {
			sql.append(" and s.sresult='"+loggerInfo.getResult()+"' \n");
		}
		if (loggerInfo.getDepartmentid() > 0) {
			sql.append(" and d.id="+loggerInfo.getDepartmentid()+" \n");
		}
		
		return sql.toString();
		
	}

}
