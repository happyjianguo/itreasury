package com.iss.itreasury.settlement.query.Action;

import java.sql.Timestamp;
import java.util.Map;

import com.iss.itreasury.settlement.query.bizlogic.QClientGatherBiz;
import com.iss.itreasury.settlement.query.paraminfo.QueryClientGatherWhereInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.sysframe.pager.dataentity.PagerInfo;

/**
 * 账户查询操作类
 * @author xiang
 *
 */
public class QClientGatherAction {
	
	QClientGatherBiz qclientgatherbiz = new QClientGatherBiz();
	
	public PagerInfo queryClientGather(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{ 
			 //定义变量
			String strStartClientCode = ""; // 查询由客户编号
			String strEndClientCode = ""; // 查询至客户编号
			String strAccountType = "0";//账户类型
			long lAccountStatusID = -1;//账户状态
			long lIsIncludeParent = -1;//包括母公司
			long lIsIncluedSub  = -1;//包括子公司
			long lIsFilter  = -1;//是否滤空
			long lStartClientID = -1;
			long lEndClientID = -1;
			Timestamp tsDate = null;//结束日期
			//读取数据
			String strTemp = null;
			long lCurrencyID=-1;
			long lOfficeID=-1;
			strTemp = (String)map.get("lcurrencyid");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lCurrencyID = Long.valueOf(strTemp).longValue();
			}			strTemp = (String)map.get("lofficeid");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lOfficeID = Long.valueOf(strTemp).longValue();
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

			strTemp = (String)map.get("lstartclientid");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lStartClientID = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("lendclientid");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lEndClientID = Long.valueOf(strTemp).longValue();
			}

			strTemp = (String)map.get("tsdate");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				tsDate = DataFormat.getDateTime(strTemp);
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
			strTemp = (String)map.get("lisincludeparent");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lIsIncludeParent = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("lisincluedsub");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lIsIncluedSub = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("lisfilter");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lIsFilter = Long.valueOf(strTemp).longValue();
			}
			QueryClientGatherWhereInfo	qcgi = new QueryClientGatherWhereInfo();
			
		    qcgi.setOfficeID(lOfficeID);
			qcgi.setCurrencyID(lCurrencyID);
			qcgi.setStartClientCode(strStartClientCode);
			qcgi.setEndClientCode(strEndClientCode);
			qcgi.setAccountType(strAccountType);
			qcgi.setDate(tsDate);
			qcgi.setIsFilter(lIsFilter);
			qcgi.setIsIncludeParent(lIsIncludeParent);
			qcgi.setIsIncluedSub(lIsIncluedSub);
			qcgi.setAccountStatusID(lAccountStatusID);
			qcgi.setStartClientID(lStartClientID);
			qcgi.setEndClientID(lEndClientID);
			pagerInfo = qclientgatherbiz.queryClientGather(qcgi);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}

}
