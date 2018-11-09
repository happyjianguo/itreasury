/*
 * 创建日期 2004-5-11
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package com.iss.itreasury.securities.print.bizlogic;

import java.sql.Timestamp;

import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.print.dataentity.PrintSecuritiesApplyParam;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
/**
 * @author wangyi
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class PrintSecuritiesApplyBean {

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
	private void getSQL(PrintSecuritiesApplyParam queryParam) {
		sbSelect = new StringBuffer();

		sbSelect.append("   *  \n");
		sbWhere = new StringBuffer();
		sbWhere.append(" ");
		sbFrom = new StringBuffer();
		
		sbFrom.append("(     \n");
  		
		
		
		
		//条件0：证券名称 公共条件，都要用到的
		String[] securitiesIds = queryParam.getSecuritiesIds();
		//条件一：业务通知单录入日期开始日 公共条件 都要用到的
		Timestamp noticeInputDateStart = queryParam.getNoticeInputDateStart();
		String strNoticeInputDateStart = DataFormat.getDateString(noticeInputDateStart);
		//条件二：业务通知单录入日期结束日 公共条件 都要用到的
		Timestamp noticeInputDateEnd = queryParam.getNoticeInputDateEnd();
		String strNoticeInputDateEnd = DataFormat.getDateString(noticeInputDateEnd);
		
		//申购交割单录入日期
		Timestamp deliveryOrderInputDateStart = queryParam.getDeliveryOrderInputDateStart();
		String strDeliveryOrderInputDateStart = DataFormat.getDateString(deliveryOrderInputDateStart);
		
		Timestamp deliveryOrderInputDateEnd = queryParam.getDeliveryOrderInputDateEnd();
		String strDeliveryOrderInputDateEnd = DataFormat.getDateString(deliveryOrderInputDateEnd);
		
		//条件3：业务单位
		long clientIds = queryParam.getClientIds();
		//条件4：股东帐户
		String[] stockHolderAccountIds = queryParam.getStockHolderAccountIds();
		//条件5：业务类型
		long businessTypeId = queryParam.getBusinessTypeId();
		//条件6：交易类型
		long transactionTypeIds = queryParam.getTransactionTypeIds();
		//条件7：开户营业部名称
		long bankOfDepositIds = queryParam.getBankOfDepositIds();
		//条件8：资金账号
		String[] accountIds = queryParam.getAccountIds();
		

		sbFrom.append("  select   \n");						
		sbFrom.append("   sec_Securities.ShortName as securitiesName, --证券名称  \n");
		sbFrom.append("   apply_deliveryOrder.code as deliveryOrderCode,  --交割单编号  \n");
		sbFrom.append("   sec_client.name as clientName,  --业务单位名称  \n");
		sbFrom.append("   SEC_STOCKHOLDERACCOUNT.name as stockHolderAccountName ,--股东帐户名称  \n");
		sbFrom.append("   ST_Type.businessTypeName as businessTypeName,  --业务类型  \n");
		sbFrom.append("   ST_Type.businessTypeId as businessTypeId, --业务类型ID  \n");
		sbFrom.append("   ST_Type.transactionTypeName as transactionTypeName, --交易类型 \n");
		sbFrom.append("   apply_deliveryOrder.transactionTypeID as transactionTypeID, --交易类型Id  \n");
		sbFrom.append("   ST_Type.businessAttributeID as businessAttributeID, --业务性质ID(3：开放式基金业务，2:交易所业务，1:银行间业务)  \n");
		sbFrom.append("   sec_Securities.IssueUnderwriter as issueUnderwriter,  -- 主承销商  \n");
		sbFrom.append("   Sec_CounterPArt.name as counterPartName,  --指定开户营业部  \n");
		sbFrom.append("   sec_Account.AccountCode as accountcode, --资金帐号  \n");
		sbFrom.append("   SEC_PurchaseRegister.ApplyDate as applyDate, -- 申购1 - 申购日期 \n");
		sbFrom.append("   SEC_PurchaseRegister.ApplyQuantity as applyQuantity, -- 申购2 - 数量  \n");
		sbFrom.append("   SEC_PurchaseRegister.ApplyPrice as applyPrice, -- 申购3 - 价格  \n");
		sbFrom.append("   SEC_PurchaseRegister.ApplyAmount as applyAmount, -- 申购4 - 金额 \n");
		sbFrom.append("   SEC_PurchaseRegister.MarginRate as marginRate, -- 定金比例 \n");
		//如果中签的交割单的状态不是已复核，则不显示出来
		sbFrom.append("   decode(confirm_deliveryOrder.STATUSID,3,SEC_PURCHASEREGISTER.ConfirmDate,'') as confirmDate, -- 中标1 - 中标日期 \n");
		sbFrom.append("   decode(confirm_deliveryOrder.STATUSID,3,SEC_PURCHASEREGISTER.ConfirmQuantity,'') as confirmQuantity, -- 中标2 - 数量 \n");
		sbFrom.append("   decode(confirm_deliveryOrder.STATUSID,3,SEC_PURCHASEREGISTER.ConfirmPrice,'') as confirmPrice, -- 中标3 - 价格 \n");	
		sbFrom.append("   decode(confirm_deliveryOrder.STATUSID,3,SEC_PURCHASEREGISTER.ConfirmAmount,'') as confirmAmount, -- 中标4 - 金额 \n");
		sbFrom.append("   SEC_PurchaseRegister.DrawbackAmount as drawbackAmount -- 预计手续费返还 \n");		
		
		sbFrom.append("  from \n");
		
		sbFrom.append("   sec_Securities,sec_deliveryOrder apply_deliveryOrder,sec_deliveryOrder confirm_deliveryOrder, \n");
		sbFrom.append("   sec_client,sec_Account,SEC_STOCKHOLDERACCOUNT,Sec_CounterPArt,SEC_PURCHASEREGISTER,sec_NoticeForm, \n");						
		sbFrom.append("   (select a.id as transactionTypeId, \n");
		sbFrom.append("           a.name as transactionTypeName,  \n");
		sbFrom.append("           b.Id as businessTypeId,  \n");
		sbFrom.append("           b.name as businessTypeName, \n");
		sbFrom.append("            c.ID as businessAttributeID \n");
		sbFrom.append(
			"    from  SEC_TRANSACTIONTYPE a ,SEC_BUSINESSTYPE b,SEC_BUSINESSATTRIBUTE c  \n");
		sbFrom.append(
			"    where subStr(a.Id,0,2) = b.id  and b.BUSINESSATTRIBUTEID = c.ID  \n");
		sbFrom.append("     ) ST_Type  \n");				
		
		sbFrom.append("   where 1=1 \n");
		
		//申购交割单对应的通知单必须是已完成的，而且交易类型为（企业债申购）4601和（可转债网下申购）5201
		sbFrom.append("   and \n");
		sbFrom.append("   apply_deliveryOrder.id = SEC_PurchaseRegister.APPLYDELIVERYORDERID \n");
		sbFrom.append("   and \n");
		sbFrom.append("   sec_NoticeForm.DELIVERYORDERID = apply_deliveryOrder.id \n");
		sbFrom.append("   and \n");
		sbFrom.append("   sec_NoticeForm.STATUSID = 4 \n");
		sbFrom.append("   and \n");
		sbFrom.append("   apply_deliveryOrder.TRANSACTIONTYPEID in (4601,5201) \n");
		//由申购交割单的交易类型决定的，中签交割单的交易类型相应的为（企业债一级中签）4604，可转债一级网下中签（5204）
		sbFrom.append("   and \n");
		sbFrom.append("   confirm_deliveryOrder.id = SEC_PurchaseRegister.CONFIRMDELIVERYORDERID \n");
		sbFrom.append("   and \n");
		sbFrom.append("   confirm_deliveryOrder.TRANSACTIONTYPEID in (4604,5204) \n");
		
		sbFrom.append("   and \n");
		sbFrom.append("   SEC_STOCKHOLDERACCOUNT.ID(+) = sec_Account.STOCKHOLDERACCOUNTID1 \n");
		sbFrom.append("   and \n");
		sbFrom.append("   apply_deliveryOrder.clientId = sec_client.ID  \n");
		sbFrom.append("   and \n");
		sbFrom.append("   apply_deliveryOrder.transactionTypeID = ST_Type.transactionTypeId \n");
		sbFrom.append("   and \n");
		sbFrom.append("   apply_deliveryOrder.accountID = sec_Account.ID(+) \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   apply_deliveryOrder.securitiesID = sec_Securities.ID(+)  \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   apply_deliveryOrder.counterPartID = Sec_CounterPArt.id  \n");	
		
		//申购交割单录入日期校验
		if (!"".equals(strDeliveryOrderInputDateStart))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(apply_deliveryOrder.INPUTDATE , 'yyyy-mm-dd') >= " + "'" + strDeliveryOrderInputDateStart + "'");
		}
		 
		if (!"".equals(strDeliveryOrderInputDateEnd))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(apply_deliveryOrder.INPUTDATE , 'yyyy-mm-dd') <= " + "'" + strDeliveryOrderInputDateEnd + "'");
		}
		
		commonCondition(
			securitiesIds,
			strNoticeInputDateStart,
			strNoticeInputDateEnd,
			clientIds,
			stockHolderAccountIds,
			businessTypeId,
			transactionTypeIds,
			bankOfDepositIds,
			accountIds);
			
		sbFrom.append("   union  \n");
				
		sbFrom.append("  select   \n");						
		sbFrom.append("   sec_Securities.ShortName as securitiesName, --证券名称  \n");
		sbFrom.append("   apply_deliveryOrder.code as deliveryOrderCode,  --交割单编号  \n");
		sbFrom.append("   sec_client.name as clientName,  --业务单位名称  \n");
		sbFrom.append("   SEC_STOCKHOLDERACCOUNT.name as stockHolderAccountName ,--股东帐户名称  \n");
		sbFrom.append("   ST_Type.businessTypeName as businessTypeName,  --业务类型  \n");
		sbFrom.append("   ST_Type.businessTypeId as businessTypeId, --业务类型ID  \n");
		sbFrom.append("   ST_Type.transactionTypeName as transactionTypeName, --交易类型 \n");
		sbFrom.append("   apply_deliveryOrder.transactionTypeID as transactionTypeID, --交易类型Id  \n");
		sbFrom.append("   ST_Type.businessAttributeID as businessAttributeID, --业务性质ID(3：开放式基金业务，2:交易所业务，1:银行间业务)  \n");
		sbFrom.append("   sec_Securities.IssueUnderwriter as issueUnderwriter,  -- 主承销商  \n");
		sbFrom.append("   Sec_CounterPArt.name as counterPartName,  --指定开户营业部  \n");
		sbFrom.append("   sec_Account.AccountCode as accountcode, --资金帐号  \n");
		sbFrom.append("   SEC_PurchaseRegister.ApplyDate as applyDate, -- 申购1 - 申购日期 \n");
		sbFrom.append("   SEC_PurchaseRegister.ApplyQuantity as applyQuantity, -- 申购2 - 数量  \n");
		sbFrom.append("   SEC_PurchaseRegister.ApplyPrice as applyPrice, -- 申购3 - 价格  \n");
		sbFrom.append("   SEC_PurchaseRegister.ApplyAmount as applyAmount, -- 申购4 - 金额 \n");
		sbFrom.append("   SEC_PurchaseRegister.MarginRate as marginRate, -- 定金比例 \n");
		//如果中签的交割单的状态不是已复核，则不显示出来
		sbFrom.append("   decode(confirm_deliveryOrder.STATUSID,3,SEC_PURCHASEREGISTER.ConfirmDate,'') as confirmDate, -- 中标1 - 中标日期 \n");
		sbFrom.append("   decode(confirm_deliveryOrder.STATUSID,3,SEC_PURCHASEREGISTER.ConfirmQuantity,'') as confirmQuantity, -- 中标2 - 数量 \n");
		sbFrom.append("   decode(confirm_deliveryOrder.STATUSID,3,SEC_PURCHASEREGISTER.ConfirmPrice,'') as confirmPrice, -- 中标3 - 价格 \n");	
		sbFrom.append("   decode(confirm_deliveryOrder.STATUSID,3,SEC_PURCHASEREGISTER.ConfirmAmount,'') as confirmAmount, -- 中标4 - 金额 \n");
		sbFrom.append("   SEC_PurchaseRegister.DrawbackAmount as drawbackAmount -- 预计手续费返还 \n");		
		
		sbFrom.append("  from \n");
		
		sbFrom.append("   sec_Securities,sec_deliveryOrder apply_deliveryOrder,sec_deliveryOrder confirm_deliveryOrder, \n");
		sbFrom.append("   sec_client,sec_Account,SEC_STOCKHOLDERACCOUNT,Sec_CounterPArt,SEC_PURCHASEREGISTER, \n");						
		sbFrom.append("   (select a.id as transactionTypeId, \n");
		sbFrom.append("           a.name as transactionTypeName,  \n");
		sbFrom.append("           b.Id as businessTypeId,  \n");
		sbFrom.append("           b.name as businessTypeName, \n");
		sbFrom.append("            c.ID as businessAttributeID \n");
		sbFrom.append(
			"    from  SEC_TRANSACTIONTYPE a ,SEC_BUSINESSTYPE b,SEC_BUSINESSATTRIBUTE c  \n");
		sbFrom.append(
			"    where subStr(a.Id,0,2) = b.id  and b.BUSINESSATTRIBUTEID = c.ID  \n");
		sbFrom.append("     ) ST_Type  \n");				
		
		sbFrom.append("   where 1=1 \n");
		
		//申购交割单必须是已复核的，而且交易类型为（可转债网上申购）5101
		sbFrom.append("   and \n");
		sbFrom.append("   apply_deliveryOrder.id = SEC_PurchaseRegister.APPLYDELIVERYORDERID \n");
		sbFrom.append("   and \n");
		sbFrom.append("   apply_deliveryOrder.STATUSID >= 3 \n");
		sbFrom.append("   and \n");
		sbFrom.append("   apply_deliveryOrder.TRANSACTIONTYPEID in (5101) \n");
		//由申购交割单的交易类型决定的，中签交割单的交易类型相应的为 可转债申购中签（5102）
		sbFrom.append("   and \n");
		sbFrom.append("   confirm_deliveryOrder.id = SEC_PurchaseRegister.CONFIRMDELIVERYORDERID \n");
		sbFrom.append("   and \n");
		sbFrom.append("   confirm_deliveryOrder.TRANSACTIONTYPEID in (5102) \n");
		sbFrom.append("   and \n");
		sbFrom.append("   SEC_STOCKHOLDERACCOUNT.ID(+) = sec_Account.STOCKHOLDERACCOUNTID1 \n");
		sbFrom.append("   and \n");
		sbFrom.append("   apply_deliveryOrder.clientId = sec_client.ID  \n");
		sbFrom.append("   and \n");
		sbFrom.append("   apply_deliveryOrder.transactionTypeID = ST_Type.transactionTypeId \n");
		sbFrom.append("   and \n");
		sbFrom.append("   apply_deliveryOrder.accountID = sec_Account.ID(+) \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   apply_deliveryOrder.securitiesID = sec_Securities.ID(+)  \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   apply_deliveryOrder.counterPartID = Sec_CounterPArt.id  \n");		
		
		//申购交割单录入日期校验
		if (!"".equals(strDeliveryOrderInputDateStart))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(apply_deliveryOrder.INPUTDATE , 'yyyy-mm-dd') >= " + "'" + strDeliveryOrderInputDateStart + "'");
		}
		 
		if (!"".equals(strDeliveryOrderInputDateEnd))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(apply_deliveryOrder.INPUTDATE , 'yyyy-mm-dd') <= " + "'" + strDeliveryOrderInputDateEnd + "'");
		}
		
		
		commonCondition(
			securitiesIds,
			strNoticeInputDateStart,
			strNoticeInputDateEnd,
			clientIds,
			stockHolderAccountIds,
			businessTypeId,
			transactionTypeIds,
			bankOfDepositIds,
			accountIds);
					
		
		//sbFrom拼写结束！！！！！！！！！！
		
		
		
		
		sbFrom.append("   \n )  \n");
		
		

	}
	/**
	 * 返回证券查询的SQLString语句
	 * 
	 * @param queryParam
	 * 仅用于国机项目--债券申购报表
	 * @return
	 */
	private void getCNMEFSQL(PrintSecuritiesApplyParam queryParam) {
		sbSelect = new StringBuffer();

		sbSelect.append("   *  \n");
		sbWhere = new StringBuffer();
		sbWhere.append(" ");
		sbFrom = new StringBuffer();
	
		sbFrom.append("(       select * from ( \n");
	
	
	
	
		//条件0：证券名称 公共条件，都要用到的
		String[] securitiesIds = queryParam.getSecuritiesIds();
		//条件一：业务通知单录入日期开始日 公共条件 都要用到的
		Timestamp noticeInputDateStart = queryParam.getNoticeInputDateStart();
		String strNoticeInputDateStart = DataFormat.getDateString(noticeInputDateStart);
		//条件二：业务通知单录入日期结束日 公共条件 都要用到的
		Timestamp noticeInputDateEnd = queryParam.getNoticeInputDateEnd();
		String strNoticeInputDateEnd = DataFormat.getDateString(noticeInputDateEnd);
		//条件3：业务单位
		long clientIds = queryParam.getClientIds();
		//条件4：股东帐户
		String[] stockHolderAccountIds = queryParam.getStockHolderAccountIds();
		//条件5：业务类型
		long businessTypeId = queryParam.getBusinessTypeId();
		//条件6：交易类型
		long transactionTypeIds = queryParam.getTransactionTypeIds();
		//条件7：开户营业部名称
		long bankOfDepositIds = queryParam.getBankOfDepositIds();
		//条件8：资金账号
		String[] accountIds = queryParam.getAccountIds();
	

		sbFrom.append("  select   \n");						
		sbFrom.append("   apply_deliveryOrder.Transactiondate, \n");
		sbFrom.append("   sec_Securities.ShortName as securitiesName, --证券名称  \n");
		sbFrom.append("   apply_deliveryOrder.code as deliveryOrderCode,  --交割单编号  \n");
		sbFrom.append("   sec_client.name as clientName,  --业务单位名称  \n");
		sbFrom.append("   SEC_STOCKHOLDERACCOUNT.name as stockHolderAccountName ,--股东帐户名称  \n");
		sbFrom.append("   ST_Type.businessTypeName as businessTypeName,  --业务类型  \n");
		sbFrom.append("   ST_Type.businessTypeId as businessTypeId, --业务类型ID  \n");
		sbFrom.append("   ST_Type.transactionTypeName as transactionTypeName, --交易类型 \n");
		sbFrom.append("   apply_deliveryOrder.transactionTypeID as transactionTypeID, --交易类型Id  \n");
		sbFrom.append("   ST_Type.businessAttributeID as businessAttributeID, --业务性质ID(3：开放式基金业务，2:交易所业务，1:银行间业务)  \n");
		sbFrom.append("   sec_Securities.IssueUnderwriter as issueUnderwriter,  -- 主承销商  \n");
		sbFrom.append("   Sec_CounterPArt.name as counterPartName,  --指定开户营业部  \n");
		sbFrom.append("   sec_Account.AccountCode as accountcode, --资金帐号  \n");
		
		sbFrom.append("   apply_deliveryOrder.DeliveryDate as applyDate, -- 申购1 - 申购日期 \n");
		sbFrom.append("   apply_deliveryOrder.Quantity as applyQuantity, -- 申购2 - 数量  \n");
		sbFrom.append("   apply_deliveryOrder.Price as applyPrice, -- 申购3 - 价格  \n");
		sbFrom.append("   apply_deliveryOrder.Amount as applyAmount, -- 申购4 - 金额 \n");
		sbFrom.append("   apply_deliveryOrder.netIncome as applyNetIncome, -- 申购5 - 实际收付 \n");
		sbFrom.append("   apply_deliveryOrder.rate as marginRate, -- 定金比例 \n");
		
		//如果中签的交割单的状态不是已复核，则不显示出来
		sbFrom.append("   confirm_deliveryOrder.ConfirmDate as confirmDate, -- 中标1 - 中标日期 \n");
		sbFrom.append("   confirm_deliveryOrder.ConfirmQuantity as confirmQuantity, -- 中标2 - 数量 \n");
		sbFrom.append("   confirm_deliveryOrder.ConfirmPrice as confirmPrice, -- 中标3 - 价格 \n");	
		sbFrom.append("   confirm_deliveryOrder.ConfirmAmount as confirmAmount, -- 中标4 - 金额 \n");
		sbFrom.append("   confirm_deliveryOrder.CommissionCharge   as drawbackAmount,  -- 预计手续费返还 \n");
		
		sbFrom.append("   sell.Amount/sell.Quantity  as sellingPrice,     -- 已卖出1 - 价格 \n");
		sbFrom.append("   sell.Quantity  as sellingQuantity, -- 已卖出2 - 数量 \n");
		sbFrom.append("   sell.Amount    as sellingAmount  -- 已卖出3 - 金额 \n");		
		sbFrom.append("   ,m.closePrice closePrice,n.cost \n");
	
		sbFrom.append("  from \n");
	
		sbFrom.append("   sec_Securities,sec_deliveryOrder apply_deliveryOrder, \n");
		sbFrom.append("   sec_client,sec_Account,SEC_STOCKHOLDERACCOUNT,Sec_CounterPArt,\n");						
		sbFrom.append("   (select a.id as transactionTypeId, \n");
		sbFrom.append("           a.name as transactionTypeName,  \n");
		sbFrom.append("           b.Id as businessTypeId,  \n");
		sbFrom.append("           b.name as businessTypeName, \n");
		sbFrom.append("            c.ID as businessAttributeID \n");
		sbFrom.append(
			"    from  SEC_TRANSACTIONTYPE a ,SEC_BUSINESSTYPE b,SEC_BUSINESSATTRIBUTE c  \n");
		sbFrom.append(
			"    where subStr(a.Id,0,2) = b.id  and b.BUSINESSATTRIBUTEID = c.ID  \n");
		sbFrom.append("     ) ST_Type,  \n");	
		
		//拼写一个申购确认的逻辑表
		sbFrom.append("    ( select ID,STATUSID,DeliveryDate ConfirmDate,securitiesid,accountid,  \n");
		sbFrom.append("         Quantity ConfirmQuantity, Price ConfirmPrice, Amount ConfirmAmount,CommissionCharge  \n");
		sbFrom.append("     from sec_deliveryorder  \n");
		sbFrom.append("     where TRANSACTIONTYPEID in (4604,5204)  and  STATUSID >= 3 \n");
		sbFrom.append("     )confirm_deliveryOrder, \n");
		
        //拼写一个申购卖出的逻辑表
		sbFrom.append("    ( select accountid,securitiesid,  \n");
		sbFrom.append("        sum(Quantity) Quantity,sum(Amount) Amount \n");
		sbFrom.append("     from sec_deliveryorder  \n");
		sbFrom.append("     where TRANSACTIONTYPEID in (4702,5205) and STATUSID >= 3   \n");
		sbFrom.append("     group by accountid,securitiesid )sell \n");
		sbFrom.append("   ,sec_securitiesmarket m ,sec_dailystock n  \n");
	
		sbFrom.append("   where 1=1 \n");
	
		//申购交割单对应的通知单必须是已完成的，而且交易类型为（企业债申购）4601和（可转债网下申购）5201
		//sbFrom.append("   and \n");
		//sbFrom.append("   apply_deliveryOrder.id = SEC_PurchaseRegister.APPLYDELIVERYORDERID \n");
		//sbFrom.append("   and \n");
		//sbFrom.append("   sec_NoticeForm.DELIVERYORDERID = apply_deliveryOrder.id \n");
		//sbFrom.append("   and \n");
		//sbFrom.append("   sec_NoticeForm.STATUSID = 4 \n");
		sbFrom.append("   and \n");
		sbFrom.append("   apply_deliveryOrder.TRANSACTIONTYPEID in (4601,5201) \n");
		//由申购交割单的交易类型决定的，中签交割单的交易类型相应的为（企业债一级中签）4604，可转债一级网下中签（5204）
		//sbFrom.append("   and \n");
		//sbFrom.append("    confirm_deliveryOrder.id(+) = SEC_PurchaseRegister.CONFIRMDELIVERYORDERID \n");
		//sbFrom.append("   and \n");
		//sbFrom.append("   confirm_deliveryOrder.TRANSACTIONTYPEID in (4604,5204) \n");
	
		sbFrom.append("   and \n");
		sbFrom.append("   SEC_STOCKHOLDERACCOUNT.ID(+) = sec_Account.STOCKHOLDERACCOUNTID1 \n");
		sbFrom.append("   and \n");
		sbFrom.append("   apply_deliveryOrder.clientId = sec_client.ID  \n");
		sbFrom.append("   and \n");
		sbFrom.append("   apply_deliveryOrder.transactionTypeID = ST_Type.transactionTypeId \n");
		sbFrom.append("   and \n");
		sbFrom.append("   apply_deliveryOrder.accountID = sec_Account.ID(+) \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   apply_deliveryOrder.securitiesID = sec_Securities.ID(+)  \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   apply_deliveryOrder.counterPartID = Sec_CounterPArt.id  \n");
		//sbFrom.append("   and  SEC_PurchaseRegister.transactiontypeid in(4601,5201)  \n");	
		//sbFrom.append("   and SEC_PurchaseRegister.accountid = sell.accountid(+) \n");	
		//sbFrom.append("   and SEC_PurchaseRegister.securitiesid = sell.securitiesid(+)  \n");	
		//sbFrom.append("   and sell.deliverydate >= confirm_deliveryOrder.confirmDate(+)  \n");	
		//sbFrom.append("   and confirm_deliveryOrder.confirmDate >= apply_deliveryOrder.deliverydate(+)  \n");
		sbFrom.append("   and apply_deliveryOrder.accountid = sell.accountid(+)  \n");
		sbFrom.append("   and apply_deliveryOrder.securitiesid=  sell.securitiesid (+) \n");
		
		sbFrom.append("   and apply_deliveryOrder.accountid = confirm_deliveryOrder.accountid(+) \n");
		sbFrom.append("   and apply_deliveryOrder.securitiesid = confirm_deliveryOrder.securitiesid(+) \n");
	
		commonCondition(
			securitiesIds,
			strNoticeInputDateStart,
			strNoticeInputDateEnd,
			clientIds,
			stockHolderAccountIds,
			businessTypeId,
			transactionTypeIds,
			bankOfDepositIds,
			accountIds);
		
		sbFrom.append("   union  \n");
			
		sbFrom.append("  select   \n");		
		sbFrom.append("   apply_deliveryOrder.Transactiondate, \n");
		sbFrom.append("   sec_Securities.ShortName as securitiesName, --证券名称  \n");
		sbFrom.append("   apply_deliveryOrder.code as deliveryOrderCode,  --交割单编号  \n");
		sbFrom.append("   sec_client.name as clientName,  --业务单位名称  \n");
		sbFrom.append("   SEC_STOCKHOLDERACCOUNT.name as stockHolderAccountName ,--股东帐户名称  \n");
		sbFrom.append("   ST_Type.businessTypeName as businessTypeName,  --业务类型  \n");
		sbFrom.append("   ST_Type.businessTypeId as businessTypeId, --业务类型ID  \n");
		sbFrom.append("   ST_Type.transactionTypeName as transactionTypeName, --交易类型 \n");
		sbFrom.append("   apply_deliveryOrder.transactionTypeID as transactionTypeID, --交易类型Id  \n");
		sbFrom.append("   ST_Type.businessAttributeID as businessAttributeID, --业务性质ID(3：开放式基金业务，2:交易所业务，1:银行间业务)  \n");
		sbFrom.append("   sec_Securities.IssueUnderwriter as issueUnderwriter,  -- 主承销商  \n");
		sbFrom.append("   Sec_CounterPArt.name as counterPartName,  --指定开户营业部  \n");
		sbFrom.append("   sec_Account.AccountCode as accountcode, --资金帐号  \n");
		
		sbFrom.append("   apply_deliveryOrder.DeliveryDate as applyDate, -- 申购1 - 申购日期 \n");
		sbFrom.append("   apply_deliveryOrder.Quantity as applyQuantity, -- 申购2 - 数量  \n");
		sbFrom.append("   apply_deliveryOrder.Price as applyPrice, -- 申购3 - 价格  \n");
		sbFrom.append("   apply_deliveryOrder.Amount as applyAmount, -- 申购4 - 金额 \n");
		sbFrom.append("   apply_deliveryOrder.netIncome as applyNetIncome, -- 申购5 - 实际收付 \n");
		sbFrom.append("   apply_deliveryOrder.rate as marginRate, -- 定金比例 \n");
		
		//如果中签的交割单的状态不是已复核，则不显示出来
		sbFrom.append("   confirm_deliveryOrder.ConfirmDate as confirmDate, -- 中标1 - 中标日期 \n");
		sbFrom.append("   confirm_deliveryOrder.ConfirmQuantity as confirmQuantity, -- 中标2 - 数量 \n");
		sbFrom.append("   confirm_deliveryOrder.ConfirmPrice as confirmPrice, -- 中标3 - 价格 \n");	
		sbFrom.append("   confirm_deliveryOrder.ConfirmAmount as confirmAmount, -- 中标4 - 金额 \n");
		sbFrom.append("   confirm_deliveryOrder.CommissionCharge   as drawbackAmount,  -- 预计手续费返还 \n");
		
		sbFrom.append("   sell.Amount/sell.Quantity     as sellingPrice,    -- 已卖出1 - 价格 \n");
		sbFrom.append("   sell.Quantity  as sellingQuantity, -- 已卖出2 - 数量 \n");
		sbFrom.append("   sell.Amount    as sellingAmount     -- 已卖出3 - 金额 \n");		
		sbFrom.append("   ,m.closePrice closePrice,n.cost \n");
	
		sbFrom.append("  from \n");
	
		sbFrom.append("   sec_Securities,sec_deliveryOrder apply_deliveryOrder, \n");
		sbFrom.append("   sec_client,sec_Account,SEC_STOCKHOLDERACCOUNT,Sec_CounterPArt, \n");						
		sbFrom.append("   (select a.id as transactionTypeId, \n");
		sbFrom.append("           a.name as transactionTypeName,  \n");
		sbFrom.append("           b.Id as businessTypeId,  \n");
		sbFrom.append("           b.name as businessTypeName, \n");
		sbFrom.append("            c.ID as businessAttributeID \n");
		sbFrom.append(
			"    from  SEC_TRANSACTIONTYPE a ,SEC_BUSINESSTYPE b,SEC_BUSINESSATTRIBUTE c  \n");
		sbFrom.append(
			"    where subStr(a.Id,0,2) = b.id  and b.BUSINESSATTRIBUTEID = c.ID  \n");
		sbFrom.append("     ) ST_Type,  \n");	
		
		
        //拼写一个申购确认的逻辑表
		sbFrom.append("    ( select ID,STATUSID,DeliveryDate ConfirmDate,securitiesid,accountid,  \n");
		sbFrom.append("         Quantity ConfirmQuantity, Price ConfirmPrice, Amount ConfirmAmount,CommissionCharge  \n");
		sbFrom.append("     from sec_deliveryorder  \n");
		sbFrom.append("     where TRANSACTIONTYPEID in (5102)  and  STATUSID >= 3 \n");
		sbFrom.append("     )confirm_deliveryOrder, \n");
		
		//拼写一个申购卖出的逻辑表
		sbFrom.append("    ( select accountid,securitiesid,  \n");
		sbFrom.append("        sum(Quantity) Quantity,sum(Amount) Amount \n");
		sbFrom.append("     from sec_deliveryorder  \n");
		sbFrom.append("     where TRANSACTIONTYPEID in (5104) and STATUSID >= 3   \n");
		sbFrom.append("     group by accountid,securitiesid )sell \n");
		sbFrom.append("   ,sec_securitiesmarket m ,sec_dailystock n  \n");
		
	
		sbFrom.append("   where 1=1 \n");
	
		//申购交割单必须是已复核的，而且交易类型为（可转债网上申购）5101
		//sbFrom.append("   and \n");
		//sbFrom.append("   apply_deliveryOrder.id = SEC_PurchaseRegister.APPLYDELIVERYORDERID \n");
		sbFrom.append("   and \n");
		sbFrom.append("   apply_deliveryOrder.STATUSID >= 3 \n");
		sbFrom.append("   and \n");
		sbFrom.append("   apply_deliveryOrder.TRANSACTIONTYPEID in (5101) \n");
		//由申购交割单的交易类型决定的，中签交割单的交易类型相应的为 可转债申购中签（5102）
		
		//sbFrom.append("   and \n");
		//sbFrom.append("   confirm_deliveryOrder.id(+) = SEC_PurchaseRegister.CONFIRMDELIVERYORDERID \n");
		//sbFrom.append("   and \n");
		//sbFrom.append("   confirm_deliveryOrder.TRANSACTIONTYPEID in (5102) \n");
		
		sbFrom.append("   and \n");
		sbFrom.append("   SEC_STOCKHOLDERACCOUNT.ID(+) = sec_Account.STOCKHOLDERACCOUNTID1 \n");
		sbFrom.append("   and \n");
		sbFrom.append("   apply_deliveryOrder.clientId = sec_client.ID  \n");
		sbFrom.append("   and \n");
		sbFrom.append("   apply_deliveryOrder.transactionTypeID = ST_Type.transactionTypeId \n");
		sbFrom.append("   and \n");
		sbFrom.append("   apply_deliveryOrder.accountID = sec_Account.ID(+) \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   apply_deliveryOrder.securitiesID = sec_Securities.ID(+)  \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   apply_deliveryOrder.counterPartID = Sec_CounterPArt.id  \n");		
		sbFrom.append("   and  apply_deliveryOrder.transactiontypeid in(5101)  \n");
		
		//sbFrom.append("   and SEC_PurchaseRegister.accountid = sell.accountid(+) \n");	
		//sbFrom.append("   and SEC_PurchaseRegister.securitiesid = sell.securitiesid(+)  \n");	
		//sbFrom.append("   and sell.deliverydate >= confirm_deliveryOrder.confirmDate(+)  \n");	
		//sbFrom.append("   and confirm_deliveryOrder.confirmDate >= apply_deliveryOrder.deliverydate(+)  \n");	
		sbFrom.append("   and apply_deliveryOrder.accountid = sell.accountid(+)  \n");
		sbFrom.append("    and apply_deliveryOrder.securitiesid=  sell.securitiesid (+) \n");
		
		sbFrom.append("   and apply_deliveryOrder.accountid = confirm_deliveryOrder.accountid(+) \n");
		sbFrom.append("   and apply_deliveryOrder.securitiesid = confirm_deliveryOrder.securitiesid(+) \n");
		
		commonCondition(
			securitiesIds,
			strNoticeInputDateStart,
			strNoticeInputDateEnd,
			clientIds,
			stockHolderAccountIds,
			businessTypeId,
			transactionTypeIds,
			bankOfDepositIds,
			accountIds);
				
	
		//sbFrom拼写结束！！！！！！！！！！
	
	
	
		sbFrom.append("  )  order by  Transactiondate \n");
		sbFrom.append("   \n )  \n");
	
	

	}

	private void commonCondition(
		String[] securitiesIds,
		String strNoticeInputDateStart,
		String strNoticeInputDateEnd,
		long clientIds,
		String[] stockHolderAccountIds,
		long businessTypeId,
		long transactionTypeIds,
		long bankOfDepositIds,
		String[] accountIds) {
		//		===========以下是根据页面传递的参数决定的条件======start====
		//条件一：业务通知单录入日期开始日
		if(!"".equals(strNoticeInputDateStart))
		{
			sbFrom.append(" and \n");
			//if( Env.getProjectName().equalsIgnoreCase(Constant.ProjectName.CNMEF) )
			//{
			//	sbFrom.append(
			//		" to_char(apply_deliveryOrder.deliveryDate, 'yyyy-mm-dd') >= " + "'" + strNoticeInputDateStart + "'" + "\n");
			//}
			//else
			//{
				sbFrom.append(
						" to_char(SEC_PurchaseRegister.ApplyDate , 'yyyy-mm-dd') >= " + "'" + strNoticeInputDateStart + "'" + "\n");
			//}
			
		}
		//条件二：业务通知单录入日期结束日
		if(!"".equals(strNoticeInputDateEnd))
		{
			sbFrom.append(" and \n");
			//if( Env.getProjectName().equalsIgnoreCase(Constant.ProjectName.CNMEF) )
			//{
			//	sbFrom.append(
			//			" to_char(apply_deliveryOrder.deliveryDate, 'yyyy-mm-dd') <= " + "'" + strNoticeInputDateEnd + "'" + "\n");
			//}
			//else
			//{
				sbFrom.append(
						" to_char(SEC_PurchaseRegister.ApplyDate , 'yyyy-mm-dd') <= " + "'" + strNoticeInputDateEnd + "'" + "\n");
			//}
			
		}
		
		///////////////////////////////////////////
		/*if( Env.getProjectName().equalsIgnoreCase(Constant.ProjectName.CNMEF) )
		{
			sbFrom.append("   and apply_deliveryOrder.securitiesid = sec_Securities.id(+) \n");
			sbFrom.append("   and sec_Securities.securitiescode1=m.securitiescode(+) \n");
			sbFrom.append("   and apply_deliveryOrder.clientid = n.clientid(+) \n");
			sbFrom.append("   and apply_deliveryOrder.securitiesid =n.securitiesid(+) \n");
			sbFrom.append("   and apply_deliveryOrder.accountid = n.accountid(+) \n");
			sbFrom.append("   and n.stockdate(+) = to_Date('"+strNoticeInputDateEnd+ "','yyyy-mm-dd') \n");
			sbFrom.append("   and m.closedate(+) = to_Date('"+strNoticeInputDateEnd+ "','yyyy-mm-dd') \n");
		}*/
	    ///////////////////////////////////////////
		//条件3：业务单位
		if (clientIds != -1) {
			sbFrom.append(" and \n SEC_CLIENT.id = " + clientIds + " \n");
		}
		//条件4：股东帐户
		if (stockHolderAccountIds != null && stockHolderAccountIds.length > 0) {
			sbFrom.append(" and SEC_STOCKHOLDERACCOUNT.ID in ( ");
			for (int i = 0; i < stockHolderAccountIds.length - 1; i++) {
				sbFrom.append(Long.parseLong(stockHolderAccountIds[i]) + ",");
			}
			sbFrom.append(
				Long.parseLong(stockHolderAccountIds[stockHolderAccountIds.length - 1]) + ") \n");
		}
		//条件5：业务类型
		if (businessTypeId != -1) {
			sbFrom.append(" and \n");
			sbFrom.append(
				" ST_Type.businessTypeId = " + businessTypeId + " \n");
		}
		//条件6：交易类型
		if (transactionTypeIds != -1) {
			sbFrom.append(" and \n");
			sbFrom.append(
				" apply_deliveryOrder.transactionTypeID = " + transactionTypeIds + " \n");
		}
		//条件7：开户营业部名称
		if (bankOfDepositIds != -1) {
			sbFrom.append(" and \n apply_deliveryOrder.counterPartId = " + bankOfDepositIds + " \n");
		}
		//条件8：资金账号
		if (accountIds != null && accountIds.length > 0) {
			sbFrom.append(" and SEC_ACCOUNT.id in ( ");
			for (int i = 0; i < accountIds.length - 1; i++) {
				sbFrom.append(Long.parseLong(accountIds[i]) + ",");
			}
			sbFrom.append(
				Long.parseLong(accountIds[accountIds.length - 1]) + ") \n");
		}
		//如果条件7和8成立其中一个，就说明客户说要的是 交易所业务 的数据
		if( (bankOfDepositIds != -1) || (accountIds != null && accountIds.length > 0) )
		{
			sbFrom.append("   and  \n");
			sbFrom.append("   ST_Type.businessAttributeID = 2  \n");   //对应着第2种业务
		}
		
		//条件0：证券名称
		if (securitiesIds != null && securitiesIds.length > 0) {
			sbFrom.append(" and apply_deliveryOrder.securitiesID in ( ");
			for (int i = 0; i < securitiesIds.length - 1; i++) {
				sbFrom.append(Long.parseLong(securitiesIds[i]) + ",");
			}
			sbFrom.append(
				Long.parseLong(securitiesIds[securitiesIds.length - 1])
					+ ") \n");
		}
	}


	/**
	 * 生成PageLoader
	 * 
	 * @param queryParam
	 * @return @throws
	 *         SecuritiesException
	 */
	public PageLoader printSecuritiesApply(PrintSecuritiesApplyParam printParam)
		throws SecuritiesException {

		//if( Env.getProjectName().equalsIgnoreCase(Constant.ProjectName.CNMEF) )
		//{
		//	System.out.println("项目名称---国机");
		//	getCNMEFSQL(printParam);
		//}
		//else
		//{
			System.out.println("项目名称---华能");
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
			"com.iss.itreasury.securities.print.dataentity.PrintSecuritiesApplyInfo",
			null);
		pageLoader.setOrderBy("  ");
		return pageLoader;
	}

}