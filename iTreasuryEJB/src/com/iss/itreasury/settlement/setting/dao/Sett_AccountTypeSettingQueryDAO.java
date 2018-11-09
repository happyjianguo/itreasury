package com.iss.itreasury.settlement.setting.dao;

import com.iss.itreasury.util.Log;

public class Sett_AccountTypeSettingQueryDAO {
	
	public String queryAccountTypeSql(long lAccountGroupID, long lOfficeID, long lCurrencyID) {
		StringBuffer strBuff = new StringBuffer();
		strBuff = new StringBuffer();
		strBuff.append("SELECT ID  ,sAccountTypeCode,sAccountType ,nIsExistSubClass ,");
		strBuff.append(" nIsLoanType,nIsLoanMonth,nIsLoanYear,nIsConsign,nIsDraftType ,");
		strBuff.append(" sDefaultDocCode,nAutoClearAccount,nAccountGroupID,nIsClient,nIsAccount,nIsDeposit,nIsContract,sSubjectCode,sInterestSubjectCode,sBookedInterestSubjectCode,sNegotiateInterestSubjectCode,AccountModule,interestCalculationMode,payModule ");
		strBuff.append(" FROM ( ");
		strBuff.append(" SELECT ID,sAccountTypeCode,sAccountType ,nIsExistSubClass , nIsLoanType,nIsLoanMonth,nIsLoanYear,nIsConsign,nIsDraftType ,nIsClient, nIsAccount,nIsDeposit,nIsContract, \n");
		strBuff.append(" 	   sDefaultDocCode,nAutoClearAccount,nAccountGroupID,sett_currencysubject.ssubject sSubjectCode,sett_currencysubject.sInterestSubject sInterestSubjectCode,sett_currencysubject.sBookedInterestSubject sBookedInterestSubjectCode,sett_currencysubject.sNegotiateInterestSubject sNegotiateInterestSubjectCode,AccountModule ,payModule,interestCalculationMode\n");
		strBuff.append(" FROM   sett_currencysubject,( select * from sett_AccountType where officeid= "+lOfficeID+" and currencyid= "+lCurrencyID+" ) \n");
		strBuff.append(" WHERE  nbackofficeid= "+lOfficeID+" and ncurrencyid= "+lCurrencyID+" and stablename ='Sett_accounttype'and nrecordid = id \n");
		strBuff.append(" 	   and nStatusID=1 \n");
		if (lAccountGroupID != 0)//!=Notes.CODE_RECORD_ALL
		{
			strBuff.append(" AND  nAccountGroupID= "+lAccountGroupID);
		}
		
		strBuff.append(" ) ");
		Log.print(strBuff.toString());
		
		return strBuff.toString();
		
	}

}
