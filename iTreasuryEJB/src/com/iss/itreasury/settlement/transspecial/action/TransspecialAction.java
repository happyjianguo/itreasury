package com.iss.itreasury.settlement.transspecial.action;

import java.util.Map;

import com.iss.itreasury.settlement.transspecial.bizlogic.TransspecialBiz;
import com.iss.itreasury.settlement.transspecial.dataentity.QueryBySubSpecialTypeAndStatusInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.sysframe.pager.dataentity.PagerInfo;

/**
 * ����ҵ���� ���Ӳ��ҷ���
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
			qInfo.convertMapToDataEntity(map);//��Mapת��ΪINFO
			long lNstatusids = Long.parseLong(map.get("lNstatusids".toLowerCase()).toString());
			
			long[] StatusIDs;
			if (lNstatusids == -1 || lNstatusids == 0) //"ȫ��"ѡ��
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
