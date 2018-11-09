/*
 * Created on 2004-4-5
 * 
 * To change the template for this generated file go to Window - Preferences -
 * Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.query.bizlogic;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.query.dao.QuerySecuritiesStockDAO;

import com.iss.itreasury.securities.query.dataentity.QuerySecuritiesStockParam;
import com.iss.itreasury.securities.query.dataentity.StockQuerySumInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

/**
 * @author hjliu
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class QuerySecuritiesStockBean {

    protected Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);

    private StringBuffer sbSelect = null;

    private StringBuffer sbFrom = null;

    private StringBuffer sbWhere = null;

    private StringBuffer sbOrderBy = null;

    public final static int OrderBy_ClientName = 1;

    public final static int OrderBy_BankOfDepositName = 2;

    public final static int OrderBy_AccountName = 3;

    public final static int OrderBy_FundManagerCoName = 4;//华能用交易对手排序，国机用基金管理公司排序

    public final static int OrderBy_SecuritiesType = 5;

    public final static int OrderBy_SecuritiesName = 6;

    public final static int OrderBy_Quantity = 7;

    public final static int OrderBy_Cost = 8;

    public final static int OrderBy_NetCost = 9;

    /**
     * 返回证券查询的SQLString语句
     * 
     * @param queryParam
     * @return
     */
    private void getSQL(QuerySecuritiesStockParam queryParam) {
        sbSelect = new StringBuffer();

        sbSelect.append("   *  \n");

        sbFrom = new StringBuffer();

        sbFrom.append("   (  \n");

        long exchangeCenterId = queryParam.getExchangeCenterId(); //证券交易市场Id

        long securitiesType = queryParam.getSecuritiesType(); //证券类别

        String[] securitiesIds = queryParam.getSecuritiesIds(); //证券名称Id的数组

        long clientId = queryParam.getClientId();//业务单位ID

        String[] stockHolderAccountIds = queryParam.getStockHolderAccountIds();// 股东帐户名称Id的数组

        long bankOfDepositId = queryParam.getBankOfDepositId();//开户营业部ID

        String[] accountIds = queryParam.getAccountIds();//资金账号Id的数组

		String[] counterPartIds = queryParam.getCounterPartIds();//交易对手

        sbFrom.append("   select  DISTINCT  \n");
        sbFrom.append("   daily.stockDate as StockDate, --库存日期 \n");
        sbFrom.append("   SEC_STOCKHOLDERACCOUNT.name as stockHolderAccountName, --股东帐户名称\n");
        sbFrom.append("   client.name as ClientName, --业务单位名称 \n");
        sbFrom.append("   0 as businessAttributeID, --业务性质 \n");
        sbFrom.append("   decode(counterpart.isbankOfDeposit ,1,counterpart.name,'') as BankOfDepositName, --开户营业部名称 \n");
        sbFrom.append("   account.accountCode as AccountCode, --资金账号 \n");
        sbFrom.append("   account.accountName as AccountName, --资金账户名称 \n");
        sbFrom.append("   --account.type as AccountType, --资金账户类型：1:证券账户，2:开放式基金账户，3:银行账户 \n");
		sbFrom.append("   decode(counterpart.IsBankOfDeposit,1,-1,counterpart.Id) as CounterPartId,--交易对手 \n");
        sbFrom.append("   secType.name as SecuritiesType, --证券类型 \n");
        sbFrom.append("   securities.shortname as securitiesName , --证券名称 \n");
        sbFrom.append("   --securities.pledgeRate as SecuritiesRate, --回购抵押比率％，用于计算折成标准券 \n");
        sbFrom.append("   daily.Quantity as Quantity, --库存数量 \n");
        sbFrom.append("  (daily.Quantity * decode(securities.pledgeRate,null,0,securities.pledgeRate) /100) \n" +
        	"		as StandardQuantity, --折成标准券（如果没有回购抵押比率，则返回零）,去尾法 \n");
        sbFrom.append("   daily.frozenQuantity as FrozenQuantity, --已冻结证券数量 \n");
        sbFrom.append("   decode((daily.cost/decode(daily.Quantity,0,1,daily.Quantity)) ,null, 0 , daily.cost/decode(daily.Quantity,0,1,daily.Quantity)) as UnitCost, --单位成本 \n");
        sbFrom.append("   decode((daily.Netcost/decode(daily.Quantity,0,1,daily.Quantity)),null , 0 , daily.Netcost/decode(daily.Quantity,0,1,daily.Quantity)) as UnitNetCost, --单位成本（净价） \n");
        sbFrom.append("   daily.cost as Cost,daily.NetCost as NetCost --实际成本，实际成本（净价） \n");
        
        sbFrom.append("   from \n");

        //---------------------//证券库存日结情况表，业务单位表 股东帐户
        sbFrom.append("   sec_dailyStock daily,sec_client  client, SEC_STOCKHOLDERACCOUNT ,\n");
        //---------------------//资金账户表，证券资料表
        sbFrom.append("   sec_account account , sec_securities securities, \n");
        //---------------------//交易对手表，证券类型表
        sbFrom.append("   (select * from SEC_COUNTERPART where ISBANK is null or ISBANK = -1) counterpart ,sec_SecuritiesType secType \n");

//        sbFrom.append("   (select a.id as transactionTypeId, \n");
//        sbFrom.append("           a.name as transactionTypeName,  \n");
//        sbFrom.append("           b.Id as businessTypeId,  \n");
//        sbFrom.append("           b.name as businessTypeName, \n");
//        sbFrom.append("            c.ID as businessAttributeID \n");
//        sbFrom
//                .append("    from  SEC_TRANSACTIONTYPE a ,SEC_BUSINESSTYPE b,SEC_BUSINESSATTRIBUTE c  \n");
//        sbFrom
//                .append("    where subStr(a.Id,0,2) = b.id  and b.BUSINESSATTRIBUTEID = c.ID  \n");
//        sbFrom.append("     ) ST_Type  \n");

        sbFrom.append("   where  \n");
        
        sbFrom.append("   SEC_STOCKHOLDERACCOUNT.ID(+) = account.STOCKHOLDERACCOUNTID1 \n");
        sbFrom.append("   and \n");
        sbFrom.append("   daily.ClientID = client.id \n");
        sbFrom.append("   and \n");
        sbFrom.append("   daily.accountid = account.id  \n");
        sbFrom.append("   and \n");
        sbFrom.append("   daily.securitiesID= securities.id \n");
        sbFrom.append("   and \n");
        sbFrom.append("   account.counterpartID = counterpart.ID \n");
        sbFrom.append("   and \n");
        sbFrom.append("   securities.typeId = secType.ID \n");
//        sbFrom.append("   and  \n");
//        sbFrom.append("   ST_Type.businessAttributeID in (1,2,3)  \n"); //对应着第1种业务

        //		===========以下是根据页面传递的参数决定的条件======start====
        //条件1：查询日期
        Timestamp queryDate = queryParam.getQueryDate();
        if (queryDate != null) {
            String strQueryDate = DataFormat.getDateString(queryDate);
            sbFrom.append(" and \n");
            sbFrom.append(" daily.stockDate = to_Date('" + strQueryDate
                    + "','yyyy-mm-dd')");
        }
        //条件2：证券交易市场
        if (exchangeCenterId != -1) {
            sbFrom.append(" and securities.EXCHANGECENTERID = "
                    + exchangeCenterId + " \n");
        }

        //条件3：证券类别
        if (securitiesType > 0) {
            sbFrom.append(" and \n");
            sbFrom.append(" securities.typeId = " + securitiesType);
        }
        //条件4：证券名称
        if (securitiesIds != null && securitiesIds.length > 0) {
            sbFrom.append(" and securities.id in ( ");
            for (int i = 0; i < securitiesIds.length - 1; i++) {
                sbFrom.append(Long.parseLong(securitiesIds[i]) + ",");
            }
            sbFrom.append(Long.parseLong(securitiesIds[securitiesIds.length - 1])
                    + ") \n");
        }

        //条件5：业务单位
        if (clientId != -1) {
            sbFrom.append(" and client.id = " + clientId + " \n");
        }

        //条件6：股东帐户
        if (stockHolderAccountIds != null && stockHolderAccountIds.length > 0) {
            sbFrom.append(" and SEC_STOCKHOLDERACCOUNT.CLIENTID in ( ");
            for (int i = 0; i < stockHolderAccountIds.length - 1; i++) {
                sbFrom.append(Long.parseLong(stockHolderAccountIds[i]) + ",");
            }
            sbFrom.append(Long.parseLong(stockHolderAccountIds[stockHolderAccountIds.length - 1])
                            + ") \n");
        }

        //条件7：资金账号
        if (accountIds != null && accountIds.length > 0) {
            sbFrom.append(" and account.id in ( ");
            for (int i = 0; i < accountIds.length - 1; i++) {
                sbFrom.append(Long.parseLong(accountIds[i]) + ",");
            }
            sbFrom.append(Long.parseLong(accountIds[accountIds.length - 1])
                    + ") \n");
        }

		//条件7.1：只选交易对手
		if (counterPartIds != null && counterPartIds.length > 0 && bankOfDepositId == -1 )
		{
			sbFrom.append(" and counterpart.ID in ( ");
			for (int i = 0; i < counterPartIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(counterPartIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(counterPartIds[counterPartIds.length - 1]) + ") \n");
		}
		//条件7.2：只选交易对手,开户营业部名称
		if (counterPartIds != null && counterPartIds.length > 0 && bankOfDepositId != -1 )
		{
			sbFrom.append(" and counterpart.ID in ( ");
			for (int i = 0; i < counterPartIds.length; i++)
			{
				sbFrom.append(Long.parseLong(counterPartIds[i]) + ",");
			}
			sbFrom.append(bankOfDepositId + ") \n");
		}
		//条件8.1：只选开户营业部名称
		if (bankOfDepositId != -1 && (counterPartIds == null || counterPartIds.length == 0))
		{
			sbFrom.append(" and \n counterpart.ID = " + bankOfDepositId + " \n");
		}

        //		===========以下是根据页面传递的参数决定的条件======end====

        //以下是处理accout = -1的情况的

        sbFrom.append(" 	UNION   \n");
        sbFrom.append("   select  DISTINCT  \n");
        sbFrom.append("   daily.stockDate as StockDate, --库存日期 \n");
        sbFrom.append("   '' as stockHolderAccountName, --股东帐户名称 \n");
        sbFrom.append("   client.name as ClientName, --业务单位名称 \n");
        sbFrom.append("   0 as businessAttributeID, --业务性质 \n");
        sbFrom.append("   '' as BankOfDepositName, --开户营业部名称 \n");
        sbFrom.append("   '' as AccountCode, --资金账号 \n");
        sbFrom.append("   '' as AccountName, --资金账户名称 \n");
        sbFrom.append("   --account.type as AccountType,--资金账户类型：1:证券账户，2:开放式基金账户，3:银行账户  \n");
        sbFrom.append("   -1 as CounterPartId , --交易对手 \n");
        sbFrom.append("   secType.name as SecuritiesType, --证券类型 \n");
        sbFrom.append("   securities.shortname as securitiesName , --证券名称 \n");
        sbFrom.append("   --securities.pledgeRate as SecuritiesRate, --回购抵押比率％，用于计算折成标准券 \n");
        sbFrom.append("   daily.Quantity as Quantity, --库存数量 \n");
        sbFrom.append("  (daily.Quantity * decode(securities.pledgeRate,null,0,securities.pledgeRate) /100) " +
        	"		as StandardQuantity, --折成标准券（如果没有回购抵押比率，则返回零）,去尾法 \n");
        sbFrom.append("   daily.frozenQuantity as FrozenQuantity, --已冻结证券数量 \n");
        sbFrom.append("   decode((daily.cost/decode(daily.Quantity,0,1,daily.Quantity)) ,null, 0 ) as UnitCost,--单位成本 \n");
        sbFrom.append("   decode((daily.Netcost/decode(daily.Quantity,0,1,daily.Quantity)),null , 0 ) as UnitNetCost,--单位成本（净价）\n");
        sbFrom.append("   daily.cost as Cost,daily.NetCost as NetCost --实际成本，实际成本（净价）\n");

        sbFrom.append("   from \n");
        //---------------------//证券库存日结情况表，业务单位表 股东帐户
        sbFrom.append("   sec_dailyStock daily,sec_client  client,SEC_STOCKHOLDERACCOUNT, \n");
        //---------------------//资金账户表，证券资料表
        sbFrom.append("   sec_securities securities,sec_account account, \n");
        //---------------------//交易对手表，证券类型表
        sbFrom.append("   sec_SecuritiesType secType \n");

//        sbFrom.append("   (select a.id as transactionTypeId, \n");
//        sbFrom.append("           a.name as transactionTypeName,  \n");
//        sbFrom.append("           b.Id as businessTypeId,  \n");
//        sbFrom.append("           b.name as businessTypeName, \n");
//        sbFrom.append("            c.ID as businessAttributeID \n");
//        sbFrom
//                .append("    from  SEC_TRANSACTIONTYPE a ,SEC_BUSINESSTYPE b,SEC_BUSINESSATTRIBUTE c  \n");
//        sbFrom
//                .append("    where subStr(a.Id,0,2) = b.id  and b.BUSINESSATTRIBUTEID = c.ID  \n");
//        sbFrom.append("     ) ST_Type  \n");

        sbFrom.append("   where 1=1 \n");
        sbFrom.append("   and SEC_STOCKHOLDERACCOUNT.ID(+) = account.STOCKHOLDERACCOUNTID1 \n");
        sbFrom.append("   and \n");
        sbFrom.append("   daily.ClientID = client.id(+) \n");
        sbFrom.append("   and \n");
        sbFrom.append("   daily.accountid = -1 \n");
		sbFrom.append("   and \n");
		sbFrom.append("   daily.accountid = account.id(+)  \n");
        sbFrom.append("   and \n");
        sbFrom.append("   daily.securitiesID= securities.id \n");
//        sbFrom.append("   and \n");
//        sbFrom.append("   account.counterpartID = counterpart.ID \n");
        sbFrom.append("   and \n");
        sbFrom.append("   securities.typeId = secType.ID \n");
//        sbFrom.append("   and  \n");
//        sbFrom.append("   ST_Type.businessAttributeID = 1 \n"); //对应着第1种业务

        //		===========以下是根据页面传递的参数决定的条件======start====
        //条件1：查询日期
         queryDate = queryParam.getQueryDate();
        if (queryDate != null) {
            String strQueryDate = DataFormat.getDateString(queryDate);
            sbFrom.append(" and \n");
            sbFrom.append(" daily.stockDate = to_Date('" + strQueryDate
                    + "','yyyy-mm-dd')");
        }
        //条件2：证券交易市场
        if (exchangeCenterId != -1) {
            sbFrom.append(" and securities.EXCHANGECENTERID = "
                    + exchangeCenterId + " \n");
        }

        //条件3：证券类别
        if (securitiesType > 0) {
            sbFrom.append(" and \n");
            sbFrom.append(" securities.typeId = " + securitiesType);
        }
        //条件4：证券名称
        if (securitiesIds != null && securitiesIds.length > 0) {
            sbFrom.append(" and securities.id in ( ");
            for (int i = 0; i < securitiesIds.length - 1; i++) {
                sbFrom.append(Long.parseLong(securitiesIds[i]) + ",");
            }
            sbFrom.append(Long
                    .parseLong(securitiesIds[securitiesIds.length - 1])
                    + ") \n");
        }

        //条件5：业务单位
        if (clientId != -1) {
            sbFrom.append(" and client.id = " + clientId + " \n");
        }

		//条件6：股东帐户
		if (stockHolderAccountIds != null && stockHolderAccountIds.length > 0) {
			sbFrom.append(" and account.STOCKHOLDERACCOUNTID1 in ( ");
			for (int i = 0; i < stockHolderAccountIds.length - 1; i++) {
				sbFrom.append(Long.parseLong(stockHolderAccountIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(stockHolderAccountIds[stockHolderAccountIds.length - 1])
							+ ") \n");
		}

		//条件7：资金账号
		if (accountIds != null && accountIds.length > 0) {
			sbFrom.append(" and daily.accountid in ( ");
			for (int i = 0; i < accountIds.length - 1; i++) {
				sbFrom.append(Long.parseLong(accountIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(accountIds[accountIds.length - 1])
					+ ") \n");
		}

		//条件7.1：只选交易对手
		if (counterPartIds != null && counterPartIds.length > 0 && bankOfDepositId == -1 )
		{
			sbFrom.append(" and account.counterpartID in ( ");
			for (int i = 0; i < counterPartIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(counterPartIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(counterPartIds[counterPartIds.length - 1]) + ") \n");
		}
		//条件7.2：只选交易对手,开户营业部名称
		if (counterPartIds != null && counterPartIds.length > 0 && bankOfDepositId != -1 )
		{
			sbFrom.append(" and account.counterpartID in ( ");
			for (int i = 0; i < counterPartIds.length; i++)
			{
				sbFrom.append(Long.parseLong(counterPartIds[i]) + ",");
			}
			sbFrom.append(bankOfDepositId + ") \n");
		}
		//条件8.1：只选开户营业部名称
		if (bankOfDepositId != -1 && (counterPartIds == null || counterPartIds.length == 0))
		{
			sbFrom.append(" and \n account.counterpartID = " + bankOfDepositId + " \n");
		}

        //sbFrom拼写结束！！！！！！！！！！！

        sbFrom.append("   )  \n");

        //------------from----------------end-------------------------
        sbWhere = new StringBuffer();

        //------------where---------------end-------------------------
        sbWhere.append(" ");

        //------------where---------------end-------------------------

        sbOrderBy = new StringBuffer();
        String strDesc = queryParam.getDesc() == 1 ? " desc " : "";
        log
                .debug("----queryParam.getDesc() -----------"
                        + queryParam.getDesc());
        switch ((int) queryParam.getOrderField()) {
        case OrderBy_ClientName:
            sbOrderBy.append(" \n order by ClientName" + strDesc);
            break;
        case OrderBy_BankOfDepositName:
            sbOrderBy.append(" \n order by BankOfDepositName" + strDesc);
            break;
        case OrderBy_AccountName:
            sbOrderBy.append(" \n order by AccountName" + strDesc);
            break;
        case OrderBy_FundManagerCoName:
            sbOrderBy.append(" \n order by CounterPartId" + strDesc);
            break;
        case OrderBy_SecuritiesType:
            sbOrderBy.append(" \n order by SecuritiesType" + strDesc);
            break;
        case OrderBy_SecuritiesName:
            sbOrderBy.append(" \n order by securitiesName" + strDesc);
            break;
        case OrderBy_Quantity:
            sbOrderBy.append(" \n order by Quantity" + strDesc);
            break;
        case OrderBy_Cost:
            sbOrderBy.append(" \n order by cost" + strDesc);
            break;
        case OrderBy_NetCost:
            sbOrderBy.append(" \n order by netcost" + strDesc);
            break;

        }
        log.debug("-----------------------------------------");

    }
	/**
	 * 返回证券查询的SQLString语句
	 * 
	 * @param queryParam
	 * 说明：仅适用于国机项目的查询---证券库存查询 
	 * @return
	 */
	private void getCNMEFSQL(QuerySecuritiesStockParam queryParam)
	{
		System.out.println("^_^国机：证券库存查询---getCNMEFSQL()");
		sbSelect = new StringBuffer();
		sbSelect.append("   *  \n");
		sbFrom = new StringBuffer();
		sbFrom.append("   (  \n");
		//long exchangeCenterId = queryParam.getExchangeCenterId(); //证券交易市场Id
		long securitiesType = queryParam.getSecuritiesType(); //证券类别
		String[] securitiesIds = queryParam.getSecuritiesIds(); //证券名称Id的数组
		long clientId = queryParam.getClientId(); //业务单位ID
		//String[] stockHolderAccountIds = queryParam.getStockHolderAccountIds(); // 股东帐户名称Id的数组
		long bankOfDepositId = queryParam.getBankOfDepositId(); //开户营业部ID
		String[] accountIds = queryParam.getAccountIds(); //资金账号Id的数组
		String[] fundManagerCoIds = queryParam.getFundManagerCoIds(); //基金管理公司Id的数组
		sbFrom.append("   select  DISTINCT  \n");
		//---------------------//库存日期
		sbFrom.append("  daily.stockDate as StockDate,  \n");
		//---------------------//股东帐户名称
		sbFrom.append("   SEC_STOCKHOLDERACCOUNT.name as stockHolderAccountName, \n");
		//---------------------//业务单位名称
		sbFrom.append("   client.name as ClientName,  \n");
		//---------------------//业务性质
		sbFrom.append("   0 as businessAttributeID,  \n");
		//---------------------//开户营业部名称
		sbFrom.append("   decode(counterpart.isbankOfDeposit ,1,counterpart.name,'') as BankOfDepositName,  \n");
		//---------------------//资金账号
		sbFrom.append("   account.accountCode as AccountCode,  \n");
		//---------------------//资金账户名称
		sbFrom.append("   account.accountName as AccountName,  \n");
		//---------------------//资金账户类型：1:证券账户，2:开放式基金账户，3:银行账户
		sbFrom.append("   --account.type as AccountType,  \n");
		//---------------------//基金管理公司名称
		sbFrom.append("   decode(counterpart.isFundManagementCo ,1,counterpart.name,'') as FundManagerCoName ,  \n");
		//---------------------//证券类型
		sbFrom.append("   secType.name as SecuritiesType,  \n");
		//---------------------//证券名称
		sbFrom.append("   securities.shortname as securitiesName ,  \n");
		//---------------------//回购抵押比率％，用于计算折成标准券
		sbFrom.append("   --securities.pledgeRate as SecuritiesRate,  \n");
		//---------------------//库存数量
		sbFrom.append("   daily.Quantity as Quantity,  \n");
		//---------------------//折成标准券（如果没有回购抵押比率，则返回零）,去尾法
		sbFrom.append("  (daily.Quantity * decode(securities.pledgeRate,null,0,securities.pledgeRate) /100) as StandardQuantity, \n");
		//---------------------//已冻结证券数量
		sbFrom.append("   daily.frozenQuantity as FrozenQuantity,  \n");
		//---------------------//单位成本
		sbFrom.append("   decode(sign(daily.Quantity),0,0,round(daily.cost/daily.Quantity,2)) as UnitCost, \n");
		//---------------------//单位成本（净价）
		sbFrom.append("   decode(sign(daily.Quantity),0,0,round(daily.Netcost/daily.Quantity,2)) as UnitNetCost, \n");
		//---------------------//实际成本，实际成本（净价）
		sbFrom.append("   daily.cost as Cost,daily.NetCost as NetCost\n");
		sbFrom.append("   from \n");
		//---------------------//证券库存日结情况表，业务单位表 股东帐户
		sbFrom.append("   sec_dailyStock daily,sec_client  client, SEC_STOCKHOLDERACCOUNT ,\n");
		//---------------------//资金账户表，证券资料表
		sbFrom.append("   sec_account account , sec_securities securities, \n");
		//---------------------//交易对手表，证券类型表
		sbFrom.append("   sec_counterPArt counterpart ,sec_SecuritiesType secType \n");
		//		  sbFrom.append("   (select a.id as transactionTypeId, \n");
		//		  sbFrom.append("           a.name as transactionTypeName,  \n");
		//		  sbFrom.append("           b.Id as businessTypeId,  \n");
		//		  sbFrom.append("           b.name as businessTypeName, \n");
		//		  sbFrom.append("            c.ID as businessAttributeID \n");
		//		  sbFrom
		//				  .append("    from  SEC_TRANSACTIONTYPE a ,SEC_BUSINESSTYPE b,SEC_BUSINESSATTRIBUTE c  \n");
		//		  sbFrom
		//				  .append("    where subStr(a.Id,0,2) = b.id  and b.BUSINESSATTRIBUTEID = c.ID  \n");
		//		  sbFrom.append("     ) ST_Type  \n");
		sbFrom.append("   where  \n");
		sbFrom.append("   SEC_STOCKHOLDERACCOUNT.ID(+) = account.STOCKHOLDERACCOUNTID1 \n");
		sbFrom.append("   and \n");
		sbFrom.append("   daily.ClientID = client.id \n");
		sbFrom.append("   and \n");
		sbFrom.append("   daily.accountid = account.id  \n");
		sbFrom.append("   and \n");
		sbFrom.append("   daily.securitiesID= securities.id \n");
		sbFrom.append("   and \n");
		sbFrom.append("   account.counterpartID = counterpart.ID \n");
		sbFrom.append("   and \n");
		sbFrom.append("   securities.typeId = secType.ID \n");
		sbFrom.append("   and \n");
		sbFrom.append("   daily.Quantity > 0             \n");
		
		//		  sbFrom.append("   and  \n");
		//		  sbFrom.append("   ST_Type.businessAttributeID in (1,2,3)  \n"); //对应着第1种业务
		//		===========以下是根据页面传递的参数决定的条件======start====
		//条件1：查询日期
		Timestamp queryDate = queryParam.getQueryDate();
		if (queryDate != null)
		{
			String strQueryDate = DataFormat.getDateString(queryDate);
			sbFrom.append(" and \n");
			sbFrom.append(" daily.stockDate = to_Date('" + strQueryDate + "','yyyy-mm-dd')");
		}
		/**
		//条件2：证券交易市场
		if (exchangeCenterId != -1)
		{
			sbFrom.append(" and securities.EXCHANGECENTERID = " + exchangeCenterId + " \n");
		}
		**/
		//条件3：证券类别
		if (securitiesType > 0)
		{
			sbFrom.append(" and \n");
			sbFrom.append(" securities.typeId = " + securitiesType);
		}
		//条件4：证券名称
		if (securitiesIds != null && securitiesIds.length > 0)
		{
			sbFrom.append(" and securities.id in ( ");
			for (int i = 0; i < securitiesIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(securitiesIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(securitiesIds[securitiesIds.length - 1]) + ") \n");
		}
		//条件5：业务单位
		if (clientId != -1)
		{
			sbFrom.append(" and client.id = " + clientId + " \n");
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
		//条件7：资金账号
		if (accountIds != null && accountIds.length > 0)
		{
			sbFrom.append(" and account.id in ( ");
			for (int i = 0; i < accountIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(accountIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(accountIds[accountIds.length - 1]) + ") \n");
		}
		//条件8：只选开户营业部名称
		if (bankOfDepositId != -1 && (fundManagerCoIds == null || fundManagerCoIds.length == 0))
		{
			sbFrom.append(" and counterpart.ID = " + bankOfDepositId + " \n");
		}
		//条件9：只选基金管理公司名称
		if (fundManagerCoIds != null && fundManagerCoIds.length > 0 && bankOfDepositId == -1)
		{
			sbFrom.append(" and counterpart.ID in ( ");
			for (int i = 0; i < fundManagerCoIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(fundManagerCoIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(fundManagerCoIds[fundManagerCoIds.length - 1]) + ") \n");
		}
		//条件10：只选开户营业部名称,基金管理公司名称
		if (bankOfDepositId != -1 && fundManagerCoIds != null && fundManagerCoIds.length > 0)
		{
			sbFrom.append(" and counterpart.ID in ( ");
			for (int i = 0; i < fundManagerCoIds.length; i++)
			{
				sbFrom.append(Long.parseLong(fundManagerCoIds[i]) + ",");
			}
			sbFrom.append(bankOfDepositId + ") \n");
		}
		//		===========以下是根据页面传递的参数决定的条件======end====
		//以下是处理accout = -1的情况的
		sbFrom.append(" UNION   \n");
		sbFrom.append("   select  DISTINCT  \n");
		//---------------------//库存日期
		sbFrom.append("  daily.stockDate as StockDate,  \n");
		//---------------------//股东帐户名称
		sbFrom.append("   '' as stockHolderAccountName, \n");
		//---------------------//业务单位名称
		sbFrom.append("   client.name as ClientName,  \n");
		//---------------------//业务性质
		sbFrom.append("   0 as businessAttributeID,  \n");
		//---------------------//开户营业部名称
		sbFrom.append("   '' as BankOfDepositName,  \n");
		//---------------------//资金账号
		sbFrom.append("   '' as AccountCode,  \n");
		//---------------------//资金账户名称
		sbFrom.append("   '' as AccountName,  \n");
		//---------------------//资金账户类型：1:证券账户，2:开放式基金账户，3:银行账户
		sbFrom.append("   --account.type as AccountType,  \n");
		//---------------------//基金管理公司名称
		sbFrom.append("   '' as FundManagerCoName ,  \n");
		//---------------------//证券类型
		sbFrom.append("   secType.name as SecuritiesType,  \n");
		//---------------------//证券名称
		sbFrom.append("   securities.shortname as securitiesName ,  \n");
		//---------------------//回购抵押比率％，用于计算折成标准券
		sbFrom.append("   --securities.pledgeRate as SecuritiesRate,  \n");
		//---------------------//库存数量
		sbFrom.append("   daily.Quantity as Quantity,  \n");
		//---------------------//折成标准券（如果没有回购抵押比率，则返回零）,去尾法
		sbFrom.append("  (daily.Quantity * decode(securities.pledgeRate,null,0,securities.pledgeRate) /100) as StandardQuantity, \n");
		//---------------------//已冻结证券数量
		sbFrom.append("   daily.frozenQuantity as FrozenQuantity,  \n");
		//---------------------//单位成本
		sbFrom.append("   decode((daily.cost/decode(daily.Quantity,0,1,daily.Quantity)) ,null, 0 ) as UnitCost, \n");
		//---------------------//单位成本（净价）
		sbFrom.append("   decode((daily.Netcost/decode(daily.Quantity,0,1,daily.Quantity)),null , 0 ) as UnitNetCost, \n");
		//---------------------//实际成本，实际成本（净价）
		sbFrom.append("   daily.cost as Cost,daily.NetCost as NetCost\n");
		sbFrom.append("   from \n");
		//---------------------//证券库存日结情况表，业务单位表 股东帐户
		sbFrom.append("   sec_dailyStock daily,sec_client  client, \n");
		//---------------------//资金账户表，证券资料表
		sbFrom.append("   sec_securities securities, \n");
		//---------------------//交易对手表，证券类型表
		sbFrom.append("   sec_SecuritiesType secType \n");
		//		  sbFrom.append("   (select a.id as transactionTypeId, \n");
		//		  sbFrom.append("           a.name as transactionTypeName,  \n");
		//		  sbFrom.append("           b.Id as businessTypeId,  \n");
		//		  sbFrom.append("           b.name as businessTypeName, \n");
		//		  sbFrom.append("            c.ID as businessAttributeID \n");
		//		  sbFrom
		//				  .append("    from  SEC_TRANSACTIONTYPE a ,SEC_BUSINESSTYPE b,SEC_BUSINESSATTRIBUTE c  \n");
		//		  sbFrom
		//				  .append("    where subStr(a.Id,0,2) = b.id  and b.BUSINESSATTRIBUTEID = c.ID  \n");
		//		  sbFrom.append("     ) ST_Type  \n");
		sbFrom.append("   where 1=1 \n");
//		sbFrom.append("   and SEC_STOCKHOLDERACCOUNT.ID(+) = account.STOCKHOLDERACCOUNTID1 \n");
		sbFrom.append("   and \n");
		sbFrom.append("   daily.ClientID = client.id \n");
		sbFrom.append("   and \n");
		sbFrom.append("   daily.accountid = -1 \n");
		sbFrom.append("   and \n");
		sbFrom.append("   daily.securitiesID= securities.id \n");
//		sbFrom.append("   and \n");
//		sbFrom.append("   account.counterpartID = counterpart.ID \n");
		sbFrom.append("   and \n");
		sbFrom.append("   securities.typeId = secType.ID \n");
		//		  sbFrom.append("   and  \n");
		//		  sbFrom.append("   ST_Type.businessAttributeID in (1,2,3) \n"); //对应着第1种业务
		//		===========以下是根据页面传递的参数决定的条件======start====
		//条件1：查询日期
		queryDate = queryParam.getQueryDate();
		if (queryDate != null)
		{
			String strQueryDate = DataFormat.getDateString(queryDate);
			sbFrom.append(" and \n");
			sbFrom.append(" daily.stockDate = to_Date('" + strQueryDate + "','yyyy-mm-dd')");
		}
		/**
		//条件2：证券交易市场
		if (exchangeCenterId != -1)
		{
			sbFrom.append(" and securities.EXCHANGECENTERID = " + exchangeCenterId + " \n");
		}
		**/
		//条件3：证券类别
		if (securitiesType > 0)
		{
			sbFrom.append(" and \n");
			sbFrom.append(" securities.typeId = " + securitiesType);
		}
		//条件4：证券名称
		if (securitiesIds != null && securitiesIds.length > 0)
		{
			sbFrom.append(" and securities.id in ( ");
			for (int i = 0; i < securitiesIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(securitiesIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(securitiesIds[securitiesIds.length - 1]) + ") \n");
		}
		//条件5：业务单位
		if (clientId != -1)
		{
			sbFrom.append(" and client.id = " + clientId + " \n");
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
//		//条件7：资金账号
//		if (accountIds != null && accountIds.length > 0)
//		{
//			sbFrom.append(" and account.id in ( ");
//			for (int i = 0; i < accountIds.length - 1; i++)
//			{
//				sbFrom.append(Long.parseLong(accountIds[i]) + ",");
//			}
//			sbFrom.append(Long.parseLong(accountIds[accountIds.length - 1]) + ") \n");
//		}
//		//条件8：只选开户营业部名称
//		if (bankOfDepositId != -1 && (fundManagerCoIds == null || fundManagerCoIds.length == 0))
//		{
//			sbFrom.append(" and counterpart.ID = " + bankOfDepositId + " \n");
//		}
//		//条件9：只选基金管理公司名称
//		if (fundManagerCoIds != null && fundManagerCoIds.length > 0 && bankOfDepositId == -1)
//		{
//			sbFrom.append(" and counterpart.ID in ( ");
//			for (int i = 0; i < fundManagerCoIds.length - 1; i++)
//			{
//				sbFrom.append(Long.parseLong(fundManagerCoIds[i]) + ",");
//			}
//			sbFrom.append(Long.parseLong(fundManagerCoIds[fundManagerCoIds.length - 1]) + ") \n");
//		}
//		//条件10：只选开户营业部名称,基金管理公司名称
//		if (bankOfDepositId != -1 && fundManagerCoIds != null && fundManagerCoIds.length > 0)
//		{
//			sbFrom.append(" and counterpart.ID in ( ");
//			for (int i = 0; i < fundManagerCoIds.length; i++)
//			{
//				sbFrom.append(Long.parseLong(fundManagerCoIds[i]) + ",");
//			}
//			sbFrom.append(bankOfDepositId + ") \n");
//		}
		//sbFrom拼写结束！！！！！！！！！！！
		sbFrom.append("   )  \n");
		//------------from----------------end-------------------------
		sbWhere = new StringBuffer();
		//------------where---------------end-------------------------
		sbWhere.append(" ");
		//------------where---------------end-------------------------
		sbOrderBy = new StringBuffer();
		String strDesc = queryParam.getDesc() == 1 ? " desc " : "";
		log.debug("----queryParam.getDesc() -----------" + queryParam.getDesc());
		switch ((int) queryParam.getOrderField())
		{
			case OrderBy_ClientName :
				sbOrderBy.append(" \n order by ClientName" + strDesc);
				break;
			case OrderBy_BankOfDepositName :
				sbOrderBy.append(" \n order by BankOfDepositName" + strDesc);
				break;
			case OrderBy_AccountName :
				sbOrderBy.append(" \n order by AccountName" + strDesc);
				break;
			case OrderBy_FundManagerCoName :
				sbOrderBy.append(" \n order by FundManagerCoName" + strDesc);
				break;
			case OrderBy_SecuritiesType :
				sbOrderBy.append(" \n order by SecuritiesType" + strDesc);
				break;
			case OrderBy_SecuritiesName :
				sbOrderBy.append(" \n order by securitiesName" + strDesc);
				break;
			case OrderBy_Quantity :
				sbOrderBy.append(" \n order by Quantity" + strDesc);
				break;
			case OrderBy_Cost :
				sbOrderBy.append(" \n order by cost" + strDesc);
				break;
			case OrderBy_NetCost :
				sbOrderBy.append(" \n order by netcost" + strDesc);
				break;
		}
		log.debug("-----------------------------------------");
	}

    /*
     * 
     * 在这里还需增加一个返回sum的方法 刘惠军 2004-04-08 public QueryFixedDepositSumInfo SELECT
     * sum(quantity) as QuantitySum,sum(cost) as CostSum,sum(netcost) as
     * NetCostSum!!!!
     *  
     */
    public StockQuerySumInfo queryStockSum(QuerySecuritiesStockParam queryParam)
            throws SecuritiesException {
        StockQuerySumInfo sumInfo = new StockQuerySumInfo();
        String sqlString = "";
        //返回sql语句
        getSQL(queryParam);
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(" SELECT \n");
        sqlBuffer.append(" sum(quantity) as stockQuantitySum, \n");
        sqlBuffer.append(" sum(cost) as stockCostSum,  \n");
        sqlBuffer.append(" sum(netcost) as stockNetCostSum \n");
        sqlBuffer.append(" FROM  \n");
        //String sqlString = "SELECT sum(quantity) as
        // stockQuantitySum,sum(cost) as stockCostSum,sum(netcost) as
        // stockNetCostSum FROM \n";

        sqlString = sqlBuffer.toString() + sbFrom.toString();
        log.debug("返回证券库存的sql语句：" + sqlString);
        QuerySecuritiesStockDAO dao = new QuerySecuritiesStockDAO();
        try {
            Collection col = dao.findByCondiction(sqlString);
            if (col != null && col.size() > 0) {
                Iterator colIterator = col.iterator();
                sumInfo = (StockQuerySumInfo) colIterator.next();
            }

        } catch (SecuritiesDAOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new SecuritiesException("Gen_E001", null);
        }
        log.debug("sumInfo=库存合计" + sumInfo.getStockQuantitySum());
        log.debug("sumInfo=成本合计" + sumInfo.getStockCostSum());
        log.debug("sumInfo=成本（净价）合计" + sumInfo.getStockNetCostSum());
        return sumInfo;
    }

	/**
	 * 生成PageLoader
	 * 
	 * @param queryParam
	 * @return @throws
	 *         SecuritiesException
	 */
	public PageLoader querySecuritiesStockInfo(QuerySecuritiesStockParam queryParam) throws SecuritiesException
	{
		/*if (Env.getProjectName().equalsIgnoreCase(Constant.ProjectName.CNMEF))
		{
			System.out.println("项目名称：国机;模块：证券库存查询");
			getCNMEFSQL(queryParam);
		}
		else
		{*/
			System.out.println("项目名称：--;模块：证券库存查询");
			getSQL(queryParam);
		//}
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		//		log.debug("selectString :" + sbSelect.toString());
		//		log.debug("fromString :" + sbFrom.toString());
		//		log.debug("whereString :" + sbWhere.toString());
		log.debug("sbOrderBy :" + sbOrderBy.toString());
		pageLoader.initPageLoader(
			new AppContext(),
			sbFrom.toString(),
			sbSelect.toString(),
			sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.securities.query.dataentity.QuerySecuritiesStockInfo",
			null);
		pageLoader.setOrderBy(" " + sbOrderBy.toString() + " ");
		return pageLoader;
	}

}
