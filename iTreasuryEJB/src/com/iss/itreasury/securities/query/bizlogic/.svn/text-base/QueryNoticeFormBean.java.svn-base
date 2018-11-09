/*
 * Created on 2004-4-13
 * 
 * To change the template for this generated file go to Window - Preferences -
 * Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.query.bizlogic;

import java.sql.Timestamp;

import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.query.dataentity.QueryNoticeFormParam;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Constant;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.util.Env;

/**
 * @author hjliu
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class QueryNoticeFormBean {

	protected Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);

	private StringBuffer sbSelect = null;
	private StringBuffer sbFrom = null;
	private StringBuffer sbWhere = null;
	private StringBuffer sbOrderBy = null;

	public final static int OrderBy_NoticeCode = 1; //业务通知单编号
	public final static int OrderBy_DeliveryOrderCode = 2; //对应交割单编号
	public final static int OrderBy_ClientName = 3; //业务单位名称
	public final static int OrderBy_BusinessTypeName = 4; //业务类型
	public final static int OrderBy_TransactionTypeName = 5; //交易类型
	public final static int OrderBy_NoticeInputDate = 6; //业务通知单录入日期
	public final static int OrderBy_Accountcode = 7; //资金账户
	public final static int OrderBy_NetIncome = 8; //实际收付
	public final static int OrderBy_NoticeStatusId = 9; //业务通知单状态

	/**
	 * 返回证券查询的SQLString语句
	 * 
	 * @param queryParam
	 * @return
	 */
	private void getSQL(QueryNoticeFormParam queryParam)
	{
		sbSelect = new StringBuffer();
		//////////////select//////////////start//////////////////////-
		sbSelect.append("   *  \n");
		sbFrom = new StringBuffer();
		sbFrom.append("(     \n");
		//条件1：业务通知单录入日期开始日 
		Timestamp noticeInputDateStart = queryParam.getNoticeInputDateStart();
		String strNoticeInputDateStart = DataFormat.getDateString(noticeInputDateStart);
		//条件2：业务通知单录入日期结束日
		Timestamp noticeInputDateEnd = queryParam.getNoticeInputDateEnd();
		String strNoticeInputDateEnd = DataFormat.getDateString(noticeInputDateEnd);
		//条件3：业务类型
		long businessTypeId = queryParam.getBusinessTypeId();
		//条件4：交易类型
		String[] transactionTypeIds = queryParam.getTransactionTypeIds();
		//条件5：业务单位
		long clientId = queryParam.getClientId();
		//条件6：股东帐户
		String[] stockHolderAccountIds = queryParam.getStockHolderAccountIds();
		//条件7：交易对手
		String[] counterPartIds = queryParam.getCounterPartIds();
		//条件8：开户营业部名称
		long bankOfDepositId = queryParam.getBankOfDepositId();
		//条件9：资金账号
		String[] accountIds = queryParam.getAccountIds();
		//条件10：基金管理公司名称---查询变更，去掉的条件
		//String[] fundManagerCoIds = queryParam.getFundManagerCoIds();
		//条件11：证券名称 
		String[] securitiesIds = queryParam.getSecuritiesIds();
		//条件12：申请书状态
		long statusId = queryParam.getStatusId();
		//条件13：录入人
		long inputUserId = queryParam.getInputUserId();
		//条件14：业务通知单收付款日期
		Timestamp noticeExecuteDateStart = queryParam.getNoticeExecuteDateStart();
		String strNoticeExecuteDateStart = DataFormat.getDateString(noticeExecuteDateStart);
		
		Timestamp noticeExecuteDateEnd = queryParam.getNoticeExecuteDateEnd();
		String strNoticeExecuteDateEnd = DataFormat.getDateString(noticeExecuteDateEnd);
		//申请单id
		//由申请单的明细页面点击“显示通知单信息”进入通知单查询专用
		long applyFormId = queryParam.getApplyFormId();
		//合同id
		//由合同的明细页面点击“业务通知单信息”进入通知单查询专用
		long contractID = queryParam.getContractID(); 	
		sbFrom.append("  select   \n");
		sbFrom.append("   notice.ID as NoticeId,  --业务通知单 \n");
		sbFrom.append("   delivery.ID as DeliveryOrderID,  --对应交割单 \n");
		sbFrom.append("   delivery.ISRELATEDBYNOTICEFORM as isRelatedByNoticeForm ,--是否虚拟交割单 \n");		
		sbFrom.append("   SEC_ApplyContract.ID as contractID,--对应合同id \n");
		sbFrom.append("   notice.code as NoticeCode,  --业务通知单编号 \n");
		sbFrom.append("   delivery.Code as DeliveryOrderCode,  --对应交割单编号 \n");
		sbFrom.append("   client.name as ClientName,  --业务单位名称 \n");
		sbFrom.append("   SEC_STOCKHOLDERACCOUNT.name as stockHolderAccountName ,--股东帐户名称  \n");
		sbFrom.append("   ST_Type.businessTypeName as BusinessTypeName,  --业务类型 \n");
		sbFrom.append("   ST_Type.businessTypeId, --业务类型ID \n");
		sbFrom.append("   ST_Type.transactionTypeName, --交易类型 \n");
		sbFrom.append("   delivery.transactionTypeID , --交易类型Id \n");
		sbFrom.append("   ST_Type.businessAttributeID, --业务性质ID(3：开放式基金业务，2:交易所业务，1:银行间业务) \n");
		sbFrom.append("   notice.inputDate as NoticeInputDate, --业务通知单录入日期 \n");
		sbFrom.append("   delivery.TransactionDate as TransactionDate, --业务通知单成交日期 \n");
		sbFrom.append("   decode(counterPart.IsBankOfDeposit,1,-1,counterPart.Id) as CounterPartId, -- 交易对手 \n");
		sbFrom.append("   decode(counterPart.IsBankOfDeposit,1,counterPart.Id,'') as BankOfDepositId, -- 开户营业部 \n");
	    sbFrom.append("   account.AccountCode as Accountcode, --资金账户 \n");
		sbFrom.append("   securities.name as SecuritiesName, --证券名称 \n");
		sbFrom.append("   delivery.netIncome as NetIncome, --实际收付 \n");
		sbFrom.append("   notice.StatusID as NoticeStatusId, --业务通知单状态 \n");
		sbFrom.append("   notice.NEXTCHECKUSERID nextCheckUserId,  --审核人，作为参数查出审核状态 \n");
		sbFrom.append("   notice.InputUserID as userId  \n");
		//sbFrom.append("   userInfo.sName as UserName --录入人 \n");
		//---------------------//业务单位ID
		//sbFrom.append(" delivery.clientID as ClientId, \n");
		//---------------------//交易对手ID		
		//sbFrom.append(" delivery.counterPartId as CounterPartId, \n");
		//---------------------//账户ID
		//sbFrom.append(" delivery.accountID as AccountId, \n");
		//---------------------//证券ID
		//sbFrom.append(" delivery.securitiesID as SecuritiesID \n");
		sbFrom.append("  from \n");
		//---------------------//表：业务通知单
		sbFrom.append("   sec_noticeForm notice ,SEC_ApplyContract, \n");
		//---------------------//表：交割单
		sbFrom.append("   sec_deliveryOrder delivery, \n");
		//---------------------//表：业务单位
		sbFrom.append("   sec_client client, SEC_STOCKHOLDERACCOUNT ,\n");
		//---------------------//三表合一：
		sbFrom.append("   (select a.id as transactionTypeId, \n");
		sbFrom.append("           a.name as transactionTypeName,  \n");
		sbFrom.append("           b.Id as businessTypeId,  \n");
		sbFrom.append("           b.name as businessTypeName, \n");
		sbFrom.append("            c.ID as businessAttributeID \n");
		//---------------------//表：交易类型，业务类型，业务性质
		sbFrom.append("    from  sec_transactionType a ,sec_businessType b,sec_businessAttribute c  \n");
		sbFrom.append("    where subStr(a.Id,0,2) = b.id  and b.BUSINESSATTRIBUTEID = c.ID  \n");
		sbFrom.append("     ) ST_Type,  \n");
		//---------------------//表：资金账户
		sbFrom.append("    sec_Account account, \n");
		//---------------------//表：证券信息
		sbFrom.append("    sec_Securities securities, \n");
		//---------------------//表：用户信息
		sbFrom.append("    USERINFO userInfo , \n");
		//---------------------//表：交易对手
		sbFrom.append("    (SELECT * FROM Sec_CounterPArt WHERE IsBank is null or IsBank = -1)counterPart \n");

		sbFrom.append("   where 1=1 \n");
		sbFrom.append("   and \n");
		sbFrom.append("   SEC_ApplyContract.ID(+) = notice.ContractID \n");
		sbFrom.append("   and \n");
		sbFrom.append("   SEC_STOCKHOLDERACCOUNT.ID(+) = account.STOCKHOLDERACCOUNTID1 \n");
		sbFrom.append("   and \n");
		sbFrom.append("   notice.deliveryOrderID = delivery.ID \n");
		sbFrom.append("   and \n");
		sbFrom.append("   delivery.clientId = client.ID(+) \n");
		sbFrom.append("   and \n");
		sbFrom.append("   notice.transactionTypeID = ST_Type.transactionTypeId \n");
		sbFrom.append("   and \n");
		sbFrom.append("   delivery.transactionTypeID = notice.transactionTypeID \n");
		sbFrom.append("   and \n");
		sbFrom.append("   delivery.accountID = account.ID(+) \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   delivery.securitiesID = securities.ID(+)  \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   notice.inputUserID = userInfo.id  \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   --2004-09-06 增加外连接，兑息无交易对手ID  \n");
		sbFrom.append("   delivery.counterPartID = counterPart.id(+)  \n");
		//sbFrom.append("   and  \n");
		//sbFrom.append("   ST_Type.businessAttributeID in (1,2,3)  \n");
		//条件1：业务通知单录入日期开始日
		if (!"".equals(strNoticeInputDateStart))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(notice.INPUTDATE , 'yyyy-mm-dd') >= " + "'" + strNoticeInputDateStart + "'");
		}
		//条件2：业务通知单录入日期开始日
		if (!"".equals(strNoticeInputDateEnd))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(notice.INPUTDATE , 'yyyy-mm-dd') <= " + "'" + strNoticeInputDateEnd + "'");
		}
		
		//条件3：业务类型
		if (businessTypeId > 0)
		{
			sbFrom.append(" and \n");
			sbFrom.append(" ST_Type.businessTypeId = " + businessTypeId + " \n");
		}
		//东方电器  业务通知单查询时需要查询的业务
		sbFrom.append(" and ST_Type.businessTypeId in ( 17,18,21,22,26,31,32,33,36,37,46,52,57,61,62,63,64,85 ) ");
		
		//条件4：交易类型
		if (transactionTypeIds != null && transactionTypeIds.length > 0)
		{
			sbFrom.append(" and notice.transactionTypeID in ( ");
			for (int i = 0; i < transactionTypeIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(transactionTypeIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(transactionTypeIds[transactionTypeIds.length - 1]) + ") \n");
		}
		//条件5：业务单位
		if (clientId != -1)
		{
			sbFrom.append(" and \n client.id = " + clientId + " \n");
		}
		//条件6：股东帐户
		if (stockHolderAccountIds != null && stockHolderAccountIds.length > 0)
		{
			sbFrom.append(" and SEC_STOCKHOLDERACCOUNT.CLIENTID in ( ");
			for (int i = 0; i < stockHolderAccountIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(stockHolderAccountIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(stockHolderAccountIds[stockHolderAccountIds.length - 1]) + ") \n");
		}
		//			条件7.1：只选交易对手
		if (counterPartIds != null && counterPartIds.length > 0 && bankOfDepositId == -1)
		{
			sbFrom.append(" and delivery.counterPartId in ( ");
			for (int i = 0; i < counterPartIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(counterPartIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(counterPartIds[counterPartIds.length - 1]) + ") \n");
		}
		//条件7.2：只选交易对手,开户营业部名称
		if (counterPartIds != null && counterPartIds.length > 0 && bankOfDepositId != -1)
		{
			sbFrom.append(" and delivery.counterPartId in ( ");
			for (int i = 0; i < counterPartIds.length; i++)
			{
				sbFrom.append(Long.parseLong(counterPartIds[i]) + ",");
			}
			sbFrom.append(bankOfDepositId + ") \n");
		}
		//条件8.1：只选开户营业部名称
		if (bankOfDepositId != -1 && (counterPartIds == null || counterPartIds.length == 0))
		{
			sbFrom.append(" and \n delivery.counterPartId = " + bankOfDepositId + " \n");
		}
		
		//条件9：资金账号
		if (accountIds != null && accountIds.length > 0)
		{
			sbFrom.append(" and account.id in ( ");
			for (int i = 0; i < accountIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(accountIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(accountIds[accountIds.length - 1]) + ") \n");
		}
		//条件11：证券名称
		if (securitiesIds != null && securitiesIds.length > 0)
		{
			sbFrom.append(" and SECURITIES.id in ( ");
			for (int i = 0; i < securitiesIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(securitiesIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(securitiesIds[securitiesIds.length - 1]) + ") \n");
		}
		//条件12：业务通知单状态
		if (statusId != -1)
		{
			sbFrom.append(" and \n notice.statusId = " + statusId + " \n");
		}
		//条件13：录入人
		if (inputUserId != -1)
		{
			sbFrom.append(" and \n notice.inputUserId = " + inputUserId + " \n");
		}
		//条件14：业务通知单收付款日期
		if (!"".equals(strNoticeExecuteDateStart))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(notice.ExecuteDate , 'yyyy-mm-dd') >= " + "'" + strNoticeExecuteDateStart + "'");
		}
		if (!"".equals(strNoticeExecuteDateEnd))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(notice.ExecuteDate , 'yyyy-mm-dd') <= " + "'" + strNoticeExecuteDateEnd + "'");
		}
		//申请单id
		//由申请单的明细页面点击“显示通知单信息”进入通知单查询专用
		if(applyFormId != -1)
		{
			sbFrom.append(" and \n");
			sbFrom.append(" notice.DeliveryOrderID in (select SEC_DELIVERYORDER.id from SEC_DELIVERYORDER, ");
			sbFrom.append(" SEC_APPLYFORM where SEC_APPLYFORM.id = SEC_DELIVERYORDER.APPLYFORMID ");
			sbFrom.append(" and SEC_APPLYFORM.id = " + applyFormId + ")");
		}
		//合同id
		//由合同的明细页面点击“业务通知单信息”进入通知单查询专用
		if(contractID != -1)
		{
			sbFrom.append(" and \n");
			sbFrom.append(" SEC_ApplyContract.ID = " + contractID );
		}
		//sbFrom拼写结束！！！！！！！！！！！	
		sbFrom.append("   )  \n");
		//////////////from////////////////end////////////////////////-
		sbWhere = new StringBuffer();
		//////////////where//////////////-end////////////////////////-
		sbWhere.append(" ");
		//////////////where//////////////-end////////////////////////-
		sbOrderBy = new StringBuffer();
		String strDesc = queryParam.getDesc() == 1 ? " desc " : "";
		//log.debug( "////queryParam.getDesc() //////////-" +
		// queryParam.getDesc());
		switch ((int) queryParam.getOrderField())
		{
			case OrderBy_NoticeCode :
				sbOrderBy.append(" \n order by NoticeCode" + strDesc);
				break;
			case OrderBy_DeliveryOrderCode :
				sbOrderBy.append(" \n order by DeliveryOrderCode" + strDesc);
				break;
			case OrderBy_ClientName :
				sbOrderBy.append(" \n order by ClientName" + strDesc);
				break;
			case OrderBy_BusinessTypeName :
				sbOrderBy.append(" \n order by BusinessTypeName" + strDesc);
				break;
			case OrderBy_TransactionTypeName :
				sbOrderBy.append(" \n order by TransactionTypeName" + strDesc);
				break;
			case OrderBy_NoticeInputDate :
				sbOrderBy.append(" \n order by NoticeInputDate" + strDesc);
				break;
			case OrderBy_Accountcode :
				sbOrderBy.append(" \n order by Accountcode" + strDesc);
				break;
			case OrderBy_NetIncome :
				sbOrderBy.append(" \n order by NetIncome" + strDesc);
				break;
			case OrderBy_NoticeStatusId :
				sbOrderBy.append(" \n order by NoticeStatusId" + strDesc);
				break;
		}
		log.info("////////////////////////////////////////-");
	}
	/**
	 * 返回证券查询的SQLString语句
	 * 
	 * @param queryParam
	 * @return
	 * 说明：仅用于国机项目的查询---业务通知单查询
	 */
	private void getCNMEFSQL(QueryNoticeFormParam queryParam)
	{
		System.out.println("国机：业务通知单查询---getCNMEFSQL()");
		sbSelect = new StringBuffer();
		//////////////select//////////////start//////////////////////-
		sbSelect.append("   *  \n");
		sbFrom = new StringBuffer();
		sbFrom.append("(     \n");
		//条件1：业务通知单录入日期开始日 
		Timestamp noticeInputDateStart = queryParam.getNoticeInputDateStart();
		String strNoticeInputDateStart = DataFormat.getDateString(noticeInputDateStart);
		//条件2：业务通知单录入日期结束日
		Timestamp noticeInputDateEnd = queryParam.getNoticeInputDateEnd();
		String strNoticeInputDateEnd = DataFormat.getDateString(noticeInputDateEnd);
		//条件3：业务类型
		long businessTypeId = queryParam.getBusinessTypeId();
		//条件4：交易类型
		String[] transactionTypeIds = queryParam.getTransactionTypeIds();
		//条件5：业务单位
		long clientId = queryParam.getClientId();
		//条件6：股东帐户
		//String[] stockHolderAccountIds = queryParam.getStockHolderAccountIds();
		//条件7：交易对手
		String[] interBankCounterPartIds = queryParam.getInterBankCounterPartIds();
		//条件8：开户营业部名称
		long bankOfDepositId = queryParam.getBankOfDepositId();
		//条件9：资金账号
		String[] accountIds = queryParam.getAccountIds();
		//条件10：基金管理公司名称
		String[] fundManagerCoIds = queryParam.getFundManagerCoIds();
		//条件11：证券名称 
		String[] securitiesIds = queryParam.getSecuritiesIds();
		//条件12：申请书状态
		long statusId = queryParam.getStatusId();
		//条件13：录入人
		long inputUserId = queryParam.getInputUserId();
		//条件14：业务通知单收付款日期起
		Timestamp noticeExecuteDateStart = queryParam.getNoticeExecuteDateStart();
		String strNoticeExecuteDateStart = DataFormat.getDateString(noticeExecuteDateStart);
        //条件15：业务通知单收付款日期始
		Timestamp noticeExecuteDateEnd = queryParam.getNoticeExecuteDateEnd();
		String strNoticeExecuteDateEnd = DataFormat.getDateString(noticeExecuteDateEnd);
		
		sbFrom.append("  select   \n");
		sbFrom.append("   notice.ID as NoticeId,  --业务通知单 \n");
		sbFrom.append("   delivery.ID as DeliveryOrderID,  --对应交割单 \n");
		sbFrom.append("   notice.code as NoticeCode,  --业务通知单编号 \n");
		sbFrom.append("   delivery.Code as DeliveryOrderCode,  --对应交割单编号 \n");
		sbFrom.append("   client.name as ClientName,  --业务单位名称 \n");
		//sbFrom.append("   SEC_STOCKHOLDERACCOUNT.name as stockHolderAccountName ,--股东帐户名称  \n");
		sbFrom.append("   ST_Type.businessTypeName as BusinessTypeName,  --业务类型 \n");
		sbFrom.append("   ST_Type.businessTypeId, --业务类型ID \n");
		sbFrom.append("   ST_Type.transactionTypeName, --交易类型 \n");
		sbFrom.append("   delivery.transactionTypeID , --交易类型Id \n");
		sbFrom.append("    ST_Type.businessAttributeID, --业务性质ID(3：开放式基金业务，2:交易所业务，1:银行间业务) \n");
		sbFrom.append("   notice.inputDate as NoticeInputDate, --业务通知单录入日期 \n");
		sbFrom.append("   delivery.TransactionDate as TransactionDate, --业务通知单成交日期 \n");
		sbFrom.append("   counterPart.name as CounterPartName,  --交易对手名称（根据业务性质Id的不同而不同） \n");
		sbFrom.append("   account.AccountCode as Accountcode, --资金账户 \n");
		sbFrom.append("   securities.name as SecuritiesName, --证券名称 \n");
		sbFrom.append("   delivery.netIncome as NetIncome, --实际收付 \n");
		sbFrom.append("   notice.StatusID as NoticeStatusId, --业务通知单状态 \n");
		sbFrom.append("   notice.NEXTCHECKUSERID nextCheckUserId,  --审核人，作为参数查出审核状态 \n");
		sbFrom.append("   userInfo.Id UserId, --录入人Id \n");
		sbFrom.append("   userInfo.sName as UserName --录入人 \n");
		//---------------------//业务单位ID
		//sbFrom.append(" delivery.clientID as ClientId, \n");
		//---------------------//交易对手ID		
		//sbFrom.append(" delivery.counterPartId as CounterPartId, \n");
		//---------------------//账户ID
		//sbFrom.append(" delivery.accountID as AccountId, \n");
		//---------------------//证券ID
		//sbFrom.append(" delivery.securitiesID as SecuritiesID \n");
		sbFrom.append("  from \n");
		//---------------------//表：业务通知单
		sbFrom.append("   sec_noticeForm notice , \n");
		//---------------------//表：交割单
		sbFrom.append("   sec_deliveryOrder delivery, \n");
		//---------------------//表：业务单位
		sbFrom.append("   sec_client client, SEC_STOCKHOLDERACCOUNT ,\n");
		//---------------------//三表合一：
		sbFrom.append("   (select a.id as transactionTypeId, \n");
		sbFrom.append("           a.name as transactionTypeName,  \n");
		sbFrom.append("           b.Id as businessTypeId,  \n");
		sbFrom.append("           b.name as businessTypeName, \n");
		sbFrom.append("            c.ID as businessAttributeID \n");
		//---------------------//表：交易类型，业务类型，业务性质
		sbFrom.append("    from  sec_transactionType a ,sec_businessType b,sec_businessAttribute c  \n");
		sbFrom.append("    where subStr(a.Id,0,2) = b.id  and b.BUSINESSATTRIBUTEID = c.ID  \n");
		sbFrom.append("     ) ST_Type,  \n");
		//---------------------//表：资金账户
		sbFrom.append("    sec_Account account, \n");
		//---------------------//表：证券信息
		sbFrom.append("    sec_Securities securities, \n");
		//---------------------//表：用户信息
		sbFrom.append("    USERINFO userInfo , \n");
		//---------------------//表：交易对手
		sbFrom.append("    Sec_CounterPArt counterPart \n");

		sbFrom.append("   where 1=1 and \n");
		sbFrom.append("   SEC_STOCKHOLDERACCOUNT.ID(+) = account.STOCKHOLDERACCOUNTID1 \n");
		sbFrom.append("   and \n");
		sbFrom.append("   notice.deliveryOrderID = delivery.ID \n");
		sbFrom.append("   and \n");
		sbFrom.append("   delivery.clientId = client.ID \n");
		sbFrom.append("   and \n");
		sbFrom.append("   notice.transactionTypeID = ST_Type.transactionTypeId \n");
		sbFrom.append("   and \n");
		sbFrom.append("   delivery.transactionTypeID = notice.transactionTypeID \n");
		sbFrom.append("   and \n");
		sbFrom.append("   delivery.accountID = account.ID(+) \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   delivery.securitiesID = securities.ID(+)  \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   notice.inputUserID = userInfo.id  \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   delivery.counterPartID = counterPart.id  \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   ST_Type.businessAttributeID in (1,2,3)  \n");
		//条件1：业务通知单录入日期开始日
		if (!"".equals(strNoticeInputDateStart))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(notice.INPUTDATE , 'yyyy-mm-dd') >= " + "'" + strNoticeInputDateStart + "'");
		}
		//条件2：业务通知单录入日期开始日
		if (!"".equals(strNoticeInputDateEnd))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(notice.INPUTDATE , 'yyyy-mm-dd') <= " + "'" + strNoticeInputDateEnd + "'");
		}
		//条件3：业务类型
		if (businessTypeId > 0)
		{
			sbFrom.append(" and \n");
			sbFrom.append(" ST_Type.businessTypeId = " + businessTypeId + " \n");
		}
		//条件4：交易类型
		if (transactionTypeIds != null && transactionTypeIds.length > 0)
		{
			sbFrom.append(" and notice.transactionTypeID in ( ");
			for (int i = 0; i < transactionTypeIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(transactionTypeIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(transactionTypeIds[transactionTypeIds.length - 1]) + ") \n");
		}
		//条件5：业务单位
		if (clientId != -1)
		{
			sbFrom.append(" and \n client.id = " + clientId + " \n");
		}
		/**
		//条件6：股东帐户
		if (stockHolderAccountIds != null && stockHolderAccountIds.length > 0)
		{
			sbFrom.append(" and SEC_STOCKHOLDERACCOUNT.CLIENTID in ( ");
			for (int i = 0; i < stockHolderAccountIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(stockHolderAccountIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(stockHolderAccountIds[stockHolderAccountIds.length - 1]) + ") \n");
		}
		**/
		// 条件7.1：只选交易对手
		if (interBankCounterPartIds != null && interBankCounterPartIds.length > 0 && bankOfDepositId == -1 && (fundManagerCoIds == null || fundManagerCoIds.length == 0))
		{
			sbFrom.append(" and delivery.counterPartId in ( ");
			for (int i = 0; i < interBankCounterPartIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(interBankCounterPartIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(interBankCounterPartIds[interBankCounterPartIds.length - 1]) + ") \n");
		}
		//条件7.2：只选交易对手,开户营业部名称
		if (interBankCounterPartIds != null && interBankCounterPartIds.length > 0 && bankOfDepositId != -1 && (fundManagerCoIds == null || fundManagerCoIds.length == 0))
		{
			sbFrom.append(" and delivery.counterPartId in ( ");
			for (int i = 0; i < interBankCounterPartIds.length; i++)
			{
				sbFrom.append(Long.parseLong(interBankCounterPartIds[i]) + ",");
			}
			sbFrom.append(bankOfDepositId + ") \n");
		}
		//条件7.3：只选交易对手,基金管理公司名称
		if (interBankCounterPartIds != null && interBankCounterPartIds.length > 0 && bankOfDepositId == -1 && fundManagerCoIds != null && fundManagerCoIds.length > 0)
		{
			sbFrom.append(" and delivery.counterPartId in ( ");
			for (int i = 0; i < interBankCounterPartIds.length; i++)
			{
				sbFrom.append(Long.parseLong(interBankCounterPartIds[i]) + ",");
			}
			for (int i = 0; i < fundManagerCoIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(fundManagerCoIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(fundManagerCoIds[fundManagerCoIds.length - 1]) + ") \n");
		}
		//条件8.1：只选开户营业部名称
		if (bankOfDepositId != -1 && (fundManagerCoIds == null || fundManagerCoIds.length == 0) && (interBankCounterPartIds == null || interBankCounterPartIds.length == 0))
		{
			sbFrom.append(" and \n delivery.counterPartId = " + bankOfDepositId + " \n");
		}
		//条件8.2：只选开户营业部名称,基金管理公司名称
		if (bankOfDepositId != -1 && fundManagerCoIds != null && fundManagerCoIds.length > 0 && (interBankCounterPartIds == null || interBankCounterPartIds.length == 0))
		{
			//sbFrom.append(" and \n SEC_APPLYFORM.counterPartId = " +
			// bankOfDepositId + " \n");
			sbFrom.append(" and delivery.counterPartId in ( ");
			for (int i = 0; i < fundManagerCoIds.length; i++)
			{
				sbFrom.append(Long.parseLong(fundManagerCoIds[i]) + ",");
			}
			sbFrom.append(bankOfDepositId + ") \n");
		}
		//条件10.1：只选基金管理公司名称
		if (fundManagerCoIds != null && fundManagerCoIds.length > 0 && bankOfDepositId == -1 && (interBankCounterPartIds == null || interBankCounterPartIds.length == 0))
		{
			sbFrom.append(" and delivery.counterPartId in ( ");
			for (int i = 0; i < fundManagerCoIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(fundManagerCoIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(fundManagerCoIds[fundManagerCoIds.length - 1]) + ") \n");
		}
		//条件10.2：都选
		if (fundManagerCoIds != null && fundManagerCoIds.length > 0 && bankOfDepositId != -1 && interBankCounterPartIds != null && interBankCounterPartIds.length > 0)
		{
			sbFrom.append(" and delivery.counterPartId in ( ");
			for (int i = 0; i < fundManagerCoIds.length; i++)
			{
				sbFrom.append(Long.parseLong(fundManagerCoIds[i]) + ",");
			}
			for (int i = 0; i < interBankCounterPartIds.length; i++)
			{
				sbFrom.append(Long.parseLong(interBankCounterPartIds[i]) + ",");
			}
			sbFrom.append(bankOfDepositId + ") \n");
		}
		//条件9：资金账号
		if (accountIds != null && accountIds.length > 0)
		{
			sbFrom.append(" and account.id in ( ");
			for (int i = 0; i < accountIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(accountIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(accountIds[accountIds.length - 1]) + ") \n");
		}
		//条件11：证券名称
		if (securitiesIds != null && securitiesIds.length > 0)
		{
			sbFrom.append(" and SEC_SECURITIES.id in ( ");
			for (int i = 0; i < securitiesIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(securitiesIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(securitiesIds[securitiesIds.length - 1]) + ") \n");
		}
		//条件12：业务通知单状态
		if (statusId != -1)
		{
			sbFrom.append(" and \n notice.statusId = " + statusId + " \n");
		}
		//条件13：录入人
		if (inputUserId != -1)
		{
			sbFrom.append(" and \n notice.inputUserId = " + inputUserId + " \n");
		}
		//条件14：业务通知单收付款日期
		if (!"".equals(strNoticeExecuteDateStart))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(notice.ExecuteDate , 'yyyy-mm-dd') >= " + "'" + strNoticeExecuteDateStart + "'");
		}
		if (!"".equals(strNoticeExecuteDateEnd))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(notice.ExecuteDate , 'yyyy-mm-dd') <= " + "'" + strNoticeExecuteDateEnd + "'");
		}
		//sbFrom拼写结束！！！！！！！！！！！	
		sbFrom.append("   )  \n");
		//////////////from////////////////end////////////////////////-
		sbWhere = new StringBuffer();
		//////////////where//////////////-end////////////////////////-
		sbWhere.append(" ");
		//////////////where//////////////-end////////////////////////-
		sbOrderBy = new StringBuffer();
		String strDesc = queryParam.getDesc() == 1 ? " desc " : "";
		//log.debug( "////queryParam.getDesc() //////////-" +
		// queryParam.getDesc());
		switch ((int) queryParam.getOrderField())
		{
			case OrderBy_NoticeCode :
				sbOrderBy.append(" \n order by NoticeCode" + strDesc);
				break;
			case OrderBy_DeliveryOrderCode :
				sbOrderBy.append(" \n order by DeliveryOrderCode" + strDesc);
				break;
			case OrderBy_ClientName :
				sbOrderBy.append(" \n order by ClientName" + strDesc);
				break;
			case OrderBy_BusinessTypeName :
				sbOrderBy.append(" \n order by BusinessTypeName" + strDesc);
				break;
			case OrderBy_TransactionTypeName :
				sbOrderBy.append(" \n order by TransactionTypeName" + strDesc);
				break;
			case OrderBy_NoticeInputDate :
				sbOrderBy.append(" \n order by NoticeInputDate" + strDesc);
				break;
			case OrderBy_Accountcode :
				sbOrderBy.append(" \n order by Accountcode" + strDesc);
				break;
			case OrderBy_NetIncome :
				sbOrderBy.append(" \n order by NetIncome" + strDesc);
				break;
			case OrderBy_NoticeStatusId :
				sbOrderBy.append(" \n order by NoticeStatusId" + strDesc);
				break;
		}
		log.info("////////////////////////////////////////-");
	}

	/**
	 * 生成PageLoader
	 * 
	 * @param queryParam
	 * @return @throws
	 *         SecuritiesException
	 */
	public PageLoader queryNoticeFormInfo(QueryNoticeFormParam queryParam)
		throws SecuritiesException {

		//if( Env.getProjectName().equalsIgnoreCase(Constant.ProjectName.CNMEF) )
		//{
		//	System.out.println("项目名称：国机;模块：业务通知单查询");
		//	getCNMEFSQL(queryParam);
		//}
		//else
		//{
			System.out.println("项目名称：--;模块：业务通知单查询");
			getSQL(queryParam);
		//}
		//
		PageLoader pageLoader =
			(PageLoader) com.iss.system.BaseObjectFactory.getBaseObject(
				"com.iss.system.dao.PageLoader");

		log.debug("queryNoticeForm ==sbOrderBy :" + sbOrderBy.toString());
		pageLoader.initPageLoader(
			new AppContext(),
			sbFrom.toString(),
			sbSelect.toString(),
			sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.securities.query.dataentity.QueryNoticeFormInfo",
			null);

		pageLoader.setOrderBy(" " + sbOrderBy.toString() + " ");
		return pageLoader;
	}

}
