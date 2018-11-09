package com.iss.itreasury.ebank.system.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Constant.PageControl;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.ebank.obaccountinfo.bizlogic.OBAccountQueryBiz;
import com.iss.itreasury.ebank.obaccountinfo.bizlogic.OBAccountQueryWhere;
import com.iss.itreasury.ebank.obaccountinfo.dao.OBAccountBalanceQueryDao;
import com.iss.itreasury.ebank.obaccountinfo.dao.OBAccountQueryAmountDao;
import com.iss.itreasury.ebank.obaccountinfo.dataentity.OBAccountQueryAmountInfo;
import com.iss.itreasury.ebank.obquery.bizlogic.OBQueryTransAccountBiz;
import com.iss.itreasury.ebank.obquery.dao.OBQueryTransAccountDao;
import com.iss.itreasury.ebank.system.dataentity.AccountInfo;
import com.iss.itreasury.ebank.system.dataentity.AcctTransInfo;
import com.iss.itreasury.ebank.system.dataentity.AcctTransParam;
import com.iss.itreasury.ebank.system.dataentity.QAccountCurBalanceInfo;
import com.iss.itreasury.ebank.system.dataentity.QAccountHisBalanceInfo;
import com.iss.itreasury.ebank.system.dataentity.QueryBillPrintInfo;
import com.iss.itreasury.ebank.system.dataentity.QueryBillPrintParam;
import com.iss.itreasury.ebank.system.util.Arithmetic;
import com.iss.itreasury.ebank.system.util.DateUtil;
import com.iss.itreasury.settlement.bizdelegation.AccountDailyTransGatherDelegation;
import com.iss.itreasury.settlement.query.paraminfo.AccountDailyTransGatherConditionInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransAccountDetailWhereInfo;
import com.iss.itreasury.settlement.query.queryobj.QTransAccountDetail;
import com.iss.itreasury.settlement.query.resultinfo.QueryTransAccountDetailResultInfo;
import com.iss.itreasury.settlement.util.*;

/**
 * @author gqfang
 */
public class BillPrintBiz
{
    /** 日志对象 */
    //private static Logger log       = new Logger(BillPrintBiz.class);

    /** 定义SQL语句变量* */
    private StringBuffer  sbSelect  = null;

    private StringBuffer  sbFrom    = null;  

    private StringBuffer  sbWhere   = null;

    //private StringBuffer  sbOrderBy = null;

    private StringBuffer  sbParam   = null;

    private void getSQL(QueryBillPrintParam param,long lUserID) throws Exception
    {
    	
        /** 查询区间* */
        String transStartDateString = DataFormat.formatDate(param.getTransactionStartDate(), 1);
        String transEndDateString = DataFormat.formatDate(param.getTransactionEndDate(), 1);

        sbSelect  = new StringBuffer();
        sbFrom    = new StringBuffer();
        sbWhere   = new StringBuffer();
        //sbOrderBy = new StringBuffer();
        sbParam   = new StringBuffer();

        /** 查询范围约束条件* */
        if (param.getTransactionStartDate() != null)//查询日期起
        {
            sbParam.append(" WHERE to_char( all_balance.dt_Date,'yyyy-mm-dd' ) >= " + "'" + transStartDateString + "'" + " \n");
        }

        if (param.getTransactionEndDate() != null)//查询日期止
        {
            sbParam.append(" and to_char( all_balance.dt_Date,'yyyy-mm-dd' ) <= " + "'" + transEndDateString + "'" + " \n");
        }

        sbSelect.append("  *  \n");

        /** sbFrom---拼写开始* */
        sbFrom.append("  ( \n");

        sbFrom.append(" SELECT  \n");
        sbFrom.append(" 	acct.n_Id  as accountId, --账户Id   \n");
        sbFrom.append(" 	acct.n_clientid  as clientId, --客户Id   \n");
        sbFrom.append(" 	acct.n_bankid  as bankId, --银行类型   \n");
        sbFrom.append(" 	acct.n_countryid as countryId, -- 国家Id  \n");
        sbFrom.append(" 	acct.n_currencytype as currencyType, -- 币种 \n");
        sbFrom.append(" 	acct.s_accountno as accountNo, -- 账号 \n");
        sbFrom.append(" 	acct.s_accountname as accountName, -- 账户名称 \n");
        sbFrom.append(      "'"+transStartDateString+"' as queryStartDateString, -- 查询开始日期 \n");
        sbFrom.append(      "'"+transEndDateString+"' as queryEndDateString, -- 查询截至日期 \n");
        sbFrom.append(" 	a.minDate as balanceStartDate, -- 余额开始日 \n");
        sbFrom.append(" 	a.maxDate as balanceEndDate,  -- 余额结束日 \n");
        sbFrom.append(" 	b.n_balance as balance  -- 余额结束日的日终余额 \n");

        sbFrom.append(" FROM  \n");

        sbFrom.append(" 	bs_bankaccountinfo acct, \n");

        sbFrom.append(" 	(	select n_accountid, min(dt_date) as minDate ,max(dt_date) as maxDate  \n");
        sbFrom.append(" 		from ( \n");
        sbFrom.append("                select ba1.n_accountid n_accountid, ba1.dt_date dt_date from bs_accthisbalance ba1 \n");
        sbFrom.append("                union \n");
        sbFrom.append("                select ba2.n_accountid n_accountid, ba2.dt_importtime dt_date from bs_acctcurbalance ba2  \n");
        sbFrom.append("              ) all_balance \n");
        sbFrom.append(sbParam.toString());
        sbFrom.append(" 		group by n_accountid   \n");
        sbFrom.append(" 	) a,  \n");

        sbFrom.append(" 	(	select n_accountid, dt_date,n_balance \n");
        sbFrom.append(" 		from (  \n");
        sbFrom.append("                select ba1.n_accountid n_accountid, ba1.dt_date dt_date,ba1.n_balance n_balance from bs_accthisbalance ba1 \n");
        sbFrom.append("                union \n");
        sbFrom.append("                select ba2.n_accountid n_accountid, ba2.dt_importtime dt_date,ba2.n_balance n_balance from bs_acctcurbalance ba2  \n");
        sbFrom.append("              ) all_balance \n");
        
        sbFrom.append(sbParam.toString());
        sbFrom.append(" 	) b \n");

        sbFrom.append(" WHERE  \n");

        /** ********************拼写查询条件串-START********************* */
        sbFrom.append(" 	acct.n_rdstatus > 0  \n");      	
        sbFrom.append("     and acct.n_ischeck = '1'\n"); //12.8 在对账单查询中增加审核条件为"审核"
        sbFrom.append("     and acct.n_Id = a.n_accountid(+)  \n");
        sbFrom.append(" 	and a.n_accountid = b.n_accountid(+) \n");
        sbFrom.append(" 	and a.maxDate = b.dt_date(+)  \n");
        sbFrom.append(" and acct.n_officeId = " + param.getOfficeID() + " \n");

        if (param.getClientIdFrom() > 0)//客户 从
        {
            sbFrom.append(" and acct.n_ClientId >= " + param.getClientIdFrom() + " \n");
        }
        if (param.getClientIdTo() > 0)//客户 到
        {
            sbFrom.append(" and acct.n_ClientId <= " + param.getClientIdTo()+ " \n");
        }
        if (param.getCountryId() > 0)//国家
        {
            sbFrom.append(" and acct.n_CountryId = " + param.getCountryId() + " \n");
        }
     //   if (param.getBankId() > 0)//银行
     //   {
     //       sbFrom.append(" and acct.n_BankId = " + param.getBankId() + " \n");
     //   }
        if (param.getAllbankId() !=null && param.getAllbankId().length()>0)//银行
           {
              if(!"all".equals(param.getAllbankId())){ 
        	      sbFrom.append(" and acct.n_BankId in( " + param.getAllbankId() + " )\n");
              }
           }else{
               sbFrom.append(" and acct.n_BankId = '-1' \n");        	   
           }
        
        if (param.getCurrencyType() > 0)//币种 
        {
            sbFrom.append(" and acct.n_CurrencyType = " + param.getCurrencyType() + " \n");
        }

        if (param.getOwnerType() > 0)//账户所有者属性
        {
            sbFrom.append(" and acct.n_OwnerType = " + param.getOwnerType()  + " \n");
        }

        if (param.getInputOrOutput() > 0)//收支属性
        {
            sbFrom.append(" and acct.n_InputOrOutput = " + param.getInputOrOutput() + " \n");
        }

        if (param.getIsDirectLink() > -1)//是否是直连账户
        {
            sbFrom.append(" and acct.n_IsDirectLink = " + param.getIsDirectLink() + " \n");
        }

        if (param.getAccountPropertyOne() > 0)//账户属性一
        {
            sbFrom.append(" and acct.n_AccountPropertyOne = "  + param.getAccountPropertyOne() + " \n");
        }

        if (param.getAccountPropertyTwo() > 0)//账户属性二
        {
            sbFrom.append(" and acct.n_AccountPropertyTwo = "  + param.getAccountPropertyTwo() + " \n");
        }

        if (param.getAccountPropertyThree() > 0)//账户属性三
        {
            sbFrom.append(" and acct.n_AccountPropertyThree = " + param.getAccountPropertyThree() + " \n");
        }
        

        if (param.getAccountId() > 0)//账户
        {
            sbFrom.append(" and acct.n_Id = " + param.getAccountId() + " \n");
        }
        sbFrom.append(" and  acct.s_accountno in \n");
        sbFrom.append(" ( select e.saccountno \n");
        sbFrom.append(" from OB_EbankAccountByUserQuery e,bs_bankaccountinfo t \n");
        sbFrom.append(" where e.saccountno=t.s_accountno \n");
        sbFrom.append(" and t.n_accountstatus=1 \n");
        sbFrom.append(" and t.n_rdstatus=1 \n");
        sbFrom.append(" and t.n_ischeck=1 \n");
        sbFrom.append(" and e.nuserid="+lUserID+" \n");
        sbFrom.append(" and t.n_currencytype="+param.getCurrencyType()+") \n");
        
        
        /** ********************拼写查询条件串-END*********************** */
        
        sbFrom.append("  )  \n");
        /** sbFrom---拼写结束* */

        /** sbWhere---拼写开始* */
        sbWhere.append("  \n ");
        
        
        
        /** sbWhere---拼写结束* */

    }

