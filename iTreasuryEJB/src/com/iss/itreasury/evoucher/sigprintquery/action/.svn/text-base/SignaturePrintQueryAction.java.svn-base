package com.iss.itreasury.evoucher.sigprintquery.action;

import java.util.Map;

import com.iss.itreasury.clientmanage.client.dataentity.QueryCorporationInfo;
import com.iss.itreasury.evoucher.sigprintquery.bizlogic.SignaturePrintQueryBiz;
import com.iss.itreasury.evoucher.sigprintquery.dataentity.SigPrintEntity;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class SignaturePrintQueryAction {

	SignaturePrintQueryBiz sigPrintBiz = new SignaturePrintQueryBiz();
	
	/**
	 * 电子签章打印查询 action层
	 * add by liaiyi 2012-04-02
	 * @return
	 * @throws Exception
	 */
	public PagerInfo querySignaturePrintQuery(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{
			SigPrintEntity spe = new SigPrintEntity();
			spe.convertMapToDataEntity(map);
			pagerInfo = sigPrintBiz.querySignaturePrintQuery(spe);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
}
