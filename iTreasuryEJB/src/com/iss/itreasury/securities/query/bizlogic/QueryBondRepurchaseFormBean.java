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
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

/**
 * @author wangyi
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class QueryBondRepurchaseFormBean {

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

	/**
	 * 返回证券查询的SQLString语句
	 * 
	 * @param queryParam
	 * @return
	 */
	private void getSQL(QueryApplyFormParam queryParam) {
		sbSelect = new StringBuffer();

		sbSelect.append("   *  \n");


		sbFrom = new StringBuffer();


//申请书编号  业务单位名称  业务类型  交易类型  申请录入日期  申请成交开始日  申请成交结束日  交易对手名称  
//开户营业部名称  资金账号  基金管理公司名称  申请单状态  录入人 
		sbFrom.append("(     \n");
		sbFrom.append("  select   \n");
		
		sbFrom.append("   SEC_APPLYFORM.ID as NoticeId,--申请书ID  \n");
		sbFrom.append("   SEC_APPLYFORM.code as NoticeCode,--申请书编号  \n");
		sbFrom.append("   SEC_CLIENT.name as ClientName ,--业务单位名称  \n");
		sbFrom.append("   ST_Type.businessTypeName as BusinessTypeName ,--业务类型  \n");
		sbFrom.append("   ST_Type.businessTypeId,  \n");
		sbFrom.append("   ST_Type.transactionTypeName, --交易类型 \n");
		sbFrom.append("   SEC_APPLYFORM.TRANSACTIONTYPEID ,  \n");
		sbFrom.append("    ST_Type.businessAttributeID,  \n");
		sbFrom.append("   SEC_APPLYFORM.INPUTDATE as NoticeInputDate,--申请录入日期  \n");
		sbFrom.append("   SEC_APPLYFORM.TRANSACTIONSTARTDATE  ,--申请成交开始日  \n");
		sbFrom.append("   SEC_APPLYFORM.TRANSACTIONENDDATE  ,--申请成交结束日  \n");
		sbFrom.append("   SEC_COUNTERPART.name as CounterPartName,--交易对手名称,开户营业部名称,基金管理公司名称 \n");
		sbFrom.append("   SEC_ACCOUNT.AccountCode as Accountcode,  --资金帐号  \n");
		sbFrom.append("   SEC_SECURITIES.name as SecuritiesName,  --证券名称  \n");
		sbFrom.append("   SEC_APPLYFORM.StatusID NoticeStatusId,  --申请书状态 \n");
		sbFrom.append("   USERINFO.SNAME UserName  --录入人 \n");


		sbFrom.append("  from \n");

		sbFrom.append("   SEC_CLIENT , SEC_TRANSACTIONTYPE , SEC_COUNTERPART , SEC_APPLYFORM ,\n");
		sbFrom.append("   SEC_BUSINESSTYPE, USERINFO,SEC_ACCOUNT , SEC_SECURITIES , \n");
		
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
		

		sbFrom.append("   where  \n");

		sbFrom.append("   SEC_CLIENT.id(+) = SEC_APPLYFORM.ClientID \n");
		sbFrom.append("   and \n");
		sbFrom.append("   SEC_TRANSACTIONTYPE.id(+) = SEC_APPLYFORM.TransactionTypeID \n");
		sbFrom.append("   and \n");
		sbFrom.append("   SEC_COUNTERPART.id(+) = SEC_APPLYFORM.CounterpartID  \n");
		sbFrom.append("   and \n");
		sbFrom.append("   SEC_BUSINESSTYPE.id = subStr(SEC_TRANSACTIONTYPE.Id,0,2) \n");
		sbFrom.append("   and \n");
		sbFrom.append("   USERINFO.ID(+) = SEC_APPLYFORM.InputUserID \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   SEC_APPLYFORM.ACCOUNTID = SEC_ACCOUNT.ID (+)  \n");
		sbFrom.append("   and SEC_APPLYFORM.TransactionTypeID = ST_Type.transactionTypeId(+) \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   SEC_APPLYFORM.SECURITIESID = SEC_SECURITIES.ID (+)  \n");



		//		===========以下是根据页面传递的参数决定的条件======start====
		//条件一：业务通知单录入日期开始日

//		Timestamp noticeInputDateStart = queryParam.getNoticeInputDateStart();
//		String strNoticeInputDateStart =
//			DataFormat.getDateString(noticeInputDateStart);
//		if(!"".equals(strNoticeInputDateStart))
//		{
//			sbFrom.append(" and \n");
//			sbFrom.append(
//				" to_char(SEC_APPLYFORM.INPUTDATE , 'yyyy-mm-dd') >= " + "'" + strNoticeInputDateStart + "'");
//		}
//		//条件二：业务通知单录入日期开始日
//		Timestamp noticeInputDateEnd = queryParam.getNoticeInputDateEnd();
//		String strNoticeInputDateEnd =
//			DataFormat.getDateString(noticeInputDateEnd);
//		if(!"".equals(strNoticeInputDateEnd))
//		{
//			sbFrom.append(" and \n");
//			sbFrom.append(
//				" to_char(SEC_APPLYFORM.INPUTDATE , 'yyyy-mm-dd') <= " + "'" + strNoticeInputDateEnd + "'");
//		}
//		//条件四：业务类型
//		long businessTypeId = queryParam.getBusinessTypeId();
//		if (businessTypeId > 0) {
//			sbFrom.append(" and \n");
//			sbFrom.append(
//				" ST_Type.businessTypeId = " + businessTypeId + " \n");
//		}
//		//条件五：交易类型
//		String[] transactionTypeIds = queryParam.getTransactionTypeIds();
//		if (transactionTypeIds != null && transactionTypeIds.length > 0) {
//			sbFrom.append(" and SEC_APPLYFORM.transactionTypeID in ( ");
//			for (int i = 0; i < transactionTypeIds.length - 1; i++) {
//				sbFrom.append(Long.parseLong(transactionTypeIds[i]) + ",");
//			}
//			sbFrom.append(
//				Long.parseLong(
//					transactionTypeIds[transactionTypeIds.length - 1])
//					+ ") \n");
//		}
//
//		//条件六：业务单位
//		String[] clientIds = queryParam.getClientIds();
//		if (clientIds != null && clientIds.length > 0) {
//			sbFrom.append(" and SEC_CLIENT.id in ( ");
//			for (int i = 0; i < clientIds.length - 1; i++) {
//				sbFrom.append(Long.parseLong(clientIds[i]) + ",");
//			}
//			sbFrom.append(
//				Long.parseLong(clientIds[clientIds.length - 1]) + ") \n");
//		}
//		//条件七：资金账号
//		String[] accountIds = queryParam.getAccountIds();
//		if (accountIds != null && accountIds.length > 0) {
//			sbFrom.append(" and SEC_ACCOUNT.id in ( ");
//			for (int i = 0; i < accountIds.length - 1; i++) {
//				sbFrom.append(Long.parseLong(accountIds[i]) + ",");
//			}
//			sbFrom.append(
//				Long.parseLong(accountIds[accountIds.length - 1]) + ") \n");
//		}
//
//		//条件八：证券名称
//		String[] securitiesIds = queryParam.getSecuritiesIds();
//		if (securitiesIds != null && securitiesIds.length > 0) {
//			sbFrom.append(" and SEC_SECURITIES.id in ( ");
//			for (int i = 0; i < securitiesIds.length - 1; i++) {
//				sbFrom.append(Long.parseLong(securitiesIds[i]) + ",");
//			}
//			sbFrom.append(
//				Long.parseLong(securitiesIds[securitiesIds.length - 1])
//					+ ") \n");
//		}
//		//条件× 业务状态
//		long businessAttributeId = queryParam.getBusinessAttributeId();
//		log.debug("！！！！业务状态businessAttributeId== "+businessAttributeId);
//		//条件九：交易对手
//		String[] interBankCounterPartIds = queryParam.getInterBankCounterPartIds();
//		//条件十：开户营业部名称
//		String[] bankOfDepositIds = queryParam.getBankOfDepositIds();
//		//条件十一：基金管理公司名称
//		String[] fundManagerCoIds = queryParam.getFundManagerCoIds();
//		if (businessAttributeId == -1) {
//			/*
//			 * 表示没有选择或者输入业务类型！！！！！
//			 */
//
//			String sqlBusinessIds = "";
//			if (interBankCounterPartIds != null	&& interBankCounterPartIds.length > 0) {
//				for (int i = 0; i < interBankCounterPartIds.length - 1; i++) {
//					sqlBusinessIds += Long.parseLong(interBankCounterPartIds[i])
//						+ ",";
//				}
//				sqlBusinessIds
//					+= Long.parseLong(
//						interBankCounterPartIds[interBankCounterPartIds.length
//							- 1])
//					+ ",";
//			}
//			log.debug("交易对手sqlBusinessIds== "+sqlBusinessIds);
//			
//			if (bankOfDepositIds != null && bankOfDepositIds.length > 0) {
//				for (int i = 0; i < bankOfDepositIds.length - 1; i++) {
//					sqlBusinessIds += Long.parseLong(bankOfDepositIds[i]) + ",";
//				}
//				sqlBusinessIds
//					+= Long.parseLong(
//						bankOfDepositIds[bankOfDepositIds.length - 1])
//					+ ",";
//			}
//			log.debug("开户营业部sqlBusinessIds== "+sqlBusinessIds);
//			
//			if (fundManagerCoIds != null && fundManagerCoIds.length > 0) {
//				for (int i = 0; i < fundManagerCoIds.length - 1; i++) {
//					sqlBusinessIds += Long.parseLong(fundManagerCoIds[i]) + ",";
//				}
//				sqlBusinessIds
//					+= Long.parseLong(
//						fundManagerCoIds[fundManagerCoIds.length - 1])
//					+ ",";
//			}
//			
//			log.debug("基金管理公司sqlBusinessIds== "+sqlBusinessIds);
//			log.debug("sqlBusinessIds.length() == "+sqlBusinessIds.length());
//			if(sqlBusinessIds.length()>0){
//				
//				sbFrom.append(" and SEC_APPLYFORM.counterPartId in ( ");
//				 //去掉最后一个逗号，
//				 sqlBusinessIds = sqlBusinessIds.substring(0,sqlBusinessIds.length()-1);
//				 log.debug("去掉最后一个逗号sqlBusinessIds== "+sqlBusinessIds);
//				sbFrom.append(sqlBusinessIds +" )"); 
//			
//			}
//
//		} else {
//			if (businessAttributeId == 1) {
//				/*
//				 * 银行间业务,特指交易对手
//				 */
//				if (interBankCounterPartIds != null
//					&& interBankCounterPartIds.length > 0) {
//					sbFrom.append(" and SEC_APPLYFORM.counterPartId in ( ");
//					for (int i = 0;
//						i < interBankCounterPartIds.length - 1;
//						i++) {
//						sbFrom.append(
//							Long.parseLong(interBankCounterPartIds[i]) + ",");
//					}
//					sbFrom.append(
//						Long.parseLong(
//							interBankCounterPartIds[interBankCounterPartIds
//								.length
//								- 1])
//							+ ") \n");
//				}
//
//			} else if (businessAttributeId == 2) {
//				/*
//				 * 交易所业务，特指开户营业部
//				 */
//				if (bankOfDepositIds != null && bankOfDepositIds.length > 0) {
//					sbFrom.append(" and SEC_APPLYFORM.counterPartId in ( ");
//					for (int i = 0; i < bankOfDepositIds.length - 1; i++) {
//						sbFrom.append(
//							Long.parseLong(bankOfDepositIds[i]) + ",");
//					}
//					sbFrom.append(
//						Long.parseLong(
//							bankOfDepositIds[bankOfDepositIds.length - 1])
//							+ ") \n");
//				}
//
//			} else if (businessAttributeId == 3) {
//				/*
//				 * 开放式基金业务：特指基金管理公司
//				 */
//				if (fundManagerCoIds != null && fundManagerCoIds.length > 0) {
//					sbFrom.append(" and SEC_APPLYFORM.counterPartId in ( ");
//					for (int i = 0; i < fundManagerCoIds.length - 1; i++) {
//						sbFrom.append(
//							Long.parseLong(fundManagerCoIds[i]) + ",");
//					}
//					sbFrom.append(
//						Long.parseLong(
//							fundManagerCoIds[fundManagerCoIds.length - 1])
//							+ ") \n");
//				}
//			}
//
//		}
		


		//===========以下是根据页面传递的参数决定的条件======end====
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
		switch ((int) queryParam.getOrderField()) {
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
	 * 生成PageLoader
	 * 
	 * @param queryParam
	 * @return @throws
	 *         SecuritiesException
	 */
	public PageLoader queryApplyFormInfo(QueryApplyFormParam queryParam)
		throws SecuritiesException {

		getSQL(queryParam);
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
			"com.iss.itreasury.securities.query.dataentity.QueryApplyFormInfo",
			null);

		pageLoader.setOrderBy(" " + sbOrderBy.toString() + " ");
		return pageLoader;
	}

}
