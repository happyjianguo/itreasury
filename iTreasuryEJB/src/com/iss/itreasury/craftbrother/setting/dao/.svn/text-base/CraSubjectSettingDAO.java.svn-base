package com.iss.itreasury.craftbrother.setting.dao;

import java.sql.Connection;
import java.util.Collection;
import com.iss.itreasury.craftbrother.setting.dataentity.CraSubjectSettingInfo;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.base.SettlementException;


public class CraSubjectSettingDAO extends SettlementDAO{
	
	public CraSubjectSettingDAO()
	{
		super();
		this.strTableName = "CRA_SUBJECTSETTING";
	}
	public CraSubjectSettingDAO(Connection conn)
	{
		super(conn);
		this.strTableName = "CRA_SUBJECTSETTING";
	}
	
	public long saveSubjectSettingInfo(CraSubjectSettingInfo info) throws SettlementException
	{
		String sttransRSQL = null;
		long lResult = -1;
		try {
			this.initDAO();
			// if Code duplicate
			sttransRSQL = "select id from CRA_SUBJECTSETTING where StatusID > 0 and businessTypeID=?";
			transPS = transConn.prepareStatement(sttransRSQL);
			transPS.setLong(1, info.getBusinessTypeID());
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
			throw new SettlementException("Gen_E001",e);
		} finally {
			try {
				this.finalizeDAO();
			} catch (Exception ex) {
				
			}
		}
		return lResult;
	}
	

}
