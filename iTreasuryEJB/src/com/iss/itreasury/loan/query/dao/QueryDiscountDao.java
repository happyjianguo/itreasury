package com.iss.itreasury.loan.query.dao;

import java.io.UnsupportedEncodingException;

import com.iss.itreasury.loan.query.dataentity.QueryDiscountInfo;
import com.iss.itreasury.loan.query.dataentity.QuestContractInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;

public class QueryDiscountDao {
	
	/**
     * 贴现合同查询
     * @param QueryDiscountInfo discountInfo
     * @return
     * @throws Exception
     */
	public String queryDiscountContractInfo(QueryDiscountInfo discountInfo) throws Exception {
		
		String[] sql = null;
		String getQuerySql = "";
		if(discountInfo.getQueryPurpose()==QuestContractInfo.TX){
			
			sql = getContractSQL_wlx(discountInfo);	//贴现SQL
		}else if(discountInfo.getQueryPurpose()==QuestContractInfo.RZZL){
			
			//sql = getRZZLContractSQL(discountInfo);		//融资租赁SQL
		}else if(discountInfo.getQueryPurpose()==QuestContractInfo.DB){
			
			//sql = getDBContractSQL(discountInfo);			//担保SQL
		}else{
			
			//sql = getContractSQL(discountInfo);			//贷款合同查询
		}
		
		getQuerySql = "select " + sql[0] +" from " +sql[1] +" where " + sql[2];
		
		return getQuerySql;	
			
	}
	public String[] getContractSQL_wlx(QueryDiscountInfo qInfo){
		
		String[] sql = new String[3];
		StringBuffer buf = new StringBuffer();

		//select
		//修改by kenny(2007-03-06)，修改贷款合同查询时自动断开数据库连接的问题
		sql[0] = " /*+ INDEX_COMBINE(Y) */";
		// modified by fxzhang 2012-6-6 对于初始化数据，loan_DiscountFormBill中没有相关记录，可直接取合同中的
		//sql[0] +=" distinct w.NACCEPTPOTYPEID as tsDiscountTypeID,c.ID as contractID,c.nTypeID as loanTypeID,c.sContractCode as contractCode,c.NINOROUT as inOrOut,c.mdiscountaccrual as mDiscountAccrual ,"
		sql[0] += " distinct nvl(w.NACCEPTPOTYPEID, (case when(c.nbankacceptpo > 0) then 1 else  2 end)) as tsDiscountTypeID,c.ID as contractID,c.nTypeID as loanTypeID,c.sContractCode as contractCode,c.NINOROUT as inOrOut,c.mdiscountaccrual as mDiscountAccrual ,"			
				+ " c1.Name as borrowClientName,c2.Name as ClientName,c.mLoanAmount as loanAmount,c.assureChargeRate as assureChargeRate,c.mPurchaserAmount as discountPurchaserInterest,"
				+ " c.mInterestRate as interestRate,c.dtStartDate as LoanStart,c.dtEndDate as LoanEnd,c.DTINPUTDATE as InputDate,"
				+ " c.nBankInterestID as bankInterestID ,c.nInterestTypeID interestTypeID,c.mAdjustRate as adjustRate ,c.mStaidAdjustRate as staidAdjustRate ,"
				+ " c.nIntervalNum as intervalNum,c.nStatusID as statusID,u.sName as inputUserName,u2.sName as checkUserName,"
				+ " c.mDisCountRate as discountRate,c.mCheckAmount as CheckAmount,c.mExamineAmount as examineAmount,"
				+ " nvl(c.purchaserInterestRate,0) as purchaserInterestRate,(nvl(c.mExamineAmount,0) - nvl(c.mCheckAmount,0)) as discountInterest,"
				+ " ((nvl(c.mExamineAmount,0) - nvl(c.mCheckAmount,0))/decode((1-nvl(c.purchaserInterestRate,0)*0.01),0,1,(1-nvl(c.purchaserInterestRate,0)*0.01))*nvl(c.purchaserInterestRate,0)*0.01) as discountPurchaserInterest1,"
				+ " d.sApplyCode as applyCode,lp.balance,c3.Name as discountClientName"
				+ ", a.overdueAmount as overdueAmount"
				+ ", b.punishInterest as punishInterest";
		//from
		//sql[1] = "loan_DiscountCredence w,Loan_DiscountContractBill m,loan_contractForm c,client c1,client c2,client c3,userInfo u,userInfo u2,loan_loanForm d, loan_lateRateView y,";
		sql[1] = "(select distinct nLoanID,NACCEPTPOTYPEID from loan_DiscountFormBill) w ,loan_contractForm c,client_clientinfo c1,client_clientinfo c2,client_clientinfo c3,userInfo u,userInfo u2,loan_loanForm d, loan_lateRateView y,";
		//从子账户表中取得当前余额（包括贴现）
		sql[1] += "(select lp.nCOntractID,sum(sa.mbalance) as balance from sett_subAccount sa,(select ID,nContractID from loan_PayForm union select ID,nContractID from loan_DiscountCredence) lp where sa.AL_nLoanNoteID = lp.ID group by lp.nCOntractID) lp ";
		//胡志强修改(2004-03-09)
		sql[1] += ",(SELECT a.id,sum(NVL(c.mBalance,0.0)) as overdueAmount";
		sql[1] += " FROM loan_contractForm a,loan_payForm b,sett_subAccount c,client_clientinfo c1,client_clientinfo c2,userInfo u,userInfo u2";
		sql[1] += " WHERE c1.id(+)=a.nBorrowClientID";
		sql[1] += " and c2.id(+)=a.nconsignclientid";
		sql[1] += " and u.id(+)=a.nInputUserID";
		sql[1] += " and u2.id(+)=a.nNextCheckUserID";
		sql[1] += " and a.id = b.nContractID(+)";
		sql[1] += " and a.id = c.al_nLoanNoteID(+)";
		sql[1] += " and a.nStatusID = " + LOANConstant.ContractStatus.OVERDUE;
		sql[1] += " GROUP BY a.id) a";
		sql[1] += ",(SELECT a.id,sum(NVL(c.mInterest,0.0)) as punishInterest";
		sql[1] += " FROM loan_contractForm a,loan_payForm b,sett_subAccount c,client_clientinfo c1,client_clientinfo c2,userInfo u,userInfo u2";
		sql[1] += " WHERE c1.id(+)=a.nBorrowClientID";
		sql[1] += " and c2.id(+)=a.nconsignclientid";
		sql[1] += " and u.id(+)=a.nInputUserID";
		sql[1] += " and u2.id(+)=a.nNextCheckUserID";
		sql[1] += " and a.id = b.nContractID(+)";
		sql[1] += " and a.id = c.al_nLoanNoteID(+)";
		sql[1] += " and sysdate-c.dtClearInterest >= 90"; //上一结息日90天后的日期
		sql[1] += " GROUP BY a.id) b";
		//where
		//胡志强修改(2004-03-09)
		sql[2]  = " c.nLoanId = w.nLoanID(+)";
		//sql[2] += " and m.nAcceptpotypeID =w.ndrafttypeid(+)";
		//sql[2] += " and w.ndrafttypeid>0";
		sql[2] += " and nvl(w.NACCEPTPOTYPEID,9999) > 0 ";
		//sql[2] += " and m.ncontractid=w.ncontractid(+) ";
		if(qInfo.getQueryPurpose() == QuestContractInfo.TX){
			
			sql[2] += " and c.nTypeID=" + LOANConstant.LoanType.TX;
			if(qInfo.getMinRate()>0){
				sql[2] += " and c.mDiscountRate >= " + qInfo.getMinRate();
			}
			if(qInfo.getMaxRate()>0){
				sql[2] += " and c.mDiscountRate <= " + qInfo.getMaxRate();
			}
			/*//贴现客户名称
			if ((qInfo.getDiscountClientName() != null) && (qInfo.getDiscountClientName().length() > 0))
			{
				buf.append(" and c.discountClientName like '%" + qInfo.getDiscountClientName() + "%'");
			}*/
			//贴现汇票种类
			if(qInfo.getTsDiscountTypeID()>0){		
				buf.append(" and w.NACCEPTPOTYPEID="+qInfo.getTsDiscountTypeID() );
			}
		}else if (qInfo.getQueryPurpose() == QuestContractInfo.ZTX){
			
			sql[2] += " and c.nTypeID=" + LOANConstant.LoanType.ZTX;
			if(qInfo.getMinRate()>0)
			{
				sql[2] += " and c.mDiscountRate >= " + qInfo.getMinRate();
			}
			if(qInfo.getMaxRate()>0)
			{
				sql[2] += " and c.mDiscountRate <= " + qInfo.getMaxRate();
			}
		}else{
			
			sql[2] += " and c.nTypeID<>" + LOANConstant.LoanType.TX;
			sql[2] += " and c.nTypeID<>" + LOANConstant.LoanType.ZTX;
			if(qInfo.getMinRate()>0)
			{
				sql[2] += " and y.lateRate >= " + qInfo.getMinRate();
			}
			if(qInfo.getMaxRate()>0)
			{
				sql[2] += " and y.lateRate <= " + qInfo.getMaxRate();
			}
		}
		sql[2] += " and c1.id(+)=c.nBorrowClientID and c2.id(+)=c.nconsignclientid and c3.id(+)=c.DiscountClientID and u.id(+)=c.nInputUserID and u2.id(+)=c.NNEXTCHECKUSERID and d.id(+)=c.nLoanID" + " and lp.nContractID(+) =c.id";
		sql[2] += " and c.id = a.id(+)";
		sql[2] += " and c.id = b.id(+)";
		sql[2] += " and c.id = y.contractID(+)";
		//（上海电气）担保类型 modified by zntan 2004-12-02
		if (qInfo.getQueryPurpose()== QuestContractInfo.DB){
			
			sql[2] += " and c.nTypeID=" + LOANConstant.LoanType.DB;
		}else{
			
			sql[2] += " and c.nTypeID<>" + LOANConstant.LoanType.DB;
		}
		//合同编号开始
		if (qInfo.getMinContractCode() != null && qInfo.getMinContractCode().length() > 0)
			buf.append(" and c.sContractCode>='" + qInfo.getMinContractCode() + "'");

		//合同编号结束
		if (qInfo.getMaxContractCode() != null && qInfo.getMaxContractCode().length() > 0)
			buf.append(" and c.sContractCode<='" + qInfo.getMaxContractCode() + "'");

		long contractStatusVal[] = LOANConstant.ContractStatus.getAllCode(qInfo.getOfficeID(),qInfo.getCurrencyID());
		String strStatus = "";
		for (int i = 0; i < contractStatusVal.length; i++){
			//去掉已提交
			if(contractStatusVal[i]==LOANConstant.ContractStatus.SUBMIT){
				continue;
			}
			//去掉呆滞
  			if(contractStatusVal[i]==LOANConstant.ContractStatus.DELAYDEBT){
  				continue;
  			}
  			//去掉呆账
  			if(contractStatusVal[i]==LOANConstant.ContractStatus.BADDEBT){
  				continue;
  			}
  			//去掉已拒绝
  			if(contractStatusVal[i]==LOANConstant.ContractStatus.REFUSE){
  				continue;
  			}
  		    //去掉已逾期
  			if(contractStatusVal[i]==LOANConstant.ContractStatus.EXTEND){
  				continue;
  			}
  			//去掉已展期
  			if(contractStatusVal[i]==LOANConstant.ContractStatus.OVERDUE){
  				continue;
  			}
			strStatus += contractStatusVal[i];
			if ((contractStatusVal.length - i) > 1)
				strStatus += ",";
		}
		if(qInfo.getStatusIDs()== null ||qInfo.getStatusIDs().equals("") || qInfo.getStatusIDs().trim().equals("-1")){
			
		    buf.append(" and c.nStatusID in (" + strStatus + ")");
		}else{
			
			buf.append(" and c.nStatusID in (" + qInfo.getStatusIDs()+")");
		}
		/*if (qInfo.getStatusID() > 0)
			{
			System.out.println("qInfo.getStatusID()================="+qInfo.getStatusID());
			buf.append(" and c.nStatusID=" + qInfo.getStatusID());
			}*/
		//minzhao20090505修改将合同状态增加为多状态查询
		//出票人
		if (qInfo.getBillDrawer()!=null && qInfo.getBillDrawer().length()>0 ){
			
			String test = qInfo.getBillDrawer();
			try {
				test = new String(test.getBytes("ISO-8859-1"),"GBK");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			buf.append(" and c.DISCOUNTCLIENTNAME like '%" +test+"%'");
		}
		//贴现申请单位开始
		if (qInfo.getMinborrowClientID() > 0  ){
			buf.append(" and c.nborrowclientid >= "+qInfo.getMinborrowClientID());
		}
		//贴现申请单位结束
		if (qInfo.getMaxborrowClientID() > 0  ){
			buf.append(" and c.nborrowclientid <= "+qInfo.getMaxborrowClientID());
		}
		//(贴现)贷款金额结束
		if (qInfo.getMaxLoanAmount() > 0){
			buf.append(" and c.mExamineAmount<=" + DataFormat.formatAmount(qInfo.getMaxLoanAmount()));
		}
		//(贴现)贷款金额开始
		if (qInfo.getMinLoanAmount() > 0){
			buf.append(" and c.mExamineAmount>=" + DataFormat.formatAmount(qInfo.getMinLoanAmount()));
		}
		//贴现实付金额结束
		if (qInfo.getMaxCheckAmount() >0){
			buf.append(" and c.mCheckAmount<=" + DataFormat.formatAmount(qInfo.getMaxCheckAmount()));
		}
		//贴现实付金额开始
		if (qInfo.getMinCheckAmount() > 0){
			buf.append(" and c.mCheckAmount>=" + DataFormat.formatAmount(qInfo.getMinCheckAmount()));
		}
		//贷款日期开始
		if (qInfo.getMaxStartDate() != null){
			buf.append(" and TRUNC(c.dtStartDate)<= To_Date('" + DataFormat.getDateString(qInfo.getMaxStartDate()) + "','yyyy-mm-dd') ");
		}
		//贷款日期结束
		if (qInfo.getMinStartDate() != null){
			buf.append(" and TRUNC(c.dtStartDate)>= To_Date('" + DataFormat.getDateString(qInfo.getMinStartDate()) + "','yyyy-mm-dd') ");
		}
		//贷款结束日期结束
		if (qInfo.getMaxEndDate() != null){
			buf.append(" and TRUNC(c.dtEndDate)<= To_Date('" + DataFormat.getDateString(qInfo.getMaxEndDate()) + "','yyyy-mm-dd') ");
		}
		//贷款结束日期开始
		if (qInfo.getMinEndDate() != null){
			buf.append(" and TRUNC(c.dtEndDate)>= To_Date('" + DataFormat.getDateString(qInfo.getMinEndDate()) + "','yyyy-mm-dd') ");
		}
		//贴现日期结束
		if (qInfo.getMaxDiscountDate() != null){
			buf.append(" and TRUNC(c.dtDiscountDate) <= To_Date('" + DataFormat.getDateString(qInfo.getMaxDiscountDate()) + "','yyyy-mm-dd') ");
		}
		//贴现日期开始
		if (qInfo.getMinDiscountDate() != null){
			buf.append(" and TRUNC(c.dtDiscountDate) >= To_Date('" + DataFormat.getDateString(qInfo.getMinDiscountDate()) + "','yyyy-mm-dd') ");
		}
		//录入日期结束
		if (qInfo.getMaxDisccountInputDate() != null){
			buf.append(" and TRUNC(c.dtInputDate) <= To_Date('" + DataFormat.getDateString(qInfo.getMaxDisccountInputDate()) + "','yyyy-mm-dd') ");
		}
		//录入日期开始
		if (qInfo.getMinDisccountInputDate() != null){
			buf.append(" and TRUNC(c.dtInputDate) >= To_Date('" + DataFormat.getDateString(qInfo.getMinDisccountInputDate()) + "','yyyy-mm-dd') ");
		}
		//买方付息结束
		if (qInfo.getMaxPayerRate() > 0){
			buf.append(" and c.mPurchaserAmount<=" + DataFormat.formatAmount(qInfo.getMaxPayerRate()));
		}
		//买方付息开始
		if (qInfo.getMinPayerRate() > 0){
			buf.append(" and c.mPurchaserAmount>=" + DataFormat.formatAmount(qInfo.getMinPayerRate()));
		}
		//管理人
		if (qInfo.getInputUserID() > 0)
			buf.append(" and c.nInputUserID=" + qInfo.getInputUserID());
		
		//办事处
		if(qInfo.getOfficeID() > 0)
			buf.append(" and c.nOfficeID=" + qInfo.getOfficeID());
		
        //币种
		if(qInfo.getCurrencyID() > 0)
			buf.append(" and c.nCurrencyID=" + qInfo.getCurrencyID());
		
		sql[2] = sql[2] + buf.toString();

		return sql;
	}
	/**
	 * 贴现票据明细表查询 贴现票据计息明细表查询
	 * @param lContractID
	 */
	public String queryDiscountBillByContractID(long lContractID){
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from Loan_DiscountContractBill ");
		sql.append(" where nStatusID = "+Constant.RecordStatus.VALID+" and nContractID = "+lContractID);
		
		return sql.toString();
	}
	/**
	 * 贴现票据计息明细表查询
	 * @param lCredenceID
	 */
	public String queryDiscountBillByCredenceID(long lCredenceID){
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from Loan_DiscountContractBill ");
		sql.append(" where nStatusID = "+Constant.RecordStatus.VALID+" and nDiscountCredenceID = "+lCredenceID);
		
		return sql.toString();
	}
	/**
	 * 贴现票据明细表查询 贴现票据计息明细表查询
	 * @param lDiscountID
	 */
	public String queryDiscountBillByDiscountID(long lDiscountID){
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from Loan_DiscountFormBill ");
		sql.append(" where nStatusID = "+Constant.RecordStatus.VALID+" and nLoanID = "+lDiscountID);
		
		return sql.toString();
	}
	/**
	 * 贴现凭证信息查询
	 * @param QueryDiscountInfo discountVoucherInfo
	 */
	public String queryDiscountVoucherInfo(QueryDiscountInfo discountVoucherInfo){
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select l.ID,l.sCode as code,c.sContractCode as DiscountContractCode,l.sApplyClient as applyClientName,l.mAmount as BillAmount, ");
		sql.append(" 	c.mDiscountRate as DiscountRate,l.mInterest as BillInterest,l.PURCHASERINTEREST as PurchaserInterest, ");
		sql.append(" 	l.dtInputDate as InputDate,c.dtDiscountDate as DiscountDate,l.nStatusID as statusID,u.sName as InputUserName ");
		sql.append("  from Loan_DiscountCredence l,loan_contractForm c,userInfo u ");
		sql.append(" where c.ID(+) = l.nContractID and u.id(+) = l.nInputUserId and c.ntypeid != 6 ");
		sql.append(" and l.nofficeid = "+discountVoucherInfo.getOfficeID()+" and l.ncurrencyid = "+discountVoucherInfo.getCurrencyID());
		
		//查询凭证时的合同ID
		if (discountVoucherInfo.getContractID() > 0  ){
			sql.append(" and l.NCONTRACTID = "+discountVoucherInfo.getContractID());
		}
		//合同编号开始
		if (discountVoucherInfo.getMinContractCode() != null && discountVoucherInfo.getMinContractCode().length() > 0){
			sql.append(" and c.sContractCode >= '"+discountVoucherInfo.getMinContractCode()+"'");
		}
		//合同编号结束
		if (discountVoucherInfo.getMaxContractCode() != null && discountVoucherInfo.getMaxContractCode().length() > 0){
			sql.append(" and c.sContractCode <= '"+discountVoucherInfo.getMaxContractCode()+"'");
		}
		//贴现申请单位开始
		if (discountVoucherInfo.getMinborrowClientID() > 0  ){
			sql.append(" and c.nborrowclientid >= "+discountVoucherInfo.getMinborrowClientID());
		}
		//贴现申请单位结束
		if (discountVoucherInfo.getMaxborrowClientID() > 0  ){
			sql.append(" and c.nborrowclientid <= "+discountVoucherInfo.getMaxborrowClientID());
		}
		//贴现日期开始
		if (discountVoucherInfo.getMinDiscountDate() != null){
			sql.append(" and TRUNC(c.dtDiscountDate) >= To_Date('"+DataFormat.getDateString(discountVoucherInfo.getMinDiscountDate())+"','yyyy-mm-dd') ");
		}
		//贴现日期结束
		if (discountVoucherInfo.getMaxDiscountDate() != null){
			sql.append(" and TRUNC(c.dtDiscountDate) <= To_Date('"+DataFormat.getDateString(discountVoucherInfo.getMaxDiscountDate())+"','yyyy-mm-dd') ");
		}
		//录入日期开始
		if (discountVoucherInfo.getMinDisccountInputDate() != null){
			sql.append(" and TRUNC(l.DTINPUTDATE) >= To_Date('"+DataFormat.getDateString(discountVoucherInfo.getMinDisccountInputDate())+"','yyyy-mm-dd') ");
		}
		//录入日期结束
		if (discountVoucherInfo.getMaxDisccountInputDate() != null){
			sql.append(" and TRUNC(l.DTINPUTDATE) <= To_Date('"+DataFormat.getDateString(discountVoucherInfo.getMaxDisccountInputDate())+"','yyyy-mm-dd') ");
		}
		
		long credenceStatusVal[] = LOANConstant.DiscountCredenceStatus.getAllCode(discountVoucherInfo.getOfficeID(),discountVoucherInfo.getCurrencyID());
		String strStatus = "";
		for (int i = 0; i < credenceStatusVal.length; i++){	
			if(credenceStatusVal[i]==LOANConstant.DiscountCredenceStatus.SUBMIT){
				continue;
			}
			strStatus += credenceStatusVal[i];
			if((credenceStatusVal.length - i) > 1){
				strStatus += ", ";
			}	
		}
		if(discountVoucherInfo.getCredenceStatusIDs()==null || discountVoucherInfo.getCredenceStatusIDs().equals("") || discountVoucherInfo.getCredenceStatusIDs().equals("-1")){
			
			sql.append(" and l.nStatusID in ("+strStatus+")");
		
		}else{
			
			sql.append(" and l.nStatusID in ("+discountVoucherInfo.getCredenceStatusIDs()+")");
		}
		
		return sql.toString();
	}

}
