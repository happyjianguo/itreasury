package com.iss.itreasury.system.approval.dao;

import com.iss.itreasury.system.approval.dataentity.ApprovalOpinionSettingInfo;

public class ApprovalOpinionSettingDao {
	
	/**
	 * 查询已设置审批流的操作类型dao
	 * @author zk 2012-12-12
	 *
	 */
   public String queryApprovalOpinionInfo(ApprovalOpinionSettingInfo info){
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select * from sys_approvalopinion where 1=1 \n");
		if (info.getId() > 0) {
			sql.append(" and id = " + info.getId());
		}
		if (info.getOfficeID() > 0) {
			sql.append(" and officeid = " + info.getOfficeID());
		}
		if (info.getModuleID() > 0) {
			sql.append(" and moduleid = " + info.getModuleID());
		}
		if (info.getCode() != null && info.getCode().length() > 0) {
			sql.append(" and code like '" + info.getCode() + "'");
		}
		if (info.getDescription() != null && info.getDescription().length() > 0) {
			sql.append(" and description like '" + info.getDescription() + "'");
		}
		if (info.getStatusID() > 0) {
			sql.append(" and statusid = " + info.getStatusID());
		}
		if (info.getInputUserID() > 0) {
			sql.append(" and inputuserid = " + info.getInputUserID());
		}
		if (info.getInputDate() != null) {
			sql.append(" and inputdate = " + info.getStatusID());
		}
		
		return sql.toString();
		
	}

}
