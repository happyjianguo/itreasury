/*
 * Created on 2004-4-13
 * 
 * To change the template for this generated file go to Window - Preferences -
 * Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.query.bizlogic;
import java.sql.Timestamp;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.query.dataentity.QueryDeliveryorderParam;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
/**
 * @author chluo
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class QueryDeliveryorderBean
{
	protected Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);
	private StringBuffer sbSelect = null;
	private StringBuffer sbFrom = null;
	private StringBuffer sbWhere = null;
	private StringBuffer sbOrderBy = null;
	public final static int OrderBy_DeliveryOrderCode = 1; //对应交割单编号
	public final static int OrderBy_ClientName = 2; //业务单位名称
	public final static int OrderBy_BusinessTypeName = 3; //业务类型
	public final static int OrderBy_TransactionTypeName = 4; //交易类型
	public final static int OrderBy_NoticeInputDate = 5; //业务通知单录入日期
	public final static int OrderBy_Accountcode = 6; //资金账户
	public final static int OrderBy_NetIncome = 7; //实际收付
	public final static int OrderBy_NoticeStatusId = 8; //业务通知单状态
	/**
	 * 返回证券查询的SQLString语句
	 * 
	 * @param queryParam
	 * @return
	 */
	private void getSQL(QueryDeliveryorderParam queryParam)
	{
		sbSelect = new StringBuffer();
		sbSelect.append("   *  \n");
		sbFrom = new StringBuffer();
		sbFrom.append("(     \n");
		//条件1：交割单录入日期开始日 
		Timestamp noticeInputDateStart = queryParam.getNoticeInputDateStart();
		String strNoticeInputDateStart = DataFormat.getDateString(noticeInputDateStart);
		//条件2：交割单录入日期结束日 
		Timestamp noticeInputDateEnd = queryParam.getNoticeInputDateEnd();
		String strNoticeInputDateEnd = DataFormat.getDateString(noticeInputDateEnd);
		
        //交割单成交日期开始日 
		Timestamp transactionDateStart = queryParam.getTransactionDateStart();
		String strTransactionDateStart = DataFormat.getDateString(transactionDateStart);
		//交割单成交日期结束日
		Timestamp transactionDateEnd = queryParam.getTransactionDateEnd();
		String strTransactionDateEnd = DataFormat.getDateString(transactionDateEnd);
		
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
		//条件12：交割单状态
		long statusId = queryParam.getStatusId();
		//条件13：录入人
		long inputUserId = queryParam.getInputUserId();
		//条件14：是否违规
		long isViolation = queryParam.getIsViolation();
		//对应的申请单id
		//由申请单的明细点击按钮进入交割单查询专用
		long applyFormId = queryParam.getApplyFormId();
		sbFrom.append(" select \n");
		sbFrom.append(" sec_deliveryorder.ID as deliveryId, \n");
		sbFrom.append(" sec_deliveryorder.code as deliveryCode,\n");
		sbFrom.append(" SEC_STOCKHOLDERACCOUNT.name as stockHolderAccountName ,--股东帐户名称 \n");
		sbFrom.append(" SEC_APPLYFORM.CODE applyFormCode,   \n");
		sbFrom.append(" SEC_CLIENT.name clientName ,  \n");
		sbFrom.append(" SEC_BUSINESSTYPE.ID businessTypeId , \n");
		sbFrom.append(" SEC_BUSINESSTYPE.name businessTypeName ,  \n");
		sbFrom.append(" SEC_TRANSACTIONTYPE.name transactionTypeName  , \n");
		sbFrom.append(" TransactionDate transactionDate,  \n");
		sbFrom.append(" decode(SEC_COUNTERPART.IsBankOfDeposit,1,-1,SEC_COUNTERPART.Id) as counterPartId , -- 交易对手 \n");
		sbFrom.append(" decode(SEC_COUNTERPART.IsBankOfDeposit,1,SEC_COUNTERPART.Id,'') as bankOfDepositId , -- 开户营业部 \n");
		sbFrom.append(" SEC_ACCOUNT.code accountID, \n");
		sbFrom.append(" sec_deliveryorder.NetIncome netIncome,   \n");
		sbFrom.append(" sec_deliveryorder.StatusID statusID, \n   ");
		sbFrom.append(" sec_deliveryorder.InputUserID as userId ,  \n");
		//sbFrom.append("    userinfo.sname InputUserName ,  \n ");
		sbFrom.append(" SEC_DELIVERYORDER.ISVIOLATION  as isViolation , \n");
		sbFrom.append(" SEC_BUSINESSATTRIBUTE.id  businessAttribute  \n");
		
		sbFrom.append(" from  \n");
		
		//排除交易对手是开户银行的可能性
		sbFrom.append(" SEC_CLIENT  ,  SEC_TRANSACTIONTYPE , (SELECT * FROM SEC_COUNTERPART WHERE IsBank is null or IsBank = -1)SEC_COUNTERPART  , sec_deliveryorder ,SEC_BUSINESSTYPE , \n");
		sbFrom.append(" SEC_BUSINESSATTRIBUTE , SEC_ACCOUNT ,SEC_APPLYFORM ,SEC_STOCKHOLDERACCOUNT,SEC_SECURITIES \n");
				
		sbFrom.append(" where  1=1 \n ");
		/*lhj modified 2004-07-01 不查询出虚拟交割单的内容*/
		sbFrom.append(" and  \n");
		sbFrom.append(" sec_deliveryorder.ISRELATEDBYNOTICEFORM <> 1 \n");
		sbFrom.append(" and  \n");
		sbFrom.append(" SEC_STOCKHOLDERACCOUNT.ID(+) = SEC_ACCOUNT.STOCKHOLDERACCOUNTID1 \n");
		sbFrom.append(" and \n");
		sbFrom.append(" SEC_CLIENT.id(+) = sec_deliveryorder.ClientID   \n ");
		sbFrom.append(" and  \n");
		sbFrom.append(" SEC_TRANSACTIONTYPE.id = sec_deliveryorder.TransactionTypeID  \n");
		sbFrom.append(" and  \n");
		sbFrom.append(" SEC_COUNTERPART.id(+) = sec_deliveryorder.CounterpartID  \n");
		sbFrom.append(" and  \n");
		sbFrom.append(" SEC_BUSINESSTYPE.id = subStr(SEC_TRANSACTIONTYPE.Id,0,2) \n");
//		sbFrom.append(" and userinfo.id(+) =   sec_deliveryorder.InputUserID \n  ");
		sbFrom.append(" and  \n");
		sbFrom.append(" SEC_BUSINESSATTRIBUTE.id =  SEC_BUSINESSTYPE.BUSINESSATTRIBUTEID  \n");
		sbFrom.append(" and  \n");
		sbFrom.append(" SEC_ACCOUNT.id(+) = sec_deliveryorder.accountID  \n");
		sbFrom.append(" and  \n");
		sbFrom.append(" sec_deliveryorder.APPLYFORMID = SEC_APPLYFORM.ID(+)  \n");
		sbFrom.append(" and  \n");
		sbFrom.append(" sec_deliveryorder.SECURITIESID = SEC_SECURITIES.ID (+)  \n");
		sbFrom.append(" and  \n");
		sbFrom.append(" SEC_BUSINESSATTRIBUTE.id in (1,2,3,4)  \n");
		//			==========以下是根据页面传递的参数决定的条件======start====
		//条件1：申请书录入日期开始日
		if (!"".equals(strNoticeInputDateStart))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(SEC_DELIVERYORDER.InputDate , 'yyyy-mm-dd') >= " + "'" + strNoticeInputDateStart + "' \n");
		}
		//条件2：申请书录入日期开始日
		if (!"".equals(strNoticeInputDateEnd))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(SEC_DELIVERYORDER.InputDate , 'yyyy-mm-dd') <= " + "'" + strNoticeInputDateEnd + "' \n");
		}
		
		//成交日期开始日
		if (!"".equals(strTransactionDateStart))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(SEC_DELIVERYORDER.TransactionDate , 'yyyy-mm-dd') >= " + "'" + strTransactionDateStart + "' \n");
		}
		//成交日期结束日
		if (!"".equals(strTransactionDateEnd))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(SEC_DELIVERYORDER.TransactionDate , 'yyyy-mm-dd') <= " + "'" + strTransactionDateEnd + "' \n");
		}
		
		//条件3：业务类型
		if (businessTypeId > 0)
		{
			sbFrom.append(" and \n");
			sbFrom.append(" SEC_BUSINESSTYPE.Id = " + businessTypeId + " \n");
		}
		//东方电器  交割单查询时不查询10大业务之外的业务
		sbFrom.append(" and SEC_BUSINESSTYPE.Id not in ( 6,11,12,13,41,42,71,73,75,77,79,81,83,89,93 ) ");
		
		//条件4：交易类型
		if (transactionTypeIds != null && transactionTypeIds.length > 0)
		{
			sbFrom.append(" and SEC_DELIVERYORDER.transactionTypeID in ( ");
			for (int i = 0; i < transactionTypeIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(transactionTypeIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(transactionTypeIds[transactionTypeIds.length - 1]) + ") \n");
		}
		//条件5：业务单位
		if (clientId != -1)
		{
			sbFrom.append(" and \n SEC_CLIENT.id = " + clientId + " \n");
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
		//条件7.1：只选交易对手
		if (counterPartIds != null && counterPartIds.length > 0 && bankOfDepositId == -1 )
		{
			sbFrom.append(" and SEC_DELIVERYORDER.counterPartId in ( ");
			for (int i = 0; i < counterPartIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(counterPartIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(counterPartIds[counterPartIds.length - 1]) + ") \n");
		}
		//条件7.2：只选交易对手,开户营业部名称
		if (counterPartIds != null && counterPartIds.length > 0 && bankOfDepositId != -1)
		{
			sbFrom.append(" and SEC_DELIVERYORDER.counterPartId in ( ");
			for (int i = 0; i < counterPartIds.length; i++)
			{
				sbFrom.append(Long.parseLong(counterPartIds[i]) + ",");
			}
			sbFrom.append(bankOfDepositId + ") \n");
		}
		//条件8.1：只选开户营业部名称
		if (bankOfDepositId != -1 && (counterPartIds == null || counterPartIds.length == 0))
		{
			sbFrom.append(" and \n SEC_DELIVERYORDER.counterPartId = " + bankOfDepositId + " \n");
		}
		//条件9：资金账号
		if (accountIds != null && accountIds.length > 0)
		{
			sbFrom.append(" and SEC_ACCOUNT.id in ( ");
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
		//条件12：交割单状态
		if (statusId != -1)
		{
			sbFrom.append(" and \n SEC_DELIVERYORDER.statusId = " + statusId + " \n");
		}
		//条件13：录入人
		if (inputUserId != -1)
		{
			sbFrom.append(" and \n SEC_DELIVERYORDER.inputUserId = " + inputUserId + " \n");
		}
		//条件14：是否违规
		if (isViolation == 1)
		{
			sbFrom.append(" and \n SEC_DELIVERYORDER.ISVIOLATION = " + isViolation + " \n");
		}
		else if (isViolation == 2)
		{
			sbFrom.append(" and \n SEC_DELIVERYORDER.ISVIOLATION is null \n");
		}
		//对应的申请单id
		//由申请单的明细点击按钮进入交割单查询专用
		if(applyFormId != -1)
		{
			sbFrom.append(" and \n SEC_DELIVERYORDER.APPLYFORMID = "+ applyFormId +"\n");
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
		switch ((int) queryParam.getOrderField())
		{
			case OrderBy_DeliveryOrderCode :
				sbFrom.append(" \n order by deliveryCode" + strDesc);
				break;
			case OrderBy_ClientName :
				sbFrom.append(" \n order by ClientName" + strDesc);
				break;
			case OrderBy_BusinessTypeName :
				sbFrom.append(" \n order by BusinessTypeName" + strDesc);
				break;
			case OrderBy_TransactionTypeName :
				sbFrom.append(" \n order by TransactionTypeName" + strDesc);
				break;
			case OrderBy_NoticeInputDate :
				sbFrom.append(" \n order by transactionDate" + strDesc);
				break;
			case OrderBy_Accountcode :
				sbFrom.append(" \n order by accountID" + strDesc);
				break;
			case OrderBy_NetIncome :
				sbFrom.append(" \n order by NetIncome" + strDesc);
				break;
			case OrderBy_NoticeStatusId :
				sbFrom.append(" \n order by statusID" + strDesc);
				break;
		}
	}
	/**
	 * 返回证券查询的SQLString语句
	 * 
	 * @param queryParam
	 * 说明：仅适用于国机项目的查询--交割单查询
	 * @return
	 */
	private void getCNMEFSQL(QueryDeliveryorderParam queryParam)
	{
		System.out.println("国机：交割单查询---getCNMEFSQL()");
		sbSelect = new StringBuffer();
		sbSelect.append("   *  \n");
		sbFrom = new StringBuffer();
		sbFrom.append("(     \n");
		//		条件1：交割单录入日期开始日 
		Timestamp noticeInputDateStart = queryParam.getNoticeInputDateStart();
		String strNoticeInputDateStart = DataFormat.getDateString(noticeInputDateStart);
		//条件2：交割单录入日期结束日
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
		//条件12：交割单状态
		long statusId = queryParam.getStatusId();
		//条件13：录入人
		long inputUserId = queryParam.getInputUserId();
//		//条件14：是否违规
//		long isViolation = queryParam.getIsViolation();
		sbFrom.append(" select \n");
		sbFrom.append("    sec_deliveryorder.ID    deliveryId,   \n ");
		sbFrom.append("    sec_deliveryorder.code deliveryCode,  \n  ");
		//sbFrom.append("   SEC_STOCKHOLDERACCOUNT.name as stockHolderAccountName ,--股东帐户名称  \n");
		sbFrom.append("    SEC_APPLYFORM.CODE applyFormCode,   \n  ");
		sbFrom.append("    SEC_CLIENT.name clientName ,  \n");
		sbFrom.append("    SEC_BUSINESSTYPE.ID businessTypeId , \n  ");
		sbFrom.append("    SEC_BUSINESSTYPE.name businessTypeName ,  \n  ");
		sbFrom.append("    SEC_TRANSACTIONTYPE.name transactionTypeName  , \n   ");
		sbFrom.append("    TransactionDate transactionDate,  \n ");
		sbFrom.append("    SEC_COUNTERPART.name counterpartName,  \n ");
		sbFrom.append("    SEC_ACCOUNT.code accountID, \n  ");
		sbFrom.append("    sec_deliveryorder.NetIncome netIncome,   \n ");
		sbFrom.append("    sec_deliveryorder.StatusID statusID, \n   ");
		sbFrom.append("    sec_deliveryorder.OwnerTypeId  , \n");
		sbFrom.append("    userinfo.Id UserId , \n");
		sbFrom.append("    userinfo.sname InputUserName ,  \n ");
		sbFrom.append("    SEC_DELIVERYORDER.ISVIOLATION  as isViolation , \n ");
		sbFrom.append("    SEC_BUSINESSATTRIBUTE.id  businessAttribute  \n ");
		sbFrom.append("  from ");
		sbFrom.append(
			"  SEC_CLIENT  ,  SEC_TRANSACTIONTYPE , SEC_COUNTERPART  , sec_deliveryorder ,SEC_BUSINESSTYPE , \n"
				+ "USERINFO , SEC_BUSINESSATTRIBUTE , SEC_ACCOUNT ,SEC_APPLYFORM ,SEC_STOCKHOLDERACCOUNT,SEC_SECURITIES\n");
		sbFrom.append("  where   \n ");
		/*lhj modified 2004-07-01 不查询出虚拟交割单的内容*/
		sbFrom.append("   sec_deliveryorder.ISRELATEDBYNOTICEFORM <> 1 and \n");
		sbFrom.append("   SEC_STOCKHOLDERACCOUNT.ID(+) = SEC_ACCOUNT.STOCKHOLDERACCOUNTID1 \n");
		sbFrom.append("   and \n");
		sbFrom.append("   SEC_CLIENT.id = sec_deliveryorder.ClientID   \n ");
		sbFrom.append("  and SEC_TRANSACTIONTYPE.id = sec_deliveryorder.TransactionTypeID  \n");
		sbFrom.append("  and SEC_COUNTERPART.id = sec_deliveryorder.CounterpartID  \n ");
		sbFrom.append("  and SEC_BUSINESSTYPE.id = subStr(SEC_TRANSACTIONTYPE.Id,0,2) \n ");
		sbFrom.append("  and userinfo.id(+) =   sec_deliveryorder.InputUserID \n  ");
		sbFrom.append("  and SEC_BUSINESSATTRIBUTE.id =  SEC_BUSINESSTYPE.BUSINESSATTRIBUTEID  \n");
		sbFrom.append("  and SEC_ACCOUNT.id(+) = sec_deliveryorder.accountID  \n ");
		sbFrom.append("  and sec_deliveryorder.APPLYFORMID = SEC_APPLYFORM.ID(+)  \n ");
		sbFrom.append("   and  \n");
		sbFrom.append("   sec_deliveryorder.SECURITIESID = SEC_SECURITIES.ID (+)  \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   SEC_BUSINESSATTRIBUTE.id in (1,2,3)  \n"); //对应着第一种业务
        //过滤掉业务通知单生成的虚拟交割单
		sbFrom.append("   and sec_deliveryorder.isRelatedByNoticeForm != 1  \n");
		//			===========以下是根据页面传递的参数决定的条件======start====
		//条件1：申请书录入日期开始日
		if (!"".equals(strNoticeInputDateStart))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(SEC_DELIVERYORDER.TransactionDate , 'yyyy-mm-dd') >= " + "'" + strNoticeInputDateStart + "'");
		}
		//条件2：申请书录入日期开始日
		if (!"".equals(strNoticeInputDateEnd))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(SEC_DELIVERYORDER.TransactionDate , 'yyyy-mm-dd') <= " + "'" + strNoticeInputDateEnd + "'");
		}
		//条件3：业务类型
		if (businessTypeId > 0)
		{
			sbFrom.append(" and \n");
			sbFrom.append(" SEC_BUSINESSTYPE.Id = " + businessTypeId + " \n");
		}
		//条件4：交易类型
		if (transactionTypeIds != null && transactionTypeIds.length > 0)
		{
			sbFrom.append(" and SEC_DELIVERYORDER.transactionTypeID in ( ");
			for (int i = 0; i < transactionTypeIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(transactionTypeIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(transactionTypeIds[transactionTypeIds.length - 1]) + ") \n");
		}
		//条件5：业务单位
		if (clientId != -1)
		{
			sbFrom.append(" and \n SEC_CLIENT.id = " + clientId + " \n");
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
		//条件7.1：只选交易对手
		if (interBankCounterPartIds != null && interBankCounterPartIds.length > 0 && bankOfDepositId == -1 && (fundManagerCoIds == null || fundManagerCoIds.length == 0))
		{
			sbFrom.append(" and SEC_DELIVERYORDER.counterPartId in ( ");
			for (int i = 0; i < interBankCounterPartIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(interBankCounterPartIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(interBankCounterPartIds[interBankCounterPartIds.length - 1]) + ") \n");
		}
		//条件7.2：只选交易对手,开户营业部名称
		if (interBankCounterPartIds != null && interBankCounterPartIds.length > 0 && bankOfDepositId != -1 && (fundManagerCoIds == null || fundManagerCoIds.length == 0))
		{
			sbFrom.append(" and SEC_DELIVERYORDER.counterPartId in ( ");
			for (int i = 0; i < interBankCounterPartIds.length; i++)
			{
				sbFrom.append(Long.parseLong(interBankCounterPartIds[i]) + ",");
			}
			sbFrom.append(bankOfDepositId + ") \n");
		}
		//条件7.3：只选交易对手,基金管理公司名称
		if (interBankCounterPartIds != null && interBankCounterPartIds.length > 0 && bankOfDepositId == -1 && fundManagerCoIds != null && fundManagerCoIds.length > 0)
		{
			sbFrom.append(" and SEC_DELIVERYORDER.counterPartId in ( ");
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
			sbFrom.append(" and \n SEC_DELIVERYORDER.counterPartId = " + bankOfDepositId + " \n");
		}
		//条件8.2：只选开户营业部名称,基金管理公司名称
		if (bankOfDepositId != -1 && fundManagerCoIds != null && fundManagerCoIds.length > 0 && (interBankCounterPartIds == null || interBankCounterPartIds.length == 0))
		{
			//sbFrom.append(" and \n SEC_APPLYFORM.counterPartId = " +
			// bankOfDepositId + " \n");
			sbFrom.append(" and SEC_DELIVERYORDER.counterPartId in ( ");
			for (int i = 0; i < fundManagerCoIds.length; i++)
			{
				sbFrom.append(Long.parseLong(fundManagerCoIds[i]) + ",");
			}
			sbFrom.append(bankOfDepositId + ") \n");
		}
		//条件10.1：只选基金管理公司名称
		if (fundManagerCoIds != null && fundManagerCoIds.length > 0 && bankOfDepositId == -1 && (interBankCounterPartIds == null || interBankCounterPartIds.length == 0))
		{
			sbFrom.append(" and SEC_DELIVERYORDER.counterPartId in ( ");
			for (int i = 0; i < fundManagerCoIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(fundManagerCoIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(fundManagerCoIds[fundManagerCoIds.length - 1]) + ") \n");
		}
		//条件10.2：都选
		if (fundManagerCoIds != null && fundManagerCoIds.length > 0 && bankOfDepositId != -1 && interBankCounterPartIds != null && interBankCounterPartIds.length > 0)
		{
			sbFrom.append(" and SEC_DELIVERYORDER.counterPartId in ( ");
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
			sbFrom.append(" and SEC_ACCOUNT.id in ( ");
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
		//条件12：交割单状态
		if (statusId != -1)
		{
			sbFrom.append(" and \n SEC_DELIVERYORDER.statusId = " + statusId + " \n");
		}
		//条件13：录入人
		if (inputUserId != -1)
		{
			sbFrom.append(" and \n SEC_DELIVERYORDER.inputUserId = " + inputUserId + " \n");
		}
//		//条件14：是否违规
//		if (isViolation == 1)
//		{
//			sbFrom.append(" and \n SEC_DELIVERYORDER.ISVIOLATION = " + isViolation + " \n");
//		}
//		else if (isViolation == 2)
//		{
//			sbFrom.append(" and \n SEC_DELIVERYORDER.ISVIOLATION is null \n");
//		}
		//sbFrom拼写结束！！！！！！！！！！！	
		sbFrom.append("   )  \n");
		//////////////from////////////////end////////////////////////-
		sbWhere = new StringBuffer();
		//////////////where//////////////-end////////////////////////-
		sbWhere.append(" ");
		//////////////where//////////////-end////////////////////////-
		sbOrderBy = new StringBuffer();
		String strDesc = queryParam.getDesc() == 1 ? " desc " : "";
		switch ((int) queryParam.getOrderField())
		{
			case OrderBy_DeliveryOrderCode :
				sbFrom.append(" \n order by deliveryCode" + strDesc);
				break;
			case OrderBy_ClientName :
				sbFrom.append(" \n order by ClientName" + strDesc);
				break;
			case OrderBy_BusinessTypeName :
				sbFrom.append(" \n order by BusinessTypeName" + strDesc);
				break;
			case OrderBy_TransactionTypeName :
				sbFrom.append(" \n order by TransactionTypeName" + strDesc);
				break;
			case OrderBy_NoticeInputDate :
				sbFrom.append(" \n order by transactionDate" + strDesc);
				break;
			case OrderBy_Accountcode :
				sbFrom.append(" \n order by accountID" + strDesc);
				break;
			case OrderBy_NetIncome :
				sbFrom.append(" \n order by NetIncome" + strDesc);
				break;
			case OrderBy_NoticeStatusId :
				sbFrom.append(" \n order by statusID" + strDesc);
				break;
		}
	}
	/**
	 * 生成PageLoader
	 * 
	 * @param queryParam
	 * @return @throws
	 *         SecuritiesException
	 */
	public PageLoader queryDeliveryorderInfo(QueryDeliveryorderParam queryParam) throws SecuritiesException
	{
		/*if (Env.getProjectName().equalsIgnoreCase(Constant.ProjectName.CNMEF))
		{
			System.out.println("项目名称：国机;模块：交割单查询");
			getCNMEFSQL(queryParam);
		}
		else
		{*/
			System.out.println("项目名称：--;模块：交割单查询");
			getSQL(queryParam);
		//}
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		//log.debug("queryNoticeForm ==sbOrderBy :" + sbOrderBy.toString());
		pageLoader.initPageLoader(
			new AppContext(),
			sbFrom.toString(),
			sbSelect.toString(),
			sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.securities.query.dataentity.QueryDeliveryorderInfo",
			null);
		pageLoader.setOrderBy(" ");
		return pageLoader;
	}
}
