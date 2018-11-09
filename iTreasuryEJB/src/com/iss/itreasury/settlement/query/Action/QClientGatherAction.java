package com.iss.itreasury.settlement.query.Action;

import java.sql.Timestamp;
import java.util.Map;

import com.iss.itreasury.settlement.query.bizlogic.QClientGatherBiz;
import com.iss.itreasury.settlement.query.paraminfo.QueryClientGatherWhereInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.sysframe.pager.dataentity.PagerInfo;

/**
 * �˻���ѯ������
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
			 //�������
			String strStartClientCode = ""; // ��ѯ�ɿͻ����
			String strEndClientCode = ""; // ��ѯ���ͻ����
			String strAccountType = "0";//�˻�����
			long lAccountStatusID = -1;//�˻�״̬
			long lIsIncludeParent = -1;//����ĸ��˾
			long lIsIncluedSub  = -1;//�����ӹ�˾
			long lIsFilter  = -1;//�Ƿ��˿�
			long lStartClientID = -1;
			long lEndClientID = -1;
			Timestamp tsDate = null;//��������
			//��ȡ����
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
