package com.iss.itreasury.settlement.transloan.action;

import java.util.Map;

import com.iss.itreasury.settlement.transloan.bizlogic.QTransGrantLoanBiz;
import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Env;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class QTransGrantLoanAction {

	QTransGrantLoanBiz qtransgrantloanbiz = new QTransGrantLoanBiz();
	
	
	public PagerInfo queryAccount(Map map) throws Exception{
		PagerInfo pagerInfo = null;

	        //定义变量
			long lInputUserID = -1;
			long lCheckUserID = -1;
			long lStatusID = -1;
			long[] lStatusIDs = null;
			long lTransactionTypeID = -1;

			String strTemp = null;
			strTemp = (String)map.get("lstatusid");
			if(strTemp != null && strTemp.trim().length() > 0)
			{
				lStatusID = Long.valueOf(strTemp).longValue();
			}
			else
			{
				lStatusID = SETTConstant.TransactionStatus.SAVE;
			}
			strTemp = (String)map.get("ltransactiontypeid");
			if(strTemp != null && strTemp.trim().length() > 0)
			{
				lTransactionTypeID = Long.valueOf(strTemp).longValue();
			}
			
			strTemp = (String)map.get("linputuserid");
			if(strTemp != null && strTemp.length()>0)
			{
				lInputUserID = Long.valueOf(strTemp).longValue();
			}
			
			strTemp = (String)map.get("lcheckuserid");
			if(strTemp != null && strTemp.length()>0)
			{
				lCheckUserID = Long.valueOf(strTemp).longValue();
			}
			
	        

			//"全部"选项
			if(lStatusID == 0)
			{
				lStatusIDs = new long[2];
				lStatusIDs[0] = SETTConstant.TransactionStatus.TEMPSAVE;
				lStatusIDs[1] = SETTConstant.TransactionStatus.SAVE;
			}
			else
			{
				lStatusIDs = new long[1];
				lStatusIDs[0] = lStatusID;
			}
			
			TransGrantLoanInfo grantloaninfo = new TransGrantLoanInfo();        
	        
			grantloaninfo.setInputUserID(lInputUserID);
			grantloaninfo.setCheckUserID(lCheckUserID);
			grantloaninfo.setStatusID(lStatusIDs);
			grantloaninfo.setTransactionTypeID(lTransactionTypeID);
			grantloaninfo.setOfficeID(Long.valueOf((String)map.get("lofficeid")).longValue());
			grantloaninfo.setCurrencyID(Long.valueOf((String)map.get("lcurrencyid")).longValue());
			grantloaninfo.setExecute(Env.getSystemDate(Long.valueOf((String)map.get("lofficeid")).longValue(),Long.valueOf((String)map.get("lcurrencyid")).longValue()));
			pagerInfo = qtransgrantloanbiz.queryTransGrantLoan(grantloaninfo);
			return pagerInfo;
	}
}
