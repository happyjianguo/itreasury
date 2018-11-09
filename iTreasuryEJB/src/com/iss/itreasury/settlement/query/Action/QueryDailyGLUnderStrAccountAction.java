package com.iss.itreasury.settlement.query.Action;

import java.sql.Timestamp;
import java.util.Map;

import com.iss.itreasury.settlement.query.bizlogic.QDailySubjectBiz;
import com.iss.itreasury.util.DataFormat;
import com.iss.sysframe.pager.dataentity.PagerInfo;

/**
 * 日结科目汇总查询-科目类型
 * @author songwenxiao
 *
 */

public class QueryDailyGLUnderStrAccountAction {
	
	QDailySubjectBiz biz = new QDailySubjectBiz();
	
	public PagerInfo queryDailyGLUnderSubject(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{

	       //定义变量
			 //定义变量
			long lOfficeID = -1;//办事处
			long lCurrencyID = -1;//币种
			
			long lAccountId = -1;
			String strRootAccount = "";
			Timestamp tsDateStart=null;
			Timestamp tsDateEnd =null;

			//读取数据
			String strTemp = null;
			strTemp = (String)map.get("lAccountId".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lAccountId=Long.parseLong(strTemp);
			}	
			strTemp = (String)map.get("lOfficeID".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lOfficeID=Long.parseLong(strTemp);
			}	
			strTemp = (String)map.get("lCurrencyID".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lCurrencyID=Long.parseLong(strTemp);
			}	
			strTemp = (String)map.get("strRootAccount".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				strRootAccount =strTemp;
			}	
			strTemp = (String)map.get("tsDateStart".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				tsDateStart = DataFormat.getDateTime(strTemp);
			}	
			strTemp = (String)map.get("tsDateEnd".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				tsDateEnd = DataFormat.getDateTime(strTemp);
			}
			
			pagerInfo = biz.queryCounterTrans(lAccountId, lOfficeID, lCurrencyID, tsDateStart, tsDateEnd);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}

}
