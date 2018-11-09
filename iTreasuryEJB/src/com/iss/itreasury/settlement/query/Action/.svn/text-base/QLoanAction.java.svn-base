package com.iss.itreasury.settlement.query.Action;

import java.sql.Timestamp;
import java.util.Map;

import com.iss.itreasury.settlement.query.bizlogic.QLoanBiz;
import com.iss.itreasury.settlement.query.paraminfo.QueryAccountWhereInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.sysframe.pager.dataentity.PagerInfo;

/**
 * 贷款查询
 * @author songwenxiao
 *
 */
public class QLoanAction {
	
	QLoanBiz biz = new QLoanBiz();
	
	public PagerInfo queryLoanDetail(Map map) throws Exception
	{
		PagerInfo pagerInfo = null;
		try
		{
			 //定义变量
			long lAccountStatusID = -1;
			long lAccountTypeID = -1;
			long lClientTypeID = -1;
			long lConsignClientID = -1;
			long lContractStatusID = -1;
			long lContractYear = -1;
			long lCurrencyID = -1;
			String strEndAccountNo = "";
			String strEndClientCode = "";
			double dEndContractAmount = 0.0;
			String strEndContractCode = "";
			java.sql.Timestamp tsEndContractEndDate = null;
			java.sql.Timestamp tsEndLoanPayEndDate = null;
			double dEndContractInterestRate = 0.0;
			long lEndContractPeriod = -1;
			java.sql.Timestamp tsEndContractStartDate = null;
			java.sql.Timestamp tsEndLoanPayStartDate = null;
			String strEndFixFormNo = "";
			long lEndFixPeriod = -1;
			double dEndLoanpayAmount = 0.0;
			String strEndLoanPayCode = "";
			double dEndLoanPayRate = 0.0;
			long lEnterpriseTypeID1 = -1;
			long lEnterpriseTypeID2 = -1;
			long lEnterpriseTypeID3 = -1;
			long lEnterpriseTypeID4 = -1;
			long lEnterpriseTypeID5 = -1;
			long lEnterpriseTypeID6 = -1;
			long lEnterpriseTypeID = -1;
			long lIndustrytype1 = -1;
			long lIndustrytype2 = -1;
			//2008-11-4增加 kaishao
			long lIndustryType3 = -1;
			//增加结束
			long lIndustryTypeID = -1;
			long lIsFilterNull = -1;
			long lIsNegotiate = -1;
			long lIsValidAccount = -1;
			long lLoanPayAccountID = -1;
			long lLoanPayBankID = -1;
			long lLoanPayStatusID = -1;
			long lLoanType = -1;
			long lOfficeID = -1;
			long lParentCorpID = -1;
			java.sql.Timestamp tsQueryDate = null;
			long lRegiontypeID = -1;
			long lRisklevel = -1;
			String strStartAccountNo = "";
			String strStartClientCode = "";
			double dStartContractAmount = 0.0;
			String strStartContractCode = "";
			java.sql.Timestamp tsStartContractEndDate = null;
			java.sql.Timestamp tsStartLoanPayEndDate = null;
			double dStartContractInterestRate = 0.0;
			long lStartContractPeriod = -1;
			java.sql.Timestamp tsStartContractStartDate = null;
			java.sql.Timestamp tsStartLoanPayStartDate = null;
			String strStartFixFormNo = "";
			long lStartFixPeriod = -1;
			double dStartLoanpayAmount = 0.0;
			String strStartLoanPayCode = "";
			double dStartLoanPayRate = 0.0;
			//贷款查询中添加的字段
			long DiscountCredenceIDStart = -1; //贴现票据号-由
			long DiscountCredenceIDEnd = -1; //贴现票据号-到
			Timestamp DtFinish = null; //截至日期
			double StartLoanpayAmount=0.0; //金额-从  与贷款金额相匹配
			double EndLoanpayAmount=0.0; //金额-到 
			long ParentCorpID1 = -1; //上级单位1
			long ParentCorpID2 = -1; //上级单位2
			long LoanPayAccountID=-1; // 贷款发放至活期账户号
			long LoanPayBankID=-1; // 贷款发放/收回至银行开户行
			long lExtendAttribute1 = -1;//扩展属性1
			long lExtendAttribute2 = -1;//扩展属性2
			long lExtendAttribute3 = -1;//扩展属性3
			long lExtendAttribute4 = -1;//扩展属性4
			long sss = -1;
			long lOrderField = -1;
			long cID = -1;
			long lUnit=1;
			long lDesc = Constant.PageControl.CODE_ASCORDESC_DESC;
			//add by 2012-05-21 添加指定编号
			String strAccountCodes = "";
			String CurrencySymbol="";

			String strTemp = null;
			strTemp = (String)map.get("lAccountStatusID".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lAccountStatusID = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("lAccountTypeID".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lAccountTypeID = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("lClientTypeID".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lClientTypeID = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("lConsignClientID".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lConsignClientID = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("lContractStatusID".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lContractStatusID = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("lContractYear".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lContractYear = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("lCurrencyID".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lCurrencyID = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("lDesc".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lDesc = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("strEndAccountNoCtrl".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				if( strTemp.length() > 8 )
					strEndAccountNo = strTemp;
			}
			strTemp = (String)map.get("strEndClientCodeCtrl".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				strEndClientCode = strTemp;
			}
			strTemp = (String)map.get("CurrencySymbol".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				CurrencySymbol = strTemp;
			}
			strTemp = (String)map.get("dEndContractAmount".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				dEndContractAmount = Double.valueOf(strTemp).doubleValue();
			}
			strTemp = (String)map.get("strEndContractCodeCtrl".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				strEndContractCode = strTemp;
			}
			strTemp = (String)map.get("tsEndContractEndDate".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				tsEndContractEndDate = DataFormat.getDateTime(strTemp);
			}
			strTemp = (String)map.get("tsEndLoanPayEndDate".toLowerCase());
			
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				tsEndLoanPayEndDate = DataFormat.getDateTime(strTemp);
			}
			strTemp = (String)map.get("dEndContractInterestRate".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				dEndContractInterestRate = Double.valueOf(strTemp).doubleValue();
			}
			strTemp = (String)map.get("lEndContractPeriod".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lEndContractPeriod = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("tsEndContractStartDate".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				tsEndContractStartDate = DataFormat.getDateTime(strTemp);
			}
			/*s*/
			strTemp = (String)map.get("strEndFixFormNoCtrl".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				strEndFixFormNo = strTemp;
			}
			strTemp = (String)map.get("lEndFixPeriod".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lEndFixPeriod = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("dEndLoanpayAmount".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				dEndLoanpayAmount = Double.valueOf(strTemp).doubleValue();
			}
			strTemp = (String)map.get("strEndLoanPayCodeCtrl".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				strEndLoanPayCode = strTemp;
			}
			/*strTemp = (String)map.get("tsEndLoanPayEndDate".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				tsEndLoanPayEndDate = DataFormat.getDateTime(strTemp);
			}*/
			strTemp = (String)map.get("dEndLoanPayRate".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				dEndLoanPayRate = Double.valueOf(strTemp).doubleValue();
			}
			strTemp = (String)map.get("tsEndLoanPayStartDate".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				tsEndLoanPayStartDate = DataFormat.getDateTime(strTemp);
			}
			strTemp = (String)map.get("lEnterpriseTypeID".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lEnterpriseTypeID = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("lIndustrytype1".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lIndustrytype1 = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("lIndustrytype2".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lIndustrytype2 = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("lIndustryTypeID".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lIndustryTypeID = Long.valueOf(strTemp).longValue();
			}
			//2008-11-4增加 kaishao
			strTemp = (String)map.get("lIndustryType3".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lIndustryType3 = Long.valueOf(strTemp).longValue();
			}
			//增加结束
			strTemp = (String)map.get("lIsFilterNull".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lIsFilterNull = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("lIsNegotiate".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lIsNegotiate = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("lIsValidAccount".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lIsValidAccount = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("lLoanPayAccountID".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lLoanPayAccountID = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("lLoanPayBankID".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lLoanPayBankID = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("lLoanPayStatusID".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lLoanPayStatusID = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("lLoanType".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lLoanType = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("lOfficeID".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lOfficeID = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("unit".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lUnit = Long.valueOf(strTemp).longValue();
			}
			/*strTemp = (String)map.get("lOrderField".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lOrderField = Long.valueOf(strTemp).longValue();
			}*/
			strTemp = (String)map.get("lParentCorpID".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lParentCorpID = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("strQueryDate".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				tsQueryDate = DataFormat.getDateTime(strTemp);
			}
			strTemp = (String)map.get("lRegiontypeID".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lRegiontypeID = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("lRisklevel".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lRisklevel = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("strStartAccountNoCtrl".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				if( strTemp.length() > 8 )
					strStartAccountNo = strTemp;
			}
			strTemp = (String)map.get("strStartClientCodeCtrl".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				strStartClientCode = strTemp;
			}
			strTemp = (String)map.get("dStartContractAmount".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				dStartContractAmount = Double.valueOf(strTemp).doubleValue();
			}
			strTemp = (String)map.get("strStartContractCodeCtrl".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				strStartContractCode = strTemp;
			}
			strTemp = (String)map.get("tsStartContractEndDate".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				tsStartContractEndDate = DataFormat.getDateTime(strTemp);
			}
			strTemp = (String)map.get("tsStartLoanPayEndDate".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				tsStartLoanPayEndDate = DataFormat.getDateTime(strTemp);
			}
			strTemp = (String)map.get("dStartContractInterestRate".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				dStartContractInterestRate = Double.valueOf(strTemp).doubleValue();
			}
			strTemp = (String)map.get("lStartContractPeriod".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lStartContractPeriod = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("tsStartContractStartDate".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				tsStartContractStartDate = DataFormat.getDateTime(strTemp);
			}
			strTemp = (String)map.get("tsStartLoanPayStartDate".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				tsStartLoanPayStartDate = DataFormat.getDateTime(strTemp);
			}
			strTemp = (String)map.get("strStartFixFormNoCtrl".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				strStartFixFormNo = strTemp;
			}
			strTemp = (String)map.get("lStartFixPeriod".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lStartFixPeriod = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("dStartLoanpayAmount".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				dStartLoanpayAmount = Double.valueOf(strTemp).doubleValue();
			}
			strTemp = (String)map.get("strStartLoanPayCodeCtrl".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				strStartLoanPayCode = strTemp;
			}
			/*strTemp = (String)map.get("tsStartLoanPayEndDate".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				tsStartLoanPayEndDate = DataFormat.getDateTime(strTemp);
			}*/
			strTemp = (String)map.get("dStartLoanPayRate".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				dStartLoanPayRate = Double.valueOf(strTemp).doubleValue();
			}
			
			//取值
		
			// DiscountCredenceIDStart = -1; //贴现票据号-由
			strTemp = (String)map.get("lNDiscountBillID1".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				DiscountCredenceIDStart = Long.valueOf(strTemp).longValue();
			}
			// DiscountCredenceIDEnd = -1; //贴现票据号-到
			strTemp = (String)map.get("lNDiscountBillID2".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				DiscountCredenceIDEnd = Long.valueOf(strTemp).longValue();
			}
			/*
			// DtFinish = null; //截至日期*/
			strTemp = (String)map.get("DtFinish".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				DtFinish = DataFormat.getDateTime(strTemp);
			}		
			// ContractAmountStart=0.0; //金额-从  与子账户中的开户金额匹配
			strTemp = (String)map.get("StartLoanpayAmount".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				StartLoanpayAmount = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
			}
			// ContractAmountEnd=0.0; //金额-到 
			strTemp = (String)map.get("EndLoanpayAmount".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				EndLoanpayAmount = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
			}
			// ParentCorpID1 = -1; //上级单位1
			strTemp = (String)map.get("ParentCorpID1".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				ParentCorpID1 = Long.valueOf(strTemp).longValue();
			}
			// ParentCorpID2 = -1; //上级单位2
			strTemp = (String)map.get("ParentCorpID2".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				ParentCorpID2 = Long.valueOf(strTemp).longValue();
			}
			// LoanPayAccountID=-1; // 贷款发放至活期账户号
			strTemp = (String)map.get("LoanPayAccountIDCtrl".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				LoanPayAccountID = Long.valueOf(strTemp).longValue();
			}
			// LoanPayBankID 贷款发放至/收回至银行开户行
			strTemp = (String)map.get("LoanPayBankIDCtrl".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				LoanPayBankID = Long.valueOf(strTemp).longValue();
			}
			//扩展属性1
			strTemp = (String)map.get("SelExtendAttribute1".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lExtendAttribute1 = Long.parseLong(strTemp);
			}
			//扩展属性2
			strTemp = (String)map.get("SelExtendAttribute2".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lExtendAttribute2 = Long.parseLong(strTemp);
			}
			//扩展属性3
			strTemp = (String)map.get("SelExtendAttribute3".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lExtendAttribute3 = Long.parseLong(strTemp);
			}
			//扩展属性4
			strTemp = (String)map.get("SelExtendAttribute4".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lExtendAttribute4 = Long.parseLong(strTemp);
			}
			/*strTemp = (String)map.get("mod".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				sss = Long.parseLong(strTemp);
			}
			
			strTemp = (String)map.get("cID".toLowerCase());
			if (strTemp != null && !("null").equals(strTemp) && strTemp.trim().length() > 0)
			{
				cID = Long.parseLong(strTemp);
			}*/
			strTemp = (String)map.get("EnterpriseTypeID1".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lEnterpriseTypeID1 = Long.parseLong(strTemp);
			}
			strTemp = (String)map.get("EnterpriseTypeID2".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lEnterpriseTypeID2 = Long.parseLong(strTemp);
			}
			strTemp = (String)map.get("EnterpriseTypeID3".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lEnterpriseTypeID3 = Long.parseLong(strTemp);
			}
			strTemp = (String)map.get("EnterpriseTypeID4".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lEnterpriseTypeID4 = Long.parseLong(strTemp);
			}
			strTemp = (String)map.get("EnterpriseTypeID5".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lEnterpriseTypeID5 = Long.parseLong(strTemp);
			}
			strTemp = (String)map.get("EnterpriseTypeID6".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lEnterpriseTypeID6 = Long.parseLong(strTemp);
			}
			//add by 2012-05-21 添加指定编号
			strTemp = (String)map.get("accountNos".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				strAccountCodes = strTemp;
			}
			
			
			
	        QueryAccountWhereInfo qawi = null;
			qawi = new QueryAccountWhereInfo();
			//设值
			qawi.setDiscountCredenceIDStart (DiscountCredenceIDStart);
			qawi.setDiscountCredenceIDEnd(DiscountCredenceIDEnd);
			qawi.setParentCorpID1(ParentCorpID1);
			qawi.setParentCorpID2(ParentCorpID2);

			qawi.setEnterpriseTypeID1(lEnterpriseTypeID1);
			qawi.setEnterpriseTypeID2(lEnterpriseTypeID2);
			qawi.setEnterpriseTypeID3(lEnterpriseTypeID3);
			qawi.setEnterpriseTypeID4(lEnterpriseTypeID4);
			qawi.setEnterpriseTypeID5(lEnterpriseTypeID5);
			qawi.setEnterpriseTypeID6(lEnterpriseTypeID6);
			qawi.setAccountStatusID(lAccountStatusID);
			qawi.setAccountTypeID(lAccountTypeID);
			qawi.setClientTypeID(lClientTypeID);
			qawi.setConsignClientID(lConsignClientID);
			qawi.setContractStatusID(lContractStatusID);
			qawi.setContractYear(lContractYear);
			qawi.setCurrencyID(lCurrencyID);
			qawi.setDesc(lDesc);
			qawi.setEndAccountNo(strEndAccountNo);
			qawi.setEndClientCode(strEndClientCode);
			qawi.setEndContractAmount(dEndContractAmount);
			qawi.setEndContractCode(strEndContractCode);
			qawi.setEndContractEndDate(tsEndContractEndDate);
			qawi.setEndLoanPayEnddate(tsEndLoanPayEndDate);
			qawi.setEndContractInterestRate(dEndContractInterestRate);
			qawi.setEndContractPeriod(lEndContractPeriod);
			qawi.setEndContractStartDate(tsEndContractStartDate);
			qawi.setEndLoanPayStartDate(tsEndLoanPayStartDate);
			qawi.setEndFixFormNo(strEndFixFormNo);
			qawi.setEndFixPeriod(lEndFixPeriod);
			qawi.setEndLoanpayAmount(dEndLoanpayAmount);
			qawi.setEndLoanPayCode(strEndLoanPayCode);
			qawi.setEndLoanPayRate(dEndLoanPayRate);
			qawi.setEnterpriseTypeID(lEnterpriseTypeID);
			qawi.setIndustrytype1(lIndustrytype1);
			qawi.setIndustrytype2(lIndustrytype2);
			//2008-11-4增加 kaishao
			qawi.setIndustrytype3(lIndustryType3);
			//增加结束
			qawi.setIndustryTypeID(lIndustryTypeID);
			qawi.setIsFilterNull(lIsFilterNull);
			qawi.setIsNegotiate(lIsNegotiate);
			qawi.setIsValidAccount(lIsValidAccount);
			qawi.setLoanPayAccountID(lLoanPayAccountID);
			qawi.setLoanPayBankID(lLoanPayBankID);
			//qawi.setLoanPayStatusID(lLoanPayStatusID);
			//说明：放款单状态作为子账户状态处理
            qawi.setLoanPayStatusID(lLoanPayStatusID);

			qawi.setLoanType(lLoanType);
			qawi.setOfficeID(lOfficeID);
			qawi.setOrderField(lOrderField);
			qawi.setParentCorpID(lParentCorpID);
			qawi.setQueryDate(tsQueryDate);
			qawi.setRegiontypeID(lRegiontypeID);
			qawi.setRisklevel(lRisklevel);
			qawi.setStartAccountNo(strStartAccountNo);
			qawi.setStartClientCode(strStartClientCode);
			qawi.setStartContractAmount(dStartContractAmount);
			qawi.setStartContractCode(strStartContractCode);
			qawi.setStartContractEndDate(tsStartContractEndDate);
			qawi.setStartLoanPayEnddate(tsStartLoanPayEndDate);
			qawi.setStartContractInterestRate(dStartContractInterestRate);
			qawi.setStartContractPeriod(lStartContractPeriod);
			qawi.setStartContractStartDate(tsStartContractStartDate);
			qawi.setStartLoanPayStartDate(tsStartLoanPayStartDate);
			qawi.setStartFixFormNo(strStartFixFormNo);
			qawi.setStartFixPeriod(lStartFixPeriod);
			//2004-01-07 lxr 修改 begin
			qawi.setDtFinish(DtFinish);
			//2004-01-07 lxr 修改 end
			qawi.setStartLoanpayAmount(dStartLoanpayAmount);
			qawi.setStartLoanPayCode(strStartLoanPayCode);
			
			qawi.setStartLoanPayRate(dStartLoanPayRate);
			//金额 作为放款金额处理
			qawi.setStartLoanpayAmount(StartLoanpayAmount);
			qawi.setEndLoanpayAmount(EndLoanpayAmount);
			//贷款发放至活期账户号
			qawi.setLoanPayAccountID(LoanPayAccountID);
			// LoanPayBankID 贷款发放至/收回至银行开户行
			qawi.setLoanPayBankID(LoanPayBankID);
			qawi.setExtendAttribute1(lExtendAttribute1);
			qawi.setExtendAttribute2(lExtendAttribute2);
			qawi.setExtendAttribute3(lExtendAttribute3);
			qawi.setExtendAttribute4(lExtendAttribute4);
			//add by 2012-05-21 添加指定编号
			qawi.setAppointAccountNo(strAccountCodes);
			pagerInfo = biz.queryCounterTrans(qawi, lUnit,CurrencySymbol);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}

}
