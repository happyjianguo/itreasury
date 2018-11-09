package com.iss.itreasury.settlement.query.Action;

import java.sql.Timestamp;
import java.util.Map;

import com.iss.itreasury.settlement.query.bizlogic.QDepositDetailBiz;
import com.iss.itreasury.settlement.query.paraminfo.QueryDepositDetailWhereInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.sysframe.pager.dataentity.PagerInfo;

/**
 * 账户查询操作类
 * @author xiang
 *
 */
public class QDepositDetailAction {
	
	QDepositDetailBiz biz = new QDepositDetailBiz();
	
	public PagerInfo queryDepositDetail(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{ 
			long lOfficeID = Long.valueOf((String)map.get("lofficeid")).longValue();
			long lCurrencyID = Long.valueOf((String)map.get("lcurrencyid")).longValue();
			String strStartClientCode = "";//客户号
			String strEndClientCode = "";//客户号
			String strAccountType = "";//账户类型
			Timestamp tsDate=null;
			long lAccountStatusID = -1;//账户状态
			long lIsFilter  = -1;//是否滤空
			String strAssistAction ="";
			String strTemp = null;
			strTemp = (String)map.get("strdate");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				tsDate = DataFormat.getDateTime(strTemp);
			}
			strTemp = (String)map.get("strstartclientcode");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				strStartClientCode = strTemp;
			}
			strTemp = (String)map.get("strendclientcode");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				strEndClientCode = strTemp;
			}
			strTemp = (String)map.get("straccounttype");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				strAccountType = strTemp;
			}
			strTemp = (String)map.get("laccountstatusid");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lAccountStatusID = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("lisfiltrate");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lIsFilter = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("strassistaction");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				strAssistAction = strTemp.trim();
			}
			QueryDepositDetailWhereInfo qdwi = new QueryDepositDetailWhereInfo();

			qdwi.setOfficeID(lOfficeID);
			qdwi.setCurrencyID(lCurrencyID);
			qdwi.setStartClientCode(strStartClientCode);//客户号
			qdwi.setEndClientCode(strEndClientCode);//客户号
			qdwi.setAccountType(strAccountType);
			qdwi.setDate(tsDate);
			qdwi.setIsFilter(lIsFilter);
			qdwi.setAccountStatusID(lAccountStatusID);
			pagerInfo = biz.queryDepositDetail(qdwi,strAssistAction );
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}

}
