package com.iss.itreasury.settlement.transcommission.bizlogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.iss.itreasury.settlement.transcommission.dataentity.TransCommissionConditionInfo;
import com.iss.itreasury.settlement.transcommission.dataentity.TransCommissionResultInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

public class TransCommissionBean
{
    private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
    
    StringBuffer sbSelect  = null;
    StringBuffer sbFrom    = null;
    StringBuffer sbWhere   = null;
    StringBuffer sbOrderBy = null;
    
    
    /**
     *@description:拼写pageLoader的sql语句，查询出未结的手续费
     *void        从 sett_TransCurrentDeposit 和 sett_TransSpecailOperation
     *            两个表中查询数据
     *@param queryInfo
     */
    private void getPrepareSQL(TransCommissionConditionInfo info)
    {
        sbSelect = new StringBuffer();
        
        sbSelect.append("  Prepare.id id, \n");
        sbSelect.append("  Prepare.transactionTypeId transactionTypeId, \n");
        sbSelect.append("  Prepare.transactionNo  transNo, \n");
        sbSelect.append("  Prepare.accountId  accountId, \n");
        sbSelect.append("  Prepare.amount  amount, \n");
        sbSelect.append("  Prepare.commissionAmount commissionAmount \n");
        
        sbFrom   = new StringBuffer();
        
        //银行收款,银行收款－上收款项,银行收款－成员单位收款,银行收款－转成员单位收款
        //银行付款,银行付款-财司代付,银行付款-拨子账户
        sbFrom.append(" (  \n");
        sbFrom.append("  select id  Id,  \n");
        sbFrom.append("      ntransactiontypeid transactionTypeId,  \n");
        sbFrom.append("      stransno transactionNo,  \n");
        sbFrom.append("      dtExecute executeDate,  \n");
        sbFrom.append("      decode( ntransactiontypeid,");
        sbFrom.append(                SETTConstant.TransactionType.BANKRECEIVE);
        sbFrom.append("               ,nReceiveAccountId,");
        sbFrom.append(                SETTConstant.TransactionType.BANKRECEIVE_GATHERING);
        sbFrom.append("               ,nReceiveAccountId,");
        sbFrom.append(                SETTConstant.TransactionType.BANKRECEIVE_SUBCLIENT);
        sbFrom.append("               ,nReceiveAccountId,");
        sbFrom.append(                SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT);
        sbFrom.append("               ,nReceiveAccountId,");
        sbFrom.append(                SETTConstant.TransactionType.BANKPAY);
        sbFrom.append("               ,nPayAccountId,");
        sbFrom.append(                SETTConstant.TransactionType.BANKPAY_FINCOMPANYPAY);
        sbFrom.append("               ,nPayAccountId,");
        sbFrom.append(                SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT);
        sbFrom.append("               ,nPayAccountId");
        sbFrom.append("               ,-1) accountId,    \n");
        sbFrom.append("      mAmount amount,    \n");
        sbFrom.append("      mCommissionAmount commissionAmount    \n");
        sbFrom.append("  from sett_transcurrentdeposit   \n");
        sbFrom.append("  where nTransactionTypeId in ( "+SETTConstant.TransactionType.BANKRECEIVE+","+SETTConstant.TransactionType.BANKPAY+","
                +SETTConstant.TransactionType.BANKPAY_FINCOMPANYPAY+","+SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT+","
                +SETTConstant.TransactionType.BANKRECEIVE_GATHERING+","+SETTConstant.TransactionType.BANKRECEIVE_SUBCLIENT+","+SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT+") \n");
        sbFrom.append("\n    and mCommissionAmount > 0.00 ");
        sbFrom.append("\n    and sCommissionTransNo is null ");
        sbFrom.append("\n    and nStatusId = "+SETTConstant.TransactionStatus.CHECK);
        sbFrom.append("\n    and nCurrencyId = "+info.getCurrencyId());
        sbFrom.append("\n    and nOfficeId = "+info.getOfficeId());
        
        sbFrom.append("\n    union  ");
        
        // 特殊业务
        sbFrom.append("  select id  Id,  \n");
        sbFrom.append("      ntransactiontypeid transactionTypeId,  \n");
        sbFrom.append("      stransno transactionNo,  \n");
        sbFrom.append("      dtExecute executeDate,  \n");
        sbFrom.append("      nvl(nPayAccountId,nReceiveAccountId) accountId,");
        sbFrom.append("      nvl(mPayAmount,mReceiveAmount) amount,    \n");
        sbFrom.append("      mCommissionAmount commissionAmount    \n");
        sbFrom.append("  from sett_transspecialoperation   \n");
        sbFrom.append("  where nTransactionTypeId in ( "+SETTConstant.TransactionType.SPECIALOPERATION+") \n");
        sbFrom.append("\n    and mCommissionAmount > 0.00 ");
        sbFrom.append("\n    and sCommissionTransNo is null ");
        sbFrom.append("\n    and nStatusId = "+SETTConstant.TransactionStatus.CHECK);
        sbFrom.append("\n    and nCurrencyId = "+info.getCurrencyId());
        sbFrom.append("\n    and nOfficeId = "+info.getOfficeId());
        sbFrom.append(" ) Prepare ");
        
        sbWhere = new StringBuffer();
        
        sbWhere.append(" 1=1  \n");
        
        if( info.getStartDate() != null )
        {
            sbWhere.append("\n  and to_char( Prepare.executeDate,'yyyy-mm-dd' )  >= " +  "'" + DataFormat.formatDate( info.getStartDate() )+  "'" );
        }
        if( info.getEndDate() != null )
        {
            sbWhere.append("\n  and to_char( Prepare.executeDate,'yyyy-mm-dd' ) <= " +   "'" + DataFormat.formatDate( info.getEndDate() )+  "'" );
        }
        if( info.getAccountIdFrom() > 0 )
        {
            sbWhere.append("\n  and Prepare.accountId >= "  + info.getAccountIdFrom()  );
        }
        if( info.getAccountIdTo() > 0 )
        {
            sbWhere.append("\n  and Prepare.accountId <= "  + info.getAccountIdTo() );
        }
        
        sbWhere.append("\n  and Prepare.accountId IN  "  );
        sbWhere.append("\n  ( "  );
        sbWhere.append("\n  	select distinct id   from sett_account where 1=1 "  );
        if( info.getClientIdFrom() > 0 )
        {
        	sbWhere.append("\n  	and nclientid >= " + info.getClientIdFrom()  );
        }
        if( info.getClientIdTo() > 0 )
        {
        	sbWhere.append("\n  	and nclientid <= " + info.getClientIdTo()  );
        }
        sbWhere.append("\n  ) "  );
        
        
        sbOrderBy = new StringBuffer();
        
        sbOrderBy.append("\n Order By Prepare.accountId  ");
        
        String strDesc = info.getDesc() == 1 ? " desc " : "";
        
        
        log.debug("select " + sbSelect.toString()+"from "+sbFrom.toString()+"where "+sbWhere.toString());
        
        
    }
    
