package com.iss.itreasury.settlement.query.Action;

import java.sql.Timestamp;
import java.util.Map;

import com.iss.itreasury.settlement.query.bizlogic.QuerySubjectBalanceBiz;
import com.iss.itreasury.util.DataFormat;
import com.iss.sysframe.pager.dataentity.PagerInfo;

/**
 * 科目余额查询
 * @author songwenxiao
 *
 */
public class QuerySubjectBalanceAction {
	
	QuerySubjectBalanceBiz biz = new QuerySubjectBalanceBiz();
	
	public PagerInfo querySubjectBalanceDetail(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{
			
			//定义变量
			long lCurrencyID = -1; 
		   	long lOfficeID = -1; 
		  //得到参数
			String strTemp = "";
			Timestamp tsStart = null;
			Timestamp tsEnd = null;
			String strSubjectStart = "";
			String strSubjectEnd="";
			long lUnit=-1;
			String lCurrencyType="";


			//获得查询日期
			strTemp = (String) map.get("dtstart".toLowerCase());
 		    if(strTemp != null && strTemp.length() > 0)
			{
		    	tsStart = DataFormat.getDateTime(strTemp);
			}
			
			strTemp = (String) map.get("dtend".toLowerCase());
			if(strTemp != null && strTemp.length() > 0)
			{ 
				tsEnd = DataFormat.getDateTime(strTemp);
			}
			
			strTemp = (String) map.get("strSubjectStartCtrl".toLowerCase());
		    if(strTemp != null && strTemp.length() > 0)
			{
		    	strSubjectStart = strTemp;
			}
		    
		    strTemp = (String) map.get("strSubjectEndCtrl".toLowerCase());
		    if(strTemp != null && strTemp.length() > 0)
		    {
		    	strSubjectEnd = strTemp;
		    }
		    
		    strTemp = (String) map.get("OfficeID".toLowerCase());
		    if(strTemp != null && strTemp.length() > 0)
		    {
		    	lOfficeID = Long.valueOf(strTemp);
		    }
		    
		    strTemp = (String) map.get("lCurrencyID".toLowerCase());
		    if(strTemp != null && strTemp.length() > 0)
		    {
		    	lCurrencyID = Long.valueOf(strTemp);
		    }
		    
		    strTemp = (String) map.get("amount".toLowerCase());
		    if(strTemp != null && strTemp.length() > 0)
		    {
		    	lUnit = Long.parseLong(strTemp);
		    }
		    
			 strTemp = (String) map.get("lCurrencyType".toLowerCase());
			    if(strTemp != null && strTemp.length() > 0)
			    {
			    	lCurrencyType = strTemp;
			    }
			
			pagerInfo = biz.findSubjectBalance(lOfficeID, lCurrencyID, tsStart, tsEnd, strSubjectStart, strSubjectEnd, lCurrencyType, lUnit);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}

}
