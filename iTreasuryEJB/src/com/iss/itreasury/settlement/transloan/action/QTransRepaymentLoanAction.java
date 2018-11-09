package com.iss.itreasury.settlement.transloan.action;

import java.util.Map;

import com.iss.itreasury.settlement.transloan.bizlogic.QTransRepaymentLoanBiz;
import com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Env;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class QTransRepaymentLoanAction {

	QTransRepaymentLoanBiz qTransRepaymentLoanBiz = new QTransRepaymentLoanBiz();
	
	public PagerInfo queryAccountCheck(Map map) throws Exception {
		return queryAccount(map,true);
	}
	
	public PagerInfo queryAccountInput(Map map) throws Exception {
		return queryAccount(map,false);
	}
	
	public PagerInfo queryAccount(Map map,boolean isCheck) throws Exception {
		PagerInfo pagerInfo = null;
		try {
			long lInputUserID = Long.valueOf((String) map.get("linputuserid"))
					.longValue();
			long lStatusID = -1;
			long[] lStatusIDs = null;
			long lTransactionTypeID = -1;
			TransRepaymentLoanInfo transRepaymentLoanInfo = new TransRepaymentLoanInfo();
			String strTemp = null;
			strTemp = map.get("lstatusid") == null ? "" : (String) map
					.get("lstatusid");
			if (strTemp != null && strTemp.trim().length() > 0) {
				lStatusID = Long.valueOf(strTemp).longValue();
			} else {
				lStatusID = SETTConstant.TransactionStatus.SAVE;
			}
			strTemp = (String) map.get("ltransactiontypeid");
			if (strTemp != null && strTemp.trim().length() > 0) {
				lTransactionTypeID = Long.valueOf(strTemp).longValue();
			}
			if (lStatusID == 0) {
				lStatusIDs = new long[2];
				lStatusIDs[0] = SETTConstant.TransactionStatus.TEMPSAVE;
				lStatusIDs[1] = SETTConstant.TransactionStatus.SAVE;
			} else {
				lStatusIDs = new long[1];
				lStatusIDs[0] = lStatusID;
			}
			transRepaymentLoanInfo.setOfficeID(Long.valueOf(
					(String) map.get("lofficeid")).longValue());
			transRepaymentLoanInfo.setCurrencyID(Long.valueOf(
					(String) map.get("lcurrencyid")).longValue());
			transRepaymentLoanInfo.setExecute(Env.getSystemDate(Long.valueOf(
					(String) map.get("lofficeid")).longValue(), Long.valueOf(
					(String) map.get("lcurrencyid")).longValue()));
			if(isCheck)
				transRepaymentLoanInfo.setCheckUserID(lInputUserID);
			else
				transRepaymentLoanInfo.setInputUserID(lInputUserID);
			transRepaymentLoanInfo.setTransactionTypeID(lTransactionTypeID);
			transRepaymentLoanInfo.setStatusID(lStatusIDs);
			pagerInfo = qTransRepaymentLoanBiz
					.queryAccount(transRepaymentLoanInfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
}
