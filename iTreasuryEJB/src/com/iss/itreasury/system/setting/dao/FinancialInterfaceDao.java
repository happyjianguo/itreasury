package com.iss.itreasury.system.setting.dao;

import com.iss.itreasury.system.setting.dataentity.GlSettingInfo;

public class FinancialInterfaceDao {
	
	/**
	 * 财务接口设置查询类
	 * add by liaiyi 2013-01-28
	 */
	 public String queryFinancialInterfaceSQL(GlSettingInfo glSettingInfo){
		   
		   StringBuffer sbSQL = new StringBuffer();

		   sbSQL.append("SELECT * FROM sett_GlSetting \n");
		   sbSQL.append(" WHERE nStatusID = 1 \n");
		   sbSQL.append(" AND officeID = "+glSettingInfo.getOfficeID()+" \n");
		   
		   if(glSettingInfo.getCurrencyID() > 0){
				sbSQL.append(" and currencyID = '"+glSettingInfo.getCurrencyID()+"'");
			}
		   return sbSQL.toString();
	   }

}
