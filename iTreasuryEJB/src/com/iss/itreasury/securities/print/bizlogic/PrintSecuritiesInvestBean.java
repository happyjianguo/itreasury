/*
 * Created on 2004-09-07
 *
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.print.bizlogic;

import java.sql.Timestamp;

import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.print.dataentity.PrintSecuritiesInvestParam;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2004
 * Company:             iSoftStone
 * @author              kewen hu
 * @version
 * Date of Creation    2004-09-07
 */
public class PrintSecuritiesInvestBean {
	protected Log4j log4j = new Log4j(Constant.ModuleType.SECURITIES, this);
	private StringBuffer sbSelect	= null;
	private StringBuffer sbFrom		= null;
	private StringBuffer sbWhere	= null;

	/**
	 * 返回查询余额表的SQL语句
	 * @param  PrintSecuritiesInvestParam parameter
	 * @return void
	 * @exception nothing
	 */
	private void initialSql(PrintSecuritiesInvestParam parameter) {
		//SELECT
		sbSelect	= new StringBuffer();
		sbSelect.append("	NVL(bb.typeID, -1) typeID, \n");
		sbSelect.append("	NVL(bb.ID, -1) id, \n");
		sbSelect.append("	NVL(bb.shortName, '') shortName, \n");
		sbSelect.append("	NVL(bb.securitiesCode1, '') securitiesCode1, \n");
		sbSelect.append("	NVL(bb.securitiesCode2, '') securitiesCode2, \n");
		sbSelect.append("	NVL(bb.remark, '') remark, \n");
		sbSelect.append("	NVL(cc.applyAmount,0.0) applyAmount, \n");
		sbSelect.append("	DECODE(aa.remainQuantity,0,0,DECODE(bb.typeID,"+SECConstant.SecuritiesType.MUTUAL_FUND+",dd.netClosePrice,dd.closePrice)) currentPrice, \n");
		sbSelect.append("	aa.*, \n");
		sbSelect.append("	ROUND(aa.unitCost*aa.remainQuantity,2) remainAmount, \n");
		sbSelect.append("	ROUND((aa.outAmount-aa.unitCost*aa.outQuantity),2) unitProfitLoss, \n");
		sbSelect.append("	ROUND((aa.outAmount-aa.unitNetCost*aa.outQuantity),2) unitNetProfitLoss, \n");
		sbSelect.append("	DECODE(NVL(dd.closePrice,0.0),0,0,ROUND((NVL(dd.closePrice,0.0)-aa.unitCost)*aa.remainQuantity,2)) payoff, \n");
		sbSelect.append("	DECODE(NVL(dd.netClosePrice,0.0),0,0,ROUND((NVL(dd.netClosePrice,0.0)-aa.unitNetCost)*aa.remainQuantity,2)) netPayoff \n");
		//FROM
		sbFrom		= new StringBuffer();
		sbFrom.append("	(SELECT securitiesID, inAmount, inQuantity, outAmount, outQuantity, \n");
		sbFrom.append("		inQuantity-outQuantity remainQuantity, \n");
		sbFrom.append("		ROUND(DECODE(SIGN(inQuantity),0,0,inAmount/inQuantity),2) unitCost, \n");
		sbFrom.append("		ROUND(DECODE(SIGN(inQuantity),0,0,inAmount/inQuantity),2) unitNetCost \n");
		sbFrom.append("	FROM \n");
		sbFrom.append("		(SELECT a.securitiesID, \n");
		sbFrom.append("			SUM(NVL(DECODE(b.stockDirection,"+SECConstant.StockDirection.OUT+",netIncome),0.0)) outAmount, \n");
		sbFrom.append("			SUM(NVL(DECODE(b.stockDirection,"+SECConstant.StockDirection.OUT+",a.quantity),0.0)) outQuantity, \n");
		sbFrom.append("			SUM(NVL(DECODE(b.stockDirection,"+SECConstant.StockDirection.IN+",netIncome),0.0)) inAmount, \n");
		sbFrom.append("			SUM(NVL(DECODE(b.stockDirection,"+SECConstant.StockDirection.IN+",a.quantity),0.0)) inQuantity \n");
		sbFrom.append("		FROM sec_DeliveryOrder a, sec_TransactionType b, sec_Securities c \n");
		sbFrom.append("		WHERE 1 = 1 \n");
		sbFrom.append("		AND a.officeID = "+parameter.getOfficeId()+" \n");
		sbFrom.append("		AND a.currencyID = "+parameter.getCurrencyId()+" \n");
		sbFrom.append("		AND a.statusID >= "+SECConstant.DeliveryOrderStatus.CHECKED+" \n");
		sbFrom.append("		AND a.deliveryDate <= TO_DATE('"+DataFormat.getDateString(parameter.getInputDate())+"', 'YYYY-MM-DD') \n");
		sbFrom.append("		AND b.statusID = "+SECConstant.TransactionTypeStatus.CHECKED+" \n");
		sbFrom.append("		AND b.ID = a.transactionTypeID \n");
		sbFrom.append("		AND c.currencyID = "+parameter.getCurrencyId()+" \n");
		sbFrom.append("		AND c.statusID >= "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbFrom.append("		AND c.ID = a.securitiesID \n");
		//业务单位名称
		if (parameter.getClientId() > 0) {
			sbFrom.append("		AND a.clientID = "+parameter.getClientId()+" \n");
		}
		//证券类别
		if (parameter.getTransactionTypeId() > 0) {
			sbFrom.append("		AND c.typeID = "+parameter.getTransactionTypeId()+" \n");
		}
		//资金帐号
		if (parameter.getAccountId() != null && !"".equals(parameter.getAccountId().trim())) {
			sbFrom.append("		AND a.accountID IN ("+parameter.getAccountId()+") \n");
		}
		//证券名称
		if (parameter.getSecuritiesId() != null && !"".equals(parameter.getSecuritiesId().trim())) {
			sbFrom.append("		AND a.securitiesID IN ("+parameter.getSecuritiesId()+") \n");
		}
		//交易对手名称ID
		if (parameter.getBankCounterPartId() != null && !"".equals(parameter.getBankCounterPartId().trim())) {
			sbFrom.append("		AND a.counterPartId IN ("+parameter.getBankCounterPartId()+") \n");
		}
		//开户营业部名称ID
		if (parameter.getBourseCounterPartId() != null && !"".equals(parameter.getBourseCounterPartId().trim())) {
			sbFrom.append("		AND a.counterPartId IN ("+parameter.getBourseCounterPartId()+") \n");
		}
		//基金管理公司名称ID
		if (parameter.getFundCounterPartId() != null && !"".equals(parameter.getFundCounterPartId().trim())) {
			sbFrom.append("		AND a.counterPartId IN ("+parameter.getFundCounterPartId()+") \n");
		}
		sbFrom.append("	GROUP BY securitiesID) \n");
		sbFrom.append("	) aa, \n");
		sbFrom.append("	sec_Securities bb, \n");
		sbFrom.append("	(SELECT securitiesID, SUM(NVL(a.netIncome,0.0)) applyAmount \n");
		sbFrom.append("	FROM sec_DeliveryOrder a,sec_TransactionType b \n");
		sbFrom.append("	WHERE 1 = 1 \n");
		sbFrom.append("		AND a.currencyID = "+parameter.getCurrencyId()+" \n");
		sbFrom.append("		AND a.officeID = "+parameter.getOfficeId()+" \n");
		sbFrom.append("		AND a.statusID >= "+SECConstant.DeliveryOrderStatus.CHECKED+" \n");
		sbFrom.append("		AND a.deliveryDate <= TO_DATE('"+DataFormat.getDateString(parameter.getInputDate())+"', 'YYYY-MM-DD') \n");
		sbFrom.append("		AND b.statusID = "+SECConstant.TransactionTypeStatus.CHECKED+" \n");
		sbFrom.append("		AND b.ID = a.transactionTypeID \n");
		sbFrom.append("		AND b.ID IN ("+
				SECConstant.BusinessType.STOCK_BID_ONLINE.INITIAL_OFFERING_BID_ONLINE+","+
				SECConstant.BusinessType.STOCK_BID_ONLINE.PROMOTION_BID_ONLINE+","+
				SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_BID+","+
				SECConstant.BusinessType.STOCK_BID.PROMOTION_BID+","+
				SECConstant.BusinessType.BANK_NATIONAL_BOND_BID.BOND_BID+","+
				SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_BID.BOND_BID+","+
				SECConstant.BusinessType.FINANCIAL_BOND_BID.BOND_BID+","+
				SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID+","+
				SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.BOND_BID_ONLINE+","+
				SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID+","+
				SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.FUND_BID_ONLINE+","+
				SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID+","+
				SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.FUND_SUBSCRIBE+","+
				SECConstant.BusinessType.MUTUAL_FUND_BID.FUND_BID+
				") \n");
		sbFrom.append("	GROUP BY securitiesID \n");
		sbFrom.append("	) cc, \n");
		sbFrom.append("	(SELECT securitiesCode, NVL(closePrice,0.0) closePrice, NVL(netClosePrice,0.0) netClosePrice \n");
		sbFrom.append("	FROM sec_SecuritiesMarket \n");
		sbFrom.append("	WHERE 1 = 1 \n");
		//sbFrom.append("		AND statusID = "+SECConstant.SecuritiesMarketStatus.CHECKED+" \n");
		sbFrom.append("		AND statusID = 1 \n");
		sbFrom.append("		AND closeDate = TO_DATE('"+
			DataFormat.getDateString(parameter.getInputDate())+"', 'YYYY-MM-DD') \n");
		sbFrom.append("	) dd \n");
		//WHERE
		sbWhere		= new StringBuffer();
		sbWhere.append(" 1 = 1 \n");
		sbWhere.append("	AND bb.currencyID = "+parameter.getCurrencyId()+" \n");
		sbWhere.append("	AND bb.statusID = "+SECConstant.SecuritiesStatus.CHECKED+" \n");
		sbWhere.append("	AND aa.securitiesID = bb.ID \n");
		sbWhere.append("	AND aa.securitiesID = cc.securitiesID(+) \n");
		sbWhere.append("	AND bb.securitiesCode1 || bb.securitiesCode2 LIKE dd.securitiesCode(+) || '%' \n");
		sbWhere.append("	AND bb.typeID IN ("+
			SECConstant.SecuritiesType.STOCK_A+","+
			SECConstant.SecuritiesType.STOCK_B+","+
			SECConstant.SecuritiesType.ENTERPRISE_BOND+","+
			SECConstant.SecuritiesType.TRANSFORMABLE_BOND+","+
			SECConstant.SecuritiesType.ENCLOSED_FUND+","+
			SECConstant.SecuritiesType.MUTUAL_FUND+","+
			SECConstant.SecuritiesType.EXCHANGECENTER_NATIONAL_BOND+","+
			SECConstant.SecuritiesType.EXCHANGECENTER_BOND_REPURCHASE+","+
			SECConstant.SecuritiesType.EXCHANGECENTER_ENTERPRISE_BOND+","+
			SECConstant.SecuritiesType.BANK_NATIONAL_BOND+","+
			SECConstant.SecuritiesType.BANK_BOND_REPURCHASE+","+
			SECConstant.SecuritiesType.FINANCIAL_BOND+") \n");
	}

	/**
	 * 余额表数据
	 * @param  PrintSecuritiesInvestParam
	 * @return PageLoader
	 * @exception SecuritiesException
	 */
	public PageLoader printSecuritiesInvest(PrintSecuritiesInvestParam parameter) throws SecuritiesException {
		this.initialSql(parameter);
		//获取PageLoader对象
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject(
			"com.iss.system.dao.PageLoader");

		//初始化PageLoader对象、实现查询和分页
		pageLoader.initPageLoader(
			new AppContext(),
			sbFrom.toString(),
			sbSelect.toString(),
			sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.securities.print.dataentity.PrintSecuritiesInvestInfo",
			null);
		pageLoader.setOrderBy("ORDER BY bb.TypeID, bb.ID");

		return pageLoader;
	}
}