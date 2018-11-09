package com.iss.itreasury.ebank.system.action;

import java.util.HashMap;
import java.util.Map;

import com.iss.itreasury.ebank.system.dao.BillPrintBiz;
import com.iss.itreasury.ebank.system.dataentity.AcctTransParam;
import com.iss.itreasury.ebank.system.dataentity.QueryBillPrintParam;
import com.iss.itreasury.ebank.util.NameRef;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class BillPrintAction {
	
	BillPrintBiz billPrintBiz = new BillPrintBiz();
	
	/**
	 * 账户对账单查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PagerInfo queryBillPrintInfo(Map map) throws Exception{
		PagerInfo pagerInfo = null;
		
		try
		{
			QueryBillPrintParam paramInfo    = new QueryBillPrintParam();
			
			String transactionStartDateString = "";
			String transactionEndDateString = "";
			long lOfficeID = -1;
			long clientId = -1;
			long lCurrencyID = -1;
			String bankType = "";
			long accountId = -1;
			long lUserID = -1;
			
			if(map.get("transactionstartdatestring")!=null && map.get("transactionstartdatestring").toString().trim().length()>0){
				transactionStartDateString = map.get("transactionstartdatestring").toString().trim();
				paramInfo.setTransactionStartDateString(transactionStartDateString);
			}
			if(map.get("transactionenddatestring")!=null && map.get("transactionenddatestring").toString().trim().length()>0){
				transactionEndDateString = map.get("transactionenddatestring").toString().trim();
				paramInfo.setTransactionEndDateString(transactionEndDateString);
			}
			if(map.get("lofficeid")!=null && map.get("lofficeid").toString().trim().length()>0){
				lOfficeID = Long.parseLong(map.get("lofficeid").toString().trim());
				paramInfo.setOfficeID(lOfficeID);
			}
			if(map.get("clientid").toString()!=null && map.get("clientid").toString().trim().length()>0)
			{
				clientId = Long.parseLong(map.get("clientid").toString().trim());
				paramInfo.setClientIdFrom(clientId);
				paramInfo.setClientIdTo(clientId);
			}
			if(map.get("lcurrencyid")!=null && map.get("lcurrencyid").toString().trim().length()>0){
				lCurrencyID = Long.parseLong(map.get("lcurrencyid").toString().trim());
				paramInfo.setCurrencyType(lCurrencyID);
			}
			if(map.get("banktype")!=null && map.get("banktype").toString().trim().length()>0){
				bankType = map.get("banktype").toString().trim();
				paramInfo.setBankType(bankType);
				//if(null != paramInfo.getBankType() && paramInfo.getBankType().length()>0){
					if("null".equals(paramInfo.getBankType())){
						paramInfo.setAllbankId("all");
					}else{
					   String bankid=NameRef.getBankIdByRefCode(paramInfo.getBankType());
						paramInfo.setAllbankId(bankid);
					}
				//}	
			}
			if(map.get("accountid")!=null && map.get("accountid").toString().trim().length()>0){
				accountId = Long.parseLong(map.get("accountid").toString().trim());
				paramInfo.setAccountId(accountId);
			}
			if(map.get("luserid")!=null && map.get("luserid").toString().trim().length()>0){
				lUserID = Long.parseLong(map.get("luserid").toString().trim());
			}
		    Map paramMap = new HashMap();
		    paramMap.put("info", paramInfo);
		    paramMap.put("lUserID", lUserID);
		    
			pagerInfo = billPrintBiz.queryBillPrintInfo(paramMap);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
		
	}
	
	/**
	 * 账户对账单明细查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PagerInfo queryBillPrintDetailInfo(Map map) throws Exception{
		PagerInfo pagerInfo = null;
		
		try
		{
			QueryBillPrintParam param = new QueryBillPrintParam();
			AcctTransParam transParam = new AcctTransParam();
			
			String transactionStartDateString = "";
			String transactionEndDateString = "";
			long accountId = -1;
			String bankType = "";
			long lOfficeID = -1;
			long lCurrencyID = -1;
			long lClientID = -1;
			
			
			if(map.get("transactionstartdatestring")!=null && map.get("transactionstartdatestring").toString().trim().length()>0){
				transactionStartDateString = map.get("transactionstartdatestring").toString().trim();
				param.setTransactionStartDateString(transactionStartDateString);
				transParam.setTransactionStartDateString(transactionStartDateString);
			}
			if(map.get("transactionenddatestring")!=null && map.get("transactionenddatestring").toString().trim().length()>0){
				transactionEndDateString = map.get("transactionenddatestring").toString().trim();
				param.setTransactionEndDateString(transactionEndDateString);
				transParam.setTransactionEndDateString(transactionEndDateString);
			}
			if(map.get("accountid")!=null && map.get("accountid").toString().trim().length()>0){
				accountId = Long.parseLong(map.get("accountid").toString().trim());
				param.setAccountId(accountId);
				transParam.setAccountId(accountId);
			}
			if(map.get("banktype")!=null && map.get("banktype").toString().trim().length()>0){
				bankType = map.get("banktype").toString().trim();
				param.setBankType(bankType);
				String bankid=NameRef.getBankIdByRefCode(param.getBankType());
				param.setAllbankId(bankid);
			}
			if(map.get("lofficeid")!=null && map.get("lofficeid").toString().trim().length()>0){
				lOfficeID = Long.parseLong(map.get("lofficeid").toString().trim());
				param.setOfficeID(lOfficeID);
			}
			if(map.get("lcurrencyid")!=null && map.get("lcurrencyid").toString().trim().length()>0){
				lCurrencyID = Long.parseLong(map.get("lcurrencyid").toString().trim());
				param.setCurrencyType(lCurrencyID);
			}
			if(map.get("lclientid")!=null && map.get("lclientid").toString().trim().length()>0){
				lClientID = Long.parseLong(map.get("lclientid").toString().trim());
				param.setClientId(lClientID);
				param.setClientIdFrom(lClientID);
			}
			pagerInfo = billPrintBiz.queryBillPrintDetailInfo(param,transParam);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
		
	}
	
	/**
	 * 下属单位账户查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PagerInfo querySubAccountBillPrintInfo(Map map) throws Exception{
		PagerInfo pagerInfo = null;
		
		try
		{
			QueryBillPrintParam paramInfo    = new QueryBillPrintParam();
			
			String transactionStartDateString = "";
			String transactionEndDateString = "";
			long lOfficeID = -1;
			long clientId = -1;
			long lCurrencyID = -1;
			String bankType = "";
			long accountId = -1;
			long lUserID = -1;
			long subClientId = -1;
			long subAaccountId = -1;
			
			if(map.get("transactionstartdatestring")!=null && map.get("transactionstartdatestring").toString().trim().length()>0){
				transactionStartDateString = map.get("transactionstartdatestring").toString().trim();
				paramInfo.setTransactionStartDateString(transactionStartDateString);
			}
			if(map.get("transactionenddatestring")!=null && map.get("transactionenddatestring").toString().trim().length()>0){
				transactionEndDateString = map.get("transactionenddatestring").toString().trim();
				paramInfo.setTransactionEndDateString(transactionEndDateString);
			}
			if(map.get("lofficeid")!=null && map.get("lofficeid").toString().trim().length()>0){
				lOfficeID = Long.parseLong(map.get("lofficeid").toString().trim());
				paramInfo.setOfficeID(lOfficeID);
			}
			if(map.get("clientid").toString()!=null && map.get("clientid").toString().trim().length()>0)
			{
				clientId = Long.parseLong(map.get("clientid").toString().trim());
				paramInfo.setClientIdFrom(clientId);
				paramInfo.setClientIdTo(clientId);
			}
			if(map.get("lcurrencyid")!=null && map.get("lcurrencyid").toString().trim().length()>0){
				lCurrencyID = Long.parseLong(map.get("lcurrencyid").toString().trim());
				paramInfo.setCurrencyType(lCurrencyID);
			}
			if(map.get("banktype")!=null && map.get("banktype").toString().trim().length()>0){
				bankType = map.get("banktype").toString().trim();
				paramInfo.setBankType(bankType);
				//if(null != paramInfo.getBankType() && paramInfo.getBankType().length()>0){
					if("null".equals(paramInfo.getBankType())){
						paramInfo.setAllbankId("all");
					}else{
						String bankid=NameRef.getBankIdByRefCode(paramInfo.getBankType());
						paramInfo.setAllbankId(bankid);
					}
				//}	
			}
			if(map.get("accountid")!=null && map.get("accountid").toString().trim().length()>0){
				accountId = Long.parseLong(map.get("accountid").toString().trim());
				paramInfo.setAccountId(accountId);
			}
			if(map.get("luserid")!=null && map.get("luserid").toString().trim().length()>0){
				lUserID = Long.parseLong(map.get("luserid").toString().trim());
			}
			if(map.get("subclientid")!=null && map.get("subclientid").toString().trim().length()>0){
				subClientId = Long.parseLong(map.get("subclientid").toString().trim());
				paramInfo.setSubclientId(subClientId);
			}
			if(map.get("subaaccountid")!=null && map.get("subaaccountid").toString().trim().length()>0){
				subAaccountId = Long.parseLong(map.get("subaaccountid").toString().trim());
				paramInfo.setSubaccountId(subAaccountId);
			}
		    Map paramMap = new HashMap();
		    paramMap.put("info", paramInfo);
		    
			pagerInfo = billPrintBiz.querySubAccountBillPrintInfo(paramMap);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
		
	}
	
	/**
	 * 账户对账单明细查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PagerInfo querySubAccountBillPrintDetailInfo(Map map) throws Exception{
		PagerInfo pagerInfo = null;
		
		try
		{
			QueryBillPrintParam param = new QueryBillPrintParam();
			AcctTransParam transParam = new AcctTransParam();
			
			String transactionStartDateString = "";
			String transactionEndDateString = "";
			long accountId = -1;
			String bankType = "";
			long lOfficeID = -1;
			long lCurrencyID = -1;
			long lClientID = -1;
			
			
			if(map.get("transactionstartdatestring")!=null && map.get("transactionstartdatestring").toString().trim().length()>0){
				transactionStartDateString = map.get("transactionstartdatestring").toString().trim();
				param.setTransactionStartDateString(transactionStartDateString);
				transParam.setTransactionStartDateString(transactionStartDateString);
			}
			if(map.get("transactionenddatestring")!=null && map.get("transactionenddatestring").toString().trim().length()>0){
				transactionEndDateString = map.get("transactionenddatestring").toString().trim();
				param.setTransactionEndDateString(transactionEndDateString);
				transParam.setTransactionEndDateString(transactionEndDateString);
			}
			if(map.get("accountid")!=null && map.get("accountid").toString().trim().length()>0){
				accountId = Long.parseLong(map.get("accountid").toString().trim());
				param.setAccountId(accountId);
				transParam.setAccountId(accountId);
			}
			if(map.get("banktype")!=null && map.get("banktype").toString().trim().length()>0){
				bankType = map.get("banktype").toString().trim();
				param.setBankType(bankType);
				String bankid=NameRef.getBankIdByRefCode(param.getBankType());
				param.setAllbankId(bankid);
			}
			if(map.get("lofficeid")!=null && map.get("lofficeid").toString().trim().length()>0){
				lOfficeID = Long.parseLong(map.get("lofficeid").toString().trim());
				param.setOfficeID(lOfficeID);
			}
			if(map.get("lcurrencyid")!=null && map.get("lcurrencyid").toString().trim().length()>0){
				lCurrencyID = Long.parseLong(map.get("lcurrencyid").toString().trim());
				param.setCurrencyType(lCurrencyID);
			}
			if(map.get("lclientid")!=null && map.get("lclientid").toString().trim().length()>0){
				lClientID = Long.parseLong(map.get("lclientid").toString().trim());
				param.setClientId(lClientID);
				param.setClientIdFrom(lClientID);
			}
			pagerInfo = billPrintBiz.querySubAccountBillPrintDetailInfo(param,transParam);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
		
	}
	 
}
