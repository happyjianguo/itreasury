package com.iss.itreasury.settlement.setting.dao;

import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.SettlementDAO;

public class SettOfficeTimeDAO extends SettlementDAO{
	public SettOfficeTimeDAO (){
		super();
		this.strTableName = "sett_officetime";
		this.setUseMaxID();
	}
	
	public Collection getExistCurrency(long lOfficeId) throws Exception{
		Collection c = new ArrayList();
		String strSQL = "select nCurrencyId from sett_officetime where nOfficeId = "+lOfficeId;
		try{
			initDAO();
			transPS = transConn.prepareStatement(strSQL);
			transRS = transPS.executeQuery();
			while(transRS != null && transRS.next()){
				c.add(String.valueOf(transRS.getLong("nCurrencyId")));
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}finally{
			finalizeDAO();
		}
		return c;
	}
}
