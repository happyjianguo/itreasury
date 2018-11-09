package com.iss.itreasury.settlement.transdiscount.action;

import java.util.Map;

import com.iss.itreasury.settlement.transdiscount.bizlogic.QTransGrantDiscountBiz;
import com.iss.itreasury.settlement.transdiscount.bizlogic.QTransRepaymentDiscountBiz;
import com.iss.itreasury.settlement.transdiscount.dataentity.QueryConditionInfo;
import com.iss.itreasury.util.Env;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class QTransRepaymentDiscountAction {

	QTransRepaymentDiscountBiz qtransrepaymentdiscountbiz = new QTransRepaymentDiscountBiz();

	public PagerInfo queryAccount(Map map) throws Exception {
		PagerInfo pagerInfo = null;
        //定义变量	
		long nOfficeID=-1;   
		long nCurrencyID=-1; 
		long nInputUserID=-1;     
		long QueryTypeID=-1;    
		long lNStatusID=-1;   
		long lNTransactionTypeID=-1;  

		//读取数据
		nOfficeID=Long.valueOf((String)map.get("lofficeid")).longValue();
        nCurrencyID=Long.valueOf((String)map.get("lcurrencyid")).longValue();
        nInputUserID=Long.valueOf((String)map.get("linputuserid")).longValue();		
		String strTemp = null;	
		strTemp = (String)map.get("querytypeid");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    QueryTypeID = Long.valueOf(strTemp).longValue();
		}

		strTemp = (String)map.get("lnstatusid");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lNStatusID = Long.valueOf(strTemp).longValue();
		}

		
		strTemp = (String)map.get("transactiontypeid");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lNTransactionTypeID = Long.valueOf(strTemp).longValue();
		}

        QueryConditionInfo queryInfo = new QueryConditionInfo();        
        
		queryInfo.setCurrencyID(nCurrencyID);   
		queryInfo.setOfficeID(nOfficeID);
		queryInfo.setStatusID(lNStatusID);
		queryInfo.setTypeID(QueryTypeID);
		queryInfo.setUserID(nInputUserID);
		queryInfo.setTransactionTypeID(lNTransactionTypeID);
		queryInfo.setDate(Env.getSystemDate(nOfficeID, nCurrencyID));		

		pagerInfo = qtransrepaymentdiscountbiz.queryTransRepaymentDiscount(queryInfo);
		return pagerInfo;
	}
}
