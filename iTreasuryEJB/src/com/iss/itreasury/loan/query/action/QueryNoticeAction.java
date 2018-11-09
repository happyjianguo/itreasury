package com.iss.itreasury.loan.query.action;

import java.util.Map;

import com.iss.itreasury.loan.query.bizlogic.QueryNoticeBiz;
import com.iss.itreasury.loan.query.dataentity.QueryNoticeInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class QueryNoticeAction {
	
	QueryNoticeBiz noticeBiz = new QueryNoticeBiz();
	
	/**
	 * 放款通知单查询
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zk 2012-01-09
	 */
	public PagerInfo queryPayNoticeInfo(Map map) throws Exception {
		
		PagerInfo pagerInfo = null;
		try {
			QueryNoticeInfo nInfo = new QueryNoticeInfo();
			nInfo.convertMapToDataEntity(map);
			
			pagerInfo = noticeBiz.queryPayNoticeInfo(nInfo);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	/**
	 * 还款通知单查询
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zk 2012-01-11
	 */
	public PagerInfo queryRePayNoticeInfo(Map map) throws Exception {
		
		PagerInfo pagerInfo = null;
		try {
			QueryNoticeInfo nInfo = new QueryNoticeInfo();
			nInfo.convertMapToDataEntity(map);
			
			pagerInfo = noticeBiz.queryRePayNoticeInfo(nInfo);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}

}
