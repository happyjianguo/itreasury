package com.iss.itreasury.system.history.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.system.approval.dataentity.InutApprovalRecordInfo;
import com.iss.itreasury.system.history.dataentity.HistoryAdviseInfo;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;

public class ApproveHistoryDao {
	
	/**
     * ÉóÅúÒâ¼û
     * @param HistoryAdviseInfo hInfo
     * @return
     * @throws Exception
     */
	public String queryApproveHistoryInfoByID(HistoryAdviseInfo hInfo) throws Exception {
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select * from sys_historystep where 1=1 ");
		if(hInfo.getId()>0){
			sql.append(" and id = "+ hInfo.getId());
		}
		if(hInfo.getOperator()!=null && hInfo.getOperator().length()>0){
			sql.append(" and CALLER = '" + hInfo.getOperator() + "'");
		}
		if(hInfo.getEntryID()>0){
			sql.append(" and ENTRY_ID = "+ hInfo.getEntryID());
		}
		if(hInfo.getActionKey()!=null && hInfo.getActionKey().length()>0){
			sql.append(" and ACTION_KEY = '"+ hInfo.getActionKey() +"'");
		}
		if(hInfo.getStatusID()>0){
			sql.append(" and STATUSID = "+ hInfo.getStatusID());
		}
		
		return sql.toString();	
	}
	public String queryApproveHistoryInfoWithoutID(InutApprovalRecordInfo info) throws Exception {
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select * from sys_approvalrecord where 1=1 ");
		if(info.getId()>0){
			sql.append(" and id = "+ info.getId());
		}
		if(info.getOfficeID()>0){
			sql.append(" and officeid = " + info.getOfficeID());
		}
		if(info.getCurrencyID()>0){
			sql.append(" and currencyid = " + info.getCurrencyID());
		}
		if(info.getModuleID()>0){
			sql.append(" and moduleid = " + info.getModuleID());
		}
		if(info.getTransTypeID()>0){
			sql.append(" and transtypeid = " + info.getTransTypeID());
		}
		if(info.getActionID()>0){
			sql.append(" and actionid = " + info.getActionID());
		}
		if(info.getTransID()!=null && info.getTransID().length()>0){
			sql.append(" and transid = '" + info.getTransID() + "'");
		}
		if(info.getApprovalEntryID()>0){
			sql.append(" and approvalentryid = " + info.getApprovalEntryID());
		}
		if(info.getLinkUrl()!=null && info.getLinkUrl().length()>0){
			sql.append(" and linkurl like '" + info.getLinkUrl()+"'");
		}
		if(info.getLastAppUserID()>0){
			sql.append(" and lastappuserid = " + info.getLastAppUserID());
		}
		if(info.getStatusID()>=0){
			sql.append(" and statusid = " + info.getStatusID());
		}
		sql.append(" order by id,transtypeid,actionid ");
		
		return sql.toString();	
	}
	public List queryApproveHistoryInfoByCondition(HistoryAdviseInfo hInfo)throws IException{
		
		List list = new ArrayList();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		try {
			con = Database.getConnection();
			
			sql.append("select * from sys_historystep where 1=1 ");
			if(hInfo.getId()>0){
				sql.append(" and id = "+ hInfo.getId());
			}
			if(hInfo.getOperator()!=null && hInfo.getOperator().length()>0){
				sql.append(" and CALLER = '" + hInfo.getOperator() + "'");
			}
			if(hInfo.getEntryID()>0){
				sql.append(" and ENTRY_ID = "+ hInfo.getEntryID());
			}
			if(hInfo.getActionKey()!=null && hInfo.getActionKey().length()>0){
				sql.append(" and ACTION_KEY = '"+ hInfo.getActionKey() +"'");
			}
			if(hInfo.getStatusID()>0){
				sql.append(" and STATUSID = "+ hInfo.getStatusID());
			}
			ps = con.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				HistoryAdviseInfo tempInfo = new HistoryAdviseInfo();
				tempInfo.setId(rs.getLong("id"));
				tempInfo.setOperator(NameRef.getUserNameByID(Long.parseLong((String)rs.getString("CALLER"))));
				tempInfo.setOpTime(rs.getTimestamp("EXECUTE_DATE"));
				tempInfo.setAdvise(rs.getString("ADVISE_VALUE"));
				tempInfo.setAction(rs.getString("ACTION_NAME"));				
				tempInfo.setStatusID(rs.getLong("STATUSID"));
				list.add(tempInfo);				
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		} catch(Exception e){
			e.printStackTrace();
			throw new IException("Gen_E001",e);
		} finally{
			try
			{
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}		
		return list;
	}

}