    /**
     *@description:拼写pageLoader的sql语句，查询出已结的手续费
     *void        从 sett_TransCommission , sett_TransCurrentDeposit
     *            和 sett_TransSpecailOperation表中查询数据
     *@param queryInfo
     */
    private void getDoneSQL(TransCommissionConditionInfo info)
    {
        sbSelect = new StringBuffer();
        
        sbSelect.append("  Done.transNo transNo,  \n");
        sbSelect.append("  Done.accountId accountId, \n");
        sbSelect.append("  sum(Done.amount) amount, \n");
        sbSelect.append("  sum(Done.commissionAmount) commissionAmount, \n");
        sbSelect.append("  Done.executeDate executeDate, \n");
        sbSelect.append("  max(transactionDate) maxDate, \n");
        sbSelect.append("  min(transactionDate) minDate, \n");
        sbSelect.append("  rebate, \n");
        sbSelect.append("  sum(rebatecommissionAmount) rebatecommissionAmount, \n");
        sbSelect.append("  count(*) countNum \n");
        
        sbFrom   = new StringBuffer();
        
        // 银行收款,银行付款,银行付款-财司代付,银行付款-拨子账户
        sbFrom.append(" (  \n");
        sbFrom.append("  select  a.id id ,  \n");
        sbFrom.append("      a.sCommissionTransNo transNo,  \n");
        
        sbFrom.append("      decode( a.ntransactiontypeid,");
        sbFrom.append(                SETTConstant.TransactionType.BANKRECEIVE);
        sbFrom.append("               ,a.nReceiveAccountId,");
        sbFrom.append(                SETTConstant.TransactionType.BANKRECEIVE_GATHERING);
        sbFrom.append("               ,a.nReceiveAccountId,");
        sbFrom.append(                SETTConstant.TransactionType.BANKRECEIVE_SUBCLIENT);
        sbFrom.append("               ,a.nReceiveAccountId,");
        sbFrom.append(                SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT);
        sbFrom.append("               ,a.nReceiveAccountId,");
        sbFrom.append(                SETTConstant.TransactionType.BANKPAY);
        sbFrom.append("               ,a.nPayAccountId,");
        sbFrom.append(                SETTConstant.TransactionType.BANKPAY_FINCOMPANYPAY);
        sbFrom.append("               ,a.nPayAccountId,");
        sbFrom.append(                SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT);
        sbFrom.append("               ,a.nPayAccountId");
        sbFrom.append("               ,-1) accountId,    \n");
        
        sbFrom.append("      a.mamount amount, \n");
        sbFrom.append("      a.mCommissionAmount commissionAmount,  \n");
        sbFrom.append("      a.ntransactiontypeid TransactionTypeid,   \n");
        sbFrom.append("      a.dtexecute executeDate,   \n");
        sbFrom.append("      a.dtexecute transactionDate,   \n");
        sbFrom.append("      b.rebate rebate,   \n");
        sbFrom.append("      b.rebatecommissionAmount rebatecommissionAmount   \n");
        sbFrom.append("  FROM  sett_transcurrentdeposit a, Sett_TransCommission b   \n");
        sbFrom.append("  where a.nTransactionTypeId in ( "+SETTConstant.TransactionType.BANKRECEIVE+","+SETTConstant.TransactionType.BANKPAY+","
                +SETTConstant.TransactionType.BANKPAY_FINCOMPANYPAY+","+SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT+","
                +SETTConstant.TransactionType.BANKRECEIVE_GATHERING+","+SETTConstant.TransactionType.BANKRECEIVE_SUBCLIENT+","+SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT+") \n");
        sbFrom.append("\n    and a.nstatusId = "+SETTConstant.TransactionStatus.CHECK);
        sbFrom.append("\n    and a.sCommissionTransNo is not null ");
        sbFrom.append("\n    and a.ncurrencyId = "+info.getCurrencyId());
        sbFrom.append("\n    and a.nofficeId = "+info.getOfficeId());
        sbFrom.append("\n    and a.scommissiontransno = b.transno ");
        sbFrom.append("\n    and a.mcommissionamount = b.commissionamount ");
        
        sbFrom.append("\n    union  ");
        
        // 特殊业务
        sbFrom.append("  select  c.id id ,  \n");
        sbFrom.append("      c.sCommissionTransNo transNo,  \n");
        sbFrom.append("      nvl(c.nPayAccountId,c.nReceiveAccountId) accountId,");
        sbFrom.append("      nvl(c.mPayAmount,c.mReceiveAmount) amount, \n");
        sbFrom.append("      c.mCommissionAmount commissionAmount,  \n");
        sbFrom.append("      c.ntransactiontypeid TransactionTypeid,   \n");
        sbFrom.append("      c.dtexecute executeDate,   \n");
        sbFrom.append("      c.dtexecute transactionDate,   \n");
        sbFrom.append("      d.rebate rebate,   \n");
        sbFrom.append("      d.rebatecommissionAmount rebatecommissionAmount   \n");
        sbFrom.append("  FROM   sett_transspecialoperation c, Sett_TransCommission d  \n");
        sbFrom.append("  where c.nTransactionTypeId in ( "+SETTConstant.TransactionType.SPECIALOPERATION+") \n");
        sbFrom.append("\n    and c.nstatusId = "+SETTConstant.TransactionStatus.CHECK);
        sbFrom.append("\n    and c.sCommissionTransNo is not null ");
        sbFrom.append("\n    and c.ncurrencyId = "+info.getCurrencyId());
        sbFrom.append("\n    and c.nofficeId = "+info.getOfficeId());
        sbFrom.append("\n    and c.scommissiontransno = d.transno ");
        sbFrom.append("\n    and c.mcommissionamount = d.commissionamount ");
        
        sbFrom.append(" ) Done ");
        
        sbWhere = new StringBuffer();
        
        sbWhere.append(" 1=1  \n");
        
        if( info.getStartDate() != null )
        {
            sbWhere.append("\n  and to_char( Done.executeDate,'yyyy-mm-dd' )  >= " +  "'" + DataFormat.formatDate( info.getStartDate() )+  "'" );
        }
        if( info.getEndDate() != null )
        {
            sbWhere.append("\n  and to_char( Done.executeDate,'yyyy-mm-dd' ) <= " +   "'" + DataFormat.formatDate( info.getEndDate() )+  "'" );
        }
        if( info.getAccountIdFrom() > 0 )
        {
            sbWhere.append("\n  and Done.accountId >= " + info.getAccountIdFrom());
        }
        if( info.getAccountIdTo() > 0 )
        {
            sbWhere.append("\n  and Done.accountId <= " + info.getAccountIdTo());
        }
        
        
        sbWhere.append("\n  and Done.accountId IN  "  );
        sbWhere.append("\n  ( "  );
        sbWhere.append("\n  	select distinct id   from sett_account where 1=1 "  );
        if( info.getClientIdFrom() > 0 )
        {
        	sbWhere.append("\n  	and nclientid >= " + info.getClientIdFrom()  );
        }
        if( info.getClientIdTo() > 0 )
        {
        	sbWhere.append("\n  	and nclientid <= " + info.getClientIdTo()  );
        }
        sbWhere.append("\n  ) "  );
        
        
        //Group By
        sbWhere.append("\n  Group By Done.transNo, Done.accountId ,Done.executeDate, Done.rebate ");
        
        sbOrderBy = new StringBuffer();
        
        sbOrderBy.append("\n Order By Done.transNo  ");
        
        String strDesc = info.getDesc() == 1 ? " desc " : "";
        
        
        log.debug("select " + sbSelect.toString()+"from "+sbFrom.toString()+"where "+sbWhere.toString());
        
        
    }
    /**
     *@description：查找未结手续费
     *从银行收/付款，特殊业务处理交易中
     *查找手续费大于零，并且已经已复核的交易
     *PageLoader
     *@param conditionInfo
     *@return
     *@throws Exception
     */
    public PageLoader findPrepareByCondition(TransCommissionConditionInfo conditionInfo) throws Exception
    {

        getPrepareSQL(conditionInfo);

        PageLoader pageLoader = (PageLoader)
        com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

        pageLoader.initPageLoader(
                                new AppContext(),
                                sbFrom.toString(),
                                sbSelect.toString(),
                                sbWhere.toString(),
                                (int)Constant.PageControl.CODE_PAGELINECOUNT,
                                "com.iss.itreasury.settlement.transcommission.dataentity.TransCommissionResultInfo",
                                null);
         pageLoader.setOrderBy(" " + sbOrderBy.toString() + " ");
         return pageLoader;
    }
    
