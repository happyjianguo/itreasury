/*
 * Created on 2004-5-13
 * @author ygzhao
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.securities.print.bizlogic;

import java.sql.Timestamp;

import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.print.dataentity.PrintBondRepurchaseParam;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

public class PrintBondRepurchaseBean
{
	    protected Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);

		private StringBuffer sbSelect = null;
		private StringBuffer sbFrom = null;
		private StringBuffer sbWhere = null;
	
		private void getSQL(PrintBondRepurchaseParam printParam)
		{
			sbSelect = new StringBuffer();
			sbFrom = new StringBuffer();
			sbWhere = new StringBuffer();
			sbWhere.append(" ");
			sbSelect.append("   *  \n");
			//开始的左括号
			sbFrom.append(" ( \n");
			
		
			//条件一：业务通知单录入日期开始日
			Timestamp noticeInputDateStart = printParam.getNoticeInputDateStart();
			String strNoticeInputDateStart = DataFormat.getDateString(noticeInputDateStart);
			//条件二：业务通知单录入日期结束日
			Timestamp noticeInputDateEnd = printParam.getNoticeInputDateEnd();
		    String strNoticeInputDateEnd = DataFormat.getDateString(noticeInputDateEnd);
		    
			//条件：申购交割单录入日期
			Timestamp deliveryOrderInputDateStart = printParam.getDeliveryOrderInputDateStart();
			String strDeliveryOrderInputDateStart = DataFormat.getDateString(deliveryOrderInputDateStart);
			
			Timestamp deliveryOrderInputDateEnd = printParam.getDeliveryOrderInputDateEnd();
			String strDeliveryOrderInputDateEnd = DataFormat.getDateString(deliveryOrderInputDateEnd);

			//条件3：业务单位
			long clientIds = printParam.getClientIds();
			//条件4：股东帐户
			String[] stockHolderAccountIds = printParam.getStockHolderAccountIds();
			//条件5：交易类型
			String[] transactionTypeIds = printParam.getTransactionTypeIds();
			//条件6：交易对手
			String[] counterpartIds = printParam.getCounterpartIds();
			//条件7：开户营业部ID
			long bankOfDepositIds = printParam.getBankOfDepositIds();
			//条件8：资金账号
			String[] accountIds = printParam.getAccountIds();

				 
			sbFrom.append(" select * from \n");
			sbFrom.append(" (select \n");
			sbFrom.append(" repurchasedeliveryorder.code  as repurchaseCode, \n");//回购交割单编号
			sbFrom.append(" repaydeliveryorder.code   as repayCode, \n");//购回交割单编号			
			sbFrom.append(" repurchasedeliveryorder.clientid  as clientId, \n");//业务单位ID
			sbFrom.append(" repurchasedeliveryorder.accountId as stockHolderAccountId , \n");//股东帐户ID 
			sbFrom.append(" repurchasedeliveryorder.accountid   as  accountId , \n");//资金帐号
			sbFrom.append(" repurchasedeliveryorder.counterpartid  as counterpartId  , \n");//对于银行间业务,指交易对手.			
			sbFrom.append(" repurchasedeliveryorder.deliveryDate as  deliveryDate ,\n");//购回日期
			sbFrom.append(" repurchasedeliveryorder.transactiondate as  transactionDate ,\n");//成交日期
			sbFrom.append(" repurchasedeliveryorder.INPUTDATE as inputdate,\n");//录入日期
			sbFrom.append(" repurchasedeliveryorder.SECURITIESID as securitiesId , \n");//品种：证券名称
			sbFrom.append(" repurchasedeliveryorder.amount as repayAmount , \n");//购回金额
			
			
			sbFrom.append(" case when repaydeliveryorder.transactiontypeid in(2604,2704) \n");
			sbFrom.append(" then repaydeliveryorder.netIncome - repurchasedeliveryorder.netIncome \n ");
			sbFrom.append(" when  repaydeliveryorder.transactiontypeid in(2602,2702)  then \n");
			sbFrom.append(" repurchasedeliveryorder.netIncome - repaydeliveryorder.netIncome end as businessIncome ,\n");
			sbFrom.append(" ");
			
			//sbFrom.append(" repurchasedeliveryorder.netIncome - repaydeliveryorder.netIncome  as businessIncome , \n");//业务收益
			
			sbFrom.append(" repurchasedeliveryorder.amount   as    businessCapital , \n");//业务本金：取自成交金额	
			sbFrom.append(" repurchasedeliveryorder.tax   as     businessExpenditure , \n");//业务费用:取自税费(银行间国债回购中没有)
			sbFrom.append(" repurchasedeliveryorder.TRANSACTIONTYPEID as transactionTypeId , \n");//交易类型
			sbFrom.append(" repaydeliveryorder.MATURITYDATE - repurchasedeliveryorder.MATURITYDATE as occupyDays , \n"); //占用天数			
			sbFrom.append(" (repaydeliveryorder.MATURITYDATE - repurchasedeliveryorder.MATURITYDATE) * repurchasedeliveryorder.amount  as businessCapitalOccupy , \n"); //业务资金占用
			sbFrom.append(" (repaydeliveryorder.MATURITYDATE - repurchasedeliveryorder.MATURITYDATE) * (repurchasedeliveryorder.amount + repurchasedeliveryorder.tax )  as accountCapitalOccupy , \n") ;//会计资金占用
			sbFrom.append(" (repaydeliveryorder.netIncome - repurchasedeliveryorder.netIncome) * 365 * (1 - sec_client.BUSINESSTAXRATE - sec_client.CONSTRUCTIONTAXRATE - sec_client.EDUCATIONFEERATE ) / ((repaydeliveryorder.MATURITYDATE - repurchasedeliveryorder.MATURITYDATE) * (repurchasedeliveryorder.amount + repurchasedeliveryorder.tax)) as singleBusinessIncomeRate  \n");// 单笔业务收益率(除税)
			
						
			sbFrom.append(" from sec_deliveryorder repurchasedeliveryorder,sec_deliveryorder repaydeliveryorder, \n");
			sbFrom.append(" SEC_RepurchaseRegister, sec_client \n");		
			sbFrom.append(" where  SEC_RepurchaseRegister.FIRSTDELIVERYORDERID = repurchasedeliveryorder.id  \n");
			sbFrom.append(" and SEC_RepurchaseRegister.LASTDELIVERYORDERID = repaydeliveryorder.id  \n ");
			sbFrom.append(" and subStr(repurchasedeliveryorder.transactiontypeid,0,2) in (26,27)");
			sbFrom.append(" and subStr(repaydeliveryorder.transactiontypeid,0,2) in (26,27) ");
			sbFrom.append(" and  sec_client.statusID = 3 \n");
			sbFrom.append(" order by subStr(transactionTypeId,3,4),transactionTypeId ) \n");
            //sbFrom.append(" and  sec_client.statusID = -1 ) \n");
			
			sbFrom.append( " where 1=1 \n");
						
			//条件一：成交日期开始日	
			if(noticeInputDateStart!=null)
			{			
				sbFrom.append(" and \n");
				sbFrom.append(" to_char(transactionDate , 'yyyy-mm-dd') >= " + "'" + strNoticeInputDateStart + "'");
			}
			//条件二：成交日期结束日		
			if (noticeInputDateEnd!=null)
			{	
				sbFrom.append(" and \n");
				sbFrom.append(" to_char(transactionDate , 'yyyy-mm-dd') <= " + "'" + strNoticeInputDateEnd + "'");
			}
			
            //申购交割单录入日期校验
			if (!"".equals(strDeliveryOrderInputDateStart))
			{
				sbFrom.append(" and \n");
				sbFrom.append(" to_char(inputdate , 'yyyy-mm-dd') >= " + "'" + strDeliveryOrderInputDateStart + "'");
			}
			 
			if (!"".equals(strDeliveryOrderInputDateEnd))
			{
				sbFrom.append(" and \n");
				sbFrom.append(" to_char(inputdate , 'yyyy-mm-dd') <= " + "'" + strDeliveryOrderInputDateEnd + "'");
			}
			
			//条件3：业务单位			
			if (clientIds != -1) 
			{
				sbFrom.append( " and clientId = " + clientIds +" \n ");
			}			
			//条件4：交易类型		
			if (transactionTypeIds != null && transactionTypeIds.length >0)
			{
				sbFrom.append( " and transactionTypeId in ( ");
				for (int i = 0; i < transactionTypeIds.length -1; i++) {
					sbFrom.append(Long.parseLong(transactionTypeIds[i]) + ",");
				}
				sbFrom.append(
					Long.parseLong(transactionTypeIds[transactionTypeIds.length - 1]) + ") \n");
			}
			
			//条件5： 股东帐户			
			if (stockHolderAccountIds != null && stockHolderAccountIds.length > 0) {
				sbFrom.append(" and stockHolderAccountId in ( ");
				for (int i = 0; i < stockHolderAccountIds.length - 1; i++) {
					sbFrom.append(Long.parseLong(stockHolderAccountIds[i]) + ",");
				}
				sbFrom.append(Long.parseLong(stockHolderAccountIds[stockHolderAccountIds.length - 1]) + ") \n");
			}
			//如果交易对手,开户营业部名称都选择了
			if (counterpartIds != null && counterpartIds.length > 0 && bankOfDepositIds != -1)
			{
				sbFrom.append(" and counterpartId in ( ");
				for (int i = 0; i < counterpartIds.length ; i++) {
						sbFrom.append(Long.parseLong(counterpartIds[i]) + ",");
					}
					sbFrom.append( bankOfDepositIds + ") \n");
			
			}
			//条件6： 如果只选择了 交易对手，没有选择开户营业部			
			if (counterpartIds != null && counterpartIds.length > 0 && bankOfDepositIds == -1) {
				sbFrom.append(" and counterpartId in ( ");
				for (int i = 0; i < counterpartIds.length -1; i++) {
						sbFrom.append(Long.parseLong(counterpartIds[i]) + ",");
					}
					sbFrom.append(
						Long.parseLong(counterpartIds[counterpartIds.length - 1]) + ") \n");
			}
			//条件7： 如果只选择了开户营业部名称，没有选择交易对手
			if ( (counterpartIds == null || counterpartIds.length == 0) && bankOfDepositIds != -1)
			{
				sbFrom.append( " and counterpartId = " +bankOfDepositIds +" \n ");
			}
			//条件8：资金账号
			if (accountIds != null && accountIds.length > 0) {
				sbFrom.append(" and accountId in ( ");
				for (int i = 0; i < accountIds.length - 1; i++) {
					sbFrom.append(Long.parseLong(accountIds[i]) + ",");
				}
				sbFrom.append(
					Long.parseLong(accountIds[accountIds.length - 1]) + ") \n");
			}

		
		
		//sbFrom拼写结束！！！！！！！！！！	
		
		sbFrom.append("   )  \n");
		 
	}
	
		public PageLoader PrintBondRepurchase(PrintBondRepurchaseParam printParam)
		throws SecuritiesException 
		{

			System.out.println("get into PrintBondRepurchaseBean:PrintBondRepurchase()");
			getSQL(printParam);
			System.out.println("after getSQL()");
			

			PageLoader pageLoader =
				(PageLoader) com.iss.system.BaseObjectFactory.getBaseObject(
					"com.iss.system.dao.PageLoader");
			if(pageLoader != null)
				System.out.println("before initPageLoader() pageloader is " + pageLoader.toString());
			else 
				System.out.println("before initPageLoader() : pageloader is null");
			
			System.out.println("AppContext() is " + new AppContext().toString());
			//System.out.println("IAppContext() is " + new IAppContext().);
			System.out.println("in PrintBondRepurchaseBean:PrintBondRepurchase()  sbSelect.toString()= "+sbSelect.toString());
			System.out.println("in PrintBondRepurchaseBean:PrintBondRepurchase()    sbFrom.toString()= "+sbFrom.toString());
			System.out.println("in PrintBondRepurchaseBean:PrintBondRepurchase()   sbWhere.toString()= "+sbWhere.toString());			
			System.out.println("Constant.PageControl.CODE_PAGELINECOUNT = " + Constant.PageControl.CODE_PAGELINECOUNT);
			pageLoader.initPageLoader(
				new AppContext(),
				sbFrom.toString(),
				sbSelect.toString(),
				sbWhere.toString(),
				(int) Constant.PageControl.CODE_PAGELINECOUNT,
				"com.iss.itreasury.securities.print.dataentity.PrintBondRepurchaseInfo",
				null);
			
			if(pageLoader != null)
				System.out.println("after initPageLoader() : pageloader is " + pageLoader.toString());
			else 
				System.out.println("after initPageLoader() : pageloader is null");
				
			pageLoader.setOrderBy("  ");
			
			return pageLoader;
	}
}

