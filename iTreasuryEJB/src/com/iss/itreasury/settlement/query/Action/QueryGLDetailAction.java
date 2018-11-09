package com.iss.itreasury.settlement.query.Action;

import java.sql.Timestamp;
import java.util.Map;

import com.iss.itreasury.settlement.query.bizlogic.QueryGLDetailBiz;
import com.iss.itreasury.util.DataFormat;
import com.iss.sysframe.pager.dataentity.PagerInfo;

/**
 * 开户行余额汇总查询
 * @author songwenxiao
 *
 */
public class QueryGLDetailAction {
	
	QueryGLDetailBiz biz = new QueryGLDetailBiz();
	
	public PagerInfo queryGLDetail(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{
			
			//定义变量
			long lCurrencyID = -1; 
		   	long lOfficeID = -1; 
		  //得到参数
			String allSub = "";
			String strTemp = "";
			Timestamp tsDateStart = null;
			Timestamp tsDateEnd = null;
			long lAccountId = -1;
			String strRootAccount = "";
			String strAccount="";
			String strURL="";
			long lTypeID = -1;
			long lTransTypeID = -1;
			String strTransNo = "";
			String lCurrencyType="";


			//获得查询日期
			strTemp = (String) map.get("tsDateStart".toLowerCase());
		    if(strTemp != null && strTemp.length() > 0)
			{
		    	tsDateStart = DataFormat.getDateTime(strTemp);
			}
			
			strTemp = (String) map.get("tsDateEnd".toLowerCase());
			if(strTemp != null && strTemp.length() > 0)
			{ 
				tsDateEnd = DataFormat.getDateTime(strTemp);
			}
			
			strTemp = (String) map.get("lTypeID".toLowerCase());
		    if(strTemp != null && strTemp.length() > 0)
			{
		    	lTypeID = Long.parseLong(strTemp);
			}
		    
		    strTemp = (String) map.get("strAccount".toLowerCase());
		    if(strTemp != null && strTemp.length() > 0)
		    {
		    	strAccount = strTemp;
		    }
		    
		    strTemp = (String) map.get("lOfficeID".toLowerCase());
		    if(strTemp != null && strTemp.length() > 0)
		    {
		    	lOfficeID = Long.valueOf(strTemp);
		    }
		    
		    strTemp = (String) map.get("lCurrencyID".toLowerCase());
		    if(strTemp != null && strTemp.length() > 0)
		    {
		    	lCurrencyID = Long.valueOf(strTemp);
		    }
		    
		    strTemp = (String) map.get("lTransactionTypeID".toLowerCase());
		    if(strTemp != null && strTemp.length() > 0)
		    {
		    	lTransTypeID = Long.parseLong(strTemp);
		    }
		    
		    strTemp = (String) map.get("lAccountId".toLowerCase());
			if(strTemp != null && strTemp.length() > 0){
				lAccountId = Long.parseLong(strTemp);
			}
			
			 strTemp = (String) map.get("lCurrencyType".toLowerCase());
			    if(strTemp != null && strTemp.length() > 0)
			    {
			    	lCurrencyType = strTemp;
			    }
			
			pagerInfo = biz.findGLDetails(lAccountId, strAccount, strTransNo, lOfficeID, lCurrencyID, lTypeID, lTransTypeID, tsDateStart, tsDateEnd,lCurrencyType);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}

}