    /**
     *@description:查找已结手续费
     *PageLoader
     *@param conditionInfo
     *@return
     *@throws Exception
     */
    public PageLoader findDoneByCondition(TransCommissionConditionInfo conditionInfo) throws Exception
    {

        getDoneSQL(conditionInfo);

        PageLoader pageLoader = (PageLoader)
        com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

        pageLoader.initPageLoader(
                                new AppContext(),
                                sbFrom.toString(),
                                sbSelect.toString(),
                                sbWhere.toString(),
                                (int)Constant.PageControl.CODE_PAGELINECOUNT,
                                "com.iss.itreasury.settlement.transcommission.dataentity.TransCommissionResultInfo",
                                null);
         pageLoader.setOrderBy(" " + sbOrderBy.toString() + " ");
         return pageLoader;
    }
    
    
    /**
     *@description: 根据交易号查找对应的手续费收取交易
     *@param transNo
     *@return TransCommissionResultInfo
     *@throws Exception
     */
    public TransCommissionResultInfo findByTransNo(String transNo) throws Exception
    {
        TransCommissionResultInfo resultInfo = null;
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sbSQL = null;
        
        try
        {
            conn = Database.getConnection();
            sbSQL = new StringBuffer();
            
            sbSQL.append("  SELECT  \n");
            
            sbSQL.append("  Done.transNo transNo,  \n");
            sbSQL.append("  Done.accountId accountId, \n");
            sbSQL.append("  sum(Done.amount) amount, \n");
            sbSQL.append("  sum(Done.commissionAmount) commissionAmount, \n");
            sbSQL.append("  Done.executeDate executeDate, \n");
            sbSQL.append("  max(Done.transactionDate) maxDate, \n");
            sbSQL.append("  min(Done.transactionDate) minDate, \n");
            sbSQL.append("  rebate, \n");
            sbSQL.append("  sum(Done.rebatecommissionAmount) rebatecommissionAmount, \n");
            sbSQL.append("  count(*) countNum \n");
            
            sbSQL.append("  FROM  \n");
            
            //银行收款,银行付款,银行付款-财司代付,银行付款-拨子账户
            sbSQL.append(" (  \n");
            sbSQL.append("  select    \n");
            sbSQL.append("      a.sCommissionTransNo transNo,  \n");
            sbSQL.append("      decode( a.ntransactiontypeid,");
            sbSQL.append(                SETTConstant.TransactionType.BANKRECEIVE);
            sbSQL.append("               ,a.nReceiveAccountId,");
            sbSQL.append(                SETTConstant.TransactionType.BANKRECEIVE_GATHERING);
            sbSQL.append("               ,a.nReceiveAccountId,");
            sbSQL.append(                SETTConstant.TransactionType.BANKRECEIVE_SUBCLIENT);
            sbSQL.append("               ,a.nReceiveAccountId,");
            sbSQL.append(                SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT);
            sbSQL.append("               ,a.nReceiveAccountId,");
            sbSQL.append(                SETTConstant.TransactionType.BANKPAY);
            sbSQL.append("               ,a.nPayAccountId,");
            sbSQL.append(                SETTConstant.TransactionType.BANKPAY_FINCOMPANYPAY);
            sbSQL.append("               ,a.nPayAccountId,");
            sbSQL.append(                SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT);
            sbSQL.append("               ,a.nPayAccountId");
            sbSQL.append("               ,-1) accountId,    \n");
            sbSQL.append("      a.mamount amount, \n");
            sbSQL.append("      a.mCommissionAmount commissionAmount,  \n");
            sbSQL.append("      a.ntransactiontypeid TransactionTypeid,   \n");
            sbSQL.append("      a.dtexecute executeDate,   \n");
            sbSQL.append("      a.dtexecute transactionDate,   \n");
            sbSQL.append("      b.rebate rebate,   \n");
            sbSQL.append("      b.rebatecommissionAmount rebatecommissionAmount   \n");
            sbSQL.append("  FROM  sett_transcurrentdeposit a, Sett_TransCommission b   \n");
            sbSQL.append("  where a.nTransactionTypeId in ( "+SETTConstant.TransactionType.BANKRECEIVE+","+SETTConstant.TransactionType.BANKPAY+","
                    +SETTConstant.TransactionType.BANKPAY_FINCOMPANYPAY+","+SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT+","
                    +SETTConstant.TransactionType.BANKRECEIVE_GATHERING+","+SETTConstant.TransactionType.BANKRECEIVE_SUBCLIENT+","+SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT+") \n");
            sbSQL.append("\n    and a.nstatusId = "+SETTConstant.TransactionStatus.CHECK);
            sbSQL.append("\n    and sCommissionTransNo is not null ");
            sbSQL.append("\n    and a.scommissiontransno = b.transno ");
            sbSQL.append("\n    and a.mcommissionamount = b.commissionamount ");
            
            sbSQL.append("\n    union  ");
            
            // 特殊业务
            sbSQL.append("  select    \n");
            sbSQL.append("      c.sCommissionTransNo  transNo,  \n");
            sbSQL.append("      nvl(c.nPayAccountId,c.nReceiveAccountId) accountId,");
            sbSQL.append("      nvl(c.mPayAmount,c.mReceiveAmount) amount, \n");
            sbSQL.append("      c.mcommissionAmount commissionAmount,  \n");
            sbSQL.append("      c.ntransactiontypeid TransactionTypeid,   \n");
            sbSQL.append("      c.dtexecute executeDate,   \n");
            sbSQL.append("      c.dtexecute transactionDate,   \n");
            sbSQL.append("      d.rebate rebate,   \n");
            sbSQL.append("      d.rebatecommissionAmount rebatecommissionAmount   \n");
            sbSQL.append("  FROM    sett_transspecialoperation c, Sett_TransCommission d  \n");
            sbSQL.append("  where c.nTransactionTypeId in ( "+SETTConstant.TransactionType.SPECIALOPERATION+") \n");
            sbSQL.append("\n    and c.nstatusId = "+SETTConstant.TransactionStatus.CHECK);
            sbSQL.append("\n    and c.sCommissionTransNo is not null ");
            sbSQL.append("\n    and c.scommissiontransno = d.transno ");
            sbSQL.append("\n    and c.mcommissionamount = d.commissionamount ");
            
            sbSQL.append(" ) Done ");
            
            sbSQL.append("\n WHERE  Done.transNo = "+"'"+transNo+"'");
            
            sbSQL.append("\n  Group By Done.transNo, Done.accountId, Done.executeDate, Done.rebate ");
            
            log.debug("  findByTransNo   \n"+sbSQL.toString());
            
            ps = conn.prepareStatement(sbSQL.toString());
            
            rs = ps.executeQuery();
            
            if( rs != null && rs.next() )
            {
                resultInfo = new TransCommissionResultInfo();
                //resultInfo.setId(rs.getLong("id"));
                resultInfo.setTransNo( rs.getString("transNo") );
                resultInfo.setAccountId( rs.getLong("accountId") );
                resultInfo.setAmount( rs.getDouble("amount") );
                resultInfo.setCommissionAmount( rs.getDouble("commissionAmount") );
                resultInfo.setExecuteDate( rs.getTimestamp("executeDate") );
                resultInfo.setMaxDate( rs.getTimestamp("maxDate") );
                resultInfo.setMinDate( rs.getTimestamp("minDate") );
                resultInfo.setCountNum(rs.getLong("countNum"));
                resultInfo.setRebate( rs.getDouble("rebate") );
                resultInfo.setRebatecommissionAmount( rs.getDouble("rebatecommissionAmount") );
            }
        }
        finally
        {
            if( ps != null )
            {
                ps.close();
                ps = null;
            }
            if( conn != null )
            {
                conn.close();
                conn = null;
            }
        }
        
        return resultInfo;
    }
    
