package com.iss.itreasury.settlement.reportlossorfreeze.action;

import java.util.Map;

import com.iss.itreasury.settlement.reportlossorfreeze.biz.QReportLossOrFreezeBiz;
import com.iss.itreasury.settlement.reportlossorfreeze.dataentity.ReportLossOrFreezeQueryInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class QReportLossOrFreezeAction {
	QReportLossOrFreezeBiz biz = new QReportLossOrFreezeBiz();
	public PagerInfo queryReportLossOrFreezeInfo(Map map) throws Exception{
		
		PagerInfo pagerInfo = null;
		
		String strTemp;
		String strExecuteStart = "";
		String strExecuteEnd = "";
		long lTransActionType = -1;
		long lClientId = -1;
		long lCurrencyId = Long.valueOf(map.get("lcurrencyid")+"").longValue();
		long lOfficeId =  Long.valueOf(map.get("lofficeid")+"").longValue();
		
		strTemp = (String)map.get("lclientid");
		if( strTemp != null && strTemp.length() > 0 )
		{
			lClientId = Long.parseLong(strTemp.trim());
		}
		strTemp = (String)map.get("strexecutestart");
		if( strTemp != null && strTemp.length() > 0 )
		{
			strExecuteStart = strTemp.trim();
		}
		strTemp = (String)map.get("strexecuteend");
		if( strTemp != null && strTemp.length() > 0 )
		{
			strExecuteEnd = strTemp.trim();
		}
		
		strTemp = (String)map.get("ltransactiontype");
		if( strTemp != null && strTemp.length() > 0 )
		{
			lTransActionType = Long.parseLong(strTemp.trim());
		}
		
		ReportLossOrFreezeQueryInfo qInfo = new ReportLossOrFreezeQueryInfo();
		qInfo.setTransActionType(lTransActionType);
		qInfo.setStartDate(DataFormat.getDateTime((strExecuteStart)));
		qInfo.setEndDate(DataFormat.getDateTime((strExecuteEnd)));
		qInfo.setClientId(lClientId);
		qInfo.setOfficeId(lOfficeId);
		qInfo.setCurrencyId(lCurrencyId);
		qInfo.setStatus(3);
		return pagerInfo = biz.getReportLoss(qInfo);
	}
	
	public PagerInfo queryFreezeInfo(Map map) throws Exception{
		
		PagerInfo pagerInfo = null;
		
		String strTemp;
		String strExecuteStart = "";
		String strExecuteEnd = "";
		long lTransActionType = -1;
		long lClientId = -1;
		long lCurrencyId = Long.valueOf(map.get("lcurrencyid")+"").longValue();
		long lOfficeId =  Long.valueOf(map.get("lofficeid")+"").longValue();
		
		strTemp = (String)map.get("lclientid");
		if( strTemp != null && strTemp.length() > 0 )
		{
			lClientId = Long.parseLong(strTemp.trim());
		}
		strTemp = (String)map.get("strexecutestart");
		if( strTemp != null && strTemp.length() > 0 )
		{
			strExecuteStart = strTemp.trim();
		}
		strTemp = (String)map.get("strexecuteend");
		if( strTemp != null && strTemp.length() > 0 )
		{
			strExecuteEnd = strTemp.trim();
		}
		
		strTemp = (String)map.get("ltransactiontype");
		if( strTemp != null && strTemp.length() > 0 )
		{
			lTransActionType = Long.parseLong(strTemp.trim());
		}
		
		ReportLossOrFreezeQueryInfo qInfo = new ReportLossOrFreezeQueryInfo();
		qInfo.setTransActionType(lTransActionType);
		qInfo.setStartDate(DataFormat.getDateTime((strExecuteStart)));
		qInfo.setEndDate(DataFormat.getDateTime((strExecuteEnd)));
		qInfo.setClientId(lClientId);
		qInfo.setOfficeId(lOfficeId);
		qInfo.setCurrencyId(lCurrencyId);
		qInfo.setStatus(3);
		return pagerInfo = biz.queryFreezeInfo(qInfo);
	}
}
