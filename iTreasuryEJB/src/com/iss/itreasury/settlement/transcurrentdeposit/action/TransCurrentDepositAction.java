package com.iss.itreasury.settlement.transcurrentdeposit.action;

import java.util.Map;

import com.iss.itreasury.settlement.transcurrentdeposit.bizlogic.TransCurrentDepositBiz;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.sysframe.pager.dataentity.PagerInfo;

/**
 * 活期查询
 * @author add by xiangzhou 20120704
 *
 */
public class TransCurrentDepositAction {
	
	TransCurrentDepositBiz biz = new TransCurrentDepositBiz();
	/*
	 * 银行收款查询
	 */
	public PagerInfo findByConditions(Map map) throws Exception{
		
		PagerInfo pagerInfo = null;
		try 
		{
			TransCurrentDepositInfo depositInfo = new TransCurrentDepositInfo();
			depositInfo.convertMapToDataEntity(map);//将Map转化为INFO
			
			long[] lStatusIDs = null;
			
			long lStatusID = Long.parseLong(map.get("statusIDs".toLowerCase()).toString());
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
			depositInfo.setStatusIDs(lStatusIDs);
			
			pagerInfo = biz.findByConditions(depositInfo);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}

}
