/*
 * 创建日期 2004-5-11
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package com.iss.itreasury.securities.print.bizlogic;

import java.sql.Timestamp;

import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.print.dataentity.PrintSecuritiesExchangeStatusParam;
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
public class PrintSecuritiesExchangeStatusBean {

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
	private void getSQL(PrintSecuritiesExchangeStatusParam queryParam) {
		sbSelect = new StringBuffer();

		sbSelect.append("   *  \n");
		sbWhere = new StringBuffer();
		sbWhere.append(" ");
		sbFrom = new StringBuffer();
		
		sbFrom.append("(     \n");
  		
		
		//*** 证券菜单id:决定拼哪一个SQL ***
		long securitiesMenuId = queryParam.getSecuritiesMenuId();

		//条件1：日期开始日 
		Timestamp noticeInputDateStart = queryParam.getNoticeInputDateStart();
		String strNoticeInputDateStart = DataFormat.getDateString(noticeInputDateStart);
		//条件2：日期结束日 
		Timestamp noticeInputDateEnd = queryParam.getNoticeInputDateEnd();
		String strNoticeInputDateEnd = DataFormat.getDateString(noticeInputDateEnd);
		
		//条件：申购交割单录入日期
		Timestamp deliveryOrderInputDateStart = queryParam.getDeliveryOrderInputDateStart();
		String strDeliveryOrderInputDateStart = DataFormat.getDateString(deliveryOrderInputDateStart);
		
		Timestamp deliveryOrderInputDateEnd = queryParam.getDeliveryOrderInputDateEnd();
		String strDeliveryOrderInputDateEnd = DataFormat.getDateString(deliveryOrderInputDateEnd);

		//条件3：业务类型ID
		long businessTypeId              = queryParam.getBusinessTypeId();		
		//条件4：交易类型ID
		String[] transactionTypeIds     = queryParam.getTransactionTypeIds();		
		//条件5：业务单位
		long clientId = queryParam.getClientId();
		//条件6：股东帐户
		String[] stockHolderAccountIds = queryParam.getStockHolderAccountIds();		
		//条件7：交易对手id
		long counterPartId = queryParam.getCounterPartId();		
		//条件8：开户营业部
		long bankOfDepositId = queryParam.getBourseCounterPartId();
		//条件9：资金账号
		String[] accountIds = queryParam.getAccountIds();
		//条件10：证券名称
		String[] securitiesIds = queryParam.getSecuritiesIds();
		
		if(securitiesMenuId == 1)//报表1：资金拆借业务
		{
				sbFrom.append("  select   \n");									
				sbFrom.append("   SEC_TRANSACTIONTYPE.BUSINESSTYPEID, --业务类型(ID)  \n");
				sbFrom.append("   SEC_DELIVERYORDER.TRANSACTIONTYPEID,--交易类型(ID)  \n");
				sbFrom.append("   SEC_DELIVERYORDER.TRANSACTIONDATE,--成交日期  \n");
				sbFrom.append("   SEC_DELIVERYORDER.CLIENTID,--业务单位(ID)  \n");
				sbFrom.append("   SEC_DELIVERYORDER.COUNTERPARTID,--交易对手(ID)  \n");
				sbFrom.append("   SEC_DELIVERYORDER.DELIVERYDATE VALUEDATE,--起息日  \n");
				sbFrom.append("   SEC_DELIVERYORDER.MATURITYDATE,--还款日 \n");
				sbFrom.append("   SEC_DELIVERYORDER.AMOUNT,--拆借金额  \n");
				sbFrom.append("   SEC_DELIVERYORDER.RATE,--拆借利率  \n");
				sbFrom.append("   SEC_DELIVERYORDER.TERM,--拆借期限  \n");
				
				sbFrom.append("   SEC_DELIVERYORDER.SUSPENSEINTEREST,--应计利息  \n");
				sbFrom.append("   SEC_DELIVERYORDER.MATURITYAMOUNT,--到期还款金额  \n");
				sbFrom.append("   SEC_DELIVERYORDER.INTEREST,--实计利息  \n");
				sbFrom.append("   SEC_DELIVERYORDER.TAX,--税费  \n");
				sbFrom.append("   SEC_DELIVERYORDER.NETINCOME,--实际收付  \n");
				sbFrom.append("   SEC_DELIVERYORDER.STATUSID--状态  \n");
				sbFrom.append("   from \n");
				sbFrom.append("   SEC_TRANSACTIONTYPE,SEC_ACCOUNT,SEC_DELIVERYORDER,SEC_STOCKHOLDERACCOUNT,SEC_COUNTERPART\n");
				sbFrom.append("   where SEC_DELIVERYORDER.TRANSACTIONTYPEID=SEC_TRANSACTIONTYPE.ID \n");
				sbFrom.append("   and  SEC_STOCKHOLDERACCOUNT.ID(+)=SEC_ACCOUNT.STOCKHOLDERACCOUNTID1 \n");
				sbFrom.append("   and SEC_DELIVERYORDER.ACCOUNTID=SEC_ACCOUNT.ID(+)\n");
				sbFrom.append("   and SEC_DELIVERYORDER.COUNTERPARTID = SEC_COUNTERPART.ID(+) \n");
				
				sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (13)  \n");
				
				getRemainderStrOnetoEight(
					strNoticeInputDateStart,
					strNoticeInputDateEnd,
					strDeliveryOrderInputDateStart,
					strDeliveryOrderInputDateEnd,
					businessTypeId,
					transactionTypeIds,
					clientId,
					stockHolderAccountIds,
					counterPartId,
					bankOfDepositId,
					accountIds,
					securitiesIds); 
				sbFrom.append( " order by  SEC_DELIVERYORDER.SECURITIESID,SEC_DELIVERYORDER.TRANSACTIONDATE  \n");
		}
		
		if(securitiesMenuId >= 2 && securitiesMenuId <= 6)//报表2：股票/封闭式基金/企业债/可转债/其它业务
		{
			sbFrom.append("  select   \n");									
			sbFrom.append("   SEC_TRANSACTIONTYPE.BUSINESSTYPEID, --业务类型(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TRANSACTIONTYPEID,--交易类型(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TRANSACTIONDATE,--成交日期  \n");
			sbFrom.append("   SEC_DELIVERYORDER.CLIENTID,--业务单位(ID)  \n");
			sbFrom.append("   SEC_STOCKHOLDERACCOUNT.id   STOCKHOLDERACCOUNTID,--股东帐户名称  \n");
			sbFrom.append("   decode(SEC_COUNTERPART.ISBANKOFDEPOSIT,1,'') COUNTERPARTID,--交易对手(ID)  \n");
			sbFrom.append("   decode(SEC_COUNTERPART.ISBANKOFDEPOSIT,1,SEC_COUNTERPART.ID)  BANKOFDEPOSITID,--开户营业部名称  \n");
			
			sbFrom.append("   SEC_DELIVERYORDER.ACCOUNTID,--资金帐号(ID) \n");
			sbFrom.append("   SEC_DELIVERYORDER.SECURITIESID,--证券(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.PRICE,--成交价格  \n");
			sbFrom.append("   SEC_DELIVERYORDER.QUANTITY,--成交数量  \n");
				
			sbFrom.append("   SEC_DELIVERYORDER.AMOUNT,--成交金额  \n");
			sbFrom.append("   SEC_DELIVERYORDER.RATE,--预付定金比例  \n");
			sbFrom.append("   SEC_DELIVERYORDER.MATURITYAMOUNT,--预付定金  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TAX,--税费  \n");
			sbFrom.append("   SEC_DELIVERYORDER.NETINCOME,--实际收付  \n");
			sbFrom.append("   SEC_DELIVERYORDER.UnitCost,--单位成本  \n");
			//对于资金划拨的业务（85），状态应该取其通知单的状态信息
			sbFrom.append("   decode(SEC_TRANSACTIONTYPE.BUSINESSTYPEID,85,SEC_NOTICEFORM.STATUSID,SEC_DELIVERYORDER.STATUSID) as STATUSID--状态  \n");
			sbFrom.append("   from \n");
			sbFrom.append("   SEC_TRANSACTIONTYPE,SEC_ACCOUNT,SEC_DELIVERYORDER,SEC_STOCKHOLDERACCOUNT,SEC_COUNTERPART,SEC_NOTICEFORM \n");
			sbFrom.append("   where SEC_DELIVERYORDER.TRANSACTIONTYPEID=SEC_TRANSACTIONTYPE.ID \n");
			sbFrom.append("   and  SEC_STOCKHOLDERACCOUNT.ID(+)=SEC_ACCOUNT.STOCKHOLDERACCOUNTID1 \n");
			sbFrom.append("   and SEC_DELIVERYORDER.ACCOUNTID=SEC_ACCOUNT.ID(+)\n");
			sbFrom.append("   and SEC_DELIVERYORDER.COUNTERPARTID = SEC_COUNTERPART.ID(+) \n");
			sbFrom.append("   and SEC_NOTICEFORM.DELIVERYORDERID(+) =  SEC_DELIVERYORDER.id \n");
			//在报表2里面确定执行哪一个业务的SQL
			if(securitiesMenuId == 2)
			{
				sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (16,17,18)  \n");
			}
			if(securitiesMenuId == 3)
			{
				sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (56,57,58)  \n");
			}
			if(securitiesMenuId == 4)
			{
				sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (46,47)  \n");
			}
			if(securitiesMenuId == 5)
			{
				sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (51,52,53)  \n");
			}
			if(securitiesMenuId == 6)//如果选择的是 其它业务
			{
				sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (85,87,91)  \n");
			}
			getRemainderStrOnetoEight(
				strNoticeInputDateStart,
				strNoticeInputDateEnd,
				strDeliveryOrderInputDateStart,
				strDeliveryOrderInputDateEnd,
				businessTypeId,
				transactionTypeIds,
				clientId,
				stockHolderAccountIds,
				counterPartId,
				bankOfDepositId,
				accountIds,
				securitiesIds);
			sbFrom.append( " order by  SEC_DELIVERYORDER.SECURITIESID,SEC_DELIVERYORDER.TRANSACTIONDATE  \n");
		}
		if(securitiesMenuId == 7)//报表3：债转股
		{
			sbFrom.append("  select   \n");									
			sbFrom.append("   SEC_TRANSACTIONTYPE.BUSINESSTYPEID, --业务类型(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TRANSACTIONTYPEID,--交易类型(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TRANSACTIONDATE,--成交日期  \n");
			sbFrom.append("   SEC_DELIVERYORDER.CLIENTID,--业务单位(ID)  \n");
			sbFrom.append("   SEC_STOCKHOLDERACCOUNT.id   STOCKHOLDERACCOUNTID,--股东帐户名称  \n");
			sbFrom.append("   decode(SEC_COUNTERPART.ISBANKOFDEPOSIT,1,'') COUNTERPARTID,--交易对手(ID)  \n");
			sbFrom.append("   decode(SEC_COUNTERPART.ISBANKOFDEPOSIT,1,SEC_COUNTERPART.ID)  BANKOFDEPOSITID,--开户营业部名称  \n");			
			sbFrom.append("   SEC_DELIVERYORDER.ACCOUNTID,--资金帐号(ID) \n");
			
			sbFrom.append("   SEC_DELIVERYORDER.SECURITIESID,--可转债名称(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.OPPOSITESECURITIESID,--转成股票名称(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.PRICE,--转股价格  \n");				
			sbFrom.append("   SEC_DELIVERYORDER.QUANTITY,--转成的债券数量  \n");
			sbFrom.append("   SEC_DELIVERYORDER.OPPOSITEQUANTITY,--转成的股票数量  \n");
			sbFrom.append("   SEC_DELIVERYORDER.AMOUNT,--转股金额  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TAX,--税费  \n");
			sbFrom.append("   SEC_DELIVERYORDER.NETINCOME,--不足一股的剩余收入  \n");
			sbFrom.append("   SEC_DELIVERYORDER.STATUSID--状态  \n");
			sbFrom.append("   from \n");
			sbFrom.append("   SEC_TRANSACTIONTYPE,SEC_ACCOUNT,SEC_DELIVERYORDER,SEC_STOCKHOLDERACCOUNT,SEC_COUNTERPART \n");
			sbFrom.append("   where SEC_DELIVERYORDER.TRANSACTIONTYPEID=SEC_TRANSACTIONTYPE.ID \n");
			sbFrom.append("   and  SEC_STOCKHOLDERACCOUNT.ID(+)=SEC_ACCOUNT.STOCKHOLDERACCOUNTID1 \n");
			sbFrom.append("   and SEC_DELIVERYORDER.ACCOUNTID=SEC_ACCOUNT.ID(+)\n");
			sbFrom.append("   and SEC_DELIVERYORDER.COUNTERPARTID = SEC_COUNTERPART.ID(+) \n");
			sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (54)  \n");
				
			getRemainderStrOnetoEight(
				strNoticeInputDateStart,
				strNoticeInputDateEnd,
				strDeliveryOrderInputDateStart,
				strDeliveryOrderInputDateEnd,
				businessTypeId,
				transactionTypeIds,
				clientId,
				stockHolderAccountIds,
				counterPartId,
				bankOfDepositId,
				accountIds,
				securitiesIds);
			sbFrom.append( " order by  SEC_DELIVERYORDER.SECURITIESID,SEC_DELIVERYORDER.TRANSACTIONDATE  \n");
		}
		if(securitiesMenuId == 8)//报表4：交易所国债
		{
			sbFrom.append("  select   \n");									
			sbFrom.append("   SEC_TRANSACTIONTYPE.BUSINESSTYPEID, --业务类型(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TRANSACTIONTYPEID,--交易类型(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TRANSACTIONDATE,--成交日期  \n");
			sbFrom.append("   SEC_DELIVERYORDER.CLIENTID,--业务单位(ID)  \n");
			sbFrom.append("   SEC_STOCKHOLDERACCOUNT.id   STOCKHOLDERACCOUNTID,--股东帐户名称  \n");
			sbFrom.append("   decode(SEC_COUNTERPART.ISBANKOFDEPOSIT,1,'') COUNTERPARTID,--交易对手(ID)  \n");
			sbFrom.append("   decode(SEC_COUNTERPART.ISBANKOFDEPOSIT,1,SEC_COUNTERPART.ID)  BANKOFDEPOSITID,--开户营业部名称  \n");
			
			sbFrom.append("   SEC_DELIVERYORDER.ACCOUNTID,--资金帐号(ID) \n");
			sbFrom.append("   SEC_DELIVERYORDER.SECURITIESID,--证券(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.NETPRICE,--净价  \n");
			sbFrom.append("   SEC_DELIVERYORDER.PRICE,--全价  \n");
				
			sbFrom.append("   SEC_DELIVERYORDER.SUSPENSEINTEREST,--应计利息  \n");
			sbFrom.append("   SEC_DELIVERYORDER.QUANTITY,--成交数量  \n");
			sbFrom.append("   SEC_DELIVERYORDER.AMOUNT,--成交金额  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TAX,--税费  \n");
			sbFrom.append("   SEC_DELIVERYORDER.NETINCOME,--实际收付  \n");
			sbFrom.append("   SEC_DELIVERYORDER.UnitCost,--单位成本  \n");
			sbFrom.append("   SEC_DELIVERYORDER.STATUSID--状态  \n");
			sbFrom.append("   from \n");
			sbFrom.append("   SEC_TRANSACTIONTYPE,SEC_ACCOUNT,SEC_DELIVERYORDER,SEC_STOCKHOLDERACCOUNT,SEC_COUNTERPART \n");
			sbFrom.append("   where SEC_DELIVERYORDER.TRANSACTIONTYPEID=SEC_TRANSACTIONTYPE.ID \n");
			sbFrom.append("   and  SEC_STOCKHOLDERACCOUNT.ID(+)=SEC_ACCOUNT.STOCKHOLDERACCOUNTID1 \n");
			sbFrom.append("   and SEC_DELIVERYORDER.ACCOUNTID=SEC_ACCOUNT.ID(+)\n");
			sbFrom.append("   and SEC_DELIVERYORDER.COUNTERPARTID = SEC_COUNTERPART.ID(+) \n");
			sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (33,34)  \n");
				
			getRemainderStrOnetoEight(
				strNoticeInputDateStart,
				strNoticeInputDateEnd,
				strDeliveryOrderInputDateStart,
				strDeliveryOrderInputDateEnd,
				businessTypeId,
				transactionTypeIds,
				clientId,
				stockHolderAccountIds,
				counterPartId,
				bankOfDepositId,
				accountIds,
				securitiesIds);
			sbFrom.append( " order by  SEC_DELIVERYORDER.SECURITIESID,SEC_DELIVERYORDER.TRANSACTIONDATE  \n");
		}
		if(securitiesMenuId == 9)//报表5：交易所债券回购
		{
			sbFrom.append("  select   \n");									
			sbFrom.append("   SEC_TRANSACTIONTYPE.BUSINESSTYPEID, --业务类型(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TRANSACTIONTYPEID,--交易类型(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TRANSACTIONDATE,--成交日期  \n");
			sbFrom.append("   SEC_DELIVERYORDER.CLIENTID,--业务单位(ID)  \n");
			sbFrom.append("   SEC_STOCKHOLDERACCOUNT.id   STOCKHOLDERACCOUNTID,--股东帐户名称  \n");
			sbFrom.append("   decode(SEC_COUNTERPART.ISBANKOFDEPOSIT,1,'') COUNTERPARTID,--交易对手(ID)  \n");
			sbFrom.append("   decode(SEC_COUNTERPART.ISBANKOFDEPOSIT,1,SEC_COUNTERPART.ID)  BANKOFDEPOSITID,--开户营业部名称  \n");
			
			sbFrom.append("   SEC_DELIVERYORDER.ACCOUNTID,--资金帐号(ID) \n");
			sbFrom.append("   SEC_DELIVERYORDER.SECURITIESID,--证券(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.MATURITYDATE,--购回日期  \n");
			sbFrom.append("   SEC_DELIVERYORDER.AMOUNT,--成交金额  \n");
				
			sbFrom.append("   SEC_DELIVERYORDER.QUANTITY,--成交数量  \n");
			sbFrom.append("   SEC_DELIVERYORDER.PLEDGESECURITIESQUANTITY,--抵押标准券  \n");
			sbFrom.append("   SEC_DELIVERYORDER.PRICE,--成交价格  \n");
			sbFrom.append("   SEC_DELIVERYORDER.MATURITYAMOUNT,--购回金额  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TAX,--税费  \n");
			sbFrom.append("   SEC_DELIVERYORDER.NETINCOME,--实际收付  \n");
			sbFrom.append("   SEC_DELIVERYORDER.STATUSID--状态  \n");
			sbFrom.append("   from \n");
			sbFrom.append("   SEC_TRANSACTIONTYPE,SEC_ACCOUNT,SEC_DELIVERYORDER,SEC_STOCKHOLDERACCOUNT,SEC_COUNTERPART \n");
			sbFrom.append("   where SEC_DELIVERYORDER.TRANSACTIONTYPEID=SEC_TRANSACTIONTYPE.ID \n");
			sbFrom.append("   and  SEC_STOCKHOLDERACCOUNT.ID(+)=SEC_ACCOUNT.STOCKHOLDERACCOUNTID1 \n");
			sbFrom.append("   and SEC_DELIVERYORDER.ACCOUNTID=SEC_ACCOUNT.ID(+)\n");
			sbFrom.append("   and SEC_DELIVERYORDER.COUNTERPARTID = SEC_COUNTERPART.ID(+) \n");
			sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (27)  \n");
				
			getRemainderStrOnetoEight(
				strNoticeInputDateStart,
				strNoticeInputDateEnd,
				strDeliveryOrderInputDateStart,
				strDeliveryOrderInputDateEnd,
				businessTypeId,
				transactionTypeIds,
				clientId,
				stockHolderAccountIds,
				counterPartId,
				bankOfDepositId,
				accountIds,
				securitiesIds);
			sbFrom.append( " order by  SEC_DELIVERYORDER.SECURITIESID,SEC_DELIVERYORDER.TRANSACTIONDATE  \n");
		}
		if(securitiesMenuId >= 10 && securitiesMenuId <= 13)//报表6：银行间国债/央行票据/金融债/政策性金融债
		{
			sbFrom.append("  select   \n");									
			sbFrom.append("   SEC_TRANSACTIONTYPE.BUSINESSTYPEID, --业务类型(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TRANSACTIONTYPEID,--交易类型(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TRANSACTIONDATE,--成交日期  \n");
			sbFrom.append("   SEC_DELIVERYORDER.CLIENTID,--业务单位(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.COUNTERPARTID,--交易对手(ID)  \n");

			sbFrom.append("   SEC_DELIVERYORDER.SECURITIESID,--证券(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.DELIVERYDATE,--结算日  \n");
			sbFrom.append("   SEC_DELIVERYORDER.PRICE,--全价  \n");
				
			sbFrom.append("   SEC_DELIVERYORDER.SUSPENSEINTEREST,--应计利息  \n");
			sbFrom.append("   SEC_DELIVERYORDER.NETPRICE,--净价  \n");
			sbFrom.append("   SEC_DELIVERYORDER.QUANTITY,--成交数量  \n");
			sbFrom.append("   SEC_DELIVERYORDER.PLEDGESECURITIESAMOUNT,--券面总额  \n");
			sbFrom.append("   SEC_DELIVERYORDER.AMOUNT,--全价金额  \n");
			sbFrom.append("   SEC_DELIVERYORDER.NETPRICEAMOUNT,--净价金额  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TAX,--税费  \n");
			sbFrom.append("   SEC_DELIVERYORDER.NETINCOME,--实际收付  \n");
			sbFrom.append("   SEC_DELIVERYORDER.UnitNetCost,--净价单位成本  \n");
			sbFrom.append("   SEC_DELIVERYORDER.UnitCost,--全价单位成本  \n");
			sbFrom.append("   SEC_DELIVERYORDER.STATUSID--状态  \n");
			sbFrom.append("   from \n");
			sbFrom.append("   SEC_TRANSACTIONTYPE,SEC_ACCOUNT,SEC_DELIVERYORDER,SEC_STOCKHOLDERACCOUNT,SEC_COUNTERPART \n");
			sbFrom.append("   where SEC_DELIVERYORDER.TRANSACTIONTYPEID=SEC_TRANSACTIONTYPE.ID \n");
			sbFrom.append("   and  SEC_STOCKHOLDERACCOUNT.ID(+)=SEC_ACCOUNT.STOCKHOLDERACCOUNTID1 \n");
			sbFrom.append("   and SEC_DELIVERYORDER.ACCOUNTID=SEC_ACCOUNT.ID(+)\n");
			sbFrom.append("   and SEC_DELIVERYORDER.COUNTERPARTID = SEC_COUNTERPART.ID(+) \n");
			if(securitiesMenuId == 10)
			{
				sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (31,32)  \n");
			}
			if(securitiesMenuId == 11)
			{
				sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (21,22)  \n");
			}
			if(securitiesMenuId == 12)
			{
				sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (36,37)  \n");
			}
			if(securitiesMenuId == 13)
			{
				sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (41,42)  \n");
			}					
			getRemainderStrOnetoEight(
				strNoticeInputDateStart,
				strNoticeInputDateEnd,
				strDeliveryOrderInputDateStart,
				strDeliveryOrderInputDateEnd,
				businessTypeId,
				transactionTypeIds,
				clientId,
				stockHolderAccountIds,
				counterPartId,
				bankOfDepositId,
				accountIds,
				securitiesIds);
			sbFrom.append( " order by  SEC_DELIVERYORDER.SECURITIESID,SEC_DELIVERYORDER.TRANSACTIONDATE  \n");
		}
		if(securitiesMenuId == 14)//报表7：银行间债券回购
		{
			sbFrom.append("  select   \n");									
			sbFrom.append("   SEC_TRANSACTIONTYPE.BUSINESSTYPEID, --业务类型(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TRANSACTIONTYPEID,--交易类型(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TRANSACTIONDATE,--成交日期  \n");
			sbFrom.append("   SEC_DELIVERYORDER.CLIENTID,--业务单位(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.COUNTERPARTID,--交易对手(ID)  \n");

			sbFrom.append("   SEC_DELIVERYORDER.DELIVERYDATE,--首次结算日  \n");
			sbFrom.append("   SEC_DELIVERYORDER.MATURITYDATE,--到期结算日  \n");
			sbFrom.append("   SEC_DELIVERYORDER.PLEDGESECURITIESID,--抵押债券名称(ID)  \n");
				
			sbFrom.append("   SEC_DELIVERYORDER.RATE,--回购利率  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TERM,--回购期限  \n");
			sbFrom.append("   SEC_DELIVERYORDER.PLEDGESECURITIESAMOUNT,--券面总额  \n");
			sbFrom.append("   SEC_DELIVERYORDER.PLEDGESECURITIESQUANTITY,--抵押债券数量  \n");
			sbFrom.append("   SEC_DELIVERYORDER.PLEDGERATE,--折算比例  \n");
			sbFrom.append("   SEC_DELIVERYORDER.AMOUNT,--成交金额  \n");
			sbFrom.append("   SEC_DELIVERYORDER.MATURITYAMOUNT,--到期还款金额  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TAX,--税费  \n");
			sbFrom.append("   SEC_DELIVERYORDER.NETINCOME,--实际收付  \n");
			sbFrom.append("   SEC_DELIVERYORDER.STATUSID--状态  \n");
			sbFrom.append("   from \n");
			sbFrom.append("   SEC_TRANSACTIONTYPE,SEC_ACCOUNT,SEC_DELIVERYORDER,SEC_STOCKHOLDERACCOUNT,SEC_COUNTERPART \n");
			sbFrom.append("   where SEC_DELIVERYORDER.TRANSACTIONTYPEID=SEC_TRANSACTIONTYPE.ID \n");
			sbFrom.append("   and  SEC_STOCKHOLDERACCOUNT.ID(+)=SEC_ACCOUNT.STOCKHOLDERACCOUNTID1 \n");
			sbFrom.append("   and SEC_DELIVERYORDER.ACCOUNTID=SEC_ACCOUNT.ID(+)\n");
			sbFrom.append("   and SEC_DELIVERYORDER.COUNTERPARTID = SEC_COUNTERPART.ID(+) \n");
			sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (26)  \n");
				
			getRemainderStrOnetoEight(
				strNoticeInputDateStart,
				strNoticeInputDateEnd,
				strDeliveryOrderInputDateStart,
				strDeliveryOrderInputDateEnd,
				businessTypeId,
				transactionTypeIds,
				clientId,
				stockHolderAccountIds,
				counterPartId,
				bankOfDepositId,
				accountIds,
				securitiesIds);
			sbFrom.append( " order by  SEC_DELIVERYORDER.SECURITIESID,SEC_DELIVERYORDER.TRANSACTIONDATE  \n");
		}
		if(securitiesMenuId == 15)//报表8：开放式基金
		{
			sbFrom.append("  select   \n");									
			sbFrom.append("   SEC_TRANSACTIONTYPE.BUSINESSTYPEID, --业务类型(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TRANSACTIONTYPEID,--交易类型(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TRANSACTIONDATE,--成交日期  \n");
			sbFrom.append("   SEC_DELIVERYORDER.CLIENTID,--业务单位(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.COUNTERPARTID,--交易对手(ID)  \n");

			sbFrom.append("   SEC_DELIVERYORDER.ACCOUNTID,--交易帐号(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.DELIVERYDATE,--结算日  \n");
			sbFrom.append("   SEC_DELIVERYORDER.SECURITIESID,--证券名称(ID)  \n");
				
			sbFrom.append("   SEC_DELIVERYORDER.AMOUNT,--金额  \n");
			sbFrom.append("   SEC_DELIVERYORDER.NETPRICE,--净值  \n");
			sbFrom.append("   SEC_DELIVERYORDER.QUANTITY,--份额  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TAX,--税费  \n");
			sbFrom.append("   SEC_DELIVERYORDER.NETINCOME,--实际收付  \n");
			sbFrom.append("   SEC_DELIVERYORDER.UnitCost,--单位成本  \n");
			sbFrom.append("   SEC_DELIVERYORDER.STATUSID--状态  \n");
			sbFrom.append("   from \n");
			sbFrom.append("   SEC_TRANSACTIONTYPE,SEC_ACCOUNT,SEC_DELIVERYORDER,SEC_STOCKHOLDERACCOUNT,SEC_COUNTERPART \n");
			sbFrom.append("   where SEC_DELIVERYORDER.TRANSACTIONTYPEID=SEC_TRANSACTIONTYPE.ID \n");
			sbFrom.append("   and  SEC_STOCKHOLDERACCOUNT.ID(+)=SEC_ACCOUNT.STOCKHOLDERACCOUNTID1 \n");
			sbFrom.append("   and SEC_DELIVERYORDER.ACCOUNTID=SEC_ACCOUNT.ID(+)\n");
			sbFrom.append("   and SEC_DELIVERYORDER.COUNTERPARTID = SEC_COUNTERPART.ID(+) \n");
			sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (61,62,63,64) \n");
				
			getRemainderStrOnetoEight(
				strNoticeInputDateStart,
				strNoticeInputDateEnd,
				strDeliveryOrderInputDateStart,
				strDeliveryOrderInputDateEnd,
				businessTypeId,
				transactionTypeIds,
				clientId,
				stockHolderAccountIds,
				counterPartId,
				bankOfDepositId,
				accountIds,
				securitiesIds);
			sbFrom.append( " order by  SEC_DELIVERYORDER.SECURITIESID,SEC_DELIVERYORDER.TRANSACTIONDATE  \n");
		}
		if(securitiesMenuId == 16)//报表9：资产回购
		{
			sbFrom.append("  select   \n");									
			sbFrom.append("   SEC_TRANSACTIONTYPE.BUSINESSTYPEID, --业务类型(ID)  \n");
			sbFrom.append("   SEC_NOTICEFORM.TRANSACTIONTYPEID,--交易类型(ID)  \n");
			sbFrom.append("   SEC_NOTICEFORM.CODE as NOTICEFORMCODE,--业务通知单(CODE)  \n");
			sbFrom.append("   SEC_APPLYCONTRACT.COUNTERPARTID,--交易对手名称  \n");
			sbFrom.append("   SEC_APPLYCONTRACT.AMOUNT,--回购金额  \n");
			sbFrom.append("   SEC_ApplyContract.TransactionStartDate,--资产回购开始日  \n");
			sbFrom.append("   SEC_ApplyContract.TransactionEndDate,--资产回购结束日  \n");
			sbFrom.append("   SEC_APPLYCONTRACT.RATE,--利率  \n");
			sbFrom.append("   SEC_APPLYCONTRACT.SETTLEMENTTYPEID,--结息方式(ID)  \n");				
			sbFrom.append("   SEC_NOTICEFORM.EXECUTEDATE,--交易执行日  \n");
			sbFrom.append("   SEC_NOTICEFORM.NOTICEAMOUNT,--本金金额  \n");
			sbFrom.append("   SEC_NOTICEFORM.NOTICEINTEREST,--实付利息  \n");
			sbFrom.append("   SEC_NOTICEFORM.STATUSID--状态  \n");
			sbFrom.append("   from \n");
			sbFrom.append("   SEC_NOTICEFORM,SEC_APPLYCONTRACT,SEC_APPLYFORM,SEC_TRANSACTIONTYPE,SEC_ACCOUNT,SEC_STOCKHOLDERACCOUNT \n");
			sbFrom.append("   where SEC_NOTICEFORM.CONTRACTID = SEC_APPLYCONTRACT.ID(+) \n");
			sbFrom.append("   and  SEC_APPLYCONTRACT.APPLYID = SEC_APPLYFORM.ID(+) \n");
			sbFrom.append("   and SEC_NOTICEFORM.TRANSACTIONTYPEID = SEC_TRANSACTIONTYPE.ID\n");
			sbFrom.append("   and  SEC_STOCKHOLDERACCOUNT.ID(+) = SEC_ACCOUNT.STOCKHOLDERACCOUNTID1\n");
			sbFrom.append("   and  SEC_ApplyContract.ACCOUNTID = SEC_ACCOUNT.ID(+)\n");
			sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (71)  \n");
				
			getRemainderStrNinetoTwelve(
				strNoticeInputDateStart,
				strNoticeInputDateEnd,
				businessTypeId,
				transactionTypeIds,
				clientId,
				stockHolderAccountIds,
				counterPartId,
				bankOfDepositId,
				accountIds,
				securitiesIds);
		}
		if(securitiesMenuId == 17)//报表10：委托理财
		{
			sbFrom.append("  select   \n");									
			sbFrom.append("   SEC_TRANSACTIONTYPE.BUSINESSTYPEID, --业务类型(ID)  \n");
			sbFrom.append("   SEC_NOTICEFORM.TRANSACTIONTYPEID,--交易类型(ID)  \n");
			sbFrom.append("   SEC_NOTICEFORM.CODE as NOTICEFORMCODE,--业务通知单(CODE)  \n");
			sbFrom.append("   SEC_APPLYCONTRACT.COUNTERPARTID,--交易对手名称  \n");
			sbFrom.append("   SEC_APPLYCONTRACT.AMOUNT,--委托金额  \n");
			sbFrom.append("   SEC_ApplyContract.TransactionDate as TRANSACTIONSTARTDATE,--委托理财开始日  \n");
			sbFrom.append("   SEC_ApplyContract.TransactionEndDate,--委托理财结束日  \n");
			sbFrom.append("   SEC_APPLYCONTRACT.RATE,--收益率  \n");
			sbFrom.append("   SEC_APPLYCONTRACT.SETTLEMENTTYPEID,--结息方式(ID)  \n");				
			sbFrom.append("   SEC_NOTICEFORM.EXECUTEDATE,--交易执行日  \n");
			sbFrom.append("   SEC_NOTICEFORM.NOTICEAMOUNT,--本金金额  \n");
			sbFrom.append("   SEC_NOTICEFORM.NOTICEINTEREST,--实收收益金额  \n");
			sbFrom.append("   SEC_NOTICEFORM.STATUSID--状态  \n");
			sbFrom.append("   from \n");
			sbFrom.append("   SEC_NOTICEFORM,SEC_APPLYCONTRACT,SEC_APPLYFORM,SEC_TRANSACTIONTYPE,SEC_ACCOUNT,SEC_STOCKHOLDERACCOUNT \n");
			sbFrom.append("   where SEC_NOTICEFORM.CONTRACTID = SEC_APPLYCONTRACT.ID(+) \n");
			sbFrom.append("   and  SEC_APPLYCONTRACT.APPLYID = SEC_APPLYFORM.ID(+) \n");
			sbFrom.append("   and SEC_NOTICEFORM.TRANSACTIONTYPEID = SEC_TRANSACTIONTYPE.ID\n");
			sbFrom.append("   and  SEC_STOCKHOLDERACCOUNT.ID(+) = SEC_ACCOUNT.STOCKHOLDERACCOUNTID1\n");
			sbFrom.append("   and  SEC_ApplyContract.ACCOUNTID = SEC_ACCOUNT.ID(+)\n");
			sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (73,75)  \n");
				
			getRemainderStrNinetoTwelve(
				strNoticeInputDateStart,
				strNoticeInputDateEnd,
				businessTypeId,
				transactionTypeIds,
				clientId,
				stockHolderAccountIds,
				counterPartId,
				bankOfDepositId,
				accountIds,
				securitiesIds);
		}
		if(securitiesMenuId == 18)//报表11：结构性投资
		{
			sbFrom.append("  select   \n");									
			sbFrom.append("   SEC_TRANSACTIONTYPE.BUSINESSTYPEID, --业务类型(ID)  \n");
			sbFrom.append("   SEC_NOTICEFORM.TRANSACTIONTYPEID,--交易类型(ID)  \n");
			sbFrom.append("   SEC_NOTICEFORM.CODE as NOTICEFORMCODE,--业务通知单(CODE)  \n");
			sbFrom.append("   SEC_APPLYCONTRACT.COUNTERPARTID,--交易对手名称  \n");
			sbFrom.append("   SEC_APPLYCONTRACT.AMOUNT,--存款金额  \n");
			sbFrom.append("   SEC_APPLYCONTRACT.CURRENCYID,--币种(ID)  \n");
			sbFrom.append("   SEC_ApplyContract.TransactionStartDate,--合同生效日开始日  \n");
			sbFrom.append("   SEC_ApplyContract.TransactionEndDate,--合同生效日结束日  \n");
			sbFrom.append("   SEC_APPLYCONTRACT.RATE,--收益率  \n");
			sbFrom.append("   SEC_APPLYCONTRACT.SETTLEMENTTYPEID,--结息方式(ID)  \n");				
			sbFrom.append("   SEC_NOTICEFORM.EXECUTEDATE,--交易执行日  \n");
			sbFrom.append("   SEC_NOTICEFORM.NOTICEAMOUNT,--本金金额  \n");
			sbFrom.append("   SEC_NOTICEFORM.NOTICEINTEREST,--实收收益金额  \n");
			sbFrom.append("   SEC_NOTICEFORM.STATUSID--状态  \n");
			sbFrom.append("   from \n");
			sbFrom.append("   SEC_NOTICEFORM,SEC_APPLYCONTRACT,SEC_APPLYFORM,SEC_TRANSACTIONTYPE,SEC_ACCOUNT,SEC_STOCKHOLDERACCOUNT \n");
			sbFrom.append("   where SEC_NOTICEFORM.CONTRACTID = SEC_APPLYCONTRACT.ID(+) \n");
			sbFrom.append("   and  SEC_APPLYCONTRACT.APPLYID = SEC_APPLYFORM.ID(+) \n");
			sbFrom.append("   and SEC_NOTICEFORM.TRANSACTIONTYPEID = SEC_TRANSACTIONTYPE.ID\n");
			sbFrom.append("   and  SEC_STOCKHOLDERACCOUNT.ID(+) = SEC_ACCOUNT.STOCKHOLDERACCOUNTID1\n");
			sbFrom.append("   and  SEC_ApplyContract.ACCOUNTID = SEC_ACCOUNT.ID(+)\n");
			sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (77) \n ");
				
			getRemainderStrNinetoTwelve(
				strNoticeInputDateStart,
				strNoticeInputDateEnd,
				businessTypeId,
				transactionTypeIds,
				clientId,
				stockHolderAccountIds,
				counterPartId,
				bankOfDepositId,
				accountIds,
				securitiesIds);
		}
		if(securitiesMenuId == 19)//报表12：债券承销
		{
			sbFrom.append("  select   \n");									
			sbFrom.append("   SEC_TRANSACTIONTYPE.BUSINESSTYPEID, --业务类型(ID)  \n");
			sbFrom.append("   SEC_NOTICEFORM.TRANSACTIONTYPEID,--交易类型(ID)  \n");
			sbFrom.append("   SEC_NOTICEFORM.CODE as NOTICEFORMCODE,--业务通知单(CODE)  \n");
			sbFrom.append("   SEC_APPLYCONTRACT.COUNTERPARTID,--交易对手名称  \n");
			sbFrom.append("   SEC_ApplyForm.SECURITIESID,--债券(ID)  \n");
			sbFrom.append("   SEC_APPLYCONTRACT.AMOUNT,--承销金额  \n");
			sbFrom.append("   SEC_APPLYCONTRACT.INTERESTTYPEID,--承销方式ID  \n");
			sbFrom.append("   SEC_APPLYCONTRACT.COMMISSIONCHARGERATE,--手续费率 \n");
			sbFrom.append("   SEC_NOTICEFORM.NOTICEAMOUNT,--本金金额  \n");
			sbFrom.append("   SEC_NOTICEFORM.NOTICEINTEREST,--实付利息  \n");
			sbFrom.append("   SEC_NOTICEFORM.EXECUTEDATE,--交易执行日  \n");
			sbFrom.append("   SEC_NOTICEFORM.STATUSID--状态  \n");
			sbFrom.append("   from \n");
			sbFrom.append("   SEC_NOTICEFORM,SEC_APPLYCONTRACT,SEC_APPLYFORM,SEC_TRANSACTIONTYPE,SEC_ACCOUNT,SEC_STOCKHOLDERACCOUNT \n");
			sbFrom.append("   where SEC_NOTICEFORM.CONTRACTID = SEC_APPLYCONTRACT.ID(+) \n");
			sbFrom.append("   and  SEC_APPLYCONTRACT.APPLYID = SEC_APPLYFORM.ID(+) \n");
			sbFrom.append("   and SEC_NOTICEFORM.TRANSACTIONTYPEID = SEC_TRANSACTIONTYPE.ID\n");
			sbFrom.append("   and  SEC_STOCKHOLDERACCOUNT.ID(+) = SEC_ACCOUNT.STOCKHOLDERACCOUNTID1\n");
			sbFrom.append("   and  SEC_ApplyContract.ACCOUNTID = SEC_ACCOUNT.ID(+)\n");
			sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (81)  \n");
				
			getRemainderStrNinetoTwelve(
				strNoticeInputDateStart,
				strNoticeInputDateEnd,
				businessTypeId,
				transactionTypeIds,
				clientId,
				stockHolderAccountIds,
				counterPartId,
				bankOfDepositId,
				accountIds,
				securitiesIds);
		}


		
		
		//sbFrom拼写结束！！！！！！！！！！
		
		
		
		
		sbFrom.append("   )  \n");
		
		

	}




	//前8个报表SQL后面的str
	private void getRemainderStrOnetoEight(
		String strNoticeInputDateStart,
		String strNoticeInputDateEnd,
		String strDeliveryOrderInputDateStart,
		String strDeliveryOrderInputDateEnd,
		long businessTypeId,
		String[] transactionTypeIds,
		long clientId,
		String[] stockHolderAccountIds,
		long counterPartId,
		long bankOfDepositId,
		String[] accountIds,
		String[] securitiesIds) {

		//条件1：日期开始日 
		if(!"".equals(strNoticeInputDateStart))
		{
			sbFrom.append(
				" and to_char(SEC_DELIVERYORDER.TRANSACTIONDATE , 'yyyy-mm-dd') >= " + "'" + strNoticeInputDateStart + "' \n");
		}
		//条件2：日期结束日 
		if(!"".equals(strNoticeInputDateEnd))
		{
			sbFrom.append(
				"and to_char(SEC_DELIVERYORDER.TRANSACTIONDATE , 'yyyy-mm-dd') <= " + "'" + strNoticeInputDateEnd + "' \n");
		}
//		条件1：日期开始日 
		if(!"".equals(strDeliveryOrderInputDateStart))
		{
			sbFrom.append(
				" and to_char(SEC_DELIVERYORDER.INPUTDATE , 'yyyy-mm-dd') >= " + "'" + strDeliveryOrderInputDateStart + "' \n");
		}
		//条件2：日期结束日 
		if(!"".equals(strDeliveryOrderInputDateEnd))
		{
			sbFrom.append(
				"and to_char(SEC_DELIVERYORDER.INPUTDATE , 'yyyy-mm-dd') <= " + "'" + strDeliveryOrderInputDateEnd + "' \n");
		}
		//条件3：业务类型ID
		if(businessTypeId!=-1)
		{
			sbFrom.append( " and \n ");
			sbFrom.append( " SEC_TRANSACTIONTYPE.BUSINESSTYPEID = "+businessTypeId);
		}
		//条件4：交易类型ID
		if (transactionTypeIds != null && transactionTypeIds.length > 0) 
		{
			sbFrom.append(" and SEC_DELIVERYORDER.TRANSACTIONTYPEID in ( ");
			for (int i = 0; i < transactionTypeIds.length - 1; i++) {
				sbFrom.append(Long.parseLong(transactionTypeIds[i]) + ",");
			}
			sbFrom.append(
				Long.parseLong(transactionTypeIds[transactionTypeIds.length - 1])
					+ ") \n");
		}
		//条件5：业务单位
		if(clientId!=-1)
		{
			sbFrom.append( " and \n");
			sbFrom.append( " SEC_DELIVERYORDER.CLIENTID = "+clientId);
		}
		//条件6：股东帐户
		if(stockHolderAccountIds !=null&&stockHolderAccountIds.length >0)
		{
			sbFrom.append( " and SEC_STOCKHOLDERACCOUNT.ID in (");
			for(int i=0;i<stockHolderAccountIds.length -1;i++)
			{
				sbFrom.append(Long.parseLong(stockHolderAccountIds[i])+" , ");
					
			}
			sbFrom.append(Long.parseLong(stockHolderAccountIds[stockHolderAccountIds.length -1])+" ) \n ");
		}
		//条件7：交易对手id,条件8：开户营业部: 如果 交易对手,开户营业部 都选择了
		if (counterPartId != -1 && bankOfDepositId != -1)
		{
			sbFrom.append( " and SEC_DELIVERYORDER.COUNTERPARTID in (" + counterPartId + "," + bankOfDepositId + ") \n ");					
		}
		//条件7：交易对手id,条件8：开户营业部: 如果只选择了 交易对手，没有选择 开户营业部			
		if (counterPartId != -1 && bankOfDepositId == -1)
		{
			sbFrom.append( " and SEC_DELIVERYORDER.COUNTERPARTID = " + counterPartId + " \n ");	
		}
		//条件7：交易对手id,条件8：开户营业部: 如果只选择了 开户营业部，没有选择 交易对手
		if (counterPartId == -1 && bankOfDepositId != -1)
		{
			sbFrom.append( " and SEC_DELIVERYORDER.COUNTERPARTID = " + bankOfDepositId + " \n ");	
		}
		//条件9：资金账号
		if (accountIds != null && accountIds.length > 0) {
			sbFrom.append(" and SEC_DELIVERYORDER.ACCOUNTID in ( ");
			for (int i = 0; i < accountIds.length - 1; i++) {
				sbFrom.append(Long.parseLong(accountIds[i]) + ",");
			}
			sbFrom.append(
				Long.parseLong(accountIds[accountIds.length - 1]) + ") \n");
		}
		//条件10：证券名称
		if (securitiesIds != null && securitiesIds.length > 0) 
		{
			sbFrom.append(" and SEC_DELIVERYORDER.SECURITIESID in ( ");
			for (int i = 0; i < securitiesIds.length - 1; i++) {
				sbFrom.append(Long.parseLong(securitiesIds[i]) + ",");
			}
			sbFrom.append(
				Long.parseLong(securitiesIds[securitiesIds.length - 1])
					+ ") \n");
		}
	}


	//后4个报表SQL后面的str
	private void getRemainderStrNinetoTwelve(
		String strNoticeInputDateStart,
		String strNoticeInputDateEnd,
		long businessTypeId,
		String[] transactionTypeIds,
		long clientId,
		String[] stockHolderAccountIds,
		long counterPartId,
		long bankOfDepositId,
		String[] accountIds,
		String[] securitiesIds) {

		//条件1：日期开始日 
		if(!"".equals(strNoticeInputDateStart))
		{
			sbFrom.append(" and \n");
			sbFrom.append(
				" to_char(SEC_NOTICEFORM.ExecuteDate , 'yyyy-mm-dd') >= " + "'" + strNoticeInputDateStart + "'");
		}
		//条件2：日期结束日 
		if(!"".equals(strNoticeInputDateEnd))
		{
			sbFrom.append(" and \n");
			sbFrom.append(
				" to_char(SEC_NOTICEFORM.ExecuteDate , 'yyyy-mm-dd') <= " + "'" + strNoticeInputDateEnd + "'");
		}
		//条件3：业务类型ID
		if(businessTypeId!=-1)
		{
			sbFrom.append( " and \n ");
			sbFrom.append( " SEC_TRANSACTIONTYPE.BUSINESSTYPEID = "+businessTypeId);
		}
		//条件4：交易类型ID
		if (transactionTypeIds != null && transactionTypeIds.length > 0) 
		{
			sbFrom.append(" and SEC_NOTICEFORM.TRANSACTIONTYPEID in ( ");
			for (int i = 0; i < transactionTypeIds.length - 1; i++) {
				sbFrom.append(Long.parseLong(transactionTypeIds[i]) + ",");
			}
			sbFrom.append(
				Long.parseLong(transactionTypeIds[transactionTypeIds.length - 1])
					+ ") \n");
		}
		//条件5：业务单位
		if(clientId!=-1)
		{
			sbFrom.append( " and \n");
			sbFrom.append( " SEC_ApplyContract.CLIENTID = "+clientId);
		}
		//条件6：股东帐户
		if(stockHolderAccountIds !=null&&stockHolderAccountIds.length >0)
		{
			sbFrom.append( " and SEC_STOCKHOLDERACCOUNT.ID in (");
			for(int i=0;i<stockHolderAccountIds.length -1;i++)
			{
				sbFrom.append(Long.parseLong(stockHolderAccountIds[i])+" , ");
					
			}
			sbFrom.append(Long.parseLong(stockHolderAccountIds[stockHolderAccountIds.length -1])+" ) \n ");
		}
		//条件7：交易对手id,条件8：开户营业部: 如果 交易对手,开户营业部 都选择了
		if (counterPartId != -1 && bankOfDepositId != -1)
		{
			sbFrom.append( " and SEC_ApplyContract.COUNTERPARTID in (" + counterPartId + "," + bankOfDepositId + ") \n ");					
		}
		//条件7：交易对手id,条件8：开户营业部: 如果只选择了 交易对手，没有选择 开户营业部			
		if (counterPartId != -1 && bankOfDepositId == -1)
		{
			sbFrom.append( " and SEC_ApplyContract.COUNTERPARTID = " + counterPartId + " \n ");	
		}
		//条件7：交易对手id,条件8：开户营业部: 如果只选择了 开户营业部，没有选择 交易对手
		if (counterPartId == -1 && bankOfDepositId != -1)
		{
			sbFrom.append( " and SEC_ApplyContract.COUNTERPARTID = " + bankOfDepositId + " \n ");	
		}
		//条件9：资金账号
		if (accountIds != null && accountIds.length > 0) {
			sbFrom.append(" and SEC_ApplyContract.ACCOUNTID in ( ");
			for (int i = 0; i < accountIds.length - 1; i++) {
				sbFrom.append(Long.parseLong(accountIds[i]) + ",");
			}
			sbFrom.append(
				Long.parseLong(accountIds[accountIds.length - 1]) + ") \n");
		}
		//条件10：证券名称
		if (securitiesIds != null && securitiesIds.length > 0) 
		{
			sbFrom.append(" and SEC_ApplyForm.SECURITIESID in ( ");
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
	public PageLoader printSecuritiesExchangeStatus(PrintSecuritiesExchangeStatusParam printParam)
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
			"com.iss.itreasury.securities.print.dataentity.PrintSecuritiesExchangeStatusInfo",
			null);
		pageLoader.setOrderBy("  ");
		return pageLoader;
	}

}