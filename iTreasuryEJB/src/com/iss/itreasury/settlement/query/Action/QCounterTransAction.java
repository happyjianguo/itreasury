package com.iss.itreasury.settlement.query.Action;

import java.sql.Timestamp;
import java.util.Map;

import com.iss.itreasury.settlement.query.bizlogic.QCounterTransBiz;
import com.iss.itreasury.settlement.query.paraminfo.QCounterTransInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.sysframe.pager.dataentity.PagerInfo;

/**
 * ҵ����ͳ��
 * @author songwenxiao
 *
 */
public class QCounterTransAction {
	
	QCounterTransBiz biz = new QCounterTransBiz();
	
	public PagerInfo queryCounterTrans(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{
			QCounterTransInfo paramInfo = new QCounterTransInfo();
			
			paramInfo.convertMapToDataEntity(map);//��Mapת��ΪINFO
			
			pagerInfo = biz.queryCounterTrans(paramInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}

}
