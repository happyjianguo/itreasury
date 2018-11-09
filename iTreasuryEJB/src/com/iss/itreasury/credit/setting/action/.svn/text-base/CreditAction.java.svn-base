package com.iss.itreasury.credit.setting.action;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import com.iss.itreasury.clientmanage.client.dataentity.ExtClientInfo;
import com.iss.itreasury.credit.setting.bizlogic.CreditBiz;
import com.iss.itreasury.credit.setting.dataentity.AmountFormInfo;
import com.iss.itreasury.credit.setting.dataentity.AmountFormViewInfo;
import com.iss.itreasury.credit.setting.dataentity.AmountSetupInfo;
import com.iss.itreasury.credit.setting.dataentity.AmountSetupViewInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class CreditAction {

	CreditBiz creditBiz = new CreditBiz();
	
	/**
	 * 授信变更action层
	 * @param amountFormInfo
	 * @return
	 * @throws Exception
	 */		
	public PagerInfo queryCredit(Map map) throws Exception
	{
		long officeId = -1;
		long currencyId = -1;
		long clientId = -1;
		double creditAmount = 0.0;
		Timestamp startDate = null;
		Timestamp endDate = null;
		
		PagerInfo pagerInfo = null;
		try
		{
			AmountFormInfo amountFormInfo = new AmountFormInfo();
			//amountFormInfo.convertMapToDataEntity(map);//将Map转化为INFO
			if(map.get("officeid") != null){
				officeId = Long.parseLong((String)map.get("officeid"));
				amountFormInfo.setOfficeId(officeId);
			}
			if(map.get("currencyid") != null){
				currencyId = Long.parseLong((String)map.get("currencyid"));
				amountFormInfo.setCurrencyId(currencyId);
			}
			if(map.get("clientid") !=null && !map.get("clientid").equals("")){
				clientId = Long.parseLong((String)map.get("clientid"));
				amountFormInfo.setClientId(clientId);
			}
			if(map.get("creditamount") != null && !map.get("creditamount").equals("")){
				creditAmount = DataFormat.parseNumber((String)map.get("creditamount"));
				amountFormInfo.setCreditAmount(creditAmount);
			}
			if(map.get("startdate") != null && !map.get("startdate").equals("")){
				startDate = DataFormat.getDateTime((String)map.get("startdate"));
				amountFormInfo.setStartDate(startDate);
			}
			if(map.get("enddate") != null && !map.get("enddate").equals("")){
				endDate = DataFormat.getDateTime((String)map.get("enddate"));
				amountFormInfo.setEndDate(endDate);
			}
			
			pagerInfo = creditBiz.queryCreditInfo(amountFormInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	
	/**
	 * 授信激活action层
	 * @param amountFormInfo
	 * @return
	 * @throws Exception
	 */		
	public PagerInfo queryCreditStatus(Map map) throws Exception
	{
		long officeId = -1;
		long currencyId = -1;
		long state = -1; 
		long clientId = -1;
		double CreditAmount = 0.0;
		Timestamp startDate = null;
		Timestamp endDate = null;
		
		PagerInfo pagerInfo = null;
		try
		{
			AmountSetupInfo amountSetupInfo = new AmountSetupInfo();
			//amountFormInfo.convertMapToDataEntity(map);//将Map转化为INFO
			if(map.get("officeid") != null){
				officeId = Long.parseLong((String)map.get("officeid"));
				amountSetupInfo.setOfficeId(officeId);
			}
			if(map.get("currencyid") != null){
				currencyId = Long.parseLong((String)map.get("currencyid"));
				amountSetupInfo.setCurrencyId(currencyId);
			}
			if(map.get("creditstate") != null){
				state = Long.parseLong((String)map.get("creditstate"));
				amountSetupInfo.setState(state);
			}
			if(map.get("clientid") != null && !map.get("clientid").equals("")){
				clientId = Long.parseLong((String)map.get("clientid"));
				amountSetupInfo.setClientId(clientId);
			}
			if(map.get("creditamount") != null){
				CreditAmount = DataFormat.parseNumber((String)map.get("creditamount"));
				amountSetupInfo.setCreditAmount(CreditAmount);
			}
			if(map.get("startdate") != null){
				startDate = DataFormat.getDateTime((String)map.get("startdate"));
				amountSetupInfo.setStartDate(startDate);
			}
			if(map.get("enddate") != null){
				endDate = DataFormat.getDateTime((String)map.get("enddate"));
				amountSetupInfo.setEndDate(endDate);
			}
			pagerInfo = creditBiz.queryCreditStatusInfo(amountSetupInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	/**
	 * 授信可用额度查询action层
	 */
	public PagerInfo queryCreditQuery(Map map) throws Exception
	{
		long officeId = -1;
		long currencyId = -1;
		long clientId = -1;
		double creditAmount = 0.0;
		Timestamp startDate = null;
		
		PagerInfo pagerInfo = null;
		try
		{
			AmountFormViewInfo amountFormViewInfo = new AmountFormViewInfo();
			
			if(map.get("officeid") != null ){
				officeId = Long.parseLong((String)map.get("officeid"));
				amountFormViewInfo.setOfficeId(officeId);
			}
			if(map.get("currencyid") != null){
				currencyId = Long.parseLong((String)map.get("currencyid"));
				amountFormViewInfo.setCurrencyId(currencyId);
			}
			if(map.get("clientid") !=null){
				clientId = Long.parseLong((String)map.get("clientid"));
				amountFormViewInfo.setClientId(clientId);
			}
			if(map.get("creditamount") !=null && !map.get("creditamount").equals("")){
				creditAmount = DataFormat.parseNumber((String)map.get("creditamount"));
				amountFormViewInfo.setCreditAmount(creditAmount);
			}
			if(map.get("startdate") != null && !map.get("startdate").equals("")){
				startDate = DataFormat.getDateTime((String)map.get("startdate"));
				amountFormViewInfo.setStartDate(startDate);
				amountFormViewInfo.setEndDate(startDate);
			}
			pagerInfo = creditBiz.queryCreditQueryInfo(amountFormViewInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	/**
	 * 授信记录查询action层
	 */
	public PagerInfo queryCreditRecordQuery(Map map) throws Exception
	{
		long officeId = -1;
		long currencyId = -1;
		long clientId = -1;
		double creditAmount = 0.00;
		Timestamp startDate = null;
		Timestamp endDate = null;
		long operationType = -1;
		long state = -1;
		
		PagerInfo pagerInfo = null;
		try
		{
			AmountSetupViewInfo amountSetupViewInfo = new AmountSetupViewInfo();
			
			if(map.get("officeid") != null){
				officeId = Long.parseLong((String)map.get("officeid"));
				amountSetupViewInfo.setOfficeId(officeId);
			}
			if(map.get("currencyid") != null){
				currencyId = Long.parseLong((String)map.get("currencyid"));
				amountSetupViewInfo.setCurrencyId(currencyId);
			}
			if(map.get("clientid") != null && !map.get("clientid").equals("")){
				clientId = Long.parseLong((String)map.get("clientid"));
				amountSetupViewInfo.setClientId(clientId);
			}
			if(map.get("creditamount") != null && !map.get("creditamount").equals("")){
				creditAmount = DataFormat.parseNumber((String)map.get("creditamount"));
				amountSetupViewInfo.setCreditAmount(creditAmount);
			}
			if(map.get("startdate") != null && !map.get("startdate").equals("")){
				startDate = DataFormat.getDateTime((String)map.get("startdate"));
				amountSetupViewInfo.setStartDate(startDate);
			}
			if(map.get("enddate") != null && !map.get("enddate").equals("")){
				endDate = DataFormat.getDateTime((String)map.get("enddate"));
				amountSetupViewInfo.setEndDate(endDate);
			}
			if(map.get("operationtype") != null){
				operationType = Long.parseLong((String)map.get("operationtype"));
				amountSetupViewInfo.setOperationType(operationType);
			}
			if(map.get("creditstate") != null && !map.get("creditstate").equals("") ){
				state = Long.parseLong((String)map.get("creditstate"));
				amountSetupViewInfo.setState(state);
			}
			pagerInfo = creditBiz.queryCreditRecordQueryInfo(amountSetupViewInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	
}
