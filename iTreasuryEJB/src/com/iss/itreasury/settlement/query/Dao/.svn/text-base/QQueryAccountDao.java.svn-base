package com.iss.itreasury.settlement.query.Dao;

import java.sql.Timestamp;

import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.query.paraminfo.QueryAccountAmountWhereInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryAccountWhereInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransAccountDetailWhereInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransactionConditionInfo;
import com.iss.itreasury.settlement.query.queryobj.BaseQueryObject;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;

public class QQueryAccountDao extends BaseQueryObject {
	
	public String queryAccountInfoSQL(QueryAccountWhereInfo qInfo){
		
		StringBuffer sbSQL= new StringBuffer();

		// select 
		sbSQL.append(" 		  select \n ");
		sbSQL.append("        distinct acct.nofficeID as OfficeID, acct.nCurrencyID CurrencyID,acct.ID AccountID, \n");
		sbSQL.append("        acct.sAccountNo as AccountNo, acct.sName as AccountName, acct.nClientID as ClientID, \n");
		sbSQL.append("        Client.SCode as ClientCode,Client.SQUERYPASSWORD as QueryPassWord, Client.sname ClientName,acct.nAccountTypeID as AccountTypeID, \n");
		sbSQL.append("        acct.dtOpen as OpenDate,acct.dtFinish  as ClearDate,acct.nstatusid as MainAccountStatusID, acct.sabstract, \n"); 		
		sbSQL.append("        b.balance,b.interest,b.availableBalance,b.IsNegotiate,b.limitamount,aa.ac_nInterestRatePlanID interestPlanID,1 isToday ,0.0 UncheckPaymentAmount \n");
		sbSQL.append("        ,ss.AC_NFIRSTLIMITTYPEID  firstLimitTypeId,");
		sbSQL.append("        decode(ss.AC_NFIRSTLIMITTYPEID,-1,0.00,ss.AC_MFIRSTLIMITAMOUNT) firstLimitAmount,");
		sbSQL.append("        ss.AC_NSECONDLIMITTYPEID  secondLimitTypeId,");
		sbSQL.append("        decode(ss.AC_NSECONDLIMITTYPEID,-1,0.00,ss.AC_MSECONDLIMITAMOUNT) secondLimitAmount,");
		sbSQL.append("        ss.AC_NTHIRDLIMITTYPEID   thirdLimitTypeId,");
		sbSQL.append("        decode(ss.AC_NTHIRDLIMITTYPEID,-1,0.00,ss.AC_MTHIRDLIMITAMOUNT) thirdLimitAmount,");
		sbSQL.append("        ss.AC_MCAPITALLIMITAMOUNT capitalLimitAmount");
		
		// from 
		sbSQL.append("        from \n");
		sbSQL.append("         sett_account acct, client,sett_subaccount ss, (select DISTINCT a.id,b.ac_nInterestRatePlanID from sett_account a,(select * from sett_subaccount where nstatusid >0) b where a.ID=b.naccountid(+)) aa, \n");
		sbSQL.append("              (select distinct a.naccountid,a.balance,a.interest,a.availableBalance,a.limitamount, aa.IsNegotiate \n");
		sbSQL.append("               from  (select sum(nvl(ac_nIsNegotiate,0)) IsNegotiate,naccountid from sett_subaccount group by naccountid) aa, \n");
		sbSQL.append("                    (select acc.id　naccountid, sum(round(nvl(subAcct.mbalance,0),2)) balance,sum(round(nvl(subAcct.ac_mcapitallimitamount,0),2)) limitamount,sum(round(nvl(decode(subAcct.minterest,0,subAcct.af_mpredrawinterest,subAcct.minterest),0),2)+round(nvl(subAcct.ac_mNegotiateInterest,0),2)) Interest, \n");
		sbSQL.append("                            sum(round(nvl(decode(subAcct.nstatusid,1,subAcct.mbalance-subAcct.MUNCHECKPAYMENTAMOUNT,5,subAcct.mbalance-subAcct.MUNCHECKPAYMENTAMOUNT,4,0,2,0,7,0,8,subAcct.mbalance-subAcct.MUNCHECKPAYMENTAMOUNT,0),0),2)) availableBalance \n");
		sbSQL.append("                     from sett_account acc,sett_subaccount subAcct \n");
		sbSQL.append("                     where acc.nofficeid=" + qInfo.getOfficeID() + " and acc.ncurrencyid=" + qInfo.getCurrencyID() + " and acc.id=subAcct.naccountid(+) \n");
		sbSQL.append("                    group by acc.id) a \n");
		sbSQL.append("               where a.naccountid=aa.naccountid(+) ) b \n");

		// where 
		sbSQL.append("        where \n");
		sbSQL.append("        b.nAccountId=aa.id  \n");
		sbSQL.append("        and ss.naccountid(+) = acct.id  \n");		
		sbSQL.append("        and acct.nclientid=client.id  \n");
		sbSQL.append("        and acct.nofficeid=" + qInfo.getOfficeID() + " and acct.nCurrencyID=" + qInfo.getCurrencyID() + " \n");
		sbSQL.append("        and acct.id=b.naccountid(+) and acct.nCheckStatusID=" + SETTConstant.AccountCheckStatus.CHECK + " \n");
		if (qInfo.getStartClientCode() != null && qInfo.getStartClientCode().length() > 0)
			sbSQL.append("        and client.scode>='" + qInfo.getStartClientCode() + "'");
		if (qInfo.getEndClientCode() != null && qInfo.getEndClientCode().length() > 0)
			sbSQL.append("        and client.scode<='" + qInfo.getEndClientCode() + "'");
		if (qInfo.getStartAccountNo() != null && qInfo.getStartAccountNo().length() > 0)
			sbSQL.append("        and acct.sAccountNo>='" + qInfo.getStartAccountNo() + "'");
		if (qInfo.getEndAccountNo() != null && qInfo.getEndAccountNo().length() > 0)
			sbSQL.append("        and acct.sAccountNo<='" + qInfo.getEndAccountNo() + "'");
		if (qInfo.getAccountTypeID() > 0)
			sbSQL.append("        and acct.naccounttypeid=" + qInfo.getAccountTypeID());
		else if(!"".equals(qInfo.getAccountTypeSet()))
		{
			sbSQL.append("        and acct.naccounttypeid  IN(" + qInfo.getAccountTypeSet()+")");
		}
		if (qInfo.getClientManager() > 0)
			sbSQL.append("        and client.ncustomermanageruserid=" + qInfo.getClientManager());
		if(qInfo.getOpenDateFrom()!=null)
		    sbSQL.append("		  and nvl(acct.dtOpen,to_date('9000-01-01','yyyy-mm-dd')) >= to_date('"+DataFormat.getDateString(qInfo.getOpenDateFrom())+"','yyyy-mm-dd')	");
		if(qInfo.getOpenDateTo()!=null)
		    sbSQL.append("		  and nvl(acct.dtOpen,to_date('9000-01-01','yyyy-mm-dd')) <= to_date('"+DataFormat.getDateString(qInfo.getOpenDateTo())+"','yyyy-mm-dd')	");
		if(qInfo.getFinishDateFrom()!=null)
		{
		    sbSQL.append("		  and nvl(acct.dtFinish,to_date('9000-01-01','yyyy-mm-dd')) >= to_date('"+DataFormat.getDateString(qInfo.getFinishDateFrom())+"','yyyy-mm-dd')	");
		    sbSQL.append("		  and acct.nstatusid="+SETTConstant.AccountStatus.CLOSE+"	");
		}
		if(qInfo.getFinishDateTo()!=null)
		{
		    sbSQL.append("		  and nvl(acct.dtFinish,to_date('9000-01-01','yyyy-mm-dd')) <= to_date('"+DataFormat.getDateString(qInfo.getFinishDateTo())+"','yyyy-mm-dd')	");
		    sbSQL.append("		  and acct.nstatusid="+SETTConstant.AccountStatus.CLOSE+"	");
		}
		
		System.out.println("select " +sbSQL.toString() + " from "+ sbSQL.toString() + " where "+sbSQL.toString() + sbSQL.toString());
	
		return sbSQL.toString();
	}
	
	
	public String queryAccountBalanceSQL(QueryAccountWhereInfo qInfo){
		
		StringBuffer sbSQL= new StringBuffer();
		
		// select 
		sbSQL.append(" 		  select \n");
		sbSQL.append("        distinct acct.nofficeID as OfficeID, acct.nCurrencyID CurrencyID,acct.ID AccountID,acct.sabstract, \n");
		sbSQL.append("        acct.sAccountNo as AccountNo, acct.sName as AccountName, acct.nClientID as ClientID, \n");
		sbSQL.append("        Client.SCode as ClientCode, Client.sname ClientName,Client.SQUERYPASSWORD as QueryPassWord,acct.nAccountTypeID as AccountTypeID, \n");
		sbSQL.append("        acct.dtOpen as OpenDate,acct.dtFinish  as ClearDate,acct.nstatusid as MainAccountStatusID, \n");
		sbSQL.append("        b.balance,b.interest,b.balance availableBalance,b.UncheckPaymentAmount,b.InterestPlanID,b.IsNegotiate,b.limitamount,1 isToday, \n");
		sbSQL.append("        -1 firstLimitTypeId,");
		sbSQL.append("        0 firstLimitAmount,");
		sbSQL.append("        -1 secondLimitTypeId,");
		sbSQL.append("        0 secondLimitAmount,");
		sbSQL.append("        -1 thirdLimitTypeId,");
		sbSQL.append("        0 thirdLimitAmount,");
		sbSQL.append("        0 capitalLimitAmount");
		// from 
		sbSQL.append(" 		  from \n");
		sbSQL.append("         sett_account acct, client, \n");
		sbSQL.append("              (select distinct a.naccountid,a.balance,a.interest,a.UncheckPaymentAmount,ac_nInterestRatePlanID InterestPlanID,sett_subaccount.NSTATUSID SubAccountStatus,sett_subaccount.ac_mcapitallimitamount limitamount,ac_nIsNegotiate IsNegotiate \n");
		sbSQL.append("               from  sett_subaccount, \n");
		sbSQL.append("               (select tt.naccountid,sum(round(nvl(tt.balance,0),2)) balance,sum(round(nvl(decode(tt.Interest,0,tt.af_mpredrawinterest,tt.Interest),0),2)) Interest, 0.0 UncheckPaymentAmount from \n");
		sbSQL.append("                    (select distinct daily.nsubaccountid  nsubaccoutnid, daily.naccountid naccountid, daily.mbalance balance,daily.minterest Interest, subAcct.af_mpredrawinterest af_mpredrawinterest \n");		
		sbSQL.append("                     from sett_account acct,sett_DailyAccountBalance daily,sett_subaccount subacct \n");
		sbSQL.append("                     where acct.nofficeid=" + qInfo.getOfficeID() + " and acct.ncurrencyid=" + qInfo.getCurrencyID() + " and acct.id=daily.naccountid \n");
		sbSQL.append("                       and daily.dtDate=to_date('" + DataFormat.getDateString(qInfo.getQueryDate()) + "','yyyy-mm-dd')  \n");
		sbSQL.append("                       and acct.id=subacct.naccountid  \n");
		sbSQL.append("                       and subacct.nstatusid in ("+SETTConstant.SubAccountStatus.NORMAL+","+SETTConstant.SubAccountStatus.FINISH+") \n");
		sbSQL.append("                       and daily.nsubaccountid = subacct.id \n");
		sbSQL.append("         ) tt  \n");
		sbSQL.append("                    group by tt.naccountid) a \n");
		sbSQL.append("               where a.naccountid=sett_subaccount.naccountid ) b \n");

		// where 
		sbSQL.append(" 		  where \n");
		sbSQL.append("        acct.nclientid=client.id  \n");
		sbSQL.append("        and acct.nofficeid=" + qInfo.getOfficeID() + " and acct.nCurrencyID=" + qInfo.getCurrencyID() + " \n");
		sbSQL.append("        and acct.id=b.naccountid(+) and acct.nCheckStatusID=" + SETTConstant.AccountCheckStatus.CHECK + " \n");
		if (qInfo.getStartClientCode() != null && qInfo.getStartClientCode().length() > 0)
			sbSQL.append("        and client.scode>='" + qInfo.getStartClientCode() + "'");
		if (qInfo.getEndClientCode() != null && qInfo.getEndClientCode().length() > 0)
			sbSQL.append("        and client.scode<='" + qInfo.getEndClientCode() + "'");
		if (qInfo.getStartAccountNo() != null && qInfo.getStartAccountNo().length() > 0)
			sbSQL.append("        and acct.sAccountNo>='" + qInfo.getStartAccountNo() + "'");
		if (qInfo.getEndAccountNo() != null && qInfo.getEndAccountNo().length() > 0)
			sbSQL.append("        and acct.sAccountNo<='" + qInfo.getEndAccountNo() + "'");
		if (qInfo.getAccountTypeID() > 0)
			sbSQL.append("        and acct.naccounttypeid=" + qInfo.getAccountTypeID());
		
		return sbSQL.toString();
	}

