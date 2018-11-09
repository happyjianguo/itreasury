package com.iss.itreasury.evoucher.setting.action;

import java.util.Map;

import com.iss.itreasury.clientmanage.client.dataentity.QueryCorporationInfo;
import com.iss.itreasury.evoucher.setting.bizlogic.ClientRightSetBiz_New;
import com.iss.itreasury.evoucher.setting.dataentity.ClientRightEntity;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class ClientRightSetAction {

	ClientRightSetBiz_New clientRightSetBiz = new ClientRightSetBiz_New();
	
	/**
	 * 客户授权设置 action层
	 * add by liaiyi 2012-04-02
	 * @return
	 * @throws Exception
	 */
	public PagerInfo queryClientRightSet(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{
			ClientRightEntity ce = new ClientRightEntity();
			ce.convertMapToDataEntity(map);
			pagerInfo = clientRightSetBiz.queryClientRightSetQuery(ce);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
}
