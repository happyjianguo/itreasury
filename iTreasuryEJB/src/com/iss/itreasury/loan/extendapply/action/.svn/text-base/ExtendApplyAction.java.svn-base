package com.iss.itreasury.loan.extendapply.action;

import java.util.Map;

import com.iss.itreasury.loan.extendapply.bizlogic.ExtendApplyBiz;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class ExtendApplyAction {
	
	ExtendApplyBiz extendApplyBiz = new ExtendApplyBiz();

	/**
	 * 合同查找，根据条件查询loan_ContractForm和loan_LoanForm表。
	 * lContractIDFrom和lContractIDTo，同一类型的合同的流水号的部分作为查询范围
	 * @param lInputUserID 登录人标识
	 * @param lCurrencyID 币种标识
	 * @param lOfficeID 办事处标识
	 * @param lTypeID 贷款申请类型标识
	 * @param lContractIDFrom 合同编号起始
	 * @param lContractIDTo 合同编号结束
	 * @param lClientID 借款单位标识
	 * @return PagerInfo
	 * @exception Exception
	 * @author zk 2012-12-31
	 * 
	 */
	public PagerInfo queryContractByMultiOption(Map map) throws Exception {
		
		PagerInfo pagerInfo = null;
		long lInputUserID = -1;
		long lCurrencyID = -1;
		long lOfficeID = -1;
		long lTypeID = -1;
		long lContractIDFrom = -1;
		long lContractIDTo = -1;
		long lClientID = -1;
		try
		{
			if(map !=null && map.get("inputuserid") != null){
				lInputUserID = Long.parseLong((String)map.get("inputuserid"));
			}
			if(map !=null && map.get("currencyid") != null){
				lCurrencyID = Long.parseLong((String)map.get("currencyid"));
			}
			if(map !=null && map.get("officeid") != null){
				lOfficeID = Long.parseLong((String)map.get("officeid"));
			}
			if(map !=null && map.get("txtlloantypeid") != null){
				lTypeID = Long.parseLong((String)map.get("txtlloantypeid"));
			}
			if(map !=null && map.get("txtidstart") != null && !"".equals(map.get("txtidstart"))){
				lContractIDFrom = Long.parseLong((String)map.get("txtidstart"));
			}
			if(map !=null && map.get("txtidend") != null && !"".equals(map.get("txtidend"))){
				lContractIDTo = Long.parseLong((String)map.get("txtidend"));
			}
			if(map !=null && map.get("txtsborrowclientname") != null && !"".equals(map.get("txtsborrowclientname"))){
				lClientID = Long.parseLong((String)map.get("txtsborrowclientname"));
			}
			pagerInfo = extendApplyBiz.queryContractByMultiOption(lInputUserID,lCurrencyID,lOfficeID,lTypeID,lContractIDFrom,lContractIDTo,lClientID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(),e);
		}
		return pagerInfo;
	}
	/**
	 * 查找合同的最新版本还款计划
	 * @param lContractID 合同标识
	 * @return PagerInfo
	 * @exception Exception
	 * @author zk 2012-12-31
	 * 
	 */
	public PagerInfo queryPlanByContract(Map map) throws Exception {
		
		PagerInfo pagerInfo = null;
		long lContractID = -1;
		String lateRateString = null;
		try
		{
			if(map !=null && map.get("contractid") != null){
				lContractID = Long.parseLong((String)map.get("contractid"));
			}
			if(map !=null && map.get("lateratestring") != null){
				lateRateString = (String)map.get("lateratestring");
			}
			pagerInfo = extendApplyBiz.queryPlanByContract(lContractID,lateRateString);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(),e);
		}
		return pagerInfo;
	}

}
