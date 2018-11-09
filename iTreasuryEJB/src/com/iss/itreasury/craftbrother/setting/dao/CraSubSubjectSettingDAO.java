package com.iss.itreasury.craftbrother.setting.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.iss.itreasury.craftbrother.setting.dataentity.CraSubSubjectSettingInfo;
import com.iss.itreasury.craftbrother.setting.dataentity.CraSubjectSettingInfo;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.base.SettlementException;

public class CraSubSubjectSettingDAO extends SettlementDAO{
	
	public CraSubSubjectSettingDAO()
	{
		super();
		this.strTableName = "cra_sub_subjectsetting";
		setUseMaxID();
	}
	
	public CraSubSubjectSettingDAO(Connection conn)
	{
		super(conn);
		this.strTableName = "cra_sub_subjectsetting";
		setUseMaxID();
	}
	
	public long saveSubSubjectSettingInfo(CraSubSubjectSettingInfo info) throws SettlementException
	{
		String sttransRSQL = null;
		long lResult = -1;
		int index = 1;
		try {
			this.initDAO();
			// if Code duplicate
			if(info.getCounterPartID() > 0)
			{
				sttransRSQL = "select id from cra_sub_subjectsetting where StatusID > 0 and SUBJECTSETTID = ? and COUNTERPARTID = ? and SUBTYPEID = ?";
			}
			else
			{
				sttransRSQL = "select id from cra_sub_subjectsetting where StatusID > 0 and SUBJECTSETTID = ? and SUBTYPEID = ?";
			}
			transPS = transConn.prepareStatement(sttransRSQL);
			transPS.setLong(index++, info.getSubjectSettID());
			if(info.getCounterPartID() > 0)
			{
				transPS.setLong(index++, info.getCounterPartID());
			}
			transPS.setLong(index++, info.getSubTypeID());
			transRS = transPS.executeQuery();
			while (transRS.next()) {
				long lVar = transRS.getLong("id");
				if (lVar != info.getId()) {
					return -1;
				}
			}
			if (info.getId() < 0) {
				lResult = this.add(info);
			} else {
				this.update(info);
				lResult = info.getId();
			}

		} catch (Exception e) {
			throw new SettlementException();
		} finally {
			try {
				this.finalizeDAO();
			} catch (Exception ex) {
				
			}
		}
		return lResult;
	}
	
	public long updateStatus(long id, long StatusID) throws Exception
	{
		long lReturn = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = getConnection();
			/**Important: If any field in database changed, please correct them at here and fucntion:addDatatoPrepareStatement*/
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("UPDATE \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" SET \n");
			//strSQLBuffer.append("dtModify = sysdate, \n");
			strSQLBuffer.append("StatusID = ? \n");
			strSQLBuffer.append(" WHERE ID = ? \n");
			String strSQL = strSQLBuffer.toString();
			ps = con.prepareStatement(strSQL);
			log.info(strSQL);
			ps.setLong(1, StatusID);
			ps.setLong(2, id);
			ps.executeUpdate();
		}
		finally
		{
			cleanup(ps);
			cleanup(con);				
		}
		return id;
	}

}
