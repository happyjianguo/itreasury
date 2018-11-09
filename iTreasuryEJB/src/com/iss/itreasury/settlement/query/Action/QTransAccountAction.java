package com.iss.itreasury.settlement.query.Action;

import java.util.Map;

import com.iss.itreasury.settlement.query.bizlogic.QTransAccountBiz;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransAccountDetailWhereInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;

/**
 * �˻���ѯ������
 * @author xiang
 *
 */
public class QTransAccountAction {
	
	QTransAccountBiz qTransAccountBiz = new QTransAccountBiz();
	
	public PagerInfo queryAccount(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{
			QueryTransAccountDetailWhereInfo qInfo = new QueryTransAccountDetailWhereInfo();
			qInfo.convertMapToDataEntity(map);//��Mapת��ΪINFO
			pagerInfo = qTransAccountBiz.queryAccount(qInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}

}
