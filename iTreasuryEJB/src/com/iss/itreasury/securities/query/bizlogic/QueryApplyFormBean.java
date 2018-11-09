/*
 * Created on 2004-4-13
 * 
 * To change the template for this generated file go to Window - Preferences -
 * Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.query.bizlogic;
import java.sql.Timestamp;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.query.dataentity.QueryApplyFormParam;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
/**
 * @author 王怡
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class QueryApplyFormBean
{
	protected Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);
	private StringBuffer sbSelect = null;
	private StringBuffer sbFrom = null;
	private StringBuffer sbWhere = null;
	private StringBuffer sbOrderBy = null;
	public final static int OrderBy_NoticeCode = 1; //业务通知单编号
	public final static int OrderBy_ClientName = 2; //业务单位名称
	public final static int OrderBy_BusinessTypeName = 3; //业务类型
	public final static int OrderBy_TransactionTypeName = 4; //交易类型
	public final static int OrderBy_NoticeInputDate = 5; //业务通知单录入日期
	public final static int OrderBy_TransactionStartDate = 6; //申请成交开始日
	public final static int OrderBy_TransactionEndDate = 7; //申请成交结束日
	public final static int OrderBy_Accountcode = 8; //资金账户
	public final static int OrderBy_NoticeStatusId = 9; //业务通知单状态
	public final static int OrderBy_PledgeSecuritiesAmount = 10;//用款金额
	public final static int OrderBy_MaturityDate = 11;//锁定期
	/**
	 * 返回证券查询的SQLString语句
	 * 
	 * @param queryParam
	 * @return
	 */
	private void getSQL(QueryApplyFormParam queryParam)
	{
		sbSelect = new StringBuffer();
		sbSelect.append("   *  \n");
		sbFrom = new StringBuffer();
		//需要查出来的在v002显示的信息
		//申请书编号 业务单位名称 股东帐户名称 业务类型 交易类型 申请录入日期 申请成交开始日 申请成交结束日 交易对手名称
		//开户营业部名称 资金账号 基金管理公司名称 申请单状态 审核状态 录入人
		sbFrom.append("(     \n");
		//条件1：申请书录入日期开始日
		Timestamp noticeInputDateStart = queryParam.getNoticeInputDateStart();
		String strNoticeInputDateStart = DataFormat.getDateString(noticeInputDateStart);
		//条件2：申请书录入日期结束日
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
//		//条件10：基金管理公司名称
//		String[] fundManagerCoIds = queryParam.getFundManagerCoIds();
		//条件11：证券名称
		String[] securitiesIds = queryParam.getSecuritiesIds();
		//条件12：申请书状态
		long statusId = queryParam.getStatusId();
		//条件13：录入人
		long inputUserId = queryParam.getInputUserId();
		sbFrom.append("  select   \n");
		sbFrom.append("   SEC_APPLYFORM.ID as NoticeId,--申请书ID  \n");
		sbFrom.append("   SEC_APPLYFORM.code as NoticeCode,--申请书编号  \n");
		sbFrom.append("   SEC_CLIENT.name as ClientName ,--业务单位名称  \n");
		sbFrom.append("   SEC_STOCKHOLDERACCOUNT.name as stockHolderAccountName ,--股东帐户名称  \n");
		sbFrom.append("   ST_Type.businessTypeName as BusinessTypeName ,--业务类型  \n");
		sbFrom.append("   ST_Type.businessTypeId,  \n");
		sbFrom.append("   ST_Type.transactionTypeName, --交易类型 \n");
		sbFrom.append("   SEC_APPLYFORM.TRANSACTIONTYPEID ,  \n");
		sbFrom.append("    ST_Type.businessAttributeID,  \n");
		sbFrom.append("   SEC_APPLYFORM.INPUTDATE as NoticeInputDate,--申请录入日期  \n");
		sbFrom.append("   SEC_APPLYFORM.TRANSACTIONSTARTDATE  ,--申请成交开始日  \n");
		sbFrom.append("   SEC_APPLYFORM.TRANSACTIONENDDATE  ,--申请成交结束日  \n");
		sbFrom.append("   decode(SEC_COUNTERPART.IsBankOfDeposit,1,-1,SEC_COUNTERPART.Id) as CounterPartId,--交易对手 \n");
		sbFrom.append("   decode(SEC_COUNTERPART.IsBankOfDeposit,1,SEC_COUNTERPART.Id,'') as BankOfDepositId,--开户营业部名称 \n");
		sbFrom.append("   SEC_ACCOUNT.AccountCode as Accountcode,  --资金帐号  \n");
		sbFrom.append("   SEC_SECURITIES.name as SecuritiesName,  --证券名称  \n");
		sbFrom.append("   SEC_APPLYFORM.StatusID NoticeStatusId,  --申请书状态 \n");
		sbFrom.append("   DECODE(SEC_APPLYFORM.StatusID,5,-1,SEC_APPLYFORM.NEXTCHECKUSERID) nextCheckUserId,  --审核人，作为参数查出审核状态 \n");
		sbFrom.append("   SEC_APPLYFORM.InputUserID userId  --录入人 \n");
		sbFrom.append("  from \n");
		//排除 开户银行的可能
		sbFrom.append("   SEC_CLIENT,SEC_TRANSACTIONTYPE,(select * from SEC_COUNTERPART where ISBANK is null or ISBANK = -1)SEC_COUNTERPART, \n");
		sbFrom.append("   SEC_APPLYFORM ,SEC_BUSINESSTYPE, SEC_ACCOUNT , SEC_SECURITIES , SEC_STOCKHOLDERACCOUNT ,\n");
		sbFrom.append("   (select a.id as transactionTypeId, \n");
		sbFrom.append("           a.name as transactionTypeName,  \n");
		sbFrom.append("           b.Id as businessTypeId,  \n");
		sbFrom.append("           b.name as businessTypeName, \n");
		sbFrom.append("            c.ID as businessAttributeID \n");
		sbFrom.append("    from  SEC_TRANSACTIONTYPE a ,SEC_BUSINESSTYPE b,SEC_BUSINESSATTRIBUTE c  \n");
		sbFrom.append("    where subStr(a.Id,0,2) = b.id  and b.BUSINESSATTRIBUTEID = c.ID  \n");
		sbFrom.append("     ) ST_Type  \n");
		sbFrom.append("   where  \n");
		sbFrom.append("   SEC_STOCKHOLDERACCOUNT.ID(+) = SEC_ACCOUNT.STOCKHOLDERACCOUNTID1 \n");
		sbFrom.append("   and \n");
		sbFrom.append("   SEC_CLIENT.id(+) = SEC_APPLYFORM.ClientID \n");
		sbFrom.append("   and \n");
		sbFrom.append("   SEC_TRANSACTIONTYPE.id = SEC_APPLYFORM.TransactionTypeID \n");
		sbFrom.append("   and \n");
		sbFrom.append("   SEC_COUNTERPART.id = SEC_APPLYFORM.CounterpartID  \n");
		sbFrom.append("   and \n");
		sbFrom.append("   SEC_BUSINESSTYPE.id = subStr(SEC_TRANSACTIONTYPE.Id,0,2) \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   SEC_APPLYFORM.ACCOUNTID = SEC_ACCOUNT.ID (+)  \n");
		sbFrom.append("   and SEC_APPLYFORM.TransactionTypeID = ST_Type.transactionTypeId \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   SEC_APPLYFORM.SECURITIESID = SEC_SECURITIES.ID (+)  \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   ST_Type.businessAttributeID in (1,2,3,4)  \n"); //对应着第一种业务
		//		===========以下是根据页面传递的参数决定的条件======start====
		//条件1：申请书录入日期开始日
		if (!"".equals(strNoticeInputDateStart))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(SEC_APPLYFORM.INPUTDATE , 'yyyy-mm-dd') >= " + "'" + strNoticeInputDateStart + "'");
		}
		//条件2：申请书录入日期开始日
		if (!"".equals(strNoticeInputDateEnd))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(SEC_APPLYFORM.INPUTDATE , 'yyyy-mm-dd') <= " + "'" + strNoticeInputDateEnd + "'");
		}
		
        //成交日期开始日
		if (!"".equals(strTransactionDateEnd))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(SEC_APPLYFORM.transactionstartdate , 'yyyy-mm-dd') >= " + "'" + strTransactionDateStart + "'");
		}
		//成交日期结束日
		if (!"".equals(strTransactionDateStart))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(SEC_APPLYFORM.transactionenddate , 'yyyy-mm-dd') <= " + "'" + strTransactionDateEnd + "'");
		}
		
		//条件3：业务类型
		if (businessTypeId > 0)
		{
			sbFrom.append(" and \n");
			sbFrom.append(" ST_Type.businessTypeId = " + businessTypeId + " \n");
		}
		//东方电器  申请查询时不查询9大业务之外的业务
		sbFrom.append(" and ST_Type.businessTypeId not in ( 6,11,12,13,41,42,71,73,75,77,79,81,83,85,87,89,91,93 ) ");
		
		//条件4：交易类型
		if (transactionTypeIds != null && transactionTypeIds.length > 0)
		{
			sbFrom.append(" and SEC_APPLYFORM.transactionTypeID in ( ");
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
			sbFrom.append(" and SEC_APPLYFORM.counterPartId in ( ");
			for (int i = 0; i < counterPartIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(counterPartIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(counterPartIds[counterPartIds.length - 1]) + ") \n");
		}
		//条件7.2：只选交易对手,开户营业部名称
		if (counterPartIds != null && counterPartIds.length > 0 && bankOfDepositId != -1 )
		{
			sbFrom.append(" and SEC_APPLYFORM.counterPartId in ( ");
			for (int i = 0; i < counterPartIds.length; i++)
			{
				sbFrom.append(Long.parseLong(counterPartIds[i]) + ",");
			}
			sbFrom.append(bankOfDepositId + ") \n");
		}
		//条件8.1：只选开户营业部名称
		if (bankOfDepositId != -1 && (counterPartIds == null || counterPartIds.length == 0))
		{
			sbFrom.append(" and \n SEC_APPLYFORM.counterPartId = " + bankOfDepositId + " \n");
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
		//条件12：申请书状态
		if (statusId != -1)
		{
			sbFrom.append(" and \n SEC_APPLYFORM.statusId = " + statusId + " \n");
		}
		//条件13：录入人
		if (inputUserId != -1)
		{
			sbFrom.append(" and \n SEC_APPLYFORM.inputUserId = " + inputUserId + " \n");
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
			case OrderBy_TransactionStartDate :
				sbOrderBy.append(" \n order by TransactionStartDate" + strDesc);
				break;
			case OrderBy_TransactionEndDate :
				sbOrderBy.append(" \n order by TransactionEndDate" + strDesc);
				break;
			case OrderBy_Accountcode :
				sbOrderBy.append(" \n order by Accountcode" + strDesc);
				break;
			case OrderBy_NoticeStatusId :
				sbOrderBy.append(" \n order by NoticeStatusId" + strDesc);
				break;
		}
		log.debug("////////////////////////////////////////-");
	}
	/**
	 * 返回证券查询的SQLString语句
	 * 
	 * @param queryParam
	 * 说明：仅适用于国机项目的查询---申请书查询
	 *  @return
	 */
	private void getCNMEFSQL(QueryApplyFormParam queryParam)
	{
		System.out.println("国机：申请书查询---getCNMEFSQL()");
		sbSelect = new StringBuffer();
		sbSelect.append("   *  \n");
		sbFrom = new StringBuffer();
		//需要查出来的在v002显示的信息
		//申请书编号 业务单位名称 股东帐户名称 业务类型 交易类型 申请录入日期 申请成交开始日 申请成交结束日 交易对手名称
		//开户营业部名称 资金账号 基金管理公司名称 申请单状态 审核状态 录入人
		sbFrom.append("(     \n");
		//条件1：申请书录入日期开始日
		Timestamp noticeInputDateStart = queryParam.getNoticeInputDateStart();
		String strNoticeInputDateStart = DataFormat.getDateString(noticeInputDateStart);
		//条件2：申请书录入日期结束日
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
		sbFrom.append("  select   \n");
		sbFrom.append("   SEC_APPLYFORM.ID as NoticeId,--申请书ID  \n");
		sbFrom.append("   SEC_APPLYFORM.code as NoticeCode,--申请书编号  \n");
		sbFrom.append("   SEC_CLIENT.name as ClientName ,--业务单位名称  \n");
		//sbFrom.append("   SEC_STOCKHOLDERACCOUNT.name as stockHolderAccountName ,--股东帐户名称  \n");
		sbFrom.append("   ST_Type.businessTypeName as BusinessTypeName ,--业务类型  \n");
		sbFrom.append("   ST_Type.businessTypeId,  \n");
		sbFrom.append("   ST_Type.transactionTypeName, --交易类型 \n");
		sbFrom.append("   SEC_APPLYFORM.TRANSACTIONTYPEID ,  \n");
		sbFrom.append("    ST_Type.businessAttributeID,  \n");
		sbFrom.append("   SEC_APPLYFORM.INPUTDATE as NoticeInputDate,--申请录入日期  \n");
		sbFrom.append("   SEC_APPLYFORM.TRANSACTIONSTARTDATE  ,--申请成交开始日  \n");
		sbFrom.append("   SEC_APPLYFORM.TRANSACTIONENDDATE  ,--申请成交结束日  \n");
		sbFrom.append("   SEC_APPLYFORM.PledgeSecuritiesAmount  ,--用款金额  \n");
		sbFrom.append("   SEC_APPLYFORM.MaturityDate  ,--锁定期  \n");
		sbFrom.append("   SEC_APPLYFORM.OwnerTypeId  ,--类别选择  \n");
		sbFrom.append("   SEC_COUNTERPART.name as CounterPartName,--交易对手名称,开户营业部名称,基金管理公司名称 \n");
		sbFrom.append("   SEC_ACCOUNT.AccountCode as Accountcode,  --资金帐号  \n");
		sbFrom.append("   SEC_SECURITIES.name as SecuritiesName,  --证券名称  \n");
		sbFrom.append("   SEC_APPLYFORM.StatusID NoticeStatusId,  --申请书状态 \n");
		sbFrom.append("   SEC_APPLYFORM.NEXTCHECKUSERID nextCheckUserId,  --审核人，作为参数查出审核状态 \n");
		sbFrom.append("   SEC_APPLYFORM.InputUserID userId --录入人Id \n");
		sbFrom.append("  from \n");
		sbFrom.append("   SEC_CLIENT , SEC_TRANSACTIONTYPE , SEC_COUNTERPART , SEC_APPLYFORM ,\n");
		sbFrom.append("   SEC_BUSINESSTYPE, SEC_ACCOUNT , SEC_SECURITIES , SEC_STOCKHOLDERACCOUNT ,\n");
		sbFrom.append("   (select a.id as transactionTypeId, \n");
		sbFrom.append("           a.name as transactionTypeName,  \n");
		sbFrom.append("           b.Id as businessTypeId,  \n");
		sbFrom.append("           b.name as businessTypeName, \n");
		sbFrom.append("            c.ID as businessAttributeID \n");
		sbFrom.append("    from  SEC_TRANSACTIONTYPE a ,SEC_BUSINESSTYPE b,SEC_BUSINESSATTRIBUTE c  \n");
		sbFrom.append("    where subStr(a.Id,0,2) = b.id  and b.BUSINESSATTRIBUTEID = c.ID  \n");
		sbFrom.append("     ) ST_Type  \n");
		sbFrom.append("   where  \n");
		sbFrom.append("   SEC_STOCKHOLDERACCOUNT.ID(+) = SEC_ACCOUNT.STOCKHOLDERACCOUNTID1 \n");
		sbFrom.append("   and \n");
		sbFrom.append("   SEC_CLIENT.id(+) = SEC_APPLYFORM.ClientID \n");
		sbFrom.append("   and \n");
		sbFrom.append("   SEC_TRANSACTIONTYPE.id = SEC_APPLYFORM.TransactionTypeID \n");
		sbFrom.append("   and \n");
		sbFrom.append("   SEC_COUNTERPART.id = SEC_APPLYFORM.CounterpartID  \n");
		sbFrom.append("   and \n");
		sbFrom.append("   SEC_BUSINESSTYPE.id = subStr(SEC_TRANSACTIONTYPE.Id,0,2) \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   SEC_APPLYFORM.ACCOUNTID = SEC_ACCOUNT.ID (+)  \n");
		sbFrom.append("   and SEC_APPLYFORM.TransactionTypeID = ST_Type.transactionTypeId \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   SEC_APPLYFORM.SECURITIESID = SEC_SECURITIES.ID (+)  \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   ST_Type.businessAttributeID in (1,2,3)  \n"); //对应着第一种业务
		//		===========以下是根据页面传递的参数决定的条件======start====
		//条件1：申请书录入日期开始日
		if (!"".equals(strNoticeInputDateStart))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(SEC_APPLYFORM.INPUTDATE , 'yyyy-mm-dd') >= " + "'" + strNoticeInputDateStart + "'");
		}
		//条件2：申请书录入日期开始日
		if (!"".equals(strNoticeInputDateEnd))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(SEC_APPLYFORM.INPUTDATE , 'yyyy-mm-dd') <= " + "'" + strNoticeInputDateEnd + "'");
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
			sbFrom.append(" and SEC_APPLYFORM.transactionTypeID in ( ");
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
			sbFrom.append(" and SEC_APPLYFORM.counterPartId in ( ");
			for (int i = 0; i < interBankCounterPartIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(interBankCounterPartIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(interBankCounterPartIds[interBankCounterPartIds.length - 1]) + ") \n");
		}
		//条件7.2：只选交易对手,开户营业部名称
		if (interBankCounterPartIds != null && interBankCounterPartIds.length > 0 && bankOfDepositId != -1 && (fundManagerCoIds == null || fundManagerCoIds.length == 0))
		{
			sbFrom.append(" and SEC_APPLYFORM.counterPartId in ( ");
			for (int i = 0; i < interBankCounterPartIds.length; i++)
			{
				sbFrom.append(Long.parseLong(interBankCounterPartIds[i]) + ",");
			}
			sbFrom.append(bankOfDepositId + ") \n");
		}
		//条件7.3：只选交易对手,基金管理公司名称
		if (interBankCounterPartIds != null && interBankCounterPartIds.length > 0 && bankOfDepositId == -1 && fundManagerCoIds != null && fundManagerCoIds.length > 0)
		{
			sbFrom.append(" and SEC_APPLYFORM.counterPartId in ( ");
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
			sbFrom.append(" and \n SEC_APPLYFORM.counterPartId = " + bankOfDepositId + " \n");
		}
		//条件8.2：只选开户营业部名称,基金管理公司名称
		if (bankOfDepositId != -1 && fundManagerCoIds != null && fundManagerCoIds.length > 0 && (interBankCounterPartIds == null || interBankCounterPartIds.length == 0))
		{
			//sbFrom.append(" and \n SEC_APPLYFORM.counterPartId = " +
			// bankOfDepositId + " \n");
			sbFrom.append(" and SEC_APPLYFORM.counterPartId in ( ");
			for (int i = 0; i < fundManagerCoIds.length; i++)
			{
				sbFrom.append(Long.parseLong(fundManagerCoIds[i]) + ",");
			}
			sbFrom.append(bankOfDepositId + ") \n");
		}
		//条件10.1：只选基金管理公司名称
		if (fundManagerCoIds != null && fundManagerCoIds.length > 0 && bankOfDepositId == -1 && (interBankCounterPartIds == null || interBankCounterPartIds.length == 0))
		{
			sbFrom.append(" and SEC_APPLYFORM.counterPartId in ( ");
			for (int i = 0; i < fundManagerCoIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(fundManagerCoIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(fundManagerCoIds[fundManagerCoIds.length - 1]) + ") \n");
		}
		//条件10.2：都选
		if (fundManagerCoIds != null && fundManagerCoIds.length > 0 && bankOfDepositId != -1 && interBankCounterPartIds != null && interBankCounterPartIds.length > 0)
		{
			sbFrom.append(" and SEC_APPLYFORM.counterPartId in ( ");
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
		//条件12：申请书状态
		if (statusId != -1)
		{
			sbFrom.append(" and \n SEC_APPLYFORM.statusId = " + statusId + " \n");
		}
		//条件13：录入人
		if (inputUserId != -1)
		{
			sbFrom.append(" and \n SEC_APPLYFORM.inputUserId = " + inputUserId + " \n");
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
			case OrderBy_TransactionStartDate :
				sbOrderBy.append(" \n order by TransactionStartDate" + strDesc);
				break;
			case OrderBy_TransactionEndDate :
				sbOrderBy.append(" \n order by TransactionEndDate" + strDesc);
				break;
			case OrderBy_Accountcode :
				sbOrderBy.append(" \n order by Accountcode" + strDesc);
				break;
			case OrderBy_NoticeStatusId :
				sbOrderBy.append(" \n order by NoticeStatusId" + strDesc);
				break;
			case OrderBy_PledgeSecuritiesAmount :
				sbOrderBy.append(" \n order by PledgeSecuritiesAmount" + strDesc);
				break;
			case OrderBy_MaturityDate :
				sbOrderBy.append(" \n order by MaturityDate" + strDesc);
				break;
		}
		log.debug("////////////////////////////////////////-");
	}
	/**
	 * 生成PageLoader
	 * 
	 * @param queryParam
	 * @return @throws
	 *         SecuritiesException
	 */
	public PageLoader queryApplyFormInfo(QueryApplyFormParam queryParam) throws SecuritiesException
	{
		/*if (Env.getProjectName().equalsIgnoreCase(Constant.ProjectName.CNMEF))
		{
			System.out.println("项目名称：国机;模块：申请书查询");
			getCNMEFSQL(queryParam);
		}
		else
		{*/
			System.out.println("项目名称：--;模块：申请书查询");
			getSQL(queryParam);
		//}
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		log.debug("queryNoticeForm ==sbOrderBy :" + sbOrderBy.toString());
		pageLoader.initPageLoader(
			new AppContext(),
			sbFrom.toString(),
			sbSelect.toString(),
			sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.securities.query.dataentity.QueryApplyFormInfo",
			null);
		pageLoader.setOrderBy(" " + sbOrderBy.toString() + " ");
		return pageLoader;
	}
}
