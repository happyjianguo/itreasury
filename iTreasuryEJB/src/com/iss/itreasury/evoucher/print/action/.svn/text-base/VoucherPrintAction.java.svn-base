package com.iss.itreasury.evoucher.print.action;

import java.util.Map;

import com.iss.itreasury.evoucher.print.bizlogic.VoucherPrintBiz;
import com.iss.itreasury.evoucher.print.dataentity.QueryPrintConditionInfo_New;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class VoucherPrintAction {
	VoucherPrintBiz printBiz = new VoucherPrintBiz();
	
	/**
	 * 电子单据柜 单据打印
	 * @author zk 2013-01-22
	 *
	 */
	public PagerInfo queryVouchersInfo(Map map) throws Exception {
		
		PagerInfo pagerInfo = null;
		try
		{
			QueryPrintConditionInfo_New info = new QueryPrintConditionInfo_New();
			info.convertMapToDataEntity(map);
			pagerInfo = printBiz.queryVouchersInfo(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(),e);
		}
		return pagerInfo;
	}
	
}
