package com.iss.itreasury.settlement.query.Action;

import java.sql.Timestamp;
import java.util.Map;

import com.iss.itreasury.settlement.query.bizlogic.QDepositDetailsBiz;
import com.iss.itreasury.settlement.query.paraminfo.QueryAccountWhereInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.sysframe.pager.dataentity.PagerInfo;

/**
 * 账户查询操作类
 * 
 * @author xiang
 * 
 */
public class QDepositDetailsAction {

	QDepositDetailsBiz biz = new QDepositDetailsBiz();

	public PagerInfo queryDepositDetails(Map map) throws Exception {

		long lOfficeID = Long.valueOf((String) map.get("lofficeid"))
				.longValue();// 办事处
		long lCurrencyID = Long.valueOf((String) map.get("lcurrencyid"))
				.longValue();// 币种
		long lIsFilterNull = -1;
		Timestamp tsQueryDate = null;
		long unit = 1;
		String strTemp = "";
		strTemp = (String) map.get("strquerydate");
		if (strTemp != null && strTemp.trim().length() > 0) {
			tsQueryDate = DataFormat.getDateTime(strTemp);
		}

		strTemp = (String) map.get("lisfilternull");
		if (strTemp != null && strTemp.trim().length() > 0) {
			lIsFilterNull = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String) map.get("unit");
		if (strTemp != null && strTemp.trim().length() > 0) {
			unit = Long.parseLong(strTemp);
		}
		QueryAccountWhereInfo info = new QueryAccountWhereInfo();
		info.setOfficeID(lOfficeID);
		info.setCurrencyID(lCurrencyID);
		info.setQueryDate(tsQueryDate);
		info.setIsFilterNull(lIsFilterNull);
		info.setStartQueryDate(info.getQueryDate());
		info.setEndQueryDate(info.getQueryDate());

		PagerInfo pagerInfo = biz.queryQueryAccount(info, unit);
		return pagerInfo;
	}
	
	public PagerInfo queryAccountInfo(Map map) throws Exception {

		long lOfficeID = Long.valueOf((String) map.get("lofficeid"))
				.longValue();// 办事处
		long lCurrencyID = Long.valueOf((String) map.get("lcurrencyid"))
				.longValue();// 币种
		long lIsFilterNull = -1;
		Timestamp tsQueryDate = null;
		long unit = 1;
		String strTemp = "";
		String lClientIDEndCtrl = "";
		String lClientIDStartCtrl = "";
		String CurrencySymbol = "";
		strTemp = (String) map.get("QueryDate");
		if (strTemp != null && strTemp.trim().length() > 0) {
			tsQueryDate = DataFormat.getDateTime(strTemp);
		}

		strTemp = (String) map.get("EndClientCode");
		if (strTemp != null && strTemp.trim().length() > 0) {
			lClientIDEndCtrl =strTemp;
		}
		
		strTemp = (String) map.get("StartClientCode");
		if (strTemp != null && strTemp.trim().length() > 0) {
			lClientIDStartCtrl =strTemp;
		
		}
		strTemp = (String) map.get("unit");
		if (strTemp != null && strTemp.trim().length() > 0) {
			unit = Long.parseLong(strTemp);
		}
		
		strTemp = (String) map.get("currencysymbol");
		if (strTemp != null && strTemp.trim().length() > 0) {
			CurrencySymbol = strTemp;
		}
		
		QueryAccountWhereInfo	info = new QueryAccountWhereInfo();
		//
        info.setOfficeID(lOfficeID);
		info.setCurrencyID(lCurrencyID);
		info.setStartClientCode(lClientIDStartCtrl);
		info.setEndClientCode(lClientIDEndCtrl);
		info.setQueryDate(tsQueryDate);

		PagerInfo pagerInfo = biz.queryAccountInfo(info, unit,CurrencySymbol);
		return pagerInfo;
	}
	

}
