package com.iss.itreasury.evoucher.print.dao;

import com.iss.itreasury.evoucher.print.dataentity.QueryPrintConditionInfo_New;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;

public class VoucherPrintDao {

	/**
	 * 电子单据柜 单据打印
	 * @param clientInfo
	 */
	public String queryVouchersInfo(QueryPrintConditionInfo_New info){
		
		StringBuffer sql = new StringBuffer();
		sql.append("select a.id ID,a.officeid OfficeID,a.currencyid CurrencyID,a.transno TransNo,a.operationtypeid OperationTypeID, ");
		sql.append(" a.transactiontypeid TransactionTypeID,a.statusid StatusID,a.execute dtTrans,a.inputuserid InputUserID,a.abstract Abstract, ");
		sql.append(" a.payaccountid PayAccountID,a.payaccountno,a.receiveaccountid ReceiveAccountID,a.receiveaccountno,a.receiveamount transAmount ");
		sql.append(" from sett_vtransaction a ");
		sql.append(" where a.statusid not in("+SETTConstant.TransactionStatus.DELETED+","+SETTConstant.TransactionStatus.TEMPSAVE+","+SETTConstant.TransactionStatus.REFUSE+") ");
		//编号为空的不能查出来
		sql.append(" and a.transno is not null ");
		//根据页面条件增加查询条件
    	if(info.getStrTransactionType() != null && info.getStrTransactionType().length() > 0){
    		sql.append(" and a.transactiontypeid in ("+info.getStrTransactionType()+") or a.OperationTypeID in ("+ info.getStrTransactionType()+") ");
    	}
    	if(info.getLClientIDStart() > 0){
    		sql.append(" and a.payclientid >= "+info.getLClientIDStart());
    	}
    	if(info.getLClientIDEnd() > 0){
    		sql.append(" and a.payclientid <= "+info.getLClientIDEnd());
    	}
    	if(info.getDMoneyStart()>0){
    		sql.append(" and a.receiveamount >= "+info.getDMoneyStart());
    	}
    	if(info.getDMoneyEnd()>0){
    		sql.append(" and a.receiveamount <= "+info.getDMoneyEnd());
    	}
    	if(info.getStrTransNoStart() != null && info.getStrTransNoStart().length() > 0){
    		sql.append(" and a.transno >= '"+info.getStrTransNoStart()+"'");
    	}
    	if(info.getStrTransNoEnd() != null && info.getStrTransNoEnd().length() > 0){
    		sql.append(" and a.transno <= '"+info.getStrTransNoEnd()+"'");
    	}
    	if(info.getTsTransStartDate() != null){
    		sql.append(" and a.execute >= to_date('" + DataFormat.getDateString(info.getTsTransStartDate()) + "','yyyy-mm-dd')");
    	}
    	if(info.getTsTransEndDate() != null){
    		sql.append(" and a.execute <= to_date('" + DataFormat.getDateString(info.getTsTransEndDate()) + "','yyyy-mm-dd')");
    	}
    	if(info.getLInputuserID() > 0 || info.getLInputuserID() == -100) {
    		sql.append(" and a.inputuserid = " + info.getLInputuserID());
    	}
    	if(info.getLCheckUserID() > 0 || info.getLCheckUserID() == -101) {
    		sql.append(" and a.checkuserid = " + info.getLCheckUserID());
    	}
    	//modify by xwhe 2008-09-25
    	if(info.getLTransactionStatusID() > 0){
    		sql.append(" and a.statusid = " + info.getLTransactionStatusID());
    	}
    	if(info.getOfficeID() > 0){
    		sql.append(" and a.officeid = " + info.getOfficeID());
    	}
    	if(info.getCurrencyID() > 0){
    		sql.append(" and a.currencyid = " + info.getCurrencyID());
    	}
    	//add by minzhao 2009.3.20
    	if(info.getLClientIDStartIn() > 0){
    		sql.append(" and a.ReceiveClientID >= " + info.getLClientIDStartIn());
    	}
    	if(info.getLClientIDEndIn() > 0){
    		sql.append(" and a.ReceiveClientID <= " + info.getLClientIDEndIn());
    	}
    	if(info.getSigner()>0){
    		sql.append(" and nvl(a.PayclientID,0) not in (select nclientid from sett_signature where NISSIGNATURE=1 ) ");
    		sql.append(" and nvl(a.ReceiveClientID,0) not in (select nclientid from sett_signature where NISSIGNATURE=1 ) ");
		}
		
		return sql.toString();
	}
}