    /**
     * Boxu add 2007-7-16 用于电子回单柜查询手续费收取交易记录,结算视图没有返回该业务的ID
     *@description: 根据交易编号查找对应的手续费收取交易
     *@param transNo
     *@return TransCommissionResultInfo
     *@throws Exception
     */
    public TransCommissionResultInfo findByNo(String transNo) throws Exception
    {
        TransCommissionResultInfo resultInfo = null;
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sbSQL = null;
        
        try
        {
            conn = Database.getConnection();
            sbSQL = new StringBuffer();
            
            sbSQL.append(" SELECT ");
            
            sbSQL.append(" Done.transNo transNo, Done.accountId accountId, sum(Done.amount) amount, sum(Done.commissionAmount) commissionAmount, Done.executeDate executeDate ");
            sbSQL.append(" , max(Done.transactionDate) maxDate, min(Done.transactionDate) minDate, count(*) countNum ");
            sbSQL.append(" , Comm.officeid officeId, Comm.currencyid currencyId, Comm.transactiontypeid typeId, Comm.inputuserid inputUserId, Comm.statusid statusId ");

            sbSQL.append(" FROM ");
            
            //银行收款,银行付款,银行付款-财司代付,银行付款-拨子账户
            sbSQL.append(" ( ");
            sbSQL.append(" select id id, sCommissionTransNo transNo, ");
            
            sbSQL.append(" decode( ntransactiontypeid, ");
            sbSQL.append( SETTConstant.TransactionType.BANKRECEIVE );
            sbSQL.append(" , nReceiveAccountId, ");
            sbSQL.append( SETTConstant.TransactionType.BANKRECEIVE_GATHERING );
            sbSQL.append(" , nReceiveAccountId, ");
            sbSQL.append( SETTConstant.TransactionType.BANKRECEIVE_SUBCLIENT );
            sbSQL.append(" , nReceiveAccountId, ");
            sbSQL.append( SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT );
            sbSQL.append(" , nReceiveAccountId, ");
            sbSQL.append( SETTConstant.TransactionType.BANKPAY );
            sbSQL.append(" , nPayAccountId, ");
            sbSQL.append( SETTConstant.TransactionType.BANKPAY_FINCOMPANYPAY );
            sbSQL.append(" , nPayAccountId, ");
            sbSQL.append( SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT );
            sbSQL.append(" , nPayAccountId ");
            sbSQL.append(" , -1 ) accountId, ");
            
            sbSQL.append(" mamount amount, mCommissionAmount commissionAmount, ntransactiontypeid TransactionTypeid, dtexecute executeDate, dtexecute transactionDate ");
            
            sbSQL.append(" FROM sett_transcurrentdeposit ");
            
            sbSQL.append(" where nTransactionTypeId in ( "
            		+SETTConstant.TransactionType.BANKRECEIVE+","
            		+SETTConstant.TransactionType.BANKPAY+","
                    +SETTConstant.TransactionType.BANKPAY_FINCOMPANYPAY+","
                    +SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT+","
                    +SETTConstant.TransactionType.BANKRECEIVE_GATHERING+","
                    +SETTConstant.TransactionType.BANKRECEIVE_SUBCLIENT+","
                    +SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT+" ) ");
            
            sbSQL.append(" and nstatusId = "+SETTConstant.TransactionStatus.CHECK );
            sbSQL.append(" and sCommissionTransNo is not null ");
            
            sbSQL.append(" union ");
            
            // 特殊业务
            sbSQL.append(" select id id, sCommissionTransNo transNo, nvl(nPayAccountId,nReceiveAccountId) accountId ");
            sbSQL.append(" , nvl(mPayAmount,mReceiveAmount) amount, mcommissionAmount commissionAmount, ntransactiontypeid TransactionTypeid ");
            sbSQL.append(" , dtexecute executeDate, dtexecute transactionDate ");
            
            sbSQL.append(" FROM sett_transspecialoperation ");
            
            sbSQL.append(" where nTransactionTypeId in ( "+SETTConstant.TransactionType.SPECIALOPERATION+") ");
            sbSQL.append(" and nstatusId = "+SETTConstant.TransactionStatus.CHECK );
            sbSQL.append(" and sCommissionTransNo is not null ");
            sbSQL.append(" ) Done, sett_transcommission Comm ");
            
            sbSQL.append(" WHERE Done.transNo = Comm.transno and Done.transNo = '"+transNo+"' ");
            
            sbSQL.append(" Group By Done.transNo, Done.accountId, Done.executeDate ");
            sbSQL.append(" , Comm.officeid, Comm.currencyid, Comm.transactiontypeid, Comm.inputuserid, Comm.statusid ");
            
            System.out.println("SQL===="+sbSQL.toString());
            
            ps = conn.prepareStatement(sbSQL.toString());
            
            rs = ps.executeQuery();
            
            if( rs != null && rs.next() )
            {
                resultInfo = new TransCommissionResultInfo();
                
                resultInfo.setOfficeId(rs.getLong("officeId"));
                resultInfo.setCurrencyId(rs.getLong("currencyId"));
                resultInfo.setTransactionTypeId(rs.getLong("typeId"));
                resultInfo.setInputUserId(rs.getLong("inputUserId"));
                resultInfo.setStatusId(rs.getLong("statusId"));
                resultInfo.setTransNo( rs.getString("transNo") );
                resultInfo.setAccountId( rs.getLong("accountId") );
                resultInfo.setAmount( rs.getDouble("amount") );
                resultInfo.setCommissionAmount( rs.getDouble("commissionAmount") );
                resultInfo.setExecuteDate( rs.getTimestamp("executeDate") );
                resultInfo.setMaxDate( rs.getTimestamp("maxDate") );
                resultInfo.setMinDate( rs.getTimestamp("minDate") );
                resultInfo.setCountNum(rs.getLong("countNum"));
            }
        }
        finally
        {
            if( ps != null )
            {
                ps.close();
                ps = null;
            }
            if( conn != null )
            {
                conn.close();
                conn = null;
            }
        }
        
        return resultInfo;
    }
    
