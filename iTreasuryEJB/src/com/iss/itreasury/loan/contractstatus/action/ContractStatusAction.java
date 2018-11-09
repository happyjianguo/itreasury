package com.iss.itreasury.loan.contractstatus.action;

import java.sql.Timestamp;
import java.util.Map;

import com.iss.itreasury.clientmanage.client.dataentity.QueryCorporationInfo;
import com.iss.itreasury.loan.contractstatus.bizlogic.ContractStatusBiz;
import com.iss.itreasury.loan.contractstatus.dataentity.ContractStatusInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.sysframe.pager.dataentity.PagerInfo;


public class ContractStatusAction {

	ContractStatusBiz contractStatusBiz = new ContractStatusBiz();
	/**
	 * 贷款合同状态变更action层
	 * @author issuser
	 *
	 */
	public PagerInfo queryContractStatusInfo(Map map) throws Exception
 	{
 		PagerInfo pagerInfo = null;
 		long lOfficeID = -1;
 		long lCurrencyID = -1;
		long lUserID = -1;
		long lActionID = -1;
		long lStatusID = -1;
		Timestamp tsDateFrom = null;
        Timestamp tsDateTo = null;
		long m_lStatusID = -1;
 		try
 		{
 			ContractStatusInfo contractStatusInfo = new ContractStatusInfo();
 			if(map.get("lofficeid") !=null && !map.get("lofficeid").equals("")){
 				lOfficeID = Long.parseLong((String)map.get("lofficeid"));
 			}
 			if(map.get("lcurrencyid") !=null && !map.get("lcurrencyid").equals("")){
 				lCurrencyID = Long.parseLong((String)map.get("lcurrencyid"));
 			}
 			if(map.get("txtlloanstatusid") !=null && !map.get("txtlloanstatusid").equals("")){
 				lStatusID = Long.parseLong((String)map.get("txtlloanstatusid"));
 			}
 			if(map.get("txtdtinputdate1") !=null && !map.get("txtdtinputdate1").equals("")){
 				tsDateFrom =  DataFormat.getDateTime((String)map.get("txtdtinputdate1"));
 			}
 			if(map.get("txtdtinputdate2") !=null && !map.get("txtdtinputdate2").equals("")){
 				tsDateTo =  DataFormat.getDateTime((String)map.get("txtdtinputdate2"));
 			}
 			contractStatusInfo.convertMapToDataEntity(map);
 			pagerInfo = contractStatusBiz.queryContractStatus(lCurrencyID,lOfficeID,lUserID,lActionID,tsDateFrom,tsDateTo,lStatusID);
 		}
 		catch(Exception e)
 		{
 			e.printStackTrace();
 			throw new Exception(e.getMessage(), e);
 		}
 		return pagerInfo;
 	}
}
