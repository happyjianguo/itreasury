package com.iss.itreasury.settlement.query.queryobj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;



import com.iss.itreasury.settlement.bankinterface.dataentity.QueryAccountBalanceInfo;
import com.iss.itreasury.settlement.bankinterface.dataentity.QueryAccountTransSumInfo;
import com.iss.itreasury.settlement.bankinterface.dataentity.QueryBankAccountTransConditionInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryBankAccountCheckWhereInfo;
import com.iss.itreasury.settlement.query.resultinfo.BankAccountCheckInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IDate;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;

public class QBankAccountCheck extends BaseQueryObject {

	public QBankAccountCheck() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
    
	/*
	//暂时不用，航天科工不能查询当天  add by yanliu 05.8.3
    public Collection queryAccountBankCheck(QueryBankAccountCheckWhereInfo qInfo) throws Exception
    {
    	Vector v= new Vector();
    	
    	return (v.size() > 0 ? v : null); 
    }
    
    //暂时不用，航天科工不能查询当天  add by yanliu 05.8.3
    public Collection getCurrentCheckInfo(QueryBankAccountCheckWhereInfo qInfo) throws Exception 
    {
        Vector vReturn = new Vector();
    	long accountId = -1;
        //根据银行类型查询所有的二级银行账户
        FilialeAccountInfo[] filialeAccountInfo = null;
        Connection con;
        con = Database.getConnection();
        QueryBalanceResultInfo[] bankAccountBalances = null;
        AccountBalanceInfo[] accountBalanceInfo = null;
        try
        {
	        Sett_FilialeAccountSetDAO filialeAccountSetDao = new Sett_FilialeAccountSetDAO(
	                con);
	        filialeAccountInfo = filialeAccountSetDao.findAll(qInfo.getBankType());
	        if (filialeAccountInfo == null || filialeAccountInfo.length <= 0)
	        { 
	        	return null; 
	        }
	        
	        //根据二级账户去银行同步当日余额信息
	        BankServiceDelegation bankService = BankServiceDelegation.getInstance(qInfo.getBankType());
	        AccountInfo[] accountInfo = new AccountInfo[filialeAccountInfo.length];
	        for (int i = 0; i < filialeAccountInfo.length; i++) 
	        {
	            accountInfo[i] = new AccountInfo();
	            accountInfo[i].setAccountNo(filialeAccountInfo[i]
	                    .getBankAccountNo());
	            accountInfo[i].setAccountName(filialeAccountInfo[i]
	                    .getBankAccountName());
	            accountInfo[i].setBranchName(filialeAccountInfo[i].getBranchName());
	            accountInfo[i].setCurrencyType(Constant.CurrencyType
	                    .convertToCurrencyObjectOfBS(filialeAccountInfo[i]
	                            .getCurrencyID()));
	        }
        
	        bankAccountBalances = bankService.queryMultiAccountCurrentBalance(accountInfo);
	        if (bankAccountBalances == null || bankAccountBalances.length <= 0) 
	        { 
	        	return null; 
	        }
	        
	        //查询二级账户对应活期账户的当日余额      
	        QAccountBalanceInfo qAccountBalanceInfo = new QAccountBalanceInfo();
	        accountBalanceInfo = qAccountBalanceInfo.getAccountBalance(qInfo.getBankType(),con);
        }
        finally
        {
        	cleanup(con);        
        }
        
        //组成对比清单
        BankAccountCheckInfo[] BankAccountCheckInfo = new BankAccountCheckInfo[bankAccountBalances.length];
        for (int i = 0; i < filialeAccountInfo.length; i++) 
        {
        	BankAccountCheckInfo[i] = new BankAccountCheckInfo();
        	BankAccountCheckInfo[i].setBankAccountNo(filialeAccountInfo[i]
                    .getBankAccountNo());
        	BankAccountCheckInfo[i].setSettClientName(filialeAccountInfo[i]
                    .getBankAccountName());
            for (int j = 0; j < bankAccountBalances.length; j++) 
            {
                if (bankAccountBalances[j].getAccountInfo().getAccountNo()
                        .equals(filialeAccountInfo[i].getBankAccountNo())) 
                {
                	BankAccountCheckInfo[i]
                            .setBankAccountBalance(bankAccountBalances[j]
                                    .getAccountInfo().getBalance());
                	BankAccountCheckInfo[i]
                            .setBankIsOperateFailed(bankAccountBalances[j]
                                    .isOperateFailed());
                	BankAccountCheckInfo[i]
                            .setBankErrorMessage(bankAccountBalances[j]
                                    .getErrorMessage());
                    break;
                }
            }
            if (accountBalanceInfo == null || accountBalanceInfo.length <= 0) 
            {
                continue;
            }
            for (int k = 0; k < accountBalanceInfo.length; k++) 
            {
                if (accountBalanceInfo[k].getAccountId() == filialeAccountInfo[i]
                        .getWithinAccountID()) 
                {
                	BankAccountCheckInfo[i]
                            .setSettAccountBalance(accountBalanceInfo[k]
                                    .getAccountInfo().getBalance());
                	BankAccountCheckInfo[i]
                            .setSettAccountNo(accountBalanceInfo[k]
                                    .getAccountInfo().getAccountNo());
                }
            }
            //组织差额
            if (BankAccountCheckInfo[i].isBankIsOperateFailed() == true) 
            {
            } 
            else 
            {
            	BankAccountCheckInfo[i]
                        .setSettAccountBalance(BankAccountCheckInfo[i]
                                .getSettAccountBalance()
                                - BankAccountCheckInfo[i]
                                        .getBankAccountBalance());
            }
            
            vReturn.add(BankAccountCheckInfo[i]);
        }
    
        return vReturn;
    }
    */
    
