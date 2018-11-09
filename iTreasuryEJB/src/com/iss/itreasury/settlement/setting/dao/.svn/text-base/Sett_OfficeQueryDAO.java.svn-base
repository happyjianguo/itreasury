package com.iss.itreasury.settlement.setting.dao;

public class Sett_OfficeQueryDAO {
	
	public String queryOfficeSql() {
		
		String strSQL = null;
		StringBuffer sb = new StringBuffer();
		sb.append(" select a.id id, a.sCode sCode, a.sName sName, a.ORGLEVEL ORGLEVEL1 ,( case a.ORGLEVEL when 1 then 'ÊÇ' else '·ñ' end) ORGLEVEL  from Office a where  nStatusID>0 \n");
		strSQL = sb.toString();
		return strSQL;
		
	}

}
