package com.iss.itreasury.settlement.obinstruction.dao;

import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.settlement.obinstruction.dataentity.QueryOBFinanceInstrInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.DataFormat;

public class QOBFinanceInfoDao {
	
	public String queryOBFinanceInfoSQL(QueryOBFinanceInstrInfo qInfo){
		
		StringBuffer sbSQL= new StringBuffer();

		boolean isHTKG = Config.getBoolean(ConfigConstant.SETTLEMENT_ISHTKG, false);  //是否为航天科工项目
		sbSQL = new StringBuffer();
						
		//select	
		sbSQL.append(" select \n ");
		sbSQL.append(" fin.id as ID,  \n");			
		sbSQL.append(" fin.nTransType as TransType,  \n");
		sbSQL.append(" client.sName as PayClientName,  \n");
		sbSQL.append(" fin.nPayerAcctID as PayerAcctID,  \n");
		sbSQL.append(" fin.nPayeeAcctID as PayeeAcctID,  \n");
		sbSQL.append(" fin.nRemitType as RemitType,  \n");
		sbSQL.append(" fin.mAmount as Amount,  \n");
		sbSQL.append(" fin.CPF_NDEALUSERID as DealUserID,  \n");		
		sbSQL.append(" fin.mRealInterest as Interest,  \n");
		sbSQL.append(" fin.dtExecute as ExecuteDate,  \n");
		sbSQL.append(" fin.sNote as Note,  \n");
		sbSQL.append(" fin.nStatus as Status,  \n");
		sbSQL.append(" fin.nDepositBillStatusID as DepositBillStatusID,  \n");	//得到换开定期存单的ID
		sbSQL.append(" fin.cpf_nDefaultTransType as DefaultTransType,  \n");
		sbSQL.append(" fin.cpf_sTransNo as TransNo, \n");
		sbSQL.append(" fin.dtcheck as CheckDate, \n");
		sbSQL.append(" fin.dtsign as signSignDate, \n");
		sbSQL.append(" fin.dtmodify as dtModify, \n");
		sbSQL.append(" fin.isautocontinue as isautocontinue, \n");
		sbSQL.append(" fin.autocontinuetype as autocontinuetype, \n");
		sbSQL.append(" fin.autocontinueaccountid as autocontinueaccountid, \n");
		sbSQL.append(" fin.lsource as Source \n");
		
		// from 
		sbSQL.append(" from \n");	
		sbSQL.append(" OB_FinanceInstr fin,sett_account acc,client,client c \n");
		
		// where 
		sbSQL.append(" where \n");		
		sbSQL.append(" fin.NPAYERACCTID=acc.id \n");
		sbSQL.append(" and acc.nclientid=client.id \n");	
		sbSQL.append(" and fin.nclientid=c.id \n");
		sbSQL.append(" and fin.nCurrencyID=" + qInfo.getCurrencyID()+ " \n");
		sbSQL.append(" and acc.nofficeid=" + qInfo.getOfficeID()+ " \n");
		if(qInfo.getTransType()>0)
		{
			if(qInfo.getTransType()==SETTConstant.SettInstrType.CHANGEFIXDEPOSIT){
				qInfo.setTransType(SETTConstant.SettInstrType.OPENFIXDEPOSIT);	//定期开立
				sbSQL.append(" and ( fin.NDEPOSITBILLINPUTUSERID is not null ) ");	//换开定期存单输入人ID不为空或者不是不存在
			}
			else if(qInfo.getTransType()==SETTConstant.SettInstrType.OPENFIXDEPOSIT){
				sbSQL.append(" and ( fin.NDEPOSITBILLINPUTUSERID is null ) ");	//换开定期存单输入人ID不为空或者不是不存在
			}
			if(isHTKG)
			{
				if(qInfo.getTransType()==SETTConstant.SettInstrType.CAPTRANSFER_BANKPAY)
				{
					sbSQL.append(" and fin.nTransType in ("+SETTConstant.SettInstrType.TRANSFER_BANKEXTERNAL+","+SETTConstant.SettInstrType.BOOKING_BANKEXTERNAL+") \n");
				}
				else if(qInfo.getTransType()==SETTConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT)
				{
					sbSQL.append(" and fin.nTransType in ("+SETTConstant.SettInstrType.TRANSFER_INTERNALVIREMENT+","+SETTConstant.SettInstrType.SCM_TRANSFER_INTERNALVIREMENT+","+SETTConstant.SettInstrType.BOOKING_INTERNALVIREMENT+","+SETTConstant.SettInstrType.SCM_BOOKING_INTERNALVIREMENT+") \n");
				}
				else
				{
					sbSQL.append(" and fin.nTransType=" + qInfo.getTransType()+ " \n");
				}
			}
			else
			{
				sbSQL.append(" and fin.nTransType=" + qInfo.getTransType()+ " \n");
			}
		}
		if(qInfo.getStatus()==0)   //全部
		{
			sbSQL.append("and fin.nIsCanAccept = 1  \n");
		}
		else if(qInfo.getStatus()==SETTConstant.SettInstrStatus.SIGN)   //已提交
		{
			sbSQL.append("and (fin.nStatus =" + OBConstant.SettInstrStatus.CHECK + " or fin.nStatus =" + OBConstant.SettInstrStatus.SIGN + ") \n");
			sbSQL.append("and fin.nIsCanAccept = 1  \n");
		}	
		else
		{
			sbSQL.append("and fin.nStatus =" + qInfo.getStatus()+ " \n");
			sbSQL.append("and fin.nIsCanAccept = 1  \n");
		}
		if(qInfo.getAmountFrom()>0)
		{
			sbSQL.append("and fin.mAmount>=" + qInfo.getAmountFrom()+ " \n");
		}
		if(qInfo.getAmountTo()>0)
		{
			sbSQL.append("and fin.mAmount<=" + qInfo.getAmountTo()+ " \n");
		}
		// 判断选择的查询日期
		if(qInfo.getDateRadio() != null && qInfo.getDateRadio().equals("execute")){
			// 执行日期
			if(qInfo.getExecuteDateFrom()!=null)
			{
				sbSQL.append("and to_char(fin.dtExecute,'yyyy-mm-dd') >='"+DataFormat.formatDate(qInfo.getExecuteDateFrom())+"'");
			}
			if(qInfo.getExecuteDateTo()!=null)
			{
				sbSQL.append("and to_char(fin.dtExecute,'yyyy-mm-dd') <= '"+DataFormat.formatDate(qInfo.getExecuteDateTo())+"'");
			}
		}else if(qInfo.getDateRadio() != null && qInfo.getDateRadio().equals("check")){
			// 复核日期
			if(qInfo.getCheckDateFrom()!=null)
			{
				sbSQL.append("and to_char(fin.dtCheck,'yyyy-mm-dd') >= '"+DataFormat.formatDate(qInfo.getCheckDateFrom())+"'");
			}
			if(qInfo.getCheckDateTo()!=null)
			{
				sbSQL.append("and to_char(fin.dtCheck,'yyyy-mm-dd') <= '"+DataFormat.formatDate(qInfo.getCheckDateTo())+"'");
			}
		}else if(qInfo.getDateRadio() != null && qInfo.getDateRadio().equals("sign")){
			// 签认日期
			if(qInfo.getSignDateFrom()!=null)
			{
				sbSQL.append("and to_char(fin.dtSign,'yyyy-mm-dd') >='"+DataFormat.formatDate(qInfo.getSignDateFrom())+"'");
			}
			if(qInfo.getSignDateTo()!=null)
			{
				sbSQL.append("and to_char(fin.dtSign,'yyyy-mm-dd') <='"+DataFormat.formatDate(qInfo.getSignDateTo())+"'");
			}
		}
		if (qInfo.getAccountNoFrom() != null && qInfo.getAccountNoFrom().length() > 0)
			sbSQL.append("and acc.sAccountNo>='" + qInfo.getAccountNoFrom() + "'");
		if (qInfo.getAccountNoTo() != null && qInfo.getAccountNoTo().length() > 0)
			sbSQL.append("and acc.sAccountNo<='" + qInfo.getAccountNoTo() + "'");
		
		if (qInfo.getClientNoFrom() != null && qInfo.getClientNoFrom().length() > 0)
			sbSQL.append("and c.sCode>='" + qInfo.getClientNoFrom() + "'");
		if (qInfo.getClientNoTo() != null && qInfo.getClientNoTo().length() > 0)
			sbSQL.append("and c.sCode<='" + qInfo.getClientNoTo() + "'");
		
		if (qInfo.getTransNo() != null && qInfo.getTransNo().length() > 0)
		{
			sbSQL.append("and fin.cpf_sTransNo='" + qInfo.getTransNo() + "'");
		}
		if(qInfo.getInstructionNo()>0)
		{
			sbSQL.append("and fin.id=" + qInfo.getInstructionNo()+ " \n");
		}
		if(qInfo.getSource()>0)
		{
			sbSQL.append("and fin.lsource=" + qInfo.getSource()+ " \n");
		}
		if (qInfo.getApplyCode() != null && qInfo.getApplyCode().length() > 0)
		{
			sbSQL.append("and fin.sApplyCode like '%" + qInfo.getApplyCode() + "%'");
		}
		
	
		System.out.println("select " +sbSQL.toString() + " from "+ sbSQL.toString() + " where "+sbSQL.toString() + sbSQL.toString());
	
		return sbSQL.toString();
	}

