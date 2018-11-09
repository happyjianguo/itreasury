package com.iss.itreasury.securities.print.bizlogic;
import java.sql.Timestamp;

import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.securities.util.NameRef;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.print.dataentity.PrintOpenFundApplyingParam;
/**
 * @author gqfang
 * 开放式基金一览表
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PrintOpenFundApplyingBean
{


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
	private void getSQL(PrintOpenFundApplyingParam queryParam) {
		sbSelect = new StringBuffer();

		sbSelect.append("   *  \n");
		sbWhere = new StringBuffer();
		sbWhere.append(" ");
		sbFrom = new StringBuffer();
		
		sbFrom.append("(     \n");
  		
		
		
		

		//条件1：日期开始日 公共条件
		Timestamp queryDateStart = queryParam.getQueryDateStart();
		String strQueryDateStart = DataFormat.getDateString(queryDateStart);
		//条件2：日期结束日 公共条件
		Timestamp queryDateEnd = queryParam.getQueryDateEnd();
		String strQueryDateEnd = DataFormat.getDateString(queryDateEnd);
		//条件3：业务单位
		long clientId = queryParam.getClientId();
		//条件4：证券名称
		String[] securitiesIds = queryParam.getSecuritiesId();
		//条件5：基金管理公司名称
		String[] fundManagerCoId = queryParam.getFundManagerCoId();
		//条件6：//资金帐号
		String[] accountIds = queryParam.getAccountId();
		//收盘日
		Timestamp closeDate = queryParam.getPreInputDate();
		String strCloseDate = DataFormat.getDateString(closeDate);
		
		
		sbFrom.append("  select   \n");
						
		sbFrom.append("   applyDelivery.clientId as clientId,--业务单位  \n");
		sbFrom.append("   applyDelivery.counterPartId as counterPartId,  --资金管理公司  \n");
		sbFrom.append("   applyDelivery.accountId as accountId,--资金账号  \n");
		sbFrom.append("   applyDelivery.securitiesId as securitiesId, --证券id  \n");
		sbFrom.append("   applyDelivery.transactionTypeId as transactionTypeId, --交易类型id  \n");
		
		sbFrom.append("   applyDelivery.amountForApplying as amountForApplying,--申购-金额  \n");
		sbFrom.append("   applyDelivery.netPriceForApplying as netPriceForApplying,  --申购-净值  \n");
		sbFrom.append("   applyDelivery.quantityForApplying as quantityForApplying,--申购-份额  \n");
		
		sbFrom.append("   confirmDelivery.amountForConfirm as amountForConfirm,--确认-认购金额  \n");
		sbFrom.append("   confirmDelivery.netPriceForConfirm as netPriceForConfirm,  --确认-净值  \n");
		sbFrom.append("   confirmDelivery.quantityForConfirm as quantityForConfirm,--确认-确认份额  \n");
		
		sbFrom.append("   cuttingDelivery.cashForCutting as cashForCutting,--分红-现金红利  \n");
		sbFrom.append("   cuttingDelivery.toQuantityForCutting as toQuantityForCutting,--分红-红利转份额  \n");
		sbFrom.append("   cuttingDelivery.finalQuantityForCutting as finalQuantityForCutting,  --分红-最终份额  \n");
		
		sbFrom.append("   redeemDelivery.netPriceForRedeem as netPriceForRedeem,--赎回-净值  \n");
		sbFrom.append("   redeemDelivery.amountForRedeem as amountForRedeem,  --赎回-金额  \n");
		sbFrom.append("   redeemDelivery.quantityForRedeem as quantityForRedeem, --赎回-份额 \n");
		//sbFrom.append("   sum(totalDetails.finalQuantityForCutting) as finalQuantityForCutting,  --赎回-实际盈亏  \n");
		
		sbFrom.append("   presentQuantity.presentQuantity as presentQuantity, --现份额 \n");
		sbFrom.append("   marketValue.marketValue as marketValue --市值 \n");
	
		sbFrom.append("  from \n");
		
				sbFrom.append("  ( \n");
				 
				sbFrom.append("  select  -- 开放式基金-申购 \n");
				sbFrom.append("    id as id , \n");
				sbFrom.append("    clientId as clientId,--业务单位  \n");
		        sbFrom.append("    counterPartId as counterPartId,  --资金管理公司  \n");
		        sbFrom.append("    accountId as accountId,--资金账号  \n");
		        sbFrom.append("    securitiesId as securitiesId, --证券id  \n");
		        sbFrom.append("    transactionTypeId as transactionTypeId, --交易类型id  \n");
				sbFrom.append("    amount as amountForApplying ,  --金额\n");
				sbFrom.append("    netPrice as netPriceForApplying ,  --净值\n");
				sbFrom.append("    quantity as quantityForApplying, --份额\n");
				sbFrom.append("    TransactionDate as TransactionDate --交易日期\n");
				sbFrom.append("  from Sec_DeliveryOrder \n");
				sbFrom.append("  where transactionTypeId in (" + SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.FUND_SUBSCRIBE + ","
								                               + SECConstant.BusinessType.MUTUAL_FUND_BID.FUND_BID + ")" + "\n");
		        sbFrom.append("  and statusid >= 3 \n");
				sbFrom.append("  ) applyDelivery,\n");
				
				sbFrom.append("  ( \n");
				sbFrom.append("  select  --  开放式基金-确认 \n");
				sbFrom.append("    id as id , \n");
				sbFrom.append("    clientId as clientId,--业务单位  \n");
		        sbFrom.append("    counterPartId as counterPartId,  --资金管理公司  \n");
		        sbFrom.append("    accountId as accountId,--资金账号  \n");
		        sbFrom.append("    securitiesId as securitiesId, --证券id  \n");
				sbFrom.append("    amount as amountForConfirm,  --认购金额\n");
				sbFrom.append("    netPrice as netPriceForConfirm,  --净值\n");
				sbFrom.append("    quantity as quantityForConfirm  --确认份额\n");
				sbFrom.append("  from Sec_DeliveryOrder\n");
				sbFrom.append("  where transactionTypeId in (" + SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.FUND_SUBSCRIBE_CONFIRM + ","
		                                                       + SECConstant.BusinessType.MUTUAL_FUND_BID.FUND_BID_CONFIRM + ")" + "\n");
				sbFrom.append("  )confirmDelivery, \n");
				
				sbFrom.append("  ( \n"); 
				sbFrom.append("  select  --开放式基金-分红\n");
				//sbFrom.append("    Sec_DeliveryOrder.id as id , \n");
				sbFrom.append("    Sec_DeliveryOrder.clientId as clientId,--业务单位  \n");
		        sbFrom.append("    Sec_DeliveryOrder.counterPartId as counterPartId,  --资金管理公司  \n");
		        sbFrom.append("    Sec_DeliveryOrder.accountId as accountId,--资金账号  \n");
		        sbFrom.append("    Sec_DeliveryOrder.securitiesId as securitiesId, --证券id  \n");
				sbFrom.append("    sum(Sec_DeliveryOrder.amount) as cashForCutting,  --现金红利\n");
				sbFrom.append("    sum(Sec_DeliveryOrder.quantity) as toQuantityForCutting,  --红利转份额\n");
				sbFrom.append("    Sec_SecuritiesStock.quantity as finalQuantityForCutting  --最终份额\n");
				sbFrom.append("  from Sec_DeliveryOrder,Sec_SecuritiesStock \n");
				sbFrom.append("  where transactionTypeId in (" + SECConstant.BusinessType.MUTUAL_FUND_MELON_CUTTING.FUND_CASH_MELON_CUTTING + ","
		                                                       + SECConstant.BusinessType.MUTUAL_FUND_MELON_CUTTING.FUND_SHARE_MELON_CUTTING + ")" + "\n");
				sbFrom.append("    And Sec_DeliveryOrder.SecuritiesId = Sec_SecuritiesStock.SecuritiesId  \n");
				sbFrom.append("    And Sec_DeliveryOrder.AccountID = Sec_SecuritiesStock.AccountID  \n");
				sbFrom.append("    And Sec_DeliveryOrder.ClientId = Sec_SecuritiesStock.ClientId   \n");
				sbFrom.append(" group by Sec_DeliveryOrder.clientId,Sec_DeliveryOrder.counterPartId, \n");
				sbFrom.append("    Sec_DeliveryOrder.accountId,Sec_DeliveryOrder.securitiesId  \n");
				sbFrom.append("    ,Sec_SecuritiesStock.quantity \n");
				sbFrom.append("  ) cuttingDelivery, \n");
				
				sbFrom.append("  ( \n");
				sbFrom.append("  select  --开放式基金-赎回\n");
				//sbFrom.append("    id as id , \n");
				sbFrom.append("    Sec_DeliveryOrder.clientId as clientId,--业务单位  \n");
		        sbFrom.append("    Sec_DeliveryOrder.counterPartId as counterPartId,  --资金管理公司  \n");
		        sbFrom.append("    Sec_DeliveryOrder.accountId as accountId,--资金账号  \n");
		        sbFrom.append("    Sec_DeliveryOrder.securitiesId as securitiesId, --证券id  \n");
				sbFrom.append("    sum(amount)/ sum(quantity) as netPriceForRedeem,  --净值\n");
				sbFrom.append("    sum(amount) as amountForRedeem,  --金额\n");
				sbFrom.append("    sum(quantity) as quantityForRedeem  --份额\n");
				sbFrom.append("  from Sec_DeliveryOrder\n");
				sbFrom.append("  where transactionTypeId = " + SECConstant.BusinessType.MUTUAL_FUND_REDEEM.FUND_REDEEM +  "\n");
				sbFrom.append(" group by Sec_DeliveryOrder.clientId,Sec_DeliveryOrder.counterPartId, \n");
				sbFrom.append("    Sec_DeliveryOrder.accountId,Sec_DeliveryOrder.securitiesId  \n");
				sbFrom.append("  ) redeemDelivery, \n");
				
				
				sbFrom.append("  ( \n");	
				sbFrom.append("  select  distinct --现份额\n");
				//sbFrom.append("    Sec_DeliveryOrder.id as id , \n");
				sbFrom.append("    Sec_DeliveryOrder.clientId as clientId,--业务单位  \n");
		        sbFrom.append("    Sec_DeliveryOrder.counterPartId as counterPartId,  --资金管理公司  \n");
		        sbFrom.append("    Sec_DeliveryOrder.accountId as accountId,--资金账号  \n");
		        sbFrom.append("    Sec_DeliveryOrder.securitiesId as securitiesId, --证券id  \n");
				sbFrom.append("    Sec_SecuritiesStock.quantity as presentQuantity  --现份额\n");
				sbFrom.append("  from Sec_DeliveryOrder,Sec_SecuritiesStock \n");
				sbFrom.append("  where Sec_DeliveryOrder.SecuritiesId = Sec_SecuritiesStock.SecuritiesId  \n");
				sbFrom.append("    And Sec_DeliveryOrder.AccountID = Sec_SecuritiesStock.AccountID  \n");
				sbFrom.append("    And Sec_DeliveryOrder.ClientId = Sec_SecuritiesStock.ClientId   \n");
				sbFrom.append("  ) presentQuantity,\n");
				
				sbFrom.append("  ( \n");
				sbFrom.append("  select  distinct--市值\n");
				//sbFrom.append("    Sec_DeliveryOrder.id as id , \n");
				sbFrom.append("    Sec_DeliveryOrder.clientId as clientId,--业务单位  \n");
		        sbFrom.append("    Sec_DeliveryOrder.counterPartId as counterPartId,  --资金管理公司  \n");
		        sbFrom.append("    Sec_DeliveryOrder.accountId as accountId,--资金账号  \n");
		        sbFrom.append("    Sec_DeliveryOrder.securitiesId as securitiesId, --证券id  \n");
				sbFrom.append("    Sec_SecuritiesMarket.netClosePrice as marketValue  --市值\n");
				sbFrom.append("  from Sec_DeliveryOrder,Sec_SecuritiesMarket,Sec_Securities \n");
				sbFrom.append("  where Sec_DeliveryOrder.SecuritiesId = Sec_Securities.Id  \n");
				sbFrom.append("    And Sec_Securities.SecuritiesCode2 = Sec_SecuritiesMarket.securitiesCode  \n");
				
				sbFrom.append("    And to_char(Sec_SecuritiesMarket.CloseDate , 'yyyy-mm-dd') = " + "'" + DataFormat.getDateString(queryParam.getQueryDateEnd()) + "'");
				sbFrom.append("  ) marketValue\n");
				
			
			sbFrom.append(" WHERE  applyDelivery.clientId = confirmDelivery.clientId(+)  \n");
			sbFrom.append("    AND applyDelivery.clientId = cuttingDelivery.clientId(+)  AND applyDelivery.clientId = redeemDelivery.clientId(+)  \n");
			sbFrom.append("    AND applyDelivery.clientId = presentQuantity.clientId(+)  AND applyDelivery.clientId = marketValue.clientId(+)  \n");
			
			sbFrom.append("    AND applyDelivery.counterPartId = confirmDelivery.counterPartId(+)  \n");
			sbFrom.append("    AND applyDelivery.counterPartId = cuttingDelivery.counterPartId(+)  AND applyDelivery.counterPartId = redeemDelivery.counterPartId(+)  \n");
			sbFrom.append("    AND applyDelivery.counterPartId = presentQuantity.counterPartId(+)  AND applyDelivery.counterPartId = marketValue.counterPartId(+)  \n");
			
			sbFrom.append("    AND  applyDelivery.accountId = confirmDelivery.accountId(+)  \n");
			sbFrom.append("    AND applyDelivery.accountId = cuttingDelivery.accountId(+)  AND applyDelivery.accountId = redeemDelivery.accountId(+)  \n");
			sbFrom.append("    AND applyDelivery.accountId = presentQuantity.accountId(+)  AND applyDelivery.accountId = marketValue.accountId(+)  \n");
			
			sbFrom.append("    AND applyDelivery.securitiesId = confirmDelivery.securitiesId(+)  \n");
			sbFrom.append("    AND applyDelivery.securitiesId = cuttingDelivery.securitiesId(+)  AND applyDelivery.securitiesId = redeemDelivery.securitiesId(+)  \n");
			sbFrom.append("    AND applyDelivery.securitiesId = presentQuantity.securitiesId(+)  AND applyDelivery.securitiesId = marketValue.securitiesId(+)  \n");
			
			
        //===========以下是根据页面传递的参数决定的条件==========
		//条件一：交割单录入日期开始日
		if(!"".equals(strQueryDateStart))
		{
			sbFrom.append(" and \n");
			sbFrom.append(
				" to_char(applyDelivery.TransactionDate , 'yyyy-mm-dd') >= " + "'" + strQueryDateStart + "'");
		}
		
		//条件二：交割单录入结束日期
		if(!"".equals(strQueryDateEnd))
		{
			sbFrom.append(" and \n");
			sbFrom.append(
				" to_char(applyDelivery.TransactionDate , 'yyyy-mm-dd') <= " + "'" + strQueryDateEnd + "'");
		}
		
		//条件三：业务单位
		if (clientId != -1) {
			sbFrom.append(" and \n applyDelivery.clientId = " + clientId + " \n");
		} 
		
        //条件四：证券名称
		if (securitiesIds != null && securitiesIds.length > 0) {
			sbFrom.append(" and applyDelivery.SecuritiesID in ( ");
			for (int i = 0; i < securitiesIds.length - 1; i++) {
				sbFrom.append(Long.parseLong(securitiesIds[i]) + ",");
			}
			sbFrom.append(
				Long.parseLong(securitiesIds[securitiesIds.length - 1])
					+ ") \n");
		}
		//条件五：资金管理公司名称
		if (fundManagerCoId != null && fundManagerCoId.length > 0) {
			sbFrom.append(" and applyDelivery.counterpartID in ( ");
			for (int i = 0; i < fundManagerCoId.length - 1; i++) {
				sbFrom.append(Long.parseLong(fundManagerCoId[i]) + ",");
			}
			sbFrom.append(
				Long.parseLong(fundManagerCoId[fundManagerCoId.length - 1])
					+ ") \n");
		}
        //条件6：资金账号
		if (accountIds != null && accountIds.length > 0) {
			sbFrom.append(" and applyDelivery.AccountID in ( ");
			for (int i = 0; i < accountIds.length - 1; i++) {
				sbFrom.append(Long.parseLong(accountIds[i]) + ",");
			}
			sbFrom.append(
				Long.parseLong(accountIds[accountIds.length - 1]) + ") \n");
		}

		sbFrom.append("     order by applyDelivery.TransactionDate , clientId, counterPartId, accountId ,securitiesId ,transactionTypeId  \n");
		sbFrom.append("   )  \n");
		
		
		//sbFrom拼写结束！！！！！！！！！！
	
		
		

	}

	

	/**
	 * 生成PageLoader
	 * 
	 * @param queryParam
	 * @return @throws
	 *         SecuritiesException
	 */
	public PageLoader printOpenFund(PrintOpenFundApplyingParam printParam) throws SecuritiesException {

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
			"com.iss.itreasury.securities.print.dataentity.PrintOpenFundApplyingInfo",
			null);
		pageLoader.setOrderBy("  ");
		return pageLoader;
	}


}