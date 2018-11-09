package com.iss.itreasury.settlement.query.Action;

import java.sql.Timestamp;
import java.util.Map;

import com.iss.itreasury.settlement.query.bizlogic.QQueryAccountBiz;
import com.iss.itreasury.settlement.query.paraminfo.QueryAccountAmountWhereInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryAccountWhereInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransAccountDetailWhereInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransactionConditionInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class QQueryAccountAction {

	QQueryAccountBiz qQueryAccountBiz = new QQueryAccountBiz();
	
	public PagerInfo queryQueryAccount(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{
			QueryAccountWhereInfo qInfo = new QueryAccountWhereInfo();
			qInfo.convertMapToDataEntity(map);//将Map转化为INFO
			
			pagerInfo = qQueryAccountBiz.queryQueryAccount(qInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	
	}
	
	public PagerInfo queryQueryTransaction(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{
			QueryTransactionConditionInfo qInfo = new QueryTransactionConditionInfo();
			
			String strTemp = "";
			double dPayAmountEnd = 0.0;
			double dPayAmountStart = 0.0;
			double dReceiveAmountEnd = 0.0;
			double dReceiveAmountStart = 0.0;
			String payMoneyStartBlank = "";
			String payMoneyEndBlank = "";
			String receiveMoneyStartBlank = "";
			String receiveMoneyEndBlank = "";
			if(map.get("dPayMoneyEnd".toLowerCase())!=null){
				strTemp = map.get("dPayMoneyEnd".toLowerCase()).toString();
			}
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				dPayAmountEnd = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
			    
			}else{
				payMoneyEndBlank = "blank";
			}
			if(map.get("dPayMoneyEnd".toLowerCase())!=null){
				strTemp = map.get("dPayMoneyStart".toLowerCase()).toString();
			}
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    dPayAmountStart = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
			}else{
				payMoneyStartBlank = "blank";
			}
			if(map.get("dPayMoneyEnd".toLowerCase())!=null){
				strTemp = map.get("dReceiveMoneyEnd".toLowerCase()).toString();
			}
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    dReceiveAmountEnd = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
			}else{
				receiveMoneyEndBlank = "blank";
			}
			if(map.get("dPayMoneyEnd".toLowerCase())!=null){
				strTemp = map.get("dReceiveMoneyStart".toLowerCase()).toString();
			}
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    dReceiveAmountStart = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
			}else{
				receiveMoneyStartBlank = "blank";
			}
			if(map.get("amount".toLowerCase())!=null && !(("").equals(map.get("amount".toLowerCase()).toString()))){
				strTemp = map.get("amount".toLowerCase()).toString();
				qInfo.setUnit(Long.valueOf(strTemp).longValue());
			}else{
				qInfo.setUnit(1l);
			}
			
			qInfo.setPayAmountEnd(dPayAmountEnd);
			qInfo.setPayAmountStart(dPayAmountStart);
			qInfo.setReceiveAmountEnd(dReceiveAmountEnd);
			qInfo.setReceiveAmountStart(dReceiveAmountStart);
			qInfo.setPayMoneyStartBlank(payMoneyStartBlank);
			qInfo.setPayMoneyEndBlank(payMoneyEndBlank);
			qInfo.setReceiveMoneyStartBlank(receiveMoneyStartBlank);
			qInfo.setReceiveMoneyEndBlank(receiveMoneyEndBlank);
			
			qInfo.convertMapToDataEntity(map);//将Map转化为INFO
			
			pagerInfo = qQueryAccountBiz.queryQueryTransaction(qInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
		
	}

	public PagerInfo queryQueryAccountAmount(Map map) throws Exception{
		PagerInfo pagerInfo = null;
		try
		{
			QueryAccountWhereInfo recInfo = new QueryAccountWhereInfo();
			QueryAccountAmountWhereInfo qInfo = new QueryAccountAmountWhereInfo();
			recInfo.convertMapToDataEntity(map);//将Map转化为INFO
			qInfo.copy(recInfo);
			long AccountTypeID1 = 0;       // 账户类型ID1
		    long AccountTypeID2 = 0;       // 账户类型ID2
		    long IsCheckedActive = -1;     // 动户查询
		    long IsCheckedType = -1;       // 账户类型1、2(判断)
		    String strTemp = "";
		    
		    if(map.get("AccountTypeID1".toLowerCase())!=null){
				strTemp = map.get("AccountTypeID1".toLowerCase()).toString();
			}
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				AccountTypeID1 = Long.valueOf(strTemp).longValue();
				qInfo.setAccountTypeID1(AccountTypeID1);
			}
			if(map.get("AccountTypeID2".toLowerCase())!=null){
				strTemp = map.get("AccountTypeID2".toLowerCase()).toString();
			}
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				AccountTypeID2 = Long.valueOf(strTemp).longValue();
				qInfo.setAccountTypeID2(AccountTypeID2);
			}
			if(map.get("IsCheckedActive".toLowerCase())!=null){
				strTemp = map.get("IsCheckedActive".toLowerCase()).toString();
			}
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				IsCheckedActive = Long.valueOf(strTemp).longValue();
				qInfo.setIsCheckedActive(IsCheckedActive);
			}
			if(map.get("IsCheckedType".toLowerCase())!=null){
				strTemp = map.get("IsCheckedType".toLowerCase()).toString();
			}
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				IsCheckedType = Long.valueOf(strTemp).longValue();
				qInfo.setIsCheckedType(IsCheckedType);
			}
			
			pagerInfo = qQueryAccountBiz.queryQueryAccountAmount(qInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	
	}

	public PagerInfo queryQueryAccountDetail(Map map) throws Exception{
		PagerInfo pagerInfo = null;
		long accountID = Long.parseLong(map.get("accountid")+"");
		Timestamp startDate = DataFormat.getDateTime(map.get("startdate")+"");
		Timestamp endDate = DataFormat.getDateTime(map.get("enddate")+"");
		long OfficeID = Long.parseLong(map.get("officeid")+"");
		long CurrencyID = Long.parseLong(map.get("currencyid")+"");
		try
		{
			QueryAccountAmountWhereInfo qInfo = new QueryAccountAmountWhereInfo();
			qInfo.setAccountID(accountID);
			qInfo.setStartQueryDate(startDate);
			qInfo.setEndQueryDate(endDate);
			qInfo.setOfficeID(OfficeID);
			qInfo.setCurrencyID(CurrencyID);
			pagerInfo = qQueryAccountBiz.queryQueryAccountDetail(qInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
		
	}
	
	public PagerInfo queryQueryAccountBalance(Map map) throws Exception{
		PagerInfo pagerInfo = null;
		try
		{
			QueryAccountWhereInfo qInfo = new QueryAccountWhereInfo();
			
			qInfo.convertMapToDataEntity(map);//将Map转化为INFO
			
			String strTemp = "";
			if(map.get("amount".toLowerCase())!=null && !(("").equals(map.get("amount".toLowerCase()).toString()))){
				strTemp = map.get("amount".toLowerCase()).toString();
				qInfo.setUnit(Long.valueOf(strTemp).longValue());
			}else{
				qInfo.setUnit(1l);
			}
			
			pagerInfo = qQueryAccountBiz.queryQueryAccountBalance(qInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
		
	}
	
	public PagerInfo queryQueryTransAccountDetail(Map map) throws Exception{
		PagerInfo pagerInfo = null;
		try
		{
			QueryTransAccountDetailWhereInfo qInfo = new QueryTransAccountDetailWhereInfo();
			
			qInfo.convertMapToDataEntity(map);//将Map转化为INFO
			
			String strTemp = "";
			if(map.get("amount".toLowerCase())!=null && !(("").equals(map.get("amount".toLowerCase()).toString()))){
				strTemp = map.get("amount".toLowerCase()).toString();
				qInfo.setUnit(Long.valueOf(strTemp).longValue());
			}else{
				qInfo.setUnit(1l);
			}
			
			pagerInfo = qQueryAccountBiz.queryQueryTransAccountDetail(qInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	} 
	
	public PagerInfo queryTransAccountDetail(Map map) throws Exception{
		PagerInfo pagerInfo = null;
		try
		{
			QueryTransAccountDetailWhereInfo qInfo = new QueryTransAccountDetailWhereInfo();
			
			qInfo.convertMapToDataEntity(map);//将Map转化为INFO
			
			String strTemp = "";
			if(map.get("dCanUseBalance".toLowerCase())!=null && !(("").equals(map.get("dCanUseBalance".toLowerCase()).toString()))){
				strTemp = map.get("dCanUseBalance".toLowerCase()).toString();
				qInfo.setDCanUseBalance(Double.valueOf(strTemp).doubleValue());
			}
			
			pagerInfo = qQueryAccountBiz.queryTransAccountDetail(qInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
}
