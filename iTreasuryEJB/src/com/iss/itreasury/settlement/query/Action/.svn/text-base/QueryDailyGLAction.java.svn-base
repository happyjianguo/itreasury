package com.iss.itreasury.settlement.query.Action;

import java.sql.Timestamp;
import java.util.Map;

import com.iss.itreasury.settlement.query.bizlogic.QueryDailyGLBiz;
import com.iss.itreasury.settlement.query.paraminfo.AccountRecordConditionInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.sysframe.pager.dataentity.PagerInfo;

/**
 * 日结科目汇总查询
 * @author songwenxiao
 *
 */
public class QueryDailyGLAction {
	
	QueryDailyGLBiz biz = new QueryDailyGLBiz();
	
	public PagerInfo queryDailyGLDetail(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{
			
			//定义变量
			long lCurrencyID = -1; 
		   	long lOfficeID = -1; 
		  //得到参数
			String strTemp = "";
			Timestamp tsDateStart = null;
			Timestamp tsDateEnd = null;
			String lCurrencyType="";
			String allSub = "";
			long iflk = -1;//是否虑空
			long lUnit=-1;
			AccountRecordConditionInfo conditionInfo = new AccountRecordConditionInfo();


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
			
			strTemp = (String) map.get("allSub".toLowerCase());
		    if(strTemp != null && strTemp.length() > 0)
			{
		    	allSub = strTemp;
			}
		    
		    strTemp = (String) map.get("iflk".toLowerCase());
		    if(strTemp != null && strTemp.length() > 0)
		    {
		    	iflk = Long.parseLong(strTemp);
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
			
			 strTemp = (String) map.get("lCurrencyType".toLowerCase());
		    if(strTemp != null && strTemp.length() > 0)
		    {
		    	lCurrencyType = strTemp;
		    }
		    
		    strTemp = (String) map.get("lAmount".toLowerCase());
		    if(strTemp != null && strTemp.length() > 0)
		    {
		    	lUnit = Long.parseLong(strTemp);
		    }
		    
		    conditionInfo.setTsDateEnd(tsDateEnd);
			conditionInfo.setTsDateStart(tsDateStart);
			conditionInfo.setOfficeId(lOfficeID);
			conditionInfo.setCurrencyId(lCurrencyID);
			conditionInfo.setIflv(iflk);
			conditionInfo.setAllSub(allSub);
		
			pagerInfo = biz.getDailyGL(conditionInfo, lUnit, lCurrencyType);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}

}
