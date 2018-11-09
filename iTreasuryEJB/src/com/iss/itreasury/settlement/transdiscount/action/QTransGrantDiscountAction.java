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

		// �������
		long nOfficeID = -1;
		long nCurrencyID = -1;
		long nInputUserID = -1;
		long QueryTypeID = -1;
		long StatusID = -1;
		long TransactionTypeID = -1;
		// ��ȡ����
		nOfficeID = Long.valueOf((String) map.get("lofficeid")).longValue();
		nCurrencyID = Long.valueOf((String) map.get("lcurrencyid")).longValue();
		nInputUserID = Long.valueOf((String) map.get("linputuserid"))
				.longValue();
		String strTemp = null;
		// ҳ����Ʋ���

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
		// ���û���趨���������,ȱʡ���ս��׺�

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
