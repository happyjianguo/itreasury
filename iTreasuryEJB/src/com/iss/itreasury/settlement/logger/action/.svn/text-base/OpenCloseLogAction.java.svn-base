package com.iss.itreasury.settlement.logger.action;

import java.util.Map;

import com.iss.itreasury.settlement.logger.bizlogic.OpenCloseLogNewUIBiz;
import com.iss.itreasury.settlement.logger.dataentity.QueryOpenCloseLog;
import com.iss.sysframe.pager.dataentity.PagerInfo;

/**
 * 开关机日志查询
 * @author songwenxiao
 *
 */
public class OpenCloseLogAction {
	
	OpenCloseLogNewUIBiz biz = new OpenCloseLogNewUIBiz();
	
	public PagerInfo queryOpenCloseLogInfo(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		QueryOpenCloseLog searchInfo  = new QueryOpenCloseLog();
		try
		{
			String strPageLoaderKey = "";
			long lOpenCloseType = -1;
			long lOfficeID = -1;
			long lCurrencyID = -1;
			String dtStart = null;
			String dtEnd = null;
			String strTemp = null;
			
			strTemp = (String) map.get("openCloseType".toLowerCase());
			if(strTemp != null && strTemp.trim().length() > 0)
			{
				lOpenCloseType = Long.parseLong(strTemp);
			}
			strTemp = (String) map.get("txtLoanStart".toLowerCase());
			if(strTemp != null && strTemp.trim().length() > 0)
			{
				dtStart = strTemp;
			}
			strTemp = (String) map.get("txtLoanEnd".toLowerCase());
			if(strTemp != null && strTemp.trim().length() > 0)
			{
				dtEnd = strTemp;
			}
			strTemp = (String) map.get("_pageLoaderKey".toLowerCase());
			if(strTemp != null && strTemp.trim().length() > 0)
			{
				strPageLoaderKey = strTemp;
			}
			strTemp = (String) map.get("lOfficeID".toLowerCase());
			if(strTemp != null && strTemp.trim().length() > 0)
			{
				lOfficeID = Long.parseLong(strTemp);
			}
			strTemp = (String) map.get("lCurrencyID".toLowerCase());
			if(strTemp != null && strTemp.trim().length() > 0)
			{
				lCurrencyID = Long.parseLong(strTemp);
			}

	        
	        if(!strPageLoaderKey.equals("")) {
	        	//searchInfo = (QueryOpenCloseLog)sessionMng.getQueryCondition(strPageLoaderKey);
	        }
	        else {
		        searchInfo.setOfficeId(lOfficeID);
		        searchInfo.setCurrencyId(lCurrencyID);
				searchInfo.setOpenCloseType(lOpenCloseType);
				searchInfo.setOpenCloseStartDate(dtStart);
				searchInfo.setOpenCloseEndDate(dtEnd);
			}
			
			pagerInfo = biz.getOpenCloseLogInfo(searchInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	
	/**
	 * 开关机日志查询——详细信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PagerInfo queryOpenCloseLogDetail(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{
			String code = "";
			String strTemp = null;
			
			strTemp = (String) map.get("logCode".toLowerCase());
			if(strTemp != null && strTemp.trim().length() > 0)
			{
				code = strTemp;
			}
			
			pagerInfo = biz.getOpenCloseLogDetail(code);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}


}
