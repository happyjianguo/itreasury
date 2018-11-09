package com.iss.itreasury.settlement.transfixeddeposit.action;

import java.util.Map;

import com.iss.itreasury.settlement.transfixeddeposit.bizlogic.TransFixedDepositBiz;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import java.sql.Timestamp;

public class TransFixedDepositAction {
	TransFixedDepositBiz fixedContinueBiz = new TransFixedDepositBiz();
	/*
	 * 定期开立--链接查询
	 */
	public PagerInfo findByConditions(Map map) throws Exception{
		
		PagerInfo pagerInfo = null;
		try 
		{
			QueryByStatusConditionInfo depositInfo = new QueryByStatusConditionInfo();
			depositInfo.convertMapToDataEntity(map);//将Map转化为INFO
			long lStatusID = Long.parseLong(map.get("statusIDs".toLowerCase()).toString());
			long inputUserID = Long.parseLong(map.get("inputUserID".toLowerCase()).toString());
			depositInfo.setUserID(inputUserID);
			long lTypeID = Long.parseLong(map.get("TypeID".toLowerCase()).toString());
			depositInfo.setTypeID(lTypeID);
			Timestamp executeDate = Timestamp.valueOf(map.get("executeDate".toLowerCase()).toString());
			depositInfo.setDate(executeDate);
			long[] StatusIDs;
			if (lStatusID == -1 || lStatusID == 0) //"全部"选项
			{
				
				StatusIDs = new long[2];
				StatusIDs[0] = SETTConstant.TransactionStatus.TEMPSAVE;
				StatusIDs[1] = SETTConstant.TransactionStatus.SAVE;
			}
			else
			{
				
				StatusIDs = new long[1];
				StatusIDs[0] = lStatusID;
			}
			depositInfo.setStatus(StatusIDs);
			pagerInfo = fixedContinueBiz.findByConditions(depositInfo);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}

}
