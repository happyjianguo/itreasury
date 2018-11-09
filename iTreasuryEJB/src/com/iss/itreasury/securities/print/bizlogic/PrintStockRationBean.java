/*
 * Created on 2004-5-13
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.securities.print.bizlogic;



import java.sql.Timestamp;

import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.print.dataentity.PrintStockRationParam;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.securities.util.SECConstant;

/**
 * @author jsxie
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PrintStockRationBean 
{
	protected Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);

		private StringBuffer sbSelect = null;
		private StringBuffer sbFrom = null;
		private StringBuffer sbWhere = null;
	
		private void getSQL(PrintStockRationParam printParam)
		{
			System.out.println("printBean:getSQL() in*************************");
			sbSelect = new StringBuffer();
			sbFrom = new StringBuffer();
			sbWhere = new StringBuffer();
			sbWhere.append(" ");
			sbSelect.append("   *  \n");

			//开始的左括号
			sbFrom.append(" ( \n");
	
			sbFrom.append(" select * from \n");
			sbFrom.append(" (select \n");
			sbFrom.append(" sec_deliveryorder.SecuritiesID   securitiesId, \n");//3
			sbFrom.append(" sec_deliveryorder.clientid  clientID, \n");//4
			sbFrom.append(" sec_deliveryorder.transactiontypeid transactiontypeID, \n");//5
			sbFrom.append(" sec_deliveryorder.COUNTERPARTID  counterpartID  , \n");//6
			sbFrom.append(" sec_deliveryorder.accountid     accountID , \n");//7
			sbFrom.append(" sec_deliveryorder.transactiondate  transactionDate ,\n");//8
			sbFrom.append(" sec_DailyStock.Quantity       stockQuantity , \n");//9
			sbFrom.append(" sec_DailyStock.stockdate      stockDate , \n");//9
			sbFrom.append(" sec_deliveryorder.Price       price  , \n");//10
			sbFrom.append(" sec_deliveryorder.quantity    quantity , \n");//11
			sbFrom.append(" sec_deliveryorder.amount        amount , \n");//12
			sbFrom.append(" sec_deliveryorder.tax  tax, \n");//13
			sbFrom.append(" sec_deliveryorder.netIncome       netIncome, \n");//14
			sbFrom.append(" sec_account.stockholderaccountid1  \n");//14
			sbFrom.append(" from  sec_DailyStock  , sec_deliveryorder,sec_noticeform,sec_account \n");//15
			sbFrom.append(" where  sec_DailyStock.clientid = sec_deliveryorder.clientid \n");//16
			sbFrom.append(" and  sec_DailyStock.SecuritiesID = sec_deliveryorder.SecuritiesID \n");//17
			sbFrom.append(" and  sec_DailyStock.stockdate=sec_deliveryorder.transactiondate-1 \n");//18
			sbFrom.append(" and  sec_DailyStock.AccountID = sec_deliveryorder.AccountID ");//19
			sbFrom.append(" and  sec_deliveryorder.AccountID = sec_account.id ");//19
			
			sbFrom.append(" and sec_deliveryorder.id = sec_noticeform.deliveryorderid(+) ");
			sbFrom.append(" and ( (decode(sec_deliveryorder.TRANSACTIONTYPEID,1803,1,0)=1 and sec_deliveryorder.statusID>=3)  \n");
			sbFrom.append(" or (decode(sec_deliveryorder.TRANSACTIONTYPEID,1804,1,0)=1 \n");
			sbFrom.append(" and sec_noticeform.statusid="+SECConstant.NoticeFormStatus.COMPLETED + ") ) )\n");
					
			sbFrom.append( " where 1=1 \n");
//			条件一：业务通知单录入日期开始日

			Timestamp transactionDateStart = printParam.getTransactionDateStart();
				
			if(transactionDateStart!=null)
			{
				/*//取前一天
				Date date=IDate.before(transactionDateStart,1);
				String strStockDateStart =DataFormat.formatDate(date,1);
				System.out.println(strStockDateStart+"**********stockstart*************");
				sbFrom.append(" and \n");
				sbFrom.append(
				" stockDate >= to_Date('"
					+ strStockDateStart
					+ "','yyyy-mm-dd')");*/
				String strTransactionDateStart=DataFormat.getDateString(transactionDateStart);
				sbFrom.append(" and \n");
				sbFrom.append(" transactionDate >= to_Date('"
									+ strTransactionDateStart
									+ "','yyyy-mm-dd')");
			}
			//条件二：业务通知单录入日期结束日
			Timestamp transactionDateEnd = printParam.getTransactionDateEnd();
			if (transactionDateEnd!=null)
			{
              /*//取前一天
			  Date dt=IDate.before(transactionDateEnd,1);
			  String strStockDateEnd =DataFormat.formatDate(dt,1);
			  System.out.println(strStockDateEnd+"*************stockend*************");
			  
			  sbFrom.append(" and \n");
			  sbFrom.append(
			  " stockDate <= to_Date('"
				  + strStockDateEnd
				  + "','yyyy-mm-dd')");*/
			String strTransactionDateEnd=DataFormat.getDateString(transactionDateEnd);
			sbFrom.append(" and \n");
			sbFrom.append(" transactionDate <= to_Date('"
			              + strTransactionDateEnd
						  + "','yyyy-mm-dd')");
			}
		    
			//条件：申购交割单录入日期
			Timestamp deliveryOrderInputDateStart = printParam.getDeliveryOrderInputDateStart();
			String strDeliveryOrderInputDateStart = DataFormat.getDateString(deliveryOrderInputDateStart);
			
			Timestamp deliveryOrderInputDateEnd = printParam.getDeliveryOrderInputDateEnd();
			String strDeliveryOrderInputDateEnd = DataFormat.getDateString(deliveryOrderInputDateEnd);

			 //申购交割单录入日期校验
			if (!"".equals(strDeliveryOrderInputDateStart))
			{
				sbFrom.append(" and \n");
				sbFrom.append(" to_char(sec_deliveryorder.INPUTDATE , 'yyyy-mm-dd') >= " + "'" + strDeliveryOrderInputDateStart + "'");
			}
			 
			if (!"".equals(strDeliveryOrderInputDateEnd))
			{
				sbFrom.append(" and \n");
				sbFrom.append(" to_char(sec_deliveryorder.INPUTDATE , 'yyyy-mm-dd') <= " + "'" + strDeliveryOrderInputDateEnd + "'");
			}
			
