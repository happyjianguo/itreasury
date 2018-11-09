/*
 * Created on 2004-4-26
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.print.bizlogic;

import java.sql.Timestamp;

import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.print.dataentity.PrintCapitalTransferParam;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.util.Env;

/**
 * @author chluo
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PrintCapitalTransferBean 
{
	protected Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);

	private StringBuffer sbSelect = null;
	private StringBuffer sbFrom = null;
	private StringBuffer sbWhere = null;
	
	private void getSQL(PrintCapitalTransferParam printParam)
	{
		sbSelect = new StringBuffer();
		sbFrom = new StringBuffer();
		sbWhere = new StringBuffer();
		sbWhere.append(" ");
		sbSelect.append("   *  \n");
		//开始的左括号
		sbFrom.append(" ( \n");
		
		sbFrom.append(" select * from \n");
		sbFrom.append(" (select \n");
		sbFrom.append(" sec_noticeform.code   noticeformcode, \n");//3
		sbFrom.append(" sec_deliveryorder.clientid  clientID, \n");//4
		sbFrom.append(" sec_deliveryorder.transactiondate  transactiondate ,\n");//5
		sbFrom.append(" sec_deliveryorder.transactiontypeid transactiontypeid, \n");//6
		sbFrom.append(" sec_deliveryorder.COUNTERPARTID  counterpartid  , \n");//7
		sbFrom.append(" sec_deliveryorder.accountid     accountid , \n");//8
		sbFrom.append(" sec_deliveryorder.netincome        amount , \n");//9
		sbFrom.append(" sec_noticeform.statusid        noticeStatusId  , \n");//10
		sbFrom.append(" sec_noticeform.inputuserid    inputuserid , \n");//11
		sbFrom.append(" sec_deliveryorder.counterpartbankid  counterpartbankid, \n");//12
		sbFrom.append(" sec_deliveryorder.companybankid       companybankid \n");//13
		sbFrom.append(" from  sec_noticeform  , sec_deliveryorder \n");//14
		sbFrom.append(" where    sec_noticeform.deliveryorderid = sec_deliveryorder.id \n");//15
		sbFrom.append(" and subStr(sec_deliveryorder.transactiontypeid,0,2) = 85 )");//16
		
		sbFrom.append( " where 1=1 \n");
//		条件一：业务通知单录入日期开始日

		Timestamp noticeInputDateStart = printParam.getNoticeInputDateStart();
		if(noticeInputDateStart!=null)
		{
			String strNoticeInputDateStart =
			DataFormat.getDateString(noticeInputDateStart);
			sbFrom.append(" and \n");
			sbFrom.append(
			" transactiondate >= to_Date('"
				+ strNoticeInputDateStart
				+ "','yyyy-mm-dd')");
		}
		//条件二：业务通知单录入日期开始日
		Timestamp noticeInputDateEnd = printParam.getNoticeInputDateEnd();
		if (noticeInputDateEnd!=null)
		{
			String strNoticeInputDateEnd =
				DataFormat.getDateString(noticeInputDateEnd);
			sbFrom.append(" and \n");
			sbFrom.append(
				" transactiondate <= to_Date('"
					+ strNoticeInputDateEnd
					+ "','yyyy-mm-dd')");
		}
		
//		条件：业务单位
		long clientId = printParam.getClientId();
		if (clientId != -1) 
		{
			sbFrom.append( " and clientId = " +clientId +" \n ");
		}
		//条件：资金账号
		String[] accountIds = printParam.getAccountIds();
		if (accountIds != null && accountIds.length > 0) {
			sbFrom.append(" and accountid in ( ");
			for (int i = 0; i < accountIds.length - 1; i++) {
				sbFrom.append(Long.parseLong(accountIds[i]) + ",");
			}
			sbFrom.append(
				Long.parseLong(accountIds[accountIds.length - 1]) + ") \n");
		}
		//条件：交易类型
		long transactionTypeId = printParam.getTransactionTypeId() ;
		
		if (transactionTypeId != -1)
		{
			sbFrom.append( " and transactiontypeid = " +transactionTypeId +" \n ");
		}
		//条件： 开户营业部
		long bankOfDepositId = printParam.getBankOfDepositId() ;
		if(bankOfDepositId != -1)
		{
			sbFrom.append( " and counterpartid = " +bankOfDepositId +" \n ");
		}
		//条件： 股东帐户

		String[] stockHolderAccountIds = printParam.getStockHolderAccountIds();
		if (stockHolderAccountIds != null && stockHolderAccountIds.length > 0) {
			sbFrom.append(" and accountid in ( ");
			for (int i = 0; i < stockHolderAccountIds.length - 1; i++) 
			{
				sbFrom.append( "select id from sec_account where stockholderaccountid1 = ");
				sbFrom.append(Long.parseLong(stockHolderAccountIds[i]) + " union \n");
			}
			sbFrom.append("select id from sec_account where stockholderaccountid1 = \n" );
			sbFrom.append(
				Long.parseLong(stockHolderAccountIds[stockHolderAccountIds.length - 1]) + ") \n");
		}
		
	
		
		 
		
		
		//结束的右括号
		sbFrom.append(" ) \n");
		
		
		
	}
	private void getCNMEFSQL(PrintCapitalTransferParam printParam)
	{
		sbSelect = new StringBuffer();
		sbFrom = new StringBuffer();
		sbWhere = new StringBuffer();
		sbWhere.append(" ");
		sbSelect.append("   *  \n");
		
		//开始的左括号
		sbFrom.append(" ( \n");
	
		sbFrom.append(" select * from \n");
		sbFrom.append(" (select \n");
		sbFrom.append(" sec_noticeform.code   noticeformcode, \n");//3
		sbFrom.append(" sec_deliveryorder.clientid  clientID, \n");//4
		sbFrom.append(" sec_deliveryorder.transactiondate  transactiondate ,\n");//5
		sbFrom.append(" sec_deliveryorder.transactiontypeid transactiontypeid, \n");//6
		sbFrom.append(" sec_deliveryorder.securitiesId securitiesId, \n ");
		sbFrom.append(" sec_noticeform.remark remark, \n");
		sbFrom.append(" sec_deliveryorder.COUNTERPARTID  counterpartid  , \n");//7
		sbFrom.append(" sec_deliveryorder.accountid     accountid , \n");//8
		sbFrom.append(" sec_deliveryorder.netincome        amount , \n");//9
		sbFrom.append(" sec_noticeform.statusid        noticeStatusId  , \n");//10
		sbFrom.append(" sec_noticeform.inputuserid    inputuserid , \n");//11
		sbFrom.append(" sec_deliveryorder.counterpartbankid  counterpartbankid, \n");//12
		sbFrom.append(" sec_deliveryorder.companybankid       companybankid \n");//13
		sbFrom.append(" from  sec_noticeform  , sec_deliveryorder \n");//14
		sbFrom.append(" where    sec_noticeform.deliveryorderid = sec_deliveryorder.id \n");//15
		sbFrom.append(" and subStr(sec_deliveryorder.transactiontypeid,0,2) = 85 )");//16
	
		sbFrom.append( " where 1=1 \n");
        //条件一：业务通知单录入日期开始日

		Timestamp noticeInputDateStart = printParam.getNoticeInputDateStart();
		if(noticeInputDateStart!=null)
		{
			String strNoticeInputDateStart =
			DataFormat.getDateString(noticeInputDateStart);
			sbFrom.append(" and \n");
			sbFrom.append(
			" transactiondate >= to_Date('"
				+ strNoticeInputDateStart
				+ "','yyyy-mm-dd')");
		}
		//条件二：业务通知单录入日期开始日
		Timestamp noticeInputDateEnd = printParam.getNoticeInputDateEnd();
		if (noticeInputDateEnd!=null)
		{
			String strNoticeInputDateEnd =
				DataFormat.getDateString(noticeInputDateEnd);
			sbFrom.append(" and \n");
			sbFrom.append(
				" transactiondate <= to_Date('"
					+ strNoticeInputDateEnd
					+ "','yyyy-mm-dd')");
		}
	
        //条件：业务单位
		long clientId = printParam.getClientId();
		if (clientId != -1) 
		{
			sbFrom.append( " and clientId = " +clientId +" \n ");
		}
		//条件：资金账号
		String[] accountIds = printParam.getAccountIds();
		if (accountIds != null && accountIds.length > 0) {
			sbFrom.append(" and accountid in ( ");
			for (int i = 0; i < accountIds.length - 1; i++) {
				sbFrom.append(Long.parseLong(accountIds[i]) + ",");
			}
			sbFrom.append(
				Long.parseLong(accountIds[accountIds.length - 1]) + ") \n");
		}
		//条件：交易类型
		long transactionTypeId = printParam.getTransactionTypeId() ;
	
		if (transactionTypeId != -1)
		{
			sbFrom.append( " and transactiontypeid = " +transactionTypeId +" \n ");
		}
		//条件： 开户营业部
		long bankOfDepositId = printParam.getBankOfDepositId() ;
		if(bankOfDepositId != -1)
		{
			sbFrom.append( " and counterpartid = " +bankOfDepositId +" \n ");
		}
		//条件： 股东帐户

		String[] stockHolderAccountIds = printParam.getStockHolderAccountIds();
		if (stockHolderAccountIds != null && stockHolderAccountIds.length > 0) {
			sbFrom.append(" and accountid in ( ");
			for (int i = 0; i < stockHolderAccountIds.length - 1; i++) 
			{
				sbFrom.append( "select id from sec_account where stockholderaccountid1 = ");
				sbFrom.append(Long.parseLong(stockHolderAccountIds[i]) + " union \n");
			}
			sbFrom.append("select id from sec_account where stockholderaccountid1 = \n" );
			sbFrom.append(
				Long.parseLong(stockHolderAccountIds[stockHolderAccountIds.length - 1]) + ") \n");
		}
		
		//结束的右括号
		sbFrom.append(" ) \n");
	}
	
	public PageLoader PrintCapitalTransfer(PrintCapitalTransferParam printParam)
	throws SecuritiesException 
	{
        //if( Env.getProjectName().equalsIgnoreCase(Constant.ProjectName.CNMEF) )
        //{
		//	System.out.println("项目名称：国机");
        //	getCNMEFSQL(printParam);
        //}
        //else
        //{
			System.out.println("项目名称：--");
			getSQL(printParam);
        //}
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
			"com.iss.itreasury.securities.print.dataentity.PrintCapitalTransferInfo",
			null);
			
		pageLoader.setOrderBy("  ");
		
		return pageLoader;
}
}
