package com.iss.itreasury.loan.repayplan.action;

import java.text.DateFormat;
import java.util.Map;


import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.repayplan.bizlogic.ContractPlanBiz;
import com.iss.itreasury.loan.repayplan.dataentity.PlanVersionInfo;
import com.iss.itreasury.loan.repayplan.dataentity.QueryContractInfo;
import com.iss.itreasury.loan.repayplan.dataentity.RepayPlanInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class ContractPlanAction {

	/**合同执行计划更改action层
	 * add by liaiyi 2012-12-18
	 * 
	 */
	ContractPlanBiz contractPlanBiz = new ContractPlanBiz();

		public PagerInfo queryContractPlan(Map map) throws Exception
		{
			PagerInfo pagerInfo = null;
			long LoanTypeID = -1;
			long ContractIDFrom = -1;
			long ContractIDTo = -1;
			long ClientID = -1;
			long Period = -1;
			long StatusID = -1;
			double dAmountFrom = 0.0;
			double dAmountTo = 0.0;
			try
			{
				QueryContractInfo contractInfo = new QueryContractInfo();
				if(map != null && map.get("lloantype") != null && !(map.get("lloantype")).equals("")){
					LoanTypeID = Long.parseLong((String) map.get("lloantype"));
					contractInfo.setLoanTypeID(LoanTypeID);
				}
				if(map != null && map.get("txtidstart") != null && !(map.get("txtidstart")).equals("")){
					ContractIDFrom = Long.parseLong((String) map.get("txtidstart"));
					contractInfo.setContractIDFrom(ContractIDFrom);
				}
				if(map != null && map.get("txtidend") != null && !(map.get("txtidend")).equals("")){
					ContractIDTo = Long.parseLong((String) map.get("txtidend"));
					contractInfo.setContractIDTo(ContractIDTo);
				}
				if(map != null && map.get("txtsborrowclientname") != null && !(map.get("txtsborrowclientname")).equals("")){
					ClientID = Long.parseLong((String) map.get("txtsborrowclientname"));
					contractInfo.setClientID(ClientID);
				}
				if(map != null && map.get("txtlloanintervalnum") != null && !(map.get("txtlloanintervalnum")).equals("")){
					Period =Long.parseLong((String) map.get("txtlloanintervalnum"));
					contractInfo.setPeriod(Period);
				}
				if(map != null && map.get("damount1") != null && !(map.get("damount1")).equals("")){
					dAmountFrom = DataFormat.parseNumber((String) map.get("damount1"));
					contractInfo.setAmountFrom(dAmountFrom);
				}
				if(map != null && map.get("damount2") != null && !(map.get("damount2")).equals("")){
					dAmountTo = DataFormat.parseNumber((String) map.get("damount2"));
					contractInfo.setAmountTo(dAmountTo);
				}
				if(map != null && map.get("txtlloanstatusid") != null && !(map.get("txtlloanstatusid")).equals("")){
					StatusID = Long.parseLong((String) map.get("txtlloanstatusid"));
					contractInfo.setStatusID(StatusID);
				}
				contractInfo.convertMapToDataEntity(map);//将Map转化为INFO
				pagerInfo = contractPlanBiz.queryContractInfo(contractInfo);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				throw new Exception(e.getMessage(), e);
			}
			return pagerInfo;
		}
	/**合同执行计划更改执行计划版本列表action层
	 * add by liaiyi 2012-12-18
	 * 
	 */	
		public PagerInfo queryContractModify(Map map) throws Exception
		{
			PagerInfo pagerInfo = null;
			long lContractID = -1;
			long nStatusID = -1;
			try
			{
				PlanVersionInfo planVersionInfo = new PlanVersionInfo();
				if(map != null && map.get("nrcontractid") != null && !map.equals("")){
					lContractID = Long.parseLong((String) map.get("nrcontractid"));
					planVersionInfo.setContractID(lContractID);
				}
				if(map != null && map.get("nstatusid") != null && !map.equals("")){
					nStatusID = Long.parseLong((String)map.get("nstatusid"));
					planVersionInfo.setStatusID(nStatusID);
				}
				pagerInfo = contractPlanBiz.queryContractModifyInfo(lContractID);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				throw new Exception(e.getMessage(), e);
			}
			return pagerInfo;
		}
		
		public PagerInfo queryContractModifyInfo(Map map) throws Exception
		{
			PagerInfo pagerInfo = null;
			long contractPayPlanVersionID = -1;
			long lVersionID = -1;
			long lVersionID_r = -1;
			try
			{
				RepayPlanInfo repayPlanInfo = new RepayPlanInfo();
				if(map != null && map.get("contractpayplanversionid") != null){
					contractPayPlanVersionID = Long.parseLong((String) map.get("contractpayplanversionid"));
					repayPlanInfo.setLContractPayPlanVersionID(contractPayPlanVersionID);
				}
				if(map != null && map.get("nversionid") != null){
					lVersionID = Long.parseLong((String) map.get("nversionid"));
				}
				if(map != null && map.get("nversionid_r") != null){
					lVersionID_r = Long.parseLong((String) map.get("nversionid_r"));
				}
				pagerInfo = contractPlanBiz.queryContractModify(contractPayPlanVersionID,lVersionID,lVersionID_r);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				throw new Exception(e.getMessage(), e);
			}
			return pagerInfo;
		}
		
		public PagerInfo queryContractPlanInfo(Map map) throws Exception
		{
			PagerInfo pagerInfo = null;
			long contractPayPlanVersionID = -1;
			
			try
			{
				RepayPlanInfo repayPlanInfo = new RepayPlanInfo();
				if(map != null && map.get("contractpayplanversionid") != null){
					contractPayPlanVersionID = Long.parseLong((String) map.get("contractpayplanversionid"));
					repayPlanInfo.setLContractPayPlanVersionID(contractPayPlanVersionID);
				}
				
				pagerInfo = contractPlanBiz.queryContractPlan(contractPayPlanVersionID);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				throw new Exception(e.getMessage(), e);
			}
			return pagerInfo;
		}
}