    /***************************************************************************
     * 根据银行类型查询指定日期的二级银行账户和对应的财务公司的活期存款户的余额，借贷金额、笔数对比清单
     * 
     * @param bankType
     *            银行类型
     * @param queryDate
     *            对比日期
     * @param isDesc
     *            是否升序，true ：是，false：否
     * @return Collection 账户对比清单集合
     * @throws Exception
     */
    public Collection queryHistoryCheckInfo(QueryBankAccountCheckWhereInfo qInfo)throws Exception 
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        QueryAccountBalanceInfo[] accountBalanceInfos = null;
        BankAccountCheckInfo bankAccountCheckInfo = null;
        Vector v_Return = new Vector();
        try 
        {
            conn = Database.getConnection();
            StringBuffer strQuerySql = new StringBuffer();
            strQuerySql.append("select acct.sbankaccountno as bankAccountNo, acct.sbankaccountname as bankaccountname,settacct.saccountno as settaccountno,settacct.id as settaccountid ,accountbalance.mbalance as accountbalance,tran.debitAmount as debitAmount, tran.creditAmount as creditAmount,bankbalance.mbalance as bankaccountbalance,acct.nwithinaccountid as settaccountid, nvl(accountbalance.mbalance,0) - nvl(tran.debitAmount,0) + nvl(tran.creditAmount,0) - nvl(bankbalance.mbalance,0) as balance\r\n");
            strQuerySql.append(" from \r\n");
            strQuerySql.append(" ( select saccountno, saccountname, sum(debitAmount) as debitAmount,sum(creditAmount) as creditAmount \r\n");
            strQuerySql.append(" from ( select saccountno, saccountname, sum(mamount) as debitAmount, 0 as creditAmount ");
            strQuerySql.append(" from sett_histransinfoofbankaccount where ndirection = "
                            + SETTConstant.DebitOrCredit.DEBIT
                            + " and NTURNINRESULT = "
                            + SETTConstant.AutoTurnInBankTransStatus.CHECKSUCCESS
                            + "\r\n");
            strQuerySql.append(" and to_char(DTTRANSACTION,'yyyy-mm-dd') = '"
                            + DataFormat.formatDate(qInfo.getQueryDate())
                            + "'  and to_char(DTTRANSACTION,'yyyymmdd') <>  nvl(to_char(DTTURNIN,'yyyymmdd'),'19700101') group by saccountno,saccountname \r\n");
            strQuerySql.append(" union all");
            strQuerySql.append(" select saccountno, saccountname, 0 as debitAmount, sum(mamount) as creditAmount \r\n");
            strQuerySql.append(" from sett_histransinfoofbankaccount where ndirection = "
                            + SETTConstant.DebitOrCredit.CREDIT
                            + " and NTURNINRESULT = "
                            + SETTConstant.AutoTurnInBankTransStatus.CHECKSUCCESS
                            + "\r\n");
            strQuerySql.append(" and to_char(DTTRANSACTION,'yyyy-mm-dd') = '"
                            + DataFormat.formatDate(qInfo.getQueryDate())
                            + "' and to_char(DTTRANSACTION,'yyyymmdd') <>  nvl(to_char(DTTURNIN,'yyyymmdd'),'19700101') group by saccountno,saccountname \r\n");
            strQuerySql.append(" ) group by saccountno, saccountname )tran ,sett_bankaccountoffiliale acct \r\n");
            strQuerySql.append(" , sett_account settacct  \r\n");
            strQuerySql.append(",( select b.sbankaccountno bankaccountno,b.mbalance as mbalance from sett_balanceofbankaccount b ,sett_bankaccountoffiliale ba  where   ba.sbankaccountno= b.sbankaccountno and to_char(b.dtcurrent,'yyyy-mm-dd') = '"
                            + DataFormat.formatDate(qInfo.getQueryDate())
                            + "') bankbalance ");
            strQuerySql.append(" ,(select  saccount.id naccountid,daily.mbalance  from sett_dailyaccountbalance daily,sett_account saccount where daily.naccountid = saccount.id and to_char(daily.dtdate,'yyyy-mm-dd') = '"
                            + DataFormat.formatDate(qInfo.getQueryDate())
                            + "') accountbalance ");
            strQuerySql.append(" where acct.sbankaccountno = tran.saccountno(+) and acct.nwithinaccountid=settacct.id(+) and acct.nbnktype = "
                            + qInfo.getBankType()
                            + " and acct.sbankaccountno = bankbalance.bankaccountno (+) ");
            strQuerySql.append(" and settacct.id = accountbalance.naccountid(+) \r\n");
            strQuerySql.append(" order by tran.saccountno ");
            if (qInfo.getDesc() == 1) 
            {
                strQuerySql.append(" desc");
            } else 
            {
                strQuerySql.append("asc");
            }
            log.info(strQuerySql.toString());

            ps = conn.prepareStatement(strQuerySql.toString());
            rs = ps.executeQuery();
            while (rs.next()) 
            {
            	bankAccountCheckInfo = new BankAccountCheckInfo();
            	bankAccountCheckInfo.setBankAccountNo(rs.getString("bankAccountNo"));
            	bankAccountCheckInfo.setSettClientName(rs.getString("bankaccountname"));
                bankAccountCheckInfo.setSettAccountNo(rs.getString("settaccountno"));
                //bankAccountCheckInfo.setSettAccountBalance(rs.getDouble("accountbalance"));
                bankAccountCheckInfo.setBankAccountBalance(rs.getDouble("bankAccountbalance"));
                
                //查询活期账户借贷金额及笔数
                qInfo.setSettAccountID(rs.getLong("settaccountid"));
                BankAccountCheckInfo tempInfo = this.findSettAccountTransInfo(qInfo);
                bankAccountCheckInfo.setSettAccountBalance(tempInfo.getSettAccountBalance());
                bankAccountCheckInfo.setSettCreditAmount(tempInfo.getSettCreditAmount());
                bankAccountCheckInfo.setSettCreditCount(tempInfo.getSettCreditCount());
                bankAccountCheckInfo.setSettDebitAmount(tempInfo.getSettDebitAmount());
                bankAccountCheckInfo.setSettDebitCount(tempInfo.getSettDebitCount());                
 
                v_Return.add(bankAccountCheckInfo);
            }
            cleanup(rs);
            cleanup(ps);
            cleanup(conn);
            
            //查询银行账户借贷金额及笔数
            //QBankAccountTransInfo qTransBiz = new QBankAccountTransInfo();
            QueryBankAccountTransConditionInfo whereInfo = new QueryBankAccountTransConditionInfo();
            whereInfo.setStartDate(qInfo.getQueryDate());
            whereInfo.setTurnInResult(new long[]{SETTConstant.AutoTurnInBankTransStatus.CHECKSUCCESS});//复核完成
            whereInfo.setOrder(qInfo.getOrderParam());
            whereInfo.setDesc(qInfo.getDesc());
            QueryAccountTransSumInfo[] c_TransInfo = null;
            c_TransInfo = this.queryMultiFilialeAccountTransInfoSum(whereInfo,-1);
            
            //拼接
            int k = v_Return.size();
            for(int i = 0;i<k;i++)
            {
            	BankAccountCheckInfo tempInfo = new BankAccountCheckInfo();
            	tempInfo = (BankAccountCheckInfo)v_Return.get(i);
            	if(c_TransInfo!=null)
            	{	
            		for(int j = 0;j<c_TransInfo.length;j++)
            		{
            			if(c_TransInfo[j].getBankAccountNo().equals(tempInfo.getBankAccountNo()))
            			{
            				tempInfo.setBankCreditAmount(c_TransInfo[j].getCreditAmountSum());
            				tempInfo.setBankCreditCount(c_TransInfo[j].getCreditCount());
            				tempInfo.setBankDebitAmount(c_TransInfo[j].getDebitAmountSum());
            				tempInfo.setBankDebitCount(c_TransInfo[j].getDebitCount());
            				break;
            			}		
            		}
            	}
            	v_Return.set(i,tempInfo);
            }	
        } 
        catch (Exception e) 
        {
            log.error("查询错误");
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } 
        finally 
        {
            cleanup(rs);
            cleanup(ps);
            cleanup(conn);
        }
        return v_Return;
    }

    public BankAccountCheckInfo findSettAccountTransInfo(QueryBankAccountCheckWhereInfo qInfo) throws Exception
    {
    	BankAccountCheckInfo resultInfo = new BankAccountCheckInfo();
    	
		Vector v = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer m_sbSQL = null;
		m_sbSQL = new StringBuffer();
		try
		{
			conn = getConnection();
			
			m_sbSQL.append(" select sum(mBalance) Balance from sett_DailyAccountBalance where nAccountID=" + qInfo.getSettAccountID() + " \n");
			m_sbSQL.append(" and dtDate = to_date('" + DataFormat.formatDate(qInfo.getQueryDate()) + "','yyyy-mm-dd') \n");
			ps = conn.prepareStatement(m_sbSQL.toString());
			rs = ps.executeQuery();
			//get all the ResultSet elements
			while (rs.next())
			{
				resultInfo.setSettAccountBalance( rs.getDouble("Balance"));
			}
			cleanup(rs);
            cleanup(ps);
			
			// select 
			m_sbSQL = new StringBuffer();
			m_sbSQL.append(" select nvl(sum(trans.mamount),0) as sumAmount,1 as Direction,count(trans.mamount) as sumCount \n");
			m_sbSQL.append(" from sett_TransAccountDetail trans,sett_Account account,sett_SubAccount subaccount, SETT_TRANSCURRENTDEPOSIT transCurrencyDeposit \n");
			m_sbSQL.append(" where subaccount.nAccountID = account.ID and trans.nSubAccountID = subaccount.ID  \n");
			m_sbSQL.append(" and trans.nStatusID=" + SETTConstant.TransactionStatus.CHECK + " and trans.nTransDirection = 1 \n");
			m_sbSQL.append(" and account.nOfficeID=" + qInfo.getOfficeID() + " and account.nCurrencyID=" + qInfo.getCurrencyID() + " \n");
			m_sbSQL.append(" and subaccount.nAccountID = " + qInfo.getSettAccountID() + " \n");
			m_sbSQL.append(" and transCurrencyDeposit.sTransNo(+) = trans.sTransNo \n");
			m_sbSQL.append(" and trans.dtExecute = to_date('"+ DataFormat.formatDate(qInfo.getQueryDate())+"','yyyy-mm-dd')");

			ps = conn.prepareStatement(m_sbSQL.toString());
			
			rs = ps.executeQuery();
			if(rs.next())
			{
				resultInfo.setSettDebitAmount(rs.getDouble("sumAmount"));
				resultInfo.setSettDebitCount(rs.getLong("sumCount"));
			}
			
            cleanup(rs);
            cleanup(ps);
            
            m_sbSQL = new StringBuffer();
			m_sbSQL.append(" select nvl(sum(trans.mamount),0) as sumAmount,2 as Direction,count(trans.mamount) as sumCount \n");
			m_sbSQL.append(" from sett_TransAccountDetail trans,sett_Account account,sett_SubAccount subaccount, SETT_TRANSCURRENTDEPOSIT transCurrencyDeposit \n");
			m_sbSQL.append(" where subaccount.nAccountID = account.ID and trans.nSubAccountID = subaccount.ID  \n");
			m_sbSQL.append(" and trans.nStatusID=" + SETTConstant.TransactionStatus.CHECK + " and trans.nTransDirection = 2 \n");
			m_sbSQL.append(" and account.nOfficeID=" + qInfo.getOfficeID() + " and account.nCurrencyID=" + qInfo.getCurrencyID() + " \n");
			m_sbSQL.append(" and subaccount.nAccountID = " + qInfo.getSettAccountID() + " \n");
			m_sbSQL.append(" and transCurrencyDeposit.sTransNo(+) = trans.sTransNo \n");
			m_sbSQL.append(" and trans.dtExecute = to_date('"+ DataFormat.formatDate(qInfo.getQueryDate())+"','yyyy-mm-dd')");
			
			ps = conn.prepareStatement(m_sbSQL.toString());
			
			rs = ps.executeQuery();
			if(rs.next())
			{
				resultInfo.setSettCreditAmount(rs.getDouble("sumAmount"));
				resultInfo.setSettCreditCount(rs.getLong("sumCount"));
			}
			
            cleanup(rs);
            cleanup(ps);
            cleanup(conn);
		}
		catch(Exception e)
		{
			e.printStackTrace();
            throw new Exception(e.getMessage());
		}
		finally 
        {
            cleanup(rs);
            cleanup(ps);
            cleanup(conn);
        }
    	
    	return resultInfo;
    }
    
    /**
     * 上海电气项目银行交易对账清单使用,只查需要结算入账的交易，对于结算发出的指令产生的交易，不统计在内
     * 
     * @param qInfo
     *            查询条件对象
     * @param type
     *            查询类型（当日入账、他日入账，见当前对象的常量定义，指定为-1时为所有）
     * @return QueryAccountTransSumInfo[]
     * @throws Exception
     */
    public QueryAccountTransSumInfo[] queryMultiFilialeAccountTransInfoSum(
            QueryBankAccountTransConditionInfo qInfo, long type)
            throws Exception {
        if (qInfo.getStartDate() == null
                || IDate.compareDate(qInfo.getStartDate(), qInfo.getEndDate()) != 0) { throw new IException(
                "只能查询一天的数据"); 

        }

        QueryAccountTransSumInfo[] result = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sbSQL = null;
        ArrayList list = new ArrayList(64);
        try {
            //get the connection from Database
            conn = Database.getConnection();
            //establish the query sql string

            /*
            String strTableName = null;
            BankServiceDelegation bsDelegation = BankServiceDelegation
                    .getInstance(SETTConstant.BankType.ICBC);
            Date dateOfBank = bsDelegation.getBankCurrentTime();
            if (IDate.compareDate(qInfo.getStartDate(), dateOfBank) == 0) {
                //查询日期和银行是同一天，查询当日表
                strTableName = "Sett_TransInfoOfBankAccount";
            } else {
                //查询日期和银行不是同一天，查询历史表
            	strTableName = "Sett_HisTransInfoOfBankAccount";
            }
            */
            
            //航天科工不查当日，只查历史
            String strTableName = "Sett_HisTransInfoOfBankAccount";
            
            StringBuffer strTransWhere = new StringBuffer(128);
            String strTemp = DataFormat.formatDate(qInfo.getStartDate());
            //交易日期条件
            strTransWhere
                    .append(" to_date(to_char(dtTransaction,'yyyymmdd'),'yyyymmdd')>= to_date('");
            strTransWhere.append(strTemp);
            strTransWhere.append("','yyyy-mm-dd')");
            strTransWhere
                    .append("\n and to_date(to_char(dtTransaction,'yyyymmdd'),'yyyymmdd')<= to_date('");
            strTransWhere.append(strTemp);
            strTransWhere.append("','yyyy-mm-dd')");

            //同日、他日入账条件
            log.debug("查询当日还是他日入账的：" + type);
            if (type == 1) {
                strTransWhere
                        .append("\n and to_date(to_char(dtTransaction,'yyyymmdd'),'yyyymmdd') = to_date(to_char(DTTURNIN,'yyyymmdd'),'yyyymmdd')");
            } else if (type == 2) {
                strTransWhere
                        .append("\n and to_date(to_char(dtTransaction,'yyyymmdd'),'yyyymmdd') != to_date(to_char(DTTURNIN,'yyyymmdd'),'yyyymmdd')");
            }

            //状态条件
            long[] status = qInfo.getTurnInResult();
            if (status != null) {
                for (int i = 0; i < status.length; i++) {
                    if (status[i] == SETTConstant.AutoTurnInBankTransStatus.CHECKSUCCESS) {
                        //入账结果条件，结算产生的交易不用入账，这种类型不统计在内
                        strTransWhere
                                .append("\n and NTURNINRESULT = "
                                        + SETTConstant.AutoTurnInBankTransStatus.CHECKSUCCESS);
                    }
                }
            }
            sbSQL = new StringBuffer();
            sbSQL.append(" select ");
            sbSQL
                    .append("\n     tran.saccountno BankAccountNo, acct.sfilialename BankAccountName, tran.debitAmount DebitAmountSum, tran.creditAmount CreditAmountSum, tran.debitCount DebitCount, tran.creditCount CreditCount, ");
            sbSQL
                    .append("\n     acct.id BankAccountID, acct.nclientid ClientID, acct.sfilialename ClientName, ");
            sbSQL
                    .append("\n     acct.nwithinaccountid SettAccountID,settacct.saccountno SettAccountNo ");
            sbSQL.append("\n from ");
            sbSQL.append("\n     (");
            sbSQL
                    .append("\n     select saccountno, saccountname, sum(debitAmount) debitAmount,");
            sbSQL
                    .append("\n     sum(creditAmount) creditAmount, sum(debitCount) debitCount, sum(creditCount) creditCount");
            sbSQL.append("\n     from ");
            sbSQL.append("\n         (");
            sbSQL
                    .append("\n         select saccountno, saccountname, sum(mamount) as debitAmount, 0 as creditAmount, count(*) as debitCount, 0 as creditCount ");
            sbSQL.append("\n         from " + strTableName);
            sbSQL.append("\n         where ");
            sbSQL.append("\n             ndirection = "
                    + SETTConstant.DebitOrCredit.DEBIT);
            sbSQL.append("\n     	     and" + strTransWhere.toString());
            sbSQL.append("\n         group by saccountno,saccountname ");
            sbSQL.append("\n         union all ");
            sbSQL
                    .append("\n         select saccountno, saccountname, 0 as debitAmount, sum(mamount) as creditAmount, 0 as debitCount, count(*) as creditCount ");
            sbSQL.append("\n         from " + strTableName);
            sbSQL.append("\n         where ");
            sbSQL.append("\n             ndirection = "
                    + SETTConstant.DebitOrCredit.CREDIT);
            sbSQL.append("\n     	     and" + strTransWhere.toString());
            sbSQL.append("\n         group by saccountno,saccountname ");
            sbSQL.append("\n         ) ");
            sbSQL.append("\n     group by saccountno,saccountname ");
            sbSQL.append("\n     ) tran,");
            sbSQL.append("\n     sett_bankaccountoffiliale acct,");
            sbSQL.append("\n     sett_account settacct ");
            sbSQL.append("\n where ");
            sbSQL
                    .append("\n     tran.saccountno=acct.sbankaccountno and acct.nwithinaccountid=settacct.id(+)");

            //排序条件
            String strDesc = null;
            if (qInfo.getDesc() == 1) {
                strDesc = " desc ";
            } else {
                strDesc = " asc ";
            }
            switch ((int) qInfo.getOrder()) {
            case 1:
                sbSQL.append("\n order by tran.saccountno" + strDesc);
                break;
            }

            log.info(sbSQL.toString());

            ps = conn.prepareStatement(sbSQL.toString());

            rs = ps.executeQuery();
            QueryAccountTransSumInfo temp = null;
            while (rs.next()) {
                temp = new QueryAccountTransSumInfo();

                temp.setBankAccountID(rs.getLong("BankAccountID"));
                temp.setBankAccountNo(rs.getString("BankAccountNo"));
                temp.setBankAccountName(rs.getString("BankAccountName"));
                temp.setDebitAmountSum(rs.getDouble("DebitAmountSum"));
                temp.setCreditAmountSum(rs.getDouble("CreditAmountSum"));
                temp.setDebitCount(rs.getLong("DebitCount"));
                temp.setCreditCount(rs.getLong("CreditCount"));
                temp.setClientID(rs.getLong("ClientID"));
                temp.setClientName(rs.getString("ClientName"));
                temp.setSettAccountID(rs.getLong("SettAccountID"));
                temp.setSettAccountNo(rs.getString("SettAccountNo"));

                list.add(temp);
            }

        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }

        if (list.size() > 0) {
            result = new QueryAccountTransSumInfo[0];

            result = (QueryAccountTransSumInfo[]) list.toArray(result);
        }

        return result;

        /*
         * select tran.saccountno, tran.saccountname, tran.debitAmount,
         * tran.creditAmount, tran.debitCount, tran.creditCount, acct.id as
         * bankaccountid, acct.nclientid as clientid, acct.sfilialename as
         * clientname, acct.nwithinaccountid as
         * settaccountid,settacct.saccountno as settaccountno from ( select
         * saccountno, saccountname, sum(debitAmount) debitAmount,
         * sum(creditAmount) creditAmount, sum(debitCount) debitCount,
         * sum(creditCount) creditCount from ( ) as debitCount, 0 as
         * creditCount from sett_transinfoofbankaccount where ndirection = 1
         * group by saccountno,saccountname union all ) as creditCount from
         * sett_transinfoofbankaccount where ndirection = 2 group by
         * saccountno,saccountname ) group by saccountno,saccountname) tran
         * ,sett_bankaccountoffiliale acct , sett_account settacct where
         * tran.saccountno=acct.sbankaccountno and
         * acct.nwithinaccountid=settacct.id(+)
         */
    }    
    
}