    public PageLoader findBillByCondition(QueryBillPrintParam param,long lUserID) throws Exception
    {        
        getSQL(param,lUserID);
        /** 获取PageLoader* */
        PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

        /** 初始化PageLoader * */
        pageLoader .initPageLoader(
                        new AppContext(),
                        sbFrom.toString(),
                        sbSelect.toString(),
                        sbWhere.toString(),
                        (int) PageControl.CODE_PAGELINECOUNT,
                        "com.iss.itreasury.ebank.system.dataentity.QueryBillPrintInfo",
                        null);

        return pageLoader;
    }
    /**
     * 网银查询银行账户信息 add by zcwang 2008-4-30
     * @param param
     * @return
     * @throws SystemException
     */
    public PageLoader findBillByCondition_accountForEbank(QueryBillPrintParam param) throws Exception
    {        
    	getSQL_accountForEbank(param);
        /** 获取PageLoader* */
        PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

        /** 初始化PageLoader * */
        pageLoader .initPageLoader(
                        new AppContext(),
                        sbFrom.toString(),
                        sbSelect.toString(),
                        sbWhere.toString(),
                        (int) PageControl.CODE_PAGELINECOUNT,
                        "com.iss.itreasury.ebank.system.dataentity.QueryBillPrintInfo",
                        null);

        return pageLoader;
    }
    /**
     * 网银查询银行账户信息SQL add by zcwang 2008-4-30
     * @param param
     * @throws SystemException
     */
    private void getSQL_accountForEbank(QueryBillPrintParam param) throws Exception
    {

        /** 查询区间* */
        String transStartDateString = DataFormat.formatDate(param.getTransactionStartDate(), 1);
        String transEndDateString = DataFormat.formatDate(param.getTransactionEndDate(), 1);

        sbSelect  = new StringBuffer();
        sbFrom    = new StringBuffer();
        sbWhere   = new StringBuffer();
        //sbOrderBy = new StringBuffer();
        sbParam   = new StringBuffer();

        /** 查询范围约束条件* */
        if (param.getTransactionStartDate() != null)//查询日期起
        {
            sbParam.append(" WHERE to_char( all_balance.dt_Date,'yyyy-mm-dd' ) >= " + "'" + transStartDateString + "'" + " \n");
        }

        if (param.getTransactionEndDate() != null)//查询日期止
        {
            sbParam.append(" and to_char( all_balance.dt_Date,'yyyy-mm-dd' ) <= " + "'" + transEndDateString + "'" + " \n");
        }

        sbSelect.append("  *  \n");

        /** sbFrom---拼写开始* */
        sbFrom.append("  ( \n");

        sbFrom.append(" SELECT  \n");
        sbFrom.append(" 	acct.n_Id  as accountId, --账户Id   \n");
        sbFrom.append(" 	acct.n_clientid  as clientId, --客户Id   \n");
        sbFrom.append(" 	acct.n_bankid  as bankId, --银行类型   \n");
        sbFrom.append(" 	acct.n_countryid as countryId, -- 国家Id  \n");
        sbFrom.append(" 	acct.n_currencytype as currencyType, -- 币种 \n");
        sbFrom.append(" 	acct.s_accountno as accountNo, -- 账户号 \n");
        sbFrom.append(" 	acct.s_accountname as accountName, -- 账户名称 \n");
        sbFrom.append(      "'"+transStartDateString+"' as queryStartDateString, -- 查询开始日期 \n");
        sbFrom.append(      "'"+transEndDateString+"' as queryEndDateString, -- 查询截至日期 \n");
        sbFrom.append(" 	a.minDate as balanceStartDate, -- 余额开始日 \n");
        sbFrom.append(" 	a.maxDate as balanceEndDate,  -- 余额结束日 \n");
        sbFrom.append(" 	b.n_balance as balance  -- 余额结束日的日终余额 \n");

        sbFrom.append(" FROM  \n");
        if(param.getClientId()>0)
        {
        	sbFrom.append(" 	bs_bankaccountinfo acct, ob_bankacctprvgbyclient bpg, \n");
        }
        else
        {
        	sbFrom.append(" 	bs_bankaccountinfo acct,  \n");
        }
        sbFrom.append(" 	(	select n_accountid, min(dt_date) as minDate ,max(dt_date) as maxDate  \n");
        sbFrom.append(" 		from ( \n");
        sbFrom.append("                select ba1.n_accountid n_accountid, ba1.dt_date dt_date from bs_accthisbalance ba1 \n");
        sbFrom.append("                union \n");
        sbFrom.append("                select ba2.n_accountid n_accountid, ba2.dt_importtime dt_date from bs_acctcurbalance ba2  \n");
        sbFrom.append("              ) all_balance \n");
        sbFrom.append(sbParam.toString());
        sbFrom.append(" 		group by n_accountid   \n");
        sbFrom.append(" 	) a,  \n");

        sbFrom.append(" 	(	select n_accountid, dt_date,n_balance \n");
        sbFrom.append(" 		from (  \n");
        sbFrom.append("                select ba1.n_accountid n_accountid, ba1.dt_date dt_date,ba1.n_balance n_balance from bs_accthisbalance ba1 \n");
        sbFrom.append("                union \n");
        sbFrom.append("                select ba2.n_accountid n_accountid, ba2.dt_importtime dt_date,ba2.n_balance n_balance from bs_acctcurbalance ba2  \n");
        sbFrom.append("              ) all_balance \n");
        
        sbFrom.append(sbParam.toString());
        sbFrom.append(" 	) b \n");

        sbFrom.append(" WHERE  \n");

        /** ********************拼写查询条件串-START********************* */
        sbFrom.append(" 	acct.n_rdstatus > 0  \n");      	
        sbFrom.append("     and acct.n_ischeck = 1 \n"); //12.8 在对账单查询中增加审核条件为"审核"
        sbFrom.append("     and acct.n_Id = a.n_accountid(+)  \n");
        sbFrom.append(" 	and a.n_accountid = b.n_accountid(+) \n");
        sbFrom.append(" 	and a.maxDate = b.dt_date(+)  \n");
        if(param.getClientId()>0)
        {
        	 sbFrom.append(" and bpg.ACCOUNTID=acct.N_ID \n");
        	 sbFrom.append(" and bpg.clientid = "+ param.getClientId() + "\n");
        	 if(param.getSubclientId()>0)
        	 {
        		 sbFrom.append(" and acct.n_ClientId = "+ param.getSubclientId() + "\n");
        	 }
        	 if(param.getSubaccountId()>0)
        	 {
        		 sbFrom.append(" and acct.n_Id = "+ param.getSubaccountId() + "\n");
        	 }
        	 
        }
        //增加对多办事处的处理
   //     if(!param.isHQ())
   //     {
   //     	sbFrom.append(" and acct.n_officeId = " + param.getOfficeID() + " \n");
   //     }
        if (param.getClientIdFrom() > 0)//客户 从
        {
            sbFrom.append(" and acct.n_ClientId >= " + param.getClientIdFrom() + " \n");
        }
        if (param.getClientIdTo() > 0)//客户 到
        {
            sbFrom.append(" and acct.n_ClientId <= " + param.getClientIdTo()+ " \n");
        }
        if (param.getCountryId() > 0)//国家
        {
            sbFrom.append(" and acct.n_CountryId = " + param.getCountryId() + " \n");
        }
     //   if (param.getBankId() > 0)//银行
     //   {
      //      sbFrom.append(" and acct.n_BankId = " + param.getBankId() + " \n");
     //   }                     
        if (param.getAllbankId() !=null && param.getAllbankId().length()>0)//银行
        {
        	if(!"all".equals(param.getAllbankId())){ 
      	      sbFrom.append(" and acct.n_BankId in( " + param.getAllbankId() + " )\n");
            }
        }else{
        	
        }
        
        if (param.getCurrencyType() > 0)//币种
        {
            sbFrom.append(" and acct.n_CurrencyType = " + param.getCurrencyType() + " \n");
        }

        if (param.getOwnerType() > 0)//账户所有者属性
        {
            sbFrom.append(" and acct.n_OwnerType = " + param.getOwnerType()  + " \n");
        }

        if (param.getInputOrOutput() > 0)//收支属性
        {
            sbFrom.append(" and acct.n_InputOrOutput = " + param.getInputOrOutput() + " \n");
        }

        if (param.getIsDirectLink() > -1)//是否是直连账户
        {
            sbFrom.append(" and acct.n_IsDirectLink = " + param.getIsDirectLink() + " \n");
        }

        if (param.getAccountPropertyOne() > 0)//账户属性一
        {
            sbFrom.append(" and acct.n_AccountPropertyOne = "  + param.getAccountPropertyOne() + " \n");
        }

        if (param.getAccountPropertyTwo() > 0)//账户属性二
        {
            sbFrom.append(" and acct.n_AccountPropertyTwo = "  + param.getAccountPropertyTwo() + " \n");
        }

        if (param.getAccountPropertyThree() > 0)//账户属性三
        {
            sbFrom.append(" and acct.n_AccountPropertyThree = " + param.getAccountPropertyThree() + " \n");
        }

        if (param.getAccountId() > 0)//账户
        {
            sbFrom.append(" and acct.n_Id = " + param.getAccountId() + " \n");
        }
        /** ********************拼写查询条件串-END*********************** */

        sbFrom.append("  )  \n");
        /** sbFrom---拼写结束* */

        /** sbWhere---拼写开始* */
        sbWhere.append("  ");
        /** sbWhere---拼写结束* */

    }
    /**
     * 返回单一账户在某一天的余额,从当日表和历史表中查询
     * 
     * @param param
     * @return @throws
     *         BusinessException
     * @throws Exception
     */
    public double findAcctBalance(QueryBillPrintParam param)  throws Exception
    {
        double result = 0.00;
        
        QAccountHisBalanceInfo hisConditionInfo = null;
        QAccountCurBalanceInfo curConditionInfo = null;
        if (param != null)
        {
            /** 某天的期初余额是前一天的期末余额 **/            
            Date tempDate = DataFormat.parseDate(DataFormat.formatDate(param.getTransactionStartDate(), 1), 1);
            Date beginDate = DateUtil.before( tempDate ,1 );
            
            hisConditionInfo = new QAccountHisBalanceInfo();
            hisConditionInfo.setAccountId(param.getAccountId());
            hisConditionInfo.setDate( beginDate );
            
            curConditionInfo = new QAccountCurBalanceInfo();
            curConditionInfo.setAccountId(param.getAccountId());
            curConditionInfo.setImportTime(beginDate);
            
        }
        //System.out.println("1 返回单一账户在某一天的余额   conditionInfo.getAccountId() = " + conditionInfo.getAccountId());
        //System.out.println("2 返回单一账户在某一天的余额   date = "+ conditionInfo.getDate());

        Collection coll = null;
        
        try
        {
            //首先从历史表里面查
        	AccountHisBalanceDAO hisDAO=new AccountHisBalanceDAO();
            coll = hisDAO.findByConditionAndTable(hisConditionInfo,AccountHisBalanceDAO.strTableName);
            if(coll!=null && coll.size()>0)
            {
                QAccountHisBalanceInfo hisBalance = (QAccountHisBalanceInfo) coll.toArray(new QAccountHisBalanceInfo[0])[0];
                result = hisBalance.getN_balance();
            }else{
            	AccountCurBalanceDAO curDAO=new AccountCurBalanceDAO();
                coll = curDAO.findByConditionAndTable(curConditionInfo,AccountCurBalanceDAO.strTableName);
                if(coll!=null && coll.size()>0)
                {
                    QAccountCurBalanceInfo curBalance = (QAccountCurBalanceInfo) coll.toArray(new QAccountCurBalanceInfo[0])[0];
                    result = curBalance.getN_balance();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("查询账户余额时出现异常，出错原因：" + e.getMessage(), e);
        }
        return result;
    }

    /**
     * 返回单一账户的历史余额信息
     * 
     * @param paramInfo
     * @return @throws
     *         BusinessException
     */
    public AcctTransInfo[] findSingleAcctHisBalance(AcctTransParam paramInfo) throws Exception
    {
        Collection coll = null;
        AcctTransInfo[] resultInfo = null;

        System.out .println("Log Here!!!!!!!!!----------findSingleAcctHisBalance");
        try
        {
            AccountHisTransDAO hisTransDAO = new AccountHisTransDAO();
            coll = hisTransDAO.findSingleAcctHisBalance(paramInfo);

            if (coll != null && coll.size() > 0)
            {
                resultInfo = new AcctTransInfo[0];
                resultInfo = (AcctTransInfo[]) coll.toArray(resultInfo);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("查询单一账户的历史余额信息时出现异常，出错原因："+ e.getMessage(), e);
        }

        return resultInfo;
    }
   
    /**
	 * 查找账户
	 * @param id
	 * @return AccountInfo
	 * @throws BusinessException
	 */
	public AccountInfo findAccountInfoByID(long id,String strTableName)throws Exception
	{
		AccountInfo result = null;
		try
		{
			AccountDAO acctDAO = new AccountDAO();
			result = (AccountInfo)acctDAO.findByID(id,AccountInfo.class,strTableName);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception("查询账户信息时出现异常，出错原因："+e.getMessage(), e);
		}
		return result;
	}
	
//	Connection con = null;
//	ResultSet rs = null;
//	PreparedStatement ps = null;
//	try
//	{
//		con = Database.getConnection();
//		String strSQL = "select t.* from BS_BANKACCOUNTINFO t  where t.ID="+id;
//		ps = con.prepareStatement(strSQL);
//		rs = ps.executeQuery();
//		
//		if (rs.next()) 
//		{
//			ClientID = rs.getLong("NCLIENTID");
//		}
//		
//	}
//	catch(Exception e)
//	{
//		Log.print(e.toString());
//	}
//	finally
//	{
//		try {
//			rs.close();
//			rs = null;
//			ps.close();
//			ps = null;
//			con.close();
//			con = null;
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}

	/**
	 * 账户对账单查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PagerInfo queryBillPrintInfo(Map map) throws Exception{

		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			QueryBillPrintParam paramInfo = (QueryBillPrintParam)map.get("info");
			long lUserID = Long.valueOf(map.get("lUserID").toString());
			//得到查询SQL
			sql = getBillPrintInfoSQL(paramInfo,lUserID);
			pagerInfo.setSqlString(sql);
			pagerInfo.setExtensionMothod(BillPrintBiz.class, "queryBillPrintInfoResultSetHandle", map);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	
	}
	
	/**
	 * 账户对账单查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public ArrayList queryBillPrintInfoResultSetHandle(ResultSet rs, Map map) throws Exception{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		long accountId = -1;
		long clientId = -1;
		long bankId = -1;
		long countryId = -1;
		long currencyType = -1;
		String accountNo = "";
		String accountName = "";
		String queryStartDateString = "";
		String queryEndDateString = "";
		Timestamp balanceStartDate = null;
		Timestamp balanceEndDate = null;
		double balance = 0.0;
		QueryBillPrintParam paramInfo = (QueryBillPrintParam)map.get("info");
		try {
			if(rs!=null)
			{
				while(rs.next())
				{
					QueryBillPrintInfo info = new QueryBillPrintInfo();
					
					//获取数据
					accountId = rs.getLong("accountId");
					clientId = rs.getLong("clientId");
					bankId = rs.getLong("bankId");
					countryId = rs.getLong("countryId");
					currencyType = rs.getLong("currencyType");
					accountNo = rs.getString("accountNo");
					accountName = rs.getString("accountName");
					queryStartDateString = rs.getString("queryStartDateString");
					queryEndDateString = rs.getString("queryEndDateString");
					balanceStartDate = rs.getTimestamp("balanceStartDate");
					balanceEndDate = rs.getTimestamp("balanceEndDate");
					balance = rs.getDouble("balance");
					
					//处理数据
					info.setAccountId(accountId);
					info.setClientId(clientId);
					info.setBankId(bankId);
					info.setCountryId(countryId);
					info.setCurrencyType(currencyType);
					info.setAccountNo(accountNo);
					info.setAccountName(accountName);
					info.setQueryStartDateString(queryStartDateString);
					info.setQueryEndDateString(queryEndDateString);
					info.setBalanceStartDate(balanceStartDate);
					info.setBalanceEndDate(balanceEndDate);
					info.setBalance(balance);
			        
					//存储列数据
					cellList = new ArrayList();
					PagerTools.returnCellList(cellList,com.iss.itreasury.ebank.util.NameRef.getClientCodeByID(info.getClientId()));
					PagerTools.returnCellList(cellList,com.iss.itreasury.ebank.util.NameRef.getClientNameByID(info.getClientId()));
					PagerTools.returnCellList(cellList,com.iss.itreasury.ebank.util.NameRef.getBankNameByID(info.getBankId())); 
					PagerTools.returnCellList(cellList,com.iss.itreasury.ebank.util.NameRef.getCountryNameByID(info.getCountryId())); 
					PagerTools.returnCellList(cellList,com.iss.itreasury.ebank.util.NameRef.getCurrencyNameByID(info.getCurrencyType()));
					PagerTools.returnCellList(cellList,info.getAccountNo()+","+info.getAccountId()+","+info.getQueryStartDateString()+","+info.getQueryEndDateString()+","+paramInfo.getBankType());
					PagerTools.returnCellList(cellList,DataFormat.formatString(info.getAccountName()));
					if( info.getBalanceStartDate() != null || info.getBalanceEndDate() != null ){
						PagerTools.returnCellList(cellList,DataFormat.formatDate(info.getBalanceStartDate(),DataFormat.FMT_DATE_YYYYMMDD)+" - "+DataFormat.formatDate(info.getBalanceEndDate(),DataFormat.FMT_DATE_YYYYMMDD));
					}else{
						PagerTools.returnCellList(cellList,"&nbsp;");
					}
					PagerTools.returnCellList(cellList,DataFormat.formatNumber(info.getBalance(),2));
				
					//存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					
					//返回数据
					resultList.add(rowInfo);
					
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	}
	
	
	public String getBillPrintInfoSQL(QueryBillPrintParam param , long lUserID){
		
		StringBuffer sbSQL= new StringBuffer();
		sbParam = new StringBuffer();
    	
        /** 查询区间* */
        String transStartDateString = DataFormat.formatDate(param.getTransactionStartDate(), 1);
        String transEndDateString = DataFormat.formatDate(param.getTransactionEndDate(), 1);

        /** 查询范围约束条件* */
        if (param.getTransactionStartDate() != null)//查询日期起
        {
            sbParam.append(" WHERE to_char( all_balance.dt_Date,'yyyy-mm-dd' ) >= " + "'" + transStartDateString + "'" + " \n");
        }

        if (param.getTransactionEndDate() != null)//查询日期止
        {
            sbParam.append(" and to_char( all_balance.dt_Date,'yyyy-mm-dd' ) <= " + "'" + transEndDateString + "'" + " \n");
        }

        sbSQL.append("  select * from \n");

        /** sbFrom---拼写开始* */
        sbSQL.append("  ( \n");

        sbSQL.append(" SELECT  \n");
        sbSQL.append(" 	acct.n_Id  as accountId, --账户Id   \n");
        sbSQL.append(" 	acct.n_clientid  as clientId, --客户Id   \n");
        sbSQL.append(" 	acct.n_bankid  as bankId, --银行类型   \n");
        sbSQL.append(" 	acct.n_countryid as countryId, -- 国家Id  \n");
        sbSQL.append(" 	acct.n_currencytype as currencyType, -- 币种 \n");
        sbSQL.append(" 	acct.s_accountno as accountNo, -- 账号 \n");
        sbSQL.append(" 	acct.s_accountname as accountName, -- 账户名称 \n");
        sbSQL.append(      "'"+transStartDateString+"' as queryStartDateString, -- 查询开始日期 \n");
        sbSQL.append(      "'"+transEndDateString+"' as queryEndDateString, -- 查询截至日期 \n");
        sbSQL.append(" 	a.minDate as balanceStartDate, -- 余额开始日 \n");
        sbSQL.append(" 	a.maxDate as balanceEndDate,  -- 余额结束日 \n");
        sbSQL.append(" 	b.n_balance as balance  -- 余额结束日的日终余额 \n");

        sbSQL.append(" FROM  \n");

        sbSQL.append(" 	bs_bankaccountinfo acct, \n");

        sbSQL.append(" 	(	select n_accountid, min(dt_date) as minDate ,max(dt_date) as maxDate  \n");
        sbSQL.append(" 		from ( \n");
        sbSQL.append("                select ba1.n_accountid n_accountid, ba1.dt_date dt_date from bs_accthisbalance ba1 \n");
        sbSQL.append("                union \n");
        sbSQL.append("                select ba2.n_accountid n_accountid, ba2.dt_importtime dt_date from bs_acctcurbalance ba2  \n");
        sbSQL.append("              ) all_balance \n");
        sbSQL.append(sbParam.toString());
        sbSQL.append(" 		group by n_accountid   \n");
        sbSQL.append(" 	) a,  \n");

        sbSQL.append(" 	(	select n_accountid, dt_date,n_balance \n");
        sbSQL.append(" 		from (  \n");
        sbSQL.append("                select ba1.n_accountid n_accountid, ba1.dt_date dt_date,ba1.n_balance n_balance from bs_accthisbalance ba1 \n");
        sbSQL.append("                union \n");
        sbSQL.append("                select ba2.n_accountid n_accountid, ba2.dt_importtime dt_date,ba2.n_balance n_balance from bs_acctcurbalance ba2  \n");
        sbSQL.append("              ) all_balance \n");
        
        sbSQL.append(sbParam.toString());
        sbSQL.append(" 	) b \n");

        sbSQL.append(" WHERE  \n");

        /** ********************拼写查询条件串-START********************* */
        sbSQL.append(" 	acct.n_rdstatus > 0  \n");      	
        sbSQL.append("     and acct.n_ischeck = '1'\n"); //12.8 在对账单查询中增加审核条件为"审核"
        sbSQL.append("     and acct.n_Id = a.n_accountid(+)  \n");
        sbSQL.append(" 	and a.n_accountid = b.n_accountid(+) \n");
        sbSQL.append(" 	and a.maxDate = b.dt_date(+)  \n");
        sbSQL.append(" and acct.n_officeId = " + param.getOfficeID() + " \n");

        if (param.getClientIdFrom() > 0)//客户 从
        {
            sbSQL.append(" and acct.n_ClientId >= " + param.getClientIdFrom() + " \n");
        }
        if (param.getClientIdTo() > 0)//客户 到
        {
            sbSQL.append(" and acct.n_ClientId <= " + param.getClientIdTo()+ " \n");
        }
        if (param.getCountryId() > 0)//国家
        {
            sbSQL.append(" and acct.n_CountryId = " + param.getCountryId() + " \n");
        }
        if (param.getAllbankId() !=null && param.getAllbankId().length()>0)//银行
           {
              if(!"all".equals(param.getAllbankId())){ 
        	      sbSQL.append(" and acct.n_BankId in( " + param.getAllbankId() + " )\n");
              }
           }else{
               sbSQL.append(" and acct.n_BankId = '-1' \n");        	   
           }
        
        if (param.getCurrencyType() > 0)//币种 
        {
            sbSQL.append(" and acct.n_CurrencyType = " + param.getCurrencyType() + " \n");
        }

        if (param.getOwnerType() > 0)//账户所有者属性
        {
            sbSQL.append(" and acct.n_OwnerType = " + param.getOwnerType()  + " \n");
        }

        if (param.getInputOrOutput() > 0)//收支属性
        {
            sbSQL.append(" and acct.n_InputOrOutput = " + param.getInputOrOutput() + " \n");
        }

        if (param.getIsDirectLink() > -1)//是否是直连账户
        {
            sbSQL.append(" and acct.n_IsDirectLink = " + param.getIsDirectLink() + " \n");
        }

        if (param.getAccountPropertyOne() > 0)//账户属性一
        {
            sbSQL.append(" and acct.n_AccountPropertyOne = "  + param.getAccountPropertyOne() + " \n");
        }

        if (param.getAccountPropertyTwo() > 0)//账户属性二
        {
            sbSQL.append(" and acct.n_AccountPropertyTwo = "  + param.getAccountPropertyTwo() + " \n");
        }

        if (param.getAccountPropertyThree() > 0)//账户属性三
        {
            sbSQL.append(" and acct.n_AccountPropertyThree = " + param.getAccountPropertyThree() + " \n");
        }

        if (param.getAccountId() > 0)//账户
        {
            sbSQL.append(" and acct.n_Id = " + param.getAccountId() + " \n");
        }
        sbSQL.append(" and  acct.s_accountno in \n");
        sbSQL.append(" ( select e.saccountno \n");
        sbSQL.append(" from OB_EbankAccountByUserQuery e,bs_bankaccountinfo t \n");
        sbSQL.append(" where e.saccountno=t.s_accountno \n");
        sbSQL.append(" and t.n_accountstatus=1 \n");
        sbSQL.append(" and t.n_rdstatus=1 \n");
        sbSQL.append(" and t.n_ischeck=1 \n");
        sbSQL.append(" and e.nuserid="+lUserID+" \n");
        sbSQL.append(" and t.n_currencytype="+param.getCurrencyType()+") \n");
        sbSQL.append("  )  \n");
		
		return sbSQL.toString();
		
	}
	
	
	/**
	 * 账户对账单明细查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PagerInfo queryBillPrintDetailInfo(QueryBillPrintParam param,AcctTransParam transParam) throws Exception{
		
		OBQueryTransAccountDao oBQueryTransAccountDao = new OBQueryTransAccountDao();
		/**账户历史余额Biz**/
		BillPrintBiz billPrintBiz = new BillPrintBiz();
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			Map paramMap = new HashMap();
			sql = "select 1 from sys_user ";
			//sql = getBillPrintDetailInfoSQL(transParam);
			//得到查询SQL
			pagerInfo.setSqlString(sql);
			double beginBalance   = Double.NaN;
			beginBalance = billPrintBiz.findAcctBalance( param );
			paramMap.put("beginBalance", beginBalance);
			paramMap.put("transParam", transParam);
			pagerInfo.setExtensionMothod(BillPrintBiz.class, "queryBillPrintDetailInfoResultSetHandle" , paramMap);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	/**
	 * 账户对账单明细查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public ArrayList queryBillPrintDetailInfoResultSetHandle(ResultSet rs , Map paramMap) throws Exception{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		long isIntrDate = -1;
		Timestamp interestStartDate = null;
		Timestamp executeDate = null;
		String sAbstract = "";
		long transTypeId = -1;
		String transNo = "";
		long groupId = -1;
		String billNo = "";
		String oppAccountNo = "";
		String oppAccountName = "";
		double payAmount = 0.0;
		double receiveAmount = 0.0;
		Timestamp tsStartDate = null;
		Timestamp tsEndDate = null;
		String executeMonth = "";
		String executeYear = "";
		double startBalance          = 0.00;  //期初余额
		double sumDebitAmount  		 = 0.00;  //借方金额合计
		double sumCreditAmount 		 = 0.00;  //贷方金额合计
		double sumDebitAmountPerDay  = 0.00;  //每日借方金额合计
		double sumCreditAmountPerDay = 0.00;  //每日贷方金额合计
		double beginBalancePerDay	=0.00;	//每日的期初余额		
		String strDebitAmount = null;
		String strCreditAmount = null;				
		Date   statDate       		 = null;  //日期		
		
		
		try{
			AcctTransInfo[] transInfos = null;
			/**账户历史余额Biz**/
			BillPrintBiz billPrintBiz = new BillPrintBiz();
	        AcctTransParam transParam = (AcctTransParam)paramMap.get("transParam");
	        transInfos = billPrintBiz.findSingleAcctHisBalance( transParam );    
	            
			double beginBalance = Double.valueOf(paramMap.get("beginBalance").toString()).doubleValue();
			
			startBalance = beginBalance;
			beginBalancePerDay = beginBalance;
			
			//存储列数据
			cellList = new ArrayList();
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"<B>期初余额</B>");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"<B>"+(DataFormat.formatNumber(beginBalance,2))+"</B>");
			//存储行数据
			rowInfo = new ResultPagerRowInfo();
			rowInfo.setCell(cellList);
			//返回数据
			resultList.add(rowInfo);
			
			
			if(transInfos!=null && transInfos.length>0)
			{
				//临时日期
				statDate = (transInfos[0]).getTransactionTime();
				
				for(int i = 0;i<transInfos.length;i++)
				{
				
					AcctTransInfo info = transInfos[i];
					
					if( info.getDirection() == AccountInfo.DEBIT )
					{
						sumDebitAmount   += info.getDebitAmount();
						strDebitAmount = DataFormat.formatNumber(info.getDebitAmount(),2);
						strCreditAmount = "";									
					}
					else if( info.getDirection() == AccountInfo.CREDIT )
					{
						sumCreditAmount  += info.getCreditAmount();
						strCreditAmount = DataFormat.formatNumber(info.getCreditAmount(),2);
						strDebitAmount = "";																										
					}
					
					if( transInfos.length == 1 || info.getTransactionTime().equals(statDate) )
					{
						//汇总账户同一天的借贷方金额
						sumDebitAmountPerDay   += info.getDebitAmount();
						sumCreditAmountPerDay  += info.getCreditAmount();
					}
					
					if(  !info.getTransactionTime().equals(statDate) )
					{
						beginBalancePerDay = Arithmetic.add( beginBalancePerDay,Arithmetic.sub(sumCreditAmountPerDay,sumDebitAmountPerDay) );								
								
						//存储列数据
						cellList = new ArrayList();
						PagerTools.returnCellList(cellList,DataFormat.formatDate(statDate,1));
						PagerTools.returnCellList(cellList,"<b>本日合计</b>");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"<B>"+(DataFormat.formatNumber( sumDebitAmountPerDay!=Double.NaN?sumDebitAmountPerDay:0.00,2))+"</B>");
						PagerTools.returnCellList(cellList,"<B>"+(DataFormat.formatNumber( sumCreditAmountPerDay!=Double.NaN?sumCreditAmountPerDay:0.00,2))+"</B>");
						PagerTools.returnCellList(cellList,"<B>"+(DataFormat.formatNumber( beginBalancePerDay!=Double.NaN?beginBalancePerDay:0.00 ,2 ))+"</B>");
						//存储行数据
						rowInfo = new ResultPagerRowInfo();
						rowInfo.setCell(cellList);
						//返回数据
						resultList.add(rowInfo);
								
						statDate = info.getTransactionTime();
						sumDebitAmountPerDay   = info.getDebitAmount();
						sumCreditAmountPerDay  = info.getCreditAmount();
					}
					
					String strBalance ="";
					double balance = beginBalance+info.getCreditAmount()-info.getDebitAmount();
					beginBalance = balance;
					strBalance = DataFormat.formatNumber(balance , 2);
					
					String infoCheckNo = "";
					if(info.getCheckNo() != null && info.getCheckNo().trim().length() > 0 && !info.getCheckNo().endsWith("0")){
						infoCheckNo = info.getCheckNo();
					}
	                  
					//存储列数据
					cellList = new ArrayList();
					PagerTools.returnCellList(cellList,DataFormat.formatDate(info.getTransactionTime(),1));
					PagerTools.returnCellList(cellList,DataFormat.formatString(info.getAbstractInfo()));
					PagerTools.returnCellList(cellList,infoCheckNo);
					PagerTools.returnCellList(cellList,DataFormat.formatString(info.getOppAccountNo()));
					PagerTools.returnCellList(cellList,DataFormat.formatString(info.getOppAccountName()));
					PagerTools.returnCellList(cellList,strDebitAmount);
					PagerTools.returnCellList(cellList,strCreditAmount);
					PagerTools.returnCellList(cellList,strBalance);
					//存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					//返回数据
					resultList.add(rowInfo);
					
					/** 如果只有一条记录，则在这条记录后面输出一行本日合计
					 * &如果已经是最后一条记录了，则在这条记录后面输出一行本日合计
					 **/
					if( transInfos.length == 1 || i == transInfos.length -1 )
					{
						beginBalancePerDay = Arithmetic.add( beginBalancePerDay,Arithmetic.sub(sumCreditAmountPerDay,sumDebitAmountPerDay) );																
						
						//存储列数据
						cellList = new ArrayList();
						PagerTools.returnCellList(cellList,DataFormat.formatDate(statDate,1));
						PagerTools.returnCellList(cellList,"<b>本日合计</b>");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,DataFormat.formatNumber( sumDebitAmountPerDay!=Double.NaN?sumDebitAmountPerDay:0.00 ,2));
						PagerTools.returnCellList(cellList,DataFormat.formatNumber( sumCreditAmountPerDay!=Double.NaN?sumCreditAmountPerDay:0.00,2));
						PagerTools.returnCellList(cellList,DataFormat.formatNumber( beginBalancePerDay!=Double.NaN?beginBalancePerDay:0.00 ,2  ));
						//存储行数据
						rowInfo = new ResultPagerRowInfo();
						rowInfo.setCell(cellList);
						//返回数据
						resultList.add(rowInfo);
						
						//痴思化统计数据清零
						statDate = info.getTransactionTime();
						sumDebitAmountPerDay  = 0.00;
						sumCreditAmountPerDay = 0.00;
					}
				}
			}
					
			//存储列数据
			cellList = new ArrayList();
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"<B>期末余额</B>");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"<B>"+(DataFormat.formatNumber( sumDebitAmount!=Double.NaN?sumDebitAmount:0.00,2)) + "</B>");
			PagerTools.returnCellList(cellList,"<B>"+(DataFormat.formatNumber( sumCreditAmount!=Double.NaN?sumCreditAmount:0.00,2)) + "</B>");
			PagerTools.returnCellList(cellList,"<B>"+(DataFormat.formatNumber(	Arithmetic.add( startBalance,Arithmetic.sub(sumCreditAmount,sumDebitAmount) )  ,2  )) + "</B>");
			//存储行数据
			rowInfo = new ResultPagerRowInfo();
			rowInfo.setCell(cellList);
			//返回数据
			resultList.add(rowInfo);
					
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	}
	
	
	public String getBillPrintDetailInfoSQL(AcctTransParam qInfo){
		
		StringBuffer sbSQL= new StringBuffer();
		
		sbSQL.append(" 		SELECT dt_TransactionTime as transactionTime , \n");
        sbSQL.append(" 		  s_RemarkInfo as remarkInfo , \n");
        sbSQL.append(" 		  s_Abstractinfo as abstractInfo , \n");
        sbSQL.append(" 		  s_CheckNo as checkNo , \n");
        sbSQL.append(" 		  s_OppAccountNo as oppAccountNo , \n");
        sbSQL.append(" 		  s_OppAccountName as oppAccountName , \n");
        sbSQL.append(" 		  n_direction as direction , \n");
        sbSQL.append(" 		  decode(n_direction,1,n_amount,null) as debitAmount , \n");
        sbSQL.append(" 		  decode(n_direction,2,n_amount,null) as creditAmount  \n");
        sbSQL.append(" 		FROM ( \n");
        sbSQL.append("           SELECT his.* from bs_accthistransinfo his \n");
        sbSQL.append("           union \n");
        sbSQL.append("           SELECT cur.* from bs_acctcurtransinfo cur \n");
        sbSQL.append("           ) all_transinfo \n");
        sbSQL.append(" 		WHERE   \n");

        sbSQL.append(" \n 	n_accountid = " + qInfo.getAccountId());
        
        //12.23日修改，更正对账单中统计已被银行删除数据
        sbSQL.append("\n  and n_isdeletedbybank = 0 ");

        if (qInfo.getTransactionStartDate() != null)// 查询日期起
        {
            sbSQL.append(" and to_char( dt_transactiontime,'yyyy-mm-dd' ) >= " + "'"
                    + DataFormat.formatDate(qInfo.getTransactionStartDate(), 1) + "'" + " \n");
        }

        if (qInfo.getTransactionEndDate() != null)// 查询日期止
        {
            sbSQL.append(" and to_char( dt_transactiontime,'yyyy-mm-dd' ) <= " + "'"
                    + DataFormat.formatDate(qInfo.getTransactionEndDate(), 1) + "'" + " \n");
        }

        sbSQL.append(" Order By transactionTime \n");
	        
		return sbSQL.toString();
	}
	
	/**
	 * 下属单位银行账户
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PagerInfo querySubAccountBillPrintInfo(Map map) throws Exception{

		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			QueryBillPrintParam paramInfo = (QueryBillPrintParam)map.get("info");
			//得到查询SQL
			sql = getSubAccountBillPrintInfoSQL(paramInfo);
			pagerInfo.setSqlString(sql);
			pagerInfo.setExtensionMothod(BillPrintBiz.class, "querySubAccountBillPrintInfoResultSetHandle", map);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	
	}
	
	/**
	 * 下属单位银行账户
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public ArrayList querySubAccountBillPrintInfoResultSetHandle(ResultSet rs, Map map) throws Exception{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		long accountId = -1;
		long clientId = -1;
		long bankId = -1;
		long countryId = -1;
		long currencyType = -1;
		String accountNo = "";
		String accountName = "";
		String queryStartDateString = "";
		String queryEndDateString = "";
		Timestamp balanceStartDate = null;
		Timestamp balanceEndDate = null;
		double balance = 0.0;
		QueryBillPrintParam paramInfo = (QueryBillPrintParam)map.get("info");
		try {
			if(rs!=null)
			{
				while(rs.next())
				{
					QueryBillPrintInfo info = new QueryBillPrintInfo();
					
					//获取数据
					accountId = rs.getLong("accountId");
					clientId = rs.getLong("clientId");
					bankId = rs.getLong("bankId");
					countryId = rs.getLong("countryId");
					currencyType = rs.getLong("currencyType");
					accountNo = rs.getString("accountNo");
					accountName = rs.getString("accountName");
					queryStartDateString = rs.getString("queryStartDateString");
					queryEndDateString = rs.getString("queryEndDateString");
					balanceStartDate = rs.getTimestamp("balanceStartDate");
					balanceEndDate = rs.getTimestamp("balanceEndDate");
					balance = rs.getDouble("balance");
					
					//处理数据
					info.setAccountId(accountId);
					info.setClientId(clientId);
					info.setBankId(bankId);
					info.setCountryId(countryId);
					info.setCurrencyType(currencyType);
					info.setAccountNo(accountNo);
					info.setAccountName(accountName);
					info.setQueryStartDateString(queryStartDateString);
					info.setQueryEndDateString(queryEndDateString);
					info.setBalanceStartDate(balanceStartDate);
					info.setBalanceEndDate(balanceEndDate);
					info.setBalance(balance);
			        
					//存储列数据
					cellList = new ArrayList();
					PagerTools.returnCellList(cellList,com.iss.itreasury.ebank.util.NameRef.getClientCodeByID(info.getClientId()));
					PagerTools.returnCellList(cellList,com.iss.itreasury.ebank.util.NameRef.getClientNameByID(info.getClientId()));
					PagerTools.returnCellList(cellList,com.iss.itreasury.ebank.util.NameRef.getBankNameByID(info.getBankId())); 
					PagerTools.returnCellList(cellList,com.iss.itreasury.ebank.util.NameRef.getCountryNameByID(info.getCountryId())); 
					PagerTools.returnCellList(cellList,com.iss.itreasury.ebank.util.NameRef.getCurrencyNameByID(info.getCurrencyType()));
					PagerTools.returnCellList(cellList,info.getAccountNo()+","+info.getAccountId()+","+info.getQueryStartDateString()+","+info.getQueryEndDateString()+","+paramInfo.getBankType());
					PagerTools.returnCellList(cellList,DataFormat.formatString(info.getAccountName()));
					if( info.getBalanceStartDate() != null || info.getBalanceEndDate() != null ){
						PagerTools.returnCellList(cellList,DataFormat.formatDate(info.getBalanceStartDate(),DataFormat.FMT_DATE_YYYYMMDD)+" - "+DataFormat.formatDate(info.getBalanceEndDate(),DataFormat.FMT_DATE_YYYYMMDD));
					}else{
						PagerTools.returnCellList(cellList,"&nbsp;");
					}
					PagerTools.returnCellList(cellList,DataFormat.formatNumber(info.getBalance(),2));
				
					//存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					
					//返回数据
					resultList.add(rowInfo);
					
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	}
	
	
	public String getSubAccountBillPrintInfoSQL(QueryBillPrintParam param){
		
		StringBuffer sbSQL= new StringBuffer();
		sbParam = new StringBuffer();
    	
        /** 查询区间* */
        String transStartDateString = DataFormat.formatDate(param.getTransactionStartDate(), 1);
        String transEndDateString = DataFormat.formatDate(param.getTransactionEndDate(), 1);

        /** 查询范围约束条件* */
        if (param.getTransactionStartDate() != null)//查询日期起
        {
            sbParam.append(" WHERE to_char( all_balance.dt_Date,'yyyy-mm-dd' ) >= " + "'" + transStartDateString + "'" + " \n");
        }

        if (param.getTransactionEndDate() != null)//查询日期止
        {
            sbParam.append(" and to_char( all_balance.dt_Date,'yyyy-mm-dd' ) <= " + "'" + transEndDateString + "'" + " \n");
        }

        sbSQL.append("  select * from \n");

        /** sbFrom---拼写开始* */
        sbSQL.append("  ( \n");

        sbSQL.append(" SELECT  \n");
        sbSQL.append(" 	acct.n_Id  as accountId, --账户Id   \n");
        sbSQL.append(" 	acct.n_clientid  as clientId, --客户Id   \n");
        sbSQL.append(" 	acct.n_bankid  as bankId, --银行类型   \n");
        sbSQL.append(" 	acct.n_countryid as countryId, -- 国家Id  \n");
        sbSQL.append(" 	acct.n_currencytype as currencyType, -- 币种 \n");
        sbSQL.append(" 	acct.s_accountno as accountNo, -- 账户号 \n");
        sbSQL.append(" 	acct.s_accountname as accountName, -- 账户名称 \n");
        sbSQL.append(      "'"+transStartDateString+"' as queryStartDateString, -- 查询开始日期 \n");
        sbSQL.append(      "'"+transEndDateString+"' as queryEndDateString, -- 查询截至日期 \n");
        sbSQL.append(" 	a.minDate as balanceStartDate, -- 余额开始日 \n");
        sbSQL.append(" 	a.maxDate as balanceEndDate,  -- 余额结束日 \n");
        sbSQL.append(" 	b.n_balance as balance  -- 余额结束日的日终余额 \n");

        sbSQL.append(" FROM  \n");
        if(param.getClientId()>0)
        {
        	sbSQL.append(" 	bs_bankaccountinfo acct, ob_bankacctprvgbyclient bpg, \n");
        }
        else
        {
        	sbSQL.append(" 	bs_bankaccountinfo acct,  \n");
        }

        sbSQL.append(" 	(	select n_accountid, min(dt_date) as minDate ,max(dt_date) as maxDate  \n");
        sbSQL.append(" 		from ( \n");
        sbSQL.append("                select ba1.n_accountid n_accountid, ba1.dt_date dt_date from bs_accthisbalance ba1 \n");
        sbSQL.append("                union \n");
        sbSQL.append("                select ba2.n_accountid n_accountid, ba2.dt_importtime dt_date from bs_acctcurbalance ba2  \n");
        sbSQL.append("              ) all_balance \n");
        sbSQL.append(sbParam.toString());
        sbSQL.append(" 		group by n_accountid   \n");
        sbSQL.append(" 	) a,  \n");
        sbSQL.append(" 	(	select n_accountid, dt_date,n_balance \n");
        sbSQL.append(" 		from (  \n");
        sbSQL.append("                select ba1.n_accountid n_accountid, ba1.dt_date dt_date,ba1.n_balance n_balance from bs_accthisbalance ba1 \n");
        sbSQL.append("                union \n");
        sbSQL.append("                select ba2.n_accountid n_accountid, ba2.dt_importtime dt_date,ba2.n_balance n_balance from bs_acctcurbalance ba2  \n");
        sbSQL.append("              ) all_balance \n");
        
        sbSQL.append(sbParam.toString());
        sbSQL.append(" 	) b \n");

        sbSQL.append(" WHERE  \n");


        /** ********************拼写查询条件串-START********************* */
        sbSQL.append(" 	acct.n_rdstatus > 0  \n");      	
        sbSQL.append("     and acct.n_ischeck = 1 \n"); //12.8 在对账单查询中增加审核条件为"审核"
        sbSQL.append("     and acct.n_Id = a.n_accountid(+)  \n");
        sbSQL.append(" 	and a.n_accountid = b.n_accountid(+) \n");
        sbSQL.append(" 	and a.maxDate = b.dt_date(+)  \n");
        if(param.getClientId()>0)
        {
        	 sbSQL.append(" and bpg.ACCOUNTID=acct.N_ID \n");
        	 sbSQL.append(" and bpg.clientid = "+ param.getClientId() + "\n");
        	 if(param.getSubclientId()>0)
        	 {
        		 sbSQL.append(" and acct.n_ClientId = "+ param.getSubclientId() + "\n");
        	 }
        	 if(param.getSubaccountId()>0)
        	 {
        		 sbSQL.append(" and acct.n_Id = "+ param.getSubaccountId() + "\n");
        	 }
        	 
        }
        if (param.getClientIdFrom() > 0)//客户 从
        {
            sbSQL.append(" and acct.n_ClientId >= " + param.getClientIdFrom() + " \n");
        }
        if (param.getClientIdTo() > 0)//客户 到
        {
            sbSQL.append(" and acct.n_ClientId <= " + param.getClientIdTo()+ " \n");
        }
        if (param.getCountryId() > 0)//国家
        {
            sbSQL.append(" and acct.n_CountryId = " + param.getCountryId() + " \n");
        }
        if (param.getAllbankId() !=null && param.getAllbankId().length()>0)//银行
        {
        	if(!"all".equals(param.getAllbankId())){ 
      	      sbSQL.append(" and acct.n_BankId in( " + param.getAllbankId() + " )\n");
            }
        }else{
        	
        }
        
        if (param.getCurrencyType() > 0)//币种
        {
            sbSQL.append(" and acct.n_CurrencyType = " + param.getCurrencyType() + " \n");
        }

        if (param.getOwnerType() > 0)//账户所有者属性
        {
            sbSQL.append(" and acct.n_OwnerType = " + param.getOwnerType()  + " \n");
        }

        if (param.getInputOrOutput() > 0)//收支属性
        {
            sbSQL.append(" and acct.n_InputOrOutput = " + param.getInputOrOutput() + " \n");
        }

        if (param.getIsDirectLink() > -1)//是否是直连账户
        {
            sbSQL.append(" and acct.n_IsDirectLink = " + param.getIsDirectLink() + " \n");
        }

        if (param.getAccountPropertyOne() > 0)//账户属性一
        {
            sbSQL.append(" and acct.n_AccountPropertyOne = "  + param.getAccountPropertyOne() + " \n");
        }

        if (param.getAccountPropertyTwo() > 0)//账户属性二
        {
            sbSQL.append(" and acct.n_AccountPropertyTwo = "  + param.getAccountPropertyTwo() + " \n");
        }

        if (param.getAccountPropertyThree() > 0)//账户属性三
        {
            sbSQL.append(" and acct.n_AccountPropertyThree = " + param.getAccountPropertyThree() + " \n");
        }

        if (param.getAccountId() > 0)//账户
        {
            sbSQL.append(" and acct.n_Id = " + param.getAccountId() + " \n");
        }
        /** ********************拼写查询条件串-END*********************** */

        sbSQL.append("  )  \n");
        /** sbFrom---拼写结束* */
		
		return sbSQL.toString();
		
	}
	
	
	 /**
	 * 下属单位银行账户明细查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PagerInfo querySubAccountBillPrintDetailInfo(QueryBillPrintParam param,AcctTransParam transParam) throws Exception{
		
		OBQueryTransAccountDao oBQueryTransAccountDao = new OBQueryTransAccountDao();
		/**账户历史余额Biz**/
		BillPrintBiz billPrintBiz = new BillPrintBiz();
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			Map paramMap = new HashMap();
			//sql = "select 1 from sys_user where 1=2";
			sql = getSubAccountBillPrintDetailInfoSQL(transParam);
			//得到查询SQL
			pagerInfo.setSqlString(sql);
			double beginBalance   = Double.NaN;
			beginBalance = billPrintBiz.findAcctBalance( param );
			paramMap.put("beginBalance", beginBalance);
			paramMap.put("transParam", transParam);
			pagerInfo.setExtensionMothod(BillPrintBiz.class, "querySubAccountBillPrintDetailInfoResultSetHandle" , paramMap);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	/**
	 * 下属单位银行账户明细查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public ArrayList querySubAccountBillPrintDetailInfoResultSetHandle(ResultSet rs , Map paramMap) throws Exception{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		long isIntrDate = -1;
		Timestamp interestStartDate = null;
		Timestamp executeDate = null;
		String sAbstract = "";
		long transTypeId = -1;
		String transNo = "";
		long groupId = -1;
		String billNo = "";
		String oppAccountNo = "";
		String oppAccountName = "";
		double payAmount = 0.0;
		double receiveAmount = 0.0;
		Timestamp tsStartDate = null;
		Timestamp tsEndDate = null;
		String executeMonth = "";
		String executeYear = "";
		double startBalance          = 0.00;  //期初余额
		double sumDebitAmount  		 = 0.00;  //借方金额合计
		double sumCreditAmount 		 = 0.00;  //贷方金额合计
		double sumDebitAmountPerDay  = 0.00;  //每日借方金额合计
		double sumCreditAmountPerDay = 0.00;  //每日贷方金额合计
		double beginBalancePerDay	=0.00;	//每日的期初余额		
		String strDebitAmount = null;
		String strCreditAmount = null;				
		Date   statDate       		 = null;  //日期		
		
		
		try{
			AcctTransInfo[] transInfos = null;
			/**账户历史余额Biz**/
			BillPrintBiz billPrintBiz = new BillPrintBiz();
	        AcctTransParam transParam = (AcctTransParam)paramMap.get("transParam");
	        transInfos = billPrintBiz.findSingleAcctHisBalance( transParam );    
	            
			double beginBalance = Double.valueOf(paramMap.get("beginBalance").toString()).doubleValue();
			
			startBalance = beginBalance;
			beginBalancePerDay = beginBalance;
			
			//存储列数据
			cellList = new ArrayList();
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"<B>期初余额</B>");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"<B>"+(DataFormat.formatNumber(beginBalance,2))+"</B>");
			//存储行数据
			rowInfo = new ResultPagerRowInfo();
			rowInfo.setCell(cellList);
			//返回数据
			resultList.add(rowInfo);
			
			
			if(transInfos!=null && transInfos.length>0)
			{
				//临时日期
				statDate = (transInfos[0]).getTransactionTime();
				
				for(int i = 0;i<transInfos.length;i++)
				{
				
					AcctTransInfo info = transInfos[i];
					
					if( info.getDirection() == AccountInfo.DEBIT )
					{
						sumDebitAmount   += info.getDebitAmount();
						strDebitAmount = DataFormat.formatNumber(info.getDebitAmount(),2);
						strCreditAmount = "";									
					}
					else if( info.getDirection() == AccountInfo.CREDIT )
					{
						sumCreditAmount  += info.getCreditAmount();
						strCreditAmount = DataFormat.formatNumber(info.getCreditAmount(),2);
						strDebitAmount = "";																										
					}
					
					if( transInfos.length == 1 || info.getTransactionTime().equals(statDate) )
					{
						//汇总账户同一天的借贷方金额
						sumDebitAmountPerDay   += info.getDebitAmount();
						sumCreditAmountPerDay  += info.getCreditAmount();
					}
					
					if(  !info.getTransactionTime().equals(statDate) )
					{
						beginBalancePerDay = Arithmetic.add( beginBalancePerDay,Arithmetic.sub(sumCreditAmountPerDay,sumDebitAmountPerDay) );								
								
						//存储列数据
						cellList = new ArrayList();
						PagerTools.returnCellList(cellList,DataFormat.formatDate(statDate,1));
						PagerTools.returnCellList(cellList,"<b>本日合计</b>");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"<B>"+(DataFormat.formatNumber( sumDebitAmountPerDay!=Double.NaN?sumDebitAmountPerDay:0.00,2))+"</B>");
						PagerTools.returnCellList(cellList,"<B>"+(DataFormat.formatNumber( sumCreditAmountPerDay!=Double.NaN?sumCreditAmountPerDay:0.00,2))+"</B>");
						PagerTools.returnCellList(cellList,"<B>"+(DataFormat.formatNumber( beginBalancePerDay!=Double.NaN?beginBalancePerDay:0.00 ,2 ))+"</B>");
						//存储行数据
						rowInfo = new ResultPagerRowInfo();
						rowInfo.setCell(cellList);
						//返回数据
						resultList.add(rowInfo);
								
						statDate = info.getTransactionTime();
						sumDebitAmountPerDay   = info.getDebitAmount();
						sumCreditAmountPerDay  = info.getCreditAmount();
					}
					
					String strBalance ="";
					double balance = beginBalance+info.getCreditAmount()-info.getDebitAmount();
					beginBalance = balance;
					strBalance = DataFormat.formatNumber(balance , 2);
					
					String infoCheckNo = "";
					if(info.getCheckNo() != null && info.getCheckNo().trim().length() > 0 && !info.getCheckNo().endsWith("0")){
						infoCheckNo = info.getCheckNo();
					}
	                  
					//存储列数据
					cellList = new ArrayList();
					PagerTools.returnCellList(cellList,DataFormat.formatDate(info.getTransactionTime(),1));
					PagerTools.returnCellList(cellList,DataFormat.formatString(info.getAbstractInfo()));
					PagerTools.returnCellList(cellList,infoCheckNo);
					PagerTools.returnCellList(cellList,DataFormat.formatString(info.getOppAccountNo()));
					PagerTools.returnCellList(cellList,DataFormat.formatString(info.getOppAccountName()));
					PagerTools.returnCellList(cellList,strDebitAmount);
					PagerTools.returnCellList(cellList,strCreditAmount);
					PagerTools.returnCellList(cellList,strBalance);
					//存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					//返回数据
					resultList.add(rowInfo);
					
					/** 如果只有一条记录，则在这条记录后面输出一行本日合计
					 * &如果已经是最后一条记录了，则在这条记录后面输出一行本日合计
					 **/
					if( transInfos.length == 1 || i == transInfos.length -1 )
					{
						beginBalancePerDay = Arithmetic.add( beginBalancePerDay,Arithmetic.sub(sumCreditAmountPerDay,sumDebitAmountPerDay) );																
						
						//存储列数据
						cellList = new ArrayList();
						PagerTools.returnCellList(cellList,DataFormat.formatDate(statDate,1));
						PagerTools.returnCellList(cellList,"<b>本日合计</b>");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,DataFormat.formatNumber( sumDebitAmountPerDay!=Double.NaN?sumDebitAmountPerDay:0.00 ,2));
						PagerTools.returnCellList(cellList,DataFormat.formatNumber( sumCreditAmountPerDay!=Double.NaN?sumCreditAmountPerDay:0.00,2));
						PagerTools.returnCellList(cellList,DataFormat.formatNumber( beginBalancePerDay!=Double.NaN?beginBalancePerDay:0.00 ,2  ));
						//存储行数据
						rowInfo = new ResultPagerRowInfo();
						rowInfo.setCell(cellList);
						//返回数据
						resultList.add(rowInfo);
						
						//痴思化统计数据清零
						statDate = info.getTransactionTime();
						sumDebitAmountPerDay  = 0.00;
						sumCreditAmountPerDay = 0.00;
					}
				}
			}
					
			//存储列数据
			cellList = new ArrayList();
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"<B>期末余额</B>");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"<B>"+(DataFormat.formatNumber( sumDebitAmount!=Double.NaN?sumDebitAmount:0.00,2)) + "</B>");
			PagerTools.returnCellList(cellList,"<B>"+(DataFormat.formatNumber( sumCreditAmount!=Double.NaN?sumCreditAmount:0.00,2)) + "</B>");
			PagerTools.returnCellList(cellList,"<B>"+(DataFormat.formatNumber(	Arithmetic.add( startBalance,Arithmetic.sub(sumCreditAmount,sumDebitAmount) )  ,2  )) + "</B>");
			//存储行数据
			rowInfo = new ResultPagerRowInfo();
			rowInfo.setCell(cellList);
			//返回数据
			resultList.add(rowInfo);
					
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	}
	
	
	public String getSubAccountBillPrintDetailInfoSQL(AcctTransParam qInfo){
		
		StringBuffer sbSQL= new StringBuffer();
		
		sbSQL.append(" 		SELECT dt_TransactionTime as transactionTime , \n");
        sbSQL.append(" 		  s_RemarkInfo as remarkInfo , \n");
        sbSQL.append(" 		  s_Abstractinfo as abstractInfo , \n");
        sbSQL.append(" 		  s_CheckNo as checkNo , \n");
        sbSQL.append(" 		  s_OppAccountNo as oppAccountNo , \n");
        sbSQL.append(" 		  s_OppAccountName as oppAccountName , \n");
        sbSQL.append(" 		  n_direction as direction , \n");
        sbSQL.append(" 		  decode(n_direction,1,n_amount,null) as debitAmount , \n");
        sbSQL.append(" 		  decode(n_direction,2,n_amount,null) as creditAmount  \n");
        sbSQL.append(" 		FROM ( \n");
        sbSQL.append("           SELECT his.* from bs_accthistransinfo his \n");
        sbSQL.append("           union \n");
        sbSQL.append("           SELECT cur.* from bs_acctcurtransinfo cur \n");
        sbSQL.append("           ) all_transinfo \n");
        sbSQL.append(" 		WHERE   \n");

        sbSQL.append(" \n 	n_accountid = " + qInfo.getAccountId());
        
        //12.23日修改，更正对账单中统计已被银行删除数据
        sbSQL.append("\n  and n_isdeletedbybank = 0 ");

        if (qInfo.getTransactionStartDate() != null)// 查询日期起
        {
            sbSQL.append(" and to_char( dt_transactiontime,'yyyy-mm-dd' ) >= " + "'"
                    + DataFormat.formatDate(qInfo.getTransactionStartDate(), 1) + "'" + " \n");
        }

        if (qInfo.getTransactionEndDate() != null)// 查询日期止
        {
            sbSQL.append(" and to_char( dt_transactiontime,'yyyy-mm-dd' ) <= " + "'"
                    + DataFormat.formatDate(qInfo.getTransactionEndDate(), 1) + "'" + " \n");
        }

        sbSQL.append(" Order By transactionTime \n");
	        
		return sbSQL.toString();
	}
	
}