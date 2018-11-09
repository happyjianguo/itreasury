package com.iss.itreasury.settlement.query.Action;

import java.util.Map;

import com.iss.itreasury.settlement.query.bizlogic.QLoanNoticeBookBiz;
import com.iss.itreasury.settlement.query.paraminfo.QueryLoanNoticeInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;

/**
 * 贷款通知书查询数据层
 * @author songwenxiao
 *
 */

public class QLoanNoticeBookAction {
	
	QLoanNoticeBookBiz biz = new QLoanNoticeBookBiz();
	
	public PagerInfo queryLoanNoticeBook(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{

	       //定义变量
			long lNoticeTypeID=-1;   
			long lAccountIDFrom = -1;
			long lAccountIDTo =	-1;
			String strAccountNoFrom = "";
			String strAccountNoTo = "";
			String strClientNameFrom = "";
			String strClientNameTo = "";
			String strClientNoFrom = "";
			String strClientNoTo = "";
			String strContractNoFrom = "";
			String strContractNoTo = "";
			String strPayFormNoFrom = "";
			String strPayFormNoTo = "";	
			
			String strFormYearFrom ="";
			String strFormYearTo ="";
			String strFormNoFrom ="";
			String strFormNoTo ="";
			long lFormNumFrom = -1;
			long lFormNumTo = -1;
			long lCurrencyID = -1;
			long lOfficeID = -1;

			//读取数据
			String strTemp = null;
			strTemp = (String)map.get("lNoticeTypeID".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    lNoticeTypeID = Long.valueOf(strTemp).longValue();
			}	
			strTemp = (String)map.get("lAccountIDFrom".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    lAccountIDFrom = Long.valueOf(strTemp).longValue();
			}	
			strTemp = (String)map.get("lAccountIDTo".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    lAccountIDTo = Long.valueOf(strTemp).longValue();
			}	
			strTemp = (String)map.get("lAccountIDFromCtrl".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    strAccountNoFrom = strTemp;
			}
			strTemp = (String)map.get("lAccountIDToCtrl".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    strAccountNoTo = strTemp;
			}
			strTemp = (String)map.get("strClientNameFrom".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    strClientNameFrom = strTemp;
			}
			strTemp = (String)map.get("strClientNameTo".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    strClientNameTo = strTemp;
			}
			strTemp = (String)map.get("lClientIDFromCtrl".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    strClientNoFrom = strTemp;
			}
			strTemp = (String)map.get("lClientIDToCtrl".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    strClientNoTo = strTemp;
			}
			strTemp = (String)map.get("lContractIDFromCtrl".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    strContractNoFrom = strTemp;
			}
			strTemp = (String)map.get("lContractIDToCtrl".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    strContractNoTo = strTemp;
			}
			strTemp = (String)map.get("lPayFormIDFromCtrl".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    strPayFormNoFrom = strTemp;
			}
			strTemp = (String)map.get("lPayFormIDToCtrl".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    strPayFormNoTo = strTemp;
			}
			strTemp = (String)map.get("strFormYearFrom".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    strFormYearFrom = strTemp;
			}
			strTemp = (String)map.get("strFormYearTo".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    strFormYearTo = strTemp;
			}
			strTemp = (String)map.get("strFormNoFrom".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    strFormNoFrom = strTemp;
			}
			strTemp = (String)map.get("strFormNoTo".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    strFormNoTo = strTemp;
			}
			strTemp = (String)map.get("lFormNumFrom".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    lFormNumFrom = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("lFormNumTo".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    lFormNumTo = Long.valueOf(strTemp).longValue();
			}			
			strTemp = (String)map.get("lCurrencyID".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    lCurrencyID = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("lOfficeID".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    lOfficeID = Long.valueOf(strTemp).longValue();
			}
			
	        QueryLoanNoticeInfo info = null;
			info = new QueryLoanNoticeInfo();
			if (lAccountIDFrom > 0 )
			  info.setAccountNoFrom(strAccountNoFrom);
			if (lAccountIDTo > 0)
				info.setAccountNoTo(strAccountNoTo);
			info.setClientNameFrom(strClientNameFrom);
			info.setClientNameTo(strClientNameTo);
			info.setClientNoFrom(strClientNoFrom);
			info.setClientNoTo(strClientNoTo);
			info.setContractNoFrom(strContractNoFrom);
			info.setContractNoTo(strContractNoTo);			
			info.setFormNumFrom(lFormNumFrom);
			info.setFormNumTo(lFormNumTo);
			info.setNoticeNoFrom(strFormNoFrom);
			info.setNoticeNoTo(strFormNoTo);
			info.setNoticeTypeID(lNoticeTypeID);			
			info.setPayFormNoFrom(strPayFormNoFrom);
			info.setPayFormNoTo(strPayFormNoTo);
			info.setFormYearFrom(strFormYearFrom);
			info.setFormYearTo(strFormYearTo);
			info.setOfficeID(lOfficeID);
			info.setCurrencyID(lCurrencyID);
			
			pagerInfo = biz.queryCounterTrans(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}

}
