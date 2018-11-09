package com.iss.itreasury.settlement.query.Action;

import java.sql.Timestamp;
import java.util.Map;

import com.iss.itreasury.settlement.query.bizlogic.QAverageAccountBalanceBiz;
import com.iss.itreasury.settlement.query.paraminfo.QueryAccountWhereInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class QAverageAccountBalanceAction {
	
	String AccountTypeIDArray[] = null;
	
	QAverageAccountBalanceBiz qAverageAccountBalanceBiz = new QAverageAccountBalanceBiz();
	
	public PagerInfo queryAccount(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try{
			
			long lAccountStatusID = Long.valueOf((String)map.get("lAccountStatusID".toLowerCase()));
			String AccountTypeIDArray = (String)map.get("_AccountTypeIDArray".toLowerCase());
			long lClientTypeID = Long.valueOf((String)map.get("lClientTypeID".toLowerCase()));
			long lConsignClientID = Long.valueOf((String)map.get("lConsignClientID".toLowerCase()));
			long lContractStatusID = Long.valueOf((String)map.get("lContractStatusID".toLowerCase()));
			long lContractYear = Long.valueOf((String)map.get("lContractYear".toLowerCase()));
			long lClientManager = Long.valueOf((String)map.get("lClientManager".toLowerCase()));
			String strEndAccountNo = (String)map.get("strEndAccountNo".toLowerCase());
			String strEndClientCode = (String)map.get("strEndClientCode".toLowerCase());
			double dEndContractAmount = Double.parseDouble((String)map.get("dEndContractAmount".toLowerCase()));
			String strEndContractCode = (String)map.get("strEndContractCode".toLowerCase());
			java.sql.Timestamp tsEndContractEndDate = toTimestamp(map.get("tsEndContractEndDate".toLowerCase()));
			double dEndContractInterestRate = Double.parseDouble((String)map.get("dEndContractInterestRate".toLowerCase()));
			long lEndContractPeriod = Long.valueOf((String)map.get("lEndContractPeriod".toLowerCase()));
			java.sql.Timestamp tsEndContractStartDate = toTimestamp(map.get("tsEndContractStartDate".toLowerCase()));
			String strEndFixFormNo = (String)map.get("strEndFixFormNo".toLowerCase());
			long lEndFixPeriod = Long.valueOf((String)map.get("lEndFixPeriod".toLowerCase()));
			double dEndLoanpayAmount = Double.parseDouble((String)map.get("dEndLoanpayAmount".toLowerCase()));
			String strEndLoanPayCode = (String)map.get("strEndLoanPayCode".toLowerCase());
			java.sql.Timestamp tsEndLoanPayEnddate = toTimestamp(map.get("tsEndLoanPayEnddate".toLowerCase()));
			double dEndLoanPayRate = Double.parseDouble((String)map.get("dEndLoanPayRate".toLowerCase()));
			java.sql.Timestamp tsEndLoanPayStartDate = toTimestamp(map.get("tsEndLoanPayStartDate".toLowerCase()));
			long lEnterpriseTypeID = Long.valueOf((String)map.get("lEnterpriseTypeID".toLowerCase()));
			long lIndustrytype1 = Long.valueOf((String)map.get("lIndustrytype1".toLowerCase()));
			long lIndustrytype2 = Long.valueOf((String)map.get("lIndustrytype2".toLowerCase()));
			long lIndustryTypeID = Long.valueOf((String)map.get("lIndustryTypeID".toLowerCase()));
			long lIsFilterNull = Long.valueOf((String)map.get("lIsFilterNull".toLowerCase()));
			long lIsNegotiate = Long.valueOf((String)map.get("lIsNegotiate".toLowerCase()));
			long lIsValidAccount = Long.valueOf((String)map.get("lIsValidAccount".toLowerCase()));
			long lLoanPayAccountID = Long.valueOf((String)map.get("lLoanPayAccountID".toLowerCase()));
			long lLoanPayBankID = Long.valueOf((String)map.get("lLoanPayBankID".toLowerCase()));
			long lLoanPayStatusID = Long.valueOf((String)map.get("lLoanPayStatusID".toLowerCase()));
			long lLoanType = Long.valueOf((String)map.get("lLoanType".toLowerCase()));
			long lParentCorpID = Long.valueOf((String)map.get("lParentCorpID".toLowerCase()));
			java.sql.Timestamp tsStartQueryDate = toTimestamp(map.get("tsStartQueryDate".toLowerCase()));
			java.sql.Timestamp tsEndQueryDate = toTimestamp(map.get("tsEndQueryDate".toLowerCase()));
			long lRegiontypeID = Long.valueOf((String)map.get("lRegiontypeID".toLowerCase()));
			long lRisklevel = Long.valueOf((String)map.get("lRisklevel".toLowerCase()));
			String strStartAccountNo = (String)map.get("strStartAccountNo".toLowerCase());
			String strStartClientCode = (String)map.get("strStartClientCode".toLowerCase());
			double dStartContractAmount = Double.parseDouble((String)map.get("dStartContractAmount".toLowerCase()));
			String strStartContractCode = (String)map.get("strStartContractCode".toLowerCase());
			java.sql.Timestamp tsStartContractEndDate =  toTimestamp(map.get("tsStartContractEndDate".toLowerCase()));
			double dStartContractInterestRate = Double.parseDouble((String)map.get("dStartContractInterestRate".toLowerCase()));
			long lStartContractPeriod = Long.valueOf((String)map.get("lStartContractPeriod".toLowerCase()));
			java.sql.Timestamp tsStartContractStartDate = toTimestamp(map.get("tsStartContractStartDate".toLowerCase()));
			String strStartFixFormNo = (String)map.get("strStartFixFormNo".toLowerCase());
			long lStartFixPeriod = Long.valueOf((String)map.get("lStartFixPeriod".toLowerCase()));
			double dStartLoanpayAmount = Double.parseDouble((String)map.get("dStartLoanpayAmount".toLowerCase()));
			String strStartLoanPayCode = (String)map.get("strStartLoanPayCode".toLowerCase());
			java.sql.Timestamp tsStartLoanPayEnddate = toTimestamp(map.get("tsStartLoanPayEnddate".toLowerCase()));
			double dStartLoanPayRate = Double.parseDouble((String)map.get("dStartLoanPayRate".toLowerCase()));
			java.sql.Timestamp tsStartLoanPayStartDate = toTimestamp(map.get("tsStartLoanPayStartDate".toLowerCase()));
			long ParentCorpID1 = Long.valueOf((String)map.get("ParentCorpID1".toLowerCase())); //上级单位1
			long ParentCorpID2 = Long.valueOf((String)map.get("ParentCorpID2".toLowerCase()));
			long lEnterpriseTypeID1 = Long.valueOf((String)map.get("lEnterpriseTypeID1".toLowerCase()));
			long lEnterpriseTypeID2 = Long.valueOf((String)map.get("lEnterpriseTypeID2".toLowerCase()));
			long lEnterpriseTypeID3 = Long.valueOf((String)map.get("lEnterpriseTypeID3".toLowerCase()));
			long lEnterpriseTypeID4 = Long.valueOf((String)map.get("lEnterpriseTypeID4".toLowerCase()));
			long lEnterpriseTypeID5 = Long.valueOf((String)map.get("lEnterpriseTypeID5".toLowerCase()));
			long lEnterpriseTypeID6 = Long.valueOf((String)map.get("lEnterpriseTypeID6".toLowerCase()));
			//add by 2012-05-16 添加指定编号
			String StrAccountCodes = (String)map.get("StrAccountCodes".toLowerCase());
			
			long lUnit = Long.valueOf((String)map.get("lUnit".toLowerCase()));
			
			long IntervalDays = Long.valueOf((String)map.get("IntervalDays".toLowerCase()));
			long OfficeID = Long.valueOf((String)map.get("_OfficeID".toLowerCase()));
			long CurrencyID = Long.valueOf((String)map.get("_CurrencyID".toLowerCase()));
			
			//赋值
			QueryAccountWhereInfo qawi = new QueryAccountWhereInfo();

			qawi.setParentCorpID1(ParentCorpID1);
			qawi.setParentCorpID2(ParentCorpID2);

			qawi.setEnterpriseTypeID1(lEnterpriseTypeID1);
			qawi.setEnterpriseTypeID2(lEnterpriseTypeID2);
			qawi.setEnterpriseTypeID3(lEnterpriseTypeID3);
			qawi.setEnterpriseTypeID4(lEnterpriseTypeID4);
			qawi.setEnterpriseTypeID5(lEnterpriseTypeID5);
			qawi.setEnterpriseTypeID6(lEnterpriseTypeID6);
			qawi.setAccountStatusID(lAccountStatusID);
			//qawi.setAccountTypeID(lAccountTypeID);
			qawi.set_AccountTypeIDArray(AccountTypeIDArray);
			qawi.setClientTypeID(lClientTypeID);
			qawi.setClientManager(lClientManager);
			qawi.setConsignClientID(lConsignClientID);
			qawi.setContractStatusID(lContractStatusID);
			qawi.setContractYear(lContractYear);
			qawi.setCurrencyID(CurrencyID);
			qawi.setEndAccountNo(strEndAccountNo);
			qawi.setEndClientCode(strEndClientCode);
			qawi.setEndContractAmount(dEndContractAmount);
			qawi.setEndContractCode(strEndContractCode);
			qawi.setEndContractEndDate(tsEndContractEndDate);
			qawi.setEndContractInterestRate(dEndContractInterestRate);
			qawi.setEndContractPeriod(lEndContractPeriod);
			qawi.setEndContractStartDate(tsEndContractStartDate);
			qawi.setEndFixFormNo(strEndFixFormNo);
			qawi.setEndFixPeriod(lEndFixPeriod);
			qawi.setEndLoanpayAmount(dEndLoanpayAmount);
			qawi.setEndLoanPayCode(strEndLoanPayCode);
			qawi.setEndLoanPayEnddate(tsEndLoanPayEnddate);
			qawi.setEndLoanPayRate(dEndLoanPayRate);
			qawi.setEndLoanPayStartDate(tsEndLoanPayStartDate);
			qawi.setEnterpriseTypeID(lEnterpriseTypeID);
			qawi.setIndustrytype1(lIndustrytype1);
			qawi.setIndustrytype2(lIndustrytype2);
			qawi.setIndustryTypeID(lIndustryTypeID);
			qawi.setIsFilterNull(lIsFilterNull);
			qawi.setIsNegotiate(lIsNegotiate);
			qawi.setIsValidAccount(lIsValidAccount);
			qawi.setLoanPayAccountID(lLoanPayAccountID);
			qawi.setLoanPayBankID(lLoanPayBankID);
			qawi.setLoanPayStatusID(lLoanPayStatusID);
			qawi.setLoanType(lLoanType);
			qawi.setOfficeID(OfficeID);
			qawi.setParentCorpID(lParentCorpID);
			qawi.setStartQueryDate(tsStartQueryDate);
			qawi.setEndQueryDate(tsEndQueryDate);
			qawi.setRegiontypeID(lRegiontypeID);
			qawi.setRisklevel(lRisklevel);
			qawi.setStartAccountNo(strStartAccountNo);
			qawi.setStartClientCode(strStartClientCode);
			qawi.setStartContractAmount(dStartContractAmount);
			qawi.setStartContractCode(strStartContractCode);
			qawi.setStartContractEndDate(tsStartContractEndDate);
			qawi.setStartContractInterestRate(dStartContractInterestRate);
			qawi.setStartContractPeriod(lStartContractPeriod);
			qawi.setStartContractStartDate(tsStartContractStartDate);
			qawi.setStartFixFormNo(strStartFixFormNo);
			qawi.setStartFixPeriod(lStartFixPeriod);
			qawi.setStartLoanpayAmount(dStartLoanpayAmount);
			qawi.setStartLoanPayCode(strStartLoanPayCode);
			qawi.setStartLoanPayEnddate(tsStartLoanPayEnddate);
			qawi.setStartLoanPayRate(dStartLoanPayRate);
			qawi.setStartLoanPayStartDate(tsStartLoanPayStartDate);			
			qawi.setIntervalDays(IntervalDays);
			//add by 2012-05-16 添加指定编号
			qawi.setAppointAccountNo(StrAccountCodes);
			
			qawi.setLUnit(lUnit);//金额转换
			
			pagerInfo = qAverageAccountBalanceBiz.queryAccount(qawi);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	
	public static Timestamp toTimestamp(Object object){
		Timestamp timestamp = null;
		if(!object.equals("null")){
			timestamp = Timestamp.valueOf((String)object);
		}
		return timestamp;
	}

}