	public String queryTransactionSQL(QueryTransactionConditionInfo qInfo){
		
		StringBuffer sbSQL= new StringBuffer();
		

		sbSQL = new StringBuffer();
		// select 
		sbSQL.append(" select \n");
		sbSQL.append(" ID,SerialNo,OfficeID,CurrencyID,TransNo, \n");
		sbSQL.append(" TransactionTypeID,InterestStart,Execute,StatusID,InputuserID, \n");
		sbSQL.append(" CheckuserID,Abstract,PayclientID,PayaccountID,PayAmount, \n");
		sbSQL.append(" ReceiveAmount,ReceiveClientID,ReceiveAccountID,BankID,ContractID, \n");
		sbSQL.append(" LoanFormID,DepositNo,PayAccountNo,PayAccountName,ReceiveAccountNo, \n");
		sbSQL.append(" ReceiveAccountName,PayClientCode,PayClientName,ReceiveClientCode,ReceiveClientName, \n");
		sbSQL.append(" DeclarationNo,BankChequeNo,");
		sbSQL.append(" Operationtypeid,Source, \n");
		sbSQL.append(" PayBakAccountNo,ReceiveBakAccountNo, '"+qInfo.getPayAccountNoStart()+"' strAccountNo \n");
		// from 
		sbSQL.append(" from \n");
		sbSQL.append(" SETT_VTRANSACTION \n");
		// where 
		sbSQL.append(" where \n");
		if (qInfo.getQueryType() == 200)		
		{
			//当页面上点选了 查询已删除定期开立、通知开立交易 按钮功能时走此分支
			sbSQL.append(" StatusID =0 ");
			sbSQL.append(
					" and TransactionTypeID in ("
						+ SETTConstant.TransactionType.OPENFIXEDDEPOSIT				//定期开立
						+ ","
						+ SETTConstant.TransactionType.FIXEDCONTINUETRANSFER		//定期转存
						+ ","
						+ SETTConstant.TransactionType.OPENNOTIFYACCOUNT			//通知存款开立
						+ ")");
			System.out.println("执行的是定期，通知开立交易的记录!");
		}
		else
		{
			sbSQL.append(" StatusID>0 ");
		}
		
		if (qInfo.getOfficeID() > 0)
		{
			if (qInfo.getQueryType() == 100)
			{
				//从账户金额查询进入，默认查询通存通兑交易
				qInfo.setDifoffice(Constant.TRUE);
			}
			if(qInfo.getDifoffice()==Constant.TRUE)
			{//如果显示通存通兑交易
				//如果需要显示通存通兑交易，则需要增加不在本办事处发生的，但是与本办事处相关的交易
				//分两种情况
				//1.当前查询机构是总部：总部需要查出所有通存通兑交易
				//2.当前查询机构是分部：只能查出与本机构相关的通存通兑交易
				String sbOr = "";
				if(qInfo.getOfficeID()==Env.getHQOFFICEID())
				{//如果当前办事处是总部
					sbOr = "or (OfficeID!=" + qInfo.getOfficeID() + " and Isdifoffice = 1)";
				}
				else
				{//如果是分支机构
					sbOr = "or (OfficeID!=" + qInfo.getOfficeID() + " and Isdifoffice = 1 and (Payofficeid ="+qInfo.getOfficeID()+" or Receiveofficeid ="+qInfo.getOfficeID()+ "))";
				}
				sbSQL.append(" and (OfficeID=" + qInfo.getOfficeID() + sbOr+")");
			}
			else
			{
				sbSQL.append(" and OfficeID=" + qInfo.getOfficeID() + "");
			}
		}
			
		if (qInfo.getCurrencyID() > 0)
			sbSQL.append(" and CurrencyID=" + qInfo.getCurrencyID() + "");
		//选择了账户交易类型后，交易类型便无效
		if (qInfo.getAccountTransTypeID() > 0)
		{
		    String sqlTmp = "";
			switch ((int) qInfo.getAccountTransTypeID())
			{
				//活期存款：可以查询出所有使用活期存款账户的交易
				case (int) SETTConstant.AccountTransactionType.CURRENT_DEPOSIT :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
							+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.CURRENT;
					sbSQL.append(
						" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//定期存款：可以查询出和定期存款业务相关的交易。
				case (int) SETTConstant.AccountTransactionType.FIXED_DEPOSIT :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.FIXED;
				    sbSQL.append(
							" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//通知存款：可以查询出和通知存款业务相关的交易。
				case (int) SETTConstant.AccountTransactionType.NOTIFY_DEPOSIT :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.NOTIFY;
				    sbSQL.append(
							" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//信托贷款：可以查询出和信托贷款业务相关的交易。
				case (int) SETTConstant.AccountTransactionType.TRUST_LOAN :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.TRUST;
				    sbSQL.append(
							" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//委托贷款：可以查询出和委托贷款业务相关的交易。
				case (int) SETTConstant.AccountTransactionType.CONSIGN_LOAN :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.CONSIGN;
				    sbSQL.append(
							" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//贴现：可以查询出和贴现业务相关的交易。
				case (int) SETTConstant.AccountTransactionType.DISCOUNT_LOAN :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.DISCOUNT;
				    sbSQL.append(
							" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					// 短期贷款：可以查询出和短期贷款业务相关的交易。
					//循环贷款：可以查询出和循环贷款业务相关的交易。
					//委托业务：可以查询出委托业务菜单下所有的交易。
					//对外付款：可以查询银行付款、支票付款、现金付款、票汇付款业务。
				case (int) SETTConstant.AccountTransactionType.OUT_PAYMENT :
					sbSQL.append(
						" and TransactionTypeID in ("
							+ SETTConstant.TransactionType.BANKPAY
							+ ","
							+ SETTConstant.TransactionType.DRAFTPAY
							+ ","
							+ SETTConstant.TransactionType.CASHPAY
							+ ","
							+ SETTConstant.TransactionType.CHECKPAY
							+ ")");
					break;
			}
		}
		else
		{
			sbSQL.append(" and TransactionTypeID<>" + SETTConstant.TransactionType.CHANGECERTIFICATE + "");
			if (qInfo.getTransactionTypeIDs().length() > 0) 
				sbSQL.append(" and ( TransactionTypeID in (" + qInfo.getTransactionTypeIDs() + ") or OperationTypeID in (" + qInfo.getTransactionTypeIDs() + ") )");
		}
		if (qInfo.getSource() > 0)
			sbSQL.append(" and source=" + qInfo.getSource() + "");
		if (qInfo.getBankID() > 0)
			sbSQL.append(" and BankID=" + qInfo.getBankID() + "");
		if (qInfo.getInputuserID() != -1)
			sbSQL.append(" and InputuserID=" + qInfo.getInputuserID() + "");
		if (qInfo.getCheckuserID() != -1)
			sbSQL.append(" and CheckuserID=" + qInfo.getCheckuserID() + "");
		if (qInfo.getStatusID() > 0)
			sbSQL.append(" and StatusID=" + qInfo.getStatusID() + "");
		if (qInfo.getPayClientIDStart() > 0 && (qInfo.getPayClientNoStart() == null || qInfo.getPayClientNoStart().trim().length()<=0))
			sbSQL.append(" and PayclientID>=" + qInfo.getPayClientIDStart() + "");
		if (qInfo.getPayClientIDEnd() > 0 && (qInfo.getPayClientNoEnd() == null || qInfo.getPayClientNoEnd().trim().length()<=0))
			sbSQL.append(" and PayclientID<=" + qInfo.getPayClientIDEnd() + "");
		if (qInfo.getPayAccountIDStart() > 0 && (qInfo.getPayAccountNoStart() == null || qInfo.getPayAccountNoStart().trim().length()<=0))
			sbSQL.append(" and PayaccountID>=" + qInfo.getPayAccountIDStart() + "");
		if (qInfo.getPayAccountIDEnd() > 0 && (qInfo.getPayAccountNoEnd() == null || qInfo.getPayAccountNoEnd().trim().length()<=0))
			sbSQL.append(" and PayaccountID<=" + qInfo.getPayAccountIDEnd() + "");
		if (qInfo.getReceiveClientIDStart() > 0 && (qInfo.getReceiveClientNoStart() == null || qInfo.getReceiveClientNoStart().trim().length()<=0))
			sbSQL.append(" and ReceiveClientID>=" + qInfo.getReceiveClientIDStart() + "");
		if (qInfo.getReceiveClientIDEnd() > 0 && (qInfo.getReceiveClientNoEnd() == null || qInfo.getReceiveClientNoEnd().trim().length()<=0))
			sbSQL.append(" and ReceiveClientID<=" + qInfo.getReceiveClientIDEnd() + "");
		if (qInfo.getReceiveAccountIDStart() > 0 && (qInfo.getReceiveAccountNoStart() == null || qInfo.getReceiveAccountNoStart().trim().length()<=0))
			sbSQL.append(" and ReceiveAccountID>=" + qInfo.getReceiveAccountIDStart() + "");
		if (qInfo.getReceiveAccountIDEnd() > 0 && (qInfo.getReceiveAccountNoEnd() == null || qInfo.getReceiveAccountNoEnd().trim().length()<=0))
			sbSQL.append(" and ReceiveAccountID<=" + qInfo.getReceiveAccountIDEnd() + "");
		if (qInfo.getContractID() > 0)
			sbSQL.append(" and ContractID=" + qInfo.getContractID() + "");
		if (qInfo.getPayFormID() > 0)
			sbSQL.append(" and LoanFormID=" + qInfo.getPayFormID() + "");
		if (!"blank".equals(qInfo.getPayMoneyStartBlank()))
			sbSQL.append(" and PayAmount>=" + qInfo.getPayAmountStart() + "");
		if (!"blank".equals(qInfo.getPayMoneyEndBlank()))
			sbSQL.append(" and PayAmount<=" + qInfo.getPayAmountEnd() + "");
		if (!"blank".equals(qInfo.getReceiveMoneyStartBlank()))
			sbSQL.append(" and ReceiveAmount>=" + qInfo.getReceiveAmountStart() + "");
		if (!"blank".equals(qInfo.getReceiveMoneyEndBlank()))
			sbSQL.append(" and ReceiveAmount<=" + qInfo.getReceiveAmountEnd() + "");
		if (qInfo.getQueryType() == 100)
		{
			//从账户金额查询进入，逻辑是，收付账户是“或”的关系
			if ((qInfo.getPayAccountNoStart() != null && qInfo.getPayAccountNoStart().trim().length() > 0)
				&& (qInfo.getReceiveAccountNoStart() != null && qInfo.getReceiveAccountNoStart().trim().length() > 0))
			{
				sbSQL.append(" and (PayAccountNo='" + qInfo.getPayAccountNoStart() + "' or ReceiveAccountNo='" + qInfo.getReceiveAccountNoStart() + "'");
				sbSQL.append(" or  paybakaccountno='" + qInfo.getPayAccountNoStart() + "' or receivebakaccountno='" + qInfo.getReceiveAccountNoStart() + "')");
			}
		}
		else
		{
			if (qInfo.getPayClientNoStart() != null && qInfo.getPayClientNoStart().trim().length() > 0
					&& (qInfo.getPayAccountNoStart() == null || qInfo.getPayAccountNoStart().trim().length()<=0))
				sbSQL.append(" and PayClientCode>='" + qInfo.getPayClientNoStart() + "'");
			if (qInfo.getPayClientNoEnd() != null && qInfo.getPayClientNoEnd().trim().length() > 0
				 	&& (qInfo.getPayAccountNoEnd() == null || qInfo.getPayAccountNoEnd().trim().length()<=0))
				sbSQL.append(" and PayClientCode<='" + qInfo.getPayClientNoEnd() + "'");
			if (qInfo.getPayAccountNoStart() != null && qInfo.getPayAccountNoStart().trim().length() > 0)
				sbSQL.append(" and PayAccountNo>='" + qInfo.getPayAccountNoStart() + "'");
			if (qInfo.getPayAccountNoEnd() != null && qInfo.getPayAccountNoEnd().trim().length() > 0)
				sbSQL.append(" and PayAccountNo<='" + qInfo.getPayAccountNoEnd() + "'");
			if(qInfo.getPayAppointAccountNo() != null && qInfo.getPayAppointAccountNo().length() > 0){
				sbSQL.append(" and PayAccountNo in ('"+qInfo.getPayAppointAccountNo()+"')");
			}
			if (qInfo.getReceiveClientNoStart() != null && qInfo.getReceiveClientNoStart().trim().length() > 0
					&& (qInfo.getReceiveAccountNoStart() == null || qInfo.getReceiveAccountNoStart().trim().length()<=0))
				sbSQL.append(" and ReceiveClientCode>='" + qInfo.getReceiveClientNoStart() + "'");
			if (qInfo.getReceiveClientNoEnd() != null && qInfo.getReceiveClientNoEnd().trim().length() > 0
					&& (qInfo.getReceiveAccountNoEnd() == null || qInfo.getReceiveAccountNoEnd().trim().length()<=0))
				sbSQL.append(" and ReceiveClientCode<='" + qInfo.getReceiveClientNoEnd() + "'");
			if (qInfo.getReceiveAccountNoStart() != null && qInfo.getReceiveAccountNoStart().trim().length() > 0)
				sbSQL.append(" and ReceiveAccountNo>='" + qInfo.getReceiveAccountNoStart() + "'");
			if (qInfo.getReceiveAccountNoEnd() != null && qInfo.getReceiveAccountNoEnd().trim().length() > 0)
				sbSQL.append(" and ReceiveAccountNo<='" + qInfo.getReceiveAccountNoEnd() + "'");
			if(qInfo.getReceiveAppointAccountNo() != null && qInfo.getReceiveAppointAccountNo().length() > 0){
				sbSQL.append(" and ReceiveAccountNo in ('"+qInfo.getReceiveAppointAccountNo()+"')");
			}
		}
		if (qInfo.getApplyCode() != null && qInfo.getApplyCode().trim().length() > 0)
			sbSQL.append(" and applycode like '%" + qInfo.getApplyCode() + "%'");
		if (qInfo.getTransNoStart() != null && qInfo.getTransNoStart().trim().length() > 0)
			sbSQL.append(" and TransNo>='" + qInfo.getTransNoStart() + "'");
		if (qInfo.getTransNoEnd() != null && qInfo.getTransNoEnd().trim().length() > 0)
			sbSQL.append(" and TransNo<='" + qInfo.getTransNoEnd() + "'");
		if (qInfo.getDepositNo() != null && qInfo.getDepositNo().trim().length() > 0)
			sbSQL.append(" and DepositNo='" + qInfo.getDepositNo() + "'");
		if (qInfo.getInterestStartStart() != null)
			sbSQL.append(" and InterestStart>=to_date('" + DataFormat.getDateString(qInfo.getInterestStartStart()) + "','yyyy-mm-dd')");
		if (qInfo.getInterestStartEnd() != null)
			sbSQL.append(" and InterestStart<=to_date('" + DataFormat.getDateString(qInfo.getInterestStartEnd()) + "','yyyy-mm-dd')");
		if (qInfo.getExecuteStart() != null)
			sbSQL.append(" and Execute>=to_date('" + DataFormat.getDateString(qInfo.getExecuteStart()) + "','yyyy-mm-dd')");
		if (qInfo.getExecuteEnd() != null)
			sbSQL.append(" and Execute<=to_date('" + DataFormat.getDateString(qInfo.getExecuteEnd()) + "','yyyy-mm-dd')");
		if(qInfo.getBankChequeNO() != null && !"".equals(qInfo.getBankChequeNO().trim()))
		{
			sbSQL.append(" and bankchequeno ='" + qInfo.getBankChequeNO().trim()+"'");
		}
		if(qInfo.getDeclarationNO() != null && !"".equals(qInfo.getDeclarationNO().trim()))
		{
			sbSQL.append(" and declarationno ='" + qInfo.getDeclarationNO().trim()+"'");
		}
		
		int index=qInfo.getTransactionTypeIDs().indexOf(""+SETTConstant.TransactionType.OPENFIXEDDEPOSIT);
		if(index >= 0 || qInfo.getTransactionTypeIDs()==""){
		
			sbSQL.append(" and StatusID <> "+SETTConstant.TransactionStatus.WAITAPPROVAL);
		
		}
		if(qInfo.getAbstract() != null && !"".equals(qInfo.getAbstract().trim()))
		{
			sbSQL.append(" and abstract like '%" + qInfo.getAbstract().trim()+"%'");
		}
		if(qInfo.getSigner()>0)
		{
			sbSQL.append(" and nvl(PayclientID,0) not in (select nclientid from sett_signature where NISSIGNATURE=1 ) ");
			sbSQL.append(" and nvl(ReceiveClientID,0) not in (select nclientid from sett_signature where NISSIGNATURE=1 ) ");
		}
		if(index >= 0 || qInfo.getTransactionTypeIDs()==""){
			sbSQL.append(" and StatusID <> "+SETTConstant.TransactionStatus.WAITAPPROVAL);
		}
		if (qInfo.getBankID() > 0)
		{
			StringBuffer strSQL = new StringBuffer();
			strSQL = getTransactionSQLForQueryTSYW(qInfo);
			sbSQL.append(" Union ");
			sbSQL.append(strSQL);
		}
		return sbSQL.toString(); 
	}
	
	public StringBuffer getTransactionSQLForQueryTSYW(QueryTransactionConditionInfo qInfo){

		
		StringBuffer sbSQL = new StringBuffer();
		// select 
		sbSQL.append(" select /n");
		sbSQL.append(" t1.ID,SerialNo,OfficeID,CurrencyID,TransNo, \n");
		sbSQL.append(" TransactionTypeID,InterestStart,Execute,StatusID,InputuserID, \n");
		sbSQL.append(" CheckuserID,Abstract,PayclientID,PayaccountID,PayAmount, \n");
		sbSQL.append(" ReceiveAmount,ReceiveClientID,ReceiveAccountID,BankID,ContractID, \n");
		sbSQL.append(" LoanFormID,DepositNo,PayAccountNo,PayAccountName,ReceiveAccountNo, \n");
		sbSQL.append(" ReceiveAccountName,PayClientCode,PayClientName,ReceiveClientCode,ReceiveClientName, \n");
		sbSQL.append(" DeclarationNo,BankChequeNo,");
		sbSQL.append(" Operationtypeid,Source,PayBakAccountNo,ReceiveBakAccountNo , '"+qInfo.getPayAccountNoStart()+"' strAccountNo \n");
		// from 
		sbSQL.append(" from /n");
		sbSQL.append(" SETT_VTRANSACTION t1,Sett_TransSpecialOperation t2 \n");
		// where 
		sbSQL.append(" where /n");
		if (qInfo.getQueryType() == 200)		
		{
			//当页面上点选了 查询已删除定期开立、通知开立交易 按钮功能时走此分支
			sbSQL.append(" t1.StatusID =0 ");
			sbSQL.append(
					" and t1.TransactionTypeID in ("
						+ SETTConstant.TransactionType.OPENFIXEDDEPOSIT				//定期开立
						+ ","
						+ SETTConstant.TransactionType.FIXEDCONTINUETRANSFER		//定期转存
						+ ","
						+ SETTConstant.TransactionType.OPENNOTIFYACCOUNT			//通知存款开立
						+ ")");
			System.out.println("执行的是定期，通知开立交易的记录!");
		}
		else
		{
			sbSQL.append(" t1.StatusID>0 ");
		}
		sbSQL.append(" and t1.transno = t2.stransno ");
		if (qInfo.getOfficeID() > 0)
			sbSQL.append(" and t1.OfficeID=" + qInfo.getOfficeID() + "");
		if (qInfo.getCurrencyID() > 0)
			sbSQL.append(" and t1.CurrencyID=" + qInfo.getCurrencyID() + "");
		//选择了账户交易类型后，交易类型便无效
		if (qInfo.getAccountTransTypeID() > 0)
		{
		    String sqlTmp = "";
			switch ((int) qInfo.getAccountTransTypeID())
			{
				//活期存款：可以查询出所有使用活期存款账户的交易
				case (int) SETTConstant.AccountTransactionType.CURRENT_DEPOSIT :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
							+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.CURRENT;
					sbSQL.append(
						" and (t1.PayAccountID in (" + sqlTmp + ") or t1.ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//定期存款：可以查询出和定期存款业务相关的交易。
				case (int) SETTConstant.AccountTransactionType.FIXED_DEPOSIT :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.FIXED;
				    sbSQL.append(
							" and (t1.PayAccountID in (" + sqlTmp + ") or t1.ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//通知存款：可以查询出和通知存款业务相关的交易。
				case (int) SETTConstant.AccountTransactionType.NOTIFY_DEPOSIT :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.NOTIFY;
				    sbSQL.append(
							" and (t1.PayAccountID in (" + sqlTmp + ") or t1.ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//信托贷款：可以查询出和信托贷款业务相关的交易。
				case (int) SETTConstant.AccountTransactionType.TRUST_LOAN :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.TRUST;
				    sbSQL.append(
							" and (t1.PayAccountID in (" + sqlTmp + ") or t1.ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//委托贷款：可以查询出和委托贷款业务相关的交易。
				case (int) SETTConstant.AccountTransactionType.CONSIGN_LOAN :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.CONSIGN;
				    sbSQL.append(
							" and (t1.PayAccountID in (" + sqlTmp + ") or t1.ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//贴现：可以查询出和贴现业务相关的交易。
				case (int) SETTConstant.AccountTransactionType.DISCOUNT_LOAN :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.DISCOUNT;
				    sbSQL.append(
							" and (t1.PayAccountID in (" + sqlTmp + ") or t1.ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					// 短期贷款：可以查询出和短期贷款业务相关的交易。
					//循环贷款：可以查询出和循环贷款业务相关的交易。
					//委托业务：可以查询出委托业务菜单下所有的交易。
					//对外付款：可以查询银行付款、支票付款、现金付款、票汇付款业务。
				case (int) SETTConstant.AccountTransactionType.OUT_PAYMENT :
					sbSQL.append(
						" and t1.TransactionTypeID in ("
							+ SETTConstant.TransactionType.BANKPAY
							+ ","
							+ SETTConstant.TransactionType.DRAFTPAY
							+ ","
							+ SETTConstant.TransactionType.CASHPAY
							+ ","
							+ SETTConstant.TransactionType.CHECKPAY
							+ ")");
					break;
			}
		}
		else
		{
			sbSQL.append(" and t1.TransactionTypeID<>" + SETTConstant.TransactionType.CHANGECERTIFICATE + "");
			if (qInfo.getTransactionTypeIDs().length() > 0) 
				sbSQL.append(" and ( t1.TransactionTypeID in (" + qInfo.getTransactionTypeIDs() + ") or t1.OperationTypeID in (" + qInfo.getTransactionTypeIDs() + ") )");
		}
		if (qInfo.getSource() > 0)
			sbSQL.append(" and t1.source=" + qInfo.getSource() + "");
		if (qInfo.getBankID() > 0)
			sbSQL.append(" and (t2.npaybankid="+ qInfo.getBankID() +" or t2.NRECEIVEBANKID="+ qInfo.getBankID() +" or t2.sextaccountno = (select sbankaccountcode from sett_branch where id = "+qInfo.getBankID()+"))");
		if (qInfo.getInputuserID() != -1)
			sbSQL.append(" and t1.InputuserID=" + qInfo.getInputuserID() + "");
		if (qInfo.getCheckuserID() != -1)
			sbSQL.append(" and t1.CheckuserID=" + qInfo.getCheckuserID() + "");
		if (qInfo.getStatusID() > 0)
			sbSQL.append(" and t1.StatusID=" + qInfo.getStatusID() + "");
		if (qInfo.getPayClientIDStart() > 0 && (qInfo.getPayClientNoStart() == null || qInfo.getPayClientNoStart().trim().length()<=0))
			sbSQL.append(" and t1.PayclientID>=" + qInfo.getPayClientIDStart() + "");
		if (qInfo.getPayClientIDEnd() > 0 && (qInfo.getPayClientNoEnd() == null || qInfo.getPayClientNoEnd().trim().length()<=0))
			sbSQL.append(" and t1.PayclientID<=" + qInfo.getPayClientIDEnd() + "");
		if (qInfo.getPayAccountIDStart() > 0 && (qInfo.getPayAccountNoStart() == null || qInfo.getPayAccountNoStart().trim().length()<=0))
			sbSQL.append(" and t1.PayaccountID>=" + qInfo.getPayAccountIDStart() + "");
		if (qInfo.getPayAccountIDEnd() > 0 && (qInfo.getPayAccountNoEnd() == null || qInfo.getPayAccountNoEnd().trim().length()<=0))
			sbSQL.append(" and t1.PayaccountID<=" + qInfo.getPayAccountIDEnd() + "");
		if (qInfo.getReceiveClientIDStart() > 0 && (qInfo.getReceiveClientNoStart() == null || qInfo.getReceiveClientNoStart().trim().length()<=0))
			sbSQL.append(" and t1.ReceiveClientID>=" + qInfo.getReceiveClientIDStart() + "");
		if (qInfo.getReceiveClientIDEnd() > 0 && (qInfo.getReceiveClientNoEnd() == null || qInfo.getReceiveClientNoEnd().trim().length()<=0))
			sbSQL.append(" and t1.ReceiveClientID<=" + qInfo.getReceiveClientIDEnd() + "");
		if (qInfo.getReceiveAccountIDStart() > 0 && (qInfo.getReceiveAccountNoStart() == null || qInfo.getReceiveAccountNoStart().trim().length()<=0))
			sbSQL.append(" and t1.ReceiveAccountID>=" + qInfo.getReceiveAccountIDStart() + "");
		if (qInfo.getReceiveAccountIDEnd() > 0 && (qInfo.getReceiveAccountNoEnd() == null || qInfo.getReceiveAccountNoEnd().trim().length()<=0))
			sbSQL.append(" and t1.ReceiveAccountID<=" + qInfo.getReceiveAccountIDEnd() + "");
		if (qInfo.getContractID() > 0)
			sbSQL.append(" and t1.ContractID=" + qInfo.getContractID() + "");
		if (qInfo.getPayFormID() > 0)
			sbSQL.append(" and t1.LoanFormID=" + qInfo.getPayFormID() + "");
		if (!"blank".equals(qInfo.getPayMoneyStartBlank()))
			sbSQL.append(" and t1.PayAmount>=" + qInfo.getPayAmountStart() + "");
		if (!"blank".equals(qInfo.getPayMoneyEndBlank()))
			sbSQL.append(" and t1.PayAmount<=" + qInfo.getPayAmountEnd() + "");
		if (!"blank".equals(qInfo.getReceiveMoneyStartBlank()))
			sbSQL.append(" and t1.ReceiveAmount>=" + qInfo.getReceiveAmountStart() + "");
		if (!"blank".equals(qInfo.getReceiveMoneyEndBlank()))
			sbSQL.append(" and t1.ReceiveAmount<=" + qInfo.getReceiveAmountEnd() + "");
		if (qInfo.getQueryType() == 100)
		{
			//从账户金额查询进入，逻辑是，收付账户是“或”的关系
			if ((qInfo.getPayAccountNoStart() != null && qInfo.getPayAccountNoStart().trim().length() > 0)
				&& (qInfo.getReceiveAccountNoStart() != null && qInfo.getReceiveAccountNoStart().trim().length() > 0))
			{
				sbSQL.append(" and (t1.PayAccountNo='" + qInfo.getPayAccountNoStart() + "' or t1.ReceiveAccountNo='" + qInfo.getReceiveAccountNoStart() + "')");
			}
		}
		else
		{
			if (qInfo.getPayClientNoStart() != null && qInfo.getPayClientNoStart().trim().length() > 0
					&& (qInfo.getPayAccountNoStart() == null || qInfo.getPayAccountNoStart().trim().length()<=0))
				sbSQL.append(" and t1.PayClientCode>='" + qInfo.getPayClientNoStart() + "'");
			if (qInfo.getPayClientNoEnd() != null && qInfo.getPayClientNoEnd().trim().length() > 0
					&& (qInfo.getPayAccountNoEnd() == null || qInfo.getPayAccountNoEnd().trim().length()<=0))
				sbSQL.append(" and t1.PayClientCode<='" + qInfo.getPayClientNoEnd() + "'");
			if (qInfo.getPayAccountNoStart() != null && qInfo.getPayAccountNoStart().trim().length() > 0)
				sbSQL.append(" and t1.PayAccountNo>='" + qInfo.getPayAccountNoStart() + "'");
			if (qInfo.getPayAccountNoEnd() != null && qInfo.getPayAccountNoEnd().trim().length() > 0)
				sbSQL.append(" and t1.PayAccountNo<='" + qInfo.getPayAccountNoEnd() + "'");
			if(qInfo.getPayAppointAccountNo() != null && qInfo.getPayAppointAccountNo().length() > 0){
				sbSQL.append(" and PayAccountNo in ('"+qInfo.getPayAppointAccountNo()+"')");
			}
			if (qInfo.getReceiveClientNoStart() != null && qInfo.getReceiveClientNoStart().trim().length() > 0
					&& (qInfo.getReceiveAccountNoStart() == null || qInfo.getReceiveAccountNoStart().trim().length()<=0))
				sbSQL.append(" and t1.ReceiveClientCode>='" + qInfo.getReceiveClientNoStart() + "'");
			if (qInfo.getReceiveClientNoEnd() != null && qInfo.getReceiveClientNoEnd().trim().length() > 0
					&& (qInfo.getReceiveAccountNoEnd() == null || qInfo.getReceiveAccountNoEnd().trim().length()<=0))
				sbSQL.append(" and t1.ReceiveClientCode<='" + qInfo.getReceiveClientNoEnd() + "'");
			if (qInfo.getReceiveAccountNoStart() != null && qInfo.getReceiveAccountNoStart().trim().length() > 0)
				sbSQL.append(" and t1.ReceiveAccountNo>='" + qInfo.getReceiveAccountNoStart() + "'");
			if (qInfo.getReceiveAccountNoEnd() != null && qInfo.getReceiveAccountNoEnd().trim().length() > 0)
				sbSQL.append(" and t1.ReceiveAccountNo<='" + qInfo.getReceiveAccountNoEnd() + "'");
			if(qInfo.getReceiveAppointAccountNo() != null && qInfo.getReceiveAppointAccountNo().length() > 0){
				sbSQL.append(" and ReceiveAccountNo in ('"+qInfo.getReceiveAppointAccountNo()+"')");
			}
		}
		if (qInfo.getApplyCode() != null && qInfo.getApplyCode().trim().length() > 0)
			sbSQL.append(" and t1.applycode like '%" + qInfo.getApplyCode() + "%'");
		if (qInfo.getTransNoStart() != null && qInfo.getTransNoStart().trim().length() > 0)
			sbSQL.append(" and t1.TransNo>='" + qInfo.getTransNoStart() + "'");
		if (qInfo.getTransNoEnd() != null && qInfo.getTransNoEnd().trim().length() > 0)
			sbSQL.append(" and t1.TransNo<='" + qInfo.getTransNoEnd() + "'");
		if (qInfo.getDepositNo() != null && qInfo.getDepositNo().trim().length() > 0)
			sbSQL.append(" and t1.DepositNo='" + qInfo.getDepositNo() + "'");
		if (qInfo.getInterestStartStart() != null)
			sbSQL.append(" and t1.InterestStart>=to_date('" + DataFormat.getDateString(qInfo.getInterestStartStart()) + "','yyyy-mm-dd')");
		if (qInfo.getInterestStartEnd() != null)
			sbSQL.append(" and t1.InterestStart<=to_date('" + DataFormat.getDateString(qInfo.getInterestStartEnd()) + "','yyyy-mm-dd')");
		if (qInfo.getExecuteStart() != null)
			sbSQL.append(" and t1.Execute>=to_date('" + DataFormat.getDateString(qInfo.getExecuteStart()) + "','yyyy-mm-dd')");
		if (qInfo.getExecuteEnd() != null)
			sbSQL.append(" and t1.Execute<=to_date('" + DataFormat.getDateString(qInfo.getExecuteEnd()) + "','yyyy-mm-dd')");
		if(qInfo.getBankChequeNO() != null && !"".equals(qInfo.getBankChequeNO().trim()))
		{
			sbSQL.append(" and t1.bankchequeno ='" + qInfo.getBankChequeNO().trim()+"'");
		}
		if(qInfo.getDeclarationNO() != null && !"".equals(qInfo.getDeclarationNO().trim()))
		{
			sbSQL.append(" and t1.declarationno ='" + qInfo.getDeclarationNO().trim()+"'");
		}
		int index=qInfo.getTransactionTypeIDs().indexOf(""+SETTConstant.TransactionType.OPENFIXEDDEPOSIT);
		if(index >= 0 || qInfo.getTransactionTypeIDs()==""){
			sbSQL.append(" and t1.StatusID <> "+SETTConstant.TransactionStatus.WAITAPPROVAL);
		}
		if(qInfo.getAbstract() != null && !"".equals(qInfo.getAbstract().trim()))
		{
			sbSQL.append(" and abstract like '%" + qInfo.getAbstract().trim()+"%'");
		}
		if(qInfo.getSigner()>0)
		{
			sbSQL.append(" and nvl(PayclientID,0) not in (select nclientid from sett_signature where NISSIGNATURE=1 ) ");
			sbSQL.append(" and nvl(ReceiveClientID,0) not in (select nclientid from sett_signature where NISSIGNATURE=1 ) ");
		}
		if(index >= 0 || qInfo.getTransactionTypeIDs()==""){
			sbSQL.append(" and StatusID <> "+SETTConstant.TransactionStatus.WAITAPPROVAL);
		}
		
		return sbSQL;
	
	}

	public String queryAccountAmountInfoSQL(QueryAccountAmountWhereInfo qInfo){
		
		StringBuffer sbSQL= new StringBuffer();
		

        StringBuffer bufTmp = new StringBuffer();
        StringBuffer bufTemp = new StringBuffer();
        StringBuffer bufFromTemp = new StringBuffer();
        
        // create select
        sbSQL.append("select \n");
        sbSQL.append("\n bbb.ID AccountID,bbb.sAccountNo AccountNo, \n");
        if (qInfo.getIsCheckedType() == 3 || qInfo.getIsCheckedType() == 6) {
            sbSQL.append("bbb.nAccountTypeID AccountTypeID, \n");
        }
        sbSQL.append("ccc.sName Name, \n");
		sbSQL.append("NVL(aaa.mStartBalance,0.0) StartBalance, \n");
		sbSQL.append("NVL(aaa.mPayAmount,0.0) PayAmount, \n");
        sbSQL.append("NVL(aaa.mRecAmount,0.0) RecAmount, \n");
		sbSQL.append("NVL(aaa.mStartBalance,0.0)+ \n");
		sbSQL.append("NVL(aaa.mRecAmount,0.0)- \n");
		sbSQL.append("NVL(aaa.mPayAmount, 0.0) EndBalance \n");

        // create from 
        sbSQL.append("from \n");
        sbSQL.append("(SELECT dd.nAccountID, \n");
		bufTmp.append("NVL(dd.mStartBalance,0.0) mStartBalance, \n");
		bufTmp.append("NVL(cc.mPayAmount,0.0) mPayAmount,"+
            "NVL(cc.mRecAmount,0.0) mRecAmount FROM ( \n");
		bufTmp.append("    SELECT bb.nTransAccountID,"+
            "aa.mPayAmount,bb.mRecAmount FROM \n");
        bufFromTemp.append("        (SELECT nTransAccountID,"+
            "nTransDirection,SUM(mAmount) mPayAmount \n");
        bufTemp.append("        FROM sett_TransAccountDetail a, "+
            "sett_account b \n");
        bufTemp.append("        WHERE a.nTransAccountID = b.ID \n");
		bufTemp.append("        	AND a.nStatusID = "+
			SETTConstant.TransactionStatus.CHECK+" \n");
            
		if (!(qInfo.getStartQueryDate() == null || qInfo.getStartQueryDate().toString().trim().length() == 0))
      	{ 
			bufTemp.append(" AND a.dtExecute >= to_date('" + DataFormat.getDateString(qInfo.getStartQueryDate()) + "','yyyy-mm-dd')   \n");
       	}
       	if (!(qInfo.getEndQueryDate() == null || qInfo.getEndQueryDate().toString().trim().length() == 0))
      	{
            bufTemp.append(" AND a.dtExecute <= to_date('" + DataFormat.getDateString(qInfo.getEndQueryDate()) + "','yyyy-mm-dd')   \n");
       	}
                
        bufFromTemp.append(bufTemp.toString());
        bufFromTemp.append(this.setFrom(qInfo, SETTConstant.DebitOrCredit.DEBIT));
        bufFromTemp.append("            GROUP BY nTransAccountID,"+
            "nTransDirection) aa, \n");
        bufFromTemp.append("        (SELECT nTransAccountID,"+
            "nTransDirection,SUM(mAmount) mRecAmount \n");
        bufFromTemp.append(bufTemp.toString());
        bufFromTemp.append(this.setFrom(qInfo, SETTConstant.DebitOrCredit.CREDIT));
        bufFromTemp.append("            GROUP BY  nTransAccountID,"+
            "nTransDirection) bb \n");
		bufTmp.append(bufFromTemp.toString());
		bufTmp.append("        WHERE aa.nTransAccountID(+) = "+
            "bb.nTransAccountID \n");
		bufTmp.append("        UNION \n");
		bufTmp.append("    SELECT aa.nTransAccountID,"+
			"aa.mPayAmount,bb.mRecAmount FROM \n");
		bufTmp.append(bufFromTemp.toString());
		bufTmp.append("        WHERE aa.nTransAccountID = "+
            "bb.nTransAccountID(+)) cc, \n");
		bufTmp.append("        (SELECT SUM(mBalance) mStartBalance,"+
            "nAccountID FROM sett_DailyAccountBalance \n");
        if (!(qInfo.getStartQueryDate() == null || 
            qInfo.getStartQueryDate().toString().trim().length() == 0))
			bufTmp.append("        WHERE dtDate = TO_DATE('" + 
            	DataFormat.getDateString(DataFormat.
                getPreviousDate(qInfo.getStartQueryDate())) + 
                "','yyyy-mm-dd') \n");
        bufTmp.append("        GROUP BY nAccountID) dd \n");
		sbSQL.append(bufTmp.toString());
        sbSQL.append("    WHERE  cc.nTransAccountID(+) = dd.nAccountID \n");
		sbSQL.append("UNION \n");
		sbSQL.append("SELECT cc.nTransAccountID, \n");
		sbSQL.append(bufTmp.toString());
		sbSQL.append("    WHERE  cc.nTransAccountID = dd.nAccountID(+)) aaa, \n");
        sbSQL.append("    sett_Account bbb, \n");
        sbSQL.append("    Client ccc \n");

        // create where 
        sbSQL.append(" where \n");
        sbSQL.append("  aaa.nAccountID = bbb.ID \n");
        sbSQL.append("  AND bbb.nClientID = ccc.ID \n");
        sbSQL.append("  AND bbb.nOfficeID = " + 
            qInfo.getOfficeID() + " \n");
        sbSQL.append("  AND bbb.nCurrencyID = " + 
            qInfo.getCurrencyID() + " \n");
        sbSQL.append("  AND bbb.nCheckStatusID = " + 
            SETTConstant.AccountCheckStatus.CHECK + " \n");

        System.out.println("--------得到客户类型ID为:"+qInfo.getClientTypeID());
        if(qInfo.getClientTypeID() != -1){		//客户类型ID
        	sbSQL.append("  AND ccc.NSETTCLIENTTYPEID = " + 
        			qInfo.getClientTypeID() + " \n");
        }
        
        if(qInfo.getEnterpriseTypeID1() > 0){		//客户属性1
        	sbSQL.append("  AND ccc.nClienttypeID1 = " + 
        			qInfo.getEnterpriseTypeID1() + " \n");
        }
        if(qInfo.getEnterpriseTypeID2() > 0){		//客户属性2
        	sbSQL.append("  AND ccc.nClienttypeID2 = " + 
        			qInfo.getEnterpriseTypeID2() + " \n");
        }
        if(qInfo.getEnterpriseTypeID3() > 0){		//客户属性3
        	sbSQL.append("  AND ccc.nClienttypeID3 = " + 
        			qInfo.getEnterpriseTypeID3() + " \n");
        }
        if(qInfo.getEnterpriseTypeID4() > 0){		//客户属性4
        	sbSQL.append("  AND ccc.nClienttypeID4 = " + 
        			qInfo.getEnterpriseTypeID4() + " \n");
        }
        if(qInfo.getEnterpriseTypeID5() > 0){		//客户属性5
        	sbSQL.append("  AND ccc.nClienttypeID5 = " + 
        			qInfo.getEnterpriseTypeID5() + " \n");
        }
        if(qInfo.getEnterpriseTypeID6() > 0){		//客户属性6
        	sbSQL.append("  AND ccc.nClienttypeID6 = " + 
        			qInfo.getEnterpriseTypeID6() + " \n");
        }
        if(qInfo.getClientManager() > 0){	    //客户经理
        	sbSQL.append("  AND ccc.nCustomerManagerUserId = " + 
        			qInfo.getClientManager() + " \n");
        }
        if (qInfo.getStartAccountNo() != null && 
            qInfo.getStartAccountNo().length() > 0) {
	            sbSQL.append("  AND bbb.sAccountNo >= '" +
	            qInfo.getStartAccountNo() + "'");
        }
        if (qInfo.getEndAccountNo() != null && 
            qInfo.getEndAccountNo().length() > 0) {
	            sbSQL.append("  AND bbb.sAccountNo <= '" + 
	            qInfo.getEndAccountNo() + "'");
        }
		if(qInfo.getAppointAccountNo() != null && qInfo.getAppointAccountNo().length() > 0){
			sbSQL.append(" AND bbb.sAccountNo in ('"+qInfo.getAppointAccountNo()+"')");
		}
		if (qInfo.getStartClientCode() != null && 
			qInfo.getStartClientCode().length() > 0) {
				sbSQL.append("  AND ccc.sCode >= '" + 
				qInfo.getStartClientCode() + "'");
		}
		if (qInfo.getEndClientCode() != null && 
			qInfo.getEndClientCode().length() > 0) {
				sbSQL.append("  AND ccc.sCode <= '" + 
				qInfo.getEndClientCode() + "'");
        }
        if (qInfo.getIsCheckedType() == 3 && qInfo.getAccountTypeID1() > 0) {
            sbSQL.append("  AND bbb.nAccountTypeID = " + 
                qInfo.getAccountTypeID1());
        } else if (qInfo.getIsCheckedType() == 6 && 
            qInfo.getAccountTypeID2() > 0) {
        	sbSQL.append("  AND bbb.nAccountTypeID IN ( \n");
            sbSQL.append("SELECT ID FROM "+"sett_accounttype WHERE \n");
            sbSQL.append(" NACCOUNTGROUPID = " + qInfo.getAccountTypeID2() + ") \n");
        }
        if (qInfo.getIsCheckedActive() > 0)
            sbSQL.append("  AND (mPayAmount <> 0.0 OR "+
                "mRecAmount <> 0.0) \n");

        System.out.println(sbSQL.toString());
        
		return sbSQL.toString();
		
	}
	
	public String queryAccountDetailInfoSQL(QueryAccountAmountWhereInfo qInfo){
		
		StringBuffer sbSQL= new StringBuffer();

        //SELECT
    	sbSQL.append(" select \n");
    	sbSQL.append("a.ID ID,NVL(b.ID,-1) trnasID, a.dtExecute executeDate, a.sTransNo transNo, \n");
    	sbSQL.append("a.nTransactionTypeId transactionTypeId, a.nTransAccountId transAccountId, \n");
    	sbSQL.append("a.mAmount amount, a.nTransDirection transDirection, a.nOppAccountId oppAccountId, \n");
    	sbSQL.append("a.nStatusId statusId, a.sAbstract remark, a.nSerialNo serialNo, a.oppaccountno oppAccountNo, \n");
    	sbSQL.append("b.InputUserID, b.CheckUserID, c.nAccountTypeID accountTypeId,a.amounttype amounttype \n");
    	//FROM
    	sbSQL.append(" from \n");
    	sbSQL.append("sett_TransAccountDetail a, sett_VTransaction b, sett_account c \n");
    	//WHERE
    	sbSQL.append(" where \n");
    	sbSQL.append("1 = 1 \n");
    	sbSQL.append("	AND a.sTransNo = b.TransNo(+) \n");
    	sbSQL.append("	AND a.nCurrencyID = "+qInfo.getCurrencyID()+" \n");
    	sbSQL.append("	And a.nStatusID = "+SETTConstant.TransactionStatus.CHECK+" \n");
    	sbSQL.append("	AND a.nTransAccountID = "+qInfo.getAccountID()+" \n");
    	if (qInfo.getStartQueryDate() != null && qInfo.getStartQueryDate().toString().trim().length() > 0) {
    		sbSQL.append("	AND a.dtExecute >= TO_DATE('"+DataFormat.getDateString(qInfo.getStartQueryDate())+"', 'YYYY-MM-DD') \n");
    	}
    	if (qInfo.getEndQueryDate() != null && qInfo.getEndQueryDate().toString().trim().length() > 0) {
    		sbSQL.append("	AND a.dtExecute <= TO_DATE('"+DataFormat.getDateString(qInfo.getEndQueryDate())+"', 'YYYY-MM-DD') \n");
    	}
    	sbSQL.append("	AND a.nTransAccountID = c.ID \n");

		System.out.println(sbSQL.toString());
		
		return sbSQL.toString();
		
	}
	
	 /**
     * 临时SQL
     * @param  qInfo,nTransTypeID
     * @return bufForm
     */
    private String setFrom(QueryAccountAmountWhereInfo qaawi,
        long lTransTypeID) {
        StringBuffer bufFrom = new StringBuffer();

        String depositAccountType = this.getDepositAccountType(qaawi.getCurrencyID(),qaawi.getOfficeID());
        String loanAccountType = this.getLoanAccountType(qaawi.getCurrencyID(),qaawi.getOfficeID());
        if ((depositAccountType != null && depositAccountType.trim().length()>0) || 
	        (loanAccountType != null && loanAccountType.trim().length()>0)) {
	        bufFrom.append(" AND (");
	        if (depositAccountType != null && depositAccountType.trim().length()>0) {
		        bufFrom.append(" (a.nTransDirection = " +
		            (lTransTypeID == SETTConstant.DebitOrCredit.DEBIT?
		            SETTConstant.DebitOrCredit.DEBIT:
		            SETTConstant.DebitOrCredit.CREDIT));
		        bufFrom.append(" AND b.nAccountTypeID IN (" + depositAccountType + "))");
	        }
	        if ((depositAccountType != null && depositAccountType.trim().length()>0) && 
	        	(loanAccountType != null && loanAccountType.trim().length()>0)) {
	        	bufFrom.append(" OR");
	        }
	        if (loanAccountType != null && loanAccountType.trim().length()>0) {
	            bufFrom.append(" (a.nTransDirection = " +
	                    (lTransTypeID == SETTConstant.DebitOrCredit.DEBIT?
	                    SETTConstant.DebitOrCredit.CREDIT:
	                    SETTConstant.DebitOrCredit.DEBIT));
		        bufFrom.append(" AND b.nAccountTypeID IN (" + loanAccountType + "))");
	        }
	        bufFrom.append(" )");
        }

        return bufFrom.toString();
    }
    
    public String queryTodayAccountBalanceInfoSQL(QueryAccountWhereInfo qInfo){

    	StringBuffer sbSQL= new StringBuffer();
    	String JoinOut = "(+)";
    	
		// select
    	sbSQL.append("  select \n");
		sbSQL.append("  round(a.Balance,2) Minterestbalance,a.BalanceDate,a.OfficeID,a.CurrencyID,a.ClientID,a.ClientCode,a.ClientName,a.AccountID,a.AccountNo,a.AccountTypeID,a.AccountStatusID, \n");
		sbSQL.append("        a.SubAccountID,a.SubAccountStatusID,round(a.Balance,2) Balance,round(a.OpenAmount,2) OpenAmount,round(a.Interest,2) Interest, \n");
		sbSQL.append("        a.InterestDate,a.FinishDate,round(a.UnCheckPaymentAmount,2) UnCheckPaymentAmount, \n");
		sbSQL.append("        a.ClearInterestDate,a.InterestRate,a.AccountOpenDate, 1 isToday ,\n");
		sbSQL.append("            -- 活期 \n");
		sbSQL.append("        a.IsOverdraft, a.IsNegotiate,round(a.NegotiateAmount,2) NegotiateAmount, round(a.NegotiateUnit,2) NegotiateUnit,a.NegotiateRate, \n");
		sbSQL.append("        decode(a.accountgroupid,"+ SETTConstant.AccountGroupType.CURRENT +",round(a.NegotiateInterest,2)) NegotiateInterest,round(a.NegotiateBalance,2) NegotiateBalance, a.InterestPlanID, \n");
		sbSQL.append("        -- 定期 \n");
		sbSQL.append("        a.DepositNo,a.FixPeriod,a.FixDepositStartDate,a.FixDepositEndDate,a.FixInterestRate,a.NoticeDay, \n");
		sbSQL.append("        round(a.FixPreDrawInterest,2) FixPreDrawInterest,a.FixPreDrawDate, \n");
		sbSQL.append("        -- 贷款 \n");
		sbSQL.append("        a.LoanPayID, \n");
		sbSQL.append("        -- 贷款 \n");
		sbSQL.append("        b.ContractID,b.LoanTypeID,b.ContractStatusID,round(b.ContractAmount,2) ContractAmount,round(b.LoanPayAmount,2) LoanPayAmount, \n");
		sbSQL.append("        b.ContractPeriod,b.ContractYear,b.ContractCode, b.LoanpayCode,b.ConsignClientID,b.ContractStartDate,b.ContractEndDate, \n");
		sbSQL.append("        b.ContractInterestRate,b.LoanPayStartDate,b.LoanpayEndDate,b.IndustryType1,b.IndustryType2,b.IndustryType3,b.RegionTypeID,b.ClientTypeID1, \n");
		sbSQL.append("        b.ClientTypeID2,b.ClientTypeID3,b.ClientTypeID4,b.ClientTypeID5,b.ClientTypeID6, \n");
		sbSQL.append("        b.RiskLevel,b.ParentCorpID,b.LoanPayRate,b.CommissionRate,b.SecutyFeeRate,b.CommissionTypeID \n");
		
		// from
		sbSQL.append("        from \n");
		sbSQL.append(" (select client.id ClientID,Client.sCode ClientCode,Client.sname ClientName,acct.id AccountID,acct.saccountno AccountNo,acct.naccounttypeid AccountTypeID, acctype.naccountgroupid AccountGroupID, \n");
		sbSQL.append("        acct.nstatusID AccountStatusID,acct.nOfficeID OfficeID,acct.nCurrencyID CurrencyID, \n");
		sbSQL.append("        subAcct.id SubAccountID, subAcct.nstatusid SubAccountStatusID,subAcct.mbalance Balance,subAcct.mOpenAmount OpenAmount, \n");
		sbSQL.append("        subAcct.mInterest Interest, subAcct.dtOpen InterestDate, subAcct.dtFinish FinishDate, subAcct.mUnCheckPaymentAmount UncheckPaymentAmount, \n");
		sbSQL.append("        subAcct.dtClearInterest ClearInterestDate, OfficeTime.dtOpenDate BalanceDate,0.00 InterestRate,subAcct.dtOpen AccountOpenDate,  \n");
		sbSQL.append("        -- 活期 \n");
		sbSQL.append("        subAcct.ac_nIsOverdraft IsOverdraft, subAcct.ac_nIsNegotiate IsNegotiate,subAcct.ac_mNegotiateAmount NegotiateAmount, \n");
		sbSQL.append("        subAcct.ac_mNegotiateUnit NegotiateUnit,subAcct.ac_mNegotiateRate NegotiateRate,0  NegotiateBalance, subAcct.ac_mNegotiateInterest NegotiateInterest, \n");
		sbSQL.append("        subAcct.ac_nInterestRatePlanID InterestPlanID, \n");
		sbSQL.append("        -- 定期 \n");
		sbSQL.append("        subAcct.af_sDepositNo DepositNo,subAcct.af_nDepositTerm FixPeriod,subAcct.af_dtStart FixDepositStartDate, \n");
		sbSQL.append("        subAcct.af_dtEnd FixDepositEndDate,subAcct.af_mRate FixInterestRate,subAcct.af_nNoticeDay NoticeDay, \n");
		sbSQL.append("        subAcct.af_mPreDrawInterest FixPreDrawInterest,subAcct.af_dtPreDraw FixPreDrawDate, \n");
		sbSQL.append("        -- 贷款 \n");
		sbSQL.append("        subAcct.al_nLoanNoteID LoanPayID \n");
		sbSQL.append(" from sett_OfficeTime OfficeTime,client,sett_account acct, sett_subAccount subAcct, sett_accounttype acctype \n");
		sbSQL.append(" where client.id=acct.nclientid and acct.id=subAcct.nAccountID and acct.nofficeid=" + qInfo.getOfficeID() + " and acct.ncurrencyid=" + qInfo.getCurrencyID() + " \n");
		sbSQL.append("       and OfficeTime.nOfficeID=acct.nOfficeID and OfficeTime.nCurrencyID=acct.nCurrencyID and subAcct.NSTATUSID>0 \n");
		sbSQL.append(" and acct.nCheckStatusID = 4 \n");//add by rxie 没有复核的账户和已删除的账户不会显示出来没有复核的账户和已删除的账户信息都显示出来了
		//  客户号
		if (qInfo.getStartClientCode() != null && qInfo.getStartClientCode().length() > 0)
			sbSQL.append("    and   client.scode>='" + qInfo.getStartClientCode() + "' ");
		if (qInfo.getEndClientCode() != null && qInfo.getEndClientCode().length() > 0)
			sbSQL.append("    and   client.scode<='" + qInfo.getEndClientCode() + "' ");
		// 账户号
		if (qInfo.getStartAccountNo() != null && qInfo.getStartAccountNo().length() > 0)
			sbSQL.append("    and   acct.saccountno>='" + qInfo.getStartAccountNo() + "' ");
		if (qInfo.getEndAccountNo() != null && qInfo.getEndAccountNo().length() > 0)
			sbSQL.append("    and   acct.saccountno<='" + qInfo.getEndAccountNo() + "' ");
		//add by 2012-05-16 添加指定编号
		if(qInfo.getAppointAccountNo() != null && qInfo.getAppointAccountNo().length() > 0){
			sbSQL.append("    and   acct.saccountno in ('"+qInfo.getAppointAccountNo()+"')");
		}
		if (qInfo.getEnterpriseTypeID1() > 0)
			sbSQL.append("    and   client.nClienttypeID1 = " + qInfo.getEnterpriseTypeID1());
		if (qInfo.getEnterpriseTypeID2() > 0)
			sbSQL.append("    and   client.nClienttypeID2 = " + qInfo.getEnterpriseTypeID2());
		if (qInfo.getEnterpriseTypeID3() > 0)
			sbSQL.append("    and   client.nClienttypeID3 = " + qInfo.getEnterpriseTypeID3());
		if (qInfo.getEnterpriseTypeID4() > 0)
			sbSQL.append("    and   client.nClienttypeID4 = " + qInfo.getEnterpriseTypeID4());
		if (qInfo.getEnterpriseTypeID5() > 0)
			sbSQL.append("    and   client.nClienttypeID5 = " + qInfo.getEnterpriseTypeID5());
		if (qInfo.getEnterpriseTypeID6() > 0)
			sbSQL.append("    and   client.nClienttypeID6 = " + qInfo.getEnterpriseTypeID6());
		if (qInfo.getIsNegotiate() > 0)
			sbSQL.append("    and   subAcct.ac_nIsNegotiate>0 ");
		if (qInfo.getIsFilterNull() > 0)
			sbSQL.append("    and   subAcct.mBalance >0.0 ");
		if (qInfo.getIsValidAccount() > 0)
			sbSQL.append("    and   subAcct.nStatusID=" + SETTConstant.SubAccountStatus.NORMAL);
		// 定期
		if (qInfo.getStartFixFormNo() != null && qInfo.getStartFixFormNo().length() > 0)
			sbSQL.append("    and   subAcct.af_sDepositNo='" + qInfo.getStartFixFormNo() + "' ");
		if (qInfo.getStartFixPeriod() > 0)
			sbSQL.append("    and   subAcct.af_nDepositTerm=" + qInfo.getStartFixPeriod());
		// 自营贷款
		if (qInfo.getLoanType() == LOANConstant.LoanType.ZY) {
			sbSQL.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.TRUST +" and nstatusId=1 and officeId="+qInfo.getOfficeID()+" and currencyId="+qInfo.getCurrencyID()+")");
		//　委托贷款
		} else if (qInfo.getLoanType() == LOANConstant.LoanType.WT) {
			sbSQL.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.CONSIGN +" and nstatusId=1 and officeId="+qInfo.getOfficeID()+" and currencyId="+qInfo.getCurrencyID()+")");
		//　银团贷款
		} else if (qInfo.getLoanType() == LOANConstant.LoanType.YT) {
			sbSQL.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.YT +" and nstatusId=1 and officeId="+qInfo.getOfficeID()+" and currencyId="+qInfo.getCurrencyID()+")");
		//　贴现
		} else if (qInfo.getLoanType() == LOANConstant.LoanType.TX) {
			sbSQL.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.DISCOUNT +" and nstatusId=1 and officeId="+qInfo.getOfficeID()+" and currencyId="+qInfo.getCurrencyID()+")");
		//　担保
		} else if (qInfo.getLoanType() == LOANConstant.LoanType.DB || qInfo.getLoanType() == LOANConstant.LoanType.RZZL) {
			sbSQL.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.MARGIN +" and nstatusId=1 and officeId="+qInfo.getOfficeID()+" and currencyId="+qInfo.getCurrencyID()+")");
		//　其他贷款
		} else if (qInfo.getLoanType() == LOANConstant.LoanType.OTHER) {
			sbSQL.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.OTHERLOAN +" and nstatusId=1 and officeId="+qInfo.getOfficeID()+" and currencyId="+qInfo.getCurrencyID()+")");
		} else {
			sbSQL.append("     and acct.naccounttypeid in (select id from sett_accounttype where nstatusId=1 and officeId="+qInfo.getOfficeID()+" and currencyId="+qInfo.getCurrencyID()+")");
		}
		sbSQL.append(" and acct.naccounttypeid = acctype.id \n");
		
		//
		sbSQL.append(" ) a, \n");
		sbSQL.append(" (select * \n");
		sbSQL.append("  from sett_vContractInfo \n");
		sbSQL.append("  where officeid=" + qInfo.getOfficeID() + " and currencyid=" + qInfo.getCurrencyID() + " \n");
		// 贷款
		if (qInfo.getStartContractCode() != null && qInfo.getStartContractCode().length() > 0)
		{
			sbSQL.append("      and ContractCode ='" + qInfo.getStartContractCode() + "' ");
			JoinOut = "";
		}
		if (qInfo.getStartLoanPayCode() != null && qInfo.getStartLoanPayCode().length() > 0)
		{
			sbSQL.append("      and LoanPayCode ='" + qInfo.getStartLoanPayCode() + "' ");
			JoinOut = "";
		}
		if (qInfo.getContractYear() > 0)
		{
			sbSQL.append("      and ContractYear =" + qInfo.getContractYear());
			JoinOut = "";
		}
		if (qInfo.getConsignClientID() > 0)
		{
			sbSQL.append("      and ConsignClientID =" + qInfo.getConsignClientID());
			JoinOut = "";
		}
		if (qInfo.getStartContractPeriod() > 0)
		{
			sbSQL.append("      and ContractPeriod>=" + qInfo.getStartContractPeriod());
			JoinOut = "";
		}
		if (qInfo.getEndContractPeriod() > 0)
		{
			sbSQL.append("      and ContractPeriod<=" + qInfo.getEndContractPeriod());
			JoinOut = "";
		}
		if (qInfo.getLoanType() > 0)
		{
			sbSQL.append("      and LoanTypeID=" + qInfo.getLoanType());
			JoinOut = "";
		}
		sbSQL.append(" ) b        \n");
		
		// where
		sbSQL.append("        where \n");
		sbSQL.append("  a.LoanPayID=b.LoanPayID" + JoinOut + " \n");
	
		
		System.out.println(sbSQL.toString());
		
		return sbSQL.toString();
    }
    
    public String queryHistoryAccountBalanceInfoSQL(QueryAccountWhereInfo qInfo){

    	StringBuffer sbSQL= new StringBuffer();
    	String JoinOut = "(+)";
		
		// select
    	sbSQL.append("  select \n");
		sbSQL.append("  round(a.Minterestbalance,2) Minterestbalance,a.BalanceDate,a.OfficeID,a.CurrencyID,a.ClientID,a.ClientCode,a.ClientName,a.AccountID,a.AccountNo,a.AccountTypeID,a.AccountStatusID, \n");
		sbSQL.append("        a.SubAccountID,a.SubAccountStatusID,round(a.Balance,2) Balance,round(a.OpenAmount,2) OpenAmount,round(a.Interest,2) Interest, \n");
		sbSQL.append("        a.InterestDate,a.FinishDate,round(a.UnCheckPaymentAmount,2) UnCheckPaymentAmount, \n");
		sbSQL.append("        a.ClearInterestDate,a.InterestRate,a.AccountOpenDate,0 isToday,  \n");
		sbSQL.append("            -- 活期 \n");
		sbSQL.append("        a.IsOverdraft, a.IsNegotiate,round(a.NegotiateAmount,2) NegotiateAmount, round(a.NegotiateUnit,2) NegotiateUnit,a.NegotiateRate, \n");
		sbSQL.append("        decode(a.accountgroupid,"+ SETTConstant.AccountGroupType.CURRENT +",round(a.NegotiateInterest,2)) NegotiateInterest,round(a.NegotiateBalance,2) NegotiateBalance,a.InterestPlanID, \n");
		sbSQL.append("        -- 定期 \n");
		sbSQL.append("        a.DepositNo,a.FixPeriod,a.FixDepositStartDate,a.FixDepositEndDate,a.FixInterestRate,a.NoticeDay, \n");
		sbSQL.append("        round(a.FixPreDrawInterest,2) FixPreDrawInterest,a.FixPreDrawDate, \n");
		sbSQL.append("        -- 贷款 \n");
		sbSQL.append("        a.LoanPayID, \n");
		sbSQL.append("        -- 贷款 \n");
		sbSQL.append("        b.ContractID,b.LoanTypeID,b.ContractStatusID,round(b.ContractAmount,2) ContractAmount,round(b.LoanPayAmount,2) LoanPayAmount, \n");
		sbSQL.append("        b.ContractPeriod,b.ContractYear,b.ContractCode, b.LoanpayCode,b.ConsignClientID,b.ContractStartDate,b.ContractEndDate, \n");
		sbSQL.append("        b.ContractInterestRate,b.LoanPayStartDate,b.LoanpayEndDate,b.IndustryType1,b.IndustryType2,b.IndustryType3,b.RegionTypeID,b.ClientTypeID1, \n");
		sbSQL.append("        b.ClientTypeID2,b.ClientTypeID3,b.ClientTypeID4,b.ClientTypeID5,b.ClientTypeID6, \n");
		sbSQL.append("        b.RiskLevel,b.ParentCorpID,b.LoanPayRate,b.CommissionRate,b.SecutyFeeRate,b.CommissionTypeID \n");

		// from
		sbSQL.append("        from \n");
		sbSQL.append(" (select client.id ClientID,Client.sCode ClientCode,Client.sname ClientName,acct.id AccountID,acct.saccountno AccountNo,acct.naccounttypeid AccountTypeID, acctype.naccountgroupid AccountGroupID, \n");
		sbSQL.append("        acct.nstatusID AccountStatusID,acct.nOfficeID OfficeID,acct.nCurrencyID CurrencyID, \n");
		sbSQL.append("        subAcct.id SubAccountID, subAcct.nstatusid SubAccountStatusID,daily.mbalance Balance,subAcct.mOpenAmount OpenAmount, \n");
		sbSQL.append("        daily.minterestbalance  Minterestbalance,\n");
		sbSQL.append("        daily.mInterest Interest, \n");
		sbSQL.append("        subAcct.dtOpen InterestDate, subAcct.dtFinish FinishDate, 0.0 UncheckPaymentAmount, \n");
		sbSQL.append("        subAcct.dtClearInterest ClearInterestDate, daily.dtDate BalanceDate,daily.mInterestRate InterestRate, \n");
		sbSQL.append("        subAcct.dtOpen AccountOpenDate, \n");
		sbSQL.append("        -- 活期 \n");
		sbSQL.append("        subAcct.ac_nIsOverdraft IsOverdraft, subAcct.ac_nIsNegotiate IsNegotiate,subAcct.ac_mNegotiateAmount NegotiateAmount, \n");
		sbSQL.append("        subAcct.ac_mNegotiateUnit NegotiateUnit,daily.ac_mNegotiateRate NegotiateRate,daily.ac_mNegotiateBalance  NegotiateBalance, \n");
		sbSQL.append("        daily.ac_mNegotiateInterest NegotiateInterest, \n");
		sbSQL.append("        subAcct.ac_nInterestRatePlanID InterestPlanID, \n");
		sbSQL.append("        -- 定期 \n");
		sbSQL.append("        subAcct.af_sDepositNo DepositNo,subAcct.af_nDepositTerm FixPeriod,subAcct.af_dtStart FixDepositStartDate, \n");
		sbSQL.append("        subAcct.af_dtEnd FixDepositEndDate,subAcct.af_mRate FixInterestRate,subAcct.af_nNoticeDay NoticeDay, \n");
		sbSQL.append("        subAcct.af_mPreDrawInterest FixPreDrawInterest,subAcct.af_dtPreDraw FixPreDrawDate, \n");
		sbSQL.append("        -- 贷款 \n");
		sbSQL.append("        subAcct.al_nLoanNoteID LoanPayID,subAcct.al_mPreDrawInterest LoanPreDrawInterest \n");
		sbSQL.append(" from client,sett_account acct, sett_subAccount subAcct,sett_DailyAccountBalance daily, sett_accounttype acctype \n");
		sbSQL.append("  where client.id=acct.nclientid and acct.id=subAcct.nAccountID and acct.nofficeid=" + qInfo.getOfficeID() + " and acct.ncurrencyid=" + qInfo.getCurrencyID() + " \n");
		sbSQL.append("         and subAcct.NSTATUSID>0 and subAcct.id=daily.nSubAccountID and daily.dtDate=to_date('" + DataFormat.getDateString(qInfo.getQueryDate()) + "','yyyy-mm-dd') \n");
		sbSQL.append(" and acct.nCheckStatusID = 4 \n");//add by rxie 没有复核的账户和已删除的账户不会显示出来没有复核的账户和已删除的账户信息都显示出来了
		//  客户号
		if (qInfo.getStartClientCode() != null && qInfo.getStartClientCode().length() > 0)
			sbSQL.append("    and   client.scode>='" + qInfo.getStartClientCode() + "' ");
		if (qInfo.getEndClientCode() != null && qInfo.getEndClientCode().length() > 0)
			sbSQL.append("    and   client.scode<='" + qInfo.getEndClientCode() + "' ");
		// 账户号
		if (qInfo.getStartAccountNo() != null && qInfo.getStartAccountNo().length() > 0)
			sbSQL.append("    and   acct.saccountno>='" + qInfo.getStartAccountNo() + "' ");
		if (qInfo.getEndAccountNo() != null && qInfo.getEndAccountNo().length() > 0)
			sbSQL.append("    and   acct.saccountno<='" + qInfo.getEndAccountNo() + "' ");
		if(qInfo.getAppointAccountNo() != null && qInfo.getAppointAccountNo().length() > 0){
			sbSQL.append("    and   acct.saccountno in ('"+qInfo.getAppointAccountNo()+"')");
		}
		if (qInfo.getEnterpriseTypeID1() > 0)
			sbSQL.append("    and   client.nClienttypeID1 = " + qInfo.getEnterpriseTypeID1());
		if (qInfo.getEnterpriseTypeID2() > 0)
			sbSQL.append("    and   client.nClienttypeID2 = " + qInfo.getEnterpriseTypeID2());
		if (qInfo.getEnterpriseTypeID3() > 0)
			sbSQL.append("    and   client.nClienttypeID3 = " + qInfo.getEnterpriseTypeID3());
		if (qInfo.getEnterpriseTypeID4() > 0)
			sbSQL.append("    and   client.nClienttypeID4 = " + qInfo.getEnterpriseTypeID4());
		if (qInfo.getEnterpriseTypeID5() > 0)
			sbSQL.append("    and   client.nClienttypeID5 = " + qInfo.getEnterpriseTypeID5());
		if (qInfo.getEnterpriseTypeID6() > 0)
			sbSQL.append("    and   client.nClienttypeID6 = " + qInfo.getEnterpriseTypeID6());
		if (qInfo.getIsNegotiate() > 0)
			sbSQL.append("    and   subAcct.ac_nIsNegotiate>0 ");
		if (qInfo.getIsFilterNull() > 0)
			sbSQL.append("    and   daily.mBalance>0");
		if (qInfo.getIsValidAccount() > 0)
			sbSQL.append("    and   subAcct.nStatusID=" + SETTConstant.SubAccountStatus.NORMAL);
		// 定期
		if (qInfo.getStartFixFormNo() != null && qInfo.getStartFixFormNo().length() > 0)
			sbSQL.append("    and   subAcct.af_sDepositNo='" + qInfo.getStartFixFormNo() + "' ");
		if (qInfo.getStartFixPeriod() > 0)
			sbSQL.append("    and   subAcct.af_nDepositTerm=" + qInfo.getStartFixPeriod());
		// 自营贷款
		if (qInfo.getLoanType() == LOANConstant.LoanType.ZY) {
			sbSQL.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.TRUST +" and nstatusId=1 and officeId="+qInfo.getOfficeID()+" and currencyId="+qInfo.getCurrencyID()+")");
		//　委托贷款
		} else if (qInfo.getLoanType() == LOANConstant.LoanType.WT) {
			sbSQL.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.CONSIGN +" and nstatusId=1 and officeId="+qInfo.getOfficeID()+" and currencyId="+qInfo.getCurrencyID()+")");
		//　银团贷款
		} else if (qInfo.getLoanType() == LOANConstant.LoanType.YT) {
			sbSQL.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.YT +" and nstatusId=1 and officeId="+qInfo.getOfficeID()+" and currencyId="+qInfo.getCurrencyID()+")");
		//　贴现
		} else if (qInfo.getLoanType() == LOANConstant.LoanType.TX) {
			sbSQL.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.DISCOUNT +" and nstatusId=1 and officeId="+qInfo.getOfficeID()+" and currencyId="+qInfo.getCurrencyID()+")");
		//　担保
		} else if (qInfo.getLoanType() == LOANConstant.LoanType.DB || qInfo.getLoanType() == LOANConstant.LoanType.RZZL) {
			sbSQL.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.MARGIN +" and nstatusId=1 and officeId="+qInfo.getOfficeID()+" and currencyId="+qInfo.getCurrencyID()+")");
		//　其他贷款
		} else if (qInfo.getLoanType() == LOANConstant.LoanType.OTHER) {
			sbSQL.append("     and acct.naccounttypeid in (select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.OTHERLOAN +" and nstatusId=1 and officeId="+qInfo.getOfficeID()+" and currencyId="+qInfo.getCurrencyID()+")");
		} else {
			sbSQL.append("     and acct.naccounttypeid in (select id from sett_accounttype where nstatusId=1 and officeId="+qInfo.getOfficeID()+" and currencyId="+qInfo.getCurrencyID()+")");
		}
		sbSQL.append(" and acct.naccounttypeid = acctype.id \n");
		sbSQL.append(" ) a, \n");
		sbSQL.append(" (select * \n");
		sbSQL.append("  from sett_vContractInfo \n");
		sbSQL.append("  where officeid=" + qInfo.getOfficeID() + " and currencyid=" + qInfo.getCurrencyID() + " \n");
		// 贷款
		if (qInfo.getStartContractCode() != null && qInfo.getStartContractCode().length() > 0)
		{
			sbSQL.append("      and ContractCode ='" + qInfo.getStartContractCode() + "' ");
			JoinOut = "";
		}
		if (qInfo.getStartLoanPayCode() != null && qInfo.getStartLoanPayCode().length() > 0)
		{
			sbSQL.append("      and LoanPayCode ='" + qInfo.getStartLoanPayCode() + "' ");
			JoinOut = "";
		}
		if (qInfo.getContractYear() > 0)
		{
			sbSQL.append("      and ContractYear =" + qInfo.getContractYear());
			JoinOut = "";
		}
		if (qInfo.getConsignClientID() > 0)
		{
			sbSQL.append("      and ConsignClientID =" + qInfo.getConsignClientID());
			JoinOut = "";
		}
		if (qInfo.getStartContractPeriod() > 0)
		{
			sbSQL.append("      and ContractPeriod>=" + qInfo.getStartContractPeriod());
			JoinOut = "";
		}
		if (qInfo.getEndContractPeriod() > 0)
		{
			sbSQL.append("      and ContractPeriod<=" + qInfo.getEndContractPeriod());
			JoinOut = "";
		}
		if (qInfo.getLoanType() > 0)
		{
			sbSQL.append("      and LoanTypeID=" + qInfo.getLoanType());
			JoinOut = "";
		}
		sbSQL.append(" ) b        \n");

		// where
		sbSQL.append("        where \n");
		sbSQL.append("  a.LoanPayID=b.LoanPayID" + JoinOut + " \n");
	
		System.out.println(sbSQL.toString());
		
		return sbSQL.toString();
    }
    
    public String queryQueryTransAccountDetail(QueryTransAccountDetailWhereInfo qInfo){

    	StringBuffer sbSQL= new StringBuffer();
    	String JoinOut = "(+)";
		
		boolean isSelectToday = false;
		if(qInfo !=null)
		{
			Timestamp tempToday = Env.getSystemDate(qInfo.getOfficeID(),qInfo.getCurrencyID());
			if( qInfo.getEndDate().compareTo(tempToday)==0)
			{
				isSelectToday = true;
			}
		}

		// select 
		sbSQL.append("  select \n");
		sbSQL.append(" sett_Account.ID AccountID,sett_Account.sAccountNo AccountNo,Client.sCode ClientCode,Client.sName ClientName, \n");
		if(!isSelectToday)
		{
			sbSQL.append(" sum(sett_SubAccount.mBalance) CurrentBalance,nvl(sum(sett_DailyAccountBalance.mBalance),0.00) HistoryBalance \n");
		}
		else
		{
			sbSQL.append(" sum(sett_SubAccount.mBalance) CurrentBalance,sum(sett_SubAccount.mBalance) HistoryBalance \n");
		}
		
		// from 
		sbSQL.append("        from \n");
		if(!isSelectToday)
		{
			sbSQL.append(" sett_Account ,sett_SubAccount,Client,sett_DailyAccountBalance \n");
		}
		else
		{
			sbSQL.append(" sett_Account ,sett_SubAccount,Client \n");
		}
		
		// where 
		sbSQL.append("        where \n");
		sbSQL.append(" sett_SubAccount.nAccountID = sett_Account.ID and sett_Account.nClientID = Client.ID \n");
		if(!isSelectToday)
		{
			sbSQL.append(" and sett_SubAccount.ID = sett_DailyAccountBalance.nSubAccountID(+) \n");
		}
		sbSQL.append(" and sett_Account.nOfficeID = "+qInfo.getOfficeID()+" and sett_Account.nCurrencyID = "+qInfo.getCurrencyID()+" \n");
		sbSQL.append(" and sett_Account.nCheckStatusID="+SETTConstant.AccountCheckStatus.CHECK+" \n");
		if(!isSelectToday)
		{
			sbSQL.append(" and sett_DailyAccountBalance.dtDate(+) = to_date('"+DataFormat.formatDate(qInfo.getEndDate())+"','yyyy-mm-dd') \n");
		}
		if (qInfo.getStartClientCode() != null && qInfo.getStartClientCode().length() > 0)
			sbSQL.append(" and client.scode>='" + qInfo.getStartClientCode() + "'");
		if (qInfo.getEndClientCode() != null && qInfo.getEndClientCode().length() > 0)
			sbSQL.append(" and client.scode<='" + qInfo.getEndClientCode() + "'");
		if (qInfo.getStartAccountNo() != null && qInfo.getStartAccountNo().length() > 0)
			sbSQL.append(" and sett_Account.sAccountNo>='" + qInfo.getStartAccountNo() + "'");
		if (qInfo.getEndAccountNo() != null && qInfo.getEndAccountNo().length() > 0)
			sbSQL.append(" and sett_Account.sAccountNo<='" + qInfo.getEndAccountNo() + "'");
		if(qInfo.getAppointAccountNo() != null && qInfo.getAppointAccountNo().length() > 0){
			sbSQL.append(" and sett_Account.sAccountNo in ('"+qInfo.getAppointAccountNo()+"')");
		}
		if (!qInfo.getAccountStatusIDs().equals("") && qInfo.getAccountStatusIDs() != null )
		{
			sbSQL.append(" and sett_Account.Nstatusid in ("+qInfo.getAccountStatusIDs()+")");
		}		
		if(qInfo.getIsFilterNull() == 1)
		{
			sbSQL.append(" and sett_Account.ID in (select distinct ntransaccountid from sett_transaccountdetail where nstatusID="+SETTConstant.TransactionStatus.CHECK);
			if(qInfo.getIsIntrDate() == 1)
			{
				if(qInfo.getStartDate()!=null){
					sbSQL.append(" and dtintereststart>=to_date('"+DataFormat.formatDate(qInfo.getStartDate())+"','yyyy-mm-dd')");
				}
				if(qInfo.getStartDate()!=null){
					sbSQL.append(" and dtintereststart<=to_date('"+DataFormat.formatDate(qInfo.getEndDate())+"','yyyy-mm-dd')");
				}
			}
			else
			{
				if(qInfo.getStartDate()!=null){
					sbSQL.append(" and DTEXECUTE>=to_date('"+DataFormat.formatDate(qInfo.getStartDate())+"','yyyy-mm-dd')");
				}
				if(qInfo.getStartDate()!=null){
					sbSQL.append(" and DTEXECUTE<=to_date('"+DataFormat.formatDate(qInfo.getEndDate())+"','yyyy-mm-dd')");
				}
			}
			sbSQL.append(")");
		}
		sbSQL.append(" \n group by sett_Account.ID,sett_Account.sAccountNo,client.sCode,client.sName \n");
	
		System.out.println(sbSQL.toString());
		
		return sbSQL.toString();
    }

    public String queryTransAccountDetail(QueryTransAccountDetailWhereInfo qInfo){

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
		sbSQL.append(" from sett_TransAccountDetail trans,sett_Account account,sett_SubAccount subaccount,Loan_Contractform contractform,Loan_PayForm loanpayform, SETT_TRANSCURRENTDEPOSIT transCurrencyDeposit \n");
		sbSQL.append(" where subaccount.nAccountID = account.ID and trans.nSubAccountID = subaccount.ID and subaccount.AL_NLOANNOTEID = loanpayform.ID(+) and loanpayform.nContractID = contractform.ID(+) \n");
		sbSQL.append(" and trans.nStatusID=" + SETTConstant.TransactionStatus.CHECK + " and trans.nTransDirection = 2 \n");
		sbSQL.append(" and account.nCurrencyID=" + qInfo.getCurrencyID() + " \n");
		sbSQL.append(" and subaccount.nAccountID = " + qInfo.getAccountID() + " \n");
		sbSQL.append(" and transCurrencyDeposit.sTransNo(+) = trans.sTransNo \n");
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

		sbSQL.append(" ) order by executeday, transno \n");
	
		System.out.println(sbSQL.toString());
		
		return sbSQL.toString();
    }
}