    public List findWithTransNo(String transNo) throws Exception
    {
        List list = null;
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sbSQL = null;
        
        try
        {
            conn = Database.getConnection();
            sbSQL = new StringBuffer();
            
            sbSQL.append("SELECT * FROM (");
            
            //银行收款,银行付款,银行付款-财司代付,银行付款-拨子账户
            
            sbSQL.append("  select  a.id id ,  \n");
            sbSQL.append("      a.sCommissionTransNo transNo,  \n");
            sbSQL.append("      decode( a.ntransactiontypeid,");
            sbSQL.append(                SETTConstant.TransactionType.BANKRECEIVE);
            sbSQL.append("               ,a.nReceiveAccountId,");
            sbSQL.append(                SETTConstant.TransactionType.BANKRECEIVE_GATHERING);
            sbSQL.append("               ,a.nReceiveAccountId,");
            sbSQL.append(                SETTConstant.TransactionType.BANKRECEIVE_SUBCLIENT);
            sbSQL.append("               ,a.nReceiveAccountId,");
            sbSQL.append(                SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT);
            sbSQL.append("               ,a.nReceiveAccountId,");
            sbSQL.append(                SETTConstant.TransactionType.BANKPAY);
            sbSQL.append("               ,a.nPayAccountId,");
            sbSQL.append(                SETTConstant.TransactionType.BANKPAY_FINCOMPANYPAY);
            sbSQL.append("               ,a.nPayAccountId,");
            sbSQL.append(                SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT);
            sbSQL.append("               ,a.nPayAccountId");
            sbSQL.append("               ,-1) accountId,    \n");
            sbSQL.append("      a.mamount amount, \n");
            sbSQL.append("      a.mCommissionAmount commissionAmount,  \n");
            sbSQL.append("      a.ntransactiontypeid TransactionTypeid,   \n");
            sbSQL.append("      a.dtexecute executeDate,   \n");
            sbSQL.append("      a.dtexecute transactionDate,   \n");
            sbSQL.append("      b.rebate rebate,   \n");
            sbSQL.append("      b.rebatecommissionAmount rebatecommissionAmount   \n");
            sbSQL.append("  FROM  sett_transcurrentdeposit a, Sett_TransCommission b  \n");
            sbSQL.append("  where a.nTransactionTypeId in ( "+SETTConstant.TransactionType.BANKRECEIVE+","+SETTConstant.TransactionType.BANKPAY+","
                    +SETTConstant.TransactionType.BANKPAY_FINCOMPANYPAY+","+SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT+","
                    +SETTConstant.TransactionType.BANKRECEIVE_GATHERING+","+SETTConstant.TransactionType.BANKRECEIVE_SUBCLIENT+","+SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT+") \n");
            sbSQL.append("\n    and a.nstatusId = "+SETTConstant.TransactionStatus.CHECK);
            sbSQL.append("\n    and a.sCommissionTransNo is not null ");
            sbSQL.append("\n    and a.scommissiontransno = b.transno ");
            sbSQL.append("\n    and a.mcommissionamount = b.commissionamount ");
            
            sbSQL.append("\n    union  ");
            
            // 特殊业务
            sbSQL.append("  select  c.id id ,  \n");
            sbSQL.append("      c.sCommissionTransNo  transNo,  \n");
            sbSQL.append("      nvl(c.nPayAccountId,c.nReceiveAccountId) accountId,");
            sbSQL.append("      nvl(c.mPayAmount,c.mReceiveAmount) amount, \n");
            sbSQL.append("      c.mcommissionAmount commissionAmount,  \n");
            sbSQL.append("      c.ntransactiontypeid TransactionTypeid,   \n");
            sbSQL.append("      c.dtexecute executeDate,   \n");
            sbSQL.append("      c.dtexecute transactionDate,   \n");
            sbSQL.append("      d.rebate rebate,   \n");
            sbSQL.append("      d.rebatecommissionAmount rebatecommissionAmount   \n");
            sbSQL.append("  FROM    sett_transspecialoperation c, Sett_TransCommission d   \n");
            sbSQL.append("  where c.nTransactionTypeId in ( "+SETTConstant.TransactionType.SPECIALOPERATION+") \n");
            sbSQL.append("\n    and c.nstatusId = "+SETTConstant.TransactionStatus.CHECK);
            sbSQL.append("\n    and c.scommissiontransno = d.transno ");
            sbSQL.append("\n    and c.mcommissionamount = d.commissionamount ");
            
            sbSQL.append("\n    and c.sCommissionTransNo is not null  ) a where a.transno="+transNo);
            sbSQL.append(" order by a.transno ");
            
            log.debug("  findByTransNo   \n"+sbSQL.toString());
            
            ps = conn.prepareStatement(sbSQL.toString());
            
            rs = ps.executeQuery();
            
            list = new ArrayList();
            
            while( rs != null && rs.next() )
            {
            	TransCommissionResultInfo resultInfo = new TransCommissionResultInfo();
                
                resultInfo.setTransNo( rs.getString("transNo") );
                resultInfo.setAccountId( rs.getLong("accountId") );
                resultInfo.setAmount( rs.getDouble("amount") );
                resultInfo.setCommissionAmount( rs.getDouble("commissionAmount") );
                resultInfo.setTransactionTypeId(rs.getLong("transactionTypeid"));
                resultInfo.setRebate( rs.getDouble("rebate") );
                resultInfo.setRebatecommissionAmount( rs.getDouble("rebatecommissionAmount") );
                
                list.add(resultInfo);
                
            }
        }
        finally
        {
            if( ps != null )
            {
                ps.close();
                ps = null;
            }
            if( conn != null )
            {
                conn.close();
                conn = null;
            }
        }
        
        return list;
    }
}