	public String queryBatchOBFinanceInfoSQL(QueryOBFinanceInstrInfo qInfo){
		
		StringBuffer sbSQL= new StringBuffer();

		boolean isHTKG = Config.getBoolean(ConfigConstant.SETTLEMENT_ISHTKG, false);  //是否为航天科工项目
						
		//select	
		sbSQL.append(" select \n ");		
		sbSQL.append(" fin.id as ID,  \n");			
		sbSQL.append(" fin.nTransType as TransType,  \n");
		sbSQL.append(" client.sName as PayClientName,  \n");
		sbSQL.append(" fin.nPayerAcctID as PayerAcctID,  \n");
		sbSQL.append(" fin.nPayeeAcctID as PayeeAcctID,  \n");
		sbSQL.append(" fin.nRemitType as RemitType,  \n");
		sbSQL.append(" fin.mAmount as Amount,  \n");
		sbSQL.append(" fin.CPF_NDEALUSERID as DealUserID,  \n");		
		sbSQL.append(" fin.mRealInterest as Interest,  \n");
		sbSQL.append(" fin.dtExecute as ExecuteDate,  \n");
		sbSQL.append(" fin.sNote as Note,  \n");
		sbSQL.append(" fin.nStatus as Status,  \n");
		sbSQL.append(" fin.nDepositBillStatusID as DepositBillStatusID,  \n");	//得到换开定期存单的ID
		sbSQL.append(" fin.cpf_nDefaultTransType as DefaultTransType,  \n");
		sbSQL.append(" fin.cpf_sTransNo as TransNo, \n");
		sbSQL.append(" fin.dtcheck as CheckDate, \n");
		sbSQL.append(" fin.dtmodify as dtModify, \n");
		sbSQL.append(" fin.remitArea as remitArea, \n");
		sbSQL.append(" fin.remitSpeed as remitSpeed, \n");
		sbSQL.append(" fin.lsource as Source, \n");
		sbSQL.append(" p.bankname as bankname, \n");
		sbSQL.append(" p.spayeebankname as PayeeBankName \n");
		
		// from 
		sbSQL.append(" from \n");		
		sbSQL.append(" OB_FinanceInstr fin left join ob_payeeinfo p on fin.npayeeacctid = p.id, \n");
		sbSQL.append(" sett_account acc,client,client c \n ");
		
		// where 
		sbSQL.append(" where \n");
		sbSQL.append("fin.cpf_nofficeid="+ qInfo.getOfficeID() + " \n");	
		sbSQL.append(" and fin.NPAYERACCTID=acc.id \n");
		sbSQL.append(" and acc.nclientid=client.id \n");	
		sbSQL.append(" and fin.nclientid=c.id \n");
		sbSQL.append(" and fin.nCurrencyID=" + qInfo.getCurrencyID()+ " \n");
		
		//Boxu Update 2009年02月19日 批量接收只接收"资金划拨-银行付款"和"资金划拨-内部转账"
		if(isHTKG)
		{
			if(qInfo.getTransType()>0)
			{
				if(qInfo.getTransType()==SETTConstant.SettInstrType.CAPTRANSFER_BANKPAY)
				{
					sbSQL.append(" and fin.nTransType in ("+SETTConstant.SettInstrType.TRANSFER_BANKEXTERNAL+","+SETTConstant.SettInstrType.BOOKING_BANKEXTERNAL+") \n");
				}
				else if(qInfo.getTransType()==SETTConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT)
				{
					sbSQL.append(" and fin.nTransType in ("+SETTConstant.SettInstrType.TRANSFER_INTERNALVIREMENT+","+SETTConstant.SettInstrType.SCM_TRANSFER_INTERNALVIREMENT+","+SETTConstant.SettInstrType.BOOKING_INTERNALVIREMENT+","+SETTConstant.SettInstrType.SCM_BOOKING_INTERNALVIREMENT+") \n");
				}
				else
				{
					sbSQL.append(" and fin.nTransType=" + qInfo.getTransType()+ " \n");
				}
					
			}
			else
			{
				sbSQL.append(" and fin.nTransType in ("+SETTConstant.SettInstrType.TRANSFER_INTERNALVIREMENT+","+SETTConstant.SettInstrType.SCM_TRANSFER_INTERNALVIREMENT+","+SETTConstant.SettInstrType.BOOKING_INTERNALVIREMENT+","+SETTConstant.SettInstrType.SCM_BOOKING_INTERNALVIREMENT+","+SETTConstant.SettInstrType.TRANSFER_BANKEXTERNAL+","+SETTConstant.SettInstrType.BOOKING_BANKEXTERNAL+") \n");
			}
		}
		else 
		{
			if(qInfo.getTransType()>0)
			{
				sbSQL.append(" and fin.nTransType=" + qInfo.getTransType()+ " \n");
			}
			else
			{
				sbSQL.append(" and fin.nTransType in (" + SETTConstant.SettInstrType.CAPTRANSFER_BANKPAY + ", " + SETTConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT + ") \n");
			}
		}
		
		if(qInfo.getStatus()==0)   //全部
		{
			sbSQL.append("and fin.nIsCanAccept = 1  \n");
		}
		else if(qInfo.getStatus()==SETTConstant.SettInstrStatus.SIGN)   //已提交
		{
			sbSQL.append("and (fin.nStatus =" + OBConstant.SettInstrStatus.CHECK + " or fin.nStatus =" + OBConstant.SettInstrStatus.SIGN + ") \n");
			sbSQL.append("and fin.nIsCanAccept = 1  \n");
		}
		else if(qInfo.getStatus()==SETTConstant.SettInstrStatus.DEAL)   //处理中
		{
			sbSQL.append("and fin.nStatus =" + OBConstant.SettInstrStatus.DEAL +  " \n");				
		}
		else
		{
			sbSQL.append("and fin.nStatus =" + qInfo.getStatus()+ " \n");
			sbSQL.append("and fin.nIsCanAccept = 1  \n");
		}
		if(qInfo.getAmountFrom()>0)
		{
			sbSQL.append("and fin.mAmount>=" + qInfo.getAmountFrom()+ " \n");
		}
		if(qInfo.getAmountTo()>0)
		{
			sbSQL.append("and fin.mAmount<=" + qInfo.getAmountTo()+ " \n");
		}
		// 判断选择的查询日期
		if(qInfo.getDateRadio() != null && qInfo.getDateRadio().equals("execute")){
			// 执行日期
			if(qInfo.getExecuteDateFrom()!=null)
			{
				sbSQL.append("and to_char(fin.dtExecute,'yyyy-mm-dd') >='"+DataFormat.formatDate(qInfo.getExecuteDateFrom())+"'");
			}
			if(qInfo.getExecuteDateTo()!=null)
			{
				sbSQL.append("and to_char(fin.dtExecute,'yyyy-mm-dd') <= '"+DataFormat.formatDate(qInfo.getExecuteDateTo())+"'");
			}
		}else if(qInfo.getDateRadio() != null && qInfo.getDateRadio().equals("check")){
			// 复核日期
			if(qInfo.getCheckDateFrom()!=null)
			{
				sbSQL.append("and to_char(fin.dtCheck,'yyyy-mm-dd') >= '"+DataFormat.formatDate(qInfo.getCheckDateFrom())+"'");
			}
			if(qInfo.getCheckDateTo()!=null)
			{
				sbSQL.append("and to_char(fin.dtCheck,'yyyy-mm-dd') <= '"+DataFormat.formatDate(qInfo.getCheckDateTo())+"'");
			}
		}else if(qInfo.getDateRadio() != null && qInfo.getDateRadio().equals("sign")){
			// 签认日期
			if(qInfo.getSignDateFrom()!=null)
			{
				sbSQL.append("and to_char(fin.dtSign,'yyyy-mm-dd') >='"+DataFormat.formatDate(qInfo.getSignDateFrom())+"'");
			}
			if(qInfo.getSignDateTo()!=null)
			{
				sbSQL.append("and to_char(fin.dtSign,'yyyy-mm-dd') <='"+DataFormat.formatDate(qInfo.getSignDateTo())+"'");
			}
		}
		if (qInfo.getAccountNoFrom() != null && qInfo.getAccountNoFrom().length() > 0)
			sbSQL.append("and acc.sAccountNo>='" + qInfo.getAccountNoFrom() + "'");
		if (qInfo.getAccountNoTo() != null && qInfo.getAccountNoTo().length() > 0)
			sbSQL.append("and acc.sAccountNo<='" + qInfo.getAccountNoTo() + "'");
		
		if (qInfo.getClientNoFrom() != null && qInfo.getClientNoFrom().length() > 0)
			sbSQL.append("and c.sCode>='" + qInfo.getClientNoFrom() + "'");
		if (qInfo.getClientNoTo() != null && qInfo.getClientNoTo().length() > 0)
			sbSQL.append("and c.sCode<='" + qInfo.getClientNoTo() + "'");
		
		if (qInfo.getTransNo() != null && qInfo.getTransNo().length() > 0)
		{
			sbSQL.append("and fin.cpf_sTransNo='" + qInfo.getTransNo() + "'");
		}
		if(qInfo.getInstructionNo()>0)
		{
			sbSQL.append("and fin.id=" + qInfo.getInstructionNo()+ " \n");
		}
		if(qInfo.getSource()>0)
		{
			sbSQL.append("and fin.lsource=" + qInfo.getSource()+ " \n");
		}
		if (qInfo.getApplyCode() != null && qInfo.getApplyCode().length() > 0)
		{
			sbSQL.append("and fin.sApplyCode like '%" + qInfo.getApplyCode() + "%'");
		}
		if(qInfo.getBankName() !=null && qInfo.getBankName().trim().length()>0)
		{
			sbSQL.append(" and p.bankname ='"+qInfo.getBankName()+"'");
		}
		
		System.out.println("select " +sbSQL.toString() + " from "+ sbSQL.toString() + " where "+sbSQL.toString() + sbSQL.toString());
	
		return sbSQL.toString();
	}

