package com.iss.itreasury.settlement.transdiscount.action;

import java.util.Map;

import com.iss.itreasury.settlement.transdiscount.bizlogic.QTransGrantDiscountBiz;
import com.iss.itreasury.settlement.transdiscount.dataentity.QueryConditionInfo;
import com.iss.itreasury.util.Env;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class QTransGrantDiscountAction {

	QTransGrantDiscountBiz qtransgrantdiscountbiz = new QTransGrantDiscountBiz();

	public PagerInfo queryAccount(Map map) throws Exception {
		PagerInfo pagerInfo = null;

		// 定义变量
		long nOfficeID = -1;
		long nCurrencyID = -1;
		long nInputUserID = -1;
		long QueryTypeID = -1;
		long StatusID = -1;
		long TransactionTypeID = -1;
		// 读取数据
		nOfficeID = Long.valueOf((String) map.get("lofficeid")).longValue();
		nCurrencyID = Long.valueOf((String) map.get("lcurrencyid")).longValue();
		nInputUserID = Long.valueOf((String) map.get("linputuserid"))
				.longValue();
		String strTemp = null;
		// 页面控制参数

		strTemp = (String) map.get("querytypeid");
		if (strTemp != null && strTemp.trim().length() > 0) {
			QueryTypeID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String) map.get("statusid");
		if (strTemp != null && strTemp.trim().length() > 0) {
			StatusID = Long.valueOf(strTemp).longValue();
		}

		strTemp = (String) map.get("transactiontypeid");
		if (strTemp != null && strTemp.trim().length() > 0) {
			TransactionTypeID = Long.valueOf(strTemp).longValue();
		}
		// 如果没有设定排序的条件,缺省按照交易号

		QueryConditionInfo queryInfo = new QueryConditionInfo();

		queryInfo.setCurrencyID(nCurrencyID);
		queryInfo.setOfficeID(nOfficeID);
		queryInfo.setStatusID(StatusID);
		queryInfo.setTypeID(QueryTypeID);
		queryInfo.setUserID(nInputUserID);
		queryInfo.setTransactionTypeID(TransactionTypeID);
		queryInfo.setDate(Env.getSystemDate(nOfficeID, nCurrencyID));
		queryInfo.setPayForm( new Boolean(map.get("ispayform")+"").booleanValue());

		pagerInfo = qtransgrantdiscountbiz.queryTransGrantDiscount(queryInfo);
		return pagerInfo;
	}
	
	public PagerInfo findDiscountBillByContractIdAndCredenceId(Map<String,String> map) throws Exception
	{
		PagerInfo pagerInfo = null;

		pagerInfo = qtransgrantdiscountbiz.queryDiscountBill(map);
		return pagerInfo;
	}	
}
