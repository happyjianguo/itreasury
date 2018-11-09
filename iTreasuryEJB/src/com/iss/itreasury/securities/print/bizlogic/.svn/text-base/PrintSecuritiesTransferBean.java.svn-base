/*
 * 创建日期 2004-5-11
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package com.iss.itreasury.securities.print.bizlogic;

import java.sql.Timestamp;

import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.print.dataentity.PrintSecuritiesTransferParam;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
/**
 * @author wangyi
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class PrintSecuritiesTransferBean {

	protected Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);

	private StringBuffer sbSelect = null;
	private StringBuffer sbFrom = null;
	private StringBuffer sbWhere = null;

	/**
	 * 返回证券查询的SQLString语句
	 * 
	 * @param queryParam
	 * @return
	 */
	private void getSQL(PrintSecuritiesTransferParam queryParam) {
		sbSelect = new StringBuffer();

		sbSelect.append("   *  \n");
		sbWhere = new StringBuffer();
		sbWhere.append(" ");
		sbFrom = new StringBuffer();
		
		sbFrom.append("(     \n");
  		
		
		
		//条件一：业务通知单录入日期开始日 公共条件 都要用到的
		Timestamp noticeInputDateStart = queryParam.getNoticeInputDateStart();
		String strNoticeInputDateStart = DataFormat.getDateString(noticeInputDateStart);
		//条件二：业务通知单录入日期结束日 公共条件 都要用到的
		Timestamp noticeInputDateEnd = queryParam.getNoticeInputDateEnd();
		String strNoticeInputDateEnd = DataFormat.getDateString(noticeInputDateEnd);



		//		===========对于第一块的查询======start====		
		//条件3：业务单位
		long clientIds_bank = queryParam.getClientIds_bank();
		//条件4：股东帐户
		String[] stockHolderAccountIds_bank = queryParam.getStockHolderAccountIds_bank();
		//条件5：开户营业部ID
		long bankOfDepositIds_bank = queryParam.getBankOfDepositIds_bank();
		//条件6：资金账号
		String[] accountIds_bank = queryParam.getAccountIds_bank();
		//条件7：证券名称
		String[] securitiesIds_bank = queryParam.getSecuritiesIds_bank();
		

		//只要5个条件中有一个有值，说明传入了条件
		if((clientIds_bank != -1) || (stockHolderAccountIds_bank != null) || (bankOfDepositIds_bank != -1) || 
			(accountIds_bank != null) || (securitiesIds_bank != null))
		{
				log.info("----- 对于第一块的查询 条件成立");
				sbFrom.append("  select   \n");
								
				sbFrom.append("   SEC_DeliveryOrder.CODE as code, -- 交易编号  \n");
				sbFrom.append("   SEC_DeliveryOrder.TransactionDate as transactionDate,-- 成交日期  \n");
				
				sbFrom.append("   SEC_DeliveryOrder.ClientID  as inClientID, -- 转入资料--业务单位id  \n");
				sbFrom.append("   SEC_DeliveryOrder.counterPartID as inCounterPartId, --转入资料-开户营业部id  \n");
				sbFrom.append("   SEC_DeliveryOrder.AccountID as inAccountId, --转入资料-资金帐号id  \n");
				sbFrom.append("   SEC_DeliveryOrder.SecuritiesID as inSecuritiesId, --转入资料-证券id  \n");
				
				sbFrom.append("   SEC_DeliveryOrder.CompanyBankID as outClientID, -- 转出资料--业务单位id \n");
				sbFrom.append("   SEC_DeliveryOrder.CounterpartBankID as outCounterPartId, --转出资料-开户营业部id  \n");
				sbFrom.append("   SEC_DeliveryOrder.CounterpartAccountID as outAccountId,--转出资料-资金帐号id  \n");
				sbFrom.append("   SEC_DeliveryOrder.OppositeSecuritiesID as outSecuritiesId,--转出资料-证券id  \n");
				
				sbFrom.append("   SEC_DeliveryOrder.Quantity as quantity,-- 划转数量  \n");
				sbFrom.append("   SEC_DeliveryOrder.amount as price, -- 划转证券成本 \n");
				sbFrom.append("   SEC_DeliveryOrder.StatusID as statusID,-- 状态 \n");
				sbFrom.append("   SEC_DeliveryOrder.inputUserID as userId --录入人id \n");

			
			
				sbFrom.append("  from \n");
				
				sbFrom.append("   SEC_DeliveryOrder \n");


				sbFrom.append("   where  \n");
				sbFrom.append("   1=1  \n");
				sbFrom.append("   and SEC_DeliveryOrder.TRANSACTIONTYPEID = 9101  \n");
		
				//		===========以下是根据页面传递的参数决定的条件======start====
				//条件一：业务通知单录入日期开始日
				if(!"".equals(strNoticeInputDateStart))
				{
					sbFrom.append(" and \n");
					sbFrom.append(
						" to_char(SEC_DeliveryOrder.TransactionDate , 'yyyy-mm-dd') >= " + "'" + strNoticeInputDateStart + "'");
				}
				//条件二：业务通知单录入日期开始日
				if(!"".equals(strNoticeInputDateEnd))
				{
					sbFrom.append(" and \n");
					sbFrom.append(
						" to_char(SEC_DeliveryOrder.TransactionDate , 'yyyy-mm-dd') <= " + "'" + strNoticeInputDateEnd + "'");
				}
				//条件3：业务单位
				if (clientIds_bank != -1) {
					sbFrom.append(" and \n SEC_DeliveryOrder.ClientID = " + clientIds_bank + " \n");
				}
				//条件4：股东帐户
				if (stockHolderAccountIds_bank != null && stockHolderAccountIds_bank.length > 0) {
					sbFrom.append(" and sec_deliveryOrder.AccountID in (select sec_Account.id \n" +
						"from sec_Account,SEC_STOCKHOLDERACCOUNT where SEC_STOCKHOLDERACCOUNT.ID = sec_Account.STOCKHOLDERACCOUNTID1 \n" +
						" and SEC_STOCKHOLDERACCOUNT.ID in ( ");
					for (int i = 0; i < stockHolderAccountIds_bank.length - 1; i++) {
						sbFrom.append(Long.parseLong(stockHolderAccountIds_bank[i]) + ",");
					}
					sbFrom.append(
						Long.parseLong(stockHolderAccountIds_bank[stockHolderAccountIds_bank.length - 1]) + ")) \n");
				}
				//条件5：开户营业部名称
				if (bankOfDepositIds_bank != -1) {
					sbFrom.append(" and \n sec_deliveryOrder.counterPartId = " + bankOfDepositIds_bank + " \n");
				}
				//条件6：资金账号
				if (accountIds_bank != null && accountIds_bank.length > 0) {
					sbFrom.append(" and sec_deliveryOrder.AccountID in ( ");
					for (int i = 0; i < accountIds_bank.length - 1; i++) {
						sbFrom.append(Long.parseLong(accountIds_bank[i]) + ",");
					}
					sbFrom.append(
						Long.parseLong(accountIds_bank[accountIds_bank.length - 1]) + ") \n");
				}
				//条件7：证券名称
				if (securitiesIds_bank != null && securitiesIds_bank.length > 0) {
					sbFrom.append(" and sec_deliveryOrder.SecuritiesID in ( ");
					for (int i = 0; i < securitiesIds_bank.length - 1; i++) {
						sbFrom.append(Long.parseLong(securitiesIds_bank[i]) + ",");
					}
					sbFrom.append(
						Long.parseLong(securitiesIds_bank[securitiesIds_bank.length - 1])
							+ ") \n");
				}
				//		===========以下是根据页面传递的参数决定的条件======end====

		}
		//		===========对于第一块的查询======end====
		
		





		//		===========对于第二块的查询======start====
		//条件3：业务单位
		long clientIds_exchange = queryParam.getClientIds_exchange();
		//条件4：股东帐户
		String[] stockHolderAccountIds_exchange = queryParam.getStockHolderAccountIds_exchange();
		//条件5：开户营业部ID
		long bankOfDepositIds_exchange = queryParam.getBankOfDepositIds_exchange();
		//条件6：资金账号
		String[] accountIds_exchange = queryParam.getAccountIds_exchange();
		//条件7：证券名称
		String[] securitiesIds_exchange = queryParam.getSecuritiesIds_exchange();

		//只要6个条件中有一个有值，说明传入了条件
		if((clientIds_exchange != -1) || (stockHolderAccountIds_exchange != null) || (bankOfDepositIds_exchange != -1) || 
			(accountIds_exchange != null) || (securitiesIds_exchange != null))
		{
				log.info("----- 对于第二块的查询 条件成立");
				sbFrom.append("     \n");
				//如果第一块成立，则加一个Union
				if((clientIds_bank != -1) || (stockHolderAccountIds_bank != null) || (bankOfDepositIds_bank != -1) || 
					(accountIds_bank != null) || (securitiesIds_bank != null))
				{
					sbFrom.append("     \n");
					sbFrom.append("  union   \n");
				}
				sbFrom.append("     \n");
			sbFrom.append("  select   \n");
								
			sbFrom.append("   SEC_DeliveryOrder.CODE as code, -- 交易编号  \n");
			sbFrom.append("   SEC_DeliveryOrder.TransactionDate as transactionDate,-- 成交日期  \n");
				
			sbFrom.append("   SEC_DeliveryOrder.ClientID  as inClientID, -- 转入资料--业务单位id  \n");
			sbFrom.append("   SEC_DeliveryOrder.counterPartID as inCounterPartId, --转入资料-开户营业部id  \n");
			sbFrom.append("   SEC_DeliveryOrder.AccountID as inAccountId, --转入资料-资金帐号id  \n");
			sbFrom.append("   SEC_DeliveryOrder.SecuritiesID as inSecuritiesId, --转入资料-证券id  \n");
				
			sbFrom.append("   SEC_DeliveryOrder.CompanyBankID as outClientID, -- 转出资料--业务单位id \n");
			sbFrom.append("   SEC_DeliveryOrder.CounterpartBankID as outCounterPartId, --转出资料-开户营业部id  \n");
			sbFrom.append("   SEC_DeliveryOrder.CounterpartAccountID as outAccountId,--转出资料-资金帐号id  \n");
			sbFrom.append("   SEC_DeliveryOrder.OppositeSecuritiesID as outSecuritiesId,--转出资料-证券id  \n");
				
			sbFrom.append("   SEC_DeliveryOrder.Quantity as quantity,-- 划转数量  \n");
			sbFrom.append("   SEC_DeliveryOrder.amount as price, -- 划转证券成本 \n");
			sbFrom.append("   SEC_DeliveryOrder.StatusID as statusID,-- 状态 \n");
			sbFrom.append("   SEC_DeliveryOrder.inputUserID as userId --录入人id \n");	

			
			
			sbFrom.append("  from \n");
				
			sbFrom.append("   SEC_DeliveryOrder \n");


			sbFrom.append("   where  \n");
			sbFrom.append("   1=1  \n");
			sbFrom.append("   and SEC_DeliveryOrder.TRANSACTIONTYPEID = 9101  \n");
			
			
				//		===========以下是根据页面传递的参数决定的条件======start====
				//条件一：业务通知单录入日期开始日
				if(!"".equals(strNoticeInputDateStart))
				{
					sbFrom.append(" and \n");
					sbFrom.append(
						" to_char(SEC_DeliveryOrder.TransactionDate , 'yyyy-mm-dd') >= " + "'" + strNoticeInputDateStart + "'");
				}
				//条件二：业务通知单录入日期开始日
				if(!"".equals(strNoticeInputDateEnd))
				{
					sbFrom.append(" and \n");
					sbFrom.append(
						" to_char(SEC_DeliveryOrder.TransactionDate , 'yyyy-mm-dd') <= " + "'" + strNoticeInputDateEnd + "'");
				}
				//条件3：业务单位
				if (clientIds_exchange != -1) {
					sbFrom.append(" and \n SEC_DeliveryOrder.CompanyBankID = " + clientIds_exchange + " \n");
				}
				//条件4：股东帐户
				if (stockHolderAccountIds_exchange != null && stockHolderAccountIds_exchange.length > 0) {
					sbFrom.append(" and sec_deliveryOrder.CounterpartAccountID in (select sec_Account.id \n" +
						"from sec_Account,SEC_STOCKHOLDERACCOUNT where SEC_STOCKHOLDERACCOUNT.ID = sec_Account.STOCKHOLDERACCOUNTID1 \n" +
						" and SEC_STOCKHOLDERACCOUNT.ID in ( ");
					for (int i = 0; i < stockHolderAccountIds_exchange.length - 1; i++) {
						sbFrom.append(Long.parseLong(stockHolderAccountIds_exchange[i]) + ",");
					}
					sbFrom.append(
						Long.parseLong(stockHolderAccountIds_exchange[stockHolderAccountIds_exchange.length - 1]) + ")) \n");
				}
				//条件5：开户营业部名称
				if (bankOfDepositIds_exchange != -1) {
					sbFrom.append(" and \n sec_deliveryOrder.CounterpartBankID = " + bankOfDepositIds_exchange + " \n");
				}
				//条件6：资金账号
				if (accountIds_exchange != null && accountIds_exchange.length > 0) {
					sbFrom.append(" and sec_deliveryOrder.CounterpartAccountID in ( ");
					for (int i = 0; i < accountIds_exchange.length - 1; i++) {
						sbFrom.append(Long.parseLong(accountIds_exchange[i]) + ",");
					}
					sbFrom.append(
						Long.parseLong(accountIds_exchange[accountIds_exchange.length - 1]) + ") \n");
				}
				//条件7：证券名称
				if (securitiesIds_exchange != null && securitiesIds_exchange.length > 0) {
					sbFrom.append(" and sec_deliveryOrder.OppositeSecuritiesID in ( ");
					for (int i = 0; i < securitiesIds_exchange.length - 1; i++) {
						sbFrom.append(Long.parseLong(securitiesIds_exchange[i]) + ",");
					}
					sbFrom.append(
						Long.parseLong(securitiesIds_exchange[securitiesIds_exchange.length - 1])
							+ ") \n");
				}
				//===========以下是根据页面传递的参数决定的条件======end====

		}
		//		===========对于第二块的查询======end====









		
		/*-----   如果前面两块一个条件都没有输入，则执行下面一段  ----start----  */
		if( (clientIds_exchange == -1) && (stockHolderAccountIds_exchange == null) && (bankOfDepositIds_exchange == -1) && 
		(accountIds_exchange == null) && (securitiesIds_exchange == null)
		&&
		(clientIds_bank == -1) && (stockHolderAccountIds_bank == null) && (bankOfDepositIds_bank == -1) && 
		(accountIds_bank == null) && (securitiesIds_bank == null)
			)
		{
			log.debug("----前面两块一个条件都没有输入!!!!!!!!!!!");
			sbFrom.append("     \n");
			sbFrom.append("  select   \n");
								
			sbFrom.append("   SEC_DeliveryOrder.CODE as code, -- 交易编号  \n");
			sbFrom.append("   SEC_DeliveryOrder.TransactionDate as transactionDate,-- 成交日期  \n");
				
			sbFrom.append("   SEC_DeliveryOrder.ClientID  as inClientID, -- 转入资料--业务单位id  \n");
			sbFrom.append("   SEC_DeliveryOrder.counterPartID as inCounterPartId, --转入资料-开户营业部id  \n");
			sbFrom.append("   SEC_DeliveryOrder.AccountID as inAccountId, --转入资料-资金帐号id  \n");
			sbFrom.append("   SEC_DeliveryOrder.SecuritiesID as inSecuritiesId, --转入资料-证券id  \n");
				
			sbFrom.append("   SEC_DeliveryOrder.CompanyBankID as outClientID, -- 转出资料--业务单位id \n");
			sbFrom.append("   SEC_DeliveryOrder.CounterpartBankID as outCounterPartId, --转出资料-开户营业部id  \n");
			sbFrom.append("   SEC_DeliveryOrder.CounterpartAccountID as outAccountId,--转出资料-资金帐号id  \n");
			sbFrom.append("   SEC_DeliveryOrder.OppositeSecuritiesID as outSecuritiesId,--转出资料-证券id  \n");
				
			sbFrom.append("   SEC_DeliveryOrder.Quantity as quantity,-- 划转数量  \n");
			sbFrom.append("   SEC_DeliveryOrder.amount as price, -- 划转证券成本 \n");
			sbFrom.append("   SEC_DeliveryOrder.StatusID as statusID,-- 状态 \n");
			sbFrom.append("   SEC_DeliveryOrder.inputUserID as userId --录入人id \n");	

			
			
			sbFrom.append("  from \n");
				
			sbFrom.append("   SEC_DeliveryOrder \n");


			sbFrom.append("   where  \n");
			sbFrom.append("   1=1  \n");
			sbFrom.append("   and SEC_DeliveryOrder.TRANSACTIONTYPEID = 9101  \n");

			
			
			//		===========以下是根据页面传递的参数决定的条件======start====
			//条件一：业务通知单录入日期开始日
			if(!"".equals(strNoticeInputDateStart))
			{
				sbFrom.append(" and \n");
				sbFrom.append(
					" to_char(SEC_DeliveryOrder.TransactionDate , 'yyyy-mm-dd') >= " + "'" + strNoticeInputDateStart + "'");
			}
			//条件二：业务通知单录入日期开始日
			if(!"".equals(strNoticeInputDateEnd))
			{
				sbFrom.append(" and \n");
				sbFrom.append(
					" to_char(SEC_DeliveryOrder.TransactionDate , 'yyyy-mm-dd') <= " + "'" + strNoticeInputDateEnd + "'");
			}
		}
		/*-----   如果前面三块一个条件都没有输入，则执行下面一段  ----end----  */		
		
		
		
		
		
		//sbFrom拼写结束！！！！！！！！！！
		
		
		
		
		sbFrom.append("   )  \n");





	}

	/**
	 * 生成PageLoader
	 * 
	 * @param queryParam
	 * @return @throws
	 *         SecuritiesException
	 */
	public PageLoader printSecuritiesTransfer(PrintSecuritiesTransferParam printParam)
		throws SecuritiesException {

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
			"com.iss.itreasury.securities.print.dataentity.PrintSecuritiesTransferInfo",
			null);

		pageLoader.setOrderBy("  ");
		return pageLoader;
	}

}