	public String queryBatchOBFinanceInfoCheckSQL(QueryOBFinanceInstrInfo qInfo){
		
		StringBuffer sbSQL= new StringBuffer();


		boolean isHTKG = Config.getBoolean(ConfigConstant.SETTLEMENT_ISHTKG, false);  //是否为航天科工项目
		sbSQL = new StringBuffer();
						
		//select	
		sbSQL.append(" select \n ");		
		sbSQL.append(" distinct fin.id as ID,  \n");			
		sbSQL.append(" fin.nTransType as TransType,  \n");
		sbSQL.append(" client.sName as PayClientName,  \n");
		sbSQL.append(" fin.nPayerAcctID as PayerAcctID,  \n");
		sbSQL.append(" fin.nPayeeAcctID as PayeeAcctID,  \n");
		sbSQL.append(" fin.nRemitType as RemitType,  \n");
		sbSQL.append(" fin.mAmount as Amount,  \n");
		sbSQL.append(" fin.CPF_NDEALUSERID as DealUserID,  \n");		
		sbSQL.append(" fin.mRealInterest as Interest,  \n");
		sbSQL.append(" fin.dtExecute as ExecuteDate,  \n");
		sbSQL.append(" fin.sNote as Note,  \n");
		sbSQL.append(" fin.nStatus as Status,  \n");
		sbSQL.append(" fin.nDepositBillStatusID as DepositBillStatusID,  \n");	//得到换开定期存单的ID
		sbSQL.append(" fin.cpf_nDefaultTransType as DefaultTransType,  \n");
		sbSQL.append(" fin.cpf_sTransNo as TransNo, \n");
		sbSQL.append(" fin.dtcheck as CheckDate, \n");
		sbSQL.append(" fin.lsource as Source \n");

		// from 
		sbSQL.append(" from \n");	
		sbSQL.append(" OB_FinanceInstr fin,sett_account acc,client,client c,sett_vtransaction s  \n");

		// where 
		sbSQL.append(" where \n");	
		sbSQL.append("fin.cpf_nofficeid="+ qInfo.getOfficeID() + " \n");	
		sbSQL.append(" and fin.NPAYERACCTID=acc.id \n");
		sbSQL.append(" and acc.nclientid=client.id \n");	
		sbSQL.append(" and fin.nclientid=c.id \n");
		sbSQL.append(" and to_char(fin.id)=s.applycode(+) \n");
		sbSQL.append(" and fin.nCurrencyID=" + qInfo.getCurrencyID()+ " \n");
		if(qInfo.getTransType()>0)
		{
			if(qInfo.getTransType()==SETTConstant.SettInstrType.CHANGEFIXDEPOSIT){
				System.out.println("查询了换开定期存单的数据!");
				qInfo.setTransType(SETTConstant.SettInstrType.OPENFIXDEPOSIT);	//定期开立
				//sbSQL.append(" and ( fin.SDEPOSITBILLNO is not null ) ");	//换开定期存单不为空或者不是不存在
				sbSQL.append(" and ( fin.NDEPOSITBILLINPUTUSERID is not null ) ");	//换开定期存单输入人ID不为空或者不是不存在
			}
			else if(qInfo.getTransType()==SETTConstant.SettInstrType.OPENFIXDEPOSIT){
				System.out.println("查询了定期存单的数据!");
				//sbSQL.append(" and ( fin.SDEPOSITBILLNO is null ) ");	//定期存单为空(定期开立)
				sbSQL.append(" and ( fin.NDEPOSITBILLINPUTUSERID is null ) ");	//换开定期存单输入人ID不为空或者不是不存在
			}else{}
			if(isHTKG)
			{
				if(qInfo.getTransType()==SETTConstant.SettInstrType.CAPTRANSFER_BANKPAY)
				{
					sbSQL.append(" and fin.nTransType in ("+SETTConstant.SettInstrType.TRANSFER_BANKEXTERNAL+","+SETTConstant.SettInstrType.BOOKING_BANKEXTERNAL+") \n");
				}
				else if(qInfo.getTransType()==SETTConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT)
				{
					sbSQL.append(" and fin.nTransType in ("+SETTConstant.SettInstrType.TRANSFER_INTERNALVIREMENT+","+SETTConstant.SettInstrType.SCM_TRANSFER_INTERNALVIREMENT+","+SETTConstant.SettInstrType.BOOKING_INTERNALVIREMENT+","+SETTConstant.SettInstrType.SCM_BOOKING_INTERNALVIREMENT+") \n");
				}
				else
				{
					sbSQL.append(" and fin.nTransType=" + qInfo.getTransType()+ " \n");
				}
			}
			else
			{
				sbSQL.append(" and fin.nTransType=" + qInfo.getTransType()+ " \n");
			}		

		}
		if(qInfo.getStatus()==4)   //全部
		{
			sbSQL.append("and fin.nStatus =" + OBConstant.SettInstrStatus.DEAL +  " \n");
			sbSQL.append("and  fin.cpf_ndealuserid ! =" + qInfo.getUserID() +  " \n");
			
		}
		else if(qInfo.getStatus()==5)  
		{
			sbSQL.append("and fin.nStatus =" + OBConstant.SettInstrStatus.FINISH +  " \n");
			sbSQL.append("and s.statusid =" + SETTConstant.TransactionStatus.CHECK +  " \n");
			sbSQL.append("and to_char(s.execute,'yyyy-mm-dd') ='"+DataFormat.formatDate(qInfo.getOpenDate())+"' \n");
			//if ( Config.getBoolean(ConfigConstant.SETT_OBINSTRUCTION_AUTOCHECK,false) )
			//{
				sbSQL.append("and s.checkuserid in (" + qInfo.getUserID() + ",-101) \n");
			//}
			//else
			//{
			//	sbSQL.append("and s.checkuserid =" + qInfo.getUserID() + " \n");
			//}
		}
		if(qInfo.getAmountFrom()>0)
		{
			sbSQL.append("and fin.mAmount>=" + qInfo.getAmountFrom()+ " \n");
		}
		if(qInfo.getAmountTo()>0)
		{
			sbSQL.append("and fin.mAmount<=" + qInfo.getAmountTo()+ " \n");
		}
		// 判断选择的查询日期
		if(qInfo.getDateRadio() != null && qInfo.getDateRadio().equals("execute")){
			// 执行日期
			if(qInfo.getExecuteDateFrom()!=null)
			{
				sbSQL.append("and to_char(fin.dtExecute,'yyyy-mm-dd') >='"+DataFormat.formatDate(qInfo.getExecuteDateFrom())+"'");
			}
			if(qInfo.getExecuteDateTo()!=null)
			{
				sbSQL.append("and to_char(fin.dtExecute,'yyyy-mm-dd') <= '"+DataFormat.formatDate(qInfo.getExecuteDateTo())+"'");
			}
		}else if(qInfo.getDateRadio() != null && qInfo.getDateRadio().equals("check")){
			// 复核日期
			if(qInfo.getCheckDateFrom()!=null)
			{
				sbSQL.append("and to_char(fin.dtCheck,'yyyy-mm-dd') >= '"+DataFormat.formatDate(qInfo.getCheckDateFrom())+"'");
			}
			if(qInfo.getCheckDateTo()!=null)
			{
				sbSQL.append("and to_char(fin.dtCheck,'yyyy-mm-dd') <= '"+DataFormat.formatDate(qInfo.getCheckDateTo())+"'");
			}
		}else if(qInfo.getDateRadio() != null && qInfo.getDateRadio().equals("sign")){
			// 签认日期
			if(qInfo.getSignDateFrom()!=null)
			{
				sbSQL.append("and to_char(fin.dtSign,'yyyy-mm-dd') >='"+DataFormat.formatDate(qInfo.getSignDateFrom())+"'");
			}
			if(qInfo.getSignDateTo()!=null)
			{
				sbSQL.append("and to_char(fin.dtSign,'yyyy-mm-dd') <='"+DataFormat.formatDate(qInfo.getSignDateTo())+"'");
			}
		}
		if (qInfo.getAccountNoFrom() != null && qInfo.getAccountNoFrom().length() > 0)
			sbSQL.append("and acc.sAccountNo>='" + qInfo.getAccountNoFrom() + "'");
		if (qInfo.getAccountNoTo() != null && qInfo.getAccountNoTo().length() > 0)
			sbSQL.append("and acc.sAccountNo<='" + qInfo.getAccountNoTo() + "'");
		
		if (qInfo.getClientNoFrom() != null && qInfo.getClientNoFrom().length() > 0)
			sbSQL.append("and c.sCode>='" + qInfo.getClientNoFrom() + "'");
		if (qInfo.getClientNoTo() != null && qInfo.getClientNoTo().length() > 0)
			sbSQL.append("and c.sCode<='" + qInfo.getClientNoTo() + "'");
		
		if (qInfo.getTransNo() != null && qInfo.getTransNo().length() > 0)
		{
			sbSQL.append("and fin.cpf_sTransNo='" + qInfo.getTransNo() + "'");
		}
		if(qInfo.getInstructionNo()>0)
		{
			sbSQL.append("and fin.id=" + qInfo.getInstructionNo()+ " \n");
		}
		if(qInfo.getSource()>0)
		{
			sbSQL.append("and fin.lsource=" + qInfo.getSource()+ " \n");
		}
		if (qInfo.getApplyCode() != null && qInfo.getApplyCode().length() > 0)
		{
			sbSQL.append("and fin.sApplyCode like '%" + qInfo.getApplyCode() + "%'");
		}
		
		System.out.println("select " +sbSQL.toString() + " from "+ sbSQL.toString() + " where "+sbSQL.toString() + sbSQL.toString());
	
		return sbSQL.toString();
	}
	
}