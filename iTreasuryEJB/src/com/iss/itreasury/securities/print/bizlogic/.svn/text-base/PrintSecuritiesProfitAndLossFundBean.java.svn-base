/*
 * Created on 2004-5-17
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.print.bizlogic;

import java.sql.Timestamp;

import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.print.dataentity.SecuritiesProfitAndLossParam;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

/**
 * @author chluo
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PrintSecuritiesProfitAndLossFundBean {
	protected Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);

	private StringBuffer sbSelect	= null;
	private StringBuffer sbFrom		= null;
	private StringBuffer sbWhere	= null;

	private void getSQL(SecuritiesProfitAndLossParam printParam) {
		sbSelect	= new StringBuffer();
		sbFrom		= new StringBuffer();
		sbWhere		= new StringBuffer();

		//SELECT[ADD BY kewenhu]胡志强[2004-07-09]
		sbSelect.append("	NVL(a.clientID, -1)								clientID, \n");
		sbSelect.append("	NVL(a.accountID, -1)							accountID, \n");
		sbSelect.append("	NVL(a.securitiesID, -1)							securitiesID, \n");
		sbSelect.append("	NVL(a.quantity, 0.0)							quantity, \n");
		sbSelect.append("	NVL(a.netCost, 0.0)								netCost, \n");
		sbSelect.append("	NVL(a.cost, 0.0)								cost, \n");
		sbSelect.append("	NVL(a.quantity*b.netClosePrice, 0.0)			netMarketPrice, \n");
		sbSelect.append("	NVL(a.quantity*b.closePrice, 0.0)				marketPrice, \n");
		sbSelect.append("	NVL(a.quantity*b.netClosePrice-netCost, 0.0)	netPayoff, \n");
		sbSelect.append("	NVL(a.quantity*b.closePrice-Cost, 0.0)			payoff, \n");
		sbSelect.append("	a.securitiesName								securitiesName, \n");
		sbSelect.append("	NVL(a.securitiesTypeID, -1)						securitiesTypeID, \n");
		sbSelect.append("	NVL(a.counterpartID, -1)						counterpartID, \n");
		sbSelect.append("	a.stockDate										stockDate, \n");
		sbSelect.append("	NVL(a.stockHolderAccountID1, -1)				stockHolderAccountID1, \n");
		sbSelect.append("	a.securitiescode								securitiesCode \n");

		//FROM[ADD BY kewenhu]胡志强[2004-07-09]
		sbFrom.append("	(SELECT --在PrintSecuritiesProfitAndLossFundBean中拼写 \n");
		sbFrom.append("		sec_DailyStock.clientID				clientID, --业务单位 \n");
		sbFrom.append("		sec_DailyStock.accountID			accountID, --资金帐户 \n");
		sbFrom.append("		sec_DailyStock.securitiesID			securitiesID, --证券ID \n");
		sbFrom.append("		sec_DailyStock.quantity				quantity, --证券库存 \n");
		sbFrom.append("		sec_DailyStock.netCost				netCost,--单位成本 净价格 \n");
		sbFrom.append("		sec_DailyStock.cost					cost, --实际成本 全价 \n");
		sbFrom.append("		sec_Securities.shortName			securitiesName, --证券名称 \n");
		sbFrom.append("		sec_Securities.typeID				securitiesTypeID,-- 证券类别 \n");
		sbFrom.append("		sec_Account.counterpartID			counterpartID,-- 交易对手 \n");
		sbFrom.append("		sec_DailyStock.stockDate			stockDate, -- 交易时间 \n");
		sbFrom.append("		sec_Account.stockHolderAccountID1	stockHolderAccountID1,-- 股东帐户 \n");
		sbFrom.append("		sec_Securities.securitiesCode2		securitiesCode -- 证券代码 \n");
		sbFrom.append("	FROM sec_DailyStock, sec_Securities, sec_Account \n");
		sbFrom.append("	WHERE 1 = 1 \n");
		sbFrom.append("		AND sec_DailyStock.securitiesID = sec_Securities.ID \n");
		sbFrom.append("		AND sec_Securities.typeID IN (5,6) \n");
		sbFrom.append("		AND sec_DailyStock.accountID = sec_Account.ID \n");
		//时间
		if (printParam.getDailyAccountDate() != null) {
			sbFrom.append("		AND sec_DailyStock.stockDate = TO_DATE('"+DataFormat.getDateString(printParam.getDailyAccountDate())+"','YYYY-MM-DD') \n");
		}
		//业务单位ID
		if (printParam.getClientId() > 0) {
			sbFrom.append("		AND sec_DailyStock.clientID = "+printParam.getClientId()+" \n");
		}
		//基金管理公司
		String[] counterpartids = printParam.getFundCounterPartIds() ;
		if (counterpartids != null && counterpartids.length > 0) {
			sbFrom.append("		AND sec_Account.counterpartID IN (");
			for(int i = 0; i < counterpartids.length-1; i++) {
				sbFrom.append(Long.parseLong( counterpartids[i])+",");
			}
			sbFrom.append(Long.parseLong( counterpartids[counterpartids.length -1])+") \n");
		}
		//证券类别
		if (printParam.getSecuritiesTypeId() > 0) {
			sbFrom.append("		AND sec_Securities.typeID = "+printParam.getSecuritiesTypeId()+" \n");
		}
		//证券名称 :注意，其实参数里的bourseSecuritiesName存储的是ID
		String[] securitiesIds_bank = printParam.getSecuritiesIds();
		if (securitiesIds_bank != null && securitiesIds_bank.length > 0) {
			sbFrom.append("		AND sec_DailyStock.securitiesID IN (");
			for (int i = 0; i < securitiesIds_bank.length-1; i++) {
				sbFrom.append(Long.parseLong(securitiesIds_bank[i]) + ",");
			}
			sbFrom.append(Long.parseLong(securitiesIds_bank[securitiesIds_bank.length-1])+") \n");
		}
		sbFrom.append("	) a, \n");
		sbFrom.append("	(SELECT \n");
		sbFrom.append("		securitiesCode, \n");
		sbFrom.append("		NVL(netClosePrice, 0.0) netClosePrice, \n");
		sbFrom.append("		NVL(closePrice, 0.0) closePrice \n");
		sbFrom.append("	FROM sec_SecuritiesMarket \n");
		sbFrom.append("	WHERE 1 = 1 \n");
		sbFrom.append("		AND closeDate = TO_DATE('"+DataFormat.getDateString(printParam.getDailyAccountDate())+"','YYYY-MM-DD') \n");
		sbFrom.append("	) b \n");

		//WHERE[ADD BY kewenhu]胡志强[2004-07-09]
		sbWhere.append("1 = 1 \n");
		sbWhere.append("	AND a.securitiesCode = b.securitiesCode(+) \n");
		sbWhere.append("	AND NVL(a.quantity, 0.0) > 0 \n");
	}

	/**
	 * 打印操作
	 * @param  printParam
	 * @return PageLoader
	 * @throws SecuritiesException
	 */
	public PageLoader PrintSecuritiesProfitAndLossFund(SecuritiesProfitAndLossParam printParam) throws SecuritiesException {
		this.getSQL(printParam);
		//取得PageLoader对象
		PageLoader pageLoader =
			(PageLoader) com.iss.system.BaseObjectFactory.getBaseObject(
				"com.iss.system.dao.PageLoader");

		//初始化PageLoader
		pageLoader.initPageLoader(
			new AppContext(),
			sbFrom.toString(),
			sbSelect.toString(),
			sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.securities.print.dataentity.SecuritiesProfitAndLossFundInfo",
			null);
		pageLoader.setOrderBy("ORDER BY clientID, accountID \n");
		return pageLoader;
	}

	private void getSQLForCnmef(SecuritiesProfitAndLossParam printParam) {
		sbSelect	= new StringBuffer();
		sbFrom		= new StringBuffer();
		sbWhere		= new StringBuffer();

		//SELECT[ADD BY kewenhu]胡志强[2004-07-09]
		sbSelect.append("	NVL(aa.clientID, -1)							clientID, \n");
		sbSelect.append("	NVL(aa.accountID, -1)							accountID, \n");
		sbSelect.append("	NVL(aa.securitiesID, -1)						securitiesID, \n");
		sbSelect.append("	NVL(aa.quantity, 0.0)							quantity, \n");
		sbSelect.append("	ROUND((cc.outAmount-aa.netCost*cc.outQuantity),2) unitProfitLoss, \n");
		sbSelect.append("	ROUND((cc.outAmount-aa.netCost*cc.outQuantity),2) unitNetProfitLoss, \n");
		sbSelect.append("	NVL(aa.cost, 0.0)								netCost, \n");
		sbSelect.append("	NVL(aa.cost, 0.0)								cost, \n");

		//胡志强(2004-10-27)为了配合info和jsp页面的取值情况，将注释的语句改写如下
		//sbSelect.append("	NVL(cc.unitCost, 0.0) 							unitCost, \n");
		//sbSelect.append("	NVL(cc.unitNetCost, 0.0) 						unitNetCost, \n");
		//sbSelect.append("	NVL(c.price, 0.0)								price, \n");
		//sbSelect.append("	NVL(c.netPrice, 0.0)							netPrice, \n");
		//sbSelect.append("	NVL(bb.closePrice, 0.0)							closePrice, \n");
		//sbSelect.append("	NVL(bb.netClosePrice, 0.0)						netClosePrice, \n");
		sbSelect.append("	NVL(aa.netCost, 0.0) 							price, \n");
		sbSelect.append("	NVL(aa.netCost, 0.0) 							netPrice, \n");
		sbSelect.append("	NVL(bb.closePrice, 0.0)							unitCost, \n");
		sbSelect.append("	NVL(bb.netClosePrice, 0.0)						unitNetCost, \n");

		sbSelect.append("	NVL(aa.quantity*bb.netClosePrice, 0.0)			netMarketPrice, \n");
		sbSelect.append("	NVL(aa.quantity*bb.closePrice, 0.0)				marketPrice, \n");
		sbSelect.append("	DECODE(NVL(aa.quantity*bb.closePrice,0.0),0,0,NVL(aa.quantity*bb.closePrice, 0.0)-aa.cost) payoff, \n");
		sbSelect.append("	DECODE(NVL(aa.quantity*bb.netClosePrice,0.0),0,0,NVL(aa.quantity*bb.netClosePrice, 0.0)-aa.cost) netPayoff, \n");
		sbSelect.append("	aa.securitiesName								securitiesName, \n");
		sbSelect.append("	NVL(aa.securitiesTypeID, -1)					securitiesTypeID, \n");
		sbSelect.append("	NVL(aa.counterpartID, -1)						counterpartID, \n");
		sbSelect.append("	aa.stockDate									stockDate, \n");
		sbSelect.append("	NVL(aa.stockHolderAccountID1, -1)				stockHolderAccountID1, \n");
		sbSelect.append("	aa.securitiescode1								securitiesCode1, \n");
		sbSelect.append("	aa.securitiescode2								securitiesCode2 \n");

		//FROM[ADD BY kewenhu]胡志强[2004-07-09]
		sbFrom.append("	(SELECT --在PrintSecuritiesProfitAndLossStockBean中拼写 \n");
		sbFrom.append("		sec_DailyStock.clientID				clientID, --业务单位 \n");
		sbFrom.append("		sec_DailyStock.accountID			accountID, --资金帐户 \n");
		sbFrom.append("		sec_DailyStock.securitiesID			securitiesID, --证券ID \n");
		sbFrom.append("		sec_DailyStock.quantity				quantity, --证券库存 \n");
		sbFrom.append("		sec_DailyStock.cost					cost, --实际成本 \n");
		sbFrom.append("		DECODE(NVL(sec_DailyStock.quantity,0.0),0,0, \n");
		sbFrom.append("		ROUND(NVL(sec_DailyStock.cost,0.0)/NVL(sec_DailyStock.quantity,0.0),2))	netCost,--单位成本 \n");
		sbFrom.append("		sec_Securities.shortName			securitiesName, --证券名称 \n");
		sbFrom.append("		sec_Securities.typeID				securitiesTypeID,-- 证券类别 \n");
		sbFrom.append("		sec_Account.counterpartID			counterpartID,-- 交易对手 \n");
		sbFrom.append("		sec_DailyStock.stockDate			stockDate, -- 交易时间 \n");
		sbFrom.append("		sec_Account.stockHolderAccountID1	stockHolderAccountID1,-- 股东帐户 \n");
		sbFrom.append("		sec_Securities.securitiesCode1		securitiesCode1, -- 证券代码 \n");
		sbFrom.append("		sec_Securities.securitiesCode2		securitiesCode2 -- 证券代码 \n");
		sbFrom.append("	FROM sec_DailyStock, sec_Securities, sec_Account \n");
		sbFrom.append("	WHERE 1 = 1 \n");
		sbFrom.append("		AND sec_DailyStock.securitiesID = sec_Securities.ID \n");
		sbFrom.append("		AND sec_Securities.typeID IN ("+SECConstant.SecuritiesType.ENCLOSED_FUND+","+SECConstant.SecuritiesType.MUTUAL_FUND+") \n");
		sbFrom.append("		AND sec_DailyStock.accountID = sec_Account.ID(+) \n");
		//时间
		if (printParam.getDailyAccountDate() != null) {
			sbFrom.append("		AND sec_DailyStock.stockDate = TO_DATE('"+DataFormat.getDateString(printParam.getDailyAccountDate())+"','YYYY-MM-DD') \n");
		}
		//业务单位ID
		if (printParam.getClientId() > 0) {
			sbFrom.append("		AND sec_DailyStock.clientID = "+printParam.getClientId()+" \n");
		}
		//交易对手
		if (printParam.getCounterPartId() > 0) {
			sbFrom.append("		AND sec_Account.counterpartID = "+printParam.getCounterPartId()+" \n");
		}
		//证券类别
		if (printParam.getSecuritiesTypeId() > 0) {
			sbFrom.append("		AND sec_Securities.typeID = "+printParam.getSecuritiesTypeId()+" \n");
		}
		//证券名称 :注意，其实参数里的bourseSecuritiesName存储的是ID
		String[] securitiesIds_bank = printParam.getSecuritiesIds();
		if (securitiesIds_bank != null && securitiesIds_bank.length > 0) {
			sbFrom.append("		AND sec_DailyStock.securitiesID IN (");
			for (int i = 0; i < securitiesIds_bank.length-1; i++) {
				sbFrom.append(Long.parseLong(securitiesIds_bank[i]) + ",");
			}
			sbFrom.append(Long.parseLong(securitiesIds_bank[securitiesIds_bank.length-1])+") \n");
		}
		//股东帐户
		String[] stockHolderAccountIds = printParam.getStockHolderAccountIds();
		if (stockHolderAccountIds != null && stockHolderAccountIds.length > 0) {
			sbFrom.append("		AND sec_Account.stockHolderAccountID1 IN (");
			for (int i = 0; i < stockHolderAccountIds.length-1; i++) {
				sbFrom.append(Long.parseLong(stockHolderAccountIds[i])+",");
			}
			sbFrom.append(Long.parseLong(stockHolderAccountIds[stockHolderAccountIds.length -1])+") \n ");
		}
		//资金帐号
		String[] accountIds = printParam.getAccountIds();
		if (accountIds != null && accountIds.length > 0) {
			sbFrom.append("		AND sec_DailyStock.accountID IN (");
			for (int i = 0; i < accountIds.length-1; i++) {
				sbFrom.append(Long.parseLong(accountIds[i])+",");
			}
			sbFrom.append(Long.parseLong(accountIds[accountIds.length - 1])+") \n");
		}
		sbFrom.append("	) aa, \n");

		//WHERE[ADD BY kewenhu]胡志强[2004-07-09]
		sbFrom.append("	(SELECT \n");
		sbFrom.append("		securitiesCode, \n");
		sbFrom.append("		NVL(netClosePrice, 0.0) netClosePrice, \n");
		sbFrom.append("		NVL(closePrice, 0.0) closePrice \n");
		sbFrom.append("	FROM sec_SecuritiesMarket \n");
		sbFrom.append("	WHERE 1 = 1 \n");
		sbFrom.append("		AND closeDate = TO_DATE('"+DataFormat.getDateString(printParam.getDailyAccountDate())+"','YYYY-MM-DD') \n");
		sbFrom.append("	) bb, \n");

		//WHERE[ADD BY kewenhu]胡志强[2004-10-26]
		sbFrom.append("	(SELECT clientID, accountID, securitiesID, inAmount, inQuantity, outAmount, outQuantity, \n");
		sbFrom.append("		ROUND(DECODE(SIGN(inQuantity),0,0,inAmount/inQuantity),2) unitCost, \n");
		sbFrom.append("		ROUND(DECODE(SIGN(inQuantity),0,0,inAmount/inQuantity),2) unitNetCost \n");
		sbFrom.append("	FROM \n");
		sbFrom.append("		(SELECT a.clientID, a.accountID, a.securitiesID, \n");
		sbFrom.append("			SUM(NVL(DECODE(b.stockDirection,"+SECConstant.StockDirection.OUT+",netIncome),0.0)) outAmount, \n");
		sbFrom.append("			SUM(NVL(DECODE(b.stockDirection,"+SECConstant.StockDirection.OUT+",a.quantity),0.0)) outQuantity, \n");
		sbFrom.append("			SUM(NVL(DECODE(b.stockDirection,"+SECConstant.StockDirection.IN+",netIncome),0.0)) inAmount, \n");
		sbFrom.append("			SUM(NVL(DECODE(b.stockDirection,"+SECConstant.StockDirection.IN+",a.quantity),0.0)) inQuantity \n");
		sbFrom.append("		FROM sec_DeliveryOrder a, sec_TransactionType b, sec_Securities c \n");
		sbFrom.append("		WHERE 1 = 1 \n");
		sbFrom.append("		AND a.officeID = "+printParam.getOfficeId()+" \n");
		sbFrom.append("		AND a.currencyID = "+printParam.getCurrencyId()+" \n");
		sbFrom.append("		AND a.statusID >= "+SECConstant.DeliveryOrderStatus.CHECKED+" \n");
		sbFrom.append("		AND a.deliveryDate <= TO_DATE('"+DataFormat.getDateString(printParam.getDailyAccountDate())+"', 'YYYY-MM-DD') \n");
		sbFrom.append("		AND b.statusID = "+SECConstant.TransactionTypeStatus.CHECKED+" \n");
		sbFrom.append("		AND b.ID = a.transactionTypeID \n");
		sbFrom.append("		AND c.currencyID = "+printParam.getCurrencyId()+" \n");
		sbFrom.append("		AND c.statusID >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("		AND c.ID = a.securitiesID \n");
		sbFrom.append("		AND c.typeID IN ("+SECConstant.SecuritiesType.ENCLOSED_FUND+","+SECConstant.SecuritiesType.MUTUAL_FUND+") \n");
		//业务单位ID
		if (printParam.getClientId() > 0) {
			sbFrom.append("		AND a.clientID = "+printParam.getClientId()+" \n");
		}
		//交易对手
		if (printParam.getCounterPartId() > 0) {
			sbFrom.append("		AND a.counterpartID = "+printParam.getCounterPartId()+" \n");
		}
		//证券类别
		if (printParam.getSecuritiesTypeId() > 0) {
			sbFrom.append("		AND c.typeID = "+printParam.getSecuritiesTypeId()+" \n");
		}
		//证券名称 :注意，其实参数里的bourseSecuritiesName存储的是ID
		securitiesIds_bank = printParam.getSecuritiesIds();
		if (securitiesIds_bank != null && securitiesIds_bank.length > 0) {
			sbFrom.append("		AND a.securitiesID IN (");
			for (int i = 0; i < securitiesIds_bank.length-1; i++) {
				sbFrom.append(Long.parseLong(securitiesIds_bank[i]) + ",");
			}
			sbFrom.append(Long.parseLong(securitiesIds_bank[securitiesIds_bank.length-1])+") \n");
		}
		//资金帐号
		accountIds = printParam.getAccountIds();
		if (accountIds != null && accountIds.length > 0) {
			sbFrom.append("		AND a.accountID IN (");
			for (int i = 0; i < accountIds.length-1; i++) {
				sbFrom.append(Long.parseLong(accountIds[i])+",");
			}
			sbFrom.append(Long.parseLong(accountIds[accountIds.length - 1])+") \n");
		}
		sbFrom.append("	GROUP BY clientID, accountID, securitiesID) \n");
		sbFrom.append("	) cc \n");
		//WHERE[ADD BY kewenhu]胡志强[2004-07-09]
		sbWhere.append("1 = 1 \n");
		sbWhere.append("	AND aa.securitiesCode1 || aa.securitiesCode2 LIKE bb.securitiesCode(+) || '%' \n");
		sbWhere.append("	AND aa.clientID = cc.clientID(+) \n");
		sbWhere.append("	AND aa.accountID = cc.accountID(+) \n");
		sbWhere.append("	AND aa.securitiesID = cc.securitiesID(+) \n");
		sbWhere.append("	AND NVL(aa.quantity, 0.0) > 0 \n");
	}

	/**
	 * 打印操作
	 * @param  printParam
	 * @return PageLoader
	 * @throws SecuritiesException
	 */
	public PageLoader PrintSecuritiesProfitAndLossFundForCnmef(SecuritiesProfitAndLossParam printParam) throws SecuritiesException {
		this.getSQLForCnmef(printParam);
		//取得PageLoader对象
		PageLoader pageLoader =
			(PageLoader) com.iss.system.BaseObjectFactory.getBaseObject(
				"com.iss.system.dao.PageLoader");

		//初始化PageLoader
		pageLoader.initPageLoader(
			new AppContext(),
			sbFrom.toString(),
			sbSelect.toString(),
			sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.securities.print.dataentity.SecuritiesProfitAndLossFundInfo",
			null);
		pageLoader.setOrderBy("ORDER BY aa.clientID, aa.accountID \n");
		return pageLoader;
	}
}