package com.iss.itreasury.settlement.query.Action;

import java.sql.Timestamp;
import java.util.Map;

import com.iss.itreasury.settlement.query.bizlogic.QLoanTransHistoryDetailBiz;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransactionConditionInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.sysframe.pager.dataentity.PagerInfo;

/**
 * 贷款交易历史明细查询
 * @author songwenxiao
 *
 */

public class QLoanTransHistoryDetailAction {
	
	QLoanTransHistoryDetailBiz biz = new QLoanTransHistoryDetailBiz();
	
	public PagerInfo queryLoanTransDetail(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{

	       //定义变量
			 //定义变量
			long lOfficeID = -1;//办事处
			long lCurrencyID = -1;//币种
			
			String strPayAccountNoStart = "";//付款方账户号（由）
			String strPayAccountNoEnd = "";//付款方账户号（至）
			String strReceiveAccountNoStart = "";//收款方账户号（由）
			String strReceiveAccountNoEnd = "";//收款方账户号（至）
			Timestamp tsExecuteEnd = null;//
			Timestamp tsExecuteStart = null;//
			
			String strDepositNo = "";//存单号
			long lContractID = -1;//合同ID
			String strContractNo = "";//合同号
			long lPayFormID = -1;//放款通知单（贴现凭证）
			
			long lStatusID = -1;//交易纪录状态

			//读取数据
			String strTemp = null;
			strTemp = (String)map.get("tsExecuteEndDate".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				tsExecuteEnd = DataFormat.getDateTime(strTemp);
			}	
			strTemp = (String)map.get("tsExecuteStartDate".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				tsExecuteStart =DataFormat.getDateTime(strTemp);
			}	
			strTemp = (String)map.get("lPayAccountNoEndCtrl".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				strPayAccountNoEnd = strTemp;
			}	
			strTemp = (String)map.get("lPayAccountIDStartCtrl".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				strPayAccountNoStart = strTemp;
			}
			strTemp = (String)map.get("lReceiveAccountIDEndCtrl".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				 strReceiveAccountNoEnd = strTemp;
			}
			strTemp = (String)map.get("lReceiveAccountIDStartCtrl".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				strReceiveAccountNoStart = strTemp;
			}
			strTemp = (String)map.get("lTransactionStatusID".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lStatusID = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("lContractID".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lContractID = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("lPayFormID".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				 lPayFormID = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("strDepositNo".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				strDepositNo = strTemp;
			}
			strTemp = (String)map.get("strContractNo".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				strContractNo = strTemp;
			}
					
			strTemp = (String)map.get("lCurrencyID".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    lCurrencyID = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("lOfficeID".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    lOfficeID = Long.valueOf(strTemp).longValue();
			}
			
			QueryTransactionConditionInfo conditionInfo = null;
			conditionInfo = new QueryTransactionConditionInfo();
			//
			conditionInfo.setCurrencyID(lCurrencyID);
			conditionInfo.setExecuteEnd(tsExecuteEnd);
			conditionInfo.setExecuteStart(tsExecuteStart);
			conditionInfo.setOfficeID(lOfficeID);
			conditionInfo.setPayAccountNoEnd(strPayAccountNoEnd);
			conditionInfo.setPayAccountNoStart(strPayAccountNoStart);
			conditionInfo.setReceiveAccountNoEnd(strReceiveAccountNoEnd);
			conditionInfo.setReceiveAccountNoStart(strReceiveAccountNoStart);
			conditionInfo.setDepositNo(strDepositNo);
			conditionInfo.setContractID(lContractID);
			conditionInfo.setPayFormID(lPayFormID);
			conditionInfo.setStatusID(lStatusID);
			
			pagerInfo = biz.queryLoanTrans(conditionInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}

}
