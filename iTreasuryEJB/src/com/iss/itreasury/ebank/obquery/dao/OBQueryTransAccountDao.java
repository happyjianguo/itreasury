package com.iss.itreasury.ebank.obquery.dao;

import com.iss.itreasury.settlement.query.paraminfo.QueryTransAccountDetailWhereInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;

public class OBQueryTransAccountDao {
	
	public String queryTransAccountDetailSQL(QueryTransAccountDetailWhereInfo qInfo){

		
		StringBuffer sbSQL= new StringBuffer();

		// select 
		sbSQL.append(" select * \n");
		sbSQL.append(" from ");
		sbSQL.append(" ( ");
		if(1 == qInfo.getIsIntrDate()){
			sbSQL.append(" select trans.NSUBACCOUNTID NSUBACCOUNTID,trans.NOPPACCOUNTID NOPPACCOUNTID,trans.nGroup GroupID,trans.sTransNo TransNo,trans.nTransactionTypeID TransTypeID,trans.dtExecute ExecuteDate,to_char(trans.dtInterestStart,'dd') ExecuteDay,to_char(trans.dtInterestStart,'mm') ExecuteMonth,to_char(trans.dtInterestStart,'yyyy') ExecuteYear,trans.sAbstract Abstract,trans.dtInterestStart InterestStartDate,1 Direction, \n");
		}
		else{
			sbSQL.append(" select trans.NSUBACCOUNTID NSUBACCOUNTID,trans.NOPPACCOUNTID NOPPACCOUNTID,trans.nGroup GroupID,trans.sTransNo TransNo,trans.nTransactionTypeID TransTypeID,trans.dtExecute ExecuteDate,to_char(trans.dtExecute,'dd') ExecuteDay,to_char(trans.dtExecute,'mm') ExecuteMonth,to_char(trans.dtExecute,'yyyy') ExecuteYear,trans.sAbstract Abstract,trans.dtInterestStart InterestStartDate,1 Direction, \n");
		}
		sbSQL.append(" decode(trans.nTransactionTypeID, \n");
		sbSQL.append(SETTConstant.TransactionType.BANKRECEIVE+",decode(trans.sBillNo,null,transCurrencyDeposit.SCONSIGNVOUCHERNO,trans.sBillNo), \n");
		sbSQL.append(SETTConstant.TransactionType.BANKPAY+",decode(trans.sBillNo,null,transCurrencyDeposit.SCONSIGNVOUCHERNO,trans.sBillNo), \n");
		sbSQL.append(SETTConstant.TransactionType.INTERNALVIREMENT+",decode(trans.sBillNo,null,transCurrencyDeposit.SCONSIGNVOUCHERNO,trans.sBillNo), \n");
		sbSQL.append(" subaccount.af_sdepositno) BillNo, \n");
		sbSQL.append(" trans.sBankChequeNo BankChequeNo,trans.mAmount PayAmount,0 ReceiveAmount,account.nAccountTypeID AccountTypeID, \n");
		//为账户对账单信息查询 所加
		sbSQL.append(" trans.OPPACCOUNTNO OPPACCOUNTNO,trans.OPPACCOUNTNAME OPPACCOUNTNAME \n");
		//
		sbSQL.append(" from sett_TransAccountDetail trans,sett_Account account,sett_SubAccount subaccount,Loan_Contractform contractform,Loan_PayForm loanpayform, SETT_TRANSCURRENTDEPOSIT transCurrencyDeposit \n");
		sbSQL.append(" where subaccount.nAccountID = account.ID and trans.nSubAccountID = subaccount.ID and subaccount.AL_NLOANNOTEID = loanpayform.ID(+) and loanpayform.nContractID = contractform.ID(+) \n");
		sbSQL.append(" and trans.nStatusID=" + SETTConstant.TransactionStatus.CHECK + " and trans.nTransDirection = 1 \n");
		sbSQL.append(" and account.nCurrencyID=" + qInfo.getCurrencyID() + " \n");
		sbSQL.append(" and subaccount.nAccountID = " + qInfo.getAccountID() + " \n");
		sbSQL.append(" and transCurrencyDeposit.sTransNo(+) = trans.sTransNo \n");
		if(1 == qInfo.getIsIntrDate())
		{
			sbSQL.append(
					" and trans.dtintereststart between to_date('"
						+ DataFormat.formatDate(qInfo.getStartDate())
						+ "','yyyy-mm-dd') and to_date('"
						+ DataFormat.formatDate(qInfo.getEndDate())
						+ "','yyyy-mm-dd') \n");
		}
		else
		{
			sbSQL.append(
				" and trans.dtExecute between to_date('"
					+ DataFormat.formatDate(qInfo.getStartDate())
					+ "','yyyy-mm-dd') and to_date('"
					+ DataFormat.formatDate(qInfo.getEndDate())
					+ "','yyyy-mm-dd') \n");
		}
		if(!qInfo.getAccountStatusIDs().equals("")&&qInfo.getAccountStatusIDs() != null)
		{
			sbSQL.append(" and account.nStatusID in (" + qInfo.getAccountStatusIDs() + ")");
		}
		if (qInfo.getContractCode() != null && qInfo.getContractCode().length() > 0)
		{
			sbSQL.append(" and contractform.sContractCode ='" + qInfo.getContractCode() + "'");
		}
		if (qInfo.getLoanNoteNo() != null && qInfo.getLoanNoteNo().length() > 0)
		{
			sbSQL.append(" and loanpayform.sCode ='" + qInfo.getLoanNoteNo() + "'");
		}
		if (qInfo.getSubAccountID() > 0)
		{
			sbSQL.append(" and subaccount.ID=" + qInfo.getSubAccountID());
		}

		sbSQL.append(" union all \n");
		if(1 == qInfo.getIsIntrDate()){
			sbSQL.append(" select trans.NSUBACCOUNTID NSUBACCOUNTID,trans.NOPPACCOUNTID NOPPACCOUNTID,trans.nGroup GroupID,trans.sTransNo TransNo,trans.nTransactionTypeID TransTypeID,trans.dtExecute ExecuteDate,to_char(trans.dtInterestStart,'dd') ExecuteDay,to_char(trans.dtInterestStart,'mm') ExecuteMonth,to_char(trans.dtInterestStart,'yyyy') ExecuteYear,trans.sAbstract Abstract,trans.dtInterestStart InterestStartDate,2 Direction, \n");
		}
		else{
			sbSQL.append(" select trans.NSUBACCOUNTID NSUBACCOUNTID,trans.NOPPACCOUNTID NOPPACCOUNTID,trans.nGroup GroupID,trans.sTransNo TransNo,trans.nTransactionTypeID TransTypeID,trans.dtExecute ExecuteDate,to_char(trans.dtExecute,'dd') ExecuteDay,to_char(trans.dtExecute,'mm') ExecuteMonth,to_char(trans.dtExecute,'yyyy') ExecuteYear,trans.sAbstract Abstract,trans.dtInterestStart InterestStartDate,2 Direction, \n");
		}
		sbSQL.append(" decode(trans.nTransactionTypeID, \n");
		sbSQL.append(SETTConstant.TransactionType.BANKRECEIVE+",decode(trans.sBillNo,null,transCurrencyDeposit.SCONSIGNVOUCHERNO,trans.sBillNo), \n");
		sbSQL.append(SETTConstant.TransactionType.BANKPAY+",decode(trans.sBillNo,null,transCurrencyDeposit.SCONSIGNVOUCHERNO,trans.sBillNo), \n");
		sbSQL.append(SETTConstant.TransactionType.INTERNALVIREMENT+",decode(trans.sBillNo,null,transCurrencyDeposit.SCONSIGNVOUCHERNO,trans.sBillNo), \n");
		sbSQL.append(" subaccount.af_sdepositno) ||  \n");
		sbSQL.append(" decode(contractform.sContractCode,null,'',contractform.sContractCode || '(' || loanpayform.sCode || ')') BillNo, \n");
		sbSQL.append(" trans.sBankChequeNo BankChequeNo,0 PayAmount,trans.mAmount ReceiveAmount,account.nAccountTypeID AccountTypeID, \n");
		//为账户对账单信息查询 所加
		sbSQL.append(" trans.OPPACCOUNTNO OPPACCOUNTNO,trans.OPPACCOUNTNAME OPPACCOUNTNAME \n");
		//
		sbSQL.append(" from sett_TransAccountDetail trans,sett_Account account,sett_SubAccount subaccount,Loan_Contractform contractform,Loan_PayForm loanpayform, SETT_TRANSCURRENTDEPOSIT transCurrencyDeposit \n");
		sbSQL.append(" where subaccount.nAccountID = account.ID and trans.nSubAccountID = subaccount.ID and subaccount.AL_NLOANNOTEID = loanpayform.ID(+) and loanpayform.nContractID = contractform.ID(+) \n");
		sbSQL.append(" and trans.nStatusID=" + SETTConstant.TransactionStatus.CHECK + " and trans.nTransDirection = 2 \n");
		sbSQL.append(" and account.nCurrencyID=" + qInfo.getCurrencyID() + " \n");
		sbSQL.append(" and subaccount.nAccountID = " + qInfo.getAccountID() + " \n");
		sbSQL.append(" and transCurrencyDeposit.sTransNo(+) = trans.sTransNo \n");
		//sbSQL.append(" and transCurrencyDeposit.nStatusID = "+SETTConstant.TransactionStatus.CHECK+" \n");
		//sbSQL.append(" and transCurrencyDeposit.nCurrencyID = "+qInfo.getOfficeID()+" \n");
		//sbSQL.append(" and transCurrencyDeposit.nOfficeID = "+qInfo.getCurrencyID()+" \n");
		
		//Boxu Add 2008年12月10日 过滤交易方向为贷方，金额为"0"的交易，为了解决账户对账单-账户明细查询中出现贷方借方都为空的记录
		sbSQL.append(" and trans.mamount != 0 ");
		
		if(1 == qInfo.getIsIntrDate())
		{
			sbSQL.append(
					" and trans.dtintereststart between to_date('"
						+ DataFormat.formatDate(qInfo.getStartDate())
						+ "','yyyy-mm-dd') and to_date('"
						+ DataFormat.formatDate(qInfo.getEndDate())
						+ "','yyyy-mm-dd') \n");
		}
		else
		{
			sbSQL.append(
				" and trans.dtExecute between to_date('"
					+ DataFormat.formatDate(qInfo.getStartDate())
					+ "','yyyy-mm-dd') and to_date('"
					+ DataFormat.formatDate(qInfo.getEndDate())
					+ "','yyyy-mm-dd') \n");
		}
		if(!qInfo.getAccountStatusIDs().equals("")&&qInfo.getAccountStatusIDs() != null)
		{
			sbSQL.append(" and account.nStatusID in (" + qInfo.getAccountStatusIDs() + ")");
		}
		if (qInfo.getContractCode() != null && qInfo.getContractCode().length() > 0)
		{
			sbSQL.append(" and contractform.sContractCode ='" + qInfo.getContractCode() + "'");
		}
		if (qInfo.getLoanNoteNo() != null && qInfo.getLoanNoteNo().length() > 0)
		{
			sbSQL.append(" and loanpayform.sCode ='" + qInfo.getLoanNoteNo() + "'");
		}
		if (qInfo.getSubAccountID() > 0)
		{
			sbSQL.append(" and subaccount.ID=" + qInfo.getSubAccountID());
		}

		sbSQL.append(" ) \n");

		return sbSQL.toString();
		
	}
}