//			条件：业务单位
			long clientId = printParam.getClientId();
			if (clientId != -1) 
			{
				sbFrom.append( " and clientID = " +clientId +" \n ");
			}

			//条件：资金账号
			String[] accountIds = printParam.getAccountIds();
			if (accountIds != null && accountIds.length > 0) {
				sbFrom.append(" and accountID in ( ");
				for (int i = 0; i < accountIds.length - 1; i++) {
					sbFrom.append(Long.parseLong(accountIds[i]) + ",");
				}
				sbFrom.append(
					Long.parseLong(accountIds[accountIds.length - 1]) + ") \n");
			}
			//条件：交易类型
			String[] transactionTypeIds = printParam.getTransactionTypeIds();
			
			if (transactionTypeIds != null && transactionTypeIds.length > 0) {
				sbFrom.append(" and transactiontypeID in ( ");
				for (int i = 0; i < transactionTypeIds.length - 1; i++) {
					sbFrom.append(Long.parseLong(transactionTypeIds[i]) + ",");
				}
				sbFrom.append(
					Long.parseLong(transactionTypeIds[transactionTypeIds.length - 1]) + ") \n");
			}else{
                //如果不选，则也是只查询网上配股和网下配股
                //刘惠军 2004-06-24 modified
				sbFrom.append(" and transactiontypeID in ( ");
				sbFrom.append(SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_RATION_ONLINE); 
                sbFrom.append(","+SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_RATION+")");
			}
	    	//条件： 开户营业部
			long counterpartId = printParam.getCounterpartId();
			if(counterpartId != -1)
			{
				sbFrom.append( " and counterpartID = " +counterpartId +" \n ");
			}
			//条件： 股东帐户

			String[] stockHolderAccountIds = printParam.getStockHolderAccountIds();
			if (stockHolderAccountIds != null && stockHolderAccountIds.length > 0) {
				sbFrom.append(" and accountID in ( ");
				for (int i = 0; i < stockHolderAccountIds.length - 1; i++) 
				{
					sbFrom.append( "select id from sec_account where stockholderaccountid1 = ");
					sbFrom.append(Long.parseLong(stockHolderAccountIds[i]) + " union \n");
				}
				sbFrom.append("select id from sec_account where stockholderaccountid1 = \n" );
				sbFrom.append(
					Long.parseLong(stockHolderAccountIds[stockHolderAccountIds.length - 1]) + ") \n");
			}
		
	        //条件：证券ID
			String[] securitiesIdsIds = printParam.getSecuritiesIds();
			if (securitiesIdsIds != null && securitiesIdsIds.length > 0) {
				sbFrom.append(" and securitiesId in ( ");
				for (int i = 0; i < securitiesIdsIds.length - 1; i++) {
					sbFrom.append(Long.parseLong(securitiesIdsIds[i]) + ",");
				}
				sbFrom.append(
					Long.parseLong(securitiesIdsIds[securitiesIdsIds.length - 1]) + ") \n");
			}
            

			//结束的右括号
			sbFrom.append(" ) \n");
			System.out.println("printBean:getSQL() out*************************");
		
	     }//getSQL
	
		public PageLoader PrintStockRation(PrintStockRationParam printParam)
		throws SecuritiesException 
		{
            System.out.println("printBean:printStockRation() in*************************");
			getSQL(printParam);
			//
			PageLoader pageLoader =
				(PageLoader) com.iss.system.BaseObjectFactory.getBaseObject(
					"com.iss.system.dao.PageLoader");
	
			//log.debug("queryNoticeForm ==sbOrderBy :" + sbOrderBy.toString());
			pageLoader.initPageLoader(
				new AppContext(),
				sbFrom.toString(),
				sbSelect.toString(),
				sbWhere.toString(),
				(int) Constant.PageControl.CODE_PAGELINECOUNT,
				"com.iss.itreasury.securities.print.dataentity.PrintStockRationInfo",
				null);
	
			System.out.println("printBean:printStockRation() out*************************");
			
			pageLoader.setOrderBy("  ");
			
			return pageLoader;
	}
	
}
