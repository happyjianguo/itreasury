package com.iss.itreasury.settlement.transspecial.action;

import java.util.Map;

import com.iss.itreasury.settlement.transspecial.bizlogic.TransspecialBiz;
import com.iss.itreasury.settlement.transspecial.dataentity.QueryBySubSpecialTypeAndStatusInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.sysframe.pager.dataentity.PagerInfo;

/**
 * 特殊业务处理 链接查找方法
 * @author liangli5
 * 
 */
public class TransspecialAction {
	
	TransspecialBiz transspecialBiz = new TransspecialBiz();

	public PagerInfo queryTransspecial(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{
			QueryBySubSpecialTypeAndStatusInfo qInfo = new QueryBySubSpecialTypeAndStatusInfo();
			qInfo.convertMapToDataEntity(map);//将Map转化为INFO
			long lNstatusids = Long.parseLong(map.get("lNstatusids".toLowerCase()).toString());
			
			long[] StatusIDs;
			if (lNstatusids == -1 || lNstatusids == 0) //"全部"选项
			{
				StatusIDs = new long[2];
				StatusIDs[0] = SETTConstant.TransactionStatus.TEMPSAVE;
				StatusIDs[1] = SETTConstant.TransactionStatus.SAVE;
			}
			else
			{
				StatusIDs = new long[1];
				StatusIDs[0] = lNstatusids;
			}
			
			qInfo.setStatusIDs(StatusIDs);
			
			pagerInfo = transspecialBiz.queryTransspecial(qInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	
		
	}
	
	
}
