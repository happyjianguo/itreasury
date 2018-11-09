package com.iss.itreasury.settlement.setting.action;

import java.util.Map;

import com.iss.itreasury.settlement.setting.bizlogic.FixedDepositMonthSetBiz;
import com.iss.itreasury.settlement.setting.dataentity.FixedDepositMonthInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class FixedDepositMonthSetAction {
	private FixedDepositMonthSetBiz biz = new FixedDepositMonthSetBiz();

	public PagerInfo queryFixedDepositMonthSet(Map map) throws Exception {
		long transtype=Long.parseLong(map.get("transtype")+"");
		long lOfficeID=Long.parseLong(map.get("lofficeid")+"");
		long currencyID=Long.parseLong(map.get("lcurrencyid")+"");
		FixedDepositMonthInfo queryInfo = new FixedDepositMonthInfo();
		queryInfo.setOfficeId(lOfficeID);
		queryInfo.setCurrencyId(currencyID);
		queryInfo.setTransType(transtype);
		
		return biz.queryFixedDepositMonthSet(queryInfo);
	}
}
